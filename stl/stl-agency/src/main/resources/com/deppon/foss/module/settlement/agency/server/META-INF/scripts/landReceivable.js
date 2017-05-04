/**
 * 提示信息
 * @param {} message 
 * @param {} yesFn
 * @param {} noFn
 */
agency.landReceivable.billLandReceivableConfirmAlert = function(message,yesFn,noFn){
	Ext.Msg.confirm(agency.landReceivable.i18n('foss.stl.agency.common.alert'),message,function(o){
		if(o=='yes'){
			yesFn();
		}else{
			noFn();
		}
	});
};

agency.landReceivable.QUERY_BYDATE = 'TD';//按客户查询
agency.landReceivable.QUERY_RECEIVABLE_NO = 'TR';//按单号查询
agency.landReceivable.QUERY_SOURCE_BILL_NO = 'TSB';//按单号查询

/**
 * 按日期,客户查询
 */
agency.landReceivable.queryBillReceivableEntityParams = function(){
	var form = this.up('form').getForm();
	agency.landReceivable.startBusinessDate = form.findField('startDate').getValue();
	agency.landReceivable.endBusinessDate = form.findField('endDate').getValue();
	agency.landReceivable.approveStatus = form.findField('approveStatus').getValue();
	agency.landReceivable.customerDetialCode = form.findField('customerDetial').getValue();
	agency.landReceivable.queryByTab = agency.landReceivable.QUERY_BYDATE;
	if(agency.landReceivable.startBusinessDate==null 
			|| agency.landReceivable.startBusinessDate==''){
		Ext.Msg.alert(agency.landReceivable.i18n('foss.stl.agency.common.alert'),
				agency.landReceivable.i18n('foss.stl.agency.airPayAndRec.notDateNull'));
		return false;
	}

	if(agency.landReceivable.endBusinessDate==null 
			|| agency.landReceivable.endBusinessDate==''){
		Ext.Msg.alert(agency.landReceivable.i18n('foss.stl.agency.common.alert'),
				agency.landReceivable.i18n('foss.stl.agency.airPayAndRec.notDateNull'));
		return false;
	}
	
	var compareTwoDate = stl.compareTwoDate(agency.landReceivable.startBusinessDate
			,agency.landReceivable.endBusinessDate);
	if(compareTwoDate>stl.DATELIMITDAYS_MONTH){
		Ext.Msg.alert(agency.landReceivable.i18n('foss.stl.agency.common.alert'),'你好，您所查询的时间段已超过'+stl.DATELIMITDAYS_MONTH+'天，请重新输入结束时间');
		return false;
	}else if(compareTwoDate<1){
		Ext.Msg.alert(agency.landReceivable.i18n('foss.stl.agency.common.alert'),'你好，结束时间不能小于开始时间，请重新输入!');
		return false;
	}
	agency.landReceivable.customerCode = "";
	if(agency.landReceivable.customerDetialCode != "" && agency.landReceivable.customerDetialCode != null){
		agency.landReceivable.customerCode = agency.landReceivable.customerDetialCode;
	}
	Ext.getCmp('Foss_StllandReceivable_StlReceivableQueryInfoGrid_Id').store.on('beforeload',function(store, operation, eOpts){
		Ext.apply(operation,{
			params :{
				'billReceivableAgencyVo.billReceivableAgencyDto.startBusinessDate':agency.landReceivable.startBusinessDate,
				'billReceivableAgencyVo.billReceivableAgencyDto.endBusinessDate':agency.landReceivable.endBusinessDate,
				'billReceivableAgencyVo.billReceivableAgencyDto.approveStatus':agency.landReceivable.approveStatus,
				'billReceivableAgencyVo.billReceivableAgencyDto.customerCode':agency.landReceivable.customerCode,
				'billReceivableAgencyVo.billReceivableAgencyDto.queryByTab':agency.landReceivable.queryByTab
			}
		});	
	});
	if(this.up('form').getForm().isValid()){
		Ext.getCmp('Foss_StllandReceivable_StlReceivableQueryInfoGrid_Id').store.loadPage(1,{
			callback: function(records, operation, success) {
				var rawData = Ext.getCmp('Foss_StllandReceivable_StlReceivableQueryInfoGrid_Id').store.proxy.reader.rawData;
				//当success:false ,isException:false  --业务异常
			    if(!success && ! rawData.isException){
 					Ext.Msg.alert(agency.landReceivable.i18n('foss.stl.agency.common.alert'),rawData.message);
 					return false;
 				}
			    if(success){
					var result = Ext.decode(operation.response.responseText);  
    			    Ext.getCmp('Foss_StllandReceivable_StllandReceivableQueryInfoGrid_landReceivableTotalRows_Id').setValue(result.billReceivableAgencyVo.billReceivableAgencyDto.totalNumRec);
    				Ext.getCmp('Foss_StllandReceivable_StllandReceivableQueryInfoGrid_landReceivableTotalAmount_Id').setValue(result.billReceivableAgencyVo.billReceivableAgencyDto.totalAmountRec);
    				Ext.getCmp('Foss_StllandReceivable_StllandReceivableQueryInfoGrid_landReceivableVerifyAmount_Id').setValue(result.billReceivableAgencyVo.billReceivableAgencyDto.verifyTotalAmountRec);
    				Ext.getCmp('Foss_StllandReceivable_StllandReceivableQueryInfoGrid_landReceivableUnverifyAmount_Id').setValue(result.billReceivableAgencyVo.billReceivableAgencyDto.unverifyTotalAmountRec);
			    }
			}
			
		});
		
	}else{
		Ext.Msg.alert(agency.landReceivable.i18n('foss.stl.agency.common.alert'),
				agency.landReceivable.i18n('foss.stl.agency.airPayAndRec.pageCheck'));
		return false;
	}	
}

/**
 * 按应收单号查询
 * @returns {Boolean}
 */
agency.landReceivable.queryByReceivableNOs = function(){
	agency.landReceivable.receivableNo = this.up('form').getForm().findField('receivableNos').getValue();
	agency.landReceivable.queryByTab = agency.landReceivable.QUERY_RECEIVABLE_NO;
	//单号不能为空
	if(agency.landReceivable.receivableNo==null ||
			agency.landReceivable.receivableNo==''){
		Ext.Msg.alert(agency.landReceivable.i18n('foss.stl.agency.common.alert'),
				agency.landReceivable.i18n('foss.stl.agency.receivable.notNullReceivableNo'));
		return false;
	}
	//单号个数不能超过10个
	var receivableNosStr = new Array();
	receivableNosStr = stl.splitToArrayRemoveEmpty(agency.landReceivable.receivableNo);
	if(receivableNosStr.length>10){
		Ext.Msg.alert(agency.landReceivable.i18n('foss.stl.agency.common.alert'),
				agency.landReceivable.i18n('foss.stl.agency.airPayAndRec.notGreaterThanTen'));
		return false;
	}
	
	Ext.getCmp('Foss_StllandReceivable_StlReceivableQueryInfoGrid_Id').store.on('beforeload',function(store, operation, eOpts){
		Ext.apply(operation,{
			params :{
				'billReceivableAgencyVo.billReceivableAgencyDto.receivableNo':agency.landReceivable.receivableNo,
				'billReceivableAgencyVo.billReceivableAgencyDto.queryByTab':agency.landReceivable.queryByTab
			}
		});	
	});
	if(this.up('form').getForm().isValid()){
		Ext.getCmp('Foss_StllandReceivable_StlReceivableQueryInfoGrid_Id').store.loadPage(1,{
			callback: function(records, operation, success) {
    			var rawData = Ext.getCmp('Foss_StllandReceivable_StlReceivableQueryInfoGrid_Id').store.proxy.reader.rawData;
    			//当success:false ,isException:false  --业务异常
    		    if(!success && ! rawData.isException){
 					Ext.Msg.alert(agency.landReceivable.i18n('foss.stl.agency.common.alert'),rawData.message);
 					return false;
 				}
    		    if(success){
					var result = Ext.decode(operation.response.responseText);  
    			    Ext.getCmp('Foss_StllandReceivable_StllandReceivableQueryInfoGrid_landReceivableTotalRows_Id').setValue(result.billReceivableAgencyVo.billReceivableAgencyDto.totalNumRec);
    				Ext.getCmp('Foss_StllandReceivable_StllandReceivableQueryInfoGrid_landReceivableTotalAmount_Id').setValue(result.billReceivableAgencyVo.billReceivableAgencyDto.totalAmountRec);
    				Ext.getCmp('Foss_StllandReceivable_StllandReceivableQueryInfoGrid_landReceivableVerifyAmount_Id').setValue(result.billReceivableAgencyVo.billReceivableAgencyDto.verifyTotalAmountRec);
    				Ext.getCmp('Foss_StllandReceivable_StllandReceivableQueryInfoGrid_landReceivableUnverifyAmount_Id').setValue(result.billReceivableAgencyVo.billReceivableAgencyDto.unverifyTotalAmountRec);
    		    }
			}
			
		});
		
	}else{
		Ext.Msg.alert(agency.landReceivable.i18n('foss.stl.agency.common.alert'),
				agency.landReceivable.i18n('foss.stl.agency.airPayAndRec.pageCheck'));
		return false;
	}	
}

/**
 * 按航空正单号查询
 * @returns {Boolean}
 */
agency.landReceivable.queryBySourceBillNOs = function(){
	agency.landReceivable.sourceBillNo = this.up('form').getForm().findField('sourceBillNos').getValue();
	agency.landReceivable.queryByTab = agency.landReceivable.QUERY_SOURCE_BILL_NO;
	//单号不能为空
	if(agency.landReceivable.sourceBillNo==null ||
			agency.landReceivable.sourceBillNo==''){
		Ext.Msg.alert(agency.landReceivable.i18n('foss.stl.agency.common.alert'),
				agency.landReceivable.i18n('foss.stl.agency.airPayAndRec.notNullSourceBillNo'));
		return false;
	}
	//单号个数不能超过10个
	var receivableNosStr = new Array();
	receivableNosStr = stl.splitToArrayRemoveEmpty(agency.landReceivable.sourceBillNo);
	if(receivableNosStr.length>10){
		Ext.Msg.alert(agency.landReceivable.i18n('foss.stl.agency.common.alert'),
				agency.landReceivable.i18n('foss.stl.agency.airPayAndRec.notGreaterThanTen'));
		return false;
	}
	
	Ext.getCmp('Foss_StllandReceivable_StlReceivableQueryInfoGrid_Id').store.on('beforeload',function(store, operation, eOpts){
		Ext.apply(operation,{
			params :{
				'billReceivableAgencyVo.billReceivableAgencyDto.sourceBillNo':agency.landReceivable.sourceBillNo,
				'billReceivableAgencyVo.billReceivableAgencyDto.queryByTab':agency.landReceivable.queryByTab
			}
		});	
	});

	if(this.up('form').getForm().isValid()){
		Ext.getCmp('Foss_StllandReceivable_StlReceivableQueryInfoGrid_Id').store.loadPage(1,{
			callback: function(records, operation, success) {
    			var rawData = Ext.getCmp('Foss_StllandReceivable_StlReceivableQueryInfoGrid_Id').store.proxy.reader.rawData;
    			//当success:false ,isException:false  --业务异常
    		    if(!success && ! rawData.isException){
 					Ext.Msg.alert(agency.landReceivable.i18n('foss.stl.agency.common.alert'),rawData.message);
 					return false;
 				}
    		    if(success){
    		    	var result = Ext.decode(operation.response.responseText); 
    			    Ext.getCmp('Foss_StllandReceivable_StllandReceivableQueryInfoGrid_landReceivableTotalRows_Id').setValue(result.billReceivableAgencyVo.billReceivableAgencyDto.totalNumRec);
    				Ext.getCmp('Foss_StllandReceivable_StllandReceivableQueryInfoGrid_landReceivableTotalAmount_Id').setValue(result.billReceivableAgencyVo.billReceivableAgencyDto.totalAmountRec);
    				Ext.getCmp('Foss_StllandReceivable_StllandReceivableQueryInfoGrid_landReceivableVerifyAmount_Id').setValue(result.billReceivableAgencyVo.billReceivableAgencyDto.verifyTotalAmountRec);
    				Ext.getCmp('Foss_StllandReceivable_StllandReceivableQueryInfoGrid_landReceivableUnverifyAmount_Id').setValue(result.billReceivableAgencyVo.billReceivableAgencyDto.unverifyTotalAmountRec);
    		    }
			}
			
		});
		
	}else{
		Ext.Msg.alert(agency.landReceivable.i18n('foss.stl.agency.common.alert'),
				agency.landReceivable.i18n('foss.stl.agency.airPayAndRec.pageCheck'));
		return false;
	}	
}

//导出快递代理其他应收单
agency.landReceivable.landReceivableListExport = function(){
	//获取表格
	grid = Ext.getCmp('Foss_StllandReceivable_StlReceivableQueryInfoGrid_Id');
	//如果没有查询到数据，则不能进行导出操作
	if(grid.store.data.length == 0){
		Ext.Msg.alert(agency.landReceivable.i18n('foss.stl.agency.common.alert'),
				agency.landReceivable.i18n('foss.stl.agency.airPayAndRec.exportResultSetNotnull'));
		return false;
	}
	
	if(!Ext.fly('downloadAttachFileForm')){
	    var frm = document.createElement('form');
	    frm.id = 'downloadAttachFileForm';
	    frm.style.display = 'none';
	    document.body.appendChild(frm);
	}
	if(agency.landReceivable.queryByTab == agency.landReceivable.QUERY_BYDATE){
		searchParams = {
				'billReceivableAgencyVo.billReceivableAgencyDto.startBusinessDate':agency.landReceivable.startBusinessDate,
				'billReceivableAgencyVo.billReceivableAgencyDto.endBusinessDate':agency.landReceivable.endBusinessDate,
				'billReceivableAgencyVo.billReceivableAgencyDto.approveStatus':agency.landReceivable.approveStatus,
				'billReceivableAgencyVo.billReceivableAgencyDto.customerCode':agency.landReceivable.customerCode,
				'billReceivableAgencyVo.billReceivableAgencyDto.queryByTab':agency.landReceivable.queryByTab
		}
	}
	else if(agency.landReceivable.queryByTab == agency.landReceivable.QUERY_RECEIVABLE_NO){
		searchParams = {
				'billReceivableAgencyVo.billReceivableAgencyDto.receivableNo':agency.landReceivable.receivableNo,
				'billReceivableAgencyVo.billReceivableAgencyDto.queryByTab':agency.landReceivable.queryByTab
		}
	}
	else if(agency.landReceivable.queryByTab == agency.landReceivable.QUERY_SOURCE_BILL_NO){
		searchParams = {
				'billReceivableAgencyVo.billReceivableAgencyDto.sourceBillNo':agency.landReceivable.sourceBillNo,
				'billReceivableAgencyVo.billReceivableAgencyDto.queryByTab':agency.landReceivable.queryByTab
		}
	}
	var yesFn=function(){
		Ext.Ajax.request({
  		url:agency.realPath('landReceivableListExport.action'),
  		form: Ext.fly('downloadAttachFileForm'),
  		params :searchParams,
		isUpload: true,
  		success:function(response){
  			//如果异常信息有值，则弹出提示
			var result = Ext.decode(response.responseText);	
  	    	if(!Ext.isEmpty(result.errorMessage)){
  	    		Ext.Msg.alert(agency.landReceivable.i18n('foss.stl.agency.common.alert'),result.errorMessage);
  	    		return false;
  	    	}
  	    	Ext.ux.Toast.msg(agency.landReceivable.i18n('foss.stl.agency.common.alert'),
  	    			agency.landReceivable.i18n('foss.stl.agency.airPayAndRec.exportSuccess'), 'success', 1000);
  		},
  		failure:function(response){
  			Ext.ux.Toast.msg(agency.landReceivable.i18n('foss.stl.agency.common.alert'),
  					agency.landReceivable.i18n('foss.stl.agency.airPayAndRec.exportError'), 'error', 1000);
  		}
  	});
	};
	var noFn=function(){
	 	return false;
	};
	agency.landReceivable.billLandReceivableConfirmAlert(agency.landReceivable.i18n('foss.stl.agency.landPayAndRec.isExport'),yesFn,noFn);
}

agency.landReceivable.queryCondition = function(){
	var searchParams;
	if(agency.landReceivable.queryByTab == agency.landReceivable.QUERY_BYDATE){
		searchParams = {
				'startBusinessDate':agency.landReceivable.startBusinessDate,
				'endBusinessDate':agency.landReceivable.endBusinessDate,
				'approveStatus':agency.landReceivable.approveStatus,
				'customerCode':agency.landReceivable.customerCode,
				'queryByTab':agency.landReceivable.queryByTab
		}
	}
	else if(agency.landReceivable.queryByTab == agency.landReceivable.QUERY_RECEIVABLE_NO){
		searchParams = {
				'receivableNo':agency.landReceivable.receivableNo,
				'queryByTab':agency.landReceivable.queryByTab
		}
	}
	else if(agency.landReceivable.queryByTab == agency.landReceivable.QUERY_SOURCE_BILL_NO){
		searchParams = {
				'sourceBillNo':agency.landReceivable.sourceBillNo,
				'queryByTab':agency.landReceivable.queryByTab
		}
	}
	return searchParams;
}

//快递代理其他应收管理单条作废
agency.landReceivable.writeBackLandOtherOneReceivable = function(grid, rowIndex, colIndex){
	var selectionReceivable = grid.getStore().getAt(rowIndex);
	var jsonDataReceivable = new Array();
	jsonDataReceivable.push(selectionReceivable.data);
	var entity = new Object();
	entity.billReceivableAgencyDtos = jsonDataReceivable;
	
	var searchParams = agency.landReceivable.queryCondition();
	
	entity.billReceivableAgencyDto = searchParams;
	
	var yesFn=function(){
    	Ext.Ajax.request({
    		url:agency.realPath('writeBackLandOtherBillReceivable.action'),
    		jsonData:{'billReceivableAgencyVo':entity},
    		method:'post',
    		success:function(response){
    			Ext.getCmp('Foss_StllandReceivable_StlReceivableQueryInfoGrid_Id').store.load({
    				callback: function(records, operation, success) {
    				     var result =   Ext.decode(operation.response.responseText);  
    				     if(result.isException){
    	 					Ext.Msg.alert(agency.landReceivable.i18n('foss.stl.agency.common.alert'),result.message);
    	 					return false;
    	 				}
    				    Ext.getCmp('Foss_StllandReceivable_StllandReceivableQueryInfoGrid_landReceivableTotalRows_Id').setValue(result.billReceivableAgencyVo.billReceivableAgencyDto.totalNumRec);
    					Ext.getCmp('Foss_StllandReceivable_StllandReceivableQueryInfoGrid_landReceivableTotalAmount_Id').setValue(result.billReceivableAgencyVo.billReceivableAgencyDto.totalAmountRec);
    					Ext.getCmp('Foss_StllandReceivable_StllandReceivableQueryInfoGrid_landReceivableVerifyAmount_Id').setValue(result.billReceivableAgencyVo.billReceivableAgencyDto.verifyTotalAmountRec);
    					Ext.getCmp('Foss_StllandReceivable_StllandReceivableQueryInfoGrid_landReceivableUnverifyAmount_Id').setValue(result.billReceivableAgencyVo.billReceivableAgencyDto.unverifyTotalAmountRec);			
    				}
    				
    			});
    			Ext.Msg.alert(agency.landReceivable.i18n('foss.stl.agency.common.alert'),
    					agency.landReceivable.i18n('foss.stl.agency.airPayAndRec.disableSuccess'));
    		},
    		exception:function(response){
    			var result = Ext.decode(response.responseText);	
    			Ext.Msg.alert(agency.landReceivable.i18n('foss.stl.agency.common.alert'),result.message);
    		},
    		failure:function(response){
    			var result = Ext.decode(response.responseText);
    		}
    	});
	};
	var noFn=function(){
	 	return false;
	};
	agency.landReceivable.billLandReceivableConfirmAlert(agency.landReceivable.i18n('foss.stl.agency.airPayAndRec.isDisable'),yesFn,noFn);
}

//快递代理其他应收管理作废
agency.landReceivable.writeBackLandOtherBillReceivable = function(){
	
	var selectionReceivable = Ext.getCmp('Foss_StllandReceivable_StlReceivableQueryInfoGrid_Id').getSelectionModel().getSelection();
	
	if(selectionReceivable.length==0){
		Ext.Msg.alert(agency.landReceivable.i18n('foss.stl.agency.common.alert'),
				agency.landReceivable.i18n('foss.stl.agency.airPayAndRec.disableText'));
		return false;
	}
	var searchParams = agency.landReceivable.queryCondition();
	
	var jsonDataReceivable = new Array();
	for(var i=0;i<selectionReceivable.length;i++){
		jsonDataReceivable.push(selectionReceivable[i].data);
	}
	var entity = new Object();
	entity.billReceivableAgencyDto = searchParams;
	entity.billReceivableAgencyDtos = jsonDataReceivable;
    var yesFn=function(){
    	Ext.Ajax.request({
    		url:agency.realPath('writeBackLandOtherBillReceivable.action'),
    		jsonData:{'billReceivableAgencyVo':entity},
    		method:'post',
    		success:function(response){
    			Ext.getCmp('Foss_StllandReceivable_StlReceivableQueryInfoGrid_Id').store.load({
    				callback: function(records, operation, success) {
    				     var result =   Ext.decode(operation.response.responseText);  
    				     if(result.isException){
    	 					Ext.Msg.alert(agency.landReceivable.i18n('foss.stl.agency.common.alert'),result.message);
    	 					return false;
    	 				}
    				    Ext.getCmp('Foss_StllandReceivable_StllandReceivableQueryInfoGrid_landReceivableTotalRows_Id').setValue(result.billReceivableAgencyVo.billReceivableAgencyDto.totalNumRec);
    					Ext.getCmp('Foss_StllandReceivable_StllandReceivableQueryInfoGrid_landReceivableTotalAmount_Id').setValue(result.billReceivableAgencyVo.billReceivableAgencyDto.totalAmountRec);
    					Ext.getCmp('Foss_StllandReceivable_StllandReceivableQueryInfoGrid_landReceivableVerifyAmount_Id').setValue(result.billReceivableAgencyVo.billReceivableAgencyDto.verifyTotalAmountRec);
    					Ext.getCmp('Foss_StllandReceivable_StllandReceivableQueryInfoGrid_landReceivableUnverifyAmount_Id').setValue(result.billReceivableAgencyVo.billReceivableAgencyDto.unverifyTotalAmountRec);			
    				}
    				
    			});
    			Ext.Msg.alert(agency.landReceivable.i18n('foss.stl.agency.common.alert'),
    					agency.landReceivable.i18n('foss.stl.agency.airPayAndRec.disableSuccess'));
    		},
    		exception:function(response){
    			var result = Ext.decode(response.responseText);	
    			Ext.Msg.alert(agency.landReceivable.i18n('foss.stl.agency.common.alert'),result.message);
    		},
    		failure:function(response){
    			var result = Ext.decode(response.responseText);
    		}
    	});
	};
	var noFn=function(){
	 	return false;
	};
	agency.landReceivable.billLandReceivableConfirmAlert(agency.landReceivable.i18n('foss.stl.agency.airPayAndRec.isDisable'),yesFn,noFn);
}
//单条数据审核
agency.landReceivable.auditLandOtherOneBillReceivable = function(grid, rowIndex, colIndex){
	var selectionReceivable = grid.getStore().getAt(rowIndex);
	var jsonDataReceivable = new Array();
	jsonDataReceivable.push(selectionReceivable.data);
	var entity = new Object();
	entity.billReceivableAgencyDtos = jsonDataReceivable;
	
	var searchParams = agency.landReceivable.queryCondition();
	
	entity.billReceivableAgencyDto = searchParams;
	var yesFn=function(){
    	Ext.Ajax.request({
    		url:agency.realPath('auditLandOtherBillReceivable.action'),
    		jsonData:{'billReceivableAgencyVo':entity},
    		method:'post',
    		success:function(response){
    			Ext.getCmp('Foss_StllandReceivable_StlReceivableQueryInfoGrid_Id').store.load({
    				callback: function(records, operation, success) {
    				     var result =   Ext.decode(operation.response.responseText);  
    				     if(result.isException){
    	 					Ext.Msg.alert(agency.landReceivable.i18n('foss.stl.agency.common.alert'),result.message);
    	 					return false;
    	 				}
    				    Ext.getCmp('Foss_StllandReceivable_StllandReceivableQueryInfoGrid_landReceivableTotalRows_Id').setValue(result.billReceivableAgencyVo.billReceivableAgencyDto.totalNumRec);
    					Ext.getCmp('Foss_StllandReceivable_StllandReceivableQueryInfoGrid_landReceivableTotalAmount_Id').setValue(result.billReceivableAgencyVo.billReceivableAgencyDto.totalAmountRec);
    					Ext.getCmp('Foss_StllandReceivable_StllandReceivableQueryInfoGrid_landReceivableVerifyAmount_Id').setValue(result.billReceivableAgencyVo.billReceivableAgencyDto.verifyTotalAmountRec);
    					Ext.getCmp('Foss_StllandReceivable_StllandReceivableQueryInfoGrid_landReceivableUnverifyAmount_Id').setValue(result.billReceivableAgencyVo.billReceivableAgencyDto.unverifyTotalAmountRec);			
    				}
    				
    			});
    			Ext.Msg.alert(agency.landReceivable.i18n('foss.stl.agency.common.alert'),
    					agency.landReceivable.i18n('foss.stl.agency.airPayAndRec.auditSuccess'));
    		},
    		exception:function(response){
    			var result = Ext.decode(response.responseText);	
    			Ext.Msg.alert(agency.landReceivable.i18n('foss.stl.agency.common.alert'),result.message);
    		},
    		failure:function(response){
    			var result = Ext.decode(response.responseText);
    		}
    	});
	};
	var noFn=function(){
	 	return false;
	};
	agency.landReceivable.billLandReceivableConfirmAlert(agency.landReceivable.i18n('foss.stl.agency.airPayAndRec.isAudit'),yesFn,noFn);
}

//快递代理其他管理审核
agency.landReceivable.auditLandOtherBillReceivable = function(){
	
	var selectionReceivable = Ext.getCmp('Foss_StllandReceivable_StlReceivableQueryInfoGrid_Id').getSelectionModel().getSelection();
	
	if(selectionReceivable.length==0){
		Ext.Msg.alert(agency.landReceivable.i18n('foss.stl.agency.common.alert'),
				agency.landReceivable.i18n('foss.stl.agency.airPayAndRec.auditText'));
		return false;
	}
	var searchParams = agency.landReceivable.queryCondition();
	
	var jsonDataReceivable = new Array();
	for(var i=0;i<selectionReceivable.length;i++){
		jsonDataReceivable.push(selectionReceivable[i].data);
	}
	
	var entity = new Object();
	entity.billReceivableAgencyDto = searchParams;
	entity.billReceivableAgencyDtos = jsonDataReceivable;
	var yesFn=function(){
    	Ext.Ajax.request({
    		url:agency.realPath('auditLandOtherBillReceivable.action'),
    		jsonData:{'billReceivableAgencyVo':entity},
    		method:'post',
    		success:function(response){
    			Ext.getCmp('Foss_StllandReceivable_StlReceivableQueryInfoGrid_Id').store.load({
    				callback: function(records, operation, success) {
    				     var result =   Ext.decode(operation.response.responseText);  
    				     if(result.isException){
    	 					Ext.Msg.alert(agency.landReceivable.i18n('foss.stl.agency.common.alert'),result.message);
    	 					return false;
    	 				}
    				    Ext.getCmp('Foss_StllandReceivable_StllandReceivableQueryInfoGrid_landReceivableTotalRows_Id').setValue(result.billReceivableAgencyVo.billReceivableAgencyDto.totalNumRec);
    					Ext.getCmp('Foss_StllandReceivable_StllandReceivableQueryInfoGrid_landReceivableTotalAmount_Id').setValue(result.billReceivableAgencyVo.billReceivableAgencyDto.totalAmountRec);
    					Ext.getCmp('Foss_StllandReceivable_StllandReceivableQueryInfoGrid_landReceivableVerifyAmount_Id').setValue(result.billReceivableAgencyVo.billReceivableAgencyDto.verifyTotalAmountRec);
    					Ext.getCmp('Foss_StllandReceivable_StllandReceivableQueryInfoGrid_landReceivableUnverifyAmount_Id').setValue(result.billReceivableAgencyVo.billReceivableAgencyDto.unverifyTotalAmountRec);			
    				}
    				
    			});
    			Ext.Msg.alert(agency.landReceivable.i18n('foss.stl.agency.common.alert'),
    					agency.landReceivable.i18n('foss.stl.agency.airPayAndRec.auditSuccess'));
    		},
    		exception:function(response){
    			var result = Ext.decode(response.responseText);	
    			Ext.Msg.alert(agency.landReceivable.i18n('foss.stl.agency.common.alert'),result.message);
    		},
    		failure:function(response){
    			var result = Ext.decode(response.responseText);
    		}
    	});
	};
	var noFn=function(){
	 	return false;
	};
	agency.landReceivable.billLandReceivableConfirmAlert(agency.landReceivable.i18n('foss.stl.agency.airPayAndRec.isAudit'),yesFn,noFn);
}

//单条数据反审核
agency.landReceivable.reverseAuditLandOtherOneBillReceivable = function(grid, rowIndex, colIndex){
	var selectionReceivable = grid.getStore().getAt(rowIndex);
	var jsonDataReceivable = new Array();
	jsonDataReceivable.push(selectionReceivable.data);
	var entity = new Object();
	entity.billReceivableAgencyDtos = jsonDataReceivable;
	
	var searchParams = agency.landReceivable.queryCondition();
	
	entity.billReceivableAgencyDto = searchParams;
	
	var yesFn=function(){
    	Ext.Ajax.request({
    		url:agency.realPath('reverseAuditLandOtherBillReceivable.action'),
    		jsonData:{'billReceivableAgencyVo':entity},
    		method:'post',
    		success:function(response){
    			Ext.getCmp('Foss_StllandReceivable_StlReceivableQueryInfoGrid_Id').store.load({
    				callback: function(records, operation, success) {
    				     var result =   Ext.decode(operation.response.responseText);  
    				     if(result.isException){
    	 					Ext.Msg.alert(agency.landReceivable.i18n('foss.stl.agency.common.alert'),result.message);
    	 					return false;
    	 				}
    				    Ext.getCmp('Foss_StllandReceivable_StllandReceivableQueryInfoGrid_landReceivableTotalRows_Id').setValue(result.billReceivableAgencyVo.billReceivableAgencyDto.totalNumRec);
    					Ext.getCmp('Foss_StllandReceivable_StllandReceivableQueryInfoGrid_landReceivableTotalAmount_Id').setValue(result.billReceivableAgencyVo.billReceivableAgencyDto.totalAmountRec);
    					Ext.getCmp('Foss_StllandReceivable_StllandReceivableQueryInfoGrid_landReceivableVerifyAmount_Id').setValue(result.billReceivableAgencyVo.billReceivableAgencyDto.verifyTotalAmountRec);
    					Ext.getCmp('Foss_StllandReceivable_StllandReceivableQueryInfoGrid_landReceivableUnverifyAmount_Id').setValue(result.billReceivableAgencyVo.billReceivableAgencyDto.unverifyTotalAmountRec);			
    				}
    				
    			});
    			Ext.Msg.alert(agency.landReceivable.i18n('foss.stl.agency.common.alert'),
    					agency.landReceivable.i18n('foss.stl.agency.airPayAndRec.reverseAuditSuccess'));
    		},
    		exception:function(response){
    			var result = Ext.decode(response.responseText);	
    			Ext.Msg.alert(agency.landReceivable.i18n('foss.stl.agency.common.alert'),result.message);
    		},
    		failure:function(response){
    			var result = Ext.decode(response.responseText);
    		}
    	});
    };
    var noFn=function(){
     	return false;
    };
    agency.landReceivable.billLandReceivableConfirmAlert(agency.landReceivable.i18n('foss.stl.agency.airPayAndRec.isReverseAudit'),yesFn,noFn);
}

//快递代理其他管理反审核
agency.landReceivable.reverseAuditLandOtherBillReceivable = function(){
	var selectionReceivable = Ext.getCmp('Foss_StllandReceivable_StlReceivableQueryInfoGrid_Id').getSelectionModel().getSelection();
	
	if(selectionReceivable.length==0){
		Ext.Msg.alert(agency.landReceivable.i18n('foss.stl.agency.common.alert'),
				agency.landReceivable.i18n('foss.stl.agency.airPayAndRec.reverseAuditText'));
		return false;
	}
	
	var searchParams = agency.landReceivable.queryCondition();
	
	var jsonDataReceivable = new Array();
	for(var i=0;i<selectionReceivable.length;i++){
		jsonDataReceivable.push(selectionReceivable[i].data);
	}
	var entity = new Object();
	entity.billReceivableAgencyDto = searchParams;
	entity.billReceivableAgencyDtos = jsonDataReceivable;
	var yesFn=function(){
    	Ext.Ajax.request({
    		url:agency.realPath('reverseAuditLandOtherBillReceivable.action'),
    		jsonData:{'billReceivableAgencyVo':entity},
    		method:'post',
    		success:function(response){
    			Ext.getCmp('Foss_StllandReceivable_StlReceivableQueryInfoGrid_Id').store.load({
    				callback: function(records, operation, success) {
    				     var result =   Ext.decode(operation.response.responseText);  
    				     if(result.isException){
    	 					Ext.Msg.alert(agency.landReceivable.i18n('foss.stl.agency.common.alert'),result.message);
    	 					return false;
    	 				}
    				    Ext.getCmp('Foss_StllandReceivable_StllandReceivableQueryInfoGrid_landReceivableTotalRows_Id').setValue(result.billReceivableAgencyVo.billReceivableAgencyDto.totalNumRec);
    					Ext.getCmp('Foss_StllandReceivable_StllandReceivableQueryInfoGrid_landReceivableTotalAmount_Id').setValue(result.billReceivableAgencyVo.billReceivableAgencyDto.totalAmountRec);
    					Ext.getCmp('Foss_StllandReceivable_StllandReceivableQueryInfoGrid_landReceivableVerifyAmount_Id').setValue(result.billReceivableAgencyVo.billReceivableAgencyDto.verifyTotalAmountRec);
    					Ext.getCmp('Foss_StllandReceivable_StllandReceivableQueryInfoGrid_landReceivableUnverifyAmount_Id').setValue(result.billReceivableAgencyVo.billReceivableAgencyDto.unverifyTotalAmountRec);			
    				}
    				
    			});
    			Ext.Msg.alert(agency.landReceivable.i18n('foss.stl.agency.common.alert'),
    					agency.landReceivable.i18n('foss.stl.agency.airPayAndRec.reverseAuditSuccess'));
    		},
    		exception:function(response){
    			var result = Ext.decode(response.responseText);	
    			Ext.Msg.alert(agency.landReceivable.i18n('foss.stl.agency.common.alert'),result.message);
    		},
    		failure:function(response){
    			var result = Ext.decode(response.responseText);
    		}
    	});
    };
    var noFn=function(){
     	return false;
    };
    agency.landReceivable.billLandReceivableConfirmAlert(agency.landReceivable.i18n('foss.stl.agency.airPayAndRec.isReverseAudit'),yesFn,noFn);
}


//应收单model
Ext.define('Foss.StllandReceivable.BillReceivableEntryModel',{
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
Ext.define('Foss.StllandReceivable.BillReceivableEntryStore',{
	extend:'Ext.data.Store',
	model:'Foss.StllandReceivable.BillReceivableEntryModel',
	proxy:{
		type:'ajax',
		url:agency.realPath('queryLandReceivablePage.action'),
		reader:{
			type:'json',
			root:'billReceivableAgencyVo.billReceivableAgencyDto.billReceivableEntityList',
			totalProperty:'totalCount'
		}
	},
	listeners:{
		'beforeLoad':function(store, operation, eOpts){
		var form = Ext.getCmp('T_agency-landReceivable_content').getQueryInfoTab().items.items[0].items.items[0].getForm();
    		agency.landReceivable.startBusinessDate = form.findField('startDate').getValue();
    		agency.landReceivable.endBusinessDate = form.findField('endDate').getValue();
    		agency.landReceivable.approveStatus = form.findField('approveStatus').getValue();
    		agency.landReceivable.customerDetialCode = form.findField('customerDetial').getValue();
			var searchParams;
			//如果第一次进入界面，直接选择分页查询，则默认按照客户查询
			if(Ext.isEmpty(agency.landReceivable.queryByTab)){
				
				searchParams = {
						'billReceivableAgencyVo.billReceivableAgencyDto.startBusinessDate':agency.landReceivable.startBusinessDate,
						'billReceivableAgencyVo.billReceivableAgencyDto.endBusinessDate':agency.landReceivable.endBusinessDate,
						'billReceivableAgencyVo.billReceivableAgencyDto.approveStatus':agency.landReceivable.approveStatus,
						'billReceivableAgencyVo.billReceivableAgencyDto.customerCode':agency.landReceivable.customerCode,
						'billReceivableAgencyVo.billReceivableAgencyDto.queryByTab':agency.landReceivable.QUERY_BYDATE
				}
			}
			else if(agency.landReceivable.queryByTab == agency.landReceivable.QUERY_RECEIVABLE_NO){
				searchParams = {
						'billReceivableAgencyVo.billReceivableAgencyDto.receivableNo':agency.landReceivable.receivableNo,
						'billReceivableAgencyVo.billReceivableAgencyDto.queryByTab':agency.landReceivable.queryByTab
				}
			}
			else if(agency.landReceivable.queryByTab == agency.landReceivable.QUERY_SOURCE_BILL_NO){
				searchParams = {
						'billReceivableAgencyVo.billReceivableAgencyDto.sourceBillNo':agency.landReceivable.sourceBillNo,
						'billReceivableAgencyVo.billReceivableAgencyDto.queryByTab':agency.landReceivable.queryByTab
				}
			}
			Ext.apply(operation,{
				params :searchParams
			}); 
		}
	}
});

Ext.define('Foss.StllandReceivable.StllandReceivableQueryInfoTab', {
	extend:'Ext.tab.Panel',
	frame:false,
	bodyCls: 'autoHeight',
	cls: 'innerTabPanel',
	activeTab: 0,
	height : 190,
	items : [ {
		title:  agency.landReceivable.i18n('foss.stl.agency.common.queryByDate'),
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
		    	fieldLabel:agency.landReceivable.i18n('foss.stl.agency.airPayAndRec.startDateAgency'),
		    	value: stl.getTargetDate(new Date(),-1),
		    	editable:false,
		    	format:'Y-m-d',
		    	allowBlank:false
		    },{
		    	xtype:'datefield',
		   	 	name:'endDate',
		    	fieldLabel:agency.landReceivable.i18n('foss.stl.agency.airPayAndRec.endDateAgency'),
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
	        	xtype:'commonLdpAgencyCompanySelector',
				fieldLabel: agency.landReceivable.i18n('foss.stl.agency.landPayAndRec.landDetial'),
				name:'customerDetial',
				allowBlank:true,
				listWidth:300,//设置下拉框宽度
				isPaging:true //分页
		    },{
		    	xtype:'combobox',
		    	name:'approveStatus',
		    	fieldLabel:agency.landReceivable.i18n('foss.stl.agency.airPayAndRec.approveStatus'),
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
					text:agency.landReceivable.i18n('foss.stl.agency.common.reset'),
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
					text:agency.landReceivable.i18n('foss.stl.agency.common.query'),
					cls:'yellow_button',
					columnWidth:.15,
					handler:agency.landReceivable.queryBillReceivableEntityParams
				}]
		    }]
        }]
	}, {
		title: agency.landReceivable.i18n('foss.stl.agency.airPayAndRec.queryByRecNo'),
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
 	 			emptyText:agency.landReceivable.i18n('foss.stl.agency.airPayAndRec.emptyText'),
 	            fieldLabel: agency.landReceivable.i18n('foss.stl.agency.receivable.receivableNo'),
 	            name: 'receivableNos',
 	            height : 85,
 	            width : 480,
 	            allowBlank:false,
 	            regex:/^((YS)[0-9]{7,10}[;,]?)+$/i,
 	            regexText:'输入YF加上7-10位以内的数字编号',
 	            colspan:3
 		    },{
 		    	border: 1,
				xtype:'container',
				columnWidth:1,
				defaultType:'button',
				layout:'column',
				colspan:3,
				items:[{
					text:agency.landReceivable.i18n('foss.stl.agency.common.reset'),
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
					text:agency.landReceivable.i18n('foss.stl.agency.common.query'),
					cls:'yellow_button',
					columnWidth:.15,
					handler:agency.landReceivable.queryByReceivableNOs
				}]
 		    }]
        }]
	}, {
		title: agency.landReceivable.i18n('foss.stl.agency.common.queryBySourceNo'),
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
 	 			emptyText:agency.landReceivable.i18n('foss.stl.agency.airPayAndRec.emptyText'),
 	            fieldLabel: agency.landReceivable.i18n('foss.stl.agency.airPayAndRec.billNo'),
 	            name: 'sourceBillNos',
 	            height : 85,
 	            width : 480,
 				allowBlank:false,
 				colspan:2
 	           /* regex:/^((YS)[0-9]{7,10}[;,]?)+$/i,
 	            regexText:'输入YF加上7-10位以内的数字编号'*/
 		    },{
 		    	xtype:'container',
				items:[{
					xtype:'component',
					padding:'0 0 0 10',
					autoEl:{
						tag:'div',
						html:'<span style="color:red;">'+agency.landReceivable.i18n('foss.stl.agency.airPayAndRec.sourceBillNosWaybills')+'</span>'
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
					text:agency.landReceivable.i18n('foss.stl.agency.common.reset'),
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
					text:agency.landReceivable.i18n('foss.stl.agency.common.query'),
					cls:'yellow_button',
					columnWidth:.15,
					handler:agency.landReceivable.queryBySourceBillNOs
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
Ext.define('Foss.StllandReceivable.StlReceivableQueryInfoGrid',{
	extend:'Ext.grid.Panel',
    title: agency.landReceivable.i18n('foss.stl.agency.receivable.receivable'),
	frame:true,
	height:400,
	//plugins:SoperateColumnEditing,
	selModel:Ext.create('Ext.selection.CheckboxModel'),
    store: Ext.create('Foss.StllandReceivable.BillReceivableEntryStore'),
	columns:[{ 
   		xtype:'actioncolumn',
		width:73,
		text: agency.landReceivable.i18n('foss.stl.agency.common.actionColumn'),
		align: 'center',
		items:[{
			iconCls : 'foss_icons_stl_auditing',
			tooltip:agency.landReceivable.i18n('foss.stl.agency.common.audit'),
			getClass:function(v,m,r,rowIndex){
    			if(r.get('approveStatus') == "AA"
    					|| !agency.landReceivable.isPermission('/stl-web/agency/auditLandOtherBillReceivable.action')){
    				return 'statementBill_hide';
    			}else {
    				return 'foss_icons_stl_auditing';
    			}
    		},
			handler:function(grid, rowIndex, colIndex){
				agency.landReceivable.auditLandOtherOneBillReceivable(grid, rowIndex, colIndex)	
			}
		},{
			iconCls : 'foss_icons_stl_fauditing',
			tooltip:agency.landReceivable.i18n('foss.stl.agency.common.unAudit'),
			getClass:function(v,m,r,rowIndex){
    			if(r.get('approveStatus') == "NA"
    					|| !agency.landReceivable.isPermission('/stl-web/agency/reverseAuditLandOtherBillReceivable.action')){
    				return 'statementBill_hide';
    			}else {
    				return 'foss_icons_stl_fauditing';
    			}
    		},
			handler:function(grid, rowIndex, colIndex){
				agency.landReceivable.reverseAuditLandOtherOneBillReceivable(grid, rowIndex, colIndex)	
			}
		},{
			iconCls : 'deppon_icons_cancel',
			tooltip:agency.landReceivable.i18n('foss.stl.agency.common.disable'),
			getClass:function(v,m,r,rowIndex){
    			if(!agency.landReceivable.isPermission('/stl-web/agency/writeBackLandOtherBillReceivable.action')){
    				return 'statementBill_hide';
    			}else {
    				return 'deppon_icons_cancel';
    			}
    		},
			handler:function(grid, rowIndex, colIndex){
				agency.landReceivable.writeBackLandOtherOneReceivable(grid, rowIndex, colIndex)	
			}
		}]
	},{
		header: agency.landReceivable.i18n('foss.stl.agency.receivable.receivableNo'), 
		dataIndex: 'receivableNo'
	},{
		header: agency.landReceivable.i18n('foss.stl.agency.airPayAndRec.billNo'), 
		dataIndex: 'sourceBillNo'
	},{
		header: agency.landReceivable.i18n('foss.stl.agency.landPayAndRec.customerCode'), 
		dataIndex: 'customerCode'		
	},{
		header: agency.landReceivable.i18n('foss.stl.agency.landPayAndRec.customerName'), 
		dataIndex: 'customerName'
	},{
		header: agency.landReceivable.i18n('foss.stl.agency.airPayAndRec.approveStatus'), 
		dataIndex: 'approveStatus',
		renderer:function(value){
    		var displayField = FossDataDictionary.rendererSubmitToDisplay(value,settlementDict.BILL_RECEIVABLE__APPROVE_STATUS);
    		return displayField;
    	}
	},{
		header: agency.landReceivable.i18n('foss.stl.agency.airPayAndRec.origOrgCode'), 
		hidden:true,
		dataIndex:'origOrgCode'
	},{
		header: agency.landReceivable.i18n('foss.stl.agency.airPayAndRec.origOrgName'), 
		dataIndex:'origOrgName'
	},{
		header: agency.landReceivable.i18n('foss.stl.agency.airPayAndRec.amount'), 
		dataIndex: 'amount'				
	},{
		header: agency.landReceivable.i18n('foss.stl.agency.airPayAndRec.verifyAmount'),
		dataIndex: 'verifyAmount' 
	},{
		header: agency.landReceivable.i18n('foss.stl.agency.airPayAndRec.unverifyAmount'), 
		dataIndex: 'unverifyAmount'				
	},{
		header: agency.landReceivable.i18n('foss.stl.agency.airPayAndRec.businessDate'),
		dataIndex: 'businessDate',
		renderer:function(value){
			if(value!=null){
				return Ext.Date.format(new Date(value), 'Y-m-d');
			}else{
				return null;
			}
		} 
	},{
		header: agency.landReceivable.i18n('foss.stl.agency.airPayAndRec.accountDate'),
		dataIndex: 'accountDate',
		renderer:function(value){
			if(value!=null){
				return Ext.Date.format(new Date(value), 'Y-m-d');
			}else{
				return null;
			}
		} 
	},{
		header: agency.landReceivable.i18n('foss.stl.agency.airPayAndRec.auditDate'),
		dataIndex: 'auditDate',
		renderer:function(value){
			if(value!=null){
				return Ext.Date.format(new Date(value), 'Y-m-d');
			}else{
				return null;
			}
		} 
	},{
		header: agency.landReceivable.i18n('foss.stl.agency.airPayAndRec.notes'), 
		dataIndex: 'notes'
	},{
		header: agency.landReceivable.i18n('foss.stl.agency.airPayAndRec.statementBillNo'), 
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
				text:agency.landReceivable.i18n('foss.stl.agency.common.export'),
				disabled:!agency.landReceivable.isPermission('/stl-web/agency/landReceivableListExport.action'),
				hidden:!agency.landReceivable.isPermission('/stl-web/agency/landReceivableListExport.action'),
				handler : agency.landReceivable.landReceivableListExport
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
				id:'Foss_StllandReceivable_StllandReceivableQueryInfoGrid_landReceivableTotalRows_Id',
				columnWidth:.1,
				fieldLabel:agency.landReceivable.i18n('foss.stl.agency.common.sum'),
				labelWidth:50
			},{
				xtype:'container',
				border:false,
				html:'&nbsp;',
				columnWidth:.005
			},{
				xtype:'displayfield',
				allowBlank:true,
				id:'Foss_StllandReceivable_StllandReceivableQueryInfoGrid_landReceivableTotalAmount_Id',
				columnWidth:.1,
				fieldLabel:agency.landReceivable.i18n('foss.stl.agency.common.totalAmount'),
				labelWidth:50
			},{
				xtype:'container',
				border:false,
				html:'&nbsp;',
				columnWidth:.01
			},{
				xtype:'displayfield',
				allowBlank:true,
				id:'Foss_StllandReceivable_StllandReceivableQueryInfoGrid_landReceivableVerifyAmount_Id',
				columnWidth:.1,
				fieldLabel:agency.landReceivable.i18n('foss.stl.agency.airPayAndRec.verifyAmount'),
				labelWidth:80
			},{
				xtype:'container',
				border:false,
				html:'&nbsp;',
				columnWidth:.05
			},{
				xtype:'displayfield',
				allowBlank:true,
				id:'Foss_StllandReceivable_StllandReceivableQueryInfoGrid_landReceivableUnverifyAmount_Id',
				columnWidth:.1,
				fieldLabel:agency.landReceivable.i18n('foss.stl.agency.airPayAndRec.unverifyAmount'),
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
				text:agency.landReceivable.i18n('foss.stl.agency.common.disable'),
				disabled:!agency.landReceivable.isPermission('/stl-web/agency/writeBackLandOtherBillReceivable.action'),
				hidden:!agency.landReceivable.isPermission('/stl-web/agency/writeBackLandOtherBillReceivable.action'),
				columnWidth:.1,
				handler:agency.landReceivable.writeBackLandOtherBillReceivable
			},{
				xtype:'container',
				border:false,
				html:'&nbsp;',
				columnWidth:.005
			},{
				xtype:'button',
				text:agency.landReceivable.i18n('foss.stl.agency.common.audit'),
				disabled:!agency.landReceivable.isPermission('/stl-web/agency/auditLandOtherBillReceivable.action'),
				hidden:!agency.landReceivable.isPermission('/stl-web/agency/auditLandOtherBillReceivable.action'),
				columnWidth:.1,
				handler:agency.landReceivable.auditLandOtherBillReceivable
			},{
				xtype:'container',
				border:false,
				html:'&nbsp;',
				columnWidth:.005
			},{
				xtype:'button',
				text:agency.landReceivable.i18n('foss.stl.agency.common.unAudit'),
				disabled:!agency.landReceivable.isPermission('/stl-web/agency/reverseAuditLandOtherBillReceivable.action'),
				hidden:!agency.landReceivable.isPermission('/stl-web/agency/reverseAuditLandOtherBillReceivable.action'),
				columnWidth:.1,
				handler:agency.landReceivable.reverseAuditLandOtherBillReceivable
			}]
		}]
		me.callParent();
	}
});


//初始化界面
Ext.onReady(function() {
	Ext.QuickTips.init();

	var StllandReceivableQueryInfoTab = Ext.create('Foss.StllandReceivable.StllandReceivableQueryInfoTab');
	
	var StlReceivableQueryInfoGrid = Ext.create('Foss.StllandReceivable.StlReceivableQueryInfoGrid',{
		id:'Foss_StllandReceivable_StlReceivableQueryInfoGrid_Id'
	});
	
	Ext.create('Ext.panel.Panel',{
		id: 'T_agency-landReceivable_content',
		cls: "panelContentNToolbar",
		bodyCls: 'panelContentNToolbar-body',
		layout: 'auto',
		getQueryInfoTab:function(){
    		return StllandReceivableQueryInfoTab;
    	},
		items: [StllandReceivableQueryInfoTab,StlReceivableQueryInfoGrid],
		renderTo: 'T_agency-landReceivable-body'
	});
});