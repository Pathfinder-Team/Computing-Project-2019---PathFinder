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
    ResultSet result;
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
		//rp1.getStatus(request,response,stmt,conn);
		
		rp.getStatusRank(request,response,stmt,conn);
		
		System.out.println(" rp.getUserNameRights() ControlDB: "+ rp.getUserNameRights());
		
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
                + "              <nav id=\"menu\">"
                + "               <ul>"
                + "                 <li><a href=\"index.html\" >ABOUT US</a></li>"
                + "                 <li><a href=\"register.html\" >REGISTER</a></li>"
                + "                 <li><a href=\"login.html\" >LOGIN</a></li>"
                + "                 <li><a href=\"contact.html\" >CONTACT US</a></li>"
                + "             </ul>"
                + "           </nav>"
                + "            <main>"
                + "                <section id=\"form\">\n"
                + "                    <ul class=\"sign_login\">\r\n"
                + "                        <li><a href=\"DetailsDB\">DETAILS</a></li>\r\n"
               // + "                      <li><a href=\"orgDB\">MAPS</a></li>\r\n"
                + "                        <li><a href=\"Maps.jsp\">MAPS</a></li>\r\n"
                + "                        <li><a href=\"LogOutDB\" >LOG OUT</a></li>\r\n"
                + "						   <li><a href=\"ControlDB\" class=\"current\">Control</a></li>\r\n"
                + "                    </ul>\r\n"
                + "                    <br>\r\n"
                + "                    <br>\r\n"
                + "");
        try
        {
            // if AccountStatusRights which determines if your an admin or normal user equals 1 then you are an admin
            if (rp.getStatusRights() == 1)
            {
                out.println("<h2>Welcome to the Administrator Control Panel</h2>"
                        + "<h3>Controls</h3>"
                        + "<form action=\"ChangeDetailsDB\">"
                        + "<h4>Username: " + rp.getUserNameRights() + "</h4>"
                        + "<h4>Email: " + rp.getEmailRights() + "</h4>"
                        + "<h4>Organization: " + rp.getOrgRights() + "</h4>"
                        + "<button type=\"submit\" >Edit Details</button>"
                        + "</form>");

            } else if (rp.getStatusRights() == 2)
            {
                out.println("<h2>Welcome to the User Control Panel</h2>"
                        + "<h3 style=\"text-align:left\">Controls</h3>"
                        + "<form action=\"ChangeDetailsDB\">"
                        + "<h4>Username: " + rp.getUserNameRights() + "</h4>"
                        + "<h4>Email: " + rp.getEmailRights() + "</h4>"
                        + "<h4>Organization: " + rp.getOrgRights() + "</h4>"
                        + "<button type=\"submit\" >Edit Details</button>"
                        + "</form>");
            } else
            {
                //response.setHeader("Refresh", "0; authorisation.html");
                //response.sendRedirect("authorisation.html");
            }
            out.println("</section>\n"
                    + "<br>\n"
                    + "<br>\n"
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
        } catch (Exception ex)
        {
            Logger.getLogger(ControlDB.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("Error: "+ex);
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
