//卸车任务Model
Ext.define('Foss.unload.unloadtaskdetailquery.UnloadTaskModel',{
	extend:'Ext.data.Model',
	fields: [
		{name: 'id', type: 'string'},
		{name: 'unloadTaskNo' , type: 'string'},
		{name: 'gaprepNo' , type: 'string'},
		{name: 'unloadWay' , type: 'string'},
		{name: 'vehicleNo' , type: 'string'},
		{name: 'line' , type: 'string'},
		{name: 'loaderName' , type: 'string'},
		{name: 'platformNo' , type: 'string'},
		{name: 'unloadType' , type: 'string'},
		{name: 'taskState' , type: 'string'},
		{
			name : 'arriveTime',
			type : 'date',
			convert: function(value) {
				if (!Ext.isEmpty(value)) {
					var date = new Date(value);						
					return Ext.Date.format(date,'Y-m-d H:i:s');
				} else {
					return null;
				}
			}
		},
		{
			name : 'unloadStartTime',
			type : 'date',
			convert: function(value) {
				if (!Ext.isEmpty(value)) {
					var date = new Date(value);						
					return Ext.Date.format(date,'Y-m-d H:i:s');
				} else {
					return null;
				}
			}
		},
		{
			name : 'unloadEndTime',
			type : 'date',
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
//卸车类型
Ext.define('Foss.unload.unloadtaskdetailquery.UnloadTaskForm.taskType.Store', {
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

//卸车状态
Ext.define('Foss.unload.unloadtaskdetailquery.UnloadTaskForm.State.Store', {
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

Ext.define('Foss.unload.unloadtaskdetailquery.UnloadTaskDetailModel',{
	extend:'Ext.data.Model',
	fields: [
		{name: 'id', type: 'string'},
		{name: 'billNo' , type: 'string'},
		{name: 'waybillNo' , type: 'string'},
		{name: 'origOrgCode' , type: 'string'},
		{name: 'origOrgName' , type: 'string'},
		{name: 'destOrgCode' , type: 'string'},
		{name: 'destOrgName' , type: 'string'},
		{name: 'transportType' , type: 'string'},
		{name: 'handoverTotQty' , type: 'string'},
		{name: 'operationGoodsQty' , type: 'string'},
		{name: 'scanGoodsQty' , type: 'string'},
		{name: 'scanGoodsQtyRate' , type: 'string'},
		{name: 'unloadWeightTotal' , type: 'string'},
		{name: 'unloadVolumeTotal' , type: 'string'},
		{name: 'goodsName' , type: 'string'},
		{name: 'pack' , type: 'string'},
		{name: 'expressOrLingdan' , type: 'string'},
		{name: 'unloadTaskNo' , type: 'string'},
		{name: 'cargoNo' , type: 'string'},
		{name: 'cargoType' , type: 'string'},
	]
});

//装卸车人员Mode
Ext.define('Foss.unload.unloadtaskdetailquery.ScanDetailModel',{
	extend:'Ext.data.Model',
	fields: [
		{name: 'id', type: 'string'},
		{name: 'serialNo' , type: 'string'},
		{name: 'scanStatus' , type: 'string'},
		{name: 'goodsStatus' , type: 'string'},
		{name: 'optTime' , type: 'string', 
			convert: function(value) {
				if (!Ext.isEmpty(value)) {
					var date = new Date(value);						
					return Ext.Date.format(date,'Y-m-d H:i:s');
				} else {
					return value;
				}
			}
		}
	]
});

//LoadTaskStore
Ext.define('Foss.unload.unloadtaskdetailquery.UnloadTaskDetailStore',{
	extend: 'Ext.data.Store',
	model: 'Foss.unload.unloadtaskdetailquery.UnloadTaskDetailModel',
	proxy: {
        type: 'memory',
		reader: {
			type: 'json',
			root: 'items'
		}
	}
});

Ext.define('Foss.unload.unloadtaskdetailquery.UnloadTaskForm', {
	extend:'Ext.form.Panel',
	frame: true,
	animCollapse: true,
	defaultType: 'textfield',
	layout:'column',
	height: 115,
	id:'Foss_Unload_UnloadTaskForm_Id',
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
		name: 'unloadTaskNo',
		fieldLabel: unload.unloadtaskdetailquery.i18n('foss.unload.unloadtaskquery.unloadTaskNo'),
		columnWidth: .25
	},{
		xtype: 'combobox',
		name:'taskState',
		fieldLabel: unload.unloadtaskdetailquery.i18n('foss.unload.unloadtaskquery.taskState'),
		columnWidth:.25,
		displayField: 'name',
		valueField:'code', 
		queryMode:'local',
		triggerAction:'all',
		editable:false,
		store: Ext.create('Foss.unload.unloadtaskdetailquery.UnloadTaskForm.State.Store',{
			data: {
				'items':[
					{'code':'ALL','name':unload.unloadtaskdetailquery.i18n('foss.unload.unloadtaskquery.ALL')},
					{'code':'UNLOADING','name':unload.unloadtaskdetailquery.i18n('foss.unload.unloadtaskquery.UNLOADING')},
					{'code':'FINISHED','name':unload.unloadtaskdetailquery.i18n('foss.unload.unloadtaskquery.FINISHED')},
					{'code':'CANCELED','name':unload.unloadtaskdetailquery.i18n('foss.unload.unloadtaskquery.CANCELED')}
				]
			}
		})
	},{
		xtype: 'combobox',
		name:'unloadType',
		fieldLabel: unload.unloadtaskdetailquery.i18n('foss.unload.unloadtaskquery.unloadType'),
		columnWidth:.25,
		displayField: 'name',
		valueField:'code', 
		queryMode:'local',
		triggerAction:'all',
		autoSelect: true,
		editable:false,
		store: Ext.create('Foss.unload.unloadtaskdetailquery.UnloadTaskForm.taskType.Store',{
			data: {
				'items':[
					{'code':'LONG_DISTANCE','name':unload.unloadtaskdetailquery.i18n('foss.unload.unloadtaskquery.LONGDISTANCE')},
					{'code':'SHORT_DISTANCE','name':unload.unloadtaskdetailquery.i18n('foss.unload.unloadtaskquery.SHORTDISTANCE')},
					{'code':'EXPRESS_LONG_DISTANCE','name':unload.unloadtaskdetailquery.i18n('foss.unload.unloadtaskquery.LONGDISTANCE')},
					{'code':'EXPRESS_SHORT_DISTANCE','name':unload.unloadtaskdetailquery.i18n('foss.unload.unloadtaskquery.SHORTDISTANCE')},
					{'code':'DELIVER','name':unload.unloadtaskdetailquery.i18n('foss.unload.unloadtaskquery.DELIVER')},
					{'code':'DIVISION','name':unload.unloadtaskdetailquery.i18n('foss.unload.unloadtaskquery.DIVISION')},
					{'code':'PACKAGE_AIR','name':unload.unloadtaskdetailquery.i18n('foss.unload.unloadtaskquery.EXPRESSAIR')},
					{'code':'PACKAGE_AIR','name':unload.unloadtaskdetailquery.i18n('foss.unload.unloadtaskquery.PACKAGEAIR')},
					{'code':'EXPRESS_PICK','name':unload.unloadtaskdetailquery.i18n('foss.unload.unloadtaskquery.EXPRESSDELIVER')},
					{'code':'BUSINESS_AIR','name':unload.unloadtaskdetailquery.i18n('foss.unload.unloadtaskquery.BUSINESSAIR')},
					{'code':'ELECTRANSPORT','name':unload.unloadtaskdetailquery.i18n('foss.unload.unloadtaskquery.ELECTRANSPORT')}
				]
			}
		})
	},{
		name: 'platformNo',
		fieldLabel: unload.unloadtaskdetailquery.i18n('foss.unload.unloadtaskquery.platformNo'),
		columnWidth: .25
	},{
		name: 'vehicleNo',
		fieldLabel: unload.unloadtaskdetailquery.i18n('foss.unload.unloadtaskquery.vehicleNo'),
		columnWidth: .25
	},{
		xtype: 'textfield',
		name:'arriveTime',
		fieldLabel: unload.unloadtaskdetailquery.i18n('foss.unload.unloadtaskquery.arriveTime'),
		columnWidth:.25,
		html: '&nbsp;'
	},{
		xtype: 'combobox',
		name:'unloadWay',
		fieldLabel: unload.unloadtaskdetailquery.i18n('foss.unload.unloadtaskquery.unloadWay'),
		columnWidth:.25,
		displayField: 'name',
		valueField:'code', 
		queryMode:'local',
		triggerAction:'all',
		editable:false,
		store: Ext.create('Foss.unload.unloadtaskdetailquery.UnloadTaskForm.State.Store',{
			data: {
				'items':[
					{'code':'PDA','name':unload.unloadtaskdetailquery.i18n('foss.unload.unloadtaskquery.PDA')},
					{'code':'NO_PDA','name':unload.unloadtaskdetailquery.i18n('foss.unload.unloadtaskquery.NOPDA')}
				]
			}
		})
	},{
		xtype: 'textfield',
		name:'loaderName',
		fieldLabel: unload.unloadtaskdetailquery.i18n('foss.unload.unloadtaskquery.loaderName'),
		columnWidth:.25,
		html: '&nbsp;',
		tipConfig: {
			title: unload.unloadtaskdetailquery.i18n('foss.unload.unloadtaskquery.loaderName'),
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
			tipBodyElement: 'Foss.unload.unloadtaskdetailquery.LoaderGrid'
		}
	},{
		xtype: 'textfield',
		name:'unloadStartTime',
		fieldLabel: unload.unloadtaskdetailquery.i18n('foss.unload.unloadtaskquery.unloadStartTime'),
		columnWidth:.25,
		html: '&nbsp;'
	},{
		xtype: 'textfield',
		name:'unloadEndTime',
		fieldLabel: unload.unloadtaskdetailquery.i18n('foss.unload.unloadtaskquery.unloadEndTime'),
		columnWidth:.25,
		html: '&nbsp;'
	},{
		name: 'gaprepNo',
		fieldLabel: unload.unloadtaskdetailquery.i18n('foss.unload.unloadtaskquery.gaprepNo'),
		columnWidth: .25
	},{
		name: 'line',
		fieldLabel: unload.unloadtaskdetailquery.i18n('foss.unload.unloadtaskquery.line'),
		columnWidth: .25
	}]
});

//装卸车人员Mode
Ext.define('Foss.unload.unloadtaskdetailquery.LoaderModel',{
	extend:'Ext.data.Model',
	fields: [
		{name: 'id', type: 'string'},
		{name: 'loaderCode' , type: 'string'},
		{name: 'loaderName' , type: 'string'},
		{name: 'joinTime' , type: 'string', 
			convert: function(value) {
				if (!Ext.isEmpty(value)) {
					var date = new Date(value);						
					return Ext.Date.format(date,'Y-m-d H:i:s');
				} else {
					return null;
				}
			}
		},
		{name: 'leaveTime' , type: 'string', 
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

//理货员信息
Ext.define('Foss.unload.unloadtaskdetailquery.LoaderGrid',{
	extend: 'Ext.grid.Panel',
	title: unload.unloadtaskdetailquery.i18n('foss.unload.unloadtaskquery.loaderName'),
    stripeRows: true,
    frame: true,
	animCollapse: true,
	autoScroll: true,
	height:210,
	columns: [{
			header: unload.unloadtaskdetailquery.i18n('foss.unload.unloadtaskquery.loaderCode'), 
			dataIndex: 'loaderCode',
			flex : 1
		},{
			header: unload.unloadtaskdetailquery.i18n('foss.unload.unloadtaskquery.loaderName'),
			dataIndex: 'loaderName',
			flex : 1
		},{
			header: unload.unloadtaskdetailquery.i18n('foss.unload.unloadtaskquery.joinTime'),
			dataIndex: 'joinTime',
			flex : 1.8
		},{
			header: unload.unloadtaskdetailquery.i18n('foss.unload.unloadtaskquery.leaveTime'), 
			dataIndex: 'leaveTime',
			flex : 1.8
		}],
	bindData: function(record){
		var id = Ext.getCmp('Foss_Unload_UnloadTaskForm_Id').form.findField('id').getValue();
		var unloadWay = Ext.getCmp('Foss_Unload_UnloadTaskForm_Id').form.findField('unloadWay').getValue();
		var params = {
			'unloadTaskVo.unloadTaskId' : id,
			'unloadTaskVo.unloadWay' : unloadWay
		},
		me = this;
		Ext.Ajax.request({
			url : unload.realPath('queryLoaderByTaskId.action'),
			params : params,
			success : function(response) {
				var result = Ext.decode(response.responseText),
					store = me.store;
				if(result.unloadTaskVo == null) {
					return;
				}
				store.model = 'Foss.unload.unloadtaskdetailquery.LoaderModel';
				store.remoteSort = 'false';
				store.loadData(result.unloadTaskVo.loaderParticipationList);
			}
		});
	},
	constructor: function(config){
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

//表格panel
Ext.define('Foss.unload.unloadtaskdetailquery.UnloadTaskDetailResult', {
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
			header: unload.unloadtaskdetailquery.i18n('foss.unload.unloadtaskquery.billNo'), 
			dataIndex: 'billNo',
			flex : 1.8
		},{
			header: unload.unloadtaskdetailquery.i18n('foss.unload.unloadtaskquery.origOrgName'), 
			dataIndex: 'origOrgName',
			flex : 1.5
		},{
			header: unload.unloadtaskdetailquery.i18n('foss.unload.unloadtaskquery.destOrgName'), 
			dataIndex: 'destOrgName',
			flex : 1.5
		},{
			header: unload.unloadtaskdetailquery.i18n('foss.unload.unloadtaskquery.waybillNo'),
			dataIndex: 'waybillNo',
			renderer : function(value, cellmeta, record, rowIndex, columnIndex, store){
				if(value != null){
					var id = record.get('id');
					var expressOrLingdan=record.get('expressOrLingdan');
					var unloadTaskNo=record.get('unloadTaskNo');
					var cargoNo=record.get('cargoNo');
					var cargoType=record.get('cargoType');
					return '<a href="javascript:unload.unloadtaskdetailquery.showScanDetail('+"'" + id + "'"+",'"+expressOrLingdan+"',"+"'"+unloadTaskNo+"',"+"'"+cargoNo+"',"+"'"+cargoType+"'"+')">' + value + '</a>';
				}else{
					return null;
				}
			},
//			xtype: 'tipcolumn',
//			tipConfig: {
//		        //如果要设置宽度，一定要修改maxWidth值，因为tip的maxWidth最大只有300
//				maxWidth: 425,
//				width: 425,
//				height: 210,
//		        //Tip的Body是否随鼠标移动
//				trackMouse: true,
//				//Tip的隐藏延迟时间(单位:ms)
//				hideDelay: 2000
//			},
//			//配置tip内引用的自定义组件类名
//			tipBodyElement:'Foss.unload.unloadtaskdetailquery.ScanDetailGrid',
			flex : 2.0
		},{
			header: unload.unloadtaskdetailquery.i18n('foss.unload.unloadtaskquery.transportType'), 
			dataIndex: 'transportType',
		    flex: 1.5
		},{
			header: unload.unloadtaskdetailquery.i18n('foss.unload.unloadtaskquery.handoverTotQty'),
			dataIndex: 'handoverTotQty',
			renderer : function(value) {
				if(value==0) {
					value = "";
				}
				return value;
			},
			flex : 1.5
		},{
			header: unload.unloadtaskdetailquery.i18n('foss.unload.unloadtaskquery.operationGoodsQty'),
			dataIndex: 'operationGoodsQty',
			flex: 1.8
		},{
			header: unload.unloadtaskdetailquery.i18n('foss.unload.unloadtaskquery.scanGoodsQty'),
			dataIndex: 'scanGoodsQty',
			flex: 1.5
		},{
			header: unload.unloadtaskdetailquery.i18n('foss.unload.unloadtaskquery.scanGoodsQtyRate'),
			dataIndex: 'scanGoodsQtyRate',
			flex: 1.8,
			renderer : function(value) {
				if(value != null) {
					value = value * 100;
					value = value.toFixed(3) + '%'
				}
				return value;
			}
		},{
			header: unload.unloadtaskdetailquery.i18n('foss.unload.unloadtaskquery.unloadWeightTotal'),
			dataIndex: 'unloadWeightTotal',
			renderer : function(value) {
				if(!Ext.isEmpty(value)) {
					value = parseFloat(value);
					value = value.toFixed(3);
				}
				return value;
			},
			flex: 2.0
		},{
			header: unload.unloadtaskdetailquery.i18n('foss.unload.unloadtaskquery.unloadVolumeTotal'),
			dataIndex: 'unloadVolumeTotal',
			flex: 2.0,
			renderer : function(value) {
				if(!Ext.isEmpty(value)) {
					value = parseFloat(value);
					value = value.toFixed(3);
				}
				return value;
			}
		},{
			header: unload.unloadtaskdetailquery.i18n('foss.unload.unloadtaskquery.goodsName'),
			dataIndex: 'goodsName',
			flex: 1.8
		},{
			header: unload.unloadtaskdetailquery.i18n('foss.unload.unloadtaskquery.pack'),
			dataIndex: 'pack',
			flex: 1.8
		}
		],
		constructor: function(config){
			var me = this,
				cfg = Ext.apply({}, config);
			me.store = Ext.create('Foss.unload.unloadtaskdetailquery.UnloadTaskDetailStore');
			me.tbar = [{
				xtype : 'button',
				text : unload.unloadtaskdetailquery.i18n('foss.unload.unloadtaskquery.export'),
				disabled: !unload.unloadtaskdetailquery.isPermission('unload/exportUnloadWayBillByTaskNoButton'),
				hidden: !unload.unloadtaskdetailquery.isPermission('unload/exportUnloadWayBillByTaskNoButton'),
				name : 'export',
				handler : function(){
					var queryParams = unload.unloadtaskdetailquery.loadTaskForm.getForm().getValues();
					if(!Ext.fly('downloadAttachFileForm')){
						    var frm = document.createElement('form');
						    frm.id = 'downloadAttachFileForm';
						    frm.style.display = 'none';
						    document.body.appendChild(frm);
					}		
					Ext.Ajax.request({
						url:unload.realPath('exportUnloadWayBillByTaskNo.action'),
						form: Ext.fly('downloadAttachFileForm'),
						method : 'POST',
						params : {'unloadTaskVo.unloadTaskDto.unloadTaskNo': unload.unloadtaskdetailquery.unloadTaskNo},
		    			isUpload: true,
		    			exception : function(response) {
		    				var result = Ext.decode(response.responseText);
		    				top.Ext.MessageBox.alert(unload.unloadtaskdetailquery.i18n('foss.unload.unloadtaskquery.exportFail'),result.message);
		    			}
					});
				}
			}];
			me.callParent([cfg]);
		}
});

//扫描明细Store
Ext.define('Foss.unload.unloadtaskdetailquery.ScanDetailGrid.Store', {
	extend: 'Ext.data.Store',
	model: 'Foss.unload.unloadtaskdetailquery.ScanDetailModel',
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
Ext.define('Foss.unload.unloadtaskdetailquery.ScanDetailGrid',{
	extend: 'Ext.grid.Panel',
	title: unload.unloadtaskdetailquery.i18n('foss.unload.unloadtaskquery.scanDetail'),
    stripeRows: true,
    frame: true,
	animCollapse: true,
	autoScroll: true,
	height:300,
	columns: [{
			header: unload.unloadtaskdetailquery.i18n('foss.unload.unloadtaskquery.serialNo'),
			dataIndex: 'serialNo',
			flex : 1
		},{
			header: unload.unloadtaskdetailquery.i18n('foss.unload.unloadtaskquery.scanStatus'),
			dataIndex: 'scanStatus',
			flex : 1,
			renderer : function(value) {
				switch(value) {
					case 'SCANED':
						return unload.unloadtaskdetailquery.i18n('foss.unload.unloadtaskquery.SCANED');
					case 'UNSCANED':
						return unload.unloadtaskdetailquery.i18n('foss.unload.unloadtaskquery.UNSCANED');
					case 'BY_HAND':
						return unload.unloadtaskdetailquery.i18n('foss.unload.unloadtaskquery.BYHAND');
					case 'CANCELED':
						return unload.unloadtaskdetailquery.i18n('foss.unload.unloadtaskquery.CANCELED');
					case 'N/A':
						return '';
					default: return value;
				}
			}
		},{
			header: unload.unloadtaskdetailquery.i18n('foss.unload.unloadtaskquery.goodsStatus'), 
			dataIndex: 'goodsStatus',
			flex : 1,
			renderer : function(value) {
				switch(value) {
					case 'LACK':
						return unload.unloadtaskdetailquery.i18n('foss.unload.unloadtaskquery.LACK');
					case 'MORE':
						return unload.unloadtaskdetailquery.i18n('foss.unload.unloadtaskquery.MORE');
					case 'NORMAL':
						return unload.unloadtaskdetailquery.i18n('foss.unload.unloadtaskquery.NORMAL');
					case 'MORE_ENTRAINED':
						return unload.unloadtaskdetailquery.i18n('foss.unload.unloadtaskquery.MOREENTRAINED');
					case 'MORE_ALLOPATRY_ENTRAINED':
						return unload.unloadtaskdetailquery.i18n('foss.unload.unloadtaskquery.MOREALLOPATRYENTRAINED');
					case 'CANCELED':
						return unload.unloadtaskdetailquery.i18n('foss.unload.unloadtaskquery.CANCELED');
					default: return value;
				}
			}
		},{
			header: unload.unloadtaskdetailquery.i18n('foss.unload.unloadtaskquery.optTime'), 
			dataIndex: 'optTime',
			flex : 1.8
		}],
	constructor: function(config){
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.unload.unloadtaskdetailquery.ScanDetailGrid.Store');
		me.callParent([cfg]);
	}
});

//点击“运单号列”打开扫描明细窗口
javascript:unload.unloadtaskdetailquery.showScanDetail = function(id,expressOrLingdan,unloadTaskNo,cargoNo,cargoType){
	var grid = Ext.create('Foss.unload.unloadtaskdetailquery.ScanDetailGrid'),
	window = Ext.create('Ext.window.Window', {
	    title: unload.unloadtaskdetailquery.i18n('foss.unload.unloadtaskquery.scanDetail'),
	    height:400,
	    width:600,
	    closable:true,
		closeAction:'hide',
	    modal: true,
	    items: [
	            grid
        ]
	});
	var params = {
		'unloadTaskVo.unloadWaybillDetailId' : id,
		'unloadTaskVo.expressOrLingdan' :expressOrLingdan,
		'unloadTaskVo.unloadTaskNo' :unloadTaskNo,
		'unloadTaskVo.cargoNo' :cargoNo,
		'unloadTaskVo.cargoType' :cargoType,
	};

	Ext.Ajax.request({
		url : unload.realPath('queryUnloadSerialNoByUnloadWaybillDetailId.action'),
		params : params,
		success : function(response) {
			var result = Ext.decode(response.responseText),
				store = grid.store;
//			store.model = 'Foss.unload.unloadtaskdetailquery.ScanDetailModel';
//			store.remoteSort = 'false';
			store.loadData(result.unloadTaskVo.unloadSerialNoDetailList);
			window.show()
		}
	});
}

Ext.onReady(function() {
	//接受前一页面传入的交接单号
	var unloadTaskNo = unload.unloadtaskdetailquery.unloadTaskNo;
	var state = unload.unloadtaskdetailquery.stateValue;
	//根据交接单号，从后台获取交接单基本信息
	Ext.Ajax.request({
		url: unload.realPath('queryUnloadTaskDetail.action'),
		params:{'unloadTaskVo.unloadTaskDto.unloadTaskNo': unloadTaskNo,'unloadTaskVo.unloadTaskDto.state': state},
		success:function(response){
			Ext.QuickTips.init();
			var loadTaskForm = Ext.create('Foss.unload.unloadtaskdetailquery.UnloadTaskForm');
			unload.unloadtaskdetailquery.loadTaskForm = loadTaskForm;
			var loadWayBillResult = Ext.create('Foss.unload.unloadtaskdetailquery.UnloadTaskDetailResult');
			Ext.create('Ext.panel.Panel',{
				id: 'T_unload-unloadtaskdetailqueryIndex_content',
				cls: "panelContentNToolbar",
				bodyCls: 'panelContentNToolbar-body',
				layout: 'auto',
				items: [loadTaskForm, loadWayBillResult],
				renderTo: 'T_unload-unloadtaskdetailqueryIndex-body'
			});
			var result = Ext.decode(response.responseText),
				unloadTaskDto = result.unloadTaskVo.unloadTaskDto,
				unloadWaybillDetailDtoList = result.unloadTaskVo.unloadWaybillDetailDtoList;
			var loadTaskRecord =  Ext.ModelManager.create(unloadTaskDto, 'Foss.unload.unloadtaskdetailquery.UnloadTaskModel');
			loadTaskForm.loadRecord(loadTaskRecord);
			loadWayBillResult.store.loadData(unloadWaybillDetailDtoList);
		}
	});
});