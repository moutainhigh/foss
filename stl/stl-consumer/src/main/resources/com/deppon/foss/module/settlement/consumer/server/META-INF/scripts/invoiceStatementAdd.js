/**
 * @项目：合伙人项目
 * @功能：“发票对账单新增”的页面
 * @author:218371-foss-zhaoyanjun
 * @date;2016-01-28上午09:11
 */

/**
  * 查询成功后，执行的公用方法
  */ 


consumer.invoiceStatementAdd.statementQuerySuccess = function(result){
	var baseInfoAdd,
		statementPreEntryAddGrid,
		statementOfAccountDBeginPeriodList,
		statementOfAccount,
		model;
		
	baseInfoAdd = Ext.getCmp('T_consumer-invoiceStatementAdd_content').getInvoiceBasicInformation();
	statementPreEntryAddGrid = Ext.getCmp('T_consumer-invoiceStatementAdd_content').getBillDetails();
	
	//获取表单后台数据
	statementOfAccount = result.invoiceManagementAllVo.invoiceManagementAddDto;
	statementOfAccountDBeginPeriodList = result.invoiceManagementAllVo.invoiceManagementAddDto.list;
	model = new Foss.invoiceStatementAdd.StatementFormModel(statementOfAccount);
	
	if(Ext.isEmpty(statementOfAccountDBeginPeriodList)){
		Ext.Msg.alert(consumer.invoiceStatementAdd.i18n('foss.stl.consumer.common.alert'),consumer.invoiceStatementAdd.i18n('foss.stl.consumer.common.emptyText'));
		statementPreEntryAddGrid.hide();
		baseInfoAdd.hide();
		return false;
	}
	
	if(Ext.isEmpty(statementOfAccountDBeginPeriodList)){
		
		Ext.Msg.alert(consumer.invoiceStatementAdd.i18n('foss.stl.consumer.common.alert'),consumer.invoiceStatementAdd.i18n('foss.stl.consumer.common.emptyText'));
		statementPreEntryAddGrid.hide();
		baseInfoAdd.hide();
		return false;
	}
	
	statementPreEntryAddGrid.store.loadData(statementOfAccountDBeginPeriodList);
	baseInfoAdd.loadRecord(model);
	
	//查询后显示隐藏组件
	baseInfoAdd.show();
	statementPreEntryAddGrid.show();
	statementPreEntryAddGrid.getSelectionModel().selectAll();
} 

/**
 * 根据客户查询对账单
 */
consumer.invoiceStatementAdd.statementQueryByCustomerInfo = function(){
	var form  = this.up('form').getForm();
	//声明客户编码和客户名称
	var customerCode,customerName;
	if(form.isValid()){
		//校验日期
		var periodBeginDate = form.findField('periodBeginDate').getValue();
		var periodEndDate = form.findField('periodEndDate').getValue();
		//比较起始日期和结束日期
		var compareTwoDate = stl.compareTwoDate(periodBeginDate,periodEndDate);
		
		//获取客户类型
		var customerType =  form.findField('customerCode').getValue();
		//获取多选框的值
		var manycheck=Ext.getCmp('manycheck').getChecked();
		//多选框
		var manychecks= new Array();
		var  count=0;
		Ext.Array.each(manycheck, function(item){
			manychecks[count]=  item.inputValue;
			count++;
		});
		//声明日期类型
//		var dateType = form.getValues().queryDateType;
		var billType = '';
		//校验客户/代理信息
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
			Ext.Msg.alert(consumer.invoiceStatementAdd.i18n('foss.stl.consumer.common.alert'),consumer.invoiceStatementAdd.i18n('foss.stl.consumer.invoiceStatementAdd.customerIsNullWarning'));
			return false;
		}
		
		//校验日期
		if(Ext.isEmpty(periodBeginDate)||Ext.isEmpty(periodEndDate)){
			Ext.Msg.alert(consumer.invoiceStatementAdd.i18n('foss.stl.consumer.common.alert'),consumer.invoiceStatementAdd.i18n('foss.stl.consumer.invoiceStatementAdd.quryDateIsNullWarning'));
			return false;
		}
		
		if(compareTwoDate>stl.DATELIMITMAXDAYS_WEEK){
			Ext.Msg.alert(consumer.invoiceStatementAdd.i18n('foss.stl.consumer.common.alert'),consumer.invoiceStatementAdd.i18n('foss.stl.consumer.invoiceStatementAdd.queryDateMaxLimit'));
			return false;
		}else if(compareTwoDate<1){
			Ext.Msg.alert(consumer.invoiceStatementAdd.i18n('foss.stl.consumer.common.alert'),consumer.invoiceStatementAdd.i18n('foss.stl.consumer.common.dateEndBeforeStatrtWarining'));
			return false;
		}
		
		var currentDeptCode=FossUserContext.getCurrentDeptCode();
		if(currentDeptCode==null){
			Ext.Msg.alert(consumer.invoiceStatementAdd.i18n('foss.stl.consumer.common.alert'),consumer.invoiceStatementAdd.i18n('foss.stl.consumer.common.currentDeptNull'));
			return false;
		}
		
		//清空对账单界面数据
		var baseInfoAdd = Ext.getCmp('T_consumer-invoiceStatementAdd_content').getInvoiceBasicInformation();
		var statementPreEntryGrid = Ext.getCmp('T_consumer-invoiceStatementAdd_content').getBillDetails();
		
		baseInfoAdd.getForm().reset();
		statementPreEntryGrid.store.removeAll();
		
		Ext.Ajax.request({
			url:consumer.realPath('queryForInvoiceStatementMake.action'),
			actionMethods:'post',
			params:{
				'invoiceManagementAllVo.invoiceManagementAddDto.customerCode':customerCode,
				'invoiceManagementAllVo.invoiceManagementAddDto.periodBeginDate':form.findField('periodBeginDate').getValue(),
				'invoiceManagementAllVo.invoiceManagementAddDto.periodEndDate':form.findField('periodEndDate').getValue(),
				'invoiceManagementAllVo.invoiceManagementAddDto.queryPage':'TD',
				'invoiceManagementAllVo.invoiceManagementAddDto.currentDeptNo':currentDeptCode,
				'invoiceManagementAllVo.invoiceManagementAddDto.manycheck':manychecks
				},
			submitEmptyText:false,
			//waitMsg:'查询中，请稍后...',
			success:function(response){
				var result = Ext.decode(response.responseText);	
				consumer.invoiceStatementAdd.statementQuerySuccess(result);
			},	
			exception:function(response){
				var result = Ext.decode(response.responseText);	
				Ext.Msg.alert(consumer.invoiceStatementAdd.i18n('foss.stl.consumer.common.alert'),result.message);
			}
		})
	}else{
		Ext.Msg.alert(consumer.invoiceStatementAdd.i18n('foss.stl.consumer.common.alert'),consumer.invoiceStatementAdd.i18n('foss.stl.consumer.common.validateFailAlert'));
		return false;
	}
}

/**
 * 根据单号查询对账单
 */
consumer.invoiceStatementAdd.statementQueryByNumbers = function(){
	//获取多选框的值
	var manycheck=Ext.getCmp('manychecks').getChecked();
	//多选框
	var manychecks= new Array();
	var  count=0;
	Ext.Array.each(manycheck, function(item){
		manychecks[count]=  item.inputValue;
		count++;
	});
	var form  = this.up('form').getForm();	
	var billDetailNoArray =[];
	var billDetailNos = form.findField('billDetailNos').getValue();
	if(Ext.String.trim(billDetailNos)!=null && Ext.String.trim(billDetailNos)!=''){
		 array = stl.splitToArray(billDetailNos);
		 for(var i=0;i<array.length;i++){
		 	if(Ext.String.trim(array[i])!=''){
		 		billDetailNoArray.push(array[i]);	
		 	}
		 }
		 if(billDetailNoArray.length>10){
		 	Ext.Msg.alert(consumer.invoiceStatementAdd.i18n('foss.stl.consumer.common.alert'),consumer.invoiceStatementAdd.i18n('foss.stl.consumer.common.queryNosLimit'));
		 	return false;
		 }
	}else{
		Ext.Msg.alert(consumer.invoiceStatementAdd.i18n('foss.stl.consumer.common.alert'),consumer.invoiceStatementAdd.i18n('foss.stl.consumer.common.billNosIsNullWarning'));
		return false;
	}
	
	var currentDeptCode=FossUserContext.getCurrentDeptCode();
	if(currentDeptCode==null){
		Ext.Msg.alert(consumer.invoiceStatementAdd.i18n('foss.stl.consumer.common.alert'),consumer.invoiceStatementAdd.i18n('foss.stl.consumer.common.currentDeptNull'));
		return false;
	}
	
	if(form.isValid()){
		
		//清空对账单界面数据
		var baseInfoAdd = Ext.getCmp('T_consumer-invoiceStatementAdd_content').getInvoiceBasicInformation();
		var statementPreEntryGrid = Ext.getCmp('T_consumer-invoiceStatementAdd_content').getBillDetails();		
		baseInfoAdd.getForm().reset();
		statementPreEntryGrid.store.removeAll();
		
		Ext.Ajax.request({
			url:consumer.realPath('queryForInvoiceStatementMake.action'),
			actionMethods:'post',
			params:{
				'invoiceManagementAllVo.invoiceManagementAddDto.billDetailNos':billDetailNoArray,
				'invoiceManagementAllVo.invoiceManagementAddDto.queryPage':'TB',
				'invoiceManagementAllVo.invoiceManagementAddDto.currentDeptNo':currentDeptCode,
				'invoiceManagementAllVo.invoiceManagementAddDto.manycheck':manychecks
				
			},
			submitEmptyText:false,
			success:function(response){
				var result = Ext.decode(response.responseText);
				consumer.invoiceStatementAdd.statementQuerySuccess(result);
			},
			exception:function(response){
				var result = Ext.decode(response.responseText);	
				Ext.Msg.alert(consumer.invoiceStatementAdd.i18n('foss.stl.consumer.common.alert'),result.message);
			}
		});
	}else{
		Ext.Msg.alert(consumer.invoiceStatementAdd.i18n('foss.stl.consumer.common.alert'),consumer.invoiceStatementAdd.i18n('foss.stl.consumer.common.validateFailAlert'));
		return false;
	}
}

/**
 * 查询tab
 */
Ext.define('Foss.invoiceStatementAdd.QueryInfoTab',{
	extend:'Ext.tab.Panel',
	frame:false,
	bodyCls: 'autoHeight',
	cls: 'innerTabPanel',
	activeTab:0,//默认显示按单号制作
	height:210,
    items: [{
      	title: consumer.invoiceStatementAdd.i18n('foss.stl.consumer.invoiceStatementAdd.makeByCustomer'),
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
	   		layout: {
	   	        type: 'table',
	   	        columns: 2
	   	    },
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
			    	xtype:'displayfield',
			    	columnWidth:.5,
			    	value:consumer.invoiceStatementAdd.i18n('foss.stl.consumer.invoiceStatementAdd.customerType')
			    	+"&nbsp;:&nbsp;"+consumer.invoiceStatementAdd.i18n('foss.stl.consumer.invoiceStatementAdd.businessPartner')
			    },{
		        	xtype:'commonsaledepartmentselector',
		        	listWidth:300,
		        	all:'true',//查询所有(包含作废)
		        	name:'customerCode',
		        	displayField : 'name',// 显示名称
					valueField : 'code',
		        	emptyText:'客户编码和手机均可查询',
		        	contcatFlag :'Y',//增加按手机号查询
		        	singlePeopleFlag:'Y',
		        	fieldLabel:'<span style="color:red;">*</span>'+consumer.invoiceStatementAdd.i18n('foss.stl.consumer.common.partnerMessage'),
		        	columnWidth:.5
		        },{
		        	xtype:'datefield',
		        	allowBlank:false,
		        	fieldLabel:consumer.invoiceStatementAdd.i18n('foss.stl.consumer.invoiceStatementAdd.accountPeriod'),
		        	name:'periodBeginDate',
		        	columnWidth: .5,
		        	format:'Y-m-d',
		        	value:stl.getTargetDate(new Date(),-stl.DATELIMITDAYS_MONTH)
		        },{
		        	xtype:'datefield',
		        	allowBlank:false,
		        	fieldLabel:consumer.invoiceStatementAdd.i18n('foss.stl.consumer.invoiceStatementAdd.to'),
		        	name:'periodEndDate',
		        	format:'Y-m-d',
		        	columnWidth: .5,
		        	value:new Date()
		        },{
					border: 1,
					xtype:'container',
					columnWidth:1,
					defaultType:'button',
					layout:'column',
					items:[{
						text:consumer.invoiceStatementAdd.i18n('foss.stl.consumer.common.reset'),
						columnWidth:.08,
						handler : function() {
							// 重置
							this.up('form').getForm().reset();
						}
					},{             
				        xtype: 'checkboxgroup',  //复选框组
				        id:'manycheck',
				        fieldLabel : '单据子类型 ', //复选框组的字段标签
				        columns: 3, //列来存放选择框项
						vertical: true, //按照columns中指定的列数来分配复选框组件
						columnWidth:0.5,
						style: { marginTop: '5px' },
				        items: [{ 
									//boxLabel  : '散货', 
									boxLabel  : '始发提成应收单',
									name      : 'manycheck',
									inputValue: 'POR'
									//checked   : true //选中
								},{ 
									//boxLabel  : '散货', 
									boxLabel  : '委托派费应收单',
									name      : 'manycheck',
									inputValue: 'PDFR'
									//checked   : true //选中
								},{ 
									//boxLabel  : '散货', 
									boxLabel  : '培训会务应收单',
									name      : 'manycheck',
									inputValue: 'PTF'
									//checked   : true //选中
								}]

			        },{
						text:consumer.invoiceStatementAdd.i18n('foss.stl.consumer.common.query'),
						cls:'yellow_button',
						columnWidth:.08,
						handler:consumer.invoiceStatementAdd.statementQueryByCustomerInfo
					}]
	        	}]
		 	}]
		}]
	},{										
        title: consumer.invoiceStatementAdd.i18n('foss.stl.consumer.invoiceStatementAdd.makeByNumber'),
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
	    		fieldLabel:consumer.invoiceStatementAdd.i18n('foss.stl.consumer.invoiceStatementAdd.number'),
	    		emptyText:consumer.invoiceStatementAdd.i18n('foss.stl.consumer.invoiceStatementAdd.billNosQueryLimit'),
	    		name:'billDetailNos',
	    		allowBlank:false,
	    		columnWidth:.65,
	    		labelWidth:70,
	    		width: 600,
	    		height:110
	        },{
				xtype:'container',
				columnWidth:.35,
				items:[{
					xtype:'component',
					padding:'0 0 0 10',
					autoEl:{
						tag:'div',
						html:'<span style="color:red;">'+consumer.invoiceStatementAdd.i18n('foss.stl.consumer.invoiceStatementAdd.billNosDesc')+'</span>'
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
					text:consumer.invoiceStatementAdd.i18n('foss.stl.consumer.common.reset'),
					columnWidth:.075,
					handler:function() {
						// 重置
						this.up('form').getForm().reset();
					}
				},{             
					xtype: 'checkboxgroup',  //复选框组
			        id:'manychecks',
			        fieldLabel : '单据子类型 ', //复选框组的字段标签
			        columns: 3, //列来存放选择框项
					vertical: true, //按照columns中指定的列数来分配复选框组件
					columnWidth:0.5,
					style: { marginTop: '5px' },
			        items: [{ 
								//boxLabel  : '散货', 
								boxLabel  : '始发提成应收单',
								name      : 'manychecks',
								inputValue: 'POR'
								//checked   : true //选中
							},{ 
								//boxLabel  : '散货', 
								boxLabel  : '委托派费应收单',
								name      : 'manychecks',
								inputValue: 'PDFR'
								//checked   : true //选中
							},{ 
								//boxLabel  : '散货', 
								boxLabel  : '培训会务应收单',
								name      : 'manychecks',
								inputValue: 'PTF'
								//checked   : true //选中
							}]

		        },{
					text:consumer.invoiceStatementAdd.i18n('foss.stl.consumer.common.query'),
					cls:'yellow_button',
					columnWidth:.075,
					handler:consumer.invoiceStatementAdd.statementQueryByNumbers
				}]
        	}]
	    }]       
    }]
});

/**
 * 发票基本属性面板
 */
Ext.define('Foss.invoiceStatementAdd.InvoiceBasicInformation', {
	extend:'Ext.form.Panel',
	layout:'column',
	height:150,
	frame:true,
	hidden:true,
	defaultType:'textfield',
	layout:'column',
	defaults:{
		labelWidth:80,
		labelAlign:'left',
		margin:'5 5 5 10',
		readOnly:true
	},
	items:[{
		xtype:'displayfield',
		columnWidth:.25,
		value:"&nbsp;&nbsp;"+consumer.invoiceStatementAdd.i18n('foss.stl.consumer.invoiceStatementAdd.billingType')
		+"&nbsp;:&nbsp;"+consumer.invoiceStatementAdd.i18n('foss.stl.consumer.invoiceStatementAdd.businessPartnerInvoiceBill')
	},{
		xtype:'datefield',
		fieldLabel:consumer.invoiceStatementAdd.i18n('foss.stl.consumer.invoiceStatementAdd.createTime'),
		name:'createTime',
		columnWidth:.25,
		editable:false,
		format:'Y-m-d'
	},{
    	name:'customerCode',
    	columnWidth:.25,
    	fieldLabel:consumer.invoiceStatementAdd.i18n('foss.stl.consumer.invoiceStatementAdd.customerCode'),
		editable:false
	},{
		xtype:'datefield',
		fieldLabel:consumer.invoiceStatementAdd.i18n('foss.stl.consumer.invoiceStatementAdd.periodBeginDate'),
		name:'periodBeginDate',
		columnWidth:.25,
		editable:false,
		format:'Y-m-d'
	},{
		fieldLabel:consumer.invoiceStatementAdd.i18n('foss.stl.consumer.invoiceStatementAdd.companyName'),
		name:'companyName',
		columnWidth:.25,
		editable:false
	},{
		fieldLabel:consumer.invoiceStatementAdd.i18n('foss.stl.consumer.invoiceStatementAdd.customerName'),
		name:'customerName',
		labelWidth:95,
		columnWidth:.25,
		editable:false
	},{
		xtype:'datefield',
		fieldLabel:consumer.invoiceStatementAdd.i18n('foss.stl.consumer.invoiceStatementAdd.periodEndDate'),
		name:'periodEndDate',
		columnWidth:.25,
		editable:false,
		format:'Y-m-d'
	},{
		fieldLabel:consumer.invoiceStatementAdd.i18n('foss.stl.consumer.invoiceStatementAdd.createOrgName'),
		name:'createOrgName',
		columnWidth:.25,
		editable:false
	},{
		fieldLabel:consumer.invoiceStatementAdd.i18n('foss.stl.consumer.invoiceStatementAdd.sectorBenchmarkingEncoding'),
		name:'sectorBenchmarkingEncoding',
		columnWidth:.25,
		editable:false
	},{
		xtype:'commongeneraltaxpayerselector',
 		fieldLabel:consumer.invoiceStatementAdd.i18n('foss.stl.consumer.invoiceStatementAdd.invoiceHeadCode'),
 		readOnly:false,
 		name:'invoiceHeadCode',
 		displayField : 'billTitle',
 		valueField : 'billTitle',
 		allowBlank:false,
    	columnWidth:.25,
    	listeners:{
 			'change':function(th,newValue,oldValue){
 				//获取record
 				var record = th.findRecordByValue(newValue);
 				//获取form表单
 				var form = Ext.getCmp('T_consumer-invoiceStatementAdd_content').getInvoiceBasicInformation().getForm();
 				if(!record){
 					form.findField('taxId').setValue(null);
 					form.findField('registeredAddress').setValue(null);
 					form.findField('registeredTelephone').setValue(null);
 					form.findField('bank').setValue(null);
 					form.findField('accountBank').setValue(null);
 					form.findField('isGeneralTaxpayer').setValue(null);
				}
 			},
 			'select':function(th,records){
 				//获取选中记录
 				var record = records[0];
 				//获取form表单
 				var form = Ext.getCmp('T_consumer-invoiceStatementAdd_content').getInvoiceBasicInformation().getForm();
 				//判断是否选中记录为空
 				if(!Ext.isEmpty(record)){
 					form.findField('taxId').setValue(record.raw.taxId);
 					form.findField('registeredAddress').setValue(record.raw.regAddress);
 					form.findField('registeredTelephone').setValue(record.raw.regTel);
 					form.findField('bank').setValue(record.raw.bankName);
 					form.findField('accountBank').setValue(record.raw.bankNumber);
 					form.findField('isGeneralTaxpayer').setValue(record.raw.isTaxpayer);
 				}
 			}
 		}
	},{
		fieldLabel:consumer.invoiceStatementAdd.i18n('foss.stl.consumer.invoiceStatementAdd.taxId'),
		name:'taxId',
		columnWidth:.25,
		editable:false
	},{
		fieldLabel:consumer.invoiceStatementAdd.i18n('foss.stl.consumer.invoiceStatementAdd.totleFee'),
		name:'totleFee',
		columnWidth:.25,
		editable:false
	},{
		fieldLabel:'',
		name:'registeredAddress',
		columnWidth:.25,
		hidden:true
	},{
		fieldLabel:'',
		name:'registeredTelephone',
		columnWidth:.25,
		hidden:true
	},{
		fieldLabel:'',
		name:'bank',
		columnWidth:.25,
		hidden:true
	},{
		fieldLabel:'',
		name:'accountBank',
		columnWidth:.25,
		hidden:true
	},{
		fieldLabel:'',
		name:'isGeneralTaxpayer',
		columnWidth:.25,
		hidden:true
	},{
		fieldLabel:'',
		name:'departmentCode',
		columnWidth:.25,
		hidden:true
	}]
});

/**
 * 对账单基础信息form表单model(是否需要发票状态待定)
 */
Ext.define('Foss.invoiceStatementAdd.StatementFormModel', {
    extend: 'Ext.data.Model',
    fields: [{
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
        name: 'customerCode'
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
        name: 'companyName'
    }, {
        name: 'customerName'
    }, {
        name: 'periodEndDate',
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
        name: 'createOrgName'
    }, {
        name: 'statementBillNo'
    }, {
        name: 'sectorBenchmarkingEncoding'
    }, {
        name: 'invoiceHeadCode'
    }, {
        name: 'taxId'
    }, {
        name: 'totleFee'
    }, {
        name: 'registeredAddress'
    }, {
        name: 'registeredTelephone'
    }, {
        name: 'bank'
    }, {
        name: 'accountBank'
    }, {
        name: 'isGeneralTaxpayer'
    }, {
        name: 'departmentCode'
    }]
});

/**
 * 对账单本期明细form表单model
 */
Ext.define('Foss.invoiceStatementAdd.receivableFormModel', {
    extend: 'Ext.data.Model',
    fields: [{
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
        name: 'waybillNumber'
    }, {
    	name: 'totleFee'
    }, {
    	name: 'transportProperties'
    }, {
    	name: 'subTypesOfDocuments'
    }, {
    	name: 'customerName'
    }, {
    	name: 'customerCode'
    },{
        name: 'departmentName'
    }, {
        name: 'departmentCode'
    }, {
        name: 'customerName'
    }, {
        name: 'dateOfEntry',
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
    	name: 'dateOfReceipt',
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
        name: 'destinationStation'
    }, {
        name: 'id'
    }]
});

/**
 * 对账单表格store
 */
Ext.define('Foss.invoiceStatementAdd.StatementQueryGridStore', {
	extend:'Ext.data.Store',
    model:'Foss.invoiceStatementAdd.receivableFormModel',
    pageSize:50
});

/**
 * 生成对账单
 */
consumer.invoiceStatementAdd.statementSave = function(){
	//声明变量
	var baseInfoAdd,
		statementPreEntryGrid,
		invoiceManagementAllVo,
		invoiceManagementAddDto,
		entryGridLength,
		myMask
		;
		
	baseInfoAdd = Ext.getCmp('T_consumer-invoiceStatementAdd_content').getInvoiceBasicInformation().getForm();
	statementPreEntryAddGrid = Ext.getCmp('T_consumer-invoiceStatementAdd_content').getBillDetails();
	//对账单明细表格数据量
	entryGridLength = statementPreEntryAddGrid.getSelectionModel().getSelection();
	
	if(entryGridLength==0){
		Ext.Msg.alert(consumer.invoiceStatementAdd.i18n('foss.stl.consumer.common.alert'),consumer.invoiceStatementAdd.i18n('foss.stl.consumer.invoiceStatementAdd.currentDataIsNullWarning'));
		return false;
	}
	
	if(baseInfoAdd.findField('isGeneralTaxpayer').getValue()==null){
		Ext.Msg.alert(consumer.invoiceStatementAdd.i18n('foss.stl.consumer.common.alert'),consumer.invoiceStatementAdd.i18n('foss.stl.consumer.invoiceStatementAdd.isGeneralTaxpayerNull'));
		return false;
	}else if(baseInfoAdd.findField('invoiceHeadCode').getValue()==null){
		Ext.Msg.alert(consumer.invoiceStatementAdd.i18n('foss.stl.consumer.common.alert'),consumer.invoiceStatementAdd.i18n('foss.stl.consumer.invoiceStatementAdd.invoiceHeadCodeNull'));
		return false;
	}
	else{
		var isGeneralTaxpayer=baseInfoAdd.findField('isGeneralTaxpayer').getValue();
		if(isGeneralTaxpayer=='Y'){
			if(baseInfoAdd.findField('registeredAddress').getValue()==null){
				Ext.Msg.alert(consumer.invoiceStatementAdd.i18n('foss.stl.consumer.common.alert'),consumer.invoiceStatementAdd.i18n('foss.stl.consumer.invoiceStatementAdd.registeredAddressNull'));
				return false;
			}
			if(baseInfoAdd.findField('registeredTelephone').getValue()==null){
				Ext.Msg.alert(consumer.invoiceStatementAdd.i18n('foss.stl.consumer.common.alert'),consumer.invoiceStatementAdd.i18n('foss.stl.consumer.invoiceStatementAdd.registeredTelephoneNull'));
				return false;
			}
			if(baseInfoAdd.findField('bank').getValue()==null){
				Ext.Msg.alert(consumer.invoiceStatementAdd.i18n('foss.stl.consumer.common.alert'),consumer.invoiceStatementAdd.i18n('foss.stl.consumer.invoiceStatementAdd.bankNull'));
				return false;
			}
			if(baseInfoAdd.findField('accountBank').getValue()==null){
				Ext.Msg.alert(consumer.invoiceStatementAdd.i18n('foss.stl.consumer.common.alert'),consumer.invoiceStatementAdd.i18n('foss.stl.consumer.invoiceStatementAdd.accountBankNull'));
				return false;
			}if(baseInfoAdd.findField('taxId').getValue()==null){
				Ext.Msg.alert(consumer.invoiceStatementAdd.i18n('foss.stl.consumer.common.alert'),consumer.invoiceStatementAdd.i18n('foss.stl.consumer.invoiceStatementAdd.taxIdNull'));
				return false;
			}
		}
	}
	
	Ext.Msg.confirm(consumer.invoiceStatementAdd.i18n('foss.stl.consumer.common.alert'), consumer.invoiceStatementAdd.i18n('foss.stl.consumer.invoiceStatementAdd.isSaveStatement'), function(btn){
		if(btn=='yes'){
			var basemodel = Ext.create('Foss.invoiceStatementAdd.StatementFormModel', baseInfoAdd.getValues());
			invoiceManagementAddDto = basemodel.data
			var jsonData = new Array();
			var tempJ;
			var tempI;
			var waybillNumber;
			for(var i=0;i<entryGridLength.length;i++){
				tempI=entryGridLength[i];
				for(var j=0;j<entryGridLength.length;j++){
					tempJ=entryGridLength[j];
					if(tempJ.customerCode!=tempI.customerCode){
						Ext.Msg.alert(consumer.invoiceStatementAdd.i18n('foss.stl.consumer.common.alert'),consumer.invoiceStatementAdd.i18n('foss.stl.consumer.invoiceStatementAdd.hhDeptNoDiffrent'));
						return false;
					}
				}
				waybillNumber=entryGridLength[i].data.id;
				jsonData.push(waybillNumber);
			}
			invoiceManagementAddDto.billDetailNos=jsonData;
			invoiceManagementAllVo=new Object();
			invoiceManagementAllVo.invoiceManagementAddDto=invoiceManagementAddDto;
			
        	myMask =Ext.getCmp('Foss.statementbill.LoadMaskAdd_ID');
        	if(myMask==null){
        		myMask = new Ext.LoadMask(Ext.getCmp('T_consumer-invoiceStatementAdd_content'), {
	        		id:'Foss.statementbill.LoadMaskAdd_ID',
	    			msg:consumer.invoiceStatementAdd.i18n('foss.stl.consumer.invoiceStatementAdd.saveStatementMask'),
    			    removeMask : true// 完成后移除
        		});
        	}
			myMask.show();
	        
			Ext.Ajax.request({
				url :consumer.realPath('addStatement.action'), 
				jsonData:{'invoiceManagementAllVo':invoiceManagementAllVo},
				method:'post',
				success:function(response){
					var result = Ext.decode(response.responseText);
					var statementBillNo = result.invoiceManagementAllVo.invoiceManagementAddDto.statementBillNo;
					myMask.hide();
					//制作完后清空对账单界面数据
					baseInfoAdd.reset();
					statementPreEntryAddGrid.store.removeAll();
					Ext.getCmp('T_consumer-invoiceStatementAdd_content').getInvoiceBasicInformation().hide();
					Ext.getCmp('T_consumer-invoiceStatementAdd_content').getBillDetails().hide();
					Ext.Msg.alert(consumer.invoiceStatementAdd.i18n('foss.stl.consumer.common.alert'),
							consumer.invoiceStatementAdd.i18n('foss.stl.consumer.invoiceStatementAdd.statementBillNo') + statementBillNo);
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
					Ext.Msg.alert(consumer.invoiceStatementAdd.i18n('foss.stl.consumer.common.alert'),result.message);
				}
			});
		}
	});
}

/**
 * 对账单本期明细
 */
Ext.define('Foss.invoiceStatementAdd.BillDetails', {
	extend:'Ext.grid.Panel',
    title: consumer.invoiceStatementAdd.i18n('foss.stl.consumer.invoiceStatementAdd.billDetails'),
	frame:true,
	hidden:true,
	height:600,
	plugins:SoperateColumnEditing,
	selModel:Ext.create('Ext.selection.CheckboxModel'),
    store: Ext.create('Foss.invoiceStatementAdd.StatementQueryGridStore'),
    viewConfig:{
  		enableTextSelection : true//设置行可以选择，进而可以复制
  	},
    columns: [{
		header: consumer.invoiceStatementAdd.i18n('foss.stl.consumer.invoiceStatementAdd.businessDate'), 
		dataIndex: 'businessDate',
		renderer:function(value){
			if(value!=null){
				return Ext.Date.format(new Date(value), 'Y-m-d');
			}else{
				return null;
			}
		}
	},{
		header: consumer.invoiceStatementAdd.i18n('foss.stl.consumer.invoiceStatementAdd.waybillNo'),
		dataIndex: 'waybillNumber' 
	},{
		header: consumer.invoiceStatementAdd.i18n('foss.stl.consumer.invoiceStatementAdd.totleFee'), 
		dataIndex: 'totleFee'
	},{ 
		header: consumer.invoiceStatementAdd.i18n('foss.stl.consumer.invoiceStatementAdd.productType'),
		dataIndex: 'transportProperties',
		renderer:function(value){
			return Foss.pkp.ProductData.rendererSubmitToDisplay(value);
		}
	},{ 
		header: consumer.invoiceStatementAdd.i18n('foss.stl.consumer.invoiceStatementAdd.documentType'),
		dataIndex: 'subTypesOfDocuments',
		renderer:function(value){
			var temp=FossDataDictionary.rendererSubmitToDisplay(value, settlementDict.BILL_RECEIVABLE__BILL_TYPE);
			if(temp==null||''==temp){
				return value;
			}else{
				return temp;
			}
		}
		
	},{ 
		header: consumer.invoiceStatementAdd.i18n('foss.stl.consumer.invoiceStatementAdd.customerName'),
		dataIndex: 'customerName' 
	},{ 
		header: consumer.invoiceStatementAdd.i18n('foss.stl.consumer.invoiceStatementAdd.customerCode'),
		dataIndex: 'customerCode'
	},{ 
		header: consumer.invoiceStatementAdd.i18n('foss.stl.consumer.invoiceStatementAdd.deptName'),
		dataIndex: 'departmentName'
	},{ 
		header: consumer.invoiceStatementAdd.i18n('foss.stl.consumer.invoiceStatementAdd.deptCode'),
		dataIndex: 'departmentCode'
	},{ 
		header: consumer.invoiceStatementAdd.i18n('foss.stl.consumer.invoiceStatementAdd.billingDate'),
		dataIndex: 'dateOfEntry',
		renderer:function(value){
			if(value!=null){
				return Ext.Date.format(new Date(value), 'Y-m-d');
			}else{
				return null;
			}
		}
	},{
		header: consumer.invoiceStatementAdd.i18n('foss.stl.consumer.invoiceStatementAdd.signDate'),
		dataIndex: 'dateOfReceipt',
		renderer:function(value){
			if(value!=null){
				return Ext.Date.format(new Date(value), 'Y-m-d');
			}else{
				return null;
			}
		}
	},{
		header: consumer.invoiceStatementAdd.i18n('foss.stl.consumer.invoiceStatementAdd.aimAddress'),
		dataIndex: 'destinationStation'
	}],
	listeners:{  
	      'rowselect':function(sm,rowIndex,record){ 
	      },  
	      'rowdeselect':function(sm,rowIndex,record){  
	      },  
	      'selectionchange':function(sm){  
	    	  var basePanel = Ext.getCmp('T_consumer-invoiceStatementAdd_content').getInvoiceBasicInformation();
				var statementPreEntryAddGrid = Ext.getCmp('T_consumer-invoiceStatementAdd_content').getBillDetails();
				var totleFee=0;
				var preEntryGridLength = statementPreEntryAddGrid.getSelectionModel().getSelection();
				if(preEntryGridLength!=null){
					for(var i=0;i<preEntryGridLength.length;i++){
						tempI=preEntryGridLength[i];
						perFee=preEntryGridLength[i].data.totleFee;
						totleFee=totleFee+perFee;
					}
				}
				totleFee= Math.round(totleFee * 100) / 100;
				basePanel.getForm().findField('totleFee').setValue(totleFee); 
	      }  
	},
  	dockedItems: [{
	    xtype: 'toolbar',
	    dock: 'bottom',
	    layout:'column',
	    items: [{
			xtype:'container',
			border:false,
			html:'&nbsp;',
			columnWidth:.9
		},{
			xtype:'button',
			text:consumer.invoiceStatementAdd.i18n('foss.stl.consumer.invoiceStatementAdd.createStatement'),
			columnWidth:.1,
			handler:consumer.invoiceStatementAdd.statementSave
		}]
	}]
});

//编辑器
var SoperateColumnEditing = Ext.create('Ext.grid.plugin.CellEditing', {  
  	clicksToEdit : 1,
  	isObservable : false
});

Ext.onReady(function() {
	Ext.QuickTips.init();
	//创建查询页签面板
	var queryInfoTab = Ext.create('Foss.invoiceStatementAdd.QueryInfoTab');
	//创建发票基本属性面板
	var invoiceBasicInformation = Ext.create('Foss.invoiceStatementAdd.InvoiceBasicInformation');
	//对账单本期明细
	var billDetails = Ext.create('Foss.invoiceStatementAdd.BillDetails');
	//创建整个面板，将上面几块包含在一起
	Ext.create('Ext.panel.Panel',{
		id: 'T_consumer-invoiceStatementAdd_content',
		cls: "panelContentNToolbar",
		bodyCls: 'panelContentNToolbar-body',
		layout: 'auto',
		getInvoiceBasicInformation:function(){
			return invoiceBasicInformation;
		},
		getBillDetails:function(){
			return billDetails;
		},
		items: [queryInfoTab,invoiceBasicInformation,billDetails],
		renderTo: 'T_consumer-invoiceStatementAdd-body'
	});
});



