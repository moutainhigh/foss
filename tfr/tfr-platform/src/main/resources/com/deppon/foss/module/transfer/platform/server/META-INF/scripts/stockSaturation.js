//仓库饱和度form
Ext.define('Foss.platform.stockSaturation.stockSaturationQueryForm', {
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
		fieldLabel : '外场',
		name : 'outfieldCode',
		xtype : 'dynamicorgcombselector',
		type : 'ORG',
		transferCenter : 'Y',
		readOnly : !Ext.isEmpty(platform.stockSaturation.outfieldCode)
	}, {
		fieldLabel : '日期',
		name : 'date',
		allowBlank : false,
		xtype : 'datefield',
		format: 'Y-m-d',
		columnWidth : 1 / 4,
		value:Ext.util.Format.date(Ext.Date.add(new Date(),Ext.Date.DAY,-1),"Y-m-d")

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
				//如果部门为外场，则外场重新赋值
				if(!Ext.isEmpty(platform.stockSaturation.outfieldCode)){
					form.findField('outfieldCode').setCombValue(
							platform.stockSaturation.outfieldName,
							platform.stockSaturation.outfieldCode
					);
				}
				//填写日期
				form.findField('date').setValue(Ext.Date.add(new Date(),Ext.Date.DAY,-1),"Y-m-d");
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
				platform.stockSaturation.resultGrid.store.load();
			}
		} ]
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
});
//仓库饱和度Model
Ext.define('Foss.platform.stockSaturation.stockSaturationModel', {
	extend : 'Ext.data.Model',
	//定义字段
	fields : [{
		name : 'bigdept',
		type : 'string'
	},{
		name : 'division',
		type : 'string'
	}, {
		name : 'orgName',
		type : 'string'
	}, {
		name : 'sustainDayMeasure',
		type : 'string'
	}, {
		name : 'operateMeasureDay',
		type : 'string'
	}, {
		name : 'saturationDay',
		type : 'string'
	}, {
		name : 'operateMeasureMonth',
		type : 'string'
	}, {
		name : 'saturationMonth',
		type : 'string'
	},{
		name:'orgCode',
		type:'string'
	},{
		name : 'warningMothDayCount',//当月预警天数
		type : 'string'
	},{
		name:'dangerMothDayCount',//当月危险预警天数
		type:'string'
	}]
});
//仓库饱和度 store
Ext.define('Foss.platform.stockSaturation.stockSaturationStore', {
	extend : 'Ext.data.Store',
	pageSize : 20,
	// 绑定一个模型
	model : 'Foss.platform.stockSaturation.stockSaturationModel',
	// 定义一个代理对象
	proxy : {
		type : 'ajax',
		actionMethods:'POST',
		// 请求的url
		url : platform.realPath('queryStockSaturation.action'),
		timeout:300000,
		// 定义一个读取器
		reader : {
			// 以JSON的方式读取
			type : 'json',
			// 定义读取JSON数据的根对象
			root : 'stockSaturationVo.stockSaturationList',
			successProperty: 'success',
			totalProperty : 'totalCount'
		}
	},
	listeners : {
		'beforeload' : function(store, operation, eOpts) {
			var queryForm = platform.stockSaturation.queryForm.getForm();
			if (!Ext.isEmpty(queryForm)) {
				var queryParams = queryForm.getValues();
				Ext.apply(operation, {
					params : {
						'stockSaturationVo.stockSaturationEntity.orgCode' : queryParams.outfieldCode,
						'stockSaturationVo.stockSaturationEntity.endDate' : queryParams.date
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
//仓库饱和度 列表
Ext.define('Foss.platform.stockSaturation.stockSaturationGrid', {
	extend : 'Ext.grid.Panel',
	frame : true,
	columnLines: true,
	title : '仓库饱和度',
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	emptyText : '查询结果为空',
	autoScroll : true,
	collapsible : true,
	animCollapse : true,
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.platform.stockSaturation.stockSaturationStore');
		
		me.bbar = Ext.create('Deppon.StandardPaging',{
			store : me.store,
			pageSize : 20,
			maximumSize : 50,
			plugins : Ext.create('Deppon.ux.PageSizePlugin', {
				sizeList : [['20', 20], ['30', 30], ['40', 40], ['50', 50]]
			})
		});
		platform.stockSaturation.totalPullbackRateGridPagingBar = me.bbar;
		me.callParent([cfg]);
	},
	tbar : [{
		xtype : 'button',
		text : '导出',
		handler : function(){
			var actionUrl=platform.realPath('stockSaturationExport.action');
			var queryForm = platform.stockSaturation.queryForm.getForm();
			if (!Ext.isEmpty(queryForm)) {
				var queryParams = queryForm.getValues();
				var exportParams = {
						'stockSaturationVo.stockSaturationEntity.orgCode' : queryParams.outfieldCode,
						'stockSaturationVo.stockSaturationEntity.endDate' : queryParams.date
				}; 
				if(!Ext.fly('downloadStockSaturationFileForm')){
								    var frm = document.createElement('form');
								    frm.id = 'downloadStockSaturationFileForm';
								    frm.style.display = 'none';
								    document.body.appendChild(frm);
				}
				
				Ext.Ajax.request({
				url:actionUrl,
				form: Ext.fly('downloadStockSaturationFileForm'),
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
		}
	}],
	columns : [{
        xtype: 'rownumberer',
        text : '序号',
        align : 'center',
        width: 50,
        sortable: false
    },{
		dataIndex : 'bigdept',
		align : 'center',
		flex : 1,
		xtype : 'ellipsiscolumn',
		text : '本部'
	},{
		dataIndex : 'division',
		align : 'center',
		flex : 1,
		xtype : 'ellipsiscolumn',
		text : '事业部'
	}, {
		dataIndex : 'orgName',
		align : 'center',
		flex : 1,
		xtype : 'ellipsiscolumn',
		text : '外场',
		renderer : function(value,metaData,record){
			if(value!=null){
				var parentTfrCtrCode = record.get('orgCode');
				return '<a href="javascript:platform.stockSaturation.addTab('+"'" + parentTfrCtrCode + "','"+ value + "'"+')">' + value + '</a>';
			}else{
				return value;
			}
		}
	},{
		dataIndex : 'sustainDayMeasure',
		align : 'center',
		flex : 1,
		xtype : 'ellipsiscolumn',
		text : '日承载货量(T)'
	},{
		dataIndex : 'operateMeasureDay',
		align : 'center',
		flex : 1,
		xtype : 'ellipsiscolumn',
		text : '当日操作货量(T)'
	},{
		dataIndex : 'saturationDay',
		align : 'center',
		flex : 1,
		xtype : 'ellipsiscolumn',
		text : '当日仓库饱和度',
		renderer : function(value){
			if(value != null){
				return value + '%';
			}else{
				return value;
			}
		}	
	}, {
		dataIndex : 'operateMeasureMonth',
		align : 'center',
		flex : 1,
		xtype : 'ellipsiscolumn',
		text : '当月操作货量(T)'		
	}, {
		dataIndex : 'saturationMonth',
		align : 'center',
		flex : 1,
		xtype : 'ellipsiscolumn',
		text : '当月仓库饱和度',
		renderer : function(value){
			if(value != null){
				return value + '%';
			}else{
				return value;
			}
		}
	},{
		dataIndex : 'warningMothDayCount',
		align : 'center',
		flex : 1,
		xtype : 'ellipsiscolumn',
		text : '当月预警天数'		
	},{
		dataIndex : 'dangerMothDayCount',
		align : 'center',
		flex : 1,
		xtype : 'ellipsiscolumn',
		text : '当月危险预警天数'		
	}],
});


//定义打开新tab方法
platform.stockSaturation.addTab = function(currentCode,parentCode){
	parentCode = platform.stockSaturation.outfieldCode;
	var mainTab = Ext.getCmp('mainStocksaturationPanel'),
    	tab = Ext.getCmp('T_platform-stockSaturationDetailQueryIndex');
	if(tab){
		if(mainTab){
			mainTab.remove(tab,true);
		}
		addTab('T_platform-stockSaturationDetailQueryIndex','仓库饱和度明细',platform.realPath('stockSaturationDetailQueryIndex.action?currentCode='+currentCode+"&currentName="+parentCode));
	}else{
		addTab('T_platform-stockSaturationDetailQueryIndex','仓库饱和度明细',platform.realPath('stockSaturationDetailQueryIndex.action?currentCode='+currentCode+"&currentName="+parentCode));
	}
};

//主页面
Ext.onReady(function() {
	Ext.QuickTips.init();
	Ext.Ajax.timeout=300000;
	
	var queryCreateForm = Ext.create('Foss.platform.stockSaturation.stockSaturationQueryForm');
	var resultCreateGrid = Ext.create('Foss.platform.stockSaturation.stockSaturationGrid');
	
	platform.stockSaturation.queryForm = queryCreateForm;
	platform.stockSaturation.resultGrid = resultCreateGrid;
	
	
	Ext.create('Ext.panel.Panel',{
		id:'T_platform-stockSaturationActionIndex_content',
		cls : "panelContentNToolbar",
		bodyCls : 'panelContentNToolbar-body',
		layout : 'auto',
		items : [platform.stockSaturation.queryForm,
		         platform.stockSaturation.resultGrid],
		renderTo: 'T_platform-stockSaturationActionIndex-body'
	});
	
	//如果当前部门为外场，则自动填写外场
	if(!Ext.isEmpty(platform.stockSaturation.outfieldCode)){
		platform.stockSaturation.queryForm.getForm().findField('outfieldCode').setCombValue(
				platform.stockSaturation.outfieldName,
				platform.stockSaturation.outfieldCode
		);
	}
});