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
import javax.servlet.http.HttpSession;

@WebServlet(name = "SrvAdd", urlPatterns = {"/SrvAdd"})
public class SrvAdd extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {
            int id = parseIntegerParameter(request.getParameter("id")); // Ensure this method is robust
            String userid = request.getParameter("userid");
            String tipo_usuario = request.getParameter("tipo_usuario");

            if (id == 0) {
                performInsertion(request, response, out); // Handle insertion
            } else {
                performUpdate(request, response, out, id); // Handle update
            }
        } catch (NumberFormatException e) {
            out.println("Invalid ID format.");
        } catch (SQLException e) {
            out.println("Database error: " + e.getMessage());
            // Log the exception using a logging framework
        } catch (Exception e) {
            out.println("An unexpected error occurred: " + e.getMessage());
            // Log the exception
        }
    }

    private void performInsertion(HttpServletRequest request, HttpServletResponse response, PrintWriter out) throws SQLException, IOException {
        
        String userid = request.getParameter("userid");
        String tipo_usuario = request.getParameter("tipo_usuario");
        
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
        
        HttpSession session = request.getSession();
        session.setAttribute("userid", userid);
        session.setAttribute("tipo_usuario", tipo_usuario);

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
    }

    private void performUpdate(HttpServletRequest request, HttpServletResponse response, PrintWriter out, int id) throws SQLException, IOException {
        
        String userid = request.getParameter("userid");
        String tipo_usuario = request.getParameter("tipo_usuario");
        
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

        // Identify the column to update based on the id
        String columnName = getColumnNameById(id);

        // Create SQL query for update
        String sql = "UPDATE documentos SET tipo_documento=?, nombre_documento=?, ubicacion_fisica=?, "
                + "cantidad_ejemplares=?, ejemplares_prestados=?, autor=?, genero=?, resumen=?, ISBN=?, releasedate=?, image_url=? "
                + "WHERE id=?";
        
        
        HttpSession session = request.getSession();
        session.setAttribute("userid", userid);
        session.setAttribute("tipo_usuario", tipo_usuario);


        // Prepare the SQL query
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
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
            preparedStatement.setInt(12, id);  // Set the id for update

            // Execute the query
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                // Redirect to SrvDocumentos after successful update
                response.sendRedirect(request.getContextPath() + "/SrvContinuar");
            } else {
                out.println("Error updating the item.");
            }
        }
    }

    private String getColumnNameById(int id) {
        // Implement logic to determine the column name based on the id
        // For example, you can query the database to get the column name
        // associated with the provided id.
        return "id";  // Replace with the actual column name
    }

    private int parseIntegerParameter(String parameter) {
        if (parameter != null && !parameter.isEmpty()) {
            try {
                return Integer.parseInt(parameter);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect(request.getContextPath() + "/SrvContinuar");
    }
}
