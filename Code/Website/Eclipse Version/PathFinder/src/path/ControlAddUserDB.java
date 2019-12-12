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
@WebServlet(name = "ControlAddUserDB", urlPatterns =
{
    "/ControlAddUserDB"
})
public class ControlAddUserDB extends HttpServlet
{

    String idPosts;
    String Account_Status_Users;
    int specialCheck;
    String first_name;
    String last_name;
    String user_name;
    String password;
    String email;

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
        } catch (Exception e)
        {
            System.err.println("Error 1" + e);
        }
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {

        
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
                + "            <main>\n"
                + "                <section id=\"form\">\n"
                + "                    <h1>Add New User</h1>\n");

            out.println("<section id=\"form\">\n"
                    + "                    <h1>Register to sign in.</h1>\n"
                    + "                    <form action=\"AddNewUserActionDB\" method=\"post\" name=\"form\"\" >\n"
                    + "                        <fieldset>\n"
                    + "                            <br>\n"
                    + "                            <br>\n"
                    + "                            <p>Fill in new User Details.</p>\n"
                    + "\n"
                    + "                            <p><label for=\"first_name\" class=\"title\">First Name: <span>*</span></label>\n"
                    + "                                <input type=\"text\" name=\"first_name\" id=\"first_name\" /required ></p>\n"
                    + "\n"
                    + "                            <p><label for=\"last_name\" class=\"title\">Last Name: <span>*</span></label>\n"
                    + "                                <input type=\"text\" name=\"last_name\" id=\"last_name\" /required ></p>\n"
                    + "\n"
                    + "                            <p><label for=\"user_name\" class=\"title\">Username: <span>*</span></label>\n"
                    + "                                <input type=\"text\" name=\"user_name\" id=\"user_name\" /required ></p>\n"
                    + "\n"
                    + "                            <p><label for=\"email\" class=\"title\">Email: <span>*</span></label>\n"
                    + "                                <input type=\"email\" name=\"email\" id=\"email\" /required ></p>\n"
                    + "\n"
                    + "                            <p><label for=\"password\" class=\"title\">Password: <span>*</span></label>\n"
                    + "                                <input type=\"password\" name=\"password\" id=\"password\" /required ></p>\n"
                    + "\n"
                    + "                            <p>Adminstrator is Rank 1, Normal User is Rank 2</p>"
                    + "                            <p><label for=\"Account_Status_Users\" class=\"title\">User Rank: <span>*</span></label>\n"
                    + "                                <input type=\"number\" name=\"Account_Status_Users\" id=\"Account_Status_Users\" /required></p>\n"
                    + "\n"
                    + "                            <p>\n"
                    + "</p>"
                    + "                            <input type=\"submit\" name=\"submit\" id=\"submit\" value=\"Commit Changes\" />\n"
                    + "                            </p>\n"
                    + "                        </fieldset>\n"
                    + "                    </form>\n");

            out.println("</section>\n"
                + "            </main>\n"
                + "            <footer>\n"
                + "                <p>Dead By Daylight Fan Club\n"
                + "                    <a href=\"https://www.facebook.com/DeadByDaylight/\" target=\"_blank\"><img src=\"images/facebook.png\"  alt=\"facebook\" title=\"Visit us on Facebook\"></a>\n"
                + "                    <a href=\"https://www.instagram.com/deadbydaylight/?hl=en\" target=\"_blank\"><img src=\"images/instagram.png\" alt=\"instagram\" title=\"Visit us on Instagram\"></a>\n"
                + "                    <a href=\"https://www.youtube.com/channel/UCaSgsFdGbwjfdawl3rOXiwQ\" target=\"_blank\"><img src=\"images/youtube.png\"  alt=\"youtube\" title=\"Visit us on Youtube\"></a>\n"
                + "                    <br>\n"
                + "                    <a style=\"text-decoration:none;color:#E7F3EF;\" href=\"mailto:K00224431@student.lit.ie\">Email: K00224431@student.lit.ie</a><br>\n"
                + "                    Author: Jekaterina Pavlenko<br>\n"
                + "                </p>\n"
                + "            </footer>	\n"
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
