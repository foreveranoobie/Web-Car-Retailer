<!DOCTYPE HTML>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${pageContext.request.locale.language}"/>
<fmt:setBundle basename="words"/>
<html>

<head>
  <title>Login Page</title>
  <meta http-equiv="content-type" content="text/html; charset=UTF-8"><title>W3.CSS Template</title>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="../../styles/w3.css">
  <link rel="stylesheet" href="../../styles/css.css">
  <link rel="stylesheet" href="../../styles/font-awesome.css">
  <script src="../../js/jquery/jquery_3.4.1.js"></script>
  <script src="../../js/cart_list.js"></script>
  <style>
    body,h1,h2,h3,h4,h5,h6 {font-family: "Karma", sans-serif}
    .w3-bar-block .w3-bar-item {padding:20px}
    </style>
</head>
<c:set var="cartList" value="${cart}"/>
<body>
<c:if test="${not empty cartList.cartProducts}">
   <div style="z-index:2; overflow-y:hidden; border:1px solid black; width:340px; height:340px; position:fixed; display:none; background-color:white; margin-left:25%;" name="cartOrder">
   <span onclick="closeOrder();" style="float:right; margin-right: 4px;">X</span>
    <br/>
    <iframe name="makeOrder_frame" style="width:-webkit-fill-available; height:-webkit-fill-available;">
    </iframe>
   </div>
   <table style="width:100%; text-align:center;">
   <tr>
   <th><fmt:message key="mark"/></th>
   <th><fmt:message key="model"/></th>
   <th><fmt:message key="prod_year"/></th>
   <th><fmt:message key="price"/></th>
   <th><fmt:message key="amount"/></th>
   <tr>
   <c:forEach items="${cartList.cartProducts}" var="item">
   <tr>
      <td>${item.key.mark}</td>
      <td>${item.key.model}</td>
      <td>${item.key.productionYear}</td>
      <td>${item.key.price}</td>
      <td><span onclick="decrementAmount(${item.key.productId}, ${item.key.price})" style="margin-right:4px; font-size:25px;">-</span><span id="span_${item.key.productId}">${item.value}</span><span onclick="incrementAmount(${item.key.productId}, ${item.key.price})" style="margin-left:4px; font-size:25px;">+</span></td>
      <td><span style="font-size:23px;" onclick="removeProduct(${item.key.productId})">X</span></td>
   </tr>
   </c:forEach>
   </table>
   <fmt:message key="sum_prod_amount"/>: <span id="amountSum">${cartList.summaryProductsAmount}</span>
   <br/>
   <fmt:message key="sum_price"/>: <span id="priceSum">${cartList.summaryPrice}</span>
   <br/>
   <button onclick="clearCart();" style="margin-left: 4px;"><fmt:message key="clear_cart"/></button>
   <br/>
   <button onclick="getOrderCart();" style="margin-left: 4px; margin-top:1%;"><fmt:message key="make_order"/></button>
   <input type="hidden" id="login" value="${userState}"/>
</c:if>
<c:if test="${empty cartList.cartProducts}">
<p style="text-align: center; margin-top:10%; font-size: 25px;"><fmt:message key="empty_cart"/></p>
</c:if>
</body>
</html>