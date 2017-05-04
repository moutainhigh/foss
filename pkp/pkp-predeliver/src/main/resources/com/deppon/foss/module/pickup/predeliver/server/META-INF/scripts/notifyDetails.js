/**
 * @author Foss-105888-Zhangxingwang
 * @date 2013-12-20 16:09:26
 */
 predeliver.PICKUPGOODSHIGHWAYS = 'PICKUPGOODSHIGHWAYS'; // 派送方式词条
 predeliver.notifyDetailsAction.transportType = 'TRANS_TYPE';//运输方式词条
//提货方式集合
Ext.define('Foss.predeliver.store.ReceiveMethodStore',{
	extend: 'Ext.data.Store',
	fields: [
		{name: 'valueCode',  type: 'string'},
		{name: 'valueName',  type: 'string'}
	],
	data: {
		'items':[
			{'valueCode':'','valueName':predeliver.notifyDetailsAction.i18n('pkp.predeliver.notifyCustomer.complete')},// '全部'
			{'valueCode':'DELIVER','valueName':predeliver.notifyDetailsAction.i18n('pkp.predeliver.notifyCustomer.deliver')},// '送货'
			{'valueCode':'PICKUP','valueName':predeliver.notifyDetailsAction.i18n('pkp.predeliver.notifyCustomer.pickup')}//'自提'
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

//创建处理异常枚举store
Ext.define('Foss.predeliver.store.ProductStore',{
	extend: 'Ext.data.Store',
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
//通知方式集合
Ext.define('Foss.predeliver.store.NoticeMethodStore',{
	extend: 'Ext.data.Store',
	fields: [
		{name: 'valueCode',  type: 'string'},
		{name: 'valueName',  type: 'string'}
	],
	data: {
		'items':[
			{'valueCode':'','valueName':predeliver.notifyDetailsAction.i18n('pkp.predeliver.notifyDetailsAction.complete')},// '全部'
			{'valueCode':'SUCCESS','valueName':predeliver.notifyDetailsAction.i18n('pkp.predeliver.notifyDetailsAction.success')},// '通知成功'
			{'valueCode':'FAILURE','valueName':predeliver.notifyDetailsAction.i18n('pkp.predeliver.notifyDetailsAction.failed')}//'通知失败'
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
predeliver.notifyDetailsAction.getStartDate = function(date, day) {
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

predeliver.notifyDetailsAction.getEndDate = function(date, day) {
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
Ext.define('Foss.NotifyDetails.Model.resultNotifyDetailsModel', {
	extend: 'Ext.data.Model',
	fields: [
		{ name: 'waybillNo',type:'string' }, //运单号
		{ name: 'deliverMethod',type:'string',
			convert:function(value) {
				var v = FossDataDictionary.rendererSubmitToDisplay(value, 'PICKUPGOODSHIGHWAYS');
				if(Ext.isEmpty(v) || value == v){
					v = FossDataDictionary.rendererSubmitToDisplay(value, 'PICKUPGOODSSPECIALDELIVERYTYPE');
				}
				return v;
			} 
		},
		{name : 'noticeStatus' , type: 'string'}, /** 通知状态 */
		{ name: 'stockStatus',type:'string' },
		{ name: 'inStockTime',convert:dateConvert },
		{ name: 'planToSendTime',convert:dateConvert },
		{ name: 'receivecustomerName',type:'string' },
		{ name: 'delivercustomerAddr',type:'string' },
		{ name: 'customerTelePhone',type:'string' },
		{ name: 'customerMobile',type:'string' },
		{ name: 'goodsTotal',type:'string' },
		{ name: 'weight',type:'string' },
		{ name: 'volume',type:'string' },
		{ name: 'arriveFee',type:'string' },
		{ name: 'creatorName',type:'string' },
		{ name: 'deliverRequire',type:'string' },
		{ name: 'createTime',convert:dateConvert },
		{ name: 'arriveTime',convert:dateConvert },/* DMANA-3694 增加6个字段：到达时间、送货司机、司机手机、签收时间、出发部门、到达部门 */
		{ name: 'signTime',convert:dateConvert },
		{ name: 'receiveOrgName',type:'string' },
		{ name: 'customerPickupOrgName',type:'string' },
		{ name: 'driverName',type:'string' },
		{ name: 'driverPhone',type:'string' }
	]
});
//
Ext.define('Foss.NotifyDetails.Store.resultNotifyDetailsStore', {
	extend: 'Ext.data.Store',
	//绑定一个模型
	model:'Foss.NotifyDetails.Model.resultNotifyDetailsModel',
	//是否自动查询
	autoLoad: false,
	//默认每页数据大小
	pageSize:10,
	proxy: {
		//代理的类型为内存代理
		type: 'ajax',
		timeout: 300000,
		//提交方式
		actionMethods:'POST',
		url:predeliver.realPath('queryNotifyDetails.action'),
		//定义一个读取器
		reader: {
			//以JSON的方式读取
			type: 'json',
			//定义读取JSON数据的根对象
			root: 'vo.notifyDetailsDtoList',
			//返回总数
			totalProperty : 'totalCount'
		}
	},
	//事件监听
	listeners: {
	//查询事件
		beforeload : function(s, operation, eOpts) {
			//执行查询，首先货物查询条件，为全局变量，在查询条件的FORM创建时生成
			var queryParams = predeliver.notifyDetailsAction.QueryNotifyDetailsForm.getValues();
			var form = predeliver.notifyDetailsAction.QueryNotifyDetailsForm.getForm();
			var waybillNo = form.getValues().waybillNo;
			var noticeTimeFrom = form.getValues().noticeTimeFrom, noticeTimeTo = form.getValues().noticeTimeTo;
			// 验证运单号输入的行数
			if (!Ext.isEmpty(waybillNo)) {
				var arrayWaybillNo = waybillNo.split('\n');
				if (arrayWaybillNo.length > 50) {
					Ext.ux.Toast.msg(predeliver.notifyDetailsAction.i18n('pkp.predeliver.notifyCustomer.tip'), predeliver.notifyDetailsAction.i18n('pkp.predeliver.notifyCustomer.waybillNo.valitation'), 'error', 3000); // 
					return false;	
				}
				for (var i = 0; i < arrayWaybillNo.length; i++) {
					if (Ext.isEmpty(arrayWaybillNo[i])) {
						Ext.ux.Toast.msg(predeliver.notifyDetailsAction.i18n('pkp.predeliver.notifyCustomer.tip'), predeliver.notifyDetailsAction.i18n('pkp.predeliver.notifyCustomer.waybillNo.valitation'), 'error', 3000); // 
						return false;	
					}
				}
			}else {
				if(Ext.isEmpty(noticeTimeFrom)||Ext.isEmpty(noticeTimeTo)){
					Ext.ux.Toast.msg(predeliver.notifyDetailsAction.i18n('pkp.predeliver.notifyCustomer.tip'), predeliver.notifyDetailsAction.i18n('pkp.predeliver.notifyDetailsAction.dateintervalNull'), 'error', 3000);//起止日期不能为空
					return false;
				}
			}
			
			if (!Ext.isEmpty(noticeTimeFrom) && !Ext.isEmpty(noticeTimeTo)) {	
				var result = Ext.Date.parse(noticeTimeTo,'Y-m-d H:i:s') - Ext.Date.parse(noticeTimeFrom,'Y-m-d H:i:s');	
				if(result / (24 * 60 * 60 * 1000) >= 3){	
					Ext.ux.Toast.msg(predeliver.notifyDetailsAction.i18n('pkp.predeliver.notifyCustomer.tip'), predeliver.notifyDetailsAction.i18n('pkp.predeliver.notifyDetailsAction.dateinterval.over.three.days'), 'error', 3000); // '起止日期相隔不能超过7天！'
					return false;	
				}	
			}
			
			if(!form.isValid()){
				return false;
			}
			Ext.apply(operation, {
				params : {					
						'vo.notifyDetailsConditonDto.waybillNo': queryParams.waybillNo,
						'vo.notifyDetailsConditonDto.noticeResult': queryParams.noticeResult,
						'vo.notifyDetailsConditonDto.receiveMethod': queryParams.receiveMethod,
						'vo.notifyDetailsConditonDto.productCode': queryParams.productCode,
						'vo.notifyDetailsConditonDto.noticeTimeFrom': queryParams.noticeTimeFrom,
						'vo.notifyDetailsConditonDto.noticeTimeTo': queryParams.noticeTimeTo
					}
			});	
		}
	}
});


Ext.define('Foss.predeliver.notifyDetailsAction.QueryNotifyDetailsForm',{
	extend: 'Ext.form.Panel',	
	title: predeliver.notifyDetailsAction.i18n('pkp.predeliver.notifyDetailsAction.query.queryParams'),	 // 查询条件
    defaultType : 'textfield',	
	collapsible: true,	
	layout: 'column',	
	cls:'autoHeight',	
	bodyCls:'autoHeight',	
	frame:true,	
	defaults: {	
		margin:'5 10 5 10',	
		anchor: '90%',	
		labelWidth:60	
	},	
	items: [{
		name : 'waybillNo',
		xtype : 'textarea',
		fieldLabel : predeliver.notifyDetailsAction.i18n('pkp.predeliver.notifyDetailsAction.query.waybillNo'),
		columnWidth : .25,
		emptyText : predeliver.notifyDetailsAction.i18n('pkp.predeliver.notifyCustomer.waybillNo.valitation'),
		regex : /^([0-9]{8,10}\n?)+$/i,
		regexText : predeliver.notifyDetailsAction.i18n('pkp.predeliver.notifyCustomer.waybillNo.valitation')
	}, {
		xtype:'combo',
		displayField:'valueName',
		valueField:'valueCode',
		queryMode:'local',
		triggerAction:'all',
		value : '',
		editable:false,
		name : 'noticeResult',
		fieldLabel : predeliver.notifyDetailsAction.i18n('pkp.predeliver.notifyDetailsAction.query.noticeStatus'),
		columnWidth : .25,
		store : Ext.create('Foss.predeliver.store.NoticeMethodStore')
	}, {
		xtype:'combo',
		displayField:'valueName',
		valueField:'valueCode',
		queryMode:'local',
		triggerAction:'all',
		value : '',
		editable:false,
		name : 'receiveMethod',
		fieldLabel : predeliver.notifyDetailsAction.i18n('pkp.predeliver.notifyCustomer.receiveMethod'),
		columnWidth : .25,
		store : Ext.create('Foss.predeliver.store.ReceiveMethodStore')//
	},{
	    xtype:'combobox',
		displayField:'name',
		valueField:'code',
		queryMode:'local',
		triggerAction:'all',
		editable:false,
		name: 'productCode',
		fieldLabel: predeliver.notifyDetailsAction.i18n('pkp.predeliver.queryGoods.productCode'),//运输性质
		columnWidth: 0.25,
		value:'',
		store : Ext.create('Foss.pkp.ProductStore')
	}, {
		xtype : 'rangeDateField',
		fieldLabel : predeliver.notifyDetailsAction.i18n('pkp.predeliver.notifyDetailsAction.query.noticeTime'),
		dateType : 'datetimefield_date97',
		fromName : 'noticeTimeFrom',
		toName : 'noticeTimeTo',
		editable:false,
		disallowBlank:false,
		fromValue: Ext.Date.format(predeliver.notifyDetailsAction.getStartDate(new Date(),-2),'Y-m-d H:i:s'),
		toValue: Ext.Date.format(predeliver.notifyDetailsAction.getEndDate(new Date(),0),'Y-m-d H:i:s'),
		columnWidth : .5
	},{
		border: 1,
		xtype:'container',
		columnWidth:1,
		defaultType:'button',
		layout:'column',
		items:[{
			text : predeliver.notifyDetailsAction.i18n('pkp.predeliver.notifyDetailsAction.query.resetButton'),
			columnWidth:.08,
			handler : function() {
				var myForm = this.up('form').getForm();	
				myForm.reset();	
				myForm.findField('noticeTimeFrom').setValue(Ext.Date.format(predeliver.notifyDetailsAction.getStartDate(new Date(),-2),'Y-m-d H:i:s')); 
				myForm.findField('noticeTimeTo').setValue(Ext.Date.format(predeliver.notifyDetailsAction.getEndDate(new Date(),0),'Y-m-d H:i:s'));
			}
		},{
			xtype: 'container',
			border : false,
			columnWidth:.76,
			html: '&nbsp;'
		}, {
			text : predeliver.notifyDetailsAction.i18n('pkp.predeliver.notifyDetailsAction.query.queryButton'),
			disabled:!predeliver.notifyDetailsAction.isPermission('notifyDetailsIndex/notifyDetailsIndexquerybutton'),
			hidden:!predeliver.notifyDetailsAction.isPermission('notifyDetailsIndex/notifyDetailsIndexquerybutton'),
			columnWidth:.08,
			cls : 'yellow_button',
			plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin', {
				seconds: 3
			}),
			handler : function() {
				predeliver.notifyDetailsAction.pagingBar.moveFirst();				
			}
		},{
			text : predeliver.notifyDetailsAction.i18n('pkp.predeliver.arriveDeliverManagerIndex.exportDeliverbillDetail'),
			disabled:!predeliver.notifyDetailsAction.isPermission('notifyDetailsIndex/notifyDetailsIndexexportbutton'),
			hidden:!predeliver.notifyDetailsAction.isPermission('notifyDetailsIndex/notifyDetailsIndexexportbutton'),
			columnWidth:.08,
			handler: function() {
				var toDoActionForm = predeliver.notifyDetailsAction.QueryNotifyDetailsForm;
				if (toDoActionForm != null) {
					var queryParams = toDoActionForm.getValues();
					if(!Ext.fly('downloadAttachFileForm')){
					    var frm = document.createElement('form');
					    frm.id = 'downloadAttachFileForm';
					    frm.style.display = 'none';
					    document.body.appendChild(frm);
					}	
					//获取查询出来的异常信息
					var toDoActionGridStore = predeliver.notifyDetailsAction.ResultNotifyDetailsGrid.getStore();	
					//若异常信息不为空
					if(toDoActionGridStore.getCount()!=0){
						//执行查询，首先货物查询条件，为全局变量，在查询条件的FORM创建时生成
						var form = toDoActionForm.getForm();
						var waybillNo = form.getValues().waybillNo;
						var noticeTimeFrom = form.getValues().noticeTimeFrom, noticeTimeTo = form.getValues().noticeTimeTo;
						// 验证运单号输入的行数
						if (!Ext.isEmpty(waybillNo)) {
							var arrayWaybillNo = waybillNo.split('\n');
							if (arrayWaybillNo.length > 50) {
								Ext.ux.Toast.msg(predeliver.notifyDetailsAction.i18n('pkp.predeliver.notifyCustomer.tip'), predeliver.notifyDetailsAction.i18n('pkp.predeliver.notifyCustomer.waybillNo.valitation'), 'error', 3000); // 
								return;	
							}
							for (var i = 0; i < arrayWaybillNo.length; i++) {
								if (Ext.isEmpty(arrayWaybillNo[i])) {
									Ext.ux.Toast.msg(predeliver.notifyDetailsAction.i18n('pkp.predeliver.notifyCustomer.tip'), predeliver.notifyDetailsAction.i18n('pkp.predeliver.notifyCustomer.waybillNo.valitation'), 'error', 3000); // '
									return;	
								}
							}
						}else {
							if(Ext.isEmpty(noticeTimeFrom)||Ext.isEmpty(noticeTimeTo)){
								Ext.ux.Toast.msg(predeliver.notifyDetailsAction.i18n('pkp.predeliver.notifyCustomer.tip'), predeliver.notifyDetailsAction.i18n('pkp.predeliver.notifyDetailsAction.dateintervalNull'), 'error', 3000);//起止日期不能为空
								return ;
							}
						}
						
						if (!Ext.isEmpty(noticeTimeFrom) && !Ext.isEmpty(noticeTimeTo)) {	
							var result = Ext.Date.parse(noticeTimeTo,'Y-m-d H:i:s') - Ext.Date.parse(noticeTimeFrom,'Y-m-d H:i:s');	
							if(result / (24 * 60 * 60 * 1000) >= 3){	
								Ext.ux.Toast.msg(predeliver.notifyDetailsAction.i18n('pkp.predeliver.notifyCustomer.tip'), predeliver.notifyDetailsAction.i18n('pkp.predeliver.notifyDetailsAction.dateinterval.over.three.days'), 'error', 3000); // '起止日期相隔不能超过3天！'
								return;	
							}	
						}
						
						if(!form.isValid()){
							return ;
						}
						Ext.Ajax.request({
							url:predeliver.realPath('exportNoticeDetails.action'),
							form: Ext.fly('downloadAttachFileForm'),
							method : 'POST',
							params : {
								'vo.notifyDetailsConditonDto.waybillNo': queryParams.waybillNo,
								'vo.notifyDetailsConditonDto.noticeResult': queryParams.noticeResult,
								'vo.notifyDetailsConditonDto.receiveMethod': queryParams.receiveMethod,
								'vo.notifyDetailsConditonDto.noticeTimeFrom': queryParams.noticeTimeFrom,
								'vo.notifyDetailsConditonDto.noticeTimeTo': queryParams.noticeTimeTo
							},
							isUpload: true
						});
					}else{
						//或者提示不能导出
						Ext.ux.Toast.msg('提示','没有记录，不能导出', 'error', 3000);
					}
				}}
		}]
	}]
});

//显示异常Grid数据
Ext.define('Foss.predeliver.notifyDetailsAction.ResultNotifyDetailsGrid', {
	extend:'Ext.grid.Panel',
	//增加表格列的分割线
	columnLines: true,
	emptyText:predeliver.notifyDetailsAction.i18n('pkp.predeliver.notifyDetailsAction.result.emptyText'),//查询结果为空！,
	bodyCls: 'autoHeight',
	cls: 'autoHeight',
	id:'Foss_Predeliver_ResultNotifyDetailsGrid_details',
	//表格对象增加一个边框
	frame: true,
	stripeRows: true,
	//定义表格的标题
	title:predeliver.notifyDetailsAction.i18n('pkp.predeliver.notifyDetailsAction.result.noticeRecord'),//通知记录,
	collapsible: true,
	animCollapse: true,	
	//自动增加滚动条
	autoScroll:true,
	dockedItems : [ {
        xtype : 'toolbar',
        dock : 'top',
        layout : 'column',
        items : [{
					xtype : 'label',
					columnWidth : .064,
					text : predeliver.notifyDetailsAction.i18n('pkp.predeliver.notifyCustomer.success')
				}, {
					xtype : 'image',
					columnWidth : .03,
					imgCls : 'foss_icons_pkp_noticeOk'
				}, {
					xtype : 'label',
					columnWidth : .064,
					text : predeliver.notifyDetailsAction.i18n('pkp.predeliver.notifyCustomer.failuer')
				}, {
					xtype : 'image',
					columnWidth : .03,
					imgCls : 'foss_icons_pkp_noticeError'
				},{
					xtype : 'container',
					border : false,
					columnWidth : .7,
					html : '&nbsp;'
				}]
	  }],	  
	columns: [
	{
		//字段标题
		header: predeliver.notifyDetailsAction.i18n('pkp.predeliver.notifyDetailsAction.query.waybillNo'),//运单号, 
		//关联model中的字段名
		dataIndex: 'waybillNo',
		width:130,
		align: 'center'
	},{
		//字段标题
		header: predeliver.notifyDetailsAction.i18n('pkp.predeliver.notifyDetailsAction.query.noticeStatus'),//通知状态, 
		//关联model中的字段名
		dataIndex: 'noticeStatus', 
		width:80,
		align: 'center',
		renderer : function(value, cellmeta, record, rowIndex, columnIndex, store){
			var result = '';
			if(value == 'SUCCESS') {
				//通知成功 
				result = '<div class="foss_icons_pkp_noticeOk"></div>';
			} else if(value == 'FAILURE') {
				//通知失败
				result = '<div class="foss_icons_pkp_noticeError"></div>';
			}
			return result;
		}
	},{
		//字段标题
		header: predeliver.notifyDetailsAction.i18n('pkp.predeliver.notifyDetailsAction.query.deliverMethod'),//开单提货方式, 
		//关联model中的字段名
		dataIndex: 'deliverMethod', 
		width:120,
		align: 'center'
	},{
		//字段标题
		header: predeliver.notifyDetailsAction.i18n('pkp.predeliver.notifyDetailsAction.query.stockStatus'),//库存状态, 
		//关联model中的字段名
		dataIndex: 'stockStatus', 
		width:80,
		align: 'center',
		renderer : function(value, cellmeta, record, rowIndex, columnIndex, store){
			var result = '';
			if(value == 'Y') {
				//通知成功 
				return predeliver.notifyDetailsAction.i18n('pkp.predeliver.notifyCustomer.inStock');
			} else if(value == 'N') {
				//通知失败
				return predeliver.notifyDetailsAction.i18n('pkp.predeliver.printArriveSheet.nonDepartmentalInventory');
			}
		}
	},{
		//字段标题
		header: predeliver.notifyDetailsAction.i18n('pkp.predeliver.notifyDetailsAction.query.inStockTime'),//入库时间, 
		//关联model中的字段名
		dataIndex: 'inStockTime', 
		width:150,
		align: 'center',
		renderer:function(value){
			if(value!=null){
				return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
			}else{
				return null;
			}
		} 
	},{
		//字段标题
		header: predeliver.notifyDetailsAction.i18n('pkp.predeliver.notifyDetailsAction.query.planToSendTime'),//预计送货日期, 
		//关联model中的字段名
		dataIndex: 'planToSendTime', 
		width:150,
		align: 'center',
		renderer:function(value){
			if(value!=null){
				return Ext.Date.format(new Date(value), 'Y-m-d');
			}else{
				return null;
			}
		} 
	},{
		//字段标题
		header: predeliver.notifyDetailsAction.i18n('pkp.predeliver.notifyDetailsAction.result.delivercustomerName'),//收货人, 
		//关联model中的字段名
		dataIndex: 'receivecustomerName',
		width:120,
		align: 'center'
	},{
		//字段标题
		header: predeliver.notifyDetailsAction.i18n('pkp.predeliver.notifyDetailsAction.result.delivercustomerAddr'),//收货人地址, 
		//关联model中的字段名
		dataIndex: 'delivercustomerAddr',
		width:150,
		align: 'center'
	},{
		//字段标题
		header: predeliver.notifyDetailsAction.i18n('pkp.predeliver.notifyDetailsAction.result.customerTelePhone'),//收货人电话, 
		//关联model中的字段名
		dataIndex: 'customerTelePhone',
		width:120,
		align: 'center'//
	},{
		//字段标题
		header: predeliver.notifyDetailsAction.i18n('pkp.predeliver.notifyDetailsAction.result.customerMobile'),//收货人电话, 
		//关联model中的字段名
		dataIndex: 'customerMobile',
		width:120,
		align: 'center'//
	},{
		//字段标题
		header: predeliver.notifyDetailsAction.i18n('pkp.predeliver.notifyDetailsAction.result.goodsTotal'),//件数, 
		//关联model中的字段名
		dataIndex: 'goodsTotal',
		width:80,
		align: 'center'//pkp.predeliver.notifyDetailsAction.result.customerMobile
	},{///件数、重量、体积、到付金额、提前通知、创建人、创建时间
		//字段标题
		header: predeliver.notifyDetailsAction.i18n('pkp.predeliver.notifyDetailsAction.result.weight'),//重量, 
		//关联model中的字段名
		dataIndex: 'weight',
		width:80,
		align: 'center'//pkp.predeliver.notifyDetailsAction.result.customerMobile
	},{///件数、重量、体积、到付金额、提前通知、创建人、创建时间
		//字段标题
		header: predeliver.notifyDetailsAction.i18n('pkp.predeliver.notifyDetailsAction.result.volume'),//体积, 
		//关联model中的字段名
		dataIndex: 'volume',
		width:80,
		align: 'center'//pkp.predeliver.notifyDetailsAction.result.customerMobile
	},{///件数、重量、体积、到付金额、提前通知、创建人、创建时间
		//字段标题
		header: predeliver.notifyDetailsAction.i18n('pkp.predeliver.notifyDetailsAction.result.arriveFee'),//到付金额, 
		//关联model中的字段名
		dataIndex: 'arriveFee',
		width:80,
		align: 'center'//pkp.predeliver.notifyDetailsAction.result.customerMobile
	},{///件数、重量、体积、到付金额、提前通知、创建人、创建时间
		//字段标题
		header: predeliver.notifyDetailsAction.i18n('pkp.predeliver.notifyDetailsAction.result.noticeNote'),//提前通知, 
		//关联model中的字段名
		dataIndex: 'deliverRequire',
		width:150,
		align: 'center'
	},{//DMANA-3694 增加6个字段：到达时间、送货司机、司机手机、签收时间、出发部门、到达部门
		header: predeliver.notifyDetailsAction.i18n('pkp.predeliver.notifyDetailsAction.arriveTime'),//到达时间, 
		//关联model中的字段名
		dataIndex: 'arriveTime',
		width:150,
		align: 'center',
		renderer:function(value){
			if(value!=null){
				return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
			}else{
				return null;
			}
		} 
	},{
		header: predeliver.notifyDetailsAction.i18n('pkp.predeliver.notifyDetailsAction.driverName'),//送货司机, 
		//关联model中的字段名
		dataIndex: 'driverName',
		width:80,
		align: 'center'
	},{
		header: predeliver.notifyDetailsAction.i18n('pkp.predeliver.notifyDetailsAction.driverMobilephone'),//司机手机, 
		//关联model中的字段名
		dataIndex: 'driverPhone',
		width:80,
		align: 'center'
	},{
		header: predeliver.notifyDetailsAction.i18n('pkp.predeliver.notifyDetailsAction.signTime'),//签收时间, 
		//关联model中的字段名
		dataIndex: 'signTime',
		width:150,
		align: 'center',
		renderer:function(value){
			if(value!=null){
				return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
			}else{
				return null;
			}
		} 
	},{
		header: predeliver.notifyDetailsAction.i18n('pkp.predeliver.notifyDetailsAction.receiveOrgName'),//出发部门, 
		//关联model中的字段名
		dataIndex: 'receiveOrgName',
		width:160,
		align: 'center'
	},{
		header: predeliver.notifyDetailsAction.i18n('pkp.predeliver.notifyDetailsAction.customerPickupOrgName'),//到达部门, 
		//关联model中的字段名
		dataIndex: 'customerPickupOrgName',
		width:160,
		align: 'center'
	},{///件数、重量、体积、到付金额、提前通知、创建人、创建时间
		//字段标题
		header: predeliver.notifyDetailsAction.i18n('pkp.predeliver.notifyDetailsAction.result.creatorName'),//创建人, 
		//关联model中的字段名
		dataIndex: 'creatorName',
		width:80,
		align: 'center'
	},{
		//字段标题
		header: predeliver.notifyDetailsAction.i18n('pkp.predeliver.notifyDetailsAction.query.createTime'),//创建时间, 
		//关联model中的字段名
		dataIndex: 'createTime', 
		width:150,
		align: 'center',
		renderer:function(value){
			if(value!=null){
				return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
			}else{
				return null;
			}
		} 
	}],
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.NotifyDetails.Store.resultNotifyDetailsStore');
		//添加分页工具条
		me.bbar = Ext.create('Deppon.StandardPaging',{
			store:me.store,
			plugins: {
        		ptype: 'pagesizeplugin',
       	 		//超出输入最大限制是否提示，true则提示，默认不提示
        		alertOperation: true,
        		//自定义分页comobo数据
        		sizeList: [['5', 5], ['10', 10], ['20', 20], ['50', 50], ['100', 100], ['200', 200]],
        		//入最大限制，默认为200
        		maximumSize: 200
        	},
			displayInfo: true
		});
		predeliver.notifyDetailsAction.pagingBar = me.bbar;
		me.callParent([cfg]);
	}
});

Ext.onReady(function(){	
	Ext.QuickTips.init();	
	predeliver.notifyDetailsAction.QueryNotifyDetailsForm = Ext.create('Foss.predeliver.notifyDetailsAction.QueryNotifyDetailsForm');
	predeliver.notifyDetailsAction.ResultNotifyDetailsGrid = Ext.create('Foss.predeliver.notifyDetailsAction.ResultNotifyDetailsGrid');
	Ext.create('Ext.panel.Panel',{	
		id: 'T_predeliver-notifyDetailsIndex_content',	
		cls: "panelContentNToolbar",	
		bodyCls: 'panelContentNToolbar-body',	
		layout: 'auto',	
		items: [predeliver.notifyDetailsAction.QueryNotifyDetailsForm,predeliver.notifyDetailsAction.ResultNotifyDetailsGrid],	
		renderTo: 'T_predeliver-notifyDetailsIndex-body'	
	});	
});