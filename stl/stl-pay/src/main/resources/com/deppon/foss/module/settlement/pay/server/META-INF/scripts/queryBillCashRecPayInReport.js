/**
 * 缴款报表查询
 */
pay.cashRec.cashRec_MAX = 90;  // 时间间隔最大不超过60天 修改为90天
pay.cashRec.cashRec_THREEDAYS = 7;  // 时间相差7天

pay.cashRec.dept = FossUserContext. getCurrentUserDept();//当前登录部门
pay.cashRec.user = FossUserContext.getCurrentUser();//当前登录用户
pay.cashRec.roles = FossUserContext. getCurrentUserRoleCodes();
pay.cashRec.STLREVERSEWRITEOFF_ONEPAGESIZE=50;

//缴款报表查询Form重置
pay.cashRec.reset=function(){
	this.up('form').getForm().reset();
}



//格式化金额
pay.cashRec.convertAmount=function setMoneyFormat(val){
	return "" + Ext.util.Format.currency(val,' ',2,false) + "";
} 



//查询缴款报表查询（缴款）报表
pay.cashRec.queryByCashRecD = function(){
	
	var form=this.up('form').getForm();
	var bt=this;
	
	var grid = Ext.getCmp('T_pay-manageCashRecPayInReport_content').getCashRecGrid();
	if(form.isValid()){
		
		bt.disable(false);
		
		setTimeout(function() {
			bt.enable(true);
		}, 10000);
		
		//校验日期是否正确
		var businessStartDate=form.findField("billCashRecDVo.billCashRecPayInDDto.businessStartDate").getValue();
		
		var businessEndDate=form.findField("billCashRecDVo.billCashRecPayInDDto.businessEndDate").getValue();
		
		var sourceBillType=form.findField('billCashRecDVo.billCashRecPayInDDto.sourceBillType').getValue();
		
		var paymentType=form.findField('billCashRecDVo.billCashRecPayInDDto.paymentType').getValue();
		
		var collectionOrgCode=form.findField('billCashRecDVo.billCashRecPayInDDto.collectionOrgCode').getValue();
		
		if(!collectionOrgCode) {
			Ext.Msg.alert(pay.cashRec.i18n('foss.stl.pay.common.alert'),
					pay.cashRec.i18n('foss.stl.pay.queryBillCashRecPayInReport.deptNoNull'));
			return false;
		}
		
		//比较起始日期和结束日期
		var compareTwoDate = stl.compareTwoDate(businessStartDate,businessEndDate);
		if(compareTwoDate>pay.cashRec.cashRec_MAX){
			Ext.Msg.alert(pay.cashRec.i18n('foss.stl.pay.common.alert'),pay.cashRec.i18n('foss.stl.pay.queryBillCashRecPayInReport.queryDateMaxLimit')+pay.cashRec.cashRec_MAX+pay.cashRec.i18n('foss.stl.pay.queryBillCashRecPayInReport.date'));
			return false;
		}else if(compareTwoDate<1){
			Ext.Msg.alert(pay.cashRec.i18n('foss.stl.pay.common.alert'),pay.cashRec.i18n('foss.stl.pay.queryBillCashRecPayInReport.dateEndBeforeStatrtWarining'));
			return false;
		}

		pay.cashRec.businessStartDate =businessStartDate;
		
		var params={};
		params= form.getValues();
		//参数设置异常时直接返回
		if(!params){
			return false;
		}
		
		if(stl.currentDept.name == collectionOrgCode) {
			Ext.apply(params,{'billCashRecDVo.billCashRecPayInDDto.collectionOrgCode':stl.currentDept.code});
		}
		
		//设置查询参数
		grid.store.setSubmitParams(params);
		//设置统计值
		grid.store.load({
	      callback: function(records, operation, success) {
  	  var rawData = grid.store.proxy.reader.rawData;
			//当success:false ,isException:false  --业务异常
			if(!success && ! rawData.isException){
				Ext.Msg.alert(pay.cashRec.i18n('foss.stl.pay.common.alert'),rawData.message);
				bt.enable(true);
				return false;
			}  
	    	if(success){
	    		var result =   Ext.decode(operation.response.responseText);
	    		grid.show();
				toolBar = grid.getDockedItems('toolbar[dock="bottom"]')[0];
				//总条数
				toolBar.getComponent('totalCount').setValue(result.billCashRecDRVo.billCashRecPayInDResultDto.totalCount);
				//应缴款金额
				toolBar.getComponent('totalAmount').setValue(pay.cashRec.convertAmount(result.billCashRecDRVo.billCashRecPayInDResultDto.totalAmount));
				//未缴款总金额
				toolBar.getComponent('paidTotalAmount').setValue(pay.cashRec.convertAmount(result.billCashRecDRVo.billCashRecPayInDResultDto.totalPaidAmount));
				//已缴款金额
				toolBar.getComponent('overdueTotalAmount').setValue(pay.cashRec.convertAmount(result.billCashRecDRVo.billCashRecPayInDResultDto.totalOverdueAmount));
				bt.enable(true);
	    	} 
	      }
	    }); 
	}else {
		Ext.Msg.alert(pay.cashRec.i18n('foss.stl.pay.common.alert'),pay.cashRec.i18n('foss.stl.pay.common.validateFailAlert'));
	}
}


/**
 *声明缴款报表查询明细grid 
 */
//缴款报表查询明细model
Ext.define('Foss.cashRecD.queryCashRecDetailModel',{
	extend:'Ext.data.Model',
	idgen: 'uuid',//EXT在前台为每个模型额外以UUID方式生成主键
	idProperty : 'extid',//以上生成的主键用在名叫“extid”的列 
	fields:[
	{
	    name: 'extid'
	},{
		name:'reportNo'
	},{
		//运单号
		name:'sourceBillNo'
	},{
		//业务日期
		name:'createTime',
		type:'date',
		convert:stl.longToDateConvert 
	},{
		//来源单据类型
		name:'sourceBillType'
	},{
		//客户编码
		name:'customerCode'
	},{
		//客户名称
		name:'customerName'
	},{
		//金额
		name:'amount',
		type:'long'
	},{
		//已缴款
		name:'paidAmount',
		type:'long'
	},{
		//未缴款
		name:'overdueAmount',
		type:'long'
	},{
		//预收款金额
		name:'precollectedAmount',
		type:'long'
	},{
		//非营业款金额
		name:'unclerksAmount',
		type:'long'
	},{
		//营业款金额		
		name:'clerksAmount',
		type:'long'
	},{
		//来源单号		
		name:'sourceBillNo'
	},{
		name:'paymentType'
	},{
		name:'businessDate',
		type:'date',
		convert:stl.longToDateConvert 
	},{
		//收款部门编号		
		name:'collectionOrgCode'
	},{
		name:'collectionOrgName'
	},{
		//客户编码		
		name:'customerCode'
	},{
		name:'customerName'
	},{
		//收入部门		
		name:'generatingOrgCode'
	},{
		name:'generatingOrgName'
	},{
		//收银确认时间		
		name:'cashConfirmTime',
		type:'date',
		convert:stl.longToDateConvert 
	},{
		name:'cashConfirmUserCode'
	},{
		//收银确认人名称		
		name:'cashConfirmUserName'
	},{
		name:'accountDate',
		type:'date',
		convert:stl.longToDateConvert 
	},{
		//单据状态		
		name:'status'
	},{
		name:'customerType'
	},{
		name:'modifyTime'
	},{
		name:'waybillNo'
	}]
});


//查询缴款报表报表Store
Ext.define('Foss.cashRecD.queryCashRecDStore',{
	extend:'Ext.data.Store',
	pageSize : pay.cashRec.STLREVERSEWRITEOFF_ONEPAGESIZE ,
	model:'Foss.cashRecD.queryCashRecDetailModel',
	sorters: [{
	     property: 'businessDate',
	     direction: 'ASC'
	 }],
	proxy:{
		type:'ajax',
		url:pay.realPath('queryReportCashRecPayInD.action'),
		actionMethods:'post',
		reader:{
			type:'json',
			root:'billCashRecDRVo.billCashRecPayInDResultDto.cashRpDList',
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




//导出缴款报表查询报表
pay.cashRec.cashRecDPayInExport=function(){
	var	columns,
	searchParams,
	arrayColumns,
	arrayColumnNames,
	grid;
//获取表格
grid = Ext.getCmp('T_pay-manageCashRecPayInReport_content').getCashRecGrid();
var form= grid.ownerCt.down('form').getForm();


//如果没有查询到数据，则不能进行导出操作
if(grid.store.data.length==0){
	Ext.Msg.alert(pay.cashRec.i18n('foss.stl.pay.common.alert'),pay.cashRec.i18n('foss.stl.pay.common.noDataToExport'));
	return false;
}



//进行导出提示
Ext.MessageBox.show({
  title: pay.cashRec.i18n('foss.stl.pay.common.alert'),
  msg: pay.cashRec.i18n('foss.stl.pay.queryBillCashRecPayInReport.isExportDataFromCashRec'),
  buttons: Ext.MessageBox.YESNOCANCEL,
  buttonText:{ 
      yes:pay.cashRec.i18n('foss.stl.pay.common.defaultColumnExport'),
      no: pay.cashRec.i18n('foss.stl.pay.common.customColumnExport'),
      cancel:pay.cashRec.i18n('foss.stl.pay.common.cancel')
  },
  fn: function(e){
  	if(e=='yes'){
  		//默认显示列
			arrayColumns = ['sourceBillNo','customerCode','customerName','sourceBillType','paymentType',
//			                'customerType',
							'businessDate',
//							'status',
							'collectionOrgCode','collectionOrgName','amount',
							'paidAmount','overdueAmount','accountDate'];
			//默认显示列名称
			arrayColumnNames = [
			                    pay.cashRec.i18n('foss.stl.pay.queryBillCashRecPayInReport.sourceBillNo'),
			                    pay.cashRec.i18n('foss.stl.pay.queryBillCashRecPayInReport.customerCode'),
			                    pay.cashRec.i18n('foss.stl.pay.queryBillCashRecPayInReport.customerName'),
			                    pay.cashRec.i18n('foss.stl.pay.queryBillCashRecPayInReport.sourceBillType'),
			                    pay.cashRec.i18n('foss.stl.pay.queryBillCashRecPayInReport.paymentType'),
//			                    pay.cashRec.i18n('foss.stl.pay.queryBillCashRecPayInReport.customerType'),
			                    pay.cashRec.i18n('foss.stl.pay.queryBillCashRecPayInReport.businessDate'),
//			                    pay.cashRec.i18n('foss.stl.pay.queryBillCashRecPayInReport.status'),
			                    pay.cashRec.i18n('foss.stl.pay.queryBillCashRecPayInReport.collectionOrgCode'),
			                    pay.cashRec.i18n('foss.stl.pay.queryBillCashRecPayInReport.collectionOrgName'),
			                    pay.cashRec.i18n('foss.stl.pay.queryBillCashRecPayInReport.amount'),
			                    pay.cashRec.i18n('foss.stl.pay.queryBillCashRecPayInReport.paidAmount'),
			                    pay.cashRec.i18n('foss.stl.pay.queryBillCashRecPayInReport.overdueAmount'),
			                    pay.cashRec.i18n('foss.stl.pay.queryBillCashRecPayInReport.accountDate')
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
					if(columns[i].text!=pay.cashRec.i18n('foss.stl.pay.common.actionColumn')){
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
		//导出报表，传入传入缴款报表查询报表参数
	  	Ext.apply(grid.store.submitParams, {
	  		'billCashRecDVo.billCashRecPayInDDto.arrayColumns':arrayColumns,
	  		'billCashRecDVo.billCashRecPayInDDto.arrayColumnNames':arrayColumnNames
	  	});
		
		//拼接vo，注入到后台
		Ext.Ajax.request({
//		    url: '../pay/exportCashRecPayIn.action',
		    url:pay.realPath('exportCashRecPayIn.action'),
		    form: Ext.fly('downloadAttachFileForm'),
		    method : 'post',
		    params :grid.store.submitParams,
		    isUpload: true,
		    success : function(response){
		    	var result = Ext.decode(response.responseText);
		    	//如果异常信息有值，则弹出提示
		    	if(!Ext.isEmpty(result.errorMessage)){
		    		Ext.Msg.alert(pay.cashRec.i18n('foss.stl.pay.common.alert'),result.errorMessage);
		    		return false;
		    	}
				Ext.ux.Toast.msg(pay.cashRec.i18n('foss.stl.pay.common.alert'),pay.cashRec.i18n('foss.stl.pay.common.exportSuccess'), 'success', 1000);
		    },
		    failure : function(response){
				Ext.ux.Toast.msg(pay.cashRec.i18n('foss.stl.pay.common.alert'),pay.cashRec.i18n('foss.stl.pay.common.exportFailed'), 'error', 1000);
		    },
			exception:function(response){
				var result = Ext.decode(response.responseText);
				Ext.Msg.alert(pay.cashRec.i18n('foss.stl.pay.common.alert'),result.message);
			}
		});
	}
});
}




//缴款报表查询按日期查询 Form
Ext.define('Foss.cashRecD.StlCashRecDFrom',{
	extend:'Ext.form.Panel',
	frame:true,
	columnWidth:1,
	defaults:{
		margin :'10 10 10 10',
		labelWidth :85,
		colspan : 1 
	},
	defaultType:'textfield',
	layout:{
		type :'column'
	},
	 items:[{
	    	xtype:'datefield',
	    	name:'billCashRecDVo.billCashRecPayInDDto.businessStartDate',
	    	fieldLabel:pay.cashRec.i18n('foss.stl.pay.queryBillCashRecPayInReport.reportDateStart'),
	    	labelWidth:100,
	    	value: stl.getTargetDate(new Date(),-pay.cashRec.cashRec_THREEDAYS),
	    	format:'Y-m-d',
	    	allowBlank:false,
	    	columnWidth:.3
	    },{
	    	xtype:'datefield',
	   	 	name:'billCashRecDVo.billCashRecPayInDDto.businessEndDate',
	   	 	labelWidth:100,
	    	fieldLabel:pay.cashRec.i18n('foss.stl.pay.queryBillCashRecPayInReport.reportDateEnd'),
	    	format:'Y-m-d',
	    	value:new Date(),
	    	columnWidth:.3,
	    	allowBlank:false,
	    	maxValue:new Date()
	    },{
	    	xtype: 'container',
			border : false,
			height : 24,
			columnWidth:.3,
			html: '&nbsp;'
	    },{
	    	xtype: 'combobox',
			name:'billCashRecDVo.billCashRecPayInDDto.sourceBillType',
			columnWidth:.3,
			labelWidth:100,
	        fieldLabel:pay.cashRec.i18n('foss.stl.pay.queryBillCashRecPayInReport.sourceBillType'),
			store:FossDataDictionary.getDataDictionaryStore(settlementDict.CASH_COLLECTION_RPT_D__SOURCE_BILL_TYPE,null,{
				 'valueCode': '',
          		 'valueName': pay.cashRec.i18n('foss.stl.pay.queryBillCashRecPayInReport.all')
			},[
			   	pay.cashRec.CASH_COLLECTION_RPT_D__SOURCE_BILL_TYPE__CASH_COLLECTION,
			   	pay.cashRec.CASH_COLLECTION_RPT_D__SOURCE_BILL_TYPE__DEPOSIT_RECEIVED,
			   	pay.cashRec.CASH_COLLECTION_RPT_D__SOURCE_BILL_TYPE__REPAYMENT
			   ]),
			queryModel:'local',
			editable:false,
			displayField:'valueName',
			valueField:'valueCode',
			value:''
	    },{
	    	xtype: 'combobox',
			name:'billCashRecDVo.billCashRecPayInDDto.paymentType',
			columnWidth:.3,
			labelWidth:100,
	        fieldLabel:pay.cashRec.i18n('foss.stl.pay.queryBillCashRecPayInReport.paymentType'),
			store:FossDataDictionary.getDataDictionaryStore(settlementDict.SETTLEMENT__PAYMENT_TYPE,null,{
				 'valueCode': '',
          		 'valueName': pay.cashRec.i18n('foss.stl.pay.queryBillCashRecPayInReport.all')
			},[
				pay.cashRec.SETTLEMENT__PAYMENT_TYPE__CASH , // 现金
				pay.cashRec.SETTLEMENT__PAYMENT_TYPE__CARD,// 银行卡
				pay.cashRec.SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER,// 电汇
				pay.cashRec.SETTLEMENT__PAYMENT_TYPE__NOTE,
				pay.cashRec.SETTLEMENT__PAYMENT_TYPE__ONLINE// 支票
			]),
			queryModel:'local',
			editable:false,
			displayField:'valueName',
			valueField:'valueCode',
			value:''
	    },{
	    	xtype:'commonsaledepartmentselector',
	    	name:'billCashRecDVo.billCashRecPayInDDto.collectionOrgCode',
	    	columnWidth:.3,
			labelWidth:100,
			height:24,
			value : stl.currentDept.name,
	        fieldLabel:pay.cashRec.i18n('foss.stl.pay.queryBillCashRecPayInReport.dept'),
	    },{	
			border: 1,
			xtype:'container',
			columnWidth:1,
			defaultType:'button',
			layout:'column',
			items:[{
				text:pay.cashRec.i18n('foss.stl.pay.common.reset'),
				columnWidth:.08,
				handler:pay.cashRec.reset
			},{
				xtype: 'container',
				border : false,
				columnWidth:.74,			
				style: {
					textAlign:'left'
				},			
				html: '&nbsp;&nbsp;<span style="color:red">新生成业务单据请于10分钟后查询</span>'
			},{
				text:pay.cashRec.i18n('foss.stl.pay.common.query'),
				cls:'yellow_button',
				columnWidth:.08,
				handler:pay.cashRec.queryByCashRecD
			}]
	    }]		
});


//定义现金缴款收入报表Grid
Ext.define('Foss.cashRec.StlCashRecReportGrid',{
	extend:'Ext.grid.Panel',
    title:pay.cashRec.i18n('foss.stl.pay.queryBillCashRecPayInReport.cashIncomeReport'),
    frame:true,
    emptyText:pay.cashRec.i18n('foss.stl.pay.common.noResult'),
	height:500,
	hidMeParams:null,
	setHidMeParams:function(params){
		this.hidMeParams=params;
	},
	getHidMeParams:function(){
		return this.hidMeParams;
	},
	//应缴款金额
	totalAmount:null,
	getTotalAmount:function(){
		var me=this;
		if(Ext.isEmpty(me.totalAmount)){
			me.totalAmount=0;
		}
		return me.totalAmount;
	},
	//已缴款金额
	paidTotalAmount:null,
	getPaidTotalAmount:function(){
		var me=this;
		if(Ext.isEmpty(me.paidTotalAmount)){
			me.paidTotalAmount=0;
		}
		return me.paidTotalAmount;
	},
	//未缴款
	overdueTotalAmount:null,
	getOverdueTotalAmount:function(){
		var me=this;
		if(Ext.isEmpty(me.overdueTotalAmount)){
			me.overdueTotalAmount=0;
		}
		return me.overdueTotalAmount;
	},
	totalCount:null,//总条数
	getTotalCount:function(){
		var me=this;
		if(Ext.isEmpty(me.totalCount)){
			me.totalCount=0;
		}
		return me.totalCount;
	},
	
	columns:[
//	         {
//				header:pay.cashRec.i18n('foss.stl.pay.queryBillCashRecPayInReport.reportNo'),
//				dataIndex: 'reportNo',
//				hidden:true
//			},
			{
				header: pay.cashRec.i18n('foss.stl.pay.queryBillCashRecPayInReport.sourceBillNo'),
				dataIndex: 'sourceBillNo'
			},{
				header: pay.cashRec.i18n('foss.stl.pay.queryBillCashRecPayInReport.waybillNo'),
				dataIndex: 'waybillNo',
				hidden:true
			},{
				header: pay.cashRec.i18n('foss.stl.pay.queryBillCashRecPayInReport.customerCode'),
				dataIndex: 'customerCode'
			},{
				header: pay.cashRec.i18n('foss.stl.pay.queryBillCashRecPayInReport.customerName'),
				dataIndex: 'customerName'
			}
			,{
				header: pay.cashRec.i18n('foss.stl.pay.queryBillCashRecPayInReport.sourceBillType'),
				dataIndex: 'sourceBillType',
				renderer:function(value){
		    		var displayField = FossDataDictionary. rendererSubmitToDisplay (value,settlementDict.CASH_COLLECTION_RPT_D__SOURCE_BILL_TYPE);
		    		return displayField;
		    	}
			},
			{
				header: pay.cashRec.i18n('foss.stl.pay.queryBillCashRecPayInReport.paymentType'),
				dataIndex: 'paymentType',
				renderer:function(value){
		    		var displayField = FossDataDictionary. rendererSubmitToDisplay (value,settlementDict.SETTLEMENT__PAYMENT_TYPE);
		    		return displayField;
		    	}
			}
//			,
//			{
//				header: pay.cashRec.i18n('foss.stl.pay.queryBillCashRecPayInReport.customerType'),
//				dataIndex: 'customerType',
//				renderer:function(value){
//		    		var displayField = FossDataDictionary. rendererSubmitToDisplay (value,settlementDict.SETTLEMENT__CUSTOMER_TYPE);
//		    		return displayField;
//		    	}
//			}
			,{
				header: pay.cashRec.i18n('foss.stl.pay.queryBillCashRecPayInReport.businessDate'),
				dataIndex: 'businessDate',
				renderer:function(value){
					return stl.dateFormat(value,'Y-m-d H:i:s');
				}
			}
//			,
//			{
//				header: pay.cashRec.i18n('foss.stl.pay.queryBillCashRecPayInReport.status'),
//				dataIndex: 'status',
//				renderer:function(value){
//		    		var displayField = FossDataDictionary. rendererSubmitToDisplay (value,settlementDict.BILL_CASH_COLLECTION__STATUS);
//		    		return displayField;
//		    	}
//			}
			,{
				header: pay.cashRec.i18n('foss.stl.pay.queryBillCashRecPayInReport.collectionOrgCode'),
				dataIndex: 'collectionOrgCode'
			},{
				header: pay.cashRec.i18n('foss.stl.pay.queryBillCashRecPayInReport.collectionOrgName'),
				dataIndex: 'collectionOrgName'
			},{
				header: pay.cashRec.i18n('foss.stl.pay.queryBillCashRecPayInReport.amount'),
				dataIndex: 'amount',
				renderer:stl.amountFormat,
				align:'right'
			},{
				header:pay.cashRec.i18n('foss.stl.pay.queryBillCashRecPayInReport.paidAmount') ,
				dataIndex: 'paidAmount',
				renderer:stl.amountFormat,
				align:'right'
			},{
				header: pay.cashRec.i18n('foss.stl.pay.queryBillCashRecPayInReport.overdueAmount'),
				dataIndex: 'overdueAmount',
				renderer:stl.amountFormat,
				align:'right'
			},{
				header:pay.cashRec.i18n('foss.stl.pay.queryBillCashRecPayInReport.clerksAmount') ,
				dataIndex: 'clerksAmount',
				renderer:stl.amountFormat,
				hidden:true,
				align:'right'
			},
//			,{
//				header:pay.cashRec.i18n('foss.stl.pay.queryBillCashRecPayInReport.unclerksAmount') ,
//				dataIndex: 'unclerksAmount',
//				renderer:stl.amountFormat,
//				hidden:true,
//				align:'right'
//			}
			{
				header: pay.cashRec.i18n('foss.stl.pay.queryBillCashRecPayInReport.accountDate'),
				dataIndex: 'accountDate',
				width:138,
				renderer:function(value){
					return stl.dateFormat(value,'Y-m-d H:i:s');
				}
			}
			,{
				header:pay.cashRec.i18n('foss.stl.pay.queryBillCashRecPayInReport.precollectedAmount') ,
				dataIndex: 'precollectedAmount',
				renderer:stl.amountFormat,
				hidden:true,
				align:'right'
			},
//			{
//				header: pay.cashRec.i18n('foss.stl.pay.queryBillCashRecPayInReport.generatingOrgCode'),
//				dataIndex: 'generatingOrgCode',
//				hidden:true
//			},{
//				header: pay.cashRec.i18n('foss.stl.pay.queryBillCashRecPayInReport.generatingOrgName'),
//				dataIndex: 'generatingOrgName',
//				hidden:true
//			},{
//				header: pay.cashRec.i18n('foss.stl.pay.queryBillCashRecPayInReport.cashConfirmTime'),
//				dataIndex: 'cashConfirmTime',
//				renderer:function(value){
//					return stl.dateFormat(value,'Y-m-d H:i:s');
//				},
//				hidden:true
//			},
			{
				header: pay.cashRec.i18n('foss.stl.pay.queryBillCashRecPayInReport.createTime'),
				dataIndex: 'createTime',
				renderer:function(value){
					return stl.dateFormat(value,'Y-m-d H:i:s');
				},
			   hidden:true
			},{
				header: pay.cashRec.i18n('foss.stl.pay.queryBillCashRecPayInReport.modifyTime'),
				dataIndex: 'modifyTime',
				renderer:function(value){
					return stl.dateFormat(value,'Y-m-d H:i:s');
				},
			   hidden:true
			}
//			,{
//				header: pay.cashRec.i18n('foss.stl.pay.queryBillCashRecPayInReport.cashConfirmUserCode'),
//				dataIndex: 'cashConfirmUserCode',
//				hidden:true
//			},{
//				header: pay.cashRec.i18n('foss.stl.pay.queryBillCashRecPayInReport.cashConfirmUserName'),
//				dataIndex: 'cashConfirmUserName',
//				hidden:true
//			}
     ],
	viewConfig:{enableTextSelection: true},
	constructor:function(config){
		var me = this;
		cfg = Ext.apply({},config);
		me.store=Ext.create('Foss.cashRecD.queryCashRecDStore');
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
				text:pay.cashRec.i18n('foss.stl.pay.common.export'),
				columnWidth:.05,
				handler:pay.cashRec.cashRecDPayInExport
			}]
		},{
	   		xtype: 'toolbar',
		    dock: 'bottom',
		    layout:'column',		    	
		    defaults:{
				margin:'0 0 5 3'
			},		
		    items: [{
				height:5,
				columnWidth:1,
				layout:'hbox'
			},{
				xtype:'displayfield',
				allowBlank:true,
				columnWidth:.25,
				name:'totalCount',
				itemId: 'totalCount',
				labelWidth:80,
				width:185,
				fieldLabel:pay.cashRec.i18n('foss.stl.pay.queryBillCashRecPayInReport.totalCountRecord')
			},{
				xtype:'displayfield',
				allowBlank:true,
				columnWidth:.25,
				labelWidth:80,
				itemId: 'totalAmount',
				name:'totalAmount',
				fieldLabel:pay.cashRec.i18n('foss.stl.pay.queryBillCashRecPayInReport.totalAmount'),
				value:me.getTotalAmount()
			},{
				xtype:'displayfield',
				allowBlank:true,
				columnWidth:.25,
				name:'paidTotalAmount',
				itemId: 'paidTotalAmount',
				value:me.getPaidTotalAmount(),
				labelWidth:90,
				fieldLabel:pay.cashRec.i18n('foss.stl.pay.queryBillCashRecPayInReport.paidAmount')
			},{
				xtype:'displayfield',
				allowBlank:true,
				columnWidth:.25,
				labelWidth:90,
				fieldLabel:pay.cashRec.i18n('foss.stl.pay.queryBillCashRecPayInReport.overdueAmount'),
				itemId: 'overdueTotalAmount',
				name:'overdueTotalAmount',
				value:me.getOverdueTotalAmount()
			},{
				xtype:'standardpaging',
				store:me.store,
				columnWidth:1,
				plugins: Ext.create('Deppon.ux.PageSizePlugin', {
					//设置分页记录最大值，防止输入过大的数值
					maximumSize: 200
				})
			 }]
		}];	
		me.callParent([cfg]);
	}
});


// 初始化界面
Ext.onReady(function() {
	Ext.QuickTips.init();
	//缴款报表查询界面
	var cashRecGrid = Ext.create('Foss.cashRec.StlCashRecReportGrid',{hidden:true});
	//缴款报表查询报表查询Form
	var cashFrom=Ext.create('Foss.cashRecD.StlCashRecDFrom');
	
	Ext.create('Ext.panel.Panel',{
		id: 'T_pay-manageCashRecPayInReport_content',
		cls: "panelContentNToolbar",
		bodyCls: 'panelContentNToolbar-body',
		layout: 'auto',
		getCashRecGrid:function(){
			return cashRecGrid;
		},
		items: [cashFrom,cashRecGrid],
		renderTo: 'T_pay-manageCashRecPayInReport-body'

	});
});