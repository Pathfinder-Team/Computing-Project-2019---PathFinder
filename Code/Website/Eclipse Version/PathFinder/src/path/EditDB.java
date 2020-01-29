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
    	String old_organisation_name = request.getParameter("old_organisation_name");
    	
    	System.out.println("special old_organisation_name: "+old_organisation_name);
    	
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
                + "                <section id=\"form\">\n"
                + "                    <ul class=\"sign_login\">\r\n"
                + "						   <li><a href=\"ControlDB\">CONTROL</a></li>\r\n"
                + "                        <li><a href=\"DetailsDB\">DETAILS</a></li>\r\n"
                + "                        <li><a href=\"Maps.jsp\">MAPS</a></li>\r\n"
                + "                        <li><a href=\"LogOutDB\" >LOG OUT</a></li>\r\n"
                + "                    </ul>\r\n"
				+ "<br>\r\n" 
				+ "<br>\r\n"
                + "                    <h1>Edit</h1>\n"
                + "                    <form action=\"EditActionDB\" method=\"post\" name=\"form\" onSubmit=\"return validateAll();\" >\n"
                + "                        <fieldset>\n"
                + "                            <legend>Edit Record</legend>\n"
                + "                            <br>\n"
                + "\n"
                + "                            <p><label for=\"organisation_name\" class=\"title\" >organisation Name: <span>*</span></label>\n"
                + "                                <input type=\"text\" name=\"organisation_name\" id=\"organisation_name\" /required></p>\n"
                + "\n"
                + "                            <p><label for=\"organisation_address\" class=\"title\">organisation_address: <span>*</span></label>\n"
                + "                                <input type=\"text\" name=\"organisation_address\" id=\"organisation_address\" /required></p>\n"
                + "\n"
                + "                            <p><label for=\"organisation_email\" class=\"title\">organisation_email: <span>*</span></label>\n"
                + "                                <input type=\"email\" name=\"organisation_email\" id=\"organisation_email\" /required></p>\n"
                + "\n"
                + "                            <p><label for=\"organisation_mobile\" class=\"title\">organisation_mobile: <span>*</span></label>\n"
                + "                                <input type=\"number\" name=\"organisation_mobile\" id=\"organisation_mobile\" /required></p>\n"
                + "\n"
                + "                            <p><label for=\"organisation_building_name\" class=\"title\">organisation_building_name: <span>*</span></label>\n"
                + "                                <input type=\"text\" name=\"organisation_building_name\" id=\"organisation_building_name\" /required></p>\n"
                + "                            <p>\n"
                + "                            <input type=\"hidden\" id=\"old_organisation_name\" name=\"old_organisation_name\" value=\""+old_organisation_name+"\">"
        );
        
        out.println("<input type=\"submit\" name=\"submit\" id=\"submit\" value=\"Submit Details\" />\n"
                + "                            </p>\n"
                + "                        </fieldset>\n"
                + "                    </form>\n");

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
