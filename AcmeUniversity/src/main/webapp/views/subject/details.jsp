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

<spring:message code="subject.name" />
<jstl:out value="${subject.name}" />
<br />

<acme:textarea code="subject.description" path="subject.description"
	readonly="true" />

<jstl:if test="${canRegister}">
	<a
		href="subject/register.do?idSubject=<jstl:out value="${subject.id}"/>">
		<spring:message code="subject.register" />
	</a>
</jstl:if>

<jstl:if test="${canEdit}">
	<a href="subject/edit.do?idSubject=<jstl:out value="${subject.id}"/>">
		<spring:message code="subject.edit" />
	</a>
</jstl:if>


<h2>
	<spring:message code="subject.lecturer" />
</h2>

<spring:message code="subject.lecturer" var="titleLecturer" />

<display:table name="lecturers" id="lecturer" requestURI="${requestURI}"
	pagesize="5" class="displaytag">

	<display:column title="${titleLecturer}">
		<a href="teacher/details.do?idTeacher=${lecturer.id}"> <jstl:out
				value="${lecturer.name}" /> <jstl:out value="${lecturer.surname}" />
		</a>
	</display:column>
</display:table>

<h2>
	<spring:message code="subject.lectureNotes" />
</h2>

<spring:message code="subject.student" var="titleStudent" />
<spring:message code="subject.lectureNote.name" var="titleName" />

<display:table name="lectureNotes" id="lectureNote"
	requestURI="${requestURI}" pagesize="5" class="displaytag">

	<display:column title="${titleStudent}">
		<jstl:out value="${lectureNote.student.name}" />
		<jstl:out value="${lectureNote.student.surname}" />
	</display:column>

	<display:column title="${titleName}" property="name" />
	<display:column>
		<a href="lectureNote/download.do?idLectureNote=${lectureNote.id}"><spring:message
				code="subject.lectureNote.download" /></a>
	</display:column>
	<security:authorize access="hasRole('LECTURER')">
		<display:column>
			<a href="lectureNote/details.do?idLectureNote=${lectureNote.id}"><spring:message
					code="subject.lectureNote.details" /></a>
		</display:column>
	</security:authorize>
</display:table>
<security:authorize access="hasRole('STUDENT')">
	<a href="lectureNote/uploadFile.do?idSubject=${subject.id}"><spring:message
			code="subject.lectureNote.uploadFile" /></a>
</security:authorize>

<h2>
	<spring:message code="subject.activities" />
</h2>

<h3>
	<spring:message code="subject.tutorials" />
</h3>

<spring:message code="subject.tutorial.tutor" var="titleTutor" />
<spring:message code="subject.tutorial.minStudents"
	var="titleMinStudents" />
<spring:message code="subject.tutorial.maxStudents"
	var="titleMaxStudents" />
<spring:message code="subject.tutorial.pricePerHour"
	var="titlePricePerHour" />

<display:table name="tutorials" id="tutorial" requestURI="${requestURI}"
	pagesize="5" class="displaytag">

	<display:column title="${titleTutor}">
		<jstl:out value="${tutorial.tutor.name}" />
		<jstl:out value="${tutorial.tutor.surname}" />
	</display:column>

	<display:column title="${titleMinStudents}" property="minStudents" />
	<display:column title="${titleMaxStudents}" property="maxStudents" />
	<display:column title="${titlePricePerHour}" property="pricePerHour" />
	<display:column>
		<a href="tutorial/detail.do?id=${tutorial.id}"><spring:message
				code="subject.tutorial.details" /></a>
	</display:column>
</display:table>

<h3>
	<spring:message code="subject.lectures" />
</h3>

<spring:message code="subject.lecture.group" var="titleGroup" />

<display:table name="lectures" id="lecture" requestURI="${requestURI}"
	pagesize="5" class="displaytag">

	<display:column title="${titleGroup}" property="group" />
	<security:authorize access="hasAnyRole('STUDENT', 'LECTURER')">
		<display:column>
			<a href="lecture/details.do?idLecture=${lecture.id}"><spring:message
					code="subject.lecture.details" /></a>
		</display:column>
	</security:authorize>
	<security:authorize access="hasRole('STUDENT')">
		<display:column>
			<jstl:if test="${!lecture.students.contains(student)}">
				<a href="lecture/register.do?idLecture=${lecture.id}"><spring:message
						code="subject.lecture.register" /></a>
			</jstl:if>
		</display:column>
	</security:authorize>
</display:table>




