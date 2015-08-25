<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<div>
	<form:label path="verification.correct">
		<spring:message code="verification.approved" />
	</form:label>
	<form:radiobutton path="verification.correct" value="true" disabled="true" />
	<spring:message code="verification.yes" />
	<form:radiobutton path="verification.correct" value="false" disabled="true" />
	<spring:message code="verification.no" />
	<form:errors path="verification.correct" cssClass="error" />
</div>

<acme:textarea code="verification.description" path="verification.description"
	readonly="true" />

<jstl:if test="${isCreator}">
	<a href="verification/edit.do?idVerification=${verification.id}"> <spring:message
			code="verification.edit" />
	</a>
</jstl:if>