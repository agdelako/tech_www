/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.*;
import javax.servlet.http.*;
/**
 *
 * @author AD
 */
public class DBConnect extends HttpServlet {

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
public void doPost(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException{
               
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            Connection connect = null;
            Statement statement = null;
            ResultSet result = null;
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connect =DriverManager.getConnection
            ("jdbc:mysql://localhost:3306/ImageStuck","root","password");
            statement = connect.createStatement();
             
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String email = request.getParameter("email");
            
             /* Check if user already exists to the database and login */
            String findUser = "SELECT * FROM Users WHERE username =\""+username+"\" AND password = \""+password+ "\"";
            result = statement.executeQuery(findUser);
            if ( result.next() ){
                
                String location = "http://agdelako.no-ip.org:8080/imagestuck/home.html";
                response.setContentType("text/html");
                response.sendRedirect(location);
                   
            }else{
                
               out.println("Username or/and password combination is incorrect. Please return to the login page and enter a correct one.");
            }  
         } catch (SQLException e) {
           throw new ServletException("Servlet Could not display records.", e);
           } catch (ClassNotFoundException e) {
           throw new ServletException("JDBC Driver not found.", e);
             } finally {
             try {
             if(result != null) {
             result.close();
             result = null;
             }
             if(statement != null) {
             statement.close();
             statement = null;
             }
             if(connect != null) {
             connect.close();
             connect = null;
             }
             } catch (SQLException e) {}
             }
             out.close();
             }
  
}
