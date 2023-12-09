package Servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "SrvContinuar", urlPatterns = {"/SrvContinuar"})
public class SrvContinuar extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Display a simple HTML form with a "Continuar" button
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        HttpSession session = request.getSession();
        String userid = (String) session.getAttribute("userid");
        String tipo_usuario = (String) session.getAttribute("tipo_usuario");


        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset=\"utf-8\" />");
        out.println("<meta name=\"viewport\" content=\"initial-scale=1, width=device-width\" />");
        out.println("<title>Continuar</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<form method=\"post\" action=\"" + request.getContextPath() + "/SrvDocumentos\">");
        out.println("<input type=\"hidden\" id=\"userid\" name=\"userid\" value=\"" + userid + "\">");
        out.println("<input type=\"hidden\" id=\"tipo_usuario\" name=\"tipo_usuario\" value=\"" + tipo_usuario + "\">");
        out.println("<input class=\"Buscar-boton\" type=\"submit\" value=\"Continuar\">");
        out.println("</form>");
        out.println("</body>");
        out.println("</html>");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Handle any specific actions for POST requests, if needed
        // For now, redirect to doGet
        doGet(request, response);
    }
}
