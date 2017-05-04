Ext.define('Foss.platform.cargoArrived.queryForm', {
	id : 'Foss_platform_cargoArrived_queryForm_id',
	extend : 'Ext.form.Panel',
	title : '查询条件',
	frame : true,
	collapsible : false,
	animCollapse : true,
	defaults : {
		margin : '5 10 5 10',
		labelWidth : 85,
		columnWidth : .25,
		xtype : 'textfield'
	},
	layout : 'column',
	items : [{
		fieldLabel : '转运场',
		name : 'destDept',
		xtype : 'dynamicorgcombselector',
		types : 'ORG',
		transferCenter : 'Y',
		readOnly : !Ext.isEmpty(platform.cargoArrived.transferCenterCode),
		allowBlank: false
	}, {
		fieldLabel : '货物状态',
		name : 'status',
		xtype : 'combobox',
		queryMode: 'local',
		allowBlank : false,
		displayField: 'name',
		valueField: 'code',
		editable : false,
		allowBlank: false,
		value : 'TFR_CTR_ON_THE_WAY',
		store : Ext.create('Ext.data.Store', {
			fields: ['name', 'code'],
			data : [{'name' : '上一外场短途未到','code' : 'PRE_SHORT_DIS_NO_ARRIVED'},
			        {'name' : '上一外场库存', 'code':'TFR_CTR_STOCK'},
			        {'name' : '长途在途', 'code':'TFR_CTR_ON_THE_WAY'},
			        {'name' : '营业部未到', 'code':'SALES_DEPT_NO_ARRIVED'},
			        {'name' : '集中接货未到', 'code':'PICKUP_NO_ARRIVED'}]
		}),
		listeners : {
			'change' : function(field,newValue,oldValue,eOpts){
				var form = this.up('form').getForm(),
					beginTimeCmp = form.findField('beginTime'),
					endTimeCmp = form.findField('endTime');
				if(newValue !== 'TFR_CTR_ON_THE_WAY'){
					beginTimeCmp.setDisabled(true);
					endTimeCmp.setDisabled(true);
				}else{
					beginTimeCmp.setDisabled(false);
					endTimeCmp.setDisabled(false);
				}
			}
		}
	},{
		columnWidth : 1 / 2,
		fieldId : 'Foss_platform_cargoArrived_queryForm_eta_id',
		xtype : 'rangeDateField',
		dateType : 'datetimefield_date97',
		dateRange : 2,
		fieldLabel : '预计到达时间',
		allowBlank : false,
		fromName : 'beginTime',
		toName : 'endTime',
		fromValue: Ext.Date.format(new Date(new Date().getFullYear(), new Date().getMonth(), new Date().getDate()
				,'00','00','00'), 'Y-m-d H:i:s'),
				toValue: Ext.Date.format(new Date(new Date().getFullYear(), new Date().getMonth(), new Date().getDate()+1
						,'23','59','59'), 'Y-m-d H:i:s')
	}],
	buttons : [{
		xtype : 'button',
		columnWidth : .08,
		text : '重置',
		handler : function() {
			var form = this.up('form').getForm();
			form.reset();
			if (platform.cargoArrived.transferCenterCode) {
				form.findField('destDept').setCombValue(
						platform.cargoArrived.transferCenterName,
						platform.cargoArrived.transferCenterCode);
			}
			var now = new Date();
			form.findField('beginTime').setValue(Ext.Date.format(
					new Date(now.getFullYear(), now.getMonth(), now.getDate(),
							0, 0, 0), 'Y-m-d H:i:s'));
			form.findField('endTime').setValue(Ext.Date.format(new Date(
					now.getFullYear(), now.getMonth(), now.getDate()+1,
					23, 59, 59), 'Y-m-d H:i:s'));
		}
	}, '->', {
		columnWidth : .08,
		xtype : 'button',
		cls : 'yellow_button',
		text : '查询',
		handler : function() {
			var form = this.up('form').getForm();
			if (form.isValid()) {
				platform.cargoArrived.resultGrid.store.load();
			}
		}
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

Ext.define('Foss.platform.cargoArrived.detailModel', {
	extend : 'Ext.data.Model',
	fields : [{name : 'vehicleNo',type : 'string'},
	          {name : 'eta',type : 'date',convert : dateConvert},
	          {name : 'vote',type : 'string'},
	          {name : 'weight',type : 'string'},
	          {name : 'volume',type : 'string'}
	          ]
});

Ext.define('Foss.platform.cargoArrived.detailStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.platform.cargoArrived.detailModel',
	proxy : {
		type : 'ajax',
		actionMethods : 'POST',
		url : platform.realPath('findDetails.action'),
		timeout : 300000,
		reader : {
			type : 'json',
			root : 'cargoArrivedVo.cargoArrivedDetailDtos',
			successProperty : 'success'
		}
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

Ext.define('Foss.platform.cargoArrived.detailGrid', {
	extend : 'Ext.grid.Panel',
	columnLines : true,
	frame : true,
	autoScroll : true,
	sortableColumns : false,
	enableColumnHide : false,
	width : 540,
	store : Ext.create('Foss.platform.cargoArrived.detailStore'),
	columns : [{
		dataIndex : 'vehicleNo',
		align : 'center',
		width : 90,
		text : '车牌号'
	},{
		dataIndex : 'eta',
		align : 'center',
		width : 140,
		xtype : 'datecolumn',
		format : 'Y-m-d H:i:s',
		text : '预计到达时间'
	},{
		dataIndex : 'vote',
		align : 'center',
		width : 90,
		text : '票数'
	},{
		dataIndex : 'weight',
		align : 'center',
		width : 90,
		text : '重量'
	},{
		dataIndex : 'volume',
		align : 'center',
		width : 90,
		text : '体积'
	}],
	bindData : function(record,grid,rowBodyElement){
		var me = this,
		origDeptCode = record.get('origDeptCode'),
		destDeptCode = record.get('destDeptCode'),
		status = record.get('status'),
		beginTimeLong = record.get('beginTime'),
		endTimeLong = record.get('endTime');
		if(status !== 'TFR_CTR_ON_THE_WAY'){
			Ext.ux.Toast.msg('提示', '只有长途在途货量有明细！', 'error', 2000);
			me.store.loadData([]);
			return;
		}
		var beginTime = new Date(),
		endTime = new Date();
		beginTime.setTime(beginTimeLong);
		endTime.setTime(endTimeLong);
		
		me.store.load({
			params : {
				'cargoArrivedVo.cargoArrivedQcDto.origDeptCode' : origDeptCode,
				'cargoArrivedVo.cargoArrivedQcDto.destDeptCode' : destDeptCode,
				'cargoArrivedVo.cargoArrivedQcDto.status' : status,
				'cargoArrivedVo.cargoArrivedQcDto.beginTime' : beginTime,
				'cargoArrivedVo.cargoArrivedQcDto.endTime' : endTime
			}
		});
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

Ext.define('Foss.platform.cargoArrived.gridModel', {
	extend : 'Ext.data.Model',
	fields : [{
		name : 'destDeptName',
		type : 'string'
	},{
		name : 'origDeptName',
		type : 'string'
	}, {
		name : 'weightTotal',
		type : 'String'
	}, {
		name : 'volumeTotal',
		type : 'string'
	}, {
		name : 'voteTotal',
		type : 'string'
	}, {
		name : 'weightFlf',
		type : 'string'
	}, {
		name : 'volumeFlf',
		type : 'string'
	}, {
		name : 'voteFlf',
		type : 'string'
	}, {
		name : 'weightFsf',
		type : 'string'
	}, {
		name : 'volumeFsf',
		type : 'string'
	}, {
		name : 'voteFsf',
		type : 'string'
	}, {
		name : 'weightExp',
		type : 'string'
	}, {
		name : 'volumeExp',
		type : 'string'
	}, {
		name : 'voteExp',
		type : 'string'
	}, {
		name : 'status',
		type : 'string'
	}, {
		name : 'origDeptCode',
		type : 'string'
	}, {
		name : 'destDeptCode',
		type : 'string'
	}, {
		name : 'beginTime',
		type : 'long'
	}, {
		name : 'endTime',
		type : 'long'
	}]
});

Ext.define('Foss.platform.cargoArrived.gridStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.platform.cargoArrived.gridModel',
	proxy : {
		type : 'ajax',
		actionMethods : 'POST',
		url : platform.realPath('findCargoArrived.action'),
		timeout : 300000,
		reader : {
			type : 'json',
			root : 'cargoArrivedVo.cargoArrivedDtos',
			successProperty : 'success'
		}
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	},
	listeners : {
		beforeload : function(store, operation, eOpts) {
			var queryForm = Ext.getCmp('Foss_platform_cargoArrived_queryForm_id').getForm();
			if (!Ext.isEmpty(queryForm)) {
				var queryParams = queryForm.getValues(),
				destDeptField = queryForm.findField('destDept'),
				destDeptCode = queryParams.destDept
				destDeptName = destDeptField.store.findRecord('code', destDeptCode, false, true, true).get('name');
				Ext.apply(operation, {
					params : {
						'cargoArrivedVo.cargoArrivedQcDto.status' : queryParams.status,
						'cargoArrivedVo.cargoArrivedQcDto.destDeptCode' : destDeptCode,
						'cargoArrivedVo.cargoArrivedQcDto.destDeptName' : destDeptName,
						'cargoArrivedVo.cargoArrivedQcDto.beginTime' : queryParams.beginTime,
						'cargoArrivedVo.cargoArrivedQcDto.endTime' : queryParams.endTime
					}
				});
			}
		}
	}
});

platform.cargoArrived.sum = function(records, field) {
	var i = 0, length = records.length, total, record, sum = 0;
	for (; i < length; ++i) {
		record = records[i];
		if(Ext.isEmpty(record.get(field))){
			sum += 0;
		}else{
			sum += parseFloat(record.get(field));
		}
	}
	total = sum.toFixed(3);
	return parseFloat(total).toString();
};

Ext.define('Foss.platform.cargoArrived.resultGrid', {
	extend : 'Ext.grid.Panel',
	frame : true,
	columnLines : false,
	title : '查询结果',
	height : 600,
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	emptyText : '查询结果为空',
	sortableColumns : true,
	enableColumnHide : false,
	autoScroll : false,
	collapsible : false,
	animCollapse : false,
	features : [{
		ftype : 'groupingsummary',
		hideGroupedHeader : true
	}],
	plugins : [ {
		header : true,
		ptype : 'rowexpander',
		rowsExpander : false,
		expandOnDblClick : false,
		rowBodyElement : 'Foss.platform.cargoArrived.detailGrid'
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.platform.cargoArrived.gridStore', {
			groupField : 'destDeptName'
		});
		me.tbar = [{
			xtype : 'button',
			text : '导出',
			handler : function() {
				var queryForm = Ext.getCmp('Foss_platform_cargoArrived_queryForm_id').getForm(), 
				queryParams = queryForm.getValues();

				if (!queryForm.isValid()) {
					Ext.ux.Toast.msg('提示', '请输入合法的导出条件！', 'error', 2000);
					return;
				}

				var params = {
						'cargoArrivedVo.cargoArrivedQcDto.status' : queryParams.status,
						'cargoArrivedVo.cargoArrivedQcDto.destDeptCode' : queryParams.destDept,
						'cargoArrivedVo.cargoArrivedQcDto.beginTime' : queryParams.beginTime,
						'cargoArrivedVo.cargoArrivedQcDto.endTime' : queryParams.endTime
				};

				if (!Ext.fly('downloadAttachFileForm')) {
					var frm = document.createElement('form');
					frm.id = 'downloadAttachFileForm';
					frm.style.display = 'none';
					document.body.appendChild(frm);
				}

				Ext.Ajax.request({
					url : platform.realPath('exportCargoArrived.action'),
					form : Ext.fly('downloadAttachFileForm'),
					method : 'POST',
					params : params,
					isUpload : true,
					exception : function(response) {
						var result = Ext.decode(response.responseText);
						Ext.ux.Toast.msg('提示', result.message, 'error', 2000);
					}
				});
			}
		}];
		me.columns = [{
			header: '转运场', 
			dataIndex: 'destDeptName',
			width:60
		},{
			dataIndex : 'origDeptName',
			align : 'center',
			width : 120,
			sortable : false,
			text : '出发部门',
			summaryRenderer : function() {
				return '&nbsp&nbsp&nbsp&nbsp&nbsp小计';
			}
		},{
			dataIndex : 'weightTotal',
			align : 'center',
			width : 75,
			sortable : false,
			text : '总重量',
			summaryType : function(records){
				return platform.cargoArrived.sum(records,'weightTotal');
			}
		},{
			dataIndex : 'volumeTotal',
			align : 'center',
			width : 75,
			sortable : false,
			text : '总体积',
			summaryType : function(records){
				return platform.cargoArrived.sum(records,'volumeTotal');
			}
		},{
			dataIndex : 'voteTotal',
			align : 'center',
			width : 60,
			sortable : false,
			text : '总票数',
			summaryType : function(records){
				return platform.cargoArrived.sum(records,'voteTotal');
			}
		},{
			text : '卡航',
			columns : [{
				dataIndex : 'weightFlf',
				align : 'center',
				width : 75,
				sortable : false,
				text : '重量'
			},{
				dataIndex : 'volumeFlf',
				align : 'center',
				width : 75,
				sortable : false,
				text : '体积'
			},{
				dataIndex : 'voteFlf',
				align : 'center',
				width : 60,
				sortable : false,
				text : '票数'
			}]
		},{
			text : '城运',
			columns : [{
				dataIndex : 'weightFsf',
				align : 'center',
				width : 75,
				text : '重量'
			},{
				dataIndex : 'volumeFsf',
				align : 'center',
				width : 75,
				text : '体积'
			},{
				dataIndex : 'voteFsf',
				align : 'center',
				width : 60,
				text : '票数'
			}]
		},{
			text : '快递',
			columns : [{
				dataIndex : 'weightExp',
				align : 'center',
				width : 75,
				text : '重量'
			},{
				dataIndex : 'volumeExp',
				align : 'center',
				width : 75,
				text : '体积'
			},{
				dataIndex : 'voteExp',
				align : 'center',
				width : 60,
				text : '票数'
			}]
		}];
		platform.cargoArrived.resultGrid = me;
		me.callParent([cfg]);
	}
});

Ext.onReady(function() {
	var queryForm = Ext.create('Foss.platform.cargoArrived.queryForm');
	var resultGrid = Ext.create('Foss.platform.cargoArrived.resultGrid');

	var destDeptField = queryForm.getForm().findField('destDept');
	destDeptField.setCombValue(platform.cargoArrived.transferCenterName,
			platform.cargoArrived.transferCenterCode);
	Ext.create('Ext.panel.Panel', {
		id : 'T_platform-cargoArrivedIndex_content',
		cls : "panelContentNToolbar",
		bodyCls : 'panelContent-body',
		layout : 'auto',
		items : [queryForm, resultGrid],
		renderTo : 'T_platform-cargoArrivedIndex-body'
	});
});