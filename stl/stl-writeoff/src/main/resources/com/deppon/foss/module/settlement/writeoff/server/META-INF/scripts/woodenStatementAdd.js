writeoff.woodenStatementAdd.statementSave = function(){
	var	woodenStatementAddGrid;
	woodenStatementAddGrid = Ext.getCmp('T_writeoff-woodenStatementAdd_content').getGrid();
	var me = this;
	Ext.MessageBox.show({
		title: writeoff.woodenStatementAdd.i18n('foss.stl.writeoff.common.alert'),
		msg: writeoff.woodenStatementAdd.i18n('foss.stl.writeoff.woodenStatementAdd.statementSave'),
		buttons: Ext.MessageBox.YESNO,
		fn: function(e){
		  	//如果本期数据为空，则提示不能导出excel
		  	if(woodenStatementAddGrid.store.data.length==0){
		  		Ext.Msg.alert(writeoff.woodenStatementAdd.i18n('foss.stl.writeoff.common.alert'), 
		  			writeoff.woodenStatementAdd.i18n('foss.stl.writeoff.statementCommon.noDataToExport'));
				return false;
		  	}
		  	if(e=='yes'){
				//判断是按那种查询进行
				var searchParams;
				if(writeoff.woodenStatementAdd.queryTabType==writeoff.STATEMENTQUERYTAB_BYCUSTOMER){
					if(Ext.isEmpty(writeoff.woodenStatementAdd.customerCode)){
						Ext.Msg.alert(writeoff.woodenStatementAdd.i18n('foss.stl.writeoff.common.alert'),
							writeoff.woodenStatementAdd.i18n('foss.stl.writeoff.common.validateFailAlertByNoCustomer'));
					 	return false;
					}
					searchParams = {
						'woodenStatementVo.woodenStatementDto.periodBeginDate':writeoff.woodenStatementAdd.periodBeginDate,	
						'woodenStatementVo.woodenStatementDto.periodEndDate':writeoff.woodenStatementAdd.periodEndDate,	
						'woodenStatementVo.woodenStatementDto.customerCode':writeoff.woodenStatementAdd.customerCode,
						'woodenStatementVo.woodenStatementDto.queryTabType':writeoff.woodenStatementAdd.queryTabType
					};
				}else{
					var numbers = stl.splitToArray(writeoff.woodenStatementAdd.numbers);
					searchParams = {
						'woodenStatementVo.woodenStatementDto.numbers':numbers,
						'woodenStatementVo.woodenStatementDto.queryTabType':writeoff.woodenStatementAdd.queryTabType
					};
				}
				//拼接vo，注入到后台
				Ext.Ajax.request({
				    url: writeoff.realPath('woodenStatementSave.action'),
				    form: Ext.fly('downloadAttachFileForm'),
				    method : 'POST',
				    params : searchParams,
				    isUpload : true,
				    success : function(response,options){
				    	var result = Ext.decode(response.responseText);
				    	var statementBillNo = result.woodenStatementVo.woodenStatementDto.statementBillNo;
				    	Ext.Msg.alert(writeoff.woodenStatementAdd.i18n('foss.stl.writeoff.common.alert'),
							writeoff.woodenStatementAdd.i18n('foss.stl.writeoff.woodenStatementAdd.exportSuccess')
							+ statementBillNo);
				    	//清空列表
				    	woodenStatementAddGrid.store.removeAll();
				    },
					exception:function(response){
						var result = Ext.decode(response.responseText);	
						Ext.Msg.alert(writeoff.woodenStatementAdd.i18n('foss.stl.writeoff.common.alert'),result.message);
					}
				});
		  	}else{
		  		return false;
		  	}
		}
	});
}

Ext.define('Foss.woodenStatementAdd.GridModel', {
	extend : 'Ext.data.Model',
	fields : [ {
		name : 'packType'
	},{
		name : 'businessDate'
	}, {
		name : 'payableNo'
	}, {
		name : 'waybillNo'
	}, {
		name : 'customerName'
	}, {
		name : 'customerCode'
	}, {
		name : 'billOrgCode'
	}, {
		name : 'billOrgName'
	}, {
		name : 'payableOrgCode'
	}, {
		name : 'payableOrgName'
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
		name : 'actualFrameVolume'
	}, {
		name : 'actualWoodenVolume'
	}, {
		name : 'actualMaskNumber'
	}, {
		name : 'bagBeltNum'
	}, {
		name : 'woodenBarLong'
	}, {
		name : 'bubbVelamenVolume'
	}, {
		name : 'bindVelamenVolume'
	} ]
});

writeoff.woodenStatementAdd.statementSelectByCust = function(){
	var form = this.up('form').getForm();
	//判断是否合法
	if(form.isValid()){
		writeoff.woodenStatementAdd.periodBeginDate = form.findField('periodBeginDate').getValue();
		writeoff.woodenStatementAdd.periodEndDate = form.findField('periodEndDate').getValue();
		writeoff.woodenStatementAdd.customerCode = form.findField('customerCode').getValue();
		writeoff.woodenStatementAdd.queryTabType = writeoff.STATEMENTQUERYTAB_BYCUSTOMER;
		var grid = Ext.getCmp('T_writeoff-woodenStatementAdd_content').getGrid();
		if(Ext.isEmpty(writeoff.woodenStatementAdd.customerCode)){
			Ext.Msg.alert(writeoff.woodenStatementAdd.i18n('foss.stl.writeoff.common.alert'),
				writeoff.woodenStatementAdd.i18n('foss.stl.writeoff.statementAdd.customerIsNullWarning'));
		 	return false;
		}
		//开始日期
		var periodBeginDate = writeoff.woodenStatementAdd.periodBeginDate;
		//结束日期
		var periodEndDate = writeoff.woodenStatementAdd.periodEndDate;
		//校验日期
		if(Ext.isEmpty(periodBeginDate)||Ext.isEmpty(periodEndDate)){
			Ext.Msg.alert(writeoff.woodenStatementAdd.i18n('foss.stl.writeoff.common.alert'),
				writeoff.woodenStatementAdd.i18n('foss.stl.writeoff.statementAdd.quryDateIsNullWarning'));
			return false;
		}
		//比较起始日期和结束日期
		var compareTwoDate = stl.compareTwoDate(periodBeginDate,periodEndDate);
		if(compareTwoDate>stl.DATELIMITDAYS_MONTH){
			Ext.Msg.alert(writeoff.woodenStatementAdd.i18n('foss.stl.writeoff.common.alert'),
				writeoff.woodenStatementAdd.i18n('foss.stl.writeoff.statementEdit.queryDateMaxLimit'));
			return false;
		}else if(compareTwoDate<1){
			Ext.Msg.alert(writeoff.woodenStatementAdd.i18n('foss.stl.writeoff.common.alert'),
				writeoff.woodenStatementAdd.i18n('foss.stl.writeoff.common.dateEndBeforeStatrtWarining'));
			return false;
		}
		//获取grid
		grid.store.loadPage(1,{
			callback: function(records, operation, success) {
				var rawData = grid.store.proxy.reader.rawData;
				if(!success){
					var result = Ext.decode(response.responseText);	
					Ext.Msg.alert(writeoff.woodenStatementAdd.i18n('foss.stl.writeoff.common.alert'),result.message);
					return false;
				}
				//如果成功显示
				if(success){
					//对结果进行转化
					var result = Ext.decode(operation.response.responseText);  
					//判断查询结果
					if(Ext.isEmpty(result.woodenStatementVo.woodenStatementDto.woodenStatementDList) 
							||result.woodenStatementVo.woodenStatementDto.woodenStatementDList.length==0){
						Ext.Msg.alert(writeoff.woodenStatementAdd.i18n('foss.stl.writeoff.common.alert'),
							writeoff.woodenStatementAdd.i18n('foss.stl.writeoff.common.noResult'));
						return false;
					}
				}
		    }
		});
		
	}else{
		Ext.Msg.alert(writeoff.woodenStatementAdd.i18n('foss.stl.writeoff.common.alert'),
			writeoff.woodenStatementAdd.i18n('foss.stl.writeoff.common.validateFailAlert'));
		return false;
	}
}

writeoff.woodenStatementAdd.statementSelectByNumber = function(){		
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
		 //判断输入单号是否超过100个
		 if(billNumberArray.length>100){
		 	Ext.Msg.alert(writeoff.woodenStatementAdd.i18n('foss.stl.writeoff.common.alert'),
		 		writeoff.woodenStatementAdd.i18n('foss.stl.writeoff.woodenStatementEdit.queryNosLimit'));
		 	return false;
		 }
	}else{
		Ext.Msg.alert(writeoff.woodenStatementAdd.i18n('foss.stl.writeoff.common.alert'),
			writeoff.woodenStatementAdd.i18n('foss.stl.writeoff.common.billNosIsNullWarning'));
		return false;
	}
	//当界面校验都通过后，才执行查询方法
	if(form.isValid()){
		writeoff.woodenStatementAdd.queryTabType = writeoff.STATEMENTQUERYTAB_BYSTATEMENTNUMBER;
		writeoff.woodenStatementAdd.numbers = numbers;
		var grid = Ext.getCmp('T_writeoff-woodenStatementAdd_content').getGrid();
		//查询后台
		grid.store.loadPage(1,{
			callback: function(records, operation, success) {
				var rawData = grid.store.proxy.reader.rawData;
				if(!success && ! rawData.isException){
					var result = Ext.decode(operation.response.responseText);	
					Ext.Msg.alert(writeoff.woodenStatementAdd.i18n('foss.stl.writeoff.common.alert'),result.message);
				}
				if(success){
					//对结果进行转化
					var result = Ext.decode(operation.response.responseText);  
					//判断查询结果
					if(Ext.isEmpty(result.woodenStatementVo.woodenStatementDto.woodenStatementDList) 
							||result.woodenStatementVo.woodenStatementDto.woodenStatementDList.length==0){
						Ext.Msg.alert(writeoff.woodenStatementAdd.i18n('foss.stl.writeoff.common.alert'),
							writeoff.woodenStatementAdd.i18n('foss.stl.writeoff.common.noResult'));
						return false;
					}
				}
		    }
		});
	}else{
		Ext.Msg.alert(writeoff.woodenStatementAdd.i18n('foss.stl.writeoff.common.alert'),
			writeoff.woodenStatementAdd.i18n('foss.stl.writeoff.common.validateFailAlert'));
		return false;
	}	
};

Ext.define('Foss.woodenStatementAdd.GridStore',{
	extend:'Ext.data.Store',
	model:'Foss.woodenStatementAdd.GridModel',
	pageSize:50,
	proxy:{
		type:'ajax',
		url:writeoff.realPath('queryWoodenStatementD.action'),
		actionMethods:'POST',
		reader:{
			type:'json',
			root:'woodenStatementVo.woodenStatementDto.woodenStatementDList',
			totalProperty:'totalCount'
		}
	},
	listeners:{
		'beforeLoad':function(store, operation, eOpts){
			var searchParams;
			if(writeoff.woodenStatementAdd.queryTabType==writeoff.STATEMENTQUERYTAB_BYCUSTOMER){
				searchParams = {
					'woodenStatementVo.woodenStatementDto.periodBeginDate':writeoff.woodenStatementAdd.periodBeginDate,	
					'woodenStatementVo.woodenStatementDto.periodEndDate':writeoff.woodenStatementAdd.periodEndDate,	
					'woodenStatementVo.woodenStatementDto.customerCode':writeoff.woodenStatementAdd.customerCode,
					'woodenStatementVo.woodenStatementDto.queryTabType':writeoff.woodenStatementAdd.queryTabType
				};
			}else{
				var numbers = stl.splitToArray(writeoff.woodenStatementAdd.numbers);
				searchParams = {
					'woodenStatementVo.woodenStatementDto.numbers':numbers,
					'woodenStatementVo.woodenStatementDto.queryTabType':writeoff.woodenStatementAdd.queryTabType
				};
			}
			//设置查询条件
			Ext.apply(operation,{
				params :searchParams
			}); 
		}
	}
});

Ext.define('Foss.woodenStatementAdd.Grid',{
	extend:'Ext.grid.Panel',
	title: writeoff.woodenStatementAdd.i18n('foss.stl.writeoff.woodenStatementAdd.woodenStatementMessage'),
    bodyCls: 'autoHeight',
	cls: 'autoHeight',
	emptyText:writeoff.woodenStatementAdd.i18n('foss.stl.writeoff.common.noResult'),
	frame:true,
	detailWin:null,
    store:Ext.create('Foss.woodenStatementAdd.GridStore'),
    //selModel:Ext.create('Ext.selection.CheckboxModel'),
    height:500,
  	viewConfig:{
  		enableTextSelection : true//设置行可以选择，进而可以复制
  	},
    defaults:{
  		align:'center'
  	},
  	columns: [{
		header:writeoff.woodenStatementAdd.i18n('foss.stl.writeoff.woodenStatementAdd.packType'),
		dataIndex:'packType',
		renderer:function(value){
			var displayField = null;
			if (!Ext.isEmpty(value)) {
				if (value == "MAP") {
					displayField = "主要包装";
				} else {
					displayField = "辅助包装";
				}
			}
			return displayField;
		}
	},{
		header:writeoff.woodenStatementAdd.i18n('foss.stl.writeoff.woodenStatementAdd.businessDate'),
		dataIndex:'businessDate',
		renderer:function(value){
			if(value!=null){
				return Ext.Date.format(new Date(value), 'Y-m-d');
			}else{
				return null;
			}
		}
	},{
		header:writeoff.woodenStatementAdd.i18n('foss.stl.writeoff.woodenStatementAdd.number'),
		dataIndex:'payableNo',
		width:120
	},{
		header:writeoff.woodenStatementAdd.i18n('foss.stl.writeoff.woodenStatementAdd.waybillNo'),
		dataIndex:'waybillNo',
		width:150
	},{
		header:writeoff.woodenStatementAdd.i18n('foss.stl.writeoff.woodenStatementAdd.customerName'),
		dataIndex:'customerName'
	},{
		header:writeoff.woodenStatementAdd.i18n('foss.stl.writeoff.woodenStatementAdd.customerCode'),
		dataIndex:'customerCode'
	},{
		header:writeoff.woodenStatementAdd.i18n('foss.stl.writeoff.woodenStatementAdd.billOrgCode'),
		dataIndex:'billOrgCode'
	},{
		header:writeoff.woodenStatementAdd.i18n('foss.stl.writeoff.woodenStatementAdd.billOrgName'),
		dataIndex:'billOrgName'
	},{
		header:writeoff.woodenStatementAdd.i18n('foss.stl.writeoff.woodenStatementAdd.payableOrgCode'),
		dataIndex:'payableOrgCode'
	},{
		header:writeoff.woodenStatementAdd.i18n('foss.stl.writeoff.woodenStatementAdd.payableOrgName'),
		dataIndex:'payableOrgName'
	},{
		header:writeoff.woodenStatementAdd.i18n('foss.stl.writeoff.woodenStatementAdd.amount'),
		dataIndex:'amount'
	},{
		header:writeoff.woodenStatementAdd.i18n('foss.stl.writeoff.woodenStatementAdd.verifyAmount'),
		dataIndex:'verifyAmount'
	},{
		header:writeoff.woodenStatementAdd.i18n('foss.stl.writeoff.woodenStatementAdd.unverifyAmount'),
		dataIndex:'unverifyAmount'
	},{
		header:writeoff.woodenStatementAdd.i18n('foss.stl.writeoff.woodenStatementAdd.actualFrameVolume'),
		dataIndex:'actualFrameVolume'
	},{
		header:writeoff.woodenStatementAdd.i18n('foss.stl.writeoff.woodenStatementAdd.actualWoodenVolume'),
		dataIndex:'actualWoodenVolume'
	},{
		header:writeoff.woodenStatementAdd.i18n('foss.stl.writeoff.woodenStatementAdd.actualMaskNumber'),
		dataIndex:'actualMaskNumber'
	},{
		header:writeoff.woodenStatementAdd.i18n('foss.stl.writeoff.woodenStatementAdd.bagBeltNum'),
		dataIndex:'bagBeltNum'
	},{
		header:writeoff.woodenStatementAdd.i18n('foss.stl.writeoff.woodenStatementAdd.woodenBarLong'),
		dataIndex:'woodenBarLong'
	},{
		header:writeoff.woodenStatementAdd.i18n('foss.stl.writeoff.woodenStatementAdd.bubbVelamenVolume'),
		dataIndex:'bubbVelamenVolume'
	},{
		header:writeoff.woodenStatementAdd.i18n('foss.stl.writeoff.woodenStatementAdd.bindVelamenVolume'),
		dataIndex:'bindVelamenVolume'
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
				text:writeoff.woodenStatementAdd.i18n('foss.stl.writeoff.statementAdd.createStatement'),
				columnWidth:.1,
				hidden:!writeoff.woodenStatementAdd.isPermission('/stl-web/writeoff/addStatement.action'),
				handler:writeoff.woodenStatementAdd.statementSave
			}]
		}];
		me.callParent();
	}
});

Ext.define('Foss.woodenStatementAdd.QueryTab',{
	extend:'Ext.tab.Panel',
	frame:false,
	bodyCls: 'autoHeight',
	cls: 'innerTabPanel',
	height:250,
	items:[{
       	title: writeoff.woodenStatementAdd.i18n('foss.stl.writeoff.common.woodenStatementByCustomer'),
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
		    	fieldLabel:writeoff.woodenStatementAdd.i18n('foss.stl.writeoff.woodenStatementAdd.periodBeginDate'),
		    	allowBlank:false,
		    	name:'periodBeginDate',
		    	columnWidth: .3,
		    	format:'Y-m-d',
		    	value:stl.getTargetDate(new Date(),-1)
		    },{
		    	xtype:'datefield',
		    	fieldLabel:writeoff.woodenStatementAdd.i18n('foss.stl.writeoff.woodenStatementAdd.periodEndDate'),
		    	labelWidth:80,
		    	name:'periodEndDate',
		    	format:'Y-m-d',
		    	columnWidth: .3,
		    	value:stl.getTargetDate(new Date(),+1)
		    },{
		    	xtype:'dynamicPackagingSupplierSelector',
		    	listWidth:300,
		    	name:'customerCode',
		    	active:'Y',
		    	fieldLabel:'<span style="color:red;">*</span>'+writeoff.woodenStatementAdd.i18n('foss.stl.writeoff.common.customerDetial'),
		    	columnWidth:.3
		    },{
				border: 1,
				xtype:'container',
				columnWidth:1,
				defaultType:'button',
				layout:'column',
				items:[{
					text:writeoff.woodenStatementAdd.i18n('foss.stl.writeoff.common.reset'),
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
					text:writeoff.woodenStatementAdd.i18n('foss.stl.writeoff.common.query'),
					cls:'yellow_button',
					columnWidth:.08,
					handler:writeoff.woodenStatementAdd.statementSelectByCust
				}]
		    }]
	    }]
	},{
        title: writeoff.woodenStatementAdd.i18n('foss.stl.writeoff.woodenStatementAdd.queryByNumber'),
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
				fieldLabel:'<span style="color:red;">*</span>'+writeoff.woodenStatementAdd.i18n('foss.stl.writeoff.statementAdd.number'),
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
						html:'<span style="color:red;">'+writeoff.woodenStatementAdd.i18n('foss.stl.writeoff.woodenStatementAdd.queryNosDesc')+'</span>'
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
					text:writeoff.woodenStatementAdd.i18n('foss.stl.writeoff.common.reset'),
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
					text:writeoff.woodenStatementAdd.i18n('foss.stl.writeoff.common.query'),
					cls:'yellow_button',
					columnWidth:.075,
					handler:writeoff.woodenStatementAdd.statementSelectByNumber
				}]
        	}]
        }]
    }]
});

Ext.onReady(function() {
	Ext.Ajax.timeout = 60000*30;
	Ext.QuickTips.init();
	//查询表单
	var queryTab = Ext.create('Foss.woodenStatementAdd.QueryTab');
	//查询结果列表
	var grid = Ext.create('Foss.woodenStatementAdd.Grid');

	//创建panel
	Ext.create('Ext.panel.Panel',{
		id: 'T_writeoff-woodenStatementAdd_content',
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
		renderTo: 'T_writeoff-woodenStatementAdd-body'
	});
});