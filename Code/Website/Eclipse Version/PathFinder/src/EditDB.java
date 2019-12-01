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
@WebServlet(name = "EditDB", urlPatterns =
{
    "/EditDB"
})
public class EditDB extends HttpServlet
{

    String first_name;
    String last_name;
    String user_name;
    String password;
    String email;
    String old_username;
    int Account_Status_Users;

    Connection conn;
    PreparedStatement prepStat;
    Statement stat;
    Timestamp timestamp = new Timestamp(System.currentTimeMillis());

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
        }
        catch (Exception e)
        {
            System.err.println("Error 1" + e);
        }
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        old_username = request.getParameter("user_name");

        System.out.println("editdb check: " + old_username);
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<!doctype html>\n"
                + "<!-- Author: Jekaterina Pavlenko K00224431\n"
                + "         Date: 09/03/2019\n"
                + "         Project Forum Page\n"
                + "-->\n"
                + "<html lang=\"en\">\n"
                + "    <head>\n"
                + "        <meta charset=\"UTF-8\">\n"
                + "        <title>Edit User Record's</title>\n"
                + "        <link rel=\"stylesheet\" type=\"text/css\" href=\"styles/mainstyle.css\" />\n"
                + "        <link rel=\"stylesheet\" type=\"text/css\" href=\"styles/forum.css\" />\n"
                + "\n"
                + "    </head>\n"
                + "\n"
                + "    <body>\n"
                + "        <div id=\"container\">\n"
                + "            <header>\n"
                + "                <img src=\"images/img3.jpg\" >\n"
                + "            </header>\n"
                + "            <nav id=\"menu\">\n"
                + "                <ul>\n"
                + "                    <li><a href=\"index.html\" >About Us</a></li>\n"
                + "                    <li><a href=\"register.html\">Register</a></li>\n"
                + "                    <li><a href=\"login.html\" >LOGIN</a></li>\n"
                + "                    <li><a href=\"forum.html\">FORUM</a></li>\n"
                + "                    <li><a href=\"contact.html\" >CONTACT</a></li>\n"
                + "                    <li><a href=\"ControlDB\" >CONTROL</a></li>\n"
                + "                    <li><a href=\"LogOutDB\" >Log Out</a></li>\n"
                + "                </ul>\n"
                + "            </nav>"
                + " <main>\n"
                + "                <section id=\"form\">\n"
                + "                    <h1>Edit</h1>\n"
                + "                    <form action=\"EditActionDB\" method=\"post\" name=\"form\" onSubmit=\"return validateAll();\" >\n"
                + "                        <fieldset>\n"
                + "                            <legend>Edit Record</legend>\n"
                + "                            <br>\n"
                + "\n"
                + "                            <p><label for=\"first_name\" class=\"title\" >First Name: <span>*</span></label>\n"
                + "                                <input type=\"text\" name=\"first_name\" id=\"first_name\" /required></p>\n"
                + "\n"
                + "                            <p><label for=\"last_name\" class=\"title\">Last Name: <span>*</span></label>\n"
                + "                                <input type=\"text\" name=\"last_name\" id=\"last_name\" /required></p>\n"
                + "\n"
                + "                            <p><label for=\"user_name\" class=\"title\">Username: <span>*</span></label>\n"
                + "                                <input type=\"text\" name=\"user_name\" id=\"user_name\" /required></p>\n"
                + "\n"
                + "                            <p><label for=\"email\" class=\"title\">Email: <span>*</span></label>\n"
                + "                                <input type=\"email\" name=\"email\" id=\"email\" /required></p>\n"
                + "\n"
                + "                            <p><label for=\"password\" class=\"title\">Password: <span>*</span></label>\n"
                + "                                <input type=\"password\" name=\"password\" id=\"password\" /required></p>\n"
                + "                            <p>\n"
                + "                            <p><label for=\"Account_Status_Users\" class=\"title\">Account Status: <span>*</span></label>\n"
                + "                                <input type=\"number\" name=\"Account_Status_Users\" id=\"Account_Status_Users\" /required></p>\n"
                + "                            <p>\n"
                + "                            <input type=\"hidden\" id=\"old_username\" name=\"old_username\" value=" + old_username + ">"
        );
        out.println("<input type=\"submit\" name=\"submit\" id=\"submit\" value=\"Submit Details\" />\n"
                + "                            </p>\n"
                + "                        </fieldset>\n"
                + "                    </form>\n");

        out.println("</section>\n"
                + "            </main>\n"
                + "<footer>"
                + "<p>PathFinder project 2019</p>"
                + "<p>Authors: Kevin Dunne,Jekaterina Pavlenko & Christopher Costelloe</p>"
                + "<p><img src=\"images/maze_ic.png\" alt=\"\" ></p>"
                + "</footer>"
                + "\n"
                + "        </div>\n"
                + "\n"
                + "    </body>\n"
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
