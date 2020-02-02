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

/**
 *
 * @author: Kevin Dunne,Jekaterina Pavlenko
 */
@WebServlet(name = "DeleteDB", urlPatterns =
{
    "/DeleteDB"
})
public class DeleteDB extends HttpServlet
{

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
    int AccountStatusRights;

    Connection conn;
    PreparedStatement prepStat;
    Statement stmt;
    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    
    String triggerIdNode = "";
    String triggerIdPoint = "";
    String triggerIdOrgBuilding = "";

    @Override
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
            throws ServletException, IOException
    {
    	
    	
    	if(request.getParameter("TriggerNode") != null)
    	{
	    	// value NodeDelete
	        triggerIdNode = request.getParameter("TriggerNode");
	        System.out.println("triggerIdNode: "+triggerIdNode);
    	}
    	else if (request.getParameter("TriggerPoint") != null)
    	{
	        // value PointDelete
	        triggerIdPoint = request.getParameter("TriggerPoint");
	        System.out.println("triggerIdPoint: "+triggerIdPoint);
    	}
    	else if(request.getParameter("TriggerOrg") != null)
    	{
	        // OrgDelete
	        triggerIdOrgBuilding = request.getParameter("TriggerOrgBuilding");
	        System.out.println("triggerIdOrgBuilding: "+triggerIdOrgBuilding);
    	}
        
        if(triggerIdPoint.equals("PointDelete"))
        {
	        try
	        {
	        	String point_id = request.getParameter("point_id");
	        	System.out.println("point_id: "+point_id);
	            String query = "delete from point_to where point_id = ?";
	            prepStat = conn.prepareStatement(query);
	            prepStat.setString(1, point_id);
	            prepStat.executeUpdate();
	            
	            response.sendRedirect("ViewPointsDB");
	
	        } catch (IOException | SQLException e)
	        {
	            System.err.println("Error 2 " + e);
	            response.sendRedirect("ChangeDetailsDB");
	        }
        }
        else if(triggerIdNode.equals("NodeDelete"))
        {
	        try
	        {
	        	String current_point_id = request.getParameter("current_point_id");
	        	System.out.println("current_point_id: "+current_point_id);
	        	
	            String query = "delete from map_points where current_point_id = ?";
	            prepStat = conn.prepareStatement(query);
	            prepStat.setString(1, current_point_id);
	            prepStat.executeUpdate();
	            
	            response.sendRedirect("ViewPointsDB");
	
	        } catch (IOException | SQLException e)
	        {
	            System.err.println("Error 2 " + e);
	            response.sendRedirect("ChangeDetailsDB");
	        }
        }
        else if(triggerIdOrgBuilding.equals("BuildingDelete"))
        {
	        try
	        {
	        	String map_id = request.getParameter("map_id");
	        	String org_building = request.getParameter("org_building");
	        	
	        	System.out.println("map_id: "+map_id);
	        	System.out.println("org_building: "+org_building);
	        	
	            String query = "delete from maps where map_id = ? and org_building = ?";
	            prepStat = conn.prepareStatement(query);
	            prepStat.setString(1, map_id);
	            prepStat.setString(2, org_building);
	            prepStat.executeUpdate();
	            
	            response.sendRedirect("ViewPointsDB");
	
	        } catch (IOException | SQLException e)
	        {
	            System.err.println("Error 2 " + e);
	            response.sendRedirect("ChangeDetailsDB");
	        }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo()
    {
        return "Short description";
    }// </editor-fold>

}
