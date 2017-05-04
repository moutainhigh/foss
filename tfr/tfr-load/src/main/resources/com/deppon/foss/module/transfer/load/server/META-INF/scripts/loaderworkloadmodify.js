//操作类型store
Ext.define('Foss.load.loaderworkloadmodify.HandleType.Store',{
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
//任务类型store
Ext.define('Foss.load.loaderworkloadmodify.TaskType.Store',{
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

//装卸车工作量Model
Ext.define('Foss.load.loaderworkloadmodify.TaskDetail.Model', {
	extend : 'Ext.data.Model',
	idProperty : 'id',
	fields : [{
				name : 'id',
				type : 'string',
				hiddenField : true
			}, {
				name : 'vehicleNo',
				type : 'string'
			}, {
				name : 'taskNo',
				type : 'string'
			}, {
				name : 'totWeight',
				type : 'string'
			}, {
				name : 'totWaybillQty',
				type : 'string'
			}, {
				name : 'totGoodsQty',
				type : 'string'
			}, {
				name : 'handleType',
				type : 'string'
			}, {
				name : 'taskType',
				type : 'string'
			}, {
				name : 'taskBeginDate',
				type : 'string'
			}, {
				name : 'goodsType',
				type : 'string'
			}]
});

//理货员工作量Model
Ext.define('Foss.load.loaderworkloadmodify.LoaderWorks.Model', {
	extend : 'Ext.data.Model',
	idProperty : 'id',
	fields : [{
				name : 'id',
				type : 'string',
				hiddenField : true
			}, {
				name : 'loaderName',
				type : 'string'
			}, {
				name : 'loaderCode',
				type : 'string'
			}, {
				name : 'loaderOrgCode',
				type : 'string'
			}, {
				name : 'loaderOrgName',
				type : 'string'
			}, {
				name : 'waybillQty',
				type : 'string'
			}, {
				name : 'weight',
				type : 'string'
			}, {
				name : 'joinTime',
				convert : dateConvert,
				type : 'date'
			}, {
				name : 'leaveTime',
				convert : dateConvert,
				type : 'date'
			}, {
				name : 'notes',
				type : 'string'
			}, {
				name : 'goodsType',
				type : 'string'
			}]
});

//装卸车工作量Store
Ext.define('Foss.load.loaderworkloadmodify.TaskDetail.Store', {
	extend : 'Ext.data.Store',
	model : 'Foss.load.loaderworkloadmodify.TaskDetail.Model',
	pageSize : 10,
	autoLoad : false,
	proxy : {
		// 以JSON的方式加载数据
		type : 'ajax',
		actionMethods : 'POST',
		url : load.realPath('queryLoaderWorkloadDetail.action'),
		reader : {
			type : 'json',
			root : 'loaderWorkloadVo.loaderWorkloadDetailList',
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
			var queryParams = load.loaderworkloadmodify.queryForm.getForm()
					.getValues();
			Ext.apply(operation, {
				params : {
					'loaderWorkloadVo.loaderWorkloadDetailDto.vehicleNo' : queryParams.vehicleNo,
					'loaderWorkloadVo.loaderWorkloadDetailDto.taskNo' : queryParams.taskNo,
					'loaderWorkloadVo.loaderWorkloadDetailDto.handleType' : queryParams.handleType,
					'loaderWorkloadVo.loaderWorkloadDetailDto.taskType' : queryParams.taskType,
					'loaderWorkloadVo.loaderWorkloadDetailDto.taskBeginDate' : queryParams.taskBeginDate
				}
			});
		}
	}
});

//理货员工作量Store 
Ext.define('Foss.load.loaderworkloadmodify.LoaderWorks.Store', {
	extend : 'Ext.data.Store',
	model : 'Foss.load.loaderworkloadmodify.LoaderWorks.Model',
	autoLoad : false,
	proxy : {
		type: 'memory',
		reader: {
			type: 'json',
			root: 'items'
		}
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

//装卸车工作量查询结果Grid
Ext.define('Foss.load.loaderworkloadmodify.TaskDetailGridPanel', {
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
	height:320,
	emptyText: load.loaderworkloadmodify.i18n('foss.load.loaderworkloadmodify.emptyResult'),
	selModel : Ext.create('Ext.selection.RadioModel'),
	columns : [{
				text : load.loaderworkloadmodify.i18n('foss.load.loaderworkloadmodify.vehicleNo'),
				dataIndex : 'vehicleNo',
				width : 100
			}, {
				text : load.loaderworkloadmodify.i18n('foss.load.loaderworkloadmodify.taskNo'),
				dataIndex : 'taskNo',
				width : 120
			}, {
				text : load.loaderworkloadmodify.i18n('foss.load.loaderworkloadmodify.totWeight'),
				dataIndex : 'totWeight',
				width : 100
			}, {
				text : load.loaderworkloadmodify.i18n('foss.load.loaderworkloadmodify.totWaybillQty'),
				dataIndex : 'totWaybillQty',
				width : 100
			}, {
				text : load.loaderworkloadmodify.i18n('foss.load.loaderworkloadmodify.totGoodsQty'),
				dataIndex : 'totGoodsQty',
				width : 100
			}, {
				text : load.loaderworkloadmodify.i18n('foss.load.loaderworkloadmodify.taskType'),
				dataIndex : 'taskType',
				width : 60,
				renderer : function(value) {
					switch(value) {
					case 'LONG_DISTANCE_LOAD':
						//长途装车
						return load.loaderworkload.i18n('foss.load.loaderworkload.LONG_LOAD');
					case 'LONG_DISTANCE':
						//长途卸车
						return load.loaderworkload.i18n('foss.load.loaderworkload.LONG_UNLOAD');
					case 'SHORT_DISTANCE_LOAD':
						//短途装车
						return load.loaderworkload.i18n('foss.load.loaderworkload.SHORT_LOAD');
					case 'SHORT_DISTANCE':
						//短途卸车
						return load.loaderworkload.i18n('foss.load.loaderworkload.SHORT_UNLOAD');
					case 'DELIVER_LOAD':
						//派送装车
						return load.loaderworkload.i18n('foss.load.loaderworkload.DELIVER_LOAD');
					case 'DELIVER':
						//集中卸车
						return load.loaderworkload.i18n('foss.load.loaderworkload.DELIVER');
					case 'PARTIALLINE_LOAD':
						//偏线装车
						return load.loaderworkload.i18n('foss.load.loaderworkload.PARTIALLINE_LOAD');
						default: return value;
					}
				}
			}, {
				//货物类型
				text : load.loaderworkloadmodify.i18n('foss.load.loaderworkload.goodsType'),
				dataIndex : 'goodsType',
				width : 60,
				renderer : function(value) {
					switch(value) {
					case 'A_TYPE':
						//A货
						return load.loaderworkloadmodify.i18n('foss.load.loaderworkload.LD');
					case 'B_TYPE':
						//B货
						return load.loaderworkloadmodify.i18n('foss.load.loaderworkload.LD');
					case 'ALL':
						//全部
						return load.loaderworkloadmodify.i18n('foss.load.loaderworkload.LD');
					default: return value;
					}
				}
			}, {
				text : load.loaderworkloadmodify.i18n('foss.load.loaderworkloadmodify.taskBeginDate'),
				dataIndex : 'taskBeginDate',
				width : 130
			}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.load.loaderworkloadmodify.TaskDetail.Store');
		me.bbar = Ext.create('Deppon.StandardPaging',{
			store:me.store,
			plugins: 'pagesizeplugin'
		});
		load.loaderworkloadmodify.pagingBar = me.bbar;
		me.callParent([cfg]);
	},
	listeners: {
		'select': function(rowModel, record, index, eOpts ) {
			var taskNo = record.get('taskNo');
			var loaderWorkloadVo = {
					'loaderWorkloadVo.loaderWorkloadDto.taskNo' : taskNo
			};
			Ext.Ajax.request({
				url : load.realPath('queryLoaderWorksByTaskNo.action'),
				params : loaderWorkloadVo,
				success : function(response) {
					var result = Ext.decode(response.responseText),
						loaderWorkloadList = result.loaderWorkloadVo.loaderWorkloadList;
					load.loaderworkloadmodify.loaderGridPanel.store.loadData(loaderWorkloadList);
				},
			    exception : function(response) {
    				var json = Ext.decode(response.responseText);
    				Ext.ux.Toast.msg(load.loaderworkloadmodify.i18n('foss.load.loaderworkloadmodify.saveFail'), json.message, 'error');
    			}
			});
		}
	}
});
//个人统计查询Form
Ext.define('Foss.load.loaderworkloadmodify.QueryForm', {
	extend : 'Ext.form.Panel',
	layout : 'column',
	frame : true,
	border : false,
	height : 50,
	defaultType: 'textfield',
	defaults : {
		margin : '5 5 5 5',
		columns : 4,
		labelWidth: 65
	},
	items : [{
		fieldLabel : load.loaderworkloadmodify.i18n('foss.load.loaderworkloadmodify.vehicleNo'),
		xtype : 'commonowntruckselector',
		name : 'vehicleNo',
		columnWidth : .18
	}, {
		fieldLabel : load.loaderworkloadmodify.i18n('foss.load.loaderworkloadmodify.taskNo'),
		name : 'taskNo',
		columnWidth : .18
	}, {
		xtype: 'combobox',
		fieldLabel : load.loaderworkloadmodify.i18n('foss.load.loaderworkloadmodify.taskType'),
		name : 'taskType',
		columnWidth : .18,
		displayField: 'name',
		valueField:'code', 
		queryMode:'local',
		triggerAction:'all',
		value:'ALL',
		editable:false,
		store: Ext.create('Foss.load.loaderworkloadmodify.TaskType.Store',{
			data: {
				'items':[
					{'code':'ALL','name':load.loaderworkloadmodify.i18n('foss.load.loaderworkloadmodify.ALL')},
					// 272681 2015/10/28 任务类型
					{'code':'LONG_DISTANCE_LOAD','name':load.loaderworkload.i18n('foss.load.loaderworkload.LONG_LOAD')}, //长途装车任务
					{'code':'LONG_DISTANCE','name':load.loaderworkload.i18n('foss.load.loaderworkload.LONG_UNLOAD')}, //长途卸车任务
					{'code':'SHORT_DISTANCE_LOAD','name':load.loaderworkload.i18n('foss.load.loaderworkload.SHORT_LOAD')}, //短途装车任务
					{'code':'SHORT_DISTANCE','name':load.loaderworkload.i18n('foss.load.loaderworkload.SHORT_UNLOAD')}, //短途卸车任务
					{'code':'DELIVER_LOAD','name':load.loaderworkload.i18n('foss.load.loaderworkload.DELIVER_LOAD')}, //派送装车任务
					{'code':'DELIVER','name':load.loaderworkload.i18n('foss.load.loaderworkload.DELIVER')}, //集中接货卸车任务
					{'code':'PARTIALLINE_LOAD','name':load.loaderworkload.i18n('foss.load.loaderworkload.PARTIALLINE_LOAD')} //偏线装车任务
				]
			}
		})
	}, {
		fieldLabel : load.loaderworkloadmodify.i18n('foss.load.loaderworkloadmodify.taskBeginDate'),
		name : 'taskBeginDate',
		xtype: 'datefield',
		format: 'Y-m-d',
		allowBlank : false,
		disallowBlank : true,
		value: Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()
				,'00','00','00'), 'Y-m-d'),
		columnWidth : .18
	}, {
		text: load.loaderworkloadmodify.i18n('foss.load.loaderworkloadmodify.query'),
		hidden: !load.loaderworkloadmodify.isPermission('load/queryLoaderWorkloadDetailButton'),
		xtype:"button",
		cls:'yellow_button',
		columnWidth:.10,
		height:30,
		handler:function(){
			var form = this.up('form').getForm();
			if(!form.isValid()){
				return;
			}
			load.loaderworkloadmodify.pagingBar.moveFirst();
			var queryParams = load.loaderworkloadmodify.queryForm.getForm().getValues();
			var param = {
				'loaderWorkloadVo.loaderWorkloadDetailDto.vehicleNo' : queryParams.vehicleNo,
				'loaderWorkloadVo.loaderWorkloadDetailDto.taskNo' : queryParams.taskNo,
				'loaderWorkloadVo.loaderWorkloadDetailDto.handleType' : queryParams.handleType,
				'loaderWorkloadVo.loaderWorkloadDetailDto.taskType' : queryParams.taskType,
				'loaderWorkloadVo.loaderWorkloadDetailDto.taskBeginDate' : queryParams.taskBeginDate,
			};
			Ext.Ajax.request({
				url : load.realPath('querySummaryDetail.action'),
				params : param,
				success : function(response) {
					var result = Ext.decode(response.responseText),
						dto = result.loaderWorkloadVo.loaderWorkloadDetailDto,
						form = load.loaderworkloadmodify.taskDetailBottomForm.getForm();
					if(dto == null) {
						return;
					}
					form.findField('totWeight').setValue(dto.totWeight);
					form.findField('totWaybillQty').setValue(dto.totWaybillQty);
					form.findField('totGoodsQty').setValue(dto.totGoodsQty);
				}
			});
		}
	}]
});

//个人统计查询BottomForm
Ext.define('Foss.load.loaderworkloadmodify.TaskDetailBottomForm', {
	extend : 'Ext.form.Panel',
	layout : 'column',
	frame : true,
	border : false,
	height : 50,
	defaultType: 'textfield',
	defaults : {
		margin : '5 5 5 5',
		columns : 4,
		labelWidth: 100
	},
	items : [{
		fieldLabel : load.loaderworkloadmodify.i18n('foss.load.loaderworkloadmodify.totWaybillQty'),
		name : 'totWaybillQty',
		readOnly: true,
		columnWidth : .25
	}, {
		fieldLabel : load.loaderworkloadmodify.i18n('foss.load.loaderworkloadmodify.totGoodsQty'),
		name : 'totGoodsQty',
		readOnly: true,
		columnWidth : .25
	}, {
		fieldLabel : load.loaderworkloadmodify.i18n('foss.load.loaderworkloadmodify.totWeight'),
		readOnly: true,
		name : 'totWeight',
		columnWidth : .25
	}]
});

//装卸车工作量查询结果Grid
Ext.define('Foss.load.loaderworkloadmodify.LoaderGridPanel', {
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
	height:320,
	emptyText: "查询结果为空",
	selModel : Ext.create('Ext.selection.CheckboxModel'),
	columns : [{
		xtype:'actioncolumn',
		header: load.loaderworkloadmodify.i18n('foss.load.loaderworkloadmodify.operate'),
		width: 50,
        items: [{
            iconCls: 'deppon_icons_edit',
            tooltip: load.loaderworkloadmodify.i18n('foss.load.loaderworkloadmodify.modify'),
            handler: function(gridView, rowIndex, colIndex) {
            	var grid = gridView.up('grid');
            	var loaderworkRecord = load.loaderworkloadmodify.taskDetailGridPanel.getSelectionModel().getSelection();
				var id = loaderworkRecord[0].get('taskNo'),
					totWeight = loaderworkRecord[0].get('totWeight'),
					totWaybillQty = loaderworkRecord[0].get('totWaybillQty');
            	//请求action获取界面上需要的数据
            	Ext.Ajax.request({
					url : load.realPath('queryLoaderWorksById.action'),
					params : {'loaderWorkloadVo.loaderWorkloadDto.id' : grid.getStore().getAt(rowIndex).get('id')},
					success : function(response) {
						var result = Ext.decode(response.responseText);
						loaderWorkloadEntity = result.loaderWorkloadVo.loaderWorkloadEntity;
						var form = grid.getLoaderWorksModifyWindow().items.items[0].form;
						form.findField('id').setValue(loaderWorkloadEntity.id);
						
						//因为程序中的判断是取所有理货员的重量 再 加上修改后的重量, 所以这里的总重量要将没修改之前的加上.
						var weight = grid.getStore().getAt(rowIndex).get('weight');
						if(Ext.isEmpty(weight)) {
							weight = 0;
						}
						if(Ext.isEmpty(totWeight)) {
							totWeight = 0;
						}
						totWeight = parseFloat(totWeight);
						weight = parseFloat(weight);
						form.findField('totWeightTemp').setValue(totWeight + weight);
						
						//因为程序中的判断是取所有理货员的票数 再 加上修改后的票数, 所以这里的总票数要将没修改之前的加上.
						var waybillQty = grid.getStore().getAt(rowIndex).get('waybillQty');
						totWaybillQty = parseInt(totWaybillQty);
						waybillQty = parseInt(waybillQty);
						if(Ext.isEmpty(totWaybillQty)) {
							totWaybillQty = 0;
						}
						if(Ext.isEmpty(waybillQty)) {
							waybillQty = 0;
						}
						form.findField('totWaybillQtyTemp').setValue(totWaybillQty + waybillQty);
						
						form.findField('taskNo').setValue(loaderWorkloadEntity.taskNo);
						form.findField('loaderName').setValue(loaderWorkloadEntity.loaderName);
						form.findField('loaderCode').setValue(loaderWorkloadEntity.loaderCode);
						form.findField('weight').setValue(loaderWorkloadEntity.weight);
						form.findField('waybillQty').setValue(loaderWorkloadEntity.waybillQty);
						
						form.findField('loaderOrgCode').setValue(loaderWorkloadEntity.loaderOrgCode);
						form.findField('loaderOrgName').setValue(loaderWorkloadEntity.loaderOrgName);
						
						form.findField('notes').setValue(loaderWorkloadEntity.notes);
						form.findField('beginDate').setValue(Ext.Date.format(new Date(loaderWorkloadEntity.joinTime), 'Y-m-d H:i:s'));
						form.findField('endDate').setValue(Ext.Date.format(new Date(loaderWorkloadEntity.leaveTime), 'Y-m-d H:i:s'));
						grid.getLoaderWorksModifyWindow().show();
					}
				});
            	
            }
        }]
	}, {
		text : load.loaderworkloadmodify.i18n('foss.load.loaderworkloadmodify.loaderName'),
		dataIndex : 'loaderName',
		width : 100
	}, {
		text : load.loaderworkloadmodify.i18n('foss.load.loaderworkloadmodify.loaderCode'),
		dataIndex : 'loaderCode',
		width : 120
	}, {
		text : load.loaderworkloadmodify.i18n('foss.load.loaderworkloadmodify.loaderOrgName'),
		dataIndex : 'loaderOrgName',
		width : 100
	}, {
		//货物类型
		text : load.loaderworkloadmodify.i18n('foss.load.loaderworkload.goodsType'),
		dataIndex : 'goodsType',
		width : 100,
		renderer : function(value) {
			switch(value) {
			case 'A_TYPE':
				//A货
				return load.loaderworkloadmodify.i18n('foss.load.loaderworkload.LD');
			case 'B_TYPE':
				//B货
				return load.loaderworkloadmodify.i18n('foss.load.loaderworkload.LD');
			case 'ALL':
				//全部
				return load.loaderworkloadmodify.i18n('foss.load.loaderworkload.LD');
			default: return value;
			}
		}
	}, {
		text : load.loaderworkloadmodify.i18n('foss.load.loaderworkloadmodify.waybillQty'),
		dataIndex : 'waybillQty',
		width : 100
	}, {
		text : load.loaderworkloadmodify.i18n('foss.load.loaderworkloadmodify.weight'),
		dataIndex : 'weight',
		width : 100
	}, {
		text : load.loaderworkloadmodify.i18n('foss.load.loaderworkloadmodify.joinTime'),
		dataIndex : 'joinTime',
		xtype : 'datecolumn',
		format : 'Y-m-d H:i:s',
		width : 130
	}, {
		text : load.loaderworkloadmodify.i18n('foss.load.loaderworkloadmodify.leaveTime'),
		dataIndex : 'leaveTime',
		xtype : 'datecolumn',
		format : 'Y-m-d H:i:s',
		width : 130
	}, {
		text : load.loaderworkloadmodify.i18n('foss.load.loaderworkloadmodify.notes'),
		dataIndex : 'notes'
	}],
	loaderWorksAddNewWindow: null,
	getLoaderWorksAddNewWindow: function(){
		var me = this;
		if(this.loaderWorksAddNewWindow == null){
			this.loaderWorksAddNewWindow = Ext.create('Ext.window.Window', {
			    title: load.loaderworkloadmodify.i18n('foss.load.loaderworkloadmodify.addLoadwork'),
			    height:250,
			    width:600,
			    closable:true,
				closeAction:'hide',
			    modal: true,
			    items: [
		            Ext.create('Foss.load.loaderworkaddnew.loaderWorksAddNewPanel')
	            ]
			});
		}
		return this.loaderWorksAddNewWindow;
	},
	loaderWorksModifyWindow: null,
	getLoaderWorksModifyWindow: function(){
		var me = this;
		if(this.loaderWorksModifyWindow == null){
			this.loaderWorksModifyWindow = Ext.create('Ext.window.Window', {
				title: load.loaderworkloadmodify.i18n('foss.load.loaderworkloadmodify.modifyLoadwork'),
				height:250,
				width:600,
				closable:true,
				closeAction:'hide',
				modal: true,
				items: [
				        Ext.create('Foss.load.loaderworkmodify.loaderWorksModifyPanel')
		        ]
			});
		}
		return this.loaderWorksModifyWindow;
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.load.loaderworkloadmodify.LoaderWorks.Store');
//		me.bbar = Ext.create('Deppon.StandardPaging',{
//			store:me.store,
//			plugins: 'pagesizeplugin'
//		});
		me.tbar = [{
			xtype : 'button',
			text : load.loaderworkloadmodify.i18n('foss.load.loaderworkloadmodify.add'),
			disabled: !load.loaderworkloadmodify.isPermission('load/modifyLoaderWorkButton'),
			hidden: !load.loaderworkloadmodify.isPermission('load/modifyLoaderWorkButton'),
			handler : function(){
				var loaderworkRecord = load.loaderworkloadmodify.taskDetailGridPanel.getSelectionModel().getSelection();
				if(loaderworkRecord.length <= 0) {
					Ext.ux.Toast.msg(load.loaderworkloadmodify.i18n('foss.load.loaderworkloadmodify.prompt'), load.loaderworkloadmodify.i18n('foss.load.loaderworkloadmodify.mustSeletedOne'), 'error');
					return;
				}
				var id = loaderworkRecord[0].get('taskNo'),
					totWeight = loaderworkRecord[0].get('totWeight'),
					totWaybillQty = loaderworkRecord[0].get('totWaybillQty');
				var form = me.getLoaderWorksAddNewWindow().items.items[0].form;
				form.reset();
				if(Ext.isEmpty(totWeight)) {
					totWeight = 0;
				}
				if(Ext.isEmpty(totWaybillQty)) {
					totWaybillQty = 0;
				}
				form.findField('totWeightTemp').setValue(totWeight);
				form.findField('totWaybillQtyTemp').setValue(totWaybillQty);
				form.findField('taskNo').setValue(id);
				me.getLoaderWorksAddNewWindow().show();
			}
		}];
		load.loaderworkloadmodify.pagingBarLoader = me.bbar;
		me.callParent([cfg]);
	}
});

Ext.onReady(function() {
	Ext.tip.QuickTipManager.init();
	Ext.QuickTips.init();
	
	var queryForm = Ext.create('Foss.load.loaderworkloadmodify.QueryForm');
	var taskDetailGridPanel = Ext.create('Foss.load.loaderworkloadmodify.TaskDetailGridPanel');
	var taskDetailBottomForm = Ext.create('Foss.load.loaderworkloadmodify.TaskDetailBottomForm');
	var loaderGridPanel = Ext.create('Foss.load.loaderworkloadmodify.LoaderGridPanel');
	
	load.loaderworkloadmodify.queryForm = queryForm;
	load.loaderworkloadmodify.taskDetailGridPanel = taskDetailGridPanel;
	load.loaderworkloadmodify.taskDetailBottomForm = taskDetailBottomForm;
	load.loaderworkloadmodify.loaderGridPanel = loaderGridPanel;
	
	Ext.create('Ext.panel.Panel', {
				id : 'T_load-loaderworkloadmodify_content',
				cls : "panelContentNToolbar",
				bodyCls : 'panelContentNToolbar-body',
				layout : 'auto',
				// 定义容器中的项
				items : [queryForm, taskDetailGridPanel, taskDetailBottomForm, loaderGridPanel],
				renderTo : 'T_load-loaderworkloadmodify-body'
			});
});