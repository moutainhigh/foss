<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext"  %>
<style type="text/css">
.custom-grid .x-grid-row-over .x-grid-cell { 
    background-color: #ff6; 
    border-bottom-color: #999; 
    border-bottom-style: dashed; 
    border-top-color: #999; 
    border-top-style: dashed; 
} 
 
/* Grid cells in the selected row */ 
.custom-grid .x-grid-row-selected .x-grid-cell { 
    background-color: #ff0 !important; 
    border-bottom-color: #999; 
    border-bottom-style: solid; 
    border-top-color: #999; 
    border-top-style: solid; 
}
</style>
<ext:module  subModule="monitorIndex"/>
<script type="text/javascript" src="${scripts}/monitorIndex.js"></script>