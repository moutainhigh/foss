//获取后台传回的上级转运场信息
if(!Ext.isEmpty(platform.deliverGoodsAreaQuery.outfieldCode)){
	platform.deliverGoodsAreaQuery.outfieldCanBeModified = true;
}else{
	platform.deliverGoodsAreaQuery.outfieldCanBeModified = false;
}

//日派送率 查询form
Ext.define('Foss.platform.deliverGoodsAreaQuery.dayDeliverRateQueryForm', {
	extend : 'Ext.form.Panel',
	title : '查询条件',
	frame : true,
	collapsible : true,
	animCollapse : true,
	defaults : {
		margin : '5 10 5 10',
		labelWidth : 85,
		columnWidth : 1 / 4,
		xtype : 'textfield'
	},
	layout : 'column',
	items : [{
		fieldLabel : '转运场',
		name : 'destOrgCode',
		xtype : 'dynamicorgcombselector',
		type : 'ORG',
		readOnly : platform.deliverGoodsAreaQuery.outfieldCanBeModified,
		value : platform.deliverGoodsAreaQuery.outfieldName,
		transferCenter : 'Y'
	}, {
		fieldLabel : '当日时间',
		readOnly : true,
		name : 'currentTime',
		value : platform.deliverGoodsAreaQuery.serverTimeString,
		columnWidth : 1 / 4
	}, {
		border : false,
		xtype : 'container',
		columnWidth : 1,
		layout : 'column',
		defaults : {
			margin : '5 0 5 0'
		},
		items : [ {
			xtype : 'button',
			columnWidth : .08,
			hidden : platform.deliverGoodsAreaQuery.outfieldCanBeModified,
			text : '重置',
			handler : function() {
				if(!platform.deliverGoodsAreaQuery.outfieldCanBeModified){
					platform.deliverGoodsAreaQuery.querySendDayLoadingForm.getForm().findField('destOrgCode').setValue("");
				}
			}
		}, {
			border : false,
			columnWidth : .84,
			html : '&nbsp;'
		}, {
			columnWidth : .08,
			xtype : 'button',
			cls : 'yellow_button',
			text : '查询',
			handler : function(){
				if(platform.deliverGoodsAreaQuery.querySendDayLoadingForm.getForm().isValid()){
					platform.deliverGoodsAreaQuery.loadingSendDayRateGrid.store.load();
				}
			}
		} ]
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
});

//日派送率 列表之Model
Ext.define('Foss.platform.deliverGoodsAreaQuery.dayDeliverRateModel', {
	extend : 'Ext.data.Model',
	//定义字段
	fields : [{
		name : 'orgName',
		type : 'string'
	},{
		name : 'statisticsTimeTheory',
		type : 'date',
		convert: function(value) {
			if (value != null) {
				var date = new Date(value);						
				return Ext.Date.format(date,'Y-m-d');
			} else {
				return null;
			}
		}	
	}, {
		name : 'yesterdayStockWaybill',
		type : 'string'
	}, {
		name : 'yesterdayStockWeight',
		type : 'string'
	}, {
		name : 'yesterdayStockVolume',
		type : 'string'
	}, {
		name : 'dayStockWaybill',
		type : 'string'
	}, {
		name : 'dayStockWeight',
		type : 'string'
	}, {
		name : 'dayStockVolume',
		type : 'string'
	},{
		name : 'daySendWaybill',
		type : 'string'
	}, {
		name : 'daySendWeight',
		type : 'string'
	}, {
		name : 'daySendVolume',
		type : 'string'
	}, {
		name : 'sendRate',
		type : 'string'
	}, {
		name : 'tomorrowStockWaybill',
		type : 'string'
	} ,{
		name : 'tomorrowStockWeight',
		type : 'string'
	}, {
		name : 'tomorrowStockVolume',
		type : 'string'
	},{
		name : 'dataTimeQuery',
		type : 'string'
	}]
});

//日派送率 列表之store
Ext.define('Foss.platform.deliverGoodsAreaQuery.dayDeliverRateStore', {
	extend : 'Ext.data.Store',
	pageSize : 20,
	// 绑定一个模型
	model : 'Foss.platform.deliverGoodsAreaQuery.dayDeliverRateModel',
	// 定义一个代理对象
	proxy : {
		type : 'ajax',
		actionMethods:'POST',
		// 请求的url
		//Ext.BLANK_IMAGE_URL='sendRateDayQuery.action',
		//Ext.Ajax.timeout=300000,
		url : platform.realPath('sendRateDayQuery.action'),
		timeout:300000,
		// 定义一个读取器
		reader : {
			// 以JSON的方式读取
			type : 'json',
			// 定义读取JSON数据的根对象
			root : 'deliverGoodsAreaQueryVo.sendRateList',
			successProperty: 'success'
		}
	},
	listeners: {
		beforeload : function(store, operation, eOpts) {
				var queryOrgCode = '';
				if(platform.deliverGoodsAreaQuery.outfieldCanBeModified){
					queryOrgCode = platform.deliverGoodsAreaQuery.outfieldCode;
				}else{
					queryOrgCode = platform.deliverGoodsAreaQuery.querySendDayLoadingForm.getValues().destOrgCode;
				}
			
				Ext.apply(operation, {
					params : {
						'deliverGoodsAreaQueryVo.sendRateEntity.orgCode':queryOrgCode
					}
				});	
		},
		'load': function(records, operation, success){
			if(records.data.items[0]){
				if(records.data.items[0].get('dataTimeQuery')){
					var dTime = records.data.items[0].get('dataTimeQuery');
					platform.deliverGoodsAreaQuery.querySendDayLoadingForm.getForm().findField('currentTime').setValue(dTime);
				}
				
			}
			}
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
});

//日派送率 列表
Ext.define('Foss.platform.deliverGoodsAreaQuery.dayDeliverRateGrid', {
	extend : 'Ext.grid.Panel',
	frame : true,
	columnLines: true,
	title : '日派送率信息',
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	emptyText : '查询结果为空',
	autoScroll : true,
	collapsible : true,
	sortableColumns : false,
	enableColumnHide : false,
	animCollapse : true,
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.platform.deliverGoodsAreaQuery.dayDeliverRateStore');
		
		me.callParent([cfg]);
	},
	tbar : [{
		xtype : 'button',
		text : '导出',
		handler: function(){
			var actionUrl=platform.realPath('sendRateDayExport.action');
			var queryOrgCode = '';
			if(platform.deliverGoodsAreaQuery.outfieldCanBeModified){
				queryOrgCode = platform.deliverGoodsAreaQuery.outfieldCode;
			}else{
				queryOrgCode = platform.deliverGoodsAreaQuery.querySendDayLoadingForm.getValues().destOrgCode;
			}
			var exportParams = {
					'deliverGoodsAreaQueryVo.sendRateEntity.orgCode':queryOrgCode
			}; 
			if(!Ext.fly('downloadSendRateDayFileForm')){
							    var frm = document.createElement('form');
							    frm.id = 'downloadSendRateDayFileForm';
							    frm.style.display = 'none';
							    document.body.appendChild(frm);
			}
			
			Ext.Ajax.request({
			url:actionUrl,
			form: Ext.fly('downloadSendRateDayFileForm'),
			method : 'POST',
			params : exportParams,
			isUpload: true,
			exception : function(response,opts) {
				var result = Ext.decode(response.responseText);
				Ext.MessageBox.alert('提示','导出失败',result.message);
				//myMask.hide();
			}	
			});
		}
	}],
	columns : [{
		dataIndex : 'orgName',
		align : 'center',
		xtype : 'ellipsiscolumn',
		text : '转运场'
	},{
		dataIndex : 'statisticsTimeTheory',
		align : 'center',
		xtype : 'ellipsiscolumn',
		text : '日期'
	}, {
		text : '前一日剩余派送量',
		columns : [{
			dataIndex : 'yesterdayStockWaybill',
			align : 'center',
			xtype : 'ellipsiscolumn',
			text : '票数'
		},{
			dataIndex : 'yesterdayStockWeight',
			align : 'center',
			xtype : 'ellipsiscolumn',
			text : '重量'
		},{
			dataIndex : 'yesterdayStockVolume',
			align : 'center',
			xtype : 'ellipsiscolumn',
			text : '体积'
		}]
	},{
		text : '当日入库货量',
		columns : [{
			dataIndex : 'dayStockWaybill',
			align : 'center',
			xtype : 'ellipsiscolumn',
			text : '票数'
		},{
			dataIndex : 'dayStockWeight',
			align : 'center',
			xtype : 'ellipsiscolumn',
			text : '重量'
		},{
			dataIndex : 'dayStockVolume',
			align : 'center',
			xtype : 'ellipsiscolumn',
			text : '体积'
		}]
	},{
		text : '当日已派送量',
		columns : [{
			dataIndex : 'daySendWaybill',
			align : 'center',
			xtype : 'ellipsiscolumn',
			text : '票数'
		},{
			dataIndex : 'daySendWeight',
			align : 'center',
			xtype : 'ellipsiscolumn',
			text : '重量'
		},{
			dataIndex : 'daySendVolume',
			align : 'center',
			xtype : 'ellipsiscolumn',
			text : '体积'
		}]
	},{
		dataIndex : 'sendRate',
		align : 'center',
		xtype : 'ellipsiscolumn',
		text : '派送率(F)',
		renderer : function(value){
			if(value != null){
				return value + '%';
			}else{
				return value;
			}
		}
			
	}, {
		text : '预计后一日派送到达货量',
		columns : [/*{
			dataIndex : 'tomorrowStockWaybill',
			align : 'center',
			xtype : 'ellipsiscolumn',
			text : '票数',
			renderer : function(value){
				if(Ext.isEmpty(value)){
					return 0;
				}else{
					return value;
				}
				}
		},*/{
			dataIndex : 'tomorrowStockWeight',
			align : 'center',
			xtype : 'ellipsiscolumn',
			text : '重量',
			renderer : function(value){
				if(Ext.isEmpty(value)){
					return 0;
				}else{
					return value;
				}
				}
		},{
			dataIndex : 'tomorrowStockVolume',
			align : 'center',
			xtype : 'ellipsiscolumn',
			text : '体积',
			renderer : function(value){
				if(Ext.isEmpty(value)){
					return 0;
				}else{
					return value;
				}
				}
		}]
	}],
});

//累计派送率 之查询form
Ext.define('Foss.platform.deliverGoodsAreaQuery.totalDeliverRateQueryForm', {
	extend : 'Ext.form.Panel',
	title : '查询条件',
	frame : true,
	collapsible : true,
	animCollapse : true,
	defaults : {
		margin : '5 10 5 10',
		labelWidth : 85,
		columnWidth : 1 / 4,
		xtype : 'textfield'
	},
	layout : 'column',
	items : [{
		fieldLabel : '转运场',
		name : 'destOrgCode',
		xtype : 'dynamicorgcombselector',
		type : 'ORG',
		columnWidth : 1/4,
		readOnly : platform.deliverGoodsAreaQuery.outfieldCanBeModified,
		value : platform.deliverGoodsAreaQuery.outfieldName,
		transferCenter : 'Y'
	}, {
		xtype: 'datefield',
		fieldLabel:  '日期',
		dateType: 'datetimefield_date97',
		name: 'totalDeliverRateQueryEndTime',
		format: 'Y-m-d', 
		value: Ext.Date.format(new Date(new Date().getFullYear(),
				new Date().getMonth(), new Date().getDate()-1), 'Y-m-d'),
		maxValue: Ext.Date.format(new Date(new Date().getFullYear(),
										new Date().getMonth(), new Date().getDate()-1), 'Y-m-d'),
		columnWidth : 1/4
	}, {
		border : false,
		xtype : 'container',
		columnWidth : 1,
		layout : 'column',
		defaults : {
			margin : '5 0 5 0'
		},
		items : [ {
			xtype : 'button',
			columnWidth : .08,
			hidden : platform.deliverGoodsAreaQuery.outfieldCanBeModified,
			text : '重置',
			handler : function() {
				if(!platform.deliverGoodsAreaQuery.outfieldCanBeModified){
					platform.deliverGoodsAreaQuery.querySendLogLoadingForm.getForm().reset();
				}				
			}
		}, {
			border : false,
			columnWidth : .84,
			html : '&nbsp;'
		}, {
			columnWidth : .08,
			xtype : 'button',
			cls : 'yellow_button',
			text : '查询',
			handler : function(){
				if(platform.deliverGoodsAreaQuery.querySendLogLoadingForm.getForm().isValid()){
					//var beginTime = platform.deliverGoodsAreaQuery.querySendLogLoadingForm.getValues().totalDeliverRateQueryBeginTime;
					var endTime = platform.deliverGoodsAreaQuery.querySendLogLoadingForm.getValues().totalDeliverRateQueryEndTime;
					
					//if(!beginTime){
					//	Ext.MessageBox.alert('提示','请输入开始日期');
					//	return;
					//}
					
					if(!endTime){
						Ext.MessageBox.alert('提示','请输入日期');
						return;
					}
					platform.deliverGoodsAreaQuery.loadingSendLogRateGrid.store.load();
				}
			}
		} ]
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
});

//累计派送率 列表之Model
Ext.define('Foss.platform.deliverGoodsAreaQuery.totalDeliverRateModel', {
	extend : 'Ext.data.Model',
	//定义字段
	fields : [{
		name : 'orgName',
		type : 'string'
	},{
		name : 'statisticsTimeTheory',
		type : 'date',
		convert: function(value) {
			if (value != null) {
				var date = new Date(value);						
				return Ext.Date.format(date,'Y-m-d');
			} else {
				return null;
			}
		}	
	}, {
		name : 'yesterdayStockWaybill',
		type : 'string'
	}, {
		name : 'yesterdayStockWeight',
		type : 'string'
	}, {
		name : 'yesterdayStockVolume',
		type : 'string'
	},{
		name : 'dayStockWaybill',
		type : 'string'
	}, {
		name : 'dayStockWeight',
		type : 'string'
	}, {
		name : 'dayStockVolume',
		type : 'string'
	}, {
		name : 'daySendWaybill',
		type : 'string'
	}, {
		name : 'daySendWeight',
		type : 'string'
	}, {
		name : 'daySendVolume',
		type : 'string'
	}, {
		name : 'sendRate',
		type : 'string'
	}, {
		name : 'sendRateAll',
		type : 'string'
	}]
});

//累计派送率 列表之store
Ext.define('Foss.platform.deliverGoodsAreaQuery.totalDeliverRateStore', {
	extend : 'Ext.data.Store',
	pageSize : 20,
	// 绑定一个模型
	model : 'Foss.platform.deliverGoodsAreaQuery.totalDeliverRateModel',
	// 定义一个代理对象
	proxy : {
		type : 'ajax',
		actionMethods:'POST',
		// 请求的url
		url : platform.realPath('sendRateLogQuery.action'),
		timeout:300000,
		// 定义一个读取器
		reader : {
			// 以JSON的方式读取
			type : 'json',
			// 定义读取JSON数据的根对象
			root : 'deliverGoodsAreaQueryVo.sendRateList',
			successProperty: 'success',
			totalProperty : 'totalCount'
		}
	},
	listeners: {
		beforeload : function(store, operation, eOpts) {
				var queryOrgCode = '';
				if(platform.deliverGoodsAreaQuery.outfieldCanBeModified){
					queryOrgCode = platform.deliverGoodsAreaQuery.outfieldCode;
				}else{
					queryOrgCode = platform.deliverGoodsAreaQuery.querySendLogLoadingForm.getValues().destOrgCode;
				}
				
				//var beginTime = platform.deliverGoodsAreaQuery.querySendLogLoadingForm.getValues().totalDeliverRateQueryBeginTime;
				var endTime = platform.deliverGoodsAreaQuery.querySendLogLoadingForm.getValues().totalDeliverRateQueryEndTime;
				
				Ext.apply(operation, {
					params : {
						'deliverGoodsAreaQueryVo.sendRateEntity.orgCode':queryOrgCode,
						//'deliverGoodsAreaQueryVo.sendRateEntity.beginDate':beginTime,
						'deliverGoodsAreaQueryVo.sendRateEntity.endDate':endTime
					}
				});	
		},
		'load': function(records, operation, success){
//				for(var i = 0; i<records.data.length;i++){
//					var record = records.data.items[i];
//				}
			}
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
});

//累计派送率 列表
Ext.define('Foss.platform.deliverGoodsAreaQuery.totalDeliverRateGrid', {
	extend : 'Ext.grid.Panel',
	frame : true,
	columnLines: true,
	title : '累计派送数据',
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	emptyText : '查询结果为空',
	autoScroll : true,
	collapsible : true,
	sortableColumns : false,
	enableColumnHide : false,
	animCollapse : true,
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.platform.deliverGoodsAreaQuery.totalDeliverRateStore');
		
		me.bbar = Ext.create('Deppon.StandardPaging',{
			store : me.store,
			pageSize : 20,
			maximumSize : 50,
			plugins : Ext.create('Deppon.ux.PageSizePlugin', {
				sizeList : [['20', 20], ['30', 30], ['40', 40], ['50', 50]]
			})
		});
		platform.deliverGoodsAreaQuery.totalDeliverRateGridPagingBar = me.bbar;
		me.callParent([cfg]);
	},
	tbar : [{
		xtype : 'button',
		text : '导出',
		handler: function(){
			var actionUrl=platform.realPath('sendRateLogExport.action');
			var queryOrgCode = '';
			if(platform.deliverGoodsAreaQuery.outfieldCanBeModified){
				queryOrgCode = platform.deliverGoodsAreaQuery.outfieldCode;
			}else{
				queryOrgCode = platform.deliverGoodsAreaQuery.querySendLogLoadingForm.getValues().destOrgCode;
			}
			
			//var beginTime = platform.deliverGoodsAreaQuery.querySendLogLoadingForm.getValues().totalDeliverRateQueryBeginTime;
			var endTime = platform.deliverGoodsAreaQuery.querySendLogLoadingForm.getValues().totalDeliverRateQueryEndTime;
			
			//if(!beginTime){
			//	Ext.MessageBox.alert('提示','请输入开始日期');
			//	return;
			//}
			
			if(!endTime){
				Ext.MessageBox.alert('提示','请输入日期');
				return;
			}
			
			var exportParams = {
					'deliverGoodsAreaQueryVo.sendRateEntity.orgCode':queryOrgCode,
					//'deliverGoodsAreaQueryVo.sendRateEntity.beginDate':beginTime,
					'deliverGoodsAreaQueryVo.sendRateEntity.endDate':endTime
			}; 
			if(!Ext.fly('downloadSendRateLogFileForm')){
							    var frm = document.createElement('form');
							    frm.id = 'downloadSendRateLogFileForm';
							    frm.style.display = 'none';
							    document.body.appendChild(frm);
			}
			
			Ext.Ajax.request({
			url:actionUrl,
			form: Ext.fly('downloadSendRateLogFileForm'),
			method : 'POST',
			params : exportParams,
			isUpload: true,
			exception : function(response,opts) {
				var result = Ext.decode(response.responseText);
				Ext.MessageBox.alert('提示','导出失败',result.message);
				//myMask.hide();
			}	
			});
		}
	}],
	columns : [{
		dataIndex : 'orgName',
		align : 'center',
		xtype : 'ellipsiscolumn',
		text : '转运场'
	},{
		dataIndex : 'statisticsTimeTheory',
		align : 'center',
		xtype : 'ellipsiscolumn',
		text : '日期'
	},{
		text : '前一日剩余派送量',
		columns : [{
			dataIndex : 'yesterdayStockWaybill',
			align : 'center',
			xtype : 'ellipsiscolumn',
			text : '票数'
		},{
			dataIndex : 'yesterdayStockWeight',
			align : 'center',
			xtype : 'ellipsiscolumn',
			text : '重量'
		},{
			dataIndex : 'yesterdayStockVolume',
			align : 'center',
			xtype : 'ellipsiscolumn',
			text : '体积'
		}]
	},{
		text : '当日入库货量',
		columns : [{
			dataIndex : 'dayStockWaybill',
			align : 'center',
			xtype : 'ellipsiscolumn',
			text : '票数'
		},{
			dataIndex : 'dayStockWeight',
			align : 'center',
			xtype : 'ellipsiscolumn',
			text : '重量'
		},{
			dataIndex : 'dayStockVolume',
			align : 'center',
			xtype : 'ellipsiscolumn',
			text : '体积'
		}]
	}, {
		text : '已派送量',
		columns : [{
			dataIndex : 'daySendWaybill',
			align : 'center',
			xtype : 'ellipsiscolumn',
			text : '票数'
		},{
			dataIndex : 'daySendWeight',
			align : 'center',
			xtype : 'ellipsiscolumn',
			text : '重量'
		},{
			dataIndex : 'daySendVolume',
			align : 'center',
			xtype : 'ellipsiscolumn',
			text : '体积'
		}]
	},{
		dataIndex : 'sendRate',
		align : 'center',
		xtype : 'ellipsiscolumn',
		text : '日派送率(F)',
			renderer : function(value){
				if(value != null){
					return value+'%';
				}else{
					return value;
				}
			}		
	},{
		dataIndex : 'sendRateAll',
		align : 'center',
		xtype : 'ellipsiscolumn',
		text : '月累计派送率(F)',
			renderer : function(value){
				if(value != null){
					return value+'%';
				}else{
					return value;
				}
			}		
	}]
//	,
//	dockedItems: [{ sendRateAll
//	    xtype: 'toolbar',
//	    dock: 'bottom',
//	    layout : 'column',
//	    defaults: {
//			xtype : 'textfield',
//			readOnly : true,
//			labelWidth : 120
//		},
//		items: [{
//			fieldLabel : '合计',
//			xtype : 'textfield',
//			readOnly : true,
//			columnWidth : 1/4,
//			id : 'Foss_platform_deliverGoodsAreaQuery_deliverRateTotalCount'
//			}],
//	}]
});

//日退单率 之查询form
Ext.define('Foss.platform.deliverGoodsAreaQuery.dayRefundRateQueryForm', {
	extend : 'Ext.form.Panel',
	title : '查询条件',
	frame : true,
	collapsible : true,
	animCollapse : true,
	defaults : {
		margin : '5 10 5 10',
		labelWidth : 85,
		columnWidth : 1 / 4,
		xtype : 'textfield'
	},
	layout : 'column',
	items : [{
		fieldLabel : '转运场',
		name : 'destOrgCode',
		xtype : 'dynamicorgcombselector',
		type : 'ORG',
		columnWidth : 1/4,
		readOnly : platform.deliverGoodsAreaQuery.outfieldCanBeModified,
		value : platform.deliverGoodsAreaQuery.outfieldName,
		transferCenter : 'Y'
	}, {
		fieldLabel : '当日时间',
		readOnly : true,
		value : platform.deliverGoodsAreaQuery.serverTimeString,
		name : 'queryTime',
		columnWidth : 1/4
	}, {
		border : false,
		xtype : 'container',
		columnWidth : 1,
		layout : 'column',
		defaults : {
			margin : '5 0 5 0'
		},
		items : [ {
			xtype : 'button',
			columnWidth : .08,
			hidden : platform.deliverGoodsAreaQuery.outfieldCanBeModified,
			text : '重置',
			handler : function() {
				if(!platform.deliverGoodsAreaQuery.outfieldCanBeModified){
					platform.deliverGoodsAreaQuery.queryReturnDayLoadingForm.getForm().findField('destOrgCode').setValue("");
				}
			}
		}, {
			border : false,
			columnWidth : .84,
			html : '&nbsp;'
		}, {
			columnWidth : .08,
			xtype : 'button',
			cls : 'yellow_button',
			text : '查询',
			handler : function(){
				if(platform.deliverGoodsAreaQuery.queryReturnDayLoadingForm.getForm().isValid()){
					platform.deliverGoodsAreaQuery.loadingReturnDayRateGrid.store.load();
				}
			}
		} ]
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
});

//日退单率 列表之Model
Ext.define('Foss.platform.deliverGoodsAreaQuery.dayRefundRateModel', {
	extend : 'Ext.data.Model',
	//定义字段
	fields : [{
		name : 'orgName',
		type : 'string'
	},{
		name : 'statisticsTimeTheory',
		type : 'date',
		convert:function(value) {
			if (value != null) {
				var date = new Date(value);						
				return Ext.Date.format(date,'Y-m-d');
			} else {
				return null;
			}
		}	
	}, {
		name : 'forecastWaybill',
		type : 'string'
	}, {
		name : 'quantityCarReality',
		type : 'string'
	}, {
		name : 'quantityReturn',
		type : 'string'
	}, {
		name : 'returnRate',
		type : 'string'
	},{
		name : 'dataTimeQuery',
		type : 'string'
	}]
});

//日退单率 列表之store
Ext.define('Foss.platform.deliverGoodsAreaQuery.dayRefundRateStore', {
	extend : 'Ext.data.Store',
	pageSize : 20,
	// 绑定一个模型
	model : 'Foss.platform.deliverGoodsAreaQuery.dayRefundRateModel',
	// 定义一个代理对象
	proxy : {
		type : 'ajax',
		actionMethods:'POST',
		// 请求的url
		url : platform.realPath('returnRateDayQuery.action'),
		timeout:300000,
		// 定义一个读取器
		reader : {
			// 以JSON的方式读取
			type : 'json',
			// 定义读取JSON数据的根对象
			root : 'deliverGoodsAreaQueryVo.returnRateList',
			successProperty: 'success',
			totalProperty : 'totalCount'
		}
	},
	listeners: {
		beforeload : function(store, operation, eOpts) {
				var queryOrgCode = '';
				if(platform.deliverGoodsAreaQuery.outfieldCanBeModified){
					queryOrgCode = platform.deliverGoodsAreaQuery.outfieldCode;
				}else{
					queryOrgCode = platform.deliverGoodsAreaQuery.queryReturnDayLoadingForm.getValues().destOrgCode;
				}
			
				Ext.apply(operation, {
					params : {
						'deliverGoodsAreaQueryVo.returnRateEntity.orgCode':queryOrgCode
					}
				});	
		},
		'load': function(records, operation, success){
			if(records.data.items[0]){
				if(records.data.items[0].get('dataTimeQuery')){
					var dTime = records.data.items[0].get('dataTimeQuery');
					platform.deliverGoodsAreaQuery.queryReturnDayLoadingForm.getForm().findField('queryTime').setValue(dTime);
				}
				
			}
//				for(var i = 0; i<records.data.length;i++){
//					var record = records.data.items[i];
//				}
			}
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
});

//日退单率 列表
Ext.define('Foss.platform.deliverGoodsAreaQuery.dayRefundRateGrid', {
	extend : 'Ext.grid.Panel',
	frame : true,
	columnLines: true,
	title : '日退单率',
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	emptyText : '查询结果为空',
	autoScroll : true,
	collapsible : true,
	sortableColumns : false,
	enableColumnHide : false,
	animCollapse : true,
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.platform.deliverGoodsAreaQuery.dayRefundRateStore');
		
		me.bbar = Ext.create('Deppon.StandardPaging',{
			store : me.store,
			pageSize : 20,
			maximumSize : 50,
			plugins : Ext.create('Deppon.ux.PageSizePlugin', {
				sizeList : [['20', 20], ['30', 30], ['40', 40], ['50', 50]]
			})
		});
		platform.deliverGoodsAreaQuery.dayRefundRateGridPagingBar = me.bbar;
		me.callParent([cfg]);
	},
	tbar : [{
		xtype : 'button',
		text : '导出',
		handler: function(){
			var actionUrl=platform.realPath('returnRateDayExport.action');
			var queryOrgCode = '';
			if(platform.deliverGoodsAreaQuery.outfieldCanBeModified){
				queryOrgCode = platform.deliverGoodsAreaQuery.outfieldCode;
			}else{
				queryOrgCode = platform.deliverGoodsAreaQuery.queryReturnDayLoadingForm.getValues().destOrgCode;
			}
			var exportParams = {
					'deliverGoodsAreaQueryVo.returnRateEntity.orgCode':queryOrgCode
			}; 
			if(!Ext.fly('downloadReturnRateFileForm')){
							    var frm = document.createElement('form');
							    frm.id = 'downloadReturnRateFileForm';
							    frm.style.display = 'none';
							    document.body.appendChild(frm);
			}
			
			Ext.Ajax.request({
			url:actionUrl,
			form: Ext.fly('downloadReturnRateFileForm'),
			method : 'POST',
			params : exportParams,
			isUpload: true,
			exception : function(response,opts) {
				var result = Ext.decode(response.responseText);
				Ext.MessageBox.alert('提示','导出失败',result.message);
				//myMask.hide();
			}	
			});
		}
	}],
	columns : [{
		dataIndex : 'orgName',
		align : 'center',
		flex : 1,
		xtype : 'ellipsiscolumn',
		text : '转运场'
	},{
		dataIndex : 'statisticsTimeTheory',
		align : 'center',
		flex : 1,
		xtype : 'ellipsiscolumn',
		text : '日期'
	}, {
		dataIndex : 'forecastWaybill',
		align : 'center',
		flex : 1,
		xtype : 'ellipsiscolumn',
		text : '排单票数'		
	},{
		dataIndex : 'quantityCarReality',
		align : 'center',
		flex : 1,
		xtype : 'ellipsiscolumn',
		text : '装车票数'		
	},{
		dataIndex : 'quantityReturn',
		align : 'center',
		flex : 1,
		xtype : 'ellipsiscolumn',
		text : '退单票数'		
	},{
		dataIndex : 'returnRate',
		align : 'center',
		flex : 1,
		xtype : 'ellipsiscolumn',
		text : '退单率'	,
			renderer : function(value){
				if(value != null){
					return value+'%';
				}else{
					return value;
				}
			}	
	}]
});

//累计退单率 之查询form
Ext.define('Foss.platform.deliverGoodsAreaQuery.totalRefundRateQueryForm', {
	extend : 'Ext.form.Panel',
	title : '查询条件',
	frame : true,
	collapsible : true,
	animCollapse : true,
	defaults : {
		margin : '5 10 5 10',
		labelWidth : 85,
		columnWidth : 1 / 4,
		xtype : 'textfield'
	},
	layout : 'column',
	items : [{
		fieldLabel : '转运场',
		name : 'destOrgCode',
		xtype : 'dynamicorgcombselector',
		readOnly : platform.deliverGoodsAreaQuery.outfieldCanBeModified,
		value : platform.deliverGoodsAreaQuery.outfieldName,
		type : 'ORG',
		columnWidth : 1/4,
		transferCenter : 'Y'
	},  {
		xtype: 'datefield',
		fieldLabel:  '日期',
		dateType: 'datetimefield_date97',
		name: 'totalDeliverRateQueryEndTime',
		format: 'Y-m-d', 
		value: Ext.Date.format(new Date(new Date().getFullYear(),
				new Date().getMonth(), new Date().getDate()-1), 'Y-m-d'),
		maxValue: Ext.Date.format(new Date(new Date().getFullYear(),
										new Date().getMonth(), new Date().getDate()-1), 'Y-m-d'),
		columnWidth : 1/4
	}, {
		border : false,
		xtype : 'container',
		columnWidth : 1,
		layout : 'column',
		defaults : {
			margin : '5 0 5 0'
		},
		items : [ {
			xtype : 'button',
			columnWidth : .08,
			hidden : platform.deliverGoodsAreaQuery.outfieldCanBeModified,
			text : '重置',
			handler : function() {
				if(!platform.deliverGoodsAreaQuery.outfieldCanBeModified){
					platform.deliverGoodsAreaQuery.queryReturnLogLoadingForm.getForm().reset();
				}	
			}
		}, {
			border : false,
			columnWidth : .84,
			html : '&nbsp;'
		}, {
			columnWidth : .08,
			xtype : 'button',
			cls : 'yellow_button',
			text : '查询',
			handler : function(){
				if(platform.deliverGoodsAreaQuery.queryReturnLogLoadingForm.getForm().isValid()){
					var endTime = platform.deliverGoodsAreaQuery.queryReturnLogLoadingForm.getValues().totalDeliverRateQueryEndTime;
					
					if(!endTime){
						Ext.MessageBox.alert('提示','请输入日期');
						return;
					}
					platform.deliverGoodsAreaQuery.loadingReturnLogRateGrid.store.load();
				}
				
			}
		} ]
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
});

//累计退单率 列表之Model
Ext.define('Foss.platform.deliverGoodsAreaQuery.totalRefundRateModel', {
	extend : 'Ext.data.Model',
	//定义字段
	fields : [{
		name : 'orgName',
		type : 'string'
	},{
		name : 'statisticsTimeTheory',
		type : 'date',
		convert: function(value) {
			if (value != null) {
				var date = new Date(value);						
				return Ext.Date.format(date,'Y-m-d');
			} else {
				return null;
			}
		}	
	}, {
		name : 'forecastWaybill',
		type : 'string'
	}, {
		name : 'quantityCarReality',
		type : 'string'
	}, {
		name : 'quantityReturn',
		type : 'string'
	}, {
		name : 'returnRate',
		type : 'string'
	}, {
		name : 'returnRateAll',
		type : 'string'
	}]
});

//累计退单率 列表之store
Ext.define('Foss.platform.deliverGoodsAreaQuery.totalRefundRateStore', {
	extend : 'Ext.data.Store',
	pageSize : 20,
	// 绑定一个模型
	model : 'Foss.platform.deliverGoodsAreaQuery.totalRefundRateModel',
	// 定义一个代理对象
	proxy : {
		type : 'ajax',
		actionMethods:'POST',
		// 请求的url
		url : platform.realPath('returnRateLogQuery.action'),
		timeout:300000,
		// 定义一个读取器
		reader : {
			// 以JSON的方式读取
			type : 'json',
			// 定义读取JSON数据的根对象
			root : 'deliverGoodsAreaQueryVo.returnRateList',
			successProperty: 'success',
			totalProperty : 'totalCount'
		}
	},
	listeners: {
		beforeload : function(store, operation, eOpts) {
			var queryOrgCode = '';
			if(platform.deliverGoodsAreaQuery.outfieldCanBeModified){
				queryOrgCode = platform.deliverGoodsAreaQuery.outfieldCode;
			}else{
				queryOrgCode = platform.deliverGoodsAreaQuery.queryReturnLogLoadingForm.getValues().destOrgCode;
			}
			var endTime = platform.deliverGoodsAreaQuery.queryReturnLogLoadingForm.getValues().totalDeliverRateQueryEndTime;
				Ext.apply(operation, {
					params : {
						'deliverGoodsAreaQueryVo.returnRateEntity.orgCode':queryOrgCode,
						'deliverGoodsAreaQueryVo.returnRateEntity.endDate':endTime
					}
				});	
		}
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
});

//累计退单率 列表
Ext.define('Foss.platform.deliverGoodsAreaQuery.totalRefundRateGrid', {
	extend : 'Ext.grid.Panel',
	frame : true,
	columnLines: true,
	title : '累计退单率',
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	emptyText : '查询结果为空',
	autoScroll : true,
	collapsible : true,
	sortableColumns : false,
	enableColumnHide : false,
	animCollapse : true,
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.platform.deliverGoodsAreaQuery.totalRefundRateStore');
		
		me.bbar = Ext.create('Deppon.StandardPaging',{
			store : me.store,
			pageSize : 20,
			maximumSize : 50,
			plugins : Ext.create('Deppon.ux.PageSizePlugin', {
				sizeList : [['20', 20], ['30', 30], ['40', 40], ['50', 50]]
			})
		});
		platform.deliverGoodsAreaQuery.totalRefundRateGridPagingBar = me.bbar;
		me.callParent([cfg]);
	},
	tbar : [{
		xtype : 'button',
		text : '导出',
		handler: function(){
			var actionUrl=platform.realPath('returnRateLogExport.action');
			var queryOrgCode = '';
			if(platform.deliverGoodsAreaQuery.outfieldCanBeModified){
				queryOrgCode = platform.deliverGoodsAreaQuery.outfieldCode;
			}else{
				queryOrgCode = platform.deliverGoodsAreaQuery.queryReturnLogLoadingForm.getValues().destOrgCode;
			}
			
			var endTime = platform.deliverGoodsAreaQuery.queryReturnLogLoadingForm.getValues().totalDeliverRateQueryEndTime;
			
			var exportParams = {
					'deliverGoodsAreaQueryVo.returnRateEntity.orgCode':queryOrgCode,
					'deliverGoodsAreaQueryVo.returnRateEntity.endDate':endTime
			}; 
			if(!Ext.fly('downloadReturnRateLogFileForm')){
							    var frm = document.createElement('form');
							    frm.id = 'downloadReturnRateLogFileForm';
							    frm.style.display = 'none';
							    document.body.appendChild(frm);
			}
			
			Ext.Ajax.request({
			url:actionUrl,
			form: Ext.fly('downloadReturnRateLogFileForm'),
			method : 'POST',
			params : exportParams,
			isUpload: true,
			exception : function(response,opts) {
				var result = Ext.decode(response.responseText);
				Ext.MessageBox.alert('提示','导出失败',result.message);
				//myMask.hide();
			}	
			});
		}
	}],
	columns : [{
		dataIndex : 'orgName',
		align : 'center',
		flex : 1,
		xtype : 'ellipsiscolumn',
		text : '转运场'
	},{
		dataIndex : 'statisticsTimeTheory',
		align : 'center',
		flex : 1,
		xtype : 'ellipsiscolumn',
		text : '日期'
	}, {
		dataIndex : 'forecastWaybill',
		align : 'center',
		flex : 1,
		xtype : 'ellipsiscolumn',
		text : '排单票数'		
	},{
		dataIndex : 'quantityCarReality',
		align : 'center',
		flex : 1,
		xtype : 'ellipsiscolumn',
		text : '装车票数'		
	},{
		dataIndex : 'quantityReturn',
		align : 'center',
		flex : 1,
		xtype : 'ellipsiscolumn',
		text : '退单票数'		
	},{
		dataIndex : 'returnRate',
		align : 'center',
		flex : 1,
		xtype : 'ellipsiscolumn',
		text : '日退单率',
			renderer : function(value){
				if(value != null){
					return value+'%';
				}else{
					return value;
				}
			}		
	},{
		dataIndex : 'returnRateAll',
		align : 'center',
		flex : 1,
		xtype : 'ellipsiscolumn',
		text : '月累计退单率',
			renderer : function(value){
				if(value != null){
					return value+'%';
				}else{
					return value;
				}
			}		
	}]
});

//日拉回率 之查询form
Ext.define('Foss.platform.deliverGoodsAreaQuery.dayPullbackRateQueryForm', {
	extend : 'Ext.form.Panel',
	title : '查询条件',
	frame : true,
	collapsible : true,
	animCollapse : true,
	defaults : {
		margin : '5 10 5 10',
		labelWidth : 85,
		columnWidth : 1 / 4,
		xtype : 'textfield'
	},
	layout : 'column',
	items : [{
		fieldLabel : '转运场',
		name : 'destOrgCode',
		xtype : 'dynamicorgcombselector',
		type : 'ORG',
		readOnly : platform.deliverGoodsAreaQuery.outfieldCanBeModified,
		value : platform.deliverGoodsAreaQuery.outfieldName,
		columnWidth : 1/4,
		transferCenter : 'Y'
	}, {
		fieldLabel : '当日时间',
		columnWidth : 1/4,
		value : platform.deliverGoodsAreaQuery.serverTimeString,
		name  : 'queryTime',
		readOnly : true
	}, {
		border : false,
		xtype : 'container',
		columnWidth : 1,
		layout : 'column',
		defaults : {
			margin : '5 0 5 0'
		},
		items : [ {
			xtype : 'button',
			columnWidth : .08,
			hidden : platform.deliverGoodsAreaQuery.outfieldCanBeModified,
			text : '重置',
			handler : function() {
				if(!platform.deliverGoodsAreaQuery.outfieldCanBeModified){
					platform.deliverGoodsAreaQuery.queryPullbackDayLoadingForm.getForm().findField('destOrgCode').setValue("");
				}
			}
		}, {
			border : false,
			columnWidth : .84,
			html : '&nbsp;'
		}, {
			columnWidth : .08,
			xtype : 'button',
			cls : 'yellow_button',
			text : '查询',
			handler : function(){
				if(platform.deliverGoodsAreaQuery.queryPullbackDayLoadingForm.getForm().isValid()){
					platform.deliverGoodsAreaQuery.loadingPullbackDayRateGrid.store.load();
				}
			}
		} ]
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
});

//日拉回率 列表之Model
Ext.define('Foss.platform.deliverGoodsAreaQuery.dayPullbackRateModel', {
	extend : 'Ext.data.Model',
	//定义字段
	fields : [{
		name : 'orgName',
		type : 'string'
	},{
		name : 'statisticsTimeTheory',
		type : 'date',
		convert: function(value) {
			if (value != null) {
				var date = new Date(value);						
				return Ext.Date.format(date,'Y-m-d');
			} else {
				return null;
			}
		}	
	}, {
		name : 'quantityCar',
		type : 'string'
	}, {
		name : 'quantityPullback',
		type : 'string'
	}, {
		name : 'pullbackRate',
		type : 'string'
	},{
		name : 'dataTimeQuery',
		type : 'string'
	}]
});

//日拉回率 列表之store
Ext.define('Foss.platform.deliverGoodsAreaQuery.dayPullbackRateStore', {
	extend : 'Ext.data.Store',
	pageSize : 20,
	// 绑定一个模型
	model : 'Foss.platform.deliverGoodsAreaQuery.dayPullbackRateModel',
	// 定义一个代理对象
	proxy : {
		type : 'ajax',
		actionMethods:'POST',
		// 请求的url
		url : platform.realPath('pullbackRateDayQuery.action'),
		timeout:300000,
		// 定义一个读取器
		reader : {
			// 以JSON的方式读取
			type : 'json',
			// 定义读取JSON数据的根对象
			root : 'deliverGoodsAreaQueryVo.pullbackRateEntityList',
			successProperty: 'success',
			totalProperty : 'totalCount'
		}
	},
	listeners: {
		beforeload : function(store, operation, eOpts) {
			var queryOrgCode = '';
			if(platform.deliverGoodsAreaQuery.outfieldCanBeModified){
				queryOrgCode = platform.deliverGoodsAreaQuery.outfieldCode;
			}else{
				queryOrgCode = platform.deliverGoodsAreaQuery.queryPullbackDayLoadingForm.getValues().destOrgCode;
			}
				Ext.apply(operation, {
					params : {
						'deliverGoodsAreaQueryVo.pullbackRateEntity.orgCode':queryOrgCode
					}
				});	
		},
		'load': function(records, operation, success){
			if(records.data.items[0]){
				if(records.data.items[0].get('dataTimeQuery')){
					platform.deliverGoodsAreaQuery.queryPullbackDayLoadingForm.getForm().findField('queryTime').setValue(records.data.items[0].get('dataTimeQuery'));
				}
				
			}
//				for(var i = 0; i<records.data.length;i++){
//					var record = records.data.items[i];
//				}
			}
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
});

//日拉回率 列表
Ext.define('Foss.platform.deliverGoodsAreaQuery.dayPullbackRateGrid', {
	extend : 'Ext.grid.Panel',
	frame : true,
	columnLines: true,
	title : '日拉回率',
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	emptyText : '查询结果为空',
	autoScroll : true,
	collapsible : true,
	sortableColumns : false,
	enableColumnHide : false,
	animCollapse : true,
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.platform.deliverGoodsAreaQuery.dayPullbackRateStore');
		
		me.bbar = Ext.create('Deppon.StandardPaging',{
			store : me.store,
			pageSize : 20,
			maximumSize : 50,
			plugins : Ext.create('Deppon.ux.PageSizePlugin', {
				sizeList : [['20', 20], ['30', 30], ['40', 40], ['50', 50]]
			})
		});
		platform.deliverGoodsAreaQuery.dayPullbackRateGridPagingBar = me.bbar;
		me.callParent([cfg]);
	},
	tbar : [{
		xtype : 'button',
		text : '导出',
		handler: function(){
			var actionUrl=platform.realPath('pullbackRateDayExport.action');
			var queryOrgCode = '';
			if(platform.deliverGoodsAreaQuery.outfieldCanBeModified){
				queryOrgCode = platform.deliverGoodsAreaQuery.outfieldCode;
			}else{
				queryOrgCode = platform.deliverGoodsAreaQuery.queryPullbackDayLoadingForm.getValues().destOrgCode;
			}
			var exportParams = {
					'deliverGoodsAreaQueryVo.pullbackRateEntity.orgCode':queryOrgCode
					}; 
			if(!Ext.fly('downloadPullbackRateFileForm')){
							    var frm = document.createElement('form');
							    frm.id = 'downloadPullbackRateFileForm';
							    frm.style.display = 'none';
							    document.body.appendChild(frm);
			}
			
			Ext.Ajax.request({
			url:actionUrl,
			form: Ext.fly('downloadPullbackRateFileForm'),
			method : 'POST',
			params : exportParams,
			isUpload: true,
			exception : function(response,opts) {
				var result = Ext.decode(response.responseText);
				Ext.MessageBox.alert('提示','导出失败',result.message);
				//myMask.hide();
			}	
			});
		}
	}],
	columns : [{
		dataIndex : 'orgName',
		align : 'center',
		flex : 1,
		xtype : 'ellipsiscolumn',
		text : '转运场'
	},{
		dataIndex : 'statisticsTimeTheory',
		align : 'center',
		flex : 1,
		xtype : 'ellipsiscolumn',
		text : '日期'
	}, {
		dataIndex : 'quantityCar',
		align : 'center',
		flex : 1,
		xtype : 'ellipsiscolumn',
		text : '装车票数'		
	},{
		dataIndex : 'quantityPullback',
		align : 'center',
		flex : 1,
		xtype : 'ellipsiscolumn',
		text : '拉回票数'		
	},{
		dataIndex : 'pullbackRate',
		align : 'center',
		flex : 1,
		xtype : 'ellipsiscolumn',
		text : '拉回率',
			renderer : function(value){
				if(value != null){
					return value+'%';
				}else{
					return value;
				}
			}		
	}]
});

//累计拉回率 之查询form
Ext.define('Foss.platform.deliverGoodsAreaQuery.totalPullbackRateQueryForm', {
	extend : 'Ext.form.Panel',
	title : '查询条件',
	frame : true,
	collapsible : true,
	animCollapse : true,
	defaults : {
		margin : '5 10 5 10',
		labelWidth : 85,
		columnWidth : 1 / 4,
		xtype : 'textfield'
	},
	layout : 'column',
	items : [{
		fieldLabel : '转运场',
		name : 'destOrgCode',
		xtype : 'dynamicorgcombselector',
		readOnly : platform.deliverGoodsAreaQuery.outfieldCanBeModified,
		value : platform.deliverGoodsAreaQuery.outfieldName,
		type : 'ORG',
		columnWidth : 1/4,
		transferCenter : 'Y'
	}, {
		xtype: 'datefield',
		fieldLabel:  '日期',
		dateType: 'datetimefield_date97',
		name: 'totalDeliverRateQueryEndTime',
		format: 'Y-m-d', 
		value: Ext.Date.format(new Date(new Date().getFullYear(),
				new Date().getMonth(), new Date().getDate()-1), 'Y-m-d'),
		maxValue: Ext.Date.format(new Date(new Date().getFullYear(),
										new Date().getMonth(), new Date().getDate()-1), 'Y-m-d'),
		columnWidth : 1/4
	}, {
		border : false,
		xtype : 'container',
		columnWidth : 1,
		layout : 'column',
		defaults : {
			margin : '5 0 5 0'
		},
		items : [ {
			xtype : 'button',
			columnWidth : .08,
			hidden : platform.deliverGoodsAreaQuery.outfieldCanBeModified,
			text : '重置',
			handler : function() {
				if(!platform.deliverGoodsAreaQuery.outfieldCanBeModified){
					platform.deliverGoodsAreaQuery.queryPullbackLogLoadingForm.getForm().reset();
				}			
			}
		}, {
			border : false,
			columnWidth : .84,
			html : '&nbsp;'
		}, {
			columnWidth : .08,
			xtype : 'button',
			cls : 'yellow_button',
			text : '查询',
			handler : function(){
				if(platform.deliverGoodsAreaQuery.queryPullbackLogLoadingForm.getForm().isValid()){
					var endTime = platform.deliverGoodsAreaQuery.queryPullbackLogLoadingForm.getValues().totalDeliverRateQueryEndTime;
					
					if(!endTime){
						Ext.MessageBox.alert('提示','请输入日期');
						return;
					}
					platform.deliverGoodsAreaQuery.loadingPullbackLogRateGrid.store.load();
				}
			}
		} ]
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
});

//累计拉回率 列表之Model
Ext.define('Foss.platform.deliverGoodsAreaQuery.totalPullbackRateModel', {
	extend : 'Ext.data.Model',
	//定义字段
	fields : [{
		name : 'orgName',
		type : 'string'
	},{
		name : 'statisticsTimeTheory',
		type : 'date',
		convert:function(value) {
			if (value != null) {
				var date = new Date(value);						
				return Ext.Date.format(date,'Y-m-d');
			} else {
				return null;
			}
		}	
	}, {
		name : 'quantityCar',
		type : 'string'
	}, {
		name : 'quantityPullback',
		type : 'string'
	}, {
		name : 'pullbackRate',
		type : 'string'
	}, {
		name : 'pullbackRateAll',
		type : 'string'
	}]
});

//累计拉回率 列表之store
Ext.define('Foss.platform.deliverGoodsAreaQuery.totalPullbackRateStore', {
	extend : 'Ext.data.Store',
	pageSize : 20,
	// 绑定一个模型
	model : 'Foss.platform.deliverGoodsAreaQuery.totalPullbackRateModel',
	// 定义一个代理对象
	proxy : {
		type : 'ajax',
		actionMethods:'POST',
		// 请求的url
		url : platform.realPath('pullbackRateLogQuery.action'),
		timeout:300000,
		// 定义一个读取器
		reader : {
			// 以JSON的方式读取
			type : 'json',
			// 定义读取JSON数据的根对象
			root : 'deliverGoodsAreaQueryVo.pullbackRateEntityList',
			successProperty: 'success',
			totalProperty : 'totalCount'
		}
	},
	listeners: {
		beforeload : function(store, operation, eOpts) {
			var queryOrgCode = '';
			if(platform.deliverGoodsAreaQuery.outfieldCanBeModified){
				queryOrgCode = platform.deliverGoodsAreaQuery.outfieldCode;
			}else{
				queryOrgCode = platform.deliverGoodsAreaQuery.queryPullbackLogLoadingForm.getValues().destOrgCode;
			}
			
			var endTime = platform.deliverGoodsAreaQuery.queryPullbackLogLoadingForm.getValues().totalDeliverRateQueryEndTime;
			
			
				Ext.apply(operation, {
					params : {
						'deliverGoodsAreaQueryVo.pullbackRateEntity.orgCode':queryOrgCode,
						'deliverGoodsAreaQueryVo.pullbackRateEntity.endDate':endTime
					}
				});	
		}
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
});

//累计拉回率 列表
Ext.define('Foss.platform.deliverGoodsAreaQuery.totalPullbackRateGrid', {
	extend : 'Ext.grid.Panel',
	frame : true,
	columnLines: true,
	title : '累计拉回率',
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	emptyText : '查询结果为空',
	autoScroll : true,
	collapsible : true,
	sortableColumns : false,
	enableColumnHide : false,
	animCollapse : true,
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.platform.deliverGoodsAreaQuery.totalPullbackRateStore');
		
		me.bbar = Ext.create('Deppon.StandardPaging',{
			store : me.store,
			pageSize : 20,
			maximumSize : 50,
			plugins : Ext.create('Deppon.ux.PageSizePlugin', {
				sizeList : [['20', 20], ['30', 30], ['40', 40], ['50', 50]]
			})
		});
		platform.deliverGoodsAreaQuery.totalPullbackRateGridPagingBar = me.bbar;
		me.callParent([cfg]);
	},
	tbar : [{
		xtype : 'button',
		text : '导出',
		handler: function(){
			var actionUrl=platform.realPath('pullbackRateLogExport.action');
			var queryOrgCode = '';
			if(platform.deliverGoodsAreaQuery.outfieldCanBeModified){
				queryOrgCode = platform.deliverGoodsAreaQuery.outfieldCode;
			}else{
				queryOrgCode = platform.deliverGoodsAreaQuery.queryPullbackLogLoadingForm.getValues().destOrgCode;
			}
			
			var endTime = platform.deliverGoodsAreaQuery.queryPullbackLogLoadingForm.getValues().totalDeliverRateQueryEndTime;
			
			if(!endTime){
				Ext.MessageBox.alert('提示','请输入日期');
				return;
			}
			
			var exportParams = {
					'deliverGoodsAreaQueryVo.pullbackRateEntity.orgCode':queryOrgCode,
					'deliverGoodsAreaQueryVo.pullbackRateEntity.endDate':endTime
					}; 
			if(!Ext.fly('downloadPullbackRateLogFileForm')){
							    var frm = document.createElement('form');
							    frm.id = 'downloadPullbackRateLogFileForm';
							    frm.style.display = 'none';
							    document.body.appendChild(frm);
			}
			
			Ext.Ajax.request({
			url:actionUrl,
			form: Ext.fly('downloadPullbackRateLogFileForm'),
			method : 'POST',
			params : exportParams,
			isUpload: true,
			exception : function(response,opts) {
				var result = Ext.decode(response.responseText);
				Ext.MessageBox.alert('提示','导出失败',result.message);
				//myMask.hide();
			}	
			});
		}
	}],
	columns : [{
		dataIndex : 'orgName',
		align : 'center',
		flex : 1,
		xtype : 'ellipsiscolumn',
		text : '转运场'
	},{
		dataIndex : 'statisticsTimeTheory',
		align : 'center',
		flex : 1,
		xtype : 'ellipsiscolumn',
		text : '日期'
	}, {
		dataIndex : 'quantityCar',
		align : 'center',
		flex : 1,
		xtype : 'ellipsiscolumn',
		text : '装车票数'		
	},{
		dataIndex : 'quantityPullback',
		align : 'center',
		flex : 1,
		xtype : 'ellipsiscolumn',
		text : '拉回票数'		
	},{
		dataIndex : 'pullbackRate',
		align : 'center',
		flex : 1,
		xtype : 'ellipsiscolumn',
		text : '日拉回率',
			renderer : function(value){
				if(value != null){
					return value+'%';
				}else{
					return value;
				}
			}		
	},{
		dataIndex : 'pullbackRateAll',
		align : 'center',
		flex : 1,
		xtype : 'ellipsiscolumn',
		text : '月累计拉回率',
			renderer : function(value){
				if(value != null){
					return value+'%';
				}else{
					return value;
				}
			}		
	}]
});

//票均装车时长 之查询form    author:218427 hongwy
Ext.define('Foss.platform.deliverGoodsAreaQuery.waybillAvgTimeForm',{
	extend : 'Ext.form.Panel',
	title:'查询条件',
	frame : true,
	collapsible : true,
	animCollapse : true,
	defaults : {
		margin : '5 10 5 10',
		labelWidth : 85,
		columnWidth : 1 / 4,
		xtype : 'textfield'
	},
	layout : 'column',
	items:[{
		fieldLabel : '转运场',
		name : 'destOrgCode',
		xtype : 'dynamicorgcombselector',
		type : 'ORG',
		columnWidth : 1/4,
		transferCenter : 'Y'
	  },{
	    xtype: 'datefield',
		fieldLabel:  '日期',
		dateType: 'datetimefield_date97',
		name: 'queryDate',
		allowBlank:false,
		format: 'Y-m-d', 
		value: Ext.Date.format(new Date(new Date().getFullYear(),
						new Date().getMonth(), new Date().getDate()-1), 'Y-m-d'),
		maxValue: Ext.Date.format(new Date(new Date().getFullYear(),
										new Date().getMonth(), new Date().getDate()-1), 'Y-m-d'),
		columnWidth : 1/4	  
	  },{
			border : false,
			xtype : 'container',
			columnWidth : 1,
			layout : 'column',
			defaults : {
			margin : '5 0 5 0'
	        },
	        items : [ {
			xtype : 'button',
			columnWidth : .08,
			hidden : platform.deliverGoodsAreaQuery.outfieldCanBeModified,
			text : '重置',
			handler : function() {
				if(!platform.deliverGoodsAreaQuery.outfieldCanBeModified){
					platform.deliverGoodsAreaQuery.queryWaybillAvgTimeLoadingForm.getForm().reset();
				}			
			}
		}, {
			border : false,
			columnWidth : .84,
			html : '&nbsp;'
		}, {
			columnWidth : .08,
			xtype : 'button',
			cls : 'yellow_button',
			text : '查询',
			handler : function(){
				
				var form = this.up('form').getForm();
				if(form.isValid()){
					platform.deliverGoodsAreaQuery.loadingWaybillAvgTimeGrid.store.load();
				}
			}
		} ]
	    } ],
		constructor : function(config) {
			var me = this, cfg = Ext.apply({}, config);
			me.callParent([ cfg ]);
		}
});

//票均时长Model author:218427 hongwy
Ext.define('Foss.platform.deliverGoodsAreaQuery.waybillAvgTimeModel',{
	extend : 'Ext.data.Model',
	fields:[{
		name : 'orgName',
		type : 'string'
       },
       /*{
    	name : 'queryDate',
   		type : 'date',
   		convert: dateConvert
   },*/
   	{
	   name :'waybillValidTimeDay',//当日有效装车时长
       type :'string'
   
   },{
	   name :'waybillDay',//当日装车票数
       type :'string'
   
   },{
	   name :'waybillAvgTimeDay',//当日票均装时长
       type :'string'
   
   },{
	   name :'waybillValidTimeMonth',//当月有效装车时长
	   type :'string'
   },{
	   name :'waybillMonth',//当月装车票数
	   type :'string'
   },{
	   name :'waybillAvgTimeMonth',//月累计票均装车时长
	   type :'string'
   }]
  });

//票均装车时长列表之Store  author:218427 hongwy
Ext.define('Foss.platform.deliverGoodsAreaQuery.waybillAvgTimeStore',{
	extend :'Ext.data.Store',
	pageSize : 20,
	model:'Foss.platform.deliverGoodsAreaQuery.waybillAvgTimeModel',
	proxy : {
		type : 'ajax',
		actionMethods:'POST',
        url:platform.realPath('waybillAvgTimeQuery.action'),
	
	    timeout:300000,
	    reader : {
		// 以JSON的方式读取
		type : 'json',
		// 定义读取JSON数据的根对象
		root : 'deliverGoodsAreaQueryVo.waybillAvgTimeEntityList',
		successProperty: 'success',
		totalProperty : 'totalCount'
	   }
    },
    listeners: {
		beforeload : function(store, operation, eOpts) {
			var queryOrgCode = platform.deliverGoodsAreaQuery.queryWaybillAvgTimeLoadingForm.getForm().findField('destOrgCode').getValue();
			var queryDate=platform.deliverGoodsAreaQuery.queryWaybillAvgTimeLoadingForm.getForm().findField('queryDate').getValue();
				Ext.apply(operation, {
					params : {
						'deliverGoodsAreaQueryVo.queryOrgCode':queryOrgCode,
						'deliverGoodsAreaQueryVo.queryDate':queryDate
					}
				});	
		}
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
});

//票均装车时长 列表 author:218427 hongwy
Ext.define('Foss.platform.deliverGoodsAreaQuery.waybillAvgTimeGrid',{
	extend : 'Ext.grid.Panel',
	frame : true,
	columnLines: true,
	title : '票均装车时长<span style="position:absolute;top:-2px;right:30px;color:red;font-size:12px;">备注：派送装车票均时长取值范围（0min,90min]</span>',
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	emptyText : '查询结果为空',
	autoScroll : true,
	collapsible : true,
	sortableColumns : false,
	enableColumnHide : false,
	animCollapse : true,
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.platform.deliverGoodsAreaQuery.waybillAvgTimeStore');
		me.bbar = Ext.create('Deppon.StandardPaging',{
			store : me.store,
			pageSize : 20,
			maximumSize : 50,
			plugins : Ext.create('Deppon.ux.PageSizePlugin', {
				sizeList : [['20', 20], ['30', 30], ['40', 40], ['50', 50]]
			})
		});
		
		platform.deliverGoodsAreaQuery.waybillAvgTimeGridPagingBar = me.bbar;
		me.callParent([cfg]);

	   },
	columns :[{
		dataIndex : 'orgName',
		align : 'center',
		flex : 1,
		xtype : 'ellipsiscolumn',
		text : '转运场'
        },{
		dataIndex : 'waybillValidTimeDay',
		align : 'center',
		flex : 1,
		xtype : 'ellipsiscolumn',
		text : '当日有效装车时长'
        },{
		dataIndex : 'waybillDay',
		align : 'center',
		flex : 1,
		xtype : 'ellipsiscolumn',
		text : '当日装车票数'
        }
        	/*{
        dataIndex : 'queryDate',
    	align : 'center',
   		flex : 1,
   		xtype : 'ellipsiscolumn',
   		text : '日期',
   		renderer:function(value){
			if(value!=null){
				return Ext.Date.format(new Date(value.getFullYear(),value.getMonth(),value.getDate()),'Y-m-d');
			}
		}
	    }*/
	    ,{
	    dataIndex : 'waybillAvgTimeDay',
	    align : 'center',
	    flex : 1,
	    xtype : 'ellipsiscolumn',
	    text : '当日票均装车时长'	
	    },{
	    dataIndex : 'waybillValidTimeMonth',
	    align : 'center',
	    flex : 1,
	    xtype : 'ellipsiscolumn',
	    text : '当月有效装车时长'	
	    },{
	    dataIndex : 'waybillMonth',
	    align : 'center',
	    flex : 1,
	    xtype : 'ellipsiscolumn',
	    text : '当月装车票数'	
	    },{
	    dataIndex : 'waybillAvgTimeMonth',
	    align : 'center',
	    flex : 1,
		xtype : 'ellipsiscolumn',
	    text : '月票均装车时长'	    	
	    }]		
});
		


//TabPanel
Ext.onReady(function() {
	Ext.QuickTips.init();
	Ext.Ajax.timeout=300000;
	//日派送率查询表单
	var querySendDayForm = Ext.create('Foss.platform.deliverGoodsAreaQuery.dayDeliverRateQueryForm'); 
	//累计派送率查询表单
	var querySendLogForm = Ext.create('Foss.platform.deliverGoodsAreaQuery.totalDeliverRateQueryForm'); 
	//日退单率查询表单
	var queryReturnDayForm = Ext.create('Foss.platform.deliverGoodsAreaQuery.dayRefundRateQueryForm'); 
	//累计退单率查询表单
	var queryReturnLogForm = Ext.create('Foss.platform.deliverGoodsAreaQuery.totalRefundRateQueryForm'); 
	//日拉回率查询表单
	var queryPullbackDayForm = Ext.create('Foss.platform.deliverGoodsAreaQuery.dayPullbackRateQueryForm'); 
	//累计拉回率查询表单
	var queryPullbackLogForm = Ext.create('Foss.platform.deliverGoodsAreaQuery.totalPullbackRateQueryForm'); 
	//票均装车时长查询表单
	var querywaybillAvgTimeForm =Ext.create('Foss.platform.deliverGoodsAreaQuery.waybillAvgTimeForm');

	if(platform.deliverGoodsAreaQuery.isAnalyst=="Y"){
		querywaybillAvgTimeForm.getForm().findField('destOrgCode').setValue("");
		querywaybillAvgTimeForm.getForm().findField('destOrgCode').readOnly=false;
	}else if(platform.deliverGoodsAreaQuery.isAnalyst=="N"){
		querywaybillAvgTimeForm.getForm().findField('destOrgCode').setCombValue(platform.deliverGoodsAreaQuery.outfieldName,platform.deliverGoodsAreaQuery.outfieldCode);
		querywaybillAvgTimeForm.getForm().findField('destOrgCode').readOnly=true;
	}else {
		querywaybillAvgTimeForm.getForm().findField('destOrgCode').setValue("错误");
		querywaybillAvgTimeForm.getForm().findField('destOrgCode').hidden=true;
	}
	
	
	//加入全局变更
	platform.deliverGoodsAreaQuery.querySendDayLoadingForm = querySendDayForm;//日派送率查询表单
	platform.deliverGoodsAreaQuery.querySendLogLoadingForm = querySendLogForm;//累计派送率查询表单
	platform.deliverGoodsAreaQuery.queryReturnDayLoadingForm = queryReturnDayForm;//日退单率查询表单
	platform.deliverGoodsAreaQuery.queryReturnLogLoadingForm = queryReturnLogForm;//累计退单率查询表单
	platform.deliverGoodsAreaQuery.queryPullbackDayLoadingForm = queryPullbackDayForm;//日拉回率查询表单
	platform.deliverGoodsAreaQuery.queryPullbackLogLoadingForm = queryPullbackLogForm;//累计拉回率查询表单
	platform.deliverGoodsAreaQuery.queryWaybillAvgTimeLoadingForm=querywaybillAvgTimeForm;//票均装车时长表单
	
	//日派送率信息表格
	var sendDayRateGrid = Ext.create('Foss.platform.deliverGoodsAreaQuery.dayDeliverRateGrid');
	//累计派送率查询表格
	var sendLogRateGrid = Ext.create('Foss.platform.deliverGoodsAreaQuery.totalDeliverRateGrid');
	//日退单率查询表格
	var returnDayRateGrid = Ext.create('Foss.platform.deliverGoodsAreaQuery.dayRefundRateGrid');
	//累计退单率查询表格
	var returnLogRateGrid = Ext.create('Foss.platform.deliverGoodsAreaQuery.totalRefundRateGrid');
	//日拉回率查询表格
	var pullbackDayRateGrid = Ext.create('Foss.platform.deliverGoodsAreaQuery.dayPullbackRateGrid');
	//累计拉回率查询表格
	var pullbackLogRateGrid = Ext.create('Foss.platform.deliverGoodsAreaQuery.totalPullbackRateGrid');
	//票均装车时长
	var waybillAvgTimeGrid = Ext.create('Foss.platform.deliverGoodsAreaQuery.waybillAvgTimeGrid');
	
	//加入全局变更
	platform.deliverGoodsAreaQuery.loadingSendDayRateGrid = sendDayRateGrid;
	platform.deliverGoodsAreaQuery.loadingSendLogRateGrid = sendLogRateGrid;
	platform.deliverGoodsAreaQuery.loadingReturnDayRateGrid = returnDayRateGrid;
	platform.deliverGoodsAreaQuery.loadingReturnLogRateGrid = returnLogRateGrid;
	platform.deliverGoodsAreaQuery.loadingPullbackDayRateGrid = pullbackDayRateGrid;
	platform.deliverGoodsAreaQuery.loadingPullbackLogRateGrid = pullbackLogRateGrid;
	platform.deliverGoodsAreaQuery.loadingWaybillAvgTimeGrid= waybillAvgTimeGrid;
	
	
	
	
	Ext.create('Ext.panel.Panel',{
		id:'T_platform-deliverGoodsAreaQueryIndex_content',
		cls : "panelContentNToolbar",
		bodyCls : 'panelContentNToolbar-body',
		layout : 'auto',
		items : [{
			xtype : 'tabpanel',
			frame : false,
			bodyCls : 'autoHeight',
			cls : 'innerTabPanel',
			items : [{
				title : '日派送数据',
				tabConfig : {
					width : 120
				},
				items : [querySendDayForm,sendDayRateGrid]
			},{
				title : '累计派送率',
				tabConfig : {
					width : 120
				},
				items : [querySendLogForm,sendLogRateGrid]
			},{
				title : '日退单率',
				tabConfig : {
					width : 120
				},
				items : [queryReturnDayForm,returnDayRateGrid]
			},{
				title : '累计退单率',
				tabConfig : {
					width : 120
				},
				items : [queryReturnLogForm,returnLogRateGrid]
			},{
				title : '日拉回率',
				tabConfig : {
					width : 120
				},
				items : [queryPullbackDayForm, pullbackDayRateGrid]
			},{
				title : '累计拉回率',
				tabConfig : {
					width : 120
				},
				items : [queryPullbackLogForm,pullbackLogRateGrid]
			},{
				title : '票均装车时长',
				tabConfig : {
					width : 120
				},
				items : [querywaybillAvgTimeForm,waybillAvgTimeGrid]
			}]
		}],
		renderTo: 'T_platform-deliverGoodsAreaQueryIndex-body'
	});
});