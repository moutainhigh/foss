/****************4.异常封签信息对比结果界面  begin**************/
Ext.define('Foss.seal.Compare.SealDetailModel',{
	extend:'Ext.data.Model',
	fields: [
		{name: 'id', type: 'string'},
		{name: 'sealType', type: 'string'},
		{name: 'sealNo' , type: 'string'},
		{name: 'inspected' , type: 'boolean'},
		{name: 'isdiff' , type: 'boolean'}
	]
});

//封签明细Store
Ext.define('Foss.seal.Compare.SealDetailStore',{
	extend: 'Ext.data.Store',
	model: 'Foss.seal.Compare.SealDetailModel',
	proxy: {
        type: 'memory',
		reader: {
			type: 'json',
			root: 'items'
		}
	}
});


//装车Grid
Ext.define('Foss.Seal.Compare.SealOrigGrid', {
	extend:'Ext.grid.Panel',
	autoScroll : true,
	columnLines: true,
	sealType : null,
	columns: [{
		header: load.seal.i18n('foss.load.seal.sealNo'),
		dataIndex: 'sealNo',
		flex : 3,
		renderer: function(value, cellmeta, record, rowIndex, columnIndex, stroe){
			//如果对比结果为有差异, 并且封签为已校验
			//则颜色为红色
			if(record.get('isdiff')) {
				return '<span style="color:red;">' + value + '</span>';
			}
			return value;
		}
	}],
    editor: null,
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.seal.Compare.SealDetailStore');
		me.callParent([cfg]);
	}
});

//卸车Grid
Ext.define('Foss.Seal.Compare.SealDestGrid', {
	extend:'Ext.grid.Panel',
	autoScroll : true,
	columnLines: true,
	sealType : null,
	columns: [{
		header: load.seal.i18n('foss.load.seal.sealNo'),
		dataIndex: 'sealNo',
		flex : 3,
		renderer: function(value, cellmeta, record, rowIndex, columnIndex, stroe){
			//如果对比结果为有差异, 并且封签为已校验
			//则颜色为红色
			if(record.get('isdiff')) {
				return '<span style="color:red;">' + value + '</span>';
			}
			return value;
		}
	}],
    editor: null,
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.seal.Compare.SealDetailStore');
		me.callParent([cfg]);
	}
});

//装车操作panel
Ext.define('Foss.Seal.Compare.SealOrigPanel',{
	extend: 'Ext.form.Panel',
	layout:'column',
	defaultType: 'textfield',
	defaults: {
	    margin: '5 5 5 5'
	},
	items:[{
		name: 'vehicleNo',
		fieldLabel: load.seal.i18n('foss.load.seal.vehicleNo'),
		columnWidth: 1,
		readOnly : true
	},{
		name: 'sealerName',
		fieldLabel: load.seal.i18n('foss.load.seal.sealerName'),
		columnWidth: 1,
		readOnly : true
	},{
		name: 'sealTime',
		fieldLabel: load.seal.i18n('foss.load.seal.operateTime'),
		columnWidth: 1,
		readOnly : true
	}]
});

//卸车操作panel
Ext.define('Foss.Seal.Compare.SealDestPanel',{
	extend: 'Ext.form.Panel',
	layout:'column',
	defaultType: 'textfield',
	defaults: {
	    margin: '5 5 5 5'
	},
	items:[{
		name: 'vehicleNo',
		fieldLabel: load.seal.i18n('foss.load.seal.vehicleNo'),
		columnWidth: 1,
		readOnly : true
	},{
		name: 'checkerCode',
		fieldLabel: load.seal.i18n('foss.load.seal.checkerUser'),
		columnWidth: 1,
		readOnly : true
	},{
		name: 'checkTime',
		fieldLabel: load.seal.i18n('foss.load.seal.operateTime'),
		columnWidth: 1,
		readOnly : true
	}]
});

Ext.define('Foss.Seal.Compare.SealPanel',{
	extend: 'Ext.panel.Panel',
	layout: 'hbox',
	leftPanel: null,
	getLeftPanel : function(){
		if(this.leftPanel == null) {
			this.leftPanel = Ext.create('Ext.panel.Panel', {
				title: load.seal.i18n('foss.load.seal.bindSealNo'),
				frame: true,
				autoScroll: true,
				height : 400,
				width: 305,
				margin: '20 10',
				items: [
					load.seal.sealOrig = load.seal.SealOrigPanel = Ext.create('Foss.Seal.Compare.SealOrigPanel'),
					load.seal.SealOrigGrid = Ext.create('Foss.Seal.Compare.SealOrigGrid')
				]
			});
		}
		return this.leftPanel;
	},
	rightPanel: null,
	getRightPanel : function(){
		if(this.rightPanel == null) {
			this.rightPanel = Ext.create('Ext.panel.Panel', {
				title: load.seal.i18n('foss.load.seal.checkSealNo'),
				frame: true,
				autoScroll: true,
				height : 400,
				width: 305,
				margin: '20 10',
				items: [
					load.seal.sealDest = Ext.create('Foss.Seal.Compare.SealDestPanel'),
					load.seal.SealDestGrid = Ext.create('Foss.Seal.Compare.SealDestGrid')
				]
			});
		}
		return this.rightPanel;
	},
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.items = [
			me.getLeftPanel(),
			me.getRightPanel()
		];
		me.callParent([cfg]);
	}
});

Ext.define('Foss.load.seal.Compare.BillNoGridModel',{
	extend:'Ext.data.Model',
	fields: [
		{name: 'vehicleNo' , type: 'string'},
		{name: 'lineName' , type: 'string'},
		{name: 'driverName' , type: 'string'},
		{name: 'status' , type: 'string'},
		{name: 'billNo' , type: 'string'},
	]
});

Ext.define('Foss.load.seal.Compare.BillNoGridStore',{
	extend: 'Ext.data.Store',
	model: 'Foss.load.seal.Compare.BillNoGridModel',
	proxy: {
        type: 'memory',
		reader: {
			type: 'json',
			root: 'items'
		}
	}
});

//表格panel
Ext.define('Foss.load.Seal.Compare.BillNoGridPanel', {
	extend:'Ext.grid.Panel',
    height:250,
    stripeRows: true,
    frame: true,
	animCollapse: true,
	autoScroll: true,
	store: null,
	columns: [{
			header: load.seal.i18n('foss.load.seal.vehicleNo'), 
			dataIndex: 'vehicleNo',
			flex : 1
		},{
			header: load.seal.i18n('foss.load.seal.lineName'),
			dataIndex: 'lineName',
			flex : 1
		},{
			header: load.seal.i18n('foss.load.seal.driverName'), 
			dataIndex: 'driverName',
		    flex: 1
		},{
			//车辆出发状态
			header: load.seal.i18n('foss.load.seal.status'), 
			dataIndex: 'status',
			width:85,
			renderer : function(value) {
				switch(value) {
					case 'UNDEPART':
						//未出发
						return load.seal.i18n('foss.load.seal.UNDEPART');
					case 'ONTHEWAY':
						//在途
						return load.seal.i18n('foss.load.seal.ONTHEWAY');
					case 'HALFWAY_ARRIVE':
						//中途到达
						return load.seal.i18n('foss.load.seal.HALFWAYARRIVE');
					case 'ARRIVED':
						//已到达
						return load.seal.i18n('foss.load.seal.ARRIVED');
					case 'CANCLED':
						//作废
						return load.seal.i18n('foss.load.seal.CANCLED');
					case 'UNLOADED':
						//已卸车
						return load.seal.i18n('foss.load.seal.UNLOADED');
					default: return value;
				}
			}
		},{
			header: load.seal.i18n('foss.load.seal.billNo'),
			dataIndex: 'billNo',
			flex : 1
		}],
		constructor: function(config){
			var me = this,
				cfg = Ext.apply({}, config);
			load.seal.BillNoGridStore = Ext.create('Foss.load.seal.Compare.BillNoGridStore')
			me.store = load.seal.BillNoGridStore;
			me.callParent([cfg]);
		}
});
/****************4.异常封签信息对比结果界面  end**************/