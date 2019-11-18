/* 
Authors: Kevin Dunne, Jekaterina Pavlenko
Date: 7/4/19
Program: Website for enterprise application development
 */
import java.sql.*;
import javax.servlet.ServletException;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet(name = "RegisterDB", urlPatterns =
{
    "/RegisterDB"
})
public class RegisterDB extends HttpServlet
{

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
        // get the values from the register.html form and store in these variables
        first_name = request.getParameter("first_name");
        last_name = request.getParameter("last_name");
        user_name = request.getParameter("user_name");
        password = request.getParameter("password");
        email = request.getParameter("email");
        try
        {
            // create a query that inserts all form values into the users table
            String query = "insert into users values(?, ?, ?, ?, ?, ?, ?, ?)";
            prepStat = conn.prepareStatement(query);
            prepStat.setInt(1, 0);
            prepStat.setString(2, user_name);
            prepStat.setString(3, last_name);
            prepStat.setString(4, first_name);
            prepStat.setString(5, password);
            prepStat.setString(6, email);
            prepStat.setTimestamp(7, timestamp);
            prepStat.setInt(8, 2);
            prepStat.executeUpdate();
            // send us to the forum.html page once query done
            response.sendRedirect("forum.html");

        } catch (IOException | SQLException e)
        {
            System.err.println("Error 2 " + e);
            response.sendRedirect("register.html");
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
