//应收单查询model
Ext.define('Foss.Stlyuscyings.ReceivableBillModel',{
	extend:'Ext.data.Model',
	fields:[{
		name:'receivableNo'
	},{
		name:'waybillNo'
	},{
		name:'receivableOrgName'
	},{
		name:'customerCode'
	},{
		name:'customerName'
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
		name:'billType'
	},{
		name:'active'
	},{
		name:'isRedBack'
	},{
		name:'isInit'
	},{
		name:'productCode'
	},{
		name:'accountDate'
	},{
		name:'businessDate'
	},{
		name:'createTime'
	},{
		name:'conrevenDate'
	},{
		name:'createUserName'
	}]
});

//应收单查询Store				
Ext.define('Foss.Stlyuscyings.ReceivableBillStore',{
	extend:'Ext.data.Store',
	model:'Foss.Stlyuscyings.ReceivableBillModel'
});

//应付单查询model
Ext.define('Foss.Stlyuscyings.PayableBillModel',{
	extend:'Ext.data.Model',
	fields:[{
		name:'payableNo'
	},{
		name:'waybillNo'
	},{
		name:'payableOrgName'
	},{
		name:'customerCode'
	},{
		name:'customerName'
	},{
		name:'amount',
		type:'long'
	},{
		name:'verifyAmount',
		type:'long'
	},{
		name:'paymentAmount',
		type:'long'
	},
	{
		name:'unverifyAmount',
		type:'long'
	},{
		name:'billType'
	},{
		name:'paymentStatus'
	},{
		name:'active'
	},{
		name:'isRedBack'
	},{
		name:'isInit'
	},{
		name:'productCode'
	},{
		name:'accountDate'
	},{
		name:'businessDate'
	},{
		name:'createTime'
	},{
		name:'createUserName'
	}]
});

//应付单查询Store				
Ext.define('Foss.Stlyuscyings.PayableBillStore',{
	extend:'Ext.data.Store',
	model:'Foss.Stlyuscyings.PayableBillModel'
});

//还款单查询model
Ext.define('Foss.Stlyuscyings.RepaymentBillModel',{
	extend:'Ext.data.Model',
	fields:[{
		name:'repaymentNo'
	},{
		name:'statementBillNo'
	},{
		name:'collectionOrgCode'
	},{
		name:'collectionOrgName'
	},{
		name:'customerCode'
	},{
		name:'customerName'
	},{
		name:'amount',
		type:'long'
	},{
		name:'trueAmount',
		type:'long'
	},{
		name:'bverifyAmount',
		type:'long'
	},{
		name:'active'
	},{
		name:'isRedBack'
	},{
		name:'auditStatus'
	},{
		name:'paymentType'
	},{
		name:'businessDate'
	},{
		name:'accountDate'
	},{
		name:'notes'
	},{
		name:'createTime'
	},{
		name:'createUserName'
	}]
});

//还款单查询Store				
Ext.define('Foss.Stlyuscyings.RepaymentBillStore',{
	extend:'Ext.data.Store',
	model:'Foss.Stlyuscyings.RepaymentBillModel'
});

//现金收款单查询model
Ext.define('Foss.Stlyuscyings.CashCollectionBillModel',{
	extend:'Ext.data.Model',
	fields:[{
		name:'waybillNo'
	},{
		name:'collectionOrgName'
	},{
		name:'generatingOrgName'
	},{
		name:'customerCode'
	},{
		name:'customerName'
	},{
		name:'status'
	},{
		name:'amount',
		type:'long'
	},{
		name:'paymentType'
	},{
		name:'productCode'
	},{
		name:'active'
	},{
		name:'isRedBack'
	},{
		name:'isInit'
	},{
		name:'cashConfirmUserName'
	},{
		name:'businessDate'
	},{
		name:'accountDate'
	},{
		name:'notes'
	},{
		name:'createTime'
	},{
		name:'createUserName'
	}]
});

//现金收款单查询Store				
Ext.define('Foss.Stlyuscyings.CashCollectionBillStore',{
	extend:'Ext.data.Store',
	model:'Foss.Stlyuscyings.CashCollectionBillModel'
});

//付款单单查询model
Ext.define('Foss.Stlyuscyings.PaymentBillModel',{
	extend:'Ext.data.Model',
	fields:[{
		name:'paymentNo'
	},{
		name:'applyWorkFlowNo'
	},{
		name:'paymentOrgCode'
	},{
		name:'paymentOrgName'
	},{
		name:'customerCode'
	},{
		name:'customerName'
	},{
		name:'amount',
		type:'long'
	},{
		name:'auditStatus'
	},{
		name:'remitStatus'
	},{
		name:'sourceBillType'
	},{
		name:'remitStatus'
	},{
		name:'paymentType'
	},{
		name:'active'
	},{
		name:'isRedBack'
	},{
		name:'payType'
	},{
		name:'businessDate'
	},{
		name:'accountDate'
	},{
		name:'notes'
	},{
		name:'createTime'
	},{
		name:'createUserName'
	}]
});

//付款单查询Store				
Ext.define('Foss.Stlyuscyings.PaymentBillStore',{
	extend:'Ext.data.Store',
	model:'Foss.Stlyuscyings.PaymentBillModel'
});

//核销单查询model
Ext.define('Foss.Stlyuscyings.WriteoffBillModel',{
	frame:true,
	extend:'Ext.data.Model',
	fields:[{
		name:'writeoffBillNo'
	},{
		name:'beginNo'
	},{
		name:'endNo'
	},{
		name:'writeoffType'
	},{
		name:'amount',
		type:'long'
	},{
		name:'active'
	},{
		name:'isRedBack'
	},{
		name:'isInit'
	},{
		name:'orgName'
	},{
		name:'customerCode'
	},{
		name:'customerName'
	},{
		name:'writeoffTime'
	},{
		name:'accountDate'
	},{
		name:'notes'
	},{
		name:'createUserName'
	}]
});

//核销单查询Store				
Ext.define('Foss.Stlyuscyings.WriteoffBillStore',{
	extend:'Ext.data.Store',
	model:'Foss.Stlyuscyings.WriteoffBillModel'
});

//第一个Tab:按运单单号查询
Ext.define('Foss.Stlyuscyings.StlyuscyingsReceivablePaymentBillForm', {
	extend:'Ext.form.Panel',
	frame:true,
	bodyCls: 'autoHeight',
	cls: 'innerTabPanel',
	title: consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.common.queryByWaybillNo'),
	tabConfig: {
		width: 120
	},
	height :210,
	layout:{
			type :'table',
			columns :3
		},
	defaults:{
    	margin:'10 5 0 5',
    	labelWidth:85
	},
	items : [{
 	 xtype: 'textarea',
	 autoScroll:true,
	 emptyText: consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.queryReceivablePaymentBill.billNosEmptyText'),
	 regex:/^([0-9]{7,14}[;,])*([0-9]{7,14}[;,]?)$/i,//329757-放开运单号是14位的校验
	 regexText: consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.queryReceivablePaymentBill.billNosRegexText'),
     fieldLabel: consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.common.waybillNo'),
     name: 'billNos',
     height : 100,
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
		text:consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.common.reset'),
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
		text:consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.common.query'),
		cls:'yellow_button',
		columnWidth:.1,
		handler:function(){		
			var wayBillNosArray =[];					
			var billNos = this.up('form').getForm().findField('billNos').getValue();
			var me = this;
			if(Ext.String.trim(billNos)!=null && Ext.String.trim(billNos)!=''){
				 var reg = /[,;]/;  
				 array = billNos.split(reg);
				 for(var i=0;i<array.length;i++){
				 	if(Ext.String.trim(array[i])!=''){
				 		wayBillNosArray.push(array[i]);	
				 	}
				 }
			}else{
				Ext.Msg.alert(consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.common.warmTips'),consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.queryReceivablePaymentBill.billNosInputIsNotNull'));
				return false;
			}
			
			if(wayBillNosArray.length>10){
				Ext.Msg.alert(consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.common.warmTips'),consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.queryReceivablePaymentBill.billNosInoutIsToMore'));
				return false;
			}
			var billReceivablePaymentVO = new Object();
			billReceivablePaymentVO.wayBillNosArray = wayBillNosArray;
			
			var params = new Object();
			var form = this.up('form').getForm();
			if (form.isValid()) {		
				//设置该按钮灰掉
				me.disable(false);
				//30秒后自动解除灰掉效果
				setTimeout(function() {
					me.enable(true);
				}, 30000);
			var tab = Ext.getCmp('T_consumer-queryReceivablePaymentBill_content').getStlyuscyingsReceivablePaymentBillGridTab();
			var activeTab = tab.getActiveTab().name;
			if(activeTab == 'receivableBillTab'){
				Ext.Ajax.request({
					url:consumer.realPath('queryReceivableBillByBillNo.action'),
					params:{
						"billReceivablePaymentVO.wayBillNosArray" : wayBillNosArray
					},
					method:'post',
					success:function(response){
						var result = Ext.decode(response.responseText);
						var receivableBillStore = Ext.getCmp('Foss_ReceivablePaymentBill_ReceivableBillQueryInfoGrid_Id').store;
						
						//点击查询的时候把之前的查询结果清空
						receivableBillStore.removeAll();
						
						//如果List不为空就加载数据到页面
						if(!Ext.isEmpty(result.billReceivablePaymentVO.billReceivableList)){
							receivableBillStore.loadData(result.billReceivablePaymentVO.billReceivableList);
						}
								
						//动态给GRID设置值
						Ext.getCmp('Foss_ReceivablePaymentBill_ReceivableBillQueryInfoGrid_TotalRecordsInDB_Id').setValue(result.billReceivablePaymentVO.totalRecordsInDB);
						Ext.getCmp('Foss_ReceivablePaymentBill_ReceivableBillQueryInfoGrid_TotalAmount_Id').setValue(result.billReceivablePaymentVO.totalAmount);
						Ext.getCmp('Foss_ReceivablePaymentBill_ReceivableBillQueryInfoGrid_TotalVerifyAmount_Id').setValue(result.billReceivablePaymentVO.totalVerifyAmount);
						Ext.getCmp('Foss_ReceivablePaymentBill_ReceivableBillQueryInfoGrid_TotalUnverifyAmount_Id').setValue(result.billReceivablePaymentVO.totalUnverifyAmount);
						me.enable(true);
					},
					failure:function(response){
						var result = Ext.decode(response.responseText);
						me.enable(true);
					}
				});	
			}
			if(activeTab == 'payableBillTab'){
				Ext.Ajax.request({
					url:consumer.realPath('queryPayableBillByBillNo.action'),
					params:{
						"billReceivablePaymentVO.wayBillNosArray" : wayBillNosArray
					},
					method:'post',
					success:function(response){
						var result = Ext.decode(response.responseText);
						var payableBillStore = Ext.getCmp('Foss_ReceivablePaymentBill_PayableBillQueryInfoGrid_Id').store;		
						
						//点击查询的时候把之前的查询结果清空
						payableBillStore.removeAll();
						
						//如果List不为空就加载数据到页面
						if(!Ext.isEmpty(result.billReceivablePaymentVO.billPayableList)){
							payableBillStore.loadData(result.billReceivablePaymentVO.billPayableList);
							}
														
						//动态给GRID设置值
						Ext.getCmp('Foss_ReceivablePaymentBill_PayableBillQueryInfoGrid_TotalRecordsInDB_Id').setValue(result.billReceivablePaymentVO.totalRecordsInDB);
						Ext.getCmp('Foss_ReceivablePaymentBill_PayableBillQueryInfoGrid_TotalAmount_Id').setValue(result.billReceivablePaymentVO.totalAmount);
						Ext.getCmp('Foss_ReceivablePaymentBill_PayableBillQueryInfoGrid_TotalVerifyAmount_Id').setValue(result.billReceivablePaymentVO.totalVerifyAmount);						
						Ext.getCmp('Foss_ReceivablePaymentBill_PayableBillQueryInfoGrid_TotalUnverifyAmount_Id').setValue(result.billReceivablePaymentVO.totalUnverifyAmount);
						me.enable(true);
					},
					failure:function(response){
						var result = Ext.decode(response.responseText);
						me.enable(true);
					}
				});	
			}
			if(activeTab == 'repaymentBillTab'){
				Ext.Ajax.request({
					url:consumer.realPath('queryRepaymentBillByBillNo.action'),
					params:{
						"billReceivablePaymentVO.wayBillNosArray" : wayBillNosArray
					},
					method:'post',
					success:function(response){
						var result = Ext.decode(response.responseText);
						var repaymentBillStore = Ext.getCmp('Foss_Stlyuscyings_RepaymentBillQueryInfoGrid_Id').store;						
						
						//点击查询的时候把之前的查询结果清空
						repaymentBillStore.removeAll();
						
						//如果List不为空就加载数据到页面
						if(!Ext.isEmpty(result.billReceivablePaymentVO.billRepaymentList)){
							repaymentBillStore.loadData(result.billReceivablePaymentVO.billRepaymentList);
							}
						
						//动态给GRID设置值
						Ext.getCmp('Foss_ReceivablePaymentBill_RepaymentBillQueryInfoGrid_TotalRecordsInDB_Id').setValue(result.billReceivablePaymentVO.totalRecordsInDB);
						Ext.getCmp('Foss_ReceivablePaymentBill_RepaymentBillQueryInfoGrid_TotalAmount_Id').setValue(result.billReceivablePaymentVO.totalAmount);
						me.enable(true);
					},
					failure:function(response){
						var result = Ext.decode(response.responseText);
						me.enable(true);
					}
				});	
			}
			if(activeTab == 'cashCollectionBillTab'){
				Ext.Ajax.request({
					url:consumer.realPath('queryCashCollectionBillByBillNo.action'),
					params:{
						"billReceivablePaymentVO.wayBillNosArray" : wayBillNosArray
					},
					method:'post',
					success:function(response){
						var result = Ext.decode(response.responseText);
						var cashCollectionBillStore = Ext.getCmp('Foss_Stlyuscyings_CashCollectionBillQueryInfoGrid_Id').store;
						
						//点击查询的时候把之前的查询结果清空
						cashCollectionBillStore.removeAll();
						
						//如果List不为空就加载数据到页面
						if(!Ext.isEmpty(result.billReceivablePaymentVO.billCashCollectionList)){
							cashCollectionBillStore.loadData(result.billReceivablePaymentVO.billCashCollectionList);
							}
												
						//动态给GRID设置值
						Ext.getCmp('Foss_ReceivablePaymentBill_CashCollectionBillQueryInfoGrid_TotalRecordsInDB_Id').setValue(result.billReceivablePaymentVO.totalRecordsInDB);
						Ext.getCmp('Foss_ReceivablePaymentBill_CashCollectionBillQueryInfoGrid_TotalAmount_Id').setValue(result.billReceivablePaymentVO.totalAmount);
						me.enable(true);
					},
					failure:function(response){
						var result = Ext.decode(response.responseText);
						me.enable(true);
					}
				});	
			}
			if(activeTab == 'paymentBillTab'){
				Ext.Ajax.request({
					url:consumer.realPath('queryPaymentBillByBillNo.action'),
					params:{
						"billReceivablePaymentVO.wayBillNosArray" : wayBillNosArray
					},
					method:'post',
					success:function(response){
						var result = Ext.decode(response.responseText);
						var paymentBillStore = Ext.getCmp('Foss_Stlyuscyings_PaymentBillQueryInfoGrid_Id').store;
						
						//点击查询的时候把之前的查询结果清空
						paymentBillStore.removeAll();
						
						//如果List不为空就加载数据到页面
						if(!Ext.isEmpty(result.billReceivablePaymentVO.billPaymentList)){
							paymentBillStore.loadData(result.billReceivablePaymentVO.billPaymentList);
							}
										
						Ext.getCmp('Foss_ReceivablePaymentBill_PaymentBillQueryInfoGrid_TotalRecordsInDB_Id').setValue(result.billReceivablePaymentVO.totalRecordsInDB);
						Ext.getCmp('Foss_ReceivablePaymentBill_PaymentBillQueryInfoGrid_TotalAmount_Id').setValue(result.billReceivablePaymentVO.totalAmount);
						me.enable(true);
					},
					failure:function(response){
						var result = Ext.decode(response.responseText);
						me.enable(true);
					}
				});	
			}
			if(activeTab == 'writeoffBillTab'){
				Ext.Ajax.request({
					url:consumer.realPath('queryWriteoffBillByBillNo.action'),
					params:{
						"billReceivablePaymentVO.wayBillNosArray" : wayBillNosArray
					},
					method:'post',
					success:function(response){
						var result = Ext.decode(response.responseText);
						var writeoffBillStore = Ext.getCmp('Foss_Stlyuscyings_WriteoffBillQueryInfoGrid_Id').store;
						
						//点击查询的时候把之前的查询结果清空
						writeoffBillStore.removeAll();
						
						//如果List不为空就加载数据到页面
						if(!Ext.isEmpty(result.billReceivablePaymentVO.billWriteoffList)){
							writeoffBillStore.loadData(result.billReceivablePaymentVO.billWriteoffList);
							}
										
						//动态给GRID设置值
						Ext.getCmp('Foss_ReceivablePaymentBill_WriteoffBillQueryInfoGrid_TotalRecordsInDB_Id').setValue(result.billReceivablePaymentVO.totalRecordsInDB);
						Ext.getCmp('Foss_ReceivablePaymentBill_WriteoffBillQueryInfoGrid_TotalAmount_Id').setValue(result.billReceivablePaymentVO.totalAmount);
						me.enable(true);
					},
					failure:function(response){
						var result = Ext.decode(response.responseText);
						me.enable(true);
					}
				});	
			}				
		}else{
			Ext.Msg.alert(consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.common.warmTips'),consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.common.validateFailAlert'));
		}}
	}]
}]
});


//应收单查询列表
Ext.define('Foss.ReceivablePaymentBill.ReceivableBillQueryInfoGrid',{
	extend:'Ext.grid.Panel',
    title: consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.queryReceivablePaymentBill.billQueryInfoGridTitle'),
    emptyText: consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.common.emptyText'),
	frame:true,
	height:420,
	sortableColumns: false,
	autoScroll:true,
	viewConfig: {         
		enableTextSelection: true       
	}, 
	selModel:Ext.create('Ext.selection.CheckboxModel'),
	columns:[{
		header: consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.common.receivableNo'),
		dataIndex: 'receivableNo'
	},{
		header: consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.common.waybillNo'),
		dataIndex: 'waybillNo'
	},{
		header: consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.common.OrgName'),
		dataIndex: 'receivableOrgName'
	},{
		header: consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.common.customerCode'),
		dataIndex: 'customerCode'		
	},{
		header: consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.common.customerName'),
		dataIndex: 'customerName'
	},{
		header: consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.common.amount'),
		dataIndex: 'amount',
		xtype: 'numbercolumn', 
		format:'0,0.00',
		align:'right'
	},{
		header: consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.common.verifyAmount'),
		dataIndex: 'verifyAmount',
		xtype: 'numbercolumn', 
		format:'0,0.00',
		align:'right'
	},{
		header: consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.common.unverifyAmount'),
		dataIndex: 'unverifyAmount',
		xtype: 'numbercolumn', 
		format:'0,0.00',
		align:'right'
	},{
		header: consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.queryReceivablePaymentBill.billType'),
		dataIndex: 'billType',
		renderer:function(value){
			var displayField = FossDataDictionary. rendererSubmitToDisplay (value,'BILL_RECEIVABLE__BILL_TYPE');
    		return displayField;
		}
	},{
		header: consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.common.active'),
		dataIndex: 'active',
		renderer:function(value){
			var displayField = FossDataDictionary.rendererSubmitToDisplay(value,settlementDict.FOSS_ACTIVE);
    		return displayField;
		}
	},{
		header: consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.common.isRedBack'),
		dataIndex: 'isRedBack',
		renderer:function(value){
			var displayField = FossDataDictionary. rendererSubmitToDisplay (value,'SETTLEMENT__IS_RED_BACK');
    		return displayField;
		}
	},{
		header: consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.common.isInit'),
		dataIndex:'isInit',
		renderer:function(value){
			if(value == 'Y'){
				return consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.common.yes');
			}else if(value == 'N'){
				return consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.common.no');
			}else{
				return value;
			}
		}
	},{
		header: consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.queryReceivablePaymentBill.productCode'),
		dataIndex: 'productCode',
		renderer:function(value){
			return Foss.pkp.ProductData.rendererSubmitToDisplay(value);
		}
	},{
		header: consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.common.accountDate'),
		dataIndex: 'accountDate',
		type:'date',
		renderer:function(value){
			if(value!=null){
				return Ext.Date.format(new Date(value), 'Y-m-d');
			}else{
				return value;
			}
		}	
	},{
		header: consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.common.businessDate'),
		dataIndex: 'businessDate',
		type:'date',
    	renderer:function(value){
    		if(value!=null){
    			return Ext.Date.format(new Date(value), 'Y-m-d');
    		}else{
    			return value;
    		}
    	}
},{
		header: consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.common.createTime'),
		dataIndex: 'createTime',
		type:'date',
	    renderer:function(value){
		if(value!=null){
			return Ext.Date.format(new Date(value), 'Y-m-d');
		}else{
			return value;
		}
	}				
	},{
		header: consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.queryReceivablePaymentBill.conrevenDate'),
		dataIndex: 'conrevenDate',
		type:'date',
	    renderer:function(value){
		if(value!=null){
			return Ext.Date.format(new Date(value), 'Y-m-d');
		}else{
			return value;
		}
	}		
	},{
		header: consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.common.createUserName'),
		dataIndex: 'createUserName'				
	}]
});

//应付单查询列表
Ext.define('Foss.Stlyuscyings.PayableBillQueryInfoGrid',{
	extend:'Ext.grid.Panel',
	frame:true,
	title: consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.queryReceivablePaymentBill.billQueryInfoGridTitle'),
	emptyText: consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.common.emptyText'),
	height:420,
	sortableColumns: false,
	viewConfig: {         
		enableTextSelection: true       
	}, 
	selModel:Ext.create('Ext.selection.CheckboxModel'),
	columns:[{
		header: consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.common.payableNo'),
		dataIndex: 'payableNo'
	},{
		header: consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.common.waybillNo'),
		dataIndex: 'waybillNo'
	},{
		header: consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.common.OrgName'),
		dataIndex: 'payableOrgName'
	},{
		header: consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.common.customerCode'),
		dataIndex: 'customerCode'		
	},{
		header: consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.common.customerName'),
		dataIndex: 'customerName'
	},{
		header: consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.common.amount'),
		dataIndex: 'amount',
		xtype: 'numbercolumn', 
		format:'0,0.00',
		align:'right'
	},{
		header: consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.common.verifyAmount'),
		dataIndex: 'verifyAmount',
		xtype: 'numbercolumn', 
		format:'0,0.00',
		align:'right'
	},{
		header: consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.common.unverifyAmount'),
		dataIndex: 'unverifyAmount',
		xtype: 'numbercolumn', 
		format:'0,0.00',
		align:'right'
	},
	{
		header: consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.queryReceivablePaymentBill.paymentAmount'),
		dataIndex: 'paymentAmount',
		xtype: 'numbercolumn', 
		format:'0,0.00',
		align:'right'
	},
	{
		header: consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.queryReceivablePaymentBill.billType'),
		dataIndex: 'billType',
		renderer:function(value){
			var displayField = FossDataDictionary. rendererSubmitToDisplay (value,'BILL_PAYABLE__BILL_TYPE');
    		return displayField;
		}
	},{
		header: consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.queryReceivablePaymentBill.paymentStatus'),
		dataIndex: 'paymentStatus',
		renderer:function(value){
			var displayField = FossDataDictionary. rendererSubmitToDisplay (value,'BILL_PAYABLE__PAYMENT_STATUS');
    		return displayField;
		}
	},{
		header: consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.common.active'),
		dataIndex: 'active',
		renderer:function(value){
			var displayField = FossDataDictionary.rendererSubmitToDisplay(value,settlementDict.FOSS_ACTIVE);
    		return displayField;
		}
	},{
		header: consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.common.isRedBack'),
		dataIndex: 'isRedBack',
		renderer:function(value){
			var displayField = FossDataDictionary. rendererSubmitToDisplay (value,'SETTLEMENT__IS_RED_BACK');
    		return displayField;
		}
	},{
		header: consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.common.isInit'),
		dataIndex: 'isInit',
		renderer:function(value){
			if(value == 'Y'){
				return consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.common.yes');
			}else if(value == 'N'){
				return consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.common.no');
			}else{
				return value;
			}
		}
	},{
		header: consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.queryReceivablePaymentBill.productCode'),
		dataIndex: 'productCode',
		renderer:function(value){
			return Foss.pkp.ProductData.rendererSubmitToDisplay(value);
		}
	},{
		header: consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.common.accountDate'),
		dataIndex: 'accountDate',
		type:'date',
	    renderer:function(value){
		if(value!=null){
			return Ext.Date.format(new Date(value), 'Y-m-d');
		}else{
			return value;
		}
	}		
	},{
		header: consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.common.businessDate'),
		dataIndex: 'businessDate',
		type:'date',
	    renderer:function(value){
		if(value!=null){
			return Ext.Date.format(new Date(value), 'Y-m-d');
		}else{
			return value;
		}
	}		
	},{
		header: consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.common.createTime'),
		dataIndex: 'createTime',
		type:'date',
	    renderer:function(value){
		if(value!=null){
			return Ext.Date.format(new Date(value), 'Y-m-d');
		}else{
			return value;
		}
	}				
	},{
		header: consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.common.createUserName'),
		dataIndex: 'createUserName'				
	}]
});


//还款单查询列表
Ext.define('Foss.Stlyuscyings.RepaymentBillQueryInfoGrid',{
	extend:'Ext.grid.Panel',
	frame:true,
	title: consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.queryReceivablePaymentBill.billQueryInfoGridTitle'),
	emptyText: consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.common.emptyText'),
	height:420,
	sortableColumns: false,
	viewConfig: {         
		enableTextSelection: true       
	}, 
	selModel:Ext.create('Ext.selection.CheckboxModel'),
	columns:[{
		header:  consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.common.repaymentNo'),
		dataIndex: 'repaymentNo'
	},{
		header:  consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.queryReceivablePaymentBill.statementBillNo'),
		dataIndex: 'statementBillNo'
	},{
		header:  consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.queryReceivablePaymentBill.collectionOrgCodeHK'),
		dataIndex: 'collectionOrgCode'
	},{
		header:  consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.queryReceivablePaymentBill.collectionOrgNameHK'),
		dataIndex: 'collectionOrgName'		
	},{
		header: consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.common.customerCode'),
		dataIndex: 'customerCode'
	},{
		header: consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.common.customerName'),
		dataIndex: 'customerName'		
	},{
		header: consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.common.amount'),
		dataIndex: 'amount',
		xtype: 'numbercolumn', 
		format:'0,0.00',
		align:'right'
	},{
		header: consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.queryReceivablePaymentBill.trueAmount'),
		dataIndex: 'trueAmount',
		xtype: 'numbercolumn', 
		format:'0,0.00',
		align:'right'
	},{
		header: consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.queryReceivablePaymentBill.bverifyAmount'),
		dataIndex: 'bverifyAmount',
		xtype: 'numbercolumn', 
		format:'0,0.00',
		align:'right'
	},{
		header: consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.common.active'),
		dataIndex: 'active',
		renderer:function(value){
			var displayField = FossDataDictionary.rendererSubmitToDisplay(value,settlementDict.FOSS_ACTIVE);
    		return displayField;
		}
	},{
		header: consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.common.isRedBack'),
		dataIndex: 'isRedBack',
		renderer:function(value){
			var displayField = FossDataDictionary. rendererSubmitToDisplay (value,'SETTLEMENT__IS_RED_BACK');
    		return displayField;
		}
	},{
		header: consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.queryReceivablePaymentBill.auditStatus'),
		dataIndex: 'auditStatus',
		renderer:function(value){
			var displayField = FossDataDictionary. rendererSubmitToDisplay (value,'NOTE_APPLICATION__APPROVE_STATUS');
    		return displayField;
		}
	},{
		header: consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.queryReceivablePaymentBill.rePaymentType'),
		dataIndex: 'paymentType',
		renderer:function(value){
			var displayField = FossDataDictionary. rendererSubmitToDisplay (value,'SETTLEMENT__PAYMENT_TYPE');
    		return displayField;
		}
	},{
		header: consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.common.businessDate'),
		dataIndex: 'businessDate',
		type:'date',
	    renderer:function(value){
		if(value!=null){
			return Ext.Date.format(new Date(value), 'Y-m-d');
		}else{
			return value;
		}
	}			
	},{
		header: consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.common.accountDate'),
		dataIndex: 'accountDate',
		type:'date',
	    renderer:function(value){
		if(value!=null){
			return Ext.Date.format(new Date(value), 'Y-m-d');
		}else{
			return value;
		}
	}		
	},{
		header: consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.common.notes'),
		dataIndex: 'notes'				
	},{
		header: consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.common.createTime'),
		dataIndex: 'createTime',
		type:'date',
	    renderer:function(value){
		if(value!=null){
			return Ext.Date.format(new Date(value), 'Y-m-d');
		}else{
			return value;
		}
	}			
	},{
		header: consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.common.createUserName'),
		dataIndex: 'createUserName'				
	}]
});

//现金收款单查询列表
Ext.define('Foss.Stlyuscyings.CashCollectionBillQueryInfoGrid',{
	extend:'Ext.grid.Panel',
	frame:true,
	title: consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.queryReceivablePaymentBill.billQueryInfoGridTitle'),
	emptyText: consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.common.emptyText'),
	height:420,
	sortableColumns: false,
	viewConfig: {         
		enableTextSelection: true       
	}, 
	selModel:Ext.create('Ext.selection.CheckboxModel'),
	columns:[{
		header: consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.common.waybillNo'),
		dataIndex: 'waybillNo'
	},{
		header: consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.queryReceivablePaymentBill.collectionOrgNameCH'),
		dataIndex: 'collectionOrgName'
	},{
		header: consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.queryReceivablePaymentBill.generatingOrgName'),
		dataIndex: 'generatingOrgName'
	},{
		header: consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.common.customerCode'),
		dataIndex: 'customerCode'
	},{
		header: consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.common.customerName'),
		dataIndex: 'customerName'		
	},{
		header: consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.queryReceivablePaymentBill.status'),
		dataIndex: 'status',
		renderer:function(value){
			var displayField = FossDataDictionary. rendererSubmitToDisplay (value,'BILL_CASH_COLLECTION__STATUS');
    		return displayField;
		}
	},{
		header: consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.common.amount'),
		dataIndex: 'amount',
		xtype: 'numbercolumn', 
		format:'0,0.00',
		align:'right'
	},{
		header: consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.queryReceivablePaymentBill.paymentType'),
		dataIndex: 'paymentType',
		renderer:function(value){
			var displayField = FossDataDictionary. rendererSubmitToDisplay (value,'SETTLEMENT__PAYMENT_TYPE');
    		return displayField;
		}
	},{
		header: consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.queryReceivablePaymentBill.productCode'),
		dataIndex: 'productCode',
		renderer:function(value){
			return Foss.pkp.ProductData.rendererSubmitToDisplay(value);
		}
	},{
		header: consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.common.active'),
		dataIndex: 'active',
		renderer:function(value){
			var displayField = FossDataDictionary.rendererSubmitToDisplay(value,settlementDict.FOSS_ACTIVE);
    		return displayField;
		}
	},{
		header: consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.common.isRedBack'),
		dataIndex: 'isRedBack',
		renderer:function(value){
			var displayField = FossDataDictionary. rendererSubmitToDisplay (value,'SETTLEMENT__IS_RED_BACK');
    		return displayField;
		}
	},{
		header: consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.common.isInit'),
		dataIndex: 'isInit',
		renderer:function(value){
			if(value == 'Y'){
				return consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.common.yes');
			}else if(value == 'N'){
				return consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.common.no');
			}else{
				return value;
			}
		}
	},{
		header: consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.queryReceivablePaymentBill.cashConfirmUserName'),
		dataIndex: 'cashConfirmUserName'
	},{
		header: consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.common.businessDate'),
		dataIndex: 'businessDate',
		type:'date',
	    renderer:function(value){
		if(value!=null){
			return Ext.Date.format(new Date(value), 'Y-m-d');
		}else{
			return value;
		}
	}			
	},{
		header: consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.common.accountDate'),
		dataIndex: 'accountDate',
		type:'date',
	    renderer:function(value){
		if(value!=null){
			return Ext.Date.format(new Date(value), 'Y-m-d');
		}else{
			return value;
		}
	}		
	},{
		header: consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.common.notes'),
		dataIndex: 'notes'				
	},{
		header: consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.common.createTime'),
		dataIndex: 'createTime',
		type:'date',
	    renderer:function(value){
		if(value!=null){
			return Ext.Date.format(new Date(value), 'Y-m-d');
		}else{
			return value;
		}
	}		
	},{
		header: consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.common.createUserName'),
		dataIndex: 'createUserName'				
	}]
});

//付款单查询列表
Ext.define('Foss.Stlyuscyings.PaymentBillQueryInfoGrid',{
	extend:'Ext.grid.Panel',
	frame:true,
	title: consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.queryReceivablePaymentBill.billQueryInfoGridTitle'),
	emptyText: consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.common.emptyText'),
	height:420,
	sortableColumns: false,
	viewConfig: {         
		enableTextSelection: true       
	}, 
	selModel:Ext.create('Ext.selection.CheckboxModel'),
	columns:[{
		header: consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.common.paymentNo'),
		dataIndex: 'paymentNo'
	},{
		header: consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.queryReceivablePaymentBill.applyWorkFlowNo'),
		dataIndex: 'applyWorkFlowNo'
	},{
		header: consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.queryReceivablePaymentBill.paymentOrgCode'),
		dataIndex: 'paymentOrgCode'
	},{
		header: consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.queryReceivablePaymentBill.paymentOrgName'),
		dataIndex: 'paymentOrgName'
	},{
		header: consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.common.customerCode'),
		dataIndex: 'customerCode'		
	},{
		header: consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.common.customerName'),
		dataIndex: 'customerName'		
	},{
		header: consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.queryReceivablePaymentBill.amountPK'),
		dataIndex: 'amount',
		xtype: 'numbercolumn', 
		format:'0,0.00',
		align:'right'
	},{
		header: consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.queryReceivablePaymentBill.auditStatus'),
		dataIndex: 'auditStatus',
		renderer:function(value){
			var displayField = FossDataDictionary. rendererSubmitToDisplay (value,'BILL_PAYABLE__APPROVE_STATUS');
    		return displayField;
		}
	},{
		header: consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.queryReceivablePaymentBill.remitStatus'),
		dataIndex: 'remitStatus',
		renderer:function(value){
			var displayField = FossDataDictionary. rendererSubmitToDisplay (value,'BILL_PAYMENT__REMIT_STATUS');
    		return displayField;
		}
	},{
		header: consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.queryReceivablePaymentBill.sourceBillType'),
		dataIndex: 'sourceBillType',
		renderer:function(value){
			var displayField = FossDataDictionary. rendererSubmitToDisplay (value,'BILL_PAYMENT__SOURCE_BILL_TYPE');
    		return displayField;
		}
	},{
		header: consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.queryReceivablePaymentBill.paymentType'), 
		dataIndex: 'paymentType',
		renderer:function(value){
			var displayField = FossDataDictionary.rendererSubmitToDisplay (value,'SETTLEMENT__PAYMENT_TYPE');
			 if(value == 'FCUS'){
				 	displayField = '到付运费转预收';
			    }
			    else if(value == 'CF'){
			    	displayField = '委托派费转预收';
			    }
    		return displayField;
		}
	},{
		header: consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.common.active'),
		dataIndex: 'active',
		renderer:function(value){
			var displayField = FossDataDictionary.rendererSubmitToDisplay(value,settlementDict.FOSS_ACTIVE);
    		return displayField;
		}
	},{
		header: consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.common.isRedBack'),
		dataIndex: 'isRedBack',
		renderer:function(value){
			var displayField = FossDataDictionary. rendererSubmitToDisplay (value,'SETTLEMENT__IS_RED_BACK');
    		return displayField;
		}
	},{
		header: consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.queryReceivablePaymentBill.rePaymentType'),
		dataIndex: 'payType',
		renderer:function(value){
			var displayField = FossDataDictionary. rendererSubmitToDisplay (value,'SETTLEMENT__PAYMENT_TYPE');
    		return displayField;
		}
	},{
		header:  consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.common.businessDate'),
		dataIndex: 'businessDate',
		type:'date',
	    renderer:function(value){
		if(value!=null){
			return Ext.Date.format(new Date(value), 'Y-m-d');
		}else{
			return value;
		}
	}				
	},{
		header:  consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.common.notes'),
		dataIndex: 'notes'				
	},{
		header:  consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.common.createTime'),
		dataIndex: 'createTime',
		type:'date',
	    renderer:function(value){
		if(value!=null){
			return Ext.Date.format(new Date(value), 'Y-m-d');
		}else{
			return value;
		}
	}			
	},{
		header:  consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.common.createUserName'),
		dataIndex: 'createUserName'				
	}]
});


//核销单查询列表
Ext.define('Foss.Stlyuscyings.WriteoffBillQueryInfoGrid',{
	extend:'Ext.grid.Panel',
	frame:true,
	title: consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.queryReceivablePaymentBill.billQueryInfoGridTitle'),
	emptyText: consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.common.emptyText'),
	height:420,
	sortableColumns: false,
	viewConfig: {         
		enableTextSelection: true       
	}, 
	selModel:Ext.create('Ext.selection.CheckboxModel'),
	columns:[{
		header: consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.common.writeoffBillNo'),
		dataIndex: 'writeoffBillNo'
	},{
		header: consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.queryReceivablePaymentBill.beginNo'),
		dataIndex: 'beginNo'
	},{
		header: consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.queryReceivablePaymentBill.endNo'),
		dataIndex: 'endNo'
	},{
		header: consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.queryReceivablePaymentBill.writeoffType'),
		dataIndex: 'writeoffType',
		renderer:function(value){
			var displayField = FossDataDictionary.rendererSubmitToDisplay(value,'BILL_WRITEOFF__WRITEOFF_TYPE');
    		return displayField;
		}
	},{
		header: consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.queryReceivablePaymentBill.amountHX'),
		dataIndex: 'amount',
		xtype: 'numbercolumn', 
		format:'0,0.00',
		align:'right'
	},{
		header: consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.common.active'),
		dataIndex: 'active',
		renderer:function(value){
			var displayField = FossDataDictionary.rendererSubmitToDisplay(value,settlementDict.FOSS_ACTIVE);
    		return displayField;
		}
	},{
		header: consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.common.isRedBack'),
		dataIndex: 'isRedBack',
		renderer:function(value){
			var displayField = FossDataDictionary. rendererSubmitToDisplay (value,'SETTLEMENT__IS_RED_BACK');
    		return displayField;
		}
	},{
		header: consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.common.isInit'),
		dataIndex: 'isInit',
		renderer:function(value){
			if(value == 'Y'){
				return consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.common.yes');
			}else if(value == 'N'){
				return consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.common.no');
			}else{
				return value;
			}
		}
	},{
		header: consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.common.OrgName'),
		dataIndex: 'orgName' 
	},{
		header: consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.common.customerCode'),
		dataIndex: 'customerCode' 
	},{
		header: consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.common.customerName'),
		dataIndex: 'customerName'				
	},{
		header: consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.common.businessDate'),
		dataIndex: 'writeoffTime',
		type:'date',
	    renderer:function(value){
		if(value!=null){
			return Ext.Date.format(new Date(value), 'Y-m-d');
		}else{
			return value;
		}
	}		
	},{
		header: consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.common.accountDate'),
		dataIndex: 'accountDate',
		type:'date',
	    renderer:function(value){
		if(value!=null){
			return Ext.Date.format(new Date(value), 'Y-m-d');
		}else{
			return value;
		}
	}		
	},{
		header: consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.common.notes'),
		dataIndex: 'notes'
	},{
		header: consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.common.createUserName'),
		dataIndex: 'createUserName'				
	}]
});

var receivableBillStore = Ext.create('Foss.Stlyuscyings.ReceivableBillStore');

var receivableBillQueryInfoGrid = Ext.create('Foss.ReceivablePaymentBill.ReceivableBillQueryInfoGrid',{
	id:'Foss_ReceivablePaymentBill_ReceivableBillQueryInfoGrid_Id',
	store:receivableBillStore,
	dockedItems: [/*{
		xtype: 'toolbar',
		dock: 'top',
		layout:'hbox',
		items: [{
			xtype:'button',
			text:consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.common.export'),
			width:115
		}]
	},*/{
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
			name:'TotalRecordsInDBInReceivableGrid',
			columnWidth:.1,
			id:'Foss_ReceivablePaymentBill_ReceivableBillQueryInfoGrid_TotalRecordsInDB_Id',
			labelWidth:40,
			fieldLabel:consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.common.totalRecords')
		},{
			xtype:'textfield',
			readOnly:true,
			name:'TotalAmountInReceivableGrid',
			columnWidth:.15,
			id:'Foss_ReceivablePaymentBill_ReceivableBillQueryInfoGrid_TotalAmount_Id',
			labelWidth:56,
			fieldLabel:consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.common.totalAmount')
		},{
			xtype:'textfield',
			readOnly:true,
			name:'TotalVerifyAmountInReceivableGrid',
			columnWidth:.15,
			id:'Foss_ReceivablePaymentBill_ReceivableBillQueryInfoGrid_TotalVerifyAmount_Id',
			labelWidth:88,
			fieldLabel:consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.common.verifyAmount')
		},{
			xtype:'textfield',
			readOnly:true,
			name:'TotalUnverifyAmountInReceivableGrid',
			columnWidth:.15,
			id:'Foss_ReceivablePaymentBill_ReceivableBillQueryInfoGrid_TotalUnverifyAmount_Id',
			labelWidth:88,
			fieldLabel:consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.common.unverifyAmount')
		}]
		}]
	}]
});

var payableBillStore = Ext.create('Foss.Stlyuscyings.PayableBillStore');

var payableBillQueryInfoGrid = Ext.create('Foss.Stlyuscyings.PayableBillQueryInfoGrid',{
	id:'Foss_ReceivablePaymentBill_PayableBillQueryInfoGrid_Id',
	store:payableBillStore,
	dockedItems: [/*{
		xtype: 'toolbar',
		dock: 'top',
		layout:'hbox',
		items: [{
			xtype:'button',
			text:consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.common.export'),
			width:115
		}]
	},*/{
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
			name:'TotalRecordsInDBInReceivableGrid',
			columnWidth:.1,
			id:'Foss_ReceivablePaymentBill_PayableBillQueryInfoGrid_TotalRecordsInDB_Id',
			labelWidth:40,
			fieldLabel:consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.common.totalRecords')
		},{
			xtype:'textfield',
			readOnly:true,
			name:'TotalAmountInReceivableGrid',
			columnWidth:.15,
			id:'Foss_ReceivablePaymentBill_PayableBillQueryInfoGrid_TotalAmount_Id',
			labelWidth:56,
			fieldLabel:consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.common.totalAmount')
		},{
			xtype:'textfield',
			readOnly:true,
			name:'TotalVerifyAmountInReceivableGrid',
			columnWidth:.15,
			id:'Foss_ReceivablePaymentBill_PayableBillQueryInfoGrid_TotalVerifyAmount_Id',
			labelWidth:88,
			fieldLabel:consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.common.verifyAmount')
		},{
			xtype:'textfield',
			readOnly:true,
			name:'TotalUnverifyAmountInReceivableGrid',
			columnWidth:.15,
			id:'Foss_ReceivablePaymentBill_PayableBillQueryInfoGrid_TotalUnverifyAmount_Id',
			labelWidth:88,
			fieldLabel:consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.common.unverifyAmount')
		}]
		}]
	}]
});

var repaymentBillStore = Ext.create('Foss.Stlyuscyings.RepaymentBillStore');

var repaymentBillQueryInfoGrid = Ext.create('Foss.Stlyuscyings.RepaymentBillQueryInfoGrid',{
	id:'Foss_Stlyuscyings_RepaymentBillQueryInfoGrid_Id',
	store:repaymentBillStore,
	dockedItems: [/*{
		xtype: 'toolbar',
		dock: 'top',
		layout:'hbox',
		items: [{
			xtype:'button',
			text:consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.common.export'),
			width:115
		}]
	},*/{
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
			name:'TotalRecordsInDBInReceivableGrid',
			columnWidth:.1,
			id:'Foss_ReceivablePaymentBill_RepaymentBillQueryInfoGrid_TotalRecordsInDB_Id',
			labelWidth:40,
			fieldLabel:consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.common.totalRecords')
		},{
			xtype:'textfield',
			readOnly:true,
			name:'TotalAmountInReceivableGrid',
			columnWidth:.15,
			id:'Foss_ReceivablePaymentBill_RepaymentBillQueryInfoGrid_TotalAmount_Id',
			labelWidth:56,
			fieldLabel:consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.common.totalAmount')
		}]
		}]
	}]
});

var cashCollectionBillStore = Ext.create('Foss.Stlyuscyings.CashCollectionBillStore');

var cashCollectionBillQueryInfoGrid = Ext.create('Foss.Stlyuscyings.CashCollectionBillQueryInfoGrid',{
	id:'Foss_Stlyuscyings_CashCollectionBillQueryInfoGrid_Id',
	store:cashCollectionBillStore,
	dockedItems: [/*{
		xtype: 'toolbar',
		dock: 'top',
		layout:'hbox',
		items: [{
			xtype:'button',
			text:consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.common.export'),
			width:115
		}]
	},*/{
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
			name:'TotalRecordsInDBInReceivableGrid',
			columnWidth:.1,
			id:'Foss_ReceivablePaymentBill_CashCollectionBillQueryInfoGrid_TotalRecordsInDB_Id',
			labelWidth:40,
			fieldLabel:consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.common.totalRecords')
		},{
			xtype:'textfield',
			readOnly:true,
			name:'TotalAmountInReceivableGrid',
			columnWidth:.15,
			id:'Foss_ReceivablePaymentBill_CashCollectionBillQueryInfoGrid_TotalAmount_Id',
			labelWidth:56,
			fieldLabel:consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.common.totalAmount')
		}]
		}]
	}]
});

var paymentBillStore = Ext.create('Foss.Stlyuscyings.PaymentBillStore');

var paymentBillQueryInfoGrid = Ext.create('Foss.Stlyuscyings.PaymentBillQueryInfoGrid',{
	id:'Foss_Stlyuscyings_PaymentBillQueryInfoGrid_Id',
	store:paymentBillStore,
	dockedItems: [/*{
		xtype: 'toolbar',
		dock: 'top',
		layout:'hbox',
		items: [{
			xtype:'button',
			text:consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.common.export'),
			width:115
		}]
	},*/{
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
			name:'TotalRecordsInDBInReceivableGrid',
			columnWidth:.1,
			id:'Foss_ReceivablePaymentBill_PaymentBillQueryInfoGrid_TotalRecordsInDB_Id',
			labelWidth:40,
			fieldLabel:consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.common.totalRecords')
		},{
			xtype:'textfield',
			readOnly:true,
			name:'TotalAmountInReceivableGrid',
			columnWidth:.15,
			id:'Foss_ReceivablePaymentBill_PaymentBillQueryInfoGrid_TotalAmount_Id',
			labelWidth:56,
			fieldLabel:consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.common.totalAmount')
		}]
		}]
	}]
});

var writeoffBillStore = Ext.create('Foss.Stlyuscyings.WriteoffBillStore');

var writeoffBillQueryInfoGrid = Ext.create('Foss.Stlyuscyings.WriteoffBillQueryInfoGrid',{
	id:'Foss_Stlyuscyings_WriteoffBillQueryInfoGrid_Id',
	store:writeoffBillStore,
	dockedItems: [/*{
		xtype: 'toolbar',
		dock: 'top',
		layout:'hbox',
		items: [{
			xtype:'button',
			text:consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.common.export'),
			width:115
		}]
	},*/{
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
			name:'TotalRecordsInDBInReceivableGrid',
			columnWidth:.1,
			id:'Foss_ReceivablePaymentBill_WriteoffBillQueryInfoGrid_TotalRecordsInDB_Id',
			labelWidth:40,
			fieldLabel:consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.common.totalRecords')
		},{
			xtype:'textfield',
			readOnly:true,
			name:'TotalAmountInReceivableGrid',
			columnWidth:.15,
			id:'Foss_ReceivablePaymentBill_WriteoffBillQueryInfoGrid_TotalAmount_Id',
			labelWidth:56,
			fieldLabel:consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.common.totalAmount')
		}]
		}]
	}]
});		


//第一个Tab:按运单单号查询
Ext.define('Foss.Stlyuscyings.StlyuscyingsReceivablePaymentBillGridTab', {
	extend:'Ext.tab.Panel',
	bodyCls: 'autoHeight',
	cls: 'innerTabPanel',
	activeTab: 0,
	margin :'0 0 0 0',
	height:500,
	listeners:{
		'tabchange':function(th,newcard,oldcard){
			
			var queryParams = Ext.getCmp('T_consumer-queryReceivablePaymentBill_content').items.items[0].getForm().findField('billNos').getValue();
			if(queryParams == null || queryParams== ''){
				Ext.Msg.alert(consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.common.warmTips'),consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.queryReceivablePaymentBill.queryNosIsNotNull'));
				return false;
			} 
			
			var wayBillNosArray = [];					
			var waybillNos = Ext.getCmp('T_consumer-queryReceivablePaymentBill_content').items.items[0].getForm().findField('billNos').getValue();
			if(Ext.String.trim(waybillNos) != null && Ext.String.trim(waybillNos) != ''){
				 var reg = /[,;]/;  
				 array = waybillNos.split(reg);
				 for(var i=0;i<array.length;i++){
				 	if(Ext.String.trim(array[i])!=''){
				 		wayBillNosArray.push(array[i]);	
				 	}
				 }
			}else{
				Ext.Msg.alert(consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.common.warmTips'),consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.queryReceivablePaymentBill.billNosInputIsNotNull'));
				return false;
			}
			
			if(wayBillNosArray.length > 10){
				Ext.Msg.alert(consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.common.warmTips'),consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.queryReceivablePaymentBill.billNosInoutIsToMore'));
				return false;
			}
			var billReceivablePaymentVO = new Object();
			billReceivablePaymentVO.wayBillNosArray = wayBillNosArray;
			var params = new Object();

			if(newcard.name == 'receivableBillTab'){
				var form = Ext.getCmp('T_consumer-queryReceivablePaymentBill_content').getReceiablePaymentTab().getForm();
				if (form.isValid()) {
				Ext.Ajax.request({
					url:consumer.realPath('queryReceivableBillByBillNo.action'),
					params:{
						"billReceivablePaymentVO.wayBillNosArray" : wayBillNosArray
					},
					method:'post',
					success:function(response){
						var result = Ext.decode(response.responseText);
						var receivableBillStore = Ext.getCmp('Foss_ReceivablePaymentBill_ReceivableBillQueryInfoGrid_Id').store;
						var payableBillStore = Ext.getCmp('Foss_ReceivablePaymentBill_PayableBillQueryInfoGrid_Id').store;	
						var repaymentBillStore = Ext.getCmp('Foss_Stlyuscyings_RepaymentBillQueryInfoGrid_Id').store;
						var cashCollectionBillStore = Ext.getCmp('Foss_Stlyuscyings_CashCollectionBillQueryInfoGrid_Id').store;
						var paymentBillStore = Ext.getCmp('Foss_Stlyuscyings_PaymentBillQueryInfoGrid_Id').store;
						var writeoffBillStore = Ext.getCmp('Foss_Stlyuscyings_WriteoffBillQueryInfoGrid_Id').store;
						
						payableBillStore.removeAll();
						repaymentBillStore.removeAll();
						cashCollectionBillStore.removeAll();
						paymentBillStore.removeAll();
						writeoffBillStore.removeAll();
						//如果List不为空就加载数据到页面
						if(!Ext.isEmpty(result.billReceivablePaymentVO.billReceivableList)){
							receivableBillStore.loadData(result.billReceivablePaymentVO.billReceivableList);
							}
				
						//动态给GRID设置值
						Ext.getCmp('Foss_ReceivablePaymentBill_ReceivableBillQueryInfoGrid_TotalRecordsInDB_Id').setValue(result.billReceivablePaymentVO.totalRecordsInDB);
						Ext.getCmp('Foss_ReceivablePaymentBill_ReceivableBillQueryInfoGrid_TotalAmount_Id').setValue(result.billReceivablePaymentVO.totalAmount);
						Ext.getCmp('Foss_ReceivablePaymentBill_ReceivableBillQueryInfoGrid_TotalVerifyAmount_Id').setValue(result.billReceivablePaymentVO.totalVerifyAmount);
						Ext.getCmp('Foss_ReceivablePaymentBill_ReceivableBillQueryInfoGrid_TotalUnverifyAmount_Id').setValue(result.billReceivablePaymentVO.totalUnverifyAmount);
					},
					failure:function(response){
						var result = Ext.decode(response.responseText);
					}
				});		
				
			}else{
				Ext.Msg.alert(consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.common.warmTips'),consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.common.validateFailAlert'));
			}}
			if(newcard.name == 'payableBillTab'){
				var form = Ext.getCmp('T_consumer-queryReceivablePaymentBill_content').getReceiablePaymentTab().getForm();
				if (form.isValid()) {
				Ext.Ajax.request({
					url:consumer.realPath('queryPayableBillByBillNo.action'),
					params:{
						"billReceivablePaymentVO.wayBillNosArray" : wayBillNosArray
					},
					method:'post',
					success:function(response){
						var result = Ext.decode(response.responseText);
						var payableBillStore = Ext.getCmp('Foss_ReceivablePaymentBill_PayableBillQueryInfoGrid_Id').store;		
						var receivableBillStore = Ext.getCmp('Foss_ReceivablePaymentBill_ReceivableBillQueryInfoGrid_Id').store;
						var repaymentBillStore = Ext.getCmp('Foss_Stlyuscyings_RepaymentBillQueryInfoGrid_Id').store;
						var cashCollectionBillStore = Ext.getCmp('Foss_Stlyuscyings_CashCollectionBillQueryInfoGrid_Id').store;
						var paymentBillStore = Ext.getCmp('Foss_Stlyuscyings_PaymentBillQueryInfoGrid_Id').store;
						var writeoffBillStore = Ext.getCmp('Foss_Stlyuscyings_WriteoffBillQueryInfoGrid_Id').store;
						
						receivableBillStore.removeAll();
						repaymentBillStore.removeAll();
						cashCollectionBillStore.removeAll();
						paymentBillStore.removeAll();
						writeoffBillStore.removeAll();
						
						//如果List不为空就加载数据到页面
						if(!Ext.isEmpty(result.billReceivablePaymentVO.billPayableList)){
							payableBillStore.loadData(result.billReceivablePaymentVO.billPayableList);
							}
														
						//动态给GRID设置值
						Ext.getCmp('Foss_ReceivablePaymentBill_PayableBillQueryInfoGrid_TotalRecordsInDB_Id').setValue(result.billReceivablePaymentVO.totalRecordsInDB);
						Ext.getCmp('Foss_ReceivablePaymentBill_PayableBillQueryInfoGrid_TotalAmount_Id').setValue(result.billReceivablePaymentVO.totalAmount);
						Ext.getCmp('Foss_ReceivablePaymentBill_PayableBillQueryInfoGrid_TotalVerifyAmount_Id').setValue(result.billReceivablePaymentVO.totalVerifyAmount);						
						Ext.getCmp('Foss_ReceivablePaymentBill_PayableBillQueryInfoGrid_TotalUnverifyAmount_Id').setValue(result.billReceivablePaymentVO.totalUnverifyAmount);
					},
					failure:function(response){
						var result = Ext.decode(response.responseText);
					}
				});	
			}else{
				Ext.Msg.alert(consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.common.warmTips'),consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.common.validateFailAlert'));
			}
		}
			if(newcard.name == 'repaymentBillTab'){
				var form = Ext.getCmp('T_consumer-queryReceivablePaymentBill_content').getReceiablePaymentTab().getForm();
				if (form.isValid()) {
				Ext.Ajax.request({
					url:consumer.realPath('queryRepaymentBillByBillNo.action'),
					params:{
						"billReceivablePaymentVO.wayBillNosArray" : wayBillNosArray
					},
					method:'post',
					success:function(response){
						var result = Ext.decode(response.responseText);
						var repaymentBillStore = Ext.getCmp('Foss_Stlyuscyings_RepaymentBillQueryInfoGrid_Id').store;	
						var receivableBillStore = Ext.getCmp('Foss_ReceivablePaymentBill_ReceivableBillQueryInfoGrid_Id').store;
						var payableBillStore = Ext.getCmp('Foss_ReceivablePaymentBill_PayableBillQueryInfoGrid_Id').store;	
						var cashCollectionBillStore = Ext.getCmp('Foss_Stlyuscyings_CashCollectionBillQueryInfoGrid_Id').store;
						var paymentBillStore = Ext.getCmp('Foss_Stlyuscyings_PaymentBillQueryInfoGrid_Id').store;
						var writeoffBillStore = Ext.getCmp('Foss_Stlyuscyings_WriteoffBillQueryInfoGrid_Id').store;
						
						receivableBillStore.removeAll();
						payableBillStore.removeAll();
						cashCollectionBillStore.removeAll();
						paymentBillStore.removeAll();
						writeoffBillStore.removeAll();
						
						//如果List不为空就加载数据到页面
						if(!Ext.isEmpty(result.billReceivablePaymentVO.billRepaymentList)){
							repaymentBillStore.loadData(result.billReceivablePaymentVO.billRepaymentList);
							}
						
						//动态给GRID设置值
						Ext.getCmp('Foss_ReceivablePaymentBill_RepaymentBillQueryInfoGrid_TotalRecordsInDB_Id').setValue(result.billReceivablePaymentVO.totalRecordsInDB);
						Ext.getCmp('Foss_ReceivablePaymentBill_RepaymentBillQueryInfoGrid_TotalAmount_Id').setValue(result.billReceivablePaymentVO.totalAmount);
					},
					failure:function(response){
						var result = Ext.decode(response.responseText);
					}
				});	
				
			}else{
				Ext.Msg.alert(consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.common.warmTips'),consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.common.validateFailAlert'));
			}}
		   if(newcard.name == 'cashCollectionBillTab'){
			   var form = Ext.getCmp('T_consumer-queryReceivablePaymentBill_content').getReceiablePaymentTab().getForm();
			   if (form.isValid()) {
				Ext.Ajax.request({
					url:consumer.realPath('queryCashCollectionBillByBillNo.action'),
					params:{
						"billReceivablePaymentVO.wayBillNosArray" : wayBillNosArray
					},
					method:'post',
					success:function(response){
						var result = Ext.decode(response.responseText);
						var cashCollectionBillStore = Ext.getCmp('Foss_Stlyuscyings_CashCollectionBillQueryInfoGrid_Id').store;
						var repaymentBillStore = Ext.getCmp('Foss_Stlyuscyings_RepaymentBillQueryInfoGrid_Id').store;	
						var receivableBillStore = Ext.getCmp('Foss_ReceivablePaymentBill_ReceivableBillQueryInfoGrid_Id').store;
						var payableBillStore = Ext.getCmp('Foss_ReceivablePaymentBill_PayableBillQueryInfoGrid_Id').store;	
						var paymentBillStore = Ext.getCmp('Foss_Stlyuscyings_PaymentBillQueryInfoGrid_Id').store;
						var writeoffBillStore = Ext.getCmp('Foss_Stlyuscyings_WriteoffBillQueryInfoGrid_Id').store;
						
						receivableBillStore.removeAll();
						payableBillStore.removeAll();
						repaymentBillStore.removeAll();
						paymentBillStore.removeAll();
						writeoffBillStore.removeAll();
						//如果List不为空就加载数据到页面
						if(!Ext.isEmpty(result.billReceivablePaymentVO.billCashCollectionList)){
							cashCollectionBillStore.loadData(result.billReceivablePaymentVO.billCashCollectionList);
							}
												
						//动态给GRID设置值
						Ext.getCmp('Foss_ReceivablePaymentBill_CashCollectionBillQueryInfoGrid_TotalRecordsInDB_Id').setValue(result.billReceivablePaymentVO.totalRecordsInDB);
						Ext.getCmp('Foss_ReceivablePaymentBill_CashCollectionBillQueryInfoGrid_TotalAmount_Id').setValue(result.billReceivablePaymentVO.totalAmount);
					},
					failure:function(response){
						var result = Ext.decode(response.responseText);
					}
				});	
			}else{
				Ext.Msg.alert(consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.common.warmTips'),consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.common.validateFailAlert'));
			}}
			if(newcard.name == 'paymentBillTab'){
				var form = Ext.getCmp('T_consumer-queryReceivablePaymentBill_content').getReceiablePaymentTab().getForm();
				 if (form.isValid()) {
				Ext.Ajax.request({
					url:consumer.realPath('queryPaymentBillByBillNo.action'),
					params:{
						"billReceivablePaymentVO.wayBillNosArray" : wayBillNosArray
					},
					method:'post',
					success:function(response){
						var result = Ext.decode(response.responseText);
						var paymentBillStore = Ext.getCmp('Foss_Stlyuscyings_PaymentBillQueryInfoGrid_Id').store;
						var cashCollectionBillStore = Ext.getCmp('Foss_Stlyuscyings_CashCollectionBillQueryInfoGrid_Id').store;
						var repaymentBillStore = Ext.getCmp('Foss_Stlyuscyings_RepaymentBillQueryInfoGrid_Id').store;	
						var receivableBillStore = Ext.getCmp('Foss_ReceivablePaymentBill_ReceivableBillQueryInfoGrid_Id').store;
						var payableBillStore = Ext.getCmp('Foss_ReceivablePaymentBill_PayableBillQueryInfoGrid_Id').store;	
						var writeoffBillStore = Ext.getCmp('Foss_Stlyuscyings_WriteoffBillQueryInfoGrid_Id').store;
						
						receivableBillStore.removeAll();
						payableBillStore.removeAll();
						repaymentBillStore.removeAll();
						cashCollectionBillStore.removeAll();
						writeoffBillStore.removeAll();
						//如果List不为空就加载数据到页面
						if(!Ext.isEmpty(result.billReceivablePaymentVO.billPaymentList)){
							paymentBillStore.loadData(result.billReceivablePaymentVO.billPaymentList);
							}
										
						Ext.getCmp('Foss_ReceivablePaymentBill_PaymentBillQueryInfoGrid_TotalRecordsInDB_Id').setValue(result.billReceivablePaymentVO.totalRecordsInDB);
						Ext.getCmp('Foss_ReceivablePaymentBill_PaymentBillQueryInfoGrid_TotalAmount_Id').setValue(result.billReceivablePaymentVO.totalAmount);
					},
					failure:function(response){
						var result = Ext.decode(response.responseText);
					}
				});	
			}else{
				Ext.Msg.alert(consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.common.warmTips'),consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.common.validateFailAlert'));
			}}
			if(newcard.name == 'writeoffBillTab'){
				var form = Ext.getCmp('T_consumer-queryReceivablePaymentBill_content').getReceiablePaymentTab().getForm();
				 if (form.isValid()) {
				Ext.Ajax.request({
					url:consumer.realPath('queryWriteoffBillByBillNo.action'),
					params:{
						"billReceivablePaymentVO.wayBillNosArray" : wayBillNosArray
					},
					method:'post',
					success:function(response){
						var result = Ext.decode(response.responseText);
						var writeoffBillStore = Ext.getCmp('Foss_Stlyuscyings_WriteoffBillQueryInfoGrid_Id').store;
						var paymentBillStore = Ext.getCmp('Foss_Stlyuscyings_PaymentBillQueryInfoGrid_Id').store;
						var cashCollectionBillStore = Ext.getCmp('Foss_Stlyuscyings_CashCollectionBillQueryInfoGrid_Id').store;
						var repaymentBillStore = Ext.getCmp('Foss_Stlyuscyings_RepaymentBillQueryInfoGrid_Id').store;	
						var receivableBillStore = Ext.getCmp('Foss_ReceivablePaymentBill_ReceivableBillQueryInfoGrid_Id').store;
						var payableBillStore = Ext.getCmp('Foss_ReceivablePaymentBill_PayableBillQueryInfoGrid_Id').store;	
						var writeoffBillStore = Ext.getCmp('Foss_Stlyuscyings_WriteoffBillQueryInfoGrid_Id').store;
						
						receivableBillStore.removeAll();
						payableBillStore.removeAll();
						repaymentBillStore.removeAll();
						cashCollectionBillStore.removeAll();
						paymentBillStore.removeAll();						
						//如果List不为空就加载数据到页面
						if(!Ext.isEmpty(result.billReceivablePaymentVO.billWriteoffList)){
							writeoffBillStore.loadData(result.billReceivablePaymentVO.billWriteoffList);
							}
										
						//动态给GRID设置值
						Ext.getCmp('Foss_ReceivablePaymentBill_WriteoffBillQueryInfoGrid_TotalRecordsInDB_Id').setValue(result.billReceivablePaymentVO.totalRecordsInDB);
						Ext.getCmp('Foss_ReceivablePaymentBill_WriteoffBillQueryInfoGrid_TotalAmount_Id').setValue(result.billReceivablePaymentVO.totalAmount);
					},
					failure:function(response){
						var result = Ext.decode(response.responseText);
					}
				});	
			}else{
				Ext.Msg.alert(consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.common.warmTips'),consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.common.validateFailAlert'));
			}}		
			}		
		},
	items: [{
    	title: consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.common.receivableBill'),
    	name:'receivableBillTab',
		tabConfig: {
			width: 120
		},
		items:[receivableBillQueryInfoGrid]
    }, {
    	title: consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.common.payableBill'),
    	name:'payableBillTab',
		tabConfig: {
			width: 120
		},
		items:[payableBillQueryInfoGrid]
    },{
    	title: consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.common.repaymentBill'),
    	name:'repaymentBillTab',
		tabConfig: {
			width: 120
		},
		items:[repaymentBillQueryInfoGrid]
    },{
    	title: consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.common.cashCollectionBill'),
    	name:'cashCollectionBillTab',
		tabConfig: {
			width: 120
		},
		items:[cashCollectionBillQueryInfoGrid]
    },{
    	title: consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.common.paymentBill'),
    	name:'paymentBillTab',
		tabConfig: {
			width: 120
		},
		items:[paymentBillQueryInfoGrid]
    },{
    	title: consumer.queryReceivablePaymentBill.i18n('foss.stl.consumer.common.writeoffBill'),
    	name:'writeoffBillTab',
		tabConfig: {
			width: 120
		},
		items:[writeoffBillQueryInfoGrid]
    }]
});

Ext.onReady(function() {
		Ext.QuickTips.init();
		var queryReceiablePaymentTab = Ext.create('Foss.Stlyuscyings.StlyuscyingsReceivablePaymentBillForm');
		var StlyuscyingsReceivablePaymentBillGridTab = Ext.create('Foss.Stlyuscyings.StlyuscyingsReceivablePaymentBillGridTab');
		
		Ext.create('Ext.panel.Panel',{
			id: 'T_consumer-queryReceivablePaymentBill_content',
			cls: "panelContentNToolbar",
			bodyCls: 'panelContentNToolbar-body',
			layout: 'auto',
			getReceiablePaymentTab:function(){
				return 	queryReceiablePaymentTab;
			},
			getStlyuscyingsReceivablePaymentBillGridTab:function(){
				return 	StlyuscyingsReceivablePaymentBillGridTab;
			},
			items: [queryReceiablePaymentTab,StlyuscyingsReceivablePaymentBillGridTab],
			renderTo: 'T_consumer-queryReceivablePaymentBill-body'
		});
	});