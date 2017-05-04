/**
 *	任务管理
 *	2013/03/22 15:10
 */
baseinfo.jobGrid.formatDefaultDate = function(isBegin) {
	var nowDate = new Date();
	if(isBegin) {
		nowDate.setHours(0);
		nowDate.setSeconds(0);
		nowDate.setMinutes(0);
	} else {
		nowDate.setHours(23);
		nowDate.setSeconds(59);
		nowDate.setMinutes(59);
	}
	return nowDate;
};
baseinfo.jobGrid.regs = {
	quartz: /^([\d*/\-,?]+\s){5}([\d*\-?#A-Z]+)$|^([\d*/\-,?A-Z]+\s){6}([\d*/\-]+)$/
};
//查询表单
Ext.define('Foss.baseinfo.JobGridQueryForm', {
	extend: 'Ext.form.Panel',
	alias: 'widget.jobgridqueryform',
	frame: true,
	title: '查询条件',
	autoScroll: true,
	collapsible: true,
	defaults: {
		lableWidth: 80,
		width: 320
	},
	defaultType: 'textfield',
	layout: {
	    type: 'vbox',
	    align: 'center'
	},
	initComponent: function() {
		var me = this;
		me.items = [{
			fieldLabel: '任务分组',
			name: 'jobGroup'
		}, {
			fieldLabel: '任务名称',
			name: 'jobName'
		}, {
			fieldLabel: '触发器名称',
			name: 'triggerName',
		}, {
			xtype: 'container',
			layout: 'column',
			items: [{
				xtype: 'button',
				text: '重置',
				cls: 'yellow_button',
				handler: me.onReset,
				scope: me,
				width: 75
			}, {
				xtype: 'container',
				columnWidth: 0.99,
				height: 30
			}, {
				xtype: 'button',
				text: '查询',
				cls: 'yellow_button',
				plugins: {
					ptype: 'buttonlimitingplugin',
					seconds: 4
				},
				handler: me.onQuery,
				scope: me,
				width: 75
			}]
		}];
		me.callParent();
	},
	onQuery: function() {
		if(this.getForm().isValid()) {
			var grid = Ext.getCmp('T_baseinfo-jobGridIndex_content').getJobGridList();
			grid.store.loadPage(1);
		} else {
			Ext.Msg.alert('请检查查询条件正确性！');
		}
	},
	onReset: function() {
		this.getForm().reset();
	}
});

//新增编辑
Ext.define('Foss.baseinfo.JobGridForm', {
	extend: 'Ext.form.Panel',
	alias: 'widget.jobgridform',
	defaultType: 'fieldset',
	initComponent: function() {
		var me = this;
		me.items = [{
			title: '触发器信息',
			defaultType: 'textfield',
			layout: {
				type: 'table',
				columns: 2
			},
			defaults: {
				labelWidth: 80,
				width: 300,
				allowBlank: false
			},
			items:[{
				fieldLabel: '触发器名称',
				name: 'triggerName'
			}, {
				fieldLabel: '触发器分组',
				name: 'triggerGroup'
			}, {
				fieldLabel: '触发器类型',
				name: 'triggerType',
				xtype: 'combobox',
				store: {
					xtype: 'store',
					fields: ['key', 'value'],
				    data: [{'key': '1', 'value': '表达式'}]
				},
				queryMode: 'local',
			    editable: false,
			    displayField: 'value',
			    valueField: 'key'
			}, {
				fieldLabel: '表达式',
				regex: baseinfo.jobGrid.regs.quartz,
				regexText: '触发器表达式格式有误',
				name: 'triggerExpression'
			}]
		},{
			title: '任务信息',
			defaultType: 'textfield',
			layout: {
				type: 'table',
				columns: 2
			},
			defaults: {
				labelWidth: 80,
				allowBlank: false,
				width: 300
			},
			items: [{
				fieldLabel: '任务名称',
				name: 'jobName'
			}, {
				fieldLabel: '任务分组',
				name: 'jobGroup'
			}, {
				fieldLabel: '任务类',
				width: 604,
				colspan: '2',
				name: 'jobClass'
			}, {
				fieldLabel: '任务参数',
				xtype: 'textarea',
				allowBlank: true,
				name: 'jobData'
			}, {
				fieldLabel: '任务描述',
				xtype: 'textarea',
				allowBlank: true,
				name: 'jobDesc'
			}]
		}];
		me.callParent();
	}
});

Ext.define('Foss.baseinfo.JobGridWin', {
	extend: 'Ext.window.Window',
	alias: 'wigdet.jobgridwin',
	title: '新增任务',
	width: 660,
	height: 380,
	closable: true,
	resizable: false,
	closeAction: 'hide',
	modal: true,
	layout: 'fit',
	operationUrl: null,
	jobGridFormMode: null,
	initComponent: function() {
		var me = this;
		me.items = [{
			xtype: 'jobgridform'
		}],
		me.buttons = [{
			text: '取消',
			handler: function() {
				me.close();
			}
		}, {
			text: '保存',
			handler: me.onSaveRecord,
			scope: me
		}]
		me.callParent();
	},
	getJobGridFormModel: function() {
		if (null == this.jobGridFormMode) {
			this.jobGridFormMode = Ext.create("Foss.baseinfo.JobGridModel");
		}
		return this.jobGridFormMode;
	},
	setOperationUrl: function(url) {
		this.operationUrl = url;
	},
	loadJobGridForm: function (record) {
		this.down('form').getForm().loadRecord(record);
		this.jobGridFormMode = record;
	},
	onSaveRecord: function() {
		var me = this,
			form = me.down('form'),
			jobGridForm = form.getForm();
		if (jobGridForm.isValid()) {
			jobGridForm.updateRecord(me.getJobGridFormModel());
			Ext.Ajax.request({
				url: me.operationUrl.toString(),
				jsonData: {
					'jobGridVo': {
						'jobGridSchedulesEntity': me.getJobGridFormModel().data
					}
				},
				success: function(response) {
					Ext.getCmp('T_baseinfo-jobGridIndex').down('grid').store.load();
					Ext.MessageBox.show({
						title: "提示",
						msg: "保存成功！",
						buttons: Ext.Msg.OK,
						callback: function() {
							me.hide();
						},
						icon: Ext.MessageBox.INFO
					});
				}
			});
		}
	}
});
Ext.define('Foss.baseinfo.JobGridModel',{
	extend: 'Ext.data.Model',
	fields: [
		{name: 'id', type: 'string'},
		{name: 'triggerName', type: 'string'},			//触发器名称
		{name: 'triggerGroup', type: 'string'},			//触发器分组
		{name: 'triggerType', type: 'number'},			//触发器类型
		{name: 'triggerExpression', type: 'string'},	//触发器表达式
		{name: 'jobName', type: 'string'},				//任务名称
		{name: 'jobGroup', type: 'string'},				//任务分组
		{name: 'jobClass', type: 'string'},				//任务类
		{name: 'jobData', type: 'string'},				//任务数据
		{name: 'jobDesc', type: 'string'},				//任务描述
		{name: 'active', type: 'number'}				//调度状态
	]
});

Ext.define('Foss.baseinfo.JobGridLogModel',{
	extend: 'Ext.data.Model',
	fields: [
		{name: 'id', type: 'string'},
		{name: 'triggerName', type: 'string'},
		{name: 'triggerGroup', type: 'string'},
		{name: 'jobName', type: 'string'},
		{name: 'jobGroup', type: 'string'},
		{name: 'errorMessage', type: 'string'},
		{name: 'startTime', type: 'string' },
		{name: 'endTime', type: 'string' }
	]
});

Ext.define('Foss.baseinfo.JobGridStore', {
	extend: 'Ext.data.Store',
	model: 'Foss.baseinfo.JobGridModel',
	proxy: {
		type: 'ajax',
		actionMethods: 'post',
		url: baseinfo.realPath('queryJobGridSchedules.action'),
		reader: {
			type: 'json',
			root: 'jobGridVo.jobGridSchedulesEntityList',
			totalProperty: 'totalCount'
		}
	}
});

Ext.define('Foss.baseinfo.JobGridLogStore', {
	extend: 'Ext.data.Store',
	model: 'Foss.baseinfo.JobGridLogModel',
	proxy: {
		type: 'ajax',
		actionMethods: 'post',
		url: baseinfo.realPath('queryJobGridLoggings.action'),
		reader: {
			type: 'json',
			root: 'jobGridVo.jobGridLoggingsEntityList',
			totalProperty: 'totalCount'
		}
	}
});

//任务列表
Ext.define('Foss.baseinfo.JobGridList', {
	extend: 'Ext.grid.Panel',
	alias: 'widget.jobgridlist',
	title: '任务列表',
	frame: true,
	cls: 'autoHeight',
	bodyCls: 'autoHeight',
	closeAction: 'hide',
	enableColumnHide: false,
	sortableColumns: false,
	viewConfig: {
        enableTextSelection: true
    },
	jobGridWin: null,
	jobGridLogWin : null,
	initComponent: function() {
		var me = this;
		me.columns =  [{
				xtype: 'actioncolumn',
				width: 60,
				align: 'center',
				text: '操作',
				items: [{
					iconCls: 'deppon_icons_edit',
	                tooltip: '编辑',
	                handler: me.onRowEditRecord,
	                scope: me
				}, {
					iconCls: 'deppon_icons_showdetail',
	                tooltip: '查看日志',
	                handler: me.onReviewLoggings,
	                scope: me
				}]
	    	},
			{ text: '触发器名称', width: 140, dataIndex: 'triggerName' },
			{ text: '触发器分组', width: 80, dataIndex: 'triggerGroup' },
			{ text: '触发器类型', width: 80, dataIndex: 'triggerType', renderer: me.onRenderType },
			{ text: '表达式', width: 100, dataIndex: 'triggerExpression' },
			{ text: '任务名称', width: 140, dataIndex: 'jobName' },
			{ text: '任务分组', width: 80, dataIndex: 'jobGroup' },
			{ text: '任务类', width: 400, dataIndex: 'jobClass' },
			{ text: '描述', width: 300, dataIndex: 'jobDesc' },
			{ text: '状态', width: 80, dataIndex: 'active', renderer: me.onRenderActive }
		];
		me.store = Ext.create('Foss.baseinfo.JobGridStore', {
			autoLoad: false,
			pageSize: 10,
			listeners: {
				beforeload: function(store, operation, eOpts) {
					var queryForm = Ext.getCmp('T_baseinfo-jobGridIndex_content').down('form').getForm(),
						entity;
					queryForm.updateRecord(queryForm.record);
					entity = queryForm.record.data;
					if(queryForm != null) {
						Ext.apply(operation, {
							params: {
								'jobGridVo.jobGridSchedulesEntity.jobGroup': entity.jobGroup,
								'jobGridVo.jobGridSchedulesEntity.jobName': entity.jobName,
								'jobGridVo.jobGridSchedulesEntity.triggerName': entity.triggerName
							}
						});	
					}
				}
		    }
		});
		me.tbar = [{
			text: '新增',
			handler: me.onAddRecord,
			scope: this
		}, {
			text: '编辑',
			handler: me.onEditRecord,
			scope: this
		}, "<font color=red>双击记录可查看任务日志</font>", "->", {
			text: '启动任务',
			handler: me.onStartTask,
			scope: this
		}, {
			text: '停止任务',
			handler: me.onStopTask,
			scope: this
		}];
		me.bbar = Ext.create('Deppon.StandardPaging', {
			store: me.store
		});
		me.on('itemdblclick', me.onRowDbClick, me);
		me.callParent(arguments);
	},
	onRowDbClick: function(view, record) {
		var me = this,
			jobName = record.get('jobName'),
			jobGroup = record.get('jobGroup'),
			win = me.getJobGridLogWin();
			logGird = win.down('grid');
		win.show();
		logGird.setBaseParams(jobName, jobGroup);
		logGird.store.loadPage(1);
	},
	onReviewLoggings: function(grid, rowIndex, colIndex) {
		var me = this,
			record = me.getStore().getAt(rowIndex),
			jobName = record.get('jobName'),
			jobGroup = record.get('jobGroup'),
			win = me.getJobGridLogWin();
			logGird = win.down('grid');
		win.show();
		logGird.setBaseParams(jobName, jobGroup);
		logGird.store.loadPage(1);
	},
	getOperationUrl: function(actionType) {
		var operationUrl = null;
		if (actionType === 'add') {
			operationUrl = baseinfo.realPath('addJobGridSchedule.action');
		} else if (actionType === 'update') {
			operationUrl = baseinfo.realPath('updateJobGridSchedule.action');
		}
		return operationUrl;
	},
	getJobGridWin: function(title, actionType) {
		var me = this,
			jobGridForm;
		if (!me.jobGridWin) {
			me.jobGridWin = Ext.create('Foss.baseinfo.JobGridWin');
		}
		jobGridForm = me.jobGridWin.down('form').getForm()
		me.jobGridWin.setOperationUrl(me.getOperationUrl(actionType));
		me.jobGridWin.setTitle(title);
		if (actionType) {
			if (actionType === "add") {
				jobGridForm.findField('triggerName').setReadOnly(false);
				jobGridForm.findField('triggerGroup').setReadOnly(false);
				jobGridForm.findField('jobName').setReadOnly(false);
				jobGridForm.findField('jobGroup').setReadOnly(false);
				me.jobGridWin.down('form').getForm().reset();
			} else if (actionType === "update") {
				jobGridForm.findField('triggerName').setReadOnly(true);
				jobGridForm.findField('triggerGroup').setReadOnly(true);
				jobGridForm.findField('jobName').setReadOnly(true);
				jobGridForm.findField('jobGroup').setReadOnly(true);
			}
		}
		return me.jobGridWin;
	},
	getJobGridLogWin: function() {
		if(!this.jobGridLogWin) {
			this.jobGridLogWin = Ext.create('Foss.baseinfo.JobGridLogWin');
		}
		return this.jobGridLogWin;
	},
	onAddRecord: function() {
		this.getJobGridWin("新增任务信息", "add").show();
	},
	onEditRecord: function() {
		var me = this,
			store = me.store,
			sm = me.getSelectionModel(),
			jobGridWin;
		if (sm.hasSelection()) {
			jobGridWin = me.getJobGridWin("编辑任务信息", "update");
			jobGridWin.loadJobGridForm(sm.getSelection()[0]);
			jobGridWin.show();
		} else {
			Ext.Msg.alert("提示", "请先选择一条任务信息。");
		}
	},
	onRowEditRecord: function(grid, rowIndex, colIndex) {
		var me = this,
			record = me.getStore().getAt(rowIndex),
			jobGridWin = me.getJobGridWin("编辑任务信息", "update");
		jobGridWin.loadJobGridForm(record);
		jobGridWin.show();
	},
	onStartTask: function() {
		var me = this,
			sm = me.getSelectionModel(),
			id, active;
		if(sm.hasSelection()) {
			id = sm.getSelection()[0].get('id');
			active = sm.getSelection()[0].get('active');
			if(active == "1") {
				Ext.MessageBox.alert("提示", "当前任务正在执行中...");
				return;
			} else {
				Ext.Ajax.request({
					url: baseinfo.realPath('startupTask.action'),
					jsonData: {
						'jobGridVo': {
							'jobGridSchedulesEntity': {
								'id': id
							}
						}
					},
					success: function(response) {
						me.getStore().load();
						Ext.MessageBox.alert("提示", "启动成功！");
					}
				});
			}
		} else {
			Ext.MessageBox.alert("提示", "请先选择一条任务！");
		}
	},
	onStopTask: function() {
		var me = this,
			sm = me.getSelectionModel(),
			id;
		if(sm.hasSelection()) {
			id = sm.getSelection()[0].get('id');
			active = sm.getSelection()[0].get('active');
			if(active == "0") {
				Ext.MessageBox.alert("提示", "当前任务已经处于停止状态...");
				return;
			} else {
				Ext.Ajax.request({
					url: baseinfo.realPath('stopTask.action'),
					jsonData: {
						'jobGridVo': {
							'jobGridSchedulesEntity': {
								'id': id
							}
						}
					},
					success: function(response) {
						me.getStore().load();
						Ext.MessageBox.alert("提示", "停止成功！");
					}
				});
			}
		} else {
			Ext.MessageBox.alert("提示", "请先选择一条任务！");
		}
	},
	onRenderType: function(value) {
		return value === '1' ? '表达式' : '表达式';
	},
	onRenderActive: function(value) {
		return value == '1' ? '启用中' : '停止中';
	}
});

//任务日志Grid
Ext.define('Foss.baseinfo.JobGridLogPanel', {
	extend: 'Ext.grid.Panel',
	frame: false,
	height: 350,
	enableColumnHide: false,
	sortableColumns: false,
	viewConfig: {
        enableTextSelection: true
    },
    autoScroll: true,
    jobName: null,
    jobGroup: null,
    setBaseParams: function(jobName, jobGroup) {
    	this.jobName = jobName;
    	this.jobGroup = jobGroup;
    },
	initComponent: function() {
		var me = this;
		me.columns =  [
			{ text: '触发时间', width: 160, dataIndex: 'startTime' },
			{ text: '结束时间', width: 160, dataIndex: 'endTime' },
			{ text: '错误信息', flex: 1, dataIndex: 'errorMessage'}
		];
		me.store = Ext.create('Foss.baseinfo.JobGridLogStore', {
			autoLoad: false,
			pageSize: 10,
			listeners: {
				beforeload: function(store, operation, eOpts) {
					var form = me.up('window').down('form'),
						startTime = form.getValues().startTime,
						endTime = form.getValues().endTime;
					if(me.jobName != null && me.jobGroup != null) {
						Ext.apply(operation, {
							params: {
								'jobGridVo.jobGridLoggingsEntity.jobGroup': me.jobGroup,
								'jobGridVo.jobGridLoggingsEntity.jobName': me.jobName,
								'jobGridVo.jobGridLoggingsEntity.startTime': startTime || Ext.Date.format(new Date(), 'Y-m-d H:i:s'),
								'jobGridVo.jobGridLoggingsEntity.endTime': endTime || Ext.Date.format(new Date(), 'Y-m-d H:i:s')
							}
						});
					}
				}
			}
		});
		me.bbar = Ext.create('Deppon.StandardPaging', {
			store: me.store
		});
		me.callParent(arguments);
	}
});

//任务日志查询表单
Ext.define('Foss.baseinfo.JobGridLogQueryFrom', {
	extend: 'Ext.form.Panel',
	initComponent: function() {
		var me = this;
		me.items = [{
    		xtype: 'fieldset',
			title: '查询区间',
			layout: {
			    type: 'vbox',
			    align: 'center'
			},
			defaults: {
				lableWidth: 60,
				width: 480
			},
			items: [{
	    		xtype: 'rangeDateField',
	    		margin: '10 0 10 0',
	    		fieldId: 'jobGird_loggingTime',
	    		dateType: 'datetimefield_date97',
	    		fromName: 'startTime',
	    		toName: 'endTime',
	    		fromValue: Ext.Date.format(baseinfo.jobGrid.formatDefaultDate(true), 'Y-m-d H:i:s'),
	    		toValue: Ext.Date.format(baseinfo.jobGrid.formatDefaultDate(false), 'Y-m-d H:i:s'),
	    		disallowBlank: true
			}, {
				xtype: 'container',
				layout: 'column',
				items: [{
		    		xtype: 'button',
					text: '重置',
					cls: 'yellow_button',
					handler: me.onReset,
					scope: me,
					columnWidth: 0.2
				}, {
					xtype: 'container',
					height: 30,
					columnWidth: 0.6
				}, {
		    		xtype: 'button',
		    		text: '查询',
					cls: 'yellow_button',
					plugins: {
						ptype: 'buttonlimitingplugin',
						seconds: 4
					},
					handler: me.onQuery,
					scope: me,
					columnWidth: 0.2
				}]
			}]
		}];
		me.callParent();
	},
	onQuery: function() {
		if(this.getForm().isValid()) {
			var grid = this.up('window').down('grid');
			grid.store.loadPage(1);
		} else {
			Ext.Msg.alert('提示', '请检查查询条件正确性！');
		}
	},
	onReset: function() {
		this.getForm().reset();
	}
});

//任务日志window
Ext.define('Foss.baseinfo.JobGridLogWin', {
	extend: 'Ext.window.Window',
	title: '任务日志',
	width: 680,
	closable: true,
	resizable: false,
	closeAction: 'hide',
	modal: true,
	layout: 'auto',
	jobGridLogPanel: null,
	jobGridLogQueryFrom: null,
	initComponent: function() {
		var me = this;
		me.items = [me.getJobGridLogQueryFrom(), me.getJobGridLogPanel()];
		me.callParent(arguments);
	},
	getJobGridLogPanel: function() {
		if(this.jobGridLogPanel == null) {
			this.jobGridLogPanel = Ext.create('Foss.baseinfo.JobGridLogPanel');
		}
		return this.jobGridLogPanel;
	},
	getJobGridLogQueryFrom: function() {
		if(this.jobGridLogQueryFrom == null) {
			this.jobGridLogQueryFrom = Ext.create('Foss.baseinfo.JobGridLogQueryFrom');
		}
		return this.jobGridLogQueryFrom;
	}
});

Ext.onReady(function() {
	var queryForm  = Ext.create('Foss.baseinfo.JobGridQueryForm', {
			'record': Ext.create('Foss.baseinfo.JobGridModel')
		}),
		queryGrid  = Ext.create('Foss.baseinfo.JobGridList');
	Ext.getCmp('T_baseinfo-jobGridIndex').add(
		Ext.create('Ext.panel.Panel', {
			id: 'T_baseinfo-jobGridIndex_content',
			cls: 'panelContentNToolbar',
			bodyCls: 'panelContentNToolbar-body',
			getJobGridList: function() {
				return queryGrid;
			},
			items: [ queryForm, queryGrid ]
		})
	);
});
