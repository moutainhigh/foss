/**
 * 声明账期model
 */
Ext.define('Foss.mvrNrfostR.PeriodModel', {
	extend : 'Ext.data.Model',
	fields : [ 'name', 'code' ]
});

/**
 * 声明账期store
 */
Ext.define('Foss.mvrNrfostR.PeriodStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.mvrNrfostR.PeriodModel',
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
Ext.define('Foss.mvrNrfostR.ProductTypeModel', {
	extend : 'Ext.data.Model',
	fields : [ 'name', 'code' ]
});
/**
 * 声明产品类型store
 */
Ext.define('Foss.mvrNrfostR.ProductTypeStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.mvrNrfostR.ProductTypeModel',
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

// 02特殊业务重分类报表model
Ext.define('Foss.closing.mvrNrfostRModel', {
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
		name : 'depCode',
		type : 'string'
	}, {
		name : 'depName',
		type : 'string'
	}, {
		name : 'unifiedSettlementType',
		type : 'string'
	}, {
		name : 'contractOrgCode',
		type : 'string'
	}, {
		name : 'contractOrgName',
		type : 'string'
	}, {
		name : 'subIncomeCpmpanyCode',
		type : 'string'
	}, {
		name : 'subIncomeCpmpanyName',
		type : 'string'
	}, {
		name : 'amountOrgCtoinTwoTro',
		type : 'decimal'
	}, {
		name : 'amountOrgCtoinTwoAola',
		type : 'decimal'
	}, {
		name : 'amountOrgCtoinTwoLtra',
		type : 'decimal'
	}, {
		name : 'amountOrgCtoinTwoLsit',
		type : 'decimal'
	}, {
		name : 'amountOrgCtoinTwoTrat',
		type : 'decimal'
	}, {
		name : 'amountDestCtoinTwoTro',
		type : 'decimal'
	}, {
		name : 'amountDestCtoinTwoAola',
		type : 'decimal'
	}, {
		name : 'amountDestCtoinTwoLtra',
		type : 'decimal'
	}, {
		name : 'amountDestCtoinTwoLsit',
		type : 'decimal'
	}, {
		name : 'amountDestCtoinTwoTrat',
		type : 'decimal'
	}, {
		name : 'amountExtoincomeTro',
		type : 'decimal'
	}, {
		name : 'amountExtoincomeAola',
		type : 'decimal'
	}, {
		name : 'amountExtoincomeLtra',
		type : 'decimal'
	}, {
		name : 'amountExtoincomeLsit',
		type : 'decimal'
	}, {
		name : 'amountExtoincomeTrat',
		type : 'decimal'
	}, {
		name : 'amountOrMbusIncTwoTro',
		type : 'decimal'
	}, {
		name : 'amountOrMbusIncTwoAola',
		type : 'decimal'
	}, {
		name : 'amountOrMbusIncTwoLtra',
		type : 'decimal'
	}, {
		name : 'amountOrMbusIncTwoLsit',
		type : 'decimal'
	}, {
		name : 'amountOrMbusIncTwoTrat',
		type : 'decimal'
	}, {
		name : 'amountOrRewrTwoTro',
		type : 'decimal'
	}, {
		name : 'amountOrRewrTwoAola',
		type : 'decimal'
	}, {
		name : 'amountOrRewrTwoLtra',
		type : 'decimal'
	}, {
		name : 'amountOrRewrTwoLsit',
		type : 'decimal'
	}, {
		name : 'amountOrRewrTwoTrat',
		type : 'decimal'
	}, {
		name : 'amountLgAllggTro',
		type : 'decimal'
	}, {
		name : 'amountLgAllggAola',
		type : 'decimal'
	}, {
		name : 'amountLgAllggLtra',
		type : 'decimal'
	}, {
		name : 'amountLgAllggLsit',
		type : 'decimal'
	}, {
		name : 'amountLgAllggTrat',
		type : 'decimal'
	}, {
		name : 'amountRetrFreTro',
		type : 'decimal'
	}, {
		name : 'amountRetrFreAola',
		type : 'decimal'
	}, {
		name : 'amountRetrFreLtra',
		type : 'decimal'
	}, {
		name : 'amountRetrFreLsit',
		type : 'decimal'
	}, {
		name : 'amountRetrFreTrat',
		type : 'decimal'
	}, {
		name : 'amountDisIncomeStbTro',
		type : 'decimal'
	}, {
		name : 'amountDisIncomeStbAola',
		type : 'decimal'
	}, {
		name : 'amountDisIncomeStbLtra',
		type : 'decimal'
	}, {
		name : 'amountDisIncomeStbLsit',
		type : 'decimal'
	}, {
		name : 'amountDisIncomeStbTrat',
		type : 'decimal'
	}, {
		name : 'amountDisIncomeRstbTro',
		type : 'decimal'
	}, {
		name : 'amountDisIncomeRstbAola',
		type : 'decimal'
	}, {
		name : 'amountDisIncomeRstbLtra',
		type : 'decimal'
	}, {
		name : 'amountDisIncomeRstbLsit',
		type : 'decimal'
	}, {
		name : 'amountDisIncomeRstbTrat',
		type : 'decimal'
	}, {
		name : 'amountDispcOrgClaimTro',
		type : 'decimal'
	}, {
		name : 'amountDispcOrgClaimAola',
		type : 'decimal'
	}, {
		name : 'amountDispcOrgClaimLtra',
		type : 'decimal'
	}, {
		name : 'amountDispcOrgClaimLsit',
		type : 'decimal'
	}, {
		name : 'amountDispcOrgClaimTrat',
		type : 'decimal'
	}, {
		name : 'amountDispcDestClaimTro',
		type : 'decimal'
	}, {
		name : 'amountDispcDestClaimAola',
		type : 'decimal'
	}, {
		name : 'amountDispcDestClaimLtra',
		type : 'decimal'
	}, {
		name : 'amountDispcDestClaimLsit',
		type : 'decimal'
	}, {
		name : 'amountDispcDestClaimTrat',
		type : 'decimal'
	}, {
		name : 'amountDisspcExtoincomeTro',
		type : 'decimal'
	}, {
		name : 'amountDisspcExtoincomeAola',
		type : 'decimal'
	}, {
		name : 'amountDisspcExtoincomeLtra',
		type : 'decimal'
	}, {
		name : 'amountDisspcExtoincomeLsit',
		type : 'decimal'
	}, {
		name : 'amountDisspcExtoincomeTrat',
		type : 'decimal'
	}, {
		name : 'amountDisspcLgAllggTro',
		type : 'decimal'
	}, {
		name : 'amountDisspcLgAllggAola',
		type : 'decimal'
	}, {
		name : 'amountDisspcLgAllggLtra',
		type : 'decimal'
	}, {
		name : 'amountDisspcLgAllggLsit',
		type : 'decimal'
	}, {
		name : 'amountDisspcLgAllggTrat',
		type : 'decimal'
	} ]
});

// 02特殊业务重分类报表store
Ext.define('Foss.closing.mvrNrfostRStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.closing.mvrNrfostRModel',
	pageSize : 100,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url : closing.realPath('queryNrfostRByConditions.action'),
		timeout : 10 * 60 * 1000,
		reader : {
			type : 'json',
			root : 'mvrNrfostRVo.mvrNrfostRList',
			totalProperty : 'mvrNrfostRVo.count'
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
				var queryForm = Ext.getCmp('T_closing-mvrNrfostR_content')
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
Ext.define('Foss.closing.mvrNrfostRQueryForm', {
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
				name : 'mvrNrfostRVo.period',
				fieldLabel : '期间',
				queryMode : 'remote',
				store : Ext.create('Foss.mvrNrfostR.PeriodStore'),
				displayField : 'name',
				valueField : 'name',
				allowBlank : false,
				columnWidth : .3
			},
			{ // 所属子公司
				xtype : 'commonsubsidiaryselector',
				name : 'mvrNrfostRVo.subIncomeCpmpanyCode',
				fieldLabel : '所属子公司',
				columnWidth : .3,
				listWidth : 260,// 设置下拉框宽度
				isPaging : true
			},
			{// 产品类型
				xtype : 'combo',
				name : 'mvrNrfostRVo.productCode',
				fieldLabel : '产品类型',
				queryMode : 'local',
				store : Ext.create('Foss.mvrNrfostR.ProductTypeStore'),
				displayField : 'name',
				valueField : 'code',
				value : Ext.create('Foss.mvrNrfostR.ProductTypeStore').first()
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
							closing.mvrNrfostR.queryMvrNrfostR(form, me);
						} else {
							Ext.Msg.alert("温馨提醒", "请检查输入条件是否合法");
							return false;
						}
					}
				} ]
			} ]
});

// 02特殊业务重分类报表查询显示Grid
Ext.define('Foss.closing.mvrNrfostRQueryGrid', {
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
		text : '02特殊业务重分类',
		defaults : {
			style : "text-align:center"
		},
		columns : [ {
			text : '理赔',
			defaults : {
				style : "text-align:center"
			},
			columns : [ {
				text : '02理赔冲收入-出发部门申请',
				defaults : {
					style : "text-align:center"
				},
				columns : [ {
					text : '含税收入冲销',
					width : 95,
					dataIndex : 'amountOrgCtoinTwoTro'
				}, {
					text : '物流辅助业金额',
					width : 95,
					dataIndex : 'amountOrgCtoinTwoAola'
				}, {
					text : '物流运输业金额',
					width : 95,
					dataIndex : 'amountOrgCtoinTwoLtra'
				}, {
					text : '物流辅助业税金',
					width : 95,
					dataIndex : 'amountOrgCtoinTwoLsit'
				}, {
					text : '运输业税金',
					width : 95,
					dataIndex : 'amountOrgCtoinTwoTrat'
				} ]
			}, {
				text : '02理赔冲收入-到达部门申请',
				defaults : {
					style : "text-align:center"
				},
				columns : [ {
					text : '含税收入冲销',
					width : 95,
					dataIndex : 'amountDestCtoinTwoTro'
				}, {
					text : '物流辅助业金额',
					width : 95,
					dataIndex : 'amountDestCtoinTwoAola'
				}, {
					text : '物流运输业金额',
					width : 95,
					dataIndex : 'amountDestCtoinTwoLtra'
				}, {
					text : '物流辅助业税金',
					width : 95,
					dataIndex : 'amountDestCtoinTwoLsit'
				}, {
					text : '运输业税金',
					width : 95,
					dataIndex : 'amountDestCtoinTwoTrat'
				} ]
			} ]
		}, {
			text : '异常冲收入',
			defaults : {
				style : "text-align:center"
			},
			columns : [ {
				text : '含税收入冲销',
				width : 95,
				dataIndex : 'amountExtoincomeTro'
			}, {
				text : '物流辅助业金额',
				width : 95,
				dataIndex : 'amountExtoincomeAola'
			}, {
				text : '物流运输业金额',
				width : 95,
				dataIndex : 'amountExtoincomeLtra'
			}, {
				text : '物流辅助业税金',
				width : 95,
				dataIndex : 'amountExtoincomeLsit'
			}, {
				text : '运输业税金',
				width : 95,
				dataIndex : 'amountExtoincomeTrat'
			} ]
		}, {
			text : '小票',
			defaults : {
				style : "text-align:center"
			},
			columns : [ {
				text : '02小票主营业务收入',
				defaults : {
					style : "text-align:center"
				},
				columns : [ {
					text : '含税收入冲销',
					width : 95,
					dataIndex : 'amountOrMbusIncTwoTro'
				}, {
					text : '物流辅助业金额',
					width : 95,
					dataIndex : 'amountOrMbusIncTwoAola'
				}, {
					text : '物流运输业金额',
					width : 95,
					dataIndex : 'amountOrMbusIncTwoLtra'
				}, {
					text : '物流辅助业税金',
					width : 95,
					dataIndex : 'amountOrMbusIncTwoLsit'
				}, {
					text : '运输业税金',
					width : 95,
					dataIndex : 'amountOrMbusIncTwoTrat'
				} ]
			}, {
				text : '02小票应收核销',
				defaults : {
					style : "text-align:center"
				},
				columns : [ {
					text : '含税收入冲销',
					width : 95,
					dataIndex : 'amountOrRewrTwoTro'
				}, {
					text : '物流辅助业金额',
					width : 95,
					dataIndex : 'amountOrRewrTwoAola'
				}, {
					text : '物流运输业金额',
					width : 95,
					dataIndex : 'amountOrRewrTwoLtra'
				}, {
					text : '物流辅助业税金',
					width : 95,
					dataIndex : 'amountOrRewrTwoLsit'
				}, {
					text : '运输业税金',
					width : 95,
					dataIndex : 'amountOrRewrTwoTrat'
				} ]
			} ]
		}, {
			text : '弃货、违禁品、全票丢货',
			defaults : {
				style : "text-align:center"
			},
			columns : [ {
				text : '含税收入冲销',
				width : 95,
				dataIndex : 'amountLgAllggTro'
			}, {
				text : '物流辅助业金额',
				width : 95,
				dataIndex : 'amountLgAllggAola'
			}, {
				text : '物流运输业金额',
				width : 95,
				dataIndex : 'amountLgAllggLtra'
			}, {
				text : '物流辅助业税金',
				width : 95,
				dataIndex : 'amountLgAllggLsit'
			}, {
				text : '运输业税金',
				width : 95,
				dataIndex : 'amountLgAllggTrat'
			} ]
		}, {
			text : '退运费',
			defaults : {
				style : "text-align:center"
			},
			columns : [ {
				text : '含税收入冲销',
				width : 95,
				dataIndex : 'amountRetrFreTro'
			}, {
				text : '物流辅助业金额',
				width : 95,
				dataIndex : 'amountRetrFreAola'
			}, {
				text : '物流运输业金额',
				width : 95,
				dataIndex : 'amountRetrFreLtra'
			}, {
				text : '物流辅助业税金',
				width : 95,
				dataIndex : 'amountRetrFreLsit'
			}, {
				text : '运输业税金',
				width : 95,
				dataIndex : 'amountRetrFreTrat'
			} ]
		} ]
	}, {
		text : '02折扣业务重分类',
		defaults : {
			style : "text-align:center"
		},
		columns : [

		{
			text : '事后折扣【02】-收入调整（签收单）',
			defaults : {
				style : "text-align:center"
			},
			columns : [ {
				text : '含税收入冲销',
				width : 95,
				dataIndex : 'amountDisIncomeStbTro'
			}, {
				text : '物流辅助业金额',
				width : 95,
				dataIndex : 'amountDisIncomeStbAola'
			}, {
				text : '物流运输业金额',
				width : 95,
				dataIndex : 'amountDisIncomeStbLtra'
			}, {
				text : '物流辅助业税金',
				width : 95,
				dataIndex : 'amountDisIncomeStbLsit'
			}, {
				text : '运输业税金',
				width : 95,
				dataIndex : 'amountDisIncomeStbTrat'
			} ]
		},

		{
			text : '事后折扣【02】-收入调整（反签收单）',
			defaults : {
				style : "text-align:center"
			},
			columns : [ {
				text : '含税收入冲销',
				width : 95,
				dataIndex : 'amountDisIncomeRstbTro'
			}, {
				text : '物流辅助业金额',
				width : 95,
				dataIndex : 'amountDisIncomeRstbAola'
			}, {
				text : '物流运输业金额',
				width : 95,
				dataIndex : 'amountDisIncomeRstbLtra'
			}, {
				text : '物流辅助业税金',
				width : 95,
				dataIndex : 'amountDisIncomeRstbLsit'
			}, {
				text : '运输业税金',
				width : 95,
				dataIndex : 'amountDisIncomeRstbTrat'
			} ]
		},

		{
			text : '事后折扣【02】-特殊业务调整',
			defaults : {
				style : "text-align:center"
			},
			columns : [ {
				text : '理赔-出发部门申请',
				defaults : {
					style : "text-align:center"
				},
				columns : [ {
					text : '含税收入冲销',
					width : 95,
					dataIndex : 'amountDispcOrgClaimTro'
				}, {
					text : '物流辅助业金额',
					width : 95,
					dataIndex : 'amountDispcOrgClaimAola'
				}, {
					text : '物流运输业金额',
					width : 95,
					dataIndex : 'amountDispcOrgClaimLtra'
				}, {
					text : '物流辅助业税金',
					width : 95,
					dataIndex : 'amountDispcOrgClaimLsit'
				}, {
					text : '运输业税金',
					width : 95,
					dataIndex : 'amountDispcOrgClaimTrat'
				} ]
			}, {
				text : '理赔-到达部门申请',
				defaults : {
					style : "text-align:center"
				},
				columns : [ {
					text : '含税收入冲销',
					width : 95,
					dataIndex : 'amountDispcDestClaimTro'
				}, {
					text : '物流辅助业金额',
					width : 95,
					dataIndex : 'amountDispcDestClaimAola'
				}, {
					text : '物流运输业金额',
					width : 95,
					dataIndex : 'amountDispcDestClaimLtra'
				}, {
					text : '物流辅助业税金',
					width : 95,
					dataIndex : 'amountDispcDestClaimLsit'
				}, {
					text : '运输业税金',
					width : 95,
					dataIndex : 'amountDispcDestClaimTrat'
				} ]
			}, {
				text : '异常冲收入',
				defaults : {
					style : "text-align:center"
				},
				columns : [ {
					text : '含税收入冲销',
					width : 95,
					dataIndex : 'amountDisspcExtoincomeTro'
				}, {
					text : '物流辅助业金额',
					width : 95,
					dataIndex : 'amountDisspcExtoincomeAola'
				}, {
					text : '物流运输业金额',
					width : 95,
					dataIndex : 'amountDisspcExtoincomeLtra'
				}, {
					text : '物流辅助业税金',
					width : 95,
					dataIndex : 'amountDisspcExtoincomeLsit'
				}, {
					text : '运输业税金',
					width : 95,
					dataIndex : 'amountDisspcExtoincomeTrat'
				} ]
			}, {
				text : '弃货、违禁品、全票丢货',
				defaults : {
					style : "text-align:center"
				},
				columns : [ {
					text : '含税收入冲销',
					width : 95,
					dataIndex : 'amountDisspcLgAllggTro'
				}, {
					text : '物流辅助业金额',
					width : 95,
					dataIndex : 'amountDisspcLgAllggAola'
				}, {
					text : '物流运输业金额',
					width : 95,
					dataIndex : 'amountDisspcLgAllggLtra'
				}, {
					text : '物流辅助业税金',
					width : 95,
					dataIndex : 'amountDisspcLgAllggLsit'
				}, {
					text : '运输业税金',
					width : 95,
					dataIndex : 'amountDisspcLgAllggTrat'
				} ]
			} ]
		}

		]
	} ],

	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);

		me.store = Ext.create('Foss.closing.mvrNrfostRStore');

		me.bbar = me.getPagingToolbar();
		me.getPagingToolbar().store = me.store;
		me.callParent([ cfg ]);
	}
});

/**
 * 查询方法
 */
closing.mvrNrfostR.queryMvrNrfostR = function(form, me) {
	var m = Ext.getCmp('T_closing-mvrNrfostR_content');
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

	if (Ext.getCmp('T_closing-mvrNrfostR_content')) {
		return;
	}

	// 查询FORM
	var queryForm = Ext.create('Foss.closing.mvrNrfostRQueryForm');

	// 显示grid
	var queryGrid = Ext.create('Foss.closing.mvrNrfostRQueryGrid');

	// 显示到JSP页面
	Ext.create('Ext.panel.Panel', {
		id : 'T_closing-mvrNrfostR_content',
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
		renderTo : 'T_closing-mvrNrfostR-body'
	});
});