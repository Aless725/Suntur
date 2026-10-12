package com.suntur.controller;

import com.suntur.dao.UsuarioDAO;
import com.suntur.model.Usuario;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "Registrar", urlPatterns = {"/Registrar"})
public class Registrar extends HttpServlet {

    UsuarioDAO udao = new UsuarioDAO();
    Usuario us = new Usuario();

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");
        if (accion != null && accion.equalsIgnoreCase("Registrar")) {
            String nom = request.getParameter("Nombres");
            String ape = request.getParameter("Apellidos");
            String email = request.getParameter("Email");
            String pass = request.getParameter("Password");

            us = udao.buscarUsuario(email);
            if (us.getCorreo() != null) {
                request.getRequestDispatcher("index.jsp").forward(request, response);
            } else {
                udao.registrar(nom, ape, email, pass);
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        } else {
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
    }
}