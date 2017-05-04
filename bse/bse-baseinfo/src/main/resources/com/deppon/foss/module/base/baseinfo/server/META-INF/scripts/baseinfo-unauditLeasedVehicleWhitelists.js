/**
 * @author 100847-foss-GaoPeng
 */
/*************************************************  定义"外请车审核白名单"列表查询条件窗口 ***********************************************/
/*
 * 定义查询条件的表单FORM
 */
Ext.define('Foss.baseinfo.unauditLeasedVehicleWhitelists.QueryForm', {
	extend : 'Ext.form.Panel',
	title : '查询条件',
	id : 'Foss_baseinfo_unauditLeasedVehicleWhitelists_QueryForm_Id',
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
	height : 135,
	defaultType : 'textfield',
	layout : 'column',
	items : [{
			name : 'vehicleNo',
			fieldLabel : '车牌号',
			columnWidth : .40
		}, {
			xtype : 'combobox',
			name : 'currentApplication',
			fieldLabel : '当前申请',
			columnWidth : .30,
			displayField : 'valueName',
			valueField : 'valueCode',
			queryMode : 'local',
			triggerAction : 'all',
			editable : false,
			value : '',
			store : FossDataDictionary.getDataDictionaryStore('BES_WHITELISTS_APPLY', 'Foss_baseinfo_leasedVehicleWhitelists_CurrentApplicationStore_Id', {
				'valueCode' : '',
				'valueName' : '全部'
			})
		}, {
			xtype : 'commonemployeeselector',
			name : 'applyUser',
			fieldLabel : '申请人',
			columnWidth : .30
		}, {
			xtype : 'button',
			columnWidth : .08,
			text : '重置',
			disabled:!baseinfo.unauditLeasedVehicleWhitelists.isPermission('unauditLeasedVehicleWhitelists/carAuditQueryButton'),
			hidden:!baseinfo.unauditLeasedVehicleWhitelists.isPermission('unauditLeasedVehicleWhitelists/carAuditQueryButton'),
			handler : function () {
				Ext.getCmp('Foss_baseinfo_unauditLeasedVehicleWhitelists_QueryForm_Id').getForm().reset();
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
			text : '查询',
			disabled:!baseinfo.unauditLeasedVehicleWhitelists.isPermission('unauditLeasedVehicleWhitelists/carAuditQueryButton'),
			hidden:!baseinfo.unauditLeasedVehicleWhitelists.isPermission('unauditLeasedVehicleWhitelists/carAuditQueryButton'),
			handler : function () {
				Ext.getCmp('Foss_baseinfo_unauditLeasedVehicleWhitelists_LeasedVehicleWhitelistsAuditGrid_Id').getPagingToolbar().moveFirst();
			}
		}
	],
	constructor : function (config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

/*******************************************************  定义"车辆白名单"列表结果窗口 *****************************************************/
/*
 * 定义：一个"车辆白名单"的数据模型"Model"
 */
Ext.define('Foss.baseinfo.unauditLeasedVehicleWhitelists.LeasedVehicleWhitelistsAuditModel', {
	extend : 'Ext.data.Model',
	idProperty : 'extid',
	idgen : 'uuid',
	fields : [{
			name : 'extid'
		}, {
			name : 'id', //ID标识
			type : 'string'
		}, {
			name : 'vehicleNo', //车牌号
			type : 'string'
		}, {
			name : 'vehicleType', //车辆类型
			type : 'string'
		}, {
			name : 'drivingLicense', //行驶证
			type : 'string'
		}, {
			name : 'operatingLic', //营运证
			type : 'string'
		}, {
			name : 'endTimeDrivingLic', //行驶证到期日期
			type : 'date',
			convert : function (value) {
				if (Ext.isEmpty(value)) {
					return null;
				}
				return new Date(value);
			}
		}, {
			name : 'endTimeOperatingLic', //营运证到期日期
			type : 'date',
			convert : function (value) {
				if (Ext.isEmpty(value)) {
					return null;
				}
				return new Date(value);
			}
		}, {
			name : 'insureCard', //保险卡
			type : 'string'
		}, {
			name : 'contact', //车主姓名
			type : 'string'
		}, {
			name : 'contactPhone', //车主联系方式
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
		}
	]
});

/*
 * 定义：一个"车辆白名单"的查询数据模型"Store"交互后台
 */
Ext.define('Foss.baseinfo.unauditLeasedVehicleWhitelists.LeasedVehicleWhitelistsAuditStore', {
	extend : 'Ext.data.Store',
	autoLoad : false,
	//页面条数定义
	pageSize : 10,
	//绑定model
	model : 'Foss.baseinfo.unauditLeasedVehicleWhitelists.LeasedVehicleWhitelistsAuditModel',
	sorters: [{     
	     property: 'applyTime',
	     direction: 'DESC'
	 }],
	proxy : {
		//以JSON的方式加载数据
		type : 'ajax',
		actionMethods : 'POST',
		url : baseinfo.realPath('queryUnauditLeasedVehicleWhitelistsList.action'),
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
			var queryForm = Ext.getCmp('Foss_baseinfo_unauditLeasedVehicleWhitelists_QueryForm_Id').getForm();
			if (queryForm != null) {
				var queryParams = queryForm.getValues();
				Ext.apply(operation, {
					params : {
						'leasedWhitelistVo.whitelistAudit.vehicleNo' : queryParams.vehicleNo,
						'leasedWhitelistVo.whitelistAudit.currentApplication' : queryParams.currentApplication,
						'leasedWhitelistVo.whitelistAudit.applyUser' : queryParams.applyUser
					}
				});
			}
		}
	}
});

/*
 * 定义："车辆白名单"内嵌在结果列表中的查询详细的窗口表单
 */
Ext.define('Foss.baseinfo.unauditLeasedVehicleWhitelists.LeasedVehicleWhitelistsAuditDetailForm', {
	extend : 'Ext.form.Panel',
	height : 270,
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
			name : 'vehicleNo',
			fieldLabel : '车牌号',
			readOnly : true,
			columnWidth : 0.5
		}, {
			xtype : 'combobox',
			name : 'vehicleType',
			fieldLabel : '车辆类型',
			readOnly : true,
			columnWidth : 0.5,
			displayField : 'valueName',
			valueField : 'valueCode',
			queryMode : 'local',
			triggerAction : 'all',
			editable : false,
			store : FossDataDictionary.getDataDictionaryStore('BES_VEHICLE_TYPE', 'Foss_baseinfo_leasedVehicleWhitelists_VehicleTypeStore_Id', {
				'valueCode' : '',
				'valueName' : '-'
			})
		}, {
			name : 'drivingLicense',
			fieldLabel : '行驶证',
			readOnly : true,
			columnWidth : 0.5
		}, {
			name : 'operatingLic',
			fieldLabel : '营运证',
			readOnly : true,
			columnWidth : 0.5
		}, {
			xtype : 'datefield',
			name : 'endTimeDrivingLic',
			fieldLabel : '行驶证到期日期',
			readOnly : true,
			columnWidth : 0.5,
			width : 450,
			format : 'Y-m-d H:i:s'
		}, {
			xtype : 'datefield',
			name : 'endTimeOperatingLic',
			fieldLabel : '营运证到期日期',
			readOnly : true,
			columnWidth : 0.5,
			width : 450,
			format : 'Y-m-d H:i:s'
		}, {
			name : 'insureCard',
			fieldLabel : '保险卡',
			readOnly : true,
			columnWidth : 0.5
		}, {
			xtype : 'label'
		}, {
			name : 'contact',
			fieldLabel : '车主姓名',
			readOnly : true,
			columnWidth : 0.5
		}, {
			name : 'contactPhone',
			fieldLabel : '车主联系方式',
			readOnly : true,
			columnWidth : 0.5
		}, {
			xtype : 'combobox',
			name : 'status',
			fieldLabel : '白名单状态',
			readOnly : true,
			columnWidth : 0.5,
			displayField : 'valueName',
			valueField : 'valueCode',
			queryMode : 'local',
			triggerAction : 'all',
			editable : false,
			store : FossDataDictionary.getDataDictionaryStore('BES_WHITELISTS_AUDIT', 'Foss_baseinfo_leasedVehicleWhitelists_StatusStore_Id', {
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
			fieldLabel : '当前申请',
			columnWidth : 0.5,
			displayField : 'valueName',
			valueField : 'valueCode',
			queryMode : 'local',
			triggerAction : 'all',
			editable : false,
			store : FossDataDictionary.getDataDictionaryStore('BES_WHITELISTS_APPLY', 'Foss_baseinfo_leasedVehicleWhitelists_CurrentApplicationStore_Id', {
				'valueCode' : '',
				'valueName' : '-'
			}),
			style : {
				fontWeight : 'bold'
			}
		}, {
			name : 'applyUser',
			fieldLabel : '申请人',
			readOnly : true,
			columnWidth : 0.5
		}, {
			xtype : 'datefield',
			name : 'applyTime',
			fieldLabel : '申请时间',
			readOnly : true,
			columnWidth : 0.5,
			width : 450,
			format : 'Y-m-d H:i:s'
		}, {
			name : 'applyNotes',
			fieldLabel : '申请备注',
			readOnly : true,
			columnWidth : 0.5,
			colspan : 2,
			width : 550
		}
	],
	constructor : function (config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

/*
 * 定义：内嵌在结果列表中的查询详细的窗口
 */
Ext.define('Foss.baseinfo.unauditLeasedVehicleWhitelists.LeasedVehicleWhitelistsAuditDetailPanel', {
	extend : 'Ext.panel.Panel',
	title : '外请车辆白名单-详细信息',
	frame : true,
	informationForm : null,
	/**
	 * 获取外请车辆白名单详细信息
	 * @return {Object} LeasedVehicleWhitelistsAuditDetailForm
	 */
	getInformationForm : function () {
		if (this.informationForm == null) {
			this.informationForm = Ext.create('Foss.baseinfo.unauditLeasedVehicleWhitelists.LeasedVehicleWhitelistsAuditDetailForm');
		}
		return this.informationForm;
	},
	constructor : function (config) {
		Ext.apply(this, config);
		this.items = [this.getInformationForm()];
		this.callParent(arguments);
	},
	bindData : function (record) {
		//绑定表格数据到表单上
		this.getInformationForm().getForm().loadRecord(record);
	}
});

/*
 * 查询结果列表窗口
 */
Ext.define('Foss.baseinfo.unauditLeasedVehicleWhitelists.LeasedVehicleWhitelistsAuditGrid', {
	extend : 'Ext.grid.Panel',
	columnLines : true, // 增加表格列的分割线
	id : 'Foss_baseinfo_unauditLeasedVehicleWhitelists_LeasedVehicleWhitelistsAuditGrid_Id',
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	frame : true, // 表格对象增加一个边框
	stripeRows : true,
	columnLines : true, // 增加表格列的分割线
	title : '申请车辆信息', // 定义表格的标题
	collapsible : true,
	animCollapse : true,
	//表格多选插件
	selModel : Ext.create('Ext.selection.CheckboxModel'),
	store : null,
	//表格行可展开的插件
	plugins : [{
			ptype : 'rowexpander',
			rowsExpander : false, // 定义行展开模式（单行与多行），默认是多行展开(值true)
			rowBodyElement : 'Foss.baseinfo.unauditLeasedVehicleWhitelists.LeasedVehicleWhitelistsAuditDetailPanel' // 行体内容
		}
	],
	auditWindow : null,
	/**
	 * 获取审核"拒绝、同意"的窗口的对象
	 * @return {Ext.Component} 申请窗口的对象
	 */
	getAuditWindow : function () {
		var me = this;
		if (this.auditWindow == null) {
			this.auditWindow = Ext.create('Foss.baseinfo.unauditLeasedVehicleWhitelists.LeasedVehicleWhitelistsAuditWindow');
		}
		return this.auditWindow;
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
			text : '操作',
			align : 'center',
			items : [{
					iconCls : 'deppon_icons_dispose',
					tooltip : '处理',
					disabled:!baseinfo.unauditLeasedVehicleWhitelists.isPermission('unauditLeasedVehicleWhitelists/carAuditHandleButton'),
					/**
					 * "撤回"响应事件
					 * @param {Object} grid 当前表格
					 * @param {Number} rowIndex 行索引
					 * @param {Number} colIndex 列索引
					 */
					handler : function (grid, rowIndex, colIndex) {
						var record = grid.getStore().getAt(rowIndex);
						var auditWindow = grid.up().getAuditWindow();
						auditWindow.getAuditForm().getForm().reset().loadRecord(record);
						auditWindow.show();
					}
				}
			]
		}, {
			header : '车牌号',
			dataIndex : 'vehicleNo',
			width : 100
		}, {
			header : '车辆类型',
			dataIndex : 'vehicleType',
			flex : 1,
			width : 110,
			renderer : function (value) {
				var val = FossDataDictionary.rendererSubmitToDisplay(value, 'BES_VEHICLE_TYPE');
				if (Ext.isEmpty(val)) {
					return '-';
				} else {
					return val;
				}
			}
		}, {
			header : '白名单状态',
			dataIndex : 'status',
			width : 90,
			renderer : function (value) {
				var val = FossDataDictionary.rendererSubmitToDisplay(value, 'BES_WHITELISTS_AUDIT');
				if (Ext.isEmpty(val)) {
					return '-';
				} else {
					return val;
				}
			}
		}, {
			header : '当前申请',
			dataIndex : 'currentApplication',
			width : 85,
			renderer : function (value) {
				var val = FossDataDictionary.rendererSubmitToDisplay(value, 'BES_WHITELISTS_APPLY');
				if (Ext.isEmpty(val)) {
					return '-';
				} else {
					return val;
				}
			}
		}, {
			header : '申请备注',
			dataIndex : 'applyNotes',
			width : 160
		}, {
			header : '申请人',
			dataIndex : 'applyUser',
			flex : 1,
			width : 110
		}, {
			header : '申请时间',
			dataIndex : 'applyTime',
			width : 115,
			renderer : function (value) {
				return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
			}
		}, {
			header : '营运证到期日期',
			dataIndex : 'endTimeOperatingLic',
			width : 115,
			renderer : function (value) {
				return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
			}
		}, {
			header : '行驶证到期日期',
			dataIndex : 'endTimeDrivingLic',
			width : 115,
			renderer : function (value) {
				return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
			}
		}
	],
	//构造函数
	constructor : function (config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.baseinfo.unauditLeasedVehicleWhitelists.LeasedVehicleWhitelistsAuditStore');
		me.bbar = me.getPagingToolbar();
		me.getPagingToolbar().store = me.store;
		me.dockedItems = [{
				xtype : 'toolbar',
				dock : 'top',
				layout : 'column',
				defaults : {
					margin : '0 0 5 3'
				},
				items : [{
						xtype : 'button',
						text : '批量同意',
						disabled:!baseinfo.unauditLeasedVehicleWhitelists.isPermission('unauditLeasedVehicleWhitelists/carAuditBatchAgreeButton'),
						hidden:!baseinfo.unauditLeasedVehicleWhitelists.isPermission('unauditLeasedVehicleWhitelists/carAuditBatchAgreeButton'),
						width : 100,
						//弹出申请入库新的外请车辆的窗口
						handler : function () {
							Ext.MessageBox.show({
								title : '确认提示',
								msg : '批量同意（外请车辆白名单）后不可恢复，确认是否继续？',
								buttons : Ext.Msg.YESNO,
								icon : Ext.MessageBox.QUESTION,
								fn : function (btn) {
									if (btn == 'yes') {
										// 获取选中的记录
										var grid = Ext.getCmp('Foss_baseinfo_unauditLeasedVehicleWhitelists_LeasedVehicleWhitelistsAuditGrid_Id');
										var selectionRecord = grid.getSelectionModel().getSelection();
										if (selectionRecord && selectionRecord.length > 0) {
											var ids = new Array();
											for (var i = 0; i < selectionRecord.length; i++) {
												Ext.Array.include(ids, selectionRecord[i].data.id);
											}
											Ext.Ajax.request({
												url : baseinfo.realPath('auditArgeeLeasedVehicleWhitelists.action'),
												jsonData : {
													'leasedWhitelistVo' : {
														'batchIds' : ids
													}
												},
												success : function (response) {
													var json = Ext.decode(response.responseText);
													Ext.getCmp('T_baseinfo-unauditLeasedVehicleWhitelists_content').getLeasedVehicleWhitelistsAuditGrid().store.load();
													Ext.MessageBox.show({
														title : '信息（成功）提示',
														msg : json.message,
														width : 300,
														buttons : Ext.Msg.OK,
														icon : Ext.MessageBox.INFO
													});
												},
												exception : function (response) {
													var json = Ext.decode(response.responseText);
													Ext.MessageBox.show({
														title : '信息（失败）提示',
														msg : json.message,
														width : 450,
														buttons : Ext.Msg.OK,
														icon : Ext.MessageBox.WARNING
													});
												}
											});
										} else {
											Ext.MessageBox.show({
												title : '信息（失败）提示',
												msg : '无选择记录数据！',
												width : 300,
												buttons : Ext.Msg.OK,
												icon : Ext.MessageBox.WARNING
											});
										}
									}
								}
							});
						}
					}, {
						xtype : 'button',
						text : '批量拒绝',
						disabled:!baseinfo.unauditLeasedVehicleWhitelists.isPermission('unauditLeasedVehicleWhitelists/carAuditBatchRefuseButton'),
						hidden:!baseinfo.unauditLeasedVehicleWhitelists.isPermission('unauditLeasedVehicleWhitelists/carAuditBatchRefuseButton'),
						width : 100,
						//弹出申请入库新的外请车辆的窗口
						handler : function () {
							Ext.MessageBox.show({
								title : '确认提示',
								msg : '批量拒绝（外请车辆白名单）后不可恢复，确认是否继续？',
								buttons : Ext.Msg.YESNO,
								icon : Ext.MessageBox.QUESTION,
								fn : function (btn) {
									if (btn == 'yes') {
										// 获取选中的记录
										var grid = Ext.getCmp('Foss_baseinfo_unauditLeasedVehicleWhitelists_LeasedVehicleWhitelistsAuditGrid_Id');
										var selectionRecord = grid.getSelectionModel().getSelection();
										if (selectionRecord && selectionRecord.length > 0) {
											var ids = new Array();
											for (var i = 0; i < selectionRecord.length; i++) {
												Ext.Array.include(ids, selectionRecord[i].data.id);
											}
											Ext.Ajax.request({
												url : baseinfo.realPath('auditDeclineLeasedVehicleWhitelists.action'),
												jsonData : {
													'leasedWhitelistVo' : {
														'batchIds' : ids
													}
												},
												success : function (response) {
													var json = Ext.decode(response.responseText);
													Ext.getCmp('T_baseinfo-unauditLeasedVehicleWhitelists_content').getLeasedVehicleWhitelistsAuditGrid().store.load();
													Ext.MessageBox.show({
														title : '信息（成功）提示',
														msg : json.message,
														width : 300,
														buttons : Ext.Msg.OK,
														icon : Ext.MessageBox.INFO
													});
												},
												exception : function (response) {
													var json = Ext.decode(response.responseText);
													Ext.MessageBox.show({
														title : '信息（失败）提示',
														msg : json.message,
														width : 450,
														buttons : Ext.Msg.OK,
														icon : Ext.MessageBox.WARNING
													});
												}
											});
										} else {
											Ext.MessageBox.show({
												title : '信息（失败）提示',
												msg : '无选择记录数据！',
												width : 300,
												buttons : Ext.Msg.OK,
												icon : Ext.MessageBox.WARNING
											});
										}
									}
								}
							});
						}
					}
				]
			}
		];
		me.callParent([cfg]);
	}
});

/*****************************************************  定义"车辆白名单"申请窗口 *****************************************************/
/*
 * 定义："车辆白名单"申请入库、申请可用、申请不可用的窗口表单
 */
Ext.define('Foss.baseinfo.unauditLeasedVehicleWhitelists.LeasedVehicleWhitelistsAuditForm', {
	title : '审核',
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
			name : 'vehicleNo',
			fieldLabel : '车牌号',
			readOnly : true,
			columnWidth : 0.5
		}, {
			xtype : 'combobox',
			name : 'vehicleType',
			fieldLabel : '车辆类型',
			readOnly : true,
			columnWidth : 0.5,
			displayField : 'valueName',
			valueField : 'valueCode',
			queryMode : 'local',
			triggerAction : 'all',
			editable : false,
			store : FossDataDictionary.getDataDictionaryStore('BES_VEHICLE_TYPE', 'Foss_baseinfo_leasedVehicleWhitelists_VehicleTypeStore_Id', {
				'valueCode' : '',
				'valueName' : '-'
			})
		}, {
			name : 'drivingLicense',
			fieldLabel : '行驶证',
			readOnly : true,
			columnWidth : 0.5
		}, {
			name : 'operatingLic',
			fieldLabel : '营运证',
			readOnly : true,
			columnWidth : 0.5
		}, {
			xtype : 'datefield',
			name : 'endTimeDrivingLic',
			fieldLabel : '行驶证到期日期',
			readOnly : true,
			columnWidth : 0.5,
			format : 'Y-m-d H:i:s'
		}, {
			xtype : 'datefield',
			name : 'endTimeOperatingLic',
			fieldLabel : '营运证到期日期',
			readOnly : true,
			columnWidth : 0.5,
			format : 'Y-m-d H:i:s'
		}, {
			name : 'insureCard',
			fieldLabel : '保险卡',
			readOnly : true,
			columnWidth : 0.5
		}, {
			xtype : 'label'
		}, {
			name : 'contact',
			fieldLabel : '车主姓名',
			readOnly : true,
			columnWidth : 0.5
		}, {
			name : 'contactPhone',
			fieldLabel : '车主电话',
			readOnly : true,
			columnWidth : 0.5
		}, {
			xtype : 'combobox',
			name : 'status',
			fieldLabel : '白名单状态',
			hidden : true,
			readOnly : true,
			columnWidth : 0.5,
			displayField : 'valueName',
			valueField : 'valueCode',
			queryMode : 'local',
			triggerAction : 'all',
			editable : false,
			store : FossDataDictionary.getDataDictionaryStore('BES_WHITELISTS_AUDIT', 'Foss_baseinfo_leasedVehicleWhitelists_StatusStore_Id', {
				'valueCode' : '',
				'valueName' : '-'
			}),
			style : {
				fontWeight : 'bold'
			}
		}, {
			xtype : 'combobox',
			name : 'currentApplication',
			hidden : true,
			readOnly : true,
			fieldLabel : '当前申请',
			columnWidth : 0.5,
			displayField : 'valueName',
			valueField : 'valueCode',
			queryMode : 'local',
			triggerAction : 'all',
			editable : false,
			store : FossDataDictionary.getDataDictionaryStore('BES_WHITELISTS_APPLY', 'Foss_baseinfo_leasedVehicleWhitelists_CurrentApplicationStore_Id', {
				'valueCode' : '',
				'valueName' : '-'
			}),
			style : {
				fontWeight : 'bold'
			}
		}, {
			name : 'applyUser',
			fieldLabel : '申请人',
			readOnly : true,
			columnWidth : 0.5
		}, {
			xtype : 'datefield',
			name : 'applyTime',
			fieldLabel : '申请时间',
			readOnly : true,
			columnWidth : 0.5,
			format : 'Y-m-d H:i:s'
		}, {
			xtype : 'textareafield',
			name : 'auditNotes',
			readOnly : false,
			fieldLabel : '审核备注',
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
 * 定义"车辆白名单"申请的表单窗口
 */
Ext.define('Foss.baseinfo.unauditLeasedVehicleWhitelists.LeasedVehicleWhitelistsAuditWindow', {
	extend : 'Ext.window.Window',
	//id : 'Foss_baseinfo-unauditLeasedVehicleWhitelistsAudit_AuditWindow_Id',
	title : '外请车辆白名单',
	width : 650,
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	modal : true,
	closeAction : 'hide',
	auditForm : null,
	getAuditForm : function () {
		if (null == this.auditForm) {
			this.auditForm = Ext.create('Foss.baseinfo.unauditLeasedVehicleWhitelists.LeasedVehicleWhitelistsAuditForm');
		}
		return this.auditForm;
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
	constructor : function (config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.items = [
			me.getAuditForm(), {
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
						text : '取消',
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
						text : '拒绝',
						handler : function () {
							var auditFrom = me.getAuditForm().getForm();
							Ext.Ajax.request({
								url : baseinfo.realPath('auditDeclineLeasedVehicleWhitelists.action'),
								jsonData : {
									'leasedWhitelistVo' : {
										'batchIds' : new Array(auditFrom.getRecord().data.id),
										'comment' : auditFrom.findField('auditNotes').getValue()
									}
								},
								success : function (response) {
									var json = Ext.decode(response.responseText);
									Ext.getCmp('T_baseinfo-unauditLeasedVehicleWhitelists_content').getLeasedVehicleWhitelistsAuditGrid().store.load();
									Ext.MessageBox.show({
										title : '信息（成功）提示',
										msg : json.message,
										width : 300,
										buttons : Ext.Msg.OK,
										callback : function () {
											me.getAuditForm().up('window').hide();
										},
										icon : Ext.MessageBox.INFO
									});
								},
								exception : function (response) {
									var json = Ext.decode(response.responseText);
									Ext.MessageBox.show({
										title : '信息（失败）提示',
										msg : json.message,
										width : 450,
										buttons : Ext.Msg.OK,
										callback : function () {
											me.getAuditForm().up('window').hide();
										},
										icon : Ext.MessageBox.WARNING
									});
								}
							});
						}
					}, {
						border : false,
						columnWidth : .10,
						html : '&nbsp;'
					}, {
						columnWidth : .16,
						xtype : 'button',
						text : '同意',
						listeners : {
							click : function (mine, event, options) {
								var auditFrom = me.getAuditForm().getForm();
								Ext.Ajax.request({
									url : baseinfo.realPath('auditArgeeLeasedVehicleWhitelists.action'),
									jsonData : {
										'leasedWhitelistVo' : {
											'batchIds' : new Array(auditFrom.getRecord().data.id),
											'comment' : auditFrom.findField('auditNotes').getValue()
										}
									},
									success : function (response) {
										var json = Ext.decode(response.responseText);
										Ext.getCmp('T_baseinfo-unauditLeasedVehicleWhitelists_content').getLeasedVehicleWhitelistsAuditGrid().store.load();
										Ext.MessageBox.show({
											title : '信息（成功）提示',
											msg : json.message,
											width : 300,
											buttons : Ext.Msg.OK,
											callback : function () {
												me.getAuditForm().up('window').hide();
											},
											icon : Ext.MessageBox.INFO
										});
									},
									exception : function (response) {
										var json = Ext.decode(response.responseText);
										Ext.MessageBox.show({
											title : '信息（失败）提示',
											msg : json.message,
											width : 450,
											buttons : Ext.Msg.OK,
											callback : function () {
												me.getAuditForm().up('window').hide();
											},
											icon : Ext.MessageBox.WARNING
										});
									}
								});
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
			var fielsds = me.getAuditForm().getForm().getFields();
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
	var queryForm = Ext.create('Foss.baseinfo.unauditLeasedVehicleWhitelists.QueryForm');
	/*
	 * 创建查询"车辆白名单"结果列表结果窗口
	 */
	var queryResult = Ext.create('Foss.baseinfo.unauditLeasedVehicleWhitelists.LeasedVehicleWhitelistsAuditGrid');
	/*
	 * 执行页面的初始化布局
	 */
	Ext.getCmp('T_baseinfo-unauditLeasedVehicleWhitelists').add(Ext.create('Ext.panel.Panel', {
		id : 'T_baseinfo-unauditLeasedVehicleWhitelists_content',
		cls : 'panelContentNToolbar',
		bodyCls : 'panelContentNToolbar-body',
		layout : 'auto',
		//获得查询表单"FORM"
		getQueryForm : function () {
			return queryForm;
		},
		//获得查询"车辆白名单"结果列表结果窗口
		getLeasedVehicleWhitelistsAuditGrid : function () {
			return queryResult;
		},
		items : [
			queryForm, //加入查询表单"FORM"
			queryResult //加入结果列表
		] 
	}));
});
