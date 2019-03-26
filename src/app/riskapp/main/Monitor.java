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
	   * ��س������
	   * @author Administrator
	   *
	   */
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws IOException, InterruptedException {
		
		String sql="from ComSysParm order by sysDate DESC";
	    List<ComSysParm> list=CommonFunctions.mydao.query(sql, null);
	    ComSysParm com_sys_parm=new ComSysParm();
	    if(list.size()==0){
	    	System.out.println("ϵͳ���ڻ�ȡ�����������ݿ⹫��������com_sys_parm��");
	    	return;
	    }
	    com_sys_parm=list.get(0);
	    long oldSysDate=com_sys_parm.getSysDate();
	    String termType="0";//�� ���ա�Ϊ���ڣ���ÿ�������� --- 0:1�죻  3:Ѯ�� 5:�£�
	    long newSysDate=CommonFunctions.getNewSysDate_byTermType(oldSysDate, termType);
	    String newSysDate_str=String.valueOf(newSysDate);
	   // System.out.println("��ʼ����Զ������...");
	   // doExcuteremotefilelist(newSysDate_str);

	    System.out.println("��ʼɨ��ok�ļ�...");
		doExcutefilelist(newSysDate_str);
	    int count = 72;//72��10����(12Сʱ)�������û���·�������ֹͣ�õ����س���
	    while(count>0){
				if(flagmap.containsKey("CMS"+newSysDate_str)&&flagmap.containsKey("BIL"+newSysDate_str)&&flagmap.containsKey("NSOP"+newSysDate_str)&&flagmap.containsKey("SOP"+newSysDate_str)){
				//if(flagmap.containsKey("ODS"+newSysDate_str)){
			   // if(flagmap.containsKey("CMS"+newSysDate_str)){
					System.out.println("��ʼִ�б�������......");
					FtpDataLogic_Whole.doNormal_LPSLogic();
					System.out.println("�����������");
					return;
				}
				
		       //ֹͣ10����
				System.out.println("��ʼִ������......");
				for(int i =10;i>0;i--){
					Thread.sleep(60*1000);
				}
	            count--;
	            //�ٴ�ɨ��
	         	System.out.println("��ʼ�ٴ�ɨ��......");
	            doExcutefilelist(newSysDate_str);       
	    }
	    //������ɱ�־�ļ�
	    File markFile=new File("/home/db2inst1/ERDDDATA/updatedata/error/"+newSysDate_str+".error");
	    System.out.println("��ʼ����ʧ����־�ļ�(����û���·�)...");
	    markFile.createNewFile();
	    System.out.println("����ʧ����־�ļ����(����û���·�)!");
	}

	/**
	 * 
	 * �жϱ��������ļ�Ŀ¼������ok�ļ��Ƿ񶼴���
	 * 
	 */
	@SuppressWarnings("unchecked")
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
									flagmap.put( typename+datename,typename+datename);
								}else{
									System.out.println(typename+datename+"��û��ok�ļ�");
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
	 * �ж�Զ���·�����ƽ̨����ok�ļ��Ƿ񶼴��ڣ��������ء�ok�ļ����롮jar���ļ���������Ŀ¼(/home/db2inst1/ERDDDATA/340311/��Դϵͳ����/��������/)��
	 * 
	 * */
	@SuppressWarnings("unchecked")
	public static void doExcuteremotefilelist(String sysdate) throws IOException, InterruptedException {
		
		
		 Map<String,String> flagmap_download = new HashMap<String,String>();
		//��ȡ���ݿ�������ļ������Ϣ��[ip,�û���,����,���ݴ�Ÿ�Ŀ¼(�������/)(��:/odsdatakf/sfts/send/)]
		String ds_conf_file="/home/db2inst1/ERDDDATA/ftp_batch/conf/data_source.txt";
		String[] ds_conf=null;
		try{
			BufferedReader reader = new BufferedReader(new FileReader(new File(ds_conf_file)));
			String tempString = null;
			tempString=reader.readLine();
			ds_conf=tempString.split(",");
			reader.close();
		}catch(Exception ds_e){
			System.out.println("��ȡ���ݿ�������ļ������Ϣʧ�ܣ���س����޷�������");
			System.out.println(ds_e.getMessage());
			ds_e.printStackTrace();
			return;
		}
		if(ds_conf.length!=4){
			System.out.println("���ݿ�������������Ϣ����["+ds_conf_file+"]����(�����������Ϊ4)���޷�������س���");
			return;
		}
		System.out.println("��ȡ���ݿ�������ļ������Ϣ�ɹ���");
		for(int i_ds_conf=0;i_ds_conf<ds_conf.length;i_ds_conf++){
			System.out.println(ds_conf[i_ds_conf]);
		}
		
		FtpClient.connect(ds_conf[0].trim(), 21, ds_conf[1].trim(), ds_conf[2].trim());//��Ϊ�������ļ����ơ�/home/oracle/ftp_batch/conf/data_source.txt��
		
		boolean del_Data_isDown_whole=false;//3����Դϵͳ����del�Ƿ��������꣺ֻ��del_Data_isDown�����3����Ϊtrueʱ��del_Data_isDown_whole��Ϊtrue

		//--------------------------------------- end -------------------------------------------------------------
		int ODS_num=5;//Ҫ���ص���Դ���ݵ�ϵͳ���� ##**##
		String[] donwLoadSh = new String[ODS_num];
		//�ж�Զ����Դ�����Ƿ��·���� 
		       
		 String newSysDate = sysdate;
		 String filename = ds_conf[3].trim();
		 int count = 72;
		 while(count>0){
			 if (FtpClient.isFileExist(filename)) {
					System.out.println("��ȡ��һ�ν�ѹ����Ŀ¼......");
					String[] filelist = FtpClient.GetFileNames(filename);
					int source_count = ODS_num; 
					for (int j = 0; j < filelist.length; j++) {
						String typename = filelist[j].toString();
						if(typename.equals("CMS")||typename.equals("SAP")||typename.equals("SOP")||typename.equals("BIL")||typename.equals("NBS")){
							source_count --;
							if (FtpClient.isFileExist(filename + "/" + typename+"/")) {
								System.out.println("��ȡ"+typename+"����Ŀ¼......");
								String[] filelist1 = FtpClient.GetFileNames(filename + "/" + typename+"/");
								for (int k = 0; k < filelist1.length; k++) {
									String datename = filelist1[k].toString();
									if(datename.equals(newSysDate)){
										if (FtpClient.isFileExist(filename + "/" + typename + "/"+ datename+"/")) {
											System.out.println("��ȡ"+typename+"-"+datename+"Ŀ¼......");
											
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
														System.out.println("--��ʼ����ftp���������У�"+typename+"-"+datename+"-"+filelist2[i]+"...");
														donwLoadSh[source_count] += "\t\n  get "+filelist2[i];
														System.out.println("����"+typename+"-"+datename+"-"+filelist2[i]+"��ftp���������н�����");
													}
												}else{
													System.out.println(typename+datename+"��û��ok�ļ�");
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
					 if (del_Data_isDown_whole){//��Դ����del�ļ��Ѿ�ȫ���������(��־�ļ��Ѵ���)
							//2.(3/3)��ͨFTP��21�˿�
							FtpClient.disconnect();
							
						    //���ftp�ű�
							for(int i=0;i<donwLoadSh.length;i++){
										OutputStream os = new FileOutputStream("/home/db2inst1/ERDDDATA/ftp_batch/download/zl/donwload_"+i+".sh");
										byte[] by = donwLoadSh[i].getBytes();// �õ��ֽ����͵�����
										os.write(by);
										os.close();// �ֽ���û��ʹ�û�����
							}
							//ִ�����ؽű�
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
							String SuccessMarkFile_houzui="";//�������������ɵı�־�ļ��ĺ�׺;�ɹ�Ϊ��ok��ʧ��Ϊ��error
							//System.out.println("--------> /home/oracle/ftp_batch/markfile/"+MarkFileName+"�Ѿ����ڣ���ʼִ����������<����>����...");
							System.out.println("--------> ��Դ�����ļ�del���Ѿ��������");
							
					}
					return;
				}
				
				//��ӡ��Щ��Դϵͳ�������·��ˣ���Щû�·�
				System.out.println("���ݻ�ûȫ���·�@"+CommonFunctions.GetCurrentTime()+"��CMS:"+flagmap_download.containsKey("CMS"+sysdate)+"; SOP:"+flagmap_download.containsKey("SOP"+sysdate)
						           +"; SAP:"+flagmap_download.containsKey("SAP"+sysdate)+"; BIL:"+flagmap_download.containsKey("BIL"+sysdate)+"; NBS:"+flagmap_download.containsKey("NBS"+sysdate)+"��");
		        //ֹͣ10����
				System.out.println("��������10���Ӻ���ȥɨ��......");
				for(int i =10;i>0;i--)
				   {
					Thread.sleep(60*1000);
				   }
		         count--;
		}
	}
	
	/**
	 * ��ȡ��ǰϵͳʱ��
	 * @return  (String)  ��ǰϵͳʱ�� ��ʽΪ "��-��-�� ʱ:��:��"���� "2009-12-02 20:45:06"
	 */
	public static String GetCurrentTime(){
		/*
		//SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy'-'MM'-'dd' 'hh':'mm':'ss");
		DateFormat dateformat = DateFormat.getDateTimeInstance(); 
		Calendar date = Calendar.getInstance();
		String CurrentTime=dateformat.format(date.getTime());
		//System.out.println("######��ǰʱ��Ϊ�� "+CurrentTime);
		*/
		
		Calendar ca = Calendar.getInstance();
	    int year = ca.get(Calendar.YEAR);//��ȡ���
	    String Stryear=String.valueOf(year);
	    
	    int month=ca.get(Calendar.MONTH)+1;//��ȡ�·�  �·�ϵͳ��0��ʼ�����
	    String Strmonth=String.valueOf(month);
	    if(month<10){
	    	Strmonth="0"+Strmonth;
	    }
	    
	    int day=ca.get(Calendar.DATE);//��ȡ��
	    String Strday=String.valueOf(day);
	    if(day<10){
	    	Strday="0"+Strday;
	    }
	    
	    int hour=ca.get(Calendar.HOUR_OF_DAY);//Сʱ
	    String Strhour=String.valueOf(hour);
	    if(hour<10){
	    	Strhour="0"+Strhour;
	    }
	    
	    int minute=ca.get(Calendar.MINUTE);//��
	    String Strminute=String.valueOf(minute);
	    if(minute<10){
	    	Strminute="0"+Strminute;
	    }
	    
	    int second=ca.get(Calendar.SECOND);//��    
	    String Strsecond=String.valueOf(second);
	    if(second<10){
	    	Strsecond="0"+Strsecond;
	    }
	    
	    String CurrentTime=Stryear+"-"+Strmonth+"-"+Strday+" "+Strhour+":"+Strminute+":"+Strsecond;
	    return CurrentTime;
	}
	
	/**
	 * ��ȡͬһ������ʱ���ַ�����ʱ��� ����Ϊ��λ,  ʱ���ַ�����ʽΪ2009-12-02 20:45:06
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
	 * ��ȡ��ǰϵͳ����
	 * @return  (String)  ��ǰϵͳ���� ����ʽΪ "��-��-��"���� "2009-12-02"
	 */
	public static String GetCurrentDate(){
		/*
		//SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy'-'MM'-'dd' 'hh':'mm':'ss");
		DateFormat dateformat = DateFormat.getDateTimeInstance(); 
		Calendar date = Calendar.getInstance();
		String CurrentTime=dateformat.format(date.getTime());
		//System.out.println("######��ǰʱ��Ϊ�� "+CurrentTime);
		*/
		
		Calendar ca = Calendar.getInstance();
	    int year = ca.get(Calendar.YEAR);//��ȡ���
	    String Stryear=String.valueOf(year);
	    
	    int month=ca.get(Calendar.MONTH)+1;//��ȡ�·�  �·�ϵͳ��0��ʼ�����
	    String Strmonth=String.valueOf(month);
	    if(month<10){
	    	Strmonth="0"+Strmonth;
	    }
	    
	    int day=ca.get(Calendar.DATE);//��ȡ��
	    String Strday=String.valueOf(day);
	    if(day<10){
	    	Strday="0"+Strday;
	    }
	    
	    int hour=ca.get(Calendar.HOUR_OF_DAY);//Сʱ
	    String Strhour=String.valueOf(hour);
	    if(hour<10){
	    	Strhour="0"+Strhour;
	    }
	    
	    int minute=ca.get(Calendar.MINUTE);//��
	    String Strminute=String.valueOf(minute);
	    if(minute<10){
	    	Strminute="0"+Strminute;
	    }
	    
	    int second=ca.get(Calendar.SECOND);//��    
	    String Strsecond=String.valueOf(second);
	    if(second<10){
	    	Strsecond="0"+Strsecond;
	    }
	    
	    String CurrentDate=Stryear+"-"+Strmonth+"-"+Strday;
	    return CurrentDate;
	}

	 /**
     * ��������������Ľ��������������ʾdateStr1��dateStr2ǰ��dateStr1��ʽΪ"yyyy-mm-dd"
     * @param dateStr1 String  �����������ַ���
     * @param dateStr2 String ���������ַ���
     * @return int dateStr1-dateStr2������
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
	 * �޸��������� 
	 * @param baseDate ��׼����String (��ʽyyyy-MM-dd)
	 * @param amount Ҫ���ӵ�����������Ϊ����
	 * @return ��׼�������ӻ����������������
	 */
	public static String dateModify(String baseDate, int amount) {
		StringBuffer sb = new StringBuffer();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // ���ַ�����ʽ��
		Date dt = sdf.parse(baseDate, new ParsePosition(0)); // �ɸ�ʽ������ַ�������һ��Date����

		Calendar c = Calendar.getInstance(); // ��ʼ��һ��Calendar
		try {
			c.setTime(dt); // ���û�׼����
		} catch (Exception e) {
			System.out.println("��������" + baseDate + "��ʽ�����ⲻ���ϸ�ʽyyyy-MM-dd��");
			return null;
			// e.printStackTrace();
		}

		c.add(Calendar.DATE, amount); // ��Ҫ�Ӽ�������
		Date dt1 = c.getTime(); // ��Calendar�����û�׼���������������
		sb = sdf.format(dt1, sb, new FieldPosition(0)); // �õ�����ַ���

		return sb.toString();
	}
}
