//处理状态store
Ext.define('Foss.unload.changelabelgoods.HandleStatus.Store',{
	extend: 'Ext.data.Store',
	fields: [
		{name: 'code',  type: 'string'},
		{name: 'name',  type: 'string'}
	],
	proxy: {
		type: 'memory',
		reader: {
			type: 'json',
			root: 'items'
		}
	},
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});
//库存状态store
Ext.define('Foss.unload.changelabelgoods.StockStatus.Store',{
	extend: 'Ext.data.Store',
	fields: [
	         {name: 'code',  type: 'string'},
	         {name: 'name',  type: 'string'}
	         ],
	         proxy: {
	        	 type: 'memory',
	        	 reader: {
	        		 type: 'json',
	        		 root: 'items'
	        	 }
	         },
	         constructor: function(config){
	        	 var me = this,
	        	 cfg = Ext.apply({}, config);
	        	 me.callParent([cfg]);
	         }
});

//grid的Model
Ext.define('Foss.unload.changelabelgoods.Model', {
			extend : 'Ext.data.Model',
			idProperty : 'id',
			fields : [{
						name : 'id',
						type : 'string',
						hiddenField : true
					}, {
						name : 'billNo',
						type : 'string'
					}, {
						name : 'waybillNo',
						type : 'string'
					}, {
						name : 'serialNo',
						type : 'string'
					}, {
						name : 'changeReason',
						type : 'string'
					}, {
						name : 'discoverTime',
						convert : dateConvert,
						type : 'date'
					}, {
						name : 'discoverTache',
						type : 'string'
					}, {
						name : 'handleStatus',
						type : 'string'
					}, {
						name : 'handleTime',
						convert : dateConvert,
						type : 'date'
					}, {
						name : 'handlerName',
						type : 'string'
					}, {
						name : 'stockStatus',
						type : 'string'
					}]
		});

//grid卸车查询的Store
Ext.define('Foss.unload.changelabelgoods.Unload.Store', {
	extend : 'Ext.data.Store',
	model : 'Foss.unload.changelabelgoods.Model',
	pageSize : 10,
	autoLoad : false,
	proxy : {
		// 以JSON的方式加载数据
		type : 'ajax',
		actionMethods : 'POST',
		url : unload.realPath('queryChangeLabelGoodsUnload.action'),
		reader : {
			type : 'json',
			root : 'changeLabelGoodsVo.changeLabelGoodsList',
			totalProperty : 'totalCount',
			successProperty : 'success'
		}
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	},
	listeners : {
		beforeload : function(store, operation, eOpts) {
			var queryParams = unload.changelabelgoods.queryUnloadPanel.getQueryUnloadForm().getForm()
					.getValues();
			Ext.apply(operation, {
				params : {
					'changeLabelGoodsVo.changeLabelGoodsDto.waybillNo' : queryParams.waybillNo,
					'changeLabelGoodsVo.changeLabelGoodsDto.billNo' : queryParams.billNo,
					'changeLabelGoodsVo.changeLabelGoodsDto.handleStatus' : queryParams.handleStatus,
					'changeLabelGoodsVo.changeLabelGoodsDto.stockStatus' : queryParams.stockStatus,
					'changeLabelGoodsVo.changeLabelGoodsDto.beginDate' : queryParams.beginDate,
					'changeLabelGoodsVo.changeLabelGoodsDto.endDate' : queryParams.endDate
				}
			});
		}
	}
});

//grid清仓查询的Store
Ext.define('Foss.unload.changelabelgoods.Stock.Store', {
	extend : 'Ext.data.Store',
	model : 'Foss.unload.changelabelgoods.Model',
	pageSize : 10,
	autoLoad : false,
	proxy : {
		// 以JSON的方式加载数据
		type : 'ajax',
		actionMethods : 'POST',
		url : unload.realPath('queryChangeLabelGoodsStock.action'),
		reader : {
			type : 'json',
			root : 'changeLabelGoodsVo.changeLabelGoodsList',
			totalProperty : 'totalCount',
			successProperty : 'success'
		}
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	},
	listeners : {
		beforeload : function(store, operation, eOpts) {
			var queryParams = unload.changelabelgoods.queryStockPanel.getQueryStockForm().getForm()
			.getValues();
			Ext.apply(operation, {
				params : {
					'changeLabelGoodsVo.changeLabelGoodsDto.waybillNo' : queryParams.waybillNo,
					'changeLabelGoodsVo.changeLabelGoodsDto.billNo' : queryParams.billNo,
					'changeLabelGoodsVo.changeLabelGoodsDto.goodsAreaCode' : queryParams.goodsAreaCode,
					'changeLabelGoodsVo.changeLabelGoodsDto.handleStatus' : queryParams.handleStatus,
					'changeLabelGoodsVo.changeLabelGoodsDto.stockStatus' : queryParams.stockStatus,
					'changeLabelGoodsVo.changeLabelGoodsDto.beginDate' : queryParams.beginDate,
					'changeLabelGoodsVo.changeLabelGoodsDto.endDate' : queryParams.endDate
				}
			});
		}
	}
});

//卸车查询结果Grid
Ext.define('Foss.unload.changelabelgoods.Unload.GridPanel', {
	extend : 'Ext.grid.Panel',
	sortableColumns : false,
	enableColumnHide : false,
	enableColumnMove : false,
	// 自动增加滚动条
	autoScroll : true,
	// 增加表格列的分割线
	columnLines : true,
	// 表格对象增加一个边框
	frame : true,
	bodyStyle : 'width:100%;',
	bodyCls: 'autoHeight',
	cls: 'autoHeight',
	height:500,
	emptyText: unload.changelabelgoods.i18n('foss.unload.changelabelgoods.emptyResult'),
	selModel : Ext.create('Ext.selection.CheckboxModel'),
	columns : [{
				xtype:'actioncolumn',
				header: unload.changelabelgoods.i18n('foss.unload.changelabelgoods.operate'),
				width : 40,
		        items: [{
	                iconCls: 'deppon_icons_print',
	                disabled: !unload.changelabelgoods.isPermission('unload/printChangeLabelGoodsAgainButton'),
	                hidden: !unload.changelabelgoods.isPermission('unload/printChangeLabelGoodsAgainButton'),
	                tooltip: unload.changelabelgoods.i18n('foss.unload.changelabelgoods.print'),
	                handler: function(grid, rowIndex, colIndex) {
	                	var record = grid.getStore().getAt(rowIndex);
	                	var form=unload.changelabelgoods.queryUnloadPanel.getQueryUnloadForm().getForm();
	                	var isPrintLogoField=form.findField("isPrintLogo");
	                	var isPrintLogoFieldValue=isPrintLogoField.getValue();
	                	if(isPrintLogoFieldValue){
	                		record.data.isPrintLogo="Y";
	                	}else{
	                		record.data.isPrintLogo="N";
	                	}
	                	unload.changelabelgoods.changelabelgoodsPrintAgain(record);
	                	unload.changelabelgoods.pagingBarUnload.moveFirst();
	                }
	            }]
			}, {
				text : unload.changelabelgoods.i18n('foss.unload.changelabelgoods.billNo'),
				dataIndex : 'billNo',
				width : 100
			}, {
				text : unload.changelabelgoods.i18n('foss.unload.changelabelgoods.waybillNo'),
				dataIndex : 'waybillNo',
				width : 120
			}, {
				text : unload.changelabelgoods.i18n('foss.unload.changelabelgoods.serialNo'),
				dataIndex : 'serialNo',
				width : 100
			}, {
				text : unload.changelabelgoods.i18n('foss.unload.changelabelgoods.changeReason'),
				dataIndex : 'changeReason',
				width : 80,
				renderer : function(value) {
					switch(value) {
						case 'BY_HAND':
							return unload.changelabelgoods.i18n('foss.unload.changelabelgoods.BYHAND');
						case 'MODIFY':
							return unload.changelabelgoods.i18n('foss.unload.changelabelgoods.MODIFY');
						default: return value;
					}
				}
			}, {
				text : unload.changelabelgoods.i18n('foss.unload.changelabelgoods.discoverTime'),
				dataIndex : 'discoverTime',
				xtype : 'datecolumn',
				format : 'Y-m-d H:i:s',
				width : 130
			}, {
				text : unload.changelabelgoods.i18n('foss.unload.changelabelgoods.discoverTache'),
				dataIndex : 'discoverTache',
				width : 60,
				renderer : function(value) {
					switch(value) {
						case 'STOCK_CHECKING':
							return unload.changelabelgoods.i18n('foss.unload.changelabelgoods.STOCKCHECKING');
						case 'UNLOAD':
							return unload.changelabelgoods.i18n('foss.unload.changelabelgoods.UNLOAD');
						default: return value;
					}
				}
			}, {
				text : unload.changelabelgoods.i18n('foss.unload.changelabelgoods.handleStatus'),
				dataIndex : 'handleStatus',
				width : 60,
				renderer : function(value) {
					switch(value) {
						case 'PROCESSED':
							return unload.changelabelgoods.i18n('foss.unload.changelabelgoods.PROCESSED');
						case 'UNTREATED':
							return unload.changelabelgoods.i18n('foss.unload.changelabelgoods.UNTREATED');
						default: return value;
					}
				}
			}, {
				text : unload.changelabelgoods.i18n('foss.unload.changelabelgoods.handleTime'),
				dataIndex : 'handleTime',
				xtype : 'datecolumn',
				format : 'Y-m-d H:i:s',
				width : 130
			}, {
				text : unload.changelabelgoods.i18n('foss.unload.changelabelgoods.handlerName'),
				dataIndex : 'handlerName',
				width : 60
			}, {
				text : unload.changelabelgoods.i18n('foss.unload.changelabelgoods.stockStatus'),
				dataIndex : 'stockStatus',
				width : 60
			}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.unload.changelabelgoods.Unload.Store');
		me.bbar = Ext.create('Deppon.StandardPaging',{
			store:me.store,
			plugins: 'pagesizeplugin'
		});
		unload.changelabelgoods.pagingBarUnload = me.bbar;
		me.callParent([cfg]);
	}
});

//清仓查询结果Grid
Ext.define('Foss.unload.changelabelgoods.Stock.GridPanel', {
	extend : 'Ext.grid.Panel',
	sortableColumns : false,
	enableColumnHide : false,
	enableColumnMove : false,
	// 自动增加滚动条
	autoScroll : true,
	// 增加表格列的分割线
	columnLines : true,
	// 表格对象增加一个边框
	frame : true,
	bodyStyle : 'width:100%;',
	bodyCls: 'autoHeight',
	cls: 'autoHeight',
	height:500,
	emptyText: unload.changelabelgoods.i18n('foss.unload.changelabelgoods.emptyResult'),
	selModel : Ext.create('Ext.selection.CheckboxModel'),
	columns : [{
		xtype:'actioncolumn',
		header: unload.changelabelgoods.i18n('foss.unload.changelabelgoods.operate'),
		width : 40,
		items: [{
			iconCls: 'deppon_icons_print',
			disabled: !unload.changelabelgoods.isPermission('unload/printChangeLabelGoodsAgainButton'),
			hidden: !unload.changelabelgoods.isPermission('unload/printChangeLabelGoodsAgainButton'),
			tooltip: unload.changelabelgoods.i18n('foss.unload.changelabelgoods.print'),
			handler: function(grid, rowIndex, colIndex) {
            	var record = grid.getStore().getAt(rowIndex);
            	var form=unload.changelabelgoods.queryStockPanel.getQueryStockForm().getForm();
            	var isPrintLogoField=form.findField("isPrintLogo");
            	var isPrintLogoFieldValue=isPrintLogoField.getValue();
            	if(isPrintLogoFieldValue){
            		record.data.isPrintLogo="Y";
            	}else{
            		record.data.isPrintLogo="N";
            	}
            	unload.changelabelgoods.changelabelgoodsPrintAgain(record);
            	unload.changelabelgoods.pagingBarStock.moveFirst();
            }
		}]
	}, {
		text : unload.changelabelgoods.i18n('foss.unload.changelabelgoods.billNo'),
		dataIndex : 'billNo',
		width : 100
	}, {
		text : unload.changelabelgoods.i18n('foss.unload.changelabelgoods.waybillNo'),
		dataIndex : 'waybillNo',
		width : 110
	}, {
		text : unload.changelabelgoods.i18n('foss.unload.changelabelgoods.serialNo'),
		dataIndex : 'serialNo',
		width : 100
	}, {
		text : unload.changelabelgoods.i18n('foss.unload.changelabelgoods.changeReason'),
		dataIndex : 'changeReason',
		width : 80,
		renderer : function(value) {
			switch(value) {
				case 'BY_HAND':
					return unload.changelabelgoods.i18n('foss.unload.changelabelgoods.BYHAND');
				case 'MODIFY':
					return unload.changelabelgoods.i18n('foss.unload.changelabelgoods.MODIFY');
				default: return value;
			}
		}
	}, {
		text : unload.changelabelgoods.i18n('foss.unload.changelabelgoods.discoverTime'),
		dataIndex : 'discoverTime',
		xtype : 'datecolumn',
		format : 'Y-m-d H:i:s',
		width : 130
	}, {
		text : unload.changelabelgoods.i18n('foss.unload.changelabelgoods.discoverTache'),
		dataIndex : 'discoverTache',
		width : 60,
		renderer : function(value) {
			switch(value) {
				case 'STOCK_CHECKING':
					return unload.changelabelgoods.i18n('foss.unload.changelabelgoods.STOCKCHECKING');
				case 'UNLOAD':
					return unload.changelabelgoods.i18n('foss.unload.changelabelgoods.UNLOAD');
				default: return value;
			}
		}
	}, {
		text : unload.changelabelgoods.i18n('foss.unload.changelabelgoods.handleStatus'),
		dataIndex : 'handleStatus',
		width : 60,
		renderer : function(value) {
			switch(value) {
				case 'PROCESSED':
					return unload.changelabelgoods.i18n('foss.unload.changelabelgoods.PROCESSED');
				case 'UNTREATED':
					return unload.changelabelgoods.i18n('foss.unload.changelabelgoods.UNTREATED');
				default: return value;
			}
		}
	}, {
		text : unload.changelabelgoods.i18n('foss.unload.changelabelgoods.handleTime'),
		dataIndex : 'handleTime',
		xtype : 'datecolumn',
		format : 'Y-m-d H:i:s',
		width : 130
	}, {
		text : unload.changelabelgoods.i18n('foss.unload.changelabelgoods.handlerName'),
		dataIndex : 'handlerName',
		width : 60
	}, {
		text : unload.changelabelgoods.i18n('foss.unload.changelabelgoods.stockStatus'),
		dataIndex : 'stockStatus',
		width : 60
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.unload.changelabelgoods.Stock.Store');
		me.bbar = Ext.create('Deppon.StandardPaging',{
			store:me.store,
			plugins: 'pagesizeplugin'
		});
		unload.changelabelgoods.pagingBarStock = me.bbar;
		me.callParent([cfg]);
	}
});

//卸车查询Form
Ext.define('Foss.unload.changelabelgoods.QueryUnloadForm', {
	extend : 'Ext.form.Panel',
	layout : 'column',
	frame : true,
	border : false,
	height : 140,
	defaultType: 'textfield',
	defaults : {
		margin : '5 5 5 5',
		columns : 4,
		labelWidth: 80
	},
	items : [{
		fieldLabel : unload.changelabelgoods.i18n('foss.unload.changelabelgoods.waybillNo'),
		name : 'waybillNo',
		columnWidth : .33
	}, {
		//交接单/配载单单号
		fieldLabel : unload.changelabelgoods.i18n('foss.unload.changelabelgoods.billNo2'),
		name : 'billNo',
		labelWidth: 120,
		columnWidth : .33
		
	}, {
		xtype: 'combobox',
		fieldLabel : unload.changelabelgoods.i18n('foss.unload.changelabelgoods.handleStatus'),
		name : 'handleStatus',
		columnWidth : .33,
		displayField: 'name',
		valueField:'code', 
		queryMode:'local',
		triggerAction:'all',
		editable:false,
		store: Ext.create('Foss.unload.changelabelgoods.HandleStatus.Store',{
			data: {
				'items':[
					{'code':'ALL','name':unload.changelabelgoods.i18n('foss.unload.changelabelgoods.ALL')},
					{'code':'PROCESSED','name':unload.changelabelgoods.i18n('foss.unload.changelabelgoods.PROCESSED')},
					{'code':'UNTREATED','name':unload.changelabelgoods.i18n('foss.unload.changelabelgoods.UNTREATED')}
				]
			}
		})
	}, {
		xtype: 'combobox',
		fieldLabel : unload.changelabelgoods.i18n('foss.unload.changelabelgoods.stockStatus'),
		name : 'stockStatus',
		columnWidth : .33,
		displayField: 'name',
		valueField:'code', 
		queryMode:'local',
		triggerAction:'all',
		editable:false,
		store: Ext.create('Foss.unload.changelabelgoods.StockStatus.Store',{
			data: {
				'items':[
					{'code':'STOCKING','name':unload.changelabelgoods.i18n('foss.unload.changelabelgoods.STOCKING')},
					{'code':'UNSTOCKING','name':unload.changelabelgoods.i18n('foss.unload.changelabelgoods.UNSTOCKING')}
				]
			}
		})
	}, {
		xtype: 'rangeDateField',
		fieldLabel: unload.changelabelgoods.i18n('foss.unload.changelabelgoods.discoverTime'),
		//类型为datetimefield_date97需要配置fieldId,可以赋予此属性任何唯一标	    //识的String值
		fieldId: 'Foss_unload_changelabelgoodsindex_discoverTime_tab1_ID',
		dateType: 'datetimefield_date97',
		//dateType: 'datefield',
		//dateType: 'timefield',
		fromName: 'beginDate',
		dateRange : 30,
		fromValue: Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()
				,'00','00','00'), 'Y-m-d H:i:s'),
		toName : 'endTime',
		toValue: Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()
				,'23','59','59'), 'Y-m-d H:i:s'),
		toName: 'endDate',
		allowBlank: false,
		disallowBlank : true,
		columnWidth: .60
	}, {
		text: unload.changelabelgoods.i18n('foss.unload.changelabelgoods.reset'),
		xtype:"button",
		columnWidth:.12,
		height:30,
		handler:function(){
			this.up('form').getForm().reset();
			//重新初始化交接时间
			this.up('form').getForm().findField('beginDate')
				.setValue(Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate(),'00','00','00'), 'Y-m-d H:i:s'));
			this.up('form').getForm().findField('endDate')
				.setValue(Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate(),'23','59','59'), 'Y-m-d H:i:s'));
		}
	}, {
		xtype : 'container',
		columnWidth : .76,
		html : '&nbsp;'
	}, {
		text: unload.changelabelgoods.i18n('foss.unload.changelabelgoods.query'),
		disabled: !unload.changelabelgoods.isPermission('unload/queryChangeLabelGoodsStockButton'),
		hidden: !unload.changelabelgoods.isPermission('unload/queryChangeLabelGoodsStockButton'),
		xtype:"button",
		cls:'yellow_button',
		columnWidth:.12,
		height:30,
		handler:function(){
			var form = this.up('form').getForm();
			if(!form.isValid()){
				return;
			}
			unload.changelabelgoods.pagingBarUnload.moveFirst();
		}
	},{
		xtype:'fieldcontainer',
		fieldLabel: '',
		columnWidth:.60,
		defaultType: 'checkboxfield',
		items:[{
		       boxLabel:'标签显示"德邦物流"',
		       name:'isPrintLogo',
		       inputValue:'Y',
		       checked: true,
		       uncheckedValue:'N'
		}]
	}]
});

//清仓查询Form
Ext.define('Foss.unload.changelabelgoods.QueryStockForm', {
	extend : 'Ext.form.Panel',
	layout : 'column',
	frame : true,
	border : false,
	height : 140,
	defaultType: 'textfield',
	defaults : {
		margin : '5 5 5 5',
		columns : 4,
		labelWidth: 80
	},
	items : [{
		fieldLabel : unload.changelabelgoods.i18n('foss.unload.changelabelgoods.waybillNo'),
		name : 'waybillNo',
		columnWidth : .25
	}, {
		//清仓任务编号
		fieldLabel : unload.changelabelgoods.i18n('foss.unload.changelabelgoods.billNo3'),
		name : 'billNo',
		labelWidth: 120,
		columnWidth : .25
		
	}, {
		fieldLabel : unload.changelabelgoods.i18n('foss.unload.changelabelgoods.goodsAreaCode'),
		xtype: 'commongoodsareaselector',
		name : 'goodsAreaCode',
		columnWidth : .25
	}, {
		xtype: 'combobox',
		fieldLabel : unload.changelabelgoods.i18n('foss.unload.changelabelgoods.handleStatus'),
		name : 'handleStatus',
		columnWidth : .25,
		displayField: 'name',
		valueField:'code', 
		queryMode:'local',
		triggerAction:'all',
		editable:false,
		store: Ext.create('Foss.unload.changelabelgoods.HandleStatus.Store',{
			data: {
				'items':[
					{'code':'ALL','name': unload.changelabelgoods.i18n('foss.unload.changelabelgoods.ALL')},
					{'code':'PROCESSED','name': unload.changelabelgoods.i18n('foss.unload.changelabelgoods.PROCESSED')},
					{'code':'UNTREATED','name': unload.changelabelgoods.i18n('foss.unload.changelabelgoods.UNTREATED')}
				]
			}
		})
	}, {
		xtype: 'combobox',
		fieldLabel : unload.changelabelgoods.i18n('foss.unload.changelabelgoods.stockStatus'),
		name : 'stockStatus',
		columnWidth : .25,
		displayField: 'name',
		valueField:'code', 
		queryMode:'local',
		triggerAction:'all',
		editable:false,
		store: Ext.create('Foss.unload.changelabelgoods.StockStatus.Store',{
			data: {
				'items':[
							{'code':'STOCKING','name':unload.changelabelgoods.i18n('foss.unload.changelabelgoods.STOCKING')},
							{'code':'UNSTOCKING','name':unload.changelabelgoods.i18n('foss.unload.changelabelgoods.UNSTOCKING')}
						]
			}
		})
	}, {
		xtype: 'rangeDateField',
		fieldLabel : unload.changelabelgoods.i18n('foss.unload.changelabelgoods.discoverTime'),
		//类型为datetimefield_date97需要配置fieldId,可以赋予此属性任何唯一标	    //识的String值
		fieldId: 'Foss_unload_changelabelgoods_discoverTime_tab2_ID',
		dateType: 'datetimefield_date97',
		//dateType: 'datefield',
		//dateType: 'timefield',
		fromName: 'beginDate',
		dateRange : 30,
		fromValue: Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()
				,'00','00','00'), 'Y-m-d H:i:s'),
		toName : 'endTime',
		toValue: Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()
				,'23','59','59'), 'Y-m-d H:i:s'),
		toName: 'endDate',
		allowBlank: false,
		disallowBlank : true,
		columnWidth: .70
	}, {
		text: unload.changelabelgoods.i18n('foss.unload.changelabelgoods.reset'),
		xtype:"button",
		columnWidth:.12,
		height:30,
		handler:function(){
			this.up('form').getForm().reset();
			//重新初始化交接时间
			this.up('form').getForm().findField('beginDate')
				.setValue(Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate(),'00','00','00'), 'Y-m-d H:i:s'));
			this.up('form').getForm().findField('endDate')
				.setValue(Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate(),'23','59','59'), 'Y-m-d H:i:s'));
		}
	}, {
		xtype : 'container',
		columnWidth : .75,
		html : '&nbsp;'
	}, {
		text: unload.changelabelgoods.i18n('foss.unload.changelabelgoods.query'),
		disabled: !unload.changelabelgoods.isPermission('unload/queryChangeLabelGoodsUnloadButton'),
		hidden: !unload.changelabelgoods.isPermission('unload/queryChangeLabelGoodsUnloadButton'),
		xtype:"button",
		cls:'yellow_button',
		columnWidth:.12,
		height:30,
		handler:function(){
			var form = this.up('form').getForm();
			if(!form.isValid()){
				return;
			}
			unload.changelabelgoods.pagingBarStock.moveFirst();
		}
	},{
		xtype:'fieldcontainer',
		fieldLabel: '',
		columnWidth:.60,
		defaultType: 'checkboxfield',
		items:[{
		       boxLabel:'标签显示"德邦物流"',
		       name:'isPrintLogo',
		       inputValue:'Y',
		       checked: true,
		       uncheckedValue:'N'
		}]
	}]
});

//卸车查询主panel
Ext.define('Foss.unload.changelabelgoods.QueryUnloadPanel', {
	extend : 'Ext.container.Container',
	frame : true,
	autoScroll : true,
	queryUnloadForm : null,
	getQueryUnloadForm : function() {
		if (this.queryUnloadForm == null) {
			this.queryUnloadForm = Ext.create('Foss.unload.changelabelgoods.QueryUnloadForm');
		}
		return this.queryUnloadForm;
	},
	unloadGridPanel : null,
	getUnloadGridPanel : function() {
		if (this.unloadGridPanel == null) {
			this.unloadGridPanel = Ext.create('Foss.unload.changelabelgoods.Unload.GridPanel');
		}
		return this.unloadGridPanel;
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.items = [me.getQueryUnloadForm(), me.getUnloadGridPanel()]
		me.callParent([cfg]);
	}
});

//清仓查询主panel
Ext.define('Foss.unload.changelabelgoods.QueryStockPanel', {
	extend : 'Ext.container.Container',
	frame : true,
	autoScroll : true,
	queryStockForm : null,
	getQueryStockForm : function() {
		if (this.queryStockForm == null) {
			this.queryStockForm = Ext.create('Foss.unload.changelabelgoods.QueryStockForm');
		}
		return this.queryStockForm;
	},
	stockGridPanel : null,
	getStockGridPanel : function() {
		if (this.stockGridPanel == null) {
			this.stockGridPanel = Ext.create('Foss.unload.changelabelgoods.Stock.GridPanel');
		}
		return this.stockGridPanel;
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.items = [me.getQueryStockForm(), me.getStockGridPanel()]
		me.callParent([cfg]);
	}
});

unload.changelabelgoods.changelabelgoodsPrintAgain = function(record) {
	var data = {
		'changeLabelGoodsVo':{
			'changeLabelGoodsDto':{
				'billNo':record.get('billNo'),
				'waybillNo':record.get('waybillNo'),
				'serialNo':record.get('serialNo'),
				'changeReason':record.get('changeReason')
			}
		}
	};
	var vurl = 'http://localhost:8077/print';
	Ext.Ajax.request({
		url: unload.realPath('printChangeLabelGoodsAgain.action'),
		jsonData: data,
		success: function(response){
			var result = Ext.decode(response.responseText),
			printDataArray = result.changeLabelGoodsVo.barcodePrintLabelList;
			for(var i=0;i<printDataArray.length;i++){
				var printData = printDataArray[i];
				Ext.data.JsonP.request({
				    url: vurl,
				    callbackKey: 'callback',
				    params: {
					     lblprtworker:"CommLabelPrintWorker",
					   	 addr1: printData.addr1,
						 addr2: printData.addr2,
						 addr3: printData.addr3,
						 addr4: printData.addr4,
						 location1: printData.location1,
						 location2: printData.location2,
						 location3: printData.location3,
						 location4: printData.location4,
						 countyRegion:printData.countyRegion,
						 optusernum: printData.optuserNum,
						 number: printData.waybillNumber,
						 serialnos: printData.printSerialnos,
						 leavecity: printData.leavecity,
						 destination: printData.destination,
						 isagent: printData.isAgent,
						 stationnumber: printData.destinationCode,
						 deptno: printData.lastTransCenterNo,
						 finaloutfieldid: printData.finaloutfieldid,
						 finaloutname: printData.lastTransCenterCity,
						 weight: printData.weight,
						 totalpieces: printData.totalPieces,
						 packing: printData.packing,
						 unusual: printData.unusual,
						 transtype: printData.transtype,
						 printdate: printData.printDate,
						 deliver: printData.deliverToDoor,
						 goodstype: printData.goodstype,
						 signFlag: printData.isStarFlag,
						 preassembly: printData.preassembly,
						 receiveBigCustomer:printData.receiveBigCustomer,
						 deliveryBigCustomer:printData.deliveryBigCustomer,
						 isNoStop:printData.isNoStop,
						 isExhibitCargo:printData.isExhibitCargo,
						 isPrintLogo:record.data.isPrintLogo
					},
				    callback: function() {
			 			//回调函数，不管请求成功与否都执行
			 			//alert("callback");
					},
				    success: function(response){
				    	var ret_code = response.data.code;
				    	if(ret_code == '1'){
				    		Ext.Ajax.request({
				    			url: unload.realPath('updateChangeLabelGoods.action'),
				    			jsonData: data,
				    			success: function(response){
				    				var activeItem = unload.changelabelgoods.queryUnloadPanel.up().up().getActiveTab().itemId;
				    				if(activeItem == 'TemporaryAssignments') {
				    					unload.changelabelgoods.pagingBarUnload.moveFirst();
				    				} else {
				    					unload.changelabelgoods.pagingBarStock.moveFirst();
				    				}
				    				Ext.ux.Toast.msg(unload.changelabelgoods.i18n('foss.unload.changelabelgoods.prompt'), unload.changelabelgoods.i18n('foss.unload.changelabelgoods.printSuccess'), 'ok', 3000);
				    			},
				    			exception: function(response){
				    	        	var result = Ext.decode(response.responseText);
							    	Ext.ux.Toast.msg(unload.changelabelgoods.i18n('foss.unload.changelabelgoods.error'), response.data.msg, 'error', 3000);
				    		    }
				    		});
				    	}else{
				    		Ext.ux.Toast.msg(unload.changelabelgoods.i18n('foss.unload.changelabelgoods.prompt'), response.data.msg, 'error', 3000);
				    	}
				    },
				    failure : function (response){
				    	var text = unload.changelabelgoods.i18n('foss.unload.changelabelgoods.printFailure');
				    	Ext.ux.Toast.msg(unload.changelabelgoods.i18n('foss.unload.changelabelgoods.error'), text);
				    }
				});
			}
		},
		exception: function(response){
        	var result = Ext.decode(response.responseText);
            Ext.Msg.alert(unload.changelabelgoods.i18n('foss.unload.changelabelgoods.error'), result.message);   
	    }
	});
}

Ext.onReady(function() {
	Ext.tip.QuickTipManager.init();
	Ext.QuickTips.init();
	
	var queryUnloadPanel = Ext.create('Foss.unload.changelabelgoods.QueryUnloadPanel');
	var queryStockPanel = Ext.create('Foss.unload.changelabelgoods.QueryStockPanel');
	
	unload.changelabelgoods.queryUnloadPanel = queryUnloadPanel;
	unload.changelabelgoods.queryStockPanel = queryStockPanel;
	
	Ext.create('Ext.panel.Panel', {
				id : 'T_unload-changelabelgoodsindex_content',
				cls : "panelContentNToolbar",
				bodyCls : 'panelContentNToolbar-body',
				layout : 'auto',
				// 定义容器中的项
				items : [{
							xtype : 'tabpanel',
							frame : false,
							bodyCls : 'autoHeight',
							cls : 'innerTabPanel',
							activeTab : 0,
							items : [{
										title : unload.changelabelgoods.i18n('foss.unload.changelabelgoods.unloadQuery'),
										tabConfig : {
											width : 120
										},
										itemId : 'TemporaryAssignments',
										items : queryUnloadPanel
									}, {
										title : unload.changelabelgoods.i18n('foss.unload.changelabelgoods.stockQuery'),
										tabConfig : {
											width : 120
										},
										itemId : 'TaskAssignments',
										items : queryStockPanel
									}]
						}],
				renderTo : 'T_unload-changelabelgoodsindex-body'
			});
});