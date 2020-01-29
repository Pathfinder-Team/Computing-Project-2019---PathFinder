package path;

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

@WebServlet(name = "UploadMapDB", urlPatterns = {"/UploadMapDB"})
public class UploadMapDB extends HttpServlet 
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
    String orgNameRights;
    
    String organisation_name;
    String organisation_address;
    String organisation_email;
    String organisation_mobile;
    String organisation_building_name;
    String user_org_name;
    
	String org_name;
	String org_building;
	String map_name;
	String map_comments;
	Blob map_image;
	getRankPower rp = new getRankPower();

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
            throws ServletException, IOException {
    	
		rp.getStatusRank(request,response,stmt,conn);
		
		System.out.println("rp.getUserNameRights() AddThings: "+ rp.getUserNameRights());
		
		try {

			prepStat = conn.prepareStatement("select * from organisation where organisation_name = ?");
			prepStat.setString(1, rp.getOrgRights());
			result = prepStat.executeQuery();
			while (result.next()) {
				organisation_name = result.getString("organisation_name");
				organisation_address = result.getString("organisation_address");
				organisation_email = result.getString("organisation_email");
				organisation_mobile = result.getString("organisation_mobile");
				organisation_building_name = result.getString("organisation_building_name");
			}

		} catch (SQLException ex) {
			System.err.println("Error Org: " + ex);
		}
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        System.out.println("organisation_name: "+organisation_name);
        System.out.println("getOrgRights: "+rp.getOrgRights());
        
        if(organisation_name.equals(rp.getOrgRights()))
        {
        out.println("<!doctype html>\n"
                + "<!-- Author: Jekaterina Pavlenko, Kevin Dunne, Christopher Costelloe Date: 09/03/2019-->"
                + "<html lang=\"en\">"
                + "<head>"
                + "<meta charset=\"UTF-8\">"
                + "<title>Organisation Page</title>"
                + "<link rel=\"stylesheet\" type=\"text/css\" href=\"styles/mainstyle.css\" />"
                + "<link rel=\"stylesheet\" type=\"text/css\" href=\"styles/forum.css\" />"
                + "     </head>"
                + "           <body>"
                + "             <div id=\"container\">"
                + "               <header>"
                + "                   <img src=\"images/bn_header.png\" alt=\"\" >"
                + "             </header>"
                + "              <nav id=\"menu\">"
                + "               <ul>"
                + "                   <li><a href=\"index.html\" >ABOUT US</a></li>"
                + "                 <li><a href=\"register.jsp\" >REGISTER</a></li>"
                + "                 <li><a href=\"login.html\" >LOGIN</a></li>"
                + "                 <li><a href=\"contact.html\" >CONTACT US</a></li>"
                + "             </ul>"
                + "           </nav>");
        out.println(""
                + "<main>\r\n"
                + "                <section id=\"form\">\r\n"
                + "                    <ul class=\"sign_login\">\r\n"
                + "                        <li><a href=\"details.html\">DETAILS</a></li>\r\n"
                + "                        <li><a href=\"Maps.jsp\">MAPS</a></li>\r\n"
                + "                        <li><a href=\"LogOutDB\" >LOG OUT</a></li>\r\n"
                + "						   <li><a href=\"ControlDB\" >Control</a></li>\r\n"
                + "                    </ul>\r\n"
                + "                    <br>\r\n"
                + "                    <br>\r\n"
                + "                    <p>Here you can upload maps:<p>\r\n");
        out.println(""
        		+ "<form method=\"post\" action=\"AddThings\" enctype=\"multipart/form-data\">\r\n"
        		+ "<fieldset>" 
        		+ "<input type=\"hidden\" name=\"org_name\" id=\"org_name\" value=\""+orgNameRights+"\" /></p>"
        		+ "<input type=\"hidden\" name=\"mapAction\" id=\"mapAction\" value=\"mapAction\" /></p>"
        		+ "<p><label for=\"org_building\" class=\"title\">Building Name: <span>*</span></label>"
        		+ "<input type=\"text\" name=\"org_building\" id=\"org_building\" /></p>"
        		+ "<p><label for=\"map_name\" class=\"title\">Floor Name: <span>*</span></label>"
        		+ "<input type=\"text\" name=\"map_name\" id=\"map_name\" /></p>"
        		+ "<p><label for=\"map_comments\" class=\"title\">Map Comments: <span>*</span></label>"
        		+ "<input type=\"text\" name=\"map_comments\" id=\"map_comments\" /></p>"
        		+ "<label for=\"map_image\"><br><strong>Choose a file</strong><span> or drag it here</span>.</label>\r\n"
        		+ "<br>"
        		+ "<br>"
        		+ "<input type=\"file\" name=\"map_image\" id=\"map_image\" />\r\n"
        		+ "<br>"
        		+ "<br>"
        		+ "<button type=\"submit\">Upload and Submit</button>\r\n"
        		+ "<br>"
        		+ "<br>"
        		+ "</fieldset>"
        		+ "</form>"
        		+ "<br>");
        out.println("</section>\n"
                + "<br>\n"
                + "<br>\n"
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
            throws ServletException, IOException {
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
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
