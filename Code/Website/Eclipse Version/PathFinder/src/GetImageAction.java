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

@WebServlet(name = "GetImageAction", urlPatterns = { "/GetImageAction" })
public class GetImageAction extends HttpServlet {

	private static final int BUFFER_SIZE = 4096;
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

	int idRights;
	String userNameRights;
	String passwordRights;
	String emailRights;
	int AccountStatusRights;
	int special;
	String orgNameRights;

	String organisation_name;
	String organisation_address;
	String organisation_email;
	String organisation_mobile;
	String organisation_building_name;
	String user_org_name;

	String org_name;
	String org_building;
	String map_name;
	String map_comments;
	Blob map_image;

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
		// create a cookie array
		Cookie cookie = null;
		Cookie[] cookies = null;
		cookies = request.getCookies();
		// were going to loop through the cookies array and if the active cookies match
		// values in the database
		// we know we are that user in the database so were going to put there
		// information into some variables
		try {
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
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		String jsp_org_name = request.getParameter("orgNameRights");
		System.out.println("orgNameRights: "+orgNameRights);
		try {
			ResultSet rs1 = stmt.executeQuery(
					"select map_image from maps join organisation on maps.org_name = organisation.organisation_name where org_name = 'Limerick Institute of Technology'");
			String imgLen = "";
			if (rs1.next()) {
				imgLen = rs1.getString(1);
				System.out.println(imgLen.length());
			}
			rs1 = stmt.executeQuery(
					"select map_image from maps join organisation on maps.org_name = organisation.organisation_name where org_name = 'Limerick Institute of Technology'");
			if (rs1.next()) {
				int len = imgLen.length();
				byte[] rb = new byte[len];
				InputStream readImg = rs1.getBinaryStream(1);
				int index = readImg.read(rb, 0, len);
				System.out.println("index" + index);
				stmt.close();
				response.reset();
				response.setContentType("image/jpg");
				response.getOutputStream().write(rb, 0, len);
				response.getOutputStream().flush();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("Error 3: " + e);
		}
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
