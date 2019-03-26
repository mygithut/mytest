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
			logger.info("开始插入数据");
			Session session=this.hibernateFactory.openSession();
			Transaction tran=session.beginTransaction();
			session.save(entity);
			tran.commit();
			logger.info("插入数据结束");
			return true;
		}
		catch (Exception e) {
			logger.info("插入数据异常："+e.getMessage());
		}
		return false;
	}
	
	/**
	 * 批量插入数据
	 * @param entity
	 * @return
	 */
	public boolean insert_s(List entityList){
		try{
			//System.out.println("!!!!!!!!!!!!");
			logger.info("开始插入数据S...");
			Session session=this.hibernateFactory.openSession();
			Transaction tran=session.beginTransaction();
			int onceCommitNum=5000;//一次提交记录数;
			for(int i=0;i<entityList.size();i++){
				if(entityList.get(i)==null){
					System.out.println("null对象的笔数序号："+i+"，跳过该对象！");
					continue;
				}
				session.save(entityList.get(i));//如果entityList.get(i)中有两个完全相同(包括存储地址)的对象时，只会insert一条进数据库。
				//entityList.set(i, null);//释放list循环定价过程中，该笔记录的所占内存(在list中该笔记录被使用后)；注意这里同时会将调用此方法的原List的数据清除掉(地址传递)
				
                if((i+1)%1000==0 || i==entityList.size()-1){
                	System.out.println("--------------------------------------------------"+(i+1));
				}
				if((i+1)%onceCommitNum==0 || i==entityList.size()-1){
					System.out.println("-------------------------------------------------事务提交...");
					tran.commit();
					session.clear();//提交一次后，清除session释放内存;
					System.out.println("-------------------------------------------------事务提交结束");
					if(i!=entityList.size()-1){
						tran=session.beginTransaction();
					}
				}			
			}			
			//session.close();
			logger.info("插入数据S结束");
			return true;
		}
		catch (Exception e) {
			logger.info("插入数据S异常："+e.getMessage());	
			e.printStackTrace();
		}
		return false;
	}
	
	

	/**
	 * 批量执行
	 * @param hsql
	 * @param values
	 * @return
	 */
	public boolean execute1_s(List<String> sqllist){
		int i=0;
		try{
			Session session=this.hibernateFactory.openSession();
			Transaction tran=session.beginTransaction();
			int onceCommitNum=5000;//一次提交记录数;
			for(i=0;i<sqllist.size();i++){
				SQLQuery localSQLQuery=session.createSQLQuery(sqllist.get(i).toString());
				localSQLQuery.executeUpdate();
				
                if((i+1)%100==0 || i==sqllist.size()-1){
                	System.out.println("-------------------------------------------------"+(i+1));
				}
				if((i+1)%onceCommitNum==0 || i==sqllist.size()-1){
					System.out.println("-------------------------------------------------事务提交...");
					tran.commit();
					System.out.println("-------------------------------------------------事务提交结束");
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
			logger.info("批量执行SQLs时" +
					"异常："+e.getMessage());	
		}
		return false;
	}
	
	
	public boolean update(Object entity){
		try{
			logger.info("开始更新数据");
			Session session=this.hibernateFactory.openSession();
			Transaction tran=session.beginTransaction();
			session.saveOrUpdate(entity);
			tran.commit();
			logger.info("更新数据结束");
			return true;
		}
		catch (Exception e) {
			logger.info("更新插入数据异常："+e.getMessage());	
		}
		return false;
	}
	
	
	public boolean execute(String hsql,Object[] values){
		try{
			logger.info("开始执行："+hsql);
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
			logger.info("执行HSQL异常："+e.getMessage());
		}
		return false;
	}
	
	/**
	 * 执行最原始的任何sql语句
	 */
	public boolean execute1(String sql){
		try{
			logger.info("开始执行："+sql);
			Session session=this.hibernateFactory.openSession();
			Transaction tran=session.beginTransaction();
			
			SQLQuery localSQLQuery = session.createSQLQuery(sql);
			
			localSQLQuery.executeUpdate();
			
			tran.commit();
			return true;
		}
		catch (Exception e) {
			logger.info("执行HSQL异常："+e.getMessage());
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
			logger.info("开始执行："+hsql);
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
			logger.info("执行HSQL异常："+e.getMessage());	
		}
		return new ArrayList();
	}
	
	
	public Object getBean(String hsql,Object[] values){
		try{
			logger.info("开始执行："+hsql);
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
			logger.info("执行HSQL异常："+e.getMessage());	
		}
		return null;
	}
	
	public List query(String hsql,Object[] values,int pageSize,int currentPage){
		pageSize=pageSize<1?12:pageSize;
		currentPage=currentPage<1?1:currentPage;
		try{
			logger.info("开始执行："+hsql);
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
			logger.info("执行HSQL异常："+e.getMessage());	
		}
		return new ArrayList();
	}
	
	//
	/**
	 * 执行最原始的sql查询语句
	 */
	public List query1(String sql)
	  {
	    Session localSession = null;
	    try
	    {
	      //logger.info("开始执行：" + sql);
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
	        logger.info("执行HSQL异常：" + localException.getMessage());
	        return null;
	    }
	  }
	
	public Object getUniqueResult(String hsql,Object[] values){
		try{
			logger.info("开始执行："+hsql);
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
			logger.info("执行HSQL异常："+e.getMessage());	
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
			buffer.append("首页&nbsp;");
			buffer.append("上一页&nbsp;");
		}
		else{
			buffer.append("<a href=\""+ pageName +"?page=1&rowsCount="+ rowsCount +"\">首页</a>&nbsp;");
			buffer.append("<a href=\""+ pageName +"?page="+ (currentPage-1) +"&rowsCount="+ rowsCount +"\">上一页</a>&nbsp;");
		}
		
		if(currentPage==pageCount){
			buffer.append("下一页&nbsp;");
			buffer.append("末页");
		}
		else{
			buffer.append("<a href=\""+ pageName +"?page="+ (currentPage+1) +"&rowsCount="+ rowsCount +"\">下一页</a>&nbsp;");
			buffer.append("<a href=\""+ pageName +"?page="+ pageCount +"&rowsCount="+ rowsCount +"\">末页</a>&nbsp;");
		}
		buffer.append("&nbsp;&nbsp;共检索出"+ rowsCount +"条数据，每页"+ pageSize +"条数据，页次<font color='red'>"+ currentPage+"</font>/"+ pageCount);
		
		//buffer.setLength(0);
		buffer.append("&nbsp;&nbsp;跳转到：");
		buffer.append("\r\n<select onchange=\"window.location.replace('"+ pageName + "?page='+this.value+'&rowsCount='+"+ rowsCount +")\">\r\n");
		for(int i=0;i<pageCount;i++){
			String selected="";
			if(i==currentPage-1){
				selected="selected";
			}
			buffer.append("<option "+ selected +" value=\""+ (i+1) +"\">第"+ (i+1) +"页</option>\r\n");
		}
		buffer.append("</select>");
		
		
		return buffer.toString();
	}
	
	
	public List sqlQuery(String sql,Class cls,Object[] values){
		try{
			logger.info("开始执行："+sql);
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
			logger.info("执行SQL异常："+e.getMessage());	
		}
		return new ArrayList();
	}
	
	
}
