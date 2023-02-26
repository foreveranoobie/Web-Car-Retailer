<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@attribute name="currentPage" required="true"%>
<%@attribute name="totalPages" required="true"%>

<c:set var="range" value="${totalPages - (currentPage + 1)}"/>
<c:if test="${range > 1 || range < -1}">
<c:set var="range" value="1"/>
</c:if>
<c:if test="${(currentPage ne 0) && (currentPage ne totalPages - 1)}">
<button onclick="getPage(0);" class="w3-bar-item w3-button w3-hover-black"><<</button>
<c:forEach var = "i" begin = "${currentPage - 1}" end = "${currentPage + range}">
         <c:if test="${i eq currentPage}">
           <button onclick="getPage(${i});" class="w3-bar-item w3-black w3-button">${i+1}</button>
         </c:if>
         <c:if test="${i ne currentPage}">
            <button onclick="getPage(${i});" class="w3-bar-item w3-button">${i+1}</button>
         </c:if>
</c:forEach>
<button onclick="getPage(${totalPages-1});" class="w3-bar-item w3-button w3-hover-black">>></button>
</c:if>
<c:if test="${(currentPage eq 0) && (currentPage ne totalPages-1)}">
<c:set var="range" value="${totalPages > 2 ? 2 : totalPages - 1}"/>
<c:forEach var = "i" begin = "${currentPage}" end = "${currentPage + range}">
         <c:if test="${i eq currentPage}">
           <button onclick="getPage(${i});" class="w3-bar-item w3-black w3-button">${i+1}</button>
         </c:if>
         <c:if test="${i ne currentPage}">
            <button onclick="getPage(${i});" class="w3-bar-item w3-button">${i+1}</button>
         </c:if>
</c:forEach>
<button onclick="getPage(${totalPages-1});" class="w3-bar-item w3-button w3-hover-black">>></button>
</c:if>
<c:if test="${(currentPage eq totalPages-1) && (currentPage ne 0)}">
<c:set var="range" value="${totalPages > 2 ? 2 : totalPages - 1}"/>
<button onclick="getPage(0);" class="w3-bar-item w3-button w3-hover-black"><<</button>
<c:forEach var = "i" begin = "${currentPage - range}" end = "${currentPage}">
         <c:if test="${i eq currentPage}">
           <button onclick="getPage(${i});" class="w3-bar-item w3-black w3-button">${i+1}</button>
         </c:if>
         <c:if test="${i ne currentPage}">
            <button onclick="getPage(${i});" class="w3-bar-item w3-button">${i+1}</button>
         </c:if>
</c:forEach>
</c:if>

<c:if test="${currentPage eq totalPages-1 && currentPage eq 0}">
           <button onclick="getPage(0);" class="w3-bar-item w3-black w3-button">1</button>
</c:if>