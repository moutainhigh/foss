pay.payable.costType_SALE = "JY";//经营
/**
 * 获取费用承担部门
 */
pay.payable.costTypeDatas = FossDataDictionary.getDataByTermsCode('RENTCAR_COST_TYPE');

/**
 * 获取费用类型
 */
pay.payable.getCostTypeDatas = function(deptType){
	var costTypeDataClone =  Ext.clone(pay.payable.costTypeDatas);
	//如果部门性质为空，则返回空
	if(!Ext.isEmpty(deptType)){
		if(deptType==pay.payable.costType_SALE){
			if(!Ext.isEmpty(costTypeDataClone) && !Ext.isEmpty(costTypeDataClone.length)){
				for(var i=0;i<costTypeDataClone.length;i++){
					if(costTypeDataClone[i].extAttribute2=='UNSALE'){
						Ext.Array.remove(costTypeDataClone,costTypeDataClone[i]);
					}
				}
			}
		}else{
			if(!Ext.isEmpty(costTypeDataClone) && !Ext.isEmpty(costTypeDataClone.length)){
				for(var i=0;i<costTypeDataClone.length;i++){
					if(costTypeDataClone[i].extAttribute2=='SALE'){
						Ext.Array.remove(costTypeDataClone,costTypeDataClone[i]);
					}
				}
			}
		}
	}
	for(var i=0;i<costTypeDataClone.length;i++){
		if(costTypeDataClone[i].valueCode=='13'){
			Ext.Array.remove(costTypeDataClone,costTypeDataClone[i]);
		}
	}
	return costTypeDataClone;
}

/**
 * 
 * @param {}
 *            产品类型
 * @return {}此处如果有全选，则需要转化下
 */
pay.payable.convertProductCode = function(productCodes) {
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

/**
 * 查询成功
 */
pay.payable.querySuccess = function(me) {
	// 设置该按钮灰掉
	me.disable(false);
	// 30秒后自动解除灰掉效果
	setTimeout(function() {
				me.enable(true);
			}, 30000);
	var paybleGrid = Ext.getCmp('T_pay-manageBillPayable_content').getPaybleGrid();
	// 查询后台
	paybleGrid.store.loadPage(1, {
				callback : function(records, operation, success) {
					var rawData = paybleGrid.store.proxy.reader.rawData;
					// 当success:false ,isException:false --业务异常
					if (!success && !rawData.isException) {
						Ext.Msg.alert(pay.payable.i18n('foss.stl.pay.common.alert'), rawData.message);
						me.enable(true);
						return false;
					}
					if (success) {
						var result = Ext.decode(operation.response.responseText);
						if (!Ext.isEmpty(result.vo.resultDto.list) && result.vo.resultDto.list.length > 0) {
							var tbars = paybleGrid.dockedItems.items[3].query('textfield');
							tbars[0].setValue(result.vo.resultDto.totalCount);
							tbars[1].setValue(result.vo.resultDto.totalAmount);
							tbars[2].setValue(result.vo.resultDto.totalVerifyAmount);
							tbars[3].setValue(result.vo.resultDto.totalUnVerifyAmount);
							paybleGrid.show();
						}
					}
					me.enable(true);
				}
			});
}

/**
 * 清空表格
 */
pay.payable.clearGrid = function() {
	var paybleGrid = Ext.getCmp('T_pay-manageBillPayable_content').getPaybleGrid();
	paybleGrid.store.removeAll();
	var tbars = paybleGrid.dockedItems.items[3].query('textfield');
	tbars[0].setValue(0);
	tbars[1].setValue(0);
	tbars[2].setValue(0);
	tbars[3].setValue(0);
}

/**
 * 按日期查询
 */
pay.payable.queryByDate = function(form, me) {

	// 判断是否合法
	if (form.isValid()) {

		// 判断用户是选择的业务日期或者记账日期
		var selectDateFlagTemp = form.getValues().selectDateFlag;
		var startDate = form.findField('startDate').getValue();
		var endDate = form.findField('endDate').getValue();

		// 比较起始日期和结束日期
		var compareTwoDate = stl.compareTwoDate(startDate, endDate);
		if (compareTwoDate > stl.DATELIMITDAYS_MONTH) {
			Ext.Msg.alert(pay.payable.i18n('foss.stl.pay.common.alert'), pay.payable
							.i18n('foss.stl.pay.queryBillPayable.queryDateMaxLimit'));
			return false;
		} else if (compareTwoDate < 1) {
			Ext.Msg.alert(pay.payable.i18n('foss.stl.pay.common.alert'), pay.payable
							.i18n('foss.stl.pay.common.dateEndBeforeStatrtWarining'));
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

		pay.payable.isInit = form.findField('isInit').getValue();
		pay.payable.payableOrgCode = form.findField('payableOrgCode').getValue();
		pay.payable.largeRegion = form.findField('largeRegion').getValue();
		pay.payable.businessBeginDate = businessStartDateTemp;
		pay.payable.businessEndDate = businessEndDateTemp;
		pay.payable.accountBeginDate = accountStartDateTemp;
		pay.payable.accountEndDate = accountEndDateTemp;
		pay.payable.queryDateFlag = selectDateFlagTemp;
		pay.payable.isSign = form.findField('isSign').getValue();
		pay.payable.createOrgCode= form.findField('createOrgCode').getValue();
		pay.payable.witholdingCostDept= form.findField('witholdingCostDept').getValue();
		pay.payable.customerCodeList=[];
		// 获取客户类型
		var customerType = form.findField('customerType').getValue();
		// 获取客户编码
		
		pay.payable.customerCode = null;
		var arrCode = ['01','02','03','04','05','06','07','08'];
		var arrName = ['customerCode',
		               'vehAgencyCode',
		               'airAgenyCode',
		               'airlineName',
		               'landStowage',
		               'packagingCode',
		               'leaseddriver',
		               'homesupply'];
		for(var i=0;i<arrCode.length;i++) { 
			if (customerType == arrCode[i] && customerType !='07'){
				pay.payable.customerCode = form.findField(arrName[i]).getValue();
				pay.payable.customerCodeList = [];
			}else if(customerType == arrCode[i] && customerType =='07'){
				pay.payable.customerCodeList = form.findField(arrName[i]).valueList;
			} 
		} 
		
		pay.payable.smallRegion = form.findField('smallRegion').getValue();
		pay.payable.effectiveStatus = form.findField('effectiveStatus').getValue();
		pay.payable.payStatus = form.findField('payStatus').getValue();
		pay.payable.billType = form.findField('billType').getValue();
		pay.payable.writeoffStatus = form.findField('writeoffStatus').getValue();
		pay.payable.approveStatus = form.findField('approveStatus').getValue();
		pay.payable.active = form.findField('active').getValue();
		pay.payable.productCode = form.findField('productCode').getValue();

		pay.payable.queryTab = pay.payable.QUERY_BYDATE;

		/*
		 * BUG-38768 BUG-38779 ISSUE-3086 控制查询条件，大区、小区和部门必须填写一项
		 * 
		 * 杨书硕 2013-6-29 上午11:50:31
		 */
		if (!(!!pay.payable.largeRegion || !!pay.payable.payableOrgCode || !!pay.payable.smallRegion)) {
			Ext.Msg.alert(pay.payable.i18n('foss.stl.pay.common.alert'), pay.payable
							.i18n('foss.stl.pay.common.validateRegionFailAlert'));
			return false;
		}

		// 去后台查询
		pay.payable.clearGrid();
		pay.payable.querySuccess(me);
	} else {
		Ext.Msg.alert(pay.payable.i18n('foss.stl.pay.common.alert'), pay.payable.i18n('foss.stl.pay.common.validateFailAlert'));
		return false;
	}
}
/**
 * 按单号查询
 */
pay.payable.queryByNumber = function(form, me) {
	// 判断是否合法
	if (form.isValid()) {
		var billNos = form.findField('billNos').getValue();
		if (Ext.String.trim(billNos) != null && Ext.String.trim(billNos) != '') {
			var array = stl.splitToArray(billNos);
			for (var i = 0; i < array.length; i++) {
				if (Ext.String.trim(array[i]) == '') {
					array.pop(array[i]);
				}
			}
			if (array.length > 10) {
				Ext.Msg.alert(pay.payable.i18n('foss.stl.pay.common.alert'), pay.payable
								.i18n('foss.stl.pay.common.queryNosLimit'));
				return false;
			}
			pay.payable.billNos = array;
		} else {
			Ext.Msg.alert(pay.payable.i18n('foss.stl.pay.common.alert'), pay.payable
							.i18n('foss.stl.pay.queryBillPayable.nosNotNull'));
			return false;
		}
		pay.payable.queryTab = pay.payable.QUERY_BYNUMBER;
		// 去后台查询
		pay.payable.clearGrid();
		pay.payable.querySuccess(me);
	} else {
		Ext.Msg.alert(pay.payable.i18n('foss.stl.pay.common.alert'), pay.payable.i18n('foss.stl.pay.common.validateFailAlert'));
		return false;
	}
}

/**
 * 按来源单号查询
 */
pay.payable.queryBySourceNumber = function(form, me) {
	// 判断是否合法
	if (form.isValid()) {
		var billNos = form.findField('billNos').getValue();
		if (Ext.String.trim(billNos) != null && Ext.String.trim(billNos) != '') {
			var array = stl.splitToArray(billNos);
			for (var i = 0; i < array.length; i++) {
				if (Ext.String.trim(array[i]) == '') {
					array.pop(array[i]);
				}
			}
			if (array.length > 10) {
				Ext.Msg.alert(pay.payable.i18n('foss.stl.pay.common.alert'), pay.payable
								.i18n('foss.stl.pay.common.queryNosLimit'));
				return false;
			}
			pay.payable.billNos = array;
		} else {
			Ext.Msg.alert(pay.payable.i18n('foss.stl.pay.common.alert'), pay.payable
							.i18n('foss.stl.pay.queryBillPayable.nosNotNull'));
			return false;
		}
		// 按来源单号查询
		pay.payable.queryTab = pay.payable.QUERY_BYSOURCENUMBER;
		// 去后台查询
		pay.payable.clearGrid();
		pay.payable.querySuccess(me);
	} else {
		Ext.Msg.alert(pay.payable.i18n('foss.stl.pay.common.alert'), pay.payable.i18n('foss.stl.pay.common.validateFailAlert'));
		return false;
	}
}
/**
 * 重置
 */
pay.payable.reset = function() {
	this.up('form').getForm().reset();

}
/**
 * 查询表格表格重置
 */
pay.payable.queryReset = function() {
	// 获取界面数据
	this.up('form').getForm().reset();
	var form = this.up('form').getForm();
	// 还原客户
	/*
	 * var customerCode = form.findField('customerCode'); var vehAgencyCode =
	 * form.findField('vehAgencyCode'); var airAgenyCode =
	 * form.findField('airAgenyCode'); var airlineName =
	 * form.findField('airlineName'); var landStowage =
	 * form.findField('landStowage'); customerCode.show();
	 * customerCode.setReadOnly(false); vehAgencyCode.setReadOnly(true);
	 * airAgenyCode.setReadOnly(true); airlineName.setReadOnly(true);
	 * vehAgencyCode.hide(); airAgenyCode.hide(); airlineName.hide();
	 * landStowage.hide();
	 */

	var dept = this.up('form').getForm().findField('payableOrgCode');
	// 重置部门为当前登录部门
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

//将字符串转换为时间格式,适用各种浏览器,格式如2016-01-01 12:00:00
pay.payable.getTimeByTimeStr = function(dateStr) {
	var timeArr=dateStr.split(" ");
	var d=timeArr[0].split("-");
	var t=timeArr[1].split(":");
	return new Date(d[0],(d[1]-1),d[2],t[0],t[1],t[2]);    
}
/**
 * 判断是否能付款
 */
pay.payable.validator = function(selection) {
	//签收时间
	var signTime = selection.get('signDate');
	//自动付款开始时间
	var dateValue = pay.payable.getTimeByTimeStr('2016-06-18 23:59:59');
	
	//如果是合伙人到付应付不能付款(在合伙人自动付款开始运行之后签收的运单，不能再页面手动付款，之前签收的可以手动申请付款)
	if (selection.get('billType')=='PFCP' && (signTime>dateValue)) {
		Ext.Msg.alert(pay.queryPartnerPayableBill.i18n('foss.stl.pay.common.alert'), 
				pay.queryPartnerPayableBill.i18n('foss.stl.pay.queryBillPayable.payableNo')
						+ ':' + selection.get('payableNo')
						+ pay.queryPartnerPayableBill.i18n('foss.stl.pay.queryBillPayable.pfcpPayToPayWarning'));
		return false;
	}
	// 如果家装，则不能付款
	if (selection.get('billType')==pay.payable.BILL_PAYABLE__BILL_PAYABLE__BILL_TYPE__HOME_IMPROVEMENT) {
		Ext.Msg.alert(pay.payable.i18n('foss.stl.pay.common.alert'), pay.payable.i18n('foss.stl.pay.queryBillPayable.payableNo')
						+ ':' + selection.get('payableNo')
						+ pay.payable.i18n('foss.stl.pay.queryBillPayable.homeToPayWarning'));
		return false;
	}
	// 无效应付单不能进行审核
	if (Ext.isEmpty(selection.get('active')) || selection.get('active') != 'Y') {
		Ext.Msg.alert(pay.payable.i18n('foss.stl.pay.common.alert'), pay.payable.i18n('foss.stl.pay.queryBillPayable.payableNo')
						+ ':' + selection.get('payableNo')
						+ pay.payable.i18n('foss.stl.pay.queryBillPayable.notEffectiveToPayWarning'));
		return false;
	}

	// 已经完全核销的不能进行付款
	if (selection.get('billType') == pay.payable.BILL_PAYABLE__BILL_TYPE__ACCOUNT_PAYABLE_COD) {
		Ext.Msg.alert(pay.payable.i18n('foss.stl.pay.common.alert'), pay.payable.i18n('foss.stl.pay.queryBillPayable.payableNo')
						+ ':' + selection.get('payableNo') + pay.payable.i18n('foss.stl.pay.queryBillPayable.codPayWarning'));
		return false;
	}
	// 已经完全核销的不能进行付款
	if (Ext.isEmpty(selection.get('payableNo'))) {
		Ext.Msg.alert(pay.payable.i18n('foss.stl.pay.common.alert'), pay.payable.i18n('foss.stl.pay.queryBillPayable.waybillNo')
						+ ':' + selection.get('waybillNo')
						+ pay.payable.i18n('foss.stl.pay.queryBillPayable.payableNoIsNullToPayWarning'));
		return false;
	}
	// 已经完全核销的不能进行付款
	if (selection.get('unverifyAmount') == 0) {
		Ext.Msg.alert(pay.payable.i18n('foss.stl.pay.common.alert'), pay.payable.i18n('foss.stl.pay.queryBillPayable.payableNo')
						+ ':' + selection.get('payableNo')
						+ pay.payable.i18n('foss.stl.pay.queryBillPayable.verifiedToPayWarning'));
		return false;
	}
	// 如果为未生效，则提示
	if (selection.get('effectiveStatus') == 'N') {
		if(selection.get("modifyUserName")=='System_job'){
			Ext.Msg.alert(pay.payable.i18n('foss.stl.pay.common.alert'), "应付单号："+selection.get('payableNo')+" 已被限制付款，如需付款，请联系应付账款管理小组取消限制 ！");
			return false;
		}
		Ext.Msg.alert(pay.payable.i18n('foss.stl.pay.common.alert'), pay.payable.i18n('foss.stl.pay.queryBillPayable.payableNo')
						+ ':' + selection.get('payableNo')
						+ pay.payable.i18n('foss.stl.pay.queryBillPayable.unEffectiveToPayWarning'));
		return false;
	}
	// 如果是折扣应付不能付款
	if (selection.get('billType')==pay.payable.BILL_PAYABLE__SOURCE_BILL_TYPE__DISCOUNT) {
		Ext.Msg.alert(pay.payable.i18n('foss.stl.pay.common.alert'), pay.payable.i18n('foss.stl.pay.queryBillPayable.payableNo')
						+ ':' + selection.get('payableNo')
						+ pay.payable.i18n('foss.stl.pay.queryBillPayable.discountToPayWarning'));
		return false;
	}
	
	//如果为临时租车, 则传递给后台调用
	if(selection.get('billType')==pay.payable.BILL_PAYABLE__BILL_TYPE__RENT_CAR){
		pay.payable.containRentCar = 'Y';
	}else{
		pay.payable.containRentCar = 'N';
	}
	return true;
}

/**
 * 校验通过后，进行付款操作
 */
pay.payable.validatorSuccess = function(selections) {
	// 发送请求传递信息到后台
	var vo = new Object();
	var manageDto = new Object();
	manageDto.billNos = selections;
	manageDto.containRentCar = pay.payable.containRentCar;
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
					if (Ext.isEmpty(pay.payable.addPaymentWindow)) {
						pay.payable.addPaymentWindow = Ext.create('Foss.payment.AddPaymentWindow');
					}
					var win = pay.payable.addPaymentWindow;
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
					//设置是否保理
					if(result.vo.resultDto.paymentEntity.factoring == 'Y'){
						payForm.findField('isfactoring').show().setValue("是");
					}else{
						payForm.findField('isfactoring').show().setValue("否");
					}
					if(pay.payable.containRentCar=='Y'){
						pay.containRentCar = pay.payable.containRentCar;
						payForm.findField('costDeptCode').show();
						payForm.findField('costType').show();
						payForm.findField('displayfield1').show();
						payForm.findField('isTaxinvoice').show();
						var costType = payForm.findField('costType');
						var costTypeStore = costType.store;
						//如果费用承担部门不为空，则表示已经做过预提。那么设置费用承担部门不可编辑、同时需要明细设置业务日期不可编辑
						if(!Ext.isEmpty(costDeptCode)){
							pay.businessOfDateEdit = 'Y';
							payForm.findField('costDeptCode').setReadOnly(true);
							payForm.findField('costDeptCode').setCombValue(costDeptName,costDeptCode);
							//此处最好在上面setcomboValue之后，因为setcombValue会出发change事件
							costTypeStore.removeAll(); 
						    costTypeStore.loadData(pay.payable.getCostTypeDatas(costDeptType));
						}else{
							//单条付款时校验业务日期，批量付款时后台校验费用承担部门是否相同
							/*if(selections.length === 1){
								// 获取表格组件
								var paybleGrid = Ext.getCmp('T_pay-manageBillPayable_content').getPaybleGrid();
								var selection = paybleGrid.getSelectionModel().getSelection();
								
								//2016年6月7日12点之前的数据仍然走之前的逻辑
								var time = new Date(2016,5,7,12,0,0);
								var bussizeDate = selection[0].data.businessDate;
								if(bussizeDate.getTime() - time.getTime() > 0){
									payForm.findField('costDeptCode').setReadOnly(true);
								}else{
									
								}
							}*/
							pay.businessOfDateEdit = 'N';
							payForm.findField('costDeptCode').setReadOnly(false);
							payForm.findField('costDeptCode').setValue(null);
							costTypeStore.loadData(pay.payable.getCostTypeDatas(null));
						}
						invoceTax.show();tax.show();businessOfDate.show();
					}else{
						pay.businessOfDateEdit = 'Y';
						pay.containRentCar = null;
						businessOfDate.setEditor(null);//设置不可编辑
						payForm.findField('costDeptCode').hide();
						payForm.findField('costType').hide();
						payForm.findField('displayfield1').hide();
						payForm.findField('isTaxinvoice').hide();
						payForm.findField('costDeptCode').setValue(null);
						invoceTax.hide();tax.hide();businessOfDate.hide();
					}
					
					// 弹出付款单
					pay.payable.addPaymentWindow.show();
					
					// 如果应付单是代打木架，付款只能选择电汇方式，如果为家装应付单，付款也只能为电汇方式
					if(result.vo.resultDto.addDtoList[0].billType == pay.payable.BILL_PAYABLE__BILL_TYPE__WOODEN_PAYABLE
							|| result.vo.resultDto.addDtoList[0].billType == pay.payable.BILL_PAYABLE__BILL_TYPE__WOODEN_OTEHR_PAYABLE){
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
					Ext.Msg.alert(pay.payable.i18n('foss.stl.pay.common.alert'), result.message);
				}
			});
}

/**
 * 单条付款
 */
pay.payable.payForBillPayable = function(grid, rowIndex, colIndex) {
	// 获取点击行数据
	var selection = grid.getStore().getAt(rowIndex);
	// 校验该条记录
	var isValid = pay.payable.validator(selection);
	// 声明付款record
	pay.payable.paySelections = [selection];
	if (isValid) {
		pay.payable.validatorSuccess([selection.data.payableNo]);
	}
}
/**
 * 批量付款
 */
pay.payable.payForBillPayableBatch = function() {
	var paybleGrid, selection;
	// 获取表格组件
	paybleGrid = Ext.getCmp('T_pay-manageBillPayable_content').getPaybleGrid();
	selection = paybleGrid.getSelectionModel().getSelection();
	// 如果没有选择数据，则提示
	if (selection.length == 0) {
		Ext.Msg.alert(pay.payable.i18n('foss.stl.pay.common.alert'), pay.payable
						.i18n('foss.stl.pay.queryBillPayable.unSelectedToPayWarning'));
		return false;
	}
	// 声明付款record
	pay.payable.paySelections = selection;
	var jsonData = [];
	var isNotRentcarFlag = false;
	var isRentcarFlag = false;
	// 循环校验
	for (var i = 0; i < selection.length; i++) {
		var isValid = pay.payable.validator(selection[i]);
		//判断是否为临时租车
		if(selection[i].get('billType')==pay.payable.BILL_PAYABLE__BILL_TYPE__RENT_CAR){
			isRentcarFlag =true;
		}else{
			isNotRentcarFlag = true;
		}
		//临时租车不能和其他单据合并付款
		if(isNotRentcarFlag && isRentcarFlag){
			Ext.Msg.alert(pay.payable.i18n('foss.stl.pay.common.alert'), pay.payable
						.i18n('foss.stl.pay.queryBillPayable.errorBillTypeToBatchPay'));
			return false;
		}
		// 如果有一条不过，则停止
		if (!isValid) {
			return false;
		}
		jsonData.push(selection[i].data.payableNo);
	}

	pay.payable.validatorSuccess(jsonData);
}

/**
 * 校验外请车的单据类型
 */
pay.payable.validateAuditOrUnAuditBillType = function(billType) {
	var flag = false;
	var targetBillTypes = pay.payable.AUDIT_OR_UNAUDIT_TYPES;
	// 循环校验billtype是否在规定单据类型范围内
	for (var i = 0; i < targetBillTypes.length; i++) {
		if (billType == targetBillTypes[i]) {
			flag = true;
			break;
		}
	}
	return flag;
}

/**
 * 校验是否可以审核
 */
pay.payable.vaildateAutit = function(selection) {
	// 已经完全核销的不能进行付款
	if (Ext.isEmpty(selection.get('payableNo'))) {
		Ext.Msg.alert(pay.payable.i18n('foss.stl.pay.common.alert'), pay.payable.i18n('foss.stl.pay.queryBillPayable.waybillNo')
						+ ':' + selection.get('waybillNo')
						+ pay.payable.i18n('foss.stl.pay.queryBillPayable.payableNoIsNullToAuditWarning'));
		return false;
	}
	// 无效应付单不能进行审核
	if (Ext.isEmpty(selection.get('active')) || selection.get('active') != 'Y') {
		Ext.Msg.alert(pay.payable.i18n('foss.stl.pay.common.alert'), pay.payable.i18n('foss.stl.pay.queryBillPayable.payableNo')
						+ ':' + selection.get('payableNo')
						+ pay.payable.i18n('foss.stl.pay.queryBillPayable.notEffectiveToAuditWarning'));
		return false;
	}
	// 只有规定单据类型，才能进行审核和反审核操作
	if (Ext.isEmpty(selection.get('billType'))) {
		Ext.Msg.alert(pay.payable.i18n('foss.stl.pay.common.alert'), pay.payable.i18n('foss.stl.pay.queryBillPayable.payableNo')
						+ ':' + pay.payable.i18n('foss.stl.pay.queryBillPayable.billTypeIsNullWarning'));
		return false;
	} else {
		// 如果为空运其他应付，则提示
		if (selection.get('billType') == pay.payable.BILL_PAYABLE__BILL_TYPE__AIR_OTHER) {
			Ext.Msg.alert(pay.payable.i18n('foss.stl.pay.common.alert'), pay.payable
							.i18n('foss.stl.pay.queryBillPayable.payableNo')
							+ ':' + pay.payable.i18n('foss.stl.pay.queryBillPayable.otherAirBilltypeToAuditWarning'));
			return false;
			// 如果为偏线外发，则提示
		} else if (selection.get('billType') == pay.payable.BILL_PAYABLE__BILL_TYPE__PARTIAL_LINE) {
			Ext.Msg.alert(pay.payable.i18n('foss.stl.pay.common.alert'), pay.payable
							.i18n('foss.stl.pay.queryBillPayable.payableNo')
							+ ':' + pay.payable.i18n('foss.stl.pay.queryBillPayable.partialLineBilltypeToAuditWarning'));
			return false;
			// 如果为装卸费应付、理赔应付、退运费应付、服务补救应付
		} else if (selection.get('billType') == pay.payable.BILL_PAYABLE__BILL_TYPE__SERVICE_FEE
				|| selection.get('billType') == pay.payable.BILL_PAYABLE__BILL_TYPE__CLAIM
				|| selection.get('billType') == pay.payable.BILL_PAYABLE__BILL_TYPE__REFUND
				|| selection.get('billType') == pay.payable.BILL_PAYABLE__BILL_TYPE__COMPENSATION
				|| selection.get('billType') == pay.payable.BILL_PAYABLE__BILL_TYPE__RENT_CAR) {
			Ext.Msg.alert(pay.payable.i18n('foss.stl.pay.common.alert'), pay.payable
							.i18n('foss.stl.pay.queryBillPayable.payableNo')
							+ ':' + pay.payable.i18n('foss.stl.pay.queryBillPayable.claimtypeToAuditWarning'));
			return false;
			// 如果为应付代收货款
		} else if (selection.get('billType') == pay.payable.BILL_PAYABLE__BILL_TYPE__ACCOUNT_PAYABLE_COD) {
			Ext.Msg.alert(pay.payable.i18n('foss.stl.pay.common.alert'), pay.payable
							.i18n('foss.stl.pay.queryBillPayable.payableNo')
							+ ':' + pay.payable.i18n('foss.stl.pay.queryBillPayable.codtypeToAuditWarning'));
			return false;
			// 如果为外请车，则不需要审核
		} else if (pay.payable.validateAuditOrUnAuditBillType(selection.get('billType'))) {
			Ext.Msg.alert(pay.payable.i18n('foss.stl.pay.common.alert'), pay.payable
							.i18n('foss.stl.pay.queryBillPayable.payableNo')
							+ ':' + pay.payable.i18n('foss.stl.pay.queryBillPayable.trunkTypeToAuditWarning'));
			return false;
			// 如果为快递代理其它应付
		} else if (selection.get('billType') == pay.payable.BILL_PAYABLE__BILL_TYPE__LAND_STOWAGE_OTHER) {
			Ext.Msg.alert(pay.payable.i18n('foss.stl.pay.common.alert'), pay.payable
							.i18n('foss.stl.pay.queryBillPayable.payableNo')
							+ ':' + pay.payable.i18n('foss.stl.pay.queryBillPayable.landOtherTypeToAuditWarning'));
			return false;
			// 偏线其它应付
		} else if (selection.get('billType') == pay.payable.BILL_PAYABLE__BILL_TYPE__PARTIAL_LINE_OTHER) {
			Ext.Msg.alert(pay.payable.i18n('foss.stl.pay.common.alert'), pay.payable
							.i18n('foss.stl.pay.queryBillPayable.payableNo')
							+ ':' + pay.payable.i18n('foss.stl.pay.queryBillPayable.PAOtherTypeToAuditWarning'));
			return false;
			// 如果为快递代理外发代理
		} else if (selection.get('billType') == pay.payable.BILL_PAYABLE__BILL_TYPE__LAND_STOWAGE) {
			Ext.Msg.alert(pay.payable.i18n('foss.stl.pay.common.alert'), pay.payable
							.i18n('foss.stl.pay.queryBillPayable.payableNo')
							+ ':' + pay.payable.i18n('foss.stl.pay.queryBillPayable.landTypeToAuditWarning'));
			return false;
			//假如是家装不需要审核
		} else if(selection.get('billType') ==pay.payable.BILL_PAYABLE__BILL_PAYABLE__BILL_TYPE__HOME_IMPROVEMENT){
			Ext.Msg.alert(pay.payable.i18n('foss.stl.pay.common.alert'), pay.payable
					.i18n('foss.stl.pay.queryBillPayable.payableNo')
					+ ':' + pay.payable.i18n('foss.stl.pay.queryBillPayable.homeTypeToAuditWarning'));
			return false;
		}
	}

	// 审核状态不是"未审核"，不能进行审核操作
	if (selection.get('approveStatus') != pay.payable.BILL_PAYABLE__APPROVE_STATUS__NOT_AUDIT) {
		Ext.Msg.alert(pay.payable.i18n('foss.stl.pay.common.alert'), pay.payable.i18n('foss.stl.pay.queryBillPayable.payableNo')
						+ ':' + selection.get('payableNo')
						+ pay.payable.i18n('foss.stl.pay.queryBillPayable.errorApproveStatusToAuditWarning'));
		return false;
	}
	return true;
}

/**
 * 审核操作成功
 */
pay.payable.autitSuccess = function(billNos, selections) {
	// 发送请求传递信息到后台
	var vo = new Object();
	var manageDto = new Object();
	manageDto.billNos = billNos;
	vo.manageDto = manageDto;
	Ext.Ajax.request({
				url : pay.realPath('auditPayable.action'),
				jsonData : {
					'vo' : vo
				},
				actionMethods : 'post',
				success : function(response) {
					var result = Ext.decode(response.responseText);
					// 此处重新查询刷新界面
					// var paybleGrid =
					// Ext.getCmp('T_pay-manageBillPayable_content').getPaybleGrid();
					// //此处重新查询刷新界面 --不能用loadpage，用load方法，刚好刷新本页
					// paybleGrid.store.load();
					// 手动刷新界面
					for (var i = 0; i < selections.length; i++) {
						selections[i].set('approveStatus', pay.payable.BILL_PAYABLE__APPROVE_STATUS__AUDIT_AGREE);
						selections[i].commit();
					}
				},
				exception : function(response) {
					var result = Ext.decode(response.responseText);
					Ext.Msg.alert(pay.payable.i18n('foss.stl.pay.common.alert'), result.message);
				}
			});
}

/**
 * 审核
 */
pay.payable.audit = function(grid, rowIndex, colIndex) {
	// 获取点击行数据
	var selection = grid.getStore().getAt(rowIndex);
	// 校验该条记录
	var isValid = pay.payable.vaildateAutit(selection);
	// 校验通过，则去审核应付单
	if (isValid) {
		pay.payable.autitSuccess([selection.data.payableNo], [selection]);
	}
}

/**
 * 批量审核
 */
pay.payable.auditBatch = function() {
	var paybleGrid, selection;
	// 获取表格组件
	paybleGrid = Ext.getCmp('T_pay-manageBillPayable_content').getPaybleGrid();
	selection = paybleGrid.getSelectionModel().getSelection();
	// 如果没有选择数据，则提示
	if (selection.length == 0) {
		Ext.Msg.alert(pay.payable.i18n('foss.stl.pay.common.alert'), pay.payable
						.i18n('foss.stl.pay.queryBillPayable.unSelectedToAuditWarning'));
		return false;
	}
	var jsonData = [];
	// 循环校验
	for (var i = 0; i < selection.length; i++) {
		var isValid = pay.payable.vaildateAutit(selection[i]);
		// 如果有一条不过，则停止
		if (!isValid) {
			return false;
		}
		jsonData.push(selection[i].data.payableNo);
	}
	// 进行审核操作
	pay.payable.autitSuccess(jsonData, selection);
}

/**
 * 校验是否可以反审核
 */
pay.payable.vaildateUnAutit = function(selection) {
	// 已经完全核销的不能进行反审核
	if (Ext.isEmpty(selection.get('payableNo'))) {
		Ext.Msg.alert(pay.payable.i18n('foss.stl.pay.common.alert'), pay.payable.i18n('foss.stl.pay.queryBillPayable.waybillNo')
						+ ':' + selection.get('waybillNo')
						+ pay.payable.i18n('foss.stl.pay.queryBillPayable.payableNoIsNullToUnAuditWarning'));
		return false;
	}
	// 无效应付单不能进行审核
	if (Ext.isEmpty(selection.get('active')) || selection.get('active') != 'Y') {
		Ext.Msg.alert(pay.payable.i18n('foss.stl.pay.common.alert'), pay.payable.i18n('foss.stl.pay.queryBillPayable.payableNo')
						+ ':' + selection.get('payableNo')
						+ pay.payable.i18n('foss.stl.pay.queryBillPayable.notEffectiveToUnAuditWarning'));
		return false;
	}
	// 只有规定单据类型，才能进行审核和反审核操作
	if (Ext.isEmpty(selection.get('billType'))) {
		Ext.Msg.alert(pay.payable.i18n('foss.stl.pay.common.alert'), pay.payable.i18n('foss.stl.pay.queryBillPayable.payableNo')
						+ ':' + pay.payable.i18n('foss.stl.pay.queryBillPayable.billTypeIsNullWarning'));
		return false;
	} else {
		// 如果为空运其他应付，则提示
		if (selection.get('billType') == pay.payable.BILL_PAYABLE__BILL_TYPE__AIR_OTHER) {
			Ext.Msg.alert(pay.payable.i18n('foss.stl.pay.common.alert'), pay.payable
							.i18n('foss.stl.pay.queryBillPayable.payableNo')
							+ ':' + pay.payable.i18n('foss.stl.pay.queryBillPayable.otherAirBilltypeToAuditWarning'));
			return false;
			// 如果为偏线外发，则提示
		} else if (selection.get('billType') == pay.payable.BILL_PAYABLE__BILL_TYPE__PARTIAL_LINE) {
			Ext.Msg.alert(pay.payable.i18n('foss.stl.pay.common.alert'), pay.payable
							.i18n('foss.stl.pay.queryBillPayable.payableNo')
							+ ':' + pay.payable.i18n('foss.stl.pay.queryBillPayable.partialLineBilltypeToAuditWarning'));
			return false;
			// 如果为装卸费应付、理赔应付、退运费应付、服务补救应付
		} else if (selection.get('billType') == pay.payable.BILL_PAYABLE__BILL_TYPE__SERVICE_FEE
				|| selection.get('billType') == pay.payable.BILL_PAYABLE__BILL_TYPE__CLAIM
				|| selection.get('billType') == pay.payable.BILL_PAYABLE__BILL_TYPE__REFUND
				|| selection.get('billType') == pay.payable.BILL_PAYABLE__BILL_TYPE__COMPENSATION
				|| selection.get('billType') == pay.payable.BILL_PAYABLE__BILL_TYPE__RENT_CAR) {
			Ext.Msg.alert(pay.payable.i18n('foss.stl.pay.common.alert'), pay.payable
							.i18n('foss.stl.pay.queryBillPayable.payableNo')
							+ ':' + pay.payable.i18n('foss.stl.pay.queryBillPayable.claimtypeToAuditWarning'));
			return false;
			// 如果为应付代收货款
		} else if (selection.get('billType') == pay.payable.BILL_PAYABLE__BILL_TYPE__ACCOUNT_PAYABLE_COD) {
			Ext.Msg.alert(pay.payable.i18n('foss.stl.pay.common.alert'), pay.payable
							.i18n('foss.stl.pay.queryBillPayable.payableNo')
							+ ':' + pay.payable.i18n('foss.stl.pay.queryBillPayable.codtypeToAuditWarning'));
			return false;
			// 如果为外请车，则不需要审核
		} else if (pay.payable.validateAuditOrUnAuditBillType(selection.get('billType'))) {
			Ext.Msg.alert(pay.payable.i18n('foss.stl.pay.common.alert'), pay.payable
							.i18n('foss.stl.pay.queryBillPayable.payableNo')
							+ ':' + pay.payable.i18n('foss.stl.pay.queryBillPayable.trunkTypeToAuditWarning'));
			return false;
			// 如果为快递代理其它应付
		} else if (selection.get('billType') == pay.payable.BILL_PAYABLE__BILL_TYPE__LAND_STOWAGE_OTHER) {
			Ext.Msg.alert(pay.payable.i18n('foss.stl.pay.common.alert'), pay.payable
							.i18n('foss.stl.pay.queryBillPayable.payableNo')
							+ ':' + pay.payable.i18n('foss.stl.pay.queryBillPayable.landOtherTypeToAuditWarning'));
			return false;
			// 偏线其它应付
		} else if (selection.get('billType') == pay.payable.BILL_PAYABLE__BILL_TYPE__PARTIAL_LINE_OTHER) {
			Ext.Msg.alert(pay.payable.i18n('foss.stl.pay.common.alert'), pay.payable
							.i18n('foss.stl.pay.queryBillPayable.payableNo')
							+ ':' + pay.payable.i18n('foss.stl.pay.queryBillPayable.PAOtherTypeToAuditWarning'));
			return false;
			// 如果为快递代理外发代理
		} else if (selection.get('billType') == pay.payable.BILL_PAYABLE__BILL_TYPE__LAND_STOWAGE) {
			Ext.Msg.alert(pay.payable.i18n('foss.stl.pay.common.alert'), pay.payable
							.i18n('foss.stl.pay.queryBillPayable.payableNo')
							+ ':' + pay.payable.i18n('foss.stl.pay.queryBillPayable.landTypeToAuditWarning'));
			return false;
			//如果为家装
		} else if(selection.get('billType') == pay.payable.BILL_PAYABLE__BILL_PAYABLE__BILL_TYPE__HOME_IMPROVEMENT){
			Ext.Msg.alert(pay.payable.i18n('foss.stl.pay.common.alert'), pay.payable
					.i18n('foss.stl.pay.queryBillPayable.payableNo')
					+ ':' + pay.payable.i18n('foss.stl.pay.queryBillPayable.homeTypeToAuditWarning'));
			return false;
		}
	}
	// 审核状态不是"已审核"，不能进行反审核操作
	if (selection.get('approveStatus') != pay.payable.BILL_PAYABLE__APPROVE_STATUS__AUDIT_AGREE) {
		Ext.Msg.alert(pay.payable.i18n('foss.stl.pay.common.alert'), pay.payable.i18n('foss.stl.pay.queryBillPayable.payableNo')
						+ ':' + selection.get('payableNo')
						+ pay.payable.i18n('foss.stl.pay.queryBillPayable.errorApproveStatusToUnAuditWarning'));
		return false;
	}
	return true;
}

/**
 * 反审核操作成功
 */
pay.payable.unAutitSuccess = function(billNos, selections) {
	// 发送请求传递信息到后台
	var vo = new Object();
	var manageDto = new Object();
	manageDto.billNos = billNos;
	vo.manageDto = manageDto;
	Ext.Ajax.request({
		url : pay.realPath('unAuditPayable.action'),
		jsonData : {
			'vo' : vo
		},
		actionMethods : 'post',
		success : function(response) {
			var result = Ext.decode(response.responseText);
			for (var i = 0; i < selections.length; i++) {
				selections[i].set('approveStatus', pay.payable.BILL_PAYABLE__APPROVE_STATUS__NOT_AUDIT);
				selections[i].commit();
			}
		},
		exception : function(response) {
			var result = Ext.decode(response.responseText);
			Ext.Msg.alert(pay.payable.i18n('foss.stl.pay.common.alert'), result.message);
		}
	});
}

/**
 * 反审核
 */
pay.payable.unAudit = function(grid, rowIndex, colIndex) {
	// 获取点击行数据
	var selection = grid.getStore().getAt(rowIndex);
	// 校验该条记录
	var isValid = pay.payable.vaildateUnAutit(selection);
	// 校验通过，则去审核应付单
	if (isValid) {
		pay.payable.unAutitSuccess([selection.data.payableNo], [selection]);
	}
}
/**
 * 批量反审核
 * 
 */
pay.payable.unAuditBatch = function() {
	var paybleGrid, selection;
	// 获取表格组件
	paybleGrid = Ext.getCmp('T_pay-manageBillPayable_content').getPaybleGrid();
	selection = paybleGrid.getSelectionModel().getSelection();
	// 如果没有选择数据，则提示
	if (selection.length == 0) {
		Ext.Msg.alert(pay.payable.i18n('foss.stl.pay.common.alert'), pay.payable
						.i18n('foss.stl.pay.queryBillPayable.unSelectedToUnAuditWarning'));
		return false;
	}
	var jsonData = [];
	// 循环校验
	for (var i = 0; i < selection.length; i++) {
		var isValid = pay.payable.vaildateUnAutit(selection[i]);
		// 如果有一条不过，则停止
		if (!isValid) {
			return false;
		}
		jsonData.push(selection[i].data.payableNo);
	}
	// 进行审核操作
	pay.payable.unAutitSuccess(jsonData, selection);
}

/**
 * 导出excel
 */
pay.payable.payForBillExport = function() {
	var columns, searchParams, arrayColumns, arrayColumnNames, grid;
	// 获取表格
	grid = Ext.getCmp('T_pay-manageBillPayable_content').getPaybleGrid();
	// 如果没有查询到数据，则不能进行导出操作
	if (grid.store.data.length == 0) {
		Ext.Msg.alert(pay.payable.i18n('foss.stl.pay.common.alert'), pay.payable
						.i18n('foss.stl.pay.queryBillPayable.noDataToExport'));
		return false;
	}
	// 进行导出提示
	Ext.MessageBox.show({
				title : pay.payable.i18n('foss.stl.pay.common.alert'),
				msg : pay.payable.i18n('foss.stl.pay.queryBillPayable.isExport'),
				buttons : Ext.MessageBox.YESNOCANCEL,
				buttonText : {
					yes : pay.payable.i18n('foss.stl.pay.queryBillPayable.defaultColumnExport'),
					no : pay.payable.i18n('foss.stl.pay.queryBillPayable.customColumnExport'),
					cancel : pay.payable.i18n('foss.stl.pay.common.cancel')
				},
				fn : function(e) {
					if (e == 'yes') {
						arrayColumns = ['payableNo', 'productCode', 'approveStatus', 'waybillNo', 'sourceBillNo','paymentWorkflowNo',
								'paymentTransfer','payableOrgCode', 'payableOrgName', 'customerCode', 'customerName', 'customerContactName',
								'customerPhone', 'billType', 'amount', 'verifyAmount', 'unverifyAmount', 'paymentAmount',
								'payStatus', 'businessDate', 'accountDate', 'active', 'isInit', 'statementBillNo'];
						// 默认显示列名称
						arrayColumnNames = [pay.payable.i18n('foss.stl.pay.queryBillPayable.payableNo'),
								pay.payable.i18n('foss.stl.pay.queryBillPayable.productCode'),
								pay.payable.i18n('foss.stl.pay.queryBillPayable.approveStatus'),
								pay.payable.i18n('foss.stl.pay.queryBillPayable.waybillNo'),
								pay.payable.i18n('foss.stl.pay.queryBillPayable.sourceBillNo'),
								pay.payable.i18n('foss.stl.pay.queryBillPayable.paymentWorkflowNo'),
								pay.payable.i18n('foss.stl.pay.queryBillPayable.paymentTransfer'),
								pay.payable.i18n('foss.stl.pay.queryBillPayable.payableOrgCode'),
								pay.payable.i18n('foss.stl.pay.queryBillPayable.payableOrgName'),
								pay.payable.i18n('foss.stl.pay.queryBillPayable.customerCode'),
								pay.payable.i18n('foss.stl.pay.queryBillPayable.customerName'),
								pay.payable.i18n('foss.stl.pay.queryBillPayable.customerContactName'),
								pay.payable.i18n('foss.stl.pay.queryBillPayable.customerPhone'),
								pay.payable.i18n('foss.stl.pay.queryBillPayable.billType'),
								pay.payable.i18n('foss.stl.pay.queryBillPayable.amount'),
								pay.payable.i18n('foss.stl.pay.queryBillPayable.verifyAmount'),
								pay.payable.i18n('foss.stl.pay.queryBillPayable.unverifyAmount'),
								pay.payable.i18n('foss.stl.pay.queryBillPayable.paymentAmount'),
								pay.payable.i18n('foss.stl.pay.queryBillPayable.payStatus'),
								pay.payable.i18n('foss.stl.pay.queryBillPayable.businessDate'),
								pay.payable.i18n('foss.stl.pay.queryBillPayable.accountDate'),
								pay.payable.i18n('foss.stl.pay.queryBillPayable.active'),
								pay.payable.i18n('foss.stl.pay.queryBillPayable.isInit'),
								pay.payable.i18n('foss.stl.pay.queryBillPayable.statementBillNo')];
					} else if (e == 'no') {
						// 转化列头和列明
						columns = grid.columns;
						arrayColumns = [];
						arrayColumnNames = [];
						// 将前台对应列头传入到后台去
						for (var i = 1; i < columns.length; i++) {
							if (columns[i].isHidden() == false) {
								var hederName = columns[i].text;
								var dataIndex = columns[i].dataIndex;
								if (columns[i].text != pay.payable.i18n('foss.stl.pay.queryBillPayable.actionColumn')) {
									arrayColumns.push(dataIndex);
									arrayColumnNames.push(hederName);
								}
							}
						}
					} else {
						return false;
					}

					if (!Ext.fly('downloadAttachFileForm')) {
						var frm = document.createElement('form');
						frm.id = 'downloadAttachFileForm';
						frm.style.display = 'none';
						document.body.appendChild(frm);
					}

					// 按客户查询
					if (pay.payable.queryTab == pay.payable.QUERY_BYDATE) {
						searchParams = {
							'vo.manageDto.businessBeginDate' : pay.payable.businessBeginDate,
							'vo.manageDto.payableOrgCode' : pay.payable.payableOrgCode,
							'vo.manageDto.largeRegion' : pay.payable.largeRegion,
							'vo.manageDto.businessEndDate' : pay.payable.businessEndDate,
							'vo.manageDto.customerCode' : pay.payable.customerCode,
							'vo.manageDto.smallRegion' : pay.payable.smallRegion,
							'vo.manageDto.effectiveStatus' : pay.payable.effectiveStatus,
							'vo.manageDto.payStatus' : pay.payable.payStatus,
							'vo.manageDto.billType' : pay.payable.convertProductCode(pay.payable.billType),
							'vo.manageDto.writeoffStatus' : pay.payable.writeoffStatus,
							'vo.manageDto.approveStatus' : pay.payable.approveStatus,
							'vo.manageDto.active' : pay.payable.active,
							'vo.manageDto.isInit' : pay.payable.isInit,
							'vo.manageDto.queryTab' : pay.payable.queryTab,
							'vo.manageDto.arrayColumns' : arrayColumns,
							'vo.manageDto.arrayColumnNames' : arrayColumnNames,
							'vo.manageDto.accountBeginDate' : pay.payable.accountBeginDate,
							'vo.manageDto.accountEndDate' : pay.payable.accountEndDate,
							'vo.manageDto.queryDateFlag' : pay.payable.queryDateFlag,
							'vo.manageDto.productCodesList' : pay.payable.convertProductCode(pay.payable.productCode),
							'vo.manageDto.createOrgCode' : pay.payable.createOrgCode,
							'vo.manageDto.witholdingCostDept' : pay.payable.witholdingCostDept,
							'vo.manageDto.customerCodeList' : pay.payable.customerCodeList

						};
					} else {
						searchParams = {
							'vo.manageDto.billNos' : pay.payable.billNos,
							'vo.manageDto.queryTab' : pay.payable.queryTab,
							'vo.manageDto.arrayColumns' : arrayColumns,
							'vo.manageDto.arrayColumnNames' : arrayColumnNames
						}
					}

					// 拼接vo，注入到后台
					Ext.Ajax.request({
								url : pay.realPath('exportBillPayable.action'),
								form : Ext.fly('downloadAttachFileForm'),
								method : 'post',
								params : searchParams,
								isUpload : true,
								success : function(response) {
									var result = Ext.decode(response.responseText);
									// 如果异常信息有值，则弹出提示
									if (!Ext.isEmpty(result.errorMessage)) {
										Ext.Msg.alert(pay.payable.i18n('foss.stl.pay.common.alert'), result.errorMessage);
										return false;
									}
									Ext.ux.Toast.msg(pay.payable.i18n('foss.stl.pay.common.alert'), pay.payable
													.i18n('foss.stl.pay.queryBillPayable.exportSuccess'), 'success', 1000);
								},
								failure : function(response) {
									Ext.ux.Toast.msg(pay.payable.i18n('foss.stl.pay.common.alert'), pay.payable
													.i18n('foss.stl.pay.queryBillPayable.exportFailed'), 'error', 1000);
								}
							});
				}
			});
}

/**************************/
/**
 * 取消限制付款 
 */
pay.payable.cancleLimit=function(){
	var paybleGrid, selection;
	// 获取表格组件
	paybleGrid = Ext.getCmp('T_pay-manageBillPayable_content').getPaybleGrid();
	selection = paybleGrid.getSelectionModel().getSelection();
	// 如果没有选择数据，则提示
	if (selection.length == 0) {
		Ext.Msg.alert(pay.payable.i18n('foss.stl.pay.common.alert'), '请至少选中一条记录进行操作!');
		return false;
	}

	var jsonData=[];
	//调用取消限制验证函数
	for(var i=0;i<selection.length;i++){
		//只要有一个验证不通过直接退出函数
		if (!pay.payable.validatorLimit(selection[i])) {
			return;
		}
		//调用后台action			
		jsonData.push(selection[i].get('payableNo'));		
	}
	pay.payable.validatorLimitSuccess(jsonData,selection);
}
/**
 * 判断是否可以取消限制
 */
pay.payable.validatorLimit = function(selection) {	
	// 如果不是名称为 'System_job'修改的应付单则不做任何处理
	if (selection.get('modifyUserName') != 'System_job') {	
		Ext.Msg.alert(pay.payable.i18n('foss.stl.pay.common.alert'),
				'应付单号:'+selection.data.payableNo+' 非系统自动限制!禁止取消限制操作!');
		return false;
	}
	return true;
}
/**
 * 判断是否取消限制验证通过
 */
pay.payable.validatorLimitSuccess= function(jsonData,selection) {
	// 发送请求传递信息到后台
	var vo = new Object();
	var manageDto = new Object();
	manageDto.billNos = jsonData;
	vo.manageDto = manageDto;

	Ext.Ajax.request({
				url : pay.realPath('updateBillPayableAutoLimitRestore.action'),
				jsonData : {
					'vo' : vo
				},
				actionMethods : 'post',
				success : function(response) {
					var result = Ext.decode(response.responseText);			
					var billNos=result.vo.manageDto.billNos;
					var message="应收单号:";
					for(var i=0;i<billNos.length;i++){						
						selection[i].set('effectiveStatus', 'Y');
						selection[i].set('frozenStatus', 'N');
						selection[i].set('modifyUserName',stl.emp.empName);
						selection[i].commit();
						message+=billNos[i]+','
					}
					message=message.substring(0,message.lastIndexOf(','));
					message+=" 已经取消限制付款";
					Ext.Msg.alert(pay.payable.i18n('foss.stl.pay.common.alert'), message);
				},
				exception : function(response) {
					var result = Ext.decode(response.responseText);
					Ext.Msg.alert(pay.payable.i18n('foss.stl.pay.common.alert'), result.message);
				}
			});
}
/**************************/
/**
 * 查看明细
 */
pay.payable.queryDetail = function(grid, rowIndex, colIndex) {
	// 获取点击行数据
	var selection = grid.getStore().getAt(rowIndex);
	var paybleGridEntry = Ext.getCmp('T_pay-manageBillPayable_content').getPaybleGridEntry();
	paybleGridEntry.store.removeAll();
	paybleGridEntry.store.loadData([selection]);
}

/**
 * 付款完毕，回来刷新应付单界面。 手动修改应付单付款状态和版本号
 */
pay.payable.refresh = function(ids) {
	// 如果id为空，则终止
	if (Ext.isEmpty(ids)) {
		return false;
	}
	var grid = Ext.getCmp('T_pay-manageBillPayable_content').getPaybleGrid();
	if (!Ext.isEmpty(pay.payable.paySelections)) {
		// 循环表格 获取与传入id一致的应付单record
		for (var i = 0; i < ids.length; i++) {
			// 循环表格
			for (var j = 0; j < pay.payable.paySelections.length; j++) {
				var record = pay.payable.paySelections[j];
				// 更新汇款状态为已付款
				record.set('payStatus', pay.payable.BILL_PAYABLE__PAY_STATUS__YES);
				// 更新版本号
				record.set('versionNo', record.get('versionNo') + 1);
				record.commit();
				break;
			}
		}
	}
}

// 客户类型：store
Ext.define('Foss.pay.payable.CustomerTypeStore', {
			extend : 'Ext.data.Store',
			fields : ['customerTypeCode', 'customerTypeName'],
			data : {
				'items' : [{
							customerTypeCode : '00',
							customerTypeName : pay.payable.i18n('foss.stl.pay.common.all')
						}, {
							customerTypeCode : '01',
							customerTypeName : pay.payable.i18n('foss.stl.pay.queryBillPayable.customer')
						}, {
							customerTypeCode : '02',
							customerTypeName : pay.payable.i18n('foss.stl.pay.queryBillPayable.vehAgency')
						}, {
							customerTypeCode : '03',
							customerTypeName : pay.payable.i18n('foss.stl.pay.queryBillPayable.airAgeny')
						}, {
							customerTypeCode : '04',
							customerTypeName : pay.payable.i18n('foss.stl.pay.queryBillPayable.airline')
						}, {
							customerTypeCode : '05',
							customerTypeName : pay.payable.i18n('foss.stl.pay.queryBillPayable.landStowage')
						}, {
							customerTypeCode : '06',
							customerTypeName : pay.payable.i18n('foss.stl.pay.common.packagingSupplier')
						} ,{
							customerTypeCode : '07',
							customerTypeName : pay.payable.i18n('foss.stl.pay.common.leaseddriver')
						} ,{
							customerTypeCode : '08',
							customerTypeName : pay.payable.i18n('foss.stl.pay.common.homesupply')
						},{
							customerTypeCode : '09',
							customerTypeName : pay.payable.i18n('foss.stl.pay.common.leasedcombinationpayment')
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
 * 核销状态store
 */
Ext.define('Foss.payable.PaybleWriteoffStatus', {
			extend : 'Ext.data.Store',
			fields : ['valueName', 'valueCode'],
			data : [{
						valueName : pay.payable.i18n('foss.stl.pay.queryBillPayable.writeoffed'),
						valueCode : 'WD'
					}, {
						valueName : pay.payable.i18n('foss.stl.pay.queryBillPayable.partWriteoff'),
						valueCode : 'PW'
					}, {
						valueName : pay.payable.i18n('foss.stl.pay.queryBillPayable.unWriteoff'),
						valueCode : 'NW'
					}, {
						valueName : pay.payable.i18n('foss.stl.pay.queryBillPayable.all'),
						valueCode : ''
					}]
		});

/**
 * 表格模型
 */
Ext.define('Foss.payable.PaybleGridModel', {
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
						name : 'createOrgCode'
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
						name : 'isInit'
					}, {
						name : 'createTime',
						type : 'date',
						convert : stl.longToDateConvert
					}, 
					{
						name : 'modifyUserName',
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
						name : 'createOrgCode'// 制单部门编码
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
                      },{
   						name : 'claimWay'// 理赔方式
   					}]
		});
/**
 * 表格store
 */
Ext.define('Foss.payable.PaybleGridStore', {
			extend : 'Ext.data.Store',
			model : 'Foss.payable.PaybleGridModel',
			sorters : [{
						property : 'businessDate',
						direction : 'ASC'
					}],
			proxy : {
				type : 'ajax',
				url : pay.realPath('queryBillPayable.action'),
				actionMethods : 'post',
				reader : {
					type : 'json',
					root : 'vo.resultDto.list',
					totalProperty : 'totalCount'
				}
			},
			listeners : {
				'beforeLoad' : function(store, operation, eOpts) {
					var searchParams;
					// 如果第一次进入界面，直接选择分页查询，则默认按照客户查询
					if (Ext.isEmpty(pay.payable.queryTab)) {
						// 获取查询条件
						var form = Ext.getCmp('T_pay-manageBillPayable_content').getQueryInfoTab().items.items[0].items.items[0]
								.getForm();

						// 判断用户是选择的业务日期或者记账日期
						var selectDateFlagTemp = form.getValues().selectDateFlag;
						var startDate = form.findField('startDate').getValue();
						var endDate = form.findField('endDate').getValue();

						// 比较起始日期和结束日期
						var compareTwoDate = stl.compareTwoDate(startDate, endDate);
						if (compareTwoDate > stl.DATELIMITDAYS_MONTH) {
							Ext.Msg.alert(pay.payable.i18n('foss.stl.pay.common.alert'), pay.payable
											.i18n('foss.stl.pay.queryBillPayable.queryDateMaxLimit'));
							return false;
						} else if (compareTwoDate < 1) {
							Ext.Msg.alert(pay.payable.i18n('foss.stl.pay.common.alert'), pay.payable
											.i18n('foss.stl.pay.common.dateEndBeforeStatrtWarining'));
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

						pay.payable.businessBeginDate = businessStartDateTemp;
						pay.payable.businessEndDate = businessEndDateTemp;
						pay.payable.accountBeginDate = accountStartDateTemp;
						pay.payable.accountEndDate = accountEndDateTemp;
						pay.payable.queryDateFlag = selectDateFlagTemp;
						pay.payable.payableOrgCode = form.findField('payableOrgCode').getValue();
						pay.payable.largeRegion = form.findField('largeRegion').getValue();
						pay.payable.customerCode = form.findField('customerCode').getValue();
						pay.payable.smallRegion = form.findField('smallRegion').getValue();
						pay.payable.effectiveStatus = form.findField('effectiveStatus').getValue();
						pay.payable.payStatus = form.findField('payStatus').getValue();
						pay.payable.billType = form.findField('billType').getValue();
						pay.payable.writeoffStatus = form.findField('writeoffStatus').getValue();
						pay.payable.approveStatus = form.findField('approveStatus').getValue();
						pay.payable.active = form.findField('active').getValue();
						pay.payable.isInit = form.findField('isInit').getValue();
						pay.payable.isSign = form.findField('isSign').getValue();
						pay.payable.productCode = form.findField('productCode').getValue();
						pay.payable.createOrgCode= form.findField('createOrgCode').getValue();
						pay.payable.witholdingCostDept= form.findField('witholdingCostDept').getValue();
						pay.payable.truckCustomerName = form.findField('truckCustomerName').getValue();	
						pay.payable.truckCustomerCode = form.findField('truckCustomerCode').getValue();
						pay.payable.queryTab = pay.payable.QUERY_BYDATE;
						pay.payable.customerCodeList=[];

					}

					// 按客户查询
					if (pay.payable.queryTab == pay.payable.QUERY_BYDATE) {
						// 获取查询条件
						var form = Ext.getCmp('T_pay-manageBillPayable_content').getQueryInfoTab().items.items[0].items.items[0]
								.getForm();
						pay.payable.truckCustomerName = form.findField('truckCustomerName').getValue();	
						pay.payable.truckCustomerCode = form.findField('truckCustomerCode').getValue();
						searchParams = {
							'vo.manageDto.businessBeginDate' : pay.payable.businessBeginDate,
							'vo.manageDto.payableOrgCode' : pay.payable.payableOrgCode,
							'vo.manageDto.largeRegion' : pay.payable.largeRegion,
							'vo.manageDto.businessEndDate' : pay.payable.businessEndDate,
							'vo.manageDto.customerCode' : pay.payable.customerCode,
							'vo.manageDto.smallRegion' : pay.payable.smallRegion,
							'vo.manageDto.effectiveStatus' : pay.payable.effectiveStatus,
							'vo.manageDto.payStatus' : pay.payable.payStatus,
							'vo.manageDto.billType' : pay.payable.convertProductCode(pay.payable.billType),
							'vo.manageDto.writeoffStatus' : pay.payable.writeoffStatus,
							'vo.manageDto.approveStatus' : pay.payable.approveStatus,
							'vo.manageDto.active' : pay.payable.active,
							'vo.manageDto.isInit' : pay.payable.isInit,
							'vo.manageDto.isSign' : pay.payable.isSign,
							'vo.manageDto.queryTab' : pay.payable.queryTab,
							'vo.manageDto.accountBeginDate' : pay.payable.accountBeginDate,
							'vo.manageDto.accountEndDate' : pay.payable.accountEndDate,
							'vo.manageDto.queryDateFlag' : pay.payable.queryDateFlag,
							'vo.manageDto.productCodesList' : pay.payable.convertProductCode(pay.payable.productCode),
							'vo.manageDto.createOrgCode' : pay.payable.createOrgCode,
							'vo.manageDto.witholdingCostDept' : pay.payable.witholdingCostDept,
							'vo.manageDto.truckCustomerName' : pay.payable.truckCustomerName,//--340403
							'vo.manageDto.truckCustomerCode' : pay.payable.truckCustomerCode,//--340403
							'vo.manageDto.customerCodeList' : pay.payable.customerCodeList
						};
					} else {
						searchParams = {
							'vo.manageDto.billNos' : pay.payable.billNos,
							'vo.manageDto.queryTab' : pay.payable.queryTab
						}
					}
					Ext.apply(operation, {
								params : searchParams
							});
				}
			}
		});

/**
 * 表格store
 */
Ext.define('Foss.payable.PaybleGridEntryStore', {
			extend : 'Ext.data.Store',
			model : 'Foss.payable.PaybleGridModel'
		});

/**
 * 查询tab
 */
Ext.define('Foss.payable.QueryInfoTab', {
	extend : 'Ext.tab.Panel',
	frame : false,
	bodyCls : 'autoHeight',
	cls : 'innerTabPanel',
	activeTab : 0,// 默认显示按单号制作
	height : 350,
	items : [{
				title : pay.payable.i18n('foss.stl.pay.common.queryByDate'),
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
													boxLabel : pay.payable
															.i18n('foss.stl.pay.queryBillPayable.queryBusinessDate'),
													checked : true,
													name : 'selectDateFlag',
													inputValue : '1',// 表单的参数值
													width : 120
												}, {
													boxLabel : pay.payable.i18n('foss.stl.pay.queryBillPayable.queryAccountDate'),
													name : 'selectDateFlag',
													inputValue : '2',// 表单的参数值
													width : 120
												}]
									}, {
										xtype : 'datefield',
										allowBlank : false,
										fieldLabel : pay.payable.i18n('foss.stl.pay.queryBillPayable.startDate'),
										labelWidth : 85,
										name : 'startDate',
										columnWidth : .3,
										format : 'Y-m-d',
										value : stl.getTargetDate(new Date(), -stl.DATE_THREE_DAYS_WEEK)
									}, {
										xtype : 'datefield',
										fieldLabel : pay.payable.i18n('foss.stl.pay.queryBillPayable.endDate'),
										allowBlank : false,
										labelWidth : 85,
										name : 'endDate',
										format : 'Y-m-d',
										columnWidth : .3,
										value : new Date()
										// },{
									// xtype:'datefield',
									// allowBlank:false,
									// fieldLabel:pay.payable.i18n('foss.stl.pay.queryBillPayable.businessDateStart'),
									// labelWidth:85,
									// name:'businessBeginDate',
									// columnWidth: .3,
									// format:'Y-m-d',
									// value:stl.getTargetDate(new
									// Date(),-stl.DATE_THREE_DAYS_WEEK)
									// },{
									// xtype:'datefield',
									// fieldLabel:pay.payable.i18n('foss.stl.pay.queryBillPayable.businessDateEnd'),
									// allowBlank:false,
									// labelWidth:85,
									// name:'businessEndDate',
									// format:'Y-m-d',
									// columnWidth: .3,
									// value:new Date()
								}	, {
										xtype : 'combobox',
										name : 'billType',
										editable : false,
										allowBlank : false,
										multiSelect : true,
										fieldLabel : pay.payable.i18n('foss.stl.pay.queryBillPayable.billType'),
										store : FossDataDictionary.getDataDictionaryStore('BILL_PAYABLE__BILL_TYPE', null, {
													'valueCode' : '',
													'valueName' : pay.payable.i18n('foss.stl.pay.queryBillPayable.all')
												}),
										queryModel : 'local',
										displayField : 'valueName',
										valueField : 'valueCode',
										value : '',
										columnWidth : .3,
										listeners:{
											'select':function(combo,records,opts){
												var form = this.up('form');var createOrgCode = form.getForm().findField('createOrgCode');
												var witholdingCostDept = form.getForm().findField('witholdingCostDept');
												//如果只选择了临时租车，则显示下面的租车部门
												if(records.length==1 && combo.getValue()==pay.payable.BILL_PAYABLE__BILL_TYPE__RENT_CAR){
													createOrgCode.setDisabled(false);
													witholdingCostDept.setDisabled(false);
												}else{
													createOrgCode.setDisabled(true);
													createOrgCode.setValue(null);
													witholdingCostDept.setDisabled(true);
													witholdingCostDept.setValue(null);
												}
											}
										}
									}, {
										xtype : 'linkagecomboselector',
										eventType : ['focus'],// 一般callparent包含focus事件，如果有callparent,则focus事件可不用传递
										itemId : 'Foss_baseinfo_BigRegion_ID',
										store : Ext.create('Foss.baseinfo.commonSelector.BigRegionStore'),// 从外面传入
										columnWidth : .3,
										fieldLabel : pay.payable.i18n('foss.stl.pay.queryBillPayable.largeRegion'),
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
										fieldLabel : pay.payable.i18n('foss.stl.pay.queryBillPayable.smallRegion'),
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
										fieldLabel : pay.payable.i18n('foss.stl.pay.queryBillPayable.payableOrgName'),
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
										xtype : 'combobox',
										name : 'customerType',
										columnWidth : .3,
										fieldLabel : pay.payable.i18n('foss.stl.pay.queryBillPayable.customerType'),
										store : Ext.create('Foss.pay.payable.CustomerTypeStore'),
										queryModel : 'local',
										value : '00',
										displayField : 'customerTypeName',
										valueField : 'customerTypeCode',
										listeners : {
											'change' : function(th, newValue, oldValue) {
												// 声明变量
												var form, customerCode, vehAgencyCode, airAgenyCode, airlineName,landStowage, packagingCode,leaseddriver,truckCustomerName,truckCustomerCode;
												// 获取表单
												form = th.up('form').getForm();
												customerCode = form.findField('customerCode');
												vehAgencyCode = form.findField('vehAgencyCode');
												airAgenyCode = form.findField('airAgenyCode');
												airlineName = form.findField('airlineName');
												landStowage = form.findField('landStowage');
												packagingCode = form.findField('packagingCode');
												leaseddriver = form.findField('leaseddriver');
												homesupply = form.findField('homesupply');	
												truckCustomerName = form.findField('truckCustomerName');	
												truckCustomerCode = form.findField('truckCustomerCode');
												var arrCode = ['01','02','03','04','05','06','07','08','09','10'];
												var arrEl = [customerCode,
												             vehAgencyCode,
												             airAgenyCode,
												             airlineName,
												             landStowage,
												             packagingCode,
												             leaseddriver,
												             homesupply,
												             truckCustomerName,
												             truckCustomerCode];
												
												for(var i=0;i<arrCode.length;i++) { 
													if(newValue == '00'){
														if(arrCode[i] == '01'){ // 全部
															arrEl[i].show();
															arrEl[i].setReadOnly(true);
															arrEl[i].setValue(null);
														}else {
															arrEl[i].hide();
															arrEl[i].setReadOnly(true);
															if(arrCode[i]!='10'){
																arrEl[i].setValue(null);
															}
														}
													}else{
														if(arrCode[i] == newValue && newValue=='09'){
															arrEl[i].show();
															arrEl[i+1].show();
															arrEl[i].setReadOnly(false);
															arrEl[i+1].setReadOnly(false);
															if(arrCode[i]!='10'){
																arrEl[i].setValue(null);
															}
														}else if (arrCode[i] == newValue){ // 选择某种客户
															arrEl[i].show();
															arrEl[i].setReadOnly(false);
															if(arrCode[i]!='10'){
																arrEl[i].setValue(null);
															}
														}else {
															if(i!=9){
																arrEl[i].hide();
																arrEl[i].setReadOnly(true);
															}
															if(i=='8'){
																arrEl[i+1].hide();
																arrEl[i+1].setReadOnly(true);
															}
															if(arrCode[i]!='10'){
																arrEl[i].setValue(null);
															}
														}
													}
												}
												
											
											}
										}
									}, {
										xtype : 'commoncustomerselector',
										singlePeopleFlag : 'Y',
										name : 'customerCode',
										listWidth : 300,
										all : 'true',
										readOnly : true,
										fieldLabel : pay.payable.i18n('foss.stl.pay.queryBillPayable.customerName'),
										columnWidth : .3
									}, {
										xtype : 'commonLdpAgencyCompanySelector',
										fieldLabel : pay.payable.i18n('foss.stl.pay.queryBillPayable.landStowage'),
										name : 'landStowage',
										isPaging : true,// 分页
										columnWidth : .3,
										hidden : true
									}, {
										xtype : 'commonairagentselector',
										all : 'true',
										listWidth : 300,
										fieldLabel : pay.payable.i18n('foss.stl.pay.queryBillPayable.airAgeny'),
										columnWidth : .3,
										name : 'airAgenyCode',
										isPaging : true,// 分页
										hidden : true
									}, {
										xtype : 'commonvehagencycompselector',
										all : 'true',
										listWidth : 300,
										fieldLabel : pay.payable.i18n('foss.stl.pay.queryBillPayable.vehAgency'),
										columnWidth : .3,
										name : 'vehAgencyCode',
										isPaging : true,// 分页
										hidden : true
									}, {
										xtype : 'commonairlinesselector',
										all : 'true',
										listWidth : 300,
										fieldLabel : pay.payable.i18n('foss.stl.pay.queryBillPayable.airline'),
										columnWidth : .3,
										name : 'airlineName',
										isPaging : true,// 分页
										hidden : true
									}, {
										xtype:'dynamicPackagingSupplierSelector',
									    listWidth:300,
									    name:'packagingCode',
									    fieldLabel : pay.payable.i18n('foss.stl.pay.common.packagingSupplier'),
									    columnWidth : .3,
									    active:'Y',
									    hidden:true
								 	}, {
										xtype:'commonMutiSelector',
										selector:Ext.create('Foss.commonSelector.LeasedDriverSelector',{
									    	fieldLabel : pay.payable.i18n('foss.stl.pay.common.leaseddriver'),
										    active:'Y',
									    }),
									    name:'leaseddriver',
									    fieldLabel : pay.payable.i18n('foss.stl.pay.common.leaseddriver'),
									    columnWidth : .3,
									    hidden:true
								 	}, {
										xtype:'supplierSelector',//家裝代理
									    listWidth:300,
									    name:'homesupply',
									    fieldLabel : pay.payable.i18n('foss.stl.pay.common.homesupply'),
									    columnWidth : .3,
									    active:'Y',
									    hidden:true
								 	}, {
										xtype:'textareafield',//外请车合并付款 caixiao--340403
									    listWidth:300,
									    height : 22,
									    minLength:2,
									    name:'truckCustomerName',
									    fieldLabel : pay.payable.i18n('客户名称'),
									    columnWidth : .3,
									    active:'Y',
									    hidden:true
								 	}, {
										xtype:'textareafield',//外请车合并付款 caixiao--340403
										height : 22,
									    listWidth:300,
									    name:'truckCustomerCode',
									    fieldLabel : pay.payable.i18n('客户编码'),
									    columnWidth : .3,
									    active:'Y',
									    hidden:true
								 	},{
										xtype : 'combobox',
										name : 'effectiveStatus',
										fieldLabel : pay.payable.i18n('foss.stl.pay.queryBillPayable.effectiveStatus'),
										labelWidth : 85,
										editable : false,
										store : FossDataDictionary.getDataDictionaryStore('BILL_PAYABLE__EFFECTIVE_STATUS', null,
												{
													'valueCode' : '',
													'valueName' : pay.payable.i18n('foss.stl.pay.queryBillPayable.all')
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
										fieldLabel : pay.payable.i18n('foss.stl.pay.queryBillPayable.payStatus'),
										editable : false,
										value : '',
										store : FossDataDictionary.getDataDictionaryStore('BILL_PAYABLE__PAY_STATUS', null, {
													'valueCode' : '',
													'valueName' : pay.payable.i18n('foss.stl.pay.queryBillPayable.all')
												}),
										queryModel : 'local',
										displayField : 'valueName',
										valueField : 'valueCode',
										columnWidth : .3
									}, {
										xtype : 'combobox',
										name : 'writeoffStatus',
										fieldLabel : pay.payable.i18n('foss.stl.pay.queryBillPayable.writeoffStatus'),
										editable : false,
										labelWidth : 85,
										store : Ext.create('Foss.payable.PaybleWriteoffStatus'),
										queryModel : 'local',
										displayField : 'valueName',
										valueField : 'valueCode',
										value : '',
										columnWidth : .3
									}, {
										xtype : 'combobox',
										name : 'approveStatus',
										fieldLabel : pay.payable.i18n('foss.stl.pay.queryBillPayable.approveStatus'),
										labelWidth : 85,
										editable : false,
										value : '',
										store : FossDataDictionary.getDataDictionaryStore('BILL_PAYABLE__APPROVE_STATUS', null, {
													'valueCode' : '',
													'valueName' : pay.payable.i18n('foss.stl.pay.queryBillPayable.all')
												}),
										queryModel : 'local',
										displayField : 'valueName',
										valueField : 'valueCode',
										columnWidth : .3
									}, {
										xtype : 'combobox',
										name : 'active',
										fieldLabel : pay.payable.i18n('foss.stl.pay.common.active'),
										labelWidth : 85,
										editable : false,
										value : 'Y',
										/*
										 * ISSUE-3086 应收单、应付单、还款单、现金收款单界面查询条件中
										 * 是否有效 默认为有效状态。 杨书硕 2013-06-27
										 */
										store : FossDataDictionary.getDataDictionaryStore('FOSS_ACTIVE', null, {
													'valueCode' : '',
													'valueName' : pay.payable.i18n('foss.stl.pay.queryBillPayable.all')
												}),
										queryModel : 'local',
										displayField : 'valueName',
										valueField : 'valueCode',
										columnWidth : .3
									}, {
										xtype : 'combobox',
										name : 'isInit',
										fieldLabel : pay.payable.i18n('foss.stl.pay.common.isInit'),
										labelWidth : 85,
										editable : false,
										value : '',
										store : FossDataDictionary.getDataDictionaryStore('FOSS_BOOLEAN', null, {
													'valueCode' : '',
													'valueName' : pay.payable.i18n('foss.stl.pay.queryBillPayable.all')
												}),
										queryModel : 'local',
										displayField : 'valueName',
										valueField : 'valueCode',
										columnWidth : .3
									}, {
										xtype : 'combobox',
										name : 'isSign',
										fieldLabel : pay.payable.i18n('foss.stl.pay.common.isSign'),
										labelWidth : 85,
										editable : false,
										value : '',
										store : FossDataDictionary.getDataDictionaryStore('FOSS_BOOLEAN', null, {
													'valueCode' : '',
													'valueName' : pay.payable.i18n('foss.stl.pay.queryBillPayable.all')
												}),
										queryModel : 'local',
										displayField : 'valueName',
										valueField : 'valueCode',
										columnWidth : .3
									}, {
										xtype : 'combobox',
										name : 'productCode',
										fieldLabel : pay.payable.i18n('foss.stl.pay.queryBillPayable.productCode'),
										store : Ext.create('Foss.pkp.ProductStore'),
										multiSelect : true,
										queryMode : 'local',
										displayField : 'name',
										valueField : 'code',
										columnWidth : .3
									},{
								    	xtype: 'dynamicorgcombselector',
										name:'createOrgCode',
								        fieldLabel:  pay.payable.i18n('foss.stl.pay.rentCarForPayReport.rentCarDeptName'),
								        allowblank:true,
										listWidth:300,//设置下拉框宽度
										isPaging:true, //分页
							    		columnWidth : .3,
							    		disabled:true
							    	}, {
							        	xtype:'commoncostcenterdeptSelector',
							    		name:'witholdingCostDept',
							            fieldLabel: pay.payable.i18n('foss.stl.pay.queryBillPayable.witholdingCostDept'),
							            allowblank:true,
							    		listWidth:300,//设置下拉框宽度
							    		isPaging:true,
							    		labelWidth:110,
							    		columnWidth : .3,
							    		disabled:true
							    	},{
										border : 1,
										xtype : 'container',
										columnWidth : 1,
										defaultType : 'button',
										layout : 'column',
										items : [{
													text : pay.payable.i18n('foss.stl.pay.common.reset'),
													columnWidth : .1,
													handler : pay.payable.queryReset
												}, {
													xtype : 'container',
													border : false,
													html : '&nbsp;',
													columnWidth : .7
												}, {
													text : pay.payable.i18n('foss.stl.pay.common.query'),
													cls : 'yellow_button',
													columnWidth : .1,
													handler : function() {
														var form = this.up('form').getForm();
														var me = this;
														pay.payable.queryByDate(form, me)
													}
												}]
									}]
						}]
			}, {
				title : pay.payable.i18n('foss.stl.pay.common.queryByNo'),
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
								fieldLabel : pay.payable.i18n('foss.stl.pay.queryBillPayable.billNo'),
								emptyText : pay.payable.i18n('foss.stl.pay.queryBillPayable.billNoEmptyText'),
								name : 'billNos',
								allowBlank : false,
								columnWidth : .7,
								labelWidth : 70,
								width : 600,
								height : 175,
								//354658-校验至14位运单号
								regex : /^((YF)?[0-9]{7,14}[;,])*((YF)?[0-9]{7,14}[;,]?)$/i,
								regexText : pay.payable.i18n('foss.stl.pay.queryBillPayable.payableNoRegexTextWarning')
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
												+ pay.payable.i18n('foss.stl.pay.queryBillPayable.billNosDesc') + '</span>'
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
											text : pay.payable.i18n('foss.stl.pay.common.reset'),
											columnWidth : .08,
											handler : pay.payable.reset
										}, {
											xtype : 'container',
											border : false,
											html : '&nbsp;',
											columnWidth : .54
										}, {
											text : pay.payable.i18n('foss.stl.pay.common.query'),
											cls : 'yellow_button',
											columnWidth : .08,
											handler : function() {
												var form = this.up('form').getForm();
												var me = this;
												pay.payable.queryByNumber(form, me)
											}
										}]
							}]
				}]
			}, {
				title : pay.payable.i18n('foss.stl.pay.common.queryBySourceNo'),
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
								fieldLabel : pay.payable.i18n('foss.stl.pay.queryBillPayable.billNo'),
								emptyText : pay.payable.i18n('foss.stl.pay.queryBillPayable.billNoEmptyText'),
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
												+ pay.payable.i18n('foss.stl.pay.queryBillPayable.billSourceNosDesc') + '</span>'
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
											text : pay.payable.i18n('foss.stl.pay.common.reset'),
											columnWidth : .08,
											handler : pay.payable.reset
										}, {
											xtype : 'container',
											border : false,
											html : '&nbsp;',
											columnWidth : .54
										}, {
											text : pay.payable.i18n('foss.stl.pay.common.query'),
											cls : 'yellow_button',
											columnWidth : .08,
											handler : function() {
												var form = this.up('form').getForm();
												var me = this;
												pay.payable.queryBySourceNumber(form, me)
											}
										}]
							}]
				}]
			}]
});

/**
 * 应付单列表
 */
Ext.define('Foss.payable.PaybleGrid', {
			extend : 'Ext.grid.Panel',
			title : pay.payable.i18n('foss.stl.pay.queryBillPayable.payablebill'),
			// hidden:true,
			frame : true,
			height : 500,
			bodyCls : 'autoHeight',
			cls : 'autoHeight',
			store : Ext.create('Foss.payable.PaybleGridStore'),
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
						header : pay.payable.i18n('foss.stl.pay.queryBillPayable.actionColumn'),
						width : 110,
						align : 'center',
						items : [{
									iconCls : 'foss_icons_stl_auditing',
									tooltip : pay.payable.i18n('foss.stl.pay.common.audit'),
									getClass : function(v, m, r, rowIndex) {
										if (!pay.payable.isPermission('/stl-web/pay/auditPayable.action')) {
											return 'statementBill_hide';
										} else if (r.get('approveStatus') == pay.payable.BILL_PAYABLE__APPROVE_STATUS__AUDIT_AGREE) {
											return 'statementBill_hide';
										} else {
											return 'foss_icons_stl_auditing';
										}
									},
									handler : function(grid, rowIndex, colIndex) {
										pay.payable.audit(grid, rowIndex, colIndex);
									}
								}, {
									iconCls : 'foss_icons_stl_fauditing',
									tooltip : pay.payable.i18n('foss.stl.pay.common.unAudit'),
									getClass : function(v, m, r, rowIndex) {
										if (!pay.payable.isPermission('/stl-web/pay/unAuditPayable.action')) {
											return 'statementBill_hide';
										} else if (r.get('approveStatus') != pay.payable.BILL_PAYABLE__APPROVE_STATUS__AUDIT_AGREE) {
											return 'statementBill_hide';
										} else {
											return 'foss_icons_stl_fauditing';
										}
									},
									handler : function(grid, rowIndex, colIndex) {
										pay.payable.unAudit(grid, rowIndex, colIndex);
									}
								}, {
									iconCls : 'foss_icons_stl_pay',
									tooltip : pay.payable.i18n('foss.stl.pay.queryBillPayable.payment'),
									getClass : function(v, m, r, rowIndex) {
										if (!pay.payable.isPermission('/stl-web/pay/payForBillPayable.action')) {
											return 'statementBill_hide';
										} else {
											return 'foss_icons_stl_pay';
										}
									},
									handler : function(grid, rowIndex, colIndex) {
										// 获取点击行数据
										var selection = grid.getStore().getAt(rowIndex);
										//如果为‘理赔出库类型时’查询应收单是否被核销----268217
										if(selection.get('billType')==pay.payable.BILL_PAYABLE__BILL_TYPE__CLAIM && 
												selection.get('claimWay') != pay.payable.BILL_PAYABLE__BILL_CLAIMSWAY_ONLINE){
											var vo = new Object();
											var manageDto = new Object();
											var resultDto = new Object();
											manageDto.waybillNo = selection.get('waybillNo');
											vo.manageDto = manageDto;
											Ext.Ajax.request({
												url : pay.realPath('queryReceivableBill.action'),
												jsonData : {
													'vo' : vo
												},
												actionMethods : 'post',
												success : function(response) {
													result = Ext.decode(response.responseText);
													if(result.vo.isNot=='success'){
													pay.payable.payForBillPayable(grid, rowIndex, colIndex);
													}else{
														Ext.Msg.alert(pay.payable.i18n('foss.stl.pay.common.alert'), '收银员未完成该单号的理赔冲应收，无法付款！');
													}
												},
												exception : function(response) {
													result = Ext.decode(response.responseText);
													Ext.Msg.alert(pay.payable.i18n('foss.stl.pay.common.alert'), '收银员未完成该单号的理赔冲应收，无法付款！');
												}
											});
										}else{
											pay.payable.payForBillPayable(grid, rowIndex, colIndex);
										}
									}
								}, {
									iconCls : 'deppon_icons_showdetail',
									tooltip : pay.payable.i18n('foss.stl.pay.queryBillPayable.entry'),
									handler : function(grid, rowIndex, colIndex) {
										pay.payable.queryDetail(grid, rowIndex, colIndex);
									}
								}]
					},{
						header : pay.payable.i18n('foss.stl.pay.queryBillPayable.payableNo'),
						dataIndex : 'payableNo'
					},{
						header : pay.payable.i18n('foss.stl.pay.queryBillPayable.claimType'),
						dataIndex : 'claimWay',//理赔方式
						renderer : function(value) {
							var displayField = null;
							if (!Ext.isEmpty(value)) {
								if (value == "1") {
									displayField = "正常理赔";
								} else if (value == "2") {
									displayField = "快速理赔";
								} else if (value == "3") {
									displayField = "在线理赔";
								}
							}
							return displayField;
						}
					},{
						header : pay.payable.i18n('foss.stl.pay.queryBillPayable.productCode'),
						dataIndex : 'productCode',
						renderer : function(value) {
							return Foss.pkp.ProductData.rendererSubmitToDisplay(value);
						}
					},{
						header : pay.payable.i18n('foss.stl.pay.queryBillPayable.approveStatus'),
						dataIndex : 'approveStatus',
						renderer : function(value) {
							var displayField = FossDataDictionary.rendererSubmitToDisplay(value, 'BILL_PAYABLE__APPROVE_STATUS');
							return displayField;
						}
					},{
						header : pay.payable.i18n('foss.stl.pay.rentCarForPayReport.withholdingStatus'),
						dataIndex : 'workflowNo',
						renderer : function(value) {
							if(Ext.isEmpty(value)){
								return pay.payable.i18n('foss.stl.pay.queryBillPayable.withholdingStatusNotTransfer');
							}else{
								return pay.payable.i18n('foss.stl.pay.queryBillPayable.withholdingStatusTransfered');
							}
						}
					}, {
						header : pay.payable.i18n('foss.stl.pay.queryBillPayable.waybillNo'),
						dataIndex : 'waybillNo'
					}, {
						header : pay.payable.i18n('foss.stl.pay.queryBillPayable.sourceBillNo'),
						dataIndex : 'sourceBillNo'
					}, {
						header : pay.payable.i18n('foss.stl.pay.queryBillPayable.paymentWorkflowNo'),
						dataIndex : 'paymentWorkflowNo'
					}, {
						header : pay.payable.i18n('foss.stl.pay.queryBillPayable.paymentTransfer'),
						dataIndex : 'paymentTransfer'
					}, {
						header : pay.payable.i18n('foss.stl.pay.queryBillPayable.payableOrgCode'),
						dataIndex : 'payableOrgCode'
					}, {
						header : pay.payable.i18n('foss.stl.pay.queryBillPayable.payableOrgName'),
						dataIndex : 'payableOrgName'
					}, {
						header : pay.payable.i18n('foss.stl.pay.queryBillPayable.customerCode'),
						dataIndex : 'customerCode'
					}, {
						header : pay.payable.i18n('foss.stl.pay.queryBillPayable.customerName'),
						dataIndex : 'customerName'
					}, {
						header : pay.payable.i18n('foss.stl.pay.queryBillPayable.customerContactName'),
						dataIndex : 'customerContactName'
					}, {
						header : pay.payable.i18n('foss.stl.pay.queryBillPayable.customerPhone'),
						dataIndex : 'customerPhone'
					}, {
						header : pay.payable.i18n('foss.stl.pay.queryBillPayable.billType'),
						dataIndex : 'billType',
						renderer : function(value) {
							var displayField = FossDataDictionary.rendererSubmitToDisplay(value, 'BILL_PAYABLE__BILL_TYPE');
							return displayField;
						}
					}, {
						header : '外请车类型',
						dataIndex : 'payableType',
						renderer : function(value, m, record) {
							var displayField = null;
							if (!Ext.isEmpty(value)) {
								if (value == "LB") {
									displayField = "押回单到达";
								} else if (value == "MH") {
									displayField = "月结";
								}
							}
							return displayField;
						}
					}, {
						header : pay.payable.i18n('foss.stl.pay.queryBillPayable.amount'),
						dataIndex : 'amount'
					}, {
						header : pay.payable.i18n('foss.stl.pay.queryBillPayable.verifyAmount'),
						dataIndex : 'verifyAmount'
					}, {
						header : pay.payable.i18n('foss.stl.pay.queryBillPayable.unverifyAmount'),
						dataIndex : 'unverifyAmount'
					}, {
						header : pay.payable.i18n('foss.stl.pay.queryBillPayable.paymentAmount'),
						dataIndex : 'paymentAmount'
					}, {
						header : pay.payable.i18n('foss.stl.pay.queryBillPayable.payStatus'),
						dataIndex : 'payStatus',
						renderer : function(value) {
							var displayField = FossDataDictionary.rendererSubmitToDisplay(value, 'BILL_PAYABLE__PAY_STATUS');
							return displayField;
						}
					}, {
						header : pay.payable.i18n('foss.stl.pay.queryBillPayable.businessDate'),
						dataIndex : 'businessDate',
						renderer : function(value) {
							return stl.dateFormat(value, 'Y-m-d  H:i:s');
						}
					}, {
						header : pay.payable.i18n('foss.stl.pay.queryBillPayable.accountDate'),
						dataIndex : 'accountDate',
						renderer : function(value) {
							return stl.dateFormat(value, 'Y-m-d  H:i:s');
						}
					}, {
						header : pay.payable.i18n('foss.stl.pay.queryBillPayable.signDate'),
						dataIndex : 'signDate',
						renderer : function(value) {
							return stl.dateFormat(value, 'Y-m-d  H:i:s');
						}
					}, {
						header : pay.payable.i18n('foss.stl.pay.queryBillPayable.active'),
						dataIndex : 'active',
						renderer : function(value) {
							if (value == 'Y') {
								return pay.payable.i18n('foss.stl.pay.common.yes');
							} else {
								return pay.payable.i18n('foss.stl.pay.common.no');
							}
						}
					}, {
						header : pay.payable.i18n('foss.stl.pay.queryBillPayable.isInit'),
						dataIndex : 'isInit',
						renderer : function(value) {
							if (value == 'Y') {
								return pay.payable.i18n('foss.stl.pay.common.yes');
							} else {
								return pay.payable.i18n('foss.stl.pay.common.no');
							}
						}
					}, {
						header : pay.payable.i18n('foss.stl.pay.queryBillPayable.statementBillNo'),
						dataIndex : 'statementBillNo'
					}, {
						header : pay.payable.i18n('foss.stl.pay.queryBillPayable.expressOrigOrgCode'),
						dataIndex : 'expressOrigOrgCode',
						hidden : true
					}, {
						header : pay.payable.i18n('foss.stl.pay.queryBillPayable.expressOrigOrgName'),
						dataIndex : 'expressOrigOrgName',
						hidden : true
					}, {
						header : pay.payable.i18n('foss.stl.pay.queryBillPayable.expressDestOrgCode'),
						dataIndex : 'expressDestOrgCode',
						hidden : true
					}, {
						header : pay.payable.i18n('foss.stl.pay.queryBillPayable.expressDestOrgName'),
						dataIndex : 'expressDestOrgName',
						hidden : true
					}, {
						header : pay.payable.i18n('foss.stl.pay.queryBillPayable.invoiceMark'),
						dataIndex : 'invoiceMark',
						hidden : true,
						renderer : function(value) {
							var displayField = FossDataDictionary.rendererSubmitToDisplay(value, 'SETTLEMENT_INVOICE_MARK');
							return displayField;
						}
					},{
                header:pay.payable.i18n('foss.stl.pay.queryBillPayable.unifiedSttlement'),
                dataIndex:'unifiedSettlement',
                hidden:true,
                renderer:function(value){
                    if(value =='Y'){
                        return pay.payable.i18n('foss.stl.pay.common.yes');
                    }else{
                        return pay.payable.i18n('foss.stl.pay.common.no');
                    }
                }
            },{
                header:pay.payable.i18n('foss.stl.pay.queryBillPayable.contractOrgCode'),
                dataIndex:'contractOrgCode',
                hidden:true
            },{
                header: pay.payable.i18n('foss.stl.pay.queryBillPayable.contractOrgName'),
                dataIndex: 'contractOrgName',
                hidden:true
            }],
			listeners:{
				'selectionchange':function(th,selected,eOpts ){
					var grid = Ext.getCmp('T_pay-manageBillPayable_content').getPaybleGrid();
					//获取当前选择条数
					var tbars = grid.dockedItems.items[3].query('textfield');
					var selectTotalAmount = 0;selectVerifyAmount=0;selectUnVerifyAmount=0;
					//计算金额
					for(var i=0;i<selected.length;i++){
						selectTotalAmount =stl.amountAdd(selectTotalAmount,selected[i].get('amount'));
						selectVerifyAmount =stl.amountAdd(selectVerifyAmount,selected[i].get('verifyAmount'));
						selectUnVerifyAmount =stl.amountAdd(selectUnVerifyAmount,selected[i].get('unverifyAmount'));
					}
					tbars[4].setValue(selected.length);
					tbars[5].setValue(selectTotalAmount);
					tbars[6].setValue(selectVerifyAmount);
					tbars[7].setValue(selectUnVerifyAmount);
				}
			},
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
										columnWidth : .81
									},{
										xtype : 'button',
										text : '取消限制',
										columnWidth : .09,										
										hidden : !pay.payable.isPermission('/stl-web/pay/updateBillPayableAutoLimitRestore.action'),
										handler : pay.payable.cancleLimit
									}, {
										xtype : 'button',
										text : pay.payable.i18n('foss.stl.pay.common.export'),
										columnWidth : .09,
										handler : pay.payable.payForBillExport
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
										fieldLabel : pay.payable.i18n('foss.stl.pay.queryBillPayable.total')
									}, {
										xtype : 'textfield',
										readOnly : true,
										name : 'totalAmount',
										columnWidth : .2,
										labelWidth : 40,
										fieldLabel : pay.payable.i18n('foss.stl.pay.queryBillPayable.amount')
									}, {
										xtype : 'textfield',
										readOnly : true,
										name : 'totalVerifyAmount',
										columnWidth : .2,
										labelWidth : 80,
										fieldLabel : pay.payable.i18n('foss.stl.pay.queryBillPayable.verifyAmount')
									}, {
										xtype : 'textfield',
										readOnly : true,
										name : 'totalUnVerifyAmount',
										columnWidth : .2,
										labelWidth : 80,
										fieldLabel : pay.payable.i18n('foss.stl.pay.queryBillPayable.unverifyAmount')
									},{
										xtype : 'container',
										border : false,
										html : '&nbsp;',
										columnWidth : .3,
										height:24
									},{
										xtype : 'textfield',
										readOnly : true,
										name : 'selectTotal',
										columnWidth : .2,
										labelWidth : 120,
										fieldLabel : pay.payable.i18n('foss.stl.pay.queryBillPayable.selectTotal')
									}, {
										xtype : 'textfield',
										readOnly : true,
										name : 'selectTotalAmount',
										columnWidth : .2,
										labelWidth : 100,
										fieldLabel : pay.payable.i18n('foss.stl.pay.queryBillPayable.selectTotalAmount')
									}, {
										xtype : 'textfield',
										readOnly : true,
										name : 'selectTotalVerifyAmount',
										columnWidth : .2,
										labelWidth : 100,
										fieldLabel : pay.payable.i18n('foss.stl.pay.queryBillPayable.selectTotalVerifyAmount')
									}, {
										xtype : 'textfield',
										readOnly : true,
										name : 'selectTotalUnVerifyAmount',
										columnWidth : .2,
										labelWidth : 100,
										fieldLabel : pay.payable.i18n('foss.stl.pay.queryBillPayable.selectTotalUnVerifyAmount')
									}, {
										xtype : 'standardpaging',
										store : me.store,
										columnWidth : 1,
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
										text : pay.payable.i18n('foss.stl.pay.common.audit'),
										columnWidth : .09,
										handler : pay.payable.auditBatch,
										disabled : !pay.payable.isPermission('/stl-web/pay/auditPayable.action'),
										hidden : !pay.payable.isPermission('/stl-web/pay/auditPayable.action')
									}, {
										xtype : 'button',
										text : pay.payable.i18n('foss.stl.pay.common.unAudit'),
										columnWidth : .09,
										handler : pay.payable.unAuditBatch,
										disabled : !pay.payable.isPermission('/stl-web/pay/unAuditPayable.action'),
										hidden : !pay.payable.isPermission('/stl-web/pay/unAuditPayable.action')
									}, {
										xtype : 'button',
										text : pay.payable.i18n('foss.stl.pay.queryBillPayable.payment'),
										columnWidth : .09,
										handler : function(grid, rowIndex, colIndex) {
											var paybleGrid, selection;
											// 获取表格组件
											paybleGrid = Ext.getCmp('T_pay-manageBillPayable_content').getPaybleGrid();
											selection = paybleGrid.getSelectionModel().getSelection();
											// 如果没有选择数据，则提示
											if (selection.length == 0) {
												Ext.Msg.alert(pay.payable.i18n('foss.stl.pay.common.alert'), pay.payable
																.i18n('foss.stl.pay.queryBillPayable.unSelectedToPayWarning'));
												return false;
											}
											//如果为‘理赔出库类型时’查询应收单是否被核销----268217
											for(var i = 0; i < selection.length; i++){
											if(selection[i].get('billType')==pay.payable.BILL_PAYABLE__BILL_TYPE__CLAIM && 
													selection[i].get('claimWay') != pay.payable.BILL_PAYABLE__BILL_CLAIMSWAY_ONLINE){
												var vo = new Object();
												var manageDto = new Object();
												var resultDto = new Object();
												manageDto.waybillNo = selection[i].get('waybillNo');
												vo.manageDto = manageDto;
												Ext.Ajax.request({
													url : pay.realPath('queryReceivableBill.action'),
													jsonData : {
														'vo' : vo
													},
													actionMethods : 'post',
													success : function(response) {
														result = Ext.decode(response.responseText);
														if(result.vo.isNot=='success'){
															pay.payable.payForBillPayableBatch();
														}else{
															Ext.Msg.alert(pay.payable.i18n('foss.stl.pay.common.alert'), '收银员未完成该单号的理赔冲应收，无法付款！');
														}
													},
													exception : function(response) {
														Ext.Msg.alert(pay.payable.i18n('foss.stl.pay.common.alert'), '收银员未完成该单号的理赔冲应收，无法付款！');
													}
												});
											}else{
												pay.payable.payForBillPayableBatch();
											}
											}
										},
										disabled : !pay.payable.isPermission('/stl-web/pay/payForBillPayable.action'),
										hidden : !pay.payable.isPermission('/stl-web/pay/payForBillPayable.action')
									}]
						}];
				me.callParent();
			}
		});

/**
 * 应付单明细记录
 */
Ext.define('Foss.payable.PaybleGridEntry', {
			extend : 'Ext.grid.Panel',
			frame : true,
			title : pay.payable.i18n('foss.stl.pay.queryBillPayable.payableEntry'),
			height : 150,
			bodyCls : 'autoHeight',
			cls : 'autoHeight',
			store : Ext.create('Foss.payable.PaybleGridEntryStore'),
			viewConfig : {
				enableTextSelection : true
				// 设置行可以选择，进而可以复制
			},
			defaults : {
				align : 'center',
				margin : '5 0 5 0'
			},
			columns : [{
						header : pay.payable.i18n('foss.stl.pay.queryBillPayable.payableNo'),
						dataIndex : 'payableNo'
					}, {
						header : pay.payable.i18n('foss.stl.pay.queryBillPayable.waybillNo'),
						dataIndex : 'waybillNo'
					}, {
						header : pay.payable.i18n('foss.stl.pay.queryBillPayable.payableOrgCode'),
						dataIndex : 'payableOrgCode'
					}, {
						header : pay.payable.i18n('foss.stl.pay.queryBillPayable.payableOrgName'),
						dataIndex : 'payableOrgName'
					}, {
						header : pay.payable.i18n('foss.stl.pay.queryBillPayable.createOrgName'),
						dataIndex : 'createOrgName'
					}, {
						header : pay.payable.i18n('foss.stl.pay.queryBillPayable.createOrgCode'),
						dataIndex : 'createOrgCode'
					}, {
						header : pay.payable.i18n('foss.stl.pay.queryBillPayable.frozenStatus'),
						dataIndex : 'frozenStatus',
						renderer : function(value) {
							var displayField = FossDataDictionary.rendererSubmitToDisplay(value, 'BILL_PAYABLE__FROZEN_STATUS');
							return displayField;
						}
					}, {
						header : pay.payable.i18n('foss.stl.pay.queryBillPayable.customerCode'),
						dataIndex : 'customerCode'
					}, {
						header : pay.payable.i18n('foss.stl.pay.queryBillPayable.customerName'),
						dataIndex : 'customerName'
					}, {
						header : pay.payable.i18n('foss.stl.pay.queryBillPayable.billType'),
						dataIndex : 'billType',
						renderer : function(value) {
							var displayField = FossDataDictionary.rendererSubmitToDisplay(value, 'BILL_PAYABLE__BILL_TYPE');
							return displayField;
						}
					}, {
						header : pay.payable.i18n('foss.stl.pay.queryBillPayable.amount'),
						dataIndex : 'amount'
					}, {
						header : pay.payable.i18n('foss.stl.pay.queryBillPayable.verifyAmount'),
						dataIndex : 'verifyAmount'
					}, {
						header : pay.payable.i18n('foss.stl.pay.queryBillPayable.unverifyAmount'),
						dataIndex : 'unverifyAmount'
					}, {
						header : pay.payable.i18n('foss.stl.pay.queryBillPayable.businessDate'),
						dataIndex : 'businessDate',
						renderer : function(value) {
							return stl.dateFormat(value, 'Y-m-d');
						}
					}, {
						header : pay.payable.i18n('foss.stl.pay.queryBillPayable.accountDate'),
						dataIndex : 'accountDate',
						renderer : function(value) {
							return stl.dateFormat(value, 'Y-m-d');
						}
					}, {
						header : pay.payable.i18n('foss.stl.pay.queryBillPayable.effectiveStatus'),
						dataIndex : 'effectiveStatus',
						renderer : function(value) {
							var displayField = FossDataDictionary
									.rendererSubmitToDisplay(value, 'BILL_PAYABLE__EFFECTIVE_STATUS');
							return displayField;
						}
					}, {
						header : pay.payable.i18n('foss.stl.pay.queryBillPayable.payStatus'),
						dataIndex : 'payStatus',
						renderer : function(value) {
							var displayField = FossDataDictionary.rendererSubmitToDisplay(value, 'BILL_PAYABLE__PAY_STATUS');
							return displayField;
						}
					}, {
						header : pay.payable.i18n('foss.stl.pay.queryBillPayable.approveStatus'),
						dataIndex : 'approveStatus',
						renderer : function(value) {
							var displayField = FossDataDictionary.rendererSubmitToDisplay(value, 'BILL_PAYABLE__APPROVE_STATUS');
							return displayField;
						}
					}, {
						header : pay.payable.i18n('foss.stl.pay.queryBillPayable.active'),
						dataIndex : 'active',
						renderer : function(value) {
							if (value == 'Y') {
								return pay.payable.i18n('foss.stl.pay.common.yes');
							} else {
								return pay.payable.i18n('foss.stl.pay.common.no');
							}
						}
					}, {
						header : pay.payable.i18n('foss.stl.pay.queryBillPayable.isRedBack'),
						dataIndex : 'isRedBack',
						renderer : function(value) {
							if (value == 'Y') {
								return pay.payable.i18n('foss.stl.pay.common.yes');
							} else {
								return pay.payable.i18n('foss.stl.pay.common.no');
							}
						}
					}, {
						header : pay.payable.i18n('foss.stl.pay.queryBillPayable.createTime'),
						dataIndex : 'createTime',
						renderer : function(value) {
							return stl.dateFormat(value, 'Y-m-d');
						}
					}, {
						header : pay.payable.i18n('foss.stl.pay.queryBillPayable.modifyTime'),
						dataIndex : 'modifyTime',
						renderer : function(value) {
							return stl.dateFormat(value, 'Y-m-d');
						}
					}, {
						header : pay.payable.i18n('foss.stl.pay.queryBillPayable.createUserName'),
						dataIndex : 'createUserName'
					}, {
						header : pay.payable.i18n('foss.stl.pay.queryBillPayable.paymentCategories'),
						dataIndex : 'paymentCategories',
						renderer : function(value) {
							var displayField = FossDataDictionary.rendererSubmitToDisplay(value,
									'BILL_PAYABLE__PAYMENT_CATEGORIES');
							return displayField;
						}
					}, {
						header : pay.payable.i18n('foss.stl.pay.queryBillPayable.paymentNo'),
						dataIndex : 'paymentNo'
					}, {
						header : pay.payable.i18n('foss.stl.pay.queryBillPayable.createType'),
						dataIndex : 'createType',
						renderer : function(value) {
							var displayField = FossDataDictionary.rendererSubmitToDisplay(value, 'SETTLEMENT__CREATE_TYPE');
							return displayField;
						}
					}, {
						header : pay.payable.i18n('foss.stl.pay.queryBillPayable.signDate'),
						dataIndex : 'signDate',
						renderer : function(value) {
							return stl.dateFormat(value, 'Y-m-d');
						}
					}, {
						header : pay.payable.i18n('foss.stl.pay.queryBillPayable.notes'),
						dataIndex : 'notes'
					}, {
						header : pay.payable.i18n('foss.stl.pay.queryBillPayable.invoiceMark'),
						dataIndex : 'invoiceMark',
						renderer : function(value) {
							var displayField = FossDataDictionary.rendererSubmitToDisplay(value, 'SETTLEMENT_INVOICE_MARK');
							return displayField;
						}
					},{
                        header:pay.payable.i18n('foss.stl.pay.queryBillPayable.unifiedSttlement'),
                        dataIndex:'unifiedSettlement',
                        renderer:function(value){
                        if(value =='Y'){
                            return pay.payable.i18n('foss.stl.pay.common.yes');
                            }else{
                            return pay.payable.i18n('foss.stl.pay.common.no');
                            }
                        }
                    },{
                        header:pay.payable.i18n('foss.stl.pay.queryBillPayable.contractOrgCode'),
                        dataIndex:'contractOrgCode'
                    },{
                        header: pay.payable.i18n('foss.stl.pay.queryBillPayable.contractOrgName'),
                        dataIndex: 'contractOrgName'
                }]
		});

// 初始化界面
Ext.onReady(function() {
			Ext.QuickTips.init();
			Ext.Ajax.timeout = 600000;
			var queryInfoTab = Ext.create('Foss.payable.QueryInfoTab');
			var paybleGrid = Ext.create('Foss.payable.PaybleGrid');
			var paybleGridEntry = Ext.create('Foss.payable.PaybleGridEntry');

			Ext.create('Ext.panel.Panel', {
						id : 'T_pay-manageBillPayable_content',
						cls : "panelContentNToolbar",
						bodyCls : 'panelContentNToolbar-body',
						layout : 'auto',
						getQueryInfoTab : function() {
							return queryInfoTab;
						},
						getPaybleGrid : function() {
							return paybleGrid;
						},
						getPaybleGridEntry : function() {
							return paybleGridEntry;
						},
						items : [queryInfoTab, paybleGrid, paybleGridEntry],
						renderTo : 'T_pay-manageBillPayable-body'
						// 角色权限问题
						,
						listeners : {
							'boxready' : function(th) {
								var roles = stl.currentUserDepts;
								var queryByDateTab = th.getQueryInfoTab().items.items[0].items.items[0];
								// var deptcombselector =
								// queryByDateTab.items.items[3];
								var dept = queryByDateTab.getForm().findField("payableOrgCode");
								// dept.setCombValue
								// (stl.currentDept.name,stl.currentDept.code);
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
