/**
 * 提示信息
 * @param {} message 
 * @param {} yesFn
 * @param {} noFn
 */
agency.PAReceivable.billPAReceivableConfirmAlert = function(message,yesFn,noFn){
	Ext.Msg.confirm(agency.PAReceivable.i18n('foss.stl.agency.common.alert'),message,function(o){
		if(o=='yes'){
			yesFn();
		}else{
			noFn();
		}
	});
};

agency.PAReceivable.QUERY_BYDATE = 'TD';//按客户查询
agency.PAReceivable.QUERY_RECEIVABLE_NO = 'TR';//按单号查询
agency.PAReceivable.QUERY_SOURCE_BILL_NO = 'TSB';//按单号查询

/**
 * 按日期,客户查询
 */
agency.PAReceivable.queryBillReceivableEntityParams = function(){
	var form = this.up('form').getForm();
	agency.PAReceivable.startBusinessDate = form.findField('startDate').getValue();
	agency.PAReceivable.endBusinessDate = form.findField('endDate').getValue();
	agency.PAReceivable.approveStatus = form.findField('approveStatus').getValue();
	agency.PAReceivable.customerDetialCode = form.findField('customerDetial').getValue();
	agency.PAReceivable.queryByTab = agency.PAReceivable.QUERY_BYDATE;
	if(agency.PAReceivable.startBusinessDate==null 
			|| agency.PAReceivable.startBusinessDate==''){
		Ext.Msg.alert(agency.PAReceivable.i18n('foss.stl.agency.common.alert'),
				agency.PAReceivable.i18n('foss.stl.agency.airPayAndRec.notDateNull'));
		return false;
	}

	if(agency.PAReceivable.endBusinessDate==null 
			|| agency.PAReceivable.endBusinessDate==''){
		Ext.Msg.alert(agency.PAReceivable.i18n('foss.stl.agency.common.alert'),
				agency.PAReceivable.i18n('foss.stl.agency.airPayAndRec.notDateNull'));
		return false;
	}
	
	var compareTwoDate = stl.compareTwoDate(agency.PAReceivable.startBusinessDate
			,agency.PAReceivable.endBusinessDate);
	if(compareTwoDate>stl.DATELIMITDAYS_MONTH){
		Ext.Msg.alert(agency.PAReceivable.i18n('foss.stl.agency.common.alert'),'你好，您所查询的时间段已超过'+stl.DATELIMITDAYS_MONTH+'天，请重新输入结束时间');
		return false;
	}else if(compareTwoDate<1){
		Ext.Msg.alert(agency.PAReceivable.i18n('foss.stl.agency.common.alert'),'你好，结束时间不能小于开始时间，请重新输入!');
		return false;
	}
	agency.PAReceivable.customerCode = "";
	if(agency.PAReceivable.customerDetialCode != "" && agency.PAReceivable.customerDetialCode != null){
		agency.PAReceivable.customerCode = agency.PAReceivable.customerDetialCode;
	}
	Ext.getCmp('Foss_StlPAReceivable_StlReceivableQueryInfoGrid_Id').store.on('beforeload',function(store, operation, eOpts){
		Ext.apply(operation,{
			params :{
				'billReceivableAgencyVo.billReceivableAgencyDto.startBusinessDate':agency.PAReceivable.startBusinessDate,
				'billReceivableAgencyVo.billReceivableAgencyDto.endBusinessDate':agency.PAReceivable.endBusinessDate,
				'billReceivableAgencyVo.billReceivableAgencyDto.approveStatus':agency.PAReceivable.approveStatus,
				'billReceivableAgencyVo.billReceivableAgencyDto.customerCode':agency.PAReceivable.customerCode,
				'billReceivableAgencyVo.billReceivableAgencyDto.queryByTab':agency.PAReceivable.queryByTab
			}
		});	
	});
	if(this.up('form').getForm().isValid()){
		Ext.getCmp('Foss_StlPAReceivable_StlReceivableQueryInfoGrid_Id').store.loadPage(1,{
			callback: function(records, operation, success) {
				var rawData = Ext.getCmp('Foss_StlPAReceivable_StlReceivableQueryInfoGrid_Id').store.proxy.reader.rawData;
				//当success:false ,isException:false  --业务异常
			    if(!success && ! rawData.isException){
 					Ext.Msg.alert(agency.PAReceivable.i18n('foss.stl.agency.common.alert'),rawData.message);
 					return false;
 				}
			    if(success){
					var result = Ext.decode(operation.response.responseText);  
    			    Ext.getCmp('Foss_StlPAReceivable_StlPAReceivableQueryInfoGrid_PAReceivableTotalRows_Id').setValue(result.billReceivableAgencyVo.billReceivableAgencyDto.totalNumRec);
    				Ext.getCmp('Foss_StlPAReceivable_StlPAReceivableQueryInfoGrid_PAReceivableTotalAmount_Id').setValue(result.billReceivableAgencyVo.billReceivableAgencyDto.totalAmountRec);
    				Ext.getCmp('Foss_StlPAReceivable_StlPAReceivableQueryInfoGrid_PAReceivableVerifyAmount_Id').setValue(result.billReceivableAgencyVo.billReceivableAgencyDto.verifyTotalAmountRec);
    				Ext.getCmp('Foss_StlPAReceivable_StlPAReceivableQueryInfoGrid_PAReceivableUnverifyAmount_Id').setValue(result.billReceivableAgencyVo.billReceivableAgencyDto.unverifyTotalAmountRec);
			    }
			}
			
		});
		
	}else{
		Ext.Msg.alert(agency.PAReceivable.i18n('foss.stl.agency.common.alert'),
				agency.PAReceivable.i18n('foss.stl.agency.airPayAndRec.pageCheck'));
		return false;
	}	
}

/**
 * 按应收单号查询
 * @returns {Boolean}
 */
agency.PAReceivable.queryByReceivableNOs = function(){
	agency.PAReceivable.receivableNo = this.up('form').getForm().findField('receivableNos').getValue();
	agency.PAReceivable.queryByTab = agency.PAReceivable.QUERY_RECEIVABLE_NO;
	//单号不能为空
	if(agency.PAReceivable.receivableNo==null ||
			agency.PAReceivable.receivableNo==''){
		Ext.Msg.alert(agency.PAReceivable.i18n('foss.stl.agency.common.alert'),
				agency.PAReceivable.i18n('foss.stl.agency.receivable.notNullReceivableNo'));
		return false;
	}
	//单号个数不能超过10个
	var receivableNosStr = new Array();
	receivableNosStr =  stl.selectReceivableNosArray(agency.PAReceivable.receivableNo);
	if(receivableNosStr.length>10){
		Ext.Msg.alert(agency.PAReceivable.i18n('foss.stl.agency.common.alert'),
				agency.PAReceivable.i18n('foss.stl.agency.airPayAndRec.notGreaterThanTen'));
		return false;
	}
	
	Ext.getCmp('Foss_StlPAReceivable_StlReceivableQueryInfoGrid_Id').store.on('beforeload',function(store, operation, eOpts){
		Ext.apply(operation,{
			params :{
				'billReceivableAgencyVo.billReceivableAgencyDto.receivableNo':agency.PAReceivable.receivableNo,
				'billReceivableAgencyVo.billReceivableAgencyDto.queryByTab':agency.PAReceivable.queryByTab
			}
		});	
	});
	if(this.up('form').getForm().isValid()){
		Ext.getCmp('Foss_StlPAReceivable_StlReceivableQueryInfoGrid_Id').store.loadPage(1,{
			callback: function(records, operation, success) {
    			var rawData = Ext.getCmp('Foss_StlPAReceivable_StlReceivableQueryInfoGrid_Id').store.proxy.reader.rawData;
    			//当success:false ,isException:false  --业务异常
    		    if(!success && ! rawData.isException){
 					Ext.Msg.alert(agency.PAReceivable.i18n('foss.stl.agency.common.alert'),rawData.message);
 					return false;
 				}
    		    if(success){
					var result = Ext.decode(operation.response.responseText);  
    			    Ext.getCmp('Foss_StlPAReceivable_StlPAReceivableQueryInfoGrid_PAReceivableTotalRows_Id').setValue(result.billReceivableAgencyVo.billReceivableAgencyDto.totalNumRec);
    				Ext.getCmp('Foss_StlPAReceivable_StlPAReceivableQueryInfoGrid_PAReceivableTotalAmount_Id').setValue(result.billReceivableAgencyVo.billReceivableAgencyDto.totalAmountRec);
    				Ext.getCmp('Foss_StlPAReceivable_StlPAReceivableQueryInfoGrid_PAReceivableVerifyAmount_Id').setValue(result.billReceivableAgencyVo.billReceivableAgencyDto.verifyTotalAmountRec);
    				Ext.getCmp('Foss_StlPAReceivable_StlPAReceivableQueryInfoGrid_PAReceivableUnverifyAmount_Id').setValue(result.billReceivableAgencyVo.billReceivableAgencyDto.unverifyTotalAmountRec);
    		    }
			}
			
		});
		
	}else{
		Ext.Msg.alert(agency.PAReceivable.i18n('foss.stl.agency.common.alert'),
				agency.PAReceivable.i18n('foss.stl.agency.airPayAndRec.pageCheck'));
		return false;
	}	
}

/**
 * 按运单号查询
 * @returns {Boolean}
 */
agency.PAReceivable.queryBySourceBillNOs = function(){
	agency.PAReceivable.sourceBillNo = this.up('form').getForm().findField('sourceBillNos').getValue();
	agency.PAReceivable.queryByTab = agency.PAReceivable.QUERY_SOURCE_BILL_NO;
	//单号不能为空
	if(agency.PAReceivable.sourceBillNo==null ||
			agency.PAReceivable.sourceBillNo==''){
		Ext.Msg.alert(agency.PAReceivable.i18n('foss.stl.agency.common.alert'),
				agency.PAReceivable.i18n('foss.stl.agency.airPayAndRec.notNullSourceBillNo'));
		return false;
	}
	//单号个数不能超过10个
	var receivableNosStr = new Array();
	receivableNosStr = stl.selectWaybillNosArray(agency.PAReceivable.sourceBillNo);
	if(receivableNosStr.length>10){
		Ext.Msg.alert(agency.PAReceivable.i18n('foss.stl.agency.common.alert'),
				agency.PAReceivable.i18n('foss.stl.agency.airPayAndRec.notGreaterThanTen'));
		return false;
	}
	
	Ext.getCmp('Foss_StlPAReceivable_StlReceivableQueryInfoGrid_Id').store.on('beforeload',function(store, operation, eOpts){
		Ext.apply(operation,{
			params :{
				'billReceivableAgencyVo.billReceivableAgencyDto.sourceBillNo':agency.PAReceivable.sourceBillNo,
				'billReceivableAgencyVo.billReceivableAgencyDto.queryByTab':agency.PAReceivable.queryByTab
			}
		});	
	});

	if(this.up('form').getForm().isValid()){
		Ext.getCmp('Foss_StlPAReceivable_StlReceivableQueryInfoGrid_Id').store.loadPage(1,{
			callback: function(records, operation, success) {
    			var rawData = Ext.getCmp('Foss_StlPAReceivable_StlReceivableQueryInfoGrid_Id').store.proxy.reader.rawData;
    			//当success:false ,isException:false  --业务异常
    		    if(!success && ! rawData.isException){
 					Ext.Msg.alert(agency.PAReceivable.i18n('foss.stl.agency.common.alert'),rawData.message);
 					return false;
 				}
    		    if(success){
    		    	var result = Ext.decode(operation.response.responseText); 
    			    Ext.getCmp('Foss_StlPAReceivable_StlPAReceivableQueryInfoGrid_PAReceivableTotalRows_Id').setValue(result.billReceivableAgencyVo.billReceivableAgencyDto.totalNumRec);
    				Ext.getCmp('Foss_StlPAReceivable_StlPAReceivableQueryInfoGrid_PAReceivableTotalAmount_Id').setValue(result.billReceivableAgencyVo.billReceivableAgencyDto.totalAmountRec);
    				Ext.getCmp('Foss_StlPAReceivable_StlPAReceivableQueryInfoGrid_PAReceivableVerifyAmount_Id').setValue(result.billReceivableAgencyVo.billReceivableAgencyDto.verifyTotalAmountRec);
    				Ext.getCmp('Foss_StlPAReceivable_StlPAReceivableQueryInfoGrid_PAReceivableUnverifyAmount_Id').setValue(result.billReceivableAgencyVo.billReceivableAgencyDto.unverifyTotalAmountRec);
    		    }
			}
			
		});
		
	}else{
		Ext.Msg.alert(agency.PAReceivable.i18n('foss.stl.agency.common.alert'),
				agency.PAReceivable.i18n('foss.stl.agency.airPayAndRec.pageCheck'));
		return false;
	}	
}

//导出偏线其他应收单
agency.PAReceivable.PAReceivableListExport = function(){
	//获取表格
	grid = Ext.getCmp('Foss_StlPAReceivable_StlReceivableQueryInfoGrid_Id');
	//如果没有查询到数据，则不能进行导出操作
	if(grid.store.data.length == 0){
		Ext.Msg.alert(agency.PAReceivable.i18n('foss.stl.agency.common.alert'),
				agency.PAReceivable.i18n('foss.stl.agency.airPayAndRec.exportResultSetNotnull'));
		return false;
	}
	
	if(!Ext.fly('downloadAttachFileForm')){
	    var frm = document.createElement('form');
	    frm.id = 'downloadAttachFileForm';
	    frm.style.display = 'none';
	    document.body.appendChild(frm);
	}
	if(agency.PAReceivable.queryByTab == agency.PAReceivable.QUERY_BYDATE){
		searchParams = {
				'billReceivableAgencyVo.billReceivableAgencyDto.startBusinessDate':agency.PAReceivable.startBusinessDate,
				'billReceivableAgencyVo.billReceivableAgencyDto.endBusinessDate':agency.PAReceivable.endBusinessDate,
				'billReceivableAgencyVo.billReceivableAgencyDto.approveStatus':agency.PAReceivable.approveStatus,
				'billReceivableAgencyVo.billReceivableAgencyDto.customerCode':agency.PAReceivable.customerCode,
				'billReceivableAgencyVo.billReceivableAgencyDto.queryByTab':agency.PAReceivable.queryByTab
		}
	}
	else if(agency.PAReceivable.queryByTab == agency.PAReceivable.QUERY_RECEIVABLE_NO){
		searchParams = {
				'billReceivableAgencyVo.billReceivableAgencyDto.receivableNo':agency.PAReceivable.receivableNo,
				'billReceivableAgencyVo.billReceivableAgencyDto.queryByTab':agency.PAReceivable.queryByTab
		}
	}
	else if(agency.PAReceivable.queryByTab == agency.PAReceivable.QUERY_SOURCE_BILL_NO){
		searchParams = {
				'billReceivableAgencyVo.billReceivableAgencyDto.sourceBillNo':agency.PAReceivable.sourceBillNo,
				'billReceivableAgencyVo.billReceivableAgencyDto.queryByTab':agency.PAReceivable.queryByTab
		}
	}
	var yesFn=function(){
		Ext.Ajax.request({
  		url:agency.realPath('PAReceivableListExport.action'),
  		form: Ext.fly('downloadAttachFileForm'),
  		params :searchParams,
		isUpload: true,
  		success:function(response){
  			//如果异常信息有值，则弹出提示
			var result = Ext.decode(response.responseText);	
  	    	if(!Ext.isEmpty(result.errorMessage)){
  	    		Ext.Msg.alert(agency.PAReceivable.i18n('foss.stl.agency.common.alert'),result.errorMessage);
  	    		return false;
  	    	}
  	    	Ext.ux.Toast.msg(agency.PAReceivable.i18n('foss.stl.agency.common.alert'),
  	    			agency.PAReceivable.i18n('foss.stl.agency.airPayAndRec.exportSuccess'), 'success', 1000);
  		},
  		failure:function(response){
  			Ext.ux.Toast.msg(agency.PAReceivable.i18n('foss.stl.agency.common.alert'),
  					agency.PAReceivable.i18n('foss.stl.agency.airPayAndRec.exportError'), 'error', 1000);
  		}
  	});
	};
	var noFn=function(){
	 	return false;
	};
	agency.PAReceivable.billPAReceivableConfirmAlert(agency.PAReceivable.i18n('foss.stl.agency.PAPayAndRec.isExport'),yesFn,noFn);
}

agency.PAReceivable.queryCondition = function(){
	var searchParams;
	if(agency.PAReceivable.queryByTab == agency.PAReceivable.QUERY_BYDATE){
		searchParams = {
				'startBusinessDate':agency.PAReceivable.startBusinessDate,
				'endBusinessDate':agency.PAReceivable.endBusinessDate,
				'approveStatus':agency.PAReceivable.approveStatus,
				'customerCode':agency.PAReceivable.customerCode,
				'queryByTab':agency.PAReceivable.queryByTab
		}
	}
	else if(agency.PAReceivable.queryByTab == agency.PAReceivable.QUERY_RECEIVABLE_NO){
		searchParams = {
				'receivableNo':agency.PAReceivable.receivableNo,
				'queryByTab':agency.PAReceivable.queryByTab
		}
	}
	else if(agency.PAReceivable.queryByTab == agency.PAReceivable.QUERY_SOURCE_BILL_NO){
		searchParams = {
				'sourceBillNo':agency.PAReceivable.sourceBillNo,
				'queryByTab':agency.PAReceivable.queryByTab
		}
	}
	return searchParams;
}

//偏线其他应收管理单条作废
agency.PAReceivable.writeBackPAOtherOneReceivable = function(grid, rowIndex, colIndex){
	var selectionReceivable = grid.getStore().getAt(rowIndex);
	var jsonDataReceivable = new Array();
	jsonDataReceivable.push(selectionReceivable.data);
	var entity = new Object();
	entity.billReceivableAgencyDtos = jsonDataReceivable;
	
	var searchParams = agency.PAReceivable.queryCondition();
	
	entity.billReceivableAgencyDto = searchParams;
	
	var yesFn=function(){
    	Ext.Ajax.request({
    		url:agency.realPath('writeBackPAOtherBillReceivable.action'),
    		jsonData:{'billReceivableAgencyVo':entity},
    		method:'post',
    		success:function(response){
    			Ext.getCmp('Foss_StlPAReceivable_StlReceivableQueryInfoGrid_Id').store.load({
    				callback: function(records, operation, success) {
    				     var result =   Ext.decode(operation.response.responseText);  
    				     if(result.isException){
    	 					Ext.Msg.alert(agency.PAReceivable.i18n('foss.stl.agency.common.alert'),result.message);
    	 					return false;
    	 				}
    				    Ext.getCmp('Foss_StlPAReceivable_StlPAReceivableQueryInfoGrid_PAReceivableTotalRows_Id').setValue(result.billReceivableAgencyVo.billReceivableAgencyDto.totalNumRec);
    					Ext.getCmp('Foss_StlPAReceivable_StlPAReceivableQueryInfoGrid_PAReceivableTotalAmount_Id').setValue(result.billReceivableAgencyVo.billReceivableAgencyDto.totalAmountRec);
    					Ext.getCmp('Foss_StlPAReceivable_StlPAReceivableQueryInfoGrid_PAReceivableVerifyAmount_Id').setValue(result.billReceivableAgencyVo.billReceivableAgencyDto.verifyTotalAmountRec);
    					Ext.getCmp('Foss_StlPAReceivable_StlPAReceivableQueryInfoGrid_PAReceivableUnverifyAmount_Id').setValue(result.billReceivableAgencyVo.billReceivableAgencyDto.unverifyTotalAmountRec);			
    				}
    				
    			});
    			Ext.Msg.alert(agency.PAReceivable.i18n('foss.stl.agency.common.alert'),
    					agency.PAReceivable.i18n('foss.stl.agency.airPayAndRec.disableSuccess'));
    		},
    		exception:function(response){
    			var result = Ext.decode(response.responseText);	
    			Ext.Msg.alert(agency.PAReceivable.i18n('foss.stl.agency.common.alert'),result.message);
    		},
    		failure:function(response){
    			var result = Ext.decode(response.responseText);
    		}
    	});
	};
	var noFn=function(){
	 	return false;
	};
	agency.PAReceivable.billPAReceivableConfirmAlert(agency.PAReceivable.i18n('foss.stl.agency.airPayAndRec.isDisable'),yesFn,noFn);
}

//偏线其他应收管理作废
agency.PAReceivable.writeBackPAOtherBillReceivable = function(){
	
	var selectionReceivable = Ext.getCmp('Foss_StlPAReceivable_StlReceivableQueryInfoGrid_Id').getSelectionModel().getSelection();
	
	if(selectionReceivable.length==0){
		Ext.Msg.alert(agency.PAReceivable.i18n('foss.stl.agency.common.alert'),
				agency.PAReceivable.i18n('foss.stl.agency.airPayAndRec.disableText'));
		return false;
	}
	var searchParams = agency.PAReceivable.queryCondition();
	
	var jsonDataReceivable = new Array();
	for(var i=0;i<selectionReceivable.length;i++){
		jsonDataReceivable.push(selectionReceivable[i].data);
	}
	var entity = new Object();
	entity.billReceivableAgencyDto = searchParams;
	entity.billReceivableAgencyDtos = jsonDataReceivable;
    var yesFn=function(){
    	Ext.Ajax.request({
    		url:agency.realPath('writeBackPAOtherBillReceivable.action'),
    		jsonData:{'billReceivableAgencyVo':entity},
    		method:'post',
    		success:function(response){
    			Ext.getCmp('Foss_StlPAReceivable_StlReceivableQueryInfoGrid_Id').store.load({
    				callback: function(records, operation, success) {
    				     var result =   Ext.decode(operation.response.responseText);  
    				     if(result.isException){
    	 					Ext.Msg.alert(agency.PAReceivable.i18n('foss.stl.agency.common.alert'),result.message);
    	 					return false;
    	 				}
    				    Ext.getCmp('Foss_StlPAReceivable_StlPAReceivableQueryInfoGrid_PAReceivableTotalRows_Id').setValue(result.billReceivableAgencyVo.billReceivableAgencyDto.totalNumRec);
    					Ext.getCmp('Foss_StlPAReceivable_StlPAReceivableQueryInfoGrid_PAReceivableTotalAmount_Id').setValue(result.billReceivableAgencyVo.billReceivableAgencyDto.totalAmountRec);
    					Ext.getCmp('Foss_StlPAReceivable_StlPAReceivableQueryInfoGrid_PAReceivableVerifyAmount_Id').setValue(result.billReceivableAgencyVo.billReceivableAgencyDto.verifyTotalAmountRec);
    					Ext.getCmp('Foss_StlPAReceivable_StlPAReceivableQueryInfoGrid_PAReceivableUnverifyAmount_Id').setValue(result.billReceivableAgencyVo.billReceivableAgencyDto.unverifyTotalAmountRec);			
    				}
    				
    			});
    			Ext.Msg.alert(agency.PAReceivable.i18n('foss.stl.agency.common.alert'),
    					agency.PAReceivable.i18n('foss.stl.agency.airPayAndRec.disableSuccess'));
    		},
    		exception:function(response){
    			var result = Ext.decode(response.responseText);	
    			Ext.Msg.alert(agency.PAReceivable.i18n('foss.stl.agency.common.alert'),result.message);
    		},
    		failure:function(response){
    			var result = Ext.decode(response.responseText);
    		}
    	});
	};
	var noFn=function(){
	 	return false;
	};
	agency.PAReceivable.billPAReceivableConfirmAlert(agency.PAReceivable.i18n('foss.stl.agency.airPayAndRec.isDisable'),yesFn,noFn);
}
//单条数据审核
agency.PAReceivable.auditPAOtherOneBillReceivable = function(grid, rowIndex, colIndex){
	var selectionReceivable = grid.getStore().getAt(rowIndex);
	var jsonDataReceivable = new Array();
	jsonDataReceivable.push(selectionReceivable.data);
	var entity = new Object();
	entity.billReceivableAgencyDtos = jsonDataReceivable;
	
	var searchParams = agency.PAReceivable.queryCondition();
	
	entity.billReceivableAgencyDto = searchParams;
	var yesFn=function(){
    	Ext.Ajax.request({
    		url:agency.realPath('auditPAOtherBillReceivable.action'),
    		jsonData:{'billReceivableAgencyVo':entity},
    		method:'post',
    		success:function(response){
    			Ext.getCmp('Foss_StlPAReceivable_StlReceivableQueryInfoGrid_Id').store.load({
    				callback: function(records, operation, success) {
    				     var result =   Ext.decode(operation.response.responseText);  
    				     if(result.isException){
    	 					Ext.Msg.alert(agency.PAReceivable.i18n('foss.stl.agency.common.alert'),result.message);
    	 					return false;
    	 				}
    				    Ext.getCmp('Foss_StlPAReceivable_StlPAReceivableQueryInfoGrid_PAReceivableTotalRows_Id').setValue(result.billReceivableAgencyVo.billReceivableAgencyDto.totalNumRec);
    					Ext.getCmp('Foss_StlPAReceivable_StlPAReceivableQueryInfoGrid_PAReceivableTotalAmount_Id').setValue(result.billReceivableAgencyVo.billReceivableAgencyDto.totalAmountRec);
    					Ext.getCmp('Foss_StlPAReceivable_StlPAReceivableQueryInfoGrid_PAReceivableVerifyAmount_Id').setValue(result.billReceivableAgencyVo.billReceivableAgencyDto.verifyTotalAmountRec);
    					Ext.getCmp('Foss_StlPAReceivable_StlPAReceivableQueryInfoGrid_PAReceivableUnverifyAmount_Id').setValue(result.billReceivableAgencyVo.billReceivableAgencyDto.unverifyTotalAmountRec);			
    				}
    				
    			});
    			Ext.Msg.alert(agency.PAReceivable.i18n('foss.stl.agency.common.alert'),
    					agency.PAReceivable.i18n('foss.stl.agency.airPayAndRec.auditSuccess'));
    		},
    		exception:function(response){
    			var result = Ext.decode(response.responseText);	
    			Ext.Msg.alert(agency.PAReceivable.i18n('foss.stl.agency.common.alert'),result.message);
    		},
    		failure:function(response){
    			var result = Ext.decode(response.responseText);
    		}
    	});
	};
	var noFn=function(){
	 	return false;
	};
	agency.PAReceivable.billPAReceivableConfirmAlert(agency.PAReceivable.i18n('foss.stl.agency.airPayAndRec.isAudit'),yesFn,noFn);
}

//偏线其他管理审核
agency.PAReceivable.auditPAOtherBillReceivable = function(){
	
	var selectionReceivable = Ext.getCmp('Foss_StlPAReceivable_StlReceivableQueryInfoGrid_Id').getSelectionModel().getSelection();
	
	if(selectionReceivable.length==0){
		Ext.Msg.alert(agency.PAReceivable.i18n('foss.stl.agency.common.alert'),
				agency.PAReceivable.i18n('foss.stl.agency.airPayAndRec.auditText'));
		return false;
	}
	var searchParams = agency.PAReceivable.queryCondition();
	
	var jsonDataReceivable = new Array();
	for(var i=0;i<selectionReceivable.length;i++){
		jsonDataReceivable.push(selectionReceivable[i].data);
	}
	
	var entity = new Object();
	entity.billReceivableAgencyDto = searchParams;
	entity.billReceivableAgencyDtos = jsonDataReceivable;
	var yesFn=function(){
    	Ext.Ajax.request({
    		url:agency.realPath('auditPAOtherBillReceivable.action'),
    		jsonData:{'billReceivableAgencyVo':entity},
    		method:'post',
    		success:function(response){
    			Ext.getCmp('Foss_StlPAReceivable_StlReceivableQueryInfoGrid_Id').store.load({
    				callback: function(records, operation, success) {
    				     var result =   Ext.decode(operation.response.responseText);  
    				     if(result.isException){
    	 					Ext.Msg.alert(agency.PAReceivable.i18n('foss.stl.agency.common.alert'),result.message);
    	 					return false;
    	 				}
    				    Ext.getCmp('Foss_StlPAReceivable_StlPAReceivableQueryInfoGrid_PAReceivableTotalRows_Id').setValue(result.billReceivableAgencyVo.billReceivableAgencyDto.totalNumRec);
    					Ext.getCmp('Foss_StlPAReceivable_StlPAReceivableQueryInfoGrid_PAReceivableTotalAmount_Id').setValue(result.billReceivableAgencyVo.billReceivableAgencyDto.totalAmountRec);
    					Ext.getCmp('Foss_StlPAReceivable_StlPAReceivableQueryInfoGrid_PAReceivableVerifyAmount_Id').setValue(result.billReceivableAgencyVo.billReceivableAgencyDto.verifyTotalAmountRec);
    					Ext.getCmp('Foss_StlPAReceivable_StlPAReceivableQueryInfoGrid_PAReceivableUnverifyAmount_Id').setValue(result.billReceivableAgencyVo.billReceivableAgencyDto.unverifyTotalAmountRec);			
    				}
    				
    			});
    			Ext.Msg.alert(agency.PAReceivable.i18n('foss.stl.agency.common.alert'),
    					agency.PAReceivable.i18n('foss.stl.agency.airPayAndRec.auditSuccess'));
    		},
    		exception:function(response){
    			var result = Ext.decode(response.responseText);	
    			Ext.Msg.alert(agency.PAReceivable.i18n('foss.stl.agency.common.alert'),result.message);
    		},
    		failure:function(response){
    			var result = Ext.decode(response.responseText);
    		}
    	});
	};
	var noFn=function(){
	 	return false;
	};
	agency.PAReceivable.billPAReceivableConfirmAlert(agency.PAReceivable.i18n('foss.stl.agency.airPayAndRec.isAudit'),yesFn,noFn);
}

//单条数据反审核
agency.PAReceivable.reverseAuditPAOtherOneBillReceivable = function(grid, rowIndex, colIndex){
	var selectionReceivable = grid.getStore().getAt(rowIndex);
	var jsonDataReceivable = new Array();
	jsonDataReceivable.push(selectionReceivable.data);
	var entity = new Object();
	entity.billReceivableAgencyDtos = jsonDataReceivable;
	
	var searchParams = agency.PAReceivable.queryCondition();
	
	entity.billReceivableAgencyDto = searchParams;
	
	var yesFn=function(){
    	Ext.Ajax.request({
    		url:agency.realPath('reverseAuditPAOtherBillReceivable.action'),
    		jsonData:{'billReceivableAgencyVo':entity},
    		method:'post',
    		success:function(response){
    			Ext.getCmp('Foss_StlPAReceivable_StlReceivableQueryInfoGrid_Id').store.load({
    				callback: function(records, operation, success) {
    				     var result =   Ext.decode(operation.response.responseText);  
    				     if(result.isException){
    	 					Ext.Msg.alert(agency.PAReceivable.i18n('foss.stl.agency.common.alert'),result.message);
    	 					return false;
    	 				}
    				    Ext.getCmp('Foss_StlPAReceivable_StlPAReceivableQueryInfoGrid_PAReceivableTotalRows_Id').setValue(result.billReceivableAgencyVo.billReceivableAgencyDto.totalNumRec);
    					Ext.getCmp('Foss_StlPAReceivable_StlPAReceivableQueryInfoGrid_PAReceivableTotalAmount_Id').setValue(result.billReceivableAgencyVo.billReceivableAgencyDto.totalAmountRec);
    					Ext.getCmp('Foss_StlPAReceivable_StlPAReceivableQueryInfoGrid_PAReceivableVerifyAmount_Id').setValue(result.billReceivableAgencyVo.billReceivableAgencyDto.verifyTotalAmountRec);
    					Ext.getCmp('Foss_StlPAReceivable_StlPAReceivableQueryInfoGrid_PAReceivableUnverifyAmount_Id').setValue(result.billReceivableAgencyVo.billReceivableAgencyDto.unverifyTotalAmountRec);			
    				}
    				
    			});
    			Ext.Msg.alert(agency.PAReceivable.i18n('foss.stl.agency.common.alert'),
    					agency.PAReceivable.i18n('foss.stl.agency.airPayAndRec.reverseAuditSuccess'));
    		},
    		exception:function(response){
    			var result = Ext.decode(response.responseText);	
    			Ext.Msg.alert(agency.PAReceivable.i18n('foss.stl.agency.common.alert'),result.message);
    		},
    		failure:function(response){
    			var result = Ext.decode(response.responseText);
    		}
    	});
    };
    var noFn=function(){
     	return false;
    };
    agency.PAReceivable.billPAReceivableConfirmAlert(agency.PAReceivable.i18n('foss.stl.agency.airPayAndRec.isReverseAudit'),yesFn,noFn);
}

//偏线其他管理反审核
agency.PAReceivable.reverseAuditPAOtherBillReceivable = function(){
	var selectionReceivable = Ext.getCmp('Foss_StlPAReceivable_StlReceivableQueryInfoGrid_Id').getSelectionModel().getSelection();
	
	if(selectionReceivable.length==0){
		Ext.Msg.alert(agency.PAReceivable.i18n('foss.stl.agency.common.alert'),
				agency.PAReceivable.i18n('foss.stl.agency.airPayAndRec.reverseAuditText'));
		return false;
	}
	
	var searchParams = agency.PAReceivable.queryCondition();
	
	var jsonDataReceivable = new Array();
	for(var i=0;i<selectionReceivable.length;i++){
		jsonDataReceivable.push(selectionReceivable[i].data);
	}
	var entity = new Object();
	entity.billReceivableAgencyDto = searchParams;
	entity.billReceivableAgencyDtos = jsonDataReceivable;
	var yesFn=function(){
    	Ext.Ajax.request({
    		url:agency.realPath('reverseAuditPAOtherBillReceivable.action'),
    		jsonData:{'billReceivableAgencyVo':entity},
    		method:'post',
    		success:function(response){
    			Ext.getCmp('Foss_StlPAReceivable_StlReceivableQueryInfoGrid_Id').store.load({
    				callback: function(records, operation, success) {
    				     var result =   Ext.decode(operation.response.responseText);  
    				     if(result.isException){
    	 					Ext.Msg.alert(agency.PAReceivable.i18n('foss.stl.agency.common.alert'),result.message);
    	 					return false;
    	 				}
    				    Ext.getCmp('Foss_StlPAReceivable_StlPAReceivableQueryInfoGrid_PAReceivableTotalRows_Id').setValue(result.billReceivableAgencyVo.billReceivableAgencyDto.totalNumRec);
    					Ext.getCmp('Foss_StlPAReceivable_StlPAReceivableQueryInfoGrid_PAReceivableTotalAmount_Id').setValue(result.billReceivableAgencyVo.billReceivableAgencyDto.totalAmountRec);
    					Ext.getCmp('Foss_StlPAReceivable_StlPAReceivableQueryInfoGrid_PAReceivableVerifyAmount_Id').setValue(result.billReceivableAgencyVo.billReceivableAgencyDto.verifyTotalAmountRec);
    					Ext.getCmp('Foss_StlPAReceivable_StlPAReceivableQueryInfoGrid_PAReceivableUnverifyAmount_Id').setValue(result.billReceivableAgencyVo.billReceivableAgencyDto.unverifyTotalAmountRec);			
    				}
    				
    			});
    			Ext.Msg.alert(agency.PAReceivable.i18n('foss.stl.agency.common.alert'),
    					agency.PAReceivable.i18n('foss.stl.agency.airPayAndRec.reverseAuditSuccess'));
    		},
    		exception:function(response){
    			var result = Ext.decode(response.responseText);	
    			Ext.Msg.alert(agency.PAReceivable.i18n('foss.stl.agency.common.alert'),result.message);
    		},
    		failure:function(response){
    			var result = Ext.decode(response.responseText);
    		}
    	});
    };
    var noFn=function(){
     	return false;
    };
    agency.PAReceivable.billPAReceivableConfirmAlert(agency.PAReceivable.i18n('foss.stl.agency.airPayAndRec.isReverseAudit'),yesFn,noFn);
}


//应收单model
Ext.define('Foss.StlPAReceivable.BillReceivableEntryModel',{
	extend:'Ext.data.Model',
	fields:[{
		name:'receivableNo'
	},{
		name:'sourceBillNo'
	},{
		name:'customerCode'
	},{
		name:'customerName'
	},{
		name:'approveStatus'
	},{
		name:'origOrgCode'
	},{
		name:'origOrgName'
	},{
		name:'receivableOrgCode'
	},{
		name:'receivableOrgName'
	},{
		name:'statementBillNo'
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
		name:'businessDate',
		type:'date',
		convert:function(value) {
			return stl.longToDateConvert(value);
		}
	},{
		name:'accountDate',
		type:'date',
		convert:function(value) {
			return stl.longToDateConvert(value);
		}
	},{
		name:'auditDate',
		type:'date',
		convert:function(value) {
			return stl.longToDateConvert(value);
		} 
	},{
		name: 'notes'
	}]
});

//应收单Store
Ext.define('Foss.StlPAReceivable.BillReceivableEntryStore',{
	extend:'Ext.data.Store',
	model:'Foss.StlPAReceivable.BillReceivableEntryModel',
	proxy:{
		type:'ajax',
		url:agency.realPath('queryPAReceivablePage.action'),
		reader:{
			type:'json',
			root:'billReceivableAgencyVo.billReceivableAgencyDto.billReceivableEntityList',
			totalProperty:'totalCount'
		}
	},
	listeners:{
		'beforeLoad':function(store, operation, eOpts){
		var form = Ext.getCmp('T_agency-PAReceivable_content').getQueryInfoTab().items.items[0].items.items[0].getForm();
    		agency.PAReceivable.startBusinessDate = form.findField('startDate').getValue();
    		agency.PAReceivable.endBusinessDate = form.findField('endDate').getValue();
    		agency.PAReceivable.approveStatus = form.findField('approveStatus').getValue();
    		agency.PAReceivable.customerDetialCode = form.findField('customerDetial').getValue();
			var searchParams;
			//如果第一次进入界面，直接选择分页查询，则默认按照客户查询
			if(Ext.isEmpty(agency.PAReceivable.queryByTab)){
				
				searchParams = {
						'billReceivableAgencyVo.billReceivableAgencyDto.startBusinessDate':agency.PAReceivable.startBusinessDate,
						'billReceivableAgencyVo.billReceivableAgencyDto.endBusinessDate':agency.PAReceivable.endBusinessDate,
						'billReceivableAgencyVo.billReceivableAgencyDto.approveStatus':agency.PAReceivable.approveStatus,
						'billReceivableAgencyVo.billReceivableAgencyDto.customerCode':agency.PAReceivable.customerCode,
						'billReceivableAgencyVo.billReceivableAgencyDto.queryByTab':agency.PAReceivable.QUERY_BYDATE
				}
			}
			else if(agency.PAReceivable.queryByTab == agency.PAReceivable.QUERY_RECEIVABLE_NO){
				searchParams = {
						'billReceivableAgencyVo.billReceivableAgencyDto.receivableNo':agency.PAReceivable.receivableNo,
						'billReceivableAgencyVo.billReceivableAgencyDto.queryByTab':agency.PAReceivable.queryByTab
				}
			}
			else if(agency.PAReceivable.queryByTab == agency.PAReceivable.QUERY_SOURCE_BILL_NO){
				searchParams = {
						'billReceivableAgencyVo.billReceivableAgencyDto.sourceBillNo':agency.PAReceivable.sourceBillNo,
						'billReceivableAgencyVo.billReceivableAgencyDto.queryByTab':agency.PAReceivable.queryByTab
				}
			}
			Ext.apply(operation,{
				params :searchParams
			}); 
		}
	}
});

Ext.define('Foss.StlPAReceivable.StlPAReceivableQueryInfoTab', {
	extend:'Ext.tab.Panel',
	frame:false,
	bodyCls: 'autoHeight',
	cls: 'innerTabPanel',
	activeTab: 0,
	height : 190,
	items : [ {
		title:  agency.PAReceivable.i18n('foss.stl.agency.common.queryByDate'),
		tabConfig: {
			width: 150
		},
        items:[{
        	xtype:'form',
        	defaults:{
	        	margin:'10 0 0 5',
	        	labelWidth:85,
	        	colspan : 1
       		 },
        	layout:{
       			type :'table',
       			columns :3
       		},
		    items:[{
		    	xtype:'datefield',
		    	name:'startDate',
		    	fieldLabel:agency.PAReceivable.i18n('foss.stl.agency.airPayAndRec.startDateAgency'),
		    	value: stl.getTargetDate(new Date(),-1),
		    	editable:false,
		    	format:'Y-m-d',
		    	allowBlank:false
		    },{
		    	xtype:'datefield',
		   	 	name:'endDate',
		    	fieldLabel:agency.PAReceivable.i18n('foss.stl.agency.airPayAndRec.endDateAgency'),
		    	editable:false,
		    	format:'Y-m-d',
		    	value:new Date(),
		    	allowBlank:false,
		    	maxValue:new Date()
		    },{
				xtype :'container',
				border :false,
				html :'&nbsp;'
			},{	
	        	xtype:'commonvehagencycompselector',
				fieldLabel: agency.PAReceivable.i18n('foss.stl.agency.PAPayAndRec.PADetial'),
				name:'customerDetial',
				allowBlank:true,
				listWidth:300,//设置下拉框宽度
				isPaging:true //分页
		    },{
		    	xtype:'combobox',
		    	name:'approveStatus',
		    	fieldLabel:agency.PAReceivable.i18n('foss.stl.agency.airPayAndRec.approveStatus'),
		    	editable:false,
				store:FossDataDictionary.getDataDictionaryStore(settlementDict.BILL_RECEIVABLE__APPROVE_STATUS,null,{
					 'valueCode': '',
               		 'valueName': '全部'
				}),
				queryModel:'local',
				displayField:'valueName',
				valueField:'valueCode',
				value:''
		    },{
		    	xtype: 'container',
				border : false,
				html: '&nbsp;'
		    },{	
				border: 1,
				xtype:'container',
				colspan:3,
				defaultType:'button',
				layout:'column',
				items:[{
					text:agency.PAReceivable.i18n('foss.stl.agency.common.reset'),
					columnWidth:.15,
					handler:function(){
						this.up('form').getForm().reset();	
					}
				},{
					xtype: 'container',
					border : false,
					columnWidth:.69,
					html: '&nbsp;'
				},{
					text:agency.PAReceivable.i18n('foss.stl.agency.common.query'),
					cls:'yellow_button',
					columnWidth:.15,
					handler:agency.PAReceivable.queryBillReceivableEntityParams
				}]
		    }]
        }]
	}, {
		title: agency.PAReceivable.i18n('foss.stl.agency.airPayAndRec.queryByRecNo'),
		tabConfig: {
			width: 150
		},
        items:[{
        	xtype:'form',
        	defaults:{
	        	margin:'10 0 0 5',
	        	labelWidth:85
       		 },
        	layout:{
       			type :'table',
       			columns :3
       		},
        	 items:[{
 		    	xtype: 'textarea',
 	 			autoScroll:true,
 	 			emptyText:agency.PAReceivable.i18n('foss.stl.agency.airPayAndRec.emptyText'),
 	            fieldLabel: agency.PAReceivable.i18n('foss.stl.agency.receivable.receivableNo'),
 	            name: 'receivableNos',
 	            height : 85,
 	            width : 480,
 	            allowBlank:false,
 	            colspan:3
 		    },{
 		    	border: 1,
				xtype:'container',
				columnWidth:1,
				defaultType:'button',
				layout:'column',
				colspan:3,
				items:[{
					text:agency.PAReceivable.i18n('foss.stl.agency.common.reset'),
					columnWidth:.15,
					handler:function(){
						this.up('form').getForm().reset();
					}
				},{
					xtype: 'container',
					border : false,
					columnWidth:.7,
					html: '&nbsp;'
				},{
					text:agency.PAReceivable.i18n('foss.stl.agency.common.query'),
					cls:'yellow_button',
					columnWidth:.15,
					handler:agency.PAReceivable.queryByReceivableNOs
				}]
 		    }]
        }]
	}, {
		title: agency.PAReceivable.i18n('foss.stl.agency.common.queryBySourceNo'),
		tabConfig: {
			width: 180
		},
        items:[{
        	xtype:'form',
        	defaults:{
	        	margin:'10 0 0 5',
	        	labelWidth:85
       		 },
        	layout:{
       			type :'table',
       			columns :3
       		},
        	 items:[{
 		    	xtype: 'textarea',
 	 			autoScroll:true,
 	 			emptyText:agency.PAReceivable.i18n('foss.stl.agency.airPayAndRec.emptyText'),
 	            fieldLabel: agency.PAReceivable.i18n('foss.stl.agency.airPayAndRec.billNo'),
 	            name: 'sourceBillNos',
 	            height : 85,
 	            width : 480,
 				allowBlank:false,
 				colspan:2
 		    },{
 		    	xtype:'container',
				items:[{
					xtype:'component',
					padding:'0 0 0 10',
					autoEl:{
						tag:'div',
						html:'<span style="color:red;">'+agency.PAReceivable.i18n('foss.stl.agency.airPayAndRec.sourceBillNosWaybills')+'</span>'
					}
				}],
				colspan : 1
 		    },{
 		    	border: 1,
				xtype:'container',
				columnWidth:1,
				defaultType:'button',
				layout:'column',
				colspan:2,
				items:[{
					text:agency.PAReceivable.i18n('foss.stl.agency.common.reset'),
					columnWidth:.15,
					handler:function(){
						this.up('form').getForm().reset();
					}
				},{
					xtype: 'container',
					border : false,
					columnWidth:.7,
					html: '&nbsp;'
				},{
					text:agency.PAReceivable.i18n('foss.stl.agency.common.query'),
					cls:'yellow_button',
					columnWidth:.15,
					handler:agency.PAReceivable.queryBySourceBillNOs
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

//应收单列表
Ext.define('Foss.StlPAReceivable.StlReceivableQueryInfoGrid',{
	extend:'Ext.grid.Panel',
    title: agency.PAReceivable.i18n('foss.stl.agency.receivable.receivable'),
	frame:true,
	height:400,
	//plugins:SoperateColumnEditing,
	selModel:Ext.create('Ext.selection.CheckboxModel'),
    store: Ext.create('Foss.StlPAReceivable.BillReceivableEntryStore'),
	columns:[{ 
   		xtype:'actioncolumn',
		width:73,
		text: agency.PAReceivable.i18n('foss.stl.agency.common.actionColumn'),
		align: 'center',
		items:[{
			iconCls : 'foss_icons_stl_auditing',
			tooltip:agency.PAReceivable.i18n('foss.stl.agency.common.audit'),
			getClass:function(v,m,r,rowIndex){
    			if(r.get('approveStatus') == "AA"
    					|| !agency.PAReceivable.isPermission('/stl-web/agency/auditPAOtherBillReceivable.action')){
    				return 'statementBill_hide';
    			}else {
    				return 'foss_icons_stl_auditing';
    			}
    		},
			handler:function(grid, rowIndex, colIndex){
				agency.PAReceivable.auditPAOtherOneBillReceivable(grid, rowIndex, colIndex)	
			}
		},{
			iconCls : 'foss_icons_stl_fauditing',
			tooltip:agency.PAReceivable.i18n('foss.stl.agency.common.unAudit'),
			getClass:function(v,m,r,rowIndex){
    			if(r.get('approveStatus') == "NA"
    					|| !agency.PAReceivable.isPermission('/stl-web/agency/reverseAuditPAOtherBillReceivable.action')){
    				return 'statementBill_hide';
    			}else {
    				return 'foss_icons_stl_fauditing';
    			}
    		},
			handler:function(grid, rowIndex, colIndex){
				agency.PAReceivable.reverseAuditPAOtherOneBillReceivable(grid, rowIndex, colIndex)	
			}
		},{
			iconCls : 'deppon_icons_cancel',
			tooltip:agency.PAReceivable.i18n('foss.stl.agency.common.disable'),
			getClass:function(v,m,r,rowIndex){
    			if(!agency.PAReceivable.isPermission('/stl-web/agency/writeBackPAOtherBillReceivable.action')){
    				return 'statementBill_hide';
    			}else {
    				return 'deppon_icons_cancel';
    			}
    		},
			handler:function(grid, rowIndex, colIndex){
				agency.PAReceivable.writeBackPAOtherOneReceivable(grid, rowIndex, colIndex)	
			}
		}]
	},{
		header: agency.PAReceivable.i18n('foss.stl.agency.receivable.receivableNo'), 
		dataIndex: 'receivableNo'
	},{
		header: agency.PAReceivable.i18n('foss.stl.agency.airPayAndRec.billNo'), 
		dataIndex: 'sourceBillNo'
	},{
		header: agency.PAReceivable.i18n('foss.stl.agency.PAPayAndRec.customerCode'), 
		dataIndex: 'customerCode'		
	},{
		header: agency.PAReceivable.i18n('foss.stl.agency.PAPayAndRec.customerName'), 
		dataIndex: 'customerName'
	},{
		header: agency.PAReceivable.i18n('foss.stl.agency.airPayAndRec.approveStatus'), 
		dataIndex: 'approveStatus',
		renderer:function(value){
    		var displayField = FossDataDictionary.rendererSubmitToDisplay(value,settlementDict.BILL_RECEIVABLE__APPROVE_STATUS);
    		return displayField;
    	}
	},{
		header: agency.PAReceivable.i18n('foss.stl.agency.airPayAndRec.origOrgCode'), 
		hidden:true,
		dataIndex:'origOrgCode'
	},{
		header: agency.PAReceivable.i18n('foss.stl.agency.airPayAndRec.origOrgName'), 
		dataIndex:'origOrgName'
	},{
		header: agency.PAReceivable.i18n('foss.stl.agency.airPayAndRec.amount'), 
		dataIndex: 'amount'				
	},{
		header: agency.PAReceivable.i18n('foss.stl.agency.airPayAndRec.verifyAmount'),
		dataIndex: 'verifyAmount' 
	},{
		header: agency.PAReceivable.i18n('foss.stl.agency.airPayAndRec.unverifyAmount'), 
		dataIndex: 'unverifyAmount'				
	},{
		header: agency.PAReceivable.i18n('foss.stl.agency.airPayAndRec.businessDate'),
		dataIndex: 'businessDate',
		renderer:function(value){
			if(value!=null){
				return Ext.Date.format(new Date(value), 'Y-m-d');
			}else{
				return null;
			}
		} 
	},{
		header: agency.PAReceivable.i18n('foss.stl.agency.airPayAndRec.accountDate'),
		dataIndex: 'accountDate',
		renderer:function(value){
			if(value!=null){
				return Ext.Date.format(new Date(value), 'Y-m-d');
			}else{
				return null;
			}
		} 
	},{
		header: agency.PAReceivable.i18n('foss.stl.agency.airPayAndRec.auditDate'),
		dataIndex: 'auditDate',
		renderer:function(value){
			if(value!=null){
				return Ext.Date.format(new Date(value), 'Y-m-d');
			}else{
				return null;
			}
		} 
	},{
		header: agency.PAReceivable.i18n('foss.stl.agency.airPayAndRec.notes'), 
		dataIndex: 'notes'
	},{
		header: agency.PAReceivable.i18n('foss.stl.agency.airPayAndRec.statementBillNo'), 
		dataIndex: 'statementBillNo',
		hidden:true
	}],
	viewConfig:{
		enableTextSelection : true//设置行可以选择，进而可以复制
	},
	initComponent:function(){
		var me = this;
		me.dockedItems=[{
			xtype: 'toolbar',
			dock: 'top',
			layout:'column',
			items: [{
				xtype:'button',
				columnWidth:.1,
				text:agency.PAReceivable.i18n('foss.stl.agency.common.export'),
				disabled:!agency.PAReceivable.isPermission('/stl-web/agency/PAReceivableListExport.action'),
				hidden:!agency.PAReceivable.isPermission('/stl-web/agency/PAReceivableListExport.action'),
				handler : agency.PAReceivable.PAReceivableListExport
			}]
		},{
			xtype: 'toolbar',
			dock: 'bottom',
			layout:'column',
			items: [{
				height:5,
				columnWidth:1
			},{
				xtype:'displayfield',
				allowBlank:true,
				id:'Foss_StlPAReceivable_StlPAReceivableQueryInfoGrid_PAReceivableTotalRows_Id',
				columnWidth:.1,
				fieldLabel:agency.PAReceivable.i18n('foss.stl.agency.common.sum'),
				labelWidth:50
			},{
				xtype:'container',
				border:false,
				html:'&nbsp;',
				columnWidth:.005
			},{
				xtype:'displayfield',
				allowBlank:true,
				id:'Foss_StlPAReceivable_StlPAReceivableQueryInfoGrid_PAReceivableTotalAmount_Id',
				columnWidth:.1,
				fieldLabel:agency.PAReceivable.i18n('foss.stl.agency.common.totalAmount'),
				labelWidth:50
			},{
				xtype:'container',
				border:false,
				html:'&nbsp;',
				columnWidth:.01
			},{
				xtype:'displayfield',
				allowBlank:true,
				id:'Foss_StlPAReceivable_StlPAReceivableQueryInfoGrid_PAReceivableVerifyAmount_Id',
				columnWidth:.1,
				fieldLabel:agency.PAReceivable.i18n('foss.stl.agency.airPayAndRec.verifyAmount'),
				labelWidth:80
			},{
				xtype:'container',
				border:false,
				html:'&nbsp;',
				columnWidth:.05
			},{
				xtype:'displayfield',
				allowBlank:true,
				id:'Foss_StlPAReceivable_StlPAReceivableQueryInfoGrid_PAReceivableUnverifyAmount_Id',
				columnWidth:.1,
				fieldLabel:agency.PAReceivable.i18n('foss.stl.agency.airPayAndRec.unverifyAmount'),
				labelWidth:80
			},{
				xtype:'container',
				border:false,
				height:24,
				html:'&nbsp;',
				columnWidth:.5
			},{
				xtype:'container',
				border:false,
				html:'&nbsp;',
				columnWidth:.4
			},{
				xtype:'standardpaging',
				store:me.store,
				columnWidth:.5,
				plugins: Ext.create('Deppon.ux.PageSizePlugin', {
					//设置分页记录最大值，防止输入过大的数值
					maximumSize: 200
				})
			 },{
				xtype:'container',
				border:false,
				html:'&nbsp;',
				columnWidth:.6
			},{
				xtype:'button',
				text:agency.PAReceivable.i18n('foss.stl.agency.common.disable'),
				disabled:!agency.PAReceivable.isPermission('/stl-web/agency/writeBackPAOtherBillReceivable.action'),
				hidden:!agency.PAReceivable.isPermission('/stl-web/agency/writeBackPAOtherBillReceivable.action'),
				columnWidth:.1,
				handler:agency.PAReceivable.writeBackPAOtherBillReceivable
			},{
				xtype:'container',
				border:false,
				html:'&nbsp;',
				columnWidth:.005
			},{
				xtype:'button',
				text:agency.PAReceivable.i18n('foss.stl.agency.common.audit'),
				disabled:!agency.PAReceivable.isPermission('/stl-web/agency/auditPAOtherBillReceivable.action'),
				hidden:!agency.PAReceivable.isPermission('/stl-web/agency/auditPAOtherBillReceivable.action'),
				columnWidth:.1,
				handler:agency.PAReceivable.auditPAOtherBillReceivable
			},{
				xtype:'container',
				border:false,
				html:'&nbsp;',
				columnWidth:.005
			},{
				xtype:'button',
				text:agency.PAReceivable.i18n('foss.stl.agency.common.unAudit'),
				disabled:!agency.PAReceivable.isPermission('/stl-web/agency/reverseAuditPAOtherBillReceivable.action'),
				hidden:!agency.PAReceivable.isPermission('/stl-web/agency/reverseAuditPAOtherBillReceivable.action'),
				columnWidth:.1,
				handler:agency.PAReceivable.reverseAuditPAOtherBillReceivable
			}]
		}]
		me.callParent();
	}
});


//初始化界面
Ext.onReady(function() {
	Ext.QuickTips.init();

	var StlPAReceivableQueryInfoTab = Ext.create('Foss.StlPAReceivable.StlPAReceivableQueryInfoTab');
	
	var StlReceivableQueryInfoGrid = Ext.create('Foss.StlPAReceivable.StlReceivableQueryInfoGrid',{
		id:'Foss_StlPAReceivable_StlReceivableQueryInfoGrid_Id'
	});
	
	Ext.create('Ext.panel.Panel',{
		id: 'T_agency-PAReceivable_content',
		cls: "panelContentNToolbar",
		bodyCls: 'panelContentNToolbar-body',
		layout: 'auto',
		getQueryInfoTab:function(){
    		return StlPAReceivableQueryInfoTab;
    	},
		items: [StlPAReceivableQueryInfoTab,StlReceivableQueryInfoGrid],
		renderTo: 'T_agency-PAReceivable-body'
	});
});