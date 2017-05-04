Ext.define('Foss.unload.querySubForkAreaGoods.transTypeStore', { 
	extend : 'Ext.data.Store',
	autoLoad : true,
	fields : [ {
		name : 'valueName',
		type : 'string'
	}, {
		name : 'valueCode',
		type : 'string'
	} ],
	proxy : {
	type : 'ajax',
	actionMethods : 'get',
	url : backupquery.realPath('queryProductList.action'),
	reader : {
	type : 'json',
	root : 'subForkAreaGoodsVo.productList'
		}
	}
});


/**
 * 定义查询的列模型 零担
 */
Ext.define('Foss.unload.querySubForkAreaGoods.querySubForkAreaGoodsModel', {
	extend : 'Ext.data.Model',
	//定义字段
	fields : [{
	name : 'packageNo',//包号
	type : 'string'
	},{
	name : 'wayBillNo',//运单号
	type : 'string'
	},{
		name : 'billingNum',//开单件数
		type : 'number'
	},{
		name : 'bindNum',//已绑定件数
		type : 'number'
	},{
		name : 'unScanNum',//未扫描件数
		type : 'number'
	},{
		name : 'transType',//运输性质
		type : 'string'
	},{
		name : 'nextDestination',//下一目的站
		type : 'string'
	},{
		name : 'unloadTaskNo',//卸车任务号
		type : 'string'
	},{
		name : 'createPerson',//创建人
		type : 'string'
	},{
		name : 'createPersonCode',//创建人工号
		type : 'string'
	},{
		name : 'scanTime',//创建时间
		type : 'date',
		convert: dateConvert
	}]
});

/**
 * 定义查询的列模型 快递
 */
Ext.define('Foss.unload.querySubForkAreaGoods.querySubForkAreaGoodsModelExpress', {
	extend : 'Ext.data.Model',
	//定义字段
	fields : [{
		name : 'waybillNo',//运单/包/笼号
		type : 'string'
	},{
		name : 'billingNum',//开单件数
		type : 'number'
	},{
		name : 'bindNum',//已绑定件数
		type : 'number'
	},{
		name : 'unScanNum',//未扫描件数
		type : 'number'
	},{
		name : 'transType',//运输性质
		type : 'string'
	},{
		name : 'nextDestination',//下一目的站
		type : 'string'
	},{
		name : 'unloadTaskNo',//卸车任务号
		type : 'string'
	},{
		name : 'createPerson',//创建人
		type : 'string'
	},{
		name : 'createPersonCode',//创建人工号
		type : 'string'
	},{
		name : 'scanTime',//创建时间
		type : 'date',
		convert: dateConvert
	}]
});

/**
 * 
 * 查询store 零担
 * */
Ext.define('Foss.unload.querySubForkAreaGoods.querySubForkAreaGoodsStore', {
	extend : 'Ext.data.Store',
	pageSize : 50,
	// 绑定一个模型
	model : 'Foss.unload.querySubForkAreaGoods.querySubForkAreaGoodsModel',
	// 定义一个代理对象
	proxy : {
		type : 'ajax',
		actionMethods:'POST',
		// 请求的url
		url :backupquery.realPath('querySubForkAreaGoods.action'),
		reader : {
			// 以JSON的方式读取
			type : 'json',
			// 定义读取JSON数据的根对象
			root : 'subForkAreaGoodsVo.subForkAreaGoodsEntityList',
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
			
			var queryParams = backupquery.querySubForkAreaGoods.QueryForm.getForm().getValues();
			Ext.apply(operation, {
				params : {
							'subForkAreaGoodsVo.wayBllNo' : queryParams.wayBllNo,//运单号
							'subForkAreaGoodsVo.transType' : queryParams.transType,//运输性质
							'subForkAreaGoodsVo.nextDestination' : queryParams.nextDestination,//车牌号
							'subForkAreaGoodsVo.unloadTaskNo' : queryParams.unloadTaskNo,//卸车编号
							'subForkAreaGoodsVo.nextDestination':queryParams.nextDestination,//下一目的站
							'subForkAreaGoodsVo.createBeginTime' : Ext.Date.parse(queryParams.createBeginTime,'Y-m-d H:i:s'),//创建卸车任务开始时间
							'subForkAreaGoodsVo.createEndTime' : Ext.Date.parse(queryParams.createEndTime,'Y-m-d H:i:s')//创建卸车任务结算时间
					}
			});	
		}
	}
});

/**
 * 
 * 查询store 快递
 * */
Ext.define('Foss.unload.querySubForkAreaGoods.querySubForkAreaGoodsStoreExpress', {
	extend : 'Ext.data.Store',
	pageSize : 50,
	// 绑定一个模型
	model : 'Foss.unload.querySubForkAreaGoods.querySubForkAreaGoodsModelExpress',
	// 定义一个代理对象
	proxy : {
		type : 'ajax',
		actionMethods:'POST',
		// 请求的url
		url :backupquery.realPath('querySubForkAreaGoodsExpress.action'),
		reader : {
			// 以JSON的方式读取
			type : 'json',
			// 定义读取JSON数据的根对象
			root : 'subForkAreaGoodsVo.subForkAreaGoodsExpressEntityList',
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
			
			var queryParams = backupquery.querySubForkAreaGoods.QueryFormExpress.getForm().getValues();
			Ext.apply(operation, {
				params : {
							'subForkAreaGoodsVo.wayBllNo' : queryParams.wayBllNo,//运单号
							'subForkAreaGoodsVo.transType' : queryParams.transType,//运输性质
							'subForkAreaGoodsVo.nextDestination' : queryParams.nextDestination,//车牌号
							'subForkAreaGoodsVo.unloadTaskNo' : queryParams.unloadTaskNo,//卸车编号
							'subForkAreaGoodsVo.nextDestination':queryParams.nextDestination,//下一目的站
							'subForkAreaGoodsVo.createBeginTime' : Ext.Date.parse(queryParams.createBeginTime,'Y-m-d H:i:s'),//创建卸车任务开始时间
							'subForkAreaGoodsVo.createEndTime' : Ext.Date.parse(queryParams.createEndTime,'Y-m-d H:i:s')//创建卸车任务结算时间
					}
			});	
		}
	}
});

/**
 *查询数据的grid 零担
 **/
Ext.define('Foss.unload.querySubForkAreaGoods.QueryGrid', {
 extend : 'Ext.grid.Panel',
	frame : true,
	columnLines: true,
	title : backupquery.querySubForkAreaGoods.i18n('foss.unload.querySubForkAreaGoods.queryGrid.title'),//待叉区货物明细列表
	height:500,
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	emptyText : backupquery.querySubForkAreaGoods.i18n('foss.unload.querySubForkAreaGoods.queryGrid.noData'),//暂无数据
	constructor: function(config){
		var me = this;
		cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.unload.querySubForkAreaGoods.querySubForkAreaGoodsStore');
		me.bbar = Ext.create('Deppon.StandardPaging',{
			store : me.store,
			pageSize : 50,
			maximumSize : 50,
			plugins : Ext.create('Deppon.ux.PageSizePlugin', {
				sizeList : [['20', 20], ['30', 30], ['50', 50]]
			})
		});	
		backupquery.querySubForkAreaGoods.pagingBar = me.bbar;
		me.callParent([ cfg ]);
	},
	columns:[{
			header:'包号',//包号
			dataIndex: 'packageNo'
			},{
			header:backupquery.querySubForkAreaGoods.i18n("foss.unload.querySubForkAreaGoods.queryGrid.wayBillNo"),//运单号
			dataIndex: 'wayBillNo'
			},{
			header:backupquery.querySubForkAreaGoods.i18n("foss.unload.querySubForkAreaGoods.queryGrid.billingNum"),//开单件数
			dataIndex: 'billingNum'
			},{
			header:backupquery.querySubForkAreaGoods.i18n("foss.unload.querySubForkAreaGoods.queryGrid.bindNum"),//已绑定件数
			dataIndex: 'bindNum'
			},{
			header:backupquery.querySubForkAreaGoods.i18n("foss.unload.querySubForkAreaGoods.queryGrid.unScanNum"),//未扫描件数
			dataIndex: 'unScanNum'
			},{
			header:backupquery.querySubForkAreaGoods.i18n("foss.unload.querySubForkAreaGoods.queryGrid.transType"),//运输性质
			dataIndex: 'transType'
			},{
			header:backupquery.querySubForkAreaGoods.i18n("foss.unload.querySubForkAreaGoods.queryGrid.nextDestination"),//下一目的站
			dataIndex: 'nextDestination'
			},{
			header:backupquery.querySubForkAreaGoods.i18n("foss.unload.querySubForkAreaGoods.queryGrid.unloadTaskNo"),//卸车任务号
			dataIndex: 'unloadTaskNo'
			},{
			header:backupquery.querySubForkAreaGoods.i18n("foss.unload.querySubForkAreaGoods.queryGrid.createPerson"),//创建人
			dataIndex: 'createPerson'
			},{
			header:backupquery.querySubForkAreaGoods.i18n("foss.unload.querySubForkAreaGoods.queryGrid.createPersonCode"),//创建人工号
			dataIndex: 'createPersonCode'
			},{
			header:backupquery.querySubForkAreaGoods.i18n("foss.unload.querySubForkAreaGoods.queryGrid.scanTime"),//扫描时间
			dataIndex: 'scanTime',
//			renderer:function(value){
//				if(value!=null){
//					return Ext.Date.format(new Date(value.getFullYear(),value.getMonth(), 
//						value.getDate()), 'Y-m-d H:i:s');
//				}
//		
//			}
			xtype : 'datecolumn',
			width:150,
			format : 'Y-m-d H:i:s'
			}]
 });

/**
 *查询数据的grid  快递
 **/
Ext.define('Foss.unload.querySubForkAreaGoods.QueryGridExpress', {
 extend : 'Ext.grid.Panel',
	frame : true,
	columnLines: true,
	title : backupquery.querySubForkAreaGoods.i18n('foss.unload.querySubForkAreaGoods.queryGrid.title'),//待叉区货物明细列表
	height:500,
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	emptyText : backupquery.querySubForkAreaGoods.i18n('foss.unload.querySubForkAreaGoods.queryGrid.noData'),//暂无数据
	constructor: function(config){
		var me = this;
		cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.unload.querySubForkAreaGoods.querySubForkAreaGoodsStoreExpress');
		me.bbar = Ext.create('Deppon.StandardPaging',{
			store : me.store,
			pageSize : 50,
			maximumSize : 50,
			plugins : Ext.create('Deppon.ux.PageSizePlugin', {
				sizeList : [['20', 20], ['30', 30], ['50', 50]]
			})
		});	
		backupquery.querySubForkAreaGoods.pagingBarExpress = me.bbar;
		me.callParent([ cfg ]);
	},
	columns:[{
			header:backupquery.querySubForkAreaGoods.i18n("foss.unload.querySubForkAreaGoods.queryGrid.number"),//运单/包/笼号
			dataIndex: 'waybillNo'
			},{
			header:backupquery.querySubForkAreaGoods.i18n("foss.unload.querySubForkAreaGoods.queryGrid.billingNum"),//开单件数
			dataIndex: 'billingNum'
			},{
			header:backupquery.querySubForkAreaGoods.i18n("foss.unload.querySubForkAreaGoods.queryGrid.bindNum"),//已绑定件数
			dataIndex: 'bindNum'
			},{
			header:backupquery.querySubForkAreaGoods.i18n("foss.unload.querySubForkAreaGoods.queryGrid.unScanNum"),//未扫描件数
			dataIndex: 'unScanNum'
			},{
			header:backupquery.querySubForkAreaGoods.i18n("foss.unload.querySubForkAreaGoods.queryGrid.transType"),//运输性质
			dataIndex: 'transType'
			},{
			header:backupquery.querySubForkAreaGoods.i18n("foss.unload.querySubForkAreaGoods.queryGrid.nextDestination"),//下一目的站
			dataIndex: 'nextDestination'
			},{
			header:backupquery.querySubForkAreaGoods.i18n("foss.unload.querySubForkAreaGoods.queryGrid.unloadTaskNo"),//卸车任务号
			dataIndex: 'unloadTaskNo'
			},{
			header:backupquery.querySubForkAreaGoods.i18n("foss.unload.querySubForkAreaGoods.queryGrid.createPerson"),//创建人
			dataIndex: 'createPerson'
			},{
			header:backupquery.querySubForkAreaGoods.i18n("foss.unload.querySubForkAreaGoods.queryGrid.createPersonCode"),//创建人工号
			dataIndex: 'createPersonCode'
			},{
			header:backupquery.querySubForkAreaGoods.i18n("foss.unload.querySubForkAreaGoods.queryGrid.scanTime"),//扫描时间
			dataIndex: 'scanTime',
//			renderer:function(value){
//				if(value!=null){
//					return Ext.Date.format(new Date(value.getFullYear(),value.getMonth(), 
//						value.getDate()), 'Y-m-d H:i:s');
//				}
//		
//			}
			xtype : 'datecolumn',
			width:150,
			format : 'Y-m-d H:i:s'
			}]
 });
 
/**
 *查询的form 零担
 ***/

Ext.define('Foss.unload.querySubForkAreaGoods.QueryForm',{
	extend: 'Ext.form.Panel',
	layout: 'column',
	frame: true,
	border: false,
	title :backupquery.querySubForkAreaGoods.i18n("foss.unload.querySubForkAreaGoods.queryForm.title"), 	
	fieldDefaults: {
		msgTarget: 'side',
		labelWidth: 75
	},
	layout:'column',
	defaults : {
		margin : '5 5 5 0',
		columns : 3
	},
	items: [{
			name: 'wayBllNo',
			fieldLabel:backupquery.querySubForkAreaGoods.i18n('foss.unload.querySubForkAreaGoods.queryGrid.wayBillNo'),//运单号
			xtype: 'textfield',
			allowBlank: true,	
			columnWidth: .25,
			autoWidth:true
			
		},{
			xtype: 'combo',
			fieldLabel: backupquery.querySubForkAreaGoods.i18n('foss.unload.querySubForkAreaGoods.queryGrid.transType'),//运输性质
			name: 'transType',
			displayField: 'valueName',
			valueField:'valueCode', 
			value: 'FLF',//默认是为精准卡航
			columnWidth:.25,
			queryMode:'local',
			triggerAction:'all',
			editable:false,
			store:Ext.create('Foss.unload.querySubForkAreaGoods.transTypeStore')
	},{
			xtype : 'dynamicorgcombselector',
			allowBlank: true,
			autoWidth:true,
			name: 'nextDestination',
			fieldLabel: backupquery.querySubForkAreaGoods.i18n('foss.unload.querySubForkAreaGoods.queryGrid.nextDestination'),//目的地
			columnWidth:.25,
			listeners:{
				'blur':function(cmp ,the,ep){
					cmp.setValue(cmp.getValue());
				}
			}
		},{
			xtype : 'textfield',
			allowBlank: true,
			autoWidth:true,
			name: 'unloadTaskNo',
			fieldLabel: backupquery.querySubForkAreaGoods.i18n('foss.unload.querySubForkAreaGoods.queryGrid.unloadTaskNo'),//卸车任务号
			columnWidth:.25
		},{
			fieldId : 'Foss_stock_QueryForm_querySubForkAreaGoodsTime_ID',
			xtype : 'rangeDateField',
			dateType : 'datetimefield_date97',
			fromName:'createBeginTime',
			fromValue:Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(), 
					new Date().getDate()-1, 00, 00, 00), 'Y-m-d H:i:s'),
			toName: 'createEndTime',
			toValue:Ext.Date.format(new Date(new Date().getFullYear(), new Date().getMonth(),
				 	new Date().getDate(), 23, 59, 59),'Y-m-d H:i:s'),
			dateRange:2,
			editable: false,
			labelWidth :120,
			fieldLabel:backupquery.querySubForkAreaGoods.i18n('foss.unload.querySubForkAreaGoods.queryForm.createDate'),//卸车任务创建时间
	    	editable:false,
			allowBlank:false,
			columnWidth: .5	
		},{
			border: 1,
			xtype:'container',
			columnWidth:1,
			defaultType:'button',
			layout:'column',
			items:[{
				text: backupquery.querySubForkAreaGoods.i18n('foss.unload.querySubForkAreaGoods.queryForm.button.reset'),//重置
				columnWidth:.08,
				handler:function(){
					var form = this.up('form').getForm();
					form.reset();
					form.findField('createBeginTime').setValue(Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(), new Date().getDate()-1, 00, 00, 00), 'Y-m-d H:i:s'));
					form.findField('createEndTime').setValue(Ext.Date.format(new Date(new Date().getFullYear(), new Date().getMonth(),new Date().getDate(), 23, 59, 59),'Y-m-d H:i:s'));
					
				}
			},{
				xtype: 'container',
				columnWidth:.835,
				html: '&nbsp;'
			},{
				text: backupquery.querySubForkAreaGoods.i18n('foss.unload.querySubForkAreaGoods.queryForm.button.query'),//查询
				disabled: !backupquery.querySubForkAreaGoods.isPermission('unload/querySubForkAreaGoodsButton'),
				hidden: !backupquery.querySubForkAreaGoods.isPermission('unload/querySubForkAreaGoodsButton'),
				cls:'yellow_button',
				columnWidth:.08,
				handler:function(){
					var form = this.up('form').getForm();
					if(form.isValid()){
						backupquery.querySubForkAreaGoods.pagingBar.moveFirst();
					}
				}
			}]
		}],
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

/**
 *查询的form 快递
 ***/

Ext.define('Foss.unload.querySubForkAreaGoods.QueryFormExpress',{
	extend: 'Ext.form.Panel',
	layout: 'column',
	frame: true,
	border: false,
	title :backupquery.querySubForkAreaGoods.i18n("foss.unload.querySubForkAreaGoods.queryForm.title"), 	
	fieldDefaults: {
		msgTarget: 'side',
		labelWidth: 75
	},
	layout:'column',
	defaults : {
		margin : '5 5 5 0',
		columns : 3
	},
	items: [{
			name: 'wayBllNo',
			fieldLabel:backupquery.querySubForkAreaGoods.i18n('foss.unload.querySubForkAreaGoods.queryGrid.wayBillNo'),//运单号
			xtype: 'textfield',
			allowBlank: true,	
			columnWidth: .25,
			autoWidth:true
			
		},{
			xtype: 'combo',
			fieldLabel: backupquery.querySubForkAreaGoods.i18n('foss.unload.querySubForkAreaGoods.queryGrid.transType'),//产品类型
			name: 'transType',
			displayField: 'valueName',
			valueField:'valueCode', 
			value: 'RCP',//默认是为3.60特惠件
			columnWidth:.25,
			queryMode:'local',
			triggerAction:'all',
			editable:false,
			store:Ext.create('Foss.unload.querySubForkAreaGoods.transTypeStore')
	},{
			xtype : 'dynamicorgcombselector',
			allowBlank: true,
			autoWidth:true,
			name: 'nextDestination',
			fieldLabel: backupquery.querySubForkAreaGoods.i18n('foss.unload.querySubForkAreaGoods.queryGrid.nextDestination'),//目的地
			columnWidth:.25,
			listeners:{
				'blur':function(cmp ,the,ep){
					cmp.setValue(cmp.getValue());
				}
			}
		},{
			xtype : 'textfield',
			allowBlank: true,
			autoWidth:true,
			name: 'unloadTaskNo',
			fieldLabel: backupquery.querySubForkAreaGoods.i18n('foss.unload.querySubForkAreaGoods.queryGrid.unloadTaskNo'),//卸车任务号
			columnWidth:.25
		},{
			fieldId : 'Foss_stock_QueryForm_querySubForkAreaGoodsTime_ID_Express',
			xtype : 'rangeDateField',
			dateType : 'datetimefield_date97',
			fromName:'createBeginTime',
			fromValue:Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(), 
					new Date().getDate()-1, 00, 00, 00), 'Y-m-d H:i:s'),
			toName: 'createEndTime',
			toValue:Ext.Date.format(new Date(new Date().getFullYear(), new Date().getMonth(),
				 	new Date().getDate(), 23, 59, 59),'Y-m-d H:i:s'),
			dateRange:2,
			editable: false,
			labelWidth :120,
			fieldLabel:backupquery.querySubForkAreaGoods.i18n('foss.unload.querySubForkAreaGoods.queryForm.createDate'),//卸车任务创建时间
	    	editable:false,
			allowBlank:false,
			columnWidth: .5	
		},{
			border: 1,
			xtype:'container',
			columnWidth:1,
			defaultType:'button',
			layout:'column',
			items:[{
				text: backupquery.querySubForkAreaGoods.i18n('foss.unload.querySubForkAreaGoods.queryForm.button.reset'),//重置
				columnWidth:.08,
				handler:function(){
					var form = this.up('form').getForm();
					form.reset();
					form.findField('createBeginTime').setValue(Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(), new Date().getDate()-1, 00, 00, 00), 'Y-m-d H:i:s'));
					form.findField('createEndTime').setValue(Ext.Date.format(new Date(new Date().getFullYear(), new Date().getMonth(),new Date().getDate(), 23, 59, 59),'Y-m-d H:i:s'));
					
				}
			},{
				xtype: 'container',
				columnWidth:.835,
				html: '&nbsp;'
			},{
				text: backupquery.querySubForkAreaGoods.i18n('foss.unload.querySubForkAreaGoods.queryForm.button.query'),//查询
				disabled: !backupquery.querySubForkAreaGoods.isPermission('unload/querySubForkAreaGoodsButton'),
				hidden: !backupquery.querySubForkAreaGoods.isPermission('unload/querySubForkAreaGoodsButton'),
				cls:'yellow_button',
				columnWidth:.08,
				handler:function(){
					var form = this.up('form').getForm();
					if(form.isValid()){
						backupquery.querySubForkAreaGoods.pagingBarExpress.moveFirst();
					}
				}
			}]
		}],
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});


var queryForm = Ext.create('Foss.unload.querySubForkAreaGoods.QueryForm');
var queryGrid = Ext.create('Foss.unload.querySubForkAreaGoods.QueryGrid');
var queryFormExpress = Ext.create('Foss.unload.querySubForkAreaGoods.QueryFormExpress');
var queryGridExpress = Ext.create('Foss.unload.querySubForkAreaGoods.QueryGridExpress');
backupquery.querySubForkAreaGoods.QueryForm=queryForm;
backupquery.querySubForkAreaGoods.QueryGrid=queryGrid;
backupquery.querySubForkAreaGoods.QueryFormExpress=queryFormExpress;
backupquery.querySubForkAreaGoods.QueryGridExpress=queryGridExpress;
Ext.onReady(function() {
	Ext.QuickTips.init();	
	Ext.create('Ext.panel.Panel',{
	id:'T_backupquery-querySubForkAreaGoodsindex_content',
	cls:'panelContent',
	bodyCls:'panelContent-body',
	layout:'auto',
	getQueryForm : function() {
			return queryForm;
		},
		//获得查询结果GRID
		getQueryGrid : function() {
			return queryGrid;
		},
	margin:'0 0 0 0',
	items : [{
		xtype : 'tabpanel',
		frame : false,
		bodyCls : 'autoHeight',
		cls : 'innerTabPanel',
		activeTab : 0,
		items:[{
			//零担
			title : backupquery.querySubForkAreaGoods.i18n('foss.unload.querySubForkAreaGoods.personCount'),
			tabConfig : {
				width : 120
			},
			itemId : 'TemporaryAssignments',
			items : [queryForm,queryGrid]
	     }, {
	    	//快递
			title : backupquery.querySubForkAreaGoods.i18n('foss.unload.querySubForkAreaGoods.teamCountPanel'),
			tabConfig : {
				width : 120
			},
			itemId : 'TaskAssignments',
			items : [queryFormExpress,queryGridExpress]
		 }]
	}],
	renderTo: 'T_backupquery-querySubForkAreaGoodsindex-body'
	});	
});

