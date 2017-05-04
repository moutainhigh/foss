Ext.define('Foss.unload.manualInput.forkVoteQueryForm', {
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
		fieldLabel : '外场',
		name : 'transferCenter',
		xtype : 'dynamicorgcombselector',
		types : 'ORG',
		transferCenter : 'Y',
		readOnly : !Ext.isEmpty(unload.manualInput.parentTfrCtrCode)
	},{
		xtype : 'monthdatefield',
		fieldLabel : '月份',
		name : 'inputMonth',
		allowBlank : false,
		value : new Date()
	}],
	buttons : [{
		xtype : 'button',
		columnWidth : .08,
		text : '重置',
		handler : function() {
			var form = this.up('form').getForm();
			form.reset();
			if (unload.manualInput.parentTfrCtrCode) {
				form.findField('transferCenter').setCombValue(
						unload.manualInput.parentTfrCtrName,
						unload.manualInput.parentTfrCtrCode);
			}
			
		}
	}, '->', {
		columnWidth : .08,
		xtype : 'button',
		cls : 'yellow_button',
		text : '查询',
		handler : function() {
			var form = this.up('form').getForm();
			if (form.isValid()) {
				unload.manualInput.forkVoteGrid.store.load();
			}
		}
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
});

Ext.define('Foss.unload.manualInput.goodsQtyQueryForm', {
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
		fieldLabel : '外场',
		name : 'transferCenter',
		xtype : 'dynamicorgcombselector',
		types : 'ORG',
		transferCenter : 'Y',
		readOnly : !Ext.isEmpty(unload.manualInput.parentTfrCtrCode)
	},{
		xtype : 'monthdatefield',
		fieldLabel : '月份',
		name : 'inputMonth',
		allowBlank : false,
		value : new Date()
	}],
	buttons : [{
		xtype : 'button',
		columnWidth : .08,
		text : '重置',
		handler : function() {
			var form = this.up('form').getForm();
			form.reset();
			if (unload.manualInput.parentTfrCtrCode) {
				form.findField('transferCenter').setCombValue(
						unload.manualInput.parentTfrCtrName,
						unload.manualInput.parentTfrCtrCode);
			}
		}
	}, '->', {
		columnWidth : .08,
		xtype : 'button',
		cls : 'yellow_button',
		text : '查询',
		handler : function() {
			var form = this.up('form').getForm();
			if (form.isValid()) {
				unload.manualInput.goodsQtyGrid.store.load();
			}
		}
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
});

Ext.define('Foss.unload.manualInput.forkVoteGridModel', {
	extend : 'Ext.data.Model',
	fields : [{
		name : 'transferCenterCode',
		type : 'string'
	}, {
		name : 'transferCenterName',
		type : 'String'
	}, {
		name : 'vote',
		type : 'string'
	}, {
		name : 'note',
		type : 'string'
	}, {
		name : 'inputDate',
		type : 'date',
		convert : dateConvert
	}, {
		name : 'inputMonth',
		type : 'string'
	}, {
		name : 'createTime',
		type : 'date',
		convert : dateConvert
	}, {
		name : 'createUserCode',
		type : 'string'
	}, {
		name : 'createUserName',
		type : 'string'
	}, {
		name : 'modifyTime',
		type : 'date',
		convert : dateConvert
	}, {
		name : 'modifyUserCode',
		type : 'string'
	}, {
		name : 'modifyUserName',
		type : 'string'
	}]
});

Ext.define('Foss.unload.manualInput.goodsQtyGridModel', {
	extend : 'Ext.data.Model',
	fields : [{
		name : 'transferCenterCode',
		type : 'string'
	}, {
		name : 'transferCenterName',
		type : 'String'
	}, {
		name : 'total',
		type : 'string'
	}, {
		name : 'loadAndUnload',
		type : 'string'
	}, {
		name : 'dispath',
		type : 'string'
	}, {
		name : 'stationReceive',
		type : 'string'
	}, {
		name : 'centralizedReceive',
		type : 'string'
	}, {
		name : 'selfPickup',
		type : 'string'
	}, {
		name : 'other',
		type : 'string'
	}, {
		name : 'note',
		type : 'string'
	}, {
		name : 'inputDate',
		type : 'date',
		convert : dateConvert
	}, {
		name : 'inputMonth',
		type : 'string'
	}, {
		name : 'createTime',
		type : 'date',
		convert : dateConvert
	}, {
		name : 'createUserCode',
		type : 'string'
	}, {
		name : 'createUserName',
		type : 'string'
	}, {
		name : 'modifyTime',
		type : 'date',
		convert : dateConvert
	}, {
		name : 'modifyUserCode',
		type : 'string'
	}, {
		name : 'modifyUserName',
		type : 'string'
	}]
});

Ext.define('Foss.unload.manualInput.forkVoteGridStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.unload.manualInput.forkVoteGridModel',
	proxy : {
		type : 'ajax',
		actionMethods : 'POST',
		url : unload.realPath('queryForkVoteEntities.action'),
		reader : {
			type : 'json',
			root : 'manualInputVo.manualInputForkVoteEntities',
			successProperty : 'success'
		}
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	},
	listeners : {
		'beforeload' : function(store, operation, eOpts) {
			var queryForm = unload.manualInput.forkVoteQueryForm.getForm();
			if (!Ext.isEmpty(queryForm)) {
				var queryParams = queryForm.getValues();
				Ext.apply(operation, {
					params : {
						'manualInputVo.manualInputQcDto.transferCenterCode' : queryForm
						.findField('transferCenter').getValue(),
						'manualInputVo.manualInputQcDto.inputMonth' : queryParams.inputMonth
					}
				});
			}
		}
	}
});

Ext.define('Foss.unload.manualInput.goodsQtyGridStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.unload.manualInput.goodsQtyGridModel',
	proxy : {
		type : 'ajax',
		actionMethods : 'POST',
		url : unload.realPath('queryGoodsQtyEntities.action'),
		reader : {
			type : 'json',
			root : 'manualInputVo.manualInputGoodsQtyEntities',
			successProperty : 'success'
		}
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	},
	listeners : {
		'beforeload' : function(store, operation, eOpts) {
			var queryForm = unload.manualInput.goodsQtyQueryForm.getForm();
			if (!Ext.isEmpty(queryForm)) {
				var queryParams = queryForm.getValues();
				Ext.apply(operation, {
					params : {
						'manualInputVo.manualInputQcDto.transferCenterCode' : queryForm
						.findField('transferCenter').getValue(),
						'manualInputVo.manualInputQcDto.inputMonth' : queryParams.inputMonth
					}
				});
			}
		}
	}
});

unload.manualInput.initWindow = function(window){
	var editForm = window.editForm.getForm(),
	transferCenterField = editForm.findField('transferCenter'),
	inputDateField = editForm.findField('inputDate');

	transferCenterField.setCombValue(
			unload.manualInput.parentTfrCtrName,
			unload.manualInput.parentTfrCtrCode);

	inputDateField.setValue(new Date());
	return window;
};

Ext.define('Foss.unload.manualInput.forkVoteGrid', {
	extend : 'Ext.grid.Panel',
	frame : true,
	columnLines : true,
	title : '操作货量补录信息',
	height : 600,
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	emptyText : '查询结果为空',
	sortableColumns : false,
	enableColumnHide : false,
	autoScroll : false,
	collapsible : false,
	animCollapse : false,
	forkVoteWindow : null,
	getForkVoteyWindow : function(){
		var me = this;
		if(Ext.isEmpty(me.forkVoteWindow)){
			me.forkVoteWindow = Ext.create('Foss.unload.manualInput.forkVoteWindow');
		}
		return unload.manualInput.initWindow(me.forkVoteWindow);
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.unload.manualInput.forkVoteGridStore');
		me.tbar = [{
			xtype : 'button',
			text : '补录',
			hidden : Ext.isEmpty(unload.manualInput.parentTfrCtrCode),
			handler : function() {
				me.getForkVoteyWindow().show();
			}
		}];
		me.columns = [{
			dataIndex : 'transferCenterName',
			align : 'center',
			width : 120,
			text : '外场'
		}, {
			dataIndex : 'inputDate',
			align : 'center',
			width : 80,
			text : '日期',
			xtype : 'datecolumn',
			format : 'Y-m-d'
		}, {
			dataIndex : 'vote',
			align : 'center',
			text : '叉车票'
		}, {
			dataIndex : 'note',
			align : 'center',
			text : '备注',
			xtype : 'ellipsiscolumn'
		}, {
			dataIndex : 'createUserName',
			align : 'center',
			text : '创建人'
		}, {
			dataIndex : 'createTime',
			align : 'center',
			text : '创建时间',
			xtype : 'datecolumn',
			format : 'Y-m-d H:i:s',
			width : 140
		}, {
			dataIndex : 'modifyUserName',
			align : 'center',
			text : '修改人'
		}, {
			dataIndex : 'modifyTime',
			align : 'center',
			text : '修改时间',
			xtype : 'datecolumn',
			format : 'Y-m-d H:i:s',
			width : 140
		}];
		me.callParent([cfg]);
	}
});

Ext.define('Foss.unload.manualInput.goodsQtyGrid', {
	extend : 'Ext.grid.Panel',
	frame : true,
	columnLines : true,
	title : '操作货量补录信息',
	height : 600,
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	emptyText : '查询结果为空',
	sortableColumns : false,
	enableColumnHide : false,
	autoScroll : false,
	collapsible : false,
	animCollapse : false,
	goodsQtyWindow : null,
	getGoodsQtyWindow : function(){
		var me = this;
		if(Ext.isEmpty(me.goodsQtyWindow)){
			me.goodsQtyWindow = Ext.create('Foss.unload.manualInput.goodsQtyWindow');
		}
		return unload.manualInput.initWindow(me.goodsQtyWindow);
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.unload.manualInput.goodsQtyGridStore');
		me.tbar = [{
			xtype : 'button',
			text : '补录',
			hidden : Ext.isEmpty(unload.manualInput.parentTfrCtrCode),
			handler : function() {
				me.getGoodsQtyWindow().show();
			}
		}];
		me.columns = [{
			dataIndex : 'transferCenterName',
			align : 'center',
			width : 120,
			text : '外场'
		}, {
			dataIndex : 'inputDate',
			align : 'center',
			width : 80,
			text : '日期',
			xtype : 'datecolumn',
			format : 'Y-m-d'
		}, {
			dataIndex : 'total',
			align : 'center',
			text : '补录货量',
			width : 60
		}, {
			text : '补录货量类型',
			columns : [{
				dataIndex : 'loadAndUnload',
				align : 'center',
				text : '装卸车货量',
				width : 70
			},{
				dataIndex : 'dispath',
				align : 'center',
				text : '派送货量',
				width : 60
			}, {
				dataIndex : 'stationReceive',
				align : 'center',
				text : '驻地收获货量',
				width : 70
			}, {
				dataIndex : 'centralizedReceive',
				align : 'center',
				text : '集中接货货量',
				width : 70
			}, {
				dataIndex : 'selfPickup',
				align : 'center',
				text : '自提货量',
				width : 60
			}, {
				dataIndex : 'other',
				align : 'center',
				text : '其他货量',
				width : 60
			}]
		}, {
			dataIndex : 'note',
			align : 'center',
			text : '备注',
			xtype : 'ellipsiscolumn'
		}, {
			dataIndex : 'createUserName',
			align : 'center',
			text : '创建人'
		}, {
			dataIndex : 'createTime',
			align : 'center',
			text : '创建时间',
			xtype : 'datecolumn',
			format : 'Y-m-d H:i:s',
			width : 140
		}, {
			dataIndex : 'modifyUserName',
			align : 'center',
			text : '修改人'
		}, {
			dataIndex : 'modifyTime',
			align : 'center',
			text : '修改时间',
			xtype : 'datecolumn',
			format : 'Y-m-d H:i:s',
			width : 140
		}];
		me.callParent([cfg]);
	}
});

Ext.define('Foss.unload.manualInput.forkVoteForm', {
	extend : 'Ext.form.Panel',
	title : '叉车票',
	frame : true,
	collapsible : true,
	animCollapse : true,
	defaults : {
		margin : '5 10 5 10',
		labelWidth : 85,
		columnWidth : 1 / 2
	},
	layout : 'column',
	viewState : null,
	oldFormRecord : null,
	items : [{
		fieldLabel : '外场',
		name : 'transferCenter',
		xtype : 'dynamicorgcombselector',
		types : 'ORG',
		transferCenter : 'Y',
		allowBlank : false,
		readOnly : true
	}, {
		fieldLabel : '日期',
		name : 'inputDate',
		xtype : 'datefield',
		allowBlank : false,
		valute : new Date(),
		format : 'Y-m-d',
		allowBlank : false,
		readOnly : true
	}, {
		xtype : 'numberfield',
		fieldLabel : '叉车票调整(张)',
		name : 'vote',
		allowDecimals : false,
		allowBlank : false,
		value: 0,
		maxValue : 999999999,
		minValue : -999999999
	}, {
		xtype : 'textareafield',
		fieldLabel : '备注',
		name : 'note',
		grow : true,
		maxLength : 100
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

Ext.define('Foss.unload.manualInput.goodsQtyForm', {
	extend : 'Ext.form.Panel',
	title : '操作货量',
	frame : true,
	collapsible : true,
	animCollapse : true,
	defaults : {
		margin : '5 10 5 10',
		labelWidth : 150,
		columnWidth : 1 / 2
	},
	layout : 'column',
	viewState : null,
	oldFormRecord : null,
	items : [{
		fieldLabel : '外场',
		name : 'transferCenter',
		xtype : 'dynamicorgcombselector',
		types : 'ORG',
		transferCenter : 'Y',
		allowBlank : false,
		readOnly : true
	}, {
		fieldLabel : '日期',
		name : 'inputDate',
		xtype : 'datefield',
		allowBlank : false,
		valute : new Date(),
		format : 'Y-m-d',
		allowBlank : false,
		readOnly : true
	}, {
		xtype : 'numberfield',
		fieldLabel : '装卸车货量调整(吨)',
		name : 'loadAndUnload',
		allowBlank : false,
		value: 0,
		maxValue : 999999999.99,
		minValue : -999999999.99
	}, {
		xtype : 'numberfield',
		fieldLabel : '派送货量调整(吨)',
		name : 'dispath',
		allowBlank : false,
		value: 0,
		maxValue : 999999999.99,
		minValue : -999999999.99
	}, {
		xtype : 'numberfield',
		fieldLabel : '驻地收货货量调整(吨)',
		name : 'stationReceive',
		allowBlank : false,
		value: 0,
		maxValue : 999999999.99,
		minValue : -999999999.99
	}, {
		xtype : 'numberfield',
		fieldLabel : '集中接货货量调整(吨)',
		name : 'centralizedReceive',
		allowBlank : false,
		value: 0,
		maxValue : 999999999.99,
		minValue : -999999999.99
	}, {
		xtype : 'numberfield',
		fieldLabel : '自提货货量调整(吨)',
		name : 'selfPickup',
		allowBlank : false,
		value: 0,
		maxValue : 999999999.99,
		minValue : -999999999.99
	}, {
		xtype : 'numberfield',
		fieldLabel : '其他货量调整(吨)',
		name : 'other',
		allowBlank : false,
		value: 0,
		maxValue : 999999999.99,
		minValue : -999999999.99
	}, {
		xtype : 'textareafield',
		fieldLabel : '备注',
		name : 'note',
		grow : true,
		columnWidth : 1,
		maxLength : 100
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

Ext.define('Foss.unload.manualInput.forkVoteWindow', {
	extend : 'Ext.window.Window',
	closable : true,
	modal : true,
	resizable : false,
	closeAction : 'hide',
	width : 750,
	height : 300,
	editForm : null,
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	resetData : function() {
		var me = this, form = me.editForm.getForm();
		form.reset();
	},
	initData : function() {
		var me = this, 
		form = me.editForm.getForm(),
		transferCenterField = form.findField('transferCenter');
		transferCenterField.setCombValue(
				unload.manualInput.parentTfrCtrName,
				unload.manualInput.parentTfrCtrCode);
		form.findField('inputDate').setValue(new Date());
	},
	listeners : {
		beforehide : function(me) {
			me.resetData();
		},
		beforeshow : function(me) {
			me.initData();
		}
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.fbar = [{
			text : '取消',
			handler : function() {
				me.hide();
			}
		},  {
			text : '重置',
			handler : function() {
				me.resetData();
				me.initData();
			}
		}, '->',{
			text : '保存',
			cls : 'yellow_button',
			handler : function() {
				var form = me.editForm.getForm();
				if(!form.isValid()){
					return;
				}

				var values = form.getValues();
				if(values.vote === '0'){
					Ext.ux.Toast.msg('提示', '请正确输入叉车票调整数量！', 'error', 2000);
					return;
				}

				var param = {
						'manualInputVo' : {
							'manualInputForkVoteEntity' : values
						}
				};

				param.manualInputVo.manualInputForkVoteEntity.transferCenterCode = unload.manualInput.parentTfrCtrCode;
				param.manualInputVo.manualInputForkVoteEntity.transferCenterName = unload.manualInput.parentTfrCtrName;

				var windowMask = new Ext.LoadMask(me.editForm, {
					msg : "保存中，请稍后..."
				});
				windowMask.show();

				Ext.Ajax.request({
					url : unload.realPath('insertForkVoteEntity.action'),
					jsonData : param,
					success : function(response) {
						windowMask.hide();
						me.close();
						Ext.ux.Toast.msg('提示', '保存成功！', 'info', 1500);
					},
					exception : function(response) {
						var result = Ext.decode(response.responseText);
						windowMask.hide();
						Ext.ux.Toast.msg('提示', '保存失败，' + result.message, 'error', 2000);
					}
				});
			}
		}];
		me.editForm = Ext.create('Foss.unload.manualInput.forkVoteForm');
		me.items = [me.editForm];
		me.callParent([cfg]);
	}
});

Ext.define('Foss.unload.manualInput.goodsQtyWindow', {
	extend : 'Ext.window.Window',
	closable : true,
	modal : true,
	resizable : false,
	closeAction : 'hide',
	width : 750,
	height : 400,
	editForm : null,
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	resetData : function() {
		var me = this, form = me.editForm.getForm();
		form.reset();
	},
	initData : function() {
		var me = this, 
		form = me.editForm.getForm(),
		transferCenterField = form.findField('transferCenter');
		transferCenterField.setCombValue(
				unload.manualInput.parentTfrCtrName,
				unload.manualInput.parentTfrCtrCode);
		form.findField('inputDate').setValue(new Date());
	},
	listeners : {
		beforehide : function(me) {
			me.resetData();
		},
		beforeshow : function(me) {
			me.initData();
		}
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.fbar = [{
			text : '取消',
			handler : function() {
				me.hide();
			}
		},  {
			text : '重置',
			handler : function() {
				me.resetData();
				me.initData();
			}
		}, '->',{
			text : '保存',
			cls : 'yellow_button',
			handler : function() {
				var form = me.editForm.getForm();
				if(!form.isValid()){
					return;
				}

				var values = form.getValues();
				var loadAndUnload = parseFloat(values.loadAndUnload),
				dispath = parseFloat(values.dispath),
				stationReceive = parseFloat(values.stationReceive),
				centralizedReceive = parseFloat(values.centralizedReceive),
				selfPickup = parseFloat(values.selfPickup),
				other = parseFloat(values.other);

				if(loadAndUnload === 0 && dispath === 0 && stationReceive === 0 
						&& centralizedReceive === 0 && selfPickup === 0 && other === 0){
					Ext.ux.Toast.msg('提示', '待调整的操作货量不能全为0！', 'error', 2000);
					return;
				}

				var param = {
						'manualInputVo' : {
							'manualInputGoodsQtyEntity' : values
						}
				};

				param.manualInputVo.manualInputGoodsQtyEntity.transferCenterCode = unload.manualInput.parentTfrCtrCode;
				param.manualInputVo.manualInputGoodsQtyEntity.transferCenterName = unload.manualInput.parentTfrCtrName;
				
				param.manualInputVo.manualInputGoodsQtyEntity.total = loadAndUnload + dispath + stationReceive 
				+ centralizedReceive + selfPickup + other;

				var windowMask = new Ext.LoadMask(me.editForm, {
					msg : "保存中，请稍后..."
				});
				windowMask.show();

				Ext.Ajax.request({
					url : unload.realPath('insertGoodsQtyEntity.action'),
					jsonData : param,
					success : function(response) {
						windowMask.hide();
						me.close();
						Ext.ux.Toast.msg('提示', '保存成功！', 'info', 1500);
					},
					exception : function(response) {
						var result = Ext.decode(response.responseText);
						windowMask.hide();
						Ext.ux.Toast.msg('提示', '保存失败，' + result.message, 'error', 2000);
					}
				});
			}
		}];
		me.editForm = Ext.create('Foss.unload.manualInput.goodsQtyForm');
		me.items = [me.editForm];
		me.callParent([cfg]);
	}
});

Ext.onReady(function() {
	unload.manualInput.forkVoteQueryForm = Ext.create('Foss.unload.manualInput.forkVoteQueryForm'),
	unload.manualInput.goodsQtyQueryForm = Ext.create('Foss.unload.manualInput.goodsQtyQueryForm'),
	unload.manualInput.forkVoteGrid = Ext.create('Foss.unload.manualInput.forkVoteGrid'),
	unload.manualInput.goodsQtyGrid = Ext.create('Foss.unload.manualInput.goodsQtyGrid');

	if (unload.manualInput.parentTfrCtrCode) {
		var forkVoteQueryFormTcField = unload.manualInput.forkVoteQueryForm.getForm().findField('transferCenter');
		var goodsQtyQueryFormTcField = unload.manualInput.goodsQtyQueryForm.getForm().findField('transferCenter');

		forkVoteQueryFormTcField.setReadOnly(true);
		forkVoteQueryFormTcField.setCombValue(
				unload.manualInput.parentTfrCtrName,
				unload.manualInput.parentTfrCtrCode);

		goodsQtyQueryFormTcField.setReadOnly(true);
		goodsQtyQueryFormTcField.setCombValue(
				unload.manualInput.parentTfrCtrName,
				unload.manualInput.parentTfrCtrCode);
	}

	Ext.create('Ext.panel.Panel',{
		id:'T_unload-manualInputDetailIndex_content',
		cls : "panelContentNToolbar",
		bodyCls : 'panelContentNToolbar-body',
		layout: 'auto',
		items : [{
			xtype : 'tabpanel',
			frame : false,
			bodyCls : 'autoHeight',
			cls : 'innerTabPanel',
			items : [{
				title : '操作货量',
				tabConfig : {
					width : 120
				},
				items : [unload.manualInput.goodsQtyQueryForm,unload.manualInput.goodsQtyGrid]
				
			},{
				title : '叉车票',
				tabConfig : {
					width : 120
				},
				items : [unload.manualInput.forkVoteQueryForm,unload.manualInput.forkVoteGrid]
			}]
		}],
		renderTo: 'T_unload-manualInputDetailIndex-body'
	});
});