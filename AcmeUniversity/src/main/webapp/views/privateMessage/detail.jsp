<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>



	<spring:message code="privateMessage.from"/>:
	<jstl:out value="${privateMessage.sender.name}"/>
	
	
	<acme:textbox code="privateMessage.subject" path="privateMessage.subject" readonly="true"/>
	<acme:textbox code="privateMessage.text" path="privateMessage.text" readonly="true"/>
	<a href="privateMessage/reply.do?idMessage=<jstl:out value="${privateMessage.id}"/>">
		<spring:message code="privateMessage.reply"/>
	</a> 
	<br/>
	<acme:cancel url="${previousURI}" code="registration.cancel" />
	
