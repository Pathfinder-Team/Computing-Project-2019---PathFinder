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
@WebServlet(name = "AddThings", urlPatterns = { "/AddThings" })
public class AddThings extends HttpServlet {
	Timestamp timestamp = new Timestamp(System.currentTimeMillis());
	Connection conn;
	Connection conn2;
	Statement stmt;
	PreparedStatement prepStat;

    String first_name;
    String last_name;
    String user_name;
    String password;
    String email;
    String old_username;
    int Account_Status_Users;

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
		rp.getStatusRank(request,response,stmt,conn);
		System.out.println(" rp.getUserNameRights() AddThings: "+ rp.getUserNameRights());
		
		
		String mapAction = "";
		mapAction = request.getParameter("mapAction");
		String userAction = "";
		userAction = request.getParameter("userAction");
		String pointsAction = "";
		pointsAction = request.getParameter("insertPoints");
		
		if(mapAction == null)
		{
			mapAction = "";
		}
		if(userAction == null)
		{
			userAction = "";
		}
		if(pointsAction == null)
		{
			pointsAction = "";
		}
		System.out.println("mapAction: "+mapAction);
		if(mapAction.equals("mapAction"))
		{
			org_name = rp.getOrgRights();
			org_building = request.getParameter("org_building");
			map_name = request.getParameter("map_name");
			map_comments = request.getParameter("map_comments");
			InputStream inputStream = null;
			Part parts1 = request.getPart("map_image");
	
			if (parts1 != null) {
				System.out.println(parts1.getName());
				System.out.println(parts1.getSize());
				System.out.println(parts1.getContentType());
				inputStream = parts1.getInputStream();
			}
			try {
				int counter = 0;
				
				System.out.println("counter : "+counter);
				System.out.println("org_name : "+org_name);
				System.out.println("org_building : "+org_building);
				System.out.println("map_name : "+map_name);
				System.out.println("map_comments : "+map_comments);
				System.out.println("inputStream : "+inputStream);
				
				prepStat = conn.prepareStatement("insert into maps values(? ,? ,? ,? ,? ,? )");
				prepStat.setInt(1, counter);
				prepStat.setString(2, org_name);
				prepStat.setString(3, org_building);
				prepStat.setString(4, map_name);
				prepStat.setString(5, map_comments);
				if (inputStream != null) {
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
		}else if(userAction.equals("userAction"))
		{
			Account_Status_Users = Integer.parseInt(request.getParameter("Account_Status_Users"));
	        first_name = request.getParameter("first_name");
	        last_name = request.getParameter("last_name");
	        user_name = request.getParameter("user_name");
	        password = request.getParameter("password");
	        email = request.getParameter("email");

	        System.out.println("Account_Status_Users " + Account_Status_Users);

	        try {
	            String query = "insert into users values(?, ?, ?, ?, ?, ?, ?, ?)";
	            prepStat = conn.prepareStatement(query);
	            prepStat.setInt(1, 0);
	            prepStat.setString(2, user_name);
	            prepStat.setString(3, last_name);
	            prepStat.setString(4, first_name);
	            prepStat.setString(5, password);
	            prepStat.setString(6, email);
	            prepStat.setTimestamp(7, timestamp);
	            prepStat.setInt(8, Account_Status_Users);
	            prepStat.executeUpdate();
	            // send us to the forum.html page once query done
	            response.sendRedirect("ControlDB");

	        } catch (IOException | SQLException e) {
	            System.err.println("Error 2 " + e);
	        }
			
		}
		else if(pointsAction.contentEquals("insertNode"))
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
		else if(pointsAction.contentEquals("insertPoints"))
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

	public void getPower(HttpServletRequest request) {

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
