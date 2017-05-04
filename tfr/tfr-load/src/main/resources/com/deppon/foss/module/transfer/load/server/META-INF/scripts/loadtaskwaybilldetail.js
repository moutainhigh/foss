//装车任务Model
Ext.define('Foss.load.queryloadtask.LoadTaskModel', {
	extend : 'Ext.data.Model',
	//定义字段
	fields : [{
		name : 'id',
		type : 'string'
	},{
		name : 'taskNo',
		type : 'string'
	}, {
		name : 'state',
		type : 'string'
	}, {
		name : 'taskType',
		type : 'string'
	}, {
		name : 'platformNo',
		type : 'string'
	}, {
		name : 'gaprepNo',
		type : 'string'
	}, {
		name : 'vehicleNo',
		type : 'string'
	}, {
		name : 'line',
		type : 'string'
	}, {
		name : 'loaderName',
		type : 'string'
	}, {
		name : 'loadStartTime',
		type : 'string'
	},{
		name: 'source',
		type: 'string'
	}, {
		name : 'loadEndTime',
		type : 'string'
	}, {
		name : 'planDepartTime',
		type : 'date',
		convert: function(value) {
			if (!Ext.isEmpty(value)) {
				var date = new Date(value);						
				return Ext.Date.format(date,'Y-m-d H:i:s');
			} else {
				return null;
			}
		}
	}]
});
//装车类型
Ext.define('Foss.load.Queryloadtask.LoadTaskForm.taskType.Store', {
	extend: 'Ext.data.Store',
	fields: [
		{name: 'code',  type: 'string'},
		{name: 'name',  type: 'string'}
	],
	proxy: {
		type: 'memory',
		reader: {
			type: 'json',
			root: 'items'
		}
	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

//装车状态
Ext.define('Foss.load.Queryloadtask.LoadTaskForm.State.Store', {
	extend: 'Ext.data.Store',
	fields: [
		{name: 'code',  type: 'string'},
		{name: 'name',  type: 'string'}
	],
	proxy: {
		type: 'memory',
		reader: {
			type: 'json',
			root: 'items'
		}
	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

Ext.define('Foss.load.queryloadtask.LoadWayBillDetailModel',{
	extend:'Ext.data.Model',
	fields: [
		{name: 'id', type: 'string'},
		{name: 'origOrgName' , type: 'string'},
		{name: 'reachOrgName' , type: 'string'},
		{name: 'waybillNo' , type: 'string'},
		{name: 'transportType' , type: 'string'},
		{name: 'stockQty' , type: 'string'},
		{name: 'scanQty' , type: 'string'},
		{name: 'loadQty' , type: 'string'},
		{name: 'stockWeight' , type: 'string'},
		{name: 'stockVolume' , type: 'string'},
		{name: 'goodsName' , type: 'string'},
		{name: 'pack' , type: 'string'},
		{name: 'source' , type: 'string'},
		{name: 'cargoType' , type: 'string'}
	]
});

//装卸车人员Mode
Ext.define('Foss.load.queryloadtask.ScanDetailModel',{
	extend:'Ext.data.Model',
	fields: [
		{name: 'id', type: 'string'},
		{name: 'serialNo' , type: 'string'},
		{name: 'scanState' , type: 'string'},
		{name: 'goodsState' , type: 'string'},
		{name: 'beLoaded' , type: 'string'},
		{name: 'loadTime' , type: 'string', 
			convert: function(value) {
				if (!Ext.isEmpty(value)) {
					var date = new Date(value);						
					return Ext.Date.format(date,'Y-m-d H:i:s');
				} else {
					return null;
				}
			}
		}
	]
});

//LoadTaskStore
Ext.define('Foss.load.queryloadtask.LoadTaskStore',{
	extend: 'Ext.data.Store',
	model: 'Foss.load.queryloadtask.LoadWayBillDetailModel',
	proxy: {
        type: 'memory',
		reader: {
			type: 'json',
			root: 'items'
		}
	}
});

Ext.define('Foss.load.queryloadtask.LoadTaskForm', {
	extend:'Ext.form.Panel',
	frame: true,
	animCollapse: true,
	defaultType: 'textfield',
	layout:'column',
	height: 115,
	id:'Foss_Load_LoadTaskForm_Id',
	defaults: {
		margin: '5 5 5 5',
		labelWidth: 100,
		readOnly : true
	},
	items:[{
		name: 'id',
		xtype: 'hiddenfield',
		columnWidth: .0
	},{
		name: 'taskNo',
		fieldLabel: load.loadtaskwaybilldetail.i18n('foss.load.queryloadtask.taskNo'),
		columnWidth: .25
	},{
		xtype: 'combobox',
		name:'state',
		fieldLabel: load.loadtaskwaybilldetail.i18n('foss.load.queryloadtask.state'),
		columnWidth:.25,
		displayField: 'name',
		valueField:'code', 
		queryMode:'local',
		triggerAction:'all',
		editable:false,
		store: Ext.create('Foss.load.Queryloadtask.LoadTaskForm.State.Store',{
			data: {
				'items':[
					{'code':'ALL','name':load.loadtaskwaybilldetail.i18n('foss.load.queryloadtask.ALL')},
					{'code':'LOADING','name':load.loadtaskwaybilldetail.i18n('foss.load.queryloadtask.LOADING')},
					{'code':'FINISHED','name':load.loadtaskwaybilldetail.i18n('foss.load.queryloadtask.FINISHED')},
					{'code':'SUBMITED','name':load.loadtaskwaybilldetail.i18n('foss.load.queryloadtask.SUBMITED')},
					{'code':'CANCELED','name':load.loadtaskwaybilldetail.i18n('foss.load.queryloadtask.CANCELED')}
				]
			}
		})
	},{
		xtype: 'combobox',
		name:'taskType',
		fieldLabel: load.loadtaskwaybilldetail.i18n('foss.load.queryloadtask.taskType'),
		columnWidth:.25,
		displayField: 'name',
		valueField:'code', 
		queryMode:'local',
		triggerAction:'all',
		autoSelect: true,
		editable:false,
		store: Ext.create('Foss.load.Queryloadtask.LoadTaskForm.taskType.Store',{
			data: {
				'items':[
					{'code':'ALL','name':load.loadtaskwaybilldetail.i18n('foss.load.queryloadtask.ALL')},
					{'code':'DELIVER_LOAD','name':load.loadtaskwaybilldetail.i18n('foss.load.queryloadtask.DELIVERLOAD')},
					{'code':'PARTIALLINE_LOAD','name':load.loadtaskwaybilldetail.i18n('foss.load.queryloadtask.PARTIALLINELOAD')},
					{'code':'LONG_DISTANCE_LOAD','name':load.loadtaskwaybilldetail.i18n('foss.load.queryloadtask.LONGDISTANCELOAD')},
					{'code':'SHORT_DISTANCE_LOAD','name':load.loadtaskwaybilldetail.i18n('foss.load.queryloadtask.SHORTDISTANCELOAD')},
					{'code':'LDP_LOAD','name':load.loadtaskwaybilldetail.i18n('foss.load.queryloadtask.LDPLOAD')},
					{'code':'DIVISION_LOAD','name':load.loadtaskwaybilldetail.i18n('foss.load.queryloadtask.DIVISIONLOAD')},
					{'code':'EXPRESS_PICK_LOAD','name':load.loadtaskwaybilldetail.i18n('foss.load.queryloadtask.EXPRESSPICKLOAD')},
					{'code':'EXPRESS_CONNECTION_LOAD','name':load.loadtaskwaybilldetail.i18n('foss.load.queryloadtask.CONNECTIONLOAD')},
					{'code':'EXPRESS_SENDPIECE_LOAD','name': load.loadtaskwaybilldetail.i18n('foss.load.queryloadtask.EXPRESSSENDPIECELOAD')}
				]
			}
		})
	},{
		name: 'platformNo',
		fieldLabel: load.loadtaskwaybilldetail.i18n('foss.load.queryloadtask.platformNo'),
		columnWidth: .25
	},{
		name: 'gaprepNo',
		fieldLabel: load.loadtaskwaybilldetail.i18n('foss.load.queryloadtask.gaprepNo'),
		columnWidth: .25
	},{
		name: 'vehicleNo',
		fieldLabel: load.loadtaskwaybilldetail.i18n('foss.load.queryloadtask.vehicleNo'),
		columnWidth: .25
	},{
		name: 'line',
		fieldLabel: load.loadtaskwaybilldetail.i18n('foss.load.queryloadtask.line'),
		columnWidth: .25
	},{
		xtype: 'textfield',
		name:'loaderName',
		fieldLabel: load.loadtaskwaybilldetail.i18n('foss.load.queryloadtask.loaderName'),
		columnWidth:.25,
		html: '&nbsp;',
		tipConfig: {
			title: load.loadtaskwaybilldetail.i18n('foss.load.queryloadtask.loaderName'),
			maxWidth: 425,
			width: 425,
			height: 210,
			//是否随鼠标滑动
			trackMouse: true,
			//隐藏的延迟时间,默认为500ms
			//hideDelay: 2000,
			//普通Form上必须配置tipType(区分普通Form和行展开的Form)
			tipType: 'normal',
			//tipBodyElement
			tipBodyElement: 'Foss.load.queryloadtask.LoaderGrid'
		}
	},{
		xtype: 'textfield',
		name:'loadStartTime',
		fieldLabel: load.loadtaskwaybilldetail.i18n('foss.load.queryloadtask.loadStartTime'),
		columnWidth:.3,
		html: '&nbsp;'
	},{
		xtype: 'textfield',
		name:'loadEndTime',
		fieldLabel: load.loadtaskwaybilldetail.i18n('foss.load.queryloadtask.loadEndTime'),
		columnWidth:.3,
		html: '&nbsp;'
	},{
		xtype: 'textfield',
		name:'planDepartTime',
		fieldLabel: load.loadtaskwaybilldetail.i18n('foss.load.queryloadtask.planDepartTime'),
		columnWidth:.3,
		html: '&nbsp;'
	}]
});

//理货员信息
Ext.define('Foss.load.queryloadtask.LoaderGrid',{
	extend: 'Ext.grid.Panel',
	title: load.loadtaskwaybilldetail.i18n('foss.load.queryloadtask.loaderName'),
    stripeRows: true,
    frame: true,
	animCollapse: true,
	autoScroll: true,
	height:210,
	columns: [{
			header: load.loadtaskwaybilldetail.i18n('foss.load.queryloadtask.loaderCode'), 
			dataIndex: 'loaderCode',
			flex : 1
		},{
			header: load.loadtaskwaybilldetail.i18n('foss.load.queryloadtask.loaderName'), 
			dataIndex: 'loaderName',
			flex : 1
		},{
			header: load.loadtaskwaybilldetail.i18n('foss.load.queryloadtask.joinTime'), 
			dataIndex: 'joinTime',
			flex : 1.8
		},{
			header: load.loadtaskwaybilldetail.i18n('foss.load.queryloadtask.leaveTime'), 
			dataIndex: 'leaveTime',
			flex : 1.8
		}],
	bindData: function(record){
		var id = Ext.getCmp('Foss_Load_LoadTaskForm_Id').form.findField('id').getValue();
		var params = {
			'loadTaskVo.loaderParticipation.taskId' : id
		},
		me = this;
		Ext.Ajax.request({
			url : load.realPath('queryLoaderByTaskId.action'),
			params : params,
			success : function(response) {
				var result = Ext.decode(response.responseText),
					store = me.store;
				store.model = 'Foss.queryloadtask.LoaderModel';
				store.remoteSort = 'false';
				store.loadData(result.loadTaskVo.loaderParticipationList);
			}
		});
	},
	constructor: function(config){
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

//表格panel
Ext.define('Foss.load.queryloadtask.LoadWayBillResult', {
	extend:'Ext.grid.Panel',
    bodyCls: 'autoHeight',
	cls: 'autoHeight',
    stripeRows: true,
    frame: true,
	animCollapse: true,
	autoScroll: true,
	height:500,
	store: null,
	columns: [{
			header: load.loadtaskwaybilldetail.i18n('foss.load.queryloadtask.origOrgName'), 
			dataIndex: 'origOrgName',
			flex : 1
		},{
			header: load.loadtaskwaybilldetail.i18n('foss.load.queryloadtask.reachOrgName'), 
			dataIndex: 'reachOrgName',
			flex : 1
		},{
			header: load.loadtaskwaybilldetail.i18n('foss.load.queryloadtask.wayBillNo'),
			dataIndex: 'waybillNo',
			renderer : function(value, cellmeta, record, rowIndex, columnIndex, store){
				if(value != null){
					var id = record.get('id');
					if(id == '') {
						id = record.get('waybillNo');
					}
					var cargoType = record.get('cargoType');
					return '<a href="javascript:load.loadtaskwaybilldetail.showScanDetail('+"'" + id + "','"+ cargoType + "'" + ')">' + value + '</a>';
				}else{
					return null;
				}
			},
			flex : 1
		},{
			header: load.loadtaskwaybilldetail.i18n('foss.load.queryloadtask.transportType'), 
			dataIndex: 'transportType',
			renderer : function(value, cellmeta, record, rowIndex, columnIndex, store){
				if(load.loadtaskwaybilldetail.source == 1){
					var cargoType = record.get('cargoType');
					var val = "快递";
					if(value != "") {
						val = value;
					}
					if(cargoType == 'PACKAGE') {
						val = "包";
					} else if(cargoType == 'CAGE') {
						val = "笼";
					} 
					return val;
				}else{
					return value;
				}
			},
		    flex: 1
		},{
			header: load.loadtaskwaybilldetail.i18n('foss.load.queryloadtask.stockQty'),
			dataIndex: 'stockQty',
			flex : 1
		},{
			header: load.loadtaskwaybilldetail.i18n('foss.load.queryloadtask.scanQty'), 
			dataIndex: 'scanQty',
			flex: 1
		},{
			header: load.loadtaskwaybilldetail.i18n('foss.load.queryloadtask.loadQty'), 
			dataIndex: 'loadQty',
			flex: 1
		},{
			header: load.loadtaskwaybilldetail.i18n('foss.load.queryloadtask.stockWeight'), 
			dataIndex: 'stockWeight',
			flex: 1,
			renderer : function(value) {
				if(!Ext.isEmpty(value)) {
					value = parseFloat(value);
					value = value.toFixed(3);
				}
				return value;
			}
		},{
			header: load.loadtaskwaybilldetail.i18n('foss.load.queryloadtask.stockVolume'), 
			dataIndex: 'stockVolume',
			flex: 1.8,
			renderer : function(value) {
				if(!Ext.isEmpty(value)) {
					value = parseFloat(value);
					value = value.toFixed(3);
				}
				return value;
			}
		},{
			header: load.loadtaskwaybilldetail.i18n('foss.load.queryloadtask.goodsName'), 
			dataIndex: 'goodsName',
			flex: 1.8
		},{
			header: load.loadtaskwaybilldetail.i18n('foss.load.queryloadtask.pack'), 
			dataIndex: 'pack',
			flex: 1.8
		}
		],
		constructor: function(config){
			var me = this,
				cfg = Ext.apply({}, config);
			me.store = Ext.create('Foss.load.queryloadtask.LoadTaskStore');
			me.tbar = [{
				xtype : 'button',
				text : load.loadtaskwaybilldetail.i18n('foss.load.queryloadtask.export'),
				name : 'export',
				handler : function(){
					var queryParams = load.loadtaskwaybilldetail.loadTaskForm.getForm().getValues();
					if(!Ext.fly('downloadAttachFileForm')){
						    var frm = document.createElement('form');
						    frm.id = 'downloadAttachFileForm';
						    frm.style.display = 'none';
						    document.body.appendChild(frm);
					}		
					Ext.Ajax.request({
						url:load.realPath('exportLoadWayBillByTaskNo.action'),
						form: Ext.fly('downloadAttachFileForm'),
						method : 'POST',
						params : {'loadTaskVo.loadTaskDto.taskNo': load.loadtaskwaybilldetail.taskNo,'loadTaskVo.loadTaskDto.source': load.loadtaskwaybilldetail.source},
		    			isUpload: true,
		    			exception : function(response) {
		    				var result = Ext.decode(response.responseText);
		    				top.Ext.MessageBox.alert(load.loadtaskwaybilldetail.i18n('foss.load.queryloadtask.exportFail'),result.message);
		    			}
					});
				}
			}];
			me.callParent([cfg]);
		}
});

//扫描明细Store
Ext.define('Foss.load.queryloadtask.ScanDetailGrid.Store', {
	extend: 'Ext.data.Store',
	model: 'Foss.load.queryloadtask.ScanDetailModel',
	proxy: {
		type: 'memory',
		reader: {
			type: 'json',
			root: 'items'
		}
	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

//扫描明细
Ext.define('Foss.load.queryloadtask.ScanDetailGrid',{
	extend: 'Ext.grid.Panel',
	title: load.loadtaskwaybilldetail.i18n('foss.load.queryloadtask.scanDetail'),
    stripeRows: true,
    frame: true,
	animCollapse: true,
	autoScroll: true,
	height:300,
	columns: [{
		//	header: load.loadtaskwaybilldetail.i18n('foss.load.queryloadtask.serialNo'),  
			header: load.loadtaskwaybilldetail.i18n('foss.load.queryloadtask.serialNoNew'),
			dataIndex: 'serialNo',
			renderer : function(value, cellmeta, record, rowIndex, columnIndex, store){
				if(load.loadtaskwaybilldetail.source == 1){
					var cargoType = record.get('cargoType');
					if(cargoType == 'PACKAGE') {
						return '<a href="javascript:load.loadtaskwaybilldetail.showScanDetail('+"'" + value + "','"+ cargoType + "'" + ')">' + value + '</a>';;
					} else if(cargoType == 'CAGE') {
						return '<a href="javascript:load.loadtaskwaybilldetail.showScanDetail('+"'" + value + "','"+ cargoType + "'" + ')">' + value + '</a>';;
					} else {
						return value;
					}
				}else{
					return value;
				}
			},
						
			flex : 1
		},{
			header: load.loadtaskwaybilldetail.i18n('foss.load.queryloadtask.scanState'), 
			dataIndex: 'scanState',
			flex : 1,
			renderer : function(value) {
				switch(value) {
					case 'SCANED':
						return load.loadtaskwaybilldetail.i18n('foss.load.queryloadtask.SCANED');
					case 'UNSCANED':
						return load.loadtaskwaybilldetail.i18n('foss.load.queryloadtask.UNSCANED');
					case 'BY_HAND':
						return load.loadtaskwaybilldetail.i18n('foss.load.queryloadtask.BYHAND');
					case 'CANCELED':
						return load.loadtaskwaybilldetail.i18n('foss.load.queryloadtask.CANCELED');
					case 'N/A':
						return '';
					default: return value;
				}
			}
		},{
			header: load.loadtaskwaybilldetail.i18n('foss.load.queryloadtask.goodsState'), 
			dataIndex: 'goodsState',
			flex : 1,
			renderer : function(value) {
				switch(value) {
					case 'NORMAL':
						//正常
						return load.loadtaskwaybilldetail.i18n('foss.load.queryloadtask.NORMAL');
					case 'NOT_LOADING':
						//未装车
						return load.loadtaskwaybilldetail.i18n('foss.load.queryloadtask.NOTLOADING');
					case 'MORE':
						//多货
						return load.loadtaskwaybilldetail.i18n('foss.load.queryloadtask.MORE');
					case 'INVALID':
						//无效
						return load.loadtaskwaybilldetail.i18n('foss.load.queryloadtask.INVALID');
					case 'MORE_ENTRAINED':
						//多货-夹带
						return load.loadtaskwaybilldetail.i18n('foss.load.queryloadtask.MOREENTRAINED');
					case 'MORE_ALLOPATRY_ENTRAINED':
						//多货-异地夹带
						return load.loadtaskwaybilldetail.i18n('foss.load.queryloadtask.MOREALLOPATRYENTRAINED');
					case 'EXTRA':
						//强装
						return load.loadtaskwaybilldetail.i18n('foss.load.queryloadtask.EXTRA');
					case 'EXTRA_ENTRAINED':
						//强装-夹带
						return load.loadtaskwaybilldetail.i18n('foss.load.queryloadtask.EXTRAENTRAINED');
					case 'EXTRA_ALLOPATRY_ENTRAINED':
						//强装-异地夹带
						return load.loadtaskwaybilldetail.i18n('foss.load.queryloadtask.EXTRAALLOPATRYENTAINED');
					case 'EXTRA_UNPACKAGE':
						//强装-未打包装
						return load.loadtaskwaybilldetail.i18n('foss.load.queryloadtask.EXTRAUNPACKAGE');
					case 'EXTRA_MODIFY':''
						//强装-有更改
						return load.loadtaskwaybilldetail.i18n('foss.load.queryloadtask.EXTRAMODIFY');
					case 'EXTRA_PACKAGE_UNOUT_STORAGE':
						//强装-代打包装未出库
						return load.loadtaskwaybilldetail.i18n('foss.load.queryloadtask.EXTRAPACKAGEUNOUTSTORAGE');
					case 'EXTRA_VALUABLES_UNOUT_STORAGE':
						//强装-贵重物品未出库
						return load.loadtaskwaybilldetail.i18n('foss.load.queryloadtask.EXTRAVALUABLESUNOUTSTORAGE');
					case 'EXTRA_UNPREWIRED':
						//强装-未预配
						return load.loadtaskwaybilldetail.i18n('foss.load.queryloadtask.EXTRAUNPREWIRED');
					case 'LACK':
						//少货
						return load.loadtaskwaybilldetail.i18n('foss.load.queryloadtask.LACK');
					case 'CANCELED':
						//已取消
						return load.loadtaskwaybilldetail.i18n('foss.load.queryloadtask.CANCELED');
					case 'NORMAL_LOAD':
						//正常装车
						return load.loadtaskwaybilldetail.i18n('foss.load.queryloadtask.NORMAL');
					case 'STRONG_LOAD':
						//强制装车
						return load.loadtaskwaybilldetail.i18n('foss.load.queryloadtask.EXTRA');
					case 'STRONG_LOADENTRAINMENT':
						//强制装车-夹带
						return load.loadtaskwaybilldetail.i18n('foss.load.queryloadtask.EXTRAENTRAINED');
					case 'STRONG_LOADREMOTEENTRAINMENT':
						//强制装车-异地夹带
						return load.loadtaskwaybilldetail.i18n('foss.load.queryloadtask.EXTRAALLOPATRYENTAINED');
					default: return value;
				}
			}
		},{
			header: load.loadtaskwaybilldetail.i18n('foss.load.queryloadtask.beLoaded'), 
			dataIndex: 'beLoaded',
			flex : 1,
			renderer : function(value) {
				switch(value) {
				case 'N':
					return load.loadtaskwaybilldetail.i18n('foss.load.queryloadtask.N');
				case 'Y':
					return load.loadtaskwaybilldetail.i18n('foss.load.queryloadtask.Y');
				default: return value;
				}
			}
		},{
			header: load.loadtaskwaybilldetail.i18n('foss.load.queryloadtask.loadTime'), 
			dataIndex: 'loadTime',
			flex : 1.8
		}],
	constructor: function(config){
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.load.queryloadtask.ScanDetailGrid.Store');
		me.callParent([cfg]);
	}
});

//点击“理货员列”打开理货员窗口
javascript:load.loadtaskwaybilldetail.showScanDetail = function(id,cargoType){
	var grid = Ext.create('Foss.load.queryloadtask.ScanDetailGrid'),
		window = Ext.create('Ext.window.Window', {
		    title: load.loadtaskwaybilldetail.i18n('foss.load.queryloadtask.scanDetail'),
		    height:400,
		    width:600,
		    closable:true,
			closeAction:'hide',
		    modal: true,
		    items: [
		            grid
	        ]
		});
	
	var taskNo = load.loadtaskwaybilldetail.taskNo;
	var source = load.loadtaskwaybilldetail.source;
	var params = {
			'loadTaskVo.loadSerialNo.loadWaybillDetailId' : id,
			'loadTaskVo.loadTaskDto.taskNo': taskNo, 
			'loadTaskVo.loadTaskDto.source': source,
			'loadTaskVo.loadSerialNo.cargoType': cargoType, 
			'loadTaskVo.loadSerialNo.cargoNo': id
	};

	Ext.Ajax.request({
		url : load.realPath('queryloadSerialNoByLoadWaybillDetailId.action'),
		params : params,
		success : function(response) {
			var result = Ext.decode(response.responseText),
				store = grid.store;
			store.loadData(result.loadTaskVo.loadSerialNoList);
			window.show()
		}
	});
}

Ext.onReady(function() {
	//接受前一页面传入的交接单号
	var taskNo = load.loadtaskwaybilldetail.taskNo;
	var source = load.loadtaskwaybilldetail.source;
	//根据交接单号，从后台获取交接单基本信息
	Ext.Ajax.request({
		url: load.realPath('queryLoadWayBillByTaskNo.action'),
		params:{'loadTaskVo.loadTaskDto.taskNo': taskNo, 'loadTaskVo.loadTaskDto.source':source},
		success:function(response){
			Ext.QuickTips.init();
			var loadTaskForm = Ext.create('Foss.load.queryloadtask.LoadTaskForm');
			load.loadtaskwaybilldetail.loadTaskForm = loadTaskForm;
			var loadWayBillResult = Ext.create('Foss.load.queryloadtask.LoadWayBillResult');;
			Ext.create('Ext.panel.Panel',{
				id: 'T_load-loadtaskwaybilldetailIndex_content',
				cls: "panelContentNToolbar",
				bodyCls: 'panelContentNToolbar-body',
				layout: 'auto',
				items: [loadTaskForm, loadWayBillResult],
				renderTo: 'T_load-loadtaskwaybilldetailIndex-body'
			});
			var result = Ext.decode(response.responseText),
				loadTaskDto = result.loadTaskVo.loadTaskDto,
				loadWaybillDetailList = result.loadTaskVo.loadWaybillDetailList;
			var loadTaskRecord =  Ext.ModelManager.create(loadTaskDto, 'Foss.load.queryloadtask.LoadTaskModel');
			loadTaskForm.loadRecord(loadTaskRecord);
			loadWayBillResult.store.loadData(loadWaybillDetailList);
		}
	});
});