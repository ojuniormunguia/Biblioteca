package Servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import AccesoDatos.DatabaseConnection;
import javax.servlet.http.HttpSession;

@WebServlet(name = "SrvRentar", urlPatterns = {"/SrvRentar"})
public class SrvRentar extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Retrieve parameters from the request
        String userid = request.getParameter("userid");
        String tipo_usuario = request.getParameter("tipo_usuario");
        String ejempId = request.getParameter("id");
        String tiempo = request.getParameter("tiempo");

        // Execute the SQL procedure
        boolean rentSuccessful = executePrestamosProcedure(userid, ejempId, tiempo);


            // Redirect to SrvContinuar after successful operation
        HttpSession session = request.getSession();
        session.setAttribute("userid", userid);
        session.setAttribute("tipo_usuario", tipo_usuario);
            // You can set other required attributes to the session as needed
        response.sendRedirect(request.getContextPath() + "/SrvContinuar");

    }

    private boolean executePrestamosProcedure(String userid, String ejempId, String tiempo) {
        try (Connection connection = DatabaseConnection.getInstance().getConnection()) {
            // Create a callable statement to execute the stored procedure
            String procedureCall = "{CALL InitPrestamos(?, ?, ?)}";
            try (CallableStatement callableStatement = connection.prepareCall(procedureCall)) {
                // Set parameters for the stored procedure
                callableStatement.setInt(1, Integer.parseInt(userid));
                callableStatement.setInt(2, Integer.parseInt(ejempId));
                callableStatement.setInt(3, Integer.parseInt(tiempo));

                // Execute the stored procedure
                boolean result = callableStatement.execute();

                // Return true if operation is successful, false otherwise
                return result;
            }
        } catch (SQLException | NumberFormatException e) {
            // Log the exception or handle it according to your needs
            e.printStackTrace();

            // Operation failed
            return false;
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Forward to the doPost method for handling the operation
        doPost(request, response);
    }

    @Override
    public String getServletInfo() {
        return "SrvRentar Servlet";
    }
}
