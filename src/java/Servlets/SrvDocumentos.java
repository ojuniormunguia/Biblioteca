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
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {
            // Get user input from the form
            String id = request.getParameter("id");
            String nombreDocumento = request.getParameter("nombre_documento");
            String sql;

            // Establish a database connection
            try (Connection connection = DatabaseConnection.getConnection()) {
                // Create a SQL query based on the user input
                if (request.getParameter("search") != null) {
                    // Handle the search operation here
                    // Modify the SQL query accordingly and set the parameters
                    sql = "SELECT * FROM documentos WHERE nombre_documento LIKE ?";
                    nombreDocumento = "%" + nombreDocumento + "%";
                } else {
                    // Check if id is not null before invoking isEmpty()
                    if (id != null && !id.isEmpty()) {
                        sql = "SELECT * FROM documentos WHERE id = ?";
                    } else if (nombreDocumento != null && !nombreDocumento.isEmpty()) {
                        sql = "SELECT * FROM documentos WHERE nombre_documento LIKE ?";
                        nombreDocumento = "%" + nombreDocumento + "%";
                    } else {
                        sql = "SELECT * FROM documentos";
                    }
                }

                // Prepare the SQL query
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    // Set parameters for the SQL query
                    if (id != null && !id.isEmpty()) {
                        preparedStatement.setString(1, id);
                    } else if (nombreDocumento != null && !nombreDocumento.isEmpty()) {
                        preparedStatement.setString(1, nombreDocumento);
                    }

                    // Execute the query
                    try (ResultSet resultSet = preparedStatement.executeQuery()) {
                        // Display the results in the servlet response
                        
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
                        out.println("<form method=\"post\" action=\"Form.html\">");
                        out.println("<input type=\"hidden\" name=\"id\" value=\"''\">");
                        out.println("<input class=\"Buscar-boton\" type=\"submit\" value=\"Ver más\" name=\"detalles\" style=\"margin-top:12px; margin-left:15px\">");
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
                            String genero = resultSet.getString("genero");
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
                            out.println("<form class=\"botones right\" method=\"post\" action=\"SrvAdd\">");
                            out.println("<input type=\"hidden\" name=\"id\" value=\"" + resultSet.getString("id")+ "\">");
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
                        out.println("<select class=\"filtros\" name=\"genero\">");
                        out.println("  <option value=\"\">Todos</option>");
                        out.println("  <option value=\"Aventura\">Aventura</option>");
                        out.println("  <option value=\"Romance\">Romance</option>");
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
        }
    }
}
