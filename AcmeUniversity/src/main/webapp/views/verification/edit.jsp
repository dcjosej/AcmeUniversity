<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="verification/edit.do" modelAttribute="verification" method="post">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="lecturer" />
	<form:hidden path="lectureNote" />
	
	<div>
		<form:label path="correct">
			<spring:message code="verification.approved" />
		</form:label>
		<form:radiobutton path="correct" value="true" /><spring:message code="verification.yes" />
		<form:radiobutton path="correct" value="false" /><spring:message code="verification.no" />
		<form:errors path="correct" cssClass="error" />
	</div>

	<acme:textarea code="verification.description" path="description" />
	
	<acme:submit name="save" code="save" />
	<acme:cancel url="${previousURI}" code="cancel" />
		
</form:form>