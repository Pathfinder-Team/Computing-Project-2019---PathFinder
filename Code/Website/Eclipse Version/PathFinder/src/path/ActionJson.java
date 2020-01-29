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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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
	PreparedStatement prepStat1;

	int powerID;
	String powerUsername;
	String powerFirstName;
	String powerLastName;
	String powerPassword;
	String powerEmail;
	int powerStatus;
	ResultSet result;
	ResultSet result1;

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
	int counter = 0;
	ArrayList<Node> map_points_array = new ArrayList();
	ArrayList<Node> points_array = new ArrayList();
	
	
	
    public void init() throws ServletException
    {

    	SQLConnection connect = new SQLConnection();
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            // setup the connection with the DB
            conn = DriverManager.getConnection(connect.URL, connect.USERNAME, connect.PASSWORD);
            
            System.out.println("Connected ActionJson");
        } catch (ClassNotFoundException | SQLException e)
        {
            System.err.println("Error 1" + e);
        }
    }

	@SuppressWarnings("unchecked")
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException 
	{
		///////////////////////////////////////////
		rp.getStatusRank(request,response,stmt,conn);
		System.out.println(" rp.getUserNameRights() ActionJson: "+ rp.getUserNameRights());
		
		org_name = request.getParameter("org_name");
		org_building = request.getParameter("org_building");
		
		System.out.println("org_name: "+org_name);
		System.out.println("org_building: "+org_building);
		
		try 
		{
			
		prepStat = conn.prepareStatement("select * from map_points join maps on maps.map_id=map_points.maps_map_id where org_name = ? and org_building = ?");
		prepStat.setString(1, org_name);
		prepStat.setString(2, org_building);
		result = prepStat.executeQuery();
		map_points_array.clear();
	    while(result.next())
	    {
	    	Node edge = new Node(result.getInt("current_point_id"),result.getString("point_name"),result.getInt("maps_map_id"));
	    	map_points_array.add(edge);
	    }
	    
		prepStat1 = conn.prepareStatement("select point_id,point_from_id,point_to_id,point_weight,point_direction from point_to join map_points on point_to.point_from_id = map_points.current_point_id join maps on maps.map_id = map_points.maps_map_id where org_name = ? and org_building =?");
		prepStat1.setString(1, org_name);
		prepStat1.setString(2, org_building);
		result1 = prepStat1.executeQuery();
		
		points_array.clear();
	    while(result1.next())
	    {
	    	Node edge = new Node(
	    			result1.getInt("point_id"),
	    			result1.getInt("point_from_id"),
	    			result1.getInt("point_to_id"),
	    			result1.getInt("point_weight"),
	    			result1.getString("point_direction"));
	    	points_array.add(edge);
	    }
	    
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		    System.out.println("Special Json error: "+e);
		}

	    JSONObject jsonObject = new JSONObject();
	    JSONArray array = new JSONArray();
	    
	    //JSONArray array2 = new JSONArray();
	   // JSONObject record = new JSONObject();
	    
	    int size = map_points_array.size();
	    
	    try
	    {
	    	counter++;
	    	//if(jsonObject != 0)
		    for(int i = 0; i < size;i++)
		    {
		    	//System.out.println("I: "+i);
			    JSONArray array2 = new JSONArray();
			    JSONObject record = new JSONObject();
			    
			    //System.out.println("array2: "+array2.size());
			    //System.out.println("record: "+record.size());
			    
			    record.put("current_point_id", map_points_array.get(i).current_point_id);
			    record.put("point_name", map_points_array.get(i).point_name);
			    record.put("maps_map_id", map_points_array.get(i).maps_map_id);
	
			    for(int j = 0; j < points_array.size();j++)
			    {
			    	if(map_points_array.get(i).current_point_id == points_array.get(j).point_from_id)
			    	{
			    		JSONObject record2 = new JSONObject();
			    		record2.put("point_weight", points_array.get(j).point_weight);
					    record2.put("point_from_id", points_array.get(j).point_from_id);
					    record2.put("point_to_id", points_array.get(j).point_to_id);
					    record2.put("point_direction", points_array.get(j).point_direction);
					    record2.put("point_id", points_array.get(j).point_id);
					    // add array record to second array
				    	array2.add(record2);
			    	}
			    	// add the second array to the record
			    	record.put("special_points",array2);
			    }
			    // add total record to array
			    array.add(record);
		    }
		    // each array is a set of values
		    jsonObject.put("map_points", array);  
		    response.setContentType("application/json");
		    PrintWriter out = response.getWriter();	      
		    out.println(jsonObject.toJSONString()+"\n");  
		    }
		
	    catch (Exception e) 
	    {
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
