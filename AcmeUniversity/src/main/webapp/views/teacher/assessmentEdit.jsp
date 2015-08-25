<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="teacher/assess.do" modelAttribute="assessmentForm" method="post">
	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<form:hidden path="teacher"/>
	
	<acme:textbox code="teacher.assessment.rating" path="rating" />
	<acme:textbox code="teacher.assessment.text" path="text" />
	
	<acme:submit name="save" code="save" />
	<acme:cancel code="cancel" url="${previousURI}"/>

</form:form>