var isHidden = false; // 是否隐藏
var isChecked = false; // 是否选中

/**
 * 发送Ajax请求，发送并接收 json
 * 
 * @param {}
 *            url
 * @param {}
 *            params
 * @param {}
 *            successFn
 * @param {}
 *            failFn
 */
function requestJsonAjax(url, params, successFn, failFn) {
	Ext.getCmp('T_baseinfo-outgoingBigCustomerIndex_content').getEl()
			.mask("数据加载中,请稍等...");
	Ext.Ajax.request({
				url : url,
				jsonData : params,
				timeout : 60000,
				success : function(response) {
					var result = Ext.decode(response.responseText);
					if (result.success) {
						successFn(result);
					} else {
						failFn(result);
					}
					Ext.getCmp('T_baseinfo-outgoingBigCustomerIndex_content')
							.getEl().unmask();
				},
				failure : function(response) {
					var result = Ext.decode(response.responseText);
					failFn(result);
					Ext.getCmp('T_baseinfo-outgoingBigCustomerIndex_content')
							.getEl().unmask();
				},
				exception : function(response) {
					var result = Ext.decode(response.responseText);
					failFn(result);
					Ext.getCmp('T_baseinfo-outgoingBigCustomerIndex_content')
							.getEl().unmask();
				}
			});
};

/**
 * 查询界面
 */
Ext.define('Foss.baseinfo.outgoingBigCustomer.outgoingBigCustomerQueryForm', {
			extend : 'Ext.form.Panel',
			title : '查询条件',
			frame : true,
			height : 120,
			defaults : {
				margin : '20 0 0 10',
				colspan : 1
			},
			defaultType : 'textfield',
			layout : {
				type : 'column',
				columns : 3
			},
			items : [{
						fieldLabel : '客户名称',
						name : 'cusName',
						labelWidth : 80,
						columnWidth : .26
					}, {
						fieldLabel : '客户编码',
						name : 'cusCode',
						labelWidth : 80,
						columnWidth : .26
					}, {
						xtype : 'button',
						tooltip : '重置',
						text : '重置',
						columnWidth : .1,
						handler : function() {
							var form = this.up('form').getForm();
							form.reset();
						}
					}, {
						xtype : 'button',
						width : 70,
						columnWidth : .1,
						tooltip : '查询',
						text : '查询',// 查询
						cls : 'yellow_button',
						handler : function() {
							Ext
									.getCmp('T_baseinfo-outgoingBigCustomerIndex_content')
									.getOutgoingBigCustomesGrid()
									.getPagingToolbar().moveFirst();
						}
					}]
		});

/**
 * 外发大客户Store的Model
 */
Ext.define('Foss.baseinfo.outgoingBigCustomer.outgoingBigCustomerModel', {
			extend : 'Ext.data.Model',
			fields : [{
						name : 'id',
						type : 'string'
					}, {
						name : 'outgoingBigCustomersCode',
						type : 'string'
					}, {
						name : 'outgoingBigCustomersName',
						type : 'string'
					}, {
						name : 'active',
						type : 'string'
					}, {
						name : 'activeStr',
						type : 'string'
					}, {
						name : 'outgoingBigCustomersRemark',
						type : 'string'
					}]
		});

/**
 * 外发大客户列表Store
 */
Ext.define('Foss.baseinfo.outgoingBigCustomer.outgoingBigCustomerStore', {
			extend : 'Ext.data.Store',
			model : 'Foss.baseinfo.outgoingBigCustomer.outgoingBigCustomerModel',// 
			pageSize : 10,
			proxy : {
				type : 'ajax',
				actionMethods : 'post',
				url : baseinfo
						.realPath('queryOutgoingBigCustomersByCondition.action'),// 请求地址
				reader : {
					type : 'json',
					root : 'outgoingBigCustomersVo.outgoingBigCustomersEntityList',// 获取的数据
					totalProperty : 'totalCount'// 总个数
				}
			}
		});

/**
 * 新增外发大客户表单
 */
Ext.define(
		'Foss.baseinfo.outgoingBigCustomer.AddOrUpdateOutgoingBigCustomerForm',
		{
			extend : 'Ext.form.Panel',
			// title:'新增外发大客户',
			height : '600',
			width : '800',
			frame : true,
			defaultType : 'textfield',
			defaults : {
				margin : '5 5 5 0',
				labelWidth : 85
			},
			layout : {
				type : 'table',
				columns : 1
			},
			formRecord : null,
			items : [{
						fieldLabel : '客户编码',
						xtype : 'commoncustomerselector',
						name : 'outgoingBigCustomersName',
						columnWidth : .45,
						allowBlank : false
					}, {
						fieldLabel : '备注',
						xtype : 'textarea',
						allowBlank : true,
						name : 'outgoingBigCustomersRemark'
					}, {
						fieldLabel : '是否有效',
						name : 'active',
						xtype : 'checkbox',
						colspan : 1,
						allowBlank : true
					}, {
						fieldLabel : '客户',
						allowBlank : true,
						name : 'outgoingBigCustomersCode',
						hidden : true
					}]
		});

/**
 * 新增外发大客户窗口
 */
Ext.define('Foss.baseinfo.outgoingBigCustomer.OutgoingBigCustomerWin', {
	extend : 'Ext.window.Window',
	title : '新增',
	closable : true,
	modal : true,
	layout : 'auto',
	resizable : false,
	closeAction : 'hide',
	width : 400,
	height : 250,
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	form : null,
	getForm : function() {
		if (this.form == null) {
			this.form = Ext
					.create('Foss.baseinfo.outgoingBigCustomer.AddOrUpdateOutgoingBigCustomerForm');
		}
		return this.form;
	},
	listeners : {
		beforehide : function(me) {
			// 关闭窗口的时候清空数据，由于不能清本外场文本框，所以重置另外一个框
			this.down('form').getForm().reset();// 表格重置
		}
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.items = [me.getForm()];
		me.fbar = me.getFbar();
		me.callParent([cfg]);
	},
	getFbar : function() {
		var me = this;
		return [{
					width : 60,
					text : '取消', // 取消
					handler : function() {
						me.close();
					}
				}, {
					text : '保存', // 保存
					width : 60,
					cls : 'yellow_button',
					handler : function() {
						// saveOutgoingBigCustomer(me);
						var form = me.down('form').getForm();
						if (form.isValid()) {
							var params = {
								'outgoingBigCustomersVo' : {
									'outgoingBigCustomersEntity' : {
										'outgoingBigCustomersName' : form
												.findField('outgoingBigCustomersName')
												.getRawValue()
									}
								}
							};

							params.outgoingBigCustomersVo.outgoingBigCustomersEntity.outgoingBigCustomersCode = form
									.findField('outgoingBigCustomersName')
									.getValue();
							params.outgoingBigCustomersVo.outgoingBigCustomersEntity.outgoingBigCustomersRemark = form
									.findField('outgoingBigCustomersRemark')
									.getValue();
							if (form.findField('active').getValue()) {
								params.outgoingBigCustomersVo.outgoingBigCustomersEntity.active = 'Y';
							} else {
								params.outgoingBigCustomersVo.outgoingBigCustomersEntity.active = 'N';
							}
							// params.outgoingBigCustomersVo.outgoingBigCustomersEntity.active=form.findField('active').getValue()=true?'Y':'N';
							params.outgoingBigCustomersVo.outgoingBigCustomersEntity.createUserCode = FossUserContext
									.getCurrentUserEmp().empCode;
							var successFun = function(json) {
								baseinfo.showInfoMes("保存成功");// 提示保存成功
								me.close();
								Ext
										.getCmp('T_baseinfo-outgoingBigCustomerIndex_content')
										.getOutgoingBigCustomesGrid()
										.getPagingToolbar().moveFirst();
							};
							var failureFun = function(json) {
								if (Ext.isEmpty(json)) {
									baseinfo.showErrorMes('请求超时');// 请求超时
								} else {
									baseinfo.showErrorMes(json.message);// 提示失败原因
								}
							};
							var ajaxUrl = baseinfo
									.realPath('createOutgoingBigCustomers.action');
							baseinfo.requestJsonAjax(ajaxUrl, params,
									successFun, failureFun);// 发送AJAX请求
						}
					}
				}];
	}
});

/**
 * 修改窗口
 */
Ext.define('Foss.baseinfo.outgoingBigCustomer.OutgoingBigCustomerUpdateWin', {
	extend : 'Ext.window.Window',
	title : '修改',// 修改
	closable : true,
	modal : true,
	layout : 'auto',
	resizable : false,
	closeAction : 'hide',
	width : 400,
	height : 250,
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	form : null,
	getForm : function() {
		if (this.form == null) {
			this.form = Ext
					.create('Foss.baseinfo.outgoingBigCustomer.AddOrUpdateOutgoingBigCustomerForm');
		}
		return this.form;
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.items = [me.getForm()];
		me.fbar = me.getFbar();
		me.callParent([cfg]);
	},
	getFbar : function() {
		var me = this;
		return [{
					width : 60,
					text : '取消', // 取消
					handler : function() {
						me.close();
					}
				}, {
					text : '保存', // 保存
					width : 60,
					cls : 'yellow_button',
					handler : function() {
						// saveOutgoingBigCustomer(me);
						var form = me.down('form').getForm();
						if (form.isValid()) {
							var params = {
								'outgoingBigCustomersVo' : {
									'outgoingBigCustomersEntity' : {
										'outgoingBigCustomersName' : form
												.findField('outgoingBigCustomersName')
												.getRawValue()
									}
								}
							};

							params.outgoingBigCustomersVo.outgoingBigCustomersEntity.outgoingBigCustomersCode = form
									.findField('outgoingBigCustomersCode')
									.getValue();
							params.outgoingBigCustomersVo.outgoingBigCustomersEntity.outgoingBigCustomersRemark = form
									.findField('outgoingBigCustomersRemark')
									.getValue();
							// params.outgoingBigCustomersVo.outgoingBigCustomersEntity.active
							// = form.findField('active').getValue();
							params.outgoingBigCustomersVo.outgoingBigCustomersEntity.modifyUserCode = FossUserContext
									.getCurrentUserEmp().empCode;
							if (form.findField('active').getValue()) {
								params.outgoingBigCustomersVo.outgoingBigCustomersEntity.active = 'Y';
							} else {
								params.outgoingBigCustomersVo.outgoingBigCustomersEntity.active = 'N';
							}
							var successFun = function(json) {
								baseinfo.showInfoMes("修改成功");// 提示保存成功
								me.close();
								Ext
										.getCmp('T_baseinfo-outgoingBigCustomerIndex_content')
										.getOutgoingBigCustomesGrid()
										.getPagingToolbar().moveFirst();
							};
							var failureFun = function(json) {
								if (Ext.isEmpty(json)) {
									baseinfo.showErrorMes('请求超时');// 请求超时
								} else {
									baseinfo.showErrorMes(json.message);// 提示失败原因
								}
							};
							var ajaxUrl = baseinfo
									.realPath('updateOutgoingBigCustomers.action');
							baseinfo.requestJsonAjax(ajaxUrl, params,
									successFun, failureFun);// 发送AJAX请求
						}
					}
				}];
	}
});

/**
 * 外发大客户列表
 */
Ext.define('Foss.baseinfo.outgoingBigCustomer.outgoingBigCustomerGrid', {
	extend : 'Ext.grid.Panel',
	title : '查询结果列表',
	frame : true,
	flex : 1,
	sortableColumns : false,
	enableColumnHide : false,
	enableColumnMove : false,
	stripeRows : true, // 交替行效果
	selType : "rowmodel", // 选择类型设置为：行选择
	emptyText : "查询结果为空",// 查询结果为空
	pagingToolbar : null,
	getPagingToolbar : function() {
		if (this.pagingToolbar == null) {
			this.pagingToolbar = Ext.create('Deppon.StandardPaging', {
						store : this.store,
						pageSize : 10
					});
		}
		return this.pagingToolbar;
	},
	// 新增窗口
	getOutgoingBigCustomerWin : null,
	getOutgoingBigCustomerAddWindow : function() {
		if (this.getOutgoingBigCustomerWin == null) {
			this.getOutgoingBigCustomerWin = Ext
					.create('Foss.baseinfo.outgoingBigCustomer.OutgoingBigCustomerWin');
			this.getOutgoingBigCustomerWin.parent = this;// 父元素
		}
		/*
		 * var win = Ext.create(
		 * 'Foss.baseinfo.outgoingBigCustomer.OutgoingBigCustomerWin', { 'title' :
		 * title }); return win;
		 */
		return this.getOutgoingBigCustomerWin;
	},
	// 修改窗口
	OutgoingBigCustomerUpdateWin : null,
	OutgoingBigCustomerUpdateWindow : function() {
		if (this.OutgoingBigCustomerUpdateWin == null) {
			this.OutgoingBigCustomerUpdateWin = Ext
					.create('Foss.baseinfo.outgoingBigCustomer.OutgoingBigCustomerUpdateWin');
			this.OutgoingBigCustomerUpdateWin.parent = this;// 父元素
		}
		return this.OutgoingBigCustomerUpdateWin;
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.columns = [{
					xtype : 'rownumberer',
					width : 40,
					text : '序号'// 序号
				}, {
					text : '操作',// 操作
					xtype : 'actioncolumn',
					align : 'center',
					width : 80,
					items : [{
						iconCls : 'deppon_icons_edit',
						tooltip : '修改',// 修改
						width : 42,
						handler : function(grid, rowIndex, colIndex) {
							var record = grid.getStore().getAt(rowIndex);
							var updatewindow = me
									.OutgoingBigCustomerUpdateWindow();
							updatewindow.outgoingBigCustomersName = record.data.outgoingBigCustomersName
									+ "/"
									+ record.data.outgoingBigCustomersCode;
							updatewindow.outgoingBigCustomersRemark = record.data.outgoingBigCustomersRemark;
							updatewindow.active = record.data.active;
							var form = updatewindow.down('form').getForm();
							// form.findField('ID').setValue(record.data.id);
							form
									.findField('outgoingBigCustomersName')
									.setValue(record.data.outgoingBigCustomersName);
							form
									.findField('outgoingBigCustomersCode')
									.setValue(record.data.outgoingBigCustomersCode);
							form
									.findField('outgoingBigCustomersRemark')
									.setValue(record.data.outgoingBigCustomersRemark);
							if("Y" == record.data.active){
								form.findField('active')
								.setValue(true);
							} else {
								form.findField('active')
								.setValue(false);
							}
							form.findField('outgoingBigCustomersName')
									.setReadOnly(true);
							updatewindow.show();
						}
					}]
				}, {
					header : '客户编码',
					dataIndex : 'outgoingBigCustomersCode'
				}, {
					header : '客户名称',
					dataIndex : 'outgoingBigCustomersName'
				}, {
					header : '是否为有效客户',
					dataIndex : 'activeStr'
				}, {
					header : '备注',
					dataIndex : 'outgoingBigCustomersRemark'
				}, {
					header : 'ID',
					dataIndex : 'id',
					hidden : true
				}];

		me.store = Ext.create(
				'Foss.baseinfo.outgoingBigCustomer.outgoingBigCustomerStore', {
					autoLoad : false,// 不自动加载
					pageSize : 10,
					listeners : {
						beforeload : function(store, operation, eOpts) {
							var queryForm = me.up()
									.getExpressPartSalesDeptQueryForm();
							if (queryForm != null) {
								Ext.apply(operation, {
									params : {// 查询条件
										'outgoingBigCustomersVo.outgoingBigCustomersEntity.outgoingBigCustomersCode' : queryForm
												.getForm().findField('cusCode')
												.getValue(),
										'outgoingBigCustomersVo.outgoingBigCustomersEntity.outgoingBigCustomersName' : queryForm
												.getForm().findField('cusName')
												.getValue()
									}
								});
							}
						}
					}
				});
		me.listeners = {
			scrollershow : function(scroller) {
				if (scroller && scroller.scrollEl) {
					scroller.clearManagedListeners();
					scroller.mon(scroller.scrollEl, 'scroll',
							scroller.onElScroll, scroller);
				}
			}
		}, me.selModel = Ext.create('Ext.selection.CheckboxModel', {// 多选框
			mode : 'MULTI',
			checkOnly : true
		});
		me.tbar = [{
					text : '新增',// 新增
					handler : function() {
						me.getOutgoingBigCustomerAddWindow().show();
					}
				}];
		me.bbar = me.getPagingToolbar();
		me.callParent([cfg]);
	}
});

/**
 * 初始化界面
 */
Ext.onReady(function() {
	Ext.QuickTips.init();

	if (Ext.getCmp('T_baseinfo-outgoingBigCustomerIndex_content')) {
		return;
	}

	var outgoingBigCustomerQueryForm = Ext
			.create('Foss.baseinfo.outgoingBigCustomer.outgoingBigCustomerQueryForm');

	// 外发客户的列表grid
	var outgoingBigCustomerGrid = Ext
			.create('Foss.baseinfo.outgoingBigCustomer.outgoingBigCustomerGrid');

	Ext.create('Ext.panel.Panel', {
				id : 'T_baseinfo-outgoingBigCustomerIndex_content',
				cls : 'panelContentNToolbar',
				bodyCls : 'panelContentNToolbar-body',
				layout : 'auto',
				getExpressPartSalesDeptQueryForm : function() {
					return outgoingBigCustomerQueryForm;
				},
				getOutgoingBigCustomesGrid : function() {
					return outgoingBigCustomerGrid;
				},
				items : [outgoingBigCustomerQueryForm, outgoingBigCustomerGrid],
				renderTo : 'T_baseinfo-outgoingBigCustomerIndex-body'
			});
});