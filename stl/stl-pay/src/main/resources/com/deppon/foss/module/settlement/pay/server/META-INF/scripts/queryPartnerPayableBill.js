pay.queryPartnerPayableBill.costType_SALE = "JY";//经营
/**
 * 获取费用承担部门
 */
pay.queryPartnerPayableBill.costTypeDatas = FossDataDictionary.getDataByTermsCode('RENTCAR_COST_TYPE');

/**
 * 
 * @param {}
 *            产品类型
 * @return {}此处如果有全选，则需要转化下
 */
pay.queryPartnerPayableBill.convertProductCode = function(productCodes) {
	if (!Ext.isEmpty(productCodes)) {
		var productCodeList = [];
		for (var i = 0; i < productCodes.length; i++) {
			// 如果产品类型中存在值为空，则表明为全部，那么默认全部查询
			if (Ext.isEmpty(productCodes[i])) {
				productCodeList = [];
				break;
			} else {
				productCodeList.push(productCodes[i]);
			}
		}
		return productCodeList;
	} else {
		return [];
	}
}

//按应付单查询应付单明细
pay.queryPartnerPayableBill.queryDetailByPayableNo = function(grid, rowIndex, colIndex){
	//获取表头数据
	var selection = grid.getStore().getAt(rowIndex);
	//获取grid
	var payableDetailGrid = Ext.getCmp('T_pay-queryPartnerPayableBill_content').getPayableDetailGrid();
	//封装参数
	pay.queryPartnerPayableBill.detailParams = {
			"payableDetailVO.payableNo" : selection.data.payableNo
		};
	//获取URL
	var queryPayableDetailUrl = pay.realPath('queryPayableDetail.action');
	//获取明细store
	var payableDetailGridStore = payableDetailGrid.store;
	//设置url
	payableDetailGridStore.getProxy().url = queryPayableDetailUrl;
	//查询
	payableDetailGridStore.loadPage(1, {
		callback: function(records, operation, success) {
			var rawData = payableDetailGridStore.proxy.reader.rawData;
			if(!success){
				var result = Ext.decode(response.responseText);	
				Ext.Msg.alert(pay.queryPartnerPayableBill.i18n('foss.stl.pay.common.alert'), result.message);
				return false;
			}
			//如果成功显示
			if(success){
				//对结果进行转化
				var result = Ext.decode(operation.response.responseText);
				//判断查询结果
				if(Ext.isEmpty(result.vo.resultDto.list) && result.vo.resultDto.list.length == 0){
					Ext.Msg.alert(pay.queryPartnerPayableBill.i18n('foss.stl.pay.common.alert'),
							pay.queryPartnerPayableBill.i18n('foss.stl.pay.common.noResult'));
					return false;
				}
			}
	    }
	});
}

/**
 * 查询成功
 */
pay.queryPartnerPayableBill.querySuccess = function(queryurl) {
	var payableGrid = Ext.getCmp('T_pay-queryPartnerPayableBill_content').getPayableGrid();
	var payableGridStore = payableGrid.store;
	payableGridStore.getProxy().url = queryurl;
	// 查询后台
	payableGridStore.loadPage(1, {
		callback : function(records, operation, success) {
			var rawData = payableGrid.store.proxy.reader.rawData;
			if (!success && !rawData.isException) {
				Ext.Msg.alert(pay.queryPartnerPayableBill.i18n('foss.stl.pay.common.alert'), rawData.message);
				return false;
			}
			if (success) {
				var result = Ext.decode(operation.response.responseText);
				if(Ext.isEmpty(result.vo.resultDto.list) && result.vo.resultDto.list.length == 0){
					Ext.Msg.alert(pay.queryPartnerPayableBill.i18n('foss.stl.pay.common.alert'),
							pay.queryPartnerPayableBill.i18n('foss.stl.pay.common.noResult'));
					return false;
				} else {
					var tbars = payableGrid.dockedItems.items[3].query('textfield');
					tbars[0].setValue(result.vo.resultDto.totalCount);
					tbars[1].setValue(result.vo.resultDto.totalAmount);
					tbars[2].setValue(result.vo.resultDto.totalVerifyAmount);
					tbars[3].setValue(result.vo.resultDto.totalUnVerifyAmount);
				}
			}
		}
	});
}

/**
 * 清空表格
 */
pay.queryPartnerPayableBill.clearGrid = function() {
	var payableGrid = Ext.getCmp('T_pay-queryPartnerPayableBill_content').getPayableGrid();
	payableGrid.store.removeAll();
	var tbars = payableGrid.dockedItems.items[3].query('textfield');
	tbars[0].setValue(0);
	tbars[1].setValue(0);
	tbars[2].setValue(0);
	tbars[3].setValue(0);
}

/**
 * 按日期查询
 */
pay.queryPartnerPayableBill.queryByDate = function(form, me) {

	// 判断是否合法
	if (form.isValid()) {

		// 判断用户是选择的业务日期或者记账日期
		var selectDateFlagTemp = form.getValues().selectDateFlag;
		var startDate = form.findField('startDate').getValue();
		var endDate = form.findField('endDate').getValue();

		// 比较起始日期和结束日期
		var compareTwoDate = stl.compareTwoDate(startDate, endDate);
		if (compareTwoDate > stl.DATELIMITDAYS_MONTH) {
			Ext.Msg.alert(pay.queryPartnerPayableBill.i18n('foss.stl.pay.common.alert'), 
					pay.queryPartnerPayableBill.i18n('foss.stl.pay.queryBillPayable.queryDateMaxLimit'));
			return false;
		} else if (compareTwoDate < 1) {
			Ext.Msg.alert(pay.queryPartnerPayableBill.i18n('foss.stl.pay.common.alert'), 
					pay.queryPartnerPayableBill.i18n('foss.stl.pay.common.dateEndBeforeStatrtWarining'));
			return false;
		}

		var businessStartDateTemp;
		var businessEndDateTemp;
		var accountStartDateTemp;
		var accountEndDateTemp;
		// 判断是根据业务日期查询或者是按照记账业务查询 1=按业务日期 2记账日期
		if ('1' == selectDateFlagTemp) {
			businessStartDateTemp = form.findField('startDate').getValue();
			businessEndDateTemp = form.findField('endDate').getValue();
		} else {
			accountStartDateTemp = form.findField('startDate').getValue();
			accountEndDateTemp = form.findField('endDate').getValue();
		}
		pay.queryPartnerPayableBill.customerCode = form.findField('customerCode').getValue();
		pay.queryPartnerPayableBill.payableOrgCode = form.findField('payableOrgCode').getValue();
		pay.queryPartnerPayableBill.largeRegion = form.findField('largeRegion').getValue();
		pay.queryPartnerPayableBill.businessBeginDate = businessStartDateTemp;
		pay.queryPartnerPayableBill.businessEndDate = businessEndDateTemp;
		pay.queryPartnerPayableBill.accountBeginDate = accountStartDateTemp;
		pay.queryPartnerPayableBill.accountEndDate = accountEndDateTemp;
		pay.queryPartnerPayableBill.queryDateFlag = selectDateFlagTemp;
		pay.queryPartnerPayableBill.isSign = form.findField('isSign').getValue();
		pay.queryPartnerPayableBill.smallRegion = form.findField('smallRegion').getValue();
		pay.queryPartnerPayableBill.effectiveStatus = form.findField('effectiveStatus').getValue();
		pay.queryPartnerPayableBill.payStatus = form.findField('payStatus').getValue();
		pay.queryPartnerPayableBill.billType = form.findField('billType').getValue();
		pay.queryPartnerPayableBill.writeoffStatus = form.findField('writeoffStatus').getValue();
		pay.queryPartnerPayableBill.approveStatus = form.findField('approveStatus').getValue();
		pay.queryPartnerPayableBill.active = form.findField('active').getValue();
		pay.queryPartnerPayableBill.productCode = form.findField('productCode').getValue();

		pay.queryPartnerPayableBill.queryTab = 'TD';

		if (Ext.isEmpty(pay.queryPartnerPayableBill.largeRegion) 
				&& Ext.isEmpty(pay.queryPartnerPayableBill.smallRegion) 
				&& Ext.isEmpty(pay.queryPartnerPayableBill.payableOrgCode)) {
			Ext.Msg.alert(pay.queryPartnerPayableBill.i18n('foss.stl.pay.common.alert'), 
					pay.queryPartnerPayableBill.i18n('foss.stl.pay.common.validateRegionFailAlert'));
			return false;
		}

		var queryPayableBillByDateUrl = pay.realPath('queryBillPayable.action');
		// 去后台查询
		pay.queryPartnerPayableBill.clearGrid();
		pay.queryPartnerPayableBill.querySuccess(queryPayableBillByDateUrl);
	} else {
		Ext.Msg.alert(pay.queryPartnerPayableBill.i18n('foss.stl.pay.common.alert'), 
				pay.queryPartnerPayableBill.i18n('foss.stl.pay.common.validateFailAlert'));
		return false;
	}
}
/**
 * 按单号查询
 */
pay.queryPartnerPayableBill.queryByNumber = function(form, me) {
	// 判断是否合法
	if (form.isValid()) {
		var billNosArray = [];
		var billNos = form.findField('billNos').getValue();
		if (Ext.String.trim(billNos) != null && Ext.String.trim(billNos) != '') {
			var array = stl.splitToArray(billNos);
			for (var i = 0; i < array.length; i++) {
				if (Ext.String.trim(array[i]) != '') {
					billNosArray.push(array[i]);
				}
			}
			if (billNosArray.length > 10) {
				Ext.Msg.alert(pay.queryPartnerPayableBill.i18n('foss.stl.pay.common.alert'), pay.queryPartnerPayableBill
								.i18n('foss.stl.pay.common.queryNosLimit'));
				return false;
			}
		} else {
			Ext.Msg.alert(pay.queryPartnerPayableBill.i18n('foss.stl.pay.common.alert'), pay.queryPartnerPayableBill
							.i18n('foss.stl.pay.queryBillPayable.nosNotNull'));
			return false;
		}
		pay.queryPartnerPayableBill.queryTab = 'TB';
		pay.queryPartnerPayableBill.billNosArray = billNosArray;
		var queryPayableBillByBillNoUrl = pay.realPath('queryBillPayable.action');
		// 去后台查询
		pay.queryPartnerPayableBill.clearGrid();
		pay.queryPartnerPayableBill.querySuccess(queryPayableBillByBillNoUrl);
	} else {
		Ext.Msg.alert(pay.queryPartnerPayableBill.i18n('foss.stl.pay.common.alert'), pay.queryPartnerPayableBill.i18n('foss.stl.pay.common.validateFailAlert'));
		return false;
	}
}

/**
 * 按来源单号查询
 */
pay.queryPartnerPayableBill.queryBySourceNumber = function(form, me) {
	// 判断是否合法
	if (form.isValid()) {
		//设置来源单号集合
		var sourceBillNosArray = [];
		var billNos = form.findField('billNos').getValue();
		if (Ext.String.trim(billNos) != null && Ext.String.trim(billNos) != '') {
			var array = stl.splitToArray(billNos);
			for (var i = 0; i < array.length; i++) {
				if (Ext.String.trim(array[i]) != '') {
					sourceBillNosArray.push(array[i]);
				}
			}
			if (sourceBillNosArray.length > 10) {
				Ext.Msg.alert(pay.queryPartnerPayableBill.i18n('foss.stl.pay.common.alert'), pay.queryPartnerPayableBill
								.i18n('foss.stl.pay.common.queryNosLimit'));
				return false;
			}
		} else {
			Ext.Msg.alert(pay.queryPartnerPayableBill.i18n('foss.stl.pay.common.alert'), pay.queryPartnerPayableBill
							.i18n('foss.stl.pay.queryBillPayable.nosNotNull'));
			return false;
		}
		// 按来源单号查询
		pay.queryPartnerPayableBill.queryTab = 'TSB';
		pay.queryPartnerPayableBill.sourceBillNosArray = sourceBillNosArray;
		var queryPayableBillBySourceBillNoUrl = pay.realPath('queryBillPayable.action');
		// 去后台查询
		pay.queryPartnerPayableBill.clearGrid();
		pay.queryPartnerPayableBill.querySuccess(queryPayableBillBySourceBillNoUrl);
	} else {
		Ext.Msg.alert(pay.queryPartnerPayableBill.i18n('foss.stl.pay.common.alert'), pay.queryPartnerPayableBill.i18n('foss.stl.pay.common.validateFailAlert'));
		return false;
	}
}

//将字符串转换为时间格式,适用各种浏览器,格式如2016-01-01 12:00:00
pay.queryPartnerPayableBill.getTimeByTimeStr = function(dateStr) {
	var timeArr=dateStr.split(" ");
	var d=timeArr[0].split("-");
	var t=timeArr[1].split(":");
	return new Date(d[0],(d[1]-1),d[2],t[0],t[1],t[2]);    
}

/**
 * 判断是否能付款
 */
pay.queryPartnerPayableBill.validator = function(selection) {
	//签收时间
	var signTime = selection.get('signDate');
	//自动付款开始时间
	var dateValue = pay.queryPartnerPayableBill.getTimeByTimeStr('2016-06-18 23:59:59');
	
	//如果是合伙人到付应付不能付款(在合伙人自动付款开始运行之后签收的运单，不能再页面手动付款，之前签收的可以手动申请付款)
	if (selection.get('billType')=='PFCP' && (signTime>dateValue)) {
		Ext.Msg.alert(pay.queryPartnerPayableBill.i18n('foss.stl.pay.common.alert'), 
				pay.queryPartnerPayableBill.i18n('foss.stl.pay.queryBillPayable.payableNo')
						+ ':' + selection.get('payableNo')
						+ pay.queryPartnerPayableBill.i18n('foss.stl.pay.queryBillPayable.pfcpPayToPayWarning'));
		return false;
	}
	// 无效应付单不能进行审核
	if (Ext.isEmpty(selection.get('active')) || selection.get('active') != 'Y') {
		Ext.Msg.alert(pay.queryPartnerPayableBill.i18n('foss.stl.pay.common.alert'), pay.queryPartnerPayableBill.i18n('foss.stl.pay.queryBillPayable.payableNo')
						+ ':' + selection.get('payableNo')
						+ pay.queryPartnerPayableBill.i18n('foss.stl.pay.queryBillPayable.notEffectiveToPayWarning'));
		return false;
	}

	// 代收货款应付不能进行付款
	if (selection.get('billType') == 'APC') {
		Ext.Msg.alert(pay.queryPartnerPayableBill.i18n('foss.stl.pay.common.alert'), 
				pay.queryPartnerPayableBill.i18n('foss.stl.pay.queryBillPayable.payableNo')
						+ ':' + selection.get('payableNo') + pay.queryPartnerPayableBill.i18n('foss.stl.pay.queryBillPayable.codPayWarning'));
		return false;
	}
	// 应付单号为空的不能进行付款
	if (Ext.isEmpty(selection.get('payableNo'))) {
		Ext.Msg.alert(pay.queryPartnerPayableBill.i18n('foss.stl.pay.common.alert'), 
				pay.queryPartnerPayableBill.i18n('foss.stl.pay.queryBillPayable.waybillNo')
						+ ':' + selection.get('waybillNo')
						+ pay.queryPartnerPayableBill.i18n('foss.stl.pay.queryBillPayable.payableNoIsNullToPayWarning'));
		return false;
	}
	// 已经完全核销的不能进行付款
	if (selection.get('unverifyAmount') == 0) {
		Ext.Msg.alert(pay.queryPartnerPayableBill.i18n('foss.stl.pay.common.alert'), 
				pay.queryPartnerPayableBill.i18n('foss.stl.pay.queryBillPayable.payableNo')
						+ ':' + selection.get('payableNo')
						+ pay.queryPartnerPayableBill.i18n('foss.stl.pay.queryBillPayable.verifiedToPayWarning'));
		return false;
	}
	// 如果为未生效，则提示
	if (selection.get('effectiveStatus') == 'N') {
		Ext.Msg.alert(pay.queryPartnerPayableBill.i18n('foss.stl.pay.common.alert'), 
				pay.queryPartnerPayableBill.i18n('foss.stl.pay.queryBillPayable.payableNo')
						+ ':' + selection.get('payableNo')
						+ pay.queryPartnerPayableBill.i18n('foss.stl.pay.queryBillPayable.unEffectiveToPayWarning'));
		return false;
	}
	// 如果是到达提成应付不能付款
	if (selection.get('billType')=='PDFP') {
		Ext.Msg.alert(pay.queryPartnerPayableBill.i18n('foss.stl.pay.common.alert'), 
				pay.queryPartnerPayableBill.i18n('foss.stl.pay.queryBillPayable.payableNo')
						+ ':' + selection.get('payableNo')
						+ pay.queryPartnerPayableBill.i18n('foss.stl.pay.queryBillPayable.deliveryFeeToPayWarning'));
		return false;
	}
	//如果是委托派费应付不能付款
	if (selection.get('billType')=='PDDF') {
		Ext.Msg.alert(pay.queryPartnerPayableBill.i18n('foss.stl.pay.common.alert'), 
				pay.queryPartnerPayableBill.i18n('foss.stl.pay.queryBillPayable.payableNo')
						+ ':' + selection.get('payableNo')
						+ pay.queryPartnerPayableBill.i18n('foss.stl.pay.queryBillPayable.deliveryFeeToPayWarning'));
		return false;
	}
	return true;
}

/**
 * 校验通过后，进行付款操作
 */
pay.queryPartnerPayableBill.validatorSuccess = function(selections) {
	// 发送请求传递信息到后台
	var vo = new Object();
	var manageDto = new Object();
	manageDto.billNos = selections;
	manageDto.containRentCar = pay.queryPartnerPayableBill.containRentCar;
	vo.manageDto = manageDto;

	Ext.Ajax.request({
				url : pay.realPath('payForBillPayable.action'),
				jsonData : {
					'vo' : vo
				},
				actionMethods : 'post',
				success : function(response) {
					var result = Ext.decode(response.responseText);
					// 获取付款单窗口
					if (Ext.isEmpty(pay.queryPartnerPayableBill.addPaymentWindow)) {
						pay.queryPartnerPayableBill.addPaymentWindow = Ext.create('Foss.payment.AddPaymentWindow');
					}
					var win = pay.queryPartnerPayableBill.addPaymentWindow;
					var formModel = new Foss.payable.AddFormModel(result.vo.resultDto.paymentEntity);
					// 设置付款单弹出界面来源界面 为应付单
					formModel.set('sourceBillType', pay.SOURCE_BILL_TYPE__ADVANCED_PAYMENT);// 应付
					pay.payFromPage = pay.SOURCE_BILL_TYPE__ADVANCED_PAYMENT;
					win.getAddFormPanel().loadRecord(formModel);
					var payForm = win.getAddFormPanel().getForm();
					// 如果账户非空，将其绑定到银行账户控件上 --理赔
					if (!Ext.isEmpty(formModel.get('accountNo'))) {
						payForm.findField('accountNo').setCombValue(formModel.get('payeeName'), formModel.get('accountNo'));
						// 手动设置账户类型值，单传的loadRecord不会进行转化
						payForm.findField('accountTypeName').setValue(FossDataDictionary.rendererSubmitToDisplay(formModel
										.get('accountType'), settlementDict.FIN_ACCOUNT_TYPE));
						payForm.findField('accountNo').setReadOnly(true);
						// 设置银行账号从理赔带过来标志
						payForm.findField('claimAccountNo').setValue(formModel.get('accountNo'));
					} else {
						payForm.findField('accountNo').setReadOnly(false);
						// 设置银行账号从理赔带过来标志
						payForm.findField('claimAccountNo').setValue(null);
					}
					// 绑定发票抬头 为默认
					var comCode = payForm.findField('paymentCompanyCode').getValue();
					var comName = payForm.findField('paymentCompanyName').getValue();
					// 获取发票抬头
					if (!Ext.isEmpty(comCode) && !Ext.isEmpty(comName)) {
						payForm.findField('invoiceHeadCode').setCombValue(comName, comCode);
					}
					var addpaymentEntryGrid = win.getAddPaymentEntryGrid();
					addpaymentEntryGrid.store.loadData(result.vo.resultDto.addDtoList);
					//如果为临时租车，则界面需要做相应处理
					//获取费用承担部门   
					var costDeptCode = result.vo.resultDto.paymentEntity.costDeptCode;
					var costDeptName = result.vo.resultDto.paymentEntity.costDeptName;
					var costDeptType = result.vo.resultDto.costDeptType;
					
					//获取业务发生日期。临时租车要控制编辑和不可编辑
					var invoceTax = addpaymentEntryGrid.columns[7];
					var tax = addpaymentEntryGrid.columns[8];
					var businessOfDate = addpaymentEntryGrid.columns[9];
					
					pay.businessOfDateEdit = 'Y';
					pay.containRentCar = null;
					businessOfDate.setEditor(null);//设置不可编辑
					payForm.findField('costDeptCode').hide();
					payForm.findField('costType').hide();
					payForm.findField('displayfield1').hide();
					payForm.findField('isTaxinvoice').hide();
					payForm.findField('costDeptCode').setValue(null);
					invoceTax.hide();tax.hide();businessOfDate.hide();
					
					// 弹出付款单
					pay.queryPartnerPayableBill.addPaymentWindow.show();
					
					// 如果应付单是代打木架，付款只能选择电汇方式
					if(result.vo.resultDto.addDtoList[0].billType == 'PFCP'){
						var tt = pay.SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER;
						win.items.items[0].items.items[6].store = FossDataDictionary.getDataDictionaryStore(settlementDict.SETTLEMENT__PAYMENT_TYPE,null,null,[tt]);
						win.items.items[0].items.items[6].setValue(tt);
						win.items.items[0].items.items[6].lastValue = tt;
						win.items.items[0].items.items[6].rawValue = tt;
						win.items.items[0].items.items[6].originalValue = tt;
					}
				},
				exception : function(response) {
					var result = Ext.decode(response.responseText);
					Ext.Msg.alert(pay.queryPartnerPayableBill.i18n('foss.stl.pay.common.alert'), result.message);
				}
			});
}

/**
 * 批量付款
 */
pay.queryPartnerPayableBill.payForBillPayableBatch = function() {
	var payableGrid, selection;
	// 获取表格组件
	payableGrid = Ext.getCmp('T_pay-queryPartnerPayableBill_content').getPayableGrid();
	selection = payableGrid.getSelectionModel().getSelection();
	// 如果没有选择数据，则提示
	if (selection.length == 0) {
		Ext.Msg.alert(pay.queryPartnerPayableBill.i18n('foss.stl.pay.common.alert'), 
				pay.queryPartnerPayableBill.i18n('foss.stl.pay.queryBillPayable.unSelectedToPayWarning'));
		return false;
	}
	// 声明付款record
	pay.queryPartnerPayableBill.paySelections = selection;
	var jsonData = [];
	var isNotRentcarFlag = false;
	var isRentcarFlag = false;
	// 循环校验
	for (var i = 0; i < selection.length; i++) {
		//付款校验
		var isValid = pay.queryPartnerPayableBill.validator(selection[i]);
		//判断是否为临时租车
		if(selection[i].get('billType')==pay.queryPartnerPayableBill.BILL_PAYABLE__BILL_TYPE__RENT_CAR){
			isRentcarFlag =true;
		}else{
			isNotRentcarFlag = true;
		}
		//临时租车不能和其他单据合并付款
		if(isNotRentcarFlag && isRentcarFlag){
			Ext.Msg.alert(pay.queryPartnerPayableBill.i18n('foss.stl.pay.common.alert'), 
					pay.queryPartnerPayableBill.i18n('foss.stl.pay.queryBillPayable.errorBillTypeToBatchPay'));
			return false;
		}
		// 如果有一条不过，则停止
		if (!isValid) {
			return false;
		}
		jsonData.push(selection[i].data.payableNo);
	}

	pay.queryPartnerPayableBill.validatorSuccess(jsonData);
}

/**ddw
 * 导出excel
 */
pay.queryPartnerPayableBill.payForBillExport = function() {
	var columns, searchParams, arrayColumns, arrayColumnNames, grid;
	// 获取表格
	grid = Ext.getCmp('T_pay-queryPartnerPayableBill_content').getPayableGrid();
	// 如果没有查询到数据，则不能进行导出操作
	if (grid.store.data.length == 0) {
		Ext.Msg.alert(pay.queryPartnerPayableBill.i18n('foss.stl.pay.common.alert'), 
				pay.queryPartnerPayableBill.i18n('foss.stl.pay.queryBillPayable.noDataToExport'));
		return false;
	}
	// 进行导出提示
	Ext.MessageBox.show({
		title : pay.queryPartnerPayableBill.i18n('foss.stl.pay.common.alert'),
		msg : pay.queryPartnerPayableBill.i18n('foss.stl.pay.queryBillPayable.isExport'),
		buttons : Ext.MessageBox.YESNO,
		fn : function(e) {
			if (!Ext.fly('downloadAttachFileForm')) {
				var frm = document.createElement('form');
				frm.id = 'downloadAttachFileForm';
				frm.style.display = 'none';
				document.body.appendChild(frm);
			}
			var payableGrid = Ext.getCmp('T_pay-queryPartnerPayableBill_content').getPayableGrid();
			if (e == 'yes') {
				// 转化列头和列明
				columns = payableGrid.columns;
				arrayColumns = [];
				arrayColumnNames = [];
				// 将前台对应列头传入到后台去
				for (var i = 1; i < columns.length; i++) {
					if (columns[i].isHidden() == false) {
						var hederName = columns[i].text;
						var dataIndex = columns[i].dataIndex;
						if (columns[i].text != '操作列') {
							arrayColumns.push(dataIndex);
							arrayColumnNames.push(hederName);
						}
					}
				}
				//封装参数
				var params;
				if(pay.queryPartnerPayableBill.queryTab == 'TD'){
					params = {
						'vo.manageDto.businessBeginDate' : pay.queryPartnerPayableBill.businessBeginDate,
						'vo.manageDto.payableOrgCode' : pay.queryPartnerPayableBill.payableOrgCode,
						'vo.manageDto.largeRegion' : pay.queryPartnerPayableBill.largeRegion,
						'vo.manageDto.businessEndDate' : pay.queryPartnerPayableBill.businessEndDate,
						'vo.manageDto.customerCode' : pay.queryPartnerPayableBill.customerCode,
						'vo.manageDto.smallRegion' : pay.queryPartnerPayableBill.smallRegion,
						'vo.manageDto.effectiveStatus' : pay.queryPartnerPayableBill.effectiveStatus,
						'vo.manageDto.payStatus' : pay.queryPartnerPayableBill.payStatus,
						'vo.manageDto.billType' : pay.queryPartnerPayableBill.convertProductCode(pay.queryPartnerPayableBill.billType),
						'vo.manageDto.writeoffStatus' : pay.queryPartnerPayableBill.writeoffStatus,
						'vo.manageDto.approveStatus' : pay.queryPartnerPayableBill.approveStatus,
						'vo.manageDto.active' : pay.queryPartnerPayableBill.active,
						'vo.manageDto.isSign' : pay.queryPartnerPayableBill.isSign,
						'vo.manageDto.queryTab' : pay.queryPartnerPayableBill.queryTab,
						'vo.manageDto.accountBeginDate' : pay.queryPartnerPayableBill.accountBeginDate,
						'vo.manageDto.accountEndDate' : pay.queryPartnerPayableBill.accountEndDate,
						'vo.manageDto.queryDateFlag' : pay.queryPartnerPayableBill.queryDateFlag,
						'vo.manageDto.productCodesList' : pay.queryPartnerPayableBill.convertProductCode(pay.queryPartnerPayableBill.productCode),
						'vo.manageDto.isPartner' : 'Y'
					};
				} else if(pay.queryPartnerPayableBill.queryTab == 'TB'){
					params = {
						'vo.manageDto.billNos' : pay.queryPartnerPayableBill.billNosArray,
						'vo.manageDto.queryTab' : pay.queryPartnerPayableBill.queryTab,
						'vo.manageDto.isPartner' : 'Y'
					};
				} else if(pay.queryPartnerPayableBill.queryTab == 'TSB'){
					params = {
						'vo.manageDto.billNos' : pay.queryPartnerPayableBill.sourceBillNosArray,
						'vo.manageDto.queryTab' : pay.queryPartnerPayableBill.queryTab,
						'vo.manageDto.isPartner' : 'Y'
					};
				}
				//导出列
				params['vo.manageDto.arrayColumns'] = arrayColumns;
				//导出列名称
				params['vo.manageDto.arrayColumnNames'] = arrayColumnNames;
				// 拼接vo，注入到后台
				Ext.Ajax.request({
					url : pay.realPath('exportBillPayable.action'),
					form : Ext.fly('downloadAttachFileForm'),
					method : 'post',
					params : params,
					isUpload : true,
					success : function(response) {
						var result = Ext.decode(response.responseText);
						// 如果异常信息有值，则弹出提示
						if (!Ext.isEmpty(result.errorMessage)) {
							Ext.Msg.alert(pay.queryPartnerPayableBill.i18n('foss.stl.pay.common.alert'), result.errorMessage);
							return false;
						}
						Ext.ux.Toast.msg(pay.queryPartnerPayableBill.i18n('foss.stl.pay.common.alert'), 
								pay.queryPartnerPayableBill.i18n('foss.stl.pay.queryBillPayable.exportSuccess'), 'success', 1000);
						},
						failure : function(response) {
							Ext.ux.Toast.msg(pay.queryPartnerPayableBill.i18n('foss.stl.pay.common.alert'), 
									pay.queryPartnerPayableBill.i18n('foss.stl.pay.queryBillPayable.exportFailed'), 'error', 1000);
						}
				});
			}
		}
	});
}

/**
 * 查看明细
 */
pay.queryPartnerPayableBill.queryDetail = function(grid, rowIndex, colIndex) {
	// 获取点击行数据
	var selection = grid.getStore().getAt(rowIndex);
	var payableGridEntry = Ext.getCmp('T_pay-queryPartnerPayableBill_content').getPayableGridEntry();
	payableGridEntry.store.removeAll();
	payableGridEntry.store.loadData([selection]);
}

/**
 * 付款完毕，回来刷新应付单界面。 手动修改应付单付款状态和版本号
 */
pay.queryPartnerPayableBill.refresh = function(ids) {
	// 如果id为空，则终止
	if (Ext.isEmpty(ids)) {
		return false;
	}
	var grid = Ext.getCmp('T_pay-queryPartnerPayableBill_content').getPayableGrid();
	if (!Ext.isEmpty(pay.queryPartnerPayableBill.paySelections)) {
		// 循环表格 获取与传入id一致的应付单record
		for (var i = 0; i < ids.length; i++) {
			// 循环表格
			for (var j = 0; j < pay.queryPartnerPayableBill.paySelections.length; j++) {
				var record = pay.queryPartnerPayableBill.paySelections[j];
				// 更新汇款状态为已付款
				record.set('payStatus', pay.queryPartnerPayableBill.BILL_PAYABLE__PAY_STATUS__YES);
				// 更新版本号
				record.set('versionNo', record.get('versionNo') + 1);
				record.commit();
				break;
			}
		}
	}
}

/**
 * 核销状态store
 */
Ext.define('Foss.PartnerPayableBill.PayableWriteoffStatus', {
			extend : 'Ext.data.Store',
			fields : ['valueName', 'valueCode'],
			data : [{
						valueName : pay.queryPartnerPayableBill.i18n('foss.stl.pay.queryBillPayable.writeoffed'),
						valueCode : 'WD'
					}, {
						valueName : pay.queryPartnerPayableBill.i18n('foss.stl.pay.queryBillPayable.partWriteoff'),
						valueCode : 'PW'
					}, {
						valueName : pay.queryPartnerPayableBill.i18n('foss.stl.pay.queryBillPayable.unWriteoff'),
						valueCode : 'NW'
					}, {
						valueName : pay.queryPartnerPayableBill.i18n('foss.stl.pay.queryBillPayable.all'),
						valueCode : ''
					}]
		});

/**
 * 表格模型
 */
Ext.define('Foss.PartnerPayableBill.PayableGridModel', {
			extend : 'Ext.data.Model',
			fields : [{
						name : 'id'
					}, {
						name : 'payableNo'
					}, {
						name : 'waybillNo'
					}, {
						name : 'payableOrgCode'
					}, {
						name : 'payableOrgName'
					}, {
						name : 'createOrgName'
					}, {
						name : 'customerCode'
					}, {
						name : 'customerName'
					}, {
						name : 'amount',
						type : 'double'
					}, {
						name : 'verifyAmount',
						type : 'double'
					}, {
						name : 'unverifyAmount',
						type : 'double'
					}, {
						name : 'businessDate',
						type : 'date',
						convert : stl.longToDateConvert
					}, {
						name : 'accountDate',
						type : 'date',
						convert : stl.longToDateConvert
					}, {
						name : 'effectiveDate',
						type : 'date',
						convert : stl.longToDateConvert
					}, {
						name : 'billType'
					}, {
						name : 'frozenStatus'
					}, {
						name : 'payStatus'
					}, {
						name : 'effectiveStatus'
					}, {
						name : 'createUserName'
					}, {
						name : 'versionNo',
						type : 'int'
					}, {
						name : 'active'
					}, {
						name : 'isRedBack'
					}, {
						name : 'createTime',
						type : 'date',
						convert : stl.longToDateConvert
					}, {
						name : 'modifyTime',
						type : 'date',
						convert : stl.longToDateConvert
					}, {
						name : 'createUserName'
					}, {
						name : 'approveStatus'
					}, {
						name : 'createOrgName'// 制单部门
					}, {
						name : 'frozenStatus'// 冻结状态
					}, {
						name : 'sourceBillNo'// 来源单据编号
					}, {
						name : 'paymentAmount'// 付款金额
					}, {
						name : 'customerContactName'// 联系人
					}, {
						name : 'customerPhone'// 联系人电话
					}, {
						name : 'paymentCategories'// 联系人电话
					}, {
						name : 'payableType'// 应付方式
					}, {
						name : 'paymentNo'// 付款编号
					}, {
						name : 'createType'// 生成方式
					}, {
						name : 'signDate',
						type : 'date',
						convert : stl.longToDateConvert
						// 签收日期
					}, {
						name : 'statementBillNo'// 对账单号
					}, {
						name : 'notes'// 备注
					}, {
						name : 'productCode'
					}, {
						name : 'expressOrigOrgCode'
					}, {
						name : 'expressOrigOrgName'
					}, {
						name : 'expressDestOrgCode'
					}, {
						name : 'expressDestOrgName'
					}, {
						name : 'invoiceMark'
					}, {
						name : 'workflowNo'
					},{
                        name:'unifiedSettlement',
                        type:'string'
                    },{
                        name:'contractOrgCode',
                        type:'string'
                    },{
                        name:'contractOrgName',
                        type:'string'
                     },{
                         name:'paymentWorkflowNo',
                         type:'string'
                      },{
                          name:'paymentTransfer',
                          type:'string'
                       }]
		});
/**
 * 应收单明细模型
 */
Ext.define('Foss.PartnerPayableBill.PayableDetailGridModel', {
	extend : 'Ext.data.Model',
	fields : [{
				name : 'payableNo'
			}, {
				name : 'active'
			}, {
				name : 'amount',
				type : 'long'
			}, {
				name : 'sourceBillNo'
			}, {
				name : 'payableType'
			}, {
				name : 'createTime',
				type:'date',
				convert:stl.longToDateConvert 
			}, {
				name : 'createUserCode'
			}, {
				name : 'createUserName'
			}
    ]
});

/**
 * 表格store
 */
Ext.define('Foss.PartnerPayableBill.PayableGridStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.PartnerPayableBill.PayableGridModel',
	pageSize : 10,
	sorters : [{
				property : 'businessDate',
				direction : 'ASC'
			}],
	proxy : {
		type : 'ajax',
		url : null,
		actionMethods : 'post',
		reader : {
			type : 'json',
			root : 'vo.resultDto.list',
			totalProperty : 'totalCount'
		}
	},
	listeners : {
		'beforeLoad' : function(store, operation, eOpts) {
			
			var params;
			if(pay.queryPartnerPayableBill.queryTab == 'TD'){
				params = {
					'vo.manageDto.businessBeginDate' : pay.queryPartnerPayableBill.businessBeginDate,
					'vo.manageDto.payableOrgCode' : pay.queryPartnerPayableBill.payableOrgCode,
					'vo.manageDto.largeRegion' : pay.queryPartnerPayableBill.largeRegion,
					'vo.manageDto.businessEndDate' : pay.queryPartnerPayableBill.businessEndDate,
					'vo.manageDto.customerCode' : pay.queryPartnerPayableBill.customerCode,
					'vo.manageDto.smallRegion' : pay.queryPartnerPayableBill.smallRegion,
					'vo.manageDto.effectiveStatus' : pay.queryPartnerPayableBill.effectiveStatus,
					'vo.manageDto.payStatus' : pay.queryPartnerPayableBill.payStatus,
					'vo.manageDto.billType' : pay.queryPartnerPayableBill.convertProductCode(pay.queryPartnerPayableBill.billType),
					'vo.manageDto.writeoffStatus' : pay.queryPartnerPayableBill.writeoffStatus,
					'vo.manageDto.approveStatus' : pay.queryPartnerPayableBill.approveStatus,
					'vo.manageDto.active' : pay.queryPartnerPayableBill.active,
					'vo.manageDto.isSign' : pay.queryPartnerPayableBill.isSign,
					'vo.manageDto.queryTab' : pay.queryPartnerPayableBill.queryTab,
					'vo.manageDto.accountBeginDate' : pay.queryPartnerPayableBill.accountBeginDate,
					'vo.manageDto.accountEndDate' : pay.queryPartnerPayableBill.accountEndDate,
					'vo.manageDto.queryDateFlag' : pay.queryPartnerPayableBill.queryDateFlag,
					'vo.manageDto.productCodesList' : pay.queryPartnerPayableBill.convertProductCode(pay.queryPartnerPayableBill.productCode),
					'vo.manageDto.isPartner' : 'Y'
				};
			} else if(pay.queryPartnerPayableBill.queryTab == 'TB'){
				params = {
					'vo.manageDto.billNos' : pay.queryPartnerPayableBill.billNosArray,
					'vo.manageDto.queryTab' : pay.queryPartnerPayableBill.queryTab,
					'vo.manageDto.isPartner' : 'Y'
				};
			} else if(pay.queryPartnerPayableBill.queryTab == 'TSB'){
				params = {
					'vo.manageDto.billNos' : pay.queryPartnerPayableBill.sourceBillNosArray,
					'vo.manageDto.queryTab' : pay.queryPartnerPayableBill.queryTab,
					'vo.manageDto.isPartner' : 'Y'
				};
			}
			Ext.apply(operation, {
				params : params
			});
		}
	}
});

/**
 * 应付单明细表格store
 */
Ext.define('Foss.PartnerPayableBill.PayableDetailGridStore', {
			extend : 'Ext.data.Store',
			model : 'Foss.PartnerPayableBill.PayableDetailGridModel',
			sorters : [{
				property : 'createTime',
				direction : 'ASC'
			}],
	proxy : {
		type : 'ajax',
		url : null,
		actionMethods : 'post',
		reader : {
			type : 'json',
			root : 'payableDetailVO.payableDEntityList'
		}
	},
	listeners : {
		'beforeLoad' : function(store, operation, eOpts) {
				var params = pay.queryPartnerPayableBill.detailParams;
				Ext.apply(operation, {
					params : params
				});
		}
	}
		});

/**
 * 查询tab
 */
Ext.define('Foss.PartnerPayableBill.QueryInfoTab', {
	extend : 'Ext.tab.Panel',
	frame : false,
	bodyCls : 'autoHeight',
	cls : 'innerTabPanel',
	activeTab : 0,// 默认显示按单号制作
	height : 310,
	items : [{
				title : pay.queryPartnerPayableBill.i18n('foss.stl.pay.common.queryByDate'),
				tabConfig : {
					width : 120
				},
				layout : 'hbox',
				items : [{
							xtype : 'form',
							width : 850,
							defaults : {
								labelWidth : 85,
								margin : '5,5,5,5'
							},
							layout : 'column',
							items : [{
										xtype : 'fieldcontainer',
										defaultType : 'radiofield',
										columnWidth : 1,
										layout : 'hbox',
										items : [{
													boxLabel : pay.queryPartnerPayableBill.i18n('foss.stl.pay.queryBillPayable.queryBusinessDate'),
													checked : true,
													name : 'selectDateFlag',
													inputValue : '1',// 表单的参数值
													width : 120
												}, {
													boxLabel : pay.queryPartnerPayableBill.i18n('foss.stl.pay.queryBillPayable.queryAccountDate'),
													name : 'selectDateFlag',
													inputValue : '2',// 表单的参数值
													width : 120
												}]
									}, {
										xtype : 'datefield',
										allowBlank : false,
										fieldLabel : pay.queryPartnerPayableBill.i18n('foss.stl.pay.queryBillPayable.startDate'),
										labelWidth : 85,
										name : 'startDate',
										columnWidth : .3,
										format : 'Y-m-d',
										value : stl.getTargetDate(new Date(), -stl.DATE_THREE_DAYS_WEEK)
									}, {
										xtype : 'datefield',
										fieldLabel : pay.queryPartnerPayableBill.i18n('foss.stl.pay.queryBillPayable.endDate'),
										allowBlank : false,
										labelWidth : 85,
										name : 'endDate',
										format : 'Y-m-d',
										columnWidth : .3,
										value : new Date()
									}, {
										xtype : 'combobox',
										name : 'billType',
										editable : false,
										allowBlank : false,
										multiSelect : true,
										fieldLabel : pay.queryPartnerPayableBill.i18n('foss.stl.pay.queryBillPayable.billType'),
										store : FossDataDictionary.getDataDictionaryStore('BILL_PAYABLE__BILL_TYPE', null, {
													'valueCode' : '',
													'valueName' : pay.queryPartnerPayableBill.i18n('foss.stl.pay.queryBillPayable.all')
												}),
										queryModel : 'local',
										displayField : 'valueName',
										valueField : 'valueCode',
										value : '',
										columnWidth : .3
									},{
										xtype : 'linkagecomboselector',
										eventType : ['focus'],// 一般callparent包含focus事件，如果有callparent,则focus事件可不用传递
										itemId : 'Foss_baseinfo_BigRegion_ID',
										store : Ext.create('Foss.baseinfo.commonSelector.BigRegionStore'),// 从外面传入
										columnWidth : .3,
										fieldLabel : pay.queryPartnerPayableBill.i18n('foss.stl.pay.queryBillPayable.largeRegion'),
										name : 'largeRegion',
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
										columnWidth : .3,
										fieldLabel : pay.queryPartnerPayableBill.i18n('foss.stl.pay.queryBillPayable.smallRegion'),
										name : 'smallRegion',
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
										columnWidth : .3,
										fieldLabel : pay.queryPartnerPayableBill.i18n('foss.stl.pay.queryBillPayable.payableOrgName'),
										name : 'payableOrgCode',
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
										xtype : 'commonsaledepartmentselector',
										all:'true',
										name : 'customerCode',
										fieldLabel : pay.queryPartnerPayableBill.i18n('foss.stl.pay.queryBillPayable.partnerName'),
										allowBlank : true,
										isPaging : true,
										columnWidth : .3,
										height:24,
										singlePeopleFlag : 'Y'
									}, {
										xtype : 'combobox',
										name : 'effectiveStatus',
										fieldLabel : pay.queryPartnerPayableBill.i18n('foss.stl.pay.queryBillPayable.effectiveStatus'),
										labelWidth : 85,
										editable : false,
										store : FossDataDictionary.getDataDictionaryStore('BILL_PAYABLE__EFFECTIVE_STATUS', null,
												{
													'valueCode' : '',
													'valueName' : pay.queryPartnerPayableBill.i18n('foss.stl.pay.queryBillPayable.all')
												}),
										queryModel : 'local',
										displayField : 'valueName',
										valueField : 'valueCode',
										value : '',
										columnWidth : .3
									}, {
										xtype : 'combobox',
										name : 'payStatus',
										labelWidth : 85,
										fieldLabel : pay.queryPartnerPayableBill.i18n('foss.stl.pay.queryBillPayable.payStatus'),
										editable : false,
										value : '',
										store : FossDataDictionary.getDataDictionaryStore('BILL_PAYABLE__PAY_STATUS', null, {
													'valueCode' : '',
													'valueName' : pay.queryPartnerPayableBill.i18n('foss.stl.pay.queryBillPayable.all')
												}),
										queryModel : 'local',
										displayField : 'valueName',
										valueField : 'valueCode',
										columnWidth : .3
									}, {
										xtype : 'combobox',
										name : 'writeoffStatus',
										fieldLabel : pay.queryPartnerPayableBill.i18n('foss.stl.pay.queryBillPayable.writeoffStatus'),
										editable : false,
										labelWidth : 85,
										store : Ext.create('Foss.PartnerPayableBill.PayableWriteoffStatus'),
										queryModel : 'local',
										displayField : 'valueName',
										valueField : 'valueCode',
										value : '',
										columnWidth : .3
									}, {
										xtype : 'combobox',
										name : 'approveStatus',
										fieldLabel : pay.queryPartnerPayableBill.i18n('foss.stl.pay.queryBillPayable.approveStatus'),
										labelWidth : 85,
										editable : false,
										value : '',
										store : FossDataDictionary.getDataDictionaryStore('BILL_PAYABLE__APPROVE_STATUS', null, {
													'valueCode' : '',
													'valueName' : pay.queryPartnerPayableBill.i18n('foss.stl.pay.queryBillPayable.all')
												}),
										queryModel : 'local',
										displayField : 'valueName',
										valueField : 'valueCode',
										columnWidth : .3
									}, {
										xtype : 'combobox',
										name : 'active',
										fieldLabel : pay.queryPartnerPayableBill.i18n('foss.stl.pay.common.active'),
										labelWidth : 85,
										editable : false,
										value : 'Y',
										store : FossDataDictionary.getDataDictionaryStore('FOSS_ACTIVE', null, {
													'valueCode' : '',
													'valueName' : pay.queryPartnerPayableBill.i18n('foss.stl.pay.queryBillPayable.all')
												}),
										queryModel : 'local',
										displayField : 'valueName',
										valueField : 'valueCode',
										columnWidth : .3
									}, {
										xtype : 'combobox',
										name : 'isSign',
										fieldLabel : pay.queryPartnerPayableBill.i18n('foss.stl.pay.common.isSign'),
										labelWidth : 85,
										editable : false,
										value : '',
										store : FossDataDictionary.getDataDictionaryStore('FOSS_BOOLEAN', null, {
													'valueCode' : '',
													'valueName' : pay.queryPartnerPayableBill.i18n('foss.stl.pay.queryBillPayable.all')
												}),
										queryModel : 'local',
										displayField : 'valueName',
										valueField : 'valueCode',
										columnWidth : .3
									}, {
										xtype : 'combobox',
										name : 'productCode',
										fieldLabel : pay.queryPartnerPayableBill.i18n('foss.stl.pay.queryBillPayable.productCode'),
										store : Ext.create('Foss.pkp.ProductStore'),
										multiSelect : true,
										queryMode : 'local',
										displayField : 'name',
										valueField : 'code',
										columnWidth : .3
									}, {
										border : 1,
										xtype : 'container',
										columnWidth : 1,
										defaultType : 'button',
										layout : 'column',
										items : [{
													text : pay.queryPartnerPayableBill.i18n('foss.stl.pay.common.reset'),
													columnWidth : .1,
													handler : function(){
														var form=this.up('form').getForm();
														form.reset();
														var dept = form.findField("payableOrgCode");
														if (!Ext.isEmpty(stl.currentDept.code)) {
															var displayText = stl.currentDept.name
															var valueText = stl.currentDept.code;
															var store = dept.store;
															var key = dept.displayField + '';
															var value = dept.valueField + '';
						
															var m = Ext.create(store.model.modelName);
															m.set(key, displayText);
															m.set(value, valueText);
						
															store.loadRecords([m]);
															dept.setValue(valueText);
														}
													}
												}, {
													xtype : 'container',
													border : false,
													html : '&nbsp;',
													columnWidth : .7
												}, {
													text : pay.queryPartnerPayableBill.i18n('foss.stl.pay.common.query'),
													cls : 'yellow_button',
													columnWidth : .1,
													handler : function() {
														var form = this.up('form').getForm();
														var me = this;
														pay.queryPartnerPayableBill.queryByDate(form, me)
													}
												}]
									}]
						}]
			}, {
				title : pay.queryPartnerPayableBill.i18n('foss.stl.pay.common.queryByNo'),
				tabConfig : {
					width : 120
				},
				layout : 'fit',
				items : [{
					xtype : 'form',
					defaults : {
						margin : '5 0 5 5'
					},
					layout : 'column',
					items : [{
								xtype : 'textareafield',
								fieldLabel : pay.queryPartnerPayableBill.i18n('foss.stl.pay.queryBillPayable.billNo'),
								emptyText : pay.queryPartnerPayableBill.i18n('foss.stl.pay.queryBillPayable.billNoEmptyText'),
								name : 'billNos',
								allowBlank : false,
								columnWidth : .7,
								labelWidth : 70,
								width : 600,
								height : 175,
								//354658-校验至14位运单号
								regex : /^((YF)?[0-9]{7,14}[;,])*((YF)?[0-9]{7,14}[;,]?)$/i,
								regexText : pay.queryPartnerPayableBill.i18n('foss.stl.pay.queryBillPayable.payableNoRegexTextWarning')
							}, {
								xtype : 'container',
								columnWidth : .3,
								margin : '10 0 0 10 ',
								items : [{
									xtype : 'component',
									padding : '0 0 0 10',
									autoEl : {
										tag : 'div',
										html : '<span style="color:red;">'
												+ pay.queryPartnerPayableBill.i18n('foss.stl.pay.queryBillPayable.billNosDesc') + '</span>'
									}
								}]
							}, {
								xtype : 'container',
								columnWidth : 1,
								layout : 'column',
								defaultType : 'button',
								defaults : {
									width : 80
								},
								items : [{
											text : pay.queryPartnerPayableBill.i18n('foss.stl.pay.common.reset'),
											columnWidth : .08,
											handler : function() {
												this.up('form').getForm().reset();
											}
										}, {
											xtype : 'container',
											border : false,
											html : '&nbsp;',
											columnWidth : .54
										}, {
											text : pay.queryPartnerPayableBill.i18n('foss.stl.pay.common.query'),
											cls : 'yellow_button',
											columnWidth : .08,
											handler : function() {
												var form = this.up('form').getForm();
												var me = this;
												pay.queryPartnerPayableBill.queryByNumber(form, me)
											}
										}]
							}]
				}]
			}, {
				title : pay.queryPartnerPayableBill.i18n('foss.stl.pay.common.queryBySourceNo'),
				tabConfig : {
					width : 120
				},
				layout : 'fit',
				items : [{
					xtype : 'form',
					defaults : {
						margin : '5 0 5 5'
					},
					layout : 'column',
					items : [{
								xtype : 'textareafield',
								fieldLabel : pay.queryPartnerPayableBill.i18n('foss.stl.pay.queryBillPayable.billNo'),
								emptyText : pay.queryPartnerPayableBill.i18n('foss.stl.pay.queryBillPayable.billNoEmptyText'),
								name : 'billNos',
								allowBlank : false,
								columnWidth : .7,
								labelWidth : 70,
								width : 600,
								height : 175
							}, {
								xtype : 'container',
								columnWidth : .3,
								margin : '10 0 0 10 ',
								items : [{
									xtype : 'component',
									padding : '0 0 0 10',
									autoEl : {
										tag : 'div',
										html : '<span style="color:red;">'
												+ pay.queryPartnerPayableBill.i18n('foss.stl.pay.queryBillPayable.billSourceNosDesc') + '</span>'
									}
								}]
							}, {
								xtype : 'container',
								columnWidth : 1,
								layout : 'column',
								defaultType : 'button',
								defaults : {
									width : 80
								},
								items : [{
											text : pay.queryPartnerPayableBill.i18n('foss.stl.pay.common.reset'),
											columnWidth : .08,
											handler : function() {
												this.up('form').getForm().reset();
											}
										}, {
											xtype : 'container',
											border : false,
											html : '&nbsp;',
											columnWidth : .54
										}, {
											text : pay.queryPartnerPayableBill.i18n('foss.stl.pay.common.query'),
											cls : 'yellow_button',
											columnWidth : .08,
											handler : function() {
												var form = this.up('form').getForm();
												var me = this;
												pay.queryPartnerPayableBill.queryBySourceNumber(form, me)
											}
										}]
							}]
				}]
			}]
});

/**
 * 应付单列表
 */
Ext.define('Foss.PartnerPayableBill.PayableGrid', {
			extend : 'Ext.grid.Panel',
			title : pay.queryPartnerPayableBill.i18n('foss.stl.pay.queryBillPayable.queryPayableBill'),
			frame : true,
			height : 500,
			bodyCls : 'autoHeight',
			cls : 'autoHeight',
			store : Ext.create('Foss.PartnerPayableBill.PayableGridStore'),
			selModel : Ext.create('Ext.selection.CheckboxModel'),
			defaults : {
				align : 'center',
				margin : '5 0 5 0'
			},
			viewConfig : {
				enableTextSelection : true
				// 设置行可以选择，进而可以复制
			},
			columns : [{
						xtype : 'actioncolumn',
						header : pay.queryPartnerPayableBill.i18n('foss.stl.pay.queryBillPayable.actionColumn'),
						width : 110,
						align : 'center',
						items : [{
									iconCls : 'deppon_icons_showdetail',
									tooltip : pay.queryPartnerPayableBill.i18n('foss.stl.pay.queryBillPayable.entry'),
									handler : function(grid, rowIndex, colIndex) {
										pay.queryPartnerPayableBill.queryDetailByPayableNo(grid, rowIndex, colIndex);
									}
								}]
					}, {
						header : pay.queryPartnerPayableBill.i18n('foss.stl.pay.queryBillPayable.payableNo'),
						dataIndex : 'payableNo'
					}, {
						header : pay.queryPartnerPayableBill.i18n('foss.stl.pay.queryBillPayable.productCode'),
						dataIndex : 'productCode',
						renderer : function(value) {
							return Foss.pkp.ProductData.rendererSubmitToDisplay(value);
						}
					},{
						header : pay.queryPartnerPayableBill.i18n('foss.stl.pay.queryBillPayable.approveStatus'),
						dataIndex : 'approveStatus',
						renderer : function(value) {
							var displayField = FossDataDictionary.rendererSubmitToDisplay(value, 'BILL_PAYABLE__APPROVE_STATUS');
							return displayField;
						}
					}, {
						header : pay.queryPartnerPayableBill.i18n('foss.stl.pay.queryBillPayable.waybillNo'),
						dataIndex : 'waybillNo'
					}, {
						header : pay.queryPartnerPayableBill.i18n('foss.stl.pay.queryBillPayable.sourceBillNo'),
						dataIndex : 'sourceBillNo'
					}, {
						header : pay.queryPartnerPayableBill.i18n('foss.stl.pay.queryBillPayable.payableOrgCode'),
						dataIndex : 'payableOrgCode'
					}, {
						header : pay.queryPartnerPayableBill.i18n('foss.stl.pay.queryBillPayable.payableOrgName'),
						dataIndex : 'payableOrgName'
					}, {
						header : pay.queryPartnerPayableBill.i18n('foss.stl.pay.queryBillPayable.partnerCode'),
						dataIndex : 'customerCode'
					}, {
						header : pay.queryPartnerPayableBill.i18n('foss.stl.pay.queryBillPayable.partnerName'),
						dataIndex : 'customerName'
					}, {
						header : pay.queryPartnerPayableBill.i18n('foss.stl.pay.queryBillPayable.customerContactName'),
						dataIndex : 'customerContactName'
					}, {
						header : pay.queryPartnerPayableBill.i18n('foss.stl.pay.queryBillPayable.customerPhone'),
						dataIndex : 'customerPhone'
					}, {
						header : pay.queryPartnerPayableBill.i18n('foss.stl.pay.queryBillPayable.billType'),
						dataIndex : 'billType',
						renderer : function(value) {
							var displayField = FossDataDictionary.rendererSubmitToDisplay(value, 'BILL_PAYABLE__BILL_TYPE');
							return displayField;
						}
					}, {
						header : pay.queryPartnerPayableBill.i18n('foss.stl.pay.queryBillPayable.amount'),
						dataIndex : 'amount'
					}, {
						header : pay.queryPartnerPayableBill.i18n('foss.stl.pay.queryBillPayable.verifyAmount'),
						dataIndex : 'verifyAmount'
					}, {
						header : pay.queryPartnerPayableBill.i18n('foss.stl.pay.queryBillPayable.unverifyAmount'),
						dataIndex : 'unverifyAmount'
					}, {
						header : pay.queryPartnerPayableBill.i18n('foss.stl.pay.queryBillPayable.paymentAmount'),
						dataIndex : 'paymentAmount'
					}, {
						header : pay.queryPartnerPayableBill.i18n('foss.stl.pay.queryBillPayable.payStatus'),
						dataIndex : 'payStatus',
						renderer : function(value) {
							var displayField = FossDataDictionary.rendererSubmitToDisplay(value, 'BILL_PAYABLE__PAY_STATUS');
							return displayField;
						}
					}, {
						header : pay.queryPartnerPayableBill.i18n('foss.stl.pay.queryBillPayable.businessDate'),
						dataIndex : 'businessDate',
						renderer : function(value) {
							return stl.dateFormat(value, 'Y-m-d  H:i:s');
						}
					}, {
						header : pay.queryPartnerPayableBill.i18n('foss.stl.pay.queryBillPayable.accountDate'),
						dataIndex : 'accountDate',
						renderer : function(value) {
							return stl.dateFormat(value, 'Y-m-d  H:i:s');
						}
					}, {
						header : pay.queryPartnerPayableBill.i18n('foss.stl.pay.queryBillPayable.signDate'),
						dataIndex : 'signDate',
						renderer : function(value) {
							return stl.dateFormat(value, 'Y-m-d  H:i:s');
						}
					}, {
						header : pay.queryPartnerPayableBill.i18n('foss.stl.pay.queryBillPayable.active'),
						dataIndex : 'active',
						renderer : function(value) {
							if (value == 'Y') {
								return pay.queryPartnerPayableBill.i18n('foss.stl.pay.common.yes');
							} else {
								return pay.queryPartnerPayableBill.i18n('foss.stl.pay.common.no');
							}
						}
					}, {
						header : pay.queryPartnerPayableBill.i18n('foss.stl.pay.queryBillPayable.statementBillNo'),
						dataIndex : 'statementBillNo'
					}, {
						header : pay.queryPartnerPayableBill.i18n('foss.stl.pay.queryBillPayable.expressOrigOrgCode'),
						dataIndex : 'expressOrigOrgCode',
						hidden : true
					}, {
						header : pay.queryPartnerPayableBill.i18n('foss.stl.pay.queryBillPayable.expressOrigOrgName'),
						dataIndex : 'expressOrigOrgName',
						hidden : true
					}, {
						header : pay.queryPartnerPayableBill.i18n('foss.stl.pay.queryBillPayable.expressDestOrgCode'),
						dataIndex : 'expressDestOrgCode',
						hidden : true
					}, {
						header : pay.queryPartnerPayableBill.i18n('foss.stl.pay.queryBillPayable.expressDestOrgName'),
						dataIndex : 'expressDestOrgName',
						hidden : true
					}, {
						header : pay.queryPartnerPayableBill.i18n('foss.stl.pay.queryBillPayable.invoiceMark'),
						dataIndex : 'invoiceMark',
						hidden : true,
						renderer : function(value) {
							var displayField = FossDataDictionary.rendererSubmitToDisplay(value, 'SETTLEMENT_INVOICE_MARK');
							return displayField;
						}
					}],
			initComponent : function() {
				var me = this;
				me.dockedItems = [{
							xtype : 'toolbar',
							dock : 'top',
							layout : 'column',
							defaults : {
								margin : '0 0 5 3'
							},
							items : [{
										xtype : 'container',
										html : '&nbsp;',
										columnWidth : .9
									}, {
										xtype : 'button',
										text : pay.queryPartnerPayableBill.i18n('foss.stl.pay.common.export'),
										columnWidth : .09,
										handler : pay.queryPartnerPayableBill.payForBillExport
									}]
						}, {
							xtype : 'toolbar',
							dock : 'bottom',
							layout : 'column',
							defaults : {
								margin : '0 0 5 3'
							},
							items : [{
										xtype : 'textfield',
										readOnly : true,
										name : 'total',
										columnWidth : .1,
										labelWidth : 40,
										fieldLabel : pay.queryPartnerPayableBill.i18n('foss.stl.pay.queryBillPayable.total')
									}, {
										xtype : 'textfield',
										readOnly : true,
										name : 'totalAmount',
										columnWidth : .2,
										labelWidth : 40,
										fieldLabel : pay.queryPartnerPayableBill.i18n('foss.stl.pay.queryBillPayable.amount')
									}, {
										xtype : 'textfield',
										readOnly : true,
										name : 'totalVerifyAmount',
										columnWidth : .2,
										labelWidth : 80,
										fieldLabel : pay.queryPartnerPayableBill.i18n('foss.stl.pay.queryBillPayable.verifyAmount')
									}, {
										xtype : 'textfield',
										readOnly : true,
										name : 'totalUnVerifyAmount',
										columnWidth : .2,
										labelWidth : 80,
										fieldLabel : pay.queryPartnerPayableBill.i18n('foss.stl.pay.queryBillPayable.unverifyAmount')
									},{
										xtype : 'container',
										border : false,
										html : '&nbsp;',
										columnWidth : .3,
										height:24
									},{
										xtype : 'standardpaging',
										store : me.store,
										columnWidth : 1,
										pageSize : 10,
										plugins : Ext.create('Deppon.ux.PageSizePlugin', {
													// 设置分页记录最大值，防止输入过大的数值
													maximumSize : 1000,
													sizeList : [['10', 10], ['25', 25], ['50', 50], ['100', 100], ['200', 200],
															['500', 500], ['1000', 1000]]
												})
									}, {
										xtype : 'component',
										border : true,
										autoEl : {
											tag : 'hr'
										},
										columnWidth : 1
									}, {
										xtype : 'container',
										border : false,
										html : '&nbsp;',
										columnWidth : .72
									}, {
										xtype : 'button',
										text : pay.queryPartnerPayableBill.i18n('foss.stl.pay.queryBillPayable.payment'),
										columnWidth : .09,
										handler : pay.queryPartnerPayableBill.payForBillPayableBatch,
										disabled : !pay.queryPartnerPayableBill.isPermission('/stl-web/pay/payForBillPayable.action'),
										hidden : !pay.queryPartnerPayableBill.isPermission('/stl-web/pay/payForBillPayable.action'),
									}]
						}];
				me.callParent();
			}
		});

/**
 * 应付单明细记录
 */
Ext.define('Foss.PartnerPayableBill.PayableDetailGrid', {
			extend : 'Ext.grid.Panel',
			frame : true,
			title : pay.queryPartnerPayableBill.i18n('foss.stl.pay.queryBillPayable.payableEntry'),
			height : 150,
			bodyCls : 'autoHeight',
			cls : 'autoHeight',
			store : Ext.create('Foss.PartnerPayableBill.PayableDetailGridStore'),
			viewConfig : {
				enableTextSelection : true
				// 设置行可以选择，进而可以复制
			},
			defaults : {
				align : 'center',
				margin : '5 0 5 0'
			},
			columns : [{
				header : pay.queryPartnerPayableBill.i18n('foss.stl.pay.queryBillPayable.sourceBillNo'),
				dataIndex : 'sourceBillNo'
			}, {
				header : pay.queryPartnerPayableBill.i18n('foss.stl.pay.queryBillPayable.payableNo'),
				dataIndex : 'payableNo'
			}, {
				header : pay.queryPartnerPayableBill.i18n('foss.stl.pay.queryBillPayable.amount'),
				dataIndex : 'amount',
				xtype : 'numbercolumn',
				format : '0,0.00',
				align : 'right'
			}, {
				header : pay.queryPartnerPayableBill.i18n('foss.stl.pay.queryPayableBill.payableType'),
				dataIndex : 'payableType',
				renderer : function(value) {
					var displayField = FossDataDictionary.rendererSubmitToDisplay(value, 'PAYABLE_DETAIL__FEE_TYPE');
					return displayField;
				}
			}, {
				header : pay.queryPartnerPayableBill.i18n('foss.stl.pay.queryBillPayable.active'),
				dataIndex : 'active',
				renderer:function(value){
					var displayField = FossDataDictionary.rendererSubmitToDisplay(value,settlementDict.FOSS_ACTIVE);
		    		return displayField;
				}
			}, {
				header : pay.queryPartnerPayableBill.i18n('foss.stl.pay.queryBillPayable.createUserCode'),
				dataIndex : 'createUserCode'
			}, {
				header : pay.queryPartnerPayableBill.i18n('foss.stl.pay.queryBillPayable.createUserName'),
				dataIndex : 'createUserName'
			}, {
				header : pay.queryPartnerPayableBill.i18n('foss.stl.pay.common.createTime'),
				dataIndex : 'createTime',
				xtype: 'datecolumn', 
				format:'Y-m-d H:i:s'
			}]
		});

// 初始化界面
Ext.onReady(function() {
			Ext.QuickTips.init();
			Ext.Ajax.timeout = 600000;
			var queryInfoTab = Ext.create('Foss.PartnerPayableBill.QueryInfoTab');
			var payableGrid = Ext.create('Foss.PartnerPayableBill.PayableGrid');
			var payableDetailGrid = Ext.create('Foss.PartnerPayableBill.PayableDetailGrid');

			Ext.create('Ext.panel.Panel', {
						id : 'T_pay-queryPartnerPayableBill_content',
						cls : "panelContentNToolbar",
						bodyCls : 'panelContentNToolbar-body',
						layout : 'auto',
						getQueryInfoTab : function() {
							return queryInfoTab;
						},
						getPayableGrid : function() {
							return payableGrid;
						},
						getPayableDetailGrid : function() {
							return payableDetailGrid;
						},
						items : [queryInfoTab, payableGrid, payableDetailGrid],
						renderTo : 'T_pay-queryPartnerPayableBill-body',
						listeners : {
							'boxready' : function(th) {
								var roles = stl.currentUserDepts;
								var queryByDateTab = th.getQueryInfoTab().items.items[0].items.items[0];
								var dept = queryByDateTab.getForm().findField("payableOrgCode");
								if (!Ext.isEmpty(stl.currentDept.code)) {
									var displayText = stl.currentDept.name
									var valueText = stl.currentDept.code;
									var store = dept.store;
									var key = dept.displayField + '';
									var value = dept.valueField + '';

									var m = Ext.create(store.model.modelName);
									m.set(key, displayText);
									m.set(value, valueText);

									store.loadRecords([m]);
									dept.setValue(valueText);
								}
							}
						}
					});
		});