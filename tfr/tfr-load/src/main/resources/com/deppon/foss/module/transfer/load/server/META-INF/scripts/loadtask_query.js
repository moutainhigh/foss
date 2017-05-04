function getValue(id) {
	return Ext.getCmp(id).getValue();
};

//装车类型
Ext.define('Foss.Queryloadtask.QueryForm.taskType.Store', {
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
Ext.define('Foss.Queryloadtask.QueryForm.State.Store', {
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

Ext.define('Foss.queryloadtask.LoadTaskModel',{
	extend:'Ext.data.Model',
	fields: [
		{name: 'id', type: 'string'},
		{name: 'taskNo' , type: 'string'},
		{name: 'gaprepNo' , type: 'string'},
		{name: 'vehicleNo' , type: 'string'},
		{name: 'line' , type: 'string'},
		{name: 'loaderName' , type: 'string'},
		{name: 'platformNo' , type: 'string'},
		{name: 'taskType' , type: 'string'},
		{name: 'state' , type: 'string'},
		{name: 'planDepartTime' , type: 'string', 
			convert: function(value) {
				if (!Ext.isEmpty(value)) {
					var date = new Date(value);						
					return Ext.Date.format(date,'Y-m-d H:i:s');
				} else {
					return null;
				}
			}
		},
		{name: 'loadStartTime' , type: 'string'},
		{name: 'loadEndTime' , type: 'string'},
		{name: 'source', type: 'string'},
		{name: 'loadSource', type: 'string'}
	]
});

//装卸车人员Mode
Ext.define('Foss.queryloadtask.LoaderModel',{
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

//LoadTaskStore
Ext.define('Foss.queryloadtask.LoadTaskStore',{
	extend: 'Ext.data.Store',
	model: 'Foss.queryloadtask.LoadTaskModel',
	pageSize: 20,
	autoLoad: false,
	proxy: {
        type : 'ajax',
        actionMethods:'POST',
        url: load.realPath('queryLoadTask.action'),
		reader : {
			type : 'json',
			root : 'loadTaskVo.loadTaskList',
			totalProperty : 'totalCount',
			successProperty: 'success'
		}
    },
    listeners: {
		beforeload : function(store, operation, eOpts) {
			var queryParams = load.queryloadtask.queryform.getValues();
			Ext.apply(operation, {
				params : {
					'loadTaskVo.loadTaskDto.taskNo' : queryParams.taskNo,
					'loadTaskVo.loadTaskDto.vehicleNo' : queryParams.vehicleNo,
					'loadTaskVo.loadTaskDto.loaderName' :queryParams.loaderName ,
					'loadTaskVo.loadTaskDto.taskType' : queryParams.taskType,
					'loadTaskVo.loadTaskDto.state' : queryParams.state,
					'loadTaskVo.loadTaskDto.beginDate' :queryParams.beginDate ,
					'loadTaskVo.loadTaskDto.endDate' : queryParams.endDate,
					'loadTaskVo.loadTaskDto.handoverNo' : queryParams.handoverNo,
					'loadTaskVo.loadTaskDto.platformNo' : queryParams.platformNo
				}
			});
		}
	}
});

//查询条件
Ext.define('Foss.queryloadtask.QueryForm', {
	extend:'Ext.form.Panel',
	title: load.queryloadtask.i18n('foss.load.queryloadtask.queryParam'),
	frame: true,
	animCollapse: true,
	defaultType: 'textfield',
	layout:'column',
	id: 'Foss_Queryloadtask_QueryForm_Id', 
	defaults: {
		margin: '5 5 5 5',
		labelWidth: 60
	},
	items:[{
		name: 'taskNo',
		fieldLabel: load.queryloadtask.i18n('foss.load.queryloadtask.taskNo'),
		columnWidth: .25
	},{
		name: 'vehicleNo',
		fieldLabel: load.queryloadtask.i18n('foss.load.queryloadtask.vehicleNo'),
		xtype : 'commontruckselector',
		columnWidth: .25
	},{
		name: 'loaderName',
		xtype: 'commonemployeeselector',
		fieldLabel: load.queryloadtask.i18n('foss.load.queryloadtask.loaderName'),
		columnWidth: .25
	},{
		xtype: 'combobox',
		name:'taskType',
		fieldLabel: load.queryloadtask.i18n('foss.load.queryloadtask.taskType'),
		columnWidth:.25,
		displayField: 'name',
		valueField:'code', 
		queryMode:'local',
		triggerAction:'all',
		value:'ALL',
		autoSelect: true,
		editable:false,
		store: Ext.create('Foss.Queryloadtask.QueryForm.taskType.Store',{
			data: {
				'items':[
					{'code':'ALL','name': load.queryloadtask.i18n('foss.load.queryloadtask.ALL')},
					{'code':'DELIVER_LOAD','name': load.queryloadtask.i18n('foss.load.queryloadtask.DELIVERLOAD')},
					{'code':'PARTIALLINE_LOAD','name': load.queryloadtask.i18n('foss.load.queryloadtask.PARTIALLINELOAD')},
					{'code':'LONG_DISTANCE_LOAD','name': load.queryloadtask.i18n('foss.load.queryloadtask.LONGDISTANCELOAD')},
					{'code':'SHORT_DISTANCE_LOAD','name': load.queryloadtask.i18n('foss.load.queryloadtask.SHORTDISTANCELOAD')},
					{'code':'LDP_LOAD','name': load.queryloadtask.i18n('foss.load.queryloadtask.LDPLOAD')},
					{'code':'DIVISION_LOAD','name': load.queryloadtask.i18n('foss.load.queryloadtask.DIVISIONLOAD')},
					{'code':'EXPRESS_CONNECTION_LOAD','name': load.queryloadtask.i18n('foss.load.queryloadtask.CONNECTIONLOAD')},
					{'code':'EXPRESS_PICK_LOAD','name': load.queryloadtask.i18n('foss.load.queryloadtask.EXPRESSPICKLOAD')},
					{'code':'EXPRESS_SENDPIECE_LOAD','name': load.queryloadtask.i18n('foss.load.queryloadtask.EXPRESSSENDPIECELOAD')}
				]
			}
		})
	},{
		xtype: 'combobox',
		name:'state',
		fieldLabel: load.queryloadtask.i18n('foss.load.queryloadtask.state'),
		columnWidth:.25,
		displayField: 'name',
		valueField:'code', 
		queryMode:'local',
		triggerAction:'all',
		value:'ALL',
		editable:false,
		store: Ext.create('Foss.Queryloadtask.QueryForm.State.Store',{
			data: {
				'items':[
					{'code':'ALL','name': load.queryloadtask.i18n('foss.load.queryloadtask.ALL')},
					{'code':'LOADING','name': load.queryloadtask.i18n('foss.load.queryloadtask.LOADING')},
					{'code':'FINISHED','name': load.queryloadtask.i18n('foss.load.queryloadtask.FINISHED')},
					{'code':'SUBMITED','name': load.queryloadtask.i18n('foss.load.queryloadtask.SUBMITED')},
					{'code':'CANCELED','name': load.queryloadtask.i18n('foss.load.queryloadtask.CANCELED')}
				]
			}
		})
	},{
		xtype: 'rangeDateField',
		fieldLabel: load.queryloadtask.i18n('foss.load.queryloadtask.loadStartTime'),
		//类型为datetimefield_date97需要配置fieldId,可以赋予此属性任何唯一标	    //识的String值
		fieldId: 'Foss_Queryloadtask_LoadStartTime_Id',
		dateType: 'datetimefield_date97',
		//dateType: 'datefield',
		//dateType: 'timefield',
		fromName: 'beginDate',
		dateRange : 30,
		labelWidth: 85,
		fromValue: Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()
				,'00','00','00'), 'Y-m-d H:i:s'),
		toValue: Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()
				,'23','59','59'), 'Y-m-d H:i:s'),
		toName: 'endDate',
		allowBlank: false,
		columnWidth: .50
	},{
		xtype: 'textfield',
		name:'handoverNo',
		fieldLabel: load.queryloadtask.i18n('foss.load.queryloadtask.handoverNo'),
		columnWidth:.25,
		html: '&nbsp;'
	},{
		xtype: 'commonplatformselector',
		name:'platformNo',
		allowBlank: true,
		orgCode : load.queryloadtask.outfieldCode,
		fieldLabel:load.queryloadtask.i18n('foss.load.queryloadtask.platformNo'),//月台号
		columnWidth:.25
	},{
		border : false,
		xtype : 'container',
		columnWidth : 1,
		layout : 'column',
		defaults : {
			margin : '5 0 5 0'
		}
	},{
		text: load.queryloadtask.i18n('foss.load.queryloadtask.reset'),
		xtype:"button",
		columnWidth:.10,
		height:30,
		handler:function(){
			this.up('form').getForm().reset();
			//重新初始化交接时间
			this.up('form').getForm().findField('beginDate')
				.setValue(Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate(),'00','00','00'), 'Y-m-d H:i:s'));
			this.up('form').getForm().findField('endDate')
				.setValue(Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate(),'23','59','59'), 'Y-m-d H:i:s'));
		}
	},{
		xtype: 'container',
		columnWidth:.80,
		html: '&nbsp;'
	},{
		text: load.queryloadtask.i18n('foss.load.queryloadtask.query'),
		disabled: !load.queryloadtask.isPermission('load/queryLoadTaskButton'),
		hidden: !load.queryloadtask.isPermission('load/queryLoadTaskButton'),
		xtype:"button",
		cls:'yellow_button',
		columnWidth:.10,
		height:30,
		handler:function(){
			var form = this.up('form').getForm();
			if(!form.isValid()){
				return;
			}
			load.queryloadtask.pagingBar.moveFirst();
		}
	}]
});

//表格panel
Ext.define('Foss.queryloadtask.QueryResult', {
	extend:'Ext.grid.Panel',
    bodyCls: 'autoHeight',
	cls: 'autoHeight',
	id: 'Foss_Queryloadtask_QueryResult_id',
    stripeRows: true,
    frame: true,
	animCollapse: true,
	autoScroll: true,
	height:500,
	store: null,
	emptyText: load.queryloadtask.i18n('foss.load.queryloadtask.emptyResult'),
	//定义表格列信息
	columns: [{
			header: load.queryloadtask.i18n('foss.load.queryloadtask.taskNo'), 
			dataIndex: 'taskNo',
			flex : 1,
			renderer : function(value, cellmeta, record, rowIndex, columnIndex, store){
				if(value != null){
//					var source1 = record.data["id"]; 
					var source = record.get('source');
					return '<a href="javascript:load.queryloadtask.showLoadWayBillDetail('+"'" + value + "','" +source+ "'"+')">' + value + '</a>';
				}else{
					return null;
				}
			}
		},{
			header: load.queryloadtask.i18n('foss.load.queryloadtask.gaprepNo'), 
			dataIndex: 'gaprepNo',
			flex : 1,
			renderer : function(value){
				if(value != null){
					return '<a href="javascript:load.queryloadtask.showGapRepWindow('+"'" + value + "'"+')">' + value + '</a>';
				}else{
					return null;
				}
			}
		},{
			header: load.queryloadtask.i18n('foss.load.queryloadtask.vehicleNo'), 
			dataIndex: 'vehicleNo',
			flex : 1
		},{
			header: load.queryloadtask.i18n('foss.load.queryloadtask.line'), 
			dataIndex: 'line',
			flex : 1
		},{
			header: load.queryloadtask.i18n('foss.load.queryloadtask.loaderName'), 
			dataIndex: 'loaderName',
			renderer : function(value, cellmeta, record, rowIndex, columnIndex, store){
				if(value != null){
					var id = record.get('id');
					return '<a href="javascript:load.queryloadtask.showLoader('+"'" + id + "'"+')">' + value + '</a>';
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
//			配置tip内引用的自定义组件类名
//			tipBodyElement:'Foss.queryloadtask.LoaderGrid',
//			自动填列
		    flex: 1
		},{
			header: load.queryloadtask.i18n('foss.load.queryloadtask.platformNo'), 
			dataIndex: 'platformNo',
			flex : 1
		},{
			header: load.queryloadtask.i18n('foss.load.queryloadtask.taskType'), 
			dataIndex: 'taskType',
			flex : 1,
			renderer : function(value) {
				switch(value) {
					case 'DELIVER_LOAD':
						return load.queryloadtask.i18n('foss.load.queryloadtask.DELIVERLOAD');
					case 'PARTIALLINE_LOAD':
						return load.queryloadtask.i18n('foss.load.queryloadtask.PARTIALLINELOAD');
					case 'LONG_DISTANCE_LOAD':
						return load.queryloadtask.i18n('foss.load.queryloadtask.LONGDISTANCELOAD');
					case 'SHORT_DISTANCE_LOAD':
						return load.queryloadtask.i18n('foss.load.queryloadtask.SHORTDISTANCELOAD');
					case 'LDP_LOAD':
						return load.queryloadtask.i18n('foss.load.queryloadtask.LDPLOAD');
					case 'DIVISION_LOAD':
						return load.queryloadtask.i18n('foss.load.queryloadtask.DIVISIONLOAD');
					case 'EXPRESS_PICK_LOAD':
						return load.queryloadtask.i18n('foss.load.queryloadtask.EXPRESSPICKLOAD');
					case 'EXPRESS_CONNECTION_LOAD':
						return load.queryloadtask.i18n('foss.load.queryloadtask.CONNECTIONLOAD');	
					case 'EXPRESS_SENDPIECE_LOAD':
						return load.queryloadtask.i18n('foss.load.queryloadtask.EXPRESSSENDPIECELOAD');		
					default: return value;
				}
			}
		},{
			header: load.queryloadtask.i18n('foss.load.queryloadtask.state'), 
			dataIndex: 'state',
			flex : 1,
			renderer : function(value) {
				switch(value) {
					case 'LOADING':
						return load.queryloadtask.i18n('foss.load.queryloadtask.LOADING');
					case 'FINISHED':
						return load.queryloadtask.i18n('foss.load.queryloadtask.FINISHED');
					case 'SUBMITED':
						return load.queryloadtask.i18n('foss.load.queryloadtask.SUBMITED');
					case 'CANCELED':
						return load.queryloadtask.i18n('foss.load.queryloadtask.CANCELED');
					default: return value;
				}
			}
		},{
			header: load.queryloadtask.i18n('foss.load.queryloadtask.planDepartTime'), 
			dataIndex: 'planDepartTime',
			flex: 1.8
		},{
			header: load.queryloadtask.i18n('foss.load.queryloadtask.loadStartTime'), 
			dataIndex: 'loadStartTime',
			flex: 1.8
		},{
			header: load.queryloadtask.i18n('foss.load.queryloadtask.loadEndTime'), 
			dataIndex: 'loadEndTime',
			flex: 1.8
		}],
		constructor: function(config){
			var me = this,
			cfg = Ext.apply({}, config);
			me.store = Ext.create('Foss.queryloadtask.LoadTaskStore');
			me.selModel = Ext.create('Ext.selection.CheckboxModel');
			me.tbar = [{
				xtype : 'button',
				text : load.queryloadtask.i18n('foss.load.queryloadtask.export'),
				name : 'export',
				handler : function(){
					var queryParams = load.queryloadtask.queryform.getValues();
					if(!Ext.fly('downloadAttachFileForm')){
						    var frm = document.createElement('form');
						    frm.id = 'downloadAttachFileForm';
						    frm.style.display = 'none';
						    document.body.appendChild(frm);
					}		
					Ext.Ajax.request({
						url:load.realPath('exportLoadTask.action'),
						form: Ext.fly('downloadAttachFileForm'),
						method : 'POST',
						params : {
							'loadTaskVo.loadTaskDto.taskNo' : queryParams.taskNo,
							'loadTaskVo.loadTaskDto.vehicleNo' : queryParams.vehicleNo,
							'loadTaskVo.loadTaskDto.loaderName' :queryParams.loaderName ,
							'loadTaskVo.loadTaskDto.taskType' : queryParams.taskType,
							'loadTaskVo.loadTaskDto.state' : queryParams.state,
							'loadTaskVo.loadTaskDto.beginDate' :queryParams.beginDate ,
							'loadTaskVo.loadTaskDto.endDate' : queryParams.endDate,
							'loadTaskVo.loadTaskDto.handoverNo' : queryParams.handoverNo,
							'loadTaskVo.loadTaskDto.source' : queryParams.source
						},
		    			isUpload: true,
		    			exception : function(response) {
		    				var result = Ext.decode(response.responseText);
		    				top.Ext.MessageBox.alert(load.queryloadtask.i18n('foss.load.queryloadtask.exportFail'), result.message);
		    			}
					});
				}
			}];
			me.bbar = Ext.create('Deppon.StandardPaging',{
				store:me.store,
				plugins: 'pagesizeplugin'
			});
			load.queryloadtask.pagingBar = me.bbar;
			me.callParent([cfg]);
		}
});

//理货员信息
Ext.define('Foss.queryloadtask.LoaderGrid',{
	extend: 'Ext.grid.Panel',
	title: load.queryloadtask.i18n('foss.load.queryloadtask.loaderName'),
    stripeRows: true,
    frame: true,
	animCollapse: true,
	autoScroll: true,
	height:300,
	columns: [{
			header: load.queryloadtask.i18n('foss.load.queryloadtask.loaderCode'), 
			dataIndex: 'loaderCode',
			flex : 1
		},{
			header: load.queryloadtask.i18n('foss.load.queryloadtask.loaderName'), 
			dataIndex: 'loaderName',
			flex : 1
		},{
			header: load.queryloadtask.i18n('foss.load.queryloadtask.joinTime'), 
			dataIndex: 'joinTime',
			flex : 1.8
		},{
			header: load.queryloadtask.i18n('foss.load.queryloadtask.leaveTime'), 
			dataIndex: 'leaveTime',
			flex : 1.8
		}],
//	bindData: function(record){
//		var params = {
//			'loadTaskVo.loaderParticipation.taskId' : record.get('id')
//		},
//		me = this;
//		Ext.Ajax.request({
//			url : load.realPath('queryLoaderByTaskId.action'),
//			params : params,
//			success : function(response) {
//				var result = Ext.decode(response.responseText),
//					store = me.store;
//				store.model = 'Foss.queryloadtask.LoaderModel';
//				store.remoteSort = 'false';
//				store.loadData(result.loadTaskVo.loaderParticipationList);
//			}
//		});
//	},
	constructor: function(config){
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

//点击“任务编号”打开详情界面方法
load.queryloadtask.showLoadWayBillDetail = function(taskNo,source){
	addTab('T_load-loadtaskwaybilldetailIndex',//对应打开的目标页面js的onReady里定义的renderTo
			load.queryloadtask.i18n('foss.load.queryloadtask.loadDetail'),//打开的Tab页的标题
			load.realPath('loadtaskwaybilldetailIndex.action?taskNo='+ taskNo + '&source=' + source));//对应的页面URL，可以在url后使用?x=123这种形式传参
			
}

Ext.define('Foss.Load.QueryLoadTask.GaprepModel',{
	extend:'Ext.data.Model',
	fields:[{name:'id',type:'string'},
	        {name:'taskNo',type:'string'},
	        {name:'reportNo',type:'string'},
	        {name:'deliverBillNo',type:'string'},
	        {name:'createTime',type:'string'},
	        {name:'state',type:'string',
	        	convert: function(value) {
					if (value == 'GENERATED') {					
						return load.queryloadtask.i18n('foss.load.queryloadtask.GENERATED');
					} else if (value == 'BACK') {					
						return load.queryloadtask.i18n('foss.load.queryloadtask.BACK');
					}else if (value == 'AFFIRM') {					
						return load.queryloadtask.i18n('foss.load.queryloadtask.AFFIRM');
					}
				}
	        },
	        {name:'planLoadGoodsQty',type:'int'},
	        {name:'actualLoadGoodsQty',type:'int'},
	        {name:'lackGoodsQty',type:'int'},
	        {name:'vehicleNo',type:'string'},
	        {name:'beValid',type:'string'}
	       ]
});

//点击“差异报告编号”打开详情界面方法
load.queryloadtask.showGapRepWindow = function(gaprepNo){
	Ext.getCmp('Foss_Load_QueryLoadtask_GaprepWayBillGrid_Id').store.removeAll();
	load.queryloadtask.gapRepWindow.restore();
	Ext.Ajax.request({
		url : load.realPath('queryLoadGaprepIdByGaprepNo.action'),
		params : {
			'loadTaskVo.gaprepNo':gaprepNo
		},
		success : function(response) {
			var result = Ext.decode(response.responseText),
				id = result.loadTaskVo.loadGaprepId;
			Ext.Ajax.request({
				url : load.realPath('queryLoadGaprepReport.action'),
				params : {
					'loadTaskVo.loadGaprepId':id
				},
				success : function(response) {
					result = Ext.decode(response.responseText),
						vo = result.loadTaskVo;
					var record =  Ext.ModelManager.create(vo.loadGapReport, 'Foss.Load.QueryLoadTask.GaprepModel');
					Ext.getCmp('Foss_Load_QueryLoadTask_DeliverLoadGapRep_WindowBottomForm_Id').getForm().loadRecord(record);
					Ext.getCmp('Foss_Load_QueryLoadTask_DeliverLoadGapRep_WindowForm_Id').getForm().loadRecord(record);
					Ext.getCmp('Foss_Load_QueryLoadtask_GaprepWayBillGrid_Id').store.loadData(vo.reportWayBills);
					load.queryloadtask.gapRepWindow.show();
				}
			});
		}
	});
}

//点击“理货员列”打开理货员窗口
javascript:load.queryloadtask.showLoader = function(id){
	var grid = Ext.create('Foss.queryloadtask.LoaderGrid'),
		window = Ext.create('Ext.window.Window', {
		    title: load.queryloadtask.i18n('foss.load.queryloadtask.loaderName'),
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
			'loadTaskVo.loaderParticipation.taskId' : id
	};

	Ext.Ajax.request({
		url : load.realPath('queryLoaderByTaskId.action'),
		params : params,
		success : function(response) {
			var result = Ext.decode(response.responseText),
			store = grid.store;
			store.model = 'Foss.queryloadtask.LoaderModel';
			store.remoteSort = 'false';
			store.loadData(result.loadTaskVo.loaderParticipationList);
			window.show()
		}
	});
}

Ext.onReady(function() {
	Ext.QuickTips.init();
	var queryform = Ext.create('Foss.queryloadtask.QueryForm');
	load.queryloadtask.queryform = queryform;
	var queryResult = Ext.create('Foss.queryloadtask.QueryResult');
	Ext.create('Ext.panel.Panel',{
		id: 'T_load-queryloadtaskindex_content',
		cls: "panelContentNToolbar",
		bodyCls: 'panelContentNToolbar-body',
		layout: 'auto',
		items: [queryform, queryResult],
		renderTo: 'T_load-queryloadtaskindex-body'
	});
});