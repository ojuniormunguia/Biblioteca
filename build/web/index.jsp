<%-- 
    Document   : index
    Created on : 29 nov 2023, 23:00:59
    Author     : theoj
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <form action="SrvDocumentos" method="post">
            <table>
            <tr>
            
                <td><label for="id">Código</label></td>
                <td><label for="nombre_documento">Nombre</label></td>
            </tr>
            <tr>
                <td><input type="text" id="id" name="id"></td>
                <td><input type="text" id="nombre_documento" name="nombre_documento"></td>
                <td><input type="submit" value="Buscar"></td>
            </tr>
            </table>
        </form>
    </body>
</html>
