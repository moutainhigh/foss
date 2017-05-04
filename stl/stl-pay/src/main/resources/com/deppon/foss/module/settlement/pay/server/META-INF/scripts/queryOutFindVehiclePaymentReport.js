pay.ofvpaymentRpt.ONEPAGESIZE = 100; //每页显示的条数
pay.ofvpaymentRpt.QUERY_BY_DATE='QBD';// 按时间查询
pay.ofvpaymentRpt.QUERY_BY_VAB='QBVAB';// 按车次号查询
pay.ofvpaymentRpt.QUERY_BY_WORKFLOW_NOS='QBWFN';// 按工作流号查询
pay.ofvpaymentRpt.QUERY_DATE_RANGE=10; //查询时间段天数限制

/**
 * Form重置方法
 */
pay.ofvpaymentRpt.OfvpaymentRptQueryReset=function(){
	this.up('form').getForm().reset();
}

pay.ofvpaymentRpt.verifyQueryDateBound=function(params){
	var startDate = params['ofvPaymentReportVo.dto.startPaymentDate'];
	var endDate =  params['ofvPaymentReportVo.dto.endPaymentDate'];
	
	//比较起始日期和结束日期
	var compareTwoDate = stl.compareTwoDate(startDate,endDate);
	if(compareTwoDate>pay.ofvpaymentRpt.QUERY_DATE_RANGE){
		Ext.Msg.alert(pay.ofvpaymentRpt.i18n('foss.stl.pay.ofvpaymentRpt.alert'),pay.ofvpaymentRpt.i18n('foss.stl.pay.ofvpaymentRpt.queryDateOutOfBound')+pay.ofvpaymentRpt.QUERY_DATE_RANGE);
		return true;
	}else if(compareTwoDate<1){
		Ext.Msg.alert(pay.ofvpaymentRpt.i18n('foss.stl.pay.ofvpaymentRpt.alert'),pay.ofvpaymentRpt.i18n('foss.stl.pay.ofvpaymentRpt.dateEndBeforeStatrtWarining'));
		return true;
	}
}

//导出外请车付款报表
pay.ofvpaymentRpt.OfvpaymentRptListExport = function(){
	
	var grid = Ext.getCmp('T_pay-outfindvehiclepaymentReport_content').getOfvPaymentReportGrid();
	if(grid.getStore().data.length<1){
		Ext.Msg.alert(pay.ofvpaymentRpt.i18n('foss.stl.pay.common.alert'),pay.ofvpaymentRpt.i18n('foss.stl.pay.ofvpaymentRpt.exportFailAlertByNoData'));
		return false;
	}
	
	Ext.MessageBox.buttonText.yes = pay.ofvpaymentRpt.i18n('foss.stl.pay.common.ok');  
	Ext.MessageBox.buttonText.no = pay.ofvpaymentRpt.i18n('foss.stl.pay.common.cancel'); 
	
	Ext.Msg.confirm( pay.ofvpaymentRpt.i18n('foss.stl.pay.common.alert'), pay.ofvpaymentRpt.i18n('foss.stl.pay.ofvpaymentRpt.isConfirmExportAlert'), function(btn,text){
		if(btn == 'yes'){
			var params=grid.store.submitParams;
			if(!Ext.fly('ofvpaymentRptListExportForm')){
				var frm = document.createElement('form');
				frm.id = 'ofvpaymentRptListExportForm';
				frm.style.display = 'none';
				document.body.appendChild(frm);
			}
			if(pay.ofvpaymentRpt.verifyQueryDateBound(params)){
				return false;
			};
			Ext.Ajax.request({
				url:pay.realPath('exportOfvpaymentRptList.action'),
				form: Ext.fly('ofvpaymentRptListExportForm'),
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

/**
 * 打印
 */
pay.ofvpaymentRpt.print = function(){
	
	//获取grid
	var grid = Ext.getCmp('T_pay-outfindvehiclepaymentReport_content').getOfvPaymentReportGrid();
	if(grid.getStore().data.length<1){
		Ext.Msg.alert(pay.ofvpaymentRpt.i18n('foss.stl.pay.common.alert'),pay.ofvpaymentRpt.i18n('foss.stl.pay.ofvpaymentRpt.printFailAlertByNoData'));
		return false;
	}
	
	// 获取选中的付款单数据
	var selections = grid.getSelectionModel().getSelection();
	
	// 如果未选中数据，提示至少选择一条记录进行操作
	if(selections.length==0){
		Ext.Msg.alert(pay.ofvpaymentRpt.i18n('foss.stl.pay.common.alert'),pay.ofvpaymentRpt.i18n('foss.stl.pay.ofvpaymentRpt.printFailAlertByNoSelectedData'));
		return false;
	}
	
	//生成工作流号，并默认取选择的第一条的工作流号
	var workFlowNo = selections[0].get('workFlowNo');
	//如果第一条的工作流号为空提示没有工作流号不允许打印
	if(workFlowNo==null||workFlowNo==''){
		Ext.Msg.alert(pay.ofvpaymentRpt.i18n('foss.stl.pay.common.alert'),pay.ofvpaymentRpt.i18n('foss.stl.pay.ofvpaymentRpt.printFailAlertByNoWorkFlowNo'));
		return false;
	}
	
	//循环选中数据
	for(var i=0;i<selections.length;i++){
		
		//如果工作流号为空，提示没有工作流号不允许打印
		if(selections[i].get('workFlowNo')==null||selections[i].get('workFlowNo')==''){
			Ext.Msg.alert(pay.ofvpaymentRpt.i18n('foss.stl.pay.common.alert'),pay.ofvpaymentRpt.i18n('foss.stl.pay.ofvpaymentRpt.printFailAlertByNoWorkFlowNo'));
			return false;
		}
		
		//如果工作流号不唯一，提示工作流号不唯一不允许打印
		if(workFlowNo!=selections[i].get('workFlowNo')){
			Ext.Msg.alert(pay.ofvpaymentRpt.i18n('foss.stl.pay.common.alert'),pay.ofvpaymentRpt.i18n('foss.stl.pay.ofvpaymentRpt.printFailAlertByWorkFlowNoNotUnique'));
			return false;
		}
	}
	
	//提示是否要打印
	Ext.Msg.confirm( pay.ofvpaymentRpt.i18n('foss.stl.pay.common.alert'), pay.ofvpaymentRpt.i18n('foss.stl.pay.ofvpaymentRpt.isConfirmPrintAlert'), function(btn,text){
		if(btn=='yes'){
			
			//将当前登录部门传递给后台
			var empCode = stl.emp.empCode;
			
			//打印
			do_printpreview('ofvpaymentreport',{workFlowNo:workFlowNo,empCode:empCode},ContextPath.STL_WEB);
		}
	});
}

// 付款方式model
Ext.define('Foss.ofvPaymentReport.OfvPaymentReportTypeModel',{
	extend:'Ext.data.Model',
	fields:[{
		name:'name'
	},{
		name:'value'
	}]
});

// 付款方式Store
Ext.define('Foss.ofvPaymentReport.OfvPaymentReportTypeStore',{
	extend:'Ext.data.Store',
	model:'Foss.ofvPaymentReport.OfvPaymentReportTypeModel',
	data:{
		'items':[
			{name:pay.ofvpaymentRpt.i18n('foss.stl.pay.ofvpaymentRpt.all'),value:''},
			{name:pay.ofvpaymentRpt.i18n('foss.stl.pay.ofvpaymentRpt.payableTypeF'),value:'F'},
			{name:pay.ofvpaymentRpt.i18n('foss.stl.pay.ofvpaymentRpt.payableTypeL'),value:'L'}
		]
	},
	proxy:{
		type:'memory',
		reader:{
			type:'json',
			root:'items'
		}
	},
	autoLoad: true
});

//按日期查询 Form
Ext.define('Foss.ofvpaymentRpt.PaymentQueryInfoByDate',{
	extend:'Ext.form.Panel',
	frame:false,
	height :200,
	defaults:{
		margin :'10 10 10 10',
		labelWidth :85,
    	colspan : 1, 
    	columnWidth:.26
	},
	layout:{
		type :'column'
	},
	items : [{
		xtype : 'datefield',
		name : 'ofvPaymentReportVo.dto.startPaymentDate',
		fieldLabel : pay.ofvpaymentRpt.i18n('foss.stl.pay.ofvpaymentRpt.startPaymentDate'),
		value : new Date(),
		validator : function(value) {
			if (value == null || value == '') {
				return pay.ofvpaymentRpt.i18n('foss.stl.pay.ofvpaymentRpt.startPaymentDateIsNotNull');
			}
			return true;
		},
		format : 'Y-m-d',
		allowBlank : false,
		editable:false,
    	maxValue:new Date()
	},{
		xtype : 'datefield',
		name : 'ofvPaymentReportVo.dto.endPaymentDate',
		fieldLabel : pay.ofvpaymentRpt.i18n('foss.stl.pay.ofvpaymentRpt.endPaymentDate'),
		value : new Date(),
		validator : function(value) {
			if (value == null || value == '') {
				return pay.ofvpaymentRpt.i18n('foss.stl.pay.ofvpaymentRpt.endPaymentDateIsNotNull');
			}
			return true;
		},
		format : 'Y-m-d',
		allowBlank : false,
		editable:false,
    	maxValue:new Date()
	},{
		xtype : 'combobox',
		name : 'ofvPaymentReportVo.dto.payableType',
		fieldLabel : pay.ofvpaymentRpt.i18n('foss.stl.pay.ofvpaymentRpt.payableType'),
		store:FossDataDictionary.getDataDictionaryStore(settlementDict.BILL_PAYABLE__PAYABLE_TYPE,null,{
			 'valueCode': '',
       		 'valueName': pay.ofvpaymentRpt.i18n('foss.stl.pay.ofvpaymentRpt.all')
		}),
		queryModel : 'local',
		displayField : 'valueName',
		valueField : 'valueCode',
    	editable:false,
		value:''
	},{
    	xtype: 'dynamicorgcombselector',
		name:'ofvPaymentReportVo.dto.origDept',
        fieldLabel: pay.ofvpaymentRpt.i18n('foss.stl.pay.ofvpaymentRpt.origDept'),
		allowBlank: true,
		listWidth:300,//设置下拉框宽度
		isPaging:true //分页
    },{
    	xtype: 'dynamicorgcombselector',
		name:'ofvPaymentReportVo.dto.destDept',
        fieldLabel: pay.ofvpaymentRpt.i18n('foss.stl.pay.ofvpaymentRpt.destDept'),
		allowBlank: true,
		listWidth:300,//设置下拉框宽度
		isPaging:true //分页
    },{
		xtype : 'combobox',
		name : 'ofvPaymentReportVo.dto.auditStatus',
		fieldLabel : pay.ofvpaymentRpt.i18n('foss.stl.pay.ofvpaymentRpt.auditStatus'),
		store:FossDataDictionary.getDataDictionaryStore(settlementDict.BILL_PAYMENT__AUDIT_STATUS,null,{
			 'valueCode': '',
       		 'valueName': pay.ofvpaymentRpt.i18n('foss.stl.pay.ofvpaymentRpt.all')
		}),
		queryModel : 'local',
		displayField : 'valueName',
		valueField : 'valueCode',
    	editable:false,
		value:''
	},{
		border: 1,
		xtype:'container',
		columnWidth:1,
		defaultType:'button',
		layout:'column',
		items:[
		    {
		    	text:pay.ofvpaymentRpt.i18n('foss.stl.pay.common.reset'),
			  		columnWidth:.1,
			  		handler:pay.ofvpaymentRpt.OfvpaymentRptQueryReset
		  	},{
				xtype:'container',
				border:false,
				html:'&nbsp;',
				columnWidth:.58
			},
		  	{
				  text:pay.ofvpaymentRpt.i18n('foss.stl.pay.common.query'),
				  columnWidth:.1,
				  cls:'yellow_button',  
				  handler:function(){
						var form = this.up('form').getForm();
						var btn = this;
						//设置该按钮灰掉
						btn.disable(false);
						//30秒后自动解除灰掉效果
						setTimeout(function() {
							btn.enable(true);
						}, 30000);
					if (form.isValid()) {
						
						// 获取FORM所有值
						var params = form.getValues();
						if(pay.ofvpaymentRpt.verifyQueryDateBound(params)){
							return false;
						};
						var grid = Ext.getCmp('T_pay-outfindvehiclepaymentReport_content').getOfvPaymentReportGrid();
						var ofvPaymentStore = grid.store;
						ofvPaymentStore.removeAll();
						ofvPaymentStore.setSubmitParams(params);
						
						ofvPaymentStore.loadPage(1,{
							callback:function(records, operation, success) {
								var result = Ext.decode(operation.response.responseText);
								
								toolBar = grid.getDockedItems('toolbar[dock="bottom"]')[0];
								toolBar.getComponent('totalRecordsInDB').setValue(result.ofvPaymentReportVo.ofvPaymentReportResultDto.totalRecordsInDB);
								toolBar.getComponent('totalFeeSum').setValue(stl.amountFormat(result.ofvPaymentReportVo.ofvPaymentReportResultDto.totalFeeSum));
								toolBar.getComponent('prePaidFeeSum').setValue(stl.amountFormat(result.ofvPaymentReportVo.ofvPaymentReportResultDto.prePaidFeeSum));
								toolBar.getComponent('arrivePaidFeeSum').setValue(stl.amountFormat(result.ofvPaymentReportVo.ofvPaymentReportResultDto.arrivePaidFeeSum));
								toolBar.getComponent('adjustPaidFeeSum').setValue(stl.amountFormat(result.ofvPaymentReportVo.ofvPaymentReportResultDto.adjustPaidFeeSum));
								
							}
							});
					}else{
						Ext.Msg.alert(pay.ofvpaymentRpt.i18n('foss.stl.pay.common.alert'),pay.ofvpaymentRpt.i18n('foss.stl.pay.common.validateFailAlert'));
					}
		   }
	   }]
	}]			
});

//按工作流号查询 Form
Ext.define('Foss.ofvpaymentRpt.PaymentQueryInfoByWorkFlowNos',{
	extend:'Ext.form.Panel',
	frame:false,
	columnWidth:1,
	height :140,
	defaults:{
		margin :'10 10 10 10',
		labelWidth :85,
    	colspan : 1 
	},
	layout:{
		type :'column',
		columns :2
	},
	items : [{
		xtype:'textarea',
		fieldLabel:pay.ofvpaymentRpt.i18n('foss.stl.pay.ofvpaymentRpt.workFlowNo'),
		regex:/^((\s)*[A-Za-z0-9]{6,50}[;,])*((\s)*[A-Za-z0-9]{6,50}[;,]?)(\s)*$/i,
		emptyText: pay.ofvpaymentRpt.i18n('foss.stl.pay.ofvpaymentRpt.sourceBillNosEmptyText'),
		height : 80,
		allowBlank:false,
		name:'workFlowNos',
		columnWidth:.4
	},{
		xtype:'container',
		margin:'10 0 0 10 ',
		items:[{
			xtype:'component',
			padding:'0 0 0 10',
			autoEl:{
				tag:'div',
				html:'<span style="color:red;">'+pay.ofvpaymentRpt.i18n('foss.stl.pay.common.WorkFlowNosNots')+'</span>'
			}
		}],
		columnWidth:.3
	},{

		border: 1,
		xtype:'container',
		columnWidth:1,
		defaultType:'button',
		layout:'column',
		items:[
		    {
		    	text:pay.ofvpaymentRpt.i18n('foss.stl.pay.common.reset'),
			  		columnWidth:.1,
			  		handler:pay.ofvpaymentRpt.OfvpaymentRptQueryReset
		  	},{
				xtype:'container',
				border:false,
				html:'&nbsp;',
				columnWidth:.195
			},{
				  text:pay.ofvpaymentRpt.i18n('foss.stl.pay.common.query'),
				  columnWidth:.1,
				  cls:'yellow_button',  
				  handler:function(){
					var form = this.up('form').getForm();
					if (form.isValid()) {
						
						// 获取FORM所有值
						var params = {};
						
						var workFlowNos = form.findField('workFlowNos').getValue();
						var workFlowNosArray_tmp = stl.splitToArray(workFlowNos);
						var workFlowNosArray=new Array();
						
						for(var i=0;i<workFlowNosArray_tmp.length;i++){
							if(workFlowNosArray_tmp[i].trim()!=''){
								workFlowNosArray.push(workFlowNosArray_tmp[i].trim());
							} 
						}
						 
						if(workFlowNosArray.length==0){
							Ext.Msg.alert(pay.ofvpaymentRpt.i18n('foss.stl.pay.common.alert'),pay.ofvpaymentRpt.i18n('foss.stl.pay.ofvpaymentRpt.workFlowNosNotInputAlert'));
						 	return false;
						}
						if(workFlowNosArray.length>10){
							Ext.Msg.alert(pay.ofvpaymentRpt.i18n('foss.stl.pay.common.alert'),pay.ofvpaymentRpt.i18n('foss.stl.pay.common.queryNosLimit'));
						 	return false;
						}
						
						Ext.apply(params,{
							'ofvPaymentReportVo.dto.workFlowNosArray' : workFlowNosArray
						});
						
						var grid = Ext.getCmp('T_pay-outfindvehiclepaymentReport_content').getOfvPaymentReportGrid();
						var ofvPaymentStore = grid.store;
						ofvPaymentStore.removeAll();
						ofvPaymentStore.setSubmitParams(params);
						
						ofvPaymentStore.loadPage(1,{
							callback:function(records, operation, success) {
								var result = Ext.decode(operation.response.responseText);
								
								toolBar = grid.getDockedItems('toolbar[dock="bottom"]')[0];
								toolBar.getComponent('totalRecordsInDB').setValue(result.ofvPaymentReportVo.ofvPaymentReportResultDto.totalRecordsInDB);
								toolBar.getComponent('totalFeeSum').setValue(stl.amountFormat(result.ofvPaymentReportVo.ofvPaymentReportResultDto.totalFeeSum));
								toolBar.getComponent('prePaidFeeSum').setValue(stl.amountFormat(result.ofvPaymentReportVo.ofvPaymentReportResultDto.prePaidFeeSum));
								toolBar.getComponent('arrivePaidFeeSum').setValue(stl.amountFormat(result.ofvPaymentReportVo.ofvPaymentReportResultDto.arrivePaidFeeSum));
								toolBar.getComponent('adjustPaidFeeSum').setValue(stl.amountFormat(result.ofvPaymentReportVo.ofvPaymentReportResultDto.adjustPaidFeeSum));
								
							}
							});
					}else{
						Ext.Msg.alert(pay.ofvpaymentRpt.i18n('foss.stl.pay.common.alert'),pay.ofvpaymentRpt.i18n('foss.stl.pay.common.validateFailAlert'));
					}
		   }
	   }]
	}]			
});

//按车次查询 Form
Ext.define('Foss.ofvpaymentRpt.PaymentQueryInfoByvehicleAssembleBillNos',{
	extend:'Ext.form.Panel',
	frame:false,
	columnWidth:1,
	height :140,
	defaults:{
		margin :'10 10 10 10',
		labelWidth :85,
    	colspan : 1 
	},
	layout:{
		type :'column',
		columns :2
	},
	items : [{
		xtype:'textarea',
		fieldLabel:pay.ofvpaymentRpt.i18n('foss.stl.pay.ofvpaymentRpt.vehicleAssembleBillNoSSS'),
		regex:/^((\s)*[A-Za-z0-9]{6,50}[;,])*((\s)*[A-Za-z0-9]{6,50}[;,]?)(\s)*$/i,
		emptyText: pay.ofvpaymentRpt.i18n('foss.stl.pay.ofvpaymentRpt.sourceBillNosEmptyText'),
		height : 80,
		allowBlank:false,
		name:'vchAbBillNos',
		columnWidth:.4
	},{
		xtype:'container',
		margin:'10 0 0 10 ',
		items:[{
			xtype:'component',
			padding:'0 0 0 10',
			autoEl:{
				tag:'div',
				html:'<span style="color:red;">'+pay.ofvpaymentRpt.i18n('foss.stl.pay.common.vehicleAssembleBillNosNots')+'</span>'
			}
		}],
		columnWidth:.3
	},{

		border: 1,
		xtype:'container',
		columnWidth:1,
		defaultType:'button',
		layout:'column',
		items:[
		    {
		    	text:pay.ofvpaymentRpt.i18n('foss.stl.pay.common.reset'),
			  		columnWidth:.1,
			  		handler:pay.ofvpaymentRpt.OfvpaymentRptQueryReset
		  	},{
				xtype:'container',
				border:false,
				html:'&nbsp;',
				columnWidth:.195
			},{
				  text:pay.ofvpaymentRpt.i18n('foss.stl.pay.common.query'),
				  columnWidth:.1,
				  cls:'yellow_button',  
				  handler:function(){
					var form = this.up('form').getForm();
					if (form.isValid()) {
						
						// 获取FORM所有值
						var params = {};
						
						var vchAbBillNos = form.findField('vchAbBillNos').getValue();
						var vchAbBillNosArray_tmp = stl.splitToArray(vchAbBillNos);
						var vchAbBillNosArray=new Array();
						
						for(var i=0;i<vchAbBillNosArray_tmp.length;i++){
							if(vchAbBillNosArray_tmp[i].trim()!=''){
								vchAbBillNosArray.push(vchAbBillNosArray_tmp[i].trim());
							} 
						}
						 
						if(vchAbBillNosArray.length==0){
							Ext.Msg.alert(pay.ofvpaymentRpt.i18n('foss.stl.pay.common.alert'),pay.ofvpaymentRpt.i18n('foss.stl.pay.ofvpaymentRpt.vABNosNotInputAlert'));
						 	return false;
						}
						if(vchAbBillNosArray.length>10){
							Ext.Msg.alert(pay.ofvpaymentRpt.i18n('foss.stl.pay.common.alert'),pay.ofvpaymentRpt.i18n('foss.stl.pay.common.queryNosLimit'));
						 	return false;
						}
						
						Ext.apply(params,{
							'ofvPaymentReportVo.dto.vchAbillNosArray' : vchAbBillNosArray
						});
						
						var grid = Ext.getCmp('T_pay-outfindvehiclepaymentReport_content').getOfvPaymentReportGrid();
						var ofvPaymentStore = grid.store;
						ofvPaymentStore.removeAll();
						ofvPaymentStore.setSubmitParams(params);
						
						ofvPaymentStore.loadPage(1,{
							callback:function(records, operation, success) {
								var result = Ext.decode(operation.response.responseText);
								
								toolBar = grid.getDockedItems('toolbar[dock="bottom"]')[0];
								toolBar.getComponent('totalRecordsInDB').setValue(result.ofvPaymentReportVo.ofvPaymentReportResultDto.totalRecordsInDB);
								toolBar.getComponent('totalFeeSum').setValue(stl.amountFormat(result.ofvPaymentReportVo.ofvPaymentReportResultDto.totalFeeSum));
								toolBar.getComponent('prePaidFeeSum').setValue(stl.amountFormat(result.ofvPaymentReportVo.ofvPaymentReportResultDto.prePaidFeeSum));
								toolBar.getComponent('arrivePaidFeeSum').setValue(stl.amountFormat(result.ofvPaymentReportVo.ofvPaymentReportResultDto.arrivePaidFeeSum));
								toolBar.getComponent('adjustPaidFeeSum').setValue(stl.amountFormat(result.ofvPaymentReportVo.ofvPaymentReportResultDto.adjustPaidFeeSum));
								
							}
							});
					}else{
						Ext.Msg.alert(pay.ofvpaymentRpt.i18n('foss.stl.pay.common.alert'),pay.ofvpaymentRpt.i18n('foss.stl.pay.common.validateFailAlert'));
					}
		   }
	   }]
	}]			
});


//按合同编码Form--340403
Ext.define('Foss.ofvpaymentRpt.PaymentQueryInfoByByvehicleContractCodesNos',{
	extend:'Ext.form.Panel',
	frame:false,
	columnWidth:1,
	height :140,
	defaults:{
		margin :'10 10 10 10',
		labelWidth :85,
    	colspan : 1 
	},
	layout:{
		type :'column',
		columns :2
	},
	items : [{
		xtype:'textarea',
		fieldLabel:pay.ofvpaymentRpt.i18n('foss.stl.pay.ofvpaymentRpt.contractCode'),
		regex:/^((\s)*[A-Za-z0-9]{6,50}[;,])*((\s)*[A-Za-z0-9]{6,50}[;,]?)(\s)*$/i,
		emptyText: pay.ofvpaymentRpt.i18n('foss.stl.pay.ofvpaymentRpt.sourceBillNosEmptyText'),
		height : 80,
		allowBlank:false,
		name:'contractCodesNos',
		columnWidth:.4
	},{
		xtype:'container',
		margin:'10 0 0 10 ',
		items:[{
			xtype:'component',
			padding:'0 0 0 10',
			autoEl:{
				tag:'div',
				html:'<span style="color:red;">'+pay.ofvpaymentRpt.i18n('foss.stl.pay.common.vehicleAssembleContractCodesNots')+'</span>'
			}
		}],
		columnWidth:.3
	},{

		border: 1,
		xtype:'container',
		columnWidth:1,
		defaultType:'button',
		layout:'column',
		items:[
		    {
		    	text:pay.ofvpaymentRpt.i18n('foss.stl.pay.common.reset'),
			  		columnWidth:.1,
			  		handler:pay.ofvpaymentRpt.OfvpaymentRptQueryReset
		  	},{
				xtype:'container',
				border:false,
				html:'&nbsp;',
				columnWidth:.195
			},{
				  text:pay.ofvpaymentRpt.i18n('foss.stl.pay.common.query'),
				  columnWidth:.1,
				  cls:'yellow_button',  
				  handler:function(){
					var form = this.up('form').getForm();
					if (form.isValid()) {
						
						// 获取FORM所有值
						var params = {};
						
						var contractCodesNos = form.findField('contractCodesNos').getValue();
						var contractCodesNosArray_tmp = stl.splitToArray(contractCodesNos);
						var contractCodesNosArray=new Array();
						
						for(var i=0;i<contractCodesNosArray_tmp.length;i++){
							if(contractCodesNosArray_tmp[i].trim()!=''){
								contractCodesNosArray.push(contractCodesNosArray_tmp[i].trim());
							} 
						}
						 
						if(contractCodesNosArray.length==0){
							Ext.Msg.alert(pay.ofvpaymentRpt.i18n('foss.stl.pay.common.alert'),pay.ofvpaymentRpt.i18n('foss.stl.pay.ofvpaymentRpt.contractCodesInputAlert'));
						 	return false;
						}
						if(contractCodesNosArray.length>10){
							Ext.Msg.alert(pay.ofvpaymentRpt.i18n('foss.stl.pay.common.alert'),pay.ofvpaymentRpt.i18n('foss.stl.pay.common.queryNosLimit'));
						 	return false;
						}
						
						Ext.apply(params,{
							'ofvPaymentReportVo.dto.contractCodesNosArray' : contractCodesNosArray
						});
						
						var grid = Ext.getCmp('T_pay-outfindvehiclepaymentReport_content').getOfvPaymentReportGrid();
						var ofvPaymentStore = grid.store;
						ofvPaymentStore.removeAll();
						ofvPaymentStore.setSubmitParams(params);
						
						ofvPaymentStore.loadPage(1,{
							callback:function(records, operation, success) {
								var result = Ext.decode(operation.response.responseText);
								
								toolBar = grid.getDockedItems('toolbar[dock="bottom"]')[0];
								toolBar.getComponent('totalRecordsInDB').setValue(result.ofvPaymentReportVo.ofvPaymentReportResultDto.totalRecordsInDB);
								toolBar.getComponent('totalFeeSum').setValue(stl.amountFormat(result.ofvPaymentReportVo.ofvPaymentReportResultDto.totalFeeSum));
								toolBar.getComponent('prePaidFeeSum').setValue(stl.amountFormat(result.ofvPaymentReportVo.ofvPaymentReportResultDto.prePaidFeeSum));
								toolBar.getComponent('arrivePaidFeeSum').setValue(stl.amountFormat(result.ofvPaymentReportVo.ofvPaymentReportResultDto.arrivePaidFeeSum));
								toolBar.getComponent('adjustPaidFeeSum').setValue(stl.amountFormat(result.ofvPaymentReportVo.ofvPaymentReportResultDto.adjustPaidFeeSum));
								
							}
							});
					}else{
						Ext.Msg.alert(pay.ofvpaymentRpt.i18n('foss.stl.pay.common.alert'),pay.ofvpaymentRpt.i18n('foss.stl.pay.common.validateFailAlert'));
					}
		   }
	   }]
	}]			
});

//查询tab
Ext.define('Foss.ofvpaymentRpt.OfvPaymentReportQueryInfoTab', {
	extend:'Ext.tab.Panel',
	frame:false,
	bodyCls: 'autoHeight',
	cls: 'innerTabPanel',
	columnWidth:1,
	height :200,
	items : [ {
		title: pay.ofvpaymentRpt.i18n('foss.stl.pay.common.queryByDate'),
		tabConfig: {
			width: 150
		},
        layout:'fit',
        items:[
               Ext.create('Foss.ofvpaymentRpt.PaymentQueryInfoByDate')
               ]
	}, {
		title: pay.ofvpaymentRpt.i18n('foss.stl.pay.common.queryByWorkFlowNo'),
		tabConfig: {
			width: 150
		},
        layout:'fit',
        items:[
               Ext.create('Foss.ofvpaymentRpt.PaymentQueryInfoByWorkFlowNos')
               ]
	},{

		title: pay.ofvpaymentRpt.i18n('foss.stl.pay.common.queryByvehicleAssembleBillNo'),
		tabConfig: {
			width: 150
		},
        layout:'fit',
        items:[
               Ext.create('Foss.ofvpaymentRpt.PaymentQueryInfoByvehicleAssembleBillNos')
               ]
	
	},{

		title: pay.ofvpaymentRpt.i18n('foss.stl.pay.common.queryByvehicleContractCodesNo'),
		tabConfig: {
			width: 150
		},
        layout:'fit',
        items:[
               Ext.create('Foss.ofvpaymentRpt.PaymentQueryInfoByByvehicleContractCodesNos')
               ]
	
	}]
});


//外请车付款报表model
Ext.define('Foss.ofvpaymentRpt.OfvpaymentRptGridModel',{
	extend:'Ext.data.Model',
	fields:[{
		name:'origOrgCode'
	},{
		name:'origOrgName'
	},{	
		name:'destOrgCode'
	},{
		name:'destOrgName'
	},{
		name:'origCityCode'
	},{
		name:'origCityName'		
	},{
		name:'destCityCode'		
	},{
		name:'destCityName'		
	},{
		name:'payableType'		
	},{
		name:'vehicleAssembleBillNo'		
	},{
		name:'workFlowNo'		
	},{
		name:'vehicleNo'		
	},{
		name:'driverCode'				
	},{
		name:'driverName'						
	},{
		name:'stowageDate',
		type:'Date',
		convert:stl.longToDateConvert
	},{
		name:'feeTotal'
	},{	
		name:'prePaidFee'
	},{
		name:'arrivePaidFee'
	},{
		name:'adjustPaidFee'
	},{
		name:'prePaidPayer'
	},{	
		name:'prePaidAuditer'
	},{	
		name:'arrivePaidPayer'
	},{	
		name:'arrivePaidAuditer'
	},{
		name:'actualDepartTime',
		type:'Date',
		convert:stl.longToDateConvert
	},{
		name:'actualArriveTime',
		type:'Date',
		convert:stl.longToDateConvert	
	},{	
		name:'vehicleOwnerShip'
	},{	
		name:'beReturnReceipt'		
	},{
		name:'informationName'
	},{
		name:'carOwnerName'
	}]
});

//外请车付款报表store
Ext.define('Foss.ofvpaymentRpt.OfvpaymentRptGridStore',{
	extend:'Ext.data.Store',
	model:'Foss.ofvpaymentRpt.OfvpaymentRptGridModel',
	pageSize: pay.ofvpaymentRpt.ONEPAGESIZE,
	proxy:{
		type:'ajax',
		timeout: 420000,
		url:pay.realPath('queryOfvPaymentReportList.action'),
		actionMethods:'post',
		reader:{
			type:'json',
			root:'ofvPaymentReportVo.ofvPaymentReportResultDtoList',
			totalProperty:'totalCount'
		}
	},
	submitParams: {},
	setSubmitParams: function(submitParams){
		this.submitParams = submitParams;
	},
	constructor:function(config){
		var me = this;
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

//外请车付款列表
Ext.define('Foss.ofvpaymentRpt.OfvPaymentReportGrid',{
	extend:'Ext.grid.Panel',
    title: pay.ofvpaymentRpt.i18n('foss.stl.pay.ofvpaymentRpt.OfvPaymentReportGridTitle'),
    frame:true,
	height:420,
	sortableColumns: false,
	store: Ext.create('Foss.ofvpaymentRpt.OfvpaymentRptGridStore'),
	emptyText: pay.ofvpaymentRpt.i18n('foss.stl.pay.ofvpaymentRpt.OfvPaymentReportGridEmptyText'),
	selModel:Ext.create('Ext.selection.CheckboxModel'),
	viewConfig: {
		enableTextSelection: true
	},
	totalRecordsInDB:null,// 总条数
	getTotalRecordsInDB:function(){
		var me=this;
		if(Ext.isEmpty(me.totalRecordsInDB)){
			me.totalRecordsInDB=0;
		}
		return me.totalRecordsInDB;
	},
	totalFeeSum:null,// 总金额
	getTotalFeeSum:function(){
		var me=this;
		if(Ext.isEmpty(me.totalFeeSum)){
			me.totalFeeSum=0;
		}
		return me.totalFeeSum;
	},
	prePaidFeeSum:null,// 首款
	getPrePaidFeeSum:function(){
		var me=this;
		if(Ext.isEmpty(me.prePaidFeeSum)){
			me.prePaidFeeSum=0;
		}
		return me.prePaidFeeSum;
	},
	arrivePaidFeeSum:null,// 尾款
	getArrivePaidFeeSum:function(){
		var me=this;
		if(Ext.isEmpty(me.arrivePaidFeeSum)){
			me.arrivePaidFeeSum=0;
		}
		return me.arrivePaidFeeSum;
	},
	adjustPaidFeeSum:null,// 调整费用
	getAdjustPaidFeeSum:function(){
		var me=this;
		if(Ext.isEmpty(me.adjustPaidFeeSum)){
			me.adjustPaidFeeSum=0;
		}
		return me.adjustPaidFeeSum;
	},
	columns:[{
		header: pay.ofvpaymentRpt.i18n('foss.stl.pay.ofvpaymentRpt.origOrgName'),
		dataIndex: 'origOrgName'
	},{
		header: pay.ofvpaymentRpt.i18n('foss.stl.pay.ofvpaymentRpt.destOrgName'),
		dataIndex: 'destOrgName'
	},{
		header: pay.ofvpaymentRpt.i18n('foss.stl.pay.ofvpaymentRpt.payableType'),
		dataIndex: 'payableType',
		renderer:function(value){
			var displayField = FossDataDictionary.rendererSubmitToDisplay(value,settlementDict.BILL_PAYABLE__PAYABLE_TYPE);
    		return displayField;
		}
	},{
		header: pay.ofvpaymentRpt.i18n('foss.stl.pay.ofvpaymentRpt.workFlowNo'),
		dataIndex: 'workFlowNo'	
	},{
		header: pay.ofvpaymentRpt.i18n('foss.stl.pay.ofvpaymentRpt.vehicleAssembleBillNo'),
		dataIndex: 'vehicleAssembleBillNo'	
	},{
		header: pay.ofvpaymentRpt.i18n('foss.stl.pay.ofvpaymentRpt.vehicleNo'),
		dataIndex: 'vehicleNo'	
	},{
		header: pay.ofvpaymentRpt.i18n('foss.stl.pay.ofvpaymentRpt.driverName'),
		dataIndex: 'driverName'		
	},{
		header: pay.ofvpaymentRpt.i18n('foss.stl.pay.ofvpaymentRpt.stowageDate'),
		dataIndex: 'stowageDate',
		renderer:function(value){
			return stl.dateFormat(value, 'Y-m-d H:i:s');
		} 						
	},{
		header: pay.ofvpaymentRpt.i18n('foss.stl.pay.ofvpaymentRpt.feeTotal'),
		dataIndex: 'feeTotal'		
	},{
		header: pay.ofvpaymentRpt.i18n('foss.stl.pay.ofvpaymentRpt.prePaidFee'),
		dataIndex: 'prePaidFee'				
	},{
		header: pay.ofvpaymentRpt.i18n('foss.stl.pay.ofvpaymentRpt.arrivePaidFee'),
		dataIndex: 'arrivePaidFee'		
	},{
		header: pay.ofvpaymentRpt.i18n('foss.stl.pay.ofvpaymentRpt.adjustPaidFee'),
		dataIndex: 'adjustPaidFee'
	},{
		header: pay.ofvpaymentRpt.i18n('foss.stl.pay.ofvpaymentRpt.prePaidPayer'),
		dataIndex: 'prePaidPayer'		
	},{
		header: pay.ofvpaymentRpt.i18n('foss.stl.pay.ofvpaymentRpt.prePaidAuditer'),
		dataIndex: 'prePaidAuditer'		
	},{
		header: pay.ofvpaymentRpt.i18n('foss.stl.pay.ofvpaymentRpt.arrivePaidPayer'), 
		dataIndex: 'arrivePaidPayer'		
	},{
		header: pay.ofvpaymentRpt.i18n('foss.stl.pay.ofvpaymentRpt.arrivePaidAuditer'),
		dataIndex: 'arrivePaidAuditer'				
	},{
		header: pay.ofvpaymentRpt.i18n('foss.stl.pay.ofvpaymentRpt.actualDepartTime'),
		dataIndex: 'actualDepartTime',
		renderer:function(value){
			return stl.dateFormat(value, 'Y-m-d H:i:s');
		} 		
	},{
		header: pay.ofvpaymentRpt.i18n('foss.stl.pay.ofvpaymentRpt.actualArriveTime'),
		dataIndex: 'actualArriveTime',
		renderer:function(value){
			return stl.dateFormat(value, 'Y-m-d H:i:s');
		} 		
	},{
		header: pay.ofvpaymentRpt.i18n('foss.stl.pay.ofvpaymentRpt.vehicleOwnerShip'),
		dataIndex: 'vehicleOwnerShip'	
	},{
		header: pay.ofvpaymentRpt.i18n('foss.stl.pay.ofvpaymentRpt.beReturnReceipt'),
		dataIndex: 'beReturnReceipt'
//		renderer:function(value){
//			if(value!=null){
//				switch(value){
//					case 'Y':
//						return pay.ofvpaymentRpt.i18n('foss.stl.pay.ofvpaymentRpt.payableTypeY')
//						break
//					case 'N':
//						return pay.ofvpaymentRpt.i18n('foss.stl.pay.ofvpaymentRpt.payableTypeN')
//						break
//					default:
//						return value;
//			}
//		  }
//		}
	},{
		header:pay.ofvpaymentRpt.i18n('foss.stl.pay.ofvpaymentRpt.informationName'),
		dataIndex:'informationName'
	},{
		header:pay.ofvpaymentRpt.i18n('foss.stl.pay.ofvpaymentRpt.carOwnerName'),
		dataIndex:'carOwnerName'
	}
	
	],
	constructor:function(config){
		
		var me = this;
		me.store=Ext.create('Foss.ofvpaymentRpt.OfvpaymentRptGridStore');

		me.dockedItems =[{
			xtype: 'toolbar',
			dock: 'top',
			layout:'column',
			defaults :{
				margin :'0 0 5 3'
			},
			items: [{
				xtype:'button',
				text :pay.ofvpaymentRpt.i18n('foss.stl.pay.common.export'),
				columnWidth:.1,
				handler:pay.ofvpaymentRpt.OfvpaymentRptListExport,
				disabled: !pay.ofvpaymentRpt.isPermission('/stl-web/pay/exportOfvpaymentRptList.action'),
				hidden: !pay.ofvpaymentRpt.isPermission('/stl-web/pay/exportOfvpaymentRptList.action')
			},{
				xtype :'button',
				text :pay.ofvpaymentRpt.i18n('foss.stl.pay.common.print'),
				columnWidth :.1,
				handler:pay.ofvpaymentRpt.print
			}]
		},{
			xtype: 'toolbar',
			dock: 'bottom',
			layout:'column',	
			defaults:{
					margin:'0 0 5 3'
				},		
			items: [{
						xtype:'displayfield',
						allowBlank:true,
						itemId: 'totalRecordsInDB',
						name:'totalRecordsInDB',
						columnWidth:.1,
						labelWidth:80,
						fieldLabel:pay.ofvpaymentRpt.i18n('foss.stl.pay.ofvpaymentRpt.totalRecordsInDB'),
						value:me.getTotalRecordsInDB()
					},{
						xtype:'container',
						border:false,
						html:'&nbsp;',
						columnWidth:.1
					},{
						xtype:'displayfield',
						allowBlank:true,
						itemId: 'totalFeeSum',
						name:'totalFeeSum',
						columnWidth:.1,
						labelWidth:95,
						fieldLabel:pay.ofvpaymentRpt.i18n('foss.stl.pay.ofvpaymentRpt.totalFeeSum'),
						value:me.getTotalFeeSum()
					},{
						xtype:'container',
						border:false,
						html:'&nbsp;',
						columnWidth:.1
					},{
						xtype:'displayfield',
						allowBlank:true,
						itemId: 'prePaidFeeSum',
						name:'prePaidFeeSum',
						columnWidth:.1,
						labelWidth:95,
						fieldLabel:pay.ofvpaymentRpt.i18n('foss.stl.pay.ofvpaymentRpt.prePaidFeeSum'),
						value:me.getPrePaidFeeSum()
					},{
						xtype:'container',
						border:false,
						html:'&nbsp;',
						columnWidth:.1
					},{
						xtype:'displayfield',
						allowBlank:true,
						itemId: 'arrivePaidFeeSum',
						name:'arrivePaidFeeSum',
						columnWidth:.1,
						labelWidth:95,
						fieldLabel:pay.ofvpaymentRpt.i18n('foss.stl.pay.ofvpaymentRpt.arrivePaidFeeSum'),
						value:me.getArrivePaidFeeSum()
					},{
						xtype:'container',
						border:false,
						html:'&nbsp;',
						columnWidth:.1
					},{
						xtype:'displayfield',
						allowBlank:true,
						itemId: 'adjustPaidFeeSum',
						name:'adjustPaidFeeSum',
						columnWidth:.1,
						labelWidth:95,
						fieldLabel:pay.ofvpaymentRpt.i18n('foss.stl.pay.ofvpaymentRpt.adjustPaidFeeSum'),
						value:me.getAdjustPaidFeeSum()
					},{
						xtype:'container',
						border:false,
						html:'&nbsp;',
						columnWidth:.99
					},{
						xtype:'container',
						border:false,
						html:'&nbsp;',
						columnWidth:.3	
					},{
						xtype:'standardpaging',
						store:me.store,
						columnWidth:.6,
						plugins: Ext.create('Deppon.ux.PageSizePlugin',{
							maximumSize:200
						})
			}]
		}];	
		me.callParent();
	}
});

// 初始化界面
Ext.onReady(function() {
	Ext.QuickTips.init();
	//查询外请车付款报表tab
	var ofvPaymentReportQueryInfoTab = Ext.create('Foss.ofvpaymentRpt.OfvPaymentReportQueryInfoTab');

	//创建按日期查询到付清查单列表Store
	var ofvPaymentReportQueryInfoStore = Ext.create('Foss.ofvpaymentRpt.OfvpaymentRptGridStore');	

	//按日期查询到付清查列表Grid
	var ofvPaymentReportGrid = Ext.create('Foss.ofvpaymentRpt.OfvPaymentReportGrid');
	
	Ext.create('Ext.panel.Panel',{
		id: 'T_pay-outfindvehiclepaymentReport_content',
		cls: "panelContentNToolbar",
		bodyCls: 'panelContentNToolbar-body',
		layout: 'auto',
		getOfvPaymentReportGrid:function(){
			return ofvPaymentReportGrid;
		},
		items: [ofvPaymentReportQueryInfoTab,ofvPaymentReportGrid],
		renderTo: 'T_pay-outfindvehiclepaymentReport-body'
	});
});