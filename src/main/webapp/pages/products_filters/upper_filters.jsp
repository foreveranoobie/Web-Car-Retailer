<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${pageContext.request.locale.language}"/>
<fmt:setBundle basename="words"/>
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
<div style="margin: 0 auto;width: 65%;margin-top: 6%;">

<!-- Field to select order parameter -->
<span><fmt:message key="order_by"/>: </span>
<select name="sort_order" form="filters">
<option value="price" ${requestScope['selectedOrder'] == 'price' ? 'selected' : ''}><fmt:message key="price"/></option>
<option value="year" ${requestScope['selectedOrder'] == 'year' ? 'selected' : ''}><fmt:message key="prod_year"/></option>
<option value="mark" ${requestScope['selectedOrder'] == 'mark' ? 'selected' : ''}><fmt:message key="mark"/></option>
<option value="fuelType" ${requestScope['selectedOrder'] == 'fuelType' ? 'selected' : ''}><fmt:message key="fuel_type"/></option>
</select>

<!-- Field to select limit of products per page -->
<span style="margin-left:2%;">Products per page: </span>
<select name="limit_productsCount" form="filters">
<option value="6" ${requestScope['selectedLimit'] == '6' ? 'selected' : ''}>6</option>
<option value="12" ${requestScope['selectedLimit'] == '12' ? 'selected' : ''}>12</option>
<option value="18" ${requestScope['selectedLimit'] == '18' ? 'selected' : ''}>18</option>
</select>
</div>
</body>
</html>