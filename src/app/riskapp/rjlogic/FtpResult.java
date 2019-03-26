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
	 * ����������ں�ͳ��ά�Ȼ�ȡ��С����
	 * 
	 * @param maxDate
	 * @param assessScope
	 *            -1�¶�-3����-12���
	 * @return
	 */
	public static String getMinDate(String maxDate, Integer assessScope) {
		int nowMonth = Integer.valueOf(String.valueOf(maxDate).substring(4, 6));// ��ǰ��
		if (assessScope == -3) {// ����
			if (nowMonth >= 1 && nowMonth <= 3)
				assessScope = 0 - nowMonth;
			else if (nowMonth >= 4 && nowMonth <= 6)
				assessScope = 3 - nowMonth;
			else if (nowMonth >= 7 && nowMonth <= 9)
				assessScope = 6 - nowMonth;
			else if (nowMonth >= 10 && nowMonth <= 12)
				assessScope = 9 - nowMonth;
		} else if (assessScope == -12) {// ���
			assessScope = -nowMonth;
		}
		String minDate = CommonFunctions.dateModifyM(maxDate, assessScope);
		return minDate;
	}

	/**
	 * 
	 * ������
	 */
	public static void getjicache() {

		String STime = CommonFunctions.GetCurrentTime();
		String sql = "from ComSysParm order by sysDate DESC";
		List<ComSysParm> list = CommonFunctions.mydao.query(sql, null);
		ComSysParm com_sys_parm = new ComSysParm();
		if (list.size() == 0) {
			System.out.println("ϵͳ���ڻ�ȡ�����������ݿ⹫��������com_sys_parm��");
			return;
		}
		com_sys_parm = list.get(0);
		long oldTodayDate = com_sys_parm.getSysDate();
		String newTodayDate = String.valueOf(oldTodayDate);
		int nowMonth = Integer.valueOf(String.valueOf(newTodayDate).substring(
				4, 6));// ��ǰ��
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
	 * ��Ա���˻�������Ϊ��׼����ȡ���ڷ�Χ�ڵ��˻��Ļ�������Ʒ���ͻ������վ��������˺š��ͻ���
	 * 
	 * @param xlsbrNo
	 * @param minDate������˵�
	 * @param maxDate�����Ҷ˵�
	 * @return
	 */
	public static void getQxppResultList(String xlsbrNo, String minDate,
			String maxDate) {
		List resultList = new ArrayList();

		int count = 0;
		// String prdtNo = this.getPrdtNoQxpp(xlsbrNo);//����ƥ�������õ����в�Ʒ

		// �վ����map,key<�˺�>,value<ʱ�䷶Χ���վ������ۼ�>
		Map<String, Double> rjyeMap = new HashMap<String, Double>();

		// ���˺�Ϊkey���˻� ftp�۸�map,key<�˺�>,value<FTP��rate��ֵ@�ͻ���>
		Map<String, String> accFtpValueMap = new HashMap<String, String>();

		// 1.��ȡÿ���˻����µ�һ�ζ���(Ĭ��ʱ�����ֻ����һ�μ� )
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
						.toString());// ����
				double ftp = obj[1] == null ? 0.0 : Double.valueOf(obj[1]
						.toString());// FTP
				double cz = obj[2].toString().indexOf("P2") != -1 ? (ftp - rate)
						: (rate - ftp);// �ʲ�rate-ftp,��ծftp-rate
				accFtpValueMap.put(String.valueOf(obj[3]).trim(), cz + "@"
						+ String.valueOf(obj[4]).trim());

			}
		}
		dcfhqList.clear();
		System.gc();// �ͷ�dcfhqList��ռ���ڴ�
		Integer days = CommonFunctions.daysSubtract(maxDate, minDate);
		// 2.1.����������ص��վ����
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
						.toString());// �վ����
				if (rjyeMap.get(String.valueOf(obj[1]).trim()) != null) {
					System.out.println("�վ���������rjyeMap.get("
							+ String.valueOf(obj[1]).trim() + ")!=null");
				}
				rjyeMap.put(String.valueOf(obj[1]).trim(), ye / days);
			}
		}

		rjyeList.clear();
		System.gc();// �ͷ�rjyeList��ռ���ڴ�

		// 2.2.��������г���ص��վ����
		// (2.1���Ѿ������˸������������������Ϊ׼ȷ���޷���֤�������Ȱ��÷������м���)����ȡ����Чʱ���*���/������
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
					System.out.println("�����г���rjyeMap.get("
							+ String.valueOf(obj[1]).trim() + ")!=null");
				}
				rjyeMap.put(String.valueOf(obj[1]).trim(), ye * term / days);// �վ����=���*����Чʱ���/������
			}
		}
		rjyeList2.clear();
		System.gc();// �ͷ�rjyeList��ռ���ڴ�

		// 3.��ȡԱ����Ӧ�Ļ�����Ϣ
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
		System.gc();// �ͷ�empList��ռ���ڴ�

		// 4.1.��ȡ�������ҵ��ͽ����г���ҵ����˻����ͻ����������Ϣ
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
			Double rjye = rjyeMap.get(accId);// �վ����
			if (rjye == null) {
				// System.out.println("�վ����Ϊnull��:"+accId);
				num_yue_null++;
			} else if (rjye == 0) {
				System.out.println("�վ����Ϊ0��:" + accId);
				num_yue_0++;
			} else {
				rjye_whole += rjye;
				num_yue_not0++;
			}
			rjye = ((rjye == null) ? 0.0 : rjye);
			Double ftplr = 0.0;// FTP����
			String ftpInfo = accFtpValueMap.get(String.valueOf(obj[0]));
			String custNo = "";
			if (ftpInfo != null) {
				String[] ftpInfos = ftpInfo.split("@");
				// ���ڿͻ���Ϊ�յ����
				if (ftpInfos.length == 2) {
					// System.out.println("����ftpInfos.length ==2");
					ftplr = Double.valueOf(ftpInfos[0]) == null ? 0.0 : Double
							.valueOf(ftpInfos[0]);
					custNo = ftpInfos[1];
				} else {// ���Ϊ�գ���ֱ�ӻ�ȡftplr
					// System.out.println("����ftpInfos.length !=2:"+ftpInfos.length+"-->"+ftpInfo);
					ftpInfo = ftpInfo.substring(0, ftpInfo.length() - 1);
					ftplr = Double.valueOf(ftpInfo) == null ? 0.0 : Double
							.valueOf(ftpInfo);
				}

			}
			String[] empNos = String.valueOf(obj[1]).split("@");
			String[] rates = String.valueOf(obj[2]).split("@");
			double[] ratios = new double[rates.length];// �ָ����
			for (int g = 0; g < rates.length; g++) {
				ratios[g] = Double.valueOf(rates[g]);
			}
			// ������䷽ʽΪ�̶����ķ������
			if (String.valueOf(obj[3]).equals("2")) {// ���䷽ʽΪ"�̶����"ʱ
				System.out.println("����String.valueOf(obj[3]).equals(\"2\")");

				double sumAmt = 0;// sum���䷽ʽΪ�̶����ʱ�ķָ����ܺ�
				for (int i = 0; i < ratios.length; i++) {
					ratios[i] = Double.valueOf(rates[i]);
					sumAmt += ratios[i];
				}

				if (sumAmt < rjye) {// ����ܺ�<�վ������һ���ͻ�������䵽���=�վ����-��������������
					ratios[0] = rjye - (sumAmt - ratios[0]);
					sumAmt = rjye;
				}
				// �������ܺ�>=�վ�������Ҫ������ֱ�Ӱ������ռ�������м��㣬

				for (int i = 0; i < ratios.length; i++) {
					ratios[i] = ratios[i] / sumAmt;// �������
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

			// �������б�
			for (int i = 0; i < empNos.length; i++) {
				String[] result = new String[7];
				if (empInfoMap.get(empNos[i]) == null) {
					errorInfo += obj[0] + ",";
					num_errorInfo++;
					continue;
				} else {
					result[0] = empInfoMap.get(empNos[i]);// ����
				}

				result[1] = String.valueOf(obj[4]);// ��Ʒ
				result[2] = String.valueOf(empNos[i]);// �ͻ�������
				double avebal = rjye * ratios[i];

				rjye_whole_mx += avebal;

				result[3] = String.valueOf(avebal);// �վ����
				Double rj_ye = Double.valueOf(result[3].toString());
				result[4] = String.valueOf(avebal * ftplr * days / 360);// FTP����
				Double ftp_liyun = Double.valueOf(result[4].toString());
				result[5] = String.valueOf(obj[0]);// �˺�
				result[6] = custNo;// �ͻ���
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
			System.out.println("�˺�Ϊ" + errorInfo + "<n>��Ա���˻�������������δ����ͳ�ƣ����飡");
		}
		accRelList.clear();
		System.gc();// �ͷ�accRelList��ռ���ڴ�
		accFtpValueMap.clear();
		System.gc();
		empInfoMap.clear();
		System.gc();

		System.out.println(count);
		boolean f = daoFactory.execute1_s(resultList);
		if (f) {

			System.out.println("����ɹ�");
		} else {

			System.out.println("����ʧ��");
		}
	}
}
