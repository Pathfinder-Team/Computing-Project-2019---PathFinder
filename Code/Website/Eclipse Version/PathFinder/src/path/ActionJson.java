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

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

@MultipartConfig(maxFileSize = 16177216) // upto 16 MB
@WebServlet(name = "ActionJson", urlPatterns = { "/ActionJson" })
public class ActionJson extends HttpServlet {
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

	@SuppressWarnings("unchecked")
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		///////////////////////////////////////////
		rp.getStatusRank(request,response,stmt,conn);
		System.out.println(" rp.getUserNameRights() ActionJson: "+ rp.getUserNameRights());
		
		org_name = request.getParameter("org_name");
		
	    JSONObject jsonObject = new JSONObject();
	    JSONArray array = new JSONArray();
		
		try 
		{
		stmt = conn.createStatement();

		String sql5 = "select * from map_points join point_to on map_points.current_point_id=point_to.point_from_id";
		result = stmt.executeQuery(sql5);
		
	    
	      while(result.next())
	      {
	         JSONObject record = new JSONObject();
	         record.put("current_point_id", result.getInt("current_point_id"));
	         record.put("point_name", result.getString("point_name"));
	         record.put("maps_map_id", result.getInt("maps_map_id"));
	         record.put("point_from_id", result.getInt("point_from_id"));
	         record.put("point_to_id", result.getInt("point_to_id"));
	         record.put("point_weight", result.getInt("point_weight"));
	         record.put("point_direction", result.getString("point_direction"));
	         //System.out.println("Hello");
	         array.add(record);
	      }
	      jsonObject.put("map_points", array);
	     
	      /*
		  FileWriter file = new FileWriter("D:\\Documents\\GitHub\\Computing-Project-2019---PathFinder\\Code\\Website\\Eclipse Version\\PathFinder\\specialjson.json");
		  file.write(((JSONArray) array).toJSONString());
		  file.flush();
		  file.close();
		  */
		  
	     response.setContentType("application/json");
		 PrintWriter out = response.getWriter();
		
		  out.println(jsonObject.toJSONString()+"\n");
		  
		  } catch (IOException | SQLException e) {
			  e.printStackTrace();
		      System.out.println("Special Json error: "+e);
		  }
			

		}
	      //response.sendRedirect("Maps.jsp");

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
