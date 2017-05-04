/*
 * 转换long类型为日期
 */
baseinfo.changeLongToDate = function (value) {
	if (value != null) {
		var date = new Date(value);
		return date;
	} else {
		return null;
	}
};
/*
 * Ajax请求
 */
baseinfo.requestJsonAjax = function (url, params, successFn, failFn) {
	Ext.Ajax.request({
		url : url,
		jsonData : params,
		success : function (response) {
			var result = Ext.decode(response.responseText);
			if (result.success) {
				successFn(result);
			} else {
				failFn(result);
			}
		},
		failure : function (response) {
			var result = Ext.decode(response.responseText);
			failFn(result);
		},
		exception : function (response) {
			var result = Ext.decode(response.responseText);
			failFn(result);
		}
	});
};

/**
 * 公共方法，通过storeId和model创建STORE
 * @param {Object} storeId
 * @param {Object} model store所用到的model名
 * @param {Object} fields store所用到的fields
 * @param {Object} data
 * @return {Object} 返回创建的store
 */
baseinfo.getStore = function (storeId, model, fields, data) {
	var store = null;
	if (!Ext.isEmpty(storeId)) {
		store = Ext.data.StoreManager.lookup(storeId);
	}
	if (Ext.isEmpty(data)) {
		data = [];
	}
	if (!Ext.isEmpty(model)) {
		if (Ext.isEmpty(store)) {
			store = Ext.create('Ext.data.Store', {
					storeId : storeId,
					model : model,
					data : data
				});
		}
	}
	if (!Ext.isEmpty(fields)) {
		if (Ext.isEmpty(store)) {
			store = Ext.create('Ext.data.Store', {
					storeId : storeId,
					fields : fields,
					data : data
				});
		}
	}
	return store;
};
baseinfo.yes = 'Y'; //是
baseinfo.no = 'N'; //否
baseinfo.ALL = 'ALL'; //全部
//--------------------------------------baseinfo----------------------------------------
//航空公司信息
Ext.define('Foss.baseinfo.AirlinesEntity', {
	extend : 'Ext.data.Model',
	fields : [{
			name : 'id',
			type : 'string'
		}, {
			name : 'name', //航空公司名称
			type : 'string'
		}, {
			name : 'code', //航空公司代码
			type : 'string'
		}, {
			name : 'simpleName', //航空公司简称
			type : 'string'
		}, {
			name : 'prifixName', //航空公司数字前缀
			type : 'string'
		}, {
			name : 'logo', //航空公司LOGO
			type : 'string'
		}, {
			name : 'active', //是否启用
			type : 'string'
		}, {
			name : 'notes', //备注
			type : 'string'
		}
	]
});

//------------------------------------model---------------------------------------------------
/**
 * 航空公司Store（Foss.baseinfo.AirlinesEntity）
 */
Ext.define('Foss.baseinfo.AirlinesStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.baseinfo.AirlinesEntity', //航空公司的MODEL
	pageSize : 20,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url : baseinfo.realPath('queryAirlines.action'), //请求地址
		reader : {
			type : 'json',
			root : 'airlinesVo.airlinesEntityList', //获取的数据
			totalProperty : 'totalCount' //总个数
		}
	}
});

//----------------------------------------store---------------------------------

/**
 * 航空公司表单
 */
Ext.define('Foss.baseinfo.QueryAirlinesForm', {
	extend : 'Ext.form.Panel',
	title : baseinfo.airlines.i18n('foss.baseinfo.queryCondition'), //查询条件
	frame : true,
	collapsible : true,
	layout : {
		type : 'table',
		columns : 2
	},
	defaults : {
		colspan : 1,
		margin : '8 10 5 10'
	},
	height : 150,
	defaultType : 'textfield',
	constructor : function (config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.items = [{
				name : 'name',
				fieldLabel : baseinfo.airlines.i18n('foss.baseinfo.airlines'), //航空公司
				xtype : 'textfield'
			}, {
				name : 'code',
				fieldLabel : baseinfo.airlines.i18n('foss.baseinfo.airlineCode'), //航空公司代码
				xtype : 'textfield'
			},{
				border: 1,
				xtype:'container',
				columnWidth:1,
				colspan:2,
				defaultType:'button',
				layout:'column',
				items:[{
					  text:baseinfo.airlines.i18n('foss.baseinfo.reset'),//重置   
					  columnWidth:.17,
					  disabled:!baseinfo.airlines.isPermission('airlines/airlinesQueryButton'),
					  hidden:!baseinfo.airlines.isPermission('airlines/airlinesQueryButton'),
					  handler : function () {
							me.getForm().reset();
						}
				  	},{
						xtype:'container',
						border:false,
						html:'&nbsp;',
						columnWidth:.66
					},
				  	{
					  text:baseinfo.airlines.i18n('foss.baseinfo.query'),//查询
					  columnWidth:.17,
					  cls:'yellow_button',  
					  disabled:!baseinfo.airlines.isPermission('airlines/airlinesQueryButton'),
					  hidden:!baseinfo.airlines.isPermission('airlines/airlinesQueryButton'),
					  handler : function () {
							if (me.getForm().isValid()) {
								me.up().getAirlinesGrid().getPagingToolbar().moveFirst();
							}
							
						}
				  	}]
			}
		],
		me.callParent([cfg]);
	}
});
/**
 * 航空公司列表
 */
Ext.define('Foss.baseinfo.AirlinesGrid', {
	extend : 'Ext.grid.Panel',
	title : baseinfo.airlines.i18n('foss.baseinfo.airlineInformation'), //航空公司信息
	frame : true,
	flex : 1,
	sortableColumns : false,
	enableColumnHide : false,
	enableColumnMove : false,
	stripeRows : true, // 交替行效果
	selType : "rowmodel", // 选择类型设置为：行选择
	emptyText : baseinfo.airlines.i18n('foss.baseinfo.queryResultIsNull'), //查询结果为空
	//得到bbar
	pagingToolbar : null,
	getPagingToolbar : function () {
		if (this.pagingToolbar == null) {
			this.pagingToolbar = Ext.create('Deppon.StandardPaging', {
					store : this.store,
					pageSize : 20
				});
		}
		return this.pagingToolbar;
	},
	//航空公司新增WINDOW
	airlinesAddWindow : null,
	getAirlinesAddWindow : function () {
		if (this.airlinesAddWindow == null) {
			this.airlinesAddWindow = Ext.create('Foss.baseinfo.AirlinesAddWindow');
			this.airlinesAddWindow.parent = this; //父元素
		}
		return this.airlinesAddWindow;
	},
	//修改航空公司WINDOW
	airlinesUpdateWindow : null,
	getAirlinesUpdateWindow : function () {
		if (this.airlinesUpdateWindow == null) {
			this.airlinesUpdateWindow = Ext.create('Foss.baseinfo.AirlinesUpdateWindow');
			this.airlinesUpdateWindow.parent = this; //父元素
		}
		return this.airlinesUpdateWindow;
	},
	//作废航空公司
	toVoidAirlines : function (btn) {
		var me = this;
		var selections = me.getSelectionModel().getSelection(); //获取选中的数据
		if (selections.length < 1) { //判断是否至少选中了一条
			baseinfo.showWoringMessage(baseinfo.airlines.i18n('foss.baseinfo.selectVoidedOperation')); //请选择一条进行作废操作！
			return; //没有则提示并返回
		}
		baseinfo.showQuestionMes(baseinfo.airlines.i18n('foss.baseinfo.selectVoidedOperationMessage'), function (e) { //是否要作废这些航空公司？
			if (e == 'yes') { //询问是否删除，是则发送请求
				var idsStr = ''; //航空公司ID组成的字符串
				for (var i = 0; i < selections.length; i++) {
					idsStr = idsStr + ',' + selections[i].get('id');
				}
				var params = {
					'airlinesVo' : {
						'idsStr' : idsStr
					}
				};
				var successFun = function (json) {
					baseinfo.showInfoMes(json.message);
					me.getPagingToolbar().moveFirst();
				};
				var failureFun = function (json) {
					if (Ext.isEmpty(json)) {
						baseinfo.showErrorMes(baseinfo.airlines.i18n('foss.baseinfo.requestTimeout')); //请求超时
					} else {
						baseinfo.showErrorMes(json.message);
					}
				};
				var url = baseinfo.realPath('deleteAirlinesByCode.action');
				baseinfo.requestJsonAjax(url, params, successFun, failureFun);
			}
		})
		
	},
	constructor : function (config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.columns = [{
				xtype : 'rownumberer',
				width : 40,
				text : baseinfo.airlines.i18n('foss.baseinfo.noSequence') //序号
			}, {
				text : baseinfo.airlines.i18n('foss.baseinfo.operate'), //操作
				//dataIndex : 'id',
				xtype : 'actioncolumn',
				align : 'center',
				width : 80,
				items : [{
						iconCls : 'deppon_icons_edit',
						tooltip : baseinfo.airlines.i18n('foss.baseinfo.update'), //修改
						width : 42,
						disabled:!baseinfo.airlines.isPermission('airlines/airlinesEditButton'),
						handler : function (grid, rowIndex, colIndex) {
							//获取选中的数据
							var record = grid.getStore().getAt(rowIndex);
							var code = record.get('code'); //航空公司虚拟编码
							var params = {
								'airlinesVo' : {
									'airlinesEntity' : {
										'code' : code
									}
								}
							};
							var successFun = function (json) {
								var updateWindow = me.getAirlinesUpdateWindow(); //获得修改窗口
								updateWindow.airlinesEntity = json.airlinesVo.airlinesEntity; //站点组
								updateWindow.show(); //显示修改窗口
							};
							var failureFun = function (json) {
								if (Ext.isEmpty(json)) {
									baseinfo.showErrorMes(baseinfo.airlines.i18n('foss.baseinfo.requestTimeout')); //请求超时
								} else {
									baseinfo.showErrorMes(json.message);
								}
							};
							var url = baseinfo.realPath('queryAirlineByCode.action');
							baseinfo.requestJsonAjax(url, params, successFun, failureFun);
						}
					}, {
						iconCls : 'deppon_icons_cancel',
						tooltip : baseinfo.airlines.i18n('foss.baseinfo.void'), //作废
						width : 42,
						disabled :!baseinfo.airlines.isPermission('airlines/airlinesVoidButton'),
						handler : function (grid, rowIndex, colIndex) {
							//获取选中的数据
							var record = grid.getStore().getAt(rowIndex);
							baseinfo.showQuestionMes(baseinfo.airlines.i18n('foss.baseinfo.asideYouWantThisAirline'), function (e) { //是否要作废这个航空公司？
								if (e == 'yes') { //询问是否删除，是则发送请求
									var idsStr = record.get('id'); //航空公司ID的字符串
									var params = {
										'airlinesVo' : {
											'idsStr' : idsStr
										}
									};
									var successFun = function (json) {
										baseinfo.showInfoMes(json.message);
										me.getPagingToolbar().moveFirst();
									};
									var failureFun = function (json) {
										if (Ext.isEmpty(json)) {
											baseinfo.showErrorMes(baseinfo.airlines.i18n('foss.baseinfo.requestTimeout')); //请求超时
										} else {
											baseinfo.showErrorMes(json.message);
										}
									};
									var url = baseinfo.realPath('deleteAirlinesByCode.action');
									baseinfo.requestJsonAjax(url, params, successFun, failureFun);
								}
							})
						}
					}
				]
			}, {
				text : baseinfo.airlines.i18n('foss.baseinfo.airlines'), //航空公司
				dataIndex : 'name',
				flex : 1
			}, {
				text : baseinfo.airlines.i18n('foss.baseinfo.airlineCode'), //航空公司代码
				dataIndex : 'code',
				flex : 1
			}, {
				text : baseinfo.airlines.i18n('foss.baseinfo.airlineReferredTo'), //航空公司简称
				dataIndex : 'simpleName',
				flex : 1
			}, {
				text : baseinfo.airlines.i18n('foss.baseinfo.airlineNumericPrefix'), //航空公司数字前缀
				dataIndex : 'prifixName',
				flex : 1
			}
		];
		me.store = Ext.create('Foss.baseinfo.AirlinesStore', {
				autoLoad : false, //不自动加载
				pageSize : 20,
				listeners : {
					beforeload : function (store, operation, eOpts) {
						var queryForm = me.up().getQueryAirlinesForm();
						if (queryForm != null) {
							Ext.apply(operation, {
								params : { //航空公司大查询，查询条件组织
									'airlinesVo.airlinesEntity.name' : queryForm.getForm().findField('name').getValue(), //航空公司名称
									'airlinesVo.airlinesEntity.code' : queryForm.getForm().findField('code').getValue() //航空公司编号
								}
							});
						}
					}
				}
			});
		me.listeners = {
			scrollershow : function (scroller) {
				if (scroller && scroller.scrollEl) {
					scroller.clearManagedListeners();
					scroller.mon(scroller.scrollEl, 'scroll', scroller.onElScroll, scroller);
				}
			}
		},
		me.selModel = Ext.create('Ext.selection.CheckboxModel', { //多选框
				mode : 'MULTI',
				checkOnly : true
			});
		me.tbar = [{
				text : baseinfo.airlines.i18n('foss.baseinfo.add'), //新增
				width : 80,
				disabled:!baseinfo.airlines.isPermission('airlines/airlinesAddButton'),
				hidden:!baseinfo.airlines.isPermission('airlines/airlinesAddButton'),
				handler : function () {
					me.getAirlinesAddWindow().show();
				}
			}, '-', {
				text : baseinfo.airlines.i18n('foss.baseinfo.void'), //作废
				width : 80,
				disabled:!baseinfo.airlines.isPermission('airlines/airlinesVoidButton'),
				hidden:!baseinfo.airlines.isPermission('airlines/airlinesVoidButton'),
				handler : function () {
					me.toVoidAirlines();
				}
			}
		];
		me.bbar = me.getPagingToolbar();
		me.callParent([cfg]);
	}
});

/**
 * @description 航空公司主页
 */
Ext.onReady(function () {
	Ext.QuickTips.init();
	if (Ext.getCmp('T_baseinfo-airlines_content')) {
		return;
	};
	var queryAirlinesForm = Ext.create('Foss.baseinfo.QueryAirlinesForm'); //查询FORM
	var airlinesGrid = Ext.create('Foss.baseinfo.AirlinesGrid'); //查询结果GRID
	Ext.getCmp('T_baseinfo-airlines').add(Ext.create('Ext.panel.Panel', {
		id : 'T_baseinfo-airlines_content',
		cls : 'panelContentNToolbar',
		bodyCls : 'panelContentNToolbar-body',
		//获得查询FORM
		getQueryAirlinesForm : function () {
			return queryAirlinesForm;
		},
		//获得查询结果GRID
		getAirlinesGrid : function () {
			return airlinesGrid;
		},
		items : [queryAirlinesForm, airlinesGrid]
	}));
});
//----------------------------------------------上面是整体布局，下面是弹出窗口----------------------------------
/**
 * 新增航空公司信息
 */
Ext.define('Foss.baseinfo.AirlinesAddWindow', {
	extend : 'Ext.window.Window',
	title : baseinfo.airlines.i18n('foss.baseinfo.newAirline'), //新增航空公司
	closable : true,
	parent : null, //父元素（弹出这个window的gird——Foss.baseinfo.AirlinesGrid）
	modal : true,
	resizable : false, //可以调整窗口的大小
	closeAction : 'hide', //点击关闭是隐藏窗口
	width : 570,
	height : 370,
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	listeners : {
		beforehide : function (me) { //隐藏WINDOW的时候清除数据
			me.getAirlinesForm().getForm().reset(); //表格重置
		},
		beforeshow : function (me) { //显示WINDOW的时候清除数据
			var fielsds = me.getAirlinesForm().getForm().getFields();
			if (!Ext.isEmpty(fielsds)) {
				fielsds.each(function (item, index, length) {
					item.clearInvalid();
					item.unsetActiveError();
				});
			}
			fielsds = null;
		}
	},
	//新增航空公司FORM
	airlinesForm : null,
	getAirlinesForm : function () {
		if (Ext.isEmpty(this.airlinesForm)) {
			this.airlinesForm = Ext.create('Foss.baseinfo.AirlinesForm');
		}
		return this.airlinesForm;
	},
	//提交航空公司数据
	commitAirlines : function () {
		var me = this;
		if (me.getAirlinesForm().getForm().isValid()) { //校验form是否通过校验
			var airlinesModel = new Foss.baseinfo.AirlinesEntity();
			me.getAirlinesForm().getForm().updateRecord(airlinesModel); //将FORM中数据设置到MODEL里面
			var params = {
				'airlinesVo' : {
					'airlinesEntity' : airlinesModel.data
				}
			}; //组织新增数据
			var successFun = function (json) {
				baseinfo.showInfoMes(json.message); //提示新增成功
				me.close();
				me.parent.getPagingToolbar().moveFirst(); //成功之后重新查询刷新结果集
			};
			var failureFun = function (json) {
				if (Ext.isEmpty(json)) {
					baseinfo.showErrorMes(baseinfo.airlines.i18n('foss.baseinfo.requestTimeout')); //请求超时
				} else {
					baseinfo.showErrorMes(json.message); //提示失败原因
				}
			};
			var url = baseinfo.realPath('addAirlines.action'); //请求航空公司新增
			baseinfo.requestJsonAjax(url, params, successFun, failureFun); //发送AJAX请求
		}
	},
	constructor : function (config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.fbar = [{
				text : baseinfo.airlines.i18n('foss.baseinfo.cancel'), //取消
				handler : function () {
					me.close();
				}
			}, {
				text : baseinfo.airlines.i18n('foss.baseinfo.reset'), //重置
				handler : function () {
					me.getAirlinesForm().getForm().loadRecord(new Foss.baseinfo.AirlinesEntity());
				}
			}, {
				text : baseinfo.airlines.i18n('foss.baseinfo.save'), //保存
				cls : 'yellow_button',
				margin : '0 0 0 305',
				handler : function () {
					me.commitAirlines();
				}
			}
		];
		me.items = [me.getAirlinesForm()];
		me.callParent([cfg]);
	},
	operationUrl : 'add'
});
/**
 * 修改航空公司
 */
Ext.define('Foss.baseinfo.AirlinesUpdateWindow', {
	extend : 'Ext.window.Window',
	title : baseinfo.airlines.i18n('foss.baseinfo.modifyAirlines'), //修改航空公司
	closable : true,
	modal : true,
	resizable : false,
	airlinesEntity : null, //修改航空公司数据
	parent : null, //父元素（弹出这个window的gird——Foss.baseinfo.AirlinesGrid）
	closeAction : 'hide',
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	width : 570,
	height : 370,
	listeners : {
		beforehide : function (me) {
			me.getAirlinesForm().getForm().reset(); //表格重置
		},
		beforeshow : function (me) {
			var fielsds = me.getAirlinesForm().getForm().getFields();
			if (!Ext.isEmpty(fielsds)) {
				fielsds.each(function (item, index, length) {
					item.clearInvalid();
					item.unsetActiveError();
				});
			}
			fielsds = null;
			me.getAirlinesForm().getForm().loadRecord(new Foss.baseinfo.AirlinesEntity(me.airlinesEntity));
		}
	},
	//修改航空公司FORM
	airlinesForm : null,
	getAirlinesForm : function () {
		if (Ext.isEmpty(this.airlinesForm)) {
			this.airlinesForm = Ext.create('Foss.baseinfo.AirlinesForm');
		}
		return this.airlinesForm;
	},
	//修改航空公司
	commitAirlines : function () {
		var me = this;
		if (me.getAirlinesForm().getForm().isValid()) { //校验form是否通过校验
			var airlinesModel = new Foss.baseinfo.AirlinesEntity(me.airlinesEntity);
			me.getAirlinesForm().getForm().updateRecord(airlinesModel); //将FORM中数据设置到MODEL里面
			var params = {
				'airlinesVo' : {
					'airlinesEntity' : airlinesModel.data
				}
			}; //组织新增数据
			var successFun = function (json) {
				baseinfo.showInfoMes(json.message); //提示新增成功
				me.close();
				me.parent.getPagingToolbar().moveFirst(); //成功之后重新查询刷新结果集
			};
			var failureFun = function (json) {
				if (Ext.isEmpty(json)) {
					baseinfo.showErrorMes(baseinfo.airlines.i18n('foss.baseinfo.requestTimeout')); //请求超时
				} else {
					baseinfo.showErrorMes(json.message); //提示失败原因
				}
			};
			var url = baseinfo.realPath('updateAirlines.action'); //请求航空公司新增
			baseinfo.requestJsonAjax(url, params, successFun, failureFun); //发送AJAX请求
		}
	},
	constructor : function (config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.fbar = [{
				text : baseinfo.airlines.i18n('foss.baseinfo.cancel'), //取消
				handler : function () {
					me.close();
				}
			}, {
				text : baseinfo.airlines.i18n('foss.baseinfo.reset'), //重置
				handler : function () {
					me.getAirlinesForm().getForm().loadRecord(new Foss.baseinfo.AirlinesEntity(me.airlinesEntity));
				}
			}, {
				text : baseinfo.airlines.i18n('foss.baseinfo.save'), //保存
				cls : 'yellow_button',
				margin : '0 0 0 305',
				handler : function () {
					me.commitAirlines();
				}
			}
		];
		me.items = [me.getAirlinesForm()];
		me.callParent([cfg]);
	},
	operationUrl : 'add'
});
/**
 * 航空公司组-FORM
 */
Ext.define('Foss.baseinfo.AirlinesForm', {
	extend : 'Ext.form.Panel',
	title : baseinfo.airlines.i18n('foss.baseinfo.airlineInformation'), //航空公司信息
	frame : true,
	flex : 1,
	collapsible : true,
	defaults : {
		margin : '5 5 5 5',
		labelWidth : 110,
		//width:200,
		colspan : 1
	},
	layout : {
		type : 'table',
		columns : 2
	},
	constructor : function (config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.items = [{
				name : 'name', //航空公司
				allowBlank : false,
				maxLength : '20',
				fieldLabel : baseinfo.airlines.i18n('foss.baseinfo.airlines'),
				xtype : 'textfield',
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
						me.validatorMessage.value = Ext.String.trim(me.getValue()); ;
					},
					blur : function (me, event) {
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
							url : baseinfo.realPath('queryAirlineByName.action'),
							jsonData : {
								'airlinesVo' : {
									'airlinesEntity' : {
										'name' : mineVal
									}
								}
							},
							success : function (response) {
								//检测baseinfo.airlines.i18n('foss.baseinfo.name')是否已经存在
								var json = Ext.decode(response.responseText);
								var airlines = json.airlinesVo.airlinesEntity;
								//数据"已存在"
								if (Ext.isEmpty(airlines)) {
									me.validatorMessage.validity = true;
								} else {
									//初始化：错误数据
									me.validatorMessage.validity = false;
									me.validatorMessage.message = baseinfo.airlines.i18n('foss.baseinfo.theName') + me.getValue() + baseinfo.airlines.i18n('foss.baseinfo.airlinesAlreadyExists');
									/*
									 * "修改动作"的特殊验证逻辑
									 */
									if (me.up('window').operationUrl === 'upd') {
										var formModel = me.up('window').getAirlinesForm().getRecord();
										if (!Ext.isEmpty(formModel)) {
											if (airlines.id.toString() === formModel.get('id').toString()) {
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
										title : baseinfo.airlines.i18n('foss.baseinfo.failureInformationTips'),
										msg : me.validatorMessage.message,
										width : 350,
										buttons : Ext.Msg.OK,
										icon : Ext.MessageBox.WARNING
									});
								}
								json = null;
								airlines = null;
								me.validatorMessage.value = null;
							},
							exception : function (response) {
								var json = Ext.decode(response.responseText);
								me.validatorMessage.validity = false;
								me.validatorMessage.message = json.message;
								me.validatorMessage.value = null;
								Ext.MessageBox.show({
									title : baseinfo.airlines.i18n('foss.baseinfo.failureInformationTips'),
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
				name : 'code', //航空公司编号
				allowBlank : false,
				maxLength : '20',
				fieldLabel : baseinfo.airlines.i18n('foss.baseinfo.airlineCode'),
				regex:/^[A-Za-z0-9]{2}$/,
				xtype : 'textfield',
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
						me.validatorMessage.value = Ext.String.trim(me.getValue()); ;
					},
					blur : function (me, event) {
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
							url : baseinfo.realPath('queryAirlineByCode.action'),
							jsonData : {
								'airlinesVo' : {
									'airlinesEntity' : {
										'code' : mineVal
									}
								}
							},
							success : function (response) {
								//检测"代码"是否已经存在
								var json = Ext.decode(response.responseText);
								var airlines = json.airlinesVo.airlinesEntity;
								//数据"已存在"
								if (Ext.isEmpty(airlines)) {
									me.validatorMessage.validity = true;
								} else {
									//初始化：错误数据
									me.validatorMessage.validity = false;
									me.validatorMessage.message = baseinfo.airlines.i18n('foss.baseinfo.theCode') + me.getValue() + baseinfo.airlines.i18n('foss.baseinfo.airlinesAlreadyExists');
									/*
									 * "修改动作"的特殊验证逻辑
									 */
									if (me.up('window').operationUrl === 'upd') {
										var formModel = me.up('window').getAirlinesForm().getRecord();
										if (!Ext.isEmpty(formModel)) {
											if (airlines.id.toString() === formModel.get('id').toString()) {
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
										title : baseinfo.airlines.i18n('foss.baseinfo.failureInformationTips'),
										msg : me.validatorMessage.message,
										width : 350,
										buttons : Ext.Msg.OK,
										icon : Ext.MessageBox.WARNING
									});
								}
								json = null;
								airlines = null;
								me.validatorMessage.value = null;
							},
							exception : function (response) {
								var json = Ext.decode(response.responseText);
								me.validatorMessage.validity = false;
								me.validatorMessage.message = json.message;
								me.validatorMessage.value = null;
								Ext.MessageBox.show({
									title : baseinfo.airlines.i18n('foss.baseinfo.failureInformationTips'),
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
				name : 'simpleName', //航空公司简称
				allowBlank : false,
				maxLength : '20',
				fieldLabel : baseinfo.airlines.i18n('foss.baseinfo.airlineReferredTo'),
				xtype : 'textfield',
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
						me.validatorMessage.value = Ext.String.trim(me.getValue()); ;
					},
					blur : function (me, event) {
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
							url : baseinfo.realPath('queryAirlineBySimpleName.action'),
							jsonData : {
								'airlinesVo' : {
									'airlinesEntity' : {
										'simpleName' : mineVal
									}
								}
							},
							success : function (response) {
								//检测baseinfo.airlines.i18n('foss.baseinfo.simple')是否已经存在
								var json = Ext.decode(response.responseText);
								var airlines = json.airlinesVo.airlinesEntity;
								//数据"已存在"
								if (Ext.isEmpty(airlines)) {
									me.validatorMessage.validity = true;
								} else {
									//初始化：错误数据
									me.validatorMessage.validity = false;
									me.validatorMessage.message = baseinfo.airlines.i18n('foss.baseinfo.theReferred') + me.getValue() + baseinfo.airlines.i18n('foss.baseinfo.airlinesAlreadyExists');
									/*
									 * "修改动作"的特殊验证逻辑
									 */
									if (me.up('window').operationUrl === 'upd') {
										var formModel = me.up('window').getAirlinesForm().getRecord();
										if (!Ext.isEmpty(formModel)) {
											if (airlines.id.toString() === formModel.get('id').toString()) {
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
										title : baseinfo.airlines.i18n('foss.baseinfo.failureInformationTips'),
										msg : me.validatorMessage.message,
										width : 350,
										buttons : Ext.Msg.OK,
										icon : Ext.MessageBox.WARNING
									});
								}
								json = null;
								airlines = null;
								me.validatorMessage.value = null;
							},
							exception : function (response) {
								var json = Ext.decode(response.responseText);
								me.validatorMessage.validity = false;
								me.validatorMessage.message = json.message;
								me.validatorMessage.value = null;
								Ext.MessageBox.show({
									title : baseinfo.airlines.i18n('foss.baseinfo.failureInformationTips'),
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
				name : 'prifixName', //航空公司数字前缀
				regex : new RegExp('^[0-9]{3}$'),
				fieldLabel : baseinfo.airlines.i18n('foss.baseinfo.airlineNumericPrefix'),
				xtype : 'textfield'
			}, {
				xtype : 'filefield',
				name : 'logo',
				width : 400,
				fieldLabel : baseinfo.airlines.i18n('foss.baseinfo.arilineLogo'), //航空公司LOGO
				blankText : baseinfo.airlines.i18n('foss.baseinfo.pleasedSelectPicture'),
				colspan : 2,
				buttonText : '本地照片  ' //本地照片
			}, {
				name : 'notes', //备注
				fieldLabel : baseinfo.airlines.i18n('foss.baseinfo.airagencycompany.remark'),
				colspan : 2,
				maxLength : 200,
				width : 400,
				xtype : 'textareafield'
			}
		];
		me.callParent([cfg]);
	}
});


