<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext"  %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<link rel="stylesheet" type="text/css" href="${styles}/order.css">
<ext:module subModule="expressOrderHandle"/>

<spring:eval expression="@applicationProperties['gis.js.map']" var="gisjs"/> 
<script type="text/javascript" src="${gisjs}"></script>
<script type="text/javascript" src="${scripts}/expressOrderHandle.js"></script>