package app.riskapp.common;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
public class DaoFactory {
	private static final Logger logger=Logger.getLogger(DaoFactory.class);
	
	private HibernateFactory hibernateFactory=HibernateFactory.newIntance();
	
	
	public boolean insert(Object entity){
		try{
			logger.info("��ʼ��������");
			Session session=this.hibernateFactory.openSession();
			Transaction tran=session.beginTransaction();
			session.save(entity);
			tran.commit();
			logger.info("�������ݽ���");
			return true;
		}
		catch (Exception e) {
			logger.info("���������쳣��"+e.getMessage());
		}
		return false;
	}
	
	/**
	 * ������������
	 * @param entity
	 * @return
	 */
	public boolean insert_s(List entityList){
		try{
			//System.out.println("!!!!!!!!!!!!");
			logger.info("��ʼ��������S...");
			Session session=this.hibernateFactory.openSession();
			Transaction tran=session.beginTransaction();
			int onceCommitNum=5000;//һ���ύ��¼��;
			for(int i=0;i<entityList.size();i++){
				if(entityList.get(i)==null){
					System.out.println("null����ı�����ţ�"+i+"�������ö���");
					continue;
				}
				session.save(entityList.get(i));//���entityList.get(i)����������ȫ��ͬ(�����洢��ַ)�Ķ���ʱ��ֻ��insertһ�������ݿ⡣
				//entityList.set(i, null);//�ͷ�listѭ�����۹����У��ñʼ�¼����ռ�ڴ�(��list�иñʼ�¼��ʹ�ú�)��ע������ͬʱ�Ὣ���ô˷�����ԭList�����������(��ַ����)
				
                if((i+1)%1000==0 || i==entityList.size()-1){
                	System.out.println("--------------------------------------------------"+(i+1));
				}
				if((i+1)%onceCommitNum==0 || i==entityList.size()-1){
					System.out.println("-------------------------------------------------�����ύ...");
					tran.commit();
					session.clear();//�ύһ�κ����session�ͷ��ڴ�;
					System.out.println("-------------------------------------------------�����ύ����");
					if(i!=entityList.size()-1){
						tran=session.beginTransaction();
					}
				}			
			}			
			//session.close();
			logger.info("��������S����");
			return true;
		}
		catch (Exception e) {
			logger.info("��������S�쳣��"+e.getMessage());	
			e.printStackTrace();
		}
		return false;
	}
	
	

	/**
	 * ����ִ��
	 * @param hsql
	 * @param values
	 * @return
	 */
	public boolean execute1_s(List<String> sqllist){
		int i=0;
		try{
			Session session=this.hibernateFactory.openSession();
			Transaction tran=session.beginTransaction();
			int onceCommitNum=5000;//һ���ύ��¼��;
			for(i=0;i<sqllist.size();i++){
				SQLQuery localSQLQuery=session.createSQLQuery(sqllist.get(i).toString());
				localSQLQuery.executeUpdate();
				
                if((i+1)%100==0 || i==sqllist.size()-1){
                	System.out.println("-------------------------------------------------"+(i+1));
				}
				if((i+1)%onceCommitNum==0 || i==sqllist.size()-1){
					System.out.println("-------------------------------------------------�����ύ...");
					tran.commit();
					System.out.println("-------------------------------------------------�����ύ����");
					if(i!=sqllist.size()-1){
						tran=session.beginTransaction();
					}
				}			
			}
			//session.close();;
			return true;
		}
		catch (Exception e) {
			System.out.println(sqllist.get(i).toString());
			e.printStackTrace();
			logger.info("����ִ��SQLsʱ" +
					"�쳣��"+e.getMessage());	
		}
		return false;
	}
	
	
	public boolean update(Object entity){
		try{
			logger.info("��ʼ��������");
			Session session=this.hibernateFactory.openSession();
			Transaction tran=session.beginTransaction();
			session.saveOrUpdate(entity);
			tran.commit();
			logger.info("�������ݽ���");
			return true;
		}
		catch (Exception e) {
			logger.info("���²��������쳣��"+e.getMessage());	
		}
		return false;
	}
	
	
	public boolean execute(String hsql,Object[] values){
		try{
			logger.info("��ʼִ�У�"+hsql);
			Session session=this.hibernateFactory.openSession();
			Transaction tran=session.beginTransaction();
			Query query=session.createQuery(hsql);
			
			if(values!=null){
				int size=values.length;
				for(int i=0;i<size;i++){
					query.setParameter(i,values[i]);
				}
			}
			query.executeUpdate();
			
			tran.commit();
			return true;
		}
		catch (Exception e) {
			logger.info("ִ��HSQL�쳣��"+e.getMessage());
		}
		return false;
	}
	
	/**
	 * ִ����ԭʼ���κ�sql���
	 */
	public boolean execute1(String sql){
		try{
			logger.info("��ʼִ�У�"+sql);
			Session session=this.hibernateFactory.openSession();
			Transaction tran=session.beginTransaction();
			
			SQLQuery localSQLQuery = session.createSQLQuery(sql);
			
			localSQLQuery.executeUpdate();
			
			tran.commit();
			return true;
		}
		catch (Exception e) {
			logger.info("ִ��HSQL�쳣��"+e.getMessage());
		}
		return false;
	}
	
	public boolean update(String hsql,Object[] values){
		return this.execute(hsql, values);
	}
	
	public boolean delete(String hsql,Object[] values){
		return this.execute(hsql, values);
	}
	
	public List query(String hsql,Object[] values){
		try{
			logger.info("��ʼִ�У�"+hsql);
			Session session=this.hibernateFactory.openSession();
			Query query=session.createQuery(hsql);
			if(values!=null){
				int size=values.length;
				for(int i=0;i<size;i++){
					query.setParameter(i,values[i]);
				}
			}
			return query.list();
		}
		catch (Exception e) {
			logger.info("ִ��HSQL�쳣��"+e.getMessage());	
		}
		return new ArrayList();
	}
	
	
	public Object getBean(String hsql,Object[] values){
		try{
			logger.info("��ʼִ�У�"+hsql);
			Session session=this.hibernateFactory.openSession();
			Query query=session.createQuery(hsql);
			if(values!=null){
				int size=values.length;
				for(int i=0;i<size;i++){
					query.setParameter(i,values[i]);
				}
			}
			List list=query.list();
			
			return list.size()>0?list.get(0):null;
		}
		catch (Exception e) {
			logger.info("ִ��HSQL�쳣��"+e.getMessage());	
		}
		return null;
	}
	
	public List query(String hsql,Object[] values,int pageSize,int currentPage){
		pageSize=pageSize<1?12:pageSize;
		currentPage=currentPage<1?1:currentPage;
		try{
			logger.info("��ʼִ�У�"+hsql);
			Session session=this.hibernateFactory.openSession();
			Query query=session.createQuery(hsql);
			if(values!=null){
				int size=values.length;
				for(int i=0;i<size;i++){
					query.setParameter(i,values[i]);
				}
			}
			query.setFirstResult((currentPage-1)*pageSize);
			query.setMaxResults(pageSize);
			return query.list();
		}
		catch (Exception e) {
			logger.info("ִ��HSQL�쳣��"+e.getMessage());	
		}
		return new ArrayList();
	}
	
	//
	/**
	 * ִ����ԭʼ��sql��ѯ���
	 */
	public List query1(String sql)
	  {
	    Session localSession = null;
	    try
	    {
	      //logger.info("��ʼִ�У�" + sql);
	      localSession = this.hibernateFactory.openSession();
	      SQLQuery localSQLQuery = localSession.createSQLQuery(sql);
	      /*if (paramArrayOfObject != null)
	      {
	        int i = paramArrayOfObject.length;
	        for (int j = 0; j < i; ++j)
	          localSQLQuery.setParameter(j, paramArrayOfObject[j]);
	      }*/
	      localSession.flush();
	      localSession.clear();
	      List localList = localSQLQuery.list();
	      return localList;
	    }
	    catch (Exception localException)
	    {
	    	localException.printStackTrace();
	        logger.info("ִ��HSQL�쳣��" + localException.getMessage());
	        return null;
	    }
	  }
	
	public Object getUniqueResult(String hsql,Object[] values){
		try{
			logger.info("��ʼִ�У�"+hsql);
			Session session=this.hibernateFactory.openSession();
			Query query=session.createQuery(hsql);
			if(values!=null){
				int size=values.length;
				for(int i=0;i<size;i++){
					query.setParameter(i,values[i]);
				}
			}
			return query.uniqueResult();
		}
		catch (Exception e) {
			logger.info("ִ��HSQL�쳣��"+e.getMessage());	
		}
		return null;
	}
	
	public PageUtil queryPage(String hsql,Object[] values, int pageSize, int currentPage, int rowsCount,String pageName){

		if(rowsCount<0){
			rowsCount=this.query(hsql, values).size();
		}
		pageSize=pageSize<1?12:pageSize;
		currentPage=currentPage<1?1:currentPage;
		int pageCount=rowsCount/pageSize;
		pageCount=pageCount<1?1:pageCount;
		pageCount=pageCount*pageSize<rowsCount?pageCount+1:pageCount;
		currentPage=currentPage>pageCount?pageCount:currentPage;
		
		List list=this.query(hsql, values, pageSize, currentPage);
		
		String pageLine=this.formartPageLine(pageSize, currentPage, rowsCount, pageName);
		return new PageUtil(list,pageLine);
	}
	
	private String formartPageLine(int pageSize, int currentPage, int rowsCount,String pageName){
		
		pageSize=pageSize<1?12:pageSize;
		currentPage=currentPage<1?1:currentPage;
		StringBuffer buffer=new StringBuffer();
		int pageCount=rowsCount/pageSize;
		pageCount=pageCount<1?1:pageCount;
		pageCount=pageCount*pageSize<rowsCount?pageCount+1:pageCount;
		currentPage=currentPage>pageCount?pageCount:currentPage;
		
		
		if(currentPage==1){
			buffer.append("��ҳ&nbsp;");
			buffer.append("��һҳ&nbsp;");
		}
		else{
			buffer.append("<a href=\""+ pageName +"?page=1&rowsCount="+ rowsCount +"\">��ҳ</a>&nbsp;");
			buffer.append("<a href=\""+ pageName +"?page="+ (currentPage-1) +"&rowsCount="+ rowsCount +"\">��һҳ</a>&nbsp;");
		}
		
		if(currentPage==pageCount){
			buffer.append("��һҳ&nbsp;");
			buffer.append("ĩҳ");
		}
		else{
			buffer.append("<a href=\""+ pageName +"?page="+ (currentPage+1) +"&rowsCount="+ rowsCount +"\">��һҳ</a>&nbsp;");
			buffer.append("<a href=\""+ pageName +"?page="+ pageCount +"&rowsCount="+ rowsCount +"\">ĩҳ</a>&nbsp;");
		}
		buffer.append("&nbsp;&nbsp;��������"+ rowsCount +"�����ݣ�ÿҳ"+ pageSize +"�����ݣ�ҳ��<font color='red'>"+ currentPage+"</font>/"+ pageCount);
		
		//buffer.setLength(0);
		buffer.append("&nbsp;&nbsp;��ת����");
		buffer.append("\r\n<select onchange=\"window.location.replace('"+ pageName + "?page='+this.value+'&rowsCount='+"+ rowsCount +")\">\r\n");
		for(int i=0;i<pageCount;i++){
			String selected="";
			if(i==currentPage-1){
				selected="selected";
			}
			buffer.append("<option "+ selected +" value=\""+ (i+1) +"\">��"+ (i+1) +"ҳ</option>\r\n");
		}
		buffer.append("</select>");
		
		
		return buffer.toString();
	}
	
	
	public List sqlQuery(String sql,Class cls,Object[] values){
		try{
			logger.info("��ʼִ�У�"+sql);
			Session session=this.hibernateFactory.openSession();
			SQLQuery query=session.createSQLQuery(sql);
			query.addEntity(cls);
			
			if(values!=null){
				int size=values.length;
				for(int i=0;i<size;i++){
					query.setParameter(i,values[i]);
				}
			}
			return query.list();
		}
		catch (Exception e) {
			logger.info("ִ��SQL�쳣��"+e.getMessage());	
		}
		return new ArrayList();
	}
	
	
}
