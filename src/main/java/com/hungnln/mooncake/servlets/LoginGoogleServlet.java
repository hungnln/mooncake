/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hungnln.mooncake.servlets;

import com.hungnln.mooncake.daos.RoleDAO;
import com.hungnln.mooncake.daos.UserDAO;
import com.hungnln.mooncake.dtos.GooglePojo;
import com.hungnln.mooncake.dtos.Role;
import com.hungnln.mooncake.dtos.User;
import com.hungnln.mooncake.utils.GoogleUtils;
import com.hungnln.mooncake.utils.StaticVariable;
import com.hungnln.mooncake.utils.URL;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


/**
 * @author SE140018
 */
public class LoginGoogleServlet extends HttpServlet {

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
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        String url = URL.LOGIN_PAGE;
        try {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("userdata");
            String code = request.getParameter("code");
            if (user != null) {
                url = "LoadCake";
            } else {
                if (code == null || code.isEmpty()) {
                    url = URL.LOGIN_PAGE;
                } else {
                    String accessToken = GoogleUtils.getToken(code);
                    GooglePojo googlePojo = GoogleUtils.getUserInfo(accessToken);
                    String userID = googlePojo.getId();
                    String userName = googlePojo.getName();
                    UserDAO userDAO = new UserDAO();
                    if (!(userID.isEmpty())) {
                        user = userDAO.findUser(userID);
                        if (user != null) {
                            url = "LoadCake";
                        } else {
                            RoleDAO roleDAO = new RoleDAO();
                            Role role = roleDAO.getRoleByRoleId(StaticVariable.getUserRole());
                            user = new User(userID, StaticVariable.getDefaultGooglePassword(), StaticVariable.getDefaultGooglePhone(), StaticVariable.getDefaultGoogleAddress(), role, userName);
                            if (userDAO.createOrUpdateUser(user)) {
                                url = URL.USER_PROFILE_PAGE;
                            }
                        }
                        session.setAttribute("userdata", user);
                    }
                }
            }
        } catch (Exception e) {
            log("LoginGoogleServlet_Exception at: " + e.getMessage());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
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
        processRequest(request, response);
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
