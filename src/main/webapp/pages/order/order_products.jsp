<!DOCTYPE HTML>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<html>

<head>
  <title>Login Page</title>
  <meta http-equiv="content-type" content="text/html; charset=UTF-8"><title>W3.CSS Template</title>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="../styles/w3.css">
  <link rel="stylesheet" href="../styles/css.css">
  <link rel="stylesheet" href="../styles/font-awesome.css">
  <script src="../../js/jquery/jquery_3.4.1.js"></script>
  <script src="../../js/orders.js"></script>
  <style>
    body,h1,h2,h3,h4,h5,h6 {font-family: "Karma", sans-serif}
    .w3-bar-block .w3-bar-item {padding:20px}
    </style>
</head>
<body>
<h1 style:"text-align:center;">Order: ${requestScope['order_id']}</h1>
<table style="text-align:center;">
<tr>
<th>Mark</th>
<th>Model</th>
<th>Year</th>
<th>Mileage</th>
<th>Fuel Type</th>
<th>Category</th>
<th>Price</th>
</tr>
<c:forEach items="${requestScope['products_list']}" var="product">
<tr>
<td>${product.mark}</td>
<td>${product.model}</td>
<td>${product.productionYear}</td>
<td>${product.mileage}</td>
<td>${product.fuelType}</td>
<td>${product.category}</td>
<td>${product.price}</td>
</tr>
</c:forEach>
</table>
${requestScope['totalPrice']}
</body>
</html>