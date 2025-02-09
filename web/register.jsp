<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>Registration Page</title>
    <link
      href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
      rel="stylesheet"
      integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
      crossorigin="anonymous"
    />
    <link rel="stylesheet" href="style.css">
  </head>
  <body>
    <jsp:include page="header.jsp" />
    <div class="container mt-5 mb-5">
      <div class="row justify-content-center">
        <form action="./RegisterServlet" method="post" class="col-md-6 myform">
          <div class="text-center mt-3">
            <h2 class="text-primary">Registration Form</h2>
          </div>
          
          <!-- Dynamic Message Display -->
          <%
              String message = (String) request.getAttribute("message");
              if (message != null) {
          %>
              <div class="alert alert-info text-center">
                  <%= message %>
              </div>
<!--                  <p style="color:blue;"> 
                            <%=message%> 
                        </p> -->
          <%
              }
          %>
          
          <!-- Form Fields -->
          <div class="row">
            <div class="col-md-6 form-group">
              <label for="user_name">Name:</label>
              <input type="text" name="user_name" class="form-control" id="user_name" required />
            </div>

            <div class="col-md-6 form-group">
              <label for="user_email">Email:</label>
              <input type="email" name="user_email" class="form-control" id="user_email" required />
            </div>
          </div>

          <div class="form-group">
            <label for="address">Address</label>
            <textarea name="address" id="address" class="form-control" required></textarea>
          </div>

          <div class="row">
            <div class="col-md-6 form-group">
              <label for="mobile">Mobile No:</label>
              <input type="text" name="mobile" class="form-control" id="mobile" required />
            </div>

            <div class="col-md-6 form-group">
              <label for="pincode">Pin-code</label>
              <input type="number" name="pincode" class="form-control" id="pincode" required />
            </div>
          </div>

          <div class="row">
            <div class="col-md-6 form-group">
              <label for="password">Password</label>
              <input type="password" name="password" class="form-control" id="password" required />
            </div>

            <div class="col-md-6 form-group">
              <label for="cpassword">Confirm Password</label>
              <input type="password" name="cpassword" class="form-control" id="cpassword" required />
            </div>
          </div>

          <div class="row mt-3 mb-3 text-center">
            <div class="col-md-6">
              <button type="reset" class="btn btn-danger">Reset</button>
            </div>
            <div class="col-md-6">
              <button type="submit" class="btn btn-primary">Register</button>
            </div>
          </div>
        </form>
      </div>
    </div>
    <jsp:include page="footer.jsp" />
    <script
      src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
      integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
      crossorigin="anonymous"
    ></script>
  </body>
</html>