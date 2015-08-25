<%--
 * action-1.jsp
 *
 * Copyright (C) 2013 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<spring:message code="dashboard.name" var="titleName" />
<spring:message code="dashboard.subjects" var="titleSubjects" />
<spring:message code="dashboard.rating" var="titleRating" />
<spring:message code="dashboard.lectureNotes" var="titleLectureNotes" />
<spring:message code="dashboard.comments" var="titleComments" />

<h2><spring:message code="dashboard.students"/></h2>

<display:table name="students" id="stu" requestURI="${requestURI}"
	pagesize="5" class="displaytag">

	<display:column property="student.name" title="${titleName}" />
	<display:column property="numLectureNotes" title="${titleLectureNotes}" />
	
</display:table>

<h2><spring:message code="dashboard.tutors"/></h2>
<br/>
<display:table name="tutors" id="tut" requestURI="${requestURI}"
	pagesize="5" class="displaytag">

	<display:column property="tutor.name" title="${titleName}" />
	<display:column property="rating" title="${titleRating}" />
	
	<display:column>
		<a href="teacher/details.do?idTeacher=${tut.tutor.id}"> <spring:message
				code="dashboard.profile" /></a>
	</display:column>
</display:table>

<h2><spring:message code="dashboard.lecturers"/></h2>

<br/>
<display:table name="lecturers" id="lec" requestURI="${requestURI}"
	pagesize="5" class="displaytag">

	<display:column property="lecturer.name" title="${titleName}" />
	<display:column property="subjects" title="${titleSubjects}" />
	
	<display:column>
		<a href="teacher/details.do?idTeacher=${lec.lecturer.id}"> <spring:message
				code="dashboard.profile" /></a>
	</display:column>
</display:table>

<h2><spring:message code="dashboard.subjectsMC"/></h2>
<br/>
<display:table name="subjects" id="sub" requestURI="${requestURI}"
	pagesize="5" class="displaytag">

	<display:column property="subject.name" title="${titleName}" />
	<display:column property="num" title="${titleComments}" />
	
	<display:column>
		<a href="subject/details.do?idSubject=${sub.subject.id}"> <spring:message
				code="dashboard.details" /></a>
	</display:column>
</display:table>
