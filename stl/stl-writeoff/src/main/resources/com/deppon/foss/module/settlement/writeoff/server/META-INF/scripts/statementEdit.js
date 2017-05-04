/**
 * 提示信息
 * @param {} message 
 * @param {} yesFn
 * @param {} noFn
 */
writeoff.statementEdit.statementConfirmAlert = function(message,yesFn,noFn){
	Ext.Msg.confirm(writeoff.statementEdit.i18n('foss.stl.writeoff.common.alert'),message,function(o){
		if(o=='yes'){
			yesFn();
		}else{
			noFn();
		}
	});
};
//未申请发票
writeoff.statementEdit.statementNotApply = function(){
	
	if(!writeoff.statementEdit.orgCodeInspect()){
		return false;
	}
	var statementOfAccountMakeQueryResultVo,
		statementOfAccountMakeQueryResultDto,
		data = [];	
	
	var selections=Ext.getCmp('T_writeoff-statementEdit_content').getStatementQueryGrid().getSelectionModel().getSelection();
	//定义批量更新对账单状态的id
	for(var i=0;i<selections.length;i++){
		if(selections[0].get('customerCode')!=selections[i].get('customerCode')){
			Ext.Msg.alert(writeoff.statementEdit.i18n('foss.stl.writeoff.common.alert'),
					writeoff.statementEdit.i18n('foss.stl.writeoff.statementEdit.differenceCustomerCode'));
			return false;
		}
		if(Ext.isEmpty(selections[i].get('confirmStatus'))||selections[i].get('confirmStatus')=='N'){
			Ext.Msg.alert(writeoff.statementEdit.i18n('foss.stl.writeoff.common.alert'),
					writeoff.statementEdit.i18n('foss.stl.writeoff.statementEdit.unConfirmToNotApplyWarning'));
			return false;
		}
		if(Ext.isEmpty(selections[i].get('invoiceStatus'))||selections[i].get('invoiceStatus')=='NP'){
			Ext.Msg.alert(writeoff.statementEdit.i18n('foss.stl.writeoff.common.alert'),
					writeoff.statementEdit.i18n('foss.stl.writeoff.statementEdit.errorStatusToNotApplyWarning'));
			return false;
		}
		if(Ext.isEmpty(selections[i].get('applyInvoice'))||selections[i].get('applyInvoice')=='N'){
			Ext.Msg.alert(writeoff.statementEdit.i18n('foss.stl.writeoff.common.alert'),
					writeoff.statementEdit.i18n('foss.stl.writeoff.statementEdit.errorNotApplyInvoiceWarning'));
			return false;
		}
		if(Ext.isEmpty(selections[i].get('statementBillNo'))){
			Ext.Msg.alert(writeoff.statementEdit.i18n('foss.stl.writeoff.common.alert'),
					writeoff.statementEdit.i18n('foss.stl.writeoff.statementEdit.statementNoIsNullWarning'));
			return false;
		}
		//将对账单添加到面板中
		data.push(selections[i].data);
	}
	//拼接vo，注入到后台
	statementOfAccountMakeQueryResultVo = new Object();
	statementOfAccountMakeQueryResultDto = new Object();
	statementOfAccountMakeQueryResultDto.statementOfAccountList = data;
	statementOfAccountMakeQueryResultVo.invoiceStatus = writeoff.STATEMENTINVOICESTATUS_N;
	statementOfAccountMakeQueryResultVo.statementOfAccountMakeQueryResultDto = statementOfAccountMakeQueryResultDto;
	//提交后台
	Ext.Ajax.request({
		url:writeoff.realPath('applyStatement.action'),
		jsonData:{
			'statementOfAccountMakeQueryResultVo':statementOfAccountMakeQueryResultVo
		},
		success:function(response){
			var grid = Ext.getCmp('T_writeoff-statementEdit_content').getStatementQueryGrid();
			grid.store.load();
			Ext.ux.Toast.msg(writeoff.statementEdit.i18n('foss.stl.writeoff.common.alert'), writeoff.statementEdit.i18n('foss.stl.writeoff.statementEdit.notApplySuccess'), 'ok', 1000);
		},
		exception:function(response){
			var result = Ext.decode(response.responseText);
			Ext.Msg.alert(writeoff.statementEdit.i18n('foss.stl.writeoff.common.alert'),result.message);
		}
	});
}
//已申请发票
writeoff.statementEdit.statementApplied = function(){
	
	if(!writeoff.statementEdit.orgCodeInspect()){
		return false;
	}
	var statementOfAccountMakeQueryResultVo,
		statementOfAccountMakeQueryResultDto,
		data = [];	
	
	var selections=Ext.getCmp('T_writeoff-statementEdit_content').getStatementQueryGrid().getSelectionModel().getSelection();
	//定义批量更新对账单状态的id
	for(var i=0;i<selections.length;i++){
		if(selections.length > 1){
			if(selections[0].get('customerCode')!=selections[i].get('customerCode')){
				Ext.Msg.alert(writeoff.statementEdit.i18n('foss.stl.writeoff.common.alert'),
						writeoff.statementEdit.i18n('foss.stl.writeoff.statementEdit.differenceCustomerCode'));
				return false;
			}
		}
		if(Ext.isEmpty(selections[i].get('confirmStatus'))||selections[i].get('confirmStatus')=='N'){
			Ext.Msg.alert(writeoff.statementEdit.i18n('foss.stl.writeoff.common.alert'),
					writeoff.statementEdit.i18n('foss.stl.writeoff.statementEdit.unConfirmToAppliedWarning'));
			return false;
		}
		if(Ext.isEmpty(selections[i].get('invoiceStatus'))||selections[i].get('invoiceStatus')=='AP'){
			Ext.Msg.alert(writeoff.statementEdit.i18n('foss.stl.writeoff.common.alert'),
					writeoff.statementEdit.i18n('foss.stl.writeoff.statementEdit.errorStatusToAppliedWarning'));
			return false;
		}
		if(Ext.isEmpty(selections[i].get('applyInvoice'))||selections[i].get('applyInvoice')=='N'){
			Ext.Msg.alert(writeoff.statementEdit.i18n('foss.stl.writeoff.common.alert'),
					writeoff.statementEdit.i18n('foss.stl.writeoff.statementEdit.errorNotApplyInvoiceWarning'));
			return false;
		}
		if(Ext.isEmpty(selections[i].get('statementBillNo'))){
			Ext.Msg.alert(writeoff.statementEdit.i18n('foss.stl.writeoff.common.alert'),
					writeoff.statementEdit.i18n('foss.stl.writeoff.statementEdit.statementNoIsNullWarning'));
			return false;
		}
		//将对账单添加到面板中
		data.push(selections[i].data);
	}

	//拼接vo，注入到后台
	statementOfAccountMakeQueryResultVo = new Object();
	statementOfAccountMakeQueryResultDto = new Object();
	statementOfAccountMakeQueryResultDto.statementOfAccountList = data;
	statementOfAccountMakeQueryResultVo.invoiceStatus = writeoff.STATEMENTINVOICESTATUS_Y;
	statementOfAccountMakeQueryResultVo.statementOfAccountMakeQueryResultDto = statementOfAccountMakeQueryResultDto;
	//提交后台
	Ext.Ajax.request({
		url:writeoff.realPath('applyStatement.action'),
		jsonData:{
			'statementOfAccountMakeQueryResultVo':statementOfAccountMakeQueryResultVo
		},
		success:function(response){
			var grid = Ext.getCmp('T_writeoff-statementEdit_content').getStatementQueryGrid();
			grid.store.load();
			Ext.ux.Toast.msg(writeoff.statementEdit.i18n('foss.stl.writeoff.common.alert'), 
					writeoff.statementEdit.i18n('foss.stl.writeoff.statementEdit.appliedSuccess'), 'ok', 1000);
		},
		exception:function(response){
			var result = Ext.decode(response.responseText);
			Ext.Msg.alert(writeoff.statementEdit.i18n('foss.stl.writeoff.common.alert'),result.message);
		}
	});
}

/**
 * 查询成功后，后续处理方法
 * @param {} action
 */
writeoff.statementEdit.statementSelectSuccess = function(result){
	var statementQueryGridStore,
		totalAmount;
	
	statementQueryGridStore = Ext.getCmp('T_writeoff-statementEdit_content').getStatementQueryGrid().store;
	//加载后台查询到的数据到grid的store中
	var statementOfAccountList = result.statementOfAccountMakeQueryResultVo.statementOfAccountMakeQueryResultDto.statementOfAccountList
	//声明本次剩余未还总金额
	var totalAmount = result.statementOfAccountMakeQueryResultVo.statementOfAccountMakeQueryResultDto.totalUnpaidAmount;
	var totalCount = result.statementOfAccountMakeQueryResultVo.statementOfAccountMakeQueryResultDto.totalCount;
	if(!Ext.isEmpty(statementOfAccountList)){
		statementQueryGridStore.loadData(statementOfAccountList);
			//动态给总条数设置值
		Ext.getCmp('Foss_statementBill_StatementQueryGrid_TotalCount_ID').setValue(totalCount);
		
		//动态给总金额设置值
		Ext.getCmp('Foss_statementBill_StatementQueryGrid_TotalAmount_ID').setValue(totalAmount);
//		Ext.getCmp('T_writeoff-statementEdit_content').getStatementQueryGrid().show();
	}else{
		statementQueryGridStore.removeAll();
		Ext.getCmp('Foss_statementBill_StatementQueryGrid_TotalAmount_ID').setValue(0);
		Ext.Msg.alert(writeoff.statementEdit.i18n('foss.stl.writeoff.common.alert'),writeoff.statementEdit.i18n('foss.stl.writeoff.common.noResult'));
	}
		
};

/**
 * 根据客户查询对账单
 */
writeoff.statementEdit.statementSelectByCust = function(){
	var form  = this.up('form').getForm();	
	//声明客户编码和客户名称
	var customerCode,customerName;
	
	if(form.isValid()){
		//邓大伟
		writeoff.statementEdit.statementOrgCode = form.findField('statementOrgCode').getValue();
		writeoff.statementEdit.largeRegion = form.findField('largeRegion').getValue();
		writeoff.statementEdit.smallRegion = form.findField('smallRegion').getValue();
		writeoff.statementEdit.invoiceStatus = form.findField('invoiceStatus').getValue();
		writeoff.statementEdit.applyInvoice = form.findField('applyInvoice').getValue();
		if(Ext.isEmpty(writeoff.statementEdit.statementOrgCode)
				&&Ext.isEmpty(writeoff.statementEdit.largeRegion)
				&&Ext.isEmpty(writeoff.statementEdit.smallRegion)){
			Ext.Msg.alert(writeoff.statementEdit.i18n('foss.stl.writeoff.common.alert'),writeoff.statementEdit.i18n('foss.stl.writeoff.statementAdd.orgCodeNull'));
			return false;
		}
		//校验日期
		var periodBeginDate = form.findField('periodBeginDate').getValue();
		var periodEndDate = form.findField('periodEndDate').getValue();
		//校验日期
		if(Ext.isEmpty(periodBeginDate)||Ext.isEmpty(periodEndDate)){
			Ext.Msg.alert(writeoff.statementEdit.i18n('foss.stl.writeoff.common.alert'),writeoff.statementEdit.i18n('foss.stl.writeoff.statementAdd.quryDateIsNullWarning'));
			return false;
		}
		//比较起始日期和结束日期
		var compareTwoDate = stl.compareTwoDate(periodBeginDate,periodEndDate);
		if(compareTwoDate>stl.DATELIMITDAYS_MONTH){
			Ext.Msg.alert(writeoff.statementEdit.i18n('foss.stl.writeoff.common.alert'),writeoff.statementEdit.i18n('foss.stl.writeoff.statementEdit.queryDateMaxLimit'));
			return false;
		}else if(compareTwoDate<1){
			Ext.Msg.alert(writeoff.statementEdit.i18n('foss.stl.writeoff.common.alert'),writeoff.statementEdit.i18n('foss.stl.writeoff.common.dateEndBeforeStatrtWarining'));
			return false;
		}
		//获取客户类型
		var customerType =  form.findField('customerType').getValue();
			//校验客户/代理信息
		if(customerType=='01'){
			//客户编码
			customerCode = form.getValues().customerCode;
			//客户名称
			customerName = form.findField('customerCode').getRawValue();
		//如果为偏线代理，则校验偏线代理信息不能为空	
		}else if(customerType=='02'){
			//客户编码
			customerCode = form.getValues().vehagencycomp;
			//客户名称
			customerName = form.findField('vehagencycomp').getRawValue();
		//如果为空运代理
		}else if(customerType=='03'){
			//客户编码
			customerCode = form.getValues().airagency;
			//客户名称
			customerName = form.findField('airagency').getRawValue();
		//如果为航空公司
		}else if(customerType=='04'){
			//客户编码
			customerCode = form.getValues().airline;
			//客户名称
			customerName = form.findField('airline').getRawValue();
		}else if(customerType=='04'){
			//客户编码
			customerCode = form.getValues().airline;
			//客户名称
			customerName = form.findField('airline').getRawValue();
		}else if(customerType=='05'){
			//客户编码
			customerCode = form.getValues().landStowage;
			//客户名称
			customerName = form.findField('landStowage').getRawValue();
		}	
		//设置隐藏域数据
		var hiddenfield = Ext.getCmp('T_writeoff-statementEdit_content').getHiddenField();
		hiddenfield.getForm().findField('queryTypeParam').setValue(writeoff.STATEMENTQUERYTAB_BYCUSTOMER);
		hiddenfield.getForm().findField('customerCodeParam').setValue(customerCode);
		hiddenfield.getForm().findField('periodBeginDateParam').setValue(form.findField('periodBeginDate').getValue());
		hiddenfield.getForm().findField('periodEndDateParam').setValue(form.findField('periodEndDate').getValue());
		hiddenfield.getForm().findField('confirmStatusParam').setValue(form.getValues().confirmStatus);
		hiddenfield.getForm().findField('settleStatusParam').setValue(form.getValues().settleStatus);
		var grid = Ext.getCmp('T_writeoff-statementEdit_content').getStatementQueryGrid();
		//查询后台
		grid.store.loadPage(1,{
			callback: function(records, operation, success) {
				var rawData = grid.store.proxy.reader.rawData;
				if(!success && ! rawData.isException){
					var result = Ext.decode(operation.response.responseText);	
					Ext.Msg.alert(writeoff.statementEdit.i18n('foss.stl.writeoff.common.alert'),result.message);
				}
				if(success){
					var result = Ext.decode(operation.response.responseText);
					writeoff.statementEdit.statementSelectSuccess(result);	
				}
		    }
		});
	}
};

/**
 * 根据对账单号查询
 */
writeoff.statementEdit.statementSelectByStatementNumber = function(){		
	var form  = this.up('form').getForm();	
	
	var statementBillNumbers = form.findField('statementBillNumbers').getValue();
	//判断传入单号是否为null或''
	if(Ext.String.trim(statementBillNumbers)!=null && Ext.String.trim(statementBillNumbers)!=''){
		var billNumberArray = stl.splitToArray(statementBillNumbers);
		 for(var i=0;i<billNumberArray.length;i++){
		 	//循环将空格等剔除掉
		 	if(Ext.String.trim(billNumberArray[i])==''){
		 		billNumberArray.pop(billNumberArray[i]);
		 	}
		 }
		 //判断输入单号是否超过10个
		 if(billNumberArray.length>10){
		 	Ext.Msg.alert(writeoff.statementEdit.i18n('foss.stl.writeoff.common.alert'),writeoff.statementEdit.i18n('foss.stl.writeoff.common.queryNosLimit'));
		 	return false;
		 }
	}else{
		Ext.Msg.alert(writeoff.statementEdit.i18n('foss.stl.writeoff.common.alert'),writeoff.statementEdit.i18n('foss.stl.writeoff.common.billNosIsNullWarning'));
		return false;
	}
	//当界面校验都通过后，才执行查询方法
	if(form.isValid()){
		var hiddenfield = Ext.getCmp('T_writeoff-statementEdit_content').getHiddenField();
		hiddenfield.getForm().findField('queryTypeParam').setValue(writeoff.STATEMENTQUERYTAB_BYSTATEMENTNUMBER);
		hiddenfield.getForm().findField('statementBillNumbersParam').setValue(statementBillNumbers);
		var grid = Ext.getCmp('T_writeoff-statementEdit_content').getStatementQueryGrid();
		//查询后台
		grid.store.loadPage(1,{
			callback: function(records, operation, success) {
				var rawData = grid.store.proxy.reader.rawData;
				if(!success && ! rawData.isException){
					var result = Ext.decode(operation.response.responseText);	
					Ext.Msg.alert(writeoff.statementEdit.i18n('foss.stl.writeoff.common.alert'),result.message);
				}
				if(success){
					var result = Ext.decode(operation.response.responseText);
					writeoff.statementEdit.statementSelectSuccess(result);	
				}
		    }
		});
	}else{
		Ext.Msg.alert(writeoff.statementEdit.i18n('foss.stl.writeoff.common.alert'),writeoff.statementEdit.i18n('foss.stl.writeoff.common.validateFailAlert'));
		return false;
	}	
};

/**
 * 按单号查询
 */
writeoff.statementEdit.statementSelectByNumber = function(){		
	var form  = this.up('form').getForm();	
	//输入单号集合
	var numbers = form.findField('numbers').getValue();
	//判断传入单号是否为null或''
	if(Ext.String.trim(numbers)!=null && Ext.String.trim(numbers)!=''){
		var billNumberArray = stl.splitToArray(numbers);
		 for(var i=0;i<billNumberArray.length;i++){
		 	//循环将空格等剔除掉
		 	if(Ext.String.trim(billNumberArray[i])==''){
		 		billNumberArray.pop(billNumberArray[i]);
		 	}
		 }
		 //判断输入单号是否超过10个
		 if(billNumberArray.length>10){
		 	Ext.Msg.alert(writeoff.statementEdit.i18n('foss.stl.writeoff.common.alert'),writeoff.statementEdit.i18n('foss.stl.writeoff.common.queryNosLimit'));
		 	return false;
		 }
	}else{
		Ext.Msg.alert(writeoff.statementEdit.i18n('foss.stl.writeoff.common.alert'),writeoff.statementEdit.i18n('foss.stl.writeoff.common.billNosIsNullWarning'));
		return false;
	}
	//当界面校验都通过后，才执行查询方法
	if(form.isValid()){
		var hiddenfield = Ext.getCmp('T_writeoff-statementEdit_content').getHiddenField();
		hiddenfield.getForm().findField('queryTypeParam').setValue(writeoff.STATEMENTQUERYTABTAB_BYWAYNUMBER);
		hiddenfield.getForm().findField('numbersParam').setValue(numbers);
		var grid = Ext.getCmp('T_writeoff-statementEdit_content').getStatementQueryGrid();
		//查询后台
		grid.store.loadPage(1,{
			callback: function(records, operation, success) {
				var rawData = grid.store.proxy.reader.rawData;
				if(!success && ! rawData.isException){
					var result = Ext.decode(operation.response.responseText);	
					Ext.Msg.alert(writeoff.statementEdit.i18n('foss.stl.writeoff.common.alert'),result.message);
				}
				if(success){
					var result = Ext.decode(operation.response.responseText);
					writeoff.statementEdit.statementSelectSuccess(result);	
				}
		    }
		});
	}else{
		Ext.Msg.alert(writeoff.statementEdit.i18n('foss.stl.writeoff.common.alert'),writeoff.statementEdit.i18n('foss.stl.writeoff.common.validateFailAlert'));
		return false;
	}	
};

writeoff.statementEdit.statementFailingInvoice = function(form){
//	var form  = this.up('form').getForm();
	writeoff.statementEdit.failingInvoiceOrgCode = form.findField('failingInvoiceOrgCode').getValue();
	var hiddenfield = Ext.getCmp('T_writeoff-statementEdit_content').getHiddenField();
	hiddenfield.getForm().findField('queryTypeParam').setValue(writeoff.STATEMENTQUERYTAB_FAILINGINVOICE);
	var grid = Ext.getCmp('T_writeoff-statementEdit_content').getStatementQueryGrid();
	//查询后台
	grid.store.loadPage(1,{
		callback: function(records, operation, success) {
			var rawData = grid.store.proxy.reader.rawData;
			if(!success && ! rawData.isException){
				var result = Ext.decode(operation.response.responseText);	
				Ext.Msg.alert(writeoff.statementEdit.i18n('foss.stl.writeoff.common.alert'),result.message);
			}
			if(success){
				var result = Ext.decode(operation.response.responseText);
				writeoff.statementEdit.statementSelectSuccess(result);	
			}
	    }
	});
};

/**
 * 反确认操作
 * @return {Boolean}
 */
writeoff.statementEdit.statementUnConfirm = function(){
	if(!writeoff.statementEdit.orgCodeInspect()){
		return false;
	}
	var statementOfAccountMakeQueryResultVo,
		statementOfAccountMakeQueryResultDto;	
	
	var yesFn=function(){
		var selection=Ext.getCmp('T_writeoff-statementEdit_content').getStatementQueryGrid().getSelectionModel().getSelection();
		if(selection[0].get('confirmStatus')=='N'||selection[0].get('confirmStatus')==''){
			Ext.Msg.alert(writeoff.statementEdit.i18n('foss.stl.writeoff.common.alert'),writeoff.statementEdit.i18n('foss.stl.writeoff.statementEdit.errorStatusToUnConfirmWarning'));
			return false;
		}
		if(selection[0].get('statementBillNo')==''){
			Ext.Msg.alert(writeoff.statementEdit.i18n('foss.stl.writeoff.common.alert'),writeoff.statementEdit.i18n('foss.stl.writeoff.statementEdit.statementNoIsNullWarning'));
			return false;
		}
		//拼接vo，注入到后台
		statementOfAccountMakeQueryResultVo = new Object();
		statementOfAccountMakeQueryResultDto = new Object();
		statementOfAccountMakeQueryResultDto.statementOfAccountList = [selection[0].data];
		statementOfAccountMakeQueryResultVo.confirmStatus = writeoff.STATEMENTCONFIRMSTATUS_N;
		statementOfAccountMakeQueryResultVo.statementOfAccountMakeQueryResultDto = statementOfAccountMakeQueryResultDto;
		//提交后台
		Ext.Ajax.request({
			url:writeoff.realPath('confirmStatement.action'),
			jsonData:{
				'statementOfAccountMakeQueryResultVo':statementOfAccountMakeQueryResultVo
			},
			success:function(response){
				var grid = Ext.getCmp('T_writeoff-statementEdit_content').getStatementQueryGrid();
				grid.store.load();
				Ext.ux.Toast.msg(writeoff.statementEdit.i18n('foss.stl.writeoff.common.alert'), writeoff.statementEdit.i18n('foss.stl.writeoff.statementEdit.unConfirmSuccess'), 'ok', 1000);
			},
			exception:function(response){
				var result = Ext.decode(response.responseText);
				Ext.Msg.alert(writeoff.statementEdit.i18n('foss.stl.writeoff.common.alert'),result.message);
			}
		});
	};
	var noFn=function(){
	 	return false;
	};
	writeoff.statementEdit.statementConfirmAlert(writeoff.statementEdit.i18n('foss.stl.writeoff.statementEdit.isUnConfirm'),yesFn,noFn);
}

/**
 * 对账单确认  
 */
writeoff.statementEdit.statementConfReceipt = function(selection){
	if(!writeoff.statementEdit.orgCodeInspect()){
		return false;
	}
	writeoff.statementConfReceipt(selection);
}

/**
 * 对账单确认  
 */
writeoff.statementEdit.statementConfirm = function(){
	if(!writeoff.statementEdit.orgCodeInspect()){
		return false;
	}
	var yesFn=function(){
		//是否要发送邮件提示
		Ext.Msg.confirm(writeoff.statementEdit.i18n('foss.stl.writeoff.common.alert'),'是否要发送邮件通知？',function(o){
			if(o=='yes'){
				Ext.Msg.confirm(writeoff.statementEdit.i18n('foss.stl.writeoff.common.alert'),'是否需要申请发票',function(o){
					if(o=='yes'){
						var applyInvoice = 'Y';
						var sendFlag = writeoff.SENDEMAIL_Y;
						writeoff.statementEdit.statementConfirm_SendEmail(sendFlag,applyInvoice);
					}else{
						var applyInvoice = 'N';
						var sendFlag = writeoff.SENDEMAIL_Y;
						writeoff.statementEdit.statementConfirm_SendEmail(sendFlag,applyInvoice);
					}
				});
			}else{
				Ext.Msg.confirm(writeoff.statementEdit.i18n('foss.stl.writeoff.common.alert'),'是否需要申请发票',function(o){
					if(o=='yes'){
						var applyInvoice = 'Y';
						var sendFlag = writeoff.SENDEMAIL_N;
						writeoff.statementEdit.statementConfirm_SendEmail(sendFlag,applyInvoice);
					}else{
						var applyInvoice = 'N';
						var sendFlag = writeoff.SENDEMAIL_N;
						writeoff.statementEdit.statementConfirm_SendEmail(sendFlag,applyInvoice);
					}
				});
			}
		});
	};
	var noFn=function(){
	 	return false;
	};
	writeoff.statementEdit.statementConfirmAlert(writeoff.statementEdit.i18n('foss.stl.writeoff.statementEdit.isConfirm'),yesFn,noFn);
}

/**
 * 对账单确认发送邮件
 */
writeoff.statementEdit.statementConfirm_SendEmail = function(sendFlag,applyInvoice){
	
	var selection=Ext.getCmp('T_writeoff-statementEdit_content').getStatementQueryGrid().getSelectionModel().getSelection();
	if(selection[0].get('confirmStatus')==writeoff.STATEMENTCONFIRMSTATUS_Y||selection[0].get('confirmStatus')==''){
		Ext.Msg.alert(writeoff.statementEdit.i18n('foss.stl.writeoff.common.alert'),writeoff.statementEdit.i18n('foss.stl.writeoff.statementEdit.errorStatusToConfirmWarning'));
		return false;
	}
	if(selection[0].get('statementBillNo')==''){
		Ext.Msg.alert(writeoff.statementEdit.i18n('foss.stl.writeoff.common.alert'),writeoff.statementEdit.i18n('foss.stl.writeoff.statementEdit.statementNoIsNullWarning'));
		return false;
	}
	
	//拼接vo，注入到后台
	statementOfAccountMakeQueryResultVo = new Object();
	statementOfAccountMakeQueryResultDto = new Object();
	statementOfAccountMakeQueryResultDto.statementOfAccountList = [selection[0].data];
	statementOfAccountMakeQueryResultDto.sendMailFlag = sendFlag;
	statementOfAccountMakeQueryResultVo.confirmStatus = writeoff.STATEMENTCONFIRMSTATUS_Y;
	statementOfAccountMakeQueryResultVo.applyInvoice = applyInvoice;
	statementOfAccountMakeQueryResultVo.statementOfAccountMakeQueryResultDto = statementOfAccountMakeQueryResultDto;
	Ext.Ajax.request({
		url:writeoff.realPath('confirmStatement.action'),
		jsonData:{
			'statementOfAccountMakeQueryResultVo':statementOfAccountMakeQueryResultVo
		},
		success:function(response){
			var grid = Ext.getCmp('T_writeoff-statementEdit_content').getStatementQueryGrid();
			grid.store.load();
			Ext.ux.Toast.msg(writeoff.statementEdit.i18n('foss.stl.writeoff.common.alert'), writeoff.statementEdit.i18n('foss.stl.writeoff.statementEdit.confirmSuccess'), 'ok', 1000);
		},
		exception:function(response){
			var result = Ext.decode(response.responseText);
			Ext.Msg.alert(writeoff.statementEdit.i18n('foss.stl.writeoff.common.alert'),result.message);
		}			
	});
}
/**
 * 部门校验
 */	
writeoff.statementEdit.orgCodeInspect = function(){
	var selection=Ext.getCmp('T_writeoff-statementEdit_content').getStatementQueryGrid().getSelectionModel().getSelection();
	writeoff.statementEdit.statementOrgCode = selection[0].data.createOrgCode;
	writeoff.statementEdit.currentOrgCode = FossUserContext.getCurrentDeptCode();
	if(!Ext.isEmpty(writeoff.statementEdit.statementOrgCode) && !Ext.isEmpty(writeoff.statementEdit.currentOrgCode)){
		if(writeoff.statementEdit.statementOrgCode != writeoff.statementEdit.currentOrgCode){
			Ext.Msg.alert(writeoff.statementEdit.i18n('foss.stl.writeoff.common.alert'),
					writeoff.statementEdit.i18n('foss.stl.writeoff.statementEdit.orgCodeDifferent'));
			return false;
		}
	}else{
		if(Ext.isEmpty(writeoff.statementEdit.statementorgCode)){
			Ext.Msg.alert(writeoff.statementEdit.i18n('foss.stl.writeoff.common.alert'),
					writeoff.statementEdit.i18n('foss.stl.writeoff.statementEdit.statementOrgCode'));
			return false;
		}else if(Ext.isEmpty(writeoff.statementEdit.currentOrgCode)){
			Ext.Msg.alert(writeoff.statementEdit.i18n('foss.stl.writeoff.common.alert'),
					writeoff.statementEdit.i18n('foss.stl.writeoff.statementEdit.currentOrgCode'));
			return false;
		}else{
			Ext.Msg.alert(writeoff.statementEdit.i18n('foss.stl.writeoff.common.alert'),
					writeoff.statementEdit.i18n('foss.stl.writeoff.statementEdit.orgCode'));
			return false;
		}
	}
	return true;
}

/**
 * 查看明细
 */	
writeoff.statementEdit.statementViewDetail = function(){
	var selection=Ext.getCmp('T_writeoff-statementEdit_content').getStatementQueryGrid().getSelectionModel().getSelection();
	var statementOfAccountMakeQueryResultVo = new Object();
	var statementOfAccountMakeQueryResultDto = new Object();
	var statementOfAccount = new Object();
	statementOfAccount.statementBillNo = selection[0].get('statementBillNo');
	statementOfAccountMakeQueryResultVo.statementOfAccountMakeQueryResultDto = statementOfAccountMakeQueryResultDto;
	statementOfAccountMakeQueryResultDto.statementOfAccount = statementOfAccount;
	var yesFn=function(){
		Ext.Ajax.request({
			url:writeoff.realPath('queryStatementDetailByNumber.action'),
			jsonData:{	
				'statementOfAccountMakeQueryResultVo':statementOfAccountMakeQueryResultVo
			},
			success:function(response){
				
				var currentBillsFormAdd,
					result,
					model,
					win;
				result = Ext.decode(response.responseText);	
				model = new Foss.statementbill.StatementFormModel(selection[0].data);
				//获取明细界面
				win =Ext.getCmp('Foss_statementbill_EditStatementEntryWindow_ID');
				if(win==null){
					win = Ext.create('Foss.statementbill.EditStatementEntryWindow',{
						id:'Foss_statementbill_EditStatementEntryWindow_ID'
					});
				}
				currentBillsFormAdd = writeoff.currentBillsForm;
				//控制本期金额图标显示和隐藏
				writeoff.statementUnVerifyAmountShow(model,currentBillsFormAdd,writeoff.PAGEFROM_STATEMENTEDIT);
				//给界面添加数据
				win.items.items[0].loadRecord(model);
				win.items.items[1].loadRecord(model);
				win.items.items[2].loadRecord(model);
//				var statementEntryBeginGrid = win.items.items[4];
				var statementEntryEditGrid = win.items.items[4];
				var statementOfAccountDPeriodListData = result.statementOfAccountMakeQueryResultVo.statementOfAccountMakeQueryResultDto.statementOfAccountDPeriodList;
				//清空表格
//				statementEntryBeginGrid.store.removeAll();
				statementEntryEditGrid.store.removeAll();
				if(!Ext.isEmpty(statementOfAccountDPeriodListData)){
					statementEntryEditGrid.store.loadData(statementOfAccountDPeriodListData);
				}
				statementEntryEditGrid.down('textfield').setValue(statementEntryEditGrid.store.data.length);
				win.show();
				//对账单明细来源 -- 查询
				writeoff.showEntryType = writeoff.PAGEFROM_STATEMENTEDIT;
			},
			exception:function(response){
				var result = Ext.decode(response.responseText);
				Ext.Msg.alert(writeoff.statementEdit.i18n('foss.stl.writeoff.common.alert'),result.message);
			}
		});
	};
	var noFn=function(){
	 	return false;
	};
	writeoff.statementEdit.statementConfirmAlert(writeoff.statementEdit.i18n('foss.stl.writeoff.statementEdit.isWatchEntry'),yesFn,noFn);			
}


/**
 * 核销操作
 */
writeoff.statementEdit.statementWriteoff = function(){
	if(!writeoff.statementEdit.orgCodeInspect()){
		return false;
	}
	var yesFn=function(){
		var grid = Ext.getCmp('T_writeoff-statementEdit_content').getStatementQueryGrid();
		//未核销明细窗口
		var writeoffBillWindow = Ext.getCmp('Foss_statementbill_UnVerifyEntryWindow_Edit_ID');
		if(!writeoffBillWindow){
			writeoffBillWindow = Ext.create('Foss.statementbill.UnVerifyEntryWindow',{id:'Foss_statementbill_UnVerifyEntryWindow_Edit_ID'});	
		}
		var selection=grid.getSelectionModel().getSelection();
		if(selection[0].get('periodAmount')>0){
			Ext.Msg.alert(writeoff.statementEdit.i18n('foss.stl.writeoff.common.alert'),writeoff.statementEdit.i18n('foss.stl.writeoff.statementEdit.errorBillTypeToVerifyWarning'));
			return false;
		}
		if(selection[0].get('unpaidAmount')==0){
			Ext.Msg.alert(writeoff.statementEdit.i18n('foss.stl.writeoff.common.alert'),writeoff.statementEdit.i18n('foss.stl.writeoff.statementEdit.errorSettleStatusToVerifyWarning'));
			return false;
		}
		//跳转页面
		writeoff.statementWriteoffQuery(writeoffBillWindow,[selection[0].data],writeoff.PAGEFROM_STATEMENTEDIT);
	};
	var noFn=function(){
	 	return false;
	};
	writeoff.statementEdit.statementConfirmAlert(writeoff.statementEdit.i18n('foss.stl.writeoff.statementEdit.isVerify'),yesFn,noFn);
}

/**
 * 批量核销
 */
writeoff.statementEdit.statementWriteoffBatch = function(){
	if(!writeoff.statementEdit.orgCodeInspect()){
		return false;
	}
	var me =this,
		selections,
		writeoffBillWindow,
		number,
		jsonData = [],
		customerCode = null
		;
	//获取选择行和核销窗口
	selections = me.getSelectionModel().getSelection();	
	//未核销明细窗口
	writeoffBillWindow = Ext.getCmp('Foss_statementbill_UnVerifyEntryWindow_Edit_ID');
	if(!writeoffBillWindow){
		writeoffBillWindow = Ext.create('Foss.statementbill.UnVerifyEntryWindow',{id:'Foss_statementbill_UnVerifyEntryWindow_Edit_ID'});	
	}
	//判断是否选中行
	if(selections.length==0){
		Ext.Msg.alert(writeoff.statementEdit.i18n('foss.stl.writeoff.common.alert'),writeoff.statementEdit.i18n('foss.stl.writeoff.statementEdit.isVerify'));
		return false;
	}
	
	//定义批量更新对账单状态的id
	for(var i=0;i<selections.length;i++){
		if(selections[i].get('periodAmount')>0){
			Ext.Msg.alert(writeoff.statementEdit.i18n('foss.stl.writeoff.common.alert'),writeoff.statementEdit.i18n('foss.stl.writeoff.statementEdit.choiceStatement')+selections[i].get('statementBillNo')+writeoff.statementEdit.i18n('foss.stl.writeoff.statementEdit.errorBillTypeToVerifyWarning'));
			return false;
		}
		
		if(selections[i].get('unpaidAmount')==0){
			Ext.Msg.alert(writeoff.statementEdit.i18n('foss.stl.writeoff.common.alert'),writeoff.statementEdit.i18n('foss.stl.writeoff.statementEdit.choiceStatement')+selections[i].get('statementBillNo')+writeoff.statementEdit.i18n('foss.stl.writeoff.statementEdit.errorSettleStatusToVerifyWarning'));
			return false;
		}
		
		//判断是否选中同一个客户
		if(customerCode==null){
			customerCode = selections[i].get('customerCode');
		}else{
			if(customerCode!=selections[i].get('customerCode')){
				Ext.Msg.alert(writeoff.statementEdit.i18n('foss.stl.writeoff.common.alert'),writeoff.statementEdit.i18n('foss.stl.writeoff.statementEdit.choiceStatement')+selections[i].get('statementBillNo')+writeoff.statementEdit.i18n('foss.stl.writeoff.statementEdit.notSameCustomerToVerifyWarning'))
				return false;
			}
		}
		//将对账单添加到面板中
		jsonData.push(selections[i].data);
	}
	
	//跳转页面
	writeoff.statementWriteoffQuery(writeoffBillWindow,jsonData,writeoff.PAGEFROM_STATEMENTEDIT);
}


/**
 * 还款
 */
writeoff.statementEdit.statementRepayment = function(){
	if(!writeoff.statementEdit.orgCodeInspect()){
		return false;
	}
	var yesFn=function(){
		var selections=Ext.getCmp('T_writeoff-statementEdit_content').getStatementQueryGrid().getSelectionModel().getSelection();
		if(selections[0].get('periodAmount')<0){
			Ext.Msg.alert(writeoff.statementEdit.i18n('foss.stl.writeoff.common.alert'),writeoff.statementEdit.i18n('foss.stl.writeoff.statementEdit.choiceStatement')+selections[0].get('statementBillNo')+writeoff.statementEdit.i18n('foss.stl.writeoff.statementEdit.apStatementToReceiveWarning'));
			return false;
		}
		if(selections[0].get('unpaidAmount')==0){
			Ext.Msg.alert(writeoff.statementEdit.i18n('foss.stl.writeoff.common.alert'),writeoff.statementEdit.i18n('foss.stl.writeoff.statementEdit.choiceStatement')+selections[0].get('statementBillNo')+writeoff.statementEdit.i18n('foss.stl.writeoff.statementEdit.noAmountToReceiveWarning'));
			return false;
		}
		
		var repaymentBillWindow = Ext.getCmp('T_writeoff-statementEdit_content').getStatementQueryGrid().repaymentBillWindow;//combo.repaymentBillWindow;
		var number = selections[0].get('statementBillNo');
		//还款成功后，调用该方法
		writeoff.statementRepaymentSuccess(repaymentBillWindow,number,[selections[0].data],writeoff.PAGEFROM_STATEMENTEDIT)
	};
	var noFn=function(){
	 	return false;
	};
	writeoff.statementEdit.statementConfirmAlert('是否要进行还款操作？',yesFn,noFn);
}	

/**
 * 批量还款
 */
writeoff.statementEdit.statementRepaymentBatch = function(){
	if(!writeoff.statementEdit.orgCodeInspect()){
		return false;
	}
	var me =this,
		selections,
		repaymentBillWindow,
		number,
		customerCode
		;
	selections = me.getSelectionModel().getSelection();	
	repaymentBillWindow = me.repaymentBillWindow;
	//批量还款要组装对账单号
	number ='';
	customerCode = null;
	if(selections.length==0){
		Ext.Msg.alert(writeoff.statementEdit.i18n('foss.stl.writeoff.common.alert'),writeoff.statementEdit.i18n('foss.stl.writeoff.statementEdit.unSelectedWarning'));
		return false;
	}
	
	var jsonData = [];
	//定义批量更新对账单状态的id
	for(var i=0;i<selections.length;i++){
		if(selections[i].get('periodAmount')<0){
			Ext.Msg.alert(writeoff.statementEdit.i18n('foss.stl.writeoff.common.alert'),writeoff.statementEdit.i18n('foss.stl.writeoff.statementEdit.choiceStatement')+selections[i].get('statementBillNo')+writeoff.statementEdit.i18n('foss.stl.writeoff.statementEdit.apStatementToReceiveWarning'));
			return false;
		}
		if(selections[i].get('unpaidAmount')==0){
			Ext.Msg.alert(writeoff.statementEdit.i18n('foss.stl.writeoff.common.alert'),writeoff.statementEdit.i18n('foss.stl.writeoff.statementEdit.choiceStatement')+selections[i].get('statementBillNo')+writeoff.statementEdit.i18n('foss.stl.writeoff.statementEdit.noAmountToReceiveWarning'));
			return false;
		}
		//判断是否选中同一个客户
		if(customerCode==null){
			customerCode = selections[i].get('customerCode');
		}else{
			if(customerCode!=selections[i].get('customerCode')){
				Ext.Msg.alert(writeoff.statementEdit.i18n('foss.stl.writeoff.common.alert'),writeoff.statementEdit.i18n('foss.stl.writeoff.statementEdit.choiceStatement')+selections[i].get('statementBillNo')+writeoff.statementEdit.i18n('foss.stl.writeoff.statementEdit.notSameCustomerToReceiveWarning'))
				return false;
			}
		}
		//此处本来是在前台处理的
		if(number==''){
			number = selections[i].get('statementBillNo');
		}else{
			number = number+','+selections[i].get('statementBillNo');
		}
		
		jsonData.push(selections[i].data);
	}
	//批量核销处理
	writeoff.statementRepaymentSuccess(repaymentBillWindow,number,jsonData,writeoff.PAGEFROM_STATEMENTEDIT);

}

/**
 * 对账单批量确认
 */
writeoff.statementEdit.statementConfirmBatch = function(){
	if(!writeoff.statementEdit.orgCodeInspect()){
		return false;
	}
	var me = this;
	var selections = me.up('panel').getSelectionModel().getSelection();	
	if(selections.length==0){
		Ext.Msg.alert(writeoff.statementEdit.i18n('foss.stl.writeoff.common.alert'),writeoff.statementEdit.i18n('foss.stl.writeoff.statementEdit.unSelectedWarning'));
		return false;
	}

	//是否要发送邮件提示
	Ext.Msg.confirm(writeoff.statementEdit.i18n('foss.stl.writeoff.common.alert'),'是否要发送邮件通知？',function(o){
		if(o=='yes'){
			Ext.Msg.confirm(writeoff.statementEdit.i18n('foss.stl.writeoff.common.alert'),'是否需要申请发票',function(o){
				if(o=='yes'){
					var applyInvoice = 'Y';
					var sendFlag = writeoff.SENDEMAIL_Y;
					writeoff.statementEdit.statementConfirmBatch_SendMail(selections,sendFlag,applyInvoice);
				}else{
					var applyInvoice = 'N';
					var sendFlag = writeoff.SENDEMAIL_Y;
					writeoff.statementEdit.statementConfirmBatch_SendMail(selections,sendFlag,applyInvoice);
				}
			});
		}else{
			Ext.Msg.confirm(writeoff.statementEdit.i18n('foss.stl.writeoff.common.alert'),'是否需要申请发票',function(o){
				if(o=='yes'){
					var applyInvoice = 'Y';
					var sendFlag = writeoff.SENDEMAIL_N;
					writeoff.statementEdit.statementConfirmBatch_SendMail(selections,sendFlag,applyInvoice);
				}else{
					var applyInvoice = 'N';
					var sendFlag = writeoff.SENDEMAIL_N;
					writeoff.statementEdit.statementConfirmBatch_SendMail(selections,sendFlag,applyInvoice);
				}
			});
		}
	});
}

/** 
 * 批量确认对账单是否要发送邮件
 * 
 */
writeoff.statementEdit.statementConfirmBatch_SendMail=function(selections,sendFlag,applyInvoice){
	//定义批量更新对账单状态的number
	var statementOfAccountList = [];
	for(var i=0;i<selections.length;i++){
		if(selections[i].get('confirmStatus')==writeoff.STATEMENTCONFIRMSTATUS_Y){
			statementOfAccountList = [];
			Ext.Msg.alert(writeoff.statementEdit.i18n('foss.stl.writeoff.common.alert'),writeoff.statementEdit.i18n('foss.stl.writeoff.statementEdit.choiceStatement')+selections[i].get('statementBillNo')+writeoff.statementEdit.i18n('foss.stl.writeoff.statementEdit.confirmedToConfirmWarning'));
			return false;
		}
		statementOfAccountList.push(selections[i].data);
	}
	//拼接vo，注入到后台
	statementOfAccountMakeQueryResultVo = new Object();
	statementOfAccountMakeQueryResultDto = new Object();
	statementOfAccountMakeQueryResultDto.statementOfAccountList = statementOfAccountList;
	statementOfAccountMakeQueryResultDto.sendMailFlag = sendFlag;
	statementOfAccountMakeQueryResultVo.applyInvoice = applyInvoice;
	statementOfAccountMakeQueryResultVo.confirmStatus = writeoff.STATEMENTCONFIRMSTATUS_Y;
	statementOfAccountMakeQueryResultVo.statementOfAccountMakeQueryResultDto = statementOfAccountMakeQueryResultDto;
	Ext.Ajax.request({
		url:writeoff.realPath('confirmStatement.action'),
		jsonData:{
			'statementOfAccountMakeQueryResultVo':statementOfAccountMakeQueryResultVo
		},
		success:function(response){
			var grid = Ext.getCmp('T_writeoff-statementEdit_content').getStatementQueryGrid();
			grid.store.load();
			Ext.ux.Toast.msg(writeoff.statementEdit.i18n('foss.stl.writeoff.common.alert'), writeoff.statementEdit.i18n('foss.stl.writeoff.statementEdit.confirmSuccess'), 'ok', 1000);
		},
		exception:function(response){
			var result = Ext.decode(response.responseText);
			Ext.Msg.alert(writeoff.statementEdit.i18n('foss.stl.writeoff.common.alert'),result.message);
		}
	});
}

/** 
 * 打印对账单
 */
writeoff.statementEdit.statementPrint = function(){
	if(!writeoff.statementEdit.orgCodeInspect()){
		return false;
	}
	//获取选取行数
	var selections = this.up('panel').getSelectionModel().getSelection();
	var me = this;
	//判断是否选中1条
	if(selections.length!=1){
		Ext.Msg.alert(writeoff.statementEdit.i18n('foss.stl.writeoff.common.alert'),writeoff.statementEdit.i18n('foss.stl.writeoff.statementEdit.unSelectedToPrint'));
		return false;
	}
	//默认显示列 ddw
	var arrayColumns = ['businessDate','waybillNo','arrvRegionCode','productCode',
						'deliveryCustomerName','unitPrice','insuranceAmount','qty',
						'billWeight','billingVolume','insuranceFee','deliverFee',
						'packagingFee','otherFee','receiveMethod','paymentType',
						'unverifyAmount'];
	var targetObject = selections[0].copy().data;
	//拼接列头
	targetObject.arrayColumns = arrayColumns;
	targetObject.columnName1 = writeoff.statementEdit.i18n('foss.stl.writeoff.common.businessDate');
	targetObject.columnName2 = writeoff.statementEdit.i18n('foss.stl.writeoff.common.sourceBillNo');
	targetObject.columnName3 = writeoff.statementEdit.i18n('foss.stl.writeoff.statementAdd.arrvRegionCode');
	targetObject.columnName4 = writeoff.statementEdit.i18n('foss.stl.writeoff.statementAdd.productCode');
	targetObject.columnName5 = writeoff.statementEdit.i18n('foss.stl.writeoff.statementAdd.deliveryCustomerName');
	targetObject.columnName6 = writeoff.statementEdit.i18n('foss.stl.writeoff.statementAdd.unitPrice');
	targetObject.columnName7 = writeoff.statementEdit.i18n('foss.stl.writeoff.statementAdd.insuranceAmount');
	targetObject.columnName8 = writeoff.statementEdit.i18n('foss.stl.writeoff.statementAdd.qty');
	targetObject.columnName9 = writeoff.statementEdit.i18n('foss.stl.writeoff.statementAdd.billWeight');
	targetObject.columnName10 = writeoff.statementEdit.i18n('foss.stl.writeoff.statementAdd.billingVolume');
	targetObject.columnName11 = writeoff.statementEdit.i18n('foss.stl.writeoff.statementAdd.insuranceFee');
	targetObject.columnName12 = writeoff.statementEdit.i18n('foss.stl.writeoff.statementAdd.deliverFee');
	targetObject.columnName13 = writeoff.statementEdit.i18n('foss.stl.writeoff.statementAdd.packagingFee');
	targetObject.columnName14 = writeoff.statementEdit.i18n('foss.stl.writeoff.statementAdd.otherFee');
	targetObject.columnName15 = writeoff.statementEdit.i18n('foss.stl.writeoff.statementAdd.receiveMethod');
	targetObject.columnName16 = writeoff.statementEdit.i18n('foss.stl.writeoff.statementAdd.paymentType');
	targetObject.columnName17 = writeoff.statementEdit.i18n('foss.stl.writeoff.common.originalUnverifyAmount');
	me.disable(false);
	//10秒后自动解除灰掉效果
	setTimeout(function() {
		me.enable(true);
	}, 10000);
	do_printpreview('statementbill',targetObject,ContextPath.STL_WEB);
}
//邓大伟
writeoff.statementEdit.statementExportXLS = function(){
	var	columns,
	arrayColumns,
	arrayColumnNames,
	statementQueryGrid,
	statementNo,
	searchParams;
	statementQueryGrid = Ext.getCmp('T_writeoff-statementEdit_content').getStatementQueryGrid();
	var hiddenfield = Ext.getCmp('T_writeoff-statementEdit_content').getHiddenField();
	var queryTabType = hiddenfield.getForm().findField('queryTypeParam').getValue();
	var customerCode = hiddenfield.getForm().findField('customerCodeParam').getValue();
	var periodBeginDate = hiddenfield.getForm().findField('periodBeginDateParam').getValue();
	var periodEndDate = hiddenfield.getForm().findField('periodEndDateParam').getValue();
	var confirmStatus = hiddenfield.getForm().findField('confirmStatusParam').getValue();
	var settleStatus = hiddenfield.getForm().findField('settleStatusParam').getValue();
	var statementBillNumbers = hiddenfield.getForm().findField('statementBillNumbersParam').getValue();
	var numbers = hiddenfield.getForm().findField('numbersParam').getValue();
	var me = this;
	Ext.MessageBox.show({
		title: writeoff.statementEdit.i18n('foss.stl.writeoff.common.alert'),
		msg: writeoff.statementEdit.i18n('foss.stl.writeoff.statementEdit.export'),
		buttons: Ext.MessageBox.YESNO,
		fn: function(e){
		  	//如果本期数据为空，则提示不能导出excel
		  	if(statementQueryGrid.store.data.length==0){
		  		Ext.ux.Toast.msg(writeoff.statementEdit.i18n('foss.stl.writeoff.common.alert'), writeoff.statementEdit.i18n('foss.stl.writeoff.statementCommon.noDataToExport'), 'error', 1000);
				return false;
		  	}
		  	
		  	if(e=='yes'){
		  		//转化列头和列明
				columns = statementQueryGrid.columns;
				arrayColumns = [];
				arrayColumnNames = [];
				//声明单据父类型、单据子类型
				var billParentType,billType;
				//将前台对应列头传入到后台去
				for(var i=1;i<columns.length;i++){
					if(columns[i].isHidden()==false){
						var hederName = columns[i].text;
						var dataIndex = columns[i].dataIndex;
						if(columns[i].text!=writeoff.statementEdit.i18n('foss.stl.writeoff.statementAdd.actionColumn')){
							arrayColumns.push(dataIndex);
							arrayColumnNames.push(hederName);
						}
					}
				}
		
				if(!Ext.fly('downloadAttachFileForm')){
				    var frm = document.createElement('form');
				    frm.id = 'downloadAttachFileForm';
				    frm.style.display = 'none';
				    document.body.appendChild(frm);
				}
				
				//判断是按那种查询进行
				if(queryTabType==writeoff.STATEMENTQUERYTAB_BYCUSTOMER){
					searchParams = {
						'statementOfAccountMakeQueryVo.statementOfAccountMakeQueryDto.customerCode':customerCode,
						'statementOfAccountMakeQueryVo.statementOfAccountMakeQueryDto.periodBeginDate':periodBeginDate,
						'statementOfAccountMakeQueryVo.statementOfAccountMakeQueryDto.periodEndDate':periodEndDate,
						'statementOfAccountMakeQueryVo.statementOfAccountMakeQueryDto.confirmStatus':confirmStatus,
						'statementOfAccountMakeQueryVo.statementOfAccountMakeQueryDto.settleStatus':settleStatus,
						'statementOfAccountMakeQueryVo.statementOfAccountMakeQueryDto.statementOrgCode':writeoff.statementEdit.statementOrgCode,
						'statementOfAccountMakeQueryVo.statementOfAccountMakeQueryDto.largeRegion':writeoff.statementEdit.largeRegion,
						'statementOfAccountMakeQueryVo.statementOfAccountMakeQueryDto.smallRegion':writeoff.statementEdit.smallRegion,
						'statementOfAccountMakeQueryVo.statementOfAccountMakeQueryDto.queryPage':writeoff.STATEMENTQUERYTAB_BYCUSTOMER,
				    	'statementOfAccountMakeQueryResultVo.arrayColumns':arrayColumns,
				    	'statementOfAccountMakeQueryResultVo.arrayColumnNames':arrayColumnNames,
				    	'statementOfAccountMakeQueryResultVo.exportType':'byStatement'
					};
				}else if(queryTabType==writeoff.STATEMENTQUERYTAB_BYSTATEMENTNUMBER){
					//获取对账单号
					var billNumberArray = stl.splitToArray(statementBillNumbers);
					searchParams = {
						'statementOfAccountMakeQueryVo.statementOfAccountMakeQueryDto.billDetailNos':billNumberArray,
						'statementOfAccountMakeQueryVo.statementOfAccountMakeQueryDto.queryPage': writeoff.STATEMENTQUERYTAB_BYSTATEMENTNUMBER,
				    	'statementOfAccountMakeQueryResultVo.arrayColumns':arrayColumns,
				    	'statementOfAccountMakeQueryResultVo.arrayColumnNames':arrayColumnNames,
				    	'statementOfAccountMakeQueryResultVo.exportType':'byStatement'
					};
				}else if(queryTabType==writeoff.STATEMENTQUERYTABTAB_BYWAYNUMBER){
					//获取运单号、应收单号等
					var billNumberArray = stl.splitToArray(numbers);
					searchParams = {
						'statementOfAccountMakeQueryVo.statementOfAccountMakeQueryDto.billDetailNos':billNumberArray,
						'statementOfAccountMakeQueryVo.statementOfAccountMakeQueryDto.queryPage': writeoff.STATEMENTQUERYTABTAB_BYWAYNUMBER,
				    	'statementOfAccountMakeQueryResultVo.arrayColumns':arrayColumns,
				    	'statementOfAccountMakeQueryResultVo.arrayColumnNames':arrayColumnNames,
				    	'statementOfAccountMakeQueryResultVo.exportType':'byStatement'
					};
				}else{
					searchParams = {
							'statementOfAccountMakeQueryVo.statementOfAccountMakeQueryDto.statementOrgCode':writeoff.statementEdit.failingInvoiceOrgCode,
							'statementOfAccountMakeQueryVo.statementOfAccountMakeQueryDto.queryPage':writeoff.STATEMENTQUERYTAB_FAILINGINVOICE,
					    	'statementOfAccountMakeQueryResultVo.arrayColumns':arrayColumns,
					    	'statementOfAccountMakeQueryResultVo.arrayColumnNames':arrayColumnNames,
					    	'statementOfAccountMakeQueryResultVo.exportType':'byStatement'
						};
				}
				
				//拼接vo，注入到后台
				Ext.Ajax.request({
				    url: writeoff.realPath('exportStatementXLS.action'),
				    form: Ext.fly('downloadAttachFileForm'),
				    method : 'POST',
				    params : searchParams,
				    isUpload: true,
				    success : function(response,options){
						Ext.ux.Toast.msg(writeoff.statementEdit.i18n('foss.stl.writeoff.common.alert'),writeoff.statementEdit.i18n('foss.stl.writeoff.statementEdit.exportSuccess'), 'success', 1000);
				    }
				});
		  	}else{
		  		return false;
		  	}
		}
	});
}
/**
 * 导出PDF
 */
writeoff.statementEdit.statementExportPDF = function(){
	var selections,
		arrayColumns,
		arrayColumnNames,
		me;
	//获取选取行数
	selections = this.up('panel').getSelectionModel().getSelection();
	me = this;
	//判断是否选中1条
	if(selections.length!=1){
		Ext.Msg.alert(writeoff.statementEdit.i18n('foss.stl.writeoff.common.alert'),writeoff.statementEdit.i18n('foss.stl.writeoff.statementEdit.unSelectedToExport'));
		return false;
	}
	if(!Ext.fly('downloadAttachFileForm')){
	    var frm = document.createElement('form');
	    frm.id = 'downloadAttachFileForm';
	    frm.style.display = 'none';
	    document.body.appendChild(frm);
	}
	
	
	//默认显示列 ddw
	var arrayColumns = ['businessDate','waybillNo','arrvRegionCode','productCode',
						'deliveryCustomerName','unitPrice','insuranceAmount','qty',
						'billWeight','billingVolume','insuranceFee','deliverFee',
						'packagingFee','otherFee','receiveMethod','paymentType',
						'unverifyAmount'];

	//默认显示列名称
	arrayColumnNames = [writeoff.statementEdit.i18n('foss.stl.writeoff.common.businessDate'),
						writeoff.statementEdit.i18n('foss.stl.writeoff.common.sourceBillNo'),
						writeoff.statementEdit.i18n('foss.stl.writeoff.statementAdd.arrvRegionCode'),
						writeoff.statementEdit.i18n('foss.stl.writeoff.statementAdd.productCode'),
						writeoff.statementEdit.i18n('foss.stl.writeoff.statementAdd.deliveryCustomerName'),
						writeoff.statementEdit.i18n('foss.stl.writeoff.statementAdd.unitPrice'),
						writeoff.statementEdit.i18n('foss.stl.writeoff.statementAdd.insuranceAmount'),
						writeoff.statementEdit.i18n('foss.stl.writeoff.statementAdd.qty'),
						writeoff.statementEdit.i18n('foss.stl.writeoff.statementAdd.billWeight'),
						writeoff.statementEdit.i18n('foss.stl.writeoff.statementAdd.billingVolume'),
						writeoff.statementEdit.i18n('foss.stl.writeoff.statementAdd.insuranceFee'),
						writeoff.statementEdit.i18n('foss.stl.writeoff.statementAdd.deliverFee'),
						writeoff.statementEdit.i18n('foss.stl.writeoff.statementAdd.packagingFee'),
						writeoff.statementEdit.i18n('foss.stl.writeoff.statementAdd.otherFee'),
						writeoff.statementEdit.i18n('foss.stl.writeoff.statementAdd.receiveMethod'),
						writeoff.statementEdit.i18n('foss.stl.writeoff.statementAdd.paymentType'),
						writeoff.statementEdit.i18n('foss.stl.writeoff.common.originalUnverifyAmount')];							
	me.disable(false);
	//10秒后自动解除灰掉效果
	setTimeout(function() {
		me.enable(true);
	}, 10000);	
	//拼接vo，注入到后台
	Ext.Ajax.request({
	    url:writeoff.realPath('exportStatement.action'),
	    form: Ext.fly('downloadAttachFileForm'),
	    method : 'POST',
	    params : {
	    	'statementOfAccountMakeQueryResultVo.arrayColumns':arrayColumns,
	    	'statementOfAccountMakeQueryResultVo.arrayColumnNames':arrayColumnNames,
	    	'statementOfAccountMakeQueryResultVo.exportType':'byStatement',
			'statementOfAccountMakeQueryResultVo.statementofAccountStr':Ext.JSON.encode(selections[0].data)
	    },
	    isUpload: true,
	    success : function(response,options){
			Ext.Msg.alert(writeoff.statementEdit.i18n('foss.stl.writeoff.statementEdit.exportSuccess'));
	    },
	    failure : function(response,options){
			Ext.Msg.alert(writeoff.statementEdit.i18n('foss.stl.writeoff.statementEdit.exportFailed'));
	    }
	});
}
				
/**
 * 结账状态store
 */
Ext.define('Foss.statementbill.SettleStatusStore',{
	extend:'Ext.data.Store',
	model:'Foss.statementbill.queryComboModel',
	data:{
		'items':[
				{name:writeoff.statementEdit.i18n('foss.stl.writeoff.statementEdit.all'),value:'ALL'},
				{name:writeoff.statementEdit.i18n('foss.stl.writeoff.statementEdit.settled'),value:writeoff.SETTLESTATUS_Y},
				{name:writeoff.statementEdit.i18n('foss.stl.writeoff.statementEdit.unSettle'),value:writeoff.SETTLESTATUS_N}
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
//ddw
Ext.define('Foss.statementbill.ApplyInvoiceStore',{
	extend:'Ext.data.Store',
	model:'Foss.statementbill.queryComboModel',
	data:{
		'items':[
				{
					name:writeoff.statementEdit.i18n('foss.stl.writeoff.statementEdit.all'),
					value:''
				},
				{
					name:writeoff.statementEdit.i18n('foss.stl.writeoff.statementEdit.ApplyInvoiceYes'),
					value:writeoff.SETTLEMENTINVOICEAPPLYINVOICE_Y
				},
				{
					name:writeoff.statementEdit.i18n('foss.stl.writeoff.statementEdit.ApplyInvoiceNo'),
					value:writeoff.SETTLEMENTINVOICEAPPLYINVOICE_N
				}
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
//ddw
Ext.define('Foss.statementbill.InvoiceStatusStore',{
	extend:'Ext.data.Store',
	model:'Foss.statementbill.queryComboModel',
	data:{
		'items':[
				{
					name:writeoff.statementEdit.i18n('foss.stl.writeoff.statementEdit.all'),
					value:''
				},
				{
					name:writeoff.statementEdit.i18n('foss.stl.writeoff.statementEdit.applied'),
					value:writeoff.STATEMENTINVOICESTATUS_Y
				},
				{
					name:writeoff.statementEdit.i18n('foss.stl.writeoff.statementEdit.notApply'),
					value:writeoff.STATEMENTINVOICESTATUS_N
				}
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

/**
 * 对账单表格store
 */
Ext.define('Foss.statementbill.StatementQueryGridStore', {
	extend:'Ext.data.Store',
    model:'Foss.statementbill.StatementFormModel',
    pageSize:50,
    proxy:{
		type:'ajax',
		url:writeoff.realPath('queryStatement.action'),
		actionMethods:'post',
		reader:{
			type:'json',
			root:'statementOfAccountMakeQueryResultVo.statementOfAccountMakeQueryResultDto.statementOfAccountList',
			totalProperty:'totalCount'
		}
	},
	listeners:{
		'beforeLoad':function(store, operation, eOpts){
			var searchParams;
			var hiddenfield = Ext.getCmp('T_writeoff-statementEdit_content').getHiddenField();
			
			//获取隐藏域的值
			var queryTabType = hiddenfield.getForm().findField('queryTypeParam').getValue();
			var customerCode = hiddenfield.getForm().findField('customerCodeParam').getValue();
			var periodBeginDate = hiddenfield.getForm().findField('periodBeginDateParam').getValue();
			var periodEndDate = hiddenfield.getForm().findField('periodEndDateParam').getValue();
			var confirmStatus = hiddenfield.getForm().findField('confirmStatusParam').getValue();
			var settleStatus = hiddenfield.getForm().findField('settleStatusParam').getValue();
			var statementBillNumbers = hiddenfield.getForm().findField('statementBillNumbersParam').getValue();
			var numbers = hiddenfield.getForm().findField('numbersParam').getValue();
			
			//判断是按那种查询进行
			if(queryTabType==writeoff.STATEMENTQUERYTAB_BYCUSTOMER){
				searchParams = {
					'statementOfAccountMakeQueryVo.statementOfAccountMakeQueryDto.customerCode':customerCode,
					'statementOfAccountMakeQueryVo.statementOfAccountMakeQueryDto.periodBeginDate':periodBeginDate,
					'statementOfAccountMakeQueryVo.statementOfAccountMakeQueryDto.periodEndDate':periodEndDate,
					'statementOfAccountMakeQueryVo.statementOfAccountMakeQueryDto.confirmStatus':confirmStatus,
					'statementOfAccountMakeQueryVo.statementOfAccountMakeQueryDto.settleStatus':settleStatus,
					//邓大伟
					'statementOfAccountMakeQueryVo.statementOfAccountMakeQueryDto.statementOrgCode':writeoff.statementEdit.statementOrgCode,
					'statementOfAccountMakeQueryVo.statementOfAccountMakeQueryDto.largeRegion':writeoff.statementEdit.largeRegion,
					'statementOfAccountMakeQueryVo.statementOfAccountMakeQueryDto.smallRegion':writeoff.statementEdit.smallRegion,
					'statementOfAccountMakeQueryVo.statementOfAccountMakeQueryDto.applyInvoice':writeoff.statementEdit.applyInvoice,
					'statementOfAccountMakeQueryVo.statementOfAccountMakeQueryDto.invoiceStatus':writeoff.statementEdit.invoiceStatus,
					'statementOfAccountMakeQueryVo.statementOfAccountMakeQueryDto.queryPage':writeoff.STATEMENTQUERYTAB_BYCUSTOMER																				
				};
			}else if(queryTabType==writeoff.STATEMENTQUERYTAB_BYSTATEMENTNUMBER){
				//获取对账单号
				var billNumberArray = stl.splitToArray(statementBillNumbers);
				searchParams = {
					'statementOfAccountMakeQueryVo.statementOfAccountMakeQueryDto.billDetailNos':billNumberArray,
					'statementOfAccountMakeQueryVo.statementOfAccountMakeQueryDto.queryPage': writeoff.STATEMENTQUERYTAB_BYSTATEMENTNUMBER																		
				};
			}else if(queryTabType==writeoff.STATEMENTQUERYTABTAB_BYWAYNUMBER){
				//获取运单号、应收单号等
				var billNumberArray = stl.splitToArray(numbers);
				searchParams = {
					'statementOfAccountMakeQueryVo.statementOfAccountMakeQueryDto.billDetailNos':billNumberArray,
					'statementOfAccountMakeQueryVo.statementOfAccountMakeQueryDto.queryPage': writeoff.STATEMENTQUERYTABTAB_BYWAYNUMBER																		
				};
			}else{
				searchParams = {
						'statementOfAccountMakeQueryVo.statementOfAccountMakeQueryDto.statementOrgCode':writeoff.statementEdit.failingInvoiceOrgCode,
						'statementOfAccountMakeQueryVo.statementOfAccountMakeQueryDto.queryPage':writeoff.STATEMENTQUERYTAB_FAILINGINVOICE																				
					};
			}
			
			Ext.apply(operation,{
				params :searchParams
			});  
		}
	}
});

/**
 * 查询tab
 */
Ext.define('Foss.statementbill.StatementQueryInfoTab',{
	extend:'Ext.tab.Panel',
	frame:false,
	bodyCls: 'autoHeight',
	cls: 'innerTabPanel',
//	activeTab: 0,
	height:250,
    items: [{
       	title: writeoff.statementEdit.i18n('foss.stl.writeoff.statementEdit.queryByCustomer'),
       	tabConfig: {
			width: 120
		},
        layout:'hbox',
	    items:[{
        	xtype:'form',
        	width:920,
        	layout:'column',
        	defaults:{
	        	margin:'10 10 0 10',
	        	labelWidth:80
       		 },
		    items:[{
		    	xtype:'combobox',
		    	name:'billType',
		    	fieldLabel:writeoff.statementEdit.i18n('foss.stl.writeoff.common.billType'),
				store:FossDataDictionary.getDataDictionaryStore(settlementDict.STATEMENT_OF_ACCOUNT__BILL_TYPE), 
				queryModel:'local',
				displayField:'valueName',
				valueField:'valueCode',
				hidden:true,
				value:writeoff.STATEMENTTYPE_CUSTOMER,
				readOnly:true,
		    	columnWidth:.3
		    },{//邓大伟
				xtype:'linkagecomboselector',
				eventType : ['focus'],// 一般callparent包含focus事件，如果有callparent,则focus事件可不用传递
				itemId : 'Foss_baseinfo_BigRegion_ID',
				store : Ext.create('Foss.baseinfo.commonSelector.BigRegionStore'),// 从外面传入
				columnWidth:.3,
				fieldLabel : writeoff.statementEdit.i18n('foss.stl.writeoff.common.largeRegion'),
				name : 'largeRegion',
				isPaging: true,
				allowBlank : true,
				value:'',
				minChars : 0,
				displayField : 'name',// 显示名称
				valueField : 'code',
				minChars : 0,
				queryParam : 'commonOrgVo.name'
			},{
				xtype:'linkagecomboselector',
				itemId : 'Foss_baseinfo_SmallRegion_ID',
				eventType : ['callparent'],// 一般callparent包含focus事件，如果有callparent,则focus事件可不用传递
				store : Ext.create('Foss.baseinfo.commonSelector.SmallRegionStore'),// 从外面传入
				columnWidth:.3,
				fieldLabel : writeoff.statementEdit.i18n('foss.stl.writeoff.common.smallRegion'),
				name : 'smallRegion',
				allowBlank : true,
				isPaging: true,
				parentParamsAndItemIds : {
					'commonOrgVo.code' : 'Foss_baseinfo_BigRegion_ID'
				},// 此处城市不需要传入
				minChars : 0,
				displayField : 'name',// 显示名称
				valueField : 'code',
				minChars : 0,
				queryParam : 'commonOrgVo.name'
			},{
				xtype : 'dynamicorgcombselector',
				fieldLabel : writeoff.statementEdit.i18n('foss.stl.writeoff.common.statementOrgName'),
				name : 'statementOrgCode',
				value : stl.currentDept.name,
				columnWidth:.3,
				labelWidth : 85,
				listWidth : 300,// 设置下拉框宽度
				isPaging : true
			},{
	        	xtype:'datefield',
	        	fieldLabel:writeoff.statementEdit.i18n('foss.stl.writeoff.statementEdit.periodBeginDate'),
	        	allowBlank:false,
	        	name:'periodBeginDate',
	        	columnWidth: .3,
	        	format:'Y-m-d',
	        	value:stl.getTargetDate(new Date(),-1)
	        },{
	        	xtype:'datefield',
	        	fieldLabel:writeoff.statementEdit.i18n('foss.stl.writeoff.statementAdd.to'),
	        	labelWidth:80,
	        	name:'periodEndDate',
	        	format:'Y-m-d',
	        	columnWidth: .3,
	        	value:new Date()
	        },{//ddw
				xtype: 'combobox',
				name:'applyInvoice',
		        fieldLabel: writeoff.statementEdit.i18n('foss.stl.writeoff.statementEdit.applyInvoice'),
				store:Ext.create('Foss.statementbill.ApplyInvoiceStore'),
				queryModel:'local',
				displayField:'name',
				valueField:'value',
				value : '',
				labelWidth : 85,
		        columnWidth:.3,
		        forceSelection :true,
		        listeners:{
		        	change:stl.comboSelsct
		        }
		    },{
		    	xtype:'combobox',
		    	name:'customerType',
		    	columnWidth:.3,
		    	fieldLabel:writeoff.statementEdit.i18n('foss.stl.writeoff.statementCommon.customerType'),
				store:Ext.create('Foss.statementbill.CustomerTypeStore'),
				editable:false,
				queryModel:'local',
				value:'01',
				displayField:'customerTypeName',
				valueField:'customerTypeCode',
		    	listeners:{
					'change': function(th,newValue,oldValue){
						//声明变量
						var form,customerCode,vehAgencyCode,airAgenyCode,airlineName;
						//获取表单
						form= th.up('form').getForm();
						customerCode = form.findField('customerCode');
						vehAgencyCode = form.findField('vehagencycomp');
						airAgenyCode = form.findField('airagency');
						airlineName = form.findField('airline');
						landStowage= form.findField('landStowage');
						//如果为全部，则默认客户不可编辑
						if(newValue=='01'){
							customerCode.show();
							customerCode.setReadOnly(false);
							vehAgencyCode.setReadOnly(true);
							airAgenyCode.setReadOnly(true);
							airlineName.setReadOnly(true);
							vehAgencyCode.hide();
							airAgenyCode.hide();
							airlineName.hide();
							landStowage.hide();
						}else if(newValue=='02'){
							vehAgencyCode.show();
							vehAgencyCode.setReadOnly(false);
							customerCode.setReadOnly(true);
							airAgenyCode.setReadOnly(true);
							airlineName.setReadOnly(true);
							customerCode.hide();
							airAgenyCode.hide();
							airlineName.hide();
							landStowage.hide();
						}else if(newValue=='03'){
							airAgenyCode.show();
							airAgenyCode.setReadOnly(false);
							vehAgencyCode.setReadOnly(true);
							customerCode.setReadOnly(true);
							airlineName.setReadOnly(true);
							customerCode.hide();
							vehAgencyCode.hide();
							airlineName.hide();
							landStowage.hide();
						}else if(newValue=='04'){
							airlineName.show();
							airlineName.setReadOnly(false);
							vehAgencyCode.setReadOnly(true);
							airAgenyCode.setReadOnly(true);
							customerCode.setReadOnly(true);
							customerCode.hide();
							airAgenyCode.hide();
							vehAgencyCode.hide();
							landStowage.hide();
						}else if(newValue=='05'){
							landStowage.show();
							airlineName.hide();
							customerCode.hide();
							airAgenyCode.hide();
							vehAgencyCode.hide();
						}
					}
				}
		    },{
	        	xtype:'commonairagentselector',
	        	name:'airagency',
	        	all:'true',//查询所有(包含作废)
	        	hidden:true,
	        	fieldLabel:writeoff.statementEdit.i18n('foss.stl.writeoff.common.airAgencyDetial'),
	        	columnWidth:.3
	        },{
	        	xtype:'commonairlinesselector',
	        	all:'true',//查询所有(包含作废)
	        	name:'airline',
	        	hidden:true,
	        	fieldLabel:writeoff.statementEdit.i18n('foss.stl.writeoff.common.airDetial'),
	        	columnWidth:.3
	        },{
	        	xtype:'commonvehagencycompselector',
	        	all:'true',//查询所有(包含作废)
	        	name:'vehagencycomp',
	        	hidden:true,
	        	fieldLabel:writeoff.statementEdit.i18n('foss.stl.writeoff.common.vehAgencyDetial'),
	        	columnWidth:.3
	        },{
	        	xtype:'commoncustomerselector',
	        	listWidth:300,
	        	emptyText:'客户编码和手机均可查询',
	        	name:'customerCode',
	        	all:'true',//查询所有(包含作废)
	        	contcatFlag :'Y',//增加按手机号查询
    			singlePeopleFlag :'Y',
	        	fieldLabel:writeoff.statementEdit.i18n('foss.stl.writeoff.common.customerDetial'),
	        	columnWidth:.3
	        },{
	        	xtype:'commonLdpAgencyCompanySelector',
	        	listWidth:300,
	        	name:'landStowage',
	        	hidden:true,
	        	fieldLabel:writeoff.statementEdit.i18n('foss.stl.writeoff.common.landStowage'),
	        	columnWidth:.3
	        },{//ddw
				xtype: 'combobox',
				name:'invoiceStatus',
		        fieldLabel: writeoff.statementEdit.i18n('foss.stl.writeoff.statementEdit.invoiceStatus'),
				store:Ext.create('Foss.statementbill.InvoiceStatusStore'),
				queryModel:'local',
				displayField:'name',
				valueField:'value',
				value : '',
				labelWidth : 85,
		        columnWidth:.3,
		        forceSelection :true,
		        listeners:{
		        	change:stl.comboSelsct
		        }
		    },{
		   	 	xtype: 'combobox',
				name:'confirmStatus',
		        fieldLabel: writeoff.statementEdit.i18n('foss.stl.writeoff.statementEdit.statementStatus'),
				store:FossDataDictionary.getDataDictionaryStore(settlementDict.STATEMENT_OF_ACCOUNT__CONFIRM_STATUS,null,{
					 'valueCode': null,
               		 'valueName': writeoff.statementEdit.i18n('foss.stl.writeoff.statementEdit.all')
				}), 
				queryModel:'local',
				displayField:'valueName',
				valueField:'valueCode',
		        columnWidth:.3,
		        forceSelection :true,
		        listeners:{
		        	change:stl.comboSelsct
		        }
		    },{
				xtype: 'combobox',
				name:'settleStatus',
		        fieldLabel: writeoff.statementEdit.i18n('foss.stl.writeoff.statementEdit.settleStatus'),
				store:Ext.create('Foss.statementbill.SettleStatusStore'),
				queryModel:'local',
				displayField:'name',
				valueField:'value',
		        columnWidth:.3,
		        forceSelection :true,
		        listeners:{
		        	change:stl.comboSelsct
		        }
		    },{
				border: 1,
				xtype:'container',
				columnWidth:1,
				defaultType:'button',
				layout:'column',
				items:[{
					text:writeoff.statementEdit.i18n('foss.stl.writeoff.common.reset'),
					columnWidth:.08,
					handler:function(){
						this.up('form').getForm().reset();
						var dept = this.up('form').getForm().findField("statementOrgCode");
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
				},{
					xtype: 'container',
					border : false,
					columnWidth:.44,
					html: '&nbsp;'
				},{
					text:writeoff.statementEdit.i18n('foss.stl.writeoff.common.query'),
					cls:'yellow_button',
					columnWidth:.08,
					handler:writeoff.statementEdit.statementSelectByCust
				}]
		    }]
 		}]
    },{
        title: writeoff.statementEdit.i18n('foss.stl.writeoff.statementEdit.queryByStatement'),
        tabConfig:{
        	width:120
        },
        layout:'fit',
        items:[{
        	xtype:'form',
        	layout:'column',
        	defaults:{
        		margin:'5 5 5 5'
       		},
	       	items:[{       		
	    		xtype:'textareafield',
	    		fieldLabel:'<span style="color:red;">*</span>'+writeoff.statementEdit.i18n('foss.stl.writeoff.statementAdd.statementBillNo'),
	    		columnWidth:.65,
	    		labelWidth:70,
	    		labelAlign:'right',
	    		name:'statementBillNumbers',
	    		regex:/^(DZ[0-9]{7,10}[;,])*(DZ[0-9]{7,10}[;,]?)$/i,
				regexText:writeoff.statementEdit.i18n('foss.stl.writeoff.statementEdit.queryByStatementRegex'),
	    		autoScroll:true,
	    		height:104
	        },{
        		xtype:'container',
				columnWidth:1,
				layout:'column',
				defaultType:'button',
				defaults:{
					width:80
				},
				items:[{
					text:writeoff.statementEdit.i18n('foss.stl.writeoff.common.reset'),
					columnWidth:.075,
					handler:writeoff.statementReset
				},{
					xtype:'container',
					border:false,
					html:'&nbsp;',
					columnWidth:.5
				},{
					text:writeoff.statementEdit.i18n('foss.stl.writeoff.common.query'),
					cls:'yellow_button',
					columnWidth:.075,
					handler:writeoff.statementEdit.statementSelectByStatementNumber
				}]
        	}]
        }]
    },{
        title: writeoff.statementEdit.i18n('foss.stl.writeoff.statementEdit.queryByWaybillNo'),
        tabConfig:{
   			width:120
        },
        layout:'fit',
        items:[{
        	xtype:'form',
        	layout:'column',
        	defaults:{
        		margin:'5 5 5 5'
       		},
		    items:[{       		
				xtype:'textareafield',
				fieldLabel:'<span style="color:red;">*</span>'+writeoff.statementEdit.i18n('foss.stl.writeoff.statementAdd.number'),
				columnWidth:.65,
				regex:/^((YS|YF|US|UF)?[0-9]{7,14}[;,])*((YS|YF|US|UF)?[0-9]{7,14}[;,]?)$/i,
				regexText:writeoff.statementEdit.i18n('foss.stl.writeoff.statementAdd.billNosQueryRegexText'),
				labelWidth:70,
				labelAlign:'right',
				name:'numbers',
				autoScroll:true,
				height:104
		    },{
				xtype:'container',
				columnWidth:.35,
				layout:'vbox',
				items:[{
					xtype:'component',
					padding:'0 0 0 10',
					autoEl:{
						tag:'div',
						html:'<span style="color:red;">'+writeoff.statementEdit.i18n('foss.stl.writeoff.statementAdd.queryNosDesc')+'</span>'
					}
				}]
		    },{
        		xtype:'container',
				columnWidth:1,
				layout:'column',
				defaultType:'button',
				defaults:{
					width:80
				},
				items:[{
					text:writeoff.statementEdit.i18n('foss.stl.writeoff.common.reset'),
					columnWidth:.075,
					handler:writeoff.statementReset
				},{
					xtype:'container',
					border:false,
					html:'&nbsp;',
					columnWidth:.5
				},{
					text:writeoff.statementEdit.i18n('foss.stl.writeoff.common.query'),
					cls:'yellow_button',
					columnWidth:.075,
					handler:writeoff.statementEdit.statementSelectByNumber
				}]
        	}]
        }]
    },{
       	title: writeoff.statementEdit.i18n('foss.stl.writeoff.common.failingInvoiceOrgName'),
       	tabConfig: {
			width: 120
		},
        layout:'hbox',
	    items:[{
        	xtype:'form',
        	width:920,
        	layout:'column',
        	defaults:{
	        	margin:'10 10 0 10',
	        	labelWidth:80
       		 },
		    items:[{
				xtype : 'dynamicorgcombselector',
				fieldLabel : writeoff.statementEdit.i18n('foss.stl.writeoff.common.failingInvoiceOrgName'),
				name : 'failingInvoiceOrgCode',
				value : stl.currentDept.name,
				columnWidth:.3,
				labelWidth : 85,
				listWidth : 300,// 设置下拉框宽度
				isPaging : true
			},{
				border: 1,
				xtype:'container',
				columnWidth:1,
				defaultType:'button',
				layout:'column',
				items:[{
					text:writeoff.statementEdit.i18n('foss.stl.writeoff.common.reset'),
					columnWidth:.08,
					handler:function(){
						this.up('form').getForm().reset();
						var failingInvoiceDept = this.up('form').getForm().findField("failingInvoiceOrgCode");
						if(!Ext.isEmpty(stl.currentDept.code)){
							var displayText = stl.currentDept.name
							var valueText = stl.currentDept.code;
							var store = failingInvoiceDept.store;
							var  key = failingInvoiceDept.displayField + '';
							var value = failingInvoiceDept.valueField+ '';
							var m = Ext.create(store.model.modelName);
							m.set(key, displayText);
							m.set(value, valueText);
							store.loadRecords([m]);
							failingInvoiceDept.setValue(valueText);
						}
					}
				},{
					xtype: 'container',
					border : false,
					columnWidth:.44,
					html: '&nbsp;'
				},{
					text:writeoff.statementEdit.i18n('foss.stl.writeoff.common.query'),
					cls:'yellow_button',
					columnWidth:.08,
					handler:function(){
						var form = this.up('form').getForm();
						writeoff.statementEdit.statementFailingInvoice(form);
					}
				}]
		    }]
 		}]
    }]
});

Ext.define('Foss.statementbill.QueryEditParamsHiddenfield',{
	extend:'Ext.form.Panel',
	//hidden:true,
	layout:'column',
	defaults:{
		readOnly:true
	},
	items:[{
		xtype:'textfield',
		name:'billTypeParam',
		fieldLabel:writeoff.statementEdit.i18n('foss.stl.writeoff.common.billType'),
		columnWidth:.3
    },{
    	xtype:'datefield',
    	fieldLabel:'periodBeginDateParam',
    	allowBlank:false,
    	name:'periodBeginDateParam',
    	columnWidth: .3,
    	format:'Y-m-d'
    },{
    	xtype:'datefield',
    	fieldLabel:'periodEndDateParam',
    	labelWidth:30,
    	name:'periodEndDateParam',
    	format:'Y-m-d',
    	columnWidth: .24
    },{  
        xtype:'textfield',
		fieldLabel:'customerCodeParam',
		name:'customerCodeParam',
		columnWidth:.3
    },{
   	 	xtype: 'textfield',
		name:'confirmStatusParam',
        fieldLabel: writeoff.statementEdit.i18n('foss.stl.writeoff.statementEdit.statementStatus'),
        columnWidth:.27
    },{
		xtype: 'textfield',
		name:'settleStatusParam',
        fieldLabel: writeoff.statementEdit.i18n('foss.stl.writeoff.statementEdit.settleStatus'),
        columnWidth:.27
    },{       		
		xtype:'textareafield',
		fieldLabel:'statementBillNumbersParam',
		columnWidth:.5,
		labelWidth:70,
		labelAlign:'right',
		name:'statementBillNumbersParam',
		height:95
    },{       		
		xtype:'textareafield',
		fieldLabel:writeoff.statementEdit.i18n('foss.stl.writeoff.statementAdd.number'),
		columnWidth:.5,
		labelWidth:70,
		labelAlign:'right',
		name:'numbersParam',
		height:95
    },{
    	xtype:'textfield',
    	fieldLabel:'queryTypeParam',
    	columnWidth:.2,
    	name:'queryTypeParam'
    }]
});

/**
 * 对账单表格操作列store
 */
Ext.define('Foss.statementbill.operateColumnStore',{
	extened:'Ext.data.Store',
	model:'Foss.statementbill.queryComboModel',
	data:{
		'items':[
				{name:writeoff.statementEdit.i18n('foss.stl.writeoff.statementEdit.unConfirm'),value:'1'},
				{name:writeoff.statementEdit.i18n('foss.stl.writeoff.statementEdit.printReceipt'),value:'2'},
				{name:writeoff.statementEdit.i18n('foss.stl.writeoff.statementEdit.confirm'),value:'3'},
				{name:writeoff.statementEdit.i18n('foss.stl.writeoff.statementEdit.repayment'),value:'4'},
				{name:writeoff.statementEdit.i18n('foss.stl.writeoff.statementEdit.entry'),value:'5'},
				{name:writeoff.statementEdit.i18n('foss.stl.writeoff.statementEdit.verify'),value:'6'},
				{name:writeoff.statementEdit.i18n('foss.stl.writeoff.statementEdit.applied'),value:'7'},
				{name:writeoff.statementEdit.i18n('foss.stl.writeoff.statementEdit.notApply'),value:'8'}
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

/**
 * 对账单操作列的下拉框
 */
Ext.define('Foss.statementbill.operateColumn', {
	extend:'Ext.form.field.ComboBox',
	typeAhead: true,
	emptyText:'操作列',
	repaymentBillWindow:Ext.create('Foss.statementbill.RepaymentBillWindow'),
    triggerAction: 'all',
    queryMode: 'local',
    store: Ext.create('Foss.statementbill.operateColumnStore'),
    valueField: 'value',
    displayField: 'name',
	forceSelection :true,
	listeners:{
		'change':function(combo){
			if(Ext.isEmpty(combo.getValue())){
				combo.setValue("");
			}		
		},//改方法为了根据不同状态显示不同下拉
		'expand':function(field){
			var selection=Ext.getCmp('T_writeoff-statementEdit_content').getStatementQueryGrid().getSelectionModel().getSelection()[0];
			
			this.store.removeAll();
			this.store.add({name:writeoff.statementEdit.i18n('foss.stl.writeoff.statementEdit.unConfirm'),value:'1'},
    				{name:writeoff.statementEdit.i18n('foss.stl.writeoff.statementEdit.printReceipt'),value:'2'},
    				{name:writeoff.statementEdit.i18n('foss.stl.writeoff.statementEdit.confirm'),value:'3'},
    				{name:writeoff.statementEdit.i18n('foss.stl.writeoff.statementEdit.repayment'),value:'4'},
    				{name:writeoff.statementEdit.i18n('foss.stl.writeoff.statementEdit.entry'),value:'5'},
    				{name:writeoff.statementEdit.i18n('foss.stl.writeoff.statementEdit.verify'),value:'6'},
    				{name:writeoff.statementEdit.i18n('foss.stl.writeoff.statementEdit.applied'),value:'7'},
    				{name:writeoff.statementEdit.i18n('foss.stl.writeoff.statementEdit.notApply'),value:'8'});
			//反确认record
			var unConfirmRecord = this.store.getAt(0);
			//确认record
			var confirmReocrd = this.store.getAt(2);
			//还款record
			var recRecord = this.store.getAt(3);
			//核销record
			var verifyRecord = this.store.getAt(5);
			//已申请
			var applied = this.store.getAt(6);
			//未申请
			var notApply = this.store.getAt(7);
			
			//查看是对账单权限   --确认/反确认
			if(!writeoff.statementEdit.isPermission('/stl-web/writeoff/confirmStatement.action')){
				//移除确认、反确认按钮
				this.store.remove([confirmReocrd,unConfirmRecord]);
			}
			//查看对账单权限  --还款
			if(!writeoff.statementEdit.isPermission('/stl-web/writeoff/repayment.action')){
				//移除确认、反确认按钮
				this.store.remove(recRecord);
			}
			//查看对账单权限  --核销
			if(!writeoff.statementEdit.isPermission('/stl-web/writeoff/batchWriteoffStatement.action')){
				//移除确认、反确认按钮
				this.store.remove(verifyRecord);
			}
			if(!writeoff.statementEdit.isPermission('/stl-web/writeoff/applied.action')){
				//移除已申请按钮
				this.store.remove(applied);
			}
			if(!writeoff.statementEdit.isPermission('/stl-web/writeoff/notApply.action')){
				//移除未申请按钮
				this.store.remove(notApply);
			}
			if(!Ext.isEmpty(selection.get('invoiceStatus'))){
				//已申请
				if(selection.get('invoiceStatus')==writeoff.STATEMENTINVOICESTATUS_Y){
					//如果为确认，则移除反确认按钮
					this.store.remove(applied);	
				}else{
					//如果为确认，则移除反确认按钮
					this.store.remove(notApply);	
				}
			}
			if(!Ext.isEmpty(selection.get('confirmStatus'))){
				//确认
				if(selection.get('confirmStatus')==writeoff.STATEMENTCONFIRMSTATUS_Y){
					//如果为确认，则移除反确认按钮
					this.store.remove(confirmReocrd);	
				}else{
					//如果为确认，则移除反确认按钮
					this.store.remove(unConfirmRecord);	
				}
			}
			
			//如果本次未还金额等于0，则
			if(!Ext.isEmpty(selection.get('unpaidAmount'))){
				if(selection.get('unpaidAmount')==0){
					//如果本次未还金额等于0，且结账次数大于0，则移除还款和核销按钮
					if(!Ext.isEmpty(selection.get('settleNum')) && selection.get('settleNum')>0){
						//移除核销按钮
						this.store.remove(verifyRecord);
						//移除还款按钮
						this.store.remove(recRecord);
					}else{
						//移除还款按钮
						this.store.remove(recRecord);
					}
				}else if(selection.get('unpaidAmount')>0){
					//如果为确认，则移除反确认按钮
					this.store.remove(verifyRecord);	
				}else {
					//如果为确认，则移除反确认按钮
					this.store.remove(recRecord);	
				}
			}	
		},
		'select':function(combo){
			if(combo.value=='1'){
				//反确认
				writeoff.statementEdit.statementUnConfirm();
			}else if(combo.value=='2'){
				//打印对账单回执
				var yesFn=function(){
					//获取对账单打印数据
					var selection=Ext.getCmp('T_writeoff-statementEdit_content').getStatementQueryGrid().getSelectionModel().getSelection();
					if(selection.length==0){
						Ext.Msg.alert(writeoff.statementEdit.i18n('foss.stl.writeoff.common.alert'),writeoff.statementEdit.i18n('foss.stl.writeoff.statementEdit.unSelectToPrintReceiptWarning'));
						return false;
					}
					
					if(selection[0].get('id')==''){
						Ext.Msg.alert(writeoff.statementEdit.i18n('foss.stl.writeoff.common.alert'),writeoff.statementEdit.i18n('foss.stl.writeoff.statementEdit.statementIdIsNullWarning'));
						return false;
					}
					writeoff.statementEdit.statementConfReceipt(selection[0].get('id'));
				};
				var noFn=function(){
				 	return false;
				};
				writeoff.statementEdit.statementConfirmAlert(writeoff.statementEdit.i18n('foss.stl.writeoff.statementEdit.isPrintReceipt'),yesFn,noFn);
			}else if(combo.value=='3'){
				//确认	
				writeoff.statementEdit.statementConfirm();
			}else if(combo.value=='4'){
				//还款	
				writeoff.statementEdit.statementRepayment();
			}else if(combo.value=='5'){
				//明细
				writeoff.statementEdit.statementViewDetail();
			}else if(combo.value=='6'){
				//核销
				writeoff.statementEdit.statementWriteoff();
			}else if(combo.value=='7'){
				//已申请
				writeoff.statementEdit.statementApplied();
			}else if(combo.value=='8'){
				//未申请
				writeoff.statementEdit.statementNotApply();
			}
		}
	}
}); 
//操作列下拉框
var operateColumn = Ext.create('Foss.statementbill.operateColumn');

//编辑器
var SoperateColumnEditing = Ext.create('Ext.grid.plugin.CellEditing', {  
  	clicksToEdit : 1,
  	isObservable : false
}) ;

/**
 * 对账单列表
 */
Ext.define('Foss.statementbill.StatementQueryGrid', {
	extend:'Ext.grid.Panel',
    title: writeoff.statementEdit.i18n('foss.stl.writeoff.statementEdit.statementBill'),
	frame:true,
	height:600,
	repaymentBillWindow:Ext.create('Foss.statementbill.RepaymentBillWindow'),
	writeoffBillWindow:Ext.create('Foss.statementbill.UnVerifyEntryWindow'),
	plugins:SoperateColumnEditing,
	selModel:Ext.create('Ext.selection.CheckboxModel'),
    store: Ext.create('Foss.statementbill.StatementQueryGridStore'),
    viewConfig:{
  		enableTextSelection : true//设置行可以选择，进而可以复制
  	},
    columns: [{ 
		header:writeoff.statementEdit.i18n('foss.stl.writeoff.statementAdd.actionColumn'),  
		editor:operateColumn,
		renderer: function(value){
			  var record = operateColumn.findRecord(operateColumn.valueField, value);
			  return record ? record.get(operateColumn.displayField): operateColumn.valueNotFoundText;
		}
	},{
		header: writeoff.statementEdit.i18n('foss.stl.writeoff.statementAdd.statementBillNo'), 
		dataIndex: 'statementBillNo'
	},{
		header: writeoff.statementEdit.i18n('foss.stl.writeoff.common.customerCode'),
		dataIndex: 'customerCode' 
	},{
		header: writeoff.statementEdit.i18n('foss.stl.writeoff.common.customerName'), 
		dataIndex: 'customerName'
	},{ 
		header: writeoff.statementEdit.i18n('foss.stl.writeoff.statementEdit.billType'),
		dataIndex: 'billType',
		renderer:function(value){
			var displayField = FossDataDictionary. rendererSubmitToDisplay (value,settlementDict.STATEMENT_OF_ACCOUNT__BILL_TYPE);
    		return displayField;
		}
	},{ 
		header: writeoff.statementEdit.i18n('foss.stl.writeoff.statementEdit.statementStatus'),
		dataIndex: 'confirmStatus',
		renderer:function(value){
			var displayField = FossDataDictionary. rendererSubmitToDisplay (value,settlementDict.STATEMENT_OF_ACCOUNT__CONFIRM_STATUS);
    		return displayField;
		}
	},{ 
		header: writeoff.statementEdit.i18n('foss.stl.writeoff.statementEdit.periodAmount'),
		dataIndex: 'periodAmount' 
	},{ 
		header: writeoff.statementEdit.i18n('foss.stl.writeoff.statementEdit.paidAmount'),
		dataIndex: 'paidAmount',
		renderer:function(value,m,record){
			//声明默认值为0
			value = 0;
			//获取本期发生额
			var periodAmount = record.get('periodAmount');
			//获取本期未还金额
			var unpaidAmount = record.get('unpaidAmount');
			//如果本期发生金额小于等于0，则本期已还金额默认为0，反之为本期发生额-本期未还金额
			if(!Ext.isEmpty(periodAmount) && periodAmount>0){
				//value = periodAmount - unpaidAmount;
				value = stl.subtrAmountPoint(periodAmount,unpaidAmount);
			}
			//返回
			return value;
		}
	},{ 
		header: writeoff.statementEdit.i18n('foss.stl.writeoff.statementEdit.unpaidAmount'),
		dataIndex: 'unpaidAmount'
	},{ 
		header: writeoff.statementEdit.i18n('foss.stl.writeoff.statementEdit.periodStartDate'),
		dataIndex: 'periodBeginDate',
		renderer:function(value){
			if(value!=null){
				return Ext.Date.format(new Date(value), 'Y-m-d');
			}else{
				return null;
			}
		}
	},{ 
		header: writeoff.statementEdit.i18n('foss.stl.writeoff.statementEdit.periodEndAmount'),
		dataIndex: 'periodEndDate',
		renderer:function(value){
			if(value!=null){
				return Ext.Date.format(new Date(value), 'Y-m-d');
			}else{
				return null;
			}
		}
	},{
		header: writeoff.statementEdit.i18n('foss.stl.writeoff.statementAdd.periodRecAmount'),
		dataIndex: 'periodRecAmount'
	},{
		header: writeoff.statementEdit.i18n('foss.stl.writeoff.statementAdd.periodPayAmount'),
		dataIndex: 'periodPayAmount'
	},{
		header: writeoff.statementEdit.i18n('foss.stl.writeoff.statementAdd.periodPreAmount'),
		dataIndex: 'periodPreAmount'
	},{
		header: writeoff.statementEdit.i18n('foss.stl.writeoff.statementAdd.periodAdvAmount'),
		dataIndex: 'periodAdvAmount'
	},{
		header: writeoff.statementEdit.i18n('foss.stl.writeoff.statementAdd.periodUnverifyRecAmount'),
		dataIndex: 'periodUnverifyRecAmount'
	},{
		header: writeoff.statementEdit.i18n('foss.stl.writeoff.statementAdd.periodUnverifyPayAmount'),
		dataIndex: 'periodUnverifyPayAmount'
	},{
		header: writeoff.statementEdit.i18n('foss.stl.writeoff.statementAdd.periodUnverifyPreAmount'),
		dataIndex: 'periodUnverifyPreAmount'
	},{
		header: writeoff.statementEdit.i18n('foss.stl.writeoff.statementAdd.periodUnverifyAdvAmount'),
		dataIndex: 'periodUnverifyAdvAmount'
	},{
		header: writeoff.statementEdit.i18n('foss.stl.writeoff.common.companyCode'),
		dataIndex: 'companyCode'
	},{
		header: writeoff.statementEdit.i18n('foss.stl.writeoff.common.companyName'),
		dataIndex: 'companyName'
	},{
		header: writeoff.statementEdit.i18n('foss.stl.writeoff.statementAdd.confirmTime'),
		dataIndex: 'confirmTime',
		renderer:function(value){
			if(value!=null){
				return Ext.Date.format(new Date(value), 'Y-m-d');
			}else{
				return null;
			}
		}
	},{
		header: writeoff.statementEdit.i18n('foss.stl.writeoff.common.orgCode'),
		dataIndex: 'createOrgCode'
	},{
		header: writeoff.statementEdit.i18n('foss.stl.writeoff.common.orgName'),
		dataIndex: 'createOrgName'
	},{
		header: writeoff.statementEdit.i18n('foss.stl.writeoff.statementAdd.unifiedCode'),
		dataIndex: 'unifiedCode'
	},{
		header: writeoff.statementEdit.i18n('foss.stl.writeoff.statementEdit.companyAccountBankNo'),
		dataIndex: 'companyAccountBankNo'
	},{
		header: writeoff.statementEdit.i18n('foss.stl.writeoff.statementAdd.accountUserName'),
		dataIndex: 'accountUserName'
	},{
		header: writeoff.statementEdit.i18n('foss.stl.writeoff.statementEdit.bankBranchName'),
		dataIndex: 'bankBranchName'
	},{
		header: writeoff.statementEdit.i18n('foss.stl.writeoff.statementAdd.settleNum'),
		dataIndex: 'settleNum'
	},{ 
		header: writeoff.statementEdit.i18n('foss.stl.writeoff.common.createTime'),
		dataIndex: 'createTime',
		renderer:function(value){
			if(value!=null){
				return Ext.Date.format(new Date(value), 'Y-m-d');
			}else{
				return null;
			}
		}
	},{
		header: writeoff.statementEdit.i18n('foss.stl.writeoff.statementCommon.billDescription'),
		dataIndex: 'notes'
	},{
		header: writeoff.statementEdit.i18n('foss.stl.writeoff.statementEdit.invoiceStatus'),
		dataIndex: 'invoiceStatus',
		renderer:function(value){
			var displayField = null;
			if (!Ext.isEmpty(value)) {
				if (value == "AP") {
					displayField = "已申请";
				} else if (value == "NP") {
					displayField = "未申请";
				}
			}
			return displayField;
		}
	},{
		header: writeoff.statementEdit.i18n('foss.stl.writeoff.statementEdit.applyInvoice'),
		dataIndex: 'applyInvoice',
		renderer:function(value){
			var displayField = null;
			if (!Ext.isEmpty(value)) {
				if (value == "Y") {
					displayField = "是";
				} else if (value == "N") {
					displayField = "否";
				}
			}
			return displayField;
		}
	},{
		header: writeoff.statementEdit.i18n('foss.stl.writeoff.statementAdd.unifiedSettlement'),
		dataIndex: 'unifiedSettlement',
		renderer:function(value){
			var displayField = null;
			if (!Ext.isEmpty(value)) {
				if (value == "Y") {
					displayField = "是";
				} else if (value == "N") {
					displayField = "否";
				}
			}
			return displayField;
		}
	},{
		header:'id',
		dataIndex:'id',
		hidden:true
	},{
		header: writeoff.statementEdit.i18n('foss.stl.writeoff.common.versionNo'),
		dataIndex:'versionNo',
		hidden:true
	}],
	//批量还款方法
	batchRepayment:writeoff.statementEdit.statementRepaymentBatch,
	//批量核销
	statementWriteoffBatch:writeoff.statementEdit.statementWriteoffBatch,
    initComponent:function(){
		var me = this;
		me.dockedItems = [{
	   		xtype: 'toolbar',
		    dock: 'top',
		    layout:'column',		    	
		    defaults:{
			margin:'0 0 5 3'
			},		
		    items: [{
				xtype:'button',
				text:writeoff.statementEdit.i18n('foss.stl.writeoff.statementEdit.batchRepayment'),
				columnWidth:.1,
				hidden:!writeoff.statementEdit.isPermission('/stl-web/writeoff/repayment.action'),
				handler:function(){
					this.up('panel').batchRepayment();
				}
			},{
				xtype:'button',
				text:writeoff.statementEdit.i18n('foss.stl.writeoff.statementEdit.batchWriteoff'),
				columnWidth:.1,
				hidden:!writeoff.statementEdit.isPermission('/stl-web/writeoff/batchWriteoffStatement.action'),
				handler:function(){
					this.up('panel').statementWriteoffBatch();  
				}
			},{
				xtype:'displayfield',
				value:'点击表格操作列<span style="color:red;">空白处</span>，可进行查看明细等操作！',
				columnWidth:.3
			},{
				xtype:'button',
				text:writeoff.statementEdit.i18n('foss.stl.writeoff.statementEdit.batchNotApply'),
				columnWidth:.1,
				hidden:!writeoff.statementEdit.isPermission('/stl-web/writeoff/batchNotApply.action'),
				handler:writeoff.statementEdit.statementNotApply
			},{
				xtype:'button',
				text:writeoff.statementEdit.i18n('foss.stl.writeoff.statementEdit.batchApplied'),
				columnWidth:.1,
				hidden:!writeoff.statementEdit.isPermission('/stl-web/writeoff/batchApplied.action'),
				handler:writeoff.statementEdit.statementApplied
			},{
				xtype:'button',
				text:writeoff.statementEdit.i18n('foss.stl.writeoff.statementEdit.exportStatementXLS'),
				columnWidth:.1,
				hidden:!writeoff.statementEdit.isPermission('/stl-web/writeoff/exportStatementXLS.action'),
				handler:writeoff.statementEdit.statementExportXLS
			},{
				xtype:'button',
				text:writeoff.statementEdit.i18n('foss.stl.writeoff.statementEdit.exportStatement'),
				columnWidth:.1,
				handler:writeoff.statementEdit.statementExportPDF 
			},{
				xtype:'button',
				text:writeoff.statementEdit.i18n('foss.stl.writeoff.statementEdit.printStatement'),
				columnWidth:.1,
				handler:writeoff.statementEdit.statementPrint
			}]
    	},{
	   		xtype: 'toolbar',
		    dock: 'bottom',
		    layout:'column',
			defaultType:'textfield',
		    defaults:{
				margin:'5 0 5 3',
				readOnly:true,
				labelWidth:60
			},		
		    items: [
		     {
				xtype:'displayfield',
				allowBlank:true,
				id:'Foss_statementBill_StatementQueryGrid_TotalCount_ID',
				columnWidth:.1,
				fieldLabel:writeoff.statementEdit.i18n('foss.stl.writeoff.statementAdd.totalCount')
			},{
				xtype:'displayfield',
				allowBlank:true,
				columnWidth:.2,
				fieldLabel:writeoff.statementEdit.i18n('foss.stl.writeoff.common.totalAmount'),
				id:'Foss_statementBill_StatementQueryGrid_TotalAmount_ID'
			},{
				xtype:'standardpaging',
				store:me.store,
				columnWidth:.7,
				pageSize:50,
				plugins: Ext.create('Deppon.ux.PageSizePlugin', {
					//设置分页记录最大值，防止输入过大的数值
					maximumSize: 1000,
					sizeList: [['10',10],['25',25],['50',50],['100',100],['200', 200],['500', 500],['1000', 1000]]
				})
			 },{
				xtype:'button',
				text:writeoff.statementEdit.i18n('foss.stl.writeoff.statementEdit.confirmStatement'),
				columnWidth:.1,
				hidden:!writeoff.statementEdit.isPermission('/stl-web/writeoff/confirmStatement.action'),
				handler:writeoff.statementEdit.statementConfirmBatch
			},{
				xtype:'button',
				text:writeoff.statementEdit.i18n('foss.stl.writeoff.statementEdit.batchRepayment'),
				columnWidth:.1,
				hidden:!writeoff.statementEdit.isPermission('/stl-web/writeoff/repayment.action'),
				handler:function(){
					this.up('panel').batchRepayment();
				}
			},{
				xtype:'button',
				text:writeoff.statementEdit.i18n('foss.stl.writeoff.statementEdit.batchWriteoff'),
				columnWidth:.1,
				hidden:!writeoff.statementEdit.isPermission('/stl-web/writeoff/batchWriteoffStatement.action'),
				handler:function(){
					this.up('panel').statementWriteoffBatch();  
				}
			},{
				columnWidth:.5
			},{
				xtype:'button',
				text:writeoff.statementEdit.i18n('foss.stl.writeoff.statementEdit.exportStatement'),
				columnWidth:.1,
				handler:writeoff.statementEdit.statementExportPDF 
			},{
				xtype:'button',
				text:writeoff.statementEdit.i18n('foss.stl.writeoff.statementEdit.printStatement'),
				columnWidth:.1,
				handler:writeoff.statementEdit.statementPrint
			}]
    	}];
   		 me.callParent();
    }
});

/**
 * 修改对账单页面入口
 */
Ext.onReady(function() {
	Ext.QuickTips.init();
	//创建对账单查询tab
	var statementQueryInfoTab = Ext.create('Foss.statementbill.StatementQueryInfoTab');
	//创建查询隐藏域
	var queryEditHiddenfield =Ext.create('Foss.statementbill.QueryEditParamsHiddenfield',{hidden:true});
	//创建对账单查询表格
	var statementQueryGrid = Ext.create('Foss.statementbill.StatementQueryGrid');
	
	//创建对账单查询panel	
	Ext.create('Ext.panel.Panel',{
		id: 'T_writeoff-statementEdit_content',
		cls: "panelContentNToolbar",
		bodyCls: 'panelContentNToolbar-body',
		layout: 'auto',
		getQueryInfoTab:function(){
			return statementQueryInfoTab;
		},
		getHiddenField:function(){
			return queryEditHiddenfield;
		},
		getStatementQueryGrid:function(){
			return statementQueryGrid;
		},
		items: [statementQueryInfoTab,queryEditHiddenfield,statementQueryGrid],
		renderTo: 'T_writeoff-statementEdit-body',
		listeners:{
			'boxready':function(th){
				var roles = stl.currentUserDepts;
				var queryByDateTab = th.getQueryInfoTab().items.items[0].items.items[0];
				var dept = queryByDateTab.getForm().findField("statementOrgCode");
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
				
				var queryByFailingInvoice = th.getQueryInfoTab().items.items[3].items.items[0];
				var failingInvoiceDept = queryByFailingInvoice.getForm().findField("failingInvoiceOrgCode");
				if(!Ext.isEmpty(stl.currentDept.code)){
					var displayText = stl.currentDept.name
					var valueText = stl.currentDept.code;
					var store = failingInvoiceDept.store;
					var  key = failingInvoiceDept.displayField + '';
					var value = failingInvoiceDept.valueField+ '';
					
					var m = Ext.create(store.model.modelName);
					m.set(key, displayText);
					m.set(value, valueText);
					
					store.loadRecords([m]);
					failingInvoiceDept.setValue(valueText);
				}
				if(writeoff.statementEdit.ddw == 'ddw'){
					th.getQueryInfoTab().setActiveTab(3);
					var form = th.getQueryInfoTab().items.items[3].items.items[0].getForm();
					writeoff.statementEdit.statementFailingInvoice(form);
				}
			}
		}
	});
});