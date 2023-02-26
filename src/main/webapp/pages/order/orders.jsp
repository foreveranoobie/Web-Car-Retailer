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
  <link rel="stylesheet" href="../styles/w3.css">
  <link rel="stylesheet" href="../styles/css.css">
  <link rel="stylesheet" href="../styles/font-awesome.css">
  <script src="../js/jquery/jquery_3.4.1.js"></script>
  <script src="../js/orders.js"></script>
  <style>
    body,h1,h2,h3,h4,h5,h6 {font-family: "Karma", sans-serif}
    .w3-bar-block .w3-bar-item {padding:20px}
  </style>
</head>
<body>
<table style="text-align:center; width:100%;">
<tr>
<th style="margin-right:4%;"><fmt:message key="order_number"/></th>
<th style="margin-right:4%;"><fmt:message key="order_status"/></th>
<th style="margin-right:4%;"><fmt:message key="order_desc"/></th>
<th style="margin-right:4%;"><fmt:message key="order_date"/></th>
<th><fmt:message key="sum_price"/></th>
</tr>
<c:forEach items="${requestScope['orders_list']}" var="order">
<tr onclick="getDetails(${order.id});">
<td style="margin-right:4%;">${order.id}</td>
<td style="margin-right:4%;"><fmt:message key="${order.status}"/></td>
<td style="margin-right:4%;">${order.statusDescription}</td>
<td style="margin-right:4%;">${order.creationDate}</td>
<td>${order.totalPrice}</td>
</tr>

<div id="order${order.id}" style="margin-top:5%; margin-left: 10%; z-index:2; width: 80%; background-color:white; border:1px solid black; position:absolute; display:none;">
<span onclick="closeDetails(${order.id});" style="padding-left:5px; font-size: 23px; border-bottom: 1px solid black; border-right: 1px solid black;">
X
</span>
<span style="font-size:22px; padding-left:5px;">Order ID: ${order.id} / From: ${order.creationDate}</span>
<nav style="overflow-y: auto; max-height: 200px; margin-top:1%;">
<div style="display:table; width: 100%">

<div style="display:table-row; font-weight: bold;">
<div style="display:table-cell"><fmt:message key="mark"/></div>
<div style="display:table-cell"><fmt:message key="model"/></div>
<div style="display:table-cell"><fmt:message key="prod_year"/></div>
<div style="display:table-cell"><fmt:message key="category"/></div>
<div style="display:table-cell"><fmt:message key="fuel_type"/></div>
<div style="display:table-cell"><fmt:message key="price"/></div>
<div style="display:table-cell"><fmt:message key="amount"/></div>
</div>

<c:forEach items="${order.orderedProductList}" var="product">
<div style="display:table-row">
<div style="display:table-cell">${product.product.mark}</div>
<div style="display:table-cell">${product.product.model}</div>
<div style="display:table-cell">${product.product.productionYear}</div>
<div style="display:table-cell"><fmt:message key="${product.product.category}"/></div>
<div style="display:table-cell"><fmt:message key="${product.product.fuelType}"/></div>
<div style="display:table-cell">${product.product.price}</div>
<div style="display:table-cell">${product.amount}</div>
</div>
</c:forEach>
</div>
</nav>
</div>

</c:forEach>
</table>
</body>
</html>