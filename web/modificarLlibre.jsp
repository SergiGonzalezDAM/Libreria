<%-- 
    Document   : modificarLlibre
    Created on : 04-jun-2017, 22:30:43
    Author     : Sergi
--%>

<%@page import="model.Llibre"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%@ include file="myHeader.html" %>

        <form action="GestioLlibres?accio=modificarLlibre" method="post">            

            <center><b>Dades del llibre:</b></center>
            <br><br>
            <table cellspacing="2" cellpadding="2" border="0" align="center">
                <tr>
                    <td align="right">ISBN</td>
                    <td><input type="Text" name="isbn_" size="13" value=""></td>
                </tr>
                <tr>
                    <td align="right">Autor:</td>
                    <td><input type="Text" name="autor_" size="30" value=""></td>
                </tr>
                <tr>
                    <td align="right">Editorial:</td>
                    <td><input type="Text" name="editorial_" size="20" value=""</td> 
                </tr>               
                <tr>
                    <td align="right">Any edició:</td>
                    <td><input type="Text" name="anyo_" size="4" value=""></td>
                </tr>
                <tr>
                    <td align="right">Estoc:</td>
                    <td><input type="Text" name="estoc_" size="3" value=""></td>
                </tr>
                <tr>
                    <td align="right">Títol:</td>
                    <td><input type="Text" name="titol_" size="30" value=""></td>
                    <td align="center"><input type="Submit" value="Modificar"></td>
                </tr>               

            </table>   


            <% String resposta = (String) request.getAttribute("modificarLlibre");
            %>
            <a ><%=(resposta == null) ? "" : resposta%> </a>
        </form>

        <%@ include file="myFooter.html" %>

    </body>
</html>
