import javax.servlet.annotation.*;
import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "RankPower", urlPatterns = { "/RankPower" })
public class RankPower extends HttpServlet {

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
	String powerOrgName;
	ResultSet result;

	static int idRights;
	static String userNameRights;
	static String passwordRights;
	static String emailRights;
	static int AccountStatusRights;
	static String orgNameRights;
	
	
	public RankPower()
	{
		this.orgNameRights = orgNameRights;
	}
	public void init() throws ServletException {
		String URL = "jdbc:mysql://remotemysql.com:3306/4eyg55o51S?autoReconnect=true&useSSL=false";
		String USERNAME = "4eyg55o51S";
		String PASSWORD = "ADRFyeBfRn";

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			// setup the connection with the DB
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			System.out.println("Connected");
		} catch (ClassNotFoundException | SQLException e) {
			System.err.println("Error 1" + e);
		}
	}

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//System.out.println("Super Response ");
		// create a cookie array
		Cookie cookie = null;
		Cookie[] cookies = null;
		cookies = request.getCookies();

		try {
			if (cookies != null) {
				for (int i = 0; i < cookies.length; i++) {
					cookie = cookies[i];
					stmt = conn.createStatement();
					String sql5 = "select * from users";
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
							response.sendRedirect("ControlDB");
						}
					}
				}
			}
		} catch (SQLException ex) {
			Logger.getLogger(ControlDB.class.getName()).log(Level.SEVERE, null, ex);
			System.err.println("Cookie Error" + ex);
		}
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
	

// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
	/**
	 * Handles the HTTP <code>GET</code> method.
	 *
	 * @param request  servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException      if an I/O error occurs
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * Handles the HTTP <code>POST</code> method.
	 *
	 * @param request  servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException      if an I/O error occurs
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * Returns a short description of the servlet.
	 *
	 * @return a String containing servlet description
	 */
	@Override
	public String getServletInfo() {
		return "Short description";
	}// </editor-fold>

}
