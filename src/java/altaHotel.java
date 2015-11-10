/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author david.molins.goma
 */
@WebServlet(urlPatterns = {"/altaHotel"})
public class altaHotel extends HttpServlet {
    
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
            throws ServletException, IOException, ClassNotFoundException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        /* TODO output your page here. You may use following sample code. */
        Class.forName("org.sqlite.JDBC");
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\Rio\\Dropbox\\UPC\\AD\\P5\\datasqlite3.db");
            Statement statement = connection.createStatement();
            //Hem de controlar que els camps no estiguin buits, excepte cadena i estrelles, que mai tindran valors nulls gracies al desplegable
            String nom_hotel = request.getParameter("nom_hotel");
            String carrer = request.getParameter("carrer");
            String numero = request.getParameter("numero");
            String codi_postal = request.getParameter("codi_postal");
            String ciutat = request.getParameter("ciutat");
            String provincia = request.getParameter("provincia");
            String pais = request.getParameter("pais");
            String num_habitacions = request.getParameter("num_habitacions");
            String update = "error";
            ResultSet rs = statement.executeQuery("select count (*) as total from hotels where nom = \""+ request.getParameter("nom_hotel") +"\"");
            if("1".equals(rs.getString("total"))) {
                out.println("<p><h3><font color=#F70D1A> ERROR: Ja existeix l'hotel a la base de dades </font></h3><p>");
            }
            else {
                if (nom_hotel != null && !nom_hotel.isEmpty()) {
                    if (carrer != null && !carrer.isEmpty()) {
                        if (numero != null && !numero.isEmpty()) {
                            if (codi_postal != null && !codi_postal.isEmpty()) {
                                if (ciutat != null && !ciutat.isEmpty()) {
                                    if (provincia != null && !provincia.isEmpty()) {
                                        if (pais != null && !pais.isEmpty()) {
                                            if (num_habitacions != null && !num_habitacions.isEmpty()) {
                                                update = "insert into hotels values ( '"+ 
                                                        request.getParameter("nom_hotel") +"','"+
                                                        request.getParameter("companyia") +"','"+
                                                        request.getParameter("carrer") +"','"+
                                                        request.getParameter("numero") +"','"+
                                                        request.getParameter("codi_postal") +"','"+
                                                        request.getParameter("ciutat") +"','"+
                                                        request.getParameter("provincia") +"','"+
                                                        request.getParameter("pais") +"','"+
                                                        request.getParameter("num_habitacions") +"','"+
                                                        request.getParameter("clase")+"');";
                                                statement.executeUpdate(update);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                if (update != "error") out.println("<p><h3><font color=#347C2C> L'hotel s'ha afegit amb exit </font></h3><p>");
                else out.println("<p><h3><font color=#F70D1A> ERROR: Algun camp estava buit. No s'ha afegit l'hotel </font></h3><p>");
            }
            RequestDispatcher rd = request.getRequestDispatcher("menu.html");
            rd.include(request, response);
            connection.close();
        }
        catch(SQLException e) {
            System.err.println(e.getMessage());
        }
        finally {
            try {
                if(connection != null)
                    connection.close();
            }
            catch(SQLException e) {
                //Error en tancar la connexio
                System.err.println(e.getMessage());
            }
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
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(altaHotel.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(altaHotel.class.getName()).log(Level.SEVERE, null, ex);
        }
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
