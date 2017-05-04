<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="/ext" prefix="ext"  %>
<script type="text/javascript"> 
	function del(additionalID,buttonID){
		//alert("您确定要删除该附件？");
		if(confirm("您确定要删除该附件？")){
			document.getElementById(additionalID).style.display="none";
	   		document.getElementById(buttonID).style.display="none";     
		}
	}
</script>
<ext:module subModule="codRefund" groups="codRefund"/>
