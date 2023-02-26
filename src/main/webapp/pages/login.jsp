<!DOCTYPE HTML>
<%@page contentType="gzip;charset=UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${pageContext.request.locale.language}"/>
<fmt:setBundle basename="words"/>
<html>
<c:import url="frames.jsp" />
<head>
  <title>Login Page</title>
  <meta http-equiv="content-type" content="text/html; charset=UTF-8"><title>W3.CSS Template</title>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="../styles/w3.css">
  <link rel="stylesheet" href="../styles/css.css">
  <link rel="stylesheet" href="../styles/font-awesome.css">
  <script src="../js/jquery/jquery_3.4.1.js"></script>
  <script src="../js/jquery_login_validator.js"></script>
  <script src="../js/cart_list.js"></script>
  <style>
    body,h1,h2,h3,h4,h5,h6 {font-family: "Karma", sans-serif}
    .w3-bar-block .w3-bar-item {padding:20px}
    </style>
</head>

<body>
<!-- Sidebar (hidden by default) -->
<nav class="w3-sidebar w3-bar-block w3-card w3-top w3-xlarge w3-animate-left" style="display: none; z-index: 2; width: 25%; min-width: 300px;" id="mySidebar">
  <a href="javascript:void(0)" onclick="w3_close()" class="w3-bar-item w3-button">X</a>
  <tags:loginForm/>
  <a href="../index.jsp" onclick="w3_close()" class="w3-bar-item w3-button"><fmt:message key="main_page"/></a>
  <a href="/getProducts" onclick="w3_close()" class="w3-bar-item w3-button"><fmt:message key="auto_sale"/></a>
  <button onclick="openFrame();" class="w3-bar-item w3-button"><fmt:message key="cart_list"/></button>
</nav>

<!-- Top menu -->
<div class="w3-top">
  <tags:localizer/>
  <div class="w3-white w3-xlarge" style="max-width:1200px;margin:auto">
    <div class="w3-button w3-padding-16 w3-left" onclick="w3_open()">
      <div style="width: 20px; height: 3px; background-color: black; margin: 4px 0;"></div>
      <div style="width: 20px; height: 3px; background-color: black; margin: 4px 0;"></div>
      <div style="width: 20px; height: 3px; background-color: black; margin: 4px 0;"></div>
    </div>
    
    <div class="w3-center w3-padding-16">Car sale</div>
  </div>
</div>
    <div class="w3-main w3-content w3-padding" style="max-width:1200px;margin-top:100px">
      <div style="color:#c70039;position: fixed;margin-left: 28%;"><c:out value="${requestScope['user_error']}"/></div>
      <div style="margin-top: 4%;">
        <!-- insert the page content here -->
        <form name="login" method="post" action="../loginUser" style="text-align: center;" onsubmit="return loginValidation();">
        <table style="margin-left: auto;margin-right: auto; width: 100%">
          <tr>
            <td style="text-align: right;width: 15%;"><fmt:message key="username"/></td>
            <td style="width: 10%;"><input class="reg" type="text" name="login" value="${requestScope['login']}"/></td>
            <td style="width: 15%; color:#c70039;" id="login_error">${requestScope["log_error"]}</td>
          </tr>
          <tr>
            <td style="text-align: right;"><fmt:message key="password"/></td>
            <td><input class="reg" type="password" name="password" value="${password}"/></td>
            <td style="width: 15%; color:#c70039;" id="password_error">${requestScope["pas_error"]}</td>
          </tr>
        </table>
        <input type="submit" value=<fmt:message key="login"/>></input>
        </form>
        </div>
    </div>
  <div><hr id="about">

  <!-- About Section -->
  
  <hr></div>
  
  <!-- Footer -->
  <footer class="w3-row-padding w3-padding-32">
    <div class="w3-third">
      <p>Powered by <a href="https://www.w3schools.com/w3css/default.asp" target="_blank">w3.css</a></p>
    </div>
  
    

    
  </footer>

<!-- End page content -->
</div>

<script>
// Script to open and close sidebar
function w3_open() {
  document.getElementById("mySidebar").style.display = "block";
}
 
function w3_close() {
  document.getElementById("mySidebar").style.display = "none";
}
</script>

</body>
</html>
