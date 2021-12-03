/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hungnln.mooncake.servlets;

import com.hungnln.mooncake.daos.UserDAO;
import com.hungnln.mooncake.dtos.User;
import com.hungnln.mooncake.utils.StaticVariable;
import com.hungnln.mooncake.utils.URL;
import com.hungnln.mooncake.utils.Validate;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


/**
 * @author SE140018
 */
public class LoginServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = "";
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        try {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("userdata");
            if (user == null) {
                url = URL.LOGIN_PAGE;
            } else {
                if (user.getRoleId().getId() == StaticVariable.getAdminRole()) {
                    url = "LoadCake";
                    request.setAttribute("msg", "You have login");
                    request.setAttribute("msgStatus", "true");
                }
            }
        } catch (Exception e) {
            log("LoginServlet_Exception: " + e.getMessage());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        String url = URL.LOGIN_PAGE;
        try {
            HttpSession session = request.getSession(false);
            String userID = request.getParameter("userID");
            boolean valid = true;
            if (!Validate.checkUserIDValidate(userID.trim())) {
                valid = false;
            }

            String userPassword = request.getParameter("userPassword");
            if (!Validate.checkUserPasswordValidate(userPassword.trim())) {
                valid = false;
            }
            if (valid) {
                UserDAO userDao = new UserDAO();
                User user = userDao.checkLogin(userID, userPassword);
                if (user != null) {
                    session.setAttribute("userdata", user);
                    url = "LoadCake";
                } else {
                    request.setAttribute("msgLoginFail", "Wrong ID or Password. Please try again.");
                }
            }

        } catch (Exception e) {
            log("LoginServlet_Exception: " + e.getMessage());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
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
