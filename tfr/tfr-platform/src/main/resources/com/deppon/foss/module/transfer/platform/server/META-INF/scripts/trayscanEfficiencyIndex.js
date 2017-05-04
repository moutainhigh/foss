/**
*
*托盘绑定效率查询界面
*
*/
Ext.define('Foss.platform.trayscanEfficiencyIndex.ModelDetail',{
	extend : 'Ext.data.Model',
	fields : [{
		name : 'groupType',
		type : 'string'
	},{
		name : 'groupCode',
		type : 'string'
	},{
		name : 'groupName',
		type : 'string'
	},{
		name : 'bindingUseRateDay',
		type : 'float',
		convert : function(value) {
			if (value != null) {
				if(value == 1){
					return '100%'
				}else{
//					return Ext.util.Format.number(value * 100,'0.00')+'%';
					return Ext.util.Format.number(value * 100,'0.00')+'%';
				}
			} else {
				return null;
			}
		}
	},{
		name : 'bindingUseRateMonth',
		type : 'float',
		convert : function(value) {
			if (value != null) {
				if(value == 1){
					return '100%'
				}else{
					return Ext.util.Format.number(value * 100,'0.00')+'%';
				}
			} else {
				return null;
			}
		}
	},{
		name : 'trayBindingRateDay',
		type : 'float',
		convert : function(value) {
			if (value != null) {
				if(value == 1){
					return '100%'
				}else{
					return Ext.util.Format.number(value * 100,'0.00')+'%';
				}
			} else {
				return null;
			}
		}
	},{
		name : 'trayBindingRateMonth',
		type : 'float',
		convert : function(value) {
			if (value != null) {
				if(value == 1){
					return '100%'
				}else{
					return Ext.util.Format.number(value * 100,'0.00')+'%';
				}
			} else {
				return null;
			}
		}
	}]
});

Ext.define('Foss.platform.trayscanEfficiencyIndex.Model',{
	extend : 'Ext.data.Model',
	fields : [{
		name : 'tfrCtrCode',
		type : 'string'
	},{
		name : 'tfrCtrName',
		type : 'string'
	},{
		name : 'staDate',
		type : 'date',
		convert : function(value) {
			if (value != null) {
				var date = new Date(value);
				return Ext.Date.format(date, 'Y-m-d');
			} else {
				return null;
			}
		}
	},{
		name : 'unloadTaskQtyDay',
		type : 'int'
	},{
		name : 'bindingUseQtyDay',
		type : 'int'
	},{
		name : 'bindingUseRateDay',
		type : 'float',
		convert : function(value) {
			if (value != null) {
				if(value == 1){
					return '100%'
				}else{
					return Ext.util.Format.number(value * 100,'0.00')+'%';
				}
			} else {
				return null;
			}
		}
	},{
		name : 'unloadScanQtyDay',
		type : 'int'
	},{
		name : 'bindingQtyDay',
		type : 'int'
	},{
		name : 'trayBindingRateDay',
		type : 'float',
		convert : function(value) {
			if (value != null) {
				if(value == 1){
					return '100%'
				}else{
					return Ext.util.Format.number(value * 100,'0.00')+'%';
				}
			} else {
				return null;
			}
		}
	},{
		name : 'forkScanQtyDay',
		type : 'int'
	},{
		name : 'forkScanRateDay',
		type : 'float',
		convert : function(value) {
			if (value != null) {
				if(value == 1){
					return '100%'
				}else{
					return Ext.util.Format.number(value * 100,'0.00')+'%';
				}
			} else {
				return null;
			}
		}
	},{
		name : 'bindingUseRateMonth',
		type : 'float',
		convert : function(value) {
			if (value != null) {
				if(value == 1){
					return '100%'
				}else{
					return Ext.util.Format.number(value * 100,'0.00')+'%';
				}
			} else {
				return null;
			}
		}
	},{
		name : 'trayBindingRateMonth',
		type : 'float',
		convert : function(value) {
			if (value != null) {
				if(value == 1){
					return '100%'
				}else{
					return Ext.util.Format.number(value * 100,'0.00')+'%';
				}
			} else {
				return null;
			}
		}
	},{
		name : 'forkScanRateMonth',
		type : 'float',
		convert : function(value) {
			if (value != null) {
				if(value == 1){
					return '100%'
				}else{
					return Ext.util.Format.number(value * 100,'0.00')+'%';
				}
			} else {
				return null;
			}
		}
	}]
});


Ext.define('Foss.platform.trayscanEfficiencyIndex.store',{
	extend : 'Ext.data.Store',
	model : 'Foss.platform.trayscanEfficiencyIndex.Model',
	proxy : {
		type : 'ajax',
		actionMethods : 'POST',
		url : platform.realPath('queryTrayscanEfficiency.action'),
		reader : {
			type : 'json',
			root : 'trayscanEfficiencyVo.effs'
		}
	},
	listeners : {
		beforeload : function(store,operation,eOpts) {
			var queryForm = platform.trayscanEfficiencyIndex.queryForm;
			if(queryForm != null){
				var queryParams = queryForm.getValues();
				Ext.apply(operation,{
					params : queryParams
				});
			}	
		},
		load : function(store,records,successful,eOpts){
			var trayscanEfficiencyVo = store.proxy.reader.rawData.trayscanEfficiencyVo;
			if(!store.proxy.reader.rawData.success){
				Ext.ux.Toast.msg('提示', '查询失败，' + store.proxy.reader.rawData.message, 'error');
			}
		}
	}
});

//根据登陆人 的所属部门  设置经营部门或者转运场为只读
platform.trayscanEfficiencyIndex.getCurrentDept = function(item,operationCode,transferCode,str){
	if(!Ext.isEmpty(transferCode) && Ext.isEmpty(operationCode)){
		if(str == 'transferCode'){
			item.readOnly = true;
		}
	}else if(Ext.isEmpty(transferCode) && !Ext.isEmpty(operationCode)){
		if(str == 'operationCode'){
			item.readOnly = true;
		}
	}
}


Ext.define('Foss.platform.trayscanEfficiencyIndex.form', {
	extend : 'Ext.form.Panel',
	title : '查询条件',
	frame : true,
	layout : 'column',
	defaults : {
		columns : 4,
		margin : '5 5 5 5'
	},
	items : [{
		name : 'trayscanEfficiencyVo.hqCode',
		fieldLabel : '经营本部',
		columnWidth : 0.25,
		allowBlank : true,
		xtype : 'dynamicorgcombselector',
		type : 'ORG',
		listeners : {
			beforerender : function(_this, eOpts ){
				platform.trayscanEfficiencyIndex.getCurrentDept(_this,platform.trayscanEfficiencyIndex.operationDeptCode,platform.trayscanEfficiencyIndex.outfieldCode,'operationCode');
			}
		}
	},{
		name : 'trayscanEfficiencyVo.tfrCtrCode',
		fieldLabel : '转运场',
		columnWidth : 0.25,
		allowBlank : true,
		xtype : 'dynamicorgcombselector',
		type : 'ORG',
		transferCenter : 'Y',
		listeners : {
			beforerender : function(_this, eOpts ){
				platform.trayscanEfficiencyIndex.getCurrentDept(_this,platform.trayscanEfficiencyIndex.operationDeptCode,platform.trayscanEfficiencyIndex.outfieldCode,'transferCode');
			}
		}
	},{
		xtype:'datefield',
    	fieldLabel:'日期',
    	allowBlank:false,
		name:'trayscanEfficiencyVo.staDate',
		editable:false,
		value: Ext.Date.add(login.currentServerTime,Ext.Date.DAY,-1),
		maxValue:Ext.Date.add(login.currentServerTime, Ext.Date.DAY, -1),
		format:'Y-m-d',
		columnWidth :.25
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
			handler : function() {
				var form = platform.trayscanEfficiencyIndex.queryForm.getForm();
				form.reset();
				var operationCode = form.findField('trayscanEfficiencyVo.hqCode');
				var transferCode = form.findField('trayscanEfficiencyVo.tfrCtrCode');
				if(operationCode.readOnly == true){
						operationCode.setCombValue(
							platform.trayscanEfficiencyIndex.operationDeptName,
							platform.trayscanEfficiencyIndex.operationDeptCode
							)
				}
				if(transferCode.readOnly == true){
						transferCode.setCombValue(
							platform.trayscanEfficiencyIndex.outfieldName,
							platform.trayscanEfficiencyIndex.outfieldCode
							)
				}
			}
		},{
			xtype : 'container',
			columnWidth : .80,
			html : '&nbsp;'
		},{
			text : '查询',
			cls : 'yellow_button',
			columnWidth : .08,
			handler : function() {
				var form = platform.trayscanEfficiencyIndex.queryForm.getForm();
				if (!form.isValid()) {
					Ext.Msg
					.alert('提示','查询失败，请输入有效条件!');
					return false;
				}
				var params = this.up('form').getForm().getValues();
				Ext.getCmp('Foss_Platform_TrayscanEfficiencyIndex_Grid_Id').store.load({
					params : params
				});
			}
		}]
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});


platform.trayscanEfficiencyIndex.queryForm = Ext.create('Foss.platform.trayscanEfficiencyIndex.form');

Ext.define('Foss.platform.trayscanEfficiencyIndex.grid', {
	extend : 'Ext.grid.Panel',
	frame : true,
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	columnLines : true,
	height:600,
	autoScroll : true,
	defaults : {
		align : 'center'
	},
	plugins: [{
	    ptype: 'rowexpander',
		rowsExpander: false,
		rowBodyElement : 'Foss.platform.trayscanEfficiencyIndex.gridDetail'
	}],
	title : '查询结果',
	columns : [{
		xtype : 'ellipsiscolumn',
		dataIndex : 'tfrCtrCode',
		hidden : true
	},{
		xtype : 'ellipsiscolumn',
		header : '转运场',
		dataIndex : 'tfrCtrName',
		width : 100
	},{
		xtype : 'datecolumn',
		header : '日期',
		dataIndex : 'staDate',
		format:'Y-m-d',
		width : 80
	},{
		text : '当日数据明细',
		columns : [{
			xtype : 'ellipsiscolumn',
			header : '卸车任务数',
			dataIndex : 'unloadTaskQtyDay',
			width : 80
		},{
			xtype : 'ellipsiscolumn',
			header : '绑定使用数',
			dataIndex : 'bindingUseQtyDay',
			width : 80
		},{
			xtype : 'ellipsiscolumn',
			header : '绑定使用率',
			dataIndex : 'bindingUseRateDay',
			width : 80
		},{
			xtype : 'ellipsiscolumn',
			header : '卸车扫描总件数',
			dataIndex : 'unloadScanQtyDay',
			width : 80
		},{
			xtype : 'ellipsiscolumn',
			header : '托盘绑定总件数',
			dataIndex : 'bindingQtyDay',
			width : 80
		},{
			xtype : 'ellipsiscolumn',
			header : '托盘绑定率',
			dataIndex : 'trayBindingRateDay',
			width : 80
		},{
			xtype : 'ellipsiscolumn',
			header : '叉车扫描件数',
			dataIndex : 'forkScanQtyDay',
			width : 80
		},{
			xtype : 'ellipsiscolumn',
			header : '叉车扫描率',
			dataIndex : 'forkScanRateDay',
			width : 80
		}]
	},{
		text : '当月数据明细',
		columns : [{
			xtype : 'ellipsiscolumn',
			header : '绑定使用率',
			dataIndex : 'bindingUseRateMonth',
			width : 80
		},{
			xtype : 'ellipsiscolumn',
			header : '托盘绑定率',
			dataIndex : 'trayBindingRateMonth',
			width : 80
		},{
			xtype : 'ellipsiscolumn',
			header : '叉车扫描率',
			dataIndex : 'forkScanRateMonth',
			width : 80
		}]
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext
				.create('Foss.platform.trayscanEfficiencyIndex.store');
		me.callParent([cfg]);
	}
});

platform.trayscanEfficiencyIndex.grid =Ext.create('Foss.platform.trayscanEfficiencyIndex.grid',{id:'Foss_Platform_TrayscanEfficiencyIndex_Grid_Id'}) ;



//二级grid 的store
Ext.define('Foss.platform.trayscanEfficiencyIndex.storeDetail',{
	extend : 'Ext.data.Store',
	model : 'Foss.platform.trayscanEfficiencyIndex.ModelDetail',
	proxy : {
		type : 'ajax',
		actionMethods : 'POST',
		url : platform.realPath('queryTrayscanEfficiencyDetail.action'),
		reader : {
			type : 'json',
			root : 'trayscanEfficiencyVo.effDetails'
		}
	}
});

//二级grid
Ext.define('Foss.platform.trayscanEfficiencyIndex.gridDetail', {
	extend : 'Ext.grid.Panel',
	frame : true,
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	defaults : {
		align : 'center'
	},
	//根据上一层表的行数据 加载Store
	bindData :function(record){
		var transferCenterCode = record.get('tfrCtrCode');
		var statisticDate = record.get('staDate');
		var grid = this;
		Ext.Ajax.request({
			url: platform.realPath('queryTrayscanEfficiencyDetail.action'),
			params:{'trayscanEfficiencyVo.tfrCtrCode': transferCenterCode,
					'trayscanEfficiencyVo.staDate': statisticDate
					},
			success:function(response){
				var result = Ext.decode(response.responseText);
				grid.store.loadData(result.trayscanEfficiencyVo.effDetails);
			}
		})
	},
	columns : [{
		xtype : 'ellipsiscolumn',
		header : '组别',
		dataIndex : 'groupName',
		width : 120
	},{
		text : '绑定使用率',
		columns : [{
			xtype : 'ellipsiscolumn',
			header : '当日',
			dataIndex : 'bindingUseRateDay',
			width : 100
		},{
			xtype : 'ellipsiscolumn',
			header : '当月',
			dataIndex : 'bindingUseRateMonth',
			width : 100
		}]
	},{
		text : '托盘绑定率',
		columns : [{
			xtype : 'ellipsiscolumn',
			header : '当日',
			dataIndex : 'trayBindingRateDay',
			width : 100
		},{
			xtype : 'ellipsiscolumn',
			header : '当月',
			dataIndex : 'trayBindingRateMonth',
			width : 100
		}]
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext
				.create('Foss.platform.trayscanEfficiencyIndex.storeDetail');
		me.callParent([cfg]);
	}
});

Ext.onReady(function() {
	Ext.QuickTips.init();
	Ext.create('Ext.panel.Panel', {
				id : 'T_platform-trayscanEfficiencyIndex_content',
				cls : "panelContentNToolbar",
				bodyCls : 'panelContent-body',
				items : [platform.trayscanEfficiencyIndex.queryForm,platform.trayscanEfficiencyIndex.grid],
				renderTo : 'T_platform-trayscanEfficiencyIndex-body'
	});

	//如果当前部门找不到外场，但是找到了经营本部，则自动填写经营本部
	if(!Ext.isEmpty(platform.trayscanEfficiencyIndex.operationDeptCode)){
		platform.trayscanEfficiencyIndex.queryForm.getForm().findField('trayscanEfficiencyVo.hqCode').setCombValue(
			platform.trayscanEfficiencyIndex.operationDeptName,		
			platform.trayscanEfficiencyIndex.operationDeptCode
		);
	};
	//如果当前部门为外场，则自动填写外场
	if(!Ext.isEmpty(platform.trayscanEfficiencyIndex.outfieldCode)){
		platform.trayscanEfficiencyIndex.queryForm.getForm().findField('trayscanEfficiencyVo.tfrCtrCode').setCombValue(
			platform.trayscanEfficiencyIndex.outfieldName,
			platform.trayscanEfficiencyIndex.outfieldCode
			);
	}
});