/**
 * 提示信息
 * @param {} message 
 * @param {} yesFn
 * @param {} noFn
 */
agency.airPayable.billAirPayableConfirmAlert = function(message,yesFn,noFn){
	Ext.Msg.confirm(agency.airPayable.i18n('foss.stl.agency.common.alert'),message,function(o){
		if(o=='yes'){
			yesFn();
		}else{
			noFn();
		}
	});
};

agency.airPayable.QUERY_BYDATE = 'TD';//按客户查询
agency.airPayable.QUERY_PAYABLE_NO = 'TP';//按单号查询
agency.airPayable.QUERY_SOURCE_BILL_NO = 'TSB';//按单号查询

/**
 * 按日期,客户查询
 */
agency.airPayable.queryBillPayableEntityParams = function(form,me){
	agency.airPayable.startBusinessDate = form.findField('startDate').getValue();
	agency.airPayable.endBusinessDate = form.findField('endDateAgency').getValue();
	agency.airPayable.customerDetialCode = form.findField('customerDetial').getValue();
	agency.airPayable.agencyDetialCode = form.findField('agencyDetial').getValue();
	agency.airPayable.approveStatus = form.findField('approveStatus').getValue();
	agency.airPayable.productCode = form.findField('productCode').getValue();
	agency.airPayable.queryByTab = agency.airPayable.QUERY_BYDATE;
	if(agency.airPayable.startBusinessDate==null 
			|| agency.airPayable.startBusinessDate==''){
		Ext.Msg.alert(agency.airPayable.i18n('foss.stl.agency.common.alert'),
				agency.airPayable.i18n('foss.stl.agency.airPayAndRec.notDateNull'));
		return false;
	}

	if(agency.airPayable.endBusinessDate==null 
			|| agency.airPayable.endBusinessDate==''){
		Ext.Msg.alert(agency.airPayable.i18n('foss.stl.agency.common.alert'),
				agency.airPayable.i18n('foss.stl.agency.airPayAndRec.notDateNull'));
		return false;
	}
	
	var compareTwoDate = stl.compareTwoDate(agency.airPayable.startBusinessDate
			,agency.airPayable.endBusinessDate);
	if(compareTwoDate>stl.DATELIMITDAYS_MONTH){
		Ext.Msg.alert(agency.airPayable.i18n('foss.stl.agency.common.alert'),
				'你好，您所查询的时间段已超过'+stl.DATELIMITDAYS_MONTH+'天，请重新输入结束时间');
		return false;
	}else if(compareTwoDate<1){
		Ext.Msg.alert(agency.airPayable.i18n('foss.stl.agency.common.alert'),
				'你好，结束时间不能小于开始时间，请重新输入!');
		return false;
	}
	agency.airPayable.customerCode = "";
	if(agency.airPayable.customerDetialCode != "" && agency.airPayable.customerDetialCode != null){
		agency.airPayable.customerCode = agency.airPayable.customerDetialCode;
	}else if(agency.airPayable.agencyDetialCode != "" && agency.airPayable.agencyDetialCode != null){
		agency.airPayable.customerCode = agency.airPayable.agencyDetialCode;
	}
	Ext.getCmp('Foss_StlairPayable_StlPayableQueryInfoGrid_Id').store.on('beforeload',function(store, operation, eOpts){
		Ext.apply(operation,{
			params :{
				'billPayableAgencyVo.billPayableAgencyDto.startBusinessDate':agency.airPayable.startBusinessDate,
				'billPayableAgencyVo.billPayableAgencyDto.endBusinessDate':agency.airPayable.endBusinessDate,
				'billPayableAgencyVo.billPayableAgencyDto.customerCode':agency.airPayable.customerCode,
				'billPayableAgencyVo.billPayableAgencyDto.approveStatus':agency.airPayable.approveStatus,
				'billPayableAgencyVo.billPayableAgencyDto.productCode':agency.airPayable.productCode,
				'billPayableAgencyVo.billPayableAgencyDto.queryByTab':agency.airPayable.queryByTab
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
		Ext.getCmp('Foss_StlairPayable_StlPayableQueryInfoGrid_Id').store.loadPage(1,{
			callback: function(records, operation, success) {
    			var rawData = Ext.getCmp('Foss_StlairPayable_StlPayableQueryInfoGrid_Id').store.proxy.reader.rawData;
    			//当success:false ,isException:false  --业务异常
    		    if(!success && ! rawData.isException){
    		    	Ext.Msg.alert(agency.airPayable.i18n('foss.stl.agency.common.alert'),rawData.message);
					return false;
				}
    			if(success){
    				var result =   Ext.decode(operation.response.responseText);
    			    Ext.getCmp('Foss_StlairPayable_StlairPayableQueryInfoGrid_airPayableTotalRows_Id').setValue(result.billPayableAgencyVo.billPayableAgencyDto.totalNum);
    				Ext.getCmp('Foss_StlairPayable_StlairPayableQueryInfoGrid_airPayableTotalAmount_Id').setValue(result.billPayableAgencyVo.billPayableAgencyDto.totalAmount);
    				Ext.getCmp('Foss_StlairPayable_StlairPayableQueryInfoGrid_airPayableVerifyAmount_Id').setValue(result.billPayableAgencyVo.billPayableAgencyDto.verifyTotalAmount);
    				Ext.getCmp('Foss_StlairPayable_StlairPayableQueryInfoGrid_airPayableUnverifyAmount_Id').setValue(result.billPayableAgencyVo.billPayableAgencyDto.unverifyTotalAmount);			
    			}
    			me.enable(true);
			}
			
		});
		
	}else{
		Ext.Msg.alert(agency.airPayable.i18n('foss.stl.agency.common.alert'),
				agency.airPayable.i18n('foss.stl.agency.airPayAndRec.pageCheck'));
		me.enable(true);
		return false;
	}	
}

/**
 * 按应付单号查询
 * @returns {Boolean}
 */
agency.airPayable.queryByPayableNOs = function(form,me){
	agency.airPayable.payableNo = form.findField('payableNos').getValue();
	agency.airPayable.queryByTab = agency.airPayable.QUERY_PAYABLE_NO;
	//单号不能为空
	if(agency.airPayable.payableNo==null ||
			agency.airPayable.payableNo==''){
		Ext.Msg.alert(agency.airPayable.i18n('foss.stl.agency.common.alert'),
				agency.airPayable.i18n('foss.stl.agency.payable.notNullPayableNo'));
		return false;
	}
	//单号个数不能超过10个
	var payableNosStr = new Array();
	payableNosStr = stl.splitToArray(agency.airPayable.payableNo);
	if(payableNosStr.length>10){
		Ext.Msg.alert(agency.airPayable.i18n('foss.stl.agency.common.alert'),
				agency.airPayable.i18n('foss.stl.agency.airPayAndRec.notGreaterThanTen'));
		return false;
	}
	Ext.getCmp('Foss_StlairPayable_StlPayableQueryInfoGrid_Id').store.on('beforeload',function(store, operation, eOpts){
		Ext.apply(operation,{
			params :{
				'billPayableAgencyVo.billPayableAgencyDto.payableNo':agency.airPayable.payableNo,
				'billPayableAgencyVo.billPayableAgencyDto.queryByTab':agency.airPayable.queryByTab
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
		Ext.getCmp('Foss_StlairPayable_StlPayableQueryInfoGrid_Id').store.loadPage(1,{
    		callback: function(records, operation, success) {
    			var rawData = Ext.getCmp('Foss_StlairPayable_StlPayableQueryInfoGrid_Id').store.proxy.reader.rawData;
    			//当success:false ,isException:false  --业务异常
    		    if(!success && ! rawData.isException){
    		    	Ext.Msg.alert(agency.airPayable.i18n('foss.stl.agency.common.alert'),rawData.message);
    				return false;
    			}
    			if(success){
    				var result =   Ext.decode(operation.response.responseText);
    			    Ext.getCmp('Foss_StlairPayable_StlairPayableQueryInfoGrid_airPayableTotalRows_Id').setValue(result.billPayableAgencyVo.billPayableAgencyDto.totalNum);
    				Ext.getCmp('Foss_StlairPayable_StlairPayableQueryInfoGrid_airPayableTotalAmount_Id').setValue(result.billPayableAgencyVo.billPayableAgencyDto.totalAmount);
    				Ext.getCmp('Foss_StlairPayable_StlairPayableQueryInfoGrid_airPayableVerifyAmount_Id').setValue(result.billPayableAgencyVo.billPayableAgencyDto.verifyTotalAmount);
    				Ext.getCmp('Foss_StlairPayable_StlairPayableQueryInfoGrid_airPayableUnverifyAmount_Id').setValue(result.billPayableAgencyVo.billPayableAgencyDto.unverifyTotalAmount);			
			    }
    			me.enable(true);
			}
			
		});
		
	}else{
		Ext.Msg.alert(agency.airPayable.i18n('foss.stl.agency.common.alert'),
				agency.airPayable.i18n('foss.stl.agency.airPayAndRec.pageCheck'));
		me.enable(true);
		return false;
	}	
}

/**
 * 按航空正单号查询
 * @returns {Boolean}
 */
agency.airPayable.queryBySourceBillNOs = function(form,me){
	agency.airPayable.sourceBillNo = form.findField('sourceBillNos').getValue();
	agency.airPayable.queryByTab = agency.airPayable.QUERY_SOURCE_BILL_NO;
	//单号不能为空
	if(agency.airPayable.sourceBillNo==null ||
			agency.airPayable.sourceBillNo==''){
		Ext.Msg.alert(agency.airPayable.i18n('foss.stl.agency.common.alert'),
				agency.airPayable.i18n('foss.stl.agency.airPayAndRec.notNullSourceBillNo'));
		return false;
	}
	//单号个数不能超过10个
	var payableNosStr = new Array();
	payableNosStr = stl.splitToArray(agency.airPayable.sourceBillNo);
	if(payableNosStr.length>10){
		Ext.Msg.alert(agency.airPayable.i18n('foss.stl.agency.common.alert'),
				agency.airPayable.i18n('foss.stl.agency.airPayAndRec.notGreaterThanTen'));
		return false;
	}
	
	Ext.getCmp('Foss_StlairPayable_StlPayableQueryInfoGrid_Id').store.on('beforeload',function(store, operation, eOpts){
		Ext.apply(operation,{
			params :{
				'billPayableAgencyVo.billPayableAgencyDto.sourceBillNo':agency.airPayable.sourceBillNo,
				'billPayableAgencyVo.billPayableAgencyDto.queryByTab':agency.airPayable.queryByTab
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
		Ext.getCmp('Foss_StlairPayable_StlPayableQueryInfoGrid_Id').store.loadPage(1,{
			callback: function(records, operation, success) {
    			var rawData = Ext.getCmp('Foss_StlairPayable_StlPayableQueryInfoGrid_Id').store.proxy.reader.rawData;
    			//当success:false ,isException:false  --业务异常
    		    if(!success && ! rawData.isException){
    		    	Ext.Msg.alert(agency.airPayable.i18n('foss.stl.agency.common.alert'),rawData.message);
    				return false;
    			}
    			if(success){
    				var result =   Ext.decode(operation.response.responseText);
    			    Ext.getCmp('Foss_StlairPayable_StlairPayableQueryInfoGrid_airPayableTotalRows_Id').setValue(result.billPayableAgencyVo.billPayableAgencyDto.totalNum);
    				Ext.getCmp('Foss_StlairPayable_StlairPayableQueryInfoGrid_airPayableTotalAmount_Id').setValue(result.billPayableAgencyVo.billPayableAgencyDto.totalAmount);
    				Ext.getCmp('Foss_StlairPayable_StlairPayableQueryInfoGrid_airPayableVerifyAmount_Id').setValue(result.billPayableAgencyVo.billPayableAgencyDto.verifyTotalAmount);
    				Ext.getCmp('Foss_StlairPayable_StlairPayableQueryInfoGrid_airPayableUnverifyAmount_Id').setValue(result.billPayableAgencyVo.billPayableAgencyDto.unverifyTotalAmount);			
				}
    			me.enable(true);
			}
			
		});
		
	}else{
		Ext.Msg.alert(agency.airPayable.i18n('foss.stl.agency.common.alert'),
				agency.airPayable.i18n('foss.stl.agency.airPayAndRec.pageCheck'));
		me.enable(true);
		return false;
	}	
}

//导出空运其他应收单
agency.airPayable.airPayableListExport = function(){
	//获取表格
	grid = Ext.getCmp('Foss_StlairPayable_StlPayableQueryInfoGrid_Id');
	//如果没有查询到数据，则不能进行导出操作
	if(grid.store.data.length == 0){
		Ext.Msg.alert(agency.airPayable.i18n('foss.stl.agency.common.alert'),
				agency.airPayable.i18n('foss.stl.agency.airPayAndRec.exportResultSetNotnull'));
		return false;
	}
	
	if(!Ext.fly('downloadAttachFileForm')){
	    var frm = document.createElement('form');
	    frm.id = 'downloadAttachFileForm';
	    frm.style.display = 'none';
	    document.body.appendChild(frm);
	}
	if(agency.airPayable.queryByTab == agency.airPayable.QUERY_BYDATE){
		searchParams = {
				'billPayableAgencyVo.billPayableAgencyDto.startBusinessDate':agency.airPayable.startBusinessDate,
				'billPayableAgencyVo.billPayableAgencyDto.endBusinessDate':agency.airPayable.endBusinessDate,
				'billPayableAgencyVo.billPayableAgencyDto.approveStatus':agency.airPayable.approveStatus,
				'billPayableAgencyVo.billPayableAgencyDto.customerCode':agency.airPayable.customerCode,
				'billPayableAgencyVo.billPayableAgencyDto.queryByTab':agency.airPayable.queryByTab
		}
	}
	else if(agency.airPayable.queryByTab == agency.airPayable.QUERY_PAYABLE_NO){
		searchParams = {
				'billPayableAgencyVo.billPayableAgencyDto.payableNo':agency.airPayable.payableNo,
				'billPayableAgencyVo.billPayableAgencyDto.queryByTab':agency.airPayable.queryByTab
		}
	}
	else if(agency.airPayable.queryByTab == agency.airPayable.QUERY_SOURCE_BILL_NO){
		searchParams = {
				'billPayableAgencyVo.billPayableAgencyDto.sourceBillNo':agency.airPayable.sourceBillNo,
				'billPayableAgencyVo.billPayableAgencyDto.queryByTab':agency.airPayable.queryByTab
		}
	}
	var yesFn=function(){
		Ext.Ajax.request({
		url:agency.realPath('airPayableListExport.action'),
		form: Ext.fly('downloadAttachFileForm'),
		params :searchParams,
		isUpload: true,
		success:function(response){
			//如果异常信息有值，则弹出提示
			var result = Ext.decode(response.responseText);	
	    	if(!Ext.isEmpty(result.errorMessage)){
	    		Ext.Msg.alert(agency.airPayable.i18n('foss.stl.agency.common.alert'),result.errorMessage);
	    		return false;
	    	}
	    	Ext.ux.Toast.msg(agency.airPayable.i18n('foss.stl.agency.common.alert'),
	    			agency.airPayable.i18n('foss.stl.agency.airPayAndRec.exportSuccess'), 'success', 1000);
		},
		failure:function(response){
			Ext.ux.Toast.msg(agency.airPayable.i18n('foss.stl.agency.common.alert'), 
					agency.airPayable.i18n('foss.stl.agency.airPayAndRec.exportError'), 'error', 1000);
		}
	});
	};
	var noFn=function(){
	 	return false;
	};
	agency.airPayable.billAirPayableConfirmAlert(
			agency.airPayable.i18n('foss.stl.agency.airPayAndRec.isExport'),yesFn,noFn);
}

agency.airPayable.queryCondition = function(){
	var searchParams;
	if(agency.airPayable.queryByTab == agency.airPayable.QUERY_BYDATE){
		searchParams = {
				'startBusinessDate':agency.airPayable.startBusinessDate,
				'endBusinessDate':agency.airPayable.endBusinessDate,
				'approveStatus':agency.airPayable.approveStatus,
				'customerCode':agency.airPayable.customerCode,
				'queryByTab':agency.airPayable.queryByTab
		}
	}
	else if(agency.airPayable.queryByTab == agency.airPayable.QUERY_PAYABLE_NO){
		searchParams = {
				'payableNo':agency.airPayable.payableNo,
				'queryByTab':agency.airPayable.queryByTab
		}
	}
	else if(agency.airPayable.queryByTab == agency.airPayable.QUERY_SOURCE_BILL_NO){
		searchParams = {
				'sourceBillNo':agency.airPayable.sourceBillNo,
				'queryByTab':agency.airPayable.queryByTab
		}
	}
	return searchParams;
}
//空运其他管理单条作废
agency.airPayable.writeBackAirOtherOnePayable = function(grid, rowIndex, colIndex){
	var selectionPayable = grid.getStore().getAt(rowIndex);
	var jsonDataPayable = new Array();
	jsonDataPayable.push(selectionPayable.data);
	var entity = new Object();
	entity.billPayableAgencyDtos = jsonDataPayable;
	var searchParams = agency.airPayable.queryCondition(searchParams);
	entity.billPayableAgencyDto = searchParams;
	var yesFn=function(){
    	Ext.Ajax.request({
    		url:agency.realPath('writeBackAirOtherBillPayable.action'),
    		jsonData:{'billPayableAgencyVo':entity},
    		method:'post',
    		success:function(response){
    			Ext.getCmp('Foss_StlairPayable_StlPayableQueryInfoGrid_Id').store.load({
    				callback: function(records, operation, success) {
    				    var result =   Ext.decode(operation.response.responseText);  
    				    if(success){
    						if(result.isException){
    							Ext.Msg.alert(agency.airPayable.i18n('foss.stl.agency.common.alert'),result.message);
    							return false;
    						}
    	    			    Ext.getCmp('Foss_StlairPayable_StlairPayableQueryInfoGrid_airPayableTotalRows_Id').setValue(result.billPayableAgencyVo.billPayableAgencyDto.totalNum);
    	    				Ext.getCmp('Foss_StlairPayable_StlairPayableQueryInfoGrid_airPayableTotalAmount_Id').setValue(result.billPayableAgencyVo.billPayableAgencyDto.totalAmount);
    	    				Ext.getCmp('Foss_StlairPayable_StlairPayableQueryInfoGrid_airPayableVerifyAmount_Id').setValue(result.billPayableAgencyVo.billPayableAgencyDto.verifyTotalAmount);
    	    				Ext.getCmp('Foss_StlairPayable_StlairPayableQueryInfoGrid_airPayableUnverifyAmount_Id').setValue(result.billPayableAgencyVo.billPayableAgencyDto.unverifyTotalAmount);			
    				    }
    				}
    				
    			});
    			Ext.Msg.alert(agency.airPayable.i18n('foss.stl.agency.common.alert'),
    					agency.airPayable.i18n('foss.stl.agency.airPayAndRec.disableSuccess'));
    		},
    		exception:function(response){
    			var result = Ext.decode(response.responseText);	
    			Ext.Msg.alert(agency.airPayable.i18n('foss.stl.agency.common.alert'),result.message);
    		},
    		failure:function(response){
    			var result = Ext.decode(response.responseText);
    		}
    	});
	};
	var noFn=function(){
	 	return false;
	};
	agency.airPayable.billAirPayableConfirmAlert(agency.airPayable.i18n('foss.stl.agency.airPayAndRec.isDisable'),yesFn,noFn);
}
//空运其他管理作废
agency.airPayable.writeBackAirOtherBillPayable = function(me){
	var selectionPayable = Ext.getCmp('Foss_StlairPayable_StlPayableQueryInfoGrid_Id').getSelectionModel().getSelection();
	
	if(selectionPayable.length==0){
		Ext.Msg.alert(agency.airPayable.i18n('foss.stl.agency.common.alert'),
				agency.airPayable.i18n('foss.stl.agency.airPayAndRec.disableText'));
		return false;
	}
	var jsonDataPayable = new Array();
	for(var i=0;i<selectionPayable.length;i++){
		jsonDataPayable.push(selectionPayable[i].data);
	}
	var entity = new Object();
	entity.billPayableAgencyDtos = jsonDataPayable;
	var searchParams = agency.airPayable.queryCondition(searchParams);
	entity.billPayableAgencyDto = searchParams;
	
    var yesFn=function(){
    	//设置该按钮灰掉
		me.disable(false);
		//30秒后自动解除灰掉效果
		setTimeout(function() {
			me.enable(true);
		}, 30000);
    	Ext.Ajax.request({
    		url:agency.realPath('writeBackAirOtherBillPayable.action'),
    		jsonData:{'billPayableAgencyVo':entity},
    		method:'post',
    		success:function(response){
    			Ext.getCmp('Foss_StlairPayable_StlPayableQueryInfoGrid_Id').store.load({
    				callback: function(records, operation, success) {
    				    var result =   Ext.decode(operation.response.responseText);  
    				    if(success){
    						if(result.isException){
    							Ext.Msg.alert(agency.airPayable.i18n('foss.stl.agency.common.alert'),result.message);
    							return false;
    						}
    	    			    Ext.getCmp('Foss_StlairPayable_StlairPayableQueryInfoGrid_airPayableTotalRows_Id').setValue(result.billPayableAgencyVo.billPayableAgencyDto.totalNum);
    	    				Ext.getCmp('Foss_StlairPayable_StlairPayableQueryInfoGrid_airPayableTotalAmount_Id').setValue(result.billPayableAgencyVo.billPayableAgencyDto.totalAmount);
    	    				Ext.getCmp('Foss_StlairPayable_StlairPayableQueryInfoGrid_airPayableVerifyAmount_Id').setValue(result.billPayableAgencyVo.billPayableAgencyDto.verifyTotalAmount);
    	    				Ext.getCmp('Foss_StlairPayable_StlairPayableQueryInfoGrid_airPayableUnverifyAmount_Id').setValue(result.billPayableAgencyVo.billPayableAgencyDto.unverifyTotalAmount);			
    				    }
    				}
    				
    			});
    			me.enable(true);
    			Ext.Msg.alert(agency.airPayable.i18n('foss.stl.agency.common.alert'),
    					agency.airPayable.i18n('foss.stl.agency.airPayAndRec.disableSuccess'));
    		},
    		exception:function(response){
    			var result = Ext.decode(response.responseText);	
    			Ext.Msg.alert(agency.airPayable.i18n('foss.stl.agency.common.alert'),result.message);
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
	agency.airPayable.billAirPayableConfirmAlert(agency.airPayable.i18n('foss.stl.agency.airPayAndRec.isDisable'),yesFn,noFn);
}

//单条数据审核
agency.airPayable.auditAirOtherOneBillPayable = function(grid, rowIndex, colIndex){
	var selectionPayable = grid.getStore().getAt(rowIndex);
	var jsonDataPayable = new Array();
	jsonDataPayable.push(selectionPayable.data);
	var entity = new Object();
	entity.billPayableAgencyDtos = jsonDataPayable;
	var searchParams = agency.airPayable.queryCondition(searchParams);
	entity.billPayableAgencyDto = searchParams;
	var yesFn=function(){	
    	Ext.Ajax.request({
    		url:agency.realPath('auditAirOtherBillPayable.action'),
    		jsonData:{'billPayableAgencyVo':entity},
    		method:'post',
    		success:function(response){
    			Ext.getCmp('Foss_StlairPayable_StlPayableQueryInfoGrid_Id').store.load({
    				callback: function(records, operation, success) {
    				    var result =   Ext.decode(operation.response.responseText);  
    				    if(success){
    						if(result.isException){
    							Ext.Msg.alert(agency.airPayable.i18n('foss.stl.agency.common.alert'),result.message);
    							return false;
    						}
    	    			    Ext.getCmp('Foss_StlairPayable_StlairPayableQueryInfoGrid_airPayableTotalRows_Id').setValue(result.billPayableAgencyVo.billPayableAgencyDto.totalNum);
    	    				Ext.getCmp('Foss_StlairPayable_StlairPayableQueryInfoGrid_airPayableTotalAmount_Id').setValue(result.billPayableAgencyVo.billPayableAgencyDto.totalAmount);
    	    				Ext.getCmp('Foss_StlairPayable_StlairPayableQueryInfoGrid_airPayableVerifyAmount_Id').setValue(result.billPayableAgencyVo.billPayableAgencyDto.verifyTotalAmount);
    	    				Ext.getCmp('Foss_StlairPayable_StlairPayableQueryInfoGrid_airPayableUnverifyAmount_Id').setValue(result.billPayableAgencyVo.billPayableAgencyDto.unverifyTotalAmount);			
    				    }
    				}
    			});
    			Ext.Msg.alert(agency.airPayable.i18n('foss.stl.agency.common.alert'),
    					agency.airPayable.i18n('foss.stl.agency.airPayAndRec.auditSuccess'));
    		},
    		exception:function(response){
    			var result = Ext.decode(response.responseText);	
    			Ext.Msg.alert(agency.airPayable.i18n('foss.stl.agency.common.alert'),result.message);
    		},
    		failure:function(response){
    			var result = Ext.decode(response.responseText);
    		}
    	});
	};
	var noFn=function(){
	 	return false;
	};
	agency.airPayable.billAirPayableConfirmAlert(agency.airPayable.i18n('foss.stl.agency.airPayAndRec.isAudit'),yesFn,noFn);
}

//空运其他管理审核
agency.airPayable.auditAirOtherBillPayable = function(){
	var selectionPayable = Ext.getCmp('Foss_StlairPayable_StlPayableQueryInfoGrid_Id').getSelectionModel().getSelection();
	
	if(selectionPayable.length==0){
		Ext.Msg.alert(agency.airPayable.i18n('foss.stl.agency.common.alert'),
				agency.airPayable.i18n('foss.stl.agency.airPayAndRec.auditText'));
		return false;
	}
	var jsonDataPayable = new Array();
	for(var i=0;i<selectionPayable.length;i++){
		jsonDataPayable.push(selectionPayable[i].data);
	}
	var entity = new Object();
	entity.billPayableAgencyDtos = jsonDataPayable;
	var searchParams = agency.airPayable.queryCondition(searchParams);
	entity.billPayableAgencyDto = searchParams;
	
	var yesFn=function(){	
    	Ext.Ajax.request({
    		url:agency.realPath('auditAirOtherBillPayable.action'),
    		jsonData:{'billPayableAgencyVo':entity},
    		method:'post',
    		success:function(response){
    			Ext.getCmp('Foss_StlairPayable_StlPayableQueryInfoGrid_Id').store.load({
    				callback: function(records, operation, success) {
    				    var result =   Ext.decode(operation.response.responseText);  
    				    if(success){
    						if(result.isException){
    							Ext.Msg.alert(agency.airPayable.i18n('foss.stl.agency.common.alert'),result.message);
    							return false;
    						}
    	    			    Ext.getCmp('Foss_StlairPayable_StlairPayableQueryInfoGrid_airPayableTotalRows_Id').setValue(result.billPayableAgencyVo.billPayableAgencyDto.totalNum);
    	    				Ext.getCmp('Foss_StlairPayable_StlairPayableQueryInfoGrid_airPayableTotalAmount_Id').setValue(result.billPayableAgencyVo.billPayableAgencyDto.totalAmount);
    	    				Ext.getCmp('Foss_StlairPayable_StlairPayableQueryInfoGrid_airPayableVerifyAmount_Id').setValue(result.billPayableAgencyVo.billPayableAgencyDto.verifyTotalAmount);
    	    				Ext.getCmp('Foss_StlairPayable_StlairPayableQueryInfoGrid_airPayableUnverifyAmount_Id').setValue(result.billPayableAgencyVo.billPayableAgencyDto.unverifyTotalAmount);			
    				    }
    				}
    			});
    			Ext.Msg.alert(agency.airPayable.i18n('foss.stl.agency.common.alert'),
    					agency.airPayable.i18n('foss.stl.agency.airPayAndRec.auditSuccess'));
    		},
    		exception:function(response){
    			var result = Ext.decode(response.responseText);	
    			Ext.Msg.alert(agency.airPayable.i18n('foss.stl.agency.common.alert'),result.message);
    		},
    		failure:function(response){
    			var result = Ext.decode(response.responseText);
    		}
    	});
	};
	var noFn=function(){
	 	return false;
	};
	agency.airPayable.billAirPayableConfirmAlert(agency.airPayable.i18n('foss.stl.agency.airPayAndRec.isAudit'),yesFn,noFn);
}

//单条数据反审核
agency.airPayable.reverseAuditAirOtherOneBillPayable = function(grid, rowIndex, colIndex){
	var selectionPayable = grid.getStore().getAt(rowIndex);
	var jsonDataPayable = new Array();
	jsonDataPayable.push(selectionPayable.data);
	var entity = new Object();
	entity.billPayableAgencyDtos = jsonDataPayable;
	var searchParams = agency.airPayable.queryCondition(searchParams);
	entity.billPayableAgencyDto = searchParams;
	var yesFn=function(){
    	Ext.Ajax.request({
    		url:agency.realPath('reverseAuditAirOtherBillPayable.action'),
    		jsonData:{'billPayableAgencyVo':entity},
    		method:'post',
    		success:function(response){
    			Ext.getCmp('Foss_StlairPayable_StlPayableQueryInfoGrid_Id').store.load({
    				callback: function(records, operation, success) {
    				    var result =   Ext.decode(operation.response.responseText);  
    				    if(success){
    						if(result.isException){
    							Ext.Msg.alert(agency.airPayable.i18n('foss.stl.agency.common.alert'),result.message);
    							return false;
    						}
    	    			    Ext.getCmp('Foss_StlairPayable_StlairPayableQueryInfoGrid_airPayableTotalRows_Id').setValue(result.billPayableAgencyVo.billPayableAgencyDto.totalNum);
    	    				Ext.getCmp('Foss_StlairPayable_StlairPayableQueryInfoGrid_airPayableTotalAmount_Id').setValue(result.billPayableAgencyVo.billPayableAgencyDto.totalAmount);
    	    				Ext.getCmp('Foss_StlairPayable_StlairPayableQueryInfoGrid_airPayableVerifyAmount_Id').setValue(result.billPayableAgencyVo.billPayableAgencyDto.verifyTotalAmount);
    	    				Ext.getCmp('Foss_StlairPayable_StlairPayableQueryInfoGrid_airPayableUnverifyAmount_Id').setValue(result.billPayableAgencyVo.billPayableAgencyDto.unverifyTotalAmount);			
    				    }
    				}
    			});
    			Ext.Msg.alert(agency.airPayable.i18n('foss.stl.agency.common.alert'),
    					agency.airPayable.i18n('foss.stl.agency.airPayAndRec.reverseAuditSuccess'));
    		},
    		exception:function(response){
    			var result = Ext.decode(response.responseText);	
    			Ext.Msg.alert(agency.airPayable.i18n('foss.stl.agency.common.alert'),result.message);
    		},
    		failure:function(response){
    			var result = Ext.decode(response.responseText);
    		}
    	});
	};
	var noFn=function(){
	 	return false;
	};
	agency.airPayable.billAirPayableConfirmAlert(agency.airPayable.i18n('foss.stl.agency.airPayAndRec.isReverseAudit'),yesFn,noFn);
}
//空运其他管理反审核
agency.airPayable.reverseAuditAirOtherBillPayable = function(){
	
	var selectionPayable = Ext.getCmp('Foss_StlairPayable_StlPayableQueryInfoGrid_Id').getSelectionModel().getSelection();
	
	if(selectionPayable.length==0){
		Ext.Msg.alert(agency.airPayable.i18n('foss.stl.agency.common.alert'),
				agency.airPayable.i18n('foss.stl.agency.airPayAndRec.reverseAuditText'));
		return false;
	}
	var jsonDataPayable = new Array();
	for(var i=0;i<selectionPayable.length;i++){
		jsonDataPayable.push(selectionPayable[i].data);
	}
	var entity = new Object();
	entity.billPayableAgencyDtos = jsonDataPayable;
	var searchParams = agency.airPayable.queryCondition(searchParams);
	entity.billPayableAgencyDto = searchParams;
	
    var yesFn=function(){
    	Ext.Ajax.request({
    		url:agency.realPath('reverseAuditAirOtherBillPayable.action'),
    		jsonData:{'billPayableAgencyVo':entity},
    		method:'post',
    		success:function(response){
    			Ext.getCmp('Foss_StlairPayable_StlPayableQueryInfoGrid_Id').store.load({
    				callback: function(records, operation, success) {
    				    var result =   Ext.decode(operation.response.responseText);  
    				    if(success){
    						if(result.isException){
    							Ext.Msg.alert(agency.airPayable.i18n('foss.stl.agency.common.alert'),result.message);
    							return false;
    						}
    	    			    Ext.getCmp('Foss_StlairPayable_StlairPayableQueryInfoGrid_airPayableTotalRows_Id').setValue(result.billPayableAgencyVo.billPayableAgencyDto.totalNum);
    	    				Ext.getCmp('Foss_StlairPayable_StlairPayableQueryInfoGrid_airPayableTotalAmount_Id').setValue(result.billPayableAgencyVo.billPayableAgencyDto.totalAmount);
    	    				Ext.getCmp('Foss_StlairPayable_StlairPayableQueryInfoGrid_airPayableVerifyAmount_Id').setValue(result.billPayableAgencyVo.billPayableAgencyDto.verifyTotalAmount);
    	    				Ext.getCmp('Foss_StlairPayable_StlairPayableQueryInfoGrid_airPayableUnverifyAmount_Id').setValue(result.billPayableAgencyVo.billPayableAgencyDto.unverifyTotalAmount);			
    				    }
    				}
    			});
    			Ext.Msg.alert(agency.airPayable.i18n('foss.stl.agency.common.alert'),
    					agency.airPayable.i18n('foss.stl.agency.airPayAndRec.reverseAuditSuccess'));
    		},
    		exception:function(response){
    			var result = Ext.decode(response.responseText);	
    			Ext.Msg.alert(agency.airPayable.i18n('foss.stl.agency.common.alert'),result.message);
    		},
    		failure:function(response){
    			var result = Ext.decode(response.responseText);
    		}
    	});
	};
	var noFn=function(){
	 	return false;
	};
	agency.airPayable.billAirPayableConfirmAlert(agency.airPayable.i18n('foss.stl.agency.airPayAndRec.isReverseAudit'),yesFn,noFn);
}


//应付单model
Ext.define('Foss.StlairPayable.BillPayableEntryModel',{
	extend:'Ext.data.Model',
	fields:[{
		name:'payableNo'
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
		name: 'paymentNo'
	},{
		name: 'statementBillNo'
	},{
		name:'payableOrgCode'
	},{
		name:'payableOrgName'
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

//应付单Store
Ext.define('Foss.StlairPayable.BillPayableEntryStore',{
	extend:'Ext.data.Store',
	model:'Foss.StlairPayable.BillPayableEntryModel',
	proxy:{
		type:'ajax',
		url:agency.realPath('queryAirPayablePage.action'),
		reader:{
			type:'json',
			root:'billPayableAgencyVo.billPayableAgencyDto.billPayableEntityList',
			totalProperty:'totalCount'
		}
	},
	listeners:{
		'beforeLoad':function(store, operation, eOpts){
			var form = Ext.getCmp('T_agency-airPayable_content').getQueryInfoTab().items.items[0].items.items[0].getForm();
    		agency.airPayable.startBusinessDate = form.findField('startDate').getValue();
    		agency.airPayable.endBusinessDate = form.findField('endDateAgency').getValue();
    		agency.airPayable.customerDetialCode = form.findField('customerDetial').getValue();
    		agency.airPayable.agencyDetialCode = form.findField('agencyDetial').getValue();
    		agency.airPayable.approveStatus = form.findField('approveStatus').getValue();
    		agency.airPayable.productCode = form.findField('productCode').getValue();
			var searchParams;
			//如果第一次进入界面，直接选择分页查询，则默认按照客户查询
			if(Ext.isEmpty(agency.airPayable.queryByTab)){
				searchParams = {
						'billPayableAgencyVo.billPayableAgencyDto.startBusinessDate':agency.airPayable.startBusinessDate,
						'billPayableAgencyVo.billPayableAgencyDto.endBusinessDate':agency.airPayable.endBusinessDate,
						'billPayableAgencyVo.billPayableAgencyDto.approveStatus':agency.airPayable.approveStatus,
						'billPayableAgencyVo.billPayableAgencyDto.customerCode':agency.airPayable.customerCode,
						'billPayableAgencyVo.billPayableAgencyDto.productCode':agency.airPayable.poductCode,
						'billPayableAgencyVo.billPayableAgencyDto.queryByTab':agency.airPayable.QUERY_BYDATE
				}
			}
			else if(agency.airPayable.queryByTab == agency.airPayable.QUERY_PAYABLE_NO){
				searchParams = {
						'billPayableAgencyVo.billPayableAgencyDto.payableNo':agency.airPayable.payableNo,
						'billPayableAgencyVo.billPayableAgencyDto.queryByTab':agency.airPayable.queryByTab
				}
			}
			else if(agency.airPayable.queryByTab == agency.airPayable.QUERY_SOURCE_BILL_NO){
				searchParams = {
						'billPayableAgencyVo.billPayableAgencyDto.sourceBillNo':agency.airPayable.sourceBillNo,
						'billPayableAgencyVo.billPayableAgencyDto.queryByTab':agency.airPayable.queryByTab
				}
			}
			Ext.apply(operation,{
				params :searchParams
			}); 
		}
	}
});

Ext.define('Foss.StlairPayable.StlairPayableQueryInfoTab', {
	extend:'Ext.tab.Panel',
	bodyCls: 'autoHeight',
	cls: 'innerTabPanel',
	activeTab: 0,
	columnWidth:1,
	height : 190,
	items : [ {
		title: agency.airPayable.i18n('foss.stl.agency.common.queryByDate'),
		tabConfig: {
			width: 120
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
		    	//id:'agency_airPayable_startDate',
		    	name:'startDate',
		    	fieldLabel:agency.airPayable.i18n('foss.stl.agency.airPayAndRec.startDateAgency'),
		    	editable:false,
		    	value: stl.getTargetDate(new Date(),-1),
		    	format:'Y-m-d',
		    	allowBlank:false
		    },{
		    	xtype:'datefield',
		   	 	name:'endDateAgency',
		    	fieldLabel:agency.airPayable.i18n('foss.stl.agency.airPayAndRec.endDateAgency'),
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
	            colspan:3,
	            defaultType: 'radiofield',
	            labelSeparator : "",
	            fieldLabel:'&nbsp;',
	            layout: 'column',
	            items: [{
	                    boxLabel  : agency.airPayable.i18n('foss.stl.agency.airPayAndRec.customerDetial'),
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
	                    boxLabel  : agency.airPayable.i18n('foss.stl.agency.airPayAndRec.agencyDetial'),
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
				fieldLabel:agency.airPayable.i18n('foss.stl.agency.airPayAndRec.customerDetial'),
				name:'customerDetial',
				allowBlank:true,
				listWidth:300,//设置下拉框宽度
				isPaging:true //分页
		    },{	
		    	xtype:'commonairagentselector',
				fieldLabel:agency.airPayable.i18n('foss.stl.agency.airPayAndRec.agencyDetial'), 
				name:'agencyDetial',
				allowBlank:true,
				hidden:true,
				listWidth:300,//设置下拉框宽度
				isPaging:true //分页
		    },{
		    	xtype:'combobox',
		    	name:'approveStatus',
		    	fieldLabel:agency.airPayable.i18n('foss.stl.agency.airPayAndRec.approveStatus'),
		    	editable:false,
				store:FossDataDictionary.getDataDictionaryStore(settlementDict.BILL_PAYABLE__APPROVE_STATUS,null,{
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
				fieldLabel : agency.airPayable.i18n('foss.stl.agency.common.transportType'),
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
					text:agency.airPayable.i18n('foss.stl.agency.common.reset'),
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
					text:agency.airPayable.i18n('foss.stl.agency.common.query'),
					cls:'yellow_button',
					columnWidth:.15,
					handler:function(){
						var form = this.up('form').getForm();
						var me = this;
    					agency.airPayable.queryBillPayableEntityParams(form,me)
    				}
				}]
		    }]
        }]
	}, {
		title: agency.airPayable.i18n('foss.stl.agency.airPayAndRec.queryByPayNo'),
		tabConfig: {
			width: 140
		},
        items:[{
        	xtype:'form',
        	defaults:{
	        	margin:'10 0 0 0',
	        	labelWidth:85
       		 },
        	layout:{
       			type :'table',
       			columns :3
       		},
        	 items:[{
 		    	xtype: 'textarea',
 	 			autoScroll:true,
 	 			emptyText:agency.airPayable.i18n('foss.stl.agency.airPayAndRec.emptyText'),
 	            fieldLabel: agency.airPayable.i18n('foss.stl.agency.payable.payableNo'),
 	            name: 'payableNos',
 	            height : 85,
 	            width : 480,
 	            allowBlank:false,
 	            regex:/^((YF)[0-9]{7,10}[;,]?)+$/i,
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
					text:agency.airPayable.i18n('foss.stl.agency.common.reset'),
					columnWidth:.15,
					handler:function(){
						this.up('form').getForm().reset();
					}
				},{
					xtype: 'container',
					border : false,
					html: '&nbsp;',
					columnWidth:.7
				},{
					text:agency.airPayable.i18n('foss.stl.agency.common.query'),
					cls:'yellow_button',
					columnWidth:.15,
					handler:function(){
						var form = this.up('form').getForm();
						var me = this;
    					agency.airPayable.queryByPayableNOs(form,me)
    				}
				}]
 		    }]
        }]
	}, {
		title: agency.airPayable.i18n('foss.stl.agency.common.queryBySourceNo'),
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
 	 			emptyText:agency.airPayable.i18n('foss.stl.agency.airPayAndRec.emptyText'),
 	            fieldLabel: agency.airPayable.i18n('foss.stl.agency.airPayAndRec.billNo'),
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
						html:'<span style="color:red;">'+agency.airPayable.i18n('foss.stl.agency.airPayAndRec.sourceBillNosNots')+'</span>'
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
					text:agency.airPayable.i18n('foss.stl.agency.common.reset'),
					columnWidth:.15,
					handler:function(){
						this.up('form').getForm().reset();
						Ext.getCmp('Foss_StlairPayable_StlPayableQueryInfoGrid_Id').hide();
					}
				},{
					xtype: 'container',
					border : false,
					html: '&nbsp;',
					columnWidth:.70
				},{
					text:agency.airPayable.i18n('foss.stl.agency.common.query'),
					cls:'yellow_button',
					columnWidth:.15,
					handler:function(){
    					var form = this.up('form').getForm();
    					var me = this;
    					agency.airPayable.queryBySourceBillNOs(form,me)
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

//应付单列表
Ext.define('Foss.StlairPayable.StlPayableQueryInfoGrid',{
	extend:'Ext.grid.Panel',
    title: agency.airPayable.i18n('foss.stl.agency.payable.payable'),
	frame:true,
	height:400,
	//plugins:SoperateColumnEditing,
	selModel:Ext.create('Ext.selection.CheckboxModel'),
    store: Ext.create('Foss.StlairPayable.BillPayableEntryStore'),
//    listeners:{
//        cellclick:function( me, td, cellIndex, record, tr, rowIndex, e, eOpts ){           
//			if(cellIndex==16&&!Ext.isEmpty(record.raw.notes)&&
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
		text: agency.airPayable.i18n('foss.stl.agency.common.actionColumn'),
		align: 'center',
		items:[{
			iconCls : 'foss_icons_stl_auditing',
			tooltip:agency.airPayable.i18n('foss.stl.agency.common.audit'),
			getClass:function(v,m,r,rowIndex){
    			if(r.get('approveStatus') == "AA"
    					|| !agency.airPayable.isPermission('/stl-web/airPayable/auditAirOtherBillPayable.action')){
    				return 'statementBill_hide';
    			}else {
    				return 'foss_icons_stl_auditing';
    			}
    		},
			handler:function(grid, rowIndex, colIndex){
				agency.airPayable.auditAirOtherOneBillPayable(grid, rowIndex, colIndex)	
			}
		},{
			iconCls : 'foss_icons_stl_fauditing',
			tooltip:agency.airPayable.i18n('foss.stl.agency.common.unAudit'),
			getClass:function(v,m,r,rowIndex){
    			if(r.get('approveStatus') == "NA"
    					|| !agency.airPayable.isPermission('/stl-web/airPayable/reverseAuditAirOtherBillPayable.action')){
    				return 'statementBill_hide';
    			}else {
    				return 'foss_icons_stl_fauditing';
    			}
    		},
			handler:function(grid, rowIndex, colIndex){
				agency.airPayable.reverseAuditAirOtherOneBillPayable(grid, rowIndex, colIndex)	
			}
		},{
			iconCls : 'deppon_icons_cancel',
			tooltip:agency.airPayable.i18n('foss.stl.agency.common.disable'),
			getClass:function(v,m,r,rowIndex){
    			if(!agency.airPayable.isPermission('/stl-web/airPayable/writeBackAirOtherBillPayable.action')){
    				return 'statementBill_hide';
    			}else {
    				return 'deppon_icons_cancel';
    			}
    		},
			handler:function(grid, rowIndex, colIndex){
				agency.airPayable.writeBackAirOtherOnePayable(grid, rowIndex, colIndex)	
			}
		}]
	},{
		header: agency.airPayable.i18n('foss.stl.agency.payable.payableNo'), 
		dataIndex: 'payableNo'
	},{
		header: agency.airPayable.i18n('foss.stl.agency.airPayAndRec.billNo'), 
		dataIndex: 'sourceBillNo'
	},{
		header: agency.airPayable.i18n('foss.stl.agency.airPayAndRec.customerCode'), 
		dataIndex: 'customerCode'		
	},{
		header: agency.airPayable.i18n('foss.stl.agency.airPayAndRec.customerName'), 
		dataIndex: 'customerName'
	},{
		header: agency.airPayable.i18n('foss.stl.agency.airPayAndRec.approveStatus'), 
		dataIndex: 'approveStatus',
		renderer:function(value){
    		var displayField = FossDataDictionary.rendererSubmitToDisplay(value,settlementDict.BILL_PAYABLE__APPROVE_STATUS);
    		return displayField;
		}
	},{
		header: agency.airPayable.i18n('foss.stl.agency.common.transportType'), 
		dataIndex:'productCode',
		renderer : function(value) {
							return Foss.pkp.ProductData.rendererSubmitToDisplay(value);
						}
	},{
		header: agency.airPayable.i18n('foss.stl.agency.airPayAndRec.origOrgCode'), 
		hidden:true,
		dataIndex:'origOrgCode'
	},{
		header: agency.airPayable.i18n('foss.stl.agency.airPayAndRec.origOrgName'), 
		dataIndex:'origOrgName'
	},{
		header: agency.airPayable.i18n('foss.stl.agency.airPayAndRec.paymentNo'), 
		dataIndex:'paymentNo',
		hidden:true
	},{
		header: agency.airPayable.i18n('foss.stl.agency.airPayAndRec.statementBillNo'), 
		dataIndex: 'statementBillNo',
		hidden:true
	},{
		header: agency.airPayable.i18n('foss.stl.agency.airPayAndRec.amount'), 
		dataIndex: 'amount'				
	},{
		header: agency.airPayable.i18n('foss.stl.agency.airPayAndRec.verifyAmount'),
		dataIndex: 'verifyAmount' 
	},{
		header: agency.airPayable.i18n('foss.stl.agency.airPayAndRec.unverifyAmount'), 
		dataIndex: 'unverifyAmount'				
	},{
		header: agency.airPayable.i18n('foss.stl.agency.airPayAndRec.businessDate'),
		dataIndex: 'businessDate',
		renderer:function(value){
			if(value!=null){
				return Ext.Date.format(new Date(value), 'Y-m-d');
			}else{
				return null;
			}
		} 
	},{
		header: agency.airPayable.i18n('foss.stl.agency.airPayAndRec.accountDate'),
		dataIndex: 'accountDate',
		renderer:function(value){
			if(value!=null){
				return Ext.Date.format(new Date(value), 'Y-m-d');
			}else{
				return null;
			}
		} 
	},{
		header: agency.airPayable.i18n('foss.stl.agency.airPayAndRec.notes'), 
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
				text:agency.airPayable.i18n('foss.stl.agency.common.export'),
				disabled:!agency.airPayable.isPermission('/stl-web/agency/airPayableListExport.action'),
				hidden:!agency.airPayable.isPermission('/stl-web/agency/airPayableListExport.action'),
				handler:agency.airPayable.airPayableListExport 
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
				id:'Foss_StlairPayable_StlairPayableQueryInfoGrid_airPayableTotalRows_Id',
				columnWidth:.05,
				fieldLabel:agency.airPayable.i18n('foss.stl.agency.common.sum'),
				labelWidth:50
			},{
				xtype:'container',
				border:false,
				html:'&nbsp;',
				columnWidth:.005
			},{
				xtype:'displayfield',
				allowBlank:true,
				id:'Foss_StlairPayable_StlairPayableQueryInfoGrid_airPayableTotalAmount_Id',
				columnWidth:.1,
				fieldLabel:agency.airPayable.i18n('foss.stl.agency.common.totalAmount'),
				labelWidth:50
			},{
				xtype:'container',
				border:false,
				html:'&nbsp;',
				columnWidth:.01
			},{
				xtype:'displayfield',
				allowBlank:true,
				id:'Foss_StlairPayable_StlairPayableQueryInfoGrid_airPayableVerifyAmount_Id',
				columnWidth:.13,
				fieldLabel:agency.airPayable.i18n('foss.stl.agency.airPayAndRec.verifyAmount'),
				labelWidth:80
			},{
				xtype:'container',
				border:false,
				html:'&nbsp;',
				columnWidth:.05
			},{
				xtype:'displayfield',
				allowBlank:true,
				id:'Foss_StlairPayable_StlairPayableQueryInfoGrid_airPayableUnverifyAmount_Id',
				columnWidth:.1,
				fieldLabel:agency.airPayable.i18n('foss.stl.agency.airPayAndRec.unverifyAmount'),
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
				text:agency.airPayable.i18n('foss.stl.agency.common.disable'),
				disabled:!agency.airPayable.isPermission('/stl-web/agency/writeBackAirOtherBillPayable.action'),
				hidden:!agency.airPayable.isPermission('/stl-web/agency/writeBackAirOtherBillPayable.action'),
				columnWidth:.1,
				handler:function(){
					var me = this;
					agency.airPayable.writeBackAirOtherBillPayable(me)
				}
			},{
				xtype:'container',
				border:false,
				html:'&nbsp;',
				columnWidth:.005
			},{
				xtype:'button',
				text:agency.airPayable.i18n('foss.stl.agency.common.audit'),
				disabled:!agency.airPayable.isPermission('/stl-web/agency/auditAirOtherBillPayable.action'),
				hidden:!agency.airPayable.isPermission('/stl-web/agency/auditAirOtherBillPayable.action'),
				columnWidth:.1,
				handler:agency.airPayable.auditAirOtherBillPayable
			},{
				xtype:'container',
				border:false,
				html:'&nbsp;',
				columnWidth:.005
			},{
				xtype:'button',
				text:agency.airPayable.i18n('foss.stl.agency.common.unAudit'),
				disabled:!agency.airPayable.isPermission('/stl-web/agency/reverseAuditAirOtherBillPayable.action'),
				hidden:!agency.airPayable.isPermission('/stl-web/agency/reverseAuditAirOtherBillPayable.action'),
				columnWidth:.1,
				handler:agency.airPayable.reverseAuditAirOtherBillPayable
			}]
		}]
		me.callParent();
	}
});


//初始化界面
Ext.onReady(function() {
	Ext.QuickTips.init();

	var StlairPayableQueryInfoTab = Ext.create('Foss.StlairPayable.StlairPayableQueryInfoTab');
	
	var StlPayableQueryInfoGrid = Ext.create('Foss.StlairPayable.StlPayableQueryInfoGrid',{
		id:'Foss_StlairPayable_StlPayableQueryInfoGrid_Id'
	});
	
	Ext.create('Ext.panel.Panel',{
		id: 'T_agency-airPayable_content',
		cls: "panelContentNToolbar",
		bodyCls: 'panelContentNToolbar-body',
		getQueryInfoTab:function(){
    		return StlairPayableQueryInfoTab;
    	},
		layout: 'auto',
		items: [StlairPayableQueryInfoTab,StlPayableQueryInfoGrid],
		renderTo: 'T_agency-airPayable-body'
	});
});