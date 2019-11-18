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

@WebServlet(name = "LoginDB", urlPatterns =
{
    "/LoginDB"
})
public class LoginDB extends HttpServlet
{
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        Connection conn;
        Statement stmt;
        ResultSet result;

        String URL = "jdbc:mysql://localhost:3306/";
        String DB = "myDB";
        String USERNAME = "root";
        String PASSWORD = "";
        
        // get the username and password from the login.html form
        String checkUsername = request.getParameter("user_name");
        String checkPassowrd = request.getParameter("password");
        boolean found = false;
        PreparedStatement prepStat;
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(URL + DB, USERNAME, PASSWORD);
            stmt = conn.createStatement();
            
            // query to get the user, password from users table where the user_name = username you get from the form
            String query = " select user_name,password from users where user_name = ?";
            prepStat = conn.prepareStatement(query);
            prepStat.setString(1, checkUsername);
            result = prepStat.executeQuery();
            
            // loop through database
            while (result.next())
            {
                // pass the password from database into the variable pwd
                String pwd = result.getString("password");
                // if the value from the data held in pwd matchs the value in checkPassword which holds the value from the login.html form
                if (pwd.equals(checkPassowrd))
                {
                    found = true;
                }
            }
            // if found == true setup some cookies
            if (found == true)
            {
                // storing cookies for the password and username
                Cookie cookUsername = new Cookie("cookUsername", request.getParameter("user_name"));
                Cookie cookPassword = new Cookie("cookPassword", request.getParameter("password"));

                cookUsername.setMaxAge(60 * 60 * 24);
                cookPassword.setMaxAge(60 * 60 * 24);

                response.addCookie(cookUsername);
                response.addCookie(cookPassword);
                response.sendRedirect("forum.html");
            } else
            {
                // display a message using a jsp page that the was an error
                String errorMessage = "The retrived password or username did not match";
                request.setAttribute("error", errorMessage);
                RequestDispatcher rd = request.getRequestDispatcher("/login.jsp");
                rd.forward(request, response);
            }
        } catch (IOException | ClassNotFoundException | SQLException e)
        {
            System.err.println("Special Login Warning" + e);
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
