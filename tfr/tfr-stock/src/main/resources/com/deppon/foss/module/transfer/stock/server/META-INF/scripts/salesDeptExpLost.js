Ext.define('Foss.stock.salesDeptExpLost.queryForm', {
	id : 'Foss_stock_salesDeptExpLost_queryForm_id',
	extend : 'Ext.form.Panel',
	title : '查询条件',
	frame : true,
	collapsible : false,
	animCollapse : false,
	defaults : {
		margin : '5 10 5 10',
		labelWidth : 85,
		columnWidth : 1 / 4
	},
	layout : 'column',
	items : [{
		fieldLabel : '部门',
		name : 'orgInfo',
		xtype : 'dynamicorgcombselector',
		types : 'ORG',
		salesDepartment : 'Y',
		allowBlank : false,
		readOnly : FossUserContext.getCurrentDept().salesDepartment === 'Y'
	}, {
		xtype : 'textfield',
		fieldLabel : '运单号',
		name : 'waybillNo'
	}, {
		fieldLabel : '差异报告状态',
		labelWidth : 120,
		name : 'status',
		xtype : 'combobox',
		queryMode: 'local',
		allowBlank : false,
		displayField: 'name',
		valueField: 'code',
		editable : false,
		value : '',
		store : Ext.create('Ext.data.Store', {
			fields: ['name', 'code'],
			data : [
			        {'name' : '全部','code' : ''},
			        {'name' : '24H', 'code':'24H'},
			        {'name' : '48H', 'code':'48H'},
			        {'name' : '72H', 'code':'72H'}
			        ]
		})
	}, {
		fieldLabel : '是否已上报丢货',
		labelWidth : 120,
		name : 'reported',
		xtype : 'combobox',
		queryMode: 'local',
		allowBlank : false,
		displayField: 'name',
		valueField: 'code',
		editable : false,
		value : 'N',
		store : Ext.create('Ext.data.Store', {
			fields: ['name', 'code'],
			data : [
			        {'name' : '是','code' : 'Y'},
			        {'name' : '否', 'code':'N'}
			        ]
		})
	}],
	buttons : [{
		xtype : 'button',
		columnWidth : .08,
		text : '重置',
		handler : function() {
			var form = this.up('form').getForm(),
			currentDept = FossUserContext.getCurrentDept();
			form.reset();
			form.findField('orgInfo').setCombValue(
					deptName,deptCode);
		}
	}, '->', {
		columnWidth : .08,
		xtype : 'button',
		cls : 'yellow_button',
		text : '查询',
		handler : function() {
			var form = this.up('form').getForm();
			if (form.isValid()) {
				stock.salesDeptExpLost.pagingBar.moveFirst();
			}
		}
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

Ext.define('Foss.stock.salesDeptExpLost.serialNoModel', {
	extend : 'Ext.data.Model',
	fields : [{name : 'waybillNo',type : 'string'},
	          {name : 'serialNo',type : 'string'}
	]
});

Ext.define('Foss.stock.salesDeptExpLost.serialNoStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.stock.salesDeptExpLost.serialNoModel',
	proxy : {
		type : 'ajax',
		actionMethods : 'POST',
		url : stock.realPath('queryReportSerialNo.action'),
		reader : {
			type : 'json',
			root : 'salesDeptExpLostVo.waybillSerialNos',
			successProperty : 'success'
		}
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

Ext.define('Foss.stock.salesDeptExpLost.serialNoGrid', {
	extend : 'Ext.grid.Panel',
	columnLines: true,
	frame: true,
	autoScroll : true,
	width : 220,
	store : Ext.create('Foss.stock.salesDeptExpLost.serialNoStore'),
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
		orgCode = record.get('orgCode'),
		waybillNo = record.get('waybillNo'),
		status = record.get('status'),
		reported = record.get('reported');
		me.store.load({
			params : {
				'salesDeptExpLostVo.qcEntity.orgCode' : orgCode,
				'salesDeptExpLostVo.qcEntity.waybillNo' : waybillNo,
				'salesDeptExpLostVo.qcEntity.status' : status,
				'salesDeptExpLostVo.qcEntity.reported' : reported,
			}
		});
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

Ext.define('Foss.stock.salesDeptExpLost.gridModel', {
	extend : 'Ext.data.Model',
	fields : [{
		name : 'orgCode',
		type : 'string'
	}, {
		name : 'orgName',
		type : 'String'
	}, {
		name : 'waybillNo',
		type : 'string'
	}, {
		name : 'status',
		type : 'string'
	}, {
		name : 'reported',
		type : 'string'
	}, {
		name : 'staTime',
		type : 'date',
		convert : dateConvert
	}]
});

Ext.define('Foss.stock.salesDeptExpLost.gridStore', {
	extend : 'Ext.data.Store',
	pageSize : 25,
	model : 'Foss.stock.salesDeptExpLost.gridModel',
	proxy : {
		type : 'ajax',
		actionMethods : 'POST',
		url : stock.realPath('queryReportWaybillNoPaging.action'),
		reader : {
			type : 'json',
			root : 'salesDeptExpLostVo.salesDeptExpLostEntities',
			successProperty : 'success',
			totalProperty : 'totalCount'
		}
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	},
	listeners : {
		'beforeload' : function(store, operation, eOpts) {
			var queryForm = Ext.getCmp('Foss_stock_salesDeptExpLost_queryForm_id').getForm();
			if (!Ext.isEmpty(queryForm)) {
				var queryParams = queryForm.getValues();
				Ext.apply(operation, {
					params : {
						'salesDeptExpLostVo.qcEntity.orgCode' : queryForm.findField('orgInfo').getValue(),
						'salesDeptExpLostVo.qcEntity.waybillNo' : queryParams.waybillNo,
						'salesDeptExpLostVo.qcEntity.status' : queryParams.status,
						'salesDeptExpLostVo.qcEntity.reported' : queryParams.reported
					}
				});
			}
		}
	}
});

Ext.define('Foss.stock.salesDeptExpLost.resultGrid', {
	extend : 'Ext.grid.Panel',
	frame : true,
	columnLines : true,
	title : '差异报告列表',
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	emptyText : '查询结果为空',
	sortableColumns : false,
	enableColumnHide : false,
	autoScroll : false,
	collapsible : false,
	animCollapse : false,
	plugins : [ {
		header : true,
		ptype : 'rowexpander',
		rowsExpander : false,
		expandOnDblClick : false,
		rowBodyElement : 'Foss.stock.salesDeptExpLost.serialNoGrid'
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.stock.salesDeptExpLost.gridStore');
		me.tbar = [{
			xtype : 'button',
			text : '导出',
			handler : function() {
				var form = Ext.getCmp('Foss_stock_salesDeptExpLost_queryForm_id').getForm(), 
				queryParams = form.getValues();

				if (!form.isValid()) {
					Ext.ux.Toast.msg('提示', '请输入合法的导出条件！', 'error', 2000);
					return;
				}

				var params = {
						'salesDeptExpLostVo.qcEntity.orgCode' : form.findField('orgInfo').getValue(),
						'salesDeptExpLostVo.qcEntity.waybillNo' : queryParams.waybillNo,
						'salesDeptExpLostVo.qcEntity.status' : queryParams.status,
						'salesDeptExpLostVo.qcEntity.reported' : queryParams.reported
				};

				if (!Ext.fly('downloadAttachFileForm')) {
					var frm = document.createElement('form');
					frm.id = 'downloadAttachFileForm';
					frm.style.display = 'none';
					document.body.appendChild(frm);
				}

				Ext.Ajax.request({
					url : stock.realPath('exportReport.action'),
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
			dataIndex : 'orgName',
			align : 'center',
			width : 120,
			text : '部门名称'
		},{
			dataIndex : 'waybillNo',
			align : 'center',
			width : 120,
			text : '运单号'
		},{
			dataIndex : 'status',
			align : 'center',
			width : 120,
			text : '差异报告状态'
		},{
			dataIndex : 'reported',
			align : 'center',
			width : 120,
			text : '是否已上报丢货',
			renderer: function(value){
				if (value === 'Y') {
					return '是';
				}
				return '否';
			}
		},{
			dataIndex : 'staTime',
			align : 'center',
			width : 160,
			text : '报告生成时间',
			xtype : 'datecolumn',
			format : 'Y-m-d H:i:s'
		}];
		me.bbar = Ext.create('Deppon.StandardPaging', {
			store : me.store,
			pageSize : 25,
			maximumSize : 200,
			plugins : Ext.create('Deppon.ux.PageSizePlugin', {
				sizeList : [['25', 25], ['50', 50],['125', 125], ['150', 150]]
			})
		});
		stock.salesDeptExpLost.pagingBar = me.bbar;
		me.callParent([cfg]);
	}
});

Ext.onReady(function() {
	var queryForm = Ext.create('Foss.stock.salesDeptExpLost.queryForm');
	var resultGrid = Ext.create('Foss.stock.salesDeptExpLost.resultGrid');

	var orgInfoField = queryForm.getForm().findField('orgInfo'),
	currentDept = FossUserContext.getCurrentDept();
	//currentDept.name,currentDept.code
	orgInfoField.setCombValue(deptName,deptCode);

	Ext.create('Ext.panel.Panel', {
		id : 'T_stock-salesDeptExpLostIndex_content',
		cls : "panelContentNToolbar",
		bodyCls : 'panelContent-body',
		layout : 'auto',
		items : [queryForm, resultGrid],
		renderTo : 'T_stock-salesDeptExpLostIndex-body'
	});
});