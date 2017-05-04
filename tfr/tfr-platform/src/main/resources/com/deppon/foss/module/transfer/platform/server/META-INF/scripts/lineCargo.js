Ext.define('Foss.platform.lineCargo.productStore',{
	extend :'Ext.data.Store',
	autoLoad : true,
	fields:[{name: 'valueName',  type: 'string'},
	        {name: 'valueCode',  type: 'string'}
	],
	proxy: {
		type : 'ajax',
		actionMethods : 'post',
		url : platform.realPath('queryProducts.action'),
		reader : {
			type : 'json',
			root : 'lineCargoVo.products'
		}
	}
});

Ext.define('Foss.platform.lineCargo.queryForm', {
	id : 'Foss_platform_lineCargo_queryForm_id',
	extend : 'Ext.form.Panel',
	title : '查询条件',
	frame : true,
	collapsible : false,
	animCollapse : true,
	defaults : {
		margin : '5 10 5 10',
		labelWidth : 85,
		columnWidth : 1 / 4,
		xtype : 'textfield'
	},
	layout : 'column',
	items : [{
		fieldLabel : '当前部门',
		name : 'origDept',
		xtype : 'dynamicorgcombselector',
		types : 'ORG',
		transferCenter : 'Y',
		readOnly : !Ext.isEmpty(platform.lineCargo.transferCenterCode),
		allowBlank: false
	}, {
		fieldLabel : '下一线路',
		name : 'destDept',
		xtype : 'commonlineselector',
		fieldLabel : '线路',
		labelWidth : 40,
		valueField : 'destinationOrganizationCode',
		orginalOrganizationCode : platform.lineCargo.transferCenterCode,
		allowBlank: false
	}, {
		fieldLabel : '运输性质',
		name : 'product',
		xtype : 'combobox',
		queryMode: 'local',
		triggerAction:'all',
		displayField: 'valueName',
		valueField: 'valueCode',
		multiSelect : false,
		editable : false,
		allowBlank: false,
		value : 'ALL',
		store : Ext.create('Foss.platform.lineCargo.productStore')
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
		value : 'NO_HANDOVER',
		store : Ext.create('Ext.data.Store', {
			fields: ['name', 'code'],
			data : [{'name' : '未交接','code' : 'NO_HANDOVER'},
			        {'name' : '交接未出发', 'code':'HANDOVER_NO_DEPART'},
			        {'name' : '在途', 'code':'ON_THE_WAY'},
			        {'name' : '到达未卸车', 'code':'ARRIVED_NO_UNLOAD'},
			        {'name' : '在库', 'code':'IN_STOCK'}]
		})
	},{
		columnWidth : 1 / 2,
		fieldId : 'Foss_platform_lineCargo_queryForm_billTime_id',
		xtype : 'rangeDateField',
		dateType : 'datetimefield_date97',
		dateRange : 7,
		fieldLabel : '开单时间',
		allowBlank : false,
		fromName : 'beginTime',
		toName : 'endTime',
		fromValue: Ext.Date.format(new Date(new Date().getFullYear(), new Date().getMonth(), new Date().getDate() - 4
				,'00','00','00'), 'Y-m-d H:i:s'),
		toValue: Ext.Date.format(new Date(new Date().getFullYear(), new Date().getMonth(), new Date().getDate()
				,'23','59','59'), 'Y-m-d H:i:s'),
	},{
		xtype : 'textfield',
		fieldLabel : '运单号',
		name : 'waybillNo'
	}],
	buttons : [{
		xtype : 'button',
		columnWidth : .08,
		text : '重置',
		handler : function() {
			var form = this.up('form').getForm();
			form.reset();
			if (platform.lineCargo.transferCenterCode) {
				form.findField('origDept').setCombValue(
						platform.lineCargo.transferCenterName,
						platform.lineCargo.transferCenterCode);
			}
			var now = new Date();
			form.findField('beginTime').setValue(Ext.Date.format(
					new Date(now.getFullYear(), now.getMonth(), now.getDate() - 4,
							0, 0, 0), 'Y-m-d H:i:s'));
			form.findField('endTime').setValue(Ext.Date.format(new Date(
					now.getFullYear(), now.getMonth(), now.getDate(),
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
				platform.lineCargo.resultGrid.store.load();
			}
		}
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

Ext.define('Foss.platform.lineCargo.serialNoModel', {
	extend : 'Ext.data.Model',
	fields : [{name : 'waybillNo',type : 'string'},
	          {name : 'serialNo',type : 'string'}
	]
});

Ext.define('Foss.platform.lineCargo.serialNoStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.platform.lineCargo.serialNoModel',
	proxy : {
		type : 'ajax',
		actionMethods : 'POST',
		url : platform.realPath('querySerialNos.action'),
		timeout : 300000,
		reader : {
			type : 'json',
			root : 'lineCargoVo.serialNoDtos',
			successProperty : 'success'
		}
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

Ext.define('Foss.platform.lineCargo.serialNoGrid', {
	extend : 'Ext.grid.Panel',
	columnLines: true,
	frame: true,
	autoScroll : true,
	width : 220,
	store : Ext.create('Foss.platform.lineCargo.serialNoStore'),
	columns : [{
		dataIndex : 'waybillNo',
		align : 'center',
		width : 90,
		xtype : 'ellipsiscolumn',
		text : '运单号'
	},{
		dataIndex : 'serialNo',
		align : 'center',
		width : 90,
		xtype : 'ellipsiscolumn',
		text : '流水号'
	}],
	bindData : function(record,grid,rowBodyElement){
		var me = this,
		origDeptCode = record.get('origDeptCode'),
		destDeptCode = record.get('destDeptCode'),
		waybillNo = record.get('waybillNo'),
		statusCode = record.get('statusCode'),
		vehicleNo = record.get('vehicleNo');
		me.store.load({
			params : {
				'lineCargoVo.lineCargoQcDto.origDeptCode' : origDeptCode,
				'lineCargoVo.lineCargoQcDto.destDeptCode' : destDeptCode,
				'lineCargoVo.lineCargoQcDto.waybillNo' : waybillNo,
				'lineCargoVo.lineCargoQcDto.statusCode' : statusCode,
				'lineCargoVo.lineCargoQcDto.vehicleNo' : vehicleNo
			}
		});
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

Ext.define('Foss.platform.lineCargo.gridModel', {
	extend : 'Ext.data.Model',
	fields : [{
		name : 'origDeptCode',
		type : 'string'
	}, {
		name : 'destDeptCode',
		type : 'String'
	}, {
		name : 'destDeptName',
		type : 'string'
	}, {
		name : 'waybillNo',
		type : 'string'
	}, {
		name : 'statusCode',
		type : 'string'
	}, {
		name : 'statusName',
		type : 'string'
	}, {
		name : 'waybillQty',
		type : 'string'
	}, {
		name : 'currentQty',
		type : 'string'
	}, {
		name : 'currentWeight',
		type : 'string'
	}, {
		name : 'currentVolume',
		type : 'string'
	}, {
		name : 'productName',
		type : 'string'
	}, {
		name : 'billTime',
		type : 'date',
		convert : dateConvert
	}, {
		name : 'vehicleassembleNo',
		type : 'string'
	}, {
		name : 'vehicleNo',
		type : 'string'
	}]
});

Ext.define('Foss.platform.lineCargo.gridStore', {
	extend : 'Ext.data.Store',
	//pageSize : 25,
	model : 'Foss.platform.lineCargo.gridModel',
	proxy : {
		type : 'ajax',
		actionMethods : 'POST',
		url : platform.realPath('queryLineCargo.action'),
		timeout : 300000,
		reader : {
			type : 'json',
			root : 'lineCargoVo.lineCargoDtos',
			//totalProperty : 'totalCount',
			successProperty : 'success'
		}
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	},
	listeners : {
		beforeload : function(store, operation, eOpts) {
			var queryForm = Ext.getCmp('Foss_platform_lineCargo_queryForm_id').getForm();
			if (!Ext.isEmpty(queryForm)) {
				var queryParams = queryForm.getValues();
				Ext.apply(operation, {
					params : {
						'lineCargoVo.lineCargoQcDto.origDeptCode' : queryParams.origDept,
						'lineCargoVo.lineCargoQcDto.destDeptCode' : queryParams.destDept,
						'lineCargoVo.lineCargoQcDto.productCode' : queryParams.product,
						'lineCargoVo.lineCargoQcDto.statusCode' : queryParams.status,
						'lineCargoVo.lineCargoQcDto.statusName' : queryForm.findField('status').getRawValue(),
						'lineCargoVo.lineCargoQcDto.waybillNo' : queryParams.waybillNo,
						'lineCargoVo.lineCargoQcDto.beginTime' : queryParams.beginTime,
						'lineCargoVo.lineCargoQcDto.endTime' : queryParams.endTime
					}
				});
			}
		},
		load : function( store, records, successful, eOpts){
			var lineCargoVo = store.proxy.reader.rawData.lineCargoVo;
			Ext.getCmp('Foss_platform_lineCargo_resultGrid_totalVote_id')
				.setValue(lineCargoVo.lineCargoTotalDto.totalVote);
			Ext.getCmp('Foss_platform_lineCargo_resultGrid_totalQty_id')
				.setValue(lineCargoVo.lineCargoTotalDto.totalQty);
			Ext.getCmp('Foss_platform_lineCargo_resultGrid_totalWeight_id')
				.setValue(lineCargoVo.lineCargoTotalDto.totalWeight);
			Ext.getCmp('Foss_platform_lineCargo_resultGrid_totalVolume_id')
				.setValue(lineCargoVo.lineCargoTotalDto.totalVolume);
		}
	}
});

Ext.define('Foss.platform.lineCargo.resultGrid', {
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
	plugins : [ {
		header : true,
		ptype : 'rowexpander',
		rowsExpander : false,
		expandOnDblClick : false,
		rowBodyElement : 'Foss.platform.lineCargo.serialNoGrid'
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.platform.lineCargo.gridStore');
		me.tbar = [{
			xtype : 'button',
			text : '导出',
			handler : function() {
				var queryForm = Ext.getCmp('Foss_platform_lineCargo_queryForm_id').getForm(), 
				queryParams = queryForm.getValues();

				if (!queryForm.isValid()) {
					Ext.ux.Toast.msg('提示', '请输入合法的导出条件！', 'error', 2000);
					return;
				}

				var params = {
						'lineCargoVo.lineCargoQcDto.origDeptCode' : queryParams.origDept,
						'lineCargoVo.lineCargoQcDto.destDeptCode' : queryParams.destDept,
						'lineCargoVo.lineCargoQcDto.productCode' : queryParams.product,
						'lineCargoVo.lineCargoQcDto.statusCode' : queryParams.status,
						'lineCargoVo.lineCargoQcDto.statusName' : queryForm.findField('status').getRawValue(),
						'lineCargoVo.lineCargoQcDto.waybillNo' : queryParams.waybillNo,
						'lineCargoVo.lineCargoQcDto.beginTime' : queryParams.beginTime,
						'lineCargoVo.lineCargoQcDto.endTime' : queryParams.endTime
				};

				if (!Ext.fly('downloadAttachFileForm')) {
					var frm = document.createElement('form');
					frm.id = 'downloadAttachFileForm';
					frm.style.display = 'none';
					document.body.appendChild(frm);
				}

				Ext.Ajax.request({
					url : platform.realPath('exportLineCargo.action'),
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
			dataIndex : 'destDeptName',
			align : 'center',
			width : 120,
			xtype : 'ellipsiscolumn',
			sortable : false,
			text : '下一线路'
		},{
			dataIndex : 'waybillNo',
			align : 'center',
			width : 90,
			xtype : 'ellipsiscolumn',
			text : '运单号'
		},{
			dataIndex : 'statusName',
			align : 'center',
			width : 80,
			xtype : 'ellipsiscolumn',
			sortable : false,
			text : '货物状态'
		},{
			dataIndex : 'waybillQty',
			align : 'center',
			width : 60,
			xtype : 'ellipsiscolumn',
			sortable : false,
			text : '运单件数'
		},{
			dataIndex : 'currentQty',
			align : 'center',
			width : 60,
			xtype : 'ellipsiscolumn',
			sortable : false,
			text : '当前件数'
		},{
			dataIndex : 'currentWeight',
			align : 'center',
			width : 60,
			xtype : 'ellipsiscolumn',
			sortable : false,
			text : '重量'
		},{
			dataIndex : 'currentVolume',
			align : 'center',
			width : 60,
			xtype : 'ellipsiscolumn',
			sortable : false,
			text : '体积'
		},{
			dataIndex : 'productName',
			align : 'center',
			width : 85,
			xtype : 'ellipsiscolumn',
			text : '运输性质'
		},{
			dataIndex : 'billTime',
			align : 'center',
			width : 140,
			text : '开单时间',
			xtype : 'datecolumn',
			format : 'Y-m-d H:i:s'
		},{
			dataIndex : 'vehicleassembleNo',
			align : 'center',
			width : 145,
			xtype : 'ellipsiscolumn',
			text : '车次号'
		},{
			dataIndex : 'vehicleNo',
			align : 'center',
			width : 70,
			xtype : 'ellipsiscolumn',
			text : '车牌号'
		}];
		me.dockedItems = [{
			xtype:'toolbar',
			dock:'bottom',
			layout:'column',
			defaults:{
				xtype:'textfield',
				value:'0',
				readOnly:true
			},
			items:[{
				fieldLabel : '总票数',
				id : 'Foss_platform_lineCargo_resultGrid_totalVote_id',
				columnWidth : .25,
				dataIndex : 'totalVote'
			},{
				fieldLabel : '总件数',
				id:'Foss_platform_lineCargo_resultGrid_totalQty_id',
				columnWidth : .25,
				dataIndex: 'totalQty'
			},{
				fieldLabel : '总重量(吨)',
				id : 'Foss_platform_lineCargo_resultGrid_totalWeight_id',
				columnWidth : .25,
				dataIndex : 'totalWeight'
			},{
				fieldLabel : '总体积(方)',
				id : 'Foss_platform_lineCargo_resultGrid_totalVolume_id',
				columnWidth : .25,
				dataIndex : 'totalVolume'
			}]
		}];
		/*me.bbar = Ext.create('Deppon.StandardPaging', {
			store : me.store,
			pageSize : 25,
			maximumSize : 200,
			plugins : Ext.create('Deppon.ux.PageSizePlugin', {
				sizeList : [['25', 25], ['50', 50],['125', 125], ['150', 150]]
			})
		});*/
		platform.lineCargo.resultGrid = me;
		me.callParent([cfg]);
	}
});

Ext.onReady(function() {
	var queryForm = Ext.create('Foss.platform.lineCargo.queryForm');
	var resultGrid = Ext.create('Foss.platform.lineCargo.resultGrid');

	var origDeptField = queryForm.getForm().findField('origDept');
	origDeptField.setCombValue(platform.lineCargo.transferCenterName,
			platform.lineCargo.transferCenterCode);
	Ext.create('Ext.panel.Panel', {
		id : 'T_platform-lineCargoIndex_content',
		cls : "panelContentNToolbar",
		bodyCls : 'panelContent-body',
		layout : 'auto',
		items : [queryForm, resultGrid],
		renderTo : 'T_platform-lineCargoIndex-body'
	});
});