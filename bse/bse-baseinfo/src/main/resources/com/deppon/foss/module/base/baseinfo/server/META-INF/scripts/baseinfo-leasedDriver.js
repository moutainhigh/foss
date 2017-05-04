/**
 * @author 100847-foss-GaoPeng
 */
/***************************************************  定义baseinfo.leasedDriver.i18n('foss.baseinfo.baseinfo-leasedDriver.57')列表查询条件窗口 *************************************************/
/*
 * 查询条件表单FORM中的KEY/VALUE的参数MAP存储器
 */
Ext.define('Foss.baseinfo.leasedDriver.QueryFormParameterStore', {
	extend : 'Ext.data.Store',
	fields : [{
			name : 'valueCode',
			type : 'string'
		}, {
			name : 'valueName',
			type : 'string'
		}
	],
	proxy : {
		type : 'memory',
		reader : {
			type : 'json',
			root : 'items' //定义读取JSON数据的根对象
		}
	},
	constructor : function (config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

/*
 * 定义查询条件的表单FORM
 */
Ext.define('Foss.baseinfo.leasedDriver.QueryForm', {
	extend : 'Ext.form.Panel',
	title : baseinfo.leasedDriver.i18n('foss.baseinfo.queryCondition'),
	id : 'Foss_baseinfo_leasedDriver_QueryForm_Id',
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
			name : 'driverName',
			fieldLabel : baseinfo.leasedDriver.i18n('foss.baseinfo.LDRDriverName'),
			columnWidth : .30
		}, {
			name : 'driverPhone',
			fieldLabel : baseinfo.leasedDriver.i18n('foss.baseinfo.LDRDriverTelphone'),
			columnWidth : .30
		}, {
			name : 'idCard',
			fieldLabel : baseinfo.leasedDriver.i18n('foss.baseinfo.LDRDriverIdCard'),
			columnWidth : .40
		}, {
			xtype : 'button',
			columnWidth : .08,
			text : baseinfo.leasedDriver.i18n('foss.baseinfo.reset'),
			disabled:!baseinfo.leasedDriver.isPermission('leasedDriver/leasedDriverQueryButton'),
			hidden:!baseinfo.leasedDriver.isPermission('leasedDriver/leasedDriverQueryButton'),
			handler : function () {
				Ext.getCmp('Foss_baseinfo_leasedDriver_QueryForm_Id').getForm().reset();
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
			text : baseinfo.leasedDriver.i18n('foss.baseinfo.query'),
			disabled:!baseinfo.leasedDriver.isPermission('leasedDriver/leasedDriverQueryButton'),
			hidden:!baseinfo.leasedDriver.isPermission('leasedDriver/leasedDriverQueryButton'),
			handler : function () {
				Ext.getCmp('Foss_baseinfo_leasedDriver_LeasedDriverGrid_Id').getPagingToolbar().moveFirst();
			}
		}
	],
	constructor : function (config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

/*******************************************************  定义baseinfo.leasedDriver.i18n('foss.baseinfo.ba.leasedDriver.leasedDriver.58')列表结果窗口 *****************************************************/
/*
 * 定义：一个baseinfo.leasedDriver.i18n('foss.baseinfo.baseinfo-leasedDriver.58')的数据模型"Model"
 */
Ext.define('Foss.baseinfo.leasedDriver.LeasedDriverModel', {
	extend : 'Ext.data.Model',
	idProperty : 'extid',
	idgen : 'uuid',
	fields : [{
			name : 'extid'
		}, {
			name : 'id', //ID标识
			type : 'string'
		}
		/*, {
			name : 'vehicleNo', //司机姓名
			type : 'string'
		}*/
		, {
			name : 'driverName', //司机姓名
			type : 'string'
		}, {
			name : 'driverPhone', //司机电话
			type : 'string'
		}, {
			name : 'idCard', //司机身份证
			type : 'string'
		}, {
			name : 'address', //司机住址
			type : 'string'
		}, {
			name : 'relativeName', //亲属姓名
			type : 'string'
		}, {
			name : 'relativePhone', //亲属电话
			type : 'string'
		}, {
			name : 'driverLicense', //驾驶证
			type : 'string'
		}, {
			name : 'qualification', //从业资格证
			type : 'string'
		}, {
			name : 'licenseType', //驾照类型
			type : 'string'
		}, {
			name : 'beginTime', //驾照签发日期
			type : 'date',
			convert : function (value) {
				if (Ext.isEmpty(value)) {
					return null;
				}
				return new Date(value);
			}
		}, {
			name : 'expirationDate', //有效期限
			type : 'int'
		}, {
			name : 'notes', //备注
			type : 'string'
		}, {
			name : 'status', //状态
			type : 'string'
		}
	]
});

/*
 * 定义：一个baseinfo.leasedDriver.i18n('foss.baseinfo.baseinfo-leasedDriver.58')的查询数据模型"Store"交互后台
 */
Ext.define('Foss.baseinfo.leasedDriver.LeasedDriverStore', {
	extend : 'Ext.data.Store',
	autoLoad : false,
	//页面条数定义
	pageSize : 10,
	//绑定model
	model : 'Foss.baseinfo.leasedDriver.LeasedDriverModel',
	proxy : {
		//以JSON的方式加载数据
		type : 'ajax',
		actionMethods : 'POST',
		url : baseinfo.realPath('queryLeasedDriverList.action'),
		reader : {
			type : 'json',
			root : 'leasedDriverVo.leasedDriverList',
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
			var queryForm = Ext.getCmp('Foss_baseinfo_leasedDriver_QueryForm_Id').getForm();
			if (queryForm != null) {
				var queryParams = queryForm.getValues();
				Ext.apply(operation, {
					params : {
						'leasedDriverVo.leasedDriver.driverName' : queryParams.driverName,
						'leasedDriverVo.leasedDriver.driverPhone' : queryParams.driverPhone,
						'leasedDriverVo.leasedDriver.idCard' : queryParams.idCard
					}
				});
			}
		}
	}
});

/*
 * 定义：baseinfo.leasedDriver.i18n('foss.baseinfo.baseinfo-leasedDriver.58')内嵌在结果列表中的查询详细的窗口表单
 */
Ext.define('Foss.baseinfo.leasedDriver.LeasedDriverDetailForm', {
	extend : 'Ext.form.Panel',
	height : 200,
	defaults : {
		margin : '5 25 5 25',
		labelWidth : 100
	},
	defaultType : 'textfield',
	layout : {
		type : 'table',
		columns : 3
	},
	items : [{
			name : 'driverName',
			fieldLabel : baseinfo.leasedDriver.i18n('foss.baseinfo.LDRDriverName'),
			readOnly : true,
			columnWidth : 0.9
		}, {
			name : 'driverPhone',
			fieldLabel : baseinfo.leasedDriver.i18n('foss.baseinfo.LDRDriverTelphone'),
			readOnly : true,
			columnWidth : 0.9
		}, {
			name : 'idCard',
			fieldLabel : baseinfo.leasedDriver.i18n('foss.baseinfo.LDRDriverIdCard'),
			readOnly : true,
			columnWidth : 0.9
		}, {
			xtype : 'combobox',
			name : 'status',
			fieldLabel : baseinfo.leasedDriver.i18n('foss.baseinfo.LDRDriverStatus'),
			readOnly : true,
			columnWidth : 0.9,
			displayField : 'valueName',
			valueField : 'valueCode',
			queryMode : 'local',
			triggerAction : 'all',
			editable : false,
			store : Ext.create('Foss.baseinfo.leasedDriver.QueryFormParameterStore', {
				data : {
					'items' : [{
							'valueCode' : 'N',
							'valueName' : baseinfo.leasedDriver.i18n('foss.baseinfo.LDRDriverUnavailable')
						}, {
							'valueCode' : 'Y',
							'valueName' : baseinfo.leasedDriver.i18n('foss.baseinfo.LDRDriverAvailable')
						}
					]
				}
			})
		}, {
			xtype : 'label'
		}
		/*, {
			name : 'vehicleNo',
			fieldLabel : baseinfo.leasedDriver.i18n('foss.baseinfo.LDROwnVehicle'),
			readOnly : true,
			columnWidth : 0.9
		}
		*/, {
			name : 'address',
			fieldLabel : baseinfo.leasedDriver.i18n('foss.baseinfo.LDRDriverAddress'),
			readOnly : true,
			columnWidth : 0.9
		}, {
			name : 'relativeName',
			fieldLabel : baseinfo.leasedDriver.i18n('foss.baseinfo.LDRRelativesName'),
			readOnly : true,
			columnWidth : 0.9
		}, {
			name : 'relativePhone',
			fieldLabel : baseinfo.leasedDriver.i18n('foss.baseinfo.LDRRelativesPhone'),
			readOnly : true,
			columnWidth : 0.9
		}, {
			xtype : 'label'
		}, {
			name : 'driverLicense',
			fieldLabel : baseinfo.leasedDriver.i18n('foss.baseinfo.LDRDriverLicenseNumber'),
			readOnly : true,
			columnWidth : 0.9
		}, {
			name : 'qualification',
			fieldLabel : baseinfo.leasedDriver.i18n('foss.baseinfo.LDRQualificationCertificate'),
			readOnly : true,
			columnWidth : 0.9
		}, {
			xtype : 'label'
		}, {
			name : 'licenseType',
			fieldLabel : baseinfo.leasedDriver.i18n('foss.baseinfo.LDRLicenseType'),
			readOnly : true,
			columnWidth : 0.9
		}, {
			name : 'expirationDate',
			fieldLabel : baseinfo.leasedDriver.i18n('foss.baseinfo.LDREffectiveLifeYear'),
			readOnly : true,
			columnWidth : 0.9
		}, {
			xtype : 'datefield',
			name : 'beginTime',
			fieldLabel : baseinfo.leasedDriver.i18n('foss.baseinfo.LDRLicenseIssuedDate'),
			readOnly : true,
			columnWidth : 0.9,
			format : 'Y-m-d H:i:s'
		}, {
			name : 'notes',
			fieldLabel : baseinfo.leasedDriver.i18n('foss.baseinfo.airagencycompany.remark'),
			readOnly : true,
			columnWidth : 0.9,
			colspan : 3,
			width : 550
		}
	]
});

/*
 * 定义：内嵌在结果列表中的查询详细的窗口
 */
Ext.define('Foss.baseinfo.leasedDriver.LeasedDriverDetailPanel', {
	extend : 'Ext.panel.Panel',
	title : baseinfo.leasedDriver.i18n('foss.baseinfo.LDROutSideDriversDetails'),
	frame : true,
	informationForm : null,
	/**
	 * 获取外请司机详细信息
	 * @return {Object} LeasedDriverDetailForm
	 */
	getInformationForm : function () {
		if (this.informationForm == null) {
			this.informationForm = Ext.create('Foss.baseinfo.leasedDriver.LeasedDriverDetailForm');
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
Ext.define('Foss.baseinfo.leasedDriver.LeasedDriverGrid', {
	extend : 'Ext.grid.Panel',
	columnLines : true, // 增加表格列的分割线
	id : 'Foss_baseinfo_leasedDriver_LeasedDriverGrid_Id',
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	frame : true, // 表格对象增加一个边框
	stripeRows : true,
	columnLines : true, // 增加表格列的分割线
	title : baseinfo.leasedDriver.i18n('foss.baseinfo.LDROutSideDriverInformation'), // 定义表格的标题
	collapsible : true,
	animCollapse : true,
	//表格多选插件
	selModel : Ext.create('Ext.selection.CheckboxModel'),
	store : null,
	//表格行可展开的插件
	plugins : [{
			ptype : 'rowexpander',
			rowsExpander : false, // 定义行展开模式（单行与多行），默认是多行展开(值true)
			rowBodyElement : 'Foss.baseinfo.leasedDriver.LeasedDriverDetailPanel' // 行体内容
		}
	],
	addUpdateWindow : null,
	/**
	 * 获取"作废、申请可用、不可用"的窗口的对象
	 * @param {String} windowTitle 窗口的标题
	 * @param {Boolean} isNewApply true：作废，false：申请可用/不可用
	 * @param {String} actionType apply/available/unavailable
	 * @return {Ext.Component} 申请窗口的对象
	 */
	getAddUpdateWindow : function (windowTitle, actionType) {
		var me = this;
		if (this.addUpdateWindow == null) {
			this.addUpdateWindow = Ext.create('Foss.baseinfo.leasedDriver.LeasedDriverAddUpdateWindow');
		}
		this.addUpdateWindow.setOperationUrl((function () {
				var operationUrl = null;
				if (actionType === 'add') {
					operationUrl = baseinfo.realPath('addLeasedDriver.action');
				} else {}
				if (actionType === 'upd') {
					operationUrl = baseinfo.realPath('updateLeasedDriver.action');
				} else {}
				return operationUrl;
			})(actionType));
		this.addUpdateWindow.getAddUpdateForm().setTitle(windowTitle);
		if (actionType && actionType === 'add') {
			me.addUpdateWindow.getAddUpdateForm().getForm().reset();
			me.addUpdateWindow.getAddUpdateForm().getForm().findField('idCard').setReadOnly(false);
		} else {}
		if (actionType && actionType === 'upd') {
			me.addUpdateWindow.getAddUpdateForm().getForm().findField('idCard').setReadOnly(true);
		} else {}
		return this.addUpdateWindow;
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
			text : baseinfo.leasedDriver.i18n('foss.baseinfo.operate'),
			align : 'center',
			items : [{
					iconCls : 'deppon_icons_edit',
					tooltip : baseinfo.leasedDriver.i18n('foss.baseinfo.update'),
					disabled:!baseinfo.leasedDriver.isPermission('leasedDriver/leasedDriverUpdateButton'),
					/**
					 * baseinfo.leasedDriver.i18n('foss.baseinfo.update')响应事件
					 * @param {Object} grid 当前表格
					 * @param {Number} rowIndex 行索引
					 * @param {Number} colIndex 列索引
					 * @param {Object} grid 当前按钮
					 */
					handler : function (grid, rowIndex, colIndex, item) {
						var record = grid.getStore().getAt(rowIndex);
						var addUpdateWindow = grid.up().getAddUpdateWindow(item.tooltip, 'upd');
						addUpdateWindow.loadAddUpdateForm(record);
						addUpdateWindow.show();
					}
				}, {
					iconCls : 'deppon_icons_delete',
					tooltip : baseinfo.leasedDriver.i18n('foss.baseinfo.void'),
					disabled:!baseinfo.leasedDriver.isPermission('leasedDriver/leasedDriverVoidButton'),
					/**
					 * baseinfo.leasedDriver.i18n('foss.baseinfo.update')响应事件
					 * @param {Object} grid 当前表格
					 * @param {Number} rowIndex 行索引
					 * @param {Number} colIndex 列索引
					 */
					handler : function (grid, rowIndex, colIndex) {
						Ext.MessageBox.show({
							title : baseinfo.leasedDriver.i18n('foss.baseinfo.confirmationPrompt'),
							msg : baseinfo.leasedDriver.i18n('foss.baseinfo.LDRVoidDriverMessage'),
							buttons : Ext.Msg.YESNO,
							icon : Ext.MessageBox.QUESTION,
							fn : function (btn) {
								if (btn == 'yes') {
									//获取结果表格对象
									var record = grid.getStore().getAt(rowIndex);
									Ext.Ajax.request({
										url : baseinfo.realPath('deleteLeasedDriver.action'),
										jsonData : {
											'leasedDriverVo' : {
												'batchIds' : new Array(record.data.id)
											}
										},
										//baseinfo.leasedDriver.i18n('foss.baseinfo.void')成功
										success : function (response) {
											grid.up().getPagingToolbar().moveFirst();
											var json = Ext.decode(response.responseText);
											Ext.MessageBox.show({
												title : baseinfo.leasedDriver.i18n('foss.baseinfo.successPrompt'),
												msg : json.message,
												width : 300,
												buttons : Ext.Msg.OK,
												icon : Ext.MessageBox.INFO
											});
										},
										//baseinfo.leasedDriver.i18n('foss.baseinfo.void')失败
										exception : function (response) {
											var json = Ext.decode(response.responseText);
											Ext.MessageBox.show({
												title : baseinfo.leasedDriver.i18n('foss.baseinfo.failureInformationTips'),
												msg : json.message,
												width : 300,
												buttons : Ext.Msg.OK,
												icon : Ext.MessageBox.WARNING
											});
										}
									});
								}
							}
						});
					}
				}
			]
		}, {
			header : baseinfo.leasedDriver.i18n('foss.baseinfo.LDRDriverName'),
			dataIndex : 'driverName',
			flex : 0.15,
			width : 200
		}, {
			header : baseinfo.leasedDriver.i18n('foss.baseinfo.LDRDriverTelphone'),
			dataIndex : 'driverPhone',
			width : 150
		}, {
			header : baseinfo.leasedDriver.i18n('foss.baseinfo.LDRDeiverIdNumber'),
			dataIndex : 'idCard',
			flex : 0.35,
			width : 250
		}, {
			header : baseinfo.leasedDriver.i18n('foss.baseinfo.LDRQualificationCertificate'),
			dataIndex : 'qualification',
			width : 150
		}, {
			header : baseinfo.leasedDriver.i18n('foss.baseinfo.LDRLicenseType'),
			dataIndex : 'licenseType',
			width : 100
		}
	],
	/**
	 * 构造函数
	 * @param {Object} config 构造函数配置项
	 */
	constructor : function (config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.baseinfo.leasedDriver.LeasedDriverStore');
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
						text : baseinfo.leasedDriver.i18n('foss.baseinfo.add'),
						disabled:!baseinfo.leasedDriver.isPermission('leasedDriver/leasedDriverAddButton'),
						hidden:!baseinfo.leasedDriver.isPermission('leasedDriver/leasedDriverAddButton'),
						width : 80,
						//弹出baseinfo.leasedDriver.i18n('foss.baseinfo.add')的外请司机的窗口
						handler : function (mine) {
							me.getAddUpdateWindow(mine.text, 'add').show();
						}
					}, {
						xtype : 'button',
						text : baseinfo.leasedDriver.i18n('foss.baseinfo.void'),
						disabled:!baseinfo.leasedDriver.isPermission('leasedDriver/leasedDriverVoidButton'),
						hidden:!baseinfo.leasedDriver.isPermission('leasedDriver/leasedDriverVoidButton'),
						width : 80,
						/**
						 * 作废外请司机
						 */
						handler : function () {
							var selectionRecord = me.getSelectionModel().getSelection();
							if (selectionRecord && selectionRecord.length > 0) {
								var ids = new Array();
								for (var i = 0; i < selectionRecord.length; i++) {
									Ext.Array.include(ids, selectionRecord[i].data.id);
								}
								Ext.MessageBox.show({
									title : baseinfo.leasedDriver.i18n('foss.baseinfo.confirmationPrompt'),
									msg : baseinfo.leasedDriver.i18n('foss.baseinfo.LDRVoidDriverMessage'),
									buttons : Ext.Msg.YESNO,
									icon : Ext.MessageBox.QUESTION,
									fn : function (btn) {
										if (btn == 'yes') {
											//获取结果表格对象
											Ext.Ajax.request({
												url : baseinfo.realPath('deleteLeasedDriver.action'),
												jsonData : {
													'leasedDriverVo' : {
														'batchIds' : ids
													}
												},
												//baseinfo.leasedDriver.i18n('foss.baseinfo.void')成功
												success : function (response) {
													me.getPagingToolbar().moveFirst();
													var json = Ext.decode(response.responseText);
													Ext.MessageBox.show({
														title : baseinfo.leasedDriver.i18n('foss.baseinfo.successPrompt'),
														msg : json.message,
														width : 300,
														buttons : Ext.Msg.OK,
														icon : Ext.MessageBox.INFO
													});
												},
												//baseinfo.leasedDriver.i18n('foss.baseinfo.void')失败
												exception : function (response) {
													var json = Ext.decode(response.responseText);
													Ext.MessageBox.show({
														title : baseinfo.leasedDriver.i18n('foss.baseinfo.failureInformationTips'),
														msg : json.message,
														width : 300,
														buttons : Ext.Msg.OK,
														icon : Ext.MessageBox.WARNING
													});
												}
											});
										}
									}
								});
							} else {
								Ext.MessageBox.show({
									title : baseinfo.leasedDriver.i18n('foss.baseinfo.failureInformationTips'),
									msg : baseinfo.leasedDriver.i18n('foss.baseinfo.anyOfTheSelectdRecord'),
									width : 300,
									buttons : Ext.Msg.OK,
									icon : Ext.MessageBox.WARNING
								});
							}
						}
					}
				]
			}
		];
		me.callParent([cfg]);
	}
});

/*****************************************************  定义baseinfo.leasedDriver.i18n('foss.baseinfo.baseinfo-leasedDriver.58')申请窗口 *****************************************************/
/*
 * 定义：baseinfo.leasedDriver.i18n('foss.baseinfo.baseinfo-leasedDriver.58')新增/修改的窗口表单
 */
Ext.define('Foss.baseinfo.leasedDriver.LeasedDriverAddUpdateForm', {
	extend : 'Ext.form.Panel',
	frame : true,
	collapsible : true,
	isSearchComb : true,
	defaults : {
		//margin : '5 25 5 25',
		//labelWidth : 100
	},
	defaultType : 'textfield',
	layout : {
		type : 'table',
		columns : 1
	},
	items : [{
			// Fieldset in Column 1 - collapsible via toggle button
			xtype : 'fieldset',
			columnWidth : 0.5,
			title : baseinfo.leasedDriver.i18n('foss.baseinfo.LDRBasicDriverInformation'),
			collapsible : true,
			defaultType : 'textfield',
			defaults : {
				anchor : '100%',
				margin : '5 25 5 25',
				labelWidth : 100,
				labelStyle : 'text-align: left;'
			},
			layout : {
				type : 'table',
				columns : 3
			},
			items : [{
					name : 'driverName',
					fieldLabel : baseinfo.leasedDriver.i18n('foss.baseinfo.LDRDriverName'),
					allowBlank : false,
					blankText : baseinfo.leasedDriver.i18n('foss.baseinfo.LDRDriverNameCanNotBeEmpty'),
					readOnly : false,
					maxLength : 50,
					columnWidth : 0.5
				}, {
					name : 'driverPhone',
					fieldLabel : baseinfo.leasedDriver.i18n('foss.baseinfo.LDRDriverTelphone'),
					allowBlank : false,
					blankText : baseinfo.leasedDriver.i18n('foss.baseinfo.LDRDriverPhoneCanNotBeEmpty'),
					readOnly : false,
					regex: /(^\d{11}$)/,
					regexText : baseinfo.leasedDriver.i18n('foss.baseinfo.baseinfoTelphoneError'),
					columnWidth : 0.5
				}, {
					name : 'idCard',
					fieldLabel : baseinfo.leasedDriver.i18n('foss.baseinfo.LDRDeiverIdNumber'),
					allowBlank : false,
					blankText : baseinfo.leasedDriver.i18n('foss.baseinfo.LDRDriverIdNumberCanNotEmpty'),
					regex: /(^\d{15}$)|(^\d{17}([0-9]|X)$)/,
					regexText : baseinfo.leasedDriver.i18n('foss.baseinfo.baseinfoIdCardError'),
					maxLength : 50,
					columnWidth : 0.5,
					validateOnBlur : true,
					validateOnChange : true,
					readOnly : false,
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
						blur : function (me, event) {
							if (Ext.isEmpty(Ext.String.trim(me.getValue())) || me.readOnly) {
								me.setValue(null);
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
									//检测baseinfo.leasedDriver.i18n('foss.baseinfo.baseinfo-leasedDriver.57')是否已经存在
									var json = Ext.decode(response.responseText);
									if (Ext.isEmpty(json.leasedDriverVo.leasedDriver)) {
										me.validatorMessage.validity = true;
										me.validatorMessage.message = null;
										me.clearInvalid();
									} else {
										me.validatorMessage.validity = false;
										me.validatorMessage.message = baseinfo.leasedDriver.i18n('foss.baseinfo.LDRIdNumberAlreadyExists');
										me.markInvalid(me.validatorMessage.message);
										Ext.MessageBox.show({
											title : baseinfo.leasedDriver.i18n('foss.baseinfo.failureInformationTips'),
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
										title : baseinfo.leasedDriver.i18n('foss.baseinfo.failureInformationTips'),
										msg : json.message,
										width : 300,
										buttons : Ext.Msg.OK,
										icon : Ext.MessageBox.WARNING
									});
								}
							});
						}
					}
				}, {
					name : 'address',
					fieldLabel : baseinfo.leasedDriver.i18n('foss.baseinfo.LDRDriverAddress'),
					allowBlank : false,
					blankText : baseinfo.leasedDriver.i18n('foss.baseinfo.LDRDriverAddressCanNotEmpty'),
					readOnly : false,
					colspan : 2,
					maxlength : 500,
					width : 560
				}/*, {
					xtype : 'commonleasedvehicleselector',
					status : 'Y',
					name : 'vehicleNo',
					fieldLabel : baseinfo.leasedDriver.i18n('foss.baseinfo.LDROwnVehicle'),
					allowBlank : false,
					blankText : baseinfo.leasedDriver.i18n('foss.baseinfo.LDROwnVehicleCanNotEmpty'),
					readOnly : false
				}*/
			]
		}, {
			// Fieldset in Column 2 - collapsible via toggle button
			xtype : 'fieldset',
			columnWidth : 0.5,
			title : baseinfo.leasedDriver.i18n('foss.baseinfo.LDRDriverKinInformation'),
			collapsible : true,
			defaultType : 'textfield',
			defaults : {
				anchor : '100%',
				margin : '5 25 5 25',
				labelWidth : 100,
				labelStyle : 'text-align: left;'
			},
			layout : {
				type : 'table',
				columns : 3
			},
			items : [{
					name : 'relativeName',
					fieldLabel : baseinfo.leasedDriver.i18n('foss.baseinfo.LDRRelativesName'),
					readOnly : false,
					allowBlank : false,
					blankText : baseinfo.leasedDriver.i18n('foss.baseinfo.LDRRelativesNameCanNotEmpty'),
					columnWidth : 0.5
				}, {
					name : 'relativePhone',
					fieldLabel : baseinfo.leasedDriver.i18n('foss.baseinfo.LDRRelativesPhone'),
					allowBlank : false,
					blankText : baseinfo.leasedDriver.i18n('foss.baseinfo.LDRRelativesPhoneCanNotEmpty'),
					readOnly : false,
					regex: /(^\d{11}$)/,
					regexText : baseinfo.leasedDriver.i18n('foss.baseinfo.baseinfoTelphoneError'),
					columnWidth : 0.5
				}
			]
		}, {
			// Fieldset in Column 3 - collapsible via toggle button
			xtype : 'fieldset',
			columnWidth : 0.5,
			title : baseinfo.leasedDriver.i18n('foss.baseinfo.LDRDriverCertificateInformation'),
			collapsible : true,
			defaultType : 'textfield',
			defaults : {
				anchor : '100%',
				margin : '5 25 5 25',
				labelWidth : 100,
				labelStyle : 'text-align: left;'
			},
			layout : {
				type : 'table',
				columns : 3
			},
			items : [{
					name : 'driverLicense',
					fieldLabel : baseinfo.leasedDriver.i18n('foss.baseinfo.LDRDriverLicenseNumber'),
					allowBlank : false,
					maxLength:100,
					blankText : baseinfo.leasedDriver.i18n('foss.baseinfo.LDRLicenseNumberCanNotEmpty'),
					readOnly : false,
					columnWidth : 0.5
				}, {
					name : 'qualification',
					fieldLabel : baseinfo.leasedDriver.i18n('foss.baseinfo.LDRQualificationCertificate'),
					readOnly : false,
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
						 * 模拟选择器：失去焦点获取外请厢式车信息
						 * @param {Ext.Component} me 当前对象
						 * @param {Ext.EventObject} event 当前触发事件对象
						 */
						blur : function (me, event) {
							if (Ext.isEmpty(Ext.String.trim(me.getValue())) || me.readOnly) {
								me.setValue(null);
								return;
							}
							var formModel = me.up('window').getAddUpdateFormModel();
							if(Ext.String.trim(formModel.get(me.getName())) !== Ext.String.trim(me.getValue())){
								Ext.Ajax.request({
									url : baseinfo.realPath('queryLeasedDriver.action'),
									jsonData : {
										'leasedDriverVo' : {
											'leasedDriver' : {
												'qualification' : me.getValue()
											}
										}
									},
									success : function (response) {
										//检测baseinfo.leasedDriver.i18n('foss.baseinfo.LDRQualificationCertificate')是否已经存在
										me.validatorMessage.validity = true;
										var json = Ext.decode(response.responseText);
										var leasedDriver = json.leasedDriverVo.leasedDriver;
										//数据"已存在"
										if (!Ext.isEmpty(leasedDriver)) {
											//初始化：错误数据
											me.validatorMessage.validity = false;
											me.validatorMessage.message = baseinfo.leasedDriver.i18n('foss.baseinfo.LDR.QualificationCertificateInCanNotEmpty');
											/*
											 * "修改动作"的特殊验证逻辑
											 */
											if (me.up('window').operationUrl.toString().indexOf('updateLeasedDriver') != -1) {
												if(leasedDriver.id.toString() === formModel.get('id').toString()){
													me.validatorMessage.validity = true;
												}
											}
										}
										/*
										 * 错误信息提示方式
										 */
										if(Ext.isBoolean(me.validatorMessage.validity) && me.validatorMessage.validity){
											me.validatorMessage.validity = true;
											me.validatorMessage.message = null;
											me.clearInvalid();
										}else{
											me.validatorMessage.validity = false;
											me.markInvalid(me.validatorMessage.message);
											Ext.MessageBox.show({
												title : baseinfo.leasedDriver.i18n('foss.baseinfo.failureInformationTips'),
												msg : me.validatorMessage.message,
												width : 300,
												buttons : Ext.Msg.OK,
												icon : Ext.MessageBox.WARNING
											});
										}
										json = null;
										leasedDriver = null;
									},
									exception : function (response) {
										var json = Ext.decode(response.responseText);
										me.validatorMessage.validity = false;
										me.validatorMessage.message = json.message;
										Ext.MessageBox.show({
											title : baseinfo.leasedDriver.i18n('foss.baseinfo.failureInformationTips'),
											msg : json.message,
											width : 300,
											buttons : Ext.Msg.OK,
											icon : Ext.MessageBox.WARNING
										});
									}
								});
							}
							formModel = null;
						}
					}
				}, {
					xtype : 'label'
				}, {
					xtype : 'combobox',
					name : 'licenseType',
					fieldLabel : baseinfo.leasedDriver.i18n('foss.baseinfo.LDRLicenseType'),
					readOnly : false,
					columnWidth : 0.5,
					displayField : 'valueName',
					valueField : 'valueCode',
					queryMode : 'local',
					triggerAction : 'all',
					editable : false,
					store : Ext.create('Foss.baseinfo.leasedDriver.QueryFormParameterStore', {
						data : {
							'items' : [{
									'valueCode' : 'A1',
									'valueName' : 'A1'
								}, {
									'valueCode' : 'A2',
									'valueName' : 'A2'
								}, {
									'valueCode' : 'A3',
									'valueName' : 'A3'
								}, {
									'valueCode' : 'B1',
									'valueName' : 'B1'
								}, {
									'valueCode' : 'B2',
									'valueName' : 'B2'
								}, {
									'valueCode' : 'C1',
									'valueName' : 'C1'
								}, {
									'valueCode' : 'C2',
									'valueName' : 'C2'
								}, {
									'valueCode' : 'C3',
									'valueName' : 'C3'
								}, {
									'valueCode' : 'C4',
									'valueName' : 'C4'
								}
							]
						}
					})
				}, {
					xtype : 'datefield',
					name : 'beginTime',
					readOnly : false,
					fieldLabel : baseinfo.leasedDriver.i18n('foss.baseinfo.LDRLicenseIssuedDate'),
					columnWidth : 0.5,
					format : 'Y-m-d',
					editable : false,
					maxValue : new Date()
				}, {
					xtype : 'numberfield',
					name : 'expirationDate',
					fieldLabel : baseinfo.leasedDriver.i18n('foss.baseinfo.LDREffectiveLifeYear'),
					readOnly : false,
					columnWidth : 0.5,
					anchor : '100%',
					value : 6,
					maxValue : 99,
					minValue : 1
				}
			]
		}, {
			// Fieldset in Column 4 - collapsible via toggle button
			xtype : 'fieldset',
			columnWidth : 0.5,
			title : baseinfo.leasedDriver.i18n('foss.baseinfo.LDRDeiversRemarks'),
			collapsible : true,
			defaultType : 'textfield',
			defaults : {
				anchor : '100%',
				margin : '5 25 5 25',
				labelWidth : 100,
				labelStyle : 'text-align: left;'
			},
			layout : {
				type : 'table',
				columns : 3
			},
			items : [{
					xtype : 'textareafield',
					name : 'notes',
					readOnly : false,
					colspan : 3,
					maxlength : 1000,
					width : 865
				}, {
					name : 'id',
					fieldLabel : 'ID',
					readOnly : true,
					hidden : true,
					columnWidth : 0.5,
					colspan : 3
				}
			]
		}
	],
	constructor : function (config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

/*
 * 定义baseinfo.leasedDriver.i18n('foss.baseinfo.baseinfo-leasedDriver.58')申请的表单窗口
 */
Ext.define('Foss.baseinfo.leasedDriver.LeasedDriverAddUpdateWindow', {
	extend : 'Ext.window.Window',
	title : baseinfo.leasedDriver.i18n('foss.baseinfo.LDRAddModifyDriver'),
	width : 1000,
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	modal : true,
	resizable : false,
	closeAction : 'hide',
	addUpdateForm : null,
	getAddUpdateForm : function () {
		if (null == this.addUpdateForm) {
			this.addUpdateForm = Ext.create('Foss.baseinfo.leasedDriver.LeasedDriverAddUpdateForm');
		}
		return this.addUpdateForm;
	},
	loadAddUpdateForm : function (record) {
		this.getAddUpdateForm().getForm().loadRecord(record);
		this.addUpdateFormModel = record;
	},
	addUpdateFormModel : null,
	getAddUpdateFormModel : function () {
		if (null == this.addUpdateFormModel) {
			this.addUpdateFormModel = Ext.create("Foss.baseinfo.leasedDriver.LeasedDriverModel");
		}
		return this.addUpdateFormModel;
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
			me.getAddUpdateForm(), {
				border : false,
				xtype : 'container',
				layout : 'column',
				margin : '15 0 0 220',
				defaults : {
					margin : '0 0 30 0'
				},
				items : [{
						xtype : 'button',
						columnWidth : .16,
						text : baseinfo.leasedDriver.i18n('foss.baseinfo.cancel'),
						handler : function () {
							me.hide().close();
						}
					}, {
						border : false,
						columnWidth : .10,
						html : '&nbsp;'
					}, {
						xtype : 'button',
						columnWidth : .16,
						text : baseinfo.leasedDriver.i18n('foss.baseinfo.reset'),
						handler : function () {
							if (me.operationUrl.toString().indexOf('updateLeasedDriver') != -1) {
								me.loadAddUpdateForm(me.getAddUpdateFormModel());
							} else {}
							
							if (me.operationUrl.toString().indexOf('addLeasedDriver') != -1) {
								me.getAddUpdateForm().getForm().reset();
							} else {}
						}
					}, {
						border : false,
						columnWidth : .10,
						html : '&nbsp;'
					}, {
						columnWidth : .16,
						xtype : 'button',
						text : baseinfo.leasedDriver.i18n('foss.baseinfo.save'),
						plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin', {
							seconds: 5
						}),
						listeners : {
							click : function (mine, event, options) {
								var addUpdateForm = me.getAddUpdateForm().getForm();
								if (addUpdateForm.isValid()) { //校验FORM是否通过校验
									addUpdateForm.updateRecord(me.getAddUpdateFormModel());
									Ext.Ajax.request({
										url : me.operationUrl.toString(),
										jsonData : {
											'leasedDriverVo' : {
												'leasedDriver' : me.getAddUpdateFormModel().data
											}
										},
										success : function (response) {
											var json = Ext.decode(response.responseText);
											Ext.getCmp('T_baseinfo-leasedDriver_content').getLeasedDriverGrid().store.load();
											Ext.MessageBox.show({
												title : baseinfo.leasedDriver.i18n('foss.baseinfo.successPrompt'),
												msg : json.message,
												width : 300,
												buttons : Ext.Msg.OK,
												callback : function () {
													me.getAddUpdateForm().up('window').hide();
												},
												icon : Ext.MessageBox.INFO
											});
											addUpdateForm = null;
										},
										exception : function (response) {
											var json = Ext.decode(response.responseText);
											Ext.MessageBox.show({
												title : baseinfo.leasedDriver.i18n('foss.baseinfo.failureInformationTips'),
												msg : json.message,
												width : 300,
												buttons : Ext.Msg.OK,
												callback : function () {
													me.getAddUpdateForm().up('window').hide();
												},
												icon : Ext.MessageBox.WARNING
											});
											addUpdateForm = null;
										}
									});
								} else {
									top.Ext.MessageBox.show({
										title : baseinfo.leasedDriver.i18n('foss.baseinfo.failureInformationTips'),
										msg : (function () {
											var message = "<ul>";
											/*
											var idCardError = addUpdateForm.findField('idCard').validatorMessage.message;
											if(!Ext.isEmpty(idCardError)){
											message = "<li>" + addUpdateForm.findField('idCard').validatorMessage.message + "</li>";
											}
											 */
											addUpdateForm.getFields().filterBy(function (value) {
												return value.getErrors().length > 0;
											}).each(function (item, index, length) {
												message += "<li>" + item.getErrors() + "</li>";
											});
											return message + "</ul>";
										})(),
										width : 300,
										buttons : Ext.Msg.OK,
										icon : Ext.MessageBox.WARNING
									});
									addUpdateForm = null;
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
			var fielsds = me.getAddUpdateForm().getForm().getFields();
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
	var queryForm = Ext.create('Foss.baseinfo.leasedDriver.QueryForm');
	/*
	 * 创建查询baseinfo.leasedDriver.i18n('foss.baseinfo.baseinfo-leasedDriver.58')结果列表结果窗口
	 */
	var queryResult = Ext.create('Foss.baseinfo.leasedDriver.LeasedDriverGrid');
	/*
	 * 执行页面的初始化布局
	 */
	Ext.getCmp('T_baseinfo-leasedDriver').add(Ext.create('Ext.panel.Panel', {
		id : 'T_baseinfo-leasedDriver_content',
		cls : 'panelContentNToolbar',
		bodyCls : 'panelContentNToolbar-body',
		layout : 'auto',
		//获得查询表单"FORM"
		getQueryForm : function () {
			return queryForm;
		},
		//获得查询baseinfo.leasedDriver.i18n('foss.baseinfo.baseinfo-leasedDriver.58')结果列表结果窗口
		getLeasedDriverGrid : function () {
			return queryResult;
		},
		items : [
			queryForm, //加入查询表单"FORM"
			queryResult //加入结果列表
		]
//		renderTo : 'T_baseinfo-leasedDriver-body'
	}));
});


