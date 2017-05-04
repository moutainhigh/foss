/**
 * @author 100847-foss-GaoPeng
 */
/***************************************************  定义baseinfo.leasedDriverWhitelists.i18n('foss.baseinfo.baseinfo-leasedDriverWhitelists.61')列表查询条件窗口 *************************************************/
/*
 * 定义查询条件的表单FORM
 */
Ext.define('Foss.baseinfo.leasedDriverWhitelists.QueryForm', {
	extend : 'Ext.form.Panel',
	title : baseinfo.leasedDriverWhitelists.i18n('foss.baseinfo.queryCondition'),
	id : 'Foss_baseinfo_leasedDriverWhitelists_QueryForm_Id',
	frame : true,
	collapsible : true,
	layout : {
		type : 'table',
		columns : 2
	},
	defaults : {
		labelSeparator : ':',
		margin : '8 10 5 10',
		anchor : '100%'
	},
	height : 170,
	defaultType : 'textfield',
	layout : 'column',
	items : [{
			name : 'driverIdCard',
			fieldLabel : baseinfo.leasedDriverWhitelists.i18n('foss.baseinfo.LDRDriverIdCard'),
			columnWidth : .50,
			maxLength : 50
		}, {
			xtype : 'combobox',
			name : 'currentApplication',
			fieldLabel : baseinfo.leasedDriverWhitelists.i18n('foss.baseinfo.LDRWSCurrentApply'),
			columnWidth : .50,
			displayField : 'valueName',
			valueField : 'valueCode',
			queryMode : 'local',
			triggerAction : 'all',
			editable : false,
			value : 'all',
			store : FossDataDictionary.getDataDictionaryStore('BES_WHITELISTS_APPLY', 'Foss_baseinfo_leasedDriverWhitelists_CurrentApplicationStore_Id', [{
				'valueCode' : 'all',
				'valueName' : baseinfo.leasedDriverWhitelists.i18n('foss.baseinfo.all')
			}, {
				'valueCode' : 'null',
				'valueName' : baseinfo.leasedDriverWhitelists.i18n('foss.baseinfo.LDRWSNo')
			}])
		}, {
			name : 'driverName',
			fieldLabel : baseinfo.leasedDriverWhitelists.i18n('foss.baseinfo.LDRDriverName'),
			columnWidth : .50,
			maxLength : 50
		}, {
			xtype : 'combobox',
			name : 'status',
			fieldLabel : baseinfo.leasedDriverWhitelists.i18n('foss.baseinfo.LDRWSWhitelistStatus'),
			columnWidth : .50,
			displayField : 'valueName',
			valueField : 'valueCode',
			queryMode : 'local',
			triggerAction : 'all',
			editable : false,
			value : '',
			store : FossDataDictionary.getDataDictionaryStore('BES_WHITELISTS_AUDIT', 'Foss_baseinfo_leasedDriverWhitelists_StatusStore_Id', {
				'valueCode' : '',
				'valueName' : baseinfo.leasedDriverWhitelists.i18n('foss.baseinfo.all')
			})
		}, {
			xtype : 'button',
			columnWidth : .08,
			text : baseinfo.leasedDriverWhitelists.i18n('foss.baseinfo.reset'),
			disabled:!baseinfo.leasedDriverWhitelists.isPermission('leasedDriverWhitelists/driverWhitelistsQueryButton'),
			hidden:!baseinfo.leasedDriverWhitelists.isPermission('leasedDriverWhitelists/driverWhitelistsQueryButton'),
			handler : function () {
				Ext.getCmp('Foss_baseinfo_leasedDriverWhitelists_QueryForm_Id').getForm().reset();
			}
		}, {
			border : false,
			xtype : 'container',
			columnWidth : .84,
			html : '&nbsp;'
		}, {
			columnWidth : .08,
			xtype : 'button',
			cls : 'yellow_button',
			text : baseinfo.leasedDriverWhitelists.i18n('foss.baseinfo.query'),
			disabled:!baseinfo.leasedDriverWhitelists.isPermission('leasedDriverWhitelists/driverWhitelistsQueryButton'),
			hidden:!baseinfo.leasedDriverWhitelists.isPermission('leasedDriverWhitelists/driverWhitelistsQueryButton'),
			handler : function () {
				Ext.getCmp('Foss_baseinfo_leasedDriverWhitelists_LeasedDriverWhitelistsGrid_Id').getPagingToolbar().moveFirst();
			}
		}
	],
	constructor : function (config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

/*******************************************************  定义baseinfo.leasedDriverWhitelists.i18n('foss.baseinfo.baseinfo-leasedDriverWhitelists.62')列表结果窗口 *****************************************************/
/*
 * 定义：一个baseinfo.leasedDriverWhitelists.i18n('foss.baseinfo.baseinfo-leasedDriverWhitelists.62')的数据模型"Model"
 */
Ext.define('Foss.baseinfo.leasedDriverWhitelists.LeasedDriverWhitelistsModel', {
	extend : 'Ext.data.Model',
	idProperty : 'extid',
	idgen: 'uuid',
	fields : [{
			name : 'extid'
		}, {
			name : 'id',//ID标识
			type : 'string'
		}, {
			name : 'driverName', //司机姓名
			type : 'string'
		}, {
			name : 'driverPhone', //司机电话
			type : 'string'
		}, {
			name : 'driverIdCard', //司机身份证
			type : 'string'
		}, {
			name : 'qualification', //从业资格证
			type : 'string'
		}, {
			name : 'driverLicense', //驾驶证
			type : 'string'
		}, {
			name : 'status', //白名单状态
			type : 'string'
		}, {
			name : 'currentApplication', //当前申请
			type : 'string'
		}, {
			name : 'applyUser', //申请人
			type : 'string'
		}, {
			name : 'applyTime', //申请时间
			type : 'date',
			convert : function (value) {
				if (Ext.isEmpty(value)) {
					return null;
				}
				return new Date(value);
			}
		}, {
			name : 'approveUser', //审核人
			type : 'string'
		}, {
			name : 'approveTime', //审核时间
			type : 'date',
			convert : function (value) {
				if (Ext.isEmpty(value)) {
					return null;
				}
				return new Date(value);
			}
		}, {
			name : 'applyNotes', //申请备注
			type : 'string'
		}, {
			name : 'auditNotes', //审核备注
			type : 'string'
		}
	]
});

/*
 * 定义：一个baseinfo.leasedDriverWhitelists.i18n('foss.baseinfo.baseinfo-leasedDriverWhitelists.62')的查询数据模型"Store"交互后台
 */
Ext.define('Foss.baseinfo.leasedDriverWhitelists.LeasedDriverWhitelistsStore', {
	extend : 'Ext.data.Store',
	autoLoad : false,
	//页面条数定义
	pageSize : 10,
	//绑定model
	model : 'Foss.baseinfo.leasedDriverWhitelists.LeasedDriverWhitelistsModel',
	proxy : {
		//以JSON的方式加载数据
		type : 'ajax',
		actionMethods : 'POST',
		url : baseinfo.realPath('queryLeasedDriverWhitelistsList.action'),
		reader : {
			type : 'json',
			root : 'leasedWhitelistVo.whitelistAuditList',
			totalProperty : 'totalCount',
			successProperty : 'success'
		}
	},
	//构造函数
	constructor : function (config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	},
	//监听器
	listeners : {
		beforeload : function (store, operation, eOpts) {
			var queryForm = Ext.getCmp('Foss_baseinfo_leasedDriverWhitelists_QueryForm_Id').getForm();
			if (queryForm != null) {
				var queryParams = queryForm.getValues();
				Ext.apply(operation, {
					params : {
						'leasedWhitelistVo.whitelistAudit.driverIdCard' : queryParams.driverIdCard,
						'leasedWhitelistVo.whitelistAudit.currentApplication' : queryParams.currentApplication,
						'leasedWhitelistVo.whitelistAudit.driverName' : queryParams.driverName,
						'leasedWhitelistVo.whitelistAudit.status' : queryParams.status
					}
				});
			}
		}
	}
});

/*
 * 定义：baseinfo.leasedDriverWhitelists.i18n('foss.baseinfo.baseinfo-leasedDriverWhitelists.62')内嵌在结果列表中的查询详细的窗口表单
 */
Ext.define('Foss.baseinfo.leasedDriverWhitelists.LeasedDriverWhitelistsDetailForm', {
	extend : 'Ext.form.Panel',
	height : 230,
	defaults : {
		margin : '5 25 5 25',
		labelWidth : 100
	},
	defaultType : 'textfield',
	layout : {
		type : 'table',
		columns : 2
	},
	items : [{
			name : 'driverIdCard',
			fieldLabel : baseinfo.leasedDriverWhitelists.i18n('foss.baseinfo.LDRDeiverIdNumber'),
			readOnly : true,
			columnWidth : 0.5
		}, {
			name : 'driverName',
			fieldLabel : baseinfo.leasedDriverWhitelists.i18n('foss.baseinfo.LDRDriverName'),
			readOnly : true,
			columnWidth : 0.5
		}, {
			name : 'driverPhone',
			fieldLabel : baseinfo.leasedDriverWhitelists.i18n('foss.baseinfo.LDRDriverTelphone'),
			readOnly : true,
			columnWidth : 0.5
		}, {
			name : 'qualification',
			fieldLabel : baseinfo.leasedDriverWhitelists.i18n('foss.baseinfo.leasedDriverQualification'),
			readOnly : true,
			columnWidth : 0.5
		}, {
			name : 'driverLicense',
			fieldLabel : baseinfo.leasedDriverWhitelists.i18n('foss.baseinfo.LDRWSDriverLicense'),
			readOnly : true,
			columnWidth : 0.5
		}, {
			xtype : 'label'
		}, {
			xtype : 'combobox',
			name : 'status',
			fieldLabel : baseinfo.leasedDriverWhitelists.i18n('foss.baseinfo.LDRWSWhitelistStatus'),
			readOnly : true,
			columnWidth : 0.5,
			displayField : 'valueName',
			valueField : 'valueCode',
			queryMode : 'local',
			triggerAction : 'all',
			editable : false,
			store : FossDataDictionary.getDataDictionaryStore('BES_WHITELISTS_AUDIT', 'Foss_baseinfo_leasedDriverWhitelists_StatusStore_Id', {
				'valueCode' : '',
				'valueName' : '-'
			}),
			style : {
				fontWeight : 'bold'
			}
		}, {
			xtype : 'combobox',
			name : 'currentApplication',
			readOnly : true,
			fieldLabel : baseinfo.leasedDriverWhitelists.i18n('foss.baseinfo.LDRWSCurrentApply'),
			columnWidth : 0.5,
			displayField : 'valueName',
			valueField : 'valueCode',
			queryMode : 'local',
			triggerAction : 'all',
			editable : false,
			store : FossDataDictionary.getDataDictionaryStore('BES_WHITELISTS_APPLY', 'Foss_baseinfo_leasedDriverWhitelists_CurrentApplicationStore_Id', {
				'valueCode' : '',
				'valueName' : '-'
			}),
			style : {
				fontWeight : 'bold'
			}
		}, {
			name : 'applyUser',
			fieldLabel : baseinfo.leasedDriverWhitelists.i18n('foss.baseinfo.LDRWSApplicantPerson'),
			readOnly : true,
			columnWidth : 0.5
		}, {
			xtype : 'datefield',
			name : 'applyTime',
			fieldLabel : baseinfo.leasedDriverWhitelists.i18n('foss.baseinfo.LDRWSApplyTime'),
			readOnly : true,
			columnWidth : 0.5,
			width : 450,
			format : 'Y-m-d H:i:s'
		}, {
			name : 'approveUser',
			fieldLabel : baseinfo.leasedDriverWhitelists.i18n('foss.baseinfo.LDRWSReviewer'),
			readOnly : true,
			columnWidth : 0.5
		}, {
			xtype : 'datefield',
			name : 'approveTime',
			fieldLabel : baseinfo.leasedDriverWhitelists.i18n('foss.baseinfo.LDRWSAuditTime'),
			readOnly : true,
			columnWidth : 0.5,
			width : 450,
			format : 'Y-m-d H:i:s'
		}, {
			name : 'auditNotes',
			fieldLabel : baseinfo.leasedDriverWhitelists.i18n('foss.baseinfo.LDRWSAuditRemarks'),
			readOnly : true,
			columnWidth : 0.5,
			colspan : 2,
			width : 550
		}
	]
});

/*
 * 定义：内嵌在结果列表中的查询详细的窗口
 */
Ext.define('Foss.baseinfo.leasedDriverWhitelists.LeasedDriverWhitelistsDetailPanel', {
	extend : 'Ext.panel.Panel',
	title : baseinfo.leasedDriverWhitelists.i18n('foss.baseinfo.LDRWSDriverWhitelistDetails'),
	frame : true,
	informationForm : null,
	/**
	 * 获取外请司机白名单详细信息
	 * @return {Object} LeasedDriverWhitelistsDetailForm
	 */
	getInformationForm : function () {
		if (this.informationForm == null) {
			this.informationForm = Ext.create('Foss.baseinfo.leasedDriverWhitelists.LeasedDriverWhitelistsDetailForm');
		}
		return this.informationForm;
	},
	constructor : function (config) {
		Ext.apply(this, config);
		this.items = [this.getInformationForm()];
		this.callParent(arguments);
	},
	bindData : function (record) {
		var tempForm = this.getInformationForm().getForm();
		//业务规则
		if(!Ext.isEmpty(record)){
			var currentApplication = record.get('currentApplication');
			if(!Ext.isEmpty(currentApplication)){
				tempForm.findField('approveUser').hide();
				tempForm.findField('approveTime').hide();
			}else{
				tempForm.findField('approveUser').show();
				tempForm.findField('approveTime').show();
			}
			currentApplication = null;
		}
		//绑定表格数据到表单上
		tempForm.loadRecord(record);
		tempForm = null;
	}
});

/*
 * 查询结果列表窗口
 */
Ext.define('Foss.baseinfo.leasedDriverWhitelists.LeasedDriverWhitelistsGrid', {
	extend : 'Ext.grid.Panel',
	columnLines : true, // 增加表格列的分割线
	id : 'Foss_baseinfo_leasedDriverWhitelists_LeasedDriverWhitelistsGrid_Id',
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	frame : true, // 表格对象增加一个边框
	stripeRows : true,
	columnLines : true, // 增加表格列的分割线
	title : baseinfo.leasedDriverWhitelists.i18n('foss.baseinfo.LDRWSApllyDriverInformation'), // 定义表格的标题
	collapsible : true,
	animCollapse : true,
	store : null,
	//表格行可展开的插件
	plugins : [{
			ptype : 'rowexpander',
			rowsExpander : false, // 定义行展开模式（单行与多行），默认是多行展开(值true)
			rowBodyElement : 'Foss.baseinfo.leasedDriverWhitelists.LeasedDriverWhitelistsDetailPanel' // 行体内容
		}
	],
	applyWindow : null,
	/**
	 * 获取baseinfo.leasedDriverWhitelists.i18n('foss.baseinfo.baseinfo-leasedDriverWhitelists.63')的窗口的对象
	 * @param {String} availableTitle 窗口的标题
	 * @param {Boolean} isNewApply true：申请入库，false：申请可用/不可用
	 * @param {String} actionType apply/available/unavailable
	 * @return {Ext.Component} 申请窗口的对象
	 */
	getApplyWindow : function (availableTitle, isNewApply, actionType) {
		var me = this;
		if (this.applyWindow == null) {
			this.applyWindow = Ext.create('Foss.baseinfo.leasedDriverWhitelists.LeasedDriverWhitelistsApplyWindow');
		}
		this.applyWindow.setOperationUrl((function () {
				var operationUrl = null;
				if (actionType === 'apply') {
					operationUrl = baseinfo.realPath('applyLeasedDriverWhitelists.action');
				} else if (actionType === 'available') {
					operationUrl = baseinfo.realPath('availableLeasedDriverWhitelists.action');
				} else if (actionType === 'unavailable') {
					operationUrl = baseinfo.realPath('unAvailableLeasedDriverWhitelists.action');
				} else {}
				return operationUrl;
			})(actionType));
		this.applyWindow.getApplyForm().setTitle(availableTitle);
		if (Ext.isBoolean(isNewApply) && isNewApply) {
			this.applyWindow.getApplyForm().getForm().reset();
			this.applyWindow.getApplyForm().getForm().findField('driverIdCard').setReadOnly(!isNewApply);
			this.applyWindow.getApplyForm().getForm().findField('status').hide().setDisabled(isNewApply);
			this.applyWindow.getApplyForm().getForm().findField('currentApplication').hide().setDisabled(isNewApply);
			this.applyWindow.getButtons(1).setVisible(isNewApply); //重置按钮可用
			this.applyWindow.getButtons(0).setPosition(0);
			this.applyWindow.getButtons(2).setPosition(0);
		} else {
			this.applyWindow.getApplyForm().getForm().findField('driverIdCard').setReadOnly(!isNewApply);
			this.applyWindow.getApplyForm().getForm().findField('status').show().setDisabled(isNewApply);
			this.applyWindow.getApplyForm().getForm().findField('currentApplication').show().setDisabled(isNewApply);
			this.applyWindow.getButtons(1).setVisible(isNewApply); //重置按钮不可用
			this.applyWindow.getButtons(0).setPosition(50);
			this.applyWindow.getButtons(2).setPosition(50);
		}
		return this.applyWindow;
	},
	//列表分页组件对象
	pagingToolbar : null,
	getPagingToolbar : function () {
		var me = this;
		if (Ext.isEmpty(me.pagingToolbar)) {
			me.pagingToolbar = Ext.create('Deppon.StandardPaging', {
					store : me.store,
					pageSize : 10,
					prependButtons : true,
					defaults : {
						margin : '0 0 15 3'
					}
				});
		}
		return me.pagingToolbar;
	},
	// 定义表格列信息
	columns : [{
			xtype : 'actioncolumn',
			width : 60,
			text : baseinfo.leasedDriverWhitelists.i18n('foss.baseinfo.operate'),
			align : 'center',
			items : [{
					iconCls : 'foss_icons_bse_applyReturn',
					tooltip : baseinfo.leasedDriverWhitelists.i18n('foss.baseinfo.LDRWSWithDraw'),//撤回
					disabled:!baseinfo.leasedDriverWhitelists.isPermission('leasedDriverWhitelists/driverWhitelistsCancelButton'),
					getClass : function (v, m, r, rowIndex) {
						if (Ext.isEmpty(r.get('currentApplication'))) {
							return 'statementBill_hide';
						} else {
							return 'foss_icons_bse_applyReturn';
						}
					},
					/**
					 * baseinfo.leasedDriverWhitelists.i18n('foss.baseinfo.LDRWSWithDraw')响应事件
					 * @param {Object} grid 当前表格
					 * @param {Number} rowIndex 行索引
					 * @param {Number} colIndex 列索引
					 */
					handler : function (grid, rowIndex, colIndex) {
						Ext.Msg.show({
							title : baseinfo.leasedDriverWhitelists.i18n('foss.baseinfo.confirmationPrompt'),
							msg : baseinfo.leasedDriverWhitelists.i18n('foss.baseinfo.LDRWSWithDrawMessage'),
							buttons : Ext.Msg.YESNO,
							icon : Ext.MessageBox.QUESTION,
							fn : function (btn) {
								if (btn == 'yes') {
									//获取结果表格对象
									var record = grid.getStore().getAt(rowIndex);
									Ext.Ajax.request({
										url : baseinfo.realPath('withdrawLeasedDriverWhitelists.action'),
										jsonData : {
											'leasedWhitelistVo' : {
												'whitelistAudit' : record.data
											}
										},
										//baseinfo.leasedDriverWhitelists.i18n('foss.baseinfo.LDRWSWithDraw')成功
										success : function (response) {
											grid.up().getPagingToolbar().moveFirst();
											var json = Ext.decode(response.responseText);
											Ext.MessageBox.show({
												title : baseinfo.leasedDriverWhitelists.i18n('foss.baseinfo.successPrompt'),
												msg : json.message,
												width : 300,
												buttons : Ext.Msg.OK,
												icon : Ext.MessageBox.INFO
											});
										},
										//baseinfo.leasedDriverWhitelists.i18n('foss.baseinfo.LDRWSWithDraw')失败
										exception : function (response) {
											var json = Ext.decode(response.responseText);
											Ext.MessageBox.show({
												title : baseinfo.leasedDriverWhitelists.i18n('foss.baseinfo.failureInformationTips'),
												msg : json.message,
												width : 450,
												buttons : Ext.Msg.OK,
												icon : Ext.MessageBox.WARNING
											});
										}
									});
								}
							}
						});
					}
				}, {
					iconCls : 'foss_icons_bse_applyUsable',
					tooltip : baseinfo.leasedDriverWhitelists.i18n('foss.baseinfo.LDRWSApplicationAvailable'),//申请可用
					disabled:!baseinfo.leasedDriverWhitelists.isPermission('leasedDriverWhitelists/driverWhitelistsAvailableButton'),
					getClass : function (v, m, r, rowIndex) {
						if (Ext.isEmpty(r.get('currentApplication')) && r.get('status') === 'audit_unavailable') {
							return 'foss_icons_bse_applyUsable';
						} else {
							return 'statementBill_hide';
						}
					},
					// baseinfo.leasedDriverWhitelists.i18n('foss.baseinfo.LDRWSApplicationAvailable')响应事件
					handler : function (grid, rowIndex, colIndex, item) {
						var record = grid.getStore().getAt(rowIndex);
						var model = new Foss.baseinfo.leasedDriverWhitelists.LeasedDriverWhitelistsModel(record.data);
						model.set('applyNotes', '');
						model.set('currentApplication', FossDataDictionary.rendererSubmitToDisplay('apply_available', 'BES_WHITELISTS_APPLY'));
						var applyWindow = grid.up().getApplyWindow(item.tooltip, false, 'available');
						applyWindow.getApplyForm().getForm().loadRecord(model);
						applyWindow.show();
						model = null;
					}
				}, {
					iconCls : 'foss_icons_bse_applyDisabled',
					tooltip : baseinfo.leasedDriverWhitelists.i18n('foss.baseinfo.LDRWSApplicationUnAvailable'),//申请不可用
					disabled:!baseinfo.leasedDriverWhitelists.isPermission('leasedDriverWhitelists/driverWhitelistsUnavailableButton'),
					getClass : function (v, m, r, rowIndex) {
						if (Ext.isEmpty(r.get('currentApplication')) && r.get('status') === 'audit_available') {
							return 'foss_icons_bse_applyDisabled';
						} else {
							return 'statementBill_hide';
						}
					},
					// baseinfo.leasedDriverWhitelists.i18n('foss.baseinfo.LDRWSApplicationUnAvailable')响应事件
					handler : function (grid, rowIndex, colIndex, item) {
						var record = grid.getStore().getAt(rowIndex);
						var model = new Foss.baseinfo.leasedDriverWhitelists.LeasedDriverWhitelistsModel(record.data);
						model.set('applyNotes', '');
						model.set('currentApplication', FossDataDictionary.rendererSubmitToDisplay('apply_unavailable', 'BES_WHITELISTS_APPLY'));
						var applyWindow = grid.up().getApplyWindow(item.tooltip, false, 'unavailable');
						applyWindow.getApplyForm().getForm().loadRecord(model);
						applyWindow.show();
						model = null;
					}
				}
			]
		}, {
			header : baseinfo.leasedDriverWhitelists.i18n('foss.baseinfo.LDRDeiverIdNumber'),
			dataIndex : 'driverIdCard',
			width : 150
		}, {
			header : baseinfo.leasedDriverWhitelists.i18n('foss.baseinfo.LDRDriverName'),
			dataIndex : 'driverName',
			flex : 1,
			width : 80
		}, {
			header : baseinfo.leasedDriverWhitelists.i18n('foss.baseinfo.LDRWSWhitelistStatus'),
			dataIndex : 'status',
			width : 105,
			renderer : function (value) {
				var val = FossDataDictionary.rendererSubmitToDisplay(value, 'BES_WHITELISTS_AUDIT');
				if (Ext.isEmpty(val)) {
					return '-';
				} else {
					return val;
				}
			}
		}, {
			header : baseinfo.leasedDriverWhitelists.i18n('foss.baseinfo.LDRWSCurrentApply'),
			dataIndex : 'currentApplication',
			width : 105,
			renderer : function (value) {
				var val = FossDataDictionary.rendererSubmitToDisplay(value, 'BES_WHITELISTS_APPLY');
				if (Ext.isEmpty(val)) {
					return '-';
				} else {
					return val;
				}
			}
		}, {
			header : baseinfo.leasedDriverWhitelists.i18n('foss.baseinfo.LDRWSApplicationRemarks'),
			dataIndex : 'applyNotes',
			width : 240
		}, {
			header : baseinfo.leasedDriverWhitelists.i18n('foss.baseinfo.LDRWSApplicantPerson'),
			dataIndex : 'applyUser',
			flex : 1,
			width : 90
		}, {
			header : baseinfo.leasedDriverWhitelists.i18n('foss.baseinfo.LDRWSApplyTime'),
			dataIndex : 'applyTime',
			width : 120,
			renderer : function (value) {
				return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
			}
		}
	],
	//构造函数
	constructor : function (config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.baseinfo.leasedDriverWhitelists.LeasedDriverWhitelistsStore');
		me.bbar = me.getPagingToolbar();
		me.getPagingToolbar().store = me.store;
		me.dockedItems = [{
				xtype : 'toolbar',
				dock : 'top',
				layout : 'column',
				defaults : {
					margin : '0 0 5 3'
				},
				items : {
					xtype : 'button',
					text : baseinfo.leasedDriverWhitelists.i18n('foss.baseinfo.LDRWS.ApplicationStorage'),//申请入库
					disabled:!baseinfo.leasedDriverWhitelists.isPermission('leasedDriverWhitelists/driverWhitelistsAddButton'),
					hidden:!baseinfo.leasedDriverWhitelists.isPermission('leasedDriverWhitelists/driverWhitelistsAddButton'),
					width : 100,
					//弹出申请入库新的外请司机的窗口
					handler : function () {
						me.getApplyWindow(this.text, true, 'apply').show();
					}
				}
			}
		];
		me.callParent([cfg]);
	}
});

/*****************************************************  定义baseinfo.leasedDriverWhitelists.i18n('foss.baseinfo.baseinfo-leasedDriverWhitelists.62')申请窗口 *****************************************************/
/*
 * 定义：baseinfo.leasedDriverWhitelists.i18n('foss.baseinfo.baseinfo-leasedDriverWhitelists.62')申请入库、申请可用、申请不可用的窗口表单
 */
Ext.define('Foss.baseinfo.leasedDriverWhitelists.LeasedDriverWhitelistsApplyForm', {
	extend : 'Ext.form.Panel',
	frame : true,
	collapsible : true,
	isSearchComb : true,
	defaults : {
		margin : '5 25 5 25',
		labelWidth : 100
	},
	defaultType : 'textfield',
	layout : {
		type : 'table',
		columns : 2
	},
	items : [{
			xtype : 'commonleaseddriverselector',
			name : 'driverIdCard',
			fieldLabel : baseinfo.leasedDriverWhitelists.i18n('foss.baseinfo.LDRDeiverIdNumber'),
			allowBlank : false,
			blankText : baseinfo.leasedDriverWhitelists.i18n('foss.baseinfo.LDRDriverIdNumberCanNotEmpty'),
			queryParam : 'leasedDriverVo.leasedDriver.idCard',
			readOnly : false,
			statues : 'Y,N',
			columnWidth : 0.5,
			validateOnBlur : true,
			validateOnChange : true,
			validator : function (val) {
				var me = this;
				if (Ext.isEmpty(val)) {
					me.validatorMessage.message = me.blankText;
					return true;
				} else {
					if (!Ext.isEmpty(me.validatorMessage.validity) && me.validatorMessage.validity) {
						return me.validatorMessage.validity;
					} else {
						return me.validatorMessage.message;
					}
				}
			},
			validatorMessage : {
				validity : true,
				message : ''
			},
			listeners : {
				/**
				 * 模拟选择器：失去焦点获取外请司机信息
				 * @param {Ext.Component} me 当前对象
				 * @param {Ext.EventObject} event 当前触发事件对象
				 */
				select : function (me, event) {
					if (Ext.isEmpty(me.getValue())) {
						return;
					}
					Ext.Ajax.request({
						url : baseinfo.realPath('queryLeasedDriverWhitelists.action'),
						jsonData : {
							'leasedWhitelistVo' : {
								'whitelistAudit' : {
									'driverIdCard' : me.getValue()
								}
							}
						},
						success : function (response) {
							//检测baseinfo.leasedDriverWhitelists.i18n('foss.baseinfo.baseinfo-leasedDriverWhitelists.61')是否已经存在
							var whitelistAudit = Ext.decode(response.responseText).leasedWhitelistVo.whitelistAudit;
							if (Ext.isEmpty(whitelistAudit)) {
								if (Ext.isEmpty(me.getValue())) {
									return;
								}
								Ext.Ajax.request({
									url : baseinfo.realPath('queryLeasedDriver.action'),
									jsonData : {
										'leasedDriverVo' : {
											'leasedDriver' : {
												'idCard' : me.getValue()
											}
										}
									},
									success : function (response) {
										//刷新baseinfo.leasedDriverWhitelists.i18n('foss.baseinfo.baseinfo-leasedDriver.57')信息到申请入库窗口
										var leasedDriver = Ext.decode(response.responseText).leasedDriverVo.leasedDriver;
										var model = new Foss.baseinfo.leasedDriverWhitelists.LeasedDriverWhitelistsModel(leasedDriver);
										model.set('driverIdCard', me.getValue());
										model.set('applyTime', null);
										model.set('approveTime', null);
										me.up().getForm().loadRecord(model);
										if (Ext.isEmpty(leasedDriver)) {
											me.validatorMessage.validity = false;
											me.validatorMessage.message = baseinfo.leasedDriverWhitelists.i18n('foss.baseinfo.LDRWSOutSideDriverDoesNotExists');
											me.markInvalid(me.validatorMessage.message);
											//me.setActiveError(me.validatorMessage.message);
											Ext.MessageBox.show({
												title : baseinfo.leasedDriverWhitelists.i18n('foss.baseinfo.failureInformationTips'),
												msg : me.validatorMessage.message,
												width : 300,
												buttons : Ext.Msg.OK,
												icon : Ext.MessageBox.WARNING
											});
										} else {
											me.validatorMessage.validity = true;
											me.validatorMessage.message = null;
											//me.unsetActiveError();
											me.clearInvalid();
										}
									},
									exception : function (response) {
										var json = Ext.decode(response.responseText);
										me.validatorMessage.validity = false;
										me.validatorMessage.message = json.message;
										//me.setActiveError(me.validatorMessage.message);
										Ext.MessageBox.show({
											title : baseinfo.leasedDriverWhitelists.i18n('foss.baseinfo.failureInformationTips'),
											msg : me.validatorMessage.message,
											width : 300,
											buttons : Ext.Msg.OK,
											icon : Ext.MessageBox.WARNING
										});
									}
								});
							} else {
								var currentApplication = null;
								//当前申请
								if(whitelistAudit.currentApplication === 'apply_apply'){
									currentApplication = "<li>" + baseinfo.leasedDriverWhitelists.i18n('foss.baseinfo.LDRapplyApply');
								}else if(whitelistAudit.currentApplication === 'apply_available'){
									currentApplication = "<li>" + baseinfo.leasedDriverWhitelists.i18n('foss.baseinfo.LDRapplyAvailable');
								}else if(whitelistAudit.currentApplication === 'apply_unavailable'){
									currentApplication = "<li>" + baseinfo.leasedDriverWhitelists.i18n('foss.baseinfo.LDRapplyUnavailable');
								}else{
									currentApplication = "<li>" + baseinfo.leasedDriverWhitelists.i18n('foss.baseinfo.LDRapplyNull');
								}
								var status = null;
								//白名单状态
								if(whitelistAudit.status === 'audit_apply'){
									status = "<li>" + baseinfo.leasedDriverWhitelists.i18n('foss.baseinfo.LDRauditApply');
								}else if(whitelistAudit.status === 'audit_available'){
									status = "<li>" + baseinfo.leasedDriverWhitelists.i18n('foss.baseinfo.LDRauditAvailable');
								}else if(whitelistAudit.status === 'audit_unavailable'){
									status = "<li>" + baseinfo.leasedDriverWhitelists.i18n('foss.baseinfo.LDRauditUnavailable');
								}else{
									status = "<li>" + baseinfo.leasedDriverWhitelists.i18n('foss.baseinfo.LDRauditNull');
								}
								me.validatorMessage.validity = false;
								me.validatorMessage.message = baseinfo.leasedDriverWhitelists.i18n('foss.baseinfo.LDRWSDriverWhitelistAlreadyExists') + "<ui>" + currentApplication + status + "</ui>";
								me.markInvalid(me.validatorMessage.message);
								currentApplication = null;
								status = null;
								//me.setActiveError(me.validatorMessage.message);
								Ext.MessageBox.show({
									title : baseinfo.leasedDriverWhitelists.i18n('foss.baseinfo.failureInformationTips'),
									msg : me.validatorMessage.message,
									width : 300,
									buttons : Ext.Msg.OK,
									icon : Ext.MessageBox.WARNING
								});
							}
						},
						exception : function (response) {
							var json = Ext.decode(response.responseText);
							me.validatorMessage.validity = false;
							me.validatorMessage.message = json.message;
							Ext.MessageBox.show({
								title : baseinfo.leasedDriverWhitelists.i18n('foss.baseinfo.failureInformationTips'),
								msg : me.validatorMessage.message,
								width : 300,
								buttons : Ext.Msg.OK,
								icon : Ext.MessageBox.WARNING
							});
						}
					});
				},
				blur : function (me, eOpts) {
					if(Ext.isEmpty(me.getValue())){
						me.setValue(null);
					}else{
						var value = me.getValue();
						me.up('window').getApplyForm().getForm().reset();
						me.store.removeAll(false);
						me.setRawValue(value);
						me.setValue(value);
						me.store.load({
							scope : me,
							params : {
								start : 0,
								limit : 1,
								'leasedDriverVo.leasedDriver.idCard' : value
							},
							callback : function (rs) {
								me.fireEvent('select', me, eOpts);
							}
						});
						
					}
				}
			}
		}, {
			name : 'driverName',
			fieldLabel : baseinfo.leasedDriverWhitelists.i18n('foss.baseinfo.LDRDriverName'),
			readOnly : true,
			columnWidth : 0.5
		}, {
			name : 'driverPhone',
			fieldLabel : baseinfo.leasedDriverWhitelists.i18n('foss.baseinfo.LDRDriverTelphone'),
			readOnly : true,
			columnWidth : 0.5
		}, {
			name : 'qualification',
			fieldLabel : baseinfo.leasedDriverWhitelists.i18n('foss.baseinfo.leasedDriverQualification'),
			readOnly : true,
			columnWidth : 0.5
		}, {
			name : 'driverLicense',
			fieldLabel : baseinfo.leasedDriverWhitelists.i18n('foss.baseinfo.LDRWSDriverLicense'),
			readOnly : true,
			columnWidth : 0.5
		}, {
			xtype : 'label'
		}, {
			xtype : 'combobox',
			name : 'status',
			fieldLabel : baseinfo.leasedDriverWhitelists.i18n('foss.baseinfo.LDRWSWhitelistStatus'),
			readOnly : true,
			columnWidth : 0.5,
			displayField : 'valueName',
			valueField : 'valueCode',
			queryMode : 'local',
			triggerAction : 'all',
			editable : false,
			store : FossDataDictionary.getDataDictionaryStore('BES_WHITELISTS_AUDIT', 'Foss_baseinfo_leasedDriverWhitelists_StatusStore_Id', {
				'valueCode' : '',
				'valueName' : '-'
			}),
			style : {
				fontWeight : 'bold'
			}
		}, {
			xtype : 'combobox',
			name : 'currentApplication',
			fieldLabel : baseinfo.leasedDriverWhitelists.i18n('foss.baseinfo.LDRWSCurrentApply'),
			readOnly : true,
			columnWidth : 0.5,
			displayField : 'valueName',
			valueField : 'valueCode',
			queryMode : 'local',
			triggerAction : 'all',
			editable : false,
			store : FossDataDictionary.getDataDictionaryStore('BES_WHITELISTS_APPLY', 'Foss_baseinfo_leasedDriverWhitelists_CurrentApplicationStore_Id', {
				'valueCode' : '',
				'valueName' : '-'
			}),
			style : {
				fontWeight : 'bold'
			}
		}, {
			xtype : 'textareafield',
			name : 'applyNotes',
			readOnly : false,
			fieldLabel : baseinfo.leasedDriverWhitelists.i18n('foss.baseinfo.LDRWSApplicationRemarks'),
			colspan : 2,
			maxlength : 1000,
			width : 560
		}, {
			name : 'id',
			fieldLabel : 'ID',
			readOnly : true,
			hidden : true,
			columnWidth : 0.5,
			colspan : 2
		}
	],
	constructor : function (config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

/*
 * 定义baseinfo.leasedDriverWhitelists.i18n('foss.baseinfo.baseinfo-leasedDriverWhitelists.62')申请的表单窗口
 */
Ext.define('Foss.baseinfo.leasedDriverWhitelists.LeasedDriverWhitelistsApplyWindow', {
	extend : 'Ext.window.Window',
	//id : 'Foss_baseinfo-leasedDriverWhitelists_ApplyWindow_Id',
	title : baseinfo.leasedDriverWhitelists.i18n('foss.baseinfo.baseinfo-leasedDriverWhitelists.61'),
	width : 650,
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	modal : true,
	resizable : false,
	closeAction : 'hide',
	applyForm : null,
	getApplyForm : function () {
		if (null == this.applyForm) {
			this.applyForm = Ext.create('Foss.baseinfo.leasedDriverWhitelists.LeasedDriverWhitelistsApplyForm');
		}
		return this.applyForm;
	},
	buttons : null,
	getButtons : function (index) {
		this.buttons = new Array();
		if (Ext.isEmpty(this.buttons)) {
			Ext.Array.include(this.buttons, this.items.items[1].items.items[0]);
			Ext.Array.include(this.buttons, this.items.items[1].items.items[2]);
			Ext.Array.include(this.buttons, this.items.items[1].items.items[4]);
		}
		if (arguments.length === 0) {
			return this.buttons;
		} else {
			return this.buttons[index];
		}
	},
	operationUrl : null,
	setOperationUrl : function (url) {
		this.operationUrl = url;
	},
	constructor : function (config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.items = [
			me.getApplyForm(), {
				border : false,
				xtype : 'container',
				layout : 'column',
				margin : '15 0 0 155',
				defaults : {
					margin : '0 0 30 0'
				},
				items : [{
						xtype : 'button',
						columnWidth : .16,
						text : baseinfo.leasedDriverWhitelists.i18n('foss.baseinfo.cancel'),
						handler : function () {
							me.hide();
						}
					}, {
						border : false,
						columnWidth : .10,
						html : '&nbsp;'
					}, {
						xtype : 'button',
						columnWidth : .16,
						text : baseinfo.leasedDriverWhitelists.i18n('foss.baseinfo.reset'),
						handler : function () {
							me.getApplyForm().getForm().reset();
						}
					}, {
						border : false,
						columnWidth : .10,
						html : '&nbsp;'
					}, {
						columnWidth : .16,
						xtype : 'button',
						cls : 'yellow_button',
						text : baseinfo.leasedDriverWhitelists.i18n('foss.baseinfo.save'),
						plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin', {
							seconds: 5
						}),
						listeners : {
							click : function (mine, event, options) {
								var applyForm = me.getApplyForm().getForm();
								if (applyForm.isValid()) { //校验FORM是否通过校验
									var applyNotes = applyForm.findField('applyNotes').getValue();
									Ext.Ajax.request({
										url : me.operationUrl.toString(),
										jsonData : {
											'leasedWhitelistVo' : {
												'whitelistAudit' : applyForm.getRecord().data,
												'comment' : applyNotes
											}
										},
										success : function (response) {
											var json = Ext.decode(response.responseText);
											Ext.getCmp('T_baseinfo-leasedDriverWhitelists_content').getLeasedDriverWhitelistsGrid().store.load();
											Ext.MessageBox.show({
												title : baseinfo.leasedDriverWhitelists.i18n('foss.baseinfo.successPrompt'),
												msg : json.message,
												width : 300,
												buttons : Ext.Msg.OK,
												callback : function () {
													me.getApplyForm().up('window').hide();
												},
												icon : Ext.MessageBox.INFO
											});
											applyForm = null;
										},
										exception : function (response) {
											var json = Ext.decode(response.responseText);
											Ext.MessageBox.show({
												title : baseinfo.leasedDriverWhitelists.i18n('foss.baseinfo.failureInformationTips'),
												msg : json.message,
												width : 300,
												buttons : Ext.Msg.OK,
												callback : function () {
													me.getApplyForm().up('window').hide();
												},
												icon : Ext.MessageBox.WARNING
											});
											applyForm = null;
										}
									});
								} else {
									top.Ext.MessageBox.show({
										title : baseinfo.leasedDriverWhitelists.i18n('foss.baseinfo.failureInformationTips'),
										msg : applyForm.findField('driverIdCard').validatorMessage.message,
										width : 300,
										buttons : Ext.Msg.OK,
										icon : Ext.MessageBox.WARNING
									});
									applyForm = null;
								}
							}
						}
					}
				]
			}
		];
		me.callParent([cfg]);
	},
	listeners : {
		beforeshow : function(me, eOpts){
			var fielsds = me.getApplyForm().getForm().getFields();
			if(!Ext.isEmpty(fielsds)){
				fielsds.each(function (item, index, length) {
					item.clearInvalid();
					item.unsetActiveError();
				});
			}
			fielsds = null;
		}
	}
});

/****************************************************  窗口初始化 *****************************************************/
Ext.onReady(function () {
	Ext.QuickTips.init();
	/*
	 * 创建查询表单"FORM"
	 */
	var queryForm = Ext.create('Foss.baseinfo.leasedDriverWhitelists.QueryForm');
	/*
	 * 创建查询baseinfo.leasedDriverWhitelists.i18n('foss.baseinfo.baseinfo-leasedDriverWhitelists.62')结果列表结果窗口
	 */
	var queryResult = Ext.create('Foss.baseinfo.leasedDriverWhitelists.LeasedDriverWhitelistsGrid');
	/*
	 * 执行页面的初始化布局
	 */
	Ext.getCmp('T_baseinfo-leasedDriverWhitelists').add(Ext.create('Ext.panel.Panel', {
		id : 'T_baseinfo-leasedDriverWhitelists_content',
		cls : 'panelContentNToolbar',
		bodyCls : 'panelContentNToolbar-body',
		layout : 'auto',
		//获得查询表单"FORM"
		getQueryForm : function () {
			return queryForm;
		},
		//获得查询baseinfo.leasedDriverWhitelists.i18n('foss.baseinfo.baseinfo-leasedDriverWhitelists.62')结果列表结果窗口
		getLeasedDriverWhitelistsGrid : function () {
			return queryResult;
		},
		items : [
			queryForm, //加入查询表单"FORM"
			queryResult //加入结果列表
		]
//		renderTo : 'T_baseinfo-leasedDriverWhitelists-body'
	}));
});


