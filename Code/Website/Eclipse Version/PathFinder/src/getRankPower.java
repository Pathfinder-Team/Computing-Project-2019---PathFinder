import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class getRankPower 
{
	Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    Connection conn;
    Connection conn2;
    Statement stmt;
    PreparedStatement prepStat;

    int powerID;
    String powerUsername;
    String powerFirstName;
    String powerLastName;
    String powerPassword;
    String powerEmail;
    int powerStatus;
    ResultSet result;
    
    static int idRights;
	static String userNameRights;
	static String passwordRights;
	static String emailRights;
	static int AccountStatusRights;
	static String orgNameRights;
	
	public void getStatus(HttpServletRequest request, HttpServletResponse response,Statement stmt,Connection conn)
	{
		// create a cookie array
		Cookie cookie = null;
		Cookie[] cookies = null;
		cookies = request.getCookies();
		try {
			//
			//
			if (cookies != null) {
				for (int i = 0; i < cookies.length; i++) {
					cookie = cookies[i];
					stmt = conn.createStatement();
					String sql5 = "select user_id,user_name,password,account_rank_account_rank_id,email,organisation_name from users";
					result = stmt.executeQuery(sql5);
					while (result.next()) {
						String powerOrgName = result.getString("organisation_name");
						String powerUsername = result.getString("user_name");
						int powerID = result.getInt("user_id");
						String powerPassword = result.getString("password");
						int powerStatus = result.getInt("account_rank_account_rank_id");
						String powerEmail = result.getString("email");

						// if cookie username and username from the database match then we are this
						// record,
						// extremly important note!: all usernames are unique so they database cannot
						// contain 2 exact usernames
						if (powerUsername.equals(cookie.getValue())) {
							userNameRights = powerUsername;
							idRights = powerID;
							passwordRights = powerPassword;
							AccountStatusRights = powerStatus;
							emailRights = powerEmail;
							orgNameRights = powerOrgName;
						}
					}
				}
			}
		} catch (SQLException ex) {
			Logger.getLogger(ControlDB.class.getName()).log(Level.SEVERE, null, ex);
			System.err.println("Error 2" + ex);
		}
	}
	public int getStatusRank(HttpServletRequest request, HttpServletResponse response, Statement stmt,Connection conn)
	{
		// create a cookie array
		Cookie cookie = null;
		Cookie[] cookies = null;
		cookies = request.getCookies();
		try {
			//
			//
			if (cookies != null) {
				for (int i = 0; i < cookies.length; i++) {
					cookie = cookies[i];
					stmt = conn.createStatement();
					String sql5 = "select user_id,user_name,password,account_rank_account_rank_id,email,organisation_name from users";
					result = stmt.executeQuery(sql5);
					while (result.next()) {
						String powerOrgName = result.getString("organisation_name");
						String powerUsername = result.getString("user_name");
						int powerID = result.getInt("user_id");
						String powerPassword = result.getString("password");
						int powerStatus = result.getInt("account_rank_account_rank_id");
						String powerEmail = result.getString("email");

						// if cookie username and username from the database match then we are this
						// record,
						// extremly important note!: all usernames are unique so they database cannot
						// contain 2 exact usernames
						if (powerUsername.equals(cookie.getValue())) {
							userNameRights = powerUsername;
							idRights = powerID;
							passwordRights = powerPassword;
							AccountStatusRights = powerStatus;
							emailRights = powerEmail;
							orgNameRights = powerOrgName;
							setUserNameRights(powerUsername);
							setIdRights(powerID);
							setPasswordRights(powerPassword);
							setStatusRights(powerStatus);
							setEmailRights(powerEmail);
							setOrgRights(powerOrgName);
						}
					}
				}
			}
		} catch (SQLException ex) {
			Logger.getLogger(ControlDB.class.getName()).log(Level.SEVERE, null, ex);
			System.err.println("Error 2" + ex);
		}
		return 2;
	}
	
	// 1
	public static void setUserNameRights(String userNameRights)
	{
		userNameRights = userNameRights;
	}
	public String getUserNameRights()
	{
		return this.userNameRights; 
	}
	// 2
	public static void setIdRights(int powerID)
	{
		idRights = powerID;
	}
	public int getIdRights()
	{
		return this.idRights; 
	}
	// 3
	public static void setPasswordRights(String powerPassword)
	{
		passwordRights = powerPassword;
	}
	public String getPasswordRights()
	{
		return this.passwordRights; 
	}
	// 4
	public static void setStatusRights(int powerStatus)
	{
		AccountStatusRights = powerStatus;
	}
	public int getStatusRights()
	{
		return this.AccountStatusRights; 
	}
	// 5
	public static void setEmailRights(String powerEmail)
	{
		emailRights = powerEmail;
	}
	public String getEmailRights()
	{
		return this.emailRights; 
	}
	// 6
	public static void setOrgRights(String powerOrgName)
	{
		orgNameRights = powerOrgName;
	}
	public String getOrgRights()
	{
		return this.orgNameRights; 
	}
}
