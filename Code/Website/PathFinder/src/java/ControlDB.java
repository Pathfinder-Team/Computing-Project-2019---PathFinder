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
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "ControlDB", urlPatterns =
{
    "/ControlDB"
})
public class ControlDB extends HttpServlet
{

    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    Connection conn;
    Connection conn2;
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

    int idRights;
    String userNameRights;
    String passwordRights;
    String emailRights;
    int AccountStatusRights;
    int special;

    @Override
    public void init() throws ServletException
    {
        String URL = "jdbc:mysql://localhost:3306/";
        String DB = "mydb";
        String USERNAME = "root";
        String PASSWORD = "";

        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            // setup the connection with the DB
            conn = DriverManager.getConnection(URL + DB, USERNAME, PASSWORD);
            System.out.println("Connected");
        } catch (ClassNotFoundException | SQLException e)
        {
            System.err.println("Error 1" + e);
        }
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        // create a cookie array
        Cookie cookie = null;
        Cookie[] cookies = null;
        cookies = request.getCookies();
        // were going to loop through the cookies array and if the active cookies match values in the database
        // we know we are that user in the database so were going to put there information into some variables
        try
        {
            //
            //
            if (cookies != null)
            {
                for (int i = 0; i < cookies.length; i++)
                {
                    cookie = cookies[i];
                    stmt = conn.createStatement();
                    String sql5 = "select idtable1,user_name,password,Account_Status_Users,email from users";
                    result = stmt.executeQuery(sql5);
                    while (result.next())
                    {
                        String powerUsername = result.getString("user_name");
                        int powerID = result.getInt("idtable1");
                        String powerPassword = result.getString("password");
                        int powerStatus = result.getInt("Account_Status_Users");
                        String powerEmail = result.getString("email");

                        // if cookie username and username from the database match then we are this record,
                        // extremly important note!: all usernames are unique so they database cannot contain 2 exact usernames
                        if (powerUsername.equals(cookie.getValue()))
                        {
                            userNameRights = powerUsername;
                            idRights = powerID;
                            passwordRights = powerPassword;
                            AccountStatusRights = powerStatus;
                            emailRights = powerEmail;
                        }
                    }
                }
            }
        } catch (SQLException ex)
        {
            Logger.getLogger(ControlDB.class.getName()).log(Level.SEVERE, null, ex);
        }

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
                + "        <title>Control Panel</title>\n"
                + "        <link rel=\"stylesheet\" type=\"text/css\" href=\"styles/mainstyle.css\" />\n"
                + "        <link rel=\"stylesheet\" type=\"text/css\" href=\"styles/forum.css\" />\n"
                + "    </head>\n"
                + "\n"
                + "    <body>\n"
                + "        <div id=\"container\">\n"
                + "            <header>\n"
                + "                <img src=\"images/img3.jpg\" alt=\"\" >\n"
                + "            </header>\n"
                + "            <nav id=\"menu\">\n"
                + "                <ul>\n"
                + "                    <li><a href=\"index.html\" >About Us</a></li>\n"
                + "                    <li><a href=\"register.html\">Register</a></li>\n"
                + "                    <li><a href=\"login.html\" >LOGIN</a></li>\n"
                + "                    <li><a href=\"forum.html\">FORUM</a></li>\n"
                + "                    <li><a href=\"contact.html\" >CONTACT</a></li>\n"
                + "                    <li><a href=\"ControlDB\" >CONTROL</a></li>\n"
                + "                </ul>\n"
                + "            </nav>"
                + "            <main>"
                + "                <section id=\"form\">\n"
                + "                    <h3>Control Panel</h3>\n");
        try
        {
            // if AccountStatusRights which determines if your an admin or normal user equals 1 then you are an admin
            if (AccountStatusRights == 1)
            {
                out.println("<h2>Welcome to the Administrator Control Panel</h2>"
                        + "<h3>Controls</h3>"
                        + "<form action=\"ChangeDetailsDB\">"
                        + "<h4>Username: " + userNameRights + "</h4>"
                        + "<h4>Email: " + emailRights + "</h4>"
                        + "<button type=\"submit\" >Edit Details</button>"
                        + "</form>"
                        + "<br>"
                        + "<br>"
                        + "<form action=\"ControlAddUserDB\">"
                        + "<button type=\"submit\" >Add New User</button>"
                        + "</form><br>");

                Class.forName("com.mysql.jdbc.Driver");
                out.println("<h2>Messages from Users</h2>");
                stmt = conn.createStatement();
                String sql = "select * from comment join users on comment.Users_idtable1 = users.idtable1";
                result = stmt.executeQuery(sql);
                while (result.next())
                {
                    special = result.getInt("idComment");
                    out.println("<table class=\"comments\">"
                            + "<tr>"
                            + "<th>Username:" + result.getString("user_name") + "</th>"
                            + "<th>Comment Title: " + result.getString("comment_title") + "</th>"
                            + "<th>DateCreated: " + result.getString("created") + "</th>"
                            + "<th>"
                            + "<form action=\"ControlDeleteCommentsDB\" method=\"post\">"
                            + "<input type=\"hidden\" id=\"idComment\" name=\"idComment\" value=" + special + ">"
                            + "<input type=\"hidden\" id=\"pageDirection\" name=\"pageDirection\" value=\"ControlDB\">"
                            + "<input type=\"submit\" value=\"Delete\">"
                            + "</form>"
                            + "</th>"
                            + "</tr>"
                            + "<tr>"
                            + "<td colspan=\"6\">" + result.getString("comment_text") + "</td>"
                            + "</tr>"
                            + "</table>"
                            + "<br>");

                }
                // else you are a normal user
            } else if (AccountStatusRights == 2)
            {

                out.println("<h2>Welcome to the User Control Panel</h2>"
                        + "<h3 style=\"text-align:left\">Controls</h3>"
                        + "<form action=\"ChangeDetailsDB\">"
                        + "<h4>Username: " + userNameRights + "</h4>"
                        + "<h4>Email: " + emailRights + "</h4>"
                        + "<button type=\"submit\" >Edit Details</button>"
                        + "</form>");

            } else
            {
                //response.setHeader("Refresh", "0; authorisation.html");
                response.sendRedirect("authorisation.html");
            }

            out.println("</section>\n"
                    + "                <br>\n"
                    + "                <br>\n"
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
        } catch (SQLException | ClassNotFoundException ex)
        {
            Logger.getLogger(ControlDB.class.getName()).log(Level.SEVERE, null, ex);
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
