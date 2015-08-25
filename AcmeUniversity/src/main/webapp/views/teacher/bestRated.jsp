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

<spring:message code="teacher.name" var="titleName" />
<spring:message code="teacher.assessment.rating" var="titleRating" />

<h2><spring:message code="teacher.lecturers"/></h2>


<display:table name="lecturers" id="lecturerForm" requestURI="${requestURI}"
	pagesize="5" class="displaytag">

	<display:column property="lecturer.name" title="${titleName}" />
	<display:column property="rating" title="${titleRating}" />
	<display:column>
		<a href="teacher/details.do?idTeacher=${lecturerForm.lecturer.id}"> <spring:message
				code="teacher.profile" /></a>
	</display:column>
</display:table>

<h2><spring:message code="teacher.tutors"/></h2>

<display:table name="tutors" id="tutorForm" requestURI="${requestURI}"
	pagesize="5" class="displaytag">

	<display:column property="tutor.name" title="${titleName}" />
	<display:column property="rating" title="${titleRating}" />
	<display:column>
		<a href="teacher/details.do?idTeacher=${tutorForm.tutor.id}"> <spring:message
				code="teacher.profile" /></a>
	</display:column>
</display:table>