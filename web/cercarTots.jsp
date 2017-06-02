<%-- 
    Document   : cercarTots
    Created on : 01-jun-2017, 17:31:45
    Author     : ALUMNEDAM
--%>

<%@page import="control.GestioLlibres"%>
<%@page import="model.Llibre"%>
<%@page import="java.util.List"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Llibres existents:</h1>
        <ul>
            <%      
              GestioLlibres g = new GestioLlibres();
              List<Llibre> llibres = g.cercarTots();
                //llistat de persones
                for (Llibre l : llibres) {
                    out.println("<li>"+l.getAutor() +"  "+l.getEditorial()+"  "+l.getIsbn()+"  "+l.getTitol()+"  "+l.getAnyEdicio()+"  "+l.getEstoc()+"</li>");
                }
            %>
        </ul>
    </body>
</html>
