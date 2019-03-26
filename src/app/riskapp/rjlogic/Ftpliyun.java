package app.riskapp.rjlogic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.riskapp.common.CommonFunctions;
import app.riskapp.common.DaoFactory;
import app.riskapp.entity.ComSysParm;

public class Ftpliyun {

	/**
	 * @param args
	 */ 
	
	
	
    public	static DaoFactory daoFactory = new DaoFactory();
	
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
	
		getmonthcache();
        
	}
	
	
	
	/**
	 * 
	 * �»���
	 */
	public static void getmonthcache() {

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

		String xlsBrNo = "3403111034";
		String maxDate = CommonFunctions.dateModifyD(newTodayDate, 1);
		System.out.println(maxDate);
		String minDate = String.valueOf(CommonFunctions.dateModifyM(
				newTodayDate, -1));
		System.out.println(minDate);
		if(maxDate.endsWith("01")){
			QxppResultList(xlsBrNo,minDate,maxDate);
		}
	

	}
	
	/**
	  * ��ȡĳһ������ָ�������µ�����ƥ�䶨�� 
	  * @param xlsbrNo
	  * @param minDate������˵�
	  * @param maxDate�����Ҷ˵�
	  * @return
	  */
	@SuppressWarnings("unchecked")
	public static void QxppResultList(String xlsbrNo, String minDate, String maxDate) {
		List<String[]> resultList=new ArrayList<String[]>();
        @SuppressWarnings("unused")
		Map khjlmap = new HashMap();
        Map ftplymap = new HashMap();
        Map ratiosMap = new HashMap();
        Map brNameMap = new HashMap();
        Map empNameMap = new HashMap();
        
        String sql = "select BR_NO,BR_NAME from ftp.br_mst ";
    	List brList = daoFactory.query1(sql);
    	for(Object obj :brList){
    		
    		Object[] object = (Object[])obj;
    		brNameMap.put(object[0],object[1]);
    		
    	}
        
    	sql = "select EMP_NO,EMP_NAME from ftp.FTP_EMP_INFO ";
    	List empInfoList = daoFactory.query1(sql);
    	for(Object obj :empInfoList){
    		
    		Object[] object = (Object[])obj;
    		empNameMap.put(object[0],object[1]);
    		
    	}
      
		//String prdtNo = this.getPrdtNoQxpp(xlsbrNo);//����ƥ�������õ����в�Ʒ
		
		//�վ����map,key<�˺�>,value<ʱ�䷶Χ���վ������ۼ�>
		Map<String, Double> rjyeMap = new HashMap<String, Double>();
		
		//���˺�Ϊkey���˻� ftp�۸�map,key<�˺�>,value<FTP��rate��ֵ>
		Map<String, Double> accFtpValueMap = new HashMap<String, Double>();

		//1.��ȡÿ���˻����µ�һ�ζ���(Ĭ��ʱ�����ֻ����һ�μ� )
		String sql1 = " select * from (select t.rate," +
					" (case when ((case when t.NOW_FIV_STS is null then fiv_sts else t.NOW_FIV_STS end) not in ('03','04','05') and t.business_no='YW101') OR t.business_no!='YW101' then t.ftp_price else t.rate end) as ftp_price, " +
			        " t.product_no,t.ac_id,t.cust_no,(case when length(trim(t.is_zq))=1 then t.is_zq else '0' end) is_zq, row_number() over(partition by t.ac_id order by t.wrk_sys_date desc ) rn " +
	                " from ftp.ftp_qxpp_result t" +
	                " where  t.br_no is not null " +
			        " and to_date(t.wrk_sys_date,'yyyymmdd') <= to_date('"+maxDate+"','yyyymmdd')" +
			       //	" and t.product_no in ("+prdtNo+")" +
	                " ) where rn=1 ";
		
		List dcfhqList = daoFactory.query1(sql1);
		if(dcfhqList.size() > 0) {
			for(Object object : dcfhqList) {
				Object[] obj = (Object[])object;
				double rate = obj[0] == null ? 0.0 : Double.valueOf(obj[0].toString());//����
				double ftp = obj[1] == null ? 0.0 : Double.valueOf(obj[1].toString());//FTP
				double cz = obj[2].toString().indexOf("P2")!=-1?(ftp-rate):(rate-ftp);//�ʲ�rate-ftp,��ծftp-rate
				accFtpValueMap.put(String.valueOf(obj[3]).trim(), cz);
			}
		}
		dcfhqList.clear(); System.gc();//�ͷ�dcfhqList��ռ���ڴ�
		Integer days = CommonFunctions.daysSubtract(maxDate, minDate);
		//2.1.����������ص��վ����
		String sql2 = "select sum(bal_daysum),ac_id from ftp.day_averate_bal " +
        " where to_date(cyc_date,'yyyymmdd')>to_date('"+minDate+"','yyyymmdd')" +
		        " and to_date(cyc_date,'yyyymmdd')<=to_date('"+maxDate+"','yyyymmdd') " +
		        " and term_type != '00' " +
                " group by ac_id ";
		System.out.println("days"+days);
		List rjyeList = daoFactory.query1(sql2);
		if(rjyeList.size() > 0) {
			for(Object object : rjyeList) {
				Object[] obj = (Object[])object;
				double ye = obj[0] == null ? 0.0 : Double.valueOf(obj[0].toString());//�վ����
				rjyeMap.put(String.valueOf(obj[1]).trim(), ye/days);
			}
		}
		
		rjyeList.clear(); System.gc();//�ͷ�rjyeList��ռ���ڴ�
		
		//2.2.��������г���ص��վ����
		//(2.1���Ѿ������˸������������������Ϊ׼ȷ���޷���֤�������Ȱ��÷������м���)����ȡ����Чʱ���*���/������
		String sql2_2 = "select bal,ac_id," +
				" days(least(to_date(case when mtr_date is null then '20991231' else mtr_date end,'yyyymmdd'), to_date('"+maxDate+"','yyyymmdd')))-days(greatest(to_date(opn_date,'yyyymmdd'), to_date('"+minDate+"','yyyymmdd'))) term " +
				" from ftp.jr_account " +
               " where to_date(mtr_date,'yyyymmdd') > to_date('"+minDate+"','yyyymmdd') " +
			    " and to_date(opn_date,'yyyymmdd') <= to_date('"+maxDate+"','yyyymmdd')";
		List rjyeList2 = daoFactory.query1(sql2_2);
		if(rjyeList2.size() > 0) {
			for(Object object : rjyeList2) {
				Object[] obj = (Object[])object;
				Integer term = obj[2] == null ? 0: (Integer.valueOf(obj[2].toString())<0?0:Integer.valueOf(obj[2].toString()));
				double ye = obj[0] == null ? 0.0 : Double.valueOf(obj[0].toString());
				rjyeMap.put(String.valueOf(obj[1]).trim(), ye*term/days);//�վ����=���*����Чʱ���/������
			}
		}
		rjyeList2.clear(); System.gc();//�ͷ�rjyeList��ռ���ڴ�
		
		//3.��ȡԱ����Ӧ�Ļ�����Ϣ
		Map<String, String> empInfoMap = new HashMap<String, String>();
		String sql3 = "select emp_no, br_no from ftp.ftp_emp_info";
		List empList = daoFactory.query1(sql3);
		if(empList != null && empList.size() > 0) {
			for(Object object : empList) {
				Object[] obj = (Object[])object;
				empInfoMap.put(String.valueOf(obj[0]), String.valueOf(obj[1]));
			}
		}
		empList.clear(); System.gc();//�ͷ�empList��ռ���ڴ�
		
		//4.1.��ȡ�������ҵ��ͽ����г���ҵ����˻����ͻ����������Ϣ
		String sql4 = "select acc_id, emp_no, rate, rel_type, prdt_no from ftp.ftp_emp_acc_rel" +
				      " union all " +
				      "select ac_id, tel,'1','1',prdt_no from ftp.jr_account";
		List accRelList = daoFactory.query1(sql4);
		
		String errorInfo = "";
		int count = 0;
		for(Object object : accRelList) {
			Object[] obj = (Object[])object;
			String accId = String.valueOf(obj[0]).trim();
			Double rjye = rjyeMap.get(accId);//�վ����
			rjye = rjye==null?0.0:rjye;
			Double ftplr = 0.0;//FTP����
			ftplr = accFtpValueMap.get(accId)==null?0.0:accFtpValueMap.get(accId);//FTP����
			ftplymap.put(obj[0], String.valueOf(rjye*ftplr*days/360));
			ftplr = ftplr==null?0.0:ftplr;
			String[] empNos = String.valueOf(obj[1]).split("@");
			khjlmap.put(accId, empNos);
			String[] rates = String.valueOf(obj[2]).split("@");
			ratiosMap.put(accId, rates);
		}
		accRelList.clear();System.gc();
		String sql12 = " select * from (select t.BR_NO,t.CUST_NO,t.PRODUCT_NAME,t.CUS_NAME,t.OPN_DATE,t.MTR_DATE,t.BAL, t.rate,t.is_zq," +
		" (case when ((case when t.NOW_FIV_STS is null then fiv_sts else t.NOW_FIV_STS end) not in ('03','04','05') and t.business_no='YW101') OR t.business_no!='YW101' then t.ftp_price else t.rate end) as ftp_price, " +
        " t.product_no,t.ac_id, row_number() over(partition by t.ac_id order by t.wrk_sys_date desc ) rn " +
        " from ftp.ftp_qxpp_result t" +
        " where  t.br_no is not null " +
        " and to_date(t.wrk_sys_date,'yyyymmdd') <= to_date('"+maxDate+"','yyyymmdd')" +
       //	" and t.product_no in ("+prdtNo+")" +
        " ) where rn=1  ";

		int jishu =0;
		List hsqllist = new ArrayList();
		List ftpList = daoFactory.query1(sql12);
		if(ftpList.size() > 0) {
			for(Object object : ftpList) {
				
				
				String khjl_no_one = "" ;
				String  khjl_name_one = "" ;
				String ratios_one = "" ;
				
				String khjl_no_two = "";
				String  khjl_name_two =  "";
				String ratios_two = "";
				
				String khjl_no_tree = "";
		        String  khjl_name_tree = "";
		    	String ratios_tree = "";
				
				Object[] obj = (Object[])object;
				String[] khjl = (String[])khjlmap.get(obj[11]);
				if(khjl!=null&&khjl.length==1){
					
					khjl_no_one = khjl[0];
				    khjl_name_one = (String)empNameMap.get(khjl[0]);
					String[] ratios = 	(String[])ratiosMap.get(obj[11]);
				    ratios_one = ratios[0];
					
				}
		       if(khjl!=null&&khjl.length==2){
					
					 khjl_no_one = khjl[0];
					 khjl_name_one = (String)empNameMap.get(khjl[0]);
					String[] ratios = 	(String[])ratiosMap.get(obj[11]);
					 ratios_one = ratios[0];
					
					 khjl_no_two = khjl[1];
				     khjl_name_two = (String)empNameMap.get(khjl[1]);
				     ratios_two = ratios[1];
					
				}
		       if(khjl!=null&&khjl.length==3){
		    	   
		    	  khjl_no_one = khjl[0];
		    	  khjl_name_one = (String)empNameMap.get(khjl[0]);
		    	  String[] ratios = 	(String[])ratiosMap.get(obj[11]);
		    	  ratios_one = ratios[0];
		    	   
		    	  khjl_no_two = khjl[1];
		    	  khjl_name_two = (String)empNameMap.get(khjl[1]);
		    	  ratios_two = ratios[1];
		    	   
		    	  khjl_no_tree = khjl[2];
		    	  khjl_name_tree = (String)empNameMap.get(khjl[2]);
		    	  ratios_tree = ratios[2];
		       }
				Double bal = Double.valueOf(obj[6].toString());
				Double lx = Double.valueOf(obj[7].toString());
				Double ftp_price = Double.valueOf(obj[9].toString());
				Double rj_value = Double.valueOf(rjyeMap.get(obj[11])==null?"0.0":rjyeMap.get(obj[11]).toString());
				Double rjjs_value = rj_value*days;
				Double lc_value = Double.valueOf(accFtpValueMap.get(obj[11])==null?"0.0":accFtpValueMap.get(obj[11]).toString());
				Double ftp_value = Double.valueOf(ftplymap.get(obj[11])==null?"0.0":ftplymap.get(obj[11]).toString());
			    String hsql = "insert into app.FTP_RESULT(BR_NO,BR_NAME,AC_ID,PRODUCT_NAME,PRODUCT_NO,CUST_NO,CUS_NAME,OPN_DATE,MTR_DATE,is_zq,KHJL_NO_ONE,KHJL_NAME_ONE,RATIOS_ONE,KHJL_NO_TWO,KHJL_NAME_TWO,RATIOS_TWO,KHJL_NO_TREE,KHJL_NAME_TREE,RATIOS_TREE,BAL,RATE,FTP_PRICE,RJ_VALUE, RJJS_VALUE,LX,FTP_VALUE,MONTH) values('"+obj[0]+"','"+brNameMap.get(obj[0])+"','"+obj[11]+"','"+obj[2]+"','"+obj[10]+"','"+obj[1]+"','"+obj[3]+"','"+obj[4]+"','"+obj[5]+"','"+obj[8]+"','"+khjl_no_one+"','"+khjl_name_one+"','"+ratios_one+"','"+khjl_no_two+"','"+khjl_name_two+"','"+ratios_two+"','"+khjl_no_tree+"','"+khjl_name_tree+"','"+ratios_tree+"',"+bal+","+lx+","+ftp_price+","+rj_value+","+rjjs_value+","+lc_value+","+ftp_value+",'201301')";
			    if(rjjs_value!=0){
			    hsqllist.add(hsql);
			    jishu++;
			  }
			}
		}
		  System.out.println(jishu);
		  ftpList.clear(); System.gc();//�ͷ�dcfhqList��ռ���ڴ�
	     boolean f = daoFactory.execute1_s(hsqllist);
       if(f){
                System.out.println("����ɹ�");
                
			   }
		   else{
			   System.out.println("����ʧ��");
			   }
		
	}
}
