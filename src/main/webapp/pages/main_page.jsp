<!DOCTYPE HTML>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<html>

<head>
  <title>Main Page</title>
  <meta http-equiv="content-type" content="text/html; charset=UTF-8"><title>W3.CSS Template</title>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="../styles/w3.css">
  <link rel="stylesheet" href="../styles/css.css">
  <link rel="stylesheet" href="../styles/font-awesome.css">
  <style>
    body,h1,h2,h3,h4,h5,h6 {font-family: "Karma", sans-serif}
    .w3-bar-block .w3-bar-item {padding:20px}
    </style>
</head>
<body>
    <!-- Sidebar (hidden by default) -->
    <nav class="w3-sidebar w3-bar-block w3-card w3-top w3-xlarge w3-animate-left" style="display: none; z-index: 2; width: 40%; min-width: 300px;" id="mySidebar">
      <a href="javascript:void(0)" onclick="w3_close()" class="w3-bar-item w3-button">Close Menu</a>
      <a href="#" onclick="w3_close()" class="w3-bar-item w3-button">Main page</a>
      <a href="../index.jsp" onclick="w3_close()" class="w3-bar-item w3-button">Auto sale</a>
</nav>
<!-- Top menu -->
<div class="w3-top">
  <div class="w3-white w3-xlarge" style="max-width:1200px;margin:auto">
    <div class="w3-button w3-padding-16 w3-left" onclick="w3_open()">
      <div style="width: 20px; height: 3px; background-color: black; margin: 4px 0;"></div>
      <div style="width: 20px; height: 3px; background-color: black; margin: 4px 0;"></div>
      <div style="width: 20px; height: 3px; background-color: black; margin: 4px 0;"></div>
    </div>
    <div class="w3-center w3-padding-16">Car sale</div>
  </div>
</div>

<tags:loginForm/>

<div class="w3-main w3-content w3-padding" style="max-width:1200px;margin-top:100px">

  <p>Welcome to our site. We are the company which helps you to buy used cars in a good conditions. Our advantage is that we don't propose you a piece of rusted metal. We suppose you to buy a car that
    will make you a lot of nice impressions and you wouldn't have any minds about the wrong choice.</p>
  
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