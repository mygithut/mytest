package app.riskapp.main;

import java.io.File;
import java.io.IOException;
import java.util.List;
import org.apache.log4j.Logger;
import app.riskapp.common.CommonFunctions;
import app.riskapp.common.DaoFactory;
import app.riskapp.common.SFTPUtil;
import app.riskapp.dbimport.DataImortLogic;
import app.riskapp.dbimport.DataMERGE_AfterLDR;
import app.riskapp.dbimport.DoDeailClear;
import app.riskapp.entity.ComSysParm;
import com.jcraft.jsch.ChannelSftp;

/**
 * 后台数据批量入口
 * @author Administrator
 *
 */
public class FtpDataLogic_Whole {
	
	
	
	 private static final Logger logger=Logger.getLogger(FtpDataLogic_Whole.class);
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
			System.out.println("开始执行正常日期<当日>批量...");
			try{
				doNormal_LPSLogic();					
			}catch(Exception e){
				e.printStackTrace();
			}
		}

	
	
	/**
	 * 正常每日运行后台应用批量程序--- T+1模式
	 * @throws InterruptedException 
	 * @throws IOException 
	 */
	public static void doNormal_LPSLogic() throws IOException, InterruptedException{
		
		
		 //判断ftp系统批量日期和数据导入批量日期是否相等	
		String ftp_sysDate = "20150101"; 
	    String sql = "select TODAY_DATE from ftp.COM_SYS_PARM";	
	    DaoFactory df = new DaoFactory();
	    List<String> list_ftp_sysDate = df.query1(sql);
	    if(list_ftp_sysDate!=null&&list_ftp_sysDate.size()>0){
	    	 ftp_sysDate = String.valueOf(list_ftp_sysDate.get(0));
	    }
		
		String STime=CommonFunctions.GetCurrentTime();
	    sql="from ComSysParm order by sysDate DESC";
	    List<ComSysParm> list=CommonFunctions.mydao.query(sql, null);
	    ComSysParm com_sys_parm=new ComSysParm();
	    if(list.size()==0){
	    	System.out.println("系统日期获取出错，请检查数据库公共参数表com_sys_parm！");
	    	return;
	    }
	    com_sys_parm=list.get(0);
	    long oldSysDate=com_sys_parm.getSysDate();
	    if(!(String.valueOf(oldSysDate)).equals(ftp_sysDate)){
	    	
	    	System.out.println("ftp系统日期和贴源数据bips系统日期不相等，请检查数据库公共参数表com_sys_parm！");
	    	return;
	    }
	    String termType="0";//按 ‘日’为周期，即每天跑批量 --- 0:1天；  3:旬； 5:月；
	    long newSysDate=CommonFunctions.getNewSysDate_byTermType(oldSysDate, termType);
	    
	    String newSysDate_str=String.valueOf(newSysDate);
	    System.out.println(newSysDate_str);
	    String newSysDate_str10=newSysDate_str.substring(0,4)+"-"+newSysDate_str.substring(4,6)+"-"+newSysDate_str.substring(6);
	    
	    //执行批量
	    DataImortLogic.doExcutefilelist(newSysDate_str);
	    DataImortLogic.doCopyfilelist(newSysDate_str);//##特殊时，直接从z包解压缩开始，这里之前的就需要注释掉
	    DataImortLogic.doExcuteUpdate();
	    DataImortLogic.doExcuteInsertTemp();
	    //判断是否执行导入操作
	    boolean f =  DataImortLogic.doExcuteSh(newSysDate_str);
	    DataImortLogic.doDelete();
	    if(f){
		     //DataMERGE_AfterLDR.do_excute_DataMERGE_AfterLDR(newSysDate_str);
		    //DoDeailClear.doExcute_DetailClear(newSysDate_str);
		    //生成完成标志文件
		    System.out.println("开始生成成功标志文件(/home/db2inst1/ERDDDATA/updatedata/end/"+newSysDate_str10+".end)...");
		    File markFile=new File("/home/db2inst1/ERDDDATA/updatedata/end/"+newSysDate_str10+".end");
		    markFile.createNewFile();
		    System.out.println("生成成功标志文件(/home/db2inst1/ERDDDATA/updatedata/end/"+newSysDate_str10+".end)完成！");
		    //更新批量时间
		    System.out.println("开始更新数据库系统时间...");
			sql="update BIPS.com_sys_parm set Sys_Date="+newSysDate+",lst_date="+oldSysDate
			   +" where Sys_Date="+oldSysDate;
	        CommonFunctions.mydao.execute1(sql);
	        
	        
	    	//储存当日(从早上8点启动到当日执行完批量停止)日志文件---由yqdatalogic.log改名为yqdatalogic_批量日期#计算机时间.log后存储
			File logFile=new File("/home/db2inst1/ERDDDATA/java_jar/logs/bbdatalogic.log");
			File saveLogFile=new File("/home/db2inst1/ERDDDATA/java_jar/logs/bbdatalogic_"+newSysDate+"#"+CommonFunctions.GetCurrentTimeInFileName()+".log");
			logFile.renameTo(saveLogFile);
	        
	    }else{
	    	//生成完成标志文件
	    	System.out.println("开始生成失敗标志文件，数据下发有误...");
		    File markFile=new File("/home/db2inst1/ERDDDATA/updatedata/error/"+newSysDate_str10+".error");
		    markFile.createNewFile();
		    System.out.println("生成失敗标志文件完成，数据下发有误");
		    
		    
			//储存当日(从早上8点启动到当日执行完批量停止)日志文件---由yqdatalogic.log改名为yqdatalogic_批量日期#计算机时间.log后存储
			File logFile=new File("/home/db2inst1/ERDDDATA/java_jar/logs/bbdatalogic.log");
			File saveLogFile=new File("/home/db2inst1/ERDDDATA/java_jar/logs/bbdatalogic_"+newSysDate+"#"+CommonFunctions.GetCurrentTimeInFileName()+".log");
			logFile.renameTo(saveLogFile);
	    }
	    
		String ETime=CommonFunctions.GetCurrentTime();
		int costTime=CommonFunctions.GetCostTimeInSecond(STime, ETime);
		int CostFen=costTime/60;
		int CostMiao=costTime%60;
		System.out.println("耗时 "+CostFen+"分"+CostMiao+"秒");
	}
}
