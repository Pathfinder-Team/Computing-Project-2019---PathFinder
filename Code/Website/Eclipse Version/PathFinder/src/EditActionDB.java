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
        } catch (ClassNotFoundException | SQLException e)
        {
            System.err.println("Error 1" + e);
        }
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {

        old_username = request.getParameter("old_username");
        System.out.println("old_username check action "+old_username);
        
        first_name = request.getParameter("first_name");
        last_name = request.getParameter("last_name");
        user_name = request.getParameter("user_name");
        password = request.getParameter("password");
        email = request.getParameter("email");
        Account_Status_Users = Integer.parseInt(request.getParameter("Account_Status_Users"));
        try
        {
            String query = "update users set user_name = ?,first_name = ?,last_name = ?,password = ?,email = ?, Account_Status_Users = ? where user_name = ?";
            prepStat = conn.prepareStatement(query);
            prepStat.setString(1, user_name);
            prepStat.setString(2, first_name);
            prepStat.setString(3, last_name);
            prepStat.setString(4, password);
            prepStat.setString(5, email);
            prepStat.setInt(6, Account_Status_Users);
            prepStat.setString(7, old_username);
            prepStat.executeUpdate();

        } catch (SQLException e)
        {
            System.err.println("Error 2 " + e);
        }
        response.sendRedirect("ChangeDetailsDB");

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
