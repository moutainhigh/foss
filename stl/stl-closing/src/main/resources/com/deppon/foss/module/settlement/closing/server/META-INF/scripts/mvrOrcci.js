/**
 * 获取上个月期间
 */
closing.mvrOrcci.getLastMonthPeriod = function() {
	var now = new Date();
	now.setMonth(now.getMonth() - 1);
	return Ext.Date.format(now, 'Ym');
}

/**
 * 声明账期model
 */
Ext.define('Foss.mvrOrcci.PeriodModel', {
	extend : 'Ext.data.Model',
	fields : [ 'name', 'code' ]
});

/**
 * 声明账期store
 */
Ext.define('Foss.mvrOrcci.PeriodStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.mvrOrcci.PeriodModel',
	proxy : {
		type : 'ajax',
		url : closing.realPath('queryClosingPeriod.action'),
		actionMethods : 'post',
		reader : {
			type : 'json',
			root : 'periodList'
		}
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.listeners = {
			'load' : function(store, operation, eOpts) {

				if (operation.length == 0) {
					Ext.Msg.alert("提示", "没有生成凭证报表数据，凭证期间为空");
					return false;
				}
			}
		};
		me.callParent([ cfg ]);
	}
});

/**
 * 按日期查询
 */
closing.mvrOrcci.queryReport = function(form, me) {
	// 判断是否合法
	if (form.isValid()) {
		var grid = Ext.getCmp('T_closing-mvrOrcci_content').getGrid();
		closing.mvrOrcci.period = form.findField('period').getValue();
		closing.mvrOrcci.deptCode = form.findField('deptCode').getValue();
		closing.mvrOrcci.orgType = form.findField('orgType').getValue();
		grid.store.removeAll();
		// 设置该按钮灰掉
		me.disable(false);
		// 30秒后自动解除灰掉效果
		setTimeout(function() {
			me.enable(true);
		}, 30000);
		grid.store.loadPage(1, {
			callback : function(records, operation, success) {
				var rawData = grid.store.proxy.reader.rawData;
				// 当success:false ,isException:false --业务异常
				if (!success && !rawData.isException) {
					Ext.Msg.alert('提示信息', rawData.message);
					me.enable(true);
					return false;
				}
				if (success) {
					var result = Ext.decode(operation.response.responseText);
					if (!Ext.isEmpty(result.vo.list)
							&& result.vo.list.length > 0) {
						grid.show();
					}
				}
				me.enable(true);
			}
		});
	} else {
		Ext.Msg.alert('参数不合法','提示');
		return false;
	}
}

/**
 * 重置
 */
closing.mvrOrcci.reset = function() {
	this.up('form').getForm().reset();
}
/**
 * 导出excel
 */
closing.mvrOrcci.exportRfi = function() {

	// 获取主面板、查询GRID
	var mainPane = Ext.getCmp('T_closing-mvrOrcci_content');
	var queryGrid = mainPane.getGrid();
	/**
	 * 导出
	 */
	if (queryGrid.store.data.length == 0) {
		Ext.Msg.alert('温馨提示', '表格没有数据，不能进行导出操作！');
		return false;
	}

	// 提示是否导出
	Ext.Msg.confirm('温馨提示', '确定要导出报表?', function(btn, text) {
		if ('yes' == btn) {
			var params = {
				'vo.mvrOrcciQueryDto.period' : closing.mvrOrcci.period,
				'vo.mvrOrcciQueryDto.deptCode' : closing.mvrOrcci.deptCode,
				'vo.mvrOrcciQueryDto.orgType' : closing.mvrOrcci.orgType
			};

			// 创建一个form
			if (!Ext.fly('exportMvrRfiForm')) {
				var frm = document.createElement('form');
				frm.id = 'exportMvrRfiForm';
				frm.style.display = 'none';
				document.body.appendChild(frm);
			}

			// 导出Ajax请求
			Ext.Ajax.request({
				url : closing.realPath('mvrorcciExport.action'),
				form : Ext.fly('exportMvrRfiForm'),
				params : params,
				method : 'post',
				isUpload : true,
				success : function(response) {
					var result = Ext.decode(response.responseText);
					// 如果异常信息有值，则弹出提示
					if (!Ext.isEmpty(result.errorMessage)) {
						Ext.Msg.alert('温馨提示', result.errorMessage);
						return false;
					}
					Ext.ux.Toast.msg('温馨提示', '导出成功！', 'success', 1000);
				},
				failure : function(response) {
					Ext.ux.Toast.msg('温馨提示', '导出失败！', 'error', 5000);
				}
			});

		}
	});
}

/*
 * 声明报表模型
 */
Ext.define('Foss.mvrOrcci.Model', {
	extend : 'Ext.data.Model',
	fields : [ {
		name : 'id',
		type : 'String'
	}, {
		name : 'period',
		type : 'String'
	}, {
		name : 'productCode',
		type : 'String'
	}, {
		name : 'customerCode',
		type : 'String'
	}, {
		name : 'customerName',
		type : 'String'
	}, {
		name : 'orgCode',
		type : 'String'
	}, {
		name : 'orgName',
		type : 'String'
	}, {
		name : 'orgType',
		type : 'String'
	}, {
		name : 'orgUnifiedCode',
		type : 'String'
	}, {
		name : 'voucherBeginTime',
		type : 'Date',
		convert : function(value) {
			if (value != null) {
				var date = new Date(value);
				return date;
			} else {
				return null;
			}
		}
	}, {
		name : 'voucherEndTime',
		type : 'Date',
		convert : function(value) {
			if (value != null) {
				var date = new Date(value);
				return date;
			} else {
				return null;
			}
		}
	},
	/** 退运费冲到达应收未签收* */
	{
		name : 'destPayTail',
		type : 'decimal'
	} ]
});

/**
 * 声明报表store
 */
Ext.define('Foss.mvrOrcci.Store',
		{
			extend : 'Ext.data.Store',
			model : 'Foss.mvrOrcci.Model',
			pageSize : 100,
			proxy : {
				type : 'ajax',
				url : closing.realPath('mvrNRorcciQuery.action'),
				actionMethods : 'post',
				timeout : 10 * 60 * 1000,
				reader : {
					type : 'json',
					root : 'vo.orcciEntities',
					totalProperty : 'totalCount'
				}
			},
			listeners : {
				'beforeLoad' : function(store, operation, eOpts) {
					var form = Ext.getCmp('T_closing-mvrOrcci_content')
							.getForm().getForm();
					// 如果用户直接点击分页查询，即没点击查询按钮，则在此处获取查询条件
					if (Ext.isEmpty(closing.mvrOrcci.period)) {
						closing.mvrOrcci.period = form.findField('period')
								.getValue();
						closing.mvrOrcci.deptCode = form.findField('deptCode')
								.getValue();
						closing.mvrOrcci.orgType = form.findField('orgType')
								.getValue();
					}
					var searchParams = {
						'vo.mvrOrcciQueryDto.period' : closing.mvrOrcci.period,
						'vo.mvrOrcciQueryDto.deptCode' : closing.mvrOrcci.deptCode,
						'vo.mvrOrcciQueryDto.orgType' : closing.mvrOrcci.orgType
					};
					Ext.apply(operation, {
						params : searchParams
					});
				}
			}
		});

/**
 * 基本对账单信息
 */
Ext.define('Foss.mvrOrcci.QueryForm', {
	extend : 'Ext.form.Panel',
	frame : true,
	layout : 'column',
	title : '查询条件',
	bodyPadding : 5,
	defaults : {
		labelWidth : 65
		},
	items : [
			{
				xtype : 'combobox',
				name : 'period',
				fieldLabel : '查询期间',
				queryMode : 'remote',
				store : Ext.create('Foss.mvrOrcci.PeriodStore'),
				displayField : 'name',
				valueField : 'name',
				allowBlank : false,
				columnWidth : .3
			},
			{
				xtype : 'combobox',
				name : 'orgType',
				fieldLabel : '始发/到达',
				store : FossDataDictionary.getDataDictionaryStore(
						'VOUCHER__ORG_TYPE', null, {
							'valueCode' : '',
							'valueName' : '全部'
						}),
				editable : false,
				queryModel : 'local',
				displayField : 'valueName',
				valueField : 'valueCode',
				columnWidth : .3
			},{
				xtype : 'dynamicorgcombselector',
				name : 'deptCode',
				fieldLabel : '部门信息',
				columnWidth : .3
			}, {
				xtype:'container',
				boder:false,
				html:'&nbsp;',
				columnWidth:1,
				height:24
				
			},{
				border : 1,
				xtype : 'container',
				columnWidth : 1,
				defaultType : 'button',
				layout : 'column',
				items : [ {
					text : '重置',
					columnWidth : .08,
					handler : closing.mvrOrcci.reset
				}, {
					xtype : 'container',
					border : false,
					html : '&nbsp;',
					columnWidth : .64
				}, {
					text : '查询',
					cls : 'yellow_button',
					columnWidth : .08,
					handler : function() {
						var form = this.up('form').getForm();
						var me = this;
						closing.mvrOrcci.queryReport(form, me)
					}
				} ]
			} ]
});

/**
 * 表格
 */
Ext
		.define(
				'Foss.mvrOrcci.Grid',
				{
					extend : 'Ext.grid.Panel',
					title : '报表明细',
					frame : true,
					bodyCls : 'autoHeight',
					cls : 'autoHeight',
					height : 600,
					store : Ext.create('Foss.mvrOrcci.Store'),
					defaults : {
						align : 'center',
						margin : '5 0 5 0'
					},
					viewConfig : {
						enableTextSelection : true,
						stripeRows : false,// 显示重复样式，不用隔行显示
						getRowClass : function(record, rowIndex, p, store) {
							var count = store.data.length;
							if (count > 0 && rowIndex == store.data.length - 1) {
								return 'closing-totalBgColor';
							}
						}
					},
					columns : [
							{
								text : '数据统计维度',
								sortable : false,
								defaults : {
									align : 'center'
								},
								columns : [
										{
											xtype : 'rownumberer',
											width : 40
										},
										{
											/* 期间 */
											dataIndex : 'period',
											header : '期间 '
										},
										{
											dataIndex : 'productCode',
											header : '产品类型',
											sortable : false,
											renderer : function(value) {
												return Foss.pkp.ProductData
														.rendererSubmitToDisplay(value);
											}
										},
										{
											/* 客户名称 */
											dataIndex : 'customerName',
											header : '客户名称'
										},
										{
											/* 客户编码 */
											dataIndex : 'customerCode',
											header : '客户编码'
										},
										{
											/* 部门名称 */
											dataIndex : 'orgName',
											header : '部门名称',
											width : 150
										},
										{
											/* 部门编码 */
											dataIndex : 'orgCode',
											header : '部门编码 '
										},
										{
											/* 部门编码 */
											dataIndex : 'orgType',
											header : '部门类型 ',
											renderer : function(value) {
												var displayField = FossDataDictionary
														.rendererSubmitToDisplay(
																value,
																'VOUCHER__ORG_TYPE');
												return displayField;
											}
										} ]
							}, {
								text : '外请车成本',
								sortable : false,
								defaults : {
									align : 'center',
								    width : 250
								},
								columns : [ {
									dataIndex : 'destPayTail',
									header : '付款申请（到达付尾款）（预付>0）'
								} ]
							} ],
					getExportButton : function() {
						var me = this;
						if (Ext.isEmpty(me.exportButton)) {
							me.exportButton = Ext
									.create(
											'Ext.Button',
											{
												text : '导出',
												height : 20,
												handler : closing.mvrOrcci.exportRfi,
												disabled : !closing.mvrOrcci
														.isPermission('/stl-web/closing/mvrNRorcciExport.action'),
												hidden : !closing.mvrOrcci
														.isPermission('/stl-web/closing/mvrNRorcciExport.action')
											});
						}
						return me.exportButton;
					},
					initComponent : function() {
						var me = this;
						me.dockedItems = [ {
							xtype : 'toolbar',
							dock : 'bottom',
							layout : 'column',
							items : [ {
								xtype : 'standardpaging',
								store : me.store,
								pageSize : 100,
								columnWidth : 1,
								items : [ me.getExportButton() ],
								plugins : Ext.create(
										'Deppon.ux.PageSizePlugin', {
											// 设置分页记录最大值，防止输入过大的数值
											maximumSize : 100,
											sizeList : [ [ '10', 10 ],
													[ '30', 30 ], [ '50', 50 ],
													[ '100', 100 ] ]
										})
							} ]
						} ];
						me.callParent();
					}
				});
Ext.onReady(function() {
	var form = Ext.create('Foss.mvrOrcci.QueryForm');
	var grid = Ext.create('Foss.mvrOrcci.Grid');
	// 显示到JSP页面
	Ext.create('Ext.panel.Panel', {
		id : 'T_closing-mvrOrcci_content',
		cls : "panelContentNToolbar",
		bodyCls : 'panelContentNToolbar-body',
		getGrid : function() {
			return grid;
		},
		getForm : function() {
			return form;
		},
		layout : 'auto',
		items : [ form, grid ],
		renderTo : 'T_closing-mvrOrcci-body'
	});
});