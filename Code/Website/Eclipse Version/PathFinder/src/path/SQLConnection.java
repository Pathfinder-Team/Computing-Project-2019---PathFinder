package path;

public class SQLConnection {
	
    String URL = "jdbc:mysql://remotemysql.com:3306/4eyg55o51S?autoReconnect=true&useSSL=false";
    String USERNAME = "4eyg55o51S";
    String PASSWORD = "ADRFyeBfRn";
	
    /*
    String URL = "jdbc:mysql://sql2.freemysqlhosting.net:3306/sql2317892?autoReconnect=true&useSSL=false";
    String USERNAME = "sql2317892";
    String PASSWORD = "password";
    */
    
	public SQLConnection()
	{
		
	}
	
	public String URL()
	{
		return URL;
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
