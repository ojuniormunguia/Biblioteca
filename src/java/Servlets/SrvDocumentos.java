package Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import AccesoDatos.DatabaseConnection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "SrvDocumentos", urlPatterns = {"/SrvDocumentos"})
public class SrvDocumentos extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {
            
            String userid = request.getParameter("userid");
            String permiso = request.getParameter("tipo_usuario");
            String nombreDocumento = request.getParameter("nombre_documento");
            String generofilter = request.getParameter("generofilter");
            String sql;

            try (Connection connection = DatabaseConnection.getConnection()) {
                if (request.getParameter("search") != null) {
                    sql = "SELECT * FROM documentos WHERE nombre_documento LIKE ?";
                    if (generofilter != null && !generofilter.isEmpty()) {
                        sql += " AND genero = ?";
                    }
                    nombreDocumento = "%" + nombreDocumento + "%";
                } else {
                    if (nombreDocumento != null && !nombreDocumento.isEmpty()) {
                        sql = "SELECT * FROM documentos WHERE nombre_documento LIKE ?";
                        if(generofilter != null && !generofilter.isEmpty()){
                            sql += " AND genero = ?";
                        }
                        nombreDocumento = "%" + nombreDocumento + "%";
                    } else {
                        sql = "SELECT * FROM documentos";
                        if(generofilter != null && !generofilter.isEmpty()){
                            sql += " WHERE genero = ?";
                        }
                    }
                }


                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    int paramIndex = 1;
                    if (nombreDocumento != null && !nombreDocumento.isEmpty()) {
                        preparedStatement.setString(paramIndex++, nombreDocumento);
                    }
                    if (generofilter != null && !generofilter.isEmpty()) {
                        preparedStatement.setString(paramIndex, generofilter);
                    }

                    try (ResultSet resultSet = preparedStatement.executeQuery()) {
                        out.println("<!DOCTYPE html>");
                        out.println("<html>");
                        out.println("<head>");
                        out.println("<meta charset=\"utf-8\" />");
                        out.println("<meta name=\"viewport\" content=\"initial-scale=1, width=device-width\" />");
                        out.println("<link href=\"css/styles.css\" rel=\"stylesheet\" type=\"text/css\"/>");
                        out.println("<title>Search Results</title>");
                        out.println("</head>");
                        out.println("<body>");
                        out.println("<div class=\"Pantalla\">");
                        out.println("<div class=\"menu-superior\">");
                        
                        //IZQUIERDA
                        out.println("<div class=\"menu-1\">");
                        out.println("</div>");
                        
                        //EN MEDIO
                        out.println("<div class=\"menu-2\">");
                        
                        if (permiso.startsWith("Administrador") ){
                        //Botón a agregar
                        out.println("<form method=\"post\" action=\"Form.jsp\">");
                        
                        out.println("<input type=\"hidden\" id=\"userid\" name=\"userid\" value=\"" + request.getParameter("userid") + "\">");
                        out.println("<input type=\"hidden\" id=\"tipo_usuario\" name=\"tipo_usuario\" value=\"" + request.getParameter("tipo_usuario") + "\">");
                        
                        out.println("<input type=\"hidden\" name=\"id\" value=\"\">");
                        out.println("<input type=\"hidden\" name=\"nombre_documento\" value=\"\">");
                        out.println("<input type=\"hidden\" name=\"resumen\" value=\"\">");
                        out.println("<input type=\"hidden\" name=\"autor\" value=\"\">");
                        out.println("<input type=\"hidden\" name=\"genero\" value=\"\">");
                        out.println("<input type=\"hidden\" name=\"ubicacion_fisica\" value=\"\">");
                        out.println("<input type=\"hidden\" name=\"cantidad_ejemplares\" value=\"\">");
                        out.println("<input type=\"hidden\" name=\"ejemplares_prestados\" value=\"\">");
                        out.println("<input type=\"hidden\" name=\"ISBN\" value=\"\">");
                        out.println("<input type=\"hidden\" name=\"releasedate\" value=\"\">");
                        out.println("<input type=\"hidden\" name=\"image_url\" value=\"\">");
                        out.println("<input class=\"Buscar-boton\" type=\"submit\" value=\"Agregar +\" name=\"detalles\" style=\"margin-top:12px; margin-left:15px\">");
                        out.println("</form>");
                        }
                        out.println("</div>");
                        
                        //DERECHA
                        out.println("<div class=\"menu-3\">");
                        
                        if (userid == null){
                            
                        out.println("<form method=\"post\" action=\"Login.jsp\">");
                        out.println("<input class=\"button-2\" type=\"submit\" value=\"Iniciar sesión\" name=\"Login\">");
                        
                        }else{
                            
                        out.println("<form method=\"post\" action=\"SrvPrestamos\">");
                        
                        out.println("<input type=\"hidden\" id=\"userid\" name=\"userid\" value=\"" + request.getParameter("userid") + "\">");
                        out.println("<input type=\"hidden\" id=\"tipo_usuario\" name=\"tipo_usuario\" value=\"" + request.getParameter("tipo_usuario") + "\">");
                        
                        out.println("<input class=\"button-2\" type=\"submit\" value=\"ver préstamos\" name=\"Login\">");
                        
                        }
                        out.println("</div>");
                        
                        out.println("</div>");
                        
                        out.println("<div class=\"contenido-pagina\">");
                        out.println("<div class=\"degradado\"></div>");
                        out.println("<div class=\"fondo\">");
                        out.println("<div class=\"contenido\">");
                        out.println("<div class=\"para-resultados\">"); // Open para-resultados div

                        // Iterate through the result set and display the data
                        while (resultSet.next()) {                           

                            out.println("<div class=\"row\">"); //row
                            out.println("<div class=\"column\">"); //column
                            out.println("<div class=\"card\">"); //card
                            out.println("<img src='" + resultSet.getString("image_url") + "' alt='Book Cover' style='width:120px'>");
                            out.println("<h3>" + resultSet.getString("nombre_documento") + "</h3>");
                            out.println("<p>" + resultSet.getString("autor") + "</p>");                           
                            out.println("<p>");
                            out.println("<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"21\" height=\"19\" viewBox=\"0 2 21 13\" fill=\"none\">");
                            out.println("  <path d=\"M10.5 0L12.8574 7.25532H20.4861L14.3143 11.7394L16.6717 18.9947L10.5 14.5106L4.32825 18.9947L6.68565 11.7394L0.513906 7.25532H8.1426L10.5 0Z\" fill=\"#FFC700\"/>");
                            out.println("</svg>");
                            out.println(resultSet.getDouble("estrellas") + "</p>");     
                            out.println("<div class=\"botones\">");
                            
                            
                            //Botón ver
                            out.println("<form class=\"botones left\" method=\"post\" action=\"SrvInfoLibro\">");
                            
                            out.println("<input type=\"hidden\" id=\"userid\" name=\"userid\" value=\"" + userid + "\">");
                            out.println("<input type=\"hidden\" id=\"tipo_usuario\" name=\"tipo_usuario\" value=\"" + permiso + "\">");
                            
                            out.println("<input type=\"hidden\" name=\"id\" value=\"" + resultSet.getString("id") + "\">");
                            out.println("<input class=\"Buscar-boton\" type=\"submit\" value=\"Ver más\" name=\"detalles\">");
                            out.println("</form>");
                            
                            if (permiso.startsWith("Administrador") ){
                            //Botón a editar
                            out.println("<form method=\"post\" action=\"Form.jsp\">");
                            
                            out.println("<input type=\"hidden\" id=\"userid\" name=\"userid\" value=\"" + request.getParameter("userid") + "\">");
                            out.println("<input type=\"hidden\" id=\"tipo_usuario\" name=\"tipo_usuario\" value=\"" + request.getParameter("tipo_usuario") + "\">");
                            
                            out.println("<input type=\"hidden\" name=\"id\" value=\"" + resultSet.getString("id") + "\">");
                            out.println("<input type=\"hidden\" name=\"nombre_documento\" value=\"" + resultSet.getString("nombre_documento") +"\">");
                            out.println("<input type=\"hidden\" name=\"tipo_documento\" value=\"" + resultSet.getString("tipo_documento") +"\">");
                            out.println("<input type=\"hidden\" name=\"resumen\" value=\"" + resultSet.getString("resumen") + "\">");
                            out.println("<input type=\"hidden\" name=\"autor\" value=\"" + resultSet.getString("autor") + "\">");
                            out.println("<input type=\"hidden\" name=\"genero\" value=\"" + resultSet.getString("genero") + "\">");
                            out.println("<input type=\"hidden\" name=\"ubicacion_fisica\" value=\"" + resultSet.getString("ubicacion_fisica") +"\">");
                            out.println("<input type=\"hidden\" name=\"cantidad_ejemplares\" value=\"" + resultSet.getInt("cantidad_ejemplares") + "\">");
                            out.println("<input type=\"hidden\" name=\"ejemplares_prestados\" value=\"" + resultSet.getInt("ejemplares_prestados") + "\">");
                            out.println("<input type=\"hidden\" name=\"ISBN\" value=\"" +resultSet.getString("ISBN") + "\">");
                            out.println("<input type=\"hidden\" name=\"releasedate\" value=\"" + resultSet.getString("releasedate") + "\">");
                            out.println("<input type=\"hidden\" name=\"image_url\" value=\"" + resultSet.getString("image_url") + "\">");
                            out.println("<input class=\"Buscar-boton\" type=\"submit\" value=\"Editar\" name=\"detalles\">");
                            out.println("</form>");
                            } 
                            
                            out.println("</div>"); //botones ends
                            out.println("</div>"); //card
                            out.println("</div>"); //column
                            out.println("</div>"); //row
                        }

                        out.println("</div>"); // Close para-resultados div
                        out.println("</div>"); // Close contenido
                        out.println("<div class=\"principal\">");
                        // Barra de búsqueda básica
                        out.println("<img src=\"https://drive.google.com/uc?id=1k_PKQeTJVDgnu8TX7uZkQOec_wN_L9Qv\" alt=\"Image\" class=\"top-image\">");
                        
                        out.println("<form class=\"Buscar\" method=\"post\" action=\"SrvDocumentos\">");
                        
                        out.println("<input type=\"hidden\" id=\"userid\" name=\"userid\" value=\"" + request.getParameter("userid") + "\">");
                        out.println("<input type=\"hidden\" id=\"tipo_usuario\" name=\"tipo_usuario\" value=\"" + request.getParameter("tipo_usuario") + "\">");
                        
                        out.println("<label for=\"search\">Buscar:</label>");
                        out.println("<input class=\"Buscar-texto\" type=\"text\" id=\"nombre_documento\" name=\"nombre_documento\">");
                        out.println("<input class=\"Buscar-boton\" type=\"submit\" value=\"Buscar\" name=\"search\">");
                        out.println("<select class=\"filtros\" name=\"generofilter\">");  // Ensure the name attribute matches with the servlet code
                        out.println("  <option value=\"\">Todos</option>");
                        out.println("  <option value=\"Aventura\">Aventura</option>");
                        out.println("  <option value=\"Romance\">Romance</option>");
                        out.println("  <option value=\"Ciencia Ficción\">Ciencia Ficción</option>");
                        out.println("  <option value=\"Thriller\">Thriller</option>");
//                                                 Add more genre options as needed
                        out.println("</select>");
                        out.println("</form>");
                        
                        //boton a busqueda avanzada
                        out.println("<form class=\"BusquedaAv\" method=\"post\" action=\"SrvBusqueda\">");
                        
                        out.println("<input type=\"hidden\" id=\"userid\" name=\"userid\" value=\"" + request.getParameter("userid") + "\">");
                        out.println("<input type=\"hidden\" id=\"tipo_usuario\" name=\"tipo_usuario\" value=\"" + request.getParameter("tipo_usuario") + "\">");
                        
                        out.println("<input class=\"Buscar-boton\" type=\"submit\" value=\"Búsqueda avanzada\" name=\"detalles\">");
                        out.println("</form>");
                        
                        String filtrando;
                        if(generofilter == null || generofilter.isEmpty()){
                        filtrando = "";
                        }else{
                        filtrando = "Filtrando por: " + generofilter;
                        out.println("<h1>" + filtrando + "</h1>");}
                        
                        
                        
                        out.println("</div>"); // Close principal
                        out.println("</div>"); // Close fondo
                        out.println("</div>"); // Close contenido-pagina
                        out.println("</div>"); // Close Pantalla
                        out.println("</body>");
                        out.println("</html>");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            out.println("Error: " + e.getMessage());
        }
    }
}