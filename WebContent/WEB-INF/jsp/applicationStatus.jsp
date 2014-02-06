<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Application Status</title>
<style type="text/css">
    	body { background-color: #eee; font: helvetica; }
    	.green { font-weight: bold; color: green; }
    	.message { margin-bottom: 10px; }
    	label { display:inline-block;}
    	input { display:inline-block; margin-right: 10px; }
    	form {line-height: 160%; }
    	.hide { display: none; }
    	.error { color: red; font-size: 0.9em; font-weight: bold; }
    	td {width:50%; align:left;}
    	table{width:500px;}
    </style>
</head>
<body>
<c:if test="${not empty message}"><div class="message green">${message}</div></c:if>
<c:if test="${not empty errorMessage}"><div class="message error">${errorMessage}</div></c:if>
	<form:form action="applicationStatus.html" method="get">
		<table>
		<tr>
			<td>Application Id: </td>
			<td><input type="text" name="id" id="id" width="24"/></td>
		</tr>
		<tr>
		        <td colspan="3">
		            <input type="submit" value="Search"/>
		        </td>
		    </tr>
		
		</table>
	</form:form>
	
	<c:if test="${!empty application}">
	<table>
		<tr>
			<td>Name: </td>
			<td>${application.name}</td>
		</tr>
		<tr>
			<td>Job Id: </td>
			<td>${application.jobId}</td>
		</tr>
		<tr>
			<td>Justification: </td>
			<td>${application.justification}</td>
		</tr>
		<tr>
			<td>Link of the code: </td>
			<td>${application.code}</td>
		</tr>
		<tr>
			<td>Additional Links</td>
			<td><c:if  test="${!empty application.additionalLinks}">
				<c:forEach items="${application.additionalLinks}" var="links">
				<c:if  test="${!empty links}">
					${links}
				</c:if>
				</c:forEach>			
				</c:if>			
			</td>
		</tr>
	</table>
	</c:if>

</body>
</html>