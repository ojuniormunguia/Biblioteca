<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link href="css/StyleForm.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
        <div class="menu-superior">
            <div class="menu-1"></div>
            <div class="menu-2"></div>
            <div class="menu-3"></div>
        </div>
        <div class="row">
            <div class="columnindex left">
                <img src="https://images.pexels.com/photos/256431/pexels-photo-256431.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1" alt="Your Image Alt Text">
            </div>
            <div class="columnindex right">
                <form action="SrvDocumentos" method="post">
            
                    <h1>Bienvenido a nuestra biblioteca</h1>
                    <p>Puede empezar iniciando sesión</p>
                    <input type="hidden" id="id" name="id">
                    
                    <label for="nombre_documento">Buscar por título</label>
                    <input type="text" id="nombre_documento" name="nombre_documento">
                    <input type="submit" value="Ir a la librería">

                </form>
            </div>
        </div>
    </body>
</html>
