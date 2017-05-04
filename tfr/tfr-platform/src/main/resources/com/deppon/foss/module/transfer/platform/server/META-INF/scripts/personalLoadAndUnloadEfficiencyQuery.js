//操作类型store
Ext.define('Foss.platform.personalLoadAndUnloadEfficiency.HandleType.Store',{
	extend: 'Ext.data.Store',
	fields: [
		{name: 'code',  type: 'string'},
		{name: 'name',  type: 'string'}
	],
	proxy: {
		type: 'memory',
		reader: {
			type: 'json',
			root: 'items'
		}
	},
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

platform.personalLoadAndUnloadEfficiency.yesterday = new Date();
platform.personalLoadAndUnloadEfficiency.yesterday.setDate(platform.personalLoadAndUnloadEfficiency.yesterday.getDate() - 1 );


//日装卸效率查询form
Ext.define('Foss.platform.personalLoadAndUnloadEfficiency.dayEfficiencyQueryForm', {
	extend : 'Ext.form.Panel',
	title : '查询条件',
	frame : true,
	collapsible : true,
	animCollapse : true,
	defaults : {
		margin : '5 10 5 10',
		labelWidth : 85,
		columnWidth : 1 / 3,
		xtype : 'textfield'
	},
	layout : 'column',
	items : [{
		fieldLabel : '部门',
		name : 'loaderOrgCode',
		allowBlank : false,
		xtype : 'dynamicorgcombselector',
		type : 'ORG'
	}, {
		fieldLabel : '理货员',
		xtype: 'commonemployeeselector',
		name : 'loaderCode',
		parentOrgCode : platform.personalLoadAndUnloadEfficiency.superOrgCode
	}, {
		xtype: 'combobox',
		fieldLabel : '操作类型',
		name : 'handleType',
		displayField: 'name',
		valueField:'code', 
		queryMode:'local',
		triggerAction:'all',
		value:'ALL',
		editable:false,
		store: Ext.create('Foss.platform.personalLoadAndUnloadEfficiency.HandleType.Store',{
			data: {
				'items':[
					{'code':'ALL','name': '全部'},
					{'code':'LOAD','name': '装车'},
					{'code':'UNLOAD','name': '卸车'},
				]
			}
		})
	}, {
		fieldLabel : '日期',
		allowBlank : false,
		xtype : 'datefield',
		maxValue: platform.personalLoadAndUnloadEfficiency.yesterday,
		format: 'Y-m-d',
		value : platform.personalLoadAndUnloadEfficiency.yesterday,
		name : 'queryDate'
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
			text : '重置',
			handler : function() {
				var form = this.up('form').getForm();
				form.reset();
				
				var cmbOrgCode = form.findField('loaderOrgCode');
				cmbOrgCode.getStore().load({params:{'commonOrgVo.name' : FossUserContext.getCurrentDept().name}});
				cmbOrgCode.setValue(FossUserContext.getCurrentDept().code);
				
//				var queryDate = form.findField('queryDate');
//				queryDate.setValue(platform.personalLoadAndUnloadEfficiency.yesterday);
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
				if(!form.isValid()){
					Ext.ux.Toast.msg('提示', '请输入查询条件！', 'error', 1500);
					return;
				}
				platform.personalLoadAndUnloadEfficiency.dayPagingBar.moveFirst();
			}
		} ]
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	},
	listeners : {
		render : function(panel, text){
			var cmbOrgCode = panel.getForm().findField('loaderOrgCode');
			cmbOrgCode.getStore().load({params:{'commonOrgVo.name' : FossUserContext.getCurrentDept().name}});
			cmbOrgCode.setValue(FossUserContext.getCurrentDept().code);
		}
	}
});

//个人装卸车效率  日列表之Model
Ext.define('Foss.platform.personalLoadAndUnloadEfficiency.dayEfficiencyModel', {
	extend : 'Ext.data.Model',
	//定义字段
	fields : [{
		name : 'queryDate',
		type : 'date', 
		convert: function(value) {
			if (!Ext.isEmpty(value)) {
				var date = new Date(value);						
				return Ext.Date.format(date,'Y-m-d');
			} else {
				return null;
			}
		}
	},{
		name : 'loaderCode',
		type : 'string'
	}, {
		name : 'loaderName',
		type : 'string'
	}, {
		name : 'orgName',
		type : 'string'
	}, {
		name : 'loaderOrgName',
		type : 'string'
	}, {
		name : 'handleType',
		type : 'string'
	}, {
		name : 'weight',
		type : 'string'
	}, {
		name : 'duration',
		type : 'string'
	}, {
		name : 'efficiencyOfDay',
		type : 'string'
	}]
});

//个人装卸车效率  日列表之Store
Ext.define('Foss.platform.personalLoadAndUnloadEfficiency.dayEfficiencyStore', {
	extend : 'Ext.data.Store',
	pageSize : 20,
	// 绑定一个模型
	model : 'Foss.platform.personalLoadAndUnloadEfficiency.dayEfficiencyModel',
	// 定义一个代理对象
	proxy : {
		type : 'ajax',
		actionMethods:'POST',
		// 请求的url
		url : platform.realPath('queryLoaderEfficiencyByDay.action'),
		// 定义一个读取器
		reader : {
			// 以JSON的方式读取
			type : 'json',
			// 定义读取JSON数据的根对象
			root : 'loaderEfficiencyVo.loaderEfficiencys',
			successProperty: 'success',
			totalProperty : 'totalCount'
		}
	},
	listeners : {
		'beforeload' : function(store, operation, eOpts) {
			var queryForm = platform.personalLoadAndUnloadEfficiency.dayQueryForm.getForm();
			if (!Ext.isEmpty(queryForm)) {
				var queryParams = queryForm.getValues();
				Ext.apply(operation, {
					params : {
						'loaderEfficiencyVo.loaderEfficiency.loaderOrgCode' : queryParams.loaderOrgCode,
						'loaderEfficiencyVo.loaderEfficiency.loaderCode' : queryParams.loaderCode,
						'loaderEfficiencyVo.loaderEfficiency.handleType' : queryParams.handleType,
						'loaderEfficiencyVo.loaderEfficiency.queryDate' : queryParams.queryDate
					}
				});
			}
		}
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
});

//个人装卸车效率  日列表之Store
Ext.define('Foss.platform.personalLoadAndUnloadEfficiency.dayEfficiencyGrid', {
	extend : 'Ext.grid.Panel',
	frame : true,
	columnLines: true,
	title : '查询结果',
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	emptyText : '查询结果为空',
	autoScroll : true,
	collapsible : true,
	animCollapse : true,
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.platform.personalLoadAndUnloadEfficiency.dayEfficiencyStore');
		me.bbar = Ext.create('Deppon.StandardPaging',{
			store : me.store,
			pageSize : 20,
			maximumSize : 50,
			plugins : Ext.create('Deppon.ux.PageSizePlugin', {
				sizeList : [['20', 20], ['30', 30], ['40', 40], ['50', 50]]
			})
		});
		platform.personalLoadAndUnloadEfficiency.dayPagingBar = me.bbar;
		me.callParent([cfg]);
	},
	tbar : [{
		xtype : 'button',
		text : '导出',
		handler : function(){
			var queryForm = platform.personalLoadAndUnloadEfficiency.dayQueryForm.getForm();
				queryParams = queryForm.getValues();
			if(!queryForm.isValid()){
				Ext.ux.Toast.msg('提示', '请输入导出条件！', 'error', 1500);
				return;
			}
			var exportParams = {
					'loaderEfficiencyVo.loaderEfficiency.loaderOrgCode' : queryParams.loaderOrgCode,
					'loaderEfficiencyVo.loaderEfficiency.loaderCode' : queryParams.loaderCode,
					'loaderEfficiencyVo.loaderEfficiency.handleType' : queryParams.handleType,
					'loaderEfficiencyVo.loaderEfficiency.queryDate' : queryParams.queryDate
			}; 
			if(!Ext.fly('downloadReturnRateFileForm')){
				var frm = document.createElement('form');
				frm.id = 'downloadReturnRateFileForm';
				frm.style.display = 'none';
				document.body.appendChild(frm);
			}
			
			Ext.Ajax.request({
				url : platform.realPath('exportLoaderEfficiencyByDayExcel.action'),
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
		dataIndex : 'queryDate',
		align : 'center',
		flex : 1,
		xtype : 'ellipsiscolumn',
		text : '日期'
	},{
		dataIndex : 'loaderName',
		align : 'center',
		flex : 1,
		xtype : 'ellipsiscolumn',
		text : '理货员'
	}, {
		dataIndex : 'loaderCode',
		align : 'center',
		flex : 1,
		xtype : 'ellipsiscolumn',
		text : '工号'
	},{
		dataIndex : 'loaderOrgName',
		align : 'center',
		flex : 1,
		xtype : 'ellipsiscolumn',
		text : '部门名称'
	},{
		dataIndex : 'orgName',
		align : 'center',
		flex : 1,
		xtype : 'ellipsiscolumn',
		text : '队组别'
	},{
		dataIndex : 'handleType',
		align : 'center',
		flex : 1,
		text : '操作类型',
		renderer : function(value) {
			switch(value) {
				case 'LOAD':
					//装车
					return '装车';
				case 'UNLOAD':
					//卸车
					return '卸车';
				default: return value;
			}
		}
	},{
		dataIndex : 'weight',
		align : 'center',
		flex : 1,
		xtype : 'ellipsiscolumn',
		text : '重量(吨)'
	},{
		dataIndex : 'duration',
		align : 'center',
		flex : 1,
		xtype : 'ellipsiscolumn',
		text : '操作时长(小时)'
	},{
		dataIndex : 'efficiencyOfDay',
		align : 'center',
		flex : 1,
		xtype : 'ellipsiscolumn',
		text : '日均效率'
	}],
});

platform.personalLoadAndUnloadEfficiency.dayQueryForm = Ext.create('Foss.platform.personalLoadAndUnloadEfficiency.dayEfficiencyQueryForm');

platform.personalLoadAndUnloadEfficiency.dayResultGrid = Ext.create('Foss.platform.personalLoadAndUnloadEfficiency.dayEfficiencyGrid');

//个人装卸车效率-月均效率-查询form
Ext.define('Foss.platform.personalLoadAndUnloadEfficiency.monthEfficiencyQueryForm', {
	extend : 'Ext.form.Panel',
	title : '查询条件',
	frame : true,
	collapsible : true,
	animCollapse : true,
	defaults : {
		margin : '5 10 5 10',
		labelWidth : 85,
		columnWidth : 1 / 3,
		xtype : 'textfield'
	},
	layout : 'column',
	items : [{
		fieldLabel : '部门',
		name : 'loaderOrgCode',
		allowBlank : false,
		xtype : 'dynamicorgcombselector',
		type : 'ORG',
	}, {
		fieldLabel : '理货员',
		xtype: 'commonemployeeselector',
		name : 'loaderCode',
		parentOrgCode : platform.personalLoadAndUnloadEfficiency.superOrgCode
	}, {
		xtype: 'combobox',
		fieldLabel : '操作类型',
		name : 'handleType',
		displayField: 'name',
		valueField:'code', 
		queryMode:'local',
		triggerAction:'all',
		value:'ALL',
		editable:false,
		store: Ext.create('Foss.platform.personalLoadAndUnloadEfficiency.HandleType.Store',{
			data: {
				'items':[
					{'code':'ALL','name': '全部'},
					{'code':'LOAD','name': '装车'},
					{'code':'UNLOAD','name': '卸车'},
				]
			}
		})
	}, { 
		fieldLabel : '月份',
		allowBlank : false,
		xtype : 'monthdatefield',
		name : 'queryMonth',
		value : new Date()
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
			text : '重置',
			handler : function() {
				var form = this.up('form').getForm();
				form.reset();
				
				var cmbOrgCode = form.findField('loaderOrgCode');
				cmbOrgCode.getStore().load({params:{'commonOrgVo.name' : FossUserContext.getCurrentDept().name}});
				cmbOrgCode.setValue(FossUserContext.getCurrentDept().code);
				
//				var queryDate = form.findField('queryDate');
//				queryDate.setValue(platform.personalLoadAndUnloadEfficiency.yesterday);
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
				if(!form.isValid()){
					Ext.ux.Toast.msg('提示', '请输入查询条件！', 'error', 1500);
					return;
				}
				platform.personalLoadAndUnloadEfficiency.monthPagingBar.moveFirst();
			}
		} ]
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	},
	listeners : {
		render : function(panel, text){
			var cmbOrgCode = panel.getForm().findField('loaderOrgCode');
			cmbOrgCode.getStore().load({params:{'commonOrgVo.name' : FossUserContext.getCurrentDept().name}});
			cmbOrgCode.setValue(FossUserContext.getCurrentDept().code);
		}
	}
});

//个人装卸车效率-月均效率-查询列表Model
Ext.define('Foss.platform.personalLoadAndUnloadEfficiency.monthEfficiencyModel', {
	extend : 'Ext.data.Model',
	//定义字段
	fields : [{
		name : 'queryMonth',
		type : 'string'
	},{
		name : 'loaderCode',
		type : 'string'
	}, {
		name : 'loaderName',
		type : 'string'
	}, {
		name : 'orgName',
		type : 'string'
	}, {
		name : 'loaderOrgName',
		type : 'string'
	}, {
		name : 'handleType',
		type : 'string'
	}, {
		name : 'weight',
		type : 'string'
	}, {
		name : 'duration',
		type : 'string'
	}, {
		name : 'efficiencyOfMonth',
		type : 'string'
	}]
});

//个人装卸车效率-月均效率-查询列表store
Ext.define('Foss.platform.personalLoadAndUnloadEfficiency.monthEfficiencyStore', {
	extend : 'Ext.data.Store',
	pageSize : 20,
	// 绑定一个模型
	model : 'Foss.platform.personalLoadAndUnloadEfficiency.monthEfficiencyModel',
	// 定义一个代理对象
	proxy : {
		type : 'ajax',
		actionMethods:'POST',
		// 请求的url
		url : platform.realPath('queryLoaderEfficiencyByMonth.action'),
		// 定义一个读取器
		reader : {
			// 以JSON的方式读取
			type : 'json',
			// 定义读取JSON数据的根对象
			root : 'loaderEfficiencyVo.loaderEfficiencys',
			successProperty: 'success',
			totalProperty : 'totalCount'
		}
	},
	listeners : {
		'beforeload' : function(store, operation, eOpts) {
			var queryForm = platform.personalLoadAndUnloadEfficiency.monQueryForm.getForm();
			if (!Ext.isEmpty(queryForm)) {
				var queryParams = queryForm.getValues();
				Ext.apply(operation, {
					params : {
						'loaderEfficiencyVo.loaderEfficiency.loaderOrgCode' : queryParams.loaderOrgCode,
						'loaderEfficiencyVo.loaderEfficiency.loaderCode' : queryParams.loaderCode,
						'loaderEfficiencyVo.loaderEfficiency.handleType' : queryParams.handleType,
						'loaderEfficiencyVo.loaderEfficiency.queryMonth' : queryParams.queryMonth
					}
				});
			}
		}
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
});

//个人装卸车效率-月均效率-查询列表grid
Ext.define('Foss.platform.personalLoadAndUnloadEfficiency.monthEfficiencyGrid', {
	extend : 'Ext.grid.Panel',
	frame : true,
	columnLines: true,
	title : '查询结果',
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	emptyText : '查询结果为空',
	autoScroll : true,
	collapsible : true,
	animCollapse : true,
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.platform.personalLoadAndUnloadEfficiency.monthEfficiencyStore');
		me.bbar = Ext.create('Deppon.StandardPaging',{
			store : me.store,
			pageSize : 20,
			maximumSize : 50,
			plugins : Ext.create('Deppon.ux.PageSizePlugin', {
				sizeList : [['20', 20], ['30', 30], ['40', 40], ['50', 50]]
			})
		});
		platform.personalLoadAndUnloadEfficiency.monthPagingBar = me.bbar;
		me.callParent([cfg]);
	},
	tbar : [{
		xtype : 'button',
		text : '导出',
		handler : function(){
			var queryForm = platform.personalLoadAndUnloadEfficiency.monQueryForm.getForm();
			queryParams = queryForm.getValues();
		if(!queryForm.isValid()){
			Ext.ux.Toast.msg('提示', '请输入导出条件！', 'error', 1500);
			return;
		}
		var exportParams = {
				'loaderEfficiencyVo.loaderEfficiency.loaderOrgCode' : queryParams.loaderOrgCode,
				'loaderEfficiencyVo.loaderEfficiency.loaderCode' : queryParams.loaderCode,
				'loaderEfficiencyVo.loaderEfficiency.handleType' : queryParams.handleType,
				'loaderEfficiencyVo.loaderEfficiency.queryMonth' : queryParams.queryMonth
		}; 
		if(!Ext.fly('downloadReturnRateFileForm')){
			var frm = document.createElement('form');
			frm.id = 'downloadReturnRateFileForm';
			frm.style.display = 'none';
			document.body.appendChild(frm);
		}
		
		Ext.Ajax.request({
			url : platform.realPath('exportLoaderEfficiencyByMonthExcel.action'),
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
		dataIndex : 'queryMonth',
		align : 'center',
		flex : 1,
		xtype : 'ellipsiscolumn',
		text : '月份'
	},{
		dataIndex : 'loaderName',
		align : 'center',
		flex : 1,
		xtype : 'ellipsiscolumn',
		text : '理货员'
	}, {
		dataIndex : 'loaderCode',
		align : 'center',
		flex : 1,
		xtype : 'ellipsiscolumn',
		text : '工号'
	},{
		dataIndex : 'loaderOrgName',
		align : 'center',
		flex : 1,
		xtype : 'ellipsiscolumn',
		text : '部门名称'
	},{
		dataIndex : 'orgName',
		align : 'center',
		flex : 1,
		xtype : 'ellipsiscolumn',
		text : '队组别'
	},{
		dataIndex : 'handleType',
		align : 'center',
		flex : 1,
		xtype : 'ellipsiscolumn',
		text : '操作类型',
		renderer : function(value) {
			switch(value) {
				case 'LOAD':
					//装车
					return '装车';
				case 'UNLOAD':
					//卸车
					return '卸车';
				default: return value;
			}
		}
	},{
		dataIndex : 'weight',
		align : 'center',
		flex : 1,
		xtype : 'ellipsiscolumn',
		text : '重量(吨)'
	},{
		dataIndex : 'duration',
		align : 'center',
		flex : 1,
		xtype : 'ellipsiscolumn',
		text : '操作时长(小时)'
	},{
		dataIndex : 'efficiencyOfMonth',
		align : 'center',
		flex : 1,
		xtype : 'ellipsiscolumn',
		text : '月均效率'
	}],
});

//月效率查询form
platform.personalLoadAndUnloadEfficiency.monQueryForm = Ext.create('Foss.platform.personalLoadAndUnloadEfficiency.monthEfficiencyQueryForm');

//日库区密度查询结果Grid
platform.personalLoadAndUnloadEfficiency.monResultGrid = Ext.create('Foss.platform.personalLoadAndUnloadEfficiency.monthEfficiencyGrid');

//主页面
Ext.onReady(function() {
	Ext.tip.QuickTipManager.init();
	Ext.QuickTips.init();
	Ext.Ajax.request({
		url: platform.realPath('querySuperiorOrgByOrgCode.action'),
		async: false,
		success: function(response){
			var result = Ext.decode(response.responseText),
			loaderEfficiencyVo = result.loaderEfficiencyVo;
			platform.personalLoadAndUnloadEfficiency.superOrgCode = loaderEfficiencyVo.superOrgCode;
		},
		exception: function(response){
			var result = Ext.decode(response.responseText);
			Ext.ux.Toast.msg('提示', result.message);
		}
	});
	
	Ext.create('Ext.panel.Panel',{
		id:'T_platform-personalLoadAndUnloadEfficiencyIndex_content',
		cls : "panelContentNToolbar",
		bodyCls : 'panelContentNToolbar-body',
		layout: 'auto',
		items : [{
			xtype : 'tabpanel',
			frame : false,
			bodyCls : 'autoHeight',
			cls : 'innerTabPanel',
			items : [{
				title : '日均装卸车效率',
				tabConfig : {
					width : 120
				},
				items : [platform.personalLoadAndUnloadEfficiency.dayQueryForm,
				         platform.personalLoadAndUnloadEfficiency.dayResultGrid]
			},{
				title : '月均装卸车效率',
				tabConfig : {
					width : 120
				},
				items : [platform.personalLoadAndUnloadEfficiency.monQueryForm,
				         platform.personalLoadAndUnloadEfficiency.monResultGrid]
			}]
		}],
		renderTo: 'T_platform-personalLoadAndUnloadEfficiencyIndex-body'
	});
});