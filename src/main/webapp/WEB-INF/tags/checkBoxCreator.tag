<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${pageContext.request.locale.language}"/>
<fmt:setBundle basename="words"/>

<%@attribute name="field_name" required="true" rtexprvalue="true"%>
<%@attribute name="field_value" required="true" rtexprvalue="true"%>
<%@attribute name="translate" required="true" rtexprvalue="false"%>

<c:set var="contains" value="false" />
<c:if test="${translate eq yes}">
<c:forEach var="checked" items="${checkedValues}">
  <c:if test="${field_value eq checked}">
     <input type="checkbox" name="${field_name}" value="${field_value}" style="margin-left:10%;"s checked><fmt:message key="${field_value}"/></input><br/>
     <c:set var="contains" value="true" />
  </c:if>
</c:forEach>
   <c:if test="${contains eq false}">
     <input type="checkbox" name="${field_name}" "value=${field_value}" style="margin-left:10%;"><fmt:message key="${field_value}"/></input><br/>
</c:if>
</c:if>
<c:if test="${translate ne yes}">
<c:forEach var="checked" items="${checkedValues}">
  <c:if test="${field_value eq checked}">
     <input type="checkbox" name="${field_name}" value="${field_value}" style="margin-left:10%;" checked>${field_value}</input><br/>
     <c:set var="contains" value="true" />
  </c:if>
</c:forEach>
   <c:if test="${contains eq false}">
     <input type="checkbox" name="${field_name}" value="${field_value}" style="margin-left:10%;">${field_value}</input><br/>
</c:if>
</c:if>