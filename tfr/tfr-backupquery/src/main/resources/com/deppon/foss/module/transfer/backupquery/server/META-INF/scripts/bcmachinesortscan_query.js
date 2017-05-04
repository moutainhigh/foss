//定义计泡机model
Ext.define('Foss.unload.querybcmachsortscan.SortScanModel',{
	extend : 'Ext.data.Model',
	fields : [
	{name : 'waybillNo', type : 'string' },
	{name : 'serialNo' ,  type : 'string'},
	{name : 'weight'   ,  type : 'number'},
	{name : 'volumn'   ,  type : 'number'},
	{name : 'length'   ,  type : 'number'},
	{name : 'width'     ,  type : 'number'},
	{name : 'height'    ,  type : 'number'},
	{name : 'goodsSize' , type : 'string'},
	{name : 'operatorName' ,   type : 'string'},
	{name : 'operationDept' ,  type : 'string'},
	{name : 'scanTime' ,  convert : dateConvert,type : 'date'}
	]
});

Ext.define('Foss.unload.querybcmachsortscan.SortStore',{
	extend : 'Ext.data.Store',
	model : 'Foss.unload.querybcmachsortscan.SortScanModel',
	autoLoad : false,
	proxy :{
		type : 'ajax',
		actionMethod : 'POST',
		url : backupquery.realPath('queryBCMachSortScan.action'),
		reader : {
			type : 'json',
			root : 'vo.sortScanList',
			totalProperty : 'totalCount',
			successProperty: 'success'
		}
	},
	listeners: {
		beforeload : function(store, operation, eOpts) {
			var queryParams = backupquery.querybcmachsortscan.queryform.getValues();
			Ext.apply(operation, {
				params : {
					'vo.waybillNo' : queryParams.waybillNo,
					'vo.operatorName' : queryParams.operatorName,
					'vo.operationDept' :queryParams.operationDept ,
					'vo.begenTime' :queryParams.beginDate ,
					'vo.endTime' : queryParams.endDate
				
				}
			});
		}
}
});

Ext.define('Foss.unload.querybcmachsortscan.QueryForm',{
	extend : 'Ext.form.Panel',
	title : backupquery.querybcmachsortscan.i18n('foss.unload.querybcmachsortscan.queryItem'),//'查询条件',
	frame: true,
	animCollapse: true,
	defaultType: 'textfield',
	layout:'column',
	defaults: {
		margin: '5 5 5 5',
		labelWidth: 60
	},
	items:[
	   {
			 name: 'waybillNo',
			 fieldLabel: backupquery.querybcmachsortscan.i18n('foss.unload.querybcmachsortscan.waybillNo'),//'运单号',
			 columnWidth: .25
	   }, {
			 name: 'operatorName',
			 fieldLabel: backupquery.querybcmachsortscan.i18n('foss.unload.querybcmachsortscan.operatorName'),//'操作人',
			 xtype:'commonemployeeselector',
			 columnWidth: .25
		}, {
		 	xtype: 'rangeDateField',
			fieldLabel: backupquery.querybcmachsortscan.i18n('foss.unload.querybcmachsortscan.scanTime'),//'操作时间',
			fieldId: 'Foss_backupquery_querybcmachinesortscan_ScanTime_Id',
			dateType: 'datetimefield_date97',
			fromName: 'beginDate',
			dateRange : 7,
			labelWidth: 85,
			fromValue: Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()
					,'00','00','00'), 'Y-m-d H:i:s'),
			toValue: Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()
					,'23','59','59'), 'Y-m-d H:i:s'),
			toName: 'endDate',
			allowBlank : false,
			disallowBlank : true,
			columnWidth: .50
		}, {
			 name: 'operationDept',
			 fieldLabel: backupquery.querybcmachsortscan.i18n('foss.unload.querybcmachsortscan.operationDept'),//'操作部门',
			 xtype:'dynamicorgcombselector',
			 labelWidth : 70,
			 columnWidth: .25
		},{
			border : false,
			columnWidth : 1,
			xtype : 'container',
			defaults : {
				margin : '5 0 5 0'
			},
			layout:'column',
			items : [
				{
					text: backupquery.querybcmachsortscan.i18n('foss.unload.querybcmachsortscan.reset'), //'重置',
					xtype:"button",
					columnWidth:.10,
					height:30,
					handler:function(){
						this.up('form').getForm().reset();
						//重新初始化交接时间
						this.up('form').getForm().findField('beginDate')
							.setValue(Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate(),'00','00','00'), 'Y-m-d H:i:s'));
						this.up('form').getForm().findField('endDate')
							.setValue(Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate(),'23','59','59'), 'Y-m-d H:i:s'));
					}
				},{
					xtype: 'container',
					columnWidth:.80,
					html: '&nbsp;'
				},{
					text: backupquery.querybcmachsortscan.i18n('foss.unload.querybcmachsortscan.query'),//查询
					disabled: !backupquery.querybcmachsortscan.isPermission('unload/querysortscanButton'),
					hidden: !backupquery.querybcmachsortscan.isPermission('unload/querysortscanButton'),
					xtype:"button",
					cls:'yellow_button',
					columnWidth:.10,
					height:30,
					handler:function(){
						if(!this.up('form').getForm().isValid()) {
							return;
						}
						backupquery.querybcmachsortscan.pagingBar.moveFirst();
					}
				}]
			
		}]						       
	       
	
});
//表格panel
Ext.define('Foss.unload.querybcmachsortscan.QueryResult', {
	extend:'Ext.grid.Panel',
	title : backupquery.querybcmachsortscan.i18n('foss.unload.querybcmachsortscan.queryResult'),//'查询结果列表'
    bodyCls: 'autoHeight',
	cls: 'autoHeight',
    stripeRows: true,
    frame: true,
	animCollapse: true,
	autoScroll: true,
	height: 500,
	store: null,
	emptyText:backupquery.querybcmachsortscan.i18n('foss.unload.querybcmachsortscan.emptyResult'),// '查询结果为空'
	//定义表格列信息
	columns: [
	   {
		 header: backupquery.querybcmachsortscan.i18n('foss.unload.querybcmachsortscan.waybillNo'),//'运单号', 
		 align : 'center',
		 dataIndex: 'waybillNo',
		 flex : 1
	   },{
		 header:  backupquery.querybcmachsortscan.i18n('foss.unload.querybcmachsortscan.serialNo'),//'流水号',
		 dataIndex: 'serialNo',
		 align : 'center',
		 flex : .7 
	   },{
		  header:backupquery.querybcmachsortscan.i18n('foss.unload.querybcmachsortscan.weight'),//'重量',  
		  dataIndex: 'weight',
		  align : 'center',
		  flex : .7 
	  },{
		  header:backupquery.querybcmachsortscan.i18n('foss.unload.querybcmachsortscan.volumn'),//'体积',  
		  dataIndex: 'volumn',
		  align : 'center',
		  flex : .7 
	  },{
		  header: backupquery.querybcmachsortscan.i18n('foss.unload.querybcmachsortscan.goodsSize'),//'长*宽*高', 
		  dataIndex: 'goodsSize',
		  align : 'center',
		  flex : 2 
		  
	  }
	  ,{
		  header:backupquery.querybcmachsortscan.i18n('foss.unload.querybcmachsortscan.operatorName'),//'操作人',  
		  dataIndex: 'operatorName',
		  align : 'center',
		  flex : 1 
	  },{
		  header:backupquery.querybcmachsortscan.i18n('foss.unload.querybcmachsortscan.operationDept'),//'操作部门',  
		  dataIndex: 'operationDept',
		  align : 'center',
		  flex : 1 
	  },{
		  header:backupquery.querybcmachsortscan.i18n('foss.unload.querybcmachsortscan.scanTime'), //'操作时间',
		  dataIndex: 'scanTime',
		  align : 'center',
		  xtype : 'datecolumn',
			format : 'Y-m-d H:i:s',
		  flex : 1.5 
	  }
		],
		constructor: function(config){
			var me = this,
			cfg = Ext.apply({}, config);
			me.store = Ext.create('Foss.unload.querybcmachsortscan.SortStore');
			me.bbar = Ext.create('Deppon.StandardPaging',{
				store:me.store,
				pageSize : 20,
				maximumSize : 50,
				plugins : Ext.create('Deppon.ux.PageSizePlugin', {
					sizeList : [['20', 20], ['30', 30], ['40', 40], ['50', 50]]
				})
			});
			
			backupquery.querybcmachsortscan.pagingBar = me.bbar;
			me.callParent([cfg]);
	}
		
});
backupquery.querybcmachsortscan.queryform = Ext.create('Foss.unload.querybcmachsortscan.QueryForm');
backupquery.querybcmachsortscan.queryResult = Ext.create('Foss.unload.querybcmachsortscan.QueryResult');
Ext.onReady(function() {
	Ext.QuickTips.init();
	Ext.create('Ext.panel.Panel',{
		id: 'T_backupquery-querybcmachsortscanindex_content',
		cls: "panelContentNToolbar",
		bodyCls: 'panelContentNToolbar-body',
		layout: 'auto',
		items: [backupquery.querybcmachsortscan.queryform, backupquery.querybcmachsortscan.queryResult],
		renderTo: 'T_backupquery-querybcmachsortscanindex-body'
	});
});
