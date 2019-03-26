package app.riskapp.rjlogic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.riskapp.common.CommonFunctions;
import app.riskapp.common.DaoFactory;
import app.riskapp.util.IDUtil;

public class Ftpempaccl {

	/**
	 * @param args
	 */
	public static Map<String,String> map1 = new HashMap<String,String>();
	public static DaoFactory daoFactory = new DaoFactory();
	public static void main(String[] args) {
		inset_newemp();
	}

	
	/*
	 * 
	 * 已关联的到虚拟客户经理的重新关联到真正的客户经理
	 */
	@SuppressWarnings("unchecked")
	public static void inset_newemp() {
		int count = 0;
		List sqllist = new ArrayList();
	/*	String sql = " select EMP_NO,CREDIT_NO as CREDIT_NO  from ("+  
					          " select EMP_NO,CREDIT_NO, CORE_NO"+ 
					          " from ("+
					          " select EMP_NO,CREDIT_NO,'' as CORE_NO  from ftp.FTP_EMP_INFO  where (CREDIT_NO is not null and CREDIT_NO != '')"+
		                                        " union"+ 
		                                        " select EMP_NO,'',CORE_NO  from ftp.FTP_EMP_INFO where  (CORE_NO is not null and CORE_NO != '')"+
		                                        " )where EMP_NO not in ("+
		                                        " select EMP_NO"+  
		                                        " from ("+
		                                        " select EMP_NO,CREDIT_NO,'' as CORE_NO  from ftp.FTP_EMP_INFO  where (CREDIT_NO is not null and CREDIT_NO != '')"+
		                                        " union"+ 
		                                        " select EMP_NO,'',CORE_NO  from ftp.FTP_EMP_INFO where  (CORE_NO is not null and CORE_NO != '')"+
		                                        " ) group by EMP_NO having count(*)>1"+
		                                        " )"+
		                                        " union"+ 
		                                        " select EMP_NO,CREDIT_NO,'' as CORE_NO  from ftp.FTP_EMP_INFO  where (CREDIT_NO is not null and CREDIT_NO != '')"+
					         " ) where  (CREDIT_NO is not null and CREDIT_NO != '')"+
				             " union"+
					         " select EMP_NO,CORE_NO as CORE_NO  from ("+  
				                                " select EMP_NO,CREDIT_NO, CORE_NO"+ 
				                                " from ("+
				                                " select EMP_NO,CREDIT_NO,'' as CORE_NO  from ftp.FTP_EMP_INFO  where (CREDIT_NO is not null and CREDIT_NO != '')"+
				                                " union"+ 
				                                " select EMP_NO,'',CORE_NO  from ftp.FTP_EMP_INFO where  (CORE_NO is not null and CORE_NO != '')"+
				                                " )  where EMP_NO not in ("+
				                                " select EMP_NO"+  
				                                " from ("+
				                                " select EMP_NO,CREDIT_NO,'' as CORE_NO  from ftp.FTP_EMP_INFO  where (CREDIT_NO is not null and CREDIT_NO != '')"+
				                                " union"+ 
				                                " select EMP_NO,'',CORE_NO  from ftp.FTP_EMP_INFO where  (CORE_NO is not null and CORE_NO != '')"+
				                                " ) group by EMP_NO having count(*)>1"+
				                                " )"+
				                                " union"+ 
				                                " select EMP_NO,CREDIT_NO,'' as CORE_NO  from ftp.FTP_EMP_INFO  where (CREDIT_NO is not null and CREDIT_NO != '')"+
					      " ) where (CORE_NO is not null and CORE_NO != '')";*/
		String sql = "select EMP_NO,CORE_NO from ftp.FTP_EMP_INFO where (CORE_NO is not null and CORE_NO !='')";
		List accList = daoFactory.query1(sql);
		for (int i = 0; i < accList.size(); i++) {
				Object[] obj = (Object[]) accList.get(i);
				String Emp_no = obj[0].toString();
				String core_no = obj[1].toString();
				String[] coreStr = core_no.split("@"); 
				for(String str:coreStr){
					if(!map1.containsKey(str)){
					     map1.put(core_no, Emp_no); 
					}else{
						if(!core_no.startsWith("K")){
						System.out.println(core_no);
						}
					}
				}
		}
		
		
		 sql = "select EMP_NO,CREDIT_NO from ftp.FTP_EMP_INFO where (CREDIT_NO is not null and CREDIT_NO !='')";
	     accList = daoFactory.query1(sql);
		for (int i = 0; i < accList.size(); i++) {
			Object[] obj = (Object[]) accList.get(i);
			String Emp_no = obj[0].toString();
			String core_no = obj[1].toString();
			String[] coreStr = core_no.split("@"); 
			for(String str:coreStr){
				if(!map1.containsKey(str)){
					map1.put(core_no, Emp_no); 
				}else{
					if(!core_no.startsWith("K")){
						System.out.println(core_no);
					}
				}
			}
		}
		
		sql = "select AC_ID,EMP_NO,RATE from db2inst1.FTP_EMP_ACCL_N ";
		accList = daoFactory.query1(sql);
		for (int i = 0; i < accList.size(); i++) {
			Object[] obj = (Object[]) accList.get(i);
			String ac_id = obj[0].toString();
			String core_no = obj[1].toString();
			String rate = obj[2].toString();
			if(map1.containsKey(core_no)){
				 String insert_sql = "insert into db2inst1.FTP_EMP_CORE values('"+ac_id.trim()+"','"+map1.get(core_no)+"','"+rate+"')";
				 sqllist.add(insert_sql);
			}else{
				System.out.println(core_no);
				if(core_no.startsWith("K")){
					 String insert_sql = "insert into db2inst1.FTP_EMP_CORE values('"+ac_id.trim()+"','"+core_no+"','"+rate+"')";
					 sqllist.add(insert_sql);
				}
			}
	}
		
				System.out.println(count);
				boolean f = daoFactory.execute1_s(sqllist);
				if (f) {
					System.out.println("批量导入成功");
				} else {
					System.out.println("批量导入失败");
				}
	}
}
