/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hungnln.mooncake.servlets;

import com.hungnln.mooncake.daos.RoleDAO;
import com.hungnln.mooncake.daos.UserDAO;
import com.hungnln.mooncake.dtos.Role;
import com.hungnln.mooncake.dtos.User;
import com.hungnln.mooncake.utils.StaticVariable;
import com.hungnln.mooncake.utils.URL;

import java.io.IOException;
import java.io.PrintWriter;

import com.hungnln.mooncake.utils.Validate;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;



/**
 * @author SE140018
 */
public class SignUpServlet extends HttpServlet {

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
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet SignInServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SignInServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
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
        String url = URL.SIGNUP_PAGE;
        try {
            HttpSession session = request.getSession();
            {
                User user = (User) session.getAttribute("userdata");
                if (user != null) {
                    url = "LoadCake";
                    request.setAttribute("msg", "You have login");
                    request.setAttribute("msgStatus", "true");
                }
            }
        } catch (Exception e) {
            log("SignupServlet_Exception at: " + e.getMessage());
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
        String url = URL.SIGNUP_PAGE;
        boolean valid = true;
        try {
            HttpSession session = request.getSession(false);
            User user = (User) session.getAttribute("userdata");
            if (user == null) {
                String userID = request.getParameter("userID");
                if (Validate.checkUserIDValidate(userID.trim())) {
                    UserDAO userDAO = new UserDAO();
                    if (userDAO.findUser(userID) != null) {
                        valid = false;
                        request.setAttribute("msgID", "ID is exist.");
                    }
                } else {
                    valid = false;
                    request.setAttribute("msgID", "Enter length from 8 to 30 with alphanumeric characters, lowercase or uppercase. ");
                }
                String userPassword = request.getParameter("userPassword");
                if (!Validate.checkUserPasswordValidate(userPassword.trim())) {
                    valid = false;
                    request.setAttribute("msgPassword", "Enter length from 8 to 30 with at least digit, lowercase and uppercase. ");
                }
                String userRePassword = request.getParameter("userRePassword");
                if (userRePassword.trim().isEmpty()) {
                    valid = false;
                    request.setAttribute("msgRePassword", "Empty");
                } else {
                    if (!userRePassword.trim().equals(userPassword)) {
                        valid = false;
                        request.setAttribute("msgRePassword", "Do not match password");
                    }
                }

                String userName = request.getParameter("userName");
                if (!Validate.checkUserNameValidate(userName)) {
                    valid = false;
                    request.setAttribute("msgName", "Enter length from 1 to 30 with first letter is uppercase. ");
                }

                String userPhoneNumber = request.getParameter("userPhoneNumber");
                if (!Validate.checkUserPhoneValidate(userPhoneNumber.trim())) {
                    valid = false;
                    request.setAttribute("msgName", "Enter with digit and first digit is 0. ");
                }
                String userAddress = request.getParameter("userAddress");
                if (!Validate.checkUserAddressValidate(userAddress.trim())) {
                    valid = false;
                    request.setAttribute("msgAddress", "Enter length 90");
                }
                if (valid) {
                    int userRole = StaticVariable.getUserRole();
                    RoleDAO roleDAO = new RoleDAO();
                    Role role = roleDAO.getRoleByRoleId(userRole);
                    User newUser = new User(userID, userPassword, userPhoneNumber, userAddress, role, userName);
                    UserDAO userDAO = new UserDAO();
                    if (userDAO.createOrUpdateUser(newUser)) {
                        url = URL.LOGIN_PAGE;
                        request.setAttribute("msgNotification", "SignUp Success");
                    } else {
                        request.setAttribute("msgNotification", "SignUp Fail");
                    }
                }
            } else {
                url = URL.INDEX_CAKE_PAGE;
            }
        } catch (Exception e) {
            log("SignUpServlet_Exception: " + e.getMessage());
        }
        finally {
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
