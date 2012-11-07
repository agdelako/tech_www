import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 *
 * @author AD
 */
public class Registration extends HttpServlet {

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
        throws ServletException, IOException {
        
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            Connection connect = null;
            Statement statement = null;
            ResultSet result = null;
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connect =DriverManager.getConnection
            ("jdbc:mysql://localhost:3306/ImageStuck","root","53042583");
            statement = connect.createStatement();
             
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String email = request.getParameter("email");
            if(username=="" || username==null){
                out.println("You need to enter a username. Please return to the registration page and enter one.");
            }
            else if(password=="" || password==null){
                out.println("You need to enter a password. Please return to the registration page and enter one.");
            }
            else{
            
             /* Add user to the database after checking availability */
                String findUser = "SELECT username FROM Users WHERE username = \"" + username + "\"";
                result = statement.executeQuery(findUser);
                if ( result.next() ){
                
                    out.println("Username " + result.getString("username") + " already exists. Please return to the registration page and choose a new one.");  
                }else{
                    String addUser = "INSERT INTO Users VALUES ('" + username + "', '" + password + "', '" + email + "' )" ;
                    statement.executeUpdate(addUser);

                    String location = "http://agdelako.no-ip.org:8080/imagestuck/home.html";
                    response.setContentType("text/html");
                    response.sendRedirect(location);
                } 
           }
         }
         catch (SQLException e){
               throw new ServletException("Servlet Could not display records.", e);
         } catch (ClassNotFoundException e) {
                throw new ServletException("JDBC Driver not found.", e);
        } 
           finally {
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
