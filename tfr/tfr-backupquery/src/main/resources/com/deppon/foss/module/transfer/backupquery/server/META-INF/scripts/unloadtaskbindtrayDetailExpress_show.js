/**-----------------------------------------------queryForm-----------------------------------------------------*/
//定义托盘扫描查询条件form
Ext.define('Foss.unload.unloadtaskbindtrayDetail.QueryForm', {
	extend : 'Ext.form.Panel',
	title : backupquery.unloadtaskbindtrayDetail.i18n('foss.unload.unloadtaskbindtrayDetail.queryForm.title'),//查询条件
	frame : true,
	collapsible : false,//允许展开收缩
	animCollapse : true,//显示动画效果
	defaults : {
		margin : '5 10 5 10',
		labelWidth : 85,
		columnWidth : 1/ 4,
		xtype : 'textfield',
		readOnly : true
	},
	layout : 'column',
	items : [ {
		fieldLabel : backupquery.unloadtaskbindtrayDetail.i18n('foss.unload.traybindmanager.queryForm.unloadTaskNo'),//卸车任务号
		width : 150,
		name : 'unloadTaskNo'
	},{
		fieldLabel : backupquery.unloadtaskbindtrayDetail.i18n('foss.unload.traybindmanager.unloadBindTrayGird.unloadCreator'),//卸车创建人
		name : 'unloadCreator'
		
	}, {
		fieldLabel : backupquery.unloadtaskbindtrayDetail.i18n('foss.unload.traybindmanager.queryForm.unloadTaskCreateTime'),//任务建立时间
		name : 'unloadTaskCreateTime'
		
	},  {
		border : false,
		xtype : 'container',
		columnWidth : 1,
		layout : 'column',
		defaults : {
			margin : '5 0 5 0'
		},
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
});
/**-----------------------------------------------QueryUnloadbindTrayModel-----------------------------------------------------*/
//定义卸车任务绑定托盘查询结果详细列表
Ext.define('Foss.unload.unloadtaskbindtrayDetail.QueryUnloadbindTrayModel',{
	extend : 'Ext.data.Model',
	fields : [ {
		name : 'unloadTaskNo',
		type : 'string'
	}, {
		name : 'unloadCreator',
		type : 'String'
	},{
		name : 'unloadCreatorCode',
		type : 'String'
	}, 
	{
		name : 'unloadTaskCreateTime',//卸车任务创建时间
		type: 'string',
    	convert: function(v){
			if(Ext.isNumber(v)){
				var date = new Date(v);
				return Ext.Date.format(date,'Y-m-d H:i:s');
			}
			if(Ext.isDate(v)){
				return Ext.Date.format(v,'Y-m-d H:i:s');
			}
			return v;
		}

	},
	{
		name : 'totalTicks',
		type : 'number'
	}, {
		name : 'totalPieces',
		type : 'number'
	}, {
		name : 'bindPieces',
		type : 'number'
	}, {
		name : 'bindRate',
		type : 'string'
	}, {
		name : 'scanPieces',
		type : 'number'
	}, {
		name : 'unLoadScanPieces',
		type : 'number'
	},{
		name : 'vehicleNo',
		type : 'string'
	}
	, {
		name : 'forkliftTicks',
		type : 'number'
	}]
	
});
///**-----------------------------------------------QueryTrayScanStore-----------------------------------------------------*/
////定义托盘扫描列表store
Ext.define('Foss.unload.unloadtaskbindtrayDetail.QueryUnloadBindTrayStore', {
	extend : 'Ext.data.Store',
	//pageSize : 20,
	autoLoad: false,
	//绑定托盘扫描模型
	model : 'Foss.unload.unloadtaskbindtrayDetail.QueryUnloadbindTrayModel',
	// 定义一个代理对象
	proxy : {
		type : 'ajax',
		timeout: 600000,
		actionMethods:'POST',
		// 请求的url
		url : backupquery.realPath('queryUnloadbindTrayList.action'),
		// 定义一个读取器
		reader : {
			// 以JSON的方式读取
			type : 'json',
			// 定义读取JSON数据的根对象
			root : 'unloadbindTrayVo.unloadbindTrayList',
			successProperty: 'success'
			
		}
		
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	},
	listeners : {
		'beforeload' : function(store, operation, eOpts){
			var queryParams = backupquery.unloadtaskbindtrayDetail.unloadbindTrayQueryForm.getForm().getValues();
			Ext.apply(operation, {
				params : {
					'unloadbindTrayVo.unloadbindTrayQueryConditionDto.unloadTaskNo' :backupquery.traybindmanager.unloadTaskNo
				}
			});	
		}
	}
});


///**-----------------------------------------------卸车任务托盘绑定明细grid-----------------------------------------------------*/
//定义托盘扫描查询结果详细列表
Ext.define('Foss.unload.unloadtaskbindtrayDetail.unloadBindTrayDetailModel',{
	extend : 'Ext.data.Model',
	fields : [ {
		name : 'taskNo',//托盘任务编号
		type : 'string'
	},{
		name : 'waybillNo',//运单号
		type : 'string'
	},{
		name : 'bindUserName',//绑定人姓名
		type : 'string'
	},{
		name : 'bindUserCode',//绑定人工号
		type : 'string'
	},{
		name : 'bindTraytaskTime',//绑定托盘任务时间
		type: 'string',
    	convert: function(v){
			if(Ext.isNumber(v)){
				var date = new Date(v);
				return Ext.Date.format(date,'Y-m-d H:i:s');
			}
			if(Ext.isDate(v)){
				return Ext.Date.format(v,'Y-m-d H:i:s');
			}
			return v;
		}

	},{
		name : 'forkliftDriverName',//叉车司机姓名
		type : 'string'
	}
	, {
		name : 'forkliftDriverCode',//叉车司机工号
		type : 'string'
	},  
	 {
		name : 'trayscanTime',//叉车扫描时间
		type: 'string',
    	convert: function(v){
			if(Ext.isNumber(v)){
				var date = new Date(v);
				return Ext.Date.format(date,'Y-m-d H:i:s');
			}
			if(Ext.isDate(v)){
				return Ext.Date.format(v,'Y-m-d H:i:s');
			}
			return v;
		}
	},
 ]
	
});

//定义托盘扫描列表store
Ext.define('Foss.unload.unloadtaskbindtrayDetail.QueryUnloadBindTrayDetailStore', {
	extend : 'Ext.data.Store',
	pageSize : 20,
	autoLoad: false,
	//绑定托盘扫描模型
	model : 'Foss.unload.unloadtaskbindtrayDetail.unloadBindTrayDetailModel',
	// 定义一个代理对象
	proxy : {
		type : 'ajax',
		timeout: 600000,
		actionMethods:'POST',
		// 请求的url
		url : backupquery.realPath('queryUnloadbindTrayDetailByUnloadTaskNoExpress.action'),
		// 定义一个读取器
		reader : {
			// 以JSON的方式读取
			type : 'json',
			// 定义读取JSON数据的根对象
			root : 'unloadbindTrayVo.unloadBindTrayDetailList',
			successProperty: 'success',
			totalProperty : 'totalCount'
		}
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	},
	listeners : {
		'beforeload' : function(store, operation, eOpts){
			//var queryParams = unload.unloadtaskbindtrayDetail.TrayScanQueryForm.getForm().getValues();
			Ext.apply(operation, {
				params : {
					'unloadbindTrayVo.unloadbindTrayQueryConditionDto.unloadTaskNo' :backupquery.traybindmanager.unloadTaskNo
				}
			});	
		}
	}
});


/**-----------------------------------------------WaybillDetailModel-----------------------------------------------------*/
//定义运单明细model
Ext.define('Foss.unload.unloadtaskbindtrayDetail.WaybillDetailModel',{
	extend : 'Ext.data.Model',
	fields : [{
		name : 'waybillNo',
		type : 'string'
	}, {
		name : 'serialNo',
		type : 'string'
	}, {
		name : 'destDeptName',
		type : 'string'
	}, {
		name : 'destDeptCode',
		type : 'string'
	}]
	
});
/**-----------------------------------------------WaybillDetailStore-----------------------------------------------------*/
//定义运单明细store
Ext.define('Foss.unload.unloadtaskbindtrayDetail.WaybillDetailStore',{
	extend : 'Ext.data.Store',
	model : 'Foss.unload.unloadtaskbindtrayDetail.WaybillDetailModel',
	proxy : {
		type : 'ajax',
		actionMethods : 'POST',
		url : backupquery.realPath('queryTrayScanDetailByTaskNoAndWaybillNo.action'),
		reader : {
			type : 'json',
			root : 'trayScanVo.serialNoList',
			successPorperty : 'success'
		}
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
});
/**-----------------------------------------------WaybillDetailGrid-----------------------------------------------------*/
Ext.define('Foss.unload.unloadtaskbindtrayDetail.WaybillDetailGrid',{
	extend : 'Ext.grid.Panel',
	columnLines : true,
	frame : true,
	//baseCls: '',
	atuoScoll : true,
	width : 400,
	store : Ext.create('Foss.unload.unloadtaskbindtrayDetail.WaybillDetailStore'),
	columns : [{
		dataIndex : 'waybillNo',
		align : 'center',
		width : 120,
		xtype : 'ellipsiscolumn',
		text : backupquery.unloadtaskbindtrayDetail.i18n('Foss.unload.unloadtaskbindtrayDetail.queryTrayScanGird.waybilNo')/*'运单号'*/
	}, {
		dataIndex : 'serialNo',
		align : 'center',
		width : 120,
		xtype : 'ellipsiscolumn',
		text : backupquery.unloadtaskbindtrayDetail.i18n('Foss.unload.unloadtaskbindtrayDetail.waybillDetailGrid.serialNo')/*'流水号'*/
	}],
	bindData : function(record){
		var taskNo = record.get('taskNo');
		var waybillNo = record.get('waybillNo');
		var recordsMap = this.store.load({
			params : {
				'unloadbindTrayVo.taskNo' : taskNo,
				'unloadbindTrayVo.waybillNo' : waybillNo
			}
		});
	}
	
});

/**-----------------------------------------------TrayScanDetailGird-----------------------------------------------------*/
//定义托盘扫描明细列表
Ext.define('Foss.unload.unloadtaskbindtrayDetail.TrayScanDetailGird',{
	extend : 'Ext.grid.Panel',
	frame : true,
	title : backupquery.unloadtaskbindtrayDetail.i18n('Foss.unload.unloadtaskbindtrayDetail.QueryTrayScanGird.title'),/*'托盘绑定明细列表'*/
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	emptyText : backupquery.unloadtaskbindtrayDetail.i18n('foss.unload.unloadtaskbindtrayDetail.unloadBindTrayGird.emptyText'),/*'查询结果为空'*/
	autoScroll : true,//显示滚动条
	collapsible : true,
	animCollapse : true,
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.unload.unloadtaskbindtrayDetail.QueryUnloadBindTrayDetailStore');
		/*me.plugins = [{
			header : true,
			ptype : 'rowexpander',
			rowsExpander : false,
			rowBodyElement : 'Foss.unload.unloadtaskbindtrayDetail.WaybillDetailGrid'
		}],*/
		me.tbar = [{
			xtype : 'button',
			text : backupquery.unloadtaskbindtrayDetail.i18n('foss.unload.unloadtaskbindtrayDetail.ExportButtonText'),/*'导出'*/
			handler : function(){
				var form = backupquery.unloadtaskbindtrayDetail.QueryForm.getForm();
				var unloadTaskNo = form.getValues().unloadTaskNo;
				if(!Ext.fly('downloadAttachFileForm')){
					var frm = document.createElement('form');
					frm.id = 'downloadAttachFileForm',
					frm.style.display = 'none',
					document.body.appendChild(frm)
				}
				
				Ext.Ajax.request({
					url : backupquery.realPath('exportUnloadTaskbindTrayDetailExcelExpress.action'),
					method : 'POST',
					form: Ext.fly('downloadAttachFileForm'),
					params : {
							'unloadbindTrayVo.unloadbindTrayQueryConditionDto.unloadTaskNo' :unloadTaskNo
					},
					isUpload: true,
					success:function(response){
						Ext.ux.Toast.msg("提示","导出失败", 'ok', 1000);   //提示导出失败
					},
					exception : function(response) {
						var json = Ext.decode(response.responseText);
	    				Ext.Msg.alert("提示",json.message);
					}
			  });
				
			
		 }
		}],
		me.bbar = Ext.create('Deppon.StandardPaging',{
			store : me.store,
			pageSize : 20,
			maximumSize : 100,
			plugins : Ext.create('Deppon.ux.PageSizePlugin', {
				sizeList : [['20', 20], ['30', 30], ['50', 50], ['100', 100]]
			})
		});
	  
	   backupquery.unloadtaskbindtrayDetail.pagingBar = me.bbar;
	   me.callParent([cfg]);
	},
	dockedItems : [{
	    xtype: 'toolbar',
	    dock: 'bottom',
	    layout : 'column',
	    id:'Foss_backupquery_unloadtaskbindtrayDetail_toolbar',
	    name: 'Foss_backupquery_unloadtaskbindtrayDetail_toolbar',
	    defaults: {
			xtype : 'textfield',
			readOnly : true,
			labelWidth : 120
		},
		items: [{
			fieldLabel : backupquery.unloadtaskbindtrayDetail.i18n('foss.unload.unloadtaskbindtrayDetail.unloadBindTrayGird.totalPieces')/*'总件数'*/,
			xtype : 'textfield',
			readOnly : true,
			columnWidth : 1/5,
			value : 0,
			id : 'Foss_backupquery_unloadtaskbindtrayDetail_totalPieces'
			},
			{
				fieldLabel : backupquery.unloadtaskbindtrayDetail.i18n('foss.unload.unloadtaskbindtrayDetail.unloadBindTrayGird.bindPieces')/*'绑定件数'*/,
				xtype : 'textfield',
				readOnly : true,
				columnWidth : 1/5,
				value : 0,
				id : 'Foss_backupquery_unloadtaskbindtrayDetail_bindPieces'
			},
			{
					fieldLabel : backupquery.unloadtaskbindtrayDetail.i18n('foss.unload.unloadtaskbindtrayDetail.unloadBindTrayGird.scanPieces')/*'叉车司机扫描件数'*/,
					xtype : 'textfield',
					readOnly : true,
					columnWidth : 1/5,
					value : 0,
					id : 'Foss_backupquery_unloadtaskbindtrayDetail_scanPieces'
			},
			{
				fieldLabel : backupquery.unloadtaskbindtrayDetail.i18n('foss.unload.unloadtaskbindtrayDetail.unloadBindTrayGird.bindRate')/*'托盘绑定率'*/,
				xtype : 'textfield',
				readOnly : true,
				columnWidth : 1/5,
				value : 0,
				id : 'Foss_backupquery_unloadtaskbindtrayDetail_bindRate'
			}
			
		]
		}],
	columns : [{
		dataIndex : 'taskNo',
		align : 'center',
		width : 220,
		xtype : 'ellipsiscolumn',
		text : backupquery.unloadtaskbindtrayDetail.i18n('Foss.unload.unloadtaskbindtrayDetail.queryTrayScanGird.taskNo')/*'托盘任务编号'*/
	},{
		dataIndex : 'waybillNo',
		align : 'center',
		width : 130,
		xtype : 'ellipsiscolumn',
		text : backupquery.unloadtaskbindtrayDetail.i18n('Foss.unload.unloadtaskbindtrayDetail.queryTrayScanGird.waybilNo')/*'运单号'*/
	}, {
		dataIndex : 'bindUserName',
		align : 'center',
		width : 90,
		xtype : 'ellipsiscolumn',
		text : backupquery.unloadtaskbindtrayDetail.i18n('Foss.unload.unloadtaskbindtrayDetail.queryTrayScanGird.bindUserName')/*绑定人姓名'*/
	}, {
		dataIndex : 'bindUserCode',
		align : 'center',
		width : 90,
		xtype : 'ellipsiscolumn',
		text : backupquery.unloadtaskbindtrayDetail.i18n('Foss.unload.unloadtaskbindtrayDetail.queryTrayScanGird.bindUserCode')/*'绑定人工号'*/
	}, {
		dataIndex : 'bindTraytaskTime',
		align : 'center',
		width : 130,
		//xtype : 'datecolumn',
		//format : 'Y-m-d H:i:s',
		//renderer:Ext.util.Format.dateRenderer('Y-m-d H:i:s'),
		text : backupquery.unloadtaskbindtrayDetail.i18n('Foss.unload.unloadtaskbindtrayDetail.queryTrayScanGird.bindTraytaskTime')/*'绑定托盘任务时间'*/
	},{
		dataIndex : 'forkliftDriverName',
		align : 'center',
		width : 90,
		xtype : 'ellipsiscolumn',
		text : backupquery.unloadtaskbindtrayDetail.i18n('Foss.unload.unloadtaskbindtrayDetail.queryTrayScanGird.forkliftDriverName')/*'叉车司机姓名'*/
	}, {
		dataIndex : 'forkliftDriverCode',
		align : 'center',
		width : 90,
		xtype : 'ellipsiscolumn',
		text : backupquery.unloadtaskbindtrayDetail.i18n('Foss.unload.unloadtaskbindtrayDetail.queryTrayScanGird.forkliftDriverCode')/*'叉车司机工号'*/
	}, {
		dataIndex : 'trayscanTime',
		align : 'center',
		width : 130,
		//xtype : 'datecolumn',
		//format : 'Y-m-d H:i:s',
		//renderer:Ext.util.Format.dateRenderer('Y-m-d H:i:s'),
		text : backupquery.unloadtaskbindtrayDetail.i18n('Foss.unload.unloadtaskbindtrayDetail.queryTrayScanGird.trayscanTime')/*'叉车扫描时间'*/
	}

	]
		
});


///**-----------------------------------------------onReady-----------------------------------------------------*/
//定义叉车工作量查询结果列表
backupquery.unloadtaskbindtrayDetail.unloadBindTrayGird = Ext.create('Foss.unload.unloadtaskbindtrayDetail.TrayScanDetailGird');
backupquery.unloadtaskbindtrayDetail.QueryForm = Ext.create('Foss.unload.unloadtaskbindtrayDetail.QueryForm');
Ext.onReady(function() {
	Ext.QuickTips.init();
	var recordForm = backupquery.unloadtaskbindtrayDetail.QueryForm.getForm();
	//卸车任务编号
	recordForm.findField('unloadTaskNo').setValue(backupquery.traybindmanager.unloadTaskNo);
	//卸车创建人
	recordForm.findField('unloadCreator').setValue(backupquery.traybindmanager.createrName);
	//创建卸车任务时间
	recordForm.findField('unloadTaskCreateTime').setValue(backupquery.traybindmanager.createTime);
	//已扫描总件数
	Ext.getCmp('Foss_backupquery_unloadtaskbindtrayDetail_totalPieces').setValue(backupquery.traybindmanager.unLoadScanPieces);
	//已绑定件数
	Ext.getCmp('Foss_backupquery_unloadtaskbindtrayDetail_bindPieces').setValue(backupquery.traybindmanager.bindPieces);
	//叉车扫描件数
	Ext.getCmp('Foss_backupquery_unloadtaskbindtrayDetail_scanPieces').setValue(backupquery.traybindmanager.scanPieces);
	//托盘绑定率
	Ext.getCmp('Foss_backupquery_unloadtaskbindtrayDetail_bindRate').setValue(backupquery.traybindmanager.bindRate+"%");
	
	backupquery.unloadtaskbindtrayDetail.pagingBar.moveFirst();
	
	Ext.create('Ext.panel.Panel',{
		id : 'T_backupquery-unloadtaskbindtrayDetailindex_content',
		cls : "panelContentNToolbar",
		bodyCls : 'panelContentNToolbar-body',
		layout : 'auto',
		items : [ backupquery.unloadtaskbindtrayDetail.QueryForm,backupquery.unloadtaskbindtrayDetail.unloadBindTrayGird],
		renderTo : 'T_backupquery-unloadtaskbindtrayDetailindex-body'
			
	});
});