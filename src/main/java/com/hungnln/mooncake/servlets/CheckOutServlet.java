/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hungnln.mooncake.servlets;

import com.hungnln.mooncake.daos.CakeDAO;
import com.hungnln.mooncake.daos.OrderDAO;
import com.hungnln.mooncake.daos.OrderDetailDAO;
import com.hungnln.mooncake.dtos.*;
import com.hungnln.mooncake.utils.RandomID;
import com.hungnln.mooncake.utils.StaticVariable;
import com.hungnln.mooncake.utils.URL;
import com.hungnln.mooncake.utils.Validate;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * @author SE140018
 */
public class CheckOutServlet extends HttpServlet {

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
        String url = URL.CHECKOUT_PAGE;
        boolean valid = true;
        try {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("userdata");
            if (user != null) {
                if (user.getRoleId().getId() == StaticVariable.getUserRole()) {
                    if (session.getAttribute("cart") == null) {
                        url = URL.CART_PAGE;
                    } else {
                        String customerPhone = request.getParameter("customerPhone");
                        if (!Validate.checkUserPhoneValidate(customerPhone.trim())) {
                            valid = false;
                            request.setAttribute("msgCustomerPhone", "Please input right number phone");
                        }
                        String customerName = request.getParameter("customerName");
                        if (!Validate.checkUserNameValidate(customerName.trim())) {
                            valid = false;
                            request.setAttribute("msgCustomerName", "Please input right name ");
                        }
                        String customerAdress = request.getParameter("customerAddress");
                        if (!Validate.checkUserAddressValidate(customerAdress.trim())) {
                            valid = false;
                            request.setAttribute("msgCustomerAddress", "Please input address");
                        }
                        String orderPayment = request.getParameter("cbPayment");
                        if (valid) {
                            List<ShoppingCartItem> items = (List<ShoppingCartItem>) session.getAttribute("cart");
                            double orderTotal = 0;
                            for (ShoppingCartItem item : items) {
                                orderTotal += item.getCake().getCakePrice() * item.getQuantity();
                            }
                            Date creatOrderDate = new Date();
                            LocalDate creatOrderLocalDate = Instant.ofEpochMilli(creatOrderDate.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
                            String orderID;
                            OrderDAO orderDAO = new OrderDAO();
                            do {
                                orderID = RandomID.getRanDomOrderID();
                            } while (orderDAO.checkExistOrderID(orderID));
                            Order order = new Order(orderID, orderTotal, creatOrderLocalDate, user, 0, orderPayment, customerPhone, customerAdress, customerName);
                            if (orderDAO.createOrUpdateOrder(order)) {
                                for (ShoppingCartItem item : items) {
                                    OrderDetailDAO detailDAO = new OrderDetailDAO();
                                    OrderDetail orderDetail = new OrderDetail(order, item.getCake(), item.getQuantity());
                                    if (detailDAO.createOrUpdateOrderDetail(orderDetail)) {
                                        Cake cake = item.getCake();
                                        int oldQuantity = cake.getCakeQuantity();
                                        cake.setCakeQuantity(oldQuantity - item.getQuantity());
                                        CakeDAO cakeDAO = new CakeDAO();
                                        cakeDAO.createOrUpdateCake(cake);
                                        session.removeAttribute("cart");
                                    }
                                }
                                url = "OrderDetail";
                                request.setAttribute("orderID", orderID);
                            }
                        }
                    }
                } else {
                    url = "LoadCake";
                    request.setAttribute("msg", "You can't use this function");
                }

            } else {
                url = "LoadCake";
                request.setAttribute("msg", "You can't use this function");
            }
        } catch (Exception e) {
            log("CheckOut_Exception: " + e.getMessage());
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
