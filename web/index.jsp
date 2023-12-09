<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link href="css/StyleForm.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>

        <div class="row">
            <div class="columnindex left">
                <img src="https://images.pexels.com/photos/256431/pexels-photo-256431.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1" alt="Your Image Alt Text">
            </div>
            <div class="columnindex right">
                <form action="SrvDocumentos" method="post">
            
                    <h1>Bienvenido a nuestra biblioteca</h1>
                    
                    
                    <input type="hidden" id="userid" name="userid" value=" <%= request.getParameter("userid") %> ">
                    <input type="hidden" id="tipo_usuario" name="tipo_usuario" value=" <%= request.getParameter("tipo_usuario") %> "><!-- permisos -->
                    
                    
                    
                    <input type="hidden" id="nombre_documento" name="nombre_documento">
                    
                    <input type="submit" value="Ir a la librería">

                </form>
                <p>O también puedes iniciar sesión:</p>
                <form action="SrvLogin" method="post">
                    <input type="text" id="usuario" name="usuario">       
                    <input type="text" id="contrasena" name="contrasena">    
                    <input type="submit" value="Iniciar Sesión">
                </form>
                   
            </div>
        </div>
    </body>
</html>
