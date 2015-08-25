<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<ul>
	<li><a href="security/register/student.do?idSocial=<jstl:out value="${idSocial}"/>"><spring:message code="master.page.register.student" /></a></li>
	<li><a href="security/register/tutor.do?idSocial=<jstl:out value="${idSocial}"/>"><spring:message code="master.page.register.tutor" /></a></li>
	<li><a href="security/register/lecturer.do?idSocial=<jstl:out value="${idSocial}"/>"><spring:message code="master.page.register.lecturer" /></a></li>

</ul>