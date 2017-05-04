/**
 * 获取Grid组件信息
 * @returns
 */
consumer.queryCashPaymentReport.getPageObj=function(){
	var me=Ext.getCmp('T_consumer-queryCashPaymentReport_content');
	if(me){
		var grid = me.getQueryGrid();
		var store=grid.getStore();
		if(store.data.length==0){
			Ext.Msg.alert(consumer.queryCashPaymentReport.i18n('foss.stl.consumer.common.warmTips'), consumer.queryCashPaymentReport.i18n('foss.stl.consumer.common.emptyText'));
			return null;
		}
		return grid;	
	}
	return null;
};

/**
 * 设置打印公共参数
 * @returns
 */
consumer.queryCashPaymentReport.getParams=function(grid){  
	//获取Form信息
	var params =grid.store.submitParams;
	var form = Ext.getCmp('Foss_Consumer_CashPaymentReport_QueryByDate_ID');
	var formParams={
		'startDate' :form.getForm().findField('startDate').getValue(),
		'endDate' :form.getForm().findField('endDate').getValue(),
		'sourceBillType' : form.getForm().findField('sourceBillType').getValue(),
		'paymentOrgCode' : form.getForm().findField('paymentOrgCode').getValue(),
		'cInfo':stl.user.employee.empCode
	}; 
	
	//转化列头和列明
	var columns = grid.columns;
	var arrayColumns = [];
	var index=0;//列标
	//传入后台打印，与index拼接为后台列明
	var headerStr = "{";
	for(var i=1;i<columns.length;i++){
		if(columns[i].isHidden()==false){
			var hederName = columns[i].text;
			if(i!=columns.length-1){
				if(columns[i].text!=consumer.queryCashPaymentReport.i18n('foss.stl.consumer.common.actionColumn')){
					index = index+1;
					headerStr = headerStr+'\''+'columnName'+index+'\':\''+hederName+'\',';
					arrayColumns.push(columns[i].dataIndex);
				}
			}else{
				index = index+1;
				arrayColumns.push(columns[i].dataIndex);
				headerStr = headerStr+'\''+'columnName'+index+'\':\''+hederName+'\'';
			}
		}
	}
	//控制打印列数
	if(arrayColumns.length>15){
		Ext.Msg.alert(consumer.queryCashPaymentReport.i18n('foss.stl.consumer.common.warmTips'),consumer.queryCashPaymentReport.i18n('foss.stl.consumer.queryCashPaymentReport.printColumnLimit'));
		return null;
	}
	headerStr = headerStr+'}';
	var headerObject = Ext.decode(headerStr);
    targetObject = Ext.merge(headerObject,formParams);
	targetObject.arrayColumns = arrayColumns; 
	
return targetObject;
}

/**
 * 打印
 */ 
consumer.queryCashPaymentReport.printTheReportDetail=function(){
	var grid=consumer.queryCashPaymentReport.getPageObj();
	if(grid==null){
		return;
	}
	Ext.MessageBox.buttonText.yes = consumer.queryCashPaymentReport.i18n('foss.stl.consumer.common.OK');  
	Ext.MessageBox.buttonText.no = consumer.queryCashPaymentReport.i18n('foss.stl.consumer.common.cancel');
	Ext.Msg.confirm(consumer.queryCashPaymentReport.i18n('foss.stl.consumer.common.warmTips'),consumer.queryCashPaymentReport.i18n('foss.stl.consumer.queryCashPaymentReport.isPrint'), function(btn,text){
		if(btn == 'yes'){
			//获取打印参数信息
			var targetObject = consumer.queryCashPaymentReport.getParams(grid);
			do_printpreview('cashPaymentReport',targetObject,ContextPath.STL_WEB);
		}
	});
}

/**
 * 导出
 */
consumer.queryCashPaymentReport.exportReport=function(){
	
	var grid = Ext.getCmp('T_consumer-queryCashPaymentReport_content').getQueryGrid();
	if(grid.getStore().data.length<1){
		Ext.Msg.alert(consumer.queryCashPaymentReport.i18n('foss.stl.consumer.common.warmTips'),consumer.queryCashPaymentReport.i18n('foss.stl.consumer.common.emptyText'));
		return false;
	}
	
	Ext.MessageBox.buttonText.yes = consumer.queryCashPaymentReport.i18n('foss.stl.consumer.common.OK');  
	Ext.MessageBox.buttonText.no = consumer.queryCashPaymentReport.i18n('foss.stl.consumer.common.cancel'); 
	
	Ext.Msg.confirm( consumer.queryCashPaymentReport.i18n('foss.stl.consumer.common.warmTips'), consumer.queryCashPaymentReport.i18n('foss.stl.consumer.queryCashPaymentReport.isExport'), function(btn,text){
		if(btn == 'yes'){
			var params=grid.store.submitParams;
			var start = grid.store.start;
			var limit = grid.store.limit;
			
			Ext.apply(params,{
				'start':start,
				'limit':limit
			});
			
			if(!Ext.fly('queryCashPaymentReportForm')){
				var frm = document.createElement('form');
				frm.id = 'queryCashPaymentReportForm';
				frm.style.display = 'none';
				document.body.appendChild(frm);
			}
	
			Ext.Ajax.request({
				url:consumer.realPath('exportPaymentReport.action'),
				form: Ext.fly('queryCashPaymentReportForm'),
				params:params,
				method:'post',
				isUpload: true,
				success:function(response){
					var result = Ext.decode(response.responseText);
				},
				failure:function(response){
					var result = Ext.decode(response.responseText);
				}
			});
		}
	});
}


//通用时间校验
consumer.dateValidation = function(form){
	var startTime = form.findField('startDate').getValue();
	var endTime = form.findField('endDate').getValue();

		if(Ext.isEmpty(startTime)){
			Ext.Msg.alert(consumer.queryCashPaymentReport.i18n('foss.stl.consumer.common.warmTips'),consumer.queryCashPaymentReport.i18n('foss.stl.consumer.queryCashPaymentReport.startDateIsNullWarning'));
			return false;
		}
		
		if(Ext.isEmpty(endTime)){
			Ext.Msg.alert(consumer.queryCashPaymentReport.i18n('foss.stl.consumer.common.warmTips'),consumer.queryCashPaymentReport.i18n('foss.stl.consumer.queryCashPaymentReport.endDateIsNullWarning'));
			return false;
		}
		
		if(startTime > endTime){
			Ext.Msg.alert(consumer.queryCashPaymentReport.i18n('foss.stl.consumer.common.warmTips'),consumer.queryCashPaymentReport.i18n('foss.stl.consumer.queryCashPaymentReport.endDateBeforeBeginDateWarning'));
			return false;
		}

		var result = (Ext.Date.parse(endTime,'Y-m-d H:i:s') - Ext.Date.parse(startTime,'Y-m-d H:i:s'))/ (24 * 60 * 60 * 1000);
		result = parseInt(result);
		if( result > stl.DATE_THREE_DAYS_WEEK){
        			Ext.Msg.alert(consumer.queryCashPaymentReport.i18n('foss.stl.consumer.common.warmTips'),consumer.queryCashPaymentReport.i18n('foss.stl.consumer.queryCashPaymentReport.datesLimit')+ stl.DATE_THREE_DAYS_WEEK +consumer.queryCashPaymentReport.i18n('foss.stl.consumer.queryCashPaymentReport.day'));
        			return false;
        }
	
	return true;
}

//到付清查查询model
Ext.define('Foss.Consumer.CashPaymentReportModel',{
	extend:'Ext.data.Model',
	fields:[{
		name:'paymentNo'
	},{
		name:'paymentCompanyName'
	},{
		name:'paymentCompanyCode'
	},{
		name:'paymentOrgName'
	},{
		name:'paymentOrgCode'
	},{
		name:'accountDate'
	},{
		name:'businessDate'
	},{
		name:'payeeName'
	},{
		name:'amount',
		type:'long'
	},{
		name:'paymentType'
	},{
		name:'sourceBillType'
	},{
		name:'remitStatus'
	},{
		name:'isRedBack'
	},{
		name:'active'
	},{
		name:'auditStatus'
	}]
});


//日期查询列表Store
Ext.define('Foss.Consumer.CashPaymentReportStore',{
	extend:'Ext.data.Store',
	model:'Foss.Consumer.CashPaymentReportModel',
	pageSize:25,
	proxy:{
		type:'ajax',
		url:consumer.realPath('queryCashPaymentReportByDto.action'),
		reader:{
			type:'json',
			root:'cashPaymentReportVo.billPaymentList',
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


//第一个Tab:按日期查询
Ext.define('Foss.Consumer.CashPaymentReportTab', {
	extend:'Ext.form.Panel',
	bodyCls: 'autoHeight',
	cls: 'innerTabPanel',
	title: consumer.queryCashPaymentReport.i18n('foss.stl.consumer.common.queryByDate'),
	tabConfig: {
		width: 120
	},
	columnWidth:1,
	height :100,
	items : [{
       	name:'queryByDate',
        items:[{
        	xtype:'form',
        	id:'Foss_Consumer_CashPaymentReport_QueryByDate_ID',
        	defaults:{
	        	margin:'10 0 0 0',
	        	labelWidth:85,
	        	colspan:1
       		 },
        		layout:{
        			type :'table',
        			columns :3
        		},
		    items:[{
		    	xtype:'datetimefield_date97',
		    	id:'FOSS_Consumer_CashPaymentReport_StartTime_ID',
		    	name:'startDate',
		    	fieldLabel:consumer.queryCashPaymentReport.i18n('foss.stl.consumer.queryCashPaymentReport.payStartDate'),
		    	time:true,
		    	editable:false,
		    	allowBlank:false,
				value:stl.dateFormat(new Date(),stl.FORMAT_DATE)+stl.START_PREFIX,
				dateConfig: {
					el: 'FOSS_Consumer_CashPaymentReport_StartTime_ID-inputEl',
					dateFmt: 'yyyy-MM-dd HH:mi:ss'
				}
		    },{
		    	xtype:'datetimefield_date97',
		    	id:'FOSS_Consumer_CashPaymentReport_EndTime_ID',
		   	 	name:'endDate',
		    	fieldLabel:consumer.queryCashPaymentReport.i18n('foss.stl.consumer.queryCashPaymentReport.payEndDate'),
		    	time:true,
		    	editable:false,
		    	allowBlank:false,
				value:stl.dateFormat(new Date(),stl.FORMAT_DATE)+stl.END_PREFIX,
				dateConfig: {
					el: 'FOSS_Consumer_CashPaymentReport_EndTime_ID-inputEl',
					dateFmt: 'yyyy-MM-dd HH:mi:ss'
				}
		    },{
		    	xtype: 'combobox',
				name:'sourceBillType',
		        fieldLabel: consumer.queryCashPaymentReport.i18n('foss.stl.consumer.queryCashPaymentReport.billType'),
		        store:FossDataDictionary.getDataDictionaryStore('BILL_PAYMENT__SOURCE_BILL_TYPE',null,
		        		{'valueCode' : '',
						'valueName' : consumer.queryCashPaymentReport.i18n('foss.stl.consumer.common.all')
		        		},
		        		[consumer.queryCashPaymentReport.YF,
		        		consumer.queryCashPaymentReport.YS]),
				queryMode:'local',
		    	editable:false,
				displayField:'valueName',
				valueField:'valueCode'
		    },{
		    	xtype: 'dynamicorgcombselector',
				name:'paymentOrgCode',
		        fieldLabel: consumer.queryCashPaymentReport.i18n('foss.stl.consumer.queryCashPaymentReport.paymentOrg'),
				allowBlank: true,
				listWidth:300,//设置下拉框宽度
				isPaging:true //分页
		    },{
				xtype :'container',
				border :false,
				html :'&nbsp;'
			},{
				xtype :'container',
				layout:'column',
				items:[{
					xtype:'container',
					html:'&nbsp;',
					columnWidth:.7
				},{
					border :1,
					xtype:'button',
					columnWidth:.3,
					text:consumer.queryCashPaymentReport.i18n('foss.stl.consumer.common.query'),
					cls:'yellow_button',
					handler:function(){
						var form = this.up('form').getForm();
						if (form.isValid()) {
							if(consumer.dateValidation(form)){
								var cashPaymentReportStore = Ext.getCmp('Foss_Consumer_CashPaymentReportGrid_Id').store;
								
								var startDateTemp;
								var endDateTemp;
								startDateTemp = form.findField('startDate').getValue();
								endDateTemp = form.findField('endDate').getValue();
								
								var params = {
										'cashPaymentReportVo.dto.startDate':startDateTemp,
										'cashPaymentReportVo.dto.endDate':endDateTemp,
										'cashPaymentReportVo.dto.sourceBillType':form.findField('sourceBillType').getValue(),
										'cashPaymentReportVo.dto.paymentOrgCode':form.findField('paymentOrgCode').getValue()
								};	
								
								cashPaymentReportStore.setSubmitParams(params);
								cashPaymentReportStore.removeAll();
								
								cashPaymentReportStore.loadPage(1,{
									callback:function(records, operation, success) {
										
										//抛出异常  
								    	var rawData = cashPaymentReportStore.proxy.reader.rawData;
								    	if(!success && ! rawData.isException){
											Ext.Msg.alert(consumer.queryCashPaymentReport.i18n('foss.stl.consumer.common.warmTips'),rawData.message);
											return false;
										}
								    	
								    	//正常返回
								    	if(success){
								    		var result = Ext.decode(operation.response.responseText);
											Ext.getCmp('Foss_CashPaymentReportGrid_TotalCashAmount_Id').setValue(result.cashPaymentReportVo.totalCashAmount?result.cashPaymentReportVo.totalCashAmount:0);
											Ext.getCmp('Foss_CashPaymentReportGrid_TotalTelegraphicAmount_Id').setValue(result.cashPaymentReportVo.totalTelegraphicAmount?result.cashPaymentReportVo.totalTelegraphicAmount:0);
											Ext.getCmp('Foss_CashPaymentReportGrid_TotalBankCardAmount_Id').setValue(result.cashPaymentReportVo.totalBankCardAmount?result.cashPaymentReportVo.totalTelegraphicAmount:0);
											Ext.getCmp('Foss_CashPaymentReportGrid_TotalChequeAmount_Id').setValue(result.cashPaymentReportVo.totalChequeAmount?result.cashPaymentReportVo.totalChequeAmount:0);
											Ext.getCmp('Foss_CashPaymentReportGrid_TotalAuditAmount_Id').setValue(result.cashPaymentReportVo.totalAuditAmount?result.cashPaymentReportVo.totalChequeAmount:0);
											Ext.getCmp('Foss_CashPaymentReportGrid_TotalUnauditAmount_Id').setValue(result.cashPaymentReportVo.totalUnauditAmount?result.cashPaymentReportVo.totalUnauditAmount:0);
								    	}
								}
								});}
						}else{
							Ext.Msg.alert(consumer.queryCashPaymentReport.i18n('foss.stl.consumer.common.warmTips'),consumer.queryCashPaymentReport.i18n('foss.stl.consumer.common.validateFailAlert'));
						}
					}
				}]
			}]
        }]
	}]
});


//现金支出报表查询列表
Ext.define('Foss.Consumer.CashPaymentReportGrid',{
	extend:'Ext.grid.Panel',
	frame:true,
	height:500,
	emptyText: consumer.queryCashPaymentReport.i18n('foss.stl.consumer.common.emptyText'),
	sortableColumns: false,
	viewConfig: {         
		enableTextSelection: true       
	}, 
	columns:[{
		header: consumer.queryCashPaymentReport.i18n('foss.stl.consumer.common.paymentNo'), 
		dataIndex: 'paymentNo'
	},{
		header: consumer.queryCashPaymentReport.i18n('foss.stl.consumer.queryCashPaymentReport.paymentCompanyName'), 
		dataIndex: 'paymentCompanyName'
	},{
		header: consumer.queryCashPaymentReport.i18n('foss.stl.consumer.queryCashPaymentReport.paymentOrgName'), 
		dataIndex: 'paymentOrgName'		
	},{
		header: consumer.queryCashPaymentReport.i18n('foss.stl.consumer.queryCashPaymentReport.businessDate'), 
		dataIndex: 'businessDate',
		renderer:function(value){
			if(value!=null){
				return Ext.Date.format(new Date(value), 'Y-m-d');
			}else{
				return value;
			}
		}
	},{
		header: consumer.queryCashPaymentReport.i18n('foss.stl.consumer.queryCashPaymentReport.payeeName'), 
		dataIndex: 'payeeName'
	},{
		header: consumer.queryCashPaymentReport.i18n('foss.stl.consumer.common.amount'), 
		dataIndex: 'amount',
		xtype: 'numbercolumn', 
		format:'0,0.00',
		align:'right'
	},{
		header: consumer.queryCashPaymentReport.i18n('foss.stl.consumer.queryCashPaymentReport.paymentType'), 
		dataIndex: 'paymentType',
		renderer:function(value){
			var displayField = FossDataDictionary. rendererSubmitToDisplay (value,'SETTLEMENT__PAYMENT_TYPE');
    		return displayField;
		}
	},{
		header: consumer.queryCashPaymentReport.i18n('foss.stl.consumer.queryCashPaymentReport.billType'), 
		dataIndex: 'sourceBillType',
		renderer:function(value){
			var displayField = FossDataDictionary. rendererSubmitToDisplay (value,'BILL_PAYMENT__SOURCE_BILL_TYPE');
    		return displayField;
		}
	},{
		header: consumer.queryCashPaymentReport.i18n('foss.stl.consumer.queryCashPaymentReport.remitStatus'), 
		dataIndex: 'remitStatus',
		renderer:function(value){
			var displayField = FossDataDictionary. rendererSubmitToDisplay (value,'BILL_PAYMENT__REMIT_STATUS');
    		return displayField;
		}
	},{	
		header: consumer.queryCashPaymentReport.i18n('foss.stl.consumer.common.isRedBack'), 
		dataIndex: 'isRedBack',
		hidden:true,
		renderer:function(value){
			var displayField = FossDataDictionary. rendererSubmitToDisplay (value,'SETTLEMENT__IS_RED_BACK');
    		return displayField;
		}
	},{	
		header: consumer.queryCashPaymentReport.i18n('foss.stl.consumer.common.active'), 
		dataIndex: 'active',
		hidden:true,
		renderer:function(value){
			var displayField = FossDataDictionary.rendererSubmitToDisplay(value,settlementDict.FOSS_ACTIVE);
    		return displayField;
		}
	},{	
		header: consumer.queryCashPaymentReport.i18n('foss.stl.consumer.queryReceivablePaymentBill.auditStatus'), 
		dataIndex: 'auditStatus',
		hidden:true,
		renderer:function(value){
			var displayField = FossDataDictionary.rendererSubmitToDisplay(value,"BILL_REPAYMENT__AUDIT_STATUS");
    		return displayField;
		}
	}]
});

/**
 * 主启动方法
 */	
Ext.onReady(function() {
		Ext.QuickTips.init();
		
	var cashPaymentReportTab = Ext.create('Foss.Consumer.CashPaymentReportTab');

	//创建按日期查询到付清查单列表Store
	var cashPaymentReportStore = Ext.create('Foss.Consumer.CashPaymentReportStore');	
	
	//按日期查询到付清查列表Grid
	var cashPaymentReportGrid = Ext.create('Foss.Consumer.CashPaymentReportGrid',{
		id:'Foss_Consumer_CashPaymentReportGrid_Id',
		store:cashPaymentReportStore,
		dockedItems: [{
			xtype: 'toolbar',
			dock: 'top',
			layout:'column',
			defaults :{
				margin :'0 0 5 3'
			},
			items: [{
				xtype:'button',
				text:consumer.queryCashPaymentReport.i18n('foss.stl.consumer.common.export'),
				columnWidth:.1,
				handler:consumer.queryCashPaymentReport.exportReport
			},{
				xtype:'button',
				text:consumer.queryCashPaymentReport.i18n('foss.stl.consumer.common.print'),
				columnWidth:.1,
				handler :consumer.queryCashPaymentReport.printTheReportDetail
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
			    		name:'cashAmount',
			    		width:140,
			    		id:'Foss_CashPaymentReportGrid_TotalCashAmount_Id',
			    		labelWidth:40,
			    		fieldLabel:consumer.queryCashPaymentReport.i18n('foss.stl.consumer.queryCashPaymentReport.cash')
			    	},{
			    		xtype:'textfield',
			    		readOnly:true,
			    		name:'telegraphAmount',
			    		width:140,
			    		id:'Foss_CashPaymentReportGrid_TotalTelegraphicAmount_Id',
			    		labelWidth:40,
			    		fieldLabel:consumer.queryCashPaymentReport.i18n('foss.stl.consumer.queryCashPaymentReport.telegraphic')
			    	},{
			    		xtype:'textfield',
			    		readOnly:true,
			    		name:'bankCardAmount',
			    		width:160,
			    		id:'Foss_CashPaymentReportGrid_TotalBankCardAmount_Id',
			    		labelWidth:60,
			    		fieldLabel:consumer.queryCashPaymentReport.i18n('foss.stl.consumer.queryCashPaymentReport.bankCard')
			    	},{
			    		xtype:'textfield',
			    		readOnly:true,
			    		name:'chequeAmount',
			    		width:140,
			    		id:'Foss_CashPaymentReportGrid_TotalChequeAmount_Id',
			    		labelWidth:40,
			    		fieldLabel:consumer.queryCashPaymentReport.i18n('foss.stl.consumer.queryCashPaymentReport.cheque')
			    	},{
			    		xtype:'textfield',
			    		readOnly:true,
			    		name:'auditAmount',
			    		width:190,
			    		id:'Foss_CashPaymentReportGrid_TotalAuditAmount_Id',
			    		labelWidth:88,
			    		fieldLabel:consumer.queryCashPaymentReport.i18n('foss.stl.consumer.queryCashPaymentReport.auditAmount')
			    	},{
			    		xtype:'textfield',
			    		readOnly:true,
			    		name:'unauditAmount',
			    		width:190,
			    		id:'Foss_CashPaymentReportGrid_TotalUnauditAmount_Id',
			    		labelWidth:88,
			    		fieldLabel:consumer.queryCashPaymentReport.i18n('foss.stl.consumer.queryCashPaymentReport.unAuditAmount')
			    	}]
			},Ext.create('Deppon.StandardPaging', {
	    		store: cashPaymentReportStore,
	    		pageSize: 25,
	    		columnWidth:.4,
	    		plugins: Ext.create('Deppon.ux.PageSizePlugin', {
					//设置分页记录最大值，防止输入过大的数值
					maximumSize: 200
				})
	    })]
		}]
	});
		
		Ext.create('Ext.panel.Panel',{
			id: 'T_consumer-queryCashPaymentReport_content',
			cls: "panelContentNToolbar",
			bodyCls: 'panelContentNToolbar-body',
			layout: 'auto',
			//获得查询结果GRID
			getQueryGrid : function() {
				return cashPaymentReportGrid;
			},
			items: [cashPaymentReportTab,cashPaymentReportGrid],
			renderTo: 'T_consumer-queryCashPaymentReport-body'
		});
	});



