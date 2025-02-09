<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="in.gadgethub.dao.impl.ProductDaoImpl,java.util.*,javax.servlet.http.HttpSession" %>
<!DOCTYPE html>
<html>
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title>Categories of the Products</title>
 </head>
 <body>
 <%
    HttpSession userSession = request.getSession(false);
    String userType = "customer"; // Default user type
    if (userSession != null && userSession.getAttribute("userType") != null) {
        userType = (String) userSession.getAttribute("userType");
    }
    ProductDaoImpl productDao = new ProductDaoImpl();
    List<String> productTypes = productDao.getAllProductsType();
%>
  
  <nav>
    <ul class="nav">
      <!-- Other menu items -->
      <li class="nav-item dropdown" style="position: relative">
        <a
          class="nav-link dropdown-toggle"
          href="#"
          id="dropdownMenuLink"
          role="button"
          data-bs-toggle="dropdown"
          aria-expanded="false"
        >
          Category
        </a>
        <ul class="dropdown-menu" aria-labelledby="dropdownMenuLink">
          <% for (String type : productTypes) {
              String str = type.substring(0,1).toUpperCase() + type.substring(1).toLowerCase();
              String servlet = userType.equals("admin") ? "AdminViewServlet" : "LandingServlet";
          %>
            <li><a href="./<%=servlet%>?type=<%=type%>" class="dropdown-item"><%=str%></a></li>
          <% } %>
        </ul>
      </li>
      <!-- Other menu items can be added here -->
    </ul>
  </nav>
 </body>
</html>
