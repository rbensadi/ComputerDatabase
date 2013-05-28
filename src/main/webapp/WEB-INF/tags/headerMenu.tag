<%@ tag language="java" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ attribute name="columnNumber" required="true" type="java.lang.Integer"%>
<%@ attribute name="sortedColumnNumber" required="true" type="java.lang.Integer"%>
<%@ attribute name="columnName" required="true" type="java.lang.String" %>
<%@ attribute name="p" required="true" type="java.lang.Integer" %>
<%@ attribute name="f" required="true" type="java.lang.String" %>

<c:choose>
	<c:when test="${ columnNumber == sortedColumnNumber }">
		<th class="col${ columnNumber } header headerSortUp">
			<a href="<c:url value="/computers?p=${ p }&f=${ f }&s=${ -sortedColumnNumber }"/>">${ columnName }</a>
		</th>
	</c:when>
	<c:when test="${ columnNumber == -sortedColumnNumber }">
		<th class="col${ columnNumber } header headerSortDown">
			<a href="<c:url value="/computers?p=${ p }&f=${ f }&s=${ -sortedColumnNumber }"/>">${ columnName }</a>
		</th>
	</c:when>
	<c:otherwise>
		<th class="col${ columnNumber } header">
			<a href="<c:url value="/computers?p=${ p }&f=${ f }&s=${ columnNumber }"/>">${ columnName }</a>
		</th>
	</c:otherwise>
</c:choose>