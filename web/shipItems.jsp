<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Ship Item</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="mycss.css">
</head>
<body style="background-color: #E6F9E6;">

    <jsp:include page="header.jsp" />

    <div class="container">
        <h3 class="text-center text-primary m-3">Ship an Order</h3>

        <form action="ShipmentServlet" method="post">
            <div class="mb-3">
                <label for="orderId" class="form-label">Order ID</label>
                <input type="text" class="form-control" id="orderId" name="orderId" required>
            </div>

            <div class="mb-3">
                <label for="prodId" class="form-label">Product ID</label>
                <input type="text" class="form-control" id="prodId" name="prodId" required>
            </div>

            <button type="submit" class="btn btn-success">Ship Now</button>
        </form>

        <%-- Show message after processing --%>
        <%
            String message = (String) session.getAttribute("message");
            if (message != null) {
        %>
            <div class="alert alert-info mt-3"><%= message %></div>
        <%
                session.removeAttribute("message"); // Prevent message from persisting after refresh
            }
        %>
    </div>

    <%@ include file="footer.jsp"%>

</body>
</html>
