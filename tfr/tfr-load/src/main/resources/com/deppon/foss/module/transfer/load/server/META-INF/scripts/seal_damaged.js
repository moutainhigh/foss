/****************6.破损封签界面  begin**************/
Ext.define('Foss.seal.Damaged.SealDetailModel',{
	extend:'Ext.data.Model',
	fields: [
		{name: 'id', type: 'string'},
		{name: 'sealType', type: 'string'},
		{name: 'sealNo' , type: 'string'},
		{name: 'inspected' , type: 'boolean'},
		{name: 'isdiff' , type: 'boolean'}
	]
});

//装车Store
Ext.define('Foss.seal.Damaged.SealDetailStore',{
	extend: 'Ext.data.Store',
	model: 'Foss.seal.Damaged.SealDetailModel',
	proxy: {
        type: 'memory',
		reader: {
			type: 'json',
			root: 'items'
		}
	}
});


//装车Grid
Ext.define('Foss.Seal.Damaged.SealOrigGrid', {
	extend:'Ext.grid.Panel',
	autoScroll : true,
	columnLines: true,
	sealType : null,
	columns: [{
		header: load.seal.i18n('foss.load.seal.sealNo'), 
		dataIndex: 'sealNo',
		flex : 3
	}],
    editor: null,
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.seal.Damaged.SealDetailStore');
		me.callParent([cfg]);
	}
});

//装车操作panel
Ext.define('Foss.Seal.Damaged.SealOrigPanel',{
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
Ext.define('Foss.Seal.Damaged.SealDestPanel',{
	extend: 'Ext.form.Panel',
	layout:'column',
	defaultType: 'label',
	defaults: {
	    margin: '100 5 5 5'
	},
	items:[{
		name: 'vehicleNo',
		columnWidth: 1,
		style: {
            width: '95%',
            marginBottom: '10px',
            color: 'red'
        },
        html: load.seal.i18n('foss.load.seal.wrong.sealDamaged')+"　　　　<a target='_blank' href=''>"+load.seal.i18n('foss.load.seal.detail')+"</a>"
        //封签号码破损异常<a>明细</a>
	}]
});

Ext.define('Foss.Seal.Damaged.SealPanel',{
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
					load.seal.sealOrig = load.seal.SealOrigPanel = Ext.create('Foss.Seal.Damaged.SealOrigPanel'),
					load.seal.SealOrigGrid = Ext.create('Foss.Seal.Damaged.SealOrigGrid')
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
					load.seal.sealDest = Ext.create('Foss.Seal.Damaged.SealDestPanel')
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


Ext.define('Foss.load.seal.Damaged.BillNoGridModel',{
	extend:'Ext.data.Model',
	fields: [
		{name: 'vehicleNo' , type: 'string'},
		{name: 'lineName' , type: 'string'},
		{name: 'driverName' , type: 'string'},
		{name: 'status' , type: 'string'},
		{name: 'billNo' , type: 'string'},
	]
});

Ext.define('Foss.load.seal.Damaged.BillNoGridStore',{
	extend: 'Ext.data.Store',
	model: 'Foss.load.seal.Damaged.BillNoGridModel',
	proxy: {
        type: 'memory',
		reader: {
			type: 'json',
			root: 'items'
		}
	}
});

//表格panel
Ext.define('Foss.load.Seal.Damaged.BillNoGridPanel', {
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
			load.seal.BillNoGridStore = Ext.create('Foss.load.seal.Damaged.BillNoGridStore');
			me.store = load.seal.BillNoGridStore;
			me.callParent([cfg]);
		}
});

/****************4.破损封签界面  end**************/