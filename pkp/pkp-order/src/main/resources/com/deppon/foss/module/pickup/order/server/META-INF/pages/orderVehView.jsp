<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext"  %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="${styles}/order.css">
<ext:module subModule="orderVehView" groups="orderVehView" />
<spring:eval expression="@applicationProperties['gis.js.map']" var="gisjs"/> 
<script type="text/javascript" src="${gisjs}"></script>
