/**-----------------------------------------------明细窗口--------------------------------------------------**/
Ext.define('Foss.Load.QueryLoadTask.DeliverLoadGapRep.WindowForm',{
	extend: 'Ext.form.Panel',
	layout: 'column',
	frame: false,
	border: false,
	defaults: {
		margin: '2 10 3 5',
		labelWidth: 100
	},
	items: [{
		xtype: 'textfield',
		//labelWidth: 90,
		fieldLabel: load.queryloadtask.i18n('foss.load.queryloadtask.deliverBillNo'),
		name: 'deliverBillNo',
		columnWidth:.33,
		readOnly:true
	},{
		xtype: 'textfield',
		fieldLabel: load.queryloadtask.i18n('foss.load.queryloadtask.reportNo'),
		//labelWidth: 60,
		name: 'reportNo',
		columnWidth:.33,
		readOnly:true
	},{
		xtype: 'textfield',
		fieldLabel: load.queryloadtask.i18n('foss.load.queryloadtask.taskNo'),
		//labelWidth: 60,
		name: 'taskNo',
		columnWidth:.33,
		readOnly:true
	},{
		xtype: 'textfield',
		fieldLabel: load.queryloadtask.i18n('foss.load.queryloadtask.reportState'),
		//labelWidth: 60,
		name: 'state',
		columnWidth:.33,
		readOnly:true
	},{
		xtype: 'textfield',
		fieldLabel: load.queryloadtask.i18n('foss.load.queryloadtask.vehicleNo'),
		//labelWidth: 60,
		name: 'vehicleNo',
		columnWidth:.33,
		readOnly:true
	},{
		xtype: 'textfield',
		fieldLabel: load.queryloadtask.i18n('foss.load.queryloadtask.createTime'),
		columnWidth: .33,
		name: 'createTime',
		readOnly:true
	}]
});

Ext.define('Foss.Load.QueryLoadTask.DeliverLoadGapRep.WindowBottomForm',{
	extend: 'Ext.form.Panel',
	layout: 'column',
	frame: false,
	border: false,
	defaults: {
		margin: '2 3 3 3',
		labelWidth: 100
	},
	items: [{
		xtype: 'textfield',
		//labelWidth: 90,
		fieldLabel: load.queryloadtask.i18n('foss.load.queryloadtask.planLoadGoodsQty'),
		name: 'planLoadGoodsQty',
		columnWidth:.28,
		readOnly:true
	},{
		xtype: 'textfield',
		fieldLabel: load.queryloadtask.i18n('foss.load.queryloadtask.actualLoadGoodsQty'),
		//labelWidth: 60,
		name: 'actualLoadGoodsQty',
		columnWidth:.28,
		readOnly:true
	},{
		xtype: 'textfield',
		fieldLabel: load.queryloadtask.i18n('foss.load.queryloadtask.lackGoodsQtyTot'),
		//labelWidth: 60,
		name: 'lackGoodsQty',
		columnWidth:.28,
		readOnly:true
	},{
		xtype: 'button',
		text: load.queryloadtask.i18n('foss.load.queryloadtask.loadTaskDetail'),
		columnWidth:.16,
		handler:function(){
			var taskNo = Ext.getCmp('Foss_Load_QueryLoadTask_DeliverLoadGapRep_WindowForm_Id').getForm().findField('taskNo').getValue();
			if(taskNo!=null){
				addTab('T_load-loadtaskwaybilldetailIndex',//对应打开的目标页面js的onReady里定义的renderTo
						load.queryloadtask.i18n('foss.load.queryloadtask.loadDetail'),//打开的Tab页的标题
						load.realPath('loadtaskwaybilldetailIndex.action?taskNo='+ taskNo));//对应的页面URL，可以在url后使用?x=123这种形式传参
				load.queryloadtask.gapRepWindow.hide();
			}
		}
	}]
});

Ext.define('Foss.Load.QueryLoadTask.DeliverLoadGapRep.SerialModel',{
	extend:'Ext.data.Model',
	fields:[{name:'wayBillNo',type:'string'},
	        {name:'serialNo',type:'string'},
	        {name:'scanState',type:'string',
				convert: function(value) {
					if (value == 'SCANED') {
						return load.queryloadtask.i18n('foss.load.queryloadtask.SCANED');
					} else if (value == 'UNSCANED') {					
						return load.queryloadtask.i18n('foss.load.queryloadtask.UNSCANED');
					}else if (value == 'BY_HAND') {					
						return load.queryloadtask.i18n('foss.load.queryloadtask.BYHAND');
					}
				}
	        },
	        {name:'goodsState',type:'string',
	        	convert: function(value) {
					if (value == 'NOT_LOADING') {
						//未装车
						return load.queryloadtask.i18n('foss.load.queryloadtask.NOTLOADING');
					} else if (value == 'EXTRA_UNPACKAGE') {					
						//强装-未打包装
						return load.queryloadtask.i18n('foss.load.queryloadtask.EXTRAUNPACKAGE');
					}else if (value == 'EXTRA_MODIFY') {					
						//强装-有更改
						return load.queryloadtask.i18n('foss.load.queryloadtask.EXTRAMODIFY');
					}else if (value == 'EXTRA_PACKAGE_UNOUT_STORAGE') {					
						//强装-代打包装未出库
						return load.queryloadtask.i18n('foss.load.queryloadtask.EXTRAPACKAGEUNOUTSTORAGE');
					}else if (value == 'EXTRA_VALUABLES_UNOUT_STORAGE') {					
						//强装-贵重物品未出库
						return load.queryloadtask.i18n('foss.load.queryloadtask.EXTRAVALUABLESUNOUTSTORAGE');
					}
				}
	        },
	        {name:'scanTime',type:'string'}
	       ]
});

Ext.define('Foss.Load.QueryLoadTask.DeliverLoadGapRep.SerialStore',{
	extend:'Ext.data.Store',
	model:'Foss.Load.QueryLoadTask.DeliverLoadGapRep.SerialModel',
	proxy : {
		type : 'memory',
		reader : {
			type : 'json',
			root : 'vo.reportSerials'
		}
	}
});

Ext.define('Foss.Load.QueryLoadTask.DeliverLoadGapRep.SerialGrid',{
	extend: 'Ext.grid.Panel',
	emptyText : load.queryloadtask.i18n('foss.load.queryloadtask.emptyResult'),
		frame: false,
		border : false,
		autoScroll:true,
		margin: '0 0 0 50',
		columns:[     
			{text: load.queryloadtask.i18n('foss.load.queryloadtask.serialNo'),dataIndex : 'serialNo'},
			{text: load.queryloadtask.i18n('foss.load.queryloadtask.scanState'),dataIndex : 'scanState'},
			{text: load.queryloadtask.i18n('foss.load.queryloadtask.scanTime'),dataIndex : 'scanTime'},
			{text: load.queryloadtask.i18n('foss.load.queryloadtask.goodsState'),dataIndex : 'goodsState'}
		],
		constructor: function(config){
			var me = this,
				cfg = Ext.apply({}, config);
			me.store = Ext.create('Foss.Load.QueryLoadTask.DeliverLoadGapRep.SerialStore').load();
			me.callParent([cfg]);
		},
		bindData :function(record){
			var grid = this;
			var params = {
					'vo.reportWayBill.reportId':record.get('reportId'),
					'vo.reportWayBill.wayBillNo':record.get('wayBillNo')
			};
			Ext.Ajax.request({
				url : load.realPath('queryDeliverLoadGapReportSerials.action'),
				params :params,
				success:function(response){
					var result = Ext.decode(response.responseText);
					grid.store.loadData(result.vo.reportSerials);
				},
				exception:function(response){
					var result = Ext.decode(response.responseText);
					Ext.ux.Toast.msg(load.queryloadtask.i18n('foss.load.queryloadtask.prompt'), result.message, 'error', 3000);
				}
			});
		}
});

Ext.define('Foss.Load.QueryLoadTask.DeliverLoadGapRep.WayBillModel',{
	extend:'Ext.data.Model',
	fields:[{name:'wayBillNo',type:'string'},
	        {name:'gapType',type:'string'},
	        {name:'planLoadQty',type:'int'},
	        {name:'actualLoadQty',type:'int'},
	        {name:'lackGoodsQty',type:'int'},
	        {name:'notes',type:'string'},
	        {name:'transportType',type:'string'},
	        {name:'reportId',type:'string'}
	       ]
});

Ext.define('Foss.Load.QueryLoadTask.DeliverLoadGapRep.WayBillStore',{
	extend:'Ext.data.Store',
	model:'Foss.Load.QueryLoadTask.DeliverLoadGapRep.WayBillModel',
	proxy : {
		type : 'memory',
		reader : {
			type : 'json',
			root : 'loadTaskVo.reportWayBills'
		}
	}
});

Ext.define('Foss.Load.QueryLoadTask.GaprepWayBillGrid',{
	extend: 'Ext.grid.Panel',
	emptyText : load.queryloadtask.i18n('foss.load.queryloadtask.emptyResult'),
	selType:'rowmodel',
	autoScroll:true,
	stripeRows : true, 
	height:310,
	width:690,
	frame: true,
	plugins: [{
		pluginId : 'deliverloadgapreport_rowexpander_plugin_Id',
		ptype: 'rowexpander',
		rowsExpander: false,
		rowBodyElement: 'Foss.Load.QueryLoadTask.DeliverLoadGapRep.SerialGrid'
	}],
	columns:[
		{
			text: load.queryloadtask.i18n('foss.load.queryloadtask.wayBillNo'),
			dataIndex : 'wayBillNo',
			flex: 1.2
		},{
			text: load.queryloadtask.i18n('foss.load.queryloadtask.gapType'),
			dataIndex : 'gapType',
			flex: 1.2
		},{
			text: load.queryloadtask.i18n('foss.load.queryloadtask.planLoadQty'),
			dataIndex : 'planLoadQty',
			flex: 1.4
		},{
			text: load.queryloadtask.i18n('foss.load.queryloadtask.actualLoadQty'),
			dataIndex : 'actualLoadQty',
			flex: 1.4
		},{
			text: load.queryloadtask.i18n('foss.load.queryloadtask.lackGoodsQty'),
			dataIndex : 'lackGoodsQty',
			flex: 1.2
		},{
			text: load.queryloadtask.i18n('foss.load.queryloadtask.transportType'),
			dataIndex : 'transportType',
			flex: 1.2
		},{
			text: load.queryloadtask.i18n('foss.load.queryloadtask.notes'),
			dataIndex : 'notes',
			flex: 1.2
		}
	],
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.Load.QueryLoadTask.DeliverLoadGapRep.WayBillStore');
		me.callParent([cfg]);	
	}
});

load.queryloadtask.gapRepWindow = Ext.create('Ext.window.Window',{
	autoDestroy : true,
	closable : true,
	closeAction : 'hide',
	draggable : true,
	width: 720,
	height : 500,
	modal:true,
    layout: 'column',
    //id: 'windForm',
	title: load.queryloadtask.i18n('foss.load.queryloadtask.gapReportDetail'),//详细信息
	defaults: {
		margin:'5 10 10 5',
		labelWidth:85,
	},
	items:[
	       Ext.create('Foss.Load.QueryLoadTask.DeliverLoadGapRep.WindowForm',{id:'Foss_Load_QueryLoadTask_DeliverLoadGapRep_WindowForm_Id'}),
	       Ext.create('Foss.Load.QueryLoadTask.GaprepWayBillGrid',{id:'Foss_Load_QueryLoadtask_GaprepWayBillGrid_Id'}),
	       Ext.create('Foss.Load.QueryLoadTask.DeliverLoadGapRep.WindowBottomForm',{id:'Foss_Load_QueryLoadTask_DeliverLoadGapRep_WindowBottomForm_Id'})
	]
});