<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tag" %>
<!DOCTYPE html>
<html>
<head>
	<title>Computers database</title>
	<c:import url="/WEB-INF/jsp/stylesheets.jsp"></c:import>
</head>
<body>

	<header class="topbar">
		<h1 class="fill">
			<a href="<c:url value="/computers"/>"> Play 2.0 sample application &mdash; Computer database </a>
		</h1>
	</header>
	
	<section id="main">
		<h1>${ isServletAdd ? 'Add' : 'Edit' } a computer</h1>
		<form action="<c:url value="/computers/${ isServletAdd ? 'new' : 'edit?id='.concat(computer.id) }"/>" method="POST">
			<fieldset>
			
				<div class="clearfix ${ empty form.errors || form.errors['name'] ? '' : 'error'}">
					<label for="name">Computer name</label>
					<div class="input">
						<input type="text" id="name" name="name" value="${ form.fields['name'] }"> <span class="help-inline">Required</span>
					</div>
				</div> 
				
				<div class="clearfix ${ empty form.errors || form.errors['introduced'] ? '' : 'error'}">
					<label for="introduced">Introduced date</label>
					<div class="input">
						<input type="text" id="introduced" name="introduced" value="${ form.fields['introduced'] }">
						<span class="help-inline">Date (&#x27;yyyy-MM-dd&#x27;)</span>
					</div>
				</div>
				
				<div class="clearfix ${ empty form.errors || form.errors['discontinued'] ? '' : 'error'}">
					<label for="discontinued">Discontinued date</label>
					<div class="input">
						<input type="text" id="discontinued" name="discontinued" value="${ form.fields['discontinued'] }">
						<span class="help-inline">Date (&#x27;yyyy-MM-dd&#x27;)</span>
					</div>
				</div>
				
				<div class="clearfix">
					<label for="company">Company</label>
					<div class="input">
						<select id="company" name="company">
							<option class="blank" value="">-- Choose a company --</option>
							<c:forEach var="company" items="${ companies }">
								<option value="${ company.id }" ${ form.fields['company'] == company.id ? 'selected' : '' }>${ company.name }</option>
							</c:forEach>
						</select>
						
						<span class="help-inline"></span>
					</div>
				</div>
				
			</fieldset>

			<div class="actions">
				<input type="submit" value="${ isServletAdd ? 'Create' : 'Save' } this computer" class="btn primary"> or <a href="<c:url value="/computers"/>" class="btn">Cancel</a>
			</div>
		</form>
		
		 <c:if test="${ !isServletAdd }">
			<form action="<c:url value="/computers/delete?id="${ computer.id }/>" method="POST" class="topRight">
	        	<input type="submit" value="Delete this computer" class="btn danger">
			</form>
		</c:if> 
	</section>
</html>