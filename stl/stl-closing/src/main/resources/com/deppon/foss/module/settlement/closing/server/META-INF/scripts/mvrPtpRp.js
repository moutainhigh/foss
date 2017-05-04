/**
 * 声明账期model
 */
Ext.define('Foss.mvrPtpRp.PeriodModel', {
			extend : 'Ext.data.Model',
			fields : ['name', 'code']
		});

/**
 * 声明账期store
 */
Ext.define('Foss.mvrPtpRp.PeriodStore', {
			extend : 'Ext.data.Store',
			model : 'Foss.mvrPtpRp.PeriodModel',
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
				me.callParent([cfg]);
			}
		});

/**
 * 查询方法
 */
closing.mvrPtpRp.queryPtpRp= function(form, me) {
	var m = Ext.getCmp('T_closing-mvrPtpRp_content');
	if (m) {
		var grid = m.getQueryGrid();
		var store = grid.getStore();
		if (store) {
			if (grid.isHidden()) {
				grid.show();
			}
			// 设置该按钮灰掉
			me.disable(false);
			// 30秒后自动解除灰掉效果
			setTimeout(function() {
						me.enable(true);
					}, 30000);
			// 加载第一页数据
			store.loadPage(1, {
						callback : function(records, operation, success) {
							var result = Ext
									.decode(operation.response.responseText);
							if (!success && result.isException) {
								Ext.Msg.alert("提示", result.message);
								me.enable(true);
								return false;
							}

							me.enable(true);
						}
					});
		}
	}

}


/**
 * 获取期间控件下拉框Store
 */
closing.mvrPtpRp.getComboPeriodStore = function() {
	return Ext.create('Foss.common.PeriodStore');
}

/**
 * 获取业务类型下拉框Store
 */
closing.mvrPtpRp.getComboProductTypeStore = function() {
	var productStore = Ext.create('Foss.pkp.ProductStore');
	return productStore;
}

/** 数据模型 */

// 下拉框(combo)数据模型
Ext.define('Foss.closing.mvrPtpRpComboModel', {
			extend : 'Ext.data.Model',
			fields : [{
						/* 显示名 */
						name : 'name',
						type : 'string'
					}, {
						/* 实际值 */
						name : 'value',
						type : 'string'
					}]
		})

//合伙人奖罚月报表model
Ext.define('Foss.closing.MvrPtpRpModel', {
			extend : 'Ext.data.Model',
			fields : [{ //期间
			name : 'period',
			type : 'string'
		},{ //运输性质
			name : 'productCode',
			type : 'string'
		},{ //客户名称
			name : 'customerName',
			type : 'string'
		},{ //客户编码
			name : 'customerCode',
			type : 'string'
		},{ //收入部门名称
			name : 'generatingOrgName',
			type : 'string'
		},{ //收入部门编码
			name : 'generatingOrgCode',
			type : 'string'
		},{ //应收部门名称
			name : 'recOrgName',
			type : 'string'
		},{ //应收部门编码
			name : 'recOrgCode',
			type : 'string'
		},{ //应付部门名称
			name : 'payOrgName',
			type : 'string'
		},{ //应付部门编码
			name : 'payOrgCode',
			type : 'string'
		},{ //费用承担部门名称
			name : 'expenseBearName',
			type : 'string'
		},{ //费用承担部门编码
			name : 'expenseBearCode',
			type : 'string'
		},{ //合伙人罚款应收
			name : 'ptpPen',
			type : 'decimal'
		},{ //快递差错应收
			name : 'ptpErrEer',
			type : 'decimal'
		},{ //抢货应收
			name : 'ptpErrBgr',
			type : 'decimal'
		},{ //网上支付开单金额应收
			name : 'ptpErrOpbar',
			type : 'decimal'
		},{ //异常签收应收
			name : 'ptpErrEsr',
			type : 'decimal'
		},{ //派送时效应收
			name : 'ptpErrSlr',
			type : 'decimal'
		},{ //超重超方成本提取应收
			name : 'ptpErrOwoscer',
			type : 'decimal'
		},{ //投诉应收
			name : 'ptpErrRcr',
			type : 'decimal'
		},{ //网上支付差错应收
			name : 'ptpErrOper',
			type : 'decimal'
		},{ //抢货差错应收
			name : 'ptpErrBer',
			type : 'decimal'
		},{ //理赔冲成本
			name : 'ptpErrSpc',
			type : 'decimal'
		},{ //理赔入收入
			name : 'ptpErrSei',
			type : 'decimal'
		},{ //代打木架应收
			name : 'ptpErrHwfr',
			type : 'decimal'
		},{ //培训费收款
			name : 'ptpTrTcc',
			type : 'decimal'
		},{ //会务费收款
			name : 'ptpTrRfc',
			type : 'decimal'
		},{ //上报快递差错应付
			name : 'ptpLtlrUltlp',
			type : 'decimal'
		},{ //抢货应付
			name : 'ptpLtlrBgp',
			type : 'decimal'
		},{ //合伙人奖励应付
			name : 'ptpLtlrPtprp',
			type : 'decimal'
		},{ //其他应收冲其他应付
			name : 'ptpOtherRtp',
			type : 'decimal'
		},{ //奖励付款申请
			name : 'ptpLpaRpa',
			type : 'decimal'
		},{ //奖励应付付款申请
			name : 'ptpLpaRppa',
			type : 'decimal'
		},{ //快递差错应付付款申请
			name : 'ptpLpaEeppa',
			type : 'decimal'
		}]
	});

//合伙人奖罚月报表store
Ext.define('Foss.closing.MvrPtpRpStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.closing.MvrPtpRpModel',
	pageSize : 100,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url : closing.realPath('queryPtpRpByConditions.action'),
		timeout : 10 * 60 * 1000,
		reader : {
			type : 'json',
			root : 'mvrPtpRpVo.mvrPtpRpList',
			totalProperty : 'mvrPtpRpVo.mvrPtpRpDto.count'
		}
	},
	submitParams : {},
	setSubmitParams : function(submitParams) {
		this.submitParams = submitParams;
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.listeners = {
			'beforeload' : function(store, operation, eOpts) {
				var queryForm = Ext.getCmp('T_closing-mvrPtpRp_content').getQueryForm();
				if (queryForm) {
					var form = queryForm.getForm();
					var params = form.getValues();
					Ext.apply(params, {
						'mvrPtpRpVo.productCodeList' : stl.convertProductCode(form.findField('mvrPtpRpVo.productCodeList').getValue())
					});

					Ext.apply(operation, {
								params : params
							});
				}

			}
		};
		me.callParent([cfg]);
	}
});

// 定义查询Form
Ext.define('Foss.closing.MvrPtpRpQueryForm', {
			extend : 'Ext.form.Panel',
			frame : true,
			title : '查询条件',
			bodyCls : 'autoHeight',
			defaults : {
				margin : '10 5 10 5',
				labelWidth : 85,
				colspan : 1
			},
			defaultType : 'textfield',
			layout : {
				type : 'column',
				columns : 3
			},
			items : [{
		xtype : 'combo',
		name : 'mvrPtpRpVo.period', 
		fieldLabel : '期间',
		queryMode : 'remote', 
		store : Ext.create('Foss.mvrPtpRp.PeriodStore'),
		displayField : 'name',
		valueField : 'name',
		allowBlank : false,
		columnWidth : .3
	},{ //运输性质
		xtype : 'combo',
		name : 'mvrPtpRpVo.productCodeList',
		fieldLabel : '运输性质',
		forceSelection : true,
		multiSelect : true,
		displayField : 'name',
		valueField : 'code',
		queryMode : 'local',
		triggerAction : 'all',
		editable : false,
		value : closing.mvrPtpRp.getComboProductTypeStore().first().get('code'),
		store : closing.mvrPtpRp.getComboProductTypeStore(),
		columnWidth : .3
	},{
		xtype : 'commonsaledepartmentselector',
		fieldLabel : '客户信息',
		name : 'mvrPtpRpVo.customerCode',
		columnWidth : .3,
		listWidth : 260,//设置下拉框宽度
		isPaging : true
	},{
		xtype : 'dynamicorgcombselector',
		fieldLabel : '应收部门',
		name : 'mvrPtpRpVo.recOrgCode',
		columnWidth : .3,
		listWidth : 260,//设置下拉框宽度
		isPaging : true
	},{
		xtype : 'dynamicorgcombselector',
		fieldLabel : '应付部门',
		name : 'mvrPtpRpVo.payOrgCode',
		columnWidth : .3,
		listWidth : 260,//设置下拉框宽度
		isPaging : true
	},{
		xtype : 'container',
		border : 1,
		columnWidth : 1,
		colspan : 3,
		defaultType : 'button',
		layout : 'column',
		items : [{//重置按钮
			text : '重置',
			 columnWidth:.1,
		     handler:closing.mvrPtpRp.queryPtpRpReset
		},{
			xtype : 'container',
			border : false,
			html:'&nbsp;',
			columnWidth:.6
		}, {
			text : '查询',
			columnWidth : .1,
			cls : 'yellow_button',
			handler : function() {
				var form = this.up('form').getForm();
				var me = this;
				if (form.isValid()) {
					closing.mvrPtpRp.queryPtpRp(form, me);
				} else {
					Ext.Msg.alert("温馨提醒","请检查输入条件是否合法");
					return false;
				}
			}
		}]
	}]
});

// 合伙人奖罚月报表查询显示Grid
Ext.define('Foss.closing.MvrPtpRpQueryGrid', {
	extend : 'Ext.grid.Panel',
	title : '报表明细',
	columnWidth : 1,
	stripeRows : true,
	columnLines : true,
	collapsible : false,
	bodyCls : 'autoHeight',
	frame : true,
	cls : 'autoHeight',
	store : null,
	autoScroll : true,
	height : 650,
	emptyText : '查询结果为空',
	viewConfig : {
		enableTextSelection : true, // 设置行可以选择，进而可以复制
		getRowClass : function(record, rowIndex, rowParams, store) {
			if (record.data.period == '汇总') { // 汇总的样式
				return 'closing-totalBgColor';
			} else {
				return '';
			}
		}
	},
	pagingToolbar : null,
	getPagingToolbar : function() {
		var me = this;
		if (Ext.isEmpty(me.pagingToolbar)) {
			me.pagingToolbar = Ext.create('Deppon.StandardPaging', {
						store : me.store,
						pageSize : 100,
						maximumSize : 500,
						plugins : 'pagesizeplugin'
					});
		}
		return me.pagingToolbar;
	},
	columns : [{
		text : '数据统计维度',
		defaults : {
			style : "text-align:center"
		},
		columns : [{
			text : '序号',
			xtype : 'rownumberer',
			width : 40
		},{
			text : '期间',
			width : 95,
			dataIndex : 'period'
		},{
			text : '运输性质',
			width : 95,
			dataIndex : 'productCode',
			renderer : function(value) {
				return Foss.pkp.ProductData.rendererSubmitToDisplay(value);
			}
		},{
			text : '客户名称',
			width : 95,
			dataIndex : 'customerName'
		},{
			text : '客户编码',
			width : 95,
			dataIndex : 'customerCode'
		},{
			text : '收入部门',
			width : 95,
			dataIndex : 'generatingOrgName'
		},{
			text : '收入部门编码',
			width : 95,
			dataIndex : 'generatingOrgCode'
		},{
			text : '应收部门',
			width : 95,
			dataIndex : 'recOrgName'
		},{
			text : '应收部门编码',
			width : 95,
			dataIndex : 'recOrgCode'
		},{
			text : '应付部门',
			width : 95,
			dataIndex : 'payOrgName'
		},{
			text : '应付部门编码',
			width : 95,
			dataIndex : 'payOrgCode'
		},{
			text : '费用承担部门',
			width : 95,
			dataIndex : 'expenseBearName'
		},{
			text : '费用承担部门编码',
			width : 95,
			dataIndex : 'expenseBearCode'
		}]
	},{
		text : '收取合伙人款项',
		defaults : {
			style : 'text-align:center'
		},
		columns : [{
			text : '合伙人罚款应收',
			width : 150,
			dataIndex : 'ptpPen',
			defaults : {
				style : 'text-align:center'
			}
		},{
			text : '合伙人差错',
			defaults : {
				style : 'text-align:center'
			},
			columns : [{
				text : '快递差错应收',
				width:95,
				dataIndex : 'ptpErrEer'
			},{
				text : '抢货应收',
				width:120,
				dataIndex : 'ptpErrBgr'
			},{
				text : '网上支付开单金额应收',
				width:120,
				dataIndex : 'ptpErrOpbar'
			},{
				text : '异常签收应收',
				width:95,
				dataIndex : 'ptpErrEsr'
			},{
				text : '派送时效应收',
				width:95,
				dataIndex : 'ptpErrSlr'
			},{
				text : '超重超方成本提取应收',
				width:120,
				dataIndex : 'ptpErrOwoscer'
			},{
				text : '投诉应收',
				width:95,
				dataIndex : 'ptpErrRcr'
			},{
				text : '网上支付差错应收',
				width:105,
				dataIndex : 'ptpErrOper'
			},{
				text : '抢货差错应收',
				width:95,
				dataIndex : 'ptpErrBer'
			},{
				text : '理赔冲成本',
				width:95,
				dataIndex : 'ptpErrSpc'
			},{
				text : '理赔入收入',
				width:95,
				dataIndex : 'ptpErrSei'
			},{
				text : '代打木架应收',
				width:95,
				dataIndex : 'ptpErrHwfr'
			}]
		},{
			text :'合伙人培训会务',
			defaults : {
				style : 'text-align:center'
			},
			columns : [{
				text : '培训费收款',
				width:95,
				dataIndex : 'ptpTrTcc'	
			},{
				text : '会务费收款',
				width:95,
				dataIndex : 'ptpTrRfc'	
				}]
		}]
	},{
		text : '支付合伙人款项',
		defaults : {
			style : 'text-align:center'
		},
		columns : [{
			text : '合伙人上报直营快递差错',
			defaults : {
				style : 'text-align:center'
			},
			columns : [{
				text : '上报快递差错应付',
				width : 105,
				dataIndex : 'ptpLtlrUltlp'
			},{
				text : '抢货应付',
				width : 95,
				dataIndex : 'ptpLtlrBgp'
			}]
		},{
			text :'合伙人奖励应付',
			width:105,
			dataIndex : 'ptpLtlrPtprp'
		}]
	},{
		text : '其他应收冲其他应付',
		width:125,
		dataIndex : 'ptpOtherRtp'
	},{
		text : '发起付款申请',
		defaults : {
			style : 'text-align:center'
		},
		columns : [{
			text : '奖励付款申请',
			width:95,
			dataIndex:'ptpLpaRpa'
		},{
			text : '奖励应付付款申请',
			width:95,
			dataIndex:'ptpLpaRppa'
		},{
			text : '快递差错应付付款申请',
			width:95,
			dataIndex:'ptpLpaEeppa'
		}]
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);

		me.store = Ext.create('Foss.closing.MvrPtpRpStore');

		me.bbar = me.getPagingToolbar();
		me.getPagingToolbar().store = me.store;
		me.callParent([cfg]);
	}
});

// 显示
Ext.onReady(function() {
			Ext.QuickTips.init();

			if (Ext.getCmp('T_closing-mvrPtpRp_content')) {
				return;
			}

			// 查询FORM
			var queryForm = Ext.create('Foss.closing.MvrPtpRpQueryForm');

			// 显示grid
			var queryGrid = Ext.create('Foss.closing.MvrPtpRpQueryGrid');

			// 显示到JSP页面
			Ext.create('Ext.panel.Panel', {
						id : 'T_closing-mvrPtpRp_content',
						cls : "panelContentNToolbar",
						bodyCls : 'panelContentNToolbar-body',
						layout : 'auto',
						items : [queryForm, queryGrid],
						// 获得查询FORM
						getQueryForm : function() {
							return queryForm;
						},
						// 获得查询结果GRID
						getQueryGrid : function() {
							return queryGrid;
						},
						renderTo : 'T_closing-mvrPtpRp-body'
					});
		});