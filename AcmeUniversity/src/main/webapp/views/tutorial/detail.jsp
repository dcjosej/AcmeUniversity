<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

	<acme:textbox code="tutorial.minStudents" path="tutorial.minStudents"
		readonly="true" />
	<acme:textbox code="tutorial.maxStudents" path="tutorial.maxStudents"
		readonly="true" />
	<acme:textbox code="tutorial.pricePerHour" path="tutorial.pricePerHour"
		readonly="true" />
	<label>
		<spring:message code="tutorial.tutor"/>
	</label>
	
	<a href="teacher/details.do?idTeacher=<jstl:out value="${tutorial.tutor.id}"/>">
		<jstl:out value="${tutorial.tutor.name}"/>
	</a>
	<br/>
	<jstl:if test="${canRegister == true}">
		<a href="tutorial/register.do?id=<jstl:out value="${tutorial.id}"/>">
		<spring:message code="tutorial.register"/>
	</a>
	</jstl:if>
	
	<jstl:if test="${editable == true}">
		<a href="tutorial/edit.do?idTutorial=<jstl:out value="${tutorial.id}"/>">
		<spring:message code="tutorial.edit"/>
	</a>
	</jstl:if>
	
	<h2><spring:message code="tutorial.timetable"/></h2>
	
	<spring:message code="tutorial.dayOfTheWeek" var="titleDay" />
	<spring:message code="slot.start" var="titleStart" />
		<spring:message code="slot.finish" var="titleFinish" />
	
	<display:table name="tutorial.slots" id="slot"
		requestURI="${requestURI}" pagesize="5" class="displaytag">
		<display:column property="dayOfTheWeek" title="${titleDay}" />
		<display:column title="${titleStartTime}" property="start" format="{0,date,HH:mm}" />
		<display:column title="${titleEndTime}" property="finish" format="{0,date,HH:mm}"/>
	</display:table>
	
	<h2><spring:message code="tutorial.comments"/></h2>
	
	<spring:message code="tutorial.user" var="titleUser" />
	<spring:message code="tutorial.text" var="titleText" />
	
	<display:table name="tutorial.comments" id="comment"
		requestURI="${requestURI}" pagesize="5" class="displaytag">
		<display:column property="actor.name" title="${titleUser}" />
		<display:column property="text" title="${titleText}" />
	</display:table>
	<br/>
	<jstl:if test="${canComment == true}">
		<a href="comment/create.do?id=<jstl:out value="${tutorial.id}"/>">
		<spring:message code="tutorial.newComment"/>
	</a>
	</jstl:if>
	