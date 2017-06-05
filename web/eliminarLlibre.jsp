<%-- 
    Document   : eliminarLlibre
    Created on : 04-jun-2017, 22:12:40
    Author     : Sergi
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%@ include file="myHeader.html" %>

        <form action="GestioLlibres?accio=eliminarLlibre" method="post">            
            <table cellspacing="2" cellpadding="2" border="0" align="center">
                <tr>
                    <td align="right">ISBN:</td>
                    <td><input type="Text" name="isbn_" size="13"></td>
                </tr>
                <tr>
                    <td colspan="2" align="center"><input type="Submit" value="Eliminar"></td>
                </tr>                

            </table>   

            <% String resposta = (String) request.getAttribute("eliminarLlibre");
            %>
            <a ><%=(resposta == null) ? "" : resposta%> </a>

        </form>

        <%@ include file="myFooter.html" %>
    </body>
</html>
