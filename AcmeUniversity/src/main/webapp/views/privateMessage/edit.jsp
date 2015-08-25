<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="privateMessage/create.do"
	modelAttribute="privateMessageForm">

	<spring:message code="privateMessage.to"/>:
	<jstl:out value="${privateMessageForm.recipient.name}"/>
	<form:hidden path="recipient" />
	<acme:textbox code="privateMessage.subject" path="subject" />
	<acme:textbox code="privateMessage.text" path="text" />
	
	<acme:submit name="save" code="privateMessage.send" />
	<acme:cancel url="${previousURI}" code="registration.cancel" />

</form:form>