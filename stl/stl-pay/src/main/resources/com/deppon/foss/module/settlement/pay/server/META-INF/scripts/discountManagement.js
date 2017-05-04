/**
 * 确认折扣单
 */
pay.discountManagement.confirmDiscount = function(){
	//折扣单明细
	var discountDWindow = pay.discountManagement.discountDWindow;
	//折扣单明细列表
	var discountDGrid = discountDWindow.items.items[4];
	var me = this;
	Ext.MessageBox.show({
		title: pay.discountManagement.i18n('foss.stl.pay.common.alert'),//温馨提示
		msg: pay.discountManagement.i18n('foss.stl.pay.discountManagement.confirmDiscount'),//请确定是否确认折扣单？
		buttons: Ext.MessageBox.YESNO,
		fn: function(e){
		  	//如果本期数据为空，则提示不能确认折扣单
		  	if(pay.discountManagement.discountDWindow.down('grid').getStore().data.length==0){
		  		Ext.Msg.alert(pay.discountManagement.i18n('foss.stl.pay.common.alert'), 
		  			pay.discountManagement.i18n('foss.stl.pay.discountManagement.noData'));//没有折扣单明细不可以进行此操作
				pay.discountManagement.discountDWindow.close();
				return false;
		  	}
		  	
		  	if(e=='yes'){
		  		//拼接vo，注入到后台
				searchParams = {
					'discountManagementVo.discountManagementDto.discountNo':pay.discountManagement.discountNo,
                    'discountManagementVo.discountManagementDto.customerNo':pay.discountManagement.customerNo,
                    'discountManagementVo.discountManagementDto.orgCode':pay.discountManagement.discountOrgCode
				};
				var failureFn = function(json){
					Ext.Msg.alert(pay.discountManagement.i18n('foss.stl.pay.common.alert'),json.message);
					return false;
				}
				Ext.Ajax.request({
				    url: pay.realPath('confirmDiscount.action'),
				    form: Ext.fly('downloadAttachFileForm'),
				    method : 'POST',
				    params : searchParams,
				    isUpload: true,
				    success : function(response,options){
				    	var result = Ext.decode(response.responseText);
				    	Ext.Msg.alert(pay.discountManagement.i18n('foss.stl.pay.common.alert'),
							pay.discountManagement.i18n('foss.stl.pay.discountManagement.confirmSuccess'));//折扣单确认成功
							//获取grid
						var grid = Ext.getCmp('T_pay-discountManagement_content').getGrid();
						grid.getStore().loadPage(1);
						if(!result.success&&!result.isException){
							failureFn(result)
						}
				    },
				    exception : function(response,options){
				    	var result = Ext.decode(response.responseText);
				    	failureFn(result);
				    }
				});
		  	}else{
		  		return false;
		  	}
		}
	});
}

/**
 * 作废折扣单
 */
pay.discountManagement.deletediscount = function(){
	//折扣单明细
	var discountDWindow = pay.discountManagement.discountDWindow;
	//折扣单明细列表
	var discountDGrid = discountDWindow.items.items[4];
	var me = this;
	Ext.MessageBox.show({
		title: pay.discountManagement.i18n('foss.stl.pay.common.alert'),//温馨提示
		msg: pay.discountManagement.i18n('foss.stl.pay.discountManagement.deleteDiscount'),//请确定是否作废折扣单？
		buttons: Ext.MessageBox.YESNO,
		fn: function(e){
		  	//如果本期数据为空，则提示不能作废折扣单
		  	if(pay.discountManagement.discountDWindow.down('grid').getStore().data.length==0){
		  		Ext.Msg.alert(pay.discountManagement.i18n('foss.stl.pay.common.alert'), 
		  			pay.discountManagement.i18n('foss.stl.pay.discountManagement.noData'));//没有折扣单明细不可以进行此操作
				pay.discountManagement.discountDWindow.close();
				return false;
		  	}
		  	
		  	if(e=='yes'){
		  		//拼接vo，注入到后台
				searchParams = {
					'discountManagementVo.discountManagementDto.discountNo':pay.discountManagement.discountNo,//折扣单号
                    'discountManagementVo.discountManagementDto.customerNo':pay.discountManagement.customerNo,
                    'discountManagementVo.discountManagementDto.orgCode':pay.discountManagement.discountOrgCode
//					'discountManagementVo.discountManagementDto.waybillNo':
//					Ext.getCmp("Foss.discountManagement.discountDGrid").getStore().get(3).get('waybillNo')
				};
				var failureFn = function(json){
					Ext.Msg.alert(pay.discountManagement.i18n('foss.stl.pay.common.alert'),json.message);
					return false;
				}
				Ext.Ajax.request({
				    url: pay.realPath('discountDelete.action'),
				    form: Ext.fly('downloadAttachFileForm'),
				    method : 'POST',
				    params : searchParams,
				    isUpload: true,
				    success : function(response,options){
				    	var result = Ext.decode(response.responseText);
				    	Ext.Msg.alert(pay.discountManagement.i18n('foss.stl.pay.common.alert'),
							pay.discountManagement.i18n('foss.stl.pay.discountManagement.deleteSuccess'));//折扣单作废成功
							//获取grid
						var grid = Ext.getCmp('T_pay-discountManagement_content').getGrid();
						grid.getStore().loadPage(1);
				    	if(!result.success&&!result.isException){
							failureFn(result)
						}
				    },
				    exception : function(response,options){
				    	var result = Ext.decode(response.responseText);
				    	failureFn(result);
				    }
				});
		  	}else{
		  		return false;
		  	}
		}
	});
}
/**
 * 折扣单明细导出
 */
pay.discountManagement.exportExcel = function(){
	var	columns,
	arrayColumns,
	arrayColumnNames;
	//折扣单明细
	var discountDWindow = pay.discountManagement.discountDWindow;
	//折扣单明细列表
	var discountDGrid = discountDWindow.items.items[4];
	var me = this;
	Ext.MessageBox.show({
		title: pay.discountManagement.i18n('foss.stl.pay.common.alert'),//温馨提示
		msg: pay.discountManagement.i18n('foss.stl.pay.discountManagement.export'),//请确定是否导出折扣单？
		buttons: Ext.MessageBox.YESNO,
		fn: function(e){
		  	//如果本期数据为空，则提示不能导出excel
		  	if(pay.discountManagement.discountDWindow.down('grid').getStore().data.length==0){
		  		
		  		Ext.Msg.alert(pay.discountManagement.i18n('foss.stl.pay.common.alert'), 
		  			pay.discountManagement.i18n('foss.stl.pay.discountManagement.noData'));//没有折扣单明细不可以进行此操作
				pay.discountManagement.discountDWindow.close();
				return false;
		  	}
		  	
		  	if(e=='yes'){
		  		//转化列头和列明
				columns = pay.discountManagement.discountDWindow.down('grid').columns;
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
					'discountManagementVo.discountManagementDto.discountNo':pay.discountManagement.discountNo,
				    'discountManagementVo.arrayColumns':arrayColumns,
				    'discountManagementVo.arrayColumnNames':arrayColumnNames
				};
				Ext.Ajax.request({
				    url: pay.realPath('discountExportXLS.action'),
				    form: Ext.fly('downloadAttachFileForm'),
				    method : 'POST',
				    params : searchParams,
				    isUpload: true,
				    success : function(response,options){
				    	Ext.Msg.alert(pay.discountManagement.i18n('foss.stl.pay.common.alert'),
							pay.discountManagement.i18n('foss.stl.pay.discountManagement.exportSuccess'));//折扣单明细导出成功
				    }
				});
		  	}else{
		  		return false;
		  	}
		}
	});
}
//*************折扣单明细**************//
/**
 * 折扣单明细MODEL
 */
Ext.define('Foss.discountManagement.discountStatementDModel', {
	extend : 'Ext.data.Model',
	fields : [ {
		name : 'discuontNo',//折扣单号
		type : 'string'
	},{
		name : 'customerCode',//客户编码
		type : 'string'
	}, {
		name : 'customerName',//客户名称
		type : 'string'
	}, {
		name : 'dunningOrgCode',//催款部门编码
		type : 'string'
	}, {
		name : 'dunningOrgName',//催款部门名称
		type : 'string'
	}, {
		name : 'receivableId',//应收单ID
		type : 'string'
	}, {
		name : 'receivableNo',//应收单单号
		type : 'string'
	}, {
		name : 'receivableBusinessDate',//应收单业务日期
		type : 'string'
	}, {
		name : 'waybillNo',//运单号
		type : 'string'
	}, {
		name : 'productCode',//产品类型
		type : 'string'
	}, {
		name : 'preCodFee',//折前代收货款费用
		type : 'double'
	}, {
		name : 'preInsuranceFee',//折前代保价费用
		type : 'double'
	}, {
		name : 'preTransportFee',//折前运费
		type : 'double'
	}, {
		name : 'codDiscount',//代收货款折扣
		type : 'string'
	}, {
		name : 'insuranceDiscount',//保价折扣
		type : 'string'
	}, {
		name : 'transportDiscount',//运费折扣
		type : 'string'
	}, {
		name : 'receiveBillType',//应收单单据子类型
		type : 'string'
	}, {
		name : 'active',//是否有效
		type : 'string'
	}, {
		name : 'transport',//运输性质
		type : 'string'
	}, {
		name : 'totalMoney',//总金额
		type : 'double'
	}, {
		name : 'payAbleNo',//应付单编号
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
		name : 'isUnifiedAccount',//是否统一结算
		type : 'string'
	},{
		name : 'discountType',//折扣单类型
		type : 'string'
	}]
});

/**
 * 折扣单明细STORE
 */
Ext.define('Foss.discountManagement.discountStatementDStore',{
	extend:'Ext.data.Store',
	pageSize:50,
	model:'Foss.discountManagement.discountStatementDModel',
	proxy:{
		type:'ajax',
		url:pay.realPath('queryDiscountDEntity.action'),
		actionMethods:'POST',
		reader:{
			type:'json',
			root:'discountManagementVo.discountManagementDto.discountManagementDList',
			totalProperty:'totalCount'
		}
	},
	listeners:{
		'beforeLoad':function(store, operation, eOpts){
			var searchParams = {
				'discountManagementVo.discountManagementDto.discountNo' : pay.discountManagement.discountNo
			}
			//设置查询条件
			Ext.apply(operation,{
				params :searchParams
			});
		}
	}
});
/**
 * 折扣单明细GRID
 */
Ext.define('Foss.discountManagement.discountDGrid',{
	extend:'Ext.grid.Panel',
	title: pay.discountManagement.i18n('foss.stl.pay.discountManagement.statementMessage'),//折扣单明细
    bodyCls: 'autoHeight',
	cls: 'autoHeight',
	emptyText:pay.discountManagement.i18n('foss.stl.pay.common.noResult'),//查询结果为空！
	frame:true,
	detailWin:null,
    store:Ext.create('Foss.discountManagement.discountStatementDStore'),
    height:500,
    width:stl.SCREENWIDTH*0.877,
  	viewConfig:{
  		enableTextSelection : true//设置行可以选择，进而可以复制
  	},
  	columns: [{	header:pay.discountManagement.i18n('foss.stl.pay.discountManagement.discountOrgCode'),//收入部门
		dataIndex:'dunningOrgName',
		align:'center',
		width:120
  	},{	header:pay.discountManagement.i18n('foss.stl.pay.discountManagement.dunningOrgName'),//催款部门
		dataIndex:'dunningOrgName',
		align:'center',
		width:120
  	},{	header:pay.discountManagement.i18n('foss.stl.pay.discountManagement.receivableBusinessDate'),//单据业务期间
		dataIndex:'receivableBusinessDate',
		align:'center',
		renderer : function(value){
			var date = new Date(Number(value));
			return Ext.Date.format(date,'Y-m-d');
		},
		width:120
  	},{
		header:pay.discountManagement.i18n('foss.stl.pay.discountManagement.transportProperties'),//运输性质
		dataIndex:'productCode',
		align:'center',
		renderer : function(value) {
			return Foss.pkp.ProductData.rendererSubmitToDisplay(value);
			},
		width:120
	},{
		header:pay.discountManagement.i18n('foss.stl.pay.discountManagement.transportNumber'),//运单号
		dataIndex:'waybillNo',
		align:'center',
		width:120
	},{
		header:pay.discountManagement.i18n('foss.stl.pay.discountManagement.payAbleNo'),//应付单单号
		dataIndex:'payAbleNo',
		align:'center',
		width:120
	},{
		header:pay.discountManagement.i18n('foss.stl.pay.discountManagement.customerName'),//客户名称
		dataIndex:'customerName',
		align:'center',
		width:120
	},{
		header:pay.discountManagement.i18n('foss.stl.pay.discountManagement.customerNo'),//客户编码
		dataIndex:'customerCode',
		align:'center',
		width:120
	},{
		header:pay.discountManagement.i18n('foss.stl.pay.discountManagement.totalMoney'),//总金额
		dataIndex:'totalMoney',
		align:'center',
		width:120
	},{
		header:pay.discountManagement.i18n('foss.stl.pay.discountManagement.transportDiscount'),//纯运费折扣
		dataIndex:'transportDiscount',
		align:'center',
		width:120
	},{
		header:pay.discountManagement.i18n('foss.stl.pay.discountManagement.codDiscount'),//代收手续费折扣
		dataIndex:'codDiscount',
		align:'center',
		width:120
	},{
		header:pay.discountManagement.i18n('foss.stl.pay.discountManagement.insuranceDiscount'),//保价手续费折扣
		dataIndex:'insuranceDiscount',
		align:'center',
		width:120
	},{
		header:pay.discountManagement.i18n('foss.stl.pay.discountManagement.contractOrgName'),//合同部门
		dataIndex:'contractOrgName',
		align:'center',
		width:120
	},{
		header:pay.discountManagement.i18n('foss.stl.pay.discountManagement.contractOrgCode'),//合同部门编码
		dataIndex:'contractOrgCode',
		align:'center',
		width:120
	},{
		header:pay.discountManagement.i18n('foss.stl.pay.discountManagement.invoiceMark'),//发票标记
		dataIndex:'invoiceMark',
		align:'center',
		width:120,
		renderer : function(value) {
		var displayField = FossDataDictionary.rendererSubmitToDisplay(value, 'SETTLEMENT_INVOICE_MARK');//发票标记转化
		return displayField;
	}
	},{
		header:pay.discountManagement.i18n('foss.stl.pay.discountManagement.isUnifiedAccount'),//是否统一结算
		dataIndex:'isUnifiedAccount',
		align:'center',
		renderer : function(value){
			if(value=='Y'){
			  return '是';
			} else if(value=='N'){
			  return '否';
			}
		},
		width:120
	}],
	initComponent:function(){
		var me = this;
		me.dockedItems = [{
	   		xtype: 'toolbar',
		    dock: 'bottom',
		    layout:'column',
/*		    defaults:{
				margin:'5 0 5 3',
				readOnly:true,
				labelWidth:60
				
			},	*/	
		    items: [{
		    	xtype:'standardpaging',
				store:me.store,
				name : 'pay_discountManagement_pageSizePluginName',
				pageSize:50,
				columnWidth:.95,
				plugins: Ext.create('Deppon.ux.PageSizePlugin', {
					//设置分页记录最大值，防止输入过大的数值
					maximumSize: 1000,
					sizeList: [['10',10],['25',25],['50',50],['100',100],['200', 200],['500', 500],['1000', 1000]]
				})
	 },{
		xtype : 'button',
//		text :'确认折扣单',
		text : pay.discountManagement.i18n('foss.stl.pay.discountManagement.confirmDiscount'),//确认折扣单
		width:90,
		disabled:!pay.discountManagement.isPermission('/stl-web/pay/confirmDiscount.action'),
		hidden:!pay.discountManagement.isPermission('/stl-web/pay/confirmDiscount.action'),
		handler : pay.discountManagement.confirmDiscount
	},{	
		xtype : 'button',
//		text:'作废折扣单',
		text : pay.discountManagement.i18n('foss.stl.pay.discountManagement.deleteDiscount'),//作废折扣单
		width:90,
		disabled:!pay.discountManagement.isPermission('/stl-web/pay/discountDelete.action'),
		hidden:!pay.discountManagement.isPermission('/stl-web/pay/discountDelete.action'),
		handler : pay.discountManagement.deletediscount
	},{ 
		xtype : 'button',
//		text:'导出',
		text : pay.discountManagement.i18n('foss.stl.pay.discountManagement.exportExcel'),//导出
		width:90,
		handler : pay.discountManagement.exportExcel
	}]
		}];
		me.callParent();
	}
});
Ext.define('Foss.discountManagement.discountDetailPanel',{
	width:stl.SCREENWIDTH*0.9,
	//height :500,
	id:'discountDetailPanel',
	extend:'Ext.panel.Panel',
	layout:'column',
	items:[Ext.create('Foss.discountManagement.discountDGrid')]

})
/**
 * 折扣单明细窗体
 */
Ext.define('Foss.discountManagement.discountDWindow',{
	extend:'Ext.window.Window',
	//extend:'Ext.panel.Panel',
//	name : 'discountDWindow',
	
	width:stl.SCREENWIDTH*0.9,
	modal:true,
	
	constrainHeader: true,
	closeAction:'destory',
	items:[
		   Ext.create('Foss.discountManagement.discountDGrid')
	]
	/*buttons :['->',{
		 
		xtype : 'button',
		text :'确认折扣单',
		columnWidth : .09,
		handler : pay.discountManagement.confirmDiscount
									
//		text:'确认折扣单',
//		handler : pay.discountManagement.confirmDiscount
	},{
		text:'作废折扣单',
		handler : pay.discountManagement.deletediscount
	},{
		text:'导出',
		handler : pay.discountManagement.exportExcel
	}]*/
});



//***************折扣单***************//
/**
 * 折扣单MODEL
 */
Ext.define('Foss.discountManagement.GridModel', {
	extend : 'Ext.data.Model',
	fields : [ {
		name : 'id',//主键ID
		type : 'string'
	},{
		name : 'discountNo',//折扣单号
		type : 'string'
	},{
		name : 'customerNo',//客户编码
		type : 'string'
	},{
		name : 'customerName',//客户名称
		type : 'string'
	},{
		name : 'discountOrgCode',//折扣部门
		type : 'string'
	},{
		name : 'discountOrgName',//折扣名称名称(收入部门)
		type : 'string'
	},{
		name : 'codDiscount',//代收货款折扣
		type : 'double'
	},{
		name : 'insuranceDiscount',//保价费折扣(保价手续费折扣)
		type : 'double'
	},{
		name : 'transportDiscount',//运费折扣(纯运费折扣)
		type : 'double'
	},{
		name : 'codDiscountRate',//代收货款折扣率
		type : 'double'
	},{
		name : 'insuranceRate',//保价费折扣率
		type : 'double'
	},{
		name : 'transportRate',//运费折扣率
		type : 'double'
	},{
		name : 'notes',//备注
		type : 'string'
	},{
		name : 'modifyTime',//最后修改时间
		type : 'string'
	},{
		name : 'createTime',//创建时间
		type : 'string'
	},{
		name : 'totalMoney',//总金额
		type : 'double'
	},{
		name : 'status',//单据状态
		type : 'string'
	}]
});
/**
 * 折扣单STORE
 */
Ext.define('Foss.discountManagement.GridStore',{
	extend:'Ext.data.Store',
	model:'Foss.discountManagement.GridModel',
	pageSize:50,
	proxy:{
		type:'ajax',
		url:pay.realPath('queryDiscountByCust.action'),
		actionMethods:'POST',
		reader:{
			type:'json',
			root:'discountManagementVo.discountManagementDto.discountManagementList',
			totalProperty:'totalCount'
		}
	},
	listeners:{
		'beforeLoad':function(store, operation, eOpts){
			var searchParams;
			if(pay.discountManagement.queryTabType==pay.STATEMENTQUERYTAB_BYCUSTOMER){
				searchParams = {
					'discountManagementVo.discountManagementDto.periodBeginDate':pay.discountManagement.periodBeginDate,
			 		'discountManagementVo.discountManagementDto.periodEndDate':pay.discountManagement.periodEndDate,
			 		'discountManagementVo.discountManagementDto.customerNo':pay.discountManagement.customerNo
				};
			}else{
				var numbers = stl.splitToArray(pay.discountManagement.numbers);
				searchParams = {
					'discountManagementVo.discountManagementDto.discountNo':numbers
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
 * 折扣单GRID
 */
Ext.define('Foss.discountManagement.Grid',{
	extend:'Ext.grid.Panel',
	title: pay.discountManagement.i18n('foss.stl.pay.discountManagement.discount'),//折扣单
    bodyCls: 'autoHeight',
	cls: 'autoHeight',
	emptyText:pay.discountManagement.i18n('foss.stl.pay.common.noResult'),//查询结果为空！
	frame:true,
	detailWin:null,
    store:Ext.create('Foss.discountManagement.GridStore'),
    selModel:Ext.create('Ext.selection.CheckboxModel'),
    height:500,
  	viewConfig:{
  		enableTextSelection : true//设置行可以选择，进而可以复制
  	},
   listeners : {
		'itemdblclick' : function(th, record) {
			//创建明细窗口
			if(Ext.isEmpty(pay.discountManagement.discountDWindow)){
				pay.discountManagement.discountDWindow = Ext.create('Foss.discountManagement.discountDWindow',{name : 'discountManagement.discountDWindow'});
			}
//			var store = Ext.ComponentQuery.query('window[name=discountManagement.discountDWindow]')[0].down('grid').getStore();
			var store = pay.discountManagement.discountDWindow.down('grid').getStore();
			//根据折扣单单号查询出明细数据
//			selectDiscountD(record,store,window);
			pay.discountManagement.discountNo = record.get('discountNo');
            pay.discountManagement.customerNo =  record.get('customerNo');
            pay.discountManagement.discountOrgCode = record.get('discountOrgCode');
			store.loadPage(1);
			pay.discountManagement.discountDWindow.show();
		}
	},
  	columns: [{
		header:pay.discountManagement.i18n('foss.stl.pay.common.actionColumn'),//操作列
		xtype:'actioncolumn',
		align:'center',
		scope : this,  
		iconCls: 'deppon_icons_showdetail',
		tooltip: pay.discountManagement.i18n('foss.stl.pay.discountManagement.QueryInfoGridTooltip'),
		width:60,
		handler: function (grid, rowIndex, colIndex, node, e, record, rowEl) {
//				var selection = grid.getSelectionModel().getSelection()[0];
				var selection = grid.getStore().getAt(rowIndex);//获取数据
//				var store = Ext.ComponentQuery.query('window[name=discountManagement.discountDWindow]')[0].down('grid').getStore();
	        	var store = pay.discountManagement.discountDWindow.down('grid').getStore();
				
				//创建明细窗口
				if(Ext.isEmpty(pay.discountManagement.discountDWindow)){
					pay.discountManagement.discountDWindow = Ext.create('Foss.discountManagement.discountDWindow',{name : 'discountManagement.discountDWindow'});
				}
				pay.discountManagement.discountNo = selection.get('discountNo');
                pay.discountManagement.customerNo = selection.get('customerNo');
                pay.discountManagement.discountOrgCode = selection.get('discountOrgCode');
                pay.discountManagement.receivableBusinessDate = record.get('receivableBusinessDate');
				store.loadPage(1);
//	        	selectDiscountD(selection,store);
	        	pay.discountManagement.discountDWindow.show();
	        	
	       }
	},{
		header:pay.discountManagement.i18n('foss.stl.pay.discountManagement.discountNo'),//折扣单号
		dataIndex:'discountNo',
		align:'center',
		width:130
	},{
        header:pay.discountManagement.i18n('foss.stl.pay.discountManagement.discountOrgCode'),//收入部门编码（折扣部门）
        dataIndex:'discountOrgCode',
        align:'center',
        width:130,
        hidden:true
    },{
		header:pay.discountManagement.i18n('foss.stl.pay.discountManagement.discountOrgName'),//收入部门（折扣部门）
		dataIndex:'discountOrgName',
		align:'center',
		width:130
	},{
		header:pay.discountManagement.i18n('foss.stl.pay.discountManagement.customerName'),//客户名称
		dataIndex:'customerName',
		align:'center',
		width:130
	},{
		header:pay.discountManagement.i18n('foss.stl.pay.discountManagement.customerNo'),//客户编码
		dataIndex:'customerNo',
		align:'center',
		width:130
	},{
		header:pay.discountManagement.i18n('foss.stl.pay.discountManagement.createTime'),//创建时间
		dataIndex:'createTime',
		align:'center',
		renderer : function(value){
			var date = new Date(Number(value));
			return Ext.Date.format(date,'Y-m-d');
		},
		width:130
	},{
		header:pay.discountManagement.i18n('foss.stl.pay.discountManagement.status'),//单据状态
		dataIndex:'status',
		align:'center',
		renderer : function(value){
			if(value==null){
			return null;
			}
			else if(value=='N'){
				return pay.discountManagement.i18n('foss.stl.pay.discountManagement.Unrecognized')//未确认;
			}
			else if(value=='C'){
				return pay.discountManagement.i18n('foss.stl.pay.discountManagement.Recognized')//已确认;
			}
			else if(value=='D'){
				return pay.discountManagement.i18n('foss.stl.pay.discountManagement.Tovoid')//已作废;;
			}
		},
		width:130
	},{
		header:pay.discountManagement.i18n('foss.stl.pay.discountManagement.totalMoney'),//总金额
		dataIndex:'totalMoney',
		align:'center',
		width:130
	},{
		header:pay.discountManagement.i18n('foss.stl.pay.discountManagement.transportDiscount'),//纯运费折扣
		dataIndex:'transportDiscount',
		align:'center',
		width:130
	},{
		header:pay.discountManagement.i18n('foss.stl.pay.discountManagement.codDiscount'),//代收货款折扣
		dataIndex:'codDiscount',
		align:'center',
		width:130
	},{
		header:pay.discountManagement.i18n('foss.stl.pay.discountManagement.insuranceDiscount'),//保价费折扣
		dataIndex:'insuranceDiscount',
		align:'center',
		width:130
	},{
		header:pay.discountManagement.i18n('foss.stl.pay.discountManagement.insuranceRate'),//保价费折扣率
		dataIndex:'insuranceRate',
		align:'center',
		renderer : function(value){
			if(value!=null){
			return value+'%';//返回值+%
			}
			else{
				return value;
			}
		},
		width:130
	},{
		header:pay.discountManagement.i18n('foss.stl.pay.discountManagement.codDiscountRate'),//代收货款折扣率
		dataIndex:'codDiscountRate',
		align:'center',
		renderer : function(value){
			if(value!=null){
			return value+'%';//返回值+%
			}
			else{
				return value;
			}
		},
		width:130
	},{
		header:pay.discountManagement.i18n('foss.stl.pay.discountManagement.transportRate'),//运费折扣率
		dataIndex:'transportRate',
		align:'center',
		renderer : function(value){
			if(value!=null){
			return value+'%';//返回值+%
			}
			else{
				return value;
			}
		},
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
				name : 'pay_discountManagement_pageSizePluginName',
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
pay.discountManagement.statementSelectByCust = function(){
	var form = this.up('form').getForm();
	//判断是否合法
	if(form.isValid()){
		//开始时间
		pay.discountManagement.periodBeginDate = form.findField('periodBeginDate').getValue();
		//结束时间
		pay.discountManagement.periodEndDate = form.findField('periodEndDate').getValue();
		//客户编码
		pay.discountManagement.customerNo = form.findField('customerNo').getValue();
		//开始日期
		var periodBeginDate = pay.discountManagement.periodBeginDate;
		//结束日期
		var periodEndDate = pay.discountManagement.periodEndDate;
		//查询类型
		pay.discountManagement.queryTabType = pay.STATEMENTQUERYTAB_BYCUSTOMER;
		//校验日期
		if(Ext.isEmpty(periodBeginDate)||Ext.isEmpty(periodEndDate)){
			Ext.Msg.alert(pay.discountManagement.i18n('foss.stl.pay.common.alert'),//温馨提示
				pay.discountManagement.i18n('foss.stl.pay.discountManagement.quryDateIsNullWarning'));//起始日期或结束日期间为空，不能进行查询!
			return false;
		}
		//比较起始日期和结束日期
		var compareTwoDate = stl.compareTwoDate(periodBeginDate,periodEndDate);
		if(compareTwoDate>stl.DATELIMITDAYS_MONTH){
			Ext.Msg.alert(pay.discountManagement.i18n('foss.stl.pay.common.alert'),//温馨提示
				pay.discountManagement.i18n('foss.stl.pay.discountManagement.queryDateMaxLimit'));//起始日期和结束日期间隔不能超过31天!
			return false;
		}else if(compareTwoDate<1){
			Ext.Msg.alert(pay.discountManagement.i18n('foss.stl.pay.common.alert'),//温馨提示
				pay.discountManagement.i18n('foss.stl.pay.common.dateEndBeforeStatrtWarining'));//结束日期不能早于起始日期!
			return false;
		}
		//获取grid
		var grid = Ext.getCmp('T_pay-discountManagement_content').getGrid();
		grid.getStore().loadPage(1);
		//根据分页条数以及查询条件查询
//		selectCustomer(form,Ext.ComponentQuery.query('standardpaging[name=pay_discountManagement_pageSizePluginName]')[0].pageSize,grid.getStore());
		
	}else{
		Ext.Msg.alert(pay.discountManagement.i18n('foss.stl.pay.common.alert'),//温馨提示
			pay.discountManagement.i18n('foss.stl.pay.common.validateFailAlert'));//请检查输入条件是否合法!
		return false;
	}
}

/**
 * 按单号查询折扣单
 */
pay.discountManagement.statementSelectByNumber = function(){		
	var form  = this.up('form').getForm();	
	//输入单号集合
	var numbers = form.findField('numbers').getValue();
	//查询类型
	pay.discountManagement.queryTabType = pay.STATEMENTQUERYTAB_BYNUMBER;
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
		 	Ext.Msg.alert(pay.discountManagement.i18n('foss.stl.pay.common.alert'),//温馨提示
		 		pay.discountManagement.i18n('foss.stl.pay.common.queryNosLimit'));//输入单号不能超过10个!
		 	return false;
		 }
	}else{
		Ext.Msg.alert(pay.discountManagement.i18n('foss.stl.pay.common.alert'),//温馨提示
			pay.discountManagement.i18n('foss.stl.pay.discountManagement.billNosIsNullWarning'));//输入单号不能为空！
		return false;
	}
	//当界面校验都通过后，才执行查询方法
	if(form.isValid()){
		pay.discountManagement.numbers = numbers;
		var grid = Ext.getCmp('T_pay-discountManagement_content').getGrid();
		grid.getStore().loadPage(1/*,{
			callback: function(records, operation, success) {
				var rawData = grid.store.proxy.reader.rawData;
				if(!success && ! rawData.isException){
					var result = Ext.decode(operation.response.responseText);	
					Ext.Msg.alert(pay.discountManagement.i18n('foss.stl.pay.common.alert'),result.message);
				}
				if(success){
					//对结果进行转化
					var result = Ext.decode(operation.response.responseText);  
					//判断查询结果
					if(Ext.isEmpty(result.discountManagementVo.discountManagementDto.discountManagementList) 
							||result.discountManagementVo.discountManagementDto.discountManagementList.length==0){
						Ext.Msg.alert(writeoff.woodenStatementEdit.i18n('foss.stl.pay.common.alert'),
							writeoff.woodenStatementEdit.i18n('foss.stl.pay.discountManagement.noResult'));
						return false;
					}
				}
		    }
		}*/);
//		根据分页条数以及查询条件查询
//		selectNumber(form,Ext.ComponentQuery.query('standardpaging[name=pay_discountManagement_pageSizePluginName]')[0].pageSize,grid.getStore());
		
	}else{
		Ext.Msg.alert(pay.discountManagement.i18n('foss.stl.pay.common.alert'),//温馨提示
			pay.discountManagement.i18n('foss.stl.pay.common.validateFailAlert'));//请检查输入条件是否合法!
		return false;
	}	
};

/**
 * 折扣单查询页签
 */
Ext.define('Foss.discountManagement.QueryTab',{
	extend:'Ext.tab.Panel',
	frame:false,
	bodyCls: 'autoHeight',
	cls: 'innerTabPanel',
	height:160,
	items:[{
       	title: pay.discountManagement.i18n('foss.stl.pay.discountManagement.queryByCustomer'),//按客户查询
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
		    	fieldLabel:pay.discountManagement.i18n('foss.stl.pay.discountManagement.periodBeginDate'),//开始时间
		    	allowBlank:false,
		    	name:'periodBeginDate',
		    	columnWidth: .3,
		    	format:'Y-m-d',
		    	value:stl.getTargetDate(new Date(),-1)
		    },{
		    	xtype:'datefield',
		    	fieldLabel:pay.discountManagement.i18n('foss.stl.pay.discountManagement.periodEndDate'),//结束时间
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
		    	fieldLabel:pay.discountManagement.i18n('foss.stl.pay.discountManagement.customerMessage'),//客户信息
		    	columnWidth:.3
		    },{
				border: 1,
				xtype:'container',
				columnWidth:1,
				defaultType:'button',
				layout:'column',
				items:[{
					text:pay.discountManagement.i18n('foss.stl.pay.discountManagement.reset'),//重置
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
					text:pay.discountManagement.i18n('foss.stl.pay.discountManagement.query'),//查询
					cls:'yellow_button',
					columnWidth:.08,
					handler:pay.discountManagement.statementSelectByCust
				}]
		    }]
	    }]
	},{
        title: pay.discountManagement.i18n('foss.stl.pay.discountManagement.queryByNumber'),//按单号查询
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
				fieldLabel:pay.discountManagement.i18n('foss.stl.pay.discountManagement.number'),
				allowBlank:false,
				columnWidth:.65,
				regex:/^((ZK)?[0-9]{7,14}[;,])*((ZK)?[0-9]{7,14}[;,]?)$/i,//329757-放开运单号是14位的校验
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
						html:'<span style="color:red;">'+pay.discountManagement.i18n('foss.stl.pay.discountManagement.queryDiscountNo')+'</span>'
					}//请输入折扣单单号
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
					text:pay.discountManagement.i18n('foss.stl.pay.discountManagement.reset'),//重置
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
					text:pay.discountManagement.i18n('foss.stl.pay.discountManagement.query'),//查询
					cls:'yellow_button',
					columnWidth:.075,
					handler:pay.discountManagement.statementSelectByNumber
				}]
        	}]
        }]
    }]
});
////**************请求**************//
////按客户查询：根据查询条件，分页数目 查询出所需数据   form为查询所用form store为需要向其中加载数据的store   
//function selectCustomer(form,pageNum,store){
//	//向后台请求数据
//	Ext.Ajax.request({
//	 	url : pay.realPath('queryDiscountByCust.action'),
//	 	params :{
//	 		'discountManagementVo.discountManagementDto.periodBeginDate':form.findField('periodBeginDate').getValue(),
//	 		'discountManagementVo.discountManagementDto.periodEndDate':form.findField('periodEndDate').getValue(),
//	 		'discountManagementVo.discountManagementDto.customerNo':form.findField('customerNo').getValue(),
//	 		'discountManagementVo.discountManagementDto.pageNum':pageNum
//	 	},
//		actionMethods:'post',
//		submitEmptyText:false,
//		success:function(response){
//			var json=Ext.decode(response.responseText);
//			//后台返回给前台的list对象
//			store.loadData(json.discountManagementVo.discountManagementDto.discountManagementList);
//		},
//		exception:function(response){
//			var json=Ext.decode(response.responseText);
//			Ext.MessageBox.alert('提示, 服务器异常; 请稍后再试');
//		}
//	 });
//	//加载到对应grid的store中
//}

////按单号查询：根据查询条件，分页数目 查询出所需数据   form为查询所用form store为需要向其中加载数据的store   
//function selectNumber(form,pageNum,store){
//	//向后台请求数据
//	Ext.Ajax.request({
//	 	url : pay.realPath('queryDiscountByNumber.action'),
//	 	params :{
//	 		'discountManagementVo.discountManagementDto.numbers':form.findField('numbers').getValue(),
//	 		'discountManagementVo.discountManagementDto.pageNum':pageNum
//	 	},
//		actionMethods:'post',
//		submitEmptyText:false,
//		success:function(response){
//			var json=Ext.decode(response.responseText);
//			//后台返回给前台的list对象
//			store.loadData(json.discountManagementVo.discountManagementDto.discountManagementList);
//		},
//		exception:function(response){
//			var json=Ext.decode(response.responseText);
//			Ext.MessageBox.alert('提示, 服务器异常; 请稍后再试！');
//		}
//	 });
//	//加载到对应grid的store中
//}

//查询折扣单明细数据
//function selectDiscountD(record,store){
//	//向后台请求数据
//	Ext.Ajax.request({
//	 	url : pay.realPath('queryDiscountDEntity.action'),
//	 	params :{
//	 		'discountManagementVo.discountManagementDto.discountNo':record.get('discountNo')
//	 	},
//		actionMethods:'post',
//		submitEmptyText:false,
//		success:function(response){
//			var json=Ext.decode(response.responseText);
//			//后台返回给前台的list对象
//			store.loadData(json.discountManagementVo.discountManagementDto.discountManagementDList);
//			
//		},
//		exception:function(response){
//			var json=Ext.decode(response.responseText);
//			Ext.MessageBox.alert('提示, 服务器异常; 请稍后再试');
//		}
//	 });
//	//加载到对应grid的store中
//}
/**
 * 折扣单主程序入口
 */
Ext.onReady(function() {
	Ext.Ajax.timeout = 60000*30;
	//查询表单
	var queryTab = Ext.create('Foss.discountManagement.QueryTab');
	//查询结果列表
	var grid = Ext.create('Foss.discountManagement.Grid');
	//创建明细窗口
	pay.discountManagement.discountDWindow = null;
	if(Ext.isEmpty(pay.discountManagement.discountDWindow)){
		pay.discountManagement.discountDWindow = Ext.create('Foss.discountManagement.discountDWindow',{name : 'discountManagement.discountDWindow'});
	}
	//创建panel
	Ext.create('Ext.panel.Panel',{
		id: 'T_pay-discountManagement_content',
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
		renderTo: 'T_pay-discountManagement-body'
	});
});