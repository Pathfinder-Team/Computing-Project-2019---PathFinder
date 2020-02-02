package path;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "GetImageAction", urlPatterns = { "/GetImageAction" })
public class GetImageAction extends HttpServlet {

	private static final int BUFFER_SIZE = 4096;
	Timestamp timestamp = new Timestamp(System.currentTimeMillis());
	Connection conn;
	Connection conn2;
	Statement stmt;

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
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		String jsp_org_name2 = request.getParameter("map_name");
		String org_name = request.getParameter("org_name");
		//System.out.println("map_name: "+jsp_org_name2);
		try {
			
			PreparedStatement prepStat = conn.prepareStatement("select maps.map_image,maps.map_name "
					+ "from maps "
					+ "join organisation "
					+ "on maps.org_name = organisation.organisation_name "
					+ "where org_name = ?");
			prepStat.setString(1,org_name );
			
			result = prepStat.executeQuery();
			
			String imgLen = "";
			while (result.next()) 
			{
				String check = result.getString("map_name");
				if(check.equals(jsp_org_name2))
				{
					imgLen = result.getString("map_image");
						int len = imgLen.length();
						byte[] rb = new byte[len];
						InputStream readImg = result.getBinaryStream(1);
						int index = readImg.read(rb, 0, len);
						prepStat.close();
						response.reset();
						response.setContentType("image/jpg");
						response.getOutputStream().write(rb, 0, len);
						response.getOutputStream().flush();
						break;
				}
			}
			
			/*
			rs1 = stmt.executeQuery("select map_image "
					+ "from maps "
					+ "join organisation "
					+ "on maps.org_name = organisation.organisation_name "
					+ "where org_name = 'Limerick Institute of Technology'");
			
			
			if (rs1.next()) {
				int len = imgLen.length();
				byte[] rb = new byte[len];
				InputStream readImg = rs1.getBinaryStream(1);
				int index = readImg.read(rb, 0, len);
				//System.out.println("index " + index);
				stmt.close();
				response.reset();
				response.setContentType("image/jpg");
				response.getOutputStream().write(rb, 0, len);
				response.getOutputStream().flush();
			}
			*/
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("Error 3: " + e);
		}
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
