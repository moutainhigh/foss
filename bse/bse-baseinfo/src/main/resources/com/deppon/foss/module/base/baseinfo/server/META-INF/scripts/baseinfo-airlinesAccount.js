/*
 *  转换long类型为日期
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
 *  Ajax请求
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
baseinfo.yes = 'Y'; // 是
baseinfo.no = 'N'; // 否
baseinfo.ALL = 'ALL'; // 全部
// --------------------------------------baseinfo----------------------------------------
// 航空公司账户
Ext.define('Foss.baseinfo.AirlinesAccountEntity', {
	extend : 'Ext.data.Model',
	fields : [{
			name : 'id',
			type : 'string'
		}, {
			name : 'airlines', // 航空公司
			type : 'string'
		}, {
			name : 'orgId', // 使用部门
			type : 'string'
		}, {
			name : 'accessAccount', // 接入账号
			type : 'string'
		}, {
			name : 'accessPassword', // 接入密码
			type : 'string'
		}, {
			name : 'confirmPassword', // 确认接入密码
			type : 'string'
		}, {
			name : 'airlinesAccount', // 航空公司账号
			type : 'string'
		}, {
			name : 'checksum', // 航空公司校验码
			type : 'string'
		}, {
			name : 'confirmChecksum', // 确认航空公司校验码
			type : 'string'
		}, {
			name : 'active', // 是否激活
			type : 'string'
		}
	]
});
// 航空公司账户DTO
Ext.define('Foss.baseinfo.AirlinesAccountDto', {
	extend : 'Ext.data.Model',
	fields : [{
			name : 'id',
			type : 'string'
		}, {
			name : 'airlinesAccount' // “航空公司账户”实体
		}, {
			name : 'useDepartment', // 使用部门
			type : 'string'
		}, {
			name : 'airlinesName', // 航空公司
			type : 'string'
		}
	]
});
// ------------------------------------model---------------------------------------------------
/**
 * 航空公司账户Store（Foss.baseinfo.AirlinesAccountDto）
 */
Ext.define('Foss.baseinfo.AirlinesAccountStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.baseinfo.AirlinesAccountDto', // 航空公司账户MODEL
	pageSize : 20,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url : baseinfo
		.realPath('queryAirlinesAccountListBySelectiveCondition.action'), // 请求地址
		reader : {
			type : 'json',
			root : 'airlinesAccountVo.airlinesAccountDtoList', // 获取的数据
			totalProperty : 'totalCount' // 总个数
		}
	}
});

// ----------------------------------------store---------------------------------

/**
 * 航空公司账户表单
 */
Ext.define('Foss.baseinfo.QueryAirlinesAccountForm', {
	extend : 'Ext.form.Panel',
	title : baseinfo.airlinesAccount.i18n('foss.baseinfo.airlinesAccoutInformationInquiry'), // 航空公司账户信息查询
	frame : true,
	collapsible : true,
	layout : {
		type : 'table',
		columns : 3
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
				name : 'airlinesAccount',
				fieldLabel : baseinfo.airlinesAccount.i18n('foss.baseinfo.airlinesAccount'), // 航空公司账号
				xtype : 'textfield'
			}, {
				name : 'airlines',
				fieldLabel : baseinfo.airlinesAccount.i18n('foss.baseinfo.airlines'), // 航空公司
				xtype : 'commonairlinesselector'
			}, {
				name : 'orgId',
				fieldLabel : baseinfo.airlinesAccount.i18n('foss.baseinfo.userDepartments'), // 位置
				xtype : 'dynamicorgcombselector'
			},{
				border: 1,
				xtype:'container',
				columnWidth:1,
				colspan:3,
				defaultType:'button',
				layout:'column',
				items:[{
					  text : baseinfo.airlinesAccount.i18n('foss.baseinfo.reset'), // 重置
					  columnWidth:.12,
					  disabled:!baseinfo.airlinesAccount.isPermission('airlinesAccount/airlinesAccountQueryButton'),
					  hidden:!baseinfo.airlinesAccount.isPermission('airlinesAccount/airlinesAccountQueryButton'),
					  handler : function () {
							me.getForm().reset();
						}
				  	},{
						xtype:'container',
						border:false,
						html:'&nbsp;',
						columnWidth:.76
					},{
					  text : baseinfo.airlinesAccount.i18n('foss.baseinfo.query'), // 查询
					  columnWidth:.12,
					  cls:'yellow_button',
					  disabled:!baseinfo.airlinesAccount.isPermission('airlinesAccount/airlinesAccountQueryButton'),
					  hidden:!baseinfo.airlinesAccount.isPermission('airlinesAccount/airlinesAccountQueryButton'),
					  handler : function () {
						  if (me.getForm().isValid()) {
								me.up().getAirlinesAccountGrid()
								.getPagingToolbar().moveFirst();
							}
							
						}
				  	}]
			 }
		];
		me.callParent([cfg]);
	}
});
/**
 * 航空公司账户列表
 */
Ext.define('Foss.baseinfo.AirlinesAccountGrid', {
	extend : 'Ext.grid.Panel',
	title : baseinfo.airlinesAccount.i18n('foss.baseinfo.airlinesAccountInformation'), // 航空公司账户信息
	frame : true,
	flex : 1,
	sortableColumns : false,
	enableColumnHide : false,
	enableColumnMove : false,
	stripeRows : true, // 交替行效果
	selType : "rowmodel", // 选择类型设置为：行选择
	emptyText : baseinfo.airlinesAccount.i18n('foss.baseinfo.queryResultIsNull'), // 查询结果为空
	// 得到bbar
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
	// 航空公司账户新增WINDOW
	airlinesAccountAddWindow : null,
	getAirlinesAccountAddWindow : function () {
		if (this.airlinesAccountAddWindow == null) {
			this.airlinesAccountAddWindow = Ext
				.create('Foss.baseinfo.AirlinesAccountAddWindow');
			this.airlinesAccountAddWindow.parent = this; // 父元素
		}
		return this.airlinesAccountAddWindow;
	},
	// 修改航空公司账户WINDOW
	airlinesAccountUpdateWindow : null,
	getAirlinesAccountUpdateWindow : function () {
		if (this.airlinesAccountUpdateWindow == null) {
			this.airlinesAccountUpdateWindow = Ext
				.create('Foss.baseinfo.AirlinesAccountUpdateWindow');
			this.airlinesAccountUpdateWindow.parent = this; // 父元素
		}
		return this.airlinesAccountUpdateWindow;
	},
	// 作废航空公司账户
	toVoidAirlinesAccount : function (btn) {
		var me = this;
		var selections = me.getSelectionModel().getSelection(); // 获取选中的数据
		if (selections.length < 1) { // 判断是否至少选中了一条
			baseinfo.showWoringMessage(baseinfo.airlinesAccount.i18n('foss.baseinfo.selectVoidedOperation')); // 请选择一条进行作废操作！
			return; // 没有则提示并返回
		}
		baseinfo.showQuestionMes(baseinfo.airlinesAccount.i18n('foss.baseinfo.whetherToVoidTheseAirlinesAccount'), function (e) { // 是否要作废这些航空公司账户？
			if (e == 'yes') { // 询问是否删除，是则发送请求
				var ids = new Array(); // 航空公司账户
				for (var i = 0; i < selections.length; i++) {
					ids.push(selections[i].get('airlinesAccount').id);
				}
				var params = {
					'airlinesAccountVo' : {
						'ids' : ids
					}
				};
				var successFun = function (json) {
					baseinfo.showInfoMes(json.message);
					me.getPagingToolbar().moveFirst();
				};
				var failureFun = function (json) {
					if (Ext.isEmpty(json)) {
						baseinfo.showErrorMes(baseinfo.airlinesAccount.i18n('foss.baseinfo.requestTimeout')); // 请求超时
					} else {
						baseinfo.showErrorMes(json.message);
					}
				};
				var url = baseinfo
					.realPath('deleteAirlinesAccount.action');
				baseinfo.requestJsonAjax(url, params, successFun,
					failureFun);
			}
		})
		
	},
	constructor : function (config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.columns = [{
				xtype : 'rownumberer',
				width : 40,
				text : baseinfo.airlinesAccount.i18n('foss.baseinfo.noSequence') // 序号
			}, {
				text : baseinfo.airlinesAccount.i18n('foss.baseinfo.operate'), // 操作
				// dataIndex : 'id',
				xtype : 'actioncolumn',
				align : 'center',
				width : 80,
				items : [{
						iconCls : 'deppon_icons_edit',
						tooltip : baseinfo.airlinesAccount.i18n('foss.baseinfo.update'), // 修改
						width : 42,
						disabled :!baseinfo.airlinesAccount.isPermission('airlinesAccount/airlinesAccountEditButton'),
						handler : function (grid, rowIndex, colIndex) {
							// 获取选中的数据
							var record = grid.getStore().getAt(rowIndex);
							var id = record.get('airlinesAccount').id; // 航空公司账户ID
							var params = {
								'airlinesAccountVo' : {
									'airlinesAccountEntity' : {
										'id' : id
									}
								}
							};
							var successFun = function (json) {
								var updateWindow = me
									.getAirlinesAccountUpdateWindow(); // 获得修改窗口
								updateWindow.airlinesAccountEntity = json.airlinesAccountVo.airlinesAccountEntity; // 站点组
								updateWindow.show(); // 显示修改窗口
							};
							var failureFun = function (json) {
								if (Ext.isEmpty(json)) {
									baseinfo.showErrorMes(baseinfo.airlinesAccount.i18n('foss.baseinfo.requestTimeout')); // 请求超时
								} else {
									baseinfo.showErrorMes(json.message);
								}
							};
							var url = baseinfo
								.realPath('queryAirlinesAccount.action');
							baseinfo.requestJsonAjax(url, params, successFun,
								failureFun);
						}
					}, {
						iconCls : 'deppon_icons_cancel',
						tooltip : baseinfo.airlinesAccount.i18n('foss.baseinfo.void'), // 作废
						width : 42,
						disabled :!baseinfo.airlinesAccount.isPermission('airlinesAccount/airlinesAccountVoidButton'),
						handler : function (grid, rowIndex, colIndex) {
							// 获取选中的数据
							var record = grid.getStore().getAt(rowIndex);
							baseinfo.showQuestionMes(baseinfo.airlinesAccount.i18n('foss.baseinfo.whetherToVoidTheAirlinesAccount'),
								function (e) { // 是否要作废这个航空公司账户？
								if (e == 'yes') { // 询问是否删除，是则发送请求
									var ids = new Array(); // 航空公司账户
									ids.push(record
										.get('airlinesAccount').id);
									var params = {
										'airlinesAccountVo' : {
											'ids' : ids
										}
									};
									var successFun = function (json) {
										baseinfo
										.showInfoMes(json.message);
										me.getPagingToolbar()
										.moveFirst();
									};
									var failureFun = function (json) {
										if (Ext.isEmpty(json)) {
											baseinfo
											.showErrorMes(baseinfo.airlinesAccount.i18n('foss.baseinfo.requestTimeout')); // 请求超时
										} else {
											baseinfo
											.showErrorMes(json.message);
										}
									};
									var url = baseinfo
										.realPath('deleteAirlinesAccount.action');
									baseinfo.requestJsonAjax(url,
										params, successFun,
										failureFun);
								}
							})
						}
					}
				]
			}, {
				text : baseinfo.airlinesAccount.i18n('foss.baseinfo.airlines'), // 航空公司
				dataIndex : 'airlinesName',
				flex : 1
			}, {
				text : baseinfo.airlinesAccount.i18n('foss.baseinfo.userDepartments'), // 使用部门
				dataIndex : 'useDepartment',
				flex : 1
			}, {
				text : baseinfo.airlinesAccount.i18n('foss.baseinfo.accessAccount'), // 接入账号
				dataIndex : 'airlinesAccount',
				renderer : function (value) {
					if (!Ext.isEmpty(value)) {
						return value.accessAccount;
					}
				},
				flex : 1
			}, {
				text : baseinfo.airlinesAccount.i18n('foss.baseinfo.airlinesAccount'), // 航空公司账号
				dataIndex : 'airlinesAccount',
				renderer : function (value) {
					if (!Ext.isEmpty(value)) {
						return value.airlinesAccount;
					}
				},
				flex : 1
			}
		];
		me.store = Ext.create('Foss.baseinfo.AirlinesAccountStore', {
				autoLoad : false, // 不自动加载
				pageSize : 20,
				listeners : {
					beforeload : function (store, operation, eOpts) {
						var queryForm = me.up().getQueryAirlinesAccountForm();
						if (queryForm != null) {
							Ext.apply(operation, {
								params : { // 航空公司账户大查询，查询条件组织
									'airlinesAccountVo.airlinesAccountEntity.airlinesAccount' : queryForm
									.getForm().findField('airlinesAccount')
									.getValue(), // 航空公司账号
									'airlinesAccountVo.airlinesAccountEntity.airlines' : queryForm
									.getForm().findField('airlines')
									.getValue(), // 航空公司
									'airlinesAccountVo.airlinesAccountEntity.orgId' : queryForm
									.getForm().findField('orgId')
									.getValue()
									// 使用部门
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
					scroller.mon(scroller.scrollEl, 'scroll',
						scroller.onElScroll, scroller);
				}
			}
		},
		me.selModel = Ext.create('Ext.selection.CheckboxModel', { // 多选框
				mode : 'MULTI',
				checkOnly : true
			});
		me.tbar = [{
				text : baseinfo.airlinesAccount.i18n('foss.baseinfo.add'), // 新增
				width : 80,
				disabled:!baseinfo.airlinesAccount.isPermission('airlinesAccount/airlinesAccountAddButton'),
				hidden:!baseinfo.airlinesAccount.isPermission('airlinesAccount/airlinesAccountAddButton'),
				handler : function () {
					me.getAirlinesAccountAddWindow().show();
				}
			}, '-', {
				text : baseinfo.airlinesAccount.i18n('foss.baseinfo.void'), // 作废
				width : 80,
				disabled:!baseinfo.airlinesAccount.isPermission('airlinesAccount/airlinesAccountVoidButton'),	
				hidden:!baseinfo.airlinesAccount.isPermission('airlinesAccount/airlinesAccountVoidButton'),	
				handler : function () {
					me.toVoidAirlinesAccount();
				}
			} 
		];
		me.bbar = me.getPagingToolbar();
		me.callParent([cfg]);
	}
});

/**
 * @description 航空公司账户主页
 */
Ext.onReady(function () {
	Ext.QuickTips.init();
	if (Ext.getCmp('T_baseinfo-airlinesAccount_content')) {
		return;
	};
	var queryAirlinesAccountForm = Ext
		.create('Foss.baseinfo.QueryAirlinesAccountForm'); // 查询FORM
	var airlinesAccountGrid = Ext
		.create('Foss.baseinfo.AirlinesAccountGrid'); // 查询结果GRID
	Ext.getCmp('T_baseinfo-airlinesAccount').add(Ext.create('Ext.panel.Panel', {
		id : 'T_baseinfo-airlinesAccount_content',
		cls : 'panelContentNToolbar',
		bodyCls : 'panelContentNToolbar-body',
		// 获得查询FORM
		getQueryAirlinesAccountForm : function () {
			return queryAirlinesAccountForm;
		},
		// 获得查询结果GRID
		getAirlinesAccountGrid : function () {
			return airlinesAccountGrid;
		},
		items : [queryAirlinesAccountForm, airlinesAccountGrid]
	}));
});
// ----------------------------------------------上面是整体布局，下面是弹出窗口----------------------------------
/**
 * 新增航空公司账户信息
 */
Ext.define('Foss.baseinfo.AirlinesAccountAddWindow', {
	extend : 'Ext.window.Window',
	title : baseinfo.airlinesAccount.i18n('foss.baseinfo.newAirlinesAccount'), // 新增航空公司账户
	closable : true,
	parent : null, // 父元素（弹出这个window的gird——Foss.baseinfo.AirlinesAccountGrid）
	modal : true,
	resizable : false, // 可以调整窗口的大小
	closeAction : 'hide', // 点击关闭是隐藏窗口
	width : 370,
	height : 460,
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	listeners : {
		beforehide : function (me) { // 隐藏WINDOW的时候清除数据
			me.getAirlinesAccountForm().getForm().reset(); // 表格重置
		},
		beforeshow : function (me) { // 显示WINDOW的时候清除数据
			
		}
	},
	// 新增航空公司账户FORM
	airlinesAccountForm : null,
	getAirlinesAccountForm : function () {
		if (Ext.isEmpty(this.airlinesAccountForm)) {
			this.airlinesAccountForm = Ext
				.create('Foss.baseinfo.AirlinesAccountForm');
		}
		return this.airlinesAccountForm;
	},
	// 提交航空公司账户数据
	commitAirlinesAccount : function () {
		var me = this;
		if (me.getAirlinesAccountForm().getForm().isValid()) { // 校验form是否通过校验
			var airlinesAccountModel = new Foss.baseinfo.AirlinesAccountEntity();
			me.getAirlinesAccountForm().getForm()
			.updateRecord(airlinesAccountModel); // 将FORM中数据设置到MODEL里面
			if (airlinesAccountModel.get('accessPassword') != airlinesAccountModel
				.get('confirmPassword')) {
				baseinfo.showErrorMes(baseinfo.airlinesAccount.i18n('foss.baseinfo.axxessPasswordIConfirmInconsistent')); // 接入密码与确认接入密码不一致！
				return;
			}
			if (airlinesAccountModel.get('checksum') != airlinesAccountModel
				.get('confirmChecksum')) {
				baseinfo.showErrorMes(baseinfo.airlinesAccount.i18n('foss.baseinfo.airlinesCheckCodeConfirmInconsistent')); // 航空公司校验码与确认航空公司校验码不一致！
				return;
			}
			var params = {
				'airlinesAccountVo' : {
					'airlinesAccountEntity' : airlinesAccountModel.data
				}
			}; // 组织新增数据
			var successFun = function (json) {
				baseinfo.showInfoMes(json.message); // 提示新增成功
				me.close();
				me.parent.getPagingToolbar().moveFirst(); // 成功之后重新查询刷新结果集
			};
			var failureFun = function (json) {
				if (Ext.isEmpty(json)) {
					baseinfo.showErrorMes(baseinfo.airlinesAccount.i18n('foss.baseinfo.requestTimeout')); // 请求超时
				} else {
					baseinfo.showErrorMes(json.message); // 提示失败原因
				}
			};
			var url = baseinfo.realPath('addAirlinesAccount.action'); // 请求航空公司账户新增
			baseinfo.requestJsonAjax(url, params, successFun, failureFun); // 发送AJAX请求
		}
	},
	constructor : function (config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.fbar = [{
				text : baseinfo.airlinesAccount.i18n('foss.baseinfo.cancel'), // 取消
				handler : function () {
					me.close();
				}
			}, {
				text : baseinfo.airlinesAccount.i18n('foss.baseinfo.reset'), // 重置
				handler : function () {
					me
					.getAirlinesAccountForm()
					.getForm()
					.loadRecord(new Foss.baseinfo.AirlinesAccountEntity());
				}
			}, {
				text : baseinfo.airlinesAccount.i18n('foss.baseinfo.save'), // 保存
				cls : 'yellow_button',
				margin : '0 0 0 105',
				handler : function () {
					me.commitAirlinesAccount();
				}
			}
		];
		me.items = [me.getAirlinesAccountForm()];
		me.callParent([cfg]);
	}
});
/**
 * 修改航空公司账户
 */
Ext.define('Foss.baseinfo.AirlinesAccountUpdateWindow', {
	extend : 'Ext.window.Window',
	title : baseinfo.airlinesAccount.i18n('foss.baseinfo.modifyAirlinesAccount'), // 修改航空公司账户
	closable : true,
	modal : true,
	resizable : false,
	airlinesAccountEntity : null, // 修改航空公司账户数据
	parent : null, // 父元素（弹出这个window的gird——Foss.baseinfo.SiteGroupGrid）
	closeAction : 'hide',
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	width : 370,
	height : 460,
	listeners : {
		beforehide : function (me) {
			me.getAirlinesAccountForm().getForm().reset(); // 表格重置
		},
		beforeshow : function (me) {
			me
			.getAirlinesAccountForm()
			.getForm()
			.loadRecord(new Foss.baseinfo.AirlinesAccountEntity(me.airlinesAccountEntity));
		}
	},
	// 修改航空公司账户FORM
	airlinesAccountForm : null,
	getAirlinesAccountForm : function () {
		if (Ext.isEmpty(this.airlinesAccountForm)) {
			this.airlinesAccountForm = Ext
				.create('Foss.baseinfo.AirlinesAccountForm');
			// this.airlinesAccountForm.getForm().findField('organizationName').setReadOnly(true);
		}
		return this.airlinesAccountForm;
	},
	// 修改航空公司账户
	commitAirlinesAccount : function () {
		var me = this;
		if (me.getAirlinesAccountForm().getForm().isValid()) { // 校验form是否通过校验
			var airlinesAccountModel = new Foss.baseinfo.AirlinesAccountEntity(me.airlinesAccountEntity);
			me.getAirlinesAccountForm().getForm()
			.updateRecord(airlinesAccountModel); // 将FORM中数据设置到MODEL里面
			var params = {
				'airlinesAccountVo' : {
					'airlinesAccountEntity' : airlinesAccountModel.data
				}
			}; // 组织新增数据
			var successFun = function (json) {
				baseinfo.showInfoMes(json.message); // 提示新增成功
				me.close();
				me.parent.getPagingToolbar().moveFirst(); // 成功之后重新查询刷新结果集
			};
			var failureFun = function (json) {
				if (Ext.isEmpty(json)) {
					baseinfo.showErrorMes(baseinfo.airlinesAccount.i18n('foss.baseinfo.requestTimeout')); // 请求超时
				} else {
					baseinfo.showErrorMes(json.message); // 提示失败原因
				}
			};
			var url = baseinfo.realPath('updateAirlinesAccount.action'); // 请求航空公司账户新增
			baseinfo.requestJsonAjax(url, params, successFun, failureFun); // 发送AJAX请求
		}
	},
	constructor : function (config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.fbar = [{
				text : baseinfo.airlinesAccount.i18n('foss.baseinfo.cancel'), // 取消
				handler : function () {
					me.close();
				}
			}, {
				text : baseinfo.airlinesAccount.i18n('foss.baseinfo.reset'), // 重置
				handler : function () {
					me
					.getAirlinesAccountForm()
					.getForm()
					.loadRecord(new Foss.baseinfo.AirlinesAccountEntity(me.airlinesAccountEntity));
				}
			}, {
				text : baseinfo.airlinesAccount.i18n('foss.baseinfo.save'), // 保存
				cls : 'yellow_button',
				margin : '0 0 0 305',
				plugins : Ext.create('Deppon.ux.ButtonLimitingPlugin', {
					seconds : 3
				}),
				handler : function () {
					me.commitAirlinesAccount();
				}
			}
		];
		me.items = [me.getAirlinesAccountForm()];
		me.callParent([cfg]);
	}
});
/**
 * 航空公司账户组-FORM
 */
Ext.define('Foss.baseinfo.AirlinesAccountForm', {
	extend : 'Ext.form.Panel',
	title : baseinfo.airlinesAccount.i18n('foss.baseinfo.airlinesAccountInformation'), // 航空公司账户信息
	frame : true,
	flex : 1,
	collapsible : true,
	defaults : {
		margin : '5 5 5 5',
		allowBlank : false,
		labelWidth : 120,
		// width:200,
		colspan : 1
	},
	layout : {
		type : 'table',
		columns : 1
	},
	constructor : function (config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.items = [{
				xtype : 'commonairlinesselector',
				name : 'airlines', // 航空公司
				fieldLabel : baseinfo.airlinesAccount.i18n('foss.baseinfo.airlines'),
				listeners : {
					blur : function (me, event) {
						me.fireEvent('change', me, event);
					}
				}
			}, {
				xtype : 'dynamicorgcombselector',
				doAirDispatch : 'Y',
				name : 'orgId', // 使用部门
				fieldLabel : baseinfo.airlinesAccount.i18n('foss.baseinfo.userDepartments'),
				listeners : {
					blur : function (me, event) {
						me.fireEvent('change', me, event);
					}
				}
			}, {
				name : 'accessAccount', // 接入账号
				fieldLabel : baseinfo.airlinesAccount.i18n('foss.baseinfo.accessAccount'),
				xtype : 'textfield'
			}, {
				name : 'accessPassword', // 接入密码
				fieldLabel : baseinfo.airlinesAccount.i18n('foss.baseinfo.accessPassword'),
				inputType : 'password',
				xtype : 'textfield'
			}, {
				name : 'confirmPassword', // 确认接入密码
				fieldLabel : baseinfo.airlinesAccount.i18n('foss.baseinfo.confirmAccessPassword'),
				inputType : 'password',
				xtype : 'textfield'
			}, {
				name : 'airlinesAccount', // 航空公司账号
				fieldLabel : baseinfo.airlinesAccount.i18n('foss.baseinfo.airlinesAccount'),
				xtype : 'textfield'
			}, {
				name : 'checksum', // 航空公司校验码
				fieldLabel : baseinfo.airlinesAccount.i18n('foss.baseinfo.airlinesChecksum'),
				inputType : 'password',
				xtype : 'textfield'
			}, {
				name : 'confirmChecksum', // 确认航空公司校验码
				fieldLabel : baseinfo.airlinesAccount.i18n('foss.baseinfo.confirmAirlinesChecksum'),
				inputType : 'password',
				xtype : 'textfield'
			}
		];
		me.callParent([cfg]);
	}
});

