package app.riskapp.common;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateFactory {
	private static HibernateFactory instance;
	
	private SessionFactory sessionFactory;
	
	public static HibernateFactory newIntance(){
		return HibernateFactory.instance==null?HibernateFactory.instance=new HibernateFactory():HibernateFactory.instance;
	}
	
	public HibernateFactory() {
		Configuration config=new Configuration().configure("/hibernate.cfg.xml");
		this.sessionFactory=config.buildSessionFactory();
	}
	
	public Session openSession(){
		if(this.sessionFactory==null){
			System.out.println("请通过静态方法newInstance()创建事例");
			return null;
		}
		return this.sessionFactory.openSession();
	}
}
