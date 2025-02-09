/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gadgethub.servlet;

import in.gadgethub.dao.impl.UserDaoImpl;
import in.gadgethub.pojo.UserPojo;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author viren
 */
public class LoginServlet2 extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
          String userName = request.getParameter("username");
        String password = request.getParameter("password");
        String userType = request.getParameter("userType");
        String status = "Login denied! Invalid userId or password";

        if (userType.equals("admin")) {
            if (userName.equals("admin@gmail.com") && password.equals("admin")) {
                // Set session attributes
                HttpSession session = request.getSession();
                session.setAttribute("userName", userName);
                session.setAttribute("password", password);
                session.setAttribute("userType", userType);

                // Create cookies automatically (no "Remember Me" checkbox needed)
                Cookie userCookie = new Cookie("userName", userName);
                userCookie.setMaxAge(60 * 60 * 24 * 7); // 1 week
                response.addCookie(userCookie);

                Cookie passCookie = new Cookie("password", password);
                passCookie.setMaxAge(60 * 60 * 24 * 7); // 1 week
                response.addCookie(passCookie);

                RequestDispatcher rd = request.getRequestDispatcher("./AdminViewServlet");
                rd.forward(request, response);
            } else {
                RequestDispatcher rd = request.getRequestDispatcher("login.jsp?message=" + status);
                rd.include(request, response);
            }
        } else if (userType.equals("customer")) {
            UserDaoImpl userDao = new UserDaoImpl();
            status = userDao.isValidCrendentials(userName, password);

            if (status.equalsIgnoreCase("Login Successfull")) {
                UserPojo userPojo = userDao.getUserDetails(userName);

                HttpSession session = request.getSession();
                session.setAttribute("userdata", userPojo);
                session.setAttribute("userName", userName);
                session.setAttribute("password", password);
                session.setAttribute("userType", userType);

                // Create cookies automatically (no "Remember Me" checkbox needed)
                Cookie userCookie = new Cookie("userName", userName);
                userCookie.setMaxAge(60 * 60 * 24 * 7); // 1 week
                response.addCookie(userCookie);

                Cookie passCookie = new Cookie("password", password);
                passCookie.setMaxAge(60 * 60 * 24 * 7); // 1 week
                response.addCookie(passCookie);

                RequestDispatcher rd = request.getRequestDispatcher("./UserHomeServlet");
                rd.forward(request, response);
            } else {
                RequestDispatcher rd = request.getRequestDispatcher("login.jsp?message=" + status);
                rd.include(request, response);
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
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
