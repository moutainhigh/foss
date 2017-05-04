/**
 * @author 100847-foss-GaoPeng
 */
/***************************************************  定义baseinfo.aircraftType.i18n('foss.baseinfo.flightsModels')列表查询条件窗口 *************************************************/
/*
 * 查询条件表单FORM中的KEY/VALUE的参数MAP存储器
 */

Ext.define('Foss.baseinfo.aircraftType.QueryFormParameterStore', {
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
Ext.define('Foss.baseinfo.aircraftType.QueryForm', {
	extend : 'Ext.form.Panel',
	title : baseinfo.aircraftType.i18n('foss.baseinfo.queryCondition'),
	id : 'Foss_baseinfo_aircraftType_QueryForm_Id',
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
			name : 'code',
			fieldLabel : baseinfo.aircraftType.i18n('foss.baseinfo.modelCode'),
			columnWidth : .50
		}, {
			xtype : 'combobox',
			name : 'aircraftSort',
			fieldLabel : baseinfo.aircraftType.i18n('foss.baseinfo.airplaneCategory'),
			columnWidth : .50,
			displayField : 'valueName',
			valueField : 'valueCode',
			queryMode : 'local',
			triggerAction : 'all',
			editable : false,
			store : FossDataDictionary.getDataDictionaryStore('AIRPLANE_TYPE', 'Foss_baseinfo_flight_FlightSortStore_Id', {
				'valueCode' : '',
				'valueName' : baseinfo.aircraftType.i18n('foss.baseinfo.all')
			}),
			value : ''
		}, {
			xtype : 'button',
			columnWidth : .08,
			text : baseinfo.aircraftType.i18n('foss.baseinfo.reset'),
			disabled:!baseinfo.aircraftType.isPermission('aircraftType/aircraftTypeQueryButton'),
			hidden:!baseinfo.aircraftType.isPermission('aircraftType/aircraftTypeQueryButton'),
			handler : function () {
				Ext.getCmp('Foss_baseinfo_aircraftType_QueryForm_Id').getForm().reset();
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
			text : baseinfo.aircraftType.i18n('foss.baseinfo.query'),
			disabled:!baseinfo.aircraftType.isPermission('aircraftType/aircraftTypeQueryButton'),
			hidden:!baseinfo.aircraftType.isPermission('aircraftType/aircraftTypeQueryButton'),
			handler : function () {
				Ext.getCmp('Foss_baseinfo_aircraftType_AircraftTypeGrid_Id').getPagingToolbar().moveFirst();
			}
		}
	],
	constructor : function (config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

/*******************************************************  定义baseinfo.aircraftType.i18n('foss.baseinfo.flightsModels')列表结果窗口 *****************************************************/
/*
 * 定义：一个baseinfo.aircraftType.i18n('foss.baseinfo.flightsModels')的数据模型"Model"
 */
Ext.define('Foss.baseinfo.aircraftType.AircraftTypeModel', {
	extend : 'Ext.data.Model',
	idProperty : 'extid',
	idgen : 'uuid',
	fields : [{
			name : 'extid'
		}, {
			name : 'id', //ID标识
			type : 'string'
		}, {
			name : 'code', //机型代码
			type : 'string'
		}, {
			name : 'aircraftSort', //飞机类别
			type : 'string'
		}, {
			name : 'load', //司载重身份证
			type : 'float'
		}, {
			name : 'volumn', //舱位体积
			type : 'float'
		}, {
			name : 'cabinDoorWidth', //舱门尺寸-宽
			type : 'float'
		}, {
			name : 'cabinDoorHeight', //舱门尺寸-高
			type : 'float'
		}, {
			name : 'singlePieceLimitLen', //单件最大长度
			type : 'float'
		}, {
			name : 'singlePieceLimitWei', //单件最大重量
			type : 'float'
		}, {
			name : 'isLimitLenght', //长度是否应用于所有航空公司
			type : 'string'
		}, {
			name : 'isLimitWeight', //重量是否应用于所有航空公司
			type : 'string'
		}, {
			name : 'active', //是否启用
			type : 'string'
		}
	]
});

/*
 * 定义：一个baseinfo.aircraftType.i18n('foss.baseinfo.flightsModels')的查询数据模型"Store"交互后台
 */
Ext.define('Foss.baseinfo.aircraftType.AircraftTypeStore', {
	extend : 'Ext.data.Store',
	autoLoad : false,
	//页面条数定义
	pageSize : 10,
	//绑定model
	model : 'Foss.baseinfo.aircraftType.AircraftTypeModel',
	proxy : {
		//以JSON的方式加载数据
		type : 'ajax',
		actionMethods : 'POST',
		url : baseinfo.realPath('queryAircraftTypeList.action'),
		reader : {
			type : 'json',
			root : 'aircraftTypeVo.aircraftTypeList',
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
			var queryForm = Ext.getCmp('Foss_baseinfo_aircraftType_QueryForm_Id').getForm();
			if (queryForm != null) {
				var queryParams = queryForm.getValues();
				Ext.apply(operation, {
					params : {
						'aircraftTypeVo.aircraftType.code' : queryParams.code,
						'aircraftTypeVo.aircraftType.aircraftSort' : queryParams.aircraftSort
					}
				});
			}
		}
	}
});

/*
 * 定义：baseinfo.aircraftType.i18n('foss.baseinfo.flightsModels')内嵌在结果列表中的查询详细的窗口表单
 */
Ext.define('Foss.baseinfo.aircraftType.AircraftTypeDetailForm', {
	extend : 'Ext.form.Panel',
	height : 100,
	defaults : {
		margin : '5 25 5 25',
		labelWidth : 175
	},
	defaultType : 'textfield',
	layout : {
		type : 'table',
		columns : 3
	},
	items : [{
			name : 'code',
			fieldLabel : baseinfo.aircraftType.i18n('foss.baseinfo.modelCode'),
			readOnly : true,
			columnWidth : 0.9
		}, {
			xtype : 'combobox',
			name : 'aircraftSort',
			fieldLabel : baseinfo.aircraftType.i18n('foss.baseinfo.airplaneCategory'),
			readOnly : true,
			columnWidth : 0.9,
			displayField : 'valueName',
			valueField : 'valueCode',
			queryMode : 'local',
			triggerAction : 'all',
			editable : false,
			store : FossDataDictionary.getDataDictionaryStore('AIRPLANE_TYPE', 'Foss_baseinfo_flight_FlightSortStore_Id', {
				'valueCode' : '',
				'valueName' : baseinfo.aircraftType.i18n('foss.baseinfo.all')
			})
		}, {
			name : 'load',
			fieldLabel : baseinfo.aircraftType.i18n('foss.baseinfo.loadTons'),
			readOnly : true,
			columnWidth : 0.9
		}, {
			name : 'volumn',
			fieldLabel : baseinfo.aircraftType.i18n('foss.baseinfo.spaceVolumeSquare'),
			readOnly : true,
			columnWidth : 0.9
		}, {
			name : 'cabinDoorWidth',
			fieldLabel : baseinfo.aircraftType.i18n('foss.baseinfo.widthDoorSizeCm'),
			readOnly : true,
			columnWidth : 0.9
		}, {
			name : 'cabinDoorHeight',
			fieldLabel : baseinfo.aircraftType.i18n('foss.baseinfo.heightDoorSizeCm'),
			readOnly : true,
			columnWidth : 0.9
		}, {
			name : 'singlePieceLimitLen',
			fieldLabel : baseinfo.aircraftType.i18n('foss.baseinfo.singleMaximumLengthM'),
			readOnly : true,
			columnWidth : 0.9
		}, {
			name : 'singlePieceLimitWei',
			fieldLabel : baseinfo.aircraftType.i18n('foss.baseinfo.singleMaximumWeightTons'),
			readOnly : true,
			columnWidth : 0.9
		}, {
			xtype : 'label'
		}
	]
});

/*
 * 定义：内嵌在结果列表中的查询详细的窗口
 */
Ext.define('Foss.baseinfo.aircraftType.AircraftTypeDetailPanel', {
	extend : 'Ext.panel.Panel',
	title : baseinfo.aircraftType.i18n('foss.baseinfo.modelsDetails'),
	frame : true,
	informationForm : null,
	/**
	 * 获取机型详细信息
	 * @return {Object} AircraftTypeDetailForm
	 */
	getInformationForm : function () {
		if (this.informationForm == null) {
			this.informationForm = Ext.create('Foss.baseinfo.aircraftType.AircraftTypeDetailForm');
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
Ext.define('Foss.baseinfo.aircraftType.AircraftTypeGrid', {
	extend : 'Ext.grid.Panel',
	columnLines : true, // 增加表格列的分割线
	id : 'Foss_baseinfo_aircraftType_AircraftTypeGrid_Id',
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	frame : true, // 表格对象增加一个边框
	stripeRows : true,
	columnLines : true, // 增加表格列的分割线
	title : baseinfo.aircraftType.i18n('foss.baseinfo.modelInformation'), // 定义表格的标题
	collapsible : true,
	animCollapse : true,
	//表格多选插件
	selModel : Ext.create('Ext.selection.CheckboxModel'),
	store : null,
	//表格行可展开的插件
	plugins : [{
			ptype : 'rowexpander',
			rowsExpander : false, // 定义行展开模式（单行与多行），默认是多行展开(值true)
			rowBodyElement : 'Foss.baseinfo.aircraftType.AircraftTypeDetailPanel' // 行体内容
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
			this.addUpdateWindow = Ext.create('Foss.baseinfo.aircraftType.AircraftTypeAddUpdateWindow');
		}
		this.addUpdateWindow.setOperationUrl((function () {
				var operationUrl = null;
				if (actionType == 'add') {
					baseinfo.aircraftType.actionFlag = 'add';
					operationUrl = baseinfo.realPath('addAircraftType.action');
				} else {}
				if (actionType == 'upd') {
					baseinfo.aircraftType.actionFlag = 'upd';
					operationUrl = baseinfo.realPath('updateAircraftType.action');
				} else {}
				return operationUrl;
			})(actionType));
		this.addUpdateWindow.getAddUpdateForm().setTitle(windowTitle);
		if (actionType && actionType == 'add') {
			me.addUpdateWindow.getAddUpdateForm().getForm().reset();
//			me.addUpdateWindow.getAddUpdateForm().getForm().findField('idCard').setReadOnly(false);
			me.addUpdateWindow.getAddUpdateForm().getForm().findField('code').setReadOnly(false);
		} else {}
		if (actionType && actionType == 'upd') {
//			me.addUpdateWindow.getAddUpdateForm().getForm().findField('idCard').setReadOnly(true);
			me.addUpdateWindow.getAddUpdateForm().getForm().findField('code').setReadOnly(true);
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
			text : baseinfo.aircraftType.i18n('foss.baseinfo.operate'),
			align : 'center',
			items : [{
					iconCls : 'deppon_icons_edit',
					tooltip : baseinfo.aircraftType.i18n('foss.baseinfo.update'),
					disabled :!baseinfo.aircraftType.isPermission('aircraftType/aircraftTypeEditButton'),
					/**
					 * baseinfo.aircraftType.i18n('foss.baseinfo.update')响应事件
					 * @param {Object} grid 当前表格
					 * @param {Number} rowIndex 行索引
					 * @param {Number} colIndex 列索引
					 * @param {Object} grid 当前按钮
					 */
					handler : function (grid, rowIndex, colIndex, item) {
						var record = grid.getStore().getAt(rowIndex);
						var addUpdateWindow = grid.up().getAddUpdateWindow(item.tooltip, 'upd');
						addUpdateWindow.loadAddUpdateForm(record);
						//处理因checkbox提交值不正确问题
						var tempForm = addUpdateWindow.getAddUpdateForm().getForm();
						tempForm.findField('isLimitLenght').setValue(record.get('isLimitLenght') == 'Y' ? true : false);
						tempForm.findField('isLimitWeight').setValue(record.get('isLimitWeight') == 'Y' ? true : false);
						addUpdateWindow.show();
						addUpdateWindow = null;
						tempForm = null;
					}
				}, {
					iconCls : 'deppon_icons_delete',
					tooltip : baseinfo.aircraftType.i18n('foss.baseinfo.void'),
					disabled :!baseinfo.aircraftType.isPermission('aircraftType/aircraftTypeVoidButton'),
					/**
					 * baseinfo.aircraftType.i18n('foss.baseinfo.update')响应事件
					 * @param {Object} grid 当前表格
					 * @param {Number} rowIndex 行索引
					 * @param {Number} colIndex 列索引
					 */
					handler : function (grid, rowIndex, colIndex) {
						Ext.MessageBox.show({
							title : baseinfo.aircraftType.i18n('foss.baseinfo.confirmationPrompt'),
							msg : baseinfo.aircraftType.i18n('foss.baseinfo.confirmationPromptMessage'),
							buttons : Ext.Msg.YESNO,
							icon : Ext.MessageBox.QUESTION,
							fn : function (btn) {
								if (btn == 'yes') {
									//获取结果表格对象
									var record = grid.getStore().getAt(rowIndex);
									Ext.Ajax.request({
										url : baseinfo.realPath('deleteAircraftType.action'),
										jsonData : {
											'aircraftTypeVo' : {
												'batchIds' : new Array(record.data.id)
											}
										},
										//baseinfo.aircraftType.i18n('foss.baseinfo.void')成功
										success : function (response) {
											grid.up().getPagingToolbar().moveFirst();
											var json = Ext.decode(response.responseText);
											Ext.MessageBox.show({
												title : baseinfo.aircraftType.i18n('foss.baseinfo.successPrompt'),
												msg : json.message,
												width : 300,
												buttons : Ext.Msg.OK,
												icon : Ext.MessageBox.INFO
											});
										},
										//baseinfo.aircraftType.i18n('foss.baseinfo.void')失败
										exception : function (response) {
											var json = Ext.decode(response.responseText);
											Ext.MessageBox.show({
												title : baseinfo.aircraftType.i18n('foss.baseinfo.failureInformationTips'),
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
			header : baseinfo.aircraftType.i18n('foss.baseinfo.modelCode'),
			dataIndex : 'code',
			flex : 0.15,
			width : 100
		}, {
			header : baseinfo.aircraftType.i18n('foss.baseinfo.airplaneCategory'),
			dataIndex : 'aircraftSort',
			width : 100,
			renderer : function (value) {
				if (Ext.isEmpty(value)) {
					return '-';
				} 
				if (value === 'airplane_airliner') {
					return baseinfo.aircraftType.i18n('foss.baseinfo.airliner');
				} 
				if (value === 'airplane_freighter') {
					return baseinfo.aircraftType.i18n('foss.baseinfo.freighters');
				}
			}
		}, {
			header : baseinfo.aircraftType.i18n('foss.baseinfo.loadTons'),
			dataIndex : 'load',
			flex : 0.15,
			width : 100
		}, {
			header : baseinfo.aircraftType.i18n('foss.baseinfo.spaceVolume'),
			dataIndex : 'volumn',
			flex : 0.15,
			width : 100
		}, {
			header : baseinfo.aircraftType.i18n('foss.baseinfo.singleLargestWeightKg'),
			dataIndex : 'singlePieceLimitWei',
			flex : 0.15,
			width : 100
		}, {
			header : baseinfo.aircraftType.i18n('foss.baseinfo.singleMaximumLengthM'),
			dataIndex : 'singlePieceLimitLen',
			flex : 0.15,
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
		me.store = Ext.create('Foss.baseinfo.aircraftType.AircraftTypeStore');
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
						text : baseinfo.aircraftType.i18n('foss.baseinfo.add'),
						width : 80,
						disabled:!baseinfo.aircraftType.isPermission('aircraftType/aircraftTypeAddButton'),
						hidden:!baseinfo.aircraftType.isPermission('aircraftType/aircraftTypeAddButton'),
						//弹出baseinfo.aircraftType.i18n('foss.baseinfo.add')的机型的窗口
						handler : function (mine) {
							me.getAddUpdateWindow(mine.text, 'add').show();
						}
					}, {
						xtype : 'button',
						text : baseinfo.aircraftType.i18n('foss.baseinfo.void'),
						width : 80,
						disabled:!baseinfo.aircraftType.isPermission('aircraftType/aircraftTypeVoidButton'),
						hidden:!baseinfo.aircraftType.isPermission('aircraftType/aircraftTypeVoidButton'),
						/**
						 * 作废机型
						 */
						handler : function () {
							var selectionRecord = me.getSelectionModel().getSelection();
							if (selectionRecord && selectionRecord.length > 0) {
								var ids = new Array();
								for (var i = 0; i < selectionRecord.length; i++) {
									Ext.Array.include(ids, selectionRecord[i].data.id);
								}
								Ext.MessageBox.show({
									title : baseinfo.aircraftType.i18n('foss.baseinfo.confirmationPrompt'),
									msg : baseinfo.aircraftType.i18n('foss.baseinfo.confirmationPromptMessage'),
									buttons : Ext.Msg.YESNO,
									icon : Ext.MessageBox.QUESTION,
									fn : function (btn) {
										if (btn == 'yes') {
											//获取结果表格对象
											Ext.Ajax.request({
												url : baseinfo.realPath('deleteAircraftType.action'),
												jsonData : {
													'aircraftTypeVo' : {
														'batchIds' : ids
													}
												},
												//baseinfo.aircraftType.i18n('foss.baseinfo.void')成功
												success : function (response) {
													me.getPagingToolbar().moveFirst();
													var json = Ext.decode(response.responseText);
													Ext.MessageBox.show({
														title : baseinfo.aircraftType.i18n('foss.baseinfo.successPrompt'),
														msg : json.message,
														width : 300,
														buttons : Ext.Msg.OK,
														icon : Ext.MessageBox.INFO
													});
												},
												//baseinfo.aircraftType.i18n('foss.baseinfo.void')失败
												exception : function (response) {
													var json = Ext.decode(response.responseText);
													Ext.MessageBox.show({
														title : baseinfo.aircraftType.i18n('foss.baseinfo.failureInformationTips'),
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
									title : baseinfo.aircraftType.i18n('foss.baseinfo.failureInformationTips'),
									msg : baseinfo.aircraftType.i18n('foss.baseinfo.anyOfTheSelectdRecord'),
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

/*****************************************************  定义baseinfo.aircraftType.i18n('foss.baseinfo.flightsModels')申请窗口 *****************************************************/
/*
 * 定义：baseinfo.aircraftType.i18n('foss.baseinfo.flightsModels')新增/修改的窗口表单
 */
Ext.define('Foss.baseinfo.aircraftType.AircraftTypeAddUpdateForm', {
	extend : 'Ext.form.Panel',
	frame : true,
	collapsible : true,
	isSearchComb : true,
	defaults : {
		margin : '5 25 5 25',
		labelWidth : 175,
		width : 735
	},
	defaultType : 'textfield',
	layout : {
		type : 'table',
		columns : 1
	},
	items : [{
			// Fieldset in Column 1 - collapsible via toggle button
			xtype : 'fieldset',
			title : baseinfo.aircraftType.i18n('foss.baseinfo.modelsBasicInformation'),
			collapsible : true,
			defaultType : 'textfield',
			defaults : {
				anchor : '100%',
				margin : '5 25 5 25',
				labelWidth : 175,
				labelStyle : 'text-align: left;',
				width : 310
			},
			layout : {
				type : 'table',
				columns : 2
			},
			items : [{
					name : 'code',
					fieldLabel : baseinfo.aircraftType.i18n('foss.baseinfo.modelCode'),
					allowBlank : false,
					blankText : baseinfo.aircraftType.i18n('foss.baseinfo.modelCodeCanNotBeEmpty'),
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
						 * 模拟选择器：失去焦点获取机型信息
						 * @param {Ext.Component} me 当前对象
						 * @param {Ext.EventObject} event 当前触发事件对象
						 */
						blur : function (me, event) {
							if(baseinfo.aircraftType.actionFlag == 'add'){
							if (Ext.isEmpty(Ext.String.trim(me.getValue())) || me.readOnly) {
								me.setValue(null);
								return;
							}
							Ext.Ajax.request({
								url : baseinfo.realPath('queryAircraftType.action'),
								jsonData : {
									'aircraftTypeVo' : {
										'aircraftType' : {
											'code' : me.getValue()
										}
									}
								},
								success : function (response) {
									//检测baseinfo.aircraftType.i18n('foss.baseinfo.flightsModels')是否已经存在
									var json = Ext.decode(response.responseText);
									if (Ext.isEmpty(json.aircraftTypeVo.aircraftType)) {
										me.validatorMessage.validity = true;
										me.validatorMessage.message = null;
										me.clearInvalid();
									} else {
										me.validatorMessage.validity = false;
										me.validatorMessage.message = baseinfo.aircraftType.i18n('foss.baseinfo.codeModelAlreadyExists');
										me.markInvalid(me.validatorMessage.message);
										Ext.MessageBox.show({
											title : baseinfo.aircraftType.i18n('foss.baseinfo.failureInformationTips'),
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
										title : baseinfo.aircraftType.i18n('foss.baseinfo.failureInformationTips'),
										msg : json.message,
										width : 300,
										buttons : Ext.Msg.OK,
										icon : Ext.MessageBox.WARNING
									});
								}
							});
						}else{}
						}
					}
				}, {
					xtype : 'combobox',
					name : 'aircraftSort',
					fieldLabel : baseinfo.aircraftType.i18n('foss.baseinfo.airplaneCategory'),
					allowBlank : false,
					blankText : baseinfo.aircraftType.i18n('foss.baseinfo.aircraftTypeCanNotBeEmpty'),
					readOnly : false,
					displayField : 'valueName',
					valueField : 'valueCode',
					queryMode : 'local',
					triggerAction : 'all',
					editable : false,
					store : FossDataDictionary.getDataDictionaryStore('AIRPLANE_TYPE', 'Foss_baseinfo_flight_FlightSortStore_Id')
				}, {
					xtype : 'numberfield',
					name : 'load',
					fieldLabel : baseinfo.aircraftType.i18n('foss.baseinfo.loadTons'),
					allowBlank : false,
					blankText : baseinfo.aircraftType.i18n('foss.baseinfo.loadCanNotBeEmpty'),
					readOnly : false,
					minValue : 1
				}, {
					xtype : 'numberfield',
					name : 'volumn',
					fieldLabel : baseinfo.aircraftType.i18n('foss.baseinfo.spaceVolumeSquare'),
					allowBlank : false,
					blankText : baseinfo.aircraftType.i18n('foss.baseinfo.spaceVolumeCanNotBeEmpty'),
					readOnly : false,
					minValue : 1
				}
			]
		}, {
			// Fieldset in Column 2 - collapsible via toggle button
			xtype : 'fieldset',
			title : baseinfo.aircraftType.i18n('foss.baseinfo.doorSizeCm'),
			collapsible : true,
			defaultType : 'textfield',
			defaults : {
				anchor : '100%',
				margin : '5 25 5 25',
				labelWidth : 175,
				labelStyle : 'text-align: left;',
				width : 310
			},
			layout : {
				type : 'table',
				columns : 2
			},
			items : [{
					xtype : 'numberfield',
					name : 'cabinDoorWidth',
					fieldLabel : baseinfo.aircraftType.i18n('foss.baseinfo.widthDoorSizeCm'),
					readOnly : false,
					allowBlank : false,
					blankText : baseinfo.aircraftType.i18n('foss.baseinfo.wideDoorSizeCanNotBeEmpty'),
					minValue : 1
				}, {
					xtype : 'numberfield',
					name : 'cabinDoorHeight',
					fieldLabel : baseinfo.aircraftType.i18n('foss.baseinfo.heightDoorSizeCm'),
					allowBlank : false,
					blankText : baseinfo.aircraftType.i18n('foss.baseinfo.doorSizeCanNotBeEmpty'),
					readOnly : false,
					minValue : 1
				}
			]
		}, {
			// Fieldset in Column 3 - collapsible via toggle button
			xtype : 'fieldset',
			title : baseinfo.aircraftType.i18n('foss.baseinfo.singleMaximumLengthM'),
			collapsible : true,
			defaultType : 'textfield',
			defaults : {
				anchor : '100%',
				margin : '5 25 5 25',
				labelWidth : 175,
				labelStyle : 'text-align: left;',
				width : 310
			},
			layout : {
				type : 'table',
				columns : 2
			},
			items : [{
					xtype : 'numberfield',
					name : 'singlePieceLimitLen',
					labelWidth : 5,
					labelSeparator : '',
					fieldLabel : '&nbsp;',
					readOnly : false,
					allowBlank : false,
					blankText : baseinfo.aircraftType.i18n('foss.baseinfo.MaximumLenthSinglePieceCanNotBeEmpty'),
					minValue : 0
				}, {
					xtype : 'checkboxfield',
					name : 'isLimitLenght',
					fieldLabel : baseinfo.aircraftType.i18n('foss.baseinfo.whetherForAllAirlines'),
					readOnly : false,
					allowBlank : true,
					inputValue : 'Y'
				}
			]
		}, {
			// Fieldset in Column 4 - collapsible via toggle button
			xtype : 'fieldset',
			title : baseinfo.aircraftType.i18n('foss.baseinfo.singleMaximumWeightTons'),
			collapsible : true,
			defaultType : 'textfield',
			defaults : {
				anchor : '100%',
				margin : '5 25 5 25',
				labelWidth : 175,
				labelStyle : 'text-align: left;',
				width : 310
			},
			layout : {
				type : 'table',
				columns : 2
			},
			items : [{
					xtype : 'numberfield',
					name : 'singlePieceLimitWei',
					labelWidth : 5,
					labelSeparator : '',
					fieldLabel : '&nbsp;',
					readOnly : false,
					allowBlank : false,
					blankText : baseinfo.aircraftType.i18n('foss.baseinfo.maximumWeightSinglePieceCanNotBeEmpty'),
					minValue : 0
				}, {
					xtype : 'checkboxfield',
					name : 'isLimitWeight',
					fieldLabel : baseinfo.aircraftType.i18n('foss.baseinfo.whetherForAllAirlines'),
					readOnly : false,
					allowBlank : true,
					inputValue : 'Y'
				}
			]
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
 * 定义baseinfo.aircraftType.i18n('foss.baseinfo.flightsModels')申请的表单窗口
 */
Ext.define('Foss.baseinfo.aircraftType.AircraftTypeAddUpdateWindow', {
	extend : 'Ext.window.Window',
	title : baseinfo.aircraftType.i18n('foss.baseinfo.addModifyModelInformation'),
	width : 850,
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	modal : true,
	resizable : false,
	closeAction : 'hide',
	addUpdateForm : null,
	getAddUpdateForm : function () {
		if (null == this.addUpdateForm) {
			this.addUpdateForm = Ext.create('Foss.baseinfo.aircraftType.AircraftTypeAddUpdateForm');
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
			this.addUpdateFormModel = Ext.create("Foss.baseinfo.aircraftType.AircraftTypeModel");
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
		if (arguments.length == 0) {
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
						text : baseinfo.aircraftType.i18n('foss.baseinfo.cancel'),
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
						text : baseinfo.aircraftType.i18n('foss.baseinfo.reset'),
						handler : function () {
							if (me.operationUrl.toString().indexOf('updateAircraftType') != -1) {
								me.loadAddUpdateForm(me.getAddUpdateFormModel());
							} else {}
							
							if (me.operationUrl.toString().indexOf('addAircraftType') != -1) {
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
						text : baseinfo.aircraftType.i18n('foss.baseinfo.save'),
						plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin', {
							seconds: 5
						}),
						listeners : {
							click : function (mine, event, options) {
								var addUpdateForm = me.getAddUpdateForm().getForm();
								if (addUpdateForm.isValid()) { //校验FORM是否通过校验
									addUpdateForm.updateRecord(me.getAddUpdateFormModel());
									//处理因checkbox提交值问题
									var tempModel = me.getAddUpdateFormModel();
									var isLimitLenght = tempModel.get('isLimitLenght');
									tempModel.set('isLimitLenght', Ext.isBoolean(isLimitLenght) ? (isLimitLenght ? 'Y' : 'N') : (isLimitLenght) == 'true' ? 'Y' : 'N');
									isLimitLenght = null;
									var isLimitWeight = tempModel.get('isLimitWeight');
									tempModel.set('isLimitWeight', Ext.isBoolean(isLimitWeight) ? (isLimitWeight ? 'Y' : 'N') : (isLimitWeight) == 'true' ? 'Y' : 'N');
									isLimitWeight = null;
									Ext.Ajax.request({
										url : me.operationUrl.toString(),
										jsonData : {
											'aircraftTypeVo' : {
												'aircraftType' : me.getAddUpdateFormModel().data
											}
										},
										success : function (response) {
											var json = Ext.decode(response.responseText);
											Ext.getCmp('T_baseinfo-aircraftType_content').getAircraftTypeGrid().store.load();
											Ext.MessageBox.show({
												title : baseinfo.aircraftType.i18n('foss.baseinfo.successPrompt'),
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
												title : baseinfo.aircraftType.i18n('foss.baseinfo.failureInformationTips'),
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
										title : baseinfo.aircraftType.i18n('foss.baseinfo.failureInformationTips'),
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
	var queryForm = Ext.create('Foss.baseinfo.aircraftType.QueryForm');
	/*
	 * 创建查询baseinfo.aircraftType.i18n('foss.baseinfo.flightsModels')结果列表结果窗口
	 */
	var queryResult = Ext.create('Foss.baseinfo.aircraftType.AircraftTypeGrid');
	/*
	 * 执行页面的初始化布局
	 */
	Ext.getCmp('T_baseinfo-aircraftType').add(Ext.create('Ext.panel.Panel', {
		id : 'T_baseinfo-aircraftType_content',
		cls : 'panelContentNToolbar',
		bodyCls : 'panelContentNToolbar-body',
		layout : 'auto',
		//获得查询表单"FORM"
		getQueryForm : function () {
			return queryForm;
		},
		//获得查询baseinfo.aircraftType.i18n('foss.baseinfo.flightsModels')结果列表结果窗口
		getAircraftTypeGrid : function () {
			return queryResult;
		},
		items : [
			queryForm, //加入查询表单"FORM"
			queryResult //加入结果列表
		]
	}));
});


