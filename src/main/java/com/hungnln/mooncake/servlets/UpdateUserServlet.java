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

/**
 * @author SE140018
 */
public class UpdateUserServlet extends HttpServlet {

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
        String url = URL.LOGIN_PAGE;
        try {
            HttpSession session = request.getSession(false);
            {
                User user = (User) session.getAttribute("userdata");
                if (user != null) {
                    url = URL.USER_PROFILE_PAGE;
                } else {
                    request.setAttribute("msgLoginFail", "Please login and try again");
                }
            }
        } catch (Exception e) {
            log("ChangePassWord_Exception at: " + e.getMessage());
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
        String url = URL.USER_PROFILE_PAGE;
        try {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("userdata");
            boolean valid = true;

            if (user != null) {
                String userName = request.getParameter("userName");
                if (!Validate.checkUserNameValidate(userName.trim())) {
                    valid = false;
                    request.setAttribute("msgName", "Enter length from 1 to 30 with first letter is uppercase. ");
                }
                String userPhoneNumber = request.getParameter("userPhoneNumber");
                if (!Validate.checkUserPhoneValidate(userPhoneNumber)) {
                    valid = false;
                    request.setAttribute("msgPhoneNumber", "Enter with digit and first digit is 0. ");
                }
                String userAddress = request.getParameter("userAddress");
                if (userAddress.trim().isEmpty() || userAddress.trim().length() > 90) {
                    valid = false;
                    request.setAttribute("msgAddress", "Enter length 90");
                }
                if (valid) {
                    User userUpdate = new User(user.getId(), user.getUserPassword(), userPhoneNumber, userAddress, user.getRoleId(), userName);
                    UserDAO userDAO = new UserDAO();
                    if (userDAO.createOrUpdateUser(userUpdate)) {
                        session.setAttribute("userdata", userUpdate);
                        request.setAttribute("msgUpdate", "User Update Success");
                    } else {
                        request.setAttribute("msgUpdate", "User Update Fail");
                    }
                }
            } else {
                url = URL.LOGIN_PAGE;
                request.setAttribute("msgLogin", "Please login");
            }
        } catch (Exception e) {
            log("UpdateUserServlet_Exception: " + e.getMessage());

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
