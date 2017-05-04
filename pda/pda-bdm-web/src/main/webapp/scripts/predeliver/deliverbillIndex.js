/**
 * 
 * @param {日期}
 *            date
 * @param {小时}
 *            hours
 * @param {分钟}
 *            minutes
 * @param {秒}
 *            seconds
 * @param {微秒}
 *            milliseconds
 * @return {}
 */
predeliver.deliverbill.getDate = function(date, hours, minutes, seconds,
		milliseconds) {
	date.setHours(hours);
	date.setMinutes(minutes);
	date.setSeconds(seconds);
	date.setMilliseconds(milliseconds);

	return date;
};

// predeliver.deliverbill.id = '';

// 定义预派送单明细的模型
Ext
		.define(
				'Foss.predeliver.deliverbill.DeliverbillDetailModel',
				{
					extend : 'Ext.data.Model',
					// 定义字段
					fields : [
							{
								name : 'id',
								type : 'string'
							},
							{
								name : 'arrivesheetNo',
								type : 'string'
							},
							{
								name : 'tSrvDeliverbillId',
								type : 'string'
							},
							{
								name : 'serialNo', // 派送单明细序列号，仅用于派送单明细Model
								type : 'int'
							},
							{
								name : 'waybillNo', // 运单号
								type : 'string'
							},
							{
								name : 'goodsName', // 货物名称
								type : 'string'
							},
							{
								name : 'waybillGoodsQty', // 对于派送单明细，为排单货物件数；对于待排运单，为待排运单整票货物件数。
								type : 'int'
							},
							{
								name : 'arrangableGoodsQty', // 可排单件数，仅用于待排运单Model
								type : 'int'
							},
							{
								name : 'arrangeGoodsQty', // 排单件数
								type : 'int'
							},
							{
								name : 'preArrangeGoodsQty', // 预排单件数。对于待排运单，其初始值为可排单件数
								type : 'int'
							},
							{
								name : 'weight', // 对于派送单明细，为排单重量；对于待排运单，为待排运单整票货物重量。
								type : 'number'
							},
							{
								name : 'arrangedWeight', // 待排运单的排单重量
								type : 'number',
								convert : function(v, record) {
									return Ext.util.Format
											.round(
													record.data.arrangableGoodsQty > 0 ? record.data.weight
															* record.data.arrangeGoodsQty
															/ record.data.waybillGoodsQty
															: record.data.weight,
													2);
								}
							},
							{
								name : 'dimension', // 运单体积
								type : 'string'
							},
							{
								name : 'goodsVolumeTotal', // 对于派送单明细，为排单体积；对于待排运单，为待排运单整票货物体积。
								type : 'number'
							},
							{
								name : 'arrangedGoodsVolume', // 待排运单的排单体积
								type : 'number',
								convert : function(v, record) {
									return Ext.util.Format
											.round(
													record.data.arrangableGoodsQty > 0 ? record.data.goodsVolumeTotal
															* record.data.arrangeGoodsQty
															/ record.data.waybillGoodsQty
															: record.data.goodsVolumeTotal,
													2);
								}
							}, {
								name : 'transportType', // 运输方式
								type : 'string'
							}, {
								name : 'arriveTime', // 到达时间
								type : 'date',
								convert : dateConvert
							}, {
								name : 'consignee', // 收货人
								type : 'string'
							}, {
								name : 'consigneeContact', // 联系方式
								type : 'string'
							}, {
								name : 'consigneeAddress', // 收货地址
								type : 'string'
							}, {
								name : 'deliverRequire', // 送货要求
								type : 'string'
							}, {
								name : 'goodsStatus', // 货物状态
								type : 'string'
							}, {
								name : 'isAlreadyContact', // 是否已联系客户
								type : 'string'
							}, {
								name : 'estimatedDeliverTime', // 预计送货时间
								type : 'date',
								convert : dateConvert
							}, {
								name : 'isException', // 是否异常
								type : 'string'
							}, {
								name : 'isNeedInvoice', // 是否需要发票
								type : 'string'
							}, {
								name : 'paymentType', // 付款方式
								type : 'string'
							}, {
								name : 'notes', // 备注
								type : 'string'
							}, {
								name : 'deliverType', // 送货方式
								type : 'string'
							}, {
								name : 'arrangeStatus', // 排单状态
								type : 'string'
							}, {
								name : 'payAmount', // 到付款
								type : 'number'
							}, {
								name : 'fastWaybillFlag', // 卡货标志
								type : 'int'
							}, {
								name : 'isSentRequired', // 是否必送货
								type : 'string'
							}, {
								name : 'stockGoodQty', // 提货网点库存
								type : 'int'
							} ]
				});

// 定义预派送单明细的Store
Ext.define('Foss.predeliver.deliverbill.DeliverbillDetailStore', {
	extend : 'Ext.data.Store',
	autoLoad : false,
	// 绑定一个模型
	model : 'Foss.predeliver.deliverbill.DeliverbillDetailModel',
	// 定义一个代理对象
	proxy : {
		type : 'ajax',
		actionMethods : 'POST',
		url : predeliver.realPath('queryDeliverbillArrivesheetList.action'),
		// 定义一个读取器
		reader : {
			// 以JSON的方式读取
			type : 'json',
			// 定义读取JSON数据的根对象
			root : 'deliverbillVo.deliverbillDetailList'
		}
	},
	deliverbillId : '',
	setDeliverbillId : function(deliverbillId) {
		this.deliverbillId = deliverbillId;
	},
	getDeliverbillId : function() {
		return this.deliverbillId;
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	},
	listeners : {
		beforeload : function(store, operation, eOpts) {
			Ext.apply(operation, {
				params : {
					'deliverbillVo.deliverbillDto.id' : store
							.getDeliverbillId()
				}
			});
		}
	}
});

// 定义派送单明细的表格
Ext.define('Foss.predeliver.deliverbill.DeliverbillDetailGrid', {
	extend : 'Ext.grid.Panel',
	frame : true,
	title : '到达联',
	emptyText : '查询结果为空',
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	autoScroll : true,
	collapsible : true,
	animCollapse : true,
	printWindow : null,
	getPrintWindow : function() {
		var me = this;
		if (this.printWindow == null) {
			me.printWindow = Ext.create('Foss.printArriveSheet.printOneWindow',
					me, 'Y');
		}
		return me.printWindow;
	},
	columns : [ {
		dataIndex : 'id',
		align : 'center',
		flex : 1,
		hidden : true
	}, {
		dataIndex : 'arrivesheetNo',
		align : 'center',
		flex : 1,
		hidden : true
	}, {
		dataIndex : 'stockGoodQty',
		align : 'center',
		flex : 1,
		hidden : true
	}, {
		dataIndex : 'waybillNo',
		align : 'center',
		flex : 1,
		header : '运单号'
	}, {
		dataIndex : 'goodsName',
		align : 'center',
		flex : 1,
		header : '货物名称'
	}, {
		dataIndex : 'arrangeGoodsQty',
		align : 'center',
		flex : 1,
		header : '排单件数'
	}, {
		dataIndex : 'weight',
		align : 'center',
		flex : 1,
		header : '排单重量'
	}, {
		dataIndex : 'dimension',
		align : 'center',
		flex : 1,
		header : '体积'
	}, {
		dataIndex : 'transportType',
		align : 'center',
		flex : 1,
		header : '运输方式'
	}, {
		dataIndex : 'consignee',
		align : 'center',
		flex : 1,
		header : '收货人'
	}, {
		dataIndex : 'consigneeContact',
		align : 'center',
		flex : 1,
		header : '联系方式'
	}, {
		dataIndex : 'consigneeAddress',
		align : 'center',
		flex : 1,
		header : '收货地址'
	}, {
		dataIndex : 'deliverRequire',
		align : 'center',
		flex : 1,
		header : '送货要求'
	}, {
		dataIndex : 'goodsStatus',
		align : 'center',
		flex : 1,
		header : '货物状态'
	}, {
		dataIndex : 'isAlreadyContact',
		align : 'center',
		flex : 1,
		header : '是否已联系客户'
	}, {
		dataIndex : 'estimatedDeliverTime',
		align : 'center',
		flex : 1,
		header : '预计送货时间',
		xtype : 'datecolumn',
		format : 'Y-m-d H:i:s'
	}, {
		dataIndex : 'isException',
		align : 'center',
		flex : 1,
		header : '是否异常'
	}, {
		dataIndex : 'isNeedInvoice',
		align : 'center',
		flex : 1,
		header : '是否需要发票'
	}, {
		dataIndex : 'paymentType',
		align : 'center',
		flex : 1,
		header : '付款方式'
	}, {
		dataIndex : 'notes',
		align : 'center',
		flex : 1,
		header : '备注'
	}, {
		dataIndex : 'deliverType',
		align : 'center',
		flex : 1,
		header : '送货方式'
	} ],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext
				.create('Foss.predeliver.deliverbill.DeliverbillDetailStore');
		me.callParent([ cfg ]);
	}
});

// 基础信息Form
Ext.define('Foss.predeliver.deliverbill.DeliverbillBasicInfoForm', {
	extend : 'Ext.form.Panel',
	// frame : true,
	collapsible : false,
	defaults : {
		margin : '5 15 5 15',
		labelWidth : 90
	},
	defaultType : 'textfield',
	layout : 'column',
	items : [
			{
				name : 'id',
				xtype : 'hidden',
				value : predeliver.deliverbill.deliverbillId
			},
			{
				name : 'deliverbillNo',
				fieldLabel : predeliver.deliverbill
						.i18n('foss.pickup.predeliver.deliverbillNo'), // 预派送单编号
				columnWidth : .33,
				readOnly : true
			},
			{
				name : 'vehicleNo',
				fieldLabel : predeliver.deliverbill
						.i18n('foss.pickup.predeliver.vehicleNo'), // 车辆
				columnWidth : .33,
				readOnly : true
			},
			{
				name : 'driverName',
				fieldLabel : predeliver.deliverbill
						.i18n('foss.pickup.predeliver.driverName'), // 司机姓名
				columnWidth : .33,
				readOnly : true
			} ]
});

// 打印到达联Form
Ext.define('Foss.predeliver.deliverbill.PrintArrivesheetForm', {
	extend : 'Ext.form.Panel',
	// frame : true,
	collapsible : false,
	defaults : {
		margin : '5 15 5 15',
		labelWidth : 90
	},
	layout : 'column',
	items : [
			{
				columnWidth : .08,
				xtype : 'button',
				cls : 'yellow_button',
				text : '打印',
				handler : function(button, e) {
					button.up('window').getDeliverbillDetailGrid()
							.getPrintWindow().show();
				}
			}, {
				border : false,
				columnWidth : .92,
				html : '&nbsp;'
			} ]
});

// 打印到达联窗口
Ext
		.define(
				'Foss.predeliver.deliverbill.PrintArrivesheetWindow',
				{
					extend : 'Ext.window.Window',
					modal : true,
					closeAction : 'close',
					width : 1000,
					title : '根据预派送单打印到达联',
					deliverbillBasicInfoForm : null,
					getDeliverbillBasicInfoForm : function() {
						if (this.deliverbillBasicInfoForm == null) {
							this.deliverbillBasicInfoForm = Ext
									.create('Foss.predeliver.deliverbill.DeliverbillBasicInfoForm');
						}
						return this.deliverbillBasicInfoForm;
					},
					// 派送单明细GRID
					deliverbillDetailGrid : null,
					getDeliverbillDetailGrid : function() {
						if (this.deliverbillDetailGrid == null) {
							this.deliverbillDetailGrid = Ext
									.create('Foss.predeliver.deliverbill.DeliverbillDetailGrid');
						}
						return this.deliverbillDetailGrid;
					},
					printArrivesheetForm : null,
					getPrintArrivesheetForm : function() {
						if (this.printArrivesheetForm == null) {
							this.printArrivesheetForm = Ext
									.create('Foss.predeliver.deliverbill.PrintArrivesheetForm');
						}
						return this.printArrivesheetForm;
					},
					constructor : function(config) {
						var me = this, cfg = Ext.apply({}, config);
						me.items = [ me.getDeliverbillBasicInfoForm(),
								me.getDeliverbillDetailGrid(),
								me.getPrintArrivesheetForm() ];
						me.callParent([ cfg ]);
					}
				});

// 查询条件Form
Ext
		.define(
				'Foss.predeliver.deliverbill.QueryDeliverbillForm',
				{
					extend : 'Ext.form.Panel',
					title : predeliver.deliverbill
							.i18n('foss.pickup.predeliver.query'), // 查询条件
					frame : true,
					collapsible : true,
					animCollapse : true,
					bodyCls : 'autoHeight',
					cls : 'autoHeight',
					defaults : {
						margin : '5 15 5 15',
						labelWidth : 90
					},
					defaultType : 'textfield',
					layout : 'column',
					items : [
							{
								name : 'deliverbillNo',
								fieldLabel : predeliver.deliverbill
										.i18n('foss.pickup.predeliver.deliverbillNo'), // 预派送单编号
								columnWidth : .33
							},
							{
								name : 'vehicleNo',
								fieldLabel : predeliver.deliverbill
										.i18n('foss.pickup.predeliver.vehicleNo'), // 车辆
								columnWidth : .33
							},
							{
								name : 'driverName',
								fieldLabel : predeliver.deliverbill
										.i18n('foss.pickup.predeliver.driverName'), // 司机姓名
								columnWidth : .33
							},
							{
								xtype : 'combobox',
								name : 'status',
								fieldLabel : predeliver.deliverbill
										.i18n('foss.pickup.predeliver.deliverbillStatus'), // 预排单状态
								columnWidth : .33,
								store : FossDataDictionary
										.getDataDictionaryStore('PKP_DELIVERBILL_STATUS'),
								displayField : 'valueName',
								valueField : 'valueCode',
								queryMode : 'local',
								triggerAction : 'all',
								editable : false
							},
							{
								xtype : 'rangeDateField',
								fieldId : 'predeliver_deliverbill_submitTime',
								fieldLabel : predeliver.deliverbill
										.i18n('foss.pickup.predeliver.submitTime'), // 提交时间
								dateType : 'datetimefield_date97',
								fromName : 'submitTimeBegin',
								toName : 'submitTimeEnd',
								fromValue : Ext.Date.format(
										predeliver.deliverbill.getDate(
												new Date(), 0, 0, 0, 0),
										'Y-m-d H:i:s'),
								toValue : Ext.Date.format(
										predeliver.deliverbill.getDate(
												new Date(), 23, 59, 59, 999),
										'Y-m-d H:i:s'),
								columnWidth : .67,
								disallowBlank : true,
								editable : false
							},
							{
								border : false,
								xtype : 'container',
								columnWidth : 1,
								layout : 'column',
								defaults : {
									margin : '5 0 5 0'
								},
								items : [
										{
											xtype : 'button',
											columnWidth : .08,
											text : predeliver.deliverbill
													.i18n('foss.pickup.predeliver.resetButton'), // 重置
											handler : function() {
												this.up('form').getForm()
														.reset();

												predeliver.deliverbill.queryDeliverbillForm
														.getForm().findField(
																'status')
														.select('');

												this
														.up('form')
														.getForm()
														.setValues(
																{
																	'submitTimeBegin' : Ext.Date
																			.format(
																					predeliver.deliverbill
																							.getDate(
																									new Date(),
																									0,
																									0,
																									0,
																									0),
																					'Y-m-d H:i:s'),
																	'submitTimeEnd' : Ext.Date
																			.format(
																					predeliver.deliverbill
																							.getDate(
																									new Date(),
																									23,
																									59,
																									59,
																									999),
																					'Y-m-d H:i:s')
																});
											}
										},
										{
											border : false,
											columnWidth : .84,
											html : '&nbsp;'
										},
										{
											columnWidth : .08,
											xtype : 'button',
											cls : 'yellow_button',
											text : predeliver.deliverbill
													.i18n('foss.pickup.predeliver.queryButton'), // 查询
											handler : function() {
												if (!predeliver.deliverbill.queryDeliverbillForm
														.getForm().isValid()) {
													Ext.ux.Toast.msg("提示信息",
															"提交时间不能为空",
															'error', 4000);

													return;
												}

												predeliver.deliverbill.deliverbillGrid
														.getPagingToolbar()
														.moveFirst();
											}
										} ]
							} ]
				});

// 定义预派送单的模型
Ext.define('Foss.predeliver.deliverbill.DeliverbillModel', {
	extend : 'Ext.data.Model',
	idgen : 'uuid',// EXT在前台为每个模型额外以UUID方式生成主键
	idProperty : 'extid',// 以上生成的主键用在名叫“extid”的列
	// 定义字段
	fields : [ {
		name : ' extid ',
		type : 'string'
	},// 额外的用于生成的EXT使用的列
	{
		name : 'id',
		type : 'string'
	}, {
		name : 'deliverbillNo',
		type : 'string'
	}, {
		name : 'vehicleNo',
		type : 'string'
	}, {
		name : 'driverName',
		type : 'string'
	}, {
		name : 'driverCode',
		type : 'string'
	}, {
		name : 'status',
		type : 'string'
	}, {
		name : 'createUserName',
		type : 'string'
	}, {
		name : 'submitTime'
	} ]
});

// 定义预派送单的Store
Ext
		.define(
				'Foss.predeliver.deliverbill.DeliverbillStore',
				{
					extend : 'Ext.data.Store',
					// autoLoad : true,
					// 绑定一个模型
					model : 'Foss.predeliver.deliverbill.DeliverbillModel',
					pageSize : 10,
					// 定义一个代理对象
					proxy : {
						type : 'ajax',
						actionMethods : 'POST',
						url : predeliver
								.realPath('queryDeliverbillList.action'),
						// 定义一个读取器
						reader : {
							// 以JSON的方式读取
							type : 'json',
							// 定义读取JSON数据的根对象
							root : 'deliverbillVo.deliverbillDtoList',
							totalProperty : 'totalCount'
						}
					},
					constructor : function(config) {
						var me = this, cfg = Ext.apply({}, config);
						me.callParent([ cfg ]);
					},
					listeners : {
						beforeload : function(store, operation, eOpts) {
							var queryDeliverbillForm = predeliver.deliverbill.queryDeliverbillForm;
							if (queryDeliverbillForm != null) {
								var queryParams = queryDeliverbillForm
										.getValues();
								Ext
										.apply(
												operation,
												{
													params : {
														'deliverbillVo.deliverbillDto.deliverbillNo' : queryParams.deliverbillNo,
														'deliverbillVo.deliverbillDto.vehicleNo' : queryParams.vehicleNo,
														'deliverbillVo.deliverbillDto.driverName' : queryParams.driverName,
														'deliverbillVo.deliverbillDto.status' : queryParams.status,
														'deliverbillVo.deliverbillDto.submitTimeBegin' : queryParams.submitTimeBegin,
														'deliverbillVo.deliverbillDto.submitTimeEnd' : queryParams.submitTimeEnd
													}
												});
							}
						}
					}
				});

// 定义一个派送单的表格
Ext
		.define(
				'Foss.predeliver.deliverbill.DeliverbillGrid',
				{
					extend : 'Ext.grid.Panel',
					frame : true,
					title : predeliver.deliverbill
							.i18n('foss.pickup.predeliver.deliverbillHistory'), // 派送单历史
					emptyText : '查询结果为空',
					bodyCls : 'autoHeight',
					cls : 'autoHeight',
					autoScroll : true,
					collapsible : true,
					animCollapse : true,
					viewConfig : {
						enableTextSelection : true
					},
					columns : [
							{
								dataIndex : 'id',
								align : 'center',
								hidden : true
							},
							{
								xtype : 'actioncolumn',
								flex : 0.5,
								text : predeliver.deliverbill
										.i18n('foss.pickup.predeliver.deliverbillOperation'), // 派送单操作
								align : 'center',
								items : [
										{
											iconCls : 'deppon_icons_edit',
											tooltip : '编辑',
											handler : function(grid, rowIndex,
													colIndex) {
												var status = grid.getStore()
														.getAt(rowIndex).get(
																'status');

												if (status == 'CANCELED') {
													Ext.ux.Toast.msg('温馨提示',
															'派送单已取消，不能编辑',
															'error', 4000);
													return;
												}

												if (status == 'CONFIRMED'
														|| status == 'STATUS_PDA_DOWNLOADED') {
													Ext.ux.Toast.msg('温馨提示',
															'派送单已确认，不能编辑',
															'error', 4000);
													return;
												}

												if (status == 'STATUS_SIGNINFO_CONFIRMED') {
													Ext.ux.Toast.msg('温馨提示',
															'派送单已签收确认，不能编辑',
															'error', 4000);
													return;
												}

												addTab(
														'T_predeliver-editDeliverbillIndex',
														'编辑预派送单',
														predeliver
																.realPath('editDeliverbillIndex.action?deliverbillVo.deliverbill.id=')
																+ grid
																		.getStore()
																		.getAt(
																				rowIndex)
																		.get(
																				'id'));
											}
										},
										{
											iconCls : 'deppon_icons_dispose',
											tooltip : '确认/取消预派送单',
											handler : function(grid, rowIndex,
													colIndex) {
												var status = grid.getStore()
														.getAt(rowIndex).get(
																'status');

												if (status != 'LOADED') {
													Ext.ux.Toast.msg('温馨提示',
															'派送单未装车，不能操作',
															'error', 4000);
													return;
												}

												addTab(
														'T_predeliver-confirmDeliverbillIndex',
														'确认预派送单',
														predeliver
																.realPath('confirmDeliverbillIndex.action?deliverbillVo.deliverbill.id=')
																+ grid
																		.getStore()
																		.getAt(
																				rowIndex)
																		.get(
																				'id'));
											}
										} ]
							},
							{
								xtype : 'actioncolumn',
								align : 'center',
								flex : 0.5,
								header : predeliver.deliverbill
										.i18n('foss.pickup.predeliver.printDeliverbill'),
								// 打印预派送单
								items : [ {
									iconCls : 'deppon_icons_print',
									tooltip : '打印预派送单',
									handler : function(grid, rowIndex, colIndex) {
										// 打印预派送单
										var selection = grid.getStore().getAt(
												rowIndex), deliverbillId = selection
												.get('id');
										var printWin = Ext
												.create(
														'Foss.predeliver.deliverbill.PrintDeliverbillWindow',
														{
															'deliverbillId' : deliverbillId
														});
										// predeliver.deliverbill.id =
										// deliverbillId;
										// 获取打印的预排送单基本信息
										Ext.Ajax
												.request({
													url : predeliver
															.realPath('queryDeliverbill.action'),
													params : {
														'deliverbillVo.deliverbillDto.id' : deliverbillId
													},
													success : function(response) {
														var result = Ext
																.decode(response.responseText), model = Ext.ModelManager
																.create(
																		result.deliverbillVo.deliverbill,
																		'Foss.predeliver.deliverbill.PrintModel');
														printWin
																.getDeliverPrintHeadInfo()
																.loadRecord(
																		model);
														printWin
																.getDeliverPrintBottomInfo()
																.loadRecord(
																		model);
														printWin
																.getDeliverPrintGrid().store
																.setDeliverbillId(deliverbillId);
														printWin
																.getDeliverPrintGrid()
																.getPagingToolbar()
																.moveFirst();
														printWin.show();
													}
												});
									}
								} ]
							},
							{
								xtype : 'actioncolumn',
								align : 'center',
								flex : 0.5,
								header : predeliver.deliverbill
										.i18n('foss.pickup.predeliver.printArriveSheet'),
								// 打印到达联
								items : [ {
									iconCls : 'deppon_icons_print',
									tooltip : '打印到达联',
									handler : function(grid, rowIndex, colIndex) {
										var printArrivesheetWindow = Ext
												.getCmp(
														'T_predeliver-deliverbillIndex_content')
												.getPrintArrivesheetWindow();

										var deliverbillId = grid.getStore()
												.getAt(rowIndex).get('id');

										printArrivesheetWindow
												.getDeliverbillDetailGrid().store
												.setDeliverbillId(deliverbillId);

										printArrivesheetWindow
												.getDeliverbillDetailGrid().store
												.load();

										Ext.Ajax
												.request({
													url : predeliver
															.realPath('queryDeliverbill.action'),
													params : {
														'deliverbillVo.deliverbillDto.id' : deliverbillId
													},
													success : function(response) {
														var result = Ext
																.decode(response.responseText);
														var deliverbill = Ext.ModelManager
																.create(
																		result.deliverbillVo.deliverbill,
																		'Foss.predeliver.deliverbill.DeliverbillModel');

														printArrivesheetWindow
																.getDeliverbillBasicInfoForm()
																.loadRecord(
																		deliverbill);
													}
												});

										printArrivesheetWindow.show();
									}
								} ]
							},
							{
								xtype : 'actioncolumn',
								align : 'center',
								flex : 0.5,
								header : predeliver.deliverbill
										.i18n('foss.pickup.predeliver.assignTask'),
								// 分配任务
								items : [ {
									iconCls : 'foss_icons_pkp_allottask',
									tooltip : '分配任务',
									handler : function(grid, rowIndex, colIndex) {
										// 分配任务给司机
										var status = grid.getStore().getAt(
												rowIndex).get('status');

										if (status == 'CANCELED') {
											Ext.ux.Toast.msg('温馨提示',
													'派送单已取消，不能操作', 'error',
													4000);
											return;
										}

										if (status == 'CONFIRMED') {
											Ext.ux.Toast.msg('温馨提示',
													'派送单已确认，不能操作', 'error',
													4000);
											return;
										}

										var selectDriverWindow = Ext
												.getCmp(
														'T_predeliver-deliverbillIndex_content')
												.getSelectDriverWindow();

										selectDriverWindow
												.setDeliverbillId(grid
														.getStore().getAt(
																rowIndex).get(
																'id'));
										
										var vehicleNo = grid.getStore().getAt(rowIndex).get('vehicleNo');
										if(vehicleNo != null)
										{
											Ext.Ajax.request({
												url:predeliver.realPath('queryVehiclePdaSigned.action'),
												params:{'deliverbillVo.deliverbillDto.vehicleNo':vehicleNo},
												success:function(response){
													var result = Ext.decode(response.responseText);	
													var driver = result.deliverbillVo.driverDto;
													if (driver != null)
													{
														if (driver.pdaSigned == 'Y') {
															// 若PDA绑定中存在该车牌号，则系统自动带出对应的司机且不可修改
															Ext.ux.Toast.msg('提示', '当前车牌号已经存在绑定关系，不能进行再次分配!', 'ok', 3000);
														}else {
															selectDriverWindow.show();
														}
													}else
													{
														selectDriverWindow.show();
													}	
												},
									    		exception: function(response){
													var json = Ext.decode(response.responseText);
							              			Ext.ux.Toast.msg('分配失败', json.message, 'error', 3000);
												}
											});
										}
									}
								} ]
							},
							{
								dataIndex : 'deliverbillNo',
								align : 'center',
								flex : 1,
								header : predeliver.deliverbill
										.i18n('foss.pickup.predeliver.deliverbillNo')
							// 派送单号
							},
							{
								dataIndex : 'vehicleNo',
								align : 'center',
								flex : 1,
								header : predeliver.deliverbill
										.i18n('foss.pickup.predeliver.vehicleNo')
							// 车辆
							},
							{
								dataIndex : 'driverName',
								align : 'center',
								flex : 1,
								header : predeliver.deliverbill
										.i18n('foss.pickup.predeliver.driverName')
							// 司机姓名
							},
							{
								dataIndex : 'driverCode',
								align : 'center',
								flex : 1,
								hidden : true,
								header : '司机Code'
							// 司机Code
							},
							{
								dataIndex : 'status',
								align : 'center',
								flex : 1,
								renderer : function(value) {
									var displayedValue = FossDataDictionary
											.rendererSubmitToDisplay(value,
													'PKP_DELIVERBILL_STATUS');
									return displayedValue;
								},
								header : predeliver.deliverbill
										.i18n('foss.pickup.predeliver.deliverbillStatus')
							// 状态
							},
							{
								dataIndex : 'createUserName',
								align : 'center',
								flex : 1,
								header : predeliver.deliverbill
										.i18n('foss.pickup.predeliver.createUserName')
							// 提交人
							},
							{
								dataIndex : 'submitTime',
								align : 'center',
								flex : 1,
								header : predeliver.deliverbill
										.i18n('foss.pickup.predeliver.submitTime'),
								renderer : function(value) {
									if (value != null) {
										var date = Ext.Date.format(new Date(
												value), 'Y-m-d H:i:s');
										return date;
									} else {
										return null;
									}
								}
							// 提交时间
							} ],
					pagingToolbar : null,
					getPagingToolbar : function() {
						if (this.pagingToolbar == null) {
							this.pagingToolbar = Ext.create(
									'Deppon.StandardPaging', {
										store : this.store
									});
						}
						return this.pagingToolbar;
					},
					constructor : function(config) {
						var me = this, cfg = Ext.apply({}, config);
						me.store = Ext
								.create('Foss.predeliver.deliverbill.DeliverbillStore');
						// me.selModel =
						// Ext.create('Ext.selection.CheckboxModel');

						me.bbar = me.getPagingToolbar();
						me.callParent([ cfg ]);
					}
				});

// 查询司机form
Ext
		.define(
				'Foss.predeliver.deliverbill.QueryDriverForm',
				{
					title : '查询条件',
					extend : 'Ext.form.Panel',
					frame : true,
					collapsible : true,
					animCollapse : true,
					bodyCls : 'autoHeight',
					cls : 'autoHeight',
					defaults : {
						margin : '5 25 5 25',
						labelWidth : 80
					},
					defaultType : 'textfield',
					layout : 'column',
					items : [
							{
								name : 'empCode',
								fieldLabel : '工号 ',
								columnWidth : .33
							},
							{
								name : 'empName',
								fieldLabel : '姓名',
								columnWidth : .33
							},
							{
								name : 'empPhone',
								fieldLabel : '电话号码',
								columnWidth : .33
							},
							{
								border : false,
								xtype : 'container',
								columnWidth : 1,
								layout : 'column',
								defaults : {
									margin : '5 0 5 0'
								},
								items : [
										{
											xtype : 'button',
											columnWidth : .08,
											text : predeliver.deliverbill
													.i18n('foss.pickup.predeliver.resetButton'), // 重置
											handler : function() {
												this.up('form').getForm()
														.reset();
											}
										},
										{
											border : false,
											columnWidth : .84,
											html : '&nbsp;'
										},
										{
											columnWidth : .08,
											xtype : 'button',
											cls : 'yellow_button',
											text : predeliver.deliverbill
													.i18n('foss.pickup.predeliver.queryButton'), // 查询
											handler : function() {
												var pagingToolbar = this.up(
														'window')
														.getDriverGrid().store
														.load();
											}
										} ]
							} ]
				});

// 公司司机的Model
Ext.define('Foss.predeliver.deliverbill.DriverModel', {
	extend : 'Ext.data.Model',
	// 定义字段
	fields : [ {
		name : 'empCode', // 司机工号
		type : 'string'
	}, {
		name : 'empName', // 司机姓名
		type : 'string'
	}, {
		name : 'empPhone', // 司机电话
		type : 'int'
	}, {
		name : 'orgId', // 部门编号
		type : 'string'
	} ]
});

// 定义公司司机的Store
Ext
		.define(
				'Foss.predeliver.deliverbill.DriverStore',
				{
					extend : 'Ext.data.Store',
					autoLoad : false,
					// 绑定一个模型
					model : 'Foss.predeliver.deliverbill.DriverModel',
					// 定义一个代理对象
					proxy : {
						type : 'ajax',
						actionMethods : 'POST',
						url : predeliver.realPath('queryDriver.action'),
						// 定义一个读取器
						reader : {
							// 以JSON的方式读取
							type : 'json',
							// 定义读取JSON数据的根对象
							root : 'deliverbillVo.driverList'
						}
					},
					constructor : function(config) {
						var me = this, cfg = Ext.apply({}, config);
						me.callParent([ cfg ]);
					},
					listeners : {
						beforeload : function(store, operation, eOpts) {
							var queryDriverForm = Ext.getCmp(
									'T_predeliver-deliverbillIndex_content')
									.getSelectDriverWindow()
									.getQueryDriverForm();
							if (queryDriverForm != null) {
								var queryParams = queryDriverForm.getValues();
								Ext
										.apply(
												operation,
												{
													params : {
														'deliverbillVo.driverDto.empCode' : queryParams.empCode,
														'deliverbillVo.driverDto.empName' : queryParams.empName,
														'deliverbillVo.driverDto.empPhone' : queryParams.empPhone
													}
												});
							}
						}
					}
				});

// 公司司机GRID
Ext.define('Foss.predeliver.deliverbill.DriverGrid', {
	extend : 'Ext.grid.Panel',
	frame : true,
	title : '公司司机',
	emptyText : '查询结果为空',
	autoScroll : true,
	height : 450,
	collapsible : true,
	animCollapse : true,
	columns : [ {
		dataIndex : 'empCode',
		align : 'center',
		flex : 1,
		header : '工号'
	}, {
		dataIndex : 'empName',
		align : 'center',
		flex : 1,
		header : '姓名'
	}, {
		dataIndex : 'orgId',
		align : 'center',
		flex : 1,
		header : '部门'
	}, {
		dataIndex : 'empPhone',
		align : 'center',
		flex : 1,
		header : '电话号码'
	} ],
	viewConfig : {
		enableTextSelection : true
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.predeliver.deliverbill.DriverStore');
		me.selModel = Ext.create('Ext.selection.RadioModel', {
			showHeaderCheckbox : false
		});
		me.callParent([ cfg ]);
	}
});

// 确认司机form
Ext
		.define(
				'Foss.predeliver.deliverbill.ConfirmDriverForm',
				{
					extend : 'Ext.form.Panel',
					frame : false,
					height : 80,
					defaults : {
						margin : '0 45 0 25'
					},
					layout : 'column',
					items : [ {
						border : false,
						xtype : 'container',
						columnWidth : 1,
						layout : 'column',
						defaults : {
							margin : '0 0 0 0'
						},
						items : [
								{
									border : false,
									columnWidth : .92,
									html : '&nbsp;'
								},
								{
									columnWidth : .08,
									xtype : 'button',
									cls : 'yellow_button',
									text : '确认',
									handler : function() {
										var selectRow = predeliver.deliverbill.selectDriverWindow
												.getDriverGrid()
												.getSelectionModel()
												.getSelection();

										if (selectRow.length == 0) {
											Ext.ux.Toast.msg("提示信息", "请选择派送司机",
													'error', 4000);
											return;
										}
										var driverName = selectRow[0].get('empName');
										Ext.Msg.confirm('提示', '是否确认分配给司机' +driverName+'?' , function(btn){
										    if (btn == 'yes'){
												Ext.Ajax.request({
													url : predeliver.realPath('assignDriver.action'),
													params : {
														'deliverbillVo.deliverbill.id' : predeliver.deliverbill.selectDriverWindow
																.getDeliverbillId(),
														'deliverbillVo.driverDto.empCode' : selectRow[0]
																.get('empCode'),
														'deliverbillVo.driverDto.empName' : selectRow[0]
																.get('empName')
													},
													success : function(response) {
														predeliver.deliverbill.selectDriverWindow
																.close();

														predeliver.deliverbill.deliverbillGrid.store
																.load();

														Ext.ux.Toast.msg(
																"提示信息",
																result.message);
													},
													error : function(response) {
														var result = Ext
																.decode(response.responseText);
														Ext.ux.Toast.msg(
																"提示信息",
																result.message,
																'error', 4000);
													},
													exception : function(
															response) {
														var result = Ext
																.decode(response.responseText);
														Ext.ux.Toast.msg(
																"提示信息",
																result.message,
																'error', 4000);
													}
												})
										    }
										});
									}
								} ]
					} ]
				});

// 分配派送任务(司机)窗口
Ext.define('Foss.predeliver.deliverbill.SelectDriverWindow', {
	extend : 'Ext.window.Window',
	// title : '公司司机',
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	modal : true,
	closeAction : 'hide',
	width : 1000,
	// 派送单ID
	deliverbillId : '',
	getDeliverbillId : function() {
		return this.deliverbillId;
	},
	setDeliverbillId : function(deliverbillId) {
		this.deliverbillId = deliverbillId;
	},
	// 查询条件FORM
	queryDriverForm : null,
	getQueryDriverForm : function() {
		if (this.queryDriverForm == null) {
			this.queryDriverForm = Ext
					.create('Foss.predeliver.deliverbill.QueryDriverForm');
		}
		return this.queryDriverForm;
	},
	// 公司司机信息GRID
	driverGrid : null,
	getDriverGrid : function() {
		if (this.driverGrid == null) {
			this.driverGrid = Ext
					.create('Foss.predeliver.deliverbill.DriverGrid');
		}
		return this.driverGrid;
	},
	// 确认司机FORM
	confirmDriverForm : null,
	getConfirmDriverForm : function() {
		if (this.confirmDriverForm == null) {
			this.confirmDriverForm = Ext
					.create('Foss.predeliver.deliverbill.ConfirmDriverForm');
		}
		return this.confirmDriverForm;
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.items = [ me.getQueryDriverForm(), me.getDriverGrid(),
				me.getConfirmDriverForm() ];
		me.callParent([ cfg ]);
	}
});

Ext
		.onReady(function() {
			Ext.QuickTips.init();

			predeliver.deliverbill.queryDeliverbillForm = Ext
					.create('Foss.predeliver.deliverbill.QueryDeliverbillForm');

			predeliver.deliverbill.deliverbillGrid = Ext
					.create('Foss.predeliver.deliverbill.DeliverbillGrid');

			// 派送单状态下拉框增加“全部”
			var deliverbillStatusCombo = predeliver.deliverbill.queryDeliverbillForm
					.getForm().findField('status');

			deliverbillStatusCombo.store.add({
				'valueCode' : '',
				'valueName' : '全部'
			});

			deliverbillStatusCombo.select('');

			// 打印到达联窗口
			predeliver.deliverbill.printArrivesheetWindow = null;
			// 分配派送司机窗口
			predeliver.deliverbill.selectDriverWindow = null;

			Ext
					.create(
							'Ext.panel.Panel',
							{
								id : 'T_predeliver-deliverbillIndex_content',
								cls : "panelContentNToolbar",
								bodyCls : 'panelContentNToolbar-body',
								layout : 'auto',
								getPrintArrivesheetWindow : function() {
									if (predeliver.deliverbill.printArrivesheetWindow == null) {
										predeliver.deliverbill.printArrivesheetWindow = Ext
												.create('Foss.predeliver.deliverbill.PrintArrivesheetWindow');
									}

									return predeliver.deliverbill.printArrivesheetWindow;
								},
								getSelectDriverWindow : function() {
									if (predeliver.deliverbill.selectDriverWindow == null) {
										predeliver.deliverbill.selectDriverWindow = Ext
												.create('Foss.predeliver.deliverbill.SelectDriverWindow');
									}

									return predeliver.deliverbill.selectDriverWindow;
								},
								items : [
										predeliver.deliverbill.queryDeliverbillForm,
										predeliver.deliverbill.deliverbillGrid ],
								renderTo : 'T_predeliver-deliverbillIndex-body'
							});
		});