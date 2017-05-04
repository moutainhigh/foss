Ext.define('Foss.platform.tfrCtrOnDuty.queryForm', {
	id : 'Foss_platform_tfrCtrOnDuty_queryForm_id',
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
		name : 'tfrCtr',
		xtype : 'dynamicorgcombselector',
		types : 'ORG',
		transferCenter : 'Y',
		readOnly : !Ext.isEmpty(platform.tfrCtrOnDuty.tfrCtrCode),
		allowBlank: false
	}, {
		fieldLabel : '部门',
		name : 'org',
		xtype : 'dynamicorgcombselector',
		types : 'ORG'
	},{
		xtype : 'rangeDateField',
		fieldLabel : '上班日期',
		columnWidth : 1 / 2,
		dateType : 'datefield',
		fromName : 'beginDate',
		toName : 'endDate',
		fromValue : new Date(),
		toValue : new Date(),
		dateRange : 31,
		allowBlank : false
	}],
	buttons : [{
		xtype : 'button',
		columnWidth : .08,
		text : '重置',
		handler : function() {
			var form = this.up('form').getForm();
			form.reset();
			if (platform.tfrCtrOnDuty.tfrCtrCode) {
				var tfrCtrField = form.findField('tfrCtr'),
				orgField = form.findField('org');
				tfrCtrField.setCombValue(platform.tfrCtrOnDuty.tfrCtrName,platform.tfrCtrOnDuty.tfrCtrCode);
				orgField.setCombValue(FossUserContext.getCurrentDept().name,FossUserContext.getCurrentDept().code);
			}
			var now = new Date();
			form.findField('beginDate').setValue(new Date());
			form.findField('endDate').setValue(new Date());
		}
	}, '->', {
		columnWidth : .08,
		xtype : 'button',
		cls : 'yellow_button',
		text : '查询',
		handler : function() {
			var form = this.up('form').getForm();
			if (form.isValid()) {
				platform.tfrCtrOnDuty.pagingBar.moveFirst();
			}
		}
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

Ext.define('Foss.platform.tfrCtrOnDuty.gridModel', {
	extend : 'Ext.data.Model',
	fields : [{name : 'id', type : 'string'},
	          {name : 'tfrCtrCode',type : 'String'},
	          {name : 'tfrCtrName',type : 'string'},
	          {name : 'orgCode',type : 'string'},
	          {name : 'orgName',type : 'string'},
	          {name : 'empCode',type : 'string'},
	          {name : 'empName',type : 'string'},
	          {name : 'postCode',type : 'string'},
	          {name : 'postName',type : 'string'},
	          {name : 'onDutyDate',type : 'date',convert : dateConvert},
	          {name : 'beginTime',type : 'date',convert : dateConvert},
	          {name : 'endTime',type : 'date',convert : dateConvert},
	          {name : 'beginDatePart',type : 'date',convert : dateConvert},
	          {name : 'endDatePart',type : 'date',convert : dateConvert},
	          {name : 'beginTimePart',type : 'time',convert : dateConvert},
	          {name : 'endTimePart',type : 'time',convert : dateConvert},
	          {name : 'modifyTime',type : 'long'}]
});

Ext.define('Foss.platform.tfrCtrOnDuty.gridStore', {
	extend : 'Ext.data.Store',
	pageSize : 25,
	model : 'Foss.platform.tfrCtrOnDuty.gridModel',
	proxy : {
		type : 'ajax',
		actionMethods : 'POST',
		url : platform.realPath('findTfrCtrOnDutys.action'),
		timeout : 300000,
		reader : {
			type : 'json',
			root : 'tfrCtrOnDutyVo.tfrCtrOnDutys',
			successProperty : 'success'
		}
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	},
	listeners : {
		beforeload : function(store, operation, eOpts) {
			var queryForm = Ext.getCmp('Foss_platform_tfrCtrOnDuty_queryForm_id').getForm();
			if (!Ext.isEmpty(queryForm)) {
				var queryParams = queryForm.getValues();
				Ext.apply(operation, {
					params : {
						'tfrCtrOnDutyVo.queryParam.tfrCtrCode' : queryParams.tfrCtr,
						'tfrCtrOnDutyVo.queryParam.orgCode' : queryParams.org,
						'tfrCtrOnDutyVo.queryParam.beginDate' : queryParams.beginDate,
						'tfrCtrOnDutyVo.queryParam.endDate' : queryParams.endDate
					}
				});
			}
		}
	}
});

Ext.define('Foss.platform.tfrCtrOnDuty.resultGrid', {
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
	addWindow : null,
	getAddWindow : function() {
		var me = this;
		if (Ext.isEmpty(me.addWindow)) {
			me.addWindow = Ext.create('Foss.platform.tfrCtrOnDuty.addWindow');
		}
		return me.addWindow;
	},
	editWindow : null,
	getEditWindow : function(){
		var me = this;
		if (Ext.isEmpty(me.editWindow)) {
			me.editWindow = Ext.create('Foss.platform.tfrCtrOnDuty.editWindow');
		}
		return me.editWindow;
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.platform.tfrCtrOnDuty.gridStore');
		me.tbar = [{
			xtype : 'button',
			text : '新增',
			handler : function() {
				var addWindow = me.getAddWindow();
				addWindow.show();
				addWindow.result4AddGrid.store.loadData([]);
			}
		}];
		me.columns = [{
			xtype : 'actioncolumn',
			width : 40,
			text : '操作',
			align : 'center',
			menuDisabled : true,
			hideable : false,
			sortable : false,
			items : [{
				tooltip : '修改',
				iconCls : 'deppon_icons_edit',
				handler : function(grid, rowIndex, colIndex) {
					var editWindow = me.getEditWindow();
					editWindow.show();

					var formRecord = grid.getStore().getAt(rowIndex);

					var editForm = editWindow.editForm.getForm();
					editForm.reset();
					formRecord.data.beginTime = Ext.Date.format(new Date(formRecord.data.beginTime),'Y-m-d H:i:s');
					formRecord.data.endTime = Ext.Date.format(new Date(formRecord.data.endTime),'Y-m-d H:i:s');
					editForm.loadRecord(formRecord);
				}
			} , { 
				tooltip : '删除', 
				iconCls : 'deppon_icons_cancel', 
				handler : function(grid, rowIndex, colIndex) {
					var id = grid.store.getAt(rowIndex).data.id;

					Ext.Msg.show({
						title : '提示',
						msg : '确定删除选中信息?',
						buttons : Ext.Msg.YESNO,
						icon : Ext.Msg.QUESTION,
						fn : function(button) {
							if (button === 'yes') {
								Ext.Ajax.request({
									url : platform.realPath('deleteTfrCtrOnDuty.action'),
									jsonData : {
										'tfrCtrOnDutyVo' : {
											'id' : id
										}
									},
									success : function(response) {
										grid.store.loadPage(1);
										Ext.ux.Toast.msg('提示', '删除成功！', 'info', 1500);
									},
									exception : function(response) {
										var result = Ext.decode(response.responseText);
										Ext.ux.Toast.msg('提示', '删除失败，' + result.message,
												'error', 2000);
									}
								});
							}
						}
					});
				} 
			}]
		},{
			dataIndex : 'orgName',
			align : 'center',
			width : 180,
			text : '部门'
		},{
			dataIndex : 'empName',
			align : 'center',
			width : 85,
			text : '姓名'
		},{
			dataIndex : 'empCode',
			align : 'center',
			width : 85,
			text : '工号'
		},{
			dataIndex : 'postName',
			align : 'center',
			width : 120,
			text : '岗位'
		},{
			dataIndex : 'beginTime',
			align : 'center',
			width : 140,
			text : '上班开始时间',
			xtype : 'datecolumn',
			format : 'Y-m-d H:i:s'
		},{
			dataIndex : 'endTime',
			align : 'center',
			width : 140,
			text : '上班结束时间',
			xtype : 'datecolumn',
			format : 'Y-m-d H:i:s'
		}];

		me.bbar = Ext.create('Deppon.StandardPaging', {
			store : me.store,
			pageSize : 25,
			maximumSize : 200,
			plugins : Ext.create('Deppon.ux.PageSizePlugin', {
				sizeList : [['25', 25], ['50', 50],
				            ['100', 100], ['200', 200]]
			})
		});
		platform.tfrCtrOnDuty.pagingBar = me.bbar;
		me.callParent([cfg]);
	}
});

Ext.define('Foss.platform.tfrCtrOnDuty.editForm', {
	extend : 'Ext.form.Panel',
	title : '人员出勤信息修改',
	frame : true,
	collapsible : true,
	animCollapse : true,
	defaults : {
		margin : '5 10 5 10',
		labelWidth : 85,
		columnWidth : .33,
		xtype : 'textfield'
	},
	layout : 'column',
	items : [{
		name : 'id',
		hidden : true
	}, {
		name : 'modifyTime',
		xtype : 'numberfield',
		hidden : true
	},{
		fieldLabel : '转运场',
		name : 'tfrCtrName',
		readOnly : true
	}, {
		fieldLabel : '部门',
		name : 'orgName',
		readOnly : true
	},{
		fieldLabel : '姓名',
		name : 'empName',
		readOnly : true
	},{
		fieldLabel : '岗位',
		name : 'postName',
		readOnly : true
	},{
		xtype : 'rangeDateField',
		fieldLabel : '上班时间',
		columnWidth : 2 / 3,
		fieldId : 'Foss_platform_tfrCtrOnDuty_editForm_beginTime_id',
		dateType : 'datetimefield_date97',
		fromName : 'beginTime',
		toName : 'endTime',
		allowBlank : false
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

Ext.define('Foss.platform.tfrCtrOnDuty.editWindow', {
	extend : 'Ext.window.Window',
	closable : true,
	modal : true,
	resizable : false,
	closeAction : 'hide',
	width : 900,
	height : 300,
	editForm : null,
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.fbar = [{
			text : '取消',
			handler : function() {
				me.hide();
			}
		}, {
			text : '保存',
			cls : 'yellow_button',
			handler : function() {
				var windowMask = new Ext.LoadMask(me, {
					msg : "保存中，请稍后..."
				});
				windowMask.show();

				var form = me.editForm.getForm(), formValues = form.getValues();

				var date = new Date();
				date.setTime(formValues.modifyTime);

				var params = {
						'tfrCtrOnDutyVo' : {
							'tfrCtrOnDuty' : formValues
						}
				};

				params.tfrCtrOnDutyVo.tfrCtrOnDuty.id = formValues.id;
				params.tfrCtrOnDutyVo.tfrCtrOnDuty.modifyTime = date;
				params.tfrCtrOnDutyVo.tfrCtrOnDuty.beginTime = formValues.beginTime;
				params.tfrCtrOnDutyVo.tfrCtrOnDuty.endTime = formValues.endTime;

				Ext.Ajax.request({
					url : platform.realPath('updateTfrCtrOnDuty.action'),
					jsonData : params,
					success : function(response) {
						windowMask.hide();
						me.hide();
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
		me.editForm = platform.tfrCtrOnDuty.editForm;
		me.items = [me.editForm];
		me.callParent([cfg]);
	}
});

Ext.define('Foss.platform.tfrCtrOnDuty.query4AddForm', {
	extend : 'Ext.form.Panel',
	title : '人员出勤信息新增',
	frame : true,
	collapsible : false,
	animCollapse : true,
	defaults : {
		margin : '5 10 5 10',
		labelWidth : 85,
		columnWidth : .33,
		xtype : 'textfield'
	},
	layout : 'column',
	items : [{
		fieldLabel : '转运场',
		name : 'tfrCtr',
		xtype : 'dynamicorgcombselector',
		types : 'ORG',
		transferCenter : 'Y',
		readOnly : !Ext.isEmpty(platform.tfrCtrOnDuty.tfrCtrCode),
		allowBlank: false
	}, {
		fieldLabel : '部门',
		name : 'org',
		xtype : 'dynamicorgcombselector',
		types : 'ORG',
		allowBlank: false
	},{
		xtype : 'datefield',
		fieldLabel : '上班日期',
		name: 'onDutyDate',
		format: 'Y-m-d',
		allowBlank : false,
		value: new Date()
	}],
	buttons : [ '->', {
		columnWidth : .08,
		xtype : 'button',
		cls : 'yellow_button',
		text : '查询',
		handler : function() {
			var form = this.up('form').getForm();
			if (form.isValid()) {
				platform.tfrCtrOnDuty.result4AddGrid.store.load();
			}
		}
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

Ext.define('Foss.platform.tfrCtrOnDuty.grid4AddStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.platform.tfrCtrOnDuty.gridModel',
	proxy : {
		type : 'ajax',
		actionMethods : 'POST',
		url : platform.realPath('findInfos4Add.action'),
		timeout : 300000,
		reader : {
			type : 'json',
			root : 'tfrCtrOnDutyVo.tfrCtrOnDutys',
			successProperty : 'success'
		}
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	},
	listeners : {
		beforeload : function(store, operation, eOpts) {
			var form = platform.tfrCtrOnDuty.query4AddForm.getForm(), formValues = form.getValues();

			var tfrCtrField = form.findField('tfrCtr'),
			tfrCtrCode = tfrCtrField.getValue(),
			tfrCtrName = tfrCtrField.store.findRecord('code', tfrCtrCode, false, true, true).get('name'),

			orgField = form.findField('org'),
			orgCode = orgField.getValue(),
			orgName = orgField.store.findRecord('code', orgCode, false, true, true).get('name');

			Ext.apply(operation, {
				params : {
					'tfrCtrOnDutyVo.queryParam.tfrCtrCode' : tfrCtrCode,
					'tfrCtrOnDutyVo.queryParam.tfrCtrName' : tfrCtrName,
					'tfrCtrOnDutyVo.queryParam.orgCode' : orgCode,
					'tfrCtrOnDutyVo.queryParam.orgName' : orgName,
					'tfrCtrOnDutyVo.queryParam.onDutyDate' : formValues.onDutyDate
				}
			});
		}
	}
});

Ext.define('Foss.platform.tfrCtrOnDuty.result4AddGrid', {
	extend : 'Ext.grid.Panel',
	frame : true,
	columnLines : false,
	title : '信息录入',
	height : 600,
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	emptyText : '查询结果为空',
	sortableColumns : true,
	enableColumnHide : false,
	autoScroll : false,
	collapsible : false,
	animCollapse : false,
	specialWindow : null,
	getSpecialWindow : function() {
		var me = this;
		if (Ext.isEmpty(me.specialWindow)) {
			me.specialWindow = Ext.create('Foss.platform.tfrCtrOnDuty.specialWindow');
		}
		return me.specialWindow;
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.platform.tfrCtrOnDuty.grid4AddStore');
		me.selModel = Ext.create('Ext.selection.CheckboxModel', {
			mode : 'SIMPLE',
			checkOnly : true
		});
		me.plugins = [Ext.create('Ext.grid.plugin.CellEditing', {
			clicksToEdit : 1
		})];
		me.tbar = [{
			xtype : 'button',
			text : '特殊人员添加',
			handler : function() {
				me.getSpecialWindow().show();
			}
		}, '->', {
			xtype : 'button',
			text : '保存',
			handler : function() {
				var grid = this.up('grid'), 
				modelArray = grid.getSelectionModel().getSelection();
				if (Ext.isEmpty(modelArray)) {
					Ext.ux.Toast.msg('提示', '请选择要保存的记录!', 'error', 2000);
					return;
				}

				var infos = [];
				for (var index in modelArray) {
					infos.push(modelArray[index].data);

					// 322610	2016-4-16 
					// 上班结束日期-上班开始日期只能等于0或者1
					var beginDatePartVal = modelArray[index].data.beginDatePart,
					endDatePartVal = modelArray[index].data.endDatePart;
					// diff
					if((endDatePartVal - beginDatePartVal) > (24 * 60 * 60 * 1000)){
						// 提示不能保存
						Ext.ux.Toast.msg('提示', '上班结束日期与上班开始日期间隔应小于一天', 'error', 2000);
						return;
					}
				}

				var params = {
						'tfrCtrOnDutyVo' : {
							'tfrCtrOnDutys' : infos
						}
				};

				var windowMask = new Ext.LoadMask(me, {
					msg : "保存中，请稍后..."
				});
				windowMask.show();

				Ext.Ajax.request({
					url : platform.realPath('insertTfrCtrOnDuty.action'),
					jsonData : params,
					success : function(response) {
						windowMask.hide();
						Ext.ux.Toast.msg('提示', '保存成功！', 'info', 1500);

						platform.tfrCtrOnDuty.result4AddGrid.store.load();
					},
					exception : function(response) {
						var result = Ext.decode(response.responseText);
						windowMask.hide();
						Ext.ux.Toast.msg('提示', '保存失败，' + result.message, 'error', 2000);
					}
				});
			}
		}];
		me.columns = [{
			dataIndex : 'orgName',
			align : 'center',
			width : 180,
			text : '部门'
		},{
			dataIndex : 'empName',
			align : 'center',
			width : 85,
			text : '姓名'
		},{
			dataIndex : 'empCode',
			align : 'center',
			width : 85,
			text : '工号'
		},{
			dataIndex : 'postName',
			align : 'center',
			width : 120,
			text : '岗位'
		},{
			header : '上班开始时间', 
			columns: [{
				dataIndex : 'beginDatePart',
				text : '日期',
				align : 'center',
				xtype : 'datecolumn',
				format : 'Y-m-d'
			},{
				header:'时间',
				align : 'center',
				dataIndex: 'beginTimePart',
				xtype : 'datecolumn',
				format : 'H:i:s',
				editor : {
					xtype : 'timefield',
					allowBlank : false,
					format : 'H:i:s'
				}
			}]
		},{
			header : '上班结束时间', 
			columns: [{
				header:'日期',
				align : 'center',
				dataIndex: 'endDatePart',
				xtype : 'datecolumn',
				format : 'Y-m-d',
				editor : {
					xtype : 'datefield',
					allowBlank : false,
					format : 'Y-m-d'
				}
			},{
				header:'时间',
				align : 'center',
				dataIndex: 'endTimePart',
				xtype : 'datecolumn',
				format : 'H:i:s',
				editor : {
					xtype : 'timefield',
					allowBlank : false,
					format : 'H:i:s'
				}
			}]
		}];
		me.callParent([cfg]);
		platform.tfrCtrOnDuty.result4AddGrid = me;
	}
});

Ext.define('Foss.platform.tfrCtrOnDuty.addWindow', {
	extend : 'Ext.window.Window',
	closable : true,
	modal : true,
	resizable : false,
	closeAction : 'hide',
	width : 980,
	height : 850,
	query4AddForm : null,
	result4AddGrid : null,
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	resetData : function() {
		var me = this, form = me.query4AddForm.getForm();
		form.reset();
	},
	initData : function() {
		var me = this;
		if (platform.tfrCtrOnDuty.tfrCtrCode) {
			var form = me.query4AddForm.getForm();
			form.findField('tfrCtr').setCombValue(platform.tfrCtrOnDuty.tfrCtrName,platform.tfrCtrOnDuty.tfrCtrCode);
			form.findField('org').setCombValue(FossUserContext.getCurrentDept().name,FossUserContext.getCurrentDept().code);
			form.findField('onDutyDate').setValue(new Date());
		}
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
		me.query4AddForm = platform.tfrCtrOnDuty.query4AddForm
		me.result4AddGrid = platform.tfrCtrOnDuty.result4AddGrid;
		me.items = [me.query4AddForm, me.result4AddGrid];
		me.callParent([cfg]);
	}
});

Ext.define('Foss.platform.tfrCtrOnDuty.specialForm', {
	extend : 'Ext.form.Panel',
	title : '特殊人员添加',
	frame : true,
	collapsible : true,
	animCollapse : true,
	defaults : {
		margin : '5 10 5 10',
		labelWidth : 85,
		columnWidth : .33
	},
	layout : 'column',
	items : [{
		fieldLabel : '转运场',
		name : 'tfrCtr',
		xtype : 'dynamicorgcombselector',
		types : 'ORG',
		transferCenter : 'Y',
		readOnly : !Ext.isEmpty(platform.tfrCtrOnDuty.tfrCtrCode),
		allowBlank: false
	}, {
		fieldLabel : '部门',
		name : 'org',
		xtype : 'dynamicorgcombselector',
		types : 'ORG',
		allowBlank: false
	},{
		xtype : 'textfield',
		fieldLabel : '姓名',
		name : 'empName',
		allowBlank : false
	},{

		fieldLabel : '岗位',
		name : 'post',
		xtype : 'combobox',
		queryMode : 'local',
		displayField : 'value',
		valueField : 'value',
		editable : false,
		allowBlank : false,
		store : Ext.create('Ext.data.Store', {
			fields : ['key', 'value'],
			data : [{
				"key" : "1", "value" : "理货员"
			}, {
				"key" : "2", "value" : "运营外场组长"
			}, {
				"key" : "3", "value" : "电叉司机"
			}, {
				"key" : "4", "value" : "电叉司机组长"
			}, {
				"key" : "5", "value" : "机叉司机"
			}, {
				"key" : "6", "value" : "机叉司机组长"
			}, {
				"key" : "7", "value" : "货区管理员"
			}, {
				"key" : "8", "value" : "货台管理员"
			}, {
				"key" : "9", "value" : "统计分析员"
			}, {
				"key" : "10", "value" : "外场调度"
			}, {
				"key" : "11", "value" : "其他"
			}]
		})
	},{
		xtype : 'rangeDateField',
		fieldLabel : '上班时间',
		columnWidth : 2 / 3,
		fieldId : 'Foss_platform_tfrCtrOnDuty_specialForm_beginTime_id',
		dateType : 'datetimefield_date97',
		fromName : 'beginTime',
		toName : 'endTime',
		allowBlank : false
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

Ext.define('Foss.platform.tfrCtrOnDuty.specialWindow', {
	extend : 'Ext.window.Window',
	closable : true,
	modal : true,
	resizable : false,
	closeAction : 'hide',
	width : 900,
	height : 260,
	specialForm : null,
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	resetData : function() {
		var me = this, form = me.specialForm.getForm();
		form.reset();
	},
	initData : function() {
		var me = this, form = me.specialForm.getForm();
		if (platform.tfrCtrOnDuty.tfrCtrCode) {
			form.findField('tfrCtr').setCombValue(platform.tfrCtrOnDuty.tfrCtrName,platform.tfrCtrOnDuty.tfrCtrCode);
			form.findField('org').setCombValue(FossUserContext.getCurrentDept().name,FossUserContext.getCurrentDept().code);
		}
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
		}, {
			text : '重置',
			handler : function() {
				me.resetData();
				me.initData();
			}
		}, {
			text : '保存',
			cls : 'yellow_button',
			handler : function() {

				var windowMask = new Ext.LoadMask(me, {
					msg : "保存中，请稍后..."
				});
				windowMask.show();

				var form = me.specialForm.getForm(), formValues = form.getValues();

				var tfrCtrField = form.findField('tfrCtr'),
				tfrCtrCode = tfrCtrField.getValue(),
				tfrCtrName = tfrCtrField.store.findRecord('code', tfrCtrCode, false, true, true).get('name'),

				orgField = form.findField('org'),
				orgCode = orgField.getValue(),
				orgName = orgField.store.findRecord('code', orgCode, false, true, true).get('name');

				var params = {
						'tfrCtrOnDutyVo' : {
							'tfrCtrOnDuty' : formValues
						}
				};

				params.tfrCtrOnDutyVo.tfrCtrOnDuty.tfrCtrCode = tfrCtrCode;
				params.tfrCtrOnDutyVo.tfrCtrOnDuty.tfrCtrName = tfrCtrName;
				params.tfrCtrOnDutyVo.tfrCtrOnDuty.orgCode = orgCode;
				params.tfrCtrOnDutyVo.tfrCtrOnDuty.orgName = orgName;
				params.tfrCtrOnDutyVo.tfrCtrOnDuty.empName = formValues.empName;
				params.tfrCtrOnDutyVo.tfrCtrOnDuty.postName = formValues.post;
				params.tfrCtrOnDutyVo.tfrCtrOnDuty.beginTime = formValues.beginTime;
				params.tfrCtrOnDutyVo.tfrCtrOnDuty.endTime = formValues.endTime;

				Ext.Ajax.request({
					url : platform.realPath('insertSpecial.action'),
					jsonData : params,
					success : function(response) {
						windowMask.hide();
						Ext.ux.Toast.msg('提示', '保存成功！', 'info', 1500);
						me.resetData();
						me.initData();
					},
					exception : function(response) {
						var result = Ext.decode(response.responseText);
						windowMask.hide();
						Ext.ux.Toast.msg('提示', '保存失败，' + result.message, 'error', 2000);
					}
				});
			}
		}];
		me.specialForm = platform.tfrCtrOnDuty.specialForm;
		me.items = [me.specialForm];
		me.callParent([cfg]);
	}
});

Ext.onReady(function() {
	var queryForm = Ext.create('Foss.platform.tfrCtrOnDuty.queryForm');
	var resultResult = Ext.create('Foss.platform.tfrCtrOnDuty.resultGrid');

	platform.tfrCtrOnDuty.editForm = Ext.create('Foss.platform.tfrCtrOnDuty.editForm');

	platform.tfrCtrOnDuty.specialForm = Ext.create('Foss.platform.tfrCtrOnDuty.specialForm');
	platform.tfrCtrOnDuty.query4AddForm = Ext.create('Foss.platform.tfrCtrOnDuty.query4AddForm');
	platform.tfrCtrOnDuty.result4AddGrid = Ext.create('Foss.platform.tfrCtrOnDuty.result4AddGrid');

	if(platform.tfrCtrOnDuty.tfrCtrCode){
		var form = queryForm.getForm(),
		tfrCtrField = form.findField('tfrCtr'),
		orgField = form.findField('org');
		tfrCtrField.setCombValue(platform.tfrCtrOnDuty.tfrCtrName,platform.tfrCtrOnDuty.tfrCtrCode);
		orgField.setCombValue(FossUserContext.getCurrentDept().name,FossUserContext.getCurrentDept().code);
	}

	Ext.create('Ext.panel.Panel', {
		id : 'T_platform-tfrCtrOnDutyIndex_content',
		cls : "panelContentNToolbar",
		bodyCls : 'panelContent-body',
		layout : 'auto',
		items : [queryForm,resultResult],
		renderTo : 'T_platform-tfrCtrOnDutyIndex-body'
	});
});