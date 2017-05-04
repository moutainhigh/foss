//定义方法，生成查询条件中“交接时间”的起始和结束时间
backupquery.querysortingscan.getQueryTime = function(isBegin){
	var nowDate = new Date();
	if(isBegin){
		nowDate.setHours(0);
		nowDate.setSeconds(0);
		nowDate.setMinutes(0);
	}else{
		nowDate.setHours(23);
		nowDate.setSeconds(59);
		nowDate.setMinutes(59);
	}
	return nowDate;
}

Ext.define('Foss.UnLoad.QuerySortingScan.ScanTypeStore', {
	extend:'Ext.data.Store',
    fields: ['value', 'name'],
    data : [
        {"value":"UP", "name":backupquery.querysortingscan.i18n('foss.unload.querysortingscan.up')},
        {"value":"DOWN", "name":backupquery.querysortingscan.i18n('foss.unload.querysortingscan.down')}/*,
        {"value":"", "name":backupquery.querysortingscan.i18n('foss.unload.querysortingscan.all')}*/
    ]
});
Ext.define('Foss.UnLoad.QuerySortingScan.ScanModeStore', {
	extend:'Ext.data.Store',
    fields: ['value', 'name'],
    data : [
        {"value":"PDA", "name":backupquery.querysortingscan.i18n('foss.unload.querysortingscan.pda')},
        {"value":"BSC", "name":backupquery.querysortingscan.i18n('foss.unload.querysortingscan.bsc')},
        {"value":"ALL", "name":backupquery.querysortingscan.i18n('foss.unload.querysortingscan.all')}
    ]
});


Ext.define('Foss.Unload.QuerySortingScan.QueryForm',{
	extend: 'Ext.form.Panel',
	layout: 'column',
	//width: 490,
	frame: true,
	border: false,
	title : backupquery.querysortingscan.i18n('foss.unload.querysortingscan.queryCondition'),//'查询条件',
	defaults: {
		margin: '5 3 7 3',
		labelWidth: 80
	},
	items: [{
		xtype: 'textfield',
		fieldLabel: backupquery.querysortingscan.i18n('foss.unload.querysortingscan.wayBillNo'),//'运单号',
		name: 'wayBillNo',
		columnWidth:.25
	},{
		xtype: 'textfield',
		fieldLabel: backupquery.querysortingscan.i18n('foss.unload.querysortingscan.serialNo'),//'流水号',
		name: 'serialNo',
		columnWidth:.25
	},{
		name : 'operator',
		fieldLabel: backupquery.querysortingscan.i18n('foss.unload.querysortingscan.operatorName'),//'操作人',
		xtype : 'commonemployeeselector',
		parentOrgCode : backupquery.querysortingscan.superOrgCode,
		displayField : 'empName',// 显示名称
		valueField : 'empCode',// 值
		columnWidth : .25
	}/*,{
		fieldLabel : backupquery.querysortingscan.i18n('foss.unload.querysortingscan.orgName'),//'操作部门',
		columnWidth : .25,
		name : 'org',
		xtype : 'dynamicorgcombselector',
		type : 'ORG',
		salesDepartment : 'Y',
		transferCenter : 'Y',
		airDispatch : 'Y',
		doAirDispatch : 'Y'
	}*/,{
		xtype: 'combo',
		name: 'scanType',
		editable :false,
		fieldLabel: backupquery.querysortingscan.i18n('foss.unload.querysortingscan.scanType'),//'扫描类型',
		store: Ext.create('Foss.UnLoad.QuerySortingScan.ScanTypeStore'),
		queryMode: 'local',
		displayField: 'name',
		valueField: 'value',
		value : 'UP',
		columnWidth:.25
	},{
		xtype : 'rangeDateField',
		fieldLabel : backupquery.querysortingscan.i18n('foss.unload.querysortingscan.queryTime'),//操作时间,
		columnWidth : .5,
		// 类型为datetimefield_date97需要配置fieldId,可以赋予此属性任何唯一标 //识的String值
		fieldId : 'Foss_Unload_QuerySortingScan_QueryForm_QueryTime_ID',
		dateType: 'datetimefield_date97',
		//dateType: 'datefield',
		dateRange : 1,
		fromName : 'queryTimeBeign',
		fromValue : Ext.Date.format(backupquery.querysortingscan.getQueryTime(true), 'Y-m-d H:i:s'),
		toValue : Ext.Date.format(backupquery.querysortingscan.getQueryTime(false), 'Y-m-d H:i:s'),
		toName : 'queryTimeEnd',
		allowBlank : false,
		disallowBlank : true
	},{
		xtype: 'combo',
		name: 'scanMode',
		editable :false,
		fieldLabel: backupquery.querysortingscan.i18n('foss.unload.querysortingscan.scanMode'),//'扫描方式',
		store: Ext.create('Foss.UnLoad.QuerySortingScan.ScanModeStore'),
		queryMode: 'local',
		displayField: 'name',
		valueField: 'value',
		value : 'ALL',
		columnWidth:.25
	},
	{
		border: 1,
		xtype:'container',
		columnWidth:1,
		defaultType:'button',
		layout:'column',
		items:[{
			text:backupquery.querysortingscan.i18n('foss.unload.querysortingscan.reset'),//'重置',
			columnWidth:.08,
			handler:function(){
				this.up('form').getForm().reset();
				//重新初始化查询时间
				this.up('form').getForm().findField('queryTimeBeign').setValue(Ext.Date.format(backupquery.querysortingscan.getQueryTime(true), 'Y-m-d H:i:s'));
				this.up('form').getForm().findField('queryTimeEnd').setValue(Ext.Date.format(backupquery.querysortingscan.getQueryTime(false), 'Y-m-d H:i:s'));
			
			}
		},{
			xtype: 'container',
			border : false,
			columnWidth:.76,
			html: '&nbsp;'
		},{
			text:backupquery.querysortingscan.i18n('foss.unload.querysortingscan.query'),//'查询',
			cls:'yellow_button',
			columnWidth:.08,
			disabled : !backupquery.querysortingscan.isPermission('unload/querysortingscanButton'),
			hidden : !backupquery.querysortingscan.isPermission('unload/querysortingscanButton'),
			handler:function(){
				if(!this.up('form').getForm().isValid()) {
					return;
				}
				backupquery.querysortingscan.pagingBar.moveFirst();
			}
		},{
			xtype : 'button',
			text : backupquery.querysortingscan.i18n('foss.unload.querysortingscan.expertExcelButtonText'),//'导出'
			columnWidth:.08,
			handler : function(){
				if(!Ext.fly('downloadAttachFileForm')){
					    var frm = document.createElement('form');
					    frm.id = 'downloadAttachFileForm';
					    frm.style.display = 'none';
					    document.body.appendChild(frm);
				}
				//获取查询参数
				var params = this.up('form').getForm().getValues();
				Ext.Ajax.request({
					url : backupquery.realPath('exportSortingScanToExcel.action'),
					form: Ext.fly('downloadAttachFileForm'),
					method : 'POST',
					params : {
						'vo.wayBillNo':params.wayBillNo,
						'vo.serialNo':params.serialNo,
						'vo.operatorCode':params.operator,
						//'vo.orgCode':params.org,
						'vo.scanType':params.scanType,
						'vo.scanMode':params.scanMode,
						'vo.queryTimeBegin':params.queryTimeBeign,
						'vo.queryTimeEnd':params.queryTimeEnd
					},
					isUpload: true,
					success:function(response){
						
					},
					exception : function(response) {
						top.Ext.MessageBox.alert(backupquery.querysortingscan.i18n('foss.unload.querysortingscan.expertFailureAlertInfo')/*'导出失败'*/,result.message);
					}
				});
			}
		}]
	}]
	
});
Ext.define('Foss.Unload.QuerySortingScan.SortingScanModel',{
	extend: 'Ext.data.Model',
	fields: [
	    {name: 'id',type :'string'},
	    {name: 'wayBillNo',type :'string'},
		{name: 'serialNo',type :'string'},
		{name: 'scanTime',type :'date',convert :dateConvert},
		{name: 'deviceNo',type :'string'},
		{name: 'scanType',type :'string',
			convert: function(value) {
				if (value == 'UP') {					
					return backupquery.querysortingscan.i18n('foss.unload.querysortingscan.up');//'上分拣扫描';
				} else{
					return backupquery.querysortingscan.i18n('foss.unload.querysortingscan.down');//'下分拣扫描';
				}
			}
		},
		{name: 'scanMode',type :'string',
			convert: function(value) {
				if (value == 'BSC') {					
					return '巴枪扫描';//巴枪扫描';
				} else{
					return 'PDA扫描';//'PDA扫描';
				}
			}
		},
		{name: 'operatorCode',type :'string'},
		{name: 'orgCode',type :'string'},
		{name: 'operatorName',type :'string'},
		{name: 'orgName',type :'string'},
		{name: 'createTime',type :'date',convert :dateConvert}
	]
});
Ext.define('Foss.Unload.QuerySortingScan.SortingScanStore',{
	extend: 'Ext.data.Store',
	model: 'Foss.Unload.QuerySortingScan.SortingScanModel',
	pageSize:20,
	proxy : {
		type : 'ajax',
		url : backupquery.realPath('querySortingScan.action'),
		actionMethods : 'post',
		reader : {
			type : 'json',
			root : 'sortingScans',
			totalProperty : 'totalCount',
			successProperty: 'success'
		}
	},
	listeners: {
		beforeload : function(store, operation, eOpts) {
			var params = backupquery.querysortingscan.QueryForm.getValues();
			Ext.apply(operation, {
				params : {
				'vo.wayBillNo':params.wayBillNo,
				'vo.serialNo':params.serialNo,
				'vo.operatorCode':params.operator,
				//'vo.orgCode':params.org,
				'vo.scanType':params.scanType,
				'vo.queryTimeBegin':params.queryTimeBeign,
				'vo.queryTimeEnd':params.queryTimeEnd,
				'vo.scanMode':params.scanMode
				}
			});
		}
}
});
Ext.define('Foss.Unload.QuerySortingScan.SortingScanGrid',{
	extend: 'Ext.grid.Panel',
	frame : true,
	columnLines: true,
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	emptyText : backupquery.querysortingscan.i18n('foss.unload.querysortingscan.dataNotFind'),//'查询结果为空',
	autoScroll : true,
	collapsible : true,
	animCollapse : true,
	//height:300,
	//width: 720,
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.Unload.QuerySortingScan.SortingScanStore');
		me.bbar = Ext.create('Deppon.StandardPaging',{
			store : me.store,
			pageSize : 20,
			maximumSize : 50,
			plugins : Ext.create('Deppon.ux.PageSizePlugin', {
				sizeList : [['20', 20], ['30', 30], ['40', 40], ['50', 50]]
			})
		});
		backupquery.querysortingscan.pagingBar = me.bbar;
		me.callParent([cfg]);
	},
	columns:[
	 		{text: backupquery.querysortingscan.i18n('foss.unload.querysortingscan.wayBillNo'),//'运单号',
	 			dataIndex : 'wayBillNo'
	 		},
	 		{text: backupquery.querysortingscan.i18n('foss.unload.querysortingscan.serialNo'),//'流水号',
	 			dataIndex : 'serialNo'
	 		},
	 		{text: backupquery.querysortingscan.i18n('foss.unload.querysortingscan.scanType'),//扫描状态,
	 			dataIndex : 'scanType'
	 		},{
	 			text: backupquery.querysortingscan.i18n('foss.unload.querysortingscan.scanMode'),//扫描方式,
	 			dataIndex : 'scanMode'
	 		},
	 		{text: backupquery.querysortingscan.i18n('foss.unload.querysortingscan.operatorName'),//'操作人',
	 			dataIndex : 'operatorName',
	 			flex: 1.5
	 		},
	 		{text: backupquery.querysortingscan.i18n('foss.unload.querysortingscan.orgName'),//'操作部门',
	 			dataIndex : 'orgName',
	 			flex: 2
	 		},
	 		{text: backupquery.querysortingscan.i18n('foss.unload.querysortingscan.queryTime'),//操作时间,
	 			dataIndex : 'scanTime',
	 			xtype : 'datecolumn',
	 			format : 'Y-m-d H:i:s',
	 			flex: 2
	 		}]
});

backupquery.querysortingscan.QueryForm = Ext.create('Foss.Unload.QuerySortingScan.QueryForm');
backupquery.querysortingscan.SortingScanGrid = Ext.create('Foss.Unload.QuerySortingScan.SortingScanGrid',{id:'Foss_Unload_QuerySortingScan_SortingScanGrid_Id'});

Ext.onReady(function(){
	//console.log(unload.querysortingscan.superOrgCode);
	Ext.QuickTips.init();
	Ext.create('Ext.panel.Panel',{
		id:'T_backupquery-querysortingscanindex_content',
		frame:false,
		//style:'padding-top:10px',
		items : [backupquery.querysortingscan.QueryForm,
		         backupquery.querysortingscan.SortingScanGrid],
		renderTo: 'T_backupquery-querysortingscanindex-body'
	});
});