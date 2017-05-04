/**
 * 提示信息
 * @param {} message 
 * @param {} yesFn
 * @param {} noFn
 */
writeoff.paymentPayable.billPaymentAndPayableConfirmAlert = function(message,yesFn,noFn){
	Ext.Msg.confirm(writeoff.paymentPayable.i18n('foss.stl.writeoff.common.alert'),message,function(o){
		if(o=='yes'){
			yesFn();
		}else{
			noFn();
		}
	});
};

writeoff.paymentPayable.QUERY_BYDATE = 'TD';//按客户查询
writeoff.paymentPayable.QUERY_BYNUMBER = 'DERE';//按单号查询

//按日期、客户来查询信息
writeoff.paymentPayable.queryPaymentPayableParams = function(form,me){
	Ext.getCmp('Foss_StlairPaymentPayable_StlPaymentQueryInfoGrid_Id').store.removeAll();
	Ext.getCmp('Foss_StlairPaymentPayable_StlPaymentQueryInfoGrid_airPaymentTotalRows_Id').setValue("");
	Ext.getCmp('Foss_StlairPaymentPayable_StlPaymentQueryInfoGrid_airPaymentTotalAmount_Id').setValue("");
	Ext.getCmp('Foss_StlairPaymentPayable_StlPaymentQueryInfoGrid_airPaymentVerifyAmount_Id').setValue("");
	Ext.getCmp('Foss_StlairPaymentPayable_StlPaymentQueryInfoGrid_airPaymentUnverifyAmount_Id').setValue("");
	Ext.getCmp('Foss_StlairPaymentPayable_StlPayableQueryInfoGrid_Id').store.removeAll();
	Ext.getCmp('Foss_StlairPaymentPayable_StlPayableQueryInfoGrid_airPayableTotalRows_Id').setValue("");
	Ext.getCmp('Foss_StlairPaymentPayable_StlPayableQueryInfoGrid_airPayableTotalAmount_Id').setValue("");
	Ext.getCmp('Foss_StlairPaymentPayable_StlPayableQueryInfoGrid_airPayableVerifyAmount_Id').setValue("");
	Ext.getCmp('Foss_StlairPaymentPayable_StlPayableQueryInfoGrid_airPayableUnverifyAmount_Id').setValue("");
	
	writeoff.paymentPayable.periodBeginDate = form.findField('startDate').getValue();
	writeoff.paymentPayable.periodEndDate = form.findField('endDate').getValue();
	writeoff.paymentPayable.customerDetail = form.findField('customerDetail').getValue();
	writeoff.paymentPayable.agencyDetail = form.findField('agencyDetail').getValue();
	writeoff.paymentPayable.customerCode = form.findField('customerDetail').getValue();
	writeoff.paymentPayable.queryByTab = writeoff.paymentPayable.QUERY_BYDATE;
	if(writeoff.paymentPayable.periodBeginDate==null || writeoff.paymentPayable.periodBeginDate==''){
		
		Ext.Msg.alert(writeoff.paymentPayable.i18n('foss.stl.writeoff.common.alert'),
				writeoff.paymentPayable.i18n('foss.stl.writeoff.airPaymentPayable.dateIsNotNull'));
		return false;
	}

	if(writeoff.paymentPayable.periodEndDate==null || writeoff.paymentPayable.periodEndDate==''){
		Ext.Msg.alert(writeoff.paymentPayable.i18n('foss.stl.writeoff.common.alert'),
				writeoff.paymentPayable.i18n('foss.stl.writeoff.airPaymentPayable.dateIsNotNull'));
		return false;
	}
	var compareTwoDate = stl.compareTwoDate(writeoff.paymentPayable.periodBeginDate,writeoff.paymentPayable.periodEndDate);
	if(compareTwoDate>stl.DATELIMITDAYS_MONTH){
		Ext.Msg.alert(writeoff.paymentPayable.i18n('foss.stl.writeoff.common.alert'),
				writeoff.paymentPayable.i18n('foss.stl.writeoff.airPaymentPayable.queryDateMaxLimitText1')+ stl.DATELIMITDAYS_MONTH 
				+writeoff.paymentPayable.i18n('foss.stl.writeoff.airPaymentPayable.queryDateMaxLimitText2'));
		return false;
	}else if(compareTwoDate<0){
		Ext.Msg.alert(writeoff.paymentPayable.i18n('foss.stl.writeoff.common.alert'),
				writeoff.paymentPayable.i18n('foss.stl.writeoff.common.dateEndBeforeStatrtWarining'));
		return false;
	}
	var customerCode = "";
	if(writeoff.paymentPayable.customerDetail != "" && writeoff.paymentPayable.customerDetail != null){
		customerCode = writeoff.paymentPayable.customerDetail;
	}else if(writeoff.paymentPayable.agencyDetail != "" && writeoff.paymentPayable.agencyDetail != null){
		customerCode = writeoff.paymentPayable.agencyDetail;
	}else{
		Ext.Msg.alert(writeoff.paymentPayable.i18n('foss.stl.writeoff.common.alert'),
				'客户信息不能为空！');
		return false;
	}
	var params = {
			'advPayWriteoffBillPayVo.advPayWriteoffBillPayDto.startBusinessDate':writeoff.paymentPayable.periodBeginDate,
			'advPayWriteoffBillPayVo.advPayWriteoffBillPayDto.endBusinessDate':writeoff.paymentPayable.periodEndDate,
			'advPayWriteoffBillPayVo.advPayWriteoffBillPayDto.customerCode':customerCode,
			'advPayWriteoffBillPayVo.advPayWriteoffBillPayDto.queryByTab':writeoff.paymentPayable.queryByTab
	}
	if(form.isValid()){
		//设置该按钮灰掉
		me.disable(false);
		//30秒后自动解除灰掉效果
		setTimeout(function() {
			me.enable(true);
		}, 30000);
    	Ext.Ajax.request({
    		url:writeoff.realPath('queryPaymentPayableParams.action'),
    		params:params,
    		success:function(response){
    			var result = Ext.decode(response.responseText);
    			var BillAdvancedPaymentEntityStore = Ext.getCmp('Foss_StlairPaymentPayable_StlPaymentQueryInfoGrid_Id').store;
    			var BillPayableEntryStore = Ext.getCmp('Foss_StlairPaymentPayable_StlPayableQueryInfoGrid_Id').store;
    			//加载后台查询到的数据到grid的store中
    			if(result.advPayWriteoffBillPayVo.advPayWriteoffBillPayDto.billAdvancedPaymentEntityList != null
    					&& result.advPayWriteoffBillPayVo.advPayWriteoffBillPayDto.billAdvancedPaymentEntityList != ""){
    				BillAdvancedPaymentEntityStore.loadData(result.advPayWriteoffBillPayVo.advPayWriteoffBillPayDto.billAdvancedPaymentEntityList);
    			}
    			
    			if(result.advPayWriteoffBillPayVo.advPayWriteoffBillPayDto.billPayableEntityList != null
    					&& result.advPayWriteoffBillPayVo.advPayWriteoffBillPayDto.billPayableEntityList != ""){
    				BillPayableEntryStore.loadData(result.advPayWriteoffBillPayVo.advPayWriteoffBillPayDto.billPayableEntityList);
    			}
    			
    			Ext.getCmp('Foss_StlairPaymentPayable_StlPaymentQueryInfoGrid_airPaymentTotalRows_Id').setValue(result.advPayWriteoffBillPayVo.advPayWriteoffBillPayDto.advPayNum);
    			Ext.getCmp('Foss_StlairPaymentPayable_StlPaymentQueryInfoGrid_airPaymentTotalAmount_Id').setValue(result.advPayWriteoffBillPayVo.advPayWriteoffBillPayDto.advPayAmount);
    			Ext.getCmp('Foss_StlairPaymentPayable_StlPaymentQueryInfoGrid_airPaymentVerifyAmount_Id').setValue(result.advPayWriteoffBillPayVo.advPayWriteoffBillPayDto.advPayVerifyAmount);
    			Ext.getCmp('Foss_StlairPaymentPayable_StlPaymentQueryInfoGrid_airPaymentUnverifyAmount_Id').setValue(result.advPayWriteoffBillPayVo.advPayWriteoffBillPayDto.advPayUnVerifyAmount);
    			Ext.getCmp('Foss_StlairPaymentPayable_StlPayableQueryInfoGrid_airPayableTotalRows_Id').setValue(result.advPayWriteoffBillPayVo.advPayWriteoffBillPayDto.billPayNum);
    			Ext.getCmp('Foss_StlairPaymentPayable_StlPayableQueryInfoGrid_airPayableTotalAmount_Id').setValue(result.advPayWriteoffBillPayVo.advPayWriteoffBillPayDto.billPayAmount);
    			Ext.getCmp('Foss_StlairPaymentPayable_StlPayableQueryInfoGrid_airPayableVerifyAmount_Id').setValue(result.advPayWriteoffBillPayVo.advPayWriteoffBillPayDto.billPayVerifyAmount);
    			Ext.getCmp('Foss_StlairPaymentPayable_StlPayableQueryInfoGrid_airPayableUnverifyAmount_Id').setValue(result.advPayWriteoffBillPayVo.advPayWriteoffBillPayDto.billPayUnVerifyAmount);
    			me.enable(true);
    		},
    		failure:function(response){
    			var result = Ext.decode(response.responseText);
    			me.enable(true);
    		}
    	});
	}else{
		Ext.Msg.alert(writeoff.paymentPayable.i18n('foss.stl.writeoff.common.alert'),
				writeoff.paymentPayable.i18n('foss.stl.writeoff.common.validateFailAlert'));
		return false;
	}
}

//按预付应付单号查询信息
writeoff.paymentPayable.queryPaymentPayableNos = function(form,me){
	Ext.getCmp('Foss_StlairPaymentPayable_StlPaymentQueryInfoGrid_Id').store.removeAll();
	Ext.getCmp('Foss_StlairPaymentPayable_StlPaymentQueryInfoGrid_airPaymentTotalRows_Id').setValue("");
	Ext.getCmp('Foss_StlairPaymentPayable_StlPaymentQueryInfoGrid_airPaymentTotalAmount_Id').setValue("");
	Ext.getCmp('Foss_StlairPaymentPayable_StlPaymentQueryInfoGrid_airPaymentVerifyAmount_Id').setValue("");
	Ext.getCmp('Foss_StlairPaymentPayable_StlPaymentQueryInfoGrid_airPaymentUnverifyAmount_Id').setValue("");
	Ext.getCmp('Foss_StlairPaymentPayable_StlPayableQueryInfoGrid_Id').store.removeAll();
	Ext.getCmp('Foss_StlairPaymentPayable_StlPayableQueryInfoGrid_airPayableTotalRows_Id').setValue("");
	Ext.getCmp('Foss_StlairPaymentPayable_StlPayableQueryInfoGrid_airPayableTotalAmount_Id').setValue("");
	Ext.getCmp('Foss_StlairPaymentPayable_StlPayableQueryInfoGrid_airPayableVerifyAmount_Id').setValue("");
	Ext.getCmp('Foss_StlairPaymentPayable_StlPayableQueryInfoGrid_airPayableUnverifyAmount_Id').setValue("");
	writeoff.paymentPayable.advancesNo = form.findField('advancesNos').getValue();
	writeoff.paymentPayable.payableNo = form.findField('payableNos').getValue();
	writeoff.paymentPayable.queryByTab = writeoff.paymentPayable.QUERY_BYNUMBER;
	if(writeoff.paymentPayable.advancesNo ==null ||
			writeoff.paymentPayable.advancesNo ==''){
		Ext.Msg.alert(writeoff.paymentPayable.i18n('foss.stl.writeoff.common.alert'),
				writeoff.paymentPayable.i18n('foss.stl.writeoff.airPaymentPayable.queryAdvancesNosIsNotNull'));
		return false;
	}
	
	if(writeoff.paymentPayable.payableNo==null ||
			writeoff.paymentPayable.payableNo==''){
		Ext.Msg.alert(writeoff.paymentPayable.i18n('foss.stl.writeoff.common.alert'),
				writeoff.paymentPayable.i18n('foss.stl.writeoff.airPaymentPayable.queryPayableNosIsNotNull'));
		return false;
	}
	
	var advancesNosStr = new Array();
	advancesNosStr = stl.splitToArray(writeoff.paymentPayable.advancesNo);
	if(advancesNosStr.length>10){
		Ext.Msg.alert(writeoff.paymentPayable.i18n('foss.stl.writeoff.common.alert'),
				writeoff.paymentPayable.i18n('foss.stl.writeoff.airPaymentPayable.queryAdvanceNosLimit'));
		return false;
	}
	
	var payableNosStr = new Array();
	payableNosStr = stl.splitToArray(writeoff.paymentPayable.payableNo);
	if(payableNosStr.length>10){
		Ext.Msg.alert(writeoff.paymentPayable.i18n('foss.stl.writeoff.common.alert'),
				writeoff.paymentPayable.i18n('foss.stl.writeoff.airPaymentPayable.queryPayableNosLimit'));
		return false;
	}
	var params = {
			'advPayWriteoffBillPayVo.advPayWriteoffBillPayDto.advancesNo':writeoff.paymentPayable.advancesNo,
			'advPayWriteoffBillPayVo.advPayWriteoffBillPayDto.payableNo':writeoff.paymentPayable.payableNo,
			'advPayWriteoffBillPayVo.advPayWriteoffBillPayDto.queryByTab':writeoff.paymentPayable.queryByTab
	};
	//设置该按钮灰掉
	me.disable(false);
	//30秒后自动解除灰掉效果
	setTimeout(function() {
		me.enable(true);
	}, 30000);
	Ext.Ajax.request({
		url : writeoff.realPath('queryPaymentPayableNos.action'),
		params:params,
		success:function(response){
			var result = Ext.decode(response.responseText);
			var BillAdvancedPaymentEntityStore = Ext.getCmp('Foss_StlairPaymentPayable_StlPaymentQueryInfoGrid_Id').store;
			var BillPayableEntryStore = Ext.getCmp('Foss_StlairPaymentPayable_StlPayableQueryInfoGrid_Id').store;
			//加载后台查询到的数据到grid的store中
			if(result.advPayWriteoffBillPayVo.advPayWriteoffBillPayDto.billAdvancedPaymentEntityList != null
					&& result.advPayWriteoffBillPayVo.advPayWriteoffBillPayDto.billAdvancedPaymentEntityList != ""){
				BillAdvancedPaymentEntityStore.loadData(result.advPayWriteoffBillPayVo.advPayWriteoffBillPayDto.billAdvancedPaymentEntityList);
			}
			if(result.advPayWriteoffBillPayVo.advPayWriteoffBillPayDto.billPayableEntityList != null
					&& result.advPayWriteoffBillPayVo.advPayWriteoffBillPayDto.billPayableEntityList != ""){
				BillPayableEntryStore.loadData(result.advPayWriteoffBillPayVo.advPayWriteoffBillPayDto.billPayableEntityList);
			}
			Ext.getCmp('Foss_StlairPaymentPayable_StlPaymentQueryInfoGrid_airPaymentTotalRows_Id').setValue(result.advPayWriteoffBillPayVo.advPayWriteoffBillPayDto.advPayNum);
			Ext.getCmp('Foss_StlairPaymentPayable_StlPaymentQueryInfoGrid_airPaymentTotalAmount_Id').setValue(result.advPayWriteoffBillPayVo.advPayWriteoffBillPayDto.advPayAmount);
			Ext.getCmp('Foss_StlairPaymentPayable_StlPaymentQueryInfoGrid_airPaymentVerifyAmount_Id').setValue(result.advPayWriteoffBillPayVo.advPayWriteoffBillPayDto.advPayVerifyAmount);
			Ext.getCmp('Foss_StlairPaymentPayable_StlPaymentQueryInfoGrid_airPaymentUnverifyAmount_Id').setValue(result.advPayWriteoffBillPayVo.advPayWriteoffBillPayDto.advPayUnVerifyAmount);
			Ext.getCmp('Foss_StlairPaymentPayable_StlPayableQueryInfoGrid_airPayableTotalRows_Id').setValue(result.advPayWriteoffBillPayVo.advPayWriteoffBillPayDto.billPayNum);
			Ext.getCmp('Foss_StlairPaymentPayable_StlPayableQueryInfoGrid_airPayableTotalAmount_Id').setValue(result.advPayWriteoffBillPayVo.advPayWriteoffBillPayDto.billPayAmount);
			Ext.getCmp('Foss_StlairPaymentPayable_StlPayableQueryInfoGrid_airPayableVerifyAmount_Id').setValue(result.advPayWriteoffBillPayVo.advPayWriteoffBillPayDto.billPayVerifyAmount);
			Ext.getCmp('Foss_StlairPaymentPayable_StlPayableQueryInfoGrid_airPayableUnverifyAmount_Id').setValue(result.advPayWriteoffBillPayVo.advPayWriteoffBillPayDto.billPayUnVerifyAmount);
			me.enable(true);
		},
		failure:function(response){
			var result = Ext.decode(response.responseText);
			me.enable(true);
		}
	});
}

//导出待核销的预付单
writeoff.paymentPayable.advancedListExport = function(){
	//获取表格
	grid = Ext.getCmp('Foss_StlairPaymentPayable_StlPaymentQueryInfoGrid_Id');
	//如果没有查询到数据，则不能进行导出操作
	if(grid.store.data.length == 0){
		Ext.Msg.alert(writeoff.paymentPayable.i18n('foss.stl.writeoff.common.alert'),
				writeoff.paymentPayable.i18n('foss.stl.writeoff.common.notOpeByNoResult'));
		return false;
	}
	
	if(!Ext.fly('downloadAttachFileForm')){
	    var frm = document.createElement('form');
	    frm.id = 'downloadAttachFileForm';
	    frm.style.display = 'none';
	    document.body.appendChild(frm);
	}
	var customerCode = "";
	if(writeoff.paymentPayable.customerDetail != "" && writeoff.paymentPayable.customerDetail != null){
		customerCode = writeoff.paymentPayable.customerDetail;
	}else if(writeoff.paymentPayable.agencyDetail != "" && writeoff.paymentPayable.agencyDetail != null){
		customerCode = writeoff.paymentPayable.agencyDetail;
	}
	if(writeoff.paymentPayable.queryByTab == writeoff.paymentPayable.QUERY_BYDATE	){
		searchParams = {
				'advPayWriteoffBillPayVo.advPayWriteoffBillPayDto.startBusinessDate':writeoff.paymentPayable.periodBeginDate,
				'advPayWriteoffBillPayVo.advPayWriteoffBillPayDto.endBusinessDate':writeoff.paymentPayable.periodEndDate,
				'advPayWriteoffBillPayVo.advPayWriteoffBillPayDto.customerCode':customerCode,
				'advPayWriteoffBillPayVo.advPayWriteoffBillPayDto.queryByTab':writeoff.paymentPayable.queryByTab
		}
	}
	
	if(writeoff.paymentPayable.queryByTab == writeoff.paymentPayable.QUERY_BYNUMBER	){
		searchParams = {
				'advPayWriteoffBillPayVo.advPayWriteoffBillPayDto.advancesNo':writeoff.paymentPayable.advancesNo,
				'advPayWriteoffBillPayVo.advPayWriteoffBillPayDto.queryByTab':writeoff.paymentPayable.queryByTab
		}
	}
	
	var yesFn=function(){
		Ext.Ajax.request({
			url:writeoff.realPath('advancedListExport.action'),
    		form: Ext.fly('downloadAttachFileForm'),
    		params :searchParams,
    		isUpload: true,
    		success:function(response){
    			//如果异常信息有值，则弹出提示
    			var result = Ext.decode(response.responseText);	
    	    	if(!Ext.isEmpty(result.errorMessage)){
    	    		Ext.Msg.alert(writeoff.paymentPayable.i18n('foss.stl.writeoff.common.alert'),result.errorMessage);
    	    		return false;
    	    	}
    	    	Ext.ux.Toast.msg(writeoff.paymentPayable.i18n('foss.stl.writeoff.common.alert'), 
    	    			writeoff.paymentPayable.i18n('foss.stl.writeoff.airPaymentPayable.exportSuccess'), 'success', 1000);
    		},
    		failure:function(response){
    			Ext.ux.Toast.msg(writeoff.paymentPayable.i18n('foss.stl.writeoff.common.alert'), 
    					writeoff.paymentPayable.i18n('foss.stl.writeoff.airPaymentPayable.exportError'), 'error', 1000);
    		}
    	});
	};
	var noFn=function(){
	 	return false;
	};
	writeoff.paymentPayable.billPaymentAndPayableConfirmAlert(
			writeoff.paymentPayable.i18n('foss.stl.writeoff.airPaymentPayable.isExportAdvances'),yesFn,noFn);
}

//导出待核销的应付单
writeoff.paymentPayable.payableListExport = function(){
	//获取表格
	grid = Ext.getCmp('Foss_StlairPaymentPayable_StlPayableQueryInfoGrid_Id');
	//如果没有查询到数据，则不能进行导出操作
	if(grid.store.data.length == 0){
		Ext.Msg.alert(writeoff.paymentPayable.i18n('foss.stl.writeoff.common.alert'),
				writeoff.paymentPayable.i18n('foss.stl.writeoff.common.notOpeByNoResult'));
		return false;
	}
	
	if(!Ext.fly('downloadAttachFileForm')){
	    var frm = document.createElement('form');
	    frm.id = 'downloadAttachFileForm';
	    frm.style.display = 'none';
	    document.body.appendChild(frm);
	}
	var customerCode = "";
	if(writeoff.paymentPayable.customerDetail != "" && writeoff.paymentPayable.customerDetail != null){
		customerCode = writeoff.paymentPayable.customerDetail;
	}else if(writeoff.paymentPayable.agencyDetail != "" && writeoff.paymentPayable.agencyDetail != null){
		customerCode = writeoff.paymentPayable.agencyDetail;
	}
	if(writeoff.paymentPayable.queryByTab == writeoff.paymentPayable.QUERY_BYDATE	){
		searchParams = {
				'advPayWriteoffBillPayVo.advPayWriteoffBillPayDto.startBusinessDate':writeoff.paymentPayable.periodBeginDate,
				'advPayWriteoffBillPayVo.advPayWriteoffBillPayDto.endBusinessDate':writeoff.paymentPayable.periodEndDate,
				'advPayWriteoffBillPayVo.advPayWriteoffBillPayDto.customerCode':customerCode,
				'advPayWriteoffBillPayVo.advPayWriteoffBillPayDto.queryByTab':writeoff.paymentPayable.queryByTab
		}
	}
	
	if(writeoff.paymentPayable.queryByTab == writeoff.paymentPayable.QUERY_BYNUMBER	){
		searchParams = {
				'advPayWriteoffBillPayVo.advPayWriteoffBillPayDto.payableNo':writeoff.paymentPayable.payableNo,
				'advPayWriteoffBillPayVo.advPayWriteoffBillPayDto.queryByTab':writeoff.paymentPayable.queryByTab
		}
	}
	
	var yesFn=function(){
		Ext.Ajax.request({
			url:writeoff.realPath('payableListExport.action'),
    		form: Ext.fly('downloadAttachFileForm'),
      		params :searchParams,
      		isUpload: true,
      		success:function(response){
      			//如果异常信息有值，则弹出提示
      			var result = Ext.decode(response.responseText);	
      	    	if(!Ext.isEmpty(result.errorMessage)){
      	    		Ext.Msg.alert(writeoff.paymentPayable.i18n('foss.stl.writeoff.common.alert'),result.errorMessage);
      	    		return false;
      	    	}
      	    	Ext.ux.Toast.msg(writeoff.paymentPayable.i18n('foss.stl.writeoff.common.alert'), 
      	    			writeoff.paymentPayable.i18n('foss.stl.writeoff.airPaymentPayable.exportSuccess'), 'success', 1000);
      		},
      		failure:function(response){
      			Ext.ux.Toast.msg(writeoff.paymentPayable.i18n('foss.stl.writeoff.common.alert'), 
      					writeoff.paymentPayable.i18n('foss.stl.writeoff.airPaymentPayable.exportError'), 'error', 1000);
      		}
      	});	
	};
	var noFn=function(){
	 	return false;
	};
	writeoff.paymentPayable.billPaymentAndPayableConfirmAlert(
			writeoff.paymentPayable.i18n('foss.stl.writeoff.airPaymentPayable.isExportPayables'),yesFn,noFn);
}

//预付冲应付核销
writeoff.paymentPayable.writeoffAdvancedAndPayable = function(me){
	var selectionPayment = Ext.getCmp('Foss_StlairPaymentPayable_StlPaymentQueryInfoGrid_Id').getSelectionModel().getSelection();
	var selectionPayable = Ext.getCmp('Foss_StlairPaymentPayable_StlPayableQueryInfoGrid_Id').getSelectionModel().getSelection();
	if(selectionPayment.length==0){
		Ext.Msg.alert(writeoff.paymentPayable.i18n('foss.stl.writeoff.common.alert'),
				writeoff.paymentPayable.i18n('foss.stl.writeoff.airPaymentPayable.writeoffIsNotAdvances'));
		return false;
	}
	if(selectionPayable.length==0){
		Ext.Msg.alert(writeoff.paymentPayable.i18n('foss.stl.writeoff.common.alert'),
				writeoff.paymentPayable.i18n('foss.stl.writeoff.airPaymentPayable.writeoffIsNotPayables'));
		return false;
	}
	var advPayVerifyAmount = Ext.getCmp('Foss_StlairPaymentPayable_StlPaymentQueryInfoGrid_airPaymentVerifyAmount_Id').getValue();
	var advPayUnVerifyAmount = Ext.getCmp('Foss_StlairPaymentPayable_StlPaymentQueryInfoGrid_airPaymentUnverifyAmount_Id').getValue();
	var billPayVerifyAmount = Ext.getCmp('Foss_StlairPaymentPayable_StlPayableQueryInfoGrid_airPayableVerifyAmount_Id').getValue();
	var billPayUnVerifyAmount = Ext.getCmp('Foss_StlairPaymentPayable_StlPayableQueryInfoGrid_airPayableUnverifyAmount_Id').getValue();
	var jsonDataPayment = new Array();
	for(var i=0;i<selectionPayment.length;i++){
		jsonDataPayment.push(selectionPayment[i].data);
	}
	var jsonDataPayable = new Array();
	for(var i=0;i<selectionPayable.length;i++){
		jsonDataPayable.push(selectionPayable[i].data);
	}
	var entity = new Object();
	var advPayWriteoffBillPayDto = new Object();
	
	advPayWriteoffBillPayDto.advPayVerifyAmount = advPayVerifyAmount;
	advPayWriteoffBillPayDto.advPayUnVerifyAmount = advPayUnVerifyAmount;
	advPayWriteoffBillPayDto.billPayVerifyAmount = billPayVerifyAmount;
	advPayWriteoffBillPayDto.billPayUnVerifyAmount = billPayUnVerifyAmount;
	
	entity.advPayWriteoffBillPayDto = advPayWriteoffBillPayDto;
	entity.advancedPaymentEntities = jsonDataPayment;
	entity.billPayableEntities = jsonDataPayable;
	var yesFn=function(){
		//设置该按钮灰掉
		me.disable(false);
		//30秒后自动解除灰掉效果
		setTimeout(function() {
			me.enable(true);
		}, 30000);
    	Ext.Ajax.request({
    		url: writeoff.realPath('writeoffAdvancedAndPayable.action'),
    		jsonData:{'advPayWriteoffBillPayVo':entity},
    		method:'post',
    		success:function(response){
    			var result = Ext.decode(response.responseText);
				if(result.isException){
					Ext.Msg.alert(writeoff.paymentPayable.i18n('foss.stl.writeoff.common.alert'),result.message);
					return false;
				}
				for(var i=0;i<selectionPayment.length;i++){
    				Ext.getCmp('Foss_StlairPaymentPayable_StlPaymentQueryInfoGrid_Id').store.remove(selectionPayment[i]);
    				//selectionPayment[i].commit();
    			}
				for(var i=0;i<selectionPayable.length;i++){
    				Ext.getCmp('Foss_StlairPaymentPayable_StlPayableQueryInfoGrid_Id').store.remove(selectionPayable[i]);
    				//selectionPayable[i].commit();
    			}
				Ext.getCmp('Foss_StlairPaymentPayable_StlPaymentQueryInfoGrid_Id').store.insert(0,result.advPayWriteoffBillPayVo.advPayWriteoffBillPayDto.billAdvancedPaymentEntityList);
				Ext.getCmp('Foss_StlairPaymentPayable_StlPayableQueryInfoGrid_Id').store.insert(0,result.advPayWriteoffBillPayVo.advPayWriteoffBillPayDto.billPayableEntityList);
				Ext.getCmp('Foss_StlairPaymentPayable_StlPaymentQueryInfoGrid_airPaymentVerifyAmount_Id').setValue(result.advPayWriteoffBillPayVo.advPayWriteoffBillPayDto.advPayVerifyAmount);
				Ext.getCmp('Foss_StlairPaymentPayable_StlPaymentQueryInfoGrid_airPaymentUnverifyAmount_Id').setValue(result.advPayWriteoffBillPayVo.advPayWriteoffBillPayDto.advPayUnVerifyAmount);
				Ext.getCmp('Foss_StlairPaymentPayable_StlPayableQueryInfoGrid_airPayableVerifyAmount_Id').setValue(result.advPayWriteoffBillPayVo.advPayWriteoffBillPayDto.billPayVerifyAmount);
				Ext.getCmp('Foss_StlairPaymentPayable_StlPayableQueryInfoGrid_airPayableUnverifyAmount_Id').setValue(result.advPayWriteoffBillPayVo.advPayWriteoffBillPayDto.billPayUnVerifyAmount);
    			Ext.Msg.alert(writeoff.paymentPayable.i18n('foss.stl.writeoff.common.alert'),
    					writeoff.paymentPayable.i18n('foss.stl.writeoff.airPaymentPayable.writeoffSuccess'));
    			me.enable(true);
    		},
    		exception:function(response){
				var result = Ext.decode(response.responseText);	
				Ext.Msg.alert(writeoff.paymentPayable.i18n('foss.stl.writeoff.common.alert'),result.message);
				me.enable(true);
			},
    		failure:function(response){
    			var result = Ext.decode(response.responseText);
    			me.enable(true);
    		}
    	});
    };
    var noFn=function(){
     	return false;
    };
    writeoff.paymentPayable.billPaymentAndPayableConfirmAlert(
    		writeoff.paymentPayable.i18n('foss.stl.writeoff.airPaymentPayable.isWriteoff'),yesFn,noFn);
}


Ext.define('Foss.StlairPaymentPayable.StlairPaymentPayableQueryInfoTab', {
	extend:'Ext.tab.Panel',
	frame:false,
	bodyCls: 'autoHeight',
	cls: 'innerTabPanel',
	activeTab: 0,
	height : 180,
	items : [ {
		title: '<span class="statementBill_tabHead">&nbsp;&nbsp;'+writeoff.paymentPayable.i18n('foss.stl.writeoff.common.queryByDate')+'&nbsp;&nbsp;</span>',
		tabConfig: {
			width: 150
		},
		width: '100',
        layout:'fit',
        items:[{
        	xtype:'form',
        	defaults:{
	        	margin:'5 5 5 5',
	        	labelWidth:90
       		 },
        	layout:'column',
		    items:[{
		    	xtype:'datefield',
		    	id:'startDate',
		    	name:'startDate',
		    	fieldLabel:writeoff.paymentPayable.i18n('foss.stl.writeoff.airPaymentPayable.startDate'),
		    	value: stl.getTargetDate(new Date(),-7),
		    	format:'Y-m-d',
		    	allowBlank:false,
		    	columnWidth:.3
		    },{
		    	xtype: 'container',
				border : false,
				columnWidth:.03,
				html: '&nbsp;'
		    },{
		    	xtype:'datefield',
		   	 	name:'endDate',
		    	fieldLabel:writeoff.paymentPayable.i18n('foss.stl.writeoff.airPaymentPayable.endDate'),
		    	format:'Y-m-d',
		    	value:new Date(),
		    	allowBlank:false,
		    	maxValue:new Date(),
		    	columnWidth:.3
		    },{
		    	xtype: 'container',
				border : false,
				height:35,
				columnWidth:.33,
				html: '&nbsp;'
		    },{
    	    	xtype: 'combobox',
    			fieldLabel:"客户类型",
    			name:'customerType',
    	    	editable:false,
    			store:FossDataDictionary.getDataDictionaryStore("SETTLEMENT__CUSTOMER_TYPE",null,null,
    					[writeoff.paymentPayable.SETTLEMENT__CUSTOMER_TYPE__AIR,
    					 writeoff.paymentPayable.SETTLEMENT__CUSTOMER_TYPE__AIR_AGENCY]),
    			queryModel:'local',
    			displayField:'valueName',
    			valueField:'valueCode',
    			value:writeoff.paymentPayable.SETTLEMENT__CUSTOMER_TYPE__AIR,
    	    	columnWidth:.3,
    	    	listeners:{
    		    	'change':function(th,newValue,oldValue){
        				//获取表单等控件
        				var form,//表单
        					customerDetail,
        					agencyDetail,
        				//获取表单	
        				form= this.up('form').getForm();
        				//获取下面组件
        				customerDetail = form.findField('customerDetail');
        				agencyDetail = form.findField('agencyDetail');
        				if( newValue=='A'){
        					customerDetail.show();
        					customerDetail.labelEl.update('<span style="color:red;">*</span>'+
        							writeoff.paymentPayable.i18n('foss.stl.writeoff.airPaymentPayable.customerDetial'));
        					agencyDetail.hide();
        					agencyDetail.labelEl.update('空运代理');
        					agencyDetail.setValue("");
        				}else if(newValue=='AA'){
        					agencyDetail.show();
        					agencyDetail.labelEl.update('<span style="color:red;">*</span>空运代理');
        					customerDetail.hide();
        					customerDetail.setValue("");
        				}
    				}
    			}
		    },{
		    	xtype: 'container',
				border : false,
				columnWidth:.03,
				html: '&nbsp;'
		    },{	
		    	xtype:'commonairlinesselector',
		    	fieldLabel :'<span style="color:red;">*</span>'+writeoff.paymentPayable.i18n('foss.stl.writeoff.airPaymentPayable.customerDetial'),
		    	name : 'customerDetail',
				columnWidth:.3,
				listWidth:300,//设置下拉框宽度
				isPaging:true //分页
		    },{	
		    	xtype:'commonairagentselector',
				fieldLabel:'空运代理',//agency.airPayable.i18n('foss.stl.agency.airPayAndRec.agencyDetial'), 
				name:'agencyDetail',
				hidden:true,
				columnWidth:.3,
				listWidth:300,//设置下拉框宽度
				isPaging:true //分页
		    },{
		    	xtype: 'container',
				border : false,
				columnWidth:.33,
				html: '&nbsp;'
		    },{	
				border: 1,
				xtype:'container',
				columnWidth:1,
				defaultType:'button',
				layout:'column',
				items:[{
					text:writeoff.paymentPayable.i18n('foss.stl.writeoff.common.reset'),
					columnWidth:.1,
					handler:function(){
						this.up('form').getForm().reset();
					}
				},{
					xtype: 'container',
					border : false,
					columnWidth:.67,
					html: '&nbsp;'
				},{
    				text:writeoff.paymentPayable.i18n('foss.stl.writeoff.common.query'),
    				cls:'yellow_button',
    				columnWidth:.1,
    				handler:function(){
    					var form = this.up('form').getForm();
    					var me = this;
    					writeoff.paymentPayable.queryPaymentPayableParams(form,me)
    				}
				}]
		    }]
        }]
	}, {
		title: '<span class="statementBill_tabHead">&nbsp;&nbsp;'+writeoff.paymentPayable.i18n('foss.stl.writeoff.common.queryByNo')+'&nbsp;&nbsp;</span>',
        tabConfig: {
			width: 150
		},
		width: '200',
        layout:'fit',
        items:[{
        	xtype:'form',
        	defaults:{
	        	margin:'10 5 5 5',
	        	labelWidth:90
       		 },
        	layout:'column',
        	 items:[{
 		    	xtype: 'textarea',
 	 			autoScroll:true,
 	 			emptyText:writeoff.paymentPayable.i18n('foss.stl.writeoff.airPaymentPayable.queryLimitText'),
 	            fieldLabel: writeoff.paymentPayable.i18n('foss.stl.writeoff.common.advancesNo'),
 	            name: 'advancesNos',
 	            allowBlank:false,
 	            columnWidth:.4,
 	            regex:/^((UF)[0-9]{7,10}[;,]?)+$/i,
 	            regexText:writeoff.paymentPayable.i18n('foss.stl.writeoff.airPaymentPayable.advancesRegexText'), 
 		    },{
		    	xtype: 'container',
				border : false,
				columnWidth:.066,
				html: '&nbsp;'
		    },{
 		    	xtype: 'textarea',
 	 			autoScroll:true,
 	 			emptyText:writeoff.paymentPayable.i18n('foss.stl.writeoff.airPaymentPayable.queryLimitText'),
 	            fieldLabel: writeoff.paymentPayable.i18n('foss.stl.writeoff.common.payableNo'),
 	            name: 'payableNos',
 	            //height : 80,
 	            allowBlank:false,
 	            columnWidth:.4,
 	            regex:/^((YF)[0-9]{7,10}[;,]?)+$/i,
 	            regexText:writeoff.paymentPayable.i18n('foss.stl.writeoff.airPaymentPayable.payablesRegexText'), 
 		    },{
 		    	border: 1,
				xtype:'container',
				columnWidth:1,
				defaultType:'button',
				layout:'column',
				items:[{
					text:writeoff.paymentPayable.i18n('foss.stl.writeoff.common.reset'),
					columnWidth:.1,
					handler:function(){
						this.up('form').getForm().reset();
					}
				},{
					xtype: 'container',
					border : false,
					columnWidth:.67,
					html: '&nbsp;'
				},{
					text:writeoff.paymentPayable.i18n('foss.stl.writeoff.common.query'),
					cls:'yellow_button',
					columnWidth:.1,
					handler:function(){
    					var form = this.up('form').getForm();
    					var me = this;
    					writeoff.paymentPayable.queryPaymentPayableNos(form,me)
    				}
				}]
 		    }]
        }]
	}]
});


//空运预付单model
Ext.define('Foss.StlairPaymentPayable.BillPayableEntryModel',{
	extend:'Ext.data.Model',
	fields:[{
		name:'id'
	},{
		name:'advancesNo'
	},{
		name:'applyOrgName'
	},{
		name:'applyCompanyName'
	},{
		name:'customerName'
	},{
		name:'customerCode'
	},{
		name:'amount',
		type:'BigDecimal'
	},{
		name:'auditStatus'
	},{
		name:'verifyAmount',
		type:'BigDecimal'
	},{
		name:'unverifyAmount',
		type:'BigDecimal'
	},{
		name:'createTime'
	}]
});
//空运预付单Store
Ext.define('Foss.StlairPaymentPayable.BillPayableEntryStore',{
	extend:'Ext.data.Store',
	model:'Foss.StlairPaymentPayable.BillPayableEntryModel'
});

//空运预付单列表可编辑
var SoperateColumnEditing = Ext.create('Ext.grid.plugin.CellEditing', {  
  	clicksToEdit : 1
}) ;
//空运预付单列表
Ext.define('Foss.StlairPaymentPayable.StlPaymentQueryInfoGrid',{
	extend:'Ext.grid.Panel',
    title: writeoff.paymentPayable.i18n('foss.stl.writeoff.airPaymentPayable.advance'),
	frame:true,
	height:250,
	emptyText : writeoff.paymentPayable.i18n('foss.stl.writeoff.common.noResult'),
	//plugins:SoperateColumnEditing,
	selModel:Ext.create('Ext.selection.CheckboxModel'),
    store: Ext.create('Foss.StlairPaymentPayable.BillPayableEntryStore'),
	columns:[{
		header: writeoff.paymentPayable.i18n('foss.stl.writeoff.common.advancesNo'), 
		dataIndex: 'advancesNo'
	},{
		header: writeoff.paymentPayable.i18n('foss.stl.writeoff.airPaymentPayable.applyOrgName'), 
		width:150,
		dataIndex: 'applyOrgName'
	},{
		header: writeoff.paymentPayable.i18n('foss.stl.writeoff.airPaymentPayable.applyCompanyName'), 
		width:150,
		dataIndex: 'applyCompanyName'
	},{
		header: writeoff.paymentPayable.i18n('foss.stl.writeoff.airPaymentPayable.customerName'), 
		width:150,
		dataIndex: 'customerName'
	},{
		header: writeoff.paymentPayable.i18n('foss.stl.writeoff.airPaymentPayable.customerCode'),	
		dataIndex: 'customerCode'		
	},{
		header: writeoff.paymentPayable.i18n('foss.stl.writeoff.airPaymentPayable.approveStatus'),
		dataIndex:'auditStatus',
		renderer:function(value){
    		var displayField = FossDataDictionary.rendererSubmitToDisplay(value,settlementDict.BILL_ADVANCED_PAYMENT__AUDIT_STATUS);
    		return displayField;
    	}
	},{
		header: writeoff.paymentPayable.i18n('foss.stl.writeoff.common.amount'), 
		dataIndex: 'amount',
		align: 'right',  
		renderer:stl.amountFormat
	},{
		header: writeoff.paymentPayable.i18n('foss.stl.writeoff.common.verifyAmount'),
		dataIndex: 'verifyAmount' ,
		align: 'right',  
		renderer:stl.amountFormat
	},{
		header: writeoff.paymentPayable.i18n('foss.stl.writeoff.common.unverifyAmount'), 
		dataIndex: 'unverifyAmount',
		align: 'right',  
		renderer:stl.amountFormat
	},{
		header: writeoff.paymentPayable.i18n('foss.stl.writeoff.airPaymentPayable.createTime'),
		dataIndex: 'createTime' ,
		renderer:function(value){
			if(value!=null){
				return Ext.Date.format(new Date(value), 'Y-m-d');
			}else{
				return null;
			}
		} 
	}],
	viewConfig : {  
		enableTextSelection : true,  //设置行可以选择，进而可以复制
        forceFit : true,  
        enableRowBody: true,
        stripeRows: false,
        getRowClass : function(record,rowIndex,rowParams,store){ 
        	
            //禁用数据显示红色  
            if(record.data.unverifyAmount==0){  
                return 'x-grid-record-red';  
            }else{  
                return '';  
            }  
              
        }  
    }
});


//空运应付单model
Ext.define('Foss.StlairPaymentPayable.BillAdvancedPaymentEntityModel',{
	extend:'Ext.data.Model',
	fields:[{
		name:'id'
	},{
		name:'payableNo'
	},{
		name:'paymentNo'
	},{
		name:'payableOrgCode'
	},{
		name:'payableOrgName'
	},{
		name:'payableComName'
	},{
		name:'payableComCode'
	},{
		name:'customerName'
	},{
		name:'customerCode'
	},{
		name:'amount',
		type:'long'
	},{
		name:'verifyAmount',
		type:'long'
	},{
		name:'unverifyAmount',
		type:'long'
	},{
		name:'paymentAmount',
		type:'BigDecimal'
	},{
		name:'businessDate',
		type:'date'
	},{
		name:'createTime'
	}]
});
//空运应付单Store
Ext.define('Foss.StlairPaymentPayable.BillAdvancedPaymentEntityStore',{
	extend:'Ext.data.Store',
	model:'Foss.StlairPaymentPayable.BillAdvancedPaymentEntityModel'
});

//空运应付单列表可编辑
var SoperateColumnEditing1 = Ext.create('Ext.grid.plugin.CellEditing', {  
  	clicksToEdit : 1
}) ;
//空运应付单列表
Ext.define('Foss.StlairPaymentPayable.StlPayableQueryInfoGrid',{
	extend:'Ext.grid.Panel',
    title: writeoff.paymentPayable.i18n('foss.stl.writeoff.airPaymentPayable.payable'),
	frame:true,
	height:350,
	//plugins:SoperateColumnEditing1,
	emptyText : writeoff.paymentPayable.i18n('foss.stl.writeoff.common.noResult'),
	selModel:Ext.create('Ext.selection.CheckboxModel'),
    store: Ext.create('Foss.StlairPaymentPayable.BillAdvancedPaymentEntityStore'),
	columns:[{
		header: writeoff.paymentPayable.i18n('foss.stl.writeoff.common.payableNo'), 
		dataIndex: 'payableNo'
	},{
		header: writeoff.paymentPayable.i18n('foss.stl.writeoff.airPaymentPayable.paymentNo'), 
		hidden:true,
		dataIndex: 'paymentNo'
	},{
		header: writeoff.paymentPayable.i18n('foss.stl.writeoff.common.orgName'), 
		width:150,
		dataIndex: 'payableOrgName'
	},{
		header: writeoff.paymentPayable.i18n('foss.stl.writeoff.airPaymentPayable.applyCompanyName'), 
		width:150,
		dataIndex: 'payableComName'
	},{
		header: writeoff.paymentPayable.i18n('foss.stl.writeoff.common.customerName'), 
		width:150,
		dataIndex: 'customerName'
	},{
		header: writeoff.paymentPayable.i18n('foss.stl.writeoff.common.customerCode'), 
		width:150,
		dataIndex: 'customerCode'		
	},{
		header: writeoff.paymentPayable.i18n('foss.stl.writeoff.common.amount'), 
		dataIndex: 'amount'				
	},{
		header: writeoff.paymentPayable.i18n('foss.stl.writeoff.common.verifyAmount'),
		dataIndex: 'verifyAmount' 
	},{
		header: writeoff.paymentPayable.i18n('foss.stl.writeoff.common.unverifyAmount'), 
		dataIndex: 'unverifyAmount'				
	},{
		header: writeoff.paymentPayable.i18n('foss.stl.writeoff.common.paymentAmount'),
		dataIndex: 'paymentAmount',
		align: 'right',  
		renderer:stl.amountFormat
	},{
		header:writeoff.paymentPayable.i18n('foss.stl.writeoff.airPaymentPayable.createTime'),
		width:150,
		dataIndex: 'createTime' ,
		renderer:function(value){
    		if(value!=null){
    			return Ext.Date.format(new Date(value), 'Y-m-d');
    		}else{
    			return null;
    		}
    	} 
	}],
	viewConfig : {  
        forceFit : true,  
        enableRowBody: true,
        stripeRows: false,
        enableTextSelection : true, //设置行可以选择，进而可以复制
        getRowClass : function(record,rowIndex,rowParams,store){ 
        	
            //禁用数据显示灰色  
            if(record.data.unverifyAmount==0){  
                return 'x-grid-record-red';  
            }else{  
                return '';  
            }  
              
        }  
    }
});

//初始化界面
Ext.onReady(function() {
	Ext.QuickTips.init();

	var StlairPaymentPayableQueryInfoTab = Ext.create('Foss.StlairPaymentPayable.StlairPaymentPayableQueryInfoTab');
	
	var StlPaymentQuerInfoGrid = Ext.create('Foss.StlairPaymentPayable.StlPaymentQueryInfoGrid',{
		id:'Foss_StlairPaymentPayable_StlPaymentQueryInfoGrid_Id',
		dockedItems: [{
			xtype: 'toolbar',
			dock: 'top',
			layout:'column',
			items: [{
				xtype:'button',
				text:writeoff.paymentPayable.i18n('foss.stl.writeoff.common.export'),
				hidden:!writeoff.paymentPayable.isPermission('/stl-web/pay/advancedListExport.action'),
				handler:writeoff.paymentPayable.advancedListExport
			}]
		},{
			xtype: 'toolbar',
			dock: 'bottom',
			layout:'column',
			items:[{
				height:5,
				columnWidth:1
			},{
				xtype:'displayfield',
				allowBlank:true,
				id:'Foss_StlairPaymentPayable_StlPaymentQueryInfoGrid_airPaymentTotalRows_Id',
				columnWidth:.05,
				fieldLabel:writeoff.paymentPayable.i18n('foss.stl.writeoff.airPaymentPayable.totalSum'),
				labelWidth:50
			},{
				xtype:'container',
				border:false,
				html:'&nbsp;',
				columnWidth:.005
			},{
				xtype:'displayfield',
				allowBlank:true,
				id:'Foss_StlairPaymentPayable_StlPaymentQueryInfoGrid_airPaymentTotalAmount_Id',
				columnWidth:.1,
				fieldLabel:writeoff.paymentPayable.i18n('foss.stl.writeoff.common.amount'),
				labelWidth:50
			},{
				xtype:'container',
				border:false,
				html:'&nbsp;',
				columnWidth:.01
			},{
				xtype:'displayfield',
				allowBlank:true,
				id:'Foss_StlairPaymentPayable_StlPaymentQueryInfoGrid_airPaymentVerifyAmount_Id',
				columnWidth:.13,
				fieldLabel:writeoff.paymentPayable.i18n('foss.stl.writeoff.common.verifyAmount'),
				labelWidth:80
			},{
				xtype:'container',
				border:false,
				html:'&nbsp;',
				columnWidth:.05
			},{
				xtype:'displayfield',
				allowBlank:true,
				id:'Foss_StlairPaymentPayable_StlPaymentQueryInfoGrid_airPaymentUnverifyAmount_Id',
				columnWidth:.1,
				fieldLabel:writeoff.paymentPayable.i18n('foss.stl.writeoff.common.unverifyAmount'),
				labelWidth:80
			},{
				xtype:'container',
				border:false,
				height:24,
				html:'&nbsp;',
				columnWidth:.5
			}]
		}]
	});
	
	var StlPayableQuerInfoGrid = Ext.create('Foss.StlairPaymentPayable.StlPayableQueryInfoGrid',{
		id:'Foss_StlairPaymentPayable_StlPayableQueryInfoGrid_Id',
		dockedItems: [{
			xtype: 'toolbar',
			dock: 'top',
			layout:'column',
			items: [{
				xtype:'button',
				text:writeoff.paymentPayable.i18n('foss.stl.writeoff.common.export'),
				hidden:!writeoff.paymentPayable.isPermission('/stl-web/pay/payableListExport.action'),
				handler:writeoff.paymentPayable.payableListExport
			}]
		},{
			xtype: 'toolbar',
			dock: 'bottom',
			layout:'column',
			items:[{
				height:5,
				columnWidth:1
			},{
				xtype:'displayfield',
				allowBlank:true,
				id:'Foss_StlairPaymentPayable_StlPayableQueryInfoGrid_airPayableTotalRows_Id',
				columnWidth:.05,
				fieldLabel:writeoff.paymentPayable.i18n('foss.stl.writeoff.airPaymentPayable.totalSum'),
				labelWidth:50
			},{
				xtype:'container',
				border:false,
				html:'&nbsp;',
				columnWidth:.005
			},{
				xtype:'displayfield',
				allowBlank:true,
				id:'Foss_StlairPaymentPayable_StlPayableQueryInfoGrid_airPayableTotalAmount_Id',
				columnWidth:.1,
				fieldLabel:writeoff.paymentPayable.i18n('foss.stl.writeoff.common.amount'),
				labelWidth:50
			},{
				xtype:'container',
				border:false,
				html:'&nbsp;',
				columnWidth:.01
			},{
				xtype:'displayfield',
				allowBlank:true,
				id:'Foss_StlairPaymentPayable_StlPayableQueryInfoGrid_airPayableVerifyAmount_Id',
				columnWidth:.13,
				fieldLabel:writeoff.paymentPayable.i18n('foss.stl.writeoff.common.verifyAmount'),
				labelWidth:80
			},{
				xtype:'container',
				border:false,
				html:'&nbsp;',
				columnWidth:.05
			},{
				xtype:'displayfield',
				allowBlank:true,
				id:'Foss_StlairPaymentPayable_StlPayableQueryInfoGrid_airPayableUnverifyAmount_Id',
				columnWidth:.1,
				fieldLabel:writeoff.paymentPayable.i18n('foss.stl.writeoff.common.unverifyAmount'),
				labelWidth:80
			},{
				xtype:'container',
				border:false,
				html:'&nbsp;',
				columnWidth:.8
			},{
				xtype:'button',
				text:writeoff.paymentPayable.i18n('foss.stl.writeoff.airPaymentPayable.writeoff'),
				hidden:!writeoff.paymentPayable.isPermission('/stl-web/pay/writeoffAdvancedAndPayable.action'),
				columnWidth:.1,
				handler:function(){
				var me = this;
				writeoff.paymentPayable.writeoffAdvancedAndPayable(me)
			}
			}]
		}]
	});

	Ext.create('Ext.panel.Panel',{
		id: 'T_writeoff-airPaymentPayable_content',
		cls: "panelContentNToolbar",
		bodyCls: 'panelContentNToolbar-body',
		layout: 'auto',
		items: [StlairPaymentPayableQueryInfoTab,StlPaymentQuerInfoGrid,StlPayableQuerInfoGrid],
		renderTo: 'T_writeoff-airPaymentPayable-body'
	});
});