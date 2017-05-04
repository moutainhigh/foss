/**
 * 工作日
 * 
 * @author:谢艳涛 Build date: 2012-12-26
 * 
 */
// 定义一个model
Ext.define('Foss.baseinfo.workday.WorkdayModel', {
			extend : 'Ext.data.Model',
			fields : [{
						name : 'id'
					}, {
						name : 'virtualCode', // 虚拟编码
						type : 'string'
					}, {
						name : 'workMonth', // 工作月份,唯一标识,例：201203
						type : 'string'
					}, {
						name : 'day1', // 一号
						type : 'string'
					}, {
						name : 'day2', 
						type : 'string'
					}, {
						name : 'day3', 
						type : 'string'
					}, {
						name : 'day4', 
						type : 'string'
					}, {
						name : 'day5', 
						type : 'string'
					}, {
						name : 'day6', 
						type : 'string'
					}, {
						name : 'day7', 
						type : 'string'
					}, {
						name : 'day8', 
						type : 'string'
					}, {
						name : 'day9', 
						type : 'string'
					}, {
						name : 'day10', 
						type : 'string'
					}, {
						name : 'day11', 
						type : 'string'
					}, {
						name : 'day12', 
						type : 'string'
					}, {
						name : 'day13', 
						type : 'string'
					}, {
						name : 'day14', 
						type : 'string'
					}, {
						name : 'day15', 
						type : 'string'
					}, {
						name : 'day16', 
						type : 'string'
					}, {
						name : 'day17', 
						type : 'string'
					}, {
						name : 'day18', 
						type : 'string'
					}, {
						name : 'day19', 
						type : 'string'
					}, {
						name : 'day20', 
						type : 'string'
					}, {
						name : 'day21', 
						type : 'string'
					}, {
						name : 'day22', 
						type : 'string'
					}, {
						name : 'day23', 
						type : 'string'
					}, {
						name : 'day24', 
						type : 'string'
					}, {
						name : 'day25', 
						type : 'string'
					}, {
						name : 'day26', 
						type : 'string'
					}, {
						name : 'day27', 
						type : 'string'
					}, {
						name : 'day28', 
						type : 'string'
					}, {
						name : 'day29', 
						type : 'string'
					}, {
						name : 'day30', 
						type : 'string'
					}, {
						name : 'day31', 
						type : 'string'
					},{
						name : 'active', // 是否有效
						type : 'string'
					}, {
						name : 'remarkInfo', // 备注
						type : 'string'
					}, {
						name : 'createUser', // 创建人
						type : 'string'
					}, {
						name : 'createDate', // 创建时间
						type : 'date'
					}, {
						name : 'modifyUser', // 修改人
						type : 'string'
					}, {
						name : 'modifyDate', // 修改时间
						type : 'date'
					}]
		});

/* ===================================以下是对日历显示处理========================================== */

var months = new Array("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11",
		"12");
var daysInMonth = new Array(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31);
var days = new Array("周日", "周一", "周二", "周三", "周四", "周五", "周六");
var today;

/**
 * 判断是否闰年，返回2月是28或29
 * @param {} month
 * @param {} year
 * @return {}
 */
function getDays(month, year) {
	// 下面的这段代码是判断当前是否是闰年的
	if (1 == month)
		return ((0 == year % 4) && (0 != (year % 100))) || (0 == year % 400)? 29: 28;
	else
		return daysInMonth[month];
}

function getToday() {
	// 得到今天的年,月,日
	this.now = new Date();
	this.year = this.now.getFullYear();
	this.month = this.now.getMonth();
	this.day = this.now.getDate();
}

function getStringDay(str) {
	// 得到输入框的年,月,日
	var str = str.split("-")

	this.now = new Date(parseFloat(str[0]), parseFloat(str[1]) - 1,
			parseFloat(str[2]));
	this.year = this.now.getFullYear();
	this.month = this.now.getMonth();
	this.day = this.now.getDate();
}

/**
 * 创建日历，并设置背景颜色
 */
function newCalendar(workday) {
	
	// 获取年份
	var parseYear = parseInt(document.all.Year.options[document.all.Year.selectedIndex].value);
	// 获取月份
	var parseMonth = parseInt(document.all.Month.options[document.all.Month.selectedIndex].value);
	// 显示月份赋值
	document.all.showMonth.innerText = parseMonth + "月份";
	var newCal = new Date(parseYear, document.all.Month.selectedIndex, 1);
	var day = -1;
	//获取选择后的天数
	var startDay = newCal.getDay();
	var daily = 0;

	if ((today.year == newCal.getFullYear())
			&& (today.month == newCal.getMonth()))
		day = today.day;
    //获取当前日历表格
	var tableCal = document.all.calendar;
	//获取每月最大天数
	var intDaysInMonth = getDays(newCal.getMonth(), newCal.getFullYear());

	for (var intWeek = 1; intWeek < tableCal.rows.length; intWeek++){
		for (var intDay = 0; intDay < tableCal.rows[intWeek].cells.length; intDay++) {
			//获取单元格
			var cell = tableCal.rows[intWeek].cells[intDay];
			cell.style.background = '#EDF2FC';
			if ((intDay == startDay) && (0 == daily))
				daily = 1;
			if (day == daily) // 今天，调用今天的Class
			{
//				cell.style.background = '#6699CC';
				cell.style.color = 'red';
			} else if (intDay == 6) // 周六
				cell.style.color = 'green';
			else if (intDay == 0) // 周日
				cell.style.color = 'green';
			else {
				//设置单元格样式为初始颜色
				cell.style.background = '#EDF2FC';
			}

			if ((daily > 0) && (daily <= intDaysInMonth)) {
				cell.innerText = daily;
				//判断选择的日历在数据库是否存在
				if(!Ext.isEmpty(workday)){//如果存在
					var dayName = 'day' + daily;
			    	var dayx = workday.get(dayName);
			    	if(dayx == 'N'){
			    		cell.style.background = '#FFCC99';
			    		cell.style.color = '#3908A3';
			    	}
				}else{
					if(day == daily){
						cell.style.background = '#6699CC';
					}else{
						//设置单元格样式为初始颜色
						cell.style.background = '#EDF2FC';
					}
				}
				daily++;
			} else
				cell.innerText = "";
		}
	}
}

/**
 * 获取当前选中的日期
 */
function getSelectedDate() {
	var sDate;
	// 这段代码处理鼠标点击的情况
	if (event.srcElement.tagName == "TD")
		if (event.srcElement.innerText != "") {
			//获取工作日Form
			var workdayForm = Ext.getCmp('T_baseinfo-workday_content').getCalendarForm();
			// 点击时单元格样式改变
			event.srcElement.style.background = '#FFCC99'
			sDate = document.all.Year.value + "-" + document.all.Month.value
					+ "-" + event.srcElement.innerText;
			//选中日期转化成int类型 
			var intDay = parseInt(event.srcElement.innerText);
			//组装属性名称
			var propertyName = "day" + intDay;
			//定义工作日Model
			var workdayModel = workdayForm.infoModel;
			if(workdayModel.get(propertyName) == "Y"){
			   	//设置为非工作日
				workdayModel.set(propertyName,'N');
			}else{
			   	//设置为工作日
				workdayModel.set(propertyName,'Y');
				// 点击时单元格样式改变
				event.srcElement.style.background = '#EDF2FC'
			}
		}
}

/**
 * 显示工作日主界面
 */
function ShowCalendar(workday) {
	var x, y, intLoop, intWeeks, intDays;
	var DivContent;
	var year, month, day;
	var thisyear; // 真正的今年年份

	thisyear = new getToday();
	thisyear = thisyear.year;

	today = "";
	if (isDate(today))
		today = new getStringDay(today);
	else
		today = new getToday();

	document.all.Calendar.style.visibility = "visible";

	// 下面开始输出日历表格(border-color:#9DBAF7)
	DivContent = "<table border='0' width='500' height='300' cellspacing='0' style='border:1px solid #0066FF; background-color:#EDF2FC'>";
	DivContent += "<tr>";
	DivContent += "<td height='35' colspan='2' align='center' style='border-bottom:1px solid #0066FF; background-color:#C7D8FA'>";

	// 年
	DivContent += "<select name='Year' id='Year' onChange='validateInfo()' style='font-family:Verdana; font-size:14px'>";
	for (intLoop = thisyear - 2; intLoop < (thisyear + 2); intLoop++) {
		DivContent += "<option  value= " + intLoop + " "
				+ (today.year == intLoop ? "Selected" : "") + ">" + intLoop
				+ "</option>";
	}
	DivContent += "</select>";
	DivContent += "<font style='font-family:Verdana; font-size:15px'>&nbsp;年&nbsp;</font>"

	// 月
	DivContent += "<select name='Month' id='Month' onChange='validateInfo();' style='font-family:Verdana; font-size:14px'>";
	for (intLoop = 0; intLoop < months.length; intLoop++) {
		DivContent += "<option value= " + (intLoop + 1) + " "
				+ (today.month == intLoop ? "Selected" : "") + ">"
				+ months[intLoop] + "</option>";
	}
	DivContent += "</select>";
	DivContent += "<font style='font-family:Verdana; font-size:15px'>&nbsp;月&nbsp;</font>"
	DivContent += "</td>";

	DivContent += "</tr>";
	// 显示选择的月份
	DivContent += "<tr>";
	DivContent += "<td height='35' align='center' style='font-size:15px;background-color:#C7D8FA'' colspan = '2'><font id='showMonth'></font></td>";
	DivContent += "</tr>";

	DivContent += "<tr><td align='center' colspan='2'>";
	DivContent += "<table id='calendar' border='1' height='400' width='100%'>";

	// 星期
	DivContent += "<tr>";
	for (intLoop = 0; intLoop < days.length; intLoop++)
		DivContent += "<td align='center' style='font-size:12px'>"
				+ days[intLoop] + "</td>";
	DivContent += "</tr>";

	// 天
	for (intWeeks = 0; intWeeks < 6; intWeeks++) {
		DivContent += "<tr>";
		for (intDays = 0; intDays < days.length; intDays++)
			DivContent += "<td ondblclick='getSelectedDate()' style='cursor:hand; border-right:1px solid #BBBBBB; border-bottom:1px solid #BBBBBB; color:#215DC6; font-family:Verdana; font-size:12px' align='center'></td>";
		DivContent += "</tr>";
	}
	DivContent += "</table></td></tr>";
	// 操作按钮
	DivContent += "<tr>";
	DivContent += "<td height='50' align='center' style='font-size:15px;background-color:#C7D8FA'' colspan = '2'><input id='yellow_button' onClick='validateInfo();' type='button' value= '重置'/>&nbsp;&nbsp;" +
			"<input onClick='commitInfo()' type='button' value = '保存'/></td>";
	DivContent += "</tr>";
	DivContent += "</table>";
	
	DivContent += "<br/><p><font align= 'left' color= 'red'>* 默认颜色为工作日，浅红色背景颜色为休息日,红色字体表示今日</font>";
	document.all.Calendar.innerHTML = DivContent;
	//日历处理
	newCalendar(workday);
}
/**
 * 选择时进行数据库验证，工作日在数据库是否存在
 */
function validateInfo(){
    //日历处理
	Ext.getCmp('T_baseinfo-workday_content').getCalendarForm().queryWorkDay(false);
}

/**
 * 重置信息
 */
function resetInfo(){
	Ext.getCmp('T_baseinfo-workday_content').getCalendarForm().queryWorkDay(false);
}
/**
 * 保存数据信息
 */
function commitInfo(){
   Ext.getCmp('T_baseinfo-workday_content').getCalendarForm().commitCalendar();
}

function isDate(dateStr) {
	var datePat = /^(\d{4})(\-)(\d{1,2})(\-)(\d{1,2})$/;
	var matchArray = dateStr.match(datePat);
	if (matchArray == null)
		return false;
	var month = matchArray[3];
	var day = matchArray[5];
	var year = matchArray[1];
	if (month < 1 || month > 12)
		return false;
	if (day < 1 || day > 31)
		return false;
	if ((month == 4 || month == 6 || month == 9 || month == 11) && day == 31)
		return false;
	if (month == 2) {
		var isleap = (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0));
		if (day > 29 || (day == 29 && !isleap))
			return false;
	}
	return true;
}

/**
 * 工作日表单
 */
Ext.define('Foss.baseinfo.workday.CalendarForm', {
			extend : 'Ext.form.Panel',
			title : baseinfo.workday.i18n('foss.baseinfo.workday'),
			frame : true,
			infoModel : Ext.create('Foss.baseinfo.workday.WorkdayModel'),
			infoObj : null,
			html:"<div id='Calendar' style='position:absolute;top:10%; left:25%; z-index:1; visibility: hidden; '></div>",
			defaults : {
				labelSeparator : '',
				margin : '8 10 5 10',
				anchor : '100%'
			},
			calendarValue:null,
			//获取日历当前选中的值，保存信息
			commitCalendar : function(){
				var me = this;
				// 获取年份
				var parseYear = parseInt(document.all.Year.options[document.all.Year.selectedIndex].value);
				// 获取月份
				var parseMonth = document.all.Month.options[document.all.Month.selectedIndex].value;
				if(parseInt(parseMonth) < 10){
					parseMonth = "0" + parseInt(parseMonth);
				}
				me.infoModel.set('workMonth',parseYear+parseMonth);
				//组装JSON数据
				var jsonData = {'workdayVo':{'entity' : me.infoModel.data}};
				
				Ext.MessageBox.buttonText.yes = baseinfo.workday.i18n('foss.baseinfo.confirm');  //foss.baseinfo.confirm "确定"
				Ext.MessageBox.buttonText.no = baseinfo.workday.i18n('foss.baseinfo.cancel'); 
				Ext.Msg.confirm( baseinfo.workday.i18n('foss.baseinfo.tipInfo'), baseinfo.workday.i18n('foss.baseinfo.saveNotice'), function(btn,text){
					if(btn == 'yes'){
						//Ajax提交
						Ext.Ajax.request({
							url:baseinfo.realPath('saveOrUpdateWorkday.action'),
							jsonData:jsonData,
							//作废成功
							success : function(response) {
				                  var json = Ext.decode(response.responseText);
				                  
				                  me.infoObj = json.workdayVo.entity;
				                  //打印成功消息
				                  me.showWarningMsg(baseinfo.workday.i18n('foss.baseinfo.notice'),json.message);
				                },
				            //保存失败
				            exception : function(response) {
				                  var json = Ext.decode(response.responseText);
				                  //打印作废失败消息
				                  me.showWarningMsg(baseinfo.workday.i18n('foss.baseinfo.notice'),json.message);
				            }
						});
					}
				});
			},
			
			//根据工作月份查询当月的工作日是否存在
			queryWorkDay : function(istrue){
				var me = this;
				var myYear,myMonth;
				if(istrue){
					//获取当前时间
			 		var myDate = new Date();
			 		myYear = myDate.getFullYear();
					myMonth = parseInt(myDate.getMonth())+1;
					
					
				}else{
					// 获取年份
					myYear = parseInt(document.all.Year.options[document.all.Year.selectedIndex].value);
					// 获取月份
					myMonth = parseInt(document.all.Month.options[document.all.Month.selectedIndex].value);
				}
				
				//获取当前月份最大天数
		 		var maxDay = getDays(myMonth-1,myYear);
		 		//infoModel对象初始赋值（1-31字段赋值为'N'）
		 		for(var i = 1; i < 32; i++){
		 			var dayName = 'day' + i;
		 			if(i < maxDay+1){//
		 				me.infoModel.set(dayName,'Y');
		 			}else{
		 				//如果一个月小于31天，则剩下天数都设置为非工作日
		 				me.infoModel.set(dayName,'N');
		 			}
		 		}
				
				if(parseInt(myMonth) < 10){
					myMonth = "0" + parseInt(myMonth);
				}
				//获取工作月份
				var workMonth = myYear+''+myMonth;
				//组装JSON数据
				var jsonData = {'workdayVo':{'workMonth':workMonth}};
				//Ajax提交
				Ext.Ajax.request({
					url:baseinfo.realPath('queryWorkday.action'),
					jsonData:jsonData,
					//作废成功
					success : function(response) {
		                  var json = Ext.decode(response.responseText);
		                  
		                  if(!Ext.isEmpty(json.workdayVo.entity)){
		                  	me.infoModel = new Foss.baseinfo.workday.WorkdayModel(json.workdayVo.entity);
		                    if(istrue){
		                    	//显示日历
						  		ShowCalendar(me.infoModel);
		                    }else{
		                    	//日历处理
		                    	newCalendar(me.infoModel);
		                    }
		                    
		                  }else{
		                    if(istrue){
		                    	//显示日历
						  		ShowCalendar(null);
		                    }else{
		                    	//日历处理
		                    	newCalendar(null);
		                    }
		                  }
		                  
		                },
		            //保存失败
		            exception : function(response) {
		                  var json = Ext.decode(response.responseText);
		                  //打印作废失败消息
		                  me.showWarningMsg(baseinfo.workday.i18n('foss.baseinfo.notice'),json.message);
		            }
				});
				
			},
			//消息提醒框
			showWarningMsg : function(title,message,fun){
				Ext.Msg.show({
				    title:title,
				    msg:message,
				    width:120,
				    buttons: Ext.Msg.OK,
				    icon: Ext.MessageBox.WARNING,
				    callback:function(e){
				    	if(!Ext.isEmpty(fun)){
				    		if(e=='ok'){
					    		fun();
					    	}
				    	}
				    }
				});
				//3秒后提醒框隐藏
				setTimeout(function(){
			        Ext.Msg.hide();
			    }, 3000);
			},
			height : 680,
			constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				me.listeners = {
				 	afterrender:function(){
//				 		//获取当前时间
//				 		var date = new getToday();
//				 		//获取当前年份
//				 		var year = date.year;
//				 		//获取当前月份
//				 		var month = date.month;
//				 		//获取当前月份最大天数
//				 		var maxDay = getDays(month,year);
//				 		//infoModel对象初始赋值（1-31字段赋值为'N'）
//				 		for(var i = 1; i < 32; i++){
//				 			var dayName = 'day' + i;
//				 			if(i < maxDay+1){//
//				 				me.infoModel.set(dayName,'Y');
//				 			}else{
//				 				//如果一个月小于31天，则剩下天数都设置为非工作日
//				 				me.infoModel.set(dayName,'N');
//				 			}
//				 		}
				 		//查询工作日
				 		me.queryWorkDay(true);
				 	}
				 
				 };
//				 me.fbar = [{
//					xtype : 'button', 
//					width:70,
//					text : baseinfo.workday.i18n('foss.baseinfo.reset'),//重置
//					margin:'0 850 0 0',
//					handler : function() {
//						me.getForm().reset();
//					}
//				},{
//					xtype : 'button', 
//					width:70,
//					text : baseinfo.workday.i18n('foss.baseinfo.save'),
//					cls:'yellow_button',
//					handler : function() {
//						Ext.getCmp('T_baseinfo-workday_content').getCalendarForm().commitCalendar();
//					}
//				}];
				me.callParent([cfg]);
			}
		});

Ext.onReady(function() {
			Ext.QuickTips.init();
			// 工作日FORM
			var calendarForm = Ext.create('Foss.baseinfo.workday.CalendarForm');

			Ext.getCmp('T_baseinfo-workday').add(Ext.create('Ext.panel.Panel', {
						id : 'T_baseinfo-workday_content',
						cls : 'panelContentNToolbar',
						bodyCls : 'panelContentNToolbar-body',
						layout : 'auto',
						// 获得工作日FORM
						getCalendarForm : function() {
							return calendarForm;
						},
						items : [calendarForm]
					}));
		});

