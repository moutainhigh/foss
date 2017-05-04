
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext"  %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript" >
/**
 * 该js是提供结算公共方法
 */
var stl = {};
/**
 * 单号个数
 */
stl.BILL_NOS_MAX = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants@BILL_NOS_MAX"/>';
/**
 * 日期范围常量
 */
//日期相差最大天数31天
stl.DATELIMITDAYS_MONTH = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants@DATE_LIMIT_DAYS_MONTH"/>'; 
//日期相差最大天数7天 
stl.DATELIMITDAYS_WEEK = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants@DATE_LIMIT_DAYS_WEEK"/>'; 
//日期相差最大天数90天
stl.DATELIMITMAXDAYS_WEEK = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants@DATE_LIMIT_DAYS_MAX_MONTH"/>'; 
//日期相差最大天数 3天
stl.DATE_THREE_DAYS_WEEK = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants@DATE_THREE_DAYS_WEEK"/>'; 
//报表查询差额
stl.PERIOD_MAX=24;
//EXECL导出限制5万条
stl.EXPORT_EXCEL_MAX_COUNTS=50000;

/**
 * 所有定义的公用常量
 */ 
stl.user= FossUserContext.getCurrentUser();//获得当前用户信息
stl.emp=FossUserContext. getCurrentUserEmp();//获得当前用户对应的职员信息
stl.dept=FossUserContext. getCurrentUserDept();// 获得当前用户默认部门信息
stl.currentDeptCode=FossUserContext. getCurrentDeptCode();// 获取当前用户设置的当前部门编码
stl.currentDept=FossUserContext. getCurrentDept();// 获取当前用户设置的当前部门
stl.currentUserOrgCodes= FossUserContext. getCurrentUserOrgCodes();// 获得当前用户所属的所有部门编码 返回一个字符串数组
stl.currentUserDepts= FossUserContext. getCurrentUserRoleCodes();// 获得当前用户所拥有的所有角色编码 返回一个字符串数组

stl.SCREENHEIGHT =  window.screen.height; //浏览器分辨率高
stl.SCREENWIDTH = window.screen.width; //浏览器分辨率宽

stl.FORMAT_DATE = 'Y-m-d'; //格式化日期字符串
stl.FORMAT_TIME = 'Y-m-d H:i:s'; //格式化时间字符串

stl.START_PREFIX=' 00:00:00';
stl.END_PREFIX=' 23:59:59';
/**
 * @param {} date--比较日期   day--比较日期之前或之后多少天的日期 day>0表示比较日期之后，day<0表示比较日期之前
 * @return {} 返回目标日期
 */
stl.getTargetDate = function(date, day) {
	var d, s, t, t2;
  	var MinMilli = 1000 * 60;
  	var HrMilli = MinMilli * 60;
  	var DyMilli = HrMilli * 24;
  	t = Date.parse(date);
  	t2 =  new Date(t+day*DyMilli);
  	return t2;
};

/**
 * @param {} 小的日期
 * @param {} 大的日期
 * 比较两个日期差额
 */
stl.compareTwoDate = function(startDate,endDate){
  var startDateClone = Ext.Date.clone(new Date(startDate));
  var endDateClone =  Ext.Date.clone(new Date(endDate));
  startDateClone.setHours(0);
  startDateClone.setMinutes(0);
  startDateClone.setSeconds(0);
  startDateClone.setMilliseconds(0);
  
  endDateClone.setHours(0);
  endDateClone.setMinutes(0);
  endDateClone.setSeconds(0);
  endDateClone.setMilliseconds(0);
  
  var d, s, t, t2;
  var MinMilli = 1000 * 60;
  var HrMilli = MinMilli * 60;
  var DyMilli = HrMilli * 24;
  
  d = new Date();
  t = Date.parse(startDateClone);
  t2= Date.parse(endDateClone); 
  s = Math.round((t2-t)/ DyMilli)+1;
  
  return s;
};

/**
 * 声明期间model
 */
Ext.define('Foss.common.PeriodModel',{
	extend:'Ext.data.Model',
	fields:[{
		name:'name'
	},{
		name:'value'
	}]
});
/**
 * 获取从当前月份开始往前推months个月的所有月份集合
 */
stl.getPeriod = function(months){
  var format = 'Ym';
  var now,period,currentMonth,model,month;
  period = [];
  currentMonth = new Date().getMonth();
  if(!Ext.isEmpty(months) && months>0){
	   for(var i=0;i<months;i++){
		now = new Date();
		now.setMonth(currentMonth-i);
		month = Ext.Date.format(now, format);
		var monthModel = new Object();
		monthModel.name = month;
		monthModel.value = month;
		model = new Foss.common.PeriodModel(monthModel);
		period.push(model);
	  }
  }else{
    now = new Date();
    month = Ext.Date.format(now, format);
    model = new Foss.common.PeriodModel(month,month);
	period.push(model);
  }
  return period;
};
/**
 * 期间store
 */
Ext.define('Foss.common.PeriodStore',{
	extend:'Ext.data.Store',
	model:'Foss.common.PeriodModel',
	data:stl.getPeriod(stl.PERIOD_MAX)
});



/**
 * 将传入字符串以，;进行分割，返回数组
 * @param msg 要分个字符
 */
stl.splitToArray = function(msg){
	 var reg,
	 	array;
	 if(Ext.String.trim(msg)!=null && Ext.String.trim(msg)!=''){
		 reg = /[,;]/;  
		 array = msg.split(reg);
		 return array;
	}else{
		return null;
	}
}

/**
 * 将传入字符串以，;进行分割，并去除数组中空值项，返回数组
 * @param msg 要分个字符
 */
stl.splitToArrayRemoveEmpty = function(msg){
	var reg,array,realArr;
	if(Ext.String.trim(msg)!=null && Ext.String.trim(msg)!=''){
		reg = /[,;\s]/;
		array = msg.split(reg);	
		realArr = new Array();
		for (var i = 0; i < array.length; i++) { 
			 if(Ext.String.trim(array[i])!=null && Ext.String.trim(array[i])!=''){
				realArr.push(array[i]);
			}
		} 
		if(realArr.length>0){
			return realArr;
		}else{
			return null;
		}
		
	}else{
		return null;
	}
}

 /**
  * 将传入字符串以 空格，换行，符号  进行分割，返回数组
  * @param msg 要分个字符
  */
 stl.splitToArray2 = function(msg){
 	 var reg,
 	 	array;
 	 if(Ext.String.trim(msg)!=null && Ext.String.trim(msg)!=''){
 		 array = msg.match(/\d{8,}/g);
 		 return array;
 	}else{
 		return null;
 	}
 }

 /**
  * 将传入字符串以 空格，换行，符号  进行分割，返回运单号数组
  * @param msg 要分个字符
  */
 stl.selectWaybillNosArray = function(msg){
 	 var reg;
 	 var array = new Array();
 	 if(Ext.String.trim(msg)!=null && Ext.String.trim(msg)!=''){
 		 array = msg.match(/\d{8,}/g);
 		 return array;
 	}else{
 		return array;
 	}
 }
 
 /**
  * 将传入字符串以 空格，换行，符号  进行分割，返回应付单数组
  * @param msg 要分个字符
  */
 stl.selectPayableNosArray = function(msg){
 	 var reg;
 	 var array = new Array();
 	 if(Ext.String.trim(msg)!=null && Ext.String.trim(msg)!=''){
 		 array = msg.match(/YF\d{8,}/g);
 		 return array;
 	}else{
 		return array;
 	}
 }

 /**
  * 将传入字符串以 空格，换行，符号  进行分割，返回应收单数组
  * @param msg 要分个字符
  */
 stl.selectReceivableNosArray = function(msg){
 	 var reg;
 	 var array = new Array();
 	 if(Ext.String.trim(msg)!=null && Ext.String.trim(msg)!=''){
 		 array = msg.match(/YS\d{8,}/g);
 		 return array;
 	}else{
 		return array;
 	}
 }
 /**
  * 转换long类型为日期
  *@param value 要转换的时间
  */
stl.longToDateConvert = function(value) {
	if (!Ext.isEmpty(value)) {
		var date = new Date(value);
		return date;
	} else {
		return null;
	}
}

/**
  * 时间格式化
  *@param value 要格式化的时间
  *@format 格式化方式如  Y-m-d;Y-m-d H:i:s
  */
stl.dateFormat=function(value,format){
	if(!Ext.isEmpty(value)){
		return Ext.Date.format(new Date(value), format);
	}else{
		return null;
	}
	
}
 
 /**
  * comboBox的change事件重写，使其达到可以删除，且只能选择下拉框里卖弄内容
  */
stl.comboSelsct = function(combo){
	if(Ext.isEmpty(combo.getValue())){
		combo.setValue("");
	}
};
  
/**
 * 界面金额格式化
 *@param value 要格式化的金额
 *@format 格式化方式如  XXXXX.00
 */  
stl.amountFormat = function(value){
	if(value!=null && value!=0){
		return value.toFixed(2);
	}else if(value==0){
		return 0;
	}else{
		return null;
	}
}

/**
 * 格式化金额，金额格式
 @param value 要格式化的金额
 @format 格式化方式如 1,281,568.83
 @Date 2012-12-27   
 @Author dp-liqin
 */
 stl.amountConvert=function (val){
	    if(val!=null){
	    	return "" + Ext.util.Format.currency(val,' ',2,false) + "";
	    }else{
	    	return value.toFixed(2);
	    }
 }
 
 /**
  * 获取上个月第一天
  */
 stl.getLastMonthFristDay = function(date) {
 	 var month = date.getMonth() - 1;
 	 date.setMonth(month);
 	 return Ext.Date.getFirstDateOfMonth(date);;
 };

 /**
  * 获取上个月最后一天
  */
 stl.getLastMonthLastDay = function(date) {
 	 var month = date.getMonth() - 1;
 	 date.setMonth(month);
 	 return Ext.Date.getLastDateOfMonth(date);;
 };
 

 /**
  * JS中金额相减，避免出现大串小数
  */
 stl.subtrAmountPoint = function Subtr(arg1,arg2){
     var r1,r2,m,n;
     try{r1=arg1.toString().split(".")[1].length}catch(e){r1=0}
     try{r2=arg2.toString().split(".")[1].length}catch(e){r2=0}
     m=Math.pow(10,Math.max(r1,r2));
     //last modify by deeka
     //动态控制精度长度
     n=(r1>=r2)?r1:r2;
     return ((arg1*m-arg2*m)/m).toFixed(n);
}

 stl.convertProductCode = function(productCodes) {
	if (!Ext.isEmpty(productCodes)) {
		var productCodeList = [];
		for ( var i = 0; i < productCodes.length; i++) {
			// 如果产品类型中存在值为空，则表明为全部，那么默认全部查询
			if (Ext.isEmpty(productCodes[i])) {
				productCodeList = [];
				break;
			} else {
				productCodeList.push(productCodes[i]);
			}
		}
		return productCodeList;
	} else {
		return [];
	}
}
 
 /**
 * 对于JAVASCRIPT中金额的加减乘除运算
 * 2013-09-16
 * author:zbw
 */
 
 //金额相除
 stl.amountDiv = function(arg1,arg2){
	 var t1 = 0,t2 = 0,r1,r2;
	 
	 try{
		 t1 = arg1.toString().split(".")[1].length;
	 }catch(e){
		 // do nothing
	 }
	 
	 try{
		 t2 = arg2.toString().split(".")[1].length;
	 }catch(e){
		 // do nothing
	 }
	 
	 with(Math){
		 r1 = Number(arg1.toString().replace(".",""));
		 r2 = Number(arg2.toString().replace(".",""));
		 return stl.amountMul((r1/r2),pow(10,t2-t1));
	 }
 }
 
 //金额相乘
 stl.amountMul = function(arg1,arg2){
	 var m = 0,s1 = arg1.toString(),s2 = arg2.toString();
	 
	 try{
		 m += s1.split(".")[1].length;
	 }catch(e){
		 // do nothing
	 }
	 
	 try{
		 m += s2.split(".")[1].length;
	 }catch(e){
		 // do nothing
	 }
	 
	 var r1 = Number(s1.replace(".",""));
	 var r2 = Number(s2.replace(".",""));
	 
	 return r1 * r2 / Math.pow(10,m);
 }
 
 //金额相加
 stl.amountAdd = function(arg1,arg2){
	var t1 = 0,t2 = 0,m;
	 
	 try{
		 t1 = arg1.toString().split(".")[1].length;
	 }catch(e){
		 // do nothing
	 }
	 
	 try{
		 t2 = arg2.toString().split(".")[1].length;
	 }catch(e){
		 // do nothing
	 }
	 
	 m = Math.pow(10,Math.max(t1,t2));
	 return (arg1*m + arg2*m) / m;
 }
 
 //金额相减
 stl.amountSub = function(arg1,arg2){
	 var t1 = 0,t2 = 0,m,n;
	 
	 try{
		 t1 = arg1.toString().split(".")[1].length;
	 }catch(e){
		 // do nothing
	 }
	 
	 try{
		 t2 = arg2.toString().split(".")[1].length;
	 }catch(e){
		 // do nothing
	 }
	 
	 with(Math){
		 n = max(t1,t2);
		 m = pow(10,n);
		 return ((arg1*m - arg2*m)/m).toFixed(n);
	 }
	 
 }
 
 /**
 * 结算需要的数据验证
 */
 
 
 
</script>