/**
*
*外场待叉区停留时长占比 查询界面
*
*/

Ext.define('Foss.platform.forkliftStayDurationIndex.Model',{
	extend : 'Ext.data.Model',
	fields : [{
		name : 'transferCenterName',
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
		name : 'stayMinPerTrayDay',
		type : 'string'
	},{
		name : 'stayMinDayRatio30',
		type : 'string',
		convert : function(value) {
			if (value != null) {
				
			   return value+'%';
			} else {
				return null;
			}
		}
	},{
		name : 'stayMinDayRatio60',
		type : 'string',
		convert : function(value) {
			if (value != null) {
				
			   return value+'%';
			} else {
				return null;
			}
		}
	},{
		name : 'stayMinDayRatio120',
		type : 'string',
		convert : function(value) {
			if (value != null) {
			  return value+'%';
			} else {
				return null;
			}
		}
	},{
		name : 'stayMinDayRatio360',
		type : 'string',
		convert : function(value) {
			if (value != null) {
			  return value+'%';
			} else {
				return null;
			}
		}
	},{
		name : 'stayMinDayRatio0',
		type : 'string',
		convert : function(value) {
			if (value != null) {
			   return value+'%';
			} else {
				return null;
			}
		}
	},{
		name : 'stayMinPerTrayMonth',
		type : 'string'
	},{
		name : 'stayMinMonthRatio30',
		type : 'string',
		convert : function(value) {
			if (value != null) {
				return value+'%';
			} else {
				return null;
			}
		}
	},{
		name : 'stayMinMonthRatio60',
		type : 'string',
		convert : function(value) {
			if (value != null) {
			    return value+'%';
			} else {
				return null;
			}
		}
	},{
		name : 'stayMinMonthRatio120',
		type : 'string',
		convert : function(value) {
			if (value != null) {
					return value+'%';
			} else {
				return null;
			}
		}
	},{
		name : 'stayMinMonthRatio360',
		type : 'string',
		convert : function(value) {
			if (value != null) {
					return value+'%';
			} else {
				return null;
			}
		}
	},{
		name : 'stayMinMonthRatio0',
		type : 'string',
		convert : function(value) {
			if (value != null) {
					return value+'%';
			}
			 else {
				return null;
			}
		}
	}]
});


Ext.define('Foss.platform.forkliftStayDurationIndex.store',{
	extend : 'Ext.data.Store',
	model : 'Foss.platform.forkliftStayDurationIndex.Model',
	proxy : {
		type : 'ajax',
		actionMethods : 'POST',
		url : platform.realPath('queryForkliftStayDuration.action'),
		reader : {
			type : 'json',
			root : 'forkliftGoodsEfficiencyVo.forkliftGoodsStayDurationList'
		}
	},
	listeners : {
		beforeload : function(store,operation,eOpts) {
			var queryForm = platform.forkliftStayDurationIndex.queryForm;
			if(queryForm != null){
				var queryParams = queryForm.getValues();
				Ext.apply(operation,{
					params : queryParams
				});
			}	
		},
		load : function(store,records,successful,eOpts){
			var forkliftGoodsEfficiencyVo = store.proxy.reader.rawData.forkliftGoodsEfficiencyVo;
			if(!store.proxy.reader.rawData.success){
				Ext.ux.Toast.msg('提示', '查询失败，' + store.proxy.reader.rawData.message, 'error');
			}
		}
	}
});

//根据登陆人 的所属部门  设置经营部门或者转运场为只读
platform.forkliftStayDurationIndex.getCurrentDept = function(item,operationCode,transferCode,str){
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


Ext.define('Foss.platform.forkliftStayDurationIndex.form', {
	extend : 'Ext.form.Panel',
	title : '查询条件',
	frame : true,
	layout : 'column',
	defaults : {
		columns : 4,
		margin : '5 5 5 5'
	},
	items : [{
		name : 'forkliftGoodsEfficiencyVo.operationDeptCode',
		fieldLabel : '经营本部',
		columnWidth : 0.25,
		allowBlank : true,
		xtype : 'dynamicorgcombselector',
		type : 'ORG',
		listeners : {
			beforerender : function(_this, eOpts ){
				platform.forkliftStayDurationIndex.getCurrentDept(_this,platform.forkliftStayDurationIndex.operationDeptCode,platform.forkliftStayDurationIndex.outfieldCode,'operationCode');
			}
		}
	},{
		name : 'forkliftGoodsEfficiencyVo.transferCenterCode',
		fieldLabel : '转运场',
		columnWidth : 0.25,
		allowBlank : true,
		xtype : 'dynamicorgcombselector',
		type : 'ORG',
		transferCenter : 'Y',
		listeners : {
			beforerender : function(_this, eOpts ){
				platform.forkliftStayDurationIndex.getCurrentDept(_this,platform.forkliftStayDurationIndex.operationDeptCode,platform.forkliftStayDurationIndex.outfieldCode,'transferCode');
			}
		}
	},{
		xtype:'datefield',
    	fieldLabel:'日期',
    	allowBlank:false,
		name:'forkliftGoodsEfficiencyVo.staDate',
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
				var form = platform.forkliftStayDurationIndex.queryForm.getForm();
				form.reset();
				var operationCode = form.findField('forkliftGoodsEfficiencyVo.operationDeptCode');
				var transferCode = form.findField('forkliftGoodsEfficiencyVo.transferCenterCode');
				if(operationCode.readOnly == true){
						operationCode.setCombValue(
							platform.forkliftStayDurationIndex.operationDeptName,
							platform.forkliftStayDurationIndex.operationDeptCode
							)
				}
				if(transferCode.readOnly == true){
						transferCode.setCombValue(
							platform.forkliftStayDurationIndex.outfieldName,
							platform.forkliftStayDurationIndex.outfieldCode
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
				var form = platform.forkliftStayDurationIndex.queryForm.getForm();
				if (!form.isValid()) {
					Ext.Msg
					.alert('提示','查询失败，请输入有效条件!');
					return false;
				}
				var params = this.up('form').getForm().getValues();
				Ext.getCmp('Foss_Platform_forkliftStayDurationIndex_Grid_Id').store.load({
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


platform.forkliftStayDurationIndex.queryForm = Ext.create('Foss.platform.forkliftStayDurationIndex.form');

Ext.define('Foss.platform.forkliftStayDurationIndex.grid', {
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
	title : '查询结果',
	columns : [{
		xtype : 'ellipsiscolumn',
		header : '转运场',
		dataIndex : 'transferCenterName',
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
			header : '板均停留时长',
			dataIndex : 'stayMinPerTrayDay',
			width : 80
		},{
			text : '停留待叉区时长各时间段占比',
			columns : [{
				xtype : 'ellipsiscolumn',
				header : '(0,0.5h]',
				dataIndex : 'stayMinDayRatio30',
				width : 70
			},{
				xtype : 'ellipsiscolumn',
				header : '(0.5h,1h]',
				dataIndex : 'stayMinDayRatio60',
				width : 70
			},{
				xtype : 'ellipsiscolumn',
				header : '(1h,2h]',
				dataIndex : 'stayMinDayRatio120',
				width : 70
			},{
				xtype : 'ellipsiscolumn',
				header : '(2h,6h]',
				dataIndex : 'stayMinDayRatio360',
				width : 70
			},{
				xtype : 'ellipsiscolumn',
				header : '(6h,+∞]',
				dataIndex : 'stayMinDayRatio0',
				width : 70
			}]
		}]
	},{
		text : '当月数据明细',
		columns : [{
			xtype : 'ellipsiscolumn',
			header : '板均停留时长',
			dataIndex : 'stayMinPerTrayMonth',
			width : 80
		},{
			text : '停留待叉区时长各时间段占比',
			columns : [{
				xtype : 'ellipsiscolumn',
				header : '(0,0.5h]',
				dataIndex : 'stayMinMonthRatio30',
				width : 70
			},{
				xtype : 'ellipsiscolumn',
				header : '(0.5h,1h]',
				dataIndex : 'stayMinMonthRatio60',
				width : 70
			},{
				xtype : 'ellipsiscolumn',
				header : '(1h,2h]',
				dataIndex : 'stayMinMonthRatio120',
				width : 70
			},{
				xtype : 'ellipsiscolumn',
				header : '(2h,6h]',
				dataIndex : 'stayMinMonthRatio360',
				width : 70
			},{
				xtype : 'ellipsiscolumn',
				header : '(6h,+∞]',
				dataIndex : 'stayMinMonthRatio0',
				width : 70
			}]
		}]
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext
				.create('Foss.platform.forkliftStayDurationIndex.store');
		me.callParent([cfg]);
	}
});

platform.forkliftStayDurationIndex.grid =Ext.create('Foss.platform.forkliftStayDurationIndex.grid',{id:'Foss_Platform_forkliftStayDurationIndex_Grid_Id'}) ;


Ext.onReady(function() {
	Ext.QuickTips.init();
	Ext.create('Ext.panel.Panel', {
				id : 'T_platform-forkliftStayDurationIndex_content',
				cls : "panelContentNToolbar",
				bodyCls : 'panelContent-body',
				items : [platform.forkliftStayDurationIndex.queryForm,platform.forkliftStayDurationIndex.grid],
				renderTo : 'T_platform-forkliftStayDurationIndex-body'
	});

	//如果当前部门找不到外场，但是找到了经营本部，则自动填写经营本部
	if(!Ext.isEmpty(platform.forkliftStayDurationIndex.operationDeptCode)){
		platform.forkliftStayDurationIndex.queryForm.getForm().findField('forkliftGoodsEfficiencyVo.operationDeptCode').setCombValue(
			platform.forkliftStayDurationIndex.operationDeptName,		
			platform.forkliftStayDurationIndex.operationDeptCode
		);
	};
	//如果当前部门为外场，则自动填写外场
	if(!Ext.isEmpty(platform.forkliftStayDurationIndex.outfieldCode)){
		platform.forkliftStayDurationIndex.queryForm.getForm().findField('forkliftGoodsEfficiencyVo.transferCenterCode').setCombValue(
			platform.forkliftStayDurationIndex.outfieldName,
			platform.forkliftStayDurationIndex.outfieldCode
			);
	}
});








