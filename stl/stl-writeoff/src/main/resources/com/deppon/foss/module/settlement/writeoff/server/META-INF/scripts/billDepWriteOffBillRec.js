/**
 *预收应收单查询日期限制 
 */
writeoff.billDepWriteoffBillRec.STLBILLDEPWRITEOFFBILLREC_MAX = 90;  //时间间隔最大不超过90天
writeoff.billDepWriteoffBillRec.STLBILLDEPWRITEOFFBILLREC_ONEMONTH = 31;  //时间相差一月
writeoff.billDepWriteoffBillRec.MAXSHOWNUM = 1000;  //默认最大显示条数

writeoff.billDepWriteoffBillRec.STLBILLDEPWRITEOFFBILLREC_QUERY_BY_DATE='TD';//按时间查询
writeoff.billDepWriteoffBillRec.STLBILLDEPWRITEOFFBILLREC_QUERY_BY_NO='DERE';//按单号查询

/**
 * Form重置方法
 */
writeoff.billDepWriteoffBillRec.queryBillDepAndBillRecReset=function(){
	this.up('form').getForm().reset();
}

//设置参数
writeoff.billDepWriteoffBillRec.billDepAndBillRecQuerySetParams=function(form,queryType){
	//定义查询参数
	var params={};
	
	//按日期查询
	if(writeoff.billDepWriteoffBillRec.STLBILLDEPWRITEOFFBILLREC_QUERY_BY_DATE==queryType){
		
		//获取radioFiled选中值
		var selectDateDepFlagTemp = form.findField('selectDateDepFlag').getValue();
		var selectDateRecFlagTemp = form.findField('selectDateRecFlag').getValue();

		var startBusinessDateDepTemp;
		var endBusinessDateDepTemp;
		var startAccountDateDepTemp;
		var endAccountDateDepTemp;

		var startBusinessDateRecTemp;
		var endBusinessDateRecTemp;
		var startAccountDateRecTemp;
		var endAccountDateRecTemp;

		var customerCode;

		if(selectDateDepFlagTemp == '1'){
			startBusinessDateDepTemp = form.findField('startDepDate').getValue();
			endBusinessDateDepTemp = form.findField('endDepDate').getValue();
		}else{
			startAccountDateDepTemp =form.findField('startDepDate').getValue();
			endAccountDateDepTemp = form.findField('endDepDate').getValue();
		}
		if(selectDateRecFlagTemp == '1'){
			startBusinessDateRecTemp = form.findField('startRecDate').getValue();
			endBusinessDateRecTemp = form.findField('endRecDate').getValue();
		}else{
			startAccountDateRecTemp =form.findField('startRecDate').getValue();
			endAccountDateRecTemp = form.findField('endRecDate').getValue();
		}
		var maxShowNum = form.findField('showRecordCount').getValue();
		var customerDetial = form.findField('customerDetial').getValue();
		//var airDetial = form.findField('airDetial').getValue();
		var airAgencyDetial = form.findField('airAgencyDetial').getValue();
		var vehAgencyDetial = form.findField('vehAgencyDetial').getValue();
		var landStowage = form.findField('landStowage').getValue();
		var partnerOrg = form.findField('partnerOrg').getValue();
		//客户信息、代理信息不能同时为null
		if((customerDetial==null||customerDetial=='')
			&&(airAgencyDetial==null||airAgencyDetial=='')
		    &&(vehAgencyDetial==null||vehAgencyDetial=='')&& Ext.isEmpty(landStowage) &&
		    Ext.isEmpty(partnerOrg)){
			
			Ext.Msg.alert(writeoff.billDepWriteoffBillRec.i18n('foss.stl.writeoff.common.alert'), writeoff.billDepWriteoffBillRec.i18n('foss.stl.writeoff.common.validateFailAlertByNoCustomer'));
			return false;
		}
		
		if(customerDetial!=null&&customerDetial!=''){
			customerCode = customerDetial;
//		}else if(airDetial!=null&&airDetial!=''){
//			customerCode = airDetial;
		}else if(airAgencyDetial!=null&&airAgencyDetial!=''){
			customerCode = airAgencyDetial;
		}else if(vehAgencyDetial!=null&&vehAgencyDetial!=''){
			customerCode = vehAgencyDetial;
		}else if(!Ext.isEmpty(landStowage)){
			customerCode = landStowage;
		}else if(!Ext.isEmpty(partnerOrg)){
			customerCode = partnerOrg
		}
		
		
		//设置账单号参数到DTO中
		Ext.apply(params,{ 
			'billDepWriteoffBillRecVo.billDepositReceivedDto.startBusinessDate':startBusinessDateDepTemp,
			'billDepWriteoffBillRecVo.billDepositReceivedDto.endBusinessDate':endBusinessDateDepTemp,
			'billDepWriteoffBillRecVo.billDepositReceivedDto.startAccountDate':startAccountDateDepTemp,
			'billDepWriteoffBillRecVo.billDepositReceivedDto.endAccountDate':endAccountDateDepTemp,
			'billDepWriteoffBillRecVo.billDepositReceivedDto.maxShowNum':maxShowNum,
			'billDepWriteoffBillRecVo.billDepositReceivedDto.customerCode':customerCode,
			'billDepWriteoffBillRecVo.billReceivableDto.startBusinessDate':startBusinessDateRecTemp,
			'billDepWriteoffBillRecVo.billReceivableDto.endBusinessDate':endBusinessDateRecTemp,
			'billDepWriteoffBillRecVo.billReceivableDto.startAccountDate':startAccountDateRecTemp,
			'billDepWriteoffBillRecVo.billReceivableDto.endAccountDate':endAccountDateRecTemp,
			'billDepWriteoffBillRecVo.billReceivableDto.maxShowNum':maxShowNum,
			'billDepWriteoffBillRecVo.billReceivableDto.customerCode':customerCode			
		});
	
	//按单号查询
	}else if(writeoff.billDepWriteoffBillRec.STLBILLDEPWRITEOFFBILLREC_QUERY_BY_NO==queryType){
		
		var receivableNumbers = form.findField('receivableNos').getValue();
		if(receivableNumbers==null || receivableNumbers==''){
			Ext.Msg.alert(writeoff.billDepWriteoffBillRec.i18n('foss.stl.writeoff.common.alert'),writeoff.billDepWriteoffBillRec.i18n('foss.stl.writeoff.billDepWriteOffBillRec.receivableNosInputIsNotNull'));
			return false;
		}
		
		var precollectedNummbers = form.findField('precollectedNos').getValue();
		if(precollectedNummbers==null || precollectedNummbers==''){
			Ext.Msg.alert(writeoff.billDepWriteoffBillRec.i18n('foss.stl.writeoff.common.alert'),writeoff.billDepWriteoffBillRec.i18n('foss.stl.writeoff.billDepWriteOffBillRec.precollectedNosInputIsNotNull'));
			return false;
		}
		
		var receivableNosStr = new Array();
		receivableNosStr = stl.splitToArray(form.findField('receivableNos').getValue());
		if(receivableNosStr.length>10){
			Ext.Msg.alert(writeoff.billDepWriteoffBillRec.i18n('foss.stl.writeoff.common.alert'),writeoff.billDepWriteoffBillRec.i18n('foss.stl.writeoff.billDepWriteOffBillRec.receivableNosInputToMore'));
			return false;
		}
		
		var precollectedNosStr = new Array();
		precollectedNosStr = stl.splitToArray(form.findField('precollectedNos').getValue());
		if(precollectedNosStr.length>10){
			Ext.Msg.alert(writeoff.billDepWriteoffBillRec.i18n('foss.stl.writeoff.common.alert'),writeoff.billDepWriteoffBillRec.i18n('foss.stl.writeoff.billDepWriteOffBillRec.precollectedNosInputToMore'));
			return false;
		}
		
		//设置账单号参数到DTO中
		Ext.apply(params,{ 
			'billDepWriteoffBillRecVo.billDepositReceivedDto.precollectedNumbers':precollectedNummbers,
			'billDepWriteoffBillRecVo.billReceivableDto.receivableNumbers':receivableNumbers
							
		});
	}
	
	//设置查询类型
	Ext.apply(params,{
		'billDepWriteoffBillRecVo.billDepositReceivedDto.queryType':queryType,
		'billDepWriteoffBillRecVo.billReceivableDto.queryType':queryType
	});
	
	return params;
}

/**
 * Form查询方法
 */
writeoff.billDepWriteoffBillRec.queryBillDepAndBillRec=function(f,me,queryType){
	var form=f.getForm();
	
	if(writeoff.billDepWriteoffBillRec.STLBILLDEPWRITEOFFBILLREC_QUERY_BY_DATE==queryType){
		
		// 检查预收单的日期范围
		var startBusinessDate = form.findField('startDepDate').getValue();
		var endBusinessDate = form.findField('endDepDate').getValue();
		
		if(startBusinessDate==null || startBusinessDate==''){
			Ext.Msg.alert(writeoff.billDepWriteoffBillRec.i18n('foss.stl.writeoff.common.alert'),writeoff.billDepWriteoffBillRec.i18n('foss.stl.writeoff.billDepWriteOffBillRec.startDateIsNotNull'));
			return false;
		}

		if(endBusinessDate==null || endBusinessDate==''){
			Ext.Msg.alert(writeoff.billDepWriteoffBillRec.i18n('foss.stl.writeoff.common.alert'),writeoff.billDepWriteoffBillRec.i18n('foss.stl.writeoff.billDepWriteOffBillRec.endDateIsNotNull'));
			return false;
		}
		var compareTwoDate = stl.compareTwoDate(startBusinessDate,endBusinessDate);
		if(compareTwoDate>writeoff.billDepWriteoffBillRec.STLBILLDEPWRITEOFFBILLREC_MAX){
			Ext.Msg.alert(writeoff.billDepWriteoffBillRec.i18n('foss.stl.writeoff.common.alert'),writeoff.billDepWriteoffBillRec.i18n('foss.stl.writeoff.billDepWriteOffBillRec.startToEndDateIsNotMaxDay'));
			return false;
		}else if(compareTwoDate<1){
			Ext.Msg.alert(writeoff.billDepWriteoffBillRec.i18n('foss.stl.writeoff.common.alert'),writeoff.billDepWriteoffBillRec.i18n('foss.stl.writeoff.billDepWriteOffBillRec.endDateIsNotBeforeStartDate'));
			return false;
		}
	}

	// 检查应收单的日期范围
	if(writeoff.billDepWriteoffBillRec.STLBILLDEPWRITEOFFBILLREC_QUERY_BY_DATE==queryType){
		
		var startBusinessDate = form.findField('startRecDate').getValue();
		var endBusinessDate = form.findField('endRecDate').getValue();
		
		if(startBusinessDate==null || startBusinessDate==''){
			
			Ext.Msg.alert(writeoff.billDepWriteoffBillRec.i18n('foss.stl.writeoff.common.alert'),writeoff.billDepWriteoffBillRec.i18n('foss.stl.writeoff.billDepWriteOffBillRec.startDateIsNotNull'));
			return false;
		}

		if(endBusinessDate==null || endBusinessDate==''){
			Ext.Msg.alert(writeoff.billDepWriteoffBillRec.i18n('foss.stl.writeoff.common.alert'),writeoff.billDepWriteoffBillRec.i18n('foss.stl.writeoff.billDepWriteOffBillRec.endDateIsNotNull'));
			return false;
		}
		var compareTwoDate = stl.compareTwoDate(startBusinessDate,endBusinessDate);
		if(compareTwoDate>writeoff.billDepWriteoffBillRec.STLBILLDEPWRITEOFFBILLREC_MAX){
			Ext.Msg.alert(writeoff.billDepWriteoffBillRec.i18n('foss.stl.writeoff.common.alert'),writeoff.billDepWriteoffBillRec.i18n('foss.stl.writeoff.billDepWriteOffBillRec.startToEndDateIsNotMaxDay'));
			return false;
		}else if(compareTwoDate<1){
			Ext.Msg.alert(writeoff.billDepWriteoffBillRec.i18n('foss.stl.writeoff.common.alert'),writeoff.billDepWriteoffBillRec.i18n('foss.stl.writeoff.billDepWriteOffBillRec.endDateIsNotBeforeStartDate'));
			return false;
		}
	}
	
	var gridDep = Ext.getCmp('T_writeoff-billDepWriteOffBillRec_content').getQueryBillDepGrid();
	var gridRec = Ext.getCmp('T_writeoff-billDepWriteOffBillRec_content').getQueryBillRecGrid();
	if(form.isValid()){
		var params=writeoff.billDepWriteoffBillRec.billDepAndBillRecQuerySetParams(form,queryType);
		
		if(null==params || params==false){
			return;
		}
		
		//设置查询参数
		gridDep.store.setSubmitParams(params);
		gridRec.store.setSubmitParams(params);
		//设置该按钮灰掉
		me.disable(false);
		//30秒后自动解除灰掉效果
		setTimeout(function() {
			me.enable(true);
		}, 30000);
		//设置统计值
		gridDep.store.load({
	      callback: function(records, operation, success) {
	    	  
	    	//抛出异常  
			var rawData = gridDep.store.proxy.reader.rawData;
		    if(!success && ! rawData.isException){
				Ext.Msg.alert(writeoff.billDepWriteoffBillRec.i18n('foss.stl.writeoff.common.alert'),rawData.message);
				me.enable(true);
				return false;
			}  
	    	  
		  //正常返回
	    	if(success){
	    		var result =   Ext.decode(operation.response.responseText);
				
				toolBar = gridDep.getDockedItems('toolbar[dock="bottom"]')[0];
				toolBar.getComponent('totalRowsDep').setValue(result.billDepWriteoffBillRecVo.billDepWriteoffBillRecDto.totalNumDep);
				toolBar.getComponent('totalAmountDep').setValue(stl.amountFormat(result.billDepWriteoffBillRecVo.billDepWriteoffBillRecDto.totalAmountDep));
				toolBar.getComponent('totalVerifyAmountDep').setValue(stl.amountFormat(result.billDepWriteoffBillRecVo.billDepWriteoffBillRecDto.verifyTotalAmountDep));
				toolBar.getComponent('totalUnverifyAmountDep').setValue(stl.amountFormat(result.billDepWriteoffBillRecVo.billDepWriteoffBillRecDto.unverifyTotalAmountDep));
		       
				if(result.billDepWriteoffBillRecVo.billDepWriteoffBillRecDto.billDepositreceivedEntityList.length>0){
					gridDep.show();
				}else{
					Ext.Msg.alert(writeoff.billDepWriteoffBillRec.i18n('foss.stl.writeoff.common.alert'),writeoff.billDepWriteoffBillRec.i18n('foss.stl.writeoff.billDepWriteOffBillRec.noBillDepositReceiveData'));
					me.enable(true);
					return false;
				}
				me.enable(true);
	    	}
	      }
	    }); 
		gridRec.store.load({
		      callback: function(records, operation, success) {
		    	
		      //抛出异常  
	    		var rawData = gridRec.store.proxy.reader.rawData;
			    if(!success && ! rawData.isException){
					Ext.Msg.alert(writeoff.billDepWriteoffBillRec.i18n('foss.stl.writeoff.common.alert'),rawData.message);
					me.enable(true);
					return false;
				}  	  

			    //正常返回
		    	if(success){
		    		var result =   Ext.decode(operation.response.responseText);
					
					toolBar = gridRec.getDockedItems('toolbar[dock="bottom"]')[0];
					toolBar.getComponent('totalRowsRec').setValue(result.billDepWriteoffBillRecVo.billDepWriteoffBillRecDto.totalNumRec);
					toolBar.getComponent('totalAmountRec').setValue(stl.amountFormat(result.billDepWriteoffBillRecVo.billDepWriteoffBillRecDto.totalAmountRec));
					toolBar.getComponent('totalVerifyAmountRec').setValue(stl.amountFormat(result.billDepWriteoffBillRecVo.billDepWriteoffBillRecDto.verifyTotalAmountRec));
					toolBar.getComponent('totalUnverifyAmountRec').setValue(stl.amountFormat(result.billDepWriteoffBillRecVo.billDepWriteoffBillRecDto.unverifyTotalAmountRec));
			      
					if(result.billDepWriteoffBillRecVo.billDepWriteoffBillRecDto.billReceivableEntityList.length>0){
						gridRec.show();
					}else{
						Ext.Msg.alert(writeoff.billDepWriteoffBillRec.i18n('foss.stl.writeoff.common.alert'),writeoff.billDepWriteoffBillRec.i18n('foss.stl.writeoff.billDepWriteOffBillRec.noBillReceivableData'));
						me.enable(true);
						return false;
					}
					me.enable(true);
		    	}	  
		      }
		    }); 
		
	}else {
		Ext.Msg.alert(writeoff.billDepWriteoffBillRec.i18n('foss.stl.writeoff.common.alert'),writeoff.billDepWriteoffBillRec.i18n('foss.stl.writeoff.common.validateFailAlert'));
	}
}


/**
 * 提示信息
 * @param {} message 
 * @param {} yesFn
 * @param {} noFn
 */
writeoff.billDepWriteoffBillRec.billDepWriteoffBillRecConfirmAlert = function(message,yesFn,noFn){
	Ext.Msg.confirm(writeoff.billDepWriteoffBillRec.i18n('foss.stl.writeoff.common.alert'),message,function(o){
		if(o=='yes'){
			yesFn();
		}else{
			noFn();
		}
	});
};

/**
 * 会计核销
 */
writeoff.billDepWriteoffBillRec.writeoffByAccounting = function(){
	var me = this;
	var gridDep = Ext.getCmp('T_writeoff-billDepWriteOffBillRec_content').getQueryBillDepGrid();
	var gridRec = Ext.getCmp('T_writeoff-billDepWriteOffBillRec_content').getQueryBillRecGrid();
	
	var selectionRec = gridRec.getSelectionModel().getSelection();
	var selectionDep = gridDep.getSelectionModel().getSelection();
	
	if(selectionRec.length==0){
		Ext.Msg.alert(writeoff.billDepWriteoffBillRec.i18n('foss.stl.writeoff.common.alert'),writeoff.billDepWriteoffBillRec.i18n('foss.stl.writeoff.billDepWriteOffBillRec.noBillReceivableDataByWriteoff'));
		return false;
	}
	if(selectionDep.length==0){
		Ext.Msg.alert(writeoff.billDepWriteoffBillRec.i18n('foss.stl.writeoff.common.alert'),writeoff.billDepWriteoffBillRec.i18n('foss.stl.writeoff.billDepWriteOffBillRec.noBillDepositReceiveDataByWriteoff'));
		return false;
	}
	
	
	Ext.Msg.confirm(writeoff.billDepWriteoffBillRec.i18n('foss.stl.writeoff.common.alert'),writeoff.billDepWriteoffBillRec.i18n('foss.stl.writeoff.billDepWriteOffBillRec.isConfirmWriteoff'),function(o){
		if(o=='yes'){
			var entity = new Object();

			var jsonDataRec = new Array();
			for(var i=0;i<selectionRec.length;i++){
				jsonDataRec.push(selectionRec[i].data);
			}
			entity.billReceivableEntityList = jsonDataRec;
			
			var jsonDataDep = new Array();
			for(var i=0;i<selectionDep.length;i++){
				jsonDataDep.push(selectionDep[i].data);
			}
			entity.billDepositreceivedEntityList = jsonDataDep;
			
			var paramstDep=gridDep.store.submitParams;
			var paramstRec=gridRec.store.submitParams;	
						
//			//设置条件
			var billDepositReceivedDto = new Object();
			billDepositReceivedDto.startBusinessDate = paramstDep["billDepWriteoffBillRecVo.billDepositReceivedDto.startBusinessDate"];
			billDepositReceivedDto.endBusinessDate = paramstDep["billDepWriteoffBillRecVo.billDepositReceivedDto.endBusinessDate"];
			billDepositReceivedDto.startAccountDate = paramstDep["billDepWriteoffBillRecVo.billDepositReceivedDto.startAccountDate"];
			billDepositReceivedDto.endAccountDate = paramstDep["billDepWriteoffBillRecVo.billDepositReceivedDto.endAccountDate"];
			billDepositReceivedDto.maxShowNum = paramstDep["billDepWriteoffBillRecVo.billDepositReceivedDto.maxShowNum"];
			billDepositReceivedDto.customerCode = paramstDep["billDepWriteoffBillRecVo.billDepositReceivedDto.customerCode"];
			billDepositReceivedDto.precollectedNumbers = paramstDep["billDepWriteoffBillRecVo.billDepositReceivedDto.precollectedNumbers"];
			billDepositReceivedDto.queryType = paramstDep["billDepWriteoffBillRecVo.billDepositReceivedDto.queryType"];
			entity.billDepositReceivedDto = billDepositReceivedDto;
			
			var billReceivableDto = new Object();
			billReceivableDto.startBusinessDate = paramstRec["billDepWriteoffBillRecVo.billReceivableDto.startBusinessDate"];
			billReceivableDto.endBusinessDate = paramstRec["billDepWriteoffBillRecVo.billReceivableDto.endBusinessDate"]; 
			billReceivableDto.startAccountDate = paramstRec["billDepWriteoffBillRecVo.billReceivableDto.startAccountDate"];
			billReceivableDto.endAccountDate = paramstRec["billDepWriteoffBillRecVo.billReceivableDto.endAccountDate"];
			billReceivableDto.maxShowNum = paramstRec["billDepWriteoffBillRecVo.billReceivableDto.maxShowNum"];
			billReceivableDto.customerCode = paramstRec["billDepWriteoffBillRecVo.billReceivableDto.customerCode"];
			billReceivableDto.receivableNumbers = paramstRec["billDepWriteoffBillRecVo.billReceivableDto.receivableNumbers"];
			billReceivableDto.queryType = paramstRec["billDepWriteoffBillRecVo.billReceivableDto.queryType"];
			entity.billReceivableDto = billReceivableDto;
			
			me.disable(false);
			//10秒后自动解除灰掉效果
			setTimeout(function() {
			me.enable(true);
			}, 10000);
			
			Ext.Ajax.request({
				//url:'../writeoff/writeoffBillDepAndBillRecByAccounting.action',
				url:writeoff.realPath('writeoffBillDepAndBillRecByAccounting.action'),
				jsonData:{'billDepWriteoffBillRecVo':entity},
				method:'post',
				success:function(response){
					var result = Ext.decode(response.responseText);
					var billReceivableEntryStore = gridRec.store;
					var billDepositreceivedEntityStore = gridDep.store;
					
					//加载后台查询到的数据到grid的store中
					billReceivableEntryStore.loadData(result.billDepWriteoffBillRecVo.billDepWriteoffBillRecDto.billReceivableEntityList);
					billDepositreceivedEntityStore.loadData(result.billDepWriteoffBillRecVo.billDepWriteoffBillRecDto.billDepositreceivedEntityList);
					
					toolBarDep = gridDep.getDockedItems('toolbar[dock="bottom"]')[0];
					toolBarDep.getComponent('totalRowsDep').setValue(result.billDepWriteoffBillRecVo.billDepWriteoffBillRecDto.totalNumDep);
					toolBarDep.getComponent('totalAmountDep').setValue(stl.amountFormat(result.billDepWriteoffBillRecVo.billDepWriteoffBillRecDto.totalAmountDep));
					toolBarDep.getComponent('totalVerifyAmountDep').setValue(stl.amountFormat(result.billDepWriteoffBillRecVo.billDepWriteoffBillRecDto.verifyTotalAmountDep));
					toolBarDep.getComponent('totalUnverifyAmountDep').setValue(stl.amountFormat(result.billDepWriteoffBillRecVo.billDepWriteoffBillRecDto.unverifyTotalAmountDep));
					
					toolBarRec = gridRec.getDockedItems('toolbar[dock="bottom"]')[0];
					toolBarRec.getComponent('totalRowsRec').setValue(result.billDepWriteoffBillRecVo.billDepWriteoffBillRecDto.totalNumRec);
					toolBarRec.getComponent('totalAmountRec').setValue(stl.amountFormat(result.billDepWriteoffBillRecVo.billDepWriteoffBillRecDto.totalAmountRec));
					toolBarRec.getComponent('totalVerifyAmountRec').setValue(stl.amountFormat(result.billDepWriteoffBillRecVo.billDepWriteoffBillRecDto.verifyTotalAmountRec));
					toolBarRec.getComponent('totalUnverifyAmountRec').setValue(stl.amountFormat(result.billDepWriteoffBillRecVo.billDepWriteoffBillRecDto.unverifyTotalAmountRec));
					
					Ext.Msg.alert(writeoff.billDepWriteoffBillRec.i18n('foss.stl.writeoff.common.alert'),writeoff.billDepWriteoffBillRec.i18n('foss.stl.writeoff.billDepWriteOffBillRec.writeoffSuccess'));
				},
				failure:function(response){
					var result = Ext.decode(response.responseText);
				},
				exception:function(response){
					var resultExp = Ext.decode(response.responseText);
					//设置统计值
					gridDep.store.load({
				      callback: function(records, operation, success) {
						var result =   Ext.decode(operation.response.responseText);
						
						toolBar = gridDep.getDockedItems('toolbar[dock="bottom"]')[0];
						toolBar.getComponent('totalRowsDep').setValue(result.billDepWriteoffBillRecVo.billDepWriteoffBillRecDto.totalNumDep);
						toolBar.getComponent('totalAmountDep').setValue(stl.amountFormat(result.billDepWriteoffBillRecVo.billDepWriteoffBillRecDto.totalAmountDep));
						toolBar.getComponent('totalVerifyAmountDep').setValue(stl.amountFormat(result.billDepWriteoffBillRecVo.billDepWriteoffBillRecDto.verifyTotalAmountDep));
						toolBar.getComponent('totalUnverifyAmountDep').setValue(stl.amountFormat(result.billDepWriteoffBillRecVo.billDepWriteoffBillRecDto.unverifyTotalAmountDep));
				       }
				    }); 
					gridRec.store.load({
					      callback: function(records, operation, success) {
							var result =   Ext.decode(operation.response.responseText);
							
							toolBar = gridRec.getDockedItems('toolbar[dock="bottom"]')[0];
							toolBar.getComponent('totalRowsRec').setValue(result.billDepWriteoffBillRecVo.billDepWriteoffBillRecDto.totalNumRec);
							toolBar.getComponent('totalAmountRec').setValue(stl.amountFormat(result.billDepWriteoffBillRecVo.billDepWriteoffBillRecDto.totalAmountRec));
							toolBar.getComponent('totalVerifyAmountRec').setValue(stl.amountFormat(result.billDepWriteoffBillRecVo.billDepWriteoffBillRecDto.verifyTotalAmountRec));
							toolBar.getComponent('totalUnverifyAmountRec').setValue(stl.amountFormat(result.billDepWriteoffBillRecVo.billDepWriteoffBillRecDto.unverifyTotalAmountRec));
					       }
					    }); 
					
					Ext.Msg.alert(writeoff.billDepWriteoffBillRec.i18n('foss.stl.writeoff.common.alert'),resultExp.message);
				}
			});
		}
	});
}

/**
 * 收银员核销
 */
writeoff.billDepWriteoffBillRec.writeoffByCashier = function(){
	var me = this;
	var gridDep = Ext.getCmp('T_writeoff-billDepWriteOffBillRec_content').getQueryBillDepGrid();
	var gridRec = Ext.getCmp('T_writeoff-billDepWriteOffBillRec_content').getQueryBillRecGrid();
	
	var selectionRec = gridRec.getSelectionModel().getSelection();
	var selectionDep = gridDep.getSelectionModel().getSelection();
	
	if(selectionRec.length==0){
		Ext.Msg.alert(writeoff.billDepWriteoffBillRec.i18n('foss.stl.writeoff.common.alert'),writeoff.billDepWriteoffBillRec.i18n('foss.stl.writeoff.billDepWriteOffBillRec.noBillReceivableDataByWriteoff'));
		return false;
	}
	if(selectionDep.length==0){
		Ext.Msg.alert(writeoff.billDepWriteoffBillRec.i18n('foss.stl.writeoff.common.alert'),writeoff.billDepWriteoffBillRec.i18n('foss.stl.writeoff.billDepWriteOffBillRec.noBillDepositReceiveDataByWriteoff'));
		return false;
	}
	
	
	Ext.Msg.confirm(writeoff.billDepWriteoffBillRec.i18n('foss.stl.writeoff.common.alert'),writeoff.billDepWriteoffBillRec.i18n('foss.stl.writeoff.billDepWriteOffBillRec.isConfirmWriteoff'),function(o){
		if(o=='yes'){
			var entity = new Object();

			var jsonDataRec = new Array();
			for(var i=0;i<selectionRec.length;i++){
				jsonDataRec.push(selectionRec[i].data);
			}
			entity.billReceivableEntityList = jsonDataRec;
			
			var jsonDataDep = new Array();
			for(var i=0;i<selectionDep.length;i++){
				jsonDataDep.push(selectionDep[i].data);
			}
			entity.billDepositreceivedEntityList = jsonDataDep;
			
			var paramstDep=gridDep.store.submitParams;
			var paramstRec=gridRec.store.submitParams;	
						
//			//设置条件
			var billDepositReceivedDto = new Object();
			billDepositReceivedDto.startBusinessDate = paramstDep["billDepWriteoffBillRecVo.billDepositReceivedDto.startBusinessDate"];
			billDepositReceivedDto.endBusinessDate = paramstDep["billDepWriteoffBillRecVo.billDepositReceivedDto.endBusinessDate"];
			billDepositReceivedDto.startAccountDate = paramstDep["billDepWriteoffBillRecVo.billDepositReceivedDto.startAccountDate"];
			billDepositReceivedDto.endAccountDate = paramstDep["billDepWriteoffBillRecVo.billDepositReceivedDto.endAccountDate"];
			billDepositReceivedDto.maxShowNum = paramstDep["billDepWriteoffBillRecVo.billDepositReceivedDto.maxShowNum"];
			billDepositReceivedDto.customerCode = paramstDep["billDepWriteoffBillRecVo.billDepositReceivedDto.customerCode"];
			billDepositReceivedDto.precollectedNumbers = paramstDep["billDepWriteoffBillRecVo.billDepositReceivedDto.precollectedNumbers"];
			billDepositReceivedDto.queryType = paramstDep["billDepWriteoffBillRecVo.billDepositReceivedDto.queryType"];
			entity.billDepositReceivedDto = billDepositReceivedDto;
			
			var billReceivableDto = new Object();
			billReceivableDto.startBusinessDate = paramstRec["billDepWriteoffBillRecVo.billReceivableDto.startBusinessDate"];
			billReceivableDto.endBusinessDate = paramstRec["billDepWriteoffBillRecVo.billReceivableDto.endBusinessDate"]; 
			billReceivableDto.startAccountDate = paramstRec["billDepWriteoffBillRecVo.billReceivableDto.startAccountDate"];
			billReceivableDto.endAccountDate = paramstRec["billDepWriteoffBillRecVo.billReceivableDto.endAccountDate"];
			billReceivableDto.maxShowNum = paramstRec["billDepWriteoffBillRecVo.billReceivableDto.maxShowNum"];
			billReceivableDto.customerCode = paramstRec["billDepWriteoffBillRecVo.billReceivableDto.customerCode"];
			billReceivableDto.receivableNumbers = paramstRec["billDepWriteoffBillRecVo.billReceivableDto.receivableNumbers"];
			billReceivableDto.queryType = paramstRec["billDepWriteoffBillRecVo.billReceivableDto.queryType"];
			entity.billReceivableDto = billReceivableDto;
			
			me.disable(false);
			//10秒后自动解除灰掉效果
			setTimeout(function() {
			me.enable(true);
			}, 10000);
			
			Ext.Ajax.request({
				//url:'../writeoff/writeoffBillDepAndBillRecByCashier.action',
				url:writeoff.realPath('writeoffBillDepAndBillRecByCashier.action'),
				jsonData:{'billDepWriteoffBillRecVo':entity},
				method:'post',
				success:function(response){
					var result = Ext.decode(response.responseText);
					var billReceivableEntryStore = gridRec.store;
					var billDepositreceivedEntityStore = gridDep.store;
					
					//加载后台查询到的数据到grid的store中
					billReceivableEntryStore.loadData(result.billDepWriteoffBillRecVo.billDepWriteoffBillRecDto.billReceivableEntityList);
					billDepositreceivedEntityStore.loadData(result.billDepWriteoffBillRecVo.billDepWriteoffBillRecDto.billDepositreceivedEntityList);
					
					toolBarDep = gridDep.getDockedItems('toolbar[dock="bottom"]')[0];
					toolBarDep.getComponent('totalRowsDep').setValue(result.billDepWriteoffBillRecVo.billDepWriteoffBillRecDto.totalNumDep);
					toolBarDep.getComponent('totalAmountDep').setValue(stl.amountFormat(result.billDepWriteoffBillRecVo.billDepWriteoffBillRecDto.totalAmountDep));
					toolBarDep.getComponent('totalVerifyAmountDep').setValue(stl.amountFormat(result.billDepWriteoffBillRecVo.billDepWriteoffBillRecDto.verifyTotalAmountDep));
					toolBarDep.getComponent('totalUnverifyAmountDep').setValue(stl.amountFormat(result.billDepWriteoffBillRecVo.billDepWriteoffBillRecDto.unverifyTotalAmountDep));
					
					toolBarRec = gridRec.getDockedItems('toolbar[dock="bottom"]')[0];
					toolBarRec.getComponent('totalRowsRec').setValue(result.billDepWriteoffBillRecVo.billDepWriteoffBillRecDto.totalNumRec);
					toolBarRec.getComponent('totalAmountRec').setValue(stl.amountFormat(result.billDepWriteoffBillRecVo.billDepWriteoffBillRecDto.totalAmountRec));
					toolBarRec.getComponent('totalVerifyAmountRec').setValue(stl.amountFormat(result.billDepWriteoffBillRecVo.billDepWriteoffBillRecDto.verifyTotalAmountRec));
					toolBarRec.getComponent('totalUnverifyAmountRec').setValue(stl.amountFormat(result.billDepWriteoffBillRecVo.billDepWriteoffBillRecDto.unverifyTotalAmountRec));
					
					Ext.Msg.alert(writeoff.billDepWriteoffBillRec.i18n('foss.stl.writeoff.common.alert'),writeoff.billDepWriteoffBillRec.i18n('foss.stl.writeoff.billDepWriteOffBillRec.writeoffSuccess'));
					
				},
				failure:function(response){
					var result = Ext.decode(response.responseText);
				},
				exception:function(response){
					var resultExp = Ext.decode(response.responseText);
					//设置统计值
					gridDep.store.load({
				      callback: function(records, operation, success) {
						var result =   Ext.decode(operation.response.responseText);
						
						toolBar = gridDep.getDockedItems('toolbar[dock="bottom"]')[0];
						toolBar.getComponent('totalRowsDep').setValue(result.billDepWriteoffBillRecVo.billDepWriteoffBillRecDto.totalNumDep);
						toolBar.getComponent('totalAmountDep').setValue(stl.amountFormat(result.billDepWriteoffBillRecVo.billDepWriteoffBillRecDto.totalAmountDep));
						toolBar.getComponent('totalVerifyAmountDep').setValue(stl.amountFormat(result.billDepWriteoffBillRecVo.billDepWriteoffBillRecDto.verifyTotalAmountDep));
						toolBar.getComponent('totalUnverifyAmountDep').setValue(stl.amountFormat(result.billDepWriteoffBillRecVo.billDepWriteoffBillRecDto.unverifyTotalAmountDep));
				       }
				    }); 
					gridRec.store.load({
					      callback: function(records, operation, success) {
							var result =   Ext.decode(operation.response.responseText);
							
							toolBar = gridRec.getDockedItems('toolbar[dock="bottom"]')[0];
							toolBar.getComponent('totalRowsRec').setValue(result.billDepWriteoffBillRecVo.billDepWriteoffBillRecDto.totalNumRec);
							toolBar.getComponent('totalAmountRec').setValue(stl.amountFormat(result.billDepWriteoffBillRecVo.billDepWriteoffBillRecDto.totalAmountRec));
							toolBar.getComponent('totalVerifyAmountRec').setValue(stl.amountFormat(result.billDepWriteoffBillRecVo.billDepWriteoffBillRecDto.verifyTotalAmountRec));
							toolBar.getComponent('totalUnverifyAmountRec').setValue(stl.amountFormat(result.billDepWriteoffBillRecVo.billDepWriteoffBillRecDto.unverifyTotalAmountRec));
					       }
					    }); 
					
					Ext.Msg.alert(writeoff.billDepWriteoffBillRec.i18n('foss.stl.writeoff.common.alert'),resultExp.message);
				}
			});
		}
	});
}


//最大显示条数model
Ext.define('Foss.StlBillDepWirteoffBillRec.ShowRecordCountModel',{
	extend:'Ext.data.Model',
	fields:[{
		name:'name'
	},{
		name:'value',
		type:'int'
	}]
});

//最大显示条数store
Ext.define('Foss.StlBillDepWirteoffBillRec.ShowRecordCountStore',{
	extend:'Ext.data.Store',
	model:'Foss.StlBillDepWirteoffBillRec.ShowRecordCountModel',
	data:{
		'items':[
			{name:'200',value:200},
			{name:'500',value:500},
			{name:'1000',value:1000},
			{name:'5000',value:5000}
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
	

//按日期查询 Form
Ext.define('Foss.StlBillDepWirteoffBillRec.StlBillDepAndBillRecQueryByDateFrom',{
	extend:'Ext.form.Panel',
	frame:false,
//	columnWidth:1,
	height:340,
	defaults:{
		margin :'10 10 10 10',
		labelWidth :85
	},
	defaultType:'textfield',
	layout:
	{
		type :'column',
		columns :3
	},
//	{
//		type :'table',
//		columns :3
//	},

	items : [{
       	//按预收单日期查询
        xtype : "fieldset",  
        title : writeoff.billDepWriteoffBillRec.i18n('foss.stl.writeoff.billDepWriteOffBillRec.queryDepByAccountDate'),
		defaults:{
			margin :'10 10 10 10',
			labelWidth :85
		},
        layout : {
        	type:'column',
        	columns:3
        },
        colspan:3,
//        columnWidth :1,
        items:[
        	{
		    	xtype      : 'fieldcontainer', 
				defaultType: 'radiofield',
				layout:'column',
//				columnWidth:.3,
				colspan:1,
				items: [{ 
						boxLabel  : writeoff.billDepWriteoffBillRec.i18n('foss.stl.writeoff.billDepWriteOffBillRec.queryByBusinessDate'),
						checked   : true,
						name      : 'selectDateDepFlag', 
						inputValue: '1'//表单的参数值
					},{ 
						boxLabel  : writeoff.billDepWriteoffBillRec.i18n('foss.stl.writeoff.billDepWriteOffBillRec.queryByAccountDate'),
						name      : 'selectDateDepFlag', 
						inputValue: '2'//表单的参数值						
				   }]
		    },{
		    	xtype:'datefield',
		    	name:'startDepDate',
		    	fieldLabel:writeoff.billDepWriteoffBillRec.i18n('foss.stl.writeoff.billDepWriteOffBillRec.startDate'),
		    	editable:false,
		    	value: stl.getTargetDate(new Date(),-writeoff.billDepWriteoffBillRec.STLBILLDEPWRITEOFFBILLREC_ONEMONTH),
		    	format:'Y-m-d',
		    	allowBlank:false,
//		    	columnWidth:.3
		    	colspan:1
		    },{
		    	xtype:'datefield',
		   	 	name:'endDepDate',
		    	fieldLabel:writeoff.billDepWriteoffBillRec.i18n('foss.stl.writeoff.billDepWriteOffBillRec.endDate'),
		    	editable:false,
		    	format:'Y-m-d',
		    	value:new Date(),
		    	allowBlank:false,
		    	maxValue:new Date(),
//		    	columnWidth:.3
		    	colspan:1
		    }
        ]
	},{  
		//按应收单日期查询
        xtype : "fieldset",  
        title : writeoff.billDepWriteoffBillRec.i18n('foss.stl.writeoff.billDepWriteOffBillRec.queryRecByAccountDate'),
        layout : 
       	{
       		type:'column',
       		columns:3
        },
		defaults:{
			margin :'10 10 10 10',
			labelWidth :85
		},
//        "column", 
//        columnWidth : 1,
        colspan:3,
        items:[
        	{
		    	xtype      : 'fieldcontainer', 
				defaultType: 'radiofield',
				layout:'column',
//				columnWidth:.3,
				colspan:1,
				items: [{ 
						boxLabel  : writeoff.billDepWriteoffBillRec.i18n('foss.stl.writeoff.billDepWriteOffBillRec.queryByBusinessDate'),
						checked   : true,
						name      : 'selectDateRecFlag', 
						inputValue: '1'//表单的参数值
					},{ 
						boxLabel  : writeoff.billDepWriteoffBillRec.i18n('foss.stl.writeoff.billDepWriteOffBillRec.queryByAccountDate'),
						name      : 'selectDateRecFlag', 
						inputValue: '2'//表单的参数值						
				   }]
		    },{
		    	xtype:'datefield',
		    	name:'startRecDate',
		    	fieldLabel:writeoff.billDepWriteoffBillRec.i18n('foss.stl.writeoff.billDepWriteOffBillRec.startDate'),
		    	editable:false,
		    	value: stl.getTargetDate(new Date(),-writeoff.billDepWriteoffBillRec.STLBILLDEPWRITEOFFBILLREC_ONEMONTH),
		    	format:'Y-m-d',
		    	allowBlank:false,
//		    	columnWidth:.3
		    	colspan:1
		    },{
		    	xtype:'datefield',
		   	 	name:'endRecDate',
		    	fieldLabel:writeoff.billDepWriteoffBillRec.i18n('foss.stl.writeoff.billDepWriteOffBillRec.endDate'),
		    	editable:false,
		    	format:'Y-m-d',
		    	value:new Date(),
		    	allowBlank:false,
		    	maxValue:new Date(),
//		    	columnWidth:.3
		    	colspan:1
		    }
        ]
	},
//	{
//    	xtype      : 'fieldcontainer', 
//		defaultType: 'radiofield',
//		layout:'table',
//		columnWidth:.9,
//		colspan:3,
//		items: [{ 
//				boxLabel  : writeoff.billDepWriteoffBillRec.i18n('foss.stl.writeoff.billDepWriteOffBillRec.queryByBusinessDate'),
//				checked   : true,
//				name      : 'selectDateFlag', 
//				inputValue: '1'//表单的参数值
//			},{ 
//				boxLabel  : writeoff.billDepWriteoffBillRec.i18n('foss.stl.writeoff.billDepWriteOffBillRec.queryByAccountDate'),
//				name      : 'selectDateFlag', 
//				inputValue: '2'//表单的参数值						
//		   }]
//    },{
//    	xtype:'datefield',
//    	name:'startDate',
//    	fieldLabel:writeoff.billDepWriteoffBillRec.i18n('foss.stl.writeoff.billDepWriteOffBillRec.startDate'),
//    	editable:false,
//    	value: stl.getTargetDate(new Date(),-writeoff.billDepWriteoffBillRec.STLBILLDEPWRITEOFFBILLREC_ONEMONTH),
//    	format:'Y-m-d',
//    	allowBlank:false,
//    	columnWidth:.27
//    },{
//    	xtype:'datefield',
//   	 	name:'endDate',
//    	fieldLabel:writeoff.billDepWriteoffBillRec.i18n('foss.stl.writeoff.billDepWriteOffBillRec.endDate'),
//    	editable:false,
//    	format:'Y-m-d',
//    	value:new Date(),
//    	allowBlank:false,
//    	maxValue:new Date(),
//    	columnWidth:.27
//    },
   	{
		xtype      : 'fieldcontainer', 
		defaultType: 'radiofield',
		layout:{
			type:'column',
			columns:3
		},
		defaults:{
			colspan:1
		},
//		columnWidth:.3,
		colspan:1,
        items: [{
            boxLabel  : writeoff.billDepWriteoffBillRec.i18n('foss.stl.writeoff.common.customerDetial'),
            name      : 'color',
            checked:true,
            inputValue: '11',
            handler:function(val){
        		if(val.value == true){
        			var form =this.up('form').getForm();
                	form.findField('customerDetial').show();
                	form.findField('airAgencyDetial').hide();
                	form.findField('airAgencyDetial').setValue("");
//                	form.findField('airDetial').hide();
//                	form.findField('airDetial').setValue("");
                	form.findField('vehAgencyDetial').hide();
                	form.findField('vehAgencyDetial').setValue("");
                	form.findField('landStowage').hide();
                	form.findField('landStowage').setValue("");
                	form.findField('partnerOrg').hide();
                	form.findField('partnerOrg').setValue();
        		}
    		}
//        },{
//            boxLabel  : writeoff.billDepWriteoffBillRec.i18n('foss.stl.writeoff.common.airDetial'),
//            name      : 'color',
//            inputValue: '22',
//            handler:function(val){
//        		if(val.value == true){
//        			var form =this.up('form').getForm();
//                	form.findField('airDetial').show();
//                	form.findField('customerDetial').hide();
//                	form.findField('customerDetial').setValue("");
//                	form.findField('airAgencyDetial').hide();
//                	form.findField('airAgencyDetial').setValue("");
//                	form.findField('vehAgencyDetial').hide();
//                	form.findField('vehAgencyDetial').setValue("");
//    				form.findField('landStowage').hide();
//               	form.findField('landStowage').setValue("");
//        		}
//    		}
        	},{
                boxLabel  : writeoff.billDepWriteoffBillRec.i18n('foss.stl.writeoff.common.airAgencyDetial'),
                name      : 'color',
                inputValue: '33',
                handler:function(val){
            		if(val.value == true){
            			var form =this.up('form').getForm();
	                	form.findField('airAgencyDetial').show();
	                	form.findField('customerDetial').hide();
	                	form.findField('customerDetial').setValue("");
//	                	form.findField('airDetial').hide();
//	                	form.findField('airDetial').setValue("");
	                	form.findField('vehAgencyDetial').hide();
	                	form.findField('vehAgencyDetial').setValue("");
	                	form.findField('landStowage').hide();
                		form.findField('landStowage').setValue("");
                		form.findField('partnerOrg').hide();
	                	form.findField('partnerOrg').setValue();
            		}
        		}
            }, {
                boxLabel  : writeoff.billDepWriteoffBillRec.i18n('foss.stl.writeoff.common.vehAgencyDetial'),
                name      : 'color',
                inputValue: '44',
                handler:function(value){
                	if(value.value == true){
                		var form =this.up('form').getForm();
                		form.findField('vehAgencyDetial').show();
	                	form.findField('customerDetial').hide();
	                	form.findField('customerDetial').setValue("");
//	                	form.findField('airDetial').hide();
//	                	form.findField('airDetial').setValue("");
	                	form.findField('airAgencyDetial').hide();
	                	form.findField('airAgencyDetial').setValue("");
	                	form.findField('landStowage').hide();
                		form.findField('landStowage').setValue("");
                		form.findField('partnerOrg').hide();
	                	form.findField('partnerOrg').setValue();
                	}
        		}
            }, {
                boxLabel  : writeoff.billDepWriteoffBillRec.i18n('foss.stl.writeoff.common.landStowage'),
                name      : 'color',
                inputValue: '55',
                handler:function(value){
                	if(value.value == true){
                		var form =this.up('form').getForm();
						form.findField('landStowage').show();
                		form.findField('vehAgencyDetial').hide();
                		form.findField('vehAgencyDetial').setValue("");
	                	form.findField('customerDetial').hide();
	                	form.findField('customerDetial').setValue("");
	                	form.findField('airAgencyDetial').hide();
	                	form.findField('airAgencyDetial').setValue("");
	                	form.findField('partnerOrg').hide();
	                	form.findField('partnerOrg').setValue();
                	}
        		}
            },{ // 合伙人
            	boxLabel  : writeoff.billDepWriteoffBillRec.i18n('foss.stl.writeoff.common.partner'),
                name      : 'color',
                inputValue: '40',
                handler:function(value){
                	if(value.value == true){
                		var form =this.up('form').getForm();
                		form.findField('partnerOrg').show();
                		form.findField('vehAgencyDetial').hide();
                		form.findField('vehAgencyDetial').setValue("");
	                	form.findField('customerDetial').hide();
	                	form.findField('customerDetial').setValue("");
	                	form.findField('airAgencyDetial').hide();
	                	form.findField('airAgencyDetial').setValue("");
	                	form.findField('landStowage').hide();
	                	form.findField('landStowage').setValue();
                	}
                }
            }
        ]
	},{
		xtype:'commoncustomerselector',
		all:'true',
		singlePeopleFlag : 'Y',
		fieldLabel:'<span style="color:red;">*</span>'+writeoff.billDepWriteoffBillRec.i18n('foss.stl.writeoff.common.customerDetial'),
		colspan:1,
		name:'customerDetial',
		listWidth:300,//设置下拉框宽度
		isPaging:true //分页
		//name : 'reverseBillWriteoffVo.reverseBillWriteoffDto.customerCode'
			
//	 },{	
//        	xtype:'commonairlinesselector',
//			all:'true',
//			fieldLabel:writeoff.billDepWriteoffBillRec.i18n('foss.stl.writeoff.common.airDetial'),
//			colspan:3,
//			name:'airDetial',
//			hidden:true,
//			allowBlank:true,
//			listWidth:300,//设置下拉框宽度
//			isPaging:true //分页
	 },{	
    	xtype:'commonairagentselector',
    	all:'true',
		fieldLabel:'<span style="color:red;">*</span>'+writeoff.billDepWriteoffBillRec.i18n('foss.stl.writeoff.common.airAgencyDetial'), 
		colspan:1,
		name:'airAgencyDetial',
		hidden:true,
		listWidth:300,//设置下拉框宽度
		isPaging:true //分页		
	 },{	
    	xtype:'commonvehagencycompselector',
    	all:'true',
		fieldLabel:'<span style="color:red;">*</span>'+writeoff.billDepWriteoffBillRec.i18n('foss.stl.writeoff.common.vehAgencyDetial'), 
		colspan:1,
		name:'vehAgencyDetial',
		hidden:true,
		listWidth:300,//设置下拉框宽度
		isPaging:true //分页
    },{
		
		xtype:'commonLdpAgencyCompanySelector',
		fieldLabel:'<span style="color:red;">*</span>'+writeoff.billDepWriteoffBillRec.i18n('foss.stl.writeoff.common.landStowage'),
		colspan:3,
		name:'landStowage',
		listWidth:300,//设置下拉框宽度
		hidden:true,
		isPaging:true //分页
	 },{//合伙人部门
		xtype:'commonsaledepartmentselector',
		fieldLabel:'<span style="color:red;">*</span>'+writeoff.billDepWriteoffBillRec.i18n('foss.stl.writeoff.common.partnerOrg'),
		colspan:3,
		name:'partnerOrg',
		listWidth:260,//设置下拉框宽度
		hidden:true,
		isPaging:true //分页
		 },{
    	xtype: 'combobox',
		name:'showRecordCount',
		editable:false,
        fieldLabel: writeoff.billDepWriteoffBillRec.i18n('foss.stl.writeoff.billDepWriteOffBillRec.showRecordCount'),
		store:Ext.create('Foss.StlBillDepWirteoffBillRec.ShowRecordCountStore'),
		queryModel:'local',
		displayField:'name',
		valueField:'value',
		colspan:1,
//        columnWidth:.4,
        value:'1000'
    }],
    dockedItems: [{
        xtype:'container',
		columnWidth:.75,
		dock: 'bottom',
		defaultType:'button',
		layout:{
			type:'column',
			columns:3
		},
		items:[{
			  text:writeoff.billDepWriteoffBillRec.i18n('foss.stl.writeoff.common.reset'),
			  columnWidth:.1,
			  // colspan:1,
			  handler:writeoff.billDepWriteoffBillRec.queryBillDepAndBillRecReset
		  	},
		  	{
				xtype:'container',
				border:false,
				html:'&nbsp;',
				columnWidth:.6
			},{
			  text:writeoff.billDepWriteoffBillRec.i18n('foss.stl.writeoff.common.query'),
			  columnWidth:.1,
			  cls:'yellow_button',  
			  handler:function(){
				  var form=this.up('form');
				  var me = this;
				  writeoff.billDepWriteoffBillRec.queryBillDepAndBillRec(form,me,writeoff.billDepWriteoffBillRec.STLBILLDEPWRITEOFFBILLREC_QUERY_BY_DATE)
			  }
		  	}]
    }]			
});

//按运单号查询 Form
Ext.define('Foss.StlBillDepWirteoffBillRec.StlBillDepAndBillRecQueryByNoFrom',{
	extend:'Ext.form.Panel',
	frame:false,
	columnWidth:1,
	height:280,
	defaults:{
		margin :'10 10 10 10',
		labelWidth :85,
		colspan : 1 
	},
	defaultType:'textfield',
	layout:{
		type :'column',
		columns :3
	},
	items : [{
	    		xtype: 'textarea',
	 			autoScroll:true,
	 			regex:/^((\s)*(YS)?[0-9]{7,14}[;,])*((\s)*(YS)?[0-9]{7,14}[;,]?)(\s)*$/i,
	 			regexText: writeoff.billDepWriteoffBillRec.i18n('foss.stl.writeoff.billDepWriteOffBillRec.receivableNoRegexText'), 
	 			emptyText: writeoff.billDepWriteoffBillRec.i18n('foss.stl.writeoff.billDepWriteOffBillRec.receivableNoEmptyText'), 
	            fieldLabel: writeoff.billDepWriteoffBillRec.i18n('foss.stl.writeoff.common.receivableNo'), 
	            name: 'receivableNos',
	            height : 80,
	            allowBlank:false,
	            columnWidth:.8
		    },{
		    	xtype:'container',
				border:false,
				html:'&nbsp;',
				columnWidth:.05
		    },{
		    	xtype: 'textarea',
	 			autoScroll:true,
	 			regex:/^((\s)*(US)?[0-9]{7,10}[;,])*((\s)*(US)?[0-9]{7,10}[;,]?)(\s)*$/i,
	 			regexText: writeoff.billDepWriteoffBillRec.i18n('foss.stl.writeoff.billDepWriteOffBillRec.precollectedNoRegexText'), 
	 			emptyText: writeoff.billDepWriteoffBillRec.i18n('foss.stl.writeoff.billDepWriteOffBillRec.precollectedNoEmptyText'), 
	            fieldLabel: writeoff.billDepWriteoffBillRec.i18n('foss.stl.writeoff.common.precollectedNo'), 
	            name: 'precollectedNos',
	            height : 80,
	            allowBlank:false,
	            columnWidth:.8
		    },{

				border: 1,
				xtype:'container',
				columnWidth:1,
				colspan:3,
				defaultType:'button',
				layout:'column',
				items:[{
					  text:writeoff.billDepWriteoffBillRec.i18n('foss.stl.writeoff.common.reset'),
					  columnWidth:.1,
					  handler:writeoff.billDepWriteoffBillRec.queryBillDepAndBillRecReset
				  	},{
						xtype:'container',
						border:false,
						html:'&nbsp;',
						columnWidth:.6
					},
				  	{
					  text:writeoff.billDepWriteoffBillRec.i18n('foss.stl.writeoff.common.query'),
					  columnWidth:.1,
					  cls:'yellow_button',  
					  handler:function(){
						  var form=this.up('form');
						  var me = this;
						  writeoff.billDepWriteoffBillRec.queryBillDepAndBillRec(form,me,writeoff.billDepWriteoffBillRec.STLBILLDEPWRITEOFFBILLREC_QUERY_BY_NO)
					  }
				  	}]
		    
		    }]			

});

//查询tab
Ext.define('Foss.StlBillDepWirteoffBillRec.StlBillDepWirteoffBillRecQueryInfoTab', {
	extend:'Ext.tab.Panel',
	frame:false,
	bodyCls: 'autoHeight',
	cls: 'innerTabPanel',
	activeTab: 0,
	//width : 1060,
	height : 340,
	items : [ {
		title: writeoff.billDepWriteoffBillRec.i18n('foss.stl.writeoff.common.queryByDate'), 
		tabConfig: {
			width: 120
		},
		width: '200',
        layout:'fit',
        items:[
               Ext.create('Foss.StlBillDepWirteoffBillRec.StlBillDepAndBillRecQueryByDateFrom')
               ]
	}, {
		title: writeoff.billDepWriteoffBillRec.i18n('foss.stl.writeoff.common.queryByNo'), 
		tabConfig: {
			width: 120
		},
        layout:'fit',
        items:[
               Ext.create('Foss.StlBillDepWirteoffBillRec.StlBillDepAndBillRecQueryByNoFrom')
               ]
	}]
});

//应收单model
Ext.define('Foss.StlBillDepWirteoffBillRec.BillReceivableEntryModel',{
	extend:'Ext.data.Model',
	fields:[{
		name:'receivableNo'
	},{
		name:'waybillNo'
	},{
		name:'billType'
	},{
		name:'customerName'
	},{
		name:'customerCode'
	},{
		name:'receivableOrgCode'
	},{
		name:'receivableOrgName'
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
		convert:stl.longToDateConvert
	},{
		name:'accountDate',
		type:'date',
		convert:stl.longToDateConvert
	},{
		name:'unlockDateTime',
		type:'date',
		convert:stl.longToDateConvert
	},{
		name: 'notes'
	},{
		name: 'active'
	},{
		name: 'isRedBack'
	},{
		name: 'versionNo',
		type:'short'
	},{
		name:'productCode'
	}]
});
//应收单Store
Ext.define('Foss.StlBillDepWirteoffBillRec.BillReceivableEntryStore',{
	extend:'Ext.data.Store',
	model:'Foss.StlBillDepWirteoffBillRec.BillReceivableEntryModel',
	sorters: [{
	     property: 'receivableNo',
	     direction: 'ASC'
	 }],
	 proxy:{
			type:'ajax',
			//url:'../writeoff/queryBillRec.action',
			url:writeoff.realPath('queryBillRec.action'),
			actionMethods:'post',
			reader:{
				type:'json',
				root:'billDepWriteoffBillRecVo.billDepWriteoffBillRecDto.billReceivableEntityList',
				totalProperty:'totalCount'
			}
		},
		submitParams: {},
		setSubmitParams: function(submitParams){
			this.submitParams = submitParams;
		},
		constructor:function(config){
			var me = this, 
				cfg = Ext.apply({}, config);
			me.listeners = {
		   		'beforeload': function(store, operation, eOpts){
		   			Ext.apply(me.submitParams, {
			          "limit":operation.limit,
			          "page":operation.page,
			          "start":operation.start
			          }); 
		   			Ext.apply(operation, {
		   				params : me.submitParams 
		   			});
		   		} 
			};
			me.callParent([ cfg ]);
		} 
});

//应收单列表可编辑
var SoperateColumnEditing = Ext.create('Ext.grid.plugin.CellEditing', {  
  	clicksToEdit : 1
}) ;
//应收单列表
Ext.define('Foss.StlBillDepWirteoffBillRec.StlBillRecQueryInfoGrid',{
	extend:'Ext.grid.Panel',
    title: writeoff.billDepWriteoffBillRec.i18n('foss.stl.writeoff.common.billReceivable'), 
	frame:true,
	hidden:true,
	height:400,
	emptyText: writeoff.billDepWriteoffBillRec.i18n('foss.stl.writeoff.common.noResult'), 
	selModel:Ext.create('Ext.selection.CheckboxModel'),
    store: Ext.create('Foss.StlBillDepWirteoffBillRec.BillReceivableEntryStore'),
    totalRowsRec:null,//应收单总条数
	getTotalRowsRec:function(){
		var me=this;
		if(Ext.isEmpty(me.totalRowsRec)){
			me.totalRowsRec=0;
		}
		return me.totalRowsRec;
	},
	totalAmountRec:null,//应收单总金额
	getTotalAmountRec:function(){
		var me=this;
		if(Ext.isEmpty(me.totalAmountRec)){
			me.totalAmountRec=0;
		}
		return me.totalAmountRec;
	},
	totalVerifyAmountRec:null,//应收单已核销总金额
	getVerifyAmountRec:function(){
		var me=this;
		if(Ext.isEmpty(me.totalVerifyAmountRec)){
			me.totalVerifyAmountRec=0;
		}
		return me.totalVerifyAmountRec;
	},
	totalUnverifyAmountRec:null,//应收单未核销总金额
	getTotalUnverifyAmountRec:function(){
		var me=this;
		if(Ext.isEmpty(me.totalUnverifyAmountRec)){
			me.totalUnverifyAmountRec=0;
		}
		return me.totalUnverifyAmountRec;
	},
	columns:[{
		header: writeoff.billDepWriteoffBillRec.i18n('foss.stl.writeoff.common.receivableNoCol'), 
		dataIndex: 'receivableNo'
	},{
		header: writeoff.billDepWriteoffBillRec.i18n('foss.stl.writeoff.common.waybillNo'), 
		dataIndex: 'waybillNo'
	}, {
		header :writeoff.billDepWriteoffBillRec.i18n('foss.stl.writeoff.common.productCode'),
		dataIndex : 'productCode',
		renderer : function(value) {
			return Foss.pkp.ProductData.rendererSubmitToDisplay(value);
		}
	},{
		header: writeoff.billDepWriteoffBillRec.i18n('foss.stl.writeoff.billDepWriteOffBillRec.receivableOrgName'), 
		dataIndex: 'receivableOrgName'
	},{
		header: writeoff.billDepWriteoffBillRec.i18n('foss.stl.writeoff.billDepWriteOffBillRec.receivableOrgCode'), 
		dataIndex: 'receivableOrgCode'		
	},{
		header: writeoff.billDepWriteoffBillRec.i18n('foss.stl.writeoff.common.customerName'), 
		dataIndex: 'customerName'
	},{
		header: writeoff.billDepWriteoffBillRec.i18n('foss.stl.writeoff.common.customerCode'), 
		dataIndex: 'customerCode'		
	},{
		header: writeoff.billDepWriteoffBillRec.i18n('foss.stl.writeoff.common.billType'), 
		dataIndex: 'billType',
		renderer:function(value){
    		var displayField = FossDataDictionary.rendererSubmitToDisplay(value,settlementDict.BILL_RECEIVABLE__BILL_TYPE);
    		return displayField;
    	}
	},{
		header: writeoff.billDepWriteoffBillRec.i18n('foss.stl.writeoff.common.amount'),
		dataIndex: 'amount',
		align: 'right',  
		renderer:stl.amountFormat	
	},{
		header: writeoff.billDepWriteoffBillRec.i18n('foss.stl.writeoff.common.verifyAmount'),
		dataIndex: 'verifyAmount',
		align: 'right',  
		renderer:stl.amountFormat	
	},{
		header: writeoff.billDepWriteoffBillRec.i18n('foss.stl.writeoff.common.unverifyAmount'),
		dataIndex: 'unverifyAmount',
		align: 'right',  
		renderer:stl.amountFormat	
	},{
		header: writeoff.billDepWriteoffBillRec.i18n('foss.stl.writeoff.common.businessDate'),
		dataIndex: 'businessDate',
		renderer:function(value){
			return stl.dateFormat(value, 'Y-m-d H:i:s');
		} 
	},{
		header: writeoff.billDepWriteoffBillRec.i18n('foss.stl.writeoff.common.accountDate'),
		dataIndex: 'accountDate',
		renderer:function(value){
			return stl.dateFormat(value, 'Y-m-d H:i:s');
		} 
	},{
		header: writeoff.billDepWriteoffBillRec.i18n('foss.stl.writeoff.billDepWriteOffBillRec.unlockDateTime'),
		dataIndex: 'unlockDateTime',
		renderer:function(value){
			return stl.dateFormat(value, 'Y-m-d H:i:s');
		} 
	},{
		header: writeoff.billDepWriteoffBillRec.i18n('foss.stl.writeoff.common.notes'),
		dataIndex: 'notes'
	},{
		header: writeoff.billDepWriteoffBillRec.i18n('foss.stl.writeoff.common.active'),
		hidden:true,
		dataIndex: 'active',
		renderer:function(value){
			var displayField = FossDataDictionary.rendererSubmitToDisplay(value,settlementDict.FOSS_ACTIVE);
    		return displayField;
		}
	},{
		header: writeoff.billDepWriteoffBillRec.i18n('foss.stl.writeoff.common.isRedBack'),
		hidden:true,
		dataIndex: 'isRedBack'
//	},{
//		header: writeoff.billDepWriteoffBillRec.i18n('foss.stl.writeoff.common.versionNo'),
//		hidden:true,
//		dataIndex: 'versionNo'
	}],
	constructor:function(config){
		
		var me = this;
		me.store=Ext.create('Foss.StlBillDepWirteoffBillRec.BillReceivableEntryStore');

		me.dockedItems =[{
			xtype: 'toolbar',
			dock: 'bottom',
			layout:'column',
			items: [{
				height:5,
				columnWidth:1
			},{
				xtype:'displayfield',
				allowBlank:true,
				itemId: 'totalRowsRec',
				name:'totalRowsRec',
				columnWidth:.1,
				labelWidth:80,
				fieldLabel: writeoff.billDepWriteoffBillRec.i18n('foss.stl.writeoff.common.totalRows'),
				value:me.getTotalRowsRec()
			},{
				xtype:'container',
				border:false,
				html:'&nbsp;',
				columnWidth:.05
			},{
				xtype:'displayfield',
				allowBlank:true,
				itemId: 'totalAmountRec',
				name:'totalAmountRec',
				columnWidth:.1,
				labelWidth:60,
				fieldLabel: writeoff.billDepWriteoffBillRec.i18n('foss.stl.writeoff.common.amount'),
				value:me.getTotalAmountRec()
			},{
				xtype:'container',
				border:false,
				html:'&nbsp;',
				columnWidth:.05
			},{
				xtype:'displayfield',
				allowBlank:true,
				itemId: 'totalVerifyAmountRec',
				name:'totalVerifyAmountRec',
				columnWidth:.1,
				labelWidth:80,
				fieldLabel:writeoff.billDepWriteoffBillRec.i18n('foss.stl.writeoff.common.verifyAmount'),
				value:me.getVerifyAmountRec()
			},{
				xtype:'container',
				border:false,
				html:'&nbsp;',
				columnWidth:.05
			},{
				xtype:'displayfield',
				allowBlank:true,
				labelWidth:80,
				itemId: 'totalUnverifyAmountRec',
				name:'totalUnverifyAmountRec',
				columnWidth:.1,
				fieldLabel:writeoff.billDepWriteoffBillRec.i18n('foss.stl.writeoff.common.unverifyAmount'),
				value:me.getTotalUnverifyAmountRec()
			}]
		}];	
		me.callParent();
	},
	viewConfig : {  
		enableTextSelection: true,
        forceFit : true,  
        enableRowBody: true,
        stripeRows: false,
        getRowClass : function(record,rowIndex,rowParams,store){ 
        	
            //禁用数据显示灰色
            if(record.data.unverifyAmount==0){  
                return 'disabledrow';  
            }else if(record.data.unverifyAmount > 0 && record.data.unverifyAmount < record.data.amount){  
                return 'x-grid-record-lightGray';  
            }else{
            	return '';  
            }  
              
        }  
    }
});


//预收单model
Ext.define('Foss.StlBillDepWirteoffBillRec.BillDepositreceivedEntityModel',{
	extend:'Ext.data.Model',
	fields:[{
		name:'depositReceivedNo'
	},{
		name:'customerName'
	},{
		name:'customerCode'
	},{
		name:'collectionOrgName'
	},{
		name:'collectionOrgCode'
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
		type:'long'		
	},{
		name:'status'
	},{
		name:'businessDate',
		type:'date',
		convert:stl.longToDateConvert
	},{
		name:'accountDate',
		type:'date',
		convert:stl.longToDateConvert
	},{
		name: 'notes'
	},{
		name: 'active'
	},{
		name: 'isRedBack'
	},{
		name: 'versionNo',
		type:'short'
	}]
});
//预收单Store
Ext.define('Foss.StlBillDepWirteoffBillRec.BillDepositreceivedEntityStore',{
	extend:'Ext.data.Store',
	model:'Foss.StlBillDepWirteoffBillRec.BillDepositreceivedEntityModel',
	sorters: [{
	     property: 'depositReceivedNo',
	     direction: 'ASC'
	 }],
	proxy:{
		type:'ajax',
		//url:'../writeoff/queryBillDep.action',
		url:writeoff.realPath('queryBillDep.action'),
		actionMethods:'post',
		reader:{
			type:'json',
			root:'billDepWriteoffBillRecVo.billDepWriteoffBillRecDto.billDepositreceivedEntityList',
			totalProperty:'totalCount'
		}
	},
	submitParams: {},
	setSubmitParams: function(submitParams){
		this.submitParams = submitParams;
	},
	constructor:function(config){
		var me = this, 
			cfg = Ext.apply({}, config);
		me.listeners = {
	   		'beforeload': function(store, operation, eOpts){
	   			Ext.apply(me.submitParams, {
		          "limit":operation.limit,
		          "page":operation.page,
		          "start":operation.start
		          }); 
	   			Ext.apply(operation, {
	   				params : me.submitParams 
	   			});
	   		} 
		};
		me.callParent([ cfg ]);
	} 
});

//预收单列表可编辑
var SoperateColumnEditing1 = Ext.create('Ext.grid.plugin.CellEditing', {  
  	clicksToEdit : 1
}) ;
//预收单列表
Ext.define('Foss.StlBillDepWirteoffBillRec.StlBillDepQueryInfoGrid',{
	extend:'Ext.grid.Panel',
    title: writeoff.billDepWriteoffBillRec.i18n('foss.stl.writeoff.common.billDepositReceive'),
	frame:true,
	hidden:true,
	height:400,
	emptyText: writeoff.billDepWriteoffBillRec.i18n('foss.stl.writeoff.common.noResult'),
	selModel:Ext.create('Ext.selection.CheckboxModel'),
    store: Ext.create('Foss.StlBillDepWirteoffBillRec.BillDepositreceivedEntityStore'),
    totalRowsDep:null,//预收单总条数
	getTotalRowsDep:function(){
		var me=this;
		if(Ext.isEmpty(me.totalRowsDep)){
			me.totalRowsDep=0;
		}
		return me.totalRowsDep;
	},
	totalAmountDep:null,//预收单总金额
	getTotalAmountDep:function(){
		var me=this;
		if(Ext.isEmpty(me.totalAmountDep)){
			me.totalAmountDep=0;
		}
		return me.totalAmountDep;
	},
	totalVerifyAmountDep:null,//预收单已核销总金额
	getVerifyAmountDep:function(){
		var me=this;
		if(Ext.isEmpty(me.totalVerifyAmountDep)){
			me.totalVerifyAmountDep=0;
		}
		return me.totalVerifyAmountDep;
	},
	totalUnverifyAmountDep:null,//预收单未核销总金额
	getTotalUnverifyAmountDep:function(){
		var me=this;
		if(Ext.isEmpty(me.totalUnverifyAmountDep)){
			me.totalUnverifyAmountDep=0;
		}
		return me.totalUnverifyAmountDep;
	},
	columns:[{
		header: writeoff.billDepWriteoffBillRec.i18n('foss.stl.writeoff.common.precollectedNo'),
		dataIndex: 'depositReceivedNo'
	},{
		header: writeoff.billDepWriteoffBillRec.i18n('foss.stl.writeoff.billDepWriteOffBillRec.collectionOrgName'),
		dataIndex: 'collectionOrgName'
	},{
		header: writeoff.billDepWriteoffBillRec.i18n('foss.stl.writeoff.billDepWriteOffBillRec.collectionOrgCode'),
		dataIndex: 'collectionOrgCode'		
	},{
		header: writeoff.billDepWriteoffBillRec.i18n('foss.stl.writeoff.common.customerName'),
		dataIndex: 'customerName'
	},{
		header: writeoff.billDepWriteoffBillRec.i18n('foss.stl.writeoff.common.customerCode'),
		dataIndex: 'customerCode'		
	},{
		header: writeoff.billDepWriteoffBillRec.i18n('foss.stl.writeoff.common.amount'),
		dataIndex: 'amount',
		align: 'right',  
		renderer:stl.amountFormat	
	},{
		header: writeoff.billDepWriteoffBillRec.i18n('foss.stl.writeoff.common.verifyAmount'),
		dataIndex: 'verifyAmount',
		align: 'right',  
		renderer:stl.amountFormat	
	},{
		header: writeoff.billDepWriteoffBillRec.i18n('foss.stl.writeoff.common.unverifyAmount'),
		dataIndex: 'unverifyAmount',
		align: 'right',  
		renderer:stl.amountFormat	
	},{
		header: writeoff.billDepWriteoffBillRec.i18n('foss.stl.writeoff.common.paymentAmount'),
		dataIndex: 'paymentAmount',
		align: 'right',  
		renderer:stl.amountFormat		
	},{
		header: writeoff.billDepWriteoffBillRec.i18n('foss.stl.writeoff.billDepWriteOffBillRec.status'),
		dataIndex: 'status',
		renderer:function(value){
			var displayField = FossDataDictionary.rendererSubmitToDisplay(value,settlementDict.BILL_DEPOSIT_RECEIVED__STATUS);
    		return displayField;
		}
	},{
		header: writeoff.billDepWriteoffBillRec.i18n('foss.stl.writeoff.common.businessDate'),
		dataIndex: 'businessDate',
		renderer:function(value){
			return stl.dateFormat(value, 'Y-m-d H:i:s');
		} 
	},{
		header: writeoff.billDepWriteoffBillRec.i18n('foss.stl.writeoff.common.accountDate'),
		dataIndex: 'accountDate',
		renderer:function(value){
			return stl.dateFormat(value, 'Y-m-d H:i:s');
		} 
	},{
		header: writeoff.billDepWriteoffBillRec.i18n('foss.stl.writeoff.common.notes'),
		dataIndex: 'notes'
	},{
		header: writeoff.billDepWriteoffBillRec.i18n('foss.stl.writeoff.common.active'),
		hidden:true,
		dataIndex: 'active',
		renderer:function(value){
			var displayField = FossDataDictionary.rendererSubmitToDisplay(value,settlementDict.FOSS_ACTIVE);
    		return displayField;
		}
	},{
		header: writeoff.billDepWriteoffBillRec.i18n('foss.stl.writeoff.common.isRedBack'),
		hidden:true,
		dataIndex: 'isRedBack'
//	},{
//		header: writeoff.billDepWriteoffBillRec.i18n('foss.stl.writeoff.common.versionNo'),
//		hidden:true,
//		dataIndex: 'versionNo'
	}],
	constructor:function(config){
		
		var me = this;
		me.store=Ext.create('Foss.StlBillDepWirteoffBillRec.BillDepositreceivedEntityStore');

		me.dockedItems =[{
			xtype: 'toolbar',
			dock: 'bottom',
			layout:'column',
			items: [{
				height:5,
				columnWidth:1
			},{
				xtype:'displayfield',
				itemId: 'totalRowsDep',
				name:'totalRowsDep',
				allowBlank:true,
				columnWidth:.1,
				labelWidth:80,
				fieldLabel:writeoff.billDepWriteoffBillRec.i18n('foss.stl.writeoff.common.totalRows'),
				value:me.getTotalRowsDep()
			},{
				xtype:'container',
				border:false,
				html:'&nbsp;',
				columnWidth:.05
			},{
				xtype:'displayfield',
				itemId: 'totalAmountDep',
				name:'totalAmountDep',
				allowBlank:true,
				columnWidth:.1,
				labelWidth:60,
				fieldLabel:writeoff.billDepWriteoffBillRec.i18n('foss.stl.writeoff.common.amount'),
				value:me.getTotalAmountDep()
			},{
				xtype:'container',
				border:false,
				html:'&nbsp;',
				columnWidth:.05
			},{
				xtype:'displayfield',
				itemId: 'totalVerifyAmountDep',
				name:'totalVerifyAmountDep',
				allowBlank:true,
				columnWidth:.1,
				labelWidth:80,
				fieldLabel:writeoff.billDepWriteoffBillRec.i18n('foss.stl.writeoff.common.verifyAmount'),
				value:me.getVerifyAmountDep()
			},{
				xtype:'container',
				border:false,
				html:'&nbsp;',
				columnWidth:.05
			},{
				xtype:'displayfield',
				itemId: 'totalUnverifyAmountDep',
				name:'totalUnverifyAmountDep',
				allowBlank:true,
				columnWidth:.1,
				labelWidth:80,
				fieldLabel:writeoff.billDepWriteoffBillRec.i18n('foss.stl.writeoff.common.unverifyAmount'),
				value:me.getTotalUnverifyAmountDep()
			},{
				xtype:'container',
				border:false,
				html:'&nbsp;',
				columnWidth:1
			},{
				xtype:'container',
				border:false,
				html:'&nbsp;',
				columnWidth:.7
			},{
				xtype:'button',
				text:writeoff.billDepWriteoffBillRec.i18n('foss.stl.writeoff.billDepWriteOffBillRec.writeoffByAccounting'),
				columnWidth:.1,
				handler:writeoff.billDepWriteoffBillRec.writeoffByAccounting,
				hidden: !writeoff.billDepWriteoffBillRec.isPermission('/stl-web/writeoff/writeoffBillDepAndBillRecByAccounting.action')
			},{
				xtype:'container',
				border:false,
				html:'&nbsp;',
				columnWidth:.005
			},{
				xtype:'button',
				text:writeoff.billDepWriteoffBillRec.i18n('foss.stl.writeoff.billDepWriteOffBillRec.writeoffByCashier'),
				columnWidth:.1,
				handler:writeoff.billDepWriteoffBillRec.writeoffByCashier,
				hidden: !writeoff.billDepWriteoffBillRec.isPermission('/stl-web/writeoff/writeoffBillDepAndBillRecByCashier.action')
			}]
		}];	
		me.callParent();
	},
	viewConfig: {  
		enableTextSelection: true,
        forceFit:true,  
        enableRowBody: true,
        stripeRows: false,
        getRowClass : function(record,rowIndex,rowParams,store){  
            //禁用数据显示灰色  
            if(record.data.unverifyAmount==0){  
                return 'disabledrow';  
            }else if(record.data.unverifyAmount > 0 && record.data.unverifyAmount < record.data.amount){  
                return 'x-grid-record-lightGray';      
            }else{  
                return '';  
            }  
              
        }  
    }
});

//初始化界面
Ext.onReady(function() {
	
	Ext.QuickTips.init();
	
	if (Ext.getCmp('T_writeoff-billDepWriteOffBillRec_content')) {
		return;
	} 
	
	//查询tab
	var StlBillDepWirteoffBillRecQueryInfoTab = Ext.create('Foss.StlBillDepWirteoffBillRec.StlBillDepWirteoffBillRecQueryInfoTab');
	
	//应收表格
	var StlBillRecQuerInfoGrid = Ext.create('Foss.StlBillDepWirteoffBillRec.StlBillRecQueryInfoGrid');
	
	//预收表格
	var StlBillDepQuerInfoGrid = Ext.create('Foss.StlBillDepWirteoffBillRec.StlBillDepQueryInfoGrid');
	
	Ext.create('Ext.panel.Panel',{
		id: 'T_writeoff-billDepWriteOffBillRec_content',
		cls: "panelContentNToolbar",
		bodyCls: 'panelContentNToolbar-body',
		layout: 'auto',
		
		//获得查询tab
		getQueryTab : function() {
			return StlBillDepWirteoffBillRecQueryInfoTab;
		},
		//获得应收GRID
		getQueryBillRecGrid : function() {
			return StlBillRecQuerInfoGrid;
		},
		//获得预收GRID
		getQueryBillDepGrid : function() {
			return StlBillDepQuerInfoGrid;
		},

		items: [StlBillDepWirteoffBillRecQueryInfoTab,StlBillRecQuerInfoGrid,StlBillDepQuerInfoGrid],
		renderTo: 'T_writeoff-billDepWriteOffBillRec-body'
	});
});