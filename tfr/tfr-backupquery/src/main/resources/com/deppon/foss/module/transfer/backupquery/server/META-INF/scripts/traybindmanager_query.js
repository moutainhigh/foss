//定义方法，生产查询条件"托盘扫描时间"的起始时间和结束时间
backupquery.traybindmanager.getTrayScanTime4QueryForm=function(isBegin){
	var nowDate = new Date();
	if(isBegin){
		nowDate.setHours(0);
		nowDate.setMinutes(0);
		nowDate.setSeconds(0);
	}else{
		nowDate.setHours(23);
		nowDate.setMinutes(59);
		nowDate.setSeconds(59);
	}
	return nowDate;
}
/**-----------------------------------------------queryForm-----------------------------------------------------*/
//定义托盘扫描查询条件form 零担
Ext.define('Foss.unload.traybindmanager.QueryForm', {
	extend : 'Ext.form.Panel',
	title : backupquery.traybindmanager.i18n('foss.unload.traybindmanager.queryForm.title'),//查询条件
	frame : true,
	collapsible : true,//允许展开收缩
	animCollapse : true,//显示动画效果
	defaults : {
		margin : '5 10 5 10',
		labelWidth : 85,
		columnWidth : 1/ 4,
		xtype : 'textfield'
	},
	layout : 'column',
	items : [ {
		fieldLabel : backupquery.traybindmanager.i18n('foss.unload.traybindmanager.queryForm.unloadTaskNo'),//卸车任务号
		name : 'unloadTaskNo',
		maxLength : 30
	},{
		fieldLabel : backupquery.traybindmanager.i18n('foss.unload.traybindmanager.unloadBindTrayGird.unloadCreator'),//卸车创建人
		name : 'unloadCreatorCode',
		xtype:'commonemployeeselector'
		
	}, {
		fieldLabel : backupquery.traybindmanager.i18n('foss.unload.traybindmanager.queryForm.vehicleNo'),//车牌号
		name : 'vehicleNo',
		xtype:'commontruckselector'
		
	}, {
		xtype : 'rangeDateField',
		fieldLabel : backupquery.traybindmanager.i18n('foss.unload.traybindmanager.queryForm.unloadTaskCreateTime'),//任务建立时间
		columnWidth : 1/2,
		fieldId : 'Foss_stock_QueryForm_traybindmanagerTime_ID',
		dateType: 'datetimefield_date97',
		dateRange : 7,
		fromName : 'beginCreateTime',
		fromValue : Ext.Date.format(backupquery.traybindmanager.getTrayScanTime4QueryForm(true), 'Y-m-d H:i:s'),
		toValue : Ext.Date.format(backupquery.traybindmanager.getTrayScanTime4QueryForm(false), 'Y-m-d H:i:s'),
		toName : 'endCreateTime',
		allowBlank : false,
		disallowBlank : true
	}, {
		border : false,
		xtype : 'container',
		columnWidth : 1,
		layout : 'column',
		defaults : {
			margin : '5 0 5 0'
		},
	items : [{
			xtype : 'button',
			columnWidth:.08,
			text : backupquery.traybindmanager.i18n('foss.unload.traybindmanager.queryForm.resetButtonText'),//'重置'
			handler : function(){
				this.up('form').getForm().reset();
				this.up('form').getForm().findField('beginCreateTime').setValue(Ext.Date.format(backupquery.traybindmanager.getTrayScanTime4QueryForm(true),'Y-m-d H:i:s'));
				this.up('form').getForm().findField('endCreateTime').setValue(Ext.Date.format(backupquery.traybindmanager.getTrayScanTime4QueryForm(false),'Y-m-d H:i:s'));	
			}
		}, {
			border : false,
			columnWidth : .84,
			html : '&nbsp'
		}, {
			columnWidth : .08,
			xtype : 'button',
			cls : 'yellow_button',
			disabled:!backupquery.traybindmanager.isPermission('unload/queryTrayBindManagerButton'),
			hidden:!backupquery.traybindmanager.isPermission('unload/queryTrayBindManagerButton'),
			text : backupquery.traybindmanager.i18n('foss.unload.traybindmanager.queryButtonText'),//'查询'
			handler : function(opts){
				var grid=backupquery.traybindmanager.unloadBindTrayGird;
				var form = this.up('form').getForm();
				if(!form.isValid()){
					return;
				}
				
				var store=grid.getStore( );
				//清楚掉之前的数据
				store.removeAll(false);
				//重新加载数据
			    backupquery.traybindmanager.pagingBar.moveFirst();	
			}
		}]
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
});

//定义托盘扫描查询条件form  快递
Ext.define('Foss.unload.traybindmanager.QueryFormExpress', {
	extend : 'Ext.form.Panel',
	title : backupquery.traybindmanager.i18n('foss.unload.traybindmanager.queryForm.title'),//查询条件
	frame : true,
	collapsible : true,//允许展开收缩
	animCollapse : true,//显示动画效果
	defaults : {
		margin : '5 10 5 10',
		labelWidth : 85,
		columnWidth : 1/ 4,
		xtype : 'textfield'
	},
	layout : 'column',
	items : [ {
		fieldLabel : backupquery.traybindmanager.i18n('foss.unload.traybindmanager.queryForm.unloadTaskNo'),//卸车任务号
		name : 'unloadTaskNo',
		maxLength : 30
	},{
		fieldLabel : backupquery.traybindmanager.i18n('foss.unload.traybindmanager.unloadBindTrayGird.unloadCreator'),//卸车创建人
		name : 'unloadCreatorCode',
		xtype:'commonemployeeselector'
		
	}, {
		fieldLabel : backupquery.traybindmanager.i18n('foss.unload.traybindmanager.queryForm.vehicleNo'),//车牌号
		name : 'vehicleNo',
		xtype:'commontruckselector'
		
	}, {
		xtype : 'rangeDateField',
		fieldLabel : backupquery.traybindmanager.i18n('foss.unload.traybindmanager.queryForm.unloadTaskCreateTime'),//任务建立时间
		columnWidth : 1/2,
		fieldId : 'Foss_stock_QueryForm_traybindmanagerTime_ID_Express',
		dateType: 'datetimefield_date97',
		dateRange : 7,
		fromName : 'beginCreateTime',
		fromValue : Ext.Date.format(backupquery.traybindmanager.getTrayScanTime4QueryForm(true), 'Y-m-d H:i:s'),
		toValue : Ext.Date.format(backupquery.traybindmanager.getTrayScanTime4QueryForm(false), 'Y-m-d H:i:s'),
		toName : 'endCreateTime',
		allowBlank : false,
		disallowBlank : true
	}, {
		border : false,
		xtype : 'container',
		columnWidth : 1,
		layout : 'column',
		defaults : {
			margin : '5 0 5 0'
		},
	items : [{
			xtype : 'button',
			columnWidth:.08,
			text : backupquery.traybindmanager.i18n('foss.unload.traybindmanager.queryForm.resetButtonText'),//'重置'
			handler : function(){
				this.up('form').getForm().reset();
				this.up('form').getForm().findField('beginCreateTime').setValue(Ext.Date.format(backupquery.traybindmanager.getTrayScanTime4QueryForm(true),'Y-m-d H:i:s'));
				this.up('form').getForm().findField('endCreateTime').setValue(Ext.Date.format(backupquery.traybindmanager.getTrayScanTime4QueryForm(false),'Y-m-d H:i:s'));	
			}
		}, {
			border : false,
			columnWidth : .84,
			html : '&nbsp'
		}, {
			columnWidth : .08,
			xtype : 'button',
			cls : 'yellow_button',
			disabled:!backupquery.traybindmanager.isPermission('unload/queryTrayBindManagerButton'),
			hidden:!backupquery.traybindmanager.isPermission('unload/queryTrayBindManagerButton'),
			text : backupquery.traybindmanager.i18n('foss.unload.traybindmanager.queryButtonText'),//'查询'
			handler : function(opts){
				var grid=backupquery.traybindmanager.unloadBindTrayGirdExpress;
				var form = this.up('form').getForm();
				if(!form.isValid()){
					return;
				}
				
				var store=grid.getStore( );
				//清楚掉之前的数据
				store.removeAll(false);
				//重新加载数据
				backupquery.traybindmanager.pagingBarExpress.moveFirst();	
			}
		}]
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
});
/**-----------------------------------------------QueryUnloadbindTrayModel-----------------------------------------------------*/
//定义卸车任务绑定托盘查询结果详细列表  零担
Ext.define('Foss.unload.traybindmanager.QueryUnloadbindTrayModel',{
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
		type : 'date',
		convert : dateConvert
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
	},{
		name : 'forkliftTicks',
		type : 'number'
	},{
		name : 'unLoadScanPieces',
		type : 'number'
	},{	
		name : 'trayTaskTotal',
		type : 'number'
	},{
		name : 'scannedTotal',
		type : 'number'
		}]
	
});

//定义卸车任务绑定托盘查询结果详细列表  快递
Ext.define('Foss.unload.traybindmanager.QueryUnloadbindTrayModelExpress',{
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
	},{
		name : 'unloadTaskCreateTime',//卸车任务创建时间
		type : 'String'
	},{
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
	},{
		name : 'forkliftTicks',
		type : 'number'
	},{
		name : 'unLoadScanPieces',
		type : 'number'
	},{	
		name : 'trayTaskTotal',
		type : 'number'
	},{	
		name : 'totalTicks',
		type : 'number'
	},{
		name : 'scannedTotal',
		type : 'number'
		}]
	
});
///**-----------------------------------------------QueryTrayScanStore-----------------------------------------------------*/
////定义托盘扫描列表store 零担
Ext.define('Foss.unload.traybindmanager.QueryUnloadBindTrayStore', {
	extend : 'Ext.data.Store',
	pageSize : 20,
	autoLoad: false,
	//绑定托盘扫描模型
	model : 'Foss.unload.traybindmanager.QueryUnloadbindTrayModel',
	// 定义一个代理对象
	proxy : {
		type : 'ajax',
		timeout: 600000,
		actionMethods:'POST',
		// 请求的url
		url : backupquery.realPath('queryUnloadTaskbindTrayList.action'),
		// 定义一个读取器
		reader : {
			// 以JSON的方式读取
			type : 'json',
			// 定义读取JSON数据的根对象
			root : 'unloadbindTrayVo.unloadbindTrayList',
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
			var queryParams = backupquery.traybindmanager.unloadbindTrayQueryForm.getForm().getValues();
			Ext.apply(operation, {
				params : {
					'unloadbindTrayVo.unloadbindTrayQueryConditionDto.unloadTaskNo' :queryParams.unloadTaskNo,
					'unloadbindTrayVo.unloadbindTrayQueryConditionDto.createrCode' :queryParams.unloadCreatorCode,
					'unloadbindTrayVo.unloadbindTrayQueryConditionDto.vehicleNo' :queryParams.vehicleNo,
					'unloadbindTrayVo.unloadbindTrayQueryConditionDto.createStartDate' :queryParams.beginCreateTime,
					'unloadbindTrayVo.unloadbindTrayQueryConditionDto.createEndDate' : queryParams.endCreateTime
				}
			});	
		}
	}
});

////定义托盘扫描列表store 快递
Ext.define('Foss.unload.traybindmanager.QueryUnloadBindTrayStoreExpress', {
	extend : 'Ext.data.Store',
	pageSize : 20,
	autoLoad: false,
	//绑定托盘扫描模型
	model : 'Foss.unload.traybindmanager.QueryUnloadbindTrayModelExpress',
	// 定义一个代理对象
	proxy : {
		type : 'ajax',
		timeout: 600000,
		actionMethods:'POST',
		// 请求的url
		url : backupquery.realPath('queryUnloadTaskbindTrayListExpress.action'),
		// 定义一个读取器
		reader : {
			// 以JSON的方式读取
			type : 'json',
			// 定义读取JSON数据的根对象
			root : 'unloadbindTrayVo.unloadbindTrayListExpress',
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
			var queryParams = backupquery.traybindmanager.unloadbindTrayQueryFormExpress.getForm().getValues();
			Ext.apply(operation, {
				params : {
					'unloadbindTrayVo.unloadbindTrayQueryConditionDto.unloadTaskNo' :queryParams.unloadTaskNo,
					'unloadbindTrayVo.unloadbindTrayQueryConditionDto.createrCode' :queryParams.unloadCreatorCode,
					'unloadbindTrayVo.unloadbindTrayQueryConditionDto.vehicleNo' :queryParams.vehicleNo,
					'unloadbindTrayVo.unloadbindTrayQueryConditionDto.createStartDate' :queryParams.beginCreateTime,
					'unloadbindTrayVo.unloadbindTrayQueryConditionDto.createEndDate' : queryParams.endCreateTime
				}
			});	
		}
	}
});

/**-----------------------------------------------WaybillDetailGrid-----------------------------------------------------*/

/**-----------------------------------------------UnloadBindTrayGird-----------------------------------------------------*/
//定义托盘绑定Gird 零担
Ext.define('Foss.unload.traybindmanager.unloadBindTrayGird',{
	extend : 'Ext.grid.Panel',
	frame : true,
	title : backupquery.traybindmanager.i18n('foss.unload.traybindmanager.unloadBindTrayGird.title'),/*'卸车任务托盘绑定明细'*/
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	emptyText : backupquery.traybindmanager.i18n('foss.unload.traybindmanager.unloadBindTrayGird.emptyText'),/*'查询结果为空'*/
	autoScroll : true,//显示滚动条
	collapsible : true,
	animCollapse : true,
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.unload.traybindmanager.QueryUnloadBindTrayStore');
		me.tbar = [{
			xtype : 'button',
			disabled:!backupquery.traybindmanager.isPermission('unload/exportTrayBindManagerButton'),
			hidden:!backupquery.traybindmanager.isPermission('unload/exportTrayBindManagerButton'),
			text : backupquery.traybindmanager.i18n('foss.unload.traybindmanager.ExportButtonText'),/*'导出'*/
			handler : function(){
				if(!Ext.fly('downloadAttachFileForm')){
					var frm = document.createElement('form');
					frm.id = 'downloadAttachFileForm',
					frm.style.display = 'none',
					document.body.appendChild(frm)
				}
				var form = backupquery.traybindmanager.unloadbindTrayQueryForm.getForm();
				var queryParams = form.getValues();
				if(!form.isValid()){
					Ext.ux.Toast.msg('提示','请输入合法的交接时间！','error', 2000);
					return;
				}
				Ext.Ajax.request({
					url : backupquery.realPath('exportUnloadTaskbindTrayExcel.action'),
					form: Ext.fly('downloadAttachFileForm'),
					method : 'POST',
					params : {
						'unloadbindTrayVo.unloadbindTrayQueryConditionDto.unloadTaskNo' :queryParams.unloadTaskNo,
						'unloadbindTrayVo.unloadbindTrayQueryConditionDto.createrCode' :queryParams.unloadCreatorCode,
						'unloadbindTrayVo.unloadbindTrayQueryConditionDto.vehicleNo' :queryParams.vehicleNo,
						'unloadbindTrayVo.unloadbindTrayQueryConditionDto.createStartDate' :queryParams.beginCreateTime,
						'unloadbindTrayVo.unloadbindTrayQueryConditionDto.createEndDate' : queryParams.endCreateTime
					},
					isUpload: true,
					success:function(response){
					
					},
					exception : function(response) {
						top.Ext.MessageBox.alert(backupquery.traybindmanager.i18n('foss.unload.handoverbillshow.waybillGrid.expertFailureAlertInfo')/*'导出失败'*/,result.message);
					}
			});
			}
		}],
		me.bbar = Ext.create('Deppon.StandardPaging',{
			store : me.store,
			pageSize : 20,
			maximumSize : 50,
			plugins : Ext.create('Deppon.ux.PageSizePlugin', {
				sizeList : [['20', 20], ['30', 30], ['50', 50], ['100', 100]]
			})
		});
	   backupquery.traybindmanager.pagingBar = me.bbar;
	   me.callParent([cfg]);
	},
	columns : [
	 {

	   xtype : 'actioncolumn',
	   width : 40,
	   text : '操作',
	   align : 'center',
	   items : [ {
		   iconCls : 'deppon_icons_showdetail',
		   handler : function(grid, rowIndex, colIndex, item, e) {
		   var unloadTaskNo = grid.store.getAt(rowIndex).get('unloadTaskNo');//卸车任务
		   var createrName=grid.store.getAt(rowIndex).get('unloadCreator');//卸车任务创建人
		   var createTime=grid.store.getAt(rowIndex).get('unloadTaskCreateTime');//卸车任务创建时间
		   backupquery.addTab('T_backupquery-unloadtaskbindtrayDetailindex',//对应打开的目标页面js的onReady里定义的renderTo
		   			backupquery.traybindmanager.i18n('foss.unload.traybindmanager.unloadTaskTraybindDetail'),
		   			backupquery.realPath('unloadtaskbindtrayDetailindex.action') + '?unloadTaskNo="' + unloadTaskNo + '"');//对应的页面URL，可以在url后使用?x=123这种形式传参
		   
	    }
	   
	   }]
	 },        
	{
		dataIndex : 'unloadTaskNo',
		align : 'center',
		width : 150,
		xtype : 'ellipsiscolumn',
		text : backupquery.traybindmanager.i18n('foss.unload.traybindmanager.queryTrayScanGird.unloadTaskNo')/*'卸车任务编号'*/
	}, {
		dataIndex : 'unloadCreator',
		align : 'center',
		width : 90,
		xtype : 'ellipsiscolumn',
		text : backupquery.traybindmanager.i18n('foss.unload.traybindmanager.unloadBindTrayGird.unloadCreator')/*'卸车创建人'*/
	}, {
		dataIndex : 'unloadCreatorCode',
		align : 'center',
		width : 90,
		xtype : 'ellipsiscolumn',
		text : backupquery.traybindmanager.i18n('foss.unload.traybindmanager.unloadBindTrayGird.unloadCreatorCode')/*'卸车创建人工号'*/
	}, {
		dataIndex : 'totalTicks',
		align : 'center',
		width : 90,
		xtype : 'ellipsiscolumn',
		text : backupquery.traybindmanager.i18n('foss.unload.traybindmanager.unloadBindTrayGird.totalTicks')/*'总票数'*/
	}, {
		dataIndex : 'totalPieces',
		align : 'center',
		width : 90,
		xtype : 'ellipsiscolumn',
		text : backupquery.traybindmanager.i18n('foss.unload.traybindmanager.unloadBindTrayGird.totalPieces')/*'总件数'*/
	}, {
		dataIndex : 'unLoadScanPieces',
		align : 'center',
		width : 90,
		xtype : 'ellipsiscolumn',
		text : backupquery.traybindmanager.i18n('foss.unload.traybindmanager.unloadBindTrayGird.unLoadScanPieces')/*'已卸车件数'*/
	}, {
		dataIndex : 'bindPieces',
		align : 'center',
		width : 90,
		xtype : 'ellipsiscolumn',
		text : backupquery.traybindmanager.i18n('foss.unload.traybindmanager.unloadBindTrayGird.bindPieces')/*'已绑定件数'*/
	}, {
		dataIndex : 'bindRate',
		align : 'center',
		width : 90,
		xtype : 'ellipsiscolumn',
		text : backupquery.traybindmanager.i18n('foss.unload.traybindmanager.unloadBindTrayGird.bindRate')/*'托盘绑定率'*/
	}, {
		dataIndex : 'trayTaskTotal',
		align : 'center',
		width : 90,
		xtype : 'ellipsiscolumn',
		text : backupquery.traybindmanager.i18n('foss.unload.traybindmanager.unloadBindTrayGird.trayTaskTotal')/*'绑定任务数'*/
	}, {
		dataIndex : 'scannedTotal',
		align : 'center',
		width : 90,
		xtype : 'ellipsiscolumn',
		text : backupquery.traybindmanager.i18n('foss.unload.traybindmanager.unloadBindTrayGird.scannedTotal')/*'扫描任务数'*/
	}, {
		dataIndex : 'scanPieces',
		align : 'center',
		width : 130,
		xtype : 'ellipsiscolumn',
		text : backupquery.traybindmanager.i18n('foss.unload.traybindmanager.unloadBindTrayGird.scanPieces')/*'叉车扫描件数'*/
	}, {
		dataIndex : 'vehicleNo',
		align : 'center',
		width : 90,
		text : backupquery.traybindmanager.i18n('foss.unload.traybindmanager.unloadBindTrayGird.vehicleNo')/*'车牌号'*/
	}, {
		dataIndex : 'forkliftTicks',
		align : 'center',
		width : 90,
		xtype : 'ellipsiscolumn',
		text : backupquery.traybindmanager.i18n('foss.unload.traybindmanager.unloadBindTrayGird.forkliftTicks')/*'叉车票数'*/
	}
  ]
		
});

//定义托盘绑定Gird 快递
Ext.define('Foss.unload.traybindmanager.unloadBindTrayGirdExpress',{
	extend : 'Ext.grid.Panel',
	frame : true,
	title : backupquery.traybindmanager.i18n('foss.unload.traybindmanager.unloadBindTrayGird.title'),/*'卸车任务托盘绑定明细'*/
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	emptyText : backupquery.traybindmanager.i18n('foss.unload.traybindmanager.unloadBindTrayGird.emptyText'),/*'查询结果为空'*/
	autoScroll : true,//显示滚动条
	collapsible : true,
	animCollapse : true,
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.unload.traybindmanager.QueryUnloadBindTrayStoreExpress');
		me.tbar = [{
			xtype : 'button',
			disabled:!backupquery.traybindmanager.isPermission('unload/exportTrayBindManagerButton'),
			hidden:!backupquery.traybindmanager.isPermission('unload/exportTrayBindManagerButton'),
			text : backupquery.traybindmanager.i18n('foss.unload.traybindmanager.ExportButtonText'),/*'导出'*/
			handler : function(){
				if(!Ext.fly('downloadAttachFileForm')){
					var frm = document.createElement('form');
					frm.id = 'downloadAttachFileForm',
					frm.style.display = 'none',
					document.body.appendChild(frm)
				}
				var form = backupquery.traybindmanager.unloadbindTrayQueryFormExpress.getForm();
				var queryParams = form.getValues();
				if(!form.isValid()){
					Ext.ux.Toast.msg('提示','请输入合法的交接时间！','error', 2000);
					return;
				}
				Ext.Ajax.request({
					url : backupquery.realPath('exportUnloadTaskbindTrayExcelExpress.action'),
					form: Ext.fly('downloadAttachFileForm'),
					method : 'POST',
					params : {
						'unloadbindTrayVo.unloadbindTrayQueryConditionDto.unloadTaskNo' :queryParams.unloadTaskNo,
						'unloadbindTrayVo.unloadbindTrayQueryConditionDto.createrCode' :queryParams.unloadCreatorCode,
						'unloadbindTrayVo.unloadbindTrayQueryConditionDto.vehicleNo' :queryParams.vehicleNo,
						'unloadbindTrayVo.unloadbindTrayQueryConditionDto.createStartDate' :queryParams.beginCreateTime,
						'unloadbindTrayVo.unloadbindTrayQueryConditionDto.createEndDate' : queryParams.endCreateTime
					},
					isUpload: true,
					success:function(response){
					
					},
					exception : function(response) {
						top.Ext.MessageBox.alert(backupquery.traybindmanager.i18n('foss.unload.handoverbillshow.waybillGrid.expertFailureAlertInfo')/*'导出失败'*/,result.message);
					}
			});
			}
		}],
		me.bbar = Ext.create('Deppon.StandardPaging',{
			store : me.store,
			pageSize : 20,
			maximumSize : 50,
			plugins : Ext.create('Deppon.ux.PageSizePlugin', {
				sizeList : [['20', 20], ['30', 30], ['50', 50], ['100', 100]]
			})
		});
	   backupquery.traybindmanager.pagingBarExpress = me.bbar;
	   me.callParent([cfg]);
	},
	columns : [
	 {

	   xtype : 'actioncolumn',
	   width : 40,
	   text : '操作',
	   align : 'center',
	   items : [ {
		   iconCls : 'deppon_icons_showdetail',
		   handler : function(grid, rowIndex, colIndex, item, e) {
		   var unloadTaskNo = grid.store.getAt(rowIndex).get('unloadTaskNo');//卸车任务
		   var createrName=grid.store.getAt(rowIndex).get('unloadCreator');//卸车任务创建人
		   var createTime=grid.store.getAt(rowIndex).get('unloadTaskCreateTime');//卸车任务创建时间
		   var unLoadScanPieces=grid.store.getAt(rowIndex).get('unLoadScanPieces');//已卸车件数
		   var bindPieces=grid.store.getAt(rowIndex).get('bindPieces');//已绑定件数
		   var scanPieces=grid.store.getAt(rowIndex).get('scanPieces');//叉车扫描件数
		   var bindRate=grid.store.getAt(rowIndex).get('bindRate');//托盘绑定率
		   backupquery.addTab('T_backupquery-unloadtaskbindtrayDetailindex',//对应打开的目标页面js的onReady里定义的renderTo
		   			backupquery.traybindmanager.i18n('foss.unload.traybindmanager.unloadTaskTraybindDetail'),
		   			backupquery.realPath('unloadtaskbindtrayDetailindexExpress.action') + '?unloadTaskNo="' + unloadTaskNo + '"&createrName="'+encodeURI(encodeURI(createrName))+'"&createTime="'+createTime+'"&unLoadScanPieces="'+unLoadScanPieces+'"&bindPieces="'+bindPieces+'"&scanPieces="'+scanPieces+'"&bindRate="'+bindRate.substring(0,bindRate.length-1)+'"');//对应的页面URL，可以在url后使用?x=123这种形式传参
		   
	    }
	   
	   }]
	 },        
	{
		dataIndex : 'unloadTaskNo',
		align : 'center',
		width : 150,
		xtype : 'ellipsiscolumn',
		text : backupquery.traybindmanager.i18n('foss.unload.traybindmanager.queryTrayScanGird.unloadTaskNo')/*'卸车任务编号'*/
	}, {
		dataIndex : 'unloadCreator',
		align : 'center',
		width : 90,
		xtype : 'ellipsiscolumn',
		text : backupquery.traybindmanager.i18n('foss.unload.traybindmanager.unloadBindTrayGird.unloadCreator')/*'卸车创建人'*/
	}, {
		dataIndex : 'unloadCreatorCode',
		align : 'center',
		width : 90,
		xtype : 'ellipsiscolumn',
		text : backupquery.traybindmanager.i18n('foss.unload.traybindmanager.unloadBindTrayGird.unloadCreatorCode')/*'卸车创建人工号'*/
	}, {
		dataIndex : 'totalTicks',
		align : 'center',
		width : 90,
		xtype : 'ellipsiscolumn',
		text : backupquery.traybindmanager.i18n('foss.unload.traybindmanager.unloadBindTrayGird.totalTicks')/*'总票数'*/
	}, {
		dataIndex : 'unLoadScanPieces',
		align : 'center',
		width : 90,
		xtype : 'ellipsiscolumn',
		text : backupquery.traybindmanager.i18n('foss.unload.traybindmanager.unloadBindTrayGird.unLoadScanPieces')/*'已卸车件数'*/
	}, {
		dataIndex : 'bindPieces',
		align : 'center',
		width : 90,
		xtype : 'ellipsiscolumn',
		text : backupquery.traybindmanager.i18n('foss.unload.traybindmanager.unloadBindTrayGird.bindPieces')/*'已绑定件数'*/
	}, {
		dataIndex : 'bindRate',
		align : 'center',
		width : 90,
		xtype : 'ellipsiscolumn',
		text : backupquery.traybindmanager.i18n('foss.unload.traybindmanager.unloadBindTrayGird.bindRate')/*'托盘绑定率'*/
	}, {
		dataIndex : 'trayTaskTotal',
		align : 'center',
		width : 90,
		xtype : 'ellipsiscolumn',
		text : backupquery.traybindmanager.i18n('foss.unload.traybindmanager.unloadBindTrayGird.trayTaskTotal')/*'绑定任务数'*/
	}, {
		dataIndex : 'scannedTotal',
		align : 'center',
		width : 90,
		xtype : 'ellipsiscolumn',
		text : backupquery.traybindmanager.i18n('foss.unload.traybindmanager.unloadBindTrayGird.scannedTotal')/*'扫描任务数'*/
	}, {
		dataIndex : 'scanPieces',
		align : 'center',
		width : 130,
		xtype : 'ellipsiscolumn',
		text : backupquery.traybindmanager.i18n('foss.unload.traybindmanager.unloadBindTrayGird.scanPieces')/*'叉车扫描件数'*/
	}, {
		dataIndex : 'vehicleNo',
		align : 'center',
		width : 90,
		text : backupquery.traybindmanager.i18n('foss.unload.traybindmanager.unloadBindTrayGird.vehicleNo')/*'车牌号'*/
	}, {
		dataIndex : 'forkliftTicks',
		align : 'center',
		width : 90,
		xtype : 'ellipsiscolumn',
		text : backupquery.traybindmanager.i18n('foss.unload.traybindmanager.unloadBindTrayGird.forkliftTicks')/*'叉车票数'*/
	}
  ]
		
});

///**-----------------------------------------------onReady-----------------------------------------------------*/
//定义叉车工作量查询结果列表
backupquery.traybindmanager.unloadBindTrayGird = Ext.create('Foss.unload.traybindmanager.unloadBindTrayGird');
backupquery.traybindmanager.unloadbindTrayQueryForm = Ext.create('Foss.unload.traybindmanager.QueryForm');
backupquery.traybindmanager.unloadBindTrayGirdExpress = Ext.create('Foss.unload.traybindmanager.unloadBindTrayGirdExpress');
backupquery.traybindmanager.unloadbindTrayQueryFormExpress = Ext.create('Foss.unload.traybindmanager.QueryFormExpress');
Ext.onReady(function() {
	Ext.QuickTips.init();
	Ext.create('Ext.panel.Panel',{
		id : 'T_backupquery-traybindmanagerindex_content',
		cls : "panelContentNToolbar",
		bodyCls : 'panelContentNToolbar-body',
		layout : 'auto',
		items : [{
			xtype : 'tabpanel',
			frame : false,
			bodyCls : 'autoHeight',
			cls : 'innerTabPanel',
			activeTab : 0,
			items:[{
				//零担托盘绑定
				title : backupquery.traybindmanager.i18n('foss.unload.unloadtaskaddnew.personCount'),
				tabConfig : {
					width : 120
				},
				itemId : 'TemporaryAssignments',
				items : [backupquery.traybindmanager.unloadbindTrayQueryForm,backupquery.traybindmanager.unloadBindTrayGird]
		     }, {
		    	//快递托盘绑定
				title : backupquery.traybindmanager.i18n('foss.unload.unloadtaskaddnew.teamCountPanel'),
				tabConfig : {
					width : 120
				},
				itemId : 'TaskAssignments',
				items : [backupquery.traybindmanager.unloadbindTrayQueryFormExpress,backupquery.traybindmanager.unloadBindTrayGirdExpress]
			 }]
		}],
		renderTo : 'T_backupquery-traybindmanagerindex-body'
			
	});
});