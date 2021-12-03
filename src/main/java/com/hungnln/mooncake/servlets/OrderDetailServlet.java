/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hungnln.mooncake.servlets;

import com.hungnln.mooncake.daos.OrderDAO;
import com.hungnln.mooncake.daos.OrderDetailDAO;
import com.hungnln.mooncake.dtos.Order;
import com.hungnln.mooncake.dtos.OrderDetail;
import com.hungnln.mooncake.dtos.User;
import com.hungnln.mooncake.utils.URL;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


/**
 * @author SE140018
 */
public class OrderDetailServlet extends HttpServlet {

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
        String url = "";
        try {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("userdata");
            if (user != null) {
                String orderID = (String) request.getAttribute("orderID");
                if (orderID == null) {
                    orderID = request.getParameter("orderID");
                }
                OrderDetailDAO orderDetailDAO = new OrderDetailDAO();
                OrderDAO orderDAO = new OrderDAO();
                Order order = orderDAO.getOrderByOrderID(orderID);
                if (order == null) {
                    request.setAttribute("msg", "Can't Find Order");
                } else {
                    List<OrderDetail> orderDetail = orderDetailDAO.getAllOrderDetail(order);
                    request.setAttribute("orderDetails", orderDetail);
                    request.setAttribute("order", order);
                    url = URL.ORDER_DETAIL_PAGE;
                }
            } else {
                url = URL.LOGIN_PAGE;
                request.setAttribute("msg", "Login to view Order");
            }
        } catch (Exception e) {
            log("OrderDetailServlet_Exception at: " + e.getMessage());
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
