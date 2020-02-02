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

@WebServlet(name = "EditDB", urlPatterns =
{
    "/EditDB"
})
public class EditDB extends HttpServlet
{
	
    Connection conn;
    PreparedStatement prepStat;
    Statement stat;
    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    
    String triggerIdNode = "";
    String triggerIdPoint = "";
    String triggerIdOrg = "";
    String triggerIdOrgBuilding = "";


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
    	
    	////////////////////////////
    	if(request.getParameter("TriggerEditOrg") != null)
    	{
	    	// value EditOrg
    		triggerIdOrg = request.getParameter("TriggerEditOrg");
	        System.out.println("triggerIdOrg: "+triggerIdOrg);
    	}
    	///////////////////////////////
    	else if (request.getParameter("TriggerEditOrgBuilding") != null)
    	{
	        // value EditOrgBuilding
    		triggerIdOrgBuilding = request.getParameter("TriggerEditOrgBuilding");
	        System.out.println("triggerIdOrgBuilding: "+triggerIdOrgBuilding);
    	}
    	//////////////////////////////
    	else if (request.getParameter("TriggerEditPoint") != null)
    	{
	        // value EditPoint
	        triggerIdPoint = request.getParameter("TriggerEditPoint");
	        System.out.println("triggerIdPoint: "+triggerIdPoint);
    	}
    	//////////////////////////////
    	else if(request.getParameter("TriggerEditNode") != null)
    	{
	        // EditNode
    		triggerIdNode = request.getParameter("TriggerEditNode");
	        System.out.println("triggerIdNode: "+triggerIdNode);
    	}
 
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
		out.println("<!doctype html>\n"
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
                out.println("<main>\n"
                + "<section id=\"form\">\n"
                + "<ul class=\"sign_login\">\r\n"
                + "<li><a href=\"DetailsDB\">DETAILS</a></li>\r\n"
                + "<li><a href=\"Maps.jsp\">MAPS</a></li>\r\n"
                + "<li><a href=\"LogOutDB\" >LOG OUT</a></li>\r\n"
                + "<li><a href=\"ControlDB\">CONTROL</a></li>\r\n"
                + "</ul>\r\n"
				+ "<br>\r\n" 
				+ "<br>\r\n");
				
                // Edit the Org
                if(triggerIdOrg.equals("EditOrg"))
                {
    		        String organisation_name = request.getParameter("organisation_name");
    	            String organisation_address = request.getParameter("organisation_address");
    	            String organisation_email = request.getParameter("organisation_email");
    	            int organisation_mobile = Integer.parseInt(request.getParameter("organisation_mobile"));
    	            String organisation_building_name = request.getParameter("organisation_building_name");
    	            
					out.println("<h1>Edit</h1>\n"
	                + "<form action=\"EditActionDB\" method=\"post\" name=\"form\" onSubmit=\"return validateAll();\" >\n"
	                + "<fieldset>\n"
	                + "<legend>Edit Organisation </legend>\n"
	                + "<br>\n"
	                + "\n"
	                + "<p><label for=\"organisation_name\" class=\"title\" >organisation Name: <span>*</span></label>\n"
	                + "<input type=\"text\" name=\"organisation_name\" id=\"organisation_name\" value='"+organisation_name+"' /required></p>\n"
	                + "\n"
	                + "<p><label for=\"organisation_address\" class=\"title\">organisation_address: <span>*</span></label>\n"
	                + "<input type=\"text\" name=\"organisation_address\" id=\"organisation_address\" value='"+organisation_address+"' /required></p>\n"
	                + "\n"
	                + "<p><label for=\"organisation_email\" class=\"title\">organisation_email: <span>*</span></label>\n"
	                + "<input type=\"email\" name=\"organisation_email\" id=\"organisation_email\" value="+organisation_email+" /required></p>\n"
	                + "\n"
	                + "<p><label for=\"organisation_mobile\" class=\"title\">organisation_mobile: <span>*</span></label>\n"
	                + "<input type=\"number\" name=\"organisation_mobile\" id=\"organisation_mobile\" value="+organisation_mobile+" /required></p>\n"
	                + "\n"
	                + "<p><label for=\"organisation_building_name\" class=\"title\">organisation_building_name: <span>*</span></label>\n"
	                + "<input type=\"text\" name=\"organisation_building_name\" id=\"organisation_building_name\" value='"+organisation_building_name+"' /required></p>\n"
	                + "<input type=\"hidden\" id=\"TriggerEditOrg\" name=\"TriggerEditOrg\" value=\"EditOrg\">"
	                + "<p>\n");
			        out.println(
			        "<input type=\"submit\" name=\"submit\" id=\"submit\" value=\"Submit Details\" />\n"
			        + "</p>\n"
			        + "</fieldset>\n"
			        + "</form>\n");
                }
                // Edit the Node
                else if(triggerIdNode.equals("EditNode"))
                {
                	
    		        int current_point_id = Integer.parseInt(request.getParameter("current_point_id"));
    	            String point_name = request.getParameter("point_name");
    	            int maps_map_id = Integer.parseInt(request.getParameter("maps_map_id"));
    	            
					out.println("<h1>Edit</h1>\n"
	                + "<form action=\"EditActionDB\" method=\"post\" name=\"form\" onSubmit=\"return validateAll();\" >\n"
	                + "<fieldset>\n"
	                + "<legend>Edit Node</legend>\n"
	                + "<br>\n"
	                + "\n"
	                + "<p><label for=\"current_point_id\" class=\"title\" >current_point_id: <span>*</span></label>\n"
	                + "<input type=\"text\" name=\"current_point_id\" id=\"current_point_id\"  value="+current_point_id +" /required></p>\n"
	                + "\n"
	                + "<p><label for=\"point_name\" class=\"title\">point_name: <span>*</span></label>\n"
	                + "<input type=\"text\" name=\"point_name\" id=\"point_name\"  value='"+point_name +"' /required></p>\n"
	                + "\n"
	                + "<p><label for=\"maps_map_id\" class=\"title\">maps_map_id: <span>*</span></label>\n"
	                + "<input type=\"email\" name=\"maps_map_id\" id=\"maps_map_id\"  value="+maps_map_id +" /required></p>\n"
	                + "<input type=\"hidden\" id=\"TriggerEditNode\" name=\"TriggerEditNode\" value=\"EditNode\">"
	                + "\n"
	                + "<p>\n");
			        out.println(
			        "<input type=\"submit\" name=\"submit\" id=\"submit\" value=\"Submit Details\" />\n"
			        + "</p>\n"
			        + "</fieldset>\n"
			        + "</form>\n");
                }
                // edit the org building
                else if(triggerIdOrgBuilding.equals("EditOrgBuilding"))
                {
                	
    		        int map_id = Integer.parseInt(request.getParameter("map_id"));
    		        String org_name = request.getParameter("org_name");
    		        String org_building = request.getParameter("org_building");
    		        String map_name = request.getParameter("map_name");
    		        String map_comments = request.getParameter("map_comments");
   
  
                	
					out.println("<h1>Edit</h1>\n"
	                + "<form action=\"EditActionDB\" method=\"post\" name=\"form\" onSubmit=\"return validateAll();\" >\n"
	                + "<fieldset>\n"
	                + "<legend>Edit Building Details</legend>\n"
	                + "<br>\n"
	                + "\n"
	                + "<p><label for=\"map_id\" class=\"title\" >map_id: <span>*</span></label>\n"
	                + "<input type=\"text\" name=\"map_id\" id=\"map_id\" value="+map_id+"  /required></p>\n"
	                + "\n"
	                + "<p><label for=\"org_name\" class=\"title\">org_name: <span>*</span></label>\n"
	                + "<input type=\"text\" name=\"org_name\" id=\"org_name\" value="+org_name+" /required></p>\n"
	                + "\n"
	                + "<p><label for=\"org_building\" class=\"title\">org_building: <span>*</span></label>\n"
	                + "<input type=\"email\" name=\"org_building\" id=\"org_building\"  value="+org_building+" /required></p>\n"
	                + "\n"
	                + "<p><label for=\"map_name\" class=\"title\">map_name: <span>*</span></label>\n"
	                + "<input type=\"number\" name=\"map_name\" id=\"map_name\" value="+map_name+" /required></p>\n"
	                + "\n"
	                + "<p><label for=\"map_comments\" class=\"title\">map_comments: <span>*</span></label>\n"
	                + "<input type=\"text\" name=\"map_comments\" id=\"map_comments\" value="+map_comments+" /required></p>\n"
	                + "<p>\n");
			        out.println(
			        "<input type=\"submit\" name=\"submit\" id=\"submit\" value=\"Submit Details\" />\n"
			        + "</p>\n"
			        + "</fieldset>\n"
			        + "</form>\n");
                }
                // edit the point
                else if(triggerIdPoint.equals("EditPoint"))
                {
    		        int point_id = Integer.parseInt(request.getParameter("point_id"));
    	            int point_from_id = Integer.parseInt(request.getParameter("point_from_id"));
    	            int point_to_id = Integer.parseInt(request.getParameter("point_to_id"));
    	            int point_weight = Integer.parseInt(request.getParameter("point_weight"));
    	            String point_direction = request.getParameter("point_direction");
    	          
					out.println("<h1>Edit</h1>\n"
	                + "<form action=\"EditActionDB\" method=\"post\" name=\"form\" onSubmit=\"return validateAll();\" >\n"
	                + "<fieldset>\n"
	                + "<legend>Edit Point</legend>\n"
	                + "<br>\n"
	                + "\n"
	                + "<input type=\"hidden\" id=\"point_id\" name=\"point_id\" value="+point_id+">"
	                + "\n"
	                + "<p><label for=\"point_from_id\" class=\"title\">point_from_id: <span>*</span></label>\n"
	                + "<input type=\"number\" name=\"point_from_id\" id=\"point_from_id\"  value="+point_from_id +" /required></p>\n"
	                + "\n"
	                + "<p><label for=\"point_to_id\" class=\"title\">point_to_id: <span>*</span></label>\n"
	                + "<input type=\"number\" name=\"point_to_id\" id=\"point_to_id\" value="+point_to_id +" /required></p>\n"
	                + "\n"
	                + "<p><label for=\"point_weight\" class=\"title\">point_weight: <span>*</span></label>\n"
	                + "<input type=\"number\" name=\"point_weight\" id=\"point_weight\" value="+ point_weight+" /required></p>\n"
	                + "\n"
	                + "<p><label for=\"point_name\" class=\"title\">point_name: <span>*</span></label>\n"
	                + "<input type=\"text\" name=\"point_name\" id=\"point_name\" value='"+point_direction+"' /required></p>\n"
	                + "<input type=\"hidden\" id=\"TriggerEditPoint\" name=\"TriggerEditPoint\" value=\"EditPoint\">"
	                + "<p>\n");
			        out.println(
			        "<input type=\"submit\" name=\"submit\" id=\"submit\" value=\"Submit Details\" />\n"
			        + "</p>\n"
			        + "</fieldset>\n"
			        + "</form>\n");
                }
        out.println("</section>\n"
                + "</main>\n"
                + "<footer>"
                + "<p>PathFinder project 2019</p>"
                + "<p>Authors: Kevin Dunne,Jekaterina Pavlenko & Christopher Costelloe</p>"
                + "<p><img src=\"images/maze_ic.png\" alt=\"\" ></p>"
                + "</footer>"
                + "\n"
                + "</div>\n"
                + "\n"
                + "</body>\n"
                + "</html>\n"
                + "");
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
