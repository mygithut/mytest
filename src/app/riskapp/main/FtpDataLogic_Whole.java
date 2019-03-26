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
 * ��̨�����������
 * @author Administrator
 *
 */
public class FtpDataLogic_Whole {
	
	
	
	 private static final Logger logger=Logger.getLogger(FtpDataLogic_Whole.class);
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
			System.out.println("��ʼִ����������<����>����...");
			try{
				doNormal_LPSLogic();					
			}catch(Exception e){
				e.printStackTrace();
			}
		}

	
	
	/**
	 * ����ÿ�����к�̨Ӧ����������--- T+1ģʽ
	 * @throws InterruptedException 
	 * @throws IOException 
	 */
	public static void doNormal_LPSLogic() throws IOException, InterruptedException{
		
		
		 //�ж�ftpϵͳ�������ں����ݵ������������Ƿ����	
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
	    	System.out.println("ϵͳ���ڻ�ȡ�����������ݿ⹫��������com_sys_parm��");
	    	return;
	    }
	    com_sys_parm=list.get(0);
	    long oldSysDate=com_sys_parm.getSysDate();
	    if(!(String.valueOf(oldSysDate)).equals(ftp_sysDate)){
	    	
	    	System.out.println("ftpϵͳ���ں���Դ����bipsϵͳ���ڲ���ȣ��������ݿ⹫��������com_sys_parm��");
	    	return;
	    }
	    String termType="0";//�� ���ա�Ϊ���ڣ���ÿ�������� --- 0:1�죻  3:Ѯ�� 5:�£�
	    long newSysDate=CommonFunctions.getNewSysDate_byTermType(oldSysDate, termType);
	    
	    String newSysDate_str=String.valueOf(newSysDate);
	    System.out.println(newSysDate_str);
	    String newSysDate_str10=newSysDate_str.substring(0,4)+"-"+newSysDate_str.substring(4,6)+"-"+newSysDate_str.substring(6);
	    
	    //ִ������
	    DataImortLogic.doExcutefilelist(newSysDate_str);
	    DataImortLogic.doCopyfilelist(newSysDate_str);//##����ʱ��ֱ�Ӵ�z����ѹ����ʼ������֮ǰ�ľ���Ҫע�͵�
	    DataImortLogic.doExcuteUpdate();
	    DataImortLogic.doExcuteInsertTemp();
	    //�ж��Ƿ�ִ�е������
	    boolean f =  DataImortLogic.doExcuteSh(newSysDate_str);
	    DataImortLogic.doDelete();
	    if(f){
		     //DataMERGE_AfterLDR.do_excute_DataMERGE_AfterLDR(newSysDate_str);
		    //DoDeailClear.doExcute_DetailClear(newSysDate_str);
		    //������ɱ�־�ļ�
		    System.out.println("��ʼ���ɳɹ���־�ļ�(/home/db2inst1/ERDDDATA/updatedata/end/"+newSysDate_str10+".end)...");
		    File markFile=new File("/home/db2inst1/ERDDDATA/updatedata/end/"+newSysDate_str10+".end");
		    markFile.createNewFile();
		    System.out.println("���ɳɹ���־�ļ�(/home/db2inst1/ERDDDATA/updatedata/end/"+newSysDate_str10+".end)��ɣ�");
		    //��������ʱ��
		    System.out.println("��ʼ�������ݿ�ϵͳʱ��...");
			sql="update BIPS.com_sys_parm set Sys_Date="+newSysDate+",lst_date="+oldSysDate
			   +" where Sys_Date="+oldSysDate;
	        CommonFunctions.mydao.execute1(sql);
	        
	        
	    	//���浱��(������8������������ִ��������ֹͣ)��־�ļ�---��yqdatalogic.log����Ϊyqdatalogic_��������#�����ʱ��.log��洢
			File logFile=new File("/home/db2inst1/ERDDDATA/java_jar/logs/bbdatalogic.log");
			File saveLogFile=new File("/home/db2inst1/ERDDDATA/java_jar/logs/bbdatalogic_"+newSysDate+"#"+CommonFunctions.GetCurrentTimeInFileName()+".log");
			logFile.renameTo(saveLogFile);
	        
	    }else{
	    	//������ɱ�־�ļ�
	    	System.out.println("��ʼ����ʧ����־�ļ��������·�����...");
		    File markFile=new File("/home/db2inst1/ERDDDATA/updatedata/error/"+newSysDate_str10+".error");
		    markFile.createNewFile();
		    System.out.println("����ʧ����־�ļ���ɣ������·�����");
		    
		    
			//���浱��(������8������������ִ��������ֹͣ)��־�ļ�---��yqdatalogic.log����Ϊyqdatalogic_��������#�����ʱ��.log��洢
			File logFile=new File("/home/db2inst1/ERDDDATA/java_jar/logs/bbdatalogic.log");
			File saveLogFile=new File("/home/db2inst1/ERDDDATA/java_jar/logs/bbdatalogic_"+newSysDate+"#"+CommonFunctions.GetCurrentTimeInFileName()+".log");
			logFile.renameTo(saveLogFile);
	    }
	    
		String ETime=CommonFunctions.GetCurrentTime();
		int costTime=CommonFunctions.GetCostTimeInSecond(STime, ETime);
		int CostFen=costTime/60;
		int CostMiao=costTime%60;
		System.out.println("��ʱ "+CostFen+"��"+CostMiao+"��");
	}
}
