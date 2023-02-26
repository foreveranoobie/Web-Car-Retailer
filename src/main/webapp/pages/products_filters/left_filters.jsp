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

<!-- Checkboxes fields for mark -->
<div>
<p style="margin-bottom: 1%; margin-left:7%;"><fmt:message key="mark"/></p>
<nav style="overflow-y: auto; max-height: 150px; width: 70%; text-align:left;">
<c:forEach items="${requestScope['forCheckboxes']['mark']}" var="product">
<tags:checkBoxCreator field_name="checkbox_mark" field_value="${product}" translate="no"/>
</c:forEach>
<nav>
</div>

<!-- Checkboxes fields for fuel types -->
<div>
<p style="margin-bottom: 1%; margin-left:7%;"><fmt:message key="fuel_type"/></p>
<nav style="overflow-y: auto; max-height: 150px; width: 70%; text-align:left">
<c:forEach items="${requestScope['forCheckboxes']['fuelType']}" var="product">
<tags:checkBoxCreator field_name="checkbox_fuelType" field_value="${product}" translate="yes"/>
</c:forEach>
<nav>
</div>

<!-- Checkboxes fields for categories -->
<div>
<p style="margin-bottom: 1%; margin-left:7%;"><fmt:message key="category"/></p>
<nav style="overflow-y: auto; max-height: 150px; width: 70%; text-align:left">
<c:forEach items="${requestScope['forCheckboxes']['category']}" var="product">
<tags:checkBoxCreator field_name="checkbox_category" field_value="${product}" translate="yes"/>
</c:forEach>
<nav>
</div>

<c:set var="ranged" value="${requestScope['rangedValues']}"/>

<!-- Ranged price -->
<div style="margin-top:7%;">
<p style="margin-bottom: 1%;"><fmt:message key="price"/></p>
<input type="number" name="rangeMin_price" value="${ranged['rangeMin_price']}" style="width:30%;">
<input type="number" name="rangeMax_price" value="${ranged['rangeMax_price']}" style="width:30%;">
</div>

<!-- Ranged year -->
<div style="margin-top:7%;">
<p style="margin-bottom: 1%;"><fmt:message key="prod_year"/></p>
<input type="number" name="rangeMin_year" value="${ranged['rangeMin_year']}" style="width:30%;">
<input type="number" name="rangeMax_year" value="${ranged['rangeMax_year']}" style="width:30%;">
</div>

<!-- Ranged mileage -->
<div style="margin-top:7%;">
<p style="margin-bottom: 1%;"><fmt:message key="mileage"/></p>
<input type="number" name="rangeMin_mileage" value="${ranged['rangeMin_mileage']}" style="width:30%;">
<input type="number" name="rangeMax_mileage" value="${ranged['rangeMax_mileage']}" style="width:30%;">
</div>
</body>
</html>