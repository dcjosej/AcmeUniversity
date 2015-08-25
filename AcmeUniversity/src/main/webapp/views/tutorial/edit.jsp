<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="tutorial/edit.do" modelAttribute="tutorialForm">
	<form:hidden path="id" />
	<form:hidden path="version" />

	<acme:textbox code="tutorial.minStudents" path="minStudents" />
	<acme:textbox code="tutorial.maxStudents" path="maxStudents" />
	<acme:textbox code="tutorial.pricePerHour" path="pricePerHour" />

	<acme:select items="${subjects}" itemLabel="name"
		code="tutorial.subject" path="subject" />

	<h2>
		<spring:message code="tutorial.slots" />
	</h2>

<form:hidden path="numSlots" />
<jstl:if test="${tutorialForm.numSlots > 0}">
	<jstl:forEach var="i" begin="0" end="${tutorialForm.numSlots - 1}">
		<div>
			<form:hidden path="slots[${i}].id" />
			<form:hidden path="slots[${i}].version" />
			<form:label path="slots[${i}].dayOfTheWeek">
				<spring:message code="tutorial.slot.dayOfTheWeek" />
			</form:label>
			<form:select path="slots[${i}].dayOfTheWeek">
				<spring:message code="slot.monday" var="mon" />
				<spring:message code="slot.tuesday" var="tue" />
				<spring:message code="slot.wednesday" var="wed" />
				<spring:message code="slot.thursday" var="thu" />
				<spring:message code="slot.friday" var="fri" />
				<spring:message code="slot.saturday" var="sat" />
				<spring:message code="slot.sunday" var="sun" />

				<form:option label="----" value="0" />
				<form:option label="${mon}" value="Monday" />
				<form:option label="${tue}" value="Tuesday" />
				<form:option label="${wed}" value="Wednesday" />
				<form:option label="${thu}" value="Thursday" />
				<form:option label="${fri}" value="Friday" />
				<form:option label="${sat}" value="Saturday" />
				<form:option label="${sun}" value="Sunday" />
			</form:select>
			<form:errors cssClass="error" path="slots[${i}].dayOfTheWeek" />

			<acme:textbox code="slot.start"
				path="slots[${i}].start" />
			<acme:textbox code="slot.finish"
				path="slots[${i}].finish" />
		</div>
	</jstl:forEach>
</jstl:if>
	
	<acme:submit name="save" code="save" />
	<acme:submit name="addSlot" code="tutorial.addSlot" />
	<acme:submit name="removeSlot" code="tutorial.removeSlot" />
	<acme:cancel code="cancel" url="${requestURI}"/>

</form:form>