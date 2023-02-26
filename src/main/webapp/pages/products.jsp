<!DOCTYPE html>
<%@page contentType="gzip;charset=UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${pageContext.request.locale.language}"/>
<fmt:setBundle basename="words"/>
<html>
<c:import url="frames.jsp" />
<head>
<title>Cars List</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8"><title>W3.CSS Template</title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="styles/w3.css">
<link rel="stylesheet" href="styles/css.css">
<script src="../js/jquery/jquery_3.4.1.js"></script>
<script src="../js/pagination.js"></script>
<script src="../js/cart_list.js"></script>
<script src="../js/orders.js"></script>
<style>
body,h1,h2,h3,h4,h5,h6 {font-family: "Karma", sans-serif}
.w3-bar-block .w3-bar-item {padding:20px}
</style>
</head><body>
<!-- Sidebar (hidden by default) -->
<nav class="w3-sidebar w3-bar-block w3-card w3-top w3-xlarge w3-animate-left" style="display: none; z-index: 2; width: 25%; min-width: 300px;" id="mySidebar">
  <a href="javascript:void(0)" onclick="w3_close()" class="w3-bar-item w3-button">X</a>
  <tags:loginForm/>
  <a href="../index.jsp" onclick="w3_close()" class="w3-bar-item w3-button"><fmt:message key="main_page"/></a>
  <a href="#" onclick="w3_close()" class="w3-bar-item w3-button"><fmt:message key="auto_sale"/></a>
  <button onclick="openFrame();" class="w3-bar-item w3-button"><fmt:message key="cart_list"/></button>
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

<!-- Left-side filters -->
<form action="/getProducts" id="filters">
<input type="hidden" name="page_current" value="${requestScope['currentPage']}">
<div style="width: 19%;float:left;">
<c:import url="products_filters/left_filters.jsp"/>
<input type="submit" value=<fmt:message key="apply"/> style="margin-left:1%; margin-top:3%;" class="w3-button"/>
<a href="/getProducts" style="margin-left:1%; margin-top: 3%" class="w3-button"><fmt:message key="filters_reset"/></a>
</div>
</form>

<!-- Upper filters fields-->
<c:import url="products_filters/upper_filters.jsp"/>

<!-- !PAGE CONTENT! -->
<div class="w3-main w3-content w3-padding" style="float:left; width:80%;">

    <!-- Pagination -->
    <div class="w3-center" style="margin-top: 1%;">
      <div class="w3-bar">
            <tags:pagination currentPage="${requestScope['currentPage']}" totalPages="${requestScope['pagesCount']}"/>
      </div>
     </div>
      <div class="w3-row-padding w3-padding-16 w3-center" id="food">
      <c:choose>
      <c:when test="${not empty requestScope['productsList']}">
      <c:forEach items="${requestScope['productsList']}" var="product">
         <a href="#" class="w3-quarter" style="height: 5%; margin:1%;">
            <img src="../getCarPicture?picture=${product.image}" style="width:233px; height:175px; border-radius: 10px;">
            <h5><c:out value="${product.mark} ${product.model} ${product.productionYear}"/></h5>
            <p style="margin:1%;">Type: <fmt:message key="${product.category}"/></p>
            <p style="margin:1%;">Mileage: ${product.mileage}</p>
            <p style="margin:1%;">Fuel Type: <fmt:message key="${product.fuelType}"/></p>
            <b>$ - ${product.price}</b>
            <img src="../styles/tow.jpg" onclick="addProduct(${product.productId});" style="margin-left:3px;" height="30px" width="30px"/>
         </a>
      </c:forEach>
      </c:when>
      <c:when test="${empty requestScope['productsList']}">
         <p><fmt:message key="empty_products"/></p>
      </c:when>
      </c:choose>
      </div>

  <!-- Pagination -->
  <div class="w3-center w3-padding-32">
    <div class="w3-bar">
      <tags:pagination currentPage="${requestScope['currentPage']}" totalPages="${requestScope['pagesCount']}"/>
    </div>
  </div>
  
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
function w3_open() {
  document.getElementById("mySidebar").style.display = "block";
}
 
function w3_close() {
  document.getElementById("mySidebar").style.display = "none";
}
</script>


</body></html>