<%--
 * action-2.jsp
 *
 * Copyright (C) 2013 Universidad de Sevilla
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

<acme:textbox code="teacher.name" path="teacher.name" readonly="true" />
<acme:textbox code="teacher.surname" path="teacher.surname"
	readonly="true" />
<acme:textbox code="teacher.email" path="teacher.email" readonly="true" />

<jstl:if  test="${canEdit == true}">
	<a href="teacher/edit.do?idTeacher=${teacher.id}"><spring:message code="teacher.edit"/></a>
</jstl:if>
<jstl:if test="${!(teacher.photo == null)}">
	<h2>
		<spring:message code="teacher.photo" />
	</h2>

	<img src="teacher/photo.do?idTeacher=${teacher.id}" width="200"
		height="200" />

</jstl:if>

<fieldset>
	<legend>
		<spring:message code="teacher.curriculum" />
	</legend>
	<acme:textarea code="teacher.personalData"
		path="teacher.curriculum.personalData" readonly="true" />
	<acme:textarea code="teacher.academicExperience"
		path="teacher.curriculum.academicExperience" readonly="true" />
	<acme:textarea code="teacher.other" path="teacher.curriculum.other"
		readonly="true" />
</fieldset>

<spring:message code="teacher.assessment.student" var="titleStudent" />
<spring:message code="teacher.assessment.text" var="titleText" />
<spring:message code="teacher.assessment.rating" var="titleRating" />

<h2><spring:message code="teacher.assessments"/></h2>
<display:table name="assessments" id="assessment"
	requestURI="${requestURI}" pagesize="5" class="displaytag">
	<display:column title="${titleStudent}">
		<jstl:out
			value="${assessment.student.name} ${assessment.student.surname}"></jstl:out>
	</display:column>
	<display:column property="text" title="${titleText}" />
	<display:column property="rating" title="${titleRating}" />
</display:table>

<jstl:if test="${canAssess}">
	<a href="teacher/assess.do?idTeacher=${teacher.id}"><spring:message
			code="teacher.assess" /></a>
</jstl:if>

<spring:message code="teacher.tutor.degree" var="titleDegree" />
<spring:message code="teacher.tutor.subject" var="titleSubject" />
<spring:message code="teacher.tutor.minStudents" var="titleMinStudents" />
<spring:message code="teacher.tutor.maxStudents" var="titleMaxStudents" />
<spring:message code="teacher.tutor.pricePerHour"
	var="titlePricePerHour" />

<security:authorize access="hasRole('TUTOR')">
<h2><spring:message code="subject.tutorials"/></h2>
	<display:table name="tutorials" id="tutorial"
		requestURI="${requestURI}" pagesize="5" class="displaytag">

		<display:column title="${titleDegree}" property="subject.degree.name" />
		<display:column title="${titleSubject}" property="subject.name" />
		<display:column title="${titleMinStudents}" property="minStudents" />
		<display:column title="${titleMaxStudents}" property="maxStudents" />
		<display:column title="${titlePricePerHour}" property="pricePerHour" />
		<display:column>
			<a href="tutorial/detail.do?id=${tutorial.id}"><spring:message
					code="teacher.tutor.details" /></a>
		</display:column>
	</display:table>
</security:authorize>

<security:authorize access="hasRole('LECTURER')">
<h2><spring:message code="subject.lecture.group"/></h2>
	<display:table name="subjects" id="subject" requestURI="${requestURI}"
		pagesize="5" class="displaytag">

		<display:column title="${titleDegree}" property="degree.name" />
		<display:column title="${titleSubject}" property="name" />
		<display:column>
			<a href="subject/details.do?idSubject=${subject.id}"><spring:message
					code="teacher.lecturer.details" /></a>
		</display:column>
	</display:table>
</security:authorize>


