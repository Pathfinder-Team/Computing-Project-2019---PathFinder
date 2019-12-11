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
 * @author: Kevin Dunne
 */
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

    int powerID;
    String powerUsername;
    String powerFirstName;
    String powerLastName;
    String powerPassword;
    String powerEmail;
    int powerStatus;
    ResultSet result;

    int user_id;
    String userNameRights;
    String passwordRights;
    int AccountStatusRights;
    getRankPower rp = new getRankPower();

    @Override
    public void init() throws ServletException
    {
        String URL = "jdbc:mysql://remotemysql.com:3306/4eyg55o51S?autoReconnect=true&useSSL=false";
        String USERNAME = "4eyg55o51S";
        String PASSWORD = "ADRFyeBfRn";
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            // setup the connection with the DB
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Connected");
        } catch (ClassNotFoundException | SQLException e)
        {
            System.err.println("Error 1" + e);
        }
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
		rp.getStatusRank(request,response,stmt,conn);
		System.out.println(" rp.getUserNameRights() commentDB: "+ rp.getUserNameRights());

        String comment_Id = request.getParameter("comment_Id");
        String comment_title = request.getParameter("comment_title");
        String comment_text = request.getParameter("comment_text");
        try
        {
            String query = "insert into comment values(?, ?, ?, ?, ?)";
            prepStat = conn.prepareStatement(query);
            prepStat.setString(1, comment_Id);
            prepStat.setString(2, comment_title);
            prepStat.setString(3, comment_text);
            prepStat.setTimestamp(4, timestamp);
            prepStat.setInt(5, user_id);
            prepStat.executeUpdate();
            response.sendRedirect("confirmation.html");

        } catch (SQLException e)
        {
            System.err.println("Error 2 " + e);
        }
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
		out.println("<!doctype html>\n"
				+ "<!-- Author: Jekaterina Pavlenko, Kevin Dunne, Christopher Costelloe Date: 09/03/2019-->"
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
				out.println("" + "<main>\r\n" 
				+ "<section id=\"form\">\r\n"
                + "                    <ul class=\"sign_login\">\r\n"
                + "                        <li><a href=\"DetailsDB\" class=\"current\">DETAILS</a></li>\r\n"
               // + "                      <li><a href=\"orgDB\">MAPS</a></li>\r\n"
                + "                        <li><a href=\"Maps.jsp\">MAPS</a></li>\r\n"
                + "                        <li><a href=\"LogOutDB\" >LOG OUT</a></li>\r\n"
                + "						   <li><a href=\"ControlDB\">Control</a></li>\r\n"
                + "                    </ul>\r\n"
				+ "<br>\r\n"
				+ "<br>");
				
        if (rp.getStatusRights() == 2 || rp.getStatusRights() == 1 )
        {
            out.println("<form action=\"commentDB\" method=\"post\">"
                    + "<fieldlist>"
                    + "<p><label for=\"comment_title\" class=\"title\">Enter Comment Title: <span>*</span></label>\n"
                    + "<input type=\"text\" name=\"comment_title\" id=\"comment_title\" /></p>\n"
                    + "\n"
                    + "<p><label for=\"comment_text\" class=\"title\">Comment Content: <span>*</span></label>\n"
                    + "<textarea rows=\"10\" cols=\"100\" name=\"comment_text\" id=\"comment_text\" /></textarea></p>"
                    + "<p>\n"
                    + "<input type=\"submit\" name=\"submit\" id=\"submit\" value=\"Submit Post\" />\n"
                    + "</p>"
                    + "</fieldlist>"
                    + "</form>");
        }
        else 
        {
            out.println(""
            		+ "<br><br>"
            		+ "<h3>You need to login to send a message to administrator</h3>\n");
            response.setHeader("Refresh", "5; login.html");
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
