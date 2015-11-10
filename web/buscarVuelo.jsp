<%-- 
    Document   : buscarVuelo
    Created on : 22-sep-2015, 17:12:50
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
        <title>Buscador de vols</title>
    </head>
    <body>
        <h1>Buscador de vols</h1>
        <h2>Ompli els camps per filtrar la cerca</h2>
        <br>
        <form action="buscarVuelo" method="GET">
            <table>
                <tr>
                    <th>Numero de vol:</th>
                    <td><center><input name=numero_vol size=6 maxlength=6 ></center></td>
                    <th>Companyia:</th>
                    <td><select name=companyia>
                            <option selected VALUE=Qualsevol> Qualsevol</option>
                            <%
                                Class.forName("org.sqlite.JDBC");
                                Connection connection = null;
                                try {
                                    connection = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\Rio\\Dropbox\\UPC\\AD\\P5\\datasqlite3.db");
                                    Statement statement = connection.createStatement();
                                    ResultSet rs = statement.executeQuery("select distinct companyia from vols");
                                    while(rs.next()){
                                        %>  <option VALUE= <% out.print(rs.getString("companyia")); %> > <% out.print(rs.getString("companyia"));%> </option>    <%
                                    }
                            %>    
                        </select>
                    </td>
                </tr>
                <tr>
                    <th>Ciutat d'origen:</th>
                    <td><select name=origen>
                            <option selected VALUE=Qualsevol> Qualsevol</option>
                            <% rs = statement.executeQuery("select distinct origen from vols");
            
                            while(rs.next()){
                                %>  <option VALUE= "<% out.print(rs.getString("origen"));%>" > <% out.print(rs.getString("origen"));%> </option>    <%
                            }
                            %>
                        </select>
                    </td>
                    <th>Hora de sortida:</th>
                    <td><select name=hora_sortida>
                            <option selected VALUE=Qualsevol>Qualsevol
                            <option VALUE=00>00
                            <option VALUE=01>01
                            <option VALUE=02>02
                            <option VALUE=03>03
                            <option VALUE=04>04
                            <option VALUE=05>05
                            <option VALUE=06>06
                            <option VALUE=07>07
                            <option VALUE=08>08
                            <option VALUE=09>09
                            <option VALUE=10>10
                            <option VALUE=11>11
                            <option VALUE=12>12
                            <option VALUE=13>13
                            <option VALUE=14>14
                            <option VALUE=15>15
                            <option VALUE=16>16
                            <option VALUE=17>17
                            <option VALUE=18>18
                            <option VALUE=19>19
                            <option VALUE=20>20
                            <option VALUE=21>21
                            <option VALUE=22>22
                            <option VALUE=23>23
                        </select>
                    </td>
                    <th>:</th>
                    <td><select name=hora_sortida_mins>
                            <option selected VALUE=Qualsevol>Qualsevol
                            <option VALUE=00>00
                            <option VALUE=05>05
                            <option VALUE=15>15
                            <option VALUE=20>20
                            <option VALUE=25>25
                            <option VALUE=30>30
                            <option VALUE=35>35
                            <option VALUE=40>40
                            <option VALUE=45>45
                            <option VALUE=50>50
                            <option VALUE=55>55
                        </select>
                    </td>
                </tr>
                <tr>
                    <th>Ciutat destí:</th>
                    <td><select name=desti>
                            <option selected VALUE=Qualsevol> Qualsevol</option>
                           <% rs = statement.executeQuery("select distinct desti from vols");
            
                            while(rs.next()){
                                %>  <option VALUE= <% out.print(rs.getString("desti")); %> > <% out.print(rs.getString("desti"));%> </option>    <%
                            }
                            %>
                        </select>
                    </td>
                    <th>Hora d'arribada:</th>
                    <td><select name=hora_arribada_mins>
                            <option selected VALUE=Qualsevol>Qualsevol
                            <option VALUE=00>00
                            <option VALUE=01>01
                            <option VALUE=02>02
                            <option VALUE=03>03
                            <option VALUE=04>04
                            <option VALUE=05>05
                            <option VALUE=06>06
                            <option VALUE=07>07
                            <option VALUE=08>08
                            <option VALUE=09>09
                            <option VALUE=10>10
                            <option VALUE=11>11
                            <option VALUE=12>12
                            <option VALUE=13>13
                            <option VALUE=14>14
                            <option VALUE=15>15
                            <option VALUE=16>16
                            <option VALUE=17>17
                            <option VALUE=18>18
                            <option VALUE=19>19
                            <option VALUE=20>20
                            <option VALUE=21>21
                            <option VALUE=22>22
                            <option VALUE=23>23
                        </select>
                    </td>
                    <th>:</th>
                    <td><select name=hora_sortida_mins>
                            <option selected VALUE=Qualsevol>Qualsevol
                            <option VALUE=00>00
                            <option VALUE=05>05
                            <option VALUE=15>15
                            <option VALUE=20>20
                            <option VALUE=25>25
                            <option VALUE=30>30
                            <option VALUE=35>35
                            <option VALUE=40>40
                            <option VALUE=45>45
                            <option VALUE=50>50
                            <option VALUE=55>55
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