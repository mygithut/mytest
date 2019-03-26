package app.riskapp.util;
/**
 * 获取主键ID，使用时间+计数器方式实现
 * 
 * @author 孙红玉
 * 
 * @date 2012-11-21 下午03:43:09
 * 
 * @company 东华软件股份公司金融风险产品部
 */
public class IDUtil {

	public static IDUtil instance = null;

	private static final long ONE_STEP = 100;
	private static long lastTime = System.currentTimeMillis();//当前时间的毫秒数，产生一个当前的毫秒
	private static short count = 0;

	/**
	 * 获取单例对象
	 * 
	 * @return
	 */
	public synchronized static IDUtil getInstanse() {
		if (instance == null) {
			instance = new IDUtil();
		}
		return instance;
	}

	private IDUtil() {
	}

	/**
	 * 根据对象获取表主键
	 * 
	 * @param clazz
	 * @return
	 */
	public synchronized String getUID() {
		try {
			if (count == ONE_STEP) {
				boolean done = false;
				while (!done) {
					long now = System.currentTimeMillis();
					if (now == lastTime) {
						try {
							Thread.sleep(1);
						} catch (java.lang.InterruptedException e) {
						}
						continue;
					} else {
						lastTime = now;
						count = 0;
						done = true;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		String result = lastTime + "" + (count++);
		//System.out.println("主键ID="+result);
		return result;
	}

}
