package Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import AccesoDatos.DatabaseConnection;

@WebServlet(name = "SrvDocumentosAdv", urlPatterns = { "/SrvDocumentosAdv" })
public class SrvDocumentosAdv extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {
            // Get search criteria from request parameters
            String id = request.getParameter("id");
            String nombreDocumento = request.getParameter("nombre_documento");
            String tipoDocumentos = request.getParameter("tipo_documentos");
            String ubicacionFisica = request.getParameter("ubicacion_fisica");
            String cantidadesEjemplares = request.getParameter("cantidades_ejemplares");
            String ejemplaresPrestados = request.getParameter("ejemplares_prestados");
            String autor = request.getParameter("autor");
            String genero = request.getParameter("genero");
            String resumen = request.getParameter("resumen");
            String ISBN = request.getParameter("ISBN");
            String releaseDate = request.getParameter("releasedate");
            String imageUrl = request.getParameter("image_url");

            // Establish a database connection
            try (Connection connection = DatabaseConnection.getConnection()) {
                // Build the SQL query based on the search criteria
                String sql = "SELECT * FROM documentos WHERE 1=1";

                if (id != null && !id.isEmpty()) {
                    sql += " AND id = ?";
                }

                if (nombreDocumento != null && !nombreDocumento.isEmpty()) {
                    sql += " AND nombre_documento LIKE ?";
                    nombreDocumento = "%" + nombreDocumento + "%";
                }
                
                if (tipoDocumentos != null && !tipoDocumentos.isEmpty()) {
                    sql += " AND tipo_documentos LIKE ?";
                    tipoDocumentos = "%" + tipoDocumentos + "%";
                }                

                if (ubicacionFisica != null && !ubicacionFisica.isEmpty()) {
                    sql += " AND ubicacion_fisica LIKE ?";
                    ubicacionFisica = "%" + ubicacionFisica + "%";
                }     

                if (cantidadesEjemplares != null && !cantidadesEjemplares.isEmpty()) {
                    sql += " AND cantidades_ejemplares LIKE ?";
                    cantidadesEjemplares = "%" + cantidadesEjemplares + "%";
                }  
                
                if (autor != null && !autor.isEmpty()) {
                    sql += " AND autor LIKE ?";
                    autor = "%" + autor + "%";
                } 
                
                if (genero != null && !genero.isEmpty()) {
                    sql += " AND genero LIKE ?";
                    genero = "%" + genero + "%";
                } 
                
                if (resumen != null && !resumen.isEmpty()) {
                    sql += " AND resumen LIKE ?";
                    resumen = "%" + resumen + "%";
                } 
                
                if (ISBN != null && !ISBN.isEmpty()) {
                    sql += " AND ISBN LIKE ?";
                    ISBN = "%" + ISBN + "%";
                } 
                
                if (releaseDate != null && !releaseDate.isEmpty()) {
                    sql += " AND releaseDate LIKE ?";
                    releaseDate = "%" + releaseDate + "%";
                } 
 
                // Add conditions for other search criteria as needed

                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    // Set parameters for the SQL query
                    int parameterIndex = 1;

                    if (id != null && !id.isEmpty()) {
                        preparedStatement.setString(parameterIndex++, id);
                    }

                    if (nombreDocumento != null && !nombreDocumento.isEmpty()) {
                        preparedStatement.setString(parameterIndex++, nombreDocumento);
                    }
                    
                    if (tipoDocumentos != null && !tipoDocumentos.isEmpty()) {
                        preparedStatement.setString(parameterIndex++, tipoDocumentos);
                    }

                    if (ubicacionFisica != null && !ubicacionFisica.isEmpty()) {
                        preparedStatement.setString(parameterIndex++, ubicacionFisica);
                    }

                    if (cantidadesEjemplares != null && !cantidadesEjemplares.isEmpty()) {
                        preparedStatement.setString(parameterIndex++, cantidadesEjemplares);
                    }

                    if (ejemplaresPrestados != null && !ejemplaresPrestados.isEmpty()) {
                        preparedStatement.setString(parameterIndex++, ejemplaresPrestados);
                    }

                    if (autor != null && !autor.isEmpty()) {
                        preparedStatement.setString(parameterIndex++, autor);
                    }

                    if (genero != null && !genero.isEmpty()) {
                        preparedStatement.setString(parameterIndex++, genero);
                    }

                    if (resumen != null && !resumen.isEmpty()) {
                        preparedStatement.setString(parameterIndex++, resumen);
                    }

                    if (ISBN != null && !ISBN.isEmpty()) {
                        preparedStatement.setString(parameterIndex++, ISBN);
                    }

                    if (releaseDate != null && !releaseDate.isEmpty()) {
                        preparedStatement.setString(parameterIndex++, releaseDate);
                    }

                    // Set parameters for other search criteria

                    // Execute the query
                    try (ResultSet resultSet = preparedStatement.executeQuery()) {
                        // Display the search results
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
                        out.println("<div class=\"menu-1\"></div>");
                        out.println("<div class=\"menu-2\">");
                        
                        //Botón a agregar
                        out.println("<form method=\"post\" action=\"Form.jsp\">");
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
                        
                        out.println("</div>");
                        out.println("<div class=\"menu-3\"></div>");
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
                            out.println("<input type=\"hidden\" name=\"id\" value=\"" + resultSet.getString("id") + "\">");
                            out.println("<input class=\"Buscar-boton\" type=\"submit\" value=\"Ver más\" name=\"detalles\">");
                            out.println("</form>");
                            
                            //Botón a editar
                            out.println("<form method=\"post\" action=\"Form.jsp\">");
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
                            
                            out.println("</div>"); //botones ends
                            out.println("</div>"); //card
                            out.println("</div>"); //column
                            out.println("</div>"); //row
                        }

                        out.println("</div>"); // Close para-resultados div
                        out.println("</div>"); // Close contenido
                        out.println("<div class=\"principal\">");
                        // Barra de búsqueda básica
                        out.println("<form class=\"Buscar\" method=\"post\" action=\"SrvDocumentos\">");
                        out.println("<label for=\"search\">Buscar:</label>");
                        out.println("<input class=\"Buscar-texto\" type=\"text\" id=\"nombre_documento\" name=\"nombre_documento\">");
                        out.println("<input class=\"Buscar-boton\" type=\"submit\" value=\"Buscar\" name=\"search\">");
                        out.println("<select class=\"filtros\" name=\"generofilter\">");  // Ensure the name attribute matches with the servlet code
                        out.println("  <option value=\"\">Todos</option>");
                        out.println("  <option value=\"Aventura\">Aventura</option>");
                        out.println("  <option value=\"Romance\">Romance</option>");
                        out.println("  <option value=\"Ciencia Ficción\">Ciencia Ficción</option>");
                        out.println("  <option value=\"Thriller\">Thriller</option>");
                                                // Add more genre options as needed
                        out.println("</select>");
                        out.println("</form>");
                        
                        //boton a busqueda avanzada
                        out.println("<form class=\"BusquedaAv\" method=\"post\" action=\"SrvBusqueda\">");
                        out.println("<input class=\"Buscar-boton\" type=\"submit\" value=\"Búsqueda avanzada\" name=\"detalles\">");
                        out.println("</form>");
                        
                        
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
        } finally {
            out.close();
        }
    }
}
