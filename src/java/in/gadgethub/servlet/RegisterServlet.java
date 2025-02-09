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
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author viren
 */
public class RegisterServlet extends HttpServlet {

   
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       String userName = request.getParameter("user_name");
        String userEmail = request.getParameter("user_email");
        String address = request.getParameter("address");
        String mobile = request.getParameter("mobile");
        String pinCodeStr = request.getParameter("pincode");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("cpassword");

        // Validating input
        if (userName == null || userEmail == null || address == null || mobile == null || 
            pinCodeStr == null || password == null || confirmPassword == null) {
            request.setAttribute("message", "All fields are required.");
            RequestDispatcher rd = request.getRequestDispatcher("register.jsp");
            rd.forward(request, response);
            return;
        }

        if (!password.equals(confirmPassword)) {
            request.setAttribute("message", "Passwords do not match.");
            RequestDispatcher rd = request.getRequestDispatcher("register.jsp");
            rd.forward(request, response);
            return;
        }

        int pinCode;
        try {
            pinCode = Integer.parseInt(pinCodeStr);
        } catch (NumberFormatException e) {
            request.setAttribute("message", "Invalid Pin Code. Please enter a valid number.");
            RequestDispatcher rd = request.getRequestDispatcher("register.jsp");
            rd.forward(request, response);
            return;
        }

        // Creating a UserPojo object
        UserPojo user = new UserPojo();
        user.setUsername(userName);
        user.setUseremail(userEmail);
        user.setAddress(address);
        user.setMobile(mobile);
        user.setPincode(pinCode); // Set integer pin code
        user.setPassword(password);

  //       Calling DAO to save user details
        UserDaoImpl userDao = new UserDaoImpl();
        String isUserRegistered = userDao.registerUser(user);

        if (isUserRegistered != null) {
            // Redirect to success page or login
            response.sendRedirect("login.jsp?message=Registration successful! Please log in.");
        } else {
            // Show error message on the registration page
            request.setAttribute("message", "Registration failed. Email might already be in use.");
            RequestDispatcher rd = request.getRequestDispatcher("register.jsp");
            rd.forward(request, response);
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
