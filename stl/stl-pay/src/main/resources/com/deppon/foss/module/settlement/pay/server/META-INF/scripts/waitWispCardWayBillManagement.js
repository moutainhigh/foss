// 当前登录部门
pay.wscWayBillManagement.dept = FossUserContext.getCurrentUserDept();
// 当前登录用户
pay.wscWayBillManagement.user = FossUserContext.getCurrentUser();
// 当前用户角色编号
pay.wscWayBillManagement.roles = FossUserContext.getCurrentUserRoleCodes();

// 导出按钮
pay.wscWayBillManagement.dataExport = function() {
	var exportBtn = this;
	var grid = Ext.getCmp('T_pay-wscWayBillManagement_content').getDataGrid();
	// 没有查询到数据不予导出
	if (null == grid || grid.getStore().data.length < 1) {
		Ext.Msg.alert(pay.wscWayBillManagement.i18n('foss.stl.pay.common.alert'), pay.wscWayBillManagement.i18n('foss.stl.pay.wscWayBillManagement.exportFailAlertByNoData'));
		return false;
	}
	// 获取选中的待刷卡运单数据
	var selections = grid.getSelectionModel().getSelection();
	// 如果未选中数据，提示至少选择一条记录进行操作
	if (null == selections || 0 == selections.length) {
		Ext.Msg.alert(pay.wscWayBillManagement.i18n('foss.stl.pay.common.alert'), pay.wscWayBillManagement.i18n('foss.stl.pay.wscWayBillManagement.exportFailAlertByNoSelectedData'));
		return false;
	}
	// 选择的待刷卡运单ID
	var exportWscWayBillIds = [];
	for (var i = 0; i < selections.length; i++) {
		// TODO: 过滤不符合的数据
		// 将符合条件的待刷卡运单ID封进数组
		if ("" != selections[i].get('id')) {
			exportWscWayBillIds.push(selections[i].get('id'));
		}
	}
	// 导出操作确认
	Ext.Msg.confirm(pay.wscWayBillManagement.i18n('foss.stl.pay.common.alert'), pay.wscWayBillManagement.i18n('foss.stl.pay.wscWayBillManagement.isConfirmExportAlert'), function(btn, text) {
				if (btn == 'yes') {
					var params = grid.store.searchParams;
					// 创建导出查询表单
					if (!Ext.fly('wscWayBillListExportForm')) {
						var frm = document.createElement('form');
						frm.id = 'wscWayBillListExportForm';
						frm.style.display = 'none';
						document.body.appendChild(frm);
					}
					// 禁用导出按钮
					exportBtn.disable(false);
					// 10秒后自动解除禁用
					setTimeout(function() {
								exportBtn.enable(true);
							}, 10000);
					// 查询导出数据
					Ext.Ajax.request({
								url : pay.realPath('exportWscWayBillList.action'),
								form : Ext.fly('wscWayBillListExportForm'),
								params : {
									'exportWscWayBillIds' : exportWscWayBillIds
								},
								method : 'post',
								isUpload : true,
								success : function(response) {
									var result = Ext.decode(response.responseText);
								},
								failure : function(response) {
									var result = Ext.decode(response.responseText);
								}
							});
				}
			});

};

// 9.重置按钮
pay.wscWayBillManagement.queryReset = function(btn) {
	// 重置Form表单
	btn.up('form').getForm().reset();
	// 调用查询按钮
	var searchBtn = Ext.getCmp('search_btn');
	if (null != searchBtn) {
		pay.wscWayBillManagement.queryBySearchCondition(searchBtn.up('form').getForm(), searchBtn);
	}
};

// 8.清除grid数据
pay.wscWayBillManagement.clearGrid = function() {
	// 获取数据grid对象
	var dataGrid = Ext.getCmp('T_pay-wscWayBillManagement_content').getDataGrid();
	dataGrid.store.removeAll();
}

// 7.查询成功
pay.wscWayBillManagement.querySuccess = function(btn) {
	// 禁用查询按钮
	btn.disable(false);
	// 30s超时,按钮恢复
	setTimeout(function() {
				btn.enable(true);
			}, 30000);
	// 获取数据grid对象
	var dataGrid = Ext.getCmp('T_pay-wscWayBillManagement_content').getDataGrid();
	// 加载页面
	dataGrid.store.loadPage(1, {
				callback : function(records, operation, success) {
					// 查询异常提示
					var rawData = dataGrid.store.proxy.reader.rawData;
					if (!success) {
						if (null != rawData && !rawData.isException) {
							Ext.Msg.alert(pay.wscWayBillManagement.i18n('foss.stl.pay.common.alert'), rawData.message);
						}
						btn.enable(true);
						return false;
					}
					// 查询成功
					if (success) {
						var result = Ext.decode(operation.response.responseText);
						// vo不为空或空值
						if (!Ext.isEmpty(result.vo.wscWayBillDto.resultList) && result.vo.wscWayBillDto.resultList.length > 0) {
							dataGrid.show();

							// 设置合计金额
							var totalAmountTextfield = Ext.getCmp('totalAmount');
							if (null != totalAmountTextfield) {
								totalAmountTextfield.setValue(result.vo.wscWayBillDto.totalAmount);
							}
						}
					}
					btn.enable(true);
				}
			});
}

// 6.查询按钮
pay.wscWayBillManagement.queryBySearchCondition = function(searchForm, btn) {
	// 表单验证
	if (searchForm.isValid()) {
		// 表单验证通过,获取表单查询字段
		pay.wscWayBillManagement.startDate = searchForm.getValues().startDate;
		pay.wscWayBillManagement.endDate = searchForm.getValues().endDate;
		pay.wscWayBillManagement.wayBillNo = searchForm.getValues().wayBillNo;
		pay.wscWayBillManagement.wayBillSource = searchForm.getValues().wayBillSource;
		pay.wscWayBillManagement.paymentStatus = searchForm.getValues().paymentStatus;
		pay.wscWayBillManagement.active = searchForm.getValues().active;
		// 验证开单日期范围
		var startDate = pay.wscWayBillManagement.startDate;
		var endDate = pay.wscWayBillManagement.endDate;
		var compareTwoDate = stl.compareTwoDate(startDate, endDate);
		if (compareTwoDate > stl.DATELIMITDAYS_MONTH) {
			// 开始结束日期间隔不能大于31
			Ext.Msg.alert(pay.wscWayBillManagement.i18n('foss.stl.pay.common.alert'), pay.wscWayBillManagement.i18n('foss.stl.pay.wscWayBillManagement.queryDateMaxLimit'));
			return false;
		} else if (compareTwoDate < 1) {
			// 开始日期不能大于结束日期
			Ext.Msg.alert(pay.wscWayBillManagement.i18n('foss.stl.pay.common.alert'), pay.wscWayBillManagement.i18n('foss.stl.pay.common.dateEndBeforeStatrtWarining'));
			return false;
		}
		// 去后台查询
		pay.wscWayBillManagement.clearGrid();
		pay.wscWayBillManagement.querySuccess(btn);
	} else {
		// 表单验证不通过,提示
		Ext.Msg.alert(pay.wscWayBillManagement.i18n('foss.stl.pay.common.alert'), pay.wscWayBillManagement.i18n('foss.stl.pay.common.validateFailAlert'));
		return false;
	}
};

// 5.定义待刷卡运单数据源store的数据模型model
Ext.define('foss.stl.pay.wscWayBillManagement.wscWayBillGridModel', {
			extend : 'Ext.data.Model',
			fields : [{
						name : 'id'
					}, {
						name : 'wayBillNo'
					}, {
						name : 'waitSwipeAmount'
					}, {
						name : 'createBillOrgName'
					}, {
						name : 'createBillTime'
					}, {
						name : 'swipeCardOrgName'
					}, {
						name : 'alreadySwipeAmount'
					}, {
						name : 'paymentStatus'
					}, {
						name : 'wayBillSource'
					}]

		});

// 4.定义待刷卡运单grid数据源store
Ext.define('foss.stl.pay.wscWayBillManagement.wscWayBillGridStore', {
			extend : 'Ext.data.Store',
			model : 'foss.stl.pay.wscWayBillManagement.wscWayBillGridModel',
			sorters : [{
						property : 'createTime', // 排序字段
						direction : 'ASC' // 排序规则
					}],
			proxy : {
				type : 'ajax',
				url : pay.realPath('queryWscWayBill.action'),
				actionMethods : 'post',
				reader : {
					type : 'json',
					root : 'vo.wscWayBillDto.resultList',
					totalProperty : 'totalCount'
				}
			},
			listeners : {
				'beforeLoad' : function(store, operation, eOpts) {
					// 查询参数
					var searchParams;
					// 配置查询参数
					searchParams = {
						'vo.wscWayBillDto.queryStartDate' : pay.wscWayBillManagement.startDate,
						'vo.wscWayBillDto.queryEndDate' : pay.wscWayBillManagement.endDate,
						'vo.wscWayBillDto.wayBillNo' : pay.wscWayBillManagement.wayBillNo,
						'vo.wscWayBillDto.wayBillSource' : pay.wscWayBillManagement.wayBillSource,
						'vo.wscWayBillDto.paymentStatus' : pay.wscWayBillManagement.paymentStatus,
						'vo.wscWayBillDto.active' : pay.wscWayBillManagement.active
					};
					// 设置查询参数
					Ext.apply(operation, {
								params : searchParams
							});
				}
			}
		});

// 3.定义待刷卡运单grid对象
Ext.define('foss.stl.pay.wscWayBillManagement.dataGrid', {
			extend : 'Ext.grid.Panel',
			title : pay.wscWayBillManagement.i18n('foss.stl.pay.wscWayBillManagement.wscWayBill'),
			frame : true,
			height : 500,
			bodyCls : 'autoHeight',
			cls : 'autoHeight',
			store : Ext.create('foss.stl.pay.wscWayBillManagement.wscWayBillGridStore'), // 待刷卡运单数据grid数据源
			selModel : Ext.create('Ext.selection.CheckboxModel'),
			defaults : {
				align : 'center',
				margin : '5 0 5 0'
			},
			viewConfig : {
				// 设置行可以选择，进而可以复制
				enableTextSelection : true
			},
			// 定义列,dataIndex对应Model中的field
			columns : [{
						header : pay.wscWayBillManagement.i18n('foss.stl.pay.wscWayBillManagement.queryWscWayBill.gridId'), // 序号
						flex : 0.5,
						align : 'center',
						// 自增长行号
						xtype : 'rownumberer'
					}, {
						header : pay.wscWayBillManagement.i18n('foss.stl.pay.wscWayBillManagement.queryWscWayBill.wayBillNo'), // 运单号
						flex : 1,
						align : 'center',
						dataIndex : 'wayBillNo'
					}, {
						header : pay.wscWayBillManagement.i18n('foss.stl.pay.wscWayBillManagement.queryWscWayBill.waitSwipAmount'), // 待刷卡金额
						flex : 1,
						align : 'center',
						dataIndex : 'waitSwipeAmount'
					}, {
						header : pay.wscWayBillManagement.i18n('foss.stl.pay.wscWayBillManagement.queryWscWayBill.createBillOrgName'), // 开单部门名称
						flex : 1.2,
						align : 'center',
						dataIndex : 'createBillOrgName'
					}, {
						header : pay.wscWayBillManagement.i18n('foss.stl.pay.wscWayBillManagement.queryWscWayBill.createBillTime'), // 开单时间
						flex : 2,
						align : 'center',
						dataIndex : 'createBillTime',
						renderer : function(value) {
							return stl.dateFormat(value, 'Y-m-d H:i:s');
						}
					}, {
						header : pay.wscWayBillManagement.i18n('foss.stl.pay.wscWayBillManagement.queryWscWayBill.swipCardOrgName'), // 刷卡部门
						flex : 1.2,
						align : 'center',
						dataIndex : 'swipeCardOrgName',
						renderer : function(value) {
							if (null == value || value == '') {
								return "-";
							} else {
								return value;
							}
						}
					}, {
						header : pay.wscWayBillManagement.i18n('foss.stl.pay.wscWayBillManagement.queryWscWayBill.alreadySwipAmount'), // 已刷卡金额
						flex : 1,
						align : 'center',
						dataIndex : 'alreadySwipeAmount'
					}, {
						header : pay.wscWayBillManagement.i18n('foss.stl.pay.wscWayBillManagement.queryWscWayBill.paymentStatus'), // 支付状态
						flex : 1,
						align : 'center',
						dataIndex : 'paymentStatus',
						renderer : function(value) {
							if ("Y" == value) {
								return "已支付";
							} else if ("N" == value) {
								return "未支付"
							} else {
								return value;
							}
						}
					}, {
						header : pay.wscWayBillManagement.i18n('foss.stl.pay.wscWayBillManagement.queryWscWayBill.wayBillSource'), // 数据来源
						flex : 1,
						align : 'center',
						dataIndex : 'wayBillSource',
						renderer : function(value) {
							if ("1" == value) {
								return "运单开单";
							} else if ("2" == value) {
								return "运单更改"
							} else {
								return value;
							}
						}
					}],
			listeners : { // 监听事件
				'click' : function() {
					alert("点击事件");
				}
			},
			initComponent : function() {
				var me = this;
				me.dockedItems = [{
							xtype : 'toolbar', // 顶部toolbar
							dock : 'top',
							layout : 'column',
							defaults : {
								margin : '0 0 5 3'
							},
							items : [{
										xtype : 'container', // 占位div
										html : '&nbsp;',
										// style : 'border:1px solid red;',
										columnWidth : .9
									}, {
										xtype : 'button', // 导出按钮
										id : 'export_btn',
										text : pay.wscWayBillManagement.i18n('foss.stl.pay.common.export'),
										columnWidth : .09,
										// hidden : true,
										// disabled : true,
										// disabled :
										// !pay.wscWayBillManagement.isPermission('/stl-web/pay/exportWscWayBillList.action'),
										// hidden :
										// !pay.wscWayBillManagement.isPermission('/stl-web/pay/exportWscWayBillList.action'),
										handler : pay.wscWayBillManagement.dataExport
									}]
						}, {
							xtype : 'toolbar', // 底部toolbar
							dock : 'bottom',
							// style : 'border:1px solid blue;',
							layout : 'column',
							defaults : {
								margin : '0 0 5 3'
							},
							items : [{
										xtype : 'textfield', // 金额合计
										// style : 'border:1px solid red;',
										readOnly : true,
										id : 'totalAmount',
										columnWidth : .3,
										labelWidth : 40,
										labelAlign : 'right',
										fieldLabel : pay.wscWayBillManagement.i18n('foss.stl.pay.wscWayBillManagement.total')
									}, {
										xtype : 'standardpaging', // 分页
										// style : 'border:1px solid red;',
										store : me.store,
										columnWidth : 1,
										plugins : Ext.create('Deppon.ux.PageSizePlugin', {
													// 设置分页记录最大值，防止输入过大的数值
													maximumSize : 1000,
													sizeList : [['10', 10], ['25', 25], ['50', 50], ['100', 100], ['200', 200], ['500', 500], ['1000', 1000]]
												})
									}]
						}];
				me.callParent();
			}
		});

// 2.查询表单
Ext.define('foss.stl.pay.wscWayBillManagement.queryForm', {
			extend : 'Ext.form.Panel',
			title : pay.wscWayBillManagement.i18n('foss.stl.pay.wscWayBillManagement.searchCondition'),
			frame : true,
			collapsible : true,
			defaults : {
				columnWidth : .3,
				margin : '8 10 5 10',
				anchor : '100%'
			},
			// style : 'border: 1px solid red;',
			height : 'auto',
			defaultType : 'textfield',
			layout : 'column',
			items : [{
						xtype : 'datefield', // 开始日期
						name : 'startDate',
						fieldLabel : pay.wscWayBillManagement.i18n('foss.stl.pay.wscWayBillManagement.startDate'),
						labelWidth : 85,
						labelAlign : 'right',
						columnWidth : .3,
						allowBlank : false,
						format : 'Y-m-d',
						editable : false,
						value : new Date()
					}, {
						xtype : 'datefield', // 结束日期
						name : 'endDate',
						fieldLabel : pay.wscWayBillManagement.i18n('foss.stl.pay.wscWayBillManagement.endDate'),
						labelWidth : 85,
						labelAlign : 'right',
						columnWidth : .3,
						allowBlank : false,
						format : 'Y-m-d',
						editable : false,
						value : new Date()
					}, {
						xtype : 'textfield', // 运单号
						name : 'wayBillNo',
						fieldLabel : pay.wscWayBillManagement.i18n('foss.stl.pay.wscWayBillManagement.wayBillNo'),
						labelWidth : 85,
						labelAlign : 'right',
						columnWidth : .3,
						value : ''
					}, {
						xtype : 'combobox', // 数据来源{1-运单开单 2-运单更改}
						name : 'wayBillSource',
						fieldLabel : pay.wscWayBillManagement.i18n('foss.stl.pay.wscWayBillManagement.wayBillSource'),
						labelWidth : 85,
						labelAlign : 'right',
						editable : false,
						// 下拉框数据
						store : Ext.create("Ext.data.Store", {
									fields : ['valueName', 'valueCode'],
									data : [{
												valueName : pay.wscWayBillManagement.i18n('foss.stl.pay.wscWayBillManagement.all'),
												valueCode : 'ALL'
											}, {
												valueName : pay.wscWayBillManagement.i18n('foss.stl.pay.wscWayBillManagement.wayBillSourceCreate'),
												valueCode : '1'
											}, {
												valueName : pay.wscWayBillManagement.i18n('foss.stl.pay.wscWayBillManagement.wayBillSourceModify'),
												valueCode : '2'
											}]
								}),

						value : 'ALL',
						queryModel : 'local',
						displayField : 'valueName',
						valueField : 'valueCode',
						columnWidth : .3
					}, {
						xtype : 'combobox', // 支付状态
						name : 'paymentStatus',
						fieldLabel : pay.wscWayBillManagement.i18n('foss.stl.pay.wscWayBillManagement.pamentStatus'),
						labelWidth : 85,
						labelAlign : 'right',
						editable : false,
						// 下拉框数据
						store : Ext.create("Ext.data.Store", {
									fields : ['valueName', 'valueCode'],
									data : [{
												valueName : pay.wscWayBillManagement.i18n('foss.stl.pay.wscWayBillManagement.all'),
												valueCode : 'ALL'
											}, {
												valueName : pay.wscWayBillManagement.i18n('foss.stl.pay.wscWayBillManagement.pamentStatusYes'),
												valueCode : 'Y'
											}, {
												valueName : pay.wscWayBillManagement.i18n('foss.stl.pay.wscWayBillManagement.pamentStatusNo'),
												valueCode : 'N'
											}]
								}),

						value : 'ALL',
						queryModel : 'local',
						displayField : 'valueName',
						valueField : 'valueCode',
						columnWidth : .3
					}, {
						xtype : 'container', // 按钮div
						columnWidth : 1,
						border : 1,
						defaultType : 'button',
						layout : 'column',
						items : [{
									text : pay.wscWayBillManagement.i18n('foss.stl.pay.common.reset'),
									columnWidth : .1,
									id : 'reset_btn',
									handler : function() {
										pay.wscWayBillManagement.queryReset(this);
									}
								}, {
									xtype : 'container',
									border : false,
									html : '&nbsp;',
									columnWidth : .7
								}, {
									text : pay.wscWayBillManagement.i18n('foss.stl.pay.common.query'),
									cls : 'yellow_button',
									columnWidth : .1,
									id : 'search_btn',
									handler : function() {
										pay.wscWayBillManagement.queryBySearchCondition(this.up('form').getForm(), this);
									}
								}]
					}]

		});

// 1.初始化界面
Ext.onReady(function() {
			// 初始化tips
			Ext.QuickTips.init();
			// 判断组件是否已存在
			if (Ext.getCmp('T_pay-wscWayBillManagement_content')) {
				// 重新打开content不能重新渲染到div，临时解决销毁已存在的组件，重新创建
				Ext.getCmp('T_pay-wscWayBillManagement_content').destroy();
				// Ext.getCmp('T_pay-wscWayBillManagement_content').render();
				// return;
			}
			// 查询表单
			var queryForm = Ext.create('foss.stl.pay.wscWayBillManagement.queryForm');
			// 数据表格
			var dataGrid = Ext.create('foss.stl.pay.wscWayBillManagement.dataGrid');
			// 定义一个panel
			pay.wscWayBillManagement.panel = Ext.create("Ext.panel.Panel", {
						id : 'T_pay-wscWayBillManagement_content',
						cls : 'panelContentNToolbar',
						bodyCls : 'panelContentNToolbar-body',
						layout : 'auto',
						// 获取数据表格对象
						getDataGrid : function() {
							return dataGrid;
						},
						// 获得查询表单对象
						getQueryForm : function() {
							return queryForm;
						},
						items : [queryForm, dataGrid],
						// 渲染组件到div上
						renderTo : 'T_pay-wscWayBillManagement-body'
					});
			// 页面加载完成调用数据查询按钮事件
			var searchBtn = Ext.getCmp('search_btn');
			if (null != searchBtn) {
				pay.wscWayBillManagement.queryBySearchCondition(searchBtn.up('form').getForm(), searchBtn);
			}
		});