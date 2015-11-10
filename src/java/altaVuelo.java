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
@WebServlet(urlPatterns = {"/altaVuelo"})
public class altaVuelo extends HttpServlet {

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
            //Hem de controlar que els camps no estiguin buits, excepte companyia, origen i desti, que mai tindran valors nulls gracies al desplegable
            String numero_vol = request.getParameter("numero_vol");
            String hora_sortida = request.getParameter("hora_sortida");
            String hora_sortida_mins = request.getParameter("hora_sortida_mins");
            //int sortida_int = Integer.parseInt(hora_sortida);
            String hora_arribada = request.getParameter("hora_arribada");
            String hora_arribada_mins = request.getParameter("hora_arribada_mins");
            //int arribada_int = Integer.parseInt(hora_arribada);
            String update = "error";
            ResultSet rs = statement.executeQuery("select count (*) as total from vols where numero = \""+ request.getParameter("numero_vol") +"\"");
            if("1".equals(rs.getString("total"))) {
                out.println("<p><h3><font color=#F70D1A> ERROR: Ja existeix el vol a la base de dades </font></h3><p>");
            }
            else {
                if (numero_vol != null && !numero_vol.isEmpty()) {
                    if (hora_sortida != null && !hora_sortida.isEmpty()) {
                    //if (hora_sortida != null && !hora_sortida.isEmpty() && sortida_int >= 00 && sortida_int <= 23) {
                        if (hora_arribada != null && !hora_arribada.isEmpty()) {
                        //if (hora_arribada != null && !hora_arribada.isEmpty() && arribada_int >= 00 && arribada_int <= 23) {
                            update = "insert into vols"
                                    + " values ( '"+ 
                                    request.getParameter("numero_vol") +"','"+
                                    request.getParameter("companyia") +"','"+
                                    request.getParameter("origen") +"','"+
                                    request.getParameter("hora_sortida") +"','"+ 
                                    request.getParameter("hora_sortida_mins") +"','"+                   
                                    request.getParameter("desti") +"','"+
                                    request.getParameter("hora_arribada") +"','"+
                                    request.getParameter("hora_arribada_mins")+"');";
                            statement.executeUpdate(update);
                        }
                    }
                }
                if (update != "error") out.println("<p><h3><font color=#347C2C> El vol s'ha afegit amb exit </font></h3><p>");
                else out.println("<p><h3><font color=#F70D1A> ERROR: Algun camp estava buit. No s'ha afegit el vol </font></h3><p>");
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
            Logger.getLogger(altaVuelo.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(altaVuelo.class.getName()).log(Level.SEVERE, null, ex);
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