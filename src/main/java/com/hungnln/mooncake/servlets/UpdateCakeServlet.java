/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hungnln.mooncake.servlets;

import com.hungnln.mooncake.daos.CakeDAO;
import com.hungnln.mooncake.daos.CategoryDAO;
import com.hungnln.mooncake.daos.UpdateLogDAO;
import com.hungnln.mooncake.dtos.Cake;
import com.hungnln.mooncake.dtos.Category;
import com.hungnln.mooncake.dtos.Log;
import com.hungnln.mooncake.dtos.User;
import com.hungnln.mooncake.utils.StaticVariable;
import com.hungnln.mooncake.utils.URL;
import com.hungnln.mooncake.utils.UploadFileUtils;
import com.hungnln.mooncake.utils.Validate;

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
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


/**
 * @author SE140018
 */
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 10, // 10 MB 
        maxFileSize = 1024 * 1024 * 50, // 50 MB
        maxRequestSize = 1024 * 1024 * 100)
public class UpdateCakeServlet extends HttpServlet {

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
            out.println("<title>Servlet UpdateCakeServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UpdateCakeServlet at " + request.getContextPath() + "</h1>");
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
        boolean valid = false;
        try {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("userdata");
            if (user != null) {
                if (user.getRoleId().getId() == StaticVariable.getAdminRole()) {
                    url = URL.UPDATE_CAKE_PAGE;
                    String cakeID = request.getParameter("cakeID");
                    CakeDAO cakeDAO = new CakeDAO();
                    Cake cake = cakeDAO.getCakeByID(cakeID);
                    request.setAttribute("cake", cake);
                    CategoryDAO categoryDAO = new CategoryDAO();
                    List<Category> categories = categoryDAO.getAllCategory();
                    request.setAttribute("categories", categories);
                    valid = true;
                }
            }
            if (valid == false) {
                request.setAttribute("msg", "You can't use this function");
            }
        } catch (Exception e) {
            log("UpdateCakeServlet_Exception at: " + e.getMessage());
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
        boolean valid = true;
        String url = URL.UPDATE_CAKE_PAGE;
        try {
            HttpSession session = request.getSession(false);
            User user = (User) session.getAttribute("userdata");
            if (user != null) {
                String cakeID = request.getParameter("cakeID");
                CakeDAO cakeDAO = new CakeDAO();
                Cake cake = cakeDAO.getCakeByID(cakeID);
                String cakeName = request.getParameter("cakeName");
                if (!Validate.checkCakeNameValidate(cakeName.trim())) {
                    valid = false;
                    request.setAttribute("msgCakeName", "Length 30 with no digit or special character");
                }
                String cakeDescription = request.getParameter("cakeDescription");
                int cakeQuantity = StaticVariable.getDefaultQuantity();
                if (!Validate.checkCakeDescriptionValidate(cakeDescription.trim())) {
                    valid = false;
                    request.setAttribute("msgCakeDescription", "Length 200");
                }
                try {
                    String cakeQuantityStr = request.getParameter("cakeQuantity");
                    if (!cakeQuantityStr.trim().isEmpty()) {
                        int cakeQuantityConvertInt = Integer.parseInt(cakeQuantityStr);
                        if (cakeQuantityConvertInt > 1) {
                            cakeQuantity = cakeQuantityConvertInt;
                        }
                    }
                } catch (Exception e) {
                    log("UpdateCakeServlet_Exception at: " + e.getMessage());
                    valid = false;
                    request.setAttribute("msgCakeQuantity", "Wrong type input");
                }
                Integer cakePrice = 0;
                try {
                    String cakePriceStr = request.getParameter("cakePrice");
                    if (cakePriceStr.trim().isEmpty()) {
                        valid = false;
                        request.setAttribute("msgCakePrice", "Input Price");
                    } else {
                        Integer cakePriceConvertInt = Integer.parseInt(cakePriceStr);
                        if (cakePriceConvertInt > 0) {
                            cakePrice = cakePriceConvertInt;
                        } else {
                            valid = false;
                            request.setAttribute("msgCakePrice", "Cake Price > 0");
                        }
                    }
                } catch (Exception e) {
                    log("UpdateCakeServlet_Exception at: " + e.getMessage());
                    valid = false;
                    request.setAttribute("msgCakePrice", "Wrong type input");
                }
                String expirationDateStr = request.getParameter("expirationDate");
                LocalDate expirationDateLocalDate = null;
                if (expirationDateStr.trim().isEmpty()) {
                    valid = false;
                } else {
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Date expirationDate = dateFormat.parse(expirationDateStr);
                    expirationDateLocalDate = Instant.ofEpochMilli(expirationDate.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
                    if (expirationDateLocalDate.compareTo(cake.getCreateDate()) < 0) {
                        valid = false;
                        request.setAttribute("msgExpirationDate", "Expiration Date can not before CreateDate");
                    }
                }
                String categoryID = request.getParameter("categoryID");
                CategoryDAO categoryDAO = new CategoryDAO();
                Category category = categoryDAO.getCategoryByID(categoryID);
                boolean status = Boolean.parseBoolean(request.getParameter("cakeStatus").trim());
                String cakeImage = "";
                String cakeImageOld = cake.getCakeImage();
                String cakeImageNew = UploadFileUtils.uploadFile(request);
                if (cakeImageNew.trim().isEmpty()) {
                    cakeImage = cakeImageOld;
                } else {
                    cakeImage = cakeImageNew;
                }
                if (valid) {
                    Cake cakeUpdate = new Cake(cakeID, cakeName, cakeDescription, cakePrice, cakeQuantity, cake.getCreateDate(), expirationDateLocalDate, category, status, cakeImage);
                    boolean check = cakeDAO.createOrUpdateCake(cakeUpdate);
                    if (check) {
                        UpdateLogDAO updateLogDAO = new UpdateLogDAO();
                        Date date = new Date();
                        LocalDate saveDate = Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
                        Log log = new Log(cake, saveDate, user);
                        if (updateLogDAO.createOrUpdateLog(log)) {
                            request.setAttribute("msgUpdate", "Update Success");
                            request.setAttribute("cake", cakeUpdate);
                        } else {
                            request.setAttribute("msgUpdate", "Update Fail");
                            request.setAttribute("cake", cake);
                        }
                    } else {
                        request.setAttribute("msgUpdate", "Update Fail");
                        request.setAttribute("cake", cake);
                    }
                } else {
                    request.setAttribute("cake", cake);
                }
                List<Category> categories = categoryDAO.getAllCategory();
                request.setAttribute("categories", categories);
            }
        } catch (Exception e) {
            log("UpdateCakeServlet_Exception at: " + e.getMessage());
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
