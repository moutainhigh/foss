/**
 * @author 100847-foss-GaoPeng
 */
/***************************************************  定义"正单交货人"列表查询条件窗口 *************************************************/
/*
 * 查询条件表单FORM中的KEY/VALUE的参数MAP存储器
 */
Ext.define('Foss.baseinfo.contactWithAirlines.QueryFormParameterStore', {
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
Ext.define('Foss.baseinfo.contactWithAirlines.QueryForm', {
	extend : 'Ext.form.Panel',
	title : baseinfo.contactWithAirlines.i18n('foss.baseinfo.queryCondition'),
	id : 'Foss_baseinfo_contactWithAirlines_QueryForm_Id',
	frame : true,
	collapsible : true,
	layout : {
		type : 'table',
		columns : 2
	},
	defaults : {
		labelSeparator : ':',
		margin : '8 10 8 10',
		anchor : '100%'
	},
	height : 145,
	defaultType : 'textfield',
	layout : 'column', 
	constructor : function (config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.items = [{
			name : 'contactCode',
			fieldLabel : baseinfo.contactWithAirlines.i18n('foss.baseinfo.numberSequence'),
			columnWidth : .50
		},{
			name : 'contact',
			fieldLabel : baseinfo.contactWithAirlines.i18n('foss.baseinfo.deliveryPerson'),
			columnWidth : .50
		},{
			border: 1,
			xtype:'container',
			columnWidth:1,
			colspan:2,
			defaultType:'button',
			layout:'column',
			items:[{
				  text : baseinfo.contactWithAirlines.i18n('foss.baseinfo.reset'), //重置
				  columnWidth:.12,
				  disabled:!baseinfo.contactWithAirlines.isPermission('contactWithAirlines/contactWithAirlinesQueryButton'),
				  hidden:!baseinfo.contactWithAirlines.isPermission('contactWithAirlines/contactWithAirlinesQueryButton'),
				  handler : function () {
						me.getForm().reset();
					}
			  	},{
					xtype:'container',
					border:false,
					html:'&nbsp;',
					columnWidth:.76
				},{
				  text : baseinfo.contactWithAirlines.i18n('foss.baseinfo.query'), //查询
				  columnWidth:.12,
				  cls:'yellow_button',  
				  disabled:!baseinfo.contactWithAirlines.isPermission('contactWithAirlines/contactWithAirlinesQueryButton'),
				  hidden:!baseinfo.contactWithAirlines.isPermission('contactWithAirlines/contactWithAirlinesQueryButton'),
				  handler : function () {
						if (me.getForm().isValid()) {
							me.up().getContactWithAirlinesGrid().getPagingToolbar().moveFirst();
						}
					}
			  	}]
		}];
		me.callParent([cfg]);
	}
});

/*******************************************************  定义"正单交货人"列表结果窗口 *****************************************************/
/*
 * 定义：一个"正单交货人"的数据模型"Model"
 */
Ext.define('Foss.baseinfo.contactWithAirlines.ContactWithAirlinesModel', {
	extend : 'Ext.data.Model',
	idProperty : 'extid',
	idgen : 'uuid',
	fields : [{
			name : 'extid'
		}, {
			name : 'id', //ID标识
			type : 'string'
		}, {
			name : 'contactCode', //编号
			type : 'string'
		}, {
			name : 'company', //单位名称
			type : 'string'
		}, {
			name : 'contact', //交货人
			type : 'string'
		}
	]
});

/*
 * 定义：一个"正单交货人"的查询数据模型"Store"交互后台
 */
Ext.define('Foss.baseinfo.contactWithAirlines.ContactWithAirlinesStore', {
	extend : 'Ext.data.Store',
	autoLoad : false,
	//页面条数定义
	pageSize : 10,
	//绑定model
	model : 'Foss.baseinfo.contactWithAirlines.ContactWithAirlinesModel',
	proxy : {
		//以JSON的方式加载数据
		type : 'ajax',
		actionMethods : 'POST',
		url : baseinfo.realPath('queryContactWithAirlinesList.action'),
		reader : {
			type : 'json',
			root : 'contactWithAirlinesVo.contactWithAirlinesList',
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
			var queryForm = Ext.getCmp('Foss_baseinfo_contactWithAirlines_QueryForm_Id').getForm();
			if (queryForm != null) {
				var queryParams = queryForm.getValues();
				Ext.apply(operation, {
					params : {
						'contactWithAirlinesVo.contactWithAirlines.contactCode' : queryParams.contactCode,
						'contactWithAirlinesVo.contactWithAirlines.contact' : queryParams.contact
					}
				});
			}
		}
	}
});

/*
 * 查询结果列表窗口
 */
Ext.define('Foss.baseinfo.contactWithAirlines.ContactWithAirlinesGrid', {
	extend : 'Ext.grid.Panel',
	columnLines : true, // 增加表格列的分割线
	id : 'Foss_baseinfo_contactWithAirlines_ContactWithAirlinesGrid_Id',
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	frame : true, // 表格对象增加一个边框
	stripeRows : true,
	columnLines : true, // 增加表格列的分割线
	title : baseinfo.contactWithAirlines.i18n('foss.baseinfo.positiveSingleDeliveryPerson'), // 定义表格的标题
	collapsible : true,
	animCollapse : true,
	//表格多选插件
	selModel : Ext.create('Ext.selection.CheckboxModel'),
	store : null,
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
			this.addUpdateWindow = Ext.create('Foss.baseinfo.contactWithAirlines.ContactWithAirlinesAddUpdateWindow');
		}
		this.addUpdateWindow.setOperationUrl((function () {
				var operationUrl = null;
				if (actionType === 'add') {
					operationUrl = baseinfo.realPath('addContactWithAirlines.action');
				} else {}
				if (actionType === 'upd') {
					operationUrl = baseinfo.realPath('updateContactWithAirlines.action');
				} else {}
				return operationUrl;
			})(actionType));
		this.addUpdateWindow.getAddUpdateForm().setTitle(windowTitle);
		if (actionType && actionType === 'add') {
			me.addUpdateWindow.getAddUpdateForm().getForm().reset();
			me.addUpdateWindow.getAddUpdateForm().getForm().findField('contactCode').setReadOnly(false);
		} else {}
		if (actionType && actionType === 'upd') {
			me.addUpdateWindow.getAddUpdateForm().getForm().findField('contactCode').setReadOnly(true);
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
			text : baseinfo.contactWithAirlines.i18n('foss.baseinfo.operate'),
			align : 'center',
			items : [{
					iconCls : 'deppon_icons_edit',
					tooltip : baseinfo.contactWithAirlines.i18n('foss.baseinfo.update'),
					disabled :!baseinfo.contactWithAirlines.isPermission('contactWithAirlines/contactWithAirlinesEditButton'),
					/**
					 * baseinfo.contactWithAirlines.i18n('foss.baseinfo.update')响应事件
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
					tooltip : baseinfo.contactWithAirlines.i18n('foss.baseinfo.void'),
					disabled :!baseinfo.contactWithAirlines.isPermission('contactWithAirlines/contactWithAirlinesVoidButton'),
					/**
					 * baseinfo.contactWithAirlines.i18n('foss.baseinfo.update')响应事件
					 * @param {Object} grid 当前表格
					 * @param {Number} rowIndex 行索引
					 * @param {Number} colIndex 列索引
					 */
					handler : function (grid, rowIndex, colIndex) {
						Ext.MessageBox.show({
							title : baseinfo.contactWithAirlines.i18n('foss.baseinfo.confirmationPrompt'),
							msg : baseinfo.contactWithAirlines.i18n('foss.baseinfo.voidDeliveryPersonMessage'),
							buttons : Ext.Msg.YESNO,
							icon : Ext.MessageBox.QUESTION,
							fn : function (btn) {
								if (btn == 'yes') {
									//获取结果表格对象
									var record = grid.getStore().getAt(rowIndex);
									Ext.Ajax.request({
										url : baseinfo.realPath('deleteContactWithAirlines.action'),
										jsonData : {
											'contactWithAirlinesVo' : {
												'batchIds' : new Array(record.data.id)
											}
										},
										//baseinfo.contactWithAirlines.i18n('foss.baseinfo.void')成功
										success : function (response) {
											grid.up().getPagingToolbar().moveFirst();
											var json = Ext.decode(response.responseText);
											Ext.MessageBox.show({
												title : baseinfo.contactWithAirlines.i18n('foss.baseinfo.successPrompt'),
												msg : json.message,
												width : 300,
												buttons : Ext.Msg.OK,
												icon : Ext.MessageBox.INFO
											});
										},
										//baseinfo.contactWithAirlines.i18n('foss.baseinfo.void')失败
										exception : function (response) {
											var json = Ext.decode(response.responseText);
											Ext.MessageBox.show({
												title : baseinfo.contactWithAirlines.i18n('foss.baseinfo.failureInformationTips'),
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
			header : baseinfo.contactWithAirlines.i18n('foss.baseinfo.numberSequence'),
			dataIndex : 'contactCode',
			flex : 0.2,
			width : 250
		}, {
			header : baseinfo.contactWithAirlines.i18n('foss.baseinfo.compayName'),
			dataIndex : 'company',
			flex : 0.2,
			width : 250
		}, {
			header : baseinfo.contactWithAirlines.i18n('foss.baseinfo.deliveryPerson'),
			dataIndex : 'contact',
			flex : 0.25,
			width : 250
		}
	],
	/**
	 * 构造函数
	 * @param {Object} config 构造函数配置项
	 */
	constructor : function (config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.baseinfo.contactWithAirlines.ContactWithAirlinesStore');
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
						text : baseinfo.contactWithAirlines.i18n('foss.baseinfo.add'),
						width : 80,
						disabled:!baseinfo.contactWithAirlines.isPermission('contactWithAirlines/contactWithAirlinesAddButton'),
						hidden:!baseinfo.contactWithAirlines.isPermission('contactWithAirlines/contactWithAirlinesAddButton'),
						//弹出baseinfo.contactWithAirlines.i18n('foss.baseinfo.add')的正单交货人的窗口
						handler : function (mine) {
							me.getAddUpdateWindow(mine.text, 'add').show();
						}
					}, {
						xtype : 'button',
						text : baseinfo.contactWithAirlines.i18n('foss.baseinfo.void'),
						width : 80,
						disabled:!baseinfo.contactWithAirlines.isPermission('contactWithAirlines/contactWithAirlinesVoidButton'),
						hidden:!baseinfo.contactWithAirlines.isPermission('contactWithAirlines/contactWithAirlinesVoidButton'),
						/**
						 * 作废正单交货人
						 */
						handler : function () {
							var selectionRecord = me.getSelectionModel().getSelection();
							if (selectionRecord && selectionRecord.length > 0) {
								var ids = new Array();
								for (var i = 0; i < selectionRecord.length; i++) {
									Ext.Array.include(ids, selectionRecord[i].data.id);
								}
								Ext.MessageBox.show({
									title : baseinfo.contactWithAirlines.i18n('foss.baseinfo.confirmationPrompt'),
									msg : baseinfo.contactWithAirlines.i18n('foss.baseinfo.voidDeliveryPersonMessage'),
									buttons : Ext.Msg.YESNO,
									icon : Ext.MessageBox.QUESTION,
									fn : function (btn) {
										if (btn == 'yes') {
											//获取结果表格对象
											Ext.Ajax.request({
												url : baseinfo.realPath('deleteContactWithAirlines.action'),
												jsonData : {
													'contactWithAirlinesVo' : {
														'batchIds' : ids
													}
												},
												//baseinfo.contactWithAirlines.i18n('foss.baseinfo.void')成功
												success : function (response) {
													me.getPagingToolbar().moveFirst();
													var json = Ext.decode(response.responseText);
													Ext.MessageBox.show({
														title : baseinfo.contactWithAirlines.i18n('foss.baseinfo.successPrompt'),
														msg : json.message,
														width : 300,
														buttons : Ext.Msg.OK,
														icon : Ext.MessageBox.INFO
													});
												},
												//baseinfo.contactWithAirlines.i18n('foss.baseinfo.void')失败
												exception : function (response) {
													var json = Ext.decode(response.responseText);
													Ext.MessageBox.show({
														title : baseinfo.contactWithAirlines.i18n('foss.baseinfo.failureInformationTips'),
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
									title : baseinfo.contactWithAirlines.i18n('foss.baseinfo.failureInformationTips'),
									msg : baseinfo.contactWithAirlines.i18n('foss.baseinfo.anyOfTheSelectdRecord'),
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

/*****************************************************  定义"正单交货人"申请窗口 *****************************************************/
/*
 * 定义："正单交货人"新增/修改的窗口表单
 */
Ext.define('Foss.baseinfo.contactWithAirlines.ContactWithAirlinesAddUpdateForm', {
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
		columns : 1
	},
	validationBusiness : function(businessId, triggerObj, actionType){
		var me = this;
		if(businessId === 'SR-2' && !Ext.isEmpty(triggerObj)){
			var company = me.getForm().findField('company');
			var contact = me.getForm().findField('contact');
			if(!Ext.isEmpty(company) && !Ext.isEmpty(contact)){
				if(Ext.isEmpty(company.getValue()) || Ext.isEmpty(contact.getValue())){
					return;
				}
				Ext.Ajax.request({
					url : baseinfo.realPath('queryContactWithAirlines.action'),
					jsonData : {
						'contactWithAirlinesVo' : {
							'contactWithAirlines' : {
								'company' : company.getValue(),
								'contact' : contact.getValue()
							}
						}
					},
					success : function (response) {
						//检测"正单交货人"是否已经存在
						var json = Ext.decode(response.responseText);
						if (Ext.isEmpty(json.contactWithAirlinesVo.contactWithAirlines)) {
							//单位名称
							company.validatorMessage.validity = true;
							company.validatorMessage.message = null;
							company.clearInvalid();
							//交货人
							contact.validatorMessage.validity = true;
							contact.validatorMessage.message = null;
							contact.clearInvalid();
						} else {
							triggerObj.validatorMessage.validity = false;
							triggerObj.validatorMessage.message = baseinfo.contactWithAirlines.i18n('foss.baseinfo.combinationNameAndDeliveryNotRepearted');
							triggerObj.markInvalid(triggerObj.validatorMessage.message);
							Ext.MessageBox.show({
								title : baseinfo.contactWithAirlines.i18n('foss.baseinfo.failureInformationTips'),
								msg : triggerObj.validatorMessage.message,
								width : 300,
								buttons : Ext.Msg.OK,
								icon : Ext.MessageBox.WARNING
							});
						}
						company = null;
						contact = null;
					},
					exception : function (response) {
						var json = Ext.decode(response.responseText);
						triggerObj.validatorMessage.validity = false;
						triggerObj.validatorMessage.message = json.message;
						Ext.MessageBox.show({
							title : baseinfo.contactWithAirlines.i18n('foss.baseinfo.failureInformationTips'),
							msg : json.message,
							width : 300,
							buttons : Ext.Msg.OK,
							icon : Ext.MessageBox.WARNING
						});
						company = null;
						contact = null;
					}
				});
			}
		}
	},
	items : [{
		name : 'contactCode',
		fieldLabel : baseinfo.contactWithAirlines.i18n('foss.baseinfo.numberSequence'),
		allowBlank : false,
		blankText : baseinfo.contactWithAirlines.i18n('foss.baseinfo.numberSequenceCanNotBeEmpty'),
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
			 * 模拟选择器：失去焦点获取正单交货人信息
			 * @param {Ext.Component} me 当前对象
			 * @param {Ext.EventObject} event 当前触发事件对象
			 */
			blur : function (me, event) {
				if (Ext.isEmpty(Ext.String.trim(me.getValue())) || me.readOnly) {
					me.setValue(null);
					return;
				}
				Ext.Ajax.request({
					url : baseinfo.realPath('queryContactWithAirlines.action'),
					jsonData : {
						'contactWithAirlinesVo' : {
							'contactWithAirlines' : {
								'contactCode' : me.getValue()
							}
						}
					},
					success : function (response) {
						//检测"正单交货人"是否已经存在
						var json = Ext.decode(response.responseText);
						if (Ext.isEmpty(json.contactWithAirlinesVo.contactWithAirlines)) {
							me.validatorMessage.validity = true;
							me.validatorMessage.message = null;
							me.clearInvalid();
						} else {
							me.validatorMessage.validity = false;
							me.validatorMessage.message = baseinfo.contactWithAirlines.i18n('foss.baseinfo.theDeliveryPersonAlreadyExists');
							me.markInvalid(me.validatorMessage.message);
							Ext.MessageBox.show({
								title : baseinfo.contactWithAirlines.i18n('foss.baseinfo.failureInformationTips'),
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
							title : baseinfo.contactWithAirlines.i18n('foss.baseinfo.failureInformationTips'),
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
		name : 'company',
		fieldLabel : baseinfo.contactWithAirlines.i18n('foss.baseinfo.compayName'),
		allowBlank : false,
		blankText : baseinfo.contactWithAirlines.i18n('foss.baseinfo.compayNameCanNotBeEmpty'),
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
			blur : function(me, event){
				me.up().validationBusiness('SR-2', me);
			}
		}
	}, {
		name : 'contact',
		fieldLabel : baseinfo.contactWithAirlines.i18n('foss.baseinfo.deliveryPerson'),
		allowBlank : false,
		blankText : baseinfo.contactWithAirlines.i18n('foss.baseinfo.deliveryPersonCanNotBeEmpty'),
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
			blur : function(me, event){
				me.up().validationBusiness('SR-2', me);
			}
		}
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
 * 定义"正单交货人"申请的表单窗口
 */
Ext.define('Foss.baseinfo.contactWithAirlines.ContactWithAirlinesAddUpdateWindow', {
	extend : 'Ext.window.Window',
	title : baseinfo.contactWithAirlines.i18n('foss.baseinfo.addModifyDeliveryPersonInformation'),
	width : 400,
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	modal : true,
	resizable : false,
	closeAction : 'hide',
	addUpdateForm : null,
	getAddUpdateForm : function () {
		if (null == this.addUpdateForm) {
			this.addUpdateForm = Ext.create('Foss.baseinfo.contactWithAirlines.ContactWithAirlinesAddUpdateForm');
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
			this.addUpdateFormModel = Ext.create("Foss.baseinfo.contactWithAirlines.ContactWithAirlinesModel");
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
						text : baseinfo.contactWithAirlines.i18n('foss.baseinfo.cancel'),
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
						text : baseinfo.contactWithAirlines.i18n('foss.baseinfo.reset'),
						handler : function () {
							if (me.operationUrl.toString().indexOf('updateContactWithAirlines') != -1) {
								me.loadAddUpdateForm(me.getAddUpdateFormModel());
								me.getAddUpdateForm().getForm().getFields().each(function(item, index, length){
									item.clearInvalid();
								});
							} else {}
							
							if (me.operationUrl.toString().indexOf('addContactWithAirlines') != -1) {
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
						text : baseinfo.contactWithAirlines.i18n('foss.baseinfo.save'),
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
											'contactWithAirlinesVo' : {
												'contactWithAirlines' : me.getAddUpdateFormModel().data
											}
										},
										success : function (response) {
											var json = Ext.decode(response.responseText);
											Ext.getCmp('T_baseinfo-contactWithAirlines_content').getContactWithAirlinesGrid().store.load();
											Ext.MessageBox.show({
												title : baseinfo.contactWithAirlines.i18n('foss.baseinfo.successPrompt'),
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
												title : baseinfo.contactWithAirlines.i18n('foss.baseinfo.failureInformationTips'),
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
										title : baseinfo.contactWithAirlines.i18n('foss.baseinfo.failureInformationTips'),
										msg : (function () {
											var messageCollections = new Array();
											var message = "<ul>";
											addUpdateForm.getFields().filterBy(function (value) {
												return value.getErrors().length > 0;
											}).each(function (item, index, length) {
												Ext.Array.each(item.getErrors(), function(item, index, length){
													if(!Ext.Array.contains(messageCollections, item)){
														message += "<li>" + item + "</li>";
														messageCollections.push(item);
													}
												});
											});
											messageCollections = null;
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
	var queryForm = Ext.create('Foss.baseinfo.contactWithAirlines.QueryForm');
	/*
	 * 创建查询"正单交货人"结果列表结果窗口
	 */
	var queryResult = Ext.create('Foss.baseinfo.contactWithAirlines.ContactWithAirlinesGrid');
	/*
	 * 执行页面的初始化布局
	 */
	Ext.getCmp('T_baseinfo-contactWithAirlines').add(Ext.create('Ext.panel.Panel', {
		id : 'T_baseinfo-contactWithAirlines_content',
		cls : 'panelContentNToolbar',
		bodyCls : 'panelContentNToolbar-body',
		layout : 'auto',
		//获得查询表单"FORM"
		getQueryForm : function () {
			return queryForm;
		},
		//获得查询"正单交货人"结果列表结果窗口
		getContactWithAirlinesGrid : function () {
			return queryResult;
		},
		items : [
			queryForm, //加入查询表单"FORM"
			queryResult //加入结果列表
		] 
	}));
});


