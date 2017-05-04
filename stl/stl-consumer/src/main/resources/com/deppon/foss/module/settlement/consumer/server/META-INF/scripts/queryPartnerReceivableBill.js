/**
 * 
 * @param {} 产品类型
 * @return {}此处如果有全选，则需要转化下
 */
consumer.queryPartnerReceivableBill.convertProductCode = function(productCodes){
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

//导出方法
consumer.queryPartnerReceivableBill.exportBillReceivable = function() {
	var	columns,
		searchParams,
		arrayColumns,
		arrayColumnNames;
	//进行导出提示
	Ext.MessageBox.show({
        title: consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.common.alert'),
        msg: consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.isExport'),
        buttons: Ext.MessageBox.YESNO,
        fn: function(e){
    		if (!Ext.fly('downloadAttachFileForm')) {
				var frm = document.createElement('form');
				frm.id = 'downloadAttachFileForm';
				frm.style.display = 'none';
				document.body.appendChild(frm);
			}
    		var receivableGrid = Ext.getCmp('T_consumer-queryPartnerReceivableBill_content').getReceivableGrid();
   			if(e=='yes'){
	   			//转化列头和列明
	   			columns = receivableGrid.columns;
	   			arrayColumns = [];
	   			arrayColumnNames = [];
	   			//将前台对应列头传入到后台去
	   			for(var i=1;i<columns.length;i++){
	   				if(columns[i].isHidden()==false){
	   					var hederName = columns[i].text;
	   					var dataIndex = columns[i].dataIndex;
	   					if(columns[i].text!='操作列'){
	   						arrayColumns.push(dataIndex);
	   						arrayColumnNames.push(hederName);
	   					}
	   				}
	   			}
	   			//封装参数
	   			var params;
	   			if(consumer.queryPartnerReceivableBill.queryTab == 'TD'){
					params = {
						'billReceivableVO.dto.selectDateType' : consumer.queryPartnerReceivableBill.isDate,
						'billReceivableVO.dto.startDate' : consumer.queryPartnerReceivableBill.startDateTemp,
						'billReceivableVO.dto.endDate' : consumer.queryPartnerReceivableBill.endDateTemp,
						'billReceivableVO.dto.department' : consumer.queryPartnerReceivableBill.department,
						'billReceivableVO.dto.bigArea' : consumer.queryPartnerReceivableBill.bigArea,
						'billReceivableVO.dto.smallArea' : consumer.queryPartnerReceivableBill.smallArea,
						'billReceivableVO.dto.sourceBillType' : consumer.queryPartnerReceivableBill.sourceBillType,
						'billReceivableVO.dto.customerCode' : consumer.queryPartnerReceivableBill.customerCodeSelected,		
						'billReceivableVO.dto.billType' : consumer.queryPartnerReceivableBill.billType,
						'billReceivableVO.dto.active' : consumer.queryPartnerReceivableBill.active,
						'billReceivableVO.dto.isDept' : consumer.queryPartnerReceivableBill.isDept,
						'billReceivableVO.dto.productCode' : consumer.queryPartnerReceivableBill.productCode,
						'billReceivableVO.dto.queryTab' : consumer.queryPartnerReceivableBill.queryTab
					};
				}else if(consumer.queryPartnerReceivableBill.queryTab == 'SB'){
					params = {
						'billReceivableVO.sourceBillNosArray' : consumer.queryPartnerReceivableBill.sourceBillNosArray,
						'billReceivableVO.dto.queryTab' : consumer.queryPartnerReceivableBill.queryTab
					};
				}else if(consumer.queryPartnerReceivableBill.queryTab == 'WB'){
					params = {
						'billReceivableVO.wayBillNosArray' : consumer.queryPartnerReceivableBill.wayBillNosArray,
						'billReceivableVO.dto.queryTab' : consumer.queryPartnerReceivableBill.queryTab
					};
				}
	   			//导出列
	   			params['billReceivableVO.dto.arrayColumns'] = arrayColumns;
	   			//导出列名称
	   			params['billReceivableVO.dto.arrayColumnNames'] = arrayColumnNames;
	   			Ext.Ajax.request({
	   				url : consumer.realPath('exportPartnerBillReceivable.action'),
	   				form : Ext.fly('downloadAttachFileForm'),
	   				method : 'post',
	   				params : params,
	   				isUpload : true,
	   				success : function(response) {
	   					var result = Ext.decode(response.responseText);
	   					// 如果异常信息有值，则弹出提示
	   					if (!Ext.isEmpty(result.errorMessage)) {
	   						Ext.Msg.alert(consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.common.warmTips'), result.errorMessage);
	   						return false;
	   					}
	   					Ext.ux.Toast.msg(consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.common.warmTips'), 
	   						consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.exportSuccess'), 'success', 1000);
	   				},
	   				failure : function(response) {
	   					Ext.ux.Toast.msg(consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.common.warmTips'), 
	   						consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.exportFail'), 'error', 1000);
	   				}
	   			});
   			}
        }
    });
}

//按应收单查询应收单明细
consumer.queryPartnerReceivableBill.queryDetailByReceivableNo = function(grid, rowIndex, colIndex){
	//获取表头数据
	var selection = grid.getStore().getAt(rowIndex);
	//获取grid
	var receivableDetailGrid = Ext.getCmp('T_consumer-queryPartnerReceivableBill_content').getReceivableDetailGrid();
	//封装参数
	consumer.queryPartnerReceivableBill.detailParams = {
			"receivableDetailVO.receivableNo" : selection.data.receivableNo
		};
	//获取URL
	var queryReceivableDetailUrl = consumer.realPath('queryReceivableDetail.action');
	//获取明细store
	var receivableDetailGridStore = receivableDetailGrid.store;
	//设置url
	receivableDetailGridStore.getProxy().url = queryReceivableDetailUrl;
	//查询
	receivableDetailGridStore.loadPage(1, {
		callback: function(records, operation, success) {
			var rawData = receivableDetailGridStore.proxy.reader.rawData;
			if(!success){
				var result = Ext.decode(response.responseText);	
				Ext.Msg.alert(consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.alertMessage'),result.message);
				return false;
			}
			//如果成功显示
			if(success){
				//对结果进行转化
				var result = Ext.decode(operation.response.responseText);
				//判断查询结果
				if(Ext.isEmpty(result.receivableDetailVO.receivableDEntityList) || result.receivableDetailVO.receivableDEntityList.length==0){
					Ext.Msg.alert(consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.alertMessage'),
							consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.emptyResult.title'));
					return false;
				}
			}
	    }
	});
}

consumer.queryPartnerReceivableBill.querySuccess = function(queryurl) {
	//获取grid
	var receivableGrid = Ext.getCmp('T_consumer-queryPartnerReceivableBill_content').getReceivableGrid();
	//获取store
	var receivableGridStore = receivableGrid.store;
	//设置url
	receivableGridStore.getProxy().url = queryurl;
	receivableGridStore.loadPage(1, {
		callback: function(records, operation, success) {
			var rawData = receivableGrid.store.proxy.reader.rawData;
			if(!success){
				var result = Ext.decode(response.responseText);	
				Ext.Msg.alert(consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.alertMessage'),result.message);
				return false;
			}
			//如果成功显示
			if(success){
				//对结果进行转化
				var result = Ext.decode(operation.response.responseText); 
				//判断查询结果
				if(Ext.isEmpty(result.billReceivableVO.billReceivableList) || result.billReceivableVO.billReceivableList.length==0){
					Ext.Msg.alert(consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.alertMessage'),
							consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.emptyResult.title'));
					return false;
				} else {
					var tbars = receivableGrid.dockedItems.items[3].query('textfield');
					tbars[0].setValue(result.billReceivableVO.totalRecordsInDB);
					tbars[1].setValue(result.billReceivableVO.totalAmount);
					tbars[2].setValue(result.billReceivableVO.totalVerifyAmount);
					tbars[3].setValue(result.billReceivableVO.totalUnverifyAmount);
				}
			}
	    }
	});
}

//按来源单号查询应收单
consumer.queryPartnerReceivableBill.queryBySourceBillNo = function(form, me){
	//获取form
	var form = this.up('form').getForm();
	//设置来源单号集合
	var sourceBillNosArray = [];
	//获取来源单号
	var sourceBillNos = this.up('form').getForm().findField('sourceBillNos').getValue();
	//循环
	if (Ext.String.trim(sourceBillNos) != null
			&& Ext.String.trim(sourceBillNos) != '') {
		var reg = /[,;]/;
		array = sourceBillNos.split(reg);
		for (var i = 0; i < array.length; i++) {
			if (Ext.String.trim(array[i]) != '') {
				sourceBillNosArray.push(array[i]);
			}
		}
	} else {
		Ext.Msg.alert(consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.common.warmTips'),
			consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.waybillNosIsNotNull'));
		return false;
	}

	if (sourceBillNosArray.length > 10) {
		Ext.Msg.alert(consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.common.warmTips'),
			consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.sourceBillNosInputToMore'));
		return false;
	}
	//设置查询类别
	consumer.queryPartnerReceivableBill.queryTab = 'SB';
	consumer.queryPartnerReceivableBill.sourceBillNosArray = sourceBillNosArray;
	//获取url
	var queryReceivableBillBySourceBillNoUrl = consumer.realPath('queryPartnerReceivableBillBySourceBillNo.action');
	consumer.queryPartnerReceivableBill.querySuccess(queryReceivableBillBySourceBillNoUrl);
}

/**
 * 按单号查询
 */
consumer.queryPartnerReceivableBill.queryByNo = function(form, me) {
	//获取form
	var form = this.up('form').getForm();
	//运单集合
	var wayBillNosArray = [];
	//获取运单号
	var waybillNos = this.up('form').getForm().findField('waybillNos').getValue();
	//循环
	if (Ext.String.trim(waybillNos) != null&& Ext.String.trim(waybillNos) != '') {
		var reg = /[,;]/;
		array = waybillNos.split(reg);
		for (var i = 0; i < array.length; i++) {
			if (Ext.String.trim(array[i]) != '') {
				wayBillNosArray.push(array[i]);
			}
		}
	} else {
		Ext.Msg.alert(consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.common.warmTips'),
			consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.waybillNosIsNotNull'));
		return false;
	}
	if (wayBillNosArray.length > 10) {
		Ext.Msg.alert(consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.common.warmTips'),
			consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.waybillNosInputToMore'));
		return false;
	}
	//设置查询类别
	consumer.queryPartnerReceivableBill.queryTab = 'WB';
	consumer.queryPartnerReceivableBill.wayBillNosArray = wayBillNosArray;
	//获取url
	var queryReceivableBillByWayBillNoUrl = consumer.realPath('queryPartnerReceivableBillByWayBillNo.action');
	consumer.queryPartnerReceivableBill.querySuccess(queryReceivableBillByWayBillNoUrl);
}

/**
 * 按日期查询
 */
consumer.queryPartnerReceivableBill.queryByDate = function(form, me) {
	//获取form
	var form = this.up('form').getForm();
	//日期类型
	var selectDateType = form.getValues().selectDateType;
	//部门类型
	var selectDeptType = form.getValues().selectDeptType;
	if ('1'==selectDateType) {
		consumer.queryPartnerReceivableBill.isDate = 'BU';
	}else{
		consumer.queryPartnerReceivableBill.isDate = 'AC';
	}	
	//判断用户是查询始发或者是到达（默认查始发）							
	if('1'==selectDeptType){
		consumer.queryPartnerReceivableBill.isDept='DUNNING_ORG_CODE'
	}else{
		consumer.queryPartnerReceivableBill.isDept='GENERATING_ORG_CODE'
	}
	consumer.queryPartnerReceivableBill.startDateTemp = form.findField('startDate').getValue();
	consumer.queryPartnerReceivableBill.endDateTemp = form.findField('endDate').getValue();
	consumer.queryPartnerReceivableBill.customerCodeSelected = form.findField('customerName').getValue();
	consumer.queryPartnerReceivableBill.department = form.findField('department').getValue();
	consumer.queryPartnerReceivableBill.bigArea = form.findField('bigArea').getValue();
	consumer.queryPartnerReceivableBill.smallArea = form.findField('smallArea').getValue();
	consumer.queryPartnerReceivableBill.sourceBillType = form.findField('sourceBillType').getValue();
	consumer.queryPartnerReceivableBill.billType = form.findField('billType').getValue();
	consumer.queryPartnerReceivableBill.active = form.findField('active').getValue();
	consumer.queryPartnerReceivableBill.productCode = consumer.queryPartnerReceivableBill.convertProductCode(form.findField('productCode').getValue());
	
	if (Ext.isEmpty(consumer.queryPartnerReceivableBill.startDateTemp)) {
		Ext.Msg.alert(consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.common.warmTips'), 
			consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.startDateIsNotNull'));
		return false;
	}
	
	if (Ext.isEmpty(consumer.queryPartnerReceivableBill.endDateTemp)) {
		Ext.Msg.alert(consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.common.warmTips'), 
			consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.endDateIsNotNull'));
		return false;
	}
	
	if (consumer.queryPartnerReceivableBill.startDateTemp > consumer.queryPartnerReceivableBill.endDateTemp) {
		Ext.Msg.alert(consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.common.warmTips'), 
			consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.endDateIsNotBeforeStart'));
		return false;
	}
	
	if (stl.compareTwoDate(consumer.queryPartnerReceivableBill.startDateTemp, consumer.queryPartnerReceivableBill.endDateTemp) > stl.DATELIMITDAYS_MONTH) {
		Ext.Msg.alert(consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.common.warmTips'), 
			consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.startToEndDateIsNotMaxDay') + stl.DATELIMITDAYS_MONTH+
			consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.day'));
		return false;	
	}
	
	if(Ext.isEmpty(consumer.queryPartnerReceivableBill.bigArea) 
			&& Ext.isEmpty(consumer.queryPartnerReceivableBill.smallArea) 
			&& Ext.isEmpty(consumer.queryPartnerReceivableBill.department)){
		Ext.Msg.alert(consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.common.warmTips'),
			consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.validateRegionFailAlert'));
		return false;
	}
	consumer.queryPartnerReceivableBill.queryTab = 'TD';

	var queryReceivableBillByDateUrl = consumer.realPath('queryPartnerReceivableBillByDate.action');
	consumer.queryPartnerReceivableBill.querySuccess(queryReceivableBillByDateUrl);
}

Ext.define('Foss.Stlyuscyings.IsActiveModel', {
	extend : 'Ext.data.Model',
	fields : [{
				name : 'name'
			}, {
				name : 'value'
			}]
});

Ext.define('Foss.Stlyuscyings.IsActiveStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.Stlyuscyings.IsActiveModel',
	data : [{
				name : consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.common.all'),
				value : ''
			}, {
				name : consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.common.isActiveY'),
				value : 'Y'
			}, {
				name : consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.common.isActiveN'),
				value : 'N'
			}]
});

/**
 * 表格模型
 */
Ext.define('Foss.PartnerReceivableBill.ReceivableGridModel', {
	extend : 'Ext.data.Model',
	fields : [{
				name : 'waybillNo'
			}, {
				name : 'receivableNo'
			}, {
				name : 'customerName'
			}, {
				name : 'customerCode'
			}, {
				name : 'paymentType'
			}, {
				name : 'active'
			}, {
				name : 'amount',
				type : 'long'
			}, {
				name : 'verifyAmount',
				type : 'long'
			}, {
				name : 'unverifyAmount',
				type : 'long'
			}, {
				name : 'createType'
			}, {
				name : 'sourceBillNo'
			}, {
				name : 'sourceBillType'
			}, {
				name : 'billType'
			}, {
				name : 'receivableOrgCode'
			}, {
				name : 'receivableOrgName'
			}, {
				name : 'generatingOrgCode'
			}, {
				name : 'generatingOrgName'
			}, {
				name : 'generatingComCode'
			}, {
				name : 'generatingComName'
			}, {
				name : 'dunningOrgCode'
			}, {
				name : 'dunningOrgName'
			}, {
				name : 'origOrgCode'
			}, {
				name : 'origOrgName'
			}, {
				name : 'destOrgCode'
			}, {
				name : 'destOrgName'
			}, {
				name : 'deliveryCustomerCode'
			}, {
				name : 'deliveryCustomerName'
			}, {
				name : 'receiveCustomerCode'
			}, {
				name : 'receiveCustomerName'
			}, {
				name : 'currencyCode'
			}, {
				name : 'businessDate',
				type:'date',
				convert:stl.longToDateConvert 
			}, {
				name : 'accountDate',
				type:'date',
				convert:stl.longToDateConvert 
			}, {
				name : 'conrevenDate',
				type:'date',
				convert:stl.longToDateConvert 
			}, {
				name : 'productCode'
			}, {
				name : 'transportFee',
				type : 'long'
			}, {
				name : 'pickupFee',
				type : 'long'
			}, {
				name : 'deliveryGoodsFee',
				type : 'long'
			}, {
				name : 'packagingFee',
				type : 'long'
			}, {
				name : 'codFee',
				type : 'long'
			}, {
				name : 'insuranceFee',
				type : 'long'
			}, {
				name : 'otherFee',
				type : 'long'
			}, {
				name : 'valueAddFee',
				type : 'long'
			}, {
				name : 'promotionsFee',
				type : 'long'
			}, {
				name : 'goodsName'
			}, {
				name : 'goodsVolumeTotal',
				type : 'long'
			}, {
				name : 'billWeight',
				type : 'long'
			}, {
				name : 'receiveMethod'
			}, {
				name : 'customerPickupOrgCode'
			}, {
				name : 'goodsQtyTotal',
				type : 'long'
			}, {
				name : 'targetOrgCode'
			}, {
				name : 'productId'
			}, {
				name : 'isRedBack'
			}, {
				name : 'isInit'
			}, {
				name : 'createUserCode'
			}, {
				name : 'createUserName'
			}, {
				name : 'createOrgCode'
			}, {
				name : 'createOrgName'
			}, {
				name : 'createTime',
				type:'date',
				convert:stl.longToDateConvert 
			}, {
				name : 'modifyTime',
				type:'date',
				convert:stl.longToDateConvert 
			}, {
				name : 'modifyUserCode'
			}, {
				name : 'modifyUserName'
			}, {
				name : 'statementBillNo'
			}, {
				name : 'unlockDateTime',
				type:'date',
				convert:stl.longToDateConvert 
			}, {
				name : 'lockCustomerCode'
			}, {
				name : 'lockCustomerName'
			}, {
				name : 'collectionType'
			}, {
				name : 'collectionName'
			}, {
				name : 'auditUserCode'
			}, {
				name : 'auditUserName'
			}, {
				name : 'auditUserCode'
			}, {
				name : 'approveStatus'
			}, {
				name : 'auditDate',
				type:'date',
				convert:stl.longToDateConvert 
			}, {
				name : 'isDisable'
			}, {
				name : 'disableUserCode'
			}, {
				name : 'disableUserName'
			}, {
				name : 'notes'
			}, {
				name : 'stamp'
			}, {
				name : 'expressOrigOrgCode'
			}, {
				name : 'expressOrigOrgName'
			}, {
				name : 'expressDestOrgCode'
			}, {
				name : 'expressDestOrgName'
			}, {
				name:'invoiceMark'
			}, {
		        name:'unifiedSettlement',
		        type:'string'
		    }, {
		        name:'contractOrgCode',
		        type:'string'
		    }, {
		        name:'contractOrgName',
		        type:'string'
		    }, {
		        name:'isDiscount',
		        type:'string'
		    }, {
		    	name:'withholdStatus'
		    }
		]
});

/**
 * 应收单明细模型
 */
Ext.define('Foss.PartnerReceivableBill.ReceivableDetailGridModel', {
	extend : 'Ext.data.Model',
	fields : [{
				name : 'receivableNo'
			}, {
				name : 'active'
			}, {
				name : 'amount',
				type : 'long'
			}, {
				name : 'sourceBillNo'
			}, {
				name : 'receivableType'
			}, {
				name : 'createTime',
				type:'date',
				convert:stl.longToDateConvert 
			}, {
				name : 'createUserCode'
			}, {
				name : 'createUserName'
			}
    ]
});
/**
 * 应收单store
 */
Ext.define('Foss.PartnerReceivableBill.ReceivableGridStore', {
		extend : 'Ext.data.Store',
		model : 'Foss.PartnerReceivableBill.ReceivableGridModel',
		sorters : [{
					property : 'businessDate',
					direction : 'ASC'
				}],
		proxy : {
			type : 'ajax',
			url : null,
			actionMethods : 'post',
			reader : {
				type : 'json',
				root : 'billReceivableVO.billReceivableList',
				totalProperty : 'totalCount'
			}
		},
		listeners : {
			'beforeLoad' : function(store, operation, eOpts) {
					var params;
					if(consumer.queryPartnerReceivableBill.queryTab == 'TD'){
						params = {
							'billReceivableVO.dto.selectDateType' : consumer.queryPartnerReceivableBill.isDate,
							'billReceivableVO.dto.startDate' : consumer.queryPartnerReceivableBill.startDateTemp,
							'billReceivableVO.dto.endDate' : consumer.queryPartnerReceivableBill.endDateTemp,
							'billReceivableVO.dto.department' : consumer.queryPartnerReceivableBill.department,
							'billReceivableVO.dto.bigArea' : consumer.queryPartnerReceivableBill.bigArea,
							'billReceivableVO.dto.smallArea' : consumer.queryPartnerReceivableBill.smallArea,
							'billReceivableVO.dto.sourceBillType' : consumer.queryPartnerReceivableBill.sourceBillType,
							'billReceivableVO.dto.customerCode' : consumer.queryPartnerReceivableBill.customerCodeSelected,		
							'billReceivableVO.dto.billType' : consumer.queryPartnerReceivableBill.billType,
							'billReceivableVO.dto.active' : consumer.queryPartnerReceivableBill.active,
							'billReceivableVO.dto.isDept' : consumer.queryPartnerReceivableBill.isDept,
							'billReceivableVO.dto.productCode' : consumer.queryPartnerReceivableBill.productCode,
							'billReceivableVO.dto.queryTab' : consumer.queryPartnerReceivableBill.queryTab,
							'billReceivableVO.dto.isPartner' : 'N'
						};
					}else if(consumer.queryPartnerReceivableBill.queryTab == 'SB'){
						params = {
							'billReceivableVO.sourceBillNosArray' : consumer.queryPartnerReceivableBill.sourceBillNosArray,
							'billReceivableVO.dto.queryTab' : consumer.queryPartnerReceivableBill.queryTab,
							'billReceivableVO.dto.isPartner' : 'N'
						};
					}else if(consumer.queryPartnerReceivableBill.queryTab == 'WB'){
						params = {
							'billReceivableVO.wayBillNosArray' : consumer.queryPartnerReceivableBill.wayBillNosArray,
							'billReceivableVO.dto.queryTab' : consumer.queryPartnerReceivableBill.queryTab,
							'billReceivableVO.dto.isPartner' : 'N'
						};
					}
					Ext.apply(operation, {
						params : params
					});
			}
		}
	});
/**
 * 应收单明细store
 */
Ext.define('Foss.PartnerReceivableBill.ReceivableDetailStore', {
		extend : 'Ext.data.Store',
		model : 'Foss.PartnerReceivableBill.ReceivableDetailGridModel',
		sorters : [{
					property : 'createTime',
					direction : 'ASC'
				}],
		proxy : {
			type : 'ajax',
			url : null,
			actionMethods : 'post',
			reader : {
				type : 'json',
				root : 'receivableDetailVO.receivableDEntityList'
			}
		},
		listeners : {
			'beforeLoad' : function(store, operation, eOpts) {
					var params = consumer.queryPartnerReceivableBill.detailParams;
					Ext.apply(operation, {
						params : params
					});
			}
		}
	});

/**
 * 查询tab
 */
Ext.define('Foss.PartnerReceivableBill.QueryInfoTab', {
	extend : 'Ext.tab.Panel',
	frame : false,
	bodyCls : 'autoHeight',
	cls : 'innerTabPanel',
	activeTab : 0,// 默认显示按单号制作
	height : 310,
	items : [{
		title : consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.common.queryByDate'),
		tabConfig : {
			width : 120
		},
		name : 'queryByDate',
		items : [{
			xtype : 'form',
			defaults : {
				labelWidth : 95,
				padding:5
			},
			layout : 'column',
			items : [{
						xtype : 'fieldcontainer',
						defaultType : 'radiofield',
						fieldLabel : consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.dateType'),
						layout : 'column',
						labelWidth : 95,
						columnWidth : .3,
						items : [{
									boxLabel : consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.common.businessDate'),
									checked : true,
									name : 'selectDateType',
									inputValue : '1'
								}, {
									boxLabel : consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.common.accountDate'),
									name : 'selectDateType',
									inputValue : '2'
								}]
					},{
						xtype : 'fieldcontainer',
						defaultType : 'radiofield',
						fieldLabel : consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.deptType'),
						layout : 'column',
						columnWidth : .3,
						labelWidth : 95,
						items : [{
									boxLabel : consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.dugDeptCode'),
									checked : true,
									name : 'selectDeptType',
									inputValue : '1'
								}, {
									boxLabel : consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.genDeptCode'),
									name : 'selectDeptType',
									inputValue : '2'
							}]
				    },{
				    	xtype : 'container',
						border : false,
						columnWidth : .3,
						height:24,
						html : '&nbsp;'
				    },{
						xtype : 'datefield',
						name : 'startDate',
						fieldLabel : consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.startDate'),
						editable : false,
						value : new Date(),
						format : 'Y-m-d',
						columnWidth : .3,
						allowBlank : false,
						labelWidth : 95,
						maxValue : new Date()
					}, {
						xtype : 'datefield',
						name : 'endDate',
						fieldLabel : consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.endDate'),
						value : new Date(),
						editable : false,
						format : 'Y-m-d',
						columnWidth : .3,
						allowBlank : false,
						labelWidth : 95,
						maxValue : new Date()
					},{
						xtype : 'combobox',
						name : 'sourceBillType',
						fieldLabel : consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.sourceBillType'),
						store : FossDataDictionary.getDataDictionaryStore(
								'BILL_RECEIVABLE__SOURCE_BILL_TYPE', null, {
									'valueCode' : '',
									'valueName' : consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.common.all')
								}),
						queryMode : 'local',
						editable : false,
						columnWidth : .3,
						labelWidth : 95,
						displayField : 'valueName',
						valueField : 'valueCode',
						value : ''
					}, {
						xtype : 'linkagecomboselector',
						eventType : ['focus'],// 一般callparent包含focus事件，如果有callparent,则focus事件可不用传递
						itemId : 'Foss_baseinfo_BigRegion_ID',
						store : Ext.create('Foss.baseinfo.commonSelector.BigRegionStore'),// 从外面传入
						fieldLabel : consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.bigArea'),
						value:'',
						minChars : 0,
						displayField : 'name',// 显示名称
						valueField : 'code',
						queryParam : 'commonOrgVo.name',
						name : 'bigArea',
						allowBlank : true,
						columnWidth : .3,
						listWidth : 300,// 设置下拉框宽度
						isPaging : true
						// 分页
					}, {
						xtype : 'linkagecomboselector',
						itemId : 'Foss_baseinfo_SmallRegion_ID',
						eventType : ['callparent'],// 一般callparent包含focus事件，如果有callparent,则focus事件可不用传递
						store : Ext.create('Foss.baseinfo.commonSelector.SmallRegionStore'),// 从外面传入
						name : 'smallArea',
						fieldLabel : consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.smallArea'),
						parentParamsAndItemIds : {
							'commonOrgVo.code' : 'Foss_baseinfo_BigRegion_ID'
						},// 此处城市不需要传入
						minChars : 0,
						displayField : 'name',// 显示名称
						valueField : 'code',
						queryParam : 'commonOrgVo.name',
						allowBlank : true,
						columnWidth : .3,
						listWidth : 300,// 设置下拉框宽度
						isPaging : true
						// 分页
        			}, {
						xtype : 'linkagecomboselector',
						name : 'department',
						eventType : ['callparent'],
						store : Ext.create('Foss.baseinfo.commonSelector.SmallRegionStore'),// 从外面传入
						fieldLabel : consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.org'),
						parentParamsAndItemIds : {
							'commonOrgVo.code' : 'Foss_baseinfo_SmallRegion_ID'
						},// 此处区域不需要传入
						minChars : 0,
						displayField : 'name',// 显示名称
						valueField : 'code',
						queryParam : 'commonOrgVo.name',
						allowBlank : true,
						columnWidth : .3,
						listWidth : 300,// 设置下拉框宽度
						isPaging : true
						// 分页
					}, {
						xtype : 'commonsaledepartmentselector',
						all:'true',
						name : 'customerName',
						fieldLabel : consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.common.partnerName'),
						allowBlank : true,
						isPaging : true,
						columnWidth : .3,
						height:24,
						singlePeopleFlag : 'Y'
						// 分页
					}, {
						xtype : 'combobox',
						name : 'billType',
						fieldLabel : consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.billType'),
						store : FossDataDictionary.getDataDictionaryStore(
								'BILL_RECEIVABLE__BILL_TYPE', null, {
									'valueCode' : '',
									'valueName' : consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.common.all')
								}),
						queryMode : 'local',
						editable : false,
						columnWidth : .3,
						displayField : 'valueName',
						valueField : 'valueCode',
						value : ''
					}, {
						xtype : 'combobox',
						name : 'active',
						fieldLabel : consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.common.active'),
						store : Ext.create('Foss.Stlyuscyings.IsActiveStore'),
						queryMode : 'local',
						editable : false,
						columnWidth : .3,
						displayField : 'name',
						valueField : 'value',
						value : 'Y' 
					}, {
				    	xtype:'combobox',
				    	name:'productCode',
				    	fieldLabel:consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.productCode'),
						store:Ext.create('Foss.pkp.ProductStore'),
						multiSelect : true,
					    queryMode: 'local', 	
						displayField:'name',
						valueField:'code',
						columnWidth : .3
				    }, {
						border : 1,
						xtype : 'container',
						columnWidth : 1,
						defaultType : 'button',
						layout : 'column',
						items : [{
									text : consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.common.reset'),
									columnWidth : .1,
									handler : function() {
										var form=this.up('form').getForm();
										
										form.reset();			
										
										var dept = form.findField('department');
										
										//重置部门为当前登录部门
										if(!Ext.isEmpty(stl.currentDept.code)){
											var displayText = stl.currentDept.name
											var valueText = stl.currentDept.code;
											var store = dept.store;
											var  key = dept.displayField + '';
											var value = dept.valueField+ '';
											
											var m = Ext.create(store.model.modelName);
											m.set(key, displayText);
											m.set(value, valueText);
											
											store.loadRecords([m]);
											dept.setValue(valueText);
										}
									}
								}, {
									xtype : 'container',
									border : false,
									columnWidth : .7,
									html : '&nbsp;'
								}, {
									text : consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.common.query'),
									cls : 'yellow_button',
									columnWidth : .1,
									handler : consumer.queryPartnerReceivableBill.queryByDate
								}]
					}]
		}]
	},{
		title : consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.queryByNo'),
		tabConfig : {
			width : 120
		},
		name : 'queryByNumber',
		items : [{
			xtype : 'form',
			defaults : {
				margin : '10 10 5 10',
				labelWidth : 85
			},
			layout : {
				type : 'table',
				columns : 3
			},
			items : [{
						xtype : 'textarea',
						autoScroll : true,
						emptyText : consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.billNosEmptyText'),
						fieldLabel : consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.common.waybillNo'),
						name : 'waybillNos',
						height : 180,
						width : 600,
						allowBlank : false,
						colspan : 3
					}, {
						border : 1,
						xtype : 'container',
						columnWidth : 1,
						defaultType : 'button',
						layout : 'column',
						colspan : 3,
						items : [{
									text : consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.common.reset'),
									columnWidth : .1,
									handler : function() {
										this.up('form').getForm().reset();
									}
								}, {
									xtype : 'container',
									border : false,
									html : '&nbsp;',
									columnWidth : .8
								}, {
									text : consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.common.query'),
									cls : 'yellow_button',
									columnWidth : .1,
									handler : consumer.queryPartnerReceivableBill.queryByNo
								}]
					}]
		}]
	}, {
		title : consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.common.queryBySourceNo'),
		tabConfig : {
			width : 120
		},
		name : 'queryBySourceBillNo',
		items : [{
			xtype : 'form',
			defaults : {
				margin : '10 10 5 10',
				labelWidth : 85
			},
			layout : {
				type : 'table',
				columns : 3
			},
			items : [{
						xtype : 'textarea',
						autoScroll : true,
						emptyText : consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.billNosEmptyText'),
						fieldLabel : consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.common.sourceBillNo'),
						name : 'sourceBillNos',
						height : 180,
						width : 600,
						allowBlank : false,
						colspan : 2
					}, {
						xtype:'container',
						items:[{
							xtype:'component',
							padding:'0 0 0 10',
							autoEl:{
								tag:'div',
								html:'<span style="color:red;">'+consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.sourceBillNosNots')+'</span>'
							}
						}],
						colspan : 1
					},{
						border : 1,
						xtype : 'container',
						columnWidth : 1,
						defaultType : 'button',
						layout : 'column',
						colspan : 2,
						items : [{
									text : consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.common.reset'),
									columnWidth : .1,
									handler : function() {
										this.up('form').getForm().reset();
									}
								}, {
									xtype : 'container',
									border : false,
									html : '&nbsp;',
									columnWidth : .8
								}, {
									text : consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.common.query'),
									cls : 'yellow_button',
									columnWidth : .1,
									handler : consumer.queryPartnerReceivableBill.queryBySourceBillNo
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

/**
 * 应收单列表
 */
Ext.define('Foss.PartnerReceivableBill.ReceivableGrid', {
			extend : 'Ext.grid.Panel',
			title : consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.common.receivableBill'),
			frame : true,
			height : 500,
			bodyCls : 'autoHeight',
			cls : 'autoHeight',
			store : Ext.create('Foss.PartnerReceivableBill.ReceivableGridStore'),
			selModel : Ext.create('Ext.selection.CheckboxModel'),
			defaults : {
				align : 'center',
				margin : '5 0 5 0'
			},
			viewConfig : {
				enableTextSelection : true
				// 设置行可以选择，进而可以复制
			},
			columns : [{
				xtype : 'actioncolumn',
				width : 73,
				text : consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.common.actionColumn'),
				align : 'center',
				items : [{
		    			iconCls : 'deppon_icons_showdetail',
		    			tooltip : consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.QueryInfoGridTooltip'),
		    			handler : function(grid, rowIndex, colIndex){
		    				consumer.queryPartnerReceivableBill.queryDetailByReceivableNo(grid, rowIndex, colIndex)
		    			}
					}]
			}, {
				header : consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.generatingOrgName'),
				dataIndex : 'generatingOrgName'
			}, {
				header : consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.productCode'),
				dataIndex : 'productCode',
				renderer : function(value) {
					return Foss.pkp.ProductData.rendererSubmitToDisplay(value);
				}
			},  {
				header : consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.common.waybillNo'),
				dataIndex : 'waybillNo'
			}, {
				header : consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.common.sourceBillNo'),
				dataIndex : 'sourceBillNo'
			}, {
				header : consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.sourceBillType'),
				dataIndex : 'sourceBillType',
				renderer : function(value) {
					var displayField = FossDataDictionary.rendererSubmitToDisplay(value, 'BILL_RECEIVABLE__SOURCE_BILL_TYPE');
					return displayField;
				}
			}, {
				header : consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.common.receivableNo'),
				dataIndex : 'receivableNo'
			}, {
				header : consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.common.stamp'),
				dataIndex : 'stamp',
				renderer:function(value){
		    		var displayField = FossDataDictionary.rendererSubmitToDisplay(value,settlementDict.FOSS_BOOLEAN);
		    		return displayField;
		    	}
			},  {
				header : consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.common.partnerName'),
				dataIndex : 'customerName'
			}, {
				header : consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.common.partnerCode'),
				dataIndex : 'customerCode'
			}, {
				header : consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.common.totalAmount'),
				dataIndex : 'amount',
				xtype : 'numbercolumn',
				format : '0,0.00',
				align : 'right'
			}, {
				header : consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.common.verifyAmount'),
				dataIndex : 'verifyAmount',
				xtype : 'numbercolumn',
				format : '0,0.00',
				align : 'right'
			}, {
				header : consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.common.unverifyAmount'),
				dataIndex : 'unverifyAmount',
				xtype : 'numbercolumn',
				format : '0,0.00',
				align : 'right'
			}, {
				header : consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.common.businessDate'),
				dataIndex : 'businessDate',
				xtype: 'datecolumn',  
				format:'Y-m-d H:i:s'
			}, {
				header : consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.common.accountDate'),
				dataIndex : 'accountDate',
				xtype: 'datecolumn',  
				format:'Y-m-d H:i:s'
			}, {
				header : consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.transportFee'),
				dataIndex : 'transportFee',
				xtype : 'numbercolumn',
				format : '0,0.00',
				align : 'right'
			}, {
				header : consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.common.paymentType'),
				dataIndex : 'paymentType',
				renderer : function(value) {
					var displayField = FossDataDictionary.rendererSubmitToDisplay(value, 'SETTLEMENT__PAYMENT_TYPE');
					return displayField;
				}
			}, {
				header : consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.common.active'),
				dataIndex : 'active',
				renderer:function(value){
					var displayField = FossDataDictionary.rendererSubmitToDisplay(value,settlementDict.FOSS_ACTIVE);
		    		return displayField;
				}
			}, {
				header : consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.common.isRedBack'),
				dataIndex : 'isRedBack',
				renderer : function(value) {
					var displayField = FossDataDictionary.rendererSubmitToDisplay(value, 'SETTLEMENT__IS_RED_BACK');
					return displayField;
				}
			}, {
				header : consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.common.isInit'),
				dataIndex : 'isInit',
				renderer:function(value){
					if(value == 'Y'){
						return consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.common.yes');
					}else if(value == 'N'){
						return consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.common.no');
					}else{
						return value;
					}
				}
			},{
				header : consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.createType'),
				dataIndex : 'createType',
				renderer : function(value) {
					var displayField = FossDataDictionary.rendererSubmitToDisplay(value, 'SETTLEMENT__CREATE_TYPE');
					return displayField;
				},
				hidden : true
			}, {
				header : consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.receivableOrgCode'),
				dataIndex : 'receivableOrgCode',
				hidden : true
			}, {
				header : consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.receivableOrgName'),
				dataIndex : 'receivableOrgName',
				hidden : true
			}, {
				header : consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.generatingOrgCode'),
				dataIndex : 'generatingOrgCode',
				hidden : true
			}, {
				header : consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.generatingComCode'),
				dataIndex : 'generatingComCode',
				hidden : true
			}, {
				header : consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.generatingComName'),
				dataIndex : 'generatingComName',
				hidden : true
			}, {
				header : consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.dunningOrgCode'),
				dataIndex : 'dunningOrgCode',
				hidden : true
			}, {
				header : consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.dunningOrgName'),
				dataIndex : 'dunningOrgName',
				hidden : true
			}, {
				header : consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.origOrgCode'),
				dataIndex : 'origOrgCode',
				hidden : true
			}, {
				header : consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.origOrgName'),
				dataIndex : 'origOrgName',
				hidden : true
			}, {
				header : consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.destOrgCode'),
				dataIndex : 'destOrgCode',
				hidden : true
			}, {
				header : consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.destOrgName'),
				dataIndex : 'destOrgName',
				hidden : true
			}, {
				header : consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.deliveryCustomerCode'),
				dataIndex : 'deliveryCustomerCode',
				hidden : true
			}, {
				header : consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.deliveryCustomerName'),
				dataIndex : 'deliveryCustomerName',
				hidden : true
			}, {
				header : consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.receiveCustomerCode'),
				dataIndex : 'receiveCustomerCode',
				hidden : true
			}, {
				header : consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.receiveCustomerName'),
				dataIndex : 'receiveCustomerName',
				hidden : true
			}, {
				header : consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.currencyCode'),
				dataIndex : 'currencyCode',
				hidden : true
			}, {
				header : consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.conrevenDate'),
				dataIndex : 'conrevenDate',
				xtype: 'datecolumn', 
				format:'Y-m-d H:i:s'
			},{
				header : consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.billType'),
				dataIndex : 'billType',
				renderer : function(value) {
					var displayField = FossDataDictionary.rendererSubmitToDisplay(value, 'BILL_RECEIVABLE__BILL_TYPE');
					return displayField;
				}
			},{
				header : consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.withholdStatus'),
				dataIndex : 'withholdStatus',
				renderer : function(value) {
					if(value == 'UWH'){
						return consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.unWithhold');
					}else if(value == 'WHS'){
						return consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.withholdSuccess');
					}else if(value == 'WHF'){
						return consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.withholdFailed');
					}else{
						return '';
					};
				}
			}, {
				header : consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.productId'),
				dataIndex : 'productId',
				hidden : true
			}, {
				header : consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.pickupFee'),
				dataIndex : 'pickupFee',
				hidden : true,
				xtype : 'numbercolumn',
				format : '0,0.00',
				align : 'right'
			}, {
				header : consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.deliveryGoodsFee'),
				dataIndex : 'deliveryGoodsFee',
				hidden : true,
				xtype : 'numbercolumn',
				format : '0,0.00',
				align : 'right'
			}, {
				header : consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.packagingFee'),
				dataIndex : 'packagingFee',
				hidden : true,
				xtype : 'numbercolumn',
				format : '0,0.00',
				align : 'right'
			}, {
				header : consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.codFee'),
				dataIndex : 'codFee',
				hidden : true,
				xtype : 'numbercolumn',
				format : '0,0.00',
				align : 'right'
			}, {
				header : consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.insuranceFee'),
				dataIndex : 'insuranceFee',
				hidden : true,
				xtype : 'numbercolumn',
				format : '0,0.00',
				align : 'right'
			}, {
				header : consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.otherFee'),
				dataIndex : 'otherFee',
				hidden : true,
				xtype : 'numbercolumn',
				format : '0,0.00',
				align : 'right'
			}, {
				header : consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.valueAddFee'),
				dataIndex : 'valueAddFee',
				hidden : true,
				xtype : 'numbercolumn',
				format : '0,0.00',
				align : 'right'
			}, {
				header : consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.promotionsFee'),
				dataIndex : 'promotionsFee',
				hidden : true,
				xtype : 'numbercolumn',
				format : '0,0.00',
				align : 'right'
			}, {
				header : consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.goodsName'),
				dataIndex : 'goodsName',
				hidden : true
			}, {
				header : consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.goodsVolumeTotal'),
				dataIndex : 'goodsVolumeTotal',
				hidden : true
			}, {
				header : consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.billWeight'),
				dataIndex : 'billWeight',
				hidden : true
			},{
				header : consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.goodsQtyTotal'),
				dataIndex : 'goodsQtyTotal',
				hidden : true
			}, {
				header : consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.targetOrgCode'),
				dataIndex : 'targetOrgCode',
				hidden : true
			}, {
				header : consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.createUserCode'),
				dataIndex : 'createUserCode',
				hidden : true
			}, {
				header : consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.createUserName'),
				dataIndex : 'createUserName',
				hidden : true
			}, {
				header : consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.createOrgCode'),
				dataIndex : 'createOrgCode',
				hidden : true
			}, {
				header : consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.createOrgName'),
				dataIndex : 'createOrgName',
				hidden : true
			}, {
				header : consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.common.createTime'),
				dataIndex : 'createTime',
				hidden : true,
				xtype: 'datecolumn', 
				format:'Y-m-d H:i:s'
			}, {
				header : consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.common.modifyTime'),
				dataIndex : 'modifyTime',
				hidden : true,
				xtype: 'datecolumn', 
				format:'Y-m-d H:i:s'
			}, {
				header : consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.modifyUserCode'),
				dataIndex : 'modifyUserCode',
				hidden : true
			}, {
				header : consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.modifyUserName'),
				dataIndex : 'modifyUserName',
				hidden : true
			}, {
				header : consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.statementBillNo'),
				dataIndex : 'statementBillNo',
				hidden : true
			}, {
				header : consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.unlockDateTime'),
				dataIndex : 'unlockDateTime',
				hidden : true,
				xtype: 'datecolumn', 
				format:'Y-m-d H:i:s'
			}, {
				header : consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.lockCustomerCode'),
				dataIndex : 'lockCustomerCode',
				hidden : true
			}, {
				header : consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.lockCustomerName'),
				dataIndex : 'lockCustomerName',
				hidden : true
			}, {
				header : consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.collectionType'),
				dataIndex : 'collectionType',
				hidden : true,
				renderer : function(value) {
					var displayField = FossDataDictionary.rendererSubmitToDisplay(value, 'BILL_RECEIVABLE__COLLECTION_TYPE');
					return displayField;
				}
			}, {
				header : consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.collectionName'),
				dataIndex : 'collectionName',
				hidden : true
			}, {
				header : consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.auditUserCode'),
				dataIndex : 'auditUserCode',
				hidden : true
			}, {
				header : consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.auditUserName'),
				dataIndex : 'auditUserName',
				hidden : true
			}, {
				header : consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.approveStatus'),
				dataIndex : 'approveStatus',
				hidden : true,
				renderer : function(value) {
					var displayField = FossDataDictionary.rendererSubmitToDisplay(value, 'NOTE_APPLICATION__APPROVE_STATUS');
					return displayField;
				}
			}, {
				header : consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.auditDate'),
				dataIndex : 'auditDate',
				hidden : true,
				xtype: 'datecolumn', 
				format:'Y-m-d H:i:s'
			}, {
				header : consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.disableUserCode'),
				dataIndex : 'disableUserCode',
				hidden : true
			}, {
				header : consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.disableUserName'),
				dataIndex : 'disableUserName',
				hidden : true
			}, {
				header : consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.common.notes'),
				dataIndex : 'notes',
				hidden : true
			}, {
				header : consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.expressOrigOrgCode'),
				dataIndex : 'expressOrigOrgCode',
				hidden : true
			}, {
				header : consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.expressOrigOrgName'),
				dataIndex : 'expressOrigOrgName',
				hidden : true
			}, {
				header : consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.expressDestOrgCode'),
				dataIndex : 'expressDestOrgCode',
				hidden : true
			}, {
				header : consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.expressDestOrgName'),
				dataIndex : 'expressDestOrgName',
				hidden : true
			},{
				header : consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.invoiceMark'),
				dataIndex : 'invoiceMark',
				hidden : true,
				renderer:function(value){
					var displayField = FossDataDictionary.rendererSubmitToDisplay (value,'SETTLEMENT_INVOICE_MARK');
					return  displayField;
				}
			}],
			initComponent : function() {
				var me = this;
				me.dockedItems = [{
							xtype : 'toolbar',
							dock : 'top',
							layout : 'column',
							defaults : {
								margin : '0 0 5 3'
							},
							items : [{
										xtype : 'container',
										html : '&nbsp;',
										columnWidth : .9
									}, {
										xtype : 'button',
										text : consumer.queryPartnerReceivableBill.i18n('foss.stl.pay.common.export'),
										columnWidth : .09,
										handler : consumer.queryPartnerReceivableBill.exportBillReceivable
									}]
						}, {
							xtype : 'toolbar',
							dock : 'bottom',
							layout : 'column',
							defaults : {
								margin : '0 0 5 3'
							},
							items : [{
										xtype : 'textfield',
										readOnly : true,
										name : 'total',
										columnWidth : .1,
										labelWidth : 40,
										fieldLabel : consumer.queryPartnerReceivableBill.i18n('foss.stl.pay.queryBillPayable.total')
									}, {
										xtype : 'textfield',
										readOnly : true,
										name : 'totalAmount',
										columnWidth : .2,
										labelWidth : 40,
										fieldLabel : consumer.queryPartnerReceivableBill.i18n('foss.stl.pay.queryBillPayable.amount')
									}, {
										xtype : 'textfield',
										readOnly : true,
										name : 'totalVerifyAmount',
										columnWidth : .2,
										labelWidth : 80,
										fieldLabel : consumer.queryPartnerReceivableBill.i18n('foss.stl.pay.queryBillPayable.verifyAmount')
									}, {
										xtype : 'textfield',
										readOnly : true,
										name : 'totalUnVerifyAmount',
										columnWidth : .2,
										labelWidth : 80,
										fieldLabel : consumer.queryPartnerReceivableBill.i18n('foss.stl.pay.queryBillPayable.unverifyAmount')
									},{
										xtype : 'container',
										border : false,
										html : '&nbsp;',
										columnWidth : .3,
										height:24
									},{
										xtype : 'standardpaging',
										store : me.store,
										columnWidth : 1,
										plugins : Ext.create('Deppon.ux.PageSizePlugin', {
													// 设置分页记录最大值，防止输入过大的数值
													maximumSize : 1000,
													sizeList : [['10', 10], ['25', 25], ['50', 50], ['100', 100], ['200', 200],
															['500', 500], ['1000', 1000]]
												})
									}]
						}];
				me.callParent();
			}
		});

/**
 * 应收单明细记录
 */
Ext.define('Foss.PartnerReceivableBill.ReceivableDetailGrid', {
			extend : 'Ext.grid.Panel',
			frame : true,
			title : consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.common.receivableBill'),
			height : 150,
			bodyCls : 'autoHeight',
			cls : 'autoHeight',
			store : Ext.create('Foss.PartnerReceivableBill.ReceivableDetailStore'),
			viewConfig : {
				enableTextSelection : true
				// 设置行可以选择，进而可以复制
			},
			defaults : {
				align : 'center',
				margin : '5 0 5 0'
			},
			columns : [{
				header : consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.common.sourceBillNo'),
				dataIndex : 'sourceBillNo'
			}, {
				header : consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.common.receivableNo'),
				dataIndex : 'receivableNo'
			}, {
				header : consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.common.amount'),
				dataIndex : 'amount',
				xtype : 'numbercolumn',
				format : '0,0.00',
				align : 'right'
			}, {
				header : consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.receivableType'),
				dataIndex : 'receivableType',
				renderer : function(value) {
					var displayField = FossDataDictionary.rendererSubmitToDisplay(value, 'RECEIVABLE_DETAIL__FEE_TYPE');
					return displayField;
				}
			}, {
				header : consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.common.active'),
				dataIndex : 'active',
				renderer:function(value){
					var displayField = FossDataDictionary.rendererSubmitToDisplay(value,settlementDict.FOSS_ACTIVE);
		    		return displayField;
				}
			}, {
				header : consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.createUserCode'),
				dataIndex : 'createUserCode'
			}, {
				header : consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.queryReceivableBill.createUserName'),
				dataIndex : 'createUserName'
			}, {
				header : consumer.queryPartnerReceivableBill.i18n('foss.stl.consumer.common.createTime'),
				dataIndex : 'createTime',
				xtype: 'datecolumn', 
				format:'Y-m-d H:i:s'
			}]
		});

// 初始化界面
Ext.onReady(function() {
			Ext.QuickTips.init();
			Ext.Ajax.timeout = 600000;
			var queryInfoTab = Ext.create('Foss.PartnerReceivableBill.QueryInfoTab');
			var receivableGrid = Ext.create('Foss.PartnerReceivableBill.ReceivableGrid');
			var receivableDetailGrid = Ext.create('Foss.PartnerReceivableBill.ReceivableDetailGrid');

			Ext.create('Ext.panel.Panel', {
						id : 'T_consumer-queryPartnerReceivableBill_content',
						cls : "panelContentNToolbar",
						bodyCls : 'panelContentNToolbar-body',
						layout : 'auto',
						getQueryInfoTab : function() {
							return queryInfoTab;
						},
						getReceivableGrid : function() {
							return receivableGrid;
						},
						getReceivableDetailGrid : function() {
							return receivableDetailGrid;
						},
						items : [queryInfoTab, receivableGrid, receivableDetailGrid],
						renderTo : 'T_consumer-queryPartnerReceivableBill-body',
						listeners : {
							'boxready' : function(th) {
								var roles = stl.currentUserDepts;
								var queryByDateTab = th.getQueryInfoTab().items.items[0].items.items[0];
								var dept = queryByDateTab.getForm().findField("department");
								if (!Ext.isEmpty(stl.currentDept.code)) {
									var displayText = stl.currentDept.name
									var valueText = stl.currentDept.code;
									var store = dept.store;
									var key = dept.displayField + '';
									var value = dept.valueField + '';

									var m = Ext.create(store.model.modelName);
									m.set(key, displayText);
									m.set(value, valueText);

									store.loadRecords([m]);
									dept.setValue(valueText);
								}
							}
						}
					});
		});