/**
 * 日期限制
 */
pay.advancePay.STLBILLADVANCEAPPLYS_MAX = 90;  // 时间间隔最大不超过90天
pay.advancePay.STLBILLADVANCEAPPLYS_ONEMONTH = 30;  // 时间相差一月
pay.advancePay.STLADVANCEAPPLYS_ONEPAGESIZE = 50; //每页显示的条数

//查询预付单Model
//grid的model
Ext.define('Foss.stlbilladvanceapplys.queryBillAdvanceApplysModel',{
	extend:'Ext.data.Model',
	idgen: 'uuid',//EXT在前台为每个模型额外以UUID方式生成主键
	idProperty : 'extid',//以上生成的主键用在名叫“extid”的列 
	fields:[
	 //id
	{
		//额外的用于生成的EXT使用的列 
		name: 'extid'
	},{
		name:'id'
	},{//申请人编号
		name:'createUserCode'
	},
	{
		//预付单号
		name:'advancesNo'
	},{
		//申请人
		name:'auditUserName'
	},{
		//费用承担部门编号
		name:'applyOrgCode'
	},{
		//付款部门编号
		name:'paymentOrgCode'
	},{
		//客户编码
		name:'customerCode'
	},{
		//收款客户名称	
		name:'customerName'
	},{
		//金额
		name:'amount',
		type:'long'
	},{
		//开户支行
		name:'bankBranchName'
	},{
		//发票抬头
		name:'invoiceTitle'
	},{
		//版本号
		name:'versionNo'
	},{
		//单据类型
		name:'billType'
	},{
		//银行卡信息
		name:'accountBankNo'
	},{
		//账号
		name:'accountBankNo'
	},{//付款类型
		name:'paymentType'
	},{
		//最迟汇款日期
		name:'lastpaymentTime',
		type:'date',
		convert:stl.longToDateConvert
	},{
		//审批状态
		name:'auditStatus'
	},{
		//审批通过时间
		name:'auditTime',
		type:'date',
		convert:stl.longToDateConvert		
	},{
		//制单人、申请人
		name:'createUserName'
	},{
	 //付款部门所属子公司名称
	 name:'paymentCompanyName'
	},{//付款部门所属子公司编码
		name:'paymentCompanyCode'
	},{//申请部门子公司编码
	  name:'applyCompanyCode'
	},{
		name:'applyCompanyName'
	},{
	  //付款部门名称
	  name:'paymentOrgName'
	},{
		//付款部门编码
		name:'paymentOrgCode'
	},{
		//开户支行名称
		name:'bankBranchName'
	},{
		//开户支行code
		name:'bankBranchCode'
	},{
		//收款人手机号码
		name:'mobilePhone'
	},{
		//银行账号
		name:'accountNo'
	},{
		//开户人姓名
		name:'payeeName'
	},{
		name:'applyOrgCode'
	},{
		//申请部门名称
		name:'applyOrgName'
	},{
		//开户行编码
		name:'bankHqCode'
	},{
		//开户行名称
		name:'bankHqName'
	},{
		//业务日期
		name:'businessDate',
		type:'date',
		convert:stl.longToDateConvert
	},{
		//记账日期
		name:'accountDate',
		type:'date',
		convert:stl.longToDateConvert
	}
	,{
		//开户省份code
		name:'provinceCode'
	},{
		//省份名称
		name:'provinceName'
	},{
		//城市code
		name:'cityCode'
	},{
		//城市名称
		name:'cityName'
	//工作流号
	},{name:'workflowNo'
	},{//是否有效
		name:'active'
	},{//是否红单
		name:'isRedBack'
	},{//付款类型
		name:'paymentType'
	},{//单据类型
		name:'billType'
	},
//	{
//		//账户类型
//		name:'accountType'
//	},
	{
		//审核人编码
		name:'auditUserCode'
	},{
		//审核人名称
		name:'auditUserName'
	},{
		name:'verifyAmount'
	},{
		name:'unverifyAmount'
	},{
		name:'notes'
	}]
});


//客户类型：Model
Ext.define('Foss.pay.advancePay.CustomerTypeModel',{
	extend:'Ext.data.Model',
	fields:[{
		name:'customerTypeCode'
	},{
		name:'customerTypeName'
	}]
	
});

//客户类型：store
Ext.define('Foss.pay.advancePay.CustomerTypeStore',{
	extend:'Ext.data.Store',
	model:'Foss.pay.advancePay.CustomerTypeModel',
	data:{
		'items':[
			{customerTypeCode:'01',customerTypeName:pay.advancePay.i18n('foss.stl.pay.queryBillAdvanceApplys.customer')},
			{customerTypeCode:'02',customerTypeName:pay.advancePay.i18n('foss.stl.pay.queryBillAdvanceApplys.airAgeny')}
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

//预付单Store
Ext.define('Foss.stlbilladvanceapplys.queryBillAdvanceApplysStore',{
	extend:'Ext.data.Store',
	model:'Foss.stlbilladvanceapplys.queryBillAdvanceApplysModel',
	pageSize:pay.advancePay.STLADVANCEAPPLYS_ONEPAGESIZE,
	sorters: [{
        property: 'businessDate',
        direction: 'ASC'
    }],
	proxy:{
		type:'ajax',
		url:pay.realPath('queryBillAdvanceApplys.action'),
		actionMethods:'post',
		reader:{
			type:'json',
			root:'billAdvanceResultVo.billAdvanResultDto.queryBillAdvancedPayList',
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

//查询预付款
pay.queryAdvance=function(){
	var form=this.up('form').getForm();
	var grid = Ext.getCmp('T_pay-manageBillAdvanceApplys_content').getAdvanePayGrid();
	if(form.isValid()){
		var params={};
		params= form.getValues();
		
		//参数设置异常时直接返回
		if(!params){
			return false;
		}
		
		//校验日期是否正确
		var businessStartDate = form.findField('billAdvanceVo.billAdvanDto.businessStartDate').getValue();
		var businessEndDate = form.findField('billAdvanceVo.billAdvanDto.businessEndDate').getValue();
		
		//比较起始日期和结束日期
		var compareTwoDate = stl.compareTwoDate(businessStartDate,businessEndDate);
		if(compareTwoDate>pay.advancePay.STLBILLADVANCEAPPLYS_MAX){
			Ext.Msg.alert(pay.advancePay.i18n('foss.stl.pay.common.alert'),pay.advancePay.i18n('foss.stl.pay.queryBillAdvanceApplys.queryDateMaxLimit')+pay.advancePay.STLBILLADVANCEAPPLYS_MAX+pay.advancePay.i18n('foss.stl.pay.queryBillAdvanceApplys.date'));
			return false;
		}else if(compareTwoDate<1){
			Ext.Msg.alert(pay.advancePay.i18n('foss.stl.pay.common.alert'),pay.advancePay.i18n('foss.stl.pay.common.dateEndBeforeStatrtWarining'));
			return false;
		}
		
		//设置查询参数
		grid.store.setSubmitParams(params);
		//设置统计值
		grid.store.load({
	      callback: function(records, operation, success) {
    	  var rawData = grid.store.proxy.reader.rawData;
			//当success:false ,isException:false  --业务异常
			if(!success && ! rawData.isException){
				Ext.Msg.alert(pay.advancePay.i18n('foss.stl.pay.common.alert'),rawData.message);
				return false;
			}
			grid.show();
	    	if(success){
	    		var result =   Ext.decode(operation.response.responseText);
				toolBar = grid.getDockedItems('toolbar[dock="bottom"]')[0];
				toolBar.getComponent('paymentTotalRows').setValue(result.billAdvanceResultVo.billAdvanResultDto.countNum);
	    	} 
	      }
	    }); 
	}else {
		Ext.Msg.alert(pay.advancePay.i18n('foss.stl.pay.common.alert'),pay.advancePay.i18n('foss.stl.pay.common.validateFailAlert'));
	}
  
}
	

//重置预付款查询
pay.queryAdvanceReset=function(){
	this.up('form').getForm().reset();
	var form=this.up('form').getForm().reset();
	//预付单查询时，默认选中的预付单的付款部门	
	var applyCodeCombSelector=form.findField('billAdvanceVo.billAdvanDto.paymentOrgCode');
	if(!Ext.isEmpty(stl.currentDept.code)){
		applyCodeCombSelector.setCombValue(stl.currentDept.name,stl.currentDept.code);
	}
}



Ext.define('Foss.stlbilladvanceapplys.StlBillAdvanceApplysQueryInfoForm', {
	extend:'Ext.form.Panel',
	title:pay.advancePay.i18n('foss.stl.pay.queryBillAdvanceApplys.queryAirAdvance'),
	cls:'autoHeight',
	bodyCls: 'autoHeight',
	frame:true,
	defaults:{
		fieldLabel:'75',
		margin:'5 5 5 5 ',
		labelWidth:100
	},
	layout:'column',
	items:[{
    	xtype:'datefield',
    	name:'billAdvanceVo.billAdvanDto.businessStartDate',
    	fieldLabel:pay.advancePay.i18n('foss.stl.pay.queryBillAdvanceApplys.applyBeginDate'),
    	value: stl.getTargetDate(new Date(),-pay.advancePay.STLBILLADVANCEAPPLYS_ONEMONTH),
    	format:'Y-m-d',
    	allowBlank:false,
    	columnWidth:.3
    },{
    	xtype:'datefield',
   	 	name:'billAdvanceVo.billAdvanDto.businessEndDate',
    	fieldLabel:pay.advancePay.i18n('foss.stl.pay.queryBillAdvanceApplys.applyEndDate'),
    	format:'Y-m-d',
    	value:new Date(),
    	columnWidth:.3,
    	allowBlank:false,
    	maxValue:new Date()
    },{
		xtype: 'combobox',
		name:'billAdvanceVo.billAdvanDto.auditStatus',
	    fieldLabel:pay.advancePay.i18n('foss.stl.pay.queryBillAdvanceApplys.auditStatus'),
	    store:FossDataDictionary.getDataDictionaryStore('BILL_ADVANCED_PAYMENT__AUDIT_STATUS',null,{
			 'valueCode': '',
      		 'valueName': pay.advancePay.i18n('foss.stl.pay.queryBillAdvanceApplys.all')
		}),
		value:'',
		queryModel:'local',
		displayField:'valueName',
		valueField:'valueCode',
	    columnWidth:.3
	},{
    	xtype:'dynamicorgcombselector',
    	fieldLabel:pay.advancePay.i18n('foss.stl.pay.queryBillAdvanceApplys.applyCostDept'),
    	name:'billAdvanceVo.billAdvanDto.paymentOrgCode',
    	columnWidth:.3
    }
    ,{	
    	xtype:'commonairlinesselector',
    	fieldLabel:pay.advancePay.i18n('foss.stl.pay.queryBillAdvanceApplys.recviceCustomerInfo'),
    	name :'billAdvanceVo.billAdvanDto.customerCode',
    	id:'pay.advancePay.commonairagencycompanyselector',
    	columnWidth:.3,
//		labelWidth:90,
//		listWidth:260,//设置下拉框宽度
		isPaging:true //分页
    },
    {
		border: 1,
		xtype:'container',
		columnWidth:1,
		defaultType:'button',
		layout:'column',
		items:[{
			text:pay.advancePay.i18n('foss.stl.pay.common.reset'),
			columnWidth:.08,
			handler:pay.queryAdvanceReset
		},{
			xtype:'container',
			border:false,
			html:'&nbsp;',
			columnWidth:.74
		},{
			text:pay.advancePay.i18n('foss.stl.pay.common.query'),
			cls:'yellow_button',
			columnWidth:.08,
			handler:pay.queryAdvance
		}]
	}]
});




/**
 * 导出excel
 */
pay.advancePay.advanceForBillExport = function(){
	var	columns,
		searchParams,
		arrayColumns,
		arrayColumnNames,
		grid;
	//获取表格
	grid = Ext.getCmp('T_pay-manageBillAdvanceApplys_content').getAdvanePayGrid();
	var form= grid.ownerCt.down('form').getForm();
	var businessStartDate=form.findField("billAdvanceVo.billAdvanDto.businessStartDate").getValue();
	var businessEndDate=form.findField("billAdvanceVo.billAdvanDto.businessEndDate").getValue();
	var auditStatus=form.findField("billAdvanceVo.billAdvanDto.auditStatus").getValue();
	var paymentOrgCode=form.findField("billAdvanceVo.billAdvanDto.paymentOrgCode").getValue();
	var customerCode=form.findField("billAdvanceVo.billAdvanDto.customerCode").getValue();
	
	//如果没有查询到数据，则不能进行导出操作
	if(grid.store.data.length==0){
		Ext.Msg.alert(pay.advancePay.i18n('foss.stl.pay.common.alert'),pay.advancePay.i18n('foss.stl.pay.common.noDataToExport'));
		return false;
	}
	//进行导出提示
	Ext.MessageBox.show({
        title: pay.advancePay.i18n('foss.stl.pay.common.alert'),
        msg: pay.advancePay.i18n('foss.stl.pay.queryBillAdvanceApplys.isExportAdvancePrompt'),
        buttons: Ext.MessageBox.YESNOCANCEL,
        buttonText:{ 
            yes: pay.advancePay.i18n('foss.stl.pay.common.defaultColumnExport'),
            no:  pay.advancePay.i18n('foss.stl.pay.common.customColumnExport'),
            cancel: pay.advancePay.i18n('foss.stl.pay.common.cancel')
        },
        fn: function(e){
        	if(e=='yes'){
        		//默认显示列
				arrayColumns = ['advancesNo','workflowNo','amount','auditStatus','auditTime',
								'createUserName','applyOrgName','businessDate',
								'customerName','mobilePhone','payeeName',
								'provinceName','cityName','bankHqName',
								'bankBranchName','accountNo','lastpaymentTime'];
				//默认显示列名称
				arrayColumnNames = [pay.advancePay.i18n('foss.stl.pay.queryBillAdvanceApplys.advancesNo'),
									pay.advancePay.i18n('foss.stl.pay.queryBillAdvanceApplys.workflowNo'),
									pay.advancePay.i18n('foss.stl.pay.queryBillAdvanceApplys.amount'),
									pay.advancePay.i18n('foss.stl.pay.queryBillAdvanceApplys.auditStatus'),
									pay.advancePay.i18n('foss.stl.pay.queryBillAdvanceApplys.auditTime'),				                 
				                    pay.advancePay.i18n('foss.stl.pay.queryBillAdvanceApplys.createUserName'),
				                    pay.advancePay.i18n('foss.stl.pay.queryBillAdvanceApplys.applyOrgName'),
				                    pay.advancePay.i18n('foss.stl.pay.queryBillAdvanceApplys.businessDate'),
				                    pay.advancePay.i18n('foss.stl.pay.queryBillAdvanceApplys.customerName'),
				                    pay.advancePay.i18n('foss.stl.pay.queryBillAdvanceApplys.mobilePhone'),
				                    pay.advancePay.i18n('foss.stl.pay.queryBillAdvanceApplys.payeeName'),
				                    pay.advancePay.i18n('foss.stl.pay.queryBillAdvanceApplys.provinceName'),
				                    pay.advancePay.i18n('foss.stl.pay.queryBillAdvanceApplys.cityName'),
				                    pay.advancePay.i18n('foss.stl.pay.queryBillAdvanceApplys.bankHqName'),
				                    pay.advancePay.i18n('foss.stl.pay.queryBillAdvanceApplys.bankBranchName'),
				                    pay.advancePay.i18n('foss.stl.pay.queryBillAdvanceApplys.accountNo'),
				                    pay.advancePay.i18n('foss.stl.pay.queryBillAdvanceApplys.lastpaymentTime')
				                  ];									
        	}else if(e=='no'){
        		//转化列头和列明
				columns = grid.columns;
				arrayColumns = [];
				arrayColumnNames = [];
				//将前台对应列头传入到后台去
				for(var i=0;i<columns.length;i++){
					if(columns[i].isHidden()==false){
						var hederName = columns[i].text;
						var dataIndex = columns[i].dataIndex;
						if(columns[i].text!=pay.advancePay.i18n('foss.stl.pay.common.actionColumn')){
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
			
			searchParams = {
					'billAdvanceVo.billAdvanDto.businessStartDate':businessStartDate,	
					'billAdvanceVo.billAdvanDto.businessEndDate':businessEndDate,	
					'billAdvanceVo.billAdvanDto.auditStatus':auditStatus,	
					'billAdvanceVo.billAdvanDto.paymentOrgCode':paymentOrgCode,	
					'billAdvanceVo.billAdvanDto.customerCode':customerCode,	
					'billAdvanceVo.billAdvanDto.arrayColumns':arrayColumns,
			    	'billAdvanceVo.billAdvanDto.arrayColumnNames':arrayColumnNames
			};
			
			//拼接vo，注入到后台
			Ext.Ajax.request({
			    url:pay.realPath('exportBillAdvancePay.action'),
			    form: Ext.fly('downloadAttachFileForm'),
			    method : 'post',
			    params :searchParams,
			    isUpload: true,
			    success : function(response){
			    	var result = Ext.decode(response.responseText);
			    	//如果异常信息有值，则弹出提示
			    	if(!Ext.isEmpty(result.errorMessage)){
			    		Ext.Msg.alert(pay.advancePay.i18n('foss.stl.pay.common.alert'),result.errorMessage);
			    		return false;
			    	}
					Ext.ux.Toast.msg(pay.advancePay.i18n('foss.stl.pay.common.alert'),pay.advancePay.i18n('foss.stl.pay.common.exportSuccess'), 'success', 1000);
			    },
			    failure : function(response){
					Ext.ux.Toast.msg(pay.advancePay.i18n('foss.stl.pay.common.alert'),pay.advancePay.i18n('foss.stl.pay.common.exportFailed'), 'error', 1000);
			    },
				exception:function(response){
					var result = Ext.decode(response.responseText);
					Ext.Msg.alert(pay.advancePay.i18n('foss.stl.pay.common.alert'),result.message);
				}
			});
		}
    });
}

//空运预付款Grid
Ext.define('Foss.stlbilladvanceapplys.StlBillAdvanceApplysQueryInfoGrid',{
	extend:'Ext.grid.Panel',
    title: pay.advancePay.i18n('foss.stl.pay.queryBillAdvanceApplys.queryAirAdvance'),
    frame:true,
	height:500,
	bodyCls: 'autoHeight',
	cls: 'autoHeight',
	emptyText:pay.advancePay.i18n('foss.stl.pay.common.noResult'),
	hidden:true,
	hidMeParams:null,
	setHidMeParams:function(params){
		this.hidMeParams=params;
	},
	getHidMeParams:function(){
		return this.hidMeParams;
	},
	paymentTotalRows:null,//总条数
	getPaymentTotalRows:function(){
		var me=this;
		if(Ext.isEmpty(me.paymentTotalRows)){
			me.paymentTotalRows=0;
		}
		return me.paymentTotalRows;
	},
	defaults:{
  		align:'center'
  	},
  	viewConfig:{
  		enableTextSelection : true//设置行可以选择，进而可以复制
  	},
	columns:[{
		header:pay.advancePay.i18n('foss.stl.pay.queryBillAdvanceApplys.advancesNo'),
		dataIndex:'advancesNo'
	},{
		header:pay.advancePay.i18n('foss.stl.pay.queryBillAdvanceApplys.workflowNo'),
		dataIndex:'workflowNo'
	},{
		header:pay.advancePay.i18n('foss.stl.pay.queryBillAdvanceApplys.amount'),
		dataIndex:'amount',
		align: 'right',  
		renderer:stl.amountFormat
	},{
		header:pay.advancePay.i18n('foss.stl.pay.queryBillAdvanceApplys.verifyAmount'),
		dataIndex:'verifyAmount',
		align: 'right',  
		renderer:stl.amountFormat
	},{
		header:pay.advancePay.i18n('foss.stl.pay.queryBillAdvanceApplys.unverifyAmount'),
		dataIndex:'unverifyAmount',
		align: 'right',  
		renderer:stl.amountFormat
	},{
		header:pay.advancePay.i18n('foss.stl.pay.queryBillAdvanceApplys.auditStatus'),
		dataIndex:'auditStatus',
		renderer:function(value){
    		var displayField = FossDataDictionary. rendererSubmitToDisplay (value,'BILL_ADVANCED_PAYMENT__AUDIT_STATUS');
    		return displayField;
    	}
	},{//审批通过时间
		header:pay.advancePay.i18n('foss.stl.pay.queryBillAdvanceApplys.auditTime'),
		dataIndex:'auditTime',
		renderer:function(value,cellmeta, record){
			var status =  record.data['auditStatus'];
			//当应付单的审批状态为通过时显示通过时间
			if(status == 'AA'){
				return stl.dateFormat(value,'Y-m-d H:i:s');
			}
			return null;
		}
	},{
		header:pay.advancePay.i18n('foss.stl.pay.queryBillAdvanceApplys.createUserCode'),
		dataIndex:'createUserCode',
		hidden:true
	},{
		header:pay.advancePay.i18n('foss.stl.pay.queryBillAdvanceApplys.createUserName'),
		dataIndex:'createUserName'
	},{
		header:pay.advancePay.i18n('foss.stl.pay.queryBillAdvanceApplys.applyOrgCode'),
		dataIndex:'applyOrgCode',
		hidden:true
	},{
		header:pay.advancePay.i18n('foss.stl.pay.queryBillAdvanceApplys.applyOrgName'),
		dataIndex:'applyOrgName'
	},
	{
		header:pay.advancePay.i18n('foss.stl.pay.queryBillAdvanceApplys.paymentOrgCode'),
		dataIndex:'paymentOrgCode',
		hidden:true
	},{
		header:pay.advancePay.i18n('foss.stl.pay.queryBillAdvanceApplys.paymentOrgName'),
		dataIndex:'paymentOrgName'
	},
	{
		header:pay.advancePay.i18n('foss.stl.pay.queryBillAdvanceApplys.businessDate'),
		dataIndex:'businessDate',
		renderer:function(value){
			return stl.dateFormat(value,'Y-m-d H:i:s');
		}
	},{
		header:pay.advancePay.i18n('foss.stl.pay.queryBillAdvanceApplys.active'),
		dataIndex:'active',
		hidden:true,
		renderer:function(value){
    		var displayField = FossDataDictionary. rendererSubmitToDisplay (value,'FOSS_ACTIVE');
    		return displayField;
    	}
	},{
		header:pay.advancePay.i18n('foss.stl.pay.queryBillAdvanceApplys.isRedBack'),
		dataIndex:'isRedBack',
		hidden:true,
		renderer:function(value){
    		var displayField = FossDataDictionary. rendererSubmitToDisplay (value,'SETTLEMENT__IS_RED_BACK');
    		return displayField;
    	}
	},{
		header:pay.advancePay.i18n('foss.stl.pay.queryBillAdvanceApplys.versionNo'),
		dataIndex:'versionNo',
		hidden:true
	},{
		header:pay.advancePay.i18n('foss.stl.pay.queryBillAdvanceApplys.customerName'),
		dataIndex:'customerName'
	},{
		header:pay.advancePay.i18n('foss.stl.pay.queryBillAdvanceApplys.mobilePhone'),
		dataIndex:'mobilePhone'
	},{
		header:pay.advancePay.i18n('foss.stl.pay.queryBillAdvanceApplys.payeeName'),
		dataIndex:'payeeName'
	},{
		header:pay.advancePay.i18n('foss.stl.pay.queryBillAdvanceApplys.provinceName'),
		dataIndex:'provinceName'
	},{
		header:pay.advancePay.i18n('foss.stl.pay.queryBillAdvanceApplys.cityName'),
		dataIndex:'cityName'
	},{
		header:pay.advancePay.i18n('foss.stl.pay.queryBillAdvanceApplys.bankHqName'),
		dataIndex:'bankHqName'
	},{
		header:pay.advancePay.i18n('foss.stl.pay.queryBillAdvanceApplys.bankBranchName'),
		dataIndex:'bankBranchName'
	},{
		header:pay.advancePay.i18n('foss.stl.pay.queryBillAdvanceApplys.accountNo'),
		dataIndex:'accountNo'
	},{
		header:pay.advancePay.i18n('foss.stl.pay.queryBillAdvanceApplys.isLateRemitDate'),
		dataIndex:'lastpaymentTime',
		renderer:function(value){
			return stl.dateFormat(value,'Y-m-d H:i:s');
		}
	},{
		header:pay.advancePay.i18n('foss.stl.pay.queryBillAdvanceApplys.accountDate'),
		dataIndex:'accountDate',
		hidden:true,
		renderer:function(value){
			return stl.dateFormat(value,'Y-m-d H:i:s');
		}
	}
	,{
	  header:pay.advancePay.i18n('foss.stl.pay.queryBillAdvanceApplys.paymentType'),
	  dataIndex:'paymentType',
	  hidden:true,
	  renderer:function(value){
  		var displayField = FossDataDictionary. rendererSubmitToDisplay (value,'SETTLEMENT__PAYMENT_TYPE');
  		return displayField;
  	}
	},{
		header:pay.advancePay.i18n('foss.stl.pay.queryBillAdvanceApplys.bankHqCode'),
		dataIndex:'bankHqCode',
		hidden:true
	},
//	{
//		header:pay.advancePay.i18n('foss.stl.pay.queryBillAdvanceApplys.accountType'),
//		dataIndex:'accountType',
//		hidden:true,
//		renderer:function(value){
//    		var displayField = FossDataDictionary. rendererSubmitToDisplay (value,'FIN_ACCOUNT_TYPE');
//    		return displayField;
//    	}
//	},
	{
	  header:pay.advancePay.i18n('foss.stl.pay.queryBillAdvanceApplys.notes'),
	  dataIndex:'notes',
	  xtype: 'ellipsiscolumn'
	}
	,{
		header:pay.advancePay.i18n('foss.stl.pay.queryBillAdvanceApplys.applyCompanyCode'),
		dataIndex:'applyCompanyCode',
		hidden:true
	},{
		header:pay.advancePay.i18n('foss.stl.pay.queryBillAdvanceApplys.applyCompanyName'),
		dataIndex:'applyCompanyName',
		hidden:true
	},{
		header:pay.advancePay.i18n('foss.stl.pay.queryBillAdvanceApplys.paymentCompanyName'),
		dataIndex:'paymentCompanyName',
		hidden:true
	},{
		header:pay.advancePay.i18n('foss.stl.pay.queryBillAdvanceApplys.paymentCompanyCode'),
		dataIndex:'paymentCompanyCode',
		hidden:true
	}
//	,{
//		header:pay.advancePay.i18n('foss.stl.pay.queryBillAdvanceApplys.invoiceTitleCode'),
//		dataIndex:'invoiceTitleCode',
//		hidden:true
//	},{
//		header:pay.advancePay.i18n('foss.stl.pay.queryBillAdvanceApplys.invoiceTitleName'),
//		dataIndex:'invoiceTitleName',
//		hidden:true
//	}
	],
	constructor:function(config){
		var me = this;
		me.store=Ext.create('Foss.stlbilladvanceapplys.queryBillAdvanceApplysStore');
		me.dockedItems =  [{
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
				text:pay.advancePay.i18n('foss.stl.pay.common.export'),
				columnWidth:.1,
				handler:pay.advancePay.advanceForBillExport
			}]
		},{
			xtype: 'toolbar',
		    dock: 'bottom',
		    layout:'column',
		    defaults:{
				margin:'0 0 5 3'
			},	
		    items:[{
				height:5,
				columnWidth:1
			},{
			    	xtype:'displayfield',
					allowBlank:true,
					itemId: 'paymentTotalRows',
					name:'paymentTotalRows',
					columnWidth:.3,
					labelWidth:100,
					fieldLabel:pay.advancePay.i18n('foss.stl.pay.queryBillAdvanceApplys.totalCountRecord'),
					value:me.getPaymentTotalRows()
		    },{
				xtype:'standardpaging',
				store:me.store,
				columnWidth:.7,
				plugins: Ext.create('Deppon.ux.PageSizePlugin', {
					//设置分页记录最大值，防止输入过大的数值
					maximumSize: 200
				})
			 }]
		}];
		me.callParent(config);
	}
});

// 初始化界面
Ext.onReady(function() {
	Ext.QuickTips.init();
	
	if (Ext.getCmp('T_pay-manageBillAdvanceApplys-body')) {
		return;
	} 
	
	
	var stlAdvancePayForm = Ext.create('Foss.stlbilladvanceapplys.StlBillAdvanceApplysQueryInfoForm');
	
	var stlAdvancePayGrid = Ext.create('Foss.stlbilladvanceapplys.StlBillAdvanceApplysQueryInfoGrid',{hidden:true});
	
	
	Ext.create('Ext.panel.Panel',{
		id: 'T_pay-manageBillAdvanceApplys_content',
		cls: "panelContentNToolbar",
		bodyCls: 'panelContentNToolbar-body',
		layout: 'auto',
		getAdvanePayGrid:function(){
			return stlAdvancePayGrid;
		},
		listeners:{
			'boxready':function(th){
			//获取当前用户权限
			var roles = stl.currentUserDepts;
			//费用承担部门				
			var applyCodeCombSelector=stlAdvancePayForm.form.findField('billAdvanceVo.billAdvanDto.paymentOrgCode');
			
			/**
			 * 获取用户角色，判断部门是否可编辑 
			 */
			for(var i=0;i<roles.length;i++){
				//此处需要判断是否为收银员
				if(roles[i]=='1'){
					if(!Ext.isEmpty(stl.currentDept.code)){
						applyCodeCombSelector.setCombValue(stl.currentDept.name,stl.currentDept.code);
					}
				}	
			} 
		 }
		},
		items: [stlAdvancePayForm,stlAdvancePayGrid],
		renderTo: 'T_pay-manageBillAdvanceApplys-body'
	});
});