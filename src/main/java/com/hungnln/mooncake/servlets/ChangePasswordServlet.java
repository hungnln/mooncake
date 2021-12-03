/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hungnln.mooncake.servlets;

import com.hungnln.mooncake.daos.UserDAO;
import com.hungnln.mooncake.dtos.User;
import com.hungnln.mooncake.utils.URL;
import com.hungnln.mooncake.utils.Validate;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class ChangePasswordServlet extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");

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
        String url = URL.LOGIN_PAGE;
        try {
            HttpSession session = request.getSession();
            {
                User user = (User) session.getAttribute("userdata");
                if (user != null) {
                    if (user.getUserPassword().equals("1")) {
                        url = URL.USER_PROFILE_PAGE;
                        request.setAttribute("msg", "Login by Google can't change passsword");
                    } else {
                        url = URL.USER_PASSWORD_PAGE;
                    }
                } else {
                    request.setAttribute("msg", "Please login to use this funtion");
                }
            }
        } catch (Exception e) {
            log("ChangePassword_Exception at: " + e.getMessage());
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
        String url = URL.USER_PASSWORD_PAGE;
        try {
            boolean valid = true;
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("userdata");
            if (user != null) {
                String userPassword = request.getParameter("userPassword");
                if (!userPassword.trim().equals(user.getUserPassword())) {
                    valid = false;
                    request.setAttribute("msgPassword", "Password is not correct");
                }
                String userNewPassword = request.getParameter("userNewPassword");
                if (!Validate.checkUserPasswordValidate(userNewPassword.trim())) {
                    valid = false;
                    request.setAttribute("msgNewPassword", "Enter length from 8 to 30 with at least digit, lowercase and uppercase. ");
                }
                String userReNewPassword = request.getParameter("userReNewPassword");
                if (!userReNewPassword.trim().equals(userNewPassword) || userReNewPassword.isEmpty()) {
                    valid = false;
                    request.setAttribute("msgReNewPassword", "Re-Password do not match.");
                }
                if (valid) {
                    User updateUser = new User(user.getId(), userNewPassword, user.getUserPhoneNumber(), user.getUserAddress(), user.getRoleId(), user.getUserName());
                    UserDAO userDAO = new UserDAO();
                    if (userDAO.createOrUpdateUser(updateUser)) {
                        session.setAttribute("userdata", updateUser);
                        request.setAttribute("msgUpdateSuccess", "Update Password success");
                    } else {
                        request.setAttribute("msgUpdateFail", "Update Password fail");
                    }
                }
            }
        } catch (Exception e) {
            log("ChangePasswordServlet_Exception: " + e.getMessage());
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
