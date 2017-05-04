/**
  * 查询成功后，执行的公用方法
  */ 
writeoff.statementAdd.statementQuerySuccess = function(result){
	var baseInfoAdd,
		beginBillsFormAdd,
		currentBillsFormAdd,
		statementPreEntryAddGrid,
		statementEntryAddGrid,
		paymentDayPanel,
		statementOfAccountDBeginPeriodList,
		statementOfAccountDPeriodList,
		statementOfAccount,
		periodAmount,
		periodUnverifyRecAmount,
		periodUnverifyPayAmount,
		periodUnverifyPreAmount,
		periodUnverifyAdvAmount,
		model;
		
	baseInfoAdd = Ext.getCmp('Foss_statementbill_BaseInfoAdd_ID');
	beginBillsFormAdd = Ext.getCmp('Foss_statementbill_BeginBillsFormAdd_ID');
	currentBillsFormAdd = Ext.getCmp('Foss_statementbill_CurrentBillsFormAdd_ID')
	statementPreEntryAddGrid = Ext.getCmp('Foss_statementbill_StatementPreEntryAddGrid_ID')
	statementEntryAddGrid = Ext.getCmp('Foss_statementbill_StatementEntryAddGrid_ID')
	paymentDayPanel = Ext.getCmp('Foss_statementbill_PaymentDayPanel_ID');
	
	//获取表单后台数据
	statementOfAccount = result.statementOfAccountMakeQueryResultVo.statementOfAccountMakeQueryResultDto.statementOfAccount;
	statementOfAccountDBeginPeriodList = result.statementOfAccountMakeQueryResultVo.statementOfAccountMakeQueryResultDto.statementOfAccountDBeginPeriodList;
	statementOfAccountDPeriodList = result.statementOfAccountMakeQueryResultVo.statementOfAccountMakeQueryResultDto.statementOfAccountDPeriodList;
	model = new Foss.statementbill.StatementFormModel(statementOfAccount);
	
	if(Ext.isEmpty(statementOfAccountDBeginPeriodList) && Ext.isEmpty(statementOfAccountDPeriodList)){
		Ext.Msg.alert(writeoff.statementAdd.i18n('foss.stl.writeoff.common.alert'),writeoff.statementAdd.i18n('foss.stl.writeoff.common.noResult'));
		return false;
	}
	
	//判断如果本期和往期都为空，则不显示
	if(statementOfAccountDBeginPeriodList.length==0 && statementOfAccountDPeriodList.length==0){
		Ext.Msg.alert(writeoff.statementAdd.i18n('foss.stl.writeoff.common.alert'),writeoff.statementAdd.i18n('foss.stl.writeoff.common.noResult'));
		return false;
	}	
	//判断银行账号信息异常
	var accountException = result.statementOfAccountMakeQueryResultVo.statementOfAccountMakeQueryResultDto.accountException;
	if(!Ext.isEmpty(accountException)){
		Ext.Msg.alert(writeoff.statementAdd.i18n('foss.stl.writeoff.common.alert'),accountException);
	}
	
	statementPreEntryAddGrid.store.loadData(statementOfAccountDBeginPeriodList);
	statementEntryAddGrid.store.loadData(statementOfAccountDPeriodList);
	
	//form表单load后台数据
	baseInfoAdd.loadRecord(model);
	beginBillsFormAdd.loadRecord(model);
	currentBillsFormAdd.loadRecord(model);
	//获取本期剩余金额进行判断
	periodAmount = model.get('periodAmount');
	periodUnverifyRecAmount = model.get('periodUnverifyRecAmount');
	periodUnverifyPayAmount = model.get('periodUnverifyPayAmount');
	periodUnverifyPreAmount = model.get('periodUnverifyPreAmount');
	periodUnverifyAdvAmount = model.get('periodUnverifyAdvAmount');
	
	//控制本期金额图标显示和隐藏
	writeoff.statementUnVerifyAmountShow(model,currentBillsFormAdd,writeoff.PAGEFROM_STATEMENTADD);
	
	//给总条数复制
	statementEntryAddGrid.down('toolbar').query('textfield')[0].setValue(statementEntryAddGrid.store.data.length);
	//查询后显示隐藏组件
	baseInfoAdd.show();
	paymentDayPanel.show();
	statementPreEntryAddGrid.show();
	statementEntryAddGrid.show();
} 
 
/**
 * 按客户制作查询对账单
 */
writeoff.statementAdd.statementQueryByCustomerInfo = function(){
	var form  = this.up('form').getForm();
	//声明客户编码和客户名称
	var customerCode,customerName;
	if(form.isValid()){
		//校验日期
		var periodBeginDate = form.findField('periodBeginDate').getValue();
		var periodEndDate = form.findField('periodEndDate').getValue();
		//发货联系人
		var deliveryCustomerContact = form.findField('deliveryCustomerContact').getValue();
		//比较起始日期和结束日期
		var compareTwoDate = stl.compareTwoDate(periodBeginDate,periodEndDate);
		
		//获取客户类型
		var customerType =  form.findField('customerType').getValue();
		//声明日期类型
		var dateType = form.getValues().queryDateType;
		var billType = '';
		//校验客户/代理信息
		if(customerType=='01'){
			//如果不为快递代理选择器，则默认业务日期
			dateType = 'BU';
			//如果不为快递代理，则客户类型默认为空，后台继续根据部门判断是客户对账单还是代理
			billType='';
			
			//客户编码
			customerCode = form.getValues().customerCode;
			//客户名称
			customerName = form.findField('customerCode').getRawValue();
			//如果为客户，则客户编码不能为空
			if(Ext.isEmpty(customerCode)){
				Ext.Msg.alert(writeoff.statementAdd.i18n('foss.stl.writeoff.common.alert'),writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.customerIsNullWarning'));
				return false;
			}
		//如果为偏线代理，则校验偏线代理信息不能为空	
		}else if(customerType=='02'){
			dateType = 'BU';
			billType='';
			//客户编码
			customerCode = form.getValues().vehagencycomp;
			//客户名称
			customerName = form.findField('vehagencycomp').getRawValue();
			//如果为客户，则客户编码不能为空
			if(Ext.isEmpty(customerCode)){
				Ext.Msg.alert(writeoff.statementAdd.i18n('foss.stl.writeoff.common.alert'),writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.angencyIsNullWarning'));
				return false;
			}
		//如果为空运代理
		}else if(customerType=='03'){
			dateType = 'BU';
			billType='';
			//客户编码
			customerCode = form.getValues().airagency;
			//客户名称
			customerName = form.findField('airagency').getRawValue();
			//如果为客户，则客户编码不能为空
			if(Ext.isEmpty(customerCode)){
				Ext.Msg.alert(writeoff.statementAdd.i18n('foss.stl.writeoff.common.alert'),writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.airAngencyIsNullWarning'));
				return false;
			}
		//如果为航空公司
		}else if(customerType=='04'){
			dateType = 'BU';
			billType='';
			//客户编码
			customerCode = form.getValues().airline;
			//客户名称
			customerName = form.findField('airline').getRawValue();
			//如果为客户，则客户编码不能为空
			if(Ext.isEmpty(customerCode)){
				Ext.Msg.alert(writeoff.statementAdd.i18n('foss.stl.writeoff.common.alert'),writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.airIsNullWarning'));
				return false;
			}
		}else if(customerType=='05'){
			//客户编码
			customerCode = form.getValues().landStowage;
			//客户名称
			customerName = form.findField('landStowage').getRawValue();
			//客户类型为快递代理类型
			billType='LA';
			//如果为客户，则客户编码不能为空
			if(Ext.isEmpty(customerCode)){
				Ext.Msg.alert(writeoff.statementAdd.i18n('foss.stl.writeoff.common.alert'),writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.landStowageIsNullWarning'));
				return false;
			}
		}
		
		//校验日期
		if(Ext.isEmpty(periodBeginDate)||Ext.isEmpty(periodEndDate)){
			Ext.Msg.alert(writeoff.statementAdd.i18n('foss.stl.writeoff.common.alert'),writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.quryDateIsNullWarning'));
			return false;
		}
		
		if(compareTwoDate>stl.DATELIMITMAXDAYS_WEEK){
			Ext.Msg.alert(writeoff.statementAdd.i18n('foss.stl.writeoff.common.alert'),writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.queryDateMaxLimit'));
			return false;
		}else if(compareTwoDate<1){
			Ext.Msg.alert(writeoff.statementAdd.i18n('foss.stl.writeoff.common.alert'),writeoff.statementAdd.i18n('foss.stl.writeoff.common.dateEndBeforeStatrtWarining'));
			return false;
		}
		
		//选择单据子类型
		var selectBillTypes = form.getValues().billType;
		if(selectBillTypes==undefined){
			Ext.Msg.alert(writeoff.statementAdd.i18n('foss.stl.writeoff.common.alert'),writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.noSelectBillTypeWarning'));
			return false;
		}
		if(selectBillTypes.length==2 && selectBillTypes[0]==writeoff.STATEMENTDETAIL_DEPOSIT_RECEIVED
									 && selectBillTypes[1]==writeoff.STATEMENTDETAIL_ADVANCED_PAYMENT){
			Ext.Msg.alert(writeoff.statementAdd.i18n('foss.stl.writeoff.common.alert'),writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.onlyPreAndAdvWarning'));
			return false;
		}else if(selectBillTypes==writeoff.STATEMENTDETAIL_ADVANCED_PAYMENT){
			Ext.Msg.alert(writeoff.statementAdd.i18n('foss.stl.writeoff.common.alert'),writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.onlyAdvWarning'));
			return false;								
		}
		//ddw
		writeoff.statementAdd.receivableUnified = form.getValues().receivableUnified;
		//清空对账单界面数据
		var baseInfoAdd = Ext.getCmp('Foss_statementbill_BaseInfoAdd_ID');
		var beginBillsFormAdd = Ext.getCmp('Foss_statementbill_BeginBillsFormAdd_ID');
		var currentBillsFormAdd = Ext.getCmp('Foss_statementbill_CurrentBillsFormAdd_ID');
		var statementPreEntryGrid = Ext.getCmp('Foss_statementbill_StatementPreEntryAddGrid_ID');
		var statementEntryAddGrid = Ext.getCmp('Foss_statementbill_StatementEntryAddGrid_ID');
		
		baseInfoAdd.getForm().reset();
		beginBillsFormAdd.getForm().reset(); 
		currentBillsFormAdd.getForm().reset();
		statementPreEntryGrid.store.removeAll();
		statementEntryAddGrid.store.removeAll();
		statementEntryAddGrid.down('toolbar').query('textfield')[0].setValue(null);
		
		Ext.Ajax.request({
			url:writeoff.realPath('queryForStatementMake.action'),
			actionMethods:'post',
			params:{
				'statementOfAccountMakeQueryVo.statementOfAccountMakeQueryDto.customerCode':customerCode,
				'statementOfAccountMakeQueryVo.statementOfAccountMakeQueryDto.customerName':customerName,
				'statementOfAccountMakeQueryVo.statementOfAccountMakeQueryDto.periodBeginDate':form.findField('periodBeginDate').getValue(),
				'statementOfAccountMakeQueryVo.statementOfAccountMakeQueryDto.periodEndDate':form.findField('periodEndDate').getValue(),
				'statementOfAccountMakeQueryVo.statementOfAccountMakeQueryDto.billDetailTypes':form.getValues().billType,
				'statementOfAccountMakeQueryVo.statementOfAccountMakeQueryDto.deliveryCustomerContact':deliveryCustomerContact,
				'statementOfAccountMakeQueryVo.statementOfAccountMakeQueryDto.queryPage':writeoff.STATEMENTQUERYTAB_BYCUSTOMER,
				'statementOfAccountMakeQueryVo.statementOfAccountMakeQueryDto.receivableUnified':writeoff.statementAdd.receivableUnified,
				'statementOfAccountMakeQueryVo.statementOfAccountMakeQueryDto.dateType':dateType,
				'statementOfAccountMakeQueryVo.statementOfAccountMakeQueryDto.billType':billType
			},
			submitEmptyText:false,
			//waitMsg:'查询中，请稍后...',
			success:function(response){
				var result = Ext.decode(response.responseText);	
				writeoff.statementAdd.statementQuerySuccess(result);
			},	
			exception:function(response){
				var result = Ext.decode(response.responseText);	
				Ext.Msg.alert(writeoff.statementAdd.i18n('foss.stl.writeoff.common.alert'),result.message);
			}
		})
	}else{
		Ext.Msg.alert(writeoff.statementAdd.i18n('foss.stl.writeoff.common.alert'),writeoff.statementAdd.i18n('foss.stl.writeoff.common.validateFailAlert'));
		return false;
	}
}
/**
 * 根据单号查询对账单
 */
writeoff.statementAdd.statementQueryByNumbers = function(){
	var form  = this.up('form').getForm();	
	var billDetailNoArray =[];

	//ddw
	writeoff.statementAdd.receivableUnified = form.getValues().receivableUnified;
	var billDetailNos = form.findField('billDetailNos').getValue();
	if(Ext.String.trim(billDetailNos)!=null && Ext.String.trim(billDetailNos)!=''){
		 array = stl.splitToArray(billDetailNos);
		 for(var i=0;i<array.length;i++){
		 	if(Ext.String.trim(array[i])!=''){
		 		billDetailNoArray.push(array[i]);	
		 	}
		 }
		 if(billDetailNoArray.length>10){
		 	Ext.Msg.alert(writeoff.statementAdd.i18n('foss.stl.writeoff.common.alert'),writeoff.statementAdd.i18n('foss.stl.writeoff.common.queryNosLimit'));
		 	return false;
		 }
	}else{
		Ext.Msg.alert(writeoff.statementAdd.i18n('foss.stl.writeoff.common.alert'),writeoff.statementAdd.i18n('foss.stl.writeoff.common.billNosIsNullWarning'));
		return false;
	}

	//选择单据子类型
	var selectBillTypes = this.up('form').getValues().billType;
	if(selectBillTypes==undefined){
		Ext.Msg.alert(writeoff.statementAdd.i18n('foss.stl.writeoff.common.alert'),writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.noSelectBillTypeWarning'));
		return false;
	}
	
	if(selectBillTypes.length==2 && selectBillTypes[0]==writeoff.STATEMENTDETAIL_DEPOSIT_RECEIVED
								 && selectBillTypes[1]==writeoff.STATEMENTDETAIL_ADVANCED_PAYMENT){
		Ext.Msg.alert(writeoff.statementAdd.i18n('foss.stl.writeoff.common.alert'),writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.onlyPreAndAdvWarning'));
		return false;
	}else if(selectBillTypes==writeoff.STATEMENTDETAIL_ADVANCED_PAYMENT){
		Ext.Msg.alert(writeoff.statementAdd.i18n('foss.stl.writeoff.common.alert'),writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.onlyAdvWarning'));
		return false;								
	}
	
	if(form.isValid()){
		
		//清空对账单界面数据
		var baseInfoAdd = Ext.getCmp('Foss_statementbill_BaseInfoAdd_ID');
		var beginBillsFormAdd = Ext.getCmp('Foss_statementbill_BeginBillsFormAdd_ID');
		var currentBillsFormAdd = Ext.getCmp('Foss_statementbill_CurrentBillsFormAdd_ID');
		var statementPreEntryGrid = Ext.getCmp('Foss_statementbill_StatementPreEntryAddGrid_ID');
		var statementEntryAddGrid = Ext.getCmp('Foss_statementbill_StatementEntryAddGrid_ID');
		
		baseInfoAdd.getForm().reset();
		beginBillsFormAdd.getForm().reset(); 
		currentBillsFormAdd.getForm().reset();
		statementPreEntryGrid.store.removeAll();
		statementEntryAddGrid.store.removeAll();
		statementEntryAddGrid.down('toolbar').query('textfield')[0].setValue(null);
		
		Ext.Ajax.request({
			url:writeoff.realPath('queryForStatementMake.action'),
			actionMethods:'post',
			params:{
				'statementOfAccountMakeQueryVo.statementOfAccountMakeQueryDto.billDetailNos':billDetailNoArray,
				'statementOfAccountMakeQueryVo.statementOfAccountMakeQueryDto.queryPage':writeoff.STATEMENTQUERYTAB_BYNUMBER,
				'statementOfAccountMakeQueryVo.statementOfAccountMakeQueryDto.receivableUnified':writeoff.statementAdd.receivableUnified,
				'statementOfAccountMakeQueryVo.statementOfAccountMakeQueryDto.billDetailTypes':selectBillTypes
			},
			submitEmptyText:false,
			success:function(response){
				var result = Ext.decode(response.responseText);	
				writeoff.statementAdd.statementQuerySuccess(result);
			},
			exception:function(response){
				var result = Ext.decode(response.responseText);	
				Ext.Msg.alert(writeoff.statementAdd.i18n('foss.stl.writeoff.common.alert'),result.message);
			}
		});
	}else{
		Ext.Msg.alert(writeoff.statementAdd.i18n('foss.stl.writeoff.common.alert'),writeoff.statementAdd.i18n('foss.stl.writeoff.common.validateFailAlert'));
		return false;
	}
}

/**
 * 根据航空正单号查询对账单
 */
writeoff.statementAdd.statementQueryByAirNumber = function(){
	var form  = this.up('form').getForm();
	var startNumber,endNumber;
	writeoff.statementAdd.receivableUnified = writeoff.RECEIVABLEUNIFORM_N;
	//声明客户编码和客户名称
	var customerCode,customerName;
	if(form.isValid()){
		//起始和结束单号
		startNumber = Ext.String.trim(form.findField('startNumber').getValue());
		endNumber = Ext.String.trim(form.findField('endNumber').getValue());
		
		//获取客户类型
		var customerType =  form.findField('customerType').getValue();
		//如果为空运代理
	 	if(customerType=='03'){
			//客户编码
			customerCode = form.getValues().airagency;
			//客户名称
			customerName = form.findField('airagency').getRawValue();
			//如果为客户，则客户编码不能为空
			if(Ext.isEmpty(customerCode)){
				Ext.Msg.alert(writeoff.statementAdd.i18n('foss.stl.writeoff.common.alert'),writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.airAngencyIsNullWarning'));
				return false;
			}
		//如果为航空公司
		}else if(customerType=='04'){
			//客户编码
			customerCode = form.getValues().airline;
			//客户名称
			customerName = form.findField('airline').getRawValue();
			//如果为客户，则客户编码不能为空
			if(Ext.isEmpty(customerCode)){
				Ext.Msg.alert(writeoff.statementAdd.i18n('foss.stl.writeoff.common.alert'),writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.airIsNullWarning'));
				return false;
			}
		}
		
		//判断起始和结束单号必须相等
		if(startNumber.length!=endNumber.length){
			Ext.Msg.alert(writeoff.statementAdd.i18n('foss.stl.writeoff.common.alert'),writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.digitMustEquals'));
			return false;
		}
		//起始和结束单号必须相同
		if((startNumber.indexOf("DP")>=0 && endNumber.indexOf("DP")==-1)
				||(startNumber.indexOf("DP")==-1 && endNumber.indexOf("DP")>=0)){
			Ext.Msg.alert(writeoff.statementAdd.i18n('foss.stl.writeoff.common.alert'),writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.startWithMustEquals'));
			return false;
		}
		
		//清空对账单界面数据
		var baseInfoAdd = Ext.getCmp('Foss_statementbill_BaseInfoAdd_ID');
		var beginBillsFormAdd = Ext.getCmp('Foss_statementbill_BeginBillsFormAdd_ID');
		var currentBillsFormAdd = Ext.getCmp('Foss_statementbill_CurrentBillsFormAdd_ID');
		var statementPreEntryGrid = Ext.getCmp('Foss_statementbill_StatementPreEntryAddGrid_ID');
		var statementEntryAddGrid = Ext.getCmp('Foss_statementbill_StatementEntryAddGrid_ID');
		//重置 
		baseInfoAdd.getForm().reset();
		beginBillsFormAdd.getForm().reset(); 
		currentBillsFormAdd.getForm().reset();
		statementPreEntryGrid.store.removeAll();
		statementEntryAddGrid.store.removeAll();
		statementEntryAddGrid.down('toolbar').query('textfield')[0].setValue(null);
		
		Ext.Ajax.request({
			url:writeoff.realPath('queryForStatementMake.action'),
			actionMethods:'post',
			params:{
				'statementOfAccountMakeQueryVo.statementOfAccountMakeQueryDto.customerCode':customerCode,
				'statementOfAccountMakeQueryVo.statementOfAccountMakeQueryDto.startNumber':startNumber,
				'statementOfAccountMakeQueryVo.statementOfAccountMakeQueryDto.endNumber':endNumber,
				'statementOfAccountMakeQueryVo.statementOfAccountMakeQueryDto.queryPage':writeoff.STATEMENTQUERYTABTAB_BYAIRNUMBER
			},
			submitEmptyText:false,
			success:function(response){
				var result = Ext.decode(response.responseText);	
				writeoff.statementAdd.statementQuerySuccess(result);
			},	
			exception:function(response){
				var result = Ext.decode(response.responseText);	
				Ext.Msg.alert(writeoff.statementAdd.i18n('foss.stl.writeoff.common.alert'),result.message);
			}
		})
	}else{
		Ext.Msg.alert(writeoff.statementAdd.i18n('foss.stl.writeoff.common.alert'),writeoff.statementAdd.i18n('foss.stl.writeoff.common.validateFailAlert'));
		return false;
	}
}

/**
 * 生成对账单
 */
writeoff.statementAdd.statementSave = function(){
	//声明变量
	var baseInfoAdd,
		beginBillsFormAdd,
		currentBillsFormAdd,
		statementPreEntryGrid,
		statementEntryAddGrid,
		win,
		statementOfAccountMakeQueryResultVo,
		statementOfAccountMakeQueryResultDto,
		statementOfAccount,	
		preEntryGridLength,
		entryGridLength,
		myMask
		;
		
	baseInfoAdd = Ext.getCmp('Foss_statementbill_BaseInfoAdd_ID');
	beginBillsFormAdd = Ext.getCmp('Foss_statementbill_BeginBillsFormAdd_ID');
	currentBillsFormAdd = Ext.getCmp('Foss_statementbill_CurrentBillsFormAdd_ID');
	statementPreEntryGrid = Ext.getCmp('Foss_statementbill_StatementPreEntryAddGrid_ID');
	statementEntryAddGrid = Ext.getCmp('Foss_statementbill_StatementEntryAddGrid_ID');
	//对账单明细表格数据量
	entryGridLength = statementEntryAddGrid.store.data.items.length;
	
	if(entryGridLength==0){
		Ext.Msg.alert(writeoff.statementAdd.i18n('foss.stl.writeoff.common.alert'),writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.currentDataIsNullWarning'));
		return false;
	}
	
	Ext.Msg.confirm(writeoff.statementAdd.i18n('foss.stl.writeoff.common.alert'), writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.isSaveStatement'), function(btn){
		if(btn=='yes'){
			statementOfAccount = Ext.merge(baseInfoAdd.getRecord().data,beginBillsFormAdd.getRecord().data,currentBillsFormAdd.getRecord().data);	
			preEntryGridLength = statementPreEntryGrid.store.data.items.length;
			
			var jsonData = new Array();
			statementEntryAddGrid.store.each(function(record){
				//拼接本期账单数据
				var currentRecord = new Object();
				currentRecord.sourceBillNo = record.get('sourceBillNo');//来源单号
				currentRecord.billParentType = record.get('billParentType');//单据父类型
				currentRecord.unverifyAmount = record.get('unverifyAmount');//未核销金额
				currentRecord.auditStatus = record.get('auditStatus');//审核状态
				currentRecord.versionNo = record.get('versionNo');//版本号
				jsonData.push(currentRecord);
			});
			
			if(writeoff.RECEIVABLEUNIFORM_Y == writeoff.statementAdd.receivableUnified){
				statementOfAccount.unifiedSettlement = writeoff.RECEIVABLEUNIFORM_Y;
			}else{
				statementOfAccount.unifiedSettlement = writeoff.RECEIVABLEUNIFORM_N;
			}
			
			//将前台属性注入到dto中
			statementOfAccountMakeQueryResultDto = new Object();
			statementOfAccountMakeQueryResultDto.statementOfAccountDetailPeriodList=jsonData;
			statementOfAccountMakeQueryResultDto.statementOfAccount =statementOfAccount;
			//将dto注入到后台中
			statementOfAccountMakeQueryResultVo = new Object();
			statementOfAccountMakeQueryResultVo.statementOfAccountMakeQueryResultDto = statementOfAccountMakeQueryResultDto;
			
        	myMask =Ext.getCmp('Foss.statementbill.LoadMaskAdd_ID');
        	if(myMask==null){
        		myMask = new Ext.LoadMask(Ext.getCmp('T_writeoff-statementAdd_content'), {
	        		id:'Foss.statementbill.LoadMaskAdd_ID',
	    			msg:writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.saveStatementMask'),
    			    removeMask : true// 完成后移除
        		});
        	}
			myMask.show();
	        
			Ext.Ajax.request({
				url :writeoff.realPath('addStatement.action'), 
				jsonData:{'statementOfAccountMakeQueryResultVo':statementOfAccountMakeQueryResultVo},
				method:'post',
				success:function(response){
					var result = Ext.decode(response.responseText);	
					myMask.hide();
					//获取弹出窗口
					win = Ext.getCmp('Foss_statementbill_EditStatementEntryWindow_ID');
					if(win==null ){
						win = Ext.create('Foss.statementbill.EditStatementEntryWindow',{
							id:'Foss_statementbill_EditStatementEntryWindow_ID'
						});
					}
					var model = new Foss.statementbill.StatementFormModel(result.statementOfAccountMakeQueryResultVo.statementOfAccountMakeQueryResultDto.statementOfAccount);
					win.items.items[0].loadRecord(model);
					win.items.items[1].loadRecord(model);
					win.items.items[2].loadRecord(model);
					var statementOfAccountDPeriodList = result.statementOfAccountMakeQueryResultVo.statementOfAccountMakeQueryResultDto.statementOfAccountDPeriodList;
//					var statementOfAccountDBeginPeriodList = result.statementOfAccountMakeQueryResultVo.statementOfAccountMakeQueryResultDto.statementOfAccountDBeginPeriodList;
					//控制本期金额图标显示和隐藏
					writeoff.statementUnVerifyAmountShow(model,win.items.items[2],writeoff.PAGEFROM_STATEMENTEDIT);
					//从前台过去往期数据
//					win.items.items[4].store.loadData(statementPreEntryGrid.store.data.items);
					win.items.items[4].store.loadData(statementOfAccountDPeriodList);
					
					win.items.items[4].down('textfield').setValue(statementEntryAddGrid.store.data.length);
					win.show();
					//制作完后清空对账单界面数据
					baseInfoAdd.getForm().reset();
					beginBillsFormAdd.getForm().reset(); 
					currentBillsFormAdd.getForm().reset();
					statementPreEntryGrid.store.removeAll();
					statementEntryAddGrid.store.removeAll();
					statementEntryAddGrid.down('toolbar').query('textfield')[0].setValue(null);
					//对账单明细来源 -- 新增
					writeoff.showEntryType = writeoff.PAGEFROM_STATEMENTADD;
				},
				failure:function(form,action){
					myMask.hide();
				},
				unknownException:function(form,action){
					myMask.hide();
				},
				exception:function(response){
					myMask.hide();
					var result = Ext.decode(response.responseText);	
					Ext.Msg.alert(writeoff.statementAdd.i18n('foss.stl.writeoff.common.alert'),result.message);
				}
			});
		}
	});
}
/** 
 * 添加明细到对账单中
 * @param {} grid 表单
 * @param {} rowIndex 行标
 * @param {} colIndex
 */
writeoff.statementAdd.statementAdd = function(grid, rowIndex, colIndex){
	var statementPreEntryStore = Ext.getCmp('Foss_statementbill_StatementPreEntryAddGrid_ID').store;
	var statementEntryAddGrid = Ext.getCmp('Foss_statementbill_StatementEntryAddGrid_ID');
	var beginBillsForm =  Ext.getCmp('Foss_statementbill_BeginBillsFormAdd_ID');
	var currentBillsForm = Ext.getCmp('Foss_statementbill_CurrentBillsFormAdd_ID'); 
	var baseInfo = Ext.getCmp('Foss_statementbill_BaseInfoAdd_ID');
	var statementEntryStore = statementEntryAddGrid.store;
	var selection = grid.getStore().getAt(rowIndex);
	
	//调用edit.js中的操作方法
	writeoff.statementOperate(baseInfo,beginBillsForm,currentBillsForm,selection,writeoff.STATEMENT_OPERATE_ADD);	
	statementPreEntryStore.remove(selection);
	statementEntryStore.insert(0,selection);
	//给总条数复制
	statementEntryAddGrid.down('toolbar').query('textfield')[0].setValue(statementEntryStore.data.length);		
}

/**
 * 从对账单明细中删除
 * @param {} grid 表单
 * @param {} rowIndex 行标
 * @param {} colIndex 
 */
writeoff.statementAdd.statementRemove = function(grid, rowIndex, colIndex){
	var statementPreEntryStore = Ext.getCmp('Foss_statementbill_StatementPreEntryAddGrid_ID').store;
	var statementEntryAddGrid = Ext.getCmp('Foss_statementbill_StatementEntryAddGrid_ID');
	var baseInfo =Ext.getCmp('Foss_statementbill_BaseInfoAdd_ID');
	var beginBillsForm =  Ext.getCmp('Foss_statementbill_BeginBillsFormAdd_ID');
	var currentBillsForm = Ext.getCmp('Foss_statementbill_CurrentBillsFormAdd_ID'); 
	var statementEntryStore = statementEntryAddGrid.store;
	var selection = grid.getStore().getAt(rowIndex);	
	//此处一定要在修改isFlag之前进行，因为要判断如果已经标记，则删除时候不用调整本期金额
	writeoff.statementOperate(baseInfo,beginBillsForm,currentBillsForm,selection,writeoff.STATEMENT_OPERATE_DELETE);	
	statementEntryStore.remove(selection);
	statementPreEntryStore.insert(0,selection);
	//给总条数复制
	statementEntryAddGrid.down('toolbar').query('textfield')[0].setValue(statementEntryStore.data.length);
}


/**
 * 待进对账单的明细store
 */
Ext.define('Foss.statementbill.StatementPreEntryStore',{
	extend:'Ext.data.Store',
	model:'Foss.statementbill.StatementEntryModel',
	sorters: [{
         property: 'billParentType',
         direction: 'ASC'
     }]
});

/**
 * 对账单明细store
 */
Ext.define('Foss.statementbill.StatementEntryStore',{
	extend:'Ext.data.Store',
	model:'Foss.statementbill.StatementEntryModel',
	sorters: [{
	     property: 'billParentType',
	     direction: 'ASC'
	 }]
});

/**
 * 查询tab
 */
Ext.define('Foss.statementbill.QueryInfoTab',{
	extend:'Ext.tab.Panel',
	frame:false,
	bodyCls: 'autoHeight',
	cls: 'innerTabPanel',
	activeTab:1,//默认显示按单号制作
	height:210,
    items: [{
      	title: writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.makeByCustomer'),
		tabConfig: {
			width: 120
		},
		layout: 'hbox',
        items:[{
	    	xtype:'form',
	    	width:920,
	    	defaults:{
        		labelWidth:75,
        		labelAlign:'left',
        		margin:'5,5,5,5'
        	},
			layout:'column',
		    items:[{
		    	xtype:'combobox',
		    	name:'customerType',
		    	columnWidth:.3,
		    	fieldLabel:writeoff.statementAdd.i18n('foss.stl.writeoff.statementCommon.customerType'),
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
						dateType = this.up('form').items.items[7];
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
							dateType.hide();
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
							dateType.hide();
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
							dateType.hide();
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
							dateType.hide();
						}else if(newValue=='05'){
							landStowage.show();
							dateType.show();
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
	        	fieldLabel:'<span style="color:red;">*</span>'+writeoff.statementAdd.i18n('foss.stl.writeoff.common.airAgencyDetial'),
	        	columnWidth:.3
	        },{
	        	xtype:'commonairlinesselector',
	        	name:'airline',
	        	all:'true',//查询所有(包含作废)
	        	hidden:true,
	        	fieldLabel:'<span style="color:red;">*</span>'+writeoff.statementAdd.i18n('foss.stl.writeoff.common.airDetial'),
	        	columnWidth:.3
	        },{
	        	xtype:'commonvehagencycompselector',
	        	name:'vehagencycomp',
	        	all:'true',//查询所有(包含作废)
	        	hidden:true,
	        	fieldLabel:'<span style="color:red;">*</span>'+writeoff.statementAdd.i18n('foss.stl.writeoff.common.vehAgencyDetial'),
	        	columnWidth:.3
	        },{
	        	xtype:'commoncustomerselector',
	        	listWidth:300,
	        	all:'true',//查询所有(包含作废)
	        	name:'customerCode',
	        	emptyText:'客户编码和手机均可查询',
	        	contcatFlag :'Y',//增加按手机号查询
	        	singlePeopleFlag:'Y',
	        	fieldLabel:'<span style="color:red;">*</span>'+writeoff.statementAdd.i18n('foss.stl.writeoff.common.customerDetial'),
	        	columnWidth:.3
	        },{
	        	xtype:'commonLdpAgencyCompanySelector',
	        	listWidth:300,
	        	labelWidth:80,
	        	name:'landStowage',
	        	hidden:true,
	        	fieldLabel:'<span style="color:red;">*</span>'+writeoff.statementAdd.i18n('foss.stl.writeoff.common.landStowage'),
	        	columnWidth:.3
	        },{
	        	xtype:'textfield',
	        	fieldLabel:writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.deliveryCustomerContact'),
	        	name:'deliveryCustomerContact',
	        	columnWidth: .3
	        },{
				xtype : 'fieldcontainer',
				name:'dateType',
				defaultType : 'radiofield',
				hidden:true,
				columnWidth:1,
				fieldLabel : writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.dateType'),
				layout : 'column',
				items : [{
					boxLabel : writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.businessDate'),
					checked : true,
					name : 'queryDateType',
					inputValue : 'BU'
				}, {
					boxLabel : writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.signDate'),
					name : 'queryDateType',
					inputValue : 'CO'
				}]
			},{
	        	xtype:'datefield',
	        	allowBlank:false,
	        	fieldLabel:writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.accountPeriod'),
	        	name:'periodBeginDate',
	        	columnWidth: .3,
	        	format:'Y-m-d',
	        	value:stl.getTargetDate(new Date(),-stl.DATELIMITDAYS_MONTH)
	        },{
	        	xtype:'datefield',
	        	allowBlank:false,
	        	fieldLabel:writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.to'),
	        	name:'periodEndDate',
	        	format:'Y-m-d',
	        	columnWidth: .3,
	        	value:new Date()
	        },{//ddw
	       	 	xtype: 'fieldcontainer',
	            fieldLabel: writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.receivableUnified'),
	            columnWidth:.3,
	            labelWidth:85,
	            defaultType: 'radiofield',
	            layout:'column',
	            items: [{
	                boxLabel: writeoff.statementAdd.i18n('foss.stl.writeoff.common.yes'),
	                name: 'receivableUnified',
	                inputValue: writeoff.RECEIVABLEUNIFORM_Y
	            },{
	                boxLabel: writeoff.statementAdd.i18n('foss.stl.writeoff.common.no'),
	                name: 'receivableUnified',
	                checked:true,
	                inputValue: writeoff.RECEIVABLEUNIFORM_N
	            }]
       	 	},{
	       	 	xtype: 'fieldcontainer',
	            fieldLabel: writeoff.statementAdd.i18n('foss.stl.writeoff.common.billType'),
				allowBlank:true,
	            columnWidth:1,
	            defaultType: 'checkboxfield',
	            defaults:{
	            	width:60
	            },
	            layout:'hbox',
	            items: [{
	                boxLabel  : writeoff.statementAdd.i18n('foss.stl.writeoff.common.billReceivable'),
	                name      : 'billType',
	                checked:true,
	                inputValue: writeoff.STATEMENTDETAIL_RECEIVABLE
	            },{
	                boxLabel  : writeoff.statementAdd.i18n('foss.stl.writeoff.common.billpayable'),
	                name      : 'billType',
	                checked:true,
	                inputValue: writeoff.STATEMENTDETAIL_PAYABLE
	            },{
	                boxLabel  : writeoff.statementAdd.i18n('foss.stl.writeoff.common.billDepositReceive'),
	                name      : 'billType',
	                checked:true,
	                inputValue: writeoff.STATEMENTDETAIL_DEPOSIT_RECEIVED
	            },{
	                boxLabel  : writeoff.statementAdd.i18n('foss.stl.writeoff.common.billAdvance'),
	                name      : 'billType',
	                checked:true,
	               // hidden:true,//此处如果为空运对账员，则显示
	                inputValue: writeoff.STATEMENTDETAIL_ADVANCED_PAYMENT
	            }]
       	 	},{
				border: 1,
				xtype:'container',
				columnWidth:1,
				defaultType:'button',
				layout:'column',
				items:[{
					text:writeoff.statementAdd.i18n('foss.stl.writeoff.common.reset'),
					columnWidth:.08,
					handler:writeoff.statementReset
				},{
					xtype:'container',
					border:false,
					html:'&nbsp;',
					columnWidth:.44
				},{
					text:writeoff.statementAdd.i18n('foss.stl.writeoff.common.query'),
					cls:'yellow_button',
					columnWidth:.08,
					handler:writeoff.statementAdd.statementQueryByCustomerInfo 
				}]
        	}]
	 	}]
	},{										
        title: writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.makeByNumber'),
        tabConfig:{
        	width:120
        },
        layout:'fit',
        items:[{
	    	xtype:'form',
	    	defaults:{
	        	margin:'5,5,5,5'
	   		 },
	    	layout:'column',
		    items:[{       		
	    		xtype:'textareafield',
	    		fieldLabel:writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.number'),
	    		emptyText:writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.billNosQueryLimit'),
	    		name:'billDetailNos',
	    		allowBlank:false,
	    		columnWidth:.65,
	    		labelWidth:70,
	    		width: 600,
	    		height:110
	  			/*因为要支持航空正单号查询，而正单号没有规则，故而没法用正则校验
	  			 * 
	  			 *  ,regex:/^((YS|YF|US|UF)?[0-9]{7,10}[;,])*((YS|YF|US|UF)?[0-9]{7,10}[;,]?)$/i,
				regexText:writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.billNosQueryRegexText')*/
	        },{
				xtype:'container',
				columnWidth:.35,
				items:[{
					xtype:'component',
					padding:'0 0 0 10',
					autoEl:{
						tag:'div',
						html:'<span style="color:red;">'+writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.billNosDesc')+'</span>'
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
					text:writeoff.statementAdd.i18n('foss.stl.writeoff.common.reset'),
					columnWidth:.075,
					handler:writeoff.statementReset
				},{
		       	 	xtype: 'fieldcontainer',
		            fieldLabel: writeoff.statementAdd.i18n('foss.stl.writeoff.common.billType'),
					allowBlank:true,
		            columnWidth:.4,
		            defaultType: 'checkboxfield',
		            defaults:{
		            	width:60
		            },
		            layout:'hbox',
					 items: [{
		                boxLabel  : writeoff.statementAdd.i18n('foss.stl.writeoff.common.billReceivable'),
		                name      : 'billType',
		                checked:true,
		                inputValue: writeoff.STATEMENTDETAIL_RECEIVABLE
		            },{
		                boxLabel  : writeoff.statementAdd.i18n('foss.stl.writeoff.common.billpayable'),
		                name      : 'billType',
		                checked:true,
		                inputValue: writeoff.STATEMENTDETAIL_PAYABLE
		            },{
		                boxLabel  : writeoff.statementAdd.i18n('foss.stl.writeoff.common.billDepositReceive'),
		                name      : 'billType',
		                checked:true,
		                inputValue: writeoff.STATEMENTDETAIL_DEPOSIT_RECEIVED
		            },{
		                boxLabel  : writeoff.statementAdd.i18n('foss.stl.writeoff.common.billAdvance'),
		                name      : 'billType',
		                checked:true,
		               // hidden:true,//此处如果为空运对账员，则显示
		                inputValue: writeoff.STATEMENTDETAIL_ADVANCED_PAYMENT
		            }]
	       	 	},{//ddw
		       	 	xtype: 'fieldcontainer',
		            fieldLabel: writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.receivableUnified'),
		            columnWidth:.2,
		            labelWidth:90,
		            defaultType: 'radiofield',
		            layout:'column',
		            items: [{
		                boxLabel: writeoff.statementAdd.i18n('foss.stl.writeoff.common.yes'),
		                name: 'receivableUnified',
		                inputValue: writeoff.RECEIVABLEUNIFORM_Y
		            },{
		                boxLabel: writeoff.statementAdd.i18n('foss.stl.writeoff.common.no'),
		                name: 'receivableUnified',
		                checked:true,
		                inputValue: writeoff.RECEIVABLEUNIFORM_N
		            }]
	       	 	},{
					text:writeoff.statementAdd.i18n('foss.stl.writeoff.common.query'),
					cls:'yellow_button',
					columnWidth:.075,
					handler:writeoff.statementAdd.statementQueryByNumbers
				}]
        	}]
	    }]
    },{
      	title: writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.makeByAirNumber'),
		tabConfig: {
			width: 120
		},
		layout: 'hbox',
        items:[{
	    	xtype:'form',
	    	width:920,
	    	defaults:{
        		labelWidth:75,
        		labelAlign:'right',
        		margin:'5,5,5,5'
        	},
			layout:'column',
		    items:[{
		    	xtype:'combobox',
		    	name:'customerType',
		    	columnWidth:.3,
		    	fieldLabel:writeoff.statementAdd.i18n('foss.stl.writeoff.statementCommon.customerType'),
				store:Ext.create('Foss.statementbill.CustomerTypeStore',{
					data:{
						'items':[
							{customerTypeCode:'03',customerTypeName:writeoff.statementAdd.i18n('foss.stl.writeoff.common.airAgencyDetial')},
							{customerTypeCode:'04',customerTypeName:writeoff.statementAdd.i18n('foss.stl.writeoff.common.airDetial')}
						]
					}
				}),
				editable:false,
				queryModel:'local',
				value:'04',
				displayField:'customerTypeName',
				valueField:'customerTypeCode',
		    	listeners:{
					'change': function(th,newValue,oldValue){
						//声明变量
						var form,customerCode,vehAgencyCode,airAgenyCode,airlineName;
						//获取表单
						form= th.up('form').getForm();
						airAgenyCode = form.findField('airagency');
						airlineName = form.findField('airline');
						//如果为全部，则默认客户不可编辑
						if(newValue=='03'){
							airAgenyCode.show();
							airlineName.hide();
						}else if(newValue=='04'){
							airlineName.show();
							airAgenyCode.hide();	
						}
					}
				}
		    },{
	        	xtype:'commonallagentselector',//commonallagentselector
	        	name:'airagency',
	        	all:'true',//查询所有(包含作废)
	        	hidden:true,
	        	fieldLabel:'<span style="color:red;">*</span>'+writeoff.statementAdd.i18n('foss.stl.writeoff.common.airAgencyDetial'),
	        	columnWidth:.3
	        },{
	        	xtype:'commonairlinesselector',
	        	all:'true',//查询所有(包含作废)
	        	name:'airline',
	        	fieldLabel:'<span style="color:red;">*</span>'+writeoff.statementAdd.i18n('foss.stl.writeoff.common.airDetial'),
	        	columnWidth:.3
	        },{
				xtype:'container',
				border:false,
				height:24,
				html:'&nbsp;',
				columnWidth:.3
		    },{
	        	xtype:'textfield',
	        	allowBlank:false,
	        	fieldLabel:writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.startNumber'),
	        	name:'startNumber',
	        	regex:/^((DP)?[0-9]+)$/,
	        	regexText:writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.numberLimitWarning'),
	        	columnWidth: .3
	        },{
	        	xtype:'textfield',
	        	fieldLabel:writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.endNumber'),
	        	allowBlank:false,
	        	name:'endNumber',
	        	regex:/^((DP)?[0-9]+)$/,
	        	regexText:writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.numberLimitWarning'),

	        	columnWidth: .3
	        },{
				xtype:'container',
				border:false,
				height:24,
				html:'&nbsp;',
				columnWidth:.3
		    },{
				border: 1,
				xtype:'container',
				columnWidth:1,
				defaultType:'button',
				layout:'column',
				items:[{
					text:writeoff.statementAdd.i18n('foss.stl.writeoff.common.reset'),
					columnWidth:.08,
					handler:writeoff.statementReset
				},{
					xtype:'container',
					border:false,
					html:'&nbsp;',
					columnWidth:.44
				},{
					text:writeoff.statementAdd.i18n('foss.stl.writeoff.common.query'),
					cls:'yellow_button',
					columnWidth:.08,
					handler:writeoff.statementAdd.statementQueryByAirNumber 
				}]
        	}]
	 	}]
	}]
});

/**
 * 基本对账单信息
 */
Ext.define('Foss.statementbill.BaseInfo',{
	extend:'Ext.form.Panel',
	layout:'column',
	frame:true,
	hidden:true,
	defaultType:'textfield',
	layout:'column',
	defaults:{
		labelWidth:65,
		margin:'5 5 5 10',
		readOnly:true
	},
	items:[{
		fieldLabel:writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.billType'),
		name:'billType',
		xtype:'combo',
		labelWidth:80,
		columnWidth:.22,
		store:FossDataDictionary.getDataDictionaryStore(settlementDict.STATEMENT_OF_ACCOUNT__BILL_TYPE), 
		queryModel:'local',
		displayField:'valueName',
		valueField:'valueCode'
	},{
		xtype:'datefield',
		fieldLabel:writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.createTime'),
		name:'createTime',
		format:'Y-m-d H:i:s',
		columnWidth:.28
	},{
		xtype:'commoncustomerselector',
		listWidth:300,
		fieldLabel:writeoff.statementAdd.i18n('foss.stl.writeoff.common.customerCode'),
		singlePeopleFlag:'Y',
		contcatFlag :'Y',//增加按手机号查询
		name:'customerCode',
		columnWidth:.24
	},{
		fieldLabel:writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.confirmStatus'),
		name:'confirmStatus',
		xtype:'combo',
		store:FossDataDictionary.getDataDictionaryStore(settlementDict.STATEMENT_OF_ACCOUNT__CONFIRM_STATUS), 
		queryModel:'local',
		displayField:'valueName',
		valueField:'valueCode',
		columnWidth:.24,
		value:'N'
	},{
		fieldLabel:writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.periodBeginDate'),
		name:'periodBeginDate',
		xtype:'datefield',
		format:'Y-m-d',
		columnWidth:.22
	},{
		fieldLabel:writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.companyName'),
		name:'companyName',
		columnWidth:.28
	},{
		fieldLabel:writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.companyCode'),
		name:'companyCode',
		hidden:true,
		columnWidth:.28
	},{
		fieldLabel:writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.customerName'),
		name:'customerName',
		labelWidth:95,
		columnWidth:.24
	},{
		fieldLabel:writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.confirmTime'),
		name:'confirmTime',
		xtype:'datefield',
		format:'Y-m-d H:i:s',
		columnWidth:.24
	},{
		fieldLabel:writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.periodEndDate'),
		name:'periodEndDate',
		xtype:'datefield',
		format:'Y-m-d',
		columnWidth:.22
	},{
		fieldLabel:writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.createOrgName'),
		name:'createOrgName',
		columnWidth:.28
	},{
		fieldLabel:writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.createOrgCode'),
		name:'createOrgCode',
		hidden:true,
		columnWidth:.28
	},{
		fieldLabel:writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.statementBillNo'),
		name:'statementBillNo',
		columnWidth:.22
	},{
		fieldLabel:'id',
		name:'id',
		columnWidth:.22,
		hidden:true
	},{
		xtype:'numberfield',
		fieldLabel:'versionNo',
		name:'versionNo',
		columnWidth:.22,
		hidden:true
	},{
		 xtype: 'component',
		 border:true,
	     autoEl: {
	       tag: 'hr'
	     },	
	      /* xtype:'box',
		 autoEl:{ tag:'div',style:'line-height:1px;border:1px solid;color:gray;'},*/
		 columnWidth:1
	},{
		fieldLabel:writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.unifiedCode'),
		labelWidth:85,
		name:'unifiedCode',
		columnWidth:.20
	},{
		xtype:'container',
		columnWidth:.30,
		layout:'vbox',
		items:[{
			xtype:'component',
			padding:'0 0 0 0',
			autoEl:{
				tag:'div',
				html:'<span style="color:red;">'+writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.unifiedCodeNotes')+'</span>'
			}
		}]
    },{
		fieldLabel:writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.companyAccountBankNo'),
		name:'companyAccountBankNo',
		columnWidth:.22
	},{
		xtype:'container',
		columnWidth:.28,
		layout:'vbox',
		items:[{
			xtype:'component',
			padding:'0 0 0 0',
			autoEl:{
				tag:'div',
				html:'<span style="color:red;">'+writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.companyAccountBankNoNotes')+'</span>'
			}
		}]
    },{
		fieldLabel:writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.accountUserName'),
		name:'accountUserName',
		columnWidth:.5
	},{
		fieldLabel:writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.bankBranchName'),
		name:'bankBranchName',
		columnWidth:.48
	},{
		 xtype: 'component',
		 border:true,
	   autoEl: {
	       tag: 'hr'
	   },	
		 columnWidth:1
	},{
		fieldLabel:writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.unpaidAmount'),
		labelWidth:110,
		name:'unpaidAmount',
		xtype:'numberfield',
		decimalPrecision:2,
		columnWidth:.5,
		value:0
	},{
		fieldLabel:writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.settleNum'),
		columnWidth:.48,
		name:'settleNum',
		xtype:'numberfield',
		value:0
	}]
});

/**
 * 期初账单的form
 */
Ext.define('Foss.statementbill.BeginBillsForm',{
	extend:'Ext.form.Panel',
	title:'<span class="x-panel-header-text x-panel-header-text-default-framed">'+writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.periodBeginBills')+'</span>',
	layout:'column',
	collapsible: true,
	animCollapse: true,
	defaultType:'displayfield',
	defaults:{
		labelWidth:5,
		readOnly:true
	},
	items:[{
		value:writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.beginPeriodAmount'),
		columnWidth:.15,
		style:'margin-left:30px;margin-top:10px;'
	},{
		value:'=',
		style:'margin-top:10px;',
		columnWidth:.05
	},{
		value:writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.beginPeriodRecAmount'),
		style:'margin-top:10px;',
		columnWidth:.15
	},{
		value:'-',
		style:'margin-top:10px;',
		columnWidth:.05
	},{
		value:writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.beginPeriodPayAmount'),
		style:'margin-top:10px;',
		columnWidth:.15
	},{
		value:'+',
		style:'margin-top:10px;',
		columnWidth:.05
	},{
		value:writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.beginPeriodAdvAmount'),
		style:'margin-top:10px;',
		columnWidth:.15
	},{
		value:'-',
		style:'margin-top:10px;',
		columnWidth:.05
	},{
		value:writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.beginPeriodPreAmount'),
		style:'margin-top:10px;',
		columnWidth:.15
	},{
		 xtype: 'component',
		 style:'margin-left:30px;margin-top:10px;',
		 border:true,
	     autoEl: {
	       tag: 'hr'
	     },	
		 columnWidth:1
	},{
		name:'beginPeriodAmount',
		xtype:'numberfield',
		columnWidth:.2,
		style:'margin-left:30px;'
	},{
		name:'beginPeriodRecAmount',
		xtype:'numberfield',
		columnWidth:.2
	},{
		name:'beginPeriodPayAmount',
		xtype:'numberfield',
		columnWidth:.2
	},{
		name:'beginPeriodAdvAmount',
		xtype:'numberfield',
		columnWidth:.2
	},{
		name:'beginPeriodPreAmount',
		xtype:'numberfield',
		columnWidth:.2
	}]
});

/**
 * 本期账单的form
 */
Ext.define('Foss.statementbill.CurrentBillsForm',{
	extend:'Ext.form.Panel',
	title:'<span class="x-panel-header-text x-panel-header-text-default-framed">'+writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.periodCurrentBills')+'</span>',
	layout:'column',
	defaultType:'displayfield',
	defaults:{
		labelWidth:5,
		readOnly:true
	},
	items:[{
		value:writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.periodAmount'),
		columnWidth:.15,
		style:'margin-left:30px;margin-top:10px;'
	},{
		value:'=',
		style:'margin-top:10px;',
		columnWidth:.05
	},{
		value:writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.periodRecAmount'),
		style:'margin-top:10px;',
		columnWidth:.15
	},{
		value:'-',
		style:'margin-top:10px;',
		columnWidth:.05
	},{
		value:writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.periodPayAmount'),
		style:'margin-top:10px;',
		columnWidth:.15
	},{
		value:'+',
		style:'margin-top:10px;',
		columnWidth:.05
	},{
		value:writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.periodAdvAmount'),
		style:'margin-top:10px;',
		columnWidth:.15
	},{
		value:'-',
		style:'margin-top:10px;',
		columnWidth:.05
	},{
		value:writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.periodPreAmount'),
		style:'margin-top:10px;',
		columnWidth:.15
	},{
		 xtype: 'component',
		 border:true,
		 style:'margin-left:30px;margin-top:10px;',
	     autoEl: {
	       tag: 'hr'
	     },	
		 columnWidth:1
	},{
		name:'periodAmount',
		columnWidth:.2,
		xtype:'numberfield',
		style:'margin-left:30px;'
	},{
		name:'periodRecAmount',
		columnWidth:.2,
		xtype:'numberfield'
	},{
		name:'periodPayAmount',
		columnWidth:.2,
		xtype:'numberfield'
	},{
		name:'periodAdvAmount',
		columnWidth:.2,
		xtype:'numberfield'
	},{
		name:'periodPreAmount',
		columnWidth:.2,
		xtype:'numberfield'
	},{
		xtype:'label',
		height:24,
		hidden:true,
		cls:'statementBill_total',
		text:'  ',
		style:'margin-left:30px;',
		columnWidth:.065
	},{
		xtype:'numberfield',
		name:'periodUnverifyRecAmount',
		fieldLabel:writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.Ar'),
		labelWidth:40,
		style:'margin-top:5px;',
		hidden:true,
		columnWidth:.15
	},{
		xtype:'numberfield',
		name:'periodUnverifyPayAmount',
		fieldLabel:writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.Ap'),
		labelWidth:40,
		style:'margin-top:5px;',
		hidden:true,
		columnWidth:.15
	},{
		xtype:'numberfield',
		name:'periodUnverifyPreAmount',
		fieldLabel:writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.Pre'),
		style:'margin-top:5px;',
		labelWidth:40,
		hidden:true,
		columnWidth:.15
	},{
		xtype:'numberfield',
		name:'periodUnverifyAdvAmount',
		fieldLabel:writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.Adv'),
		style:'margin-top:5px;',
		labelWidth:40,
		hidden:true,
		columnWidth:.15
	}]
});

/**
 *账期信息 （将本期和往期包在一起）
 */
Ext.define('Foss.statementbill.PaymentDayPanel',{
	extend:'Ext.panel.Panel',
	hidden:true,
	frame:true,
	bodyCls: 'autoHeight',
	cls: 'autoHeight',
	items:[
		 Ext.create('Foss.statementbill.BeginBillsForm',{id:'Foss_statementbill_BeginBillsFormAdd_ID'}),
		 Ext.create('Foss.statementbill.CurrentBillsForm',{id:'Foss_statementbill_CurrentBillsFormAdd_ID'})
	]
});

/**
 * 待进对账单明细记录
 */
Ext.define('Foss.statementbill.StatementPreEntryAddGrid', {
	extend:'Ext.grid.Panel',
	collapsible: true,
	collapsed : true,
	animCollapse: true,
    title:writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.periodBeginEntry'),
    height:280,
    frame:true,
    hidden:true,
    bodyCls: 'autoHeight',
	cls: 'autoHeight',
    store: Ext.create('Foss.statementbill.StatementPreEntryStore'),
    viewConfig:{
  		enableTextSelection : true//设置行可以选择，进而可以复制
  	},
    columns: [{ 
   		xtype:'actioncolumn',
		width:73,
		text: writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.actionColumn'),
		align: 'center',
		items:[{
			iconCls : 'statementBill_add',
			tooltip:writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.addToEntry'),
			handler:function(grid, rowIndex, colIndex){
				writeoff.statementAdd.statementAdd(grid, rowIndex, colIndex)	
			}
		}]
	},{
		header:writeoff.statementAdd.i18n('foss.stl.writeoff.common.businessDate'),
		dataIndex:'businessDate',
		renderer:function(value){
			if(value!=null){
				return Ext.Date.format(new Date(value), 'Y-m-d');
			}else{
				return null;
			}
		} 
	},{
		header: writeoff.statementAdd.i18n('foss.stl.writeoff.common.waybillNo'),
		dataIndex: 'waybillNo'
	},{
		header:writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.arrvRegionCode'),
		dataIndex:'arrvRegionCode'
	},{
		header: writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.productCode'),
		dataIndex:'productCode',
		renderer:function(value){
			return Foss.pkp.ProductData.rendererSubmitToDisplay(value);
		}
	},{
		header:writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.deliveryCustomerName'),
		dataIndex:'deliveryCustomerName'
	},{
		header:writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.qty'),
		dataIndex:'qty'
	},{
		header:writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.billWeight'),
		dataIndex:'billWeight'
	},{
		header:writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.billingVolume'),
		dataIndex:'billingVolume'
	},{
		header:writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.insuranceFee'),
		dataIndex:'insuranceFee'
	},{
		header:writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.deliverFee'),
		dataIndex:'deliverFee'
	},{
		header:writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.packagingFee'),
		dataIndex:'packagingFee'	
	},{
		header:writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.otherFee'),
		dataIndex:'otherFee'
	},{
		header:writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.receiveMethod'),
		dataIndex:'receiveMethod',
		renderer:function(value){
			//先去汽运提货方式词条中拿
			var displayField = FossDataDictionary. rendererSubmitToDisplay (value,settlementDict.PICKUP_GOODS );
			//如果汽运提货方式没拿到，则去空运词条中拿
			if(value==displayField){
				displayField = FossDataDictionary. rendererSubmitToDisplay (value,settlementDict.PICKUP_GOODS_AIR);
			}
			return displayField;
		}
	},{
		header: writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.paymentType'),
		dataIndex:'paymentType',
		renderer:function(value){
			var displayField = FossDataDictionary. rendererSubmitToDisplay (value,settlementDict.SETTLEMENT__PAYMENT_TYPE);
			return displayField;
		}
	},{
		header: writeoff.statementAdd.i18n('foss.stl.writeoff.common.originalUnverifyAmount'),
		dataIndex:'unverifyAmount',
		renderer:function(v,m,record){
			if(record.get('billParentType')==writeoff.STATEMENTDETAIL_PAYABLE||record.get('billParentType')==writeoff.STATEMENTDETAIL_DEPOSIT_RECEIVED){
					return '<span style="color:red">'+v+'</span>';  
			}else{
				return v;	
			}	
		}
	},{
		header: writeoff.statementAdd.i18n('foss.stl.writeoff.common.totalAmount'),
		dataIndex: 'amount',
		renderer:function(v,m,record){
			if(record.get('billParentType')==writeoff.STATEMENTDETAIL_PAYABLE||record.get('billParentType')==writeoff.STATEMENTDETAIL_DEPOSIT_RECEIVED){
					return '<span style="color:red">'+v+'</span>';  
			}else{
				return v;	
			}	
		}
	},{
		header:writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.number'),
		dataIndex:'sourceBillNo'
	},{
		header:writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.deliveryCustomerContact'),
		dataIndex:'deliveryCustomerContact'
	},{
		header: writeoff.statementAdd.i18n('foss.stl.writeoff.common.origSourceBillNo'),
		dataIndex: 'origSourceBillNo'
	},{
		header:writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.billParentType'),
		dataIndex:'billParentType',
		renderer:function(value){
			var displayField = FossDataDictionary. rendererSubmitToDisplay (value,settlementDict.STATEMENT_OF_ACCOUNT_D__BILL_PARENT_TYPE);
    		return displayField;
		}
	},{
		header:writeoff.statementAdd.i18n('foss.stl.writeoff.common.billType'),
		dataIndex:'billType',
		renderer:function(v,m,record){
			return writeoff.statementFormatBillType(v,record);
		}
	},{
		header:writeoff.statementAdd.i18n('foss.stl.writeoff.common.customerCode'),
		dataIndex:'customerCode'
	},{
		header:writeoff.statementAdd.i18n('foss.stl.writeoff.common.customerName'),
		dataIndex:'customerName'
	},{
		header: writeoff.statementAdd.i18n('foss.stl.writeoff.common.verifyAmount'),
		dataIndex:'verifyAmount',
		hidden:true,
		renderer:function(v,m,record){
			if(record.get('billParentType')==writeoff.STATEMENTDETAIL_PAYABLE||record.get('billParentType')==writeoff.STATEMENTDETAIL_DEPOSIT_RECEIVED){
					return '<span style="color:red">'+v+'</span>';  
			}else{
				return v;	
			}	
		}
	},{
		header:writeoff.statementAdd.i18n('foss.stl.writeoff.common.orgCode'),
		dataIndex:'orgCode'
	},{
		header:writeoff.statementAdd.i18n('foss.stl.writeoff.common.orgName'),
		dataIndex:'orgName'
	},{
		header:writeoff.statementAdd.i18n('foss.stl.writeoff.common.accountDate'),
		dataIndex:'accountDate',
		renderer:function(value){
			if(value!=null){
				return Ext.Date.format(new Date(value), 'Y-m-d');
			}else{
				return null;
			}
		} 
	},{
		header:writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.customerPickupOrgName'),
		dataIndex:'customerPickupOrgName'
	},{
		header:writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.receiveCustomerName'),
		dataIndex:'receiveCustomerName'
	},{
		header:writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.receiveCustomerCode'),
		dataIndex:'receiveCustomerCode'
	},{
		header:writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.goodsName'),
		dataIndex:'goodsName'
	},{
		header:writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.transportFee'),
		dataIndex:'transportFee'
	},{
		header:writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.pickupFee'),
		dataIndex:'pickupFee'
	},{
		header:writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.codFee'),
		dataIndex:'codFee'
	},{
		header:writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.valueAddFee'),
		dataIndex:'valueAddFee'
	},{
		header:writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.promotionsFee'),
		dataIndex:'promotionsFee'
	},{
		header:writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.origOrgCode'),
		dataIndex:'origOrgCode'
	},{
		header:writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.origOrgName'),
		dataIndex:'origOrgName'
	},{
		header:writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.destOrgName'),
		dataIndex:'destOrgName'
	},{
		header:writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.destOrgCode'),
		dataIndex:'destOrgCode'
	}
//	,{
//		header:writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.deptRegionCode'),
//		dataIndex:'deptRegionCode'
//	}
	,
	{
		header:writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.deliveryCustomerCode'),
		dataIndex:'deliveryCustomerCode'
	},{
		header:writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.signDate'),
		dataIndex:'signDate',
		renderer:function(value){
			if(value!=null){
				return Ext.Date.format(new Date(value), 'Y-m-d');
			}else{
				return null;
			}
		} 
	},{
		header:writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.auditStatus'),
		dataIndex:'auditStatus',
		renderer:function(value){
			var displayField = FossDataDictionary. rendererSubmitToDisplay (value,settlementDict.BILL_RECEIVABLE__APPROVE_STATUS);
			return displayField;
		}
	},{
		header: writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.unifiedSettlement'),
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
		header:writeoff.statementAdd.i18n('foss.stl.writeoff.common.notes'),
		dataIndex:'notes',
		hidden:true
	},{
		header:writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.isDelete'),
		dataIndex:'isDelete',
		hidden:true
	},{
		header:writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.statementBillNo'),
		dataIndex:'statementBillNo',
		hidden:true
	},{
		header:writeoff.statementAdd.i18n('foss.stl.writeoff.common.disableTime'),
		dataIndex:'disableTime',
		hidden:true,
		renderer:function(value){
			if(value!=null){
				return Ext.Date.format(new Date(value), 'Y-m-d');
			}else{
				return null;
			}
		} 
	},{
		header:writeoff.statementAdd.i18n('foss.stl.writeoff.common.createTime'),
		dataIndex:'createTime',
		hidden:true,
		renderer:function(value){
			if(value!=null){
				return Ext.Date.format(new Date(value), 'Y-m-d');
			}else{
				return null;
			}
		} 
	},{
		header:'id',
		dataIndex:'id',
		hidden:true
	}]
});

/**
 * 对账单明细记录
 */
Ext.define('Foss.statementbill.StatementEntryAddGrid', {
	extend:'Ext.grid.Panel',
	title:writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.periodCurrentEntry'),
    hidden:true,
    frame:true,
    height:600,
    store:Ext.create('Foss.statementbill.StatementEntryStore',{id:'Foss_StatementBill_StatementEntryStore_ID'}),
    bodyCls: 'autoHeight',
	cls: 'autoHeight',
  	defaults:{
  		align:'center',
  		margin:'5 0 5 0'
  	},
  	viewConfig:{
  		enableTextSelection : true//设置行可以选择，进而可以复制
  	},
  	columns: [{
    	xtype:'actioncolumn',
    	header:writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.actionColumn'),
    	width:73,
    	align: 'center',
    	items:[{
			iconCls : 'statementBill_remove',
				tooltip:writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.delete'),
				handler:function(grid, rowIndex, colIndex){
					writeoff.statementAdd.statementRemove(grid, rowIndex, colIndex)
				}
    	}]
    },{
		header:writeoff.statementAdd.i18n('foss.stl.writeoff.common.businessDate'),
		dataIndex:'businessDate',
		renderer:function(value){
			if(value!=null){
				return Ext.Date.format(new Date(value), 'Y-m-d');
			}else{
				return null;
			}
		} 
	},{
		header: writeoff.statementAdd.i18n('foss.stl.writeoff.common.waybillNo'),
		dataIndex: 'waybillNo'
	},{
		header:writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.arrvRegionCode'),
		dataIndex:'arrvRegionCode'
	},{
		header: writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.productCode'),
		dataIndex:'productCode',
		renderer:function(value){
			return Foss.pkp.ProductData.rendererSubmitToDisplay(value);
		}
	},{
		header:writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.deliveryCustomerName'),
		dataIndex:'deliveryCustomerName'
	},{
		header:writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.unitPrice'),
		dataIndex:'unitPrice'
	},{
		header:writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.insuranceAmount'),
		dataIndex:'insuranceAmount'
	},{
		header:writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.qty'),
		dataIndex:'qty'
	},{
		header:writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.billWeight'),
		dataIndex:'billWeight'
	},{
		header:writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.billingVolume'),
		dataIndex:'billingVolume'
	},{
		header:writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.insuranceFee'),
		dataIndex:'insuranceFee'
	},{
		header:writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.deliverFee'),
		dataIndex:'deliverFee'
	},{
		header:writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.packagingFee'),
		dataIndex:'packagingFee'	
	},{
		header:writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.otherFee'),
		dataIndex:'otherFee'
	},{
		header:writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.receiveMethod'),
		dataIndex:'receiveMethod',
		renderer:function(value){
			//先去汽运提货方式词条中拿
			var displayField = FossDataDictionary. rendererSubmitToDisplay (value,settlementDict.PICKUP_GOODS );
			//如果汽运提货方式没拿到，则去空运词条中拿
			if(value==displayField){
				displayField = FossDataDictionary. rendererSubmitToDisplay (value,settlementDict.PICKUP_GOODS_AIR);
			}
			return displayField;
		}
	},{
		header: writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.paymentType'),
		dataIndex:'paymentType',
		renderer:function(value){
			var displayField = FossDataDictionary. rendererSubmitToDisplay (value,settlementDict.SETTLEMENT__PAYMENT_TYPE);
			return displayField;
		}
	},{
		header: writeoff.statementAdd.i18n('foss.stl.writeoff.common.originalUnverifyAmount'),
		dataIndex:'unverifyAmount',
		renderer:function(v,m,record){
			if(record.get('billParentType')==writeoff.STATEMENTDETAIL_PAYABLE||record.get('billParentType')==writeoff.STATEMENTDETAIL_DEPOSIT_RECEIVED){
					return '<span style="color:red">'+v+'</span>';  
			}else{
				return v;	
			}	
		}
	},{
		header: writeoff.statementAdd.i18n('foss.stl.writeoff.common.totalAmount'),
		dataIndex: 'amount',
		renderer:function(v,m,record){
			if(record.get('billParentType')==writeoff.STATEMENTDETAIL_PAYABLE||record.get('billParentType')==writeoff.STATEMENTDETAIL_DEPOSIT_RECEIVED){
					return '<span style="color:red">'+v+'</span>';  
			}else{
				return v;	
			}	
		}
	},{
		header:writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.number'),
		dataIndex:'sourceBillNo'
	},{
		header:writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.deliveryCustomerContact'),
		dataIndex:'deliveryCustomerContact'
	},{
		header: writeoff.statementAdd.i18n('foss.stl.writeoff.common.origSourceBillNo'),
		dataIndex: 'origSourceBillNo'
	},{
		header:writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.billParentType'),
		dataIndex:'billParentType',
		renderer:function(value){
			var displayField = FossDataDictionary. rendererSubmitToDisplay (value,settlementDict.STATEMENT_OF_ACCOUNT_D__BILL_PARENT_TYPE);
    		return displayField;
		}
	},{
		header:writeoff.statementAdd.i18n('foss.stl.writeoff.common.billType'),
		dataIndex:'billType',
		renderer:function(v,m,record){
			return writeoff.statementFormatBillType(v,record);
		}
	},{
		header:writeoff.statementAdd.i18n('foss.stl.writeoff.common.customerCode'),
		dataIndex:'customerCode'
	},{
		header:writeoff.statementAdd.i18n('foss.stl.writeoff.common.customerName'),
		dataIndex:'customerName'
	},{
		header: writeoff.statementAdd.i18n('foss.stl.writeoff.common.verifyAmount'),
		dataIndex:'verifyAmount',
		hidden:true,
		renderer:function(v,m,record){
			if(record.get('billParentType')==writeoff.STATEMENTDETAIL_PAYABLE||record.get('billParentType')==writeoff.STATEMENTDETAIL_DEPOSIT_RECEIVED){
					return '<span style="color:red">'+v+'</span>';  
			}else{
				return v;	
			}	
		}
	},{
		header:writeoff.statementAdd.i18n('foss.stl.writeoff.common.orgCode'),
		dataIndex:'orgCode'
	},{
		header:writeoff.statementAdd.i18n('foss.stl.writeoff.common.orgName'),
		dataIndex:'orgName'
	},{
		header:writeoff.statementAdd.i18n('foss.stl.writeoff.common.accountDate'),
		dataIndex:'accountDate',
		renderer:function(value){
			if(value!=null){
				return Ext.Date.format(new Date(value), 'Y-m-d');
			}else{
				return null;
			}
		} 
	},{
		header:writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.customerPickupOrgName'),
		dataIndex:'customerPickupOrgName'
	},{
		header:writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.receiveCustomerName'),
		dataIndex:'receiveCustomerName'
	},{
		header:writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.receiveCustomerCode'),
		dataIndex:'receiveCustomerCode'
	},{
		header:writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.goodsName'),
		dataIndex:'goodsName'
	},{
		header:writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.transportFee'),
		dataIndex:'transportFee'
	},{
		header:writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.pickupFee'),
		dataIndex:'pickupFee'
	},{
		header:writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.codFee'),
		dataIndex:'codFee'
	},{
		header:writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.valueAddFee'),
		dataIndex:'valueAddFee'
	},{
		header:writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.promotionsFee'),
		dataIndex:'promotionsFee'
	},{
		header:writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.origOrgCode'),
		dataIndex:'origOrgCode'
	},{
		header:writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.origOrgName'),
		dataIndex:'origOrgName'
	},{
		header:writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.destOrgName'),
		dataIndex:'destOrgName'
	},{
		header:writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.destOrgCode'),
		dataIndex:'destOrgCode'
	}
//	,{
//		header:writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.deptRegionCode'),
//		dataIndex:'deptRegionCode'
//	}
	,
	{
		header:writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.deliveryCustomerCode'),
		dataIndex:'deliveryCustomerCode'
	},{
		header:writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.signDate'),
		dataIndex:'signDate',
		renderer:function(value){
			if(value!=null){
				return Ext.Date.format(new Date(value), 'Y-m-d');
			}else{
				return null;
			}
		} 
	},{
		header:writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.auditStatus'),
		dataIndex:'auditStatus',
		renderer:function(value){
			var displayField = FossDataDictionary. rendererSubmitToDisplay (value,settlementDict.BILL_RECEIVABLE__APPROVE_STATUS);
			return displayField;
		}
	},{
		header: writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.unifiedSettlement'),
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
		header:writeoff.statementAdd.i18n('foss.stl.writeoff.common.notes'),
		dataIndex:'notes',
		hidden:true
	},{
		header:writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.isDelete'),
		dataIndex:'isDelete',
		hidden:true
	},{
		header:writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.statementBillNo'),
		dataIndex:'statementBillNo',
		hidden:true
	},{
		header:writeoff.statementAdd.i18n('foss.stl.writeoff.common.disableTime'),
		dataIndex:'disableTime',
		hidden:true,
		renderer:function(value){
			if(value!=null){
				return Ext.Date.format(new Date(value), 'Y-m-d');
			}else{
				return null;
			}
		} 
	},{
		header:writeoff.statementAdd.i18n('foss.stl.writeoff.common.createTime'),
		dataIndex:'createTime',
		hidden:true,
		renderer:function(value){
			if(value!=null){
				return Ext.Date.format(new Date(value), 'Y-m-d');
			}else{
				return null;
			}
		} 
	},{
		header:'id',
		dataIndex:'id',
		hidden:true
	}]
});

/**
 * 对账单页面入口
 */
Ext.onReady(function() {
	Ext.QuickTips.init();
	//创建查询页签面板
	var queryInfoTab = Ext.create('Foss.statementbill.QueryInfoTab');
	//创建基本信息面板
	var baseInfo = Ext.create('Foss.statementbill.BaseInfo',{id:'Foss_statementbill_BaseInfoAdd_ID'});
	//创建账期信息面板
	var paymentDayPanel = Ext.create('Foss.statementbill.PaymentDayPanel',{id:'Foss_statementbill_PaymentDayPanel_ID'})
	//创建期初信息面板
	var statementPreEntryAddGrid =  Ext.create('Foss.statementbill.StatementPreEntryAddGrid',{
										id:'Foss_statementbill_StatementPreEntryAddGrid_ID'
									});
	//创建本期信息面板
	var statementEntryAddGrid =  Ext.create('Foss.statementbill.StatementEntryAddGrid',{
									id:'Foss_statementbill_StatementEntryAddGrid_ID',
									dockedItems: [{
									    xtype: 'toolbar',
									    dock: 'bottom',
									    layout:'column',
									    items: [{
											xtype:'textfield',
											readOnly:true,
											name:'totalRows',
											columnWidth:.9,
											labelWidth:60,
											fieldLabel:writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.totalCount')
										},{
											xtype:'container',
											border:false,
											html:'&nbsp;',
											columnWidth:.9
										},{
											xtype:'button',
											text:writeoff.statementAdd.i18n('foss.stl.writeoff.statementAdd.createStatement'),
											columnWidth:.1,
											handler:writeoff.statementAdd.statementSave,
											hidden:!writeoff.statementAdd.isPermission('/stl-web/writeoff/addStatement.action')
										}]
									}]
								 });
	//创建整个对账单面板，将上面几块包含在一起
	Ext.create('Ext.panel.Panel',{
		id: 'T_writeoff-statementAdd_content',
		cls: "panelContentNToolbar",
		bodyCls: 'panelContentNToolbar-body',
		layout: 'auto',
		items: [queryInfoTab,baseInfo,paymentDayPanel,statementPreEntryAddGrid,statementEntryAddGrid],
		renderTo: 'T_writeoff-statementAdd-body'
	});
});