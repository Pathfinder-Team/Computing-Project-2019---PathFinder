package path;
/* 
Authors: Kevin Dunne, Jekaterina Pavlenko, Christopher Costelloe
Date: 11/12/2019
Program: PathFinder website application - contact us page servlet
 */

import javax.servlet.annotation.*;
import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;

@WebServlet(name = "commentDB", urlPatterns =
{
    "/commentDB"
})
public class commentDB extends HttpServlet
{

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    Connection conn;
    Statement stmt;
    PreparedStatement prepStat;
    ResultSet result;

    String organisation_name = "";

    ArrayList<String> orgNames = new ArrayList<>();

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
		try {

			prepStat = conn.prepareStatement("select organisation_name from organisation");
			result = prepStat.executeQuery();
			while (result.next()) {
				organisation_name = result.getString("organisation_name");
				if(!orgNames.contains(organisation_name))
				{
					orgNames.add(organisation_name);
				}
			}
		} catch (SQLException ex) {
			System.err.println("Error Org: " + ex);
		}
        try
        {
            String comment_Id = request.getParameter("comment_Id");
            String comment_title = request.getParameter("comment_title");
            String comment_text = request.getParameter("comment_text");
            String organisation_name = request.getParameter("organisation_name");
        	
            String query = "insert into comment values(?, ?, ?, ?, ?)";
            prepStat = conn.prepareStatement(query);
            prepStat.setString(1, comment_Id);
            prepStat.setString(2, comment_title);
            prepStat.setString(3, comment_text);
            prepStat.setTimestamp(4, timestamp);
            prepStat.setString(5, organisation_name);
            prepStat.executeUpdate();
            response.sendRedirect("confirmation.html");

        } catch (SQLException e)
        {
            System.err.println("Error 2 " + e);
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
				+ "<img src=\"images/bn_header.png\" alt=\"PathFinder banner\" >" 
				+ "</header>"
				+ "<nav id=\"menu\">" 
				+ "<ul>"
				+ "<li><a href=\"index.html\" >ABOUT US</a></li>"
				+ "<li><a href=\"register.html\" >REGISTER</a></li>"
				+ "<li><a href=\"login.html\" >LOGIN</a></li>"
				+ "<li><a href=\"contact.html\" >CONTACT US</a></li>"
				+ "</ul>"
				+ "</nav>");
		
				out.println("" + "<main>\r\n" 
				+ "<section id=\"form\">\r\n");

        	out.println("<br>\r\n"
    				+"<form action=\"commentDB\" method=\"post\">"
                    + "<fieldset id=\"field\">"
                    + "<p><label for=\"comment_title\" class=\"title1\">Enter Comment Title: </label>\n"
                    + "<input type=\"text\" name=\"comment_title\" id=\"comment_title\" /></p>\n"
                    + "\n"
                    + "<p><label for=\"comment_text\" class=\"title1\">Comment Content: </label>\n"
                    + "<textarea rows=\"5\" cols=\"30\" name=\"comment_text\" id=\"comment_text\" /></textarea></p>"
                    + "<p>\n");
    		        out.println("<p><label for=\"organisation_name\" class=\"title1\">Organisation Name: <span>*</span></label>\n"
    				        + "<select name=\"organisation_name\">"
    				        + "<option value=\"\">Select</option>");
    				        for (int i = 0; i < orgNames.size();i++)
    						{
    				            out.println("<option value='"+orgNames.get(i)+"'>"+orgNames.get(i)+"</option>");
    						}
    				        out.println("</select>"
                    + "<br><br>"
                    + "<input type=\"submit\" name=\"submit\" id=\"submit1\" value=\"Submit Post\" />\n"
                    + "</p>"
                    + "</fieldset>"
                    + "</form>");

                out.println("</section>\n"
                        + "</main>\n"
                        + "<footer>"
                        + "<p>PathFinder project 2019</p>"
                        + "<p>Authors: Kevin Dunne, Jekaterina Pavlenko & Christopher Costelloe</p>"
                        + "<p><img src=\"images/maze_ic.png\" alt=\"Maze icon\" ></p>"
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
