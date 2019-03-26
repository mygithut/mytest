package app.riskapp.common;

/**
 * 常用基础数学方法公共类
 * @author wang
 *
 */
public class CommonMath {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*int n=100;//股票数 
		double[] X=produceRandomNumGroup_ZT(0,1,100);
		for(int i=0;i<X.length;i++){
			System.out.println(X[i]);
		}*/
		/*double[] data=produceRandomNumGroup_ZT(0,1,10);
		for(int i=0;i<data.length;i++){
			System.out.println(data[i]);
		}
		System.out.println("#########从小到大排序：############");
		double[] data_smallToLarge=smallToLarge(data);
		for(int i=0;i<data_smallToLarge.length;i++){
			System.out.println(data_smallToLarge[i]);
		}*/
		
		//System.out.println(doublecut(-1.0/0, 4));
		System.out.println(binaryToLong("101"));
		/*for(int i=0;i<100;i++){
			System.out.println(produceRandomNum_JY(-10000, 1000));
		}*/

	}
	/**
	 * 产生一个符合正态分布的随机数，标准正态分布的平均数为0，方差为1
	 * @param miu  平均数
	 * @param sigma2 方差
	 * @return x double 一个符合正态分布的随机数
	 */
	public static double produceRandomNum_ZT(double miu,double sigma2){
		double N = 12;
		double x=0,temp=N;
		
		for(int i=0;i<N;i++){
			x=x+(Math.random());
		}
		
		x=(x-temp/2)/(Math.sqrt(temp/12));
		x=miu+x*Math.sqrt(sigma2);
		return x;

	}
	
	/**
	 * 产生一个符合正态分布的随机数序列，标准正态分布的平均数为0，方差为1
	 * @param miu  期望
	 * @param sigma2 方差
	 * @param n 序列长度
	 * @return X double[] 一个符合正态分布的随机数序列
	 */
	public static double[] produceRandomNumGroup_ZT(double miu,double sigma2,int n){
		double N = 12;
		double x=0,temp=N;
		double[] X= new double[n];
		for(int j=0;j<n;j++){
			x=0;
		    for(int i=0;i<N;i++){
		    	x=x+(Math.random());
		    }
		    x=(x-temp/2)/(Math.sqrt(temp/12));
		    x=miu+x*Math.sqrt(sigma2);
		    X[j]=x;
		}
		
		 return X;

	}
	
	/**
	 * 产生一个符合泊松分布的随机数
	 * @param Lamda 泊松分布的期望
	 * @return x(double) 一个符合泊松分布的随机数
	 */
	public static double produceRandomNum_PS(double Lamda){// 泊松分布
		double x=0,b=1,c=Math.exp(-Lamda),u; 
		do{
			u=Math.random();
		    b *=u;
		    if(b>=c){
		    	x++;
		    }
		}while(b>=c);
		return x;
	}
	/**
	 * 产生一个指定区间内的符合均匀分布的随机数 【包含指定区间左端点,但不包含右端点】
	 * @param Low (double) 指定区间下限
	 * @param Upper (double) 指定区间上限
	 * @return (double) 结果随机数
	 */
	public static double produceRandomNum_JY(double Low,double Upper){// 均匀分布
		double result=Double.NaN;
		result=Low+Math.random()*(Upper-Low);
		return result;
	}
	
	/**
	 * 将一数组从小到大排列 (冒泡法)
	 * @param data
	 * @return data_smallToLarge double[] 从小到大排列后的数组
	 */
	public static double[] smallToLarge(double[] data){
		int n=data.length;
		double temp=0;
		for(int i=0;i<n-1;i++){
			for(int j=0;j<n-1-i;j++){
				if(data[j]>data[j+1]){
					temp=data[j+1];
					data[j+1]=data[j];
					data[j]=temp;
			    }
			}
			
		}
		double[] data_smallToLarge=data;
		return data_smallToLarge;
		
	}
	
	/**
	 * 将一数组从大到小排列(沉底法)
	 * @param data
	 * @return data_largeToSmall double[] 从大到小排列后的数组
	 */
	public static double[] largeToSmall(double[] data){
		int n=data.length;
		double temp=0;
		for(int i=0;i<n-1;i++){
			for(int j=0;j<n-1-i;j++){
				if(data[j]<data[j+1]){
					temp=data[j+1];
					data[j+1]=data[j];
					data[j]=temp;
			    }
			}
			
		}
		double[] data_largeToSmall=data;
		return data_largeToSmall;
		
	}
	
	
	
	/**
	* 小数保留位数截取 并四舍五入,最高支持保留9位小数，大于9位时不做任何处理 直接返回
	* @param d (double) 要进行截位的小数，
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
	     if(d==0){
	    	 d=0;
	     }
	     return d;
	 }

	 /**
	  * 求以base为底数，value为真数的对数值
	  * @param base 底数
	  * @param value 真数
	  * @return logA double 以base为底数，value为真数的对数值
	  */
	 public static double logA(double base,double value){
		 return Math.log(value)/Math.log(base);
	 }
	 
	 /**
	  * 将二进制字符串转换为十进制整数,若输入字符串有大于1的数，则均视为0
	  * @param binary (String)要进行转换的二进制字符串
	  * @return (long)转换结果十进制整数
	  */
	 public static long binaryToLong(String binary){
			long result=0;
			char[] binary_char=binary.toCharArray();
			for(int i=binary_char.length-1;i>=0;i--){
				if(binary_char[i]=='1'){
					result+=Math.pow(2, binary_char.length-1-i);
			    }
			}
			return result;
	 }


}
