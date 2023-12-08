package Servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import AccesoDatos.DatabaseConnection;

@WebServlet(name = "SrvDelete", urlPatterns = {"/SrvDelete"})
public class SrvDelete extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Retrieve the document ID from the request
        String documentId = request.getParameter("id");

        // Delete the document based on the ID
        boolean deletionSuccessful = deleteDocument(documentId);

        if (deletionSuccessful) {
            // Redirect to SrvDocumentos after successful deletion
            response.sendRedirect(request.getContextPath() + "/SrvContinuar");
        } else {
            // Forward to an error page if deletion fails
            request.getRequestDispatcher("/errorPage.jsp").forward(request, response);
        }
    }

    private boolean deleteDocument(String documentId) {
        try (Connection connection = DatabaseConnection.getInstance().getConnection()) {
            // Create a prepared statement to execute the DELETE query
            String deleteQuery = "DELETE FROM documentos WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
                // Set the ID parameter
                preparedStatement.setString(1, documentId);

                // Execute the DELETE query
                int rowsAffected = preparedStatement.executeUpdate();

                // Return true if deletion is successful (at least one row affected), false otherwise
                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            // Log the exception or handle it according to your needs
            e.printStackTrace();

            // Deletion failed
            return false;
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Forward to the doPost method for handling the deletion
        doPost(request, response);
    }

    @Override
    public String getServletInfo() {
        return "SrvDelete Servlet";
    }
}
