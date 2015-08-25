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


<div>
	<spring:message code="lectureNote.student" />
	:
	<jstl:out value="${lectureNote.student.name}" />
	<jstl:out value="${lectureNote.student.surname}" />
</div>
<div>
	<spring:message code="lectureNote.name" />
	:
	<jstl:out value="${lectureNote.name}" />
</div>

<acme:textarea code="lectureNote.description"
	path="lectureNote.description" readonly="true" />

<a href="lectureNote/download.do?idLectureNote=${lectureNote.id}"><spring:message
		code="subject.lectureNote.download" /></a>

<h2>
	<spring:message code="lectureNote.verifications" />
</h2>

<spring:message code="lectureNote.lecturer" var="titleLecturer" />
<spring:message code="lectureNote.approved" var="titleApproved" />

<display:table name="verifications" id="verification"
	requestURI="${requestURI}" pagesize="5" class="displaytag">

	<display:column title="${titleLecturer}">
		<jstl:out value="${verification.lecturer.name}" />
		<jstl:out value="${verification.lecturer.surname}" />
	</display:column>
	<display:column title="${titleApproved}">
		<jstl:if test="${verification.correct == true}">
			<spring:message code="lectureNote.yes" />
		</jstl:if>
		<jstl:if test="${verification.correct == false}">
			<spring:message code="lectureNote.no" />
		</jstl:if>
	</display:column>

	<display:column>
		<a href="verification/details.do?idVerification=${verification.id}">
			<spring:message code="lectureNote.verification.details" />
		</a>
	</display:column>
</display:table>

<security:authorize access="hasRole('LECTURER')">
	<jstl:if test="${canCreate}">
		<a href="verification/create.do?idLectureNote=${lectureNote.id}">
			<spring:message code="lectureNote.verification.create" />
		</a>
	</jstl:if>
</security:authorize>