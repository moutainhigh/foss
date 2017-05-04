/**
 * @author 100847-foss-GaoPeng
 */
/***************************************************  定义（车辆车型）列表查询条件窗口 *************************************************/
/*
 * 查询条件表单FORM中的KEY/VALUE的参数MAP存储器
 */
Ext.define('Foss.baseinfo.leasedVehicleType.QueryFormParameterStore', {
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
			root : 'items' 
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
Ext.define('Foss.baseinfo.leasedVehicleType.QueryForm', {
	extend : 'Ext.form.Panel',
	title : '车辆车型',
	id : 'Foss_baseinfo_leasedVehicleType_QueryForm_Id',
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
			name : 'vehicleLengthCode',
			fieldLabel : '车型编码',
			columnWidth : .50
		}, {
			name : 'vehicleLengthName',
			fieldLabel : '车型名称',
			columnWidth : .50
		}, {
			xtype : 'button',
			columnWidth : .08,
			text : baseinfo.leasedVehicleType.i18n('foss.baseinfo.reset'),
			disabled:!baseinfo.leasedVehicleType.isPermission('leasedVehicleType/leasedVehicleTypeQueryButton'),
			hidden:!baseinfo.leasedVehicleType.isPermission('leasedVehicleType/leasedVehicleTypeQueryButton'),
			handler : function () {
				Ext.getCmp('Foss_baseinfo_leasedVehicleType_QueryForm_Id').getForm().reset();
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
			text : baseinfo.leasedVehicleType.i18n('foss.baseinfo.query'),
			disabled:!baseinfo.leasedVehicleType.isPermission('leasedVehicleType/leasedVehicleTypeQueryButton'),
			hidden:!baseinfo.leasedVehicleType.isPermission('leasedVehicleType/leasedVehicleTypeQueryButton'),
			handler : function () {
				Ext.getCmp('Foss_baseinfo_leasedVehicleType_LeasedVehicleTypeGrid_Id').getPagingToolbar().moveFirst();
			}
		}
	],
	constructor : function (config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

/*******************************************************  定义（车辆车型）列表结果窗口 *****************************************************/
/*
 * 定义：一个（车辆车型）的数据模型"Model"
 */
Ext.define('Foss.baseinfo.leasedVehicleType.LeasedVehicleTypeModel', {
	extend : 'Ext.data.Model',
	idProperty : 'extid',
	idgen : 'uuid',
	fields : [{
			name : 'extid'
		}, {
			name : 'id', // ID标识
			type : 'string'
		}, {
			name : 'seq', // 序号
			type : 'string'
		}, {
			name : 'vehicleLength', // 车型车长
			type : 'float'
		}, {
			name : 'active', // 是否启用
			type : 'string'
		}, {
			name : 'vehicleLengthCode', // 车型编码
			type : 'string'
		}, {
			name : 'eachKilometersFees', // 每公里费用
			type : 'float'
		}, {
			name : 'vehicleLengthName', // 车型名称
			type : 'string'
		}
	]
});

/*
 * 定义：一个（车辆车型）的查询数据模型"Store"交互后台
 */
Ext.define('Foss.baseinfo.leasedVehicleType.LeasedVehicleTypeStore', {
	extend : 'Ext.data.Store',
	autoLoad : false,
	pageSize : 10,
	model : 'Foss.baseinfo.leasedVehicleType.LeasedVehicleTypeModel',
	proxy : {
		type : 'ajax',
		actionMethods : 'POST',
		url : baseinfo.realPath('queryLeasedVehicleTypeList.action'),
		reader : {
			type : 'json',
			root : 'leasedVehicleTypeVo.leasedVehicleTypeList',
			totalProperty : 'totalCount',
			successProperty : 'success'
		}
	},
	constructor : function (config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	},
	listeners : {
		beforeload : function (store, operation, eOpts) {
			var queryForm = Ext.getCmp('Foss_baseinfo_leasedVehicleType_QueryForm_Id').getForm();
			if (queryForm != null) {
				var queryParams = queryForm.getValues();
				Ext.apply(operation, {
					params : {
						'leasedVehicleTypeVo.leasedVehicleType.vehicleLengthCode' : queryParams.vehicleLengthCode,
						'leasedVehicleTypeVo.leasedVehicleType.vehicleLengthName' : queryParams.vehicleLengthName
					}
				});
			}
		}
	}
});

/*
 * 定义：（车辆车型）内嵌在结果列表中的查询详细的窗口表单
 */
Ext.define('Foss.baseinfo.leasedVehicleType.LeasedVehicleTypeDetailForm', {
	extend : 'Ext.form.Panel',
	height : 100,
	defaults : {
		margin : '5 25 5 25',
		labelWidth : 125
	},
	defaultType : 'textfield',
	layout : {
		type : 'table',
		columns : 3
	},
	items : [{
			name : 'seq',
			fieldLabel : baseinfo.leasedVehicleType.i18n('foss.baseinfo.leasedVehicleType.seq'),
			readOnly : true,
			columnWidth : 0.9
		}, {
			name : 'vehicleLengthCode',
			fieldLabel : baseinfo.leasedVehicleType.i18n('foss.baseinfo.leasedVehicleType.vehicleLengthCode'),
			readOnly : true,
			columnWidth : 0.9
		}, {
			name : 'vehicleLengthName',
			fieldLabel : '车型名称',
			readOnly : true,
			columnWidth : 0.9,
			colspan : 3
		}, {
			name : 'vehicleLength',
			fieldLabel : baseinfo.leasedVehicleType.i18n('foss.baseinfo.leasedVehicleType.vehicleLength'),
			readOnly : true,
			columnWidth : 0.9
		}, {
			name : 'eachKilometersFees',
			fieldLabel : baseinfo.leasedVehicleType.i18n('foss.baseinfo.leasedVehicleType.eachKilometersFees'),
			readOnly : true,
			columnWidth : 0.9
		}
	]
});

/*
 * 定义：内嵌在结果列表中的查询详细的窗口
 */
Ext.define('Foss.baseinfo.leasedVehicleType.LeasedVehicleTypeDetailPanel', {
	extend : 'Ext.panel.Panel',
	title : baseinfo.leasedVehicleType.i18n('foss.baseinfo.leasedVehicleType.leasedVehicleTypeDetailPanel'),
	frame : true,
	informationForm : null,
	/**
	 * 获取外请车长车型详细信息
	 *
	 * @return {Object} LeasedVehicleTypeDetailForm
	 */
	getInformationForm : function () {
		if (this.informationForm == null) {
			this.informationForm = Ext.create('Foss.baseinfo.leasedVehicleType.LeasedVehicleTypeDetailForm');
		}
		return this.informationForm;
	},
	constructor : function (config) {
		Ext.apply(this, config);
		this.items = [this.getInformationForm()];
		this.callParent(arguments);
	},
	bindData : function (record) {
		this.getInformationForm().getForm().loadRecord(record);
	}
});

/*
 * 查询结果列表窗口
 */
Ext.define('Foss.baseinfo.leasedVehicleType.LeasedVehicleTypeGrid', {
	extend : 'Ext.grid.Panel',
	columnLines : true, // 增加表格列的分割线
	id : 'Foss_baseinfo_leasedVehicleType_LeasedVehicleTypeGrid_Id',
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	frame : true, // 表格对象增加一个边框
	stripeRows : true,
	columnLines : true, // 增加表格列的分割线
	title : '车辆车型列表',
	collapsible : true,
	animCollapse : true,
	store : null,
	//表格行可展开的插件
	plugins : [{
			ptype : 'rowexpander',
			rowsExpander : false, // 定义行展开模式（单行与多行），默认是多行展开(值true)
			rowBodyElement : 'Foss.baseinfo.leasedVehicleType.LeasedVehicleTypeDetailPanel' // 行体内容
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
			this.addUpdateWindow = Ext.create('Foss.baseinfo.leasedVehicleType.LeasedVehicleTypeAddUpdateWindow');
		}
		this.addUpdateWindow.setOperationUrl((function () {
				var operationUrl = null;
				if (actionType === 'upd') {
					operationUrl = baseinfo.realPath('updateLeasedVehicleType.action');
				}
				return operationUrl;
			})(actionType));
		this.addUpdateWindow.getAddUpdateForm().setTitle(windowTitle);
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
			text : baseinfo.leasedVehicleType.i18n('foss.baseinfo.leasedVan.operator'),
			align : 'center',
			items : [{
					iconCls : 'deppon_icons_edit',
					tooltip : baseinfo.leasedVehicleType.i18n('foss.baseinfo.leasedVan.modify'),
					disabled:!baseinfo.leasedVehicleType.isPermission('leasedVehicleType/leasedVehicleTypeUpdateButton'),
					/**
					 * 响应事件
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
				}
			]
		}, {
			header : baseinfo.leasedVehicleType.i18n('foss.baseinfo.leasedVehicleType.seq'),
			dataIndex : 'seq',
			align : 'center',
			flex : 1
		}, {
			header : baseinfo.leasedVehicleType.i18n('foss.baseinfo.leasedVehicleType.vehicleLengthCode'),
			dataIndex : 'vehicleLengthCode',
			align : 'center',
			flex : 1
		}, {
			header : baseinfo.leasedVehicleType.i18n('foss.baseinfo.leasedVehicleType.vehicleLengthName'),
			dataIndex : 'vehicleLengthName',
			align : 'center',
			flex : 1
		}, {
			header : baseinfo.leasedVehicleType.i18n('foss.baseinfo.leasedVehicleType.eachKilometersFees'),
			align : 'center',
			dataIndex : 'eachKilometersFees',
			flex : 1
		}, {
			header : baseinfo.leasedVehicleType.i18n('foss.baseinfo.vehicle.vehicleLength'),
			align : 'center',
			dataIndex : 'vehicleLength',
			flex : 1
		}
	],
	/**
	 * 构造函数
	 * @param {Object} config 构造函数配置项
	 */
	constructor : function (config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.baseinfo.leasedVehicleType.LeasedVehicleTypeStore');
		me.bbar = me.getPagingToolbar();
		me.getPagingToolbar().store = me.store;
		me.callParent([cfg]);
	}
});

/*****************************************************  定义（车辆车型）申请窗口 *****************************************************/
/*
 * 定义：（车辆车型）新增/修改的窗口表单
 */
Ext.define('Foss.baseinfo.leasedVehicleType.LeasedVehicleTypeAddUpdateForm', {
	extend : 'Ext.form.Panel',
	collapsible : true,
	isSearchComb : true,
	frame : true,
	defaults : {
		margin : '5 5 5 55',
		labelWidth : 120
	},
	defaultType : 'textfield',
	items : [{
			xtype : 'numberfield',
			name : 'seq',
			fieldLabel : baseinfo.leasedVehicleType.i18n('foss.baseinfo.leasedVehicleType.seq'),
			minValue : 0,
			columnWidth : .30,
			allowBlank : false,
			blankText : '序列号不能为空',
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
				message : '',
				value : null
			},
			listeners : {
				focus : function (me, event, eOpts) {
					me.validatorMessage.value = Ext.String.trim(me.getValue().toString()); ;
				},
				/**
				 * 模拟选择器：失去焦点获取外请挂车信息
				 * @param {Ext.Component} me 当前对象
				 * @param {Ext.EventObject} event 当前触发事件对象
				 */
				blur : function (me, event) {
					//临时参数
					var isBreak = false;
					var mineVal = Ext.String.trim(me.getValue().toString());
					if (Ext.isEmpty(mineVal) || me.readOnly) {
						me.setValue(null);
						isBreak = true;
					} else {
						if (Ext.isEmpty(me.validatorMessage.value)) {
							me.validatorMessage.value = mineVal;
						} else {
							if (me.validatorMessage.value === mineVal) {
								isBreak = true;
							} else {
								me.validatorMessage.value = mineVal;
							}
						}
					}
					//判断是否验证是否需要继续
					if (isBreak) {
						return;
					}
					//执行验证
					Ext.Ajax.request({
						url : baseinfo.realPath('queryLeasedVehicleType.action'),
						jsonData : {
							'leasedVehicleTypeVo' : {
								'leasedVehicleType' : {
									'seq' : me.getValue()
								}
							}
						},
						success : function (response) {
							var json = Ext.decode(response.responseText);
							var leasedVehicleType = json.leasedVehicleTypeVo.leasedVehicleType;
							if (Ext.isEmpty(leasedVehicleType)) {
								me.validatorMessage.validity = true;
							} else {
								//初始化：错误数据
								me.validatorMessage.validity = false;
								me.validatorMessage.message = '此序列号（' + me.validatorMessage.value + '）已经存在！';
								/*
								 * "修改动作"的特殊验证逻辑
								 */
								if (me.up('window').operationUrl.toString().indexOf('updateLeasedVehicleType') != -1) {
									var formModel = me.up('window').getAddUpdateFormModel();
									if (!Ext.isEmpty(formModel)) {
										if (leasedVehicleType.id.toString() === formModel.get('id').toString()) {
											me.validatorMessage.validity = true;
											me.validatorMessage.message = null;
										}
									}
									formModel = null;
								}
							}
							/*
							 * 错误信息提示方式
							 */
							if (Ext.isBoolean(me.validatorMessage.validity) && me.validatorMessage.validity) {
								me.validatorMessage.validity = true;
								me.validatorMessage.message = null;
								me.clearInvalid();
							} else {
								me.validatorMessage.validity = false;
								me.markInvalid(me.validatorMessage.message);
								Ext.MessageBox.show({
									title : '信息（失败）提示',
									msg : me.validatorMessage.message,
									width : 300,
									buttons : Ext.Msg.OK,
									icon : Ext.MessageBox.WARNING
								});
							}
							json = null;
							leasedVehicleType = null;
							me.validatorMessage.value = null;
						},
						exception : function (response) {
							var json = Ext.decode(response.responseText);
							me.validatorMessage.validity = false;
							me.validatorMessage.message = json.message;
							me.validatorMessage.value = null;
							Ext.MessageBox.show({
								title : '信息（失败）提示',
								msg : json.message,
								width : 300,
								buttons : Ext.Msg.OK,
								icon : Ext.MessageBox.WARNING
							});
						}
					});
					mineVal = null;
				}
			}
		}, {
			name : 'vehicleLengthCode',
			fieldLabel : baseinfo.leasedVehicleType.i18n('foss.baseinfo.leasedVehicleType.vehicleLengthCode'),
			columnWidth : .30,
			readOnly : true
		}, {
			name : 'vehicleLengthName',
			fieldLabel : baseinfo.leasedVehicleType.i18n('foss.baseinfo.leasedVehicleType.vehicleLengthName'),
			columnWidth : .30,
			readOnly : false
		}, {
			xtype : 'numberfield',
			name : 'vehicleLength',
			fieldLabel : baseinfo.leasedVehicleType.i18n('foss.baseinfo.vehicle.vehicleLength'),
			columnWidth : .30,
			minValue : 1,
			readOnly : true
		}, {
			xtype : 'numberfield',
			name : 'eachKilometersFees',
			fieldLabel : baseinfo.leasedVehicleType.i18n('foss.baseinfo.leasedVehicleType.eachKilometersFees'),
			minValue : 0,
			columnWidth : .30,
			allowBlank : false,
			blankText : baseinfo.leasedVehicleType.i18n('foss.baseinfo.leasedVehicleType.eachKilometersFeesIsNotNull'),
			value : 7.75
		}, {
			name : 'id',
			fieldLabel : 'ID',
			readOnly : true,
			hidden : true,
			columnWidth : 0.5
		}
	],
	constructor : function (config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

/*
 * 定义（车辆车型）申请的表单窗口
 */
Ext.define('Foss.baseinfo.leasedVehicleType.LeasedVehicleTypeAddUpdateWindow', {
	extend : 'Ext.window.Window',
	title : baseinfo.leasedVehicleType.i18n('foss.baseinfo.leasedVehicleType.leasedVehicleTypeAddUpdateWindow'),
	width : 450,
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	modal : true,
	resizable : false,
	closeAction : 'hide',
	addUpdateForm : null,
	getAddUpdateForm : function () {
		if (null == this.addUpdateForm) {
			this.addUpdateForm = Ext.create('Foss.baseinfo.leasedVehicleType.LeasedVehicleTypeAddUpdateForm');
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
			this.addUpdateFormModel = Ext.create("Foss.baseinfo.leasedVehicleType.LeasedVehicleTypeModel");
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
				margin : '15 0 0 100',
				defaults : {
					margin : '0 0 30 0'
				},
				items : [{
						xtype : 'button',
						columnWidth : .16,
						text : baseinfo.leasedVehicleType.i18n('foss.baseinfo.leasedVan.cancel'),
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
						text : baseinfo.leasedVehicleType.i18n('foss.baseinfo.vehicle.resetBtn'),
						handler : function () {
							me.loadAddUpdateForm(me.getAddUpdateFormModel());
							me.fireEvent('beforeshow', me);
							me.getAddUpdateForm().getForm().findField('seq').validatorMessage = {
								validity : true,
								message : '',
								value : null
							};
						}
					}, {
						border : false,
						columnWidth : .10,
						html : '&nbsp;'
					}, {
						columnWidth : .16,
						xtype : 'button',
						cls : 'yellow_button',
						text : baseinfo.leasedVehicleType.i18n('foss.baseinfo.leasedVan.save'),
						plugins : Ext.create('Deppon.ux.ButtonLimitingPlugin', {
							seconds : 5
						}),
						listeners : {
							click : function (mine, event, options) {
								var addUpdateForm = me.getAddUpdateForm().getForm();
								if (addUpdateForm.isValid()) { 
									addUpdateForm.updateRecord(me.getAddUpdateFormModel());
									Ext.Ajax.request({
										url : me.operationUrl.toString(),
										jsonData : {
											'leasedVehicleTypeVo' : {
												'leasedVehicleType' : me.getAddUpdateFormModel().data
											}
										},
										success : function (response) {
											var json = Ext.decode(response.responseText);
											Ext.getCmp('T_baseinfo-leasedVehicleType_content').getLeasedVehicleTypeGrid().store.load();
											Ext.MessageBox.show({
												title : baseinfo.leasedVehicleType.i18n('foss.baseinfo.leasedVan.msgSuccessPrompt'),
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
												title : baseinfo.leasedVehicleType.i18n('foss.baseinfo.leasedVan.msgErrorPrompt'),
												msg : json.message,
												width : 300,
												buttons : Ext.Msg.OK,
												callback : function () {
													//me.getAddUpdateForm().up('window').hide();
												},
												icon : Ext.MessageBox.WARNING
											});
											addUpdateForm = null;
										}
									});
								} else {
									top.Ext.MessageBox.show({
										title : baseinfo.leasedVehicleType.i18n('foss.baseinfo.leasedVan.msgErrorPrompt'),
										msg : (function () {
											var message = "<ul>";
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
		beforeshow : function (me, eOpts) {
			var fielsds = me.getAddUpdateForm().getForm().getFields();
			if (!Ext.isEmpty(fielsds)) {
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
	var queryForm = Ext.create('Foss.baseinfo.leasedVehicleType.QueryForm');
	/*
	 * 创建查询（车辆车型）结果列表结果窗口
	 */
	var queryResult = Ext.create('Foss.baseinfo.leasedVehicleType.LeasedVehicleTypeGrid');
	/*
	 * 执行页面的初始化布局
	 */
	Ext.getCmp('T_baseinfo-leasedVehicleType').add(Ext.create('Ext.panel.Panel', {
		id : 'T_baseinfo-leasedVehicleType_content',
		cls : 'panelContentNToolbar',
		bodyCls : 'panelContentNToolbar-body',
		layout : 'auto',
		//获得查询表单"FORM"
		getQueryForm : function () {
			return queryForm;
		},
		//获得查询（车辆车型）结果列表结果窗口
		getLeasedVehicleTypeGrid : function () {
			return queryResult;
		},
		items : [
			queryForm, //加入查询表单"FORM"
			queryResult //加入结果列表
		]
//		renderTo : 'T_baseinfo-leasedVehicleType-body'
	}));
});
