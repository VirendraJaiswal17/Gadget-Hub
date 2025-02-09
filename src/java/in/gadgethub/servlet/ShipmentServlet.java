/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gadgethub.servlet;

import in.gadgethub.dao.impl.OrderDaoImpl;
import in.gadgethub.pojo.OrderPojo;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author viren
 */
public class ShipmentServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
         HttpSession session = request.getSession();
        String userName = (String) session.getAttribute("userName");
        String password = (String) session.getAttribute("password");
        String userType = (String) session.getAttribute("userType");
        
        if (userType == null || !userType.equalsIgnoreCase("admin")) {
            response.sendRedirect("login.jsp?message=Access denied ! Please login as admin");
        } else if (userName == null || password == null) {
            response.sendRedirect("login.jsp?message=Session expired ! Please login again");
        } else {
            String orderId = request.getParameter("orderid");
            String prodId = request.getParameter("prodid");
            
            if (orderId != null && prodId != null) {
                OrderDaoImpl orderDao = new OrderDaoImpl();
                String status = orderDao.shipNow(orderId, prodId);
                
                if ("Order has been shipped successfully".equals(status)) {
                    response.sendRedirect("adminHome.jsp?msg=Shipment Successful");
                } else {
                    response.sendRedirect("adminHome.jsp?msg=Shipment Failed");
                }
            } else {
                response.sendRedirect("adminHome.jsp?msg=Invalid Request");
            }
        }
        
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
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
