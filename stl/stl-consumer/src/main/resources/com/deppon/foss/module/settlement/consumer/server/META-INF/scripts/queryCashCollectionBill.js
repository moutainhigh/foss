//全局运单和日期
consumer.WAYBILLNO = 'bilno'
consumer.DATE = 'date'

/**
 * 
 * @param {} 产品类型
 * @return {}此处如果有全选，则需要转化下
 */
consumer.queryCashCollectionBill.convertProductCode = function(productCodes){
	if(!Ext.isEmpty(productCodes)){
		var productCodeList = [];
		for(var i=0;i<productCodes.length;i++){
			//如果产品类型中存在值为空，则表明为全部，那么默认全部查询
			if(Ext.isEmpty(productCodes[i])){
				productCodeList=[];
				break;
			}else{
				productCodeList.push(productCodes[i]);
			}
		}
		return productCodeList;
	}else{
		return [];
	}
}

/**
 * 按银联交易流水号查询
 */
consumer.queryCashCollectionBill.queryByBatchNos = function(){
	var form = this.up('form').getForm();
	if (form.isValid()) {
	var batchNosArray =[];
	var batchNos = this.up('form').getForm().findField('batchNos').getValue();
	if(Ext.String.trim(batchNos)!=null && Ext.String.trim(batchNos)!=''){
		 var reg = /[,;]/;  
		 array = batchNos.split(reg);
		 for(var i=0;i<array.length;i++){
		 	if(Ext.String.trim(array[i])!=''){
		 		batchNosArray.push(array[i]);	
		 	}
		 }
	}else{
		Ext.Msg.alert(consumer.queryCashCollectionBill.i18n('foss.stl.consumer.common.warmTips'),consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.mustInputNoForQuery'));
		return false;
	}
	
	if(batchNosArray.length>10){
		Ext.Msg.alert(consumer.queryCashCollectionBill.i18n('foss.stl.consumer.common.warmTips'),consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.cannotExceed10SeriaNos.pleaseCheckAgain'));
		return false;
	}
	var params = new Object();
	
	Ext.Ajax.request({
		url:consumer.realPath('queryCashCollectionBillByBatchNo.action'),
		params:{
			"billCashCollectionQueryVO.batchNos" : batchNosArray
		},
		method:'post',
		success:function(response){
			var result = Ext.decode(response.responseText);
			var billNosStore = Ext.getCmp('Foss_CashCollectionBill_BatchNoQueryInfoGrid_Id').store;
			
			//每次查询先把上一次的数据清除
			
			billNosStore.removeAll();
			
			//加载后台查询到的数据到grid的store中
			if(!Ext.isEmpty(result.billCashCollectionQueryVO.billCashCollectionList)){
				//加载后台查询到的数据到grid的store中
				billNosStore.loadData(result.billCashCollectionQueryVO.billCashCollectionList);
			}
			
			//动态给GRID设置值
			Ext.getCmp('Foss_CashCollectionBill_BatchNoQueryInfoGrid_TotalRecords_Id').setValue(result.billCashCollectionQueryVO.totalRecordsInDB);
			Ext.getCmp('Foss_CashCollectionBill_BatchNoQueryInfoGrid_TotalAmount_Id').setValue(result.billCashCollectionQueryVO.totalAmount);
	
			//获取数据判断是否为空
			var billNosStoreIsNull = result.billCashCollectionQueryVO.billCashCollectionList;
			if(billNosStoreIsNull.length > 0 ) {
				Ext.getCmp('Foss_CashCollectionBill_BatchNoQueryInfoGrid_Id').show();
				}
			},
			failure:function(response){
				var result = Ext.decode(response.responseText);
			}
		});						
	}else{
		Ext.Msg.alert(consumer.queryCashCollectionBill.i18n('foss.stl.consumer.common.warmTips'),consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.pleaseCheckInputIsLegal'));
	}
}

	
	//是否有效版本
	Ext.define('Foss.CashCollectionBill.IsActiveModel',{
		extend:'Ext.data.Model',
		fields:[{
				name:'name'
			},{
				name:'value'
			}]
	});
	
	Ext.define('Foss.CashCollectionBill.IsActiveStore',{
		extend:'Ext.data.Store',
		model:'Foss.CashCollectionBill.IsActiveModel',
		data:[
				{name:consumer.queryCashCollectionBill.i18n('foss.stl.consumer.common.all'),value:''},
				{name:consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.Y'),value:'Y'},
				{name:consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.N'),value:'N'}
		]
	});

	//现金收款单支付方式
	Ext.define('Foss.CashCollectionBill.PaymentTypeModel',{
		extend:'Ext.data.Model',
		fields:[{
				name:'name'
			},{
				name:'value'
			}]
	});
	
	Ext.define('Foss.CashCollectionBill.PaymentTypeStore',{
		extend:'Ext.data.Store',
		model:'Foss.CashCollectionBill.PaymentTypeModel',
		data:[
				{name:consumer.queryCashCollectionBill.i18n('foss.stl.consumer.common.all'),value:''},
				{name:consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.paymentType.CH'),value:'CH'},
				{name:consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.CD'),value:'CD'}
		]
	});

	//通用时间校验
	consumer.dateValidation = function(form){
		var startTime = form.findField('startDate').getValue();
		var endTime = form.findField('endDate').getValue();
		
			if(Ext.isEmpty(startTime)){
				Ext.Msg.alert(consumer.queryCashCollectionBill.i18n('foss.stl.consumer.common.warmTips'),consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.startDateCannotBeNull'));
				return false;
			}
			
			if(Ext.isEmpty(endTime)){
				Ext.Msg.alert(consumer.queryCashCollectionBill.i18n('foss.stl.consumer.common.warmTips'),consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.endDateCannotBeNull'));
				return false;
			}
			
			if(startTime > endTime){
				Ext.Msg.alert(consumer.queryCashCollectionBill.i18n('foss.stl.consumer.common.warmTips'),consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.startDateCannotGtEndDate'));
				return false;
			}
			
			if(stl.compareTwoDate(startTime,endTime) > stl.DATELIMITDAYS_MONTH){
				
				//开始时间与结束时间相差不能超过{0}天
				//stl.DATELIMITDAYS_MONTH
				Ext.Msg.alert(consumer.queryCashCollectionBill.i18n('foss.stl.consumer.common.warmTips'),consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.startAndEndDateDiffCannotExceedSomeDays',[stl.DATELIMITDAYS_MONTH]));
				return false;
			}
		
		return true;
}

	
//查看详细函数
consumer.checkCashCollectionBillDetailsInforByDoubleClickInfor = function(record, type){
	
	//按单号查询时获取明细window	
	if(type == 'date'){
		var win = Ext.getCmp('Foss_CashCollectionBill_DateQueryInfoGrid_Id').getDetailWindow();
	}
	
	//按单号查询时获取明细window	
	else if(type == 'bilno'){
		var win = Ext.getCmp('Foss_CashCollectionBill_BillNoQueryInfoGrid_Id').getDetailWindow();
	}else{
		return false;
	}
	
	//获取window上的表单	
	var form = win.down('form').getForm();
	
	//获取按运单查询单号	、产品名称	
	form.findField('waybillNo').setValue(record.get('waybillNo'));
	var productName = Foss.pkp.ProductData.rendererSubmitToDisplay(record.get('productCode'));
	form.findField('productCode').setValue(productName);
	
	
	form.findField('cashCollectionNo').setValue(record.get('cashCollectionNo'));
	form.findField('customerName').setValue(record.get('customerName'));
	form.findField('customerCode').setValue(record.get('customerCode'));
	form.findField('generatingOrgName').setValue(record.get('generatingOrgName'));
	form.findField('generatingCompanyName').setValue(record.get('generatingCompanyName'));
	form.findField('collectionOrgName').setValue(record.get('collectionOrgName'));
	form.findField('collectionCompanyName').setValue(record.get('collectionCompanyName'));
	form.findField('amount').setValue(record.get('amount'));
	form.findField('transportFee').setValue(record.get('transportFee'));
	form.findField('pickupFee').setValue(record.get('pickupFee'));
	form.findField('deliveryGoodsFee').setValue(record.get('deliveryGoodsFee'));
	form.findField('packagingFee').setValue(record.get('packagingFee'));
	form.findField('codFee').setValue(record.get('codFee'));
	form.findField('insuranceFee').setValue(record.get('insuranceFee'));
	form.findField('otherFee').setValue(record.get('otherFee'));
	form.findField('smallFee').setValue(record.get('smallFee'));
	form.findField('createUserName').setValue(record.get('createUserName'));
	form.findField('createUserCode').setValue(record.get('createUserCode'));
	
	form.findField('cashConfirmUserName').setValue(record.get('cashConfirmUserName'));
	form.findField('cashConfirmUserCode').setValue(record.get('cashConfirmUserCode'));
	form.findField('notes').setValue(record.get('notes'));
	form.findField('expressOrigOrgCode').setValue(record.get('expressOrigOrgCode'));
	form.findField('expressOrigOrgName').setValue(record.get('expressOrigOrgName'));
	form.findField('expressDestOrgCode').setValue(record.get('expressDestOrgCode'));
	form.findField('expressDestOrgName').setValue(record.get('expressDestOrgName'));
	// add dp-liqin 来源单号
	form.findField('sourceBillNo').setValue(record.get('sourceBillNo'));
	
	
	//根据设置查询时，业务日期的值	
	if(record.get('businessDate') != null){
		form.findField('businessDate').setValue(Ext.Date.format(new Date(record.get('businessDate')), 'Y-m-d H:i:s'));
	}else{
		form.findField('businessDate').setValue(record.get('businessDate'));
	}
	
	// add dp-liqin 来源单号
	//来源单据类型	
	if(record.get('sourceBillType')!=null){
		form.findField('sourceBillType').setValue(FossDataDictionary. rendererSubmitToDisplay (record.get('sourceBillType'),'BILL_CASH_COLLECTION__SOURCE_BILL_TYPE'));
	}
	
	//设置支付类型	
	if(record.get('paymentType')!=null){
		form.findField('paymentType').setValue(FossDataDictionary. rendererSubmitToDisplay (record.get('paymentType'),'SETTLEMENT__PAYMENT_TYPE'));//现金
	}
	
	//现金收款单状态
	if(record.get('status')!=null){
		form.findField('status').setValue(FossDataDictionary. rendererSubmitToDisplay(record.get('status'),'BILL_CASH_COLLECTION__STATUS'));
	}
	
	
	
	//是否有效	
	if(record.get('active') !=null){
		form.findField('active').setValue(FossDataDictionary. rendererSubmitToDisplay(record.get('active'),settlementDict.FOSS_ACTIVE));
	}
	
	
	
	
	//是否红单
	if(record.get('isRedBack')!=null){
		form.findField('isRedBack').setValue(FossDataDictionary. rendererSubmitToDisplay(record.get('isRedBack'),settlementDict.SETTLEMENT__IS_RED_BACK));
	}
	
	//是否初始化	
	if(record.get('isInit')!=null){
		form.findField('isInit').setValue(FossDataDictionary. rendererSubmitToDisplay(record.get('isInit'),settlementDict.FOSS_BOOLEAN));
	}
	
	//单据类型	
	var billType = record.get('billType');
	if(billType == null){
		form.findField('billType').setValue(null);
	}else{
		form.findField('billType').setValue(consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.cashCollectionBill'));
	}

	
	//账号日期	
	if(record.get('accountDate') != null){
		form.findField('accountDate').setValue(Ext.Date.format(new Date(record.get('accountDate')), 'Y-m-d H:i:s'));
	}else{
		form.findField('accountDate').setValue(record.get('accountDate'));
	}
	
	//收银确认时间	
	if(record.get('cashConfirmTime') != null){
		form.findField('cashConfirmTime').setValue(Ext.Date.format(new Date(record.get('cashConfirmTime')), 'Y-m-d H:i:s'));
	}else{
		form.findField('cashConfirmTime').setValue(record.get('cashConfirmTime'));
	}
			
	//发票标记		
	if(record.get('invoiceMark')!=null)
	{
		var value=record.get('invoiceMark');
		var displayField = FossDataDictionary.rendererSubmitToDisplay (value,'SETTLEMENT_INVOICE_MARK');		
		form.findField('invoiceMark').setValue(displayField);	
	}else
	{
		form.findField('invoiceMark').setValue(record.get('invoiceMark'));
	}
	
	
	win.show();
}

//通过按钮查看明细
consumer.checkCashCollectionBillDetailsInfor = function(grid, rowIndex, colIndex,type){
	var record = grid.getStore().getAt(rowIndex);
	//调用明细设置的详细方法	
	consumer.checkCashCollectionBillDetailsInforByDoubleClickInfor(record,type);
}


/**
 * 导出excel
 */
consumer.exportBillCashCollection = function(queryTab){
	var	columns,
		searchParams,
		arrayColumns,
		arrayColumnNames,
		grid,
		store;
		
	if ('TD' == queryTab) {
		//获取表格
		grid = Ext.getCmp('Foss_CashCollectionBill_DateQueryInfoGrid_Id');
	}else if('WB' == queryTab){
		//获取表格
		grid = Ext.getCmp('Foss_CashCollectionBill_BillNoQueryInfoGrid_Id');
	}else if('SB' == queryTab){
		//获取表格
		grid = Ext.getCmp('Foss_CashCollectionBill_SourceNoQueryInfoGrid_Id');
	}else {
		//获取表格
		grid = Ext.getCmp('Foss_CashCollectionBill_BatchNoQueryInfoGrid_Id');
	}
	
	//如果没有查询到数据，则不能进行导出操作
	if(grid.store.data.length==0){
		Ext.Msg.alert(consumer.queryCashCollectionBill.i18n('foss.stl.consumer.common.warmTips'),consumer.queryCashCollectionBill.i18n('foss.stl.consumer.common.noDataToExport'));
		return false;
	}
	
	//进行导出提示
	Ext.MessageBox.show({
        title: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.common.warmTips'),
        msg: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.isExport'),
        buttons: Ext.MessageBox.YESNOCANCEL,
        buttonText:{ 
            yes: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.defaultColumnExport'), 
            no: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.customColumnExport'),
            cancel:consumer.queryCashCollectionBill.i18n('foss.stl.consumer.common.cancel')
        },
        
        fn: function(e){
        	if(e=='yes'){
        		//默认显示列
				arrayColumns = ['waybillNo','customerName','productCode','generatingOrgName','collectionOrgName',
								'cashCollectionNo','sourceBillNo','posSerialNum','batchNo','collectionType','status',
								'amount','billType','paymentType','transportFee'];
				//默认显示列名称
				arrayColumnNames = [consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.waybillNo'),consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.customerName'),consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.productCode'),consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.generatingOrgName'),consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.collectionOrgName'),
									consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.cashCollectionNo'),consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.sourceBillNo'),consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.posSerialNum'),consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.batchNo'),consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.collectionType'),consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.status'),
									consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.amount'),consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.billType'),consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.paymentType'),consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.transportFee')];									
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
						if(columns[i].text!=consumer.queryCashCollectionBill.i18n('foss.stl.consumer.common.actionColumn')){
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
			
			//按客户查询
			if(queryTab=='TD'){
				var form = Ext.getCmp('Foss_CashCollectionBill_QueryByDate_ID').getForm();

				if (!consumer.dateValidation(form)) {
					Ext.Msg.alert(consumer.queryCashCollectionBill.i18n('foss.stl.consumer.common.warmTips'), consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.pleaseCheckInputIsLegal'));
					return false;
				}
		
				var billReceivableVO = new Object();
				var selectBusinessDate = Ext.getCmp(
						'queryCashCollectionBill_BusinessData_Id').getValue();
				var selectAccountDate = Ext
						.getCmp('queryCashCollectionBill_AccountData_Id').getValue();
				var selectDateType;
				if (selectBusinessDate && !selectAccountDate) {
					selectDateType = 'BU';
		
				}else if (!selectBusinessDate && selectAccountDate) {
					selectDateType = 'AC';
				}else{}

				var startDate = form.findField('startDate').getValue();
				var endDate = form.findField('endDate').getValue();
				var searchParams = {		
					'billCashCollectionQueryVO.dto.queryTab' : 'TD',
					'billCashCollectionQueryVO.dto.selectDateType':selectDateType,
					'billCashCollectionQueryVO.dto.startDate':startDate,
					'billCashCollectionQueryVO.dto.endDate':endDate,
					'billCashCollectionQueryVO.dto.active':form.findField('active').getValue(),
					'billCashCollectionQueryVO.dto.collectionOrgCode':form.findField('collectionOrgName').getValue(),
					'billCashCollectionQueryVO.dto.generatingOrgCode':form.findField('generatingOrgName').getValue(),
					'billCashCollectionQueryVO.dto.customerCode':form.findField('customerName').getValue(),
					'billCashCollectionQueryVO.dto.status':form.findField('status').getValue(),
					'billCashCollectionQueryVO.dto.paymentType':form.findField('paymentType').getValue(),
					'billCashCollectionQueryVO.dto.productCode':consumer.queryCashCollectionBill.convertProductCode(form.findField('productCode').getValue()),
					'billCashCollectionQueryVO.dto.posSerialNum':Ext.String.trim(form.findField('posSerialNum').getValue()),
					'billCashCollectionQueryVO.dto.isInit':form.findField('isInit').getValue(),
					'billCashCollectionQueryVO.dto.arrayColumns':arrayColumns,
					'billCashCollectionQueryVO.dto.arrayColumnNames':arrayColumnNames
				};
			
			}
			else if('WB' == queryTab){
					var form = Ext.getCmp('Foss_CashCollectionBill_WayBillNo_ID').getForm();

					var wayBillNosArray = [];
					var waybillNos = form.findField('waybillNos').getValue();
					if (Ext.String.trim(waybillNos) != null
							&& Ext.String.trim(waybillNos) != '') {
						var reg = /[,;]/;
						array = waybillNos.split(reg);
						for ( var i = 0; i < array.length; i++) {
							if (Ext.String.trim(array[i]) != '') {
								wayBillNosArray.push(array[i]);
							}
						}
					} else {
						Ext.Msg.alert(consumer.queryCashCollectionBill.i18n('foss.stl.consumer.common.warmTips'), consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.mustInputNoForQuery'));
						return false;
					}
				searchParams = {
					"billCashCollectionQueryVO.dto.queryTab" : 'WB',
					"billCashCollectionQueryVO.wayBillNosArray" : wayBillNosArray,
					'billCashCollectionQueryVO.dto.arrayColumns':arrayColumns,
					'billCashCollectionQueryVO.dto.arrayColumnNames':arrayColumnNames
				}
			}
			else if('SB' == queryTab){
					var form = Ext.getCmp('Foss_CashCollectionBill_SourceBillNos_ID').getForm();

					var sourceBillNosArray = [];
					var sourceBillNos = form.findField('sourceBillNos').getValue();
					if (Ext.String.trim(sourceBillNos) != null
							&& Ext.String.trim(sourceBillNos) != '') {
						var reg = /[,;]/;
						array = sourceBillNos.split(reg);
						for ( var i = 0; i < array.length; i++) {
							if (Ext.String.trim(array[i]) != '') {
								sourceBillNosArray.push(array[i]);
							}
						}
					} else {
						Ext.Msg.alert(consumer.queryCashCollectionBill.i18n('foss.stl.consumer.common.warmTips'), consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.mustInputNoForQuery'));
						return false;
					}
					searchParams = {
							"billCashCollectionQueryVO.dto.queryTab" : 'SB',
							"billCashCollectionQueryVO.sourceBillNosArray" : sourceBillNosArray,
							'billCashCollectionQueryVO.dto.arrayColumns':arrayColumns,
							'billCashCollectionQueryVO.dto.arrayColumnNames':arrayColumnNames
					}
			}else {
				var form = Ext.getCmp('Foss_CashCollectionBill_BatchNos_ID').getForm();
				var batchBillNosArray = [];
				var batchBillNos = form.findField('batchNos').getValue();
				if (Ext.String.trim(batchBillNos) != null
						&& Ext.String.trim(batchBillNos) != '') {
					var reg = /[,;]/;
					array = batchBillNos.split(reg);
					for ( var i = 0; i < array.length; i++) {
						if (Ext.String.trim(array[i]) != '') {
							batchBillNosArray.push(array[i]);
						}
					}
				} else {
					Ext.Msg.alert(consumer.queryCashCollectionBill.i18n('foss.stl.consumer.common.warmTips'), consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.mustInputNoForQuery'));
					return false;
				}
				searchParams = {
						"billCashCollectionQueryVO.dto.queryTab" : 'BB',
						"billCashCollectionQueryVO.batchNos" : batchBillNosArray,
						'billCashCollectionQueryVO.dto.arrayColumns':arrayColumns,
						'billCashCollectionQueryVO.dto.arrayColumnNames':arrayColumnNames
				}
			}
			//拼接vo，注入到后台
			Ext.Ajax.request({
			    url: consumer.realPath('exportBillCashCollection.action'),
			    form: Ext.fly('downloadAttachFileForm'),
			    method : 'post',
			    params :searchParams,
			    isUpload: true,
			    success : function(response){
			    	var result = Ext.decode(response.responseText);
			    	//如果异常信息有值，则弹出提示
			    	if(!Ext.isEmpty(result.errorMessage)){
			    		Ext.Msg.alert(consumer.queryCashCollectionBill.i18n('foss.stl.consumer.common.warmTips'),result.errorMessage);
			    		return false;
			    	}
					Ext.ux.Toast.msg(consumer.queryCashCollectionBill.i18n('foss.stl.consumer.common.warmTips'),consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.exportSuccess'), 'success', 1000);
			    },
			    failure : function(response){
					Ext.ux.Toast.msg(consumer.queryCashCollectionBill.i18n('foss.stl.consumer.common.warmTips'),consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.exportFailure'), 'error', 1000);
			    }
			});
        }
    });
}


//第一个Tab:按运单单号查询
Ext.define('Foss.Consumer.CashCollectionBillQueryInfoTab', {
	extend:'Ext.tab.Panel',
	bodyCls: 'autoHeight',
	cls: 'innerTabPanel',
	height : 260,
	listeners:{
		'tabchange':function(tabPanel,newCard,oldCard, eOpts){
			if(newCard.name=='queryCashCollectionBillByDate'){
				Ext.getCmp('T_consumer-queryCashCollectionBill_content').items.items[1].getLayout().setActiveItem(0);
			}else if(newCard.name=='queryCashCollectionBillByWayBillNos'){
				Ext.getCmp('T_consumer-queryCashCollectionBill_content').items.items[1].getLayout().setActiveItem(1);
			}else if(newCard.name=='queryCashCollectionBillBySourceBillNos'){
				Ext.getCmp('T_consumer-queryCashCollectionBill_content').items.items[1].getLayout().setActiveItem(2);
			}else {
				Ext.getCmp('T_consumer-queryCashCollectionBill_content').items.items[1].getLayout().setActiveItem(3);
			}
		}
	},
	items : [ {
		title: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.common.queryByDate'),
		tabConfig: {
			width: 120
		},
       	name:'queryCashCollectionBillByDate',
        items:[{
        	xtype:'form',
        	id:'Foss_CashCollectionBill_QueryByDate_ID',
        	defaults:{
	        	margin:'10 5 0 5',
	        	labelWidth:85,
	        	colspan : 1 
       		 },
        		layout:{
        			type :'table',
        			columns :3
        		},
		    items:[{
		    	xtype      : 'fieldcontainer', 
				defaultType: 'radiofield',
				fieldLabel:consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.dateRange'),
				layout:'column',
				colspan:3,
				items: [{
						boxLabel  : consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.businessDateBlank'),
						checked   : true,
						name      : 'selectDateType', 
						inputValue: '1',  //表单的参数值
						id:'queryCashCollectionBill_BusinessData_Id'
					},{ 
						boxLabel  : consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.acctDateBlank'), 
						name      : 'selectDateType', 
						inputValue: '2', //表单的参数值
						id:'queryCashCollectionBill_AccountData_Id'
				   }]
		    },{
		    	xtype:'datefield',
		    	name:'startDate',
		    	fieldLabel:consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.startDate'),
		    	value: new Date(),
		    	editable:false,
		    	format:'Y-m-d',
		    	allowBlank:false
		    },{
		    	xtype:'datefield',
		   	 	name:'endDate',
		    	fieldLabel:consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.endDate'),
		    	value: new Date(),
		    	editable:false,
		    	format:'Y-m-d',
		    	allowBlank:false
		    },{
		    	xtype: 'combobox',
				name:'active',
		        fieldLabel: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.active'),
				store:Ext.create('Foss.CashCollectionBill.IsActiveStore'),
				queryMode:'local',
		    	editable:false,
				displayField:'name',
				valueField:'value',
				value:'Y'
				/*
				 *	ISSUE-3086  应收单、应付单、还款单、现金收款单界面查询条件中 是否有效 默认为有效状态。
				 *	杨书硕
				 *	2013-06-27
				 */
		    },{
		    	xtype: 'dynamicorgcombselector',
				name:'collectionOrgName',
		        fieldLabel: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.collectionOrgName'),
				allowBlank: true,
				listWidth:300,//设置下拉框宽度
				isPaging:true //分页
		    },{
		    	xtype: 'dynamicorgcombselector',
		        name: 'generatingOrgName',
		        fieldLabel: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.generatingOrgName'),
		        allowBlank: true,
				listWidth:300,//设置下拉框宽度
				isPaging:true //分页
		    },{
		    	xtype: 'commoncustomerselector',
		    	all:'true',
		        name: 'customerName',
		        singlePeopleFlag : 'Y',
		        fieldLabel: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.customerName'),
		        allowBlank: true,
				listWidth:300,//设置下拉框宽度
				isPaging:true //分页
		    },{
		    	xtype: 'combobox',
				name:'status',
		        fieldLabel: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.status'),
		        store:FossDataDictionary.getDataDictionaryStore('BILL_DEPOSIT_RECEIVED__STATUS',null,{
					 'valueCode': '',
           		 'valueName': consumer.queryCashCollectionBill.i18n('foss.stl.consumer.common.all')
				}),
				queryMode:'local',
		    	editable:false,
				displayField:'valueName',
				valueField:'valueCode',
				value:''
		    },{
		    	xtype: 'combobox',
				name:'paymentType',
		        fieldLabel: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.paymentType'),
		        store:Ext.create('Foss.CashCollectionBill.PaymentTypeStore'),
				queryMode:'local',
		    	editable:false,
				displayField:'name',
				valueField:'value',
				value:''
		    },{
		    	xtype:'combobox',
		    	name:'isInit',
		    	fieldLabel:consumer.queryCashCollectionBill.i18n('foss.stl.consumer.common.isInit'),
		    	editable:false,
		    	value:'',
				store:FossDataDictionary. getDataDictionaryStore('FOSS_BOOLEAN',null,{
					 'valueCode': '',
               		 'valueName': consumer.queryCashCollectionBill.i18n('foss.stl.consumer.common.all')
				}),
				queryModel:'local',
				displayField:'valueName',
				valueField:'valueCode'
		    },{
		    	xtype:'combobox',
		    	name:'productCode',
		    	fieldLabel:consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.productCode'),
				store:Ext.create('Foss.pkp.ProductStore'),
				multiSelect : true,
			    queryMode: 'local', 	
				displayField:'name',
				valueField:'code',
				colspan:1
		    },{
		    	xtype:'textfield',
		    	name:'posSerialNum',
		    	fieldLabel:consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.posSerialNum'),
				colspan:2
		    },{	
				border: 1,
				xtype:'container',
				colspan:3,
				defaultType:'button',
				layout:'column',
				items:[{
					text:consumer.queryCashCollectionBill.i18n('foss.stl.consumer.common.reset'),
					columnWidth:.1,
					handler:function(){
						this.up('form').getForm().reset();
					}
				},{
					xtype: 'container',
					border : false,
					columnWidth:.8,
					html: '&nbsp;'
				},{
					text:consumer.queryCashCollectionBill.i18n('foss.stl.consumer.common.query'),
					columnWidth:.1,
					cls:'yellow_button',
					handler:function(){		
						var form = this.up('form').getForm();
						var me = this;
						if (form.isValid()) {
							//设置该按钮灰掉
							me.disable(false);
							//30秒后自动解除灰掉效果
							setTimeout(function() {
								me.enable(true);
							}, 30000);
							if(consumer.dateValidation(form)){
							var dateGridStore = Ext.getCmp('Foss_CashCollectionBill_DateQueryInfoGrid_Id').store;
							
							dateGridStore.removeAll();
							
							dateGridStore.loadPage(1,{
								callback:function(records, operation, success) {
								var result = Ext.decode(operation.response.responseText);
									//动态给GRID设置值
									Ext.getCmp('Foss_CashCollectionBill_DateQueryInfoGrid_TotalRecords_id').setValue(result.billCashCollectionQueryVO.totalRecordsInDB);
									Ext.getCmp('Foss_CashCollectionBill_DateQueryInfoGrid_TotalAmount_id').setValue(result.billCashCollectionQueryVO.totalAmount);
									me.enable(true);
								}
								});
							
							}
							}else{
								Ext.Msg.alert(consumer.queryCashCollectionBill.i18n('foss.stl.consumer.common.warmTips'),consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.pleaseCheckInputIsLegal'));
								me.enable(true);
							}
					}
				}]
		    }]
        }]
	},{
		title: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.common.queryByWaybillNo'),
		tabConfig: {
			width: 120
		},
       	name:'queryCashCollectionBillByWayBillNos',
        layout:'fit',
        items:[{
        	xtype:'form',
        	id:'Foss_CashCollectionBill_WayBillNo_ID',
        	defaults:{
	        	margin:'10 5 0 5',
	        	labelWidth:85
       		 },
       		 layout:{
        			type :'table',
           			columns :3
           		},
           		items:[{
 		    	xtype: 'textarea',
 	 			autoScroll:true,
 	 			emptyText:consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.pleaseOneOrTenNumbers.SeparatedByCommas'),
// 	 			regex:/^([0-9]{7,10}[;,])*([0-9]{7,10}[;,]?)$/i,
 	 			regexText:consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.numberMustBeSevenOrTenNumbers'),
 	            fieldLabel: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.waybillNo'),
 	            name: 'waybillNos',
 	            height : 140,
				width : 600,
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
					text:consumer.queryCashCollectionBill.i18n('foss.stl.consumer.common.reset'),
					columnWidth:.1,
					handler:function(){
						this.up('form').getForm().reset();
					}
				},{
					xtype: 'container',
					border : false,
					columnWidth:.8,
					html: '&nbsp;'
				},{
					text:consumer.queryCashCollectionBill.i18n('foss.stl.consumer.common.query'),
					cls:'yellow_button',
					columnWidth:.1,
					handler:function(){
						var form = this.up('form').getForm();
						var me = this;
						if (form.isValid()) {
							//设置该按钮灰掉
							me.disable(false);
							//30秒后自动解除灰掉效果
							setTimeout(function() {
								me.enable(true);
							}, 30000);
						var wayBillNosArray =[];
						var waybillNos = this.up('form').getForm().findField('waybillNos').getValue();
						if(Ext.String.trim(waybillNos)!=null && Ext.String.trim(waybillNos)!=''){
							 var reg = /[,;]/;  
							 array = waybillNos.split(reg);
							 for(var i=0;i<array.length;i++){
							 	if(Ext.String.trim(array[i])!=''){
							 		wayBillNosArray.push(array[i]);	
							 	}
							 }
						}else{
							Ext.Msg.alert(consumer.queryCashCollectionBill.i18n('foss.stl.consumer.common.warmTips'),consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.mustInputNoForQuery'));
							return false;
						}
						
						if(wayBillNosArray.length>10){
							Ext.Msg.alert(consumer.queryCashCollectionBill.i18n('foss.stl.consumer.common.warmTips'),consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.cannotExceed10ReceiveBillNos.pleaseCheckAgain'));
							return false;
						}
						var billCashCollectionQueryVO = new Object();
						billCashCollectionQueryVO.wayBillNosArray = wayBillNosArray;
						
						var params = new Object();
						
						Ext.Ajax.request({
							url:consumer.realPath('queryCashCollectionBillByWayBillNo.action'),
							params:{
								"billCashCollectionQueryVO.wayBillNosArray" : wayBillNosArray
							},
							method:'post',
							success:function(response){
								var result = Ext.decode(response.responseText);
								var billNosStore = Ext.getCmp('Foss_CashCollectionBill_BillNoQueryInfoGrid_Id').store;
								
								//每次查询先把上一次的数据清除
								
								billNosStore.removeAll();
								
								//加载后台查询到的数据到grid的store中
								if(!Ext.isEmpty(result.billCashCollectionQueryVO.billCashCollectionList)){
									//加载后台查询到的数据到grid的store中
									billNosStore.loadData(result.billCashCollectionQueryVO.billCashCollectionList);
								}
								
								//动态给GRID设置值
								Ext.getCmp('Foss_CashCollectionBill_BillNoQueryInfoGrid_TotalRecords_Id').setValue(result.billCashCollectionQueryVO.totalRecordsInDB);
								Ext.getCmp('Foss_CashCollectionBill_BillNoQueryInfoGrid_TotalAmount_Id').setValue(result.billCashCollectionQueryVO.totalAmount);
	
								//获取数据判断是否为空
								var billNosStoreIsNull = result.billCashCollectionQueryVO.billCashCollectionList;
								if(billNosStoreIsNull != null ) {
									Ext.getCmp('Foss_CashCollectionBill_BillNoQueryInfoGrid_Id').show();
								}
								me.enable(true);
							},
							failure:function(response){
								var result = Ext.decode(response.responseText);
								me.enable(true);
							}
						});						
					}else{
						Ext.Msg.alert(consumer.queryCashCollectionBill.i18n('foss.stl.consumer.common.warmTips'),consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.pleaseCheckInputIsLegal'));
					}
						}
				}]
 		    }]
        }]
	},{
		title: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.common.queryBySourceNo'),
		tabConfig: {
			width: 120
		},
       	name:'queryCashCollectionBillBySourceBillNos',
        layout:'fit',
        items:[{
        	xtype:'form',
        	id:'Foss_CashCollectionBill_SourceBillNos_ID',
        	defaults:{
	        	margin:'10 5 0 5',
	        	labelWidth:85
       		 },
       		 layout:{
        			type :'table',
           			columns :3
           		},
           		items:[{
 		    	xtype: 'textarea',
 	 			autoScroll:true,
 	 			emptyText:consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.pleaseOneOrTenNumbers.SeparatedByCommas'),
 	            fieldLabel: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.sourceBillNo'),
 	            name: 'sourceBillNos',
 	            height : 140,
				width : 600,
				allowBlank:false,
				colspan:2
 		    },{
 		    	xtype:'container',
				items:[{
					xtype:'component',
					padding:'0 0 0 10',
					autoEl:{
						tag:'div',
						html:'<span style="color:red;">'+consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.sourceBillNosNots')+'</span>'
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
					text:consumer.queryCashCollectionBill.i18n('foss.stl.consumer.common.reset'),
					columnWidth:.1,
					handler:function(){
						this.up('form').getForm().reset();
					}
				},{
					xtype: 'container',
					border : false,
					columnWidth:.8,
					html: '&nbsp;'
				},{
					text:consumer.queryCashCollectionBill.i18n('foss.stl.consumer.common.query'),
					cls:'yellow_button',
					columnWidth:.1,
					handler:function(){
						var form = this.up('form').getForm();
						var me = this;
						if (form.isValid()) {
							//设置该按钮灰掉
							me.disable(false);
							//30秒后自动解除灰掉效果
							setTimeout(function() {
								me.enable(true);
							}, 30000);
						var sourceBillNosArray =[];
						var sourceBillNos = this.up('form').getForm().findField('sourceBillNos').getValue();
						if(Ext.String.trim(sourceBillNos)!=null && Ext.String.trim(sourceBillNos)!=''){
							 var reg = /[,;]/;  
							 array = sourceBillNos.split(reg);
							 for(var i=0;i<array.length;i++){
							 	if(Ext.String.trim(array[i])!=''){
							 		sourceBillNosArray.push(array[i]);	
							 	}
							 }
						}else{
							Ext.Msg.alert(consumer.queryCashCollectionBill.i18n('foss.stl.consumer.common.warmTips'),consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.mustInputNoForQuery'));
							return false;
						}
						
						if(sourceBillNosArray.length>10){
							Ext.Msg.alert(consumer.queryCashCollectionBill.i18n('foss.stl.consumer.common.warmTips'),consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.annotExceed10SourceBillNos.pleaseCheckAgain'));
							return false;
						}
						var billCashCollectionQueryVO = new Object();
						billCashCollectionQueryVO.sourceBillNosArray = sourceBillNosArray;
						
						var params = new Object();
						
						Ext.Ajax.request({
							url:consumer.realPath('queryCashCollectionBillBySourceBillNo.action'),
							params:{
								"billCashCollectionQueryVO.sourceBillNosArray" : sourceBillNosArray
							},
							method:'post',
							success:function(response){
								var result = Ext.decode(response.responseText);
								var billNosStore = Ext.getCmp('Foss_CashCollectionBill_SourceNoQueryInfoGrid_Id').store;
								
								//每次查询先把上一次的数据清除
								billNosStore.removeAll();
								
								//加载后台查询到的数据到grid的store中
								if(!Ext.isEmpty(result.billCashCollectionQueryVO.billCashCollectionList)){
									//加载后台查询到的数据到grid的store中
									billNosStore.loadData(result.billCashCollectionQueryVO.billCashCollectionList);
								}
								
								//动态给GRID设置值
								Ext.getCmp('Foss_CashCollectionBill_SourceNoQueryInfoGrid_TotalRecords_Id').setValue(result.billCashCollectionQueryVO.totalRecordsInDB);
								Ext.getCmp('Foss_CashCollectionBill_SourceNoQueryInfoGrid_TotalAmount_Id').setValue(result.billCashCollectionQueryVO.totalAmount);
	
								//获取数据判断是否为空
								var billNosStoreIsNull = result.billCashCollectionQueryVO.billCashCollectionList;
								if(billNosStoreIsNull != null ) {
									Ext.getCmp('Foss_CashCollectionBill_SourceNoQueryInfoGrid_Id').show();
								}
								me.enable(true);
							},
							failure:function(response){
								var result = Ext.decode(response.responseText);
								me.enable(true);
							}
						});						
					}else{
						Ext.Msg.alert(consumer.queryCashCollectionBill.i18n('foss.stl.consumer.common.warmTips'),consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.pleaseCheckInputIsLegal'));
					}
						}
				}]
 		    },{
 		    	xtype:'container',
 		    	border:false,
 		    	html:'&nbsp;',
 		    	colspan : 1
 		    }]
        }]
	},{
		title: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.common.batchNo'),
		tabConfig: {
			width: 140
		},
       	name:'queryCashCollectionBillByBatchNo',
        layout:'fit',
        items:[{
        	xtype:'form',
        	id:'Foss_CashCollectionBill_BatchNos_ID',
        	defaults:{
	        	margin:'10 5 0 5',
	        	labelWidth:85
       		 },
       		 layout:{
        			type :'table',
           			columns :3
           		},
           		items:[{
 		    	xtype: 'textarea',
 	 			autoScroll:true,
 	 			emptyText:consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.pleaseOneOrTenNumbers.SeparatedByCommas'),
 	            fieldLabel: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.batchNo'),
 	            name: 'batchNos',
 	            height : 140,
				width : 600,
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
					text:consumer.queryCashCollectionBill.i18n('foss.stl.consumer.common.reset'),
					columnWidth:.1,
					handler:function(){
						this.up('form').getForm().reset();
					}
				},{
					xtype: 'container',
					border : false,
					columnWidth:.8,
					html: '&nbsp;'
				},{
					text:consumer.queryCashCollectionBill.i18n('foss.stl.consumer.common.query'),
					cls:'yellow_button',
					columnWidth:.1,
					handler:consumer.queryCashCollectionBill.queryByBatchNos
				}]
 		    }]
        }]
	}]
});					

//运单号查询model
Ext.define('Foss.CashCollectionBill.BillNoQueryEntryModel',{
	extend:'Ext.data.Model',
	fields:[	{
		name:'sourceBillNo'
	},{
		name:'customerName'
	},{
		name:'generatingOrgName'
	},{
		name:'collectionOrgName'
	},{
		name:'status'
	},{
		name:'amount',
		type:'long'
	},{
		name:'sourceBillType'
	},{
		name:'paymentType'
	},{
		name:'transportFee',
		type:'long'
	},{
		name:'cashCollectionNo'
	},{
		name:'waybillId'
	},{
		name:'waybillNo'
	},{
		name:'customerCode'
	},{
		name:'createOrgCode'
	},{
		name:'createOrgName'
	},{
		name:'collectionOrgCode'
	},{
		name:'collectionCompanyCode'
	},{
		name:'collectionCompanyName'
	},{
		name:'generatingOrgCode'
	},{
		name:'generatingCompanyCode'
	},{
		name:'generatingCompanyName'
	},{
		name:'currencyCode'
	},{
		name:'productCode'
	},{
		name:'productId'
	},{
		name:'pickupFee',
		type:'long'
	},{
		name:'deliveryGoodsFee',
		type:'long'
	},{
		name:'packagingFee',
		type:'long'
	},{
		name:'codFee',
		type:'long'
	},{
		name:'insuranceFee',
		type:'long'
	},{
		name:'otherFee',
		type:'long'
	},{
		name:'valueAddFee',
		type:'long'
	},{
		name:'promotionsFee',
		type:'long'
	},{
		name:'smallFee',
		type:'long'
	},{
		name:'conrevenDate',
		type:'date',
		convert:stl.longToDateConvert 
	},{
		name:'billType'
	},{
		name:'active'
	},{
		name:'isRedBack'
	},{
		name:'createUserCode'
	},{
		name:'createUserName'
	},{
		name:'auditUserCode'
	},{
		name:'auditUserName'
	},{
		name:'modifyUserCode'
	},{
		name:'modifyUserName'
	},{
		name:'disableUserCode'
	},{
		name:'disableUserName'
	},{
		name:'cashConfirmUserCode'
	},{
		name:'cashConfirmUserName'
	},{
		name:'businessDate',
		type:'date',
		convert:stl.longToDateConvert 
	},{
		name:'accountDate',
		type:'date',
		convert:stl.longToDateConvert 
	},{
		name:'createTime',
		type:'date',
		convert:stl.longToDateConvert 
	},{
		name:'modifyTime',
		type:'date',
		convert:stl.longToDateConvert 
	},{
		name:'disableTime',
		type:'date',
		convert:stl.longToDateConvert 
	},{
		name:'cashConfirmTime',
		type:'date',
		convert:stl.longToDateConvert 
	},{
		name:'isInit'
	},{
		name:'notes'
	},{
		name:'collectionType'
	},{
		name:'posSerialNum'
	},{
		name:'batchNo'
	}, {
		name : 'expressOrigOrgCode'
	}, {
		name : 'expressOrigOrgName'
	}, {
		name : 'expressDestOrgCode'
	}, {
		name : 'expressDestOrgName'
	},{
		name:'invoiceMark'
	}]
});

//运单号查询Store				
Ext.define('Foss.CashCollectionBill.BillNoQueryEntryStore',{
	extend:'Ext.data.Store',
	model:'Foss.CashCollectionBill.BillNoQueryEntryModel'
});

//单号查询列表可编辑
var SoperateColumnEditing = Ext.create('Ext.grid.plugin.CellEditing', {  
  	clicksToEdit : 1
}) ;

//单号查询列表
Ext.define('Foss.CashCollectionBill.BillNoQueryInfoGrid',{
	extend:'Ext.grid.Panel',
    title: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.cashCollectionBill'),
    emptyText: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.common.emptyText'),
	frame:true,
	height:420,
	sortableColumns: false,
	viewConfig: {         
		enableTextSelection: true       
	}, 
	//plugins:SoperateColumnEditing,
//	selModel:Ext.create('Ext.selection.CheckboxModel'),
	listeners: {
        'itemdblclick':function(grid,record,item,index){
        	consumer.checkCashCollectionBillDetailsInforByDoubleClickInfor(record,consumer.WAYBILLNO);
        }
    },
	detailWindow:null,
	getDetailWindow:function(){
		if(Ext.isEmpty(this.detailWindow)){
			this.detailWindow = Ext.create('Foss.QueryCashCollectionBill.ShowDetailsInforWindow');
		}
		return this.detailWindow;
	},
	columns:[{ 
    xtype:'actioncolumn',
		width:73,
		text: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.common.actionColumn'),
		align: 'center',
		items:[{
			iconCls : 'deppon_icons_showdetail',
			tooltip:consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.showDetail'),
			handler:function(grid, rowIndex, colIndex){
				consumer.checkCashCollectionBillDetailsInfor(grid, rowIndex, colIndex,consumer.WAYBILLNO);
			}	
		}]
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.waybillNo'), 
		dataIndex: 'waybillNo'
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.customerName'), 
		dataIndex: 'customerName'
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.productCode'), 
		dataIndex: 'productCode',
		renderer:function(value){
			return Foss.pkp.ProductData.rendererSubmitToDisplay(value);
		}
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.generatingOrgName'), 
		dataIndex: 'generatingOrgName'		
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.collectionOrgName'), 
		dataIndex: 'collectionOrgName'
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.cashCollectionNo'), 
		dataIndex: 'cashCollectionNo'
	},{
		header:consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.sourceBillNo'),
		dataIndex:'sourceBillNo'
	},{
		header:consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.posSerialNum'),
		dataIndex:'posSerialNum'
	},{
		header:consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.batchNo'),
		dataIndex:'batchNo'
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.collectionType'), 
		dataIndex: 'collectionType',
		renderer:function(value){
			var displayField = FossDataDictionary. rendererSubmitToDisplay (value,'BILL_RECEIVABLE__COLLECTION_TYPE');
    		return displayField;
		}
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.status'), 
		dataIndex: 'status',
		renderer:function(value){
			var displayField = FossDataDictionary. rendererSubmitToDisplay (value,'BILL_CASH_COLLECTION__STATUS');
    		return displayField;
		}
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.amount'), 
		dataIndex: 'amount',
		xtype: 'numbercolumn', 
		format:'0,0.00',
		align:'right'
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.billType'), 
		dataIndex: 'billType',
		renderer: function(value) {
			if(value == null){
				return null;
			}else{
				return consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.cashCollectionBill');
			}
			}
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.paymentType'),
		dataIndex: 'paymentType',
		renderer:function(value){
			var displayField = FossDataDictionary. rendererSubmitToDisplay (value,'SETTLEMENT__PAYMENT_TYPE');
    		return displayField;
		}
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.transportFee'), 
		dataIndex: 'transportFee',
		xtype: 'numbercolumn', 
		format:'0,0.00',
		align:'right'
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.invoiceMark'), 
		dataIndex:'invoiceMark',
		hidden:true,
		renderer:function(value){
			var displayField = FossDataDictionary. rendererSubmitToDisplay (value,'SETTLEMENT_INVOICE_MARK');
    		return displayField;
		}
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.sourceBillType'), 
		dataIndex: 'sourceBillType',
		hidden:true,
		renderer:function(value){
			var displayField = FossDataDictionary. rendererSubmitToDisplay (value,'BILL_CASH_COLLECTION__SOURCE_BILL_TYPE');
    		return displayField;
		}
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.customerCode'), 
		dataIndex: 'customerCode',
		hidden:true
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.collectionOrgCode'), 
		dataIndex: 'collectionOrgCode',
		hidden:true
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.collectionCompanyCode'), 
		dataIndex: 'collectionCompanyCode',
		hidden:true
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.collectionCompanyName'), 
		dataIndex: 'collectionCompanyName',
		hidden:true
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.generatingOrgCode'), 
		dataIndex: 'generatingOrgCode',
		hidden:true
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.generatingCompanyCode'), 
		dataIndex: 'generatingCompanyCode',
		hidden:true
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.generatingCompanyName'), 
		dataIndex: 'generatingCompanyName',
		hidden:true
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.pickupFee'), 
		dataIndex: 'pickupFee',
		hidden:true,
		xtype: 'numbercolumn', 
		format:'0,0.00',
		align:'right'
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.deliveryGoodsFee'), 
		dataIndex: 'deliveryGoodsFee',
		hidden:true,
		xtype: 'numbercolumn', 
		format:'0,0.00',
		align:'right'
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.packagingFee'), 
		dataIndex: 'packagingFee',
		hidden:true,
		xtype: 'numbercolumn', 
		format:'0,0.00',
		align:'right'
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.codFee'), 
		dataIndex: 'codFee',
		hidden:true,
		xtype: 'numbercolumn', 
		format:'0,0.00',
		align:'right'
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.insuranceFee'), 
		dataIndex: 'insuranceFee',
		hidden:true,
		xtype: 'numbercolumn', 
		format:'0,0.00',
		align:'right'
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.otherFee'), 
		dataIndex: 'otherFee',
		hidden:true,
		xtype: 'numbercolumn', 
		format:'0,0.00',
		align:'right'
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.smallFee'), 
		dataIndex: 'smallFee',
		hidden:true,
		xtype: 'numbercolumn', 
		format:'0,0.00',
		align:'right'
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.active'), 
		dataIndex: 'active',
		hidden:true,
		renderer:function(value){
			var displayField = FossDataDictionary.rendererSubmitToDisplay(value,settlementDict.FOSS_ACTIVE);
    		return displayField;
		}
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.isRedBack'), 
		dataIndex: 'isRedBack',
		hidden:true,
		renderer:function(value){
			var displayField = FossDataDictionary. rendererSubmitToDisplay (value,'SETTLEMENT__IS_RED_BACK');
    		return displayField;
		}
	},{
		
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.common.isInit'), 
		dataIndex: 'isInit',
		hidden:true,
		renderer:function(value){
    		var displayField = FossDataDictionary. rendererSubmitToDisplay (value,settlementDict.FOSS_BOOLEAN);
    		return displayField;
    	}
	},
	{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.createUserCode'), 
		dataIndex: 'createUserCode',
		hidden:true
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.createUserName'), 
		dataIndex: 'createUserName',
		hidden:true
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.cashConfirmUserCode'), 
		dataIndex: 'cashConfirmUserCode',
		hidden:true
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.cashConfirmUserName'), 
		dataIndex: 'cashConfirmUserName',
		hidden:true
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.businessDate'), 
		dataIndex: 'businessDate',
		hidden:true,
    	renderer:function(value){
    		if(value!=null){
    			return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
    		}else{
    			return value;
    		}
    	}
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.accountDate'), 
		dataIndex: 'accountDate',
		hidden:true,
    	renderer:function(value){
    		if(value!=null){
    			return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
    		}else{
    			return value;
    		}
    	}
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.conrevenDate'), 
		dataIndex: 'conrevenDate',
		hidden:true,
    	renderer:function(value){
    		if(value!=null){
    			return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
    		}else{
    			return value;
    		}
    	}
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.cashConfirmTime'), 
		dataIndex: 'cashConfirmTime',
		hidden:true, 
    	renderer:function(value){
    		if(value!=null){
    			return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
    		}else{
    			return value;
    		}
    	}
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.notes'), 
		dataIndex: 'notes',
		hidden:true
	}, {
		header : consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.expressOrigOrgCode'),
		dataIndex : 'expressOrigOrgCode',
		hidden : true
	}, {
		header : consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.expressOrigOrgName'),
		dataIndex : 'expressOrigOrgName',
		hidden : true
	}, {
		header : consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.expressDestOrgCode'),
		dataIndex : 'expressDestOrgCode',
		hidden : true
	}, {
		header : consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.expressDestOrgName'),
		dataIndex : 'expressDestOrgName',
		hidden : true
	}]
});

//单号查询列表
Ext.define('Foss.CashCollectionBill.SourceNoQueryInfoGrid',{
	extend:'Ext.grid.Panel',
    title: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.cashCollectionBill'),
    emptyText: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.common.emptyText'),
	frame:true,
	height:420,
	sortableColumns: false,
	viewConfig: {         
		enableTextSelection: true       
	}, 
	listeners: {
        'itemdblclick':function(grid,record,item,index){
        	consumer.checkCashCollectionBillDetailsInforByDoubleClickInfor(record,consumer.WAYBILLNO);
        }
    },
	detailWindow:null,
	getDetailWindow:function(){
		if(Ext.isEmpty(this.detailWindow)){
			this.detailWindow = Ext.create('Foss.QueryCashCollectionBill.ShowDetailsInforWindow');
		}
		return this.detailWindow;
	},
	columns:[{ 
    xtype:'actioncolumn',
		width:73,
		text: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.common.actionColumn'),
		align: 'center',
		items:[{
			iconCls : 'deppon_icons_showdetail',
			tooltip:consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.showDetail'),
			handler:function(grid, rowIndex, colIndex){
				consumer.checkCashCollectionBillDetailsInfor(grid, rowIndex, colIndex,consumer.WAYBILLNO);
			}	
		}]
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.waybillNo'), 
		dataIndex: 'waybillNo'
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.customerName'), 
		dataIndex: 'customerName'
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.productCode'), 
		dataIndex: 'productCode',
		renderer:function(value){
			return Foss.pkp.ProductData.rendererSubmitToDisplay(value);
		}
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.generatingOrgName'), 
		dataIndex: 'generatingOrgName'		
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.collectionOrgName'), 
		dataIndex: 'collectionOrgName'
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.cashCollectionNo'), 
		dataIndex: 'cashCollectionNo'
	},{
		header:consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.sourceBillNo'),
		dataIndex:'sourceBillNo'
	},{
		header:consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.posSerialNum'),
		dataIndex:'posSerialNum'
	},{
		header:consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.batchNo'),
		dataIndex:'batchNo'
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.collectionType'), 
		dataIndex: 'collectionType',
		renderer:function(value){
			var displayField = FossDataDictionary. rendererSubmitToDisplay (value,'BILL_RECEIVABLE__COLLECTION_TYPE');
    		return displayField;
		}
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.status'), 
		dataIndex: 'status',
		renderer:function(value){
			var displayField = FossDataDictionary. rendererSubmitToDisplay (value,'BILL_CASH_COLLECTION__STATUS');
    		return displayField;
		}
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.amount'), 
		dataIndex: 'amount',
		xtype: 'numbercolumn', 
		format:'0,0.00',
		align:'right'
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.billType'), 
		dataIndex: 'billType',
		renderer: function(value) {
			if(value == null){
				return null;
			}else{
				return consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.cashCollectionBill');
			}
			}
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.paymentType'),
		dataIndex: 'paymentType',
		renderer:function(value){
			var displayField = FossDataDictionary. rendererSubmitToDisplay (value,'SETTLEMENT__PAYMENT_TYPE');
    		return displayField;
		}
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.transportFee'), 
		dataIndex: 'transportFee',
		xtype: 'numbercolumn', 
		format:'0,0.00',
		align:'right'
	},{//发票标记
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.invoiceMark'), 
		dataIndex: 'invoiceMark',
		hidden:true,
		renderer:function(value){
			var displayField = FossDataDictionary. rendererSubmitToDisplay (value,'SETTLEMENT_INVOICE_MARK');
    		return displayField;
		}
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.sourceBillType'), 
		dataIndex: 'sourceBillType',
		hidden:true,
		renderer:function(value){
			var displayField = FossDataDictionary. rendererSubmitToDisplay (value,'BILL_CASH_COLLECTION__SOURCE_BILL_TYPE');
    		return displayField;
		}
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.customerCode'), 
		dataIndex: 'customerCode',
		hidden:true
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.collectionOrgCode'), 
		dataIndex: 'collectionOrgCode',
		hidden:true
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.collectionCompanyCode'), 
		dataIndex: 'collectionCompanyCode',
		hidden:true
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.collectionCompanyName'), 
		dataIndex: 'collectionCompanyName',
		hidden:true
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.generatingOrgCode'), 
		dataIndex: 'generatingOrgCode',
		hidden:true
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.generatingCompanyCode'), 
		dataIndex: 'generatingCompanyCode',
		hidden:true
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.generatingCompanyName'), 
		dataIndex: 'generatingCompanyName',
		hidden:true
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.pickupFee'), 
		dataIndex: 'pickupFee',
		hidden:true,
		xtype: 'numbercolumn', 
		format:'0,0.00',
		align:'right'
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.deliveryGoodsFee'), 
		dataIndex: 'deliveryGoodsFee',
		hidden:true,
		xtype: 'numbercolumn', 
		format:'0,0.00',
		align:'right'
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.packagingFee'), 
		dataIndex: 'packagingFee',
		hidden:true,
		xtype: 'numbercolumn', 
		format:'0,0.00',
		align:'right'
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.codFee'), 
		dataIndex: 'codFee',
		hidden:true,
		xtype: 'numbercolumn', 
		format:'0,0.00',
		align:'right'
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.insuranceFee'), 
		dataIndex: 'insuranceFee',
		hidden:true,
		xtype: 'numbercolumn', 
		format:'0,0.00',
		align:'right'
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.otherFee'), 
		dataIndex: 'otherFee',
		hidden:true,
		xtype: 'numbercolumn', 
		format:'0,0.00',
		align:'right'
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.smallFee'), 
		dataIndex: 'smallFee',
		hidden:true,
		xtype: 'numbercolumn', 
		format:'0,0.00',
		align:'right'
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.active'), 
		dataIndex: 'active',
		hidden:true,
		renderer:function(value){
			var displayField = FossDataDictionary.rendererSubmitToDisplay(value,settlementDict.FOSS_ACTIVE);
    		return displayField;
		}
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.isRedBack'), 
		dataIndex: 'isRedBack',
		hidden:true,
		renderer:function(value){
			var displayField = FossDataDictionary. rendererSubmitToDisplay (value,'SETTLEMENT__IS_RED_BACK');
    		return displayField;
		}
	},
	{
		
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.common.isInit'), 
		dataIndex: 'isInit',
		hidden:true,
		renderer:function(value){
    		var displayField = FossDataDictionary. rendererSubmitToDisplay (value,settlementDict.FOSS_BOOLEAN);
    		return displayField;
    	}
	},
	{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.createUserCode'), 
		dataIndex: 'createUserCode',
		hidden:true
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.createUserName'), 
		dataIndex: 'createUserName',
		hidden:true
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.cashConfirmUserCode'), 
		dataIndex: 'cashConfirmUserCode',
		hidden:true
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.cashConfirmUserName'), 
		dataIndex: 'cashConfirmUserName',
		hidden:true
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.businessDate'), 
		dataIndex: 'businessDate',
		hidden:true,
    	renderer:function(value){
    		if(value!=null){
    			return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
    		}else{
    			return value;
    		}
    	}
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.accountDate'), 
		dataIndex: 'accountDate',
		hidden:true,
    	renderer:function(value){
    		if(value!=null){
    			return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
    		}else{
    			return value;
    		}
    	}
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.conrevenDate'), 
		dataIndex: 'conrevenDate',
		hidden:true,
    	renderer:function(value){
    		if(value!=null){
    			return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
    		}else{
    			return value;
    		}
    	}
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.cashConfirmTime'), 
		dataIndex: 'cashConfirmTime',
		hidden:true, 
    	renderer:function(value){
    		if(value!=null){
    			return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
    		}else{
    			return value;
    		}
    	}
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.notes'), 
		dataIndex: 'notes',
		hidden:true
	}, {
		header : consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.expressOrigOrgCode'),
		dataIndex : 'expressOrigOrgCode',
		hidden : true
	}, {
		header : consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.expressOrigOrgName'),
		dataIndex : 'expressOrigOrgName',
		hidden : true
	}, {
		header : consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.expressDestOrgCode'),
		dataIndex : 'expressDestOrgCode',
		hidden : true
	}, {
		header : consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.expressDestOrgName'),
		dataIndex : 'expressDestOrgName',
		hidden : true
	}]
});


//单号查询列表
Ext.define('Foss.CashCollectionBill.BatchNoQueryInfoGrid',{
	extend:'Ext.grid.Panel',
    title: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.cashCollectionBill'),
    emptyText: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.common.emptyText'),
	frame:true,
	height:420,
	sortableColumns: false,
	viewConfig: {         
		enableTextSelection: true       
	}, 
	listeners: {
        'itemdblclick':function(grid,record,item,index){
        	consumer.checkCashCollectionBillDetailsInforByDoubleClickInfor(record,consumer.WAYBILLNO);
        }
    },
	detailWindow:null,
	getDetailWindow:function(){
		if(Ext.isEmpty(this.detailWindow)){
			this.detailWindow = Ext.create('Foss.QueryCashCollectionBill.ShowDetailsInforWindow');
		}
		return this.detailWindow;
	},
	columns:[{ 
    xtype:'actioncolumn',
		width:73,
		text: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.common.actionColumn'),
		align: 'center',
		items:[{
			iconCls : 'deppon_icons_showdetail',
			tooltip:consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.showDetail'),
			handler:function(grid, rowIndex, colIndex){
				consumer.checkCashCollectionBillDetailsInfor(grid, rowIndex, colIndex,consumer.WAYBILLNO);
			}	
		}]
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.waybillNo'), 
		dataIndex: 'waybillNo'
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.customerName'), 
		dataIndex: 'customerName'
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.productCode'), 
		dataIndex: 'productCode',
		renderer:function(value){
			return Foss.pkp.ProductData.rendererSubmitToDisplay(value);
		}
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.generatingOrgName'), 
		dataIndex: 'generatingOrgName'		
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.collectionOrgName'), 
		dataIndex: 'collectionOrgName'
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.cashCollectionNo'), 
		dataIndex: 'cashCollectionNo'
	},{
		header:consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.sourceBillNo'),
		dataIndex:'sourceBillNo'
	},{
		header:consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.posSerialNum'),
		dataIndex:'posSerialNum'
	},{
		header:consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.batchNo'),
		dataIndex:'batchNo'
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.collectionType'), 
		dataIndex: 'collectionType',
		renderer:function(value){
			var displayField = FossDataDictionary. rendererSubmitToDisplay (value,'BILL_RECEIVABLE__COLLECTION_TYPE');
    		return displayField;
		}
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.status'), 
		dataIndex: 'status',
		renderer:function(value){
			var displayField = FossDataDictionary. rendererSubmitToDisplay (value,'BILL_CASH_COLLECTION__STATUS');
    		return displayField;
		}
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.amount'), 
		dataIndex: 'amount',
		xtype: 'numbercolumn', 
		format:'0,0.00',
		align:'right'
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.billType'), 
		dataIndex: 'billType',
		renderer: function(value) {
			if(value == null){
				return null;
			}else{
				return consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.cashCollectionBill');
			}
			}
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.paymentType'),
		dataIndex: 'paymentType',
		renderer:function(value){
			var displayField = FossDataDictionary. rendererSubmitToDisplay (value,'SETTLEMENT__PAYMENT_TYPE');
    		return displayField;
		}
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.transportFee'), 
		dataIndex: 'transportFee',
		xtype: 'numbercolumn', 
		format:'0,0.00',
		align:'right'
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.invoiceMark'), 
		dataIndex: 'invoiceMark',
		hidden:true,
		renderer:function(value){
			var displayField = FossDataDictionary. rendererSubmitToDisplay (value,'SETTLEMENT_INVOICE_MARK');
    		return displayField;
		}
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.sourceBillType'), 
		dataIndex: 'sourceBillType',
		hidden:true,
		renderer:function(value){
			var displayField = FossDataDictionary. rendererSubmitToDisplay (value,'BILL_CASH_COLLECTION__SOURCE_BILL_TYPE');
    		return displayField;
		}
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.customerCode'), 
		dataIndex: 'customerCode',
		hidden:true
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.collectionOrgCode'), 
		dataIndex: 'collectionOrgCode',
		hidden:true
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.collectionCompanyCode'), 
		dataIndex: 'collectionCompanyCode',
		hidden:true
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.collectionCompanyName'), 
		dataIndex: 'collectionCompanyName',
		hidden:true
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.generatingOrgCode'), 
		dataIndex: 'generatingOrgCode',
		hidden:true
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.generatingCompanyCode'), 
		dataIndex: 'generatingCompanyCode',
		hidden:true
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.generatingCompanyName'), 
		dataIndex: 'generatingCompanyName',
		hidden:true
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.pickupFee'), 
		dataIndex: 'pickupFee',
		hidden:true,
		xtype: 'numbercolumn', 
		format:'0,0.00',
		align:'right'
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.deliveryGoodsFee'), 
		dataIndex: 'deliveryGoodsFee',
		hidden:true,
		xtype: 'numbercolumn', 
		format:'0,0.00',
		align:'right'
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.packagingFee'), 
		dataIndex: 'packagingFee',
		hidden:true,
		xtype: 'numbercolumn', 
		format:'0,0.00',
		align:'right'
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.codFee'), 
		dataIndex: 'codFee',
		hidden:true,
		xtype: 'numbercolumn', 
		format:'0,0.00',
		align:'right'
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.insuranceFee'), 
		dataIndex: 'insuranceFee',
		hidden:true,
		xtype: 'numbercolumn', 
		format:'0,0.00',
		align:'right'
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.otherFee'), 
		dataIndex: 'otherFee',
		hidden:true,
		xtype: 'numbercolumn', 
		format:'0,0.00',
		align:'right'
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.smallFee'), 
		dataIndex: 'smallFee',
		hidden:true,
		xtype: 'numbercolumn', 
		format:'0,0.00',
		align:'right'
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.active'), 
		dataIndex: 'active',
		hidden:true,
		renderer:function(value){
			var displayField = FossDataDictionary.rendererSubmitToDisplay(value,settlementDict.FOSS_ACTIVE);
    		return displayField;
		}
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.isRedBack'), 
		dataIndex: 'isRedBack',
		hidden:true,
		renderer:function(value){
			var displayField = FossDataDictionary. rendererSubmitToDisplay (value,'SETTLEMENT__IS_RED_BACK');
    		return displayField;
		}
	},
	{
		
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.common.isInit'), 
		dataIndex: 'isInit',
		hidden:true,
		renderer:function(value){
    		var displayField = FossDataDictionary. rendererSubmitToDisplay (value,settlementDict.FOSS_BOOLEAN);
    		return displayField;
    	}
	},
	{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.createUserCode'), 
		dataIndex: 'createUserCode',
		hidden:true
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.createUserName'), 
		dataIndex: 'createUserName',
		hidden:true
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.cashConfirmUserCode'), 
		dataIndex: 'cashConfirmUserCode',
		hidden:true
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.cashConfirmUserName'), 
		dataIndex: 'cashConfirmUserName',
		hidden:true
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.businessDate'), 
		dataIndex: 'businessDate',
		hidden:true,
    	renderer:function(value){
    		if(value!=null){
    			return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
    		}else{
    			return value;
    		}
    	}
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.accountDate'), 
		dataIndex: 'accountDate',
		hidden:true,
    	renderer:function(value){
    		if(value!=null){
    			return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
    		}else{
    			return value;
    		}
    	}
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.conrevenDate'), 
		dataIndex: 'conrevenDate',
		hidden:true,
    	renderer:function(value){
    		if(value!=null){
    			return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
    		}else{
    			return value;
    		}
    	}
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.cashConfirmTime'), 
		dataIndex: 'cashConfirmTime',
		hidden:true, 
    	renderer:function(value){
    		if(value!=null){
    			return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
    		}else{
    			return value;
    		}
    	}
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.notes'), 
		dataIndex: 'notes',
		hidden:true
	}, {
		header : consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.expressOrigOrgCode'),
		dataIndex : 'expressOrigOrgCode',
		hidden : true
	}, {
		header : consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.expressOrigOrgName'),
		dataIndex : 'expressOrigOrgName',
		hidden : true
	}, {
		header : consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.expressDestOrgCode'),
		dataIndex : 'expressDestOrgCode',
		hidden : true
	}, {
		header : consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.expressDestOrgName'),
		dataIndex : 'expressDestOrgName',
		hidden : true
	}]
});

//Dto查询列表Store
Ext.define('Foss.CashCollectionBill.DateQueryEntityStore',{
	extend:'Ext.data.Store',
	model:'Foss.CashCollectionBill.BillNoQueryEntryModel',
	pageSize:50,
	proxy:{
		type:'ajax',
		url:consumer.realPath('queryCashCollectionBillByDto.action'),
		reader:{
			type:'json',
			root:'billCashCollectionQueryVO.billCashCollectionList',
			totalProperty:'totalCount'
		}
	}
});

//日期查询列表可编辑
var SoperateColumnEditing1 = Ext.create('Ext.grid.plugin.CellEditing', {  
  	clicksToEdit : 1
}) ;

/**
 * 定义查看明细的Form
 */
Ext.define('Foss.CashCollectionBill.ShowDetailsInforForm',{
	extend:'Ext.form.Panel',
	layout:'column',
	width:stl.SCREENWIDTH*0.8,
	frame:false,
	defaultType:'textfield',
	layout:'column',
	defaults:{
		margin:'0 0 0 0',
		readOnly:true
	},
	items:[	{
		fieldLabel:consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.waybillNo'),
		name:'waybillNo',
		columnWidth:.5
	},{
		fieldLabel:consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.businessDate'),
		name:'businessDate',
		columnWidth:.5
	},{
		fieldLabel: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.cashCollectionNo'), 
		name:'cashCollectionNo',
		columnWidth:.5
	},{
		fieldLabel: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.customerName'), 
		name:'customerName',
		columnWidth:.5
	},{
		fieldLabel: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.customerCode'), 
		name:'customerCode',
		columnWidth:.5
	},{
		fieldLabel: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.generatingOrgName'), 
		name: 'generatingOrgName',	
		columnWidth:.5
	},{
		fieldLabel:consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.generatingCompanyName'),
		name:'generatingCompanyName',
		columnWidth:.5
	},{
		fieldLabel:consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.productCode'),
		name:'productCode',
		columnWidth:.5
	},{
		fieldLabel: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.collectionOrgName'), 
		name: 'collectionOrgName',
		columnWidth:.5
	},{
		fieldLabel:consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.collectionCompanyName'),
		name:'collectionCompanyName',
		columnWidth:.5
	},{
		fieldLabel:consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.paymentType'),
		name:'paymentType',
		columnWidth:.5
	},{
		fieldLabel:consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.amount'),
		name:'amount',
		columnWidth:.5
	},{
		fieldLabel:consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.transportFee'),
		name:'transportFee',
		columnWidth:.5
	},{
		fieldLabel:consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.pickupFee'),
		name:'pickupFee',
		columnWidth:.5
	},{
		fieldLabel:consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.deliveryGoodsFee'),
		name:'deliveryGoodsFee',
		columnWidth:.5
	},{
		fieldLabel:consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.packagingFee'),
		name:'packagingFee',
		columnWidth:.5
	},{
		fieldLabel:consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.codFee'),
		name:'codFee',
		columnWidth:.5
	},{
		fieldLabel:consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.insuranceFee'),
		name:'insuranceFee',
		columnWidth:.5
	},{
		fieldLabel:consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.otherFee'),
		name:'otherFee',
		columnWidth:.5
	},{
		fieldLabel:consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.smallFee'),
		name:'smallFee',
		columnWidth:.5
	},{
		fieldLabel:consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.status'),
		name:'status',
		columnWidth:.5
	},{
		fieldLabel:consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.billType'),
		name:'billType',
		columnWidth:.5
	},{
		fieldLabel:consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.accountDate'),
		name:'accountDate',
		columnWidth:.5
	},{
		fieldLabel:consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.createUserName'),
		name:'createUserName',
		columnWidth:.5
	},{
		fieldLabel:consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.createUserCode'),
		name:'createUserCode',
		columnWidth:.5
	},{
		fieldLabel:consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.cashConfirmTime'),
		name:'cashConfirmTime',
		columnWidth:.5
	},{
		fieldLabel:consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.cashConfirmUserName'),
		name:'cashConfirmUserName',
		columnWidth:.5
	},{
		fieldLabel:consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.cashConfirmUserCode'),
		name:'cashConfirmUserCode',
		columnWidth:.5
	},{
		fieldLabel:consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.notes'),
		name:'notes',
		columnWidth:.5
	},{
		fieldLabel:consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.isRedBack'),
		name:'isRedBack',
		columnWidth:.5
	},
	{
		fieldLabel:consumer.queryCashCollectionBill.i18n('foss.stl.consumer.common.isInit'),
		name:'isInit',
		columnWidth:.5
	},{
		fieldLabel:consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.active'),
		name:'active',
		columnWidth:.5
	},{
		fieldLabel:consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.sourceBillType'),
		name:'sourceBillType',
		columnWidth:.5
	},{
		fieldLabel:consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.sourceBillNo'),
		name:'sourceBillNo',
		columnWidth:.5
	},{
		fieldLabel:consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.expressOrigOrgCode'),
		name:'expressOrigOrgCode',
		columnWidth:.5
	},{
		fieldLabel:consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.expressOrigOrgName'),
		name:'expressOrigOrgName',
		columnWidth:.5
	},{
		fieldLabel:consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.expressDestOrgCode'),
		name:'expressDestOrgCode',
		columnWidth:.5
	},{
		fieldLabel:consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.expressDestOrgName'),
		name:'expressDestOrgName',
		columnWidth:.5
	},{
		fieldLabel:consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.invoiceMark'),
		name:'invoiceMark',
		columnWidth:.5
	}]
});

/**
 * 声明查看明细Window
 */
Ext.define('Foss.QueryCashCollectionBill.ShowDetailsInforWindow',{
	extend:'Ext.window.Window',
	width:stl.SCREENWIDTH*0.8,
	height:stl.SCREENHEIGHT*0.6,
	modal:true,
	constrainHeader: true,
	closeAction:'hide',
	items:[Ext.create('Foss.CashCollectionBill.ShowDetailsInforForm')]
});



//按日期查询现金收款单Grid列表定义
Ext.define('Foss.CashCollectionBill.DateQueryInfoGrid',{
	extend:'Ext.grid.Panel',
    title: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.cashCollectionBill'),
    emptyText: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.common.emptyText'),
	frame:true,
	height:420,
	sortableColumns: false,
	viewConfig: {         
		enableTextSelection: true       
	}, 
	//plugins:SoperateColumnEditing1,
//	selModel:Ext.create('Ext.selection.CheckboxModel'),
	listeners: {
        'itemdblclick':function(grid,record,item,index){
        	consumer.checkCashCollectionBillDetailsInforByDoubleClickInfor(record,consumer.DATE);
        }
    },
	detailWindow:null,
	getDetailWindow:function(){
		if(Ext.isEmpty(this.detailWindow)){
			this.detailWindow = Ext.create('Foss.QueryCashCollectionBill.ShowDetailsInforWindow');
		}
		return this.detailWindow;
	},
	columns:[{ 
    xtype:'actioncolumn',
		width:73,
		text: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.common.actionColumn'),
		align: 'center',
		items:[{
			iconCls : 'deppon_icons_showdetail',
			tooltip:consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.showDetail'),
			handler:function(grid, rowIndex, colIndex){
				consumer.checkCashCollectionBillDetailsInfor(grid, rowIndex, colIndex,consumer.DATE);
			}	
		}]
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.waybillNo'), 
		dataIndex: 'waybillNo'
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.customerName'), 
		dataIndex: 'customerName'
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.productCode'), 
		dataIndex: 'productCode',
		renderer:function(value){
			return Foss.pkp.ProductData.rendererSubmitToDisplay(value);
		}
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.generatingOrgName'), 
		dataIndex: 'generatingOrgName'		
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.collectionOrgName'), 
		dataIndex: 'collectionOrgName'
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.cashCollectionNo'), 
		dataIndex: 'cashCollectionNo'
	},{
		header:consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.sourceBillNo'),
		dataIndex:'sourceBillNo'
	},{
		header:consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.posSerialNum'),
		dataIndex:'posSerialNum'
	},{
		header:consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.batchNo'),
		dataIndex:'batchNo'
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.collectionType'), 
		dataIndex: 'collectionType',
		renderer:function(value){
			var displayField = FossDataDictionary. rendererSubmitToDisplay (value,'BILL_RECEIVABLE__COLLECTION_TYPE');
    		return displayField;
		}
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.status'), 
		dataIndex: 'status',
		renderer:function(value){
			var displayField = FossDataDictionary. rendererSubmitToDisplay (value,'BILL_CASH_COLLECTION__STATUS');
    		return displayField;
		}
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.amount'), 
		dataIndex: 'amount',
		xtype: 'numbercolumn', 
		format:'0,0.00',
		align:'right'
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.billType'), 
		dataIndex: 'billType',
		renderer: function(value) {
			if(value == null){
				return null;
			}else{
				return consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.cashCollectionBill');
			}
			}
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.paymentType'),
		dataIndex: 'paymentType',
		renderer:function(value){
			var displayField = FossDataDictionary. rendererSubmitToDisplay (value,'SETTLEMENT__PAYMENT_TYPE');
    		return displayField;
		}
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.transportFee'), 
		dataIndex: 'transportFee',
		xtype: 'numbercolumn', 
		format:'0,0.00',
		align:'right'
	},{//发票标记
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.invoiceMark'), 
		dataIndex: 'invoiceMark',
		hidden:true,
		renderer:function(value){
			var displayField = FossDataDictionary. rendererSubmitToDisplay (value,'SETTLEMENT_INVOICE_MARK');
    		return displayField;
		}
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.sourceBillType'), 
		dataIndex: 'sourceBillType',
		hidden:true,
		renderer:function(value){
			var displayField = FossDataDictionary. rendererSubmitToDisplay (value,'BILL_CASH_COLLECTION__SOURCE_BILL_TYPE');
    		return displayField;
		}
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.customerCode'), 
		dataIndex: 'customerCode',
		hidden:true
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.collectionOrgCode'), 
		dataIndex: 'collectionOrgCode',
		hidden:true
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.collectionCompanyCode'), 
		dataIndex: 'collectionCompanyCode',
		hidden:true
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.collectionCompanyName'), 
		dataIndex: 'collectionCompanyName',
		hidden:true
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.generatingOrgCode'), 
		dataIndex: 'generatingOrgCode',
		hidden:true
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.generatingCompanyCode'), 
		dataIndex: 'generatingCompanyCode',
		hidden:true
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.generatingCompanyName'), 
		dataIndex: 'generatingCompanyName',
		hidden:true
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.pickupFee'), 
		dataIndex: 'pickupFee',
		hidden:true,
		xtype: 'numbercolumn', 
		format:'0,0.00',
		align:'right'
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.deliveryGoodsFee'), 
		dataIndex: 'deliveryGoodsFee',
		hidden:true,
		xtype: 'numbercolumn', 
		format:'0,0.00',
		align:'right'
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.packagingFee'), 
		dataIndex: 'packagingFee',
		hidden:true,
		xtype: 'numbercolumn', 
		format:'0,0.00',
		align:'right'
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.codFee'), 
		dataIndex: 'codFee',
		hidden:true,
		xtype: 'numbercolumn', 
		format:'0,0.00',
		align:'right'
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.insuranceFee'), 
		dataIndex: 'insuranceFee',
		hidden:true,
		xtype: 'numbercolumn', 
		format:'0,0.00',
		align:'right'
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.otherFee'), 
		dataIndex: 'otherFee',
		hidden:true,
		xtype: 'numbercolumn', 
		format:'0,0.00',
		align:'right'
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.smallFee'), 
		dataIndex: 'smallFee',
		hidden:true,
		xtype: 'numbercolumn', 
		format:'0,0.00',
		align:'right'
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.active'), 
		dataIndex: 'active',
		hidden:true,
		renderer:function(value){
			var displayField = FossDataDictionary.rendererSubmitToDisplay(value,settlementDict.FOSS_ACTIVE);
    		return displayField;
		}
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.isRedBack'), 
		dataIndex: 'isRedBack',
		hidden:true,
		renderer:function(value){
			var displayField = FossDataDictionary. rendererSubmitToDisplay (value,'SETTLEMENT__IS_RED_BACK');
    		return displayField;
		}
	},{
		
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.common.isInit'), 
		dataIndex: 'isInit',
		hidden:true,
		renderer:function(value){
    		var displayField = FossDataDictionary. rendererSubmitToDisplay (value,settlementDict.FOSS_BOOLEAN);
    		return displayField;
    	}
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.createUserCode'), 
		dataIndex: 'createUserCode',
		hidden:true
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.createUserName'), 
		dataIndex: 'createUserName',
		hidden:true
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.cashConfirmUserCode'), 
		dataIndex: 'cashConfirmUserCode',
		hidden:true
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.cashConfirmUserName'), 
		dataIndex: 'cashConfirmUserName',
		hidden:true
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.businessDate'), 
		dataIndex: 'businessDate',
		hidden:true,
	    	renderer:function(value){
	    		if(value!=null){
	    			return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
	    		}else{
	    			return value;
	    		}
	    	}
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.accountDate'), 
		dataIndex: 'accountDate',
		hidden:true,
	    	renderer:function(value){
	    		if(value!=null){
	    			return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
	    		}else{
	    			return value;
	    		}
	    	}
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.conrevenDate'), 
		dataIndex: 'conrevenDate',
		hidden:true,
	    	renderer:function(value){
	    		if(value!=null){
	    			return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
	    		}else{
	    			return value;
	    		}
	    	}
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.cashConfirmTime'), 
		dataIndex: 'cashConfirmTime',
		hidden:true, 
	    	renderer:function(value){
	    		if(value!=null){
	    			return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
	    		}else{
	    			return value;
	    		}
	    	}
	},{
		header: consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.notes'), 
		dataIndex: 'notes',
		hidden:true
	}, {
		header : consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.expressOrigOrgCode'),
		dataIndex : 'expressOrigOrgCode',
		hidden : true
	}, {
		header : consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.expressOrigOrgName'),
		dataIndex : 'expressOrigOrgName',
		hidden : true
	}, {
		header : consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.expressDestOrgCode'),
		dataIndex : 'expressDestOrgCode',
		hidden : true
	}, {
		header : consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.expressDestOrgName'),
		dataIndex : 'expressDestOrgName',
		hidden : true
	}]
});

//分片Tab
Ext.define('Foss.CashCollectionBill.QueryDetailsTab', {
	extend:'Ext.panel.Panel',
	layout: 'card'
	});

/**
 * 主启动方法
 */	
Ext.onReady(function() {
		Ext.QuickTips.init();
		
		var CashCollectionBillQueryInfoTab = Ext.create('Foss.Consumer.CashCollectionBillQueryInfoTab');
												
		var BillNoQueryEntryStore = Ext.create('Foss.CashCollectionBill.BillNoQueryEntryStore');

		var BillNoQueryInfoGrid = Ext.create('Foss.CashCollectionBill.BillNoQueryInfoGrid',{
			id:'Foss_CashCollectionBill_BillNoQueryInfoGrid_Id',
			store:BillNoQueryEntryStore,
			dockedItems: [{
				xtype: 'toolbar',
				dock: 'top',
				layout:'column',
				defaults :{
					margin :'0 0 5 3'
				},
				items: [{
					xtype:'button',
					text:consumer.queryCashCollectionBill.i18n('foss.stl.consumer.common.export'),
					columnWidth:.1,
					handler: function() {
						consumer.exportBillCashCollection('WB');
					},
					disabled:!consumer.queryCashCollectionBill.isPermission('/stl-web/consumer/exportBillCashCollection.action'),
					hidden:!consumer.queryCashCollectionBill.isPermission('/stl-web/consumer/exportBillCashCollection.action')
				}]
			},{
				xtype: 'toolbar',
				dock: 'bottom',
				layout:'column',
				items: [{
					height:5,
					columnWidth:1
				},{
					xtype:'textfield',
					readOnly:true,
					name:'totalecordsInDBInBillNoGrid',
					columnWidth:.1,
					id:'Foss_CashCollectionBill_BillNoQueryInfoGrid_TotalRecords_Id',
					labelWidth:40,
					fieldLabel:consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.sum')
				},{
					xtype:'textfield',
					readOnly:true,
					name:'totalAmount',
					columnWidth:.15,
					id:'Foss_CashCollectionBill_BillNoQueryInfoGrid_TotalAmount_Id',
					labelWidth:56,
					fieldLabel:consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.totalAmount')
				}]
			}]
		});
		
		var SourceNoQueryInfoStore = Ext.create('Foss.CashCollectionBill.BillNoQueryEntryStore');

		var SourceNoQueryInfoGrid = Ext.create('Foss.CashCollectionBill.SourceNoQueryInfoGrid',{
			id:'Foss_CashCollectionBill_SourceNoQueryInfoGrid_Id',
			store:SourceNoQueryInfoStore,
			dockedItems: [{
				xtype: 'toolbar',
				dock: 'top',
				layout:'column',
				defaults :{
					margin :'0 0 5 3'
				},
				items: [{
					xtype:'button',
					text:consumer.queryCashCollectionBill.i18n('foss.stl.consumer.common.export'),
					columnWidth:.1,
					handler: function() {
						consumer.exportBillCashCollection('SB');
					},
					disabled:!consumer.queryCashCollectionBill.isPermission('/stl-web/consumer/exportBillCashCollection.action'),
					hidden:!consumer.queryCashCollectionBill.isPermission('/stl-web/consumer/exportBillCashCollection.action')
				}]
			},{
				xtype: 'toolbar',
				dock: 'bottom',
				layout:'column',
				items: [{
					height:5,
					columnWidth:1
				},{
					xtype:'textfield',
					readOnly:true,
					name:'totalecordsInDBInBillNoGrid',
					columnWidth:.1,
					id:'Foss_CashCollectionBill_SourceNoQueryInfoGrid_TotalRecords_Id',
					labelWidth:40,
					fieldLabel:consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.sum')
				},{
					xtype:'textfield',
					readOnly:true,
					name:'totalAmount',
					columnWidth:.15,
					id:'Foss_CashCollectionBill_SourceNoQueryInfoGrid_TotalAmount_Id',
					labelWidth:56,
					fieldLabel:consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.totalAmount')
				}]
			}]
		});
		
		
		var BatchNoQueryInfoStore = Ext.create('Foss.CashCollectionBill.BillNoQueryEntryStore');

		var BatchQueryInfoGrid = Ext.create('Foss.CashCollectionBill.BatchNoQueryInfoGrid',{
			id:'Foss_CashCollectionBill_BatchNoQueryInfoGrid_Id',
			store:BatchNoQueryInfoStore,
			dockedItems: [{
				xtype: 'toolbar',
				dock: 'top',
				layout:'column',
				defaults :{
					margin :'0 0 5 3'
				},
				items: [{
					xtype:'button',
					text:consumer.queryCashCollectionBill.i18n('foss.stl.consumer.common.export'),
					columnWidth:.1,
					handler: function() {
						consumer.exportBillCashCollection('BB');
					},
					disabled:!consumer.queryCashCollectionBill.isPermission('/stl-web/consumer/exportBillCashCollection.action'),
					hidden:!consumer.queryCashCollectionBill.isPermission('/stl-web/consumer/exportBillCashCollection.action')
				}]
			},{
				xtype: 'toolbar',
				dock: 'bottom',
				layout:'column',
				items: [{
					height:5,
					columnWidth:1
				},{
					xtype:'textfield',
					readOnly:true,
					name:'totalecordsInDBInBillNoGrid',
					columnWidth:.1,
					id:'Foss_CashCollectionBill_BatchNoQueryInfoGrid_TotalRecords_Id',
					labelWidth:40,
					fieldLabel:consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.sum')
				},{
					xtype:'textfield',
					readOnly:true,
					name:'totalAmount',
					columnWidth:.15,
					id:'Foss_CashCollectionBill_BatchNoQueryInfoGrid_TotalAmount_Id',
					labelWidth:56,
					fieldLabel:consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.totalAmount')
				}]
			}]
		});
		
		
		//创建按日期查询应收单列表Store
		var DateQueryInfoGridStore = Ext.create('Foss.CashCollectionBill.DateQueryEntityStore',{
			listeners : {
					beforeload : function(store, operation, eOpts) {
						var form = Ext.getCmp('Foss_CashCollectionBill_QueryByDate_ID');
						if (form) {
							var billCashCollectionQueryVO = new Object();
							var selectBusinessDate = Ext.getCmp('queryCashCollectionBill_BusinessData_Id').getValue();
							var selectAccountDate = Ext.getCmp('queryCashCollectionBill_AccountData_Id').getValue();
							var startDateTemp;
							var endDateTemp;
							var selectDateType;
							if(selectBusinessDate && !selectAccountDate){
								selectDateType = 'BU';
							}else if(!selectBusinessDate && selectAccountDate){
								selectDateType = 'AC';
							}
							startDateTemp = form.getForm().findField('startDate').getValue();
							endDateTemp = form.getForm().findField('endDate').getValue();
							
							var params = {
									'billCashCollectionQueryVO.dto.selectDateType':selectDateType,
									'billCashCollectionQueryVO.dto.startDate':startDateTemp,
									'billCashCollectionQueryVO.dto.endDate':endDateTemp,
									'billCashCollectionQueryVO.dto.active':form.getForm().findField('active').getValue(),
									'billCashCollectionQueryVO.dto.collectionOrgCode':form.getForm().findField('collectionOrgName').getValue(),
									'billCashCollectionQueryVO.dto.generatingOrgCode':form.getForm().findField('generatingOrgName').getValue(),
									'billCashCollectionQueryVO.dto.customerCode':form.getForm().findField('customerName').getValue(),
									'billCashCollectionQueryVO.dto.status':form.getForm().findField('status').getValue(),
									'billCashCollectionQueryVO.dto.paymentType':form.getForm().findField('paymentType').getValue(),
									'billCashCollectionQueryVO.dto.isInit':form.getForm().findField('isInit').getValue(),
									'billCashCollectionQueryVO.dto.productCode':consumer.queryCashCollectionBill.convertProductCode(form.getForm().findField('productCode').getValue()),
									'billCashCollectionQueryVO.dto.posSerialNum':Ext.String.trim(form.getForm().findField('posSerialNum').getValue())

									};	
							Ext.apply(operation,{
								params:params
							});
						}
					}
				}
		});	
		
		//按日期查询应收单列表Grid
		var DateQueryInfoGrid = Ext.create('Foss.CashCollectionBill.DateQueryInfoGrid',{
			id:'Foss_CashCollectionBill_DateQueryInfoGrid_Id',
			store:DateQueryInfoGridStore,
			dockedItems: [{
				xtype: 'toolbar',
				dock: 'top',
				layout:'column',
				defaults :{
					margin :'0 0 5 3'
				},
				items: [{
					xtype:'button',
					text:consumer.queryCashCollectionBill.i18n('foss.stl.consumer.common.export'),
					columnWidth:.1,
					handler: function() {
						consumer.exportBillCashCollection('TD');
					},
					disabled:!consumer.queryCashCollectionBill.isPermission('/stl-web/consumer/exportBillCashCollection.action'),
					hidden:!consumer.queryCashCollectionBill.isPermission('/stl-web/consumer/exportBillCashCollection.action')
				},{
					xtype:'container',
					border:false,
					html:'&nbsp;',
					columnWidth:.6
				}]
			},{
				xtype: 'toolbar',
				dock: 'bottom',
				layout:'vbox',
				items: [{
						height:5,
						width:1600
				},{
					xtype:'panel',
					layout:'hbox',
					items:[{
				    		xtype:'textfield',
				    		readOnly:true,
				    		name:'totalRecordsInDBInDateGrid',
				    		columnWidth:.07,
				    		id:'Foss_CashCollectionBill_DateQueryInfoGrid_TotalRecords_id',
				    		labelWidth:40,
				    		fieldLabel:consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.sum')
				    	},{
				    		xtype:'textfield',
				    		readOnly:true,
				    		name:'totalAmountInDateGrid',
				    		columnWidth:.16,
				    		id:'Foss_CashCollectionBill_DateQueryInfoGrid_TotalAmount_id',
				    		labelWidth:56,
				    		fieldLabel:consumer.queryCashCollectionBill.i18n('foss.stl.consumer.queryCashCollectionBill.totalAmount')
				    	},Ext.create('Deppon.StandardPaging', {
				    		store: DateQueryInfoGridStore,
				    		pageSize: 50,
				    		columnWidth:1,
				    		plugins: Ext.create('Deppon.ux.PageSizePlugin', {
								//设置分页记录最大值，防止输入过大的数值
								maximumSize: 200
							})
				    })]
				}]
			}]
		});
		
		var QueryCashCollectionBillGrid = Ext.create('Foss.CashCollectionBill.QueryDetailsTab',{
			items: [
			    DateQueryInfoGrid,
				BillNoQueryInfoGrid,
				SourceNoQueryInfoGrid,
				BatchQueryInfoGrid
			]
		});
		
		
		Ext.create('Ext.panel.Panel',{
			id: 'T_consumer-queryCashCollectionBill_content',
			cls: "panelContentNToolbar",
			bodyCls: 'panelContentNToolbar-body',
			layout: 'auto',
			items: [CashCollectionBillQueryInfoTab,QueryCashCollectionBillGrid],
			renderTo: 'T_consumer-queryCashCollectionBill-body'
		});
});

