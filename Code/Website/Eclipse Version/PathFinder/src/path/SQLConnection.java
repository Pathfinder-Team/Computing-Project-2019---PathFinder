package path;

public class SQLConnection {
	
	// for college
	/*
    String URL = "jdbc:mysql://localhost:3306/4eyg55o51s?autoReconnect=true&useSSL=false";
    String DB = "4eyg55o51s";
    String USERNAME = "root";
    String PASSWORD = "password";
    */
	/*
	// for home
    String URL = "jdbc:mysql://localhost:3306/4eyg55o51s?autoReconnect=true&useSSL=false";
    String DB = "4eyg55o51s";
    String USERNAME = "root";
    String PASSWORD = "";
    */

   
    // for internet
    
	String URL = "jdbc:mysql://remotemysql.com:3306/4eyg55o51S?autoReconnect=true&useSSL=false";
	String DB = "4eyg55o51S";
    String USERNAME = "4eyg55o51S";
    String PASSWORD = "ADRFyeBfRn";
    
    
	public SQLConnection()
	{
		
	}
	
	public String URL()
	{
		return URL;
	}
	public String DB()
	{
		return DB;
	}
	public String USERNAME()
	{
		return USERNAME;
	}
	public String PASSWORD()
	{
		return PASSWORD;
	}

}
