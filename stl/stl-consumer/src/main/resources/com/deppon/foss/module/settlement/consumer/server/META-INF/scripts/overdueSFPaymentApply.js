/* 查询类别 */
consumer.overdueSFApply.QUERY_BY_ACCOUNT = 'TAD';
consumer.overdueSFApply.QUERY_BY_WAYBILL_NO = 'WB';
consumer.overdueSFApply.QUERY_BY_PAYABLE_NO = 'TP';

// 提交（apply）、审批（audit）、审批不同意（auditRejectedOnly）
consumer.overdueSFApply.AUDIT_TYPE_APPLY = 'apply';
consumer.overdueSFApply.AUDIT_TYPE_AUDIT = 'audit';
consumer.overdueSFApply.AUDIT_TYPE_AUDIT_REJECTED_ONLY = 'auditRejectedOnly';

// 审批状态数据字典名
consumer.overdueSFApply.BILL_PAYABLE__APPROVE_STATUS = 'OVERDUE_SF_AUDIT_STATUS';

/* 查询时间限制 */
consumer.overdueSFApply.MAX_RANGE_DAYS = 31;

/* 标记是否actionColumn触发 */
consumer.overdueSFApply.actionIndex = -1;
/*
 * 查询方法
 */
consumer.overdueSFApply.query = function(form, queryType) {
	/*
	 * var params = { 'overdueSFPaymentApplyVo.overdueSFPaymentApplyQueryDto.beginAccountDate' : null,
	 * 'overdueSFPaymentApplyVo.overdueSFPaymentApplyQueryDto.endAccountDate' : null, 'overdueSFPaymentApplyVo.overdueSFPaymentApplyQueryDto.status' :
	 * null, 'overdueSFPaymentApplyVo.overdueSFPaymentApplyQueryDto.bigArea' : null, 'overdueSFPaymentApplyVo.overdueSFPaymentApplyQueryDto.smallArea' :
	 * null, 'overdueSFPaymentApplyVo.overdueSFPaymentApplyQueryDto.department' : null,
	 * 'overdueSFPaymentApplyVo.overdueSFPaymentApplyQueryDto.consumer' : null, 'overdueSFPaymentApplyVo.overdueSFPaymentApplyQueryDto.queryType' :
	 * null, 'overdueSFPaymentApplyVo.overdueSFPaymentApplyQueryDto.active' : null, 'overdueSFPaymentApplyVo.overdueSFPaymentApplyQueryDto.empCode' :
	 * null, 'overdueSFPaymentApplyVo.overdueSFPaymentApplyQueryDto.waybillNos' : null,
	 * 'overdueSFPaymentApplyVo.overdueSFPaymentApplyQueryDto.payableNos' : null,
	 * 'overdueSFPaymentApplyVo.overdueSFPaymentApplyQueryDto.waybillNoList' : null,
	 * 'overdueSFPaymentApplyVo.overdueSFPaymentApplyQueryDto.payableNoList' : null, 'overdueSFPaymentApplyVo.overdueSFPaymentApplyQueryDto.depts' :
	 * null, 'overdueSFPaymentApplyVo.overdueSFPaymentApplyQueryDto.start' : null, 'overdueSFPaymentApplyVo.overdueSFPaymentApplyQueryDto.limit' :
	 * null };
	 */

	if (!form.isValid()) {
		Ext.Msg.alert(consumer.overdueSFApply.i18n('foss.stl.consumer.common.warmTips'), consumer.overdueSFApply
				.i18n('foss.stl.consumer.overdueSFApply.noInput'));
		return false;
	}
	var params = form.getValues();
	if (queryType == consumer.overdueSFApply.QUERY_BY_WAYBILL_NO) {
		var waybillNos = stl.selectWaybillNosArray(params['overdueSFPaymentApplyVo.overdueSFPaymentApplyQueryDto.waybillNos']);
		if (waybillNos == null || waybillNos.length == 0) {
			Ext.Msg.alert(consumer.overdueSFApply.i18n('foss.stl.consumer.common.warmTips'), consumer.overdueSFApply
					.i18n('foss.stl.consumer.overdueSFApply.waybillNosNotFound'));
			return false;
		} else if (waybillNos != null && waybillNos.length > 10) {
			Ext.Msg.alert(consumer.overdueSFApply.i18n('foss.stl.consumer.common.warmTips'), consumer.overdueSFApply
					.i18n('foss.stl.consumer.overdueSFApply.MoreThanTenNoFound'));
			return false;
		} else {
			Ext.apply(params, {
					'overdueSFPaymentApplyVo.overdueSFPaymentApplyQueryDto.waybillNos' : waybillNos
				});
		}
	} else if (queryType == consumer.overdueSFApply.QUERY_BY_PAYABLE_NO) {
		var payableNos = stl.selectPayableNosArray(params['overdueSFPaymentApplyVo.overdueSFPaymentApplyQueryDto.payableNos']);
		if (payableNos == null || payableNos.length == 0) {
			Ext.Msg.alert(consumer.overdueSFApply.i18n('foss.stl.consumer.common.warmTips'), consumer.overdueSFApply
					.i18n('foss.stl.consumer.overdueSFApply.payableNosNotFound'));
			return false;
		} else if (payableNos != null && payableNos.length > 10) {
			Ext.Msg.alert(consumer.overdueSFApply.i18n('foss.stl.consumer.common.warmTips'), consumer.overdueSFApply
					.i18n('foss.stl.consumer.overdueSFApply.MoreThanTenNoFound'));
			return false;
		} else {
			Ext.apply(params, {
					'overdueSFPaymentApplyVo.overdueSFPaymentApplyQueryDto.payableNos' : payableNos
				});
		}
	}

	// 处理部门信息
	if (params['overdueSFPaymentApplyVo.overdueSFPaymentApplyQueryDto.department'] == stl.currentDept.name) {
		Ext.apply(params, {
				'overdueSFPaymentApplyVo.overdueSFPaymentApplyQueryDto.department' : stl.currentDeptCode
			});
	}
	// 查询方式
	Ext.apply(params, {
			'overdueSFPaymentApplyVo.overdueSFPaymentApplyQueryDto.queryType' : queryType
		});

	// 校验
	if (!consumer.overdueSFApply.validateParams(params, queryType)) {
		return false;
	}
	var grid = Ext.getCmp('T_consumer-overdueSFPaymentApply_content').items.getAt(1);
	var store = grid.getStore();
	// grid.removeAll();
	store.setSubmitParams(params);
	var totalText = grid.getDockedItems()[2].child('tbtext');
	store.loadPage(1, {
			callback : function(records, operation, success) {

				// 抛出异常
				var rawData = grid.store.proxy.reader.rawData;
				if (!success && !rawData.isException) {
					Ext.Msg.alert(consumer.overdueSFApply.i18n('foss.stl.consumer.common.warmTips'), rawData.message);
					return false;
				}

				// 正常返回
				if (success) {
					var result = Ext.decode(operation.response.responseText);

					totalText.setText(consumer.overdueSFApply.i18n('foss.stl.consumer.overdueSFApply.gridPanel.totalCount')
						+ result.overdueSFPaymentApplyVo.overdueSFPaymentApplyResultDto.totalCount);
					if (result.overdueSFPaymentApplyVo.overdueSFPaymentApplyResultDto.overdueSFPaymentApplyDtos.length > 0) {
						grid.show();
					} else {
						Ext.Msg.alert(consumer.overdueSFApply.i18n('foss.stl.consumer.common.warmTips'), consumer.overdueSFApply
								.i18n('foss.stl.comsumer.overdueSFApply.noDataFound'));
						return false;
					}
				}
			}
		});
}

/*
 * 校验查询参数
 */
consumer.overdueSFApply.validateParams = function(params, queryType) {
	if (queryType == null || queryType == '') {
		Ext.Msg.alert(consumer.overdueSFApply.i18n('foss.stl.consumer.common.warmTips'), consumer.overdueSFApply
				.i18n('foss.stl.consumer.overdueSFApply.jsError'));
		return false;
	}

	if (queryType == consumer.overdueSFApply.QUERY_BY_ACCOUNT) {
		if (!(params['overdueSFPaymentApplyVo.overdueSFPaymentApplyQueryDto.beginAccountDate'] || params['overdueSFPaymentApplyVo.overdueSFPaymentApplyQueryDto.endAccountDate'])) {
			Ext.Msg.alert(consumer.overdueSFApply.i18n('foss.stl.consumer.common.warmTips'), consumer.overdueSFApply
					.i18n('foss.stl.consumer.overdueSFApply.noDateInpput'));
			return false;
		}
		if (params['overdueSFPaymentApplyVo.overdueSFPaymentApplyQueryDto.beginAccountDate'] > params['overdueSFPaymentApplyVo.overdueSFPaymentApplyQueryDto.endAccountDate']) {
			Ext.Msg.alert(consumer.overdueSFApply.i18n('foss.stl.consumer.common.warmTips'), consumer.overdueSFApply
					.i18n('foss.stl.consumer.overdueSFApply.beginDateGreaterThanEndDate'));
			return false;
		}

		if (stl.compareTwoDate(params['overdueSFPaymentApplyVo.overdueSFPaymentApplyQueryDto.beginAccountDate'],
			params['overdueSFPaymentApplyVo.overdueSFPaymentApplyQueryDto.endAccountDate']) > consumer.overdueSFApply.MAX_RANGE_DAYS) {
			Ext.Msg.alert(consumer.overdueSFApply.i18n('foss.stl.consumer.common.warmTips'), consumer.overdueSFApply
					.i18n('foss.stl.consumer.overdueSFApply.dateRangeGreaterThanOneMonth1')
					+ consumer.overdueSFApply.MAX_RANGE_DAYS
					+ consumer.overdueSFApply.i18n('foss.stl.consumer.overdueSFApply.dateRangeGreaterThanOneMonth2'));
			return false;
		}

		if (!(params['overdueSFPaymentApplyVo.overdueSFPaymentApplyQueryDto.bigArea']
			|| params['overdueSFPaymentApplyVo.overdueSFPaymentApplyQueryDto.smallArea'] || params['overdueSFPaymentApplyVo.overdueSFPaymentApplyQueryDto.department'])) {
			Ext.Msg.alert(consumer.overdueSFApply.i18n('foss.stl.consumer.common.warmTips'), consumer.overdueSFApply
					.i18n('foss.stl.consumer.overdueSFApply.noAreaOrDept'));
			return false;
		}

	} else if (queryType == consumer.overdueSFApply.QUERY_BY_WAYBILL_NO) {
		if (!params['overdueSFPaymentApplyVo.overdueSFPaymentApplyQueryDto.waybillNos']) {
			Ext.Msg.alert(consumer.overdueSFApply.i18n('foss.stl.consumer.common.warmTips'), consumer.overdueSFApply
					.i18n('foss.stl.consumer.overdueSFApply.noWaybillNos'));
			return false;
		}
	} else if (queryType == consumer.overdueSFApply.QUERY_BY_PAYABLE_NO) {
		if (!params['overdueSFPaymentApplyVo.overdueSFPaymentApplyQueryDto.payableNos']) {
			Ext.Msg.alert(consumer.overdueSFApply.i18n('foss.stl.consumer.common.warmTips'), consumer.overdueSFApply
					.i18n('foss.stl.consumer.overdueSFApply.noPayableNos'));
			return false;
		}
	}
	return true;
}

consumer.overdueSFApply.initPanel = function(formPanel) {
	var form = formPanel.getForm();
	form.reset();
	if (formPanel.showType == consumer.overdueSFApply.AUDIT_TYPE_APPLY) {
		formPanel.getComponent('status').hide();
		form.findField('overdueSFPaymentApplyVo.overdueSFPaymentApplyQueryDto.reason').show();
		form.findField('overdueSFPaymentApplyVo.overdueSFPaymentApplyQueryDto.notes').hide();
		formPanel.child('header').setTitle(consumer.overdueSFApply.i18n('foss.stl.consumer.overdueSFApply.applyAuditPanel.apply'));
	}
	if (formPanel.showType == consumer.overdueSFApply.AUDIT_TYPE_AUDIT) {
		formPanel.getComponent('status').show();
		formPanel.getComponent('status').getComponent('passed').show();
		formPanel.getComponent('status').getComponent('passed').setValue(true);
		var notes = formPanel.getComponent('notes');
		Ext.apply(notes, {
				allowBlank : true
			});
		notes.doComponentLayout();
		form.findField('overdueSFPaymentApplyVo.overdueSFPaymentApplyQueryDto.reason').hide();
		form.findField('overdueSFPaymentApplyVo.overdueSFPaymentApplyQueryDto.notes').show();
		formPanel.child('header').setTitle(consumer.overdueSFApply.i18n('foss.stl.consumer.overdueSFApply.applyAuditPanel.audit'));
	}
	if (formPanel.showType == consumer.overdueSFApply.AUDIT_TYPE_AUDIT_REJECTED_ONLY) {
		formPanel.getComponent('status').show();
		formPanel.getComponent('status').getComponent('passed').hide();
		formPanel.getComponent('status').getComponent('rejected').setValue(true);
		var notes = formPanel.getComponent('notes');
		Ext.apply(notes, {
				allowBlank : false
			});
		notes.doComponentLayout();
		form.findField('overdueSFPaymentApplyVo.overdueSFPaymentApplyQueryDto.reason').hide();
		form.findField('overdueSFPaymentApplyVo.overdueSFPaymentApplyQueryDto.notes').show();
		formPanel.child('header').setTitle(consumer.overdueSFApply.i18n('foss.stl.consumer.overdueSFApply.applyAuditPanel.audit'));
	}
}

// 提交（apply）、审批（audit）、审批不同意（auditRejectedOnly）弹出窗
Ext.define('consumer.overdueSFApply.applyAuditPanel', {
	extend : 'Ext.form.Panel',
	frameHeader : true,
	frame : true,
	cls : 'innerTabPanel',
	// draggable : true,
	closable : true,
	closeAction : 'hide',
	cls : 'innerTabPanel',
	renderTo : 'T_consumer-overdueSFPaymentApply-body',
	hidden : true,
	floatable : true,
	floating : true,
	toFrontOnShow : true,
	title : consumer.overdueSFApply.i18n('foss.stl.consumer.overdueSFApply.applyAuditPanel.apply'),
	showType : '',
	layout : 'vbox',
	setShowType : function(showType) {
		this.showType = showType;
	},
	records : '',
	setRecords : function(records) {
		this.records = records;
	},
	initComponent : function() {
		var me = this;
		Ext.applyIf(me, {
			items : [{
					xtype : 'textarea',
					height : 100,
					width : 520,
					maxLength : 300,
					padding : 5,
					maxLengthText : consumer.overdueSFApply.i18n('foss.stl.consumer.overdueSFApply.applyAuditPanel.tooMuchWords'),
					name : 'overdueSFPaymentApplyVo.overdueSFPaymentApplyQueryDto.reason',
					fieldLabel : consumer.overdueSFApply.i18n('foss.stl.consumer.overdueSFApply.applyAuditPanel.reason'),
					allowBlank : false
				}, {
					xtype : 'fieldcontainer',
					fieldLabel : consumer.overdueSFApply.i18n('foss.stl.consumer.overdueSFApply.applyAuditPanel.auditOption'),
					defaultType : 'radiofield',
					itemId : 'status',
					defaults : {
						width : 80
					},
					padding : 5,
					layout : 'hbox',
					items : [{
							boxLabel : consumer.overdueSFApply.i18n('foss.stl.consumer.overdueSFApply.applyAuditPanel.passed'),
							name : 'overdueSFPaymentApplyVo.overdueSFPaymentApplyQueryDto.status',
							inputValue : consumer.overdueSFApply.OVERDUE_SERVICE_FEE_AUDIT_STATUS_PASSED,
							itemId : 'passed',
							listeners : {
								change : function(me, newValue, oldValue, eOpts) {
									if (newValue) {
										var notes = me.up('form').getComponent('notes');
										Ext.apply(notes, {
												allowBlank : true
											});
										notes.doComponentLayout();
									} else {
										var notes = me.up('form').getComponent('notes');
										Ext.apply(notes, {
												allowBlank : false
											});
										notes.doComponentLayout();
									}
								}
							}
						}, {
							boxLabel : consumer.overdueSFApply.i18n('foss.stl.consumer.overdueSFApply.applyAuditPanel.rejected'),
							name : 'overdueSFPaymentApplyVo.overdueSFPaymentApplyQueryDto.status',
							inputValue : consumer.overdueSFApply.OVERDUE_SERVICE_FEE_AUDIT_STATUS_REJECTED,
							itemId : 'rejected'
						}]
				}, {
					xtype : 'textarea',
					itemId : 'notes',
					height : 100,
					width : 520,
					padding : 5,
					maxLength : 300,
					maxLengthText : consumer.overdueSFApply.i18n('foss.stl.consumer.overdueSFApply.applyAuditPanel.tooMuchWords'),
					name : 'overdueSFPaymentApplyVo.overdueSFPaymentApplyQueryDto.notes',
					fieldLabel : consumer.overdueSFApply.i18n('foss.stl.consumer.overdueSFApply.applyAuditPanel.notes'),
					allowBlank : false
				}],
			bbar : ['->', {
				text : consumer.overdueSFApply.i18n('foss.stl.consumer.common.commit'),
				handler : function() {
					var me = this;
					me.disable();
					var grid = Ext.getCmp('T_consumer-overdueSFPaymentApply_content').items.getAt(1);
					var form = this.up('form');
					var selectedRecords = consumer.overdueSFApply.auditPanel.records;
					var params = form.getForm().getValues();
					var payableNos = new Array();
					for (i = 0; i < selectedRecords.length; i++) {
						payableNos.push(selectedRecords[i].data.payableNo);
					}
					Ext.apply(params, {
							'overdueSFPaymentApplyVo.overdueSFPaymentApplyQueryDto.payableNos' : payableNos
						});
					Ext.apply(params, {
							'overdueSFPaymentApplyVo.overdueSFPaymentApplyQueryDto.notes' : Ext.String
								.trim(params['overdueSFPaymentApplyVo.overdueSFPaymentApplyQueryDto.notes'])
						});

					if (form.showType == consumer.overdueSFApply.AUDIT_TYPE_APPLY) {
						Ext.apply(params, {
							'overdueSFPaymentApplyVo.overdueSFPaymentApplyQueryDto.status' : consumer.overdueSFApply.OVERDUE_SERVICE_FEE_AUDIT_STATUS_PROCESSING
						});
					}
					if (form.showType == consumer.overdueSFApply.AUDIT_TYPE_APPLY) {
						if (params['overdueSFPaymentApplyVo.overdueSFPaymentApplyQueryDto.reason'].length > 300) {
							Ext.Msg.alert(consumer.overdueSFApply.i18n('foss.stl.consumer.common.warmTips'), consumer.overdueSFApply
									.i18n('foss.stl.consumer.overdueSFApply.applyAuditPanel.tooMuchWords'));
							me.enable();
							return false;
						}
					} else {
						if (params['overdueSFPaymentApplyVo.overdueSFPaymentApplyQueryDto.notes'].length > 300) {
							Ext.Msg.alert(consumer.overdueSFApply.i18n('foss.stl.consumer.common.warmTips'), consumer.overdueSFApply
									.i18n('foss.stl.consumer.overdueSFApply.applyAuditPanel.tooMuchWords'));
							me.enable();
							return false;
						}
					}

					Ext.Ajax.request({
						url : consumer.realPath('applyOrAuditOverdueSFApply.action'),
						actionMethods : 'post',
						params : params,
						callback : function(options, success, response) {
							me.enable();
							// 抛出异常
							var rawData = Ext.decode(response.responseText);
							if (!rawData.success) {
								Ext.Msg.alert(consumer.overdueSFApply.i18n('foss.stl.consumer.common.warmTips'), rawData.message);
								return false;
							}

							// 正常返回
							if (success) {
								Ext.Msg.alert(consumer.overdueSFApply.i18n('foss.stl.consumer.common.warmTips'), consumer.overdueSFApply
										.i18n('foss.stl.comsumer.overdueSFApply.success'));
								consumer.overdueSFApply.auditPanel.hide();
								var grid = Ext.getCmp('T_consumer-overdueSFPaymentApply_content').items.getAt(1);
								var store = grid.getStore();
								var totalText = grid.getDockedItems()[2].child('tbtext');
								store.loadPage(1, {
										callback : function(records, operation, success) {

											// 抛出异常
											var rawData = grid.store.proxy.reader.rawData;
											if (!success && !rawData.isException) {
												Ext.Msg.alert(consumer.overdueSFApply.i18n('foss.stl.consumer.common.warmTips'), rawData.message);
												return false;
											}

											// 正常返回
											if (success) {
												var result = Ext.decode(operation.response.responseText);

												totalText.setText(consumer.overdueSFApply
													.i18n('foss.stl.consumer.overdueSFApply.gridPanel.totalCount')
													+ result.overdueSFPaymentApplyVo.overdueSFPaymentApplyResultDto.totalCount);
												if (result.overdueSFPaymentApplyVo.overdueSFPaymentApplyResultDto.overdueSFPaymentApplyDtos.length > 0) {
													grid.show();
												} else {
													Ext.Msg.alert(consumer.overdueSFApply.i18n('foss.stl.consumer.common.warmTips'),
														consumer.overdueSFApply.i18n('foss.stl.comsumer.overdueSFApply.noDataFound'));
													return false;
												}
											}
										}
									});
								return false
							}
						}
					});
				}
			}, {
				xtype : 'component',
				width : 10
			}, {
				text : consumer.overdueSFApply.i18n('foss.stl.consumer.common.cancel'),
				handler : function() {
					this.up('form').hide();
				}
			}, {
				xtype : 'component',
				width : 10
			}],
			listeners : {
				show : function(me, optns) {
					consumer.overdueSFApply.initPanel(me, optns)
				},
				render : function(me, optns) {
					consumer.overdueSFApply.initPanel(me, optns)
				},
				hide : function(me, optns) {
					Ext.getCmp('T_consumer-overdueSFPaymentApply_content').unmask();
				}
			}
		});
		me.callParent(arguments);
	}
});
/*
 * 审批界面
 */
consumer.overdueSFApply.auditPanel = Ext.create('consumer.overdueSFApply.applyAuditPanel');

// 审批
consumer.overdueSFApply.audit = function(selectedRecords) {
	if (selectedRecords.length == 0) {
		Ext.Msg.alert(consumer.overdueSFApply.i18n('foss.stl.consumer.common.warmTips'), consumer.overdueSFApply
				.i18n('foss.stl.consumer.overdueSFApply.auditNoDataSelected'));
		return false;
	}
	consumer.overdueSFApply.auditPanel.setRecords(selectedRecords);
	for (i = 0; i < selectedRecords.length; i++) {
		e = selectedRecords[i];
		if (e.data.auditStatus != consumer.overdueSFApply.OVERDUE_SERVICE_FEE_AUDIT_STATUS_PASSED
			&& e.data.auditStatus != consumer.overdueSFApply.OVERDUE_SERVICE_FEE_AUDIT_STATUS_PROCESSING) {
			Ext.Msg.alert(consumer.overdueSFApply.i18n('foss.stl.consumer.common.warmTips'), consumer.overdueSFApply
					.i18n('foss.stl.consumer.overdueSFApply.auditStatusWrong'));
			return false;
		}

		if (e.data.auditStatus != selectedRecords[0].data.auditStatus) {
			Ext.Msg.alert(consumer.overdueSFApply.i18n('foss.stl.consumer.common.warmTips'), consumer.overdueSFApply
					.i18n('foss.stl.consumer.overdueSFApply.notTheSameAuditStatus'));
			return false;
		}
		if (e.data.customerCode != selectedRecords[0].data.customerCode) {
			Ext.Msg.alert(consumer.overdueSFApply.i18n('foss.stl.consumer.common.warmTips'), consumer.overdueSFApply
					.i18n('foss.stl.consumer.overdueSFApply.notTheSameCustcode'));
			return false;
		} else if (e.data.customerCode == '' || e.data.customerCode == null) {
			if (e.data.customerPhone != selectedRecords[0].data.customerPhone
				|| e.data.customerContactName != selectedRecords[0].data.customerContactName) {
				Ext.Msg.alert(consumer.overdueSFApply.i18n('foss.stl.consumer.common.warmTips'), consumer.overdueSFApply
						.i18n('foss.stl.consumer.overdueSFApply.notTheSameCustcode'));
				return false;
			}
		}
	}
	// 审批申请中状态的
	if (selectedRecords[0].data.auditStatus == consumer.overdueSFApply.OVERDUE_SERVICE_FEE_AUDIT_STATUS_PROCESSING) {
		consumer.overdueSFApply.auditPanel.setShowType(consumer.overdueSFApply.AUDIT_TYPE_AUDIT);
		Ext.getCmp('T_consumer-overdueSFPaymentApply_content').mask();
		consumer.overdueSFApply.auditPanel.show();
	}
	// 审批 审批同意状态的
	if (selectedRecords[0].data.auditStatus == consumer.overdueSFApply.OVERDUE_SERVICE_FEE_AUDIT_STATUS_PASSED) {
		consumer.overdueSFApply.auditPanel.setShowType(consumer.overdueSFApply.AUDIT_TYPE_AUDIT_REJECTED_ONLY);
		Ext.getCmp('T_consumer-overdueSFPaymentApply_content').mask();
		consumer.overdueSFApply.auditPanel.show();
	}
}
// 申请
consumer.overdueSFApply.apply = function(selectedRecords) {
	if (selectedRecords.length == 0) {
		Ext.Msg.alert(consumer.overdueSFApply.i18n('foss.stl.consumer.common.warmTips'), consumer.overdueSFApply
				.i18n('foss.stl.consumer.overdueSFApply.applyNoDataSelected'));
		return false;
	}
	consumer.overdueSFApply.auditPanel.setRecords(selectedRecords);
	var auditStatus = selectedRecords[0].data.auditStatus;
	for (i = 0; i < selectedRecords.length; i++) {
		e = selectedRecords[i];
		if (e.data.auditStatus != consumer.overdueSFApply.OVERDUE_SERVICE_FEE_AUDIT_STATUS_NOT_APPLY
			&& e.data.auditStatus != consumer.overdueSFApply.OVERDUE_SERVICE_FEE_AUDIT_STATUS_REJECTED) {
			Ext.Msg.alert(consumer.overdueSFApply.i18n('foss.stl.consumer.common.warmTips'), consumer.overdueSFApply
					.i18n('foss.stl.consumer.overdueSFApply.applyStatusWrong'));
			return false;
		}
		if (e.data.customerCode != selectedRecords[0].data.customerCode) {
			Ext.Msg.alert(consumer.overdueSFApply.i18n('foss.stl.consumer.common.warmTips'), consumer.overdueSFApply
					.i18n('foss.stl.consumer.overdueSFApply.notTheSameCustcode'));
			return false;
		} else if (e.data.customerCode == '' || e.data.customerCode == null) {
			if (e.data.customerPhone != selectedRecords[0].data.customerPhone || e.data.customerContactName != selectedRecords[0].data.customerContactName) {
				Ext.Msg.alert(consumer.overdueSFApply.i18n('foss.stl.consumer.common.warmTips'), consumer.overdueSFApply
						.i18n('foss.stl.consumer.overdueSFApply.notTheSameCustcode'));
				return false;
			}
		}
	}

	consumer.overdueSFApply.auditPanel.setShowType(consumer.overdueSFApply.AUDIT_TYPE_APPLY);
	Ext.getCmp('T_consumer-overdueSFPaymentApply_content').mask();
	consumer.overdueSFApply.auditPanel.show();
}

/*
 * 查询界面
 */
Ext.define('consumer.overdueSFApply.queryTabPanel', {
	extend : 'Ext.tab.Panel',
	requires : ['Ext.tab.Panel', 'Ext.tab.Tab', 'Ext.form.field.Date', 'Ext.form.field.ComboBox', 'Ext.form.field.TextArea'],
	frameHeader : false,
	frame : false,
	height : 213,
	cls : 'innerTabPanel',
	initComponent : function() {
		var me = this;
		Ext.applyIf(me, {
				items : [{
					title : consumer.overdueSFApply.i18n('foss.stl.consumer.overdueSFApply.queryByAccountDate'),
					tabConfig : {
						width : 120
					},
					layout : 'fit',
					items : [{
						itemId : consumer.overdueSFApply.QUERY_BY_ACCOUNT,
						xtype : 'form',
						layout : 'column',
						bodyPadding : '20px 5px 20px 5px',
						defaults : {
							columnWidth : .25
						},
						items : [{
								name : 'overdueSFPaymentApplyVo.overdueSFPaymentApplyQueryDto.beginAccountDate',
								xtype : 'datefield',
								fieldLabel : consumer.overdueSFApply.i18n('foss.stl.consumer.overdueSFApply.beginTime'),
								format : 'Y-m-d',
								editable : false,
								value : Ext.Date.add(new Date(), Ext.Date.DAY, 1 - consumer.overdueSFApply.MAX_RANGE_DAYS)
							}, {
								name : 'overdueSFPaymentApplyVo.overdueSFPaymentApplyQueryDto.endAccountDate',
								xtype : 'datefield',
								fieldLabel : consumer.overdueSFApply.i18n('foss.stl.consumer.overdueSFApply.endTime'),
								format : 'Y-m-d',
								value : new Date(),
								editable : false,
								maxValue : new Date()
							}, {
								name : 'overdueSFPaymentApplyVo.overdueSFPaymentApplyQueryDto.status',
								xtype : 'combo',
								editable : false,
								fieldLabel : consumer.overdueSFApply.i18n('foss.stl.consumer.overdueSFApply.status'),
								store : FossDataDictionary.getDataDictionaryStore(consumer.overdueSFApply.BILL_PAYABLE__APPROVE_STATUS, null, {
										'valueCode' : '',
										'valueName' : '全部'
									}),
								displayField : 'valueName',
								valueField : 'valueCode',
								value : ''
							}, {
								xtype : 'container',
								height : 24
							}, {
								xtype : 'linkagecomboselector',
								eventType : ['focus'],// 一般callparent包含focus事件，如果有callparent,则focus事件可不用传递
								itemId : 'Foss_baseinfo_BigRegion_ID',
								store : Ext.create('Foss.baseinfo.commonSelector.BigRegionStore'),// 从外面传入
								fieldLabel : consumer.overdueSFApply.i18n('foss.stl.consumer.overdueSFApply.bigArea'),
								value : '',
								minChars : 0,
								displayField : 'name',// 显示名称
								valueField : 'code',
								minChars : 0,
								queryParam : 'commonOrgVo.name',
								name : 'overdueSFPaymentApplyVo.overdueSFPaymentApplyQueryDto.bigArea',
								allowBlank : true,
								listWidth : 300,// 设置下拉框宽度
								isPaging : true
								// 分页
						}	, {
								xtype : 'linkagecomboselector',
								itemId : 'Foss_baseinfo_SmallRegion_ID',
								eventType : ['callparent'],// 一般callparent包含focus事件，如果有callparent,则focus事件可不用传递
								store : Ext.create('Foss.baseinfo.commonSelector.SmallRegionStore'),// 从外面传入
								name : 'overdueSFPaymentApplyVo.overdueSFPaymentApplyQueryDto.smallArea',
								fieldLabel : consumer.overdueSFApply.i18n('foss.stl.consumer.overdueSFApply.smallArea'),
								parentParamsAndItemIds : {
									'commonOrgVo.code' : 'Foss_baseinfo_BigRegion_ID'
								},// 此处城市不需要传入
								minChars : 0,
								displayField : 'name',// 显示名称
								valueField : 'code',
								minChars : 0,
								queryParam : 'commonOrgVo.name',
								allowBlank : true,
								listWidth : 300,// 设置下拉框宽度
								isPaging : true
							}, {
								xtype : 'dynamicorgcombselector',
								name : 'overdueSFPaymentApplyVo.overdueSFPaymentApplyQueryDto.department',
								fieldLabel : consumer.overdueSFApply.i18n('foss.stl.consumer.overdueSFApply.department'),
								allowblank : true,
								listWidth : 300,// 设置下拉框宽度
								isPaging : true,
								value : stl.currentDept.name
							}, {
								xtype : 'container',
								height : 24
							}, {
								xtype : 'commoncustomerselector',
								all : 'true',
								name : 'overdueSFPaymentApplyVo.overdueSFPaymentApplyQueryDto.consumer',
								fieldLabel : consumer.overdueSFApply.i18n('foss.stl.consumer.overdueSFApply.customer'),
								colspan : 1,
								singlePeopleFlag : 'Y',
								colspan : 1,
								allowBlank : true,
								listWidth : 300,// 设置下拉框宽度
								isPaging : true
								// 分页
						}	, {
								xtype : 'container',
								columnWidth : 0.75,
								height : 24
							}, {
								xtype : 'container',
								columnWidth : 1,
								height : 20
							}, {
								xtype : 'button',
								columnWidth : 0.1,
								text : consumer.overdueSFApply.i18n('foss.stl.consumer.common.reset'),
								handler : function() {
									form = this.up('form').getForm();
									form.reset();
									form.findField('overdueSFPaymentApplyVo.overdueSFPaymentApplyQueryDto.beginAccountDate').setValue(Ext.Date.add(
										new Date(), Ext.Date.DAY, 1 - consumer.overdueSFApply.MAX_RANGE_DAYS));
									form.findField('overdueSFPaymentApplyVo.overdueSFPaymentApplyQueryDto.endAccountDate').setValue(new Date());
									form.findField('overdueSFPaymentApplyVo.overdueSFPaymentApplyQueryDto.department').setValue(stl.currentDept.name);
								}
							}, {
								xtype : 'container',
								columnWidth : 0.55,
								height : 24
							}, {
								xtype : 'button',
								cls : 'yellow_button',
								columnWidth : 0.1,
								text : consumer.overdueSFApply.i18n('foss.stl.consumer.common.query'),
								handler : function() {
									consumer.overdueSFApply.query(this.up('form').getForm(), consumer.overdueSFApply.QUERY_BY_ACCOUNT);
								}
							}]
					}]
				}, {
					itemId : consumer.overdueSFApply.QUERY_BY_WAYBILL_NO,
					title : consumer.overdueSFApply.i18n('foss.stl.consumer.overdueSFApply.queryByWaybillNo'),
					tabConfig : {
						xtype : 'tab',
						width : 120
					},
					bodyPadding : 5,
					layout : 'fit',
					items : [{
							xtype : 'form',
							layout : 'column',
							bodyPadding : '20px 5px 20px 5px',
							items : [{
									xtype : 'textareafield',
									name : 'overdueSFPaymentApplyVo.overdueSFPaymentApplyQueryDto.waybillNos',
									columnWidth : 0.75,
									regex : /^[\s|\.]*(\d{8,}[\.\;\,\s]*)+\s*$/,
									emptyText : consumer.overdueSFApply.i18n('foss.stl.consumer.overdueSFApply.emptyText'),
									fieldLabel : consumer.overdueSFApply.i18n('foss.stl.consumer.overdueSFApply.waybillNos'),
									checkChangeBuffer : 70
								}, {
									xtype : 'container',
									columnWidth : 0.25,
									height : 67
								}, {
									xtype : 'container',
									columnWidth : 1,
									height : 20
								}, {
									xtype : 'button',
									columnWidth : 0.1,
									text : consumer.overdueSFApply.i18n('foss.stl.consumer.common.reset'),
									handler : function() {
										this.up('form').getForm().reset();
									}
								}, {
									xtype : 'container',
									columnWidth : 0.55,
									height : 24
								}, {
									xtype : 'button',
									columnWidth : 0.1,
									cls : 'yellow_button',
									text : consumer.overdueSFApply.i18n('foss.stl.consumer.common.query'),
									handler : function() {
										consumer.overdueSFApply.query(this.up('form').getForm(), consumer.overdueSFApply.QUERY_BY_WAYBILL_NO);
									}
								}]

						}]
				}, {
					itemId : consumer.overdueSFApply.QUERY_BY_PAYABLE_NO,
					title : consumer.overdueSFApply.i18n('foss.stl.consumer.overdueSFApply.queryByPayableNo'),
					tabConfig : {
						xtype : 'tab',
						width : 120
					},
					layout : 'fit',
					bodyPadding : 5,
					items : [{
							xtype : 'form',
							layout : 'column',
							bodyPadding : '20px 5px 20px 5px',
							items : [{
									xtype : 'textareafield',
									name : 'overdueSFPaymentApplyVo.overdueSFPaymentApplyQueryDto.payableNos',
									columnWidth : 0.75,
									regex : /^[\s|\.]*(YF\d{8,}[\.\;\,\s]*)+\s*$/,
									emptyText : consumer.overdueSFApply.i18n('foss.stl.consumer.overdueSFApply.emptyText'),
									fieldLabel : consumer.overdueSFApply.i18n('foss.stl.consumer.overdueSFApply.payableNos'),
									checkChangeBuffer : 70
								}, {
									xtype : 'container',
									columnWidth : 0.25,
									height : 67
								}, {
									xtype : 'container',
									columnWidth : 1,
									height : 20
								}, {
									xtype : 'button',
									columnWidth : 0.1,
									text : consumer.overdueSFApply.i18n('foss.stl.consumer.common.reset'),
									handler : function() {
										this.up('form').getForm().reset();
									}
								}, {
									xtype : 'container',
									columnWidth : 0.55,
									height : 24
								}, {
									xtype : 'button',
									columnWidth : 0.1,
									cls : 'yellow_button',
									text : consumer.overdueSFApply.i18n('foss.stl.consumer.common.query'),
									handler : function() {
										consumer.overdueSFApply.query(this.up('form').getForm(), consumer.overdueSFApply.QUERY_BY_PAYABLE_NO);
									}
								}]
						}]
				}]
			});
		me.callParent(arguments);
	}
});

Ext.define('consumer.overdueSFApply.Model', {
		extend : 'Ext.data.Model',
		fields : [{
				name : 'id'
			}, {
				name : 'payableNo'
			}, {
				name : 'payableOrgCode'
			}, {
				name : 'payableOrgName'
			}, {
				name : 'waybillNo'
			}, {
				name : 'accountDate',
				type : 'Date',
				convert : stl.longToDateConvert
			}, {
				name : 'amount'
			}, {
				name : 'applyOrgCode'
			}, {
				name : 'applyOrgName'
			}, {
				name : 'applyUserName'
			}, {
				name : 'auditOrgCode'
			}, {
				name : 'auditOrgName'
			}, {
				name : 'auditStatus'
			}, {
				name : 'auditUserName'
			}, {
				name : 'businessDate',
				type : 'Date',
				convert : stl.longToDateConvert
			}, {
				name : 'createTime',
				type : 'Date',
				convert : stl.longToDateConvert
			}, {
				name : 'customerCode'
			}, {
				name : 'customerContact'
			}, {
				name : 'customerContactName'
			}, {
				name : 'customerName'
			}, {
				name : 'customerPhone'
			}, {
				name : 'modifyTime',
				type : 'Date',
				convert : stl.longToDateConvert
			}, {
				name : 'notes'
			}, {
				name : 'reason'
			}, {
				name : 'active'
			}]
	});
Ext.define('consumer.overdueSFApply.store', {
		extend : 'Ext.data.Store',
		model : 'consumer.overdueSFApply.Model',
		sorters : {
			property : 'createTime',
			direction : 'DESC'
		},
		id : 'consumer.overdueSFApply.store_ID',
		proxy : {
			type : 'ajax',
			url : consumer.realPath('queryOverdueSFApply.action'),
			actionMethods : 'post',
			reader : {
				type : 'json',
				root : 'overdueSFPaymentApplyVo.overdueSFPaymentApplyResultDto.overdueSFPaymentApplyDtos',
				totalProperty : 'overdueSFPaymentApplyVo.overdueSFPaymentApplyResultDto.totalCount'
			}
		},
		pageSize : 20,
		submitParams : {},
		setSubmitParams : function(submitParams) {
			this.submitParams = submitParams;
		},
		listeners : {
			'beforeload' : function(store, operation, eOpts) {
				Ext.apply(this.submitParams, {
						start : operation.start,
						limit : operation.limit,
						page : operation.page
					});
				Ext.applyIf(operation, {
						params : this.submitParams
					});
			}
		}
	});

/*
 * 查询结果panel
 */
Ext.define('consumer.overdueSFApply.gridPanel', {
	extend : 'Ext.grid.Panel',
	title : consumer.overdueSFApply.i18n('foss.stl.consumer.overdueSFApply.info'),
	store : Ext.create('consumer.overdueSFApply.store'),
	cls : 'innerTabPanel',
	frame : true,
	selModel : Ext.create('Ext.selection.CheckboxModel', {
			model : 'MULTI'
		}),
	selType : 'rowmodel',
	height : 500,
	initComponent : function() {
		var me = this;
		Ext.applyIf(me, {
			columns : [{
				xtype : 'actioncolumn',
				header : consumer.overdueSFApply.i18n('foss.stl.consumer.common.actionColumn'),
				width : 70,
				align : 'center',
				items : [{
					iconCls : 'foss_icons_stl_sendmes',
					tooltip : consumer.overdueSFApply.i18n('foss.stl.consumer.overdueSFApply.applyAuditPanel.apply'),
					handler : function(grid, rowIndex, colIndex) {
						consumer.overdueSFApply.actionIndex = rowIndex;
						var selectedRecords = new Array();
						selectedRecords[0] = grid.getStore().getAt(rowIndex);
						consumer.overdueSFApply.apply(selectedRecords);
					},
					getClass : function(v, m, r, rowIndex) {
						if ((r.get('auditStatus') != consumer.overdueSFApply.OVERDUE_SERVICE_FEE_AUDIT_STATUS_NOT_APPLY && r.get('auditStatus') != consumer.overdueSFApply.OVERDUE_SERVICE_FEE_AUDIT_STATUS_REJECTED)
							|| !consumer.overdueSFApply.isPermission('/stl-web/#!consumer/applyOverdueSF.action')) {
							return 'statementBill_hide';
						} else {
							return 'foss_icons_stl_sendmes';
						}
					}
				}, {
					iconCls : 'foss_icons_stl_auditing',
					tooltip : consumer.overdueSFApply.i18n('foss.stl.consumer.overdueSFApply.applyAuditPanel.audit'),
					handler : function(grid, rowIndex, colIndex) {
						consumer.overdueSFApply.actionIndex = rowIndex;
						var selectedRecords = new Array();
						selectedRecords[0] = grid.getStore().getAt(rowIndex);
						consumer.overdueSFApply.audit(selectedRecords);
					},
					getClass : function(v, m, r, rowIndex) {
						if ((r.get('auditStatus') != consumer.overdueSFApply.OVERDUE_SERVICE_FEE_AUDIT_STATUS_PROCESSING && r.get('auditStatus') != consumer.overdueSFApply.OVERDUE_SERVICE_FEE_AUDIT_STATUS_PASSED)
							|| !consumer.overdueSFApply.isPermission('/stl-web/#!consumer/auditOverdueSF.action')) {
							return 'statementBill_hide';
						} else {
							return 'foss_icons_stl_auditing';
						}

					}
				}]
			}, {
				text : consumer.overdueSFApply.i18n('foss.stl.consumer.overdueSFApply.gridPanel.payableNo'),
				dataIndex : 'payableNo'
			}, {
				text : consumer.overdueSFApply.i18n('foss.stl.consumer.overdueSFApply.gridPanel.payableOrgCode'),
				dataIndex : 'payableOrgCode'
			}, {
				text : consumer.overdueSFApply.i18n('foss.stl.consumer.overdueSFApply.gridPanel.payableOrgName'),
				dataIndex : 'payableOrgName'
			}, {
				text : consumer.overdueSFApply.i18n('foss.stl.consumer.overdueSFApply.gridPanel.waybillNo'),
				dataIndex : 'waybillNo'
			}, {
				text : consumer.overdueSFApply.i18n('foss.stl.consumer.overdueSFApply.gridPanel.accountDate'),
				dataIndex : 'accountDate',
				xtype : 'datecolumn',
				format : 'Y-m-d H:i:s'
			}, {
				text : consumer.overdueSFApply.i18n('foss.stl.consumer.overdueSFApply.gridPanel.amount'),
				dataIndex : 'amount'
			}, {
				text : consumer.overdueSFApply.i18n('foss.stl.consumer.overdueSFApply.gridPanel.applyOrgCode'),
				dataIndex : 'applyOrgCode'
			}, {
				text : consumer.overdueSFApply.i18n('foss.stl.consumer.overdueSFApply.gridPanel.applyOrgName'),
				dataIndex : 'applyOrgName'
			}, {
				text : consumer.overdueSFApply.i18n('foss.stl.consumer.overdueSFApply.gridPanel.applyUserName'),
				dataIndex : 'applyUserName'
			}, {
				text : consumer.overdueSFApply.i18n('foss.stl.consumer.overdueSFApply.gridPanel.reason'),
				dataIndex : 'reason'
			}, {
				text : consumer.overdueSFApply.i18n('foss.stl.consumer.overdueSFApply.gridPanel.auditOrgCode'),
				dataIndex : 'auditOrgCode'
			}, {
				text : consumer.overdueSFApply.i18n('foss.stl.consumer.overdueSFApply.gridPanel.auditOrgName'),
				dataIndex : 'auditOrgName'
			}, {
				text : consumer.overdueSFApply.i18n('foss.stl.consumer.overdueSFApply.gridPanel.auditUserName'),
				dataIndex : 'auditUserName'
			}, {
				text : consumer.overdueSFApply.i18n('foss.stl.consumer.overdueSFApply.gridPanel.auditStatus'),
				dataIndex : 'auditStatus',
				renderer : function(value) {
					return FossDataDictionary.getDataByTermsCode(consumer.overdueSFApply.BILL_PAYABLE__APPROVE_STATUS, value)[0].valueName;
				}
			}, {
				text : consumer.overdueSFApply.i18n('foss.stl.consumer.overdueSFApply.gridPanel.businessDate'),
				dataIndex : 'businessDate',
				xtype : 'datecolumn',
				format : 'Y-m-d H:i:s'
			}, {
				text : consumer.overdueSFApply.i18n('foss.stl.consumer.overdueSFApply.gridPanel.createTime'),
				dataIndex : 'createTime',
				xtype : 'datecolumn',
				format : 'Y-m-d H:i:s'
			}, {
				text : consumer.overdueSFApply.i18n('foss.stl.consumer.overdueSFApply.gridPanel.customerCode'),
				dataIndex : 'customerCode'
			}, {
				text : consumer.overdueSFApply.i18n('foss.stl.consumer.overdueSFApply.gridPanel.customerName'),
				dataIndex : 'customerName'
			}, {
				text : consumer.overdueSFApply.i18n('foss.stl.consumer.overdueSFApply.gridPanel.customerPhone'),
				dataIndex : 'customerPhone'
			}, {
				text : consumer.overdueSFApply.i18n('foss.stl.consumer.overdueSFApply.gridPanel.customerContact'),
				dataIndex : 'customerContact',
				hidden : true
			}, {
				text : consumer.overdueSFApply.i18n('foss.stl.consumer.overdueSFApply.gridPanel.customerContactName'),
				dataIndex : 'customerContactName'
			}, {
				text : consumer.overdueSFApply.i18n('foss.stl.consumer.overdueSFApply.gridPanel.modifyTime'),
				dataIndex : 'modifyTime',
				xtype : 'datecolumn',
				format : 'Y-m-d H:i:s'
			}, {
				text : consumer.overdueSFApply.i18n('foss.stl.consumer.overdueSFApply.gridPanel.notes'),
				dataIndex : 'notes'
			}, {
				text : consumer.overdueSFApply.i18n('foss.stl.consumer.overdueSFApply.gridPanel.active'),
				dataIndex : 'active',
				hidden : true
			}, {
				text : consumer.overdueSFApply.i18n('foss.stl.consumer.overdueSFApply.gridPanel.id'),
				dataIndex : 'id',
				hidden : true
			}],
			bbar : [{
					xtype : 'component',
					width : 24
				}, {
					xtype : 'tbtext',
					text : consumer.overdueSFApply.i18n('foss.stl.consumer.overdueSFApply.gridPanel.totalCount'),
					width : 90
				}, '->', {
					width : 90,
					text : consumer.overdueSFApply.i18n('foss.stl.consumer.overdueSFApply.applyAuditPanel.apply'),
					disabled : !consumer.overdueSFApply.isPermission('/stl-web/#!consumer/applyOverdueSF.action'),
					hidden : !consumer.overdueSFApply.isPermission('/stl-web/#!consumer/applyOverdueSF.action'),
					handler : function() {
						consumer.overdueSFApply.actionIndex = -1;
						consumer.overdueSFApply.apply(this.up('grid').getSelectionModel().getSelection())
					}
				}, {
					xtype : 'component',
					width : 10
				}, {
					width : 90,
					text : consumer.overdueSFApply.i18n('foss.stl.consumer.overdueSFApply.applyAuditPanel.audit'),
					disabled : !consumer.overdueSFApply.isPermission('/stl-web/#!consumer/auditOverdueSF.action'),
					hidden : !consumer.overdueSFApply.isPermission('/stl-web/#!consumer/auditOverdueSF.action'),
					handler : function() {
						consumer.overdueSFApply.actionIndex = -1;
						consumer.overdueSFApply.audit(this.up('grid').getSelectionModel().getSelection())
					}
				}],
			fbar : [{
					xtype : 'component',
					width : 10
				}, {
					xtype : 'standardpaging',
					store : me.store,
					pageSize : 20,
					plugins : Ext.create('Deppon.ux.PageSizePlugin', {
							// 设置分页记录最大值，防止输入过大的数值
							maximumSize : 500
						})
				}, {
					xtype : 'component',
					flex : 1
				}]
		});
		me.callParent(arguments);
	}
});

Ext.onReady(function() {
		Ext.QuickTips.init();
		var mainPanel = Ext.create('Ext.panel.Panel', {
				id : 'T_consumer-overdueSFPaymentApply_content',
				cls : "panelContentNToolbar",
				bodyCls : 'panelContentNToolbar-body',
				border : false,
				layout : 'auto',
				items : [Ext.create('consumer.overdueSFApply.queryTabPanel'), Ext.create('consumer.overdueSFApply.gridPanel')],
				renderTo : 'T_consumer-overdueSFPaymentApply-body'
			});
	});
