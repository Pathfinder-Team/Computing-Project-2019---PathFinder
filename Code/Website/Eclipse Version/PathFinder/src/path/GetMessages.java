package path;
/* 
 Authors: Kevin Dunne, Jekaterina Pavlenko
 Date: 7/4/19
 Program: Website for enterprise application development
 */

import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

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
@WebServlet(name = "GetMessages", urlPatterns =
{
    "/GetMessages"
})
public class GetMessages extends HttpServlet
{
    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    Connection conn;
    Statement stmt;
    PreparedStatement prepStat;
    PreparedStatement prepStat1;
    ResultSet result;
    ResultSet result1;
    ArrayList<Node> commentArray = new ArrayList<>();

	getRankPower rp = new getRankPower();
    
    @Override
    public void init() throws ServletException
    {

    	SQLConnection connect = new SQLConnection();
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            // setup the connection with the DB
            conn = DriverManager.getConnection(connect.URL, connect.USERNAME, connect.PASSWORD);
            
            System.out.println("Connected ViewPointsDB");
        } catch (ClassNotFoundException | SQLException e)
        {
            System.err.println("Error 1" + e);
        }
    }
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
		
	    try
	    {
			prepStat1 = conn.prepareStatement("select * from comment");
			result1 = prepStat1.executeQuery();
			commentArray.clear();
		    while(result1.next())
		    {
		    	int comment_Id = result1.getInt("comment_Id");
		    	String comment_title = result1.getString("comment_title");
		    	String comment_text = result1.getString("comment_text");
		    	Timestamp created = result1.getTimestamp("created");
		    	String organisation_name = result1.getString("organisation_name");
		    	Node node = new Node(comment_Id,comment_title,comment_text,created,organisation_name);
		    	commentArray.add(node);
		    }
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		    System.out.println("Special Json error: "+e);
		}
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
                + "<section id=\"form\">\n"
                + "<ul class=\"sign_login\">\r\n"
                + "<li><a href=\"DetailsDB\">DETAILS</a></li>\r\n"
                + "<li><a href=\"Maps.jsp\">MAPS</a></li>\r\n"
                + "<li><a href=\"LogOutDB\" >LOG OUT</a></li>\r\n"
                + "<li><a href=\"ControlDB\">Control</a></li>\r\n"
                + "</ul>\r\n"
				+ "<br>\r\n"
				+ "<br>"
				+ "<br>"
				+ "<br>");
                out.println(
						"<table class=\"comments\">");
                
		                for (int i = 0; i < commentArray.size(); i++)
		                {		                    
			        		out.println(
			        				"<tr>"
						                +"<th>Title:" + commentArray.get(i).comment_title+ "</th>"
						                + "<th>Organisation Name:" + commentArray.get(i).organisation_name+ "</th>"
						                + "<th>Created:" + commentArray.get(i).created+ "</th>"
						                /*
						                + "<th>"
							                + "<form action=\"DeleteDB\" method=\"post\">"
							                + "<input type=\"hidden\" id=\"TriggerNode\" name=\"TriggerNode\" value=\"NodeDelete\">"
									        + "<input type=\"submit\" value=\"Delete Node\">"
									        + "</form>"
								        + "</th>"
								        + "<th>"
									        + "<form action=\"EditDB\" method=\"post\">"
									        + "<input type=\"hidden\" id=\"TriggerEditNode\" name=\"TriggerEditNode\" value=\"EditNode\">"
									        + "<input type=\"submit\" value=\"Edit Node\">"
									        + "</form>"
								        + "</th>"
									        */
							        + "</tr>"
							        + "<tr>"
							        	+ "<th colspan=\"3\" align=\"left\">Comment:" + commentArray.get(i).comment_text+"</th>"
							        + "</tr>");
			        		out.println("");
		                }
				        out.println("</table>"
				        + "<br><br>");
        out.println("</section>\n"
        		+ "<br>"
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
