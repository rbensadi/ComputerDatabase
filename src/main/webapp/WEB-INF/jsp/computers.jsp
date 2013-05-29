<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tag" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Computers database ${ f }</title>
	<c:import url="/WEB-INF/jsp/stylesheets.jsp"></c:import>
</head>
<body>

	<header class="topbar">
		<h1 class="fill">
			<a href="<c:url value="/computers"/>"> Play 2.0 sample application &mdash; Computer database </a>
		</h1>
	</header>
	
	<section id="main">
	
		<h1>${ title }</h1>
		
		<c:if test="${ message != null }">
			<div class="alert-message warning">
            	<strong>Done!</strong> ${ message }
        	</div>
		</c:if>
		
		<div id="actions">
			<form action="<c:url value="/computers"/>" method="GET">
				<input type="search" id="searchbox" name="f" value="${ f }" placeholder="Filter by computer name..."> 
				<input type="submit" id="searchsubmit" value="Filter by name" class="btn primary">
			</form>
			<a class="btn success" id="add" href="<c:url value="/computers/new"/>">Add a new computer</a>
		</div>
		
		<c:choose>
			<c:when test="${ numberOfComputers > 0 && p <= maxNumberOfPages }">
				<table class="computers zebra-striped">
					<thead>
						<tr>
							<tag:headerMenu columnName="Computer name" columnNumber="2" sortedColumnNumber="${ s }" p="${ p }" f="${ f }" />
							<tag:headerMenu columnName="Introduced" columnNumber="3" sortedColumnNumber="${ s }" p="${ p }" f="${ f }" />
							<tag:headerMenu columnName="Discontinued" columnNumber="4" sortedColumnNumber="${ s }" p="${ p }" f="${ f }" />
							<tag:headerMenu columnName="Company" columnNumber="5" sortedColumnNumber="${ s }" p="${ p }" f="${ f }" />
						</tr>
					</thead>
					<tbody>
						<c:forEach var="computer" items="${ computers }">
							<tr>
								<td><a href="<c:url value="/computers/edit?id=${ computer.id }"/>">${ computer.name }</a></td>
								<td>
									<c:choose>
										<c:when test="${ computer.introduced == null }">
											<em>-</em>
										</c:when>
										<c:otherwise>
											<fmt:formatDate value="${ computer.introduced }" pattern="dd MMM yyyy" />
										</c:otherwise>
									</c:choose>
								</td>
								<td>
									<c:choose>
										<c:when test="${ computer.discontinued == null }">
											<em>-</em>
										</c:when>
										<c:otherwise>
											<fmt:formatDate value="${ computer.discontinued }" pattern="dd MMM yyyy" />
										</c:otherwise>
									</c:choose>
								</td>
								<td>
									<c:choose>
										<c:when test="${ computer.company == null }">
											<em>-</em>
										</c:when>
										<c:otherwise>
											${ computer.company.name }
										</c:otherwise>
									</c:choose>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
		
				<div id="pagination" class="pagination">
					<ul>
						<c:choose>
							<c:when test="${ p == 1 }">
								<li class="prev disabled"><a>&larr; Previous</a></li>
							</c:when>
							<c:otherwise>
								<li class="prev"><a href="<c:url value="/computers"/>?p=${ p - 1 }&f=${ f }&s=${ s }">&larr; Previous</a></li>
							</c:otherwise>
						</c:choose>
						<li class="current"><a>Displaying ${ firstComputerIndice } to ${ lastComputerIndice } of ${ numberOfComputers }</a></li>
						<c:choose>
							<c:when test="${ p == maxNumberOfPages }">
								<li class="next disabled"><a>Next &rarr;</a></li>
							</c:when>
							<c:otherwise>
								<li class="next"><a href="<c:url value="/computers"/>?p=${ p + 1 }&f=${ f }&s=${ s }">Next &rarr;</a></li>
							</c:otherwise>
						</c:choose>
					</ul>
				</div>
		</c:when>
			<c:otherwise>
				<div class="well">
		            <em>Nothing to display</em>
		        </div>
			</c:otherwise>
		</c:choose>
	</section>
</body>
</html>