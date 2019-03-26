package app.riskapp.test;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import app.riskapp.common.CommonFunctions;
import app.riskapp.dbimport.DataImortLogic;

public class DataUpdateTest {

	
	 private static final Logger logger=Logger.getLogger(DataImortLogic.class);
	
	/**
	 * @param args
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws IOException, InterruptedException {

		 doExcuteUpdate();
		//doExcutetables();
		//doExcutefilelist("20130911");
		//doCopyfilelist("20130911");
	}
	
	
	
	/*
	 * �������ݿ����ݸ���ִ���ļ�
	 * 
	 * */
	public static void doExcuteUpdate() throws IOException {

//		Map map = new HashMap();
		String db2load = "\t\ndb2 connect to bips user db2inst1 using db2admin	";
//		String sql = "select  tabname from syscat.tables where tabschema = 'BIPS'";
//		
//		logger.info("��ѯ�������б���......");
//		List tablelist = CommonFunctions.mydao.query1(sql);
//		for (int i = 0; i < tablelist.size(); i++) {
//			map.put(i, tablelist.get(i).toString());
//		}
        Map map = new HashMap();
        map.put("SOP_MIR_BPDIA","SOP_MIR_BPDIA");
        map.put("SOP_MIR_BPSVA", "SOP_MIR_BPSVA");
        map.put("SOP_MIR_CDDSA", "SOP_MIR_CDDSA");
        map.put("SOP_MIR_CLTJB", "SOP_MIR_CLTJB");
        map.put("SOP_MIR_XFDNA", "SOP_MIR_XFDNA");
        map.put("SOP_MIR_WADIA", "SOP_MIR_WADIA");
        map.put("SOP_MIR_PSDSA", "SOP_MIR_PSDSA");
        map.put("SOP_MIR_OAVCA", "SOP_MIR_OAVCA");
        map.put("SOP_MIR_LDDOA", "SOP_MIR_LDDOA");
        map.put("SOP_MIR_LDDLA", "SOP_MIR_LDDLA");
        map.put("CMS_MIR_TELLERCUSTOMER", "CMS_MIR_TELLERCUSTOMER");
    
		db2load = "\t\ndb2 connect to bips user db2inst1 using db2admin";
		String filename = "D:\\zdata\\";
		File file = new File(filename);
		if (file.isDirectory()) {
			String[] filelist = file.list();
			
			logger.info("��ʼ����ִ���ļ�......");
			for (int j = 0; j < filelist.length; j++) {
				String name = filelist[j].toString();
				
		if(name.endsWith(".del")||name.endsWith(".txt")){		
				String[] newname = name.split("\\.");
				System.out.println(newname[1]);
			
			   String	newname2  = newname[1].substring(0,newname[1].length()-2).replace("TMP", "MIR");
		       String hsql = "select   *   from   SYSCAT.KEYCOLUSE   where   TABSCHEMA='BIPS' and TABNAME='"+newname2+"'";   
		       List tablelist = CommonFunctions.mydao.query1(hsql);
              if(tablelist!=null&&tablelist.size()!=0){	       
		       db2load += "\t\ndb2 IMPORT FROM /home/db2inst1/data_20130906/"
					+ name
					+ " OF DEL MODIFIED BY codepage=1386 COMMITCOUNT 50000  "
					+   "INSERT_UPDATE INTO BIPS."
					+ newname2;
			
              }
              }
			}
			logger.info("ִ���ļ��������......");
		}
		
		db2load += "\t\ndb2 disconnect current";
		
		logger.info("�����ִ���ļ�......");
		OutputStream os = new FileOutputStream("D:\\ftp_updateinsert_import.sh");
		byte[] by = db2load.getBytes();// �õ��ֽ����͵�����
		os.write(by);
		os.close();// �ֽ���û��ʹ�û�����,
		logger.info("�����ִ���ļ����......");
		
		
		
		
		
		
		db2load = "\t\ndb2 connect to bips user db2inst1 using db2admin";
	     filename = "D:\\zdata\\";
		 file = new File(filename);
		if (file.isDirectory()) {
			String[] filelist = file.list();
			
			logger.info("��ʼ����ִ���ļ�......");
			for (int j = 0; j < filelist.length; j++) {
				String name = filelist[j].toString();
				if(name.endsWith(".del")||name.endsWith(".txt")){			
				String[] newname1 = name.split("\\.");
				String newname = newname1[1];
				System.out.println(newname);
			
				String newname2  = newname.substring(0,newname.length()-2).replace("TMP", "MIR");
				
				
		       String hsql = "select   *   from   SYSCAT.KEYCOLUSE   where   TABSCHEMA='BIPS' and TABNAME='"+newname2+"'";   
		       List tablelist = CommonFunctions.mydao.query1(hsql);
              if(tablelist==null||tablelist.size()==0){	 
            	  
            	  
           if(!map.containsKey(newname2)) {	  
		       db2load += "\t\ndb2 IMPORT FROM /home/db2inst1/data_20130906/"
					+  name
					+ " OF DEL MODIFIED BY codepage=1386 COMMITCOUNT 50000  "
					+   "REPLACE INTO BIPS."
					+ newname2;
                  }
           else{
         	  db2load += "\t\ndb2 IMPORT FROM /home/db2inst1/data_20130906/"
					+  name
					+ " OF DEL MODIFIED BY codepage=1386 COMMITCOUNT 50000  "
					+   "INSERT INTO BIPS."
					+ newname2;
         	  
           }
              } 
             
			  }
			}
			logger.info("ִ���ļ��������......");
		}
		
		db2load += "\t\ndb2 disconnect current";
		
		logger.info("�����ִ���ļ�......");
		OutputStream os1 = new FileOutputStream("D:\\ftp_replace_import.sh");
		byte[] by1 = db2load.getBytes();// �õ��ֽ����͵�����
		os1.write(by1);
		os1.close();// �ֽ���û��ʹ�û�����,
		logger.info("�����ִ���ļ����......");
//		db2load = "\t\ndb2 connect to bips user db2inst1 using db2admin";
//		String filename1 = "/home/db2inst1/updatedata/txt/";
//		File file1 = new File(filename1);
//		if (file1.isDirectory()) {
//			
//			
//			logger.info("��ʼ����ִ���ļ�......");
//			String[] filelist = file1.list();
//			for (int j = 0; j < filelist.length; j++) {
//				String name = filelist[j].toString();
//				String[] newname1 = name.split("\\.");
//				String newname2 = newname1[1];
//				String newname = newname2.substring(0,newname2.length()-2);
//				System.out.println(newname);
//				if (!map.containsValue(newname.toUpperCase())) {
//					db2load += "\t\ndb2 IMPORT FROM /home/db2inst1/updatedata/txt/"
//							+ name
//							+ " OF IXF MODIFIED BY FORCECREATE COMMITCOUNT 50000 MESSAGES \"/home/db2inst1/logs/"
//							+ newname
//							+ ".log\"  REPLACE_CREATE INTO BIPS."
//							+ newname;
//				}
//			}
//			
//			logger.info("ִ���ļ��������......");
//		}
//		
//		db2load += "\t\ndb2 disconnect current";
//		
//		logger.info("�����ִ���ļ�......");
//		OutputStream os1 = new FileOutputStream("/home/db2inst1/ftp_import_sh/ftp_force_import.sh");
//		byte[] by1 = db2load.getBytes();// �õ��ֽ����͵�����
//		os1.write(by1);
//		os1.close();// �ֽ���û��ʹ�û�����,
//		logger.info("�����ִ���ļ����......");
	}

	
	
	
	
	
	

	/*
	 * �������ݿ����ݸ���ִ���ļ�
	 * 
	 * */
	public static void doExcuteUpdate1() throws IOException {

		Map map = new HashMap();
		String db2load = "\t\ndb2 connect to bips user db2inst1 using db2admin	";
		String sql = "select  tabname from syscat.tables where tabschema = 'BIPS'";
		
		logger.info("��ѯ�������б���......");
		List tablelist = CommonFunctions.mydao.query1(sql);
		for (int i = 0; i < tablelist.size(); i++) {
			map.put(i, tablelist.get(i).toString());
		}

		db2load = "\t\ndb2 connect to bips user db2inst1 using db2admin";
		String filename = "D:\\data_20130906\\";
		File file = new File(filename);
		if (file.isDirectory()) {
			String[] filelist = file.list();
			
			logger.info("��ʼ����ִ���ļ�......");
			for (int j = 0; j < filelist.length; j++) {
				String name = filelist[j].toString();
				if(name.endsWith(".del")){
				String newname = name.substring(4,name.length()-6);
				System.out.println(newname);
				
				if (map.containsValue(newname.toUpperCase())) {
					db2load += "\t\ndb2 IMPORT FROM /home/db2inst1/data_20130905/"
							+ name
							+ " OF DEL COMMITCOUNT 50000 MESSAGES \"/home/db2inst1/logs/"
							+ newname
							+ ".log\"  INSERT_UPDATE INTO BIPS."
							+ newname;
				}
				}
				
				else if(name.endsWith(".txt")){
					
					String newname = name.substring(4,name.length()-6);
					System.out.println(newname);
					
					if (map.containsValue(newname.toUpperCase())) {
						db2load += "\t\ndb2 IMPORT FROM /home/db2inst1/data_20130905/"
								+ name
								+ " OF DEL COMMITCOUNT 50000 MESSAGES \"/home/db2inst1/logs/"
								+ newname
								+ ".log\"  INSERT_UPDATE INTO BIPS."
								+ newname;
					}
				}
				
				else if(name.endsWith(".ixf")){
					
					String newname = name.substring(4,name.length()-6);
					System.out.println(newname);
					
					if (map.containsValue(newname.toUpperCase())) {
						db2load += "\t\ndb2 IMPORT FROM /home/db2inst1/data_20130905/"
								+ name
								+ " OF IXF COMMITCOUNT 50000 MESSAGES \"/home/db2inst1/logs/"
								+ newname
								+ ".log\"  INSERT_UPDATE INTO BIPS."
								+ newname;
					}
					
					
				}
				
			}
			logger.info("ִ���ļ��������......");
		}
		
		db2load += "\t\ndb2 disconnect current";
		
		logger.info("�����ִ���ļ�......");
		OutputStream os = new FileOutputStream("D:\\ftp_insert_update_import.sh");
		byte[] by = db2load.getBytes();// �õ��ֽ����͵�����
		os.write(by);
		os.close();// �ֽ���û��ʹ�û�����,
		logger.info("�����ִ���ļ����......");
		
		
		db2load = "\t\ndb2 connect to bips user db2inst1 using db2admin";
		String filename1 = "D:\\data_20130906\\";
		File file1 = new File(filename1);
		if (file1.isDirectory()) {
			String[] filelist = file1.list();
			for (int j = 0; j < filelist.length; j++) {
				String name = filelist[j].toString();
				
				if(name.endsWith(".txt")){
				
				String newname = name.substring(4,name.length()-6);
				System.out.println(newname);
				if (!map.containsValue(newname.toUpperCase())) {
					db2load += "\t\ndb2 IMPORT FROM /home/db2inst1/data_20130905/"
							+ name
							+ " OF DEL MODIFIED BY FORCECREATE COMMITCOUNT 50000 MESSAGES \"/home/db2inst1/logs/"
							+ newname
							+ ".log\"  REPLACE_CREATE INTO BIPS."
							+ newname;
			    	}
				}
				
				else if(name.endsWith(".ixf")){
					
					String newname = name.substring(4,name.length()-6);
					System.out.println(newname);
					if (!map.containsValue(newname.toUpperCase())) {
						db2load += "\t\ndb2 IMPORT FROM /home/db2inst1/data_20130905/"
								+ name
								+ " OF IXF MODIFIED BY FORCECREATE COMMITCOUNT 50000 MESSAGES \"/home/db2inst1/logs/"
								+ newname
								+ ".log\"  REPLACE_CREATE INTO BIPS."
								+ newname;
				    	}
					
					
				}
				
				else if(name.endsWith(".del")){
					
					String newname = name.substring(4,name.length()-6);
					System.out.println(newname);
					if (!map.containsValue(newname.toUpperCase())) {
						db2load += "\t\ndb2 IMPORT FROM /home/db2inst1/data_20130905/"
							+ name
							+ " OF DEL MODIFIED BY FORCECREATE COMMITCOUNT 50000 MESSAGES \"/home/db2inst1/logs/"
							+ newname
							+ ".log\"  REPLACE_CREATE INTO BIPS."
							+ newname;
					}
					
					
				}
			}
		}
		
		db2load += "\t\ndb2 disconnect current";
		OutputStream os1 = new FileOutputStream("D:\\ftp_forcecreate_import.sh");
		byte[] by1 = db2load.getBytes();// �õ��ֽ����͵�����
		os1.write(by1);
		os1.close();// �ֽ���û��ʹ�û�����,
	}
	
	/*
	 * �������ݿ����ݸ���ִ���ļ�
	 * 
	 * */
	public static void doExcutetables() throws IOException {
		
		Map map = new HashMap();
		String db2load = "";
		String sql = "select  tabname from syscat.tables where tabschema = 'BIPS'";
		
		logger.info("��ѯ�������б���......");
		List tablelist = CommonFunctions.mydao.query1(sql);
		
		String  filename = "D:\\zdata\\";
		File file = new File(filename);
		if(file.isDirectory()){
			String[] namestr = file.list();
		for (int i = 0; i < namestr.length; i++) {
			
			String[] newname1 = namestr[i].split("\\.");
			String newname2 = newname1[1];
			String newname = newname2.substring(0,newname2.length()-2);
			map.put(newname,newname);
			
			
	     	}
		}
		
		
		db2load = "�Ѵ��ڵı�";
		filename = "C:\\Users\\Administrator\\Desktop\\bengftp\\data\\data 20130904\\";
		
		file = new File(filename);
		if (file.isDirectory()) {
			String[] filelist = file.list();
			
			logger.info("��ʼ����ִ���ļ�......");
			for (int j = 0; j < filelist.length; j++) {
				String name = filelist[j].toString();
				
		//	if(name.endsWith(".ixf")){	
				String[] newname1 = name.split("\\.");
				String newname2 = newname1[1];
				String newname = name;
				System.out.println(newname);
				
				if (map.containsValue(newname)) {
					db2load += "\t\n"
						+ newname;
					
				  }
		    //	}	
			}
			logger.info("ִ���ļ��������......");
		}
		
	
		
		logger.info("�����ִ���ļ�......");
		OutputStream os = new FileOutputStream("D:\\exist.txt");
		byte[] by = db2load.getBytes();// �õ��ֽ����͵�����
		os.write(by);
		os.close();// �ֽ���û��ʹ�û�����,
		logger.info("�����ִ���ļ����......");
		
		
		db2load = "�����ı�";
		String filename1 =  "C:\\Users\\Administrator\\Desktop\\bengftp\\data\\data 20130904\\";
		File file1 = new File(filename1);
		if (file1.isDirectory()) {
			String[] filelist = file1.list();
			for (int j = 0; j < filelist.length; j++) {
				String name = filelist[j].toString();
				
			//	if(name.endsWith(".ixf")){		
//				String[] newname1 = name.split("\\.");
//				String newname2 = newname1[1];
				String newname = name;
				System.out.println(newname);
				if (!map.containsValue(newname)) {
					db2load += "\t\n"
						+ newname;
					
			    	}
		    //	}	
			}
		}

		OutputStream os1 = new FileOutputStream("D:\\notexist.txt");
		byte[] by1 = db2load.getBytes();// �õ��ֽ����͵�����
		os1.write(by1);
		os1.close();// �ֽ���û��ʹ�û�����,
	}

	
	
	
	
	/*
	 * 
	 * ��ѹĿ���ļ�
	 * 
	 * */
	
	public static void doExcutefilelist(String sysdate) throws IOException, InterruptedException {
		
		String newSysDate = sysdate;
		String filename = "D:\\ERDDDATA\\340311\\";
		
		File file = new File(filename);
		if (file.exists()&&file.isDirectory()) {
			
			
			logger.info("��ȡ��һ�ν�ѹ����Ŀ¼......");
			String[] filelist = file.list();
			for (int j = 0; j < filelist.length; j++) {
				String typename = filelist[j].toString();
				File file1 = new File(filename + "\\" + typename+"\\");
				if (file1.exists() && file1.isDirectory()) {
					
					logger.info("��ȡ"+typename+"����Ŀ¼......");
					String[] filelist1 = file1.list();
					for (int k = 0; k < filelist1.length; k++) {
						String datename = filelist1[k].toString();
						if(datename.equals(newSysDate)){
						File file2 = new File(filename + "\\" + typename + "\\"+ datename+"\\");
						if (file2.exists() && file2.isDirectory()) {
							
							
							logger.info("��ȡ"+typename+datename+"Ŀ¼......");
							String[] filelist2 = file2.list();
							for (int i = 0; i < filelist2.length; i++) {
								File okfile = new File(filename + "\\"+ typename + "\\" + datename + "\\"+ datename + ".ok");
								if (okfile.exists()) {
									String wjname = filelist2[i].toString();
									if (wjname.endsWith("jar")) {
										
										
										logger.info("��ʼ��ѹ"+wjname+"��......");
										Process pro =Runtime.getRuntime().exec("cmd /c  WinRAR  x    " + filename + "\\"+ typename + "\\"+ datename + "\\"+ wjname+ "    D:\\zdata\\");
										BufferedReader br = new BufferedReader(new InputStreamReader(pro.getInputStream())); 
										String msg = null; 
										while((msg = br.readLine())!= null){ 
										System.out.println(msg); 
										}
										System.out.println(pro.waitFor( ));  
										logger.info("��ѹ"+wjname+"�����......");
									}
								}else{
									
									logger.info(typename+datename+"��û��ok�ļ���ϵͳ�ܾ���ѹ����");
								}
							}
						  }
					
						}
						else{
							
							logger.info(typename+datename+"����û���·�");
						}
					}
				}
			}

		}

	}
	
	/*
	 * 
	 * ���μ��ܽ�ѹ��ָ���ļ���
	 *  
	 * */
	public static void doCopyfilelist(String sysdate) throws IOException, InterruptedException {
		
	
		String newSysDate = sysdate;
		String filename = "D:\\zdata\\";
		File file = new File(filename);
		if (file.exists()&&file.isDirectory()) {
			
			logger.info("��ȡ���ν�ѹ����Ŀ¼......");
			String[] filelist = file.list();
			for (int j = 0; j < filelist.length; j++) {
				String typename = filelist[j].toString();
				
				     	String wjname = typename;
				 		if (wjname.endsWith("z")) {
				 			
				 			
				 			logger.info("��ʼ��ѹ"+wjname+"��......");
						    Process pro =Runtime.getRuntime().exec("cmd /c WinRAR  x  -p140313  " + filename + "\\"+ wjname + "   D:\\zdata\\" );
							BufferedReader br = new BufferedReader(new InputStreamReader(pro.getInputStream())); 
					     	String msg = null; 
							while((msg = br.readLine())!= null){ 
					    	System.out.println(msg); 
																}
							System.out.println(pro.waitFor( ));  
							logger.info("��ѹ"+wjname+"�����......");
								
				}
			
			
		}
		
	}
}	
	
	
}
