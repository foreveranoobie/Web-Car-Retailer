<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${pageContext.request.locale.language}"/>
<fmt:setBundle basename="words"/>

<c:choose>
         <c:when test = "${not empty sessionScope['userState']}">
         <div style="float:left; width: 100%; font-size: 15px; margin-left: 5%;">
         <hr/>
         <form name="logout" action="../logout">
            <p><fmt:message key="logged_as"/> ${sessionScope['userState']}</p>
            <img src="../getAvatar" width="85" height="85"/>
            <input type="submit" value=<fmt:message key="logout"/>></input>
         </form>
         <hr/>
         </div>
         </c:when>

         <c:when test = "${empty sessionScope['userState']}">
         <div style="float:left; width: 100%; font-size:15px; margin-left: 5%;">
            <hr/>
            <form name="login" action="../loginUser">
            <p style="margin-bottom:1%"><fmt:message key="not_authorized"/></p>
            <input type="submit" value=<fmt:message key="login"/> style="width: 40%; border-radius: 8px;"/>
            </form>
            <form name="registration" action="../registerUser">
            <input type="submit" value=<fmt:message key="register"/> style="width: 50%; margin-top:3%; border-radius: 8px;"/>
            </form>
            <hr/>
         </div>
         </c:when>
</c:choose>