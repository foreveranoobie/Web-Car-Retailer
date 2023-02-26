<!DOCTYPE HTML>
<%@page contentType="gzip;charset=UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${pageContext.request.locale.language}"/>
<fmt:setBundle basename="words"/>
<html>
<c:import url="pages/frames.jsp" />
<head>
  <title>Main Page</title>
  <meta http-equiv="content-type" content="text/html; charset=UTF-8"><title>W3.CSS Template</title>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="../styles/w3.css">
  <link rel="stylesheet" href="../styles/css.css">
  <link rel="stylesheet" href="../styles/font-awesome.css">
  <script src="js/jquery/jquery_3.4.1.js"></script>
  <script src="js/cart_list.js"></script>
  <script src="js/orders.js"></script>
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
      <a href="#" onclick="w3_close()" class="w3-bar-item w3-button"><fmt:message key="main_page"/></a>
      <a href="/getProducts" onclick="w3_close()" class="w3-bar-item w3-button"><fmt:message key="auto_sale"/></a>
      <button onclick="openFrame();" onclick="w3_close()" class="w3-bar-item w3-button"><fmt:message key="cart_list"/></button>
      <c:if test="${not empty userState}">
      <button onclick="openOrders();" onclick="w3_close()" class="w3-bar-item w3-button"><fmt:message key="orders_list"/></button>
      </c:if>
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
  <p><fmt:message key="index_text"/></p>
  
  <hr id="about">

  <!-- About Section -->
  
  <hr>
  
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