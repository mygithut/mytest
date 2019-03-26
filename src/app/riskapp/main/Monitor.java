package app.riskapp.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.FieldPosition;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import app.riskapp.common.CommonFunctions;
import app.riskapp.dbimport.DataImortLogic;
import app.riskapp.entity.ComSysParm;
import app.riskapp.filetrans.FtpClient;

public class Monitor {
	
	  public static Map<String,String> flagmap = new HashMap<String,String>();
	  private static final Logger logger=Logger.getLogger(Monitor.class);
	
	  /**
	   * 监控程序入口
	   * @author Administrator
	   *
	   */
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws IOException, InterruptedException {
		
		String sql="from ComSysParm order by sysDate DESC";
	    List<ComSysParm> list=CommonFunctions.mydao.query(sql, null);
	    ComSysParm com_sys_parm=new ComSysParm();
	    if(list.size()==0){
	    	System.out.println("系统日期获取出错，请检查数据库公共参数表com_sys_parm！");
	    	return;
	    }
	    com_sys_parm=list.get(0);
	    long oldSysDate=com_sys_parm.getSysDate();
	    String termType="0";//按 ‘日’为周期，即每天跑批量 --- 0:1天；  3:旬； 5:月；
	    long newSysDate=CommonFunctions.getNewSysDate_byTermType(oldSysDate, termType);
	    String newSysDate_str=String.valueOf(newSysDate);
	   // System.out.println("开始下载远程数据...");
	   // doExcuteremotefilelist(newSysDate_str);

	    System.out.println("开始扫描ok文件...");
		doExcutefilelist(newSysDate_str);
	    int count = 72;//72个10分钟(12小时)后，如果还没有下发数据则停止该导入监控程序
	    while(count>0){
				if(flagmap.containsKey("CMS"+newSysDate_str)&&flagmap.containsKey("BIL"+newSysDate_str)&&flagmap.containsKey("NSOP"+newSysDate_str)&&flagmap.containsKey("SOP"+newSysDate_str)){
				//if(flagmap.containsKey("ODS"+newSysDate_str)){
			   // if(flagmap.containsKey("CMS"+newSysDate_str)){
					System.out.println("开始执行本日批量......");
					FtpDataLogic_Whole.doNormal_LPSLogic();
					System.out.println("本日批量完成");
					return;
				}
				
		       //停止10分钟
				System.out.println("开始执行休眠......");
				for(int i =10;i>0;i--){
					Thread.sleep(60*1000);
				}
	            count--;
	            //再次扫描
	         	System.out.println("开始再次扫描......");
	            doExcutefilelist(newSysDate_str);       
	    }
	    //生成完成标志文件
	    File markFile=new File("/home/db2inst1/ERDDDATA/updatedata/error/"+newSysDate_str+".error");
	    System.out.println("开始生成失敗标志文件(数据没有下发)...");
	    markFile.createNewFile();
	    System.out.println("生成失敗标志文件完成(数据没有下发)!");
	}

	/**
	 * 
	 * 判断本地数据文件目录中所有ok文件是否都存在
	 * 
	 */
	@SuppressWarnings("unchecked")
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
									flagmap.put( typename+datename,typename+datename);
								}else{
									System.out.println(typename+datename+"下没有ok文件");
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
	 * 判断远程下发数据平台所有ok文件是否都存在，并且下载‘ok文件’与‘jar包文件’到本地目录(/home/db2inst1/ERDDDATA/340311/贴源系统代码/批量日期/)下
	 * 
	 * */
	@SuppressWarnings("unchecked")
	public static void doExcuteremotefilelist(String sysdate) throws IOException, InterruptedException {
		
		
		 Map<String,String> flagmap_download = new HashMap<String,String>();
		//读取数据库服务器的计算机信息：[ip,用户名,密码,数据存放根目录(最后必须带/)(如:/odsdatakf/sfts/send/)]
		String ds_conf_file="/home/db2inst1/ERDDDATA/ftp_batch/conf/data_source.txt";
		String[] ds_conf=null;
		try{
			BufferedReader reader = new BufferedReader(new FileReader(new File(ds_conf_file)));
			String tempString = null;
			tempString=reader.readLine();
			ds_conf=tempString.split(",");
			reader.close();
		}catch(Exception ds_e){
			System.out.println("获取数据库服务器的计算机信息失败，监控程序无法启动！");
			System.out.println(ds_e.getMessage());
			ds_e.printStackTrace();
			return;
		}
		if(ds_conf.length!=4){
			System.out.println("数据库服务器计算机信息配置["+ds_conf_file+"]有误(数据项个数不为4)，无法启动监控程序！");
			return;
		}
		System.out.println("获取数据库服务器的计算机信息成功：");
		for(int i_ds_conf=0;i_ds_conf<ds_conf.length;i_ds_conf++){
			System.out.println(ds_conf[i_ds_conf]);
		}
		
		FtpClient.connect(ds_conf[0].trim(), 21, ds_conf[1].trim(), ds_conf[2].trim());//改为由配置文件控制“/home/oracle/ftp_batch/conf/data_source.txt”
		
		boolean del_Data_isDown_whole=false;//3大贴源系统数据del是否都已下载完：只有del_Data_isDown数组的3个都为true时，del_Data_isDown_whole才为true

		//--------------------------------------- end -------------------------------------------------------------
		int ODS_num=5;//要下载的贴源数据的系统个数 ##**##
		String[] donwLoadSh = new String[ODS_num];
		//判断远程贴源数据是否下发完成 
		       
		 String newSysDate = sysdate;
		 String filename = ds_conf[3].trim();
		 int count = 72;
		 while(count>0){
			 if (FtpClient.isFileExist(filename)) {
					System.out.println("获取第一次解压顶层目录......");
					String[] filelist = FtpClient.GetFileNames(filename);
					int source_count = ODS_num; 
					for (int j = 0; j < filelist.length; j++) {
						String typename = filelist[j].toString();
						if(typename.equals("CMS")||typename.equals("SAP")||typename.equals("SOP")||typename.equals("BIL")||typename.equals("NBS")){
							source_count --;
							if (FtpClient.isFileExist(filename + "/" + typename+"/")) {
								System.out.println("获取"+typename+"类型目录......");
								String[] filelist1 = FtpClient.GetFileNames(filename + "/" + typename+"/");
								for (int k = 0; k < filelist1.length; k++) {
									String datename = filelist1[k].toString();
									if(datename.equals(newSysDate)){
										if (FtpClient.isFileExist(filename + "/" + typename + "/"+ datename+"/")) {
											System.out.println("获取"+typename+"-"+datename+"目录......");
											
										   donwLoadSh[source_count] = "\t\n   cd /home/db2inst1/ERDDDATA/340311/"+typename+"/";
										   donwLoadSh[source_count] += "\t\n   mkdir "+datename;
										   donwLoadSh[source_count] += "\t\n   cd /home/db2inst1/ERDDDATA/340311/"+typename+"/"+datename+"/";
										   donwLoadSh[source_count] += "\t\n   /usr/bin/ftp -vn "+ds_conf[0].trim()+  " <<EOF ";  
										   donwLoadSh[source_count] += "\t\n   user  "+ds_conf[1].trim()+ " "+ds_conf[2].trim();
										   donwLoadSh[source_count] += "\t\n   bin  ";
										   donwLoadSh[source_count] += "\t\n   prompt  ";
										   donwLoadSh[source_count] += "\t\n  cd "+filename + "/"+ typename + "/"+ datename+"/";
										   
											String[] filelist2 = FtpClient.GetFileNames(filename + "/" + typename + "/"+ datename+"/");
											for (int i = 0; i < filelist2.length; i++) {
												if (FtpClient.isFileExist(filename + "/"+ typename + "/" + datename + "/"+ datename + ".ok")) {
													if(filelist2[i].endsWith("jar")||filelist2[i].equals(datename+".ok")){
														flagmap_download.put( typename+datename,typename+datename);
														System.out.println("--开始构建ftp下载命令行："+typename+"-"+datename+"-"+filelist2[i]+"...");
														donwLoadSh[source_count] += "\t\n  get "+filelist2[i];
														System.out.println("构建"+typename+"-"+datename+"-"+filelist2[i]+"的ftp下载命令行结束！");
													}
												}else{
													System.out.println(typename+datename+"下没有ok文件");
												    }
										     	}
												
											donwLoadSh[source_count] += "\t\n   close  ";
											donwLoadSh[source_count] += "\t\n   bye  ";
											donwLoadSh[source_count] += "\t\nEOF  ";
										}
									}
								}
							}
					    }	
					}
				}
				if(flagmap_download.containsKey("CMS"+sysdate)&&flagmap_download.containsKey("SOP"+sysdate)&&flagmap_download.containsKey("SAP"+sysdate)&&flagmap_download.containsKey("BIL"+sysdate)&&flagmap_download.containsKey("NBS"+sysdate)){
					 del_Data_isDown_whole = true;	
					 if (del_Data_isDown_whole){//贴源数据del文件已经全部下载完成(标志文件已存在)
							//2.(3/3)普通FTP：21端口
							FtpClient.disconnect();
							
						    //输出ftp脚本
							for(int i=0;i<donwLoadSh.length;i++){
										OutputStream os = new FileOutputStream("/home/db2inst1/ERDDDATA/ftp_batch/download/zl/donwload_"+i+".sh");
										byte[] by = donwLoadSh[i].getBytes();// 得到字节类型的数据
										os.write(by);
										os.close();// 字节流没有使用缓冲区
							}
							//执行下载脚本
							for(int i=0;i<donwLoadSh.length;i++){	
										String msg_ftp = null;
										Process pro_ftp =Runtime.getRuntime().exec("sh /home/db2inst1/ERDDDATA/ftp_batch/download/zl/donwload_"+i+".sh" );//
										BufferedReader br_ftp = new BufferedReader(new InputStreamReader(pro_ftp.getInputStream())); 
										while((msg_ftp = br_ftp.readLine())!= null){ 
											System.out.println(msg_ftp);
										}
										System.out.println(pro_ftp.waitFor());
							}
							String STime=GetCurrentTime();
							String SuccessMarkFile_houzui="";//批量结束后生成的标志文件的后缀;成功为：ok、失败为：error
							//System.out.println("--------> /home/oracle/ftp_batch/markfile/"+MarkFileName+"已经存在，开始执行正常日期<当日>批量...");
							System.out.println("--------> 贴源数据文件del都已经下载完成");
							
					}
					return;
				}
				
				//打印哪些贴源系统的数据下发了，哪些没下发
				System.out.println("数据还没全部下发@"+CommonFunctions.GetCurrentTime()+"【CMS:"+flagmap_download.containsKey("CMS"+sysdate)+"; SOP:"+flagmap_download.containsKey("SOP"+sysdate)
						           +"; SAP:"+flagmap_download.containsKey("SAP"+sysdate)+"; BIL:"+flagmap_download.containsKey("BIL"+sysdate)+"; NBS:"+flagmap_download.containsKey("NBS"+sysdate)+"】");
		        //停止10分钟
				System.out.println("所以休眠10分钟后再去扫描......");
				for(int i =10;i>0;i--)
				   {
					Thread.sleep(60*1000);
				   }
		         count--;
		}
	}
	
	/**
	 * 获取当前系统时间
	 * @return  (String)  当前系统时间 格式为 "年-月-日 时:分:秒"，如 "2009-12-02 20:45:06"
	 */
	public static String GetCurrentTime(){
		/*
		//SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy'-'MM'-'dd' 'hh':'mm':'ss");
		DateFormat dateformat = DateFormat.getDateTimeInstance(); 
		Calendar date = Calendar.getInstance();
		String CurrentTime=dateformat.format(date.getTime());
		//System.out.println("######当前时间为： "+CurrentTime);
		*/
		
		Calendar ca = Calendar.getInstance();
	    int year = ca.get(Calendar.YEAR);//获取年份
	    String Stryear=String.valueOf(year);
	    
	    int month=ca.get(Calendar.MONTH)+1;//获取月份  月份系统从0开始算起的
	    String Strmonth=String.valueOf(month);
	    if(month<10){
	    	Strmonth="0"+Strmonth;
	    }
	    
	    int day=ca.get(Calendar.DATE);//获取日
	    String Strday=String.valueOf(day);
	    if(day<10){
	    	Strday="0"+Strday;
	    }
	    
	    int hour=ca.get(Calendar.HOUR_OF_DAY);//小时
	    String Strhour=String.valueOf(hour);
	    if(hour<10){
	    	Strhour="0"+Strhour;
	    }
	    
	    int minute=ca.get(Calendar.MINUTE);//分
	    String Strminute=String.valueOf(minute);
	    if(minute<10){
	    	Strminute="0"+Strminute;
	    }
	    
	    int second=ca.get(Calendar.SECOND);//秒    
	    String Strsecond=String.valueOf(second);
	    if(second<10){
	    	Strsecond="0"+Strsecond;
	    }
	    
	    String CurrentTime=Stryear+"-"+Strmonth+"-"+Strday+" "+Strhour+":"+Strminute+":"+Strsecond;
	    return CurrentTime;
	}
	
	/**
	 * 获取同一天内两时间字符串的时间差 以秒为单位,  时间字符串格式为2009-12-02 20:45:06
	 * 
	 * 
	 */
	public static int GetCostTimeInSecond(String BeginTime,String EndTime){
		int CostTimeInSecond=0;
		BeginTime=BeginTime.substring(BeginTime.length()-8);
		EndTime=EndTime.substring(EndTime.length()-8);
		int IntShi_BeginTime=Integer.parseInt(BeginTime.substring(0,2)),
		    IntFen_BeginTime=Integer.parseInt(BeginTime.substring(3,5)),
		    IntMiao_BeginTime=Integer.parseInt(BeginTime.substring(6)),
		    IntShi_EndTime=Integer.parseInt(EndTime.substring(0,2)),
		    IntFen_EndTime=Integer.parseInt(EndTime.substring(3,5)),
		    IntMiao_EndTime=Integer.parseInt(EndTime.substring(6));
		int DeltaShi=IntShi_EndTime-IntShi_BeginTime,
		    DeltaFen=IntFen_EndTime-IntFen_BeginTime,
		    DeltaMiao=IntMiao_EndTime-IntMiao_BeginTime;
		//System.out.println("###########"+IntMiao_EndTime);
		CostTimeInSecond=DeltaShi*3600+DeltaFen*60+DeltaMiao;
		return CostTimeInSecond;
	}
	
	/**
	 * 获取当前系统日期
	 * @return  (String)  当前系统日期 ，格式为 "年-月-日"，如 "2009-12-02"
	 */
	public static String GetCurrentDate(){
		/*
		//SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy'-'MM'-'dd' 'hh':'mm':'ss");
		DateFormat dateformat = DateFormat.getDateTimeInstance(); 
		Calendar date = Calendar.getInstance();
		String CurrentTime=dateformat.format(date.getTime());
		//System.out.println("######当前时间为： "+CurrentTime);
		*/
		
		Calendar ca = Calendar.getInstance();
	    int year = ca.get(Calendar.YEAR);//获取年份
	    String Stryear=String.valueOf(year);
	    
	    int month=ca.get(Calendar.MONTH)+1;//获取月份  月份系统从0开始算起的
	    String Strmonth=String.valueOf(month);
	    if(month<10){
	    	Strmonth="0"+Strmonth;
	    }
	    
	    int day=ca.get(Calendar.DATE);//获取日
	    String Strday=String.valueOf(day);
	    if(day<10){
	    	Strday="0"+Strday;
	    }
	    
	    int hour=ca.get(Calendar.HOUR_OF_DAY);//小时
	    String Strhour=String.valueOf(hour);
	    if(hour<10){
	    	Strhour="0"+Strhour;
	    }
	    
	    int minute=ca.get(Calendar.MINUTE);//分
	    String Strminute=String.valueOf(minute);
	    if(minute<10){
	    	Strminute="0"+Strminute;
	    }
	    
	    int second=ca.get(Calendar.SECOND);//秒    
	    String Strsecond=String.valueOf(second);
	    if(second<10){
	    	Strsecond="0"+Strsecond;
	    }
	    
	    String CurrentDate=Stryear+"-"+Strmonth+"-"+Strday;
	    return CurrentDate;
	}

	 /**
     * 返回两日期相减的结果天数，负数表示dateStr1在dateStr2前。dateStr1格式为"yyyy-mm-dd"
     * @param dateStr1 String  被减数日期字符串
     * @param dateStr2 String 减数日期字符串
     * @return int dateStr1-dateStr2的天数
     */
    public static int daysSubtract(String dateStr1, String dateStr2) {
    	  SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    	  SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
    	  try {
    	   dateStr1 = sdf.format(sdf2.parse(dateStr1));
    	   dateStr2 = sdf.format(sdf2.parse(dateStr2));
    	  } catch (ParseException e) {
    	   e.printStackTrace();
    	  }
    	  int year1 = Integer.parseInt(dateStr1.substring(0, 4));
    	  int month1 = Integer.parseInt(dateStr1.substring(4, 6));
    	  int day1 = Integer.parseInt(dateStr1.substring(6, 8));
    	  int year2 = Integer.parseInt(dateStr2.substring(0, 4));
    	  int month2 = Integer.parseInt(dateStr2.substring(4, 6));
    	  int day2 = Integer.parseInt(dateStr2.substring(6, 8));
    	  Calendar c1 = Calendar.getInstance();
    	  c1.set(Calendar.YEAR, year1);
    	  c1.set(Calendar.MONTH, month1 - 1);
    	  c1.set(Calendar.DAY_OF_MONTH, day1);
    	  Calendar c2 = Calendar.getInstance();
    	  c2.set(Calendar.YEAR, year2);
    	  c2.set(Calendar.MONTH, month2 - 1);
    	  c2.set(Calendar.DAY_OF_MONTH, day2);
    	  long mills =c1.getTimeInMillis() - c2.getTimeInMillis();
    	  return (int) (mills / 1000 / 3600 / 24);
    }

	/**
	 * 修改日期天数 
	 * @param baseDate 基准日期String (格式yyyy-MM-dd)
	 * @param amount 要增加的天数（负数为减）
	 * @return 基准日期增加或减少若干天后的日期
	 */
	public static String dateModify(String baseDate, int amount) {
		StringBuffer sb = new StringBuffer();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // 将字符串格式化
		Date dt = sdf.parse(baseDate, new ParsePosition(0)); // 由格式化后的字符串产生一个Date对象

		Calendar c = Calendar.getInstance(); // 初始化一个Calendar
		try {
			c.setTime(dt); // 设置基准日期
		} catch (Exception e) {
			System.out.println("输入日期" + baseDate + "格式错误，这不符合格式yyyy-MM-dd！");
			return null;
			// e.printStackTrace();
		}

		c.add(Calendar.DATE, amount); // 你要加减的日期
		Date dt1 = c.getTime(); // 从Calendar对象获得基准日期增减后的日期
		sb = sdf.format(dt1, sb, new FieldPosition(0)); // 得到结果字符串

		return sb.toString();
	}
}
