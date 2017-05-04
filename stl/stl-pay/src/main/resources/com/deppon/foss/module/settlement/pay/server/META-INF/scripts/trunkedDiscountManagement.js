//***************零担事后折折扣***************//
/**
 * 折扣单MODEL
 */
Ext.define('Foss.trunkedDiscountManagement.GridModel', {
	extend : 'Ext.data.Model',
	fields : [ {
		name : 'generatingOrgName',//收入部门
		type : 'string'
	},{
		name : 'contractOrgName',//合同部门
		type : 'string'
	},{
		name : 'contractOrgCode',//合同部门编码
		type : 'string'
	},{
		name : 'invoiceMark',//发票标记
		type : 'string'
	},{
		name : 'unifiedSettlement',//是否统一结算
		type : 'string'
	},{
		name : 'discountType',//折扣单类型
		type : 'string'
	},{
		name : 'productCode',//运输性质
		type : 'string'
	},{
		name : 'waybillNo',//运单号
		type : 'string'
	},{
		name : 'payableNo',//应付单号
		type : 'string'
	},{
		name : 'customerName',//客户名称
		type : 'string'
	},{
		name : 'customerNo',//客户编码
		type : 'string'
	},{
		name : 'totalMoney',//总金额
		type : 'double'
	},{
		name : 'discountMoney',//折扣后金额
		type : 'double'
	},{
		name : 'transportRate',//折扣率
		type : 'double'
	},{
		name : 'pureFee',//原纯运费
		type : 'string'
	},{
		name : 'codAgencyFee',//代收手续费
		type : 'double'
	},{
		name : 'insuranceFee',//保价费
		type : 'double'
	},{
		name : 'deliveryGoodsFee',//送货费
		type : 'double'
	},{
		name : 'pickupFee',//接货费
		type : 'double'
	},{
		name : 'otherFee',//其他费用
		type : 'string'
	},{
		name : 'discountFee',//原纯运费折扣金额
		type : 'double'
	},{
		name : 'pureDiscountFee',//折扣后运费
		type : 'double'
	},{
		name : 'verifyDiscountFee',//已核销折扣金额
		type : 'double'
	}]
});

/**
 * 折扣单STORE
 */
Ext.define('Foss.trunkedDiscountManagement.GridStore',{
	extend:'Ext.data.Store',
	model:'Foss.trunkedDiscountManagement.GridModel',
	pageSize:50,
	proxy:{
		type:'ajax',
		url:pay.realPath('queryTrunkedDiscountByCust.action'),
		actionMethods:'POST',
		reader:{
			type:'json',
			/**
			 * 后台封装了了vo返回数据，vo里面包含dto，dto里面是Entity集合
			 */
			root:'trunkedDiscountManagementVo.trunkedDiscountManagementDto.trunkDiscountManagementDList',
			/**
			 * 总行数
			 */
			totalProperty:'totalCount'
		}
	},
	listeners:{
		'beforeLoad':function(store, operation, eOpts){
			var searchParams;
			if(pay.trunkedDiscountManagement.queryTabType==pay.STATEMENTQUERYTAB_BYCUSTOMER){
				searchParams = {
					'trunkedDiscountManagementVo.trunkedDiscountManagementDto.periodBeginDate':pay.trunkedDiscountManagement.periodBeginDate,
			 		'trunkedDiscountManagementVo.trunkedDiscountManagementDto.periodEndDate':pay.trunkedDiscountManagement.periodEndDate,
			 		'trunkedDiscountManagementVo.trunkedDiscountManagementDto.customerNo':pay.trunkedDiscountManagement.customerNo
				};
			}else{
				var numbers = stl.splitToArray(pay.trunkedDiscountManagement.numbers);
				searchParams = {
					'trunkedDiscountManagementVo.trunkedDiscountManagementDto.waybillNo':numbers
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
 * 零担事后折折扣表格
 */
Ext.define('Foss.trunkedDiscountManagement.Grid',{
	extend:'Ext.grid.Panel',
	title: pay.trunkedDiscountManagement.i18n('foss.stl.pay.trunkedDiscountManagement.trunkedDiscount'),//零担事后折折扣单
    bodyCls: 'autoHeight',
	cls: 'autoHeight',
	emptyText:pay.trunkedDiscountManagement.i18n('foss.stl.pay.common.noResult'),//查询结果为空！
	frame:true,
	detailWin:null,
    store:Ext.create('Foss.trunkedDiscountManagement.GridStore'),
    height:500,
  	viewConfig:{
  		enableTextSelection : true//设置行可以选择，进而可以复制
  	},
  	columns: [{
		xtype:'rownumberer',
		width:40
	},{
		header:pay.trunkedDiscountManagement.i18n('foss.stl.pay.trunkedDiscountManagement.generatingOrgName'),//收入部门
		dataIndex:'generatingOrgName',
		align:'center',
		width:130
	},{
		header:pay.trunkedDiscountManagement.i18n('foss.stl.pay.trunkedDiscountManagement.contractOrgName'),//合同部门
		dataIndex:'contractOrgName',
		align:'center',
		width:130
	},{
		header:pay.trunkedDiscountManagement.i18n('foss.stl.pay.trunkedDiscountManagement.contractOrgCode'),//合同部门编码
		dataIndex:'contractOrgCode',
		align:'center',
		width:130
	},{
		header:pay.trunkedDiscountManagement.i18n('foss.stl.pay.trunkedDiscountManagement.invoiceMark'),//发票标记
		dataIndex:'invoiceMark',
		align:'center',
		renderer : function(value) {
			var displayField = FossDataDictionary.rendererSubmitToDisplay(value, 'SETTLEMENT_INVOICE_MARK');
			return displayField;
		},
		width:130
	},{
		header:pay.trunkedDiscountManagement.i18n('foss.stl.pay.trunkedDiscountManagement.unifiedSettlement'),//是否统一结算
		dataIndex:'unifiedSettlement',
		align:'center',
		renderer : function(value) {
			if (value == 'Y') {
				return pay.trunkedDiscountManagement.i18n('foss.stl.pay.common.yes');
			} else {
				return pay.trunkedDiscountManagement.i18n('foss.stl.pay.common.no');
			}
		},
		width:130
	},{
		header:pay.trunkedDiscountManagement.i18n('foss.stl.pay.trunkedDiscountManagement.discountType'),//折扣单类型
		dataIndex:'discountType',
		align:'center',
		renderer : function(value){
			return pay.trunkedDiscountManagement.i18n('foss.stl.pay.trunkedDiscountManagement.discountValue');;
		},
		width:130
	},{
		header:pay.trunkedDiscountManagement.i18n('foss.stl.pay.trunkedDiscountManagement.productCode'),//运输性质
		dataIndex:'productCode',
		align:'center',
		renderer : function(value) {
			return Foss.pkp.ProductData.rendererSubmitToDisplay(value);
		},
		width:130
	},{
		header:pay.trunkedDiscountManagement.i18n('foss.stl.pay.trunkedDiscountManagement.waybillNo'),//运单号
		dataIndex:'waybillNo',
		align:'center',
		width:130
	},{
		header:pay.trunkedDiscountManagement.i18n('foss.stl.pay.trunkedDiscountManagement.payableNo'),//应付单号
		dataIndex:'payableNo',
		align:'center',
		width:130
	},{
		header:pay.trunkedDiscountManagement.i18n('foss.stl.pay.trunkedDiscountManagement.customerName'),//客户名称
		dataIndex:'customerName',
		align:'center',
		width:130
	},{
		header:pay.trunkedDiscountManagement.i18n('foss.stl.pay.trunkedDiscountManagement.customerNo'),//客户编码
		dataIndex:'customerNo',
		align:'center',
		width:130
	},{
		header:pay.trunkedDiscountManagement.i18n('foss.stl.pay.trunkedDiscountManagement.totalMoney'),//总金额
		dataIndex:'totalMoney',
		align:'center',
		width:130
	},{
		header:pay.trunkedDiscountManagement.i18n('foss.stl.pay.trunkedDiscountManagement.discountMoney'),//折扣后金额
		dataIndex:'discountMoney',
		align:'center',
		width:130
	},{
		header:pay.trunkedDiscountManagement.i18n('foss.stl.pay.trunkedDiscountManagement.transportRate'),//折扣率
		dataIndex:'transportRate',
		align:'center',
		width:130
	},{
		header:pay.trunkedDiscountManagement.i18n('foss.stl.pay.trunkedDiscountManagement.pureFee'),//原纯运费
		dataIndex:'pureFee',
		align:'center',
		width:130
	},{
		header:pay.trunkedDiscountManagement.i18n('foss.stl.pay.trunkedDiscountManagement.codAgencyFee'),//代收货款手续费
		dataIndex:'codAgencyFee',
		align:'center',
		width:130
	},{
		header:pay.trunkedDiscountManagement.i18n('foss.stl.pay.trunkedDiscountManagement.insuranceFee'),//保价费
		dataIndex:'insuranceFee',
		align:'center',
		width:130
	},{
		header:pay.trunkedDiscountManagement.i18n('foss.stl.pay.trunkedDiscountManagement.deliveryGoodsFee'),//送货费
		dataIndex:'deliveryGoodsFee',
		align:'center',
		width:130
	},{
		header:pay.trunkedDiscountManagement.i18n('foss.stl.pay.trunkedDiscountManagement.pickupFee'),//接货费
		dataIndex:'pickupFee',
		align:'center',
		width:130
	},{
		header:pay.trunkedDiscountManagement.i18n('foss.stl.pay.trunkedDiscountManagement.otherFee'),//其他费用
		dataIndex:'otherFee',
		align:'center',
		width:130
	},{
		header:pay.trunkedDiscountManagement.i18n('foss.stl.pay.trunkedDiscountManagement.discountFee'),//纯运费折扣金额
		dataIndex:'discountFee',
		align:'center',
		width:130
	},{
		header:pay.trunkedDiscountManagement.i18n('foss.stl.pay.trunkedDiscountManagement.pureDiscountFee'),//折扣后运费
		dataIndex:'pureDiscountFee',
		align:'center',
		width:130
	},{
		header:pay.trunkedDiscountManagement.i18n('foss.stl.pay.trunkedDiscountManagement.verifyDiscountFee'),//以核销折扣金额
		dataIndex:'verifyDiscountFee',
		align:'center',
		width:130
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
				name : 'pay_trunkedDiscountManagement_pageSizePluginName',
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




//*************查询条件***********//

/**
 * 按客户查询折扣单
 */
pay.trunkedDiscountManagement.statementSelectByCust = function(){
	var form = this.up('form').getForm();
	//判断是否合法
	if(form.isValid()){
		//开始时间
		pay.trunkedDiscountManagement.periodBeginDate = form.findField('periodBeginDate').getValue();
		//结束时间
		pay.trunkedDiscountManagement.periodEndDate = form.findField('periodEndDate').getValue();
		//客户编码
		pay.trunkedDiscountManagement.customerNo = form.findField('customerNo').getValue();
		
		//开始日期
		var periodBeginDate = pay.trunkedDiscountManagement.periodBeginDate;
		//结束日期
		var periodEndDate = pay.trunkedDiscountManagement.periodEndDate;
		//客户编码
		var customerNo = pay.trunkedDiscountManagement.customerNo;
		
		//查询类型
		pay.trunkedDiscountManagement.queryTabType = pay.STATEMENTQUERYTAB_BYCUSTOMER;
		//校验日期
		if(Ext.isEmpty(periodBeginDate)||Ext.isEmpty(periodEndDate)){
			Ext.Msg.alert(pay.trunkedDiscountManagement.i18n('foss.stl.pay.common.alert'),pay.trunkedDiscountManagement.i18n('foss.stl.pay.trunkedDiscountManagement.quryDateIsNullWarning'));//起始日期或结束日期间为空，不能进行查询!
			return false;
		}
		//比较起始日期和结束日期
		var compareTwoDate = stl.compareTwoDate(periodBeginDate,periodEndDate);
		if(compareTwoDate>stl.DATELIMITDAYS_MONTH){
			Ext.Msg.alert(pay.trunkedDiscountManagement.i18n('foss.stl.pay.common.alert'),//温馨提示
			pay.trunkedDiscountManagement.i18n('foss.stl.pay.trunkedDiscountManagement.queryDateMaxLimit'));//起始日期和结束日期间隔不能超过31天!
			return false;
		}else if(compareTwoDate<1){
			Ext.Msg.alert(pay.trunkedDiscountManagement.i18n('foss.stl.pay.common.alert'),//温馨提示
					pay.trunkedDiscountManagement.i18n('foss.stl.pay.common.dateEndBeforeStatrtWarining'));//结束日期不能早于起始日期!
			return false;
		}
		//获取grid
		//var grid = Ext.getCmp("T_pay-trunkedDiscountManagement_content").getGrid();
		var grid = pay.trunkedDiscountManagement.panel.getGrid();
		grid.getStore().loadPage(1);
	}else{
		Ext.Msg.alert(pay.trunkedDiscountManagement.i18n('foss.stl.pay.common.alert'),//温馨提示
			pay.trunkedDiscountManagement.i18n('foss.stl.pay.common.validateFailAlert'));//请检查输入条件是否合法!
		return false;
	}
}

/**
 * 按单号查询折扣单
 */
pay.trunkedDiscountManagement.statementSelectByNumber = function(){		
	var form  = this.up('form').getForm();	
	//输入单号集合
	var numbers = form.findField('numbers').getValue();
	//查询类型
	pay.trunkedDiscountManagement.queryTabType = pay.STATEMENTQUERYTAB_BYNUMBER;
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
		 	Ext.Msg.alert(pay.trunkedDiscountManagement.i18n('foss.stl.pay.common.alert'),//温馨提示
		 		pay.trunkedDiscountManagement.i18n('foss.stl.pay.common.queryNosLimit'));//输入单号不能超过10个!
		 	return false;
		 }
	}else{
		Ext.Msg.alert(pay.trunkedDiscountManagement.i18n('foss.stl.pay.common.alert'),//温馨提示
			pay.trunkedDiscountManagement.i18n('foss.stl.pay.trunkedDiscountManagement.billNosIsNullWarning'));//输入单号不能为空！
		return false;
	}
	//当界面校验都通过后，才执行查询方法
	if(form.isValid()){
		pay.trunkedDiscountManagement.numbers = numbers;
		//获取grid
		//var grid = Ext.getCmp("T_pay-trunkedDiscountManagement_content").getGrid();
		var grid = pay.trunkedDiscountManagement.panel.getGrid();
		grid.getStore().loadPage(1);
	}else{
		Ext.Msg.alert(pay.trunkedDiscountManagement.i18n('foss.stl.pay.common.alert'),//温馨提示
			pay.trunkedDiscountManagement.i18n('foss.stl.pay.common.validateFailAlert'));//请检查输入条件是否合法!
		return false;
	}	
};

/**
 * 折扣单查询页签
 */
Ext.define('Foss.trunkedDiscountManagement.QueryTab',{
	extend:'Ext.tab.Panel',
	frame:false,
	bodyCls: 'autoHeight',
	cls: 'innerTabPanel',
	height:160,
	items:[{
       	title: pay.trunkedDiscountManagement.i18n('foss.stl.pay.trunkedDiscountManagement.queryByCustomer'),//按客户查询
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
		    	fieldLabel:pay.trunkedDiscountManagement.i18n('foss.stl.pay.trunkedDiscountManagement.periodBeginDate'),//开始时间
		    	allowBlank:false,
		    	name:'periodBeginDate',
		    	columnWidth: .3,
		    	format:'Y-m-d',
		    	value:stl.getTargetDate(new Date(),-1)
		    },{
		    	xtype:'datefield',
		    	fieldLabel:pay.trunkedDiscountManagement.i18n('foss.stl.pay.trunkedDiscountManagement.periodEndDate'),//结束时间
		    	allowBlank:false,
		    	name:'periodEndDate',
		    	format:'Y-m-d',
		    	columnWidth: .3,
		    	value:stl.getTargetDate(new Date(),+1)
		    },{
		    	xtype:'commoncustomerselector',
		    	listWidth:300,
		    	name:'customerNo',
		    	active:'Y',									   
		    	fieldLabel:pay.trunkedDiscountManagement.i18n('foss.stl.pay.trunkedDiscountManagement.customerMessage'),//客户信息
		    	columnWidth:.3
		    },{
				border: 1,
				xtype:'container',
				columnWidth:1,
				defaultType:'button',
				layout:'column',
				items:[{
					text:pay.trunkedDiscountManagement.i18n('foss.stl.pay.trunkedDiscountManagement.reset'),//重置
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
					text:pay.trunkedDiscountManagement.i18n('foss.stl.pay.trunkedDiscountManagement.query'),//查询
					cls:'yellow_button',
					columnWidth:.08,
					handler:pay.trunkedDiscountManagement.statementSelectByCust
				}]
		    }]
	    }]
	},{
        title: pay.trunkedDiscountManagement.i18n('foss.stl.pay.trunkedDiscountManagement.queryByNumber'),//按单号查询
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
				fieldLabel:pay.trunkedDiscountManagement.i18n('foss.stl.pay.trunkedDiscountManagement.number'),//单号
				allowBlank:false,
				columnWidth:.65,
				regex:/^((ZK)?[0-9]{7,14}[;,])*((ZK)?[0-9]{7,14}[;,]?)$/i,//329757-放开对运单号14位的校验
				labelWidth:70,
				labelAlign:'right',
				name:'numbers',//单号集合
				autoScroll:true,
				height:60
		    },{
				xtype:'container',
				columnWidth:.35,
				layout:'vbox',
				items:[{
					xtype:'component',
					padding:'0 0 0 10',
					autoEl:{
						tag:'div',
						html:'<span style="color:red;">'+pay.trunkedDiscountManagement.i18n('foss.stl.pay.trunkedDiscountManagement.queryWaybillNo')+'</span>'
					}//请输入运单号
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
					text:pay.trunkedDiscountManagement.i18n('foss.stl.pay.trunkedDiscountManagement.reset'),//重置
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
					text:pay.trunkedDiscountManagement.i18n('foss.stl.pay.trunkedDiscountManagement.query'),//查询
					cls:'yellow_button',
					columnWidth:.075,
					handler:pay.trunkedDiscountManagement.statementSelectByNumber
				}]
        	}]
        }]
    }]
});

/**
 * 零担事后折折扣管理界面入口
 */
Ext.onReady(function() {
	Ext.Ajax.timeout = 60000*30;
	//查询表单
	var queryTab = Ext.create('Foss.trunkedDiscountManagement.QueryTab');
	//查询结果列表
	var grid = Ext.create('Foss.trunkedDiscountManagement.Grid');
	//创建panel
	pay.trunkedDiscountManagement.panel = 
	Ext.create('Ext.panel.Panel',{
		//id: 'T_pay-trunkedDiscountManagement_content',
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
		renderTo: 'T_pay-trunkedDiscountManagement-body',
	});
});