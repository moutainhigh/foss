/**
 * @author 129903-阮正华
 */
/***************************************************  定义baseinfo.logsMonitor.i18n('foss.baseinfo.baseinfo-logsMonitor')列表查询条件窗口 *************************************************/
/*
 * 查询条件表单FORM中的KEY/VALUE的参数MAP存储器
 */
Ext.define('Foss.baseinfo.logsMonitor.QueryFormParameterStore', {
	extend : 'Ext.data.Store',
	fields : [{
			name : 'valueCode',
			type : 'string'
		}, {
			name : 'valueName',
			type : 'string'
		}
	],
	proxy : {
		type : 'memory',
		reader : {
			type : 'json',
			root : 'items' //定义读取JSON数据的根对象
		}
	},
	constructor : function (config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});
/*
 * 定义查询条件的表单FORM
 */
Ext.define('Foss.baseinfo.logsMonitor.QueryForm', {
	extend : 'Ext.form.Panel',
	title : baseinfo.logsMonitor.i18n('foss.baseinfo.queryCondition'),
	id : 'Foss_baseinfo_logsMonitor_QueryForm_Id',
	frame : true,
	collapsible : true,
	layout:'column',
	defaults : {
		labelSeparator : ':',
		margin : '8 10 5 10',
		anchor : '100%'
	},
	height : 285,
	defaultType : 'textfield',
	layout : 'column',
	items : [{
			xtype:'datefield',
			columnWidth: 0.33,
			name : 'beginDate',
			fieldLabel : baseinfo.logsMonitor.i18n('foss.baseinfo.logsMonitor.beginDate'),
			maxValue:new Date(),
			format:'Y-m-d',
			value:new Date(),
			allowBlank:false
		}, {
			xtype:'datefield',
			name : 'endDate',
			columnWidth: 0.33,
			fieldLabel : baseinfo.logsMonitor.i18n('foss.baseinfo.logsMonitor.endDate'),
			maxValue:new Date(),
			format:'Y-m-d',
			value:new Date(),
			allowBlank:false
		}, {
			xtype : 'combobox',
			name : 'type',
			fieldLabel : baseinfo.logsMonitor.i18n('foss.baseinfo.logsMonitor.type'),
			columnWidth : 0.34,
			displayField : 'valueName',
			valueField : 'valueCode',
			editable : false,
			store : Ext.create('Foss.baseinfo.logsMonitor.QueryFormParameterStore', {
				data : {
					'items' : [{
							'valueCode' : '',
							'valueName' : baseinfo.logsMonitor.i18n('foss.baseinfo.logsMonitor.all')
					     },{
							'valueCode' : 'AUDIT',
							'valueName' : 'AUDIT'
						}, {
							'valueCode' : 'BUSINESS',
							'valueName' : 'BUSINESS'
						}, {
							'valueCode' : 'DAO',
							'valueName' : 'DAO'
						}, {
							'valueCode' : 'EXCEPTION',
							'valueName' : 'EXCEPTION'
						}, {
							'valueCode' : 'GUI',
							'valueName' : 'GUI'
						}, {
							'valueCode' : 'INTERFACE',
							'valueName' : 'INTERFACE'
						}, {
							'valueCode' : 'JOB',
							'valueName' : 'JOB'
						}, {
							'valueCode' : 'PDA',
							'valueName' : 'PDA'
						}, {
							'valueCode' : 'PERFORMANCE',
							'valueName' : 'PERFORMANCE'
						}, {
							'valueCode' : 'SERVICE',
							'valueName' : 'SERVICE'
						}, {
							'valueCode' : 'WEB',
							'valueName' : 'WEB'
						}
					]
				}
			}),
			value : ''
		}, {
			name : 'appName',
			fieldLabel : baseinfo.logsMonitor.i18n('foss.baseinfo.logsMonitor.appName'),
			columnWidth : 0.33
		}, {
			name : 'moduleName',
			fieldLabel : baseinfo.logsMonitor.i18n('foss.baseinfo.logsMonitor.moduleName'),
			columnWidth : 0.33
		}, {
			name : 'message',
			fieldLabel : baseinfo.logsMonitor.i18n('foss.baseinfo.logsMonitor.message'),
			columnWidth : 0.34
		}, {
			name : 'url',
			fieldLabel : baseinfo.logsMonitor.i18n('foss.baseinfo.logsMonitor.url'),
			columnWidth : 0.33
		}, {
			name : 'userName',
			fieldLabel : baseinfo.logsMonitor.i18n('foss.baseinfo.logsMonitor.userName'),
			columnWidth : 0.33
		}, {
			name : 'requestId',
			fieldLabel : baseinfo.logsMonitor.i18n('foss.baseinfo.logsMonitor.requestId'),
			columnWidth : 0.34
		}, {
			name : 'clazz',
			fieldLabel : baseinfo.logsMonitor.i18n('foss.baseinfo.logsMonitor.clazz'),
			columnWidth : 0.33
		}, {
			name : 'method',
			fieldLabel : baseinfo.logsMonitor.i18n('foss.baseinfo.logsMonitor.method'),
			columnWidth : 0.33
		}, {
			name : 'ip',
			fieldLabel : baseinfo.logsMonitor.i18n('foss.baseinfo.logsMonitor.ip'),
			columnWidth : 0.34
		}, {
			name : 'macAddress',
			fieldLabel : baseinfo.logsMonitor.i18n('foss.baseinfo.logsMonitor.macAddress'),
			columnWidth : 0.33
		}, {
			name : 'version',
			fieldLabel : baseinfo.logsMonitor.i18n('foss.baseinfo.logsMonitor.version'),
			columnWidth : 0.33
		}, {
			name : 'action',
			fieldLabel : baseinfo.logsMonitor.i18n('foss.baseinfo.logsMonitor.action'),
			columnWidth : 0.34
		}, {
			xtype : 'button',
			columnWidth : .08,
			text : baseinfo.logsMonitor.i18n('foss.baseinfo.reset'),
			disabled:!baseinfo.logsMonitor.isPermission('logsMonitor/logsMonitorQueryButton'),
			hidden:!baseinfo.logsMonitor.isPermission('logsMonitor/logsMonitorQueryButton'),
			handler : function () {
				Ext.getCmp('Foss_baseinfo_logsMonitor_QueryForm_Id').getForm().reset();
			}
		}, {
			border : false,
			xtype : 'container',
			columnWidth : .84,
			html : '&nbsp;'
		}, {
			columnWidth : .08,
			xtype : 'button',
			cls : 'yellow_button',
			text : baseinfo.logsMonitor.i18n('foss.baseinfo.query'),
			disabled:!baseinfo.logsMonitor.isPermission('logsMonitor/logsMonitorQueryButton'),
			hidden:!baseinfo.logsMonitor.isPermission('logsMonitor/logsMonitorQueryButton'),
			handler : function () {
				var grid = Ext.getCmp('Foss_baseinfo_logsMonitor_LogsMonitorGrid_Id');
				var store = grid.getStore();
				if(store){
					if(grid.isHidden()){
						grid.show();
					}
				}
				var theForm = Ext.getCmp('Foss_baseinfo_logsMonitor_QueryForm_Id').getForm();
				var queryParams = theForm.getValues();
				if(theForm.isValid()){
					if(queryParams.beginDate!=queryParams.endDate){
						Ext.MessageBox.show({
							title : baseinfo.logsMonitor.i18n("foss.baseinfo.tips"),
							msg : baseinfo.logsMonitor.i18n('foss.baseinfo.logsMonitor.dateMessg'),
							width : 300,
							buttons : Ext.Msg.OK,
							icon : Ext.MessageBox.INFO
						});
						return false;
					}
					grid.getPagingToolbar().moveFirst();
				}else{
					Ext.MessageBox.show({
						title : baseinfo.logsMonitor.i18n("foss.baseinfo.tips"),
						msg : baseinfo.logsMonitor.i18n('foss.baseinfo.logsMonitor.dateError'),
						width : 300,
						buttons : Ext.Msg.OK,
						icon : Ext.MessageBox.INFO
					});
					return false;
				}
				
			}
		}
	]
});
/*
 * 定义：一个日志监控的数据模型"Model"
 */
Ext.define('Foss.baseinfo.logsMonitor.LogsMonitorModel', {
	extend : 'Ext.data.Model',
	idProperty : 'extid',
	idgen : 'uuid',
	fields : [{
			name : 'extid'
		}, {
			name : 'date', 
			type : 'String'
		}, {
			name : 'type', 
			type : 'string'
		}, {
			name : 'appName', 
			type : 'string'
		}, {
			name : 'moduleName',
			type : 'string'
		}, {
			name : 'message', 
			type : 'string'
		}, {
			name : 'url', 
			type : 'string'
		}, {
			name : 'userName', 
			type : 'string'
		}, {
			name : 'requestId', 
			type : 'string'
		}, {
			name : 'clazz',
			type : 'string'
		}, {
			name : 'method', 
			type : 'string'
		}, {
			name : 'args', 
			type : 'string'
		}, {
			name : 'ip',
			type : 'string'
		}, {
			name : 'macAddress', 
			type : 'string'
		}, {
			name : 'version', 
			type : 'string'
		}, {
			name : 'result', 
			type : 'string'
		}, {
			name : 'action', 
			type : 'string'
		}
	]
});
/*
 * 定义：日志监控的查询数据模型"Store"交互后台
 */
Ext.define('Foss.baseinfo.logsMonitor.LogsMonitorStore', {
	extend : 'Ext.data.Store',
	autoLoad : false,
	//页面条数定义
	pageSize : 10,
	//绑定model
	model : 'Foss.baseinfo.logsMonitor.LogsMonitorModel',
	proxy : {
		//以JSON的方式加载数据
		type : 'ajax',
		actionMethods : 'POST',
		url : baseinfo.realPath('queryLogsMonitorList.action'),
		reader : {
			type : 'json',
			root : 'logMonitorVO.logMonitorEntityList',
			totalProperty : 'totalCount',
			successProperty : 'success'
		}
	},
	//构造函数
	constructor : function (config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	},
	//监听器
	listeners : {
		beforeload : function (store, operation, eOpts) {
			var queryForm = Ext.getCmp('Foss_baseinfo_logsMonitor_QueryForm_Id').getForm();
			if (queryForm != null) {
				var queryParams = queryForm.getValues();
				//动态设置URL
				if (!Ext.isEmpty(queryParams)) {
					var url = 'queryLogsMonitorList.action';
					var params = {
						'logMonitorVO.logMonitorEntity.date' : queryParams.beginDate,
						'logMonitorVO.logMonitorEntity.type' : queryParams.type,
						'logMonitorVO.logMonitorEntity.appName' : queryParams.appName,
						'logMonitorVO.logMonitorEntity.moduleName' : queryParams.moduleName,
						'logMonitorVO.logMonitorEntity.message' : queryParams.message,
						'logMonitorVO.logMonitorEntity.url' : queryParams.url,
						'logMonitorVO.logMonitorEntity.userName' : queryParams.userName,
						'logMonitorVO.logMonitorEntity.requestId' : queryParams.requestId,
						'logMonitorVO.logMonitorEntity.clazz' : queryParams.clazz,
						'logMonitorVO.logMonitorEntity.method' : queryParams.method,
						'logMonitorVO.logMonitorEntity.ip' : queryParams.ip,
						'logMonitorVO.logMonitorEntity.macAddress' : queryParams.macAddress,
						'logMonitorVO.logMonitorEntity.version' : queryParams.version,
						'logMonitorVO.logMonitorEntity.action' : queryParams.action,
					};
					//执行应用
					this.proxy.url = baseinfo.realPath(url);
					Ext.apply(operation, {
						params : params
					});
					url = null;
					params = null;
				}
			}
		}
	}
});
/*
 * 定义：内嵌在结果列表中的查询详细的窗口
 */
Ext.define('Foss.baseinfo.logsMonitor.LogsMonitorDetailPanel', {
	extend : 'Ext.panel.Panel',
	title : baseinfo.logsMonitor.i18n('foss.baseinfo.LogsMonitorDetails'),
	frame : true,
	informationForm : null,
	/**
	 * 获取外请司机详细信息
	 * @return {Object} logsMonitorDetailForm
	 */
	getInformationForm : function () {
		if (this.informationForm == null) {
			this.informationForm = Ext.create('Foss.baseinfo.logsMonitor.LogsMonitorDetailForm');
		}
		return this.informationForm;
	},
	constructor : function (config) {
		Ext.apply(this, config);
		this.items = [this.getInformationForm()];
		this.callParent(arguments);
	},
	bindData : function (record) {
		//绑定表格数据到表单上
		this.getInformationForm().getForm().loadRecord(record);
	}
});
/*
 * 定义：内嵌在结果列表中的查询详细的窗口表单
 */
Ext.define('Foss.baseinfo.logsMonitor.LogsMonitorDetailForm', {
	extend : 'Ext.form.Panel',
	height : 340,
	defaults : {
		margin : '5 25 5 25',
		labelWidth : 100
	},
	defaultType : 'textfield',
	layout : {
		type : 'table',
		columns : 3
	},
	items : [{
			name : 'date',
			fieldLabel : baseinfo.logsMonitor.i18n('foss.baseinfo.logsMonitor.date'),
			readOnly : true,
			width:240
		},{
			name : 'type',
			fieldLabel : baseinfo.logsMonitor.i18n('foss.baseinfo.logsMonitor.type'),
			readOnly : true,
			width:240
		},{
			name : 'appName',
			fieldLabel : baseinfo.logsMonitor.i18n('foss.baseinfo.logsMonitor.appName'),
			readOnly : true,
			width:240
		},{
			name : 'moduleName',
			fieldLabel : baseinfo.logsMonitor.i18n('foss.baseinfo.logsMonitor.moduleName'),
			readOnly : true
		},{
			name : 'url',
			fieldLabel : baseinfo.logsMonitor.i18n('foss.baseinfo.logsMonitor.url'),
			readOnly : true,
			colspan : 2,
			width:480
		},{
			name : 'userName',
			fieldLabel : baseinfo.logsMonitor.i18n('foss.baseinfo.logsMonitor.userName'),
			readOnly : true
		},{
			name : 'requestId',
			fieldLabel : baseinfo.logsMonitor.i18n('foss.baseinfo.logsMonitor.requestId'),
			readOnly : true,
			colspan : 2,
			width:480
		},{
			name : 'clazz',
			fieldLabel : baseinfo.logsMonitor.i18n('foss.baseinfo.logsMonitor.clazz'),
			readOnly : true,
			columnWidth : 1,
			colspan : 3,
			width:700
		},{
			name : 'args',
			fieldLabel : baseinfo.logsMonitor.i18n('foss.baseinfo.logsMonitor.args'),
			readOnly : true,
			colspan : 3,
			width:700
		},{
			name : 'ip',
			fieldLabel : baseinfo.logsMonitor.i18n('foss.baseinfo.logsMonitor.ip'),
			readOnly : true
		},{
			name : 'macAddress',
			fieldLabel : baseinfo.logsMonitor.i18n('foss.baseinfo.logsMonitor.macAddress'),
			readOnly : true
		},{
			name : 'version',
			fieldLabel : baseinfo.logsMonitor.i18n('foss.baseinfo.logsMonitor.version'),
			readOnly : true
		},{
			name : 'result',
			fieldLabel : baseinfo.logsMonitor.i18n('foss.baseinfo.logsMonitor.result'),
			readOnly : true,
			colspan : 2,
			width:480
		}, {
			name : 'action',
			fieldLabel : baseinfo.logsMonitor.i18n('foss.baseinfo.logsMonitor.action'),
			readOnly : true
		},{
			xtype : 'textareafield',
			name : 'message',
			fieldLabel : baseinfo.logsMonitor.i18n('foss.baseinfo.logsMonitor.message'),
			readOnly : true,
			columnWidth : 1,
			colspan : 3,
			width:710,
			height:100
		}
	]
});
/*
 * 查询结果列表窗口
 */
Ext.define('Foss.baseinfo.logsMonitor.LogsMonitorGrid', {
	extend : 'Ext.grid.Panel',
	columnLines : true, // 增加表格列的分割线
	id : 'Foss_baseinfo_logsMonitor_LogsMonitorGrid_Id',
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	frame : true, // 表格对象增加一个边框
	stripeRows : true,
	columnLines : true, // 增加表格列的分割线
	title : baseinfo.logsMonitor.i18n('foss.baseinfo.logsMonitor.logsMonitorTitleInfo'), // 定义表格的标题
	collapsible : true,
	animCollapse : true,
	store : null,
	hidden:true,
	viewConfig: {
        enableTextSelection: true
    },
	//表格行可展开的插件
	plugins : [{
			ptype : 'rowexpander',
			rowsExpander : false, // 定义行展开模式（单行与多行），默认是多行展开(值true)
			rowBodyElement : 'Foss.baseinfo.logsMonitor.LogsMonitorDetailPanel' // 行体内容
		}
	],
	//列表分页组件对象
	pagingToolbar : null,
	getPagingToolbar : function () {
		var me = this;
		if (Ext.isEmpty(me.pagingToolbar)) {
			me.pagingToolbar = Ext.create('Deppon.StandardPaging', {
					store : me.store,
					pageSize : 10,
					prependButtons : true,
					defaults : {
						margin : '0 0 15 3'
					}
				});
		}
		return me.pagingToolbar;
	},
	// 定义表格列信息
	columns : [{
			header : baseinfo.logsMonitor.i18n('foss.baseinfo.logsMonitor.date'),
			dataIndex : 'date'
		}, {
			header : baseinfo.logsMonitor.i18n('foss.baseinfo.logsMonitor.type'),
			dataIndex : 'type'
		}, {
			header : baseinfo.logsMonitor.i18n('foss.baseinfo.logsMonitor.appName'),
			dataIndex : 'appName'
		}, {
			header : baseinfo.logsMonitor.i18n('foss.baseinfo.logsMonitor.moduleName'),
			dataIndex : 'moduleName'
		}, {
			header : baseinfo.logsMonitor.i18n('foss.baseinfo.logsMonitor.message'),
			dataIndex : 'message'
		}, {
			header : baseinfo.logsMonitor.i18n('foss.baseinfo.logsMonitor.url'),
			dataIndex : 'url'
		}, {
			header : baseinfo.logsMonitor.i18n('foss.baseinfo.logsMonitor.userName'),
			dataIndex : 'userName'
		}, {
			header : baseinfo.logsMonitor.i18n('foss.baseinfo.logsMonitor.requestId'),
			dataIndex : 'requestId'
		}, {
			header : baseinfo.logsMonitor.i18n('foss.baseinfo.logsMonitor.clazz'),
			dataIndex : 'clazz'
		}, {
			header : baseinfo.logsMonitor.i18n('foss.baseinfo.logsMonitor.method'),
			dataIndex : 'method'
		}, {
			header : baseinfo.logsMonitor.i18n('foss.baseinfo.logsMonitor.args'),
			dataIndex : 'args',
		}, {
			header : baseinfo.logsMonitor.i18n('foss.baseinfo.logsMonitor.ip'),
			dataIndex : 'ip'
		}, {
			header : baseinfo.logsMonitor.i18n('foss.baseinfo.logsMonitor.macAddress'),
			dataIndex : 'macAddress'
		}, {
			header : baseinfo.logsMonitor.i18n('foss.baseinfo.logsMonitor.version'),
			dataIndex : 'version'
		}, {
			header : baseinfo.logsMonitor.i18n('foss.baseinfo.logsMonitor.result'),
			dataIndex : 'result',
		}, {
			header : baseinfo.logsMonitor.i18n('foss.baseinfo.logsMonitor.action'),
			dataIndex : 'action',
		}
	],
	/**
	 * 构造函数
	 * @param {Object} config 构造函数配置项
	 */
	constructor : function (config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.baseinfo.logsMonitor.LogsMonitorStore');
		me.bbar = me.getPagingToolbar();
		me.getPagingToolbar().store = me.store;
		me.callParent([cfg]);
	}
});
/****************************************************  窗口初始化 *****************************************************/
Ext.onReady(function () {
	Ext.QuickTips.init();
	/*
	 * 创建查询表单"FORM"
	 */
	var queryForm = Ext.create('Foss.baseinfo.logsMonitor.QueryForm');
	/*
	 * 创建查询结果列表结果窗口
	 */
	var queryResult = Ext.create('Foss.baseinfo.logsMonitor.LogsMonitorGrid');
	/*
	 * 执行页面的初始化布局
	 */
	Ext.create('Ext.panel.Panel', {
		id : 'T_baseinfo-logsMonitor_content',
		cls : 'panelContentNToolbar',
		bodyCls : 'panelContentNToolbar-body',
		layout : 'auto',
		//获得查询表单"FORM"
		getQueryForm : function () {
			return queryForm;
		},
		//获得查询结果列表结果窗口
		getLogsMonitorGrid : function () {
			return queryResult;
		},
		items : [
		     //加入查询表单"FORM"
			queryForm,
			queryResult //加入结果列表
		],
		renderTo : 'T_baseinfo-logsMonitor-body'
	});
	
});