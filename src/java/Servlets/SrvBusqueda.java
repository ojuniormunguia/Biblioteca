/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "SrvBusqueda", urlPatterns = { "/SrvBusqueda" })
public class SrvBusqueda extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        
        

        try {
            // Extract form parameters
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
            
            String actionUrl = "SrvDocumentosAdv?";
            if (nombreDocumento != null) {
                actionUrl += "nombre_documento=" + nombreDocumento;
            }
            if (tipoDocumentos != null) {
                actionUrl += "&tipo_documentos=" + tipoDocumentos;
            }
            if (ubicacionFisica != null) {
                actionUrl += "&ubicacion_fisica=" + ubicacionFisica;
            }
            if (cantidadesEjemplares != null) {
                actionUrl += "&cantidades_ejemplares=" + cantidadesEjemplares;
            }
            if (ejemplaresPrestados != null) {
                actionUrl += "&ejemplares_prestados=" + ejemplaresPrestados;
            }
            if (autor != null) {
                actionUrl += "&autor=" + autor;
            }
            if (genero != null) {
                actionUrl += "&genero=" + genero;
            }
            if (resumen != null) {
                actionUrl += "&resumen=" + resumen;
            }
            if (ISBN != null) {
                actionUrl += "&ISBN=" + ISBN;
            }
            if (releaseDate != null) {
                actionUrl += "&releaseDate=" + releaseDate;
            }

            // Display the HTML form with submitted values
            out.println("<!DOCTYPE html>");
            out.println("<html lang=\"en\">");
            out.println("<head>");
            out.println("<meta charset=\"utf-8\" />");
            out.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\" />");
            out.println("<link href=\"css/StyleForm.css\" rel=\"stylesheet\" type=\"text/css\"/>");
            out.println("<title>Form Submission Result</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<div class=\"bsqueda-avanzada\">");
            out.println("<div class=\"menu-superior\">");
            out.println("<div class=\"menu-1\"></div>");
            out.println("<div class=\"menu-2\"></div>");
            out.println("<div class=\"menu-3\"></div>");
            out.println("</div>");
            out.println("<div class=\"content\">");
            out.println("<div class=\"frame\">");
            out.println("<div class=\"image\"></div>");
            out.println("</div>");
            out.println("<div class=\"bg\">");
            
            out.println("<form method=\"post\" action=\"" + actionUrl + "\">");

            
            out.println("<label for=\"nombre_documento\">Nombre:</label>");
            out.println("<input type=\"text\" id=\"nombre_documento\" name=\"nombre_documento\">");
            
            out.println("<label for=\"tipo_documentos\">Tipo de Documentos:</label>");
            out.println("<input type=\"text\" id=\"tipo_documentos\" name=\"tipo_documentos\">");

            out.println("<label for=\"ubicacion_fisica\">Ubicación Física:</label>");
            out.println("<input type=\"text\" id=\"ubicacion_fisica\" name=\"ubicacion_fisica\">");

            out.println("<label for=\"cantidades_ejemplares\">Cantidad de Ejemplares:</label>");
            out.println("<input type=\"number\" id=\"cantidades_ejemplares\" name=\"cantidades_ejemplares\">");

            out.println("<label for=\"ejemplares_prestados\">Ejemplares Prestados:</label>");
            out.println("<input type=\"number\" id=\"ejemplares_prestados\" name=\"ejemplares_prestados\">");

            out.println("<label for=\"autor\">Autor:</label>");
            out.println("<input type=\"text\" id=\"autor\" name=\"autor\">");

            out.println("<label for=\"genero\">Género:</label>");
            out.println("<input type=\"text\" id=\"genero\" name=\"genero\">");

            out.println("<label for=\"resumen\">Resumen:</label>");
            out.println("<textarea id=\"resumen\" name=\"resumen\" ></textarea>");

            out.println("<label for=\"ISBN\">ISBN:</label>");
            out.println("<input type=\"text\" id=\"ISBN\" name=\"ISBN\" >");

            out.println("<label for=\"releasedate\">Fecha de Publicación:</label>");
            out.println("<input type=\"text\" id=\"releasedate\" name=\"releasedate\" >");


            out.println("<input type=\"submit\" value=\"Submit\">");
            out.println("</form>");
            out.println("</div>");
            out.println("</div>");
            out.println("</div>");
            out.println("</div>");
            out.println("</body>");
            out.println("</html>");
            
            
        } finally {
            out.close();
        }
    }
}
