package path;
/* 
 Authors: Kevin Dunne, Jekaterina Pavlenko
 Date: 7/4/19
 Program: Website for enterprise application development
 */

import java.sql.*;
import java.util.ArrayList;

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
@WebServlet(name = "AddPointsDB", urlPatterns =
{
    "/AddPointsDB"
})
public class AddPointsDB extends HttpServlet
{
	
    Connection conn;
    PreparedStatement prepStat;
    Statement stat;
    ResultSet result;
    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    
    int maps_map_id = 0;
	int current_point_id = 0;
	String point_name = "";
	//new
	ArrayList<Integer> pointsFromOptions = new ArrayList<>();
	ArrayList<Integer> pointsToOptions = new ArrayList<>();
	ArrayList<Integer> pointsWeightOptions = new ArrayList<>();
	//
	ArrayList<String> directionOptions = new ArrayList<>();
    
    public void init() throws ServletException
    {
    	setupPointsFromArray();
    	setupPointsToArray();
    	setupWeigthtsArray();
    	setupDirectionsArray();

    	SQLConnection connect = new SQLConnection();
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            // setup the connection with the DB
            conn = DriverManager.getConnection(connect.URL, connect.USERNAME, connect.PASSWORD);
            
            System.out.println("Connected AddPointsDB");
        } catch (ClassNotFoundException | SQLException e)
        {
            System.err.println("Error 1" + e);
        }
    }
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
    	//String maps_map_id = request.getParameter("imageId");
    	String point_org = request.getParameter("organisation_name");
    	String point_building_name = request.getParameter("organisation_building_name");
		String map_image_name = request.getParameter("maps_map_id");
    	
    	//System.out.println("special map_image_name: "+map_image_name);
    	//System.out.println("special point_org: "+point_org);
    	//System.out.println("special point_building_name: "+point_building_name);
    	
    	
		try {
			prepStat = conn.prepareStatement("select map_id from maps where map_name = ?");
			prepStat.setString(1, map_image_name);
			result = prepStat.executeQuery();
		
			while (result.next()) {
				maps_map_id = result.getInt("map_id");
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("Error 10 ae: " + e);
		}
		///
		try {
			prepStat = conn.prepareStatement("select * from map_points where maps_map_id = ?");
			prepStat.setInt(1, maps_map_id);
			result = prepStat.executeQuery();
		
			while (result.next()) {
				current_point_id = result.getInt("current_point_id");
				point_name = result.getString("point_name");
				maps_map_id = result.getInt("maps_map_id");
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("Error 10 ae: " + e);
		}
		
    	//System.out.println("special "+maps_map_id);
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
		out.println("<!doctype html>\n"
				+ "<!-- Author: Jekaterina Pavlenko, Kevin Dunne, Christopher Costelloe Date: 09/03/2019-->"
				+ "<html lang=\"en\">" 
				+ "<head>" + "<meta charset=\"UTF-8\">" 
				+ "<title>Add Points Page</title>"
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
                + "                <section id=\"form\">\n"
                + "                    <ul class=\"sign_login\">\r\n"
                + "                        <li><a href=\"DetailsDB\">DETAILS</a></li>\r\n"
                + "                        <li><a href=\"Maps.jsp\">MAPS</a></li>\r\n"
                + "                        <li><a href=\"LogOutDB\" >LOG OUT</a></li>\r\n"
                + "						   <li><a href=\"ControlDB\">Control</a></li>\r\n"
                + "                    </ul>\r\n"
				+ "<br>\r\n");
                out.println(""
                + "<br>\r\n"
                + "<h3>Add New Nodes</h3>\n"
                + "<form action=\"AddThings\" method=\"post\" name=\"form\" onSubmit=\"return validateAll();\" >\n"
                + "<fieldset>\n"
                + "<legend>Add New Points to Map</legend>\n"
                + "<br>\n"
                + "\n"
                + "<p><label for=\"point_name\" class=\"title\" >Name of Point: <span>*</span></label>\n"
                + "<input type=\"text\" name=\"point_name\" id=\"point_name\" /required></p>\n"
                + "\n"
                + "<input type=\"hidden\" name=\"maps_map_id\" id=\"maps_map_id\" value="+maps_map_id+"></p>\n"
             	+ "<input type=\"hidden\" name=\"insertPoints\" id=\"insertPoints\" value=\"insertNode\"></p>\n"
                + "\n"
                + "<input type=\"submit\" name=\"submit\" id=\"submit\" value=\"Submit Details\" />\n"
                + "</p>\n"
                + "</fieldset>\n"
                + "</form>\n<br><br>");
                
                ///
                
                out.println(""
                + "<br>\r\n"
                + "<h3>Add New Points</h3>\n"
                + "<form action=\"AddThings\" method=\"post\" name=\"form\" onSubmit=\"return validateAll();\" >\n"
                + "<fieldset>\n"
                + "<legend>Add New Points to Map</legend>\n"
                + "<br>\n"
                + "\n");
                
                out.println(""
                        + "<p><label for=\"point_from_id\" class=\"title\">From point dropdown: <span>*</span></label>\n"
                        + "<select name=\"point_from_id\">"
                        + "<option value=\"\">Select</option>");
                        for (int i = 0; i < pointsFromOptions.size();i++)
        				{
                            out.println("<option value="+pointsFromOptions.get(i)+">"+pointsFromOptions.get(i)+"</option>");
        				}
                out.println("</select>");
                
                out.println(""
                        + "<p><label for=\"point_to_id\" class=\"title\">To point dropdown: <span>*</span></label>\n"
                        + "<select name=\"point_to_id\">"
                        + "<option value=\"\">Select</option>");
                        for (int i = 0; i < pointsToOptions.size();i++)
        				{
                            out.println("<option value="+pointsToOptions.get(i)+">"+pointsToOptions.get(i)+"</option>");
        				}
                out.println("</select>");
                
                out.println(""
                        + "<p><label for=\"point_weight\" class=\"title\">Point_weight: <span>*</span></label>\n"
                        + "<select name=\"point_weight\">"
                        + "<option value=\"\">Select</option>");
                        for (int i = 0; i < pointsWeightOptions.size();i++)
        				{
                            out.println("<option value="+pointsWeightOptions.get(i)+">"+pointsWeightOptions.get(i)+"</option>");
        				}
                out.println("</select>");
                
                out.println(""
                + "<p><label for=\"point_direction\" class=\"title\">Directions: <span>*</span></label>\n"
                + "<select name=\"point_direction\">"
                + "<option value=\"\">Select</option>");
                for (int i = 0; i < directionOptions.size();i++)
				{
                    out.println("<option value="+directionOptions.get(i)+">"+directionOptions.get(i)+"</option>");
				}
                out.println("</select>");
                
                /*
                out.println(""
                + "<p><label for=\"point_direction\" class=\"title\">direciton dropdown: <span>*</span></label>\n"
                + "<input type=\"text\" name=\"point_direction\" id=\"point_direction\" /required></p>\n"
                + "\n");
                 */
                		
                out.println("<input type=\"hidden\" name=\"insertPoints\" id=\"insertPoints\" value=\"insertPoints\"></p>\n"
                + "<input type=\"submit\" name=\"submit\" id=\"submit\" value=\"Submit Details\" />\n"
                + "</p>\n"
                + "</fieldset>\n"
                + "</form>\n");

        out.println("</section>\n"
        		+ "<br><br>"
                + "</main>\n"
                + "<footer>"
                + "<p>PathFinder project 2019</p>"
                + "<p>Authors: Kevin Dunne,Jekaterina Pavlenko & Christopher Costelloe</p>"
                + "<p><img src=\"images/maze_ic.png\" alt=\"\" ></p>"
                + "</footer>"
                + "\n"
                + "</div>\n"
                + "</body>\n"
                + "</html>\n"
                + "");
    }
    public void setupPointsFromArray()
    {
    	pointsFromOptions.add(1);
    	pointsFromOptions.add(2);
    	pointsFromOptions.add(3);
    	pointsFromOptions.add(4);
    	pointsFromOptions.add(5);
    }
    
    public void setupPointsToArray()
    {
    	pointsToOptions.add(1);
    	pointsToOptions.add(2);
    	pointsToOptions.add(3);
    	pointsToOptions.add(4);
    	pointsToOptions.add(5);
    }
    
    public void setupWeigthtsArray()
    {
    	pointsWeightOptions.add(1);
    	pointsWeightOptions.add(2);
    	pointsWeightOptions.add(3);
    	pointsWeightOptions.add(4);

    }
    public void setupDirectionsArray()
    {
    	directionOptions.add("straight_ahead");
    	directionOptions.add("upstairs_hallway");
    	directionOptions.add("downstairs_hallway");
    	directionOptions.add("turn_left");
    	directionOptions.add("turn_right");
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
