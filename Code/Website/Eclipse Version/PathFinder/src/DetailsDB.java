
/* 
 Authors: Kevin Dunne, Jekaterina Pavlenko

 Date: 7/4/19
 Program: Website for enterprise application development
 
 Note:
 ///
 xu9KHOXeU/ZpJnBw+2aPy5jhGnZ/fbzJbEApmaU85uC878wcpW6hvVN6gwFiOiz2UsX3VfZaJGfwPi2JIGLJ5w==
 
 MDOl8Eb0uwfBZKUB5KadIh919IJYvXd2K0NNDYVwlfm7eQtX3ifnvUg+b2VNAz+PdFPi+4RyBZ/Fu/DX+aa3jg==
 
 //
 https://vision-api-key-path.cognitiveservices.azure.com/
 
 
 //
 65d239a931bb4a938f8c59ca5a3cc8e2
 
 c1279b3b51a541ffab4dc7cde8425c09
 
 
 */
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "DetailsDB", urlPatterns = { "/DetailsDB" })
public class DetailsDB extends HttpServlet {

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

	@Override
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
		try {
			//System.out.println("get org details");
			prepStat = conn.prepareStatement("select " + "organisation_name, " + "organisation_address,"
					+ "organisation_email," + "organisation_mobile," + "organisation_building_name "
					+ "from organisation where organisation_name = ?");
			prepStat.setString(1, orgNameRights);
			result = prepStat.executeQuery();
			while (result.next()) {
				organisation_name = result.getString("organisation_name");
				organisation_address = result.getString("organisation_address");
				organisation_email = result.getString("organisation_email");
				organisation_mobile = result.getString("organisation_mobile");
				organisation_building_name = result.getString("organisation_building_name");
			}
		} catch (SQLException ex) {
			Logger.getLogger(ControlDB.class.getName()).log(Level.SEVERE, null, ex);
		}
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		// System.out.println("power : "+userOrgRights);
		// System.out.println("power right : "+organisation_name);
		// System.out.println("if before");
		if (organisation_name.equals(orgNameRights)) {
			out.println("<!doctype html>\n"
					+ "<!-- Author: Jekaterina Pavlenko, Kevin Dunne, Christopher Costelloe Date: 09/03/2019-->"
					+ "<html lang=\"en\">" 
					+ "<head>" + "<meta charset=\"UTF-8\">" 
					+ "<title>Organisation Page</title>"
					+ "<link rel=\"stylesheet\" type=\"text/css\" href=\"styles/mainstyle.css\" />"
					+ "<link rel=\"stylesheet\" type=\"text/css\" href=\"styles/forum.css\" />" 
					+ "</head>"
					+ "<body>" 
					+ "<div id=\"container\">"
					+ "<header>"
					+ "<img src=\"images/bn_header.png\" alt=\"\" >" 
					+ "</header>"
					+ "<nav id=\"menu\">" 
					+ "<ul>"
					+ "<li><a href=\"index.html\" >ABOUT US</a></li>"
					+ "<li><a href=\"register.html\" >REGISTER</a></li>"
					+ "<li><a href=\"login.html\" >LOGIN</a></li>"
					+ "<li><a href=\"contact.html\" >CONTACT US</a></li>"
					+ "</ul>"
					+ "</nav>");
			out.println("" + "<main>\r\n" 
					+ "                <section id=\"form\">\r\n"
					+ "                    <ul class=\"sign_login\">\r\n"
					+ "                        <li><a href=\"DetailsDB\" class=\"current\">DETAILS</a></li>\r\n"
					+ "                        <li><a href=\"Maps.jsp\">MAPS</a></li>\r\n"
					+ "                        <li><a href=\"LogOutDB\" >LOG OUT</a></li>\r\n"
					+ "						   <li><a href=\"ControlDB\" >Control</a></li>\r\n"
					+ "                    </ul>\r\n" + "                    <br>\r\n" + "                    <br>\r\n"
					+ "                    <p>Organisation Information:<p>\r\n" + ""
					+ "					   <br>"
					+ "						<form action=\"EditAction\">"
												+ "Organisation Name: " + organisation_name
												+ "<button type=\"submit\" >Edit Name</button>"
											+ "</form>"
											+ "<form>"
												+ "Organisation Address: " + organisation_address
												+ "<button type=\"submit\" >Edit Name</button>"
											+ "</form>"
											+ "<form>"
												+ "Organisation Email: " + organisation_email
												+ "<button type=\"submit\" >Edit Name</button>"
											+ "</form>"
											+ "<form>"
												+ "Organisation Contact Number: " + organisation_building_name
												+ "<button type=\"submit\" >Edit Name</button>"
											+ "</form>"
											+ "<form>"
												+ "Organisation Building Name: " + organisation_name
												+ "<button type=\"submit\" >Edit Name</button>"
											+ "</form>");
			out.println("                  <div class=\"clearfix\"></div>\r\n" + "\r\n"
					+ "                    <div style=\"padding:6px;\">\r\n" + "                        <br>\r\n"
					+ "                        <br>\r\n" + "                        <br>\r\n"
					+ "                        <br>\r\n" + "                </section>\r\n" + "            </main>");
			out.println("            <footer>\r\n" + "                <p>PathFinder project 2019</p>\r\n"
					+ "                <p>Authors: Kevin Dunne,Jekaterina Pavlenko & Christopher Costelloe</p>\r\n"
					+ "                <p><img src=\"images/maze_ic.png\" alt=\"\" ></p>\r\n"
					+ "            </footer>	\r\n" + "\r\n" + "        </div>\r\n" + "\r\n" + "    </body>\r\n"
					+ "</html>");
		}
		System.out.println("end");
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
