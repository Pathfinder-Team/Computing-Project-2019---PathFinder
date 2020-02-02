package path;
/* 
Authors: Kevin Dunne, Jekaterina Pavlenko
Date: 7/4/19
Program: Website for enterprise application development
 */
import java.sql.*;
import javax.servlet.ServletException;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author: Kevin Dunne,Jekaterina Pavlenko
 */
@WebServlet(name = "EditActionDB", urlPatterns =
{
    "/EditActionDB"
})
public class EditActionDB extends HttpServlet
{
    String triggerIdNode = "";
    String triggerIdPoint = "";
    String triggerIdOrg = "";
    String triggerIdOrgBuilding = "";

    Connection conn;
    PreparedStatement prepStat;
    Statement stat;
    Timestamp timestamp = new Timestamp(System.currentTimeMillis());

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
    	
        
        // Edit the Org
        if(triggerIdOrg.equals("EditOrg"))
        {
	        try
	        {
		        String organisation_name = request.getParameter("organisation_name");
	            String organisation_address = request.getParameter("organisation_address");
	            String organisation_email = request.getParameter("organisation_email");
	            int organisation_mobile = Integer.parseInt(request.getParameter("organisation_mobile"));
	            String organisation_building_name = request.getParameter("organisation_building_name");
	            String old_organisation_name = request.getParameter("old_organisation_name");
	            
	        
	            String query = "update organisation "
	            		+ "set organisation_name = ?,"
	            		+ "organisation_address = ?,"
	            		+ "organisation_email = ?,"
	            		+ "organisation_mobile = ?,"
	            		+ "organisation_building_name = ? "
	            		+ "where organisation_name = ?";
	            prepStat = conn.prepareStatement(query);
	            
	            prepStat.setString(1, organisation_name);
	            prepStat.setString(2, organisation_address);
	            prepStat.setString(3, organisation_email);
	            prepStat.setInt(4, organisation_mobile);
	            prepStat.setString(5, organisation_building_name);
	            prepStat.setString(6, old_organisation_name);
	            prepStat.executeUpdate();
	
	        } catch (SQLException e)
	        {
	            System.err.println("Error 2 " + e);
	        }
	        response.sendRedirect("DetailsDB");
        }
        // Edit the Node
        else if(triggerIdNode.equals("EditNode"))
        {
	        try
	        {
		        int current_point_id = Integer.parseInt(request.getParameter("current_point_id"));
	            String point_name = request.getParameter("point_name");
	            int maps_map_id = Integer.parseInt(request.getParameter("maps_map_id"));
	            
	            String query = "update map_points "
	            		+ "set current_point_id = ?,"
	            		+ "point_name = ?,"
	            		+ "maps_map_id = ?,"
	            		+ "where current_map_id = ?";
	            prepStat = conn.prepareStatement(query);
	            
	            prepStat.setInt(1, current_point_id);
	            prepStat.setString(2, point_name);
	            prepStat.setInt(3, maps_map_id);
	            prepStat.setInt(4, current_point_id);
	            prepStat.executeUpdate();
	
	        } catch (SQLException e)
	        {
	            System.err.println("Error 2 " + e);
	        }
	        response.sendRedirect("DetailsDB");
        }
        // Edit points
        else if(triggerIdPoint.equals("EditPoint"))
        {
	        try
	        {
		        int point_id = Integer.parseInt(request.getParameter("point_id"));
	            int point_from_id = Integer.parseInt(request.getParameter("point_from_id"));
	            int point_to_id = Integer.parseInt(request.getParameter("point_to_id"));
	            int point_weight = Integer.parseInt(request.getParameter("point_weight"));
	            String point_direction = request.getParameter("point_direction");
	            
	            String query = "update point_to "
	            		+ "set point_id = ?,"
	            		+ "point_from_id = ?,"
	            		+ "point_to_id = ?,"
	            		+ "point_weight = ?,"
	            		+ "point_direction = ?,"
	            		+ "where point_id = ?";
	            prepStat = conn.prepareStatement(query);
	            
	            prepStat.setInt(1, point_id);
	            prepStat.setInt(2, point_from_id);
	            prepStat.setInt(3, point_to_id);
	            prepStat.setInt(4, point_weight);
	            prepStat.setString(5, point_direction);
	            prepStat.setInt(6, point_id);
	            prepStat.executeUpdate();
	
	        } catch (SQLException e)
	        {
	            System.err.println("Error 2 " + e);
	        }
	        response.sendRedirect("DetailsDB");
        }
        // Edit points
        else if(triggerIdOrgBuilding.equals("EditOrgBuilding"))
        {
	        try
	        {
		        int point_id = Integer.parseInt(request.getParameter("point_id"));
	            int point_from_id = Integer.parseInt(request.getParameter("point_from_id"));
	            int point_to_id = Integer.parseInt(request.getParameter("point_to_id"));
	            int point_weight = Integer.parseInt(request.getParameter("point_weight"));
	            String point_direction = request.getParameter("point_direction");
	            
	            String query = "update point_to "
	            		+ "set point_id = ?,"
	            		+ "point_from_id = ?,"
	            		+ "point_to_id = ?,"
	            		+ "point_weight = ?,"
	            		+ "point_direction = ?,"
	            		+ "where point_id = ?";
	            prepStat = conn.prepareStatement(query);
	            
	            prepStat.setInt(1, point_id);
	            prepStat.setInt(2, point_from_id);
	            prepStat.setInt(3, point_to_id);
	            prepStat.setInt(4, point_weight);
	            prepStat.setString(5, point_direction);
	            prepStat.setInt(6, point_id);
	            prepStat.executeUpdate();
	
	        } catch (SQLException e)
	        {
	            System.err.println("Error 2 " + e);
	        }
	        response.sendRedirect("DetailsDB");
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
