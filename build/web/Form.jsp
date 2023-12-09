<%-- 
    Document   : Form
    Created on : 6 dic 2023, 21:25:25
    Author     : theoj
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% response.setCharacterEncoding("UTF-8"); %>


<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1" />
  <link href="css/StyleForm.css" rel="stylesheet" type="text/css"/>
</head>

<body>
  <div class="bsqueda-avanzada">
    <div class="menu-superior">
      <div class="menu-1"></div>
      <div class="menu-2"></div>
      <div class="menu-3"></div>
    </div>
    <div class="content">
      <div class="frame">
        <div class="image"></div>
      </div>
      <div class="bg">
            <form action="SrvAdd" method="post">
                
                
              <h1>Editando libro</h1> 
              
              <!-- MANTIENE EL ID Y LOS PERMISOS -->
              <input type="hidden" id="userid" name="userid" value="<%= request.getParameter("userid") %>"> 
              <input type="hidden" id="tipo_usuario" name="tipo_usuario" value="<%= request.getParameter("tipo_usuario") %>">
              <input type="hidden" id="id" name="id" value="<%= request.getParameter("id") %>">

              <label for="nombre_documento">Nombre de Documentos:</label>
              <input type="text" id="nombre_documento" name="nombre_documento" value="<%= request.getParameter("nombre_documento") %>" required>

              <label for="tipo_documentos">Tipo de Documentos:</label>
              <input type="text" id="tipo_documentos" name="tipo_documento" value="<%= request.getParameter("tipo_documento") %>" required>

              <label for="resumen">Resumen:</label>
              <textarea id="resumen" name="resumen" required><%= request.getParameter("resumen") %></textarea>

              <label for="autor">Autor:</label>
              <input type="text" id="autor" name="autor" value="<%= request.getParameter("autor") %>" required>

              <label for="genero">Género:</label>
              <input type="text" id="genero" name="genero" value="<%= request.getParameter("genero") %>" required>

              <label for="ubicacion_fisica">Ubicación Física:</label>
              <input type="text" id="ubicacion_fisica" name="ubicacion_fisica" value=" <%= request.getParameter("ubicacion_fisica") %>" required>

              <label for="cantidades_ejemplares">Cantidad de Ejemplares:</label>
              <input type="number" id="cantidades_ejemplares" name="cantidad_ejemplares" value="<%= request.getParameter("cantidad_ejemplares") %>" required>

                <label for="ejemplares_prestados">Ejemplares Prestados:</label>
                <input type="number" id="ejemplares_prestados" name="ejemplares_prestados" value="<%= request.getParameter("ejemplares_prestados") %>" required>

              <label for="ISBN">ISBN:</label>
              <input type="text" id="ISBN" name="ISBN" value="<%= request.getParameter("ISBN") %>" required>

              <label for="releasedate">Fecha de Publicación:</label>
              <input type="text" id="releasedate" name="releasedate" value="<%= request.getParameter("releasedate") %>" required>

              <label for="image_url">URL de la Imagen:</label>
              <input type="text" id="image_url" name="image_url" value="<%= request.getParameter("image_url") %>" required>

              <input type="submit" value="Submit">
            </form>
              
            <%
                String idParameter = request.getParameter("id");
                boolean showDeleteButton = idParameter != null && !idParameter.isEmpty();
            %>

            <form action="SrvDelete" method="get">
                <input type="hidden" id="userid" name="userid" value="<%= request.getParameter("userid") %>">
                <input type="hidden" id="tipo_usuario" name="tipo_usuario" value="<%= request.getParameter("tipo_usuario") %>">
                
                <input type="hidden" id="id" name="id" value="<%= idParameter %>">

                <!-- Use JSP expression to conditionally render the delete button -->
                <% if (showDeleteButton) { %>
                    <input class="deletebutton" type="Submit" value="Delete">
                <% } %>
            </form>
      </div>
    </div>
  </div>
</body>
</html>
