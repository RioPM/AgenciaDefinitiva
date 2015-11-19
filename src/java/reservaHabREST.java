/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;

/**
 *
 * @author Rio
 */
@WebServlet(urlPatterns = {"/reservaHabREST"})
public class reservaHabREST extends HttpServlet {

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
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet reservaHabREST</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet reservaHabREST at " + request.getContextPath() + "</h1>");
            /* Servlet per provar HotelWS */
            String p = request.getParameter("idHotel");
            int idHotel = Integer.parseInt(p);
            p = request.getParameter("fecha");
            int fecha = Integer.parseInt(p);
            /* Crida a les operacions consulta i reserva */
            HabClient client = new HabClient();
            String ocupades = client.reserva_habitacion(String.class, String.valueOf(idHotel), String.valueOf(fecha));
            out.println("<br>");
            out.println("Ara n'hi ha "+ocupades+" ocupades");
            out.println("<br>");
            out.println("<br> <a href=\"reservaHab.html\">Tornar a la reserva d'habitacions</a>");
            out.println("<br> <h3><a href=\"menu.html\">Tornar al menu</a></h3>");
            out.println("<br>");
            out.println("</body>");
            out.println("</html>");
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

    static class HabClient {

        private WebTarget webTarget;
        private Client client;
        private static final String BASE_URI = "http://localhost:8080/AgenciaRESTDefinitiva/webresources/";

        public HabClient() {
            client = javax.ws.rs.client.ClientBuilder.newClient();
            webTarget = client.target(BASE_URI).path("hotel");
        }

        /**
         * @param responseType Class representing the response
         * @param id_hotel query parameter
         * @param fecha query parameter
         * @return response object (instance of responseType class)
         */
        public <T> T consulta_libres(Class<T> responseType, String id_hotel, String fecha) throws ClientErrorException {
            String[] queryParamNames = new String[]{"id_hotel", "fecha"};
            String[] queryParamValues = new String[]{id_hotel, fecha};
            ;
            javax.ws.rs.core.Form form = getQueryOrFormParams(queryParamNames, queryParamValues);
            javax.ws.rs.core.MultivaluedMap<String, String> map = form.asMap();
            for (java.util.Map.Entry<String, java.util.List<String>> entry : map.entrySet()) {
                java.util.List<String> list = entry.getValue();
                String[] values = list.toArray(new String[list.size()]);
                webTarget = webTarget.queryParam(entry.getKey(), (Object[]) values);
            }
            return webTarget.request().get(responseType);
        }

        /**
         * @param responseType Class representing the response
         * @param id_vuelo form parameter
         * @param fecha form parameter
         * @return response object (instance of responseType class)
         */
        public <T> T reserva_habitacion(Class<T> responseType, String id_vuelo, String fecha) throws ClientErrorException {
            String[] formParamNames = new String[]{"id_vuelo", "fecha"};
            String[] formParamValues = new String[]{id_vuelo, fecha};
            return webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_FORM_URLENCODED).post(javax.ws.rs.client.Entity.form(getQueryOrFormParams(formParamNames, formParamValues)), responseType);
        }

        private Form getQueryOrFormParams(String[] paramNames, String[] paramValues) {
            Form form = new javax.ws.rs.core.Form();
            for (int i = 0; i < paramNames.length; i++) {
                if (paramValues[i] != null) {
                    form = form.param(paramNames[i], paramValues[i]);
                }
            }
            return form;
        }

        public void close() {
            client.close();
        }
    }

}
