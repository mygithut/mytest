package app.riskapp.common;

/**
 * ���û�����ѧ����������
 * @author wang
 *
 */
public class CommonMath {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*int n=100;//��Ʊ�� 
		double[] X=produceRandomNumGroup_ZT(0,1,100);
		for(int i=0;i<X.length;i++){
			System.out.println(X[i]);
		}*/
		/*double[] data=produceRandomNumGroup_ZT(0,1,10);
		for(int i=0;i<data.length;i++){
			System.out.println(data[i]);
		}
		System.out.println("#########��С��������############");
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
	 * ����һ��������̬�ֲ������������׼��̬�ֲ���ƽ����Ϊ0������Ϊ1
	 * @param miu  ƽ����
	 * @param sigma2 ����
	 * @return x double һ��������̬�ֲ��������
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
	 * ����һ��������̬�ֲ�����������У���׼��̬�ֲ���ƽ����Ϊ0������Ϊ1
	 * @param miu  ����
	 * @param sigma2 ����
	 * @param n ���г���
	 * @return X double[] һ��������̬�ֲ������������
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
	 * ����һ�����ϲ��ɷֲ��������
	 * @param Lamda ���ɷֲ�������
	 * @return x(double) һ�����ϲ��ɷֲ��������
	 */
	public static double produceRandomNum_PS(double Lamda){// ���ɷֲ�
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
	 * ����һ��ָ�������ڵķ��Ͼ��ȷֲ�������� ������ָ��������˵�,���������Ҷ˵㡿
	 * @param Low (double) ָ����������
	 * @param Upper (double) ָ����������
	 * @return (double) ��������
	 */
	public static double produceRandomNum_JY(double Low,double Upper){// ���ȷֲ�
		double result=Double.NaN;
		result=Low+Math.random()*(Upper-Low);
		return result;
	}
	
	/**
	 * ��һ�����С�������� (ð�ݷ�)
	 * @param data
	 * @return data_smallToLarge double[] ��С�������к������
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
	 * ��һ����Ӵ�С����(���׷�)
	 * @param data
	 * @return data_largeToSmall double[] �Ӵ�С���к������
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
	* С������λ����ȡ ����������,���֧�ֱ���9λС��������9λʱ�����κδ��� ֱ�ӷ���
	* @param d (double) Ҫ���н�λ��С����
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
	     if(d==0){
	    	 d=0;
	     }
	     return d;
	 }

	 /**
	  * ����baseΪ������valueΪ�����Ķ���ֵ
	  * @param base ����
	  * @param value ����
	  * @return logA double ��baseΪ������valueΪ�����Ķ���ֵ
	  */
	 public static double logA(double base,double value){
		 return Math.log(value)/Math.log(base);
	 }
	 
	 /**
	  * ���������ַ���ת��Ϊʮ��������,�������ַ����д���1�����������Ϊ0
	  * @param binary (String)Ҫ����ת���Ķ������ַ���
	  * @return (long)ת�����ʮ��������
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
