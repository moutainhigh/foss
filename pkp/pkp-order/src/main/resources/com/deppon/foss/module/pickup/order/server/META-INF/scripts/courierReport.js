// 查询的最大天数为31天
order.courierReport.MAXDAYS = 31 * 24 * 60 * 60 * 1000;

Ext.define('Foss.order.courierReport.expressWorkerStatusQueryForm', {
	extend : 'Ext.form.Panel',
	cls : 'autoHeight',
	bodyCls : 'autoHeight',
	collapsible : true,
	title : order.courierReport.i18n('pkp.order.expressOrderHandle.queryCondition'),
	frame : true,
	layout : {
		type : 'column'
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.items = [
				{
					fieldLabel : order.courierReport.i18n('pkp.order.queryExpressDispatchOrder.bigDeliverRegCode'),
					name : 'bigRegionCode',
					xtype : 'dynamicexpressbigzonemulticombselector',
					displayField : 'regionName',// 显示名称
					valueField : 'regionCode',// 值
					active : 'Y',
					dispatchTeam:'Y',
					type:'ORG',
					showContent : '{regionName}',
					columnWidth : .30,
//					allowBlank: false,
					labelWidth : 110
				},
				{
					fieldLabel : order.courierReport.i18n('pkp.order.queryExpressDispatchOrder.smallDeliverRegCode'),
					name : 'smallRegionCode',
					xtype : 'dynamicexpressSmallzonemulticombselector',
					displayField : 'regionName',// 显示名称
					valueField : 'regionCode',// 值
					active : 'Y',
					dispatchTeam:'Y',
					type:'ORG',
					showContent : '{regionName}',
					columnWidth : .30,
//					allowBlank: false,
					labelWidth : 110

				},
				{
					fieldLabel : order.courierReport.i18n('pkp.order.queryExpressDispatchOrder.driver'),
					name : 'driverCode',
					xtype : 'commonOldExpressEmpselector',
					displayField : 'empName',// 显示名称
					valueField : 'empCode',// 值
					active : 'Y',
					districtCodes : order.countyCodes,
					showContent : '{empCode}&nbsp;&nbsp;&nbsp;{empName}',
					columnWidth : .25,
//					allowBlank: false,
					labelWidth : 110

				},
				{
					xtype : 'container',
					border : false,
					html : '&nbsp;',
					columnWidth : .15
				},
				{
					xtype : 'container',
					border : false,
					html : '&nbsp;',
					height : 24,
					columnWidth : 1
				},
				{
					xtype : 'rangeDateField',
					fieldId : 'Foss_order_QueryForm_courierReport_ID',
					fieldLabel : order.courierReport.i18n('pkp.order.courierReport.queryBeginTime'),
					dateType : 'datetimefield_date97',
					editable : false,
					allowBlank : false,
					fromName : 'queryBeginTime',
					toName : 'queryEndTime',
					fromValue : Ext.Date.format(new Date(new Date().getFullYear(), new Date().getMonth(), new Date().getDate(), 0, 0, 0), 'Y-m-d H:i:s'),
					toValue : Ext.Date.format(new Date(new Date().getFullYear(), new Date().getMonth(), new Date().getDate(), 23, 59, 59), 'Y-m-d H:i:s'),
					columnWidth : .50,
					labelWidth : 80
				},
				{
					xtype : 'container',
					border : false,
					html : '&nbsp;',
					height : 24,
					columnWidth : .50
				},
				{
					xtype : 'container',
					border : false,
					html : '&nbsp;',
					height : 24,
					columnWidth : 1
				},
				{
					// 重置和查询按钮
					border : 1,
					xtype : 'container',
					columnWidth : 1,
					defaultType : 'button',
					layout : 'column',
					items : [
							{
								text : order.courierReport.i18n('pkp.order.courierReport.reset'),
								columnWidth : .08,
								handler : function() {
									var myform = this.up('form');
									myform.getForm().reset();
									// 查询开始时间
									myform.getForm().findField('Foss_order_QueryForm_courierReport_ID_first').setValue(
											Ext.Date.format(new Date(new Date().getFullYear(), new Date().getMonth(), new Date().getDate(), 0, 0, 0), 'Y-m-d H:i:s'));
									// 查询结束时间
									myform.getForm().findField('Foss_order_QueryForm_courierReport_ID_second').setValue(
											Ext.Date.format(new Date(new Date().getFullYear(), new Date().getMonth(), new Date().getDate(), 23, 59, 59), 'Y-m-d H:i:s'));
								}
							},
							{
								xtype : 'container',
								border : false,
								columnWidth : .75,
								html : '&nbsp;'
							},
							{
								text : order.courierReport.i18n('pkp.order.courierReport.query'),
								cls : 'yellow_button',
								columnWidth : .08,
								handler : function() {
									var myForm = this.up('form').getForm();
									var beginTime = Ext.Date.parse(myForm.findField('Foss_order_QueryForm_courierReport_ID_first').getValue(), 'Y-m-d H:i:s'), endTime = Ext.Date
											.parse(myForm.findField('Foss_order_QueryForm_courierReport_ID_second').getValue(), 'Y-m-d H:i:s');
									
									// 判断结束日期和开始日期是否在范围之内
									if (endTime.getTime() - beginTime.getTime() > order.courierReport.MAXDAYS) {
										Ext.ux.Toast.msg(order.courierReport.i18n('pkp.order.courierReport.tips'), order.courierReport
												.i18n('pkp.order.courierReport.maxDays'));
										return;
									}
									if (myForm.isValid()) {
										var me = this;
										var resultGrid = Ext.getCmp('T_order-courierReport_content').getGridPanel();
										// resultGrid.show();
										// order.courierReport.pagingBar.moveFirst();
										var store = resultGrid.getStore();
										store.loadPage(1, {
											callback : function(records, operation, success) {
												// 抛出异常
												var rawData = resultGrid.store.proxy.reader.rawData;
												if (!success && !rawData.isException) {
													Ext.Msg.alert(order.courierReport.i18n('pkp.order.courierReport.tips'), rawData.message);
													return false;
												}

												// 正常返回
												if (success) {
													var result = Ext.decode(operation.response.responseText);
													if (result.expressWorerStatusConditionVo.courierReportList.length > 0) {
														resultGrid.show();						
													} else {
														Ext.Msg.alert(order.courierReport.i18n('pkp.order.courierReport.tips'), order.courierReport
																.i18n('pkp.order.courierReport.queryNullResult'));
														return false;
													}
												}
											}
										});
									}
									;
								}
							} ]

				} ];
		me.callParent([ cfg ]);
	}
});

/**
 * 快递员工作状态MODEL
 */
Ext.define('Foss.order.courierReport.expressWorkerStatusEntity', {
	extend : 'Ext.data.Model',
	idproperty : 'id',
	idgen : 'uuid',
	fields : [ {
		name : 'courierName',// 姓名
		type : 'string',
		defaultValue : null
	}, {
		name : 'courierCode',// 工号
		type : 'string',
		defaultValue : null,
	}, {
		name : 'bigRegionName',// 收派大区
		type : 'string'
	}, {
		name : 'smallRegionName',// 收派小区
		type : 'string',
		defaultValue : null
	}, {
		name : 'courierType',// 属性
		type : 'string'
	}, {
		name : 'receiveOrderTotal',// 已接受订单
		type : 'int',
		defaultValue : 0
	}, {
		name : 'receiveWaybillOrderTotal',// 已完成订单
		type : 'int',
		defaultValue : 0
	}, {
		name : 'noOrderWaybillTotal',// 无订单开单
		type : 'int',
		defaultValue : 0
	}, {
		name : 'deliverWaybillTotal',// 应派送运单
		type : 'int',
		defaultValue : 0
	}, {
		name : 'signWaybillTotal',// 已派送运单
		type : 'int',
		defaultValue : 0
	}, {
		name : 'courierType',// 快递员状态
		type : 'string',
		defaultValue : null
	} ]
});

/**
 * 快递员工作状态Store
 */
Ext.define('Foss.order.courierReport.expressWorkerStatusStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.order.courierReport.expressWorkerStatusEntity',
	pageSize : 20,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url : order.realPath('queryCourierReords.action'),
		reader : {
			type : 'json',
			root : 'expressWorerStatusConditionVo.courierReportList',
			totalProperty : 'totalCount'
		}
	},
	listeners : {
		// 查询事件
		beforeload : function(s, operation, eOpts) {
			// 执行查询，首先查询条件，为全局变量，在查询条件的FORM创建时生成
			var serachParms = Ext.getCmp('T_order-courierReport_content').getQueryForm().getValues();
			/*
			 * var driverCodes = new Array();
			 * driverCodes.push(serachParms.driverCode);
			 */
			// TODO
			var params = {
				'expressWorerStatusConditionVo.bigRegionCode' : serachParms.bigRegionCode,
				'expressWorerStatusConditionVo.smallRegionCode' : serachParms.smallRegionCode,
				'expressWorerStatusConditionVo.expressWorkerCode' : serachParms.driverCode,
				'expressWorerStatusConditionVo.startDate' : Ext.Date.parse(serachParms.queryBeginTime, 'Y-m-d H:i:s'),
				'expressWorerStatusConditionVo.endDate' : Ext.Date.parse(serachParms.queryEndTime, 'Y-m-d H:i:s')
			};
			Ext.apply(operation, {
				params : params
			});
		}
	},
	sorters : {
		property : "modifyTime",
		direction : 'DESC'

	}
});

Ext.define('Foss.pricing.courierReport.courierReportGridPanel', {
	extend : 'Ext.grid.Panel',
	title : order.courierReport.i18n('pkp.order.courierReport.queryResult'),
	frame : true,
	cls : 'autoHeight',
	bodyCls : 'autoHeight',
	sortableColumns : true,
	enableColumnHide : false,
	enableColumnMove : false,
	stripeRows : true, // 交替行效果
	selType : "rowmodel", // 选择类型设置为：行选择
	emptyText : order.courierReport.i18n('pkp.order.courierReport.queryNullResult'),// 查询结果为空
	viewConfig : {
		stripeRows : false,
		enableTextSelection : true,
		getRowClass : function(record, rowIndex, rp, ds) {
			if (record.get('empStatus') == 'STOP' || record.get('empStatus') == null) {
				return 'order-workerStatus-row-red';
			}
		}
	},

	// 得到bbar
	pagingToolbar : null,
	getPagingToolbar : function() {
		var me = this;
		if (this.pagingToolbar == null) {
			me.bbar = Ext.create('Deppon.StandardPaging', {
				store : this.store,
				pageSize : 20,
				plugins : Ext.create('Deppon.ux.PageSizePlugin', {
					maximumSize : 20,
					sizeList : [ [ '10', 10 ], [ '20', 20 ] ]
				})
			});
		}
		return me.bbar;
	},
	exportData : function() {

		if (!Ext.fly('downloadAttachFileForm')) {
			var frm = document.createElement('form');
			frm.id = 'downloadAttachFileForm';
			frm.style.display = 'none';
			document.body.appendChild(frm);
		}
		// 执行查询，首先查询条件，为全局变量，在查询条件的FORM创建时生成
		var serachParms = Ext.getCmp('T_order-courierReport_content').getQueryForm().getValues();
		/*
		 * var driverCodes = new Array();
		 * driverCodes.push(serachParms.driverCode);
		 */
		// TODO
		var params = {
			'expressWorerStatusConditionVo.bigRegionCode' : serachParms.bigRegionCode,
			'expressWorerStatusConditionVo.smallRegionCode' : serachParms.smallRegionCode,
			'expressWorerStatusConditionVo.expressWorkerCode' : serachParms.driverCode,
			'expressWorerStatusConditionVo.startDate' : Ext.Date.parse(serachParms.queryBeginTime, 'Y-m-d H:i:s'),
			'expressWorerStatusConditionVo.endDate' : Ext.Date.parse(serachParms.queryEndTime, 'Y-m-d H:i:s')
		};

		Ext.Ajax.request({
			url : order.realPath('exportCourierReord.action'),
			method : 'post',
			form : Ext.fly('downloadAttachFileForm'),
			params : params,
			isUpload : true,
			success : function(response) {
				Ext.ux.Toast.msg(order.courierReport.i18n('pkp.order.courierReport.tips'), order.courierReport
						.i18n('pkp.order.courierReport.tips.exportSuccess'));
			},
			exception : function(response) {
				var result = Ext.decode(response.responseText);
				Ext.MessageBox.alert(order.courierReport.i18n('pkp.order.courierReport.tips'), result.message);
			}
		})
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.columns = [ {
			xtype : 'rownumberer',
			width : 40,
			text : order.courierReport.i18n('pkp.order.courierReport.rownum')
		// 序号
		}, {
			text : order.courierReport.i18n('pkp.order.courierReport.empName'),// 员工名称
			dataIndex : 'courierName'
		}, {
			text : order.courierReport.i18n('pkp.order.courierReport.empCode'),// 员工工号
			dataIndex : 'courierCode'
		}, {
			text : order.courierReport.i18n('pkp.order.courierReport.bigRegoin'),// 快递大区
			dataIndex : 'bigRegionName'
		}, {
			text : order.courierReport.i18n('pkp.order.courierReport.smallRegoin'),// 快递小区
			dataIndex : 'smallRegionName'
		}, {
			text : order.courierReport.i18n('pkp.order.courierReport.empType'),// 属性
			dataIndex : 'courierType'
		}, {
			text : order.courierReport.i18n('pkp.order.courierReport.getOrder'),// 获取订单
			dataIndex : 'receiveOrderTotal'
		}, {
			text : order.courierReport.i18n('pkp.order.courierReport.receiveOrder'),// 已接货订单
			dataIndex : 'receiveWaybillOrderTotal'
		}, {
			text : order.courierReport.i18n('pkp.order.courierReport.noOrderWaybill'),// 无订单开单
			dataIndex : 'noOrderWaybillTotal'
		}, {
			text : order.courierReport.i18n('pkp.order.courierReport.getWaybills'),// 货物运单
			dataIndex : 'signWaybillTotal'
		}, {
			text : order.courierReport.i18n('pkp.order.courierReport.deliverWaybills'),// 应送货运单
			dataIndex : 'deliverWaybillTotal'
		} ];
		me.store = Ext.create('Foss.order.courierReport.expressWorkerStatusStore', {
			autoLoad : false,
			pageSize : 20
		});

		me.selModel = Ext.create('Ext.selection.CheckboxModel', {// 多选框
			mode : 'MULTI',
			checkOnly : true
		});

		me.tbar = [{
			text : order.courierReport.i18n('pkp.order.courierReport.exportData'),// 导出功能:
			// !pricing.effectivePlan.isPermission('effectivePlan/effectivePlanImportbutton'),
			handler : function() {
				me.exportData();
			}
		}, {
			text : order.courierReport.i18n('pkp.order.courierReport.refreshData'),// 刷新:
			// !pricing.effectivePlan.isPermission('effectivePlan/effectivePlanImportbutton'),
			handler : function() {
				order.courierReport.pagingBar.moveFirst();
			}
		} ];

		me.bbar = me.getPagingToolbar();
		order.courierReport.pagingBar = me.bbar;

		me.callParent([ cfg ]);
	}
});

Ext.onReady(function() {
	// 1.禁止使用全局变量,可以使用module标签生成的模块名的object对象{}
	// 用法：模块名.自定义变量
	// 2.对象都是用Ext.define定义的方式声明
	Ext.QuickTips.init();
	var queryForm = Ext.create('Foss.order.courierReport.expressWorkerStatusQueryForm');
	var gridPanel = Ext.create('Foss.pricing.courierReport.courierReportGridPanel');
	Ext.create('Ext.panel.Panel', {
		id : 'T_order-courierReport_content',
		cls : "panelContentNToolbar",
		bodyCls : 'panelContentNToolbar-body',
		layout : 'auto',
		getQueryForm : function() {
			return queryForm;
		},
		getGridPanel : function() {
			return gridPanel;
		},
		items : [ queryForm, gridPanel ],
		renderTo : 'T_order-courierReport-body'
	});
});
