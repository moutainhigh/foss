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
agency.PAPayable.billPAPayableConfirmAlert = function(message, yesFn, noFn) {
	Ext.Msg.confirm(agency.PAPayable.i18n('foss.stl.agency.common.alert'), message, function(o) {
		if (o == 'yes') {
			yesFn();
		} else {
			noFn();
		}
	});
};

agency.PAPayable.QUERY_BYDATE = 'TD';// 按客户查询
agency.PAPayable.QUERY_PAYABLE_NO = 'TP';// 按单号查询
agency.PAPayable.QUERY_SOURCE_BILL_NO = 'TSB';// 按单号查询

/**
 * 按日期,客户查询
 */
agency.PAPayable.queryBillPayableEntityParams = function() {
	var form = this.up('form').getForm();
	agency.PAPayable.startBusinessDate = form.findField('startDateAgency').getValue();
	agency.PAPayable.endBusinessDate = form.findField('endDateAgency').getValue();
	agency.PAPayable.customerDetialCode = form.findField('customerDetial').getValue();
	agency.PAPayable.approveStatus = form.findField('approveStatus').getValue();
	agency.PAPayable.queryByTab = agency.PAPayable.QUERY_BYDATE;
	if (agency.PAPayable.startBusinessDate == null || agency.PAPayable.startBusinessDate == '') {
		Ext.Msg.alert(agency.PAPayable.i18n('foss.stl.agency.common.alert'), agency.PAPayable.i18n('foss.stl.agency.airPayAndRec.notDateNull'));
		return false;
	}

	if (agency.PAPayable.endBusinessDate == null || agency.PAPayable.endBusinessDate == '') {
		Ext.Msg.alert(agency.PAPayable.i18n('foss.stl.agency.common.alert'), agency.PAPayable.i18n('foss.stl.agency.airPayAndRec.notDateNull'));
		return false;
	}

	var compareTwoDate = stl.compareTwoDate(agency.PAPayable.startBusinessDate, agency.PAPayable.endBusinessDate);
	if (compareTwoDate > stl.DATELIMITDAYS_MONTH) {
		Ext.Msg.alert(agency.PAPayable.i18n('foss.stl.agency.common.alert'), '你好，您所查询的时间段已超过' + stl.DATELIMITDAYS_MONTH + '天，请重新输入结束时间');
		return false;
	} else if (compareTwoDate < 1) {
		Ext.Msg.alert(agency.PAPayable.i18n('foss.stl.agency.common.alert'), '你好，结束时间不能小于开始时间，请重新输入!');
		return false;
	}
	agency.PAPayable.customerCode = "";
	if (agency.PAPayable.customerDetialCode != "" && agency.PAPayable.customerDetialCode != null) {
		agency.PAPayable.customerCode = agency.PAPayable.customerDetialCode;
	}
	Ext.getCmp('Foss_StlPAPayable_StlPayableQueryInfoGrid_Id').store.on('beforeload', function(store, operation, eOpts) {
		Ext.apply(operation, {
			params : {
				'billPayableAgencyVo.billPayableAgencyDto.startBusinessDate' : agency.PAPayable.startBusinessDate,
				'billPayableAgencyVo.billPayableAgencyDto.endBusinessDate' : agency.PAPayable.endBusinessDate,
				'billPayableAgencyVo.billPayableAgencyDto.customerCode' : agency.PAPayable.customerCode,
				'billPayableAgencyVo.billPayableAgencyDto.approveStatus' : agency.PAPayable.approveStatus,
				'billPayableAgencyVo.billPayableAgencyDto.queryByTab' : agency.PAPayable.queryByTab
			}
		});
	});
	if (this.up('form').getForm().isValid()) {
		Ext.getCmp('Foss_StlPAPayable_StlPayableQueryInfoGrid_Id').store.loadPage(1, {
			callback : function(records, operation, success) {
				var rawData = Ext.getCmp('Foss_StlPAPayable_StlPayableQueryInfoGrid_Id').store.proxy.reader.rawData;
				// 当success:false ,isException:false --业务异常
				if (!success && !rawData.isException) {
					Ext.Msg.alert(agency.PAPayable.i18n('foss.stl.agency.common.alert'), rawData.message);
					return false;
				}
				if (success) {
					var result = Ext.decode(operation.response.responseText);
					Ext.getCmp('Foss_StlPAPayable_StlPAPayableQueryInfoGrid_PAPayableTotalRows_Id').setValue(
						result.billPayableAgencyVo.billPayableAgencyDto.totalNum);
					Ext.getCmp('Foss_StlPAPayable_StlPAPayableQueryInfoGrid_PAPayableTotalAmount_Id').setValue(
						result.billPayableAgencyVo.billPayableAgencyDto.totalAmount);
					Ext.getCmp('Foss_StlPAPayable_StlPAPayableQueryInfoGrid_PAPayableVerifyAmount_Id').setValue(
						result.billPayableAgencyVo.billPayableAgencyDto.verifyTotalAmount);
					Ext.getCmp('Foss_StlPAPayable_StlPAPayableQueryInfoGrid_PAPayableUnverifyAmount_Id').setValue(
						result.billPayableAgencyVo.billPayableAgencyDto.unverifyTotalAmount);
				}
			}

		});

	} else {
		Ext.Msg.alert(agency.PAPayable.i18n('foss.stl.agency.common.alert'), agency.PAPayable.i18n('foss.stl.agency.airPayAndRec.pageCheck'));
		return false;
	}
}

/**
 * 按应付单号查询
 * 
 * @returns {Boolean}
 */
agency.PAPayable.queryByPayableNOs = function() {
	agency.PAPayable.payableNo = this.up('form').getForm().findField('payableNos').getValue();
	agency.PAPayable.queryByTab = agency.PAPayable.QUERY_PAYABLE_NO;
	// 单号不能为空
	if (agency.PAPayable.payableNo == null || agency.PAPayable.payableNo == '') {
		Ext.Msg.alert(agency.PAPayable.i18n('foss.stl.agency.common.alert'), agency.PAPayable.i18n('foss.stl.agency.payable.notNullPayableNo'));
		return false;
	}
	// 单号个数不能超过10个
	var payableNosStr = new Array();
	payableNosStr = stl.selectPayableNosArray(agency.PAPayable.payableNo);
	if (payableNosStr.length > 10) {
		Ext.Msg.alert(agency.PAPayable.i18n('foss.stl.agency.common.alert'), agency.PAPayable.i18n('foss.stl.agency.airPayAndRec.notGreaterThanTen'));
		return false;
	}
	Ext.getCmp('Foss_StlPAPayable_StlPayableQueryInfoGrid_Id').store.on('beforeload', function(store, operation, eOpts) {
		Ext.apply(operation, {
			params : {
				'billPayableAgencyVo.billPayableAgencyDto.payableNo' : agency.PAPayable.payableNo,
				'billPayableAgencyVo.billPayableAgencyDto.queryByTab' : agency.PAPayable.queryByTab
			}
		});
	});
	if (this.up('form').getForm().isValid()) {
		Ext.getCmp('Foss_StlPAPayable_StlPayableQueryInfoGrid_Id').store.loadPage(1, {
			callback : function(records, operation, success) {
				var rawData = Ext.getCmp('Foss_StlPAPayable_StlPayableQueryInfoGrid_Id').store.proxy.reader.rawData;
				// 当success:false ,isException:false --业务异常
				if (!success && !rawData.isException) {
					Ext.Msg.alert(agency.PAPayable.i18n('foss.stl.agency.common.alert'), rawData.message);
					return false;
				}
				if (success) {
					var result = Ext.decode(operation.response.responseText);
					Ext.getCmp('Foss_StlPAPayable_StlPAPayableQueryInfoGrid_PAPayableTotalRows_Id').setValue(
						result.billPayableAgencyVo.billPayableAgencyDto.totalNum);
					Ext.getCmp('Foss_StlPAPayable_StlPAPayableQueryInfoGrid_PAPayableTotalAmount_Id').setValue(
						result.billPayableAgencyVo.billPayableAgencyDto.totalAmount);
					Ext.getCmp('Foss_StlPAPayable_StlPAPayableQueryInfoGrid_PAPayableVerifyAmount_Id').setValue(
						result.billPayableAgencyVo.billPayableAgencyDto.verifyTotalAmount);
					Ext.getCmp('Foss_StlPAPayable_StlPAPayableQueryInfoGrid_PAPayableUnverifyAmount_Id').setValue(
						result.billPayableAgencyVo.billPayableAgencyDto.unverifyTotalAmount);
				}
			}

		});

	} else {
		Ext.Msg.alert(agency.PAPayable.i18n('foss.stl.agency.common.alert'), agency.PAPayable.i18n('foss.stl.agency.airPayAndRec.pageCheck'));
		return false;
	}
}

/**
 * 按运单号查询
 * 
 * @returns {Boolean}
 */
agency.PAPayable.queryBySourceBillNOs = function() {
	agency.PAPayable.sourceBillNo = this.up('form').getForm().findField('sourceBillNos').getValue();
	agency.PAPayable.queryByTab = agency.PAPayable.QUERY_SOURCE_BILL_NO;
	// 单号不能为空
	if (agency.PAPayable.sourceBillNo == null || agency.PAPayable.sourceBillNo == '') {
		Ext.Msg.alert(agency.PAPayable.i18n('foss.stl.agency.common.alert'), agency.PAPayable
			.i18n('foss.stl.agency.airPayAndRec.notNullSourceBillNo'));
		return false;
	}
	// 单号个数不能超过10个
	var payableNosStr = new Array();
	payableNosStr = stl.selectWaybillNosArray(agency.PAPayable.sourceBillNo);
	if (payableNosStr.length > 10) {
		Ext.Msg.alert(agency.PAPayable.i18n('foss.stl.agency.common.alert'), agency.PAPayable.i18n('foss.stl.agency.airPayAndRec.notGreaterThanTen'));
		return false;
	}

	Ext.getCmp('Foss_StlPAPayable_StlPayableQueryInfoGrid_Id').store.on('beforeload', function(store, operation, eOpts) {
		Ext.apply(operation, {
			params : {
				'billPayableAgencyVo.billPayableAgencyDto.sourceBillNo' : agency.PAPayable.sourceBillNo,
				'billPayableAgencyVo.billPayableAgencyDto.queryByTab' : agency.PAPayable.queryByTab
			}
		});
	});

	if (this.up('form').getForm().isValid()) {
		Ext.getCmp('Foss_StlPAPayable_StlPayableQueryInfoGrid_Id').store.loadPage(1, {
			callback : function(records, operation, success) {
				var rawData = Ext.getCmp('Foss_StlPAPayable_StlPayableQueryInfoGrid_Id').store.proxy.reader.rawData;
				// 当success:false ,isException:false --业务异常
				if (!success && !rawData.isException) {
					Ext.Msg.alert(agency.PAPayable.i18n('foss.stl.agency.common.alert'), rawData.message);
					return false;
				}
				if (success) {
					var result = Ext.decode(operation.response.responseText);
					Ext.getCmp('Foss_StlPAPayable_StlPAPayableQueryInfoGrid_PAPayableTotalRows_Id').setValue(
						result.billPayableAgencyVo.billPayableAgencyDto.totalNum);
					Ext.getCmp('Foss_StlPAPayable_StlPAPayableQueryInfoGrid_PAPayableTotalAmount_Id').setValue(
						result.billPayableAgencyVo.billPayableAgencyDto.totalAmount);
					Ext.getCmp('Foss_StlPAPayable_StlPAPayableQueryInfoGrid_PAPayableVerifyAmount_Id').setValue(
						result.billPayableAgencyVo.billPayableAgencyDto.verifyTotalAmount);
					Ext.getCmp('Foss_StlPAPayable_StlPAPayableQueryInfoGrid_PAPayableUnverifyAmount_Id').setValue(
						result.billPayableAgencyVo.billPayableAgencyDto.unverifyTotalAmount);
				}
			}

		});

	} else {
		Ext.Msg.alert(agency.PAPayable.i18n('foss.stl.agency.common.alert'), agency.PAPayable.i18n('foss.stl.agency.airPayAndRec.pageCheck'));
		return false;
	}
}

// 导出快递代理其他应收单
agency.PAPayable.PAPayableListExport = function() {
	// 获取表格
	grid = Ext.getCmp('Foss_StlPAPayable_StlPayableQueryInfoGrid_Id');
	// 如果没有查询到数据，则不能进行导出操作
	if (grid.store.data.length == 0) {
		Ext.Msg.alert(agency.PAPayable.i18n('foss.stl.agency.common.alert'), agency.PAPayable
			.i18n('foss.stl.agency.airPayAndRec.exportResultSetNotnull'));
		return false;
	}

	if (!Ext.fly('downloadAttachFileForm')) {
		var frm = document.createElement('form');
		frm.id = 'downloadAttachFileForm';
		frm.style.display = 'none';
		document.body.appendChild(frm);
	}
	if (agency.PAPayable.queryByTab == agency.PAPayable.QUERY_BYDATE) {
		searchParams = {
			'billPayableAgencyVo.billPayableAgencyDto.startBusinessDate' : agency.PAPayable.startBusinessDate,
			'billPayableAgencyVo.billPayableAgencyDto.endBusinessDate' : agency.PAPayable.endBusinessDate,
			'billPayableAgencyVo.billPayableAgencyDto.approveStatus' : agency.PAPayable.approveStatus,
			'billPayableAgencyVo.billPayableAgencyDto.customerCode' : agency.PAPayable.customerCode,
			'billPayableAgencyVo.billPayableAgencyDto.queryByTab' : agency.PAPayable.queryByTab
		}
	} else if (agency.PAPayable.queryByTab == agency.PAPayable.QUERY_PAYABLE_NO) {
		searchParams = {
			'billPayableAgencyVo.billPayableAgencyDto.payableNo' : agency.PAPayable.payableNo,
			'billPayableAgencyVo.billPayableAgencyDto.queryByTab' : agency.PAPayable.queryByTab
		}
	} else if (agency.PAPayable.queryByTab == agency.PAPayable.QUERY_SOURCE_BILL_NO) {
		searchParams = {
			'billPayableAgencyVo.billPayableAgencyDto.sourceBillNo' : agency.PAPayable.sourceBillNo,
			'billPayableAgencyVo.billPayableAgencyDto.queryByTab' : agency.PAPayable.queryByTab
		}
	}
	var yesFn = function() {
		Ext.Ajax.request({
			url : agency.realPath('PAPayableListExport.action'),
			form : Ext.fly('downloadAttachFileForm'),
			params : searchParams,
			isUpload : true,
			success : function(response) {
				// 如果异常信息有值，则弹出提示
				var result = Ext.decode(response.responseText);
				if (!Ext.isEmpty(result.errorMessage)) {
					Ext.Msg.alert(agency.PAPayable.i18n('foss.stl.agency.common.alert'), result.errorMessage);
					return false;
				}
				Ext.ux.Toast.msg(agency.PAPayable.i18n('foss.stl.agency.common.alert'), agency.PAPayable
					.i18n('foss.stl.agency.airPayAndRec.exportSuccess'), 'success', 1000);
			},
			failure : function(response) {
				Ext.ux.Toast.msg(agency.PAPayable.i18n('foss.stl.agency.common.alert'), agency.PAPayable
					.i18n('foss.stl.agency.airPayAndRec.exportError'), 'error', 1000);
			}
		});
	};
	var noFn = function() {
		return false;
	};
	agency.PAPayable.billPAPayableConfirmAlert(agency.PAPayable.i18n('foss.stl.agency.PAPayAndRec.isExport'), yesFn, noFn);
}

agency.PAPayable.queryCondition = function() {
	var searchParams;
	if (agency.PAPayable.queryByTab == agency.PAPayable.QUERY_BYDATE) {
		searchParams = {
			'startBusinessDate' : agency.PAPayable.startBusinessDate,
			'endBusinessDate' : agency.PAPayable.endBusinessDate,
			'approveStatus' : agency.PAPayable.approveStatus,
			'customerCode' : agency.PAPayable.customerCode,
			'queryByTab' : agency.PAPayable.queryByTab
		}
	} else if (agency.PAPayable.queryByTab == agency.PAPayable.QUERY_PAYABLE_NO) {
		searchParams = {
			'payableNo' : agency.PAPayable.payableNo,
			'queryByTab' : agency.PAPayable.queryByTab
		}
	} else if (agency.PAPayable.queryByTab == agency.PAPayable.QUERY_SOURCE_BILL_NO) {
		searchParams = {
			'sourceBillNo' : agency.PAPayable.sourceBillNo,
			'queryByTab' : agency.PAPayable.queryByTab
		}
	}
	return searchParams;
}
// 快递代理其他管理单条作废
agency.PAPayable.writeBackPAOtherOnePayable = function(grid, rowIndex, colIndex) {
	var selectionPayable = grid.getStore().getAt(rowIndex);
	var jsonDataPayable = new Array();
	jsonDataPayable.push(selectionPayable.data);
	var entity = new Object();
	entity.billPayableAgencyDtos = jsonDataPayable;
	var searchParams = agency.PAPayable.queryCondition(searchParams);
	entity.billPayableAgencyDto = searchParams;
	var yesFn = function() {
		Ext.Ajax.request({
			url : agency.realPath('writeBackPAOtherBillPayable.action'),
			jsonData : {
				'billPayableAgencyVo' : entity
			},
			method : 'post',
			success : function(response) {
				Ext.getCmp('Foss_StlPAPayable_StlPayableQueryInfoGrid_Id').store.load({
					callback : function(records, operation, success) {
						var result = Ext.decode(operation.response.responseText);
						if (success) {
							if (result.isException) {
								Ext.Msg.alert(agency.PAPayable.i18n('foss.stl.agency.common.alert'), result.message);
								return false;
							}
							Ext.getCmp('Foss_StlPAPayable_StlPAPayableQueryInfoGrid_PAPayableTotalRows_Id').setValue(
								result.billPayableAgencyVo.billPayableAgencyDto.totalNum);
							Ext.getCmp('Foss_StlPAPayable_StlPAPayableQueryInfoGrid_PAPayableTotalAmount_Id').setValue(
								result.billPayableAgencyVo.billPayableAgencyDto.totalAmount);
							Ext.getCmp('Foss_StlPAPayable_StlPAPayableQueryInfoGrid_PAPayableVerifyAmount_Id').setValue(
								result.billPayableAgencyVo.billPayableAgencyDto.verifyTotalAmount);
							Ext.getCmp('Foss_StlPAPayable_StlPAPayableQueryInfoGrid_PAPayableUnverifyAmount_Id').setValue(
								result.billPayableAgencyVo.billPayableAgencyDto.unverifyTotalAmount);
						}
					}

				});
				Ext.Msg.alert(agency.PAPayable.i18n('foss.stl.agency.common.alert'), agency.PAPayable
					.i18n('foss.stl.agency.airPayAndRec.disableSuccess'));
			},
			exception : function(response) {
				var result = Ext.decode(response.responseText);
				Ext.Msg.alert(agency.PAPayable.i18n('foss.stl.agency.common.alert'), result.message);
			},
			failure : function(response) {
				var result = Ext.decode(response.responseText);
			}
		});
	};
	var noFn = function() {
		return false;
	};
	agency.PAPayable.billPAPayableConfirmAlert(agency.PAPayable.i18n('foss.stl.agency.airPayAndRec.isDisable'), yesFn, noFn);
}
// 快递代理其他管理作废
agency.PAPayable.writeBackPAOtherBillPayable = function() {
	var selectionPayable = Ext.getCmp('Foss_StlPAPayable_StlPayableQueryInfoGrid_Id').getSelectionModel().getSelection();

	if (selectionPayable.length == 0) {
		Ext.Msg.alert(agency.PAPayable.i18n('foss.stl.agency.common.alert'), agency.PAPayable.i18n('foss.stl.agency.airPayAndRec.disableText'));
		return false;
	}
	var jsonDataPayable = new Array();
	for ( var i = 0; i < selectionPayable.length; i++) {
		jsonDataPayable.push(selectionPayable[i].data);
	}
	var entity = new Object();
	entity.billPayableAgencyDtos = jsonDataPayable;
	var searchParams = agency.PAPayable.queryCondition(searchParams);
	entity.billPayableAgencyDto = searchParams;

	var yesFn = function() {
		Ext.Ajax.request({
			url : agency.realPath('writeBackPAOtherBillPayable.action'),
			jsonData : {
				'billPayableAgencyVo' : entity
			},
			method : 'post',
			success : function(response) {
				Ext.getCmp('Foss_StlPAPayable_StlPayableQueryInfoGrid_Id').store.load({
					callback : function(records, operation, success) {
						var result = Ext.decode(operation.response.responseText);
						if (success) {
							if (result.isException) {
								Ext.Msg.alert(agency.PAPayable.i18n('foss.stl.agency.common.alert'), result.message);
								return false;
							}
							Ext.getCmp('Foss_StlPAPayable_StlPAPayableQueryInfoGrid_PAPayableTotalRows_Id').setValue(
								result.billPayableAgencyVo.billPayableAgencyDto.totalNum);
							Ext.getCmp('Foss_StlPAPayable_StlPAPayableQueryInfoGrid_PAPayableTotalAmount_Id').setValue(
								result.billPayableAgencyVo.billPayableAgencyDto.totalAmount);
							Ext.getCmp('Foss_StlPAPayable_StlPAPayableQueryInfoGrid_PAPayableVerifyAmount_Id').setValue(
								result.billPayableAgencyVo.billPayableAgencyDto.verifyTotalAmount);
							Ext.getCmp('Foss_StlPAPayable_StlPAPayableQueryInfoGrid_PAPayableUnverifyAmount_Id').setValue(
								result.billPayableAgencyVo.billPayableAgencyDto.unverifyTotalAmount);
						}
					}

				});
				Ext.Msg.alert(agency.PAPayable.i18n('foss.stl.agency.common.alert'), agency.PAPayable
					.i18n('foss.stl.agency.airPayAndRec.disableSuccess'));
			},
			exception : function(response) {
				var result = Ext.decode(response.responseText);
				Ext.Msg.alert(agency.PAPayable.i18n('foss.stl.agency.common.alert'), result.message);
			},
			failure : function(response) {
				var result = Ext.decode(response.responseText);
			}
		});
	};
	var noFn = function() {
		return false;
	};
	agency.PAPayable.billPAPayableConfirmAlert(agency.PAPayable.i18n('foss.stl.agency.airPayAndRec.isDisable'), yesFn, noFn);
}

// 单条数据审核
agency.PAPayable.auditPAOtherOneBillPayable = function(grid, rowIndex, colIndex) {
	var selectionPayable = grid.getStore().getAt(rowIndex);
	var jsonDataPayable = new Array();
	jsonDataPayable.push(selectionPayable.data);
	var entity = new Object();
	entity.billPayableAgencyDtos = jsonDataPayable;
	var searchParams = agency.PAPayable.queryCondition(searchParams);
	entity.billPayableAgencyDto = searchParams;
	var yesFn = function() {
		Ext.Ajax.request({
			url : agency.realPath('auditPAOtherBillPayable.action'),
			jsonData : {
				'billPayableAgencyVo' : entity
			},
			method : 'post',
			success : function(response) {
				Ext.getCmp('Foss_StlPAPayable_StlPayableQueryInfoGrid_Id').store.load({
					callback : function(records, operation, success) {
						var result = Ext.decode(operation.response.responseText);
						if (success) {
							if (result.isException) {
								Ext.Msg.alert(agency.PAPayable.i18n('foss.stl.agency.common.alert'), result.message);
								return false;
							}
							Ext.getCmp('Foss_StlPAPayable_StlPAPayableQueryInfoGrid_PAPayableTotalRows_Id').setValue(
								result.billPayableAgencyVo.billPayableAgencyDto.totalNum);
							Ext.getCmp('Foss_StlPAPayable_StlPAPayableQueryInfoGrid_PAPayableTotalAmount_Id').setValue(
								result.billPayableAgencyVo.billPayableAgencyDto.totalAmount);
							Ext.getCmp('Foss_StlPAPayable_StlPAPayableQueryInfoGrid_PAPayableVerifyAmount_Id').setValue(
								result.billPayableAgencyVo.billPayableAgencyDto.verifyTotalAmount);
							Ext.getCmp('Foss_StlPAPayable_StlPAPayableQueryInfoGrid_PAPayableUnverifyAmount_Id').setValue(
								result.billPayableAgencyVo.billPayableAgencyDto.unverifyTotalAmount);
						}
					}
				});
				Ext.Msg.alert(agency.PAPayable.i18n('foss.stl.agency.common.alert'), agency.PAPayable
					.i18n('foss.stl.agency.airPayAndRec.auditSuccess'));
			},
			exception : function(response) {
				var result = Ext.decode(response.responseText);
				Ext.Msg.alert(agency.PAPayable.i18n('foss.stl.agency.common.alert'), result.message);
			},
			failure : function(response) {
				var result = Ext.decode(response.responseText);
			}
		});
	};
	var noFn = function() {
		return false;
	};
	agency.PAPayable.billPAPayableConfirmAlert(agency.PAPayable.i18n('foss.stl.agency.airPayAndRec.isAudit'), yesFn, noFn);
}

// 快递代理其他管理审核
agency.PAPayable.auditPAOtherBillPayable = function() {
	var selectionPayable = Ext.getCmp('Foss_StlPAPayable_StlPayableQueryInfoGrid_Id').getSelectionModel().getSelection();

	if (selectionPayable.length == 0) {
		Ext.Msg.alert(agency.PAPayable.i18n('foss.stl.agency.common.alert'), agency.PAPayable.i18n('foss.stl.agency.airPayAndRec.auditText'));
		return false;
	}
	var jsonDataPayable = new Array();
	for ( var i = 0; i < selectionPayable.length; i++) {
		jsonDataPayable.push(selectionPayable[i].data);
	}
	var entity = new Object();
	entity.billPayableAgencyDtos = jsonDataPayable;
	var searchParams = agency.PAPayable.queryCondition(searchParams);
	entity.billPayableAgencyDto = searchParams;

	var yesFn = function() {
		Ext.Ajax.request({
			url : agency.realPath('auditPAOtherBillPayable.action'),
			jsonData : {
				'billPayableAgencyVo' : entity
			},
			method : 'post',
			success : function(response) {
				Ext.getCmp('Foss_StlPAPayable_StlPayableQueryInfoGrid_Id').store.load({
					callback : function(records, operation, success) {
						var result = Ext.decode(operation.response.responseText);
						if (success) {
							if (result.isException) {
								Ext.Msg.alert(agency.PAPayable.i18n('foss.stl.agency.common.alert'), result.message);
								return false;
							}
							Ext.getCmp('Foss_StlPAPayable_StlPAPayableQueryInfoGrid_PAPayableTotalRows_Id').setValue(
								result.billPayableAgencyVo.billPayableAgencyDto.totalNum);
							Ext.getCmp('Foss_StlPAPayable_StlPAPayableQueryInfoGrid_PAPayableTotalAmount_Id').setValue(
								result.billPayableAgencyVo.billPayableAgencyDto.totalAmount);
							Ext.getCmp('Foss_StlPAPayable_StlPAPayableQueryInfoGrid_PAPayableVerifyAmount_Id').setValue(
								result.billPayableAgencyVo.billPayableAgencyDto.verifyTotalAmount);
							Ext.getCmp('Foss_StlPAPayable_StlPAPayableQueryInfoGrid_PAPayableUnverifyAmount_Id').setValue(
								result.billPayableAgencyVo.billPayableAgencyDto.unverifyTotalAmount);
						}
					}
				});
				Ext.Msg.alert(agency.PAPayable.i18n('foss.stl.agency.common.alert'), agency.PAPayable
					.i18n('foss.stl.agency.airPayAndRec.auditSuccess'));
			},
			exception : function(response) {
				var result = Ext.decode(response.responseText);
				Ext.Msg.alert(agency.PAPayable.i18n('foss.stl.agency.common.alert'), result.message);
			},
			failure : function(response) {
				var result = Ext.decode(response.responseText);
			}
		});
	};
	var noFn = function() {
		return false;
	};
	agency.PAPayable.billPAPayableConfirmAlert(agency.PAPayable.i18n('foss.stl.agency.airPayAndRec.isAudit'), yesFn, noFn);
}

// 单条数据反审核
agency.PAPayable.reverseAuditPAOtherOneBillPayable = function(grid, rowIndex, colIndex) {
	var selectionPayable = grid.getStore().getAt(rowIndex);
	var jsonDataPayable = new Array();
	jsonDataPayable.push(selectionPayable.data);
	var entity = new Object();
	entity.billPayableAgencyDtos = jsonDataPayable;
	var searchParams = agency.PAPayable.queryCondition(searchParams);
	entity.billPayableAgencyDto = searchParams;

	var yesFn = function() {
		Ext.Ajax.request({
			url : agency.realPath('reverseAuditPAOtherBillPayable.action'),
			jsonData : {
				'billPayableAgencyVo' : entity
			},
			method : 'post',
			success : function(response) {
				Ext.getCmp('Foss_StlPAPayable_StlPayableQueryInfoGrid_Id').store.load({
					callback : function(records, operation, success) {
						var result = Ext.decode(operation.response.responseText);
						if (success) {
							if (result.isException) {
								Ext.Msg.alert(agency.PAPayable.i18n('foss.stl.agency.common.alert'), result.message);
								return false;
							}
							Ext.getCmp('Foss_StlPAPayable_StlPAPayableQueryInfoGrid_PAPayableTotalRows_Id').setValue(
								result.billPayableAgencyVo.billPayableAgencyDto.totalNum);
							Ext.getCmp('Foss_StlPAPayable_StlPAPayableQueryInfoGrid_PAPayableTotalAmount_Id').setValue(
								result.billPayableAgencyVo.billPayableAgencyDto.totalAmount);
							Ext.getCmp('Foss_StlPAPayable_StlPAPayableQueryInfoGrid_PAPayableVerifyAmount_Id').setValue(
								result.billPayableAgencyVo.billPayableAgencyDto.verifyTotalAmount);
							Ext.getCmp('Foss_StlPAPayable_StlPAPayableQueryInfoGrid_PAPayableUnverifyAmount_Id').setValue(
								result.billPayableAgencyVo.billPayableAgencyDto.unverifyTotalAmount);
						}
					}
				});
				Ext.Msg.alert(agency.PAPayable.i18n('foss.stl.agency.common.alert'), agency.PAPayable
					.i18n('foss.stl.agency.airPayAndRec.reverseAuditSuccess'));
			},
			exception : function(response) {
				var result = Ext.decode(response.responseText);
				Ext.Msg.alert(agency.PAPayable.i18n('foss.stl.agency.common.alert'), result.message);
			},
			failure : function(response) {
				var result = Ext.decode(response.responseText);
			}
		});
	};
	var noFn = function() {
		return false;
	};
	agency.PAPayable.billPAPayableConfirmAlert(agency.PAPayable.i18n('foss.stl.agency.airPayAndRec.isReverseAudit'), yesFn, noFn);
}
// 快递代理其他管理反审核
agency.PAPayable.reverseAuditPAOtherBillPayable = function() {

	var selectionPayable = Ext.getCmp('Foss_StlPAPayable_StlPayableQueryInfoGrid_Id').getSelectionModel().getSelection();

	if (selectionPayable.length == 0) {
		Ext.Msg.alert(agency.PAPayable.i18n('foss.stl.agency.common.alert'), agency.PAPayable.i18n('foss.stl.agency.airPayAndRec.reverseAuditText'));
		return false;
	}
	var jsonDataPayable = new Array();
	for ( var i = 0; i < selectionPayable.length; i++) {
		jsonDataPayable.push(selectionPayable[i].data);
	}
	var entity = new Object();
	entity.billPayableAgencyDtos = jsonDataPayable;
	var searchParams = agency.PAPayable.queryCondition(searchParams);
	entity.billPayableAgencyDto = searchParams;

	var yesFn = function() {
		Ext.Ajax.request({
			url : agency.realPath('reverseAuditPAOtherBillPayable.action'),
			jsonData : {
				'billPayableAgencyVo' : entity
			},
			method : 'post',
			success : function(response) {
				Ext.getCmp('Foss_StlPAPayable_StlPayableQueryInfoGrid_Id').store.load({
					callback : function(records, operation, success) {
						var result = Ext.decode(operation.response.responseText);
						if (success) {
							if (result.isException) {
								Ext.Msg.alert(agency.PAPayable.i18n('foss.stl.agency.common.alert'), result.message);
								return false;
							}
							Ext.getCmp('Foss_StlPAPayable_StlPAPayableQueryInfoGrid_PAPayableTotalRows_Id').setValue(
								result.billPayableAgencyVo.billPayableAgencyDto.totalNum);
							Ext.getCmp('Foss_StlPAPayable_StlPAPayableQueryInfoGrid_PAPayableTotalAmount_Id').setValue(
								result.billPayableAgencyVo.billPayableAgencyDto.totalAmount);
							Ext.getCmp('Foss_StlPAPayable_StlPAPayableQueryInfoGrid_PAPayableVerifyAmount_Id').setValue(
								result.billPayableAgencyVo.billPayableAgencyDto.verifyTotalAmount);
							Ext.getCmp('Foss_StlPAPayable_StlPAPayableQueryInfoGrid_PAPayableUnverifyAmount_Id').setValue(
								result.billPayableAgencyVo.billPayableAgencyDto.unverifyTotalAmount);
						}
					}
				});
				Ext.Msg.alert(agency.PAPayable.i18n('foss.stl.agency.common.alert'), agency.PAPayable
					.i18n('foss.stl.agency.airPayAndRec.reverseAuditSuccess'));
			},
			exception : function(response) {
				var result = Ext.decode(response.responseText);
				Ext.Msg.alert(agency.PAPayable.i18n('foss.stl.agency.common.alert'), result.message);
			},
			failure : function(response) {
				var result = Ext.decode(response.responseText);
			}
		});
	};
	var noFn = function() {
		return false;
	};
	agency.PAPayable.billPAPayableConfirmAlert(agency.PAPayable.i18n('foss.stl.agency.airPayAndRec.isReverseAudit'), yesFn, noFn);
}

// 应付单model
Ext.define('Foss.StlPAPayable.BillPayableEntryModel', {
	extend : 'Ext.data.Model',
	fields : [ {
		name : 'payableNo'
	}, {
		name : 'sourceBillNo'
	}, {
		name : 'customerCode'
	}, {
		name : 'customerName'
	}, {
		name : 'approveStatus'
	}, {
		name : 'origOrgCode'
	}, {
		name : 'origOrgName'
	}, {
		name : 'statementBillNo'
	}, {
		name : 'payableOrgCode'
	}, {
		name : 'payableOrgName'
	}, {
		name : 'amount',
		type : 'long'
	}, {
		name : 'verifyAmount',
		type : 'long'
	}, {
		name : 'unverifyAmount',
		type : 'long'
	}, {
		name : 'businessDate',
		type : 'date',
		convert : function(value) {
			return stl.longToDateConvert(value);
		}
	}, {
		name : 'accountDate',
		type : 'date',
		convert : function(value) {
			return stl.longToDateConvert(value);
		}
	}, {
		name : 'auditDate',
		type : 'date',
		convert : function(value) {
			return stl.longToDateConvert(value);
		}
	}, {
		name : 'notes'
	} ]
});

// 应付单Store
Ext.define('Foss.StlPAPayable.BillPayableEntryStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.StlPAPayable.BillPayableEntryModel',
	proxy : {
		type : 'ajax',
		url : agency.realPath('queryPAPayablePage.action'),
		reader : {
			type : 'json',
			root : 'billPayableAgencyVo.billPayableAgencyDto.billPayableEntityList',
			totalProperty : 'totalCount'
		}
	},
	listeners : {
		'beforeLoad' : function(store, operation, eOpts) {
			var form = Ext.getCmp('T_agency-PAPayable_content').getQueryInfoTab().items.items[0].items.items[0].getForm();
			agency.PAPayable.startBusinessDate = form.findField('startDateAgency').getValue();
			agency.PAPayable.endBusinessDate = form.findField('endDateAgency').getValue();
			agency.PAPayable.customerDetialCode = form.findField('customerDetial').getValue();
			agency.PAPayable.approveStatus = form.findField('approveStatus').getValue();
			var searchParams;
			// 如果第一次进入界面，直接选择分页查询，则默认按照客户查询
			if (Ext.isEmpty(agency.PAPayable.queryByTab)) {
				searchParams = {
					'billPayableAgencyVo.billPayableAgencyDto.startBusinessDate' : agency.PAPayable.startBusinessDate,
					'billPayableAgencyVo.billPayableAgencyDto.endBusinessDate' : agency.PAPayable.endBusinessDate,
					'billPayableAgencyVo.billPayableAgencyDto.approveStatus' : agency.PAPayable.approveStatus,
					'billPayableAgencyVo.billPayableAgencyDto.customerCode' : agency.PAPayable.customerCode,
					'billPayableAgencyVo.billPayableAgencyDto.queryByTab' : agency.PAPayable.QUERY_BYDATE
				}
			} else if (agency.PAPayable.queryByTab == agency.PAPayable.QUERY_PAYABLE_NO) {
				searchParams = {
					'billPayableAgencyVo.billPayableAgencyDto.payableNo' : agency.PAPayable.payableNo,
					'billPayableAgencyVo.billPayableAgencyDto.queryByTab' : agency.PAPayable.queryByTab
				}
			} else if (agency.PAPayable.queryByTab == agency.PAPayable.QUERY_SOURCE_BILL_NO) {
				searchParams = {
					'billPayableAgencyVo.billPayableAgencyDto.sourceBillNo' : agency.PAPayable.sourceBillNo,
					'billPayableAgencyVo.billPayableAgencyDto.queryByTab' : agency.PAPayable.queryByTab
				}
			}
			Ext.apply(operation, {
				params : searchParams
			});
		}
	}
});

Ext.define('Foss.StlPAPayable.StlPAPayableQueryInfoTab', {
	extend : 'Ext.tab.Panel',
	bodyCls : 'autoHeight',
	cls : 'innerTabPanel',
	activeTab : 0,
	columnWidth : 1,
	height : 190,
	items : [ {
		title : agency.PAPayable.i18n('foss.stl.agency.common.queryByDate'),
		tabConfig : {
			width : 120
		},
		items : [ {
			xtype : 'form',
			defaults : {
				margin : '10 0 0 5',
				labelWidth : 85,
				colspan : 1
			},
			layout : {
				type : 'table',
				columns : 3
			},
			items : [ {
				xtype : 'datefield',
				id : 'startDateAgency',
				name : 'startDate',
				fieldLabel : agency.PAPayable.i18n('foss.stl.agency.airPayAndRec.startDateAgency'),
				editable : false,
				value : stl.getTargetDate(new Date(), -1),
				format : 'Y-m-d',
				allowBlank : false
			}, {
				xtype : 'datefield',
				name : 'endDateAgency',
				fieldLabel : agency.PAPayable.i18n('foss.stl.agency.airPayAndRec.endDateAgency'),
				editable : false,
				format : 'Y-m-d',
				value : new Date(),
				allowBlank : false,
				maxValue : new Date()
			}, {
				xtype : 'container',
				border : false,
				html : '&nbsp;'
			}, {
				xtype : 'commonvehagencycompselector',
				fieldLabel : agency.PAPayable.i18n('foss.stl.agency.PAPayAndRec.PADetial'),
				name : 'customerDetial',
				allowBlank : true,
				listWidth : 300,// 设置下拉框宽度
				isPaging : true
			// 分页
			}, {
				xtype : 'combobox',
				name : 'approveStatus',
				fieldLabel : agency.PAPayable.i18n('foss.stl.agency.airPayAndRec.approveStatus'),
				editable : false,
				store : FossDataDictionary.getDataDictionaryStore(settlementDict.BILL_PAYABLE__APPROVE_STATUS, null, {
					'valueCode' : '',
					'valueName' : '全部'
				}),
				queryModel : 'local',
				displayField : 'valueName',
				valueField : 'valueCode',
				value : ''
			}, {
				xtype : 'container',
				border : false,
				html : '&nbsp;'
			}, {
				border : 1,
				xtype : 'container',
				colspan : 3,
				defaultType : 'button',
				layout : 'column',
				items : [ {
					text : agency.PAPayable.i18n('foss.stl.agency.common.reset'),
					columnWidth : .15,
					handler : function() {
						this.up('form').getForm().reset();
					}
				}, {
					xtype : 'container',
					border : false,
					columnWidth : .69,
					html : '&nbsp;'
				}, {
					text : agency.PAPayable.i18n('foss.stl.agency.common.query'),
					cls : 'yellow_button',
					columnWidth : .15,
					handler : agency.PAPayable.queryBillPayableEntityParams
				} ]
			} ]
		} ]
	}, {
		title : agency.PAPayable.i18n('foss.stl.agency.airPayAndRec.queryByPayNo'),
		tabConfig : {
			width : 140
		},
		items : [ {
			xtype : 'form',
			defaults : {
				margin : '10 0 0 0',
				labelWidth : 85
			},
			layout : {
				type : 'table',
				columns : 3
			},
			items : [ {
				xtype : 'textarea',
				autoScroll : true,
				emptyText : agency.PAPayable.i18n('foss.stl.agency.airPayAndRec.emptyText'),
				fieldLabel : agency.PAPayable.i18n('foss.stl.agency.payable.payableNo'),
				name : 'payableNos',
				height : 85,
				width : 480,
				allowBlank : false,
				colspan : 3
			}, {
				border : 1,
				xtype : 'container',
				columnWidth : 1,
				defaultType : 'button',
				layout : 'column',
				colspan : 3,
				items : [ {
					text : agency.PAPayable.i18n('foss.stl.agency.common.reset'),
					columnWidth : .15,
					handler : function() {
						this.up('form').getForm().reset();
					}
				}, {
					xtype : 'container',
					border : false,
					html : '&nbsp;',
					columnWidth : .7
				}, {
					text : agency.PAPayable.i18n('foss.stl.agency.common.query'),
					cls : 'yellow_button',
					columnWidth : .15,
					handler : agency.PAPayable.queryByPayableNOs
				} ]
			} ]
		} ]
	}, {
		title : agency.PAPayable.i18n('foss.stl.agency.common.queryBySourceNo'),
		tabConfig : {
			width : 180
		},
		items : [ {
			xtype : 'form',
			defaults : {
				margin : '10 0 0 5',
				labelWidth : 85
			},
			layout : {
				type : 'table',
				columns : 3
			},
			items : [ {
				xtype : 'textarea',
				autoScroll : true,
				emptyText : agency.PAPayable.i18n('foss.stl.agency.airPayAndRec.emptyText'),
				fieldLabel : agency.PAPayable.i18n('foss.stl.agency.airPayAndRec.billNo'),
				name : 'sourceBillNos',
				height : 85,
				width : 480,
				allowBlank : false,
				colspan : 2
			}, {
				xtype : 'container',
				items : [ {
					xtype : 'component',
					padding : '0 0 0 10',
					autoEl : {
						tag : 'div',
						html : '<span style="color:red;">' + agency.PAPayable.i18n('foss.stl.agency.airPayAndRec.sourceBillNosWaybills') + '</span>'
					}
				} ],
				colspan : 1
			}, {
				border : 1,
				xtype : 'container',
				columnWidth : 1,
				defaultType : 'button',
				layout : 'column',
				colspan : 2,
				items : [ {
					text : agency.PAPayable.i18n('foss.stl.agency.common.reset'),
					columnWidth : .15,
					handler : function() {
						this.up('form').getForm().reset();
						Ext.getCmp('Foss_StlPAPayable_StlPayableQueryInfoGrid_Id').hide();
					}
				}, {
					xtype : 'container',
					border : false,
					html : '&nbsp;',
					columnWidth : .70
				}, {
					text : agency.PAPayable.i18n('foss.stl.agency.common.query'),
					cls : 'yellow_button',
					columnWidth : .15,
					handler : agency.PAPayable.queryBySourceBillNOs
				} ]
			}, {
				xtype : 'container',
				border : false,
				html : '&nbsp;',
				colspan : 1
			} ]
		} ]
	} ]
});

// 应付单列表
Ext.define('Foss.StlPAPayable.StlPayableQueryInfoGrid', {
	extend : 'Ext.grid.Panel',
	title : agency.PAPayable.i18n('foss.stl.agency.payable.payable'),
	frame : true,
	height : 400,
	// plugins:SoperateColumnEditing,
	selModel : Ext.create('Ext.selection.CheckboxModel'),
	store : Ext.create('Foss.StlPAPayable.BillPayableEntryStore'),
	columns : [ {
		xtype : 'actioncolumn',
		width : 73,
		text : agency.PAPayable.i18n('foss.stl.agency.common.actionColumn'),
		align : 'center',
		items : [ {
			iconCls : 'foss_icons_stl_auditing',
			tooltip : agency.PAPayable.i18n('foss.stl.agency.common.audit'),
			getClass : function(v, m, r, rowIndex) {
				if (r.get('approveStatus') == "AA" || !agency.PAPayable.isPermission('/stl-web/agency/auditPAOtherBillPayable.action')) {
					return 'statementBill_hide';
				} else {
					return 'foss_icons_stl_auditing';
				}
			},
			handler : function(grid, rowIndex, colIndex) {
				agency.PAPayable.auditPAOtherOneBillPayable(grid, rowIndex, colIndex)
			}
		}, {
			iconCls : 'foss_icons_stl_fauditing',
			tooltip : agency.PAPayable.i18n('foss.stl.agency.common.unAudit'),
			getClass : function(v, m, r, rowIndex) {
				if (r.get('approveStatus') == "NA" || !agency.PAPayable.isPermission('/stl-web/agency/reverseAuditPAOtherBillPayable.action')) {
					return 'statementBill_hide';
				} else {
					return 'foss_icons_stl_fauditing';
				}
			},
			handler : function(grid, rowIndex, colIndex) {
				agency.PAPayable.reverseAuditPAOtherOneBillPayable(grid, rowIndex, colIndex)
			}
		}, {
			iconCls : 'deppon_icons_cancel',
			tooltip : agency.PAPayable.i18n('foss.stl.agency.common.disable'),
			getClass : function(v, m, r, rowIndex) {
				if (!agency.PAPayable.isPermission('/stl-web/agency/writeBackPAOtherBillPayable.action')) {
					return 'statementBill_hide';
				} else {
					return 'deppon_icons_cancel';
				}
			},
			handler : function(grid, rowIndex, colIndex) {
				agency.PAPayable.writeBackPAOtherOnePayable(grid, rowIndex, colIndex)
			}
		} ]
	}, {
		header : agency.PAPayable.i18n('foss.stl.agency.payable.payableNo'),
		dataIndex : 'payableNo'
	}, {
		header : agency.PAPayable.i18n('foss.stl.agency.airPayAndRec.billNo'),
		dataIndex : 'sourceBillNo'
	}, {
		header : agency.PAPayable.i18n('foss.stl.agency.PAPayAndRec.customerCode'),
		dataIndex : 'customerCode'
	}, {
		header : agency.PAPayable.i18n('foss.stl.agency.PAPayAndRec.customerName'),
		dataIndex : 'customerName'
	}, {
		header : agency.PAPayable.i18n('foss.stl.agency.airPayAndRec.approveStatus'),
		dataIndex : 'approveStatus',
		renderer : function(value) {
			var displayField = FossDataDictionary.rendererSubmitToDisplay(value, settlementDict.BILL_PAYABLE__APPROVE_STATUS);
			return displayField;
		}
	}, {
		header : agency.PAPayable.i18n('foss.stl.agency.airPayAndRec.origOrgCode'),
		hidden : true,
		dataIndex : 'origOrgCode'
	}, {
		header : agency.PAPayable.i18n('foss.stl.agency.airPayAndRec.origOrgName'),
		dataIndex : 'origOrgName'
	}, {
		header : agency.PAPayable.i18n('foss.stl.agency.airPayAndRec.amount'),
		dataIndex : 'amount'
	}, {
		header : agency.PAPayable.i18n('foss.stl.agency.airPayAndRec.verifyAmount'),
		dataIndex : 'verifyAmount'
	}, {
		header : agency.PAPayable.i18n('foss.stl.agency.airPayAndRec.unverifyAmount'),
		dataIndex : 'unverifyAmount'
	}, {
		header : agency.PAPayable.i18n('foss.stl.agency.airPayAndRec.businessDate'),
		dataIndex : 'businessDate',
		renderer : function(value) {
			if (value != null) {
				return Ext.Date.format(new Date(value), 'Y-m-d');
			} else {
				return null;
			}
		}
	}, {
		header : agency.PAPayable.i18n('foss.stl.agency.airPayAndRec.accountDate'),
		dataIndex : 'accountDate',
		renderer : function(value) {
			if (value != null) {
				return Ext.Date.format(new Date(value), 'Y-m-d');
			} else {
				return null;
			}
		}
	}, {
		header : agency.PAPayable.i18n('foss.stl.agency.airPayAndRec.auditDate'),
		dataIndex : 'auditDate',
		renderer : function(value) {
			if (value != null) {
				return Ext.Date.format(new Date(value), 'Y-m-d');
			} else {
				return null;
			}
		}
	}, {
		header : agency.PAPayable.i18n('foss.stl.agency.airPayAndRec.notes'),
		dataIndex : 'notes'
	}, {
		header : agency.PAPayable.i18n('foss.stl.agency.airPayAndRec.statementBillNo'),
		dataIndex : 'statementBillNo',
		hidden : true
	} ],
	viewConfig : {
		enableTextSelection : true
	// 设置行可以选择，进而可以复制
	},
	initComponent : function() {
		var me = this;
		me.dockedItems = [ {
			xtype : 'toolbar',
			dock : 'top',
			layout : 'column',
			items : [ {
				xtype : 'button',
				columnWidth : .1,
				text : agency.PAPayable.i18n('foss.stl.agency.common.export'),
				disabled : !agency.PAPayable.isPermission('/stl-web/agency/PAPayableListExport.action'),
				hidden : !agency.PAPayable.isPermission('/stl-web/agency/PAPayableListExport.action'),
				handler : agency.PAPayable.PAPayableListExport
			} ]
		}, {
			xtype : 'toolbar',
			dock : 'bottom',
			layout : 'column',
			items : [ {
				height : 5,
				columnWidth : 1
			}, {
				xtype : 'displayfield',
				allowBlank : true,
				id : 'Foss_StlPAPayable_StlPAPayableQueryInfoGrid_PAPayableTotalRows_Id',
				columnWidth : .15,
				fieldLabel : agency.PAPayable.i18n('foss.stl.agency.common.sum'),
				labelWidth : 50
			}, {
				xtype : 'container',
				border : false,
				html : '&nbsp;',
				columnWidth : .005
			}, {
				xtype : 'displayfield',
				allowBlank : true,
				id : 'Foss_StlPAPayable_StlPAPayableQueryInfoGrid_PAPayableTotalAmount_Id',
				columnWidth : .15,
				fieldLabel : agency.PAPayable.i18n('foss.stl.agency.common.totalAmount'),
				labelWidth : 50
			}, {
				xtype : 'container',
				border : false,
				html : '&nbsp;',
				columnWidth : .01
			}, {
				xtype : 'displayfield',
				allowBlank : true,
				id : 'Foss_StlPAPayable_StlPAPayableQueryInfoGrid_PAPayableVerifyAmount_Id',
				columnWidth : .15,
				fieldLabel : agency.PAPayable.i18n('foss.stl.agency.airPayAndRec.verifyAmount'),
				labelWidth : 80
			}, {
				xtype : 'container',
				border : false,
				html : '&nbsp;',
				columnWidth : .05
			}, {
				xtype : 'displayfield',
				allowBlank : true,
				id : 'Foss_StlPAPayable_StlPAPayableQueryInfoGrid_PAPayableUnverifyAmount_Id',
				columnWidth : .15,
				fieldLabel : agency.PAPayable.i18n('foss.stl.agency.airPayAndRec.unverifyAmount'),
				labelWidth : 80
			}, {
				xtype : 'container',
				border : false,
				height : 24,
				html : '&nbsp;',
				columnWidth : .4
			}, {
				xtype : 'container',
				border : false,
				html : '&nbsp;',
				columnWidth : .4
			}, {
				xtype : 'standardpaging',
				store : me.store,
				columnWidth : .55,
				plugins : Ext.create('Deppon.ux.PageSizePlugin', {
					// 设置分页记录最大值，防止输入过大的数值
					maximumSize : 200
				})
			}, {
				xtype : 'container',
				border : false,
				html : '&nbsp;',
				columnWidth : .6
			}, {
				xtype : 'button',
				text : agency.PAPayable.i18n('foss.stl.agency.common.disable'),
				disabled : !agency.PAPayable.isPermission('/stl-web/agency/writeBackPAOtherBillPayable.action'),
				hidden : !agency.PAPayable.isPermission('/stl-web/agency/writeBackPAOtherBillPayable.action'),
				columnWidth : .1,
				handler : agency.PAPayable.writeBackPAOtherBillPayable
			}, {
				xtype : 'container',
				border : false,
				html : '&nbsp;',
				columnWidth : .005
			}, {
				xtype : 'button',
				text : agency.PAPayable.i18n('foss.stl.agency.common.audit'),
				disabled : !agency.PAPayable.isPermission('/stl-web/agency/auditPAOtherBillPayable.action'),
				hidden : !agency.PAPayable.isPermission('/stl-web/agency/auditPAOtherBillPayable.action'),
				columnWidth : .1,
				handler : agency.PAPayable.auditPAOtherBillPayable
			}, {
				xtype : 'container',
				border : false,
				html : '&nbsp;',
				columnWidth : .005
			}, {
				xtype : 'button',
				text : agency.PAPayable.i18n('foss.stl.agency.common.unAudit'),
				disabled : !agency.PAPayable.isPermission('/stl-web/agency/reverseAuditPAOtherBillPayable.action'),
				hidden : !agency.PAPayable.isPermission('/stl-web/agency/reverseAuditPAOtherBillPayable.action'),
				columnWidth : .1,
				handler : agency.PAPayable.reverseAuditPAOtherBillPayable
			} ]
		} ]
		me.callParent();
	}
});

// 初始化界面
Ext.onReady(function() {
	Ext.QuickTips.init();

	var StlPAPayableQueryInfoTab = Ext.create('Foss.StlPAPayable.StlPAPayableQueryInfoTab');

	var StlPayableQueryInfoGrid = Ext.create('Foss.StlPAPayable.StlPayableQueryInfoGrid', {
		id : 'Foss_StlPAPayable_StlPayableQueryInfoGrid_Id'
	});

	Ext.create('Ext.panel.Panel', {
		id : 'T_agency-PAPayable_content',
		cls : "panelContentNToolbar",
		bodyCls : 'panelContentNToolbar-body',
		getQueryInfoTab : function() {
			return StlPAPayableQueryInfoTab;
		},
		layout : 'auto',
		items : [ StlPAPayableQueryInfoTab, StlPayableQueryInfoGrid ],
		renderTo : 'T_agency-PAPayable-body'
	});
});