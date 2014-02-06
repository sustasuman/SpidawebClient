<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<style type="text/css">
    	body { background-color: #eee; font: helvetica; }
    	.green { font-weight: bold; color: green; }
    	.message { margin-bottom: 10px; }
    	label { display:inline-block;}
    	input { display:inline-block; margin-right: 10px; }
    	form {line-height: 160%; }
    	.hide { display: none; }
    	.error { color: red; font-size: 0.9em; font-weight: bold; }
    </style>

<title>Insert title here</title>
</head>
<body>
	<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
	<h2><b>Fill the details below to apply for the selected job</b>  </h2>
	<c:if test="${not empty message}"><div class="message green">Application submitted Successfully. Please note this reference to check the status of your application: ${message}</div>
	<p class ="message"> Check the status of this application <a href="${contextPath}/applicationStatus.html?id=${message}">here.</a></p>  
	</c:if>
	<form:form method="post" action="application.html" commandName="application">
	 	<form:input id="jobId" path= "jobId" value = "<%= request.getParameter(\"id\") %>" readonly = "true" style ="display:none;"/>
	 	<form:errors path="jobId" cssclass="error"></form:errors>
	    <table>
	    <tr>
	        <td><form:label path="name">Your Name: </form:label></td>	    
	        <td><form:input path="name" /></td>
	        <td><form:errors path="name"  cssClass="error"></form:errors></td>
	    </tr>
	    <tr>
	        <td colspan = "3"><form:label path="justification">Why we should hire you, we are not looking for your resume, tell us your story and why that makes you good to talk to. </form:label></td>
	      </tr>
	    <tr>
	        <td colspan = "2"><form:textarea path="justification" style ="width:100%" rows = "5" /></td>
	        <td><form:errors path="justification" cssClass="error"></form:errors></td>
	    </tr>
	    <tr>
	        <td colspan = "3" >
	        	<form:label path="code">a link to the code you used to submit this application (ie github, google code etc)</form:label>
	        </td>
	    </tr>
	    <tr>  
	        <td colspan = "2" ><form:input path="code" style ="width:100%"/></td>
	        <td><form:errors path="code" cssClass="error"></form:errors></td>
	    </tr>
	    <tr>
	        <td colspan = "3" ><form:label path="additionalLinks">Additional links you think we should see.</form:label></td>
	     </tr>
<!-- 	    <tr>        -->
<%-- 	        <td colspan = "3" ><form:textarea path="additionalLinks" rows = "3" style ="width:75%"/></td> --%>
<!-- 	    </tr> -->

	    	
		 <c:forEach varStatus="counter" begin="0" end ="4">
		    <tr><td colspan = "3" >
				<form:input path="additionalLinks[${counter.index}]" style ="width:100%"/>
			</td>
			</tr>
		</c:forEach>
			

	    <tr>
	        <td colspan="3">
	            <input type="submit" value="Apply"/>
	        </td>
	    </tr>
	</table>  
	</form:form>

</body>
</html>