

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;


@WebServlet(urlPatterns = {"/Register"})
public class Register extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String dbUrl ="jdbc:mysql://localhost:3306/voterportal";
        String UserName = "root";
        String Password = "root";
        String id = request.getParameter("voter_card_number");
        String fullname = request.getParameter("name");
        String username = request.getParameter("username");
        String gender = request.getParameter("gender");
        String dob = request.getParameter("dob");
        String mail = request.getParameter("email");
        String password = request.getParameter("password");
        password=encryptThisString(password);
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection myConn = DriverManager.getConnection(dbUrl,UserName,Password);
            Statement stmt = myConn.createStatement();
            out.println("Connection Established");
            
           /* String table = "CREATE table register(id int,fullname varchar(255),username varchar(255),gender varchar(255),dob varchar(255),main varchar(255),password varchar(255))";
            stmt.execute(table);
            out.println("created");*/
           String insert = "INSERT into register values('"+id+"','"+fullname+"','"+username+"','"+gender+"','"+dob+"','"+mail+"','"+password+"')";
           stmt.execute(insert);
           out.println("Inserted");
           response.sendRedirect("home.jsp");
          
        }
        catch(Exception ex){
            out.println(ex);
            
        
        }
    }
    public static String encryptThisString(String input)
	    {
	        try {
	            MessageDigest md = MessageDigest.getInstance("SHA-512");

	            byte[] messageDigest = md.digest(input.getBytes());

	       
	            BigInteger no = new BigInteger(1, messageDigest);

	          
	            String hashtext = no.toString(16);

	            while (hashtext.length() < 32) {
	                hashtext = "0" + hashtext;
	            }

	            return hashtext;
	        }

	      
	        catch (NoSuchAlgorithmException e) {
	            throw new RuntimeException(e);
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
            throws ServletException, IOException {
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
