/**
 *应收单、应付单查询日期限制 
 */
writeoff.recWriteoffPay.STLBILLRECWRITEOFFBILLPAY_MAX = 31;  //时间间隔最大不超过90天
writeoff.recWriteoffPay.STLBILLRECWRITEOFFBILLPAY_ONEMONTH = 1;  //时间相差一月
writeoff.recWriteoffPay.MAXSHOWNUM = 1000;  //默认最大显示条数
writeoff.recWriteoffPay.COLUMNWIDTH=.3;//按日期查询，列宽
writeoff.recWriteoffPay.BNCOLUMNWIDTH=.08;//按钮之间的宽距
writeoff.recWriteoffPay.CONWIDTH=.74;

//(应收冲应付)按核销日日期查询
writeoff.recWriteoffPay.queryRecWriteoffPay_TD='TD';

//(应收冲应付)按应收单号、应付单号查询
writeoff.recWriteoffPay.queryRecWriteoffPay_RP='RP';

//格式化金额
writeoff.convertAmount=function setMoneyFormat(val){
	return "" + Ext.util.Format.currency(val,' ',2,false) + "";
} 


//最大显示条数：model
Ext.define('Foss.writeoff.recWriteoffPay.ShowRecordCountModel',{
	extend:'Ext.data.Model',
	fields:[{
		name:'name'
	},{
		name:'value',
		type:'int'
	}]
});


//最大显示条数：store
Ext.define('Foss.writeoff.recWriteoffPay.ShowRecordCountStore',{
	extend:'Ext.data.Store',
	model:'Foss.writeoff.recWriteoffPay.ShowRecordCountModel',
	data:{
		'items':[
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



//客户类型：Model
Ext.define('Foss.writeoff.recWriteoffPay.CustomerTypeModel',{
	extend:'Ext.data.Model',
	fields:[{
		name:'customerTypeCode'
	},{
		name:'customerTypeName'
	}]
	
});

//客户类型：store
Ext.define('Foss.writeoff.recWriteoffPay.CustomerTypeStore',{
	extend:'Ext.data.Store',
	model:'Foss.writeoff.recWriteoffPay.CustomerTypeModel',
	data:{
		'items':[
			{customerTypeCode:'01',customerTypeName:writeoff.recWriteoffPay.i18n('foss.stl.writeoff.receivableWriteOffPayble.customer')},
			{customerTypeCode:'02',customerTypeName:writeoff.recWriteoffPay.i18n('foss.stl.writeoff.receivableWriteOffPayble.airAgency')},
			{customerTypeCode:'03',customerTypeName:writeoff.recWriteoffPay.i18n('foss.stl.writeoff.receivableWriteOffPayble.vehAgency')},
			{customerTypeCode:'04',customerTypeName:writeoff.recWriteoffPay.i18n('foss.stl.writeoff.receivableWriteOffPayble.airline')},
			{customerTypeCode:'05',customerTypeName:writeoff.recWriteoffPay.i18n('foss.stl.writeoff.receivableWriteOffPayble.landStowage')},
			{customerTypeCode:'06',customerTypeName:writeoff.recWriteoffPay.i18n('foss.stl.writeoff.receivableWriteOffPayble.packagingSupplier')},
			{customerTypeCode:'07',customerTypeName:writeoff.recWriteoffPay.i18n('foss.stl.writeoff.receivableWriteOffPayble.homesupply')},
			{customerTypeCode:'08',customerTypeName:writeoff.recWriteoffPay.i18n('foss.stl.writeoff.receivableWriteOffPayble.partner')}
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



//应收单按日期查询
writeoff.recWriteoffPay.queryWriteoffRecAndPay=function(f,me,queryType){
	var form=f.getForm();
	if(!form.isValid()){
		Ext.Msg.alert(writeoff.recWriteoffPay.i18n('foss.stl.writeoff.common.alert'),writeoff.recWriteoffPay.i18n('foss.stl.writeoff.common.validateFailAlert'));
		return false;
	}
	var params=writeoff.recWriteoffPay.queryWriteoffRecAndPayParam(form,queryType);
	if(null==params || params==false){
		return;
	}
	//设置该按钮灰掉
	me.disable(false);
	//30秒后自动解除灰掉效果
	setTimeout(function() {
		me.enable(true);
	}, 30000);
	if(writeoff.recWriteoffPay.queryRecWriteoffPay_TD==queryType){
		Ext.Ajax.request({
			url:writeoff.realPath('queryForBillReceivableAndBillPayable.action'),
			params:params,
			actionMethods:'post',
			submitEmptyText:false,
			
			success:function(response){
				var result = Ext.decode(response.responseText);
				var billReceivableEntryStore = Ext.getCmp('Foss_writeoff_StlreceivableQueryInfoGrid_Id').store;
				var billPayableEntityStore = Ext.getCmp('Foss_writeoff_StlpayableQueryInfoGrid_Id').store;
				
				var billReceivableLength = 0;
				if(!Ext.isEmpty(result.billRecWriteoffBillPayResultVo.billReceivableEntityList)){
					billReceivableLength = result.billRecWriteoffBillPayResultVo.billReceivableEntityList.length;
				}
				
				var billPayableLength = 0;
				if(!Ext.isEmpty(result.billRecWriteoffBillPayResultVo.billPayableEntityList)){
					billPayableLength = result.billRecWriteoffBillPayResultVo.billPayableEntityList;
				}
				
				//判断是否有数据提示
				if(billReceivableLength.length ==0&&billPayableLength.length>0){
					
					Ext.Msg.alert(writeoff.recWriteoffPay.i18n('foss.stl.writeoff.common.alert'),writeoff.recWriteoffPay.i18n('foss.stl.writeoff.receivableWriteOffPayble.notFoundReceivableWarning'),function(){
						Ext.getCmp('Foss_writeoff_StlreceivableQueryInfoGrid_Id').hide();
					});
					
					billPayableEntityStore.loadData(result.billRecWriteoffBillPayResultVo.billPayableEntityList);
					var payTotalAmount=writeoff.convertAmount(result.billRecWriteoffBillPayResultVo.payTotalAmount);
					var payUnverifyTotalAmount =writeoff.convertAmount(result.billRecWriteoffBillPayResultVo.payUnverifyTotalAmount);
					var payableVerifyAmount=writeoff.convertAmount(result.billRecWriteoffBillPayResultVo.payVerifyTotalAmount);
					
					Ext.getCmp('Foss_writeoff_StlpayableQueryInfoGrid_payableTotalAmount_Id').setValue(payTotalAmount);
					Ext.getCmp('Foss_writeoff_StlpayableQueryInfoGrid_payableUnverityAmount_Id').setValue(payUnverifyTotalAmount);
					Ext.getCmp('Foss_writeoff_StlpayableQueryInfoGrid_payableVerifyAmount_Id').setValue(payableVerifyAmount);
					Ext.getCmp('Foss_writeoff_StlpayableQueryInfoGrid_payableTotalRows_Id').setValue(result.billRecWriteoffBillPayResultVo.payTotalNum);
					Ext.getCmp('Foss_writeoff_StlpayableQueryInfoGrid_Id').show();
					
				}else if(billPayableLength.length ==0&&billReceivableLength.length>0){
					
					Ext.Msg.alert(writeoff.recWriteoffPay.i18n('foss.stl.writeoff.common.alert'),writeoff.recWriteoffPay.i18n('foss.stl.writeoff.receivableWriteOffPayble.notFoundPaybleWarning'),function(){
					    Ext.getCmp('Foss_writeoff_StlpayableQueryInfoGrid_Id').hide();
					});	
					
					//加载后台查询到的数据到grid的store中
					billReceivableEntryStore.loadData(result.billRecWriteoffBillPayResultVo.billReceivableEntityList);
					var recTotalAmount=writeoff.convertAmount(result.billRecWriteoffBillPayResultVo.recTotalAmount);
					var recUnverifyTotalAmount=writeoff.convertAmount(result.billRecWriteoffBillPayResultVo.recUnverifyTotalAmount);
					var recVerifyTotalAmount=writeoff.convertAmount(result.billRecWriteoffBillPayResultVo.recVerifyTotalAmount);
					
					Ext.getCmp('Foss_writeoff_StlpayableQueryInfoGrid_receivableTotalAmount_Id').setValue(recTotalAmount);
					Ext.getCmp('Foss_writeoff_StlpayableQueryInfoGrid_receivableUnverityAmount_Id').setValue(recUnverifyTotalAmount);
					Ext.getCmp('Foss_writeoff_StlpayableQueryInfoGrid_receivableVerifyAmount_Id').setValue(recVerifyTotalAmount);
					Ext.getCmp('Foss_writeoff_StlreceivableQueryInfoGrid_Id').show();
					
				}else if(billReceivableLength.length == 0 || billPayableLength.length == 0){
					Ext.Msg.alert(writeoff.recWriteoffPay.i18n('foss.stl.writeoff.common.alert'),writeoff.recWriteoffPay.i18n('foss.stl.writeoff.receivableWriteOffPayble.notFoundReceivableAndPaybleWarning'),function(){
						Ext.getCmp('Foss_writeoff_StlreceivableQueryInfoGrid_Id').hide();
					    Ext.getCmp('Foss_writeoff_StlpayableQueryInfoGrid_Id').hide();
					});
				}else{
					//加载后台查询到的数据到grid的store中
					billReceivableEntryStore.loadData(result.billRecWriteoffBillPayResultVo.billReceivableEntityList);
					billPayableEntityStore.loadData(result.billRecWriteoffBillPayResultVo.billPayableEntityList);
					
					//动态给GRID设置值
					Ext.getCmp('Foss_writeoff_StlpayableQueryInfoGrid_receivableTotalRows_Id').setValue(result.billRecWriteoffBillPayResultVo.recTotalNum);
					Ext.getCmp('Foss_writeoff_StlpayableQueryInfoGrid_payableTotalRows_Id').setValue(result.billRecWriteoffBillPayResultVo.payTotalNum);
					
					var recTotalAmount=writeoff.convertAmount(result.billRecWriteoffBillPayResultVo.recTotalAmount);
					var recUnverifyTotalAmount=writeoff.convertAmount(result.billRecWriteoffBillPayResultVo.recUnverifyTotalAmount);
					var recVerifyTotalAmount=writeoff.convertAmount(result.billRecWriteoffBillPayResultVo.recVerifyTotalAmount);
					
					Ext.getCmp('Foss_writeoff_StlpayableQueryInfoGrid_receivableTotalAmount_Id').setValue(recTotalAmount);
					Ext.getCmp('Foss_writeoff_StlpayableQueryInfoGrid_receivableUnverityAmount_Id').setValue(recUnverifyTotalAmount);
					Ext.getCmp('Foss_writeoff_StlpayableQueryInfoGrid_receivableVerifyAmount_Id').setValue(recVerifyTotalAmount);
					
					var payTotalAmount=writeoff.convertAmount(result.billRecWriteoffBillPayResultVo.payTotalAmount);
					var payUnverifyTotalAmount =writeoff.convertAmount(result.billRecWriteoffBillPayResultVo.payUnverifyTotalAmount);
					var payableVerifyAmount=writeoff.convertAmount(result.billRecWriteoffBillPayResultVo.payVerifyTotalAmount);
					
					Ext.getCmp('Foss_writeoff_StlpayableQueryInfoGrid_payableTotalAmount_Id').setValue(payTotalAmount);
					Ext.getCmp('Foss_writeoff_StlpayableQueryInfoGrid_payableUnverityAmount_Id').setValue(payUnverifyTotalAmount);
					Ext.getCmp('Foss_writeoff_StlpayableQueryInfoGrid_payableVerifyAmount_Id').setValue(payableVerifyAmount);
					
					Ext.getCmp('Foss_writeoff_StlreceivableQueryInfoGrid_Id').show();
					Ext.getCmp('Foss_writeoff_StlpayableQueryInfoGrid_Id').show();
				}
				me.enable(true);
			},
			exception:function(response){
				var result = Ext.decode(response.responseText);
				Ext.Msg.alert(writeoff.recWriteoffPay.i18n('foss.stl.writeoff.common.alert'),result.message);
				me.enable(true);
			}
		});
	}else if(writeoff.recWriteoffPay.queryRecWriteoffPay_RP==queryType){
		
		Ext.Ajax.request({
			url:writeoff.realPath('queryBillReceivableAndBillPayableForNumbers.action'),
			params:params,
			actionMethods:'post',
			submitEmptyText:false,
			success:function(response){
				
				var result = Ext.decode(response.responseText);
				var billReceivableEntryStore = Ext.getCmp('Foss_writeoff_StlreceivableQueryInfoGrid_Id').store;
				var billPayableEntityStore = Ext.getCmp('Foss_writeoff_StlpayableQueryInfoGrid_Id').store;
				
				var billReceivableLength = 0;
				if(!Ext.isEmpty(result.billRecWriteoffBillPayResultVo.billReceivableEntityList)){
					billReceivableLength = result.billRecWriteoffBillPayResultVo.billReceivableEntityList.length;
				}
				
				var billPayableLength = 0;
				if(!Ext.isEmpty(result.billRecWriteoffBillPayResultVo.billPayableEntityList)){
					billPayableLength = result.billRecWriteoffBillPayResultVo.billPayableEntityList;
				}
				//判断是否有数据提示
				if(billReceivableLength.length == 0 || billPayableLength.length == 0){
					Ext.Msg.alert(writeoff.recWriteoffPay.i18n('foss.stl.writeoff.common.alert'),writeoff.recWriteoffPay.i18n('foss.stl.writeoff.receivableWriteOffPayble.notFoundReceivableAndPaybleWarning'),function(){
						Ext.getCmp('Foss_writeoff_StlreceivableQueryInfoGrid_Id').hide();
					    Ext.getCmp('Foss_writeoff_StlpayableQueryInfoGrid_Id').hide();
					});

				}else if(billReceivableLength.length == 0&&billPayableLength.length>0){
					
					Ext.Msg.alert(writeoff.recWriteoffPay.i18n('foss.stl.writeoff.common.alert'),writeoff.recWriteoffPay.i18n('foss.stl.writeoff.receivableWriteOffPayble.notFoundReceivableWarning'),function(){
						Ext.getCmp('Foss_writeoff_StlreceivableQueryInfoGrid_Id').hide();
					});
					
					billPayableEntityStore.loadData(result.billRecWriteoffBillPayResultVo.billPayableEntityList);
					var payTotalAmount=writeoff.convertAmount(result.billRecWriteoffBillPayResultVo.payTotalAmount);
					var payUnverifyTotalAmount =writeoff.convertAmount(result.billRecWriteoffBillPayResultVo.payUnverifyTotalAmount);
					var payableVerifyAmount=writeoff.convertAmount(result.billRecWriteoffBillPayResultVo.payVerifyTotalAmount);
					
					Ext.getCmp('Foss_writeoff_StlpayableQueryInfoGrid_payableTotalAmount_Id').setValue(payTotalAmount);
					Ext.getCmp('Foss_writeoff_StlpayableQueryInfoGrid_payableUnverityAmount_Id').setValue(payUnverifyTotalAmount);
					Ext.getCmp('Foss_writeoff_StlpayableQueryInfoGrid_payableVerifyAmount_Id').setValue(payableVerifyAmount);
					Ext.getCmp('Foss_writeoff_StlpayableQueryInfoGrid_payableTotalRows_Id').setValue(result.billRecWriteoffBillPayResultVo.payTotalNum);
					Ext.getCmp('Foss_writeoff_StlpayableQueryInfoGrid_Id').show();
					
				}else if(billPayableLength.length == 0&&billReceivableLength.length>0){
					
					Ext.Msg.alert(writeoff.recWriteoffPay.i18n('foss.stl.writeoff.common.alert'),writeoff.recWriteoffPay.i18n('foss.stl.writeoff.receivableWriteOffPayble.notFoundPaybleWarning'),function(){
					    Ext.getCmp('Foss_writeoff_StlpayableQueryInfoGrid_Id').hide();
					});	
					
					//加载后台查询到的数据到grid的store中
					billReceivableEntryStore.loadData(result.billRecWriteoffBillPayResultVo.billReceivableEntityList);
					var recTotalAmount=writeoff.convertAmount(result.billRecWriteoffBillPayResultVo.recTotalAmount);
					var recUnverifyTotalAmount=writeoff.convertAmount(result.billRecWriteoffBillPayResultVo.recUnverifyTotalAmount);
					var recVerifyTotalAmount=writeoff.convertAmount(result.billRecWriteoffBillPayResultVo.recVerifyTotalAmount);
					
					Ext.getCmp('Foss_writeoff_StlpayableQueryInfoGrid_receivableTotalAmount_Id').setValue(recTotalAmount);
					Ext.getCmp('Foss_writeoff_StlpayableQueryInfoGrid_receivableUnverityAmount_Id').setValue(recUnverifyTotalAmount);
					Ext.getCmp('Foss_writeoff_StlpayableQueryInfoGrid_receivableVerifyAmount_Id').setValue(recVerifyTotalAmount);
					Ext.getCmp('Foss_writeoff_StlreceivableQueryInfoGrid_Id').show();
				}else{
					//加载后台查询到的数据到grid的store中
					billReceivableEntryStore.loadData(result.billRecWriteoffBillPayResultVo.billReceivableEntityList);
					billPayableEntityStore.loadData(result.billRecWriteoffBillPayResultVo.billPayableEntityList);
					
				    //动态给GRID设置值
					Ext.getCmp('Foss_writeoff_StlpayableQueryInfoGrid_receivableTotalRows_Id').setValue(result.billRecWriteoffBillPayResultVo.recTotalNum);
					Ext.getCmp('Foss_writeoff_StlpayableQueryInfoGrid_payableTotalRows_Id').setValue(result.billRecWriteoffBillPayResultVo.payTotalNum);
					
					var recTotalAmount=writeoff.convertAmount(result.billRecWriteoffBillPayResultVo.recTotalAmount);
					var recUnverifyTotalAmount=writeoff.convertAmount(result.billRecWriteoffBillPayResultVo.recUnverifyTotalAmount);
					var recVerifyTotalAmount=writeoff.convertAmount(result.billRecWriteoffBillPayResultVo.recVerifyTotalAmount);
					
					Ext.getCmp('Foss_writeoff_StlpayableQueryInfoGrid_receivableTotalAmount_Id').setValue(recTotalAmount);
					Ext.getCmp('Foss_writeoff_StlpayableQueryInfoGrid_receivableUnverityAmount_Id').setValue(recUnverifyTotalAmount);
					Ext.getCmp('Foss_writeoff_StlpayableQueryInfoGrid_receivableVerifyAmount_Id').setValue(recVerifyTotalAmount);
					
					var payTotalAmount=writeoff.convertAmount(result.billRecWriteoffBillPayResultVo.payTotalAmount);
					var payUnverifyTotalAmount =writeoff.convertAmount(result.billRecWriteoffBillPayResultVo.payUnverifyTotalAmount);
					var payableVerifyAmount=writeoff.convertAmount(result.billRecWriteoffBillPayResultVo.payVerifyTotalAmount);
					
					Ext.getCmp('Foss_writeoff_StlpayableQueryInfoGrid_payableTotalAmount_Id').setValue(payTotalAmount);
					Ext.getCmp('Foss_writeoff_StlpayableQueryInfoGrid_payableUnverityAmount_Id').setValue(payUnverifyTotalAmount);
					Ext.getCmp('Foss_writeoff_StlpayableQueryInfoGrid_payableVerifyAmount_Id').setValue(payableVerifyAmount);
					
					Ext.getCmp('Foss_writeoff_StlreceivableQueryInfoGrid_Id').show();
					Ext.getCmp('Foss_writeoff_StlpayableQueryInfoGrid_Id').show();
				}
				me.enable(true);
			},
			exception:function(response){
				var result = Ext.decode(response.responseText);
				Ext.Msg.alert(writeoff.recWriteoffPay.i18n('foss.stl.writeoff.common.alert'),result.message);
				me.enable(true);
			}
		});
	}
};



//查询应收应付单参数设置
writeoff.recWriteoffPay.queryWriteoffRecAndPayParam=function(form,queryType){
	//定义查询参数
	var params={};
	//按日期查询
	if(writeoff.recWriteoffPay.queryRecWriteoffPay_TD==queryType){
		
		//应收单开始日期、结束日期
		var recBusinessStartDate=form.findField('recBusinessStartDate').getValue();
		var recBusinessEndDate=form.findField('recBusinessEndDate').getValue();
		
		//比较起始日期和结束日期
		var compareTwoDate = stl.compareTwoDate(recBusinessStartDate,recBusinessEndDate);
		if(compareTwoDate>writeoff.recWriteoffPay.STLBILLRECWRITEOFFBILLPAY_MAX){
			Ext.Msg.alert(writeoff.recWriteoffPay.i18n('foss.stl.writeoff.common.alert'),writeoff.recWriteoffPay.i18n('foss.stl.writeoff.receivableWriteOffPayble.queryDateMaxLimit')+writeoff.recWriteoffPay.STLBILLRECWRITEOFFBILLPAY_MAX+writeoff.recWriteoffPay.i18n('foss.stl.writeoff.receivableWriteOffPayble.date'));
			return false;
		}else if(compareTwoDate<1){
			Ext.Msg.alert(writeoff.recWriteoffPay.i18n('foss.stl.writeoff.common.alert'),writeoff.recWriteoffPay.i18n('foss.stl.writeoff.receivableWriteOffPayble.endDateErrorWarning'));
			return false;
		}
		
		//应付单开始日期、结束日期		
		var payBusinessStartDate=form.findField('payBusinessStartDate').getValue();
		var payBusinessEndDate=form.findField('payBusinessEndDate').getValue();
		
		
		//比较起始日期和结束日期
		var compareTwoDate = stl.compareTwoDate(payBusinessStartDate,payBusinessEndDate);
		if(compareTwoDate>writeoff.recWriteoffPay.STLBILLRECWRITEOFFBILLPAY_MAX){
			Ext.Msg.alert(writeoff.recWriteoffPay.i18n('foss.stl.writeoff.common.alert'),writeoff.recWriteoffPay.i18n('foss.stl.writeoff.receivableWriteOffPayble.queryDateMaxLimit')+writeoff.recWriteoffPay.STLBILLRECWRITEOFFBILLPAY_MAX+writeoff.recWriteoffPay.i18n('foss.stl.writeoff.receivableWriteOffPayble.date'));
			return false;
		}else if(compareTwoDate<1){
			Ext.Msg.alert(writeoff.recWriteoffPay.i18n('foss.stl.writeoff.common.alert'),writeoff.recWriteoffPay.i18n('foss.stl.writeoff.receivableWriteOffPayble.endDateErrorWarning'));
			return false;
		}
		
		//客户类型 最大页面条数
		var customerType=form.findField('customerType').getValue();
		var maxShowNum=form.findField('showRecordCount').getValue();
		
		//客户编码
		var customerCode = "";
		var commoncustomerselector = form.findField('writeoff.recWriteoffPay.commoncustomerselector').getValue();
    	var commonairagencycompanyselector = form.findField('writeoff.recWriteoffPay.commonairagencycompanyselector').getValue();
    	var commonvehagencycompselector = form.findField('writeoff.recWriteoffPay.commonvehagencycompselector').getValue();
    	var commonairlinesselector = form.findField('writeoff.recWriteoffPay.commonairlinesselector').getValue();
		var landStowage = form.findField('writeoff.recWriteoffPay.landStowage').getValue();
		var packagingCode = form.findField('writeoff.recWriteoffPay.packagingCode').getValue();
		var homesupply = form.findField('writeoff.recWriteoffPay.homesupply').getValue();
		//合伙人
		var partnerOrg = form.findField('writeoff.recWriteoffPay.partnerOrg').getValue();
		if(commoncustomerselector != "" && commoncustomerselector != null){
			customerCode = commoncustomerselector;
		}else if(commonairagencycompanyselector != "" && commonairagencycompanyselector != null){
			customerCode = commonairagencycompanyselector;
		}else if(commonvehagencycompselector != "" && commonvehagencycompselector != null){
			customerCode = commonvehagencycompselector;
		}else if(commonairlinesselector != "" && commonairlinesselector != null){
			customerCode = commonairlinesselector;
		}else if (landStowage!=""  && landStowage != null){
			customerCode = landStowage;
		}else if (packagingCode!=""  && packagingCode != null){
			customerCode = packagingCode;
		}else if(homesupply != "" && homesupply != null){
			customerCode = homesupply;
		}else if(partnerOrg !="" && partnerOrg != null){
			customerCode = partnerOrg;
		}
		
		//查询时，传入参数
		Ext.apply(params,{ 
			'billRecWriteoffBillPayVo.billRecWriteoffBillPayDto.recBusinessStartDate':recBusinessStartDate,//应收单业务开始日期
			'billRecWriteoffBillPayVo.billRecWriteoffBillPayDto.recBusinessEndDate':recBusinessEndDate,//应收单业务开始日期
			'billRecWriteoffBillPayVo.billRecWriteoffBillPayDto.payBusinessStartDate':payBusinessStartDate,//应付单业务结束日期
			'billRecWriteoffBillPayVo.billRecWriteoffBillPayDto.payBusinessEndDate':payBusinessEndDate,//应付单业务结束日期
			'billRecWriteoffBillPayVo.billRecWriteoffBillPayDto.customerType':customerType,//客户类型
			'billRecWriteoffBillPayVo.billRecWriteoffBillPayDto.maxShowNum':maxShowNum,//最大条数
			'billRecWriteoffBillPayVo.billRecWriteoffBillPayDto.customerCode':customerCode//客户名称
		});
		//按单号查询
	}else if(writeoff.recWriteoffPay.queryRecWriteoffPay_RP==queryType){
		//界面应收单运单号、应付单运单号
		receivableNos = form.findField('receivableNos').getValue();
		payableNos = form.findField('payableNos').getValue();
		
		
		if(null==receivableNos){
			Ext.Msg.alert(writeoff.recWriteoffPay.i18n('foss.stl.writeoff.common.alert'),writeoff.recWriteoffPay.i18n('foss.stl.writeoff.receivableWriteOffPayble.receivableOrWaybillNullWarning'));
		 	return false;
		}
		
		if(null==payableNos){
			Ext.Msg.alert(writeoff.recWriteoffPay.i18n('foss.stl.writeoff.common.alert'),writeoff.recWriteoffPay.i18n('foss.stl.writeoff.receivableWriteOffPayble.payableOrWaybillNullWarning'));
		 	return false;
		}
		
		receivableNosArray = stl.splitToArray(receivableNos);
		for(var i=0;i<receivableNosArray.length;i++){
			if(receivableNosArray[i]==''){
				receivableNosArray.pop(receivableNosArray[i]);
			}
		}
		
		if(receivableNosArray.length>10){
			Ext.Msg.alert(writeoff.recWriteoffPay.i18n('foss.stl.writeoff.common.alert'),writeoff.recWriteoffPay.i18n('foss.stl.writeoff.receivableWriteOffPayble.maxInputReceivableOrWaybillWarning'));
		 	return false;
		}
		
		payableNosArray = stl.splitToArray(payableNos);
		for(var i=0;i<payableNosArray.length;i++){
			if(payableNosArray[i]==''){
				payableNosArray.pop(payableNosArray[i]);
			}
		}
		if(payableNosArray.length>10){
			Ext.Msg.alert(writeoff.recWriteoffPay.i18n('foss.stl.writeoff.common.alert'),writeoff.recWriteoffPay.i18n('foss.stl.writeoff.receivableWriteOffPayble.maxInputPayableOrWaybillWarning'));
		 	return false;
		}
				
		Ext.apply(params,{
			'billRecWriteoffBillPayVo.billRecWriteoffBillPayDto.receivableNosOrWaybillNos':receivableNos,//应收单或者运单集合
			'billRecWriteoffBillPayVo.billRecWriteoffBillPayDto.payNosOrWaybillNos':payableNos,//应付单或者运单集合
			'billRecWriteoffBillPayVo.billRecWriteoffBillPayDto.queryType':queryType
		});
	}
	return params;
};


//查询条件重置Form重置
writeoff.recWriteoffPay.reset=function(){
	this.up('form').getForm().reset();
	//客户信息、空运代理公司、偏线代理公司、航空公司
	Ext.getCmp('writeoff.recWriteoffPay.commoncustomerselector').show();
	Ext.getCmp('writeoff.recWriteoffPay.commoncustomerselector').setReadOnly(false);
	Ext.getCmp('writeoff.recWriteoffPay.commonairagencycompanyselector').setValue("");
	Ext.getCmp('writeoff.recWriteoffPay.commonairagencycompanyselector').hide();
	Ext.getCmp('writeoff.recWriteoffPay.commonvehagencycompselector').setValue("");
	Ext.getCmp('writeoff.recWriteoffPay.commonvehagencycompselector').hide();
	Ext.getCmp('writeoff.recWriteoffPay.commonairlinesselector').setValue("");
	Ext.getCmp('writeoff.recWriteoffPay.commonairlinesselector').hide();	
	Ext.getCmp('writeoff.recWriteoffPay.packagingCode').hide();
	Ext.getCmp('writeoff.recWriteoffPay.packagingCode').setValue("");	
	Ext.getCmp('writeoff.recWriteoffPay.homesupply').hide();
	Ext.getCmp('writeoff.recWriteoffPay.homesupply').setValue("");	
	Ext.getCmp('writeoff.recWriteoffPay.landStowage').hide();
	Ext.getCmp('writeoff.recWriteoffPay.landStowage').setValue("");	
	Ext.getCmp('writeoff.recWriteoffPay.partnerOrg').hide();
	Ext.getCmp('writeoff.recWriteoffPay.partnerOrg').setValue("");	
};


//导出应收单
writeoff.recWriteoffPay.exportWriteoffBillRec=function(){
	var	columns,
	searchParams,
	arrayColumns,
	arrayColumnNames,
	grid;
	//获取表格
	grid = Ext.getCmp('T_writeoff-recWriteOffPayble_content').items.items[1];
	//获取tab
	var queryTab=Ext.getCmp('T_writeoff-recWriteOffPayble_content').items.items[0];
	//获取查询type辨别是按哪个表单查询
	var queryType =queryTab.getActiveTab().down('form').getQueryType();
	
	//获取当前选择的的form
	var form=queryTab.getActiveTab().down('form').getForm();
	
	
	
	//如果没有查询到数据，则不能进行导出操作
	if(grid.store.data.length==0){
		Ext.Msg.alert(writeoff.recWriteoffPay.i18n('foss.stl.writeoff.common.alert'),writeoff.recWriteoffPay.i18n('foss.stl.writeoff.receivableWriteOffPayble.noDateExportReceivable'));
		return false;
	}
	
	//进行导出提示
	Ext.MessageBox.show({
        title: writeoff.recWriteoffPay.i18n('foss.stl.writeoff.common.alert'),
        msg: writeoff.recWriteoffPay.i18n('foss.stl.writeoff.receivableWriteOffPayble.isExportReceivableWarning'),
        buttons: Ext.MessageBox.YESNOCANCEL,
        buttonText:{ 
            yes: writeoff.recWriteoffPay.i18n('foss.stl.writeoff.common.defaultColumnExport'),
            no: writeoff.recWriteoffPay.i18n('foss.stl.writeoff.common.customColumnExport'),
            cancel:writeoff.recWriteoffPay.i18n('foss.stl.writeoff.common.cancel')
        },
        fn: function(e){
        	if(e=='yes'){
        		//默认显示列
				arrayColumns = ['receivableNo','waybillNo','productCode','customerName','customerCode',
								'billType','amount','verifyAmount','unverifyAmount',
								'businessDate','accountDate','statementBillNo'];
				//默认显示列名称
				arrayColumnNames = [writeoff.recWriteoffPay.i18n('foss.stl.writeoff.common.receivableNo'),writeoff.recWriteoffPay.i18n('foss.stl.writeoff.common.waybillNo'),writeoff.recWriteoffPay.i18n('foss.stl.writeoff.common.productCode'),writeoff.recWriteoffPay.i18n('foss.stl.writeoff.common.customerName'),writeoff.recWriteoffPay.i18n('foss.stl.writeoff.receivableWriteOffPayble.customerCode'),writeoff.recWriteoffPay.i18n('foss.stl.writeoff.common.billType'),
									writeoff.recWriteoffPay.i18n('foss.stl.writeoff.common.amount'),writeoff.recWriteoffPay.i18n('foss.stl.writeoff.common.verifyAmount'),writeoff.recWriteoffPay.i18n('foss.stl.writeoff.common.unverifyAmount'),writeoff.recWriteoffPay.i18n('foss.stl.writeoff.common.businessDate'),writeoff.recWriteoffPay.i18n('foss.stl.writeoff.receivableWriteOffPayble.statementBillDate'),writeoff.recWriteoffPay.i18n('foss.stl.writeoff.receivableWriteOffPayble.statementNo')];									
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
						if(columns[i].text!=writeoff.recWriteoffPay.i18n('foss.stl.writeoff.common.actionColumn')){
							arrayColumns.push(dataIndex);
							arrayColumnNames.push(hederName);
						}
					}
				}
        	}else{
        		return false;
        	}
   			
        	//按日期查询
        	if(queryType=='TD'){
        		//开始日期
//        		var businessStartDate=form.findField('businessStartDate').getValue();
        		
        		//应收单开始日期、结束日期
        		var recBusinessStartDate=form.findField('recBusinessStartDate').getValue();
        		var recBusinessEndDate=form.findField('recBusinessEndDate').getValue();      
        		
        		//客户类型
        		var customerType=form.findField('customerType').getValue();
        		//最大条数
        		var maxShowNum=form.findField('showRecordCount').getValue();
        		//结束日期
//        		var businessEndDate=form.findField('businessEndDate').getValue();
        		//客户编码
        		var customerCode = "";
        		var commoncustomerselector = form.findField('writeoff.recWriteoffPay.commoncustomerselector').getValue();
            	var commonairagencycompanyselector = form.findField('writeoff.recWriteoffPay.commonairagencycompanyselector').getValue();
            	var commonvehagencycompselector = form.findField('writeoff.recWriteoffPay.commonvehagencycompselector').getValue();
            	var commonairlinesselector = form.findField('writeoff.recWriteoffPay.commonairlinesselector').getValue();
        		var landStowage = form.findField('writeoff.recWriteoffPay.landStowage').getValue();
        		var packagingCode = form.findField('writeoff.recWriteoffPay.packagingCode').getValue();
        		
				if(commoncustomerselector != "" && commoncustomerselector != null){
        			customerCode = commoncustomerselector;
        		}else if(commonairagencycompanyselector != "" && commonairagencycompanyselector != null){
        			customerCode = commonairagencycompanyselector;
        		}else if(commonvehagencycompselector != "" && commonvehagencycompselector != null){
        			customerCode = commonvehagencycompselector;
        		}else if(commonairlinesselector != "" && commonairlinesselector != null){
        			customerCode = commonairlinesselector;
        		}else if (landStowage!=""  && landStowage != null){
					customerCode = landStowage;
				}else if (packagingCode!=""  && packagingCode != null){
					customerCode = packagingCode;
				}
        		
        		searchParams = {
        				'billRecWriteoffBillPayVo.billRecWriteoffBillPayDto.recBusinessStartDate':recBusinessStartDate,//应收单业务开始日期
        				'billRecWriteoffBillPayVo.billRecWriteoffBillPayDto.recBusinessEndDate':recBusinessEndDate,//应收单业务开始日期
        				'billRecWriteoffBillPayVo.billRecWriteoffBillPayDto.customerType':customerType,//客户类型
        				'billRecWriteoffBillPayVo.billRecWriteoffBillPayDto.maxShowNum':maxShowNum,//最大条数
        				'billRecWriteoffBillPayVo.billRecWriteoffBillPayDto.customerCode':customerCode,//客户名称
        				'billRecWriteoffBillPayVo.billRecWriteoffBillPayDto.queryType':queryType,//按日期查询
        				'billRecWriteoffBillPayVo.billRecWriteoffBillPayDto.arrayColumns':arrayColumns,//自定义列名
        		    	'billRecWriteoffBillPayVo.billRecWriteoffBillPayDto.arrayColumnNames':arrayColumnNames//自定义列名称
        		};
        		
        	}else if(queryType=='RP'){
        		//获取界面应收单号
        		var receivableNos=form.findField('receivableNos').getValue();
        		//获取界面应付单号
        		var payableNos=form.findField('payableNos').getValue();
        		
        		if(null==receivableNos){
        			Ext.Msg.alert(writeoff.recWriteoffPay.i18n('foss.stl.writeoff.common.alert'),writeoff.recWriteoffPay.i18n('foss.stl.writeoff.receivableWriteOffPayble.receivableOrWaybillNullWarning！'));
        		 	return false;
        		}
        		
        		if(null==payableNos){
        			Ext.Msg.alert(writeoff.recWriteoffPay.i18n('foss.stl.writeoff.common.alert'),writeoff.recWriteoffPay.i18n('foss.stl.writeoff.receivableWriteOffPayble.payableOrWaybillNullWarning'));
        		 	return false;
        		}
        		
        		receivableNosArray = stl.splitToArray(receivableNos);
        		for(var i=0;i<receivableNosArray.length;i++){
        			if(receivableNosArray[i]==''){
        				receivableNosArray.pop(receivableNosArray[i]);
        			}
        		}
        		
        		payableNosArray = stl.splitToArray(payableNos);
        		for(var i=0;i<payableNosArray.length;i++){
        			if(payableNosArray[i]==''){
        				payableNosArray.pop(payableNosArray[i]);
        			}
        		}
        	
        		searchParams = {
        				'billRecWriteoffBillPayVo.billRecWriteoffBillPayDto.receivableNosOrWaybillNos':receivableNos,//应收单号集合
        				'billRecWriteoffBillPayVo.billRecWriteoffBillPayDto.payNosOrWaybillNos':payableNos,//应付单号集合
        				'billRecWriteoffBillPayVo.billRecWriteoffBillPayDto.queryType':queryType,//按单号查询
        				'billRecWriteoffBillPayVo.billRecWriteoffBillPayDto.arrayColumns':arrayColumns,//自定义列名
        		    	'billRecWriteoffBillPayVo.billRecWriteoffBillPayDto.arrayColumnNames':arrayColumnNames//自定义列名称
        		};
        	}
        	
        	
        	if(!Ext.fly('downloadAttachFileForm')){
			    var frm = document.createElement('form');
			    frm.id = 'downloadAttachFileForm';
			    frm.style.display = 'none';
			    document.body.appendChild(frm);
			}
			
			//拼接vo，注入到后台
			Ext.Ajax.request({
				//导出应收单列表
			    url:writeoff.realPath('exportWriteoffBillRec.action'),
			    form: Ext.fly('downloadAttachFileForm'),
			    method : 'post',
			    params :searchParams,
			    isUpload: true,
			    success : function(response){
			    	var result = Ext.decode(response.responseText);
			    	//如果异常信息有值，则弹出提示
			    	if(!Ext.isEmpty(result.errorMessage)){
			    		Ext.Msg.alert(writeoff.recWriteoffPay.i18n('foss.stl.writeoff.common.alert'),result.errorMessage);
			    		return false;
			    	}
					Ext.ux.Toast.msg(writeoff.recWriteoffPay.i18n('foss.stl.writeoff.common.alert'),writeoff.recWriteoffPay.i18n('foss.stl.writeoff.common.exportSuccess'), 'success', 1000);
			    },
				exception:function(response){
					var result = Ext.decode(response.responseText);
					Ext.Msg.alert(writeoff.recWriteoffPay.i18n('foss.stl.writeoff.common.alert'),result.message);
				}
			});
		}
    });
	
};


//导出应付单
writeoff.recWriteoffPay.exportWriteoffBillPay=function(){
	var	columns,
	searchParams,
	arrayColumns,
	arrayColumnNames,
	grid;
	//获取应付单表单
	grid = Ext.getCmp('T_writeoff-recWriteOffPayble_content').items.items[2];
	//获取tab查询表单
	var queryTab=Ext.getCmp('T_writeoff-recWriteOffPayble_content').items.items[0];
	//获取查询type辨别是按哪个表单查询
	var queryType =queryTab.getActiveTab().down('form').getQueryType();
	
	//获取当前选择的的form
	var form=queryTab.getActiveTab().down('form').getForm();
	
	//如果没有查询到数据，则不能进行导出操作
	if(grid.store.data.length==0){
		Ext.Msg.alert(writeoff.recWriteoffPay.i18n('foss.stl.writeoff.common.alert'),writeoff.recWriteoffPay.i18n('foss.stl.writeoff.receivableWriteOffPayble.noDateExportPayable'));
		return false;
	}
	
	//进行导出提示
	Ext.MessageBox.show({
        title: writeoff.recWriteoffPay.i18n('foss.stl.writeoff.common.alert'),
        msg: writeoff.recWriteoffPay.i18n('foss.stl.writeoff.receivableWriteOffPayble.isExportPayableWarning'),
        buttons: Ext.MessageBox.YESNOCANCEL,
        buttonText:{ 
            yes: writeoff.recWriteoffPay.i18n('foss.stl.writeoff.common.defaultColumnExport'),
            no: writeoff.recWriteoffPay.i18n('foss.stl.writeoff.common.customColumnExport'),
            cancel:writeoff.recWriteoffPay.i18n('foss.stl.writeoff.common.cancel')
        },
        fn: function(e){
        	if(e=='yes'){
        		//默认显示列
				arrayColumns = ['payableNo','waybillNo','productCode','customerName','customerCode',
								'billType','amount','verifyAmount','unverifyAmount',
								'businessDate','accountDate','statementBillNo','paymentAmount'];
				//默认显示列名称
				arrayColumnNames = [
										writeoff.recWriteoffPay.i18n('foss.stl.writeoff.common.payableNo'),
										writeoff.recWriteoffPay.i18n('foss.stl.writeoff.common.waybillNo'),
										writeoff.recWriteoffPay.i18n('foss.stl.writeoff.common.productCode'),
										writeoff.recWriteoffPay.i18n('foss.stl.writeoff.common.customerName'),
										writeoff.recWriteoffPay.i18n('foss.stl.writeoff.receivableWriteOffPayble.customerCode'),
										writeoff.recWriteoffPay.i18n('foss.stl.writeoff.common.billType'),
										writeoff.recWriteoffPay.i18n('foss.stl.writeoff.common.amount'),
										writeoff.recWriteoffPay.i18n('foss.stl.writeoff.common.verifyAmount'),
										writeoff.recWriteoffPay.i18n('foss.stl.writeoff.common.unverifyAmount'),
										writeoff.recWriteoffPay.i18n('foss.stl.writeoff.common.businessDate'),
										writeoff.recWriteoffPay.i18n('foss.stl.writeoff.receivableWriteOffPayble.statementBillDate'),
										writeoff.recWriteoffPay.i18n('foss.stl.writeoff.receivableWriteOffPayble.statementNo'),
										writeoff.recWriteoffPay.i18n('foss.stl.writeoff.receivableWriteOffPayble.paymentAmount')
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
						if(columns[i].text!=writeoff.recWriteoffPay.i18n('foss.stl.writeoff.common.actionColumn')){
							arrayColumns.push(dataIndex);
							arrayColumnNames.push(hederName);
						}
					}
				}
        	}else{
        		return false;
        	}
   			
        	//按日期查询
        	if(queryType=='TD'){
        		//开始日期
//        		var businessStartDate=form.findField('businessStartDate').getValue();
        		//客户类型
        		var customerType=form.findField('customerType').getValue();
        		//最大条数
        		var maxShowNum=form.findField('showRecordCount').getValue();
        		//结束日期
//        		var businessEndDate=form.findField('businessEndDate').getValue();
        		//应付单开始日期、结束日期		
        		var payBusinessStartDate=form.findField('payBusinessStartDate').getValue();
        		var payBusinessEndDate=form.findField('payBusinessEndDate').getValue();
        		//客户编码
        		var customerCode = "";
        		var commoncustomerselector = form.findField('writeoff.recWriteoffPay.commoncustomerselector').getValue();
            	var commonairagencycompanyselector = form.findField('writeoff.recWriteoffPay.commonairagencycompanyselector').getValue();
            	var commonvehagencycompselector = form.findField('writeoff.recWriteoffPay.commonvehagencycompselector').getValue();
            	var commonairlinesselector = form.findField('writeoff.recWriteoffPay.commonairlinesselector').getValue();
        		var landStowage = form.findField('writeoff.recWriteoffPay.landStowage').getValue();
        		var packagingCode = form.findField('writeoff.recWriteoffPay.packagingCode').getValue();

				if(commoncustomerselector != "" && commoncustomerselector != null){
        			customerCode = commoncustomerselector;
        		}else if(commonairagencycompanyselector != "" && commonairagencycompanyselector != null){
        			customerCode = commonairagencycompanyselector;
        		}else if(commonvehagencycompselector != "" && commonvehagencycompselector != null){
        			customerCode = commonvehagencycompselector;
        		}else if(commonairlinesselector != "" && commonairlinesselector != null){
        			customerCode = commonairlinesselector;
        		}else if (landStowage!=""  && landStowage != null){
					customerCode = landStowage;
				}else if (packagingCode!=""  && packagingCode != null){
					customerCode = packagingCode;
				}
				
        		searchParams = {
        				'billRecWriteoffBillPayVo.billRecWriteoffBillPayDto.payBusinessStartDate':payBusinessStartDate,//应付单业务结束日期
        				'billRecWriteoffBillPayVo.billRecWriteoffBillPayDto.payBusinessEndDate':payBusinessEndDate,//应付单业务结束日期
//        				'billRecWriteoffBillPayVo.billRecWriteoffBillPayDto.businessStartDate':businessStartDate,//业务开始日期
        				'billRecWriteoffBillPayVo.billRecWriteoffBillPayDto.customerType':customerType,//客户类型
        				'billRecWriteoffBillPayVo.billRecWriteoffBillPayDto.maxShowNum':maxShowNum,//最大条数
//        				'billRecWriteoffBillPayVo.billRecWriteoffBillPayDto.businessEndDate':businessEndDate,//业务结束日期
        				'billRecWriteoffBillPayVo.billRecWriteoffBillPayDto.customerCode':customerCode,//客户名称
        				'billRecWriteoffBillPayVo.billRecWriteoffBillPayDto.queryType':queryType,//按日期查询
        				'billRecWriteoffBillPayVo.billRecWriteoffBillPayDto.arrayColumns':arrayColumns,//自定义列名
        		    	'billRecWriteoffBillPayVo.billRecWriteoffBillPayDto.arrayColumnNames':arrayColumnNames//自定义列名称
        		};
        		
        	}else if(queryType=='RP'){
        		//获取界面应收单号
        		var receivableNos=form.findField('receivableNos').getValue();
        		//获取界面应付单号
        		var payableNos=form.findField('payableNos').getValue();
        		
        		if(null==receivableNos){
        			Ext.Msg.alert(writeoff.recWriteoffPay.i18n('foss.stl.writeoff.common.alert'),writeoff.recWriteoffPay.i18n('foss.stl.writeoff.receivableWriteOffPayble.receivableOrWaybillNullWarning'));
        		 	return false;
        		}
        		
        		if(null==payableNos){
        			Ext.Msg.alert(writeoff.recWriteoffPay.i18n('foss.stl.writeoff.common.alert'),writeoff.recWriteoffPay.i18n('foss.stl.writeoff.receivableWriteOffPayble.payableOrWaybillNullWarning'));
        		 	return false;
        		}
        		
        		receivableNosArray = stl.splitToArray(receivableNos);
        		for(var i=0;i<receivableNosArray.length;i++){
        			if(receivableNosArray[i]==''){
        				receivableNosArray.pop(receivableNosArray[i]);
        			}
        		}
        		
        		payableNosArray = stl.splitToArray(payableNos);
        		for(var i=0;i<payableNosArray.length;i++){
        			if(payableNosArray[i]==''){
        				payableNosArray.pop(payableNosArray[i]);
        			}
        		}
        	
        		searchParams = {
        				'billRecWriteoffBillPayVo.billRecWriteoffBillPayDto.receivableNosOrWaybillNos':receivableNosArray,//业务开始日期
        				'billRecWriteoffBillPayVo.billRecWriteoffBillPayDto.payNosOrWaybillNos':payableNosArray,//客户类型
        				'billRecWriteoffBillPayVo.billRecWriteoffBillPayDto.queryType':queryType,//按单号查询
        				'billRecWriteoffBillPayVo.billRecWriteoffBillPayDto.arrayColumns':arrayColumns,//自定义列名
        		    	'billRecWriteoffBillPayVo.billRecWriteoffBillPayDto.arrayColumnNames':arrayColumnNames//自定义列名称
        		};
        	}
        	
        	
        	if(!Ext.fly('downloadAttachFileForm')){
			    var frm = document.createElement('form');
			    frm.id = 'downloadAttachFileForm';
			    frm.style.display = 'none';
			    document.body.appendChild(frm);
			}
			
			//拼接vo，注入到后台
			Ext.Ajax.request({
				//导出应收单列表
			    url:writeoff.realPath('exportWriteoffBillPay.action'),
			    form: Ext.fly('downloadAttachFileForm'),
			    method : 'post',
			    params :searchParams,
			    isUpload: true,
			    success : function(response){
			    	var result = Ext.decode(response.responseText);
			    	//如果异常信息有值，则弹出提示
			    	if(!Ext.isEmpty(result.errorMessage)){
			    		Ext.Msg.alert(writeoff.recWriteoffPay.i18n('foss.stl.writeoff.common.alert'),result.errorMessage);
			    		return false;
			    	}
					Ext.ux.Toast.msg(writeoff.recWriteoffPay.i18n('foss.stl.writeoff.common.alert'), writeoff.recWriteoffPay.i18n('foss.stl.writeoff.common.exportSuccess'), 'success', 1000);
			    },
				exception:function(response){
					var result = Ext.decode(response.responseText);
					Ext.Msg.alert(writeoff.recWriteoffPay.i18n('foss.stl.writeoff.common.alert'),result.message);
				}
			});
		}
    });
};


/***
 * 按应收单应付单号查询Form
 */
Ext.define('Foss.writeoff.recWriteoffPay.StlBillQueryByPayRecNosFrom',{
	extend:'Ext.form.Panel',
	frame:false,
	columnWidth:1,
	tabConfig: {
		width: 120
	},
  layout:'column',
  getQueryType:function(){
		return writeoff.recWriteoffPay.queryRecWriteoffPay_RP='RP';
	},
    items:[{
    	xtype:'form',
    	defaults:{
        	margin:'5 5 5 5',
        	labelWidth:90
   		 },
    	layout:'column',
    	 items:[{
	    	xtype: 'textarea',
 			autoScroll:true,
 			emptyText: writeoff.recWriteoffPay.i18n('foss.stl.writeoff.receivableWriteOffPayble.inputBillErrorWarning'),
            fieldLabel: writeoff.recWriteoffPay.i18n('foss.stl.writeoff.receivableWriteOffPayble.receivableWaybill'),
            name: 'receivableNos',
            regex:/^((YS)?[0-9]{7,14}[;,])*((YS)?[0-9]{7,14}[;,]?)$/i,
			regexText:writeoff.recWriteoffPay.i18n('foss.stl.writeoff.receivableWriteOffPayble.inputYSBillErrorWarning'),
            height : 80,
            allowBlank:false,
            value:'',
            columnWidth:.45
	    },{
	    	xtype: 'textarea',
 			autoScroll:true,
 			emptyText: writeoff.recWriteoffPay.i18n('foss.stl.writeoff.receivableWriteOffPayble.inputBillErrorWarning'),
            fieldLabel: writeoff.recWriteoffPay.i18n('foss.stl.writeoff.receivableWriteOffPayble.payableWaybill'),
            regex:/^((YF)?[0-9]{7,14}[;,])*((YF)?[0-9]{7,14}[;,]?)$/i,
			regexText:writeoff.recWriteoffPay.i18n('foss.stl.writeoff.receivableWriteOffPayble.inputYFBillErrorWarning'),
            name: 'payableNos',
            height : 80,
            allowBlank:false,
            value:'',
            columnWidth:.45
	    },{
	    	border: 1,
			xtype:'container',
			columnWidth:1,
			defaultType:'button',
			layout:'column',
			items:[{
				text:writeoff.recWriteoffPay.i18n('foss.stl.writeoff.common.reset'),
				columnWidth:writeoff.recWriteoffPay.BNCOLUMNWIDTH,
				handler:writeoff.recWriteoffPay.reset
			},{
				xtype: 'container',
				border : false,
				columnWidth:.74,
				html: '&nbsp;'
			},{
				text:writeoff.recWriteoffPay.i18n('foss.stl.writeoff.common.query'),
				cls:'yellow_button',
				columnWidth:writeoff.recWriteoffPay.BNCOLUMNWIDTH,
				handler:function(){
					var form=this.up('form');
					var me =this;
					writeoff.recWriteoffPay.queryWriteoffRecAndPay(form,me,writeoff.recWriteoffPay.queryRecWriteoffPay_RP);
				}
			}]
	    }]
    }]
});



/**
 * 应收冲应付单按日期查询Form
 */
Ext.define('Foss.writeoff.recWriteoffPay.StlBillQueryByDateFrom',{
	extend:'Ext.form.Panel',
	frame:false,
	columnWidth:1,
	tabConfig: {
		width: 120
	},
	layout:'fit',
	getQueryType:function(){
		return writeoff.recWriteoffPay.queryRecWriteoffPay_TD='TD';
	},
    items:[{
    	xtype:'form',
    	defaults:{
    		margin:'5,5,5,5',
        	labelWidth:90
   		 },
	    items:[{  
            xtype : "fieldset",  
            title : "应收单查询日期范围",
            layout : "column", 
            columnWidth : writeoff.recWriteoffPay.COLUMNWIDTH,
            items:[{
    	    	xtype:'datefield',
    	    	name:'recBusinessStartDate',
    	    	fieldLabel:writeoff.recWriteoffPay.i18n('foss.stl.writeoff.receivableWriteOffPayble.businessStartDate'),
    	    	value:stl.getTargetDate(new Date(),-writeoff.recWriteoffPay.STLBILLRECWRITEOFFBILLPAY_ONEMONTH),
    	    	maxValue:new Date(),
    	    	editable:false,
    	    	columnWidth:writeoff.recWriteoffPay.COLUMNWIDTH,
    	    	invalidText : writeoff.recWriteoffPay.i18n('foss.stl.writeoff.receivableWriteOffPayble.dateFormatError'),
    	    	format:'Y-m-d',
    	    	allowBlank:false
    	    },{
    	    	xtype:'datefield',
    	   	 	name:'recBusinessEndDate',
    	   	 	editable:false,
    	    	fieldLabel:writeoff.recWriteoffPay.i18n('foss.stl.writeoff.receivableWriteOffPayble.businessEndDate'),		    	
    	    	maxValue:new Date(),
    	    	value:new Date(),
    	    	columnWidth:writeoff.recWriteoffPay.COLUMNWIDTH,
    	    	invalidText :writeoff.recWriteoffPay.i18n('foss.stl.writeoff.receivableWriteOffPayble.dateFormatError'),
    	    	allowBlank:false,
    	    	format:'Y-m-d'		    	
    	    }]	
        },{  
            xtype : "fieldset",  
            title : "应付单查询日期范围",
            layout : "column", 
            columnWidth : writeoff.recWriteoffPay.COLUMNWIDTH,
            items:[{
    	    	xtype:'datefield',
    	    	name:'payBusinessStartDate',
    	    	fieldLabel:writeoff.recWriteoffPay.i18n('foss.stl.writeoff.receivableWriteOffPayble.businessStartDate'),
    	    	value:stl.getTargetDate(new Date(),-writeoff.recWriteoffPay.STLBILLRECWRITEOFFBILLPAY_ONEMONTH),
    	    	maxValue:new Date(),
    	    	editable:false,
    	    	columnWidth:writeoff.recWriteoffPay.COLUMNWIDTH,
    	    	invalidText : writeoff.recWriteoffPay.i18n('foss.stl.writeoff.receivableWriteOffPayble.dateFormatError'),
    	    	format:'Y-m-d',
    	    	allowBlank:false
    	    },{
    	    	xtype:'datefield',
    	   	 	name:'payBusinessEndDate',
    	   	 	editable:false,
    	    	fieldLabel:writeoff.recWriteoffPay.i18n('foss.stl.writeoff.receivableWriteOffPayble.businessEndDate'),
    	    	value:new Date(),
    	    	columnWidth:writeoff.recWriteoffPay.COLUMNWIDTH,
    	    	invalidText :writeoff.recWriteoffPay.i18n('foss.stl.writeoff.receivableWriteOffPayble.dateFormatError'),
    	    	allowBlank:false,
    	    	maxValue:new Date(),
    	    	format:'Y-m-d'		    	
    	    }]	
        },{
        	xtype: 'container',
        	layout : "column", 
        	items: [{
    	    	xtype:'combobox',
    	    	name:'customerType',
    	    	fieldLabel:writeoff.recWriteoffPay.i18n('foss.stl.writeoff.receivableWriteOffPayble.customerType'),
    			store:Ext.create('Foss.writeoff.recWriteoffPay.CustomerTypeStore'),
    			queryModel:'local',
    			value:'01',
    			displayField:'customerTypeName',
    			valueField:'customerTypeCode',
    	    	columnWidth:writeoff.recWriteoffPay.COLUMNWIDTH,
    	    	listeners:{
    				"select": {
    					fn: function(_combo, _r){
    						var cusValue=_combo.getValue();
    						var commoncustomerselector = Ext.getCmp('writeoff.recWriteoffPay.commoncustomerselector');
    						var commonairagencycompanyselector = Ext.getCmp('writeoff.recWriteoffPay.commonairagencycompanyselector');
    						var commonvehagencycompselector = Ext.getCmp('writeoff.recWriteoffPay.commonvehagencycompselector');
    						var commonairlinesselector = Ext.getCmp('writeoff.recWriteoffPay.commonairlinesselector');
							var landStowage = Ext.getCmp('writeoff.recWriteoffPay.landStowage');
							var packagingCode = Ext.getCmp('writeoff.recWriteoffPay.packagingCode');
							var homesupply = Ext.getCmp('writeoff.recWriteoffPay.homesupply');
							var partnerOrg = Ext.getCmp('writeoff.recWriteoffPay.partnerOrg');
							var arrCode = ['01','02','03','04','05','06','07','08'];
							var arrEl = [commoncustomerselector,
							             commonairagencycompanyselector,
							             commonvehagencycompselector,
							             commonairlinesselector,
							             landStowage,
							             packagingCode,
							             homesupply,
							             partnerOrg];
							
							for(var i=0;i<arrCode.length;i++) { 
								
								if (arrCode[i] == cusValue){ // 选择某种客户
									arrEl[i].show();
									arrEl[i].setReadOnly(false);
									arrEl[i].setValue(null);
									
								}else {
									arrEl[i].hide();
									arrEl[i].setReadOnly(true);
									arrEl[i].setValue(null);
								}
							}
							
    					}
    				}
    			}
    	    },
    	    {	
    	    	xtype:'commoncustomerselector',
    	    	singlePeopleFlag : 'Y',
    	    	fieldLabel : writeoff.recWriteoffPay.i18n('foss.stl.writeoff.common.customerDetial'),
    	    	name : 'writeoff.recWriteoffPay.commoncustomerselector',
    	    	id:'writeoff.recWriteoffPay.commoncustomerselector',
    	    	all:'true',
    	    	columnWidth:writeoff.recWriteoffPay.COLUMNWIDTH,
    			labelWidth:90,
    			listWidth:300,//设置下拉框宽度
    			isPaging:true //分页
    	    },  {	
				xtype:'commonLdpAgencyCompanySelector',
				fieldLabel : writeoff.recWriteoffPay.i18n('foss.stl.writeoff.common.landStowage'),
				name : 'writeoff.recWriteoffPay.landStowage',
				id:'writeoff.recWriteoffPay.landStowage',
				columnWidth:writeoff.recWriteoffPay.COLUMNWIDTH,
				labelWidth:90,
				listWidth:300,//设置下拉框宽度
				hidden:true,
				isPaging:true //分页
			}
    	    ,{	
    	    	xtype:'commonairagentselector',
    	    	fieldLabel :writeoff.recWriteoffPay.i18n('foss.stl.writeoff.receivableWriteOffPayble.agencyInfo'),
    	    	name : 'writeoff.recWriteoffPay.commonairagencycompanyselector',
    	    	id:'writeoff.recWriteoffPay.commonairagencycompanyselector',
    	    	columnWidth:writeoff.recWriteoffPay.COLUMNWIDTH,
    			labelWidth:90,
    			listWidth:300,//设置下拉框宽度
    			isPaging:true ,//分页
    			hidden:true
    	    },
    	    {	
    	    	xtype:'commonvehagencycompselector',
    	    	fieldLabel :writeoff.recWriteoffPay.i18n('foss.stl.writeoff.receivableWriteOffPayble.vehAgencyCompany'),
    	    	name :'writeoff.recWriteoffPay.commonvehagencycompselector',
    	    	id:'writeoff.recWriteoffPay.commonvehagencycompselector',
    	    	columnWidth:writeoff.recWriteoffPay.COLUMNWIDTH,
    			labelWidth:90,
    			listWidth:300,//设置下拉框宽度
    			isPaging:true ,//分页
    			hidden:true
    	    },{
    			xtype:'commonairlinesselector',
    	    	fieldLabel :writeoff.recWriteoffPay.i18n('foss.stl.writeoff.receivableWriteOffPayble.airlineCompany'),
    	    	name : 'writeoff.recWriteoffPay.commonairlinesselector',
    	    	id:'writeoff.recWriteoffPay.commonairlinesselector',
    	    	columnWidth:writeoff.recWriteoffPay.COLUMNWIDTH,
				labelWidth:90,
				listWidth:300,//设置下拉框宽度
    			isPaging:true ,//分页
    			hidden:true
    	    },{
    			xtype:'dynamicPackagingSupplierSelector',
    	    	fieldLabel :writeoff.recWriteoffPay.i18n('foss.stl.writeoff.receivableWriteOffPayble.packagingSupplier'),
    	    	name : 'writeoff.recWriteoffPay.packagingCode',
    	    	id:'writeoff.recWriteoffPay.packagingCode',
    	    	columnWidth:writeoff.recWriteoffPay.COLUMNWIDTH,
				labelWidth:90,
				listWidth:300,//设置下拉框宽度
				active:'Y',
			    hidden:true
    	    },{
    			xtype:'supplierSelector',//家裝代理
    	    	fieldLabel :writeoff.recWriteoffPay.i18n('foss.stl.writeoff.receivableWriteOffPayble.homesupply'),
    	    	name : 'writeoff.recWriteoffPay.homesupply',
    	    	id:'writeoff.recWriteoffPay.homesupply',
    	    	columnWidth:writeoff.recWriteoffPay.COLUMNWIDTH,
				labelWidth:90,
				listWidth:300,//设置下拉框宽度
				active:'Y',
			    hidden:true
    	    },{ //合伙人
    	    	xtype:'commonsaledepartmentselector',
				fieldLabel:writeoff.recWriteoffPay.i18n('foss.stl.writeoff.receivableWriteOffPayble.partnerOrg'),
				colspan:1,
				name:'writeoff.recWriteoffPay.partnerOrg',
				id:'writeoff.recWriteoffPay.partnerOrg',
				columnWidth:writeoff.recWriteoffPay.COLUMNWIDTH,
				labelWidth:90,
				listWidth:300,//设置下拉框宽度
				hidden:true,
				isPaging:true 
    	    },{
    	    	xtype: 'combobox',
    			name:'showRecordCount',
    	        fieldLabel: writeoff.recWriteoffPay.i18n('foss.stl.writeoff.receivableWriteOffPayble.maxShowRow'),
    			store:Ext.create('Foss.writeoff.recWriteoffPay.ShowRecordCountStore'),
    			queryModel:'local',
    			displayField:'name',
    			valueField:'value',
    			value:'1000',
    	        columnWidth:writeoff.recWriteoffPay.COLUMNWIDTH
    	    }]
        }
	    ,{	
			border: 1,
			xtype:'container',
			columnWidth:1,
			defaultType:'button',
			layout:'column',
			items:[{
				text:writeoff.recWriteoffPay.i18n('foss.stl.writeoff.common.reset'),
				columnWidth:writeoff.recWriteoffPay.BNCOLUMNWIDTH,
				handler:writeoff.recWriteoffPay.reset
			},{
				xtype: 'container',
				border : false,
				columnWidth:writeoff.recWriteoffPay.CONWIDTH,
				html: '&nbsp;'
			},{
				text:writeoff.recWriteoffPay.i18n('foss.stl.writeoff.common.query'),
				cls:'yellow_button',
				columnWidth:writeoff.recWriteoffPay.BNCOLUMNWIDTH,
				handler:function(){
					var form=this.up('form');
					var me = this;
					writeoff.recWriteoffPay.queryWriteoffRecAndPay(form,me,writeoff.recWriteoffPay.queryRecWriteoffPay_TD);
				}
			}]
	    }]
    }]
});


//查询应收冲应付tab
Ext.define('Foss.writeoff.recWriteoffPay.StlrecWriteOffPaybleQueryInfoTab',{
	extend:'Ext.tab.Panel',
	frame:false,
	bodyCls: 'autoHeight',
	cls: 'innerTabPanel',
	activeTab: 0,
	items : [ {
		title: writeoff.recWriteoffPay.i18n('foss.stl.writeoff.receivableWriteOffPayble.queryDate'),
		tabConfig: {
			width: 120
		},
		width: '200',
        layout:'fit',
        items:[
               Ext.create('Foss.writeoff.recWriteoffPay.StlBillQueryByDateFrom')
               ]
	},{
		title: writeoff.recWriteoffPay.i18n('foss.stl.writeoff.receivableWriteOffPayble.queryBillNo'),
		tabConfig: {
			width: 120
		},
        layout:'fit',
        items:[
               Ext.create('Foss.writeoff.recWriteoffPay.StlBillQueryByPayRecNosFrom')
               ]
	}]
});


//应收单model
Ext.define('Foss.writeoff.recWriteoffPay.BillReceivableEntryModel',{
	idgen: 'uuid',//EXT在前台为每个模型额外以UUID方式生成主键
	idProperty : 'extid',//以上生成的主键用在名叫“extid”的列 
	extend:'Ext.data.Model',
	fields:[{
		//额外的用于生成的EXT使用的列 
		name: 'extid'
	},{
		name:'id'
	},{
		name:'versionNo'//应收单版本号
	},{
		name:'receivableNo'//应收单号
	},{
		name:'waybillNo'//运单号
	},{
		name:'customerName'//客户名称
	},{
		name:'customerCode'//客户编码
	},{
		name:'billType'//单据子类型	
	},{
		name:'amount', //总金额
		type:'long'
	},{
		name:'verifyAmount',//已核销金额
		type:'long'
	},{
		name:'unverifyAmount',//未核销金额
		type:'long'
	},{
		name:'businessDate',//业务日期
		type:'date',
		convert:stl.longToDateConvert
	},{
		name:'accountDate',//记账日期
		type:'date',
		convert:stl.longToDateConvert
	},{
		name:'statementBillNo'//对账单号
	}
	,{
		name:'approveStatus'//审核状态
	},{
		name:'active'
	},{
		name:'isRedBack'
	},{
		name:'conrevenDate',//确定收入日期
		type:'date',
		convert:stl.longToDateConvert
	},{
		name:'productCode'
	}]
});



//应收单store
Ext.define('Foss.writeoff.recWriteoffPay.BillReceivableEntryStore',{
	extend:'Ext.data.Store',
	model:'Foss.writeoff.recWriteoffPay.BillReceivableEntryModel',
	sorters: [{
	     property: 'customerCode',
	     direction: 'ASC'
	 },{
		 property:'amount',
		 direction:'ASC'
	 }]
});


//应收单列表可编辑
var SoperateColumnEditing = Ext.create('Ext.grid.plugin.CellEditing', {  
  	clicksToEdit : 1
}) ;



//应收单列表
Ext.define('Foss.writeoff.recWriteoffPay.StlreceivableQueryInfoGrid',{
	extend:'Ext.grid.Panel',
    title: writeoff.recWriteoffPay.i18n('foss.stl.writeoff.common.billReceivable'),
    emptyText:writeoff.recWriteoffPay.i18n('foss.stl.writeoff.common.noResult'),
	frame:true,
	height:420,
	//plugins:SoperateColumnEditing,
	selModel:Ext.create('Ext.selection.CheckboxModel'),
	amount:null,
	getAmount:function(){
		var me=this;
		if(Ext.isEmpty(me.amount)){
			me.amount=0;
		}
		return me.amount;
	},
	verifyAmount:null,
	getVerifyAmount:function(){
		var me=this;
		if(Ext.isEmpty(me.verifyAmount)){
			me.verifyAmount=0;
		}
		return me.verifyAmount;
	},
	unverifyAmount:null,
	getUnverifyAmount:function(){
		var me=this;
		if(Ext.isEmpty(me.unverifyAmount)){
			me.unverifyAmount=0;
		}
		return me.unverifyAmount;
	},
	columns:[{
		header: writeoff.recWriteoffPay.i18n('foss.stl.writeoff.common.receivableNo'), 
		dataIndex: 'receivableNo'
//		,
//		sortable: false
	},{
		header: writeoff.recWriteoffPay.i18n('foss.stl.writeoff.common.waybillNo'), 
		dataIndex: 'waybillNo'
//		,
//		sortable: false
	}, {
		header :writeoff.recWriteoffPay.i18n('foss.stl.writeoff.common.productCode'),
		dataIndex : 'productCode',
		renderer : function(value) {
			return Foss.pkp.ProductData.rendererSubmitToDisplay(value);
		}
	}
	,{
		header: writeoff.recWriteoffPay.i18n('foss.stl.writeoff.common.customerName'), 
		dataIndex: 'customerName'
//		,
//		sortable: false
	}
	,{
		header: writeoff.recWriteoffPay.i18n('foss.stl.writeoff.receivableWriteOffPayble.customerCode'), 
		dataIndex: 'customerCode'		
	},{
		header: writeoff.recWriteoffPay.i18n('foss.stl.writeoff.common.billType'), 
		dataIndex: 'billType',
		renderer:function(value){
    		var displayField = FossDataDictionary. rendererSubmitToDisplay (value,'BILL_RECEIVABLE__BILL_TYPE');
    		return displayField;
		}
//		,
//		sortable: false
	},{
		header: writeoff.recWriteoffPay.i18n('foss.stl.writeoff.common.amount'), 
		dataIndex:'amount',
		align: 'right',  
		renderer:stl.amountFormat
	},{
		header: writeoff.recWriteoffPay.i18n('foss.stl.writeoff.common.verifyAmount'),
		dataIndex:'verifyAmount',
		align: 'right',  
		renderer:stl.amountFormat
	},{
		header: writeoff.recWriteoffPay.i18n('foss.stl.writeoff.common.unverifyAmount'), 
		dataIndex:'unverifyAmount',
		align: 'right',  
		renderer:stl.amountFormat
	},{
		header: writeoff.recWriteoffPay.i18n('foss.stl.writeoff.common.businessDate'), 
		dataIndex:'businessDate',
		renderer:function(value){
    		return stl.dateFormat(value,'Y-m-d');
    	}
	},{
		header: writeoff.recWriteoffPay.i18n('foss.stl.writeoff.common.accountDate'), 
		dataIndex:'accountDate',
		renderer:function(value){
    		return stl.dateFormat(value,'Y-m-d');
    	}
	  },{
		  header:writeoff.recWriteoffPay.i18n('foss.stl.writeoff.common.versionNo'),
		  dataIndex:'versionNo',
		  hidden:true
	  },{
		  header:writeoff.recWriteoffPay.i18n('foss.stl.writeoff.receivableWriteOffPayble.statementNo'),
		  dataIndex:'statementBillNo'
	  },{
		 header:writeoff.recWriteoffPay.i18n('foss.stl.writeoff.common.approveStatus'),
		 dataIndex:'approveStatus',
		 hidden:true,
		 renderer:function(value){
	    		var displayField = FossDataDictionary. rendererSubmitToDisplay (value,'BILL_RECEIVABLE__APPROVE_STATUS');
	    		return displayField;
	    }
	  },{
		  header: writeoff.recWriteoffPay.i18n('foss.stl.writeoff.common.active'), 
		  dataIndex: 'active',
		  renderer:function(value){
			  var displayField = FossDataDictionary. rendererSubmitToDisplay (value,'FOSS_ACTIVE');
			  return displayField;
	    }
	  },{
		header: writeoff.recWriteoffPay.i18n('foss.stl.writeoff.common.isRedBack'), 
		dataIndex: 'isRedBack',
		renderer:function(value){
	    		var displayField = FossDataDictionary. rendererSubmitToDisplay (value,'SETTLEMENT__IS_RED_BACK');
	    		return displayField;
	    }
	  },{
		  header: writeoff.recWriteoffPay.i18n('foss.stl.writeoff.receivableWriteOffPayble.isIncomeDate'), 
		  dataIndex:'conrevenDate',
		  renderer:function(value){
	    		return stl.dateFormat(value,'Y-m-d');
	      }
	  }
	],
	constructor:function(config){
		var me = this;
		cfg = Ext.apply({},config);
		me.store=Ext.create('Foss.writeoff.recWriteoffPay.BillReceivableEntryStore');
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
				text:writeoff.recWriteoffPay.i18n('foss.stl.writeoff.common.alert'),
				columnWidth:.05
//				,handler:pay.repayment.repaymentForBillExport
			}]
		}];	
		me.callParent([cfg]);
	}
	,
	viewConfig : {  
        forceFit : true,  
        enableRowBody: true,
        enableTextSelection: true,
        stripeRows: false,
        getRowClass : function(record,rowIndex,rowParams,store){ 
            //禁用数据显示灰色  
        	var gridView=this;
            if(record.data.unverifyAmount==0){
            	gridView.getSelectionModel().deselect([record]);
                return 'disabledrow';  
            }else{  
                return '';  
            }  
              
        }  
    }
});


//应付单 model
Ext.define('Foss.writeoff.recWriteoffPay.BillPayableEntityModel',{
	idgen: 'uuid',//EXT在前台为每个模型额外以UUID方式生成主键
	idProperty : 'extid',//以上生成的主键用在名叫“extid”的列 
	extend:'Ext.data.Model',
	fields:[{
		//额外的用于生成的EXT使用的列 
		name: 'extid'
	},{
		name:'id'
	},{
		name:'versionNo'//应付单版本号
	},{
		name:'payableNo'//应付单
	},{
		name:'waybillNo'//运单号
	},{
		name:'customerCode'//客户名称
	},{
		name:'customerName'//客户编码
	},{
		name:'billType'//单据子类型
	},{
		name:'amount',//总金额
		type:'long'
	},{
		name:'verifyAmount',//已核销金额
		type:'long'
	},{
		name:'unverifyAmount',//未核销金额
		type:'long'
	},{
		name:'businessDate',//业务日期
		type:'date',
		convert:stl.longToDateConvert
	},{
		name:'accountDate',//记账日期
		type:'date',
		convert:stl.longToDateConvert
	}
	,{
		name:'statementBillNo'//对账单号
	  //应付单审核状态		
	}
	,{
		name:'approveStatus'
	}
	,{
		name:'active'
	},{
		name:'isRedBack'
	},{
		name:'payableOrgCode'
	},{
		name:'payableOrgName'//应付单名称
	},{
		name:'effectiveStatus'//生效状态
	},{
		name:'codType'
	},{
		//付款金额
		name:'paymentAmount',
		type:'long'
	},{
		name:'productCode'
	}]
});
//应付单 Store
Ext.define('Foss.writeoff.recWriteoffPay.BillPayableEntityStore',{
	extend:'Ext.data.Store',
	model:'Foss.writeoff.recWriteoffPay.BillPayableEntityModel',
	sorters: [{
	     property: 'payableNo',
	     direction: 'ASC'
	 }]
});

//应付单列表可编辑
var SoperateColumnEditing1 = Ext.create('Ext.grid.plugin.CellEditing', {  
  	clicksToEdit : 1
});

//应付单列表
Ext.define('Foss.writeoff.recWriteoffPay.StlpayableQueryInfoGrid',{
	extend:'Ext.grid.Panel',
	emptyText:writeoff.recWriteoffPay.i18n('foss.stl.writeoff.common.noResult'),
    title: writeoff.recWriteoffPay.i18n('foss.stl.writeoff.receivableWriteOffPayble.billPayable'),
	frame:true,
	height:420,
	//plugins:SoperateColumnEditing1,
	selModel:Ext.create('Ext.selection.CheckboxModel'),
    store: Ext.create('Foss.writeoff.recWriteoffPay.BillPayableEntityStore'),
	columns:[{
		header: writeoff.recWriteoffPay.i18n('foss.stl.writeoff.common.payableNo'), 
		dataIndex: 'payableNo'
		
//		, sortable: false
	},{
		header: writeoff.recWriteoffPay.i18n('foss.stl.writeoff.common.waybillNo'), 
		dataIndex: 'waybillNo'
//		,sortable: false
	}, {
		header :writeoff.recWriteoffPay.i18n('foss.stl.writeoff.common.productCode'),
		dataIndex : 'productCode',
		renderer : function(value) {
			return Foss.pkp.ProductData.rendererSubmitToDisplay(value);
		}
	}
	,{
		header: writeoff.recWriteoffPay.i18n('foss.stl.writeoff.common.customerName'), 
		dataIndex: 'customerName'
//		sortable: false
	},{
		header: writeoff.recWriteoffPay.i18n('foss.stl.writeoff.receivableWriteOffPayble.customerCode'), 
		dataIndex: 'customerCode'		
	},{
		header: writeoff.recWriteoffPay.i18n('foss.stl.writeoff.common.billType'), 
		dataIndex: 'billType',
//		sortable: false,
		renderer:function(value){
    		var displayField = FossDataDictionary. rendererSubmitToDisplay (value,'BILL_PAYABLE__BILL_TYPE');
    		return displayField;
		}
	}
	,{
		header: writeoff.recWriteoffPay.i18n('foss.stl.writeoff.common.amount'), 
		dataIndex: 'amount',
		align: 'right',  
		renderer:stl.amountFormat			
	},{
		header: writeoff.recWriteoffPay.i18n('foss.stl.writeoff.common.verifyAmount'),
		dataIndex: 'verifyAmount',
		align: 'right',  
		renderer:stl.amountFormat
	},{
		header: writeoff.recWriteoffPay.i18n('foss.stl.writeoff.common.unverifyAmount'), 
		dataIndex: 'unverifyAmount',
		align: 'right',  
		renderer:stl.amountFormat
	},{
		header: writeoff.recWriteoffPay.i18n('foss.stl.writeoff.common.businessDate'),
		dataIndex: 'businessDate',
		renderer:function(value){
    		return stl.dateFormat(value,'Y-m-d');
    	}
	 },{
		header: writeoff.recWriteoffPay.i18n('foss.stl.writeoff.common.accountDate'),
		dataIndex: 'accountDate',
		renderer:function(value){
    		return stl.dateFormat(value,'Y-m-d');
    	}
	 },{
		  header:writeoff.recWriteoffPay.i18n('foss.stl.writeoff.common.versionNo'),
		  dataIndex:'versionNo',
		  hidden:true
	  },{
		  header:writeoff.recWriteoffPay.i18n('foss.stl.writeoff.receivableWriteOffPayble.statementNo'),
		  dataIndex:'statementBillNo'
	  },{
		 header:writeoff.recWriteoffPay.i18n('foss.stl.writeoff.common.approveStatus'),
		 dataIndex:'approveStatus',
		 hidden:true,
		 renderer:function(value){
	    		var displayField = FossDataDictionary. rendererSubmitToDisplay (value,'BILL_PAYABLE__APPROVE_STATUS');
	    		return displayField;
	    }
	  },{
		  header: writeoff.recWriteoffPay.i18n('foss.stl.writeoff.common.active'), 
		  dataIndex: 'active',
		  hidden:true,
		  renderer:function(value){
			  var displayField = FossDataDictionary. rendererSubmitToDisplay (value,'FOSS_ACTIVE');
			  return displayField;
	    }
	  },{
		header: writeoff.recWriteoffPay.i18n('foss.stl.writeoff.common.isRedBack'), 
		dataIndex: 'isRedBack',
		 hidden:true,
		renderer:function(value){
	    		var displayField = FossDataDictionary. rendererSubmitToDisplay (value,'SETTLEMENT__IS_RED_BACK');
	    		return displayField;
	    }
	  }
	  ,{//应付部门编码
		 header: writeoff.recWriteoffPay.i18n('foss.stl.writeoff.receivableWriteOffPayble.payableDeptCode'), 
		 dataIndex: 'payableOrgCode',
		 sortable: false,
		 hidden:true
	  }
	  ,{//应付部门名称
		  header: writeoff.recWriteoffPay.i18n('foss.stl.writeoff.receivableWriteOffPayble.payableDeptName'), 
		  dataIndex: 'payableOrgName',
		  sortable: false ,
		  hidden:true
	  },{
		  header:writeoff.recWriteoffPay.i18n('foss.stl.writeoff.common.effectiveStatus'),
		  dataIndex:'effectiveStatus',
		  sortable: false,
		  hidden:true,
		  renderer:function(value){
	    		var displayField = FossDataDictionary. rendererSubmitToDisplay (value,'BILL_PAYABLE__EFFECTIVE_STATUS');
	    		return displayField;
	    }
	  },{
		  header:writeoff.recWriteoffPay.i18n('foss.stl.writeoff.receivableWriteOffPayble.RefundType'),
		  dataIndex:'codType',
		  sortable: false,
		  hidden:true,
		  renderer:function(value){
	    		var displayField = FossDataDictionary. rendererSubmitToDisplay (value,'COD__COD_TYPE');
	    		return displayField;
	    }
	  },{
		  header: writeoff.recWriteoffPay.i18n('foss.stl.writeoff.receivableWriteOffPayble.paymentAmount'),
		  dataIndex: 'paymentAmount',
		  align: 'right',  
		  renderer:stl.amountFormat
	  }
	],
	 viewConfig : {  
	        forceFit : true,  
	        //grid可选择复制
	        enableTextSelection: true,
	        enableRowBody: true,
	        stripeRows: false,
	        getRowClass : function(record,rowIndex,rowParams,store){ 
	        	var gridView = this;
	        	//禁用数据显示灰色  
	            if(record.data.unverifyAmount==0){  
	                gridView.getSelectionModel().deselect([record]);
	            	return 'disabledrow';  
	            }else{  
	                return '';  
	            }  
	              
	        }  
	    }
});



	var StlrecWriteOffPaybleQueryInfoTab = Ext.create('Foss.writeoff.recWriteoffPay.StlrecWriteOffPaybleQueryInfoTab');
	
	//应收单grid
	var StlreceivableQueryInfoGrid = Ext.create('Foss.writeoff.recWriteoffPay.StlreceivableQueryInfoGrid',{
		id:'Foss_writeoff_StlreceivableQueryInfoGrid_Id',hidden:true,
		dockedItems: [{
			xtype: 'toolbar',
			dock: 'bottom',
			layout:'column',
			items: [{
				height:5,
				columnWidth:1
			},{
				xtype:'container',
				border:false,
				html:'&nbsp;',
				columnWidth:1,
				layout:'hbox',
				items:[{
						xtype:'textfield',
						readOnly:true,
						name:'receivableTotalRows',
//						width:185,
						labelWidth:65,
						id:'Foss_writeoff_StlpayableQueryInfoGrid_receivableTotalRows_Id',
						fieldLabel:writeoff.recWriteoffPay.i18n('foss.stl.writeoff.receivableWriteOffPayble.totalRow')
					},{
						xtype:'textfield',
						readOnly:true,
						id:'Foss_writeoff_StlpayableQueryInfoGrid_receivableTotalAmount_Id',
						name:'receivableTotalAmount',
//						width:170,
						labelWidth:45,
						fieldLabel:writeoff.recWriteoffPay.i18n('foss.stl.writeoff.common.amount')
					},{
						xtype:'textfield',
						readOnly:true,
						name:'receivableVerifyAmount',
						id:'Foss_writeoff_StlpayableQueryInfoGrid_receivableVerifyAmount_Id',
//						width:220,
						labelWidth:85,
						fieldLabel:writeoff.recWriteoffPay.i18n('foss.stl.writeoff.common.verifyAmount')
					},{
						xtype:'textfield',
						readOnly:true,
						name:'receivableUnverityAmount',
						id:'Foss_writeoff_StlpayableQueryInfoGrid_receivableUnverityAmount_Id',
//						width:220,
						labelWidth:85,
						fieldLabel:writeoff.recWriteoffPay.i18n('foss.stl.writeoff.common.unverifyAmount')
					}]
			}]
		}
		,{
		    xtype: 'toolbar',
		    dock: 'top',
		    layout:'column',
		    items: [{
				xtype:'container',
				border:false,
				html:'&nbsp;',
				columnWidth:.9
			},{
				xtype:'button',
				text:writeoff.recWriteoffPay.i18n('foss.stl.writeoff.common.export'),
				columnWidth:.1,
				handler:writeoff.recWriteoffPay.exportWriteoffBillRec
			}]
		}
	]
});
		
	//应付单grid
	var StlpayableQueryInfoGrid = Ext.create('Foss.writeoff.recWriteoffPay.StlpayableQueryInfoGrid',{
		id:'Foss_writeoff_StlpayableQueryInfoGrid_Id',hidden:true,
		dockedItems: [{
			xtype: 'toolbar',
			dock: 'bottom',
			layout:'column',
			items: [{
				height:5,
				columnWidth:1
			},{
				xtype:'container',
				border:false,
				html:'&nbsp;',
				columnWidth:1,
				layout:'hbox',
				items:[{
							xtype:'textfield',
							readOnly:true,
							name:'payableTotalRows',
							width:180,
							labelWidth:65,
							id:'Foss_writeoff_StlpayableQueryInfoGrid_payableTotalRows_Id',
							fieldLabel:writeoff.recWriteoffPay.i18n('foss.stl.writeoff.receivableWriteOffPayble.totalRow')
						},{
							xtype:'textfield',
							readOnly:true,
							name:'payableTotalAmount',
							id:'Foss_writeoff_StlpayableQueryInfoGrid_payableTotalAmount_Id',
							width:170,
							labelWidth:45,
							fieldLabel:writeoff.recWriteoffPay.i18n('foss.stl.writeoff.common.amount')
						},{
							xtype:'textfield',
							readOnly:true,
							name:'payableVerifyAmount',
							width:220,
							labelWidth:85,
							id:'Foss_writeoff_StlpayableQueryInfoGrid_payableVerifyAmount_Id',
							fieldLabel:writeoff.recWriteoffPay.i18n('foss.stl.writeoff.common.verifyAmount')
						},{
							xtype:'textfield',
							readOnly:true,
							id:'Foss_writeoff_StlpayableQueryInfoGrid_payableUnverityAmount_Id',
							name:'payableUnverityAmount',
							width:220,
							labelWidth:85,
							fieldLabel:writeoff.recWriteoffPay.i18n('foss.stl.writeoff.common.unverifyAmount')
				}]
			},{
				xtype:'container',
				border:false,
				html:'&nbsp;',
				columnWidth:1
			},{
				xtype:'container',
				border:false,
				html:'&nbsp;',
				columnWidth:.9
			},{
				xtype:'button',
				text:writeoff.recWriteoffPay.i18n('foss.stl.writeoff.receivableWriteOffPayble.billWriteoff'),
				hidden:!writeoff.recWriteoffPay.isPermission('/stl-web/writeoff/writeoffBillReceivableAndBillPayable.action'),
				columnWidth:.1,
				handler:function(){
					/**
					 * 由于核销时，防止用户在页面停留太久，数据已经发生变化，将用户查询条件带入重新进行查询
					 */
					//获取界面选择的应收单集合
					var selectionRec = Ext.getCmp('Foss_writeoff_StlreceivableQueryInfoGrid_Id').getSelectionModel().getSelection();
					//获取界面选择应付单集合
					var selectionPay = Ext.getCmp('Foss_writeoff_StlpayableQueryInfoGrid_Id').getSelectionModel().getSelection();

					//获取tab
					var queryTab=Ext.getCmp('T_writeoff-recWriteOffPayble_content').items.items[0];
					//获取查询type辨别是按哪个表单查询
					var queryType =queryTab.getActiveTab().down('form').getQueryType();
					
					//获取当前选择的的form
					var form=queryTab.getActiveTab().down('form').getForm();
					
					/**
					 * 判断核销时，用户是否选择
					 */
					//判断用户是否选择核销的应收单记录和应付单记录
					if(selectionRec.length==0){
						Ext.Msg.alert(writeoff.recWriteoffPay.i18n('foss.stl.writeoff.common.alert'),writeoff.recWriteoffPay.i18n('foss.stl.writeoff.receivableWriteOffPayble.writeoffRecviceWarning'));
						return false;
					}
					if(selectionPay.length==0){
						Ext.Msg.alert(writeoff.recWriteoffPay.i18n('foss.stl.writeoff.common.alert'),writeoff.recWriteoffPay.i18n('foss.stl.writeoff.receivableWriteOffPayble.writeoffPayableWarning'));
						return false;
					}
					
					
					//获取界面grid应收单选中的记录
					var entity = new Object();
					var jsonDataRec = new Array();
					for(var i=0;i<selectionRec.length;i++){
						if(null!=selectionRec[i].data.unverifyAmount&&selectionRec[i].data.unverifyAmount<=0){
							Ext.Msg.alert(writeoff.recWriteoffPay.i18n('foss.stl.writeoff.common.alert'),writeoff.recWriteoffPay.i18n('foss.stl.writeoff.receivableWriteOffPayble.writeoffReceivableUnAmountLessThanZeroWarning'));
							return false;
						}
						jsonDataRec.push(selectionRec[i].data);
					}
					
					//选中的应付单grid选中的记录
					var jsonDataPay = new Array();
					for(var i=0;i<selectionPay.length;i++){
						if(null!=selectionPay[i].data.unverifyAmount&&selectionPay[i].data.unverifyAmount<=0){
							Ext.Msg.alert(writeoff.recWriteoffPay.i18n('foss.stl.writeoff.common.alert'),writeoff.recWriteoffPay.i18n('foss.stl.writeoff.receivableWriteOffPayble.writeoffPayableUnAmountLessThanZeroWarning'));
							return false;
						}
						jsonDataPay.push(selectionPay[i].data);
					}
					
					//界面校验是否客户编码相同
					for(var i=0;i<selectionRec.length;i++){
						for(var j=0;j<selectionPay.length;j++){
							if(null==selectionRec[i].data.customerCode||selectionRec[i].data.customerCode==''){
								Ext.Msg.alert(writeoff.recWriteoffPay.i18n('foss.stl.writeoff.common.alert'),writeoff.recWriteoffPay.i18n('foss.stl.writeoff.receivableWriteOffPayble.writeoffRecviceCustomerWarning'));
								return false;
							}
							if(null==selectionPay[j].data.customerCode||selectionPay[j].data.customerCode==''){
								Ext.Msg.alert(writeoff.recWriteoffPay.i18n('foss.stl.writeoff.common.alert'),writeoff.recWriteoffPay.i18n('foss.stl.writeoff.receivableWriteOffPayble.writeoffPayableCustomerWarning'));
								return false;
							}
							if(selectionRec[i].data.customerCode!=selectionPay[j].data.customerCode){
								Ext.Msg.alert(writeoff.recWriteoffPay.i18n('foss.stl.writeoff.common.alert'),writeoff.recWriteoffPay.i18n('foss.stl.writeoff.receivableWriteOffPayble.receivablePayableCustomerWarning'));
								return false;
							 }
						}
					}
					
					/**
					 * 2、当前用户是按日期或是单号查询,根据用户选择带入相应的条件
					 */
					var billRecWriteoffBillPayDto = new Object();
					entity.billRecWriteoffBillPayDto = billRecWriteoffBillPayDto;
					//按日期查询
					if(queryType=='TD'){
						
						
						var customerType=form.findField('customerType').getValue();
						var maxShowNum=form.findField('showRecordCount').getValue();
						
						//应收单开始日期、结束日期
						var recBusinessStartDate=form.findField('recBusinessStartDate').getValue();
						var recBusinessEndDate=form.findField('recBusinessEndDate').getValue();

						//应付单开始日期、结束日期		
						var payBusinessStartDate=form.findField('payBusinessStartDate').getValue();
						var payBusinessEndDate=form.findField('payBusinessEndDate').getValue();
						
						
						//客户编码
		        		var customerCode = "";
		        		var commoncustomerselector = form.findField('writeoff.recWriteoffPay.commoncustomerselector').getValue();
		            	var commonairagencycompanyselector = form.findField('writeoff.recWriteoffPay.commonairagencycompanyselector').getValue();
		            	var commonvehagencycompselector = form.findField('writeoff.recWriteoffPay.commonvehagencycompselector').getValue();
		            	var commonairlinesselector = form.findField('writeoff.recWriteoffPay.commonairlinesselector').getValue();
		        		if(commoncustomerselector != "" && commoncustomerselector != null){
		        			customerCode = commoncustomerselector;
		        		}else if(commonairagencycompanyselector != "" && commonairagencycompanyselector != null){
		        			customerCode = commonairagencycompanyselector;
		        		}else if(commonvehagencycompselector != "" && commonvehagencycompselector != null){
		        			customerCode = commonvehagencycompselector;
		        		}else if(commonairlinesselector != "" && commonairlinesselector != null){
		        			customerCode = commonairlinesselector;
		        		}
						
		        		//核销时用于应收单开始日期结束日期、应付单的开始日期、结束日期		        		
						entity.billRecWriteoffBillPayDto.recBusinessStartDate=recBusinessStartDate;
						entity.billRecWriteoffBillPayDto.recBusinessEndDate=recBusinessEndDate;
						entity.billRecWriteoffBillPayDto.payBusinessStartDate=payBusinessStartDate;
						entity.billRecWriteoffBillPayDto.payBusinessEndDate=payBusinessEndDate;
						
						entity.billRecWriteoffBillPayDto.maxShowNum=maxShowNum;
						entity.billRecWriteoffBillPayDto.customerCode=customerCode;
						entity.billRecWriteoffBillPayDto.customerType=customerType;
						
						//用户核销时，选择的应收单号和应付单号
						entity.billRecWriteoffBillPayDto.billReceivableEntityList=jsonDataRec;
						entity.billRecWriteoffBillPayDto.billPayableEntityList=jsonDataPay;
						//按单号查询
					}else if(queryType=='RP'){
						//获取界面应收单号
						var receivableNosId=form.findField('receivableNos').getValue();
						//获取界面应付单号
						var payableNosId=form.findField('payableNos').getValue();
						
						//用户核销时，选择的应收单号和应付单号
						entity.billRecWriteoffBillPayDto.billReceivableEntityList=jsonDataRec;
						entity.billRecWriteoffBillPayDto.billPayableEntityList=jsonDataPay;
						//用户查询时输入的单号
						entity.billRecWriteoffBillPayDto.receivableNosOrWaybillNos=receivableNosId;
						entity.billRecWriteoffBillPayDto.payNosOrWaybillNos=payableNosId;
					}
					
					//点击核销
					Ext.Ajax.request({
						url:writeoff.realPath('writeoffBillReceivableAndBillPayable.action'),
						jsonData:{'billRecWriteoffBillPayVo':entity},
						method:'post',
						success:function(response){
							var result = Ext.decode(response.responseText);
							var billReceivableEntryStore = Ext.getCmp('Foss_writeoff_StlreceivableQueryInfoGrid_Id').store;
							var billPayableEntityStore = Ext.getCmp('Foss_writeoff_StlpayableQueryInfoGrid_Id').store;
							
							
							//判断是否有数据提示
							var billReceivableLength = 0;
							if(!Ext.isEmpty(result.billRecWriteoffBillPayResultVo.billReceivableEntityList)){
								billReceivableLength = result.billRecWriteoffBillPayResultVo.billReceivableEntityList.length;
							}
							
							var billPayableLength = 0;
							if(!Ext.isEmpty(result.billRecWriteoffBillPayResultVo.billPayableEntityList)){
								billPayableLength = result.billRecWriteoffBillPayResultVo.billPayableEntityList;
							}
							
							//判断是否有数据提示
							if(billReceivableLength == 0 || billPayableLength == 0){
								Ext.Msg.alert(writeoff.recWriteoffPay.i18n('foss.stl.writeoff.common.alert'),writeoff.recWriteoffPay.i18n('foss.stl.writeoff.receivableWriteOffPayble.notFoundReceivableAndPaybleWarning'),function(){
									Ext.getCmp('Foss_writeoff_StlreceivableQueryInfoGrid_Id').hide();
								    Ext.getCmp('Foss_writeoff_StlpayableQueryInfoGrid_Id').hide();
								});	
							}else if(billReceivableLength == 0){
								
								Ext.Msg.alert(writeoff.recWriteoffPay.i18n('foss.stl.writeoff.common.alert'),writeoff.recWriteoffPay.i18n('foss.stl.writeoff.receivableWriteOffPayble.notFoundReceivableWarning'),function(){
									Ext.getCmp('Foss_writeoff_StlreceivableQueryInfoGrid_Id').hide();
								});
								
								billPayableEntityStore.loadData(result.billRecWriteoffBillPayResultVo.billPayableEntityList);
								var payTotalAmount=writeoff.convertAmount(result.billRecWriteoffBillPayResultVo.payTotalAmount);
								var payUnverifyTotalAmount =writeoff.convertAmount(result.billRecWriteoffBillPayResultVo.payUnverifyTotalAmount);
								var payableVerifyAmount=writeoff.convertAmount(result.billRecWriteoffBillPayResultVo.payVerifyTotalAmount);
								
								Ext.getCmp('Foss_writeoff_StlpayableQueryInfoGrid_payableTotalAmount_Id').setValue(payTotalAmount);
								Ext.getCmp('Foss_writeoff_StlpayableQueryInfoGrid_payableUnverityAmount_Id').setValue(payUnverifyTotalAmount);
								Ext.getCmp('Foss_writeoff_StlpayableQueryInfoGrid_payableVerifyAmount_Id').setValue(payableVerifyAmount);
								Ext.getCmp('Foss_writeoff_StlpayableQueryInfoGrid_payableTotalRows_Id').setValue(result.billRecWriteoffBillPayResultVo.payTotalNum);
								Ext.getCmp('Foss_writeoff_StlpayableQueryInfoGrid_Id').show();
									
							}else if(billPayableLength == 0){
								Ext.Msg.alert(writeoff.recWriteoffPay.i18n('foss.stl.writeoff.common.alert'),writeoff.recWriteoffPay.i18n('foss.stl.writeoff.receivableWriteOffPayble.notFoundPaybleWarning'),function(){
								    Ext.getCmp('Foss_writeoff_StlpayableQueryInfoGrid_Id').hide();
								});	
								
								//加载后台查询到的数据到grid的store中
								billReceivableEntryStore.loadData(result.billRecWriteoffBillPayResultVo.billReceivableEntityList);
								var recTotalAmount=writeoff.convertAmount(result.billRecWriteoffBillPayResultVo.recTotalAmount);
								var recUnverifyTotalAmount=writeoff.convertAmount(result.billRecWriteoffBillPayResultVo.recUnverifyTotalAmount);
								var recVerifyTotalAmount=writeoff.convertAmount(result.billRecWriteoffBillPayResultVo.recVerifyTotalAmount);
								
								Ext.getCmp('Foss_writeoff_StlpayableQueryInfoGrid_receivableTotalAmount_Id').setValue(recTotalAmount);
								Ext.getCmp('Foss_writeoff_StlpayableQueryInfoGrid_receivableUnverityAmount_Id').setValue(recUnverifyTotalAmount);
								Ext.getCmp('Foss_writeoff_StlpayableQueryInfoGrid_receivableVerifyAmount_Id').setValue(recVerifyTotalAmount);
								Ext.getCmp('Foss_writeoff_StlreceivableQueryInfoGrid_Id').show();
							}else{
								//加载后台查询到的数据到grid的store中
								billReceivableEntryStore.loadData(result.billRecWriteoffBillPayResultVo.billReceivableEntityList);
								billPayableEntityStore.loadData(result.billRecWriteoffBillPayResultVo.billPayableEntityList);
								
								//动态给GRID设置值
								Ext.getCmp('Foss_writeoff_StlpayableQueryInfoGrid_receivableTotalRows_Id').setValue(result.billRecWriteoffBillPayResultVo.recTotalNum);
								Ext.getCmp('Foss_writeoff_StlpayableQueryInfoGrid_payableTotalRows_Id').setValue(result.billRecWriteoffBillPayResultVo.payTotalNum);
								
								var recTotalAmount=writeoff.convertAmount(result.billRecWriteoffBillPayResultVo.recTotalAmount);
								var recUnverifyTotalAmount=writeoff.convertAmount(result.billRecWriteoffBillPayResultVo.recUnverifyTotalAmount);
								var recVerifyTotalAmount=writeoff.convertAmount(result.billRecWriteoffBillPayResultVo.recVerifyTotalAmount);
								
								Ext.getCmp('Foss_writeoff_StlpayableQueryInfoGrid_receivableTotalAmount_Id').setValue(recTotalAmount);
								Ext.getCmp('Foss_writeoff_StlpayableQueryInfoGrid_receivableUnverityAmount_Id').setValue(recUnverifyTotalAmount);
								Ext.getCmp('Foss_writeoff_StlpayableQueryInfoGrid_receivableVerifyAmount_Id').setValue(recVerifyTotalAmount);
								
								var payTotalAmount=writeoff.convertAmount(result.billRecWriteoffBillPayResultVo.payTotalAmount);
								var payUnverifyTotalAmount =writeoff.convertAmount(result.billRecWriteoffBillPayResultVo.payUnverifyTotalAmount);
								var payableVerifyAmount=writeoff.convertAmount(result.billRecWriteoffBillPayResultVo.payVerifyTotalAmount);
								
								Ext.getCmp('Foss_writeoff_StlpayableQueryInfoGrid_payableTotalAmount_Id').setValue(payTotalAmount);
								Ext.getCmp('Foss_writeoff_StlpayableQueryInfoGrid_payableUnverityAmount_Id').setValue(payUnverifyTotalAmount);
								Ext.getCmp('Foss_writeoff_StlpayableQueryInfoGrid_payableVerifyAmount_Id').setValue(payableVerifyAmount);
								
								Ext.getCmp('Foss_writeoff_StlreceivableQueryInfoGrid_Id').show();
								Ext.getCmp('Foss_writeoff_StlpayableQueryInfoGrid_Id').show();
								//核销成功！
								Ext.Msg.alert(writeoff.recWriteoffPay.i18n('foss.stl.writeoff.common.alert'),writeoff.recWriteoffPay.i18n('foss.stl.writeoff.receivableWriteOffPayble.writeoffSuccess'));
							}
						},
						exception:function(response){
							var result = Ext.decode(response.responseText);
							Ext.Msg.alert(writeoff.recWriteoffPay.i18n('foss.stl.writeoff.common.alert'),result.message);
						}
					});
				}
			}
		]
	}
	,{
	    xtype: 'toolbar',
	    dock: 'top',
	    layout:'column',
	    items: [{
			xtype:'container',
			border:false,
			html:'&nbsp;',
			columnWidth:.9
		},{
			xtype:'button',
			text:writeoff.recWriteoffPay.i18n('foss.stl.writeoff.common.export'),
			columnWidth:.1,
			handler:writeoff.recWriteoffPay.exportWriteoffBillPay
		}]
	}]
 });
	

	
//初始化界面
Ext.onReady(function() {
	Ext.QuickTips.init();
	Ext.create('Ext.panel.Panel',{
		id: 'T_writeoff-recWriteOffPayble_content',
		cls: "panelContentNToolbar",
		bodyCls: 'panelContentNToolbar-body',
		layout: 'auto',
		items: [StlrecWriteOffPaybleQueryInfoTab,StlreceivableQueryInfoGrid,StlpayableQueryInfoGrid],
		renderTo: 'T_writeoff-recWriteOffPayble-body'
	});
});