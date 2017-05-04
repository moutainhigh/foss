
writeoff.customersNotReconciled.exportXLS = function(){
	var	columns,
	arrayColumns,
	arrayColumnNames,
	customersNotReconciledGrid
	customersNotReconciledGrid = Ext.getCmp('T_writeoff-customersNotReconciled_content').getGrid();
	var me = this;
	Ext.MessageBox.show({
		title: writeoff.customersNotReconciled.i18n('foss.stl.writeoff.common.alert'),
		msg: writeoff.customersNotReconciled.i18n('foss.stl.writeoff.customersNotReconciled.export'),
		buttons: Ext.MessageBox.YESNO,
		fn: function(e){
		  	//如果本期数据为空，则提示不能导出excel
		  	if(customersNotReconciledGrid.store.data.length==0){
		  		Ext.ux.Toast.msg(writeoff.customersNotReconciled.i18n('foss.stl.writeoff.common.alert'), 
		  				writeoff.customersNotReconciled.i18n('foss.stl.writeoff.statementCommon.noDataToExport'), 'error', 1000);
				return false;
		  	}
		  	
		  	if(e=='yes'){
		  		//转化列头和列明
				columns = customersNotReconciledGrid.columns;
				arrayColumns = [];
				arrayColumnNames = [];
				//声明单据父类型、单据子类型
				var billParentType,billType;
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
				
				//判断是按那种查询进行
					searchParams = {
							'customersNotReconciledVo.customersNotReconciledDto.largeRegionCode':writeoff.customersNotReconciled.largeRegion,	
							'customersNotReconciledVo.customersNotReconciledDto.smallRegionCode':writeoff.customersNotReconciled.smallRegion,	
							'customersNotReconciledVo.customersNotReconciledDto.statementOrgCode':writeoff.customersNotReconciled.statementOrgCode,	
							'customersNotReconciledVo.customersNotReconciledDto.customerCode':writeoff.customersNotReconciled.customerCode,
					    	'customersNotReconciledVo.arrayColumns':arrayColumns,
					    	'customersNotReconciledVo.arrayColumnNames':arrayColumnNames
						};
				//拼接vo，注入到后台
				Ext.Ajax.request({
				    url: writeoff.realPath('exportXLS.action'),
				    form: Ext.fly('downloadAttachFileForm'),
				    method : 'POST',
				    params : searchParams,
				    isUpload: true,
				    success : function(response,options){
						Ext.ux.Toast.msg(writeoff.customersNotReconciled.i18n('foss.stl.writeoff.common.alert'),
								writeoff.customersNotReconciled.i18n('foss.stl.writeoff.customersNotReconciled.exportSuccess'), 'success', 1000);
				    }
				});
		  	}else{
		  		return false;
		  	}
		}
	});
}
Ext.define('Foss.customersNotReconciled.GridModel', {
	extend : 'Ext.data.Model',
	fields : [ {
		name : 'largeRegion'
	}, {
		name : 'smallRegion'
	}, {
		name : 'statementOrgName'
	}, {
		name : 'customerCode'
	}, {
		name : 'customerName'
	}, {
		name : 'amountOneMonth',
		type : 'double'
	}, {
		name : 'amountTwoMonth',
		type : 'double'
	}, {
		name : 'amountThreeMonth',
		type : 'double'
	} ]
});

writeoff.customersNotReconciled.statementSelectByCust = function(){
	var form = this.up('form').getForm();
	//判断是否合法
	if(form.isValid()){
		writeoff.customersNotReconciled.largeRegion = form.findField('largeRegion').getValue();
		writeoff.customersNotReconciled.smallRegion = form.findField('smallRegion').getValue();
		writeoff.customersNotReconciled.statementOrgCode = form.findField('statementOrgCode').getValue();
		writeoff.customersNotReconciled.customerCode = form.findField('customerCode').getValue();
		var grid = Ext.getCmp('T_writeoff-customersNotReconciled_content').getGrid();
		//获取grid
		grid.store.loadPage(1,{
			callback: function(records, operation, success) {
				var rawData = grid.store.proxy.reader.rawData;
				if(!success){
					var result = Ext.decode(operation.response.responseText);	
					Ext.Msg.alert(writeoff.customersNotReconciled.i18n('foss.stl.writeoff.common.alert'),result.message);
					return false;
				}
				//如果成功显示
				if(success){
					//对结果进行转化
					var result = Ext.decode(operation.response.responseText);  
					//判断查询结果
					if(Ext.isEmpty(result.customersNotReconciledVo.customersNotReconciledDto.customersNotReconciledList) 
							||result.customersNotReconciledVo.customersNotReconciledDto.customersNotReconciledList.length==0){
						Ext.Msg.alert(writeoff.customersNotReconciled.i18n('foss.stl.writeoff.common.alert'),
								writeoff.customersNotReconciled.i18n('foss.stl.writeoff.common.noResult'));
						return false;
					}
				}
		    }
		});
		
	}else{
		Ext.Msg.alert(writeoff.customersNotReconciled.i18n('foss.stl.writeoff.common.alert'),
				writeoff.customersNotReconciled.i18n('foss.stl.writeoff.common.validateFailAlert'));
		return false;
	}
}

Ext.define('Foss.customersNotReconciled.GridStore',{
	extend:'Ext.data.Store',
	model:'Foss.customersNotReconciled.GridModel',
	proxy:{
		type:'ajax',
		url:writeoff.realPath('queryCustomersNotReconciled.action'),
		actionMethods:'POST',
		reader:{
			type:'json',
			root:'customersNotReconciledVo.customersNotReconciledDto.customersNotReconciledList'
		}
	},
	listeners:{
		'beforeLoad':function(store, operation, eOpts){
			//声明查询条件
			var searchParams = {
				'customersNotReconciledVo.customersNotReconciledDto.largeRegionCode':writeoff.customersNotReconciled.largeRegion,	
				'customersNotReconciledVo.customersNotReconciledDto.smallRegionCode':writeoff.customersNotReconciled.smallRegion,	
				'customersNotReconciledVo.customersNotReconciledDto.statementOrgCode':writeoff.customersNotReconciled.statementOrgCode,	
				'customersNotReconciledVo.customersNotReconciledDto.customerCode':writeoff.customersNotReconciled.customerCode
			};
			//设置查询条件
			Ext.apply(operation,{
				params :searchParams
			}); 
		}
	}
});

Ext.define('Foss.customersNotReconciled.Grid',{
	extend:'Ext.grid.Panel',
	title: writeoff.customersNotReconciled.i18n('foss.stl.writeoff.customersNotReconciled.cusNotRecMessage'),
    bodyCls: 'autoHeight',
	cls: 'autoHeight',
	emptyText:writeoff.customersNotReconciled.i18n('foss.stl.writeoff.common.noResult'),
	frame:true,
	detailWin:null,
    store:Ext.create('Foss.customersNotReconciled.GridStore'),
    selModel:Ext.create('Ext.selection.CheckboxModel'),
    height:500,
  	viewConfig:{
  		enableTextSelection : true//设置行可以选择，进而可以复制
  	},
    defaults:{
  		align:'center'
  	},
  	columns: [{
		header:writeoff.customersNotReconciled.i18n('foss.stl.writeoff.customersNotReconciled.largeRegion'),
		dataIndex:'largeRegion'
	},{
		header:writeoff.customersNotReconciled.i18n('foss.stl.writeoff.customersNotReconciled.smallRegion'),
		dataIndex:'smallRegion',
		width:120
	},{
		header:writeoff.customersNotReconciled.i18n('foss.stl.writeoff.customersNotReconciled.statementOrgName'),
		dataIndex:'statementOrgName',
		width:150
	},{
		header:writeoff.customersNotReconciled.i18n('foss.stl.writeoff.customersNotReconciled.customerName'),
		dataIndex:'customerName'
	},{
		header:writeoff.customersNotReconciled.i18n('foss.stl.writeoff.customersNotReconciled.customerCode'),
		dataIndex:'customerCode'
	},{
		header:writeoff.customersNotReconciled.i18n('foss.stl.writeoff.customersNotReconciled.amountOneMonth'),
		dataIndex:'amountOneMonth'
	},{
		header:writeoff.customersNotReconciled.i18n('foss.stl.writeoff.customersNotReconciled.amountTwoMonth'),
		dataIndex:'amountTwoMonth'
	},{
		header:writeoff.customersNotReconciled.i18n('foss.stl.writeoff.customersNotReconciled.amountThreeMonth'),
		dataIndex:'amountThreeMonth'
	},{
		header:writeoff.customersNotReconciled.i18n('foss.stl.writeoff.customersNotReconciled.amountTotal'),
		dataIndex:'amountTotal',
		renderer:function(value,m,record){
			value = 0;
			var amountOneMonth = record.get('amountOneMonth');
			var amountTwoMonth = record.get('amountTwoMonth');
			var amountThreeMonth = record.get('amountThreeMonth');
			value = amountOneMonth+amountTwoMonth+amountThreeMonth;
			return value;
		}
	}],
	initComponent:function(){
		var me = this;
		me.dockedItems = [{
	   		xtype: 'toolbar',
		    dock: 'top',
		    layout:'column',		    	
		    defaults:{
			margin:'0 0 5 3'
			},		
		    items: [{
				xtype: 'container',
				border : false,
				columnWidth:.9,
				html: '&nbsp;'
			},{
				xtype:'button',
				text:writeoff.customersNotReconciled.i18n('foss.stl.writeoff.customersNotReconciled.exportXLS'),
				columnWidth:.1,
				hidden:!writeoff.customersNotReconciled.isPermission('/stl-web/writeoff/exportXLS.action'),
				handler:writeoff.customersNotReconciled.exportXLS
			}]
		}];
		me.callParent();
	}
});

Ext.define('Foss.customersNotReconciled.QueryForm',{
	extend:'Ext.form.Panel',
	frame:true,
	cls : 'autoHeight',
	bodyCls : 'autoHeight',
	title:writeoff.customersNotReconciled.i18n('foss.stl.writeoff.customersNotReconciled.queryByCustomer'),
	layout:'column',
	defaults:{
		labelWidth:75,
		margin:'5 5 5 5'
	},
	items:[{//邓大伟
		xtype:'linkagecomboselector',
		eventType : ['focus'],// 一般callparent包含focus事件，如果有callparent,则focus事件可不用传递
		itemId : 'Foss_baseinfo_BigRegion_ID',
		store : Ext.create('Foss.baseinfo.commonSelector.BigRegionStore'),// 从外面传入
		columnWidth:.3,
		fieldLabel : writeoff.customersNotReconciled.i18n('foss.stl.writeoff.common.largeRegion'),
		name : 'largeRegion',
		isPaging: true,
		allowBlank : true,
		value:'',
		minChars : 0,
		displayField : 'name',// 显示名称
		valueField : 'code',
		minChars : 0,
		queryParam : 'commonOrgVo.name'
	},
	{
		xtype:'linkagecomboselector',
		itemId : 'Foss_baseinfo_SmallRegion_ID',
		eventType : ['callparent'],// 一般callparent包含focus事件，如果有callparent,则focus事件可不用传递
		store : Ext.create('Foss.baseinfo.commonSelector.SmallRegionStore'),// 从外面传入
		columnWidth:.3,
		fieldLabel : writeoff.customersNotReconciled.i18n('foss.stl.writeoff.common.smallRegion'),
		name : 'smallRegion',
		allowBlank : true,
		isPaging: true,
		parentParamsAndItemIds : {
			'commonOrgVo.code' : 'Foss_baseinfo_BigRegion_ID'
		},// 此处城市不需要传入
		minChars : 0,
		displayField : 'name',// 显示名称
		valueField : 'code',
		minChars : 0,
		queryParam : 'commonOrgVo.name'
	},{
		xtype : 'dynamicorgcombselector',
		fieldLabel : writeoff.customersNotReconciled.i18n('foss.stl.writeoff.common.statementOrgName'),
		name : 'statementOrgCode',
		value : stl.currentDept.name,
		columnWidth:.3,
		labelWidth : 85,
		listWidth : 300,// 设置下拉框宽度
		isPaging : true
	},{
    	xtype:'commoncustomerselector',
    	listWidth:300,
    	emptyText:'客户编码和手机均可查询',
    	name:'customerCode',
    	all:'true',//查询所有(包含作废)
    	contcatFlag :'Y',//增加按手机号查询
		singlePeopleFlag :'Y',
    	fieldLabel:writeoff.customersNotReconciled.i18n('foss.stl.writeoff.common.customerDetial'),
    	columnWidth:.3
    },{
		border: 1,
		xtype:'container',
		columnWidth:1,
		defaultType:'button',
		layout:'column',
		items:[{
			text:writeoff.customersNotReconciled.i18n('foss.stl.writeoff.common.reset'),
			columnWidth:.08,
			handler:function(){
				this.up('form').getForm().reset();
				var dept = this.up('form').getForm().findField("statementOrgCode");
				if(!Ext.isEmpty(stl.currentDept.code)){
					var displayText = stl.currentDept.name
					var valueText = stl.currentDept.code;
					var store = dept.store;
					var key = dept.displayField + '';
					var value = dept.valueField+ '';
					
					var m = Ext.create(store.model.modelName);
					m.set(key, displayText);
					m.set(value, valueText);
					
					store.loadRecords([m]);
					dept.setValue(valueText);
				}
			}
		},{
			xtype: 'container',
			border : false,
			columnWidth:.74,
			html: '&nbsp;'
		},{
			text:writeoff.customersNotReconciled.i18n('foss.stl.writeoff.common.query'),
			cls:'yellow_button',
			columnWidth:.08,
			handler:writeoff.customersNotReconciled.statementSelectByCust
		}]
    }]
});

Ext.onReady(function() {
	Ext.QuickTips.init();
	//查询表单
	var queryForm = Ext.create('Foss.customersNotReconciled.QueryForm');
	//查询结果列表
	var grid = Ext.create('Foss.customersNotReconciled.Grid');

	//创建panel
	Ext.create('Ext.panel.Panel',{
		id: 'T_writeoff-customersNotReconciled_content',
		cls: "panelContentNToolbar",
		bodyCls: 'panelContentNToolbar-body',
		layout: 'auto',
		getGrid:function(){
			return grid;
		},
		getForm:function(){
			return queryForm;
		},
		items: [queryForm,grid],
		renderTo: 'T_writeoff-customersNotReconciled-body',
		listeners:{
			'boxready':function(th){
				var roles = stl.currentUserDepts;
				var dept = this.down('form').getForm().findField("statementOrgCode");
				if(!Ext.isEmpty(stl.currentDept.code)){
					var displayText = stl.currentDept.name
					var valueText = stl.currentDept.code;
					var store = dept.store;
					var key = dept.displayField + '';
					var value = dept.valueField+ '';
					
					var m = Ext.create(store.model.modelName);
					m.set(key, displayText);
					m.set(value, valueText);
					
					store.loadRecords([m]);
					dept.setValue(valueText);
				}
			}
		}
	});
});