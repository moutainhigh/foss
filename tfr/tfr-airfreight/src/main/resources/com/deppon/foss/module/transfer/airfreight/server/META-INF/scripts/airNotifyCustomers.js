/***
 * @author 200968 2015-08-19
 * page:空运客户通知
 */
 
 airfreight.EXCEPTIONREASON = 'PKP_NOTICE_EXCEPTION_REASON';  //异常原因词条
 airfreight.INVOICETYPE = 'PKP_RECEIPT_INVOICE_TYPE'; //收货发票类型
 
airfreight.airNotifyCustomersIndex.getInStockTimeStart = function(date, day) {	
	var d, s, t, t2;	
	var MinMilli = 1000 * 60;	
	var HrMilli = MinMilli * 60;	
	var DyMilli = HrMilli * 24;	
	t = Date.parse(date);	
	t2 =  new Date(t+day*DyMilli);	
	t2.setHours(0);	
	t2.setMinutes(0);	
	t2.setSeconds(0);	
	t2.setMilliseconds(0);		
	return t2;	
};	
	
airfreight.airNotifyCustomersIndex.getInStockTimeEnd = function(date, day) {	
	var d, s, t, t2;	
	var MinMilli = 1000 * 60;	
	var HrMilli = MinMilli * 60;	
	var DyMilli = HrMilli * 24;	
	t = Date.parse(date);	
	t2 =  new Date(t+day*DyMilli);	
	t2.setHours(23);	
	t2.setMinutes(59);	
	t2.setSeconds(59);	
	t2.setMilliseconds(0);		
	return t2;	
};	

airfreight.airNotifyCustomersIndex.compareDate = function(date1, date2) {	
	var _date1 = airfreight.airNotifyCustomersIndex.getInStockTimeStart(date1,0),
	_date2 = airfreight.airNotifyCustomersIndex.getInStockTimeStart(date2,0);
	var diff = _date1.getTime() - _date2.getTime();
	if(diff == 0){
		return 0
	} else if(diff < 0){
		return -1;
	} else {
		return 1;
	}
};

 airfreight.NOTIFY_TYPE = 'PKP_NOTIFY_CUSTOMER_TYPE'; // 通知方式词条
 airfreight.NOTIFY_STATUS = 'PKP_NOTIFY_CUSTOMER_STATUS'; // 通知结果词条
 airfreight.PICKUPGOODSHIGHWAYS = 'PICKUPGOODSHIGHWAYS'; // 派送方式词条
 airfreight.PAYMENTMODE = 'SETTLEMENT__PAYMENT_TYPE'; // 付款方式词条
 airfreight.CLEARING_TYPE = 'CLEARING_TYPE'; // 结算方式词条
 airfreight.FAILURE = 'FAILURE'; // 通知状态-失败
 airfreight.SUCCESS = 'SUCCESS'; // 通知状态-成功
 airfreight.ARRIVED = 'ARRIVED'; // 车辆到达
 airfreight.UNLOADED = 'UNLOADED'; // 已卸车
 airfreight.FC = 'FC'; // 付款方式-到付
 airfreight.CLEARING_TYPE_MONTH = 'MONTH_END'; // 客户付款方式-月结
 airfreight.CLEARING_TYPE_HALF_MONTH = 'HALF_MONTH'; // 付款方式-半月结
 airfreight.SELECT_TYPE = ''; // 查询方式
 airfreight.PAYMENT_TYPE__ONLINE = 'OL';  // 网上支付
 airfreight.PCC_PROV = '1';  // P-C-C控件定义省
 airfreight.PCC_CITY = '2';  // P-C-C控件定义市
 airfreight.PCC_DIST = '3';  // P-C-C控件定义区
 
var provCode = FossUserContext. getCurrentDept().provCode;
var cityCode = FossUserContext. getCurrentDept().cityCode;
 
// 根据免费库存天数，获取在库天数的store
airfreight.airNotifyCustomersIndex.getStorageDayStore = function() {
	var data = new Array();
	if (airfreight.airNotifyCustomersIndex.warehouseFreeSafeDataNum > 0) {
		data.unshift({'valueCode': '-' + airfreight.airNotifyCustomersIndex.warehouseFreeSafeDataNum, 'valueName': airfreight.airNotifyCustomersIndex.warehouseFreeSafeDataNum + airfreight.airNotifyCustomersIndex.i18n('pkp.airfreight.airNotifyCustomersIndex.day.over')});
	}
	for (var len = airfreight.airNotifyCustomersIndex.warehouseFreeSafeDataNum; len > 0; len --) {
		data.unshift({'valueCode': len, 'valueName': len + airfreight.airNotifyCustomersIndex.i18n('pkp.airfreight.airNotifyCustomersIndex.day')});
	}
	data.unshift({'valueCode': '', 'valueName': "全部"});
	var json={
		fields:['valueCode','valueName'],
	    data : data
	};
	return Ext.create('Ext.data.Store',json);
}

var toPayAmountHidden;

Ext.define('airfreight.airNotifyCustomersIndex.tipPanel', {
	extend: 'Ext.panel.Panel',
	cls: 'autoHeight',
	bodyCls: 'autoHeight',
	//回调方法
	bindData : function(value){
		this.update(value);
	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

//查询条件的model 200968 2015-08-22 Bingo
Ext.define('Foss.airfreight.model.NoticeCustomer', {
	extend : 'Ext.data.Model',
	fields : [
		{name : 'noticeResult' , type: 'string'}, /** 通知状态 */
		{name : 'waybillNo', type: 'string'}, /** 运单号 */
		{name : 'arriveGoodsQty', type : 'int'}, /** 到达件数 */
		{name : 'receiveCustomerCode', type: 'string'}, /** 收货人姓名 */
		{name : 'receiveCustomerName', type: 'string'}, /** 收货人姓名 */
		{name : 'receiveCustomerPhone', type: 'string'}, /** 收货人电话 */
		{name : 'receiveCustomerMobilephone', type: 'string'}, /** 收货人手机 */
/*		{name : 'receiveMethod',
			
		}, *//** 派送方式  提货方式 */
		{name : 'receiveMethod', type: 'string'}, /** 派送方式 */
		{name : 'customerPickupOrgCode', type: 'string'}, /** 送货地址 */
		{name : 'customerPickupOrgName', type: 'string'}, /** 送货地址  提货网点 */
		//{name : 'arriveTime', convert:dateConvert}, /** 到达时间 */
		{name: 'arriveTime',type:'date',
			convert: function(value) {
				if (value != null) {
					var date = new Date(value);
					return Ext.Date.format(date,'Y-m-d H:i:s');
				} else {
					return null;
				}
			}
		},
		{name : 'goodsQtyTotal', type : 'int'}, /** 开单件数 */
		{name : 'arriveGoodsQty', type : 'int'}, 
		{name : 'destOrgCode', type: 'string'}, 
		{name : 'dedtOrgName', type: 'string'},
		{name : 'receiveOrgName', type: 'string'},
		{name : 'productCode', type: 'string'},
		/*{name : 'productCode',
			convert:function(value) {
				return Foss.airfreight.ProductData.rendererSubmitToDisplay(value);
			}
		}, *//** 运输性质 */
		{name : 'productName', type: 'string'},
		{name : 'noticeType', type: 'string',
			convert: function(value){
				return "短信通知";
			}
		},//通知方式
		//{name : 'paidMethod', type: 'string'},//付款方式
		
		{name : 'paidMethod',
			convert:function(value) {
				return FossDataDictionary.rendererSubmitToDisplay(value, airfreight.PAYMENTMODE);
			}
		}, /** 付款方式 */
		{name : 'transportationRemark', type: 'string'},//储运事项
		{name : 'toPayAmount', type: 'string'},//到付款
		{name : 'goodsSize', type: 'string'},//尺寸(厘米)
		{name : 'codAmount', type: 'string'},//代收货款
		{name : 'goodsPackage', type: 'string'},//包装
		{name : 'deliveryCustomerMobilephone', type: 'string'},//发货人手机
		{name : 'deliveryCustomerPhone', type: 'string'},//发货人电话
		{name : 'deliveryCustomerName', type: 'string'},//发货人
		{name : 'insuranceFee', type: 'string'},//保价费
		{name : 'goodsVolumeTotal', type: 'string'},//体积(立方米)
		{name : 'goodsWeightTotal', type: 'string'},//重量(千克)
		{name : 'insuranceAmount', type: 'string'},//货物价值
		{name : 'otherFee', type: 'string'}, //其他费用
		{name : 'deliveryGoodsFee', type: 'string'},//送货费
		{name : 'flightNumberType', 	
			convert: function(value){
				return FossDataDictionary.rendererSubmitToDisplay(value,'AIR_FLIGHT_TYPE');
			}
		},//航班类型
		{name : 'receiveCustomerAddressSum', type: 'string'},//送货地址
		{name : 'transportFee', type: 'string'},//运费
		{name : 'freightMethod', type: 'string',
			convert: function (v) {
	            if (v == 'HDP') {
	                return "合大票";
	            } else {
	                return "单独开单";
	            }
	        }
		},//合票方式
		{name : 'goodsName', type: 'string'},
		{name : 'noticeContent', type: 'string'},
		{name: 'notificationTime',type:'date',
			convert: function(value) {
				if (value != null) {
					var date = new Date(value);
					return Ext.Date.format(date,'Y-m-d H:i:s');
				} else {
					return null;
				}
			}
		}
	]
});


//通知信息 model 200968 Bingo

Ext.define('Foss.airfreight.model.NoticeInfo', {
	extend : 'Ext.data.Model',
	fields : [
		{name: 'waybillNo',type:'string'},
		{name: 'receiveCustomerName',type:'string'},
		{name: 'receiveCustomerMobilephone',type:'string'},
		{name: 'noticeType',type:'string'},
		{name: 'noticeContent',type:'string'},
		{name: 'daysPickUp',type:'string'}
	]
});


Ext.define('Foss.airfreight.model.WarehouseCharge', {
	extend : 'Ext.data.Model',
	fields : [
		{name : 'waybillNo'}, 
		{name : 'storageDay',type : 'number'}, 
		{name : 'overdueDay',type : 'number'}, 
		{name : 'storageCharge',type : 'number'}, 
		{name : 'exceptionType'}, 
		{name : 'exceptionNotes'}]
});

//创建一个查询枚举store
Ext.define('Foss.airfreight.model.QueryNoticeInfo',{
	extend: 'Ext.data.Model',
	fields: [
		{name: 'code',  type: 'string'},
		{name: 'name',  type: 'string'}
	],
	//定义一个代理对象
	proxy: {
		//代理的类型为内存代理
		type: 'memory',
		//定义一个读取器
		reader: {
			//以JSON的方式读取
			type: 'json',
			//定义读取JSON数据的根对象
			root: 'items'
		}
	}
});

/**
 * 为对象属性添加前缀
 * @param obj 传入对象
 * @param prevName 前缀名称
 * @returns
 */
function addPrev(obj, prevName){
  if (Ext.isObject(obj)){
    for (var attr in obj){
      var keyName = prevName + '.' + attr;
      obj[keyName] = obj[attr];
      delete obj[attr];
    }
  } 
}

var addressGrid = new Array();


//Bingo
Ext.define('Foss.airfreight.store.NoticeCustomer', {
	extend : 'Ext.data.Store',
	model : 'Foss.airfreight.model.NoticeCustomer',
	pageSize : 50,
	proxy : {
		type : 'ajax',
		actionMethods : 'POST',
		url:airfreight.realPath('queryStayAirNotificationBill.action'),
		reader : {
			type : 'json',
			root : 'vo.airNotifyCustomersDtoList',
			totalProperty : 'totalCount',
			successProperty : 'success'
		}
	},
	listeners : {
		beforeload : function(store, operation, eOpts) {
			var myForm = airfreight.airNotifyCustomersIndex.queryform.getForm(), waybillNo = myForm.getValues().waybillNo;
			// 验证运单号输入的行数
			if (!Ext.isEmpty(waybillNo)) {
				var arrayWaybillNo = waybillNo.split('\n');
				if (arrayWaybillNo.length > 50) {
					Ext.ux.Toast.msg("提示", "运单号为8到9位数字，以回车输入，最多输入50个运单号。", 'error', 3000); // '起止日期相隔不能超过30天！'
					return false;	
				}
				for (var i = 0; i < arrayWaybillNo.length; i++) {
					if (Ext.isEmpty(arrayWaybillNo[i])) {
						Ext.ux.Toast.msg("提示", "运单号为8到9位数字，以回车输入，最多输入50个运单号。", 'error', 3000); // '起止日期相隔不能超过30天！'
						return false;	
					}
				}
			}
	
			if(!myForm.isValid()){
				return false;
			}
		
			//执行查询，首先load查询条件，为全局变量，在查询条件的FORM创建时生成	
			var queryParams = airfreight.airNotifyCustomersIndex.queryform.getValues();
			Ext.apply(operation, {	
				params:{	
					'vo.airNotifyCustomersDto.waybillNo':queryParams.waybillNo,  //运单号	
					'vo.airNotifyCustomersDto.airWaybillNo':queryParams.airWaybillNo,// 航空正单号	
					'vo.airNotifyCustomersDto.receiveMethod':queryParams.receiveMethod,		//派送方式
					'vo.airNotifyCustomersDto.orgCode':queryParams.orgCode, //配载部门
					'vo.airNotifyCustomersDto.noticeResult':queryParams.noticeResult,	//通知结果
					'vo.airNotifyCustomersDto.arriveTimeFrom':queryParams.arriveTimeFrom,	//到达时间开始
					'vo.airNotifyCustomersDto.arriveTimeTo':queryParams.arriveTimeTo		//到达时间结束
					}	
			});		
		},
		load : function(store, records, successful,eOpts) {
			// 根据后台传入的值动态显示列
			var columns = Ext.getCmp('Foss_airfreight_grid_NoticeCustomerGrid_Id').columns;
		        /*运单信息*/
				transportInfoForm = Ext.getCmp('Foss.airfreight.airNotifyCustomersIndex.TransportInfoFormPanel_id'),
				/*通知信息*/
				noticeInfoForm   = Ext.getCmp('Foss.airfreight.airNotifyCustomersIndex.NoticeInfoFormPanel_Id'),
			
			transportInfoForm.getForm().reset();
			noticeInfoForm.getForm().reset();
			
			var grid=Ext.getCmp('Foss_airfreight_grid_NoticeCustomerGrid_Id');
			if(store.data.length>0)
			{
				grid.getSelectionModel().select(0);
			}
	}
}});

//提货方式集合
Ext.define('Foss.airfreight.store.ReceiveMethodStore',{
	extend: 'Ext.data.Store',
	fields: [
		{name: 'valueCode',  type: 'string'},
		{name: 'valueName',  type: 'string'}
	],
	data: {
		'items':[
			{'valueCode':'','valueName':"全部"},// '全部'
			{'valueCode':'DELIVER','valueName':"送货"},// '送货'
			{'valueCode':'PICKUP','valueName':"自提"}//'自提'
		]
	},
	//定义一个代理对象
	proxy: {
		//代理的类型为内存代理
		type: 'memory',
		//定义一个读取器
		reader: {
			//以JSON的方式读取
			type: 'json',
			//定义读取JSON数据的根对象
			root: 'items'
		}
	}
});


//定义航空公司信息的model
Ext.define('Foss.airfreight.queryAirlinesModel',{
	extend:'Ext.data.Model',
	fields:[{name:'code', type:'string'},
	        {name:'simpleName', type:'string'},
	        {name:'name', type:'string'},
	        {name:'logo', type:'string'},
	        {name:'prifixName', type:'string'}
	        ]
});

//定义查询航空公司信息的store
Ext.define('Foss.airfreight.queryAirlinesStore',{
	extend:'Ext.data.Store',
	autoLoad: true,
	model: 'Foss.airfreight.queryAirlinesModel',
	proxy: {
		//代理的类型为内存代理
		type: 'ajax',
		actionMethods: 'POST',
		url: airfreight.realPath('queryAllAirlines.action'),
		//定义一个读取器
		reader: {
			//以JSON的方式读取
			type: 'json',
			//定义读取JSON数据的根对象
			root: 'printAirWaybillTagVO.airlinesEntityList'
		}
	}
});


//查询条件 200968 Bingo
Ext.define('Foss.airfreight.form.NoticeCustomerSearch', {
	extend : 'Ext.form.Panel',
	title : "查询条件",
	collapsible : true,
	animCollapse : true,
	defaults : {
		margin : '0 2 2 0',
		labelWidth : 60
	},
	cls : 'autoHeight',
	bodyCls : 'autoHeight',
	defaultType : 'textfield',
	layout : 'column',
	frame : true,
	//id : 'Foss_airfreight_form_noticeCustomerSearch_Id',
	items : [{
		name : 'airWaybillNo',
		xtype : 'textarea',
		fieldLabel : "正单号",
		columnWidth : .2,
		height: 140,
		emptyText : "请输入一个正单号",
		listeners:{
			blur:function(v,op){
				
				var form = airfreight.airNotifyCustomersIndex.queryform.getForm(); 
				form.findField('waybillNo').setDisabled(false);
				
				if(Ext.isEmpty(v.getValue())){
				}else{
					var form = airfreight.airNotifyCustomersIndex.queryform.getForm(); 
					form.findField('waybillNo').setDisabled(true);
				}
			}
		}
	},{
		name : 'waybillNo',
		xtype : 'textarea',
		fieldLabel : "运单号",  
		columnWidth : .2,
		emptyText : "运单号为8到9位数字，以回车输入，最多输入50个运单号。",
		regex : /^([0-9]{8,10}\n?)+$/i,
		regexText : "运单号为8到9位数字，以回车输入，最多输入50个运单号。",
		listeners:{
			blur:function(v,op){
				
				var form = airfreight.airNotifyCustomersIndex.queryform.getForm(); 
				form.findField('airWaybillNo').setDisabled(false);
				
				if(Ext.isEmpty(v.getValue())){
				}else{
					var form = airfreight.airNotifyCustomersIndex.queryform.getForm(); 
					form.findField('airWaybillNo').setDisabled(true);
				}
			}
		}
	},{
		xtype:'dynamicorgcombselector',
		fieldLabel:"配载部门",
		labelWidth: 100,
		name:'orgCode',     //配载部门
		type:'ORG',
		readOnly:true,
		doAirDispatch:'Y',
		allowBlank:false,
		columnWidth:.6
	},FossDataDictionary.getDataDictionaryCombo('AIR_NOTIFY_CUSTOMERS_TYPE',{
		name: 'noticeResult',
		fieldLabel:"空运通知情况",
		queryMode: 'local',
		forceSelection: true,
		editable: true,
		value: '',
		columnWidth: .25
		},{
			'valueCode':'',
			'valueName': "全部"
	}),
	/*{
		xtype:'combo',
		displayField:'valueName',
		valueField:'valueCode',
		queryMode:'local',
		triggerAction:'all',
		labelWidth: 100,
		value : '',
		editable:false,
		name : 'noticeResult',
		fieldLabel : "空运通知情况",
		columnWidth : .3,
		store : FossDataDictionary.getDataDictionaryStore(airfreight.NOTIFY_STATUS, null, {
			'valueCode': '',
            'valueName': "全部"
		})
	},*/{
		xtype: 'container',
		border : false,
		columnWidth:.04,
		html: '&nbsp;'
	},	{
		xtype:'combo',
		displayField:'valueName',
		valueField:'valueCode',
		queryMode:'local',
		triggerAction:'all',
		value : '',
		editable:false,
		name : 'receiveMethod',
		fieldLabel : "派送方式",
		columnWidth : .2,
		store : Ext.create('Foss.airfreight.store.ReceiveMethodStore')
	},
	{
		xtype : 'rangeDateField',
		fieldLabel : "到达代理处时间",
		labelWidth: 100,
		margin:'0 0 0 0',
		fieldId : 'Foss_airfreight_notifyCustomer_inStockTime_Id',
		dateType : 'datetimefield_date97',
		fromName : 'arriveTimeFrom',
		toName : 'arriveTimeTo',
		fromValue: Ext.Date.format(airfreight.airNotifyCustomersIndex.getInStockTimeStart(new Date(),0),'Y-m-d H:i:s'),	
		toValue: Ext.Date.format(airfreight.airNotifyCustomersIndex.getInStockTimeEnd(new Date(),0),'Y-m-d H:i:s'),
		editable:false,
		dateRange:7,
		columnWidth : .5
	},   
	{
		border: 1,
		xtype:'container',
		columnWidth:1,
		defaultType:'button',
		layout:'column',
		items:[{
			text : "重置",
			columnWidth:.08,
			handler : function() {
				var myForm = this.up('form').getForm();
				myForm.reset();
				myForm.findField('arriveTimeFrom').setValue(Ext.Date.format(airfreight.airNotifyCustomersIndex.getInStockTimeStart(new Date(),0),'Y-m-d H:i:s'));
				myForm.findField('arriveTimeTo').setValue(Ext.Date.format(airfreight.airNotifyCustomersIndex.getInStockTimeEnd(new Date(),0),'Y-m-d H:i:s'));
				

				var cmbOrgCode = myForm.findField('orgCode');
				if(airfreight.airNotifyCustomersIndex.dept.airDispatch == 'Y'){
					cmbOrgCode.setValue(airfreight.airNotifyCustomersIndex.deptCode);
					cmbOrgCode.getStore().load({params:{'commonOrgVo.name' : airfreight.airNotifyCustomersIndex.deptName}});
				}
			
			}
		}, {
			xtype: 'container',
			border : false,
			columnWidth:.84,
			html: '&nbsp;'
		}, {
			text : "查询",
			//hidden:!airfreight.airNotifyCustomersIndex.isPermission('notifycustomerindex/notifycustomerindexquerybutton'),
			columnWidth:.08,
			cls : 'yellow_button',
			plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin', {
				seconds: 3
			}),
			handler : function() {
				//搜索结果
				Ext.getCmp('Foss_airfreight_grid_NoticeCustomerGrid_Id').getPagingToolbar().moveFirst();
			}
		}]
	}],
	
	listeners : {
		
		render : function(panel,text){
			panel.getForm().findField('waybillNo').setHeight(140);
			
			var array = {airDispatchVo:{deptCode:FossUserContext.getCurrentDept().code}};
			Ext.Ajax.request({
				url : airfreight.realPath('queryAirDispatch.action'), //airDispatchVo
				jsonData:array,
				success : function(response) {
					var json = Ext.decode(response.responseText);
					var dept = json.airDispatchVo.orgAdministrativeInfoEntity;
					airfreight.airNotifyCustomersIndex.dept = dept;
					airfreight.airNotifyCustomersIndex.deptCode = dept.code;
					airfreight.airNotifyCustomersIndex.deptName = dept.name;
					var cmbOrgCode = panel.getForm().findField('orgCode');
					if(dept.airDispatch == 'Y'){
						cmbOrgCode.getStore().load({params:{'commonOrgVo.name' : airfreight.airNotifyCustomersIndex.deptName}});
						cmbOrgCode.setValue(airfreight.airNotifyCustomersIndex.deptCode);
					}
				}
			});
			
		}
	}
});

airfreight.airNotifyCustomersIndex.dept = '';
airfreight.airNotifyCustomersIndex.deptCode = '';
airfreight.airNotifyCustomersIndex.deptName = '';
//通知明细表格
Ext.define('Foss.airfreight.grid.NoticeCustomerGrid',{
	extend:'Ext.grid.Panel',
	title : "搜索结果",
	cls : 'autoHeight',
	bodyCls : 'autoHeight',
	frame : true,
	emptyText: "查询结果为空",
	// 增加滚动条
	autoScroll : false,
	collapsible : true,
	//动画收缩
	animCollapse: true,
	//高
	height: 615,
	store: null, 
	selModel : Ext.create('Ext.selection.CheckboxModel',{
		checkOnly: false //限制只有点击checkBox后才能选中行
	}),
	viewConfig : {
		// 单元格可复制
		enableTextSelection: true,
		//显示重复样式，不用隔行显示
		stripeRows : false,
		getRowClass : function(record, rowIndex, rp, ds) {
			// 取消未通知的颜色显示 2013-2-28 15:59:57
			
			 goodsQtyTotal = record.get('goodsQtyTotal'),
			 arriveGoodsQty = record.get('arriveGoodsQty');
			 customerPickupOrgName = record.get('customerPickupOrgName');
			 dedtOrgName = record.get('dedtOrgName');
			 
			 if(customerPickupOrgName != dedtOrgName){
				 return 'predeliver_notice_customer_row_purple';
			 }
			 //predeliver_notice_customer_row_pink
			if (goodsQtyTotal != arriveGoodsQty) {
				// 开单库存件数不一致 - 粉色
				return 'predeliver_notice_customer_row_pink';
			}
		}
	},
	tbar : [{
		xtype : 'button',
		text : "批量通知",
		//hidden:!airfreight.airNotifyCustomersIndex.isPermission('notifycustomerindex/notifycustomerindexlistbutton'),
		plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin', {
			seconds: 3
		}),
		width:68,
		handler : function() {
			    var waybillNos = '', 
			    array = new Array(), 
			    airNotifyCustomersSmsInfo = '',
				records = Ext.getCmp('Foss_airfreight_grid_NoticeCustomerGrid_Id').getSelectionModel().getSelection(),
				hasNoMobile = false, 
				isNoticing = false, 
				isSuccess = false,
				isStorageCharge=false, 
				isTodaySuccess = false, 
				isBatchStowageNOtHere=false;
			for (var i = 0; i < records.length; i++) {
				// 批量通知时，没有手机号码，退出本次循环
				if (Ext.isEmpty(records[i].get("receiveCustomerMobilephone"))) {
					hasNoMobile = true;
					continue;
				}
				
				// 通知成功且不是电话通知，今天不能再次通知
				if(records[i].data['noticeResult'] == 'SUCCESS' 
					&& airfreight.airNotifyCustomersIndex.compareDate(records[i].data['notificationTime'],new Date()) == 0){
					isSuccess = true;
					continue;
				}
				
				/**
				 * 说明:只能在8:00-21:00发送语音与短信通知
				 */
			/*	var dateStart = new Date();
				var dateEnd = new Date();
				dateStart.setHours(8, 0, 0, 0);
				dateEnd.setHours(9, 0, 0, 0);
				
				if((new Date).getTime()-dateStart.getTime() < 0
					|| (new Date).getTime()-dateEnd.getTime() >0 ){
	                isNoticing = true;
	                continue;
				}*/
				
				//过滤到开单件数与到达件数不一致的运单,不进行通知
				if(records[i].get('goodsQtyTotal') > records[i].get('arriveGoodsQty')){
					isBatchStowageNOtHere = true;
					continue;
				}
				
				waybillNos += records[i].get("waybillNo") + ",";
				
				taskStatus = records[i].get("taskStatus");
				
				airNotifyCustomersSmsInfo = {'waybillNo' : records[i].data.waybillNo, 'arriveGoodsQty' : records[i].data.arriveGoodsQty};
				
				array.push(airNotifyCustomersSmsInfo);
			}
			if (waybillNos == "") {
				// 因为所选择的运单号没有手机号被过滤
				if (records.length > 0 && isSuccess == true) {
					Ext.ux.Toast.msg("提示"/*提示*/, "您所选择的运单当天已经短信通知成功，无需再次批量通知", 'error', 1000);//'您所选择的运单当天已经语音、短信通知成功，无需再次批量通知'
					return;
				} else if (records.length > 0 && isNoticing == true) {
					Ext.ux.Toast.msg("提示"/*提示*/, "说明:只能在8:00-21:00发送语音与短信通知", 'error', 1000);//'你选择的运单正在通知中，不能多次通知'
					return;
				} else if (records.length > 0 && hasNoMobile == true) {
					Ext.ux.Toast.msg("提示"/*提示*/, '您选择的运单没有手机号，无法进行批量通知。', 'error', 1000);//'您选择的运单没有手机号，无法进行批量通知。'
					return;
				} else if (records.length > 0 && isBatchStowageNOtHere == true){
					Ext.ux.Toast.msg("提示"/*提示*/, "存有【分批配载未到齐】的运单，请货物到齐后再短信通知", 'error', 1000);//'存有【分批配载未到齐】的运单，请货物到齐后再短信通知'
					return;
				} else {
					Ext.ux.Toast.msg("提示"/*提示*/, "请选择运单。", 'error', 1000);//'请选择运单。'
					return;
				}
			} else {
				if (records.length > 0 && isBatchStowageNOtHere == true){
					Ext.ux.Toast.msg("提示"/*提示*/, "存有【分批配载未到齐】的运单，请货物到齐后再短信通知", 'error', 1000);//'存有【分批配载未到齐】的运单，请货物到齐后再短信通知'
				}
			}
			var newVo = {  // vo.airNotifyCustomersDto
				'vo': {
					'airNotifyCustomersDto' : {
						'waybillNos': waybillNos,
						'airNotifyCustomersSmsInfoList' : array
					}
				}
			}
			Ext.Ajax.request({
				url:airfreight.realPath('airBatchNotifyList.action'),
				jsonData: newVo,
				success: function(response){
					var result = Ext.decode(response.responseText);
					airfreight.airNotifyCustomersIndex.initBatchNoticeWinForm(result,records);
				},
				exception: function(response) {
	                var json = Ext.decode(response.responseText);
	                Ext.ux.Toast.msg("批量通知失败", json.message, 'error', 2000);//'批量通知失败'
	            }
			});
		}
	}],
	columns : [
	    {
			header: '序号',//序列号
			xtype:'rownumberer',
			width:50
		   },
		{text : "状态",width : 50,dataIndex : 'noticeResult', 
			renderer : function(value, cellmeta, record, rowIndex, columnIndex, store){
				var result = '';
			
  //通知成功    通知失败      录入到达件数与开单件数不一致       运单提货网点与正单到达网点不一致				
				 if(value == 'SUCCESS') {
					//通知成功 
					result = '<div class="foss_icons_pkp_noticeOk"></div>';
				} else if(value == 'FAILURE') {
					//通知失败
					result = '<div class="foss_icons_pkp_noticeError"></div>';
				}
				//处理类型:"PDA_ACTIVE"--PDA已补录 ，"PC_ACTIVE"--暂存已开单
				var pendingType = record.get('pendingType');
				 //录入到达件数与开单件数不一致
    		  	if(record.data.isPay == 'N'){
    			  	result = result + '<div class="foss_icons_pkp_3daysNoPicking"></div>';
    		  	}
    		  	//运单提货网点与正单到达网点不一致
                if (pendingType != null &&pendingType!='PC_ACTIVE') {
                	result = result + '<div class="foss_icons_pkp_flagred"style="width:18px;height:18px;"></div>';
                }
				return result;
			}
		},
		{text : "运单号",width : 85,dataIndex : 'waybillNo', xtype : 'ellipsiscolumn'},
		{text : "收货人姓名",width : 75,dataIndex : 'receiveCustomerName', xtype : 'ellipsiscolumn'},
		{text : "收货人电话",width : 75,dataIndex : 'receiveCustomerPhone', xtype : 'ellipsiscolumn'},
		{text : "收货人手机",width : 90,dataIndex : 'receiveCustomerMobilephone', xtype : 'ellipsiscolumn'},
		{text : "提货方式",width : 94,dataIndex : 'receiveMethod',xtype : 'ellipsiscolumn'},
		{text : "提货网点",width : 94,dataIndex : 'customerPickupOrgName',xtype : 'ellipsiscolumn'},
		{text : "到达代理处时间",width : 130, dataIndex : 'arriveTime',
			renderer : function(value) {
				if (value != null) {
					var date = Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
					return date;
				} else {
					return null;
				}
			} 
		},
		{text : "开单件数",width : 40,xtype : 'numbercolumn',dataIndex : 'goodsQtyTotal',format:'0'},
		{text : "录入到达件数",width : 40,xtype : 'numbercolumn',dataIndex : 'arriveGoodsQty',format:'0'},
		{text : "正单到达网点",width : 94,dataIndex : 'dedtOrgName',xtype : 'ellipsiscolumn'}
	],
	pagingToolbar : null,
  	getPagingToolbar : function() {
  		if (this.pagingToolbar == null) {
  			this.pagingToolbar = Ext.create('Deppon.StandardPaging', {
  				store : this.store,
  				maximumSize : 200,
  				layout : 'column',
  				//plugins: 'pagesizeplugin',
  				plugins : Ext.create('Deppon.ux.PageSizePlugin', {
  					sizeList : [['50', 50], ['100', 100], ['200', 200]]
  				}),
				displayInfo: true
  			});
  		}
  		return this.pagingToolbar;
  	},
  	printWindow: null,
    	getPrintWindow: function(){
    		var me = this;
    		if(this.printWindow==null){
    			me.printWindow = Ext.create('Foss.printArriveSheet.printWindow',me);
    		}
    		return me.printWindow;
    	},
	 listeners: {
        select:function(view, record,item, index){
			var transportInfoForm = Ext.getCmp('Foss.airfreight.airNotifyCustomersIndex.TransportInfoFormPanel_id'),
				noticeInfoForm   = Ext.getCmp('Foss.airfreight.airNotifyCustomersIndex.NoticeInfoFormPanel_Id'),
			    saveButton = Ext.getCmp('Foss_airfreight_notifyCustomer_NoticeInfoFormPanel_saveButten_Id');
			var recordsModel = Ext.getCmp('Foss_airfreight_grid_NoticeCustomerGrid_Id').getSelectionModel(),
				records=recordsModel.getSelection();
			if(records.length==2){
				// 设置grid的record
				 noticeInfoForm.getForm().reset();
				 transportInfoForm.getForm().reset();
				 saveButton.setDisabled(true);
				 return;
			}
			if(records.length>=3){
				 return;
			}
			selectRecord = records[0].data;
			// 设置grid的record
		    noticeInfoForm.getForm().reset();
		    transportInfoForm.getForm().reset();
		    saveButton.setDisabled(true);
		// 绑定表格数据到表单上
		Ext.Ajax.request({
			url:airfreight.realPath('queryAirNotifyCustomersWaybillInfo.action'),
			params: {
				'vo.airNotifyCustomersDto.waybillNo': record.get('waybillNo'),
				'vo.airNotifyCustomersDto.arriveGoodsQty': record.get('arriveGoodsQty'),
				'vo.airNotifyCustomersDto.goodsQtyTotal': record.get('goodsQtyTotal'),
				'vo.airNotifyCustomersDto.dedtOrgName': record.get('dedtOrgName'),
				'vo.airNotifyCustomersDto.arriveTime': record.get('arriveTime')
			},
			success: function(response){
				var result = Ext.decode(response.responseText),
					 model = Ext.ModelManager.create(result.vo.airNotifyCustomersDto,'Foss.airfreight.model.NoticeCustomer'),
					 transportInfoFormT = transportInfoForm.getForm(),
					 noticeInfoFormT = noticeInfoForm.getForm(),
					 paymentTypeGroup = noticeInfoFormT.findField('paymentTypeName');
					 //toPayAmount = noticeInfoFormT.findField('toPayAmount').getValue();
				transportInfoFormT.loadRecord(model);
				noticeInfoFormT.loadRecord(model);
				saveButton.setDisabled(false);
				Ext.suspendLayouts();
				//noticeInfoForm.viewNoticeTypeInfo(noticeInfoFormT);
				
				
				Ext.resumeLayouts(true);
			}
		});
		}
	},
  	constructor : function(config) {
  		var me = this,
			cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.airfreight.store.NoticeCustomer');
		me.bbar = me.getPagingToolbar();
		me.callParent([cfg]);
	}
});

airfreight.airNotifyCustomersIndex.initBatchNoticeWinForm = function(data,gridRecords) {
	if (Ext.getCmp('Foss_airfreight_window_batchNotice_Id')) {
		Ext.getCmp('Foss_airfreight_window_batchNotice_Id').show();
		return;
	}
	var containArray = new Array();
	Ext.create('Ext.window.Window', {
		id : 'Foss_airfreight_window_batchNotice_Id',
		width : 1000,
		title : "批量通知",//批量通知
		modal:true,
		// 动态进行通知内容赋值
		changeNotice : function(_this, newValue, oldValue, eOpts) {
			// 预计提货时间
			if (_this.name == 'daysPickUp') {
				var daysPickUpH = Ext.getCmp('Foss_airfreight_daysPickUpH_Id'), newValue = _this.getValue();
				// 判断输入小时，如果大于99，不处理
				if (newValue > 99) {
					return;
				}
				// 判断现在输入的值和未变更前的值是否相等
				
				if (newValue == null) {
					newValue = '';
				}
				if (daysPickUpH.getValue() == newValue) {
					return;
				}
				daysPickUpH.setValue(newValue);
			}
			var noticeType = Ext.getCmp('Foss_airfreight_noticeType_Id').getValue().noticeType,
				daysPickUp = Ext.getCmp('Foss_airfreight_daysPickUp_Id').getValue(), 
				array = new Array();
			
			var store = Ext.data.StoreManager.lookup('Foss_airfreight_model_BatchNoticeID'),
				batchNoticeStoreItems = store.data.items, 
				waybillNos = '';
			for (var i = 0; i < batchNoticeStoreItems.length; i++) {
				waybillNos +=  batchNoticeStoreItems[i].data.waybillNo  + ",";
				airNotifyCustomersSmsInfo = {'waybillNo' : batchNoticeStoreItems[i].data.waybillNo, 'arriveGoodsQty' : batchNoticeStoreItems[i].data.arriveGoodsQty};
				array.push(airNotifyCustomersSmsInfo);
			}
			
			airNotifyCustomersSmsInfo = { 'noticeType' : noticeType,'daysPickUp' : daysPickUp};
			var newVo = {
						'vo': {
								'airNotifyCustomersDto' : {
								'airNotifyCustomersSmsInfo' :airNotifyCustomersSmsInfo, 
								'waybillNos' : waybillNos,
								'airNotifyCustomersSmsInfoList' : array
							}
						}
					};
			// 取消多选框选择状态
			Ext.getCmp('Foss_noticeCustomer_batchNotice_ID').getSelectionModel().deselectAll();
			Ext.Ajax.request({
				url:airfreight.realPath('airBatchNotifyList.action'),
				jsonData : newVo,
				success: function(response){
					var result = Ext.decode(response.responseText);
					// 取消多选框选择状态
					Ext.getCmp('Foss_noticeCustomer_batchNotice_ID').getStore().loadData(result.vo.airNotifyCustomersDtoList);
				}
			});
		},
		items : [airfreight.airNotifyCustomersIndex.initBatchNoticeGrid(data.vo.airNotifyCustomersSmsInfoList)],
			buttons : [{
				xtype : 'label',
				text : "说明:只能在"/*'说明:只能在'*/ + data.vo.informationReceiveTimeRange + "发送短信通知",//'发送语音与短信通知'
				width : 250,
				margin: '4 45 0 0'
			}, {
				xtype : 'container',
				border: false,
				html : '<div><font color="#FF0000">' + "注：系统已自动帮您过滤了【通知中】、【今天已通知成功】、【分批配载未到齐】和【客户无手机号】的运单" + '</font></div>'
			},{
				cls : 'yellow_button',
				text : "确定通知",//确定通知
				//hidden:!airfreight.airNotifyCustomersIndex.isPermission('notifycustomerindex/notifycustomerindexnotifybutton'),
				plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin', {
					seconds: 3
				}),
				style : {
					float : 'right'
				},
				handler : function() {
					var records = Ext.getCmp('Foss_noticeCustomer_batchNotice_ID').getSelectionModel().getSelection(),
						voiceArray = new Array(),
						smsArray = new Array();
					if (records.length == 0) {
						Ext.ux.Toast.msg("提示","至少选择一条记录。", 'error', 1000);//'至少选择一条记录。'
						return;
					}
					var array = new Array(),
					    airNotifyCustomersSmsInfo = '',
					    noticeType = Ext.getCmp('Foss_airfreight_noticeType_Id').getValue().noticeType,
						daysPickUp = Ext.getCmp('Foss_airfreight_daysPickUp_Id').getValue();
					var length = gridRecords.length;
					for (var i = 0; i < records.length; i++) {
						// 判断选择的记录有没有手机号码
						if(Ext.isEmpty(records[i].data.receiveCustomerMobilephone)) {
							Ext.ux.Toast.msg("错误", "选择的运单必须有手机号码。", 'error', 2000);//选择的运单必须有手机号码。
							return;
						}
						
		                airNotifyCustomersSmsInfo =
		                {   'waybillNo' : records[i].data.waybillNo,
							'receiveCustomerName' : records[i].data.receiveCustomerName,
							'receiveCustomerMobilephone' : records[i].data.receiveCustomerMobilephone,
							'receiveMethod' : records[i].data.receiveMethod,
							'noticeType' : noticeType, 
							'daysPickUp' : daysPickUp,
							'noticeContent' : records[i].data.noticeContent,
							'arriveGoodsQty' : records[i].data.arriveGoodsQty
							};
						array.push(airNotifyCustomersSmsInfo);
						var j = 0;
						for(j = 0;j < length; j ++){
							if(gridRecords[j].get('waybillNo') == records[i].data.waybillNo){
								containArray.push(gridRecords[j]);
								
								if(gridRecords[j].get('isSmsSuccess') == 'Y' && Ext.getCmp('Foss_noticeCustomer_batchNotice_ID').down('radiogroup').getValue().noticeType == 'SMS_NOTICE'){
									smsArray.push(gridRecords[j].get('waybillNo'));
								}
								break;
							}
						}
						//Ext.Array.remove(gridRecords,gridRecords[j]);
					}
					
					var newVo = {
						'vo': {
							'airNotifyCustomersDto' : {'airNotifyCustomersSmsInfoList' :array}
						}
					};
					
					Ext.Ajax.request({
						url:airfreight.realPath('airBatchNotify.action'),
						jsonData : newVo,
						success: function(response){
							Ext.ux.Toast.msg("提示"/*提示*/,"批量通知成功。", 'ok', 1000);//'批量通知成功。'
							Ext.each(containArray, function(item, index, allItems){
								item.set('noticeResult','SUCCESS');
								item.set('notificationTime',new Date());
								if(Ext.getCmp('Foss_noticeCustomer_batchNotice_ID').down('radiogroup').getValue().noticeType == 'SMS_NOTICE'){
									item.set('isSmsSuccess','Y');
								} else {
									item.set('isVoiceSuccess','Y');
								}
							});
							Ext.getCmp('Foss_airfreight_window_batchNotice_Id').close();
						},
						 exception: function(response) {
			                var json = Ext.decode(response.responseText);
			                Ext.ux.Toast.msg("批量通知失败", json.message, 'error', 3000);//'批量通知失败'
		                }
					});
					
				}
			}],
		constructor: function(config){
			var me = this,
				cfg = Ext.apply({}, config);
			me.callParent([cfg]);
		}
	}).show();
}

airfreight.airNotifyCustomersIndex.initBatchNoticeGrid = function(data) {
	Ext.define('Foss.airfreight.model.BatchNotice', {
		extend : 'Ext.data.Model',
		fields : [
		  {name : 'waybillNo'},
		  {name : 'receiveCustomerName'},
		  {name : 'receiveCustomerMobilephone'},
		  {name : 'deliverType',
			convert:function(value) {
				return FossDataDictionary.rendererSubmitToDisplay(value, airfreight.PICKUPGOODSHIGHWAYS);
			}
		  }, /** 派送方式 */
		  {name : 'transportationRemark'},/*储运事项*/
		  {name : 'noticeContent'}]
		});
	var batchNoticeStore = Ext.create('Ext.data.Store', {
			model : 'Foss.airfreight.model.BatchNotice',
			storeId : 'Foss_airfreight_model_BatchNoticeID',
			data : data
		});
	
	return Ext.create('Ext.grid.Panel', {
		id : 'Foss_noticeCustomer_batchNotice_ID',
		store : batchNoticeStore,
		bodyCls : 'autoHeight',
		autoScroll : false,
		selModel : Ext.create('Ext.selection.CheckboxModel'),
		columns : [{
			text : "运单号",// 运单号
			width : 100,
			dataIndex : 'waybillNo'
		}, {
			text : "收货人",//收货人
			flex : .8,
			dataIndex : 'receiveCustomerName'
		}, {
			text : "手机号",
			width : 120,
			dataIndex : 'receiveCustomerMobilephone'
		}, {
			text : "派送方式",//派送方式
			flex : 1,
			dataIndex : 'deliverType',
			xtype : 'ellipsiscolumn'
		},{
			dataIndex : 'transportationRemark',
			text : "储运事项",
			flex : 1,
			xtype : 'ellipsiscolumn'
		},
		{
			text : "信息内容",//信息内容
			flex : 3.6,
			dataIndex : 'noticeContent',
			xtype : 'ellipsiscolumn'
		}],
		dockedItems: {
			xtype : 'toolbar',
			dock : 'bottom',
			layout : 'column',
			items : [
				{xtype : 'radiogroup' , vertical : true , columnWidth : .4,id : 'Foss_airfreight_noticeType_Id',
				items : [
						/*{boxLabel : "语音通知" , name : 'noticeType' , inputValue : 'VOICE_NOTICE'} , */
						{boxLabel : "短信通知" , name : 'noticeType' , inputValue : 'SMS_NOTICE', checked : true}
					],
				listeners : { 
						change:function(_this, newValue, oldValue, eOpts){
							this.up('window').changeNotice(_this, newValue, oldValue, eOpts);
						}
					}
				}, 
			/*	{xtype : 'checkbox' , name : 'isStorageCharge' , boxLabel : "是否征收保管费",columnWidth : .3, id : 'Foss_airfreight_isStorageCharge_Id', inputValue : 'Y',
					listeners : { //是否征收保管费
						change:function(_this, newValue, oldValue, eOpts){
							this.up('window').changeNotice(_this, newValue, oldValue, eOpts);
						}
					}},*/ 
				{xtype : 'container' ,  layout: 'column',columnWidth : .3,
					items : [
						{xtype : 'label' , text : "预计", margin: '4 0 0 9'} , // 预计
						{xtype : 'numberfield' , name : 'daysPickUp', value:3, width : 30,id : 'Foss_airfreight_daysPickUp_Id',
						hideTrigger : true, maxValue: 99, minValue: 1, maxLength :2, allowDecimals : false, msgTarget: 'qtip',
						listeners : { 
							blur : function (_this, The, eOpts) {
								this.up('window').changeNotice(_this, null, null, eOpts);
							}
						}} , 
						{xtype : 'label',  text : "日内提货"/*小时后提货*/, margin: '4 0 0 0'}
					]
				},
				{name : 'daysPickUpH' , xtype : 'hiddenfield', id : 'Foss_airfreight_daysPickUpH_Id'}]
		}
	});
};

//运单信息  右下二 Bingo 2015-08-28
Ext.define('Foss.airfreight.airNotifyCustomersIndex.TransportInfoFormPanel',{
	extend: 'Ext.form.Panel',
	title : "运单信息",//运单信息
	//收缩
	collapsible: true,
	//是否有框
	//frame:true,
	height:400,
	//动画收缩
	animCollapse: true,
	defaults: {
		margin:'1 1 1 1',
		labelWidth:65,
		readOnly : true
	},
	defaultType : 'textfield',
	//自动收缩高度
	//cls:'autoHeight',
	//bodyCls:'autoHeight',
	layout:'column',
	items : [
		{name : 'waybillNo' ,  fieldLabel : "运单号" ,  columnWidth : .4} ,  
		{name : 'receiveOrgName' , fieldLabel : "始发部门" ,columnWidth : .3 } , 
		{name : 'productName' , fieldLabel : "运输性质",columnWidth : .3} ,
		{name : 'receiveCustomerName' ,labelWidth:75,  fieldLabel : "收货人姓名" , columnWidth : .4} ,   
		{name : 'goodsName' , fieldLabel : "货物名称" ,columnWidth : .3} ,
		{name : 'receiveMethod' , fieldLabel : "提货方式" , columnWidth : .3} ,  
		{name : 'receiveCustomerPhone' ,labelWidth:75,   fieldLabel : "收货人电话" , columnWidth : .4} ,  
		{name : 'arriveTime',labelWidth:100, fieldLabel : "到达代理处时间" ,columnWidth : .3},
		{name : 'customerPickupOrgName' , fieldLabel : "提货网点" , columnWidth : .3} ,  
		{name : 'receiveCustomerMobilephone' , labelWidth:75, fieldLabel : "收货人手机" ,columnWidth : .4} ,
		{name : 'freightMethod' ,  fieldLabel : "合票方式" ,columnWidth : .3} ,
		{name : 'transportFee' ,fieldLabel : "运费" , columnWidth : .3} ,  
		//{name : 'receiveCustomerAddressSum' , fieldLabel : "送货地址" ,columnWidth : .8 ,labelWidth:200} ,  // 送货地址
		{name : 'receiveCustomerAddressSum' , fieldLabel : "送货地址" ,
			tipConfig: {
				cls: 'autoHeight',
				bodyCls: 'autoHeight',
				width: 150,
				//是否随鼠标滑动
				trackMouse: true,
				//普通Form上必须配置tipType(区分普通Form和行展开的Form)
				tipType: 'normal',
				//当值为空的时候，不显示tip
				isShowByData: true,
				tipBodyElement: 'airfreight.airNotifyCustomersIndex.tipPanel'
			},columnWidth : .4 ,height:95,labelWidth:65, xtype : 'textarea'} ,  // 送货地址
		{name : 'flightNumberType' , fieldLabel : "航班类型" , columnWidth : .3} ,  
		{name : 'deliveryGoodsFee' , fieldLabel : "送货费", columnWidth : .3} ,
		{name : 'goodsQtyTotal' ,fieldLabel : "开单件数" , columnWidth : .3} ,  
		{name : 'arriveGoodsQty' ,labelWidth:85,  fieldLabel : "录入到达件数", columnWidth : .3} ,  // 到达件数
		{name : 'otherFee' , fieldLabel : "其他费用" , columnWidth : .3} ,  //其他费用
		//{name : 'arriveGoodsQty' , labelWidth:85, fieldLabel : "录入到达件数" , columnWidth : .3} ,  //'库存件数'
		{name : 'paidMethod' ,labelWidth:75,  fieldLabel : "付款方式" , columnWidth : .3},  //'付款方式'
		{name : 'insuranceAmount' ,fieldLabel : "货物价值" , columnWidth : .3} ,  //'货物价值'
		{name : 'goodsWeightTotal' , labelWidth:70, fieldLabel : "重量(千克)" , columnWidth : .3} , //'重量(千克)'
		{name : 'goodsVolumeTotal', labelWidth:85 , fieldLabel : "体积(立方米)" , columnWidth : .4} ,  //体积(立方米)
		{name : 'insuranceFee' ,labelWidth:85 ,fieldLabel : "保价费" , columnWidth : .3} ,  //  保价费
		{name : 'deliveryCustomerName' ,labelWidth:65 , fieldLabel : "发货人" , columnWidth : .3} ,  //'发货人'
		{name : 'deliveryCustomerPhone' , fieldLabel :'发货人电话' , labelWidth:75, columnWidth : .4} ,  
		{name : 'deliveryCustomerMobilephone' ,labelWidth:75,  fieldLabel :'发货人手机' , labelWidth:75,columnWidth : .3} ,
		{name : 'goodsPackage' , labelWidth:75, fieldLabel : "包装",columnWidth : .3} , //'包装'
		{name : 'codAmount' , labelWidth:75, fieldLabel : "代收货款" ,
			tipConfig: {
				cls: 'autoHeight',
				bodyCls: 'autoHeight',
				width: 150,
				//是否随鼠标滑动
				trackMouse: true,
				//普通Form上必须配置tipType(区分普通Form和行展开的Form)
				tipType: 'normal',
				//当值为空的时候，不显示tip
				isShowByData: true,
				tipBodyElement: 'airfreight.airNotifyCustomersIndex.tipPanel'
			},columnWidth : .4} ,  //'代收货款'
		{name : 'goodsSize', labelWidth:75 , fieldLabel : "尺寸(厘米)" , columnWidth : .3} ,  //'尺寸(厘米)'
		{name : 'toPayAmount' ,labelWidth:75, fieldLabel : "到付款" , columnWidth : .3} ,  //到付金额
		{name : 'transportationRemark' ,labelWidth:75, fieldLabel : "储运事项" ,columnWidth : .4} //储运事项
		//{name : 'paidMethod' ,labelWidth:75,  fieldLabel : "付款方式" , columnWidth : .3}  //'付款方式'
		
	]	
});


//通知信息   右一   Bingo
Ext.define('Foss.airfreight.airNotifyCustomersIndex.NoticeInfoFormPanel',{
	extend: 'Ext.form.Panel',
	title : "通知信息",
	//收缩
	collapsible: true,
	itemId : 'noticeInfoFormPanel_Id',
	//是否有框
	frame:true,
	//动画收缩
	animCollapse: true,
	defaults: {
		margin:'1 1 1 1',
		labelWidth:100
	},
	cls : 'autoHeight',
	bodyCls : 'autoHeight',
	height:315,
	layout:'column',
	defaultType : 'textfield',
	items : [
	   {name : 'receiveCustomerName' , labelWidth:50,fieldLabel : "联系人", readOnly : true,columnWidth : .45} , 
		{name : 'receiveCustomerMobilephone' ,columnWidth : .45, labelWidth:70, fieldLabel : "联系方式", readOnly : true} ,  //联系方式
		{name : 'noticeType',labelWidth:70, fieldLabel : "通知方式", columnWidth : .55,labelWidth:70,readOnly : true} ,
		{xtype : 'container' ,  layout: 'column',columnWidth : .3,
			items : [
				{xtype : 'numberfield' , name : 'daysPickUp',value:3, width : 30, hideTrigger : true, maxValue: 999, 
					minValue: 1, maxLength :2, allowDecimals : false, msgTarget: 'qtip',
					listeners : { 
							blur : function (_this, The, eOpts) {
								this.up('panel').viewNoticeContent(this.up('form').getForm(), 'daysPickUp');
							}
						}
				} , //daysPickUp
				{xtype : 'label',  text : "日内提货", margin: '4 0 0 0'}
			]
		} , 
		
	
		{ xtype : 'textarea',name : 'noticeContent' ,columnWidth : .85,labelWidth:70, fieldLabel : "通知内容",allowBlank : false,readOnly : true //'通知内容<br>(失败原因)'
		}
		//{name : 'noticeContent' ,  fieldStyle:'color:red;',labelWidth:70,columnWidth : .55,fieldLabel : "通知内容", readOnly : true}
	],
	constructor: function(config){ 
		var me = this,
			cfg = Ext.apply({}, config);
		me.bbar = ['->',{
			text : "保存",//'保存'
			//hidden:!airfreight.airNotifyCustomersIndex.isPermission('notifycustomerindex/notifycustomerindexsavebutton'),
			id:'Foss_airfreight_notifyCustomer_NoticeInfoFormPanel_saveButten_Id',
			plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin', {
				seconds: 3
			}),
			handler : function() {
				var form = this.up('form').getForm(), 
				    values = form.getValues();
				var recordsModel = Ext.getCmp('Foss_airfreight_grid_NoticeCustomerGrid_Id').getSelectionModel(),
					records=recordsModel.getSelection(),
					noticeInfoForm   = Ext.getCmp('Foss.airfreight.airNotifyCustomersIndex.TransportInfoFormPanel_id').getForm();
				if(records.length!=1){
	    			Ext.ux.Toast.msg("提示"/*提示*/, "请选择一条运单信息", 'error', 1000);//请选择一条运单信息
	    			return;
	    		}
				if(noticeInfoForm.findField('waybillNo').getValue() != records[0].data['waybillNo']){
	    			Ext.ux.Toast.msg("提示"/*提示*/,'当前运单加载的信息与选中的运单信息不一致，请重新选择', 'error', 2000);//当前运单加载的信息与选中的运单信息不一致，请重新选择
	    			return;
	    		}
				
				if (Ext.isEmpty(values.noticeContent)) {
					Ext.ux.Toast.msg("提示"/*提示*/, "请录入/生成通知内容再进行保存。", 'error', 2000);//'请录入/生成通知内容再进行保存。'
					return;
				}
				
				if (Ext.isEmpty(values.receiveCustomerMobilephone)) {
					Ext.ux.Toast.msg("提示"/*提示*/, "您选择的运单没有手机号，无法进行通知。", 'error', 2000);//'请录入/生成通知内容再进行保存。'
					return; 
				}
				
				// 通知成功，今天不能再次通知
				if(records[0].data['noticeResult'] == 'SUCCESS' 
					&& airfreight.airNotifyCustomersIndex.compareDate(records[0].data['notificationTime'],new Date()) == 0){
					Ext.ux.Toast.msg("提示"/*提示*/, "通知成功，今天不能再次通知", 'ok', 1000);//通知成功，今天不能再次通知
					return ;
				}
				/**
				 * 说明:只能在8:00-21:00发送语音与短信通知
				 */
				/*var dateStart = new Date();
				var dateEnd = new Date();
				dateStart.setHours(8, 0, 0, 0);
				dateEnd.setHours(9, 0, 0, 0);
				
				if((new Date).getTime()-dateStart.getTime() < 0
					|| (new Date).getTime()-dateEnd.getTime() >0 ){
					Ext.ux.Toast.msg("提示"提示, "说明:只能在8:00-21:00发送语音与短信通知", 'ok', 1000);//通知成功，今天不能再次通知
					return ;
				}*/
				
				if (form.isValid()) {
					this.up('form').query('button')[0].setDisabled(false);//设置提交按钮可编辑
					var array = {'vo':{'airNotifyCustomersDto':{'airNotifyCustomersSmsInfo':values}}};
					
					
					//传运单号给后台
					if (!Ext.isEmpty(records[0].data['waybillNo'])) {
						 array.vo.airNotifyCustomersDto.airNotifyCustomersSmsInfo.waybillNo = records[0].data['waybillNo'];
					}
					
					if (!Ext.isEmpty(values.receiveCustomerName)) {
						 array.vo.airNotifyCustomersDto.airNotifyCustomersSmsInfo.receiveCustomerName = values.receiveCustomerName;
					}
					if (!Ext.isEmpty(values.receiveCustomerPhone)) {
						 array.vo.airNotifyCustomersDto.airNotifyCustomersSmsInfo.receiveCustomerPhone = values.receiveCustomerPhone;
					}
					//分批配载未到齐的货物，不进行通知
					if(records[0].data['arriveGoodsQty'] < records[0].data['goodsQtyTotal'] ){
						Ext.ux.Toast.msg("提示","分批配载的货物，在货物到齐后才可短信通知", 'error', 1000);//'分批配载的货物，在货物到齐后才可短信通知'
						return ;
					}
					
					Ext.Ajax.request({
					    // url: '../airfreight/addNotifyCustomer.action',
					    url:airfreight.realPath('addAirNotifyCustomer.action'),
					    jsonData: array,
					    success: function(response){
					    	if (values.noticeType == 'TEL_NOTICE') {
						    	Ext.ux.Toast.msg("提示"/*提示*/, "保存成功", 'ok', 1000);//'保存成功！'
						    	records[0].set('noticeResult',values.noticeResult);
								records[0].set('notificationTime',new Date());
					    	} else {
						    	Ext.ux.Toast.msg("提示"/*提示*/, "保存成功", 'ok', 1000);//'保存成功！'
						    	
								if(values.noticeType == 'SMS_NOTICE'){
									records[0].set('isSmsSuccess','Y');
								}
								records[0].set('noticeResult','SUCCESS');
								records[0].set('notificationTime',new Date());
								
					    	}
							//noticeInfoForm.findField('notificationTime').setValue(Ext.Date.format(new Date(), 'Y-m-d'));
					    	this.up('form').query('button')[2].setDisabled(true);
					    },
					    exception: function(response) {
			                var json = Ext.decode(response.responseText);
			                Ext.ux.Toast.msg("保存失败", json.message, 'error', 2000);//'保存失败'
			                this.up('form').query('button')[2].setDisabled(true);
		                },
						scope : this
					});
				}
			}}];
		me.callParent([cfg]);
	}
});

//通知状态的一些图标  200968 Bingo
Ext.define('Foss.airfreight.airNotifyCustomersIndex.warn', {
		extend : 'Ext.panel.Panel',
		cls : 'autoHeight',
		bodyCls : 'autoHeight',
		layout : 'column',
			defaults:{
			margin:'0 0 0 15'
		},	
		initComponent : function() {
			var me = this;
			Ext.applyIf(me, {
			items : [ {
					xtype : 'label',
					text : "通知成功"
				}, {
					xtype : 'image',
					imgCls : 'foss_icons_pkp_noticeOk'
				}, {
					xtype : 'label',
					text : "通知失败"
				}, {
					xtype : 'image',
					imgCls : 'foss_icons_pkp_noticeError'
				},  
				{
					xtype : 'label',
					text : "录入到达件数与开单件数不一致"
				}, {
					xtype : 'image',
					imgCls : 'foss_icons_pkp_goodsNumDisac'
				}, {
					xtype : 'label',
					text : "运单提货网点与正单到达网点不一致"
				}, {
					xtype : 'image',
					imgCls : 'foss_icons_pkp_3daysNoPicking'
				}, {
					xtype : 'tbspacer',
					flex : 2
				}]
				});
			me.callParent(arguments);
		}

	});
//下面大panel 200968  Bingo
Ext.define('Foss.airfreight.airNotifyCustomersIndex.BigDownPanel',{
	extend:'Ext.panel.Panel',
	bodyCls: 'autoHeight',
	cls: 'autoHeight',
	layout: 'column',
	noticeDetailGrid : null,
	getNoticeDetailGrid :function(){
		var me = this;
		if(this.noticeDetailGrid==null){
			//搜索结果 200968
			this.noticeDetailGrid = Ext.create('Foss.airfreight.grid.NoticeCustomerGrid',{
				columnWidth:.37,
				id:'Foss_airfreight_grid_NoticeCustomerGrid_Id'
				
			});
		}
		return this.noticeDetailGrid;
	},
	noticeInfoForm : null,
	getNoticeInfoForm : function(){
		if(this.noticeInfoForm==null){
			//通知信息 右一  200968
			this.noticeInfoForm = Ext.create('Foss.airfreight.airNotifyCustomersIndex.NoticeInfoFormPanel',{
				id:'Foss.airfreight.airNotifyCustomersIndex.NoticeInfoFormPanel_Id'
			});
		}
		return this.noticeInfoForm;
	},
	wayBillInfoForm : null,
	getWayBillInfoForm : function(){
		if(this.wayBillInfoForm==null){
			//运单信息    200968
			this.wayBillInfoForm = Ext.create('Foss.airfreight.airNotifyCustomersIndex.TransportInfoFormPanel',{
				id:'Foss.airfreight.airNotifyCustomersIndex.TransportInfoFormPanel_id',
					tabConfig : {
					width : 100
				}
				
			});
		}
		return this.wayBillInfoForm;
	},
	initNoticeTabPanel:null,
	getInitNoticeTabPanel : function() {
	if (!this.initNoticeTabPanel) {
		this.initNoticeTabPanel = Ext.create('Ext.tab.Panel', {
			bodyCls: 'autoHeight',
			cls: 'innerTabPanel',
			flex : 5.5,
			//运单信息 200968
			items : [this.getWayBillInfoForm()]	
			
		});
	}
	return this.initNoticeTabPanel;
	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.items = [me.getNoticeDetailGrid(),{
			border: 1,
			xtype:'container',
			columnWidth:.63,
			items : [
				me.getNoticeInfoForm(),me.getInitNoticeTabPanel()
			]
		}];
		me.callParent([cfg]);
	}
});

Ext.onReady(function() {
	Ext.QuickTips.init();
	if (Ext.getCmp('T_airfreight-airNotifyCustomersIndex_content')) {
		return;
	}
	     // 查询条件 200968 2015-08-18
	var queryForm = Ext.create('Foss.airfreight.form.NoticeCustomerSearch'),
	     // 查询条件  下面的整个大的panel 200968
	    bigNOticeDownPanel = Ext.create('Foss.airfreight.airNotifyCustomersIndex.BigDownPanel'), 
		// 通知客户的一些状态图标 200968 
	    warnPanel = Ext.create("Foss.airfreight.airNotifyCustomersIndex.warn")

	    airfreight.airNotifyCustomersIndex.queryform = queryForm;
	
	
	Ext.create('Ext.panel.Panel', {
		id : 'T_airfreight-airNotifyCustomersIndex_content',
		cls : 'panelContentNToolbar',
		bodyCls : 'panelContentNToolbar-body',
		layout : 'auto',
		margin : '2 2 2 2',
		items : [queryForm,warnPanel, bigNOticeDownPanel],
		renderTo : 'T_airfreight-airNotifyCustomersIndex-body'
	});
});
