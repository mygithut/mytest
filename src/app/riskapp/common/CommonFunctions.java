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
 * 公共方法
 * @author wang
 *
 */
public class CommonFunctions {
	/**
	 * 数据库连接公用DaoFactory
	 */
	public static DaoFactory mydao=new DaoFactory();//###################
	//public static String ProjectPath=System.getProperty("user.dir");
	/**
	 * 数据库查询结果公用List
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
		System.out.println("w今天："+deltaDays2);
		
		long deltaDays=daysSubtract(GetCurrentDate(), "1985-03-06");
		System.out.println("j今天："+deltaDays);
		
		
		deltaDays=daysSubtract( "1986-06-19","1985-03-06");
		System.out.println("j-w："+deltaDays);
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
	
	//标准函数解释语句
	/**
	 * 获取当前系统时间字符串，并用于文件或文件夹的命名，所以不包括文件名不支持的字符
	 * @return  (String)  当前系统时间 格式为 "年月日-时分秒" ，如 20091202-204506
	 */
	public static String GetCurrentTimeInFileName(){
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
	    
	    String CurrentTimeInFileName=Stryear+Strmonth+Strday+"-"+Strhour+Strminute+Strsecond;
	    return CurrentTimeInFileName;
	}
	
	/**
	 * 获取当前数据库系统日期(8位长整型)，是从数据库系统参数表中查询获得
	 * @return  (long)  当前数据库系统时间 格式为 "年月日" ，20091202
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
	 * 获取当前系统日期(8位长整型)，是调用计算机系统函数获取计算机时间
	 * @return  (long)  当前系统时间 格式为 "年月日" ，20091202
	 */
	public static long GetCurrentDateInLong(){
		Calendar ca = Calendar.getInstance(); 
	    int year = ca.get(Calendar.YEAR);//获取年份
	    String Stryear=String.valueOf(year);
	    
	    int month=ca.get(Calendar.MONTH)+1;//获取月份
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
	    
	    String CurrentDateStr=Stryear+Strmonth+Strday;
	    long CurrentTimeInLong=Long.parseLong(CurrentDateStr);
	    return CurrentTimeInLong;
	}
	//标准函数解释语句
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
	
	/** 修改日期天数
	    * @param baseDate 基准日期String (格式yyyy-MM-dd)
	    * @param amount 要增加的天数（负数为减）
	    * @return 基准日期增加或减少若干天后的日期 
	    */
	    public static String dateModify(String baseDate,int amount )
	    {
	       StringBuffer sb = new StringBuffer();
	      
	       SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); // 将字符串格式化
	       Date dt=sdf.parse(baseDate,new ParsePosition(0)); // 由格式化后的字符串产生一个Date对象
	      
	       Calendar c = Calendar.getInstance(); // 初始化一个Calendar
	       try{
	    	    c.setTime(dt); // 设置基准日期
	       }catch(Exception e){
	    	   System.out.println("输入日期"+baseDate+"格式错误，这不符合格式yyyy-MM-dd！");
	    	   return null;
	    	   //e.printStackTrace();
	       }
	      
	       c.add(Calendar.DATE, amount); //你要加减的日期 
	       Date dt1=c.getTime(); // 从Calendar对象获得基准日期增减后的日期
	       sb=sdf.format(dt1,sb,new FieldPosition(0)); // 得到结果字符串

	       return sb.toString();
	    }

	    /**
	     * 按天数修改日期
	     * @param baseDateLong (long) 基准日期long 8位 (格式yyyyMMdd)
	     * @param changeDays  (int) 要增加的天数（负数为减）
	     * @return (long) 基准日期增加或减少若干天后的日期(格式yyyyMMdd)
	     */
	    public static long pub_base_deadlineD(long baseDateLong,int changeDays){   	
	    	long ResultDateLong=0;
	    	
	    	String baseDate=String.valueOf(baseDateLong);
            if(baseDate.length()!=8){
            	System.out.println("输入日期"+baseDate+"格式错误，这不符合格式yyyyMMdd！");
	    	    return 0;
	    	}
	    	StringBuffer sb = new StringBuffer();
	        
	        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd"); // 将字符串格式化
	        Date dt=sdf.parse(baseDate,new ParsePosition(0)); // 由格式化后的字符串产生一个Date对象
	       
	        Calendar c = Calendar.getInstance(); // 初始化一个Calendar
	        
	        try{
	    	    c.setTime(dt); // 设置基准日期
	        }catch(Exception e){
	    	    System.out.println("输入日期"+baseDate+"格式错误，这不符合格式yyyyMMdd！");
	    	    return 0;
	    	    //e.printStackTrace();
	        }
	        
	        c.add(Calendar.DATE, changeDays); //你要加减的日期 
	        Date dt1=c.getTime(); // 从Calendar对象获得基准日期增减后的日期
	        sb=sdf.format(dt1,sb,new FieldPosition(0)); // 得到结果字符串        
	        String ResultDateStr=sb.toString();
	        
	        ResultDateLong=Long.parseLong(ResultDateStr);
	        System.out.println(baseDateLong+"变化 "+changeDays+" 天后结果为:"+ResultDateLong);
	    	return ResultDateLong;
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
	     * 返回两日期相减的结果天数，负数表示dateLong1在dateLong2前。dateLong1格式为"yyyymm-d"
	     * @param dateStr1 long  被减数日期字符串
	     * @param dateStr2 long 减数日期字符串
	     * @return int dateLong1-dateLong2的天数
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
	     *   文件选择器
	     *   @param   DialogMode  (int)   文件选择器模式，是打开文件还是保存文件(0表打开，其他代表保存)  
	     *   @param   FileDescription   (String)   通俗文件名(如：ExcelFile)
	     *   @param   FileSuffix   (String)   文件后缀名(如：xls)
	     *   @param   parent   (Component)   该文件选择器窗体的父窗体  
	     *   @return  ExcelFilePath   (String)   选择结果的文件全路径,若选择“取消”则返回空字符串"" 
	     */ 
	 	public static String FileChooser(int DialogMode,String FileDescription, String FileSuffix,Component parent){
	 		JFileChooser fc=new JFileChooser();
	 		String DialogTitle="";
	 		if(DialogMode==0){
	 			DialogTitle="打开"+FileDescription;
	 		}else{
	 			DialogTitle="保存"+FileDescription;
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
	 			//System.out.println("要"+DialogTitle+"的路径为: "+ExcelFilePath);
	 			return ExcelFilePath;
	 		}else{
	 			return "";
	 		}
	 	}
	
		/**
		* 小数保留位数截取 并四舍五入,最高支持保留9位小数，大于9位时不做任何处理 直接返回
		* @param d (double) 要进行截位的小数
		* @param n (int) 保留的小数位数
		* @return 截位后的结果
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
				System.out.println("项目对应科目表里不存在项目 "+item_no);
				return null;
			}
			super_item_no=(String)list.get(0);
			return super_item_no;
		}
	    
	    /**
	     * 截屏
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
		 * 根据具体一个日期，判断其所属的期限类型【暂且只支持日、月、年三种】
		 * @param date (long) 8位长整型如：20111201
		 * @return String[] 返回其属于的期限类型字符串数组 <0:日；5:月；8：年>
		 */
		public static String[] getTermTypes(long date){
			Vector<String> v_term_types=new Vector<String>();//临时存储
			v_term_types.add("3");//肯定属于‘旬’期限类型
					
			//每周日的贴源数据 就要跑期限类型为‘周’的批量
			long stard_zhou_date=20110102;//一个周日的参照日期	
			/*int delta_days=daysSubtract(date, stard_zhou_date);
			if(delta_days%7==0){
				v_term_types.add("2");
			}*/
			
			//每月月底(即第二天为1号)的贴源数据  就要跑期限类型为‘月’的批量
			long next_date=pub_base_deadlineD(date, 1);
			if(next_date%100==1){
				v_term_types.add("5");
			}
			
			//每年年底(即第二天为1月1号)的贴源数据  就要跑期限类型为‘年’的批量
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
		
		    /** 修改日期天数
			    * @param baseDate 基准日期String (格式yyyyMMdd)
			    * @param amount 要增加的天数（负数为减）
			    * @return 基准日期增加或减少若干天后的日期 
			    */
			    
		    public static String dateModifyD(String baseDate,int amount)
		    {
		       StringBuffer sb = new StringBuffer();
		      
		       SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd"); // 将字符串格式化
		       Date dt=sdf.parse(baseDate,new ParsePosition(0)); // 由格式化后的字符串产生一个Date对象
		      
		       Calendar c = Calendar.getInstance(); // 初始化一个Calendar
		       try{
		    	    c.setTime(dt); // 设置基准日期
		       }catch(Exception e){
		    	   System.out.println("输入日期"+baseDate+"格式错误，这不符合格式yyyyMMdd！");
		    	   return null;
		    	   //e.printStackTrace();
		       }

		       c.add(Calendar.DATE, amount); //你要加减的日期 
		       Date dt1=c.getTime(); // 从Calendar对象获得基准日期增减后的日期
		       sb=sdf.format(dt1,sb,new FieldPosition(0)); // 得到结果字符串

		       return sb.toString();
		    }
		    
		    /** 修改如干月,并处理到月底
			 * @param baseDate 基准日期String (格式yyyyMMdd)
			 * @param amount 要增加的月（负数为减）
			 * @return 基准日期增加或减少若干月后的日期 
			*/
		    public static String dateModifyM(String baseDate,int amount)
		    {
		    	//处理到月底
				String date2_nm=dateModifyM_normal(baseDate, amount+1);		
			    long d=Long.valueOf(date2_nm);
			    d=d/100*100+1;
			    d=pub_base_deadlineD(d, -1);	       
		        return String.valueOf(d);
		    }
		    
		    /** 修改如干月
			 * @param baseDate 基准日期String (格式yyyyMMdd)
			 * @param amount 要增加的月（负数为减）
			 * @return 基准日期增加或减少若干月后的日期 
			*/
		    public static String dateModifyM_normal(String baseDate,int amount)
		    {
		       StringBuffer sb = new StringBuffer();
		      
		       SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd"); // 将字符串格式化
		       Date dt=sdf.parse(baseDate,new ParsePosition(0)); // 由格式化后的字符串产生一个Date对象
		      
		       Calendar c = Calendar.getInstance(); // 初始化一个Calendar
		       try{
		    	    c.setTime(dt); // 设置基准日期
		       }catch(Exception e){
		    	   System.out.println("输入日期"+baseDate+"格式错误，这不符合格式yyyyMMdd！");
		    	   return null;
		    	   //e.printStackTrace();
		       }
		      
		       c.add(Calendar.MONTH, amount); //你要加减的日期 
		       Date dt1=c.getTime(); // 从Calendar对象获得基准日期增减后的日期
		       sb=sdf.format(dt1,sb,new FieldPosition(0)); // 得到结果字符串
		       
		       
		       return sb.toString();
		    }
		    
		    /** 修改如干年
			 * @param baseDate 基准日期String (格式yyyyMMdd)
			 * @param amount 要增加的年（负数为减）
			 * @return 基准日期增加或减少若干年后的日期 
			*/
		    public static String dateModifyY(String baseDate,int amount)
		    {
		       StringBuffer sb = new StringBuffer();
		      
		       SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd"); // 将字符串格式化
		       Date dt=sdf.parse(baseDate,new ParsePosition(0)); // 由格式化后的字符串产生一个Date对象
		      
		       Calendar c = Calendar.getInstance(); // 初始化一个Calendar
		       try{
		    	    c.setTime(dt); // 设置基准日期
		       }catch(Exception e){
		    	   System.out.println("输入日期"+baseDate+"格式错误，这不符合格式yyyyMMdd！");
		    	   return null;
		    	   //e.printStackTrace();
		       }
		      
		       c.add(Calendar.YEAR, amount); //你要加减的日期 
		       Date dt1=c.getTime(); // 从Calendar对象获得基准日期增减后的日期
		       sb=sdf.format(dt1,sb,new FieldPosition(0)); // 得到结果字符串

		       return sb.toString();
		    }
		    
		    /**
		     * 返回两日期之间相差的月份 ----DateLong1-DateLong2之间相差的月份
		     * @param DateLong1 (long) 被减数日期long 8位 (格式yyyyMMdd)
		     * @param DateLong2 (long) 减数日期long 8位 (格式yyyyMMdd)
		     * @return  DateLong1-DateLong2之间相差的月份
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
			 * 根据周期类型与本期系统日期，获取下期系统日期---当前该方法暂时只支持 0:1天、3:旬、 5:月
			 * @param oldSysDate 【long】旧的系统日期
			 * @param termType 【String】期限类型--- 0:1天；1:5日；2:周；  3:旬；4:半月； 5:月；6:季；7:半年；8:年；
			 * @return 【long】newSysDate 下期系统日期
			 */
			public static long getNewSysDate_byTermType(long oldSysDate,String termType){
				long newSysDate=0;
				if("0".equals(termType)){//1天
					long next_day=CommonFunctions.pub_base_deadlineD(oldSysDate, 1);//第二天
					newSysDate=next_day;	
					
				}else if("1".equals(termType)){//5日
					
				}else if("2".equals(termType)){//周:每周日
					
				}else if("3".equals(termType)){//旬:每月10号、20号、月底
					long next_day=CommonFunctions.pub_base_deadlineD(oldSysDate, 1);//第二天
				    if(next_day%100==1){//如果当前系统日期为月末，则新系统日期为下月10号
				    	newSysDate=next_day-1+10;
				    }else if(oldSysDate%100==10){//如果当前系统日期为10号，则新系统日期为本月20号
				    	newSysDate=oldSysDate+10;
				    }else{//如果当前系统日期为20号，则新系统日期为本月末
				    	long xyc=CommonFunctions.pub_base_deadlineD(oldSysDate, 15)/100*100+1;//下下月初
				    	newSysDate=CommonFunctions.pub_base_deadlineD(xyc,-1);
				    }
				    
				}else if("4".equals(termType)){//半月：每月15号、月底
					
				}else if("5".equals(termType)){//月：每月底
					long d_next2M=CommonFunctions.pub_base_deadlineD(oldSysDate, 45);//下下月的某一天
				    d_next2M=d_next2M/100*100+1;//下下月的1号
				    newSysDate=CommonFunctions.pub_base_deadlineD(d_next2M, -1);//下月的月底：下下月的1号减1天
					
				}else if("6".equals(termType)){//季：每季末
					
				}else if("7".equals(termType)){//半年：6月底
					
				}else if("8".equals(termType)){//年：12月底
					
				}else{//其他 不支持类型， 默认返回第二天
					System.out.println("周期类型"+termType+"参数错误，按‘1天’周期类型返回下期系统日期");
					newSysDate=CommonFunctions.pub_base_deadlineD(oldSysDate, 1);//第二天;
				}
				return newSysDate;
			}
			
	    /*public static void main(String[] args) {
	    	//System.out.println(pub_base_deadlineD(20111321,-30));
	    	snapShot();
		}*/
}
