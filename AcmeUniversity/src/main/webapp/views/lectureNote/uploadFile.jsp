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


<form:form method="post" action="lectureNote/uploadFile.do"
	modelAttribute="lectureNoteForm" enctype="multipart/form-data">

	<form:hidden path="subject" />

	<fieldset>
		<legend>
			<spring:message code="lectureNote.lectureNotes" />
		</legend>

		<form:input code="lectureNote.file" path="file" type="file" />
	</fieldset>

	<acme:textarea code="lectureNote.description"
		path="description" />
	<acme:submit name="save" code="lectureNote.save" />
	<acme:cancel url="${previousURI}" code="lectureNote.cancel" />
</form:form>