//时间默认值
order.queryAndResolveExpressBind.getTargetDate = function(date, day) {
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

//定义model
Ext.define('Foss.order.queryAndResolveExpressBind.pdaSignModel', {
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
Ext.define('Foss.order.queryAndResolveExpressBind.pdaSignStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.order.queryAndResolveExpressBind.pdaSignModel',
	// 分页
	pageSize : 10,
	proxy : {
		// 代理的类型为ajax代理
		type : 'ajax',
		// 提交方式
		actionMethods : 'POST',
		url : order.realPath('queryExpressSignedInfo.action'),
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
					'T_order-queryAndResolveExpressBindIndex_content').getQueryForm()
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
				'Foss.order.queryAndResolveExpressBind.unBundlePanel',
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
								fieldLabel : order.queryAndResolveExpressBind.i18n('pkp.order.queryAndResolveExpressBind.unbundleReason'),
								xtype : 'textareafield',
								columnWidth : 1,
								name : 'unbundleReason',
								emptyText : order.queryAndResolveExpressBind.i18n('pkp.order.queryAndResolveExpressBind.inputReason'),
								allowBlank : false,
								maxLength : 500
							},
							{
								xtype : 'button',
								text : order.queryAndResolveExpressBind.i18n('pkp.order.queryAndResolveExpressBind.unbundle'),
								columnWidth : .25,
								plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin', {
									seconds: 3
								}),
								handler : function() {
									var form = this.up('form').getForm();
									var win = this.up('window')
									if (!form.isValid()) {
										Ext.ux.Toast.msg(order.queryAndResolveExpressBind.i18n('pkp.order.queryAndResolveExpressBind.warning'),
												order.queryAndResolveExpressBind.i18n('pkp.order.queryAndResolveExpressBind.unbundleReminder'));
										return;
									}
									var unBundleValues = form.getValues();
									Ext.Ajax
											.request({
												url : order
														.realPath('handResolveExpressBind.action'),
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
													order.queryAndResolveExpressBind.pagingBar.moveFirst();
													Ext.ux.Toast.msg(order.queryAndResolveExpressBind.i18n('pkp.order.queryAndResolveExpressBind.warning'),
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
								text : order.queryAndResolveExpressBind.i18n('pkp.order.queryAndResolveExpressBind.cancel'),
								columnWidth : .25,
								handler : function() {
									this.up('window').hide();
								}
							} ]
				});

order.queryAndResolveExpressBind.unBundlePanel = Ext
		.create('Foss.order.queryAndResolveExpressBind.unBundlePanel');
// 编辑任务订单状态window
Ext.define('Foss.order.queryAndResolveExpressBind.unBundleWindow', {
	extend : 'Ext.window.Window',
	width : 300,
	height : 180,
	title : order.queryAndResolveExpressBind.i18n('pkp.order.queryAndResolveExpressBind.unbundleReason'),
	layout : 'column',
	border : false,
	// 将window的关闭事件close 设成 hide
	closeAction : 'hide',
	items : [ order.queryAndResolveExpressBind.unBundlePanel ]
});

// 查询条件
Ext.define('Foss.order.queryAndResolveExpressBind.QueryForm',
				{
					extend : 'Ext.form.Panel',
					title : order.queryAndResolveExpressBind.i18n('pkp.order.queryAndResolveExpressBind.queryCondition'),
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
														fieldLabel : order.queryAndResolveExpressBind.i18n('pkp.order.queryAndResolveExpressBind.driverCode'),
														columnWidth : .25,
														name : 'driverCode'
													},
													{
														fieldLabel : order.queryAndResolveExpressBind.i18n('pkp.order.queryAndResolveExpressBind.driverName'),
														columnWidth : .25,
														name : 'driverName'
													},
													{
														fieldLabel : order.queryAndResolveExpressBind.i18n('pkp.order.queryAndResolveExpressBind.driverMobile'),
														columnWidth : .25,
														name : 'empPhone'
													},
													{
														fieldLabel : order.queryAndResolveExpressBind.i18n('pkp.order.queryAndResolveExpressBind.deviceNo'),
														columnWidth : .25,
														name : 'deviceNo'
													},
													{
														fieldLabel : order.queryAndResolveExpressBind.i18n('pkp.order.queryAndResolveExpressBind.vehicleNo'),
														columnWidth : .25,
														name : 'vehicleNo'
													},
													{
														xtype : 'combobox',
														name : 'status',
														columnWidth : .25,
														fieldLabel : order.queryAndResolveExpressBind.i18n('pkp.order.queryAndResolveExpressBind.bundleStatus'),
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
														fieldLabel: order.queryAndResolveExpressBind.i18n('pkp.order.queryAndResolveExpressBind.signinTime'),
														fieldId: 'Foss_order_express_QueryForm_signTime_ID',
														dateType: 'datetimefield_date97',
														fromName: 'signBeginTime',
														toName: 'signEndTime',
														disallowBlank:true,
														editable:false,
														fromValue: Ext.Date.format(order.queryAndResolveExpressBind.getTargetDate(new Date(),-1),'Y-m-d H:i:s'),
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
																	text : order.queryAndResolveExpressBind.i18n('pkp.order.queryAndResolveExpressBind.reset'),
																	columnWidth : .08,
																	handler : function() {
																		var myform = this
																				.up('form');
																		myform.getForm().reset();
																		myform.getForm().findField('signBeginTime').setValue(Ext.Date.format(order.queryAndResolveExpressBind.getTargetDate(new Date(),-1),'Y-m-d H:i:s'));
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
																	text : order.queryAndResolveExpressBind.i18n('pkp.order.queryAndResolveExpressBind.query'),
																	cls : 'yellow_button',
																	columnWidth : .08,
																	plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin', {
																		seconds: 3
																	}),
																	disabled:!order.queryAndResolveExpressBind.isPermission('queryAndResolveExpressBindindex/queryAndResolveExpressBindindexquerybutton'),
																	hidden:!order.queryAndResolveExpressBind.isPermission('queryAndResolveExpressBindindex/queryAndResolveExpressBindindexquerybutton'),
																	handler : function() {
																		var myForm = this
																				.up(
																						'form')
																				.getForm();
																		if (myForm
																				.isValid()) {
																			var resultGrid = Ext
																					.getCmp(
																							'T_order-queryAndResolveExpressBindIndex_content')
																					.getResultGrid();
																			resultGrid
																					.show();
																			order.queryAndResolveExpressBind.pagingBar.moveFirst();
																		}
																	}
        		}]
        	}
            ]
        });

        me.callParent(arguments);
    }
});

Ext.define('Foss.order.queryAndResolveExpressBind.driverSignGrid', {
																			extend  : 'Ext.grid.Panel',
					title : order.queryAndResolveExpressBind.i18n('pkp.order.queryAndResolveExpressBind.signinInfo'),
					frame : true,
					hidden : true,
					emptyText : order.queryAndResolveExpressBind.i18n('pkp.order.queryAndResolveExpressBind.queryResultNull'),
					cls : 'autoHeight',
					bodyCls : 'autoHeight',
					columns : [
							{
								header : order.queryAndResolveExpressBind.i18n('null'),
								align : 'center',
								dataIndex : 'id',
								flex : 1,
								hidden : true
							},
							{
								header : order.queryAndResolveExpressBind.i18n('pkp.order.queryAndResolveExpressBind.driverCode'),
								align : 'center',
								dataIndex : 'driverCode',
								flex : 1
							},
							{
								header : order.queryAndResolveExpressBind.i18n('pkp.order.queryAndResolveExpressBind.driverName'),
								align : 'center',
								dataIndex : 'driverName',
								flex : 1
							},
							{
								header : order.queryAndResolveExpressBind.i18n('pkp.order.queryAndResolveExpressBind.vehicleNo'),
								align : 'center',
								dataIndex : 'vehicleNo',
								flex : 1
							},
							{
								header : order.queryAndResolveExpressBind.i18n('pkp.order.queryAndResolveExpressBind.mobile'),
								align : 'center',
								dataIndex : 'empPhone',
								flex : 1
							},
							{
								header : order.queryAndResolveExpressBind.i18n('pkp.order.queryAndResolveExpressBind.deviceNo'),
								align : 'center',
								dataIndex : 'deviceNo',
								flex : 1
							},/**
							{
								header : order.queryAndResolveExpressBind.i18n('pkp.order.queryAndResolveExpressBind.region'),
								align : 'center',
								dataIndex : 'regionName'
							},*/
							{
								header : order.queryAndResolveExpressBind.i18n('pkp.order.queryAndResolveExpressBind.signinTime'),
								align : 'center',
								dataIndex : 'createTime',
								flex : 1,
								xtype : 'datecolumn',
								format : 'Y-m-d H:i:s'
							},
							{
								header : order.queryAndResolveExpressBind.i18n('pkp.order.queryAndResolveExpressBind.bundleStatus'),
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
						me.store = Ext.create('Foss.order.queryAndResolveExpressBind.pdaSignStore');
						me.dockedItems = [ {
							xtype : 'toolbar',
							dock : 'bottom',
							layout : 'column',
							defaults : {
								margin : '0 0 5 3'
							},
							items : [ {
								xtype : 'button',
								text : order.queryAndResolveExpressBind.i18n('pkp.order.queryAndResolveExpressBind.unbundle'),
								disabled:!order.queryAndResolveExpressBind.isPermission('queryAndResolveExpressBindindex/queryAndResolveExpressBindunbundlebutton'),
								hidden:!order.queryAndResolveExpressBind.isPermission('queryAndResolveExpressBindindex/queryAndResolveExpressBindunbundlebutton'),
								name : 'unbundle',
								plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin', {
									seconds: 3
								}),
								handler : function() {
									var mygrid = this.up('gridpanel');
									var selectRow = mygrid.getSelectionModel()
											.getSelection();
									if (selectRow.length == 0) {
										Ext.ux.Toast.msg(order.queryAndResolveExpressBind.i18n('pkp.order.queryAndResolveExpressBind.warning'),
												order.queryAndResolveExpressBind.i18n('pkp.order.queryAndResolveExpressBind.chooseSignin'));
									} else {
										var model = Ext.ModelManager
												.create(selectRow[0].data,
														'Foss.order.queryAndResolveExpressBind.pdaSignModel');
										order.queryAndResolveExpressBind.unBundlePanel
												.getForm().loadRecord(model);
										var win = Ext
												.create(
														'Foss.order.queryAndResolveExpressBind.unBundleWindow')
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
						order.queryAndResolveExpressBind.pagingBar = me.bbar;
						me.callParent(arguments);
					}
				});

Ext.onReady(function() {
	Ext.QuickTips.init();
	var queryForm = Ext.create("Foss.order.queryAndResolveExpressBind.QueryForm");
	var driverSignGrid = Ext.create(
			"Foss.order.queryAndResolveExpressBind.driverSignGrid", {
				id : "Foss_order_queryAndResolveExpressBind_driverSignGrid_Id",
				listeners : {
					itemclick : function(dv, record, item, index, e) {
						var status = record.data.status;
						var itmes = Ext.getCmp(
								'T_order-queryAndResolveExpressBindIndex_content')
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
		id : 'T_order-queryAndResolveExpressBindIndex_content',
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
		renderTo : 'T_order-queryAndResolveExpressBindIndex-body'
	});
});
