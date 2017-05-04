/**
 * 提示信息
 * @param {} message 
 * @param {} yesFn
 * @param {} noFn
 */
agency.airReceivable.billAirReceivableConfirmAlert = function(message,yesFn,noFn){
	Ext.Msg.confirm(agency.airReceivable.i18n('foss.stl.agency.common.alert'),message,function(o){
		if(o=='yes'){
			yesFn();
		}else{
			noFn();
		}
	});
};

agency.airReceivable.QUERY_BYDATE = 'TD';//按客户查询
agency.airReceivable.QUERY_RECEIVABLE_NO = 'TR';//按单号查询
agency.airReceivable.QUERY_SOURCE_BILL_NO = 'TSB';//按单号查询

/**
 * 按日期,客户查询
 */
agency.airReceivable.queryBillReceivableEntityParams = function(form,me){
	agency.airReceivable.startBusinessDate = form.findField('startDate').getValue();
	agency.airReceivable.endBusinessDate = form.findField('endDate').getValue();
	agency.airReceivable.approveStatus = form.findField('approveStatus').getValue();
	agency.airReceivable.productCode = form.findField('productCode').getValue();
	agency.airReceivable.customerDetialCode = form.findField('customerDetial').getValue();
	agency.airReceivable.agencyDetialCode = form.findField('agencyDetial').getValue();
	agency.airReceivable.queryByTab = agency.airReceivable.QUERY_BYDATE;
	if(agency.airReceivable.startBusinessDate==null 
			|| agency.airReceivable.startBusinessDate==''){
		Ext.Msg.alert(agency.airReceivable.i18n('foss.stl.agency.common.alert'),
				agency.airReceivable.i18n('foss.stl.agency.airPayAndRec.notDateNull'));
		return false;
	}

	if(agency.airReceivable.endBusinessDate==null 
			|| agency.airReceivable.endBusinessDate==''){
		Ext.Msg.alert(agency.airReceivable.i18n('foss.stl.agency.common.alert'),
				agency.airReceivable.i18n('foss.stl.agency.airPayAndRec.notDateNull'));
		return false;
	}
	
	var compareTwoDate = stl.compareTwoDate(agency.airReceivable.startBusinessDate
			,agency.airReceivable.endBusinessDate);
	if(compareTwoDate>stl.DATELIMITDAYS_MONTH){
		Ext.Msg.alert(agency.airReceivable.i18n('foss.stl.agency.common.alert'),'你好，您所查询的时间段已超过'+stl.DATELIMITDAYS_MONTH+'天，请重新输入结束时间');
		return false;
	}else if(compareTwoDate<1){
		Ext.Msg.alert(agency.airReceivable.i18n('foss.stl.agency.common.alert'),'你好，结束时间不能小于开始时间，请重新输入!');
		return false;
	}
	agency.airReceivable.customerCode = "";
	if(agency.airReceivable.customerDetialCode != "" && agency.airReceivable.customerDetialCode != null){
		agency.airReceivable.customerCode = agency.airReceivable.customerDetialCode;
	}else if(agency.airReceivable.agencyDetialCode != "" && agency.airReceivable.agencyDetialCode != null){
		agency.airReceivable.customerCode = agency.airReceivable.agencyDetialCode;
	}
	Ext.getCmp('Foss_StlairReceivable_StlReceivableQueryInfoGrid_Id').store.on('beforeload',function(store, operation, eOpts){
		Ext.apply(operation,{
			params :{
				'billReceivableAgencyVo.billReceivableAgencyDto.startBusinessDate':agency.airReceivable.startBusinessDate,
				'billReceivableAgencyVo.billReceivableAgencyDto.endBusinessDate':agency.airReceivable.endBusinessDate,
				'billReceivableAgencyVo.billReceivableAgencyDto.approveStatus':agency.airReceivable.approveStatus,
				'billReceivableAgencyVo.billReceivableAgencyDto.customerCode':agency.airReceivable.customerCode,
				'billReceivableAgencyVo.billReceivableAgencyDto.productCode':agency.airReceivable.productCode,
				'billReceivableAgencyVo.billReceivableAgencyDto.queryByTab':agency.airReceivable.queryByTab
			}
		});	
	});
	if(form.isValid()){
		//设置该按钮灰掉
		me.disable(false);
		//30秒后自动解除灰掉效果
		setTimeout(function() {
			me.enable(true);
		}, 30000);
		Ext.getCmp('Foss_StlairReceivable_StlReceivableQueryInfoGrid_Id').store.loadPage(1,{
			callback: function(records, operation, success) {
				var rawData = Ext.getCmp('Foss_StlairReceivable_StlReceivableQueryInfoGrid_Id').store.proxy.reader.rawData;
				//当success:false ,isException:false  --业务异常
			    if(!success && ! rawData.isException){
 					Ext.Msg.alert(agency.airReceivable.i18n('foss.stl.agency.common.alert'),rawData.message);
 					return false;
 				}
			    if(success){
					var result = Ext.decode(operation.response.responseText);  
    			    Ext.getCmp('Foss_StlairReceivable_StlairReceivableQueryInfoGrid_airReceivableTotalRows_Id').setValue(result.billReceivableAgencyVo.billReceivableAgencyDto.totalNumRec);
    				Ext.getCmp('Foss_StlairReceivable_StlairReceivableQueryInfoGrid_airReceivableTotalAmount_Id').setValue(result.billReceivableAgencyVo.billReceivableAgencyDto.totalAmountRec);
    				Ext.getCmp('Foss_StlairReceivable_StlairReceivableQueryInfoGrid_airReceivableVerifyAmount_Id').setValue(result.billReceivableAgencyVo.billReceivableAgencyDto.verifyTotalAmountRec);
    				Ext.getCmp('Foss_StlairReceivable_StlairReceivableQueryInfoGrid_airReceivableUnverifyAmount_Id').setValue(result.billReceivableAgencyVo.billReceivableAgencyDto.unverifyTotalAmountRec);
			    }
			    me.enable(true);
			}
			
		});
		
	}else{
		Ext.Msg.alert(agency.airReceivable.i18n('foss.stl.agency.common.alert'),
				agency.airReceivable.i18n('foss.stl.agency.airPayAndRec.pageCheck'));
		me.enable(true);
		return false;
	}	
}

/**
 * 按应收单号查询
 * @returns {Boolean}
 */
agency.airReceivable.queryByReceivableNOs = function(form,me){
	agency.airReceivable.receivableNo = form.findField('receivableNos').getValue();
	agency.airReceivable.queryByTab = agency.airReceivable.QUERY_RECEIVABLE_NO;
	//单号不能为空
	if(agency.airReceivable.receivableNo==null ||
			agency.airReceivable.receivableNo==''){
		Ext.Msg.alert(agency.airReceivable.i18n('foss.stl.agency.common.alert'),
				agency.airReceivable.i18n('foss.stl.agency.receivable.notNullReceivableNo'));
		return false;
	}
	//单号个数不能超过10个
	var receivableNosStr = new Array();
	receivableNosStr = stl.splitToArray(agency.airReceivable.receivableNo);
	if(receivableNosStr.length>10){
		Ext.Msg.alert(agency.airReceivable.i18n('foss.stl.agency.common.alert'),
				agency.airReceivable.i18n('foss.stl.agency.airPayAndRec.notGreaterThanTen'));
		return false;
	}
	
	Ext.getCmp('Foss_StlairReceivable_StlReceivableQueryInfoGrid_Id').store.on('beforeload',function(store, operation, eOpts){
		Ext.apply(operation,{
			params :{
				'billReceivableAgencyVo.billReceivableAgencyDto.receivableNo':agency.airReceivable.receivableNo,
				'billReceivableAgencyVo.billReceivableAgencyDto.queryByTab':agency.airReceivable.queryByTab
			}
		});	
	});
	if(form.isValid()){
		//设置该按钮灰掉
		me.disable(false);
		//30秒后自动解除灰掉效果
		setTimeout(function() {
			me.enable(true);
		}, 30000);
		Ext.getCmp('Foss_StlairReceivable_StlReceivableQueryInfoGrid_Id').store.loadPage(1,{
			callback: function(records, operation, success) {
    			var rawData = Ext.getCmp('Foss_StlairReceivable_StlReceivableQueryInfoGrid_Id').store.proxy.reader.rawData;
    			//当success:false ,isException:false  --业务异常
    		    if(!success && ! rawData.isException){
 					Ext.Msg.alert(agency.airReceivable.i18n('foss.stl.agency.common.alert'),rawData.message);
 					return false;
 				}
    		    if(success){
					var result = Ext.decode(operation.response.responseText);  
    			    Ext.getCmp('Foss_StlairReceivable_StlairReceivableQueryInfoGrid_airReceivableTotalRows_Id').setValue(result.billReceivableAgencyVo.billReceivableAgencyDto.totalNumRec);
    				Ext.getCmp('Foss_StlairReceivable_StlairReceivableQueryInfoGrid_airReceivableTotalAmount_Id').setValue(result.billReceivableAgencyVo.billReceivableAgencyDto.totalAmountRec);
    				Ext.getCmp('Foss_StlairReceivable_StlairReceivableQueryInfoGrid_airReceivableVerifyAmount_Id').setValue(result.billReceivableAgencyVo.billReceivableAgencyDto.verifyTotalAmountRec);
    				Ext.getCmp('Foss_StlairReceivable_StlairReceivableQueryInfoGrid_airReceivableUnverifyAmount_Id').setValue(result.billReceivableAgencyVo.billReceivableAgencyDto.unverifyTotalAmountRec);
    		    }
    		    me.enable(true);
			}
			
		});
		
	}else{
		Ext.Msg.alert(agency.airReceivable.i18n('foss.stl.agency.common.alert'),
				agency.airReceivable.i18n('foss.stl.agency.airPayAndRec.pageCheck'));
		me.enable(true);
		return false;
	}	
}

/**
 * 按航空正单号查询
 * @returns {Boolean}
 */
agency.airReceivable.queryBySourceBillNOs = function(form,me){
	agency.airReceivable.sourceBillNo = form.findField('sourceBillNos').getValue();
	agency.airReceivable.queryByTab = agency.airReceivable.QUERY_SOURCE_BILL_NO;
	//单号不能为空
	if(agency.airReceivable.sourceBillNo==null ||
			agency.airReceivable.sourceBillNo==''){
		Ext.Msg.alert(agency.airReceivable.i18n('foss.stl.agency.common.alert'),
				agency.airReceivable.i18n('foss.stl.agency.airPayAndRec.notNullSourceBillNo'));
		return false;
	}
	//单号个数不能超过10个
	var receivableNosStr = new Array();
	receivableNosStr = stl.splitToArray(agency.airReceivable.sourceBillNo);
	if(receivableNosStr.length>10){
		Ext.Msg.alert(agency.airReceivable.i18n('foss.stl.agency.common.alert'),
				agency.airReceivable.i18n('foss.stl.agency.airPayAndRec.notGreaterThanTen'));
		return false;
	}
	
	Ext.getCmp('Foss_StlairReceivable_StlReceivableQueryInfoGrid_Id').store.on('beforeload',function(store, operation, eOpts){
		Ext.apply(operation,{
			params :{
				'billReceivableAgencyVo.billReceivableAgencyDto.sourceBillNo':agency.airReceivable.sourceBillNo,
				'billReceivableAgencyVo.billReceivableAgencyDto.queryByTab':agency.airReceivable.queryByTab
			}
		});	
	});

	if(form.isValid()){
		//设置该按钮灰掉
		me.disable(false);
		//30秒后自动解除灰掉效果
		setTimeout(function() {
			me.enable(true);
		}, 30000);
		Ext.getCmp('Foss_StlairReceivable_StlReceivableQueryInfoGrid_Id').store.loadPage(1,{
			callback: function(records, operation, success) {
    			var rawData = Ext.getCmp('Foss_StlairReceivable_StlReceivableQueryInfoGrid_Id').store.proxy.reader.rawData;
    			//当success:false ,isException:false  --业务异常
    		    if(!success && ! rawData.isException){
 					Ext.Msg.alert(agency.airReceivable.i18n('foss.stl.agency.common.alert'),rawData.message);
 					return false;
 				}
    		    if(success){
    		    	var result = Ext.decode(operation.response.responseText); 
    			    Ext.getCmp('Foss_StlairReceivable_StlairReceivableQueryInfoGrid_airReceivableTotalRows_Id').setValue(result.billReceivableAgencyVo.billReceivableAgencyDto.totalNumRec);
    				Ext.getCmp('Foss_StlairReceivable_StlairReceivableQueryInfoGrid_airReceivableTotalAmount_Id').setValue(result.billReceivableAgencyVo.billReceivableAgencyDto.totalAmountRec);
    				Ext.getCmp('Foss_StlairReceivable_StlairReceivableQueryInfoGrid_airReceivableVerifyAmount_Id').setValue(result.billReceivableAgencyVo.billReceivableAgencyDto.verifyTotalAmountRec);
    				Ext.getCmp('Foss_StlairReceivable_StlairReceivableQueryInfoGrid_airReceivableUnverifyAmount_Id').setValue(result.billReceivableAgencyVo.billReceivableAgencyDto.unverifyTotalAmountRec);
    		    }
    		    me.enable(true);
			}
			
		});
		
	}else{
		Ext.Msg.alert(agency.airReceivable.i18n('foss.stl.agency.common.alert'),
				agency.airReceivable.i18n('foss.stl.agency.airPayAndRec.pageCheck'));
		me.enable(true);
		return false;
	}	
}

//导出空运其他应收单
agency.airReceivable.airReceivableListExport = function(){
	//获取表格
	grid = Ext.getCmp('Foss_StlairReceivable_StlReceivableQueryInfoGrid_Id');
	//如果没有查询到数据，则不能进行导出操作
	if(grid.store.data.length == 0){
		Ext.Msg.alert(agency.airReceivable.i18n('foss.stl.agency.common.alert'),
				agency.airReceivable.i18n('foss.stl.agency.airPayAndRec.exportResultSetNotnull'));
		return false;
	}
	
	if(!Ext.fly('downloadAttachFileForm')){
	    var frm = document.createElement('form');
	    frm.id = 'downloadAttachFileForm';
	    frm.style.display = 'none';
	    document.body.appendChild(frm);
	}
	if(agency.airReceivable.queryByTab == agency.airReceivable.QUERY_BYDATE){
		searchParams = {
				'billReceivableAgencyVo.billReceivableAgencyDto.startBusinessDate':agency.airReceivable.startBusinessDate,
				'billReceivableAgencyVo.billReceivableAgencyDto.endBusinessDate':agency.airReceivable.endBusinessDate,
				'billReceivableAgencyVo.billReceivableAgencyDto.approveStatus':agency.airReceivable.approveStatus,
				'billReceivableAgencyVo.billReceivableAgencyDto.customerCode':agency.airReceivable.customerCode,
				'billReceivableAgencyVo.billReceivableAgencyDto.queryByTab':agency.airReceivable.queryByTab
		}
	}
	else if(agency.airReceivable.queryByTab == agency.airReceivable.QUERY_RECEIVABLE_NO){
		searchParams = {
				'billReceivableAgencyVo.billReceivableAgencyDto.receivableNo':agency.airReceivable.receivableNo,
				'billReceivableAgencyVo.billReceivableAgencyDto.queryByTab':agency.airReceivable.queryByTab
		}
	}
	else if(agency.airReceivable.queryByTab == agency.airReceivable.QUERY_SOURCE_BILL_NO){
		searchParams = {
				'billReceivableAgencyVo.billReceivableAgencyDto.sourceBillNo':agency.airReceivable.sourceBillNo,
				'billReceivableAgencyVo.billReceivableAgencyDto.queryByTab':agency.airReceivable.queryByTab
		}
	}
	var yesFn=function(){
		Ext.Ajax.request({
  		url:agency.realPath('airReceivableListExport.action'),
  		form: Ext.fly('downloadAttachFileForm'),
  		params :searchParams,
		isUpload: true,
  		success:function(response){
  			//如果异常信息有值，则弹出提示
			var result = Ext.decode(response.responseText);	
  	    	if(!Ext.isEmpty(result.errorMessage)){
  	    		Ext.Msg.alert(agency.airReceivable.i18n('foss.stl.agency.common.alert'),result.errorMessage);
  	    		return false;
  	    	}
  	    	Ext.ux.Toast.msg(agency.airReceivable.i18n('foss.stl.agency.common.alert'),
  	    			agency.airReceivable.i18n('foss.stl.agency.airPayAndRec.exportSuccess'), 'success', 1000);
  		},
  		failure:function(response){
  			Ext.ux.Toast.msg(agency.airReceivable.i18n('foss.stl.agency.common.alert'),
  					agency.airReceivable.i18n('foss.stl.agency.airPayAndRec.exportError'), 'error', 1000);
  		}
  	});
	};
	var noFn=function(){
	 	return false;
	};
	agency.airReceivable.billAirReceivableConfirmAlert(agency.airReceivable.i18n('foss.stl.agency.airPayAndRec.isExport'),yesFn,noFn);
}

agency.airReceivable.queryCondition = function(){
	var searchParams;
	if(agency.airReceivable.queryByTab == agency.airReceivable.QUERY_BYDATE){
		searchParams = {
				'startBusinessDate':agency.airReceivable.startBusinessDate,
				'endBusinessDate':agency.airReceivable.endBusinessDate,
				'approveStatus':agency.airReceivable.approveStatus,
				'customerCode':agency.airReceivable.customerCode,
				'queryByTab':agency.airReceivable.queryByTab
		}
	}
	else if(agency.airReceivable.queryByTab == agency.airReceivable.QUERY_RECEIVABLE_NO){
		searchParams = {
				'receivableNo':agency.airReceivable.receivableNo,
				'queryByTab':agency.airReceivable.queryByTab
		}
	}
	else if(agency.airReceivable.queryByTab == agency.airReceivable.QUERY_SOURCE_BILL_NO){
		searchParams = {
				'sourceBillNo':agency.airReceivable.sourceBillNo,
				'queryByTab':agency.airReceivable.queryByTab
		}
	}
	return searchParams;
}

//空运其他应收管理单条作废
agency.airReceivable.writeBackAirOtherOneReceivable = function(grid, rowIndex, colIndex){
	var selectionReceivable = grid.getStore().getAt(rowIndex);
	var jsonDataReceivable = new Array();
	jsonDataReceivable.push(selectionReceivable.data);
	var entity = new Object();
	entity.billReceivableAgencyDtos = jsonDataReceivable;
	
	var searchParams = agency.airReceivable.queryCondition();
	
	entity.billReceivableAgencyDto = searchParams;
	
	var yesFn=function(){
    	Ext.Ajax.request({
    		url:agency.realPath('writeBackAirOtherBillReceivable.action'),
    		jsonData:{'billReceivableAgencyVo':entity},
    		method:'post',
    		success:function(response){
    			Ext.getCmp('Foss_StlairReceivable_StlReceivableQueryInfoGrid_Id').store.load({
    				callback: function(records, operation, success) {
    				     var result =   Ext.decode(operation.response.responseText);  
    				     if(result.isException){
    	 					Ext.Msg.alert(agency.airReceivable.i18n('foss.stl.agency.common.alert'),result.message);
    	 					return false;
    	 				}
    				    Ext.getCmp('Foss_StlairReceivable_StlairReceivableQueryInfoGrid_airReceivableTotalRows_Id').setValue(result.billReceivableAgencyVo.billReceivableAgencyDto.totalNumRec);
    					Ext.getCmp('Foss_StlairReceivable_StlairReceivableQueryInfoGrid_airReceivableTotalAmount_Id').setValue(result.billReceivableAgencyVo.billReceivableAgencyDto.totalAmountRec);
    					Ext.getCmp('Foss_StlairReceivable_StlairReceivableQueryInfoGrid_airReceivableVerifyAmount_Id').setValue(result.billReceivableAgencyVo.billReceivableAgencyDto.verifyTotalAmountRec);
    					Ext.getCmp('Foss_StlairReceivable_StlairReceivableQueryInfoGrid_airReceivableUnverifyAmount_Id').setValue(result.billReceivableAgencyVo.billReceivableAgencyDto.unverifyTotalAmountRec);			
    				}
    				
    			});
    			Ext.Msg.alert(agency.airReceivable.i18n('foss.stl.agency.common.alert'),
    					agency.airReceivable.i18n('foss.stl.agency.airPayAndRec.disableSuccess'));
    		},
    		exception:function(response){
    			var result = Ext.decode(response.responseText);	
    			Ext.Msg.alert(agency.airReceivable.i18n('foss.stl.agency.common.alert'),result.message);
    		},
    		failure:function(response){
    			var result = Ext.decode(response.responseText);
    		}
    	});
	};
	var noFn=function(){
	 	return false;
	};
	agency.airReceivable.billAirReceivableConfirmAlert(agency.airReceivable.i18n('foss.stl.agency.airPayAndRec.isDisable'),yesFn,noFn);
}

//空运其他应收管理作废
agency.airReceivable.writeBackAirOtherBillReceivable = function(me){
	
	var selectionReceivable = Ext.getCmp('Foss_StlairReceivable_StlReceivableQueryInfoGrid_Id').getSelectionModel().getSelection();
	
	if(selectionReceivable.length==0){
		Ext.Msg.alert(agency.airReceivable.i18n('foss.stl.agency.common.alert'),
				agency.airReceivable.i18n('foss.stl.agency.airPayAndRec.disableText'));
		return false;
	}
	var searchParams = agency.airReceivable.queryCondition();
	
	var jsonDataReceivable = new Array();
	for(var i=0;i<selectionReceivable.length;i++){
		jsonDataReceivable.push(selectionReceivable[i].data);
	}
	var entity = new Object();
	entity.billReceivableAgencyDto = searchParams;
	entity.billReceivableAgencyDtos = jsonDataReceivable;
    var yesFn=function(){
    	//设置该按钮灰掉
		me.disable(false);
		//30秒后自动解除灰掉效果
		setTimeout(function() {
			me.enable(true);
		}, 30000);
    	Ext.Ajax.request({
    		url:agency.realPath('writeBackAirOtherBillReceivable.action'),
    		jsonData:{'billReceivableAgencyVo':entity},
    		method:'post',
    		success:function(response){
    			Ext.getCmp('Foss_StlairReceivable_StlReceivableQueryInfoGrid_Id').store.load({
    				callback: function(records, operation, success) {
    				     var result =   Ext.decode(operation.response.responseText);  
    				     if(result.isException){
    	 					Ext.Msg.alert(agency.airReceivable.i18n('foss.stl.agency.common.alert'),result.message);
    	 					return false;
    	 				}
    				    Ext.getCmp('Foss_StlairReceivable_StlairReceivableQueryInfoGrid_airReceivableTotalRows_Id').setValue(result.billReceivableAgencyVo.billReceivableAgencyDto.totalNumRec);
    					Ext.getCmp('Foss_StlairReceivable_StlairReceivableQueryInfoGrid_airReceivableTotalAmount_Id').setValue(result.billReceivableAgencyVo.billReceivableAgencyDto.totalAmountRec);
    					Ext.getCmp('Foss_StlairReceivable_StlairReceivableQueryInfoGrid_airReceivableVerifyAmount_Id').setValue(result.billReceivableAgencyVo.billReceivableAgencyDto.verifyTotalAmountRec);
    					Ext.getCmp('Foss_StlairReceivable_StlairReceivableQueryInfoGrid_airReceivableUnverifyAmount_Id').setValue(result.billReceivableAgencyVo.billReceivableAgencyDto.unverifyTotalAmountRec);			
    				}
    				
    			});
    			Ext.Msg.alert(agency.airReceivable.i18n('foss.stl.agency.common.alert'),
    					agency.airReceivable.i18n('foss.stl.agency.airPayAndRec.disableSuccess'));
    			me.enable(true);
    		},
    		exception:function(response){
    			var result = Ext.decode(response.responseText);	
    			Ext.Msg.alert(agency.airReceivable.i18n('foss.stl.agency.common.alert'),result.message);
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
	agency.airReceivable.billAirReceivableConfirmAlert(agency.airReceivable.i18n('foss.stl.agency.airPayAndRec.isDisable'),yesFn,noFn);
}
//单条数据审核
agency.airReceivable.auditAirOtherOneBillReceivable = function(grid, rowIndex, colIndex){
	var selectionReceivable = grid.getStore().getAt(rowIndex);
	var jsonDataReceivable = new Array();
	jsonDataReceivable.push(selectionReceivable.data);
	var entity = new Object();
	entity.billReceivableAgencyDtos = jsonDataReceivable;
	
	var searchParams = agency.airReceivable.queryCondition();
	
	entity.billReceivableAgencyDto = searchParams;
	var yesFn=function(){
    	Ext.Ajax.request({
    		url:agency.realPath('auditAirOtherBillReceivable.action'),
    		jsonData:{'billReceivableAgencyVo':entity},
    		method:'post',
    		success:function(response){
    			Ext.getCmp('Foss_StlairReceivable_StlReceivableQueryInfoGrid_Id').store.load({
    				callback: function(records, operation, success) {
    				     var result =   Ext.decode(operation.response.responseText);  
    				     if(result.isException){
    	 					Ext.Msg.alert(agency.airReceivable.i18n('foss.stl.agency.common.alert'),result.message);
    	 					return false;
    	 				}
    				    Ext.getCmp('Foss_StlairReceivable_StlairReceivableQueryInfoGrid_airReceivableTotalRows_Id').setValue(result.billReceivableAgencyVo.billReceivableAgencyDto.totalNumRec);
    					Ext.getCmp('Foss_StlairReceivable_StlairReceivableQueryInfoGrid_airReceivableTotalAmount_Id').setValue(result.billReceivableAgencyVo.billReceivableAgencyDto.totalAmountRec);
    					Ext.getCmp('Foss_StlairReceivable_StlairReceivableQueryInfoGrid_airReceivableVerifyAmount_Id').setValue(result.billReceivableAgencyVo.billReceivableAgencyDto.verifyTotalAmountRec);
    					Ext.getCmp('Foss_StlairReceivable_StlairReceivableQueryInfoGrid_airReceivableUnverifyAmount_Id').setValue(result.billReceivableAgencyVo.billReceivableAgencyDto.unverifyTotalAmountRec);			
    				}
    				
    			});
    			Ext.Msg.alert(agency.airReceivable.i18n('foss.stl.agency.common.alert'),
    					agency.airReceivable.i18n('foss.stl.agency.airPayAndRec.auditSuccess'));
    		},
    		exception:function(response){
    			var result = Ext.decode(response.responseText);	
    			Ext.Msg.alert(agency.airReceivable.i18n('foss.stl.agency.common.alert'),result.message);
    		},
    		failure:function(response){
    			var result = Ext.decode(response.responseText);
    		}
    	});
	};
	var noFn=function(){
	 	return false;
	};
	agency.airReceivable.billAirReceivableConfirmAlert(agency.airReceivable.i18n('foss.stl.agency.airPayAndRec.isAudit'),yesFn,noFn);
}

//空运其他管理审核
agency.airReceivable.auditAirOtherBillReceivable = function(){
	
	var selectionReceivable = Ext.getCmp('Foss_StlairReceivable_StlReceivableQueryInfoGrid_Id').getSelectionModel().getSelection();
	
	if(selectionReceivable.length==0){
		Ext.Msg.alert(agency.airReceivable.i18n('foss.stl.agency.common.alert'),
				agency.airReceivable.i18n('foss.stl.agency.airPayAndRec.auditText'));
		return false;
	}
	var searchParams = agency.airReceivable.queryCondition();
	
	var jsonDataReceivable = new Array();
	for(var i=0;i<selectionReceivable.length;i++){
		jsonDataReceivable.push(selectionReceivable[i].data);
	}
	
	var entity = new Object();
	entity.billReceivableAgencyDto = searchParams;
	entity.billReceivableAgencyDtos = jsonDataReceivable;
	var yesFn=function(){
    	Ext.Ajax.request({
    		url:agency.realPath('auditAirOtherBillReceivable.action'),
    		jsonData:{'billReceivableAgencyVo':entity},
    		method:'post',
    		success:function(response){
    			Ext.getCmp('Foss_StlairReceivable_StlReceivableQueryInfoGrid_Id').store.load({
    				callback: function(records, operation, success) {
    				     var result =   Ext.decode(operation.response.responseText);  
    				     if(result.isException){
    	 					Ext.Msg.alert(agency.airReceivable.i18n('foss.stl.agency.common.alert'),result.message);
    	 					return false;
    	 				}
    				    Ext.getCmp('Foss_StlairReceivable_StlairReceivableQueryInfoGrid_airReceivableTotalRows_Id').setValue(result.billReceivableAgencyVo.billReceivableAgencyDto.totalNumRec);
    					Ext.getCmp('Foss_StlairReceivable_StlairReceivableQueryInfoGrid_airReceivableTotalAmount_Id').setValue(result.billReceivableAgencyVo.billReceivableAgencyDto.totalAmountRec);
    					Ext.getCmp('Foss_StlairReceivable_StlairReceivableQueryInfoGrid_airReceivableVerifyAmount_Id').setValue(result.billReceivableAgencyVo.billReceivableAgencyDto.verifyTotalAmountRec);
    					Ext.getCmp('Foss_StlairReceivable_StlairReceivableQueryInfoGrid_airReceivableUnverifyAmount_Id').setValue(result.billReceivableAgencyVo.billReceivableAgencyDto.unverifyTotalAmountRec);			
    				}
    				
    			});
    			Ext.Msg.alert(agency.airReceivable.i18n('foss.stl.agency.common.alert'),
    					agency.airReceivable.i18n('foss.stl.agency.airPayAndRec.auditSuccess'));
    		},
    		exception:function(response){
    			var result = Ext.decode(response.responseText);	
    			Ext.Msg.alert(agency.airReceivable.i18n('foss.stl.agency.common.alert'),result.message);
    		},
    		failure:function(response){
    			var result = Ext.decode(response.responseText);
    		}
    	});
	};
	var noFn=function(){
	 	return false;
	};
	agency.airReceivable.billAirReceivableConfirmAlert(agency.airReceivable.i18n('foss.stl.agency.airPayAndRec.isAudit'),yesFn,noFn);
}

//单条数据反审核
agency.airReceivable.reverseAuditAirOtherOneBillReceivable = function(grid, rowIndex, colIndex){
	var selectionReceivable = grid.getStore().getAt(rowIndex);
	var jsonDataReceivable = new Array();
	jsonDataReceivable.push(selectionReceivable.data);
	var entity = new Object();
	entity.billReceivableAgencyDtos = jsonDataReceivable;
	
	var searchParams = agency.airReceivable.queryCondition();
	
	entity.billReceivableAgencyDto = searchParams;
	
	var yesFn=function(){
    	Ext.Ajax.request({
    		url:agency.realPath('reverseAuditAirOtherBillReceivable.action'),
    		jsonData:{'billReceivableAgencyVo':entity},
    		method:'post',
    		success:function(response){
    			Ext.getCmp('Foss_StlairReceivable_StlReceivableQueryInfoGrid_Id').store.load({
    				callback: function(records, operation, success) {
    				     var result =   Ext.decode(operation.response.responseText);  
    				     if(result.isException){
    	 					Ext.Msg.alert(agency.airReceivable.i18n('foss.stl.agency.common.alert'),result.message);
    	 					return false;
    	 				}
    				    Ext.getCmp('Foss_StlairReceivable_StlairReceivableQueryInfoGrid_airReceivableTotalRows_Id').setValue(result.billReceivableAgencyVo.billReceivableAgencyDto.totalNumRec);
    					Ext.getCmp('Foss_StlairReceivable_StlairReceivableQueryInfoGrid_airReceivableTotalAmount_Id').setValue(result.billReceivableAgencyVo.billReceivableAgencyDto.totalAmountRec);
    					Ext.getCmp('Foss_StlairReceivable_StlairReceivableQueryInfoGrid_airReceivableVerifyAmount_Id').setValue(result.billReceivableAgencyVo.billReceivableAgencyDto.verifyTotalAmountRec);
    					Ext.getCmp('Foss_StlairReceivable_StlairReceivableQueryInfoGrid_airReceivableUnverifyAmount_Id').setValue(result.billReceivableAgencyVo.billReceivableAgencyDto.unverifyTotalAmountRec);			
    				}
    				
    			});
    			Ext.Msg.alert(agency.airReceivable.i18n('foss.stl.agency.common.alert'),
    					agency.airReceivable.i18n('foss.stl.agency.airPayAndRec.reverseAuditSuccess'));
    		},
    		exception:function(response){
    			var result = Ext.decode(response.responseText);	
    			Ext.Msg.alert(agency.airReceivable.i18n('foss.stl.agency.common.alert'),result.message);
    		},
    		failure:function(response){
    			var result = Ext.decode(response.responseText);
    		}
    	});
    };
    var noFn=function(){
     	return false;
    };
    agency.airReceivable.billAirReceivableConfirmAlert(agency.airReceivable.i18n('foss.stl.agency.airPayAndRec.isReverseAudit'),yesFn,noFn);
}

//空运其他管理反审核
agency.airReceivable.reverseAuditAirOtherBillReceivable = function(){
	var selectionReceivable = Ext.getCmp('Foss_StlairReceivable_StlReceivableQueryInfoGrid_Id').getSelectionModel().getSelection();
	
	if(selectionReceivable.length==0){
		Ext.Msg.alert(agency.airReceivable.i18n('foss.stl.agency.common.alert'),
				agency.airReceivable.i18n('foss.stl.agency.airPayAndRec.reverseAuditText'));
		return false;
	}
	
	var searchParams = agency.airReceivable.queryCondition();
	
	var jsonDataReceivable = new Array();
	for(var i=0;i<selectionReceivable.length;i++){
		jsonDataReceivable.push(selectionReceivable[i].data);
	}
	var entity = new Object();
	entity.billReceivableAgencyDto = searchParams;
	entity.billReceivableAgencyDtos = jsonDataReceivable;
	var yesFn=function(){
    	Ext.Ajax.request({
    		url:agency.realPath('reverseAuditAirOtherBillReceivable.action'),
    		jsonData:{'billReceivableAgencyVo':entity},
    		method:'post',
    		success:function(response){
    			Ext.getCmp('Foss_StlairReceivable_StlReceivableQueryInfoGrid_Id').store.load({
    				callback: function(records, operation, success) {
    				     var result =   Ext.decode(operation.response.responseText);  
    				     if(result.isException){
    	 					Ext.Msg.alert(agency.airReceivable.i18n('foss.stl.agency.common.alert'),result.message);
    	 					return false;
    	 				}
    				    Ext.getCmp('Foss_StlairReceivable_StlairReceivableQueryInfoGrid_airReceivableTotalRows_Id').setValue(result.billReceivableAgencyVo.billReceivableAgencyDto.totalNumRec);
    					Ext.getCmp('Foss_StlairReceivable_StlairReceivableQueryInfoGrid_airReceivableTotalAmount_Id').setValue(result.billReceivableAgencyVo.billReceivableAgencyDto.totalAmountRec);
    					Ext.getCmp('Foss_StlairReceivable_StlairReceivableQueryInfoGrid_airReceivableVerifyAmount_Id').setValue(result.billReceivableAgencyVo.billReceivableAgencyDto.verifyTotalAmountRec);
    					Ext.getCmp('Foss_StlairReceivable_StlairReceivableQueryInfoGrid_airReceivableUnverifyAmount_Id').setValue(result.billReceivableAgencyVo.billReceivableAgencyDto.unverifyTotalAmountRec);			
    				}
    				
    			});
    			Ext.Msg.alert(agency.airReceivable.i18n('foss.stl.agency.common.alert'),
    					agency.airReceivable.i18n('foss.stl.agency.airPayAndRec.reverseAuditSuccess'));
    			me.enable(true);
    		},
    		exception:function(response){
    			var result = Ext.decode(response.responseText);	
    			Ext.Msg.alert(agency.airReceivable.i18n('foss.stl.agency.common.alert'),result.message);
    		},
    		failure:function(response){
    			var result = Ext.decode(response.responseText);
    		}
    	});
    };
    var noFn=function(){
     	return false;
    };
    agency.airReceivable.billAirReceivableConfirmAlert(agency.airReceivable.i18n('foss.stl.agency.airPayAndRec.isReverseAudit'),yesFn,noFn);
}


//应收单model
Ext.define('Foss.StlairReceivable.BillReceivableEntryModel',{
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
		name:'productCode'
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
Ext.define('Foss.StlairReceivable.BillReceivableEntryStore',{
	extend:'Ext.data.Store',
	model:'Foss.StlairReceivable.BillReceivableEntryModel',
	proxy:{
		type:'ajax',
		url:agency.realPath('queryAirReceivablePage.action'),
		reader:{
			type:'json',
			root:'billReceivableAgencyVo.billReceivableAgencyDto.billReceivableEntityList',
			totalProperty:'totalCount'
		}
	},
	listeners:{
		'beforeLoad':function(store, operation, eOpts){
		var form = Ext.getCmp('T_agency-airReceivable_content').getQueryInfoTab().items.items[0].items.items[0].getForm();
    		agency.airReceivable.startBusinessDate = form.findField('startDate').getValue();
    		agency.airReceivable.endBusinessDate = form.findField('endDate').getValue();
    		agency.airReceivable.approveStatus = form.findField('approveStatus').getValue();
    		agency.airReceivable.customerDetialCode = form.findField('customerDetial').getValue();
    		agency.airReceivable.agencyDetialCode = form.findField('agencyDetial').getValue();
    		agency.airReceivable.productCode = form.findField('productCode').getValue();
			var searchParams;
			//如果第一次进入界面，直接选择分页查询，则默认按照客户查询
			if(Ext.isEmpty(agency.airReceivable.queryByTab)){
				
				searchParams = {
						'billReceivableAgencyVo.billReceivableAgencyDto.startBusinessDate':agency.airReceivable.startBusinessDate,
						'billReceivableAgencyVo.billReceivableAgencyDto.endBusinessDate':agency.airReceivable.endBusinessDate,
						'billReceivableAgencyVo.billReceivableAgencyDto.approveStatus':agency.airReceivable.approveStatus,
						'billReceivableAgencyVo.billReceivableAgencyDto.customerCode':agency.airReceivable.customerCode,
						'billReceivableAgencyVo.billReceivableAgencyDto.productCode':agency.airReceivable.productCode,
						'billReceivableAgencyVo.billReceivableAgencyDto.queryByTab':agency.airReceivable.QUERY_BYDATE
				}
			}
			else if(agency.airReceivable.queryByTab == agency.airReceivable.QUERY_RECEIVABLE_NO){
				searchParams = {
						'billReceivableAgencyVo.billReceivableAgencyDto.receivableNo':agency.airReceivable.receivableNo,
						'billReceivableAgencyVo.billReceivableAgencyDto.queryByTab':agency.airReceivable.queryByTab
				}
			}
			else if(agency.airReceivable.queryByTab == agency.airReceivable.QUERY_SOURCE_BILL_NO){
				searchParams = {
						'billReceivableAgencyVo.billReceivableAgencyDto.sourceBillNo':agency.airReceivable.sourceBillNo,
						'billReceivableAgencyVo.billReceivableAgencyDto.queryByTab':agency.airReceivable.queryByTab
				}
			}
			Ext.apply(operation,{
				params :searchParams
			}); 
		}
	}
});

Ext.define('Foss.StlairReceivable.StlairReceivableQueryInfoTab', {
	extend:'Ext.tab.Panel',
	frame:false,
	bodyCls: 'autoHeight',
	cls: 'innerTabPanel',
	activeTab: 0,
	height : 190,
	items : [ {
		title:  agency.airReceivable.i18n('foss.stl.agency.common.queryByDate'),
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
		    	fieldLabel:agency.airReceivable.i18n('foss.stl.agency.airPayAndRec.startDateAgency'),
		    	value: stl.getTargetDate(new Date(),-1),
		    	editable:false,
		    	format:'Y-m-d',
		    	allowBlank:false
		    },{
		    	xtype:'datefield',
		   	 	name:'endDate',
		    	fieldLabel:agency.airReceivable.i18n('foss.stl.agency.airPayAndRec.endDateAgency'),
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
	            xtype      : 'radiogroup',
	            defaultType: 'radiofield',
	            colspan:3,
	            labelSeparator : "",
	            labelWidth:90,
	            fieldLabel:'&nbsp;',
	            layout: 'column',
	            items: [{
	                    boxLabel  :  agency.airReceivable.i18n('foss.stl.agency.airPayAndRec.customerDetial'),
	                    name      : 'color',
	                    checked : true,
	                    inputValue: '11',
	                    handler:function(val){
	                		if(val.value == true){
                    			var form =this.up('form').getForm();
        	                	form.findField('customerDetial').show();
        	                	form.findField('agencyDetial').hide();
        	                	form.findField('agencyDetial').setValue("");
	                		}
                		}
	                }, {
	                    boxLabel  : agency.airReceivable.i18n('foss.stl.agency.airPayAndRec.agencyDetial'),
	                    name      : 'color',
	                    inputValue: '22',
    	                handler:function(value){
    	                	if(value.value == true){
    	                		var form =this.up('form').getForm();
        	                	form.findField('agencyDetial').show();
        	                	form.findField('customerDetial').hide();
        	                	form.findField('customerDetial').setValue("");
    	                	}
                		}
	                }
	            ]
	        },{	
	        	xtype:'commonairlinesselector',
				fieldLabel: agency.airReceivable.i18n('foss.stl.agency.airPayAndRec.customerDetial'),// 航空公司
				name:'customerDetial',
				allowBlank:true,
				listWidth:300,//设置下拉框宽度
				isPaging:true //分页				
		    },{	
		    	xtype:'commonairagentselector',
				fieldLabel:agency.airReceivable.i18n('foss.stl.agency.airPayAndRec.agencyDetial'), 
				name:'agencyDetial',
				hidden:true,
				allowBlank:true,
				listWidth:300,//设置下拉框宽度
				isPaging:true //分页
		    },{
		    	xtype:'combobox',
		    	name:'approveStatus',
		    	fieldLabel:agency.airReceivable.i18n('foss.stl.agency.airPayAndRec.approveStatus'),
		    	editable:false,
				store:FossDataDictionary.getDataDictionaryStore(settlementDict.BILL_RECEIVABLE__APPROVE_STATUS,null,{
					 'valueCode': '',
               		 'valueName': '全部'
				}),
				queryModel:'local',
				displayField:'valueName',
				valueField:'valueCode',
				value:''
		    }, {
				xtype : 'combobox',
				name : 'productCode',
				fieldLabel : agency.airReceivable.i18n('foss.stl.agency.common.transportType'),
				store : Ext.create('Foss.pkp.ProductStore'),
				//multiSelect : true,
				queryMode : 'local',
				displayField : 'name',
				valueField : 'code',
				columnWidth : .3
									},{	
				border: 1,
				xtype:'container',
				colspan:3,
				defaultType:'button',
				layout:'column',
				items:[{
					text:agency.airReceivable.i18n('foss.stl.agency.common.reset'),
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
					text:agency.airReceivable.i18n('foss.stl.agency.common.query'),
					cls:'yellow_button',
					columnWidth:.15,
					handler:function(){
						var form = this.up('form').getForm();
						var me = this;
    					agency.airReceivable.queryBillReceivableEntityParams(form,me)
    				}
				}]
		    }]
        }]
	}, {
		title: agency.airReceivable.i18n('foss.stl.agency.airPayAndRec.queryByRecNo'),
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
 	 			emptyText:agency.airReceivable.i18n('foss.stl.agency.airPayAndRec.emptyText'),
 	            fieldLabel: agency.airReceivable.i18n('foss.stl.agency.receivable.receivableNo'),
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
					text:agency.airReceivable.i18n('foss.stl.agency.common.reset'),
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
					text:agency.airReceivable.i18n('foss.stl.agency.common.query'),
					cls:'yellow_button',
					columnWidth:.15,
					handler:function(){
						var form = this.up('form').getForm();
						var me = this;
    					agency.airReceivable.queryByReceivableNOs(form,me)
    				}
				}]
 		    }]
        }]
	}, {
		title: agency.airReceivable.i18n('foss.stl.agency.common.queryBySourceNo'),
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
 	 			emptyText:agency.airReceivable.i18n('foss.stl.agency.airPayAndRec.emptyText'),
 	            fieldLabel: agency.airReceivable.i18n('foss.stl.agency.airPayAndRec.billNo'),
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
						html:'<span style="color:red;">'+agency.airReceivable.i18n('foss.stl.agency.airPayAndRec.sourceBillNosNots')+'</span>'
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
					text:agency.airReceivable.i18n('foss.stl.agency.common.reset'),
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
					text:agency.airReceivable.i18n('foss.stl.agency.common.query'),
					cls:'yellow_button',
					columnWidth:.15,
					handler:function(){
						var form = this.up('form').getForm();
						var me = this;
    					agency.airReceivable.queryBySourceBillNOs(form,me)
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

//应收单列表
Ext.define('Foss.StlairReceivable.StlReceivableQueryInfoGrid',{
	extend:'Ext.grid.Panel',
    title: agency.airReceivable.i18n('foss.stl.agency.receivable.receivable'),
	frame:true,
	height:400,
	//plugins:SoperateColumnEditing,
	selModel:Ext.create('Ext.selection.CheckboxModel'),
    store: Ext.create('Foss.StlairReceivable.BillReceivableEntryStore'),
//    listeners:{
//        cellclick:function( me, td, cellIndex, record, tr, rowIndex, e, eOpts ){           
//			if(cellIndex==15&&!Ext.isEmpty(record.raw.notes)&&
//					record.raw.notes.length>50)
//			{
//				Ext.Msg.confirm({
//					title:'备注',
//					msg:record.raw.notes,
//					buttons: Ext.Msg.YES,
//					icon: Ext.Msg.INFO
//				});
//			}		
//        }
//    },    
	columns:[{ 
   		xtype:'actioncolumn',
		width:73,
		text: agency.airReceivable.i18n('foss.stl.agency.common.actionColumn'),
		align: 'center',
		items:[{
			iconCls : 'foss_icons_stl_auditing',
			tooltip:agency.airReceivable.i18n('foss.stl.agency.common.audit'),
			getClass:function(v,m,r,rowIndex){
    			if(r.get('approveStatus') == "AA"
    					|| !agency.airReceivable.isPermission('/stl-web/airReceivable/auditAirOtherBillReceivable.action')){
    				return 'statementBill_hide';
    			}else {
    				return 'foss_icons_stl_auditing';
    			}
    		},
			handler:function(grid, rowIndex, colIndex){
				agency.airReceivable.auditAirOtherOneBillReceivable(grid, rowIndex, colIndex)	
			}
		},{
			iconCls : 'foss_icons_stl_fauditing',
			tooltip:agency.airReceivable.i18n('foss.stl.agency.common.unAudit'),
			getClass:function(v,m,r,rowIndex){
    			if(r.get('approveStatus') == "NA"
    					|| !agency.airReceivable.isPermission('/stl-web/airReceivable/reverseAuditAirOtherBillReceivable.action')){
    				return 'statementBill_hide';
    			}else {
    				return 'foss_icons_stl_fauditing';
    			}
    		},
			handler:function(grid, rowIndex, colIndex){
				agency.airReceivable.reverseAuditAirOtherOneBillReceivable(grid, rowIndex, colIndex)	
			}
		},{
			iconCls : 'deppon_icons_cancel',
			tooltip:agency.airReceivable.i18n('foss.stl.agency.common.disable'),
			getClass:function(v,m,r,rowIndex){
    			if(!agency.airReceivable.isPermission('/stl-web/airReceivable/writeBackAirOtherBillReceivable.action')){
    				return 'statementBill_hide';
    			}else {
    				return 'deppon_icons_cancel';
    			}
    		},
			handler:function(grid, rowIndex, colIndex){
				agency.airReceivable.writeBackAirOtherOneReceivable(grid, rowIndex, colIndex)	
			}
		}]
	},{
		header: agency.airReceivable.i18n('foss.stl.agency.receivable.receivableNo'), 
		dataIndex: 'receivableNo'
	},{
		header: agency.airReceivable.i18n('foss.stl.agency.airPayAndRec.billNo'), 
		dataIndex: 'sourceBillNo'
	},{
		header: agency.airReceivable.i18n('foss.stl.agency.airPayAndRec.customerCode'), 
		dataIndex: 'customerCode'		
	},{
		header: agency.airReceivable.i18n('foss.stl.agency.airPayAndRec.customerName'), 
		dataIndex: 'customerName'
	},{
		header: agency.airReceivable.i18n('foss.stl.agency.airPayAndRec.approveStatus'), 
		dataIndex: 'approveStatus',
		renderer:function(value){
    		var displayField = FossDataDictionary.rendererSubmitToDisplay(value,settlementDict.BILL_RECEIVABLE__APPROVE_STATUS);
    		return displayField;
    	}
	},{
		header: agency.airReceivable.i18n('foss.stl.agency.common.transportType'), 
		dataIndex:'productCode',
		renderer : function(value) {
							return Foss.pkp.ProductData.rendererSubmitToDisplay(value);
						}
	},{
		header: agency.airReceivable.i18n('foss.stl.agency.airPayAndRec.origOrgCode'), 
		hidden:true,
		dataIndex:'origOrgCode'
	},{
		header: agency.airReceivable.i18n('foss.stl.agency.airPayAndRec.origOrgName'), 
		dataIndex:'origOrgName'
	},{
		header: agency.airReceivable.i18n('foss.stl.agency.airPayAndRec.statementBillNo'), 
		dataIndex: 'statementBillNo',
		hidden:true
	},{
		header: agency.airReceivable.i18n('foss.stl.agency.airPayAndRec.amount'), 
		dataIndex: 'amount'				
	},{
		header: agency.airReceivable.i18n('foss.stl.agency.airPayAndRec.verifyAmount'),
		dataIndex: 'verifyAmount' 
	},{
		header: agency.airReceivable.i18n('foss.stl.agency.airPayAndRec.unverifyAmount'), 
		dataIndex: 'unverifyAmount'				
	},{
		header: agency.airReceivable.i18n('foss.stl.agency.airPayAndRec.businessDate'),
		dataIndex: 'businessDate',
		renderer:function(value){
			if(value!=null){
				return Ext.Date.format(new Date(value), 'Y-m-d');
			}else{
				return null;
			}
		} 
	},{
		header: agency.airReceivable.i18n('foss.stl.agency.airPayAndRec.accountDate'),
		dataIndex: 'accountDate',
		renderer:function(value){
			if(value!=null){
				return Ext.Date.format(new Date(value), 'Y-m-d');
			}else{
				return null;
			}
		} 
	},{
		header: agency.airReceivable.i18n('foss.stl.agency.airPayAndRec.notes'), 
		dataIndex: 'notes',
		width:500
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
				text:agency.airReceivable.i18n('foss.stl.agency.common.export'),
				disabled:!agency.airReceivable.isPermission('/stl-web/agency/airReceivableListExport.action'),
				hidden:!agency.airReceivable.isPermission('/stl-web/agency/airReceivableListExport.action'),
				handler : agency.airReceivable.airReceivableListExport
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
				id:'Foss_StlairReceivable_StlairReceivableQueryInfoGrid_airReceivableTotalRows_Id',
				columnWidth:.1,
				fieldLabel:agency.airReceivable.i18n('foss.stl.agency.common.sum'),
				labelWidth:50
			},{
				xtype:'container',
				border:false,
				html:'&nbsp;',
				columnWidth:.005
			},{
				xtype:'displayfield',
				allowBlank:true,
				id:'Foss_StlairReceivable_StlairReceivableQueryInfoGrid_airReceivableTotalAmount_Id',
				columnWidth:.1,
				fieldLabel:agency.airReceivable.i18n('foss.stl.agency.common.totalAmount'),
				labelWidth:50
			},{
				xtype:'container',
				border:false,
				html:'&nbsp;',
				columnWidth:.01
			},{
				xtype:'displayfield',
				allowBlank:true,
				id:'Foss_StlairReceivable_StlairReceivableQueryInfoGrid_airReceivableVerifyAmount_Id',
				columnWidth:.1,
				fieldLabel:agency.airReceivable.i18n('foss.stl.agency.airPayAndRec.verifyAmount'),
				labelWidth:80
			},{
				xtype:'container',
				border:false,
				html:'&nbsp;',
				columnWidth:.05
			},{
				xtype:'displayfield',
				allowBlank:true,
				id:'Foss_StlairReceivable_StlairReceivableQueryInfoGrid_airReceivableUnverifyAmount_Id',
				columnWidth:.1,
				fieldLabel:agency.airReceivable.i18n('foss.stl.agency.airPayAndRec.unverifyAmount'),
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
				text:agency.airReceivable.i18n('foss.stl.agency.common.disable'),
				disabled:!agency.airReceivable.isPermission('/stl-web/agency/writeBackAirOtherBillReceivable.action'),
				hidden:!agency.airReceivable.isPermission('/stl-web/agency/writeBackAirOtherBillReceivable.action'),
				columnWidth:.1,
				handler:function(){
					var me = this;
    				agency.airReceivable.writeBackAirOtherBillReceivable(me)
    			}
			},{
				xtype:'container',
				border:false,
				html:'&nbsp;',
				columnWidth:.005
			},{
				xtype:'button',
				text:agency.airReceivable.i18n('foss.stl.agency.common.audit'),
				disabled:!agency.airReceivable.isPermission('/stl-web/agency/auditAirOtherBillReceivable.action'),
				hidden:!agency.airReceivable.isPermission('/stl-web/agency/auditAirOtherBillReceivable.action'),
				columnWidth:.1,
				handler:agency.airReceivable.auditAirOtherBillReceivable
			},{
				xtype:'container',
				border:false,
				html:'&nbsp;',
				columnWidth:.005
			},{
				xtype:'button',
				text:agency.airReceivable.i18n('foss.stl.agency.common.unAudit'),
				disabled:!agency.airReceivable.isPermission('/stl-web/agency/reverseAuditAirOtherBillReceivable.action'),
				hidden:!agency.airReceivable.isPermission('/stl-web/agency/reverseAuditAirOtherBillReceivable.action'),
				columnWidth:.1,
				handler:agency.airReceivable.reverseAuditAirOtherBillReceivable
			}]
		}]
		me.callParent();
	}
});


//初始化界面
Ext.onReady(function() {
	Ext.QuickTips.init();

	var StlairReceivableQueryInfoTab = Ext.create('Foss.StlairReceivable.StlairReceivableQueryInfoTab');
	
	var StlReceivableQueryInfoGrid = Ext.create('Foss.StlairReceivable.StlReceivableQueryInfoGrid',{
		id:'Foss_StlairReceivable_StlReceivableQueryInfoGrid_Id'
	});
	
	Ext.create('Ext.panel.Panel',{
		id: 'T_agency-airReceivable_content',
		cls: "panelContentNToolbar",
		bodyCls: 'panelContentNToolbar-body',
		layout: 'auto',
		getQueryInfoTab:function(){
    		return StlairReceivableQueryInfoTab;
    	},
		items: [StlairReceivableQueryInfoTab,StlReceivableQueryInfoGrid],
		renderTo: 'T_agency-airReceivable-body'
	});
});