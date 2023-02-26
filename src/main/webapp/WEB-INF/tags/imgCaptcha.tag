<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@attribute name="field" required="true"%>
<c:choose>
         <c:when test = "${initParam['captchaType'] eq 'Hidden fields'}">
            <input type="hidden" name="rightCaptcha" value="${requestScope.hashedNumber}"/>
            <img src="/captcha?rightCaptcha=${requestScope.hashedNumber}" style="width: 250px; height: 60px; margin-top:1%; margin-bottom:1%;"/>
         </c:when>

         <c:when test = "${initParam['captchaType'] ne 'Hidden fields'}">
            <img src="/captcha" style="width: 250px; height: 60px"/>
         </c:when>
</c:choose>