<%--
 * index.jsp
 *
 * Copyright (C) 2014 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<spring:message code="privateMessage.from" var="titleFrom" />
<spring:message code="privateMessage.subject" var="titleSubject" />

<display:table name="messages" id="message" requestURI="${requestURI}"
	pagesize="10" class="displaytag">
	
	<display:column property="sender.name" title="${titleFrom}"/>
	
	<display:column property="subject" title="${titleSubject}"/>
	
	<display:column>
		<a href="privateMessage/details.do?idMessage=<jstl:out value="${message.id}"/>">
			<spring:message code="privateMessage.detail"/>
		</a>
	</display:column>
	
</display:table>


