<%@ page language="java"  pageEncoding="UTF-8"%>
<%-- 是否 忽略EL表达式 --%>
<%@ page isELIgnored="false"%>
<%-- 是否移除html的空行 --%>
<%@ page trimDirectiveWhitespaces="true"%> 
<%-- jstl标签 --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<c:set var="base" value="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}" />

<%-- 项目路径 --%>
<c:set var="path" value="${pageContext.request.contextPath }"/>