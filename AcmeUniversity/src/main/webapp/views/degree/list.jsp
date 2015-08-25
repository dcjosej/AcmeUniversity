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

<spring:message code="degree.name" var="titleName" />

<display:table name="degrees" id="degree" requestURI="${requestURI}"
	pagesize="5" class="displaytag">

	<display:column property="name" title="${titleName}" />

	<display:column>
		<a href="subject/list.do?idDegree=${degree.id}"> <spring:message
				code="degree.details" /></a>
	</display:column>

	<security:authorize access="hasRole('STUDENT')">

		<display:column>
			<jstl:if test="${!student.degrees.contains(degree)}">
				<a href="degree/enrols.do?idDegree=${degree.id}"> <spring:message
						code="degree.enrols" /></a>
			</jstl:if>
		</display:column>

	</security:authorize>
	
	<security:authorize access="hasRole('ADMIN')">

		<display:column>
				<a href="subject/create.do?idDegree=${degree.id}"> <spring:message
						code="master.page.administrator.createSubject" /></a>
		</display:column>

	</security:authorize>

</display:table>