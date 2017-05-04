//点单任务状态点单中，点单完毕
Ext.define('Foss.unload.QueryForm.OrderTaskStatus.Store', {
			extend : 'Ext.data.Store',
			fields : ['value', 'name'],
			data : [{
				"value" : "ALL",
				"name" : unload
						.i18n('Foss.unload.checkbox.defaultvalue')
			}, {
				"value" : "IN",
				"name" : unload
						.i18n('Foss.unload.ordertask.status.doing')
			}, {
				"value" : "END",
				"name" : unload
						.i18n('Foss.unload.ordertask.status.done')
			}]
		});

/*//点单任务状态
Ext.define('Foss.Queryordertask.QueryForm.State.Store', {
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
});*/
				
Ext.define('Foss.unload.queryordertask.OrderTaskModel',{
	extend:'Ext.data.Model',
	fields: [
		{name: 'id', type: 'string'},
		{name: 'orderTaskNo' , type: 'string'},
		{name: 'vehicleNo' , type: 'string'},
		{name: 'orderTaskState' , type: 'string'},
		{name: 'reportNo' , type: 'string'},
		{name: 'reportState' , type: 'string'},
		{name: 'orderName' , type: 'string'},
		{name: 'orderCode' , type: 'string'},
		{name: 'orderStartTime' , convert : dateConvert, type: 'date'},
		{name: 'orderEndTime' , convert : dateConvert, type: 'date'}
	]
});

//272681 查询条件
Ext.define('Foss.unload.QueryForm', {
	extend : 'Ext.form.Panel',
	layout : 'column',
	frame : true,
	border : false,
	title : unload.i18n('Foss.unload.search.title'),
	defaults : {
		margin : '5 5 5 5',
		columns : 4
	},
	items : [{
		xtype : 'textfield',
		fieldLabel : unload
				.i18n('Foss.unload.ordertask.model.taskNo'), // 任务编号
		name : 'orderTaskNo',
		columnWidth : .3
	}, {
		xtype : 'combo',
		fieldLabel : unload
				.i18n('Foss.unload.ordertask.model.taskStatus'), // 点单任务状态
		name : 'orderTaskState',
		store : Ext
				.create('Foss.unload.QueryForm.OrderTaskStatus.Store'),		
		queryMode : 'local',
		displayField : 'name',
		valueField : 'value',
		value : 'ALL',
		editable : false,
		columnWidth : .3
	}, {
		xtype : 'container',
		columnWidth : .05,
		html : '&nbsp;'
	}, {
		xtype : 'container',
		columnWidth : .05,
		html : '&nbsp;'
	}, {
		name : 'orderName',// ！点单员
		fieldLabel : '点单员',
		xtype : 'commonemployeeselector',
		deptCode : FossUserContext.getCurrentDeptCode(),
		displayField : 'empName',// 显示名称
		valueField : 'empCode'// 值
	}, {
		xtype : 'rangeDateField',
		fieldLabel : unload
				.i18n('Foss.unload.sttask.model.createtime'), // 任务创建时间
		//fieldId : 'Foss_unload_QueryForm_stTaskCreateTime_ID',
		dateType : 'datetimefield_date97',
		dateRange : 7,
		disallowBlank : true,
		fromName : 'orderStartTime',
		fromValue : Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()-7
				,'00','00','00'), 'Y-m-d H:i:s'), // 默认查询7天内的数据
		toName : 'orderEndTime',
		toValue : Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()
				,'23','59','59'), 'Y-m-d H:i:s'),
		columnWidth : .5
	}, {
		xtype : 'container',
		columnWidth : .05,
		html : '&nbsp;'
	},/*
		 * { xtype: 'commongoodsareaselector', fieldLabel:
		 * unload.i18n('Foss.unload.sttask.model.goodsArea'), //货区
		 * name: 'goodsArea', //deptCode: FossUserContext.getCurrentDeptCode(),
		 * columnWidth:.3 },
		 */{
		xtype : 'container',
		columnWidth : .05,
		html : '&nbsp;'
	}, {
		border : 1,
		xtype : 'container',
		columnWidth : 1,
		defaultType : 'button',
		layout : 'column',
		items : [{
			text : unload.i18n('Foss.unload.button.reset'), // 重置
			columnWidth : .08,
			handler : function() {
				var theForm = this.up('form').getForm();
				theForm.reset();
				theForm.findField('orderStartTime').setValue(Ext.Date
						.format(new Date(new Date().getFullYear(), new Date()
												.getMonth(), new Date()
												.getDate()
												- 7, new Date().getHours() + 1,
										new Date().getMinutes(), new Date()
												.getSeconds()), 'Y-m-d H:i:s'));
				theForm.findField('orderEndTime')
						.setValue(Ext.Date
								.format(new Date(new Date().getFullYear(),
												new Date().getMonth(),
												new Date().getDate(),
												new Date().getHours() + 1,
												new Date().getMinutes(),
												new Date().getSeconds()),
										'Y-m-d H:i:s'));
			}
		}, {
			xtype : 'container',
			columnWidth : .84,
			html : '&nbsp;'
		}, {
			text : unload.i18n('Foss.unload.button.search'),
			hidden : !unload
					.isPermission('unload/queryStTaskButton'),
			cls : 'yellow_button',
			columnWidth : .08,
			handler : function() {
				if(!this.up('form').getForm().isValid()) {
				return;
			}
			unload.pagingBar.moveFirst();
			}
		}]
	}],
	constructor : function(config) {
		var me = this;
		var cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});


//		
Ext.define('Foss.unload.queryordertask.OrderTaskStore',{
	extend: 'Ext.data.Store',
	model: 'Foss.unload.queryordertask.OrderTaskModel',
//	pageSize: 25,
	autounload: false,
	proxy: {
        type : 'ajax',
        actionMethods:'POST',
        url : unload.realPath('queryOrderTask.action'),
		reader : {
			type : 'json',
			root : 'orderTaskVo.orderTaskDtoList',
			totalProperty : 'totalCount',
			successProperty: 'success'
		}
    },
    listeners: {
		beforeload : function(store, operation, eOpts) {
			var queryParams = unload.ordertaskquery.getValues();
			Ext.apply(operation, {
				params : {
					'orderTaskVo.orderTaskDto.orderTaskNo' : queryParams.orderTaskNo,
					'orderTaskVo.orderTaskDto.orderTaskState' : queryParams.orderTaskState,
					'orderTaskVo.orderTaskDto.orderName' :queryParams.orderName ,
					'orderTaskVo.orderTaskDto.orderStartTime' :queryParams.orderStartTime ,
					'orderTaskVo.orderTaskDto.orderEndTime' : queryParams.orderEndTime,
					'orderTaskVo.orderTaskDto.createOrgCode' : FossUserContext.getCurrentDeptCode()
				}
			});
		}
	}
});


//表格panel
Ext.define('Foss.unload.queryordertask.QueryResult', {
	extend:'Ext.grid.Panel',
    bodyCls: 'autoHeight',
	cls: 'autoHeight',
    stripeRows: true,
    frame: true,
	animCollapse: true,
	autoScroll: true,
	height: 500,
	emptyText: unload.i18n('Foss.unload.warn.resultnull'),//查询结果为空
	//定义表格列信息
	columns: [{
			xtype:'actioncolumn',
			align : 'center',
			header: unload.i18n('Foss.unload.button.operator'),//操作
			width : 80,
	        items: [{
	            iconCls: 'deppon_icons_edit',
	            tooltip: unload.i18n('foss.unload.ordertaskquery.modifyOrderTask'),//修改
	            handler: function(grid, rowIndex, colIndex) {
	            	var record = grid.getStore().getAt(rowIndex);
	            	var orderTaskNo = record.get('orderTaskNo');
	            	var orderCode= record.get('orderCode');
	            	if(orderCode != unload.empCode){
						Ext.ux.Toast.msg(unload.i18n('foss.unload.ordertaskaddnew.alertInfo.alertTitle'), 
									unload.i18n('foss.unload.ordertaskaddnew.alertInfo.orderManNotSame'), 
							'error', 2000);
							return;
							}
	            	// 处理点单任务
				unload.addTab('T_unload-orderTaskmodifyindex',unload.i18n('Foss.unload.ordertaskmodify.mainTab.title'),
								unload.realPath('orderTaskmodifyindex.action')+ '?orderTaskNo="' + orderTaskNo + '"');
	            }
	        }, {
				tooltip : unload.i18n('foss.unload.AssignUnloadTaskQuery.watch'),//'查看',//查看
				iconCls : 'deppon_icons_showdetail',
				handler : function(grid, rowIndex, colIndex) {
				var record = grid.getStore().getAt(rowIndex);
	            	var orderTaskNo = record.get('orderTaskNo');
	            	
	            	// 查看点单任务完毕明细界面
				unload.addTab('T_unload-orderTaskfinishindex',unload.i18n('Foss.unload.ordertaskfinish.mainTab.title'),
								unload.realPath('orderTaskfinishindex.action')+ '?orderTaskNo="' + orderTaskNo + '"');
				}
				}],
            renderer:function(value, metadata, record){
            	if(record.data.orderTaskState == 'IN'){
        			//当点单任务状态为"点单中", 可以 修改
            		//当点单任务状态为"点单完毕", 不可修改，可以查看
        			this.items[0].iconCls = 'deppon_icons_edit';
        			this.items[1].iconCls = '';
        		} else {
        			this.items[0].iconCls = '';
        			this.items[1].iconCls = 'deppon_icons_showdetail';
        		}
            }
		},{
			header: unload.i18n('Foss.unload.ordertask.model.taskNo'), //任务编号
			dataIndex: 'orderTaskNo',
			flex : 1
		},{
			header: unload.i18n('Foss.unload.ordertask.model.vehicleNo'), //车牌号
			dataIndex: 'vehicleNo',
			flex : 1
		},{
			header: unload.i18n('Foss.unload.ordertask.model.taskStatus'), //任务状态
			dataIndex: 'orderTaskState',
			flex : 1,
			renderer : function(value) {
				switch(value) {
					case 'IN'://点单中
						return unload.i18n('Foss.unload.ordertask.status.doing');
					case 'END'://点单完毕
						return unload.i18n('Foss.unload.ordertask.status.done');
					default: return value;
				}
			}
		},{
			header: unload.i18n('Foss.unload.ordertaskreport.model.reportCode'),//点单差异编号
			dataIndex: 'reportNo',
			flex : 1
		},{
			header: unload.i18n('Foss.unload.ordertaskreport.model.handleStatus'),//报告处理状态
			dataIndex: 'reportState',
			flex : 1,
			renderer : function(value) {
				switch(value) {
					case 'ING'://处理中
						return unload.i18n('Foss.unload.ordertaskreport.status.doing');
					case 'END'://处理完毕
						return unload.i18n('Foss.unload.ordertaskreport.status.done');
					default: return value;
				}
			}
		},{
			header: unload.i18n('Foss.unload.ordertask.model.operator'), //点单员
			dataIndex: 'orderName',
			flex : 1
		},{
			header: unload.i18n('Foss.unload.ordertask.model.createtime'), //任务创建时间
			dataIndex: 'orderStartTime',
			xtype : 'datecolumn',
			format : 'Y-m-d H:i:s',
			flex: 1.8
		},{
			header: unload.i18n('Foss.unload.ordertask.model.finishtime'), //任务完成时间
			dataIndex: 'orderEndTime',
			xtype : 'datecolumn',
			format : 'Y-m-d H:i:s',
			flex: 1.8
		}],
		constructor: function(config){
			var me = this,
			cfg = Ext.apply({}, config);
			me.store = Ext.create('Foss.unload.queryordertask.OrderTaskStore');
			me.bbar = Ext.create('Deppon.StandardPaging',{
				store:me.store,
				plugins: 'pagesizeplugin'
			});
			me.tbar = [{
				xtype : 'button',
				text : unload.i18n('Foss.unload.button.new'),//新增
				handler : function(){
					// 新增点单任务
				unload.addTab('T_unload-orderTaskaddnewindex',unload.i18n('Foss.unload.ordertaskaddnew.mainTab.title'),
								unload.realPath('orderTaskaddnewindex.action'));
				}
			}],
			unload.pagingBar = me.bbar;
			me.callParent([cfg]);
		}
});

//unload.ordertaskquery = Ext.create('Foss.unload.QueryForm');

Ext.onReady(function() {
	Ext.QuickTips.init();
	var queryform = Ext.create('Foss.unload.QueryForm');
	unload.ordertaskquery = queryform;
	var queryResult = Ext.create('Foss.unload.queryordertask.QueryResult');
	Ext.create('Ext.panel.Panel',{
		id: 'T_unload-orderTaskindex_content',
		cls: "panelContentNToolbar",
		bodyCls: 'panelContent-body',
		layout: 'auto',
		items: [queryform,queryResult],
		renderTo: 'T_unload-orderTaskindex-body'
	});
});



















