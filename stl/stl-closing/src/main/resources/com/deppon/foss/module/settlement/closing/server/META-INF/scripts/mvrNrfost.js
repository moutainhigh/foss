/**
 * 获取上个月期间
 */
closing.mvrNrfost.getLastMonthPeriod = function() {
	var now = new Date();
	now.setMonth(now.getMonth() - 1);
	return Ext.Date.format(now, 'Ym');
}

// 客户类型：Model
Ext.define('Foss.mvrNrfost.CustomerTypeModel', {
			extend : 'Ext.data.Model',
			fields : [{
						name : 'customerTypeCode'
					}, {
						name : 'customerTypeName'
					}]

		});

// 客户类型：store
Ext.define('Foss.mvrNrfost.CustomerTypeStore', {
			extend : 'Ext.data.Store',
			model : 'Foss.mvrNrfost.CustomerTypeModel',
			data : {
				'items' : [{
							customerTypeCode : '01',
							customerTypeName : '客户'
						}, {
							customerTypeCode : '02',
							customerTypeName : '空运代理'
						}, {
							customerTypeCode : '03',
							customerTypeName : '偏线代理'
						}, {
							customerTypeCode : '04',
							customerTypeName : '航空公司'
						}, {
							customerTypeCode : '05',
							customerTypeName : '快递代理'
						}]
			},
			proxy : {
				type : 'memory',
				reader : {
					type : 'json',
					root : 'items'
				}
			}
		});

/**
 * 声明账期model
 */
Ext.define('Foss.mvrNrfost.PeriodModel', {
			extend : 'Ext.data.Model',
			fields : ['name', 'code']
		});

/**
 * 声明账期store
 */
Ext.define('Foss.mvrNrfost.PeriodStore', {
			extend : 'Ext.data.Store',
			model : 'Foss.mvrNrfost.PeriodModel',
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

closing.mvrNrfost.setParams = function(form) {
	// 定义查询参数
	var params = {};

	var period = form.findField('mvrNrfostEntityVo.mvrNrfostEntityQueryDto.period').getValue();
	if (period == null || period == '') {
		// 期间不能为空
		Ext.Msg.alert('温馨提示', '期间不能为空');
		return null;
	}

	// 获取FORM所有值
	params = form.getValues();

	// 客户类型
	var custType = form.findField('customerType').getValue();
	// 给客户默认值
	var customerCode = null;
	// 客户类型
	if (custType == '01') {
		customerCode = Ext.getCmp('closing.mvrNrfost.commoncustomerselector').getValue();
	} else if (custType == '02') {
		customerCode = Ext.getCmp('closing.mvrNrfost.commonairagencycompanyselector').getValue();
	} else if (custType == '03') {
		customerCode = Ext.getCmp('closing.mvrNrfost.commonvehagencycompselector').getValue();
	} else if (custType == '04') {
		customerCode = Ext.getCmp('closing.mvrNrfost.commonairlinesselector').getValue();
	} else if (custType == '05') {
		customerCode = Ext.getCmp('closing.mvrNrfost.commonLdpAgencyCompanySelector').getValue();
	}

	Ext.apply(params, {
				'mvrNrfostEntityVo.mvrNrfostEntityQueryDto.productCodeList' : stl.convertProductCode(form
						.findField('mvrNrfostEntityVo.mvrNrfostEntityQueryDto.productCodeList').getValue())
			});

	return params;
}

/**
 * Form重置方法
 */
closing.mvrNrfost.reset = function() {
	this.up('form').getForm().reset();
	// 客户信息
	Ext.getCmp('closing.mvrNrfost.commoncustomerselector').show();
	// 空运代理公司
	Ext.getCmp('closing.mvrNrfost.commonairagencycompanyselector').hide();
	// 偏线代理公司
	Ext.getCmp('closing.mvrNrfost.commonvehagencycompselector').hide();
	// 航空公司
	Ext.getCmp('closing.mvrNrfost.commonairlinesselector').hide();
	// 快递代理
	Ext.getCmp('closing.mvrNrfost.commonLdpAgencyCompanySelector').hide();
}

/**
 * Form查询方法
 */
closing.mvrNrfost.query = function(f, me) {
	var form = f.getForm();
	var grid = Ext.getCmp('T_closing-mvrNrfost_content').getQueryGrid();
	if (form.isValid()) {
		var params = closing.mvrNrfost.setParams(form);
		if (null == params) {
			return;
		}
		// 设置查询参数
		grid.store.setSubmitParams(params);
		// 设置该按钮灰掉
		me.disable(false);
		// 30秒后自动解除灰掉效果
		setTimeout(function() {
					me.enable(true);
				}, 30000);
		// 设置统计值
		grid.store.loadPage(1, {
					callback : function(records, operation, success) {
						var result = Ext.decode(operation.response.responseText);
						me.enable(true);
					}
				});
	} else {

		// 请检查输入条件是否合法
		Ext.Msg.alert('温馨提示', '请检查输入条件合法性');
	}
}

/**
 * 导出02特殊业务始发月报表
 */
closing.mvrNrfost.exportmvrNrfost = function() {
	// 获取主面板、查询GRID
	var queryGrid = Ext.getCmp('T_closing-mvrNrfost_content').getQueryGrid();
	if (queryGrid.store.data.length == 0) {
		Ext.Msg.alert('温馨提示', '表格没有数据，不能进行导出操作');
		return false;
	}

	// 提示是否导出
	Ext.Msg.confirm('温馨提示', '确定导出02特殊业务始发月报表吗?', function(btn, text) {
				if ('yes' == btn) {

					var params = queryGrid.store.submitParams;

					// 创建一个form
					if (!Ext.fly('exportmvrNrfostForm')) {
						var frm = document.createElement('form');
						frm.id = 'exportmvrNrfostForm';
						frm.style.display = 'none';
						document.body.appendChild(frm);
					}

					// 导出Ajax请求
					Ext.Ajax.request({
								url : closing.realPath('exportMvrNrfost.action'),
								form : Ext.fly('exportmvrNrfostForm'),
								params : params,
								method : 'post',
								isUpload : true,
								success : function(response) {
									// var json =
									// Ext.decode(response.responseText);
								},
								failure : function(response) {
									var json = Ext.decode(response.responseText);
									Ext.MessageBox.alert('温馨提示', json.message);
								},
								exception : function(response) {
									var json = Ext.decode(response.responseText);
									Ext.MessageBox.alert('温馨提示', json.message);
								}
							});

				}
			});
}

/**
 * 获取期间控件下拉框Store
 */
closing.mvrNrfost.getComboPeriodStore = function() {
	var now = new Date();
	var nowMonth = now.getMonth();
	var format = 'Ym';

	var periods = [];
	period = Ext.Date.format(now, format);
	periods.push({
				'name' : period,
				'value' : period
			});

	for (var i = 0; i < 12; i++) {
		nowMonth = now.getMonth();
		now.setMonth(nowMonth - 1);
		period = Ext.Date.format(now, format);
		periods.push({
					'name' : period,
					'value' : period
				});
	}

	return Ext.create('Ext.data.Store', {
				model : 'Foss.closing.mvrNrfostComboModel',
				data : periods
			});
}

/**
 * 获取业务类型下拉框Store
 */
closing.mvrNrfost.getComboProductTypeStore = function() {
	return Ext.create('Foss.pkp.ProductStore');
}

/** 数据模型 */

// 下拉框(combo)数据模型
Ext.define('Foss.closing.mvrNrfostComboModel', {
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

// 02特殊业务始发月报表数据模型
Ext.define('Foss.closing.mvrNrfostModel', {
			extend : 'Ext.data.Model',
			fields : [{
						name : 'period',
						type : 'string'
					}, {
						name : 'productCode',
						type : 'string'
					}, {
						name : 'customerCode',
						type : 'string'
					}, {
						name : 'customerName',
						type : 'string'
					}, {
						name : 'origOrgCode',
						type : 'string'
					}, {
						name : 'origOrgName',
						type : 'string'
					}, {
						name : 'destOrgCode',
						type : 'string'
					}, {
						name : 'destOrgName',
						type : 'string'
					}, {
						name : 'origUnifiedCode',
						type : 'string'
					}, {
						name : 'destUnifiedCode',
						type : 'string'
					}, {
						name : 'voucherTime',
						type : 'date'
					}, {
						name : 'customerType',
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
						name : 'claimOrigtIncome',
						type : 'double'
					}, {
						name : 'claimOrigtCost',
						type : 'double'
					}, {
						name : 'claimOrigoWoOrigRcvtPod',
						type : 'double'
					}, {
						name : 'claimOrigtOrigRcvoPod',
						type : 'double'
					}, {
						name : 'claimOrigoWoOrigRcvtNpod',
						type : 'double'
					}, {
						name : 'claimOrigtWoOrigRcvoNpod',
						type : 'double'
					}, {
						name : 'claimOrigoPayApply',
						type : 'double'
					}, {
						name : 'claimOrigtPayApply',
						type : 'double'
					}, {
						name : 'claimOrigtWoOrigRcvtPod',
						type : 'double'
					}, {
						name : 'claimOrigtWoOrigRcvtNpod',
						type : 'double'
					}, {
						name : 'claimDesttIncome',
						type : 'double'
					}, {
						name : 'claimDestoWoDestRcvtPod',
						type : 'double'
					}, {
						name : 'claimDesttWoDestRcvtPod',
						type : 'double'
					}, {
						name : 'claimDestoWoDestRcvtNpod',
						type : 'double'
					}, {
						name : 'claimDesttWoDestRcvtNpod',
						type : 'double'
					}, {
						name : 'exOrigRcvtPod',
						type : 'double'
					}, {
						name : 'exDestRcvtPod',
						type : 'double'
					}, {
						name : 'bdWoOrigRcvtPod',
						type : 'double'
					}, {
						name : 'bdWoDestRcvtPod',
						type : 'double'
					}, {
						name : 'orChAc',
						type : 'double'
					}, {
						name : 'orChSi',
						type : 'double'
					}, {
						name : 'orChOpay',
						type : 'double'
					}, {
						name : 'orChOther',
						type : 'double'
					}, {
						name : 'orChPbio',
						type : 'double'
					}, {
						name : 'orChPbit',
						type : 'double'
					}, {
						name : 'orCdAc',
						type : 'double'
					}, {
						name : 'orCdBankInt',
						type : 'double'
					}, {
						name : 'orCdOpay',
						type : 'double'
					}, {
						name : 'orCdOther',
						type : 'double'
					}, {
						name : 'orCdPbio',
						type : 'double'
					}, {
						name : 'orCdPbit',
						type : 'double'
					}, {
						name : 'orCdSu',
						type : 'double'
					}, {
						name : 'orCdBu',
						type : 'double'
					}, {
						name : 'orRcvtPbi',
						type : 'double'
					}, {
						name : 'orChUrRcvo',
						type : 'double'
					}, {
						name : 'orCdUrRcvo',
						type : 'double'
					}, {
						name : 'orChUrRcvt',
						type : 'double'
					}, {
						name : 'orCdUrRcvt',
						type : 'double'
					}, {
						name : 'orCodPayWoRcvt',
						type : 'double'
					}, {
						name : 'orClaimPayoWoRcvt',
						type : 'double'
					}, {
						name : 'orClaimPaytWoRcvt',
						type : 'double'
					}, {
						name : 'orClaimPaytWoRcvo',
						type : 'double'
					}, {
						name : 'orCustDroWoRcvt',
						type : 'double'
					}, {
						name : 'orCustDrtWoRcvo',
						type : 'double'
					}, {
						name : 'orCustDrtWoRcvt',
						type : 'double'
					}, {
						name : 'orExWoRcvt',
						type : 'double'
					}, {
						name : 'orBadWoRcvt',
						type : 'double'
					}, {
						name : 'acOrigRcvNwo',
						type : 'double'
					}, {
						name : 'acOrigRcvWo',
						type : 'double'
					}, {
						name : 'acCash',
						type : 'double'
					}, {
						name : 'rdOrigoPayApply',
						type : 'double'
					}, {
						name : 'rdOrigtIncome',
						type : 'double'
					}, {
						name : 'rdOrigtCost',
						type : 'double'
					}, {
						name : 'rdOrigtPayApply',
						type : 'double'
					}, {
						name : 'rdOrigoWoOrigRcvtPod',
						type : 'double'
					}, {
						name : 'rdOrigtWoOrigRcvoPod',
						type : 'double'
					}, {
						name : 'rdOrigtWoOrigRcvtPod',
						type : 'double'
					}, {
						name : 'rdOrigoWoOrigRcvtNpod',
						type : 'double'
					}, {
						name : 'rdOrigtWoOrigRcvoNpod',
						type : 'double'
					}, {
						name : 'rdOrigtWoOrigRcvtNpod',
						type : 'double'
					}, {
						name : 'rdDesttIncome',
						type : 'double'
					}, {
						name : 'rdDestoDestRcvtPod',
						type : 'double'
					}, {
						name : 'rdDesttWoDestRcvtPod',
						type : 'double'
					}, {
						name : 'rdDestoWoDestRcvtNpod',
						type : 'double'
					}, {
						name : 'rdDesttWoDestRcvtNpod',
						type : 'double'
					}, {
						name : 'sfoPayApply',
						type : 'double'
					}, {
						name : 'sftPayApply',
						type : 'double'
					}, {
						name : 'cpoOrigPayApply',
						type : 'double'
					}, {
						name : 'cptOrigPayApply',
						type : 'double'
					}, {
						name : 'orChRentIncome',
						type : 'double'
					}, {
						name : 'orChBankInt',
						type : 'double'
					}, {
						name : 'orCdRentIncome',
						type : 'double'
					}, {
						name : 'custDrOch',
						type : 'double'
					}, {
						name : 'custDrOcd',
						type : 'double'
					}, {
						name : 'custDrTch',
						type : 'double'
					}, {
						name : 'custDrTcd',
						type : 'double'
					}, {
						name : 'custDroWoOrigRcvtNpod',
						type : 'double'
					}, {
						name : 'custDrtWoOrigRcvoNpod',
						type : 'double'
					}, {
						name : 'custDroWoOrigRcvtPod',
						type : 'double'
					}, {
						name : 'custDrtWoOrigRcvoPod',
						type : 'double'
					}, {
						name : 'custDroPayApply',
						type : 'double'
					}, {
						name : 'custDrtPayApply',
						type : 'double'
					}, {
						name : 'custDroWoDestRcvtNpod',
						type : 'double'
					}, {
						name : 'custDrtWoDestRcvtNpod',
						type : 'double'
					}, {
						name : 'custDroWoDestRcvtPod',
						type : 'double'
					}, {
						name : 'custDrtWoDestRcvtPod',
						type : 'double'
					}, {
						name : 'custDrtWoOrigRcvtNpod',
						type : 'double'
					}, {
						name : 'custDrtWoOrigRcvtPod',
						type : 'double'
					}, {
						name : 'plCostWoDestRcvtPod',
						type : 'double'
					}, {
						name : 'plDrWoDestRcvtPod',
						type : 'double'
					}, {
						name : 'plDrWoDestRcvtNpod',
						type : 'double'
					}, {
						name : 'alDrWoDestRcvtPod',
						type : 'double'
					}, {
						name : 'alDrWoDestRcvtNpod',
						type : 'double'
					}, {
						name : 'alpwrWoDestRcvtPod',
						type : 'double'
					}, {
						name : 'alpwrWoDestRcvtNpod',
						type : 'double'
					}, {
						name : 'othPayWoDestRcvtPod',
						type : 'double'
					}, {
						name : 'othPayWoDestRcvtNpod',
						type : 'double'
					}, {
						name : 'popWoDrtPod',
						type : 'double'
					}, {
						name : 'popWoDrtNpod',
						type : 'double'
					}, {
						name : 'plCostWoDestRcvtNpod',
						type : 'double'
					}, {
						name : 'orChForklift',
						type : 'double'
					}, {
						name : 'orCdForklift',
						type : 'double'
					}, {
						name : 'orChHc',
						type : 'double'
					}, {
						name : 'orCdHc',
						type : 'double'
					},{
                        name : 'orChStorage',
                        type : 'double'
                    },{
                        name : 'orChWs',
                        type : 'double'
                    },{
                        name : 'orCdStorage',
                        type : 'double'
                    },{
                        name : 'orCdWs',
                        type : 'double'
                    },{
                        name : 'orRcvtStorage',
                        type : 'double'
                    },{
                        name : 'orRcvtWs',
                        type : 'double'
                    },{
                        name : 'orExWoStorage',
                        type : 'double'
                    },{
                        name : 'orExWoWs',
                        type : 'double'
                    }]
		});

// 始发应收Store
Ext.define('Foss.closing.mvrNrfostStore', {
			extend : 'Ext.data.Store',
			model : 'Foss.closing.mvrNrfostModel',
			pageSize : 100,
			proxy : {
				type : 'ajax',
				actionMethods : 'post',
				url : closing.realPath('queryMvrNrfost.action'),
				timeout : 10 * 60 * 1000,
				reader : {
					type : 'json',
					root : 'mvrNrfostEntityVo.mvrNrfostEntityResultDto.mvrNrfostEntityList',
					totalProperty : 'totalCount'
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
						Ext.apply(me.submitParams, {
									"limit" : operation.limit,
									"page" : operation.page,
									"start" : operation.start
								});
						Ext.apply(operation, {
									params : me.submitParams
								});
					}
				};
				me.callParent([cfg]);
			}
		});

// 始发应收月报查询Form
Ext.define('Foss.closing.mvrNrfostQueryForm', {
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
						name : 'mvrNrfostEntityVo.mvrNrfostEntityQueryDto.period',
						fieldLabel : '期间',
						/*
						 * forceSelection : true, displayField : 'name',
						 * valueField : 'value', queryMode : 'local',
						 * triggerAction : 'all', editable : false, value :
						 * closing.mvrNrfost.getLastMonthPeriod(), store :
						 * closing.mvrNrfost.getComboPeriodStore(), columnWidth :
						 * .33
						 */
						queryMode : 'remote',
						store : Ext.create('Foss.mvrNrfost.PeriodStore'),
						displayField : 'name',
						valueField : 'name',
						allowBlank : false,
						columnWidth : .33
					}, {
						xtype : 'combobox',
						name : 'customerType',
						columnWidth : .33,
						fieldLabel : '客户类型',
						store : Ext.create('Foss.mvrNrfost.CustomerTypeStore'),
						queryModel : 'local',
						value : '01',
						editable : false,
						displayField : 'customerTypeName',
						valueField : 'customerTypeCode',
						listeners : {
							"select" : {
								fn : function(_combo, _r) {
									var cusValue = _combo.ownerCt.getForm().findField('customerType').getValue();
									if (cusValue === '01') {
										// 客户信息
										Ext.getCmp('closing.mvrNrfost.commoncustomerselector').show();
										// 空运代理公司
										Ext.getCmp('closing.mvrNrfost.commonairagencycompanyselector').hide();
										// 偏线代理公司
										Ext.getCmp('closing.mvrNrfost.commonvehagencycompselector').hide();
										// 航空公司
										Ext.getCmp('closing.mvrNrfost.commonairlinesselector').hide();
										// 快递代理
										Ext.getCmp('closing.mvrNrfost.commonLdpAgencyCompanySelector').hide();
									} else if (cusValue === '02') {
										// 空运代理公司
										Ext.getCmp('closing.mvrNrfost.commonairagencycompanyselector').show();
										// 客户信息
										Ext.getCmp('closing.mvrNrfost.commoncustomerselector').hide();
										// 偏线代理公司
										Ext.getCmp('closing.mvrNrfost.commonvehagencycompselector').hide();
										// 航空公司
										Ext.getCmp('closing.mvrNrfost.commonairlinesselector').hide();
										// 快递代理
										Ext.getCmp('closing.mvrNrfost.commonLdpAgencyCompanySelector').hide();
									} else if (cusValue === '03') {
										// 偏线代理公司
										Ext.getCmp('closing.mvrNrfost.commonvehagencycompselector').show();
										// 客户信息
										Ext.getCmp('closing.mvrNrfost.commoncustomerselector').hide();
										// 空运代理公司
										Ext.getCmp('closing.mvrNrfost.commonairagencycompanyselector').hide();
										// 航空公司
										Ext.getCmp('closing.mvrNrfost.commonairlinesselector').hide();
										// 快递代理
										Ext.getCmp('closing.mvrNrfost.commonLdpAgencyCompanySelector').hide();
									} else if (cusValue === '04') {
										// 航空公司
										Ext.getCmp('closing.mvrNrfost.commonairlinesselector').show();
										// 客户信息
										Ext.getCmp('closing.mvrNrfost.commoncustomerselector').hide();
										// 偏线代理公司
										Ext.getCmp('closing.mvrNrfost.commonvehagencycompselector').hide();
										// 空运代理公司
										Ext.getCmp('closing.mvrNrfost.commonairagencycompanyselector').hide();
										// 快递代理
										Ext.getCmp('closing.mvrNrfost.commonLdpAgencyCompanySelector').hide();
									} else if (cusValue === '05') {
										// 快递代理
										Ext.getCmp('closing.mvrNrfost.commonLdpAgencyCompanySelector').show();
										// 客户信息
										Ext.getCmp('closing.mvrNrfost.commoncustomerselector').hide();
										// 偏线代理公司
										Ext.getCmp('closing.mvrNrfost.commonvehagencycompselector').hide();
										// 航空公司
										Ext.getCmp('closing.mvrNrfost.commonairlinesselector').hide();
										// 空运代理公司
										Ext.getCmp('closing.mvrNrfost.commonairagencycompanyselector').hide();
									}
								}
							}
						}
					}, {
						xtype : 'commoncustomerselector',
						listWidth : 300,
						fieldLabel : '客户信息',
						name : 'mvrNrfostEntityVo.mvrNrfostEntityQueryDto.customerCode',
						singlePeopleFlag : 'Y',
						id : 'closing.mvrNrfost.commoncustomerselector',
						columnWidth : .33,
						isPaging : true
						// 分页
				}	, {
						xtype : 'commonallagentselector',
						fieldLabel : '空运代理公司',
						columnWidth : .33,
						name : 'mvrNrfostEntityVo.mvrNrfostEntityQueryDto.customerCode',
						id : 'closing.mvrNrfost.commonairagencycompanyselector',
						isPaging : true,// 分页
						hidden : true
					}, {
						xtype : 'commonvehagencycompselector',
						fieldLabel : '偏线代理公司',
						columnWidth : .33,
						name : 'mvrNrfostEntityVo.mvrNrfostEntityQueryDto.customerCode',
						id : 'closing.mvrNrfost.commonvehagencycompselector',
						isPaging : true,// 分页
						hidden : true
					}, {
						xtype : 'commonairlinesselector',
						fieldLabel : '航空公司',
						columnWidth : .33,
						name : 'mvrNrfostEntityVo.mvrNrfostEntityQueryDto.customerCode',
						id : 'closing.mvrNrfost.commonairlinesselector',
						isPaging : true,// 分页
						hidden : true
					}, {
						xtype : 'commonLdpAgencyCompanySelector',
						fieldLabel : '快递代理',
						columnWidth : .33,
						name : 'mvrNrfostEntityVo.mvrNrfostEntityQueryDto.customerCode',
						id : 'closing.mvrNrfost.commonLdpAgencyCompanySelector',
						isPaging : true,// 分页
						hidden : true
					}/*
						 * , { xtype : 'commoncustomerselector', fieldLabel :
						 * '客户', name :
						 * 'mvrNrfostEntityVo.mvrNrfostEntityQueryDto.customerCode',
						 * columnWidth : .33 }
						 */, {
						xtype : 'combo',
						name : 'mvrNrfostEntityVo.mvrNrfostEntityQueryDto.productCodeList',
						fieldLabel : '运输性质',
						forceSelection : true,
						multiSelect : true,
						displayField : 'name',
						valueField : 'code',
						queryMode : 'local',
						triggerAction : 'all',
						editable : false,
						value : closing.mvrNrfost.getComboProductTypeStore().first().get('code'),
						store : closing.mvrNrfost.getComboProductTypeStore(),
						columnWidth : .33
					}, {
						xtype : 'dynamicorgcombselector',
						name : 'mvrNrfostEntityVo.mvrNrfostEntityQueryDto.origOrgCode',
						fieldLabel : '始发部门',
						allowblank : true,
						listWidth : 300,// 设置下拉框宽度
						isPaging : true,
						columnWidth : .33
					}, {
						xtype : 'dynamicorgcombselector',
						name : 'mvrNrfostEntityVo.mvrNrfostEntityQueryDto.destOrgCode',
						fieldLabel : '到达部门',
						allowblank : true,
						listWidth : 300,// 设置下拉框宽度
						isPaging : true,
						columnWidth : .33
					}, {
						border : 1,
						xtype : 'container',
						columnWidth : 1,
						colspan : 3,
						defaultType : 'button',
						layout : 'column',
						items : [{
									text : '重置',
									columnWidth : .1,
									handler : closing.mvrNrfost.reset
								}, {
									xtype : 'container',
									border : false,
									html : '&nbsp;',
									columnWidth : .8
								}, {
									text : '查询',
									columnWidth : .1,
									cls : 'yellow_button',
									handler : function() {
										var form = this.up('form');
										var me = this;
										closing.mvrNrfost.query(form, me)
									}
								}]
					}]
		})

// 02特殊业务始发月报表查询Grid
Ext.define('Foss.closing.mvrNrfostQueryGrid', {
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
			height : 850,
			emptyText : '查询结果为空',
			viewConfig : {
				enableTextSelection : true,
				// 设置行可以选择，进而可以复制
				getRowClass : function(record, rowIndex, rowParams, store) {
					count = store.getCount();
					if (count > 0 && rowIndex == count - 1) {
						return 'closing-totalBgColor';
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
								plugins : 'pagesizeplugin',
								items : [me.getExportButton()]
							});
				}
				return me.pagingToolbar;
			},
			exportButton : null,
			getExportButton : function() {
				var me = this;
				if (Ext.isEmpty(me.exportButton)) {
					me.exportButton = Ext.create('Ext.Button', {
								text : '导出',
								height : 20,
								handler : closing.mvrNrfost.exportmvrNrfost,
								disabled : !closing.mvrNrfost.isPermission('/stl-web/closing/exportMvrNrfost.action'),
								hidden : !closing.mvrNrfost.isPermission('/stl-web/closing/exportMvrNrfost.action')
							});
				}
				return me.exportButton;
			},
			columns : [{
						text : '数据统计维度',
						defaults : {
							style : "text-align:center"
						},
						columns : [{
									xtype : 'rownumberer',
									width : 40
								}, {
									text : '期间',
									width : 110,
									dataIndex : 'period'
								}, {
									text : '运输性质',
									width : 110,
									dataIndex : 'productCode',
									renderer : function(value) {
										return Foss.pkp.ProductData.rendererSubmitToDisplay(value);
									}
								}, {
									text : '客户名称',
									width : 110,
									dataIndex : 'customerName'
								}, {
									text : '客户编码',
									width : 110,
									dataIndex : 'customerCode'
								}, {
									text : '始发部门编</br>码',
									width : 110,
									dataIndex : 'origOrgCode'
								}, {
									text : '始发部门名称',
									width : 110,
									dataIndex : 'origOrgName'
								}, {
									text : '到达部门编码',
									width : 110,
									dataIndex : 'destOrgCode'
								}, {
									text : '到达部门名称',
									width : 110,
									dataIndex : 'destOrgName'
								}, {
								    text : '统一结算类型',
									width:110,
									dataIndex : 'unifiedSettlementType',
							        renderer: function (value, metaData, record, rowIndex, colIndex, store) {
							            if (rowIndex != store.data.length - 1) {
							                return value == 'ORIG' ? "始发统一结算" : value == 'DEST' ? "到达统一结算" : "非统一结算";
							            }
							        }
								}, {
									text : '合同部门编码',
									width:110,
									dataIndex : 'contractOrgCode'
								}, {
									text : '合同部门名称',
									width:110,
									dataIndex : 'contractOrgName'
								}]
					}, {
						text : '理赔',
						columns : [{
									text : '出发部门申请',
									columns : [{
												text : '02理赔冲收入',
												width : 180,
												align : 'right',
												dataIndex : 'claimOrigtIncome'
											}, {
												text : '02理赔入成本',
												width : 180,
												align : 'right',
												dataIndex : 'claimOrigtCost'
											}, {
												text : '01理赔冲02始发应收已签收',
												width : 180,
												align : 'right',
												dataIndex : 'claimOrigoWoOrigRcvtPod'
											}, {
												text : '02理赔冲01始发应收已签收',
												width : 180,
												align : 'right',
												dataIndex : 'claimOrigtOrigRcvoPod'
											}, {
												text : '02理赔冲02始发应收已签收',
												width : 180,
												align : 'right',
												dataIndex : 'claimOrigtWoOrigRcvtPod'
											}, {
												text : '01理赔冲02始发应收未签收',
												width : 180,
												align : 'right',
												dataIndex : 'claimOrigoWoOrigRcvtNpod'
											}, {
												text : '02理赔冲01始发应收未签收',
												width : 180,
												align : 'right',
												dataIndex : 'claimOrigtWoOrigRcvoNpod'
											}, {
												text : '02理赔冲02始发应收未签收',
												width : 180,
												align : 'right',
												dataIndex : 'claimOrigtWoOrigRcvtNpod'
											}, {
												text : '01理赔付款申请',
												width : 180,
												align : 'right',
												dataIndex : 'claimOrigoPayApply'
											}, {
												text : '02理赔付款申请',
												width : 180,
												align : 'right',
												dataIndex : 'claimOrigtPayApply'
											}]
								}, {
									text : '到达部门申请',
									columns : [{
												text : '02理赔冲收入',
												width : 180,
												align : 'right',
												dataIndex : 'claimDesttIncome'
											}, {
												text : '01理赔冲02到达应收已签收',
												width : 180,
												align : 'right',
												dataIndex : 'claimDestoWoDestRcvtPod'
											}, {
												text : '02理赔冲02到达应收已签收',
												width : 180,
												align : 'right',
												dataIndex : 'claimDesttWoDestRcvtPod'
											}, {
												text : '01理赔冲02到达应收未签收',
												width : 180,
												align : 'right',
												dataIndex : 'claimDestoWoDestRcvtNpod'
											}, {
												text : '02理赔冲02到达应收未签收',
												width : 180,
												align : 'right',
												dataIndex : 'claimDesttWoDestRcvtNpod'
											}]
								}]
					}, {
						text : '异常冲收入',
						columns : [{
									text : '02应收始发运费已签收',
									width : 180,
									align : 'right',
									dataIndex : 'exOrigRcvtPod'
								}, {
									text : '02应收到付运费已签收',
									width : 180,
									align : 'right',
									dataIndex : 'exDestRcvtPod'
								}]
					}, {
						text : '坏账损失',
						columns : [{
									text : '坏账冲02应收始发运费已签收',
									width : 180,
									align : 'right',
									dataIndex : 'bdWoOrigRcvtPod'
								}, {
									text : '坏账冲02应收到付运费已签收',
									width : 180,
									align : 'right',
									dataIndex : 'bdWoDestRcvtPod'
								}]
					}, {
						text : '小票',
						columns : [{
									text : '小票录入现金',
									columns : [{
												text : '小票现金之事故赔款 ',
												width : 180,
												align : 'right',
												dataIndex : 'orChAc'
											}, {
												text : '小票现金之变卖废品收入 ',
												width : 180,
												align : 'right',
												dataIndex : 'orChSi'
											}, {
												text : '小票现金之客户多付运费或盘点长款金额  ',
												width : 180,
												align : 'right',
												dataIndex : 'orChOpay'
											}, {
												text : '小票现金之其他 ',
												width : 180,
												align : 'right',
												dataIndex : 'orChOther'
											}, {
												text : '小票现金之富余仓库出租收入 ',
												width : 180,
												align : 'right',
												dataIndex : 'orChRentIncome'
											}, {
												text : '小票现金之收银员卡利息 ',
												width : 180,
												align : 'right',
												dataIndex : 'orChBankInt'
											}, {
                                                text : '小票现金之手续费 ',
                                                width : 180,
                                                align : 'right',
                                                dataIndex : 'orChHc'
                                            }, {
												text : '小票现金之叉车费 ',
												width : 180,
												align : 'right',
												dataIndex : 'orChForklift'
											}, {
												text : '01小票现金主营业务收入 ',
												width : 180,
												align : 'right',
												dataIndex : 'orChPbio'
											}, {
												text : '02小票现金主营业务收入 ',
												width : 180,
												align : 'right',
												dataIndex : 'orChPbit'
                                            }, {
                                                text : '02小票现金之仓储费 ',
                                                width : 180,
                                                align : 'right',
                                                dataIndex : 'orChStorage'
                                            }, {
                                                text : '02小票现金之外发收入 ',
                                                width : 180,
                                                align : 'right',
                                                dataIndex : 'orChWs'
                                            }]
								}, {
									text : '小票录入银行',
									columns : [{
												text : '小票银行之事故赔款  ',
												width : 180,
												align : 'right',
												dataIndex : 'orCdAc'
											}, {
												text : '小票银行之收银员卡利息 ',
												width : 180,
												align : 'right',
												dataIndex : 'orCdBankInt'
											}, {
												text : '小票银行之客户多付运费或盘点长款金额  ',
												width : 180,
												align : 'right',
												dataIndex : 'orCdOpay'
											}, {
												text : '小票银行之其他 ',
												width : 180,
												align : 'right',
												dataIndex : 'orCdOther'
											}, {
												text : '小票银行之富余仓库出租收入 ',
												width : 180,
												align : 'right',
												dataIndex : 'orCdRentIncome'
											}, {
												text : '小票银行之手续费 ',
												width : 180,
												align : 'right',
												dataIndex : 'orCdHc'
											}, {
                                                text : '小票银行之叉车费 ',
                                                width : 180,
                                                align : 'right',
                                                dataIndex : 'orCdForklift'
                                            }, {
												text : '01小票银行主营业务收入 ',
												width : 180,
												align : 'right',
												dataIndex : 'orCdPbio'
											}, {
												text : '02小票银行主营业务收入 ',
												width : 180,
												align : 'right',
												dataIndex : 'orCdPbit'
                                            }, {
                                                text : '02小票银行之仓储费 ',
                                                width : 180,
                                                align : 'right',
                                                dataIndex : 'orCdStorage'
                                            }, {
                                                text : '02小票银行之外发收入 ',
                                                width : 180,
                                                align : 'right',
                                                dataIndex : 'orCdWs'
											}, {
												text : '小票银行之系统使用费 ',
												width : 180,
												align : 'right',
												dataIndex : 'orCdSu'
											}, {
												text : '小票银行之品牌使用费 ',
												width : 180,
												align : 'right',
												dataIndex : 'orCdBu'
                                            }]
								}, {
									text : '小票录入应收',
									columns : [{
												text : '02小票应收主营业务收入 ',
												width : 180,
												align : 'right',
												dataIndex : 'orRcvtPbi'
											},{
                                                text : '02小票应收之仓储费 ',
                                                width : 180,
                                                align : 'right',
                                                dataIndex : 'orRcvtStorage'
                                            },{
                                                text : '02小票应收之外发收入 ',
                                                width : 180,
                                                align : 'right',
                                                dataIndex : 'orRcvtWs'
                                            }]
								}, {
									text : '小票应收核销',
									columns : [{
												text : '还款现金冲01小票应收 ',
												width : 180,
												align : 'right',
												dataIndex : 'orChUrRcvo'
											}, {
												text : '还款银行冲01小票应收 ',
												width : 180,
												align : 'right',
												dataIndex : 'orCdUrRcvo'
											}, {
												text : '还款现金冲02小票应收 ',
												width : 180,
												align : 'right',
												dataIndex : 'orChUrRcvt'
											}, {
												text : '还款银行冲02小票应收 ',
												width : 180,
												align : 'right',
												dataIndex : 'orCdUrRcvt'
											}, {
												text : '应付代收货款冲02小票应收 ',
												width : 180,
												align : 'right',
												dataIndex : 'orCodPayWoRcvt'
											}, {
												text : '02应付理赔冲02小票应收 ',
												width : 180,
												align : 'right',
												dataIndex : 'orClaimPaytWoRcvt'
											}, {
												text : '01应付理赔冲02小票应收 ',
												width : 180,
												align : 'right',
												dataIndex : 'orClaimPayoWoRcvt'
											}, {
												text : '02应付理赔冲01小票应收 ',
												width : 180,
												align : 'right',
												dataIndex : 'orClaimPaytWoRcvo'
											}, {
												text : '02预收客户冲02小票应收 ',
												width : 180,
												align : 'right',
												dataIndex : 'orCustDrtWoRcvt'
											}, {
												text : '01预收客户冲02小票应收 ',
												width : 180,
												align : 'right',
												dataIndex : 'orCustDroWoRcvt'
											}, {
												text : '02预收客户冲01小票应收 ',
												width : 180,
												align : 'right',
												dataIndex : 'orCustDrtWoRcvo'
											}, {
												text : '异常冲收入冲02小票应收 ',
												width : 180,
												align : 'right',
												dataIndex : 'orExWoRcvt'
                                            }, {
                                                text : '异常冲收入冲02小票应收之仓储费 ',
                                                width : 180,
                                                align : 'right',
                                                dataIndex : 'orExWoStorage'
                                            }, {
                                                text : '异常冲收入冲02小票应收之外发收入 ',
                                                width : 180,
                                                align : 'right',
                                                dataIndex : 'orExWoWs'
											}, {
												text : '坏账损失冲02小票应收 ',
												width : 180,
												align : 'right',
												dataIndex : 'orBadWoRcvt'
											}]
								}]
					}, {
						text : '弃货、违禁品、全票丢货',
						columns : [{
									text : '开单且为月结临时欠款网上支付未核销',
									width : 180,
									align : 'right',
									dataIndex : 'acOrigRcvNwo'
								}, {
									text : '开单且为月结临时欠款网上支付已核销',
									width : 180,
									align : 'right',
									dataIndex : 'acOrigRcvWo'
								}, {
									text : '开单且为现金银行卡',
									width : 180,
									align : 'right',
									dataIndex : 'acCash'
								}]
					}, {
						text : '退运费',
						columns : [{
									text : '出发部门申请',
									columns : [{
												text : '02退运费冲收入',
												width : 180,
												align : 'right',
												dataIndex : 'rdOrigtIncome'
											}, {
												text : '02退运费入成本',
												width : 180,
												align : 'right',
												dataIndex : 'rdOrigtCost'
											}, {
												text : '01退运费付款申请',
												width : 180,
												align : 'right',
												dataIndex : 'rdOrigoPayApply'
											}, {
												text : '02退运费付款申请',
												width : 180,
												align : 'right',
												dataIndex : 'rdOrigtPayApply'
											}, {
												text : '02退运费冲02始发应收已签收',
												width : 180,
												align : 'right',
												dataIndex : 'rdOrigtWoOrigRcvtPod'
											}, {
												text : '01退运费冲02始发应收已签收',
												width : 180,
												align : 'right',
												dataIndex : 'rdOrigoWoOrigRcvtPod'
											}, {
												text : '02退运费冲01始发应收已签收',
												width : 180,
												align : 'right',
												dataIndex : 'rdOrigtWoOrigRcvoPod'
											}, {
												text : '02退运费冲02始发应收未签收',
												width : 180,
												align : 'right',
												dataIndex : 'rdOrigtWoOrigRcvtNpod'
											}, {
												text : '01退运费冲02始发应收未签收',
												width : 180,
												align : 'right',
												dataIndex : 'rdOrigoWoOrigRcvtNpod'
											}, {
												text : '02退运费冲01始发应收未签收',
												width : 180,
												align : 'right',
												dataIndex : 'rdOrigtWoOrigRcvoNpod'
											}]
								}, {
									text : '到达部门申请',
									columns : [{
												text : '02退运费冲收入',
												width : 180,
												align : 'right',
												dataIndex : 'rdDesttIncome'
											}, {
												text : '01退运费冲02到达应收已签收',
												width : 180,
												align : 'right',
												dataIndex : 'rdDestoDestRcvtPod'
											}, {
												text : '02退运费冲02到达应收已签收',
												width : 180,
												align : 'right',
												dataIndex : 'rdDesttWoDestRcvtPod'
											}, {
												text : '01退运费冲02到达应收未签收',
												width : 180,
												align : 'right',
												dataIndex : 'rdDestoWoDestRcvtNpod'
											}, {
												text : '02退运费冲02到达应收未签收',
												width : 180,
												align : 'right',
												dataIndex : 'rdDesttWoDestRcvtNpod'
											}]
								}]
					}, {
						text : '装卸费',
						columns : [{
									text : '01装卸费付款申请',
									width : 180,
									align : 'right',
									dataIndex : 'sfoPayApply'
								}, {
									text : '02装卸费付款申请',
									width : 180,
									align : 'right',
									dataIndex : 'sftPayApply'
								}]
					}, {
						text : '服务补救',
						columns : [{
									text : '01始发服务补救付款申请',
									width : 180,
									align : 'right',
									dataIndex : 'cpoOrigPayApply'
								}, {
									text : '02始发服务补救付款申请',
									width : 180,
									align : 'right',
									dataIndex : 'cptOrigPayApply'
								}]
					}, {
						text : '预收客户',
						columns : [{
							text:'预收客户【01】',
							columns:[{
								text : '预收客户现金',
								width : 180,
								align : 'right',
								dataIndex : 'custDrOch'
							}, {
								text : '预收客户银行',
								width : 180,
								align : 'right',
								dataIndex : 'custDrOcd'
							}]
						},{
							text:'预收客户【02】',
							columns:[{
								text : '预收客户现金',
								width : 180,
								align : 'right',
								dataIndex : 'custDrTch'
							}, {
								text : '预收客户银行',
								width : 180,
								align : 'right',
								dataIndex : 'custDrTcd'
							}]
						}, {
							text : '01预收客户冲02应收到付运费未签收',
							width : 180,
							align : 'right',
							dataIndex : 'custDroWoDestRcvtNpod'
						}, {
							text : '02预收客户冲02应收到付运费未签收',
							width : 180,
							align : 'right',
							dataIndex : 'custDrtWoDestRcvtNpod'
						}, {
							text : '01预收客户冲02应收到付运费已签收',
							width : 180,
							align : 'right',
							dataIndex : 'custDroWoDestRcvtPod'
						}, {
							text : '02预收客户冲02应收到付运费已签收',
							width : 180,
							align : 'right',
							dataIndex : 'custDrtWoDestRcvtPod'
						}, {
							text : '02预收客户冲02应收始发运费未签收',
							width : 180,
							align : 'right',
							dataIndex : 'custDrtWoOrigRcvtNpod'
						}, {
							text : '01预收客户冲02应收始发运费未签收',
							width : 180,
							align : 'right',
							dataIndex : 'custDroWoOrigRcvtNpod'
						}, {
							text : '02预收客户冲01应收始发运费未签收',
							width : 180,
							align : 'right',
							dataIndex : 'custDrtWoOrigRcvoNpod'
						}, {
							text : '02预收客户冲02应收始发运费已签收',
							width : 180,
							align : 'right',
							dataIndex : 'custDrtWoOrigRcvtPod'
						}, {
							text : '01预收客户冲02应收始发运费已签收',
							width : 180,
							align : 'right',
							dataIndex : 'custDroWoOrigRcvtPod'
						}, {
							text : '02预收客户冲01应收始发运费已签收',
							width : 180,
							align : 'right',
							dataIndex : 'custDrtWoOrigRcvoPod'
						}, {
							text : '01始发退预收付款申请',
							width : 180,
							align : 'right',
							dataIndex : 'custDroPayApply'
						}, {
							text : '02始发退预收付款申请',
							width : 180,
							align : 'right',
							dataIndex : 'custDrtPayApply'
						} ]
					}, {
						text : '偏线代理成本',
						columns : [ {
							text : '应付偏线代理成本冲02应收到付运费已签收',
							width : 180,
							align : 'right',
							dataIndex : 'plCostWoDestRcvtPod'
						}, {
							text : '应付偏线代理成本冲02应收到付运费未签收',
							width : 180,
							align : 'right',
							dataIndex : 'plCostWoDestRcvtNpod'
						} ]
					}, {
						text : '预收偏线代理',
						columns : [ {
							text : '预收偏线代理冲02应收到付运费已签收',
							width : 180,
							align : 'right',
							dataIndex : 'plDrWoDestRcvtPod'
						}, {
							text : '预收偏线代理冲02应收到付运费未签收',
							width : 180,
							align : 'right',
							dataIndex : 'plDrWoDestRcvtNpod'
						} ]
					}, {
						text : '预收空运/快递代理',
						columns : [ {
							text : '预收空运/快递代理冲02应收到付运费已签收',
							width : 180,
							align : 'right',
							dataIndex : 'alDrWoDestRcvtPod'
						}, {
							text : '预收空运/快递代理冲02应收到付运费未签收',
							width : 180,
							align : 'right',
							dataIndex : 'alDrWoDestRcvtNpod'
						} ]
					}, {
						text : '空运/快递代理应付冲应收',
						columns : [ {
							text : '应付到达代理/快递代理成本冲02应收到付运费已签收',
							width : 180,
							align : 'right',
							dataIndex : 'alpwrWoDestRcvtPod'
						}, {
							text : '应付到达代理/快递代理成本冲02应收到付运费未签收',
							width : 180,
							align : 'right',
							dataIndex : 'alpwrWoDestRcvtNpod'
						}, {
							text : '其他应付冲02应收到付运费已签收',
							width : 180,
							align : 'right',
							dataIndex : 'othPayWoDestRcvtPod'
						}, {
							text : '其他应付冲02应收到付运费未签收',
							width : 180,
							align : 'right',
							dataIndex : 'othPayWoDestRcvtNpod'
						} ]
					}, {
						text : '偏线应收冲应付',
						columns : [ {
							text : '其他应付冲02应收到付运费已签收',
							width : 180,
							align : 'right',
							dataIndex : 'popWoDrtPod'
						}, {
							text : '其他应付冲02应收到付运费未签收',
							width : 180,
							align : 'right',
							dataIndex : 'popWoDrtNpod'
						} ]
					} ],
			constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);

				me.store = Ext.create('Foss.closing.mvrNrfostStore');

				me.bbar = me.getPagingToolbar();
				me.getPagingToolbar().store = me.store;
				me.callParent([cfg]);
			}
		});

// 显示
Ext.onReady(function() {
			Ext.QuickTips.init();
			Ext.Ajax.timeout = 600000;

			if (Ext.getCmp('T_closing-mvrNrfost_content')) {
				return;
			}

			closing.mvrNrfost.mvrNrfostTotalRecord = Ext.create('Foss.closing.mvrNrfostModel');
			// 查询FORM
			var queryForm = Ext.create('Foss.closing.mvrNrfostQueryForm');

			// 显示grid
			var queryGrid = Ext.create('Foss.closing.mvrNrfostQueryGrid');

			// 显示到JSP页面
			Ext.create('Ext.panel.Panel', {
						id : 'T_closing-mvrNrfost_content',
						cls : "panelContentNToolbar",
						bodyCls : 'panelContentNToolbar-body',
						layout : 'auto',
						// 获得查询FORM
						getQueryFrom : function() {
							return queryForm;
						},
						// 获得查询结果GRID
						getQueryGrid : function() {
							return queryGrid;
						},
						items : [queryForm, queryGrid],
						renderTo : 'T_closing-mvrNrfost-body'
					});
		});