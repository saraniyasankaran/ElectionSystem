/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 *
 * @author saraniya
 */
@WebServlet(urlPatterns = {"/AddParty"})
@MultipartConfig(maxFileSize = 16177215)
public class AddParty extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String code = request.getParameter("party_code");
        String partyname = request.getParameter("pName");
        InputStream inputStream = null;
        String message = null;
        Part filePart = request.getPart("image");
        if (filePart != null){
            out.println(filePart.getName());
            out.println(filePart.getSize());
            out.println(filePart.getContentType());
            inputStream = filePart.getInputStream();
        }
        
        String dbUrl ="jdbc:mysql://localhost:3306/voterportal";
        String UserName = "root";
        String Password = "root";
         try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection myConn = DriverManager.getConnection(dbUrl,UserName,Password);
            Statement stmt = myConn.createStatement();
            out.println("Connection Established");
            
           /* String table = "CREATE table addparty(partycode varchar(255), partyname varchar(255),file mediumblob)";
            stmt.execute(table);
            out.print("Created");*/
           String insert = "INSERT into addparty values('"+code+"','"+partyname+"','"+filePart+"')";
           int row = stmt.executeUpdate(insert);
            if (row > 0) {
                message = "File uploaded and saved into database";
                out.println(message);
            }
            response.sendRedirect("addParty.jsp");
            
        }
         catch(Exception ex){
                 out.println(ex);
             
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
