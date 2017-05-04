<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib uri="/ext" prefix="ext" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 
<spring:eval expression="@applicationProperties['gis.js.map']" var="gishost"/> 
<script src="${gishost}" type="text/javascript"> </script>
<ext:module subModule="expressDeliverySmallZone" groups = "expressDeliverySmallZone"/>