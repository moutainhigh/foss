<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext"  %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ include file="../predeliver/productInfo.jsp" %>
<link rel="stylesheet" type="text/css" href="${styles}/order.css">
<ext:module subModule="orderHandle" groups="orderHandle"/>
<spring:eval expression="@applicationProperties['gis.js.map']" var="gisjs"/> 
<script type="text/javascript" src="${gisjs}"></script>