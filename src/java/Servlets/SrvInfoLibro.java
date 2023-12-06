package Servlets;

import AccesoDatos.DatabaseConnection;
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

@WebServlet(name = "SrvInfoLibro", urlPatterns = {"/SrvInfoLibro"})
public class SrvInfoLibro extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {
            // Get user input from the form
            String id = request.getParameter("id");
            String sql;

            try (Connection connection = DatabaseConnection.getConnection()) {
                // Create a SQL query based on the user input
                if (id != null && !id.isEmpty()) {
                    sql = "SELECT * FROM documentos WHERE id = ?";
                } else {
                    sql = "SELECT * FROM documentos";
                }

                // Prepare the SQL query
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    // Set parameters for the SQL query
                    if (id != null && !id.isEmpty()) {
                        preparedStatement.setString(1, id);
                    }

                    // Execute the query
                    try (ResultSet resultSet = preparedStatement.executeQuery()) {
                        // Display the results in the servlet response
                        out.println("<!DOCTYPE html>");
                        out.println("<html lang=\"en\">");
                        out.println("<head>");
                        out.println("<meta charset=\"utf-8\" />");
                        out.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />");
                        out.println("<link href=\"css/infocss.css\" rel=\"stylesheet\" type=\"text/css\"/>");
                        
                        // Include CSS styles
                        out.println("<style>");
                        // Add your existing styles here
                        out.println("</style>");
                        
                        out.println("<title>Search Results</title>");
                        out.println("</head>");
                        out.println("<body>");
                        
                        // Include menu-superior section
                        out.println("<div class=\"menu-superior\">");
                        out.println("<div class=\"menu-1\"></div>");
                        out.println("<div class=\"menu-2\"></div>");
                        out.println("<div class=\"menu-3\"></div>");
                        out.println("</div>");
                        
                        // Display the results in the servlet response
                        out.println("<div class=\"container\">");
                        while (resultSet.next()) {
                            out.println("<div class=\"bsqueda-avanzada\">");
                            out.println("<div class=\"content\">");
                            out.println("<div class=\"frame\">");
                            out.println("<div class=\"image\">");
                            out.println("<img src='" + resultSet.getString("image_url") + "' alt='Book Cover' style=\"width:auto; height: 100%; object-fit: cover;\">");
                            out.println("</div>");
                            out.println("</div>");
                            
                            out.println("<div class=\"bg\">"); //bg start
                            
                            out.println("<div class=\"in-bg\">"); //in-bg start
                            
                            out.println("<div class=\"in left\">"); //in left start
                            out.println("<img src='" + resultSet.getString("image_url") + "' class=\"coverart\" alt='Book Cover'>");
                            out.println("</div>"); //in left end
                            
                            out.println("<div class=\"in right\">"); //in right start
                            out.println("<h3 style=\"font-size:40px\">" + resultSet.getString("nombre_documento") + "</h3>");
                            out.println("<p>");
                            out.println("<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"21\" height=\"19\" viewBox=\"0 2 21 13\" fill=\"none\">");
                            out.println("  <path d=\"M10.5 0L12.8574 7.25532H20.4861L14.3143 11.7394L16.6717 18.9947L10.5 14.5106L4.32825 18.9947L6.68565 11.7394L0.513906 7.25532H8.1426L10.5 0Z\" fill=\"#FFC700\"/>");
                            out.println("</svg>");
                            out.println(resultSet.getDouble("estrellas") + "</p>");  
                            out.println("<p>Escrito por " + resultSet.getString("autor") + "</p>");
                            out.println("<div class=\"bg-row\">"); //bg-row start
                            out.println("<div class=\"column left\">"); //left start
                            out.println("<p>Publicado un " + resultSet.getString("releasedate") + "</p>");
                            out.println("<p>Número ISBN: " + resultSet.getString("ISBN") + "</p>");
                            out.println("<p>Ubicación: " + resultSet.getString("ubicacion_fisica") + "</p>");
                            resultSet.getString("cantidad_ejemplares");
                            resultSet.getString("ejemplares_prestados");
                            out.println("<p>" + resultSet.getString("genero") + " | " + resultSet.getString("tipo_documento") + "</p>");
                            out.println("</div>"); //left end
                            
                            out.println("<div class=\"column right\">"); //right start
                            out.println("<p>" + resultSet.getString("resumen") + "</p>");
                            out.println("</div>"); //right end
                            out.println("</div>"); //bg-row end
                            out.println("</div>"); //in right end
                            out.println("</div>"); //in.bg end
                            out.println("</div>"); //bg end
                            out.println("</div>");
                            out.println("</div>");
                        }
                        out.println("</div>");
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



