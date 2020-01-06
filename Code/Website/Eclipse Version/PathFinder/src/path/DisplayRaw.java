package path;

import javax.servlet.annotation.*;
import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "DisplayRaw", urlPatterns = { "/DisplayRaw" })
public class DisplayRaw extends HttpServlet {

	private static final int BUFFER_SIZE = 4096;
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
	int organisation_mobile;
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
		///////////////////////////////////////////
		rp.getStatusRank(request,response,stmt,conn);
		System.out.println(" rp.getUserNameRights() ControlDB: "+ rp.getUserNameRights());
		
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
	
			out.println("{\r\n" + 
					"    \"contacts\": [\r\n" + 
					"        {\r\n" + 
					"                \"id\": \"c200\",\r\n" + 
					"                \"name\": \"Ravi Tamada\",\r\n" + 
					"                \"email\": \"ravi@gmail.com\",\r\n" + 
					"                \"address\": \"xx-xx-xxxx,x - street, x - country\",\r\n" + 
					"                \"gender\" : \"male\",\r\n" + 
					"                \"phone\": {\r\n" + 
					"                    \"mobile\": \"+91 0000000000\",\r\n" + 
					"                    \"home\": \"00 000000\",\r\n" + 
					"                    \"office\": \"00 000000\"\r\n" + 
					"                }\r\n" + 
					"        },\r\n" + 
					"        {\r\n" + 
					"                \"id\": \"c201\",\r\n" + 
					"                \"name\": \"Johnny Depp\",\r\n" + 
					"                \"email\": \"johnny_depp@gmail.com\",\r\n" + 
					"                \"address\": \"xx-xx-xxxx,x - street, x - country\",\r\n" + 
					"                \"gender\" : \"male\",\r\n" + 
					"                \"phone\": {\r\n" + 
					"                    \"mobile\": \"+91 0000000000\",\r\n" + 
					"                    \"home\": \"00 000000\",\r\n" + 
					"                    \"office\": \"00 000000\"\r\n" + 
					"                }\r\n" + 
					"        },\r\n" + 
					"        {\r\n" + 
					"                \"id\": \"c202\",\r\n" + 
					"                \"name\": \"Leonardo Dicaprio\",\r\n" + 
					"                \"email\": \"leonardo_dicaprio@gmail.com\",\r\n" + 
					"                \"address\": \"xx-xx-xxxx,x - street, x - country\",\r\n" + 
					"                \"gender\" : \"male\",\r\n" + 
					"                \"phone\": {\r\n" + 
					"                    \"mobile\": \"+91 0000000000\",\r\n" + 
					"                    \"home\": \"00 000000\",\r\n" + 
					"                    \"office\": \"00 000000\"\r\n" + 
					"                }\r\n" + 
					"        },\r\n" + 
					"        {\r\n" + 
					"                \"id\": \"c203\",\r\n" + 
					"                \"name\": \"John Wayne\",\r\n" + 
					"                \"email\": \"john_wayne@gmail.com\",\r\n" + 
					"                \"address\": \"xx-xx-xxxx,x - street, x - country\",\r\n" + 
					"                \"gender\" : \"male\",\r\n" + 
					"                \"phone\": {\r\n" + 
					"                    \"mobile\": \"+91 0000000000\",\r\n" + 
					"                    \"home\": \"00 000000\",\r\n" + 
					"                    \"office\": \"00 000000\"\r\n" + 
					"                }\r\n" + 
					"        },\r\n" + 
					"        {\r\n" + 
					"                \"id\": \"c204\",\r\n" + 
					"                \"name\": \"Angelina Jolie\",\r\n" + 
					"                \"email\": \"angelina_jolie@gmail.com\",\r\n" + 
					"                \"address\": \"xx-xx-xxxx,x - street, x - country\",\r\n" + 
					"                \"gender\" : \"female\",\r\n" + 
					"                \"phone\": {\r\n" + 
					"                    \"mobile\": \"+91 0000000000\",\r\n" + 
					"                    \"home\": \"00 000000\",\r\n" + 
					"                    \"office\": \"00 000000\"\r\n" + 
					"                }\r\n" + 
					"        },\r\n" + 
					"        {\r\n" + 
					"                \"id\": \"c205\",\r\n" + 
					"                \"name\": \"Dido\",\r\n" + 
					"                \"email\": \"dido@gmail.com\",\r\n" + 
					"                \"address\": \"xx-xx-xxxx,x - street, x - country\",\r\n" + 
					"                \"gender\" : \"female\",\r\n" + 
					"                \"phone\": {\r\n" + 
					"                    \"mobile\": \"+91 0000000000\",\r\n" + 
					"                    \"home\": \"00 000000\",\r\n" + 
					"                    \"office\": \"00 000000\"\r\n" + 
					"                }\r\n" + 
					"        },\r\n" + 
					"        {\r\n" + 
					"                \"id\": \"c206\",\r\n" + 
					"                \"name\": \"Adele\",\r\n" + 
					"                \"email\": \"adele@gmail.com\",\r\n" + 
					"                \"address\": \"xx-xx-xxxx,x - street, x - country\",\r\n" + 
					"                \"gender\" : \"female\",\r\n" + 
					"                \"phone\": {\r\n" + 
					"                    \"mobile\": \"+91 0000000000\",\r\n" + 
					"                    \"home\": \"00 000000\",\r\n" + 
					"                    \"office\": \"00 000000\"\r\n" + 
					"                }\r\n" + 
					"        },\r\n" + 
					"        {\r\n" + 
					"                \"id\": \"c207\",\r\n" + 
					"                \"name\": \"Hugh Jackman\",\r\n" + 
					"                \"email\": \"hugh_jackman@gmail.com\",\r\n" + 
					"                \"address\": \"xx-xx-xxxx,x - street, x - country\",\r\n" + 
					"                \"gender\" : \"male\",\r\n" + 
					"                \"phone\": {\r\n" + 
					"                    \"mobile\": \"+91 0000000000\",\r\n" + 
					"                    \"home\": \"00 000000\",\r\n" + 
					"                    \"office\": \"00 000000\"\r\n" + 
					"                }\r\n" + 
					"        },\r\n" + 
					"        {\r\n" + 
					"                \"id\": \"c208\",\r\n" + 
					"                \"name\": \"Will Smith\",\r\n" + 
					"                \"email\": \"will_smith@gmail.com\",\r\n" + 
					"                \"address\": \"xx-xx-xxxx,x - street, x - country\",\r\n" + 
					"                \"gender\" : \"male\",\r\n" + 
					"                \"phone\": {\r\n" + 
					"                    \"mobile\": \"+91 0000000000\",\r\n" + 
					"                    \"home\": \"00 000000\",\r\n" + 
					"                    \"office\": \"00 000000\"\r\n" + 
					"                }\r\n" + 
					"        },\r\n" + 
					"        {\r\n" + 
					"                \"id\": \"c209\",\r\n" + 
					"                \"name\": \"Clint Eastwood\",\r\n" + 
					"                \"email\": \"clint_eastwood@gmail.com\",\r\n" + 
					"                \"address\": \"xx-xx-xxxx,x - street, x - country\",\r\n" + 
					"                \"gender\" : \"male\",\r\n" + 
					"                \"phone\": {\r\n" + 
					"                    \"mobile\": \"+91 0000000000\",\r\n" + 
					"                    \"home\": \"00 000000\",\r\n" + 
					"                    \"office\": \"00 000000\"\r\n" + 
					"                }\r\n" + 
					"        },\r\n" + 
					"        {\r\n" + 
					"                \"id\": \"c2010\",\r\n" + 
					"                \"name\": \"Barack Obama\",\r\n" + 
					"                \"email\": \"barack_obama@gmail.com\",\r\n" + 
					"                \"address\": \"xx-xx-xxxx,x - street, x - country\",\r\n" + 
					"                \"gender\" : \"male\",\r\n" + 
					"                \"phone\": {\r\n" + 
					"                    \"mobile\": \"+91 0000000000\",\r\n" + 
					"                    \"home\": \"00 000000\",\r\n" + 
					"                    \"office\": \"00 000000\"\r\n" + 
					"                }\r\n" + 
					"        },\r\n" + 
					"        {\r\n" + 
					"                \"id\": \"c2011\",\r\n" + 
					"                \"name\": \"Kate Winslet\",\r\n" + 
					"                \"email\": \"kate_winslet@gmail.com\",\r\n" + 
					"                \"address\": \"xx-xx-xxxx,x - street, x - country\",\r\n" + 
					"                \"gender\" : \"female\",\r\n" + 
					"                \"phone\": {\r\n" + 
					"                    \"mobile\": \"+91 0000000000\",\r\n" + 
					"                    \"home\": \"00 000000\",\r\n" + 
					"                    \"office\": \"00 000000\"\r\n" + 
					"                }\r\n" + 
					"        },\r\n" + 
					"        {\r\n" + 
					"                \"id\": \"c2012\",\r\n" + 
					"                \"name\": \"Eminem\",\r\n" + 
					"                \"email\": \"eminem@gmail.com\",\r\n" + 
					"                \"address\": \"xx-xx-xxxx,x - street, x - country\",\r\n" + 
					"                \"gender\" : \"male\",\r\n" + 
					"                \"phone\": {\r\n" + 
					"                    \"mobile\": \"+91 0000000000\",\r\n" + 
					"                    \"home\": \"00 000000\",\r\n" + 
					"                    \"office\": \"00 000000\"\r\n" + 
					"                }\r\n" + 
					"        }\r\n" + 
					"    ]\r\n" + 
					"}");
		}

// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
	/**
	 * Handles the HTTP <code>GET</code> method.
	 *
	 * @param request  servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException      if an I/O error occurs
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * Handles the HTTP <code>POST</code> method.
	 *
	 * @param request  servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException      if an I/O error occurs
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
