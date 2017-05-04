//定义运单表格信息model
predeliver.printArriveSheet.getInStockTimeStart = function(date, day) {	
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
	
predeliver.printArriveSheet.getInStockTimeEnd = function(date, day) {	
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
Ext.define('Foss.predeliver.printArriveSheet.Waybill', {
			extend : 'Ext.data.Model',
			fields : [{
						name : 'id',
						type : 'string'
					}, {
						name : 'waybillNo',
						type : 'string'
					}, //运单号
					{
						name : 'receiveCustomerName',
						type : 'string'
					}, //收货客户姓名
					{
						name : 'receiveBigCustomer',
						type : 'string'
					}, //收货人大客户标记
					{
						name : 'receiveCustomerMobilephone',
						type : 'string'
					}, //收货客户手机
					{
						name : 'goodsName',
						type : 'string'
					}, //货物名称
					{
						name : 'goodsQtyTotal',
						type : 'int'
					}, //货物开单件数
					{
						name : 'receiveCustomerPhone',
						type : 'string'
					}, //收货客户固定电话
					{
						name : 'receiveCustomerAddress',
						type : 'string'
					}, //收货具体地址
					{
						name : 'arriveNotoutGoodsQty',
						type : 'int'
					}, //到达未出库件数
					{
						name : 'destOrgCode',
						type : 'string'
					}, //车辆到达部门ID
					{
						name : 'returnBillType',
						type : 'string'
					}, //返单类型
					{
						name : 'waybillrfcStatus',
						type : 'string'
					}, //是否含未处理更改单
					{
						name : 'targetOrgCode',
						type : 'string'
					}, //目的站
					{
						name : 'arriveGoodsQty',
						type : 'int'
					}, //到达件数
					{
						name : 'isPrinted',
						type : 'string'
					}, //是否打印
					{
						name : 'paidMethod'
					}, //付款方式
					{
						name : 'isPay',
						type : 'string'
					}, //是否已付款
					{
						name : 'isSelfFlg',
						type : 'string'
					},//是否是本部门
					{
						name : 'receiveCustomerContact',
						type : 'string'
					},{
						name : 'printUserName',
						type : 'string'
					},{
						name : 'printTime',type:'date',
						convert: function(value) {
							if (value != null) {
								var date = new Date(value);
								return Ext.Date.format(date,'Y-m-d H:i:s');
							} else {
								return null;
							}
						}
					},{
						name : 'printOrgName',
						type : 'string'
					},{
						name : 'printtimes',
						type : 'string'
					},{
						name : 'transportType',
						type : 'string'
					},{name : 'receiveMethod',
						convert:function(value,record) {
							if(record.get('transportType')=='TRANS_AIRCRAFT'){//空运
								return FossDataDictionary.rendererSubmitToDisplay(value, 'PICKUPGOODSAIR');
							}else{//汽运
								var v = FossDataDictionary.rendererSubmitToDisplay(value, 'PICKUPGOODSHIGHWAYS');
								if(Ext.isEmpty(v) || value == v){
									v = FossDataDictionary.rendererSubmitToDisplay(value, 'PICKUPGOODSSPECIALDELIVERYTYPE');
								}
								return v;			
							}
						}
					}]
		});

//定义运单基本信息model
Ext.define('Foss.predeliver.printArriveSheet.WaybillEntity', {
			extend : 'Ext.data.Model',
			fields : [{
						name : 'arriveType'
					}, //到达类型
					{
						name : 'billWeight'
					}, //计费重量
					{
						name : 'billingType',
						convert : function(value) {//运费计费类型
							return FossDataDictionary.rendererSubmitToDisplay(
									value, 'BILLINGWAY');
						}
					}, {
						name : 'codAmount'
					}, //代收货款
					{
						name : 'codFee'
					}, //代收货款手续费
					{
						name : 'createOrgCode'
					}, //开单组织
					{
						name : 'createUserCode'
					}, //开单人
					{
						name : 'targetOrgCode'
					}, {
						name : 'receiveCustomerCityCode'//目的站
					}, {
						name : 'deliveryCustomerCityCode'//始发站
					}, {
						name : 'customerPickupOrgCode'
					}, //提货网点
					{
						name : 'goodsName'
					}, //货物名称
					{
						name : 'goodsPackage'
					}, //包装
					{
						name : 'goodsQtyTotal'
					}, //开单件数
					{
						name : 'arriveNotoutGoodsQty'
					}, {
						name : 'goodsVolumeTotal'
					}, //体积
					{
						name : 'goodsWeightTotal'
					}, //重量
					{
						name : 'insuranceAmount'
					}, //保价声明值
					{
						name : 'lastLoadOrgCode'
					}, //运单配载部门
					{
						name : 'paidMethod',
						convert : function(value) {// 付款方式
							return FossDataDictionary.rendererSubmitToDisplay(
									value, 'SETTLEMENT__PAYMENT_TYPE');
						}
					}, //付款方式
					{
						name : 'preArriveTime',
						type : 'date',
						convert : dateConvert
					}, //预计到达时间
					{
						name : 'prePayAmount'
					}, //现付总计
					{
						name : 'productCode'
					}, //精准卡航
					{
						name : 'receiveCustomerAddress'
					}, //收货具体地址
					{
						name : 'receiveCustomerAddressNote'
					}, //收货具体地址备注
					{
						name : 'receiveCustomerMobilephone'
					}, //手机/固定电话号码
					{
						name : 'receiveCustomerName'
					},{//收货人
						name : 'receiveCustomerContact'
					}, //收货人姓名
					{
						name : 'receiveCustomerPhone'
					}, //收货客户固定电话
					{
						name : 'receiveMethod',//派送方式
						convert:function(value,record) {
							if(record.get('transportType')=='TRANS_AIRCRAFT'){//空运
								return FossDataDictionary.rendererSubmitToDisplay(value, 'PICKUPGOODSAIR');
							}else{//汽运
								var v = FossDataDictionary.rendererSubmitToDisplay(value, 'PICKUPGOODSHIGHWAYS');
								if(Ext.isEmpty(v) || value == v){
									v = FossDataDictionary.rendererSubmitToDisplay(value, 'PICKUPGOODSSPECIALDELIVERYTYPE');
								}
								return v;
							}
						}
					}, //提货方式
					{
						name : 'receiveOrgCode'
					}, //发货网点
					{
						name : 'returnBillType'
					}, //是否含签收单
					{
						name : 'toPayAmount'
					}, //到付总计
					{
						name : 'totalFee'
					}, //费用总计
					{
						name : 'transportFee'
					}, //运费
					{
						name : 'unitPrice'
					}, //费率
					{
						name : 'waybillNo'
					}, //运单号
					{
						name : 'billTime',
						type : 'date',
						convert : dateConvert
					}, //开单时间
					{
						name : 'currencyCode'
					}, //币种
					{
						name : 'transportationRemark'
					}, //储运事项
					{
						name : 'transportType'/*,
						convert : function(value) {// 运输类型
							return FossDataDictionary.rendererSubmitToDisplay(
									value, 'TRANS_TYPE');
						}*/
					}, //运输类型
					{
						name : 'productCode'
					}, //运输类型
					{
						name : 'receiveDepTelephone'
					}, //发货网点电话
					{
						name : 'customerPickupOrgCode'
					}, //提货网点电话
					{
						name : 'waybillChargeDtl',
						type : 'object'
					}, {
						name : 'waybillDisDtl',
						type : 'object'
					}, //提货网点电话
					{
						name : 'arriveSheetCreateUserCode'
					}, //到达联创建人
					{
						name : 'arriveSheetCreateDate',
						type : 'date',
						convert : dateConvert
					}, //提货网点电话
					{
						name : 'deptName'
					}, //保管费
					{
						name : 'storageCharge'
					}]
		});

//定义运单费用信息model
Ext.define('Foss.predeliver.printArriveSheet.WaybillChargeDtlEntity', {
			extend : 'Ext.data.Model',
			fields : [{
						name : 'pricingEntryName'
					}, //服务名称
					{
						name : 'amount'
					}, //费用
					{
						name : 'currencyCode'
					}, //币种
					{
						name : 'rowNum'
					} //序列
			]
		});

//提货方式集合
Ext.define('Foss.predeliver.printArriveSheet.ReceiveMethodStore',{
	extend: 'Ext.data.Store',
	fields: [
		{name: 'valueCode',  type: 'string'},
		{name: 'valueName',  type: 'string'}
	],
	data: {
		'items':[
			{'valueCode':'','valueName':predeliver.printArriveSheet.i18n('pkp.predeliver.printArriveSheet.complete')},// '全部'
			{'valueCode':'DELIVER','valueName':predeliver.printArriveSheet.i18n('pkp.predeliver.printArriveSheet.deliver')},// '送货'
			{'valueCode':'PICKUP','valueName':predeliver.printArriveSheet.i18n('pkp.predeliver.printArriveSheet.pickup')}//'自提'
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
//定义到达联数据store
Ext.define('Foss.predeliver.printArriveSheet.ArriveStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.predeliver.printArriveSheet.Waybill',
	proxy : {
		//代理的类型为ajax代理
		type : 'ajax',
		//提交方式
		actionMethods : 'POST',
		url : predeliver.realPath('queryArriveSheetByWaybill.action'),
		//定义一个读取器
		reader : {
			//以JSON的方式读取
			type : 'json',
			//定义读取JSON数据的根对象
			root : 'vo.arriveSheetWaybillList'
		}
	},
	listeners : {
		//查询事件
		beforeload : function(s, operation, eOpts) {
			//执行查询，首先货物查询条件，为全局变量，在查询条件的FORM创建时生成
			var serachParms = Ext
					.getCmp('T_predeliver-printArriveSheetIndex_content')
					.getQueryForm().getValues();
			Ext.apply(operation, {
				params : {
					'vo.arriveSheetWaybillDto.waybillNo' : serachParms.waybillNo,
					'vo.arriveSheetWaybillDto.handoverNo' : serachParms.handoverNo,
					'vo.arriveSheetWaybillDto.receiveMethod' : serachParms.receiveMethod,
					'vo.arriveSheetWaybillDto.receiveCustomerName' : serachParms.receiveCustomerName,
					'vo.arriveSheetWaybillDto.receiveCustomerMobilephone' : serachParms.receiveCustomerMobilephone,
					'vo.arriveSheetWaybillDto.deliverbillId' : serachParms.deliverbillId,
					'vo.arriveSheetWaybillDto.isPrinted' : serachParms.isPrinted,
					'vo.arriveSheetWaybillDto.inStockTimeFrom' : serachParms.inStockTimeFrom,
					'vo.arriveSheetWaybillDto.inStockTimeTo' : serachParms.inStockTimeTo,
					'vo.arriveSheetWaybillDto.arriveStartTime' : serachParms.arriveStartTime,
					'vo.arriveSheetWaybillDto.arriveEndTime' : serachParms.arriveEndTime,
					'vo.arriveSheetWaybillDto.paidMethod' : serachParms.paidMethod
				}
			});
		}
	}
});

//打印状态集合
Ext.define('Foss.predeliver.printArriveSheet.store.printStore',{
	extend: 'Ext.data.Store',
	fields: [
		{name: 'valueCode',  type: 'string'},
		{name: 'valueName',  type: 'string'}
	],
	data: {
		'items':[
			{'valueCode':'','valueName':predeliver.printArriveSheet.i18n('pkp.predeliver.printArriveSheet.complete')},// '全部'
			{'valueCode':'N','valueName':predeliver.printArriveSheet.i18n('pkp.predeliver.printArriveSheet.print.no')},// '未打印'
			{'valueCode':'Y','valueName':predeliver.printArriveSheet.i18n('pkp.predeliver.printArriveSheet.print.yes')}//'已打印'
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

//查询条件
Ext.define('Foss.predeliver.printArriveSheet.queryForm', {
	extend : 'Ext.form.Panel',
	layout : 'column',
	frame : true,
	cls : 'autoHeight',
	bodyCls : 'autoHeight',
	//收缩
	collapsible : true,
	//动画收缩
	animCollapse : true,
	defaults : {
		xtype : 'textfield',
		margin : '5 5 5 5',
		labelWidth : 60
	},
	// bodyPadding: 10,
	title : predeliver.printArriveSheet.i18n('pkp.predeliver.printArriveSheet.search'), //查询,
	initComponent : function() {
		var me = this;
		Ext.applyIf(me, {
			items : [{
						fieldLabel : predeliver.printArriveSheet.i18n('pkp.predeliver.printArriveSheet.waybillNo'), //运单号,
						columnWidth : 0.20,
						name : 'waybillNo',
						labelWidth : 60,
						xtype : 'textarea',
						emptyText : predeliver.printArriveSheet.i18n('pkp.predeliver.printArriveSheet.waybillNo.valitation'),
						regex : /^([0-9]{8,10}\n?)+$/i,
						regexText : predeliver.printArriveSheet.i18n('pkp.predeliver.printArriveSheet.waybillNo.valitation')
					}, {
						fieldLabel : predeliver.printArriveSheet.i18n('pkp.predeliver.printArriveSheet.handoverNo'), //交接单号,
						columnWidth : 0.25,
						name : 'handoverNo'
					}, {
						xtype : 'combobox',
						name : 'receiveMethod',
						columnWidth : 0.25,
						fieldLabel : predeliver.printArriveSheet.i18n('pkp.predeliver.printArriveSheet.receiveMethod'), //提货方式,
						value : '',
						queryModel : 'local',
						displayField : 'valueName',
						valueField : 'valueCode',
						editable : false,
						store : Ext.create('Foss.predeliver.printArriveSheet.ReceiveMethodStore')
					}, {
						fieldLabel : predeliver.printArriveSheet.i18n('pkp.predeliver.printArriveSheet.receiveCustomerName1'), //收货人姓名,
						columnWidth : 0.3,
						labelWidth : 75,
						name : 'receiveCustomerName'
					}, {
						fieldLabel : predeliver.printArriveSheet.i18n('pkp.predeliver.printArriveSheet.receiveCustomerMobilephone'), //手机/固定电话号码,
						columnWidth : 0.25,
						labelWidth : 120,
						name : 'receiveCustomerMobilephone'
					}, {
						fieldLabel : predeliver.printArriveSheet.i18n('pkp.predeliver.printArriveSheet.deliverbillId'), //预派送单号,
						columnWidth : 0.25,
						labelWidth : 75,
						name : 'deliverbillId'
					}, {
						xtype : 'combobox',
						name : 'paidMethod',
						columnWidth : 0.30,
						fieldLabel : predeliver.printArriveSheet.i18n('pkp.predeliver.printArriveSheet.paidMethod'), //支付方式,
						value : '',
						queryModel : 'local',
						displayField : 'valueName',
						valueField : 'valueCode',
						editable : false,
						store : FossDataDictionary.getDataDictionaryStore(
								'SETTLEMENT__PAYMENT_TYPE', null, {
									'valueCode' : '',
									'valueName' : '全部'
								})
					}, /*{
						xtype : 'rangeDateField',
						fieldLabel : predeliver.printArriveSheet.i18n('pkp.predeliver.printArriveSheet.planArriveTime'),
						labelWidth:110,
						fieldId : 'Foss_predeliver_arrive_planArriveTime_Id',
						dateType : 'datetimefield_date97',
						fromName : 'planArriveTimeFrom',
						toName : 'planArriveTimeTo',
						editable:false,
						columnWidth : .5
					},*/ {
						xtype : 'rangeDateField',
						fieldLabel : predeliver.printArriveSheet.i18n('pkp.predeliver.printArriveSheet.inStockTime'),
						//labelWidth:110,
						fieldId : 'Foss_predeliver_arrive_inStockTime_Id',
						dateType : 'datetimefield_date97',
						fromName : 'inStockTimeFrom',
						toName : 'inStockTimeTo',
						fromValue: Ext.Date.format(predeliver.printArriveSheet.getInStockTimeStart(new Date(),0),'Y-m-d H:i:s'),	
						toValue: Ext.Date.format(predeliver.printArriveSheet.getInStockTimeEnd(new Date(),0),'Y-m-d H:i:s'),
						editable:false,
						columnWidth : .5
					},{
						xtype : 'combobox',
						name : 'isPrinted',
						columnWidth : 0.3,
						fieldLabel : predeliver.printArriveSheet.i18n('pkp.predeliver.printArriveSheet.print.status'), //打印状态
						value : '',
						queryModel : 'local',
						displayField : 'valueName',
						valueField : 'valueCode',
						editable : false,
						store : Ext.create('Foss.predeliver.printArriveSheet.store.printStore')
					},{
						xtype : 'rangeDateField',
						fieldId : 'FOSS_pringArriveShetTime_Id3',
						fieldLabel : predeliver.printArriveSheet.i18n('pkp.predeliver.printArriveSheet.arriveTime'), // 到达时间
						allowFromBlank : true,
						allowToBlank : true,
						editable : false,
						dateType : 'datetimefield_date97',
						fromName : 'arriveStartTime',
						toName : 'arriveEndTime',
						fromValue: Ext.Date.format(predeliver.printArriveSheet.getInStockTimeStart(new Date(),0),'Y-m-d H:i:s'),	
						toValue: Ext.Date.format(predeliver.printArriveSheet.getInStockTimeEnd(new Date(),0),'Y-m-d H:i:s'),
						format : 'Y-m-d H:i:s',
						columnWidth : .5
					},{
						border : 1,
						xtype : 'container',
						columnWidth : 1,
						defaultType : 'button',
						layout : 'column',
						items : [{
									text : predeliver.printArriveSheet.i18n('pkp.predeliver.printArriveSheet.reset'), //重置,
									columnWidth : .08,
									handler : function() {
										var myform = this.up('form');
										myform.getForm().reset();
										myform.getForm().findField('inStockTimeFrom').setValue(Ext.Date.format(predeliver.printArriveSheet.getInStockTimeStart(new Date(),0),'Y-m-d H:i:s'));	
										myform.getForm().findField('inStockTimeTo').setValue(Ext.Date.format(predeliver.printArriveSheet.getInStockTimeEnd(new Date(),0),'Y-m-d H:i:s'));	
										myform.getForm().findField('arriveStartTime').setValue(Ext.Date.format(predeliver.printArriveSheet.getInStockTimeStart(new Date(),0),'Y-m-d H:i:s'));	
										myform.getForm().findField('arriveEndTime').setValue(Ext.Date.format(predeliver.printArriveSheet.getInStockTimeEnd(new Date(),0),'Y-m-d H:i:s'));	
									}
								}, {
									xtype : 'container',
									border : false,
									columnWidth : .84,
									html : '&nbsp;'
								}, {
									text : predeliver.printArriveSheet.i18n('pkp.predeliver.printArriveSheet.search'), //查询,
									cls : 'yellow_button',
									disabled:!predeliver.printArriveSheet.isPermission('printarrivesheetindex/printarrivesheetindexquerybutton'),
									hidden:!predeliver.printArriveSheet.isPermission('printarrivesheetindex/printarrivesheetindexquerybutton'),
									columnWidth : .08,
									plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin', {
										seconds: 3
									}),
									handler : function() {
										var serachParms = this.up('form').getForm().getValues();
										if(!this.up('form').getForm().isValid()){
											return;
										}
										var resultGridStore = Ext.getCmp('T_predeliver-printArriveSheetIndex_content').getResultGrid().store;
										if (Ext.isEmpty(serachParms.waybillNo)
												&& Ext.isEmpty(serachParms.handoverNo)
												&& Ext.isEmpty(serachParms.receiveCustomerName)
												&& Ext.isEmpty(serachParms.receiveCustomerMobilephone)
												&& Ext.isEmpty(serachParms.deliverbillId)
												//&& Ext.isEmpty(serachParms.planArriveTimeFrom)
												//&& Ext.isEmpty(serachParms.planArriveTimeTo)
												&& Ext.isEmpty(serachParms.inStockTimeFrom)
												&& Ext.isEmpty(serachParms.inStockTimeTo)
												&& Ext.isEmpty(serachParms.arriveStartTime)
												&& Ext.isEmpty(serachParms.arriveEndTime)) {
											Ext.ux.Toast.msg("提示", "请输入任意一个条件查询！");
											return;
										}
										if((!Ext.isEmpty(serachParms.inStockTimeFrom) && Ext.isEmpty(serachParms.inStockTimeTo) || (Ext.isEmpty(serachParms.inStockTimeFrom) && (!Ext.isEmpty(serachParms.inStockTimeTo))))){
											Ext.ux.Toast.msg(predeliver.printArriveSheet.i18n('pkp.predeliver.notifyCustomer.tip'), predeliver.printArriveSheet.i18n('pkp.predeliver.printArriveSheet.the.date.range.cannot.be.more.than.2.days'), 'error', 3000); // '起止日期相隔不能超过2天！'
											return;
										}
										if((!Ext.isEmpty(serachParms.arriveStartTime) && Ext.isEmpty(serachParms.arriveEndTime) || (Ext.isEmpty(serachParms.arriveStartTime) && (!Ext.isEmpty(serachParms.arriveEndTime))))){
											Ext.ux.Toast.msg(predeliver.printArriveSheet.i18n('pkp.predeliver.notifyCustomer.tip'), predeliver.printArriveSheet.i18n('pkp.predeliver.printArriveSheet.the.date.range.cannot.be.more.than.2.days'), 'error', 3000); // '起止日期相隔不能超过2天！'
											return;
										}
										if (!Ext.isEmpty(serachParms.inStockTimeFrom) && !Ext.isEmpty(serachParms.inStockTimeTo)) {	
											var result = Ext.Date.parse(serachParms.inStockTimeTo,'Y-m-d H:i:s') - Ext.Date.parse(serachParms.inStockTimeFrom,'Y-m-d H:i:s');	
											if(result / (24 * 60 * 60 * 1000) >= 2){	
												Ext.ux.Toast.msg(predeliver.printArriveSheet.i18n('pkp.predeliver.notifyCustomer.tip'), predeliver.printArriveSheet.i18n('pkp.predeliver.printArriveSheet.the.date.range.cannot.be.more.than.2.days'), 'error', 3000); // '起止日期相隔不能超过2天！'
												return;	
											}	
										}
										// 到达时间验证
										if (!Ext.isEmpty(serachParms.arriveStartTime) && !Ext.isEmpty(serachParms.arriveEndTime)) {	
											var result = Ext.Date.parse(serachParms.arriveEndTime,'Y-m-d H:i:s') - Ext.Date.parse(serachParms.arriveStartTime,'Y-m-d H:i:s');	
											if(result / (24 * 60 * 60 * 1000) >= 2){	
												Ext.ux.Toast.msg(predeliver.printArriveSheet.i18n('pkp.predeliver.notifyCustomer.tip'), predeliver.printArriveSheet.i18n('pkp.predeliver.printArriveSheet.the.date.range.cannot.be.more.than.2.days'), 'error', 3000); // '起止日期相隔不能超过2天！'
												return;	
											}	
										}
										if (Ext.isEmpty(serachParms.waybillNo)
												&& Ext.isEmpty(serachParms.handoverNo)
												&& Ext.isEmpty(serachParms.receiveCustomerName)
												&& Ext.isEmpty(serachParms.receiveCustomerMobilephone)
												&& Ext.isEmpty(serachParms.deliverbillId)) {
											if ((Ext.isEmpty(serachParms.arriveStartTime) || Ext.isEmpty(serachParms.arriveEndTime)) 
												&& (Ext.isEmpty(serachParms.inStockTimeFrom) || Ext.isEmpty(serachParms.inStockTimeTo))) {
												Ext.ux.Toast.msg(predeliver.printArriveSheet.i18n('pkp.predeliver.notifyCustomer.tip'), '入库时间,到达时间必须输入一组起止日期', 'error', 3000); // '入库时间、到达时间必须输入'
												return;	
											}
										}
										// 验证运单号输入的行数
										if (!Ext.isEmpty(serachParms.waybillNo)) {
											var arrayWaybillNo = serachParms.waybillNo.split('\n');
											if (arrayWaybillNo.length > 100) {
												Ext.ux.Toast.msg(predeliver.printArriveSheet.i18n('pkp.predeliver.notifyCustomer.tip'), predeliver.printArriveSheet.i18n('pkp.predeliver.printArriveSheet.waybillNo.valitation'), 'error', 3000); // 
												return;	
											}
											for (var i = 0; i < arrayWaybillNo.length; i++) {
												if (Ext.isEmpty(arrayWaybillNo[i])) {
													Ext.ux.Toast.msg(predeliver.printArriveSheet.i18n('pkp.predeliver.notifyCustomer.tip'), predeliver.printArriveSheet.i18n('pkp.predeliver.printArriveSheet.waybillNo.valitation'), 'error', 3000); // 运单号不能录入空回车
													return;	
												}
											}
										}
										resultGridStore.load();
									}
								}]
					}],
					listeners : { 
						'render' : function(_this, eOpts){
							_this.getForm().findField('waybillNo').setHeight(125);
						}
					}
		});

		me.callParent(arguments);
	}

});

//显示结果运单详细信息
Ext.define('Foss.predeliver.printArriveSheet.waybillInfo', {
			extend : 'Ext.form.Panel',
			//frame: true,
			defaults : {
				xtype : 'textfield',
				margin : '5 5 5 5',
				labelWidth : 60,
				readOnly : true
			},
			//自动收缩高度
			cls : 'autoHeight',
			bodyCls : 'autoHeight',
			layout : 'column',
			items : [{
						fieldLabel : predeliver.printArriveSheet.i18n('pkp.predeliver.printArriveSheet.deliveryCustomerCityName'), //始发站,
						columnWidth : .25,
						name : 'deliveryCustomerCityCode'
					}, {
						fieldLabel : predeliver.printArriveSheet.i18n('pkp.predeliver.printArriveSheet.targetOrgCode'), //目的站,
						columnWidth : .25,
						name : 'targetOrgCode'
					}, {
						fieldLabel : predeliver.printArriveSheet.i18n('pkp.predeliver.printArriveSheet.transportType'), //运输类型,
						columnWidth : .25,
						name : 'productCode'
					}, {
						fieldLabel : predeliver.printArriveSheet.i18n('pkp.predeliver.printArriveSheet.receiveCustomerName'), //收货人,
						columnWidth : .25,
						name : 'receiveCustomerContact'
					}, {
						fieldLabel : predeliver.printArriveSheet.i18n('pkp.predeliver.printArriveSheet.telephone'), //电话,
						columnWidth : .25,
						name : 'receiveCustomerMobilephone'
					}, {
						fieldLabel : predeliver.printArriveSheet.i18n('pkp.predeliver.printArriveSheet.receiveCustomerAddress'), //地址,
						columnWidth : .25,
						name : 'receiveCustomerAddress'
					}, {
						fieldLabel : predeliver.printArriveSheet.i18n('pkp.predeliver.printArriveSheet.goodsQtyTotal'), //件数,
						columnWidth : .25,
						name : 'arriveNotoutGoodsQty'
					}, {
						fieldLabel : predeliver.printArriveSheet.i18n('pkp.predeliver.printArriveSheet.goodsPackage'), //包装,
						columnWidth : .25,
						name : 'goodsPackage'
					}, {
						fieldLabel : predeliver.printArriveSheet.i18n('pkp.predeliver.printArriveSheet.goodsVolumeTotal'), //体积,
						columnWidth : .25,
						name : 'goodsVolumeTotal'
					}, {
						fieldLabel : predeliver.printArriveSheet.i18n('pkp.predeliver.printArriveSheet.goodsWeightTotal'), //重量,
						columnWidth : .25,
						name : 'goodsWeightTotal'
					}, {
						fieldLabel : predeliver.printArriveSheet.i18n('pkp.predeliver.printArriveSheet.transportFee'), //运费,
						columnWidth : .25,
						name : 'transportFee'
					}, {
						fieldLabel : predeliver.printArriveSheet.i18n('pkp.predeliver.printArriveSheet.unitPrice'), //费率,
						columnWidth : .25,
						name : 'unitPrice'
					}, {
						fieldLabel : predeliver.printArriveSheet.i18n('pkp.predeliver.printArriveSheet.prePayAmount'), //现付总计,
						columnWidth : .25,
						name : 'prePayAmount'
					}, {
						fieldLabel : predeliver.printArriveSheet.i18n('pkp.predeliver.printArriveSheet.toPayAmount'), //到付总计,
						columnWidth : .25,
						name : 'toPayAmount'
					}, {
						fieldLabel : predeliver.printArriveSheet.i18n('pkp.predeliver.printArriveSheet.receiveMethod1'), //交货方式,
						columnWidth : .25,
						name : 'receiveMethod'
					}, {
						fieldLabel : predeliver.printArriveSheet.i18n('pkp.predeliver.printArriveSheet.paidMethod1'), //付款方式,
						columnWidth : .25,
						name : 'paidMethod'
					}, {
						fieldLabel : predeliver.printArriveSheet.i18n('pkp.predeliver.printArriveSheet.codAmount'), //贷收货款,
						columnWidth : .25,
						name : 'codAmount'
					}, {
						fieldLabel : predeliver.printArriveSheet.i18n('pkp.predeliver.printArriveSheet.flightShift'), //航班/日期,
						columnWidth : .4,
						name : 'flightShift',
						labelWidth : 65
					}, {
						fieldLabel : predeliver.printArriveSheet.i18n('pkp.predeliver.printArriveSheet.preArriveTime'), //预计到达时间,
						columnWidth : .35,
						name : 'preArriveTime',
						xtype : 'datefield',
						format : 'Y-m-d H:i:s',
						labelWidth : 85
					}, {
						fieldLabel : predeliver.printArriveSheet.i18n('pkp.predeliver.printArriveSheet.receiveOrgCode'), //发货网点,
						columnWidth : .5,
						name : 'receiveOrgCode'
					}, {
						fieldLabel : predeliver.printArriveSheet.i18n('pkp.predeliver.printArriveSheet.telephone'), //电话,
						columnWidth : .25,
						name : 'receiveDepTelephone'
					}, {
						fieldLabel : predeliver.printArriveSheet.i18n('pkp.predeliver.printArriveSheet.storageCharge'), //保管费,
						columnWidth : .25,
						name : 'storageCharge'
					}, {
						fieldLabel : predeliver.printArriveSheet.i18n('pkp.predeliver.printArriveSheet.customerPickupOrgCode'), //提货网点,
						columnWidth : .5,
						name : 'customerPickupOrgCode'
					}, {
						fieldLabel : predeliver.printArriveSheet.i18n('pkp.predeliver.printArriveSheet.telephone'), //电话,
						columnWidth : .25,
						name : 'customerPickup\DepTelephone'
					}, {
						fieldLabel : predeliver.printArriveSheet.i18n('pkp.predeliver.printArriveSheet.createUserCode'), //制单人/时间（发货网点）,
						columnWidth : .3,
						name : 'createUserCode',
						labelWidth : 150
					}, {
						columnWidth : .2,
						name : 'billTime',
						labelWidth : 150,
						xtype : 'datefield',
						format : 'Y-m-d H:i:s'
					}, {
						columnWidth : .5,
						name : 'createOrgCode',
						labelWidth : 150
					}, {
						fieldLabel : predeliver.printArriveSheet.i18n('pkp.predeliver.printArriveSheet.arriveSheetCreateUserCode'), //制单人/时间（提货网点）,
						columnWidth : .3,
						name : 'arriveSheetCreateUserCode',
						labelWidth : 150
					}, {
						columnWidth : .2,
						name : 'arriveSheetCreateDate',
						labelWidth : 150,
						xtype : 'datefield',
						format : 'Y-m-d H:i:s'
					}, {
						columnWidth : .5,
						name : 'deptName',
						labelWidth : 150
					}, {
						xtype : 'textareafield',
						fieldLabel : predeliver.printArriveSheet.i18n('pkp.predeliver.printArriveSheet.transportationRemark'), //其他储运事项,
						columnWidth : .6,
						name : 'transportationRemark',
						labelWidth : 150
					}

			]
		});

//显示结果右上form
Ext.define('Foss.predeliver.printArriveSheet.rightGoodsNamePanel', {
			extend : 'Ext.form.Panel',
			//frame: true,
			defaults : {
				margin : '5 10 5 10',
				labelWidth : 100,
				readOnly : true
			},
			defaultType : 'textfield',
			//自动收缩高度
			cls : 'autoHeight',
			bodyCls : 'autoHeight',
			layout : 'column',
			items : [{
						xtype : 'textfield',
						fieldLabel : predeliver.printArriveSheet.i18n('pkp.predeliver.printArriveSheet.goodsName'), //货品名称,
						name : 'goodsName'
					}, {
						xtype : 'textfield',
						fieldLabel : predeliver.printArriveSheet.i18n('pkp.predeliver.printArriveSheet.insuranceAmount'), //保价声明值,
						name : 'insuranceAmount'
					}, {
						xtype : 'textfield',
						fieldLabel : predeliver.printArriveSheet.i18n('pkp.predeliver.printArriveSheet.arriveType'), //到货类型,
						name : 'arriveType'
					}]
		});

//显示结果右下grid
Ext.define('Foss.predeliver.printArriveSheet.rightDownGrid', {
			extend : 'Ext.grid.Panel',
			//frame: true,
			defaults : {
				//margin:'5 10 5 10',
				labelWidth : 60,
				readOnly : true
			},
			store : Ext.create('Ext.data.Store', {
						fields : ['pricingEntryName', 'amount', 'currencyCode',
								'rowNum'],
						proxy : {
							type : 'memory',
							reader : {
								type : 'json'
							}
						}
					}),
			//自动收缩高度
			cls : 'autoHeight',
			bodyCls : 'autoHeight',
			columns : [{
						xtype : 'rownumberer',
						text : predeliver.printArriveSheet.i18n('pkp.predeliver.printArriveSheet.no'), //序号,
						align : 'center',
						width : 60
					}, {
						header : predeliver.printArriveSheet.i18n('pkp.predeliver.printArriveSheet.pricingEntryCode'), //服务名称,
						align : 'center',
						dataIndex : 'pricingEntryName',
						width : 95
					}, {
						header : predeliver.printArriveSheet.i18n('pkp.predeliver.printArriveSheet.amount'), //费用,
						align : 'center',
						dataIndex : 'amount',
						width : 80
					}],

			constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				me.dockedItems = [{
							xtype : 'toolbar',
							dock : 'bottom',
							layout : 'column',
							defaults : {
								margin : '5 5 5 5',
								readOnly : true
							},
							items : [{
										xtype : 'textfield',
										name : 'totalFee',
										columnWidth : .8,
										fieldLabel : predeliver.printArriveSheet.i18n('pkp.predeliver.printArriveSheet.totalFee') //费用总计
									}, {
										xtype : 'textfield',
										name : 'billWeight',
										columnWidth : .8,
										fieldLabel : predeliver.printArriveSheet.i18n('pkp.predeliver.printArriveSheet.billWeight') //计费重量
									}, {
										name : 'billingType',
										xtype : 'textfield',
										columnWidth : .8,
										fieldLabel : predeliver.printArriveSheet.i18n('pkp.predeliver.printArriveSheet.billingType') //计费方式
									}]
						}]

				me.callParent([cfg]);
			}

		});

Ext.define('Foss.predeliver.printArriveSheet.waybillTotalPanels', {
	extend : 'Ext.panel.Panel',
	layout : {
		type : 'column'
	},
	waybillInfo : null,
	getWaybillInfo : function() {
		if (this.waybillInfo == null) {
			this.waybillInfo = Ext.create(
					"Foss.predeliver.printArriveSheet.waybillInfo", {
						columnWidth : .75
					});
		}
		return this.waybillInfo;
	},
	rightGoodsNamePanel : null,
	getRightGoodsNamePanel : function() {
		if (this.rightGoodsNamePanel == null) {
			this.rightGoodsNamePanel = Ext
					.create("Foss.predeliver.printArriveSheet.rightGoodsNamePanel");
		}
		return this.rightGoodsNamePanel;
	},
	rightDownGrid : null,
	getRightDownGrid : function() {
		if (this.rightDownGrid == null) {
			this.rightDownGrid = Ext
					.create("Foss.predeliver.printArriveSheet.rightDownGrid");
		}
		return this.rightDownGrid;
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.items = [me.getWaybillInfo(), {
					border : 1,
					xtype : 'container',
					columnWidth : .238,
					items : [me.getRightGoodsNamePanel(), me.getRightDownGrid()]
				}];
		me.callParent([cfg]);
	},
	bindData : function(record, grid, rowBodyElement) {
		// 绑定表格数据到表单上
		Ext.Ajax.request({
					url : predeliver
							.realPath('queryWaybillInfoByWaybillNo.action'),
					params : {
						'vo.arriveSheetWaybillDto.waybillNo' : record
								.get('waybillNo'),
						'vo.arriveSheetWaybillDto.arriveNotoutGoodsQty' : record
								.get('arriveNotoutGoodsQty')
					},
					success : function(response) {
						var result = Ext.decode(response.responseText);
						var waybillEntity = Ext.ModelManager
								.create(
										result.vo.arriveSheetWaybillAddPropertyDto,
										'Foss.predeliver.printArriveSheet.WaybillEntity');
						var user = FossUserContext.getCurrentUserEmp();
						var dept = FossUserContext.getCurrentDept();
						waybillEntity.set('arriveSheetCreateUserCode',
								user.empName);//到达联创建人
						waybillEntity.set('deptName', dept.name);//到达联创建部门
						//如果没有保管费费用生成，则显示0
						if(!result.vo.arriveSheetWaybillAddPropertyDto.storageCharge){
							waybillEntity.set('storageCharge','0');
						}
						rowBodyElement.getWaybillInfo()
								.loadRecord(waybillEntity);
						rowBodyElement.getRightGoodsNamePanel()
								.loadRecord(waybillEntity);
						if (result.vo.arriveSheetWaybillAddPropertyDto != null && result.vo.arriveSheetWaybillAddPropertyDto.waybillChargeDtlPrintDtos != null) {
						rowBodyElement.getRightDownGrid().getStore()
								.loadData(result.vo.arriveSheetWaybillAddPropertyDto.waybillChargeDtlPrintDtos);
						}
						var items = rowBodyElement.getRightDownGrid().getDockedItems('toolbar[dock="bottom"]');
						items[0].getComponent(0).setValue(waybillEntity.data.totalFee);
						items[0].getComponent(1).setValue(waybillEntity.data.billWeight);
						items[0].getComponent(2).setValue(waybillEntity.data.billingType);
						/*items[0].getComponent(3).setValue(waybillEntity.data.currencyCode);*/
					}
				});
		//rowBodyElement.getWaybillInfo().loadRecord(record);
	}
});

//查询结果显示grid
Ext.define('Foss.predeliver.printArriveSheet.resultGrid', {
	extend : 'Ext.grid.Panel',
	title : predeliver.printArriveSheet.i18n('pkp.predeliver.printArriveSheet.arriveSheetInfo'), //到达联信息,
	frame : true,
	//收缩
	collapsible : true,
	//动画收缩
	animCollapse : true,
	cls : 'autoHeight',
	bodyCls : 'autoHeight',
	emptyText : predeliver.printArriveSheet.i18n('pkp.predeliver.printArriveSheet.emptyText'), //查询结果为空,
	selModel : Ext.create('Ext.selection.CheckboxModel'),
	columns : [{
		header : '',
		align : 'center',
		dataIndex : '',
		flex : 1,
		renderer : function(value, cellmeta, record, rowIndex, columnIndex,
				store) {
			/*var targetOrgCode = record.data["targetOrgCode"];
			//获取当前用户设置的当前部门编码
			var currentDeptCode = FossUserContext.getCurrentDeptCode();*/

			var result = '';
			//非到达部门库存用黄旗表示
			if (record.data.isSelfFlg != 'Y') {
				result = '<div class="foss_icons_pkp_flagyellow"></div>'
						+ result;
			}
			//判断是否含签收单用绿旗表示  NO_RETURN_BILL == 无需返单
			if (record.data.returnBillType != 'NONE'
					&& !Ext.isEmpty(record.data.returnBillType)) {
				result = '<div class="foss_icons_pkp_flaggreen"></div>'
						+ result;
			}
			//判断是否含有未受理更改单  001 状态为待审核  002 ：待受理  红旗表示
			//更改单状态 -- 待审核、起草 PRE_AUDIT
			//待受理、审核同意  PRE_ACCECPT
			if (!Ext.isEmpty(record.data.waybillrfcStatus)
					&& (record.data.waybillrfcStatus == 'PRE_AUDIT' || record.data.waybillrfcStatus == 'PRE_ACCECPT')) {
				result = '<div class="foss_icons_pkp_flagred"></div>' + result;
			}
			//增加支付状态类型，可以筛选出"网上支付"未付款运单； 2、对"网上支付"未付款的运单，在查询出来之后进行颜色标识
			if (!Ext.isEmpty(record.data.paidMethod)
					&& (record.data.paidMethod == 'OL' && record.data.isPay == 'N')) {
				result = '<div class="foss_icons_pkp_flagblue"></div>' + result;
			}
			//判断是否打印
			if (record.data.isPrinted == 'Y') {
				result = '<div class="foss_icons_pkp_yes"></div>' + result;
			}
			return result;
		}
	}, {
		header : predeliver.printArriveSheet.i18n('pkp.predeliver.printArriveSheet.waybillNo'), //运单号,
		align : 'center',
		dataIndex : 'waybillNo',
		flex : 1
	}, {
		header : predeliver.printArriveSheet.i18n('pkp.predeliver.printArriveSheet.receiveMethod'), //提货方式
		align : 'center',
		dataIndex : 'receiveMethod',
		flex : 1
	}, {
		header : predeliver.printArriveSheet.i18n('pkp.predeliver.printArriveSheet.receiveCustomerName'), //收货人,
		align : 'center',
		dataIndex : 'receiveCustomerContact',
		flex : 1,
		renderer : function(value, cellmeta, record, rowIndex, columnIndex, store){
			//标示大客户
		  	if(record.data.receiveBigCustomer == 'Y'){
		  		value = '<div class="big_Customer_pic_common"></div>' + value  ;
		  	}
		  	return value;
		}
	}, {
		header : predeliver.printArriveSheet.i18n('pkp.predeliver.printArriveSheet.telephone'), //电话,
		align : 'center',
		dataIndex : 'receiveCustomerMobilephone',
		flex : 1
	}, {
		header : predeliver.printArriveSheet.i18n('pkp.predeliver.printArriveSheet.goodsName1'), //货物名称,
		align : 'center',
		xtype: 'ellipsiscolumn',
		dataIndex : 'goodsName',
		flex : 1
	}, {
		header : predeliver.printArriveSheet.i18n('pkp.predeliver.printArriveSheet.goodsQtyTotal'), //件数,
		align : 'center',
		dataIndex : 'arriveNotoutGoodsQty',
		flex : 1
	}, {
		header : predeliver.printArriveSheet.i18n('pkp.predeliver.printArriveSheet.receiveCustomerAddress'), //地址,
		align : 'center',
		xtype: 'ellipsiscolumn',
		dataIndex : 'receiveCustomerAddress',
		flex : 1
	}, {
		header : predeliver.printArriveSheet.i18n('pkp.predeliver.printArriveSheet.printUserName'), //打印人
		align : 'center',
		xtype: 'ellipsiscolumn',
		dataIndex : 'printUserName',
		flex : 1
	}, {
		header : predeliver.printArriveSheet.i18n('pkp.predeliver.printArriveSheet.printTime'), //打印时间
		align : 'center',
		xtype: 'ellipsiscolumn',
		dataIndex : 'printTime',
		flex : 1
	}, {
		header : predeliver.printArriveSheet.i18n('pkp.predeliver.printArriveSheet.printOrgName'), //打印部门
		align : 'center',
		xtype: 'ellipsiscolumn',
		dataIndex : 'printOrgName',
		flex : 1
	}, {
		header : predeliver.printArriveSheet.i18n('pkp.predeliver.printArriveSheet.printtimes'), //打印次数
		align : 'center',
		xtype: 'ellipsiscolumn',
		dataIndex : 'printtimes',
		flex : 1
	}],

	//表格行可展开的插件
	plugins : [{
				header : true,
				//定义行可展开的插件ID
				pluginId : 'rowexpander_plugin_id',
				//定义插件的类型
				ptype : 'rowexpander',
				//定义行展开模式（单行与多行），默认是多行展开(值true)
				rowsExpander : false,
				//行体内容
				rowBodyElement : 'Foss.predeliver.printArriveSheet.waybillTotalPanels'
			}],
	//给表格行涂层
	viewConfig : {
		stripeRows : false,
		enableTextSelection : true,
		getRowClass : function(record, rowIndex, rp, ds) {
			var goodsQtyTotal = record.get('goodsQtyTotal');
			var arriveGoodsQty = record.get('arriveGoodsQty');
			if (parseInt(goodsQtyTotal) != parseInt(arriveGoodsQty)) {
				return 'predeliver-printArriveSheetIndex-row-B4E0FD';
			}
		}
	},
	printWindow : null,
	getPrintWindow : function() {
		var me = this;
		if (this.printWindow == null) {
			me.printWindow = Ext
					.create('Foss.printArriveSheet.printWindow', me);
		}
		return me.printWindow;
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.predeliver.printArriveSheet.ArriveStore');
		Ext.MessageBox.buttonText.yes = "确定";
		Ext.MessageBox.buttonText.no = "取消";
		me.dockedItems = [{
			xtype : 'toolbar',
			dock : 'top',
			layout : 'column',
			defaults : {
				margin : '0 0 5 3'
			},
			items : [{
				xtype : 'button',
				text : predeliver.printArriveSheet.i18n('pkp.predeliver.printArriveSheet.view'), //预览,
				plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin', {
					seconds: 3
				}),
				disabled:!predeliver.printArriveSheet.isPermission('printarrivesheetindex/printarrivesheetindexprintbutton'),
				hidden:!predeliver.printArriveSheet.isPermission('printarrivesheetindex/printarrivesheetindexprintbutton'),
				handler : function() {
					//alert("打印");
					var mygrid = this.up('gridpanel');
					var selectWaybill = mygrid.getSelectionModel()
							.getSelection();
					if (selectWaybill.length == 0) {
						Ext.ux.Toast.msg("提示信息", "请选择行！");
						return;
					}
					for (var i = 0; i < selectWaybill.length; i++) {
						//更改单状态 -- 待审核、起草 PRE_AUDIT
						//待受理、审核同意  PRE_ACCECPT
						var waybillrfcStatus = selectWaybill[i].data.waybillrfcStatus;
						var paidMethod = selectWaybill[i].data.paidMethod;
						if (!Ext.isEmpty(waybillrfcStatus) 
								&& (waybillrfcStatus == 'PRE_AUDIT' || waybillrfcStatus == 'PRE_ACCECPT')) {
							Ext.ux.Toast.msg("提示信息",
									selectWaybill[i].data.waybillNo
											+ "运单有未受理更改单，不能打印！");
							return;
							break;
						}
						//临时欠款
						/*if(paidMethod == 'DT'){
							Ext.ux.Toast.msg("提示信息",selectWaybill[i].data.waybillNo+"运单为临欠，还未付款，不能打印到达联！");
							return;
							break;
						}*/
					}
					mygrid.getPrintWindow().show();
					mygrid.getPrintWindow().setPrintType('preview');

				}
			}, {
				xtype : 'button',
				text : predeliver.printArriveSheet.i18n('pkp.predeliver.printArriveSheet.print'), //打印,
				plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin', {
					seconds: 3
				}),
				disabled:!predeliver.printArriveSheet.isPermission('printarrivesheetindex/printarrivesheetindexprintbutton'),
				hidden:!predeliver.printArriveSheet.isPermission('printarrivesheetindex/printarrivesheetindexprintbutton'),
				margin : '0 10 5 10',
				handler : function() {
					//alert("打印");
					var mygrid = this.up('gridpanel');
					var selectWaybill = mygrid.getSelectionModel()
							.getSelection();
					if (selectWaybill.length == 0) {
						Ext.ux.Toast.msg("提示信息", "请选择打印行！");
						return;
					}
					for (var i = 0; i < selectWaybill.length; i++) {
						var waybillrfcStatus = selectWaybill[i].data.waybillrfcStatus;
						var paidMethod = selectWaybill[i].data.paidMethod;
						if (!Ext.isEmpty(waybillrfcStatus) 
								&& (waybillrfcStatus == 'PRE_AUDIT' || waybillrfcStatus == 'PRE_ACCECPT')) {
							Ext.ux.Toast.msg("提示信息",
									selectWaybill[i].data.waybillNo
											+ "运单有未受理更改单，不能打印！");
							return;
							break;
						}
						/*//临时欠款
						if(paidMethod == 'DT'){
							Ext.ux.Toast.msg("提示信息",selectWaybill[i].data.waybillNo+"运单为临欠，还未付款，不能打印到达联！");
							return;
							break;
						}*/
					}
					mygrid.getPrintWindow().show();
					mygrid.getPrintWindow().setPrintType('print');

				}
			}, {
				xtype : 'image',
				imgCls : 'foss_icons_pkp_purplebg'
			}, {
				xtype : 'label',
				margin : '0 30 0 10',
				text : predeliver.printArriveSheet.i18n('pkp.predeliver.printArriveSheet.inconsistentInventoryAndBilling') //库存与开单不一致
			}, {
				xtype : 'image',
				imgCls : 'foss_icons_pkp_flagyellow'
			}, {
				xtype : 'label',
				margin : '0 30 0 10',
				text : predeliver.printArriveSheet.i18n('pkp.predeliver.printArriveSheet.nonDepartmentalInventory') //非本部门库存
			}, {
				xtype : 'image',
				imgCls : 'foss_icons_pkp_flagred'
			}, {
				xtype : 'label',
				margin : '0 30 0 10',
				text : predeliver.printArriveSheet.i18n('pkp.predeliver.printArriveSheet.containingNotAcceptingChange') //含未受理更改
			}, {
				xtype : 'image',
				imgCls : 'foss_icons_pkp_flaggreen'
			}, {
				xtype : 'label',
				margin : '0 30 0 10',
				text : predeliver.printArriveSheet.i18n('pkp.predeliver.printArriveSheet.haveSignle') //含签收单
			}, {
				xtype : 'image',
				imgCls : 'foss_icons_pkp_flagblue'
			}, {
				xtype : 'label',
				margin : '0 30 0 10',
				text : predeliver.printArriveSheet.i18n('pkp.predeliver.printArriveSheet.onlinePaymentUnpaid') //网上支付-未付款
			}, {
				xtype : 'image',
				imgCls : 'foss_icons_pkp_yes'
			}, {
				xtype : 'label',
				margin : '0 30 0 10',
				text : predeliver.printArriveSheet.i18n('pkp.predeliver.printArriveSheet.havePrint') //已打印
			}]
		}]
		me.callParent([cfg]);
	}
});

Ext.onReady(function() {
			Ext.QuickTips.init();
			var queryForm = Ext
					.create("Foss.predeliver.printArriveSheet.queryForm");
			//var warnPanel = Ext.create("Foss.predeliver.printArriveSheet.warn");
			var resultGrid = Ext
					.create("Foss.predeliver.printArriveSheet.resultGrid");

			Ext.create('Ext.panel.Panel', {
						id : 'T_predeliver-printArriveSheetIndex_content',
						cls : "panelContentNToolbar",
						bodyCls : 'panelContentNToolbar-body',
						layout : 'auto',
						getQueryForm : function() {
							return queryForm;
						},
						getResultGrid : function() {
							return resultGrid;
						},
						items : [queryForm, resultGrid],
						renderTo : 'T_predeliver-printArriveSheetIndex-body'
					});
		});
