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

@WebServlet(name = "SrvPrestamos", urlPatterns = {"/SrvPrestamos"})
public class SrvPrestamos extends HttpServlet {

        @Override
        protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        try (PrintWriter out = response.getWriter()) {
            String userid = request.getParameter("userid");
            String tipo_usuario = request.getParameter("tipo_usuario");
            String sql = "CALL Prestamos(?)";

            try (Connection connection = DatabaseConnection.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                
                preparedStatement.setString(1, userid);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    out.println("<!DOCTYPE html>");
                    out.println("<html lang=\"en\">");
                    out.println("<head>");
                    out.println("<meta charset=\"utf-8\" />");
                    out.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />");
                    out.println("<link href=\"css/Prestamoscss.css\" rel=\"stylesheet\" type=\"text/css\"/>");
                    out.println("<title>Search Results</title>");
                    out.println("</head>");
                    out.println("<body>");

                    // Include menu-superior section
                    out.println("<div class=\"menu-superior\">");
                    out.println("<div class=\"menu-1\"></div>");
                    out.println("<div class=\"menu-2\">");
                    out.println("<form method=\"post\" action=\"index.jsp\">");
                        
                        
                        out.println("<input class=\"Buscar-boton\" type=\"submit\" value=\"Cerrar sesión\" name=\"Login\">");
                        
                    out.println("</div>");    
                    out.println("<div class=\"menu-3\"></div>");
                    out.println("</div>");

                    out.println("<div class=\"container\">");
                    while (resultSet.next()) {
                        // Process each row of the result set
                        out.println("<div class=\"row\">"); //row
                        out.println("<div class=\"column\">"); //column
                        out.println("<div class=\"card\">"); //card
                        
                        out.println("<h3>" + resultSet.getString("ejemp") + "</h3>");
                        out.println("<p><strong>Fecha de préstamo: </strong>" + resultSet.getString("fecha_prestamo") + "</p>");
                        out.println("<p><strong>Devolver antes de: </strong>" + resultSet.getString("fecha_devolucion") + "</p>");
                        
                        out.println("<form method=\"post\" action=\"SrvDevolver\">");
                        
                        out.println("<input type=\"hidden\" id=\"userid\" name=\"userid\" value=\"" + userid + "\">");
                        out.println("<input type=\"hidden\" id=\"tipo_usuario\" name=\"tipo_usuario\" value=\"" + tipo_usuario + "\">");
                        out.println("<input type=\"hidden\" id=\"prestamoid\" name=\"prestamoid\" value=\"" + resultSet.getString("prestamoid") + "\">");
                        out.println("<input class=\"Buscar-boton\" type=\"submit\" value=\"devolver\" name=\"Login\">");
                        
                        out.println("</div>"); // Close container
                        out.println("</div>"); // Close container
                        out.println("</div>"); // Close container

                    }
                    out.println("</div>"); // Close container
                    out.println("</body>");
                    out.println("</html>");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the SQL exception appropriately
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    public String getServletInfo() {
        return "SrvPrestamos Servlet";
    }
}
