/**
*
*电叉分货效率查询界面
*
*/
/**分货效率明细   转运场*/
Ext.define('Foss.platform.forkliftGoodsEfficiencyIndex.ModelOfTfr',{
	extend : 'Ext.data.Model',
	fields : [{
		name : 'transferCenterCode',
		type : 'string'
	},{
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
		name : 'effPerVoteDay',
		type : 'string'
	},{
		name : 'effPerTrayDay',
		type : 'string'
	},{
		name : 'effPerVoteMonth',
		type : 'string'
	},{
		name : 'effPerTrayMonth',
		type : 'string'
	}]
});

/**分货效率明细   叉车司机所属部门*/
Ext.define('Foss.platform.forkliftGoodsEfficiencyIndex.ModelOfOrg',{
	extend : 'Ext.data.Model',
	fields : [{
		name : 'transferCenterCode',
		type : 'string'
	},{
		name : 'driverOrgCode',
		type : 'string'
	},{
		name : 'driverOrgName',
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
		name : 'effPerVoteDay',
		type : 'string'
	},{
		name : 'effPerTrayDay',
		type : 'string'
	},{
		name : 'effPerVoteMonth',
		type : 'string'
	},{
		name : 'effPerTrayMonth',
		type : 'string'
	}]
});

/**分货效率明细   叉车司机*/
Ext.define('Foss.platform.forkliftGoodsEfficiencyIndex.ModelOfDriver',{
	extend : 'Ext.data.Model',
	fields : [{
		name : 'driverName',
		type : 'string'
	},{
		name : 'driverCode',
		type : 'string'
	},{
		name : 'effPerVoteDay',
		type : 'string'
	},{
		name : 'effPerTrayDay',
		type : 'string'
	},{
		name : 'effPerVoteMonth',
		type : 'string'
	},{
		name : 'effPerTrayMonth',
		type : 'string'
	}]
});


Ext.define('Foss.platform.forkliftGoodsEfficiencyIndex.storeOfTfr',{
	extend : 'Ext.data.Store',
	model : 'Foss.platform.forkliftGoodsEfficiencyIndex.ModelOfTfr',
	proxy : {
		type : 'ajax',
		actionMethods : 'POST',
		url : platform.realPath('queryForkliftGoodsEfficiencyOfTfr.action'),
		reader : {
			type : 'json',
			root : 'forkliftGoodsEfficiencyVo.forkliftEffList'
		}
	},
	listeners : {
		beforeload : function(store,operation,eOpts) {
			var queryForm = platform.forkliftGoodsEfficiencyIndex.queryFormOfTfr;
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

platform.forkliftGoodsEfficiencyIndex.getCurrentDept = function(item,operationCode,transferCode,str){
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


Ext.define('Foss.platform.forkliftGoodsEfficiencyIndex.formOfTfr', {
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
				platform.forkliftGoodsEfficiencyIndex.getCurrentDept(_this,platform.forkliftGoodsEfficiencyIndex.operationDeptCode,platform.forkliftGoodsEfficiencyIndex.outfieldCode,'operationCode');
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
				platform.forkliftGoodsEfficiencyIndex.getCurrentDept(_this,platform.forkliftGoodsEfficiencyIndex.operationDeptCode,platform.forkliftGoodsEfficiencyIndex.outfieldCode,'transferCode');
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
				var form = platform.forkliftGoodsEfficiencyIndex.queryFormOfTfr.getForm();
				form.reset();
				var operationCode = form.findField('forkliftGoodsEfficiencyVo.operationDeptCode');
				var transferCode = form.findField('forkliftGoodsEfficiencyVo.transferCenterCode');
				if(operationCode.readOnly == true){
						operationCode.setCombValue(
							platform.forkliftGoodsEfficiencyIndex.operationDeptName,
							platform.forkliftGoodsEfficiencyIndex.operationDeptCode
							)
				}
				if(transferCode.readOnly == true){
						transferCode.setCombValue(
							platform.forkliftGoodsEfficiencyIndex.outfieldName,
							platform.forkliftGoodsEfficiencyIndex.outfieldCode
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
				var form = platform.forkliftGoodsEfficiencyIndex.queryFormOfTfr.getForm();
				if (!form.isValid()) {
					Ext.Msg
					.alert('提示','查询失败，请输入有效条件!');
					return false;
				}
				var params = this.up('form').getForm().getValues();
				Ext.getCmp('Foss_Platform_ForkliftGoodsEfficiencyIndex_Grid_Tfr_Id').store.load({
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


platform.forkliftGoodsEfficiencyIndex.queryFormOfTfr = Ext.create('Foss.platform.forkliftGoodsEfficiencyIndex.formOfTfr');

Ext.define('Foss.platform.forkliftGoodsEfficiencyIndex.gridOfTfr', {
	extend : 'Ext.grid.Panel',
	frame : true,
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	height:400,
	autoScroll : true,
	defaults : {
		align : 'center'
	},
	title : '查询结果',
	columns : [{
		xtype : 'ellipsiscolumn',
		header : '转运场',
		dataIndex : 'transferCenterName',
		width : 140
	},{
		xtype : 'ellipsiscolumn',
		header : '日期',
		dataIndex : 'staDate',
		width : 140
	},{
		text : '当日电叉分货效率',
		columns : [{
			xtype : 'ellipsiscolumn',
			header : '票均效率',
			dataIndex : 'effPerVoteDay',
			width : 140
		},{
			xtype : 'ellipsiscolumn',
			header : '板均效率',
			dataIndex : 'effPerTrayDay',
			width : 140
		}]
	},{
		text : '当月电叉分货效率',
		columns : [{
			xtype : 'ellipsiscolumn',
			header : '票均效率',
			dataIndex : 'effPerVoteMonth',
			width : 140
		},{
			xtype : 'ellipsiscolumn',
			header : '板均效率',
			dataIndex : 'effPerTrayMonth',
			width : 140
		}]
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext
				.create('Foss.platform.forkliftGoodsEfficiencyIndex.storeOfTfr');
		me.callParent([cfg]);
	}
});

platform.forkliftGoodsEfficiencyIndex.gridOfTfr =Ext.create('Foss.platform.forkliftGoodsEfficiencyIndex.gridOfTfr',{id : 'Foss_Platform_ForkliftGoodsEfficiencyIndex_Grid_Tfr_Id'}) ;

Ext.define('Foss.platform.forkliftGoodsEfficiencyIndex.PanelOfTfr', {
	extend : 'Ext.panel.Panel',
	frame : false,
	layout : 'auto',
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		var gridOfTfr = platform.forkliftGoodsEfficiencyIndex.gridOfTfr;
		var queryFormOfTfr = platform.forkliftGoodsEfficiencyIndex.queryFormOfTfr;
		me.items = [queryFormOfTfr, gridOfTfr];
		me.callParent([cfg]);
	}
})


/**
 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!上面是  转运场电叉分货效率  store  grid  panel
 * ！！！！！！！！！！！！！！！！！下面 是   组别和叉车司机电叉分货效率  store grid panel
 */
Ext.define('Foss.platform.forkliftGoodsEfficiencyIndex.storeOfOrg',{
	extend : 'Ext.data.Store',
	model : 'Foss.platform.forkliftGoodsEfficiencyIndex.ModelOfOrg',
	proxy : {
		type : 'ajax',
		actionMethods : 'POST',
		url : platform.realPath('queryForkliftGoodsEfficiencyOfOrg.action'),
		reader : {
			type : 'json',
			root : 'forkliftGoodsEfficiencyVo.forkliftDriverOrgEffList',
			totalProperty : 'totalCount',
			successProperty : 'success'
		}
	},
	listeners : {
		beforeload : function(store,operation,eOpts) {
			var queryForm = platform.forkliftGoodsEfficiencyIndex.queryFormOfOrg;
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

Ext.define('Foss.platform.forkliftGoodsEfficiencyIndex.formOfOrg', {
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
				platform.forkliftGoodsEfficiencyIndex.getCurrentDept(_this,platform.forkliftGoodsEfficiencyIndex.operationDeptCode,platform.forkliftGoodsEfficiencyIndex.outfieldCode,'operationCode');
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
				platform.forkliftGoodsEfficiencyIndex.getCurrentDept(_this,platform.forkliftGoodsEfficiencyIndex.operationDeptCode,platform.forkliftGoodsEfficiencyIndex.outfieldCode,'transferCode');
			}
		}
	},{
		xtype:'commonemployeeselector',
    	fieldLabel:'叉车司机',
    	allowBlank:true,
		name:'forkliftGoodsEfficiencyVo.driverCode',
		columnWidth :.25
	},{
		xtype:'datefield',
    	fieldLabel:'日期',
    	allowBlank:false,
		name:'forkliftGoodsEfficiencyVo.staDate',
		editable:false,
		value: Ext.Date.add(login.currentServerTime,Ext.Date.DAY,-1),
		format:'Y-m-d',
		columnWidth :.25
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
				var form = platform.forkliftGoodsEfficiencyIndex.queryFormOfOrg.getForm();
				form.reset();
				var operationCode = form.findField('forkliftGoodsEfficiencyVo.operationDeptCode');
				var transferCode = form.findField('forkliftGoodsEfficiencyVo.transferCenterCode');
				if(operationCode.readOnly == true){
						operationCode.setCombValue(
							platform.forkliftGoodsEfficiencyIndex.operationDeptName,
							platform.forkliftGoodsEfficiencyIndex.operationDeptCode
							)
				}
				if(transferCode.readOnly == true){
						transferCode.setCombValue(
							platform.forkliftGoodsEfficiencyIndex.outfieldName,
							platform.forkliftGoodsEfficiencyIndex.outfieldCode
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
				var form = platform.forkliftGoodsEfficiencyIndex.queryFormOfOrg.getForm();
				if (!form.isValid()) {
					Ext.Msg
					.alert('提示','查询失败，请输入有效条件!');
					return false;
				}
				platform.forkliftGoodsEfficiencyIndex.pagingBarOfOrg.moveFirst();
			}
		}]
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

platform.forkliftGoodsEfficiencyIndex.queryFormOfOrg = Ext.create('Foss.platform.forkliftGoodsEfficiencyIndex.formOfOrg');


Ext.define('Foss.platform.forkliftGoodsEfficiencyIndex.gridOfOrg', {
	extend : 'Ext.grid.Panel',
	frame : true,
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	height:400,
	autoScroll : true,
	defaults : {
		align : 'center'
	},
	plugins: [{
	    ptype: 'rowexpander',
		rowsExpander: false,
		rowBodyElement : 'Foss.platform.forkliftGoodsEfficiencyIndex.gridOfDriver'
	}],
	title : '查询结果',
	columns : [{
        xtype: 'rownumberer',
        text : '序号',
        align : 'center',
        width: 50,
        sortable: false
    },{
		xtype : 'ellipsiscolumn',
		dataIndex : 'driverOrgCode',
		hidden : true
	},{
		xtype : 'ellipsiscolumn',
		dataIndex : 'transferCenterCode',
		hidden : true
	},{
		xtype : 'ellipsiscolumn',
		header : '组别',
		dataIndex : 'driverOrgName',
		width : 140
	},{
		xtype : 'ellipsiscolumn',
		header : '查询时间',
		dataIndex : 'staDate',
		width : 140,
		renderer : function(value){
			if (value != null) {
				return Ext.Date.format(new Date(value), 'Y-m-d');
			} else {
				return null;
			}
		}
	},{
		text : '当日电叉分货效率',
		columns : [{
			xtype : 'ellipsiscolumn',
			header : '票均效率',
			dataIndex : 'effPerVoteDay',
			width : 140
		},{
			xtype : 'ellipsiscolumn',
			header : '板均效率',
			dataIndex : 'effPerTrayDay',
			width : 140
		}]
	},{
		text : '当月电叉分货效率',
		columns : [{
			xtype : 'ellipsiscolumn',
			header : '票均效率',
			dataIndex : 'effPerVoteMonth',
			width : 140
		},{
			xtype : 'ellipsiscolumn',
			header : '板均效率',
			dataIndex : 'effPerTrayMonth',
			width : 140
		}]
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext
				.create('Foss.platform.forkliftGoodsEfficiencyIndex.storeOfOrg');
		
		me.bbar = Ext.create('Deppon.StandardPaging',{
			store : me.store,
			pageSize : 20,
			maximumSize : 50,
			plugins : Ext.create('Deppon.ux.PageSizePlugin', {
				sizeList : [['20', 20], ['30', 30], ['40', 40], ['50', 50]]
			})
		}),
		platform.forkliftGoodsEfficiencyIndex.pagingBarOfOrg = me.bbar;
		me.callParent([cfg]);
	}
});


platform.forkliftGoodsEfficiencyIndex.gridOfOrg =Ext.create('Foss.platform.forkliftGoodsEfficiencyIndex.gridOfOrg') ;

Ext.define('Foss.platform.forkliftGoodsEfficiencyIndex.PanelOfOrg', {
	extend : 'Ext.panel.Panel',
	frame : false,
	layout : 'auto',
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		var gridOfOrg = platform.forkliftGoodsEfficiencyIndex.gridOfOrg;
		var queryFormOfOrg = platform.forkliftGoodsEfficiencyIndex.queryFormOfOrg;
		me.items = [queryFormOfOrg, gridOfOrg];
		me.callParent([cfg]);
	}
});


/***************二级 store  grid***********************/

Ext.define('Foss.platform.forkliftGoodsEfficiencyIndex.storeOfDriver',{
	extend : 'Ext.data.Store',
	model : 'Foss.platform.forkliftGoodsEfficiencyIndex.ModelOfDriver',
	proxy : {
		type : 'ajax',
		actionMethods : 'POST',
		url : platform.realPath('queryForkliftGoodsEfficiencyOfDriver.action'),
		reader : {
			type : 'json',
			root : 'forkliftGoodsEfficiencyVo.forkliftDriverEffList',
		}
	}
});

Ext.define('Foss.platform.forkliftGoodsEfficiencyIndex.gridOfDriver', {
	extend : 'Ext.grid.Panel',
	frame : true,
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	defaults : {
		align : 'center'
	},
	//根据上一层表的行数据 加载Store
	bindData :function(record){
		var transferCenterCode = record.get('transferCenterCode');
		var staDate = record.get('staDate');
		var driverOrgCode = record.get('driverOrgCode');
		var grid = this;
		Ext.Ajax.request({
			url: platform.realPath('queryForkliftGoodsEfficiencyOfDriver.action'),
			params:{'forkliftGoodsEfficiencyVo.transferCenterCode': transferCenterCode,
					'forkliftGoodsEfficiencyVo.staDate': staDate,
					'forkliftGoodsEfficiencyVo.driverOrgCode': driverOrgCode
					},
			success:function(response){
				var result = Ext.decode(response.responseText);
				grid.store.loadData(result.forkliftGoodsEfficiencyVo.forkliftDriverEffList);
			}
		})
	},
	columns : [{
		xtype : 'ellipsiscolumn',
		header : '叉车司机姓名',
		dataIndex : 'driverName',
		width : 140
	},{
		xtype : 'ellipsiscolumn',
		header : '叉车司机工号',
		dataIndex : 'driverCode',
		width : 140
	},{
		text : '当日电叉分货效率',
		columns : [{
			xtype : 'ellipsiscolumn',
			header : '票均效率',
			dataIndex : 'effPerVoteDay',
			width : 130
		},{
			xtype : 'ellipsiscolumn',
			header : '板均效率',
			dataIndex : 'effPerTrayDay',
			width : 130
		}]
	},{
		text : '当月电叉分货效率',
		columns : [{
			xtype : 'ellipsiscolumn',
			header : '票均效率',
			dataIndex : 'effPerVoteMonth',
			width : 130
		},{
			xtype : 'ellipsiscolumn',
			header : '板均效率',
			dataIndex : 'effPerTrayMonth',
			width : 130
		}]
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext
				.create('Foss.platform.forkliftGoodsEfficiencyIndex.storeOfDriver');
		
		me.callParent([cfg]);
	}
});

/**
 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!      mainPanel
 */
Ext.define('Foss.platform.forkliftGoodsEfficiencyIndex.MainTabPanel', {
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
				title : '分货效率明细',
				items : Ext.create('Foss.platform.forkliftGoodsEfficiencyIndex.PanelOfTfr')
			}, {
				tabConfig : {
					width : 100
				},
				title : '叉车司机数据',
				items : Ext.create('Foss.platform.forkliftGoodsEfficiencyIndex.PanelOfOrg')
			}]
});
/** -----------------------------------------------viewpanel--------------------------------------------------* */
Ext.onReady(function() {
	Ext.QuickTips.init();
	platform.forkliftGoodsEfficiencyIndex.MainTabPanel = Ext
			.create('Foss.platform.forkliftGoodsEfficiencyIndex.MainTabPanel');
	Ext.create('Ext.panel.Panel', {
				id : 'T_platform-forkliftGoodsEfficiencyIndex_content',
				cls : "panelContentNToolbar",
				bodyCls : 'panelContent-body',
				items : [platform.forkliftGoodsEfficiencyIndex.MainTabPanel],
				renderTo : 'T_platform-forkliftGoodsEfficiencyIndex-body'
			});

	//如果当前部门找不到外场，但是找到了经营本部，则自动填写经营本部
	if(!Ext.isEmpty(platform.forkliftGoodsEfficiencyIndex.operationDeptCode)){
		platform.forkliftGoodsEfficiencyIndex.queryFormOfTfr.getForm().findField('forkliftGoodsEfficiencyVo.operationDeptCode').setCombValue(
			platform.forkliftGoodsEfficiencyIndex.operationDeptName,		
			platform.forkliftGoodsEfficiencyIndex.operationDeptCode
		);
		platform.forkliftGoodsEfficiencyIndex.queryFormOfOrg.getForm().findField('forkliftGoodsEfficiencyVo.operationDeptCode').setCombValue(
		platform.forkliftGoodsEfficiencyIndex.operationDeptName,		
		platform.forkliftGoodsEfficiencyIndex.operationDeptCode
		);
	};
	//如果当前部门为外场，则自动填写外场
	if(!Ext.isEmpty(platform.forkliftGoodsEfficiencyIndex.outfieldCode)){
		platform.forkliftGoodsEfficiencyIndex.queryFormOfTfr.getForm().findField('forkliftGoodsEfficiencyVo.transferCenterCode').setCombValue(
			platform.forkliftGoodsEfficiencyIndex.outfieldName,
			platform.forkliftGoodsEfficiencyIndex.outfieldCode
			);
		platform.forkliftGoodsEfficiencyIndex.queryFormOfOrg.getForm().findField('forkliftGoodsEfficiencyVo.transferCenterCode').setCombValue(
			platform.forkliftGoodsEfficiencyIndex.outfieldName,
			platform.forkliftGoodsEfficiencyIndex.outfieldCode
			);
	}
});



































