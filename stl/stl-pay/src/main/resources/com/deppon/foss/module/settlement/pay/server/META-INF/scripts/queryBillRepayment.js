/**
 * 还款查询日期限制
 */
pay.repayment.STLBILLREPAYMENT_MAX = 31;  // 时间间隔最大不超过90天
pay.repayment.STLBILLREPAYMENT_ONEMONTH = 1;  // 时间相差一月
pay.repayment.STLREVERSEWRITEOFF_ONEPAGESIZE = 50; //每页显示的条数

//按还款日期、按还款单、对账单、运单号、来源单号、银联交易流水号查询
pay.repayment.queryRepayment_TD='TD';
pay.repayment.queryRepayment_RT='RT';
pay.repayment.queryRepayment_TDZ='TDZ';
pay.repayment.queryRepayment_WB='WB';
pay.repayment.queryRepayment_TSB='TSB';
pay.repayment.queryRepayment_BB='BB';



pay.repayment.dept = FossUserContext. getCurrentUserDept();//当前登录部门
pay.repayment.user = FossUserContext.getCurrentUser();//当前登录用户
pay.repayment.roles = FossUserContext. getCurrentUserRoleCodes();


//格式化金额
pay.repayment.convertAmount=function setMoneyFormat(val){
	return "" + Ext.util.Format.currency(val,' ',2,false) + "";
} 

/**
 * 提示信息
 * @param {} message 
 * @param {} yesFn
 * @param {} noFn
 */
pay.billRepaymentConfirmAlert = function(message,yesFn,noFn){
	Ext.Msg.confirm(pay.repayment.i18n('foss.stl.pay.common.alert'),message,function(o){
		if(o=='yes'){
			yesFn();
		}else{
			noFn();
		}
	});
};

//还款单查询Form重置
pay.repayment.reset=function(){
	this.up('form').getForm().reset();
	var form =this.up('form').getForm().reset(); 
	var orgNameSeletor = form.findField('orgName');
	if(!Ext.isEmpty(stl.currentDept.code)){

		var displayText = stl.currentDept.name;
		var valueText = stl.currentDept.code;
		
		var store = orgNameSeletor.store;
		var  key = orgNameSeletor.displayField + '';
		var value = orgNameSeletor.valueField+ '';
		
		var m = Ext.create(store.model.modelName);
		m.set(key, displayText);
		m.set(value, valueText);
		
		store.loadRecords([m]);
		orgNameSeletor.setValue(valueText);
	}
	
	Ext.getCmp('pay.repayment.commoncustomerselector').show();
	Ext.getCmp('pay.repayment.commonairagencycompanyselector').hide();
	Ext.getCmp('pay.repayment.commonvehagencycompselector').hide();
	Ext.getCmp('pay.repayment.commonairlinesselector').hide();
	Ext.getCmp('pay.repayment.landStowage').hide();
}

//还款单查询
pay.repayment.queryByRepayment=function(me,btn,queryType){
	var form=me.getForm();
	if(form.isValid()){
		var gridRepaymentGrid = Ext.getCmp('T_pay-manageBillRepayment_content').getQueryGrid();
		var params=pay.repayment.queryByRepaymentSetParam(form,queryType);
		
		if(null==params || params==false){
			return;
		}
		//设置查询参数
		gridRepaymentGrid.store.setSubmitParams(params);
		//设置该按钮灰掉
		btn.disable(false);
		//30秒后自动解除灰掉效果
		setTimeout(function() {
			btn.enable(true);
		}, 30000);
		//查询后台
		gridRepaymentGrid.store.loadPage(1,{
		callback: function(records, operation, success) {
			var rawData = gridRepaymentGrid.store.proxy.reader.rawData;
			//当success:false ,isException:false  --业务异常
			if(!success && ! rawData.isException){
				Ext.Msg.alert(pay.repayment.i18n('foss.stl.pay.common.alert'),rawData.message);
				btn.enable(true);
				return false;
			}
			if(success){
				var result = Ext.decode(operation.response.responseText);  
				toolBar = gridRepaymentGrid.getDockedItems('toolbar[dock="bottom"]')[0];
//				toolBar = gridRepaymentGrid.down('toolbar');
				var totalAmount=pay.repayment.convertAmount(result.billRepaymentManageResultVo.billRepaymentManageResultDto.totalAmount);
				var tatalTrueAmount=pay.repayment.convertAmount(result.billRepaymentManageResultVo.billRepaymentManageResultDto.totalTrueAmount);
				var totalBVerifyAmount=pay.repayment.convertAmount(result.billRepaymentManageResultVo.billRepaymentManageResultDto.totalBVerifyAmount);
				
				toolBar.getComponent(1).setValue(result.billRepaymentManageResultVo.billRepaymentManageResultDto.totalCount);
				toolBar.getComponent(2).setValue(totalAmount);
				toolBar.getComponent(3).setValue(tatalTrueAmount);
				toolBar.getComponent(4).setValue(totalBVerifyAmount);
				
				var billRepaymentLength = 0;
				if(!Ext.isEmpty(result.billRepaymentManageResultVo.billRepaymentManageResultDto.billRepaymentQueryList)){
					billRepaymentLength = result.billRepaymentManageResultVo.billRepaymentManageResultDto.billRepaymentQueryList;
				}
				//判断是否有数据提示
				if(billRepaymentLength == 0){
					Ext.Msg.alert(pay.repayment.i18n('foss.stl.pay.common.alert'),pay.repayment.i18n('foss.stl.pay.queryBillRepayment.queryByRepaymentIsNullRegexText'),function(){
						btn.enable(true);
						return false;
					});	
				}else{
					gridRepaymentGrid.show();
				}
				btn.enable(true);
			}
	    }
	});
	}else {
		Ext.Msg.alert(pay.repayment.i18n('foss.stl.pay.common.alert'), pay.repayment.i18n('foss.stl.pay.common.validateFailAlert'));
 }
}


//设置还款单的查询参数
pay.repayment.queryByRepaymentSetParam=function(form,queryType){
	//定义查询参数
	var params={};

	//按照日期查询
	if(pay.repayment.queryRepayment_TD==queryType){
		
		
		//判断用户是选择的业务日期或者记账日期、还款单是否有效
		var selectDateFlagTemp=form.getValues().selectDateFlag;
		var isActive=form.getValues().isActive;
		
		//客户名称、部门名称、大区名称、小区名称、审核状态、还款方式
		var custType = form.findField('customerType').getValue();
		//给客户默认值
		var customerCode = null;
		//客户类型
		if(custType=='01'){
			customerCode=Ext.getCmp('pay.repayment.commoncustomerselector').getValue();
		}else if(custType=='02'){
			customerCode=Ext.getCmp('pay.repayment.commonairagencycompanyselector').getValue();
		}else if(custType=='03'){
			customerCode=Ext.getCmp('pay.repayment.commonvehagencycompselector').getValue();
		}else if(custType=='04'){
			customerCode=Ext.getCmp('pay.repayment.commonairlinesselector').getValue();
		}else if(custType=='05'){
			customerCode=Ext.getCmp('pay.repayment.landStowage').getValue();
		}
		
		var collectionOrgCode=form.findField('orgName').getValue();
		var largeArea =form.findField('largeAreaName').getValue();
		var smallArea=form.findField('smallAreaName').getValue();
		var auditStatus=form.findField('auditStatus').getValue();
		var paymentType=form.findField('repayWay').getValue();
		var isInit=form.getValues().isInit;

		//业务日期、账户日期
		var businessStartDateTemp;
		var businessEndDateTemp;
		var accountStartDateTemp;
		var accountEndDateTemp;
		var active;
		var IsYesOrNoInit;
		
		//校验日期是否正确
		var businessStartDate = form.findField('startDate').getValue();
		var businessEndDate = form.findField('endDate').getValue();
		//比较起始日期和结束日期
		var compareTwoDate = stl.compareTwoDate(businessStartDate,businessEndDate);
		if(compareTwoDate>pay.repayment.STLBILLREPAYMENT_MAX){
			Ext.Msg.alert(pay.repayment.i18n('foss.stl.pay.common.alert'),pay.repayment.i18n('foss.stl.pay.queryBillRepayment.queryDateMaxLimit'));
			return false;
		}else if(compareTwoDate<1){
			Ext.Msg.alert(pay.repayment.i18n('foss.stl.pay.common.alert'),pay.repayment.i18n('foss.stl.pay.common.dateEndBeforeStatrtWarining'));
			return false;
		}

		/* BUG-38768  BUG-38779 ISSUE-3086 控制查询条件，大区、小区和部门必须填写一项
		 * 
		 * 杨书硕
		 * 2013-6-29 下午3:36:52
		 */
		
		if(!(!!form.findField('largeAreaName').getValue()||!!form.findField('smallAreaName').getValue()||!!form.findField('orgName').getValue())){
			Ext.Msg.alert(pay.repayment.i18n('foss.stl.pay.common.alert'),pay.repayment.i18n('foss.stl.pay.common.validateRegionFailAlert'));
			return false;
		}

		//判断是根据业务日期查询或者是按照记账业务查询 1=按业务日期 2记账日期
		if('1'==selectDateFlagTemp){
			businessStartDateTemp=form.findField('startDate').getValue();
			businessEndDateTemp=form.findField('endDate').getValue();
		}else{
			accountStartDateTemp=form.findField('startDate').getValue();
			accountEndDateTemp=form.findField('endDate').getValue();
		}
		
		//用户选择查询有效或者无效
		if('Y'==isActive){
			active='Y';
		}else if('N'==isActive){
			active='N';
		}
		
		
		if('Y'==isInit){
			IsYesOrNoInit='Y';
		}else if('N'==isInit){
			IsYesOrNoInit='N';
		}
		//POS串号
		var	posSerialNum=form.findField('posSerialNum').getValue();
		//按日期查询时，提交参数
		Ext.apply(params,{ 
				'billRepaymentManageVo.billRepaymentManageDto.businessStartDate':businessStartDateTemp,
				'billRepaymentManageVo.billRepaymentManageDto.businessEndDate':businessEndDateTemp,
				'billRepaymentManageVo.billRepaymentManageDto.accountStartDate':accountStartDateTemp,
				'billRepaymentManageVo.billRepaymentManageDto.accountEndDate':accountEndDateTemp,
				'billRepaymentManageVo.billRepaymentManageDto.customerCode':customerCode,
				'billRepaymentManageVo.billRepaymentManageDto.collectionOrgCode':collectionOrgCode,
				'billRepaymentManageVo.billRepaymentManageDto.largeArea':largeArea,
				'billRepaymentManageVo.billRepaymentManageDto.smallArea':smallArea,
				'billRepaymentManageVo.billRepaymentManageDto.auditStatus':auditStatus,
				'billRepaymentManageVo.billRepaymentManageDto.paymentType':paymentType,
				'billRepaymentManageVo.billRepaymentManageDto.active':active,
				'billRepaymentManageVo.billRepaymentManageDto.isInit':IsYesOrNoInit,
				'billRepaymentManageVo.billRepaymentManageDto.queryType':queryType,
				'billRepaymentManageVo.billRepaymentManageDto.queryDateFlag':selectDateFlagTemp,
				'billRepaymentManageVo.billRepaymentManageDto.posSerialNum':Ext.String.trim(posSerialNum)
		});
		
	//按照还款单查询
	}else if(pay.repayment.queryRepayment_RT==queryType){
		
		//还款单号
		var repaymentNos=form.findField('repaymentNos').getValue();
		if(repaymentNos==null || repaymentNos==''){
			Ext.Msg.alert(pay.repayment.i18n('foss.stl.pay.common.alert'),pay.repayment.i18n('foss.stl.pay.queryBillRepayment.queryRepaymentNoNotNull'));
			return false;
		}
		var billRepayNoArray =[];
		if(Ext.String.trim(repaymentNos)!=null && Ext.String.trim(repaymentNos)!=''){
			 array = stl.splitToArray(repaymentNos);
			 for(var i=0;i<array.length;i++){
			 	if(Ext.String.trim(array[i])!=''){
			 		billRepayNoArray.push(array[i]);	
			 	}
			 }
			 if(billRepayNoArray.length>10){
			 	Ext.Msg.alert(pay.repayment.i18n('foss.stl.pay.common.alert'),pay.repayment.i18n('foss.stl.pay.common.queryNosLimit'));
			 	return false;
			 }
		}else{
			Ext.Msg.alert(pay.repayment.i18n('foss.stl.pay.common.alert'),pay.repayment.i18n('foss.stl.pay.queryBillRepayment.nosNotNull'));
			return false;
		}
		Ext.apply(params,{
			'billRepaymentManageVo.billRepaymentManageDto.repaymentNos':billRepayNoArray,
			'billRepaymentManageVo.billRepaymentManageDto.queryType':queryType
			});
	//按照对账单查询	
	}else if(pay.repayment.queryRepayment_TDZ==queryType){
		var statementofaccountNos= form.findField('statementofaccountNos').getValue();
		if(statementofaccountNos==null || statementofaccountNos==''){
			//对账单号是不能为空
			Ext.Msg.alert(pay.repayment.i18n('foss.stl.pay.common.alert'),pay.repayment.i18n('foss.stl.pay.queryBillRepayment.queryStatementBillNoNotNull'));
			return false;
		}
		var billRepayNoArray =[];
		if(Ext.String.trim(statementofaccountNos)!=null && Ext.String.trim(statementofaccountNos)!=''){
			 array = stl.splitToArray(statementofaccountNos);
			 for(var i=0;i<array.length;i++){
			 	if(Ext.String.trim(array[i])!=''){
			 		billRepayNoArray.push(array[i]);	
			 	}
			 }
			 if(billRepayNoArray.length>10){
			 	Ext.Msg.alert(pay.repayment.i18n('foss.stl.pay.common.alert'),pay.repayment.i18n('foss.stl.pay.common.queryNosLimit'));
			 	return false;
			 }
			 Ext.apply(params,{
				 'billRepaymentManageVo.billRepaymentManageDto.statementBillNos':billRepayNoArray,
				 'billRepaymentManageVo.billRepaymentManageDto.queryType':queryType
				 });
		}else{
			Ext.Msg.alert(pay.repayment.i18n('foss.stl.pay.common.alert'),pay.repayment.i18n('foss.stl.pay.queryBillRepayment.nosNotNull'));
			return false;
		}
	//按照运单查询
	}else if(pay.repayment.queryRepayment_WB==queryType){
		//运单号
		var waybillNos= form.findField('waybillNos').getValue();
		if(waybillNos==null || waybillNos==''){
			//‘温馨提示’，还款单号不能为空
			Ext.Msg.alert(pay.repayment.i18n('foss.stl.pay.common.alert'),pay.repayment.i18n('foss.stl.pay.queryBillRepayment.queryWaybillNoNotNull'));
			return false;
		}
		if(Ext.String.trim(waybillNos)!=null && Ext.String.trim(waybillNos)!=''){
			 var billRepayNoArray = waybillNos.match(/\d{8,}/g);
			 if(billRepayNoArray.length>10){
			 	Ext.Msg.alert(pay.repayment.i18n('foss.stl.pay.common.alert'),pay.repayment.i18n('foss.stl.pay.common.queryNosLimit'));
			 	return false;
			 }
			 Ext.apply(params,{
				 'billRepaymentManageVo.billRepaymentManageDto.wayBillNos':billRepayNoArray,
				 'billRepaymentManageVo.billRepaymentManageDto.queryType':queryType
				 });
		}else{
			Ext.Msg.alert(pay.repayment.i18n('foss.stl.pay.common.alert'),pay.repayment.i18n('foss.stl.pay.queryBillRepayment.nosNotNull'));
			return false;
		}
	 //按来源单号查询
	}else if(pay.repayment.queryRepayment_TSB==queryType){
		//来源单号
		var sourceBillNos= form.findField('sourceBillNo').getValue();
		if(sourceBillNos==null || sourceBillNos==''){
			//来源单号不能为空
			Ext.Msg.alert(pay.repayment.i18n('foss.stl.pay.common.alert'),pay.repayment.i18n('foss.stl.pay.queryBillRepayment.querySourceBillNoNotNull'));
			return false;
		}
		var billRepayNoArray =[];
		if(Ext.String.trim(sourceBillNos)!=null && Ext.String.trim(sourceBillNos)!=''){
			 array = stl.splitToArray(sourceBillNos);
			 for(var i=0;i<array.length;i++){
			 	if(Ext.String.trim(array[i])!=''){
			 		billRepayNoArray.push(array[i]);	
			 	}
			 }
			 if(billRepayNoArray.length>10){
			 	Ext.Msg.alert(pay.repayment.i18n('foss.stl.pay.common.alert'),pay.repayment.i18n('foss.stl.pay.common.queryNosLimit'));
			 	return false;
			 }
			 Ext.apply(params,{
				 'billRepaymentManageVo.billRepaymentManageDto.sourceBillNos':billRepayNoArray,
				 'billRepaymentManageVo.billRepaymentManageDto.queryType':queryType
				 });
		}else{
			Ext.Msg.alert(pay.repayment.i18n('foss.stl.pay.common.alert'),pay.repayment.i18n('foss.stl.pay.queryBillRepayment.nosNotNull'));
			return false;
		}
	}else if(pay.repayment.queryRepayment_BB==queryType){
		//银联交易流水号
		var batchBillNos= form.findField('batchBillNos').getValue();
		if(batchBillNos==null || batchBillNos==''){
			//银联交易流水号不能为空
			Ext.Msg.alert(pay.repayment.i18n('foss.stl.pay.common.alert'),pay.repayment.i18n('foss.stl.pay.queryBillRepayment.querySourceBillNoNotNull'));
			return false;
		}
		var billRepayNoArray =[];
		if(Ext.String.trim(batchBillNos)!=null && Ext.String.trim(batchBillNos)!=''){
			 array = stl.splitToArray(batchBillNos);
			 for(var i=0;i<array.length;i++){
			 	if(Ext.String.trim(array[i])!=''){
			 		billRepayNoArray.push(array[i]);	
			 	}
			 }
			 if(billRepayNoArray.length>10){
			 	Ext.Msg.alert(pay.repayment.i18n('foss.stl.pay.common.alert'),pay.repayment.i18n('foss.stl.pay.common.queryNosLimit'));
			 	return false;
			 }
			 Ext.apply(params,{
				 'billRepaymentManageVo.billRepaymentManageDto.batchNos':billRepayNoArray,
				 'billRepaymentManageVo.billRepaymentManageDto.queryType':queryType
				 });
		}else{
			Ext.Msg.alert(pay.repayment.i18n('foss.stl.pay.common.alert'),pay.repayment.i18n('foss.stl.pay.queryBillRepayment.nosNotNull'));
			return false;
		}
	}
	return params;
}



/**
 * 还款导出excel
 */
pay.repayment.repaymentForBillExport = function(){
	var me = this;
	var	columns,
		searchParams,
		arrayColumns,
		arrayColumnNames,
		grid;
	//获取表格
	grid = Ext.getCmp('T_pay-manageBillRepayment_content').getQueryGrid();
	var tab=Ext.getCmp('T_pay-manageBillRepayment_content').getQueryTab();
	//获取当前页签的表单
	var queryType = tab.getActiveTab().down('form').getQueryType(); 
	//获取当前选择的的form
	var form=tab.getActiveTab().down('form').getForm();
	
	

	
	//给客户默认值
	var customerCode = null;
	var IsYesOrNoInit;
	
	//如果没有查询到数据，则不能进行导出操作
	if(grid.store.data.length==0){
		Ext.Msg.alert(pay.repayment.i18n('foss.stl.pay.common.alert'),pay.repayment.i18n('foss.stl.pay.queryBillRepayment.noDataToExport'));
		return false;
	}
	
	//进行导出提示
	Ext.MessageBox.show({
        title: pay.repayment.i18n('foss.stl.pay.common.alert'),
        msg:pay.repayment.i18n('foss.stl.pay.queryBillRepayment.isExport'),
        buttons: Ext.MessageBox.YESNOCANCEL,
        buttonText:{ 
            yes:pay.repayment.i18n('foss.stl.pay.queryBillRepayment.defaultColumnExport'),
            no: pay.repayment.i18n('foss.stl.pay.queryBillRepayment.customColumnExport'),
            cancel:pay.repayment.i18n('foss.stl.pay.common.cancel')
        },
        fn: function(e){
        	if(e=='yes'){
        		//默认显示列
				arrayColumns = ['repaymentNo','customerCode','customerName','largeArea','smallArea',
				                'collectionOrgName','paymentType','posSerialNum','batchNo','amount',
								'trueAmount','bverifyAmount','auditStatus',
								'active','isRedBack','businessDate','accountDate',
								'notes','oaPaymentNo','oaPayee'];
				//默认显示列名称
				arrayColumnNames = [
				                    pay.repayment.i18n('foss.stl.pay.queryBillRepayment.repaymentNo'),
				                    pay.repayment.i18n('foss.stl.pay.queryBillRepayment.customerCode'),
				                    pay.repayment.i18n('foss.stl.pay.queryBillRepayment.customerName'),
				                    pay.repayment.i18n('foss.stl.pay.queryBillRepayment.largeRegion'),
				                    pay.repayment.i18n('foss.stl.pay.queryBillRepayment.smallRegin'),
				                    pay.repayment.i18n('foss.stl.pay.queryBillRepayment.collectionOrgName'),
				                    pay.repayment.i18n('foss.stl.pay.queryBillRepayment.paymentType'),
				                    pay.repayment.i18n('foss.stl.pay.queryBillRepayment.posSerialNum'), 
				                    pay.repayment.i18n('foss.stl.pay.queryBillRepayment.batchNo'), 
				                    pay.repayment.i18n('foss.stl.pay.queryBillRepayment.amount'),
				                    pay.repayment.i18n('foss.stl.pay.queryBillRepayment.trueAmount'),
				                    pay.repayment.i18n('foss.stl.pay.queryBillRepayment.bverifyAmount'),
				                    pay.repayment.i18n('foss.stl.pay.queryBillRepayment.auditStatus'),
				                    pay.repayment.i18n('foss.stl.pay.queryBillRepayment.active'),
				                    pay.repayment.i18n('foss.stl.pay.queryBillRepayment.isRedBack'),
				                    pay.repayment.i18n('foss.stl.pay.queryBillRepayment.businessDate'),
				                    pay.repayment.i18n('foss.stl.pay.queryBillRepayment.accountDate'),
				                    pay.repayment.i18n('foss.stl.pay.queryBillRepayment.notes'),
				                    pay.repayment.i18n('foss.stl.pay.queryBillRepayment.oaPaymentNo'),
				                    pay.repayment.i18n('foss.stl.pay.queryBillRepayment.oaPayee')
				                   ];									
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
						if(columns[i].text!=pay.repayment.i18n('foss.stl.pay.queryBillRepayment.actionColumn')){
							arrayColumns.push(dataIndex);
							arrayColumnNames.push(hederName);
						}
					}
				}
        	}else{
        		return false;
        	}
   			
        	if(!Ext.fly('downloadAttachFileForm')){
			    var frm = document.createElement('form');
			    frm.id = 'downloadAttachFileForm';
			    frm.style.display = 'none';
			    document.body.appendChild(frm);
			}
			
			//按日期查询
			if(pay.repayment.queryRepayment_TD==queryType){
				//客户名称、部门名称、大区名称、小区名称、审核状态、还款方式
				var custType = form.findField('customerType').getValue();
				//判断用户是选择的业务日期或者记账日期
				var selectDateFlagTemp=form.getValues().selectDateFlag;
				//是否有效
				var isActive=form.getValues().isActive;
				//业务日期、账户日期
				var businessStartDateTemp;
				var businessEndDateTemp;
				var accountStartDateTemp;
				var accountEndDateTemp;
				var active;
				
				var isInit=form.getValues().isInit;
				
				//判断是根据业务日期查询或者是按照记账业务查询 1=按业务日期 2记账日期
				if('1'==selectDateFlagTemp){
					businessStartDateTemp=form.findField('startDate').getValue();
					businessEndDateTemp=form.findField('endDate').getValue();
				}else{
					accountStartDateTemp=form.findField('startDate').getValue();
					accountEndDateTemp=form.findField('endDate').getValue();
				}
				
				//用户选择查询有效或者无效
				if('Y'==isActive){
					active='Y';
				}else if('N'==isActive){
					active='N';
				}
				
				if('Y'==isInit){
					IsYesOrNoInit='Y';
				}else if('N'==isInit){
					IsYesOrNoInit='N';
				}
			
				//客户类型
				if(custType=='01'){
					customerCode=Ext.getCmp('pay.repayment.commoncustomerselector').getValue();
				}else if(custType=='02'){
					customerCode=Ext.getCmp('pay.repayment.commonairagencycompanyselector').getValue();
				}else if(custType=='03'){
					customerCode=Ext.getCmp('pay.repayment.commonvehagencycompselector').getValue();
				}else if(custType=='04'){ 
					customerCode=Ext.getCmp('pay.repayment.commonairlinesselector').getValue();
				}else if(custType=='05'){
					customerCode=Ext.getCmp('pay.repayment.landStowage').getValue();
				}
				
				//部门名称
				var collectionOrgCode=form.findField('orgName').getValue();
				//大区名称
				var largeArea =form.findField('largeAreaName').getValue();
				//小区名称
				var smallArea=form.findField('smallAreaName').getValue();
				//审核状态
				var auditStatus=form.findField('auditStatus').getValue();
				//还款方式
				var paymentType=form.findField('repayWay').getValue();
				//POS串号
				var	posSerialNum=form.findField('posSerialNum').getValue();
				searchParams = {
						'billRepaymentManageVo.billRepaymentManageDto.businessStartDate':businessStartDateTemp,
						'billRepaymentManageVo.billRepaymentManageDto.businessEndDate':businessEndDateTemp,
						'billRepaymentManageVo.billRepaymentManageDto.accountStartDate':accountStartDateTemp,
						'billRepaymentManageVo.billRepaymentManageDto.accountEndDate':accountEndDateTemp,
						'billRepaymentManageVo.billRepaymentManageDto.customerCode':customerCode,
						'billRepaymentManageVo.billRepaymentManageDto.collectionOrgCode':collectionOrgCode,
						'billRepaymentManageVo.billRepaymentManageDto.largeArea':largeArea,
						'billRepaymentManageVo.billRepaymentManageDto.smallArea':smallArea,
						'billRepaymentManageVo.billRepaymentManageDto.auditStatus':auditStatus,
						'billRepaymentManageVo.billRepaymentManageDto.paymentType':paymentType,
						'billRepaymentManageVo.billRepaymentManageDto.active':active,
						'billRepaymentManageVo.billRepaymentManageDto.isInit':IsYesOrNoInit,
						'billRepaymentManageVo.billRepaymentManageDto.queryType':queryType,
						'billRepaymentManageVo.billRepaymentManageDto.queryDateFlag':selectDateFlagTemp,
						'billRepaymentManageVo.billRepaymentManageDto.arrayColumns':arrayColumns,
						'billRepaymentManageVo.billRepaymentManageDto.arrayColumnNames':arrayColumnNames,
						'billRepaymentManageVo.billRepaymentManageDto.posSerialNum':Ext.String.trim(posSerialNum)
				};
				//对账单
			}else if(pay.repayment.queryRepayment_RT==queryType){
				//还款单号
				var repaymentNos=form.findField('repaymentNos').getValue();
				if(repaymentNos==null || repaymentNos==''){
					Ext.Msg.alert(pay.repayment.i18n('foss.stl.pay.common.alert'),pay.repayment.i18n('foss.stl.pay.queryBillRepayment.queryRepaymentNoNotNull'));
					return false;
				}
				var billRepayNoArray =[];
				if(Ext.String.trim(repaymentNos)!=null && Ext.String.trim(repaymentNos)!=''){
					 array = stl.splitToArray(repaymentNos);
					 for(var i=0;i<array.length;i++){
					 	if(Ext.String.trim(array[i])!=''){
					 		billRepayNoArray.push(array[i]);	
					 	}
					 }
					 if(billRepayNoArray.length>10){
					 	Ext.Msg.alert(pay.repayment.i18n('foss.stl.pay.common.alert'),pay.repayment.i18n('foss.stl.pay.common.queryNosLimit'));
					 	return false;
					 }
				}else{
					Ext.Msg.alert(pay.repayment.i18n('foss.stl.pay.common.alert'),pay.repayment.i18n('foss.stl.pay.queryBillRepayment.nosNotNull'));
					return false;
				}
				searchParams = {
					'billRepaymentManageVo.billRepaymentManageDto.repaymentNos':billRepayNoArray,
					'billRepaymentManageVo.billRepaymentManageDto.queryType':queryType,
					'billRepaymentManageVo.billRepaymentManageDto.arrayColumns':arrayColumns,
					'billRepaymentManageVo.billRepaymentManageDto.arrayColumnNames':arrayColumnNames
				}
				//还款单
			}else if(pay.repayment.queryRepayment_TDZ==queryType){
				var statementofaccountNos=form.findField('statementofaccountNos').getValue();
				if(statementofaccountNos==null || statementofaccountNos==''){
					//查询对账单号不能为空
					Ext.Msg.alert(pay.repayment.i18n('foss.stl.pay.common.alert'),pay.repayment.i18n('foss.stl.pay.queryBillRepayment.queryStatementBillNoNotNull'));
					return false;
				}
				var billRepayNoArray =[];
				if(Ext.String.trim(statementofaccountNos)!=null && Ext.String.trim(statementofaccountNos)!=''){
					 array = stl.splitToArray(statementofaccountNos);
					 for(var i=0;i<array.length;i++){
					 	if(Ext.String.trim(array[i])!=''){
					 		billRepayNoArray.push(array[i]);	
					 	}
					 }
					 if(billRepayNoArray.length>10){
					 	Ext.Msg.alert(pay.repayment.i18n('foss.stl.pay.common.alert'),pay.repayment.i18n('foss.stl.pay.common.queryNosLimit'));
					 	return false;
					 }
					searchParams={
						 'billRepaymentManageVo.billRepaymentManageDto.statementBillNos':billRepayNoArray,
						 'billRepaymentManageVo.billRepaymentManageDto.queryType':queryType,
						 'billRepaymentManageVo.billRepaymentManageDto.arrayColumns':arrayColumns,
						 'billRepaymentManageVo.billRepaymentManageDto.arrayColumnNames':arrayColumnNames
						 }
				}else{
					Ext.Msg.alert(pay.repayment.i18n('foss.stl.pay.common.alert'),pay.repayment.i18n('foss.stl.pay.queryBillRepayment.nosNotNull'));
					return false;
				}
			}else if(pay.repayment.queryRepayment_WB==queryType){//运单
				//运单号
				var waybillNos=form.findField('waybillNos').getValue();
				if(waybillNos==null || waybillNos==''){
					Ext.Msg.alert(pay.repayment.i18n('foss.stl.pay.common.alert'),pay.repayment.i18n('foss.stl.pay.queryBillRepayment.queryWaybillNoNotNull'));
					return false;
				}
				var billRepayNoArray =[];
				if(Ext.String.trim(waybillNos)!=null && Ext.String.trim(waybillNos)!=''){
					 array = stl.splitToArray(waybillNos);
					 for(var i=0;i<array.length;i++){
					 	if(Ext.String.trim(array[i])!=''){
					 		billRepayNoArray.push(array[i]);	
					 	}
					 }
					 if(billRepayNoArray.length>10){
					 	Ext.Msg.alert(pay.repayment.i18n('foss.stl.pay.common.alert'),pay.repayment.i18n('foss.stl.pay.common.queryNosLimit'));
					 	return false;
					 }
					 searchParams={
						 'billRepaymentManageVo.billRepaymentManageDto.wayBillNos':billRepayNoArray,
						 'billRepaymentManageVo.billRepaymentManageDto.queryType':queryType,
						 'billRepaymentManageVo.billRepaymentManageDto.arrayColumns':arrayColumns,
						 'billRepaymentManageVo.billRepaymentManageDto.arrayColumnNames':arrayColumnNames
						 };
				}else{
					Ext.Msg.alert(pay.repayment.i18n('foss.stl.pay.common.alert'),pay.repayment.i18n('foss.stl.pay.queryBillRepayment.nosNotNull'));
					return false;
				}
			}else if(pay.repayment.queryRepayment_TSB==queryType){
				//来源单号
				//按来源单号查询
				var sourceBillNos=form.findField('sourceBillNo').getValue();
				if(sourceBillNos==null || sourceBillNos==''){
					Ext.Msg.alert(pay.repayment.i18n('foss.stl.pay.common.alert'),pay.repayment.i18n('foss.stl.pay.queryBillRepayment.querySourceBillNoNotNull'));
					return false;
				}
				var billRepayNoArray =[];
				if(Ext.String.trim(sourceBillNos)!=null && Ext.String.trim(sourceBillNos)!=''){
					 array = stl.splitToArray(sourceBillNos);
					 for(var i=0;i<array.length;i++){
					 	if(Ext.String.trim(array[i])!=''){
					 		billRepayNoArray.push(array[i]);	
					 	}
					 }
					 if(billRepayNoArray.length>10){
					 	Ext.Msg.alert(pay.repayment.i18n('foss.stl.pay.common.alert'),pay.repayment.i18n('foss.stl.pay.common.queryNosLimit'));
					 	return false;
					 }
					 searchParams={
						 'billRepaymentManageVo.billRepaymentManageDto.sourceBillNos':billRepayNoArray,
						 'billRepaymentManageVo.billRepaymentManageDto.queryType':queryType,
						 'billRepaymentManageVo.billRepaymentManageDto.arrayColumns':arrayColumns,
						 'billRepaymentManageVo.billRepaymentManageDto.arrayColumnNames':arrayColumnNames
						 };
				}else{
					Ext.Msg.alert(pay.repayment.i18n('foss.stl.pay.common.alert'),pay.repayment.i18n('foss.stl.pay.queryBillRepayment.nosNotNull'));
					return false;
				}
			}else{
				//按银联交易流水号导出
				var batchBillNos=form.findField('batchBillNos').getValue();
				if(batchBillNos==null || batchBillNos==''){
					Ext.Msg.alert(pay.repayment.i18n('foss.stl.pay.common.alert'),pay.repayment.i18n('foss.stl.pay.queryBillRepayment.querySourceBillNoNotNull'));
					return false;
				}
				var billRepayNoArray =[];
				if(Ext.String.trim(batchBillNos)!=null && Ext.String.trim(batchBillNos)!=''){
					 array = stl.splitToArray(batchBillNos);
					 for(var i=0;i<array.length;i++){
					 	if(Ext.String.trim(array[i])!=''){
					 		billRepayNoArray.push(array[i]);	
					 	}
					 }
					 if(billRepayNoArray.length>10){
					 	Ext.Msg.alert(pay.repayment.i18n('foss.stl.pay.common.alert'),pay.repayment.i18n('foss.stl.pay.common.queryNosLimit'));
					 	return false;
					 }
					 searchParams={
						 'billRepaymentManageVo.billRepaymentManageDto.batchNos':billRepayNoArray,
						 'billRepaymentManageVo.billRepaymentManageDto.queryType':queryType,
						 'billRepaymentManageVo.billRepaymentManageDto.arrayColumns':arrayColumns,
						 'billRepaymentManageVo.billRepaymentManageDto.arrayColumnNames':arrayColumnNames
						 };
				}else{
					Ext.Msg.alert(pay.repayment.i18n('foss.stl.pay.common.alert'),pay.repayment.i18n('foss.stl.pay.queryBillRepayment.nosNotNull'));
					return false;
				}
			}
			
			me.disable(false);
			
			//10秒后自动解除灰掉效果
			setTimeout(function() {
			me.enable(true);
			}, 10000);
			
			//拼接vo，注入到后台
			Ext.Ajax.request({
			    url:pay.realPath('exportBillrepayment.action'),
			    form: Ext.fly('downloadAttachFileForm'),
			    method : 'post',
			    params :searchParams,
			    isUpload: true,
			    success : function(response){
			    	var result = Ext.decode(response.responseText);
			    	
			    	if(!result.success && !result.isException){
			    		Ext.Msg.alert(pay.repayment.i18n('foss.stl.pay.common.alert'),result.message);
			    		return false;
			    	}
					Ext.ux.Toast.msg(pay.repayment.i18n('foss.stl.pay.common.alert'),pay.repayment.i18n('foss.stl.pay.queryBillRepayment.exportSuccess'), 'success', 1000);
			    },
				exception:function(response){
					var result = Ext.decode(response.responseText);
					Ext.ux.Toast.msg(pay.repayment.i18n('foss.stl.pay.common.alert'),pay.repayment.i18n('foss.stl.pay.queryBillRepayment.exportFailed'), 'error', 1000);
				}
			});
        }
	});
}




//--------------------------------------------------------qiaolifeng- 审核、作废、反审核、查看还款单明细--------------------------------------------------

//审核还款单
pay.repayment.aduitRepayment = function(grid, rowIndex, colIndex){
	
	//获取选中的还款单数据
	var selection = grid.getStore().getAt(rowIndex);	
	
	// 无效或红单的还款单不能审核
	if(selection.get('active')==pay.repayment.INACTIVE||selection.get('isRedBack')==pay.repayment.SETTLEMENT__IS_RED_BACK__YES){
		Ext.Msg.alert(pay.repayment.i18n('foss.stl.pay.common.alert'),pay.repayment.i18n('foss.stl.pay.queryBillRepayment.repaymentIsNotActiveOrRedBackCannotAuditWarning'));
		return false;
	}
	//判断是否可以审核还款单
	if(selection.get('auditStatus')==pay.repayment.BILL_PAYMENT__AUDIT_STATUS__AUDIT_AGREE){
		Ext.Msg.alert(pay.repayment.i18n('foss.stl.pay.common.alert'),pay.repayment.i18n('foss.stl.pay.queryBillRepayment.auditedRepaymentCannotAuditAgain'));
		return false;
	}
	//反核消生成的还款单不可以审核
	if(selection.get('amount')<=0){
		Ext.Msg.alert(pay.repayment.i18n('foss.stl.pay.common.alert'),pay.repayment.i18n('foss.stl.pay.queryBillRepayment.reWriteoffRepaymentCannotAudit'));
		return false;
	}
	
	//执行过反核消的还款单不可以审核
	if(selection.get('bverifyAmount')>0){
		Ext.Msg.alert(pay.repayment.i18n('foss.stl.pay.common.alert'),pay.repayment.i18n('foss.stl.pay.queryBillRepayment.reWriteoffRepaymentCannotAudit'));
		return false;
	}
	
	var yesFn=function(){
	
		//调用
		Ext.Ajax.request({
			url:pay.realPath('aduitBillRepayment.action'),
			params:{
				'billRepaymentManageVo.billRepaymentManageDto.selectBillRepayNos':selection.data.repaymentNo
			},
			success:function(response){
				//获取查询还款单grid
				var queryGrid = Ext.getCmp('T_pay-manageBillRepayment_content').getQueryGrid();
				//查询后台
				queryGrid.store.loadPage(1,{
				callback: function(records, operation, success) {
						var rawData = queryGrid.store.proxy.reader.rawData;
						//当success:false ,isException:false  --业务异常
						if(!success && ! rawData.isException){
							Ext.Msg.alert(pay.repayment.i18n('foss.stl.pay.common.alert'),rawData.message);
							return false;
						}
						if(success){
						 var result =   Ext.decode(operation.response.responseText); 
						 toolBar = queryGrid.getDockedItems('toolbar[dock="bottom"]')[0];
						 toolBar.getComponent(1).setValue(result.billRepaymentManageResultVo.billRepaymentManageResultDto.totalCount);
						 
						var totalAmount=pay.repayment.convertAmount(result.billRepaymentManageResultVo.billRepaymentManageResultDto.totalAmount);
						var tatalTrueAmount=pay.repayment.convertAmount(result.billRepaymentManageResultVo.billRepaymentManageResultDto.totalTrueAmount);
						var totalBVerifyAmount=pay.repayment.convertAmount(result.billRepaymentManageResultVo.billRepaymentManageResultDto.totalBVerifyAmount);
								
						toolBar.getComponent(1).setValue(result.billRepaymentManageResultVo.billRepaymentManageResultDto.totalCount);
						toolBar.getComponent(2).setValue(totalAmount);
						toolBar.getComponent(3).setValue(tatalTrueAmount);
						toolBar.getComponent(4).setValue(totalBVerifyAmount);
					}
				}
			});
			//查询后显示隐藏组件
			queryGrid.show();
			Ext.Msg.alert(pay.repayment.i18n('foss.stl.pay.common.alert'),pay.repayment.i18n('foss.stl.pay.queryBillRepayment.auditRepaymentSuccess'));
			},
			exception:function(response){
				var result = Ext.decode(response.responseText);	
				Ext.Msg.alert(pay.repayment.i18n('foss.stl.pay.common.alert'),result.message);
				
				//获取查询还款单grid
				var queryGrid = Ext.getCmp('T_pay-manageBillRepayment_content').getQueryGrid();
				
				//查询后台
				queryGrid.store.loadPage(1,{
				callback: function(records, operation, success) {
				var rawData = queryGrid.store.proxy.reader.rawData;
				//当success:false ,isException:false  --业务异常
						if(!success && ! rawData.isException){
							Ext.Msg.alert(pay.repayment.i18n('foss.stl.pay.common.alert'),rawData.message);
							return false;
						}
						if(success){
						 var result =   Ext.decode(operation.response.responseText); 
						 toolBar = queryGrid.getDockedItems('toolbar[dock="bottom"]')[0];
						 var totalAmount=pay.repayment.convertAmount(result.billRepaymentManageResultVo.billRepaymentManageResultDto.totalAmount);
						 var tatalTrueAmount=pay.repayment.convertAmount(result.billRepaymentManageResultVo.billRepaymentManageResultDto.totalTrueAmount);
						 var totalBVerifyAmount=pay.repayment.convertAmount(result.billRepaymentManageResultVo.billRepaymentManageResultDto.totalBVerifyAmount);
								
						 toolBar.getComponent(1).setValue(result.billRepaymentManageResultVo.billRepaymentManageResultDto.totalCount);
						 toolBar.getComponent(2).setValue(totalAmount);
						 toolBar.getComponent(3).setValue(tatalTrueAmount);
						 toolBar.getComponent(4).setValue(totalBVerifyAmount);
					}
				}
				});
			}
		});	
	};
	var noFn=function(){
	 	return false;
	};
	pay.billRepaymentConfirmAlert(pay.repayment.i18n('foss.stl.pay.queryBillRepayment.isConfimAudit'),yesFn,noFn);
}



//反审核还款单
pay.repayment.revAduitRepayment = function(grid, rowIndex, colIndex){
	
	//获取选中的还款单数据
	var selection = grid.getStore().getAt(rowIndex);	
	
	// 无效或红单的还款单不能审核
	if(selection.get('active')==pay.repayment.INACTIVE||selection.get('isRedBack')==pay.repayment.SETTLEMENT__IS_RED_BACK__YES){
		Ext.Msg.alert(pay.repayment.i18n('foss.stl.pay.common.alert'),pay.repayment.i18n('foss.stl.pay.queryBillRepayment.repaymentIsNotActiveOrRedBackCannotRevAuditWarning'));
		return false;
	}
	//判断是否可以审核还款单
	if(selection.get('auditStatus')==pay.repayment.BILL_PAYMENT__AUDIT_STATUS__NOT_AUDIT){
		Ext.Msg.alert(pay.repayment.i18n('foss.stl.pay.common.alert'),pay.repayment.i18n('foss.stl.pay.queryBillRepayment.unauditedRepaymentCannotRevAudit'));
		return false;
	}
	//反核消生成的还款单不可以反审核
	if(selection.get('amount')<=0){
		Ext.Msg.alert(pay.repayment.i18n('foss.stl.pay.common.alert'),pay.repayment.i18n('foss.stl.pay.queryBillRepayment.reWriteoffRepaymentCannotAuditBack'));
		return false;
	}
	//执行过反核消的还款单不可以反审核
	if(selection.get('bverifyAmount')>0){
		Ext.Msg.alert(pay.repayment.i18n('foss.stl.pay.common.alert'),pay.repayment.i18n('foss.stl.pay.queryBillRepayment.reWriteoffRepaymentCannotAuditBack'));
		return false;
	}
	
	var yesFn=function(){
		
		//调用
		Ext.Ajax.request({
			url:pay.realPath('revAduitBillRepayment.action'),
			params:{
				'billRepaymentManageVo.billRepaymentManageDto.selectBillRepayNos':selection.data.repaymentNo
			},
			success:function(response){
			//获取查询还款单grid
			var queryGrid = Ext.getCmp('T_pay-manageBillRepayment_content').getQueryGrid();
			//查询后台
			queryGrid.store.loadPage(1,{
			callback: function(records, operation, success) {
			var rawData = queryGrid.store.proxy.reader.rawData;
			//当success:false ,isException:false  --业务异常
			if(!success && ! rawData.isException){
				Ext.Msg.alert(pay.repayment.i18n('foss.stl.pay.common.alert'),rawData.message);
				return false;
			}
			if(success){
			 var result =Ext.decode(operation.response.responseText); 
			 toolBar = queryGrid.getDockedItems('toolbar[dock="bottom"]')[0];
			 var totalAmount=pay.repayment.convertAmount(result.billRepaymentManageResultVo.billRepaymentManageResultDto.totalAmount);
			 var tatalTrueAmount=pay.repayment.convertAmount(result.billRepaymentManageResultVo.billRepaymentManageResultDto.totalTrueAmount);
			 var totalBVerifyAmount=pay.repayment.convertAmount(result.billRepaymentManageResultVo.billRepaymentManageResultDto.totalBVerifyAmount);
					
			 toolBar.getComponent(1).setValue(result.billRepaymentManageResultVo.billRepaymentManageResultDto.totalCount);
			 toolBar.getComponent(2).setValue(totalAmount);
			 toolBar.getComponent(3).setValue(tatalTrueAmount);
			 toolBar.getComponent(4).setValue(totalBVerifyAmount);
			}
		}
	});
			//查询后显示隐藏组件
			queryGrid.show();
			//提示，反审核成功
			Ext.Msg.alert(pay.repayment.i18n('foss.stl.pay.common.alert'),pay.repayment.i18n('foss.stl.pay.queryBillRepayment.revAuditRepaymentSuccess'));
			},
			exception:function(response){
				var result = Ext.decode(response.responseText);	
				Ext.Msg.alert(pay.repayment.i18n('foss.stl.pay.common.alert'),result.message);
				
				//获取查询还款单grid
				var queryGrid = Ext.getCmp('T_pay-manageBillRepayment_content').getQueryGrid();
				
				//查询后台
				queryGrid.store.loadPage(1,{
				callback: function(records, operation, success) {
				var rawData = queryGrid.store.proxy.reader.rawData;
				//当success:false ,isException:false  --业务异常
						if(!success && ! rawData.isException){
							Ext.Msg.alert(pay.repayment.i18n('foss.stl.pay.common.alert'),rawData.message);
							return false;
						}
						if(success){
						 var result =   Ext.decode(operation.response.responseText); 
						 toolBar = queryGrid.getDockedItems('toolbar[dock="bottom"]')[0];
						 var totalAmount=pay.repayment.convertAmount(result.billRepaymentManageResultVo.billRepaymentManageResultDto.totalAmount);
						 var tatalTrueAmount=pay.repayment.convertAmount(result.billRepaymentManageResultVo.billRepaymentManageResultDto.totalTrueAmount);
						 var totalBVerifyAmount=pay.repayment.convertAmount(result.billRepaymentManageResultVo.billRepaymentManageResultDto.totalBVerifyAmount);
								
						 toolBar.getComponent(1).setValue(result.billRepaymentManageResultVo.billRepaymentManageResultDto.totalCount);
						 toolBar.getComponent(2).setValue(totalAmount);
						 toolBar.getComponent(3).setValue(tatalTrueAmount);
						 toolBar.getComponent(4).setValue(totalBVerifyAmount);
					}
				}
				});
			}
	});
 };
		var noFn=function(){
	 	return false;};
	 	pay.billRepaymentConfirmAlert(pay.repayment.i18n('foss.stl.pay.queryBillRepayment.isConfimReAudit'),yesFn,noFn);
}

//作废还款单
pay.repayment.disabledRepayment = function(grid, rowIndex, colIndex){

	//获取选中的还款单数据
	var selection = grid.getStore().getAt(rowIndex);	
	
	// 无效或红单的还款单不能审核
	if(selection.get('active')==pay.repayment.INACTIVE||selection.get('isRedBack')==pay.repayment.SETTLEMENT__IS_RED_BACK__YES){
		Ext.Msg.alert(pay.repayment.i18n('foss.stl.pay.common.alert'),pay.repayment.i18n('foss.stl.pay.queryBillRepayment.repaymentIsNotActiveOrRedBackCannotDisableWarning'));
		return false;
	}
	//反核消生成的还款单不可以作废
	if(selection.get('amount')<=0){
		Ext.Msg.alert(pay.repayment.i18n('foss.stl.pay.common.alert'),pay.repayment.i18n('foss.stl.pay.queryBillRepayment.reWriteoffRepaymentCannotDistable'));
		return false;
	}
	//执行过反核消的还款单不可以反作废
	if(selection.get('bverifyAmount')>0){
		Ext.Msg.alert(pay.repayment.i18n('foss.stl.pay.common.alert'),pay.repayment.i18n('foss.stl.pay.queryBillRepayment.reWriteoffRepaymentCannotDistable'));
		return false;
	}
	
	var yesFn=function(){
		//调用
		Ext.Ajax.request({
			url:pay.realPath('disabledBillRepayment.action'),
			params:{
				'billRepaymentManageVo.billRepaymentManageDto.selectBillRepayNos':selection.data.repaymentNo
			},
			success:function(response){
				//获取查询还款单grid
				var queryGrid = Ext.getCmp('T_pay-manageBillRepayment_content').getQueryGrid();
				//查询后台
				queryGrid.store.loadPage(1,{
				callback: function(records, operation, success) {
				var rawData = queryGrid.store.proxy.reader.rawData;
				//当success:false ,isException:false  --业务异常
						if(!success && ! rawData.isException){
							Ext.Msg.alert(pay.repayment.i18n('foss.stl.pay.common.alert'),rawData.message);
							return false;
						}
						if(success){
						 var result =   Ext.decode(operation.response.responseText); 
						 toolBar = queryGrid.getDockedItems('toolbar[dock="bottom"]')[0];
						 
						 var totalAmount=pay.repayment.convertAmount(result.billRepaymentManageResultVo.billRepaymentManageResultDto.totalAmount);
						 var tatalTrueAmount=pay.repayment.convertAmount(result.billRepaymentManageResultVo.billRepaymentManageResultDto.totalTrueAmount);
						 var totalBVerifyAmount=pay.repayment.convertAmount(result.billRepaymentManageResultVo.billRepaymentManageResultDto.totalBVerifyAmount);
								
						 toolBar.getComponent(1).setValue(result.billRepaymentManageResultVo.billRepaymentManageResultDto.totalCount);
						 toolBar.getComponent(2).setValue(totalAmount);
						 toolBar.getComponent(3).setValue(tatalTrueAmount);
						 toolBar.getComponent(4).setValue(totalBVerifyAmount);
					}
				}
			});
			//查询后显示隐藏组件
			queryGrid.show();
			Ext.Msg.alert(pay.repayment.i18n('foss.stl.pay.common.alert'),pay.repayment.i18n('foss.stl.pay.queryBillRepayment.disableRepaymentSuccess'));
			},
			exception:function(response){
				var result = Ext.decode(response.responseText);	
				Ext.Msg.alert(pay.repayment.i18n('foss.stl.pay.common.alert'),result.message);
				
				//获取查询还款单grid
				var queryGrid = Ext.getCmp('T_pay-manageBillRepayment_content').getQueryGrid();
				//查询后台
				queryGrid.store.loadPage(1,{
				callback: function(records, operation, success) {
				var rawData = queryGrid.store.proxy.reader.rawData;
				//当success:false ,isException:false  --业务异常
						if(!success && ! rawData.isException){
							Ext.Msg.alert(pay.repayment.i18n('foss.stl.pay.common.alert'),rawData.message);
							return false;
						}
						if(success){
						 var result =   Ext.decode(operation.response.responseText); 
						 toolBar = queryGrid.getDockedItems('toolbar[dock="bottom"]')[0];
						 var totalAmount=pay.repayment.convertAmount(result.billRepaymentManageResultVo.billRepaymentManageResultDto.totalAmount);
						 var tatalTrueAmount=pay.repayment.convertAmount(result.billRepaymentManageResultVo.billRepaymentManageResultDto.totalTrueAmount);
						 var totalBVerifyAmount=pay.repayment.convertAmount(result.billRepaymentManageResultVo.billRepaymentManageResultDto.totalBVerifyAmount);
								
						 toolBar.getComponent(1).setValue(result.billRepaymentManageResultVo.billRepaymentManageResultDto.totalCount);
						 toolBar.getComponent(2).setValue(totalAmount);
						 toolBar.getComponent(3).setValue(tatalTrueAmount);
						 toolBar.getComponent(4).setValue(totalBVerifyAmount);
					}
				}
				});
			}
		});	
	};
	var noFn=function(){
	 	return false;
	};
	pay.billRepaymentConfirmAlert(pay.repayment.i18n('foss.stl.pay.queryBillRepayment.isConfimDisabled'),yesFn,noFn);
}


//批量审核还款单
pay.repayment.aduitRepaymentBatch = function(){
	var me = this;
	//获取选中的还款单数据
	var selections = Ext.getCmp('T_pay-manageBillRepayment_content').getQueryGrid().getSelectionModel().getSelection();
	
	//如果未选中数据，提示至少选择一条记录进行操作
	if(selections.length==0){
		Ext.Msg.alert(pay.repayment.i18n('foss.stl.pay.common.alert'),pay.repayment.i18n('foss.stl.pay.queryBillRepayment.unSelectedOneAuditedRecordWarning'));
		return false;
	}
	
	var selectBillRepayNos = [];
	for(var i=0;i<selections.length;i++){
		
		// 无效或红单的还款单不能审核
		if(selections[i].get('active')==pay.repayment.INACTIVE||selections[i].get('isRedBack')==pay.repayment.SETTLEMENT__IS_RED_BACK__YES){
			Ext.Msg.alert(pay.repayment.i18n('foss.stl.pay.common.alert'),pay.repayment.i18n('foss.stl.pay.queryBillRepayment.repaymentIsNotActiveOrRedBackCannotAuditWarning'));
			return false;
		}
		
		//反核消生成的还款单不可以审核
		if(selections[i].get('amount')<=0){
			Ext.Msg.alert(pay.repayment.i18n('foss.stl.pay.common.alert'),pay.repayment.i18n('foss.stl.pay.queryBillRepayment.repayment')+selections[i].get('repaymentNo')+pay.repayment.i18n('foss.stl.pay.queryBillRepayment.reWriteoffRepaymentCannotAudit'));
			return false;
		}
		
		if(selections[i].get('auditStatus')==pay.repayment.BILL_PAYMENT__AUDIT_STATUS__AUDIT_AGREE){
			Ext.Msg.alert(pay.repayment.i18n('foss.stl.pay.common.alert'),pay.repayment.i18n('foss.stl.pay.queryBillRepayment.repayment')+selections[i].get('repaymentNo')+pay.repayment.i18n('foss.stl.pay.queryBillRepayment.repaymentHasBeenAuditedReselectAgain'));
			return false;
		}
		
		//执行过反核消的还款单不可以审核
		if(selections[i].get('bverifyAmount')>0){
			Ext.Msg.alert(pay.repayment.i18n('foss.stl.pay.common.alert'),pay.repayment.i18n('foss.stl.pay.queryBillRepayment.reWriteoffRepaymentCannotAudit'));
			return false;
		}
		
		selectBillRepayNos.push(selections[i].get('repaymentNo'));
	}
	
	var yesFn=function(){
		
	
		me.disable(false);
		//10秒后自动解除灰掉效果
		setTimeout(function() {
		me.enable(true);
		}, 10000);
		
		//调用
		Ext.Ajax.request({
			url:pay.realPath('aduitBillRepayment.action'),
			params:{
				'billRepaymentManageVo.billRepaymentManageDto.selectBillRepayNos':selectBillRepayNos
			},
			success:function(response){
				//获取查询还款单grid
				var queryGrid = Ext.getCmp('T_pay-manageBillRepayment_content').getQueryGrid();
				//查询后台
				queryGrid.store.loadPage(1,{
				callback: function(records, operation, success) {
				var rawData = queryGrid.store.proxy.reader.rawData;
				//当success:false ,isException:false  --业务异常
						if(!success && ! rawData.isException){
							Ext.Msg.alert(pay.repayment.i18n('foss.stl.pay.common.alert'),rawData.message);
							return false;
						}
						if(success){
						 var result =   Ext.decode(operation.response.responseText); 
						 toolBar = queryGrid.getDockedItems('toolbar[dock="bottom"]')[0];
						 var totalAmount=pay.repayment.convertAmount(result.billRepaymentManageResultVo.billRepaymentManageResultDto.totalAmount);
						 var tatalTrueAmount=pay.repayment.convertAmount(result.billRepaymentManageResultVo.billRepaymentManageResultDto.totalTrueAmount);
						 var totalBVerifyAmount=pay.repayment.convertAmount(result.billRepaymentManageResultVo.billRepaymentManageResultDto.totalBVerifyAmount);
								
						 toolBar.getComponent(1).setValue(result.billRepaymentManageResultVo.billRepaymentManageResultDto.totalCount);
						 toolBar.getComponent(2).setValue(totalAmount);
						 toolBar.getComponent(3).setValue(tatalTrueAmount);
						 toolBar.getComponent(4).setValue(totalBVerifyAmount);
					}
				}
			});
			//查询后显示隐藏组件
			queryGrid.show();
			Ext.Msg.alert(pay.repayment.i18n('foss.stl.pay.common.alert'),pay.repayment.i18n('foss.stl.pay.queryBillRepayment.auditedRepaymentSuccess'));
			},
			exception:function(response){
				var result = Ext.decode(response.responseText);	
				Ext.Msg.alert(pay.repayment.i18n('foss.stl.pay.common.alert'),result.message);
				
				//获取查询还款单grid
				var queryGrid = Ext.getCmp('T_pay-manageBillRepayment_content').getQueryGrid();
				//查询后台
				queryGrid.store.loadPage(1,{
				callback: function(records, operation, success) {
				var rawData = queryGrid.store.proxy.reader.rawData;
				//当success:false ,isException:false  --业务异常
						if(!success && ! rawData.isException){
							Ext.Msg.alert(pay.repayment.i18n('foss.stl.pay.common.alert'),rawData.message);
							return false;
						}
						if(success){
						 var result =   Ext.decode(operation.response.responseText); 
						 toolBar = queryGrid.getDockedItems('toolbar[dock="bottom"]')[0];
						 var totalAmount=pay.repayment.convertAmount(result.billRepaymentManageResultVo.billRepaymentManageResultDto.totalAmount);
						 var tatalTrueAmount=pay.repayment.convertAmount(result.billRepaymentManageResultVo.billRepaymentManageResultDto.totalTrueAmount);
						 var totalBVerifyAmount=pay.repayment.convertAmount(result.billRepaymentManageResultVo.billRepaymentManageResultDto.totalBVerifyAmount);
								
						 toolBar.getComponent(1).setValue(result.billRepaymentManageResultVo.billRepaymentManageResultDto.totalCount);
						 toolBar.getComponent(2).setValue(totalAmount);
						 toolBar.getComponent(3).setValue(tatalTrueAmount);
						 toolBar.getComponent(4).setValue(totalBVerifyAmount);
					}
				}
				});
			}
		});	
	};
	var noFn=function(){
	 	return false;
	};
	pay.billRepaymentConfirmAlert(pay.repayment.i18n('foss.stl.pay.queryBillRepayment.isConfimAudit'),yesFn,noFn);
}




//批量反审核还款单
pay.repayment.revAduitRepaymentBatch = function(){
	var me = this;
	
	//获取选中的还款单数据
	var selections = Ext.getCmp('T_pay-manageBillRepayment_content').getQueryGrid().getSelectionModel().getSelection();
	
	//如果未选中数据，提示至少选择一条记录进行操作
	if(selections.length==0){
		Ext.Msg.alert(pay.repayment.i18n('foss.stl.pay.common.alert'),pay.repayment.i18n('foss.stl.pay.queryBillRepayment.unSelectedOneRevAuditedRecordWarning'));
		return false;
	}
	
	var selectBillRepayNos = [];
	for(var i=0;i<selections.length;i++){
		
		// 无效或红单的还款单不能审核
		if(selections[i].get('active')==pay.repayment.INACTIVE||selections[i].get('isRedBack')==pay.repayment.SETTLEMENT__IS_RED_BACK__YES){
			Ext.Msg.alert(pay.repayment.i18n('foss.stl.pay.common.alert'),pay.repayment.i18n('foss.stl.pay.queryBillRepayment.repaymentIsNotActiveOrRedBackCannotRevAuditWarning'));
			return false;
		}
		//反核消生成的还款单不可以反审核
		if(selections[i].get('amount')<=0){
			Ext.Msg.alert(pay.repayment.i18n('foss.stl.pay.common.alert'),pay.repayment.i18n('foss.stl.pay.queryBillRepayment.repayment')+selections[i].get('repaymentNo')+pay.repayment.i18n('foss.stl.pay.queryBillRepayment.reWriteoffRepaymentCannotAuditBack'));
			return false;
		}
		if(selections[i].get('auditStatus')==pay.repayment.BILL_PAYMENT__AUDIT_STATUS__NOT_AUDIT){
			Ext.Msg.alert(pay.repayment.i18n('foss.stl.pay.common.alert'),pay.repayment.i18n('foss.stl.pay.queryBillRepayment.repayment')+selections[i].get('repaymentNo')+pay.repayment.i18n('foss.stl.pay.queryBillRepayment.repaymentNoAuditedReselectAgain'));
			return false;
		}
		
		//执行过反核消的还款单不可以反审核
		if(selections[i].get('bverifyAmount')>0){
			Ext.Msg.alert(pay.repayment.i18n('foss.stl.pay.common.alert'),pay.repayment.i18n('foss.stl.pay.queryBillRepayment.reWriteoffRepaymentCannotAuditBack'));
			return false;
		}
		
		selectBillRepayNos.push(selections[i].get('repaymentNo'));
	}
	
	var yesFn=function(){
		
	
		me.disable(false);
		//10秒后自动解除灰掉效果
		setTimeout(function() {
		me.enable(true);
		}, 10000);
		
		//调用
		Ext.Ajax.request({
			url:pay.realPath('revAduitBillRepayment.action'),
			params:{
				'billRepaymentManageVo.billRepaymentManageDto.selectBillRepayNos':selectBillRepayNos
			},
			success:function(response){
				//获取查询还款单grid
				var queryGrid = Ext.getCmp('T_pay-manageBillRepayment_content').getQueryGrid();
				//查询后台
				queryGrid.store.loadPage(1,{
				callback: function(records, operation, success) {
				var rawData = queryGrid.store.proxy.reader.rawData;
				//当success:false ,isException:false  --业务异常
						if(!success && ! rawData.isException){
							Ext.Msg.alert(pay.repayment.i18n('foss.stl.pay.common.alert'),rawData.message);
							return false;
						}
						if(success){
						 var result =   Ext.decode(operation.response.responseText); 
						 toolBar = queryGrid.getDockedItems('toolbar[dock="bottom"]')[0];
						 var totalAmount=pay.repayment.convertAmount(result.billRepaymentManageResultVo.billRepaymentManageResultDto.totalAmount);
						 var tatalTrueAmount=pay.repayment.convertAmount(result.billRepaymentManageResultVo.billRepaymentManageResultDto.totalTrueAmount);
						 var totalBVerifyAmount=pay.repayment.convertAmount(result.billRepaymentManageResultVo.billRepaymentManageResultDto.totalBVerifyAmount);
								
						 toolBar.getComponent(1).setValue(result.billRepaymentManageResultVo.billRepaymentManageResultDto.totalCount);
						 toolBar.getComponent(2).setValue(totalAmount);
						 toolBar.getComponent(3).setValue(tatalTrueAmount);
						 toolBar.getComponent(4).setValue(totalBVerifyAmount);
					}
				}
			});
			//查询后显示隐藏组件
			queryGrid.show();
			Ext.Msg.alert(pay.repayment.i18n('foss.stl.pay.common.alert'),pay.repayment.i18n('foss.stl.pay.queryBillRepayment.revAuditedRepaymentSuccess'));
			},
			exception:function(response){
				var result = Ext.decode(response.responseText);	
				Ext.Msg.alert(pay.repayment.i18n('foss.stl.pay.common.alert'),result.message);
				
				//获取查询还款单grid
				var queryGrid = Ext.getCmp('T_pay-manageBillRepayment_content').getQueryGrid();
				
				//查询后台
				queryGrid.store.loadPage(1,{
				callback: function(records, operation, success) {
				var rawData = queryGrid.store.proxy.reader.rawData;
				//当success:false ,isException:false  --业务异常
						if(!success && ! rawData.isException){
							Ext.Msg.alert(pay.repayment.i18n('foss.stl.pay.common.alert'),rawData.message);
							return false;
						}
						if(success){
						 var result =   Ext.decode(operation.response.responseText); 
						 toolBar = gridRepaymentGrid.getDockedItems('toolbar[dock="bottom"]')[0];
						 
						var totalAmount=pay.repayment.convertAmount(result.billRepaymentManageResultVo.billRepaymentManageResultDto.totalAmount);
						var tatalTrueAmount=pay.repayment.convertAmount(result.billRepaymentManageResultVo.billRepaymentManageResultDto.totalTrueAmount);
						var totalBVerifyAmount=pay.repayment.convertAmount(result.billRepaymentManageResultVo.billRepaymentManageResultDto.totalBVerifyAmount);
								
						toolBar.getComponent(1).setValue(result.billRepaymentManageResultVo.billRepaymentManageResultDto.totalCount);
						toolBar.getComponent(2).setValue(totalAmount);
						toolBar.getComponent(3).setValue(tatalTrueAmount);
						toolBar.getComponent(4).setValue(totalBVerifyAmount);
					}
				}
				});
			}
		});
	};
	var noFn=function(){
	 	return false;
	};
	pay.billRepaymentConfirmAlert(pay.repayment.i18n('foss.stl.pay.queryBillRepayment.isConfimReAudit'),yesFn,noFn);
}

//批量作废还款单
pay.repayment.disabledRepaymentBatch = function(){
	var me = this;
	//获取选中的还款单数据
	var selections = Ext.getCmp('T_pay-manageBillRepayment_content').getQueryGrid().getSelectionModel().getSelection();
	
	//如果未选中数据，提示至少选择一条记录进行操作
	if(selections.length==0){
		Ext.Msg.alert(pay.repayment.i18n('foss.stl.pay.common.alert'),pay.repayment.i18n('foss.stl.pay.queryBillRepayment.unSelectedOneDisableRecordWarning'));
		return false;
	}
	
	var selectBillRepayNos = [];
	for(var i=0;i<selections.length;i++){
		
		// 无效或红单的还款单不能审核
		if(selections[i].get('isRedBack')==pay.repayment.SETTLEMENT__IS_RED_BACK__YES){
			Ext.Msg.alert(pay.repayment.i18n('foss.stl.pay.common.alert'),pay.repayment.i18n('foss.stl.pay.queryBillRepayment.currentRepayment')+selections[i].get('repaymentNo')+pay.repayment.i18n('foss.stl.pay.queryBillRepayment.isWriteoffRedBackCannotDisable'));
			return false;
		}
		//反核消生成的还款单不可以作废
		if(selections[i].get('amount')<=0){
			Ext.Msg.alert(pay.repayment.i18n('foss.stl.pay.common.alert'),pay.repayment.i18n('foss.stl.pay.queryBillRepayment.repayment')+selections[i].get('repaymentNo')+pay.repayment.i18n('foss.stl.pay.queryBillRepayment.reWriteoffRepaymentCannotDistable'));
			return false;
		}
		if(selections[i].get('active')==pay.repayment.INACTIVE){
			Ext.Msg.alert(pay.repayment.i18n('foss.stl.pay.common.alert'),pay.repayment.i18n('foss.stl.pay.queryBillRepayment.repayment')+selections[i].get('repaymentNo')+pay.repayment.i18n('foss.stl.pay.queryBillRepayment.repaymentHasBeenDisabledReselectAgain'));
			return false;
		}
		
		//执行过反核消的还款单不可以反审核
		if(selections[i].get('bverifyAmount')>0){
			Ext.Msg.alert(pay.repayment.i18n('foss.stl.pay.common.alert'),pay.repayment.i18n('foss.stl.pay.queryBillRepayment.reWriteoffRepaymentCannotDistable'));
			return false;
		}
		
		selectBillRepayNos.push(selections[i].get('repaymentNo'));
	}
	
	var yesFn=function(){
		
		
		me.disable(false);
		//10秒后自动解除灰掉效果
		setTimeout(function() {
		me.enable(true);
		}, 10000);
		
		//调用
		Ext.Ajax.request({

			url:pay.realPath('disabledBillRepayment.action'),
			params:{
				'billRepaymentManageVo.billRepaymentManageDto.selectBillRepayNos':selectBillRepayNos
			},
			success:function(response){
				//获取查询还款单grid
				var queryGrid = Ext.getCmp('T_pay-manageBillRepayment_content').getQueryGrid();
				//查询后台
				queryGrid.store.loadPage(1,{
				callback: function(records, operation, success) {
				var rawData = queryGrid.store.proxy.reader.rawData;
				//当success:false ,isException:false  --业务异常
						if(!success && ! rawData.isException){
							Ext.Msg.alert(pay.repayment.i18n('foss.stl.pay.common.alert'),rawData.message);
							return false;
						}
						if(success){
						 var result =   Ext.decode(operation.response.responseText); 
						 toolBar = queryGrid.getDockedItems('toolbar[dock="bottom"]')[0];
						 var totalAmount=pay.repayment.convertAmount(result.billRepaymentManageResultVo.billRepaymentManageResultDto.totalAmount);
						 var tatalTrueAmount=pay.repayment.convertAmount(result.billRepaymentManageResultVo.billRepaymentManageResultDto.totalTrueAmount);
						 var totalBVerifyAmount=pay.repayment.convertAmount(result.billRepaymentManageResultVo.billRepaymentManageResultDto.totalBVerifyAmount);
								
						 toolBar.getComponent(1).setValue(result.billRepaymentManageResultVo.billRepaymentManageResultDto.totalCount);
						 toolBar.getComponent(2).setValue(totalAmount);
						 toolBar.getComponent(3).setValue(tatalTrueAmount);
						 toolBar.getComponent(4).setValue(totalBVerifyAmount);
					}
				}
			});
			//查询后显示隐藏组件
			queryGrid.show();
			Ext.Msg.alert(pay.repayment.i18n('foss.stl.pay.common.alert'),pay.repayment.i18n('foss.stl.pay.queryBillRepayment.disableRepaymentSuccess'));
			},
			exception:function(response){
				var result = Ext.decode(response.responseText);	
				Ext.Msg.alert(pay.repayment.i18n('foss.stl.pay.common.alert'),result.message);
				
				//获取查询还款单grid
				var queryGrid = Ext.getCmp('T_pay-manageBillRepayment_content').getQueryGrid();
				
				//查询后台
				queryGrid.store.loadPage(1,{
				callback: function(records, operation, success) {
				var rawData = queryGrid.store.proxy.reader.rawData;
				//当success:false ,isException:false  --业务异常
						if(!success && ! rawData.isException){
							Ext.Msg.alert(pay.repayment.i18n('foss.stl.pay.common.alert'),rawData.message);
							return false;
						}
						if(success){
						 var result =   Ext.decode(operation.response.responseText); 
						 toolBar = queryGrid.getDockedItems('toolbar[dock="bottom"]')[0];
						 var totalAmount=pay.repayment.convertAmount(result.billRepaymentManageResultVo.billRepaymentManageResultDto.totalAmount);
						 var tatalTrueAmount=pay.repayment.convertAmount(result.billRepaymentManageResultVo.billRepaymentManageResultDto.totalTrueAmount);
						 var totalBVerifyAmount=pay.repayment.convertAmount(result.billRepaymentManageResultVo.billRepaymentManageResultDto.totalBVerifyAmount);
								
						 toolBar.getComponent(1).setValue(result.billRepaymentManageResultVo.billRepaymentManageResultDto.totalCount);
						 toolBar.getComponent(2).setValue(totalAmount);
						 toolBar.getComponent(3).setValue(tatalTrueAmount);
						 toolBar.getComponent(4).setValue(totalBVerifyAmount);
					}
				}
				});
			}
		});	
	};
	var noFn=function(){
	 	return false;
	};
	pay.billRepaymentConfirmAlert(pay.repayment.i18n('foss.stl.pay.queryBillRepayment.isConfimDisabled'),yesFn,noFn);
}



//还款单明细
pay.repayment.detailRepayment = function(grid, rowIndex, colIndex){

	var yesFn=function(){
		//获取选中的还款单数据
		var selection = grid.getStore().getAt(rowIndex);	
		
		//提交后台生产对账单回执，并返回回执数据
		Ext.Ajax.request({
			url:pay.realPath('detailBillRepayment.action'),
			params:{
				'billRepaymentManageVo.billRepaymentManageDto.selectBillRepayNos':selection.data.repaymentNo
			},
			method:'post',
			success:function(response){
				var result = Ext.decode(response.responseText);	
				
				//获取弹出窗口
				win = Ext.getCmp('Foss_Repaymentbill_WriteoffDetailWindow_ID');
				if(win==null ){
					win = Ext.create('Foss.Repaymentbill.WriteoffDetailWindow');
				}
				//加载数据
				win.items.items[0].store.loadData(result.billRepaymentManageResultVo.billRepaymentManageResultDto.billWriteoffEntityList);
				
				toolBar = win.getWriteoffRepayGrid().getDockedItems('toolbar[dock="bottom"]')[0];
				//核销明细总条数
				toolBar.getComponent(0).setValue(result.billRepaymentManageResultVo.billRepaymentManageResultDto.totalWriteoffCount);
				//核销明细总金额
				toolBar.getComponent(2).setValue(pay.repayment.convertAmount(result.billRepaymentManageResultVo.billRepaymentManageResultDto.totalWriteoffAmount));
				win.show();
			},
			exception:function(response){
				var result = Ext.decode(response.responseText);	
				Ext.Msg.alert(pay.repayment.i18n('foss.stl.pay.common.alert'),result.message);
			}		
		});
	};
	var noFn=function(){
	 	return false;
	};
	pay.billRepaymentConfirmAlert(pay.repayment.i18n('foss.stl.pay.queryBillRepayment.isConfimToDetails'),yesFn,noFn);
}





//--------------------------------------------------------qiaolifeng---------------------------------------------------

//grid的model
Ext.define('Foss.stlbillrepayment.queryRepaymentModel',{
	extend:'Ext.data.Model',
	idgen: 'uuid',//EXT在前台为每个模型额外以UUID方式生成主键
	idProperty : 'extid',//以上生成的主键用在名叫“extid”的列 
	fields:[{
		//额外的用于生成的EXT使用的列 
		name: 'extid'
	},{
		name:'ID'
	},{
		//还款单号
		name:'repaymentNo'
	},{
		//客户编码
		name:'customerCode'
	},{
		//客户名称
		name:'customerName'
	},{
		//金额
		name:'amount',
		type:'long'
	},{
		//实际金额
		name:'trueAmount',
		type:'long'
	},{
		//反审核金额		
		name:'bverifyAmount',
		type:'long'
	},{
		//审核状态
		name:'auditStatus'
	},{
		//收款部门名称
		name:'collectionOrgName'
	},{
		//单据状态
		name:'status'
	},{
		//来源单号
		name:'sourceBillNo'
	},{
		//支付方式
		name:'paymentType'
	},{
		//是否有效
		name:'active'
	},{
		//业务日期
		name:'businessDate',
		type:'date',
		convert:stl.longToDateConvert
	},{
		//记账日期
		name:'accountDate',
		type:'date',
		convert:stl.longToDateConvert
	},{
		//备注
		name:'notes'
	},{//是否红单
		name:'isRedBack'
	},{//对账单号
		name:'statementBillNo'
	},{
		name:'largeArea'//大区
	},{
		name:'smallArea'//小区
	},{
		//币种		
		name:'currencyCode'
	},{
		//在线支付编号		
		name:'onlinePaymentNo'
	},{
		//oa占领编号			
		name:'oaPaymentNo'
	},{
		//oa汇款名称			
		name:'oaPayee'
	},{
		name:'createOrgCode'
	},{
		name:'createOrgName'
	},{
		name:'auditUserCode'
	},{
		name:'auditUserName'
	},{
		name:'modifyUserCode'
	},{
		//修改用户名称		
		name:'modifyUserName'
	},{
		//收银确认用户编码		
		name:'cashConfirmUserCode'
	},{
		//收银确认用户		
		name:'cashConfirmUserName'
	},{
		name:'createTime',
		type:'date',
		convert:stl.longToDateConvert
	},{
		//修改时间		
		name:'modifyTime',
		type:'date',
		convert:stl.longToDateConvert
	},{
		//收银确认时间		
		name:'cashConfirmTime',
		type:'date',
		convert:stl.longToDateConvert
	},{
		//生成方式		
		name:'createType'
	},{
		//是否初始化		
		name:'isInit'
	},{
		name:'waybillNo'
	},{
		name:'sourceBillType'
	},{
		name:'versionNo'
	},{
		name:'auditUserCode'
	},{
		name:'auditUserName'
	},{
		name:'modifyUserCode'
	},{
		name:'modifyUserName'
	},{
		//收银确认人工号		
		name:'cashConfirmUserCode'
	},{
		name:'cashConfirmUserName'
	},{
		//创建部门		
		name:'createOrgCode'
	},{
		name:'createOrgName'
	},{
		name:'createTime',
		type:'date',
		convert:stl.longToDateConvert
	},{
		name:'modifyTime',
		type:'date',
		convert:stl.longToDateConvert
	},{
		name:'auditStatus'
	},{
		name:'status'
	},{
		//收款公司编码		
		name:'collectionCompanyCode'
	},{
		//收款公司名称
		name:'collectionCompanyName'
	},{
		//收入部门编码		
		name:'generatingOrgCode'
	},{
		//收入部门名称		
		name:'generatingOrgName'
	},{
		//收入公司编码		
		name:'generatingCompanyCode'
	},{
		//收入公司名称		
		name:'generatingCompanyName'
	},{
		//确认收入日期		
		name:'conrevenDate',
		type:'date',
		convert:stl.longToDateConvert
	},{
		name:'notes'
	},{
		//POS串号
		name:'posSerialNum'
	},{
		//银行交易流水号
		name:'batchNo'
	}]
});






//客户类型：Model
Ext.define('Foss.pay.repayment.CustomerTypeModel',{
	extend:'Ext.data.Model',
	fields:[{
		name:'customerTypeCode'
	},{
		name:'customerTypeName'
	}]
	
});


//客户类型：store
Ext.define('Foss.pay.repayment.CustomerTypeStore',{
	extend:'Ext.data.Store',
	model:'Foss.pay.repayment.CustomerTypeModel',
	data:{
		'items':[
			{customerTypeCode:'01',customerTypeName:pay.repayment.i18n('foss.stl.pay.queryBillRepayment.customer')},
			{customerTypeCode:'02',customerTypeName:pay.repayment.i18n('foss.stl.pay.queryBillRepayment.airAgeny')},
			{customerTypeCode:'03',customerTypeName:pay.repayment.i18n('foss.stl.pay.queryBillRepayment.vehAgency')},
			{customerTypeCode:'04',customerTypeName:pay.repayment.i18n('foss.stl.pay.queryBillRepayment.airline')},
			{customerTypeCode:'05',customerTypeName:pay.repayment.i18n('foss.stl.consumer.common.landStowage')}
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





//还款单查询store
Ext.define('Foss.stlbillrepayment.queryRepaymentStore',{
	extend:'Ext.data.Store',
	pageSize : pay.repayment.STLREVERSEWRITEOFF_ONEPAGESIZE ,
	model:'Foss.stlbillrepayment.queryRepaymentModel',
	sorters: [{
	     property: 'businessDate',
	     direction: 'ASC'
	 }],
	proxy:{
		type:'ajax',
		url:pay.realPath('queryBillRepayment.action'),
		actionMethods:'post',
		reader:{
			type:'json',
			root:'billRepaymentManageResultVo.billRepaymentManageResultDto.billRepaymentQueryList',
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





//还款单按日期查询 Form
Ext.define('Foss.repayment.StlBillRepaymentQueryByDateFrom',{
	extend:'Ext.form.Panel',
	frame:false,
	columnWidth:1,
	height:260,
	width:700,
	defaults:{
		margin :'5 5 5 5',
		labelWidth :85,
		labelSeparator:':'
	},
	getQueryType:function(){
		return pay.repayment.queryRepayment_TD='TD';
	},
	defaultType:'textfield',
	layout:'column',
	 items:[{
	    	xtype      : 'fieldcontainer', 
			defaultType: 'radiofield',
			columnWidth:1,
			layout:'hbox',
			items: [{ 
					boxLabel  : pay.repayment.i18n('foss.stl.pay.queryBillRepayment.queryByBusinessDate'),
					checked   : true,
					name      : 'selectDateFlag', 
					inputValue: '1',// 表单的参数值
					id        : 'Foss_stlbillrepayment_StlBillRepaymentQueryInfoTab_businessDateFlagId',
					width:150
				},{ 
					boxLabel  : pay.repayment.i18n('foss.stl.pay.queryBillRepayment.queryByAccountDate'), 
					name      : 'selectDateFlag', 
					inputValue: '2',// 表单的参数值
					id        : 'Foss_stlbillrepayment_StlBillRepaymentQueryInfoTab_accountDateFlagId' ,
					width:150
			   },{
				   	xtype:'textfield',
       				fieldLabel:'<span style="color:red;">可以通过还款单明细查看运单号</span>',
       				readOnly:true,
       				name:'notess',
       				labelWidth:200
			   }]
	    },
	    {
	    	xtype:'datefield',
	    	columnWidth:0.33,
	    	name:'startDate',
	    	fieldLabel:pay.repayment.i18n('foss.stl.pay.queryBillRepayment.startDate'),
	    	value: stl.getTargetDate(new Date(),-pay.repayment.STLBILLREPAYMENT_ONEMONTH),
	    	format:'Y-m-d',
	    	allowBlank:false
	    }
	    ,{
	    	xtype:'datefield',
	   	 	name:'endDate',
	   	 	columnWidth:0.33,
	    	fieldLabel:pay.repayment.i18n('foss.stl.pay.queryBillRepayment.endDate'),
	    	format:'Y-m-d',
	    	value:new Date(),
	    	allowBlank:false,
	    	maxValue:new Date()
	    },{
	    	xtype: 'combobox',
			name:'auditStatus',
			columnWidth:0.33,
	        fieldLabel:pay.repayment.i18n('foss.stl.pay.queryBillRepayment.auduitStatus'),
			store:FossDataDictionary.getDataDictionaryStore(settlementDict.BILL_PAYABLE__APPROVE_STATUS,null,{
				 'valueCode': '',
          		 'valueName': pay.repayment.i18n('foss.stl.pay.queryBillRepayment.all')
			}),
			queryModel:'local',
			editable:false,
			displayField:'valueName',
			valueField:'valueCode',
			value:''
	    }
	    ,{
			xtype:'linkagecomboselector',
			eventType : ['focus'],// 一般callparent包含focus事件，如果有callparent,则focus事件可不用传递
			itemId : 'Foss_baseinfo_BigRegion_ID',
			store : Ext.create('Foss.baseinfo.commonSelector.BigRegionStore'),// 从外面传入
			columnWidth:.33,
			fieldLabel : pay.repayment.i18n('foss.stl.pay.queryBillRepayment.largeRegionSearch'),
			name : 'largeAreaName',
			isPaging: true,
			allowBlank : true,
			value:'',
			minChars : 0,
			displayField : 'name',// 显示名称
			valueField : 'code',
			minChars : 0,
			queryParam : 'commonOrgVo.name'
		},
		{
			xtype:'linkagecomboselector',
			itemId : 'Foss_baseinfo_SmallRegion_ID',
			eventType : ['callparent'],// 一般callparent包含focus事件，如果有callparent,则focus事件可不用传递
			store : Ext.create('Foss.baseinfo.commonSelector.SmallRegionStore'),// 从外面传入
			columnWidth:.33,
			fieldLabel : pay.repayment.i18n('foss.stl.pay.queryBillRepayment.smallReginSearch'),
			name : 'smallAreaName',
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
		},
		{
			xtype:'linkagecomboselector',
			eventType : ['callparent'],
			store : Ext.create('Foss.baseinfo.commonSelector.SmallRegionStore'),// 从外面传入
			columnWidth:.33,
			fieldLabel : pay.repayment.i18n('foss.stl.pay.queryBillRepayment.repayOrgSearch'),
			name : 'orgName',
			allowBlank : true,
			isPaging: true,
			parentParamsAndItemIds : {
				'commonOrgVo.code' : 'Foss_baseinfo_SmallRegion_ID'
			},// 此处区域不需要传入
			minChars : 0,
			displayField : 'name',// 显示名称
			valueField : 'code',
			minChars : 0,
			queryParam : 'commonOrgVo.name'
		},
	    {
	    	xtype:'combobox',
	    	name:'customerType',
	    	columnWidth:.33,
	    	fieldLabel:pay.repayment.i18n('foss.stl.pay.queryBillRepayment.customerType'),
			store:Ext.create('Foss.pay.repayment.CustomerTypeStore'),
			queryModel:'local',
			value:'01',
			editable:false,
			displayField:'customerTypeName',
			valueField:'customerTypeCode',
	    	listeners:{
				"select": {
					fn: function(_combo, _r){
						var cusValue=_combo.ownerCt.getForm().findField('customerType').getValue();
						if(cusValue=='01'){
							// 客户信息
							Ext.getCmp('pay.repayment.commoncustomerselector').show();
							// 空运代理公司
							Ext.getCmp('pay.repayment.commonairagencycompanyselector').hide();
							// 偏线代理公司
							Ext.getCmp('pay.repayment.commonvehagencycompselector').hide();
							// 航空公司
							Ext.getCmp('pay.repayment.commonairlinesselector').hide();
							Ext.getCmp('pay.repayment.landStowage').hide();
						}else if(cusValue=='02'){
							// 空运代理公司
							Ext.getCmp('pay.repayment.commonairagencycompanyselector').show();
							// 客户信息
							Ext.getCmp('pay.repayment.commoncustomerselector').hide();
							// 偏线代理公司
							Ext.getCmp('pay.repayment.commonvehagencycompselector').hide();
							// 航空公司
							Ext.getCmp('pay.repayment.commonairlinesselector').hide();
							Ext.getCmp('pay.repayment.landStowage').hide();
						}else if(cusValue=='03'){
							// 偏线代理公司
							Ext.getCmp('pay.repayment.commonvehagencycompselector').show();
							// 客户信息
							Ext.getCmp('pay.repayment.commoncustomerselector').hide();
							// 空运代理公司
							Ext.getCmp('pay.repayment.commonairagencycompanyselector').hide();
							// 航空公司
							Ext.getCmp('pay.repayment.commonairlinesselector').hide();
							Ext.getCmp('pay.repayment.landStowage').hide();
						}else if(cusValue=='04'){
							// 航空公司
							Ext.getCmp('pay.repayment.commonairlinesselector').show();
							// 客户信息
							Ext.getCmp('pay.repayment.commoncustomerselector').hide();
							// 偏线代理公司
							Ext.getCmp('pay.repayment.commonvehagencycompselector').hide();
							// 空运代理公司
							Ext.getCmp('pay.repayment.commonairagencycompanyselector').hide();
							Ext.getCmp('pay.repayment.landStowage').hide();
						}else if(cusValue=='05'){
							Ext.getCmp('pay.repayment.landStowage').show()
	                		// 航空公司
							Ext.getCmp('pay.repayment.commonairlinesselector').hide();
							// 客户信息
							Ext.getCmp('pay.repayment.commoncustomerselector').hide();
							// 偏线代理公司
							Ext.getCmp('pay.repayment.commonvehagencycompselector').hide();
							// 空运代理公司
							Ext.getCmp('pay.repayment.commonairagencycompanyselector').hide();
						}
					}
				}
			}
	    }
	    ,
	    {	
	    	xtype:'commoncustomerselector',
	    	listWidth:300,
	    	all:'true',
	    	fieldLabel :pay.repayment.i18n('foss.stl.pay.queryBillRepayment.customerInfo'),
	    	name : 'customerName',
	    	singlePeopleFlag : 'Y',
	    	id:'pay.repayment.commoncustomerselector',
	    	columnWidth:.33,
			isPaging:true // 分页
	    }
	    ,{	
	    	xtype:'commonairagentselector',
	    	all:'true',
	    	fieldLabel : pay.repayment.i18n('foss.stl.pay.queryBillRepayment.airAgenyCompany'),
	    	columnWidth:.33,
	    	name :'customerName',
	    	id:'pay.repayment.commonairagencycompanyselector',
			isPaging:true ,// 分页
			hidden:true
	    },
	    {	
	    	xtype:'commonvehagencycompselector',
	    	all:'true',
	    	fieldLabel :pay.repayment.i18n('foss.stl.pay.queryBillRepayment.vehAgencyCompany'),
	    	columnWidth:.33,
	    	name :'customerName',
	    	id:'pay.repayment.commonvehagencycompselector',
			isPaging:true ,// 分页
			hidden:true
	    },{
			xtype:'commonairlinesselector',
			all:'true',
	    	fieldLabel :pay.repayment.i18n('foss.stl.pay.queryBillRepayment.airlineCompany'),
	    	columnWidth:.33,
	    	name : 'customerName',
	    	id:'pay.repayment.commonairlinesselector',
			isPaging:true ,// 分页
			hidden:true
	    },{
			xtype:'commonLdpAgencyCompanySelector',
			fieldLabel:pay.repayment.i18n('foss.stl.consumer.common.landStowage'),
			name:'customerName',
			id:'pay.repayment.landStowage',
			allowBlank:true,
			columnWidth:.33,
			listWidth:300,//设置下拉框宽度
			hidden:true,
			isPaging:true //分页		
		 },{
	    	xtype: 'combobox',
			name:'repayWay',
	        fieldLabel:pay.repayment.i18n('foss.stl.pay.queryBillRepayment.paymentType'),
	        columnWidth:.33,
	        editable:false,
	    	store:FossDataDictionary.getDataDictionaryStore(settlementDict.SETTLEMENT__PAYMENT_TYPE,null,{
				 'valueCode': '',
         		 'valueName': pay.repayment.i18n('foss.stl.pay.queryBillRepayment.all')
			},[
				pay.repayment.SETTLEMENT__PAYMENT_TYPE__CASH , // 现金
				pay.repayment.SETTLEMENT__PAYMENT_TYPE__CARD,// 银行卡
				pay.repayment.SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER,// 电汇
				pay.repayment.SETTLEMENT__PAYMENT_TYPE__NOTE,// 支票
				pay.repayment.SETTLEMENT__PAYMENT_TYPE__ONLINE,//网上支付
				pay.repayment.CLAIMSWAY_CLAIMS_OUT //理赔出库
			]),
			queryModel:'local',
			displayField:'valueName',
			valueField:'valueCode',
			value:''
	    },{
	    	xtype: 'combobox',
			name:'isActive',
			editable:false,
	        fieldLabel:pay.repayment.i18n('foss.stl.pay.queryBillRepayment.isActive'),
	        columnWidth:.33,
			store:FossDataDictionary.getDataDictionaryStore(settlementDict.FOSS_ACTIVE,null,{
				 'valueCode': '',
         		 'valueName': pay.repayment.i18n('foss.stl.pay.queryBillRepayment.all')
			}),
			queryModel:'local',
			displayField:'valueName',
			valueField:'valueCode',
			value: 'Y'
	    },{
	    	xtype: 'combobox',
			name:'isInit',
			editable:false,
	        fieldLabel:pay.repayment.i18n('foss.stl.pay.queryBillRepayment.isInit'),
	        columnWidth:.33,
			store:FossDataDictionary.getDataDictionaryStore(settlementDict.FOSS_BOOLEAN,null,{
				 'valueCode': '',
         		 'valueName': pay.repayment.i18n('foss.stl.pay.queryBillRepayment.all')
			}),
			queryModel:'local',
			displayField:'valueName',
			valueField:'valueCode',
			value: pay.repayment.i18n('foss.stl.pay.queryBillRepayment.all')
	    },{
	    	xtype:'textfield',
	    	name:'posSerialNum',
	    	fieldLabel:pay.repayment.i18n('foss.stl.pay.queryBillRepayment.posSerialNum'),
	    	columnWidth:.33
		}
	    ,{	
			border: 1,
			xtype:'container',
			columnWidth:.99,
			defaultType:'button',
			layout:'column',
			items:[{
				text:pay.repayment.i18n('foss.stl.pay.common.reset'),
				columnWidth:.08,
				handler:pay.repayment.reset
			},{
				xtype: 'container',
				border : false,
				columnWidth:.84,
				html: '&nbsp;'
			},{
				text:pay.repayment.i18n('foss.stl.pay.common.query'),
				cls:'yellow_button',
				columnWidth:.08,
				handler:function(){
					var form=this.up('form');
					var btn = this;
					pay.repayment.queryByRepayment(form,btn,pay.repayment.queryRepayment_TD);
				}
			}]
	    }]		
});




//还款单按运单号查询
//按还款单号查询 Form
Ext.define('Foss.repayment.StlBillRepaymentQueryByRepayNosFrom',{
	extend:'Ext.form.Panel',
	frame:false,
	columnWidth:1,
	height:260,
	tabConfig: {
		width: 120
	},
  layout:'fit',
  getQueryType:function(){
		return pay.repayment.queryRepayment_RT='RT';
	},
  items:[{
  	xtype:'form',
  	defaults:{
      	margin:'5 5 5 5',
      	labelWidth:90
 		 },
  	layout:'column',
  	 items:[{
				xtype: 'container',
				border : false,
				columnWidth:1,
				html: '&nbsp;'
			},{
	    	xtype: 'textarea',
			autoScroll:true,
			regex:/^((HK)?[0-9]{7,10}[;,])*((HK)?[0-9]{7,10}[;,]?)$/i,
			regexText:pay.repayment.i18n('foss.stl.pay.queryBillRepayment.inputHKErrorWarning'),
			emptyText: pay.repayment.i18n('foss.stl.pay.queryBillRepayment.billNoEmptyText'),
          fieldLabel: pay.repayment.i18n('foss.stl.pay.queryBillRepayment.repaymentBillNo'),
          name: 'repaymentNos',
          height : 80,
          allowBlank:false,
          columnWidth:.4
	    },{
	    	border: 1,
			xtype:'container',
			columnWidth:1,
			defaultType:'button',
			layout:'column',
			items:[{
				text:pay.repayment.i18n('foss.stl.pay.common.reset'),
				columnWidth:.08,
				handler:pay.repayment.reset
			},{
				xtype: 'container',
				border : false,
				columnWidth:.24,
				html: '&nbsp;'
			},{
				text:pay.repayment.i18n('foss.stl.pay.common.query'),
				cls:'yellow_button',
				columnWidth:.08,
				handler:function(){
					    var form=this.up('form');
					    var btn = this;
						pay.repayment.queryByRepayment(form,btn,pay.repayment.queryRepayment_RT);
					}
			}]
	    }]
  }]
});





//按对账单号查询还款单 Form
Ext.define('Foss.repayment.StlBillRepaymentQueryByStatementNosFrom',{
	extend:'Ext.form.Panel',
	frame:false,
	columnWidth:1,
	height:260,
	defaults:{
		margin :'10 10 10 10',
		labelWidth :85,
		colspan : 1 
	},
	tabConfig: {
		width: 120
	},
	 getQueryType:function(){
			return pay.repayment.queryRepayment_TDZ='TDZ';
	},
    layout:'fit',
    items:[{
    	xtype:'form',
    	defaults:{
        	margin:'5 5 5 5',
        	labelWidth:90
   		 },
    	layout:'column',
    	 items:[{
				xtype: 'container',
				border : false,
				columnWidth:1,
				html: '&nbsp;'
			},{
		    	xtype: 'textarea',
	 			autoScroll:true,
	 			regex:/^((DZ)?[0-9]{7,10}[;,])*((DZ)?[0-9]{7,10}[;,]?)$/i,
	 			regexText:pay.repayment.i18n('foss.stl.pay.queryBillRepayment.inputDZErrorWarning'),
	 			emptyText: pay.repayment.i18n('foss.stl.pay.queryBillRepayment.billNoEmptyText'),
	            fieldLabel: pay.repayment.i18n('foss.stl.pay.queryBillRepayment.statementillNO'),
	            name:'statementofaccountNos',
	            height : 80,
	            allowBlank:false,
	            columnWidth:.4
		    },{
		    	border: 1,
			xtype:'container',
			columnWidth:1,
			defaultType:'button',
			layout:'column',
			items:[{
				text:pay.repayment.i18n('foss.stl.pay.common.reset'),
				columnWidth:.08,
				handler:pay.repayment.reset
			},{
				xtype: 'container',
				border : false,
				columnWidth:.24,
				html: '&nbsp;'
			},{
				text:pay.repayment.i18n('foss.stl.pay.common.query'),
				cls:'yellow_button',
				columnWidth:.08,
				handler:function (){
						var form=this.up('form');
						var btn = this;
						pay.repayment.queryByRepayment(form,btn,pay.repayment.queryRepayment_TDZ);
					}
			}]
		    }]
    }]
});


//还款单运单查询
//按运单号查询 Form
Ext.define('Foss.repayment.StlBillRepaymentQueryByWaybillNosFrom',{
	extend:'Ext.form.Panel',
	frame:false,
	columnWidth:1,
	height:260,
	 getQueryType:function(){
			return pay.repayment.queryRepayment_WB='WB';
	},
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
	tabConfig: {
		width: 120
	},
    layout:'fit',
    items:[{
    	xtype:'form',
    	defaults:{
        	margin:'5 5 5 5',
        	labelWidth:90
   		 },
    	layout:'column',
    	 items:[{
				xtype: 'container',
				border : false,
				columnWidth:1,
				html: '&nbsp;'
			},{
		    	xtype: 'textarea',
	 			autoScroll:true,
	 			regex:/^\s*\d{8,}([\.\,\s。，；\;]+\d{8,}){0,9}\s*$/,
	 			regexText:'请输入1-10个单号，以标点符号隔开',
	 			emptyText: pay.repayment.i18n('foss.stl.pay.queryBillRepayment.WaybillNoEmptyText'),
	            fieldLabel:pay.repayment.i18n('foss.stl.pay.queryBillRepayment.waybillNo') ,
	            name: 'waybillNos',
	            height : 80,
	            allowBlank:false,
	            columnWidth:.4
		    },{
		    	border: 1,
			xtype:'container',
			columnWidth:1,
			defaultType:'button',
			layout:'column',
			items:[{
				text:pay.repayment.i18n('foss.stl.pay.common.reset'),
				columnWidth:.08,
				handler:pay.repayment.reset
			},{
				xtype: 'container',
				border : false,
				columnWidth:.24,
				html: '&nbsp;'
			},{
				text:pay.repayment.i18n('foss.stl.pay.common.query'),
				cls:'yellow_button',
				columnWidth:.08,
				handler:function () {
						var form=this.up('form');
						var btn = this;
						pay.repayment.queryByRepayment(form,btn,pay.repayment.queryRepayment_WB);
					}
			}]
		    }]
    }]
});


//按银联交易流水号查询
Ext.define('Foss.repayment.StlBillRepaymentQueryByBatchNosFrom',{
	extend:'Ext.form.Panel',
	frame:false,
	columnWidth:1,
	height:260,
	getQueryType:function(){
		return pay.repayment.queryRepayment_BB;
	},
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
	tabConfig: {
		width: 120
	},
    layout:'fit',
    items:[{
    	xtype:'form',
    	defaults:{
        	margin:'5 5 5 5',
        	labelWidth:90
   		 },
    	layout:'column',
    	 items:[{
				xtype: 'container',
				border : false,
				columnWidth:1,
				html: '&nbsp;'
			},{
		    	xtype: 'textarea',
	 			autoScroll:true,
	 			emptyText: pay.repayment.i18n('foss.stl.pay.queryBillRepayment.billNoEmptyText'),
	            fieldLabel:pay.repayment.i18n('foss.stl.pay.queryBillRepayment.batchNo') ,
	            name: 'batchBillNos',
	            height : 80,
	            allowBlank:false,
	            columnWidth:.4
		    },{
		    	border: 1,
			xtype:'container',
			columnWidth:1,
			defaultType:'button',
			layout:'column',
			items:[{
				text:pay.repayment.i18n('foss.stl.pay.common.reset'),
				columnWidth:.08,
				handler:pay.repayment.reset
			},{
				xtype: 'container',
				border : false,
				columnWidth:.24,
				html: '&nbsp;'
			},{
				text:pay.repayment.i18n('foss.stl.pay.common.query'),
				cls:'yellow_button',
				columnWidth:.08,
				handler:function () {
					var form=this.up('form');
					var btn = this;
					pay.repayment.queryByRepayment(form,btn,pay.repayment.queryRepayment_BB);
				}
			}]
		 }]
    }]
});




//按来源单号查询
Ext.define('Foss.repayment.StlBillRepaymentQueryBySourceBillNoFrom',{
	extend:'Ext.form.Panel',
	frame:false,
	columnWidth:1,
	height:260,
	 getQueryType:function(){
			return pay.repayment.queryRepayment_TSB='TSB';
	},
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
	tabConfig: {
		width: 120
	},
    layout:'fit',
    items:[{
    	xtype:'form',
    	defaults:{
        	margin:'5 5 5 5',
        	labelWidth:90
   		 },
    	layout:'column',
    	 items:[{
				xtype: 'container',
				border : false,
				columnWidth:1,
				html: '&nbsp;'
			},{
		    	xtype: 'textarea',
	 			autoScroll:true,
//	 			regex:/^((YD)?[0-9]{7,10}[;,])*((YD)?[0-9]{7,10}[;,]?)$/i,
//	 			regexText:'输入以YD开头加数字或只输入数字，必须是7-10位数字以， 或；隔开',
	 			emptyText: pay.repayment.i18n('foss.stl.pay.queryBillRepayment.billNoEmptyText'),
	            fieldLabel: pay.repayment.i18n('foss.stl.pay.queryBillRepayment.sourceBillNo'),
	            name: 'sourceBillNo',
	            height : 80,
	            allowBlank:false,
	            columnWidth:.4
		    },{
		    	xtype:'container',
				columnWidth:.3,
				margin:'10 0 0 10 ',
				items:[{
					xtype:'component',
					padding:'0 0 0 10',
					autoEl:{
						tag:'div',
						html:'<span style="color:red;">'+pay.repayment.i18n('foss.stl.pay.queryBillRepayment.billNosNots')+'</span>'
					}
				}]
		    },{
		    border: 1,
			xtype:'container',
			columnWidth:1,
			defaultType:'button',
			layout:'column',
			items:[{
				text:pay.repayment.i18n('foss.stl.pay.common.reset'),
				columnWidth:.08,
				handler:pay.repayment.reset
			},{
				xtype: 'container',
				border : false,
				columnWidth:.24,
				html: '&nbsp;'
			},{
				text:pay.repayment.i18n('foss.stl.pay.common.query'),
				cls:'yellow_button',
				columnWidth:.08,
				handler:function () {
						var form=this.up('form');
						var btn = this;
						pay.repayment.queryByRepayment(form,btn,pay.repayment.queryRepayment_TSB);
					}
			}]
		    }]
    }]
});





//查询tab
Ext.define('Foss.stlbillrepayment.StlBillRepaymentQueryInfoTab',{
	extend:'Ext.tab.Panel',
	frame:false,
	bodyCls: 'autoHeight',
	cls: 'innerTabPanel',
	activeTab: 0,
	items : [ {
		title: pay.repayment.i18n('foss.stl.pay.queryBillRepayment.queryByRepaymentDate'),//按日期查询
		tabConfig: {
			width: 120
		},
		width: '200',
        items:[
               Ext.create('Foss.repayment.StlBillRepaymentQueryByDateFrom')
               ]
	},{
		title: pay.repayment.i18n('foss.stl.pay.queryBillRepayment.queryByRepaymentBillNo'),//按还款单号查询
		tabConfig: {
			width: 120
		},
        layout:'fit',
        items:[
               Ext.create('Foss.repayment.StlBillRepaymentQueryByRepayNosFrom')
               ]
	},{
		title:pay.repayment.i18n('foss.stl.pay.queryBillRepayment.queryByStatementillNO'),//对账单号查询
		tabConfig: {
			width: 120
		},
        layout:'fit',
        items:[
               Ext.create('Foss.repayment.StlBillRepaymentQueryByStatementNosFrom')
               ]
	},{
		title: pay.repayment.i18n('foss.stl.pay.queryBillRepayment.queryByWaybillNo'),//运单号查询
		tabConfig: {
			width: 120
		},
        layout:'fit',
        items:[
               Ext.create('Foss.repayment.StlBillRepaymentQueryByWaybillNosFrom')
              ]
	},{
		title: pay.repayment.i18n('foss.stl.pay.queryBillRepayment.queryBySourceBillNo'),//来源单号
		tabConfig: {
			width: 120
		},
        layout:'fit',
        items:[
               Ext.create('Foss.repayment.StlBillRepaymentQueryBySourceBillNoFrom')
              ]
	},{
		title: pay.repayment.i18n('foss.stl.pay.queryBillRepayment.queryByatchNo'),//银联交易流水号
		tabConfig: {
			width: 140
		},
        layout:'fit',
        items:[
           Ext.create('Foss.repayment.StlBillRepaymentQueryByBatchNosFrom')
        ]
	}]
});






//还款单Grid
Ext.define('Foss.stlbillrepayment.StlBillRepaymentQueryInfoGrid',{
	extend:'Ext.grid.Panel',
    title: pay.repayment.i18n('foss.stl.pay.queryBillRepayment.repaymentInfo'),
    emptyText:pay.repayment.i18n('foss.stl.pay.common.noResult'),
    frame:true,
	height:500,
	//width : 1060,
	selModel:Ext.create('Ext.selection.CheckboxModel'),
	hidMeParams:null,
	setHidMeParams:function(params){
		this.hidMeParams=params;
	},
	getHidMeParams:function(){
		return this.hidMeParams;
	},
	bverifyTotalAmount:null,//反核销总金额
	getBverifyTotalAmount:function(){
		var me=this;
		if(Ext.isEmpty(me.bverifyTotalAmount)){
			me.bverifyTotalAmount=0;
		}
		return me.bverifyTotalAmount;
	},
	totalAmount:null,//总金额
	getTotalAmount:function(){
		var me=this;
		if(Ext.isEmpty(me.totalAmount)){
			me.totalAmount=0;
		}
		return me.totalAmount;
	},
	trueTotalAmount:null,//实际还款金额
	getTrueTotalAmount:function(){
		var me=this;
		if(Ext.isEmpty(me.trueTotalAmount)){
			me.trueTotalAmount=0;
		}
		return me.trueTotalAmount;
	},
	totalRows:null,//总条数
	getTotalRows:function(){
		var me=this;
		if(Ext.isEmpty(me.totalRows)){
			me.totalRows=0;
		}
		return me.totalRows;
	},
	
	columns:[{
    	xtype:'actioncolumn',
    	header:pay.repayment.i18n('foss.stl.pay.queryBillRepayment.actionColumn'),
    	width:90,
    	align: 'center',
    	items:[{
			iconCls : 'foss_icons_stl_auditing',
			tooltip:pay.repayment.i18n('foss.stl.pay.common.audit'),
			handler:function(grid, rowIndex, colIndex){
				
				pay.repayment.aduitRepayment(grid, rowIndex, colIndex)
			},
			getClass:function(v,m,r,rowIndex){
				if(r.get('auditStatus')==pay.repayment.BILL_PAYMENT__AUDIT_STATUS__AUDIT_AGREE
						||r.get('amount')<=0
						||r.get('bverifyAmount')>0
						||r.get('active')==pay.repayment.INACTIVE
						||r.get('isRedBack')==pay.repayment.SETTLEMENT__IS_RED_BACK__YES
						||!pay.repayment.isPermission('/stl-web/pay/aduitBillRepayment.action')){
					return 'statementBill_hide';
				}else {
					return 'foss_icons_stl_auditing';
				}
			}
	},{
		iconCls : 'foss_icons_stl_fauditing',
		tooltip:pay.repayment.i18n('foss.stl.pay.common.unAudit'),
		getClass:function(v,m,r,rowIndex){
			if(r.get('auditStatus')==pay.repayment.BILL_PAYMENT__AUDIT_STATUS__NOT_AUDIT
					||r.get('amount')<=0
					||r.get('bverifyAmount')>0
					||r.get('active')==pay.repayment.INACTIVE
					||r.get('isRedBack')==pay.repayment.SETTLEMENT__IS_RED_BACK__YES
					||!pay.repayment.isPermission('/stl-web/pay/revAduitBillRepayment.action')){
				return 'statementBill_hide';
			}else {
				return 'foss_icons_stl_fauditing';
			}
		},
		handler:function(grid, rowIndex, colIndex){
			
			pay.repayment.revAduitRepayment(grid, rowIndex, colIndex)
		}
	},{
		iconCls : 'deppon_icons_delete',
		tooltip:pay.repayment.i18n('foss.stl.pay.common.disabled'),
		getClass:function(v,m,r,rowIndex){
			if(r.get('amount')<=0
					||r.get('bverifyAmount')>0
					||r.get('active')==pay.repayment.INACTIVE
					||r.get('isRedBack')==pay.repayment.SETTLEMENT__IS_RED_BACK__YES
					||!pay.repayment.isPermission('/stl-web/pay/disabledBillRepayment.action')){
				return 'statementBill_hide';
			}else{
				return 'deppon_icons_delete';
			}
		},
		handler:function(grid, rowIndex, colIndex){
		
			pay.repayment.disabledRepayment(grid, rowIndex, colIndex)
		}
	},{
		iconCls : 'deppon_icons_cancel',
		tooltip:pay.repayment.i18n('foss.stl.pay.queryBillRepayment.applyForDisable'),
		getClass:function(v,m,r,rowIndex){
			if(r.get('amount')<=0
					||r.get('auditStatus') != 'NA'
					||r.get('active')==pay.repayment.INACTIVE
					||r.get('isRedBack')==pay.repayment.SETTLEMENT__IS_RED_BACK__YES
					||r.get('createType')== 'A'
					||!pay.repayment.isPermission('/stl-web/pay/disableBillRepaymentApply.action')){
				return 'statementBill_hide';
			}else{
				return 'deppon_icons_cancel';
			}
		},
		handler:function(grid, rowIndex, colIndex){
		
			pay.repayment.disabledRepaymentApply(grid, rowIndex, colIndex)
		}
	},{
		iconCls : 'deppon_icons_showdetail',
		tooltip:pay.repayment.i18n('foss.stl.pay.queryBillRepayment.entry'),
		handler:function(grid, rowIndex, colIndex){
			pay.repayment.detailRepayment(grid, rowIndex, colIndex)
		}
	}]
    },
	{
		header: pay.repayment.i18n('foss.stl.pay.queryBillRepayment.repaymentNo'), 
		dataIndex: 'repaymentNo'
	},
	{
		header:pay.repayment.i18n('foss.stl.pay.queryBillRepayment.customerCode'),
		dataIndex: 'customerCode'
	},
	{
		header:pay.repayment.i18n('foss.stl.pay.queryBillRepayment.customerName'),
		dataIndex: 'customerName'
	},
	{
		header:pay.repayment.i18n('foss.stl.pay.queryBillRepayment.largeRegionName'), 
		dataIndex: 'largeArea'
	},{
		header:pay.repayment.i18n('foss.stl.pay.queryBillRepayment.smallReginName'), 
		dataIndex: 'smallArea'
	},
	{
		header: pay.repayment.i18n('foss.stl.pay.queryBillRepayment.collectionOrgName'), 
		dataIndex: 'collectionOrgName'
	},{
		header: pay.repayment.i18n('foss.stl.pay.queryBillRepayment.paymentType'), 
		dataIndex: 'paymentType',
		renderer:function(value){
    		var displayField = FossDataDictionary. rendererSubmitToDisplay (value,settlementDict.SETTLEMENT__PAYMENT_TYPE);
    		return displayField;
    	}
	},{
		header:pay.repayment.i18n('foss.stl.pay.queryBillRepayment.posSerialNum'), 
		dataIndex: 'posSerialNum',
		align: 'right'
	},{
		header:pay.repayment.i18n('foss.stl.pay.queryBillRepayment.batchNo'), 
		dataIndex: 'batchNo',
		align: 'right'
	},{
		header:pay.repayment.i18n('foss.stl.pay.queryBillRepayment.amount'), 
		dataIndex: 'amount',
		align: 'right',  
		renderer:stl.amountFormat
	},{
		header: pay.repayment.i18n('foss.stl.pay.queryBillRepayment.trueAmount'), 
		dataIndex: 'trueAmount',
		align: 'right',  
		renderer:stl.amountFormat
	},{
		header: pay.repayment.i18n('foss.stl.pay.queryBillRepayment.bverifyAmount'),
		dataIndex: 'bverifyAmount',
		align: 'right',  
		renderer:stl.amountFormat
	}/*,{
		header:'来源单号',
		dataIndex:'sourceBillNo'
	},*/,{
		header: pay.repayment.i18n('foss.stl.pay.queryBillRepayment.isRedBack'), 
		dataIndex: 'isRedBack',
	  	renderer:function(value){
    		var displayField = FossDataDictionary. rendererSubmitToDisplay (value,settlementDict.SETTLEMENT__IS_RED_BACK);
    		return displayField;
    	}
	}
	,{
		header: pay.repayment.i18n('foss.stl.pay.queryBillRepayment.auditStatus'), 
		dataIndex: 'auditStatus',
	  	renderer:function(value){
    		var displayField = FossDataDictionary. rendererSubmitToDisplay (value,settlementDict.BILL_PAYABLE__APPROVE_STATUS);
    		return displayField;
    	}
	},{
		header: pay.repayment.i18n('foss.stl.pay.queryBillRepayment.active'), 
		dataIndex: 'active',
	  	renderer:function(value){
    		var displayField = FossDataDictionary. rendererSubmitToDisplay (value,settlementDict.FOSS_ACTIVE);
    		return displayField;
    	}
	},{
		header:pay.repayment.i18n('foss.stl.pay.queryBillRepayment.businessDate'),
		dataIndex: 'businessDate',
		renderer:function(value){
    		return stl.dateFormat(value,'Y-m-d H:i:s');
    	}
	},{
		header: pay.repayment.i18n('foss.stl.pay.queryBillRepayment.accountDate'),
		dataIndex: 'accountDate',
		type:'date',
		renderer:function(value){
    		return stl.dateFormat(value,'Y-m-d H:i:s');
    	}
	},{
		header: pay.repayment.i18n('foss.stl.pay.queryBillRepayment.notes'),
		dataIndex: 'notes'
	},{
		header: pay.repayment.i18n('foss.stl.pay.queryBillRepayment.isInit'),
		dataIndex: 'isInit',
		hidden:true,
		renderer:function(value){
    		var displayField = FossDataDictionary. rendererSubmitToDisplay (value,settlementDict.FOSS_BOOLEAN);
    		return displayField;
    	}
	},{
		header: pay.repayment.i18n('foss.stl.pay.queryBillRepayment.createType'),
		dataIndex: 'createType',
		hidden:true,
		renderer:function(value){
    		var displayField = FossDataDictionary. rendererSubmitToDisplay (value,settlementDict.SETTLEMENT__CREATE_TYPE);
    		return displayField;
    	}
	},{
		header: pay.repayment.i18n('foss.stl.pay.queryBillRepayment.onlinePaymentNo'),
		dataIndex: 'onlinePaymentNo',
		hidden:true
	},{
		header: pay.repayment.i18n('foss.stl.pay.queryBillRepayment.oaPaymentNo'),
		dataIndex: 'oaPaymentNo',
		hidden:false
	},{
		header: pay.repayment.i18n('foss.stl.pay.queryBillRepayment.oaPayee'),
		dataIndex: 'oaPayee',
		hidden:false	
	},{
		header: pay.repayment.i18n('foss.stl.pay.queryBillRepayment.currencyCode'),
		dataIndex: 'currencyCode',
		hidden:true,
		renderer:function(value){
    		var displayField = FossDataDictionary.rendererSubmitToDisplay (value,settlementDict.SETTLEMENT__CURRENCY_CODE);
    		return displayField;
    	}
	},{
		header: pay.repayment.i18n('foss.stl.pay.queryBillRepayment.sourceBillType'),
		dataIndex: 'sourceBillType',
		hidden:true,
		renderer:function(value){
    		var displayField = FossDataDictionary.rendererSubmitToDisplay(value,'BILL_REPAYMENT__SOURCE_BILL_TYPE');
    		return displayField;
    	}
	},{
		header: pay.repayment.i18n('foss.stl.pay.queryBillRepayment.cashConfirmTime'), 
		dataIndex: 'cashConfirmTime',
		renderer:function(value){
    		if (value != null) {
				return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
			} else {
				return null;
			}
    	},
    	hidden:true
	},{
		header: pay.repayment.i18n('foss.stl.pay.queryBillRepayment.versionNo'), 
		dataIndex: 'versionNo',
    	hidden:true
	},{
		header: pay.repayment.i18n('foss.stl.pay.queryBillRepayment.auditUserCode'), 
		dataIndex: 'auditUserCode',
    	hidden:true
			
	},{
		header: pay.repayment.i18n('foss.stl.pay.queryBillRepayment.auditUserName'), 
		dataIndex: 'auditUserName',
    	hidden:true
	},{
		header: pay.repayment.i18n('foss.stl.pay.queryBillRepayment.modifyUserCode'), 
		dataIndex: 'modifyUserCode',
    	hidden:true
	},{
		header: pay.repayment.i18n('foss.stl.pay.queryBillRepayment.modifyUserName'), 
		dataIndex: 'modifyUserName',
    	hidden:true
	},{
		header: pay.repayment.i18n('foss.stl.pay.queryBillRepayment.cashConfirmUserCode'), 
		dataIndex: 'cashConfirmUserCode',
    	hidden:true
	},{
		header: pay.repayment.i18n('foss.stl.pay.queryBillRepayment.cashConfirmUserName'), 
		dataIndex: 'cashConfirmUserName',
    	hidden:true
	},{
		header: pay.repayment.i18n('foss.stl.pay.queryBillRepayment.createOrgCode'), 
		dataIndex: 'createOrgCode',
    	hidden:true
	},{
		header: pay.repayment.i18n('foss.stl.pay.queryBillRepayment.createOrgName'), 
		dataIndex: 'createOrgName',
    	hidden:true
	},{
		header: pay.repayment.i18n('foss.stl.pay.queryBillRepayment.modifyUserName'), 
		dataIndex: 'modifyUserName',
    	hidden:true
	},{
		header: pay.repayment.i18n('foss.stl.pay.queryBillRepayment.createTime'),
		dataIndex: 'createTime',
		type:'date',
		renderer:function(value){
    		return stl.dateFormat(value,'Y-m-d H:i:s');
    	}
		,hidden:true
	},{
		header: pay.repayment.i18n('foss.stl.pay.queryBillRepayment.modifyTime'),
		dataIndex: 'modifyTime',
		type:'date',
		renderer:function(value){
    		return stl.dateFormat(value,'Y-m-d H:i:s');
    	}
		,hidden:true
	},{
		header: pay.repayment.i18n('foss.stl.pay.queryBillRepayment.auditStatus'),
		dataIndex: 'auditStatus',
		renderer:function(value){
    		var displayField = FossDataDictionary. rendererSubmitToDisplay (value,'BILL_REPAYMENT__AUDIT_STATUS');
    		return displayField;
    	},
		hidden:true
	},{
		header: pay.repayment.i18n('foss.stl.pay.queryBillRepayment.status'),
		dataIndex: 'status',
		type:'date',
		renderer:function(value){
    		var displayField = FossDataDictionary. rendererSubmitToDisplay (value,'BILL_REPAYMENT__STATUS');
    		return displayField;
    	},
		hidden:true
	},{
		header:pay.repayment.i18n('foss.stl.pay.queryBillRepayment.collectionCompanyCode'),
		dataIndex: 'collectionCompanyCode',
    	hidden:true
	},{
	
		header:pay.repayment.i18n('foss.stl.pay.queryBillRepayment.collectionCompanyName'),
		dataIndex: 'collectionCompanyName',
    	hidden:true
	},{
		header:'运单号',
		dataIndex: 'waybillNo',
		hidden:true
	}
	/* 重复
	,{
		header:'备注',
		dataIndex: 'notes'
	}*/
	/*还款单 确认收入时间无意义，删除
	,{
	
		header:pay.repayment.i18n('foss.stl.pay.queryBillRepayment.conrevenDate'),
		dataIndex: 'conrevenDate',
		renderer:function(value){
    		return stl.dateFormat(value,'Y-m-d H:i:s');
    	},
    	hidden:true
	}*/],
	viewConfig: {
		enableTextSelection: true
	},
	constructor:function(config){
		var me = this;
		cfg = Ext.apply({},config);
		me.store=Ext.create('Foss.stlbillrepayment.queryRepaymentStore');
		me.dockedItems =[{
		    xtype: 'toolbar',
		    dock: 'top',
		    layout:'column',
		    items: [{
				xtype:'container',
				border:false,
				html:'&nbsp;',
				columnWidth:.80
			},{
				xtype:'button',
				text:pay.repayment.i18n('foss.stl.pay.common.export'),
				columnWidth:.05,
				handler:pay.repayment.repaymentForBillExport
			}]
		},{
	   		xtype: 'toolbar',
		    dock: 'bottom',
		    layout:'column',		    	
		    defaults:{
				margin:'0 0 5 3'
			},		
		    items: [{
				height:5,
				columnWidth:1,
				layout:'hbox'
			},{
				xtype:'displayfield',
				allowBlank:true,
				columnWidth:.25,
				name:'totalRows',
				labelWidth:80,
				width:185,
				fieldLabel:pay.repayment.i18n('foss.stl.pay.queryBillRepayment.totalCountRecord'),
				value:me.getTotalRows()
			},{
				xtype:'displayfield',
				allowBlank:true,
				columnWidth:.25,
				labelWidth:80,
				name:'totalAmount',
				fieldLabel:pay.repayment.i18n('foss.stl.pay.queryBillRepayment.amount'),
//				renderer:stl.amountFormat,
				value:me.getTotalAmount()
			},{
				xtype:'displayfield',
				allowBlank:true,
				columnWidth:.25,
				name:'trueTotalAmount',
				labelWidth:90,
				fieldLabel:pay.repayment.i18n('foss.stl.pay.queryBillRepayment.trueAmount'),
//				renderer:stl.amountFormat,
				value:me.getTrueTotalAmount()
			},{
				xtype:'displayfield',
				allowBlank:true,
				columnWidth:.25,
				labelWidth:90,
				fieldLabel:pay.repayment.i18n('foss.stl.pay.queryBillRepayment.bverifyAmount'),
				name:'bverifyTotalAmount',
//				renderer:stl.amountFormat,
				value:me.getBverifyTotalAmount()
			},{
				xtype:'standardpaging',
				store:me.store,
				columnWidth:1,
				plugins: Ext.create('Deppon.ux.PageSizePlugin', {
					//设置分页记录最大值，防止输入过大的数值
					maximumSize: 200
				})
			 },{
				xtype:'container',
				border:false,
				html:'&nbsp;',
				columnWidth:.80
			},{
				xtype: 'button', 
				disabled:!pay.repayment.isPermission('/stl-web/pay/aduitBillRepayment.action'),
				hidden:!pay.repayment.isPermission('/stl-web/pay/aduitBillRepayment.action'),
				text: pay.repayment.i18n('foss.stl.pay.common.audit'),
				columnWidth:.05,
				handler:pay.repayment.aduitRepaymentBatch
			},{
				xtype: 'button', 
				disabled:!pay.repayment.isPermission('/stl-web/pay/revAduitBillRepayment.action'),
				hidden:!pay.repayment.isPermission('/stl-web/pay/revAduitBillRepayment.action'),
				text: pay.repayment.i18n('foss.stl.pay.common.unAudit'),
				columnWidth:.052,
				handler:pay.repayment.revAduitRepaymentBatch
			},{
				xtype: 'button', 
				disabled:!pay.repayment.isPermission('/stl-web/pay/disabledBillRepayment.action'),
				hidden:!pay.repayment.isPermission('/stl-web/pay/disabledBillRepayment.action'),
				text: pay.repayment.i18n('foss.stl.pay.common.disabled'),
				columnWidth:.05,
				handler:pay.repayment.disabledRepaymentBatch
			}]
		}];	
		me.callParent([cfg]);
	}
});





//-------------------------------------------------qiaolifeng-------------------------------------------------

//核销单model
Ext.define('Foss.Repaymentbill.WriteoffDetailModel',{
	extend:'Ext.data.Model',
	fields:[{
		name:'writeoffBillNo'
	},{
		name:'beginNo'
	},{
		name:'endNo'
	},{
		name:'beginWaybillNo'
	},{
		name:'endWaybillNo'
	},{
		name:'customerName'
	},{
		name:'customerCode'
	},{
		name:'amount',
		type:'long'
	},{
		name:'writeoffType'
	},{
		name:'orgName'
	},{
		name:'createUserName'
	},{
		name:'writeoffTime',
		type:'date',
		convert:stl.longToDateConvert 
	},{
		name:'isRedBack'
	},{
		name:'active'
	},{
		name:'orgCode'
	},{
		name:'createUserCode'
	},{
		name:'accountDate',
		type:'date',
		convert:stl.longToDateConvert 
	},{
		name:'statementBillNo'
	}]
});



//核销单Store
Ext.define('Foss.Repaymentbill.WriteoffDetailStore',{
	extend:'Ext.data.Store',
	model:'Foss.Repaymentbill.WriteoffDetailModel'
});



//查询还款单明细（核销单表）
Ext.define('Foss.Repaymentbill.WriteoffDetailGrid',{
	extend:'Ext.grid.Panel',
    title: pay.repayment.i18n('foss.stl.pay.queryBillRepayment.repaymentDetail'),
    frame:true,
	height:500,
	emptyText:pay.repayment.i18n('foss.stl.pay.common.noResult'),
    store: Ext.create('Foss.Repaymentbill.WriteoffDetailStore'),
	columns:[{
		header: pay.repayment.i18n('foss.stl.pay.queryBillRepayment.writeoffBillNo'), 
		dataIndex: 'writeoffBillNo'
	},{
		header: pay.repayment.i18n('foss.stl.pay.queryBillRepayment.repaymentNo'), 
		dataIndex: 'beginNo'
	},{
		header: '还款运单号', 
		dataIndex: 'beginWaybillNo',
		hidden:true
	},{
		header:pay.repayment.i18n('foss.stl.pay.queryBillRepayment.statementillNO'), 
		dataIndex: 'statementBillNo'
	},{
		header:pay.repayment.i18n('foss.stl.pay.queryBillRepayment.endNo'), 
		dataIndex: 'endNo'
	},{
		header: '应收运单号', 
		dataIndex: 'endWaybillNo'
	},{
		header: pay.repayment.i18n('foss.stl.pay.queryBillRepayment.customerName'), 
		dataIndex: 'customerName'		
	},{
		header: pay.repayment.i18n('foss.stl.pay.queryBillRepayment.customerCode'), 
		dataIndex: 'customerCode'
	},{
		header: pay.repayment.i18n('foss.stl.pay.queryBillRepayment.writeOffAmount'), 
		dataIndex: 'amount',
		align: 'right',  
		renderer:stl.amountFormat
	},{
		header: pay.repayment.i18n('foss.stl.pay.queryBillRepayment.writeoffType'),
		dataIndex: 'writeoffType',
		renderer:function(value){
    		var displayField = FossDataDictionary.rendererSubmitToDisplay(value,settlementDict.BILL_WRITEOFF__WRITEOFF_TYPE);
    		return displayField;
    	}
	},{
		header: pay.repayment.i18n('foss.stl.pay.queryBillRepayment.orgName'), 
		dataIndex: 'orgName'				
	},{
		header: pay.repayment.i18n('foss.stl.pay.queryBillRepayment.createUserName'),
		dataIndex: 'createUserName' 
	},{
		header: pay.repayment.i18n('foss.stl.pay.queryBillRepayment.writeoffTime'),
		dataIndex: 'writeoffTime',
		renderer:function(value){
    		return stl.dateFormat(value,'Y-m-d H:i:s');
    	}
	},{
		header:pay.repayment.i18n('foss.stl.pay.queryBillRepayment.accountDate'),
		dataIndex: 'accountDate',
		renderer:function(value){
    		return stl.dateFormat(value,'Y-m-d H:i:s');
    	}
	}]
	,
	viewConfig: {
		enableTextSelection: true
	},
	dockedItems:[{
   		xtype: 'toolbar',
	    dock: 'bottom',
	    layout:'column',		    	
	    defaults:{
			margin:'0 0 5 3'
		},		
	    items: [{
			xtype:'displayfield',
			allowBlank:true,
			columnWidth:.2,
			labelWidth:90,
			fieldLabel:pay.repayment.i18n('foss.stl.pay.queryBillRepayment.totalCountRecord')
		},{
			xtype:'container',
			border:false,
			html:'&nbsp;',
			columnWidth:.05
		},{
			xtype:'displayfield',
			allowBlank:true,
			columnWidth:.2,
			labelWidth:90,
//			renderer:stl.amountFormat,
			fieldLabel:pay.repayment.i18n('foss.stl.pay.queryBillRepayment.writeoffAmount')
		}]
	}]
});

//核销grid创建
var repayWriteoffGrid=Ext.create('Foss.Repaymentbill.WriteoffDetailGrid');


/**
 * 声明还款单核销明细window界面
 */
Ext.define('Foss.Repaymentbill.WriteoffDetailWindow',{
	extend:'Ext.window.Window',
	width:stl.SCREENWIDTH*0.68,
	modal:true,
	constrainHeader: true,
	closeAction:'destory',
	getWriteoffRepayGrid:function(){
  	   return repayWriteoffGrid;
  	 },
  	 id:'Foss_Repaymentbill_WriteoffDetailWindow_ID',
	items:[repayWriteoffGrid]
});
//-------------------------------------------------------------------------------------------------------------------------
//DisableReasonStore：Model
Ext.define('Foss.Repaymentbill.DisableReasonModel',{
	extend:'Ext.data.Model',
	fields:[{
		name:'disableReasonCode',
		type:'string'
	},{
		name:'disableReasonName',
		type:'string'
	}]
	
});

//DisableReasonStore：store
Ext.define('Foss.Repaymentbill.DReasonStore',{
	extend:'Ext.data.Store',
	model:'Foss.Repaymentbill.DisableReasonModel',
	data:{
		'items':[
			{disableReasonCode:'11',disableReasonName:pay.repayment.i18n('foss.stl.pay.queryBillRepayment.genggaidan')},
			{disableReasonCode:'22',disableReasonName:pay.repayment.i18n('foss.stl.pay.queryBillRepayment.fukuanfangshicuowu')},
			{disableReasonCode:'33',disableReasonName:pay.repayment.i18n('foss.stl.pay.queryBillRepayment.huancuodanhao')},
			{disableReasonCode:'44',disableReasonName:pay.repayment.i18n('foss.stl.pay.queryBillRepayment.huancuojine')},
			{disableReasonCode:'55',disableReasonName:pay.repayment.i18n('foss.stl.pay.queryBillRepayment.weishoukuancuowuhuankuan')},
			{disableReasonCode:'66',disableReasonName:pay.repayment.i18n('foss.stl.pay.queryBillRepayment.qita')}
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

//核销单model
Ext.define('Foss.Repaymentbill.RepayEntryDetailModel',{
	extend:'Ext.data.Model',
	fields:[{
		name:'id'
	},{
		name:'repaymentApplicationId'
	},{
		name:'writeOffBillNo'
	},{
		name:'repaymentNo'
	},{
		name:'receviceNo'
	},{
		name:'receviceWaybillNo'
	},{
		name:'statementNo'
	},{
		name:'amount',
		type:'long'
	},{
		name:'customerCode'
	},{
		name:'customerName'
	},{
		name:'writeoffType'
	},{
		name:'writeoffTime',
		type:'date',
		convert:stl.longToDateConvert 
	},{
		name:'orgCode'
	},{
		name:'orgName'
	}]
});

Ext.define('Foss.stlbillrepayment.RepayEntryDetailStore',{
	extend:'Ext.data.Store',
	model:'Foss.Repaymentbill.RepayEntryDetailModel',
	pageSize : 10 ,
	sorters: [{
	     property: 'writeoffTime',
	     direction: 'ASC'
	 }],
	 proxy:{
		type:'ajax',
		url:pay.realPath('disableBillRepaymentApplyDetail.action'),
		actionMethods:'post',
		reader:{
			type:'json',
			root:'billRepaymentDisableResultVo.billRepaymentDisableResultDto.billRepaymentDisableDto.details',
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
	   			var grid = Ext.getCmp('Foss_Repaymentbill_ApplyForDisableWin_ID').getRepayDisableGrid();
   				if(grid) {
   					grid.removeListener('deselect',this);
   				}
   				
	   		},
	   		'load':function(_this,records,successful,eOpts){
	   			var me = this;
	   			if (successful) {
					var grid = Ext.getCmp('Foss_Repaymentbill_ApplyForDisableWin_ID')
							.getRepayDisableGrid();
					if (grid) {
						var selection = grid.getSelectionModel();
						var selectedItems = grid.getSelectedItems();

						for (var index = 0; index < records.length; index++) {
							var record = records[index];
							for (var i = 0; i < selectedItems.length; i++) {
								if (selectedItems[i] == record.get('id')) {
									selection.select(index, true, false);
								}
							}
						}
						grid.addListener('deselect',function(_this, record, index, eOpts) {
							var me = this;
							var item = record.get('id');
							for(var i=0;i<me.selectedItems.length;i++) {
								if(item == me.selectedItems[i]) {
									delete me.selectedItems[i]
								}
							}
						});
					}
				}
				
			}
		};
		me.callParent([ cfg ]);
	} 
});


// 092036-BOCHENLONG 20131113
// 作废还款单
pay.repayment.disabledRepaymentApply = function(grid, rowIndex, colIndex){

	// 获取选中的还款单数据
	var selection = grid.getStore().getAt(rowIndex);
	
	
	pay.repayment.applyDisable(selection);

	
};

// 092036-BOCHENLONG 20131113
pay.repayment.applyDisable = function(selection) {

	Ext.Ajax.request({
			url:pay.realPath('disableBillRepaymentApply.action'),
			params:{
				'billRepaymentDisableVo.billRepaymentDisableDto.repaymentNo':selection.data.repaymentNo
			},
			method:'post',
			success:function(response){
				var result = Ext.decode(response.responseText);	
				
				var win = Ext.getCmp('Foss_Repaymentbill_ApplyForDisableWin_ID');
				
				if(win == null){
					 win = Ext.create('Foss.Repaymentbill.ApplyForDisableWin');
				}
				var  billRepaymentDisableDto = result.billRepaymentDisableResultVo.billRepaymentDisableResultDto.billRepaymentDisableDto;
				
				var disableForm = win.getRepayDisableForm().getForm();
				disableForm.findField('orgName').setValue(billRepaymentDisableDto.applyOrgName);
				disableForm.findField('repaymentBillNo').setValue(billRepaymentDisableDto.repaymentNo);
				disableForm.findField('customerCode').setValue(billRepaymentDisableDto.customerCode);
				disableForm.findField('customerName').setValue(billRepaymentDisableDto.customerName);
				disableForm.findField('repaymentAmount').setValue(pay.repayment.convertAmount(billRepaymentDisableDto.repaymentAmount));
				disableForm.findField('paymentType').setValue(FossDataDictionary.rendererSubmitToDisplay(billRepaymentDisableDto.paymentType,settlementDict.SETTLEMENT__PAYMENT_TYPE));
				
				var disableGrid = win.getRepayDisableGrid();
				var params = {'billRepaymentDisableVo.billRepaymentDisableDto.repaymentNo':selection.data.repaymentNo};
				if(null==params || params==false){
					return;
				}
				//设置查询参数
				disableGrid.store.setSubmitParams(params);
				disableGrid.store.loadPage(1, {
					callback : function(records, operation, success) {
						var rawData = disableGrid.store.proxy.reader.rawData;
						if (!success && !rawData.isException) {
							return false;
						}
						if (success) {
							var result = Ext
									.decode(operation.response.responseText);
							toolBar = disableGrid
									.getDockedItems('toolbar[dock="bottom"]')[0];
	
							var resultDto = result.billRepaymentDisableResultVo.billRepaymentDisableResultDto;
	
							toolBar.getComponent(0).setValue(resultDto.totalCount);
							toolBar.getComponent(1).setValue(pay.repayment.convertAmount(resultDto.totalVerifyAmount));								
						}
					}
				});
				
				win.show();
			},
			exception:function(response){
				var result = Ext.decode(response.responseText);	
				Ext.Msg.alert(pay.repayment.i18n('foss.stl.pay.common.alert'),result.message);
			}		
		});
	;
};

// 092036-BOCHENLONG 20131113
Ext.define('Foss.Repaymentbill.RepaymentEntryFrom',{
	extend:'Ext.form.Panel',
 	frame:true,
 	collapsible:false,
	animcollapse:true,
	defaults:{
		margin :'5 5 5 0',
		labelWidth :85,
//		colspan :1 ,
		readOnly:true,
		columnWidth:.5
	},
 	defaultType:'textfield',
 	layout:'column',
 	items:[{
 		fieldLabel:pay.repayment.i18n('foss.stl.pay.queryBillRepayment.applyOrgName'),
 		name:'orgName'
 	},{
 		fieldLabel:pay.repayment.i18n('foss.stl.pay.queryBillRepayment.repaymentBillNo'),
 		name:'repaymentBillNo'
 	},{
 		fieldLabel:pay.repayment.i18n('foss.stl.pay.queryBillRepayment.customerCode'),
 		name:'customerCode'
 	},{
 		fieldLabel:pay.repayment.i18n('foss.stl.pay.queryBillRepayment.customerName'),
 		name:'customerName'
 	},{
 		fieldLabel:pay.repayment.i18n('foss.stl.pay.queryBillRepayment.amount'),
 		name:'repaymentAmount'
 	},{
 		fieldLabel:pay.repayment.i18n('foss.stl.pay.queryBillRepayment.paymentType'),
 		name:'paymentType'
 	}]
}) ;

// 092036-BOCHENLONG 20131113
Ext.define('Foss.repaymentbill.RepayEntryDetailGrid',{
	extend:'Ext.grid.Panel',
    title: pay.repayment.i18n('foss.stl.pay.queryBillRepayment.repaymentDetail'),
    frame:true,
	height:400,
	selModel:Ext.create('Ext.selection.CheckboxModel'),
	totalVerifyAmount:null,
	getTotalVerifyAmount:function(){
		var me=this;
		if(Ext.isEmpty(me.totalVerifyAmount)){
			me.totalVerifyAmount=0;
		}
		return me.totalVerifyAmount;
	},
	totalCount:null,//总条数
	getTotalCount:function(){
		var me=this;
		if(Ext.isEmpty(me.totalCount)){
			me.totalCount=0;
		}
		return me.totalCount;
	},
	selectedItems:[],
	getSelectedItems:function(){
		var me = this;
		return me.selectedItems;
	},
	setSelectedItems:function() {
		var me = this;
		me.selectedItems.length = 0;
	},
	//curSelected:null,
	sumItem:0,
	setSumItem:function() {
		var me = this;
		me.sumItem = 0;
	},
	getSumItem:function() {
		var me = this;
		return me.sumItem;
	},
	containItem:function(item){
		var me = this;
		for(var i=0;i<me.selectedItems.length;i++){
			if(me.selectedItems[i] == item){
				return true;
			}		
		}
		return false;
	},
	emptyText:pay.repayment.i18n('foss.stl.pay.common.noResult'),
	columns:[
	{
		dataIndex: 'id',
		text: pay.repayment.i18n('foss.stl.pay.queryBillRepayment.id'), 
		hidden:true
	}
	,{
		text: pay.repayment.i18n('foss.stl.pay.queryBillRepayment.writeoffBillNo'), 
		dataIndex: 'writeOffBillNo'
	},{
		text: pay.repayment.i18n('foss.stl.pay.queryBillRepayment.repaymentNo'), 
		dataIndex: 'repaymentNo'
	},{
		text:pay.repayment.i18n('foss.stl.pay.queryBillRepayment.statementillNO'), 
		dataIndex: 'statementNo'
	},{
		header:pay.repayment.i18n('foss.stl.pay.queryBillRepayment.endNo'), 
		dataIndex: 'receviceNo'
	},{
		header: pay.repayment.i18n('foss.stl.pay.common.waybillNo'), 
		dataIndex: 'receviceWaybillNo'
	},{
		header: pay.repayment.i18n('foss.stl.pay.queryBillRepayment.customerName'), 
		dataIndex: 'customerName'		
	},{
		header: pay.repayment.i18n('foss.stl.pay.queryBillRepayment.customerCode'), 
		dataIndex: 'customerCode'
	},{
		header: pay.repayment.i18n('foss.stl.pay.queryBillRepayment.writeOffAmount'), 
		dataIndex: 'amount',
		align: 'right',  
		renderer:stl.amountFormat
	},{
		header: pay.repayment.i18n('foss.stl.pay.queryBillRepayment.writeoffType'),
		dataIndex: 'writeoffType',
		renderer:function(value){
    		var displayField = FossDataDictionary.rendererSubmitToDisplay(value,settlementDict.BILL_WRITEOFF__WRITEOFF_TYPE);
    		return displayField;
    	}
	},{
		header: pay.repayment.i18n('foss.stl.pay.queryBillRepayment.orgName'), 
		dataIndex: 'orgName'				
	},
//		{
//		header: pay.repayment.i18n('foss.stl.pay.queryBillRepayment.createUserName'),
//		dataIndex: 'createUserName' 
//	},
	{
		header: pay.repayment.i18n('foss.stl.pay.queryBillRepayment.writeoffTime'),
		dataIndex: 'writeoffTime',
		renderer:function(value){
    		return stl.dateFormat(value,'Y-m-d H:i:s');
    	}
	}],
	listeners:{
		'select':function(_this, record, index, eOpts) {
			var me = this;
			var contained = false;
			var item = record.get('id');
			var m = record.get('amount');
			if(me.selectedItems) {
				if(!me.containItem(item)) {
					me.selectedItems.push(item);
					me.sumItem = me.sumItem + m;
				} 
			} else {
				me.selectedItems.push(item);
			};
			var form = Ext.getCmp('Foss_Repaymentbill_ApplyForDisableWin_ID').getRepaymentApplyEntryFrom().getForm();
   			form.findField('disableCount').setValue(me.selectedItems.length);
   			form.findField('disableAmount').setValue(pay.repayment.convertAmount(me.sumItem));
			},
		'deselect':function(_this, record, index, eOpts) {
			var me = this;
			var item = record.get('id');
			var m = record.get('amount');
			for(var i=0;i<me.selectedItems.length;i++) {
				if(item == me.selectedItems[i]) {
					me.selectedItems.splice(i,1);
					me.sumItem = me.sumItem - m;
				}
			}
			
			var form = Ext.getCmp('Foss_Repaymentbill_ApplyForDisableWin_ID').getRepaymentApplyEntryFrom().getForm();
   			if(me.selectedItems.length==0) {
   				form.findField('disableCount').setValue(0);
	   			form.findField('disableAmount').setValue(0);
   			} else {
   				form.findField('disableCount').setValue(me.selectedItems.length);
	   			form.findField('disableAmount').setValue(pay.repayment.convertAmount(me.sumItem));
   			}
		}
	},
	constructor:function(config){
		var me = this;
		cfg = Ext.apply({},config);
		me.store=Ext.create('Foss.stlbillrepayment.RepayEntryDetailStore');
		me.dockedItems =[{
	   		xtype: 'toolbar',
		    dock: 'bottom',
		    layout:'column',		    	
		    defaults:{
				margin:'0 0 5 3'
			},		
		    items: [{
				xtype:'displayfield',
				allowBlank:true,
				columnWidth:.25,
				name:'totalCount',
				labelWidth:80,
				width:185,
				fieldLabel:pay.repayment.i18n('foss.stl.pay.queryBillRepayment.totalCountRecord'),
				value:me.getTotalCount()
			},{
				xtype:'displayfield',
				allowBlank:true,
				columnWidth:.25,
				labelWidth:80,
				name:'totalVerifyAmount',
				fieldLabel:pay.repayment.i18n('foss.stl.pay.queryBillPayment.verifyAmount'),
				value:me.getTotalVerifyAmount()
			},{
				xtype:'standardpaging',
				store:me.store,
				pageSize:10,
				columnWidth:1,
				plugins: Ext.create('Deppon.ux.PageSizePlugin', {
					//设置分页记录最大值，防止输入过大的数值
					maximumSize: 100,
					sizeList: [['10', 10], ['50', 50], ['100', 100]]
				})
			 },{
					xtype:'container',
					height:5,
					border:false,
					html:'&nbsp;',
					columnWidth:1 
				}
			 ]
		}];	
		me.callParent([cfg]);
	}

});

// 092036-BOCHENLONG 20131113
Ext.define('Foss.Repaymentbill.RepaymentApplyEntryFrom',{
	extend:'Ext.form.Panel',
 	frame:true,
 	collapsible:false,
	animcollapse:true,
	defaults:{
		margin :'5 5 5 0',
		labelWidth :85,
//		colspan :1 ,
		columnWidth:.5
	},
 	defaultType:'textfield',
 	layout:'column',
 	items:[{
 		fieldLabel:pay.repayment.i18n('foss.stl.pay.queryBillRepayment.disableNum'),
 		name:'disableCount',
 		value:0,
 		readOnly:true
 	},{
 		fieldLabel:pay.repayment.i18n('foss.stl.pay.queryBillRepayment.disableAmount'),
 		name:'disableAmount',
 		value:0,
 		readOnly:true
 	}
 	,{
	    xtype:'combobox',
	    name:'disableReason',
	    columnWidth:.33,
	    fieldLabel:pay.repayment.i18n('foss.stl.pay.queryBillRepayment.disableReason'),
	    id:'pay.repayment.disableReasonselector',
		store:Ext.create('Foss.Repaymentbill.DReasonStore'),
		queryModel:'local',
		value:'请选择一项作废原因',
		editable:false,
		displayField:'disableReasonName',
		valueField:'disableReasonCode'
	 }
 	,{
 		fieldLabel:pay.repayment.i18n('foss.stl.pay.queryBillRepayment.note'),
		xtype:'textarea',
		name:'disableNote',
		columnWidth:1,
		autoScroll:true,
		emptyText: pay.repayment.i18n('foss.stl.pay.queryBillRepayment.notebitian'),
        allowBlank:false
 	}]
}) ;

// 092036-BOCHENLONG 20131113
Ext.define('Foss.Repaymentbill.ApplyForDisableWin',{
	extend:'Ext.window.Window',
	title:pay.repayment.i18n('foss.stl.pay.queryBillRepayment.applyDisableTitle'),
    id:'Foss_Repaymentbill_ApplyForDisableWin_ID',
	titleAlign:'left',
	width:stl.SCREENWIDTH*0.68,
	modal:true,
	constrainHeader: true,
	closeAction:'close',
	repayDisableForm:null,
	getRepayDisableForm:function() {
		var me = this;
		return me.repayDisableForm;
	},
	repayDisableGrid:null,
	getRepayDisableGrid:function() {
		var me = this;
		return me.repayDisableGrid;
	},
	repaymentApplyEntryFrom:null,
	getRepaymentApplyEntryFrom:function() {
		var me = this;
		return this.repaymentApplyEntryFrom;
	},
	
	listeners:{'beforeclose':function(_this, eOpts) {
		var me=this;
		me.getRepayDisableGrid().setSelectedItems();
		me.getRepayDisableGrid().setSumItem();
		me.getRepayDisableForm().getForm().reset();
		me.getRepaymentApplyEntryFrom().getForm().reset();
	 }},
	
	constructor:function(config){
		var me = this;
		cfg = Ext.apply({},config);
	
		me.repayDisableForm = Ext.create('Foss.Repaymentbill.RepaymentEntryFrom');
		me.repayDisableGrid = Ext.create('Foss.repaymentbill.RepayEntryDetailGrid');
		me.repaymentApplyEntryFrom = Ext.create('Foss.Repaymentbill.RepaymentApplyEntryFrom');
		var buttons = {	
			border: 1,
			xtype:'container',
			columnWidth:.99,
			defaultType:'button',
			layout:'column',
			items:[
			{
				xtype: 'container',
				border : false,
				columnWidth:.84,
				html: '&nbsp;'
			},{
				text:pay.repayment.i18n('foss.stl.pay.common.cancel'),
				columnWidth:.08,
				handler:function() {
					var window=this.up('window');
					window.getRepayDisableGrid().setSelectedItems();
					window.getRepayDisableGrid().setSumItem();
					window.getRepayDisableForm().getForm().reset();
					window.getRepaymentApplyEntryFrom().getForm().reset();
					window.close();
				}
			},{
				text:pay.repayment.i18n('foss.stl.pay.common.commit'),
				cls:'yellow_button',
				columnWidth:.08,
				handler:function(){
					var window=this.up('window');
					pay.repayment.addDisableApply(window);
				}
			}]
	    };
		
		me.items = [me.repayDisableForm,me.repayDisableGrid,me.repaymentApplyEntryFrom,buttons];
		me.callParent([cfg]);
	}
});

pay.repayment.addDisableApply = function(window) {
	var me = window;
	var yesFn=function() {
	
	var repayDisableForm = me.getRepayDisableForm().getForm();
	var repayDisableGrid = me.getRepayDisableGrid();
	var repaymentApplyEntryFrom = me.getRepaymentApplyEntryFrom().getForm();
	

	var repaymentNo = repayDisableForm.findField('repaymentBillNo').getValue();
	var disableReason = pay.repayment.gomboDisplay(Ext.getCmp('pay.repayment.disableReasonselector'));
	
	var disableAmount = repayDisableGrid.getSumItem();
	var disableNote = repaymentApplyEntryFrom.findField('disableNote').getValue();
	var detailIDs = repayDisableGrid.getSelectedItems();
		// var isAllDisable = '';
		// if(detailIDs.length == repayDisableGrid.getTotalCount()) {
		// isAllDisable = 'Y';
		// }else{
		// isAllDisable = 'N';
		//	};
	
	
//	if(!disableReason) {
//		Ext.Msg.alert(pay.repayment.i18n('foss.stl.pay.common.alert'),pay.repayment.i18n('foss.stl.pay.queryBillRepayment.nodisreason')).toFront();
//		return false;
//	}
//	
//	if(!disableNote) {
//		Ext.Msg.alert(pay.repayment.i18n('foss.stl.pay.common.alert'),pay.repayment.i18n('foss.stl.pay.queryBillRepayment.nodisreason')).toFront();
//		return false;
//	}
	
	me.disable(false);
		//10秒后自动解除灰掉效果
		setTimeout(function() {
		me.enable(true);
	}, 2000);
	
	var submitParams = {
			'billRepaymentDisableVo.billRepaymentDisableDto.repaymentNo':repaymentNo,
			'billRepaymentDisableVo.billRepaymentDisableDto.disableReason':disableReason,
			'billRepaymentDisableVo.billRepaymentDisableDto.amount':disableAmount,
			'billRepaymentDisableVo.billRepaymentDisableDto.disableNote':disableNote,
			'billRepaymentDisableVo.billRepaymentDisableDto.detailIds':detailIDs,
			//'billRepaymentDisableVo.billRepaymentDisableDto.isAllDisable':isAllDisable,
			'billRepaymentDisableVo.billRepaymentDisableDto.disableNum':detailIDs.length
		};
	
	
	Ext.Ajax.request({
		url:pay.realPath('addRepaymentApply.action'),
		method:'post',
		params:submitParams,
		success : function(response){
			var result = Ext.decode(response.responseText);
			if(!result.success && !result.isException){
			    Ext.Msg.alert(pay.repayment.i18n('foss.stl.pay.common.alert'),result.message);
			    	return false;
			    }
			Ext.ux.Toast.msg(pay.repayment.i18n('foss.stl.pay.common.alert'),pay.repayment.i18n('foss.stl.pay.queryBillRepayment.baocunchenggong'), 'success', 1000);
			//审批成功之后，重置下作废信息FORM和GRID的中间量
			repayDisableGrid.setSelectedItems();
			//设置查询参数
			var params = {'billRepaymentDisableVo.billRepaymentDisableDto.repaymentNo':repaymentNo};
				if(null==params || params==false){
					return;
				}
			repayDisableGrid.store.setSubmitParams(params);
			repayDisableGrid.store.loadPage(1, {
				callback : function(records, operation, success) {
				var rawData = repayDisableGrid.store.proxy.reader.rawData;
				if (!success && !rawData.isException) {
						return false;
				}
				if (success) {
				var result = Ext.decode(operation.response.responseText);
				toolBar = repayDisableGrid.getDockedItems('toolbar[dock="bottom"]')[0];
				var resultDto = result.billRepaymentDisableResultVo.billRepaymentDisableResultDto;
				toolBar.getComponent(0).setValue(resultDto.totalCount);
				toolBar.getComponent(1).setValue(pay.repayment.convertAmount(resultDto.totalVerifyAmount));								
				}
				}});
			repaymentApplyEntryFrom.reset();
		},
		exception:function(response){
			var result = Ext.decode(response.responseText);
			Ext.ux.Toast.msg(pay.repayment.i18n('foss.stl.pay.common.alert'),result.message + pay.repayment.i18n('foss.stl.pay.queryBillRepayment.baocunshibai'), 'error', 1000);
		}
	});
	};
	var noFn=function(){
	 	return false;
	};
	pay.billRepaymentConfirmAlert(pay.repayment.i18n('foss.stl.pay.queryBillRepayment.querentijiao'),yesFn,noFn);
};	

pay.repayment.gomboDisplay = function(combo) {
    var value = combo.getValue();
    var valueField = combo.valueField;
    var record;
    combo.getStore().each(function(r){
        if(r.data[valueField] == value){
            record = r;
            return false;
        }
    });
    return record ? record.get(combo.displayField) : null;
}

// 初始化界面
Ext.onReady(function() {
	Ext.Ajax.timeout=180000;
	Ext.QuickTips.init();
	
	if (Ext.getCmp('T_pay-manageBillRepayment_content')) {
		return;
	} 
	//创建还款单4个tab
	var StlBillRepaymentQueryInfoTab = Ext.create('Foss.stlbillrepayment.StlBillRepaymentQueryInfoTab');	
	
	//还款单列表
	//查询还款单grid创建
	var StlBillRepaymentQueryInfoGrid = Ext.getCmp('Foss.stlbillrepayment.StlBillRepaymentQueryInfoGrid_Id');
	if(Ext.isEmpty(StlBillRepaymentQueryInfoGrid)){
		StlBillRepaymentQueryInfoGrid = Ext.create('Foss.stlbillrepayment.StlBillRepaymentQueryInfoGrid',{
			id:'Foss.stlbillrepayment.StlBillRepaymentQueryInfoGrid_Id',
			hidden:true
		});
	}
	
	//主页面创建
	Ext.create('Ext.panel.Panel',{
		id: 'T_pay-manageBillRepayment_content',
		cls: "panelContentNToolbar",
		bodyCls: 'panelContentNToolbar-body',
		layout: 'auto',
		//获得查询FORM
		getQueryTab : function() {
			return StlBillRepaymentQueryInfoTab;
		},
		//获得查询结果GRID
		getQueryGrid : function() {
			return StlBillRepaymentQueryInfoGrid;
		},
		items: [StlBillRepaymentQueryInfoTab,StlBillRepaymentQueryInfoGrid],
		renderTo: 'T_pay-manageBillRepayment-body'
		 //角色权限问题		
		,listeners:{
			'boxready':function(th){
				var roles = pay.repayment.roles;
				var queryByDateTab = th.getQueryTab().items.items[0].items.items[0];
				
				var orgName = queryByDateTab.getForm().findField('orgName');
				//设置部门为默认当前登录部门
				if(!Ext.isEmpty(stl.currentDept.code)){
					var displayText = stl.currentDept.name
					var valueText = stl.currentDept.code;
					var store = orgName.store;
					var  key = orgName.displayField + '';
					var value = orgName.valueField+ '';
					
					var m = Ext.create(store.model.modelName);
					m.set(key, displayText);
					m.set(value, valueText);
					
					store.loadRecords([m]);
					orgName.setValue(valueText);
				}
			}
		}
	});
});