package app.riskapp.dbimport;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import app.riskapp.common.CommonFunctions;
import app.riskapp.common.DaoFactory;

/**
 * 
 * 
 * @author Administrator
 * 
 */
public class DataImortLogic {

	
	
  private static final Logger logger=Logger.getLogger(DataImortLogic.class);
	
	
	/**
	 * @param args
	 * @throws IOException
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws IOException, InterruptedException {
		
		 //doExcutefilelist("20130905");
		 //doCopyfilelist("20130905");
		 //doExcuteUpdate();
		 //doExcuteSh(); 
	     //doDelete();
	     doExcuteinnit();
	}

	
	/*
	 * 
	 * ���ݳ�ʼ����ִ��һ�Σ�
	 * 
	 * 
	 * */
public static void doExcuteinnit() throws IOException {
		
		String filename = "/home/db2inst1/yqbips_20160220/bb/";
		File file = new File(filename);
		if (file.exists() && file.isDirectory()) {
			String db2load = "";
			String[] filelist = file.list();
			for (int i = 0; i < filelist.length; i++) {
				String name = filelist[i].toString();
				db2load += "\t\ndb2 IMPORT FROM /home/db2inst1/yqbips_20160220/bb/"
						+ name
						+ " OF DEL MODIFIED BY  codepage=1386  COMMITCOUNT 50000 "
						+ "  REPLACE INTO ODS."
						+ name.substring(0, name.length() - 6);
			    }
			OutputStream os = new FileOutputStream("/home/db2inst1/ftp_insert_import.sh");
			byte[] b = db2load.getBytes();// �õ��ֽ����͵�����
			os.write(b);
			os.close();// �ֽ���û��ʹ�û�����,
		}
	}
	
	
	/**
	 * 
	 * ��ѹĿ�������ļ�ѹ����(jar)��ͬһ��ָ��Ŀ¼(/home/db2inst1/ERDDDATA/updatedata/z/)�£�
	 * <br>��jar�� ��ѹ�ɡ�.z����ʽ��ѹ���ļ���
	 * 
	 * */
	
	public static void doExcutefilelist(String sysdate) throws IOException, InterruptedException {
		
		String newSysDate = sysdate;
		String filename = "/home/db2inst1/ERDDDATA/340311/";
		File file = new File(filename);
		if (file.exists()&&file.isDirectory()) {
			
			System.out.println("��ȡ��һ�ν�ѹ����Ŀ¼......");
			String[] filelist = file.list();
			for (int j = 0; j < filelist.length; j++) {
				String typename = filelist[j].toString();
				File file1 = new File(filename + "/" + typename+"/");
				if (file1.exists() && file1.isDirectory()) {
					
					System.out.println("��ȡ"+typename+"����Ŀ¼......");
					String[] filelist1 = file1.list();
					for (int k = 0; k < filelist1.length; k++) {
						String datename = filelist1[k].toString();
						if(datename.equals(newSysDate)){
							File file2 = new File(filename + "/" + typename + "/"+ datename+"/");
							if (file2.exists() && file2.isDirectory()) {
								
								System.out.println("��ȡ"+typename+datename+"Ŀ¼......");
								String[] filelist2 = file2.list();
								for (int i = 0; i < filelist2.length; i++) {
									File okfile = new File(filename + "/"+ typename + "/" + datename + "/"+ datename + ".ok");
									if (okfile.exists()) {
										String wjname = filelist2[i].toString();
										if (wjname.endsWith("jar")) {
											System.out.println("��ʼ��ѹ"+wjname+"��......");
											Process pro =Runtime.getRuntime().exec("unzip -o  " + filename + "/"+ typename + "/"+ datename + "/"+ wjname+ "   -d  /home/db2inst1/ERDDDATA/updatedata/z/");
											BufferedReader br = new BufferedReader(new InputStreamReader(pro.getInputStream())); 
											String msg = null; 
											while((msg = br.readLine())!= null){ 
											System.out.println(msg); 
											System.out.println(msg);
											}
											if (pro.waitFor() != 0) {
												if (pro.exitValue() == 1)// p.exitValue()==0��ʾ����������1������������
												System.err.println("����ִ��ʧ��!");
												System.out.println("��ѹ"+wjname+"��ʧ��");
												}  
											System.out.println("��ѹ"+wjname+"�����");
										}
									}else{
										
										System.out.println(typename+datename+"��û��ok�ļ���ϵͳ�ܾ���ѹ����");
									}
								}
							}
						}
					}
				}
			}

		}

	}
	
	/**
	 * 
	 * ���ν��ܽ�ѹ����txt�����ļ���ͬһ��ָ���ļ���(/home/db2inst1/ERDDDATA/updatedata/txt/)��
	 * <br>�ɡ�.z������ѹ��txt�����ļ�
	 *  
	 * */
	public static void doCopyfilelist(String sysdate) throws IOException, InterruptedException {
		
		String newSysDate = sysdate;
		String filename = "/home/db2inst1/ERDDDATA/updatedata/z/";
		File file = new File(filename);
		if (file.exists()&&file.isDirectory()) {
			
			System.out.println("��ȡ���ν�ѹ����Ŀ¼......");
			String[] filelist = file.list();
			for (int j = 0; j < filelist.length; j++) {
				String typename = filelist[j].toString();
				     	String wjname = typename;
				 		if (wjname.endsWith(newSysDate+".z")) {
				 			System.out.println("��ʼ��ѹ"+wjname+"��......");
						    Process pro =Runtime.getRuntime().exec("unzip -o -P140313   " + filename + "/"+ wjname + "   -d /home/db2inst1/ERDDDATA/updatedata/txt/" );
							BufferedReader br = new BufferedReader(new InputStreamReader(pro.getInputStream())); 
					     	String msg = null; 
							while((msg = br.readLine())!= null){ 
							    	System.out.println(msg); 
							    	System.out.println(msg);
								}
							if (pro.waitFor() != 0) {
								if (pro.exitValue() == 1)// p.exitValue()==0��ʾ����������1������������
								System.err.println("����ִ��ʧ��!");
								System.out.println("��ѹ"+wjname+"��ʧ��");
								}  
							System.out.println("��ѹ"+wjname+"�����");
			}
		}
	}
}	
	
	
	
	/**
	 * ���� ���ݿ� ����Դ����ÿ���������롯��2��sh�ű��ļ�;
	 * <br>(INSERT_UPDATE INTO�ķ�һ����ftp_updateinsert_import.sh���insert into��replace into����һ����ftp_replace_import.sh����);
	 * <br><br>��������ֱ�� INSERT_UPDATE INTO �����������Ƿ���map�map���insert into������map���replace into 
	 * ��map����д���ģ�û�������ı� �� ֱ��insert into�����б�map��
	 * 
	 * */
	public static void doExcuteUpdate() throws IOException {
		
		//FTPϵͳ��Ҫ�ı�
		Map<String,String> ftp_map = new HashMap<String, String>();
		ftp_map.put("CMS_MIR_LISTLOANBALANCE", "CMS_MIR_LISTLOANBALANCE");
		
		ftp_map.put("BIL_MIR_BRCHINFO", "BIL_MIR_BRCHINFO");
		ftp_map.put("BIL_MIR_CUSTINFO", "BIL_MIR_CUSTINFO");
		ftp_map.put("BIL_MIR_DISCBILLINFO", "BIL_MIR_DISCBILLINFO");
		ftp_map.put("BIL_MIR_REBUYBILLINFO", "BIL_MIR_REBUYBILLINFO");
		ftp_map.put("BIL_MIR_SALEAPPLYINFO", "BIL_MIR_SALEAPPLYINFO");
		ftp_map.put("BIL_MIR_SALEBILLINFO", "BIL_MIR_SALEBILLINFO");
		ftp_map.put("CMS_MIR_CUSINFOUNITGENERAL", "CMS_MIR_CUSINFOUNITGENERAL");
		ftp_map.put("CMS_MIR_CUSTOMERPERSONALINFO", "CMS_MIR_CUSTOMERPERSONALINFO");
		ftp_map.put("CMS_MIR_ENTERPRISECUSTOMER", "CMS_MIR_ENTERPRISECUSTOMER");
		ftp_map.put("CMS_MIR_LISTFORCASH", "CMS_MIR_LISTFORCASH");
		ftp_map.put("CMS_MIR_LISTLOANEXP", "CMS_MIR_LISTLOANEXP");
		ftp_map.put("CMS_MIR_LISTMONEYORDER", "CMS_MIR_LISTMONEYORDER");
		ftp_map.put("CMS_MIR_TELLERCUSTOMER", "CMS_MIR_TELLERCUSTOMER");
		ftp_map.put("CMS_MIR_TELLERINFO", "CMS_MIR_TELLERINFO");
	
		//�ͻ���ϵ����ϵͳ
		ftp_map.put("ECIF_MIR_T00ORGCUSTNOREF", "ECIF_MIR_T00ORGCUSTNOREF");
    	ftp_map.put("ECIF_MIR_T00PERCERTNOREF", "ECIF_MIR_T00PERCERTNOREF");
		ftp_map.put("ECIF_MIR_T00PERCUSTNOREF", "ECIF_MIR_T00PERCUSTNOREF");
		ftp_map.put("ECIF_MIR_T01EMPLOYEEINFO", "ECIF_MIR_T01EMPLOYEEINFO");
		ftp_map.put("ECIF_MIR_T01ORGBANKEXTINFO", "ECIF_MIR_T01ORGBANKEXTINFO");
		ftp_map.put("ECIF_MIR_T01ORGCUSTDISINFO", "ECIF_MIR_T01ORGCUSTDISINFO");
		ftp_map.put("ECIF_MIR_T01ORGCUSTEXTINFO", "ECIF_MIR_T01ORGCUSTEXTINFO");
		ftp_map.put("ECIF_MIR_T01ORGCUSTINFO", "ECIF_MIR_T01ORGCUSTINFO");
		ftp_map.put("ECIF_MIR_T01ORGCUSTSIMILIST", "ECIF_MIR_T01ORGCUSTSIMILIST");
		ftp_map.put("ECIF_MIR_T01ORGGROUPINFO", "ECIF_MIR_T01ORGGROUPINFO");
		ftp_map.put("ECIF_MIR_T01ORGGROUPMEMBERS", "ECIF_MIR_T01ORGGROUPMEMBERS");
		ftp_map.put("ECIF_MIR_T01ORGTAXINFO", "ECIF_MIR_T01ORGTAXINFO");
		ftp_map.put("ECIF_MIR_T01PERCUSTDISINFO", "ECIF_MIR_T01PERCUSTDISINFO");
		ftp_map.put("ECIF_MIR_T01PERCUSTEXTINFO", "ECIF_MIR_T01PERCUSTEXTINFO");
		ftp_map.put("ECIF_MIR_T01PERCUSTINFO", "ECIF_MIR_T01PERCUSTINFO");
		ftp_map.put("ECIF_MIR_T01PERCUSTSIMILIST", "ECIF_MIR_T01PERCUSTSIMILIST");
		ftp_map.put("ECIF_MIR_T01PERTAXINFO", "ECIF_MIR_T01PERTAXINFO");
		ftp_map.put("ECIF_MIR_T01RELCORPINFO", "ECIF_MIR_T01RELCORPINFO");
		ftp_map.put("ECIF_MIR_T01RELPEREXTINFO", "ECIF_MIR_T01RELPEREXTINFO");
		ftp_map.put("ECIF_MIR_T01RELPERINFO", "ECIF_MIR_T01RELPERINFO");
		ftp_map.put("ECIF_MIR_T02ORGCHANNEREL", "ECIF_MIR_T02ORGCHANNEREL");
		ftp_map.put("ECIF_MIR_T02ORGCUSTCUSTREL", "ECIF_MIR_T02ORGCUSTCUSTREL");
		ftp_map.put("ECIF_MIR_T02ORGCUSTCUSTRELINVST", "ECIF_MIR_T02ORGCUSTCUSTRELINVST");
		ftp_map.put("ECIF_MIR_T02ORGCUSTCUSTRELMANFAM", "ECIF_MIR_T02ORGCUSTCUSTRELMANFAM");
		ftp_map.put("ECIF_MIR_T02ORGCUSTEVENTREL", "ECIF_MIR_T02ORGCUSTEVENTREL");
		ftp_map.put("ECIF_MIR_T02ORGCUSTRISKREL", "ECIF_MIR_T02ORGCUSTRISKREL");
		ftp_map.put("ECIF_MIR_T02ORGCUSTSTATIREL", "ECIF_MIR_T02ORGCUSTSTATIREL");
		ftp_map.put("ECIF_MIR_T02PERCHANNEREL", "ECIF_MIR_T02PERCHANNEREL");
		ftp_map.put("ECIF_MIR_T02PERCUSTCUSTREL", "ECIF_MIR_T02PERCUSTCUSTREL");
		ftp_map.put("ECIF_MIR_T02PERCUSTEVENTREL", "ECIF_MIR_T02PERCUSTEVENTREL");
		ftp_map.put("ECIF_MIR_T02PERCUSTRISKREL", "ECIF_MIR_T02PERCUSTRISKREL");
		ftp_map.put("ECIF_MIR_T02PERCUSTSTATIREL", "ECIF_MIR_T02PERCUSTSTATIREL");
		ftp_map.put("ECIF_MIR_T03ORGELECADDRESS", "ECIF_MIR_T03ORGELECADDRESS");
		ftp_map.put("ECIF_MIR_T03ORGPHONEINFO", "ECIF_MIR_T03ORGPHONEINFO");
		ftp_map.put("ECIF_MIR_T03ORGPHYSICADDRESS", "ECIF_MIR_T03ORGPHYSICADDRESS");
		ftp_map.put("ECIF_MIR_T03PERELECADDRESS", "ECIF_MIR_T03PERELECADDRESS");
		ftp_map.put("ECIF_MIR_T03PERPHONEINFO", "ECIF_MIR_T03PERPHONEINFO");
		ftp_map.put("ECIF_MIR_T03PERPHYSICADDRESS", "ECIF_MIR_T03PERPHYSICADDRESS");
		ftp_map.put("ECIF_MIR_T05ORGCUSTOMERSIGN", "ECIF_MIR_T05ORGCUSTOMERSIGN");
		ftp_map.put("ECIF_MIR_T05PERCUSTOMERSIGN", "ECIF_MIR_T05PERCUSTOMERSIGN");
		ftp_map.put("ECIF_MIR_T06ORGEVENT", "ECIF_MIR_T06ORGEVENT");
		ftp_map.put("ECIF_MIR_T06PEREVENT", "ECIF_MIR_T06PEREVENT");
		ftp_map.put("ECIF_MIR_T08NAMELISTINFO", "ECIF_MIR_T08NAMELISTINFO");
		ftp_map.put("ECIF_MIR_T09PERCUSTFININFO", "ECIF_MIR_T09PERCUSTFININFO");
		ftp_map.put("ECIF_MIR_T09ORGCUSTFININFO", "ECIF_MIR_T09ORGCUSTFININFO");
		ftp_map.put("ECIF_MIR_T00ORGCERTNOREF", "ECIF_MIR_T00ORGCERTNOREF");
		
		
		//�º���
		ftp_map.put("NSOP_MIR_AEFD01", "NSOP_MIR_AEFD01");
		ftp_map.put("NSOP_MIR_AEFD02", "NSOP_MIR_AEFD02");
		ftp_map.put("NSOP_MIR_AEFM21", "NSOP_MIR_AEFM21");
		ftp_map.put("NSOP_MIR_AMFE51", "NSOP_MIR_AMFE51");
		ftp_map.put("NSOP_MIR_AMFE52", "NSOP_MIR_AMFE52");
		ftp_map.put("NSOP_MIR_AMFE53", "NSOP_MIR_AMFE53");
		ftp_map.put("NSOP_MIR_AMFE54", "NSOP_MIR_AMFE54");
		ftp_map.put("NSOP_MIR_AMFM21", "NSOP_MIR_AMFM21");
		ftp_map.put("NSOP_MIR_AMFM22", "NSOP_MIR_AMFM22");
		ftp_map.put("NSOP_MIR_AMFM23", "NSOP_MIR_AMFM23");
		ftp_map.put("NSOP_MIR_AMFM24", "NSOP_MIR_AMFM24");
		ftp_map.put("NSOP_MIR_AMFM25", "NSOP_MIR_AMFM25");
		ftp_map.put("NSOP_MIR_AMFM26", "NSOP_MIR_AMFM26");
		ftp_map.put("NSOP_MIR_AMFM27", "NSOP_MIR_AMFM27");
		ftp_map.put("NSOP_MIR_AMFM28", "NSOP_MIR_AMFM28");
		ftp_map.put("NSOP_MIR_AMFM29", "NSOP_MIR_AMFM29");
		ftp_map.put("NSOP_MIR_AMFM31", "NSOP_MIR_AMFM31");
		ftp_map.put("NSOP_MIR_AMFM33", "NSOP_MIR_AMFM33");
		ftp_map.put("NSOP_MIR_AMFM34", "NSOP_MIR_AMFM34");
		ftp_map.put("NSOP_MIR_BIFD01", "NSOP_MIR_BIFD01");
		ftp_map.put("NSOP_MIR_BIFD05", "NSOP_MIR_BIFD05");
		ftp_map.put("NSOP_MIR_BIFD08", "NSOP_MIR_BIFD08");
		ftp_map.put("NSOP_MIR_BIFD21", "NSOP_MIR_BIFD21");
		ftp_map.put("NSOP_MIR_BIFD23", "NSOP_MIR_BIFD23");
		ftp_map.put("NSOP_MIR_BIFM31", "NSOP_MIR_BIFM31");
		ftp_map.put("NSOP_MIR_BIFM32", "NSOP_MIR_BIFM32");
		ftp_map.put("NSOP_MIR_BRFB11", "NSOP_MIR_BRFB11");
		ftp_map.put("NSOP_MIR_BRFD01", "NSOP_MIR_BRFD01");
		ftp_map.put("NSOP_MIR_BRFD02", "NSOP_MIR_BRFD02");
		ftp_map.put("NSOP_MIR_BRFD03", "NSOP_MIR_BRFD03");
		ftp_map.put("NSOP_MIR_BRFM13", "NSOP_MIR_BRFM13");
		ftp_map.put("NSOP_MIR_CDFD01", "NSOP_MIR_CDFD01");
		ftp_map.put("NSOP_MIR_CDFD05", "NSOP_MIR_CDFD05");
		ftp_map.put("NSOP_MIR_CDFD06", "NSOP_MIR_CDFD06");
		ftp_map.put("NSOP_MIR_CDFD07", "NSOP_MIR_CDFD07");
		ftp_map.put("NSOP_MIR_CDFE65", "NSOP_MIR_CDFE65");
		ftp_map.put("NSOP_MIR_CDFE67", "NSOP_MIR_CDFE67");
		ftp_map.put("NSOP_MIR_CDFE71", "NSOP_MIR_CDFE71");
		ftp_map.put("NSOP_MIR_CDFE72", "NSOP_MIR_CDFE72");
		ftp_map.put("NSOP_MIR_CDFM21", "NSOP_MIR_CDFM21");
		ftp_map.put("NSOP_MIR_CDFM23", "NSOP_MIR_CDFM23");
		ftp_map.put("NSOP_MIR_CDFT81", "NSOP_MIR_CDFT81");
		ftp_map.put("NSOP_MIR_CIFM11", "NSOP_MIR_CIFM11");
		ftp_map.put("NSOP_MIR_CIFM21", "NSOP_MIR_CIFM21");
		
		
		ftp_map.put("NSOP_MIR_DPFM21", "NSOP_MIR_DPFM21");
		ftp_map.put("NSOP_MIR_DPFM22", "NSOP_MIR_DPFM22");
		ftp_map.put("NSOP_MIR_DPFM24", "NSOP_MIR_DPFM24");
		ftp_map.put("NSOP_MIR_DPFM25", "NSOP_MIR_DPFM25");
		ftp_map.put("NSOP_MIR_DPFM26", "NSOP_MIR_DPFM26");
		ftp_map.put("NSOP_MIR_DPFM27", "NSOP_MIR_DPFM27");
		ftp_map.put("NSOP_MIR_DPFM28", "NSOP_MIR_DPFM28");
		ftp_map.put("NSOP_MIR_DPFM29", "NSOP_MIR_DPFM29");
		
		
		ftp_map.put("NSOP_MIR_GLFD01", "NSOP_MIR_GLFD01");
		ftp_map.put("NSOP_MIR_GLFM12", "NSOP_MIR_GLFM12");
		ftp_map.put("NSOP_MIR_GLFM51", "NSOP_MIR_GLFM51");
		ftp_map.put("NSOP_MIR_GLFM52", "NSOP_MIR_GLFM52");
		ftp_map.put("NSOP_MIR_GLFM53", "NSOP_MIR_GLFM53");
		ftp_map.put("NSOP_MIR_GLFM54", "NSOP_MIR_GLFM54");
		
		ftp_map.put("NSOP_MIR_ILFM30", "NSOP_MIR_ILFM30");
		ftp_map.put("NSOP_MIR_ILFM31", "NSOP_MIR_ILFM31");
		ftp_map.put("NSOP_MIR_ILFM32", "NSOP_MIR_ILFM32");
		ftp_map.put("NSOP_MIR_ILFM33", "NSOP_MIR_ILFM33");
		ftp_map.put("NSOP_MIR_ILFM34", "NSOP_MIR_ILFM34");
		ftp_map.put("NSOP_MIR_ILFM40", "NSOP_MIR_ILFM40");
		ftp_map.put("NSOP_MIR_ILFM60", "NSOP_MIR_ILFM60");
		ftp_map.put("NSOP_MIR_ILFM61", "NSOP_MIR_ILFM61");
		
		ftp_map.put("NSOP_MIR_INFD01", "NSOP_MIR_INFD01");
		ftp_map.put("NSOP_MIR_INFM41", "NSOP_MIR_INFM41");
		ftp_map.put("NSOP_MIR_INFM44", "NSOP_MIR_INFM44");
		ftp_map.put("NSOP_MIR_INFM51", "NSOP_MIR_INFM51");
		ftp_map.put("NSOP_MIR_INFM52", "NSOP_MIR_INFM52");
		ftp_map.put("NSOP_MIR_INFM53", "NSOP_MIR_INFM53");
		
		ftp_map.put("NSOP_MIR_VAFM11", "NSOP_MIR_VAFM11");
		ftp_map.put("NSOP_MIR_VAFM12", "NSOP_MIR_VAFM12");
		
		//������������
		ftp_map.put("NBS_MIR_MPCLOSECIFBOOK", "NBS_MIR_MPCLOSECIFBOOK");
		ftp_map.put("NBS_MIR_MPOPENCIFBOOK", "NBS_MIR_MPOPENCIFBOOK");
		//���Ŵ�ϵͳ���ӵı�
		ftp_map.put("AHXD_MIR_DXLOANBAL", "AHXD_MIR_DXLOANBAL");
		ftp_map.put("AHXD_MIR_DXLOANBOOK", "AHXD_MIR_DXLOANBOOK");
		ftp_map.put("AHXD_MIR_DYACSUBJOCCUR", "AHXD_MIR_DYACSUBJOCCUR");
		ftp_map.put("AHXD_MIR_DXDISCBAL", "AHXD_MIR_DXDISCBAL");
		ftp_map.put("AHXD_MIR_DXBILLBAL", "AHXD_MIR_DXBILLBAL");
		
		//�����·��ı�
		Map<String,String> map = new HashMap<String, String>();//û�������ı� �� ֱ��insert into�����б�map
		
		//�ͻ���ϵ����ϵͳ
		map.put("ECIF_MIR_T00ORGCERTNOREF", "ECIF_MIR_T00ORGCERTNOREF");
        map.put("ECIF_MIR_T00ORGCUSTNOREF", "ECIF_MIR_T00ORGCUSTNOREF");
        map.put("ECIF_MIR_T00PERCERTNOREF", "ECIF_MIR_T00PERCERTNOREF");
        map.put("ECIF_MIR_T00PERCUSTNOREF", "ECIF_MIR_T00PERCUSTNOREF");
        map.put("ECIF_MIR_T01EMPLOYEEINFO", "ECIF_MIR_T01EMPLOYEEINFO");
        map.put("ECIF_MIR_T01ORGCUSTEXTINFO", "ECIF_MIR_T01ORGCUSTEXTINFO");
        map.put("ECIF_MIR_T01ORGCUSTINFO", "ECIF_MIR_T01ORGCUSTINFO");
        map.put("ECIF_MIR_T01PERCUSTEXTINFO", "ECIF_MIR_T01PERCUSTEXTINFO");
        map.put("ECIF_MIR_T01PERCUSTINFO", "ECIF_MIR_T01PERCUSTINFO");
        map.put("ECIF_MIR_T01PERCUSTSIMILIST", "ECIF_MIR_T01PERCUSTSIMILIST");
        map.put("ECIF_MIR_T01RELPEREXTINFO", "ECIF_MIR_T01RELPEREXTINFO_1");
        map.put("ECIF_MIR_T02PERCHANNEREL", "ECIF_MIR_T01RELPEREXTINFO");
        map.put("ECIF_MIR_T02PERCUSTSTATIREL", "ECIF_MIR_T02PERCUSTSTATIREL");
        map.put("ECIF_MIR_T03ORGELECADDRESS", "ECIF_MIR_T03ORGELECADDRESS");
        map.put("ECIF_MIR_T03ORGPHONEINFO", "ECIF_MIR_T03ORGPHONEINFO");
        map.put("ECIF_MIR_T03ORGPHYSICADDRESS", "ECIF_MIR_T03ORGPHYSICADDRESS");
        map.put("ECIF_MIR_T03PERELECADDRESS", "ECIF_MIR_T03PERELECADDRESS");
        map.put("ECIF_MIR_T03PERPHONEINFO", "ECIF_MIR_T03PERPHONEINFO");
        map.put("ECIF_MIR_T03PERPHYSICADDRESS", "ECIF_MIR_T03PERPHYSICADDRESS");
        map.put("ECIF_MIR_T05ORGCUSTOMERSIGN", "ECIF_MIR_T05ORGCUSTOMERSIGN");
        map.put("ECIF_MIR_T05PERCUSTOMERSIGN", "ECIF_MIR_T05PERCUSTOMERSIGN");
        map.put("ECIF_MIR_T09PERCUSTFININFO", "ECIF_MIR_T09PERCUSTFININFO");
		//�º���
	    map.put("NSOP_TMP_BRFB11", "NSOP_TMP_BRFB11");
	    map.put("NSOP_TMP_BRFM13", "NSOP_TMP_BRFM13");
	    map.put("NSOP_MIR_GLFM54", "NSOP_MIR_GLFM54");
	    map.put("NSOP_MIR_ILFM31", "NSOP_MIR_ILFM31");
	    map.put("NSOP_MIR_ILFM34", "NSOP_MIR_ILFM34");
	    map.put("NSOP_MIR_INFT47", "NSOP_MIR_INFT47");
	    map.put("NSOP_MIR_VAFM12", "NSOP_MIR_VAFM12");
    	//������������
		map.put("NBS_MIR_MPCLOSECIFBOOK", "NBS_MIR_MPCLOSECIFBOOK");
		map.put("NBS_MIR_MPOPENCIFBOOK", "NBS_MIR_MPOPENCIFBOOK");

		String db2load = "\t\ndb2 connect to bips user db2inst1 using db2admin	";
		db2load = "\t\ndb2 connect to bips user db2inst1 using db2admin";
		String filename = "/home/db2inst1/ERDDDATA/updatedata/txt/";
		File file = new File(filename);
		if (file.isDirectory()) {
			String[] filelist = file.list();
			System.out.println("��ʼ����ִ���ļ�......");
			for (int j = 0; j < filelist.length; j++) {
				String name = filelist[j].toString();
				if (name.endsWith(".del") || name.endsWith(".txt")) {
					String[] newname1 = name.split("\\.");
					String newname2 = newname1[1];
					String newname = newname2.substring(0,newname2.length() - 2);
					String newname3 = newname.replace("TMP", "MIR").replace("_ALL", "");
					String hsql = "select   *   from   SYSCAT.KEYCOLUSE   where   TABSCHEMA='BIPS' and TABNAME='"
							+ newname3 + "'";
					List pklist = CommonFunctions.mydao.query1(hsql);
					if (pklist != null && pklist.size() != 0
							&& !newname3.equals("CMS_MIR_REPORTSTORAGE")&&ftp_map.containsKey(newname3)) {
						db2load += "\t\n echo ��ʼ"+newname3+"��ĵ���...";
						db2load += "\t\ndb2 IMPORT FROM /home/db2inst1/ERDDDATA/updatedata/txt/"
								+ name
								+ " OF DEL MODIFIED BY codepage=1386  COMMITCOUNT 50000 MESSAGES \"/home/db2inst1/logs/"
								+ newname3
								+ ".log\"  INSERT_UPDATE INTO bips."
								+ newname3;
					}
				}
			}
			System.out.println("ִ���ļ��������");
		}
		db2load += "\t\ndb2 disconnect current";
		System.out.println("�����ִ���ļ�......");
		OutputStream os = new FileOutputStream("/home/db2inst1/ERDDDATA/ftp_batch/dataimport/ftp_updateinsert_import.sh");
		byte[] by = db2load.getBytes();// �õ��ֽ����͵�����
		os.write(by);
		os.close();// �ֽ���û��ʹ�û�����,
		System.out.println("�����ִ���ļ����");
		
		db2load = "\t\ndb2 connect to bips user db2inst1 using db2admin";
		String filename1 = "/home/db2inst1/ERDDDATA/updatedata/txt/";
		File file1 = new File(filename1);
		if (file1.isDirectory()) {
			System.out.println("��ʼ����ִ���ļ�......");
			String[] filelist = file1.list();
			for (int j = 0; j < filelist.length; j++) {
				String name = filelist[j].toString();
				if (name.endsWith(".del") || name.endsWith(".txt")) {
					String[] newname1 = name.split("\\.");
					String newname2 = newname1[1];
					String newname = newname2.substring(0,newname2.length() - 2);
					String newname3 = newname.replace("TMP", "MIR").replace("_ALL", "");
					String hsql = "select   *   from   SYSCAT.KEYCOLUSE   where   TABSCHEMA='BIPS' and TABNAME='"
							+ newname3 + "'";
					List pklist = CommonFunctions.mydao.query1(hsql);
					if ((pklist == null || pklist.size() == 0)&&ftp_map.containsKey(newname3)) {
						db2load += "\t\n echo ��ʼ"+newname3+"��ĵ���...";
						if (!map.containsValue(newname3)) {
							db2load += "\t\ndb2 IMPORT FROM /home/db2inst1/ERDDDATA/updatedata/txt/"
									+ name
									+ " OF DEL MODIFIED BY codepage=1386 COMMITCOUNT 50000 MESSAGES \"/home/db2inst1/logs/"
									+ newname3
									+ ".log\"  REPLACE INTO bips."
									+ newname3;
						}
						else {
							db2load += "\t\ndb2 IMPORT FROM /home/db2inst1/ERDDDATA/updatedata/txt/"
									+ name
									+ " OF DEL MODIFIED BY codepage=1386 COMMITCOUNT 50000 MESSAGES \"/home/db2inst1/logs/"
									+ newname3
									+ ".log\"  INSERT INTO bips."
									+ newname3;
						}
					}
				}
			}
			System.out.println("ִ���ļ��������");
		}
		db2load += "\t\ndb2 disconnect current";
		System.out.println("�����ִ���ļ�......");
		OutputStream os1 = new FileOutputStream("/home/db2inst1/ERDDDATA/ftp_batch/dataimport/ftp_replace_import.sh");
		byte[] by1 = db2load.getBytes();// �õ��ֽ����͵�����
		os1.write(by1);
		os1.close();// �ֽ���û��ʹ�û�����,
		System.out.println("�����ִ���ļ����");
	}
	
	
	
	/**
	 * �Ѹ������ݲ����Ӧ�����ʱ���� ��ftp_insert_temp.sh�ű����� ---�ؼ����˻����ݵ���������
	 * 
	 * */
	public static void doExcuteInsertTemp() throws IOException {
		
		//��Ҫ����������Դ��
		Map<String,String> map = new HashMap<String, String>();
	    map.put("NSOP_MIR_DPFM21_TEMP", "NSOP_MIR_DPFM21_TEMP");
		String db2load = "\t\ndb2 connect to bips user db2inst1 using db2admin	";
		db2load = "\t\ndb2 connect to bips user db2inst1 using db2admin";
		String filename1 = "/home/db2inst1/ERDDDATA/updatedata/txt/";
		File file1 = new File(filename1);
		if (file1.isDirectory()) {
			System.out.println("��ʼ����ִ���ļ�......");
			String[] filelist = file1.list();
			for (int j = 0; j < filelist.length; j++) {
				String name = filelist[j].toString();
				if (name.endsWith(".del") || name.endsWith(".txt")) {
					String[] newname1 = name.split("\\.");
					String newname2 = newname1[1];
					String newname = newname2.substring(0,newname2.length() - 2);
					String newname3 = newname.replace("TMP", "MIR").replace("_ALL", "");
				    newname3 = newname3.replace("TMP", "MIR");
					newname3 = newname3+"_TEMP"; 
					System.out.println("�������ǣ�"+newname3);
					if (map.containsValue(newname3)) {
					db2load += "\t\ndb2 IMPORT FROM /home/db2inst1/ERDDDATA/updatedata/txt/"
						+ name
						+ " OF DEL MODIFIED BY codepage=1386 COMMITCOUNT 50000 MESSAGES \"/home/db2inst1/logs/"
						+ newname3
						+ ".log\"  REPLACE INTO bips."
						+ newname3;
					}
				}
			}
			System.out.println("ִ���ļ��������");
		}
		db2load += "\t\ndb2 disconnect current";
		System.out.println("�����ִ���ļ�......");
		OutputStream os1 = new FileOutputStream("/home/db2inst1/ERDDDATA/ftp_batch/dataimport/ftp_insert_temp.sh");
		byte[] by1 = db2load.getBytes();// �õ��ֽ����͵�����
		os1.write(by1);
		os1.close();// �ֽ���û��ʹ�û�����,
		System.out.println("�����ִ���ļ����");
	}

	

	
	/**
	 * 
	 * ִ��֮ǰ���ɵ�3��sh�ű�(��ʼ��һ���жϣ��ж϶�˽�������µ�ά�������Ƿ���ϵͳ�������ڣ�����ִ�е���ű���������ֹ)
	 * 
	 * */
	@SuppressWarnings("unchecked")
	public static boolean doExcuteSh(String sys_date) throws IOException, InterruptedException {
		String filename1 = "/home/db2inst1/ERDDDATA/ftp_batch/dataimport/ftp_insert_temp.sh";
		File file = new File(filename1);
	    	if(file.exists()){
			System.out.println("��ʼִ�з��˻�����TEMP��Ĳ������......");
			Process pro =Runtime.getRuntime().exec("sh /home/db2inst1/ERDDDATA/ftp_batch/dataimport/ftp_insert_temp.sh" );
	    	BufferedReader br = new BufferedReader(new InputStreamReader(pro.getInputStream())); 
			String msg = null; 
			while((msg = br.readLine())!= null){ 
				System.out.println(msg);
		    }
			System.out.println(pro.waitFor( ));  
			System.out.println("ִ�з��˻�����TEMP��Ĳ���������");
		}
	    	
		
	    //�ж϶�˽�������µ�ά�������Ƿ���ϵͳ��������	
	    String sql = "select trim(max(DATLAST)) from BIPS.NSOP_MIR_DPFM21_TEMP";	
	    DaoFactory df = new DaoFactory();
	    List<String> list = df.query1(sql);
	    if(list!=null&&list.size()>0){
	    	System.out.println("ϵͳ����ά�������ǣ�"+ String.valueOf(list.get(0)));
	        String repareDate = String.valueOf(list.get(0)).replaceAll("-","");
	        //#### �����·�ά�������쳣ʱ��ǿ�е���
/*	        System.out.println("ά������Ϊ"+repareDate+",��������Ϊ"+sys_date+"�����߲�����ǿ�е��룡");//#### �����·�ά�������쳣ʱ��ǿ�е���
	        repareDate=sys_date;//#### �����·�ά�������쳣ʱ��ǿ�е���
*/	        
		    if(sys_date.equals(repareDate))	{
		    System.out.println("�������ں�����ά��������ȣ�ִ�����ݵ���");
	     	filename1 = "/home/db2inst1/ERDDDATA/ftp_batch/dataimport/ftp_updateinsert_import.sh";
			file = new File(filename1);
			if(file.exists()){
				System.out.println("��ʼִ���������ı����͸��²���......");
				Process pro =Runtime.getRuntime().exec("sh /home/db2inst1/ERDDDATA/ftp_batch/dataimport/ftp_updateinsert_import.sh" );
				BufferedReader br = new BufferedReader(new InputStreamReader(pro.getInputStream())); 
				String msg = null; 
				while((msg = br.readLine())!= null){ 
				System.out.println(msg);
				}
				System.out.println(pro.waitFor( ));  
				System.out.println("ִ����������Ĳ���͸��²������");
			}
			 filename1 = "/home/db2inst1/ERDDDATA/ftp_batch/dataimport/ftp_replace_import.sh";
			 file = new File(filename1);
		    	if(file.exists()){
				System.out.println("��ʼִ���������ı�Ĳ���������滻(replace)����......");
				Process pro =Runtime.getRuntime().exec("sh /home/db2inst1/ERDDDATA/ftp_batch/dataimport/ftp_replace_import.sh" );
		    	BufferedReader br = new BufferedReader(new InputStreamReader(pro.getInputStream())); 
				String msg = null; 
				while((msg = br.readLine())!= null){ 
					System.out.println(msg);
			    }
				System.out.println(pro.waitFor( ));  
				System.out.println("ִ���������ı�Ĳ���������滻(replace)�������");
			}
		    return true;	
		    }else{
		    	  System.out.println("��������"+sys_date+"�ͷ��˻�����ά������"+repareDate+"����ȣ���ִ�����ݵ���");
		    	  return false;	
		    }
		}
	    else{
	    	  System.out.println("BIPS.NSOP_MIR_DPFM21_TEMP������δ���룬��ִ�����ݵ���");
	    	  return false;	
	    }
	}
	
	
	/**
	 * 
	 * ɾ����txt���͡�z��("/home/db2inst1/ERDDDATA/updatedata/txt/)�ļ����µ������ļ��������ļ��б���
	 * 
	 * */
	public static void doDelete() throws IOException, InterruptedException {
		
		String filename1 = "/home/db2inst1/ERDDDATA/updatedata/txt/";
		File file = new File(filename1);
		if(file.exists()){
			System.out.println("��ʼɾ��TXT�ļ���......");
			Process pro =Runtime.getRuntime().exec("rm  -rf  /home/db2inst1/ERDDDATA/updatedata/txt" );
			BufferedReader br = new BufferedReader(new InputStreamReader(pro.getInputStream())); 
			String msg = null; 
			while((msg = br.readLine())!= null){ 
				System.out.println(msg);
			}
			if (pro.waitFor() != 0) {
				if (pro.exitValue() == 1)// p.exitValue()==0��ʾ����������1������������
				System.err.println("����ִ��ʧ��!");
				System.out.println("ɾ��Z�ļ���ʧ��");
				}  
			System.out.println("ɾ��TXT�ļ������");
		}
		filename1 = "/home/db2inst1/ERDDDATA/updatedata/z/";
	    file = new File(filename1);
		if(file.exists()){
			System.out.println("��ʼɾ��Z�ļ���......");
			Process pro =Runtime.getRuntime().exec("rm  -rf  /home/db2inst1/ERDDDATA/updatedata/z" );
			BufferedReader br = new BufferedReader(new InputStreamReader(pro.getInputStream())); 
			String msg = null; 
			while((msg = br.readLine())!= null){ 
				System.out.println(msg);
			}
			if (pro.waitFor() != 0) {
				if (pro.exitValue() == 1)// p.exitValue()==0��ʾ����������1������������
				System.err.println("����ִ��ʧ��!");
				System.out.println("ɾ��Z�ļ���ʧ��");
				}  
			System.out.println("ɾ��Z�ļ������");
		}
	}
}
