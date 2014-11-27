<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf8">
	<link href="<c:url value="/resources/css/screen.css" />" rel="stylesheet">
	<title><spring:message code="label.title" /></title>
</head>

<body>
<spring:message code="label.greet" /> <%= request.getUserPrincipal().getName() %>
<a href="<c:url value="/logout" />"> <spring:message code="label.logout" /> </a>

<h2><spring:message code="label.title" /></h2>

<sec:authorize access="hasRole('ROLE_ADMIN')">
<p><spring:message code="label.greet.admin"/></p>
</sec:authorize>
<sec:authorize access="!hasRole('ROLE_ADMIN')">
<p><spring:message code="label.greet.user"/></p>
</sec:authorize>

<sec:authorize access="hasRole('ROLE_ADMIN')">
<form:form method="post" action="add" commandName="user">

	<table>
		<tr>
			<td><form:label path="email">
				<spring:message code="label.email" />
			</form:label></td>
			<td><form:input path="email" /></td>
		</tr>		
		<tr>
			<td><form:label path="password">
				<spring:message code="label.password" />
			</form:label></td>
			<td><form:input path="password" /></td>
		</tr>
		<tr>
			<td colspan="2"><input type="submit" value="<spring:message code="label.adduser"/>" /></td>
		</tr>
	</table>
</form:form>
</sec:authorize>
	
<h3><spring:message code="label.users" /></h3>
<c:if test="${!empty userList}">
	<table class="data">
		<tr>
			<th><spring:message code="label.id" /></th>
			<th><spring:message code="label.email" /></th>
			<sec:authorize access="hasRole('ROLE_ADMIN')">
			<th><spring:message code="label.actions" /></th>
			</sec:authorize>
			<th>&nbsp;</th>
		</tr>
		
		<c:forEach items="${userList}" var="user">		  
			<tr>
				<td>${user.id}</td>
				<td>${user.email}</td>		
				<sec:authorize access="hasRole('ROLE_ADMIN')">
				<td>
				<a href="javascript:window.open('edit.jsp?userId=${user.id}&email=${user.email}','NewPassword',
					'height=200,width=350,resizable=no,scrollbars=no').focus() ;"><spring:message code="label.modify" /></a>
				</td>
				<td><a href="delete/${user.id}"><spring:message code="label.delete" /></a></td>
				</sec:authorize>
			</tr>
		</c:forEach>		
	</table>
</c:if>


</body>
</html>