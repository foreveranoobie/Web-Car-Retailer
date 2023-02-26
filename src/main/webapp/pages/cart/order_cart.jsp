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
<body>
<form name="applyOrder" method="post" onsubmit="return submitOrder();" style="text-align:center;" action="/applyOrder"/>
<fmt:message key="receiver_initials"/><br/><input type="text" name="receiverName"/><br/>
<p id="name_error" style="display:none; color:red;">Wrong name value<br/></p>
<fmt:message key="payment"/><br/>
<select name="payment" onchange="getPayment();">
<option value="Cash payment"><fmt:message key="cash_payment"/></option>
<option value="Bank card"><fmt:message key="bank_card"/></option>
</select>
<br/>
<span id="card_info" style="display:none;"><fmt:message key="card_number"/><br/><input type="number" name="cardNumber"/><br/>
<p id="card_info_error" style="display:none; font-color:red;">Wrong card number<br/></p>
CVV<br/><input type="number" name="cvv"/><br/>
<p id="cvv_error" style="display:none; font-color:red;">Wrong CVV code</p>
<fmt:message key="exp_date"/><br/><input type="number" name="month" style="margin-right:2px;width:10%;"/><input type="number" name="year" style="width:20%;"/><br/>
<p id="date_error" style="display:none; font-color:red;">Wrong expiration date</p>
</span>
<input type="submit" value=<fmt:message key="apply_order"/>/>
</form>
</body>
</html>