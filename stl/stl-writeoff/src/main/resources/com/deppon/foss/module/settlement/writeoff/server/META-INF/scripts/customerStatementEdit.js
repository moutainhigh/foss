/**
 * 对账单明细MODEL
 */
Ext.define('Foss.customerStatementEdit.CustomerStatementDModel', {
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

/**
 * 对账单明细导出
 */
writeoff.customerStatementEdit.exportExcel = function(){
	var	columns,
	arrayColumns,
	arrayColumnNames;
	//对账单明细
	var statementEntryWindow = writeoff.customerStatementEdit.statementEntryWindow;
	//对账单明细列表
	var statementEntryGrid = statementEntryWindow.items.items[2];
	//如果本期数据为空，则提示不能导出excel
  	if(statementEntryGrid.store.data.length==0){
  		Ext.Msg.alert(writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.alert'), 
  			writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementCommon.noData'));
		return false;
  	}
	var me = this;
	Ext.MessageBox.show({
		title: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.alert'),
		msg: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.customerStatementAdd.export'),
		buttons: Ext.MessageBox.YESNO,
		fn: function(e){
		  	if(e=='yes'){
		  		//转化列头和列明
				columns = statementEntryGrid.columns;
				arrayColumns = [];
				arrayColumnNames = [];
				//将前台对应列头传入到后台去
				for(var i=1;i<columns.length;i++){
					if(columns[i].isHidden()==false){
						var hederName = columns[i].text;
						var dataIndex = columns[i].dataIndex;
						arrayColumns.push(dataIndex);
						arrayColumnNames.push(hederName);
					}
				}
		
				if(!Ext.fly('downloadAttachFileForm')){
				    var frm = document.createElement('form');
				    frm.id = 'downloadAttachFileForm';
				    frm.style.display = 'none';
				    document.body.appendChild(frm);
				}
				//拼接vo，注入到后台
				searchParams = {
					'customerStatementVo.customerStatementDto.statementBillNo':writeoff.customerStatementEdit.statementBillNo,
				    'customerStatementVo.arrayColumns':arrayColumns,
				    'customerStatementVo.arrayColumnNames':arrayColumnNames
				};
				Ext.Ajax.request({
				    url: writeoff.realPath('customerExportXLS.action'),
				    form: Ext.fly('downloadAttachFileForm'),
				    method : 'POST',
				    params : searchParams,
				    isUpload: true,
				    success : function(response,options){
				    	Ext.Msg.alert(writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.alert'),
							writeoff.customerStatementEdit.i18n('foss.stl.writeoff.customerStatementAdd.exportSuccess'));
				    }
				});
		  	}else{
		  		return false;
		  	}
		}
	});
}

/**
 * 格式化分录的单据子类型
 */
writeoff.customerStatementEdit.statementFormatBillType = function (value, record) {
    var displayField = value;
    //如果为应收单，则读取应收单的单据子类型
    if (record.get('billParentType') == writeoff.STATEMENTDETAIL_RECEIVABLE) {
        displayField = FossDataDictionary.rendererSubmitToDisplay(value, settlementDict.BILL_RECEIVABLE__BILL_TYPE);
    } else if (record.get('billParentType') == writeoff.STATEMENTDETAIL_PAYABLE) {
        displayField = FossDataDictionary.rendererSubmitToDisplay(value, settlementDict.BILL_PAYABLE__BILL_TYPE);
    } else if (record.get('billParentType') == writeoff.STATEMENTDETAIL_DEPOSIT_RECEIVED) {
        displayField = FossDataDictionary.rendererSubmitToDisplay(value, settlementDict.BILL_DEPOSIT_RECEIVED__BILL_TYPE);
    } else if (record.get('billParentType') == writeoff.STATEMENTDETAIL_ADVANCED_PAYMENT) {
        displayField = FossDataDictionary.rendererSubmitToDisplay(value, settlementDict.BILL_ADVANCED_PAYMENT__BILL_TYPE);
    } else {
        displayField = value;
    }
    return displayField;
}

/**
 * 对账单按钮设置
 */
writeoff.customerStatementEdit.operateButton = function(){
	//对账单明细
	var statementEntryWindow = writeoff.customerStatementEdit.statementEntryWindow;
	//对账单基础信息
	var baseForm = statementEntryWindow.items.items[0].getForm();
	//对账单操作按钮
	var operateFrom = statementEntryWindow.items.items[1];
	//对账单确认状态
	var confirmStatus = baseForm.findField('confirmStatus').getValue();
	//对账单单据类型
	var billType = baseForm.findField('billType').getValue();
	//确认状态判断
	if(confirmStatus==writeoff.STATEMENTCONFIRMSTATUS_N){
		//确认按钮
		operateFrom.items.items[1].show();
		//反确认按钮
		operateFrom.items.items[2].hide();
	}else{
		//确认按钮
		operateFrom.items.items[1].hide();
		//反确认按钮
		operateFrom.items.items[2].show();
	}
	//还款按钮
	operateFrom.items.items[3].show();
	//确认反确认权限
	if(!writeoff.customerStatementEdit.isPermission('/stl-web/writeoff/confirmOrUnConfirmStatement.action')){
		//确认按钮
		operateFrom.items.items[1].hide();
		//反确认按钮
		operateFrom.items.items[2].hide();
	}
	//还款权限
	if(writeoff.customerStatementEdit.isPermission('/stl-web/writeoff/customerToRepayment.action')){
		//还款按钮
		operateFrom.items.items[3].show();
	}
	operateFrom.items.items[4].show();
	operateFrom.items.items[5].show();
}

/**
 * 对账单确认
 */
writeoff.customerStatementEdit.statementConfirm = function(){
	//选择对账单数据
	var selection=writeoff.customerStatementEdit.selection;
	//对账单明细窗口
	var win = writeoff.customerStatementEdit.statementEntryWindow;
	//对账单基础信息
	var baseForm = win.items.items[0].getForm();
	//对账单单号
	var statementBillNo = baseForm.findField('statementBillNo').getValue();
	//对账单确认状态
	var confirmStatus = baseForm.findField('confirmStatus').getValue();
	//获取明细GRID
	var CustomerStatementEntryEditGrid = win.items.items[2];
	//如果本期数据为空，则提示不能打印
  	if(CustomerStatementEntryEditGrid.store.data.length==0){
  		Ext.Msg.alert(writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.alert'),
  				writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementCommon.noData'));
		return false;
  	}
	//对账单确认状态判断
	if(confirmStatus==writeoff.STATEMENTCONFIRMSTATUS_Y||confirmStatus==''){
		Ext.Msg.alert(writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.alert'),
			writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementEdit.errorStatusToConfirmWarning'));
		return false;
	}
	//对账单单号判断
	if(statementBillNo==''){
		Ext.Msg.alert(writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.alert'),
			writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementEdit.statementNoIsNullWarning'));
		return false;
	}
	//对账单金额判断
	if(selection[0].data.periodAmount != selection[0].data.unpaidAmount){
		Ext.Msg.alert(writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.alert'),
			writeoff.customerStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.amountVerify'));
		return false;
	}
	//拼接vo，注入到后台
	customerStatementVo = new Object();
	customerStatementDto = new Object();
	customerStatementDto.confirmStatus = writeoff.STATEMENTCONFIRMSTATUS_Y;
	customerStatementDto.statementBillNo = statementBillNo;
	customerStatementVo.customerStatementDto = customerStatementDto;
	Ext.Ajax.request({
		url:writeoff.realPath('confirmOrUnConfirmStatement.action'),
		jsonData:{
			'customerStatementVo':customerStatementVo
		},
		success:function(response){
			var grid = Ext.getCmp('T_writeoff-customerStatementEdit_content').getGrid();
			//刷新对账单列表
			grid.store.load();
			//设置对账单确认状态
			baseForm.findField('confirmStatus').setValue(writeoff.STATEMENTCONFIRMSTATUS_Y);
			//设置对账单确认日期
			baseForm.findField('confirmTime').setValue(new Date());
			//设置操作按钮
			writeoff.customerStatementEdit.operateButton();
			Ext.ux.Toast.msg(writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.alert'), 
				writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementEdit.confirmSuccess'), 'ok', 1000);
		},
		exception:function(response){
			var result = Ext.decode(response.responseText);
			Ext.Msg.alert(writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.alert'),result.message);
		}			
	});
}

/**
 * 对账单反确认
 */
writeoff.customerStatementEdit.statementUnConfirm = function(){
	//选择对账单数据
	var selection=writeoff.customerStatementEdit.selection;
	//对账单明细窗口
	var win = writeoff.customerStatementEdit.statementEntryWindow;
	//对账单基础信息
	var baseForm = win.items.items[0].getForm();
	//对账单单号
	var statementBillNo = baseForm.findField('statementBillNo').getValue();
	//对账单确认状态
	var confirmStatus = baseForm.findField('confirmStatus').getValue();
	//对账单确认状态判断
	if(confirmStatus==writeoff.STATEMENTCONFIRMSTATUS_N||confirmStatus==''){
		Ext.Msg.alert(writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.alert'),
			writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementEdit.errorStatusToConfirmWarning'));
		return false;
	}
	//对账单单号判断
	if(statementBillNo==''){
		Ext.Msg.alert(writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.alert'),
			writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementEdit.statementNoIsNullWarning'));
		return false;
	}
	//对账单金额判断
	if(selection[0].data.periodAmount != selection[0].data.unpaidAmount){
		Ext.Msg.alert(writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.alert'),
			writeoff.customerStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.amountVerify'));
		return false;
	}
	//拼接vo，注入到后台
	customerStatementVo = new Object();
	customerStatementDto = new Object();
	customerStatementDto.confirmStatus = writeoff.STATEMENTCONFIRMSTATUS_N;
	customerStatementDto.statementBillNo = statementBillNo;
	customerStatementVo.customerStatementDto = customerStatementDto;
	Ext.Ajax.request({
		url:writeoff.realPath('confirmOrUnConfirmStatement.action'),
		jsonData:{
			'customerStatementVo':customerStatementVo
		},
		success:function(response){
			var grid = Ext.getCmp('T_writeoff-customerStatementEdit_content').getGrid();
			//刷新对账单列表
			grid.store.load();
			//设置对账单确认状态
			baseForm.findField('confirmStatus').setValue(writeoff.STATEMENTCONFIRMSTATUS_N);
			//设置对账单确认日期
			baseForm.findField('confirmTime').setValue(null);
			//设置操作按钮
			writeoff.customerStatementEdit.operateButton();
			Ext.ux.Toast.msg(writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.alert'), 
				writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementEdit.unConfirmSuccess'), 'ok', 1000);
		},
		exception:function(response){
			var result = Ext.decode(response.responseText);
			Ext.Msg.alert(writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.alert'),result.message);
		}
	});
}
/**
 * 还款提交操作
 */
writeoff.customerStatementEdit.statementRepaymentComplete = function(repaymentForm){
	var billRepaymentManageVo,
	billStatementToPaymentQueryDto;
	//还款单FORM
	var form=repaymentForm.getForm();
	if(form.isValid()){
		//处理对账单号
		var statementBillNos = [];
		var grid = Ext.getCmp('T_writeoff-customerStatementEdit_content').getGrid();
		//对账单基础信息
		var statementEntryWindow = writeoff.customerStatementEdit.statementEntryWindow;
		//还款单窗口
		var repaymentBillWindow = writeoff.customerStatementEdit.repaymentBillWindow;
		//还款单FORM
		var repaymentStatementForm = repaymentBillWindow.items.items[0].getForm();
		//对账单单号
		statementBillNos = stl.splitToArray(repaymentStatementForm.findField('statementBillNo').getValue());
		//设置对账单参数
		billStatementToPaymentQueryDto = new Object();
		billStatementToPaymentQueryDto.customerCode = repaymentStatementForm.findField('customerCode').getValue();
		billStatementToPaymentQueryDto.customerName = repaymentStatementForm.findField('customerName').getValue();
		billStatementToPaymentQueryDto.repaymentType = form.findField('repaymentType').getValue();
		billStatementToPaymentQueryDto.repaymentAmount = form.findField('repaymentAmount').getValue();
		billStatementToPaymentQueryDto.description = form.findField('description').getValue();
		billStatementToPaymentQueryDto.remittanceNumber = form.findField('remittanceNumber').getValue();
		billStatementToPaymentQueryDto.remittanceName = form.findField('remittanceName').getValue();
		billStatementToPaymentQueryDto.statementBillNos = statementBillNos;
		billRepaymentManageVo = new Object();
		billRepaymentManageVo.billStatementToPaymentQueryDto = billStatementToPaymentQueryDto;
		//将数据传递给后台
		Ext.Ajax.request({
			url:ContextPath.STL_WEB + '/pay/customerToRepayment.action',
			actionMethods:'POST',
			jsonData:{	
				'billRepaymentManageVo':billRepaymentManageVo
			},
			success:function(response){
				//获取返回结果
				var result = Ext.decode(response.responseText);
				//获取付款单号
	 			var repaymentNo = result.billRepaymentManageVo.billRepaymentManageDto.repaymentNo;
	 			//遮罩窗口
	 			repaymentForm.up('panel').hide();
				Ext.Msg.alert(writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.alert'),
					writeoff.customerStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.addRepaymentSuccess')
					+repaymentNo);
				//关闭窗口
				repaymentBillWindow.close();  //关闭弹出的还款窗口
	 			statementEntryWindow.close();  //关闭弹出的对账单明细窗口
	 			grid.store.load();
			},
			exception:function(response){
				var result = Ext.decode(response.responseText);
				Ext.Msg.alert(writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.alert'),result.message);
			}
		});
	}else{
		Ext.Msg.alert(writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.alert'), 
			writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.validateFailAlert'));
	}	
}
/**
 * 还款对账单信息
 */
Ext.define('Foss.customerStatementEdit.RepaymentStatementForm',{
	extend:'Ext.form.Panel',
	title:writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementCommon.statementInfo'),
	frame:true,
	layout: {
        type: 'table',
        columns: 3
    },
    defaultType:'textfield',
    defaults:{
  	    readOnly:true,
  	    labelWidth:70,
  	    width:240
    },
	items:[{
		fieldLabel:writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.customerName'),
		style:'margin-left:100px',
		name:'customerName',
		colspan:2
	},{
		fieldLabel:writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.customerCode'),
		name:'customerCode',
		style:'margin-left:100px',
		colspan:2
	},{
		fieldLabel:writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementCommon.statementBillNo'),
		name:'statementBillNo',
		style:'margin-left:100px',
		colspan:3
	},{
		fieldLabel:writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementAdd.periodAmount'),
		style:'margin-left:100px',
		labelWidth:95,
		xtype:'numberfield',
		name:'currentAmount',
		decimalPrecision:2,
		colspan:2
	},{
		fieldLabel:writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementCommon.currentRemainAmount'),
		style:'margin-left:100px',
		name:'currentRemainAmount',
		labelWidth:95,
		xtype:'numberfield',
		decimalPrecision:2
	}]
});

/**
* 还款单信息
*/
Ext.define('Foss.customerStatementEdit.RepaymentForm',{
	extend:'Ext.form.Panel',
	title:writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementCommon.repaymentInfo'),
	frame:true,
	layout: {
        type: 'table',
        columns: 3
    },
    defaultType:'textfield',
    defaults:{
  	    labelWidth:70,
  	    width:(stl.SCREENWIDTH*0.7-180)/3
    },
	items:[{
		xtype:'combo',
		name:'repaymentType',
		fieldLabel:writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementCommon.repaymentType'),
		allowBlank : false,
		style:'margin-left:100px',
		store:FossDataDictionary.getDataDictionaryStore(
				settlementDict.SETTLEMENT__PAYMENT_TYPE,null,null,
				[writeoff.SETTLEMENT__PAYMENT_TYPE__CASH,
				/**
				 * NCI项目，这里没有流水号校验，所以先给禁掉
				 */
				 /*writeoff.SETTLEMENT__PAYMENT_TYPE__CARD,*/
				 writeoff.SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER,
				 writeoff.SETTLEMENT__PAYMENT_TYPE__NOTE]),
		queryModel:'local',
		displayField:'valueName',
		valueField:'valueCode',
		value:writeoff.SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER,
		editable:false,
		listeners:{
			'select':function(combo){
				if(combo.value==writeoff.SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER
						||combo.value==writeoff.SETTLEMENT__PAYMENT_TYPE__NOTE){
					var currentRemainAmount = this.up('window').items.items[0].getForm().findField('currentRemainAmount').getValue();
					this.up('form').getForm().findField('remittanceNumber').enable();
					this.up('form').getForm().findField('remittanceNumber').labelEl.update('<span style="color:red;">*</span>'+writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementCommon.remittanceNumber')+':');
					this.up('form').getForm().findField('repaymentAmount').setValue(currentRemainAmount);
					this.up('form').getForm().findField('repaymentAmount').disable();
				}else if(combo.value==writeoff.SETTLEMENT__PAYMENT_TYPE__CASH
						||combo.value==writeoff.SETTLEMENT__PAYMENT_TYPE__CARD){
					var currentRemainAmount = this.up('window').items.items[0].getForm().findField('currentRemainAmount').getValue();
					this.up('form').getForm().findField('remittanceNumber').disable();
					this.up('form').getForm().findField('remittanceNumber').labelEl.update('&nbsp;&nbsp;'+writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementCommon.remittanceNumber')+':');
					this.up('form').getForm().findField('remittanceNumber').setValue(null);
					this.up('form').getForm().findField('remittanceName').setValue(null);
					this.up('form').getForm().findField('remittanceDate').setValue(null);
					this.up('form').getForm().findField('availableAmount').setValue(null);
					this.up('form').getForm().findField('repaymentAmount').setValue(currentRemainAmount);
					this.up('form').getForm().findField('repaymentAmount').enable();
				}
			}
		}
	},{
		fieldLabel:'<span style="color:red;">*</span>'+writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementCommon.remittanceNumber'),
		name:'remittanceNumber',
		listeners:{
			'blur':function(th){
				if(th.getValue()!=null&&th.getValue()!=''){
					var form = this.up('form').getForm();
					var repaymentAmount = form.findField('repaymentAmount').getValue();
					var repaymentType = form.findField('repaymentType').getValue();
			
					//设置参数体	
					var billRepaymentManageVo,
						billStatementToPaymentQueryDto;
					billStatementToPaymentQueryDto = new Object();
					billRepaymentManageVo = new Object();
					billStatementToPaymentQueryDto.remittanceNumber = th.getValue();
					billStatementToPaymentQueryDto.repaymentType = repaymentType;
					billRepaymentManageVo.billStatementToPaymentQueryDto = billStatementToPaymentQueryDto;
					
					//调用后台接口根据输入汇款编号获取汇款人、汇款日期，汇款可用金额、
					Ext.Ajax.request({
						url:ContextPath.STL_WEB + '/pay/queryRemittanceDetail.action',
						jsonData:{
							'billRepaymentManageVo':billRepaymentManageVo
						},
						method:'post',
						success:function(response){
							var result = Ext.decode(response.responseText);	
							var remittanceDate = stl.dateFormat(result.billRepaymentManageVo.billStatementToPaymentResultDto.remittanceDate,'Y-m-d');
							form.findField('remittanceName').setValue(result.billRepaymentManageVo.billStatementToPaymentResultDto.remittanceName);
							form.findField('remittanceDate').setValue(remittanceDate);
							form.findField('availableAmount').setValue(result.billRepaymentManageVo.billStatementToPaymentResultDto.availableAmount);							
							//比较还款金额和当前电汇、支票的可用金额，显示两者中较小的
							if(result.billRepaymentManageVo.billStatementToPaymentResultDto.availableAmount!=null
									&&result.billRepaymentManageVo.billStatementToPaymentResultDto.availableAmount!=''){
								if(result.billRepaymentManageVo.billStatementToPaymentResultDto.availableAmount<repaymentAmount){
									form.findField('repaymentAmount').setValue(result.billRepaymentManageVo.billStatementToPaymentResultDto.availableAmount);
								}
							}
						},
						exception:function(response){
							var result = Ext.decode(response.responseText);	
							Ext.Msg.alert(writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.alert'),result.message);
							form.findField('remittanceNumber').setValue(null);
						}		
					});
				}
			}
		}
	},{
		fieldLabel:writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementCommon.remittanceName'),
		name:'remittanceName',
		labelWidth:90,
		disabled:true
	},{
		fieldLabel:writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementCommon.repaymentAmount'),
		style:'margin-left:100px',
		name:'repaymentAmount',
		disabled:true,
		xtype:'numberfield',
		allowBlank : false
	},{
		fieldLabel:writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementCommon.remittanceDate'),
		name:'remittanceDate',
		format:'Y-m-d',
		xtype:'datefield',
		disabled:true
	},{
		fieldLabel:writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementCommon.availableAmount'),
		name:'availableAmount',
		disabled:true,
		labelWidth:90
	},{
		xtype:'textareafield',
		name:'description',
		fieldLabel:writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementCommon.description'),
		autoScroll : true,
		style:'margin-left:100px',
		format:'Y-m-d',
		colspan:3,
		width:(stl.SCREENWIDTH*0.7-180)*2/3
	},{
		xtype:'container',
		colspan:3,
		width:660,
		style:'margin-left:100px',
		defaultType:'button',
		layout:'table',
		items:[{
			xtype:'container',
			html:'&nbsp;',
			width:180
		},{
			text:writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementCommon.return'),
			width:70,
			cls:'yellow_button',
			handler:function(){
				this.up('form').up('panel').hide();		
			}
		},{
			text:writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementEdit.repayment'),
			width:70,
			style:'margin-left:20px',
			cls:'yellow_button',
			handler:function(){
				var form = this.up('form');
				var me = this;
				var currentRemainAmount = this.up('window').items.items[0].getForm().findField('currentRemainAmount').getValue();
				var repaymentAmount = form.getForm().findField('repaymentAmount').getValue();
				var availableAmount = form.getForm().findField('availableAmount').getValue();
				var repaymentType = form.getForm().findField('repaymentType').getValue();
				var remittanceNumber = form.getForm().findField('remittanceNumber').getValue();
				var repaymentTypeStr =writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementCommon.ticket'); 
				//还款金额判断
				if(repaymentAmount==null||repaymentAmount<=0){
					Ext.Msg.alert(writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.alert'),
						writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementCommon.repaymentAmountMin'));
					return false;
				}
				//还款金额和应还金额判断
				if(repaymentAmount>currentRemainAmount){
					Ext.Msg.alert(writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.alert'),
						writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementCommon.repaymentAmountMax'));
					return false;
				}	
				//电汇付款方式判断
				if(repaymentType==writeoff.SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER){
					repaymentTypeStr=writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementCommon.ticket');
					//校验汇款编码
					if(remittanceNumber==null||remittanceNumber==''){
						Ext.Msg.alert(writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.alert'),
							writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementCommon.checkRemittanceNumberIsNull'));
						return false;
					}
					//校验还款金额和汇款金额
					if(repaymentAmount>availableAmount){
						Ext.Msg.alert(writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.alert'),
							writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementCommon.ticketRepaymentAmountMax'));
						return false;
					}
				}
				//支票付款方式判断
				if(repaymentType==writeoff.SETTLEMENT__PAYMENT_TYPE__NOTE){
					repaymentTypeStr=writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementCommon.check');
					//校验汇款编码
					if(remittanceNumber==null||remittanceNumber==''){
						Ext.Msg.alert(writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.alert'),
							writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementCommon.checkRemittanceNumberIsNull'));
						return false;
					}
					//校验还款金额和汇款金额
					if(repaymentAmount>availableAmount){
						Ext.Msg.alert(writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.alert'),
							writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementCommon.checkRepaymentAmountMax'));
						return false;
					}
				}
				//现金付款方式判断
				if(repaymentType==writeoff.SETTLEMENT__PAYMENT_TYPE__CASH){
					repaymentTypeStr=writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementCommon.cash');
				}
				//银行卡付款方式判断
				if(repaymentType==writeoff.SETTLEMENT__PAYMENT_TYPE__CARD){
					repaymentTypeStr=writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementCommon.bankcard');
				}
				//还款金额和应还金额判断
				if(repaymentAmount<currentRemainAmount){
					Ext.MessageBox.show({
				        title:writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.alert'),
				        msg:writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementCommon.currentRepaymentType')+
				            repaymentTypeStr+writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementCommon.repaymentAlert'),
				        buttons:Ext.MessageBox.YESCANCEL,
				        buttonText:{ 
				            yes: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.ok'),
				            cancel:writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.cancel')
				        },
				        fn: function(e){
				        	if(e=='yes'){
			        			//设置该按钮灰掉
								me.disable(false);
								//10秒后自动解除灰掉效果
								setTimeout(function() {
									me.enable(true);
								}, 10000);
								writeoff.customerStatementEdit.statementRepaymentComplete(form);					
				        	}else if(e=='cancel'){
				        		return false;					
				        	}
				        }
					});
				//还款金额和应还金额判断
				}else if(repaymentAmount==currentRemainAmount){
					Ext.MessageBox.show({
				        title:writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.alert'),
				        msg:writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementCommon.currentRepaymentType')+
				        	repaymentTypeStr+writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementCommon.isRepaymentAlert'),
				        buttons:Ext.MessageBox.YESCANCEL,
				        buttonText:{ 
				            yes: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.ok'),
				            cancel:writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.cancel')
				        },
				        fn: function(e){
				        	if(e=='yes'){
			        			//设置该按钮灰掉
								me.disable(false);
								//10秒后自动解除灰掉效果
								setTimeout(function() {
									me.enable(true);
								}, 10000);
								writeoff.customerStatementEdit.statementRepaymentComplete(form);					
				        	}else if(e=='cancel'){
				        		return false;					
				        	}
				        }
					});
				}
			}
		}]
	}]
});
/**
 * 对账单还款窗体
 */
Ext.define('Foss.customerStatementEdit.RepaymentBillWindow',{
	extend:'Ext.window.Window',
	title:writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementCommon.repaymentBill'),
	width:stl.SCREENWIDTH*0.7,
	modal:true,
	constrainHeader: true,
	closeAction:'hide',
	items:[Ext.create('Foss.customerStatementEdit.RepaymentStatementForm'),
		Ext.create('Foss.customerStatementEdit.RepaymentForm')]
});
/**
 * 对账单还款操作
 */
writeoff.customerStatementEdit.repayment = function(){
	//还款单窗口
	if(Ext.isEmpty(writeoff.customerStatementEdit.repaymentBillWindow)){
		writeoff.customerStatementEdit.repaymentBillWindow = Ext.create('Foss.customerStatementEdit.RepaymentBillWindow');
	}
	var repaymentBillWindow = writeoff.customerStatementEdit.repaymentBillWindow;
	//对账单基础信息窗口
	var statementEntryWindow = writeoff.customerStatementEdit.statementEntryWindow;
	//对账单基础信息
	var statementEntryForm = statementEntryWindow.items.items[0].getForm();
	//还款对账单单信息
	var repaymentStatementForm = repaymentBillWindow.items.items[0].getForm();
	//还款单信息
	var repaymentForm = repaymentBillWindow.items.items[1].getForm();
	//对账单确认状态
	var confirmStatus = statementEntryForm.findField('confirmStatus').getValue();
	//单据类型
	var billType = statementEntryForm.findField('billType').getValue();
	//金额
	var periodAmount = statementEntryForm.findField('periodAmount').getValue();
	//未核销金额
	var unpaidAmount = statementEntryForm.findField('unpaidAmount').getValue();
	//单据类型判断
	if(billType==writeoff.STATEMENT_OF_ACCOUNT__BILL_TYPE__YF_WOODEN_ACCOUNT){
		Ext.Msg.alert(writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.alert'),
			writeoff.customerStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.YFWoodenAccount'));
		return false;
	}
	//对账单确认状态判断-对账单未确认时，不可以进行还款
	if(confirmStatus==writeoff.STATEMENTCONFIRMSTATUS_N||confirmStatus==''){
		Ext.Msg.alert(writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.alert'),
			writeoff.customerStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.unConfirmRepayment'));
		return false;
	}
	//设置还款界面
	repaymentStatementForm.reset();
	repaymentStatementForm.findField('customerName').setValue(statementEntryForm.findField('customerName').getValue());
	repaymentStatementForm.findField('customerCode').setValue(statementEntryForm.findField('customerCode').getValue());
	repaymentStatementForm.findField('statementBillNo').setValue(statementEntryForm.findField('statementBillNo').getValue());
	repaymentStatementForm.findField('currentAmount').setValue(periodAmount);
	repaymentStatementForm.findField('currentRemainAmount').setValue(unpaidAmount);
	repaymentForm.reset();
	repaymentForm.findField('repaymentType').enable();
	repaymentForm.findField('remittanceNumber').enable();
	repaymentForm.findField('description').enable();
	repaymentForm.findField('repaymentAmount').setValue(unpaidAmount);
	repaymentForm.findField('repaymentAmount').disable();
	repaymentBillWindow.show();
}

/**
 * 对账单明细STORE
 */
Ext.define('Foss.customerStatementEdit.CustomerStatementDStore',{
	extend:'Ext.data.Store',
	model:'Foss.customerStatementEdit.CustomerStatementDModel',
	pageSize:50,
	proxy:{
		type:'ajax',
		url:writeoff.realPath('queryCustomerDByStatementBillNo.action'),
		actionMethods:'POST',
		reader:{
			type:'json',
			root:'customerStatementVo.customerStatementDto.customerStatementDList',
			totalProperty:'totalCount'
		}
	},
	listeners:{
		'beforeLoad':function(store, operation, eOpts){
			var searchParams = {
					'customerStatementVo.customerStatementDto.statementBillNo':writeoff.customerStatementEdit.statementBillNo
				};
			//设置查询条件
			Ext.apply(operation,{
				params :searchParams
			}); 
		}
	}
});
/**
 * 按应收单号查询要添加的明细
 */
writeoff.customerStatementEdit.statementAddByYSNumber = function(){
	//添加对账单明细窗口
	var win = writeoff.customerStatementEdit.addCustomerStatementDWindow;
	var form = win.items.items[0].getForm();
	var grid = win.items.items[1];
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
		 	Ext.Msg.alert(writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.alert'),
		 		writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.queryNosLimit'));
		 	return false;
		 }
	}else{
		Ext.Msg.alert(writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.alert'),
			writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.billNosIsNullWarning'));
		return false;
	}
	//当界面校验都通过后，才执行查询方法
	if(form.isValid()){
		writeoff.customerStatementEdit.queryDTabType = writeoff.STATEMENTQUERYTAB_BYNUMBER;
		writeoff.customerStatementEdit.dNumbers = numbers;
		//查询后台
		grid.store.loadPage(1,{
			callback: function(records, operation, success) {
				var rawData = grid.store.proxy.reader.rawData;
				if(!success && ! rawData.isException){
					var result = Ext.decode(operation.response.responseText);	
					Ext.Msg.alert(writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.alert'),result.message);
				}
				if(success){
					//对结果进行转化
					var result = Ext.decode(operation.response.responseText);  
					//判断查询结果
					if(Ext.isEmpty(result.customerStatementVo.customerStatementDto.customerStatementDList) 
							||result.customerStatementVo.customerStatementDto.customerStatementDList.length==0){
						Ext.Msg.alert(writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.alert'),
							writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.noResult'));
						return false;
					}
				}
		    }
		});
	}else{
		Ext.Msg.alert(writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.alert'),
			writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.validateFailAlert'));
		return false;
	}	
}
/**
 * 对账单添加明细FORM
 */
Ext.define('Foss.customerStatementEdit.addCustomerStatementDForm',{
	extend:'Ext.form.Panel',
	frame:true,
	cls : 'autoHeight',
	bodyCls : 'autoHeight',
	title:writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.queryByNo'),
	layout:'column',
	defaults:{
		labelWidth:75,
		margin:'5 5 5 5'
	},
	items:[{       		
		xtype:'textareafield',
		fieldLabel:writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementAdd.number'),
		emptyText:writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementAdd.billNosQueryLimit'),
		allowBlank:false,
		columnWidth:.65,
		regex:/^([0-9]{7,10}[;,])*([0-9]{7,10}[;,]?)$/i,
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
				html:'<span style="color:red;">'+writeoff.customerStatementEdit.i18n('foss.stl.writeoff.customerStatementAdd.billNosDesc')+'</span>'
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
			text:writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.reset'),
			columnWidth:.075,
			handler:function(){
				this.up('form').getForm().reset();
			}
		},{
			xtype:'container',
			border:false,
			html:'&nbsp;',
			columnWidth:.5
		},{
			text:writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.query'),
			cls:'yellow_button',
			columnWidth:.075,
			handler:writeoff.customerStatementEdit.statementAddByYSNumber
		}]
	}]
});
/**
 * 对账单添加明细STORE
 */
Ext.define('Foss.customerStatementEdit.addCustomerStatementDStore',{
	extend:'Ext.data.Store',
	model:'Foss.customerStatementEdit.CustomerStatementDModel',
	pageSize:100,
	proxy:{
		type:'ajax',
		url:writeoff.realPath('queryAddCustomerStatementD.action'),
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
			//对账单明细窗口
			var statementEntryWindow = writeoff.customerStatementEdit.statementEntryWindow;
			var customerCode = statementEntryWindow.items.items[0].getForm().findField('customerCode').getValue();
			//是否统一结算
			var unifiedSettlement = statementEntryWindow.items.items[0].getForm().findField('unifiedSettlement').getValue();
			if(writeoff.customerStatementEdit.queryDTabType==writeoff.STATEMENTQUERYTAB_BYCUSTOMER){
				searchParams = {
					'customerStatementVo.customerStatementDto.periodBeginDate':writeoff.customerStatementEdit.periodBeginDate,	
					'customerStatementVo.customerStatementDto.periodEndDate':writeoff.customerStatementEdit.periodEndDate,	
					'customerStatementVo.customerStatementDto.customerCode':writeoff.customerStatementEdit.customerCode,
					'customerStatementVo.customerStatementDto.queryTabType':writeoff.customerStatementEdit.queryDTabType
				};
			}else{
				var numbers = stl.splitToArray(writeoff.customerStatementEdit.dNumbers);
				searchParams = {
					'customerStatementVo.customerStatementDto.numbers':numbers,
					'customerStatementVo.customerStatementDto.customerCode':customerCode,
					'customerStatementVo.customerStatementDto.receivableUnified':unifiedSettlement,
					'customerStatementVo.customerStatementDto.queryTabType':writeoff.customerStatementEdit.queryDTabType
				};
			}
			//设置查询条件
			Ext.apply(operation,{
				params :searchParams
			}); 
		}
	}
});
/**
 * 对账单添加明细提交操作
 */
writeoff.customerStatementEdit.addCustomerStatementDSubmit = function(){
	//添加对账单明细窗口
	var addCustomerStatementDWindow = writeoff.customerStatementEdit.addCustomerStatementDWindow;
	//添加对账单明细FORM
	var addCustomerStatementDForm = addCustomerStatementDWindow.items.items[0].getForm();
	//添加对账单明细GRID
	var addCustomerStatementDGrid = addCustomerStatementDWindow.items.items[1];
	//对账单明细窗口
	var statementEntryWindow = writeoff.customerStatementEdit.statementEntryWindow;
	//对账单明细基础信息
	var statementEntryForm = statementEntryWindow.items.items[0].getForm();
	//对账单明细GRID
	var statementEntryGrid = statementEntryWindow.items.items[2];
	//对账单单号
	var statementBillNo = statementEntryForm.findField('statementBillNo').getValue();
	//判断所添加的对账单明细的客户跟对账单中的客户编码是否一致
	var customerCode = statementEntryForm.findField('customerCode').getValue();
	var customerName = statementEntryForm.findField('customerName').getValue();
	//添加对账单明细列表
	var datas = addCustomerStatementDGrid.store.data;
	//添加对账单明细条数
	var lengths = datas.items.length;
	//获取添加对账单明细里的客户编码
	for(var i=0;i<lengths;i++){
		if(customerCode != datas.items[i].get('customerCode') || customerName != datas.items[i].get('customerName')){
			Ext.Msg.alert(writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.alert'), 
		  			writeoff.customerStatementEdit.i18n('foss.stl.writeoff.customerStatementEdit.DifferentCustomerCode'));
			return false;
		}
	}
	var me = this;
	Ext.MessageBox.show({
		title: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.alert'),
		msg: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.addWoodenStatementD'),
		buttons: Ext.MessageBox.YESNO,
		fn: function(e){
		  	//添加明细列表校验
		  	if(addCustomerStatementDGrid.store.data.length==0){
		  		Ext.Msg.alert(writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.alert'), 
		  			writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementCommon.noData'));
				return false;
		  	}
		  	if(e=='yes'){
				//判断是按那种查询进行
				var searchParams;
				if(writeoff.customerStatementEdit.queryDTabType==writeoff.STATEMENTQUERYTAB_BYCUSTOMER){
					//设置参数
					searchParams = {
						'customerStatementVo.customerStatementDto.periodBeginDate':writeoff.customerStatementEdit.periodBeginDate,	
						'customerStatementVo.customerStatementDto.periodEndDate':writeoff.customerStatementEdit.periodEndDate,	
						'customerStatementVo.customerStatementDto.customerCode':writeoff.customerStatementEdit.customerCode,
						'customerStatementVo.customerStatementDto.queryTabType':writeoff.customerStatementEdit.queryDTabType
					};
				}else{
					//应收单号集合
					var numbers = [];
					//添加对账单明细列表
					var data = addCustomerStatementDGrid.store.data;
					//添加对账单明细条数
					var length = data.items.length;
					//循环编译单号集合
					for(var i=0;i<length;i++){
						numbers.push(data.items[i].get('sourceBillNo'));
					}
					//设置参数
					searchParams = {
						'customerStatementVo.customerStatementDto.numbers':numbers,
						'customerStatementVo.customerStatementDto.statementBillNo':statementBillNo,
						'customerStatementVo.customerStatementDto.queryTabType':writeoff.customerStatementEdit.queryDTabType
					};
				}
				//拼接vo，注入到后台
				Ext.Ajax.request({
				    url: writeoff.realPath('addCustomerStatementD.action'),
				    method : 'POST',
				    params : searchParams,
				    success : function(response,options){
				    	var result = Ext.decode(response.responseText);
				    	statementEntryGrid.store.load();
				    	//设置操作按钮
				    	writeoff.customerStatementEdit.operateButton();
				    	//关闭对账单明细窗口
				    	addCustomerStatementDWindow.close();
				    	Ext.Msg.alert(writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.alert'),
							writeoff.customerStatementEdit.i18n('foss.stl.writeoff.woodenStatementAdd.addWoodenStatementDSuccess'));
				    },
					exception:function(response){
						var result = Ext.decode(response.responseText);	
						Ext.Msg.alert(writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.alert'),result.message);
					}
				});
		  	}else{
		  		return false;
		  	}
		}
	});
}
/**
 * 对账单添加明细GRID
 */
Ext.define('Foss.customerStatementEdit.addCustomerStatementDGrid',{
	extend:'Ext.grid.Panel',
	title: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.customerStatementAdd.Message'),
    bodyCls: 'autoHeight',
	cls: 'autoHeight',
	emptyText:writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.noResult'),
	frame:true,
	detailWin:null,
    store:Ext.create('Foss.customerStatementEdit.addCustomerStatementDStore'),
    //selModel:Ext.create('Ext.selection.CheckboxModel'),
    height:500,
  	viewConfig:{
  		enableTextSelection : true//设置行可以选择，进而可以复制
  	},
    defaults:{
  		align:'center'
  	},
  	columns: [{
        header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.businessDate'),
        dataIndex: 'businessDate',
        renderer: function (value) {
            if (value != null) {
                return Ext.Date.format(new Date(value), 'Y-m-d');
            } else {
                return null;
            }
        }
	    }, {
	        header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.waybillNo'),
	        dataIndex: 'waybillNo'
	    }, {
	        header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementAdd.arrvRegionCode'),
	        dataIndex: 'arrvRegionCode'
	    }, {
	        header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementAdd.productCode'),
	        dataIndex: 'productCode',
	        renderer: function (value) {
	            return Foss.pkp.ProductData.rendererSubmitToDisplay(value);
	        }
	    }, {
	        header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementAdd.deliveryCustomerName'),
	        dataIndex: 'deliveryCustomerName'
	    }, {
	        header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementAdd.unitPrice'),
	        dataIndex: 'unitPrice'
	    }, {
	        header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementAdd.insuranceAmount'),
	        dataIndex: 'insuranceAmount'
	    }, {
	        header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementAdd.qty'),
	        dataIndex: 'qty'
	    }, {
	        header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementAdd.billWeight'),
	        dataIndex: 'billWeight'
	    }, {
	        header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementAdd.billingVolume'),
	        dataIndex: 'billingVolume'
	    }, {
	        header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementAdd.insuranceFee'),
	        dataIndex: 'insuranceFee'
	    }, {
	        header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementAdd.deliverFee'),
	        dataIndex: 'deliverFee'
	    }, {
	        header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementAdd.packagingFee'),
	        dataIndex: 'packagingFee'
	    }, {
	        header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementAdd.otherFee'),
	        dataIndex: 'otherFee'
	    }, {
	        header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementAdd.receiveMethod'),
	        dataIndex: 'receiveMethod',
	        renderer: function (value) {
	            //先去汽运提货方式词条中拿
	            var displayField = FossDataDictionary.rendererSubmitToDisplay(value, settlementDict.PICKUP_GOODS);
	            //如果汽运提货方式没拿到，则去空运词条中拿
	            if (value == displayField || Ext.isEmpty(displayField)) {
	                displayField = FossDataDictionary.rendererSubmitToDisplay(value, settlementDict.PICKUP_GOODS_AIR);
	            }
	            return displayField;
	        }
	    }, {
	        header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementAdd.paymentType'),
	        dataIndex: 'paymentType',
	        renderer: function (value) {
	            var displayField = FossDataDictionary.rendererSubmitToDisplay(value, settlementDict.SETTLEMENT__PAYMENT_TYPE);
	            return displayField;
	        }
	    }, {
	        header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.originalUnverifyAmount'),
	        dataIndex: 'unverifyAmount',
	        renderer: function (v, m, record) {
	            if (record.get('billParentType') == writeoff.STATEMENTDETAIL_PAYABLE || record.get('billParentType') == writeoff.STATEMENTDETAIL_DEPOSIT_RECEIVED) {
	                return '<span style="color:red">' + v + '</span>';
	            } else {
	                return v;
	            }
	        }
	    }, {
	        header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.totalAmount'),
	        dataIndex: 'amount',
	        renderer: function (v, m, record) {
	            if (record.get('billParentType') == writeoff.STATEMENTDETAIL_PAYABLE || record.get('billParentType') == writeoff.STATEMENTDETAIL_DEPOSIT_RECEIVED) {
	                return '<span style="color:red">' + v + '</span>';
	            } else {
	                return v;
	            }
	        }
	    }, {
	        header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementAdd.number'),
	        dataIndex: 'sourceBillNo'
	    }, {
	        header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementAdd.deliveryCustomerContact'),
	        dataIndex: 'deliveryCustomerContact'
	    }, {
	        header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.origSourceBillNo'),
	        dataIndex: 'origSourceBillNo'
	    }, {
	        header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementAdd.billParentType'),
	        dataIndex: 'billParentType',
	        renderer: function (value) {
	            var displayField = FossDataDictionary.rendererSubmitToDisplay(value, settlementDict.STATEMENT_OF_ACCOUNT_D__BILL_PARENT_TYPE);
	            return displayField;
	        }
	    }, {
	        header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.billType'),
	        dataIndex: 'billType',
	        renderer: function (v, m, record) {
	            return writeoff.customerStatementEdit.statementFormatBillType(v, record);
	        }
	    }, {
	        header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.customerCode'),
	        dataIndex: 'customerCode'
	    }, {
	        header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.customerName'),
	        dataIndex: 'customerName'
	    }, {
	        header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.verifyAmount'),
	        dataIndex: 'verifyAmount',
	        hidden: true,
	        renderer: function (v, m, record) {
	            if (record.get('billParentType') == writeoff.STATEMENTDETAIL_PAYABLE || record.get('billParentType') == writeoff.STATEMENTDETAIL_DEPOSIT_RECEIVED) {
	                return '<span style="color:red">' + v + '</span>';
	            } else {
	                return v;
	            }
	        }
	    }, {
	        header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.orgCode'),
	        dataIndex: 'orgCode'
	    }, {
	        header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.orgName'),
	        dataIndex: 'orgName'
	    }, {
	        header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.accountDate'),
	        dataIndex: 'accountDate',
	        renderer: function (value) {
	            if (value != null) {
	                return Ext.Date.format(new Date(value), 'Y-m-d');
	            } else {
	                return null;
	            }
	        }
	    }, {
	        header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementAdd.customerPickupOrgName'),
	        dataIndex: 'customerPickupOrgName'
	    }, {
	        header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementAdd.receiveCustomerName'),
	        dataIndex: 'receiveCustomerName'
	    }, {
	        header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementAdd.receiveCustomerCode'),
	        dataIndex: 'receiveCustomerCode'
	    }, {
	        header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementAdd.goodsName'),
	        dataIndex: 'goodsName'
	    }, {
	        header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementAdd.transportFee'),
	        dataIndex: 'transportFee'
	    }, {
	        header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementAdd.pickupFee'),
	        dataIndex: 'pickupFee'
	    }, {
	        header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementAdd.codFee'),
	        dataIndex: 'codFee'
	    }, {
	        header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementAdd.valueAddFee'),
	        dataIndex: 'valueAddFee'
	    }, {
	        header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementAdd.promotionsFee'),
	        dataIndex: 'promotionsFee'
	    }, {
	        header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementAdd.origOrgCode'),
	        dataIndex: 'origOrgCode'
	    }, {
	        header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementAdd.origOrgName'),
	        dataIndex: 'origOrgName'
	    }, {
	        header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementAdd.destOrgName'),
	        dataIndex: 'destOrgName'
	    }, {
	        header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementAdd.destOrgCode'),
	        dataIndex: 'destOrgCode'
	    }, {
            header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementAdd.deliveryCustomerCode'),
            dataIndex: 'deliveryCustomerCode'
        }, {
            header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementAdd.signDate'),
            dataIndex: 'signDate',
            renderer: function (value) {
                if (value != null) {
                    return Ext.Date.format(new Date(value), 'Y-m-d');
                } else {
                    return null;
                }
            }
        }, {
            header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementAdd.auditStatus'),
            dataIndex: 'auditStatus',
            renderer: function (value) {
                var displayField = FossDataDictionary.rendererSubmitToDisplay(value, settlementDict.BILL_RECEIVABLE__APPROVE_STATUS);
                return displayField;
            }
        }, {
            header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.notes'),
            dataIndex: 'notes',
            hidden: true
        }, {
            header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementAdd.isDelete'),
            dataIndex: 'isDelete',
            hidden: true
        }, {
            header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementAdd.statementBillNo'),
            dataIndex: 'statementBillNo',
            hidden: true
        }, {
            header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.disableTime'),
            dataIndex: 'disableTime',
            hidden: true,
            renderer: function (value) {
                if (value != null) {
                    return Ext.Date.format(new Date(value), 'Y-m-d');
                } else {
                    return null;
                }
            }
        }, {
            header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.createTime'),
            dataIndex: 'createTime',
            hidden: true,
            renderer: function (value) {
                if (value != null) {
                    return Ext.Date.format(new Date(value), 'Y-m-d');
                } else {
                    return null;
                }
            }
        }, {
            header: 'id',
            dataIndex: 'id',
            hidden: true
        },{
            header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementAdd.unifiedSettlement'),
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
				xtype:'button',
				text:writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementCommon.addStatementEntry'),
				columnWidth:.1,
				hidden:!writeoff.customerStatementEdit.isPermission('/stl-web/writeoff/addCustomerStatementD.action'),
				handler:writeoff.customerStatementEdit.addCustomerStatementDSubmit
			}]
		}];
		me.callParent();
	}
});
/**
 * 对账单添加明细窗体
 */
Ext.define('Foss.customerStatementEdit.addCustomerStatementDWindow',{
	extend:'Ext.window.Window',
	width:stl.SCREENWIDTH*0.9,
	modal:true,
	constrainHeader: true,
	closeAction:'destory',
	items:[Ext.create('Foss.customerStatementEdit.addCustomerStatementDForm'),
	       Ext.create('Foss.customerStatementEdit.addCustomerStatementDGrid')]
});

/**
 * 对账单添加明细操作
 */
writeoff.customerStatementEdit.addCustomerStatementD = function(){
	//添加对账单明细窗口
	var win = writeoff.customerStatementEdit.addCustomerStatementDWindow;
	if(Ext.isEmpty(writeoff.customerStatementEdit.addCustomerStatementDWindow)){
		writeoff.customerStatementEdit.addCustomerStatementDWindow = Ext.create('Foss.customerStatementEdit.addCustomerStatementDWindow');
		win = writeoff.customerStatementEdit.addCustomerStatementDWindow;
	}
	//清空表单
	win.items.items[0].getForm().reset();
	//清空列表
	win.items.items[1].store.removeAll();
	//对账单明细窗口
	var statementEntryWindow = writeoff.customerStatementEdit.statementEntryWindow;
	//对账单基础信息
	var baseForm = statementEntryWindow.items.items[0].getForm();
	//对账单确认状态
	var confirmStatus = baseForm.findField('confirmStatus').getValue();
	//对账单确认状态判断
	if(confirmStatus==writeoff.STATEMENTCONFIRMSTATUS_Y||confirmStatus==''){
		Ext.Msg.alert(writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.alert'),
			writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementEdit.confirmAddWooden'));
		return false;
	}
	win.show();
}
/**
 * 对账单删除明细提交操作
 */
writeoff.customerStatementEdit.delCustomerStatementDSubmit = function(){
	//删除对账单明细窗口
	var delCustomerStatementDWindow = writeoff.customerStatementEdit.delCustomerStatementDWindow;
	//删除对账单明细FORM
	var delCustomerStatementDForm = delCustomerStatementDWindow.items.items[0].getForm();
	//删除对账单明细GRID
	var delCustomerStatementDGrid = delCustomerStatementDWindow.items.items[1];
	//对账单明细窗口
	var statementEntryWindow = writeoff.customerStatementEdit.statementEntryWindow;
	//对账单明细FORM
	var statementEntryForm = statementEntryWindow.items.items[0].getForm();
	//对账单明细GRID
	var statementEntryGrid = statementEntryWindow.items.items[2];
	//对账单单号
	var statementBillNo = statementEntryForm.findField('statementBillNo').getValue();
	var me = this;
	Ext.MessageBox.show({
		title: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.alert'),
		msg: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.delWoodenStatementD'),
		buttons: Ext.MessageBox.YESNO,
		fn: function(e){
		  	//删除明细列表校验
		  	if(delCustomerStatementDGrid.store.data.length==0){
		  		Ext.Msg.alert(writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.alert'), 
		  			writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementCommon.noData'));
				return false;
		  	}
		  	
		  	if(e=='yes'){
		  		//应收单号集合
				var numbers = [];
				//删除对账单明细列表
				var data = delCustomerStatementDGrid.store.data;
				//删除对账单明细条数
				var length = data.items.length;
				//循环编译单号集合
				for(var i=0;i<length;i++){
					numbers.push(data.items[i].get('sourceBillNo'));
				}
				//设置参数
				var searchParams = {
						'customerStatementVo.customerStatementDto.numbers':numbers,
						'customerStatementVo.customerStatementDto.statementBillNo':statementBillNo
					};
				Ext.Ajax.request({
				    url: writeoff.realPath('delCustomerStatementD.action'),
				    method : 'POST',
				    params : searchParams,
				    success : function(response,options){
				    	var result = Ext.decode(response.responseText);
				    	//刷新对账单明细列表
				    	statementEntryGrid.store.load();
				    	//设置操作按钮
				    	writeoff.customerStatementEdit.operateButton();
				    	//关闭对账单明细窗口
				    	delCustomerStatementDWindow.close();
				    	Ext.Msg.alert(writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.alert'),
							writeoff.customerStatementEdit.i18n('foss.stl.writeoff.woodenStatementAdd.delWoodenStatementDSuccess'));
				    },
					exception:function(response){
						var result = Ext.decode(response.responseText);	
						Ext.Msg.alert(writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.alert'),result.message);
					}
				});
		  	}else{
		  		return false;
		  	}
		}
	});
}
/**
 * 对账单删除明细STORE
 */
Ext.define('Foss.customerStatementEdit.delCustomerStatementDStore',{
	extend:'Ext.data.Store',
	model:'Foss.customerStatementEdit.CustomerStatementDModel',
	pageSize:100,
	proxy:{
		type:'ajax',
		url:writeoff.realPath('queryDelCustomerStatementD.action'),
		actionMethods:'POST',
		reader:{
			type:'json',
			root:'customerStatementVo.customerStatementDto.customerStatementDList',
			totalProperty:'totalCount'
		}
	},
	listeners:{
		'beforeLoad':function(store, operation, eOpts){
			var numbers = stl.splitToArray(writeoff.customerStatementEdit.dNumbers);
			var searchParams = {
					'customerStatementVo.customerStatementDto.numbers':numbers,
					'customerStatementVo.customerStatementDto.statementBillNo':writeoff.customerStatementEdit.statementBillNo
				};
			//设置查询条件
			Ext.apply(operation,{
				params :searchParams
			}); 
		}
	}
});
/**
 * 对账单删除明细GRID
 */
Ext.define('Foss.customerStatementEdit.delCustomerStatementDGrid',{
	extend:'Ext.grid.Panel',
	title: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.customerStatementAdd.Message'),
    bodyCls: 'autoHeight',
	cls: 'autoHeight',
	emptyText:writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.noResult'),
	frame:true,
	detailWin:null,
    store:Ext.create('Foss.customerStatementEdit.delCustomerStatementDStore'),
    //selModel:Ext.create('Ext.selection.CheckboxModel'),
    height:500,
  	viewConfig:{
  		enableTextSelection : true//设置行可以选择，进而可以复制
  	},
    defaults:{
  		align:'center'
  	},
  	columns: [{
        header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.businessDate'),
        dataIndex: 'businessDate',
        renderer: function (value) {
            if (value != null) {
                return Ext.Date.format(new Date(value), 'Y-m-d');
            } else {
                return null;
            }
        }
	    }, {
	        header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.waybillNo'),
	        dataIndex: 'waybillNo'
	    }, {
	        header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementAdd.arrvRegionCode'),
	        dataIndex: 'arrvRegionCode'
	    }, {
	        header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementAdd.productCode'),
	        dataIndex: 'productCode',
	        renderer: function (value) {
	            return Foss.pkp.ProductData.rendererSubmitToDisplay(value);
	        }
	    }, {
	        header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementAdd.deliveryCustomerName'),
	        dataIndex: 'deliveryCustomerName'
	    }, {
	        header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementAdd.unitPrice'),
	        dataIndex: 'unitPrice'
	    }, {
	        header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementAdd.insuranceAmount'),
	        dataIndex: 'insuranceAmount'
	    }, {
	        header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementAdd.qty'),
	        dataIndex: 'qty'
	    }, {
	        header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementAdd.billWeight'),
	        dataIndex: 'billWeight'
	    }, {
	        header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementAdd.billingVolume'),
	        dataIndex: 'billingVolume'
	    }, {
	        header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementAdd.insuranceFee'),
	        dataIndex: 'insuranceFee'
	    }, {
	        header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementAdd.deliverFee'),
	        dataIndex: 'deliverFee'
	    }, {
	        header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementAdd.packagingFee'),
	        dataIndex: 'packagingFee'
	    }, {
	        header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementAdd.otherFee'),
	        dataIndex: 'otherFee'
	    }, {
	        header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementAdd.receiveMethod'),
	        dataIndex: 'receiveMethod',
	        renderer: function (value) {
	            //先去汽运提货方式词条中拿
	            var displayField = FossDataDictionary.rendererSubmitToDisplay(value, settlementDict.PICKUP_GOODS);
	            //如果汽运提货方式没拿到，则去空运词条中拿
	            if (value == displayField || Ext.isEmpty(displayField)) {
	                displayField = FossDataDictionary.rendererSubmitToDisplay(value, settlementDict.PICKUP_GOODS_AIR);
	            }
	            return displayField;
	        }
	    }, {
	        header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementAdd.paymentType'),
	        dataIndex: 'paymentType',
	        renderer: function (value) {
	            var displayField = FossDataDictionary.rendererSubmitToDisplay(value, settlementDict.SETTLEMENT__PAYMENT_TYPE);
	            return displayField;
	        }
	    }, {
	        header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.originalUnverifyAmount'),
	        dataIndex: 'unverifyAmount',
	        renderer: function (v, m, record) {
	            if (record.get('billParentType') == writeoff.STATEMENTDETAIL_PAYABLE || record.get('billParentType') == writeoff.STATEMENTDETAIL_DEPOSIT_RECEIVED) {
	                return '<span style="color:red">' + v + '</span>';
	            } else {
	                return v;
	            }
	        }
	    }, {
	        header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.totalAmount'),
	        dataIndex: 'amount',
	        renderer: function (v, m, record) {
	            if (record.get('billParentType') == writeoff.STATEMENTDETAIL_PAYABLE || record.get('billParentType') == writeoff.STATEMENTDETAIL_DEPOSIT_RECEIVED) {
	                return '<span style="color:red">' + v + '</span>';
	            } else {
	                return v;
	            }
	        }
	    }, {
	        header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementAdd.number'),
	        dataIndex: 'sourceBillNo'
	    }, {
	        header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementAdd.deliveryCustomerContact'),
	        dataIndex: 'deliveryCustomerContact'
	    }, {
	        header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.origSourceBillNo'),
	        dataIndex: 'origSourceBillNo'
	    }, {
	        header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementAdd.billParentType'),
	        dataIndex: 'billParentType',
	        renderer: function (value) {
	            var displayField = FossDataDictionary.rendererSubmitToDisplay(value, settlementDict.STATEMENT_OF_ACCOUNT_D__BILL_PARENT_TYPE);
	            return displayField;
	        }
	    }, {
	        header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.billType'),
	        dataIndex: 'billType',
	        renderer: function (v, m, record) {
	            return writeoff.customerStatementEdit.statementFormatBillType(v, record);
	        }
	    }, {
	        header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.customerCode'),
	        dataIndex: 'customerCode'
	    }, {
	        header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.customerName'),
	        dataIndex: 'customerName'
	    }, {
	        header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.verifyAmount'),
	        dataIndex: 'verifyAmount',
	        hidden: true,
	        renderer: function (v, m, record) {
	            if (record.get('billParentType') == writeoff.STATEMENTDETAIL_PAYABLE || record.get('billParentType') == writeoff.STATEMENTDETAIL_DEPOSIT_RECEIVED) {
	                return '<span style="color:red">' + v + '</span>';
	            } else {
	                return v;
	            }
	        }
	    }, {
	        header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.orgCode'),
	        dataIndex: 'orgCode'
	    }, {
	        header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.orgName'),
	        dataIndex: 'orgName'
	    }, {
	        header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.accountDate'),
	        dataIndex: 'accountDate',
	        renderer: function (value) {
	            if (value != null) {
	                return Ext.Date.format(new Date(value), 'Y-m-d');
	            } else {
	                return null;
	            }
	        }
	    }, {
	        header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementAdd.customerPickupOrgName'),
	        dataIndex: 'customerPickupOrgName'
	    }, {
	        header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementAdd.receiveCustomerName'),
	        dataIndex: 'receiveCustomerName'
	    }, {
	        header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementAdd.receiveCustomerCode'),
	        dataIndex: 'receiveCustomerCode'
	    }, {
	        header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementAdd.goodsName'),
	        dataIndex: 'goodsName'
	    }, {
	        header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementAdd.transportFee'),
	        dataIndex: 'transportFee'
	    }, {
	        header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementAdd.pickupFee'),
	        dataIndex: 'pickupFee'
	    }, {
	        header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementAdd.codFee'),
	        dataIndex: 'codFee'
	    }, {
	        header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementAdd.valueAddFee'),
	        dataIndex: 'valueAddFee'
	    }, {
	        header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementAdd.promotionsFee'),
	        dataIndex: 'promotionsFee'
	    }, {
	        header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementAdd.origOrgCode'),
	        dataIndex: 'origOrgCode'
	    }, {
	        header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementAdd.origOrgName'),
	        dataIndex: 'origOrgName'
	    }, {
	        header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementAdd.destOrgName'),
	        dataIndex: 'destOrgName'
	    }, {
	        header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementAdd.destOrgCode'),
	        dataIndex: 'destOrgCode'
	    }, {
            header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementAdd.deliveryCustomerCode'),
            dataIndex: 'deliveryCustomerCode'
        }, {
            header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementAdd.signDate'),
            dataIndex: 'signDate',
            renderer: function (value) {
                if (value != null) {
                    return Ext.Date.format(new Date(value), 'Y-m-d');
                } else {
                    return null;
                }
            }
        }, {
            header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementAdd.auditStatus'),
            dataIndex: 'auditStatus',
            renderer: function (value) {
                var displayField = FossDataDictionary.rendererSubmitToDisplay(value, settlementDict.BILL_RECEIVABLE__APPROVE_STATUS);
                return displayField;
            }
        }, {
            header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.notes'),
            dataIndex: 'notes',
            hidden: true
        }, {
            header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementAdd.isDelete'),
            dataIndex: 'isDelete',
            hidden: true
        }, {
            header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementAdd.statementBillNo'),
            dataIndex: 'statementBillNo',
            hidden: true
        }, {
            header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.disableTime'),
            dataIndex: 'disableTime',
            hidden: true,
            renderer: function (value) {
                if (value != null) {
                    return Ext.Date.format(new Date(value), 'Y-m-d');
                } else {
                    return null;
                }
            }
        }, {
            header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.createTime'),
            dataIndex: 'createTime',
            hidden: true,
            renderer: function (value) {
                if (value != null) {
                    return Ext.Date.format(new Date(value), 'Y-m-d');
                } else {
                    return null;
                }
            }
        }, {
            header: 'id',
            dataIndex: 'id',
            hidden: true
        },{
            header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementAdd.unifiedSettlement'),
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
				xtype:'button',
				text:writeoff.customerStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.delStatementD'),
				columnWidth:.1,
				hidden:!writeoff.customerStatementEdit.isPermission('/stl-web/writeoff/delCustomerStatementD.action'),
				handler:writeoff.customerStatementEdit.delCustomerStatementDSubmit
			}]
		}];
		me.callParent();
	}
});
/**
 * 按运单号查询要删除的明细
 */
writeoff.customerStatementEdit.statementDelByNumber = function(){
	//对账单删除明细窗口
	var win = writeoff.customerStatementEdit.delCustomerStatementDWindow;
	var form = win.items.items[0].getForm();
	var grid = win.items.items[1];
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
		 //判断输入单号是否超过100个
		 if(billNumberArray.length>100){
		 	Ext.Msg.alert(writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.alert'),
		 		writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.queryNosLimit'));
		 	return false;
		 }
	}else{
		Ext.Msg.alert(writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.alert'),
			writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.billNosIsNullWarning'));
		return false;
	}
	//当界面校验都通过后，才执行查询方法
	if(form.isValid()){
		writeoff.customerStatementEdit.dNumbers = numbers;
		//查询后台
		grid.store.loadPage(1,{
			callback: function(records, operation, success) {
				var rawData = grid.store.proxy.reader.rawData;
				if(!success && ! rawData.isException){
					var result = Ext.decode(operation.response.responseText);	
					Ext.Msg.alert(writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.alert'),result.message);
				}
				if(success){
					//对结果进行转化
					var result = Ext.decode(operation.response.responseText);  
					//判断查询结果
					if(Ext.isEmpty(result.customerStatementVo.customerStatementDto.customerStatementDList) 
							||result.customerStatementVo.customerStatementDto.customerStatementDList.length==0){
						Ext.Msg.alert(writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.alert'),
							writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.noResult'));
						return false;
					}
				}
		    }
		});
	}else{
		Ext.Msg.alert(writeoff.woodenStatementEdit.i18n('foss.stl.writeoff.common.alert'),
			writeoff.woodenStatementAdd.i18n('foss.stl.writeoff.common.validateFailAlert'));
		return false;
	}	
}
/**
 * 对账单删除明细FORM
 */
Ext.define('Foss.customerStatementEdit.delCustomerStatementDForm',{
	extend:'Ext.form.Panel',
	frame:true,
	cls : 'autoHeight',
	bodyCls : 'autoHeight',
	title:writeoff.customerStatementEdit.i18n('foss.stl.writeoff.woodenStatementAdd.queryByNumber'),
	layout:'column',
	defaults:{
		labelWidth:75,
		margin:'5 5 5 5'
	},
	items:[{       		
		xtype:'textareafield',
		fieldLabel:writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementAdd.number'),
		emptyText:writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementAdd.billNosQueryLimit'),
		allowBlank:false,
		columnWidth:.65,
		regex:/^([0-9]{7,10}[;,])*([0-9]{7,10}[;,]?)$/i,
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
				html:'<span style="color:red;">'+writeoff.customerStatementEdit.i18n('foss.stl.writeoff.customerStatementAdd.billNosDesc')+'</span>'
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
			text:writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.reset'),
			columnWidth:.075,
			handler:function(){
				this.up('form').getForm().reset();
			}
		},{
			xtype:'container',
			border:false,
			html:'&nbsp;',
			columnWidth:.5
		},{
			text:writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.query'),
			cls:'yellow_button',
			columnWidth:.075,
			handler:writeoff.customerStatementEdit.statementDelByNumber
		}]
	}]
});
/**
 * 对账单删除明细窗体
 */
Ext.define('Foss.customerStatementEdit.delCustomerStatementDWindow',{
	extend:'Ext.window.Window',
	width:stl.SCREENWIDTH*0.9,
	modal:true,
	constrainHeader: true,
	closeAction:'destory',
	items:[Ext.create('Foss.customerStatementEdit.delCustomerStatementDForm'),
	       Ext.create('Foss.customerStatementEdit.delCustomerStatementDGrid')]
});
/**
 * 对账单删除明细操作
 */
writeoff.customerStatementEdit.delCustomerStatementD = function(){
	//对账单删除窗口
	var win = writeoff.customerStatementEdit.delCustomerStatementDWindow;
	if(Ext.isEmpty(writeoff.customerStatementEdit.delCustomerStatementDWindow)){
		writeoff.customerStatementEdit.delCustomerStatementDWindow = Ext.create('Foss.customerStatementEdit.delCustomerStatementDWindow');
		win = writeoff.customerStatementEdit.delCustomerStatementDWindow;
	}
	//清空表单
	win.items.items[0].getForm().reset();
	//清空列表
	win.items.items[1].store.removeAll();
	//对账单明细窗口
	var statementEntryWindow = writeoff.customerStatementEdit.statementEntryWindow;
	//对账单基础信息
	var baseForm = statementEntryWindow.items.items[0].getForm();
	//对账单确认状态
	var confirmStatus = baseForm.findField('confirmStatus').getValue();
	//对账单确认状态判断
	if(confirmStatus==writeoff.STATEMENTCONFIRMSTATUS_Y||confirmStatus==''){
		Ext.Msg.alert(writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.alert'),
			writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementEdit.confirmDelWooden'));
		return false;
	}
	win.show();
}

//ddw1-勾选删除
writeoff.customerStatementEdit.checkDelCustomerStatementD = function(){
	//对账单明细窗口
	var statementEntryWindow = writeoff.customerStatementEdit.statementEntryWindow;
	//对账单明细FORM
	var statementEntryForm = statementEntryWindow.items.items[0].getForm();
	//对账单明细GRID
	var statementEntryGrid = statementEntryWindow.items.items[2];
	//对账单单号
	var statementBillNo = statementEntryForm.findField('statementBillNo').getValue();
	//勾选的单号
	var selections = statementEntryGrid.getSelectionModel().getSelection();
	//对账单确认状态
	var confirmStatus = statementEntryForm.findField('confirmStatus').getValue();
	//对账单确认状态判断
	if(confirmStatus==writeoff.STATEMENTCONFIRMSTATUS_Y||confirmStatus==''){
		Ext.Msg.alert(writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.alert'),
			writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementEdit.confirmDelWooden'));
		return false;
	}
	//删除明细列表校验
  	if(selections.length==0){
  		Ext.Msg.alert(writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.alert'), 
  			writeoff.customerStatementEdit.i18n('foss.stl.writeoff.customerStatementEdit.CustomerStatementSelect'));
		return false;
  	}
	Ext.MessageBox.show({
		title: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.alert'),
		msg: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.delWoodenStatementD'),
		buttons: Ext.MessageBox.YESNO,
		fn: function(e){
		  	if(e=='yes'){
		  		//应收单号集合
				var numbers = [];
				//删除对账单明细条数
				var length = selections.length;
				//循环编译单号集合
				for(var i=0;i<length;i++){
					numbers.push(selections[i].get('sourceBillNo'));
				}
		  		//拼接vo，注入到后台
		  		customerStatementVo = new Object();
		  		customerStatementDto = new Object();
		  		customerStatementDto.numbers = numbers;
		  		customerStatementDto.statementBillNo = statementBillNo;
		  		customerStatementVo.customerStatementDto = customerStatementDto;
				Ext.Ajax.request({
				    url: writeoff.realPath('delCustomerStatementD.action'),
				    method : 'POST',
		  			jsonData:{
		  				'customerStatementVo':customerStatementVo
		  			},
				    success : function(response,options){
				    	var result = Ext.decode(response.responseText);
				    	//刷新对账单明细列表
				    	statementEntryGrid.store.load();
				    	//设置应收应付金额
				    	//writeoff.customerStatementEdit.recFormORpayForm(statementEntryWindow,result,'');
				    	//设置操作按钮
				    	writeoff.customerStatementEdit.operateButton();
				    	Ext.Msg.alert(writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.alert'),
							writeoff.customerStatementEdit.i18n('foss.stl.writeoff.woodenStatementAdd.delWoodenStatementDSuccess'));
				    },
					exception:function(response){
						var result = Ext.decode(response.responseText);	
						Ext.Msg.alert(writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.alert'),result.message);
					}
				});
		  	}else{
		  		return false;
		  	}
		}
	});
}

/**
 * 对账单明细GRID
 */
Ext.define('Foss.customerStatementEdit.CustomerStatementDGrid',{
	extend:'Ext.grid.Panel',
	title: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementCommon.statementEntry'),
    bodyCls: 'autoHeight',
	cls: 'autoHeight',
	emptyText:writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.noResult'),
	frame:true,
	detailWin:null,
    store:Ext.create('Foss.customerStatementEdit.CustomerStatementDStore'),
    selModel:Ext.create('Ext.selection.CheckboxModel'),
    height:500,
  	viewConfig:{
  		enableTextSelection : true//设置行可以选择，进而可以复制
  	},
    defaults:{
  		align:'center'
  	},
  	columns: [{
        header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.businessDate'),
        dataIndex: 'businessDate',
        renderer: function (value) {
            if (value != null) {
                return Ext.Date.format(new Date(value), 'Y-m-d');
            } else {
                return null;
            }
        }
	    }, {
	        header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.waybillNo'),
	        dataIndex: 'waybillNo'
	    }, {
	        header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementAdd.arrvRegionCode'),
	        dataIndex: 'arrvRegionCode'
	    }, {
	        header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementAdd.productCode'),
	        dataIndex: 'productCode',
	        renderer: function (value) {
	            return Foss.pkp.ProductData.rendererSubmitToDisplay(value);
	        }
	    }, {
	        header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementAdd.deliveryCustomerName'),
	        dataIndex: 'deliveryCustomerName'
	    }, {
	        header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementAdd.unitPrice'),
	        dataIndex: 'unitPrice'
	    }, {
	        header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementAdd.insuranceAmount'),
	        dataIndex: 'insuranceAmount'
	    }, {
	        header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementAdd.qty'),
	        dataIndex: 'qty'
	    }, {
	        header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementAdd.billWeight'),
	        dataIndex: 'billWeight'
	    }, {
	        header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementAdd.billingVolume'),
	        dataIndex: 'billingVolume'
	    }, {
	        header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementAdd.insuranceFee'),
	        dataIndex: 'insuranceFee'
	    }, {
	        header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementAdd.deliverFee'),
	        dataIndex: 'deliverFee'
	    }, {
	        header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementAdd.packagingFee'),
	        dataIndex: 'packagingFee'
	    }, {
	        header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementAdd.otherFee'),
	        dataIndex: 'otherFee'
	    }, {
	        header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementAdd.receiveMethod'),
	        dataIndex: 'receiveMethod',
	        renderer: function (value) {
	            //先去汽运提货方式词条中拿
	            var displayField = FossDataDictionary.rendererSubmitToDisplay(value, settlementDict.PICKUP_GOODS);
	            //如果汽运提货方式没拿到，则去空运词条中拿
	            if (value == displayField || Ext.isEmpty(displayField)) {
	                displayField = FossDataDictionary.rendererSubmitToDisplay(value, settlementDict.PICKUP_GOODS_AIR);
	            }
	            return displayField;
	        }
	    }, {
	        header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementAdd.paymentType'),
	        dataIndex: 'paymentType',
	        renderer: function (value) {
	            var displayField = FossDataDictionary.rendererSubmitToDisplay(value, settlementDict.SETTLEMENT__PAYMENT_TYPE);
	            return displayField;
	        }
	    }, {
	        header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.originalUnverifyAmount'),
	        dataIndex: 'unverifyAmount',
	        renderer: function (v, m, record) {
	            if (record.get('billParentType') == writeoff.STATEMENTDETAIL_PAYABLE || record.get('billParentType') == writeoff.STATEMENTDETAIL_DEPOSIT_RECEIVED) {
	                return '<span style="color:red">' + v + '</span>';
	            } else {
	                return v;
	            }
	        }
	    }, {
	        header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.totalAmount'),
	        dataIndex: 'amount',
	        renderer: function (v, m, record) {
	            if (record.get('billParentType') == writeoff.STATEMENTDETAIL_PAYABLE || record.get('billParentType') == writeoff.STATEMENTDETAIL_DEPOSIT_RECEIVED) {
	                return '<span style="color:red">' + v + '</span>';
	            } else {
	                return v;
	            }
	        }
	    }, {
	        header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementAdd.number'),
	        dataIndex: 'sourceBillNo'
	    }, {
	        header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementAdd.deliveryCustomerContact'),
	        dataIndex: 'deliveryCustomerContact'
	    }, {
	        header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.origSourceBillNo'),
	        dataIndex: 'origSourceBillNo'
	    }, {
	        header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementAdd.billParentType'),
	        dataIndex: 'billParentType',
	        renderer: function (value) {
	            var displayField = FossDataDictionary.rendererSubmitToDisplay(value, settlementDict.STATEMENT_OF_ACCOUNT_D__BILL_PARENT_TYPE);
	            return displayField;
	        }
	    }, {
	        header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.billType'),
	        dataIndex: 'billType',
	        renderer: function (v, m, record) {
	            return writeoff.customerStatementEdit.statementFormatBillType(v, record);
	        }
	    }, {
	        header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.customerCode'),
	        dataIndex: 'customerCode'
	    }, {
	        header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.customerName'),
	        dataIndex: 'customerName'
	    }, {
	        header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.verifyAmount'),
	        dataIndex: 'verifyAmount',
	        hidden: true,
	        renderer: function (v, m, record) {
	            if (record.get('billParentType') == writeoff.STATEMENTDETAIL_PAYABLE || record.get('billParentType') == writeoff.STATEMENTDETAIL_DEPOSIT_RECEIVED) {
	                return '<span style="color:red">' + v + '</span>';
	            } else {
	                return v;
	            }
	        }
	    }, {
	        header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.orgCode'),
	        dataIndex: 'orgCode'
	    }, {
	        header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.orgName'),
	        dataIndex: 'orgName'
	    }, {
	        header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.accountDate'),
	        dataIndex: 'accountDate',
	        renderer: function (value) {
	            if (value != null) {
	                return Ext.Date.format(new Date(value), 'Y-m-d');
	            } else {
	                return null;
	            }
	        }
	    }, {
	        header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementAdd.customerPickupOrgName'),
	        dataIndex: 'customerPickupOrgName'
	    }, {
	        header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementAdd.receiveCustomerName'),
	        dataIndex: 'receiveCustomerName'
	    }, {
	        header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementAdd.receiveCustomerCode'),
	        dataIndex: 'receiveCustomerCode'
	    }, {
	        header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementAdd.goodsName'),
	        dataIndex: 'goodsName'
	    }, {
	        header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementAdd.transportFee'),
	        dataIndex: 'transportFee'
	    }, {
	        header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementAdd.pickupFee'),
	        dataIndex: 'pickupFee'
	    }, {
	        header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementAdd.codFee'),
	        dataIndex: 'codFee'
	    }, {
	        header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementAdd.valueAddFee'),
	        dataIndex: 'valueAddFee'
	    }, {
	        header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementAdd.promotionsFee'),
	        dataIndex: 'promotionsFee'
	    }, {
	        header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementAdd.origOrgCode'),
	        dataIndex: 'origOrgCode'
	    }, {
	        header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementAdd.origOrgName'),
	        dataIndex: 'origOrgName'
	    }, {
	        header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementAdd.destOrgName'),
	        dataIndex: 'destOrgName'
	    }, {
	        header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementAdd.destOrgCode'),
	        dataIndex: 'destOrgCode'
	    }, {
            header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementAdd.deliveryCustomerCode'),
            dataIndex: 'deliveryCustomerCode'
        }, {
            header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementAdd.signDate'),
            dataIndex: 'signDate',
            renderer: function (value) {
                if (value != null) {
                    return Ext.Date.format(new Date(value), 'Y-m-d');
                } else {
                    return null;
                }
            }
        }, {
            header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementAdd.auditStatus'),
            dataIndex: 'auditStatus',
            renderer: function (value) {
                var displayField = FossDataDictionary.rendererSubmitToDisplay(value, settlementDict.BILL_RECEIVABLE__APPROVE_STATUS);
                return displayField;
            }
        }, {
            header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.notes'),
            dataIndex: 'notes',
            hidden: true
        }, {
            header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementAdd.isDelete'),
            dataIndex: 'isDelete',
            hidden: true
        }, {
            header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementAdd.statementBillNo'),
            dataIndex: 'statementBillNo',
            hidden: true
        }, {
            header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.disableTime'),
            dataIndex: 'disableTime',
            hidden: true,
            renderer: function (value) {
                if (value != null) {
                    return Ext.Date.format(new Date(value), 'Y-m-d');
                } else {
                    return null;
                }
            }
        }, {
            header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.createTime'),
            dataIndex: 'createTime',
            hidden: true,
            renderer: function (value) {
                if (value != null) {
                    return Ext.Date.format(new Date(value), 'Y-m-d');
                } else {
                    return null;
                }
            }
        }, {
            header: 'id',
            dataIndex: 'id',
            hidden: true
        }],
	initComponent:function(){
		var me = this;
		me.dockedItems = [{
	   		xtype: 'toolbar',
		    dock: 'top',
		    layout:'column',
			defaultType:'textfield',
		    defaults:{
				margin:'5 0 5 3',
				readOnly:true,
				labelWidth:60
			},		
		    items: [{//添加明细
				xtype:'button',
				text:writeoff.customerStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.addWoodenStatementD'),
				columnWidth:.1,
				hidden:!writeoff.customerStatementEdit.isPermission('/stl-web/writeoff/addCustomerStatementD.action'),
				handler:writeoff.customerStatementEdit.addCustomerStatementD
			},{//删除明细
				xtype:'button',
				text:writeoff.customerStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.delWoodenStatementD'),
				columnWidth:.1,
				hidden:!writeoff.customerStatementEdit.isPermission('/stl-web/writeoff/delCustomerStatementD.action'),
				handler:writeoff.customerStatementEdit.delCustomerStatementD
			},{//勾选删除
				xtype:'button',
				text:writeoff.customerStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.checkDelWoodenStatementD'),
				columnWidth:.1,
				hidden:!writeoff.customerStatementEdit.isPermission('/stl-web/writeoff/delCustomerStatementD.action'),
				handler:writeoff.customerStatementEdit.checkDelCustomerStatementD
			}]
		},{
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
			 }]
		}];
		me.callParent();
	}
});
/**
 * 删除所有明细
 */
writeoff.customerStatementEdit.removeAllStatementD = function(){
	//对账单明细窗口
	var statementEntryWindow = writeoff.customerStatementEdit.statementEntryWindow;
	//对账单明细FORM
	var statementEntryForm = statementEntryWindow.items.items[0].getForm();
	//对账单明细GRID
	var statementEntryGrid = statementEntryWindow.items.items[2];
	//对账单单号
	var statementBillNo = statementEntryForm.findField('statementBillNo').getValue();
	//勾选的单号
	var selections = statementEntryGrid.store.data;
	//对账单确认状态
	var confirmStatus = statementEntryForm.findField('confirmStatus').getValue();
	//对账单确认状态判断
	if(confirmStatus==writeoff.STATEMENTCONFIRMSTATUS_Y||confirmStatus==''){
		Ext.Msg.alert(writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.alert'),
			writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementEdit.confirmDelWooden'));
		return false;
	}
	//删除明细列表校验
  	if(selections.length==0){
  		Ext.Msg.alert(writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.alert'), 
  			writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementCommon.noData'));
		return false;
  	}
	Ext.MessageBox.show({
		title: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.alert'),
		msg: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementAdd.isDelete'),
		buttons: Ext.MessageBox.YESNO,
		fn: function(e){
		  	if(e=='yes'){
				//设置参数
				var searchParams = {
						'customerStatementVo.customerStatementDto.statementBillNo':statementBillNo
					};
				Ext.Ajax.request({
				    url: writeoff.realPath('delCustomerStatementD.action'),
				    method : 'POST',
				    params : searchParams,
				    success : function(response,options){
				    	var result = Ext.decode(response.responseText);
				    	//刷新对账单明细列表
				    	statementEntryGrid.store.load();
				    	//设置操作按钮
				    	writeoff.customerStatementEdit.operateButton();
				    	Ext.Msg.alert(writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.alert'),
							writeoff.customerStatementEdit.i18n('foss.stl.writeoff.woodenStatementAdd.delWoodenStatementDSuccess'));
				    },
					exception:function(response){
						var result = Ext.decode(response.responseText);	
						Ext.Msg.alert(writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.alert'),result.message);
					}
				});
		  	}else{
		  		return false;
		  	}
		}
	});
}
/**
 * 对账单明细操作按钮
 */
Ext.define('Foss.customerStatementEdit.OperateButtonPanel',{
	extend:'Ext.panel.Panel',
	layout:'column',
	defaultType:'button',
	defaults:{
		columnWidth:.1
	},
	items:[{
		xtype:'container',
		border:false,
		html:'&nbsp;',
		columnWidth:.25
	},{
		text:writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementEdit.confirm'),
		hidden:true,
		hidden:!writeoff.customerStatementEdit.isPermission('/stl-web/writeoff/confirmOrUnConfirmStatement.action'),
		handler:writeoff.customerStatementEdit.statementConfirm
	},{
		text:writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementEdit.unConfirm'),
		hidden:true,
		hidden:!writeoff.customerStatementEdit.isPermission('/stl-web/writeoff/confirmOrUnConfirmStatement.action'),
		handler:writeoff.customerStatementEdit.statementUnConfirm
	},{
		text:writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementEdit.repayment'),
		hidden:true,
		hidden:!writeoff.customerStatementEdit.isPermission('/stl-web/writeoff/customerToRepayment.action'),
		handler:writeoff.customerStatementEdit.repayment
	},{
		text:writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementEdit.exportExcel'),
		hidden:true,
		hidden:!writeoff.customerStatementEdit.isPermission('/stl-web/writeoff/customerExportXLS.action'),
		handler:writeoff.customerStatementEdit.exportExcel
	},{
		text:writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementEdit.removeAllStatementD'),
		hidden:true,
		hidden:!writeoff.customerStatementEdit.isPermission('/stl-web/writeoff/delCustomerStatementD.action'),
		handler:writeoff.customerStatementEdit.removeAllStatementD
	}]
});

/**
 * 对账单单据类型
 */
Ext.define('Foss.customerStatementEdit.BillTypeStore',{
	extend:'Ext.data.Store',
	fields:['billTypeCode','billTypeName'],
	data:{
		'items':[
			{billTypeCode:'CA',billTypeName:writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementCommon.customerStatement')},
			{billTypeCode:'YSWA',billTypeName:writeoff.customerStatementEdit.i18n('foss.stl.writeoff.woodenStatementAdd.woodenStatementMessage')}
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
/**
 * 统一结算 
 */
Ext.define('Foss.customerStatementEdit.unifiedSettlement',{
	extend:'Ext.data.Store',
	fields:['unifiedSettlementCode','unifiedSettlementName'],
	data:{
		'items':[
			{unifiedSettlementCode:'Y',unifiedSettlementName:writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.yes')},
			{unifiedSettlementCode:'N',unifiedSettlementName:writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.no')}
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
/**
 * 对账单基础信息
 */
Ext.define('Foss.customerStatementEdit.BaseInfo',{
	extend:'Ext.form.Panel',
	layout:'column',
	frame:true,
	defaultType:'textfield',
	layout:'column',
	defaults:{
		labelWidth:60,
		margin:'5 10 5 30',
		readOnly:true
	},
	items:[{
		fieldLabel:writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementAdd.billType'),
		name:'billType',
		xtype:'combo',
		labelWidth:75,
		columnWidth:.24,
		store:Ext.create('Foss.customerStatementEdit.BillTypeStore'), 
		queryModel:'local',
		displayField:'billTypeName',
		valueField:'billTypeCode'
	},{
		xtype:'datefield',
		fieldLabel:writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementAdd.createTime'),
		name:'createTime',
		labelWidth:75,
		format:'Y-m-d H:i:s',
		columnWidth:.28,
		value:new Date()
	},{
		fieldLabel:writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.customerCode'),
		name:'customerCode',
		labelWidth:75,
		columnWidth:.24
	},{
		fieldLabel:writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementAdd.confirmStatus'),
		name:'confirmStatus',
		labelWidth:75,
		xtype:'combo',
		store:FossDataDictionary.getDataDictionaryStore(settlementDict.STATEMENT_OF_ACCOUNT__CONFIRM_STATUS), 
		queryModel:'local',
		displayField:'valueName',
		valueField:'valueCode',
		columnWidth:.24,
		value:0
	},{
		fieldLabel:writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementAdd.periodBeginDate'),
		name:'periodBeginDate',
		labelWidth:75,
		xtype:'datefield',
		format:'Y-m-d H:i:s',
		columnWidth:.24
	},{
		fieldLabel:writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementAdd.companyName'),
		name:'companyName',
		labelWidth:75,
		columnWidth:.28
	},{
		fieldLabel:writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementAdd.companyCode'),
		name:'companyCode',
		labelWidth:75,
		hidden:true,
		columnWidth:.28
	},{
		fieldLabel:writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.customerName'),
		name:'customerName',
		labelWidth:75,
		columnWidth:.24
	},{
		fieldLabel:writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementAdd.confirmTime'),
		name:'confirmTime',
		labelWidth:75,
		xtype:'datefield',
		format:'Y-m-d H：i:s',
		columnWidth:.24
	},{
		fieldLabel:writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementAdd.periodEndDate'),
		name:'periodEndDate',
		labelWidth:75,
		xtype:'datefield',
		format:'Y-m-d H:i:s',
		columnWidth:.24,
		value:new Date()
	},{
		fieldLabel:writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementAdd.createOrgName'),
		name:'createOrgName',
		labelWidth:75,
		columnWidth:.28
	},{
		fieldLabel:writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementAdd.createOrgCode'),
		name:'createOrgCode',
		labelWidth:75,
		hidden:true,
		columnWidth:.28
	},{
		fieldLabel:writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementAdd.statementBillNo'),
		name:'statementBillNo',
		labelWidth:75,
		columnWidth:.24
	},{
		fieldLabel:'id',
		name:'id',
		columnWidth:.22,
		hidden:true
	},{
		xtype:'numberfield',
		fieldLabel:writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementEdit.versionNo'),
		name:'versionNo',
		columnWidth:.22,
		hidden:true
	},{
		fieldLabel:writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementAdd.unifiedCode'),
		labelWidth:85,
		name:'unifiedCode',
		columnWidth:.24
	},{//应收金额
		xtype:'numberfield',
		fieldLabel:writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.amount'),
		labelWidth:75,
		name:'periodAmount',
		columnWidth:.24
	},{//未核销金额
		xtype:'numberfield',
		fieldLabel:writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.unverifyAmount'),
		labelWidth:80,
		name:'unpaidAmount',
		columnWidth:.28
	},{//是否统一结算
        fieldLabel: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementAdd.unifiedSettlement'),
		name:'unifiedSettlement',
		xtype:'combo',
		labelWidth:85,
		columnWidth:.3,
		store:Ext.create('Foss.customerStatementEdit.unifiedSettlement'), 
		queryModel:'local',
		displayField:'unifiedSettlementName',
		valueField:'unifiedSettlementCode'
	}]
});
/**
 * 对账单明细窗体
 */
Ext.define('Foss.customerStatementEdit.StatementEntryWindow',{
	extend:'Ext.window.Window',
	width:stl.SCREENWIDTH*0.9,
	modal:true,
	constrainHeader: true,
	closeAction:'destory',
	items:[
	       Ext.create('Foss.customerStatementEdit.BaseInfo'),
	       Ext.create('Foss.customerStatementEdit.OperateButtonPanel'),
		   Ext.create('Foss.customerStatementEdit.CustomerStatementDGrid')
	]
});
/**
 * 对账单MODEL
 */
Ext.define('Foss.customerStatementEdit.GridModel', {
	extend : 'Ext.data.Model',
	fields : [{
        //客户编码
        name: 'customerCode'
    }, {
        //单据类型
        name: 'billType'
    }, {
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
        name: 'confirmStatus'
    }, {
        name: 'periodBeginDate',
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
        name: 'companyCode'
    }, {
        name: 'companyName'
    }, {
        name: 'customerName'
    }, {
        name: 'confirmTime',
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
        name: 'periodEndDate',
        type: 'date',
        convert: function (value) {
            if (value != null) {
                //此处一定要这样写，不能转化成字符串，如果转化成字符串，那么你getRecord拿到的也是字符串。
                var date = new Date(value);
                return date;
            } else {
                return null;
            }
        }
    }, {
        name: 'createOrgCode'
    }, {
        name: 'createUserName'
    }, {
        name: 'createOrgName'
    }, {
        name: 'unifiedCode'
    }, {
        name: 'statementBillNo'
    }, {
        name: 'companyAccountBankNo'
    }, {
        name: 'accountUserName'
    }, {
        name: 'bankBranchName'
    }, {
        name: 'unpaidAmount',
        type: 'double'
    }, {
        name: 'settleNum',
        type: 'int'
    }, {
        name: 'periodAmount',
        type: 'double'
    }, {
        name: 'periodRecAmount',
        type: 'double'
    }, {
        name: 'periodPayAmount',
        type: 'double'
    }, {
        name: 'periodUnverifyRecAmount',
        type: 'double'
    }, {
        name: 'periodUnverifyPayAmount',
        type: 'double'
    }, {
        name: 'notes'
    }, {
        name: 'id'
    }, {
        name: 'versionNo',
        type: 'short'
    }, {
        name: 'invoiceStatus'
    }, {
        name: 'applyInvoice'
    }, {
        //统一结算
        name: 'unifiedSettlement'
    }]
});
/**
 * 按客户查询对账单
 */
writeoff.customerStatementEdit.statementSelectByCust = function(){
	var form = this.up('form').getForm();
	//判断是否合法
	if(form.isValid()){
		//开始时间
		writeoff.customerStatementEdit.periodBeginDate = form.findField('periodBeginDate').getValue();
		//结束时间
		writeoff.customerStatementEdit.periodEndDate = form.findField('periodEndDate').getValue();
		//客户编码
		writeoff.customerStatementEdit.customerCode = form.findField('customerCode').getValue();
		//确认状态
		writeoff.customerStatementEdit.confirmStatus = form.findField('confirmStatus').getValue();
		//结清状态
		writeoff.customerStatementEdit.settleStatus = form.findField('settleStatus').getValue();
		//查询类型
		writeoff.customerStatementEdit.queryTabType = writeoff.STATEMENTQUERYTAB_BYCUSTOMER;
		//开始日期
		var periodBeginDate = writeoff.customerStatementEdit.periodBeginDate;
		//结束日期
		var periodEndDate = writeoff.customerStatementEdit.periodEndDate;
		//校验日期
		if(Ext.isEmpty(periodBeginDate)||Ext.isEmpty(periodEndDate)){
			Ext.Msg.alert(writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.alert'),
				writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementAdd.quryDateIsNullWarning'));
			return false;
		}
		//比较起始日期和结束日期
		var compareTwoDate = stl.compareTwoDate(periodBeginDate,periodEndDate);
		if(compareTwoDate>stl.DATELIMITDAYS_MONTH){
			Ext.Msg.alert(writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.alert'),
				writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementEdit.queryDateMaxLimit'));
			return false;
		}else if(compareTwoDate<1){
			Ext.Msg.alert(writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.alert'),
				writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.dateEndBeforeStatrtWarining'));
			return false;
		}
		//获取grid
		var grid = Ext.getCmp('T_writeoff-customerStatementEdit_content').getGrid();
		grid.store.loadPage(1,{
			callback: function(records, operation, success) {
				var rawData = grid.store.proxy.reader.rawData;
				if(!success){
					var result = Ext.decode(response.responseText);	
					Ext.Msg.alert(writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.alert'),result.message);
					return false;
				}
				//如果成功显示
				if(success){
					//对结果进行转化
					var result = Ext.decode(operation.response.responseText);  
					//判断查询结果
					if(Ext.isEmpty(result.customerStatementVo.customerStatementDto.customerStatementList) 
							||result.customerStatementVo.customerStatementDto.customerStatementList.length==0){
						Ext.Msg.alert(writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.alert'),
							writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.noResult'));
						return false;
					}
				}
		    }
		});
		
	}else{
		Ext.Msg.alert(writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.alert'),
			writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.validateFailAlert'));
		return false;
	}
}
/**
 * 按单号查询对账单
 */
writeoff.customerStatementEdit.statementSelectByNumber = function(){		
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
		 	Ext.Msg.alert(writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.alert'),
		 		writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.queryNosLimit'));
		 	return false;
		 }
	}else{
		Ext.Msg.alert(writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.alert'),
			writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.billNosIsNullWarning'));
		return false;
	}
	//当界面校验都通过后，才执行查询方法
	if(form.isValid()){
		writeoff.customerStatementEdit.queryTabType = writeoff.STATEMENTQUERYTAB_BYNUMBER;
		writeoff.customerStatementEdit.numbers = numbers;
		var grid = Ext.getCmp('T_writeoff-customerStatementEdit_content').getGrid();
		//查询后台
		grid.store.loadPage(1,{
			callback: function(records, operation, success) {
				var rawData = grid.store.proxy.reader.rawData;
				if(!success && ! rawData.isException){
					var result = Ext.decode(operation.response.responseText);	
					Ext.Msg.alert(writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.alert'),result.message);
				}
				if(success){
					//对结果进行转化
					var result = Ext.decode(operation.response.responseText);  
					//判断查询结果
					if(Ext.isEmpty(result.customerStatementVo.customerStatementDto.customerStatementList) 
							||result.customerStatementVo.customerStatementDto.customerStatementList.length==0){
						Ext.Msg.alert(writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.alert'),
							writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.noResult'));
						return false;
					}
				}
		    }
		});
	}else{
		Ext.Msg.alert(writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.alert'),
			writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.validateFailAlert'));
		return false;
	}	
};
/**
 * 对账单STORE
 */
Ext.define('Foss.customerStatementEdit.GridStore',{
	extend:'Ext.data.Store',
	model:'Foss.customerStatementEdit.GridModel',
	pageSize:50,
	proxy:{
		type:'ajax',
		url:writeoff.realPath('queryCustomerStatement.action'),
		actionMethods:'POST',
		reader:{
			type:'json',
			root:'customerStatementVo.customerStatementDto.customerStatementList',
			totalProperty:'totalCount'
		}
	},
	listeners:{
		'beforeLoad':function(store, operation, eOpts){
			var searchParams;
			if(writeoff.customerStatementEdit.queryTabType==writeoff.STATEMENTQUERYTAB_BYCUSTOMER){
				searchParams = {
					'customerStatementVo.customerStatementDto.periodBeginDate':writeoff.customerStatementEdit.periodBeginDate,	
					'customerStatementVo.customerStatementDto.periodEndDate':writeoff.customerStatementEdit.periodEndDate,	
					'customerStatementVo.customerStatementDto.customerCode':writeoff.customerStatementEdit.customerCode,
					'customerStatementVo.customerStatementDto.confirmStatus':writeoff.customerStatementEdit.confirmStatus,
					'customerStatementVo.customerStatementDto.settleStatus':writeoff.customerStatementEdit.settleStatus,
					'customerStatementVo.customerStatementDto.queryTabType':writeoff.customerStatementEdit.queryTabType
				};
			}else{
				var numbers = stl.splitToArray(writeoff.customerStatementEdit.numbers);
				searchParams = {
					'customerStatementVo.customerStatementDto.numbers':numbers,
					'customerStatementVo.customerStatementDto.queryTabType':writeoff.customerStatementEdit.queryTabType
				};
			}
			//设置查询条件
			Ext.apply(operation,{
				params :searchParams
			}); 
		}
	}
});

/**
 * 对账单GRID
 */
Ext.define('Foss.customerStatementEdit.Grid',{
	extend:'Ext.grid.Panel',
	title: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementEdit.statementBill'),
    bodyCls: 'autoHeight',
	cls: 'autoHeight',
	emptyText:writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.noResult'),
	frame:true,
	detailWin:null,
    store:Ext.create('Foss.customerStatementEdit.GridStore'),
    //selModel:Ext.create('Ext.selection.CheckboxModel'),
    height:500,
  	viewConfig:{
  		enableTextSelection : true//设置行可以选择，进而可以复制
  	},
    defaults:{
  		align:'center'
  	},
  	listeners : {
		'itemdblclick' : function(th, record) {
			//对账单明细
			var statementEntryWindow = writeoff.customerStatementEdit.statementEntryWindow;
			//对账单单号
			writeoff.customerStatementEdit.statementBillNo = record.data.statementBillNo;
			//获取选择的对账单数据
			var selection=Ext.getCmp('T_writeoff-customerStatementEdit_content').getGrid().getSelectionModel().getSelection();
			var me = this;
			var model = new Foss.customerStatementEdit.GridModel(selection[0].data);
			writeoff.customerStatementEdit.selection = selection;
			//对账单明细窗口
			var statementEntryWindow = writeoff.customerStatementEdit.statementEntryWindow;
			if(Ext.isEmpty(writeoff.customerStatementEdit.statementEntryWindow)){
				writeoff.customerStatementEdit.statementEntryWindow = Ext.create('Foss.customerStatementEdit.StatementEntryWindow');
				statementEntryWindow = writeoff.customerStatementEdit.statementEntryWindow;
			}
			//对账单操作按钮
			var operateFrom = statementEntryWindow.items.items[1];
			//确认按钮
			operateFrom.items.items[1].hide();
			//反确认按钮
			operateFrom.items.items[2].hide();
			//还款按钮
			operateFrom.items.items[3].hide();
			//导出按钮
			operateFrom.items.items[4].hide();
			//删除按钮
			operateFrom.items.items[5].hide();
			//设置对账单基础信息
			statementEntryWindow.items.items[0].loadRecord(model);
			statementEntryWindow.show();
			//查询明细
			statementEntryWindow.items.items[2].store.loadPage(1,{
				callback: function(records, operation, success) {
					if(!success){
						var result = Ext.decode(operation.response.responseText);	
						Ext.Msg.alert(writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.alert'),result.message);
						return false;
					}
					//如果成功显示
					if(success){
						//对结果进行转化
						var result = Ext.decode(operation.response.responseText);
						//判断查询结果
						if(Ext.isEmpty(result.customerStatementVo.customerStatementDto.customerStatementDList) 
								||result.customerStatementVo.customerStatementDto.customerStatementDList.length==0){
							Ext.Msg.alert(writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.alert'),
								writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.noResult'));
							return false;
						}
						//设置操作按钮
						writeoff.customerStatementEdit.operateButton();
					}
			    }
			});
		}
	},
  	columns: [{
		header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementAdd.statementBillNo'), 
		dataIndex: 'statementBillNo'
	},{
		header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.customerCode'),
		dataIndex: 'customerCode' 
	},{
		header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.customerName'), 
		dataIndex: 'customerName'
	},{ 
		header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementEdit.billType'),
		dataIndex: 'billType',
		renderer:function(value){
			var displayField = null;
			if (!Ext.isEmpty(value)) {
				if (value == "YSWA") {
					displayField = "代打木架对账单";
				} else if (value == "CA") {
					displayField = "客户对账单";
				}
			}
			return displayField;
		}
		/*renderer:function(value){
			var displayField = FossDataDictionary.rendererSubmitToDisplay (value,settlementDict.STATEMENT_OF_ACCOUNT__BILL_TYPE);
    		return displayField;
		}*/
	},{ 
		header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementEdit.statementStatus'),
		dataIndex: 'confirmStatus',
		renderer:function(value){
			var displayField = FossDataDictionary.rendererSubmitToDisplay (value,settlementDict.STATEMENT_OF_ACCOUNT__CONFIRM_STATUS);
    		return displayField;
		}
	},{ 
		header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementEdit.periodAmount'),
		dataIndex: 'periodAmount' 
	},{ 
		header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementEdit.paidAmount'),
		dataIndex: 'paidAmount',
		renderer:function(value,m,record){
			//声明默认值为0
			value = 0;
			//获取本期发生额
			var periodAmount = record.get('periodAmount');
			//获取本期未还金额
			var unpaidAmount = record.get('unpaidAmount');
			//如果本期发生金额小于等于0，则本期已还金额默认为0，反之为本期发生额-本期未还金额
			if(!Ext.isEmpty(periodAmount) && periodAmount>0){
				//value = periodAmount - unpaidAmount;
				value = stl.subtrAmountPoint(periodAmount,unpaidAmount);
			}
			//返回
			return value;
		}
	},{ 
		header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementEdit.unpaidAmount'),
		dataIndex: 'unpaidAmount'
	},{ 
		header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementEdit.periodStartDate'),
		dataIndex: 'periodBeginDate',
		renderer:function(value){
			if(value!=null){
				return Ext.Date.format(new Date(value), 'Y-m-d');
			}else{
				return null;
			}
		}
	},{ 
		header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementEdit.periodEndAmount'),
		dataIndex: 'periodEndDate',
		renderer:function(value){
			if(value!=null){
				return Ext.Date.format(new Date(value), 'Y-m-d');
			}else{
				return null;
			}
		}
	},{
		header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.companyCode'),
		dataIndex: 'companyCode'
	},{
		header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.companyName'),
		dataIndex: 'companyName'
	},{
		header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementAdd.confirmTime'),
		dataIndex: 'confirmTime',
		renderer:function(value){
			if(value!=null){
				return Ext.Date.format(new Date(value), 'Y-m-d');
			}else{
				return null;
			}
		}
	},{
		header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.orgCode'),
		dataIndex: 'createOrgCode'
	},{
		header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.orgName'),
		dataIndex: 'createOrgName'
	},{
		header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementAdd.unifiedCode'),
		dataIndex: 'unifiedCode'
	},{
		header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementEdit.companyAccountBankNo'),
		dataIndex: 'companyAccountBankNo'
	},{
		header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementAdd.accountUserName'),
		dataIndex: 'accountUserName'
	},{
		header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementEdit.bankBranchName'),
		dataIndex: 'bankBranchName'
	},{
		header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementAdd.settleNum'),
		dataIndex: 'settleNum'
	},{ 
		header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.createTime'),
		dataIndex: 'createTime',
		renderer:function(value){
			if(value!=null){
				return Ext.Date.format(new Date(value), 'Y-m-d');
			}else{
				return null;
			}
		}
	},{
		header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementCommon.billDescription'),
		dataIndex: 'notes'
	},{
		header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementEdit.invoiceStatus'),
		dataIndex: 'invoiceStatus',
		renderer:function(value){
			var displayField = null;
			if (!Ext.isEmpty(value)) {
				if (value == "AP") {
					displayField = "已申请";
				} else if (value == "NP") {
					displayField = "未申请";
				}
			}
			return displayField;
		}
	},{
		header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementEdit.applyInvoice'),
		dataIndex: 'applyInvoice',
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
		header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementAdd.unifiedSettlement'),
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
		header:'id',
		dataIndex:'id',
		hidden:true
	},{
		header: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.versionNo'),
		dataIndex:'versionNo',
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
			 }]
		}];
		me.callParent();
	}
});

/**
 * 结账状态store
 */
Ext.define('Foss.statementbill.SettleStatusStore',{
	extend:'Ext.data.Store',
	fields:['name','value'],
	data:{
		'items':[
				{name:writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementEdit.all'),value:''},
				{name:writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementEdit.settled'),value:writeoff.SETTLESTATUS_Y},
				{name:writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementEdit.unSettle'),value:writeoff.SETTLESTATUS_N}
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

/**
 * 对账单查询页签
 */
Ext.define('Foss.customerStatementEdit.QueryTab',{
	extend:'Ext.tab.Panel',
	frame:false,
	bodyCls: 'autoHeight',
	cls: 'innerTabPanel',
	height:250,
	items:[{
       	title: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.woodenStatementByCustomer'),
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
		    	fieldLabel:writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementAdd.periodBeginDate'),
		    	allowBlank:false,
		    	name:'periodBeginDate',
		    	columnWidth: .3,
		    	format:'Y-m-d',
		    	value:stl.getTargetDate(new Date(),-1)
		    },{
		    	xtype:'datefield',
		    	fieldLabel:writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementAdd.periodEndDate'),
		    	allowBlank:false,
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
	        	fieldLabel:writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.customerDetial'),
	        	columnWidth:.3
	        },{
		   	 	xtype: 'combobox',
				name:'confirmStatus',
		        fieldLabel: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementAdd.confirmStatus'),
				store:FossDataDictionary.getDataDictionaryStore(settlementDict.STATEMENT_OF_ACCOUNT__CONFIRM_STATUS,null,{
					 'valueCode': '',
               		 'valueName': writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementEdit.all')
				}),
				queryModel:'local',
				displayField:'valueName',
				valueField:'valueCode',
				value:'',
		        columnWidth:.3,
		        forceSelection :true,
		        listeners:{
		        	change:stl.comboSelsct
		        }
		    },{
				xtype: 'combobox',
				name:'settleStatus',
		        fieldLabel: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementEdit.settleStatus'),
				store:Ext.create('Foss.statementbill.SettleStatusStore'),
				queryModel:'local',
				displayField:'name',
				valueField:'value',
				value:'',
		        columnWidth:.3,
		        forceSelection :true,
		        listeners:{
		        	change:stl.comboSelsct
		        }
		    },{
				border: 1,
				xtype:'container',
				columnWidth:1,
				defaultType:'button',
				layout:'column',
				items:[{
					text:writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.reset'),
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
					text:writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.query'),
					cls:'yellow_button',
					columnWidth:.08,
					handler:writeoff.customerStatementEdit.statementSelectByCust
				}]
		    }]
	    }]
	},{
        title: writeoff.customerStatementEdit.i18n('foss.stl.writeoff.woodenStatementAdd.queryByNumber'),
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
				fieldLabel:writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementAdd.number'),
				emptyText:writeoff.customerStatementEdit.i18n('foss.stl.writeoff.statementEdit.queryByStatementRegex'),
				allowBlank:false,
				columnWidth:.65,
				regex:/^((DZ)?[0-9]{7,10}[;,])*((DZ)?[0-9]{7,10}[;,]?)$/i,
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
						html:'<span style="color:red;">'+writeoff.customerStatementEdit.i18n('foss.stl.writeoff.woodenStatementEdit.queryByStatementBillNo')+'</span>'
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
					text:writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.reset'),
					columnWidth:.075,
					handler:function(){
						this.up('form').getForm().reset();
					}
				},{
					xtype:'container',
					border:false,
					html:'&nbsp;',
					columnWidth:.5
				},{
					text:writeoff.customerStatementEdit.i18n('foss.stl.writeoff.common.query'),
					cls:'yellow_button',
					columnWidth:.075,
					handler:writeoff.customerStatementEdit.statementSelectByNumber
				}]
        	}]
        }]
    }]
});

Ext.onReady(function() {
	Ext.Ajax.timeout = 60000*30;
	//查询表单
	var queryTab = Ext.create('Foss.customerStatementEdit.QueryTab');
	//查询结果列表
	var grid = Ext.create('Foss.customerStatementEdit.Grid');
	//创建明细窗口
	if(Ext.isEmpty(writeoff.customerStatementEdit.statementEntryWindow)){
		writeoff.customerStatementEdit.statementEntryWindow = Ext.create('Foss.customerStatementEdit.StatementEntryWindow');
	}
	//创建panel
	Ext.create('Ext.panel.Panel',{
		id: 'T_writeoff-customerStatementEdit_content',
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
		renderTo: 'T_writeoff-customerStatementEdit-body'
	});
});