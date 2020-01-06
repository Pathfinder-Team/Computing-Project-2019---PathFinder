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
@WebServlet(name = "AddNewPointsActionDB", urlPatterns = { "/AddNewPointsActionDB" })
public class AddNewPointsActionDB extends HttpServlet {
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
		System.out.println(" rp.getUserNameRights() AddNewPointsActionDB: "+ rp.getUserNameRights());
		
		
		String insertPoints = request.getParameter("insertPoints");
		
		if(insertPoints.contentEquals("insertNode"))
		{
			int current_point_id = Integer.parseInt(request.getParameter("current_point_id"));
			String point_name = request.getParameter("point_name");
			int maps_map_id = Integer.parseInt(request.getParameter("maps_map_id"));
			
			System.out.println("maps_map_id "+maps_map_id);
			
			try {
				prepStat = conn.prepareStatement("insert into map_points values(? ,? ,?)");
				prepStat.setInt(1, current_point_id);
				prepStat.setString(2, point_name);
				prepStat.setInt(3, maps_map_id);
				prepStat.executeUpdate();
				response.sendRedirect("ViewPointsDB");
				
			} catch (SQLException ex) {
				Logger.getLogger(ControlDB.class.getName()).log(Level.SEVERE, null, ex);
				System.err.println("Error 2: " + ex);
			}
			
			
		}
		else if(insertPoints.contentEquals("insertPoints"))
		{
			int point_from_id = Integer.parseInt(request.getParameter("current_point_id"));
			int point_to_id = Integer.parseInt(request.getParameter("point_to_id"));
			int point_weight = Integer.parseInt(request.getParameter("point_weight"));
			String point_direction = request.getParameter("point_direction");
			
			try {
				prepStat = conn.prepareStatement("insert into map point_to values(? ,? ,?, ?)");
				prepStat.setInt(1, point_from_id);
				prepStat.setInt(2, point_to_id);
				prepStat.setInt(3, point_weight);
				prepStat.setString(4, point_direction);
				prepStat.executeUpdate();
				response.sendRedirect("ViewPointsDB");				
			} catch (SQLException ex) {
				Logger.getLogger(ControlDB.class.getName()).log(Level.SEVERE, null, ex);
				System.err.println("Error 2: " + ex);
			}
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
