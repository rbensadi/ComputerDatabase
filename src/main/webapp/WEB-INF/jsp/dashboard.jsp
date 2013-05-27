<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
			<a href="/"> Play 2.0 sample application &mdash; Computer database </a>
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
						<td><em>${ computer.introduced == null ? "-" : computer.introduced }</em></td>
						<td><em>${ computer.discontinued == null ? "-" : computer.discontinued }</em></td>
						<td><em>${ computer.companyId == null ? "-" : computer.companyId }</em></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>

		<div id="pagination" class="pagination">
			<ul>
				<li class="prev disabled"><a>&larr; Previous</a></li>
				<li class="current"><a>Displaying ${ firstComputerIndice } to ${ lastComputerIndice } of ${ numberOfComputers }</a></li>
				<li class="next"><a href="/computers?p=1">Next &rarr;</a></li>
			</ul>
		</div>
	</section>
</body>
</html>