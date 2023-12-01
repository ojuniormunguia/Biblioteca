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
                if (nombreDocumento.isEmpty() && !id.isEmpty()) {
                    sql = "SELECT * FROM documentos WHERE id = ?";
                } else if (id.isEmpty() && !nombreDocumento.isEmpty()) {
                    sql = "SELECT * FROM documentos WHERE nombre_documento LIKE ?";
                } else {
                    sql = "SELECT * FROM documentos";
                }

                // Prepare the SQL query
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    // Set parameters for the SQL query
                    if (!id.isEmpty()) {
                        preparedStatement.setString(1, id);
                    } else if (!nombreDocumento.isEmpty()) {
                        preparedStatement.setString(1, "%" + nombreDocumento + "%");
                    }

                    // Execute the query
                    
                    try (ResultSet resultSet = preparedStatement.executeQuery()) {
                        // Display the results in the servlet response
         
                        out.println("<html>");
                        out.println("<head>");
                        out.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">");
                        out.println("<link href=\"css/styles.css\" rel=\"stylesheet\" type=\"text/css\"/>");
                        out.println("<title>Search Results</title>");                        
                        out.println("</head>");
                        out.println("<body>");
                        out.println("<h2 style=\"text-align:center\">Lista de documentos</h2>");
                        out.println("</div class=\"container\">");

                        // Iterate through the result set and display the data
                        int cardCounter = 4;
                        while (resultSet.next()) {
                            out.println("<div class=\"row\">");
                            out.println("<div class=\"column\">");
                            out.println("<div class=\"card\">");
                            out.println("<h3>Card " + cardCounter + "</h3>");
                            out.println("<p>ID: " + resultSet.getInt("id") + "</p>");
                            out.println("<p>Tipo Documento: " + resultSet.getString("tipo_documento") + "</p>");
                            out.println("<p>Nombre Documento: " + resultSet.getString("nombre_documento") + "</p>");
                            out.println("<p>Ubicacion Fisica: " + resultSet.getString("ubicacion_fisica") + "</p>");
                            out.println("<p>Cantidad Ejemplares: " + resultSet.getInt("cantidad_ejemplares") + "</p>");
                            out.println("<p>Ejemplares Prestados: " + resultSet.getInt("ejemplares_prestados") + "</p>");
                            out.println("<p>Disponibles: " + resultSet.getInt("disponibles") + "</p>");
                            out.println("</div>");
                            out.println("</div>");
                            out.println("</div>");
                            cardCounter++;
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
