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
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<spring:message code="slot.subject" var="titleSubject" />
<spring:message code="slot.activity" var="titleActivity" />
<spring:message code="slot.start" var="titleStart" />
<spring:message code="slot.finish" var="titleFinish" />

<h2><spring:message code="slot.monday"/></h2>

<display:table name="monday" id="mondaySlot" requestURI="${requestURI}">
	<display:column title="${titleSubject}">
		<a href="subject/details.do?idSubject=<jstl:out value="${mondaySlot.slot.activity.subject.id}"/>">
			<jstl:out value="${mondaySlot.slot.activity.subject.name}"/>
		</a>
	</display:column>
	
	<display:column title="${titleActivity}">
		<jstl:if test="${mondaySlot.type == 'Tutorial'}">
			<a href="tutorial/detail.do?id=<jstl:out value="${mondaySlot.slot.activity.id}"/>">
			<spring:message code="slot.tutorial"/>
		</a>
		</jstl:if> 
		
		<jstl:if test="${mondaySlot.type == 'Lecture'}">
			<a href="lecture/details.do?idLecture=<jstl:out value="${mondaySlot.slot.activity.id}"/>">
			<spring:message code="slot.lecture"/>
		</a>
		</jstl:if>
	</display:column>
	
	<display:column title="${titleStart}">
		<fmt:formatDate pattern="HH:mm" type="time" value="${mondaySlot.slot.start}" />
	</display:column>
	<display:column title="${titleFinish}">
		<fmt:formatDate pattern="HH:mm" type="time" value="${mondaySlot.slot.finish}" />
	</display:column>
</display:table>

<h2><spring:message code="slot.tuesday"/></h2>

<display:table name="tuesday" id="tuesdaySlot" requestURI="${requestURI}">
	<display:column title="${titleSubject}">
		<a href="subject/details.do?idSubject=<jstl:out value="${tuesdaySlot.slot.activity.subject.id}"/>">
			<jstl:out value="${tuesdaySlot.slot.activity.subject.name}"/>
		</a>
	</display:column>
	
	<display:column title="${titleActivity}">
		<jstl:if test="${tuesdaySlot.type == 'Tutorial'}">
			<a href="tutorial/detail.do?id=<jstl:out value="${tuesdaySlot.slot.activity.id}"/>">
			<spring:message code="slot.tutorial"/>
		</a>
		</jstl:if> 
		
		<jstl:if test="${tuesdaySlot.type == 'Lecture'}">
			<a href="lecture/details.do?idLecture=<jstl:out value="${tuesdaySlot.slot.activity.id}"/>">
			<spring:message code="slot.lecture"/>
		</a>
		</jstl:if>
	</display:column>
	
	<display:column title="${titleStart}">
		<fmt:formatDate pattern="HH:mm" type="time" value="${tuesdaySlot.slot.start}" />
	</display:column>
	<display:column title="${titleFinish}">
		<fmt:formatDate pattern="HH:mm" type="time" value="${tuesdaySlot.slot.finish}" />
	</display:column>
</display:table>

<h2><spring:message code="slot.wednesday"/></h2>

<display:table name="wednesday" id="wednesdaySlot" requestURI="${requestURI}">
	<display:column title="${titleSubject}">
		<a href="subject/details.do?idSubject=<jstl:out value="${wednesdaySlot.slot.activity.subject.id}"/>">
		<jstl:out value="${wednesdaySlot.slot.activity.subject.name}"/>
		</a>
	</display:column>
	
	<display:column title="${titleActivity}">
		<jstl:if test="${wednesdaySlot.type == 'Tutorial'}">
			<a href="tutorial/detail.do?id=<jstl:out value="${wednesdaySlot.slot.activity.id}"/>">
			<spring:message code="slot.tutorial"/>
		</a>
		</jstl:if> 
		
		<jstl:if test="${wednesdaySlot.type == 'Lecture'}">
			<a href="lecture/details.do?idLecture=<jstl:out value="${wednesdaySlot.slot.activity.id}"/>">
			<spring:message code="slot.lecture"/>
		</a>
		</jstl:if>
	</display:column>
	
	<display:column title="${titleStart}">
		<fmt:formatDate pattern="HH:mm" type="time" value="${wednesdaySlot.slot.start}" />
	</display:column>
	<display:column title="${titleFinish}">
		<fmt:formatDate pattern="HH:mm" type="time" value="${wednesdaySlot.slot.finish}" />
	</display:column>
</display:table>

<h2><spring:message code="slot.thursday"/></h2>

<display:table name="thursday" id="thursdaySlot" requestURI="${requestURI}">
	<display:column title="${titleSubject}">
		<a href="subject/details.do?idSubject=<jstl:out value="${thursdaySlot.slot.activity.subject.id}"/>">
			<jstl:out value="${thursdaySlot.slot.activity.subject.name}"/>
		</a>
	</display:column>
	
	<display:column title="${titleActivity}">
		<jstl:if test="${thursdaySlot.type == 'Tutorial'}">
			<a href="tutorial/detail.do?id=<jstl:out value="${thursdaySlot.slot.activity.id}"/>">
			<spring:message code="slot.tutorial"/>
		</a>
		</jstl:if> 
		
		<jstl:if test="${thursdaySlot.type == 'Lecture'}">
			<a href="lecture/details.do?idLecture=<jstl:out value="${thursdaySlot.slot.activity.id}"/>">
			<spring:message code="slot.lecture"/>
		</a>
		</jstl:if>
	</display:column>
	
	<display:column title="${titleStart}">
		<fmt:formatDate pattern="HH:mm" type="time" value="${thursdaySlot.slot.start}" />
	</display:column>
	<display:column title="${titleFinish}">
		<fmt:formatDate pattern="HH:mm" type="time" value="${thursdaySlot.slot.finish}" />
	</display:column>
</display:table>

<h2><spring:message code="slot.friday"/></h2>

<display:table name="friday" id="fridaySlot" requestURI="${requestURI}">
	<display:column title="${titleSubject}">
		<a href="subject/details.do?idSubject=<jstl:out value="${fridaySlot.slot.activity.subject.id}"/>">
			<jstl:out value="${fridaySlot.slot.activity.subject.name}"/>
		</a>
	</display:column>
	
	<display:column title="${titleActivity}">
		<jstl:if test="${fridaySlot.type == 'Tutorial'}">
			<a href="tutorial/detail.do?id=<jstl:out value="${fridaySlot.slot.activity.id}"/>">
			<spring:message code="slot.tutorial"/>
		</a>
		</jstl:if> 
		
		<jstl:if test="${fridaySlot.type == 'Lecture'}">
			<a href="lecture/details.do?idLecture=<jstl:out value="${fridaySlot.slot.activity.id}"/>">
			<spring:message code="slot.lecture"/>
		</a>
		</jstl:if>
	</display:column>
	
	<display:column title="${titleStart}">
		<fmt:formatDate pattern="HH:mm" type="time" value="${fridaySlot.slot.start}" />
	</display:column>
	<display:column title="${titleFinish}">
		<fmt:formatDate pattern="HH:mm" type="time" value="${fridaySlot.slot.finish}" />
	</display:column>
</display:table>

<h2><spring:message code="slot.saturday"/></h2>

<display:table name="saturday" id="saturdaySlot" requestURI="${requestURI}">
	<display:column title="${titleSubject}">
		<a href="subject/details.do?idSubject=<jstl:out value="${saturdaySlot.slot.activity.subject.id}"/>">
			<jstl:out value="${saturdaySlot.slot.activity.subject.name}"/>
		</a>
	</display:column>
	
	<display:column title="${titleActivity}">
		<jstl:if test="${saturdaySlot.type == 'Tutorial'}">
			<a href="tutorial/detail.do?id=<jstl:out value="${saturdaySlot.slot.activity.id}"/>">
			<spring:message code="slot.tutorial"/>
		</a>
		</jstl:if> 
		
		<jstl:if test="${saturdaySlot.type == 'Lecture'}">
			<a href="lecture/details.do?idLecture=<jstl:out value="${saturdaySlot.slot.activity.id}"/>">
			<spring:message code="slot.lecture"/>
		</a>
		</jstl:if>
	</display:column>
	
	<display:column title="${titleStart}">
		<fmt:formatDate pattern="HH:mm" type="time" value="${saturdaySlot.slot.start}" />
	</display:column>
	<display:column title="${titleFinish}">
		<fmt:formatDate pattern="HH:mm" type="time" value="${saturdaySlot.slot.finish}" />
	</display:column>
</display:table>

<h2><spring:message code="slot.sunday"/></h2>

<display:table name="sunday" id="sundaySlot" requestURI="${requestURI}">
	<display:column title="${titleSubject}">
		<a href="subject/details.do?idSubject=<jstl:out value="${sundaySlot.slot.activity.subject.id}"/>">
			<jstl:out value="${sundaySlot.slot.activity.subject.name}"/>
		</a>
	</display:column>
	
	<display:column title="${titleActivity}">
		<jstl:if test="${sundaySlot.type == 'Tutorial'}">
			<a href="tutorial/detail.do?id=<jstl:out value="${sundaySlot.slot.activity.id}"/>">
			<spring:message code="slot.tutorial"/>
		</a>
		</jstl:if> 
		
		<jstl:if test="${sundaySlot.type == 'Lecture'}">
			<a href="lecture/details.do?idLecture=<jstl:out value="${sundaySlot.slot.activity.id}"/>">
			<spring:message code="slot.lecture"/>
		</a>
		</jstl:if>
	</display:column>
	
	<display:column title="${titleStart}">
		<fmt:formatDate pattern="HH:mm" type="time" value="${sundaySlot.slot.start}" />
	</display:column>
	<display:column title="${titleFinish}">
		<fmt:formatDate pattern="HH:mm" type="time" value="${sundaySlot.slot.finish}" />
	</display:column>
</display:table>

