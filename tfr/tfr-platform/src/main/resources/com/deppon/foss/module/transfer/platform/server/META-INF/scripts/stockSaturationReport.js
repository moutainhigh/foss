//仓库预警数据监控报表 查询form
Ext.define('Foss.platform.stockSaturationReport.querySaturationReportForm', {
	extend : 'Ext.form.Panel',
	title : '查询条件',
	frame : true,
	collapsible : true,
	animCollapse : true,
	defaults : {
		margin : '5 10 5 10',
		labelWidth : 85
		//columnWidth : 1 / 4,
		//xtype : 'textfield'
	},
	layout : 'column',
	items : [{
		name : 'queryDate',
		columnWidth : .25,
		xtype: 'datefield',
		fieldLabel:  '查询日期',
		allowBlank: false,
		dateType: 'datetimefield_date97',
		format: 'Y-m-d', 
		value: Ext.Date.format(new Date(new Date().getFullYear(),
				new Date().getMonth(), new Date().getDate()-2), 'Y-m-d'),
		maxValue: Ext.Date.format(new Date(new Date().getFullYear(),
										new Date().getMonth(), new Date().getDate()-2), 'Y-m-d')
	}, {
		columnWidth : .6,
		html : '&nbsp;'
	},{
		xtype : 'button',
		columnWidth : .1,
		cls : 'yellow_button',
		text : '查询',
		handler : function() {
			if(platform.stockSaturationReport.querySaturationReportForm.getForm().isValid()){
					platform.stockSaturationReport.querySaturationReportGrid.store.load();
				}
		}
	}, {
		border : false,
		columnWidth : .05,
		html : '&nbsp;'
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
});

//仓库预警数据监控报表 列表之Model
Ext.define('Foss.platform.stockSaturationReport.querySaturationReportModel', {
	extend : 'Ext.data.Model',
	//定义字段
	fields : [{
		name : 'bigdept',
		type : 'string'
	},{
		name : 'division',
		type : 'string'
	},{
		name : 'bigArea',
		type : 'string'
	},{
		name : 'orgName',
		type : 'string'
	},{
		name : 'queryDateB',
		type : 'string'
	},{
		name : 'saturationDay',
		type : 'string'
	},{
		name : 'saturationMonth',
		type : 'string'
	},{
		name : 'sendrateDay',
		type : 'string'
	},{
		name : 'sendrateMonth',
		type : 'string'
	},{
		name : 'pullbackDay',
		type : 'string'
	},{
		name : 'pullbackMonth',
		type : 'string'
	},{
		name : 'densityDay',
		type : 'string'
	},{
		name : 'densityMonth',
		type : 'string'
	},{
		name : 'returndateDay',
		type : 'string'
	},{
		name : 'returndateMoth',
		type : 'string'
	}]
});

//仓库预警数据监控报表 列表之store
Ext.define('Foss.platform.stockSaturationReport.querySaturationReportStore', {
	extend : 'Ext.data.Store',
	pageSize : 20,
	// 绑定一个模型
	model : 'Foss.platform.stockSaturationReport.querySaturationReportModel',
	// 定义一个代理对象
	proxy : {
		type : 'ajax',
		actionMethods:'POST',
		// 请求的url
		url : platform.realPath('queryStockSaturationReport.action'),
		timeout:300000,
		// 定义一个读取器
		reader : {
			// 以JSON的方式读取
			type : 'json',
			// 定义读取JSON数据的根对象
			root : 'stockSaturationReportVo.stockSaturationReportList',
			successProperty: 'success',
			totalProperty : 'totalCount'
		}
	},
	listeners: {
		beforeload : function(store, operation, eOpts) {
		   var valuesForm = platform.stockSaturationReport.querySaturationReportForm.getValues();
				Ext.apply(operation, {
					params : {
						'stockSaturationReportVo.stockSaturationReportDto.queryDateB':valuesForm.queryDate
					}
				});	
		},
		'load': function(records, operation, success){
			}
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
});

//仓库预警数据监控报表 列表
Ext.define('Foss.platform.stockSaturationReport.querySaturationReportGrid', {
	extend : 'Ext.grid.Panel',
	frame : true,
	columnLines: true,
	title : '仓库预警数据监控报表',
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
		me.store = Ext.create('Foss.platform.stockSaturationReport.querySaturationReportStore');
		
		me.bbar = Ext.create('Deppon.StandardPaging',{
			store : me.store,
			pageSize : 20,
			maximumSize : 50,
			plugins : Ext.create('Deppon.ux.PageSizePlugin', {
				sizeList : [['20', 20], ['30', 30], ['40', 40], ['50', 50]]
			})
		});
		me.callParent([cfg]);
	},
	tbar : [{
		xtype : 'button',
		text : '导出',
		handler: function(){
			var actionUrl=platform.realPath('exportStockSaturationReport.action');
			var valuesForm = platform.stockSaturationReport.querySaturationReportForm.getValues();
		
			var exportParams = {
					'stockSaturationReportVo.stockSaturationReportDto.queryDateB':valuesForm.queryDate
			}; 
			
			if(!Ext.fly('downloadSaturationReportFileForm')){
							    var frm = document.createElement('form');
							    frm.id = 'downloadSaturationReportFileForm';
							    frm.style.display = 'none';
							    document.body.appendChild(frm);
			}
			
			Ext.Ajax.request({
			url:actionUrl,
			form: Ext.fly('downloadSaturationReportFileForm'),
			method : 'POST',
			params : exportParams,
			isUpload: true,
			exception : function(response,opts) {
				var result = Ext.decode(response.responseText);
				Ext.MessageBox.alert('提示','导出失败',result.message);
			}	
			});
		}
	}],
	columns : [{
		dataIndex : 'bigdept',
		align : 'center',
		xtype : 'ellipsiscolumn',
		text : '本部'
	},{
		dataIndex : 'division',
		align : 'center',
		xtype : 'ellipsiscolumn',
		text : '事业部'
	},{
		dataIndex : 'bigArea',
		align : 'center',
		xtype : 'ellipsiscolumn',
		text : '大区'
	},{
		dataIndex : 'orgName',
		align : 'center',
		xtype : 'ellipsiscolumn',
		text : '转运场'
	},{
		dataIndex : 'queryDateB',
		align : 'center',
		xtype : 'ellipsiscolumn',
		text : '日期'
	},{
		text : '仓库饱和度',
		columns : [{
			dataIndex : 'saturationDay',
			align : 'center',
			xtype : 'ellipsiscolumn',
			text : '日数据',
			renderer : function(value){
				if(value != null){
					if(value.length==0){
						value='0';
					}
					return value + '%';
				}else{
					return value;
				}
			}	
		},{
			dataIndex : 'saturationMonth',
			align : 'center',
			xtype : 'ellipsiscolumn',
			text : '月累计',
			renderer : function(value){
				if(value != null){
					if(value.length==0){
						value='0';
					}
					return value + '%';
				}else{
					return value;
				}
			}	
		}]
	},{
		text : '派送率',
		columns : [{
			dataIndex : 'sendrateDay',
			align : 'center',
			xtype : 'ellipsiscolumn',
			text : '日数据',
			renderer : function(value){
				if(value != null){
					if(value.length==0){
						value='0';
					}
					return value + '%';
				}else{
					return value;
				}
			}	
		},{
			dataIndex : 'sendrateMonth',
			align : 'center',
			xtype : 'ellipsiscolumn',
			text : '月累计',
			renderer : function(value){
				if(value != null){
					if(value.length==0){
						value='0';
					}
					return value + '%';
				}else{
					return value;
				}
			}	
		}]
	},{
		text : '派送拉回率',
		columns : [{
			dataIndex : 'pullbackDay',
			align : 'center',
			xtype : 'ellipsiscolumn',
			text : '日数据',
			renderer : function(value){
				if(value != null){
					if(value.length==0){
						value='0';
					}
					return value + '%';
				}else{
					return value;
				}
			}	
		},{
			dataIndex : 'pullbackMonth',
			align : 'center',
			xtype : 'ellipsiscolumn',
			text : '月累计',
			renderer : function(value){
				if(value != null){
					if(value.length==0){
						value='0';
					}
					return value + '%';
				}else{
					return value;
				}
			}	
		}]
	},{
		text : '库区密度峰值',
		columns : [{
			dataIndex : 'densityDay',
			align : 'center',
			xtype : 'ellipsiscolumn',
			text : '日库区密度峰值',
			renderer : function(value){
				if(value != null){
					if(value.length==0){
						value='0';
					}
					return value + '%';
				}else{
					return value;
				}
			}	
		},{
			dataIndex : 'densityMonth',
			align : 'center',
			xtype : 'ellipsiscolumn',
			text : '月库区密度峰值',
			renderer : function(value){
				if(value != null){
					if(value.length==0){
						value='0';
					}
					return value + '%';
				}else{
					return value;
				}
			}	
		}]
	},{
		text : '退单率',
		columns : [{
			dataIndex : 'returndateDay',
			align : 'center',
			xtype : 'ellipsiscolumn',
			text : '日数据',
			renderer : function(value){
				if(value != null){
					if(value.length==0){
						value='0';
					}
					return value + '%';
				}else{
					return value;
				}
			}	
		},{
			dataIndex : 'returndateMoth',
			align : 'center',
			xtype : 'ellipsiscolumn',
			text : '月累计',
			renderer : function(value){
				if(value != null){
					if(value.length==0){
						value='0';
					}
					return value + '%';
				}else{
					return value;
				}
			}	
		}]
	}]
});

Ext.onReady(function() {
	Ext.QuickTips.init();
	Ext.Ajax.timeout=300000;
	//仓库预警数据监控报表的表单
	var querySaturationReportForm = Ext.create('Foss.platform.stockSaturationReport.querySaturationReportForm'); 
	
	//加入全局变更
	platform.stockSaturationReport.querySaturationReportForm = querySaturationReportForm;//仓库预警数据监控报表的表单
	
	//仓库预警数据监控报表表格
	var querySaturationReportGrid = Ext.create('Foss.platform.stockSaturationReport.querySaturationReportGrid');
	
	//加入全局变更
	platform.stockSaturationReport.querySaturationReportGrid = querySaturationReportGrid;
	
	Ext.create('Ext.panel.Panel',{
		id:'T_platform-stockSaturationReportIndex_content',
		cls : "panelContentNToolbar",
		bodyCls : 'panelContentNToolbar-body',
		layout : 'auto',
		items : [{
			xtype : 'tabpanel',
			frame : false,
			bodyCls : 'autoHeight',
			cls : 'innerTabPanel',
			items : [{
				title : '仓库预警数据监控报表',
				tabConfig : {
					width : 180
				},
				items : [querySaturationReportForm,querySaturationReportGrid]
			}]
		}],
		renderTo: 'T_platform-stockSaturationReportIndex-body'
	});
});