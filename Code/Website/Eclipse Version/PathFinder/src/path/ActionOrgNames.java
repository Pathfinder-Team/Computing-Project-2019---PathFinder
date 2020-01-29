package path;

import java.util.*;
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
import java.util.ArrayList;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

@WebServlet(name = "ActionOrgNames", urlPatterns =
{
    "/ActionOrgNames"
})
public class ActionOrgNames extends HttpServlet
{
    Connection conn;
    Statement stmt;
    ResultSet result;
    List<String> orgList = new ArrayList<>();
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {

    	System.out.println("LoginDB");
    	SQLConnection connect = new SQLConnection();
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(connect.URL, connect.USERNAME, connect.PASSWORD);
            System.out.println("Connected ActionOrgNames");
        } catch (ClassNotFoundException | SQLException e)
        {
            System.err.println("Error 1 " + e);
        }
        PreparedStatement prepStat;
        try
        {
            String query = "select organisation_name from organisation";
            prepStat = conn.prepareStatement(query);
            result = prepStat.executeQuery();
           
            while (result.next())
            {
            	String name = result.getString("organisation_name");
            	System.out.println("name: "+name);
            	orgList.add(name);
            }
            request.setAttribute("orgList", orgList);
            RequestDispatcher rd = request.getRequestDispatcher("/registerUser.jsp");
            rd.forward(request, response);
        } catch (IOException | SQLException e)
        {
            System.err.println("Special Login Warning: " + e);
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