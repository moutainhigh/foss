/**
 * 零担场内货物流动轨迹界面，包含中转货物流动轨迹和派送货物流动轨迹2个页签
 * xiaohongye
 * 2015-04-10
 */
//中转货物流动轨迹查询实体model
Ext.define('Foss.platform.ltcGoodsTrack.ModelOfTfr',{
	extend : 'Ext.data.Model',
	fields : [{//外场编码
		name : 'tfrCtrCode',
		type : 'string'
	},{//外场名称 
		name : 'tfrCtrName',
		type : 'string'
	},{//运单号
		name : 'waybillNo',
		type : 'string'
	},{//下一部门编码
		name : 'nextOrgCode',
		type : 'string'
	},{//下一部门名称
		name : 'nextOrgName',
		type : 'string'
	},{//到达时间
		name : 'arrivedTime',
		type : 'date',
		convert : function(value) {
			if (value != null) {
				var date = new Date(value);
				return Ext.Date.format(date, 'Y-m-d H:i:s');
			} else {
				return null;
			}
		}
	},{// 卸车任务开始时间
		name : 'unloadTaskBeginTime',
		type : 'date',
		convert : function(value) {
			if (value != null) {
				var date = new Date(value);
				return Ext.Date.format(date, 'Y-m-d H:i:s');
			} else {
				return null;
			}
		}
	},{//卸车扫描时间
		name : 'unloadScanTime',
		type : 'date',
		convert : function(value) {
			if (value != null) {
				var date = new Date(value);
				return Ext.Date.format(date, 'Y-m-d H:i:s');
			} else {
				return null;
			}
		}
	},{//托盘绑定时间
		name : 'trayBindingTime',
		type : 'date',
		convert : function(value) {
			if (value != null) {
				var date = new Date(value);
				return Ext.Date.format(date, 'Y-m-d H:i:s');
			} else {
				return null;
			}
		}
	},{//叉车扫描时间
		name : 'forkliftScanTime',
		type : 'date',
		convert : function(value) {
			if (value != null) {
				var date = new Date(value);
				return Ext.Date.format(date, 'Y-m-d H:i:s');
			} else {
				return null;
			}
		}
	},{//入包装库区时间
		name : 'inPkgAreaTime',
		type : 'date',
		convert : function(value) {
			if (value != null) {
				var date = new Date(value);
				return Ext.Date.format(date, 'Y-m-d H:i:s');
			} else {
				return null;
			}
		}
	},{//出包装库区时间
		name : 'outPkgAreaTime',
		type : 'date',
		convert : function(value) {
			if (value != null) {
				var date = new Date(value);
				return Ext.Date.format(date, 'Y-m-d H:i:s');
			} else {
				return null;
			}
		}
	},{//入中转库区时间
		name : 'inTfrAreaTime',
		type : 'date',
		convert : function(value) {
			if (value != null) {
				var date = new Date(value);
				return Ext.Date.format(date, 'Y-m-d H:i:s');
			} else {
				return null;
			}
		}
	},{//中转装车扫描时间
		name : 'tfrLoadScanTime',
		type : 'date',
		convert : function(value) {
			if (value != null) {
				var date = new Date(value);
				return Ext.Date.format(date, 'Y-m-d H:i:s');
			} else {
				return null;
			}
		}
	},{//中转装车任务提交时间
		name : 'tfrLoadTaskSubmitTime',
		type : 'date',
		convert : function(value) {
			if (value != null) {
				var date = new Date(value);
				return Ext.Date.format(date, 'Y-m-d H:i:s');
			} else {
				return null;
			}
		}
	},{//统计日期
		name : 'staDate',
		type : 'date',
		convert : function(value) {
			if (value != null) {
				var date = new Date(value);
				return Ext.Date.format(date, 'Y-m-d H:i:s');
			} else {
				return null;
			}
		}
	}]
});
//派送货物流动轨迹查询实体model
Ext.define('Foss.platform.ltcGoodsTrack.ModelOfDpt',{
	extend : 'Ext.data.Model',
	fields : [{//外场编码
		name : 'tfrCtrCode',
		type : 'string'
	},{//外场名称 
		name : 'tfrCtrName',
		type : 'string'
	},{//运单号
		name : 'waybillNo',
		type : 'string'
	},{//上一部门编码
		name : 'preOrgCode',
		type : 'string'
	},{//上一部门名称
		name : 'preOrgName',
		type : 'string'
	},{//提货方式
		name : 'receiveMethod',
		type : 'string'
	},{//到达时间
		name : 'arrivedTime',
		type : 'date',
		convert : function(value) {
			if (value != null) {
				var date = new Date(value);
				return Ext.Date.format(date, 'Y-m-d H:i:s');
			} else {
				return null;
			}
		}
	},{// 卸车任务开始时间
		name : 'unloadTaskBeginTime',
		type : 'date',
		convert : function(value) {
			if (value != null) {
				var date = new Date(value);
				return Ext.Date.format(date, 'Y-m-d H:i:s');
			} else {
				return null;
			}
		}
	},{//卸车扫描时间
		name : 'unloadScanTime',
		type : 'date',
		convert : function(value) {
			if (value != null) {
				var date = new Date(value);
				return Ext.Date.format(date, 'Y-m-d H:i:s');
			} else {
				return null;
			}
		}
	},{//托盘绑定时间
		name : 'trayBindingTime',
		type : 'date',
		convert : function(value) {
			if (value != null) {
				var date = new Date(value);
				return Ext.Date.format(date, 'Y-m-d H:i:s');
			} else {
				return null;
			}		
		}
	},{//托盘扫描时间
		name : 'forkliftScanTime',
		type : 'date',
		convert : function(value) {
			if (value != null) {
				var date = new Date(value);
				return Ext.Date.format(date, 'Y-m-d H:i:s');
			} else {
				return null;
			}		
		}
	},{//入包装库区时间
		name : 'inPkgAreaTime',
		type : 'date',
		convert : function(value) {
			if (value != null) {
				var date = new Date(value);
				return Ext.Date.format(date, 'Y-m-d H:i:s');
			} else {
				return null;
			}		
		}
	},{//出包装库区时间
		name : 'outPkgAreaTime',
		type : 'date',
		convert : function(value) {
			if (value != null) {
				var date = new Date(value);
				return Ext.Date.format(date, 'Y-m-d H:i:s');
			} else {
				return null;
			}		
		}
	},{//入派送库区时间
		name : 'inDptAreaTime',
		type : 'date',
		convert : function(value) {
			if (value != null) {
				var date = new Date(value);
				return Ext.Date.format(date, 'Y-m-d H:i:s');
			} else {
				return null;
			}		
		}
	},{//派送装车时间
		name : 'dptLoadScanTime',
		type : 'date',
		convert : function(value) {
			if (value != null) {
				var date = new Date(value);
				return Ext.Date.format(date, 'Y-m-d H:i:s');
			} else {
				return null;
			}		
		}
	},{//派送装车任务提交时间
		name : 'dptLoadTaskSubmitTime',
		type : 'date',
		convert : function(value) {
			if (value != null) {
				var date = new Date(value);
				return Ext.Date.format(date, 'Y-m-d H:i:s');
			} else {
				return null;
			}		
		}
	},{//签收时间
		name : 'signTime',
		type : 'date',
		convert : function(value) {
			if (value != null) {
				var date = new Date(value);
				return Ext.Date.format(date, 'Y-m-d H:i:s');
			} else {
				return null;
			}	
		}
	},{//统计日期
		name : 'staDate',
		type : 'date',
		convert : function(value) {
			if (value != null) {
				var date = new Date(value);
				return Ext.Date.format(date, 'Y-m-d H:i:s');
			} else {
				return null;
			}
		}
	}]
});
//提货方式的model
Ext.define('Foss.platform.ltcGoodsTrack.modelOfReceiveMethod', {
	extend : 'Ext.data.Model',
	fields : [{
		name: 'code', 
		type: 'string'
	},{
		name: 'name',  
		type: 'string'
	}]
});
//提货方式的store
Ext.define('Foss.platform.ltcGoodsTrack.storeOfReceiveMethod',{
	extend : 'Ext.data.Store',
	model:'Foss.platform.ltcGoodsTrack.modelOfReceiveMethod',
	proxy : {
		type : 'ajax',
		actionMethods : 'POST',
		url : platform.realPath('findReceiveMethod.action'),
		reader : {
			type : 'json',
			root : 'stockDurationVo.receiveMethods'
		}
	},
	listeners : {
		load : function(store,records,successful,eOpts){
			//提货方式
			platform.ltcGoodsTrack.receiveMethod = store.data.items;
		}
	}
});

//零担中转货物流动轨迹界面的store
Ext.define('Foss.platform.ltcGoodsTrack.storeByLtcTfr',{
	extend : 'Ext.data.Store',
	model : 'Foss.platform.ltcGoodsTrack.ModelOfTfr',
	proxy : {
		type : 'ajax',
		actionMethods : 'POST',
		url : platform.realPath('findTfrLtc.action'),
		reader : {
			type : 'json',
			root : 'stockDurationVo.tfrLtcs',
			totalProperty : 'totalCount',
			successProperty : 'success'
		}
	},
	listeners : {
		beforeload : function(store,operation,eOpts) {
			//获得查询参数
			var queryForm = platform.ltcGoodsTrack.formByLtcTfr.getForm();
			var tfrCtrCode = queryForm.findField('queryCondition.tfrCtrCode').getValue();
			var waybillNo = queryForm.findField('queryCondition.waybillNo').getValue();
			var nextOrgCode = queryForm.findField('queryCondition.nextOrgCode').getValue();
			var staDate = queryForm.findField('queryCondition.staDate').getValue();
			var params = {
				'stockDurationVo.stockDurationQcDto.tfrCtrCode':tfrCtrCode,
				'stockDurationVo.stockDurationQcDto.staDate':staDate,
				'stockDurationVo.stockDurationQcDto.waybillNo':waybillNo,
				'stockDurationVo.stockDurationQcDto.nextOrgCode':nextOrgCode
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
//零担派送货物流动轨迹界面的store
Ext.define('Foss.platform.ltcGoodsTrack.storeByLtcDpt',{
	extend : 'Ext.data.Store',
	model : 'Foss.platform.ltcGoodsTrack.ModelOfDpt',
	proxy : {
		type : 'ajax',
		actionMethods : 'POST',
		url : platform.realPath('findDptLtc.action'),
		reader : {
			type : 'json',
			root : 'stockDurationVo.dptLtcs',
			totalProperty : 'totalCount',
			successProperty : 'success'
		}
	},
	listeners : {
		beforeload : function(store,operation,eOpts) {
			//获得查询参数
			var queryForm = platform.ltcGoodsTrack.formByLtcDpt.getForm();
			var tfrCtrCode = queryForm.findField('queryCondition.tfrCtrCode').getValue();
			var waybillNo = queryForm.findField('queryCondition.waybillNo').getValue();
			var receiveMethod = queryForm.findField('queryCondition.receiveMethod').getValue();
			var staDate = queryForm.findField('queryCondition.staDate').getValue();
			var params = {
				'stockDurationVo.stockDurationQcDto.tfrCtrCode':tfrCtrCode,
				'stockDurationVo.stockDurationQcDto.staDate':staDate,
				'stockDurationVo.stockDurationQcDto.waybillNo':waybillNo,
				'stockDurationVo.stockDurationQcDto.receiveMethod':receiveMethod
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
//中转货物流动轨迹查询条件
Ext.define('Foss.platform.ltcGoodsTrack.formByTfr', {
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
		allowBlank:false,
		columnWidth : 0.25,
		xtype : 'dynamicorgcombselector',
		type : 'ORG',
		transferCenter : 'Y',
		listeners : {
			beforerender : function(_this, eOpts ){
				//初始化外场信息
				platform.ltcGoodsTrack.initDeptInfo(_this);
			}
		}
	},{
		name : 'queryCondition.waybillNo',
		fieldLabel : '运单号',
		columnWidth : 0.25,
		allowBlank : true,
		xtype : 'textfield'
	},{
		name : 'queryCondition.nextOrgCode',
		fieldLabel : '下一线路',
		columnWidth : 0.25,
		allowBlank : true,
		xtype : 'dynamicorgcombselector',
		type : 'ORG'
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
				platform.ltcGoodsTrack.initDeptInfo(tfrCtrCode);
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
//派送货物流动轨迹查询条件
Ext.define('Foss.platform.ltcGoodsTrack.formByDpt', {
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
		allowBlank : false,
		xtype : 'dynamicorgcombselector',
		type : 'ORG',
		transferCenter : 'Y',
		listeners : {
			beforerender : function(_this, eOpts ){
				//初始化外场信息
				platform.ltcGoodsTrack.initDeptInfo(_this);
			}
		}
	},{
		name : 'queryCondition.waybillNo',
		fieldLabel : '运单号',
		columnWidth : 0.25,
		allowBlank : true,
		xtype : 'textfield'
	},{
		xtype : 'combobox',
    	fieldLabel:'提货方式',
    	//剔出朦层
		listConfig: {
		   loadMask:false
		},
    	queryMode:'local',
    	forceSelection :true,
    	store:Ext.create('Foss.platform.ltcGoodsTrack.storeOfReceiveMethod'),
    	displayField:'name',
		valueField:'code',
		columnWidth : 0.25,
		name : 'queryCondition.receiveMethod',
		listeners:{
			//使得combobox中的内容可删除
			change:function(combo){
				if(Ext.isEmpty(combo.getValue())){
					combo.setValue("");
				}
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
				platform.ltcGoodsTrack.initDeptInfo(tfrCtrCode);
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
//中转货物流动轨迹查询结果表格
Ext.define('Foss.platform.ltcGoodsTrack.gridByTfr', {
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
		header : '运单号',
		sortable: false,
		dataIndex : 'waybillNo',
		width:120
	},{
		xtype : 'ellipsiscolumn',
		header : '下一线路',
		sortable: false,
		align : 'center',
		dataIndex : 'nextOrgName',
		width:150
	},{
		xtype : 'ellipsiscolumn',
		header : '到达时间',
		sortable: false,
		align : 'center',
		dataIndex : 'arrivedTime',
		width:150
	},{
		xtype : 'ellipsiscolumn',
		align : 'center',
		header : '建立卸车任务时间',
		sortable: false,
		dataIndex : 'unloadTaskBeginTime',
		width:150
	},{
		xtype : 'ellipsiscolumn',
		header : '卸车扫描时间',
		sortable: false,
		align : 'center',
		dataIndex : 'unloadScanTime',
		width:150
	},{
		xtype : 'ellipsiscolumn',
		header : '托盘绑定时间',
		sortable: false,
		align : 'center',
		dataIndex : 'trayBindingTime',
		width:150
	},{
		xtype : 'ellipsiscolumn',
		header : '叉车扫描时间',
		sortable: false,
		align : 'center',
		dataIndex : 'forkliftScanTime',
		width:150
	},{
		xtype : 'ellipsiscolumn',
		header : '进入包装库区时间',
		sortable: false,
		align : 'center',
		dataIndex : 'inPkgAreaTime',
		width:150
	},{
		xtype : 'ellipsiscolumn',
		align : 'center',
		header : '登出包装库区时间',
		sortable: false,
		dataIndex : 'outPkgAreaTime',
		width:150
	},{
		xtype : 'ellipsiscolumn',
		header : '进入中转库区时间',
		sortable: false,
		align : 'center',
		dataIndex : 'inTfrAreaTime',
		width:150
	},{
		xtype : 'ellipsiscolumn',
		header : '中转装车扫描时间',
		sortable: false,
		align : 'center',
		dataIndex : 'tfrLoadScanTime',
		width:150
	},{
		xtype : 'ellipsiscolumn',
		header : '提交中转装车任务时间',
		sortable: false,
		align : 'center',
		dataIndex : 'tfrLoadTaskSubmitTime',
		width:150
	}],
	constructor : function(config) {
		var me = this;
		var cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.platform.ltcGoodsTrack.storeByLtcTfr');
		me.bbar = Ext.create('Deppon.StandardPaging',{
			store : me.store,
			pageSize : 50,
			maximumSize : 100,
			plugins : Ext.create('Deppon.ux.PageSizePlugin', {
				sizeList : [['25', 25], ['50', 50], ['100', 100]]
			})
		});
		me.callParent([cfg]);
	}
});

//派送货物流动轨迹查询结果表格
Ext.define('Foss.platform.ltcGoodsTrack.gridByDpt', {
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
		header : '运单号',
		sortable: false,
		dataIndex : 'waybillNo',
		width:120
	},{
		xtype : 'ellipsiscolumn',
		header : '提货方式',
		sortable: false,
		align : 'center',
		dataIndex : 'receiveMethod',
		width:150,
		renderer:function(val){
			return platform.ltcGoodsTrack.convertReceiveMethod(val);
		}
	},{
		xtype : 'ellipsiscolumn',
		header : '上一线路',
		sortable: false,
		align : 'center',
		dataIndex : 'preOrgName',
		width:150
	},{
		xtype : 'ellipsiscolumn',
		header : '到达时间',
		sortable: false,
		align : 'center',
		dataIndex : 'arrivedTime',
		width:150
	},{
		xtype : 'ellipsiscolumn',
		align : 'center',
		header : '建立卸车任务时间',
		sortable: false,
		dataIndex : 'unloadTaskBeginTime',
		width:150
	},{
		xtype : 'ellipsiscolumn',
		header : '卸车扫描时间',
		sortable: false,
		align : 'center',
		dataIndex : 'unloadScanTime',
		width:150
	},{
		xtype : 'ellipsiscolumn',
		header : '托盘绑定时间',
		sortable: false,
		align : 'center',
		dataIndex : 'trayBindingTime',
		width:150
	},{
		xtype : 'ellipsiscolumn',
		header : '叉车扫描时间',
		sortable: false,
		align : 'center',
		dataIndex : 'forkliftScanTime',
		width:150
	},{
		xtype : 'ellipsiscolumn',
		header : '进入包装库区时间',
		sortable: false,
		align : 'center',
		dataIndex : 'inPkgAreaTime',
		width:150
	},{
		xtype : 'ellipsiscolumn',
		align : 'center',
		header : '登出包装库区时间',
		sortable: false,
		dataIndex : 'outPkgAreaTime',
		width:150
	},{
		xtype : 'ellipsiscolumn',
		header : '进入派送库区时间',
		sortable: false,
		align : 'center',
		dataIndex : 'inDptAreaTime',
		width:150
	},{
		xtype : 'ellipsiscolumn',
		header : '派送装车扫描时间',
		sortable: false,
		align : 'center',
		dataIndex : 'dptLoadScanTime',
		width:150
	},{
		xtype : 'ellipsiscolumn',
		header : '提交派送装车任务时间',
		sortable: false,
		align : 'center',
		dataIndex : 'dptLoadTaskSubmitTime',
		width:150
	},{
		xtype : 'ellipsiscolumn',
		header : '签收出库时间',
		sortable: false,
		align : 'center',
		dataIndex : 'signTime',
		width:150
	}],
	constructor : function(config) {
		var me = this;
		var cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.platform.ltcGoodsTrack.storeByLtcDpt');
		me.bbar = Ext.create('Deppon.StandardPaging',{
			store : me.store,
			pageSize : 50,
			maximumSize : 100,
			plugins : Ext.create('Deppon.ux.PageSizePlugin', {
				sizeList : [['25', 25], ['50', 50], ['100', 100]]
			})
		});
		me.callParent([cfg]);
	}
});
//填充外场信息并设置外场combobox的readOnly属性
platform.ltcGoodsTrack.initDeptInfo = function(item){
	//初始化外场信息
	if(!Ext.isEmpty(platform.ltcGoodsTrack.outfieldCodeOfLtc)){
		//月台操作效率页签外场初始化
		item.setCombValue(
			platform.ltcGoodsTrack.outfieldOfLtc,
			platform.ltcGoodsTrack.outfieldCodeOfLtc
		);
		item.readOnly = true;
	}
}
//转换提货方式的code为相应的name
platform.ltcGoodsTrack.convertReceiveMethod = function(code){
	if(!Ext.isEmpty(code)){
		for(var i = 0; i < platform.ltcGoodsTrack.receiveMethod.length;i++){
			if(platform.ltcGoodsTrack.receiveMethod[i].data.code == code){
				return platform.ltcGoodsTrack.receiveMethod[i].data.name;
			}
		}
		return null;
	}
}
/** -----------------------------------------------页面显示view--------------------------------------------------* */
Ext.onReady(function() {
	Ext.QuickTips.init();
	//零担中转货物流动轨迹查询条件form
	platform.ltcGoodsTrack.formByLtcTfr = Ext.create('Foss.platform.ltcGoodsTrack.formByTfr');
	//零担中转货物流动轨迹查询结果表格
	platform.ltcGoodsTrack.gridByLtcTfr = Ext.create('Foss.platform.ltcGoodsTrack.gridByTfr');
	//零担派送货物流动轨迹查询条件form
	platform.ltcGoodsTrack.formByLtcDpt = Ext.create('Foss.platform.ltcGoodsTrack.formByDpt');
	//零担派送货物流动轨迹查询结果表格
	platform.ltcGoodsTrack.gridByLtcDpt = Ext.create('Foss.platform.ltcGoodsTrack.gridByDpt');
	
	//零担中转货物流动轨迹页签页面
	Ext.define('Foss.platform.ltcGoodsTrack.PanelByLtcTfr', {
		extend : 'Ext.panel.Panel',
		frame : false,
		layout : 'auto',
		constructor : function(config) {
			var me = this;
			var cfg = Ext.apply({}, config);
			//零担中转货物流动轨迹查询条件form
			var form = platform.ltcGoodsTrack.formByLtcTfr;
			//零担中转货物流动轨迹查询结果表格
			var grid = platform.ltcGoodsTrack.gridByLtcTfr;
			me.items = [form, grid];
			me.callParent([cfg]);
		}
	});
	//零担派送货物流动轨迹页签页面
	Ext.define('Foss.platform.ltcGoodsTrack.PanelByLtcDpt', {
		extend : 'Ext.panel.Panel',
		frame : false,
		layout : 'auto',
		constructor : function(config) {
			var me = this;
			var cfg = Ext.apply({}, config);
			//零担中转货物流动轨迹查询条件form
			var form = platform.ltcGoodsTrack.formByLtcDpt;
			//零担中转货物流动轨迹查询结果表格
			var grid = platform.ltcGoodsTrack.gridByLtcDpt;
			me.items = [form, grid];
			me.callParent([cfg]);
		}
	});
	/**
	 * 零担场内货物流动轨迹界面两个页签panel
	 */
	Ext.define('Foss.platform.ltcGoodsTrack.MainTabPanel', {
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
			title : '中转货物流动轨迹',
//			html:'中转货物流动轨迹'
			items : Ext.create('Foss.platform.ltcGoodsTrack.PanelByLtcTfr')
		}, {
			tabConfig : {
				width : 100
			},
			title : '派送货物流动轨迹',
//			html:'派送货物流动轨迹'
			items : Ext.create('Foss.platform.ltcGoodsTrack.PanelByLtcDpt')
		}]
	});
	platform.ltcGoodsTrack.MainTabPanel = Ext.create('Foss.platform.ltcGoodsTrack.MainTabPanel');
	Ext.create('Ext.panel.Panel', {
		id : 'T_platform-ltcGoodsTrack_content',
		cls : "panelContentNToolbar",
		bodyCls : 'panelContent-body',
		items : [platform.ltcGoodsTrack.MainTabPanel],
		renderTo : 'T_platform-ltcGoodsTrack-body'
	});
	//初始化提货方式
	platform.ltcGoodsTrack.formByLtcDpt.getForm().findField('queryCondition.receiveMethod').store.load();
});