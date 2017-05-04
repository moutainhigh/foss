pickup.PKP_TRACK_TYPE = 'PKP_TRACK_TYPE'; // 跟踪类别的词条	
pickup.PKP_TRACK_METHOD = 'PKP_TRACK_METHOD'; // 跟踪方法的词条	
pickup.HANDOVER_TYPE = 'HANDOVER_TYPE'; // 交接单类型的词条	
pickup.TRACKTYPE_INTO_PORT = 'TRACKTYPE_INTO_PORT'; // 默认显示的跟踪方式	
pickup.TRACKMETHOD_PHONE = 'TRACKMETHOD_PHONE'; // 默认显示的跟踪方法	
/**	
 * @param {} date--比较日期   day--比较日期之前或之后多少天的日期 day>0表示比较日期之后，day<0表示比较日期之前	
 * @return {} 返回目标日期	
 */	
pickup.queryTrackingWaybill.getBillTimeFrom = function(date, day) {	
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
	
pickup.queryTrackingWaybill.getBillTimeTo = function(date, day) {	
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
//跟踪类型	
Ext.define('Foss.pickup.queryTrackingWaybill.TrackTypeStore',{	
	extend: 'Ext.data.Store',	
	fields: [	
		{name: 'code',  type: 'string'},	
		{name: 'name',  type: 'string'}	
	],	
	data: {	
		'items':[	
			{'code':'TRACK_NEXT','name':pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.track.next')},	//接上次跟踪
			{'code':'TRACK_NEW','name':pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.track.new')}, //新跟踪
			{'code':'TRACK_COMPLETE','name':pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.track.complete')}, //已完成跟踪
			{'code':'TRACK_ALL','name':pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.track.all')} //全部
		]	
	},	
	proxy: {	
		type: 'memory',	
		reader: {	
			type: 'json',	
			root: 'items'	
		}	
	}	
});	
	
	
//运输方式	
Ext.define('Foss.pickup.queryTrackingWaybill.TransportationTypeStore',{	
	extend: 'Ext.data.Store',	
	fields: [	
		{name: 'code',  type: 'string'},	
		{name: 'name',  type: 'string'}	
	],	
	data: {	
		'items':[	
			{'code':'','name':pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.complete')},	//'全部'
			{'code':'TRANS_VEHICLE','name':pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.vehicle')},	//'汽运'
			{'code':'TRANS_AIRCRAFT','name':pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.aircraft')}	//'空运'
		]	
	},	
	proxy: {	
		type: 'memory',	
		reader: {	
			type: 'json',	
			root: 'items'	
		}	
	}	
});	

//运单类型
Ext.define('Foss.pickup.queryTrackingWaybill.WaybillType',{	
	extend: 'Ext.data.Store',	
	fields: [	
		{name: 'code',  type: 'string'},	
		{name: 'name',  type: 'string'}	
	],	
	data: {	
		'items':[	
			{'code':'','name':pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.common.all')},	//'全部'
			{'code':'NORMAL','name':pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.common.normal')},	//'普通运单'
			{'code':'EWAYBILL','name':pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.common.ewaybill')}	//'电子运单'
		]	
	},	
	proxy: {	
		type: 'memory',	
		reader: {	
			type: 'json',	
			root: 'items'	
		}	
	}	
});	
	
//签收情况	
Ext.define('Foss.pickup.queryTrackingWaybill.SignTypeStore',{	
	extend: 'Ext.data.Store',	
	fields: [	
		{name: 'code',  type: 'string'},	
		{name: 'name',  type: 'string'}	
	],	
	data: {	
		'items':[	
			{'code':'0','name':pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.abnormal.sign')},	// '非正常签收'
			{'code':'1','name':pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.normal.sign')}	// '正常签收'
		]	
	},	
	proxy: {	
		type: 'memory',	
		reader: {	
			type: 'json',	
			root: 'items'	
		}	
	}	
});	
	
	
//是否签收单	
Ext.define('Foss.pickup.queryTrackingWaybill.SignBillTypeStore',{	
	extend: 'Ext.data.Store',	
	fields: [	
		{name: 'code',  type: 'string'},	
		{name: 'name',  type: 'string'}	
	],	
	data: {	
		'items':[	
			{'code':'','name':pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.complete')}, // 全部	
			{'code':'Y','name':pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.have')},	
			{'code':'N','name':pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.have.not')}	
		]	
	},	
	proxy: {	
		type: 'memory',	
		reader: {	
			type: 'json',	
			root: 'items'	
		}	
	}	
});	
	
	
//跟踪方式	
Ext.define('Foss.pickup.queryTrackingWaybill.TrackWayStore',{	
	extend: 'Ext.data.Store',	
	fields: [	
		{name: 'code',  type: 'string'},	
		{name: 'name',  type: 'string'}	
	],	
	data: {	
		'items':[	
			{'code':'0','name':pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.tel.feedback')},	// '电话反馈'
			{'code':'1','name':pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.gis.track')}	//'GIS跟踪'
		]	
	},	
	proxy: {	
		type: 'memory',	
		reader: {	
			type: 'json',	
			root: 'items'	
		}	
	}	
});	
//跟踪方式	
Ext.define('Foss.pickup.queryTrackingWaybill.WaybillTypeStore',{	
	extend: 'Ext.data.Store',	
	fields: [	
		{name: 'code',  type: 'string'},	
		{name: 'name',  type: 'string'}	
	],	
	data: {	
		'items':[	
			{'code':'EWAYBILL','name':pickup.queryTrackingWaybill.i18n('pkp.pickup.cashDiffReport.queryTrackingWaybill.type.ewaybill')},	// 电子运单
			{'code':'COMMONS','name':pickup.queryTrackingWaybill.i18n('pkp.pickup.cashDiffReport.queryTrackingWaybill.type.commonswaybill')},	//普通运单
			{'code':'','name':pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.complete')}	//全部
		]
	},	
	proxy: {	
		type: 'memory',	
		reader: {	
			type: 'json',	
			root: 'items'	
		}	
	}	
});	
	
/**
 * 开单管理==》跟踪运单
 * 添加新的查询选项字段：  出发客户群
 * 
 * @author 321519-zhangweisheng 
 * */
Ext.define('Foss.pickup.queryTrackingWaybill.SendCustomerGroup',{	
	extend: 'Ext.data.Store',	
	fields: [	
		{name: 'code',  type: 'string'},	
		{name: 'name',  type: 'string'}	
	],	
	data: {	
		'items':[	
			{'code':'NEWCUST','name':pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.sendcustomergroup.type.newcustomergroup')},	// 新客户群
			{'code':'VIP','name':pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.sendcustomergroup.type.vipcustomergroup')},	//vip客户群
			{'code':'OMNI-ACTIVE','name':pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.sendcustomergroup.type.allnetactivegroup')},	//全网活跃群
			{'code':'LINER-ACTIVE','name':pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.sendcustomergroup.type.dedicatedactivegroup')},	//专线活跃群
			{'code':'OMNI-UNACTIVE','name':pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.sendcustomergroup.type.allnetnoactivegroup')},	//全网低频群
			{'code':'LINER-UNACTIVE','name':pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.sendcustomergroup.type.dedicatednoactivegroup')},	//专线低频群
			{'code':'LOW-VALUE','name':pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.sendcustomergroup.type.lowpricecustomergroup')},	//低价值群
			{'code':'','name':pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.transport.status.type.complete')}	//全部
		]
	},	
	proxy: {	
		type: 'memory',	
		reader: {	
			type: 'json',	
			root: 'items'	
		}	
	}	
});	

//查询显示列表MODEL	
Ext.define('Foss.pickup.queryTrackingWaybill.QueryResultTackingWayBillModel',{	
	extend: 'Ext.data.Model',	
	fields: [	
	    { name: 'waybillNo',type:'string'}, //单号	
		{ name: 'billTime',	convert : dateConvert}, //收货日期	
		{ name: 'targetOrgName',		type:'string'}, //目的站	
		{ name: 'productCode',			type:'string',	
			convert:function(value) {	
 				return Foss.pkp.ProductData.rendererSubmitToDisplay(value);	
 			}	
		}, //运输性质	
		{ name: 'deliveryCustomerContact',	type:'string'},  //托运人	
		{ name: 'deliveryCustomerMobilephone',	type:'string'},  //托运人电话	
		{ name: 'deliveryCustomerCode',	type:'string'},  //客户编号	
		{ name: 'receiveCustomerContact',	type:'string'}, //收货人	
		{ name: 'receiveBigCustomer',	type:'string'}, //收货人大客户标记
		{ name: 'deliveryBigCustomer',	type:'string'}, //发货人人大客户标记
		{ name: 'receiveCustomerMobilephone',	type:'string'}, //收货人电话	
		{ name: 'stowageActive',type:'string',	
		convert:function(value){	
					 if(value=='Y'){	
						 return pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.yes');	
					 }else{	
						 return pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.no')	
					 }	
				  }}, //是否配载	
	    { name: 'goodsAreaCode',	type:'string'}, //库存专线	
	    { name: 'stockGoodsCount',	type:'string'}, //库存专线	
		{ name: 'signTime',	convert : dateConvert}, //签收时间	
		{ name: 'stock',	type:'string'}, //库存专线	
		{ name: 'storageDay',	type:'string'},//库存天数	
		{ name: 'situation',	type:'string'},//签收状态	
		{ name: 'stockStatus',	type:'string',
			convert:function(value){	
				 if(value=='IN_STOCK'){	
					 return pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.in.stock');	//库存中
				 }else{	
					 return pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.out.stock')//'已出库'	
				 }	
		    }	
		},//库存状况	
		{ name: 'deliverymanName',	type:'string'},//签收人	
		{ name: 'returnBillStatus',	type:'string',convert:function(value){	
					 if(value=='NONE_RETURN_BILL'){	
						 return pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.none.return.bill');	//'未返单'
					 }else if(value=='ALREADY_RETURN_BILL'){	
					 	return pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.already.return.bill');	// '已返单'
					 }else{	
						 return value;	
					 }	
				  }},//签收返单状态	
	  { name: 'waybillType',	type:'string',
			convert:function(value){	
				 if(value==null){	
					 return pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.common.normal');	//普通运单
				 }else if(value=='EWAYBILL'){	
					 return pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.common.ewaybill')//电子运单	
				 }	
		    }	
		},//运单类型
		{ name: 'expressNo',	type:'string'}//返单快递单号
		,//运输状态 
		{ name: 'wayBillStatus',type:'string',	                      // 数据回显 运输状态  --2015-05-05需求添加 @Author 321519 zhangweisheng
			convert:function(value){	
						 if(value=='NORMAL-ON-WAY'){	// 正常在途
							 return pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.transport.status.type.normalonway');	
						 }else if(value=='TIME-ARRIVE-LATE'){	 // 时效晚到
							 return pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.transport.status.type.timearrivelate')	
						 }else if(value=='ARRIVE-THE-DESTINATION'){	// 到达目的站
							 return pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.transport.status.type.arrivethedestination')	
						 }else if(value=='BATCH-LOADING'){	 //分批配载
							 return pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.transport.status.type.batchloading')	
						 }else{	
							 return ''  // 其他	
						 }	
					  }},
		//出发客户群
	   { name: 'sendCustomerGroup',type:'string',	  // 数据回显 出发客户群  --2015-05-05需求添加 @Author 321519 zhangweisheng
		 convert:function(value){	
					 if(value=='VIP'){	
							 return pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.sendcustomergroup.type.vipcustomergroup');   // vip 客户群
						 }
					 else if(value=='LOW-VALUE'){	
						 return pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.sendcustomergroup.type.lowpricecustomergroup')	//低价值群
					 }else if(value=='OMNI-ACTIVE'){	
						 return pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.sendcustomergroup.type.allnetactivegroup')	    // 全网活跃客户
					 }else if(value=='OMNI-UNACTIVE'){	
						 return pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.sendcustomergroup.type.allnetnoactivegroup')	   //全网低频客户
					 }else if(value=='LINER-UNACTIVE'){	
						 return pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.sendcustomergroup.type.dedicatednoactivegroup')	//专线低频客户
					 }else if(value=='LINER-ACTIVE'){	
						 return pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.sendcustomergroup.type.lowpricecustomergroup')	// 专线活跃客户
					 }else if(value=='NEWCUST'){	
						 return pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.sendcustomergroup.type.newcustomergroup')	//  新客户群
					 }else{	
							 return ''  // 其他	
						 }	
					 }},			  
		
		
	]	
	
});	
	
//定义到达联数据store	
Ext.define('Foss.pickup.queryTrackingWaybill.QueryResultTackingWayBillStore', {	
	 extend: 'Ext.data.Store',	
     model: 'Foss.pickup.queryTrackingWaybill.QueryResultTackingWayBillModel',	
     pageSize: 10,	
     proxy: {	
   	//代理的类型为内存代理	
 		type: 'ajax',	
 		//提交方式	
 		actionMethods:'POST',	
 		url:pickup.realPath('queryTrackingWaybill.action'),	
 		//定义一个读取器	
 		reader: {	
 			//以JSON的方式读取	
 			type: 'json',	
 			//定义读取JSON数据的根对象f	
 			root: 'vo.trackingWaybillDtoList',	
 			//返回总数	
			totalProperty : 'totalCount'	
 		}	
     },//事件监听	
 	listeners: {	
 		//查询事件	
 			beforeload : function(s, operation, eOpts) {	
 				//执行查询，首先load查询条件，为全局变量，在查询条件的FORM创建时生成	
 				var queryParams = Ext.getCmp('T_pickup-queryTrackingWaybillIndex_content').getQueryForm().getValues();	
 				var waybillNo = queryParams.waybillNo;	
 				Ext.apply(operation, {	
 					params:{	
 						'vo.conditionDto.trackType':queryParams.trackType,  //跟踪类型	
 						'vo.conditionDto.billTimeFrom':queryParams.billTimeFrom,// 开单时间起/收货日期起	
 						'vo.conditionDto.billTimeTo':queryParams.billTimeTo,	//开单时间止/收货日期止	
 						'vo.conditionDto.waybillNo':queryParams.waybillNo,		//运单号	
 						'vo.conditionDto.deliveryCustomerContact':queryParams.deliveryCustomerContact, //发货客户联系人	
 						'vo.conditionDto.receiveCustomerContact':queryParams.receiveCustomerContact,	//收货客户联系人	
 						'vo.conditionDto.transportType':queryParams.transportType,	//运输类型	
 						'vo.conditionDto.productCode':queryParams.productCode, 		//运输性质	
 						'vo.conditionDto.situation':queryParams.situation,	//签收情况	
 						'vo.conditionDto.returnBillFlg':queryParams.returnBillFlg,	//返单类别	
 						'vo.conditionDto.storageDay':queryParams.storageDay	,	//在库天数
  						'vo.conditionDto.sendCustomerGroup':queryParams.sendCustomerGroup		  // 查询条件  出发客户群  --2015-05-05需求添加 @Author 321519 zhangweisheng
 						}	
 				});		
 			}	
 		}	
    // autoLoad: true	
 });	
	
	
	
//查询条件	
Ext.define('Foss.pickup.queryTrackingWaybill.TrackingWayBillQueryForm',{	
	extend: 'Ext.form.Panel',	
	title: pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.query.condition'),	 // 查询条件
    defaultType : 'textfield',	
	collapsible: true,	
	layout: 'column',	
	cls:'autoHeight',	
	bodyCls:'autoHeight',	
	frame:true,	
	defaults: {	
		margin:'5 10 5 10',	
		anchor: '90%',	
		labelWidth:110	
	},	
	items: [{	
		xtype:'combo',	
		name: 'trackType',	
		fieldLabel: pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.track.type'),	 // 跟踪类别
		value:'TRACK_NEW',	
		valueField:'code', 	
		displayField: 'name',	
		queryMode:'local',	
		triggerAction:'all',	
		editable:false,	
		columnWidth: .33,	
		store: Ext.create('Foss.pickup.queryTrackingWaybill.TrackTypeStore')	
	},{	
		xtype: 'rangeDateField',	
		fieldLabel: pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.receive.time'),	 // 收货日期
		dateType: 'datefield',	
		fromName: 'billTimeFrom',	
		toName: 'billTimeTo',	
		dateType : 'datetimefield_date97',	
		fieldId : 'Foss_pickup_billTime_Id',	
		editable:false,	
		disallowBlank: true,	
		fromValue: Ext.Date.format(pickup.queryTrackingWaybill.getBillTimeFrom(new Date(),-2),'Y-m-d H:i:s'),	
		toValue: Ext.Date.format(pickup.queryTrackingWaybill.getBillTimeTo(new Date(),0),'Y-m-d H:i:s'),	
		columnWidth: .66	
	},{	
		name: 'waybillNo',	
		fieldLabel: pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.waybill.no'),	 // 运单号
		columnWidth:.33,	
		vtype: 'waybill'	
	},{	
		name: 'deliveryCustomerContact',	
		fieldLabel: pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.consignor'),	 // 发货人
		allowBlank:true,	
		columnWidth:.33	
	},{	
		fieldLabel: pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.consignee'),	 // 收货人
		name: 'receiveCustomerContact',	
		allowBlank:true,	
		columnWidth:.33	
	},{	
		xtype:'combo',	
		name: 'transportType',	
		fieldLabel: pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.transport.type'),	 // 运输方式
		value:'',	
		valueField:'code', 	
		displayField: 'name',	
		queryMode:'local',	
		editable:false,	
		triggerAction:'all',	
		columnWidth: .33,	
		store: Ext.create('Foss.pickup.queryTrackingWaybill.TransportationTypeStore')	
	},{	
		xtype:'combo',	
		name: 'productCode',	
		fieldLabel: pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.product.code'),	 // 运输性质
		value:'',	
		valueField:'code', 	
		displayField: 'name',	
		queryMode:'local',	
		editable:false,	
		triggerAction:'all',	
		columnWidth: .33,	
//		store: null
		store: Ext.create('Foss.pkp.ProductStore')	
	},{	
		xtype: 'combobox',	
        name: 'situation',	
        columnWidth: .33,	
        fieldLabel: pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.sign.situation'),	 // 签收情况
        value: '',	
        queryModel:'local',	
		displayField:'valueName',	
		valueField:'valueCode',	
		editable:false,	
		store: FossDataDictionary.getDataDictionaryStore('PKP_SIGN_SITUATION',null,[
		 {'valueCode': 'NO_SIGN', 'valueName': pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.no.sign')},
		 {'valueCode': '', 'valueName': pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.complete')}
	])	
	},{	
		xtype:'combo',	
		name: 'returnBillFlg',	
		fieldLabel: pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.return.bill.flg'),	 // 是否有签收单
		value:'',	
		valueField:'code', 	
		displayField: 'name',	
		queryMode:'local',	
		triggerAction:'all',	
		columnWidth: .33,	
		editable:false,	
		store: Ext.create('Foss.pickup.queryTrackingWaybill.SignBillTypeStore')	
	},{	
		name: 'storageDay',	
		xtype : 'numberfield',
		allowDecimals : false,
		minValue: 0,
		fieldLabel: pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.storage.day'),	 // 库存天数
		columnWidth:.33,	
		maxValue: 999, 
		minValue: 0,
		value: '0'	
	},{	
		xtype:'combo',	
		name: 'waybillType',	
		fieldLabel: pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.common.waybill.type'),	 // 运单类型
		value:'',	
		valueField:'code', 	
		displayField: 'name',	
		queryMode:'local',	
		editable:false,	
		triggerAction:'all',	
		columnWidth: .33,	
//		store: null
		store: Ext.create('Foss.pickup.queryTrackingWaybill.WaybillType')	
	},{	
		xtype:'combo',	
		name: 'sendCustomerGroup',	
		fieldLabel: pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.sendcustomergroup'),	     // 查询条件label  出发客户群 --2015-05-05需求添加 @Author 321519 zhangweisheng
		value:'',	
		valueField:'code', 	
		displayField: 'name',	
		queryMode:'local',	
		editable:false,	
		triggerAction:'all',	
		columnWidth: .33,	
		store: Ext.create('Foss.pickup.queryTrackingWaybill.SendCustomerGroup')	
	}, {	
		border: 1,
		xtype:'container',	
		columnWidth:1,	
		defaultType:'button',	
		layout:'column',	
		items:[{	
			text:pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.reset'),	 // 重置
			columnWidth:.08,	
			handler:function(){	
				var myForm = this.up('form').getForm();	
				myForm.reset();	
				myForm.findField('billTimeFrom').setValue(Ext.Date.format(pickup.queryTrackingWaybill.getBillTimeFrom(new Date(),-2),'Y-m-d H:i:s'));	
				myForm.findField('billTimeTo').setValue(Ext.Date.format(pickup.queryTrackingWaybill.getBillTimeTo(new Date(),0),'Y-m-d H:i:s'));	
			}	
		},{	
			xtype: 'container',	
			border : false,	
			columnWidth:.76,	
			html: '&nbsp;'	
		},{	
			xtype : 'button',
			text :pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.export'),//导出
			columnWidth:.08,	
			cls:'yellow_button',	
			 handler : function(button) {
					//获得查询条件的值
	 				var queryTrackingWaybillsForm = Ext.getCmp('T_pickup-queryTrackingWaybillIndex_content').getQueryForm();	
	 				if (queryTrackingWaybillsForm != null) {
						var queryParams = queryTrackingWaybillsForm.getValues();
						if(!Ext.fly('downloadAttachFileForm')){
						    var frm = document.createElement('form');
						    frm.id = 'downloadAttachFileForm';
						    frm.style.display = 'none';
						    document.body.appendChild(frm);
						}
						var billTimeFrom = queryParams.billTimeFrom, billTimeTo = queryParams.billTimeTo;	
						if (Ext.isEmpty(billTimeFrom) || Ext.isEmpty(billTimeTo)) {	
							Ext.ux.Toast.msg(pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.tip'), pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.goods.time.required.input'), 'error', 3000);	// '起止收货日期为必填项！'
							return;	
						}	
						var result = Ext.Date.parse(billTimeTo,'Y-m-d H:i:s') - Ext.Date.parse(billTimeFrom,'Y-m-d H:i:s');	
						if(result / (24 * 60 * 60 * 1000) >= 4){	
							Ext.ux.Toast.msg(pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.tip'), pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.goods.time.must.within.three.days'), 'error', 3000); // '起止日期相隔不能超过3天！'
							return;	
						}	
						//获取查询出来的异常信息
						var TrackingWaybillsGridStore = Ext.getCmp('T_pickup-queryTrackingWaybillIndex_content').getResultGridPanel().getStore();	
						//若异常信息不为空
						if(TrackingWaybillsGridStore.getCount()!=0){
							Ext.Ajax.request({
								url:pickup.realPath('trackingWaybillExport.action'),
								form: Ext.fly('downloadAttachFileForm'),
								method : 'POST',
								isUpload: true,
								params : {
									'vo.conditionDto.trackType':queryParams.trackType,  //跟踪类型	
			 						'vo.conditionDto.billTimeFrom':queryParams.billTimeFrom,// 开单时间起/收货日期起	
			 						'vo.conditionDto.billTimeTo':queryParams.billTimeTo,	//开单时间止/收货日期止	
			 						'vo.conditionDto.waybillNo':queryParams.waybillNo,		//运单号	
			 						'vo.conditionDto.deliveryCustomerContact':queryParams.deliveryCustomerContact, //发货客户联系人	
			 						'vo.conditionDto.receiveCustomerContact':queryParams.receiveCustomerContact,	//收货客户联系人	
			 						'vo.conditionDto.transportType':queryParams.transportType,	//运输类型	
			 						'vo.conditionDto.productCode':queryParams.productCode, 		//运输性质	
			 						'vo.conditionDto.situation':queryParams.situation,	//签收情况	
			 						'vo.conditionDto.returnBillFlg':queryParams.returnBillFlg,	//返单类别	
			 						'vo.conditionDto.storageDay':queryParams.storageDay	,	//在库天数
			 						'vo.conditionDto.waybillType':queryParams.waybillType		//在库天数
								},
							});
						}else{
							//或者提示不能导出
							Ext.ux.Toast.msg(pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.tip'),pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.exceptionInfo.notExport'), 'error', 3000);
						}
					}
			 }
		},{	
			text:pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.query'),	 // 查询
			disabled:!pickup.queryTrackingWaybill.isPermission('querytrackingwaybillindex/querytrackingwaybillindexquerybutton'),
			hidden:!pickup.queryTrackingWaybill.isPermission('querytrackingwaybillindex/querytrackingwaybillindexquerybutton'),
			columnWidth:.08,	
			cls:'yellow_button',	
			handler:function(){	
				var myForm = this.up('form').getForm();	
				var billTimeFrom = myForm.getValues().billTimeFrom, billTimeTo = myForm.getValues().billTimeTo;	
				if (Ext.isEmpty(billTimeFrom) || Ext.isEmpty(billTimeTo)) {	
					Ext.ux.Toast.msg(pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.tip'), pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.goods.time.required.input'), 'error', 3000);	// '起止收货日期为必填项！'
					return;	
				}	
				var result = Ext.Date.parse(billTimeTo,'Y-m-d H:i:s') - Ext.Date.parse(billTimeFrom,'Y-m-d H:i:s');	
				if(result / (24 * 60 * 60 * 1000) >= 4){	
					Ext.ux.Toast.msg(pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.tip'), pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.goods.time.must.within.three.days'), 'error', 3000); // '起止日期相隔不能超过3天！'
					return;	
				}	
				if(myForm.isValid()){	
					var grid = Ext.getCmp('T_pickup-queryTrackingWaybillIndex_content').getResultGridPanel();	
					grid.getPagingToolbar().moveFirst();	
				}	
			}	
		}]	
	}]	
});	
	
// 结果显示区域	
Ext.define('Foss.pickup.queryTrackingWaybill.TrackingWayBillGridPanel',{	
	extend: 'Ext.grid.Panel',	
	columnLines: true,	
	cls : 'autoHeight',	
	bodyCls : 'autoHeight',	
	// 增加滚动条
	emptyText: pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.query.result.empty'),	 // 查询结果为空
    frame: true,	
    title:pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.result.display.area'),	 // 结果显示区域
	collapsible: true,	
	autoScroll:true,	
	columns:[	
		{text: pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.waybill.no'),dataIndex : 'waybillNo', align: 'center',xtype : 'ellipsiscolumn',width : 80},	 // 运单号
		{text: pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.receive.time'),dataIndex : 'billTime', width : 140, align: 'center',	 // 收货日期
			renderer : function(value) {	
				if (value != null) {	
					var date = Ext.Date.format(new Date(value), 'Y-m-d H:i:s');	
					return date;	
				} else {	
					return null;	
				}	
			} 	
		},	
		{text: pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.destination'),dataIndex : 'targetOrgName', align: 'center', width : 80,xtype : 'ellipsiscolumn'},	 // 目的站
		{text: pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.product.code'),dataIndex : 'productCode', align: 'center', width : 80,xtype : 'ellipsiscolumn'},	 // 运输性质
		{text: pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.consignment.people'),dataIndex : 'deliveryCustomerContact', align: 'center', width : 80,xtype : 'ellipsiscolumn'
			,renderer : function(value, cellmeta, record, rowIndex, columnIndex, store){
				//标示大客户
			  	if(record.data.deliveryBigCustomer == 'Y'){
			  		value = '<div class="big_Customer_pic_common"></div>' + value  ;
			  	}
			  	return value;
			}
		},	 // 托运人
		{text: pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.consignor.phone'),dataIndex : 'deliveryCustomerMobilephone', align: 'center', width : 90,xtype : 'ellipsiscolumn'},	 // 托运人电话
		{text: pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.customer.code'),dataIndex : 'deliveryCustomerCode', align: 'center', width : 80,xtype : 'ellipsiscolumn'},	 // 客户编号
		{text: pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.consignee'),dataIndex : 'receiveCustomerContact', align: 'center', width : 80,xtype : 'ellipsiscolumn'
			,renderer : function(value, cellmeta, record, rowIndex, columnIndex, store){
				//标示大客户
			  	if(record.data.receiveBigCustomer == 'Y'){
			  		value = '<div class="big_Customer_pic_common"></div>' + value  ;
			  	}
			  	return value;
			}
		},	 // 收货人
		{text: pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.consignee.phone'),dataIndex : 'receiveCustomerMobilephone', align: 'center', width : 90,xtype : 'ellipsiscolumn'},	 // 收货人电话
		{text: pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.stowage.active'),dataIndex : 'stowageActive', align: 'center', width : 80,xtype : 'ellipsiscolumn'},	 // 是否配载
		{text: pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.goodsAreaCode'),dataIndex : 'goodsAreaCode', align: 'center', width : 80,xtype : 'ellipsiscolumn'},	 // 库区
		{text: pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.stockQty'),dataIndex : 'stockGoodsCount', align: 'center', width : 80,xtype : 'ellipsiscolumn'},	 // 库存件数
		{text: pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.sign.time'),dataIndex : 'signTime', align: 'center', width : 140,	 // 签收时间
			renderer : function(value) {	
				if (value != null) {	
					var date = Ext.Date.format(new Date(value), 'Y-m-d H:i:s');	
					return date;	
				} else {	
					return null;	
				}	
			} 	
		},	
		{text: pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.storage.line'),dataIndex : 'stock', align: 'center', width : 80,xtype : 'ellipsiscolumn'},	 // 库存专线
		{text: pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.storage.day'),dataIndex : 'storageDay', align: 'center', width : 80,xtype : 'ellipsiscolumn'},	 // 库存天数
		{text: pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.sign.status'),dataIndex : 'situation', align: 'center', width : 80,xtype : 'ellipsiscolumn',	 // 签收状态
			renderer:function(value){	
		    			return FossDataDictionary.rendererSubmitToDisplay(value,'PKP_SIGN_SITUATION');	
				}	
			}, // == 签收情况	
		{text: pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.storage.status'),dataIndex : 'stockStatus', align: 'center', width : 80,xtype : 'ellipsiscolumn'},	 // 库存状态
		{text: pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.sign.person'),dataIndex : 'deliverymanName', align: 'center', width : 80,xtype : 'ellipsiscolumn'},	 // 签收人
		{text: pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.return.bill.status'),dataIndex : 'returnBillStatus', align: 'center', width : 80,xtype : 'ellipsiscolumn'},	 // 签收返单状态
		{text: pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.common.waybill.type'),dataIndex : 'waybillType', align: 'center', width : 80,xtype : 'ellipsiscolumn'},	 // 运单类型
		{text: pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.transport.status'),dataIndex : 'wayBillStatus', align: 'center', width : 80,xtype : 'ellipsiscolumn'},	 // 运输状态
		{text: pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.sendcustomergroup'),dataIndex : 'sendCustomerGroup', align: 'center', width : 80,xtype : 'ellipsiscolumn'},	 // 出发客户群
		{text: pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.express.no'),dataIndex : 'expressNo', align: 'center', width : 80,xtype : 'ellipsiscolumn'}	 // 返单快递单号
		],		
	 viewConfig: {	
	 	//表格可复制	
        enableTextSelection: true,	
        stripeRows: false	
	},	
	plugins: [{	
		ptype: 'rowexpander',	
		rowsExpander: false,	
		//有头部加号	
		pluginId : 'Foss.pickup.queryTrackingWaybill.TrackingWayBillGrid_Id',	
		rowBodyElement: 'Foss.pickup.queryTrackingWaybill.TrackingWayBillTaskForm'	
	}],	
	pagingToolbar : null,	
  	getPagingToolbar : function() {	
  		if (this.pagingToolbar == null) {	
  			this.pagingToolbar = Ext.create('Deppon.StandardPaging', {	
  				store : this.store,
  				plugins: 'pagesizeplugin',
				displayInfo: true	
  			});	
  		}	
  		return this.pagingToolbar;	
  	},	
	constructor: function(config){	
		var me = this,	
			cfg = Ext.apply({}, config);	
		me.store = Ext.create('Foss.pickup.queryTrackingWaybill.QueryResultTackingWayBillStore');	
		me.bbar = me.getPagingToolbar();	
		me.callParent([cfg]);	
	}	
});	
	
	
Ext.define('Foss.pickup.queryTrackingWaybill.TrackingWayBillTaskForm',{	
	extend: 'Ext.form.Panel',	
	title: pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.track.operator'),	 // 跟踪操作
    defaultType : 'textfield',	
	layout: 'column',	
	cls:'autoHeight',	
	bodyCls:'autoHeight',	
	defaults: {	
		margin:'5 10 5 10',	
		anchor: '90%',	
		labelWidth:90	
	},	
	items: [{	
		xtype:'combo',	
		name: 'trackType',	
		fieldLabel: pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.track.type'),	 // 跟踪类别
		value:pickup.TRACKTYPE_INTO_PORT,	
		labelWidth: 60,	
		valueField:'valueCode', 	
		displayField: 'valueName',	
		queryMode:'local',	
		triggerAction:'all',	
		columnWidth: .25,	
		allowBlank:false,	
		editable:false,	
		store: FossDataDictionary.getDataDictionaryStore(pickup.PKP_TRACK_TYPE, null)	
	},{	
		xtype:'combo',	
		name: 'trackMethod',	
		fieldLabel: pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.track.mode'),	 // 跟踪方式
		value:pickup.TRACKMETHOD_PHONE,	
		labelWidth: 60,	
		valueField:'valueCode', 	
		displayField: 'valueName',	
		queryMode:'local',	
		triggerAction:'all',	
		columnWidth: .25,	
		allowBlank:false,	
		editable:false,	
		store: FossDataDictionary.getDataDictionaryStore(pickup.PKP_TRACK_METHOD, null)	
	},{	
		name: 'contactMethod',	
		labelWidth: 60,	
		fieldLabel: pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.contact.method'),	 // 联系方式
		allowBlank:false,	
		columnWidth:.25	
	},{	
		name: 'trackMan',	
		fieldLabel: pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.track.user'),	 // 跟踪对象
		labelWidth: 60,	
		allowBlank:false,	
		columnWidth:.25	
	},{	
		xtype: 'textarea',	
		fieldLabel: pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.track.content'),	 // 跟踪内容
		labelWidth: 60,	
		autoScroll:true,	
		allowBlank:false,	
		name: 'trackContent',	
		columnWidth:.54	
	},{	
		xtype: 'radiofield',	
		boxLabel  : pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.next.to.continue.tracking'), // '下次继续跟踪'
		name      : 'trackStatus',	
		inputValue: 'TRACK_NEXT', 	
		checked   : true,	
		columnWidth: .12	
	},{	
		xtype: 'radiofield',	
		boxLabel  : pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.complete.this.bill.track'), //完成本单跟踪
		name      : 'trackStatus', 	
		inputValue: 'TRACK_COMPLETE', 	
		columnWidth: .12	
	},{	
		name: 'waybillNo',	
		labelWidth: 60,	
		fieldLabel: pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.waybill.no'),	 // 运单号
		hidden: true,	
		columnWidth:.25	
	},{	
		xtype:'container',	
		defaultType:'button',	
		columnWidth:.3,	
		layout:'column',	
		items:[{	
			text:pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.confirm'),	 // 确认
			disabled:!pickup.queryTrackingWaybill.isPermission('querytrackingwaybillindex/querytrackingwaybillindexconfirmbutton'),
			hidden:!pickup.queryTrackingWaybill.isPermission('querytrackingwaybillindex/querytrackingwaybillindexconfirmbutton'),
			handler:function(){	
				var myForm = this.up('form').getForm();	
				var saveParams = myForm.getValues();
				var grid = Ext.getCmp('T_pickup-queryTrackingWaybillIndex_content').getResultGridPanel();	
				var plugin = grid.getPlugin('Foss.pickup.queryTrackingWaybill.TrackingWayBillGrid_Id');	
				if(myForm.isValid()){	
					Ext.Ajax.request({	
					    url: pickup.realPath('addTrackWaybill.action'),	
					   /* params: {	
					    	'vo.arriveSheet.id': ids,	
					    	'vo.arriveSheet.waybillNo': waybillNos,	
					    	'vo.arriveSheetGoodsQtys': arriveSheetGoodsQtys	
					    },*/	
					    jsonData:{'vo':{'waybillTrackInfoEntity':saveParams}},	
					    success: function(response){	
					    	var json = Ext.decode(response.responseText);	
					    	Ext.ux.Toast.msg(pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.tip'),json.message);	
					        grid.store.load();	
					        var data = {'waybillNo' : saveParams.waybillNo};	
							var record = Ext.ModelManager.create(data,'Foss.pickup.queryTrackingWaybill.QueryResultTackingWayBillModel');	
					        plugin.toggleRow(grid.getView().getNode(record));		
					    },	
					    exception: function(response){	
							var json = Ext.decode(response.responseText);	
	              			Ext.ux.Toast.msg(pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.tip'), json.message, 'error', 3000);	
						}	
					});	
				}	
			}	
		}]	
	}],	
	getTrackingFormDetailFormPanel : function(){	
		if(this.trackingFormDetailFormPanel == null){	
			this.trackingFormDetailFormPanel = Ext.create('Foss.pickup.queryTrackingWaybill.TrackingFormDetailWindow');	
		}	
		return this.trackingFormDetailFormPanel;	
	},	
	
	bindData : function(record, grid, rowBodyElement){	
		var me = this;	
		this.getForm().findField('waybillNo').setValue(record.data.waybillNo);	
		//跟踪对象为托运人	
		this.getForm().findField('trackMan').setValue(record.data.deliveryCustomerContact);	
		//联系方式为托运人电话	
		this.getForm().findField('contactMethod').setValue(record.data.deliveryCustomerMobilephone);	
		//this.loadRecord(record);	
		var trackInfoWindow = this.getTrackingFormDetailFormPanel();	
		Ext.Ajax.request({	
					   url: pickup.realPath('queryTrackingWaybillInfo.action'),	
					   params: {	
					    	'vo.conditionDto.waybillNo': record.data.waybillNo	
					    },	
					    success: function(response){	
					    	trackInfoWindow.show();	
					    	var result = Ext.decode(response.responseText);	
					    	// 跟踪信息	
					    	trackInfoWindow.getTrackingGridInfo().getStore().loadData(result.vo.queryTrackingWaybillDto.waybillTrackInfoEntityList);	
					    	// 配置信息	
					    	trackInfoWindow.getAutoStowGridInfo().getStore().loadData(result.vo.queryTrackingWaybillDto.assemblyDtoList);	
					    	// 交接信息	
					    	trackInfoWindow.getAssociateGridInfo().getStore().loadData(result.vo.queryTrackingWaybillDto.handOverDtoList);	
					    	// 库存情况	
					    	trackInfoWindow.getStoreDetailGridPanel().getStore().loadData(result.vo.queryTrackingWaybillDto.stockDtoList);	
					    	trackInfoWindow.getDockedItems('toolbar[dock="bottom"]');	
					    	var items = trackInfoWindow.items;	
					    	items.first().items.items[0].setValue(record.data.waybillNo);	
					    },	
					    exception: function(response){	
							var json = Ext.decode(response.responseText);	
	              			Ext.ux.Toast.msg(pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.tip'), json.message, 'error', 3000);	
						}	
					});	
	}	
});	
	
//运单跟踪情况MODEL	
Ext.define('Foss.pickup.queryTrackingWaybill.TackingWayBillModel',{	
	extend: 'Ext.data.Model',	
	fields: [	
	    { name: 'waybillNo',type:'string'}, //单号	
		{ name: 'trackType',type:'string', 	
			convert:function(value) {	
				return FossDataDictionary.rendererSubmitToDisplay(value, pickup.PKP_TRACK_TYPE);	
			}	
		}, //跟踪类型	
		{ name: 'trackMan',type:'string'}, //跟踪对象	
		{ name: 'trackContent',type:'string'}, //跟踪内容	
		{ name: 'operator',	type:'string'},  //跟踪人	
		{ name: 'operateTime',convert:dateConvert} //跟踪时间	
	]	
});	
//跟踪信息store	
Ext.define('Foss.pickup.queryTrackingWaybill.TrackingStore',{	
	extend: 'Ext.data.Store',	
	model: 'Foss.pickup.queryTrackingWaybill.TackingWayBillModel',	
	//是否自动查询	
	autoLoad: false,	
	proxy : {	
		type : 'memory',	
		reader : {	
			type : 'json',	
			root : 'items'	
		}	
	},	
	constructor : function (config){	
		var me = this,	
		cfg = Ext.apply({}, config);	
		me.callParent([cfg]);	
	}	
});	
//跟踪信息Grid	
Ext.define('Foss.pickup.queryTrackingWaybill.TrackingGridInfo',{	
	title: pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.track.information'),	 // 跟踪信息
	extend: 'Ext.grid.Panel',	
	frame: true,	
	emptyText: pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.no.data'),	 // 暂无数据
	viewConfig: {	
	 	//表格可复制	
        enableTextSelection: true,	
        stripeRows: false	
	},	
	store: Ext.create('Foss.pickup.queryTrackingWaybill.TrackingStore'),	
	columns:[	
	{text: pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.track.content'),dataIndex : 'trackContent', align: 'center', flex: 1,xtype : 'ellipsiscolumn'},	 // 跟踪内容
	{text: pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.track.type'),dataIndex : 'trackType', align: 'center', width : 100},	 // 跟踪类别
	{text: pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.track.user'),dataIndex : 'trackMan', align: 'center', width : 100},	 // 跟踪对象
	{text: pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.track.people'),dataIndex : 'operator', align: 'center', width : 100},	 // 跟踪人
	{text: pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.track.time'),dataIndex : 'operateTime', align: 'center', width : 140,xtype : 'datecolumn',format : 'Y-m-d H:i:s'}	 // 跟踪时间
	],	
	constructor: function(config){	
		var me = this,	
			cfg = Ext.apply({}, config);	
		me.callParent([cfg]);	
	}	
});	
	
/*******************配载信息************/	
//配载信息 Model	
Ext.define('Foss.pickup.queryTrackingWaybill.AutoStowInfoModel',{	
	extend: 'Ext.data.Model',	
	fields: [	
	    {name: 'assemblyToolNo' , type: 'string'},	
		{name: 'departureTime', convert:dateConvert},	
		{name: 'planArriveTime', convert:dateConvert}	
	]	
});	
//配载信息store	
Ext.define('Foss.pickup.queryTrackingWaybill.AutoStowInfoStore',{	
	extend: 'Ext.data.Store',	
	model: 'Foss.pickup.queryTrackingWaybill.AutoStowInfoModel',	
	//是否自动查询	
	autoLoad: false,	
	proxy : {	
		type : 'memory',	
		reader : {	
			type : 'json',	
			root : 'items'	
		}	
	},	
	constructor : function (config){	
		var me = this,	
			cfg = Ext.apply({}, config);	
		me.callParent([cfg]);	
	}	
});	
//配载信息Grid	
Ext.define('Foss.pickup.queryTrackingWaybill.AutoStowGridInfo',{	
	title: pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.stowage.information'),	 // 配载信息
	extend: 'Ext.grid.Panel',	
	frame: true,	
	emptyText: pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.no.data'),	 // 暂无数据
	viewConfig: {	
	 	//表格可复制	
        enableTextSelection: true,	
        stripeRows: false	
	},	
	store: Ext.create('Foss.pickup.queryTrackingWaybill.AutoStowInfoStore'),	
	columns:[	
	{text: pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.vehicle.or.flight'),dataIndex : 'assemblyToolNo', align: 'center', flex: 1},	 // 车次/航班
	{text: pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.departure.time'),dataIndex : 'departureTime', align: 'center', flex: 1,	 // 发车时间
		renderer : function(value) {	
			if (value != null) {	
				var date = Ext.Date.format(new Date(value), 'Y-m-d H:i:s');	
				return date;	
			} else {	
				return null;	
			}	
		} 		
	},	
	{text: pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.plan.arrive.time'),dataIndex : 'planArriveTime', align: 'center', flex: 1,	 // 预到时间
		renderer : function(value) {	
			if (value != null) {	
				var date = Ext.Date.format(new Date(value), 'Y-m-d H:i:s');	
				return date;	
			} else {	
				return null;	
			}	
		} 		
	}	
	],	
	constructor: function(config){	
		var me = this,	
			cfg = Ext.apply({}, config);	
		me.callParent([cfg]);	
	}	
});	
	
/*******************交接情况************/	
//交接单Model	
Ext.define('Foss.pickup.queryTrackingWaybill.AssociatInfoModel',{	
	extend: 'Ext.data.Model',	
	fields: [	
	    {name: 'createTime' , convert:dateConvert},	
		{name: 'leaveTime', convert:dateConvert},	
		{name: 'handOverNo', type: 'string'},	
		{name: 'origOrgName', type: 'string'},	
		{name: 'handOverType', type: 'string'},	
		{name: 'isPreHandOverBill', type: 'string',	
			convert:function(value){	
				 if(value=='Y'){	
					 return pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.yes');	//'是'
				 }else{	
					 return pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.no')//'否'	
				 }	
		    }	
		},	
		{name: 'destOrgName', type: 'string'},	
		{name: 'arriveTime', convert:dateConvert},	
		{name: 'goodsQty'}	
		]	
})	
//交接Store	
Ext.define('Foss.pickup.queryTrackingWaybill.AssociateStore',{	
	extend: 'Ext.data.Store',	
	model: 'Foss.pickup.queryTrackingWaybill.AssociatInfoModel',	
	//是否自动查询	
	autoLoad: false,	
	proxy : {	
		type : 'memory',	
		reader : {	
			type : 'json',	
			root : 'items'	
		}	
	},	
	constructor : function (config){	
		var me = this,	
		cfg = Ext.apply({}, config);	
		me.callParent([cfg]);	
	}	
})	
//交接情况 Grid	
Ext.define('Foss.pickup.queryTrackingWaybill.AssociateGridInfo',{	
	extend: 'Ext.grid.Panel',	
	title:pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.handover.situation'),	 // 交接情况
	frame: true,	
	viewConfig: {	
	 	//表格可复制	
        enableTextSelection: true,	
        stripeRows: false	
	},	
	store: Ext.create('Foss.pickup.queryTrackingWaybill.AssociateStore'),	
	emptyText: pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.no.data'),	 // 暂无数据
	columns:[	
	{text: pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.handover.save.time'),dataIndex : 'createTime', align: 'center', flex: 1,	 // 交接单保存时间
		renderer : function(value) {	
			if (value != null) {	
				var date = Ext.Date.format(new Date(value), 'Y-m-d H:i:s');	
				return date;	
			} else {	
				return null;	
			}	
		} 	
	},	
	{text: pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.leave.time'),dataIndex : 'leaveTime', align: 'center', flex: 1,	 // 出发时间
		renderer : function(value) {	
			if (value != null) {	
				var date = Ext.Date.format(new Date(value), 'Y-m-d H:i:s');	
				return date;	
			} else {	
				return null;	
			}	
		} 	
	},	
	{text: pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.handover.no'),dataIndex : 'handOverNo', align: 'center', flex: 1,xtype : 'ellipsiscolumn'},	 // 交接单编号
	{text: pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.orig.org'),dataIndex : 'origOrgName', align: 'center', flex: 1,xtype : 'ellipsiscolumn'},	 // 交接部门
	{text: pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.handover.type'),dataIndex : 'handOverType', align: 'center', flex: 1,	 // 交接类型
		renderer : function(value){	
	    	return FossDataDictionary.rendererSubmitToDisplay(value, pickup.HANDOVER_TYPE);	
		}	
	},	
	{text: pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.pre.handover'),dataIndex : 'isPreHandOverBill', align: 'center', flex: 1,xtype : 'ellipsiscolumn'},	 // 预配交接
	{text: pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.destination'),dataIndex : 'destOrgName', align: 'center', flex: 1,xtype : 'ellipsiscolumn'},	 // 目的站
	{text: pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.arrive.time'),dataIndex : 'arriveTime', align: 'center', flex: 1,	 // 到达时间
		renderer : function(value) {	
			if (value != null) {	
				var date = Ext.Date.format(new Date(value), 'Y-m-d H:i:s');	
				return date;	
			} else {	
				return null;	
			}	
		} 	
	},	
	{text: pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.goods.qty'),dataIndex : 'goodsQty', align: 'center', flex: 1}	 // 已配件数
	],	
	constructor: function(config){	
		var me = this,	
			cfg = Ext.apply({}, config);	
		me.callParent([cfg]);	
	}	
})	
	
/*************************库存情况************/	
//库存明细Model	
Ext.define('Foss.pickup.queryTrackingWaybill.StoreDetailModel',{	
	extend: 'Ext.data.Model',	
	fields: [	
	    {name: 'waybillNo' , type: 'string'},	
		{name: 'orgName', type: 'string'},	
		{name: 'serialCount', type: 'string'},	
		{name: 'stockStatus', type: 'string',
			convert:function(value){	
				 if(value=='IN_STOCK'){	
					 return pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.in.stock');	//库存中
				 }else{	
					 return pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.out.stock')//'已出库'	
				 }	
		    }	
		},	
		{name: 'operateTime', convert:dateConvert},	
		{name: 'billNo', type: 'string'},	
		{name: 'updateRemark', type: 'string'}	
		]	
})	
//库存明细Store	
Ext.define('Foss.pickup.queryTrackingWaybill.StoreDetailStore',{	
	extend: 'Ext.data.Store',	
	model: 'Foss.pickup.queryTrackingWaybill.StoreDetailModel',	
	//是否自动查询	
	autoLoad: false,	
	proxy : {	
		type : 'memory',	
		reader : {	
			type : 'json',	
			root : 'items'	
		}	
	},	
	constructor : function (config){	
		var me = this,	
			cfg = Ext.apply({}, config);	
		me.callParent([cfg]);	
	}	
})	
//库存情况 Grid	
Ext.define('Foss.pickup.queryTrackingWaybill.StoreDetailGridPanel',{	
	extend: 'Ext.grid.Panel',	
	title:pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.storage.info'),	 // 库存情况
	frame: true,	
	store: Ext.create('Foss.pickup.queryTrackingWaybill.StoreDetailStore'),	
	emptyText: pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.no.data'),	 // 暂无数据
	columns:[	
	{text: pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.waybill.no'),dataIndex : 'waybillNo', align: 'center', flex: 1},	 // 运单号
	{text: pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.storage.org'),dataIndex : 'orgName', align: 'center', flex: 1,xtype : 'ellipsiscolumn'},	 // 库存部门
	{text: pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.stockQty'),dataIndex : 'serialCount', align: 'center', flex: .5},	 // 库存件数
	{text: pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.storage.status'),dataIndex : 'stockStatus', align: 'center', flex: .5},	 // 库存状态
	{text: pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.operator.time'),dataIndex : 'operateTime', align: 'center', flex: 1, 
	renderer : function(value) {	
			if (value != null) {	
				var date = Ext.Date.format(new Date(value), 'Y-m-d H:i:s');	
				return date;	
			} else {	
				return null;	
			}	
		} 	
	},	 // 操作时间
	{text: pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.handover.no'),dataIndex : 'billNo', align: 'center', flex: 1},	 // 交接单编号
	{text: pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.modify.remark'),dataIndex : 'updateRemark', align: 'center', flex: 1}	 // 修改备注
	],	
	constructor: function(config){	
		var me = this,	
			cfg = Ext.apply({}, config);	
		me.callParent([cfg]);	
	}	
})	
/********合并明细信息******/	
Ext.define('Foss.pickup.queryTrackingWaybill.TrackingFormDetailWindow',{	
	extend: 'Ext.window.Window',	
	layout: 'column',	
	title: pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.track.waybill.detail'),	 // 跟踪运单明细信息
    width: 900,	
	closeAction:'hide',	
	defaults: {	
		margin:'5 10 5 10',	
		anchor: '90%',	
		labelWidth:90	
	},	
	trackingGridInfo : null,	
	getTrackingGridInfo: function(){	
		if(this.trackingGridInfo == null){	
			this.trackingGridInfo = Ext.create('Foss.pickup.queryTrackingWaybill.TrackingGridInfo',{	
				columnWidth : 1	
			});	
		}	
		return this.trackingGridInfo;	
	},	
		
	autoStowGridInfo : null,	
	getAutoStowGridInfo: function(){	
		if(this.autoStowGridInfo == null){	
			this.autoStowGridInfo = Ext.create('Foss.pickup.queryTrackingWaybill.AutoStowGridInfo',{	
				columnWidth : 1	
			});	
		}	
		return this.autoStowGridInfo;	
	},	
	associateGridInfo : null,	
	getAssociateGridInfo: function(){	
		if(this.associateGridInfo == null){	
			this.associateGridInfo = Ext.create('Foss.pickup.queryTrackingWaybill.AssociateGridInfo',{	
				columnWidth : 1	
			});	
		}	
		return this.associateGridInfo;	
	},	
	storeDetailGridPanel : null,	
	getStoreDetailGridPanel: function(){	
		if(this.storeDetailGridPanel == null){this.storeDetailGridPanel = Ext.create('Foss.pickup.queryTrackingWaybill.StoreDetailGridPanel',{	
			columnWidth : 1	
		});}	
		return this.storeDetailGridPanel;	
	},	
		
	//初始化构造器装载	
	constructor: function(config){	
		var me = this,	
		cfg = Ext.apply({}, config);	
		me.items = [{	
			layout : 'column',	
			items : [{	
						xtype: 'displayfield',	
						name: 'waybillNo',	
						fieldLabel: pickup.queryTrackingWaybill.i18n('pkp.pickup.queryTrackingWaybill.bill.no'),	 // <b>单号
						align: 'center',	
						labelWidth: 60,	
						columnWidth: 1	
					},	
					me.getTrackingGridInfo(),{	
						 xtype:'container',	
						 columnWidth:.19	
					},					
					 me.getAutoStowGridInfo(),{	
						 xtype:'container',	
						 columnWidth:.55	
					 },	
					 me.getAssociateGridInfo(),	
					 me.getStoreDetailGridPanel(),	
					{	
						xtype:'container',	
						columnWidth:.21	
					}]	
		}];	
		me.callParent([cfg]);	
	}	
		
});	
	
Ext.onReady(function(){	
	Ext.QuickTips.init();	
	var  queryForm = Ext.create('Foss.pickup.queryTrackingWaybill.TrackingWayBillQueryForm');	
	var  resultGridPanel = Ext.create('Foss.pickup.queryTrackingWaybill.TrackingWayBillGridPanel');	
	
	Ext.create('Ext.panel.Panel',{	
		id: 'T_pickup-queryTrackingWaybillIndex_content',	
		cls: "panelContentNToolbar",	
		bodyCls: 'panelContentNToolbar-body',	
		layout: 'auto',	
		getQueryForm: function(){	
			return queryForm;	
		},	
		getResultGridPanel: function(){	
			return resultGridPanel;	
		},	
		items: [queryForm,resultGridPanel],	
		renderTo: 'T_pickup-queryTrackingWaybillIndex-body'	
	});	
})