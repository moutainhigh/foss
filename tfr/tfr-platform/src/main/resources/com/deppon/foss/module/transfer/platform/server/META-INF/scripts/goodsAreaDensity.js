//外场库区密度 查询form
Ext.define('Foss.platform.goodsAreaDensity.goodsAreaDensityQueryForm', {
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
		readOnly : !Ext.isEmpty(platform.goodsAreaDensity.outfieldCode)
	}, {
		fieldLabel : '日期',
		name : 'date',
		allowBlank : false,
		xtype : 'datefield',
		format: 'Y-m-d',
		columnWidth : 1 / 4,
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
				//如果部门为外场，则外场重新赋值
				if(!Ext.isEmpty(platform.goodsAreaDensity.outfieldCode)){
					form.findField('outfieldCode').setCombValue(
							platform.goodsAreaDensity.outfieldName,
							platform.goodsAreaDensity.outfieldCode
					);
				}
				//填写日期
				form.findField('date').setValue(Ext.Date.format(new Date(), 'Y-m-d'));
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
				platform.goodsAreaDensity.goodsAreaDensityPagingBar.moveFirst();
			}
		} ]
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
});

//外场库区密度 列表之Model
Ext.define('Foss.platform.goodsAreaDensity.goodsAreaDensityModel', {
	extend : 'Ext.data.Model',
	//定义字段
	fields : [{
		name : 'headquartersName',
		type : 'string'
	},{
		name : 'businessUnitName',
		type : 'string'
	}, {
		name : 'transferCenterCode',
		type : 'string'
	}, {
		name : 'transferCenterName',
		type : 'string'
	}, {
		name : 'sumGoodsAreaVolume4Daily',
		type : 'string'
	}, {
		name : 'sumGoodsVolume4Daily',
		type : 'string'
	}, {
		name : 'avgDensity4Daily',
		type : 'string'
	}, {
		name : 'sumGoodsVolume4Monthly',
		type : 'string'
	}, {
		name : 'avgDensity4Monthly',
		type : 'string'
	}]
});

//外场库区密度 列表之store
Ext.define('Foss.platform.goodsAreaDensity.goodsAreaDensityStore', {
	extend : 'Ext.data.Store',
	pageSize : 20,
	// 绑定一个模型
	model : 'Foss.platform.goodsAreaDensity.goodsAreaDensityModel',
	// 定义一个代理对象
	proxy : {
		type : 'ajax',
		actionMethods:'POST',
		// 请求的url
		url : platform.realPath('selectPagingGoodsAreaDensity4Sum.action'),
		// 定义一个读取器
		reader : {
			// 以JSON的方式读取
			type : 'json',
			// 定义读取JSON数据的根对象
			root : 'goodsAreaDensityVo.goodsAreaDensity4SumDtos',
			successProperty: 'success',
			totalProperty : 'totalCount'
		}
	},
	listeners : {
		'beforeload' : function(store, operation, eOpts) {
			var queryForm = platform.goodsAreaDensity.queryForm.getForm();
			if (!Ext.isEmpty(queryForm)) {
				var queryParams = queryForm.getValues();
				Ext.apply(operation, {
					params : {
						'goodsAreaDensityVo.goodsAreaDensityQcDto.transferCenterCode' : queryParams.outfieldCode,
						'goodsAreaDensityVo.goodsAreaDensityQcDto.statisticDate' : queryParams.date
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

//外场库区密度 列表
Ext.define('Foss.platform.goodsAreaDensity.goodsAreaDensityGrid', {
	extend : 'Ext.grid.Panel',
	frame : true,
	columnLines: true,
	title : '库区密度信息',
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	emptyText : '查询结果为空',
	autoScroll : true,
	collapsible : true,
	animCollapse : true,
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.platform.goodsAreaDensity.goodsAreaDensityStore');
		
		me.bbar = Ext.create('Deppon.StandardPaging',{
			store : me.store,
			pageSize : 20,
			maximumSize : 50,
			plugins : Ext.create('Deppon.ux.PageSizePlugin', {
				sizeList : [['20', 20], ['30', 30], ['40', 40], ['50', 50]]
			})
		});
		platform.goodsAreaDensity.goodsAreaDensityPagingBar = me.bbar;
		me.callParent([cfg]);
	},
	tbar : [{
		xtype : 'button',
		text : '导出',
		handler : function(){
			var queryForm = platform.goodsAreaDensity.queryForm.getForm(),
				queryParams = queryForm.getValues();
			if(!queryForm.isValid()){
				Ext.ux.Toast.msg('提示', '请输入导出条件！', 'error', 1500);
				return;
			}
			var exportParams = {
					'goodsAreaDensityVo.goodsAreaDensityQcDto.transferCenterCode' : queryParams.outfieldCode,
					'goodsAreaDensityVo.goodsAreaDensityQcDto.statisticDate' : queryParams.date
			}; 
			if(!Ext.fly('downloadReturnRateFileForm')){
			    var frm = document.createElement('form');
			    frm.id = 'downloadReturnRateFileForm';
			    frm.style.display = 'none';
			    document.body.appendChild(frm);
			}
			
			Ext.Ajax.request({
				url : platform.realPath('exportGoodsAreaDensity4Sum.action'),
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
        xtype: 'rownumberer',
        text : '序号',
        align : 'center',
        width: 50,
        sortable: false
    },{
		dataIndex : 'headquartersName',
		align : 'center',
		flex : 1,
		xtype : 'ellipsiscolumn',
		text : '本部'
	},{
		dataIndex : 'businessUnitName',
		align : 'center',
		flex : 1,
		xtype : 'ellipsiscolumn',
		text : '事业部'
	}, {
		dataIndex : 'transferCenterName',
		align : 'center',
		flex : 1,
		xtype : 'ellipsiscolumn',
		text : '外场',
		renderer : function(value,metaData,record){
			if(value!=null){
				var parentTfrCtrCode = record.get('transferCenterCode');
				return '<a href="javascript:platform.goodsAreaDensity.addTab('+"'" + parentTfrCtrCode + "','"+ value + "'"+')">' + value + '</a>';
			}else{
				return value;
			}
		}
	},{
		dataIndex : 'sumGoodsAreaVolume4Daily',
		align : 'center',
		flex : 1,
		xtype : 'ellipsiscolumn',
		text : '库区总容量(F)'
	},{
		dataIndex : 'sumGoodsVolume4Daily',
		align : 'center',
		flex : 1,
		xtype : 'ellipsiscolumn',
		text : '当日库区总货量(F)'
	},{
		dataIndex : 'avgDensity4Daily',
		align : 'center',
		flex : 1,
		xtype : 'ellipsiscolumn',
		text : '当日库区密度',
		renderer : function(value){
			if(value != null){
				value = new String((value*100).toFixed(1));
				return value + '%';
			}else{
				return value;
			}
		}	
	}, {
		dataIndex : 'sumGoodsVolume4Monthly',
		align : 'center',
		flex : 1,
		xtype : 'ellipsiscolumn',
		text : '当月库区总货量(F)'		
	}, {
		dataIndex : 'avgDensity4Monthly',
		align : 'center',
		flex : 1,
		xtype : 'ellipsiscolumn',
		text : '当月库区密度',
		renderer : function(value){
			if(value != null){
				value = new String((value*100).toFixed(1));
				return value + '%';
			}else{
				return value;
			}
		}
	}],
});

//定义打开新tab方法
platform.goodsAreaDensity.addTab = function(parentTfrCtrCode,parentTfrCtrName){
	platform.goodsAreaDensity.mainPageOrgCode = parentTfrCtrCode;
	platform.goodsAreaDensity.mainPageOrgName = parentTfrCtrName;
	var mainTab = Ext.getCmp('mainAreaPanel'),
    	tab = Ext.getCmp('T_platform-goodsAreaDensityDetailIndex');
//	if(tab){
//		mainTab.remove(tab,true);
//		addTab('T_platform-goodsAreaDensityDetailIndex','库区密度明细',platform.realPath('goodsAreaDensityDetailIndex.action?parentTfrCtrCode="') + parentTfrCtrCode +'"&parentTfrCtrName="' + parentTfrCtrName + '"');
//	}else{
//		addTab('T_platform-goodsAreaDensityDetailIndex','库区密度明细',platform.realPath('goodsAreaDensityDetailIndex.action?parentTfrCtrCode="') + parentTfrCtrCode +'"&parentTfrCtrName="' + parentTfrCtrName + '"');
//	}
	if(tab){
		mainTab.remove(tab,true);
		addTab('T_platform-goodsAreaDensityDetailIndex','库区密度明细',platform.realPath('goodsAreaDensityDetailIndex.action'));
	}else{
		addTab('T_platform-goodsAreaDensityDetailIndex','库区密度明细',platform.realPath('goodsAreaDensityDetailIndex.action'));
	}
}

platform.goodsAreaDensity.queryForm = Ext.create('Foss.platform.goodsAreaDensity.goodsAreaDensityQueryForm');
platform.goodsAreaDensity.resultGrid = Ext.create('Foss.platform.goodsAreaDensity.goodsAreaDensityGrid')

//主页面
Ext.onReady(function() {
	Ext.create('Ext.panel.Panel',{
		id:'T_platform-goodsAreaDensityIndex_content',
		cls : "panelContentNToolbar",
		bodyCls : 'panelContentNToolbar-body',
		layout : 'auto',
		items : [platform.goodsAreaDensity.queryForm,
		         platform.goodsAreaDensity.resultGrid],
		renderTo: 'T_platform-goodsAreaDensityIndex-body'
	});
	
	//如果当前部门为外场，则自动填写外场
	if(!Ext.isEmpty(platform.goodsAreaDensity.outfieldCode)){
		platform.goodsAreaDensity.queryForm.getForm().findField('outfieldCode').setCombValue(
				platform.goodsAreaDensity.outfieldName,
				platform.goodsAreaDensity.outfieldCode
		);
	}
});