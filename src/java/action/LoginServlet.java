/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package action;

import dao.AccountDAO;
import dao.CategoryDAO;
import dao.CustomerDAO;
import dao.ProductDAO;
import dto.AccountDTO;
import dto.CategoryDTO;
import dto.CustomerDTO;
import dto.ProductDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import md5.MD5;

/**
 *
 * @author Hello
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

    final private String loginError = "invalid.html";
    final private String displayServlet = "DisplayServlet";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        PrintWriter out = response.getWriter();
        try {
            String url = loginError;
            String username = request.getParameter("txtUsername");
            String password = request.getParameter("txtPassword");

            password = MD5.getPasswordEncrypt(password);

            try {
                AccountDAO accountDAO = new AccountDAO();
                AccountDTO accountDTO = accountDAO.checkLogin(username, password);

                if (accountDTO != null) {
                    HttpSession session = request.getSession();
                    session.setAttribute("ACCOUNT", accountDTO);

                    url = displayServlet;
                }

                RequestDispatcher rd = request.getRequestDispatcher(url);
                rd.forward(request, response);
            } catch (SQLException | NamingException e) {
                Logger.getAnonymousLogger().log(Level.CONFIG, "msg", e);
            }

        } finally {
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
