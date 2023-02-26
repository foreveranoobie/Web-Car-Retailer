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
  <script src="../js/jquery/jquery_3.4.1.js"></script>
  <script src="../js/cart_list.js"></script>
  <script src="../js/orders.js"></script>
  <style>
    body,h1,h2,h3,h4,h5,h6 {font-family: "Karma", sans-serif}
    .w3-bar-block .w3-bar-item {padding:20px}
    </style>
</head>
<body>
<div style="z-index:2; overflow-y:hidden; border:1px solid black; width:700px; height:420px; position:fixed; display:none; background-color:white; margin-left:22%;" name="cart">
    <span onclick="closeFrame();" style="float:right; margin-right: 4px;">X</span>
    <br/>
    <iframe name="cart_frame" style="width:-webkit-fill-available; height:-webkit-fill-available;">
    </iframe>
</div>
<div style="z-index:2; overflow-y:hidden; border:1px solid black; width:740px; height:420px; position:fixed; display:none; background-color:white; margin-left:22%;" name="orders">
    <span name="returnArrow" onclick="previousFrame();" style="float:left; margin-left: 4px; display:none;"><-</span>
    <span onclick="closeOrders();" style="float:right; margin-right: 4px;">X</span>
    <br/>
    <iframe name="orders_frame" style="width:-webkit-fill-available; height:-webkit-fill-available;">
    </iframe>
</div>
</body>
</html>