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
	String org_building = "";
	int map_id = 0;
	String map_name = "";

	ArrayList<String> buildingName = new ArrayList<>();
	getRankPower rp = new getRankPower();
	
    public void init() throws ServletException
    {

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
    	
    	//System.out.println("rp.getUserNameRights() AddPointsDB: "+ rp.getUserNameRights());
    	
    	String point_org = request.getParameter("organisation_name");
    	String point_building_name = request.getParameter("organisation_building_name");
		String map_image_name = request.getParameter("maps_map_id");

		try {
			prepStat = conn.prepareStatement("select org_building from maps where org_name = ?");
			
			if(point_org != null)
			{
				System.out.println("Checking if point_org is null");
				prepStat.setString(1, point_org);
			}
			else
			{
				prepStat.setString(1, rp.getOrgRights());	
			}
			result = prepStat.executeQuery();
			while (result.next()) {
				org_building = result.getString("org_building");
				if(!buildingName.contains(org_building))
				{
					buildingName.add(org_building);
				}
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
                
                //////////////////////////////////////////////////
                out.println("<br>\r\n"
                + "<h3>Add New Map Nodes</h3>\n"
                + "<h4 align=\"center\">Organisation: "+point_org+"</h4>\n"
                + "<form action=\"PointNamesDB\" method=\"post\" name=\"form\" onSubmit=\"return validateAll();\" >\n"
                + "<fieldset>\n"
                + "<legend>Select which Building to add new Nodes</legend>\n"
                + "<br>\n"
                + "\n");
               
                out.println(""
                + "<p><label for=\"organisation_building_name\" class=\"title\">Organisation Building's: <span>*</span></label>\n"
                + "<select name=\"organisation_building_name\">"
                + "<option value=\"\">Select</option>");
                for (int i = 0; i < buildingName.size();i++)
				{
                	
                    out.println("<option value='"+buildingName.get(i)+"'>"+buildingName.get(i)+"</option>");
                    //System.out.println("buildingName.get(i): "+buildingName.get(i));
				}
                out.println("</select><br><br>"
                		+ "<input type=\"hidden\" name=\"org_name\" id=\"org_name\" value='"+point_org+"'></p>\n"
                		+ "");
                
                out.println("<input type=\"submit\" name=\"submit\" id=\"submit\" value=\"Select Building\" />\n"
                + "</p>\n"
                + "</fieldset>\n"
                + "</form>"
                + "<br>"
                + "<br>");

                //////////////////////////////////////////////
                out.println("<br>\r\n"
                + "<h3>Add New Map Points</h3>\n"
                + "<form action=\"AddMapPoints\" method=\"post\" name=\"form\" onSubmit=\"return validateAll();\" >\n"
                + "<fieldset>\n"
                + "<legend>Select which Building to Add New Points</legend>\n"
                + "<br>\n"
                + "\n");
               
                out.println(""
                + "<p><label for=\"organisation_building_name\" class=\"title\">Organisation Building's: <span>*</span></label>\n"
                + "<select name=\"organisation_building_name\">"
                + "<option value=\"\">Select</option>");
                for (int i = 0; i < buildingName.size();i++)
				{
                	
                    out.println("<option value='"+buildingName.get(i)+"'>"+buildingName.get(i)+"</option>");
                    //System.out.println("buildingName.get(i): "+buildingName.get(i));
				}
                out.println("</select><br><br>"
                		+ "<input type=\"hidden\" name=\"org_name\" id=\"org_name\" value='"+point_org+"'></p>\n"
                		+ "");
                
                out.println("<input type=\"submit\" name=\"submit\" id=\"submit\" value=\"Select Building\" />\n"
                + "</p>\n"
                + "</fieldset>\n"
                + "</form>"
                + "<br>"
                + "<br>");
                //////////////////////////////////////////////////

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
