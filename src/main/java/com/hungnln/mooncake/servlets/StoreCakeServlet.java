/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hungnln.mooncake.servlets;

import com.hungnln.mooncake.daos.CakeDAO;
import com.hungnln.mooncake.dtos.Cake;
import com.hungnln.mooncake.dtos.ShoppingCartItem;
import com.hungnln.mooncake.dtos.User;
import com.hungnln.mooncake.utils.Cart;
import com.hungnln.mooncake.utils.StaticVariable;
import com.hungnln.mooncake.utils.URL;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


/**
 * @author SE140018
 */
public class StoreCakeServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String url = "LoadCake";
        boolean valid = true;
        try {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("userdata");
            if (user != null) {
                if (user.getRoleId().getId() == StaticVariable.getAdminRole()) {
                    valid = false;

                    request.setAttribute("msg", "You can't use this function");
                    request.setAttribute("msgStatus", "true");
                }
            }
            String cakeID = request.getParameter("cakeID");
            CakeDAO cakeDAO = new CakeDAO();
            Cake cake = cakeDAO.getCakeByID(cakeID);
            if (cake == null) {
                request.setAttribute("msg", "Can't find any cake to store in cart");
                request.setAttribute("msgStatus", "true");
            } else if (!cake.getCakeStatus() || cake.getCakeQuantity() < 0) {
                request.setAttribute("msg", "Cake is not sale or no cake left");
                request.setAttribute("msgStatus", "true");
            } else {
                boolean status = cakeDAO.getCakeByID(cakeID).getCakeStatus();
                if (valid && status) {
                    url = URL.CART_PAGE;
                    if (session.getAttribute("cart") == null) {
                        List<ShoppingCartItem> cart = new ArrayList<>();
                        ShoppingCartItem item = new ShoppingCartItem(cake, 1);
                        cart.add(item);
                        session.setAttribute("cart", cart);
                    } else {
                        List<ShoppingCartItem> cart = (List<ShoppingCartItem>) session.getAttribute("cart");
                        int index = Cart.isExistCake(cakeID, cart);
                        if (index == -1) {
                            ShoppingCartItem item = new ShoppingCartItem(cake, 1);
                            cart.add(item);
                        } else {
                            int quantity = cart.get(index).getQuantity() + 1;
                            cart.get(index).setQuantity(quantity);
                        }
                        session.setAttribute("cart", cart);
                    }
                }
            }
        } catch (Exception e) {
            log("StoreCakeServlet_Exception at: " + e.getMessage());
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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
