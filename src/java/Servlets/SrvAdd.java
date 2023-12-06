package Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import AccesoDatos.DatabaseConnection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "SrvAdd", urlPatterns = {"/SrvAdd"})
public class SrvAdd extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {
            // Retrieve form data
            String tipoDocumento = request.getParameter("tipo_documento");
            tipoDocumento = (tipoDocumento != null && !tipoDocumento.isEmpty()) ? tipoDocumento : "DefaultTipoDocumento";

            String nombreDocumento = request.getParameter("nombre_documento");
            nombreDocumento = (nombreDocumento != null && !nombreDocumento.isEmpty()) ? nombreDocumento : "DefaultNombreDocumento";

            String ubicacionFisica = request.getParameter("ubicacion_fisica");
            ubicacionFisica = (ubicacionFisica != null && !ubicacionFisica.isEmpty()) ? ubicacionFisica : "DefaultUbicacionFisica";

            int cantidadEjemplares = parseIntegerParameter(request.getParameter("cantidad_ejemplares"));
            int ejemplaresPrestados = parseIntegerParameter(request.getParameter("ejemplares_prestados"));

            String autor = request.getParameter("autor");
            String genero = request.getParameter("genero");
            String resumen = request.getParameter("resumen");
            String isbn = request.getParameter("ISBN");
            String releaseDate = request.getParameter("releasedate");
            String imageUrl = request.getParameter("image_url");

            // Establish a database connection
            try (Connection connection = DatabaseConnection.getConnection()) {
                // Create SQL query for insertion
                String sql = "INSERT INTO documentos (tipo_documento, nombre_documento, ubicacion_fisica, "
                        + "cantidad_ejemplares, ejemplares_prestados, autor, genero, resumen, ISBN, releasedate, image_url) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

                // Prepare the SQL query
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    // Set parameters for the SQL query
                    preparedStatement.setString(1, tipoDocumento);
                    preparedStatement.setString(2, nombreDocumento);
                    preparedStatement.setString(3, ubicacionFisica);
                    preparedStatement.setInt(4, cantidadEjemplares);
                    preparedStatement.setInt(5, ejemplaresPrestados);
                    preparedStatement.setString(6, autor);
                    preparedStatement.setString(7, genero);
                    preparedStatement.setString(8, resumen);
                    preparedStatement.setString(9, isbn);
                    preparedStatement.setString(10, releaseDate);
                    preparedStatement.setString(11, imageUrl);

                    // Execute the query
                    int rowsAffected = preparedStatement.executeUpdate();

                    if (rowsAffected > 0) {
                        // Redirect to SrvDocumentos after successful insertion
                        response.sendRedirect(request.getContextPath() + "/SrvContinuar");


                    } else {
                        out.println("Error inserting the item.");
                    }
                }
            }
        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
            out.println("Error: " + e.getMessage());
        }
    }

    private int parseIntegerParameter(String parameter) {
        if (parameter != null && !parameter.isEmpty()) {
            try {
                return Integer.parseInt(parameter);
            } catch (NumberFormatException e) {
                // Log or handle the exception as needed
                e.printStackTrace();
            }
        }
        return 0;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Handle GET requests, for example, redirect to SrvDocumentos
        response.sendRedirect(request.getContextPath() + "/SrvContinuar");


    }
}
