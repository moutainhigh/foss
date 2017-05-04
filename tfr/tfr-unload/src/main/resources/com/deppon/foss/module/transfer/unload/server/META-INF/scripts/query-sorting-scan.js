//定义方法，生成查询条件中“交接时间”的起始和结束时间
unload.querysortingscan.getQueryTime = function(isBegin){
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

/**分拣扫描与库存比对结果model*/
Ext.define('Foss.UnLoad.QuerySortingScanCompare.CompareModel',{
	extend : 'Ext.data.Model',
	fields : [{
		name : 'waybillNo',
		type : 'string'
	},{
		name : 'goodsAreaCode',
		type : 'string'
	},{
		name : 'goodsAreaName',
		type : 'string'
	},{
		name : 'stockGoodsQty',
		type : 'number'
	},{
		name : 'goodsQtyTotal',
		type : 'number'
	},{
		name : 'billTime',
		type : 'date',
		convert: dateConvert
	},{
		name : 'inStockTime',
		type : 'date',
		convert: dateConvert
	},{
		name : 'productCode',
		type : 'string'
	},{
		name : 'productName',
		type : 'string'
	},{
		name : 'receiveMethod',
		type : 'string'
	},{
		name : 'goodsWeightTotal',
		type : 'number'
	}]
});

/**分拣扫描与库存比对结果store*/
Ext.define('Foss.UnLoad.QuerySortingScanCompare.CompareStore',{
	extend : 'Ext.data.Store',
	pageSize : 20,
	model : 'Foss.UnLoad.QuerySortingScanCompare.CompareModel',
	// 定义一个代理对象
	proxy : {
		type : 'ajax',
		actionMethods:'POST',
		// 请求的url
		url : unload.realPath('querySortingScanCompare.action'),
		// 定义一个读取器
		reader : {
			// 以JSON的方式读取
			type : 'json',
			// 定义读取JSON数据的根对象
			root : 'vo.sortingScanCompareDtoList',
			successProperty: 'success',
			totalProperty : 'totalCount'
		}
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	},
	listeners : {
		'beforeload' : function(store, operation, eOpts){
			var queryParams = unload.querysortingscan.QueryForm.getForm().getValues();
			Ext.apply(operation, {
				params : {
				            'vo.sortingScanDto.waybillNo':queryParams.wayBillNo,
							'vo.sortingScanDto.serialNo':queryParams.serialNo,
							'vo.sortingScanDto.operatorCode':queryParams.operator,
							'vo.sortingScanDto.scanType':queryParams.scanType,
							'vo.sortingScanDto.queryTimeBegin':queryParams.queryTimeBeign,
							'vo.sortingScanDto.queryTimeEnd':queryParams.queryTimeEnd
				}
			});	
		},
		//此监听用于支持翻页勾选
		'load' : function( store, records, successful, eOpts){
			var record;
			for(var i in records){
				record = records[i];
				
				var receiveMethod= record.get('receiveMethod');
				if(!Ext.isEmpty(receiveMethod)){
					var regex = /(DELIVER)+/g.test(receiveMethod);//是否为派送
					if(regex){
						record.set('receiveMethod','派送');
					}else{
						record.set('receiveMethod','自提');
					}
				}
			}
		}
	}
});

//定义分拣扫描与库存数据比对结果列表
Ext.define('Foss.UnLoad.QuerySortingScanCompare.CompareGrid', {
	extend : 'Ext.grid.Panel',
	frame : true,
	columnLines: true,
	title : unload.querysortingscan.i18n('foss.unload.querysortingscan.CompareGrid.compareStockResult'),/*比对当前库存结果*/
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	emptyText : unload.querysortingscan.i18n('foss.unload.querysortingscan.CompareGrid.resultEmptyText'),
	autoScroll : true,
	collapsible : true,
	animCollapse : true,
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config)
		handOverBillNos = new Array();
		me.store = Ext.create('Foss.UnLoad.QuerySortingScanCompare.CompareStore');
		me.bbar = Ext.create('Deppon.StandardPaging',{
			store : me.store,
			pageSize : 20,
			maximumSize : 50,
			plugins : Ext.create('Deppon.ux.PageSizePlugin', {
				sizeList : [['20', 20], ['30', 30], ['40', 40], ['50', 50]]
			})
		});
	unload.querysortingscan.pagingBar = me.bbar;
	me.callParent([cfg]);
 },
	columns : [{
		dataIndex : 'waybillNo',
		align : 'center',
		width : 80,
		xtype : 'ellipsiscolumn',
		text : unload.querysortingscan.i18n('foss.unload.querysortingscan.CompareGrid.waybillNoColumn')
	},{
		dataIndex: 'goodsAreaName',
		align : 'center',
		width : 150,
		text : unload.querysortingscan.i18n('foss.unload.querysortingscan.CompareGrid.goodsAreaNameColumn')
	}, {
		dataIndex : 'stockGoodsQty',
		align : 'center',
		width : 80,
		text : unload.querysortingscan.i18n('foss.unload.querysortingscan.CompareGrid.stockGoodsQtyColumn')
	},{
		dataIndex : 'goodsQtyTotal',
		align : 'center',
		width : 80,
		text : unload.querysortingscan.i18n('foss.unload.querysortingscan.CompareGrid.goodsQtyTotalColumn')
	}, {
		dataIndex : 'billTime',
		align : 'center',
		width : 136,
		text : unload.querysortingscan.i18n('foss.unload.querysortingscan.CompareGrid.billTimeColumn'),
		xtype : 'datecolumn',
		format : 'Y-m-d H:i:s'
	}, {
		dataIndex : 'inStockTime',
		align : 'center',
		width : 136,
		text : unload.querysortingscan.i18n('foss.unload.querysortingscan.CompareGrid.inStockTimeColumn'),
		xtype : 'datecolumn',
		format : 'Y-m-d H:i:s'
	}, {
		dataIndex : 'productName',
		align : 'center',
		width : 150,
		text : unload.querysortingscan.i18n('foss.unload.querysortingscan.CompareGrid.productNameColumn')
	}, {
		dataIndex : 'receiveMethod',
		align : 'center',
		width : 136,
		text : unload.querysortingscan.i18n('foss.unload.querysortingscan.CompareGrid.receiveMethodColumn')
	}, {
		dataIndex : 'goodsWeightTotal',
		align : 'center',
		width : 80,
		text : unload.querysortingscan.i18n('foss.unload.querysortingscan.CompareGrid.goodsWeightTotalColumn')
	}]
});



Ext.define('Foss.UnLoad.QuerySortingScan.ScanTypeStore', {
	extend:'Ext.data.Store',
    fields: ['value', 'name'],
    data : [
        {"value":"UP", "name":unload.querysortingscan.i18n('foss.unload.querysortingscan.up')},
        {"value":"DOWN", "name":unload.querysortingscan.i18n('foss.unload.querysortingscan.down')}/*,
        {"value":"", "name":unload.querysortingscan.i18n('foss.unload.querysortingscan.all')}*/
    ]
});
Ext.define('Foss.UnLoad.QuerySortingScan.ScanModeStore', {
	extend:'Ext.data.Store',
    fields: ['value', 'name'],
    data : [
        {"value":"PDA", "name":unload.querysortingscan.i18n('foss.unload.querysortingscan.pda')},
        {"value":"BSC", "name":unload.querysortingscan.i18n('foss.unload.querysortingscan.bsc')},
        {"value":"ALL", "name":unload.querysortingscan.i18n('foss.unload.querysortingscan.all')}
    ]
});


Ext.define('Foss.Unload.QuerySortingScan.QueryForm',{
	extend: 'Ext.form.Panel',
	layout: 'column',
	//width: 490,
	frame: true,
	border: false,
	title : unload.querysortingscan.i18n('foss.unload.querysortingscan.queryCondition'),//'查询条件',
	defaults: {
		margin: '5 3 7 3',
		labelWidth: 80
	},
	items: [{
		xtype: 'textfield',
		fieldLabel: unload.querysortingscan.i18n('foss.unload.querysortingscan.wayBillNo'),//'运单号',
		name: 'wayBillNo',
		columnWidth:.25
	},{
		xtype: 'textfield',
		fieldLabel: unload.querysortingscan.i18n('foss.unload.querysortingscan.serialNo'),//'流水号',
		name: 'serialNo',
		columnWidth:.25
	},{
		name : 'operator',
		fieldLabel: unload.querysortingscan.i18n('foss.unload.querysortingscan.operatorName'),//'操作人',
		xtype : 'commonemployeeselector',
		parentOrgCode : unload.querysortingscan.superOrgCode,
		displayField : 'empName',// 显示名称
		valueField : 'empCode',// 值
		columnWidth : .25
	}/*,{
		fieldLabel : unload.querysortingscan.i18n('foss.unload.querysortingscan.orgName'),//'操作部门',
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
		fieldLabel: unload.querysortingscan.i18n('foss.unload.querysortingscan.scanType'),//'扫描类型',
		store: Ext.create('Foss.UnLoad.QuerySortingScan.ScanTypeStore'),
		queryMode: 'local',
		displayField: 'name',
		valueField: 'value',
		value : 'UP',
		columnWidth:.25
	},{
		xtype : 'rangeDateField',
		fieldLabel : unload.querysortingscan.i18n('foss.unload.querysortingscan.queryTime'),//操作时间,
		columnWidth : .5,
		// 类型为datetimefield_date97需要配置fieldId,可以赋予此属性任何唯一标 //识的String值
		fieldId : 'Foss_Unload_QuerySortingScan_QueryForm_QueryTime_ID',
		dateType: 'datetimefield_date97',
		//dateType: 'datefield',
		dateRange : 1,
		fromName : 'queryTimeBeign',
		fromValue : Ext.Date.format(unload.querysortingscan.getQueryTime(true), 'Y-m-d H:i:s'),
		toValue : Ext.Date.format(unload.querysortingscan.getQueryTime(false), 'Y-m-d H:i:s'),
		toName : 'queryTimeEnd',
		allowBlank : false,
		disallowBlank : true
	},{
		xtype: 'combo',
		name: 'scanMode',
		editable :false,
		fieldLabel: unload.querysortingscan.i18n('foss.unload.querysortingscan.scanMode'),//'扫描方式',
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
			text:unload.querysortingscan.i18n('foss.unload.querysortingscan.reset'),//'重置',
			columnWidth:.08,
			handler:function(){
				this.up('form').getForm().reset();
				//重新初始化查询时间
				this.up('form').getForm().findField('queryTimeBeign').setValue(Ext.Date.format(unload.querysortingscan.getQueryTime(true), 'Y-m-d H:i:s'));
				this.up('form').getForm().findField('queryTimeEnd').setValue(Ext.Date.format(unload.querysortingscan.getQueryTime(false), 'Y-m-d H:i:s'));
			
			}
		},{
			xtype: 'container',
			border : false,
			columnWidth:.76,
			html: '&nbsp;'
		},{
			text:unload.querysortingscan.i18n('foss.unload.querysortingscan.query'),//'查询',
			cls:'yellow_button',
			columnWidth:.08,
			disabled : !unload.querysortingscan.isPermission('unload/querysortingscanButton'),
			hidden : !unload.querysortingscan.isPermission('unload/querysortingscanButton'),
			handler:function(){
				if(this.up('form').getForm().isValid()){
					var params = this.up('form').getForm().getValues();
					Ext.getCmp('Foss_Unload_QuerySortingScan_SortingScanGrid_Id').store.load({
						params : {
							'vo.wayBillNo':params.wayBillNo,
							'vo.serialNo':params.serialNo,
							'vo.operatorCode':params.operator,
							//'vo.orgCode':params.org,
							'vo.scanType':params.scanType,
							'vo.queryTimeBegin':params.queryTimeBeign,
							'vo.queryTimeEnd':params.queryTimeEnd,
							'vo.scanMode':params.scanMode
						}/*,
						callback : function(records, operation, success) {
							if (success == false) {
								var errorMessage = operation.request.proxy.reader.jsonData.message;
								Ext.Msg.alert(unload.querysortingscan.i18n('foss.unload.querysortingscan.alert')//提示
										,errorMessage);
								Ext.getCmp('Foss_Unload_QuerySortingScan_SortingScanGrid_Id').store.removeAll();
							}
						}*/
					});
					unload.querysortingscan.pagingBar.moveFirst();
			}
			}
		},{
			xtype : 'button',
			text : unload.querysortingscan.i18n('foss.unload.querysortingscan.expertExcelButtonText'),//'导出'
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
					url : unload.realPath('exportSortingScanToExcel.action'),
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
						top.Ext.MessageBox.alert(unload.querysortingscan.i18n('foss.unload.querysortingscan.expertFailureAlertInfo')/*'导出失败'*/,result.message);
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
					return unload.querysortingscan.i18n('foss.unload.querysortingscan.up');//'上分拣扫描';
				} else{
					return unload.querysortingscan.i18n('foss.unload.querysortingscan.down');//'下分拣扫描';
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
	pageSize:10,
	proxy : {
		type : 'ajax',
		url : unload.realPath('querySortingScan.action'),
		actionMethods : 'post',
		reader : {
			type : 'json',
			root : 'sortingScans',
			//totalProperty : 'totalCount',
			successProperty: 'success'
		}
	}
});
Ext.define('Foss.Unload.QuerySortingScan.SortingScanGrid',{
	extend: 'Ext.grid.Panel',
	emptyText : unload.querysortingscan.i18n('foss.unload.querysortingscan.dataNotFind'),//'查询结果为空',
	autoScroll:true,
	frame: true,
	border: false,
	//height:300,
	//width: 720,
	columns:[
		{text: unload.querysortingscan.i18n('foss.unload.querysortingscan.wayBillNo'),//'运单号',
			dataIndex : 'wayBillNo'
		},
		{text: unload.querysortingscan.i18n('foss.unload.querysortingscan.serialNo'),//'流水号',
			dataIndex : 'serialNo'
		},
		{text: unload.querysortingscan.i18n('foss.unload.querysortingscan.scanType'),//扫描状态,
			dataIndex : 'scanType'
		},{
			text: unload.querysortingscan.i18n('foss.unload.querysortingscan.scanMode'),//扫描方式,
			dataIndex : 'scanMode'
		},
		{text: unload.querysortingscan.i18n('foss.unload.querysortingscan.operatorName'),//'操作人',
			dataIndex : 'operatorName',
			flex: 1.5
		},
		{text: unload.querysortingscan.i18n('foss.unload.querysortingscan.orgName'),//'操作部门',
			dataIndex : 'orgName',
			flex: 2
		},
		{text: unload.querysortingscan.i18n('foss.unload.querysortingscan.queryTime'),//操作时间,
			dataIndex : 'scanTime',
			xtype : 'datecolumn',
			format : 'Y-m-d H:i:s',
			flex: 2
		}
	],
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.Unload.QuerySortingScan.SortingScanStore');
		me.callParent([cfg]);
	}
});

unload.querysortingscan.QueryForm = Ext.create('Foss.Unload.QuerySortingScan.QueryForm');
unload.querysortingscan.SortingScanGrid = Ext.create('Foss.Unload.QuerySortingScan.SortingScanGrid',{id:'Foss_Unload_QuerySortingScan_SortingScanGrid_Id'});

Ext.onReady(function(){
	console.log(unload.querysortingscan.superOrgCode);
	Ext.QuickTips.init();
	Ext.create('Ext.panel.Panel',{
		id:'T_unload-querysortingscanindex_content',
		frame:false,
		//style:'padding-top:10px',
		items : [unload.querysortingscan.QueryForm,
		         unload.querysortingscan.SortingScanGrid,
		         Ext.create('Foss.UnLoad.QuerySortingScanCompare.CompareGrid')],
		renderTo: 'T_unload-querysortingscanindex-body'
	});
});