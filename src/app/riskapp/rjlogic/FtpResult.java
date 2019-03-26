package app.riskapp.rjlogic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.riskapp.common.CommonFunctions;
import app.riskapp.common.DaoFactory;
import app.riskapp.entity.ComSysParm;
import app.riskapp.util.IDUtil;

public class FtpResult {

	/**
	 * @param args
	 */

	public static DaoFactory daoFactory = new DaoFactory();

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {

		getjicache();

	}

	/**
	 * 根据最大日期和统计维度获取最小日期
	 * 
	 * @param maxDate
	 * @param assessScope
	 *            -1月度-3季度-12年度
	 * @return
	 */
	public static String getMinDate(String maxDate, Integer assessScope) {
		int nowMonth = Integer.valueOf(String.valueOf(maxDate).substring(4, 6));// 当前月
		if (assessScope == -3) {// 季度
			if (nowMonth >= 1 && nowMonth <= 3)
				assessScope = 0 - nowMonth;
			else if (nowMonth >= 4 && nowMonth <= 6)
				assessScope = 3 - nowMonth;
			else if (nowMonth >= 7 && nowMonth <= 9)
				assessScope = 6 - nowMonth;
			else if (nowMonth >= 10 && nowMonth <= 12)
				assessScope = 9 - nowMonth;
		} else if (assessScope == -12) {// 年度
			assessScope = -nowMonth;
		}
		String minDate = CommonFunctions.dateModifyM(maxDate, assessScope);
		return minDate;
	}

	/**
	 * 
	 * 季缓存
	 */
	public static void getjicache() {

		String STime = CommonFunctions.GetCurrentTime();
		String sql = "from ComSysParm order by sysDate DESC";
		List<ComSysParm> list = CommonFunctions.mydao.query(sql, null);
		ComSysParm com_sys_parm = new ComSysParm();
		if (list.size() == 0) {
			System.out.println("系统日期获取出错，请检查数据库公共参数表com_sys_parm！");
			return;
		}
		com_sys_parm = list.get(0);
		long oldTodayDate = com_sys_parm.getSysDate();
		String newTodayDate = String.valueOf(oldTodayDate);
		int nowMonth = Integer.valueOf(String.valueOf(newTodayDate).substring(
				4, 6));// 当前月
		String date = getMinDate(newTodayDate, -3);
		System.out.println(date);
		String xlsBrNo = "3403111034";
		String minDate = date;
		String maxDate = CommonFunctions.dateModifyD(newTodayDate, 1);
		System.out.println(maxDate);
		if (nowMonth == 3 || nowMonth == 6 || nowMonth == 9 || nowMonth == 12) {
			if (maxDate.endsWith("01")) {
				getQxppResultList(xlsBrNo, minDate, newTodayDate);
			}
		}
	}

	/**
	 * 以员工账户关联表为基准，获取日期范围内的账户的机构、产品、客户经理、日均余额、利润、账号、客户号
	 * 
	 * @param xlsbrNo
	 * @param minDate日期左端点
	 * @param maxDate日期右端点
	 * @return
	 */
	public static void getQxppResultList(String xlsbrNo, String minDate,
			String maxDate) {
		List resultList = new ArrayList();

		int count = 0;
		// String prdtNo = this.getPrdtNoQxpp(xlsbrNo);//期限匹配下配置的所有产品

		// 日均余额map,key<账号>,value<时间范围内日均余额的累计>
		Map<String, Double> rjyeMap = new HashMap<String, Double>();

		// 以账号为key的账户 ftp价格map,key<账号>,value<FTP和rate差值@客户号>
		Map<String, String> accFtpValueMap = new HashMap<String, String>();

		// 1.获取每个账户最新的一次定价(默认时间段内只定过一次价 )
		String sql1 = " select * from (select t.rate,"
				+ " (case when ((case when t.NOW_FIV_STS is null then fiv_sts else t.NOW_FIV_STS end) not in ('03','04','05') and t.business_no='YW101') OR t.business_no!='YW101' then t.ftp_price else t.rate end) as ftp_price, "
				+ " t.product_no,t.ac_id,t.cust_no, row_number() over(partition by t.ac_id order by t.wrk_sys_date desc ) rn "
				+ " from ftp.ftp_qxpp_result t" + " where t.br_no is not null "
				+ " and to_date(t.wrk_sys_date,'yyyymmdd') <= to_date('"
				+ maxDate + "','yyyymmdd')" +
				// " and t.product_no in ("+prdtNo+")" +
				" ) where rn=1 ";

		List dcfhqList = daoFactory.query1(sql1);
		if (dcfhqList.size() > 0) {
			for (Object object : dcfhqList) {
				Object[] obj = (Object[]) object;
				double rate = obj[0] == null ? 0.0 : Double.valueOf(obj[0]
						.toString());// 利率
				double ftp = obj[1] == null ? 0.0 : Double.valueOf(obj[1]
						.toString());// FTP
				double cz = obj[2].toString().indexOf("P2") != -1 ? (ftp - rate)
						: (rate - ftp);// 资产rate-ftp,负债ftp-rate
				accFtpValueMap.put(String.valueOf(obj[3]).trim(), cz + "@"
						+ String.valueOf(obj[4]).trim());

			}
		}
		dcfhqList.clear();
		System.gc();// 释放dcfhqList所占的内存
		Integer days = CommonFunctions.daysSubtract(maxDate, minDate);
		// 2.1.计算存贷款相关的日均余额
		String sql2 = "select sum(bal_daysum),ac_id from ftp.day_averate_bal "
				+ " where to_date(cyc_date,'yyyymmdd')>to_date('" + minDate
				+ "','yyyymmdd')"
				+ " and to_date(cyc_date,'yyyymmdd')<=to_date('" + maxDate
				+ "','yyyymmdd') " + " and term_type != '00' "
				+ " and business_no in ('YW101')" + " group by ac_id ";
		System.out.println("days=" + days);
		List rjyeList = daoFactory.query1(sql2);
		if (rjyeList.size() > 0) {
			for (Object object : rjyeList) {
				Object[] obj = (Object[]) object;
				double ye = obj[0] == null ? 0.0 : Double.valueOf(obj[0]
						.toString());// 日均余额
				if (rjyeMap.get(String.valueOf(obj[1]).trim()) != null) {
					System.out.println("日均余额积数表：rjyeMap.get("
							+ String.valueOf(obj[1]).trim() + ")!=null");
				}
				rjyeMap.put(String.valueOf(obj[1]).trim(), ye / days);
			}
		}

		rjyeList.clear();
		System.gc();// 释放rjyeList所占的内存

		// 2.2.计算金融市场相关的日均余额
		// (2.1中已经计算了该类的余额基数，但是因为准确性无法保证，所以先按该方法进行计算)，获取其生效时间段*余额/日期数
		String sql2_2 = "select bal,ac_id,"
				+ " days(least(to_date(case when mtr_date is null then '20991231' else mtr_date end,'yyyymmdd'), to_date('"
				+ maxDate
				+ "','yyyymmdd')))-days(greatest(to_date(opn_date,'yyyymmdd'), to_date('"
				+ minDate + "','yyyymmdd'))) term " + " from ftp.jr_account "
				+ " where to_date(mtr_date,'yyyymmdd') >to_date('" + minDate
				+ "','yyyymmdd') "
				+ " and to_date(opn_date,'yyyymmdd') <= to_date('" + maxDate
				+ "','yyyymmdd')";
		List rjyeList2 = daoFactory.query1(sql2_2);
		if (rjyeList2.size() > 0) {
			for (Object object : rjyeList2) {
				Object[] obj = (Object[]) object;
				Integer term = obj[2] == null ? 0 : (Integer.valueOf(obj[2]
						.toString()) < 0 ? 0 : Integer.valueOf(obj[2]
						.toString()));
				double ye = obj[0] == null ? 0.0 : Double.valueOf(obj[0]
						.toString());
				if (rjyeMap.get(String.valueOf(obj[1]).trim()) != null) {
					System.out.println("金融市场：rjyeMap.get("
							+ String.valueOf(obj[1]).trim() + ")!=null");
				}
				rjyeMap.put(String.valueOf(obj[1]).trim(), ye * term / days);// 日均余额=余额*其生效时间段/日期数
			}
		}
		rjyeList2.clear();
		System.gc();// 释放rjyeList所占的内存

		// 3.获取员工对应的机构信息
		Map<String, String> empInfoMap = new HashMap<String, String>();
		String sql3 = "select emp_no, br_no from ftp.ftp_emp_info";
		List empList = daoFactory.query1(sql3);
		if (empList != null && empList.size() > 0) {
			for (Object object : empList) {
				Object[] obj = (Object[]) object;
				empInfoMap.put(String.valueOf(obj[0]), String.valueOf(obj[1]));
			}
		}
		empList.clear();
		System.gc();// 释放empList所占的内存

		// 4.1.获取存贷款类业务和金融市场类业务的账户、客户经理关联信息
		String sql4 = "select acc_id, emp_no, rate, rel_type, prdt_no from ftp.ftp_emp_acc_rel";
		// +" union all " +
		// "select ac_id, tel,'1','1',prdt_no from ftp.jr_account";
		List accRelList = daoFactory.query1(sql4);
		System.out.println("accRelList.size()=" + accRelList.size());

		String errorInfo = "";

		int num_yue_0 = 0;
		int num_yue_null = 0;
		int num_yue_not0 = 0;
		int num_errorInfo = 0;
		double rjye_whole = 0;
		double rjye_rate_shao = 0;

		double rjye_whole_mx = 0;

		for (Object object : accRelList) {
			Object[] obj = (Object[]) object;
			String accId = String.valueOf(obj[0]).trim();
			Double rjye = rjyeMap.get(accId);// 日均余额
			if (rjye == null) {
				// System.out.println("日均余额为null的:"+accId);
				num_yue_null++;
			} else if (rjye == 0) {
				System.out.println("日均余额为0的:" + accId);
				num_yue_0++;
			} else {
				rjye_whole += rjye;
				num_yue_not0++;
			}
			rjye = ((rjye == null) ? 0.0 : rjye);
			Double ftplr = 0.0;// FTP利润
			String ftpInfo = accFtpValueMap.get(String.valueOf(obj[0]));
			String custNo = "";
			if (ftpInfo != null) {
				String[] ftpInfos = ftpInfo.split("@");
				// 存在客户号为空的情况
				if (ftpInfos.length == 2) {
					// System.out.println("进入ftpInfos.length ==2");
					ftplr = Double.valueOf(ftpInfos[0]) == null ? 0.0 : Double
							.valueOf(ftpInfos[0]);
					custNo = ftpInfos[1];
				} else {// 如果为空，则直接获取ftplr
					// System.out.println("进入ftpInfos.length !=2:"+ftpInfos.length+"-->"+ftpInfo);
					ftpInfo = ftpInfo.substring(0, ftpInfo.length() - 1);
					ftplr = Double.valueOf(ftpInfo) == null ? 0.0 : Double
							.valueOf(ftpInfo);
				}

			}
			String[] empNos = String.valueOf(obj[1]).split("@");
			String[] rates = String.valueOf(obj[2]).split("@");
			double[] ratios = new double[rates.length];// 分割比例
			for (int g = 0; g < rates.length; g++) {
				ratios[g] = Double.valueOf(rates[g]);
			}
			// 处理分配方式为固定金额的分配比例
			if (String.valueOf(obj[3]).equals("2")) {// 分配方式为"固定金额"时
				System.out.println("进入String.valueOf(obj[3]).equals(\"2\")");

				double sumAmt = 0;// sum分配方式为固定金额时的分割金额总和
				for (int i = 0; i < ratios.length; i++) {
					ratios[i] = Double.valueOf(rates[i]);
					sumAmt += ratios[i];
				}

				if (sumAmt < rjye) {// 如果总和<日均余额，则第一个客户经理分配到金额=日均余额-其他经理分配金额和
					ratios[0] = rjye - (sumAmt - ratios[0]);
					sumAmt = rjye;
				}
				// 如果金额总和>=日均余额，不需要处理金额直接按金额所占比例进行计算，

				for (int i = 0; i < ratios.length; i++) {
					ratios[i] = ratios[i] / sumAmt;// 计算比例
				}
			}

			double r_whole = 0;
			for (int i = 0; i < empNos.length; i++) {
				r_whole += ratios[i];
			}
			if (r_whole != 1) {
				System.out.println(r_whole + "!=1," + obj[1] + "," + obj[2]
						+ "-->" + String.valueOf(obj[0]) + ":" + rjye);
				rjye_rate_shao += rjye * (1 - r_whole);
			}

			// 存入结果列表
			for (int i = 0; i < empNos.length; i++) {
				String[] result = new String[7];
				if (empInfoMap.get(empNos[i]) == null) {
					errorInfo += obj[0] + ",";
					num_errorInfo++;
					continue;
				} else {
					result[0] = empInfoMap.get(empNos[i]);// 机构
				}

				result[1] = String.valueOf(obj[4]);// 产品
				result[2] = String.valueOf(empNos[i]);// 客户经理编号
				double avebal = rjye * ratios[i];

				rjye_whole_mx += avebal;

				result[3] = String.valueOf(avebal);// 日均余额
				Double rj_ye = Double.valueOf(result[3].toString());
				result[4] = String.valueOf(avebal * ftplr * days / 360);// FTP利润
				Double ftp_liyun = Double.valueOf(result[4].toString());
				result[5] = String.valueOf(obj[0]);// 账号
				result[6] = custNo;// 客户号
				String sql12 = "insert into app.PA_AM_EMP_PVALUE_TEST(EPVALUE_ID,AC_ID,PRDT_NO,STARTDATE,ENDDATE,ORG_ID,KHJL,RIJUN_VALUE,FTP_VALUE,TERM) values('"
						+ IDUtil.getInstanse().getUID()
						+ "','"
						+ result[5]
						+ "','"
						+ result[1]
						+ "','"
						+ minDate
						+ "','"
						+ maxDate
						+ "','"
						+ result[0]
						+ "','"
						+ result[2]
						+ "',"
						+ rj_ye
						+ "," + ftp_liyun + ",'5')";
				if (rj_ye != 0) {

					resultList.add(sql12);
					count++;

				}
			}
		}
		System.out.println("num_yue_null=" + num_yue_null);
		System.out.println("num_yue_0=" + num_yue_0);
		System.out.println("num_yue_not0=" + num_yue_not0);
		System.out.println("num_errorInfo=" + num_errorInfo);
		System.out.println("rjye_whole=" + rjye_whole);
		System.out.println("rjye_whole_mx=" + rjye_whole_mx);
		System.out.println("rjye_rate_shao=" + rjye_rate_shao);

		if (!errorInfo.equals("")) {
			System.out.println("账号为" + errorInfo + "<n>的员工账户关联配置有误，未纳入统计，请检查！");
		}
		accRelList.clear();
		System.gc();// 释放accRelList所占的内存
		accFtpValueMap.clear();
		System.gc();
		empInfoMap.clear();
		System.gc();

		System.out.println(count);
		boolean f = daoFactory.execute1_s(resultList);
		if (f) {

			System.out.println("导入成功");
		} else {

			System.out.println("导入失败");
		}
	}
}
