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
@WebServlet(name = "EditActionDB", urlPatterns =
{
    "/EditActionDB"
})
public class EditActionDB extends HttpServlet
{

	String organisation_name = "";
	String old_organisation_name = "";
	String organisation_address = "";
	String organisation_email = "";
	int organisation_mobile = 0;
	String organisation_building_name = "";
	String user_org_name = "";

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
        } catch (ClassNotFoundException | SQLException e)
        {
            System.err.println("Error 1" + e);
        }
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {

        
        organisation_name = request.getParameter("organisation_name");
        old_organisation_name = request.getParameter("old_organisation_name");
        organisation_address = request.getParameter("organisation_address");
        organisation_email = request.getParameter("organisation_email");
        organisation_building_name = request.getParameter("organisation_building_name");
        organisation_mobile = Integer.parseInt(request.getParameter("organisation_mobile"));
        
        System.out.println("organisation_name: "+organisation_name);
        System.out.println("old_organisation_name: "+old_organisation_name);
        System.out.println("organisation_address: "+organisation_address);
        System.out.println("organisation_email: "+organisation_email);
        System.out.println("organisation_building_name: "+organisation_building_name);
        System.out.println("organisation_mobile: "+organisation_mobile);
        try
        {
            String query = "update organisation "
            		+ "set organisation_name = ?,"
            		+ "organisation_address = ?,"
            		+ "organisation_email = ?,"
            		+ "organisation_mobile = ?,"
            		+ "organisation_building_name = ? "
            		+ "where organisation_name = ?";
            prepStat = conn.prepareStatement(query);
            
            prepStat.setString(1, organisation_name);
            prepStat.setString(2, organisation_address);
            prepStat.setString(3, organisation_email);
            prepStat.setInt(4, organisation_mobile);
            prepStat.setString(5, organisation_building_name);
            prepStat.setString(6, old_organisation_name);
            prepStat.executeUpdate();

        } catch (SQLException e)
        {
            System.err.println("Error 2 " + e);
        }
        response.sendRedirect("DetailsDB");

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
