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
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:textbox code="lecture.group" path="lecture.group" readonly="true" />

<h2>
	<spring:message code="lecture.slots" />
</h2>

<spring:message code="lecture.dayOfTheWeek" var="titleDayOfTheWeek" />
<spring:message code="lecture.startTime" var="titleStartTime" />
<spring:message code="lecture.endTime" var="titleEndTime" />


<display:table name="slots" id="slot" requestURI="${requestURI}"
	pagesize="5" class="displaytag">

	<display:column title="${titleDayOfTheWeek}" property="dayOfTheWeek" />
	<display:column title="${titleStartTime}" property="start" format="{0,date,HH:mm}" />
	<display:column title="${titleEndTime}" property="finish" format="{0,date,HH:mm}"/>

</display:table>

	<h2><spring:message code="tutorial.comments"/></h2>
	
	<spring:message code="tutorial.user" var="titleUser" />
	<spring:message code="tutorial.text" var="titleText" />
	
<display:table name="lecture.comments" id="comment"
		requestURI="${requestURI}" pagesize="5" class="displaytag">
		<display:column property="actor.name" title="${titleUser}" />
		<display:column property="text" title="${titleText}" />
	</display:table>
	<br/>
	<jstl:if test="${canComment == true}">
		<a href="comment/create.do?id=<jstl:out value="${lecture.id}"/>">
		<spring:message code="tutorial.newComment"/>
	</a>
	</jstl:if>
	