// 点单任务状态点单中，点单完毕
Ext.define('Foss.unload.orderDifferReport.QueryForm.orderReportType', {
			extend : 'Ext.data.Store',
			fields : ['value', 'name'],
			data : [{// 点单差异类型（NORMAL正常,LOSE少货,MORE多货）
				"value" : "ALL",
				"name" : unload.orderDifferReport.i18n('Foss.unload.checkbox.defaultvalue')
			}, {
				"value" : "LOSE",
				"name" : unload.orderDifferReport.i18n('Foss.unload.orderDifferReport.status.LOSE')
			}, {
				"value" : "MORE",
				"name" : unload.orderDifferReport.i18n('Foss.unload.orderDifferReport.status.MORE')
			}]
		});
Ext.define('Foss.unload.orderDifferReport.OrderReportModel', {
			extend : 'Ext.data.Model',
			fields : [{
						name : 'id',
						type : 'string'
					}, {
						name : 'reportNo',// 报告编号
						type : 'string'
					}, {
						name : 'moreGoodsQty',// 多货件数
						type : 'number'
					}, {
						name : 'lostGoodsQty',// lost少货件数
						type : 'number'
					}, {
						name : 'orderManName',// 点单人
						type : 'string'
					}, {
						name : 'orderManCode',// 点单人
						type : 'string'
					}, {
						name : 'reportState',// 报告状态 ING处理中，END处理完成，viod作废
						type : 'string'
					}, {
						name : 'orderOrgName',//点单报告生成部门
						type : 'string'
					}, {
						name : 'handleStatus',// 报告处理状态
						type : 'String'
					}, {
						name : 'reportStartTime',
						convert : dateConvert,
						type : 'date'
					}, {
						name : 'reportEndTime',//
						convert : dateConvert,
						type : 'date'
					},{
					   name:'createTime',//点单报告创建时间
					   convert:dateConvert,
					   type:'date'
					},{
					   name:'reportEndTime',//点单报告处理完成时间
					   convert:dateConvert,
					   type:'date'
					}]
		});

// 查询条件
Ext.define('Foss.unload.orderDifferReport.QueryForm', {
	extend : 'Ext.form.Panel',
	layout : 'column',
	frame : true,
	border : false,
	title : unload.orderDifferReport.i18n('Foss.unload.orderDifferReport.search.title'),// 查询条件
	defaults : {
		margin : '5 5 5 5',
		columns : 3
	},
	items : [{
		xtype : 'rangeDateField',
		fieldLabel : unload.orderDifferReport.i18n('Foss.unload.orderDifferReport.form.reportTime'), // 报告生成时间
		fieldId : 'Foss_unload_QueryForm_orderDifferReportStartTime_ID',
		dateType : 'datetimefield_date97',
		disallowBlank : true,
		fromName : 'orderDifferReportStartTime',
		dateRange : 7,
		fromValue : Ext.Date.format(new Date(new Date().getFullYear(),
						new Date().getMonth(), new Date().getDate() - 7,
						new Date().getHours() + 1, new Date().getMinutes(),
						new Date().getSeconds()), 'Y-m-d H:i:s'), // 默认查询7天内的数据
		toName : 'orderDifferReportEndTime',
		toValue : Ext.Date.format(new Date(), 'Y-m-d H:i:s'),
		columnWidth : .6
	}, {
		xtype : 'combo',
		fieldLabel : unload.orderDifferReport.i18n('Foss.unload.orderDifferReport.orderReportType'), // 异常状态
		name : 'orderDifferReportType',
		store : Ext.create('Foss.unload.orderDifferReport.QueryForm.orderReportType'),
		queryMode : 'local',
		displayField : 'name',
		valueField : 'value',
		value : 'ALL',
		editable : false,
		columnWidth : .4
	}, {
		name : 'orderMan',// ！点单员
		fieldLabel : '点单员',
		xtype : 'commonemployeeselector',
		deptCode : FossUserContext.getCurrentDeptCode(),
		displayField : 'empName',// 显示名称
		valueField : 'empCode',// 值
		columnWidth : .33
	}, {
		xtype : 'textfield',
		fieldLabel : unload.orderDifferReport.i18n('Foss.unload.orderDifferReport.reportNo'), // 任务编号
		name : 'reportNo',
		columnWidth : .33
	}, {
		xtype : 'textfield',
		fieldLabel : unload.orderDifferReport.i18n('Foss.unload.orderDifferReport.handoverNo'), // 交接单号
		name : 'handoverNo',
		columnWidth : .33
	}, {
		border : 1,
		xtype : 'container',
		columnWidth : 1,
		defaultType : 'button',
		layout : 'column',
		items : [{
			text : unload.orderDifferReport.i18n('Foss.unload.orderDifferReport.button.reset'), // 重置
			columnWidth : .08,
			handler : function() {
				var theForm = this.up('form').getForm();
				theForm.reset();
				theForm.findField('orderDifferReportStartTime').setValue(
				Ext.Date.format(new Date(new Date().getFullYear(), new Date()
												.getMonth(), new Date()
												.getDate()
												- 7, new Date().getHours() + 1,
										new Date().getMinutes(), new Date()
												.getSeconds()), 'Y-m-d H:i:s'));
				theForm.findField('orderDifferReportEndTime').setValue(Ext.Date.format(
						new Date(), 'Y-m-d H:i:s'));
			}
		}, {
			xtype : 'container',
			columnWidth : .84,
			html : '&nbsp;'
		}, {
			text : unload.orderDifferReport.i18n('Foss.unload.orderDifferReport.button.search'),
			hidden : !unload.orderDifferReport.isPermission('unload/queryOrderDifferReportButton'),
			cls : 'yellow_button',
			columnWidth : .08,
			handler : function() {
				if (!this.up('form').getForm().isValid()) {
					return;
				}
				unload.orderDifferReport.pagingBar.moveFirst();
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
Ext.define('Foss.unload.orderDifferReport.OrderDifferReportStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.unload.orderDifferReport.OrderReportModel',
	// pageSize: 25,
	autoLoad : false,
	proxy : {
		type : 'ajax',
		actionMethods : 'POST',
		url : unload.realPath('qureyOrderDifferReport.action'),
		reader : {
			type : 'json',
			root : 'orderDifferReportVo.orderDifferReportList',
			totalProperty : 'totalCount',
			successProperty : 'success'
		}
	},
	listeners : {
		beforeload : function(store, operation, eOpts) {
			var queryParams = unload.orderDifferReport.orderDifferReportquery.getValues();
			Ext.apply(operation, {
					 params : {
					 'orderDifferReportVo.orderDifferReportEntity.reportNo' :queryParams.reportNo,
					 'orderDifferReportVo.orderDifferReportEntity.orderDifferReportType' :queryParams.orderDifferReportType,
					 'orderDifferReportVo.orderDifferReportEntity.orderManCode' :queryParams.orderMan,
					 'orderDifferReportVo.orderDifferReportEntity.orderDifferReportStartTime' :queryParams.orderDifferReportStartTime,
					 'orderDifferReportVo.orderDifferReportEntity.orderDifferReportEndTime' :queryParams.orderDifferReportEndTime,
					 'orderDifferReportVo.orderDifferReportEntity.handoverNo' :queryParams.handoverNo
					 }
					});
		}
	}
});
// 表格panel
Ext.define('Foss.unload.orderDifferReport.QueryResult', {
	extend : 'Ext.grid.Panel',
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	stripeRows : true,
	frame : true,
	animCollapse : true,
	autoScroll : true,
	emptyText : unload.orderDifferReport.i18n('Foss.unload.orderDifferReport.warn.resultnull'),// 查询结果为空
	// 定义表格列信息
	columns : [{
		xtype : 'actioncolumn',
		align : 'center',
		header : unload.orderDifferReport.i18n('Foss.unload.orderDifferReport.button.operator'),// 操作
		width : 80,
		items : [{
			iconCls : 'deppon_icons_edit',
			tooltip : unload.orderDifferReport.i18n('Foss.unload.orderDifferReport.handle'),// 处理
			handler : function(grid, rowIndex, colIndex) {
				var record = grid.getStore().getAt(rowIndex);
				var reportNo = record.get('reportNo');
				// 处理点单任务
				unload.addTab('T_unload-orderDifferReportHandleindex',
				unload.orderDifferReport.i18n('Foss.unload.orderDifferReport.handle.title'),
								unload.realPath('orderDifferReportHandle.action')
										+ '?type="handle"&reportNo="' + reportNo + '"');
			}
		},{
				tooltip : unload.orderDifferReport.i18n('Foss.unload.orderDifferReport.show'),//查看
				iconCls : 'deppon_icons_showdetail',
				handler : function(grid, rowIndex, colIndex) {
				var record = grid.getStore().getAt(rowIndex);
				var reportNo = record.get('reportNo');
				// 处理点单任务
				unload.addTab('T_unload-orderDifferReportHandleindex',
				unload.orderDifferReport.i18n('Foss.unload.orderDifferReport.handle.title'),
								unload.realPath('orderDifferReportHandle.action')
										+ '?type="show"&reportNo="' + reportNo + '"');
			}
				
		}],
		renderer : function(value, metadata, record) {
			if (record.data.reportState == 'ING') {
				// 当点单任务状态为"点单中", 可以 修改
				// 当点单任务状态为"点单完毕", 不可修改，可以查看
				this.items[0].iconCls = 'deppon_icons_edit';
				this.items[1].iconCls = '';
			} else if(record.data.reportState == 'END'){
			  this.items[0].iconCls = '';
			  this.items[1].iconCls = 'deppon_icons_showdetail';
			}else{
				this.items[0].iconCls = '';
				this.items[1].iconCls = '';
			}
		}
	}, {
		header : unload.orderDifferReport.i18n('Foss.unload.orderDifferReport.reportNo'), // 报告编号
		dataIndex : 'reportNo',
		flex : 1
	}, {
		header : unload.orderDifferReport.i18n('Foss.unload.orderDifferReport.moreGoodsQty'),// 多货件数
		dataIndex : 'moreGoodsQty',
		flex : 1
	}, {
		header : unload.orderDifferReport.i18n('Foss.unload.orderDifferReport.lost'),// 少货件数
		dataIndex : 'lostGoodsQty',
		flex : 1
	}, {
		header : unload.orderDifferReport.i18n('Foss.unload.orderDifferReport.orderMan'), // 点单员
		dataIndex : 'orderManName',
		flex : 1
	}, {
		header : unload.orderDifferReport.i18n('Foss.unload.orderDifferReport.handleStatus'),// 报告处理状态
		dataIndex : 'reportState',
		flex : 1,
		renderer : function(value) {
			switch (value) {
				case 'ING' :// 处理中
					return unload.orderDifferReport.i18n('Foss.unload.orderDifferReport.status.ING');
				case 'END' :// 处理完毕
					return unload.orderDifferReport.i18n('Foss.unload.orderDifferReport.status.END');
				default :
					return value;
			}
		}
	}, {
		header : unload.orderDifferReport.i18n('Foss.unload.orderDifferReport.createtime'), // 报告创建时间
		dataIndex : 'createTime',
		xtype : 'datecolumn',
		format : 'Y-m-d H:i:s',
		flex : 1.8
	}, {
		header : unload.orderDifferReport.i18n('Foss.unload.orderDifferReport.finishtime'), // 报告完成时间
		dataIndex : 'reportEndTime',
		xtype : 'datecolumn',
		format : 'Y-m-d H:i:s',
		flex : 1.8
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.unload.orderDifferReport.OrderDifferReportStore');
		me.bbar = Ext.create('Deppon.StandardPaging', {
					store : me.store,
					plugins : 'pagesizeplugin'
				});
		unload.orderDifferReport.pagingBar=me.bbar;
		me.callParent([cfg]);
	}
});
Ext.onReady(function() {
			Ext.QuickTips.init();
			// console.log(unload.orderDifferReport.superOrgCode);
			var queryform = Ext.create('Foss.unload.orderDifferReport.QueryForm');
			unload.orderDifferReport.orderDifferReportquery= queryform;
			var queryResult = Ext.create('Foss.unload.orderDifferReport.QueryResult');
			Ext.create('Ext.panel.Panel', {
						id : 'T_unload-orderDifferReportindex_content',
						cls : "panelContentNToolbar",
						bodyCls : 'panelContent-body',
						layout : 'auto',
						items : [queryform, queryResult],
						renderTo : 'T_unload-orderDifferReportindex-body'
					});
		});
