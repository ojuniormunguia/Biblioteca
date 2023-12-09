package Servlets;

import AccesoDatos.DatabaseConnection;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "SrvLogin", urlPatterns = {"/SrvLogin"})
public class SrvLogin extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("usuario");
        String password = request.getParameter("contrasena");

        if (validateUser(username, password)) {
            HttpSession session = request.getSession();
            session.setAttribute("userid", getUserId(username));
            session.setAttribute("tipo_usuario", getUserType(username));
            response.sendRedirect("SrvDocumentos");
        } else {
            // Handle invalid credentials (redirect to login page or show error)
            response.sendRedirect("index.jsp"); // Redirect to an error page or login page
        }
    }

    private boolean validateUser(String username, String password) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT COUNT(*) FROM usuarios WHERE usuario = ? AND contrasena = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);
                
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getInt(1) > 0;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Optionally handle the exception
        }
        return false;
    }

    private int getUserId(String username) {
        int userId = -1; // Default to -1 to indicate not found

        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT id FROM usuarios WHERE usuario = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, username);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        userId = resultSet.getInt("id");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Optionally handle the exception, such as logging the error
        }

        return userId;
    }


    private String getUserType(String username) {
        String userType = null; // Default to null indicating no user found

        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT tipo_usuario FROM usuarios WHERE usuario = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, username);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        userType = resultSet.getString("tipo_usuario");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Optionally handle the exception, such as logging the error
        }

        return userType;
    }
}
