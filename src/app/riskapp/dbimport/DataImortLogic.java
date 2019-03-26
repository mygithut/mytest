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
	 * 数据初始化（执行一次）
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
			byte[] b = db2load.getBytes();// 得到字节类型的数据
			os.write(b);
			os.close();// 字节流没有使用缓冲区,
		}
	}
	
	
	/**
	 * 
	 * 解压目标数据文件压缩包(jar)到同一个指定目录(/home/db2inst1/ERDDDATA/updatedata/z/)下；
	 * <br>由jar包 解压成‘.z’格式的压缩文件包
	 * 
	 * */
	
	public static void doExcutefilelist(String sysdate) throws IOException, InterruptedException {
		
		String newSysDate = sysdate;
		String filename = "/home/db2inst1/ERDDDATA/340311/";
		File file = new File(filename);
		if (file.exists()&&file.isDirectory()) {
			
			System.out.println("获取第一次解压顶层目录......");
			String[] filelist = file.list();
			for (int j = 0; j < filelist.length; j++) {
				String typename = filelist[j].toString();
				File file1 = new File(filename + "/" + typename+"/");
				if (file1.exists() && file1.isDirectory()) {
					
					System.out.println("获取"+typename+"类型目录......");
					String[] filelist1 = file1.list();
					for (int k = 0; k < filelist1.length; k++) {
						String datename = filelist1[k].toString();
						if(datename.equals(newSysDate)){
							File file2 = new File(filename + "/" + typename + "/"+ datename+"/");
							if (file2.exists() && file2.isDirectory()) {
								
								System.out.println("获取"+typename+datename+"目录......");
								String[] filelist2 = file2.list();
								for (int i = 0; i < filelist2.length; i++) {
									File okfile = new File(filename + "/"+ typename + "/" + datename + "/"+ datename + ".ok");
									if (okfile.exists()) {
										String wjname = filelist2[i].toString();
										if (wjname.endsWith("jar")) {
											System.out.println("开始解压"+wjname+"包......");
											Process pro =Runtime.getRuntime().exec("unzip -o  " + filename + "/"+ typename + "/"+ datename + "/"+ wjname+ "   -d  /home/db2inst1/ERDDDATA/updatedata/z/");
											BufferedReader br = new BufferedReader(new InputStreamReader(pro.getInputStream())); 
											String msg = null; 
											while((msg = br.readLine())!= null){ 
											System.out.println(msg); 
											System.out.println(msg);
											}
											if (pro.waitFor() != 0) {
												if (pro.exitValue() == 1)// p.exitValue()==0表示正常结束，1：非正常结束
												System.err.println("命令执行失败!");
												System.out.println("解压"+wjname+"包失败");
												}  
											System.out.println("解压"+wjname+"包完成");
										}
									}else{
										
										System.out.println(typename+datename+"下没有ok文件，系统拒绝解压数据");
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
	 * 二次解密解压所有txt数据文件到同一个指定文件夹(/home/db2inst1/ERDDDATA/updatedata/txt/)；
	 * <br>由‘.z’包解压成txt数据文件
	 *  
	 * */
	public static void doCopyfilelist(String sysdate) throws IOException, InterruptedException {
		
		String newSysDate = sysdate;
		String filename = "/home/db2inst1/ERDDDATA/updatedata/z/";
		File file = new File(filename);
		if (file.exists()&&file.isDirectory()) {
			
			System.out.println("获取二次解压顶层目录......");
			String[] filelist = file.list();
			for (int j = 0; j < filelist.length; j++) {
				String typename = filelist[j].toString();
				     	String wjname = typename;
				 		if (wjname.endsWith(newSysDate+".z")) {
				 			System.out.println("开始解压"+wjname+"包......");
						    Process pro =Runtime.getRuntime().exec("unzip -o -P140313   " + filename + "/"+ wjname + "   -d /home/db2inst1/ERDDDATA/updatedata/txt/" );
							BufferedReader br = new BufferedReader(new InputStreamReader(pro.getInputStream())); 
					     	String msg = null; 
							while((msg = br.readLine())!= null){ 
							    	System.out.println(msg); 
							    	System.out.println(msg);
								}
							if (pro.waitFor() != 0) {
								if (pro.exitValue() == 1)// p.exitValue()==0表示正常结束，1：非正常结束
								System.err.println("命令执行失败!");
								System.out.println("解压"+wjname+"包失败");
								}  
							System.out.println("解压"+wjname+"包完成");
			}
		}
	}
}	
	
	
	
	/**
	 * 生成 数据库 ‘贴源数据每日增量导入’的2个sh脚本文件;
	 * <br>(INSERT_UPDATE INTO的放一个“ftp_updateinsert_import.sh”里、insert into与replace into放在一个“ftp_replace_import.sh”里);
	 * <br><br>有主键的直接 INSERT_UPDATE INTO ；其他看其是否在map里，map里的insert into，不在map里的replace into 
	 * 【map就是写死的：没有主键的表 且 直接insert into的所有表map】
	 * 
	 * */
	public static void doExcuteUpdate() throws IOException {
		
		//FTP系统需要的表
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
	
		//客户关系管理系统
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
		
		
		//新核心
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
		
		//个人网银开户
		ftp_map.put("NBS_MIR_MPCLOSECIFBOOK", "NBS_MIR_MPCLOSECIFBOOK");
		ftp_map.put("NBS_MIR_MPOPENCIFBOOK", "NBS_MIR_MPOPENCIFBOOK");
		//新信贷系统增加的表
		ftp_map.put("AHXD_MIR_DXLOANBAL", "AHXD_MIR_DXLOANBAL");
		ftp_map.put("AHXD_MIR_DXLOANBOOK", "AHXD_MIR_DXLOANBOOK");
		ftp_map.put("AHXD_MIR_DYACSUBJOCCUR", "AHXD_MIR_DYACSUBJOCCUR");
		ftp_map.put("AHXD_MIR_DXDISCBAL", "AHXD_MIR_DXDISCBAL");
		ftp_map.put("AHXD_MIR_DXBILLBAL", "AHXD_MIR_DXBILLBAL");
		
		//增量下发的表
		Map<String,String> map = new HashMap<String, String>();//没有主键的表 且 直接insert into的所有表map
		
		//客户关系管理系统
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
		//新核心
	    map.put("NSOP_TMP_BRFB11", "NSOP_TMP_BRFB11");
	    map.put("NSOP_TMP_BRFM13", "NSOP_TMP_BRFM13");
	    map.put("NSOP_MIR_GLFM54", "NSOP_MIR_GLFM54");
	    map.put("NSOP_MIR_ILFM31", "NSOP_MIR_ILFM31");
	    map.put("NSOP_MIR_ILFM34", "NSOP_MIR_ILFM34");
	    map.put("NSOP_MIR_INFT47", "NSOP_MIR_INFT47");
	    map.put("NSOP_MIR_VAFM12", "NSOP_MIR_VAFM12");
    	//个人网银开户
		map.put("NBS_MIR_MPCLOSECIFBOOK", "NBS_MIR_MPCLOSECIFBOOK");
		map.put("NBS_MIR_MPOPENCIFBOOK", "NBS_MIR_MPOPENCIFBOOK");

		String db2load = "\t\ndb2 connect to bips user db2inst1 using db2admin	";
		db2load = "\t\ndb2 connect to bips user db2inst1 using db2admin";
		String filename = "/home/db2inst1/ERDDDATA/updatedata/txt/";
		File file = new File(filename);
		if (file.isDirectory()) {
			String[] filelist = file.list();
			System.out.println("开始生成执行文件......");
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
						db2load += "\t\n echo 开始"+newname3+"表的导入...";
						db2load += "\t\ndb2 IMPORT FROM /home/db2inst1/ERDDDATA/updatedata/txt/"
								+ name
								+ " OF DEL MODIFIED BY codepage=1386  COMMITCOUNT 50000 MESSAGES \"/home/db2inst1/logs/"
								+ newname3
								+ ".log\"  INSERT_UPDATE INTO bips."
								+ newname3;
					}
				}
			}
			System.out.println("执行文件生成完成");
		}
		db2load += "\t\ndb2 disconnect current";
		System.out.println("输出可执行文件......");
		OutputStream os = new FileOutputStream("/home/db2inst1/ERDDDATA/ftp_batch/dataimport/ftp_updateinsert_import.sh");
		byte[] by = db2load.getBytes();// 得到字节类型的数据
		os.write(by);
		os.close();// 字节流没有使用缓冲区,
		System.out.println("输出可执行文件完成");
		
		db2load = "\t\ndb2 connect to bips user db2inst1 using db2admin";
		String filename1 = "/home/db2inst1/ERDDDATA/updatedata/txt/";
		File file1 = new File(filename1);
		if (file1.isDirectory()) {
			System.out.println("开始生成执行文件......");
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
						db2load += "\t\n echo 开始"+newname3+"表的导入...";
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
			System.out.println("执行文件生成完成");
		}
		db2load += "\t\ndb2 disconnect current";
		System.out.println("输出可执行文件......");
		OutputStream os1 = new FileOutputStream("/home/db2inst1/ERDDDATA/ftp_batch/dataimport/ftp_replace_import.sh");
		byte[] by1 = db2load.getBytes();// 得到字节类型的数据
		os1.write(by1);
		os1.close();// 字节流没有使用缓冲区,
		System.out.println("输出可执行文件完成");
	}
	
	
	
	/**
	 * 把更新数据插入对应表的临时表中 的ftp_insert_temp.sh脚本生成 ---关键分账户数据的增量备份
	 * 
	 * */
	public static void doExcuteInsertTemp() throws IOException {
		
		//需要做拉链的贴源表
		Map<String,String> map = new HashMap<String, String>();
	    map.put("NSOP_MIR_DPFM21_TEMP", "NSOP_MIR_DPFM21_TEMP");
		String db2load = "\t\ndb2 connect to bips user db2inst1 using db2admin	";
		db2load = "\t\ndb2 connect to bips user db2inst1 using db2admin";
		String filename1 = "/home/db2inst1/ERDDDATA/updatedata/txt/";
		File file1 = new File(filename1);
		if (file1.isDirectory()) {
			System.out.println("开始生成执行文件......");
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
					System.out.println("表名字是："+newname3);
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
			System.out.println("执行文件生成完成");
		}
		db2load += "\t\ndb2 disconnect current";
		System.out.println("输出可执行文件......");
		OutputStream os1 = new FileOutputStream("/home/db2inst1/ERDDDATA/ftp_batch/dataimport/ftp_insert_temp.sh");
		byte[] by1 = db2load.getBytes();// 得到字节类型的数据
		os1.write(by1);
		os1.close();// 字节流没有使用缓冲区,
		System.out.println("输出可执行文件完成");
	}

	

	
	/**
	 * 
	 * 执行之前生成的3个sh脚本(开始有一个判断：判断对私活期最新的维护日期是否是系统批量日期，是则执行导入脚本，否则终止)
	 * 
	 * */
	@SuppressWarnings("unchecked")
	public static boolean doExcuteSh(String sys_date) throws IOException, InterruptedException {
		String filename1 = "/home/db2inst1/ERDDDATA/ftp_batch/dataimport/ftp_insert_temp.sh";
		File file = new File(filename1);
	    	if(file.exists()){
			System.out.println("开始执行分账户备份TEMP表的插入操作......");
			Process pro =Runtime.getRuntime().exec("sh /home/db2inst1/ERDDDATA/ftp_batch/dataimport/ftp_insert_temp.sh" );
	    	BufferedReader br = new BufferedReader(new InputStreamReader(pro.getInputStream())); 
			String msg = null; 
			while((msg = br.readLine())!= null){ 
				System.out.println(msg);
		    }
			System.out.println(pro.waitFor( ));  
			System.out.println("执行分账户备份TEMP表的插入操作完成");
		}
	    	
		
	    //判断对私活期最新的维护日期是否是系统批量日期	
	    String sql = "select trim(max(DATLAST)) from BIPS.NSOP_MIR_DPFM21_TEMP";	
	    DaoFactory df = new DaoFactory();
	    List<String> list = df.query1(sql);
	    if(list!=null&&list.size()>0){
	    	System.out.println("系统最新维护日期是："+ String.valueOf(list.get(0)));
	        String repareDate = String.valueOf(list.get(0)).replaceAll("-","");
	        //#### 数据下发维护日期异常时仍强行导入
/*	        System.out.println("维护日期为"+repareDate+",批量日期为"+sys_date+"；两者不等仍强行导入！");//#### 数据下发维护日期异常时仍强行导入
	        repareDate=sys_date;//#### 数据下发维护日期异常时仍强行导入
*/	        
		    if(sys_date.equals(repareDate))	{
		    System.out.println("批量日期和最新维护日期相等，执行数据导入");
	     	filename1 = "/home/db2inst1/ERDDDATA/ftp_batch/dataimport/ftp_updateinsert_import.sh";
			file = new File(filename1);
			if(file.exists()){
				System.out.println("开始执行有主键的表插入和更新操作......");
				Process pro =Runtime.getRuntime().exec("sh /home/db2inst1/ERDDDATA/ftp_batch/dataimport/ftp_updateinsert_import.sh" );
				BufferedReader br = new BufferedReader(new InputStreamReader(pro.getInputStream())); 
				String msg = null; 
				while((msg = br.readLine())!= null){ 
				System.out.println(msg);
				}
				System.out.println(pro.waitFor( ));  
				System.out.println("执行有主键表的插入和更新操作完成");
			}
			 filename1 = "/home/db2inst1/ERDDDATA/ftp_batch/dataimport/ftp_replace_import.sh";
			 file = new File(filename1);
		    	if(file.exists()){
				System.out.println("开始执行无主键的表的插入或整表替换(replace)操作......");
				Process pro =Runtime.getRuntime().exec("sh /home/db2inst1/ERDDDATA/ftp_batch/dataimport/ftp_replace_import.sh" );
		    	BufferedReader br = new BufferedReader(new InputStreamReader(pro.getInputStream())); 
				String msg = null; 
				while((msg = br.readLine())!= null){ 
					System.out.println(msg);
			    }
				System.out.println(pro.waitFor( ));  
				System.out.println("执行无主键的表的插入或整表替换(replace)操作完成");
			}
		    return true;	
		    }else{
		    	  System.out.println("批量日期"+sys_date+"和分账户最新维护日期"+repareDate+"不相等，不执行数据导入");
		    	  return false;	
		    }
		}
	    else{
	    	  System.out.println("BIPS.NSOP_MIR_DPFM21_TEMP表数据未导入，不执行数据导入");
	    	  return false;	
	    }
	}
	
	
	/**
	 * 
	 * 删除“txt”和“z”("/home/db2inst1/ERDDDATA/updatedata/txt/)文件夹下的所有文件；包括文件夹本身
	 * 
	 * */
	public static void doDelete() throws IOException, InterruptedException {
		
		String filename1 = "/home/db2inst1/ERDDDATA/updatedata/txt/";
		File file = new File(filename1);
		if(file.exists()){
			System.out.println("开始删除TXT文件夹......");
			Process pro =Runtime.getRuntime().exec("rm  -rf  /home/db2inst1/ERDDDATA/updatedata/txt" );
			BufferedReader br = new BufferedReader(new InputStreamReader(pro.getInputStream())); 
			String msg = null; 
			while((msg = br.readLine())!= null){ 
				System.out.println(msg);
			}
			if (pro.waitFor() != 0) {
				if (pro.exitValue() == 1)// p.exitValue()==0表示正常结束，1：非正常结束
				System.err.println("命令执行失败!");
				System.out.println("删除Z文件夹失败");
				}  
			System.out.println("删除TXT文件夹完成");
		}
		filename1 = "/home/db2inst1/ERDDDATA/updatedata/z/";
	    file = new File(filename1);
		if(file.exists()){
			System.out.println("开始删除Z文件夹......");
			Process pro =Runtime.getRuntime().exec("rm  -rf  /home/db2inst1/ERDDDATA/updatedata/z" );
			BufferedReader br = new BufferedReader(new InputStreamReader(pro.getInputStream())); 
			String msg = null; 
			while((msg = br.readLine())!= null){ 
				System.out.println(msg);
			}
			if (pro.waitFor() != 0) {
				if (pro.exitValue() == 1)// p.exitValue()==0表示正常结束，1：非正常结束
				System.err.println("命令执行失败!");
				System.out.println("删除Z文件夹失败");
				}  
			System.out.println("删除Z文件夹完成");
		}
	}
}
