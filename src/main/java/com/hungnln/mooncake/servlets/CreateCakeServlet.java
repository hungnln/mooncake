/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hungnln.mooncake.servlets;

import com.hungnln.mooncake.daos.CakeDAO;
import com.hungnln.mooncake.daos.CategoryDAO;
import com.hungnln.mooncake.dtos.Cake;
import com.hungnln.mooncake.dtos.Category;
import com.hungnln.mooncake.dtos.User;
import com.hungnln.mooncake.utils.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * @author SE140018
 */
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 10, // 10 MB 
        maxFileSize = 1024 * 1024 * 50, // 50 MB
        maxRequestSize = 1024 * 1024 * 100)
public class CreateCakeServlet extends HttpServlet {

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
            out.println("<title>Servlet CreatCakeServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CreatCakeServlet at " + request.getContextPath() + "</h1>");
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
        String url = "LoadCake";
        boolean valid = true;
        try {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("userdata");
            if (user != null) {
                if (user.getRoleId().getId() == StaticVariable.getAdminRole()) {
                    url = URL.CREATE_CAKE_PAGE;
                    CategoryDAO categoryDAO = new CategoryDAO();
                    List<Category> categories = categoryDAO.getAllCategory();
                    request.setAttribute("categories", categories);
                    valid = false;
                }
            }
            if (valid) {
                request.setAttribute("msg", "You can't use this function");
                request.setAttribute("msgStatus", "true");
            }
        } catch (Exception e) {
            log("CreateCakeServlet_Exception at: " + e.getMessage());
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
        String url = URL.CREATE_CAKE_PAGE;
        boolean valid = true;
        try {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("userdata");
            if (user != null) {
                if (user.getRoleId().getId() == StaticVariable.getAdminRole()) {
                    String cakeName = request.getParameter("cakeName");
                    if (!Validate.checkCakeNameValidate(cakeName.trim())) {
                        valid = false;
                        request.setAttribute("msgCakeName", "Length 30 with no digit or special character");
                    }
                    String cakeDescription = request.getParameter("cakeDescription");
                    if (!Validate.checkCakeDescriptionValidate(cakeDescription.trim())) {
                        valid = false;
                        request.setAttribute("msgCakeDescription", "Length 200");
                    }
                    Integer cakePrice = 0;
                    String cakePriceStr = request.getParameter("cakePrice");
                    try {
                        if (cakePriceStr.trim().isEmpty()) {
                            valid = false;
                            request.setAttribute("msgCakePrice", "Input price");
                        } else {
                            Integer cakePriceConvertInt = Integer.parseInt(cakePriceStr);
                            if (cakePriceConvertInt <= 0) {
                                valid = false;
                                request.setAttribute("msgCakePrice", "Price > 0");
                            } else {
                                cakePrice = cakePriceConvertInt;
                            }
                        }
                    } catch (Exception e) {
                        log("CreateCakeServlet_Exception at: " + e.getMessage());
                        valid = false;
                        request.setAttribute("msgCakePrice", "Wrong input type");
                    }
                    int cakeQuantity = StaticVariable.getDefaultQuantity();
                    String cakeQuantityStr = request.getParameter("cakeQuantity");
                    try {
                        if (cakeQuantityStr.trim().isEmpty() == false) {
                            int cakeQuantityConvertInt = Integer.parseInt(cakeQuantityStr);
                            if (cakeQuantityConvertInt > 1) {
                                cakeQuantity = cakeQuantityConvertInt;
                            }
                        }
                    } catch (Exception e) {
                        log("CreateCakeServlet_Exception at: " + e.getMessage());
                        valid = false;
                        request.setAttribute("msgCakeQuantity", "Wrong input type");
                    }
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Date creatDate = new Date();
                    LocalDate createDateLocalDate = Instant.ofEpochMilli(creatDate.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
                    LocalDate expirationDateLocalDate = null;
                    String expirationDateStr = request.getParameter("expirationDate");
                    if (expirationDateStr.trim().isEmpty()) {
                        valid = false;
                        request.setAttribute("msgExpirationDate", "Choose Expiration Day");
                    } else {
                        Date expirationDate = dateFormat.parse(expirationDateStr);
                        if (expirationDate.compareTo(creatDate) >= 0) {
                            expirationDateLocalDate = Instant.ofEpochMilli(expirationDate.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
                        } else {
                            valid = false;
                            request.setAttribute("msgExpirationDate", "Expiration Date can not before Create DATE");
                        }

                    }
                    boolean status = Boolean.parseBoolean(request.getParameter("cakeStatus"));
                    String categoryID = request.getParameter("categoryID");
                    CategoryDAO categoryDAO = new CategoryDAO();
                    Category category = categoryDAO.getCategoryByID(categoryID);
                    CakeDAO cakeDAO = new CakeDAO();
                    String cakeID;
                    boolean existCakeID;
                    String cakeImage = UploadFileUtils.uploadFile(request);
                    do {
                        cakeID = RandomID.getRanDomCakeID();
                        existCakeID = cakeDAO.checkExistCakeID(cakeID);
                    } while (existCakeID);
                    if (valid) {
                        Cake cake = new Cake(cakeID, cakeName, cakeDescription, cakePrice, cakeQuantity, createDateLocalDate, expirationDateLocalDate, category, status, cakeImage);
                        boolean check = cakeDAO.createOrUpdateCake(cake);
                        if (check) {
                            request.setAttribute("msgCreate", "Create Success");
                        } else {
                            request.setAttribute("msgCreate", "Create Fail");
                        }
                    }
                }
            }
            CategoryDAO categoryDAO = new CategoryDAO();
            List<Category> categories = categoryDAO.getAllCategory();
            request.setAttribute("categories", categories);
        } catch (Exception e) {
            log("CreateCakeServlet_Exception at: " + e.getMessage());
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
