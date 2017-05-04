/**
 * 声明账期model
 */
Ext.define('Foss.mvrSummaryR.PeriodModel', {
	extend : 'Ext.data.Model',
	fields : [ 'name', 'code' ]
});

/**
 * 声明账期store
 */
Ext.define('Foss.mvrSummaryR.PeriodStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.mvrSummaryR.PeriodModel',
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
Ext.define('Foss.mvrSummaryR.ProductTypeModel', {
	extend : 'Ext.data.Model',
	fields : [ 'name', 'code' ]
});
/**
 * 声明产品类型store
 */
Ext.define('Foss.mvrSummaryR.ProductTypeStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.mvrSummaryR.ProductTypeModel',
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

// 02业务重分类汇总报表model
Ext.define('Foss.closing.mvrSummaryRModel', {
	extend : 'Ext.data.Model',
	fields : [ {
		name : 'id',
		type : 'string'
	}, {
		name : 'period',
		type : 'string'
	}, {
		name : 'productCode',
		type : 'string'
	}, {
		name : 'subIncomeCpmpanyCode',
		type : 'string'
	}, {
		name : 'subIncomeCpmpanyName',
		type : 'string'
	}, {
		name : 'sourceData',
		type : 'string'
	}, {
		name : 'amountFre',
		type : 'decimal'
	}, {
		name : 'amountFreTax',
		type : 'decimal'
	}, {
		name : 'amountFreTotal',
		type : 'decimal'
	}, {
		name : 'amountTra',
		type : 'decimal'
	}, {
		name : 'amountTraTax',
		type : 'decimal'
	}, {
		name : 'amountTraTotal',
		type : 'decimal'
	} ]
});

// 02业务重分类汇总报表store
Ext.define('Foss.closing.mvrSummaryRStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.closing.mvrSummaryRModel',
	pageSize : 100,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url : closing.realPath('querySummaryRByConditions.action'),
		timeout : 10 * 60 * 1000,
		reader : {
			type : 'json',
			root : 'mvrSummaryRVo.mvrSummaryRList',
			totalProperty : 'mvrSummaryRVo.count'
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
				var queryForm = Ext.getCmp('T_closing-mvrSummaryR_content')
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
Ext.define('Foss.closing.mvrSummaryRQueryForm', {
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
				name : 'mvrSummaryRVo.period',
				fieldLabel : '期间',
				queryMode : 'remote',
				store : Ext.create('Foss.mvrSummaryR.PeriodStore'),
				displayField : 'name',
				valueField : 'name',
				allowBlank : false,
				columnWidth : .3
			},
			{ // 所属子公司
				xtype : 'commonsubsidiaryselector',
				name : 'mvrSummaryRVo.subIncomeCpmpanyCode',
				fieldLabel : '所属子公司',
				columnWidth : .3,
				listWidth : 260,// 设置下拉框宽度
				isPaging : true
			},
			{// 产品类型
				xtype : 'combo',
				name : 'mvrSummaryRVo.productCode',
				fieldLabel : '产品类型',
				queryMode : 'local',
				store : Ext.create('Foss.mvrSummaryR.ProductTypeStore'),
				displayField : 'name',
				valueField : 'code',
				value : Ext.create('Foss.mvrSummaryR.ProductTypeStore').first()
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
							closing.mvrSummaryR.queryMvrSummaryR(form, me);
						} else {
							Ext.Msg.alert("温馨提醒", "请检查输入条件是否合法");
							return false;
						}
					}
				} ]
			} ]
});

// 02业务重分类汇总报表查询显示Grid
Ext.define('Foss.closing.mvrSummaryRQueryGrid', {
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
	//selModel : Ext.create('Ext.selection.CheckboxModel'),
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
		text : '序号',
		xtype : 'rownumberer',
		width : 40
	}, {
		text : '期间',
		width : 95,
		dataIndex : 'period'
	}, {
		text : '产品类型',
		width : 180,
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
		text : '收入所属子公司',
		width : 180,
		dataIndex : 'subIncomeCpmpanyName'
	}, {
		text : '物流辅助业金额',
		width : 180,
		dataIndex : 'amountFre'
	}, {
		text : '物流辅助业税金',
		width : 180,
		dataIndex : 'amountFreTax'
	}, {
		text : '物流辅助业合计金额',
		width : 180,
		dataIndex : 'amountFreTotal'
	}, {
		text : '运输业金额',
		width : 180,
		dataIndex : 'amountTra'
	}, {
		text : '运输业税金',
		width : 180,
		dataIndex : 'amountTraTax'
	}, {
		text : '运输业合计金额',
		width : 180,
		dataIndex : 'amountTraTotal'
	} ],

	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);

		me.store = Ext.create('Foss.closing.mvrSummaryRStore');

		me.bbar = me.getPagingToolbar();
		me.getPagingToolbar().store = me.store;
		me.callParent([ cfg ]);
	}
});

/**
 * 查询方法
 */
closing.mvrSummaryR.queryMvrSummaryR = function(form, me) {
	var m = Ext.getCmp('T_closing-mvrSummaryR_content');
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

	if (Ext.getCmp('T_closing-mvrSummaryR_content')) {
		return;
	}

	// 查询FORM
	var queryForm = Ext.create('Foss.closing.mvrSummaryRQueryForm');

	// 显示grid
	var queryGrid = Ext.create('Foss.closing.mvrSummaryRQueryGrid');

	// 显示到JSP页面
	Ext.create('Ext.panel.Panel', {
		id : 'T_closing-mvrSummaryR_content',
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
		renderTo : 'T_closing-mvrSummaryR-body'
	});
});