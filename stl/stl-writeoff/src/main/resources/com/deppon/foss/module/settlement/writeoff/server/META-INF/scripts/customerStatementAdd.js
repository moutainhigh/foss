Ext.define('Foss.customerStatementAdd.GridModel', {
	extend : 'Ext.data.Model',
	fields : [{
        //id
        name: 'id'
    }, {
        //运单号
        name: 'waybillNo'
    }, {
        //原始来源单据编号
        name: 'origSourceBillNo'
    }, {
        //版本号
        name: 'versionNo',
        type: 'short'
    }, {
        //总金额
        name: 'amount',
        type: 'double'
    }, {
        //已核销金额
        name: 'verifyAmount',
        type: 'double'
    }, {
        //未核销金额
        name: 'unverifyAmount',
        type: 'double'
    }, {
        //付款方式
        name: 'paymentType'
    }, {
        //运输性质
        name: 'productCode'
    }, {
        //提货方式
        name: 'receiveMethod'
    }, {
        //计费重量
        name: 'billWeight',
        type: 'double'
    }, {
        //计费体积
        name: 'billingVolume',
        type: 'double'
    }, {
        //目的站
        name: 'arrvRegionCode'
    }, {
        //提货网点
        name: 'customerPickupOrgName'
    }, {
        //收货人
        name: 'receiveCustomerName'
    }, {
        //收货人编码
        name: 'receiveCustomerCode'
    }, {
        //货物名称、
        name: 'goodsName'
    }, {
        //件数
        name: 'qty',
        type: 'int'
    }, {
        //公布价运费
        name: 'transportFee',
        type: 'double'
    }, {
        //送货费
        name: 'deliverFee',
        type: 'double'
    }, {
        //接货费
        name: 'pickupFee',
        type: 'double'
    }, {
        //保价费
        name: 'insuranceFee',
        type: 'double'
    }, {
        //包装费
        name: 'packagingFee',
        type: 'double'
    }, {
        //代收货款手续费
        name: 'codFee',
        type: 'double'
    }, {
        //增值费用
        name: 'valueAddFee',
        type: 'double'
    }, {
        //其他费用
        name: 'otherFee',
        type: 'double'
    }, {
        //优惠费用
        name: 'promotionsFee',
        type: 'double'
    }, {
        //单据子类型
        name: 'billType'
    }, {
        //来源单号
        name: 'sourceBillNo'
    }, {
        //对账单编号
        name: 'statementBillNo'
    }, {
        //业务日期
        name: 'businessDate',
        type: 'date',
        convert: function (value) {
            if (value != null) {
                var date = new Date(value);
                return date;
            } else {
                return null;
            }
        }
    }, {
        //会计日期
        name: 'accountDate',
        type: 'date',
        convert: function (value) {
            if (value != null) {
                var date = new Date(value);
                return date;
            } else {
                return null;
            }
        }
    }, {
        //始发网点编码
        name: 'origOrgCode'
    }, {
        //始发网点名称
        name: 'origOrgName'
    }, {
        //部门编码
        name: 'orgCode'
    }, {
        //部门名称
        name: 'orgName'
    }, {
        //到达部门名称
        name: 'destOrgName'
    }, {
        //到达部门编码
        name: 'destOrgCode'
    }, {
        //客户编码
        name: 'customerCode'
    }, {
        //客户名称
        name: 'customerName'
    }
        , {
            //发货客户编码
            name: 'deliveryCustomerCode'
        }, {
            //发货客户名称
            name: 'deliveryCustomerName'
        }, {
            //运单签收日期
            name: 'signDate',
            type: 'date',
            convert: function (value) {
                if (value != null) {
                    var date = new Date(value);
                    return date;
                } else {
                    return null;
                }
            }
        }, {
            //审核状态
            name: 'auditStatus'
        }, {
            //备注
            name: 'notes'
        }, {
            //是否删除
            name: 'isDelete',
            defaultValue: 'N'
        }, {
            //删除时间
            name: 'disableTime',
            type: 'date',
            convert: function (value) {
                if (value != null) {
                    var date = new Date(value);
                    return date;
                } else {
                    return null;
                }
            }
        }, {
            //单据父类型
            name: 'billParentType'
        }, {
            //创建时间
            name: 'createTime',
            type: 'date',
            convert: function (value) {
                if (value != null) {
                    var date = new Date(value);
                    return date;
                } else {
                    return null;
                }
            }
        }, {
            //费率
            name: 'unitPrice',
            type: 'double'
        }, {
            //保价
            name: 'insuranceAmount',
            type: 'double'
        }, {
            //发货联系人
            name: 'deliveryCustomerContact'
        }, {
            //统一结算
            name: 'unifiedSettlement'
        }]
});

writeoff.customerStatementAdd.statementFormatBillType = function (value, record) {
    var displayField = value;
    //如果为应收单，则读取应收单的单据子类型
    if (record.get('billParentType') == writeoff.STATEMENTDETAIL_RECEIVABLE) {
        displayField = FossDataDictionary.rendererSubmitToDisplay(value, settlementDict.BILL_RECEIVABLE__BILL_TYPE);
    } else {
        displayField = value;
    }
    return displayField;
}

writeoff.customerStatementAdd.statementSave = function(){
	var	customerStatementAddGrid;
	customerStatementAddGrid = Ext.getCmp('T_writeoff-customerStatementAdd_content').getGrid();
	var me = this;
	Ext.MessageBox.show({
		title: writeoff.customerStatementAdd.i18n('foss.stl.writeoff.common.alert'),
		msg: writeoff.customerStatementAdd.i18n('foss.stl.writeoff.customerStatementAdd.statementSave'),
		buttons: Ext.MessageBox.YESNO,
		fn: function(e){
		  	//如果本期数据为空，则提示不能导出excel
		  	if(customerStatementAddGrid.store.data.length==0){
		  		Ext.Msg.alert(writeoff.customerStatementAdd.i18n('foss.stl.writeoff.common.alert'), 
		  			writeoff.customerStatementAdd.i18n('foss.stl.writeoff.statementCommon.noDataToExport'));
				return false;
		  	}
		  	if(e=='yes'){
				//判断是按那种查询进行
				var searchParams;
				if(writeoff.customerStatementAdd.queryTabType==writeoff.STATEMENTQUERYTAB_BYCUSTOMER){
					//应收应付单号集合
					var numbers = [];
					//添加对账单明细列表
					var data = customerStatementAddGrid.store.data;
					//添加对账单明细条数
					var length = data.items.length;
					//循环编译单号集合
					for(var i=0;i<length;i++){
						numbers.push(data.items[i].get('waybillNo'));
					}
					if(Ext.isEmpty(writeoff.customerStatementAdd.customerCode)){
						Ext.Msg.alert(writeoff.customerStatementAdd.i18n('foss.stl.writeoff.common.alert'),
							writeoff.customerStatementAdd.i18n('foss.stl.writeoff.common.validateFailAlertByNoCustomer'));
					 	return false;
					}
					searchParams = {
							//封装应收单的来源单号，用于判断运单是否存在未受理的更改单
							'customerStatementVo.customerStatementDto.numbers':numbers,
							'customerStatementVo.customerStatementDto.periodBeginDate':writeoff.customerStatementAdd.periodBeginDate,	
							'customerStatementVo.customerStatementDto.periodEndDate':writeoff.customerStatementAdd.periodEndDate,	
							'customerStatementVo.customerStatementDto.customerCode':writeoff.customerStatementAdd.customerCode,
							'customerStatementVo.customerStatementDto.queryTabType':writeoff.customerStatementAdd.queryTabType,
							'customerStatementVo.customerStatementDto.receivableUnified':writeoff.customerStatementAdd.receivableUnified
						};
				}else{
					var numbers = stl.splitToArray(writeoff.customerStatementAdd.numbers);
					searchParams = {
						'customerStatementVo.customerStatementDto.numbers':numbers,
						'customerStatementVo.customerStatementDto.queryTabType':writeoff.customerStatementAdd.queryTabType,
						'customerStatementVo.customerStatementDto.receivableUnified':writeoff.customerStatementAdd.receivableUnified
					};
				}
				//拼接vo，注入到后台
				Ext.Ajax.request({
				    url: writeoff.realPath('customerStatementSave.action'),
				    form: Ext.fly('downloadAttachFileForm'),
				    method : 'POST',
				    params : searchParams,
				    isUpload : true,
				    success : function(response,options){
				    	var result = Ext.decode(response.responseText);
				    	var statementBillNo = result.customerStatementVo.customerStatementDto.statementBillNo;
				    	Ext.Msg.alert(writeoff.customerStatementAdd.i18n('foss.stl.writeoff.common.alert'),
							writeoff.customerStatementAdd.i18n('foss.stl.writeoff.customerStatementAdd.statementSaveSuccess')
							+ statementBillNo);
				    	//清空列表
				    	customerStatementAddGrid.store.removeAll();
				    },
					exception:function(response){
						var result = Ext.decode(response.responseText);	
						Ext.Msg.alert(writeoff.customerStatementAdd.i18n('foss.stl.writeoff.common.alert'),result.message);
					}
				});
		  	}else{
		  		return false;
		  	}
		}
	});
}

writeoff.customerStatementAdd.statementSelectByCust = function(){
	var form = this.up('form').getForm();
	//判断是否合法
	if(form.isValid()){
		writeoff.customerStatementAdd.periodBeginDate = form.findField('periodBeginDate').getValue();
		writeoff.customerStatementAdd.periodEndDate = form.findField('periodEndDate').getValue();
		writeoff.customerStatementAdd.customerCode = form.findField('customerCode').getValue();
		writeoff.customerStatementAdd.queryTabType = writeoff.STATEMENTQUERYTAB_BYCUSTOMER;
		writeoff.customerStatementAdd.receivableUnified = form.getValues().receivableUnified;
		var grid = Ext.getCmp('T_writeoff-customerStatementAdd_content').getGrid();
		if(Ext.isEmpty(writeoff.customerStatementAdd.customerCode)){
			Ext.Msg.alert(writeoff.customerStatementAdd.i18n('foss.stl.writeoff.common.alert'),
				writeoff.customerStatementAdd.i18n('foss.stl.writeoff.statementAdd.customerIsNullWarning'));
		 	return false;
		}
		//开始日期
		var periodBeginDate = writeoff.customerStatementAdd.periodBeginDate;
		//结束日期
		var periodEndDate = writeoff.customerStatementAdd.periodEndDate;
		//校验日期
		if(Ext.isEmpty(periodBeginDate)||Ext.isEmpty(periodEndDate)){
			Ext.Msg.alert(writeoff.customerStatementAdd.i18n('foss.stl.writeoff.common.alert'),
				writeoff.customerStatementAdd.i18n('foss.stl.writeoff.statementAdd.quryDateIsNullWarning'));
			return false;
		}
		//比较起始日期和结束日期
		var compareTwoDate = stl.compareTwoDate(periodBeginDate,periodEndDate);
		if(compareTwoDate>stl.DATELIMITDAYS_MONTH){
			Ext.Msg.alert(writeoff.customerStatementAdd.i18n('foss.stl.writeoff.common.alert'),
				writeoff.customerStatementAdd.i18n('foss.stl.writeoff.statementEdit.queryDateMaxLimit'));
			return false;
		}else if(compareTwoDate<1){
			Ext.Msg.alert(writeoff.customerStatementAdd.i18n('foss.stl.writeoff.common.alert'),
				writeoff.customerStatementAdd.i18n('foss.stl.writeoff.common.dateEndBeforeStatrtWarining'));
			return false;
		}
		//获取grid
		grid.store.loadPage(1,{
			callback: function(records, operation, success) {
				var rawData = grid.store.proxy.reader.rawData;
				if(!success){
					var result = Ext.decode(response.responseText);	
					Ext.Msg.alert(writeoff.customerStatementAdd.i18n('foss.stl.writeoff.common.alert'),result.message);
					return false;
				}
				//如果成功显示
				if(success){
					//对结果进行转化
					var result = Ext.decode(operation.response.responseText);  
					//判断查询结果
					if(Ext.isEmpty(result.customerStatementVo.customerStatementDto.customerStatementDList) 
							||result.customerStatementVo.customerStatementDto.customerStatementDList.length==0){
						Ext.Msg.alert(writeoff.customerStatementAdd.i18n('foss.stl.writeoff.common.alert'),
							writeoff.customerStatementAdd.i18n('foss.stl.writeoff.common.noResult'));
						return false;
					}
				}
		    }
		});
		
	}else{
		Ext.Msg.alert(writeoff.customerStatementAdd.i18n('foss.stl.writeoff.common.alert'),
			writeoff.customerStatementAdd.i18n('foss.stl.writeoff.common.validateFailAlert'));
		return false;
	}
}

//根据来源单号查询
writeoff.customerStatementAdd.statementSelectByNumber = function(){		
	var form  = this.up('form').getForm();	
	//输入单号集合
	var numbers = form.findField('numbers').getValue();
	//判断传入单号是否为null或''
	if(Ext.String.trim(numbers)!=null && Ext.String.trim(numbers)!=''){
		var billNumberArray = stl.splitToArray(numbers);
		 for(var i=0;i<billNumberArray.length;i++){
		 	//循环将空格等剔除掉
		 	if(Ext.String.trim(billNumberArray[i])==''){
		 		billNumberArray.pop(billNumberArray[i]);
		 	}
		 }
		 //判断输入单号是否超过10个
		 if(billNumberArray.length>10){
		 	Ext.Msg.alert(writeoff.customerStatementAdd.i18n('foss.stl.writeoff.common.alert'),
		 		writeoff.customerStatementAdd.i18n('foss.stl.writeoff.common.queryNosLimit'));
		 	return false;
		 }
	}else{
		Ext.Msg.alert(writeoff.customerStatementAdd.i18n('foss.stl.writeoff.common.alert'),
			writeoff.customerStatementAdd.i18n('foss.stl.writeoff.common.billNosIsNullWarning'));
		return false;
	}
	//当界面校验都通过后，才执行查询方法
	if(form.isValid()){
		writeoff.customerStatementAdd.queryTabType = writeoff.STATEMENTQUERYTAB_BYSTATEMENTNUMBER;
		writeoff.customerStatementAdd.numbers = numbers;
		writeoff.customerStatementAdd.receivableUnified = form.getValues().receivableUnified;
		var grid = Ext.getCmp('T_writeoff-customerStatementAdd_content').getGrid();
		//获取grid
		grid.store.loadPage(1,{
			callback: function(records, operation, success) {
				var rawData = grid.store.proxy.reader.rawData;
				if(!success && ! rawData.isException){
					var result = Ext.decode(operation.response.responseText);	
					Ext.Msg.alert(writeoff.customerStatementAdd.i18n('foss.stl.writeoff.common.alert'),result.message);
				}
				if(success){
					//对结果进行转化
					var result = Ext.decode(operation.response.responseText);  
					//判断查询结果
					if(Ext.isEmpty(result.customerStatementVo.customerStatementDto.customerStatementDList) 
							||result.customerStatementVo.customerStatementDto.customerStatementDList.length==0){
						Ext.Msg.alert(writeoff.customerStatementAdd.i18n('foss.stl.writeoff.common.alert'),
							writeoff.customerStatementAdd.i18n('foss.stl.writeoff.common.noResult'));
						return false;
					}
				}
		    }
		});
	}else{
		Ext.Msg.alert(writeoff.customerStatementAdd.i18n('foss.stl.writeoff.common.alert'),
				writeoff.customerStatementAdd.i18n('foss.stl.writeoff.common.validateFailAlert'));
			return false;
	}	
};

Ext.define('Foss.customerStatementAdd.GridStore',{
	extend:'Ext.data.Store',
	model:'Foss.customerStatementAdd.GridModel',
	pageSize:50,
	proxy:{
		type:'ajax',
		url:writeoff.realPath('queryCustomerStatementD.action'),
		actionMethods:'POST',
		reader:{
			type:'json',
			root:'customerStatementVo.customerStatementDto.customerStatementDList',
			totalProperty:'totalCount'
		}
	},
	listeners:{
		'beforeLoad':function(store, operation, eOpts){
			var searchParams;
			if(writeoff.customerStatementAdd.queryTabType==writeoff.STATEMENTQUERYTAB_BYCUSTOMER) {
			    searchParams = {
					'customerStatementVo.customerStatementDto.periodBeginDate':writeoff.customerStatementAdd.periodBeginDate,	
					'customerStatementVo.customerStatementDto.periodEndDate':writeoff.customerStatementAdd.periodEndDate,	
					'customerStatementVo.customerStatementDto.customerCode':writeoff.customerStatementAdd.customerCode,
					'customerStatementVo.customerStatementDto.queryTabType':writeoff.customerStatementAdd.queryTabType,
					'customerStatementVo.customerStatementDto.receivableUnified':writeoff.customerStatementAdd.receivableUnified
				};
			} else {
				var numbers = stl.splitToArray(writeoff.customerStatementAdd.numbers);
				searchParams = {
					'customerStatementVo.customerStatementDto.numbers':numbers,
					'customerStatementVo.customerStatementDto.queryTabType':writeoff.customerStatementAdd.queryTabType,
					'customerStatementVo.customerStatementDto.receivableUnified':writeoff.customerStatementAdd.receivableUnified
				};
			}
			//设置查询条件
			Ext.apply(operation,{
				params :searchParams
			}); 
		}
	}
});

Ext.define('Foss.customerStatementAdd.Grid',{
	extend:'Ext.grid.Panel',
	title: writeoff.customerStatementAdd.i18n('foss.stl.writeoff.customerStatementAdd.Message'),
    bodyCls: 'autoHeight',
	cls: 'autoHeight',
	emptyText:writeoff.customerStatementAdd.i18n('foss.stl.writeoff.common.noResult'),
	frame:true,
	detailWin:null,
    store:Ext.create('Foss.customerStatementAdd.GridStore'),
    //selModel:Ext.create('Ext.selection.CheckboxModel'),
    height:500,
  	viewConfig:{
  		enableTextSelection : true//设置行可以选择，进而可以复制
  	},
    defaults:{
  		align:'center'
  	},
  	columns: [{
		header:writeoff.customerStatementAdd.i18n('foss.stl.writeoff.common.businessDate'),
		dataIndex:'businessDate',
		renderer:function(value){
			if(value!=null){
				return Ext.Date.format(new Date(value), 'Y-m-d');
			}else{
				return null;
			}
		} 
	},{
		header: writeoff.customerStatementAdd.i18n('foss.stl.writeoff.common.waybillNo'),
		dataIndex: 'waybillNo'
	},{
		header:writeoff.customerStatementAdd.i18n('foss.stl.writeoff.statementAdd.arrvRegionCode'),
		dataIndex:'arrvRegionCode'
	},{
		header: writeoff.customerStatementAdd.i18n('foss.stl.writeoff.statementAdd.productCode'),
		dataIndex:'productCode',
		renderer:function(value){
			return Foss.pkp.ProductData.rendererSubmitToDisplay(value);
		}
	},{
		header:writeoff.customerStatementAdd.i18n('foss.stl.writeoff.statementAdd.deliveryCustomerName'),
		dataIndex:'deliveryCustomerName'
	},{
		header:writeoff.customerStatementAdd.i18n('foss.stl.writeoff.statementAdd.unitPrice'),
		dataIndex:'unitPrice'
	},{
		header:writeoff.customerStatementAdd.i18n('foss.stl.writeoff.statementAdd.insuranceAmount'),
		dataIndex:'insuranceAmount'
	},{
		header:writeoff.customerStatementAdd.i18n('foss.stl.writeoff.statementAdd.qty'),
		dataIndex:'qty'
	},{
		header:writeoff.customerStatementAdd.i18n('foss.stl.writeoff.statementAdd.billWeight'),
		dataIndex:'billWeight'
	},{
		header:writeoff.customerStatementAdd.i18n('foss.stl.writeoff.statementAdd.billingVolume'),
		dataIndex:'billingVolume'
	},{
		header:writeoff.customerStatementAdd.i18n('foss.stl.writeoff.statementAdd.insuranceFee'),
		dataIndex:'insuranceFee'
	},{
		header:writeoff.customerStatementAdd.i18n('foss.stl.writeoff.statementAdd.deliverFee'),
		dataIndex:'deliverFee'
	},{
		header:writeoff.customerStatementAdd.i18n('foss.stl.writeoff.statementAdd.packagingFee'),
		dataIndex:'packagingFee'	
	},{
		header:writeoff.customerStatementAdd.i18n('foss.stl.writeoff.statementAdd.otherFee'),
		dataIndex:'otherFee'
	},{
		header:writeoff.customerStatementAdd.i18n('foss.stl.writeoff.statementAdd.receiveMethod'),
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
		header: writeoff.customerStatementAdd.i18n('foss.stl.writeoff.statementAdd.paymentType'),
		dataIndex:'paymentType',
		renderer:function(value){
			var displayField = FossDataDictionary. rendererSubmitToDisplay (value,settlementDict.SETTLEMENT__PAYMENT_TYPE);
			return displayField;
		}
	},{
		header: writeoff.customerStatementAdd.i18n('foss.stl.writeoff.common.originalUnverifyAmount'),
		dataIndex:'unverifyAmount',
		renderer:function(v,m,record){
			if(record.get('billParentType')==writeoff.STATEMENTDETAIL_PAYABLE||record.get('billParentType')==writeoff.STATEMENTDETAIL_DEPOSIT_RECEIVED){
					return '<span style="color:red">'+v+'</span>';  
			}else{
				return v;	
			}	
		}
	},{
		header: writeoff.customerStatementAdd.i18n('foss.stl.writeoff.common.totalAmount'),
		dataIndex: 'amount',
		renderer:function(v,m,record){
			if(record.get('billParentType')==writeoff.STATEMENTDETAIL_PAYABLE||record.get('billParentType')==writeoff.STATEMENTDETAIL_DEPOSIT_RECEIVED){
					return '<span style="color:red">'+v+'</span>';  
			}else{
				return v;	
			}	
		}
	},{
		header:writeoff.customerStatementAdd.i18n('foss.stl.writeoff.statementAdd.number'),
		dataIndex:'sourceBillNo'
	},{
		header:writeoff.customerStatementAdd.i18n('foss.stl.writeoff.statementAdd.deliveryCustomerContact'),
		dataIndex:'deliveryCustomerContact'
	},{
		header: writeoff.customerStatementAdd.i18n('foss.stl.writeoff.common.origSourceBillNo'),
		dataIndex: 'origSourceBillNo'
	},{
		header:writeoff.customerStatementAdd.i18n('foss.stl.writeoff.statementAdd.billParentType'),
		dataIndex:'billParentType',
		renderer:function(value){
			var displayField = FossDataDictionary. rendererSubmitToDisplay (value,settlementDict.STATEMENT_OF_ACCOUNT_D__BILL_PARENT_TYPE);
    		return displayField;
		}
	},{
		header:writeoff.customerStatementAdd.i18n('foss.stl.writeoff.common.billType'),
		dataIndex:'billType',
		renderer:function(v,m,record){
			return writeoff.customerStatementAdd.statementFormatBillType(v,record);
		}
	},{
		header:writeoff.customerStatementAdd.i18n('foss.stl.writeoff.common.customerCode'),
		dataIndex:'customerCode'
	},{
		header:writeoff.customerStatementAdd.i18n('foss.stl.writeoff.common.customerName'),
		dataIndex:'customerName'
	},{
		header: writeoff.customerStatementAdd.i18n('foss.stl.writeoff.common.verifyAmount'),
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
		header:writeoff.customerStatementAdd.i18n('foss.stl.writeoff.common.orgCode'),
		dataIndex:'orgCode'
	},{
		header:writeoff.customerStatementAdd.i18n('foss.stl.writeoff.common.orgName'),
		dataIndex:'orgName'
	},{
		header:writeoff.customerStatementAdd.i18n('foss.stl.writeoff.common.accountDate'),
		dataIndex:'accountDate',
		renderer:function(value){
			if(value!=null){
				return Ext.Date.format(new Date(value), 'Y-m-d');
			}else{
				return null;
			}
		} 
	},{
		header:writeoff.customerStatementAdd.i18n('foss.stl.writeoff.statementAdd.customerPickupOrgName'),
		dataIndex:'customerPickupOrgName'
	},{
		header:writeoff.customerStatementAdd.i18n('foss.stl.writeoff.statementAdd.receiveCustomerName'),
		dataIndex:'receiveCustomerName'
	},{
		header:writeoff.customerStatementAdd.i18n('foss.stl.writeoff.statementAdd.receiveCustomerCode'),
		dataIndex:'receiveCustomerCode'
	},{
		header:writeoff.customerStatementAdd.i18n('foss.stl.writeoff.statementAdd.goodsName'),
		dataIndex:'goodsName'
	},{
		header:writeoff.customerStatementAdd.i18n('foss.stl.writeoff.statementAdd.transportFee'),
		dataIndex:'transportFee'
	},{
		header:writeoff.customerStatementAdd.i18n('foss.stl.writeoff.statementAdd.pickupFee'),
		dataIndex:'pickupFee'
	},{
		header:writeoff.customerStatementAdd.i18n('foss.stl.writeoff.statementAdd.codFee'),
		dataIndex:'codFee'
	},{
		header:writeoff.customerStatementAdd.i18n('foss.stl.writeoff.statementAdd.valueAddFee'),
		dataIndex:'valueAddFee'
	},{
		header:writeoff.customerStatementAdd.i18n('foss.stl.writeoff.statementAdd.promotionsFee'),
		dataIndex:'promotionsFee'
	},{
		header:writeoff.customerStatementAdd.i18n('foss.stl.writeoff.statementAdd.origOrgCode'),
		dataIndex:'origOrgCode'
	},{
		header:writeoff.customerStatementAdd.i18n('foss.stl.writeoff.statementAdd.origOrgName'),
		dataIndex:'origOrgName'
	},{
		header:writeoff.customerStatementAdd.i18n('foss.stl.writeoff.statementAdd.destOrgName'),
		dataIndex:'destOrgName'
	},{
		header:writeoff.customerStatementAdd.i18n('foss.stl.writeoff.statementAdd.destOrgCode'),
		dataIndex:'destOrgCode'
	}
//	,{
//		header:writeoff.customerStatementAdd.i18n('foss.stl.writeoff.statementAdd.deptRegionCode'),
//		dataIndex:'deptRegionCode'
//	}
	,
	{
		header:writeoff.customerStatementAdd.i18n('foss.stl.writeoff.statementAdd.deliveryCustomerCode'),
		dataIndex:'deliveryCustomerCode'
	},{
		header:writeoff.customerStatementAdd.i18n('foss.stl.writeoff.statementAdd.signDate'),
		dataIndex:'signDate',
		renderer:function(value){
			if(value!=null){
				return Ext.Date.format(new Date(value), 'Y-m-d');
			}else{
				return null;
			}
		} 
	},{
		header:writeoff.customerStatementAdd.i18n('foss.stl.writeoff.statementAdd.auditStatus'),
		dataIndex:'auditStatus',
		renderer:function(value){
			var displayField = FossDataDictionary. rendererSubmitToDisplay (value,settlementDict.BILL_RECEIVABLE__APPROVE_STATUS);
			return displayField;
		}
	},{
		header: writeoff.customerStatementAdd.i18n('foss.stl.writeoff.statementAdd.unifiedSettlement'),
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
		header:writeoff.customerStatementAdd.i18n('foss.stl.writeoff.common.notes'),
		dataIndex:'notes',
		hidden:true
	},{
		header:writeoff.customerStatementAdd.i18n('foss.stl.writeoff.statementAdd.isDelete'),
		dataIndex:'isDelete',
		hidden:true
	},{
		header:writeoff.customerStatementAdd.i18n('foss.stl.writeoff.statementAdd.statementBillNo'),
		dataIndex:'statementBillNo',
		hidden:true
	},{
		header:writeoff.customerStatementAdd.i18n('foss.stl.writeoff.common.disableTime'),
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
		header:writeoff.customerStatementAdd.i18n('foss.stl.writeoff.common.createTime'),
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
	}],
	initComponent:function(){
		var me = this;
		me.dockedItems = [{
	   		xtype: 'toolbar',
		    dock: 'bottom',
		    layout:'column',
			defaultType:'textfield',
		    defaults:{
				margin:'5 0 5 3',
				readOnly:true,
				labelWidth:60
			},		
		    items: [{
				xtype:'standardpaging',
				store:me.store,
				columnWidth:.9,
				pageSize:50,
				plugins: Ext.create('Deppon.ux.PageSizePlugin', {
					//设置分页记录最大值，防止输入过大的数值
					maximumSize: 1000,
					sizeList: [['10',10],['25',25],['50',50],['100',100],['200', 200],['500', 500],['1000', 1000]]
				})
			 },{
				xtype:'button',
				text:writeoff.customerStatementAdd.i18n('foss.stl.writeoff.statementAdd.createStatement'),
				columnWidth:.1,
				hidden:!writeoff.customerStatementAdd.isPermission('/stl-web/writeoff/customerStatementSave.action'),
				handler:writeoff.customerStatementAdd.statementSave
			}]
		}];
		me.callParent();
	}
});

Ext.define('Foss.customerStatementAdd.QueryTab',{
	extend:'Ext.tab.Panel',
	frame:false,
	bodyCls: 'autoHeight',
	cls: 'innerTabPanel',
	height:250,
	items:[{
       	title: writeoff.customerStatementAdd.i18n('foss.stl.writeoff.common.woodenStatementByCustomer'),
       	tabConfig: {
			width: 120
		},
        layout:'hbox',
	    items:[{
        	xtype:'form',
        	width:920,
        	layout:'column',
        	defaults:{
	        	margin:'10 10 0 10',
	        	labelWidth:80
       		 },
		    items:[{
		    	xtype:'datefield',
		    	fieldLabel:writeoff.customerStatementAdd.i18n('foss.stl.writeoff.statementAdd.periodBeginDate'),
		    	allowBlank:false,
		    	name:'periodBeginDate',
		    	columnWidth: .3,
		    	format:'Y-m-d',
		    	value:stl.getTargetDate(new Date(),-1)
		    },{
		    	xtype:'datefield',
		    	fieldLabel:writeoff.customerStatementAdd.i18n('foss.stl.writeoff.statementAdd.periodEndDate'),
		    	labelWidth:80,
		    	name:'periodEndDate',
		    	format:'Y-m-d',
		    	columnWidth: .3,
		    	value:stl.getTargetDate(new Date(),+1)
		    },{
	        	xtype:'commoncustomerselector',
	        	listWidth:300,
	        	all:'true',//查询所有(包含作废)
	        	name:'customerCode',
	        	emptyText:'客户编码和手机均可查询',
	        	contcatFlag :'Y',//增加按手机号查询
	        	singlePeopleFlag:'Y',
	        	fieldLabel:'<span style="color:red;">*</span>'+writeoff.customerStatementAdd.i18n('foss.stl.writeoff.common.customerDetial'),
	        	columnWidth:.3
	        },{//ddw
	       	 	xtype: 'fieldcontainer',
	            fieldLabel: writeoff.customerStatementAdd.i18n('foss.stl.writeoff.statementAdd.receivableUnified'),
	            columnWidth:.3,
	            labelWidth:85,
	            defaultType: 'radiofield',
	            layout:'column',
	            items: [{
	                boxLabel: writeoff.customerStatementAdd.i18n('foss.stl.writeoff.common.yes'),
	                name: 'receivableUnified',	                
	                inputValue: writeoff.RECEIVABLEUNIFORM_Y
	            },{
	                boxLabel: writeoff.customerStatementAdd.i18n('foss.stl.writeoff.common.no'),
	                name: 'receivableUnified',
	                checked:true,
	                inputValue: writeoff.RECEIVABLEUNIFORM_N
	            }]
       	 	},{
				border: 1,
				xtype:'container',
				columnWidth:1,
				defaultType:'button',
				layout:'column',
				items:[{
					text:writeoff.customerStatementAdd.i18n('foss.stl.writeoff.common.reset'),
					columnWidth:.08,
					handler:function(){
						this.up('form').getForm().reset();
					}
				},{
					xtype: 'container',
					border : false,
					columnWidth:.74,
					html: '&nbsp;'
				},{
					text:writeoff.customerStatementAdd.i18n('foss.stl.writeoff.common.query'),
					cls:'yellow_button',
					columnWidth:.08,
					handler:writeoff.customerStatementAdd.statementSelectByCust
				}]
		    }]
	    }]
	},{
        title: writeoff.customerStatementAdd.i18n('foss.stl.writeoff.common.queryByNo'),
        tabConfig:{
   			width:120
        },
        layout:'fit',
        items:[{
        	xtype:'form',
        	layout:'column',
        	defaults:{
        		margin:'5 5 5 5'
       		},
		    items:[{       		
				xtype:'textareafield',
				fieldLabel:'<span style="color:red;">*</span>'+writeoff.customerStatementAdd.i18n('foss.stl.writeoff.statementAdd.number'),
				emptyText:writeoff.customerStatementAdd.i18n('foss.stl.writeoff.statementAdd.billNosQueryLimit'),
				columnWidth:.65,
				regex:/^((YS)?[0-9]{7,10}[;,])*((YS)?[0-9]{7,10}[;,]?)$/i,
				labelWidth:70,
				labelAlign:'right',
				name:'numbers',
				autoScroll:true,
				height:104
		    },{
				xtype:'container',
				columnWidth:.35,
				layout:'vbox',
				items:[{
					xtype:'component',
					padding:'0 0 0 10',
					autoEl:{
						tag:'div',
						html:'<span style="color:red;">'+writeoff.customerStatementAdd.i18n('foss.stl.writeoff.customerStatementAdd.billNosDesc')+'</span>'
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
					text:writeoff.customerStatementAdd.i18n('foss.stl.writeoff.common.reset'),
					columnWidth:.075,
					handler:function(){
						this.up('form').getForm().reset();
					}
				},{
					xtype:'container',
					border:false,
					html:'&nbsp;',
					columnWidth:.5
				},{//ddw 是否统一结算
		       	 	xtype: 'fieldcontainer',
		            fieldLabel: writeoff.customerStatementAdd.i18n('foss.stl.writeoff.statementAdd.receivableUnified'),
		            columnWidth:.2,
		            labelWidth:90,
		            defaultType: 'radiofield',
		            layout:'column',
		            items: [{
		                boxLabel: writeoff.customerStatementAdd.i18n('foss.stl.writeoff.common.yes'),
		                name: 'receivableUnified',
		                inputValue: writeoff.RECEIVABLEUNIFORM_Y
		            },{
		                boxLabel: writeoff.customerStatementAdd.i18n('foss.stl.writeoff.common.no'),
		                name: 'receivableUnified',
		                checked:true,
		                inputValue: writeoff.RECEIVABLEUNIFORM_N
		            }]
	       	 	},{
					text:writeoff.customerStatementAdd.i18n('foss.stl.writeoff.common.query'),
					cls:'yellow_button',
					columnWidth:.075,
					handler:writeoff.customerStatementAdd.statementSelectByNumber
				}]
        	}]
        }]
    }]
});

Ext.onReady(function() {
	Ext.Ajax.timeout = 60000*30;
	Ext.QuickTips.init();
	//查询表单
	var queryTab = Ext.create('Foss.customerStatementAdd.QueryTab');
	//查询结果列表
	var grid = Ext.create('Foss.customerStatementAdd.Grid');

	//创建panel
	Ext.create('Ext.panel.Panel',{
		id: 'T_writeoff-customerStatementAdd_content',
		cls: "panelContentNToolbar",
		bodyCls: 'panelContentNToolbar-body',
		layout: 'auto',
		getGrid:function(){
			return grid;
		},
		getTab:function(){
			return queryTab;
		},
		items: [queryTab,grid],
		renderTo: 'T_writeoff-customerStatementAdd-body'
	});
});