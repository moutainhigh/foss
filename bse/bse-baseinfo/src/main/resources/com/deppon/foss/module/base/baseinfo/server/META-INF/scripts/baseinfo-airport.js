/**
 * @author 100847-foss-GaoPeng
 */
/***************************************************  定义"机场"列表查询条件窗口 *************************************************/
/*
 * 查询条件表单FORM中的KEY/VALUE的参数MAP存储器
 */
Ext.define('Foss.baseinfo.airport.QueryFormParameterStore', {
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
Ext.define('Foss.baseinfo.airport.QueryForm', {
	extend : 'Ext.form.Panel',
	title : baseinfo.airport.i18n('foss.baseinfo.queryCondition'),
	id : 'Foss_baseinfo_airport_QueryForm_Id',
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
			xtype : 'commoncityselector',
			name : 'cityCode',
			fieldLabel : baseinfo.airport.i18n('foss.baseinfo.airportCity'),
			columnWidth : .30
		}, {
			name : 'airportCode',
			fieldLabel : baseinfo.airport.i18n('foss.baseinfo.airportCode'),
			columnWidth : .30
		}, {
			name : 'airportName',
			fieldLabel : baseinfo.airport.i18n('foss.baseinfo.airportName'),
			columnWidth : .40
		}, {
			xtype : 'button',
			columnWidth : .08,
			text : baseinfo.airport.i18n('foss.baseinfo.reset'),
			disabled:!baseinfo.airport.isPermission('airport/airportQueryButton'),
			hidden:!baseinfo.airport.isPermission('airport/airportQueryButton'),
			handler : function () {
				Ext.getCmp('Foss_baseinfo_airport_QueryForm_Id').getForm().reset();
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
			text : baseinfo.airport.i18n('foss.baseinfo.query'),
			disabled:!baseinfo.airport.isPermission('airport/airportQueryButton'),
			hidden:!baseinfo.airport.isPermission('airport/airportQueryButton'),
			handler : function () {
				Ext.getCmp('Foss_baseinfo_airport_AirportGrid_Id').getPagingToolbar().moveFirst();
			}
		}
	],
	constructor : function (config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

/*******************************************************  定义"机场"列表结果窗口 *****************************************************/
/*
 * 定义：一个"机场"的数据模型"Model"
 */
Ext.define('Foss.baseinfo.airport.AirportModel', {
	extend : 'Ext.data.Model',
	idProperty : 'extid',
	idgen : 'uuid',
	fields : [{
			name : 'extid'
		}, {
			name : 'id', //ID标识
			type : 'string'
		}, {
			name : 'airportName', //机场名称
			type : 'string'
		}, {
			name : 'airportCode', //机场三字码
			type : 'string'
		}, {
			name : 'cityCode', //机场所在城市
			type : 'string'
		}, {
			name : 'countyCode', //所属区县
			type : 'string'
		}, {
			name : 'contact', //联系人
			type : 'string'
		}, {
			name : 'contactPhone', //联系电话
			type : 'string'
		}, {
			name : 'pickupAddress', //提货地址
			type : 'string'
		}, {
			name : 'notes', //机场描述信息
			type : 'string'
		}, {
			name : 'active', //是否启用
			type : 'string'
		}, {
			name : 'simplename', //中文简称
			type : 'string'
		}, {
			name : 'provCode', //所在省份
			type : 'string'
		}, {
			name : 'pinyin', //中文简称拼音
			type : 'string'
		},
		//扩展DTO的字段
		{
			name : 'airportIntoProvinceName', //机场所在省份名称
			type : 'string'
		}, {
			name : 'airportIntoCityName', //机场所在城市名称
			type : 'string'
		}, {
			name : 'airportIntoAreaName', //机场所在区县名称
			type : 'string'
		}
	]
});

/*
 * 定义：一个"机场"的查询数据模型"Store"交互后台
 */
Ext.define('Foss.baseinfo.airport.AirportStore', {
	extend : 'Ext.data.Store',
	autoLoad : false,
	//页面条数定义
	pageSize : 10,
	//绑定model
	model : 'Foss.baseinfo.airport.AirportModel',
	proxy : {
		//以JSON的方式加载数据
		type : 'ajax',
		actionMethods : 'POST',
		url : baseinfo.realPath('queryAirportList.action'),
		reader : {
			type : 'json',
			root : 'airportVo.airportList',
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
			var queryForm = Ext.getCmp('Foss_baseinfo_airport_QueryForm_Id').getForm();
			if (queryForm != null) {
				var queryParams = queryForm.getValues();
				Ext.apply(operation, {
					params : {
						'airportVo.airport.cityCode' : queryParams.cityCode,
						'airportVo.airport.airportCode' : queryParams.airportCode,
						'airportVo.airport.airportName' : queryParams.airportName
					}
				});
			}
		}
	}
});

/*
 * 定义："机场"内嵌在结果列表中的查询详细的窗口表单
 */
Ext.define('Foss.baseinfo.airport.AirportDetailForm', {
	extend : 'Ext.form.Panel',
	height : 140,
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
			name : 'airportName',
			fieldLabel : baseinfo.airport.i18n('foss.baseinfo.airportName'),
			readOnly : true,
			columnWidth : 0.9
		}, {
			name : 'airportCode',
			fieldLabel : baseinfo.airport.i18n('foss.baseinfo.airportCode'),
			readOnly : true,
			columnWidth : 0.9
		}, {
			name : 'simplename',
			fieldLabel : baseinfo.airport.i18n('foss.baseinfo.airportChineseReferredTo'),
			readOnly : true,
			columnWidth : 0.9
		}, {
			name : 'pinyin',
			fieldLabel : baseinfo.airport.i18n('foss.baseinfo.airportPinyin'),
			readOnly : true,
			columnWidth : 0.9
		}, {
			name : 'airportIntoProvinceName',
			fieldLabel : baseinfo.airport.i18n('foss.baseinfo.airportProvince'),
			readOnly : true,
			columnWidth : 0.9
		}, {
			name : 'airportIntoCityName',
			fieldLabel : baseinfo.airport.i18n('foss.baseinfo.airportCity'),
			readOnly : true,
			columnWidth : 0.9
		}, {
			name : 'contact',
			fieldLabel : baseinfo.airport.i18n('foss.baseinfo.airagencycompany.contact'),
			readOnly : true,
			columnWidth : 0.9
		}, {
			name : 'contactPhone',
			fieldLabel : baseinfo.airport.i18n('foss.baseinfo.airagencycompany.tel'),
			readOnly : true,
			columnWidth : 0.9
		}, {
			name : 'airportIntoAreaName',
			fieldLabel : baseinfo.airport.i18n('foss.baseinfo.airportBelongsCounties'),
			readOnly : true,
			columnWidth : 0.9
		}, {
			name : 'pickupAddress',
			fieldLabel : baseinfo.airport.i18n('foss.baseinfo.airportDeliveryAddress'),
			readOnly : true,
			columnWidth : 0.9,
			colspan : 3,
			width : 550
		}, {
			name : 'notes',
			fieldLabel : baseinfo.airport.i18n('foss.baseinfo.airportDescription'),
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
Ext.define('Foss.baseinfo.airport.AirportDetailPanel', {
	extend : 'Ext.panel.Panel',
	title : baseinfo.airport.i18n('foss.baseinfo.airportDetails'),
	frame : true,
	informationForm : null,
	/**
	 * 获取机场详细信息
	 * @return {Object} AirportDetailForm
	 */
	getInformationForm : function () {
		if (this.informationForm == null) {
			this.informationForm = Ext.create('Foss.baseinfo.airport.AirportDetailForm');
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
Ext.define('Foss.baseinfo.airport.AirportGrid', {
	extend : 'Ext.grid.Panel',
	columnLines : true, // 增加表格列的分割线
	id : 'Foss_baseinfo_airport_AirportGrid_Id',
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	frame : true, // 表格对象增加一个边框
	stripeRows : true,
	columnLines : true, // 增加表格列的分割线
	title : baseinfo.airport.i18n('foss.baseinfo.airportInformation'), // 定义表格的标题
	collapsible : true,
	animCollapse : true,
	//表格多选插件
	selModel : Ext.create('Ext.selection.CheckboxModel'),
	store : null,
	//表格行可展开的插件
	plugins : [{
			ptype : 'rowexpander',
			rowsExpander : false, // 定义行展开模式（单行与多行），默认是多行展开(值true)
			rowBodyElement : 'Foss.baseinfo.airport.AirportDetailPanel' // 行体内容
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
			this.addUpdateWindow = Ext.create('Foss.baseinfo.airport.AirportAddUpdateWindow');
		}
		this.addUpdateWindow.setOperationUrl((function () {
				var operationUrl = null;
				if (actionType === 'add') {
					baseinfo.airport.actionFlag = 'add';
					operationUrl = baseinfo.realPath('addAirport.action');
				} else {}
				if (actionType === 'upd') {
					baseinfo.airport.actionFlag = 'upd';
					operationUrl = baseinfo.realPath('updateAirport.action');
				} else {}
				return operationUrl;
			})(actionType));
		this.addUpdateWindow.getAddUpdateForm().setTitle(windowTitle);
		if (actionType && actionType === 'add') {
			me.addUpdateWindow.getAddUpdateForm().getForm().reset();
			me.addUpdateWindow.getAddUpdateForm().getForm().findField('countyCode').setValue(null);
			me.addUpdateWindow.getAddUpdateForm().getForm().findField('airportName').setReadOnly(false);
			me.addUpdateWindow.getAddUpdateForm().getForm().findField('airportCode').setReadOnly(false);
		} else {}
		if (actionType && actionType == 'upd') {
			me.addUpdateWindow.getAddUpdateForm().getForm().findField('airportName').setReadOnly(true);
			me.addUpdateWindow.getAddUpdateForm().getForm().findField('airportCode').setReadOnly(true);
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
			text : baseinfo.airport.i18n('foss.baseinfo.operate'),
			align : 'center',
			items : [{
					iconCls : 'deppon_icons_edit',
					tooltip : baseinfo.airport.i18n('foss.baseinfo.update'),
					/**
					 * baseinfo.airport.i18n('foss.baseinfo.update')响应事件
					 * @param {Object} grid 当前表格
					 * @param {Number} rowIndex 行索引
					 * @param {Number} colIndex 列索引
					 * @param {Object} grid 当前按钮
					 */
					disabled :!baseinfo.airport.isPermission('airport/airportEditButton'),
					handler : function (grid, rowIndex, colIndex, item) {
						var record = grid.getStore().getAt(rowIndex);
						var addUpdateWindow = grid.up().getAddUpdateWindow(item.tooltip, 'upd');
						addUpdateWindow.loadAddUpdateForm(record);
						addUpdateWindow.show();
					}
				}, {
					iconCls : 'deppon_icons_delete',
					tooltip : baseinfo.airport.i18n('foss.baseinfo.void'),
					disabled :!baseinfo.airport.isPermission('airport/airportVoidButton'),
					/**
					 * baseinfo.airport.i18n('foss.baseinfo.update')响应事件
					 * @param {Object} grid 当前表格
					 * @param {Number} rowIndex 行索引
					 * @param {Number} colIndex 列索引
					 */
					handler : function (grid, rowIndex, colIndex) {
						Ext.MessageBox.show({
							title : baseinfo.airport.i18n('foss.baseinfo.confirmationPrompt'),
							msg : baseinfo.airport.i18n('foss.baseinfo.airportVoidMessage'),
							buttons : Ext.Msg.YESNO,
							icon : Ext.MessageBox.QUESTION,
							fn : function (btn) {
								if (btn == 'yes') {
									//获取结果表格对象
									var record = grid.getStore().getAt(rowIndex);
									Ext.Ajax.request({
										url : baseinfo.realPath('deleteAirport.action'),
										jsonData : {
											'airportVo' : {
												'batchIds' : new Array(record.data.id)
											}
										},
										//baseinfo.airport.i18n('foss.baseinfo.void')成功
										success : function (response) {
											grid.up().getPagingToolbar().moveFirst();
											var json = Ext.decode(response.responseText);
											Ext.MessageBox.show({
												title : baseinfo.airport.i18n('foss.baseinfo.successPrompt'),
												msg : json.message,
												width : 300,
												buttons : Ext.Msg.OK,
												icon : Ext.MessageBox.INFO
											});
										},
										//baseinfo.airport.i18n('foss.baseinfo.void')失败
										exception : function (response) {
											var json = Ext.decode(response.responseText);
											Ext.MessageBox.show({
												title : baseinfo.airport.i18n('foss.baseinfo.failureInformationTips'),
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
			header : baseinfo.airport.i18n('foss.baseinfo.airportName'),
			dataIndex : 'airportName',
			flex : 0.15,
			width : 200
		}, {
			header : baseinfo.airport.i18n('foss.baseinfo.airportCode'),
			dataIndex : 'airportCode',
			flex : 0.15,
			width : 150
		}, {
			header : baseinfo.airport.i18n('foss.baseinfo.airportCity'),
			dataIndex : 'airportIntoCityName',
			flex : 0.15,
			width : 150
		}, {
			header : baseinfo.airport.i18n('foss.baseinfo.airportDeliveryAddress'),
			dataIndex : 'pickupAddress',
			flex : 0.35,
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
		me.store = Ext.create('Foss.baseinfo.airport.AirportStore');
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
						text : baseinfo.airport.i18n('foss.baseinfo.add'),
						width : 80,
						disabled:!baseinfo.airport.isPermission('airport/airportAddButton'),
						hidden:!baseinfo.airport.isPermission('airport/airportAddButton'),
						//弹出baseinfo.airport.i18n('foss.baseinfo.add')的机场的窗口
						handler : function (mine) {
							me.getAddUpdateWindow(mine.text, 'add').show();
						}
					}, {
						xtype : 'button',
						text : baseinfo.airport.i18n('foss.baseinfo.void'),
						width : 80,
						disabled:!baseinfo.airport.isPermission('airport/airportVoidButton'),
						hidden:!baseinfo.airport.isPermission('airport/airportVoidButton'),
						/**
						 * 作废机场
						 */
						handler : function () {
							var selectionRecord = me.getSelectionModel().getSelection();
							if (selectionRecord && selectionRecord.length > 0) {
								var ids = new Array();
								for (var i = 0; i < selectionRecord.length; i++) {
									Ext.Array.include(ids, selectionRecord[i].data.id);
								}
								Ext.MessageBox.show({
									title : baseinfo.airport.i18n('foss.baseinfo.confirmationPrompt'),
									msg : baseinfo.airport.i18n('foss.baseinfo.airportVoidMessage'),
									buttons : Ext.Msg.YESNO,
									icon : Ext.MessageBox.QUESTION,
									fn : function (btn) {
										if (btn == 'yes') {
											//获取结果表格对象
											Ext.Ajax.request({
												url : baseinfo.realPath('deleteAirport.action'),
												jsonData : {
													'airportVo' : {
														'batchIds' : ids
													}
												},
												//baseinfo.airport.i18n('foss.baseinfo.void')成功
												success : function (response) {
													me.getPagingToolbar().moveFirst();
													var json = Ext.decode(response.responseText);
													Ext.MessageBox.show({
														title : baseinfo.airport.i18n('foss.baseinfo.successPrompt'),
														msg : json.message,
														width : 300,
														buttons : Ext.Msg.OK,
														icon : Ext.MessageBox.INFO
													});
												},
												//baseinfo.airport.i18n('foss.baseinfo.void')失败
												exception : function (response) {
													var json = Ext.decode(response.responseText);
													Ext.MessageBox.show({
														title : baseinfo.airport.i18n('foss.baseinfo.failureInformationTips'),
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
									title : baseinfo.airport.i18n('foss.baseinfo.failureInformationTips'),
									msg : baseinfo.airport.i18n('foss.baseinfo.anyOfTheSelectdRecord'),
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

/*****************************************************  定义"机场"申请窗口 *****************************************************/
/*
 * 定义："机场"新增/修改的窗口表单
 */
Ext.define('Foss.baseinfo.airport.AirportAddUpdateForm', {
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
			name : 'airportName',
			fieldLabel : baseinfo.airport.i18n('foss.baseinfo.airportName'),
			allowBlank : false,
			blankText : baseinfo.airport.i18n('foss.baseinfo.airportNameCanNotBeEmtpy'),
			readOnly : false,
			columnWidth : 0.5,
			validateOnBlur : true,
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
					me.validatorMessage.value = Ext.String.trim(me.getValue());
				},
				/**
				 * 模拟选择器：失去焦点获取机场信息
				 * @param {Ext.Component} me 当前对象
				 * @param {Ext.EventObject} event 当前触发事件对象
				 */
				blur : function (me, event) {
					if(baseinfo.airport.actionFlag == 'add'){
					//临时参数
					var isBreak = false;
					var mineVal = Ext.String.trim(me.getValue());
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
						url : baseinfo.realPath('queryAirport.action'),
						jsonData : {
							'airportVo' : {
								'airport' : {
									'airportName' : me.getValue()
								}
							}
						},
						success : function (response) {
							//检测"机场"是否已经存在
							var json = Ext.decode(response.responseText);
							var airport = json.airportVo.airport;
							if (Ext.isEmpty(airport)) {
								me.validatorMessage.validity = true;
							} else {
								me.validatorMessage.validity = false;
								me.validatorMessage.message = baseinfo.airport.i18n('foss.baseinfo.airportTheNameAlreadyExists');
								/*
								 * "修改动作"的特殊验证逻辑
								 */
								if (me.up('window').operationUrl.toString().indexOf('updateAirport') != -1) {
									var formModel = me.up('window').getAddUpdateFormModel();
									if (!Ext.isEmpty(formModel)) {
										if (airport.id.toString() === formModel.get('id').toString()) {
											me.validatorMessage.validity = true;
											me.validatorMessage.message = null;
										}
									}
									formModel = null;
								}
							}
							if (Ext.isBoolean(me.validatorMessage.validity) && me.validatorMessage.validity) {
								me.validatorMessage.validity = true;
								me.validatorMessage.message = null;
								me.clearInvalid();
							} else {
								me.validatorMessage.validity = false;
								me.markInvalid(me.validatorMessage.message);
								Ext.MessageBox.show({
									title : baseinfo.airport.i18n('foss.baseinfo.failureInformationTips'),
									msg : me.validatorMessage.message,
									width : 300,
									buttons : Ext.Msg.OK,
									icon : Ext.MessageBox.WARNING
								});
							}
							json = null;
							airport = null;
							me.validatorMessage.value = null;
						},
						exception : function (response) {
							var json = Ext.decode(response.responseText);
							me.validatorMessage.validity = false;
							me.validatorMessage.message = json.message;
							Ext.MessageBox.show({
								title : baseinfo.airport.i18n('foss.baseinfo.failureInformationTips'),
								msg : json.message,
								width : 300,
								buttons : Ext.Msg.OK,
								icon : Ext.MessageBox.WARNING
							});
						}
					});
				}
			}
		}
		}, {
			name : 'airportCode',
			fieldLabel : baseinfo.airport.i18n('foss.baseinfo.airportCode'),
			allowBlank : false,
			blankText : baseinfo.airport.i18n('foss.baseinfo.airportThreeCodeCanNotBeEmpty'),
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
				message : '',
				value : null
			},
			listeners : {
				focus : function (me, event, eOpts) {
					me.validatorMessage.value = Ext.String.trim(me.getValue());
				},
				/**
				 * 模拟选择器：失去焦点获取机场信息
				 * @param {Ext.Component} me 当前对象
				 * @param {Ext.EventObject} event 当前触发事件对象
				 */
				blur : function (me, event) {
					if(baseinfo.airport.actionFlag == 'add'){
					var isBreak = false;
					var mineVal = Ext.String.trim(me.getValue());
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
					Ext.Ajax.request({
						url : baseinfo.realPath('queryAirport.action'),
						jsonData : {
							'airportVo' : {
								'airport' : {
									'airportCode' : me.getValue()
								}
							}
						},
						success : function (response) {
							//检测"机场"是否已经存在
							var json = Ext.decode(response.responseText);
							var airport = json.airportVo.airport;
							if (Ext.isEmpty(airport)) {
								me.validatorMessage.validity = true;
							} else {
								me.validatorMessage.validity = false;
								me.validatorMessage.message = baseinfo.airport.i18n('foss.baseinfo.airportThreeCodeAlreadyEmtpy');
								/*
								 * "修改动作"的特殊验证逻辑
								 */
								if (me.up('window').operationUrl.toString().indexOf('updateAirport') != -1) {
									var formModel = me.up('window').getAddUpdateFormModel();
									if (!Ext.isEmpty(formModel)) {
										if (airport.id.toString() === formModel.get('id').toString()) {
											me.validatorMessage.validity = true;
											me.validatorMessage.message = null;
										}
									}
									formModel = null;
								}
							}
							if (Ext.isBoolean(me.validatorMessage.validity) && me.validatorMessage.validity) {
								me.validatorMessage.validity = true;
								me.validatorMessage.message = null;
								me.clearInvalid();
							} else {
								me.validatorMessage.validity = false;
								me.markInvalid(me.validatorMessage.message);
								Ext.MessageBox.show({
									title : baseinfo.airport.i18n('foss.baseinfo.failureInformationTips'),
									msg : me.validatorMessage.message,
									width : 300,
									buttons : Ext.Msg.OK,
									icon : Ext.MessageBox.WARNING
								});
							}
							json = null;
							airport = null;
							me.validatorMessage.value = null;
						},
						exception : function (response) {
							var json = Ext.decode(response.responseText);
							me.validatorMessage.validity = false;
							me.validatorMessage.message = json.message;
							Ext.MessageBox.show({
								title : baseinfo.airport.i18n('foss.baseinfo.failureInformationTips'),
								msg : json.message,
								width : 300,
								buttons : Ext.Msg.OK,
								icon : Ext.MessageBox.WARNING
							});
						}
					});
				}
			}
		}
		}, {
			name : 'simplename',
			fieldLabel : baseinfo.airport.i18n('foss.baseinfo.airportChineseReferredTo'),
			allowBlank : false,
			blankText : baseinfo.airport.i18n('foss.baseinfo.airportChineseReferredCanNotBeEmpty'),
			readOnly : false,
			columnWidth : 0.5
		}, {
			name : 'pinyin',
			fieldLabel : baseinfo.airport.i18n('foss.baseinfo.airportPinyin'),
			allowBlank : false,
			blankText : baseinfo.airport.i18n('foss.baseinfo.airportPinyinCanNotBeEmpty'),
			readOnly : false,
			columnWidth : 0.5
		}, {
			xtype : 'commonprovinceselector',
			name : 'provCode',
			fieldLabel : baseinfo.airport.i18n('foss.baseinfo.airportProvince'),
			allowBlank : false,
			blankText : baseinfo.airport.i18n('foss.baseinfo.airportProvinceCanNotBeEmpty'),
			readOnly : false,
			columnWidth : 0.5,
			listeners : {
				select : function (me, records, eOpts) {
					if (Ext.isArray(records) && records.length > 0) {
						var firstIndex = 0;
						var cityCode = me.up().getForm().findField('cityCode');
						if (!Ext.isEmpty(cityCode)) {
							var parentCode = records[firstIndex].data.code;
							if (!Ext.isEmpty(parentCode)) {
								cityCode.store.load({
									start : 0,
									limit : 50,
									params : {
										'cityVo.parentId' : parentCode
									},
									scope : cityCode,
									callback : function (records, operation, success) {
										this.setValue(null);
									}
								});
							}
							parentCode = null;
						}
						cityCode = null;
					}
				},
				beforerender : function (me, eOpts) {
					var airportIntoProvinceName = me.up('window').getAddUpdateFormModel().get('airportIntoProvinceName');
					if (Ext.isEmpty(airportIntoProvinceName) || airportIntoProvinceName === me.getValue()) {
						return;
					}
					me.store.load({
						start : 0,
						limit : 1,
						params : {
							'cityVo.name' : airportIntoProvinceName
						},
						scope : this,
						callback : function (records, operation, success) {
							var m = Ext.create(me.getStore().model.modelName);
							m.set('name', airportIntoProvinceName);
							m.set('code', me.getValue());
							me.getStore().loadRecords([m]);
							me.setValue(me.getValue());
						}
					});
				},
				blur : function (me, eOpts) {
					if (Ext.isEmpty(me.value)) {
						me.setValue(null);
						return;
					}
				}
			}
		}, {
			xtype : 'combobox',
			name : 'cityCode',
			fieldLabel : baseinfo.airport.i18n('foss.baseinfo.airportCity'),
			allowBlank : false,
			blankText : baseinfo.airport.i18n('foss.baseinfo.airportCityCanNotBeEmpty'),
			readOnly : false,
			columnWidth : 0.5,
			triggerAction : 'all',
			editable : false,
			queryMode : 'local',
			displayField : 'name', // 显示名称
			valueField : 'code',
			autoLoad : false,
			store : new Ext.create('Foss.baseinfo.commonSelector.CityStore'),
			listeners : {
				select : function (me, records, eOpts) {
					if (Ext.isArray(records) && records.length > 0) {
						var firstIndex = 0;
						var countyCode = me.up().getForm().findField('countyCode');
						if (!Ext.isEmpty(countyCode)) {
							var parentCode = records[firstIndex].data.code;
							if (!Ext.isEmpty(parentCode)) {
								countyCode.store.load({
									start : 0,
									limit : 50,
									params : {
										'cityVo.parentId' : records[firstIndex].data.code
									},
									scope : countyCode,
									callback : function (records, operation, success) {
										this.setValue(null);
									}
								});
							}
							parentCode = null;
						}
						countyCode = null;
					}
				},
				beforerender : function (me, eOpts) {
					var airportIntoCityName = me.up('window').getAddUpdateFormModel().get('airportIntoCityName');
					if (Ext.isEmpty(airportIntoCityName) || airportIntoCityName === me.getValue()) {
						return;
					}
					me.store.load({
						start : 0,
						limit : 1,
						params : {
							'cityVo.name' : airportIntoCityName
						},
						scope : this,
						callback : function (records, operation, success) {
							var m = Ext.create(me.getStore().model.modelName);
							m.set('name', airportIntoCityName);
							m.set('code', me.getValue());
							me.getStore().loadRecords([m]);
							me.setValue(me.getValue());
						}
					});
				},
				blur : function (me, eOpts) {
					if (Ext.isEmpty(me.value)) {
						me.setValue(null);
					}
				},
				focus : function (me, eOpts) {
					var provCode = me.up().getForm().findField('provCode');
					if (Ext.isEmpty(provCode.getValue()) || Ext.isEmpty(me.getValue())) {
						me.setValue(null);
						return;
					}
				}
			}
		}, {
			name : 'contact',
			fieldLabel : baseinfo.airport.i18n('foss.baseinfo.airagencycompany.contact'),
			readOnly : false,
			columnWidth : 0.5
		}, {
			xtype : 'combobox',
			name : 'countyCode',
			fieldLabel : baseinfo.airport.i18n('foss.baseinfo.airportBelongsCounties'),
			allowBlank : false,
			blankText : baseinfo.airport.i18n('foss.baseinfo.airportBelongsCountiesCanNotBeEmpty'),
			readOnly : false,
			columnWidth : 0.5,
			triggerAction : 'all',
			editable : false,
			queryMode : 'local',
			displayField : 'name', // 显示名称
			valueField : 'code',
			autoLoad : false,
			store : new Ext.create('Foss.baseinfo.commonSelector.AreaStore'),
			listeners : {
				beforerender : function (me, eOpts) {
					var airportIntoAreaName = me.up('window').getAddUpdateFormModel().get('airportIntoAreaName');
					if (Ext.isEmpty(airportIntoAreaName) || airportIntoAreaName === me.getValue()) {
						return;
					}
					me.store.load({
						start : 0,
						limit : 1,
						params : {
							'cityVo.name' : airportIntoAreaName
						},
						scope : this,
						callback : function (records, operation, success) {
							var m = Ext.create(me.getStore().model.modelName);
							m.set('name', airportIntoAreaName);
							m.set('code', me.getValue());
							me.getStore().loadRecords([m]);
							me.setValue(me.getValue());
						}
					});
				},
				focus : function (me, eOpts) {
					var cityCode = me.up().getForm().findField('cityCode');
					if (Ext.isEmpty(cityCode.getValue()) || Ext.isEmpty(me.getValue())) {
						me.setValue(null);
						return;
					}
				}
			}
		}, {
			name : 'contactPhone',
			fieldLabel : baseinfo.airport.i18n('foss.baseinfo.airagencycompany.tel'),
			readOnly : false,
			columnWidth : 0.5,
			colspan : 2
		}, {
			xtype : 'textareafield',
			name : 'pickupAddress',
			fieldLabel : baseinfo.airport.i18n('foss.baseinfo.airportDeliveryAddress'),
			allowBlank : false,
			blankText : baseinfo.airport.i18n('foss.baseinfo.airportDeliveryAddressCanNotBeEmpty'),
			readOnly : false,
			colspan : 2,
			maxlength : 500,
			width : 560
		}, {
			xtype : 'textareafield',
			name : 'notes',
			fieldLabel : baseinfo.airport.i18n('foss.baseinfo.airportDescription'),
			readOnly : false,
			colspan : 2,
			maxlength : 500,
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
 * 定义"机场"申请的表单窗口
 */
Ext.define('Foss.baseinfo.airport.AirportAddUpdateWindow', {
	extend : 'Ext.window.Window',
	title : baseinfo.airport.i18n('foss.baseinfo.airportAddModifyInformation'),
	width : 680,
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	modal : true,
	resizable : false,
	closeAction : 'hide',
	addUpdateForm : null,
	getAddUpdateForm : function () {
		if (null == this.addUpdateForm) {
			this.addUpdateForm = Ext.create('Foss.baseinfo.airport.AirportAddUpdateForm');
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
			this.addUpdateFormModel = Ext.create("Foss.baseinfo.airport.AirportModel");
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
						text : baseinfo.airport.i18n('foss.baseinfo.cancel'),
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
						text : baseinfo.airport.i18n('foss.baseinfo.reset'),
						handler : function () {
							if (me.operationUrl.toString().indexOf('updateAirport') != -1) {
								me.loadAddUpdateForm(me.getAddUpdateFormModel());
							}
							if (me.operationUrl.toString().indexOf('addAirport') != -1) {
								me.getAddUpdateForm().getForm().reset();
								//处理公共选择器不兼容问题
								var tempForm = me.getAddUpdateForm().getForm();
								var provCode = tempForm.findField('provCode');
								if (!Ext.isEmpty(provCode)) {
									provCode.getStore().removeAll(true);
								}
								var cityCode = tempForm.findField('cityCode');
								if (!Ext.isEmpty(cityCode)) {
									cityCode.getStore().removeAll(true);
								}
								var countyCode = tempForm.findField('countyCode');
								if (!Ext.isEmpty(countyCode)) {
									countyCode.getStore().removeAll(true);
								}
								tempForm = null;
							}
							me.fireEvent('beforeshow', me);
						}
					}, {
						border : false,
						columnWidth : .10,
						html : '&nbsp;'
					}, {
						columnWidth : .16,
						xtype : 'button',
						plugins : Ext.create('Deppon.ux.ButtonLimitingPlugin', {
							seconds : 5
						}),
						text : baseinfo.airport.i18n('foss.baseinfo.save'),
						listeners : {
							click : function (mine, event, options) {
								var addUpdateForm = me.getAddUpdateForm().getForm();
								if (addUpdateForm.isValid()) { //校验FORM是否通过校验
									addUpdateForm.updateRecord(me.getAddUpdateFormModel());
									Ext.Ajax.request({
										url : me.operationUrl.toString(),
										jsonData : {
											'airportVo' : {
												'airport' : me.getAddUpdateFormModel().data
											}
										},
										success : function (response) {
											var json = Ext.decode(response.responseText);
											Ext.getCmp('T_baseinfo-airport_content').getAirportGrid().store.load();
											Ext.MessageBox.show({
												title : baseinfo.airport.i18n('foss.baseinfo.successPrompt'),
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
												title : baseinfo.airport.i18n('foss.baseinfo.failureInformationTips'),
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
										title : baseinfo.airport.i18n('foss.baseinfo.failureInformationTips'),
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
	var queryForm = Ext.create('Foss.baseinfo.airport.QueryForm');
	/*
	 * 创建查询"机场"结果列表结果窗口
	 */
	var queryResult = Ext.create('Foss.baseinfo.airport.AirportGrid');
	/*
	 * 执行页面的初始化布局
	 */
	Ext.getCmp('T_baseinfo-airport').add(Ext.create('Ext.panel.Panel', {
		id : 'T_baseinfo-airport_content',
		cls : 'panelContentNToolbar',
		bodyCls : 'panelContentNToolbar-body',
		layout : 'auto',
		//获得查询表单"FORM"
		getQueryForm : function () {
			return queryForm;
		},
		//获得查询"机场"结果列表结果窗口
		getAirportGrid : function () {
			return queryResult;
		},
		items : [
			queryForm, //加入查询表单"FORM"
			queryResult //加入结果列表
		]
	}));
});