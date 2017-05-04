//时间默认值
order.queryAndResolveBind.getTargetDate = function(date, day) {
	var d, s, t, t2;
	var MinMilli = 1000 * 60;
	var HrMilli = MinMilli * 60;
	var DyMilli = HrMilli * 24;
	t = Date.parse(date);
	t2 =  new Date(t+day*DyMilli);
	t2.setHours(0);
	t2.setMinutes(0);
	t2.setSeconds(0);
	t2.setMilliseconds(0);	
	return t2;
};

(function() {
	// 获得当前组织对应的车队
	Ext.Ajax.request({
		url : order.realPath('querySuperOrg.action'),
		async : false,
		success : function(response) {
			var json = Ext.decode(response.responseText);
			Ext.apply(order, {
				fleetCode : json.fleetCode
			});
		},
		exception : function(response) {
			var result = Ext.decode(response.responseText);
			Ext.ux.Toast.msg(order.queryAndResolveBind.i18n('pkp.order.orderHandle.warning'), result.message, 'error', 3000);
		}
	});
})();

//定义model
Ext.define('Foss.order.queryAndResolveBind.pdaSignModel', {
	extend : 'Ext.data.Model',
	fields : [ {
		name : 'id',
		type : 'string'
	}, {
		name : 'deviceNo',
		type : 'string'
	}, // PDA设备号
	{
		name : 'driverName',
		type : 'string'
	}, // 司机姓名
	{
		name : 'driverCode',
		type : 'string'
	}, // 司机编码
	{
		name : 'vehicleNo',
		type : 'string'
	}, // 车牌号
	{
		name : 'empPhone',
		type : 'string'
	}, // 司机手机
	{
		name : 'status',
		type : 'string'
	}, // 绑定状态
	{
		name : 'signBeginTime',
		type : 'date',
		convert : dateConvert
	}, // 签到开始时间
	{
		name : 'signEndTime',
		type : 'date',
		convert : dateConvert
	}, // 签到结束时间
	{
		name : 'regionName',
		type : 'string'
	}, // 区域
	{
		name : 'createTime',
		type : 'date',
		convert : dateConvert
	}, // 签到时间
	{
		name : 'unbundleReason',
		type : 'string'
	} // 解绑原因
	]
});

// 定义签到数据store
Ext.define('Foss.order.queryAndResolveBind.pdaSignStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.order.queryAndResolveBind.pdaSignModel',
	// 分页
	pageSize : 10,
	proxy : {
		// 代理的类型为ajax代理
		type : 'ajax',
		// 提交方式
		actionMethods : 'POST',
		url : order.realPath('querySignedInfo.action'),
		// 定义一个读取器
		reader : {
			// 以JSON的方式读取
			type : 'json',
			// 定义读取JSON数据的根对象
			root : 'vo.pdasignList',
			//返回总数
 			totalProperty : 'totalCount'
		}
	},
	listeners : {
		// 查询事件
		beforeload : function(s, operation, eOpts) {
			// 执行查询，首先查询条件，为全局变量，在查询条件的FORM创建时生成
			var serachParms = Ext.getCmp(
					'T_order-queryAndResolveBindIndex_content').getQueryForm()
					.getValues();
			var dto = '';
			Ext.apply(operation, {
				params : {
					'vo.dto.driverCode' : serachParms.driverCode,
					'vo.dto.driverName' : serachParms.driverName,
					'vo.dto.deviceNo' : serachParms.deviceNo,
					'vo.dto.vehicleNo' : serachParms.vehicleNo,
					'vo.dto.status' : serachParms.status,
					'vo.dto.empPhone' : serachParms.empPhone,
					'vo.dto.signBeginTime' : serachParms.signBeginTime,
					'vo.dto.signEndTime' : serachParms.signEndTime
				}
			});
		}
	}
});

Ext.define(
				'Foss.order.queryAndResolveBind.unBundlePanel',
				{
					extend : 'Ext.form.Panel',
					layout : 'column',
					cls : 'autoHeight',
					bodyCls : 'autoHeight',
					defaults : {
						margin : '5 10 5 10',
						xtype : 'textfield',
						labelWidth : 90
					},
					items : [
							{
								fieldLabel : 'id',
								columnWidth : 1,
								name : 'id',
								hidden : true
							},
							{
								fieldLabel : order.queryAndResolveBind.i18n('pkp.order.queryAndResolveBind.unbundleReason'),
								xtype : 'textareafield',
								columnWidth : 1,
								name : 'unbundleReason',
								emptyText : order.queryAndResolveBind.i18n('pkp.order.queryAndResolveBind.inputReason'),
								allowBlank : false,
								maxLength : 500
							},
							{
								xtype : 'button',
								text : order.queryAndResolveBind.i18n('pkp.order.queryAndResolveBind.unbundle'),
								columnWidth : .25,
								handler : function() {
									var form = this.up('form').getForm();
									var win = this.up('window')
									if (!form.isValid()) {
										Ext.ux.Toast.msg(order.queryAndResolveBind.i18n('pkp.order.queryAndResolveBind.warning'),
												order.queryAndResolveBind.i18n('pkp.order.queryAndResolveBind.unbundleReminder'));
										return;
									}
									var unBundleValues = form.getValues();
									Ext.Ajax
											.request({
												url : order
														.realPath('handResolveBind.action'),
												jsonData : {
													"vo" : {
														"pdaEntity" : unBundleValues
													}
												},
												success : function(response) {
													var text = response.responseText;
													var result = Ext
															.decode(response.responseText);
													win.hide();
													order.queryAndResolveBind.pagingBar.moveFirst();
													Ext.ux.Toast.msg(order.queryAndResolveBind.i18n('pkp.order.queryAndResolveBind.warning'),
															result.message);
												}
											});
								}
							}, {
								xtype : 'container',
								html : '&nbsp;',
								columnWidth : .5
							}, {
								xtype : 'button',
								text : order.queryAndResolveBind.i18n('pkp.order.queryAndResolveBind.cancel'),
								columnWidth : .25,
								handler : function() {
									this.up('window').hide();
								}
							} ]
				});

order.queryAndResolveBind.unBundlePanel = Ext
		.create('Foss.order.queryAndResolveBind.unBundlePanel');
// 编辑任务订单状态window
Ext.define('Foss.order.queryAndResolveBind.unBundleWindow', {
	extend : 'Ext.window.Window',
	width : 300,
	height : 180,
	title : order.queryAndResolveBind.i18n('pkp.order.queryAndResolveBind.unbundleReason'),
	layout : 'column',
	border : false,
	// 将window的关闭事件close 设成 hide
	closeAction : 'hide',
	items : [ order.queryAndResolveBind.unBundlePanel ]
});

// 查询条件
Ext
		.define(
				'Foss.order.queryAndResolveBind.QueryForm',
				{
					extend : 'Ext.form.Panel',
					title : order.queryAndResolveBind.i18n('pkp.order.queryAndResolveBind.queryCondition'),
					frame : true,
					cls : 'autoHeight',
					bodyCls : 'autoHeight',
					layout : {
						type : 'column'
					},
					defaults : {
						xtype : 'textfield',
						margin : '5 10 5 10',
						labelWidth : 80
					},
					initComponent : function() {
						var me = this;

						Ext
								.applyIf(
										me,
										{
											items : [
													{
														fieldLabel : order.queryAndResolveBind.i18n('pkp.order.queryAndResolveBind.driverCode'),
														columnWidth : .25,
														name : 'driverCode'
													},
													{
														fieldLabel : order.queryAndResolveBind.i18n('pkp.order.queryAndResolveBind.driverName'),
														columnWidth : .25,
														name : 'driverName'
													},
													{
														fieldLabel : order.queryAndResolveBind.i18n('pkp.order.queryAndResolveBind.driverMobile'),
														columnWidth : .25,
														name : 'empPhone'
													},
													{
														fieldLabel : order.queryAndResolveBind.i18n('pkp.order.queryAndResolveBind.deviceNo'),
														columnWidth : .25,
														name : 'deviceNo'
													},
													{
														fieldLabel : order.queryAndResolveBind.i18n('pkp.order.queryAndResolveBind.vehicleNo'),
														columnWidth : .25,
														xtype : 'commontruckselector',
														loopOrgCode : order.fleetCode,
														name : 'vehicleNo'
													},
													{
														xtype : 'combobox',
														name : 'status',
														columnWidth : .25,
														fieldLabel : order.queryAndResolveBind.i18n('pkp.order.queryAndResolveBind.bundleStatus'),
														queryModel : 'local',
														displayField : 'valueName',
														valueField : 'valueCode',
														editable : false,
														value : 'BUNDLE',
														store : FossDataDictionary
																.getDataDictionaryStore(
																		'PKP_PDA_SIGN_STATUS',
																		null,
																		{
																			'valueCode' : '',
																			'valueName' : '全部'
																		})
													},
													{
														xtype: 'rangeDateField',
														fieldLabel: order.queryAndResolveBind.i18n('pkp.order.queryAndResolveBind.signinTime'),
														fieldId: 'Foss_order_QueryForm_signTime_ID',
														dateType: 'datetimefield_date97',
														fromName: 'signBeginTime',
														toName: 'signEndTime',
														disallowBlank:true,
														editable:false,
														fromValue: Ext.Date.format(order.queryAndResolveBind.getTargetDate(new Date(),-1),'Y-m-d H:i:s'),
														toValue: Ext.Date.format(new Date(),'Y-m-d H:i:s'),
														columnWidth: .5
													},
													{
														border : 1,
														xtype : 'container',
														columnWidth : 1,
														defaultType : 'button',
														layout : 'column',
														items : [
																{
																	text : order.queryAndResolveBind.i18n('pkp.order.queryAndResolveBind.reset'),
																	columnWidth : .08,
																	handler : function() {
																		var myform = this
																				.up('form');
																		myform.getForm().reset();
																		myform.getForm().findField('signBeginTime').setValue(Ext.Date.format(order.queryAndResolveBind.getTargetDate(new Date(),-1),'Y-m-d H:i:s'));
																		myform.getForm().findField('signEndTime').setValue(Ext.Date.format(new Date(),'Y-m-d H:i:s'));
																	}
																},
																{
																	xtype : 'container',
																	border : false,
																	columnWidth : .84,
																	html : '&nbsp;'
																},
																{
																	text : order.queryAndResolveBind.i18n('pkp.order.queryAndResolveBind.query'),
																	cls : 'yellow_button',
																	columnWidth : .08,
																	disabled:!order.queryAndResolveBind.isPermission('queryandresolvebindindex/queryandresolvebindindexquerybutton'),
																	hidden:!order.queryAndResolveBind.isPermission('queryandresolvebindindex/queryandresolvebindindexquerybutton'),
																	handler : function() {
																		var myForm = this
																				.up(
																						'form')
																				.getForm();
																		if (myForm
																				.isValid()) {
																			var resultGrid = Ext
																					.getCmp(
																							'T_order-queryAndResolveBindIndex_content')
																					.getResultGrid();
																			resultGrid
																					.show();
																			order.queryAndResolveBind.pagingBar.moveFirst();
																		}
																	}
        		}]
        	}
            ]
        });

        me.callParent(arguments);
    }
});

Ext.define('Foss.order.queryAndResolveBind.driverSignGrid', {
																			extend  : 'Ext.grid.Panel',
					title : order.queryAndResolveBind.i18n('pkp.order.queryAndResolveBind.signinInfo'),
					frame : true,
					hidden : true,
					emptyText : order.queryAndResolveBind.i18n('pkp.order.queryAndResolveBind.queryResultNull'),
					cls : 'autoHeight',
					bodyCls : 'autoHeight',
					columns : [
							{
								header : order.queryAndResolveBind.i18n('null'),
								align : 'center',
								dataIndex : 'id',
								flex : 1,
								hidden : true
							},
							{
								header : order.queryAndResolveBind.i18n('pkp.order.queryAndResolveBind.driverCode'),
								align : 'center',
								dataIndex : 'driverCode',
								flex : 1
							},
							{
								header : order.queryAndResolveBind.i18n('pkp.order.queryAndResolveBind.driverName'),
								align : 'center',
								dataIndex : 'driverName',
								flex : 1
							},
							{
								header : order.queryAndResolveBind.i18n('pkp.order.queryAndResolveBind.vehicleNo'),
								align : 'center',
								dataIndex : 'vehicleNo',
								flex : 1
							},
							{
								header : order.queryAndResolveBind.i18n('pkp.order.queryAndResolveBind.mobile'),
								align : 'center',
								dataIndex : 'empPhone',
								flex : 1
							},
							{
								header : order.queryAndResolveBind.i18n('pkp.order.queryAndResolveBind.deviceNo'),
								align : 'center',
								dataIndex : 'deviceNo',
								flex : 1
							},
							{
								header : order.queryAndResolveBind.i18n('pkp.order.queryAndResolveBind.region'),
								align : 'center',
								dataIndex : 'regionName'
							},
							{
								header : order.queryAndResolveBind.i18n('pkp.order.queryAndResolveBind.signinTime'),
								align : 'center',
								dataIndex : 'createTime',
								flex : 1,
								xtype : 'datecolumn',
								format : 'Y-m-d H:i:s'
							},
							{
								header : order.queryAndResolveBind.i18n('pkp.order.queryAndResolveBind.bundleStatus'),
								align : 'center',
								dataIndex : 'status',
								flex : 1,
								renderer : function(value) {
									return FossDataDictionary
											.rendererSubmitToDisplay(value,
													'PKP_PDA_SIGN_STATUS');
								}
							} ],
					constructor : function(config) {
						var me = this, cfg = Ext.apply({}, config);
						me.store = Ext.create('Foss.order.queryAndResolveBind.pdaSignStore');
						me.dockedItems = [ {
							xtype : 'toolbar',
							dock : 'bottom',
							layout : 'column',
							defaults : {
								margin : '0 0 5 3'
							},
							items : [ {
								xtype : 'button',
								text : order.queryAndResolveBind.i18n('pkp.order.queryAndResolveBind.unbundle'),
								disabled : !order.queryAndResolveBind.isPermission('queryandresolvebindindex/queryandresolvebindindexunbundlebutton'),
								hidden : !order.queryAndResolveBind.isPermission('queryandresolvebindindex/queryandresolvebindindexunbundlebutton'),
								name : 'unbundle',
								handler : function() {
									var mygrid = this.up('gridpanel');
									var selectRow = mygrid.getSelectionModel()
											.getSelection();
									if (selectRow.length == 0) {
										Ext.ux.Toast.msg(order.queryAndResolveBind.i18n('pkp.order.queryAndResolveBind.warning'),
												order.queryAndResolveBind.i18n('pkp.order.queryAndResolveBind.chooseSignin'));
									} else {
										var model = Ext.ModelManager
												.create(selectRow[0].data,
														'Foss.order.queryAndResolveBind.pdaSignModel');
										order.queryAndResolveBind.unBundlePanel
												.getForm().loadRecord(model);
										var win = Ext
												.create(
														'Foss.order.queryAndResolveBind.unBundleWindow')
												.show();
									}
								}
							} ]
						} ];
						me.bbar = Ext.create('Deppon.StandardPaging', {
							store : me.store,
							plugins: 'pagesizeplugin',
							displayInfo: true
						});
						order.queryAndResolveBind.pagingBar = me.bbar;
						me.callParent(arguments);
					}
				});

Ext.onReady(function() {
	Ext.QuickTips.init();
	var queryForm = Ext.create("Foss.order.queryAndResolveBind.QueryForm");
	var driverSignGrid = Ext.create(
			"Foss.order.queryAndResolveBind.driverSignGrid", {
				id : "Foss_order_queryAndResolveBind_driverSignGrid_Id",
				listeners : {
					itemclick : function(dv, record, item, index, e) {
						var status = record.data.status;
						var itmes = Ext.getCmp(
								'T_order-queryAndResolveBindIndex_content')
								.getResultGrid().getDockedItems(
										'toolbar[dock="bottom"]');
						var itme = itmes[0];
						if (status == 'UNBUNDLE') {
							itme.setDisabled(true);
						} else {
							itme.setDisabled(false);
						}
					}
				}
			});

	Ext.create('Ext.panel.Panel', {
		id : 'T_order-queryAndResolveBindIndex_content',
		cls : "panelContentNToolbar",
		bodyCls : 'panelContentNToolbar-body',
		layout : 'auto',
		getQueryForm : function() {
			return queryForm;
		},
		getResultGrid : function() {
			return driverSignGrid;
		},
		items : [ queryForm, driverSignGrid ],
		renderTo : 'T_order-queryAndResolveBindIndex-body'
	});
});
