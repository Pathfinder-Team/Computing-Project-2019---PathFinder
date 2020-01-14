package path;
/* 
Authors: Kevin Dunne, Jekaterina Pavlenko, Christopher Costelloe
Date: 11/12/2019
Program: PathFinder website application - Login servlet
 */
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

@WebServlet(name = "LoginDB", urlPatterns =
{
    "/LoginDB"
})
public class LoginDB extends HttpServlet
{
    Connection conn;
    Statement stmt;
    ResultSet result;
    
   
	
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {

    	SQLConnection connect = new SQLConnection();
        try
        {
        	//Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            Class.forName("com.mysql.cj.jdbc.Driver");
            // setup the connection with the DB
            conn = DriverManager.getConnection(connect.URL, connect.USERNAME, connect.PASSWORD);
            //conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/?user=root");
            
            System.out.println("Connected");
        } catch (ClassNotFoundException | SQLException e)
        {
            System.err.println("Error 1 " + e);
        }
    	
    	
        // get the username and password from the login.html form
        String checkUsername = request.getParameter("user_name");
        String checkPassowrd = request.getParameter("password");
        
        boolean found = false;
        PreparedStatement prepStat;
        try
        {
            
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
                String getNameRights = checkUsername;
                response.sendRedirect("ControlDB");
            } else
            {
                // display a message using a jsp page that the was an error
                String errorMessage = "The retrived password or username did not match";
                request.setAttribute("error", errorMessage);
                RequestDispatcher rd = request.getRequestDispatcher("/login.html");
                rd.forward(request, response);
            }
        } catch (IOException | SQLException e)
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