package app.riskapp.common;

import java.sql.*;

public class ORCLDBConnect {
	Connection con = null;
	Statement stmt = null;
	ResultSet rs = null;

	public ORCLDBConnect(String hostType) {
		try {

			
			// 连接SQl2000 jdbc
			/*
			Class.forName("com.microsoft.jdbc.sqlserver.SQLServerDriver");
			  con = DriverManager.getConnection(
			  "jdbc:sqlserver://222.28.54.123:1433;DatabaseName=lxgl" +
			  "", "sa","1");
			
			 */
              //连接SQl2000 odbc
			/**
			 * Class.forName("sun.jdbc.odbc.JdbcOdbcDriver"); con =
			 * DriverManager.getConnection("jdbc:odbc:1", "MIS_User",
			 * "MIS_User");
			 */

		
			 //连接SQl2005 jdbc
			
//			 Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//			 con = DriverManager.getConnection(
//			 "jdbc:sqlserver://127.0.0.1:1433;DatabaseName=GroundManager",   //GroundManager,RFIDPosition
//			 "LRGIS", "tudiziyrgrli");
			 
			//连接oracle jdbc
			if(hostType.equals("server")){
				Class.forName("oracle.jdbc.driver.OracleDriver");
				java.sql.DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			    con = DriverManager.getConnection("jdbc:oracle:thin:@10.10.111.201:1521:BIDB","risk", "risk");
			}else if(hostType.indexOf(".")>=0){
				Class.forName("oracle.jdbc.driver.OracleDriver");
				java.sql.DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			    con = DriverManager.getConnection("jdbc:oracle:thin:@"+hostType+":1521:RKDB","risk", "risk");
			}else{
				Class.forName("oracle.jdbc.driver.OracleDriver");
				java.sql.DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			    con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:ORCL","risk", "risk");
			}
			
		    
		    
			
			if (con != null) {
				System.out.println("ORACLE数据库连接成功，祝贺你！");
			}
			
			//stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			
		} catch (java.lang.ClassNotFoundException classnotfound) {
			classnotfound.printStackTrace();
		} catch (java.sql.SQLException sql) {
			sql.printStackTrace();
		}
	}

	public ResultSet executeQuery(String OSqlQuery) {
		try {
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			rs = stmt.executeQuery(OSqlQuery);
			//stmt.close();
		} catch (java.sql.SQLException sql) {
			sql.printStackTrace();
		}
		return rs;
	}

	public void executeUpdate(String OSqlUpdate) throws SQLException {
		
		stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
		stmt.executeUpdate(OSqlUpdate);
		//con.close();
		
	}

}
