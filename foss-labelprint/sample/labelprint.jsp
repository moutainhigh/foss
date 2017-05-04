<%@page import="java.io.Writer"%>
<%@page language="java" pageEncoding="UTF-8"%>
    <link rel="stylesheet" type="text/css" href="http://192.168.17.167/poc/rsc/css/frame-all-4_1.css">
    <link rel="stylesheet" type="text/css" href="http://192.168.17.167/poc/rsc/css/frame-all-4_1-customized.css">
    <link rel="stylesheet" type="text/css" href="http://192.168.17.167/poc/rsc/css/commonicons.css">
    <link rel="stylesheet" type="text/css" href="http://192.168.17.167/poc/rsc/css/moduleicons.css">
    <script type="text/javascript" src="http://192.168.17.167/poc/extjs/bootstrap.js"></script>
    <script type="text/javascript" src="http://192.168.17.167/poc/extend/dateTimeField_date97/dateTimeField.js"></script>
	<script type="text/javascript" src="http://192.168.17.167/poc/extend/dateTimeField_date97/WdatePicker.js" id='$date97js'></script>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <script type="text/javascript" src="http://192.168.17.167/poc/extjs/ext-all-dev.js"></script>
</head>
<body>
hello world
<input type="button" value="运单标签" onclick="f_prt_CommLabelPrintWorker()" >
<input type="button" value="整车标签" onclick="f_prt_WholeCarLabelPrintWorker()" >
<input type="button" value="无标签货物标签" onclick="f_prt_NoLabelGoodsLabelPrintWorker()" >
<input type="button" value="航空正单标签" onclick="f_prt_AirWayBillLabelPrintWorker()" >
<input type="button" value="车辆标签" onclick="f_prt_VehicleLabelPrintWorker()" >
</body>
</html>
<script language="javascript" >

var vurl = "http://localhost:8077/print";

function f_prt_VehicleLabelPrintWorker(){
	Ext.data.JsonP.request({
        url: vurl,
        callbackKey: 'callback',
	    params: {
	  	 lblprtworker:"VehicleLabelPrintWorker",
		 optusernum:"101057",
		 printdate:"2012-10-29",
		 serialnos:"ha00001,se88888",
		 carnos:"沪A00001,苏E88888"
		 teams:"上海车队,苏州车队",
	     groups:"上海车队嘉定东部接送货组,苏州车队东部接送货组"
	    },
		callback: function() {
 			//回调函数，不管请求成功与否都执行
 			//alert("callback");
		},   	    
	    success: function(result, request) {
			var ret_code = result.data.code; // 1=,0=
			alert(result.data.msg);
	    },
	    failure : function (result, request) {
	    	var ret_code = result.data.code;
			alert(result.data.msg);
	    }
	});
}

function f_prt_CommLabelPrintWorker(){
	Ext.data.JsonP.request({
        url: vurl,
        callbackKey: 'callback',
	    params: {
	  	 lblprtworker:"CommLabelPrintWorker",
	   	 addr1:"D01",
		 addr2:"D02",
		 addr3:"D03",
		 addr4:"D04",
		 location1:"73",
		 location2:"75",
		 location3:"104",
		 location4:"106",
		 optusernum:"101057",
		 number:"10251111",
		 serialnos:"0001,0002,0003,0005",
		 leavecity:"广州",
		 destination:"上海崇明",
		 isagent:"true",
		 stationnumber:"0000",
		 deptno:"D05",
		 finaloutfieldid:"D05",
		 finaloutname:"青浦",
		 weight:"20.8",
		 totalpieces:"20",
		 packing:"木",
		 unusual:"异",
		 transtype:"精准卡航",
		 printdate:"2012-10-29",
		 deliver:"送",
		 goodstype:"B",
		 preassembly:""
	    },
		callback: function() {
 			//回调函数，不管请求成功与否都执行
 			//alert("callback");
		},   	    
	    success: function(result, request) {
			var ret_code = result.data.code;
			alert(result.data.msg);
	    },
	    failure : function (result, request) {
	    	var ret_code = result.data.code;
			alert(result.data.msg);
	    }
	});
}


function f_prt_WholeCarLabelPrintWorker(){
	Ext.data.JsonP.request({
        url: vurl,
        callbackKey: 'callback',
	    params: {
	  	 lblprtworker:"WholeCarLabelPrintWorker",
	  	 prtpiece:"1",
	  	 destination:"上海青浦",
	  	 number:"01234567",
	  	 departure:"广州东平营业部",
	  	 arrival:"上海派送部",
	  	 username:"张三",
	  	 datetime:"2012-10-29"
	    },
		callback: function() {
 			//回调函数，不管请求成功与否都执行
 			//alert("callback");
		},   	    
	    success: function(result, request) {
			var ret_code = result.data.code;
			alert(result.data.msg);
	    },
	    failure : function (result, request) {
	    	var ret_code = result.data.code;
			alert(result.data.msg);
	    }
	});
}


function f_prt_NoLabelGoodsLabelPrintWorker(){
	Ext.data.JsonP.request({
        url: vurl,
        callbackKey: 'callback',
	    params: {
	  	 lblprtworker:"NoLabelGoodsLabelPrintWorker",
	     optusernum:"101057", 
		 printdate:"2012-12-06",  
		 number:"12345678",
		 serialnos:"0002",
		 totalpieces:"10",
		 packing:"1木2纸",
		 currentOrgName:"上海外场",
		 goodsAreaName:"D01",
		 handoverNo:"",
		 goodsName:"冰箱",
		 weight:"1.3",
		 volume:"2.1"
	    },
		callback: function() {
 			//回调函数，不管请求成功与否都执行
 			//alert("callback");
		},   	    
	    success: function(result, request) {
			var ret_code = result.data.code;
			alert(result.data.msg);
	    },
	    failure : function (result, request) {
	    	var ret_code = result.data.code;
			alert(result.data.msg);
	    }
	});
}

/**
 * 编码cnpycode:HH,SD,SZ,XM,DF,GH,NH
 * 航空公司名称cnpyname:海南航空,山东航空,深圳航空,厦门航空,中国东方航空公司,中国国际航空公司,中国南方航空公司
 */
function f_prt_AirWayBillLabelPrintWorker(){
	Ext.data.JsonP.request({
        url: vurl,
        callbackKey: 'callback',
	    params: {
	  	 lblprtworker:"AirWayBillLabelPrintWorker",
		 cnpycode:"HH",
		 cnpyname:"海南航空",
		 awbno:"30087634",
		 pieces:"10", 
		 goodsweight:"20.35", 
		 pcsweight:"5.3", 
		 from:"上海",
		 to:"呼和浩特",
		 size:"1"
	    },
		callback: function() {
 			//回调函数，不管请求成功与否都执行
 			//alert("callback");
		},   	    
	    success: function(result, request) {
			var ret_code = result.data.code;
			alert(result.data.msg);
	    },
	    failure : function (result, request) {
	    	var ret_code = result.data.code;
			alert(result.data.msg);
	    }
	});
}
</script>
