<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Computers database</title>
	<link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/stylesheets/bootstrap.min.css"/>">
	<link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/stylesheets/main.css"/>">
</head>
<body>

	<header class="topbar">
		<h1 class="fill">
			<a href="<c:url value="/computers"/>"> Play 2.0 sample application &mdash; Computer database </a>
		</h1>
	</header>

	<section id="main">
		<h1>${ numberOfComputers } computers found</h1>
		
		<div id="actions">
			<form action="/computers" method="GET">
				<input type="search" id="searchbox" name="f" value="" placeholder="Filter by computer name..."> 
				<input type="submit" id="searchsubmit" value="Filter by name" class="btn primary">
			</form>
			<a class="btn success" id="add" href="/computers/new">Add a new computer</a>
		</div>
		
		<table class="computers zebra-striped">
			<thead>
				<tr>
					<th class="col2 header headerSortUp"><a href="/computers?s=-2">Computer name</a></th>
					<th class="col3 header "><a href="/computers?s=3">Introduced</a></th>
					<th class="col4 header "><a href="/computers?s=4">Discontinued</a></th>
					<th class="col5 header "><a href="/computers?s=5">Company</a></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="computer" items="${ computers }">
					<tr>
						<td><a href="/computers/${ computer.id }">${computer.name }</a></td>
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
								<c:when test="${ computer.companyId == null }">
									<em>-</em>
								</c:when>
								<c:otherwise>
									${ computer.companyId }
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
						<li class="prev"><a href="<c:url value="/computers"/>?p=${ p - 1 }">&larr; Previous</a></li>
					</c:otherwise>
				</c:choose>
				<li class="current"><a>Displaying ${ firstComputerIndice } to ${ lastComputerIndice } of ${ numberOfComputers }</a></li>
				<c:choose>
					<c:when test="${ p == maxSheet }">
						<li class="next disabled"><a>Next &rarr;</a></li>
					</c:when>
					<c:otherwise>
						<li class="next"><a href="<c:url value="/computers"/>?p=${ p + 1 }">Next &rarr;</a></li>
					</c:otherwise>
				</c:choose>
			</ul>
		</div>
	</section>
</body>
</html>