/**
 * 查询日期限制
 */
pay.applyDisableRepayment.STLBILLREPAYMENT_MAX = 31; // 时间间隔最大不超过90天
pay.applyDisableRepayment.STLBILLREPAYMENT_ONEMONTH = 1; // 时间相差一月
pay.applyDisableRepayment.STLREVERSEWRITEOFF_ONEPAGESIZE = 50; // 每页显示的条数

// 按日期、按还款单查询
pay.applyDisableRepayment.queryRepayment_TD = 'TD';
pay.applyDisableRepayment.queryRepayment_RT = 'RT';

pay.applyDisableRepayment.roleOfJL = '00';
pay.applyDisableRepayment.roleOfKJ = '01';

pay.applyDisableRepayment.ty = 'A';
pay.applyDisableRepayment.th = 'B';


pay.applyDisableRepayment.dept = FossUserContext.getCurrentUserDept();// 当前登录部门
pay.applyDisableRepayment.user = FossUserContext.getCurrentUser();// 当前登录用户
pay.applyDisableRepayment.roles = FossUserContext.getCurrentUserRoleCodes();

// 格式化金额
pay.applyDisableRepayment.convertAmount = function setMoneyFormat(val) {
	return "" + Ext.util.Format.currency(val, ' ', 2, false) + "";
}

/**
 * 提示信息
 * 
 * @param {}
 *            message
 * @param {}
 *            yesFn
 * @param {}
 *            noFn
 */
pay.billRepaymentConfirmAlert = function(message, yesFn, noFn) {
	Ext.Msg.confirm(pay.applyDisableRepayment.i18n('foss.stl.pay.common.alert'), message, function(o) {
				if (o == 'yes') {
					yesFn();
				} else {
					noFn();
				}
			});
};

// 查询Form 重置
pay.applyDisableRepayment.reset = function() {
	this.up('form').getForm().reset();
	var form = this.up('form').getForm().reset();
	var orgNameSeletor = form.findField('orgName');
	if (!Ext.isEmpty(stl.currentDept.code)) {

		var displayText = stl.currentDept.name;
		var valueText = stl.currentDept.code;

		var store = orgNameSeletor.store;
		var key = orgNameSeletor.displayField + '';
		var value = orgNameSeletor.valueField + '';

		var m = Ext.create(store.model.modelName);
		m.set(key, displayText);
		m.set(value, valueText);

		store.loadRecords([m]);
		orgNameSeletor.setValue(valueText);
	}
}

// 还款单查询
pay.applyDisableRepayment.queryByRepayment = function(me, btn, queryType) {
	
	var form = me.getForm();
	if (form.isValid()) {
		var gridRepaymentGrid = Ext.getCmp('T_pay-applyDisableRepayment_content').getQueryGrid();
		var params = pay.applyDisableRepayment.queryByRepaymentSetParam(form, queryType);

		if (null == params || params == false) {
			return;
		}
		// 设置查询参数
		gridRepaymentGrid.store.setSubmitParams(params);
		// 设置该按钮灰掉
		btn.disable(false);
		// 30秒后自动解除灰掉效果
		setTimeout(function() {
					btn.enable(true);
				}, 30000);
		// 查询后台
		gridRepaymentGrid.store.loadPage(1, {
			callback : function(records, operation, success) {
				var rawData = gridRepaymentGrid.store.proxy.reader.rawData;
				// 当success:false ,isException:false --业务异常
				if (!success && !rawData.isException) {
					Ext.Msg.alert(pay.applyDisableRepayment.i18n('foss.stl.pay.common.alert'), rawData.message);
					btn.enable(true);
					return false;
				}
				if (success) {
					var result = Ext.decode(operation.response.responseText);
					toolBar = gridRepaymentGrid.getDockedItems('toolbar[dock="bottom"]')[0];
					// toolBar = gridRepaymentGrid.down('toolbar');
					var resultDto = result.billRepaymentDisableResultVo.billRepaymentDisableResultDto;
					var totalRepayAmount = pay.applyDisableRepayment.convertAmount(resultDto.totalRepayAmount);
					var totalApplyAmount = pay.applyDisableRepayment.convertAmount(resultDto.totalApplyAmount);

					toolBar.getComponent(1).setValue(resultDto.totalCount);
					toolBar.getComponent(2).setValue(totalRepayAmount);
					toolBar.getComponent(3).setValue(totalApplyAmount);

					var billRepaymentLength = 0;
					if (!Ext.isEmpty(resultDto.applys)) {
						billRepaymentLength = resultDto.applys;
					}
					// 判断是否有数据提示
					if (billRepaymentLength == 0) {
						Ext.Msg.alert(pay.applyDisableRepayment.i18n('foss.stl.pay.common.alert'), pay.applyDisableRepayment
										.i18n('foss.stl.pay.disableBillRepayment.queryByApplyIsNullRegexText'), function() {
									btn.enable(true);
									return false;
								});
					} else {
						gridRepaymentGrid.show();
					}
					btn.enable(true);
				}
			}
		});
	} else {
		Ext.Msg.alert(pay.applyDisableRepayment.i18n('foss.stl.pay.common.alert'), pay.applyDisableRepayment
						.i18n('foss.stl.pay.common.validateFailAlert'));
	}
}

// 设置还款单的查询参数
pay.applyDisableRepayment.queryByRepaymentSetParam = function(form, queryType) {
	// 定义查询参数
	var params = {};

	// 按照日期查询
	if (pay.applyDisableRepayment.queryRepayment_TD == queryType) {

		// 部门名称、大区名称、小区名称

		var collectionOrgCode = form.findField('orgName').getValue();
		var largeArea = form.findField('largeAreaName').getValue();
		var smallArea = form.findField('smallAreaName').getValue();

		// 校验日期是否正确
		var applyDateStart = form.findField('startDate').getValue();
		var applyDateEnd = form.findField('endDate').getValue();
		// 比较起始日期和结束日期
		var compareTwoDate = stl.compareTwoDate(applyDateStart, applyDateEnd);
		if (compareTwoDate > pay.applyDisableRepayment.STLBILLREPAYMENT_MAX) {
			Ext.Msg.alert(pay.applyDisableRepayment.i18n('foss.stl.pay.common.alert'), pay.applyDisableRepayment
							.i18n('foss.stl.pay.disableBillRepayment.queryDateMaxLimit'));
			return false;
		} else if (compareTwoDate < 1) {
			Ext.Msg.alert(pay.applyDisableRepayment.i18n('foss.stl.pay.common.alert'), pay.applyDisableRepayment
							.i18n('foss.stl.pay.common.dateEndBeforeStatrtWarining'));
			return false;
		}
		var role;
		
		if(pay.applyDisableRepayment.isPermission('/stl-web/pay/approveRepayApplyByJL.action') &&
			pay.applyDisableRepayment.isPermission('/stl-web/pay/approveRepayApplyByKJ.action')) {
			//Ext.Msg.alert(pay.applyDisableRepayment.i18n('foss.stl.pay.common.alert'), pay.applyDisableRepayment.i18n('foss.stl.pay.disableBillRepayment.morerole'));
			return false;
		}
		
		if (pay.applyDisableRepayment.isPermission('/stl-web/pay/approveRepayApplyByJL.action')) {
			role = pay.applyDisableRepayment.roleOfJL;
		}
		/*
		 * 如果为会计控制查询条件，大区、小区和部门必须填写一项
		 */
		if (pay.applyDisableRepayment.isPermission('/stl-web/pay/approveRepayApplyByKJ.action')) {
			if (!(!!form.findField('largeAreaName').getValue() || !!form.findField('smallAreaName').getValue() || !!form
					.findField('orgName').getValue())) {
				Ext.Msg.alert(pay.applyDisableRepayment.i18n('foss.stl.pay.common.alert'), pay.applyDisableRepayment
								.i18n('foss.stl.pay.common.validateRegionFailAlert'));
				return false;
			}
			role = pay.applyDisableRepayment.roleOfKJ;
		}
		

		// 按日期查询时，提交参数
		Ext.apply(params, {
					'billRepaymentDisableVo.billRepaymentDisableDto.applyDateStart' : applyDateStart,
					'billRepaymentDisableVo.billRepaymentDisableDto.applyDateEnd' : applyDateEnd,
					'billRepaymentDisableVo.billRepaymentDisableDto.applyOrgCode' : collectionOrgCode,
					'billRepaymentDisableVo.billRepaymentDisableDto.applyAreaCode' : largeArea,
					'billRepaymentDisableVo.billRepaymentDisableDto.applyParentOrgCode' : smallArea,
					'billRepaymentDisableVo.billRepaymentDisableDto.role' : role,
					'billRepaymentDisableVo.billRepaymentDisableDto.queryType' : queryType
				});

		// 按照还款单查询
	} else if (pay.applyDisableRepayment.queryRepayment_RT == queryType) {
		var role;
		
		if(pay.applyDisableRepayment.isPermission('/stl-web/pay/approveRepayApplyByJL.action') &&
			pay.applyDisableRepayment.isPermission('/stl-web/pay/approveRepayApplyByKJ.action')) {
//			Ext.Msg.alert(pay.applyDisableRepayment.i18n('foss.stl.pay.common.alert'), pay.applyDisableRepayment.i18n('foss.stl.pay.disableBillRepayment.morerole'));
			return false;
		}
		if (pay.applyDisableRepayment.isPermission('/stl-web/pay/approveRepayApplyByJL.action')) {
			role = pay.applyDisableRepayment.roleOfJL;
		}
		if (pay.applyDisableRepayment.isPermission('/stl-web/pay/approveRepayApplyByKJ.action')) {
			role = pay.applyDisableRepayment.roleOfKJ;
		}
		
		// 还款单号
		var repaymentNos = form.findField('repaymentNos').getValue();
		if (repaymentNos == null || repaymentNos == '') {
			Ext.Msg.alert(pay.applyDisableRepayment.i18n('foss.stl.pay.common.alert'), pay.applyDisableRepayment
							.i18n('foss.stl.pay.disableBillRepayment.queryRepaymentNoNotNull'));
			return false;
		}
		var billRepayNoArray = [];
		if (Ext.String.trim(repaymentNos) != null && Ext.String.trim(repaymentNos) != '') {
			array = stl.splitToArray(repaymentNos);
			for (var i = 0; i < array.length; i++) {
				if (Ext.String.trim(array[i]) != '') {
					billRepayNoArray.push(array[i]);
				}
			}
			if (billRepayNoArray.length > 10) {
				Ext.Msg.alert(pay.applyDisableRepayment.i18n('foss.stl.pay.common.alert'), pay.applyDisableRepayment
								.i18n('foss.stl.pay.common.queryNosLimit'));
				return false;
			}
		} 
		Ext.apply(params, {
					'billRepaymentDisableVo.billRepaymentDisableDto.repaymentNos' : billRepayNoArray,
					'billRepaymentDisableVo.billRepaymentDisableDto.queryType' : queryType,
					'billRepaymentDisableVo.billRepaymentDisableDto.role' : role
				});
	}
	return params;
}

// ------单调审批、退回、批量审批、退回------------
pay.applyDisableRepayment.singleApprove = function(grid, rowIndex, colIndex, type) {

	// 获取数据
	var selection = grid.getStore().getAt(rowIndex);
	// 获取操作类型
	var operateType = type;
	// 获取role
	var role;
	if(pay.applyDisableRepayment.isPermission('/stl-web/pay/approveRepayApplyByJL.action') &&
			pay.applyDisableRepayment.isPermission('/stl-web/pay/approveRepayApplyByKJ.action')) {
//			Ext.Msg.alert(pay.applyDisableRepayment.i18n('foss.stl.pay.common.alert'), pay.applyDisableRepayment
//							.i18n('foss.stl.pay.disableBillRepayment.morerole'));
			return false;
	}
	if(pay.applyDisableRepayment.isPermission('/stl-web/pay/approveRepayApplyByJL.action'))
	 {
	   role = pay.applyDisableRepayment.roleOfJL;
	 };
	if (pay.applyDisableRepayment.isPermission('/stl-web/pay/approveRepayApplyByKJ.action')) {
		role = pay.applyDisableRepayment.roleOfKJ;
	};

	var applyIDs = [];
	applyIDs.push(selection.data.id);

	var yesFn = function() {

		// 调用
		Ext.Ajax.request({
					url : pay.realPath('aprroveRepayApply.action'),
					params : {
						'billRepaymentDisableVo.billRepaymentDisableDto.applyIDs' : applyIDs,
						'billRepaymentDisableVo.billRepaymentDisableDto.role' : role,
						'billRepaymentDisableVo.billRepaymentDisableDto.operateType' : operateType
					},
					success : function(response) {
						// 获取查询还款单grid
						var queryGrid = Ext.getCmp('T_pay-applyDisableRepayment_content').getQueryGrid();
						// 查询后台
						queryGrid.store.loadPage(1, {
									callback : function(records, operation, success) {
										var rawData = queryGrid.store.proxy.reader.rawData;
										// 当success:false ,isException:false
										// --业务异常
										if (!success && !rawData.isException) {
											Ext.Msg
													.alert(pay.applyDisableRepayment.i18n('foss.stl.pay.common.alert'),
															rawData.message);
											return false;
										}
										if (success) {
											var result = Ext.decode(operation.response.responseText);
											toolBar = queryGrid.getDockedItems('toolbar[dock="bottom"]')[0];

											var resultDto = result.billRepaymentDisableResultVo.billRepaymentDisableResultDto;
											var totalRepayAmount = pay.applyDisableRepayment.convertAmount(resultDto.totalRepayAmount);
											var totalApplyAmount = pay.applyDisableRepayment.convertAmount(resultDto.totalApplyAmount);

											toolBar.getComponent(1).setValue(resultDto.totalCount);
											toolBar.getComponent(2).setValue(totalRepayAmount);
											toolBar.getComponent(3).setValue(totalApplyAmount);
										}
									}
								});
						// 查询后显示隐藏组件
						queryGrid.show();
						if (operateType == pay.applyDisableRepayment.ty) {
Ext.Msg.alert(pay.applyDisableRepayment.i18n('foss.stl.pay.common.alert'), pay.applyDisableRepayment
										.i18n('foss.stl.pay.disableBillRepayment.shenpiRepayApplySuccess'));	};
	if (operateType == pay.applyDisableRepayment.th) {
		Ext.Msg.alert(pay.applyDisableRepayment.i18n('foss.stl.pay.common.alert'), pay.applyDisableRepayment
										.i18n('foss.stl.pay.disableBillRepayment.backRepayApplySuccess'));
	};
					},
					exception : function(response) {
						var result = Ext.decode(response.responseText);
						Ext.Msg.alert(pay.applyDisableRepayment.i18n('foss.stl.pay.common.alert'), result.message);

						// 获取查询grid
						var queryGrid = Ext.getCmp('T_pay-applyDisableRepayment_content').getQueryGrid();
						// 查询后台
						queryGrid.store.loadPage(1, {
									callback : function(records, operation, success) {
										var rawData = queryGrid.store.proxy.reader.rawData;
										// 当success:false ,isException:false
										// --业务异常
										if (!success && !rawData.isException) {
											Ext.Msg
													.alert(pay.applyDisableRepayment.i18n('foss.stl.pay.common.alert'),
															rawData.message);
											return false;
										}
										if (success) {
											var result = Ext.decode(operation.response.responseText);
											toolBar = queryGrid.getDockedItems('toolbar[dock="bottom"]')[0];
											var resultDto = result.billRepaymentDisableResultVo.billRepaymentDisableResultDto;
											var totalRepayAmount = pay.applyDisableRepayment.convertAmount(resultDto.totalRepayAmount);
											var totalApplyAmount = pay.applyDisableRepayment.convertAmount(resultDto.totalApplyAmount);

											toolBar.getComponent(1).setValue(resultDto.totalCount);
											toolBar.getComponent(2).setValue(totalRepayAmount);
											toolBar.getComponent(3).setValue(totalApplyAmount);
										}
									}
								});
					}
				});
	};
	var noFn = function() {
		return false;
	};
	if (operateType == pay.applyDisableRepayment.ty) {
		pay.billRepaymentConfirmAlert(pay.applyDisableRepayment.i18n('foss.stl.pay.disableBillRepayment.isConfimshenpi'), yesFn, noFn);
	};
	if (operateType == pay.applyDisableRepayment.th) {
		pay.billRepaymentConfirmAlert(pay.applyDisableRepayment.i18n('foss.stl.pay.disableBillRepayment.isConfimback'), yesFn, noFn);
	};
}

// 批量审批还款单申请
pay.applyDisableRepayment.moreApprove = function(bu,type) {
	var me = bu;
	// 获取选中的还款单数据
	var selections = Ext.getCmp('T_pay-applyDisableRepayment_content').getQueryGrid().getSelectionModel().getSelection();

	// 如果未选中数据，提示至少选择一条记录进行操作
	if (selections.length == 0) {
		Ext.Msg.alert(pay.applyDisableRepayment.i18n('foss.stl.pay.common.alert'), pay.applyDisableRepayment
						.i18n('foss.stl.pay.disableBillRepayment.unSelectedOneAuditedRecordWarning'));
		return false;
	}

	// 获取操作类型
	var operateType = type;
	var selectionIds = [];
	for (var i = 0; i < selections.length; i++) {
		selectionIds.push(selections[i].get('id'));
	}

	// 获取角色传至后台进行不同到校验操作
	var role ;
	if(pay.applyDisableRepayment.isPermission('/stl-web/pay/approveRepayApplyByJL.action') &&
			pay.applyDisableRepayment.isPermission('/stl-web/pay/approveRepayApplyByKJ.action')) {
//			Ext.Msg.alert(pay.applyDisableRepayment.i18n('foss.stl.pay.common.alert'), pay.applyDisableRepayment
//							.i18n('foss.stl.pay.disableBillRepayment.morerole'));
			return false;
	}
	if(pay.applyDisableRepayment.isPermission('/stl-web/pay/approveRepayApplyByJL.action'))
	 {
	 	role = pay.applyDisableRepayment.roleOfJL;
	 }
	if (pay.applyDisableRepayment.isPermission('/stl-web/pay/approveRepayApplyByKJ.action')) {
		role = pay.applyDisableRepayment.roleOfKJ;
	}

	var yesFn = function() {

		me.disable(false);
		// 10秒后自动解除灰掉效果
		setTimeout(function() {
					me.enable(true);
				}, 10000);

		// 调用
		Ext.Ajax.request({
					url : pay.realPath('aprroveRepayApply.action'),
					params : {
						'billRepaymentDisableVo.billRepaymentDisableDto.applyIDs' : selectionIds,
						'billRepaymentDisableVo.billRepaymentDisableDto.role' : role,
						'billRepaymentDisableVo.billRepaymentDisableDto.operateType' : operateType
					},
					success : function(response) {
						// 获取查询还款单grid
						var queryGrid = Ext.getCmp('T_pay-applyDisableRepayment_content').getQueryGrid();
						// 查询后台
						queryGrid.store.loadPage(1, {
									callback : function(records, operation, success) {
										var rawData = queryGrid.store.proxy.reader.rawData;
										// 当success:false ,isException:false
										// --业务异常
										if (!success && !rawData.isException) {
											Ext.Msg
													.alert(pay.applyDisableRepayment.i18n('foss.stl.pay.common.alert'),
															rawData.message);
											return false;
										}
										if (success) {
											var result = Ext.decode(operation.response.responseText);
											toolBar = queryGrid.getDockedItems('toolbar[dock="bottom"]')[0];

											var resultDto = result.billRepaymentDisableResultVo.billRepaymentDisableResultDto;
											var totalRepayAmount = pay.applyDisableRepayment.convertAmount(resultDto.totalRepayAmount);
											var totalApplyAmount = pay.applyDisableRepayment.convertAmount(resultDto.totalApplyAmount);

											toolBar.getComponent(1).setValue(resultDto.totalCount);
											toolBar.getComponent(2).setValue(totalRepayAmount);
											toolBar.getComponent(3).setValue(totalApplyAmount);
										}
									}
								});
						// 后显示隐藏组件
						queryGrid.show();
							if (operateType == pay.applyDisableRepayment.ty) {
Ext.Msg.alert(pay.applyDisableRepayment.i18n('foss.stl.pay.common.alert'), pay.applyDisableRepayment
										.i18n('foss.stl.pay.disableBillRepayment.shenpiRepayApplySuccess'));	};
	if (operateType == pay.applyDisableRepayment.th) {
		Ext.Msg.alert(pay.applyDisableRepayment.i18n('foss.stl.pay.common.alert'), pay.applyDisableRepayment
										.i18n('foss.stl.pay.disableBillRepayment.backRepayApplySuccess'));
	};
						
					},
					exception : function(response) {
						var result = Ext.decode(response.responseText);
						Ext.Msg.alert(pay.applyDisableRepayment.i18n('foss.stl.pay.common.alert'), result.message);

						// 获取查询grid
						var queryGrid = Ext.getCmp('T_pay-applyDisableRepayment_content').getQueryGrid();
						// 查询后台
						queryGrid.store.loadPage(1, {
									callback : function(records, operation, success) {
										var rawData = queryGrid.store.proxy.reader.rawData;
										// 当success:false ,isException:false
										// --业务异常
										if (!success && !rawData.isException) {
											Ext.Msg.alert(pay.applyDisableRepayment.i18n('foss.stl.pay.common.alert'),
															rawData.message);
											return false;
										}
										if (success) {
											var result = Ext.decode(operation.response.responseText);
											toolBar = queryGrid.getDockedItems('toolbar[dock="bottom"]')[0];
											var resultDto = result.billRepaymentDisableResultVo.billRepaymentDisableResultDto;
											var totalRepayAmount = pay.applyDisableRepayment.convertAmount(resultDto.totalRepayAmount);
											var totalApplyAmount = pay.applyDisableRepayment.convertAmount(resultDto.totalApplyAmount);

											toolBar.getComponent(1).setValue(resultDto.totalCount);
											toolBar.getComponent(2).setValue(totalRepayAmount);
											toolBar.getComponent(3).setValue(totalApplyAmount);
										}
									}
								});
					}
				});
	};
	var noFn = function() {
		return false;
	};
	if (operateType == pay.applyDisableRepayment.ty) {
		pay.billRepaymentConfirmAlert(pay.applyDisableRepayment.i18n('foss.stl.pay.disableBillRepayment.isConfimshenpi'), yesFn, noFn);
	};
	if (operateType == pay.applyDisableRepayment.th) {
		pay.billRepaymentConfirmAlert(pay.applyDisableRepayment.i18n('foss.stl.pay.disableBillRepayment.isConfimback'), yesFn, noFn);
	};
}

pay.applyDisableRepayment.detailRepaymentApply = function(grid, rowIndex, colIndex) {
	var yesFn=function(){
		//获取选中的还款单数据
		var selection = grid.getStore().getAt(rowIndex);	
		
		//提交后台生产对账单回执，并返回回执数据
		Ext.Ajax.request({
			url:pay.realPath('applyDetailQuery.action'),
			params:{
				'billRepaymentDisableVo.billRepaymentDisableDto.applyId':selection.data.id
			},
			method:'post',
			success:function(response){
				var result = Ext.decode(response.responseText);	
				
				//获取弹出窗口
				win = Ext.getCmp('Foss_DisableRepaymentbill_WriteoffDetailWindow_ID');
				if(win==null ){
					win = Ext.create('Foss.DisableRepaymentbill.WriteoffDetailWindow');
				}
				var resultDto = result.billRepaymentDisableResultVo.billRepaymentDisableResultDto;
				//加载数据
				win.items.items[0].store.loadData(resultDto.billRepaymentDisableDto.details);
				
				toolBar = win.getWriteoffDetailGrid().getDockedItems('toolbar[dock="bottom"]')[0];
				//核销明细总条数
				toolBar.getComponent(0).setValue(resultDto.totalCount);
				//核销明细总金额
				toolBar.getComponent(2).setValue(pay.applyDisableRepayment.convertAmount(resultDto.totalVerifyAmount));
				win.show();
			},
			exception:function(response){
				var result = Ext.decode(response.responseText);	
				Ext.Msg.alert(pay.repayment.i18n('foss.stl.pay.common.alert'),result.message);
			}		
		});
	};
	var noFn=function(){
	 	return false;
	};
	pay.billRepaymentConfirmAlert(pay.applyDisableRepayment.i18n('foss.stl.pay.disableBillRepayment.isView'), yesFn, noFn);
};


// grid的model
Ext.define('Foss.repaymentApply.repaymentApplyModel', {
			extend : 'Ext.data.Model',
			idgen : 'uuid',// EXT在前台为每个模型额外以UUID方式生成主键
			idProperty : 'extid',// 以上生成的主键用在名叫“extid”的列
			fields : [{
						// 额外的用于生成的EXT使用的列
						name : 'extid'
					}, {
						name : 'id'
					}, {
						name : 'repaymentNo'
					}, {
						name : 'customerCode'
					}, {
						name : 'customerName'
					}, {
						name : 'applyParentOrgCode'
					}, {
						name : 'applyParentOrgName'
					}, {
						name : 'applyAreaCode'
					}, {
						name : 'applyAreaName'
					}, {
						name : 'applyOrgCode'
					}, {
						name : 'applyOrgName'
					}, {
						name : 'applyUserCode'
					}, {
						name : 'applyUserName'
					}, {
						name : 'applyTime',
						type : 'date',
						convert : stl.longToDateConvert
					}, {
						name : 'amount',
						type : 'long'
					}, {
						name : 'paymentType'
					}, {
						name : 'disableReason'
					}, {
						name : 'approveUserCode'
					}, {
						name : 'approveUserName'
					}, {
						name : 'approveOrgCode'
					}, {
						name : 'approveOrgName'
					}, {
						name : 'approveTime',
						type : 'date',
						convert : stl.longToDateConvert
					}, {
						name : 'createTime',
						type : 'date',
						convert : stl.longToDateConvert
					}, {
						name : 'modifyTime',
						type : 'date',
						convert : stl.longToDateConvert
					}, {
						name : 'approveStatus'
					}, {
						name : 'isAllDisable'
					}, {
						name : 'disableNote'
					}, {
						name : 'repaymentAmount',
						type : 'long'
					}, {
						name : 'disableNum'
					},{
						name : 'trueAmount',
						type : 'long'
					},{
						name : 'oaPaymentNo'
					}

			]
		});

// 查询store
Ext.define('Foss.repaymentApply.repaymentApplyStore', {
			extend : 'Ext.data.Store',
			pageSize : pay.applyDisableRepayment.STLREVERSEWRITEOFF_ONEPAGESIZE,
			model : 'Foss.repaymentApply.repaymentApplyModel',
			sorters : [{
						property : 'applyTime',
						direction : 'ASC'
					}],
			proxy : {
				type : 'ajax',
				url : pay.realPath('queryBillRepaymentApply.action'),
				actionMethods : 'post',
				reader : {
					type : 'json',
					root : 'billRepaymentDisableResultVo.billRepaymentDisableResultDto.applys',
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

// 按日期查询 Form
Ext.define('Foss.applyDisableRepayment.StlBillRepaymentQueryByDateFrom', {
			extend : 'Ext.form.Panel',
			frame : false,
			columnWidth : 1,
			height : 180,
			width : 700,
			defaults : {
				margin : '5 5 5 5',
				labelWidth : 70,
				labelSeparator : ':'
			},
			getQueryType : function() {
				return pay.applyDisableRepayment.queryRepayment_TD = 'TD';
			},
			defaultType : 'textfield',
			layout : 'column',
			items : [{
						xtype : 'container',
						height : 10,
						border : false,
						html : '&nbsp;',
						columnWidth : 1
					}, {
						xtype : 'datefield',
						columnWidth : 0.33,
						name : 'startDate',
						fieldLabel : pay.applyDisableRepayment.i18n('foss.stl.pay.disableBillRepayment.startDate'),
						value : stl.getTargetDate(new Date(), -7),
						format : 'Y-m-d',
						allowBlank : false
					}, {
						xtype : 'datefield',
						name : 'endDate',
						columnWidth : 0.33,
						fieldLabel : pay.applyDisableRepayment.i18n('foss.stl.pay.disableBillRepayment.endDate'),
						format : 'Y-m-d',
						value : new Date(),
						allowBlank : false,
						maxValue : new Date()
					}, {
						xtype : 'container',
						height : 10,
						border : false,
						html : '&nbsp;',
						columnWidth : .33
					},{
						xtype : 'container',
						height : 10,
						border : false,
						html : '&nbsp;',
						columnWidth : 1
					}, {
						xtype : 'linkagecomboselector',
						eventType : ['focus'],// 一般callparent包含focus事件，如果有callparent,则focus事件可不用传递
						itemId : 'Foss_baseinfo_BigRegion_ID',
						store : Ext.create('Foss.baseinfo.commonSelector.BigRegionStore'),// 从外面传入
						columnWidth : .33,
						fieldLabel : pay.applyDisableRepayment.i18n('foss.stl.pay.disableBillRepayment.largeRegionSearch'),
						name : 'largeAreaName',
						isPaging : true,
						allowBlank : true,
						value : '',
						minChars : 0,
						displayField : 'name',// 显示名称
						valueField : 'code',
						minChars : 0,
						queryParam : 'commonOrgVo.name'
					}, {
						xtype : 'linkagecomboselector',
						itemId : 'Foss_baseinfo_SmallRegion_ID',
						eventType : ['callparent'],// 一般callparent包含focus事件，如果有callparent,则focus事件可不用传递
						store : Ext.create('Foss.baseinfo.commonSelector.SmallRegionStore'),// 从外面传入
						columnWidth : .33,
						fieldLabel : pay.applyDisableRepayment.i18n('foss.stl.pay.disableBillRepayment.smallReginSearch'),
						name : 'smallAreaName',
						allowBlank : true,
						isPaging : true,
						parentParamsAndItemIds : {
							'commonOrgVo.code' : 'Foss_baseinfo_BigRegion_ID'
						},// 此处城市不需要传入
						minChars : 0,
						displayField : 'name',// 显示名称
						valueField : 'code',
						minChars : 0,
						queryParam : 'commonOrgVo.name'
					}, {
						xtype : 'linkagecomboselector',
						eventType : ['callparent'],
						store : Ext.create('Foss.baseinfo.commonSelector.SmallRegionStore'),// 从外面传入
						columnWidth : .33,
						fieldLabel : pay.applyDisableRepayment.i18n('foss.stl.pay.disableBillRepayment.repayOrgSearch'),
						name : 'orgName',
						allowBlank : true,
						isPaging : true,
						parentParamsAndItemIds : {
							'commonOrgVo.code' : 'Foss_baseinfo_SmallRegion_ID'
						},// 此处区域不需要传入
						minChars : 0,
						displayField : 'name',// 显示名称
						valueField : 'code',
						minChars : 0,
						queryParam : 'commonOrgVo.name'
					}, {
						xtype : 'container',
						height : 5,
						border : false,
						html : '&nbsp;',
						columnWidth : 1
					},	
					{
						border : 1,
						xtype : 'container',
						columnWidth : .99,
						defaultType : 'button',
						layout : 'column',
						items : [{
									text : pay.applyDisableRepayment.i18n('foss.stl.pay.common.reset'),
									columnWidth : .12,
									handler : pay.applyDisableRepayment.reset
								}, {
									xtype : 'container',
									border : false,
									columnWidth : .4,
									html : '&nbsp;'
								}, {
									text : pay.applyDisableRepayment.i18n('foss.stl.pay.common.query'),
									cls : 'yellow_button',
									columnWidth : .12,
									handler : function() {
										var form = this.up('form');
										var btn = this;
										pay.applyDisableRepayment.queryByRepayment(form, btn, pay.applyDisableRepayment.queryRepayment_TD);
									}
								}]
					}]

		});

// 按还款单号查询 Form
Ext.define('Foss.applyDisableRepayment.StlBillRepaymentQueryByRepayNosFrom', {
			extend : 'Ext.form.Panel',
			frame : false,
			columnWidth : 1,
			height : 180,
			tabConfig : {
				width : 120
			},
			layout : 'fit',
			getQueryType : function() {
				return pay.applyDisableRepayment.queryRepayment_RT = 'RT';
			},
			items : [{
				xtype : 'form',
				defaults : {
					margin : '5 5 5 5',
					labelWidth : 70
				},
				layout : 'column',
				items : [{
							xtype : 'container',
							border : false,
							columnWidth : 1,
							html : '&nbsp;'
						}, {
							xtype : 'textarea',
							autoScroll : true,
							regex : /^((HK)?[0-9]{7,10}[;,])*((HK)?[0-9]{7,10}[;,]?)$/i,
							regexText : pay.applyDisableRepayment.i18n('foss.stl.pay.disableBillRepayment.inputHKErrorWarning'),
							emptyText : pay.applyDisableRepayment.i18n('foss.stl.pay.disableBillRepayment.billNoEmptyText'),
							fieldLabel : pay.applyDisableRepayment.i18n('foss.stl.pay.disableBillRepayment.repaymentBillNo'),
							name : 'repaymentNos',
							height : 80,
							allowBlank : false,
							columnWidth : .45
						}, {
						border : 1,
						xtype : 'container',
						columnWidth : .99,
						defaultType : 'button',
						layout : 'column',
						items : [{
									text : pay.applyDisableRepayment.i18n('foss.stl.pay.common.reset'),
									columnWidth : .09,
									handler : pay.applyDisableRepayment.reset
								}, {
									xtype : 'container',
									border : false,
									columnWidth : .2,
									html : '&nbsp;'
								}, {
									text : pay.applyDisableRepayment.i18n('foss.stl.pay.common.query'),
									cls : 'yellow_button',
									columnWidth : .09,
									handler : function() {
										var form = this.up('form');
										var btn = this;
										pay.applyDisableRepayment.queryByRepayment(form, btn, pay.applyDisableRepayment.queryRepayment_RT);
									}
								}]
					}]
			}]
		});

// 查询tab
Ext.define('Foss.stlapplyDisableRepayment.StlDisableRepaymentQueryInfoTab', {
			extend : 'Ext.tab.Panel',
			frame : false,
			bodyCls : 'autoHeight',
			cls : 'innerTabPanel',
			activeTab : 0,
			items : [{
						title : pay.applyDisableRepayment.i18n('foss.stl.pay.disableBillRepayment.queryByRepaymentDate'),// 按日期查询
						tabConfig : {
							width : 120
						},
						width : '200',
						items : [Ext.create('Foss.applyDisableRepayment.StlBillRepaymentQueryByDateFrom')]
					}, {
						title : pay.applyDisableRepayment.i18n('foss.stl.pay.disableBillRepayment.queryByRepaymentBillNo'),// 按还款单号查询
						tabConfig : {
							width : 120
						},
						layout : 'fit',
						items : [Ext.create('Foss.applyDisableRepayment.StlBillRepaymentQueryByRepayNosFrom')]
					}]
		});

// 还款单Grid
Ext.define('Foss.stlapplyDisableRepayment.StlDisableRepaymentQueryInfoGrid', {
	extend : 'Ext.grid.Panel',
	title : pay.applyDisableRepayment.i18n('foss.stl.pay.disableBillRepayment.repaymentInfo'),
	emptyText : pay.applyDisableRepayment.i18n('foss.stl.pay.common.noResult'),
	frame : true,
	height : 500,
	// width : 1060,
	selModel : Ext.create('Ext.selection.CheckboxModel'),
	hidMeParams : null,
	setHidMeParams : function(params) {
		this.hidMeParams = params;
	},
	getHidMeParams : function() {
		return this.hidMeParams;
	},
	totalRepayAmount : null,// 还款单总金额
	getTotalRepayAmount : function() {
		var me = this;
		if (Ext.isEmpty(me.totalRepayAmount)) {
			me.totalRepayAmount = 0;
		}
		return me.totalRepayAmount;
	},
	totalApplyAmount : null,// 申请总金额
	getTotalApplyAmount : function() {
		var me = this;
		if (Ext.isEmpty(me.totalApplyAmount)) {
			me.totalApplyAmount = 0;
		}
		return me.totalAmount;
	},
	totalCount : null,// 总条数
	getTotalCount : function() {
		var me = this;
		if (Ext.isEmpty(me.totalCount)) {
			me.totalCount = 0;
		}
		return me.totalCount;
	},
	columns : [{
				xtype : 'actioncolumn',
				header : pay.applyDisableRepayment.i18n('foss.stl.pay.disableBillRepayment.actionColumn'),
				width : 73,
				align : 'center',
				items : [{
							iconCls : 'foss_icons_bse_applyUsable',
							tooltip : pay.applyDisableRepayment.i18n('foss.stl.pay.disableBillRepayment.shenpitongyi'),
							handler : function(grid, rowIndex, colIndex) {
								pay.applyDisableRepayment.singleApprove(grid, rowIndex, colIndex, pay.applyDisableRepayment.ty)
							}
						}, {
							iconCls : 'foss_icons_bse_applyDisabled',
							tooltip : pay.applyDisableRepayment.i18n('foss.stl.pay.disableBillRepayment.back'),
							handler : function(grid, rowIndex, colIndex) {
								pay.applyDisableRepayment.singleApprove(grid, rowIndex, colIndex, pay.applyDisableRepayment.th)
							}
						}, {
							iconCls : 'deppon_icons_showdetail',
							tooltip : pay.applyDisableRepayment.i18n('foss.stl.pay.disableBillRepayment.entry'),
							handler : function(grid, rowIndex, colIndex) {
								pay.applyDisableRepayment.detailRepaymentApply(grid, rowIndex, colIndex)
							}
						}]
			}, {
				header : pay.applyDisableRepayment.i18n('foss.stl.pay.disableBillRepayment.id'),
				dataIndex : 'id',
				hidden : true
			}
			,{
				header : pay.applyDisableRepayment.i18n('foss.stl.pay.disableBillRepayment.repaymentNo'),
				dataIndex : 'repaymentNo',
				hidden : false
			}
			,{
				header : pay.applyDisableRepayment.i18n('foss.stl.pay.disableBillRepayment.isAllDisable'),
				dataIndex : 'isAllDisable',
				hidden : false,
				renderer:function(value){
    		var displayField = FossDataDictionary. rendererSubmitToDisplay (value,settlementDict.FOSS_BOOLEAN);
    		return displayField;
    	}
			}, {
				header : pay.applyDisableRepayment.i18n('foss.stl.pay.disableBillRepayment.customerCode'),
				dataIndex : 'customerCode',
				hidden : false
			}, {
				header : pay.applyDisableRepayment.i18n('foss.stl.pay.disableBillRepayment.customerName'),
				dataIndex : 'customerName',
				hidden : false
			}, {
				header : pay.applyDisableRepayment.i18n('foss.stl.pay.disableBillRepayment.applyParentOrgCode'),
				dataIndex : 'applyParentOrgCode',
				hidden : false
			}, {
				header : pay.applyDisableRepayment.i18n('foss.stl.pay.disableBillRepayment.applyParentOrgName'),
				dataIndex : 'applyParentOrgName',
				hidden : false
			}, {
				header : pay.applyDisableRepayment.i18n('foss.stl.pay.disableBillRepayment.applyAreaCode'),
				dataIndex : 'applyAreaCode',
				hidden : false
			}, {
				header : pay.applyDisableRepayment.i18n('foss.stl.pay.disableBillRepayment.applyAreaName'),
				dataIndex : 'applyAreaName',
				hidden : false
			}, {
				header : pay.applyDisableRepayment.i18n('foss.stl.pay.disableBillRepayment.applyOrgCode'),
				dataIndex : 'applyOrgCode',
				hidden : false
			}, {
				header : pay.applyDisableRepayment.i18n('foss.stl.pay.disableBillRepayment.applyOrgName'),
				dataIndex : 'applyOrgName',
				hidden : false
			}, {
				header : pay.applyDisableRepayment.i18n('foss.stl.pay.disableBillRepayment.applyUserCode'),
				dataIndex : 'applyUserCode',
				hidden : false
			}, {
				header : pay.applyDisableRepayment.i18n('foss.stl.pay.disableBillRepayment.applyUserName'),
				dataIndex : 'applyUserName',
				hidden : false
			}, {
				header : pay.applyDisableRepayment.i18n('foss.stl.pay.disableBillRepayment.applyTime'),
				dataIndex : 'applyTime',
				hidden : false,
				renderer:function(value){
    				return stl.dateFormat(value,'Y-m-d H:i:s');
    			}
			}, {
				header : pay.applyDisableRepayment.i18n('foss.stl.pay.disableBillRepayment.amount'),
				dataIndex : 'amount',
				hidden : false
			}, {
				header : pay.applyDisableRepayment.i18n('foss.stl.pay.disableBillRepayment.paymentType'),
				dataIndex : 'paymentType',
				hidden : false,
				renderer:function(value){
		    		var displayField = FossDataDictionary. rendererSubmitToDisplay (value,settlementDict.SETTLEMENT__PAYMENT_TYPE);
		    		return displayField;
    			}
			}, {
				header : pay.applyDisableRepayment.i18n('foss.stl.pay.disableBillRepayment.disableReason'),
				dataIndex : 'disableReason',
				hidden : false
			}, {
				header : pay.applyDisableRepayment.i18n('foss.stl.pay.disableBillRepayment.approveUserCode'),
				dataIndex : 'approveUserCode',
				hidden : false
			}, {
				header : pay.applyDisableRepayment.i18n('foss.stl.pay.disableBillRepayment.approveUserName'),
				dataIndex : 'approveUserName',
				hidden : false
			}, {
				header : pay.applyDisableRepayment.i18n('foss.stl.pay.disableBillRepayment.approveOrgCode'),
				dataIndex : 'approveOrgCode',
				hidden : false
			}, {
				header : pay.applyDisableRepayment.i18n('foss.stl.pay.disableBillRepayment.approveOrgName'),
				dataIndex : 'approveOrgName',
				hidden : false
			}, {
				header : pay.applyDisableRepayment.i18n('foss.stl.pay.disableBillRepayment.approveTime'),
				dataIndex : 'approveTime',
				hidden : false,
				renderer:function(value){
    				return stl.dateFormat(value,'Y-m-d H:i:s');
    			}
			}, {
				header : pay.applyDisableRepayment.i18n('foss.stl.pay.disableBillRepayment.createTime'),
				dataIndex : 'createTime',
				hidden : false,
				renderer:function(value){
    		return stl.dateFormat(value,'Y-m-d H:i:s');
    	}
			}, {
				header : pay.applyDisableRepayment.i18n('foss.stl.pay.disableBillRepayment.modifyTime'),
				dataIndex : 'modifyTime',
				hidden : false,
				renderer:function(value){
    		return stl.dateFormat(value,'Y-m-d H:i:s');
    	}
			}, {
				header : pay.applyDisableRepayment.i18n('foss.stl.pay.disableBillRepayment.approveStatus'),
				dataIndex : 'approveStatus',
				hidden : false,
				renderer:function(value){
	    		if(value=='NA') {
				return pay.applyDisableRepayment.i18n('foss.stl.pay.disableBillRepayment.approveStatusNA')
			}
			if(value=='AG') {
				return pay.applyDisableRepayment.i18n('foss.stl.pay.disableBillRepayment.approveStatusAG')
			}
			if(value=='AA') {
				return pay.applyDisableRepayment.i18n('foss.stl.pay.disableBillRepayment.approveStatusAA')
			}
			if(value=='AD') {
				return pay.applyDisableRepayment.i18n('foss.stl.pay.disableBillRepayment.approveStatusAD')
			}
    	}
			}, {
				header : pay.applyDisableRepayment.i18n('foss.stl.pay.disableBillRepayment.disableNote'),
				dataIndex : 'disableNote',
				hidden : false
			}, {
				header : pay.applyDisableRepayment.i18n('foss.stl.pay.disableBillRepayment.repaymentAmount'),
				dataIndex : 'repaymentAmount',
				hidden : false
			}, {
				header : pay.applyDisableRepayment.i18n('foss.stl.pay.disableBillRepayment.disableNum'),
				dataIndex : 'disableNum',
				hidden : false
			}, {
				header : pay.applyDisableRepayment.i18n('foss.stl.pay.disableBillRepayment.trueAmount'),
				dataIndex : 'trueAmount',
				hidden : false
			}, {
				header : pay.applyDisableRepayment.i18n('foss.stl.pay.disableBillRepayment.oaPaymentNo'),
				dataIndex : 'oaPaymentNo',
				hidden : false
			}
			],
	viewConfig : {
		enableTextSelection : true
	},
	constructor : function(config) {
		var me = this;
		cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.repaymentApply.repaymentApplyStore');
		me.dockedItems = [{
					xtype : 'toolbar',
					dock : 'top',
					layout : 'column',
					items : [{
								xtype : 'container',
								border : false,
								html : '&nbsp;',
								columnWidth : .80
							}]
				}, {
					xtype : 'toolbar',
					dock : 'bottom',
					layout : 'column',
					defaults : {
						margin : '0 0 5 3'
					},
					items : [{
								height : 5,
								columnWidth : 1,
								layout : 'hbox'
							}, {
								xtype : 'displayfield',
								allowBlank : true,
								columnWidth : .2,
								name : 'totalCount',
								labelWidth : 80,
								width : 185,
								fieldLabel : pay.applyDisableRepayment.i18n('foss.stl.pay.disableBillRepayment.totalCountRecord'),
								value : me.getTotalCount()
							}, {
								xtype : 'displayfield',
								allowBlank : true,
								columnWidth : .2,
								name : 'totalRepayAmount',
								labelWidth : 90,
								fieldLabel : pay.applyDisableRepayment.i18n('foss.stl.pay.disableBillRepayment.totalRepayAmount'),
								value : me.getTotalRepayAmount()
							}, {
								xtype : 'displayfield',
								allowBlank : true,
								columnWidth : .3,
								labelWidth : 120,
								fieldLabel : pay.applyDisableRepayment.i18n('foss.stl.pay.disableBillRepayment.totalApplyAmount'),
								name : 'totalApplyAmount',
								value : me.getTotalApplyAmount()
							}, {
								xtype : 'standardpaging',
								store : me.store,
								pageSize : 10,
								columnWidth : 1,
								plugins : Ext.create('Deppon.ux.PageSizePlugin', {
											// 设置分页记录最大值，防止输入过大的数值
											maximumSize : 200,
											sizeList : [['20', 10], ['50', 50], ['100', 100], ['200', 200]]
										})
							}, {
								xtype : 'container',
								border : false,
								html : '&nbsp;',
								columnWidth : .80
							}, {
								xtype : 'button',
								disabled : !(pay.applyDisableRepayment.isPermission('/stl-web/pay/approveRepayApplyByJL.action') || pay.applyDisableRepayment
										.isPermission('/stl-web/pay/approveRepayApplyByKJ.action')),
								hidden : !(pay.applyDisableRepayment.isPermission('/stl-web/pay/approveRepayApplyByJL.action') || pay.applyDisableRepayment
										.isPermission('/stl-web/pay/approveRepayApplyByKJ.action')),
								text : pay.applyDisableRepayment.i18n('foss.stl.pay.disableBillRepayment.shenpitongyi'),
								columnWidth : .05,
								handler : function(_this){
								var me = this;
								pay.applyDisableRepayment.moreApprove(me,pay.applyDisableRepayment.ty)}
							}, {
								xtype : 'button',
								disabled : !(pay.applyDisableRepayment.isPermission('/stl-web/pay/approveRepayApplyByJL.action') || pay.applyDisableRepayment
										.isPermission('/stl-web/pay/approveRepayApplyByKJ.action')),
								hidden : !(pay.applyDisableRepayment.isPermission('/stl-web/pay/approveRepayApplyByJL.action') || pay.applyDisableRepayment
										.isPermission('/stl-web/pay/approveRepayApplyByKJ.action')),
								text : pay.applyDisableRepayment.i18n('foss.stl.pay.disableBillRepayment.back'),
								columnWidth : .05,
								handler :function(_this){
								var me =this
								pay.applyDisableRepayment.moreApprove(me,pay.applyDisableRepayment.th)}
							}]
				}];
		me.callParent([cfg]);
	}
});


// 核销单model
Ext.define('Foss.DisableRepaymentbill.WriteoffDetailModel', {
			extend : 'Ext.data.Model',
			fields : [{
						name : 'id'
					}, {
						name : 'repaymentApplicationId'
					}, {
						name : 'writeOffBillNo'
					}, {
						name : 'repaymentNo'
					}, {
						name : 'receviceNo'
					}, {
						name : 'receviceWaybillNo'
					}, {
						name : 'statementNo'
					}, {
						name : 'customerCode'
					}, {
						name : 'customerName'
					}, {
						name : 'amount',
						type : 'long'
					}, {
						name : 'writeoffType'
					}, {
						name : 'writeoffTime',
						type : 'date',
						convert : stl.longToDateConvert
					}, {
						name : 'orgCode'
					}, {
						name : 'orgName'
					}, {
						name : 'createTime',
						type : 'date',
						convert : stl.longToDateConvert
					}, {
						name : 'modifyTime',
						type : 'date',
						convert : stl.longToDateConvert
					}, {
						name : 'active'
					}, {
						name : 'isInit'
					}]
		});

// 明细Store
Ext.define('Foss.DisableRepaymentbill.WriteoffDetailStore', {
			extend : 'Ext.data.Store',
			model : 'Foss.DisableRepaymentbill.WriteoffDetailModel'
		});

// 查询作废明细
Ext.define('Foss.DisableRepaymentbill.WriteoffDetailGrid', {
			extend : 'Ext.grid.Panel',
			title : pay.applyDisableRepayment.i18n('foss.stl.pay.disableBillRepayment.repaymentDetail'),
			frame : true,
			height : 500,
			emptyText : pay.applyDisableRepayment.i18n('foss.stl.pay.common.noResult'),
			store : Ext.create('Foss.DisableRepaymentbill.WriteoffDetailStore'),
			columns : [{
						header : pay.applyDisableRepayment.i18n('foss.stl.pay.disableBillRepayment.id'),
						dataIndex : 'id',
						hidden : true
					}, {
						header : pay.applyDisableRepayment.i18n('foss.stl.pay.disableBillRepayment.repaymentApplicationId'),
						dataIndex : 'repaymentApplicationId',
						hidden : true
					}, {
						header : pay.applyDisableRepayment.i18n('foss.stl.pay.disableBillRepayment.writeOffBillNo'),
						dataIndex : 'writeOffBillNo',
						hidden : false
					}, {
						header : pay.applyDisableRepayment.i18n('foss.stl.pay.disableBillRepayment.repaymentNo'),
						dataIndex : 'repaymentNo',
						hidden : false
					}, {
						header : pay.applyDisableRepayment.i18n('foss.stl.pay.disableBillRepayment.receviceNo'),
						dataIndex : 'receviceNo',
						hidden : false
					}, {
						header : pay.applyDisableRepayment.i18n('foss.stl.pay.disableBillRepayment.receviceWaybillNo'),
						dataIndex : 'receviceWaybillNo',
						hidden : false
					}, {
						header : pay.applyDisableRepayment.i18n('foss.stl.pay.disableBillRepayment.statementNo'),
						dataIndex : 'statementNo',
						hidden : false
					}, {
						header : pay.applyDisableRepayment.i18n('foss.stl.pay.disableBillRepayment.customerCode'),
						dataIndex : 'customerCode',
						hidden : false
					}, {
						header : pay.applyDisableRepayment.i18n('foss.stl.pay.disableBillRepayment.customerName'),
						dataIndex : 'customerName',
						hidden : false
					}, {
						header : pay.applyDisableRepayment.i18n('foss.stl.pay.disableBillRepayment.amount'),
						dataIndex : 'amount',
						hidden : false
					}, {
						header : pay.applyDisableRepayment.i18n('foss.stl.pay.disableBillRepayment.writeoffType'),
						dataIndex : 'writeoffType',
						hidden : false,
						renderer:function(value){
    		var displayField = FossDataDictionary.rendererSubmitToDisplay(value,settlementDict.BILL_WRITEOFF__WRITEOFF_TYPE);
    		return displayField;
    	}
					}, {
						header : pay.applyDisableRepayment.i18n('foss.stl.pay.disableBillRepayment.writeoffTime'),
						dataIndex : 'writeoffTime',
						hidden : false,
				renderer:function(value){
    		return stl.dateFormat(value,'Y-m-d H:i:s');
    	}
					}, {
						header : pay.applyDisableRepayment.i18n('foss.stl.pay.disableBillRepayment.orgCode'),
						dataIndex : 'orgCode',
						hidden : false
					}, {
						header : pay.applyDisableRepayment.i18n('foss.stl.pay.disableBillRepayment.orgName'),
						dataIndex : 'orgName',
						hidden : false
					}, {
						header : pay.applyDisableRepayment.i18n('foss.stl.pay.disableBillRepayment.createTime'),
						dataIndex : 'createTime',
						hidden : false,
				renderer:function(value){
    		return stl.dateFormat(value,'Y-m-d H:i:s');
    	}
					}, {
						header : pay.applyDisableRepayment.i18n('foss.stl.pay.disableBillRepayment.modifyTime'),
						dataIndex : 'modifyTime',
						hidden : false,
				renderer:function(value){
    		return stl.dateFormat(value,'Y-m-d H:i:s');
    	}
					}],
			viewConfig : {
				enableTextSelection : true
			},
			dockedItems : [{
						xtype : 'toolbar',
						dock : 'bottom',
						layout : 'column',
						defaults : {
							margin : '0 0 5 3'
						},
						items : [{
									xtype : 'displayfield',
									allowBlank : true,
									columnWidth : .2,
									labelWidth : 90,
									fieldLabel : pay.applyDisableRepayment.i18n('foss.stl.pay.disableBillRepayment.totalCountRecord')
								}, {
									xtype : 'container',
									border : false,
									html : '&nbsp;',
									columnWidth : .05
								}, {
									xtype : 'displayfield',
									allowBlank : true,
									columnWidth : .2,
									labelWidth : 90,
									// renderer:stl.amountFormat,
									fieldLabel : pay.applyDisableRepayment.i18n('foss.stl.pay.disableBillRepayment.writeOffAmount')
								}]
					}]
		});

Ext.define('Foss.DisableRepaymentbill.WriteoffDetailWindow',{
	extend:'Ext.window.Window',
	title:pay.applyDisableRepayment.i18n('foss.stl.pay.disableBillRepayment.applyDisableTitle'),
    id:'Foss_DisableRepaymentbill_WriteoffDetailWindow_ID',
	titleAlign:'left',
	width:stl.SCREENWIDTH*0.68,
	modal:true,
	constrainHeader: true,
	closeAction:'destory',
	writeoffDetailGrid:null,
	getWriteoffDetailGrid:function() {
		var me = this;
		return this.writeoffDetailGrid;
	},
	
	constructor:function(config){
		var me = this;
		cfg = Ext.apply({},config);
		
		me.writeoffDetailGrid = Ext.create('Foss.DisableRepaymentbill.WriteoffDetailGrid');
		
		
		me.items = [me.writeoffDetailGrid];
		me.callParent([cfg]);
	}
});


// 初始化界面
Ext.onReady(function() {
			Ext.Ajax.timeout = 180000;
			Ext.QuickTips.init();

			if (Ext.getCmp('T_pay-applyDisableRepayment_content')) {
				return;
			}
			// 创建还款单4个tab
			var StlDisableRepaymentQueryInfoTab = Ext.create('Foss.stlapplyDisableRepayment.StlDisableRepaymentQueryInfoTab');

			// 查询grid创建
			var StlDisableRepaymentQueryInfoGrid = Ext.getCmp('Foss_stlapplyDisableRepayment_StlDisableRepaymentQueryInfoGrid_Id');
			if (Ext.isEmpty(StlDisableRepaymentQueryInfoGrid)) {
				StlDisableRepaymentQueryInfoGrid = Ext.create('Foss.stlapplyDisableRepayment.StlDisableRepaymentQueryInfoGrid', {
							id : 'Foss_stlapplyDisableRepayment_StlDisableRepaymentQueryInfoGrid_Id',
							hidden : true
						});
			}

			// 主页面创建
			Ext.create('Ext.panel.Panel', {
						id : 'T_pay-applyDisableRepayment_content',
						cls : "panelContentNToolbar",
						bodyCls : 'panelContentNToolbar-body',
						layout : 'auto',
						// 获得查询FORM
						getQueryTab : function() {
							return StlDisableRepaymentQueryInfoTab;
						},
						// 获得查询结果GRID
						getQueryGrid : function() {
							return StlDisableRepaymentQueryInfoGrid;
						},
						items : [StlDisableRepaymentQueryInfoTab, StlDisableRepaymentQueryInfoGrid],
						renderTo : 'T_pay-applyDisableRepayment-body',
						// 角色权限问题
						listeners : {
							'boxready' : function(th) {
								var roles = pay.applyDisableRepayment.roles;
								var queryByDateTab = th.getQueryTab().items.items[0].items.items[0];
								var orgName = queryByDateTab.getForm().findField('orgName');
								// 设置部门为默认当前登录部门
								if (!Ext.isEmpty(stl.currentDept.code)) {
									var displayText = stl.currentDept.name
									var valueText = stl.currentDept.code;
									var store = orgName.store;
									var key = orgName.displayField + '';
									var value = orgName.valueField + '';
									var m = Ext.create(store.model.modelName);
									m.set(key, displayText);
									m.set(value, valueText);
									store.loadRecords([m]);
									orgName.setValue(valueText);
								}
							}
						}
					});
		});