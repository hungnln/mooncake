/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hungnln.mooncake.servlets;

import com.hungnln.mooncake.daos.OrderDAO;
import com.hungnln.mooncake.dtos.Order;
import com.hungnln.mooncake.dtos.User;
import com.hungnln.mooncake.utils.StaticVariable;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


/**
 * @author SE140018
 */
public class ChangeOrderStatusServlet extends HttpServlet {

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
        String url = "OrderDetail";
        int status = -2;
        try {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("userdata");
            if (user == null) {
                url = "LoadCake";
                request.setAttribute("msg", "You can't use this funtion");
                request.setAttribute("msgStatus", "true");
            } else {
                String statusString = request.getParameter("status");
                if (!(statusString.isEmpty())) {
                    status = Integer.parseInt(statusString);
                }
                String orderID = request.getParameter("orderID");
                OrderDAO orderDAO = new OrderDAO();
                Order order = orderDAO.getOrderByOrderID(orderID);
                if (order == null) {
                    request.setAttribute("msg", "Can't find order");

                    request.setAttribute("msg", "Can't find detail");
                } else {
                    if (user.getRoleId().getId() == StaticVariable.getAdminRole()) {
                        if ((status == -2 || status == 3) || (status > 3 || status < -2)) {
                            request.setAttribute("msg", "You can't do this");
                        } else {
                            order.setOrderStatus(status);
                            boolean check = orderDAO.createOrUpdateOrder(order);
                            if (check) {
                                request.setAttribute("msg", "Change success");
                                request.setAttribute("orderID", orderID);
                            }
                        }
                    } else {
                        if (status != 3) {
                            request.setAttribute("msg", "You can't do this");
                        } else {
                            order.setOrderStatus(status);
                            boolean check = orderDAO.createOrUpdateOrder(order);
                            if (check) {
                                request.setAttribute("msg", "Change success");
                                request.setAttribute("orderID", orderID);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            log("ChangeOrderStatusServlet_Exception at: " + e.getMessage());
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
