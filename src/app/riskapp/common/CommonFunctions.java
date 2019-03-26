package app.riskapp.common;

import java.awt.AWTException;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.FieldPosition;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import app.riskapp.entity.ComSysParm;

/**
 * ��������
 * @author wang
 *
 */
public class CommonFunctions {
	/**
	 * ���ݿ����ӹ���DaoFactory
	 */
	public static DaoFactory mydao=new DaoFactory();//###################
	//public static String ProjectPath=System.getProperty("user.dir");
	/**
	 * ���ݿ��ѯ�������List
	 */
	public static List list=null;
	
	
	public  static void main(String[] args) throws Exception {
		/*String str="-ewrrwer-gffghfh";
		String[] strs=str.split("-");
		for(int i=0;i<strs.length;i++){
			System.out.println("strs["+i+"]:"+strs[i]);
		}*/
		System.out.println(dateModify(GetCurrentDate(), -30));
		
		String theDay=dateModify("1985-03-06",10000);
		System.out.println("j theDay:"+theDay);
		theDay=dateModify("1986-06-19",10000);
		System.out.println("w theDay:"+theDay);
		
		
		long deltaDays2=daysSubtract(GetCurrentDate(), "1986-06-19");
		System.out.println("w���죺"+deltaDays2);
		
		long deltaDays=daysSubtract(GetCurrentDate(), "1985-03-06");
		System.out.println("j���죺"+deltaDays);
		
		
		deltaDays=daysSubtract( "1986-06-19","1985-03-06");
		System.out.println("j-w��"+deltaDays);
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
	
	//��׼�����������
	/**
	 * ��ȡ��ǰϵͳʱ���ַ������������ļ����ļ��е����������Բ������ļ�����֧�ֵ��ַ�
	 * @return  (String)  ��ǰϵͳʱ�� ��ʽΪ "������-ʱ����" ���� 20091202-204506
	 */
	public static String GetCurrentTimeInFileName(){
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
	    
	    String CurrentTimeInFileName=Stryear+Strmonth+Strday+"-"+Strhour+Strminute+Strsecond;
	    return CurrentTimeInFileName;
	}
	
	/**
	 * ��ȡ��ǰ���ݿ�ϵͳ����(8λ������)���Ǵ����ݿ�ϵͳ�������в�ѯ���
	 * @return  (long)  ��ǰ���ݿ�ϵͳʱ�� ��ʽΪ "������" ��20091202
	 */
	public static long GetDBSysDate(){
		long SysDate=0;
		String sql="from ComSysParm order by sysDate DESC";
	    list=mydao.query(sql, null);
	    ComSysParm com_sys_parm=new ComSysParm();
	    if(list.size()==0){
	    	System.out.println("");
	    	return 0;
	    }
	    com_sys_parm=(ComSysParm)list.get(0);
	    SysDate=com_sys_parm.getSysDate();
	    return SysDate;
	}
	
	/**
	 * ��ȡ��ǰϵͳ����(8λ������)���ǵ��ü����ϵͳ������ȡ�����ʱ��
	 * @return  (long)  ��ǰϵͳʱ�� ��ʽΪ "������" ��20091202
	 */
	public static long GetCurrentDateInLong(){
		Calendar ca = Calendar.getInstance(); 
	    int year = ca.get(Calendar.YEAR);//��ȡ���
	    String Stryear=String.valueOf(year);
	    
	    int month=ca.get(Calendar.MONTH)+1;//��ȡ�·�
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
	    
	    String CurrentDateStr=Stryear+Strmonth+Strday;
	    long CurrentTimeInLong=Long.parseLong(CurrentDateStr);
	    return CurrentTimeInLong;
	}
	//��׼�����������
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
	
	/** �޸���������
	    * @param baseDate ��׼����String (��ʽyyyy-MM-dd)
	    * @param amount Ҫ���ӵ�����������Ϊ����
	    * @return ��׼�������ӻ���������������� 
	    */
	    public static String dateModify(String baseDate,int amount )
	    {
	       StringBuffer sb = new StringBuffer();
	      
	       SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); // ���ַ�����ʽ��
	       Date dt=sdf.parse(baseDate,new ParsePosition(0)); // �ɸ�ʽ������ַ�������һ��Date����
	      
	       Calendar c = Calendar.getInstance(); // ��ʼ��һ��Calendar
	       try{
	    	    c.setTime(dt); // ���û�׼����
	       }catch(Exception e){
	    	   System.out.println("��������"+baseDate+"��ʽ�����ⲻ���ϸ�ʽyyyy-MM-dd��");
	    	   return null;
	    	   //e.printStackTrace();
	       }
	      
	       c.add(Calendar.DATE, amount); //��Ҫ�Ӽ������� 
	       Date dt1=c.getTime(); // ��Calendar�����û�׼���������������
	       sb=sdf.format(dt1,sb,new FieldPosition(0)); // �õ�����ַ���

	       return sb.toString();
	    }

	    /**
	     * �������޸�����
	     * @param baseDateLong (long) ��׼����long 8λ (��ʽyyyyMMdd)
	     * @param changeDays  (int) Ҫ���ӵ�����������Ϊ����
	     * @return (long) ��׼�������ӻ����������������(��ʽyyyyMMdd)
	     */
	    public static long pub_base_deadlineD(long baseDateLong,int changeDays){   	
	    	long ResultDateLong=0;
	    	
	    	String baseDate=String.valueOf(baseDateLong);
            if(baseDate.length()!=8){
            	System.out.println("��������"+baseDate+"��ʽ�����ⲻ���ϸ�ʽyyyyMMdd��");
	    	    return 0;
	    	}
	    	StringBuffer sb = new StringBuffer();
	        
	        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd"); // ���ַ�����ʽ��
	        Date dt=sdf.parse(baseDate,new ParsePosition(0)); // �ɸ�ʽ������ַ�������һ��Date����
	       
	        Calendar c = Calendar.getInstance(); // ��ʼ��һ��Calendar
	        
	        try{
	    	    c.setTime(dt); // ���û�׼����
	        }catch(Exception e){
	    	    System.out.println("��������"+baseDate+"��ʽ�����ⲻ���ϸ�ʽyyyyMMdd��");
	    	    return 0;
	    	    //e.printStackTrace();
	        }
	        
	        c.add(Calendar.DATE, changeDays); //��Ҫ�Ӽ������� 
	        Date dt1=c.getTime(); // ��Calendar�����û�׼���������������
	        sb=sdf.format(dt1,sb,new FieldPosition(0)); // �õ�����ַ���        
	        String ResultDateStr=sb.toString();
	        
	        ResultDateLong=Long.parseLong(ResultDateStr);
	        System.out.println(baseDateLong+"�仯 "+changeDays+" �����Ϊ:"+ResultDateLong);
	    	return ResultDateLong;
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
	    	   dateStr1 = sdf.format(sdf.parse(dateStr1));
	    	   dateStr2 = sdf.format(sdf.parse(dateStr2));
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
	     * ��������������Ľ��������������ʾdateLong1��dateLong2ǰ��dateLong1��ʽΪ"yyyymm-d"
	     * @param dateStr1 long  �����������ַ���
	     * @param dateStr2 long ���������ַ���
	     * @return int dateLong1-dateLong2������
	     */
	    public static int daysSubtract(long dateLong1, long dateLong2) {
	    	  
	    	  String dateStr1 = String.valueOf(dateLong1);
	    	  String dateStr2 =String.valueOf(dateLong2);
	    	  
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
	     *   �ļ�ѡ����
	     *   @param   DialogMode  (int)   �ļ�ѡ����ģʽ���Ǵ��ļ����Ǳ����ļ�(0��򿪣�����������)  
	     *   @param   FileDescription   (String)   ͨ���ļ���(�磺ExcelFile)
	     *   @param   FileSuffix   (String)   �ļ���׺��(�磺xls)
	     *   @param   parent   (Component)   ���ļ�ѡ��������ĸ�����  
	     *   @return  ExcelFilePath   (String)   ѡ�������ļ�ȫ·��,��ѡ��ȡ�����򷵻ؿ��ַ���"" 
	     */ 
	 	public static String FileChooser(int DialogMode,String FileDescription, String FileSuffix,Component parent){
	 		JFileChooser fc=new JFileChooser();
	 		String DialogTitle="";
	 		if(DialogMode==0){
	 			DialogTitle="��"+FileDescription;
	 		}else{
	 			DialogTitle="����"+FileDescription;
	 		}
	 		final String FileSuffixLowerCase=FileSuffix.toLowerCase(),
	 		             FileSuffixUpperCase=FileSuffix.toUpperCase();
	 		final String Description=FileDescription+"s";
	 		fc.setDialogTitle(DialogTitle);
	 		fc.setFileFilter(new FileFilter() {
	 			public boolean accept(File f) {
	 				return f.isDirectory() || f.getPath().endsWith(FileSuffixLowerCase)
	 						|| f.getPath().endsWith(FileSuffixUpperCase);
	 			}

	 			public String getDescription() {
	 				return Description;
	 			}
	 		});
	 		int intRetVal=0;
	 		if(DialogMode==0){
	 			intRetVal=fc.showOpenDialog(parent);
	 		}else{
	 			intRetVal=fc.showSaveDialog(parent);
	 		}
	 			
	 		if(intRetVal==JFileChooser.APPROVE_OPTION){
	 			String ExcelFilePath=fc.getSelectedFile().getPath();
	 			//System.out.println("Ҫ"+DialogTitle+"��·��Ϊ: "+ExcelFilePath);
	 			return ExcelFilePath;
	 		}else{
	 			return "";
	 		}
	 	}
	
		/**
		* С������λ����ȡ ����������,���֧�ֱ���9λС��������9λʱ�����κδ��� ֱ�ӷ���
		* @param d (double) Ҫ���н�λ��С��
		* @param n (int) ������С��λ��
		* @return ��λ��Ľ��
		*/
	    public static double doublecut(double d,int n){
	    	if(d==Double.POSITIVE_INFINITY || d==Double.NEGATIVE_INFINITY ||d==Double.NaN){
				 return  Double.NaN;
			 }
			 
			 if(n>=10){
				 return d;
			 }
			 boolean isLowZero=false;
			 if(d<0){
				 d=-d;
				 isLowZero=true;
			 }
			 long jishu=(int)Math.pow(10, n);
		     long longd=(long)(d*jishu);
		     if(d*jishu>=(longd+0.5)){
		    	 longd++;
		     }
		     d=longd/(double)jishu;
		     
		     if(isLowZero){
		    	 d=-d;
		     }
		     return d;
	   }
	    
	    public static String getSuper_item_no(String item_no){
			String super_item_no="";
			String sql="select superItemNo from ItemToAcc where itemNo='"+item_no+"'";
			List list=mydao.query(sql, null);
			if(list.size()==0){
				System.out.println("��Ŀ��Ӧ��Ŀ���ﲻ������Ŀ "+item_no);
				return null;
			}
			super_item_no=(String)list.get(0);
			return super_item_no;
		}
	    
	    /**
	     * ����
	     */
	    public static void snapShot() {   
	        try {   
	            Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();   
	            BufferedImage screenshot = (new Robot()).createScreenCapture(   
	                    new Rectangle(0, 0, (int) dimension.getWidth(), (int) dimension.getHeight()));   
	      
	            String fileName ="E:/testdir/snapShotTest.jpg";   
	            File file = new File(fileName);  
	            System.out.println("1###########"+file.getName());
	            File parentFile = file.getParentFile();
	            //System.out.println("2###########"+parentFile.getName());
	            if (parentFile != null && !parentFile.exists()) {   
	                parentFile.mkdir();
	                System.out.println("3###########"+parentFile.getName());
	            }   
	      
	            ImageIO.write(screenshot, "jpg", file);   
	        } catch (AWTException e) {   
	            e.printStackTrace();   
	        } catch (IOException e) {   
	            e.printStackTrace();
	        }   
	    }
	    
	    /**
		 * ���ݾ���һ�����ڣ��ж����������������͡�����ֻ֧���ա��¡������֡�
		 * @param date (long) 8λ�������磺20111201
		 * @return String[] ���������ڵ����������ַ������� <0:�գ�5:�£�8����>
		 */
		public static String[] getTermTypes(long date){
			Vector<String> v_term_types=new Vector<String>();//��ʱ�洢
			v_term_types.add("3");//�϶����ڡ�Ѯ����������
					
			//ÿ���յ���Դ���� ��Ҫ����������Ϊ���ܡ�������
			long stard_zhou_date=20110102;//һ�����յĲ�������	
			/*int delta_days=daysSubtract(date, stard_zhou_date);
			if(delta_days%7==0){
				v_term_types.add("2");
			}*/
			
			//ÿ���µ�(���ڶ���Ϊ1��)����Դ����  ��Ҫ����������Ϊ���¡�������
			long next_date=pub_base_deadlineD(date, 1);
			if(next_date%100==1){
				v_term_types.add("5");
			}
			
			//ÿ�����(���ڶ���Ϊ1��1��)����Դ����  ��Ҫ����������Ϊ���ꡯ������
			next_date=pub_base_deadlineD(date, 1);
			if(next_date%10000==101){
				v_term_types.add("8");
			}
			
			String[] term_types=new String[v_term_types.size()];
			for(int i=0;i<term_types.length;i++){
				term_types[i]=v_term_types.get(i);
			}
			return term_types;
		}
		
		    /** �޸���������
			    * @param baseDate ��׼����String (��ʽyyyyMMdd)
			    * @param amount Ҫ���ӵ�����������Ϊ����
			    * @return ��׼�������ӻ���������������� 
			    */
			    
		    public static String dateModifyD(String baseDate,int amount)
		    {
		       StringBuffer sb = new StringBuffer();
		      
		       SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd"); // ���ַ�����ʽ��
		       Date dt=sdf.parse(baseDate,new ParsePosition(0)); // �ɸ�ʽ������ַ�������һ��Date����
		      
		       Calendar c = Calendar.getInstance(); // ��ʼ��һ��Calendar
		       try{
		    	    c.setTime(dt); // ���û�׼����
		       }catch(Exception e){
		    	   System.out.println("��������"+baseDate+"��ʽ�����ⲻ���ϸ�ʽyyyyMMdd��");
		    	   return null;
		    	   //e.printStackTrace();
		       }

		       c.add(Calendar.DATE, amount); //��Ҫ�Ӽ������� 
		       Date dt1=c.getTime(); // ��Calendar�����û�׼���������������
		       sb=sdf.format(dt1,sb,new FieldPosition(0)); // �õ�����ַ���

		       return sb.toString();
		    }
		    
		    /** �޸������,�������µ�
			 * @param baseDate ��׼����String (��ʽyyyyMMdd)
			 * @param amount Ҫ���ӵ��£�����Ϊ����
			 * @return ��׼�������ӻ���������º������ 
			*/
		    public static String dateModifyM(String baseDate,int amount)
		    {
		    	//�����µ�
				String date2_nm=dateModifyM_normal(baseDate, amount+1);		
			    long d=Long.valueOf(date2_nm);
			    d=d/100*100+1;
			    d=pub_base_deadlineD(d, -1);	       
		        return String.valueOf(d);
		    }
		    
		    /** �޸������
			 * @param baseDate ��׼����String (��ʽyyyyMMdd)
			 * @param amount Ҫ���ӵ��£�����Ϊ����
			 * @return ��׼�������ӻ���������º������ 
			*/
		    public static String dateModifyM_normal(String baseDate,int amount)
		    {
		       StringBuffer sb = new StringBuffer();
		      
		       SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd"); // ���ַ�����ʽ��
		       Date dt=sdf.parse(baseDate,new ParsePosition(0)); // �ɸ�ʽ������ַ�������һ��Date����
		      
		       Calendar c = Calendar.getInstance(); // ��ʼ��һ��Calendar
		       try{
		    	    c.setTime(dt); // ���û�׼����
		       }catch(Exception e){
		    	   System.out.println("��������"+baseDate+"��ʽ�����ⲻ���ϸ�ʽyyyyMMdd��");
		    	   return null;
		    	   //e.printStackTrace();
		       }
		      
		       c.add(Calendar.MONTH, amount); //��Ҫ�Ӽ������� 
		       Date dt1=c.getTime(); // ��Calendar�����û�׼���������������
		       sb=sdf.format(dt1,sb,new FieldPosition(0)); // �õ�����ַ���
		       
		       
		       return sb.toString();
		    }
		    
		    /** �޸������
			 * @param baseDate ��׼����String (��ʽyyyyMMdd)
			 * @param amount Ҫ���ӵ��꣨����Ϊ����
			 * @return ��׼�������ӻ���������������� 
			*/
		    public static String dateModifyY(String baseDate,int amount)
		    {
		       StringBuffer sb = new StringBuffer();
		      
		       SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd"); // ���ַ�����ʽ��
		       Date dt=sdf.parse(baseDate,new ParsePosition(0)); // �ɸ�ʽ������ַ�������һ��Date����
		      
		       Calendar c = Calendar.getInstance(); // ��ʼ��һ��Calendar
		       try{
		    	    c.setTime(dt); // ���û�׼����
		       }catch(Exception e){
		    	   System.out.println("��������"+baseDate+"��ʽ�����ⲻ���ϸ�ʽyyyyMMdd��");
		    	   return null;
		    	   //e.printStackTrace();
		       }
		      
		       c.add(Calendar.YEAR, amount); //��Ҫ�Ӽ������� 
		       Date dt1=c.getTime(); // ��Calendar�����û�׼���������������
		       sb=sdf.format(dt1,sb,new FieldPosition(0)); // �õ�����ַ���

		       return sb.toString();
		    }
		    
		    /**
		     * ����������֮�������·� ----DateLong1-DateLong2֮�������·�
		     * @param DateLong1 (long) ����������long 8λ (��ʽyyyyMMdd)
		     * @param DateLong2 (long) ��������long 8λ (��ʽyyyyMMdd)
		     * @return  DateLong1-DateLong2֮�������·�
		     */
		    public static int monthsSubtract(long DateLong1,long DateLong2){
		    	int m1=(int)(DateLong1/100%100);
		    	int m2=(int)(DateLong2/100%100);
		    	int y1=(int)(DateLong1/10000);
		    	int y2=(int)(DateLong2/10000);
		    	int delta_m=(y1-y2)*12+(m1-m2);
		    	return delta_m;
		    }

		    /**
			 * �������������뱾��ϵͳ���ڣ���ȡ����ϵͳ����---��ǰ�÷�����ʱֻ֧�� 0:1�졢3:Ѯ�� 5:��
			 * @param oldSysDate ��long���ɵ�ϵͳ����
			 * @param termType ��String����������--- 0:1�죻1:5�գ�2:�ܣ�  3:Ѯ��4:���£� 5:�£�6:����7:���ꣻ8:�ꣻ
			 * @return ��long��newSysDate ����ϵͳ����
			 */
			public static long getNewSysDate_byTermType(long oldSysDate,String termType){
				long newSysDate=0;
				if("0".equals(termType)){//1��
					long next_day=CommonFunctions.pub_base_deadlineD(oldSysDate, 1);//�ڶ���
					newSysDate=next_day;	
					
				}else if("1".equals(termType)){//5��
					
				}else if("2".equals(termType)){//��:ÿ����
					
				}else if("3".equals(termType)){//Ѯ:ÿ��10�š�20�š��µ�
					long next_day=CommonFunctions.pub_base_deadlineD(oldSysDate, 1);//�ڶ���
				    if(next_day%100==1){//�����ǰϵͳ����Ϊ��ĩ������ϵͳ����Ϊ����10��
				    	newSysDate=next_day-1+10;
				    }else if(oldSysDate%100==10){//�����ǰϵͳ����Ϊ10�ţ�����ϵͳ����Ϊ����20��
				    	newSysDate=oldSysDate+10;
				    }else{//�����ǰϵͳ����Ϊ20�ţ�����ϵͳ����Ϊ����ĩ
				    	long xyc=CommonFunctions.pub_base_deadlineD(oldSysDate, 15)/100*100+1;//�����³�
				    	newSysDate=CommonFunctions.pub_base_deadlineD(xyc,-1);
				    }
				    
				}else if("4".equals(termType)){//���£�ÿ��15�š��µ�
					
				}else if("5".equals(termType)){//�£�ÿ�µ�
					long d_next2M=CommonFunctions.pub_base_deadlineD(oldSysDate, 45);//�����µ�ĳһ��
				    d_next2M=d_next2M/100*100+1;//�����µ�1��
				    newSysDate=CommonFunctions.pub_base_deadlineD(d_next2M, -1);//���µ��µף������µ�1�ż�1��
					
				}else if("6".equals(termType)){//����ÿ��ĩ
					
				}else if("7".equals(termType)){//���꣺6�µ�
					
				}else if("8".equals(termType)){//�꣺12�µ�
					
				}else{//���� ��֧�����ͣ� Ĭ�Ϸ��صڶ���
					System.out.println("��������"+termType+"�������󣬰���1�졯�������ͷ�������ϵͳ����");
					newSysDate=CommonFunctions.pub_base_deadlineD(oldSysDate, 1);//�ڶ���;
				}
				return newSysDate;
			}
			
	    /*public static void main(String[] args) {
	    	//System.out.println(pub_base_deadlineD(20111321,-30));
	    	snapShot();
		}*/
}
