<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
 <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
 <link href="<c:url value="/resources/css/screen.css" />" rel="stylesheet">
 <title><spring:message code="label.edit" /></title>
</head>
<body>

<spring:message code="label.modify.user.info"/> <%=request.getParameter("email") %>
 <form onsubmit="submitPopup();">
  <fieldset>
   <table>
   <tr>
    <th>
     <label for="password"><spring:message code="label.new.password"/></label>
    </th>
    <td>
     <input id="password" name="newpassword" type="password" />     
    </td>
   </tr>
   <tr>
    <th></th>
    <td>
     <input name="commit" type="button" value="<spring:message code="label.modify"/>" onclick="submitPopup();"/>
    </td>
   </tr>
    </table>
  </fieldset>
 </form>

 <script type="text/javascript" language="Javascript1.5">
  function submitPopup()  {
   var newPassword = document.getElementById('password').value;
   if (newPassword.length > 0) {
    var theUrl = '<%= request.getContextPath() %>/modify/<%=request.getParameter("userId")%>/' + newPassword;   
    var xmlHttp = null;
       xmlHttp = new XMLHttpRequest();
       xmlHttp.open( "GET", theUrl, false );
       xmlHttp.send( null ); 
       window.opener=self;
       window.close();
   } else {
    alert("Empty password field");
   }
  }  
 </script>
</body>
</html>