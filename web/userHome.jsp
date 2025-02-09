<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ page import="in.gadgethub.utility.AppInfo" %>
<%@ page import="in.gadgethub.dao.impl.*,in.gadgethub.pojo.*,in.gadgethub.dao.*,java.util.*,javax.servlet.ServletOutputStream.*,java.io.*" %>
<!DOCTYPE html>
<html>
    <head>
        <title><%= AppInfo.appName %> Application</title>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" 
              rel="stylesheet" 
              integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" 
              crossorigin="anonymous">
        <link rel="stylesheet" href="mycss.css">
    </head>
    <body style="background-color: #E6F9E6;">

        <!-- Include Header -->
        <jsp:include page="header.jsp" />
        
        <!-- Display Message if Available -->
        <%
            String message = (String) request.getAttribute("message");
            if (message != null) {
        %>
        <div class="text-center h3 text-primary m-3"><%= message %></div>
        <% } %>

        <!-- Product List -->
        <div class="container">
            <div class="row text-center">
                <%
                    // Safely retrieve request attributes
//                    Map<String, Integer> mapList = (Map<String, Integer>) request.getAttribute("map");
                     Map<String, Integer> mapList = (HashMap) session.getAttribute("map");
                    //String userName = (String) request.getAttribute("userName");
                    String userName = (String) session.getAttribute("userName");
//                    List<ProductPojo> prodList = (List<ProductPojo>) request.getAttribute("products");
                    List<ProductPojo> prodList = (ArrayList<ProductPojo>) session.getAttribute("products");

                    // Null checks to avoid NullPointerException
                    if (mapList != null && prodList != null && userName != null) {
                        for (ProductPojo product : prodList) {
                            int cartQty = mapList.getOrDefault(product.getProdId(), 0);
                %>
                <div class="col-sm-4">
                    <div class="thumbnail mt-3 mb-3">
                        <img src="./ShowImageServlet?pid=<%= product.getProdId() %>" 
                             alt="Product"
                             style="height: 150px; max-width: 180px" 
                             class="mt-3">
                        <p class="productname"><%= product.getProdName() %></p>
                        <%
                            // Truncate product information safely
                            String description = product.getProdInfo();
                            description = description != null ? description.substring(0, Math.min(100, description.length())) : "";
                        %>
                        <p class="productinfo"><%=description%></p>
                        <p class="price">Rs <%=product.getProdPrice()%></p>
                        <form method="post">
                            <%if(cartQty == 0){%>
                            <button type="submit"
                                    formaction="./AddToCartServlet?uid=<%= userName %>&pid=<%= product.getProdId() %>&pqty=1"
                                    class="btn btn-warning">Add to Cart</button>
                            &nbsp;&nbsp;&nbsp;
                            <button type="submit"
                                    formaction="./AddToCartServlet?uid=<%= userName %>&pid=<%= product.getProdId() %>&pqty=1&action=buy"
                                    class="btn btn-primary">Buy Now</button>
                            <% } else { %>
                            <button type="submit"
                                    formaction="./AddToCartServlet?uid=<%= userName %>&pid=<%= product.getProdId() %>&pqty=0"
                                    class="btn btn-danger">Remove From Cart</button>
                                    &nbsp;&nbsp;&nbsp;
                            <button type="submit" formaction="./CartDetailsServlet"
                                    class="btn btn-success">Checkout</button>
                            <% } %>
                        </form>
                        <br />
                    </div>
                </div>
                <%
                        }
                    } else {
                %>
                <div class="text-center text-danger h4">No products available to display.</div>
                <% } %>
            </div>
        </div>
            

        <!-- Include Footer -->
        <%@ include file="footer.jsp" %>

    </body>
</html>