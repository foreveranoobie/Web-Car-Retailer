<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<script src="../../js/jquery/jquery_3.4.1.js"></script>
<script src="../../js/localizer.js"></script>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${pageContext.request.locale.language}"/>
<fmt:setBundle basename="words"/>

<div style="position:absolute; margin-left:75%; margin-top:1%;">
<fmt:message key="curr_language"/>
<select name="lang" onchange="setLanguage()">
<c:forEach var="lang" items="${locales}">
<c:if test="${lang eq pageContext.request.locale.language}">
 <option value="${lang}" selected="selected">${fn:toUpperCase(lang)}</option>
</c:if>
<c:if test="${lang ne pageContext.request.locale.language}">
 <option value="${lang}">${fn:toUpperCase(lang)}</option>
</c:if>
</c:forEach>
</select>
</div>