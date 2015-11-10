<%-- 
    Document   : newjspbuscarHotel
    Created on : 22-sep-2015, 17:11:21
    Author     : david.molins.goma
--%>
<%@page import="java.sql.Connection"%>
<%@page import="java.io.IOException"%>
<%@page import="java.io.PrintWriter"%>
<%@page import="javax.servlet.ServletException"%>
<%@page import="javax.servlet.annotation.WebServlet"%>
<%@page import="javax.servlet.http.HttpServlet"%>
<%@page import="javax.servlet.http.HttpServletRequest"%>
<%@page import="javax.servlet.http.HttpServletResponse"%>
<%@page import="java.sql.*"%>
<%@page import="java.util.logging.Level"%>
<%@page import="java.util.logging.Logger"%>
<%@page import="javax.servlet.RequestDispatcher"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Buscador d'hotels</title>
    </head>
    <body>
        <h1>Buscador d'hotels</h1>
        <h2>Ompli els camps per filtrar la cerca</h2>
        <br>
        <form action="buscarHotel" method="GET">
            <table summary="">
                <tr>
                    <th>Nom de l'hotel:</th>
                    <td><input name=nom_hotel size=20 maxlength=20></td>
                    <th>Cadena hotelera:</th>
                    <td><select name=companyia>
                            <option selected VALUE=Qualsevol> Qualsevol</option>
                    <%
                            Class.forName("org.sqlite.JDBC");
                            Connection connection = null;
                            try{
                                connection = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\Rio\\Dropbox\\UPC\\AD\\P5\\datasqlite3.db");
                                Statement statement = connection.createStatement();
                                ResultSet rs = statement.executeQuery("select distinct cadena as busca from hotels");

                                while(rs.next()){
                                    %>  <option VALUE= <% out.print(rs.getString("busca")); %> > <% out.print(rs.getString("busca"));%> </option>    <%
                                }
                    %>    
                        </select>
                    </td>
                </tr>
                <tr>
                    <th>Ciutat:</th>
                    <td><select name=ciutat>
                            <option selected VALUE=Qualsevol> Qualsevol</option>
                    <% 
                                rs = statement.executeQuery("select distinct ciutat as busca from hotels");
                                while(rs.next()){
                                    %>  <option VALUE= <% out.print(rs.getString("busca")); %> > <% out.print(rs.getString("busca"));%> </option>    <%
                                }
                    %>
                        </select>
                    </td>
                    <th>Estrelles:</th>
                    <td><select name=clase>
                            <option selected VALUE=Qualsevol> Qualsevol</option>
                            <option VALUE=1>*
                            <option VALUE=2>**
                            <option VALUE=3>***
                            <option VALUE=4>****
                            <option VALUE=5>*****
                        </select>
                    </td>
                </tr>
            </table>
            <br>
            <br>
            <input name=Busca type=submit value="Buscar">
            <br>
        </form>
        <br>
        <h3><a href="menu.html">Tornar al menu</a></h3>
    </body>
    <% 
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
    %>
</html>
