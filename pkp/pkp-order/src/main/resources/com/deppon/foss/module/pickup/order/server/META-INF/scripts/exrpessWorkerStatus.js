// 查询的最大天数为31天
order.expressWorkerStatus.MAXDAYS = 31 * 24 * 60 * 60 * 1000;

Ext.define('Foss.order.expressWorkerStatus.GlobalViewModel', {
	extend: 'Ext.data.Model',
	//定义字段
	fields: [
	  {name: 'name',type:'string'},
	  {name: 'value',type:'string'}
	]
	});

//接收订单状态store
Ext.define('Foss.order.expressWorkerStatus.RecieveOrderStore',{
extend: 'Ext.data.Store',
model: 'Foss.order.expressWorkerStatus.GlobalViewModel',
data : {
'items':[
{name : '全部', value : 'ALL'},
{name : '开启', value : 'OPEN'},
{name : '暂停', value : 'STOP'}
]
},
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
  
Ext.define('Foss.order.expressWorkerStatus.expressWorkerStatusQueryForm', {
	id : 'Foss_order_expressWorkerStatusQueryForm_Id',
	extend : 'Ext.form.Panel',
	cls : 'autoHeight',
	bodyCls : 'autoHeight',
	collapsible : true,
	title : order.expressWorkerStatus.i18n('pkp.order.expressOrderHandle.queryCondition'),
	frame : true,
	layout : {
		type : 'column'
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.items = [
		         {
		            fieldLabel : order.expressWorkerStatus.i18n('pkp.order.expressWorkerStatus.operateBigDeliverRegCode'),
		            name : 'operateBigRegionCode',
		            xtype : 'combobox',
					displayField : 'name',
					valueField : 'code',
					editable : false,
					value : order.expressWorkerStatus.operateBigRegionCode,
					store : Ext.create('Ext.data.Store',{
						fields:['code','name'],
						data:[{'code':order.expressWorkerStatus.operateBigRegionCode,
							   'name':order.expressWorkerStatus.operateBigRegionName}]
					})
		         },
				{
					fieldLabel : order.expressWorkerStatus.i18n('pkp.order.queryExpressDispatchOrder.bigDeliverRegCode'),
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
					fieldLabel : order.expressWorkerStatus.i18n('pkp.order.queryExpressDispatchOrder.smallDeliverRegCode'),
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
					fieldLabel : order.expressWorkerStatus.i18n('pkp.order.queryExpressDispatchOrder.driver'),
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
					fieldId : 'Foss_order_QueryForm_orderHandle_ID',
					fieldLabel : order.expressWorkerStatus.i18n('pkp.order.expressWorkerStatus.queryBeginTime'),
					dateType : 'datetimefield_date97',
					editable : false,
					allowBlank : false,
					disabled:true,
					fromName : 'queryBeginTime',
					toName : 'queryEndTime',
					fromValue : Ext.Date.format(new Date(new Date().getFullYear(), new Date().getMonth(), new Date().getDate(), 0, 0, 0), 'Y-m-d H:i:s'),
					toValue : Ext.Date.format(new Date(new Date().getFullYear(), new Date().getMonth(), new Date().getDate(), 23, 59, 59), 'Y-m-d H:i:s'),
					columnWidth : .50,
					labelWidth : 80
				},{//14.10.14 gcl 添加接收状态 查询条件
		    		fieldLabel : '接收状态',
		    		name : 'recieveOrderStatus',
		    		xtype : 'combobox',
		    		editable : false,
		    		displayField : 'name',
		    		valueField : 'value',
		    		queryMode:'local',
		    		triggerAction:'all',
		    		store :  Ext.create('Foss.order.expressWorkerStatus.RecieveOrderStore'),
		    		columnWidth:.25
		    	},
				{
					xtype : 'container',
					border : false,
					html : '&nbsp;',
					height : 24,
					columnWidth : .25
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
								text : order.expressWorkerStatus.i18n('pkp.order.expressWorkerStatus.reset'),
								columnWidth : .08,
								handler : function() {
									var myform = this.up('form');
									myform.getForm().reset();
									// 查询开始时间
									myform.getForm().findField('Foss_order_QueryForm_orderHandle_ID_first').setValue(
											Ext.Date.format(new Date(new Date().getFullYear(), new Date().getMonth(), new Date().getDate(), 0, 0, 0), 'Y-m-d H:i:s'));
									// 查询结束时间
									myform.getForm().findField('Foss_order_QueryForm_orderHandle_ID_second').setValue(
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
								text : order.expressWorkerStatus.i18n('pkp.order.expressWorkerStatus.query'),
								cls : 'yellow_button',
								columnWidth : .08,
								handler : function() {
									var myForm = this.up('form').getForm();
									var beginTime = Ext.Date.parse(myForm.findField('Foss_order_QueryForm_orderHandle_ID_first').getValue(), 'Y-m-d H:i:s'), endTime = Ext.Date
											.parse(myForm.findField('Foss_order_QueryForm_orderHandle_ID_second').getValue(), 'Y-m-d H:i:s');
									
									// 判断结束日期和开始日期是否在范围之内
									if (endTime.getTime() - beginTime.getTime() > order.expressWorkerStatus.MAXDAYS) {
										Ext.ux.Toast.msg(order.expressWorkerStatus.i18n('pkp.order.expressWorkerStatus.tips'), order.expressWorkerStatus
												.i18n('pkp.order.expressWorkerStatus.maxDays'));
										return;
									}
									if (myForm.isValid()) {
										var me = this;
										var resultGrid = Ext.getCmp('T_order-expressWorkerStatus_content').getGridPanel();
										// resultGrid.show();
										// order.expressWorkerStatus.pagingBar.moveFirst();
										var store = resultGrid.getStore();
										store.loadPage(1, {
											callback : function(records, operation, success) {
												// 抛出异常
												var rawData = resultGrid.store.proxy.reader.rawData;
												if (!success && !rawData.isException) {
													Ext.Msg.alert(order.expressWorkerStatus.i18n('pkp.order.expressWorkerStatus.tips'), rawData.message);
													return false;
												}

												// 正常返回
												if (success) {
													var result = Ext.decode(operation.response.responseText);
													if (result.expressWorerStatusConditionVo.expressWorkerStatusTatal.length > 0) {
														resultGrid.show();						
													} else {
														Ext.Msg.alert(order.expressWorkerStatus.i18n('pkp.order.expressWorkerStatus.tips'), order.expressWorkerStatus
																.i18n('pkp.order.expressWorkerStatus.queryNullResult'));
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
Ext.define('Foss.order.expressWorkerStatus.expressWorkerStatusEntity', {
	extend : 'Ext.data.Model',
	idproperty : 'id',
	idgen : 'uuid',
	fields : [ {
		name : 'emp_name',// 姓名
		type : 'string',
		defaultValue : null
	}, {
		name : 'emp_code',// 工号
		type : 'string',
		defaultValue : null,
	}, {
		name : 'bigRegion',// 收派大区
		type : 'string'
	}, {
		name : 'smallRegion',// 收派小区
		type : 'string',
		defaultValue : null
	}, {
		name : 'emptype',// 属性
		type : 'string'
	}, {
		name : 'getOrder',// 已接受订单
		type : 'int',
		defaultValue : 0
	}, {
		name : 'receiveOrder',// 已完成订单
		type : 'int',
		defaultValue : 0
	}, {
		name : 'noOrderWaybill',// 无订单开单
		type : 'int',
		defaultValue : 0
	}, {
		name : 'getWaybill',// 应派送运单
		type : 'int',
		defaultValue : 0
	}, {
		name : 'deliverWaybill',// 已派送运单
		type : 'int',
		defaultValue : 0
	}, {
		name : 'modifyTime',// 已派送运单
		type : 'date',
		defaultValue : null,
		convert : function(value) {
			if (value != null) {
				var date = new Date(value);
				return date;
			}
		}
	}, {
		name : 'empStatus',// 快递员状态
		type : 'string',
		defaultValue : null
	}, {
		name : 'active',// 工作状态
		type : 'string',
		defaultValue : null
	} ]
});

/**
 * 快递员工作状态Store
 */
Ext.define('Foss.order.expressWorkerStatus.expressWorkerStatusStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.order.expressWorkerStatus.expressWorkerStatusEntity',
	pageSize : 20,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url : order.realPath('queryExpressWorkerComplete.action'),
		reader : {
			type : 'json',
			root : 'expressWorerStatusConditionVo.expressWorkerStatusTatal',
			totalProperty : 'totalCount'
		}
	},
	listeners : {
		// 查询事件
		beforeload : function(s, operation, eOpts) {
			// 执行查询，首先查询条件，为全局变量，在查询条件的FORM创建时生成
			var serachParms = Ext.getCmp('T_order-expressWorkerStatus_content').getQueryForm().getValues();
			/*
			 * var driverCodes = new Array();
			 * driverCodes.push(serachParms.driverCode);
			 */
			// TODO
			var params = {
				'expressWorerStatusConditionVo.operateBigRegionCode' : order.expressWorkerStatus.operateBigRegionCode,
				'expressWorerStatusConditionVo.bigRegionCode' : serachParms.bigRegionCode,
				'expressWorerStatusConditionVo.smallRegionCode' : serachParms.smallRegionCode,
				'expressWorerStatusConditionVo.expressWorkerCode' : serachParms.driverCode,
				'expressWorerStatusConditionVo.startDate' : Ext.Date.parse(Ext.Date.format(new Date(new Date().getFullYear(), new Date().getMonth(), new Date().getDate(), 0, 0, 0), 'Y-m-d H:i:s'), 'Y-m-d H:i:s'),//AUTO-177 gcl 14.7.17 日期为当天
				'expressWorerStatusConditionVo.endDate' : Ext.Date.parse(Ext.Date.format(new Date(new Date().getFullYear(), new Date().getMonth(), new Date().getDate(), 23, 59, 59), 'Y-m-d H:i:s'), 'Y-m-d H:i:s'),
				'expressWorerStatusConditionVo.recieveOrderStatus' :serachParms.recieveOrderStatus  //14.10.8 gcl
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

Ext.define('Foss.pricing.exrpessWorkerStatus.exrpessWorkerStatusGridPanel', {
	extend : 'Ext.grid.Panel',
	title : order.expressWorkerStatus.i18n('pkp.order.expressWorkerStatus.queryResult'),
	frame : true,
	cls : 'autoHeight',
	bodyCls : 'autoHeight',
	sortableColumns : true,
	enableColumnHide : false,
	enableColumnMove : false,
	stripeRows : true, // 交替行效果
	selType : "rowmodel", // 选择类型设置为：行选择
	emptyText : order.expressWorkerStatus.i18n('pkp.order.expressWorkerStatus.queryNullResult'),// 查询结果为空
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
	stopReceiveOrder : function() {
		// 暂停接受订单
		var me = this;
		var workerStatus = new Array();
		var selector = me.getSelectionModel().getSelection();
		if (selector.length < 1) {
			Ext.ux.Toast.msg(order.expressWorkerStatus.i18n('pkp.order.expressWorkerStatus.tips'), order.expressWorkerStatus.i18n('pkp.order.expressWorkerStatus.tips.noSelected'),
					'error', 3000);
			return;
		}
		;
		for ( var i = 0; i < selector.length; i++) {
			workerStatus.push(selector[i].get('emp_code'));
		}
		;
		var params = {
			'expressWorerStatusConditionVo.changeStatusEmpCodes' : workerStatus
		};
		// 提交对应的数据
		Ext.Ajax.request({
			url : order.realPath('stopExpressWorker.action'),
			params : params,
			success : function(response) {
				Ext.ux.Toast.msg(order.expressWorkerStatus.i18n('pkp.order.expressWorkerStatus.tips'), order.expressWorkerStatus
						.i18n('pkp.order.expressWorkerStatus.tips.stopSuccess'));
			},
			exception : function(response) {
				var result = Ext.decode(response.responseText);
				Ext.MessageBox.alert(order.expressWorkerStatus.i18n('pkp.order.orderHandle.warning'), result.message);
				return;
			}
		});
		this.store.load();
	},
	openReceiveOrder : function() {
		// 开启接受订单
		var me = this;
		var workerStatus = new Array();
		var selector = me.getSelectionModel().getSelection();
		if (selector.length < 1) {
			Ext.ux.Toast.msg(order.expressWorkerStatus.i18n('pkp.order.expressWorkerStatus.tips'), order.expressWorkerStatus.i18n('pkp.order.expressWorkerStatus.tips.noSelected'),
					'error', 3000);
			return;
		}
		;
		for ( var i = 0; i < selector.length; i++) {
			workerStatus.push(selector[i].get('emp_code'));
		}
		;
		var params = {
			'expressWorerStatusConditionVo.changeStatusEmpCodes' : workerStatus
		};
		// 提交对应的数据
		Ext.Ajax.request({
			url : order.realPath('openExpressWorker.action'),
			params : params,
			success : function(response) {
				Ext.ux.Toast.msg(order.expressWorkerStatus.i18n('pkp.order.expressWorkerStatus.tips'), order.expressWorkerStatus
						.i18n('pkp.order.expressWorkerStatus.tips.openSuccess'));
			},
			exception : function(response) {
				var result = Ext.decode(response.responseText);
				Ext.MessageBox.alert(order.expressWorkerStatus.i18n('pkp.order.expressWorkerStatus.tips'), result.message);
			}
		});
		this.store.load();
	},
	exportData : function() {

		if (!Ext.fly('downloadAttachFileForm')) {
			var frm = document.createElement('form');
			frm.id = 'downloadAttachFileForm';
			frm.style.display = 'none';
			document.body.appendChild(frm);
		}
		// 执行查询，首先查询条件，为全局变量，在查询条件的FORM创建时生成
		var serachParms = Ext.getCmp('T_order-expressWorkerStatus_content').getQueryForm().getValues();
		/*
		 * var driverCodes = new Array();
		 * driverCodes.push(serachParms.driverCode);
		 */
		// TODO
		var params = {
			'expressWorerStatusConditionVo.operateBigRegionCode' : order.expressWorkerStatus.operateBigRegionCode,
			'expressWorerStatusConditionVo.bigRegionCode' : serachParms.bigRegionCode,
			'expressWorerStatusConditionVo.smallRegionCode' : serachParms.smallRegionCode,
			'expressWorerStatusConditionVo.expressWorkerCode' : serachParms.driverCode,
			'expressWorerStatusConditionVo.startDate' : Ext.Date.parse(Ext.Date.format(new Date(new Date().getFullYear(), new Date().getMonth(), new Date().getDate(), 0, 0, 0), 'Y-m-d H:i:s'), 'Y-m-d H:i:s'),//AUTO-177 gcl 14.7.17 日期为当天
			'expressWorerStatusConditionVo.endDate' : Ext.Date.parse(Ext.Date.format(new Date(new Date().getFullYear(), new Date().getMonth(), new Date().getDate(), 23, 59, 59), 'Y-m-d H:i:s'), 'Y-m-d H:i:s'),
			'expressWorerStatusConditionVo.recieveOrderStatus' :serachParms.recieveOrderStatus  //14.10.8 gcl
		};

		Ext.Ajax.request({
			url : order.realPath('exportWorkerComplete.action'),
			method : 'post',
			form : Ext.fly('downloadAttachFileForm'),
			params : params,
			isUpload : true,
			success : function(response) {
				Ext.ux.Toast.msg(order.expressWorkerStatus.i18n('pkp.order.expressWorkerStatus.tips'), order.expressWorkerStatus
						.i18n('pkp.order.expressWorkerStatus.tips.exportSuccess'));
			},
			exception : function(response) {
				var result = Ext.decode(response.responseText);
				Ext.MessageBox.alert(order.expressWorkerStatus.i18n('pkp.order.expressWorkerStatus.tips'), result.message);
			}
		})
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.columns = [ {
			xtype : 'rownumberer',
			width : 40,
			text : order.expressWorkerStatus.i18n('pkp.order.expressWorkerStatus.rownum')
		// 序号
		}, {
			text : order.expressWorkerStatus.i18n('pkp.order.expressWorkerStatus.empName'),// 员工名称
			dataIndex : 'emp_name'
		}, {
			text : order.expressWorkerStatus.i18n('pkp.order.expressWorkerStatus.empCode'),// 员工工号
			dataIndex : 'emp_code'
		}, {
			text : order.expressWorkerStatus.i18n('pkp.order.expressWorkerStatus.bigRegoin'),// 快递大区
			dataIndex : 'bigRegion'
		}, {
			text : order.expressWorkerStatus.i18n('pkp.order.expressWorkerStatus.smallRegoin'),// 快递小区
			dataIndex : 'smallRegion'
		}, {
			text : order.expressWorkerStatus.i18n('pkp.order.expressWorkerStatus.empType'),// 属性
			dataIndex : 'emptype'
		}, {
			text : order.expressWorkerStatus.i18n('pkp.order.expressWorkerStatus.getOrder'),// 获取订单
			dataIndex : 'getOrder'
		}, {
			text : order.expressWorkerStatus.i18n('pkp.order.expressWorkerStatus.receiveOrder'),// 已接货订单
			dataIndex : 'receiveOrder'
		}, {
			text : order.expressWorkerStatus.i18n('pkp.order.expressWorkerStatus.noOrderWaybill'),// 无订单开单
			dataIndex : 'noOrderWaybill'
		}, {
			text : order.expressWorkerStatus.i18n('pkp.order.expressWorkerStatus.getWaybills'),// 货物运单
			dataIndex : 'getWaybill'
		}, {
			text : order.expressWorkerStatus.i18n('pkp.order.expressWorkerStatus.deliverWaybills'),// 应送货运单
			dataIndex : 'deliverWaybill'
		} ];
		me.store = Ext.create('Foss.order.expressWorkerStatus.expressWorkerStatusStore', {
			autoLoad : false,
			pageSize : 20
		});

		me.selModel = Ext.create('Ext.selection.CheckboxModel', {// 多选框
			mode : 'MULTI',
			checkOnly : true
		});

		me.tbar = [{
			text : order.expressWorkerStatus.i18n('pkp.order.expressWorkerStatus.stopReceiveOrder'),// 暂停接收订单:
			// !pricing.effectivePlan.isPermission('effectivePlan/effectivePlanImportbutton'),
			handler : function() {
				me.stopReceiveOrder();
			}
		}, {
			text : order.expressWorkerStatus.i18n('pkp.order.expressWorkerStatus.openReceiveOrder'),// 开启接收订单:
			// !pricing.effectivePlan.isPermission('effectivePlan/effectivePlanImportbutton'),
			handler : function() {
				me.openReceiveOrder();
			}
		}, {
			text : order.expressWorkerStatus.i18n('pkp.order.expressWorkerStatus.exportData'),// 导出功能:
			// !pricing.effectivePlan.isPermission('effectivePlan/effectivePlanImportbutton'),
			handler : function() {
				me.exportData();
			}
		}, {
			text : order.expressWorkerStatus.i18n('pkp.order.expressWorkerStatus.refreshData'),// 刷新:
			// !pricing.effectivePlan.isPermission('effectivePlan/effectivePlanImportbutton'),
			handler : function() {
				order.expressWorkerStatus.pagingBar.moveFirst();
			}
		} ];

		me.bbar = me.getPagingToolbar();
		order.expressWorkerStatus.pagingBar = me.bbar;

		me.callParent([ cfg ]);
	}
});

Ext.onReady(function() {
	// 1.禁止使用全局变量,可以使用module标签生成的模块名的object对象{}
	// 用法：模块名.自定义变量
	// 2.对象都是用Ext.define定义的方式声明
	Ext.QuickTips.init();
	var queryForm = Ext.create('Foss.order.expressWorkerStatus.expressWorkerStatusQueryForm');
	var gridPanel = Ext.create('Foss.pricing.exrpessWorkerStatus.exrpessWorkerStatusGridPanel');
	Ext.create('Ext.panel.Panel', {
		id : 'T_order-expressWorkerStatus_content',
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
		renderTo : 'T_order-expressWorkerStatus-body',
		listeners:{
			'boxready':function(){
				var form = this.getQueryForm();
				order.expressWorkerStatus.operateBigRegionCode="";
				order.expressWorkerStatus.operateBigRegionName="";
				//调用后台根据当前用户机构code获取所属快递大区编码
				Ext.Ajax.request({
					url:order.realPath('../baseinfo/getExpressPartBigRegionByPartCode.action'),
					params:{
						'expressPartSalesDeptVo.expressPartSalesDeptQueryDto.expressPartCode':FossUserContext.getCurrentDept().code
					},
					success:function(response){
						var result = Ext.decode(response.responseText);	
						if(result.expressPartSalesDeptVo.orgAdministrativeInfoEntity!=null){
							order.expressWorkerStatus.operateBigRegionName=result.expressPartSalesDeptVo.orgAdministrativeInfoEntity.name;	
							order.expressWorkerStatus.operateBigRegionCode=result.expressPartSalesDeptVo.orgAdministrativeInfoEntity.code;										
							form.getForm().findField('operateBigRegionCode').setValue(order.expressWorkerStatus.operateBigRegionCode);
							form.getForm().findField('operateBigRegionCode').setRawValue(order.expressWorkerStatus.operateBigRegionName);
						}
					},
					exception:function(response){
						var result = Ext.decode(response.responseText);
						Ext.MessageBox.show({
							title : '温馨提示',
							msg : result.message,
							width : 300,
							buttons : Ext.Msg.OK,
							icon : Ext.MessageBox.WARNING
						});
					}			
				});
			}
		}
	});
});
