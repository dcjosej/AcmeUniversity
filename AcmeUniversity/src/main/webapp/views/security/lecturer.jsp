<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form method="post" action="security/register/lecturer.do"
	modelAttribute="registrationTeacherForm" enctype="multipart/form-data">

	<form:hidden path="idSocial" />

	<acme:textbox code="registration.name" path="name" />
	<acme:textbox code="registration.surname" path="surname" />
	<acme:textbox code="registration.email" path="email" />
	<acme:textbox code="registration.phone" path="phoneNumber" />
	<acme:textbox code="registration.description" path="description" />
	<br />
	<fieldset>
		<legend>
			<spring:message code="registration.curriculum" />
		</legend>
		<acme:textbox code="registration.personalData"
			path="curriculum.personalData" />
		<acme:textbox code="registration.academicExperience"
			path="curriculum.academicExperience" />
		<acme:textbox code="registration.otherData" path="curriculum.other" />
	</fieldset>
	<br />
	<acme:textbox code="registration.username" path="username" />
	<acme:password code="registration.password" path="password" />
	<acme:password code="registration.passwordVerification"
		path="passwordVerification" />

	<br />
	<fieldset>
		<legend>
			<spring:message code="registration.photo" />
		</legend>

		<form:input code="registration.photo" path="photo" type="file" />
	</fieldset>
	<br />

	<div>
		<form:checkbox id="check" path="terms" value="" />
		<label for="check"><spring:message code="terms.1" /> <a
			href="<spring:message code="terms.link" />" target="_blank"> <spring:message
					code="terms.2" />
		</a></label>
	</div>
	<br />
	<div id="botonRegistrar">
		<acme:submit name="save" code="registration.save" />
	</div>
	<acme:cancel url="${previousUri}" code="registration.cancel" />

</form:form>