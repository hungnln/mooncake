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
import com.hungnln.mooncake.utils.StaticVariable;
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
public class SearchCakeServlet extends HttpServlet {

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
        String url = URL.INDEX_CAKE_PAGE;
        try {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("userdata");
            if (user != null) {
                if (user.getRoleId().getId() == StaticVariable.getAdminRole()) {
                    url = URL.CAKE_CRUD_PAGE;
                }
            }
            String min_max = request.getParameter("searchPriceRange");
            String[] part = min_max.split(",");
            int min = Integer.parseInt(part[0]);
            int max = Integer.parseInt(part[1]);
            String searchCakeName = request.getParameter("searchCakeName");
            String categoryID = request.getParameter("categoryID");
            CategoryDAO categoryDAO = new CategoryDAO();
            Category category = categoryDAO.getCategoryByID(categoryID);
            CakeDAO cakeDAO = new CakeDAO();
            List<Cake> cakes = null;
            int index = 1;
            String indexString = request.getParameter("index");
            if (indexString != null) {
                index = Integer.parseInt(indexString);
            }
            int pageSize = 8;
            int endPage = 0;
            int count = 0;
            if (searchCakeName.trim().isEmpty() && category == null) {
                count = cakeDAO.countSearchSortByRange(min, max);
                endPage = count / pageSize;
                if (count % pageSize != 0) {
                    endPage++;
                }
                cakes = cakeDAO.getAllCakeSortByPriceRange(min, max, pageSize, index);
            } else if (category == null || searchCakeName.isEmpty()) {
                if (category == null && !searchCakeName.isEmpty()) {
                    count = cakeDAO.countSearchByNameSortByRange(searchCakeName, min, max);
                    endPage = count / pageSize;
                    if (count % pageSize != 0) {
                        endPage++;
                    }
                    cakes = cakeDAO.searchCakesByNameSortByPriceRange(searchCakeName, min, max, pageSize, index);
                } else if (category != null) {
                    count = cakeDAO.countSearchByCategorySortByRange(categoryID, min, max);
                    endPage = count / pageSize;
                    if (count % pageSize != 0) {
                        endPage++;
                    }
                    cakes = cakeDAO.getAllCakeByCategorySortByRange(category, min, max, pageSize, index);
                }
            } else {
                count = cakeDAO.countSearchByNameByCategorySortByRange(searchCakeName, category, min, max);
                endPage = count / pageSize;
                if (count % pageSize != 0) {
                    endPage++;
                }
                cakes = cakeDAO.searchCakesByNameByCategorySortByPriceRange(category, searchCakeName, min, max, pageSize, index);
            }
            if (count == 0) {
                request.setAttribute("msg", "Can't find any cakes");
            } else {
                request.setAttribute("cakes", cakes);
                request.setAttribute("end", endPage);
                request.setAttribute("count", count);
                request.setAttribute("size", pageSize);
            }
            request.setAttribute("categories", categoryDAO.getAllCategory());
        } catch (Exception e) {
            log("SearchCakeServlet_Exception at: " + e.getMessage());
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
