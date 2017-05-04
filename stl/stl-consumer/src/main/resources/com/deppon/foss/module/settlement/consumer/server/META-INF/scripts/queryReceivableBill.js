consumer.WAYBILLNO = 'bilno'
consumer.DATE = 'date'

/**
 * 
 * @param {} 产品类型
 * @return {}此处如果有全选，则需要转化下
 */
consumer.queryReceivableBill.convertProductCode = function(productCodes){
	if(!Ext.isEmpty(productCodes)){
		var productCodeList = [];
		for(var i=0;i<productCodes.length;i++){
			//如果产品类型中存在值为空，则表明为全部，那么默认全部查询
			if(Ext.isEmpty(productCodes[i])){
				productCodeList=[];
				break;
			}else{
				productCodeList.push(productCodes[i]);
			}
		}
		return productCodeList;
	}else{
		return [];
	}
}
/**
 * 提示信息
 * @param {} message 
 * @param {} yesFn
 * @param {} noFn
 */
consumer.queryReceivableBill.billReceivableConfirmAlert = function(message,yesFn,noFn){
	Ext.Msg.confirm(consumer.queryReceivableBill.i18n('foss.stl.consumer.common.warmTips'),message,function(o){
		if(o=='yes'){
			yesFn();
		}else{
			noFn();
		}
	});
};

// 查看详细双击函数
consumer.checkReceivableBillDetailsInforByDoubleClick = function(record, type) {

	if (type == 'date') {
		var win = Ext.getCmp('Foss_Stlyuscyings_BillNoQueryInfoGrid_Id').getDetailWindow();
	} else if (type == 'bilno') {
		var win = Ext.getCmp('Foss_Stlyuscyings_BillNoQueryInfoGrid_Id').getDetailWindow();
	} else {
		return false;
	}
	var form = win.down('form').getForm();	
	form.findField('waybillNo').setValue(record.get('waybillNo'));
	form.findField('transportFee').setValue(record.get('transportFee'));
	var createType = (record.get('createType'));
	if (createType == 'A') {
		form.findField('createType').setValue(consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.createTypeA'));
	} else if (createType == 'M') {
		form.findField('createType').setValue(consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.createTypeM'));
	} else {
		form.findField('createType').setValue(createType);
	}
	form.findField('sourceBillNo').setValue(record.get('sourceBillNo'));
	var sourceBillTypeValue = record.get('sourceBillType');
	if (sourceBillTypeValue == 'W') {
		form.findField('sourceBillType').setValue(consumer.queryReceivableBill.i18n('foss.stl.consumer.common.sourceBillTypeW'));
	} else if (sourceBillTypeValue == 'R') {
		form.findField('sourceBillType').setValue(consumer.queryReceivableBill.i18n('foss.stl.consumer.common.sourceBillTypeR'));
	} else if (sourceBillTypeValue == 'M') {
		form.findField('sourceBillType').setValue(consumer.queryReceivableBill.i18n('foss.stl.consumer.common.sourceBillTypeM'));
	} else if (sourceBillTypeValue == 'E') {
		form.findField('sourceBillType').setValue(consumer.queryReceivableBill.i18n('foss.stl.consumer.common.sourceBillTypeE'));
	}  else {
		form.findField('sourceBillType').setValue(sourceBillTypeValue);
	}
	var billTypeValue = record.get('billType');
	form.findField('billType').setValue(FossDataDictionary.rendererSubmitToDisplay(billTypeValue, 'BILL_RECEIVABLE__BILL_TYPE'));
	form.findField('receivableOrgCode').setValue(record.get('receivableOrgCode'));
	form.findField('receivableOrgName').setValue(record.get('receivableOrgName'));
	form.findField('generatingOrgCode').setValue(record.get('generatingOrgCode'));
	form.findField('generatingComCode').setValue(record.get('generatingComCode'));
	form.findField('generatingComName').setValue(record.get('generatingComName'));
	form.findField('dunningOrgCode').setValue(record.get('dunningOrgCode'));
	form.findField('dunningOrgName').setValue(record.get('dunningOrgName'));
	form.findField('origOrgCode').setValue(record.get('origOrgCode'));
	form.findField('origOrgName').setValue(record.get('origOrgName'));
	form.findField('destOrgCode').setValue(record.get('destOrgCode'));
	form.findField('destOrgName').setValue(record.get('destOrgName'));
	form.findField('deliveryCustomerCode').setValue(record.get('deliveryCustomerCode'));
	form.findField('deliveryCustomerName').setValue(record.get('deliveryCustomerName'));
	form.findField('receiveCustomerCode').setValue(record.get('receiveCustomerCode'));
	form.findField('receiveCustomerName').setValue(record.get('receiveCustomerName'));
	form.findField('generatingOrgName').setValue(record.get('generatingOrgName'));
	if (record.get('currencyCode') == 'RMB') {
		form.findField('currencyCode').setValue(consumer.queryReceivableBill.i18n('foss.stl.consumer.common.currencyCodeRMB'));
	} else if (record.get('currencyCode') == 'HKD') {
		form.findField('currencyCode').setValue(consumer.queryReceivableBill.i18n('foss.stl.consumer.common.currencyCodeHKD'));
	} else if (record.get('currencyCode') == 'NTD') {
		form.findField('currencyCode').setValue(consumer.queryReceivableBill.i18n('foss.stl.consumer.common.currencyCodeNTD'));
	} else if (record.get('currencyCode') == 'USD') {
		form.findField('currencyCode').setValue(consumer.queryReceivableBill.i18n('foss.stl.consumer.common.currencyCodeUSD'));
	} else {
		form.findField('currencyCode').setValue(record.get('currencyCode'));
	}
	if (!Ext.isEmpty(record.get('conrevenDate'))) {
		form.findField('conrevenDate').setValue(Ext.Date.format(new Date(record.get('conrevenDate')), 'Y-m-d H:i:s'));
	} else {
		form.findField('conrevenDate').setValue(record.get('conrevenDate'));
	}	
	var productName = Foss.pkp.ProductData.rendererSubmitToDisplay(record.get('productCode'));
	form.findField('productCode').setValue(productName);

	form.findField('productId').setValue(record.get('productId'));
	form.findField('pickupFee').setValue(record.get('pickupFee'));
	form.findField('deliveryGoodsFee').setValue(record.get('deliveryGoodsFee'));
	form.findField('packagingFee').setValue(record.get('packagingFee'));
	form.findField('codFee').setValue(record.get('codFee'));
	form.findField('insuranceFee').setValue(record.get('insuranceFee'));
	form.findField('otherFee').setValue(record.get('otherFee'));
	form.findField('valueAddFee').setValue(record.get('valueAddFee'));
	form.findField('promotionsFee').setValue(record.get('promotionsFee'));
	form.findField('goodsName').setValue(record.get('goodsName'));
	form.findField('goodsVolumeTotal').setValue(record.get('goodsVolumeTotal'));
	form.findField('billWeight').setValue(record.get('billWeight'));
	form.findField('goodsQtyTotal').setValue(record.get('goodsQtyTotal'));
	form.findField('statementBillNo').setValue(record.get('statementBillNo'));
	form.findField('targetOrgCode').setValue(record.get('targetOrgCode'));
	if (record.get('isRedBack') == 'Y') {
		form.findField('isRedBack').setValue(consumer.queryReceivableBill.i18n('foss.stl.consumer.common.yes'));
	} else {
		form.findField('isRedBack').setValue(consumer.queryReceivableBill.i18n('foss.stl.consumer.common.no'));
	}
	if (record.get('isInit') == 'Y') {
		form.findField('isInit').setValue(consumer.queryReceivableBill.i18n('foss.stl.consumer.common.yes'));
	} else {
		form.findField('isInit').setValue(consumer.queryReceivableBill.i18n('foss.stl.consumer.common.no'));
	}
	form.findField('createUserCode').setValue(record.get('createUserCode'));
	form.findField('createUserName').setValue(record.get('createUserName'));
	form.findField('createOrgCode').setValue(record.get('createOrgCode'));
	form.findField('createOrgName').setValue(record.get('createOrgName'));
	if (record.get('createTime') != null) {
		form.findField('createTime').setValue(Ext.Date.format(new Date(record.get('createTime')), 'Y-m-d H:i:s'));
	} else {
		form.findField('createTime').setValue(record.get('createTime'));
	}
	if (record.get('modifyTime') != null) {
		form.findField('modifyTime').setValue(Ext.Date.format(new Date(record.get('modifyTime')), 'Y-m-d H:i:s'));
	} else {
		form.findField('modifyTime').setValue(record.get('modifyTime'));
	}
	form.findField('modifyUserCode').setValue(record.get('modifyUserCode'));
	form.findField('modifyUserName').setValue(record.get('modifyUserName'));
	if (record.get('unlockDateTime') != null) {
		form.findField('unlockDateTime').setValue(Ext.Date.format(new Date(record.get('unlockDateTime')), 'Y-m-d H:i:s'));
	} else {
		form.findField('unlockDateTime').setValue(record.get('unlockDateTime'));
	}
	form.findField('lockCustomerCode').setValue(record.get('lockCustomerCode'));
	form.findField('lockCustomerName').setValue(record.get('lockCustomerName'));
	form.findField('collectionType').setValue(FossDataDictionary.rendererSubmitToDisplay(record.get('collectionType'), 'BILL_RECEIVABLE__COLLECTION_TYPE'));
	form.findField('collectionName').setValue(record.get('collectionName'));
	form.findField('lockCustomerCode').setValue(record.get('lockCustomerCode'));
	form.findField('auditUserCode').setValue(record.get('auditUserCode'));
	form.findField('auditUserName').setValue(record.get('auditUserName'));
	if (record.get('approveStatus') == 'NA') {
		form.findField('approveStatus').setValue(consumer.queryReceivableBill.i18n('foss.stl.consumer.common.approveStatusNA'));
	} else if (record.get('approveStatus') == 'AA') {
		form.findField('approveStatus').setValue(consumer.queryReceivableBill.i18n('foss.stl.consumer.common.approveStatusAA'));
	} else {
		form.findField('approveStatus').setValue(record.get('approveStatus'));
	}
	if (record.get('auditDate') != null) {
		form.findField('auditDate').setValue(Ext.Date.format(new Date(record.get('auditDate')), 'Y-m-d H:i:s'));
	} else {
		form.findField('auditDate').setValue(record.get('auditDate'));
	}
	form.findField('disableUserCode').setValue(record.get('disableUserCode'));
	form.findField('disableUserName').setValue(record.get('disableUserName'));

	if (record.get('modifyTime') != null) {
		form.findField('modifyTime').setValue(Ext.Date.format(new Date(record.get('modifyTime')), 'Y-m-d H:i:s'));
	} else {
		form.findField('modifyTime').setValue(record.get('modifyTime'));
	}
	form.findField('notes').setValue(record.get('notes'));
	form.findField('expressOrigOrgCode').setValue(record.get('expressOrigOrgCode'));
	form.findField('expressOrigOrgName').setValue(record.get('expressOrigOrgName'));
	form.findField('expressDestOrgCode').setValue(record.get('expressDestOrgCode'));
	form.findField('expressDestOrgName').setValue(record.get('expressDestOrgName'));
	
	//发票标记		
	if(record.get('invoiceMark')!=null){
		var value=record.get('invoiceMark');
		var displayField = FossDataDictionary.rendererSubmitToDisplay (value,'SETTLEMENT_INVOICE_MARK');		
		form.findField('invoiceMark').setValue(displayField);	
	}else{
		form.findField('invoiceMark').setValue(record.get('invoiceMark'));
	}
    if(record.get('unifiedSettlement') == 'Y'){
        form.findField('unifiedSettlement').setValue(consumer.queryReceivableBill.i18n('foss.stl.consumer.common.yes'));
    }else {
        form.findField('unifiedSettlement').setValue(consumer.queryReceivableBill.i18n('foss.stl.consumer.common.no'));
    }

    form.findField('contractOrgCode').setValue(record.get('contractOrgCode'));
    form.findField('contractOrgName').setValue(record.get('contractOrgName'));

	win.show();
}

// 查看详细单击查看详细按钮函数
consumer.checkReceivableBillDetailsInfor = function(grid, rowIndex, colIndex, type) {
	var record = grid.getStore().getAt(rowIndex);
	consumer.checkReceivableBillDetailsInforByDoubleClick(record, type);
}
// 通用时间校验
consumer.dateValidation = function(form) {
	var startTime = form.findField('startDate').getValue();
	var endTime = form.findField('endDate').getValue();

	if (Ext.isEmpty(startTime)) {
		Ext.Msg.alert(consumer.queryReceivableBill.i18n('foss.stl.consumer.common.warmTips'), 
			consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.startDateIsNotNull'));
		return false;
	}

	if (Ext.isEmpty(endTime)) {
		Ext.Msg.alert(consumer.queryReceivableBill.i18n('foss.stl.consumer.common.warmTips'), 
			consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.endDateIsNotNull'));
		return false;
	}

	if (startTime > endTime) {
		Ext.Msg.alert(consumer.queryReceivableBill.i18n('foss.stl.consumer.common.warmTips'), 
			consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.endDateIsNotBeforeStart'));
		return false;
	}

	if (stl.compareTwoDate(startTime, endTime) > stl.DATELIMITDAYS_MONTH) {
		Ext.Msg.alert(consumer.queryReceivableBill.i18n('foss.stl.consumer.common.warmTips'), 
			consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.startToEndDateIsNotMaxDay') + stl.DATELIMITDAYS_MONTH+
			consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.day'));
		return false;	
	}
	
	/*BUG-38768  BUG-38779 ISSUE-3086 控制查询条件，大区、小区和部门必须填写一项
	 * 
	 * 杨书硕 
	 * 2013-6-29 上午11:50:31
	 */
	if(!(!!form.findField('bigArea').getValue()||!!form.findField('smallArea').getValue()||!!form.findField('department').getValue())){
		Ext.Msg.alert(consumer.queryReceivableBill.i18n('foss.stl.consumer.common.warmTips'),
			consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.validateRegionFailAlert'));
		return false;
	}
	return true;
}

// 导出方法
consumer.exportBillReceivable = function(queryTab) {
	var	columns,
		searchParams,
		arrayColumns,
		arrayColumnNames,
		grid;
	//进行导出提示
	Ext.MessageBox.show({
        title: consumer.queryReceivableBill.i18n('foss.stl.consumer.common.alert'),
        msg: consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.isExport'),
        buttons: Ext.MessageBox.YESNOCANCEL,
        buttonText:{ 
            yes: '默认列导出', 
            no: '自定义导出',
            cancel:'取消'
        },
        fn: function(e){
    		if (!Ext.fly('downloadAttachFileForm')) {
				var frm = document.createElement('form');
				frm.id = 'downloadAttachFileForm';
				frm.style.display = 'none';
				document.body.appendChild(frm);
			}

    		if ('TD' == queryTab) {
    			grid = Ext.getCmp('Foss_Stlyuscyings_DateQueryInfoGrid_Id');
    			if(e=='yes'){
    				//默认显示列
    				arrayColumns = ['generatingOrgName','productCode','waybillNo','sourceBillNo','sourceBillType','receivableNo',
    				                'customerName','customerCode','amount','verifyAmount','unverifyAmount','businessDate','accountDate',
    				                'transportFee','paymentType','active','isRedBack','isInit'];
    				//默认显示列名称
    				arrayColumnNames = ["收入部门","运输性质", "运单号","来源单号", "来源单据类型", "应收单编号",
    				                    "客户名称", "客户编码", "总金额", "核销金额", "未核销金额", "业务日期","记账日期",
    				                    "运费", "支付方式", "是否有效", "是否红单", "是否初始化"];									
    			}else if(e=='no'){
    				//转化列头和列明
    				columns = grid.columns;
    				arrayColumns = [];
    				arrayColumnNames = [];
    				//将前台对应列头传入到后台去
    				for(var i=1;i<columns.length;i++){
    					if(columns[i].isHidden()==false){
    						var hederName = columns[i].text;
    						var dataIndex = columns[i].dataIndex;
    						if(columns[i].text!='操作列'){
    							arrayColumns.push(dataIndex);
    							arrayColumnNames.push(hederName);
    						}
    					}
    				}
    			}else{
    				return false;
    			}
			
    			var form = Ext.getCmp('Foss_Stlyuscyings_StlyuscyingsQueryInfoTab_QueryByDate_ID').getForm();
			
    			var selectDeptType=form.getValues().selectDeptType;//部门类型
    			var isDept;
			
    			//判断用户是查询始发或者是到达（默认查始发）							
    			if('1'==selectDeptType){
    				isDept='DUNNING_ORG_CODE'
    			}else{
    				isDept='GENERATING_ORG_CODE'
    			}
			
			
    			if (!consumer.dateValidation(form)) {
    				Ext.Msg.alert(consumer.queryReceivableBill.i18n('foss.stl.consumer.common.warmTips'),
    					consumer.queryReceivableBill.i18n('foss.stl.consumer.common.validateFailAlert'));
    				return false;
    			}
	
    			var billReceivableVO = new Object();
    			var selectBusinessDate = Ext.getCmp('queryReceivableBill_BusinessData_Id').getValue();
    			var selectAccountDate = Ext.getCmp('queryReceivableBill_AccountData_Id').getValue();
    			var selectDateType;
    			if (selectBusinessDate && !selectAccountDate) {
    				selectDateType = 'BU';
    			} else if (!selectBusinessDate && selectAccountDate) {
    				selectDateType = 'AC';
    			}
			
    			var startDate = form.findField('startDate').getValue();
    			var endDate = form.findField('endDate').getValue();
    			var customerName = form.findField('customerName').getValue();
    			var airAgenyCode = form.findField('airAgenyCode').getValue();
    			var vehAgencyCode = form.findField('vehAgencyCode').getValue();
    			var airlineName = form.findField('airlineName').getValue();
    			var landStowage = form.findField('landStowage').getValue();
    			var packagingCode = form.findField('packagingCode').getValue();
			
    			var customerCode = "";
    			var arrVal = [customerName,
    			              airAgenyCode,
    			              vehAgencyCode,
    			              airlineName,
    			              landStowage,
    			              packagingCode];
    			for(var i=0;i<arrVal.length;i++) { 
    				if (!Ext.isEmpty(arrVal[i])){
    					customerCode = arrVal[i];
    				} 
    			} 
    			var params = {
    				'billReceivableVO.dto.queryTab' : 'TD',
    				'billReceivableVO.dto.selectDateType' : selectDateType,
    				'billReceivableVO.dto.startDate' : startDate,
    				'billReceivableVO.dto.endDate' : endDate,
    				'billReceivableVO.dto.department' : form.findField('department').getValue(),
    				'billReceivableVO.dto.bigArea' : form.findField('bigArea').getValue(),
    				'billReceivableVO.dto.smallArea' : form.findField('smallArea').getValue(),
    				'billReceivableVO.dto.sourceBillType' : form.findField('sourceBillType').getValue(),
    				'billReceivableVO.dto.customerCode' : customerCode,
    				'billReceivableVO.dto.billType' : form.findField('billType').getValue(),
    				'billReceivableVO.dto.active' : form.findField('active').getValue(),
    				'billReceivableVO.dto.isInit' : form.findField('isInit').getValue(),
    				'billReceivableVO.dto.isSign' : form.findField('isSign').getValue(),
    				'billReceivableVO.dto.arrayColumns':arrayColumns,
    				'billReceivableVO.dto.arrayColumnNames':arrayColumnNames,
    				'billReceivableVO.dto.isDept' : isDept,
    				'billReceivableVO.dto.productCode' : consumer.queryReceivableBill.convertProductCode(form.findField('productCode').getValue())
    			};
	
    			Ext.Ajax.request({
    				url : consumer.realPath('exportBillReceivable.action'),
    				form : Ext.fly('downloadAttachFileForm'),
    				method : 'post',
    				params : params,
    				isUpload : true,
    				success : function(response) {
    					var result = Ext.decode(response.responseText);
    					// 如果异常信息有值，则弹出提示
    					if (!Ext.isEmpty(result.errorMessage)) {
    						Ext.Msg.alert(consumer.queryReceivableBill.i18n('foss.stl.consumer.common.warmTips'), result.errorMessage);
    						return false;
    					}
    					Ext.ux.Toast.msg(consumer.queryReceivableBill.i18n('foss.stl.consumer.common.warmTips'), 
    						consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.exportSuccess'), 'success', 1000);
    				},
    				failure : function(response) {
    					Ext.ux.Toast.msg(consumer.queryReceivableBill.i18n('foss.stl.consumer.common.warmTips'), 
    						consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.exportFail'), 'error', 1000);
    				}
    			});
	
    		} else if ('WB' == queryTab) {
    			grid = Ext.getCmp('Foss_Stlyuscyings_BillNoQueryInfoGrid_Id');
    			if(e=='yes'){
				//默认显示列
    				arrayColumns = ['generatingOrgName','productCode','waybillNo','sourceBillNo','sourceBillType','receivableNo',
    				                'customerName','customerCode','amount','verifyAmount','unverifyAmount','businessDate','accountDate',
    				                'transportFee','paymentType','active','isRedBack','isInit'];
    				//默认显示列名称
    				arrayColumnNames = ["收入部门","运输性质", "运单号","来源单号", "来源单据类型", "应收单编号",
    				                    "客户名称", "客户编码", "总金额", "核销金额", "未核销金额", "业务日期","记账日期",
    				                    "运费", "支付方式", "是否有效", "是否红单", "是否初始化"];					
    			}else if(e=='no'){
    				//转化列头和列明
    				columns = grid.columns;
    				arrayColumns = [];
    				arrayColumnNames = [];
    				//将前台对应列头传入到后台去
    				for(var i=1;i<columns.length;i++){
    					if(columns[i].isHidden()==false){
    						var headerName = columns[i].text;
    						var dataIndex = columns[i].dataIndex;
    						if(columns[i].text!='操作列'){
    							arrayColumns.push(dataIndex);
    							arrayColumnNames.push(headerName);
    						}
    					}
    				}
    			}else{
    				return false;
    			}
    			//按来源单号导出
    			if(Ext.getCmp('T_consumer-queryReceivableBill_content').items.items[0].getActiveTab().name=="queryBySourceBillNo"){
				
    				var form = Ext.getCmp('Foss_BillReceivableBill_queryBySourceBillNo_ID').getForm();
				
    				if (!form.isValid()) {
    					Ext.Msg.alert(consumer.queryReceivableBill.i18n('foss.stl.consumer.common.warmTips'), 
    						consumer.queryReceivableBill.i18n('foss.stl.consumer.common.validateFailAlert'));
    					return false;
    				}
    				var wayBillNosArray = [];
    				var waybillNos = form.findField('sourceBillNos').getValue();
    				if (Ext.String.trim(waybillNos) != null	&& Ext.String.trim(waybillNos) != '') {
    					var reg = /[,;]/;
    					array = waybillNos.split(reg);
    					for (var i = 0; i < array.length; i++) {
    						if (Ext.String.trim(array[i]) != '') {
    							wayBillNosArray.push(array[i]);
    						}
    					}
    				} else {
    					Ext.Msg.alert(consumer.queryReceivableBill.i18n('foss.stl.consumer.common.warmTips'), 
    						consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.waybillNosIsNotNull'));
    					return false;
    				}
    				Ext.Ajax.request({
    					url : consumer.realPath('exportBillReceivable.action'),
    					form : Ext.fly('downloadAttachFileForm'),
    					method : 'post',
    					params : {
    						"billReceivableVO.dto.queryTab" : 'SB',
    						"billReceivableVO.sourceBillNosArray" : wayBillNosArray,
    						'billReceivableVO.dto.arrayColumns':arrayColumns,
    						'billReceivableVO.dto.arrayColumnNames':arrayColumnNames
    					},
    					isUpload : true,
    					success : function(response) {
    						var result = Ext.decode(response.responseText);
    						// 如果异常信息有值，则弹出提示
    						if (!Ext.isEmpty(result.errorMessage)) {
    							Ext.Msg.alert(consumer.queryReceivableBill.i18n('foss.stl.consumer.common.warmTips'), result.errorMessage);
    							return false;
    						}
    						Ext.ux.Toast.msg(consumer.queryReceivableBill.i18n('foss.stl.consumer.common.warmTips'), 
    							consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.exportSuccess'), 'success', 1000);
    					},
    					failure : function(response) {
    						Ext.ux.Toast.msg(consumer.queryReceivableBill.i18n('foss.stl.consumer.common.warmTips'), 
    							consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.exportFail'), 'error', 1000);
    					}
    				});
    			//按运单号导出
    			}else{
    				var form = Ext.getCmp('Foss_Stlyuscyings_StlyuscyingsQueryInfoTab_QueryByNumber_ID').getForm();
    				if (!form.isValid()) {
    					Ext.Msg.alert(consumer.queryReceivableBill.i18n('foss.stl.consumer.common.warmTips'), 
							consumer.queryReceivableBill.i18n('foss.stl.consumer.common.validateFailAlert'));
    					return false;
    				}
    				var wayBillNosArray = [];
    				var waybillNos = form.findField('waybillNos').getValue();
    				if (Ext.String.trim(waybillNos) != null	&& Ext.String.trim(waybillNos) != '') {
    					var reg = /[,;]/;
    					array = waybillNos.split(reg);
    					for (var i = 0; i < array.length; i++) {
    						if (Ext.String.trim(array[i]) != '') {
    							wayBillNosArray.push(array[i]);
    						}
    					}
    				} else {
    					Ext.Msg.alert(consumer.queryReceivableBill.i18n('foss.stl.consumer.common.warmTips'), 
    						consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.waybillNosIsNotNull'));
    					return false;
    				}
    				Ext.Ajax.request({
    					url : consumer.realPath('exportBillReceivable.action'),
    					form : Ext.fly('downloadAttachFileForm'),
    					method : 'post',
    					params : {
    						"billReceivableVO.dto.queryTab" : 'WB',
    						"billReceivableVO.wayBillNosArray" : wayBillNosArray,
    						'billReceivableVO.dto.arrayColumns':arrayColumns,
    						'billReceivableVO.dto.arrayColumnNames':arrayColumnNames
    					},
    					isUpload : true,
    					success : function(response) {
    						var result = Ext.decode(response.responseText);
    						// 如果异常信息有值，则弹出提示
    						if (!Ext.isEmpty(result.errorMessage)) {
    							Ext.Msg.alert(consumer.queryReceivableBill.i18n('foss.stl.consumer.common.warmTips'), result.errorMessage);
    							return false;
    						}
    						Ext.ux.Toast.msg(consumer.queryReceivableBill.i18n('foss.stl.consumer.common.warmTips'), 
    							consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.exportSuccess'), 'success', 1000);
    					},
    					failure : function(response) {
    						Ext.ux.Toast.msg(consumer.queryReceivableBill.i18n('foss.stl.consumer.common.warmTips'), 
    							consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.exportFail'), 'error', 1000);
    					}
					});
				}
			}
        }
    });
}
//ddw
consumer.queryReceivableBill.stampReceivableBatch = function(grid){
	var selections=grid.getSelectionModel().getSelection();
	var numbers = [];
	for(var i = 0;i < selections.length;i++){
		if(selections[0].data.customerCode!=selections[i].data.customerCode){
			Ext.Msg.alert(consumer.queryReceivableBill.i18n('foss.stl.consumer.common.warmTips'),"请选择同一个客户进行标记操作！");
			return false;
		}
		if(Ext.isEmpty(selections[i].data.active) || selections[i].data.active == 'N'){
			Ext.Msg.alert(consumer.queryReceivableBill.i18n('foss.stl.consumer.common.warmTips'),"请选择有效的单据进行标记操作！");
			return false;
		}
		if(selections[i].data.stamp == 'Y'){
			Ext.Msg.alert(consumer.queryReceivableBill.i18n('foss.stl.consumer.common.warmTips'),"请选择未标记的单据进行标记操作！");
			return false;
		}
		numbers.push(selections[i].data.receivableNo);
	}
	var entity = new Object();
	entity.receivableNosArray = numbers;
    var yesFn=function(){
    	Ext.Ajax.request({
    		url:consumer.realPath('stampReceivable.action'),
    		jsonData:{'billReceivableVO':entity},
    		method:'post',
    		success:function(response){
    				grid.store.load({
    				callback: function(records, operation, success) {
    				    var result =   Ext.decode(operation.response.responseText);  
    				    if(success){
    						if(result.isException){
    							Ext.Msg.alert(consumer.queryReceivableBill.i18n('foss.stl.consumer.common.warmTips'),result.message);
    							return false;
    						}
    				    }
    				}
    			});
    			Ext.Msg.alert(consumer.queryReceivableBill.i18n('foss.stl.consumer.common.warmTips'),"标记成功！");
    		},
    		exception:function(response){
    			var result = Ext.decode(response.responseText);	
    			Ext.Msg.alert(consumer.queryReceivableBill.i18n('foss.stl.consumer.common.warmTips'),result.message);
    		},
    		failure:function(response){
    			var result = Ext.decode(response.responseText);
    		}
    	});
	};
	var noFn=function(){
	 	return false;
	};
	consumer.queryReceivableBill.billReceivableConfirmAlert("确定要标记应收单？",yesFn,noFn);
}
//标记应收单
consumer.queryReceivableBill.stampReceivable = function(grid, rowIndex,colIndex){

	var selectionReceivable = grid.getStore().getAt(rowIndex);
	var numbers = [];
	if(Ext.isEmpty(selectionReceivable.data.active) || selectionReceivable.data.active == 'N'){
		Ext.Msg.alert(consumer.queryReceivableBill.i18n('foss.stl.consumer.common.warmTips'),"请选择有效的单据进行标记操作！");
		return false;
	}
	
	if(selectionReceivable.data.stamp == 'Y'){
		Ext.Msg.alert(consumer.queryReceivableBill.i18n('foss.stl.consumer.common.warmTips'),"请选择未标记的单据进行标记操作！");
		return false;
	}
	numbers.push(selectionReceivable.data.receivableNo);
	var entity = new Object();
	entity.receivableNosArray = numbers;
    var yesFn=function(){
    	Ext.Ajax.request({
    		url:consumer.realPath('stampReceivable.action'),
    		jsonData:{'billReceivableVO':entity},
    		method:'post',
    		success:function(response){
    				grid.store.load({
    				callback: function(records, operation, success) {
    				    var result =   Ext.decode(operation.response.responseText);  
    				    if(success){
    						if(result.isException){
    							Ext.Msg.alert(consumer.queryReceivableBill.i18n('foss.stl.consumer.common.warmTips'),result.message);
    							return false;
    						}			
    				    }
    				}
    			});
    			Ext.Msg.alert(consumer.queryReceivableBill.i18n('foss.stl.consumer.common.warmTips'),"标记成功！");
    		},
    		exception:function(response){
    			var result = Ext.decode(response.responseText);	
    			Ext.Msg.alert(consumer.queryReceivableBill.i18n('foss.stl.consumer.common.warmTips'),result.message);
    		},
    		failure:function(response){
    			var result = Ext.decode(response.responseText);
    		}
    	});
	};
	var noFn=function(){
	 	return false;
	};
	consumer.queryReceivableBill.billReceivableConfirmAlert("确定要标记应收单？",yesFn,noFn);
}
//ddw
consumer.queryReceivableBill.reverseStampReceivableBatch = function(grid){
	var selections=grid.getSelectionModel().getSelection();
	var numbers = [];
	for(var i = 0;i < selections.length;i++){
		if(selections[0].data.customerCode!=selections[i].data.customerCode){
			Ext.Msg.alert(consumer.queryReceivableBill.i18n('foss.stl.consumer.common.warmTips'),"请选择同一个客户进行标记操作！");
			return false;
		}
		if(Ext.isEmpty(selections[i].data.active) || selections[i].data.active == 'N'){
			Ext.Msg.alert(consumer.queryReceivableBill.i18n('foss.stl.consumer.common.warmTips'),"请选择有效的单据进行反标记操作！");
			return false;
		}
		if(selections[i].data.stamp == 'N'){
			Ext.Msg.alert(consumer.queryReceivableBill.i18n('foss.stl.consumer.common.warmTips'),"请选择已标记的单据进行反标记操作！");
			return false;
		}
		numbers.push(selections[i].data.receivableNo);
	}
	var entity = new Object();
	entity.receivableNosArray = numbers;
    var yesFn=function(){
    	Ext.Ajax.request({
    		url:consumer.realPath('reverseStampReceivable.action'),
    		jsonData:{'billReceivableVO':entity},
    		method:'post',
    		success:function(response){
    			grid.store.load({
    				callback: function(records, operation, success) {
    				    var result =   Ext.decode(operation.response.responseText);  
    				    if(success){
    						if(result.isException){
    							Ext.Msg.alert(consumer.queryReceivableBill.i18n('foss.stl.consumer.common.warmTips'),result.message);
    							return false;
    						}
    				    }
    				}
    			});
    			Ext.Msg.alert(consumer.queryReceivableBill.i18n('foss.stl.consumer.common.warmTips'),"反标记成功！");
    		},
    		exception:function(response){
    			var result = Ext.decode(response.responseText);	
    			Ext.Msg.alert(consumer.queryReceivableBill.i18n('foss.stl.consumer.common.warmTips'),result.message);
    		},
    		failure:function(response){
    			var result = Ext.decode(response.responseText);
    		}
    	});
	};
	var noFn=function(){
	 	return false;
	};
	consumer.queryReceivableBill.billReceivableConfirmAlert("确定要反标记应收单？",yesFn,noFn);
}
//反标记应收单
consumer.queryReceivableBill.reverseStampReceivable = function(grid, rowIndex,colIndex){

	var selectionReceivable = grid.getStore().getAt(rowIndex);
	var numbers = [];
	if(Ext.isEmpty(selectionReceivable.data.active) || selectionReceivable.data.active == 'N'){
		Ext.Msg.alert(consumer.queryReceivableBill.i18n('foss.stl.consumer.common.warmTips'),"请选择有效的单据进行反标记操作！");
		return false;
	}
	
	if(selectionReceivable.data.stamp == 'N'){
		Ext.Msg.alert(consumer.queryReceivableBill.i18n('foss.stl.consumer.common.warmTips'),"请选择已标记的单据进行反标记操作！");
		return false;
	}
	
	var entity = new Object();
	numbers.push(selectionReceivable.data.receivableNo);
	entity.receivableNosArray = numbers;
	
    var yesFn=function(){
    	Ext.Ajax.request({
    		url:consumer.realPath('reverseStampReceivable.action'),
    		jsonData:{'billReceivableVO':entity},
    		method:'post',
    		success:function(response){
    			grid.store.load({
    				callback: function(records, operation, success) {
    				    var result =   Ext.decode(operation.response.responseText);  
    				    if(success){
    						if(result.isException){
    							Ext.Msg.alert(consumer.queryReceivableBill.i18n('foss.stl.consumer.common.warmTips'),result.message);
    							return false;
    						}
    				    }
    				}
    			});
    			Ext.Msg.alert(consumer.queryReceivableBill.i18n('foss.stl.consumer.common.warmTips'),"反标记成功！");
    		},
    		exception:function(response){
    			var result = Ext.decode(response.responseText);	
    			Ext.Msg.alert(consumer.queryReceivableBill.i18n('foss.stl.consumer.common.warmTips'),result.message);
    		},
    		failure:function(response){
    			var result = Ext.decode(response.responseText);
    		}
    	});
	};
	var noFn=function(){
	 	return false;
	};
	consumer.queryReceivableBill.billReceivableConfirmAlert("确定要反标记应收单？",yesFn,noFn);
}
// 是否签收
Ext.define('Foss.Stlyuscyings.IsSignModel', {
			extend : 'Ext.data.Model',
			fields : [{
						name : 'name'
					}, {
						name : 'value'
					}]
		});

Ext.define('Foss.Stlyuscyings.IsSignStore', {
			extend : 'Ext.data.Store',
			model : 'Foss.Stlyuscyings.IsSignModel',
			data : [{
						name : consumer.queryReceivableBill.i18n('foss.stl.consumer.common.all'),
						value : ''
					}, {
						name : consumer.queryReceivableBill.i18n('foss.stl.consumer.common.isSignY'),
						value : 'Y'
					}, {
						name : consumer.queryReceivableBill.i18n('foss.stl.consumer.common.isSignN'),
						value : 'N'
					}]
		});

		//客户类型：store
Ext.define('Foss.stl.consumer.CustomerTypeStore',{
	extend:'Ext.data.Store',
	fields:['customerTypeCode','customerTypeName'],
	data:{
		'items':[
			{customerTypeCode:'00',customerTypeName:consumer.queryReceivableBill.i18n('foss.stl.consumer.common.all')},
			{customerTypeCode:'01',customerTypeName:consumer.queryReceivableBill.i18n('foss.stl.consumer.common.customerType')},
			{customerTypeCode:'02',customerTypeName:consumer.queryReceivableBill.i18n('foss.stl.consumer.common.vehAgency')},
			{customerTypeCode:'03',customerTypeName:consumer.queryReceivableBill.i18n('foss.stl.consumer.common.airAgeny')},
			{customerTypeCode:'04',customerTypeName:consumer.queryReceivableBill.i18n('foss.stl.consumer.common.airline')},
			{customerTypeCode:'05',customerTypeName:consumer.queryReceivableBill.i18n('foss.stl.consumer.common.landStowage')},
			{customerTypeCode:'06',customerTypeName:consumer.queryReceivableBill.i18n('foss.stl.consumer.common.packagingSupplier')},
			{customerTypeCode:'07',customerTypeName:consumer.queryReceivableBill.i18n('foss.stl.consumer.common.homesupply')}
		]
	},
	proxy:{
		type:'memory',
		reader:{
			type:'json',
			root:'items'
		}
	}
});

// 是否有效
Ext.define('Foss.Stlyuscyings.IsActiveModel', {
			extend : 'Ext.data.Model',
			fields : [{
						name : 'name'
					}, {
						name : 'value'
					}]
		});

Ext.define('Foss.Stlyuscyings.IsActiveStore', {
			extend : 'Ext.data.Store',
			model : 'Foss.Stlyuscyings.IsActiveModel',
			data : [/*{
						name : consumer.queryReceivableBill.i18n('foss.stl.consumer.common.all'),
						value : ''
					},*/{
						name : consumer.queryReceivableBill.i18n('foss.stl.consumer.common.isActiveY'),
						value : 'Y'
					}, {
						name : consumer.queryReceivableBill.i18n('foss.stl.consumer.common.isActiveN'),
						value : 'N'
					}]
		});

// 第一个Tab:按运单单号查询
Ext.define('Foss.Stlyuscyings.StlyuscyingsQueryInfoTab', {
	extend : 'Ext.tab.Panel',
	bodyCls : 'autoHeight',
	cls : 'innerTabPanel',
	activeTab : 0,
	columnWidth : 1,
	height : 290,
	listeners : {
		'tabchange' : function(tabPanel, newCard, oldCard, eOpts) {
			if (newCard.name == 'queryByNumber') {
				Ext.getCmp('T_consumer-queryReceivableBill_content').items.items[1].getLayout().setActiveItem(1);
			} else if (newCard.name == 'queryBySourceBillNo') {
				Ext.getCmp('T_consumer-queryReceivableBill_content').items.items[1].getLayout().setActiveItem(1);
			} else {
				Ext.getCmp('T_consumer-queryReceivableBill_content').items.items[1].getLayout().setActiveItem(0);
			}
		}

	},
	items : [{
		title : consumer.queryReceivableBill.i18n('foss.stl.consumer.common.queryByDate'),
		tabConfig : {
			width : 120
		},
		name : 'queryByDate',
		items : [{
			xtype : 'form',
			id : 'Foss_Stlyuscyings_StlyuscyingsQueryInfoTab_QueryByDate_ID',
			defaults : {
				labelWidth : 95,
				padding:5
			},
			layout : 'column',
			items : [{
						xtype : 'fieldcontainer',
						defaultType : 'radiofield',
						fieldLabel : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.dateType'),
						layout : 'column',
						labelWidth : 95,
						columnWidth : .3,
						items : [{
									boxLabel : consumer.queryReceivableBill.i18n('foss.stl.consumer.common.businessDate'),
									checked : true,
									name : 'selectDateType',
									inputValue : '1', // 表单的参数值
									id : 'queryReceivableBill_BusinessData_Id'
								}, {
									boxLabel : consumer.queryReceivableBill.i18n('foss.stl.consumer.common.accountDate'),
									name : 'selectDateType',
									inputValue : '2', // 表单的参数值
									id : 'queryReceivableBill_AccountData_Id'
								}]
					},{
						xtype : 'fieldcontainer',
						defaultType : 'radiofield',
						fieldLabel : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.deptType'),
						layout : 'column',
						columnWidth : .3,
						labelWidth : 95,
						items : [{
									boxLabel : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.dugDeptCode'),
									checked : true,
									name : 'selectDeptType',
									inputValue : '1', // 表单的参数值
									id : 'queryReceivableBill_dugDeptCode_Id'
								}, {
									boxLabel : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.genDeptCode'),
									name : 'selectDeptType',
									inputValue : '2', // 表单的参数值
									id : 'queryReceivableBill_genDeptCode_Id'
							}]
				    },{
				    	xtype : 'container',
						border : false,
						columnWidth : .3,
						height:24,
						html : '&nbsp;'
				    },{
						xtype : 'datefield',
						name : 'startDate',
						fieldLabel : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.startDate'),
						editable : false,
						id : 'FOSS_QueryReceivableBill_StartDate_ID',
						value : new Date(),
						format : 'Y-m-d',
						columnWidth : .3,
						allowBlank : false,
						labelWidth : 95,
						maxValue : new Date()
					}, {
						xtype : 'datefield',
						name : 'endDate',
						id : 'FOSS_QueryReceivableBill_EndDate_ID',
						fieldLabel : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.endDate'),
						value : new Date(),
						editable : false,
						format : 'Y-m-d',
						columnWidth : .3,
						allowBlank : false,
						labelWidth : 95,
						maxValue : new Date()
					},{
						xtype : 'combobox',
						name : 'sourceBillType',
						fieldLabel : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.sourceBillType'),
						store : FossDataDictionary.getDataDictionaryStore(
								'BILL_RECEIVABLE__SOURCE_BILL_TYPE', null/*, {
									'valueCode' : '',
									'valueName' : consumer.queryReceivableBill.i18n('foss.stl.consumer.common.all')
								}*/),
						queryMode : 'local',
						editable : false,
						columnWidth : .3,
						labelWidth : 95,
						displayField : 'valueName',
						valueField : 'valueCode',
						value : ''
					}, {
						xtype : 'linkagecomboselector',
						eventType : ['focus'],// 一般callparent包含focus事件，如果有callparent,则focus事件可不用传递
						itemId : 'Foss_baseinfo_BigRegion_ID',
						store : Ext.create('Foss.baseinfo.commonSelector.BigRegionStore'),// 从外面传入
						fieldLabel : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.bigArea'),
						value:'',
						minChars : 0,
						displayField : 'name',// 显示名称
						valueField : 'code',
						queryParam : 'commonOrgVo.name',
						name : 'bigArea',
						allowBlank : true,
						columnWidth : .3,
						listWidth : 300,// 设置下拉框宽度
						isPaging : true
						// 分页
					}, {
						xtype : 'linkagecomboselector',
						itemId : 'Foss_baseinfo_SmallRegion_ID',
						eventType : ['callparent'],// 一般callparent包含focus事件，如果有callparent,则focus事件可不用传递
						store : Ext.create('Foss.baseinfo.commonSelector.SmallRegionStore'),// 从外面传入
						name : 'smallArea',
						fieldLabel : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.smallArea'),
						parentParamsAndItemIds : {
							'commonOrgVo.code' : 'Foss_baseinfo_BigRegion_ID'
						},// 此处城市不需要传入
						minChars : 0,
						displayField : 'name',// 显示名称
						valueField : 'code',
						queryParam : 'commonOrgVo.name',
						allowBlank : true,
						columnWidth : .3,
						listWidth : 300,// 设置下拉框宽度
						isPaging : true
						// 分页
        			}, {
						xtype : 'linkagecomboselector',
						name : 'department',
						eventType : ['callparent'],
						store : Ext.create('Foss.baseinfo.commonSelector.SmallRegionStore'),// 从外面传入
						fieldLabel : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.org'),
						parentParamsAndItemIds : {
							'commonOrgVo.code' : 'Foss_baseinfo_SmallRegion_ID'
						},// 此处区域不需要传入
						minChars : 0,
						displayField : 'name',// 显示名称
						valueField : 'code',
						queryParam : 'commonOrgVo.name',
						allowBlank : true,
						columnWidth : .3,
						listWidth : 300,// 设置下拉框宽度
						isPaging : true
						// 分页
					}, {
        		    	xtype:'combobox',
        		    	name:'customerType',
        		    	columnWidth:.3,
        		    	fieldLabel:consumer.queryReceivableBill.i18n('foss.stl.consumer.common.customerType'),
        				store:Ext.create('Foss.stl.consumer.CustomerTypeStore'),
        				queryModel:'local',
        				value:'00',
        				displayField:'customerTypeName',
        				valueField:'customerTypeCode',
        		    	listeners:{
        					'change': function(th,newValue,oldValue){
        						//声明变量
        						var form,customerCode,vehAgencyCode,airAgenyCode,airlineName,landStowage,packagingCode;
        						//获取表单
        						form= th.up('form').getForm();
        						customerCode = form.findField('customerName');
        						vehAgencyCode = form.findField('vehAgencyCode');
        						airAgenyCode = form.findField('airAgenyCode');
        						airlineName = form.findField('airlineName');
        						landStowage = form.findField('landStowage');
        						packagingCode = form.findField('packagingCode');
        						homesupply = form.findField('homesupply');
        						
        						var arrCode = ['01','02','03','04','05','06','07'];
								var arrEl = [customerCode,
								             vehAgencyCode,
								             airAgenyCode,
								             airlineName,
								             landStowage,
								             packagingCode,
								             homesupply];
								
								for(var i=0;i<arrCode.length;i++) { 
									if(newValue == '00'){
										if(arrCode[i] == '01'){ // 如果为全部，则默认客户不可编辑
											arrEl[i].show();
											arrEl[i].setValue("");
											arrEl[i].setReadOnly(true);
										}else {
											arrEl[i].hide();
											arrEl[i].setValue("");
											arrEl[i].setReadOnly(true);
										}
									}else{
										if (arrCode[i] == newValue){ // 选择某种客户
											arrEl[i].show();
											arrEl[i].setValue("");
											arrEl[i].setReadOnly(false);
										}else {
											arrEl[i].hide();
											arrEl[i].setValue("");
											arrEl[i].setReadOnly(true);
										}
									}
								}
        						
        					}
        				}
        		    }, {
						xtype : 'commoncustomerselector',
						all:'true',
						name : 'customerName',
						fieldLabel : consumer.queryReceivableBill.i18n('foss.stl.consumer.common.customerName'),
						allowBlank : true,
						isPaging : true,
						readOnly:true,
						columnWidth : .3,
						height:24,
						singlePeopleFlag : 'Y'
						// 分页
					},{
						xtype:'commonLdpAgencyCompanySelector',
				    	fieldLabel :consumer.queryReceivableBill.i18n('foss.stl.consumer.common.landStowage'),
				    	name : 'landStowage',
						isPaging:true ,// 分页
						columnWidth : .3,
						height:24,
						hidden:true
				    },{	
				    	xtype:'commonairagencycompanyselector',
				    	all:'true',
				    	fieldLabel : consumer.queryReceivableBill.i18n('foss.stl.consumer.common.airAgeny'),
				    	name :'airAgenyCode',
						isPaging:true ,// 分页
						columnWidth : .3,
						height:24,
						hidden:true
				    },{	
				    	xtype:'commonvehagencycompselector',
				    	all:'true',
				    	fieldLabel :consumer.queryReceivableBill.i18n('foss.stl.consumer.common.vehAgency'),
				    	name :'vehAgencyCode',
						isPaging:true ,// 分页
						columnWidth : .3,
						height:24,
						hidden:true
				    },{
						xtype:'commonairlinesselector',
						all:'true',
				    	fieldLabel :consumer.queryReceivableBill.i18n('foss.stl.consumer.common.airline'),
				    	name : 'airlineName',
						isPaging:true ,// 分页
						columnWidth : .3,
						height:24,
						hidden:true
				    }, {
						xtype:'dynamicPackagingSupplierSelector',
					    name:'packagingCode',
					    fieldLabel :consumer.queryReceivableBill.i18n('foss.stl.consumer.common.packagingSupplier'),
					    active:'Y',
					    columnWidth : .3,
					    height:24,
					    hidden:true
				 	},{
						xtype:'supplierSelector',//家装代理
					    name:'homesupply',
					    fieldLabel :consumer.queryReceivableBill.i18n('foss.stl.consumer.common.homesupply'),
					    active:'Y',
					    columnWidth : .3,
					    height:24,
					    hidden:true
				 	}, {
						xtype : 'combobox',
						name : 'billType',
						fieldLabel : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.billType'),
						store : FossDataDictionary.getDataDictionaryStore(
								'BILL_RECEIVABLE__BILL_TYPE', null, {
									'valueCode' : '',
									'valueName' : consumer.queryReceivableBill.i18n('foss.stl.consumer.common.all')
								}),
						queryMode : 'local',
						editable : false,
						columnWidth : .3,
						displayField : 'valueName',
						valueField : 'valueCode',
						value : ''
					}, {
						xtype : 'combobox',
						name : 'isSign',
						fieldLabel : consumer.queryReceivableBill.i18n('foss.stl.consumer.common.isSign'),
						store : Ext.create('Foss.Stlyuscyings.IsSignStore'),
						queryMode : 'local',
						editable : false,
						columnWidth : .3,
						displayField : 'name',
						valueField : 'value',
						value : ''
					}, {
						xtype : 'combobox',
						name : 'active',
						fieldLabel : consumer.queryReceivableBill.i18n('foss.stl.consumer.common.active'),
						store : Ext.create('Foss.Stlyuscyings.IsActiveStore'),
						queryMode : 'local',
						editable : false,
						columnWidth : .3,
						displayField : 'name',
						valueField : 'value',
						value : 'Y' 
						/* ISSUE-3086  应收单、应付单、还款单、现金收款单界面查询条件中 是否有效 默认为有效状态。
						 * 杨书硕
						 * 2013-06-27
						 */
						  
					},{
				    	xtype:'combobox',
				    	name:'isInit',
				    	fieldLabel:consumer.queryReceivableBill.i18n('foss.stl.consumer.common.isInit'),
				    	editable:false,
				    	value:'',
						store:FossDataDictionary. getDataDictionaryStore('FOSS_BOOLEAN',null,{
							 'valueCode': '',
		               		 'valueName': consumer.queryReceivableBill.i18n('foss.stl.consumer.common.all')
						}),
						queryModel:'local',
						columnWidth : .3,
						displayField:'valueName',
						valueField:'valueCode'
				    },{
				    	xtype:'combobox',
				    	name:'productCode',
				    	fieldLabel:consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.productCode'),
						store:Ext.create('Foss.pkp.ProductStore'),
						multiSelect : true,
					    queryMode: 'local', 	
						displayField:'name',
						valueField:'code',
						columnWidth : .3
				    },{
				    	xtype:'combobox',
				    	name:'isDiscount',
				    	fieldLabel:consumer.queryReceivableBill.i18n('foss.stl.consumer.common.isDiscount'),
				    	editable:false,
				    	value:'',
						store:FossDataDictionary. getDataDictionaryStore('FOSS_BOOLEAN',null,{
							 'valueCode': '',
		               		 'valueName': consumer.queryReceivableBill.i18n('foss.stl.consumer.common.all')
						}),
						queryModel:'local',
						columnWidth : .3,
						displayField:'valueName',
						valueField:'valueCode'
				    },{
						border : 1,
						xtype : 'container',
						columnWidth : 1,
						defaultType : 'button',
						layout : 'column',
						items : [{
									text : consumer.queryReceivableBill.i18n('foss.stl.consumer.common.reset'),
									columnWidth : .1,
									handler : function() {
										var form=this.up('form').getForm();
										
										form.reset();			
										
										var dept = form.findField('department');
										
										//重置部门为当前登录部门
										if(!Ext.isEmpty(stl.currentDept.code)){
											var displayText = stl.currentDept.name
											var valueText = stl.currentDept.code;
											var store = dept.store;
											var  key = dept.displayField + '';
											var value = dept.valueField+ '';
											
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
									columnWidth : .8,
									html : '&nbsp;'
								}, {
									text : consumer.queryReceivableBill.i18n('foss.stl.consumer.common.query'),
									cls : 'yellow_button',
									columnWidth : .1,
									handler : function() {
										var form = this.up('form').getForm();
										var me = this;
										if (form.isValid()) {
											//设置该按钮灰掉
											me.disable(false);
											//30秒后自动解除灰掉效果
											setTimeout(function() {
												me.enable(true);
											}, 30000);
											if (consumer.dateValidation(form)) {
												var dateGridStore = Ext.getCmp('Foss_Stlyuscyings_DateQueryInfoGrid_Id').store;
												dateGridStore.removeAll();
												dateGridStore.loadPage(1, {
													callback : function(records, operation,success) {
														var result = Ext.decode(operation.response.responseText);
														Ext.getCmp('Foss_Stlyuscyings_DateQueryInfoGrid_TotalRecordsInDB_Id')
															.setValue(result.billReceivableVO.totalRecordsInDB);
														Ext.getCmp('Foss_Stlyuscyings_DateQueryInfoGrid_TotalAmount_Id')
															.setValue(result.billReceivableVO.totalAmount);
														Ext.getCmp('Foss_Stlyuscyings_DateQueryInfoGrid_VerifyAmount_Id')
															.setValue(result.billReceivableVO.totalVerifyAmount);
														Ext.getCmp('Foss_Stlyuscyings_DateQueryInfoGrid_UnverifyAmount_Id')
															.setValue(result.billReceivableVO.totalUnverifyAmount);
														me.enable(true);
													}
												});
											}
										} else {
											Ext.Msg.alert(consumer.queryReceivableBill.i18n('foss.stl.consumer.common.warmTips'),
												consumer.queryReceivableBill.i18n('foss.stl.consumer.common.validateFailAlert'));
										}
									}
								}]
					}]
		}]
	}, {
		title : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.queryByNo'),
		tabConfig : {
			width : 120
		},
		name : 'queryByNumber',
		items : [{
			id : 'Foss_Stlyuscyings_StlyuscyingsQueryInfoTab_QueryByNumber_ID',
			xtype : 'form',
			defaults : {
				margin : '10 10 5 10',
				labelWidth : 85
			},
			layout : {
				type : 'table',
				columns : 3
			},
			items : [{
						xtype : 'textarea',
						autoScroll : true,
						emptyText : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.billNosEmptyText'),
						regex : /^YS|([0-9]{9}[;,])*([0-9]{7,10}[;,]?)$/i,
						regexText : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.billNosRegexText'),
						fieldLabel : consumer.queryReceivableBill.i18n('foss.stl.consumer.common.waybillNo'),
						name : 'waybillNos',
						height : 180,
						width : 600,
						allowBlank : false,
						colspan : 3
					}, {
						border : 1,
						xtype : 'container',
						columnWidth : 1,
						defaultType : 'button',
						layout : 'column',
						colspan : 3,
						items : [{
									text : consumer.queryReceivableBill.i18n('foss.stl.consumer.common.reset'),
									columnWidth : .1,
									handler : function() {
										this.up('form').getForm().reset();
									}
								}, {
									xtype : 'container',
									border : false,
									html : '&nbsp;',
									columnWidth : .8
								}, {
									text : consumer.queryReceivableBill.i18n('foss.stl.consumer.common.query'),
									cls : 'yellow_button',
									columnWidth : .1,
									handler : function() {
										var form = this.up('form').getForm();
										var me = this;
										if (form.isValid()) {
											//设置该按钮灰掉
											me.disable(false);
											//30秒后自动解除灰掉效果
											setTimeout(function() {
												me.enable(true);
											}, 30000);
											var wayBillNosArray = [];
											var waybillNos = this.up('form').getForm().findField('waybillNos').getValue();
											if (Ext.String.trim(waybillNos) != null&& Ext.String.trim(waybillNos) != '') {
												var reg = /[,;]/;
												array = waybillNos.split(reg);
												for (var i = 0; i < array.length; i++) {
													if (Ext.String.trim(array[i]) != '') {
														wayBillNosArray.push(array[i]);
													}
												}
											} else {
												Ext.Msg.alert(consumer.queryReceivableBill.i18n('foss.stl.consumer.common.warmTips'),
													consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.waybillNosIsNotNull'));
												return false;
											}

											if (wayBillNosArray.length > 10) {
												Ext.Msg.alert(consumer.queryReceivableBill.i18n('foss.stl.consumer.common.warmTips'),
													consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.waybillNosInputToMore'));
												return false;
											}
											consumer.queryReceivableBill.wayBillNosArray = wayBillNosArray;
											var billGridStore = Ext.getCmp('Foss_Stlyuscyings_BillNoQueryInfoGrid_Id').store;
											billGridStore.removeAll();
											billGridStore.loadPage(1, {
												callback : function(records, operation,success) {
													var result = Ext.decode(operation.response.responseText);
													// 动态给GRID设置值
													Ext.getCmp('Foss_Stlyuscyings_BillNoQueryInfoGrid_TotalRecordesInDB_Id')
														.setValue(result.billReceivableVO.totalRecordsInDB);
													Ext.getCmp('Foss_Stlyuscyings_BillNoQueryInfoGrid_TotalAmount_Id')
														.setValue(result.billReceivableVO.totalAmount);
													Ext.getCmp('Foss_Stlyuscyings_BillNoQueryInfoGrid_TotalVerifyAmount_Id')
														.setValue(result.billReceivableVO.totalVerifyAmount);
													Ext.getCmp('Foss_Stlyuscyings_BillNoQueryInfoGrid_TotalUnverifyAmount_Id')
														.setValue(result.billReceivableVO.totalUnverifyAmount);
													me.enable(true);
												}
											});
										} else {
											Ext.Msg.alert(consumer.queryReceivableBill.i18n('foss.stl.consumer.common.warmTips'),
												consumer.queryReceivableBill.i18n('foss.stl.consumer.common.validateFailAlert'));
										}
									}
								}]
					}]
		}]
	}, {
		title : consumer.queryReceivableBill.i18n('foss.stl.consumer.common.queryBySourceNo'),
		tabConfig : {
			width : 120
		},
		name : 'queryBySourceBillNo',
		items : [{
			id : 'Foss_BillReceivableBill_queryBySourceBillNo_ID',
			xtype : 'form',
			defaults : {
				margin : '10 10 5 10',
				labelWidth : 85
			},
			layout : {
				type : 'table',
				columns : 3
			},
			items : [{
						xtype : 'textarea',
						autoScroll : true,
						emptyText : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.billNosEmptyText'),
						fieldLabel : consumer.queryReceivableBill.i18n('foss.stl.consumer.common.sourceBillNo'),
						name : 'sourceBillNos',
						height : 180,
						width : 600,
						allowBlank : false,
						colspan : 2
					}, {
						xtype:'container',
						items:[{
							xtype:'component',
							padding:'0 0 0 10',
							autoEl:{
								tag:'div',
								html:'<span style="color:red;">'+consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.sourceBillNosNots')+'</span>'
							}
						}],
						colspan : 1
					},{
						border : 1,
						xtype : 'container',
						columnWidth : 1,
						defaultType : 'button',
						layout : 'column',
						colspan : 2,
						items : [{
									text : consumer.queryReceivableBill.i18n('foss.stl.consumer.common.reset'),
									columnWidth : .1,
									handler : function() {
										this.up('form').getForm().reset();
									}
								}, {
									xtype : 'container',
									border : false,
									html : '&nbsp;',
									columnWidth : .8
								}, {
									text : consumer.queryReceivableBill.i18n('foss.stl.consumer.common.query'),
									cls : 'yellow_button',
									columnWidth : .1,
									handler : function() {
										var form = this.up('form').getForm();
										var me = this;
										if (form.isValid()) {
											//设置该按钮灰掉
											me.disable(false);
											//30秒后自动解除灰掉效果
											setTimeout(function() {
												me.enable(true);
											}, 30000);
											var sourceBillNosArray = [];
											var sourceBillNos = this.up('form').getForm().findField('sourceBillNos').getValue();
											if (Ext.String.trim(sourceBillNos) != null
													&& Ext.String.trim(sourceBillNos) != '') {
												var reg = /[,;]/;
												array = sourceBillNos.split(reg);
												for (var i = 0; i < array.length; i++) {
													if (Ext.String.trim(array[i]) != '') {
														sourceBillNosArray.push(array[i]);
													}
												}
											} else {
												Ext.Msg.alert(consumer.queryReceivableBill.i18n('foss.stl.consumer.common.warmTips'),
													consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.waybillNosIsNotNull'));
												return false;
											}

											if (sourceBillNosArray.length > 10) {
												Ext.Msg.alert(consumer.queryReceivableBill.i18n('foss.stl.consumer.common.warmTips'),
													consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.sourceBillNosInputToMore'));
												return false;
											}
											var billReceivableVO = new Object();
											billReceivableVO.sourceBillNosArray = sourceBillNosArray;

											var params = new Object();

											Ext.Ajax.request({
												url : consumer.realPath('queryReceivableBillBySourceBillNo.action'),
												params : {
													"billReceivableVO.sourceBillNosArray" : sourceBillNosArray
												},
												method : 'post',
												success : function(response) {
													var result = Ext.decode(response.responseText);
													var billNosStore = Ext.getCmp('Foss_Stlyuscyings_BillNoQueryInfoGrid_Id').store;

													billNosStore.removeAll();

													// 加载后台查询到的数据到grid的store中
													if (!Ext.isEmpty(result.billReceivableVO.billReceivableList)) {
														billNosStore.loadData(result.billReceivableVO.billReceivableList);
													}

													// 动态给GRID设置值
													Ext.getCmp('Foss_Stlyuscyings_BillNoQueryInfoGrid_TotalRecordesInDB_Id')
														.setValue(result.billReceivableVO.totalRecordsInDB);
													Ext.getCmp('Foss_Stlyuscyings_BillNoQueryInfoGrid_TotalAmount_Id')
														.setValue(result.billReceivableVO.totalAmount);
													Ext.getCmp('Foss_Stlyuscyings_BillNoQueryInfoGrid_TotalVerifyAmount_Id')
														.setValue(result.billReceivableVO.totalVerifyAmount);
													Ext.getCmp('Foss_Stlyuscyings_BillNoQueryInfoGrid_TotalUnverifyAmount_Id')
														.setValue(result.billReceivableVO.totalUnverifyAmount);
													me.enable(true);
												},
												failure : function(response) {
													var result = Ext.decode(response.responseText);
													me.enable(true);
												}
											});
										} else {
											Ext.Msg.alert(consumer.queryReceivableBill.i18n('foss.stl.consumer.common.warmTips'),
												consumer.queryReceivableBill.i18n('foss.stl.consumer.common.validateFailAlert'));
										}
									}
								}]
					},{
						xtype:'container',
						border:false,
						html:'&nbsp;',
						colspan : 1
					}]
		}]
	}]
});

// 单号查询model
Ext.define('Foss.Stlyuscyings.BillNoQueryEntryModel', {
			extend : 'Ext.data.Model',
			fields : [{
						name : 'waybillNo'
					}, {
						name : 'receivableNo'
					}, {
						name : 'customerName'
					}, {
						name : 'customerCode'
					}, {
						name : 'paymentType'
					}, {
						name : 'active'
					}, {
						name : 'amount',
						type : 'long'
					}, {
						name : 'verifyAmount',
						type : 'long'
					}, {
						name : 'unverifyAmount',
						type : 'long'
					},{
						name : 'createType'
					}, {
						name : 'sourceBillNo'
					}, {
						name : 'sourceBillType'
					}, {
						name : 'billType'
					}, {
						name : 'receivableOrgCode'
					}, {
						name : 'receivableOrgName'
					}, {
						name : 'generatingOrgCode'
					}, {
						name : 'generatingOrgName'
					}, {
						name : 'generatingComCode'
					}, {
						name : 'generatingComName'
					}, {
						name : 'dunningOrgCode'
					}, {
						name : 'dunningOrgName'
					}, {
						name : 'origOrgCode'
					}, {
						name : 'origOrgName'
					}, {
						name : 'destOrgCode'
					}, {
						name : 'destOrgName'
					}, {
						name : 'deliveryCustomerCode'
					}, {
						name : 'deliveryCustomerName'
					}, {
						name : 'receiveCustomerCode'
					}, {
						name : 'receiveCustomerName'
					}, {
						name : 'currencyCode'
					}, {
						name : 'businessDate',
						type:'date',
						convert:stl.longToDateConvert 
					}, {
						name : 'accountDate',
						type:'date',
						convert:stl.longToDateConvert 
					}, {
						name : 'conrevenDate',
						type:'date',
						convert:stl.longToDateConvert 
					}, {
						name : 'productCode'
					}, {
						name : 'transportFee',
						type : 'long'
					}, {
						name : 'pickupFee',
						type : 'long'
					}, {
						name : 'deliveryGoodsFee',
						type : 'long'
					}, {
						name : 'packagingFee',
						type : 'long'
					}, {
						name : 'codFee',
						type : 'long'
					}, {
						name : 'insuranceFee',
						type : 'long'
					}, {
						name : 'otherFee',
						type : 'long'
					}, {
						name : 'valueAddFee',
						type : 'long'
					}, {
						name : 'promotionsFee',
						type : 'long'
					}, {
						name : 'goodsName'
					}, {
						name : 'goodsVolumeTotal',
						type : 'long'
					}, {
						name : 'billWeight',
						type : 'long'
					}, {
						name : 'receiveMethod'
					}, {
						name : 'customerPickupOrgCode'
					}, {
						name : 'goodsQtyTotal',
						type : 'long'
					}, {
						name : 'targetOrgCode'
					}, {
						name : 'productId'
					}, {
						name : 'isRedBack'
					}, {
						name : 'isInit'
					}, {
						name : 'createUserCode'
					}, {
						name : 'createUserName'
					}, {
						name : 'createOrgCode'
					}, {
						name : 'createOrgName'
					}, {
						name : 'createTime',
						type:'date',
						convert:stl.longToDateConvert 
					}, {
						name : 'modifyTime',
						type:'date',
						convert:stl.longToDateConvert 
					}, {
						name : 'modifyUserCode'
					}, {
						name : 'modifyUserName'
					}, {
						name : 'statementBillNo'
					}, {
						name : 'unlockDateTime',
						type:'date',
						convert:stl.longToDateConvert 
					}, {
						name : 'lockCustomerCode'
					}, {
						name : 'lockCustomerName'
					}, {
						name : 'collectionType'
					}, {
						name : 'collectionName'
					}, {
						name : 'auditUserCode'
					}, {
						name : 'auditUserName'
					}, {
						name : 'auditUserCode'
					}, {
						name : 'approveStatus'
					}, {
						name : 'auditDate',
						type:'date',
						convert:stl.longToDateConvert 
					}, {
						name : 'isDisable'
					}, {
						name : 'disableUserCode'
					}, {
						name : 'disableUserName'
					}, {
						name : 'notes'
					}, {
						name : 'stamp'
					}, {
						name : 'expressOrigOrgCode'
					}, {
						name : 'expressOrigOrgName'
					}, {
						name : 'expressDestOrgCode'
					}, {
						name : 'expressDestOrgName'
					},{
						name:'invoiceMark'
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
                        name:'isDiscount',
                        type:'string'
                    }
            ]
		});

// 单号查询Store
Ext.define('Foss.Stlyuscyings.BillNoQueryEntryStore', {
			extend : 'Ext.data.Store',
			model : 'Foss.Stlyuscyings.BillNoQueryEntryModel',
			proxy:{
				type:'ajax',
				url:consumer.realPath('queryReceivableBillByWayBillNo.action'),
				actionMethods:'POST',
				reader:{
					type:'json',
					root:'billReceivableVO.billReceivableList'
				}
			},
			listeners:{
				'beforeLoad':function(store, operation, eOpts){
					var searchParams = {
							"billReceivableVO.wayBillNosArray" : consumer.queryReceivableBill.wayBillNosArray
						};
					//设置查询条件
					Ext.apply(operation,{
						params :searchParams
					}); 
				}
			}
		});

// 单号查询列表可编辑
var SoperateColumnEditing = Ext.create('Ext.grid.plugin.CellEditing', {
			clicksToEdit : 1
		});

/**
 * 定义查看明细的Form
 */
Ext.define('Foss.QueryBillReceivable.ShowDetailsInforForm', {
			extend : 'Ext.form.Panel',
			layout : 'column',
			width : stl.SCREENWIDTH * 0.68,
			frame : false,
			defaultType : 'textfield',
			defaults : {
				margin : '0 0 0 0',
				readOnly : true,
                labelWidth:140
			},
			items : [{
						fieldLabel : consumer.queryReceivableBill.i18n('foss.stl.consumer.common.waybillNo'),
						name : 'waybillNo',
						columnWidth : .5
					},{
						fieldLabel : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.transportFee'),
						name : 'transportFee',
						columnWidth : .5
					},{
						fieldLabel : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.createType'),
						name : 'createType',
						columnWidth : .5
					}, {
						fieldLabel : consumer.queryReceivableBill.i18n('foss.stl.consumer.common.sourceBillNo'),
						name : 'sourceBillNo',
						columnWidth : .5
					}, {
						fieldLabel : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.sourceBillType'),
						name : 'sourceBillType',
						columnWidth : .5
					}, {
						fieldLabel : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.billType'),
						name : 'billType',
						columnWidth : .5
					}, {
						fieldLabel : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.receivableOrgCode'),
						name : 'receivableOrgCode',
						columnWidth : .5
					}, {
						fieldLabel : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.receivableOrgName'),
						name : 'receivableOrgName',
						columnWidth : .5
					}, {
						fieldLabel : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.generatingOrgName'),
						name : 'generatingOrgName',
						columnWidth : .5
					}, {
						fieldLabel : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.generatingOrgCode'),
						name : 'generatingOrgCode',
						columnWidth : .5
					}, {
						fieldLabel : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.generatingComCode'),
						name : 'generatingComCode',
						columnWidth : .5
					}, {
						fieldLabel : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.generatingComName'),
						name : 'generatingComName',
						columnWidth : .5
					}, {
						fieldLabel : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.dunningOrgCode'),
						name : 'dunningOrgCode',
						columnWidth : .5
					}, {
						fieldLabel : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.dunningOrgName'),
						name : 'dunningOrgName',
						columnWidth : .5
					}, {
						fieldLabel : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.origOrgCode'),
						name : 'origOrgCode',
						columnWidth : .5
					}, {
						fieldLabel : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.origOrgName'),
						name : 'origOrgName',
						columnWidth : .5
					}, {
						fieldLabel : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.destOrgCode'),
						name : 'destOrgCode',
						columnWidth : .5
					}, {
						fieldLabel : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.destOrgName'),
						name : 'destOrgName',
						columnWidth : .5
					}, {
						fieldLabel : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.deliveryCustomerCode'),
						name : 'deliveryCustomerCode',
						columnWidth : .5
					}, {
						fieldLabel : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.deliveryCustomerName'),
						name : 'deliveryCustomerName',
						columnWidth : .5
					}, {
						fieldLabel : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.receiveCustomerCode'),
						name : 'receiveCustomerCode',
						columnWidth : .5
					}, {
						fieldLabel : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.receiveCustomerName'),
						name : 'receiveCustomerName',
						columnWidth : .5
					}, {
						fieldLabel : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.currencyCode'),
						name : 'currencyCode',
						columnWidth : .5
					}, {
						fieldLabel : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.conrevenDate'),
						name : 'conrevenDate',
						columnWidth : .5
					}, {
						fieldLabel : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.productCode'),
						name : 'productCode',
						columnWidth : .5
					}, {
						fieldLabel : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.productId'),
						name : 'productId',
						columnWidth : .5
					}, {
						fieldLabel : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.pickupFee'),
						name : 'pickupFee',
						columnWidth : .5
					}, {
						fieldLabel : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.deliveryGoodsFee'),
						name : 'deliveryGoodsFee',
						columnWidth : .5
					}, {
						fieldLabel : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.packagingFee'),
						name : 'packagingFee',
						columnWidth : .5
					}, {
						fieldLabel : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.codFee'),
						name : 'codFee',
						columnWidth : .5
					}, {
						fieldLabel : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.insuranceFee'),
						name : 'insuranceFee',
						columnWidth : .5
					}, {
						fieldLabel : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.otherFee'),
						name : 'otherFee',
						columnWidth : .5
					}, {
						fieldLabel : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.valueAddFee'),
						name : 'valueAddFee',
						columnWidth : .5
					}, {
						fieldLabel : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.promotionsFee'),
						name : 'promotionsFee',
						columnWidth : .5
					}, {
						fieldLabel : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.goodsName'),
						name : 'goodsName',
						columnWidth : .5
					}, {
						fieldLabel : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.goodsVolumeTotal'),
						name : 'goodsVolumeTotal',
						columnWidth : .5
					}, {
						fieldLabel : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.billWeight'),
						name : 'billWeight',
						columnWidth : .5
					},{
						fieldLabel : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.goodsQtyTotal'),
						name : 'goodsQtyTotal',
						columnWidth : .5
					}, {
						fieldLabel : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.targetOrgCode'),
						name : 'targetOrgCode',
						columnWidth : .5
					}, {
						fieldLabel : consumer.queryReceivableBill.i18n('foss.stl.consumer.common.isRedBack'),
						name : 'isRedBack',
						columnWidth : .5
					}, {
						fieldLabel : consumer.queryReceivableBill.i18n('foss.stl.consumer.common.isInit'),
						name : 'isInit',
						columnWidth : .5
					}, {
						fieldLabel : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.createUserCode'),
						name : 'createUserCode',
						columnWidth : .5
					}, {
						fieldLabel : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.createUserName'),
						name : 'createUserName',
						columnWidth : .5
					}, {
						fieldLabel : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.createOrgCode'),
						name : 'createOrgCode',
						columnWidth : .5
					}, {
						fieldLabel : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.createOrgName'),
						name : 'createOrgName',
						columnWidth : .5
					}, {
						fieldLabel : consumer.queryReceivableBill.i18n('foss.stl.consumer.common.createTime'),
						name : 'createTime',
						columnWidth : .5
					}, {
						fieldLabel : consumer.queryReceivableBill.i18n('foss.stl.consumer.common.modifyTime'),
						name : 'modifyTime',
						columnWidth : .5
					}, {
						fieldLabel : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.modifyUserCode'),
						name : 'modifyUserCode',
						columnWidth : .5
					}, {
						fieldLabel : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.modifyUserName'),
						name : 'modifyUserName',
						columnWidth : .5
					}, {
						fieldLabel : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.statementBillNo'),
						name : 'statementBillNo',
						columnWidth : .5
					}, {
						fieldLabel : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.unlockDateTime'),
						name : 'unlockDateTime',
						columnWidth : .5
					}, {
						fieldLabel : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.lockCustomerCode'),
						name : 'lockCustomerCode',
						columnWidth : .5
					}, {
						fieldLabel : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.lockCustomerName'),
						name : 'lockCustomerName',
						columnWidth : .5
					}, {
						fieldLabel : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.collectionType'),
						name : 'collectionType',
						columnWidth : .5
					}, {
						fieldLabel : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.collectionName'),
						name : 'collectionName',
						columnWidth : .5
					}, {
						fieldLabel : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.auditUserCode'),
						name : 'auditUserCode',
						columnWidth : .5
					}, {
						fieldLabel : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.auditUserName'),
						name : 'auditUserName',
						columnWidth : .5
					}, {
						fieldLabel : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.approveStatus'),
						name : 'approveStatus',
						columnWidth : .5
					}, {
						fieldLabel : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.auditDate'),
						name : 'auditDate',
						columnWidth : .5
					}, {
						fieldLabel : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.disableUserCode'),
						name : 'disableUserCode',
						columnWidth : .5
					}, {
						fieldLabel : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.disableUserName'),
						name : 'disableUserName',
						columnWidth : .5
					}, {
						fieldLabel : consumer.queryReceivableBill.i18n('foss.stl.consumer.common.notes'),
						name : 'notes',
						columnWidth : .5
					}, {
						fieldLabel : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.expressOrigOrgCode'),
						name : 'expressOrigOrgCode',
						columnWidth : .5
					}, {
						fieldLabel : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.expressOrigOrgName'),
						name : 'expressOrigOrgName',
						columnWidth : .5
					}, {
						fieldLabel : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.expressDestOrgCode'),
						name : 'expressDestOrgCode',
						columnWidth : .5
					}, {
						fieldLabel : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.expressDestOrgName'),
						name : 'expressDestOrgName',
						columnWidth : .5
					},{
						fieldLabel : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.invoiceMark'),
						name : 'invoiceMark',
						columnWidth : .5
					},{
                        fieldLabel:consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.isUnified'),
                        name:'unifiedSettlement',
                        columnWidth:.5
                    },{
                        fieldLabel:consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.contractOrgCode'),
                        name:'contractOrgCode',
                        columnWidth:.5
                    },{
                        fieldLabel:consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.contractOrgName'),
                        name:'contractOrgName',
                        columnWidth:.5
            }]
		});

/**
 * 声明查看明细Window
 */
Ext.define('Foss.QueryBillReceivable.ShowDetailsInforWindow', {
			extend : 'Ext.window.Window',
			width : stl.SCREENWIDTH * 0.6,
			height : stl.SCREENHEIGHT * 0.95,
			modal : true,
			constrainHeader : true,
			closeAction : 'hide',
			items : [Ext.create('Foss.QueryBillReceivable.ShowDetailsInforForm')]
		});

// 单号查询列表
Ext.define('Foss.Stlyuscyings.BillNoQueryInfoGrid', {
	store : Ext.create('Foss.Stlyuscyings.BillNoQueryEntryStore'),
	extend : 'Ext.grid.Panel',
	title : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.BillNoQueryInfoGridTitle'),
	emptyText : consumer.queryReceivableBill.i18n('foss.stl.consumer.common.emptyText'),
	frame : true,
	height : 468,
	sortableColumns : false,
	viewConfig : {
		enableTextSelection : true
	},
	plugins:SoperateColumnEditing,
	selModel:Ext.create('Ext.selection.CheckboxModel'),
	listeners : {
		'itemdblclick' : function(grid, record, item, index) {
			consumer.checkReceivableBillDetailsInforByDoubleClick(record,consumer.WAYBILLNO);
		}
	},
	detailWindow : null,
	getDetailWindow : function() {
		if (Ext.isEmpty(this.detailWindow)) {
			this.detailWindow = Ext.create('Foss.QueryBillReceivable.ShowDetailsInforWindow');
		}
		return this.detailWindow;
	},
	dockedItems : [{
				xtype : 'toolbar',
				dock : 'top',
				layout : 'column',
				defaults : {
					margin : '0 0 5 3'
				},
				items : [{
							xtype : 'button',
							disabled:!consumer.queryReceivableBill.isPermission('/stl-web/consumer/exportBillReceivable.action'),
							hidden:!consumer.queryReceivableBill.isPermission('/stl-web/consumer/exportBillReceivable.action'),
							text : consumer.queryReceivableBill.i18n('foss.stl.consumer.common.export'),
							columnWidth : .1,
							handler : function() {
								consumer.exportBillReceivable('WB');
							}
						},{
							xtype : 'button',
							disabled:!consumer.queryReceivableBill.isPermission('/stl-web/consumer/stampReceivable.action'),
							hidden:!consumer.queryReceivableBill.isPermission('/stl-web/consumer/stampReceivable.action'),
							text : consumer.queryReceivableBill.i18n('foss.stl.consumer.common.stampReceivable'),
							columnWidth : .1,
							handler : function() {
								//ddw
								var grid=Ext.getCmp('Foss_Stlyuscyings_BillNoQueryInfoGrid_Id');
								consumer.queryReceivableBill.stampReceivableBatch(grid);
							}
						},{
							xtype : 'button',
							disabled:!consumer.queryReceivableBill.isPermission('/stl-web/consumer/reverseStampReceivable.action'),
							hidden:!consumer.queryReceivableBill.isPermission('/stl-web/consumer/reverseStampReceivable.action'),
							text : consumer.queryReceivableBill.i18n('foss.stl.consumer.common.reverseStampReceivable'),
							columnWidth : .1,
							handler : function() {
								var grid=Ext.getCmp('Foss_Stlyuscyings_BillNoQueryInfoGrid_Id');
								consumer.queryReceivableBill.reverseStampReceivableBatch(grid);
							}
						}]
			}, {
				xtype : 'toolbar',
				dock : 'bottom',
				layout : 'column',
				items : [{
							height : 5,
							columnWidth : 1
						}, {
							xtype : 'textfield',
							readOnly : true,
							name : 'totalRecordsInDBInBilNoGrid',
							columnWidth : .1,
							id : 'Foss_Stlyuscyings_BillNoQueryInfoGrid_TotalRecordesInDB_Id',
							labelWidth : 40,
							fieldLabel : consumer.queryReceivableBill.i18n('foss.stl.consumer.common.totalRecords')
						}, {
							xtype : 'textfield',
							readOnly : true,
							name : 'totalAmountInBilNoGrid',
							columnWidth : .15,
							id : 'Foss_Stlyuscyings_BillNoQueryInfoGrid_TotalAmount_Id',
							labelWidth : 56,
							fieldLabel : consumer.queryReceivableBill.i18n('foss.stl.consumer.common.totalAmount')
						}, {
							xtype : 'textfield',
							readOnly : true,
							name : 'verifyAmountInBilNoGrid',
							columnWidth : .15,
							id : 'Foss_Stlyuscyings_BillNoQueryInfoGrid_TotalVerifyAmount_Id',
							labelWidth : 88,
							fieldLabel : consumer.queryReceivableBill.i18n('foss.stl.consumer.common.verifyAmount')
						}, {
							xtype : 'textfield',
							readOnly : true,
							name : 'unverifyAmountInBilNoGrid',
							columnWidth : .15,
							id : 'Foss_Stlyuscyings_BillNoQueryInfoGrid_TotalUnverifyAmount_Id',
							labelWidth : 88,
							fieldLabel : consumer.queryReceivableBill.i18n('foss.stl.consumer.common.unverifyAmount')
						}]
			}],
	columns : [{
		xtype : 'actioncolumn',
		width : 73,
		text : consumer.queryReceivableBill.i18n('foss.stl.consumer.common.actionColumn'),
		align : 'center',
		items : [{
    			iconCls : 'foss_icons_stl_mark',
    			tooltip : "标记",
    			getClass:function(v,m,r,rowIndex){
        			if(r.get('stamp') == 'Y' || r.get('stamp') == ""
    						|| !consumer.queryReceivableBill.isPermission('/stl-web/consumer/stampReceivable.action')){
        				return 'statementBill_hide';
        			}else {
        				return 'foss_icons_stl_mark';
        			}
        		},
    			handler : function(grid, rowIndex, colIndex) {
    				consumer.queryReceivableBill.stampReceivable(grid, rowIndex,colIndex);
    			}
    		},{
				iconCls : 'foss_icons_stl_fmark',
				tooltip : "反标记",
				getClass:function(v,m,r,rowIndex){
        			if(r.get('stamp') == "N" || r.get('stamp') == "" 
        					|| !consumer.queryReceivableBill.isPermission('/stl-web/consumer/reverseStampReceivable.action')){
        				return 'statementBill_hide';
        			}else {
        				return 'foss_icons_stl_fmark';
        			}
        		},
				handler : function(grid, rowIndex, colIndex) {
					consumer.queryReceivableBill.reverseStampReceivable(grid, rowIndex,	colIndex);
				}
			},{
    			iconCls : 'deppon_icons_showdetail',
    			tooltip : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.QueryInfoGridTooltip'),
    			handler : function(grid, rowIndex, colIndex) {
    				consumer.checkReceivableBillDetailsInfor(grid, rowIndex,colIndex, consumer.WAYBILLNO);
				}
			}]
	}, {
		header : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.generatingOrgName'),
		dataIndex : 'generatingOrgName'
	}, {
		header : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.productCode'),
		dataIndex : 'productCode',
		renderer : function(value) {
			return Foss.pkp.ProductData.rendererSubmitToDisplay(value);
		}
	},  {
		header : consumer.queryReceivableBill.i18n('foss.stl.consumer.common.waybillNo'),
		dataIndex : 'waybillNo'
	}, {
		header : consumer.queryReceivableBill.i18n('foss.stl.consumer.common.sourceBillNo'),
		dataIndex : 'sourceBillNo'
	}, {
		header : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.sourceBillType'),
		dataIndex : 'sourceBillType',
		renderer : function(value) {
			var displayField = FossDataDictionary.rendererSubmitToDisplay(value, 'BILL_RECEIVABLE__SOURCE_BILL_TYPE');
			return displayField;
		}
	}, {
		header : consumer.queryReceivableBill.i18n('foss.stl.consumer.common.receivableNo'),
		dataIndex : 'receivableNo'
	}, {
		header : consumer.queryReceivableBill.i18n('foss.stl.consumer.common.stamp'),
		dataIndex : 'stamp',
		renderer:function(value){
    		var displayField = FossDataDictionary.rendererSubmitToDisplay(value,settlementDict.FOSS_BOOLEAN);
    		return displayField;
    	}
	},  {
		header : consumer.queryReceivableBill.i18n('foss.stl.consumer.common.customerName'),
		dataIndex : 'customerName'
	}, {
		header : consumer.queryReceivableBill.i18n('foss.stl.consumer.common.customerCode'),
		dataIndex : 'customerCode'
	}, {
		header : consumer.queryReceivableBill.i18n('foss.stl.consumer.common.totalAmount'),
		dataIndex : 'amount',
		xtype : 'numbercolumn',
		format : '0,0.00',
		align : 'right'
	}, {
		header : consumer.queryReceivableBill.i18n('foss.stl.consumer.common.verifyAmount'),
		dataIndex : 'verifyAmount',
		xtype : 'numbercolumn',
		format : '0,0.00',
		align : 'right'
	}, {
		header : consumer.queryReceivableBill.i18n('foss.stl.consumer.common.unverifyAmount'),
		dataIndex : 'unverifyAmount',
		xtype : 'numbercolumn',
		format : '0,0.00',
		align : 'right'
	}, {
		header : consumer.queryReceivableBill.i18n('foss.stl.consumer.common.businessDate'),
		dataIndex : 'businessDate',
		xtype: 'datecolumn',  
		format:'Y-m-d H:i:s'
	}, {
		header : consumer.queryReceivableBill.i18n('foss.stl.consumer.common.accountDate'),
		dataIndex : 'accountDate',
		xtype: 'datecolumn',  
		format:'Y-m-d H:i:s'
	}, {
		header : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.transportFee'),
		dataIndex : 'transportFee',
		xtype : 'numbercolumn',
		format : '0,0.00',
		align : 'right'
	}, {
		header : consumer.queryReceivableBill.i18n('foss.stl.consumer.common.paymentType'),
		dataIndex : 'paymentType',
		renderer : function(value) {
			var displayField = FossDataDictionary.rendererSubmitToDisplay(value, 'SETTLEMENT__PAYMENT_TYPE');
			return displayField;
		}
	}, {
		header : consumer.queryReceivableBill.i18n('foss.stl.consumer.common.active'),
		dataIndex : 'active',
		renderer:function(value){
			var displayField = FossDataDictionary.rendererSubmitToDisplay(value,settlementDict.FOSS_ACTIVE);
    		return displayField;
		}
	}, {
		header : consumer.queryReceivableBill.i18n('foss.stl.consumer.common.isRedBack'),
		dataIndex : 'isRedBack',
		renderer : function(value) {
			var displayField = FossDataDictionary.rendererSubmitToDisplay(value, 'SETTLEMENT__IS_RED_BACK');
			return displayField;
		}
	}, {
		header : consumer.queryReceivableBill.i18n('foss.stl.consumer.common.isInit'),
		dataIndex : 'isInit',
		renderer:function(value){
			if(value == 'Y'){
				return consumer.queryReceivableBill.i18n('foss.stl.consumer.common.yes');
			}else if(value == 'N'){
				return consumer.queryReceivableBill.i18n('foss.stl.consumer.common.no');
			}else{
				return value;
			}
		}
	},{
		header : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.createType'),
		dataIndex : 'createType',
		renderer : function(value) {
			var displayField = FossDataDictionary.rendererSubmitToDisplay(value, 'SETTLEMENT__CREATE_TYPE');
			return displayField;
		},
		hidden : true
	}, {
		header : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.receivableOrgCode'),
		dataIndex : 'receivableOrgCode',
		hidden : true
	}, {
		header : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.receivableOrgName'),
		dataIndex : 'receivableOrgName',
		hidden : true
	}, {
		header : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.generatingOrgCode'),
		dataIndex : 'generatingOrgCode',
		hidden : true
	}, {
		header : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.generatingComCode'),
		dataIndex : 'generatingComCode',
		hidden : true
	}, {
		header : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.generatingComName'),
		dataIndex : 'generatingComName',
		hidden : true
	}, {
		header : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.dunningOrgCode'),
		dataIndex : 'dunningOrgCode',
		hidden : true
	}, {
		header : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.dunningOrgName'),
		dataIndex : 'dunningOrgName',
		hidden : true
	}, {
		header : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.origOrgCode'),
		dataIndex : 'origOrgCode',
		hidden : true
	}, {
		header : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.origOrgName'),
		dataIndex : 'origOrgName',
		hidden : true
	}, {
		header : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.destOrgCode'),
		dataIndex : 'destOrgCode',
		hidden : true
	}, {
		header : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.destOrgName'),
		dataIndex : 'destOrgName',
		hidden : true
	}, {
		header : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.deliveryCustomerCode'),
		dataIndex : 'deliveryCustomerCode',
		hidden : true
	}, {
		header : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.deliveryCustomerName'),
		dataIndex : 'deliveryCustomerName',
		hidden : true
	}, {
		header : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.receiveCustomerCode'),
		dataIndex : 'receiveCustomerCode',
		hidden : true
	}, {
		header : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.receiveCustomerName'),
		dataIndex : 'receiveCustomerName',
		hidden : true
	}, {
		header : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.currencyCode'),
		dataIndex : 'currencyCode',
		hidden : true
	}, {
		header : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.conrevenDate'),
		dataIndex : 'conrevenDate',
		xtype: 'datecolumn', 
		format:'Y-m-d H:i:s'
	},{
		header : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.billType'),
		dataIndex : 'billType',
		renderer : function(value) {
			var displayField = FossDataDictionary.rendererSubmitToDisplay(value, 'BILL_RECEIVABLE__BILL_TYPE');
			return displayField;
		}
	}, {
		header : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.productId'),
		dataIndex : 'productId',
		hidden : true
	}, {
		header : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.pickupFee'),
		dataIndex : 'pickupFee',
		hidden : true,
		xtype : 'numbercolumn',
		format : '0,0.00',
		align : 'right'
	}, {
		header : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.deliveryGoodsFee'),
		dataIndex : 'deliveryGoodsFee',
		hidden : true,
		xtype : 'numbercolumn',
		format : '0,0.00',
		align : 'right'
	}, {
		header : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.packagingFee'),
		dataIndex : 'packagingFee',
		hidden : true,
		xtype : 'numbercolumn',
		format : '0,0.00',
		align : 'right'
	}, {
		header : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.codFee'),
		dataIndex : 'codFee',
		hidden : true,
		xtype : 'numbercolumn',
		format : '0,0.00',
		align : 'right'
	}, {
		header : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.insuranceFee'),
		dataIndex : 'insuranceFee',
		hidden : true,
		xtype : 'numbercolumn',
		format : '0,0.00',
		align : 'right'
	}, {
		header : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.otherFee'),
		dataIndex : 'otherFee',
		hidden : true,
		xtype : 'numbercolumn',
		format : '0,0.00',
		align : 'right'
	}, {
		header : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.valueAddFee'),
		dataIndex : 'valueAddFee',
		hidden : true,
		xtype : 'numbercolumn',
		format : '0,0.00',
		align : 'right'
	}, {
		header : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.promotionsFee'),
		dataIndex : 'promotionsFee',
		hidden : true,
		xtype : 'numbercolumn',
		format : '0,0.00',
		align : 'right'
	}, {
		header : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.goodsName'),
		dataIndex : 'goodsName',
		hidden : true
	}, {
		header : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.goodsVolumeTotal'),
		dataIndex : 'goodsVolumeTotal',
		hidden : true
	}, {
		header : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.billWeight'),
		dataIndex : 'billWeight',
		hidden : true
	},{
		header : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.goodsQtyTotal'),
		dataIndex : 'goodsQtyTotal',
		hidden : true
	}, {
		header : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.targetOrgCode'),
		dataIndex : 'targetOrgCode',
		hidden : true
	}, {
		header : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.createUserCode'),
		dataIndex : 'createUserCode',
		hidden : true
	}, {
		header : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.createUserName'),
		dataIndex : 'createUserName',
		hidden : true
	}, {
		header : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.createOrgCode'),
		dataIndex : 'createOrgCode',
		hidden : true
	}, {
		header : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.createOrgName'),
		dataIndex : 'createOrgName',
		hidden : true
	}, {
		header : consumer.queryReceivableBill.i18n('foss.stl.consumer.common.createTime'),
		dataIndex : 'createTime',
		hidden : true,
		xtype: 'datecolumn', 
		format:'Y-m-d H:i:s'
	}, {
		header : consumer.queryReceivableBill.i18n('foss.stl.consumer.common.modifyTime'),
		dataIndex : 'modifyTime',
		hidden : true,
		xtype: 'datecolumn', 
		format:'Y-m-d H:i:s'
	}, {
		header : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.modifyUserCode'),
		dataIndex : 'modifyUserCode',
		hidden : true
	}, {
		header : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.modifyUserName'),
		dataIndex : 'modifyUserName',
		hidden : true
	}, {
		header : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.statementBillNo'),
		dataIndex : 'statementBillNo',
		hidden : true
	}, {
		header : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.unlockDateTime'),
		dataIndex : 'unlockDateTime',
		hidden : true,
		xtype: 'datecolumn', 
		format:'Y-m-d H:i:s'
	}, {
		header : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.lockCustomerCode'),
		dataIndex : 'lockCustomerCode',
		hidden : true
	}, {
		header : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.lockCustomerName'),
		dataIndex : 'lockCustomerName',
		hidden : true
	}, {
		header : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.collectionType'),
		dataIndex : 'collectionType',
		hidden : true,
		renderer : function(value) {
			var displayField = FossDataDictionary.rendererSubmitToDisplay(value, 'BILL_RECEIVABLE__COLLECTION_TYPE');
			return displayField;
		}
	}, {
		header : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.collectionName'),
		dataIndex : 'collectionName',
		hidden : true
	}, {
		header : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.auditUserCode'),
		dataIndex : 'auditUserCode',
		hidden : true
	}, {
		header : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.auditUserName'),
		dataIndex : 'auditUserName',
		hidden : true
	}, {
		header : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.approveStatus'),
		dataIndex : 'approveStatus',
		hidden : true,
		renderer : function(value) {
			var displayField = FossDataDictionary.rendererSubmitToDisplay(value, 'NOTE_APPLICATION__APPROVE_STATUS');
			return displayField;
		}
	}, {
		header : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.auditDate'),
		dataIndex : 'auditDate',
		hidden : true,
		xtype: 'datecolumn', 
		format:'Y-m-d H:i:s'
	}, {
		header : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.disableUserCode'),
		dataIndex : 'disableUserCode',
		hidden : true
	}, {
		header : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.disableUserName'),
		dataIndex : 'disableUserName',
		hidden : true
	}, {
		header : consumer.queryReceivableBill.i18n('foss.stl.consumer.common.notes'),
		dataIndex : 'notes',
		hidden : true
	}, {
		header : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.expressOrigOrgCode'),
		dataIndex : 'expressOrigOrgCode',
		hidden : true
	}, {
		header : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.expressOrigOrgName'),
		dataIndex : 'expressOrigOrgName',
		hidden : true
	}, {
		header : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.expressDestOrgCode'),
		dataIndex : 'expressDestOrgCode',
		hidden : true
	}, {
		header : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.expressDestOrgName'),
		dataIndex : 'expressDestOrgName',
		hidden : true
	},{
		header : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.invoiceMark'),
		dataIndex : 'invoiceMark',
		hidden : true,
		renderer:function(value){
			var displayField = FossDataDictionary.rendererSubmitToDisplay (value,'SETTLEMENT_INVOICE_MARK');
			return  displayField;
		}
	},{
         header:consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.isUnified'),
         dataIndex:'unifiedSettlement',
         hidden:true,
         renderer:function(value){
            if(value == 'Y'){
                return consumer.queryReceivableBill.i18n('foss.stl.consumer.common.yes');
            }else {
                return consumer.queryReceivableBill.i18n('foss.stl.consumer.common.no');
            }
        }
    },{
         header:consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.contractOrgCode'),
         dataIndex:'contractOrgCode',
         hidden:true
    },{
        header:consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.contractOrgName'),
        hidden:true,
        dataIndex:'contractOrgName'
    },{
        header:consumer.queryReceivableBill.i18n('foss.stl.consumer.common.isDiscount'),
        dataIndex:'isDiscount',
        renderer:function(value){
           if(value == 'Y'){
               return consumer.queryReceivableBill.i18n('foss.stl.consumer.common.yes');
           }else {
               return consumer.queryReceivableBill.i18n('foss.stl.consumer.common.no');
           }
       }
   }]
});

// 日期查询列表Store
Ext.define('Foss.Stlyuscyings.DateQueryEntityStore', {
			extend : 'Ext.data.Store',
			model : 'Foss.Stlyuscyings.BillNoQueryEntryModel',
			pageSize : 50,
			proxy : {
				type : 'ajax',
				url : consumer.realPath('queryReceivableBillByDate.action'),
				reader : {
					type : 'json',
					root : 'billReceivableVO.billReceivableList',
					totalProperty : 'totalCount'
				}
			}
		});

// 日期查询列表可编辑
var SoperateColumnEditing1 = Ext.create('Ext.grid.plugin.CellEditing', {
			clicksToEdit : 1
		});
// 日期查询列表
Ext.define('Foss.Stlyuscyings.DateQueryInfoGrid', {
			extend : 'Ext.grid.Panel',
			title : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.DateQueryInfoGridTitle'),
			frame : true,
			emptyText : consumer.queryReceivableBill.i18n('foss.stl.consumer.common.emptyText'),
			height : 468,
			sortableColumns : false,
			viewConfig : {
				enableTextSelection : true
			},
			plugins:SoperateColumnEditing,
			selModel:Ext.create('Ext.selection.CheckboxModel'),
			listeners : {
				'itemdblclick' : function(grid, record, item, index) {
					consumer.checkReceivableBillDetailsInforByDoubleClick(record, consumer.DATE);
				}
			},
			detailWindow : null,
			getDetailWindow : function() {
				if (Ext.isEmpty(this.detailWindow)) {
					this.detailWindow = Ext.create('Foss.QueryBillReceivable.ShowDetailsInforWindow');
				}
				return this.detailWindow;
			},
			columns : [{
				xtype : 'actioncolumn',
				width : 73,
				text : consumer.queryReceivableBill.i18n('foss.stl.consumer.common.actionColumn'),
				align : 'center',
				items : [{
    					iconCls : 'foss_icons_stl_mark',
    					tooltip : "标记",
    					getClass:function(v,m,r,rowIndex){
                			if(r.get('stamp') == 'Y' || r.get('stamp') == ""
            						|| !consumer.queryReceivableBill.isPermission('/stl-web/consumer/stampReceivable.action')){
                				return 'statementBill_hide';
                			}else {
                				return 'foss_icons_stl_mark';
                			}
                		},
    					handler : function(grid, rowIndex, colIndex) {
    						consumer.queryReceivableBill.stampReceivable(grid, rowIndex,colIndex);
    					}
    				},{
						iconCls : 'foss_icons_stl_fmark',
						tooltip : "反标记",
						getClass:function(v,m,r,rowIndex){
    	        			if(r.get('stamp') == "N" || r.get('stamp') == "" 
    	        					|| !consumer.queryReceivableBill.isPermission('/stl-web/consumer/reverseStampReceivable.action')){
    	        				return 'statementBill_hide';
    	        			}else {
    	        				return 'foss_icons_stl_fmark';
    	        			}
    	        		},
						handler : function(grid, rowIndex, colIndex) {
							consumer.queryReceivableBill.reverseStampReceivable(grid, rowIndex,	colIndex);
						}
					},{
    					iconCls : 'deppon_icons_showdetail',
    					tooltip : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.QueryInfoGridTooltip'),
    					handler : function(grid, rowIndex, colIndex) {
    						consumer.checkReceivableBillDetailsInfor(grid,rowIndex, colIndex, consumer.DATE);
						}
					}]
			}, {
				header : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.generatingOrgName'),
				dataIndex : 'generatingOrgName'
			}, {
				header : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.productCode'),
				dataIndex : 'productCode',
				renderer : function(value) {
					return Foss.pkp.ProductData.rendererSubmitToDisplay(value);
				}
			}, {
				header :  consumer.queryReceivableBill.i18n('foss.stl.consumer.common.waybillNo'),
				dataIndex : 'waybillNo'
			}, {
				header : consumer.queryReceivableBill.i18n('foss.stl.consumer.common.sourceBillNo'),
				dataIndex : 'sourceBillNo'
			}, {
				header : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.sourceBillType'),
				dataIndex : 'sourceBillType',
				renderer : function(value) {
					var displayField = FossDataDictionary.rendererSubmitToDisplay(value,'BILL_RECEIVABLE__SOURCE_BILL_TYPE');
					return displayField;
				}
			}, {
				header : consumer.queryReceivableBill.i18n('foss.stl.consumer.common.receivableNo'),
				dataIndex : 'receivableNo'
			},{
				header : consumer.queryReceivableBill.i18n('foss.stl.consumer.common.stamp'),
				dataIndex : 'stamp',
				renderer:function(value){
		    		var displayField = FossDataDictionary.rendererSubmitToDisplay(value,settlementDict.FOSS_BOOLEAN);
		    		return displayField;
		    	}
			},{
				header : consumer.queryReceivableBill.i18n('foss.stl.consumer.common.customerName'),
				dataIndex : 'customerName'
			}, {
				header :consumer.queryReceivableBill.i18n('foss.stl.consumer.common.customerCode'),
				dataIndex : 'customerCode'
			}, {
				header : consumer.queryReceivableBill.i18n('foss.stl.consumer.common.totalAmount'),
				dataIndex : 'amount',
				xtype : 'numbercolumn',
				format : '0,0.00',
				align : 'right'
			}, {
				header : consumer.queryReceivableBill.i18n('foss.stl.consumer.common.verifyAmount'),
				dataIndex : 'verifyAmount',
				xtype : 'numbercolumn',
				format : '0,0.00',
				align : 'right'
			}, {
				header : consumer.queryReceivableBill.i18n('foss.stl.consumer.common.unverifyAmount'),
				dataIndex : 'unverifyAmount',
				xtype : 'numbercolumn',
				format : '0,0.00',
				align : 'right'
			}, {
				header : consumer.queryReceivableBill.i18n('foss.stl.consumer.common.businessDate'),
				dataIndex : 'businessDate',
				xtype: 'datecolumn',
				format:'Y-m-d H:i:s'
			}, {
				header : consumer.queryReceivableBill.i18n('foss.stl.consumer.common.accountDate'),
				dataIndex : 'accountDate',
				xtype: 'datecolumn',
				format:'Y-m-d H:i:s'
			}, {
				header : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.transportFee'),
				dataIndex : 'transportFee',
				xtype : 'numbercolumn',
				format : '0,0.00',
				align : 'right'
			}, {
				header : consumer.queryReceivableBill.i18n('foss.stl.consumer.common.paymentType'),
				dataIndex : 'paymentType',
				renderer : function(value) {
					var displayField = FossDataDictionary.rendererSubmitToDisplay(value, 'SETTLEMENT__PAYMENT_TYPE');
					return displayField;
				}
			}, {
				header : consumer.queryReceivableBill.i18n('foss.stl.consumer.common.active'),
				dataIndex : 'active',
				renderer:function(value){
					var displayField = FossDataDictionary.rendererSubmitToDisplay(value,settlementDict.FOSS_ACTIVE);
		    		return displayField;
				}
			}, {
				header : consumer.queryReceivableBill.i18n('foss.stl.consumer.common.isRedBack'),
				dataIndex : 'isRedBack',
				renderer : function(value) {
					var displayField = FossDataDictionary.rendererSubmitToDisplay(value,'SETTLEMENT__IS_RED_BACK');
					return displayField;
				}
			}, {
				header : consumer.queryReceivableBill.i18n('foss.stl.consumer.common.isInit'),
				dataIndex : 'isInit',
				renderer:function(value){
					if(value == 'Y'){
						return consumer.queryReceivableBill.i18n('foss.stl.consumer.common.yes');
					}else if(value == 'N'){
						return consumer.queryReceivableBill.i18n('foss.stl.consumer.common.no');
					}else{
						return value;
					}
				}
			},{
				header : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.createType'),
				dataIndex : 'createType',
				renderer : function(value) {
				var displayField = FossDataDictionary.rendererSubmitToDisplay(value, 'SETTLEMENT__CREATE_TYPE');
    				return displayField;
    			},
				hidden : true
			}, {
				header : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.receivableOrgCode'),
				dataIndex : 'receivableOrgCode',
				hidden : true
			}, {
				header : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.receivableOrgName'),
				dataIndex : 'receivableOrgName',
				hidden : true
			}, {
				header : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.generatingOrgCode'),
				dataIndex : 'generatingOrgCode',
				hidden : true
			}, {
				header : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.generatingComCode'),
				dataIndex : 'generatingComCode',
				hidden : true
			}, {
				header : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.generatingComName'),
				dataIndex : 'generatingComName',
				hidden : true
			}, {
				header : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.dunningOrgCode'),
				dataIndex : 'dunningOrgCode',
				hidden : true
			}, {
				header : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.dunningOrgName'),
				dataIndex : 'dunningOrgName',
				hidden : true
			}, {
				header : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.origOrgCode'),
				dataIndex : 'origOrgCode',
				hidden : true
			}, {
				header : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.origOrgName'),
				dataIndex : 'origOrgName',
				hidden : true
			}, {
				header : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.destOrgCode'),
				dataIndex : 'destOrgCode',
				hidden : true
			}, {
				header : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.destOrgName'),
				dataIndex : 'destOrgName',
				hidden : true
			}, {
				header : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.deliveryCustomerCode'),
				dataIndex : 'deliveryCustomerCode',
				hidden : true
			}, {
				header : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.deliveryCustomerName'),
				dataIndex : 'deliveryCustomerName',
				hidden : true
			}, {
				header : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.receiveCustomerCode'),
				dataIndex : 'receiveCustomerCode',
				hidden : true
			}, {
				header : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.receiveCustomerName'),
				dataIndex : 'receiveCustomerName',
				hidden : true
			}, {
				header : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.currencyCode'),
				dataIndex : 'currencyCode',
				hidden : true
			}, {
				header : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.conrevenDate'),
				dataIndex : 'conrevenDate',
				xtype: 'datecolumn',
				format:'Y-m-d H:i:s'
			}, {
				header :  consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.billType'),
				dataIndex : 'billType',
				renderer : function(value) {
					var displayField = FossDataDictionary.rendererSubmitToDisplay(value,'BILL_RECEIVABLE__BILL_TYPE');
					return displayField;
				}
			}, {
				header : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.productId'),
				dataIndex : 'productId',
				hidden : true
			}, {
				header : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.pickupFee'),
				dataIndex : 'pickupFee',
				hidden : true,
				xtype : 'numbercolumn',
				format : '0,0.00',
				align : 'right'
			}, {
				header : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.deliveryGoodsFee'),
				dataIndex : 'deliveryGoodsFee',
				hidden : true,
				xtype : 'numbercolumn',
				format : '0,0.00',
				align : 'right'
			}, {
				header : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.packagingFee'),
				dataIndex : 'packagingFee',
				hidden : true,
				xtype : 'numbercolumn',
				format : '0,0.00',
				align : 'right'
			}, {
				header : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.codFee'),
				dataIndex : 'codFee',
				hidden : true,
				xtype : 'numbercolumn',
				format : '0,0.00',
				align : 'right'
			}, {
				header : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.insuranceFee'),
				dataIndex : 'insuranceFee',
				hidden : true,
				xtype : 'numbercolumn',
				format : '0,0.00',
				align : 'right'
			}, {
				header : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.otherFee'),
				dataIndex : 'otherFee',
				hidden : true,
				xtype : 'numbercolumn',
				format : '0,0.00',
				align : 'right'
			}, {
				header : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.valueAddFee'),
				dataIndex : 'valueAddFee',
				hidden : true,
				xtype : 'numbercolumn',
				format : '0,0.00',
				align : 'right'
			}, {
				header : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.promotionsFee'),
				dataIndex : 'promotionsFee',
				hidden : true,
				xtype : 'numbercolumn',
				format : '0,0.00',
				align : 'right'
			}, {
				header : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.goodsName'),
				dataIndex : 'goodsName',
				hidden : true
			}, {
				header : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.goodsVolumeTotal'),
				dataIndex : 'goodsVolumeTotal',
				hidden : true
			}, {
				header : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.billWeight'),
				dataIndex : 'billWeight',
				hidden : true
			}, {
				header : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.goodsQtyTotal'),
				dataIndex : 'goodsQtyTotal',
				hidden : true
			}, {
				header : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.targetOrgCode'),
				dataIndex : 'targetOrgCode',
				hidden : true
			}, {
				header : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.createUserCode'),
				dataIndex : 'createUserCode',
				hidden : true
			}, {
				header : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.createUserName'),
				dataIndex : 'createUserName',
				hidden : true
			}, {
				header : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.createOrgCode'),
				dataIndex : 'createOrgCode',
				hidden : true
			}, {
				header : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.createOrgName'),
				dataIndex : 'createOrgName',
				hidden : true
			}, {
				header : consumer.queryReceivableBill.i18n('foss.stl.consumer.common.createTime'),
				dataIndex : 'createTime',
				hidden : true,
				xtype: 'datecolumn',
				format:'Y-m-d H:i:s'
			}, {
				header : consumer.queryReceivableBill.i18n('foss.stl.consumer.common.modifyTime'),
				dataIndex : 'modifyTime',
				hidden : true,
				xtype: 'datecolumn',
				format:'Y-m-d H:i:s'
			}, {
				header : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.modifyUserCode'),
				dataIndex : 'modifyUserCode',
				hidden : true
			}, {
				header : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.modifyUserName'),
				dataIndex : 'modifyUserName',
				hidden : true
			}, {
				header : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.statementBillNo'),
				dataIndex : 'statementBillNo',
				hidden : true
			}, {
				header : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.unlockDateTime'),
				dataIndex : 'unlockDateTime',
				hidden : true,
				renderer : function(value) {
					if(!Ext.isEmpty(value)){
						return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
					} else {
						return null;
					}
				}
			}, {
				header : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.lockCustomerCode'),
				dataIndex : 'lockCustomerCode',
				hidden : true
			}, {
				header : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.lockCustomerName'),
				dataIndex : 'lockCustomerName',
				hidden : true
			}, {
				header : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.collectionType'),
				dataIndex : 'collectionType',
				hidden : true,
				renderer : function(value) {
					var displayField = FossDataDictionary.rendererSubmitToDisplay(value,'BILL_RECEIVABLE__COLLECTION_TYPE');
					return displayField;
				}
			}, {
				header : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.collectionName'),
				dataIndex : 'collectionName',
				hidden : true
			}, {
				header : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.auditUserCode'),
				dataIndex : 'auditUserCode',
				hidden : true
			}, {
				header : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.auditUserName'),
				dataIndex : 'auditUserName',
				hidden : true
			}, {
				header : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.approveStatus'),
				dataIndex : 'approveStatus',
				hidden : true,
				renderer : function(value) {
					var displayField = FossDataDictionary.rendererSubmitToDisplay(value, 'NOTE_APPLICATION__APPROVE_STATUS');
					return displayField;
				}
			}, {
				header : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.auditDate'),
				dataIndex : 'auditDate',
				hidden : true,
				xtype: 'datecolumn',
				format:'Y-m-d H:i:s'
			}, {
				header : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.disableUserCode'),
				dataIndex : 'disableUserCode',
				hidden : true
			}, {
				header : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.disableUserName'),
				dataIndex : 'disableUserName',
				hidden : true
			}, {
				header : consumer.queryReceivableBill.i18n('foss.stl.consumer.common.notes'),
				dataIndex : 'notes',
				hidden : true
			}, {
				header : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.expressOrigOrgCode'),
				dataIndex : 'expressOrigOrgCode',
				hidden : true
			}, {
				header : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.expressOrigOrgName'),
				dataIndex : 'expressOrigOrgName',
				hidden : true
			}, {
				header : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.expressDestOrgCode'),
				dataIndex : 'expressDestOrgCode',
				hidden : true
			}, {
				header : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.expressDestOrgName'),
				dataIndex : 'expressDestOrgName',
				hidden : true
			},{
				header : consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.invoiceMark'),
				dataIndex : 'invoiceMark',
				hidden : true,
				renderer:function(value){
					var displayField = FossDataDictionary.rendererSubmitToDisplay (value,'SETTLEMENT_INVOICE_MARK');
					return  displayField;
				}
			},{
                header:consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.isUnified'),
                dataIndex:'unifiedSettlement',
                renderer:function(value){
                    if(value=='Y'){
                        return consumer.queryReceivableBill.i18n('foss.stl.consumer.note.common.yes');
                    }else{
                        return consumer.queryReceivableBill.i18n('foss.stl.consumer.note.common.no');
                    }
                },
                hidden:true
            },{
                header:consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.contractOrgCode'),
                dataIndex:'contractOrgCode',
                hidden:true
            },{
                header:consumer.queryReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.contractOrgName'),
                dataIndex:'contractOrgName',
                hidden:true
            },{
                header:consumer.queryReceivableBill.i18n('foss.stl.consumer.common.isDiscount'),
                dataIndex:'isDiscount',
                renderer:function(value){
                   if(value == 'Y'){
                       return consumer.queryReceivableBill.i18n('foss.stl.consumer.common.yes');
                   }else {
                       return consumer.queryReceivableBill.i18n('foss.stl.consumer.common.no');
                   }
               }
           }]
		});

// 分片Tab
Ext.define('Foss.Stlyuscyings.StlyuscyingsQueryDetailsTab', {
			extend : 'Ext.panel.Panel',
			layout : 'card'
		});

/**
 * 主启动方法
 */
Ext.onReady(function() {
	Ext.QuickTips.init();

	var StlyuscyingsQueryInfoTab = Ext
			.create('Foss.Stlyuscyings.StlyuscyingsQueryInfoTab');

	var BillNoQueryInfoGrid = Ext.create(
			'Foss.Stlyuscyings.BillNoQueryInfoGrid', {
				id : 'Foss_Stlyuscyings_BillNoQueryInfoGrid_Id'
			});

	// 创建按日期查询应收单列表Store
	var DateQueryInfoGridStore = Ext.create(
			'Foss.Stlyuscyings.DateQueryEntityStore', {
				listeners : {
					beforeload : function(store, operation, eOpts) {
						var form = Ext.getCmp('Foss_Stlyuscyings_StlyuscyingsQueryInfoTab_QueryByDate_ID');
						if (form) {
							var billReceivableVO = new Object();
							var startDateTemp;
							var endDateTemp;
							
							var selectBusinessDate = Ext.getCmp('queryReceivableBill_BusinessData_Id').getValue();
							var selectAccountDate = Ext.getCmp('queryReceivableBill_AccountData_Id').getValue();
							var selectDateType;//日期类型
							var selectDeptType=form.getValues().selectDeptType;//部门类型
							var customerType =  form.getForm().findField('customerType').getValue();//获取客户类型
							var customerCodeSelected;
							var isDept;
							startDateTemp = form.getForm().findField('startDate').getValue();
							endDateTemp = form.getForm().findField('endDate').getValue();

							if (selectBusinessDate && !selectAccountDate) {
								selectDateType = 'BU';

							} else if (!selectBusinessDate && selectAccountDate) {
								selectDateType = 'AC';
							}	
							
							//判断用户是查询始发或者是到达（默认查始发）							
							if('1'==selectDeptType){
								isDept='DUNNING_ORG_CODE'
							}else{
								isDept='GENERATING_ORG_CODE'
							}
							
							//获取客户编码
							customerCodeSelected = null;
							var arrCode = ['01','02','03','04','05','06','07'];
							var arrName = ['customerName','vehAgencyCode','airAgenyCode','airlineName','landStowage','packagingCode','homesupply'];
							
							for(var i=0;i<arrCode.length;i++) { 
								if (customerType == arrCode[i]){
									customerCodeSelected = form.getForm().findField(arrName[i]).getValue();
								} 
							} 
							
							var params = {
								'billReceivableVO.dto.selectDateType' : selectDateType,
								'billReceivableVO.dto.startDate' : startDateTemp,
								'billReceivableVO.dto.endDate' : endDateTemp,
								'billReceivableVO.dto.department' : form.getForm().findField('department').getValue(),
								'billReceivableVO.dto.bigArea' : form.getForm().findField('bigArea').getValue(),
								'billReceivableVO.dto.smallArea' : form.getForm().findField('smallArea').getValue(),
								'billReceivableVO.dto.sourceBillType' : form.getForm().findField('sourceBillType').getValue(),
								'billReceivableVO.dto.customerCode' :customerCodeSelected,		
								'billReceivableVO.dto.billType' : form.getForm().findField('billType').getValue(),
								'billReceivableVO.dto.active' : form.getForm().findField('active').getValue(),
								'billReceivableVO.dto.isInit' : form.getForm().findField('isInit').getValue(),
								'billReceivableVO.dto.isSign' : form.getForm().findField('isSign').getValue(),
								'billReceivableVO.dto.isDiscount' : form.getForm().findField('isDiscount').getValue(),
								'billReceivableVO.dto.isDept' : isDept,
								'billReceivableVO.dto.productCode' : consumer.queryReceivableBill.convertProductCode(form.getForm().findField('productCode').getValue())
							};
							Ext.apply(operation, {
										params : params
									});
						}
					}
				}
			});

	// 按日期查询应收单列表Grid
	var DateQueryInfoGrid = Ext.create('Foss.Stlyuscyings.DateQueryInfoGrid', {
		id : 'Foss_Stlyuscyings_DateQueryInfoGrid_Id',
		store : DateQueryInfoGridStore,
		dockedItems : [{
					xtype : 'toolbar',
					dock : 'top',
					layout : 'column',
					items : [{
								xtype : 'button',
								text : consumer.queryReceivableBill.i18n('foss.stl.consumer.common.export'),
								disabled:!consumer.queryReceivableBill.isPermission('/stl-web/consumer/exportBillReceivable.action'),
								hidden:!consumer.queryReceivableBill.isPermission('/stl-web/consumer/exportBillReceivable.action'),
								columnWidth : .1,
								handler : function() {
									consumer.exportBillReceivable('TD');
								}
							},{
								xtype : 'button',
								disabled:!consumer.queryReceivableBill.isPermission('/stl-web/consumer/stampReceivable.action'),
								hidden:!consumer.queryReceivableBill.isPermission('/stl-web/consumer/stampReceivable.action'),
								text : consumer.queryReceivableBill.i18n('foss.stl.consumer.common.stampReceivable'),
								columnWidth : .1,
								handler : function() {
									//ddw
									var grid=Ext.getCmp('Foss_Stlyuscyings_DateQueryInfoGrid_Id');
									consumer.queryReceivableBill.stampReceivableBatch(grid);
								}
							},{
								xtype : 'button',
								disabled:!consumer.queryReceivableBill.isPermission('/stl-web/consumer/reverseStampReceivable.action'),
								hidden:!consumer.queryReceivableBill.isPermission('/stl-web/consumer/reverseStampReceivable.action'),
								text : consumer.queryReceivableBill.i18n('foss.stl.consumer.common.reverseStampReceivable'),
								columnWidth : .1,
								handler : function() {
									var grid=Ext.getCmp('Foss_Stlyuscyings_DateQueryInfoGrid_Id');
									consumer.queryReceivableBill.reverseStampReceivableBatch(grid);
								}
							}]
				}, {
					xtype : 'toolbar',
					dock : 'bottom',
					layout : 'vbox',
					items : [{
								height : 5,
								width : 1600
							}, {
								xtype : 'panel',
								layout : 'hbox',
								items : [{
									xtype : 'textfield',
									readOnly : true,
									name : 'totalRecordsInDBInDateGrid',
									columnWidth : .1,
									id : 'Foss_Stlyuscyings_DateQueryInfoGrid_TotalRecordsInDB_Id',
									labelWidth : 40,
									fieldLabel : consumer.queryReceivableBill.i18n('foss.stl.consumer.common.totalRecords')
								}, {
									xtype : 'textfield',
									readOnly : true,
									name : 'totalAmountInDateGrid',
									columnWidth : .15,
									id : 'Foss_Stlyuscyings_DateQueryInfoGrid_TotalAmount_Id',
									labelWidth : 56,
									fieldLabel : consumer.queryReceivableBill.i18n('foss.stl.consumer.common.totalAmount')
								}, {
									xtype : 'textfield',
									readOnly : true,
									name : 'verifyAmountInDateGrid',
									columnWidth : .15,
									id : 'Foss_Stlyuscyings_DateQueryInfoGrid_VerifyAmount_Id',
									labelWidth : 88,
									fieldLabel : consumer.queryReceivableBill.i18n('foss.stl.consumer.common.verifyAmount')
								}, {
									xtype : 'textfield',
									readOnly : true,
									name : 'unverifyAmountInDateGrid',
									columnWidth : .15,
									id : 'Foss_Stlyuscyings_DateQueryInfoGrid_UnverifyAmount_Id',
									labelWidth : 88,
									fieldLabel : consumer.queryReceivableBill.i18n('foss.stl.consumer.common.unverifyAmount')
								}]
							}, Ext.create('Deppon.StandardPaging', {
										store : DateQueryInfoGridStore,
										pageSize : 50,
										columnWidth : 1,
										plugins : Ext.create('Deppon.ux.PageSizePlugin', {
													// 设置分页记录最大值，防止输入过大的数值
													maximumSize : 200
												})
									})]
				}]
	});

	var queryDetailsByReceivableNoGrid = Ext.create('Foss.Stlyuscyings.StlyuscyingsQueryDetailsTab', {
				items : [DateQueryInfoGrid, BillNoQueryInfoGrid]
			});

	Ext.create('Ext.panel.Panel', {
				id : 'T_consumer-queryReceivableBill_content',
				cls : "panelContentNToolbar",
				bodyCls : 'panelContentNToolbar-body',
				layout : 'auto',
				items : [StlyuscyingsQueryInfoTab,
						queryDetailsByReceivableNoGrid],
				getGrid:function(){
					return queryDetailsByReceivableNoGrid;
				},
				renderTo : 'T_consumer-queryReceivableBill-body',
				listeners:{
					'boxready':function(th){
						var form = Ext.getCmp('Foss_Stlyuscyings_StlyuscyingsQueryInfoTab_QueryByDate_ID');
						var dept = form.getForm().findField('department');
						if(!Ext.isEmpty(stl.currentDept.code)){
							var displayText = stl.currentDept.name
							var valueText = stl.currentDept.code;
							var store = dept.store;
							var  key = dept.displayField + '';
							var value = dept.valueField+ '';
							
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