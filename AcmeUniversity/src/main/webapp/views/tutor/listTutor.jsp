<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>



	<spring:message code="tutor.name" var="titleName" />
	<spring:message code="tutor.subject" var="titleSubject" />

<display:table name="tutors" id="tutorForm" requestURI="${requestURI}"
	pagesize="5" class="displaytag">

	<display:column property="tutor.name" title="${titleName}" />
	<display:column property="subject.name" title="${titleSubject}" />
	<display:column>
		<a href="teacher/details.do?idTeacher=${tutorForm.tutor.id}"> <spring:message
				code="tutor.profile" /></a>
	</display:column>

</display:table>