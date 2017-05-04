//时间默认值
order.queryAndResolveSalesPartmentBind.getTargetDate = function(date, day) {
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
Ext.define('Foss.order.queryAndResolveSalesPartmentBind.pdaSignModel', {
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
	//liding add
	{
		name : 'memberPosition',
		type : 'string'
	}, // 登录人岗位
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
Ext.define('Foss.order.queryAndResolveSalesPartmentBind.pdaSignStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.order.queryAndResolveSalesPartmentBind.pdaSignModel',
	// 分页
	pageSize : 10,
	proxy : {
		// 代理的类型为ajax代理
		type : 'ajax',
		// 提交方式
		actionMethods : 'POST',
		url : order.realPath('querySalesPartmentSignedInfo.action'),
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
					'T_order-queryAndResolveSalesPartmentBindIndex_content').getQueryForm()
					.getValues();
			var dto = '';
			Ext.apply(operation, {
				params : {
					'vo.dto.driverCode' : serachParms.driverCode,
					'vo.dto.driverName' : serachParms.driverName,
					'vo.dto.deviceNo' : serachParms.deviceNo,
					//liding comment
					//'vo.dto.vehicleNo' : serachParms.vehicleNo,
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
				'Foss.order.queryAndResolveSalesPartmentBind.unBundlePanel',
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
								fieldLabel : order.queryAndResolveSalesPartmentBind.i18n('pkp.order.queryAndResolveSalesPartmentBind.unbundleReason'),
								xtype : 'textareafield',
								columnWidth : 1,
								name : 'unbundleReason',
								emptyText : order.queryAndResolveSalesPartmentBind.i18n('pkp.order.queryAndResolveSalesPartmentBind.inputReason'),
								allowBlank : false,
								maxLength : 500
							},
							{
								xtype : 'button',
								text : order.queryAndResolveSalesPartmentBind.i18n('pkp.order.queryAndResolveSalesPartmentBind.unbundle'),
								columnWidth : .25,
								plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin', {
									seconds: 3
								}),
								handler : function() {
									var form = this.up('form').getForm();
									var win = this.up('window')
									if (!form.isValid()) {
										Ext.ux.Toast.msg(order.queryAndResolveSalesPartmentBind.i18n('pkp.order.queryAndResolveSalesPartmentBind.warning'),
												order.queryAndResolveSalesPartmentBind.i18n('pkp.order.queryAndResolveSalesPartmentBind.unbundleReminder'));
										return;
									}
									var unBundleValues = form.getValues();
									Ext.Ajax
											.request({
												url : order
														.realPath('handResolveSalesPartmentBind.action'),
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
													order.queryAndResolveSalesPartmentBind.pagingBar.moveFirst();
													Ext.ux.Toast.msg(order.queryAndResolveSalesPartmentBind.i18n('pkp.order.queryAndResolveSalesPartmentBind.warning'),
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
								text : order.queryAndResolveSalesPartmentBind.i18n('pkp.order.queryAndResolveSalesPartmentBind.cancel'),
								columnWidth : .25,
								handler : function() {
									this.up('window').hide();
								}
							} ]
				});

order.queryAndResolveSalesPartmentBind.unBundlePanel = Ext
		.create('Foss.order.queryAndResolveSalesPartmentBind.unBundlePanel');
// 编辑任务订单状态window
Ext.define('Foss.order.queryAndResolveSalesPartmentBind.unBundleWindow', {
	extend : 'Ext.window.Window',
	width : 300,
	height : 180,
	title : order.queryAndResolveSalesPartmentBind.i18n('pkp.order.queryAndResolveSalesPartmentBind.unbundleReason'),
	layout : 'column',
	border : false,
	// 将window的关闭事件close 设成 hide
	closeAction : 'hide',
	items : [ order.queryAndResolveSalesPartmentBind.unBundlePanel ]
});

// 查询条件
Ext.define('Foss.order.queryAndResolveSalesPartmentBind.QueryForm',
				{
					extend : 'Ext.form.Panel',
					title : order.queryAndResolveSalesPartmentBind.i18n('pkp.order.queryAndResolveSalesPartmentBind.queryCondition'),
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
														fieldLabel : order.queryAndResolveSalesPartmentBind.i18n('pkp.order.queryAndResolveSalesPartmentBind.driverCode'),
														columnWidth : .25,
														name : 'driverCode'
													},
													{
														fieldLabel : order.queryAndResolveSalesPartmentBind.i18n('pkp.order.queryAndResolveSalesPartmentBind.driverName'),
														columnWidth : .25,
														name : 'driverName'
													},
													{
														fieldLabel : order.queryAndResolveSalesPartmentBind.i18n('pkp.order.queryAndResolveSalesPartmentBind.driverMobile'),
														columnWidth : .25,
														name : 'empPhone'
													},
													{
														fieldLabel : order.queryAndResolveSalesPartmentBind.i18n('pkp.order.queryAndResolveSalesPartmentBind.deviceNo'),
														columnWidth : .25,
														name : 'deviceNo'
													},
													//liding comment
//													{
//														fieldLabel : order.queryAndResolveSalesPartmentBind.i18n('pkp.order.queryAndResolveSalesPartmentBind.vehicleNo'),
//														columnWidth : .25,
//														name : 'vehicleNo'
//													},
													{
														xtype : 'combobox',
														name : 'status',
														columnWidth : .25,
														fieldLabel : order.queryAndResolveSalesPartmentBind.i18n('pkp.order.queryAndResolveSalesPartmentBind.bundleStatus'),
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
														fieldLabel: order.queryAndResolveSalesPartmentBind.i18n('pkp.order.queryAndResolveSalesPartmentBind.signinTime'),
														fieldId: 'Foss_order_salesPartment_QueryForm_signTime_ID',
														dateType: 'datetimefield_date97',
														fromName: 'signBeginTime',
														toName: 'signEndTime',
														disallowBlank:true,
														editable:false,
														fromValue: Ext.Date.format(order.queryAndResolveSalesPartmentBind.getTargetDate(new Date(),-1),'Y-m-d H:i:s'),
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
																	text : order.queryAndResolveSalesPartmentBind.i18n('pkp.order.queryAndResolveSalesPartmentBind.reset'),
																	columnWidth : .08,
																	handler : function() {
																		var myform = this
																				.up('form');
																		myform.getForm().reset();
																		myform.getForm().findField('signBeginTime').setValue(Ext.Date.format(order.queryAndResolveSalesPartmentBind.getTargetDate(new Date(),-1),'Y-m-d H:i:s'));
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
																	text : order.queryAndResolveSalesPartmentBind.i18n('pkp.order.queryAndResolveSalesPartmentBind.query'),
																	cls : 'yellow_button',
																	columnWidth : .08,
																	plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin', {
																		seconds: 3
																	}),
																	disabled:!order.queryAndResolveSalesPartmentBind.isPermission('queryAndResolveSalesPartmentBindindex/queryAndResolveSalesPartmentBindindexquerybutton'),
																	hidden:!order.queryAndResolveSalesPartmentBind.isPermission('queryAndResolveSalesPartmentBindindex/queryAndResolveSalesPartmentBindindexquerybutton'),
																	handler : function() {
																		//liding add for NCI
																		//获得查询条件的值
																		var myForm = this.up('form').getForm();	
																		var billTimeFrom = myForm.getValues().signBeginTime, billTimeTo = myForm.getValues().signEndTime;
																		//查询日期不能超过7天
																		var result = Ext.Date.parse(billTimeTo,'Y-m-d H:i:s') - Ext.Date.parse(billTimeFrom,'Y-m-d H:i:s');	
																		if(result / (24 * 60 * 60 * 1000) >= 8){	
																			Ext.ux.Toast.msg(order.queryAndResolveSalesPartmentBind.i18n('pkp.order.queryAndResolveSalesPartmentBind.tip'), order.queryAndResolveSalesPartmentBind.i18n('pkp.order.queryAndResolveSalesPartmentBind.time.must.within.three.days'), 'error', 3000); // '起止日期相隔不能超过7天！'
																			return;	
																		}	
																		if (myForm
																				.isValid()) {
																			var resultGrid = Ext
																					.getCmp(
																							'T_order-queryAndResolveSalesPartmentBindIndex_content')
																					.getResultGrid();
																			resultGrid
																					.show();
																			order.queryAndResolveSalesPartmentBind.pagingBar.moveFirst();
																		}
																	}
        		}]
        	}
            ]
        });

        me.callParent(arguments);
    }
});

Ext.define('Foss.order.queryAndResolveSalesPartmentBind.driverSignGrid', {
																			extend  : 'Ext.grid.Panel',
					title : order.queryAndResolveSalesPartmentBind.i18n('pkp.order.queryAndResolveSalesPartmentBind.signinInfo'),
					frame : true,
					hidden : true,
					emptyText : order.queryAndResolveSalesPartmentBind.i18n('pkp.order.queryAndResolveSalesPartmentBind.queryResultNull'),
					cls : 'autoHeight',
					bodyCls : 'autoHeight',
					columns : [
							{
								header : order.queryAndResolveSalesPartmentBind.i18n('null'),
								align : 'center',
								dataIndex : 'id',
								flex : 1,
								hidden : true
							},
							{
								header : order.queryAndResolveSalesPartmentBind.i18n('pkp.order.queryAndResolveSalesPartmentBind.driverCode'),
								align : 'center',
								dataIndex : 'driverCode',
								flex : 1
							},
							{
								header : order.queryAndResolveSalesPartmentBind.i18n('pkp.order.queryAndResolveSalesPartmentBind.driverName'),
								align : 'center',
								dataIndex : 'driverName',
								flex : 1
							},
							//liding mod
							{
								header : order.queryAndResolveSalesPartmentBind.i18n('pkp.order.queryAndResolveSalesPartmentBind.memberPosition'),
								align : 'center',
								dataIndex : 'memberPosition',
								flex : 1
							},
							{
								header : order.queryAndResolveSalesPartmentBind.i18n('pkp.order.queryAndResolveSalesPartmentBind.mobile'),
								align : 'center',
								dataIndex : 'empPhone',
								flex : 1
							},
							{
								header : order.queryAndResolveSalesPartmentBind.i18n('pkp.order.queryAndResolveSalesPartmentBind.deviceNo'),
								align : 'center',
								dataIndex : 'deviceNo',
								flex : 1
							},/**
							{
								header : order.queryAndResolveSalesPartmentBind.i18n('pkp.order.queryAndResolveSalesPartmentBind.region'),
								align : 'center',
								dataIndex : 'regionName'
							},*/
							{
								header : order.queryAndResolveSalesPartmentBind.i18n('pkp.order.queryAndResolveSalesPartmentBind.signinTime'),
								align : 'center',
								dataIndex : 'createTime',
								flex : 1,
								xtype : 'datecolumn',
								format : 'Y-m-d H:i:s'
							},
							{
								header : order.queryAndResolveSalesPartmentBind.i18n('pkp.order.queryAndResolveSalesPartmentBind.bundleStatus'),
								align : 'center',
								dataIndex : 'status',
								flex : 1,
								renderer : function(value) {
									return FossDataDictionary
											.rendererSubmitToDisplay(value,
													'PKP_PDA_SIGN_STATUS');
								}
							}],
					constructor : function(config) {
						var me = this, cfg = Ext.apply({}, config);
						me.store = Ext.create('Foss.order.queryAndResolveSalesPartmentBind.pdaSignStore');
						me.dockedItems = [ {
							xtype : 'toolbar',
							dock : 'bottom',
							layout : 'column',
							defaults : {
								margin : '0 0 5 3'
							},
							items : [ {
								xtype : 'button',
								text : order.queryAndResolveSalesPartmentBind.i18n('pkp.order.queryAndResolveSalesPartmentBind.unbundle'),
								disabled:!order.queryAndResolveSalesPartmentBind.isPermission('queryAndResolveSalesPartmentBindindex/queryAndResolveSalesPartmentBindunbundlebutton'),
								hidden:!order.queryAndResolveSalesPartmentBind.isPermission('queryAndResolveSalesPartmentBindindex/queryAndResolveSalesPartmentBindunbundlebutton'),
								name : 'unbundle',
								plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin', {
									seconds: 3
								}),
								handler : function() {
									var mygrid = this.up('gridpanel');
									var selectRow = mygrid.getSelectionModel()
											.getSelection();
									if (selectRow.length == 0) {
										Ext.ux.Toast.msg(order.queryAndResolveSalesPartmentBind.i18n('pkp.order.queryAndResolveSalesPartmentBind.warning'),
												order.queryAndResolveSalesPartmentBind.i18n('pkp.order.queryAndResolveSalesPartmentBind.chooseSignin'));
									} else {
										var model = Ext.ModelManager
												.create(selectRow[0].data,
														'Foss.order.queryAndResolveSalesPartmentBind.pdaSignModel');
										order.queryAndResolveSalesPartmentBind.unBundlePanel
												.getForm().loadRecord(model);
										var win = Ext
												.create(
														'Foss.order.queryAndResolveSalesPartmentBind.unBundleWindow')
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
						order.queryAndResolveSalesPartmentBind.pagingBar = me.bbar;
						me.callParent(arguments);
					}
				});

Ext.onReady(function() {
	Ext.QuickTips.init();
	var queryForm = Ext.create("Foss.order.queryAndResolveSalesPartmentBind.QueryForm");
	var driverSignGrid = Ext.create(
			"Foss.order.queryAndResolveSalesPartmentBind.driverSignGrid", {
				id : "Foss_order_queryAndResolveSalesPartmentBind_driverSignGrid_Id",
				listeners : {
					itemclick : function(dv, record, item, index, e) {
						var status = record.data.status;
						var itmes = Ext.getCmp(
								'T_order-queryAndResolveSalesPartmentBindIndex_content')
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
		id : 'T_order-queryAndResolveSalesPartmentBindIndex_content',
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
		renderTo : 'T_order-queryAndResolveSalesPartmentBindIndex-body'
	});
});
