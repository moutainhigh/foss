//生成对账单
writeoff.homeStatementAdd.statementSave = function(){
	var	homeStatementAddGrid;
	homeStatementAddGrid = Ext.getCmp('T_writeoff-homeStatementAdd_content').getGrid();
	var me = this;
	Ext.MessageBox.show({
		title: writeoff.homeStatementAdd.i18n('foss.stl.writeoff.common.alert'),
		msg: writeoff.homeStatementAdd.i18n('foss.stl.writeoff.homeStatementAdd.statementSave'),//
		buttons: Ext.MessageBox.YESNO,
		fn: function(e){
		  	//如果本期数据为空，则提示不能导出excel
		  	if(homeStatementAddGrid.store.data.length==0){
		  		Ext.Msg.alert(writeoff.homeStatementAdd.i18n('foss.stl.writeoff.common.alert'), 
		  			writeoff.homeStatementAdd.i18n('foss.stl.writeoff.statementCommon.noDataToExport'));
		  		return false;
		  	}
		  	if(e=='yes'){
				//判断是按那种查询进行
				var searchParams;
				if(writeoff.homeStatementAdd.queryTabType==writeoff.STATEMENTQUERYTAB_BYCUSTOMER){
					if(Ext.isEmpty(writeoff.homeStatementAdd.customerCode)){
						Ext.Msg.alert(writeoff.homeStatementAdd.i18n('foss.stl.writeoff.common.alert'),
							writeoff.homeStatementAdd.i18n('foss.stl.writeoff.common.validateFailAlertByNoCustomer'));
						return false;
					}
					searchParams = {
							'homeStatementVo.homeStatementDto.periodBeginDate':writeoff.homeStatementAdd.periodBeginDate,	
							'homeStatementVo.homeStatementDto.periodEndDate':writeoff.homeStatementAdd.periodEndDate,	
							'homeStatementVo.homeStatementDto.customerCode':writeoff.homeStatementAdd.customerCode,
							'homeStatementVo.homeStatementDto.queryTabType':writeoff.homeStatementAdd.queryTabType,
							'homeStatementVo.homeStatementDto.subCompanyCode':writeoff.homeStatementAdd.subCompanyCode,
							'homeStatementVo.homeStatementDto.billTypes':writeoff.homeStatementAdd.billTypes
						};
				}else{
					var numbers = stl.splitToArray(writeoff.homeStatementAdd.numbers);
					searchParams = {
						'homeStatementVo.homeStatementDto.numbers':numbers,
						'homeStatementVo.homeStatementDto.queryTabType':writeoff.homeStatementAdd.queryTabType
					};
				}
				
				//拼接vo，注入到后台
				Ext.Ajax.request({
				    url: writeoff.realPath('homeStatementSave.action'),
				    form: Ext.fly('downloadAttachFileForm'),
				    method : 'POST',
				    params : searchParams,
				    isUpload : true,
				    success : function(response,options){
				    	var result = Ext.decode(response.responseText);
				    	var statementBillNo = result.homeStatementVo.homeStatementDto.statementBillNo;
				    	Ext.Msg.alert(writeoff.homeStatementAdd.i18n('foss.stl.writeoff.common.alert'),
							writeoff.homeStatementAdd.i18n('foss.stl.writeoff.homeStatementAdd.exportSuccess')
							+ statementBillNo);
				    	//清空列表
				    	homeStatementAddGrid.store.removeAll();
				    },
					exception:function(response){
						var result = Ext.decode(response.responseText);	
						Ext.Msg.alert(writeoff.homeStatementAdd.i18n('foss.stl.writeoff.common.alert'),result.message);
					}
				});
		  	}else{
		  		return false;
		  	}
		}
	});
}

/**
 * Store的model
 */
Ext.define('Foss.homeStatementAdd.GridModel', {
	extend : 'Ext.data.Model',
	fields : [ {
		name : 'payableNo'
	},{
		name : 'businessDate'
	}, {
		name : 'waybillNo'
	}, {
		name : 'orgName'
	}, {
		name : 'orgCode'
	}, {
		name : 'subCompanyName'
	}, {
		name : 'subCompanyCode'
	}, {
		name : 'billType'
	}, {
		name : 'homeSupplyName'
	}, {
		name : 'homeSupplyCode'
	}, {
		name : 'amount',
		type : 'double'
	}, {
		name : 'verifyAmount',
		type : 'double'
	}, {
		name : 'unverifyAmount',
		type : 'double'
	}, {
		name : 'notes'
	}]
});

//按客户查询
writeoff.homeStatementAdd.statementSelectByCust = function(){
	var form = this.up('form').getForm();
	//判断是否合法
	if(form.isValid()){
		writeoff.homeStatementAdd.periodBeginDate = form.findField('periodBeginDate').getValue();
		writeoff.homeStatementAdd.periodEndDate = form.findField('periodEndDate').getValue();
		writeoff.homeStatementAdd.customerCode = form.findField('customerCode').getValue();
		writeoff.homeStatementAdd.subCompanyCode = form.findField('subCompanyCode').getValue();
		writeoff.homeStatementAdd.billTypes = form.getFieldValues().billType;
		writeoff.homeStatementAdd.queryTabType = writeoff.STATEMENTQUERYTAB_BYCUSTOMER;
		
		var grid = Ext.getCmp('T_writeoff-homeStatementAdd_content').getGrid();
		
		//判断客户信息是否为空
		if(Ext.isEmpty(writeoff.homeStatementAdd.customerCode)){
			Ext.Msg.alert(writeoff.homeStatementAdd.i18n('foss.stl.writeoff.common.alert'),
				writeoff.homeStatementAdd.i18n('foss.stl.writeoff.homeStatementAdd.customerIsNullWarning'));
		 	return false;
		}
		
		//判断所属子公司是否为空
		if(Ext.isEmpty(writeoff.homeStatementAdd.subCompanyCode)){
			Ext.Msg.alert(writeoff.homeStatementAdd.i18n('foss.stl.writeoff.common.alert'),
				writeoff.homeStatementAdd.i18n('foss.stl.writeoff.homeStatementAdd.subCompanyIsNullWarning'));
		 	return false;
		}
		//开始日期
		var periodBeginDate = writeoff.homeStatementAdd.periodBeginDate;
		//结束日期
		var periodEndDate = writeoff.homeStatementAdd.periodEndDate;
		//校验日期
		if(Ext.isEmpty(periodBeginDate)||Ext.isEmpty(periodEndDate)){
			 Ext.Msg.alert(writeoff.homeStatementAdd.i18n('foss.stl.writeoff.common.alert'),
				writeoff.homeStatementAdd.i18n('foss.stl.writeoff.statementAdd.quryDateIsNullWarning'));
			return false;
		}
		
		//比较起始日期和结束日期
		var compareTwoDate = stl.compareTwoDate(periodBeginDate,periodEndDate);
		if(compareTwoDate>stl.DATELIMITDAYS_MONTH){
			Ext.Msg.alert(writeoff.homeStatementAdd.i18n('foss.stl.writeoff.common.alert'),
				writeoff.homeStatementAdd.i18n('foss.stl.writeoff.homestatementAdd.queryDateMaxLimit'));
			//Ext.Msg.alert('温馨提示','起始日期和结束日期间隔不能超过31天!');
			return false;
		}else if(compareTwoDate<1){
			 Ext.Msg.alert(writeoff.homeStatementAdd.i18n('foss.stl.writeoff.common.alert'),
				writeoff.homeStatementAdd.i18n('foss.stl.writeoff.common.dateEndBeforeStatrtWarining'));
			//Ext.Msg.alert('温馨提示','结束日期不能早于起始日期!');
			return false;
		}
		
		//获取单据子类型
		var selectBillTypes = form.getFieldValues().billType;
		if(selectBillTypes[0]==false && selectBillTypes[1]==false){
			Ext.Msg.alert(writeoff.homeStatementAdd.i18n('foss.stl.writeoff.common.alert'),writeoff.homeStatementAdd.i18n('foss.stl.writeoff.statementAdd.noSelectBillTypeWarning'));
			return false;
		}
		
		//获取grid
		grid.store.loadPage(1,{
			callback: function(records, operation, success) {
				var rawData = grid.store.proxy.reader.rawData;
				if(!success){
					var result = Ext.decode(response.responseText);	
					Ext.Msg.alert(writeoff.homeStatementAdd.i18n('foss.stl.writeoff.common.alert'),result.message);
					return false;
				}
				//如果成功显示
				if(success){
					//对结果进行转化
					var result = Ext.decode(operation.response.responseText);  
					//判断查询结果
					if(Ext.isEmpty(result.homeStatementVo.homeStatementDto.homeStatementDList) 
							||result.homeStatementVo.homeStatementDto.homeStatementDList.length==0){
						Ext.Msg.alert(writeoff.homeStatementAdd.i18n('foss.stl.writeoff.common.alert'),
							writeoff.homeStatementAdd.i18n('foss.stl.writeoff.common.noResult'));
						return false;
					}
				}
		    }
		});
		
	}else{
		Ext.Msg.alert(writeoff.homeStatementAdd.i18n('foss.stl.writeoff.common.alert'),
			writeoff.homeStatementAdd.i18n('foss.stl.writeoff.common.validateFailAlert'));
		return false;
	}
}

/**
 * 按单号查询
 */
writeoff.homeStatementAdd.statementSelectByNumber = function(){		
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
		 	Ext.Msg.alert(writeoff.homeStatementAdd.i18n('foss.stl.writeoff.common.alert'),
		 		writeoff.homeStatementAdd.i18n('foss.stl.writeoff.homeStatementAdd.queryNosLimit'));
			 return false;
		 }
	}else{
		Ext.Msg.alert(writeoff.homeStatementAdd.i18n('foss.stl.writeoff.common.alert'),
			writeoff.homeStatementAdd.i18n('foss.stl.writeoff.common.billNosIsNullWarning'));
		return false;
	}
	//当界面校验都通过后，才执行查询方法
	if(form.isValid()){
		writeoff.homeStatementAdd.queryTabType = writeoff.STATEMENTQUERYTAB_BYSTATEMENTNUMBER;
		writeoff.homeStatementAdd.numbers = numbers;
		var grid = Ext.getCmp('T_writeoff-homeStatementAdd_content').getGrid();
		//查询后台
		grid.store.loadPage(1,{
			callback: function(records, operation, success) {
				var rawData = grid.store.proxy.reader.rawData;
				if(!success && ! rawData.isException){
					var result = Ext.decode(operation.response.responseText);	
					Ext.Msg.alert(writeoff.homeStatementAdd.i18n('foss.stl.writeoff.common.alert'),result.message);
				}
				if(success){
					//对结果进行转化
					var result = Ext.decode(operation.response.responseText);  
					//判断查询结果
					if(Ext.isEmpty(result.homeStatementVo.homeStatementDto.homeStatementDList) 
							||result.homeStatementVo.homeStatementDto.homeStatementDList.length==0){
						Ext.Msg.alert(writeoff.homeStatementAdd.i18n('foss.stl.writeoff.common.alert'),
							writeoff.homeStatementAdd.i18n('foss.stl.writeoff.common.noResult'));
						return false;
					}
				}
		    }
		});
	}else{
		Ext.Msg.alert(writeoff.homeStatementAdd.i18n('foss.stl.writeoff.common.alert'),
			writeoff.homeStatementAdd.i18n('foss.stl.writeoff.common.validateFailAlert'));
		return false;
	}	
};


Ext.define('Foss.homeStatementAdd.GridStore',{
	extend:'Ext.data.Store',
	model:'Foss.homeStatementAdd.GridModel',
	pageSize:50,
	proxy:{
		type:'ajax',
		url:writeoff.realPath('queryHomeStatementD.action'),
		actionMethods:'POST',
		reader:{
			type:'json',
			root:'homeStatementVo.homeStatementDto.homeStatementDList',
			totalProperty:'totalCount'
		}
	},
	listeners:{
		'beforeLoad':function(store, operation, eOpts){
			var searchParams;
			if(writeoff.homeStatementAdd.queryTabType==writeoff.STATEMENTQUERYTAB_BYCUSTOMER){
				searchParams = {
					'homeStatementVo.homeStatementDto.periodBeginDate':writeoff.homeStatementAdd.periodBeginDate,	
					'homeStatementVo.homeStatementDto.periodEndDate':writeoff.homeStatementAdd.periodEndDate,	
					'homeStatementVo.homeStatementDto.customerCode':writeoff.homeStatementAdd.customerCode,
					'homeStatementVo.homeStatementDto.queryTabType':writeoff.homeStatementAdd.queryTabType,
					'homeStatementVo.homeStatementDto.subCompanyCode':writeoff.homeStatementAdd.subCompanyCode,
					'homeStatementVo.homeStatementDto.billTypes':writeoff.homeStatementAdd.billTypes
				};
			}else{
				var numbers = stl.splitToArray(writeoff.homeStatementAdd.numbers);
				searchParams = {
					'homeStatementVo.homeStatementDto.numbers':numbers,
					'homeStatementVo.homeStatementDto.queryTabType':writeoff.homeStatementAdd.queryTabType
				};
			}
			//设置查询条件
			Ext.apply(operation,{
				params :searchParams
			}); 
		}
	}
});

//查询结果表格
Ext.define('Foss.homeStatementAdd.Grid',{
	extend:'Ext.grid.Panel',
	title:  writeoff.homeStatementAdd.i18n('foss.stl.writeoff.homeStatements.woodenStatementMessage'),
    bodyCls: 'autoHeight',
	cls: 'autoHeight',
	emptyText:writeoff.homeStatementAdd.i18n('foss.stl.writeoff.common.noResult'),
	frame:true,
	detailWin:null,
    store:Ext.create('Foss.homeStatementAdd.GridStore'),
    //selModel:Ext.create('Ext.selection.CheckboxModel'),
    height:500,
  	viewConfig:{
  		enableTextSelection : true//设置行可以选择，进而可以复制
  	},
    defaults:{
  		align:'center'
  	},
  	columns: [{
		header:writeoff.homeStatementAdd.i18n('foss.stl.writeoff.homeStatementAdd.payableNo'),
		dataIndex:'payableNo'
	},{
		header:writeoff.homeStatementAdd.i18n('foss.stl.writeoff.homeStatementAdd.businessDate'),
		dataIndex:'businessDate',
		renderer:function(value){
			if(value!=null){
				return Ext.Date.format(new Date(value), 'Y-m-d');
			}else{
				return null;
			}
		}
	},{
		header:writeoff.homeStatementAdd.i18n('foss.stl.writeoff.homeStatementAdd.number'),
		dataIndex:'waybillNo'
	},{
		header:writeoff.homeStatementAdd.i18n('foss.stl.writeoff.homeStatements.departmentName'),
		dataIndex:'orgName'
		
	},{
		header:writeoff.homeStatementAdd.i18n('foss.stl.writeoff.homeStatements.departmentCode'),
		dataIndex:'orgCode'
	},{
		header:writeoff.homeStatementAdd.i18n('foss.stl.writeoff.homeStatements.subsidiaryCompanyName'),
		dataIndex:'subCompanyName'
	},{
		header:writeoff.homeStatementAdd.i18n('foss.stl.writeoff.homeStatements.subsidiaryCompanyCode'),
		dataIndex:'subCompanyCode'
	},{
		header:writeoff.homeStatementAdd.i18n('foss.stl.writeoff.homeStatementAdd.billOrgName'),
		dataIndex:'billType',
		renderer : function(value) {
			if(value != null){
				var displayField = FossDataDictionary.rendererSubmitToDisplay(value, 'BILL_PAYABLE__BILL_TYPE');
				if(displayField.length == 3){
					displayField = FossDataDictionary.rendererSubmitToDisplay(value, 'BILL_RECEIVABLE__BILL_TYPE');
				}
				return displayField;
			}else{
				return null;
			}
			
		}
	},{
		header:writeoff.homeStatementAdd.i18n('foss.stl.writeoff.homeStatements.homeSupplierName'),
		dataIndex:'homeSupplyName'
	},{
		header:writeoff.homeStatementAdd.i18n('foss.stl.writeoff.homeStatements.homeSupplierCode'),
		dataIndex:'homeSupplyCode'
	},{
		header: writeoff.homeStatementAdd.i18n('foss.stl.writeoff.homeStatements.periodAmount'),
		dataIndex:'amount'
	},{
		header:writeoff.homeStatementAdd.i18n('foss.stl.writeoff.homeStatements.paidAmount'),
		dataIndex:'verifyAmount'
	},{
		header:writeoff.homeStatementAdd.i18n('foss.stl.writeoff.homeStatements.unpaidAmount'),
		dataIndex:'unverifyAmount'
	},{
		header:writeoff.homeStatementAdd.i18n('foss.stl.writeoff.homeStatementAdd.notes'),
		dataIndex:'notes',
		width:250,
		renderer : function(value) {
			if(value != null){
				var v = value.substr(0,20);
				return v;
			}else{
				return null;
			}
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
				text:writeoff.homeStatementAdd.i18n('foss.stl.writeoff.statementAdd.createStatement'),//生成对账单
				columnWidth:.1,
				handler:writeoff.homeStatementAdd.statementSave
			}]
		}];
		me.callParent();
	}
});

//创建查询表单
Ext.define('Foss.homeStatementAdd.QueryTab',{
	extend:'Ext.tab.Panel',
	frame:false,
	bodyCls: 'autoHeight',
	cls: 'innerTabPanel',
	height:250,
	items:[{
       	title: writeoff.homeStatementAdd.i18n('foss.stl.writeoff.common.homeStatementByCustomer'),
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
		    	fieldLabel:writeoff.homeStatementAdd.i18n('foss.stl.writeoff.homeStatementAdd.periodBeginDate'),
		    	allowBlank:false,
		    	name:'periodBeginDate',
		    	columnWidth: .3,
		    	format:'Y-m-d',
		    	value:stl.getTargetDate(stl.getLastMonthLastDay(new Date()),+1)
		    },{
		    	xtype:'datefield',
		    	fieldLabel:writeoff.homeStatementAdd.i18n('foss.stl.writeoff.homeStatementAdd.periodEndDate'),
		    	labelWidth:80,
		    	name:'periodEndDate',
		    	format:'Y-m-d',
		    	columnWidth: .3,
		    	value:stl.getTargetDate(new Date(),+1)
		    },{
		    	xtype:'supplierSelector',//家装代理
		    	listWidth:300,
		    	name:'customerCode',
		    	active:'Y',
		    	fieldLabel:'<span style="color:red;">*</span>'+writeoff.homeStatementAdd.i18n('foss.stl.writeoff.common.customerDetial'),
		    	columnWidth:.3
		    },{
		    	xtype:'commonsubsidiaryselector',//所属子公司名称
		    	listWidth:400,
		    	labelWidth:110,
		    	name:'subCompanyCode',
		    	active:'Y',
		    	fieldLabel:'<span style="color:red;">*</span>'+writeoff.homeStatementAdd.i18n('foss.stl.writeoff.homeStatements.subsidiaryCompanyName'),//'所属子公司名称',
		    	columnWidth:.4
		    },{
	       	 	xtype: 'fieldcontainer',
	            fieldLabel: writeoff.homeStatementAdd.i18n('foss.stl.writeoff.common.billType'),
				allowBlank:true,
	            columnWidth:1,
	            defaultType: 'checkboxfield',
	            defaults:{
	            	width:60
	            },
	            layout:'hbox',
	            items: [{
	                boxLabel  :  writeoff.homeStatementAdd.i18n('foss.stl.writeoff.common.billReceivable'),
	                name      : 'billType',
	                checked:true,
	                inputValue: writeoff.STATEMENTDETAIL_RECEIVABLE
	            },{
	                boxLabel  : writeoff.homeStatementAdd.i18n('foss.stl.writeoff.common.billpayable'),
	                name      : 'billType',
	                checked:true,
	                inputValue: writeoff.STATEMENTDETAIL_PAYABLE
	            }]
       	 	},{
				border: 1,
				xtype:'container',
				columnWidth:1,
				defaultType:'button',
				layout:'column',
				items:[{
					text:writeoff.homeStatementAdd.i18n('foss.stl.writeoff.common.reset'),
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
					text:writeoff.homeStatementAdd.i18n('foss.stl.writeoff.common.query'),
					cls:'yellow_button',
					columnWidth:.08,
					handler:writeoff.homeStatementAdd.statementSelectByCust
				}]
		    }]
	    }]
	},{
        title: writeoff.homeStatementAdd.i18n('foss.stl.writeoff.homeStatementAdd.queryByNumber'),
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
				fieldLabel:'<span style="color:red;">*</span>'+writeoff.homeStatementAdd.i18n('foss.stl.writeoff.statementAdd.number'),
				columnWidth:.65,
				regex:/^((YS|YF)?[0-9]{7,10}[;,])*((YS|YF)?[0-9]{7,10}[;,]?)$/i,
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
						html:'<span style="color:red;">'+writeoff.homeStatementAdd.i18n('foss.stl.writeoff.statementAdd.payablleorreciveNumber')+'</span>'
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
					text:writeoff.homeStatementAdd.i18n('foss.stl.writeoff.common.reset'),
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
					text:writeoff.homeStatementAdd.i18n('foss.stl.writeoff.common.query'),
					cls:'yellow_button',
					columnWidth:.075,
					handler:writeoff.homeStatementAdd.statementSelectByNumber
				}]
        	}]
        }]
    }]
});

Ext.onReady(function() {
	Ext.Ajax.timeout = 60000*30;
	Ext.QuickTips.init();
	//查询表单
	var queryTab = Ext.create('Foss.homeStatementAdd.QueryTab');
	//查询结果列表
	var grid = Ext.create('Foss.homeStatementAdd.Grid');
	//创建panel
	Ext.create('Ext.panel.Panel',{
		id: 'T_writeoff-homeStatementAdd_content',
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
		renderTo: 'T_writeoff-homeStatementAdd-body'
	});
});