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

<form:form action="teacher/edit.do" modelAttribute="teacherForm"
	method="post" enctype="multipart/form-data">

	<form:hidden path="id" />
	<form:hidden path="version" />
	
	<acme:textbox code="teacher.email" path="email" />
	<acme:textbox code="teacher.phoneNumber" path="phoneNumber" />
	<acme:textbox code="teacher.description" path="description" />

	<jstl:if test="${!(oldPhoto == null)}">
		<h2>
			<spring:message code="teacher.photo" />
		</h2>

		<img src="teacher/photo.do?idTeacher=${teacher.id}" width="200"
			height="200" />

	</jstl:if>

	<form:input code="teacher.photo" path="newPhoto" type="file" />

	<fieldset>
		<legend>
			<spring:message code="teacher.curriculum" />
		</legend>
		<acme:textarea code="teacher.personalData"
			path="curriculum.personalData" />
		<acme:textarea code="teacher.academicExperience"
			path="curriculum.academicExperience" />
		<acme:textarea code="teacher.other" path="curriculum.other" />
	</fieldset>

	<acme:submit name="save" code="save" />
	<acme:cancel code="cancel" url="${previousURI}"/>
</form:form>