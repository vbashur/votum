<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
	<link href="<c:url value="/resources/css/screen.css" />" rel="stylesheet">
	<title><spring:message code="label.title" /></title>
</head>
<body>

<div>
	<h2><spring:message code="label.title"/></h2>
	
	<spring:url var ="authUrl" value="/static/j_spring_security_check" />
	<form method="post" class="signin" action="${authUrl}">
		<fieldset>
			<table>
			<tr>
				<th>
					<label for="Email"><spring:message code="label.email"/></label>
				</th>
				<td>
					<input id ="Email" name="j_username" type="text" />
				</td>
			</tr>
			<tr>
				<th>
					<label for="password"><spring:message code="label.password"/></label>
				</th>
				<td>
					<input id="password" name="j_password" type="password" />
					<small><a href="/account/resend_password"><spring:message code="label.forgot"/></a></small>
				</td>
			</tr>
			<tr>
				<th></th>
				<td><input id="remember_me" name="_spring_security_remember_me" type="checkbox"/>
					<label for="remember_me" class="inline"><spring:message code="label.remember"/></label>
				</td>
			</tr>
			<tr>
				<th></th>
				<td>
					<input name="commit"type="submit"value="<spring:message code="label.login"/>"/>
				</td>
			</tr>
		</table>
	</fieldset>
</form>
<script type="text/javascript">
	document.getElementById('Email').focus();
</script>
</div>
</body>
</html>