package path;

/* 
 Authors: Kevin Dunne, Jekaterina Pavlenko
 Date: 7/4/19
 Program: Website for enterprise application development
 */
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@MultipartConfig(maxFileSize = 16177216) // upto 16 MB
@WebServlet(name = "AddNewMapActionDB", urlPatterns = { "/AddNewMapActionDB" })
public class AddNewMapActionDB extends HttpServlet {
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
	
	getRankPower rp = new getRankPower();

    public void init() throws ServletException
    {

    	SQLConnection connect = new SQLConnection();
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            // setup the connection with the DB
            conn = DriverManager.getConnection(connect.URL, connect.USERNAME, connect.PASSWORD);
            
            System.out.println("Connected");
        } catch (ClassNotFoundException | SQLException e)
        {
            System.err.println("Error 1" + e);
        }
    }

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		///////////////////////////////////////////
		rp.getStatusRank(request,response,stmt,conn);
		System.out.println(" rp.getUserNameRights() AddNewMapActionDB: "+ rp.getUserNameRights());
		
		org_name = request.getParameter("org_name");
		org_building = request.getParameter("org_building");
		map_name = request.getParameter("map_name");
		map_comments = request.getParameter("map_comments");
		InputStream inputStream = null;
		Part parts1 = request.getPart("map_image");
		
		System.out.println("part: "+parts1);

		if (parts1 != null) {
			System.out.println(parts1.getName());
			System.out.println(parts1.getSize());
			System.out.println(parts1.getContentType());
			inputStream = parts1.getInputStream();
		}
		try {
			prepStat = conn.prepareStatement("insert into maps values(? ,? ,? ,? ,? ,? )");
			prepStat.setInt(1, 0);
			prepStat.setString(2, org_name);
			prepStat.setString(3, org_building);
			prepStat.setString(4, map_name);
			prepStat.setString(5, map_comments);
			if (inputStream != null) {
				System.out.println("inputStream: "+inputStream.read());
				prepStat.setBlob(6, inputStream);
			}
			int i = prepStat.executeUpdate();
			response.sendRedirect("Maps.jsp");
		} catch (SQLException ex) {
			response.sendRedirect("UploadMapDB");
			System.err.println("inputStream: " + inputStream);
			System.err.println("Error 5: " + parts1.getInputStream());
			System.err.println("Error 6: " + parts1);
			System.err.println("Error 2: " + ex);
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
