/**
 * 库存时长查询页面，包含四个页签，分别是：
 * 1 零担日均库存时长
 * 2 零担月均库存时长
 * 3 快递日均库存时长
 * 4 快递月均库存时长
 * 肖红叶
 * 2015-04-08
 */
//库存时长查询实体model
Ext.define('Foss.platform.stockDuration.Model',{
	extend : 'Ext.data.Model',
	fields : [{//外场编码
		name : 'tfrCtrCode',
		type : 'string'
	},{//外场名称 
		name : 'tfrCtrName',
		type : 'string'
	},{//平均卸车等待时长
		name : 'avgUnloadWaitTime',
		type:'float',
		convert : function(value) {
			if (value != null) {
				return Ext.util.Format.number(value,'0.00');
			} else {
				return null;
			}
		}
	},{//平均卸车时长
		name : 'avgUnloadTime',
		type:'float',
		convert : function(value) {
			if (value != null) {
				return Ext.util.Format.number(value,'0.00');
			} else {
				return null;
			}
		}
	},{//平均待叉区停留时长
		name : 'avgForkAreaStayTime',
		type:'float',
		convert : function(value) {
			if (value != null) {
				return Ext.util.Format.number(value,'0.00');
			} else {
				return null;
			}
		}
	},{//平均包装库区停留时长
		name : 'avgPkgAreaStayTime',
		type:'float',
		convert : function(value) {
			if (value != null) {
				return Ext.util.Format.number(value,'0.00');
			} else {
				return null;
			}
		}
	},{//平均中转库区停留时长
		name : 'avgTfrAreaTime',
		type:'float',
		convert : function(value) {
			if (value != null) {
				return Ext.util.Format.number(value,'0.00');
			} else {
				return null;
			}
		}
	},{//平均派送库区停留时长
		name : 'avgDptAreaTime',
		type:'float',
		convert : function(value) {
			if (value != null) {
				return Ext.util.Format.number(value,'0.00');
			} else {
				return null;
			}
		}
	}]
});
//零担日均库存时长的store
Ext.define('Foss.platform.stockDuration.storeByLtcDay',{
	extend : 'Ext.data.Store',
	model : 'Foss.platform.stockDuration.Model',
	proxy : {
		type : 'ajax',
		actionMethods : 'POST',
		url : platform.realPath('findLtcDay.action'),
		reader : {
			type : 'json',
			root : 'stockDurationVo.ltcDays',
			successProperty : 'success'
		}
	},
	listeners : {
		beforeload : function(store,operation,eOpts) {
			//获得查询参数
			var queryForm = platform.stockDuration.formByLtcDay.getForm();
			var tfrCtrCode = queryForm.findField('queryCondition.tfrCtrCode').getValue();
			var staDate = queryForm.findField('queryCondition.staDate').getValue();
			var params = {
				'stockDurationVo.stockDurationQcDto.tfrCtrCode':tfrCtrCode,
				'stockDurationVo.stockDurationQcDto.staDate':staDate
			};
			if(queryForm != null){
				Ext.apply(operation,{
					params : params
				});
			}
		},
		load : function(store,records,successful,eOpts){
			if(!store.proxy.reader.rawData.success){
				Ext.ux.Toast.msg('提示', '查询失败，' + store.proxy.reader.rawData.message, 'error');
			}
		}
	}
});

//零担月均库存时长的store
Ext.define('Foss.platform.stockDuration.storeByLtcMonth',{
	extend : 'Ext.data.Store',
	model : 'Foss.platform.stockDuration.Model',
	proxy : {
		type : 'ajax',
		actionMethods : 'POST',
		url : platform.realPath('findLtcMonth.action'),
		reader : {
			type : 'json',
			root : 'stockDurationVo.ltcMonths',
			successProperty : 'success'
		}
	},
	listeners : {
		beforeload : function(store,operation,eOpts) {
			//获得查询参数
			var queryForm = platform.stockDuration.formByLtcMonth.getForm();
			var tfrCtrCode = queryForm.findField('queryCondition.tfrCtrCode').getValue();
			var staDate = queryForm.findField('queryCondition.staDate').getValue();
			var params = {
				'stockDurationVo.stockDurationQcDto.tfrCtrCode':tfrCtrCode,
				'stockDurationVo.stockDurationQcDto.staDate':staDate
			};
			if(queryForm != null){
				Ext.apply(operation,{
					params : params
				});
			}
		},
		load : function(store,records,successful,eOpts){
			if(!store.proxy.reader.rawData.success){
				Ext.ux.Toast.msg('提示', '查询失败，' + store.proxy.reader.rawData.message, 'error');
			}
		}
	}
});

//快递日均库存时长的store
Ext.define('Foss.platform.stockDuration.storeByExpDay',{
	extend : 'Ext.data.Store',
	model : 'Foss.platform.stockDuration.Model',
	proxy : {
		type : 'ajax',
		actionMethods : 'POST',
		url : platform.realPath('findExpDay.action'),
		reader : {
			type : 'json',
			root : 'stockDurationVo.expDays',
			successProperty : 'success'
		}
	},
	listeners : {
		beforeload : function(store,operation,eOpts) {
			//获得查询参数
			var queryForm = platform.stockDuration.formByExpDay.getForm();
			var tfrCtrCode = queryForm.findField('queryCondition.tfrCtrCode').getValue();
			var staDate = queryForm.findField('queryCondition.staDate').getValue();
			var params = {
				'stockDurationVo.stockDurationQcDto.tfrCtrCode':tfrCtrCode,
				'stockDurationVo.stockDurationQcDto.staDate':staDate
			};
			if(queryForm != null){
				Ext.apply(operation,{
					params : params
				});
			}
		},
		load : function(store,records,successful,eOpts){
			if(!store.proxy.reader.rawData.success){
				Ext.ux.Toast.msg('提示', '查询失败，' + store.proxy.reader.rawData.message, 'error');
			}
		}
	}
});

//快递月均库存时长的store
Ext.define('Foss.platform.stockDuration.storeByExpMonth',{
	extend : 'Ext.data.Store',
	model : 'Foss.platform.stockDuration.Model',
	proxy : {
		type : 'ajax',
		actionMethods : 'POST',
		url : platform.realPath('findExpMonth.action'),
		reader : {
			type : 'json',
			root : 'stockDurationVo.expMonths',
			successProperty : 'success'
		}
	},
	listeners : {
		beforeload : function(store,operation,eOpts) {
			//获得查询参数
			var queryForm = platform.stockDuration.formByExpMonth.getForm();
			var tfrCtrCode = queryForm.findField('queryCondition.tfrCtrCode').getValue();
			var staDate = queryForm.findField('queryCondition.staDate').getValue();
			var params = {
				'stockDurationVo.stockDurationQcDto.tfrCtrCode':tfrCtrCode,
				'stockDurationVo.stockDurationQcDto.staDate':staDate
			};
			if(queryForm != null){
				Ext.apply(operation,{
					params : params
				});
			}
		},
		load : function(store,records,successful,eOpts){
			if(!store.proxy.reader.rawData.success){
				Ext.ux.Toast.msg('提示', '查询失败，' + store.proxy.reader.rawData.message, 'error');
			}
		}
	}
});

//库存时长查询条件
Ext.define('Foss.platform.stockDuration.form', {
	extend : 'Ext.form.Panel',
	title : '查询条件',
	frame : true,
	layout : 'column',
	defaults : {
		columns : 4,
		margin : '5 5 5 5'
	},
	items : [{
		name : 'queryCondition.tfrCtrCode',
		fieldLabel : '外场',
		columnWidth : 0.25,
		allowBlank : true,
		xtype : 'dynamicorgcombselector',
		type : 'ORG',
		transferCenter : 'Y',
		listeners : {
			beforerender : function(_this, eOpts ){
				//初始化外场信息
				platform.stockDuration.initDeptInfo(_this);
			}
		}
	},{
		xtype:'datefield',
    	fieldLabel:'日期',
    	allowBlank:false,
		name:'queryCondition.staDate',
		editable:false,
		value: Ext.Date.add(login.currentServerTime, Ext.Date.DAY, -1),
		maxValue:Ext.Date.add(login.currentServerTime, Ext.Date.DAY, -1),
		format:'Y-m-d',
		columnWidth:.25
	},{
		xtype : 'container',
		columnWidth : .25,
		html : '&nbsp;'
	},{
		xtype : 'container',
		columnWidth : .25,
		html : '&nbsp;'
	},{
		border : 1,
		xtype : 'container',
		columnWidth : 1,
		defaultType : 'button',
		layout : 'column',
		items : [{
			text : '重置',
			columnWidth : .08,
			handler : function(th) {
				//获得查询条件form
				var form = th.up('form').getForm();
				//重置查询条件form
				form.reset();
				//获得查询条件的外场
				var tfrCtrCode = form.findField('queryCondition.tfrCtrCode');
				//初始化外场信息
				platform.stockDuration.initDeptInfo(tfrCtrCode);
			}
		},{
			xtype : 'container',
			columnWidth : .80,
			html : '&nbsp;'
		},{
			text : '查询',
			cls : 'yellow_button',
			columnWidth : .08,
			handler : function(th) {
				//获得查询条件form
				var form = th.up('form').getForm();
				if (!form.isValid()) {
					Ext.Msg.alert('提示','查询失败，请输入有效条件!');
					return false;
				}
				//加载数据
				th.up('form').up('panel').down('grid').store.load();
			}
		}]
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

//库存时长查询结果表格
Ext.define('Foss.platform.stockDuration.grid', {
	extend : 'Ext.grid.Panel',
	frame : true,
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	enableColumnHide : false,
	height:400,
	autoScroll : true,
	defaults : {
		align : 'center'
	},
	columns : [{
        xtype: 'rownumberer',
        text : '序号',
        align : 'center',
        width: 50,
        sortable: false
    },{
		xtype : 'ellipsiscolumn',
		align : 'center',
		header : '外场',
		sortable: false,
		dataIndex : 'tfrCtrName',
		width:120
	},{
		xtype : 'ellipsiscolumn',
		header : '卸车等待时长(h)',
		sortable: false,
		align : 'center',
		dataIndex : 'avgUnloadWaitTime',
		width:150
	},{
		xtype : 'ellipsiscolumn',
		header : '卸车时长(h)',
		sortable: false,
		align : 'center',
		dataIndex : 'avgUnloadTime',
		width:120
	},{
		xtype : 'ellipsiscolumn',
		align : 'center',
		header : '待叉区停留时长(h)',
		sortable: false,
		dataIndex : 'avgForkAreaStayTime',
		width:150
	},{
		xtype : 'ellipsiscolumn',
		header : '包装库区停留时长(h)',
		sortable: false,
		align : 'center',
		dataIndex : 'avgPkgAreaStayTime',
		width:150
	},{
		xtype : 'ellipsiscolumn',
		header : '中转库区停留时长(h)',
		sortable: false,
		align : 'center',
		dataIndex : 'avgTfrAreaTime',
		width:150
	},{
		xtype : 'ellipsiscolumn',
		header : '派送库区停留时长(h)',
		sortable: false,
		align : 'center',
		dataIndex : 'avgDptAreaTime',
		width:150
	}],
	constructor : function(config) {
		var me = this;
		var cfg = Ext.apply({}, config);
		me.store = null;
		me.tbar = [{
			xtype : 'button',
			text : '导出',
			handler : function(){
				//判断是否有数据可供导出
				if (me.store.data.length === 0) {
					Ext.ux.Toast.msg('提示', '列表中无可供导出的数据！', 'error', 2000);
					return;
				}
				//根据store的名称判断导出属于哪个页签
				var url = null;
				if('platform.stockDuration.storeByLtcDay' === me.store.name){
					url = platform.realPath('exportLtcDay.action');
				}else if('platform.stockDuration.storeByExpDay' === me.store.name){
					url = platform.realPath('exportExpDay.action');
				}else if('platform.stockDuration.storeByLtcMonth' === me.store.name){
					url = platform.realPath('exportLtcMonth.action');
				}else if('platform.stockDuration.storeByExpMonth' === me.store.name){
					url = platform.realPath('exportExpMonth.action');
				}
				//获得查询条件form
				var queryForm = me.up('panel').down('form').getForm();
				var tfrCtrCode = queryForm.findField('queryCondition.tfrCtrCode').getValue();
				var staDate = queryForm.findField('queryCondition.staDate').getValue();
				var params = {
					'stockDurationVo.stockDurationQcDto.tfrCtrCode':tfrCtrCode,
					'stockDurationVo.stockDurationQcDto.staDate':staDate
				};
//				创建附件导出提示窗体
				if (!Ext.fly('downloadAttachFileForm')) {
					var frm = document.createElement('form');
					frm.id = 'downloadAttachFileForm';
					frm.style.display = 'none';
					document.body.appendChild(frm);
				}
				//向后端发送请求
				Ext.Ajax.request({
					url : url,
					form : Ext.fly('downloadAttachFileForm'),
					method : 'POST',
					params : params,
					isUpload : true,
					success : function(response) {

					},
					exception : function(response) {
						top.Ext.MessageBox.alert('导出失败', result.message);
					}
				});
			}
		}];
		me.callParent([cfg]);
	}
});
//创建零担日均库存时长查询条件form
platform.stockDuration.formByLtcDay = Ext.create('Foss.platform.stockDuration.form');
//创建快递日均库存时长查询条件form
platform.stockDuration.formByExpDay = Ext.create('Foss.platform.stockDuration.form');
//创建零担月均库存时长查询条件form
platform.stockDuration.formByLtcMonth = Ext.create('Foss.platform.stockDuration.form');
//创建快递月均库存时长查询条件form
platform.stockDuration.formByExpMonth = Ext.create('Foss.platform.stockDuration.form');

//创建零担日均库存时长查询结果表格
platform.stockDuration.gridByLtcDay =Ext.create('Foss.platform.stockDuration.grid',{
	store:Ext.create('Foss.platform.stockDuration.storeByLtcDay',{
		name:'platform.stockDuration.storeByLtcDay'
	})
}) ;
//创建快递日均库存时长查询结果表格
platform.stockDuration.gridByExpDay =Ext.create('Foss.platform.stockDuration.grid',{
	store:Ext.create('Foss.platform.stockDuration.storeByExpDay',{
		name:'platform.stockDuration.storeByExpDay'
	})
}) ;
//创建零担月均库存时长查询结果表格
platform.stockDuration.gridByLtcMonth =Ext.create('Foss.platform.stockDuration.grid',{
	store:Ext.create('Foss.platform.stockDuration.storeByLtcMonth',{
		name:'platform.stockDuration.storeByLtcMonth'
	})
}) ;
//创建快递月均库存时长查询结果表格
platform.stockDuration.gridByExpMonth =Ext.create('Foss.platform.stockDuration.grid',{
	store:Ext.create('Foss.platform.stockDuration.storeByExpMonth',{
		name:'platform.stockDuration.storeByExpMonth'
	})
}) ;

//零担日均库存时长页签页面
Ext.define('Foss.platform.stockDuration.PanelByLtcDay', {
	extend : 'Ext.panel.Panel',
	frame : false,
	layout : 'auto',
	constructor : function(config) {
		var me = this;
		var cfg = Ext.apply({}, config);
		//创建零担日均库存时长查询结果表格
		var gridByLtcDay = platform.stockDuration.gridByLtcDay;
		//创建零担日均库存时长查询条件
		var formByLtcDay = platform.stockDuration.formByLtcDay;
		me.items = [formByLtcDay, gridByLtcDay];
		me.callParent([cfg]);
	}
});
//快递日均库存时长页签页面
Ext.define('Foss.platform.stockDuration.PanelByExpDay', {
	extend : 'Ext.panel.Panel',
	frame : false,
	layout : 'auto',
	constructor : function(config) {
		var me = this;
		var cfg = Ext.apply({}, config);
		//创建零担日均库存时长查询结果表格
		var gridByExpDay = platform.stockDuration.gridByExpDay;
		//创建零担日均库存时长查询条件
		var formByExpDay = platform.stockDuration.formByExpDay;
		me.items = [formByExpDay, gridByExpDay];
		me.callParent([cfg]);
	}
});
//零担月均库存时长页签页面
Ext.define('Foss.platform.stockDuration.PanelByLtcMonth', {
	extend : 'Ext.panel.Panel',
	frame : false,
	layout : 'auto',
	constructor : function(config) {
		var me = this;
		var cfg = Ext.apply({}, config);
		//创建零担日均库存时长查询结果表格
		var gridByLtcMonth = platform.stockDuration.gridByLtcMonth;
		//创建零担日均库存时长查询条件
		var formByLtcMonth = platform.stockDuration.formByLtcMonth;
		me.items = [formByLtcMonth, gridByLtcMonth];
		me.callParent([cfg]);
	}
});
//快递月均库存时长页签页面
Ext.define('Foss.platform.stockDuration.PanelByExpMonth', {
	extend : 'Ext.panel.Panel',
	frame : false,
	layout : 'auto',
	constructor : function(config) {
		var me = this;
		var cfg = Ext.apply({}, config);
		//创建零担日均库存时长查询结果表格
		var gridByExpMonth = platform.stockDuration.gridByExpMonth;
		//创建零担日均库存时长查询条件
		var formByExpMonth = platform.stockDuration.formByExpMonth;
		me.items = [formByExpMonth, gridByExpMonth];
		me.callParent([cfg]);
	}
});


/*------------------------------------------------*/
//填充外场信息并设置外场combobox的readOnly属性
platform.stockDuration.initDeptInfo = function(item){
	//初始化外场信息
	if(!Ext.isEmpty(platform.stockDuration.outfieldCode)){
		//月台操作效率页签外场初始化
		item.setCombValue(
			platform.stockDuration.outfield,
			platform.stockDuration.outfieldCode
		);
		item.readOnly = true;
	}
}
/** -----------------------------------------------页面显示view--------------------------------------------------* */
Ext.onReady(function() {
	Ext.QuickTips.init();
	/**
	 * 库存时长四个页签panel
	 */
	Ext.define('Foss.platform.stockDuration.MainTabPanel', {
		extend : 'Ext.tab.Panel',
		cls : "innerTabPanel",
		bodyCls : "overrideChildLeft",
		activeTab : 0,
		autoScroll : false,
		frame : false,
		items : [{
			tabConfig : {
				width : 100
			},
			title : '零担日均库存时长',
			items : Ext.create('Foss.platform.stockDuration.PanelByLtcDay')
		}, {
			tabConfig : {
				width : 100
			},
			title : '零担月均库存时长',
			items : Ext.create('Foss.platform.stockDuration.PanelByLtcMonth')
		},{
			tabConfig : {
				width : 100
			},
			title : '快递日均库存时长',
			items : Ext.create('Foss.platform.stockDuration.PanelByExpDay')
		}, {
			tabConfig : {
				width : 100
			},
			title : '快递月均库存时长',
			items : Ext.create('Foss.platform.stockDuration.PanelByExpMonth')
		}]
	});
	platform.stockDuration.MainTabPanel = Ext.create('Foss.platform.stockDuration.MainTabPanel');
	Ext.create('Ext.panel.Panel', {
		id : 'T_platform-stockDuration_content',
		cls : "panelContentNToolbar",
		bodyCls : 'panelContent-body',
//		html:'库存时长查询页面',
		items : [platform.stockDuration.MainTabPanel],
		renderTo : 'T_platform-stockDuration-body'
	});
});