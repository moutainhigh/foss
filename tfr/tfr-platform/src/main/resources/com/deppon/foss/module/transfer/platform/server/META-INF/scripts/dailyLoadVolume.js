//查询form
Ext.define('Foss.platform.dailyLoadVolume.queryForm', {
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
		name : 'orgCode',
		xtype : 'dynamicorgcombselector',
		type : 'ORG',
		transferCenter : 'Y'
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
				platform.dailyLoadVolume.mainGridPagingBar.moveFirst();
			}
		} ]
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
});

//Model
Ext.define('Foss.platform.dailyLoadVolume.model', {
	extend : 'Ext.data.Model',
	//定义字段
	fields : [{
		name : 'id',
		type : 'string'
	},{
		name : 'orgCode',
		type : 'string'
	}, {
		name : 'orgName',
		type : 'string'
	}, {
		name : 'dailyLoadVolume',
		type : 'string'
	}, {
		name : 'fullValue',
		type : 'string'
	}, {
		name : 'dangerValue',
		type : 'string'
	}, {
		name : 'availabilityDate',
		type : 'date',
		convert: dateConvert
	}, {
		name : 'expiryDate',
		type : 'date',
		convert: dateConvert
	},{
		name : 'beNewest',
		type : 'string'
	}, {
		name : 'createUserCode',
		type : 'string'
	}, {
		name : 'createUserName',
		type : 'string'
	}, {
		name : 'modifyUserCode',
		type : 'string'
	}, {
		name : 'modifyUserName',
		type : 'string'
	} ,{
		name : 'createTime',
		type : 'date',
		convert: dateConvert
	}, {
		name : 'modifyTime',
		type : 'date',
		convert: dateConvert
	}]
});

//一级列表之store
Ext.define('Foss.platform.dailyLoadVolume.mainStore', {
	extend : 'Ext.data.Store',
	pageSize : 20,
	// 绑定一个模型
	model : 'Foss.platform.dailyLoadVolume.model',
	// 定义一个代理对象
	proxy : {
		type : 'ajax',
		actionMethods:'POST',
		// 请求的url
		url : platform.realPath('queryDailyLoadVolumeList.action'),
		// 定义一个读取器
		reader : {
			// 以JSON的方式读取
			type : 'json',
			// 定义读取JSON数据的根对象
			root : 'dailyLoadVolumeVo.dailyLoadVolumeEntityList',
			successProperty: 'success',
			totalProperty : 'totalCount'
		}
	},
	listeners: {
		beforeload : function(store, operation, eOpts) {
			var queryOrgCode = platform.dailyLoadVolume.queryForm.getForm().findField('orgCode').getValue();
			Ext.apply(operation, {
				params : {
					'dailyLoadVolumeVo.dailyLoadVolumeEntity.orgCode': queryOrgCode
				}
			});	
		}
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
});

//一级列表
Ext.define('Foss.platform.dailyLoadVolume.mainGrid', {
	extend : 'Ext.grid.Panel',
	frame : true,
	columnLines: true,
	title : '查询结果',
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	emptyText : '查询结果为空',
	enableColumnResize : false,//禁止调节列宽，因为要和二级表格表头对应
	enableColumnMove : false,//禁止挪动表头位置，理由同上。
	width : 1023,
	autoScroll : true,
	collapsible : true,
	sortableColumns : false,
	enableColumnHide : false,
	animCollapse : true,
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.platform.dailyLoadVolume.mainStore');
		
		me.bbar = Ext.create('Deppon.StandardPaging',{
			store : me.store,
			pageSize : 20,
			maximumSize : 50,
			plugins : Ext.create('Deppon.ux.PageSizePlugin', {
				sizeList : [['20', 20], ['30', 30], ['40', 40], ['50', 50]]
			})
		});
		platform.dailyLoadVolume.mainGridPagingBar = me.bbar;
		me.callParent([cfg]);
	},
	tbar : [{
		xtype : 'button',
		text : '新增',
		handler: function(){
			platform.dailyLoadVolume.initWindow('ADD');
		}
	}],
	columns : [{
        xtype: 'rownumberer',
        text : '序号',
        align : 'center',
        width: 35,
        sortable: false
	},{
		xtype : 'actioncolumn',
		width : 35,
		text : '操作',
		align : 'center',
		menuDisabled : true,
		hideable : false,
		sortable : false,
		items : [{
			tooltip : '修改',
			iconCls : 'deppon_icons_edit',
			handler : function(grid, rowIndex, colIndex) {
				var record = grid.store.getAt(rowIndex);
				platform.dailyLoadVolume.initWindow('UPDATE',record);
			}
		}]
	},{
		dataIndex : 'orgCode',
		align : 'center',
		flex : .1,
		xtype : 'ellipsiscolumn',
		text : '转运场编号'
	},{
		dataIndex : 'orgName',
		align : 'center',
		flex : .1,
		xtype : 'ellipsiscolumn',
		text : '转运场名称',
		renderer : function(value,metaData,record){
			if(value!=null){
				var orgCode = record.get('orgCode');
				return '<a href="javascript:platform.dailyLoadVolume.showViewWindow('+"'" + orgCode + "'" + ')">' + value + '</a>';
			}else{
				return value;
			}
		}
	},{
		dataIndex : 'dailyLoadVolume',
		align : 'center',
		flex : .1,
		xtype : 'ellipsiscolumn',
		text : '日承载货量(T)'
	}, {
		dataIndex : 'fullValue',
		align : 'center',
		flex : .1,
		xtype : 'ellipsiscolumn',
		text : '仓库饱和度预警值',
		renderer : function(value){
			return value + '%';
		}
	},{
		dataIndex : 'dangerValue',
		align : 'center',
		flex : .1,
		xtype : 'ellipsiscolumn',
		text : '仓库饱和度危险值',
		renderer : function(value){
			return value + '%';
		}
	},{
		dataIndex : 'availabilityDate',
		align : 'center',
		flex : .1,
		xtype : 'datecolumn',
		text : '生效日期',
		format : 'Y-m-d'
	},{
		dataIndex : 'expiryDate',
		align : 'center',
		flex : .1,
		xtype : 'datecolumn',
		text : '截止日期',
		format : 'Y-m-d'		
	}],
	plugins : [ {
		header: true,
		ptype : 'rowexpander',
		// 定义行展开模式（单行与多行），默认是多行展开(值true)
		rowsExpander : false,
		// 行体内容
		rowBodyElement : 'Foss.platform.dailyLoadVolume.subGrid',
		pluginId : 'Foss_dailyLoadVolume_subGrid_ID'
	}]
});
//弹出详情方法
platform.dailyLoadVolume.showViewWindow = function(orgCode){
	var record = platform.dailyLoadVolume.mainGrid.store.findRecord('orgCode', orgCode, 0, false,true,true);
	platform.dailyLoadVolume.initWindow('VIEW',record);
}

//历史记录之store
Ext.define('Foss.platform.dailyLoadVolume.subStore', {
	extend : 'Ext.data.Store',
	pageSize : 20,
	// 绑定一个模型
	model : 'Foss.platform.dailyLoadVolume.model',
	// 定义一个代理对象
	proxy : {
		type : 'ajax',
		actionMethods:'POST',
		// 请求的url
		url : platform.realPath('queryDailyLoadVolumeNoPaging.action'),
		// 定义一个读取器
		reader : {
			// 以JSON的方式读取
			type : 'json',
			// 定义读取JSON数据的根对象
			root : 'dailyLoadVolumeVo.dailyLoadVolumeEntityList',
			successProperty: 'success',
			totalProperty : 'totalCount'
		}
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
});

//二级列表
Ext.define('Foss.platform.dailyLoadVolume.subGrid', {
	extend : 'Ext.grid.Panel',
	columnLines: true,
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	emptyText : '无历史修改记录',
	sortableColumns : false,
	enableColumnHide : false,
	width : 695,
	hideHeaders : true,
	enableColumnResize : false,
	baseCls : 'dailyLoadVolume_queryHistory_historyGap',
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.platform.dailyLoadVolume.subStore');
		me.callParent([cfg]);
	},
	columns : [{
        xtype: 'rownumberer',
        text : '序号',
        align : 'center',
        width: 50,
        sortable: false
	},{
		dataIndex : 'dailyLoadVolume',
		align : 'center',
		flex : .1,
		xtype : 'ellipsiscolumn',
		text : '日承载货量(T)'
	}, {
		dataIndex : 'fullValue',
		align : 'center',
		flex : .1,
		xtype : 'ellipsiscolumn',
		text : '仓库饱和度预警值',
		renderer : function(value){
			return value + '%';
		}
	},{
		dataIndex : 'dangerValue',
		align : 'center',
		flex : .1,
		xtype : 'ellipsiscolumn',
		text : '仓库饱和度危险值',
		renderer : function(value){
			return value + '%';
		}
	},{
		dataIndex : 'availabilityDate',
		align : 'center',
		flex : .1,
		xtype : 'datecolumn',
		text : '生效日期',
		format : 'Y-m-d'
	},{
		dataIndex : 'expiryDate',
		align : 'center',
		flex : .1,
		xtype : 'datecolumn',
		text : '截止日期',
		format : 'Y-m-d'
	}],
	bindData : function(record){
		var orgCode = record.get('orgCode'),
			grid = this,
			store = grid.store;
		store.load({params : {
			'dailyLoadVolumeVo.dailyLoadVolumeEntity.orgCode' : orgCode
		}});
	}
});

//form
Ext.define('Foss.platform.dailyLoadVolume.viewForm', {
	extend : 'Ext.form.Panel',
	frame : true,
	width : 600,
	defaults : {
		margin : '5 10 5 10',
		labelWidth : 85,
		columnWidth : 1 / 2,
		xtype : 'hiddenfield'
	},
	layout : 'column',
	items : [{
		fieldLabel : '转运场名称',
		name : 'orgName',
		xtype : 'dynamicorgcombselector',
		type : 'ORG',
		allowBlank : false,
		transferCenter : 'Y',
		listeners : {
			'change' : function(field,newValue,oldValue,eOpts){
				if(newValue != null){
					var viewState = platform.dailyLoadVolume.viewWindow.viewState;
					if(viewState === 'ADD'){
						var form = this.up('form').getForm();
						form.findField('orgCode').setValue(newValue);
					}
				}
			}
		}
	}, {
		fieldLabel : '转运场编号',
		name : 'orgCode',
		readOnly : true,
		allowBlank : false,
		xtype : 'textfield'
	},{
		name : 'id'
	},{
		name : 'beNewest'
	}, {
		name : 'createUserCode'
	}, {
		name : 'createUserName'
	}, {
		name : 'modifyUserCode'
	}, {
		name : 'modifyUserName'
	}, {
		fieldLabel : '日承载货量(吨)',
		name : 'dailyLoadVolume',
		allowBlank : false,
		xtype : 'numberfield',
		step : 100,
		decimalPrecision : 1,
		maxValue : 9999999999.9
	},{
		fieldLabel : '仓库饱和度预警值',
		name : 'fullValue',
		allowBlank : false,
		xtype : 'numberfield',
		maxValue : 9999.99,
		decimalPrecision : 2
	},{
		fieldLabel : '仓库饱和度危险值',
		name : 'dangerValue',
		allowBlank : false,
		xtype : 'numberfield',
		maxValue : 9999.99,
		decimalPrecision : 2
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
});

//弹出窗口
Ext.define('Foss.platform.dailyLoadVolume.viewWindow', {
	extend : 'Ext.window.Window',
	closable : true,
	modal : true,
	resizable : false,
	closeAction : 'hide',
	form : Ext.create('Foss.platform.dailyLoadVolume.viewForm'),
	viewState : null,
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.fbar = [{
					text : '取消',
					handler : function() {
						me.hide();
					}
				}, {
					text : '重置',
					handler : function() {
						var form = me.form.getForm();
						//新增界面则全部清空，修改界面则清空三个数值字段
						if(me.viewState === 'ADD'){
							form.reset();
						}
						if(me.viewState === 'UPDATE'){
							form.findField('dailyLoadVolume').setValue(null);
							form.findField('dangerValue').setValue(null);
							form.findField('fullValue').setValue(null);
						}
					}
				}, {
					text : '保存',
					cls : 'yellow_button',
					handler : function() {
						var form = me.form.getForm();
						if(!form.isValid()){
							return;
						}
						var viewState = me.viewState,
							entity = form.getValues(),
							url = viewState ==='UPDATE' ? platform.realPath('updateDailyLoadVolumeEntity.action') : platform.realPath('addDailyLoadVolumeEntity.action');
						if(viewState === 'ADD'){
							var orgRecord = form.findField('orgName').store.findRecord('code', entity.orgCode, 0, false,true,true);
							entity.orgName = orgRecord.get('name');
						}
						Ext.Ajax.request({
							url : url,
							jsonData : {
								'dailyLoadVolumeVo' : {
									'dailyLoadVolumeEntity' : entity
								}
							},
							success : function(response) {
								Ext.ux.Toast.msg('提示', '保存成功！', 'info', 1500);
								//关闭窗口
								me.close();
								//刷新列表
								platform.dailyLoadVolume.mainGrid.store.load();
							},
							exception : function(response) {
								var result = Ext.decode(response.responseText);
								Ext.ux.Toast.msg('提示', '保存失败，' + result.message,
										'error', 2000);
							}
						});
					}
				}];
		me.items = [me.form];
		me.callParent([cfg]);
	}
});

//弹出前初始化窗口
platform.dailyLoadVolume.initWindow = function(viewState,record){
	//createwindow
	if(platform.dailyLoadVolume.viewWindow == null){
		platform.dailyLoadVolume.viewWindow = Ext.create('Foss.platform.dailyLoadVolume.viewWindow');
	}
	var win = platform.dailyLoadVolume.viewWindow,
		form = win.form.getForm(),
		buttons = win.query('button');
	win.viewState = viewState;
	//新增，清空控件值，除orgCode外其他字段均可编辑
	if(viewState === 'ADD'){
		//清空界面字段
		form.reset();
		win.setTitle('新增日承载货量信息');
		//字段可编辑
		form.getFields().each(function(field) {
			if (field.getName() !== 'orgCode') {
				field.setReadOnly(false);
			}
		});
		//按钮全部可见
		for (var i = 0; i < buttons.length; i++) {
			buttons[i].setVisible(true);
		}
	}else if(viewState === 'UPDATE'){
		win.setTitle('修改日承载货量信息');
		form.loadRecord(record);
		//字段可编辑
		form.getFields().each(function(field) {
			if (field.getName() === 'orgName'
					|| field.getName() === 'orgCode') {
				field.setReadOnly(true);
			}else{
				field.setReadOnly(false);
			}
		});
		//按钮全部可见
		for (var i = 0; i < buttons.length; i++) {
			buttons[i].setVisible(true);
		}
	}else if(viewState === 'VIEW'){
		win.setTitle('查看日承载货量信息');
		form.loadRecord(record);
		//字段可编辑
		form.getFields().each(function(field) {
			field.setReadOnly(true);
		});
		//按钮全部可见
		for (var i = 0; i < buttons.length; i++) {
			buttons[i].setVisible(false);
		}
	}
	platform.dailyLoadVolume.viewWindow.show();
}

//onready
Ext.onReady(function() {
	platform.dailyLoadVolume.queryForm = Ext.create('Foss.platform.dailyLoadVolume.queryForm');
	platform.dailyLoadVolume.mainGrid = Ext.create('Foss.platform.dailyLoadVolume.mainGrid');
	
	Ext.create('Ext.panel.Panel',{
		id:'T_platform-dailyLoadVolumeQueryIndex_content',
		cls : "panelContentNToolbar",
		bodyCls : 'panelContentNToolbar-body',
		layout : 'auto',
		items : [platform.dailyLoadVolume.queryForm,platform.dailyLoadVolume.mainGrid],
		renderTo: 'T_platform-dailyLoadVolumeQueryIndex-body'
	});
});