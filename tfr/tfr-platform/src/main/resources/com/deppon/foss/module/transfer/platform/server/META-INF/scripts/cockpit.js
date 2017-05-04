Ext.define('Foss.platform.cockpit.queryForm', {
	id : 'Foss_platform_cockpit_queryForm_id',
	extend : 'Ext.form.Panel',
	title : '查询条件',
	frame : true,
	collapsible : false,
	animCollapse : true,
	defaults : {
		margin : '5 10 5 10',
		labelWidth : 85,
		columnWidth : 1 / 2,
		xtype : 'textfield'
	},
	layout : 'column',
	items : [{
		fieldLabel : '外场',
		name : 'tfrCtr',
		xtype : 'dynamicorgcombselector',
		types : 'ORG',
		transferCenter : 'Y',
		readOnly : !Ext.isEmpty(platform.cockpit.tfrCtrCode),
		columnWidth : 0.4,
		allowBlank: false
	}, {
		xtype : 'textfield',
		fieldLabel : '查询时间',
		name : 'currentTimeStr',
		readOnly : true
	}],
	buttons : ['->', {
		columnWidth : .08,
		xtype : 'button',
		cls : 'yellow_button',
		text : '查询',
		handler : function() {
			var form = this.up('form').getForm();
			if (form.isValid()) {
				platform.cockpit.resultGrid.store.load();
			}
		}
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

Ext.define('Foss.platform.cockpit.gridModel', {
	extend : 'Ext.data.Model',
	fields : [{
		name : 'onDutyNums',
		type : 'string'
	}, {
		name : 'absenteeNums',
		type : 'String'
	}, {
		name : 'tallyNums',
		type : 'string'
	}, {
		name : 'forkNums',
		type : 'string'
	}, {
		name : 'stockWeight',
		type : 'string'
	}, {
		name : 'waitUnloadWeight',
		type : 'string'
	}, {
		name : 'lngDisOnTheWayNums',
		type : 'string'
	}, {
		name : 'shtDisOnTheWayNums',
		type : 'string'
	}, {
		name : 'lngDisArrivedNums',
		type : 'string'
	}, {
		name : 'shtDisArrivedNums',
		type : 'string'
	}, {
		name : 'loadUnloadProgressAbnormalNums',
		type : 'string'
	}, {
		name : 'platformUsageRate',
		type : 'string'
	}, {
		name : 'dispatchStockVolume',
		type : 'string'
	}, {
		name : 'sendBackPct',
		type : 'string'
	}, {
		name : 'stockSaturation',
		type : 'string'
	}]
});

Ext.define('Foss.platform.cockpit.gridStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.platform.cockpit.gridModel',
	proxy : {
		type : 'ajax',
		actionMethods : 'POST',
		url : platform.realPath('findCockpitResult.action'),
		timeout : 300000,
		reader : {
			type : 'json',
			root : 'cockpitVo.reuslt',
			successProperty : 'success'
		}
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	},
	listeners : {
		beforeload : function(store, operation, eOpts) {
			var queryForm = Ext.getCmp('Foss_platform_cockpit_queryForm_id').getForm();
			if (!Ext.isEmpty(queryForm)) {
				var queryParams = queryForm.getValues();
				Ext.apply(operation, {
					params : {
						'cockpitVo.tfrCtrCode' : queryParams.tfrCtr
					}
				});
			}
		},
		load : function( store, records, successful, eOpts){
			var cockpitVo = store.proxy.reader.rawData.cockpitVo;
			Ext.getCmp('Foss_platform_cockpit_queryForm_id').getForm().findField('currentTimeStr')
				.setValue(cockpitVo.currentTimeStr);
		}
	}
});

Ext.define('Foss.platform.cockpit.resultGrid', {
	extend : 'Ext.grid.Panel',
	frame : true,
	title : '查询结果',
	height : 300,
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	emptyText : '查询结果为空',
	sortableColumns : true,
	enableColumnHide : false,
	autoScroll : false,
	collapsible : false,
	animCollapse : false,
	sortableColumns : false,
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.platform.cockpit.gridStore');
		me.columns = [{
			dataIndex : 'onDutyNums',
			align : 'center',
			width : 56,
			xtype : 'ellipsiscolumn',
			text : '上班人数'
		},{
			dataIndex : 'absenteeNums',
			align : 'center',
			width : 56,
			xtype : 'ellipsiscolumn',
			text : '异常人数'
		},{
			dataIndex : 'tallyNums',
			align : 'center',
			width : 95,
			xtype : 'ellipsiscolumn',
			text : '在线理货员人数'
		},{
			dataIndex : 'forkNums',
			align : 'center',
			width : 108,
			xtype : 'ellipsiscolumn',
			text : '在线电叉司机人数'
		},{
			dataIndex : 'stockWeight',
			align : 'center',
			width : 100,
			xtype : 'ellipsiscolumn',
			text : '货台库存货量(吨)'
		},{
			dataIndex : 'waitUnloadWeight',
			align : 'center',
			width : 75,
			xtype : 'ellipsiscolumn',
			text : '待卸货量(吨)'
		},{
			dataIndex : 'lngDisOnTheWayNums',
			align : 'center',
			width : 95,
			xtype : 'ellipsiscolumn',
			text : '长途在途车辆数'
		},{
			dataIndex : 'shtDisOnTheWayNums',
			align : 'center',
			width : 95,
			xtype : 'ellipsiscolumn',
			text : '短途在途车辆数'
		},{
			dataIndex : 'lngDisArrivedNums',
			align : 'center',
			width : 120,
			xtype : 'ellipsiscolumn',
			text : '长途到达未卸车辆数'
		},{
			dataIndex : 'shtDisArrivedNums',
			align : 'center',
			width : 120,
			xtype : 'ellipsiscolumn',
			text : '短途到达未卸车辆数'
		},{
			dataIndex : 'loadUnloadProgressAbnormalNums',
			align : 'center',
			width : 108,
			xtype : 'ellipsiscolumn',
			text : '装卸车进度异常数'
		},{
			dataIndex : 'platformUsageRate',
			align : 'center',
			width : 70,
			xtype : 'ellipsiscolumn',
			text : '月台使用率'
		},{
			dataIndex : 'dispatchStockVolume',
			align : 'center',
			width : 76,
			xtype : 'ellipsiscolumn',
			text : '派送库存体积'
		},{
			dataIndex : 'sendBackPct',
			align : 'center',
			width : 70,
			xtype : 'ellipsiscolumn',
			text : '派送退单率'
		},{
			dataIndex : 'stockSaturation',
			align : 'center',
			width : 70,
			xtype : 'ellipsiscolumn',
			text : '仓库饱和度'
		}];
		platform.cockpit.resultGrid = me;
		me.callParent([cfg]);
	}
});

Ext.onReady(function() {
	var queryForm = Ext.create('Foss.platform.cockpit.queryForm');
	var resultGrid = Ext.create('Foss.platform.cockpit.resultGrid');

	var tfrCtrField = queryForm.getForm().findField('tfrCtr');
	tfrCtrField.setCombValue(platform.cockpit.tfrCtrName, platform.cockpit.tfrCtrCode);
	Ext.create('Ext.panel.Panel', {
		id : 'T_platform-cockpitIndex_content',
		cls : "panelContentNToolbar",
		bodyCls : 'panelContent-body',
		layout : 'auto',
		items : [queryForm, resultGrid],
		renderTo : 'T_platform-cockpitIndex-body'
	});
});