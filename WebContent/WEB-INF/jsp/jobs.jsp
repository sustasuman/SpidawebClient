<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>List And Apply Job at Spidaweb</title>
	<style type="text/css">
    	body { background-color: #eee; font: helvetica; }
    	.green { font-weight: bold; color: green; }
    	.message { margin-bottom: 10px; }
    	label { display:inline-block;}
    	input { display:inline-block; margin-right: 10px; }
    	form {line-height: 160%; }
    	.hide { display: none; }
    	.error { color: red; font-size: 0.9em; font-weight: bold; }
    	table {width:750px;}
    </style>
</head>
<body>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:if test="${not empty message}"><div class="message error">${message}</div></c:if>
<c:if  test="${!empty jobList}">
	<h3>Select a job from the list below.</h3>
	<table style = "width:solid; background: #aaaaaa; padding: 5px;">
		<tr style="padding: 5px; " >
		    <th>Position</th>
		    <th>Description</th>
		    <th>Job ID</th>
		    <th>&nbsp;</th>
		</tr>
		<c:forEach items="${jobList}" var="job">
		    <tr>
		        <td>${job.position}</td>
		        <td>${job.description}</td>
		        <td>${job._id}</td>
		        <td><a href="${contextPath}/applicationSelect.html?id=${job._id}">Select</a></td>
		    </tr>
		</c:forEach>
	</table>
	
	<br />
</c:if>

<p> Check the status of applied jobs <a href="${contextPath}/applicationStatus.html?id=">here.</a></p>    

</body>
</html>