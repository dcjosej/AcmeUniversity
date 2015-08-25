<%--
 * header.jsp
 *
 * Copyright (C) 2014 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>

<div>
	<img src="images/logo.png" alt="Sample Co., Inc." />
</div>

<div>
	<ul id="jMenu">
		<!-- Do not forget the "fNiv" class for the first level links !! -->
		<security:authorize access="hasRole('ADMIN')">
			<li><a class="fNiv"><spring:message	code="master.page.administrator" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="degree/list.do"><spring:message code="master.page.student.listDegrees" /></a></li>
					<li><a href="application/list.do"><spring:message code="master.page.administrator.listApplication" /></a></li>
					<li><a href="degree/create.do"><spring:message code="master.page.administrator.createDegree" /></a></li>					
					<li><a href="admin/dashboard.do"><spring:message code="master.page.administrator.dashboard" /></a></li>					
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="hasRole('TUTOR')">
			<li><a class="fNiv"><spring:message	code="master.page.tutor" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="degree/list.do"><spring:message code="master.page.student.listDegrees" /></a></li>
					<li><a href="tutorial/create.do"><spring:message code="master.page.tutorial.create" /></a></li>
					<li><a href="teacher/myProfile.do"><spring:message code="master.page.teacher.myProfile" /></a></li>
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="hasRole('LECTURER')">
			<li><a class="fNiv"><spring:message	code="master.page.lecturer" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="degree/list.do"><spring:message code="master.page.student.listDegrees" /></a></li>
					<li><a href="tutor/listTutors.do"><spring:message code="master.page.tutor.listTutor" /></a></li>
					<li><a href="teacher/myProfile.do"><spring:message code="master.page.teacher.myProfile" /></a></li>
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="hasRole('STUDENT')">
			<li><a class="fNiv"><spring:message	code="master.page.student" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="degree/list.do"><spring:message code="master.page.student.listDegrees" /></a></li>
					<li><a href="degree/listMyDegrees.do"><spring:message code="master.page.student.myDegrees" /></a></li>
					<li><a href="slot/timetable.do"><spring:message code="master.page.student.timetable" /></a></li>
					<li><a href="teacher/bestRated.do"><spring:message code="master.page.student.bestRatedTeachers" /></a></li>
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="security/login.do"><spring:message code="master.page.login" /></a></li>
			<li><a class="fNiv"><spring:message	code="master.page.register" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="security/register/student.do"><spring:message code="master.page.register.student" /></a></li>
					<li><a href="security/register/tutor.do"><spring:message code="master.page.register.tutor" /></a></li>
					<li><a href="security/register/lecturer.do"><spring:message code="master.page.register.lecturer" /></a></li>
				</ul>
			</li>
		</security:authorize>
		
		
		<security:authorize access="isAuthenticated()">
			<li>
				<a class="fNiv"> 
					<spring:message code="master.page.profile" /> 
			        (<security:authentication property="principal.username" />)
				</a>
				<ul>
					<li class="arrow"></li>
					<li><a href="privateMessage/send.do"><spring:message code="master.page.student.createMessage" /></a></li>
					<li><a href="privateMessage/received.do"><spring:message code="master.page.student.receivedMessages" /></a></li>
					<li><a href="j_spring_security_logout"><spring:message code="master.page.logout" /> </a></li>
				</ul>
			</li>
		</security:authorize>
	</ul>
</div>

<div>
	<a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>

