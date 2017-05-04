/**
 * 声明账期model
 */
Ext.define('Foss.mvrNrfontR.PeriodModel', {
	extend : 'Ext.data.Model',
	fields : [ 'name', 'code' ]
});

/**
 * 声明账期store
 */
Ext.define('Foss.mvrNrfontR.PeriodStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.mvrNrfontR.PeriodModel',
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
 * 声明产品类型model
 */
Ext.define('Foss.mvrNrfontR.ProductTypeModel', {
	extend : 'Ext.data.Model',
	fields : [ 'name', 'code' ]
});
/**
 * 声明产品类型store
 */
Ext.define('Foss.mvrNrfontR.ProductTypeStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.mvrNrfontR.ProductTypeModel',
	data : [ {
		name : '全部',
		code : ''
	}, {
		name : '零担',
		code : 'LTL'
	}, {
		name : '快递',
		code : 'EXPRESS'
	}, {
		name : '整车',
		code : 'VEHICLE'
	} ]
});

// 02普通业务重分类报表model
Ext.define('Foss.closing.mvrNrfontRModel', {
	extend : 'Ext.data.Model',
	fields : [ { // 期间
		name : 'period',
		type : 'string'
	}, { // 产品类型编码
		name : 'productCode',
		type : 'string'
	}, { // 始发部门编码
		name : 'depCode',
		type : 'string'
	}, { // 始发部门名称
		name : 'depName',
		type : 'string'
	}, { // 是否统一结算
		name : 'unifiedSettlementType',
		type : 'string'
	}, { // 合同部门编码
		name : 'contractOrgCode',
		type : 'string'
	}, { // 合同部门名称
		name : 'contractOrgName',
		type : 'string'
	}, { // 所属子公司编码
		name : 'subIncomeCpmpanyCode',
		type : 'string'
	}, { // 所属子公司名称
		name : 'subIncomeCpmpanyName',
		type : 'string'
	}, { // 签收运单【02】-含税收入冲销
		name : 'amountStbtwoTro',
		type : 'decimal'
	}, { // 签收运单【02】-物流辅助业金额
		name : 'amountStbtwoAola',
		type : 'decimal'
	}, { // 签收运单【02】-物流运输业金额
		name : 'amountStbtwoLtra',
		type : 'decimal'
	}, { // 签收运单【02】-物流辅助业税金
		name : 'amountStbtwoLsit',
		type : 'decimal'
	}, { // 签收运单【02】-物流运输业税金
		name : 'amountStbtwoTrat',
		type : 'decimal'
	}, {
		name : 'amountRstbtwoTro',
		type : 'decimal'
	}, {
		name : 'amountRstbtwoAola',
		type : 'decimal'
	}, {
		name : 'amountRstbtwoLtra',
		type : 'decimal'
	}, {
		name : 'amountRstbtwoLsit',
		type : 'decimal'
	}, {
		name : 'amountRstbtwoTrat',
		type : 'decimal'
	}, {
		name : 'amountStbhTro',
		type : 'decimal'
	}, {
		name : 'amountStbhAola',
		type : 'decimal'
	}, {
		name : 'amountStbhLtra',
		type : 'decimal'
	}, {
		name : 'amountStbhLsit',
		type : 'decimal'
	}, {
		name : 'amountStbhTrat',
		type : 'decimal'
	}, {
		name : 'amountRstbhTro',
		type : 'decimal'
	}, {
		name : 'amountRstbhAola',
		type : 'decimal'
	}, {
		name : 'amountRstbhLtra',
		type : 'decimal'
	}, {
		name : 'amountRstbhLsit',
		type : 'decimal'
	}, {
		name : 'amountRstbhTrat',
		type : 'decimal'
	}, {
		name : 'amountAirotrTro',
		type : 'decimal'
	}, {
		name : 'amountAirotrAola',
		type : 'decimal'
	}, {
		name : 'amountAirotrLtra',
		type : 'decimal'
	}, {
		name : 'amountAirotrLsit',
		type : 'decimal'
	}, {
		name : 'amountAirotrTrat',
		type : 'decimal'
	} ]
});

// 02普通业务重分类报表store
Ext.define('Foss.closing.mvrNrfontRStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.closing.mvrNrfontRModel',
	pageSize : 100,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url : closing.realPath('queryNrfontRByConditions.action'),
		timeout : 10 * 60 * 1000,
		reader : {
			type : 'json',
			root : 'mvrNrfontRVo.mvrNrfontRList',
			totalProperty : 'mvrNrfontRVo.count'
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
				var queryForm = Ext.getCmp('T_closing-mvrNrfontR_content')
						.getQueryForm();
				if (queryForm) {
					var form = queryForm.getForm();
					var params = form.getValues();
					Ext.apply(operation, {
						params : params
					});
				}

			}
		};
		me.callParent([ cfg ]);
	}
});

// 定义查询Form
Ext.define('Foss.closing.mvrNrfontRQueryForm', {
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
	items : [
			{
				xtype : 'combo',
				name : 'mvrNrfontRVo.period',
				fieldLabel : '期间',
				queryMode : 'remote',
				store : Ext.create('Foss.mvrNrfontR.PeriodStore'),
				displayField : 'name',
				valueField : 'name',
				allowBlank : false,
				columnWidth : .3
			},
			{ // 所属子公司
				xtype : 'commonsubsidiaryselector',
				name : 'mvrNrfontRVo.subIncomeCpmpanyCode',
				fieldLabel : '所属子公司',
				columnWidth : .3,
				listWidth : 260,// 设置下拉框宽度
				isPaging : true
			},
			{// 产品类型
				xtype : 'combo',
				name : 'mvrNrfontRVo.productCode',
				fieldLabel : '产品类型',
				queryMode : 'local',
				store : Ext.create('Foss.mvrNrfontR.ProductTypeStore'),
				displayField : 'name',
				valueField : 'code',
				value : Ext.create('Foss.mvrNrfontR.ProductTypeStore').first()
						.get('code'),
				allowBlank : true,
				columnWidth : .3,
				listWidth : 260,// 设置下拉框宽度
				isPaging : true
			}, {
				xtype : 'container',
				border : 1,
				columnWidth : 1,
				colspan : 3,
				defaultType : 'button',
				layout : 'column',
				items : [ {// 重置按钮
					text : '重置',
					columnWidth : .1,
					handler : function() {
						this.up('form').getForm().reset();
					}
				}, {
					xtype : 'container',
					border : false,
					html : '&nbsp;',
					columnWidth : .6
				}, {
					text : '查询',
					columnWidth : .1,
					cls : 'yellow_button',
					handler : function() {
						var form = this.up('form').getForm();
						var me = this;
						if (form.isValid()) {
							closing.mvrNrfontR.queryMvrNrfontR(form, me);
						} else {
							Ext.Msg.alert("温馨提醒", "请检查输入条件是否合法");
							return false;
						}
					}
				} ]
			} ]
});

// 02普通业务重分类报表查询显示Grid
Ext.define('Foss.closing.mvrNrfontRQueryGrid', {
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
	columns : [ {
		text : '数据统计维度',
		defaults : {
			style : "text-align:center"
		},
		columns : [ {
			text : '序号',
			xtype : 'rownumberer',
			width : 40
		}, {
			text : '期间',
			width : 95,
			dataIndex : 'period'
		}, {
			text : '产品类型',
			width : 95,
			dataIndex : 'productCode',
			renderer : function(value){
				var data=[{name:'全部',code:''},{name:'零担',code:'LTL'},{name:'快递',code:'EXPRESS'},{name:'整车',code:'VEHICLE'}];
				if(!Ext.isEmpty(value)){
					for(var i=0;i<data.length;i++){
						if(data[i].code==value){
							return data[i].name;
						}
					}
				}
				return value;
			}
		}, {
			text : '部门名称',
			width : 95,
			dataIndex : 'depName'
		}, {
			text : '部门编码',
			width : 95,
			dataIndex : 'depCode'
		}, {
			text : '是否统一结算',
			width : 95,
			dataIndex : 'unifiedSettlementType',
			renderer : function(value) {
				if (!Ext.isEmpty(value) && value == 'Y') {
					return '是';
				} else {
					return '否';
				}
			}
		}, {
			text : '合同部门名称',
			width : 95,
			dataIndex : 'contractOrgName'
		}, {
			text : '合同部门编码',
			width : 95,
			dataIndex : 'contractOrgCode'
		}, {
			text : '收入所属子公司',
			width : 95,
			dataIndex : 'subIncomeCpmpanyName'
		} ]
	}, {
		text : '02普通始发月报表重分类',
		defaults : {
			style : 'text-align:center'
		},
		columns : [ {
			text : '签收运单【02】',
			defaults : {
				style : 'text-align:center'
			},
			columns : [ {
				text : '含税收入冲销',
				width : 95,
				dataIndex : 'amountStbtwoTro'
			}, {
				text : '物流辅助业金额',
				width : 95,
				dataIndex : 'amountStbtwoAola'
			}, {
				text : '物流运输业金额',
				width : 95,
				dataIndex : 'amountStbtwoLtra'
			}, {
				text : '物流辅助业税金',
				width : 95,
				dataIndex : 'amountStbtwoLsit'
			}, {
				text : '运输业税金',
				width : 95,
				dataIndex : 'amountStbtwoTrat'
			} ]
		}, {
			text : '反签收运单【02】',
			defaults : {
				style : 'text-align:center'
			},
			columns : [ {
				text : '含税收入冲销',
				width : 95,
				dataIndex : 'amountRstbtwoTro'
			}, {
				text : '物流辅助业金额',
				width : 95,
				dataIndex : 'amountRstbtwoAola'
			}, {
				text : '物流运输业金额',
				width : 95,
				dataIndex : 'amountRstbtwoLtra'
			}, {
				text : '物流辅助业税金',
				width : 95,
				dataIndex : 'amountRstbtwoLsit'
			}, {
				text : '运输业税金',
				width : 95,
				dataIndex : 'amountRstbtwoTrat'
			} ]
		} ]
	}, {
		text : '合伙人月报表重分类',
		defaults : {
			style : 'text-align:center'
		},
		columns : [ {
			text : '签收运单【02】',
			defaults : {
				style : 'text-align:center'
			},
			columns : [ {
				text : '含税收入冲销',
				width : 95,
				dataIndex : 'amountStbhTro'
			}, {
				text : '物流辅助业金额',
				width : 95,
				dataIndex : 'amountStbhAola'
			}, {
				text : '物流运输业金额',
				width : 95,
				dataIndex : 'amountStbhLtra'
			}, {
				text : '物流辅助业税金',
				width : 95,
				dataIndex : 'amountStbhLsit'
			}, {
				text : '运输业税金',
				width : 95,
				dataIndex : 'amountStbhTrat'
			} ]
		}, {
			text : '反签收运单【02】',
			defaults : {
				style : 'text-align:center'
			},
			columns : [ {
				text : '含税收入冲销',
				width : 95,
				dataIndex : 'amountRstbhTro'
			}, {
				text : '物流辅助业金额',
				width : 95,
				dataIndex : 'amountRstbhAola'
			}, {
				text : '物流运输业金额',
				width : 95,
				dataIndex : 'amountRstbhLtra'
			}, {
				text : '物流辅助业税金',
				width : 95,
				dataIndex : 'amountRstbhLsit'
			}, {
				text : '运输业税金',
				width : 95,
				dataIndex : 'amountRstbhTrat'
			} ]
		} ]
	}, {
		text : '空运其他应收重分类',
		defaults : {
			style : 'text-align:center'
		},
		columns : [ {
			text : '空运其他应收录入重分类',
			defaults : {
				style : 'text-align:center'
			},
			columns : [ {
				text : '含税收入冲销',
				width : 95,
				dataIndex : 'amountAirotrTro'
			}, {
				text : '物流辅助业金额',
				width : 95,
				dataIndex : 'amountAirotrAola'
			}, {
				text : '物流运输业金额',
				width : 95,
				dataIndex : 'amountAirotrLtra'
			}, {
				text : '物流辅助业税金',
				width : 95,
				dataIndex : 'amountAirotrLsit'
			}, {
				text : '运输业税金',
				width : 95,
				dataIndex : 'amountAirotrTrat'
			} ]
		} ]
	} ],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);

		me.store = Ext.create('Foss.closing.mvrNrfontRStore');

		me.bbar = me.getPagingToolbar();
		me.getPagingToolbar().store = me.store;
		me.callParent([ cfg ]);
	}
});

/**
 * 查询方法
 */
closing.mvrNrfontR.queryMvrNrfontR = function(form, me) {
	var m = Ext.getCmp('T_closing-mvrNrfontR_content');
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
					var result = Ext.decode(operation.response.responseText);
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

// 显示
Ext.onReady(function() {
	Ext.QuickTips.init();

	if (Ext.getCmp('T_closing-mvrNrfontR_content')) {
		return;
	}

	// 查询FORM
	var queryForm = Ext.create('Foss.closing.mvrNrfontRQueryForm');

	// 显示grid
	var queryGrid = Ext.create('Foss.closing.mvrNrfontRQueryGrid');

	// 显示到JSP页面
	Ext.create('Ext.panel.Panel', {
		id : 'T_closing-mvrNrfontR_content',
		cls : "panelContentNToolbar",
		bodyCls : 'panelContentNToolbar-body',
		layout : 'auto',
		items : [ queryForm, queryGrid ],
		// 获得查询FORM
		getQueryForm : function() {
			return queryForm;
		},
		// 获得查询结果GRID
		getQueryGrid : function() {
			return queryGrid;
		},
		renderTo : 'T_closing-mvrNrfontR-body'
	});
});