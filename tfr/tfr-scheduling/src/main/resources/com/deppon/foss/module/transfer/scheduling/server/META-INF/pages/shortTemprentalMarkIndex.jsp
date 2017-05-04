<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext"  %>
<ext:module subModule="shortTemprentalmark"/>
<script type="text/javascript" src="${scripts}/shortTemprentalMark.js"></script>
<script>
scheduling.shortTemprentalmark.departmentSignle = '${requestScope.shortRentalMarkVo.shortRentalMarkDto.departmentSignle}';
if (scheduling.shortTemprentalmark.departmentSignle.split('_')[0] == 'SalesDepartment') {
	scheduling.shortTemprentalmark.useCarDepartment = '${requestScope.shortRentalMarkVo.shortRentalMarkDto.useCarDepartment}';
}
</script>
<body>
		<div id="T_scheduling-shortTemprentalmark-body"></div>
</body>