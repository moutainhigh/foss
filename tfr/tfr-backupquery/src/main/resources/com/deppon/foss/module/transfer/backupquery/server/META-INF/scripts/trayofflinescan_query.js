//定义方法，生产查询条件"托盘扫描时间"的起始时间和结束时间
backupquery.trayofflinescanquery.getScanTime4queryForm = function(isBegin){
	var nowDate =new Date();
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
/**===============================================叉车离线扫描Form=====start====================================================**/
//定义叉车离线扫描查询form 零担
Ext.define('Foss.unload.trayofflinescanquery.QueryForm',{
	extend : 'Ext.form.Panel',
	title : backupquery.trayofflinescanquery.i18n('foss.unload.trayofflinescanquery.queryForm.title'),//查询条件	
	frame : true,
	collapsible : true,//允许展开收缩
	animCollapse : true,//显示动画效果
	defaults : {
		margin : '5 10 5 10',
		labelWidth : 85,
		columnWidth : 1/4,
		xtype : 'textfield'
	},
	layout : 'column',
	items : [{
		fieldLabel :backupquery.trayofflinescanquery.i18n('foss.unload.trayofflinescanquery.queryForm.waybillNo'),//运单号 
		name:'billNo',
		maxLength:13
	}, {
		fieldLabel : backupquery.trayofflinescanquery.i18n('foss.unload.trayofflinescanquery.queryForm.forkLiftDriverName'),//叉车司机
		name : 'forkLiftDriver',
		xtype:'commonemployeeselector'
	}, {
		xtype : 'rangeDateField',
		fieldLabel : backupquery.trayofflinescanquery.i18n('foss.unload.trayofflinescanquery.queryForm.scanTime'),//扫描时间
		columnWidth : 1/2,
		// 类型为datetimefield_date97需要配置fieldId,可以赋予此属性任何唯一标 //识的String值
		fieldId : 'Foss_backupquery_QueryForm_TrayScanTime_ID',
		dateType: 'datetimefield_date97',
		dateRange : 7,
		fromName : 'beginOfflineTrayScanTime',
		fromValue : Ext.Date.format(backupquery.trayofflinescanquery.getScanTime4queryForm(true), 'Y-m-d H:i:s'),
		toValue : Ext.Date.format(backupquery.trayofflinescanquery.getScanTime4queryForm(false), 'Y-m-d H:i:s'),
		toName : 'endOfflineTrayScanTime',
		allowBlank : false,
		disallowBlank : true	
	}, {
		fieldLabel : backupquery.trayofflinescanquery.i18n('foss.unload.trayofflinescanquery.queryForm.forkliftDept'),//叉车部门
		name : 'forkLiftDept',
		xtype:'dynamicorgcombselector'
	}, {
		border : false,
		columnWidth : 1,
		xtype : 'container',
		defaults : {
			margin : '5 0 5 0'
		},
		layout:'column',
		items : [{
			xtype : 'button',
			columnWidth:.08,
			text : backupquery.trayofflinescanquery.i18n('foss.unload.trayofflinescanquery.queryForm.resetButtonText'),//'重置'
			handler : function(){
				this.up('form').getForm().reset();
				this.up('form').getForm().findField('beginOfflineTrayScanTime').setValue(Ext.Date.format(backupquery.trayofflinescanquery.getScanTime4queryForm(true),'Y-m-d H:i:s'));
				this.up('form').getForm().findField('endOfflineTrayScanTime').setValue(Ext.Date.format(backupquery.trayofflinescanquery.getScanTime4queryForm(false),'Y-m-d H:i:s'));	
			}
		}, {
			xtype:'container',
			columnWidth : .84,
			html : '&nbsp;'
		}, {
			columnWidth : .08,
			xtype : 'button',
			cls : 'yellow_button',
			disabled:!backupquery.trayofflinescanquery.isPermission('unload/queryofflinescanButton'),
			hidden:!backupquery.trayofflinescanquery.isPermission('unload/queryofflinescanButton'),
			text : backupquery.trayofflinescanquery.i18n('foss.unload.trayofflinescanquery.queryButtonText'),//'查询'
			handler : function(){
				var form = this.up('form').getForm();
				if(!form.isValid()) {
					return;
				};
				backupquery.trayofflinescanquery.pagingBar.moveFirst();
			}
		}]
	
	}],
	constructor : function(config){
		var me = this,cfg = Ext.apply({},config);
		me.callParent([ cfg ]);
		
	}
	
})

//定义叉车离线扫描查询form 快递
Ext.define('Foss.unload.trayofflinescanquery.QueryFormExpress',{
	extend : 'Ext.form.Panel',
	title : backupquery.trayofflinescanquery.i18n('foss.unload.trayofflinescanquery.queryForm.title'),//查询条件	
	frame : true,
	collapsible : true,//允许展开收缩
	animCollapse : true,//显示动画效果
	defaults : {
		margin : '5 10 5 10',
		labelWidth : 85,
		columnWidth : 1/4,
		xtype : 'textfield'
	},
	layout : 'column',
	items : [{
		fieldLabel :backupquery.trayofflinescanquery.i18n('foss.unload.trayofflinescanquery.queryForm.waybillNo'),//运单号 
		name:'billNo'
	}, {
		fieldLabel : backupquery.trayofflinescanquery.i18n('foss.unload.trayofflinescanquery.queryForm.forkLiftDriverName'),//叉车司机
		name : 'forkLiftDriver',
		xtype:'commonemployeeselector'
	}, {
		xtype : 'rangeDateField',
		fieldLabel : backupquery.trayofflinescanquery.i18n('foss.unload.trayofflinescanquery.queryForm.scanTime'),//扫描时间
		columnWidth : 1/2,
		// 类型为datetimefield_date97需要配置fieldId,可以赋予此属性任何唯一标 //识的String值
		fieldId : 'Foss_backupquery_QueryForm_TrayScanTime_ID_Express',
		dateType: 'datetimefield_date97',
		dateRange : 7,
		fromName : 'beginOfflineTrayScanTime',
		fromValue : Ext.Date.format(backupquery.trayofflinescanquery.getScanTime4queryForm(true), 'Y-m-d H:i:s'),
		toValue : Ext.Date.format(backupquery.trayofflinescanquery.getScanTime4queryForm(false), 'Y-m-d H:i:s'),
		toName : 'endOfflineTrayScanTime',
		allowBlank : false,
		disallowBlank : true	
	}, {
		fieldLabel : backupquery.trayofflinescanquery.i18n('foss.unload.trayofflinescanquery.queryForm.forkliftDept'),//叉车部门
		name : 'forkLiftDept',
		xtype:'dynamicorgcombselector'
	}, {
		border : false,
		columnWidth : 1,
		xtype : 'container',
		defaults : {
			margin : '5 0 5 0'
		},
		layout:'column',
		items : [{
			xtype : 'button',
			columnWidth:.08,
			text : backupquery.trayofflinescanquery.i18n('foss.unload.trayofflinescanquery.queryForm.resetButtonText'),//'重置'
			handler : function(){
				this.up('form').getForm().reset();
				this.up('form').getForm().findField('beginOfflineTrayScanTime').setValue(Ext.Date.format(backupquery.trayofflinescanquery.getScanTime4queryForm(true),'Y-m-d H:i:s'));
				this.up('form').getForm().findField('endOfflineTrayScanTime').setValue(Ext.Date.format(backupquery.trayofflinescanquery.getScanTime4queryForm(false),'Y-m-d H:i:s'));	
			}
		}, {
			xtype:'container',
			columnWidth : .84,
			html : '&nbsp;'
		}, {
			columnWidth : .08,
			xtype : 'button',
			cls : 'yellow_button',
			disabled:!backupquery.trayofflinescanquery.isPermission('unload/queryofflinescanButton'),
			hidden:!backupquery.trayofflinescanquery.isPermission('unload/queryofflinescanButton'),
			text : backupquery.trayofflinescanquery.i18n('foss.unload.trayofflinescanquery.queryButtonText'),//'查询'
			handler : function(){
				var form = this.up('form').getForm();
				if(!form.isValid()) {
					return;
				};
				backupquery.trayofflinescanquery.pagingBarExpress.moveFirst();
			}
		}]
	
	}],
	constructor : function(config){
		var me = this,cfg = Ext.apply({},config);
		me.callParent([ cfg ]);
		
	}
	
})
/**===============================================叉车离线扫描Form===end======================================================**/

/**===============================================叉车离线扫描grid===start======================================================**/
//离线扫描信息model 零担
Ext.define('Foss.unload.trayofflinescanquery.TrayOfflineScanModel',{
 	extend : 'Ext.data.Model',
 	fields :[{
 		name : 'trayOfflineScanTime',
 		type : 'date',
 		convert : dateConvert
 	}, {	
 		name : 'waybillNo',
 		type : 'string'
 	}, {
 		name : 'serialNo',
 		type : 'string'
 	}, {
 		name : 'packageNo',
 		type : 'string'
 	}, {
 		name : 'forkLiftDriverName',
 		type : 'string'
 	}, {
 		name : 'forkLiftDriverCode',
 		type : 'string'
 	}, {
 		name : 'forkLiftDept',
 		type : 'string'
 	}, {
 		name : 'offlineTaskNo',
 		type : 'string'
 	}, {
 		name : 'taskNo',
 		type : 'string'
 	}, {
 		name : 'createUserName',
 		type : 'string'
 	}, {
 		name : 'createUserCode',
 		type : 'string'
 	}, {
 		name : 'createrDept',
 		type : 'string'
 	}, {
 		name : 'forkliftCount',
 		type : 'number'
 	},{
 		name : 'loaderCode',
 		type : 'string'
 	},{
 	 	name : 'loaderName',
 		type : 'string'
 	},{
 		name : 'loadOrgName',
 		type : 'string' 	
 	}] 
});

//离线扫描信息model 快递
Ext.define('Foss.unload.trayofflinescanquery.TrayOfflineScanModelExpress',{
 	extend : 'Ext.data.Model',
 	fields :[{
 		name : 'trayOfflineScanTime',
 		type : 'date',
 		convert : dateConvert
 	}, {	
 		name : 'waybillNo',
 		type : 'string'
 	},{
 		name : 'forkLiftDriverName',
 		type : 'string'
 	}, {
 		name : 'forkLiftDriverCode',
 		type : 'string'
 	}, {
 		name : 'forkLiftDept',
 		type : 'string'
 	}, {
 		name : 'offlineTaskNo',
 		type : 'string'
 	}, {
 		name : 'taskNo',
 		type : 'string'
 	}, {
 		name : 'createUserName',
 		type : 'string'
 	}, {
 		name : 'createUserCode',
 		type : 'string'
 	}, {
 		name : 'createrDept',
 		type : 'string'
 	}] 
});

//离线扫描信息store 零担
Ext.define('Foss.unload.trayofflinescanquery.TrayOfflineScanStore',{
	extend : 'Ext.data.Store',
	//页数
	pageSize : 20,
	//模型
	model : 'Foss.unload.trayofflinescanquery.TrayOfflineScanModel',
	//代理
	proxy : {
		type : 'ajax',
		actionMethods : 'POST',
		url :  backupquery.realPath('queryTrayOfflineScanList.action'),
		reader : {
			type : 'json',
			root : 'trayOfflineScanVo.trayOfflineScanList',
			successProperty : 'success', 
			totalProperty : 'totalCount'
		}
	}, 
	constructor : function(config){
		var me = this, cfg = Ext.apply[{ },config];
		me.callParent([ cfg ]);
	},
	listeners : {
		'beforeload' : function(store, operation, eOpts){
			var queryParams = backupquery.trayofflinescanquery.TrayOfflineScanQueryForm.getForm().getValues();
			Ext.apply(operation, {
				params : {
					'trayOfflineScanVo.queryTrayOfflineScanConditionDto.forkLiftDept' :queryParams.forkLiftDept,
					'trayOfflineScanVo.queryTrayOfflineScanConditionDto.forkLiftDriverCode' :queryParams.forkLiftDriver,
					'trayOfflineScanVo.queryTrayOfflineScanConditionDto.waybillNo' : queryParams.billNo,
					'trayOfflineScanVo.queryTrayOfflineScanConditionDto.beginOfflineTrayScanTime' : queryParams.beginOfflineTrayScanTime,
					'trayOfflineScanVo.queryTrayOfflineScanConditionDto.endOfflineTrayScanTime' : queryParams.endOfflineTrayScanTime
				}
			});	
		}
	}
});

//离线扫描信息store 快递
Ext.define('Foss.unload.trayofflinescanquery.TrayOfflineScanStoreExpress',{
	extend : 'Ext.data.Store',
	//页数
	pageSize : 20,
	//模型
	model : 'Foss.unload.trayofflinescanquery.TrayOfflineScanModelExpress',
	//代理
	proxy : {
		type : 'ajax',
		actionMethods : 'POST',
		url :  backupquery.realPath('queryTrayOfflineScanListExpress.action'),
		reader : {
			type : 'json',
			root : 'trayOfflineScanVo.trayOfflineScanListExpress',
			successProperty : 'success', 
			totalProperty : 'totalCount'
		}
	}, 
	constructor : function(config){
		var me = this, cfg = Ext.apply[{ },config];
		me.callParent([ cfg ]);
	},
	listeners : {
		'beforeload' : function(store, operation, eOpts){
			var queryParams = backupquery.trayofflinescanquery.TrayOfflineScanQueryFormExpress.getForm().getValues();
			Ext.apply(operation, {
				params : {
					'trayOfflineScanVo.queryTrayOfflineScanConditionDto.forkLiftDept' :queryParams.forkLiftDept,
					'trayOfflineScanVo.queryTrayOfflineScanConditionDto.forkLiftDriverCode' :queryParams.forkLiftDriver,
					'trayOfflineScanVo.queryTrayOfflineScanConditionDto.waybillNo' : queryParams.billNo,
					'trayOfflineScanVo.queryTrayOfflineScanConditionDto.beginOfflineTrayScanTime' : queryParams.beginOfflineTrayScanTime,
					'trayOfflineScanVo.queryTrayOfflineScanConditionDto.endOfflineTrayScanTime' : queryParams.endOfflineTrayScanTime
				}
			});	
		}
	}
});

//显示的Grid  零担
Ext.define('Foss.unload.trayofflinescanquery.TrayOfflineScanGrid',{
	extend : 'Ext.grid.Panel',
	frame : true,
	collapsible : true,//允许展开收缩
	animCollapse : true,//显示动画效果
	autoScroll : true,
	height: 500,
	title : backupquery.trayofflinescanquery.i18n('foss.unload.trayofflinescanquery.TrayOfflineScanInfo'),
	constructor : function(config){
		var me = this ,cfg = Ext.apply({}, config);
		me.store =Ext.create('Foss.unload.trayofflinescanquery.TrayOfflineScanStore');
		me.tbar = [{
			xtype : 'button',
			hidden:!backupquery.trayofflinescanquery.isPermission('unload/exporttrayofflinescanButton'),
			text : backupquery.trayofflinescanquery.i18n('foss.unload.trayofflinescanquery.ExportButtonText'),/*'导出'*/
			handler : function(){
				if(!Ext.fly('downloadAttachFileForm')){
					var frm = document.createElement('form');
					frm.id = 'downloadAttachFileForm',
					frm.style.display = 'none',
					document.body.appendChild(frm)
				}
				var form = backupquery.trayofflinescanquery.TrayOfflineScanQueryForm.getForm();
				var queryParams = form.getValues();
				if(!form.isValid()){
					Ext.ux.Toast.msg('提示','请输入合法的叉车扫描时间！','error', 2000);
					return;
				}
				Ext.Ajax.request({
					url : backupquery.realPath('exportTrayOfflineScanTaskExcel.action'),
					form: Ext.fly('downloadAttachFileForm'),
					method : 'POST',
					params : {
						'trayOfflineScanVo.queryTrayOfflineScanConditionDto.forkLiftDept' :queryParams.forkLiftDept,
						'trayOfflineScanVo.queryTrayOfflineScanConditionDto.forkLiftDriverCode' :queryParams.forkLiftDriver,
						'trayOfflineScanVo.queryTrayOfflineScanConditionDto.waybillNo' : queryParams.billNo,
						'trayOfflineScanVo.queryTrayOfflineScanConditionDto.beginOfflineTrayScanTime' : queryParams.beginOfflineTrayScanTime,
						'trayOfflineScanVo.queryTrayOfflineScanConditionDto.endOfflineTrayScanTime' : queryParams.endOfflineTrayScanTime
					},
					isUpload: true,
					success:function(response){
					
					},
					exception : function(response) {
						top.Ext.MessageBox.alert(backupquery.trayofflinescanquery.i18n('foss.unload.trayofflinescanquery.TrayOfflineScanGrid.expertFailureAlertInfo')/*'导出失败'*/,result.message);
					}
			});
			}
		}],
		me.bbar = Ext.create('Deppon.StandardPaging',{
			store : me.store,
			pageSize : 20,
			maximumSize : 50,
			plugins : Ext.create('Deppon.ux.PageSizePlugin', {
				sizeList : [['20', 20], ['30', 30], ['40', 40], ['50', 50]]
			})
		});
	   backupquery.trayofflinescanquery.pagingBar = me.bbar;
	   me.callParent([cfg]);
	},
	columns : [{
		dataIndex : 'action',
		text : '操作',
		width : 50
		
	}, {
		dataIndex : 'trayOfflineScanTime',
		align : 'center',
		width : 130,
		xtype : 'datecolumn',
		format : 'Y-m-d H:i:s',
		text : backupquery.trayofflinescanquery.i18n('foss.unload.trayofflinescanquery.TrayOfflineScanGrid.trayOfflineScanTime')//离线扫描时间
	},  {
		dataIndex : 'packageNo',
		align : 'center',
		width : 90,
		xtype : 'ellipsiscolumn',
		text : '包号'//包号
	},{
		dataIndex : 'waybillNo',
		align : 'center',
		width : 90,
		xtype : 'ellipsiscolumn',
		text : backupquery.trayofflinescanquery.i18n('foss.unload.trayofflinescanquery.TrayOfflineScanGrid.waybillNo')//运单号
	}, {
		dataIndex : 'serialNo',
		align : 'center',
		width : 90,
		xtype : 'ellipsiscolumn',
		text : backupquery.trayofflinescanquery.i18n('foss.unload.trayofflinescanquery.TrayOfflineScanGrid.serialNo')//流水号
	}, {
		dataIndex : 'forkLiftDriverName',
		align : 'center',
		width : 90,
		xtype : 'ellipsiscolumn',
		text : backupquery.trayofflinescanquery.i18n('foss.unload.trayofflinescanquery.TrayOfflineScanGrid.forkLiftDriverName')//叉车司机姓名
	}
	, {
		dataIndex : 'forkLiftDriverCode',
		align : 'center',
		width : 90,
		xtype : 'ellipsiscolumn',
		text : backupquery.trayofflinescanquery.i18n('foss.unload.trayofflinescanquery.TrayOfflineScanGrid.forkLiftDriverCode')//叉车司机工号
	}, {
		dataIndex : 'forkLiftDept',
		align : 'center',
		width : 90,
		xtype : 'ellipsiscolumn',
		text : backupquery.trayofflinescanquery.i18n('foss.unload.trayofflinescanquery.TrayOfflineScanGrid.forkLiftDept')//叉车司机部门
	}, {
		dataIndex : 'offlineTaskNo',
		align : 'center',
		width : 90,
		xtype : 'ellipsiscolumn',
		text : backupquery.trayofflinescanquery.i18n('foss.unload.trayofflinescanquery.TrayOfflineScanGrid.offlineTaskNo')//叉车离线扫描任务号
	}, {
		dataIndex : 'taskNo',
		align : 'center',
		width : 90,
		xtype : 'ellipsiscolumn',
		text : backupquery.trayofflinescanquery.i18n('foss.unload.trayofflinescanquery.TrayOfflineScanGrid.taskNo')//任务号
	}, {
		dataIndex : 'createUserName',
		align : 'center',
		width : 90,
		xtype : 'ellipsiscolumn',
		text : backupquery.trayofflinescanquery.i18n('foss.unload.trayofflinescanquery.TrayOfflineScanGrid.createUserName')//绑定人姓名
	}, {
		dataIndex : 'createUserCode',
		align : 'center',
		width : 90,
		xtype : 'ellipsiscolumn',
		text : backupquery.trayofflinescanquery.i18n('foss.unload.trayofflinescanquery.TrayOfflineScanGrid.createUserCode')//绑定人工号
	}, {
		dataIndex : 'createrDept',
		align : 'center',
		width : 90,
		xtype : 'ellipsiscolumn',
		text : backupquery.trayofflinescanquery.i18n('foss.unload.trayofflinescanquery.TrayOfflineScanGrid.createrDept')//绑定人部门
	}, {
		dataIndex : 'forkliftCount',
		align : 'center',
		width : 90,
		xtype : 'ellipsiscolumn',
		text : backupquery.trayofflinescanquery.i18n('foss.unload.trayofflinescanquery.TrayOfflineScanGrid.forkliftCount')//叉车票数
	},{
		dataIndex : 'loaderCode',
		align : 'center',
		width : 130,
		xtype : 'ellipsiscolumn',
		text : backupquery.trayofflinescanquery.i18n('foss.unload.trayofflinescanquery.TrayOfflineScanGrid.loaderCode')//卸车任务创建人工号
	},{
		dataIndex : 'loaderName',
		align : 'center',
		width : 130,
		xtype : 'ellipsiscolumn',
		text : backupquery.trayofflinescanquery.i18n('foss.unload.trayofflinescanquery.TrayOfflineScanGrid.loaderName')//卸车任务创建人姓名
	},{
		dataIndex : 'loadOrgName',
		align : 'center',
		width : 130,
		xtype : 'ellipsiscolumn',
		text : backupquery.trayofflinescanquery.i18n('foss.unload.trayofflinescanquery.TrayOfflineScanGrid.loadOrgName')//卸车任务创建人部门
	}]
});

//显示的Grid  快递
Ext.define('Foss.unload.trayofflinescanquery.TrayOfflineScanGridExpress',{
	extend : 'Ext.grid.Panel',
	frame : true,
	collapsible : true,//允许展开收缩
	animCollapse : true,//显示动画效果
	autoScroll : true,
	height: 500,
	title : backupquery.trayofflinescanquery.i18n('foss.unload.trayofflinescanquery.TrayOfflineScanInfo'),
	constructor : function(config){
		var me = this ,cfg = Ext.apply({}, config);
		me.store =Ext.create('Foss.unload.trayofflinescanquery.TrayOfflineScanStoreExpress');
		me.tbar = [{
			xtype : 'button',
			hidden:!backupquery.trayofflinescanquery.isPermission('unload/exporttrayofflinescanButton'),
			text : backupquery.trayofflinescanquery.i18n('foss.unload.trayofflinescanquery.ExportButtonText'),/*'导出'*/
			handler : function(){
				if(!Ext.fly('downloadAttachFileForm')){
					var frm = document.createElement('form');
					frm.id = 'downloadAttachFileForm',
					frm.style.display = 'none',
					document.body.appendChild(frm)
				}
				var form = backupquery.trayofflinescanquery.TrayOfflineScanQueryFormExpress.getForm();
				var queryParams = form.getValues();
				if(!form.isValid()){
					Ext.ux.Toast.msg('提示','请输入合法的叉车扫描时间！','error', 2000);
					return;
				}
				Ext.Ajax.request({
					url : backupquery.realPath('exportTrayOfflineScanTaskExcelExpress.action'),
					form: Ext.fly('downloadAttachFileForm'),
					method : 'POST',
					params : {
						'trayOfflineScanVo.queryTrayOfflineScanConditionDto.forkLiftDept' :queryParams.forkLiftDept,
						'trayOfflineScanVo.queryTrayOfflineScanConditionDto.forkLiftDriverCode' :queryParams.forkLiftDriver,
						'trayOfflineScanVo.queryTrayOfflineScanConditionDto.waybillNo' : queryParams.billNo,
						'trayOfflineScanVo.queryTrayOfflineScanConditionDto.beginOfflineTrayScanTime' : queryParams.beginOfflineTrayScanTime,
						'trayOfflineScanVo.queryTrayOfflineScanConditionDto.endOfflineTrayScanTime' : queryParams.endOfflineTrayScanTime
					},
					isUpload: true,
					success:function(response){
					
					},
					exception : function(response) {
						top.Ext.MessageBox.alert(backupquery.trayofflinescanquery.i18n('foss.unload.trayofflinescanquery.TrayOfflineScanGrid.expertFailureAlertInfo')/*'导出失败'*/,result.message);
					}
			});
			}
		}],
		me.bbar = Ext.create('Deppon.StandardPaging',{
			store : me.store,
			pageSize : 20,
			maximumSize : 50,
			plugins : Ext.create('Deppon.ux.PageSizePlugin', {
				sizeList : [['20', 20], ['30', 30], ['40', 40], ['50', 50]]
			})
		});
	   backupquery.trayofflinescanquery.pagingBarExpress = me.bbar;
	   me.callParent([cfg]);
	},
	columns : [{
		dataIndex : 'action',
		text : '操作',
		width : 50
		
	}, {
		dataIndex : 'trayOfflineScanTime',
		align : 'center',
		width : 130,
		xtype : 'datecolumn',
		format : 'Y-m-d H:i:s',
		text : backupquery.trayofflinescanquery.i18n('foss.unload.trayofflinescanquery.TrayOfflineScanGrid.trayOfflineScanTime')//离线扫描时间
	},  {
		dataIndex : 'waybillNo',
		align : 'center',
		width : 90,
		xtype : 'ellipsiscolumn',
		text : backupquery.trayofflinescanquery.i18n('foss.unload.trayofflinescanquery.TrayOfflineScanGrid.Number')//运单/包/笼号
	},{
		dataIndex : 'forkLiftDriverName',
		align : 'center',
		width : 90,
		xtype : 'ellipsiscolumn',
		text : backupquery.trayofflinescanquery.i18n('foss.unload.trayofflinescanquery.TrayOfflineScanGrid.forkLiftDriverName')//叉车司机姓名
	}
	, {
		dataIndex : 'forkLiftDriverCode',
		align : 'center',
		width : 90,
		xtype : 'ellipsiscolumn',
		text : backupquery.trayofflinescanquery.i18n('foss.unload.trayofflinescanquery.TrayOfflineScanGrid.forkLiftDriverCode')//叉车司机工号
	}, {
		dataIndex : 'forkLiftDept',
		align : 'center',
		width : 90,
		xtype : 'ellipsiscolumn',
		text : backupquery.trayofflinescanquery.i18n('foss.unload.trayofflinescanquery.TrayOfflineScanGrid.forkLiftDept')//叉车司机部门
	}, {
		dataIndex : 'offlineTaskNo',
		align : 'center',
		width : 90,
		xtype : 'ellipsiscolumn',
		text : backupquery.trayofflinescanquery.i18n('foss.unload.trayofflinescanquery.TrayOfflineScanGrid.offlineTaskNo')//叉车离线扫描任务号
	}, {
		dataIndex : 'taskNo',
		align : 'center',
		width : 90,
		xtype : 'ellipsiscolumn',
		text : backupquery.trayofflinescanquery.i18n('foss.unload.trayofflinescanquery.TrayOfflineScanGrid.taskNo')//任务号
	}, {
		dataIndex : 'createUserName',
		align : 'center',
		width : 90,
		xtype : 'ellipsiscolumn',
		text : backupquery.trayofflinescanquery.i18n('foss.unload.trayofflinescanquery.TrayOfflineScanGrid.createUserName')//绑定人姓名
	}, {
		dataIndex : 'createUserCode',
		align : 'center',
		width : 90,
		xtype : 'ellipsiscolumn',
		text : backupquery.trayofflinescanquery.i18n('foss.unload.trayofflinescanquery.TrayOfflineScanGrid.createUserCode')//绑定人工号
	}, {
		dataIndex : 'createrDept',
		align : 'center',
		width : 90,
		xtype : 'ellipsiscolumn',
		text : backupquery.trayofflinescanquery.i18n('foss.unload.trayofflinescanquery.TrayOfflineScanGrid.createrDept')//绑定人部门
	}]
});

//Ext.define('Foss.unload.trayofflinescanquery.QueryOfflineTrayScanModel',{});
backupquery.trayofflinescanquery.TrayOfflineScanGrid = Ext.create('Foss.unload.trayofflinescanquery.TrayOfflineScanGrid');
backupquery.trayofflinescanquery.TrayOfflineScanQueryForm = Ext.create('Foss.unload.trayofflinescanquery.QueryForm');
backupquery.trayofflinescanquery.TrayOfflineScanGridExpress = Ext.create('Foss.unload.trayofflinescanquery.TrayOfflineScanGridExpress');
backupquery.trayofflinescanquery.TrayOfflineScanQueryFormExpress = Ext.create('Foss.unload.trayofflinescanquery.QueryFormExpress');
Ext.onReady(function(){
	Ext.QuickTips.init();
	
	Ext.create('Ext.panel.Panel',{
		id : 'T_backupquery-trayofflinescanqueryindex_content',
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
				//零担
				title : backupquery.trayofflinescanquery.i18n('foss.unload.trayofflinescanquery.personCount'),
				tabConfig : {
					width : 120
				},
				itemId : 'TemporaryAssignments',
				items : [backupquery.trayofflinescanquery.TrayOfflineScanQueryForm,backupquery.trayofflinescanquery.TrayOfflineScanGrid]
		     }, {
		    	//快递
				title : backupquery.trayofflinescanquery.i18n('foss.unload.trayofflinescanquery.teamCountPanel'),
				tabConfig : {
					width : 120
				},
				itemId : 'TaskAssignments',
				items : [backupquery.trayofflinescanquery.TrayOfflineScanQueryFormExpress,backupquery.trayofflinescanquery.TrayOfflineScanGridExpress]
			 }]
		}],
		renderTo : 'T_backupquery-trayofflinescanqueryindex-body'
	});

})