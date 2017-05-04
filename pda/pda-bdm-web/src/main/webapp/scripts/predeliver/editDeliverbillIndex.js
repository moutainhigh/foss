/**
 * @author ibm-wangxiexu page 新增/编辑派送单
 */
// 定义数据字典model
Ext.define('Foss.predeliver.editDeliverbill.DataDictionaryModel', {
	extend : 'Ext.data.Model',
	fields : [ {
		name : 'code',
		type : 'string'
	}, {
		name : 'name',
		type : 'string'
	} ]
});

//获得当前部门是否营业部
var dept = FossUserContext.getCurrentUserDept().salesDepartment;

(function() {
	if(dept != 'Y'){
		// 获得当前组织对应的车队
		Ext.Ajax.request({
			url : predeliver.realPath('querySuperOrg.action'),
			async : false,
			success : function(response) {
				var json = Ext.decode(response.responseText);
				Ext.apply(predeliver, {
					fleetCode : json.fleetCode
				});
			},
			exception : function(response) {
				var result = Ext.decode(response.responseText);
				Ext.ux.Toast.msg('提示', result.message, 'error', 3000);
			}
		});
	}else
	{
		Ext.apply(predeliver, {
			fleetCode : FossUserContext.getCurrentUserDept().code
		});
	};
})();

// 基础信息Form
Ext
		.define(
				'Foss.predeliver.editDeliverbill.DeliverbillBasicInfoForm',
				{
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
								value : predeliver.editDeliverbill.deliverbillId
							},
							{
								name : 'deliverbillNo',
								fieldLabel : predeliver.editDeliverbill
										.i18n('foss.pickup.predeliver.deliverbillNo'), // 预派送单编号
								columnWidth : .33,
								readOnly : true,
								value : predeliver.editDeliverbill.deliverbillNo
							},
							{
								name : 'vehicleNo',
								fieldLabel : predeliver.editDeliverbill
										.i18n('foss.pickup.predeliver.vehicleNo'), // 车辆
								columnWidth : .33,
								xtype : 'commonowntruckselector',
								allowBlank : false,
								listeners : {
									blur : function(field, event, eOpts) {
										if (field.value != '') {
											if (predeliver.editDeliverbill.deliverbillId != '') {
												// 车牌号不为空，且派送单已保存时，刷新派送单统计信息
												var deliverbillInfo = predeliver.editDeliverbill.deliverbillInfoForm
														.getRecord();
												var oldVehicleNo = deliverbillInfo
														.get('vehicleNo');

												if (field.value != oldVehicleNo) {
													Ext
															.getCmp(
																	'T_predeliver-editDeliverbillIndex_content')
															.refreshVehicleLoadInfo(
																	field.value,
																	field,
																	oldVehicleNo);
												}
											}
										}
									}
								}
							},
							{
								name : 'driverName',
								fieldLabel : predeliver.editDeliverbill
										.i18n('foss.pickup.predeliver.driverName'), // 司机姓名
								columnWidth : .33,
								xtype : 'commonowndriverselector',
								deptCode : predeliver.fleetCode,
								// allowBlank : false,
								listeners : {
									blur : function(field, event, eOpts) {
										// 公共选择器的value为driverCode,rawValue为driverName
										// 更新driverCode
										predeliver.editDeliverbill.deliverbillBasicInfoForm
												.getForm().findField(
														'driverCode').setValue(
														field.value);

										// 若修改了司机，且派送单已保存，则更新“统计信息”中的司机姓名
										if (field.value != '') {
											if (predeliver.editDeliverbill.deliverbillId != '') {
												var deliverbillInfo = predeliver.editDeliverbill.deliverbillInfoForm
														.getRecord();
												var oldDriverName = deliverbillInfo
														.get('driverName');
												
												// 更新“统计信息”中的司机姓名
												if (field.rawValue != oldDriverName) {
													deliverbillInfo.set(
															'driverName',
															field.rawValue);
													predeliver.editDeliverbill.deliverbillInfoForm
															.loadRecord(deliverbillInfo);
												}
											}
										}
									}
								}

							}, {
								name : 'driverCode',
								hidden : true
							} ]
				});

// 定义预派送单明细（待排运单复用）的模型
Ext
		.define(
				'Foss.predeliver.editDeliverbill.DeliverbillDetailModel',
				{
					extend : 'Ext.data.Model',
					idgen : 'uuid',// EXT在前台为每个模型额外以UUID方式生成主键
					idProperty : 'extid',// 以上生成的主键用在名叫“extid”的列
					// 定义字段
					fields : [
							{
								name : ' extid ',
								type : 'string'
							},// 额外的用于生成的EXT使用的列
							{
								name : 'id',
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
							/*
							 * ,convert : function(v, record) { return
							 * record.data.arrangableGoodsQty > 0 ?
							 * record.data.arrangableGoodsQty :
							 * record.data.arrangeGoodsQty; }
							 */
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
													predeliver.editDeliverbill.numberPrecision);
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
													predeliver.editDeliverbill.numberPrecision);
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
							}, {
								name : 'currencyCode', // 币种
								type : 'string'
							}, {
								name : 'returnBillType', // 返单类型
								type : 'string'
							}, {
								name : 'goodsPackage', // 包装
								type : 'string'
							} ]
				});

// 定义预派送单明细的Store
Ext
		.define(
				'Foss.predeliver.editDeliverbill.DeliverbillDetailStore',
				{
					extend : 'Ext.data.Store',
					autoLoad : true,
					// 绑定一个模型
					model : 'Foss.predeliver.editDeliverbill.DeliverbillDetailModel',
					pageSize : 10,
					// 定义一个代理对象
					proxy : {
						type : 'ajax',
						actionMethods : 'POST',
						url : predeliver
								.realPath('queryDeliverbillDetailList.action'),
						// 定义一个读取器
						reader : {
							// 以JSON的方式读取
							type : 'json',
							// 定义读取JSON数据的根对象
							root : 'deliverbillVo.deliverbillDetailList',
							totalProperty : 'totalCount'
						}
					},
					constructor : function(config) {
						var me = this, cfg = Ext.apply({}, config);
						me.callParent([ cfg ]);
					},
					listeners : {
						beforeload : function(store, operation, eOpts) {
							Ext
									.apply(
											operation,
											{
												params : {
													'deliverbillVo.deliverbillDto.id' : predeliver.editDeliverbill.deliverbillId
												}
											});
						}
					}
				});

// 定义预派送单的模型
Ext.define('Foss.predeliver.editDeliverbill.DeliverbillModel', {
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
		name : 'deliverbillNo', // 派送单号
		type : 'string'
	}, {
		name : 'vehicleNo', // 车辆
		type : 'string'
	}, {
		name : 'driverCode', // 司机编号
		type : 'string'
	}, {
		name : 'driverName', // 司机姓名
		type : 'string'
	}, {
		name : 'waybillQtyTotal', // 总票数
		type : 'int'
	}, {
		name : 'goodsQtyTotal', // 总件数
		type : 'int'
	}, {
		name : 'payAmountTotal', // 总到付金额
		type : 'number'
	}, {
		name : 'weightTotal', // 总重量
		type : 'number'
	}, {
		name : 'volumeTotal', // 总体积
		type : 'number'
	}, {
		name : 'createUserName', // 创建人
		type : 'string'
	}, {
		name : 'createUserCode', // 创建人编码
		type : 'string'
	}, {
		name : 'submitTime' // 提交时间
	}, {
		name : 'tOptTruckDepartId', // 车辆放行ID
		type : 'string'
	}, {
		name : 'status', // 状态
		type : 'string'
	}, {
		name : 'createOrgName', // 创建部门
		type : 'string'
	}, {
		name : 'createOrgCode', // 创建部门编码
		type : 'string'
	}, {
		name : 'operator', // 操作人
		type : 'date'
	}, {
		name : 'operatorCode', // 操作人编码
		type : 'string'
	}, {
		name : 'operateOrgName', // 操作人部门
		type : 'string'
	}, {
		name : 'operateOrgCode', // 操作人部门编码
		type : 'string'
	}, {
		name : 'operateTime', // 操作时间
		type : 'string'
	}, {
		name : 'fastWaybillQty', // 卡货票数
		type : 'int'
	} ]
});

Ext.define('Foss.predeliver.editDeliverbill.DeliverbillDetailForm', {
	extend : 'Ext.form.Panel',
	frame : false,
	defaults : {
		margin : '5 15 5 15',
		labelWidth : 100
	},
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	defaultType : 'textfield',
	layout : 'column',
	items : [ {
		name : 'waybillNo',
		fieldLabel : '运单号',
		columnWidth : .33,
		readOnly : true
	}, {
		name : 'goodsName',
		fieldLabel : '货物名称',
		columnWidth : .33,
		readOnly : true
	}, {
		name : 'arrangeGoodsQty',
		fieldLabel : '排单件数',
		columnWidth : .33,
		readOnly : true
	}, {
		name : 'arrangedWeight',
		fieldLabel : '排单重量',
		columnWidth : .33,
		readOnly : true
	}, {
		name : 'transportType',
		fieldLabel : '运输方式',
		columnWidth : .33,
		readOnly : true
	}, {
		name : 'dimension',
		fieldLabel : '体积',
		columnWidth : .33,
		readOnly : true
	}, {
		name : 'consignee',
		fieldLabel : '收货人',
		columnWidth : .33,
		readOnly : true
	}, {
		name : 'consigneeContact',
		fieldLabel : '联系方式',
		columnWidth : .33,
		readOnly : true
	}, {
		name : 'consigneeAddress',
		fieldLabel : '收货地址',
		columnWidth : .33,
		readOnly : true
	}, {
		name : 'deliverRequire',
		fieldLabel : '注意事项',
		columnWidth : .33,
		readOnly : true
	}, {
		name : 'goodsStatus',
		fieldLabel : '货物状态',
		columnWidth : .33,
		readOnly : true
	}, {
		name : 'isAlreadyContact',
		fieldLabel : '是否已联系客户',
		columnWidth : .33,
		readOnly : true
	}, {
		name : 'estimatedDeliverTime',
		fieldLabel : '预计送货时间',
		columnWidth : .33,
		readOnly : true,
		xtype : 'datefield',
		format : 'Y-m-d H:i:s'
	}, {
		name : 'isException',
		fieldLabel : '是否异常',
		columnWidth : .33,
		readOnly : true
	}, {
		name : 'isNeedInvoice',
		fieldLabel : '是否需要发票',
		columnWidth : .33,
		readOnly : true
	}, {
		name : 'paymentType',
		fieldLabel : '付款方式',
		columnWidth : .33,
		readOnly : true
	}, {
		name : 'notes',
		fieldLabel : '备注',
		columnWidth : .33,
		readOnly : true
	}, {
		name : 'deliverType',
		fieldLabel : '送货方式',
		columnWidth : .33,
		readOnly : true
	} ],
	bindData : function(record) {
		this.loadRecord(record);
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
});

// 定义派送单明细的表格
Ext
		.define(
				'Foss.predeliver.editDeliverbill.DeliverbillDetailGrid',
				{
					extend : 'Ext.grid.Panel',
					frame : true,
					title : predeliver.editDeliverbill
							.i18n('foss.pickup.predeliver.deliverbillDetailInfo'), // 待排单信息
					emptyText : '查询结果为空',
					bodyCls : 'autoHeight',
					cls : 'autoHeight',
					autoScroll : true,
					collapsible : true,
					animCollapse : true,
					dockedItems : [ {
						xtype : 'toolbar',
						dock : 'top',
						layout : 'column',
						items : [
								{
									border : 1,
									xtype : 'button',
									columnWidth : .08,
									text : '移除',
									handler : function() {
										var selectRow = predeliver.editDeliverbill.deliverbillDetailGrid
												.getSelectionModel()
												.getSelection();

										if (selectRow.length == 0) {
											Ext.ux.Toast.msg("提示信息",
													"请选中要移除的派送单明细");
											return;
										}

										Ext.Msg
												.confirm(
														'提示信息',
														'确定要移除选中的派送单明细吗？',
														function(btn, text) {
															if (btn == "yes") {
																var ids = '';
																for ( var i = 0; i < selectRow.length; i++) {
																	ids = selectRow[i].data.id
																			+ ","
																			+ ids;
																}

																predeliver.editDeliverbill.deliverbillDetailGrid
																		.deleteDeliverbillDetails(ids);
															}
														})
									}
								},{
					            	xtype: 'image',
					            	margin:'0 0 0 30',
					            	imgCls: 'foss_icons_pkp_flaggreen'
				                },{
				                	 xtype: 'label',
				                     margin:'0 0 0 10',
				                     text: '排单货物的件数与开单的件数不同'
				                },{
					            	xtype: 'image',
					            	margin:'0 0 0 30',
					            	imgCls: 'foss_icons_pkp_goodsNumDisac'
				                },{
				                	 xtype: 'label',
				                     margin:'0 0 0 10',
				                     text: '排单件数与库存件数不同'
				                },{
									xtype : 'container',
									border : false,
									columnWidth : .80,
									html : '&nbsp;'
								},{
									border : 1,
									xtype : 'button',
									columnWidth : .08,
									text : '排单',
									handler : function() {
										// 排单前需验证车辆信息
										if (predeliver.editDeliverbill.deliverbillId == "") {
											// 排单前，若还未保存派送单，则需先保存派送单
											// 验证车辆信息
											if (!predeliver.editDeliverbill.submitForm
													.validateOnArrange()) {
												return;
											}

											var deliverbillBasicInfo = predeliver.editDeliverbill.deliverbillBasicInfoForm
													.getValues();

											// 保存派送单
											Ext.Ajax
													.request({
														url : predeliver
																.realPath('saveDeliverbill.action'),
														jsonData : {
															'deliverbillVo' : {
																'deliverbill' : {
																	'id' : predeliver.editDeliverbill.deliverbillId,
																	'deliverbillNo' : deliverbillBasicInfo.deliverbillNo,
																	'vehicleNo' : deliverbillBasicInfo.vehicleNo,
																	'driverCode' : deliverbillBasicInfo.driverCode,
																	'driverName' : predeliver.editDeliverbill.deliverbillBasicInfoForm
																			.getForm()
																			.findField(
																					"driverName").rawValue
																}
															}
														},
														success : function(
																response) {
															var result = Ext
																	.decode(response.responseText);
															predeliver.editDeliverbill.deliverbillId = result.deliverbillVo.deliverbill.id;

															predeliver.editDeliverbill.deliverbillDetailGrid
																	.showArrangeWaybillWindow();
														}
													});
										} else {
											predeliver.editDeliverbill.deliverbillDetailGrid
													.showArrangeWaybillWindow();
										}
									}
								} ]
					} ],
					columns : [
							{
								dataIndex : 'id',
								align : 'center',
								flex : 1,
								hidden : true
							},
							{
								dataIndex : 'stockGoodQty',
								align : 'center',
								flex : 1,
								hidden : true
							},
							{
								dataIndex : 'serialNo',
								align : 'center',
								flex : 1,
								hidden : true
							},
							{
								// SR-4 如果排单货物的件数与开单的件数不同,用绿旗标示
								align : 'center',
								flex : 0.5,
								renderer : function(value, metaData, record) {
									if (record.get('waybillGoodsQty') != record
											.get('preArrangeGoodsQty')) {
										return "<div class='foss_icons_pkp_flaggreen'></div>";
									}
									return "";
								}
							},
							{
								xtype : 'actioncolumn',
								flex : 1,
								text : '操作',
								align : 'center',
								items : [
										{
											iconCls : 'deppon_icons_up',
											tooltip : '上移',
											handler : function(grid, rowIndex,
													colIndex) {
												// alert(grid.getStore().getAt(rowIndex).get('id'));
												if (grid.getStore().getAt(
														rowIndex).get(
														'serialNo') == 1) {
													Ext.ux.Toast.msg('温馨提示',
															'已经是第一条记录！',
															'error', 4000);
													return;
												}

												Ext.Ajax
														.request({
															url : predeliver
																	.realPath('upgradeDeliverbillDetail.action'),
															params : {
																'deliverbillVo.deliverbillDto.id' : predeliver.editDeliverbill.deliverbillId,
																'deliverbillVo.deliverbillDetail.id' : grid
																		.getStore()
																		.getAt(
																				rowIndex)
																		.get(
																				'id'),
																'deliverbillVo.deliverbillDetail.serialNo' : grid
																		.getStore()
																		.getAt(
																				rowIndex)
																		.get(
																				'serialNo')
															},
															success : function(
																	response) {
																// 刷新当前页
																predeliver.editDeliverbill.deliverbillDetailGrid.store
																		.load();
															}
														});

												if (grid.getStore().getAt(
														rowIndex).get(
														'serialNo') == 1) {
													Ext.ux.Toast.msg('温馨提示',
															'已经是第一条记录！',
															'error', 4000);
													return;
												}
											}
										},
										{
											iconCls : 'deppon_icons_down',
											tooltip : '下移',
											handler : function(grid, rowIndex,
													colIndex) {
												// alert(grid.getStore().getAt(rowIndex).get('id'));
												if (grid.getStore().getAt(
														rowIndex).get(
														'serialNo') == grid
														.getStore()
														.getTotalCount()) {
													Ext.ux.Toast.msg('温馨提示',
															'已经是最后一条记录！',
															'error', 4000);
													return;
												}

												Ext.Ajax
														.request({
															url : predeliver
																	.realPath('downgradeDeliverbillDetail.action'),
															params : {
																'deliverbillVo.deliverbillDto.id' : predeliver.editDeliverbill.deliverbillId,
																'deliverbillVo.deliverbillDetail.id' : grid
																		.getStore()
																		.getAt(
																				rowIndex)
																		.get(
																				'id'),
																'deliverbillVo.deliverbillDetail.serialNo' : grid
																		.getStore()
																		.getAt(
																				rowIndex)
																		.get(
																				'serialNo')
															},
															success : function(
																	response) {
																// 刷新当前页
																predeliver.editDeliverbill.deliverbillDetailGrid.store
																		.load();
															}
														});
											}
										},
										{
											iconCls : 'deppon_icons_cancel',
											tooltip : '移除',
											handler : function(grid, rowIndex,
													colIndex) {
												// alert(grid.getStore().getAt(rowIndex).get('id'));
												Ext.Msg
														.confirm(
																'提示信息',
																'确定要移除该派送单明细吗？',
																function(btn,
																		text) {
																	if (btn == "yes") {
																		predeliver.editDeliverbill.deliverbillDetailGrid
																				.deleteDeliverbillDetails(grid
																						.getStore()
																						.getAt(
																								rowIndex)
																						.get(
																								'id'));
																	}
																});
											}
										} ]
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
								dataIndex : 'waybillGoodsQty',
								align : 'center',
								flex : 1,
								header : '件数'
							}, {
								dataIndex : 'preArrangeGoodsQty',
								align : 'center',
								flex : 1,
								header : '排单件数'
							}, {
								dataIndex : 'transportType',
								align : 'center',
								flex : 1,
								// renderer : function(value) {
								// var store = Ext
								// .create('Foss.pickup.DeliverbillStatusStore');
								// var index = store.findBy(function(record, id)
								// {
								// return record.get('code') == value;
								// });
								//
								// return store.getAt(index).get('name');
								// },
								header : '运输方式'
							}, {
								dataIndex : 'arriveTime',
								align : 'center',
								flex : 1,
								header : '到达时间',
								xtype : 'datecolumn',
								format : 'Y-m-d H:i:s'
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
					// 弹出排单窗口
					showArrangeWaybillWindow : function() {
						var arrangeDeliverbillWindow = Ext.getCmp(
								'T_predeliver-editDeliverbillIndex_content')
								.getArrangeDeliverbillWindow();
						// 初始化界面
						arrangeDeliverbillWindow.getWaybillToArrangeGrid().store
								.removeAll();
						arrangeDeliverbillWindow.getWaybillToArrangeGrid()
								.getPagingToolbar().hide();
						// 清空选中的运单
						predeliver.editDeliverbill.waybillsToArrange.clear();

						arrangeDeliverbillWindow.show();
					},
					deleteDeliverbillDetails : function(deliverbillDetailIds) {
						Ext.Ajax
								.request({
									url : predeliver
											.realPath('deleteDeliverbillDetails.action'),
									params : {
										'deliverbillVo.deliverbillDto.id' : predeliver.editDeliverbill.deliverbillId,
										'deliverbillVo.deliverbillDetailIds' : deliverbillDetailIds
									},
									success : function(response) {
										var result = Ext
												.decode(response.responseText);
										var deliverbill = Ext
												.create(
														'Foss.predeliver.editDeliverbill.DeliverbillModel',
														result.deliverbillVo.deliverbill);

										predeliver.editDeliverbill.deliverbillDetailGrid.store
												.load();

										predeliver.editDeliverbill.deliverbillInfoForm
												.loadRecord(deliverbill);

										Ext
												.getCmp(
														'T_predeliver-editDeliverbillIndex_content')
												.refreshVehicleLoadInfo(
														deliverbill
																.get('vehicleNo'));
									}
								});
					},
					plugins : [ {
						header : true,
						// 定义行可展开的插件ID
						pluginId : 'rowexpander_plugin_id',
						// 定义插件的类型
						ptype : 'rowexpander',
						// 定义行展开模式（单行与多行），默认是多行展开(值true)
						rowsExpander : false,
						// 行体内容
						rowBodyElement : 'Foss.predeliver.editDeliverbill.DeliverbillDetailForm'
					} ],
					viewConfig : {
						stripeRows : false,
						getRowClass : function(record, rowIndex, rp, ds) {
							// SR-4 排单件数与库存件数不同，系统用红色标识此行
							if (record.get('preArrangeGoodsQty') != record
									.get('stockGoodQty')) {
								return 'predeliver_row_red';
							}
						}
					},
					constructor : function(config) {
						var me = this, cfg = Ext.apply({}, config);
						me.store = Ext
								.create('Foss.predeliver.editDeliverbill.DeliverbillDetailStore');
						me.selModel = Ext.create('Ext.selection.CheckboxModel');

						me.bbar = me.getPagingToolbar();
						me.callParent([ cfg ]);
					}
				});

// 统计信息Form
Ext.define('Foss.predeliver.editDeliverbill.DeliverbillInfoForm', {
	extend : 'Ext.form.Panel',
	title : '统计信息',
	frame : true,
	collapsible : true,
	animCollapse : true,
	defaults : {
		margin : '5 15 5 15',
		labelWidth : 120
	},
	defaultType : 'textfield',
	layout : 'column',
	items : [ {
		name : 'vehicleNo',
		fieldLabel : '车辆',
		columnWidth : .33,
		readOnly : true
	}, {
		name : 'vehicleType',
		fieldLabel : '车型',
		columnWidth : .33,
		readOnly : true
	}, {
		name : 'driverName',
		fieldLabel : '司机',
		columnWidth : .33,
		readOnly : true
	}, {
		name : 'deliverbillNo',
		fieldLabel : '对应预派送单',
		columnWidth : .33,
		value : predeliver.editDeliverbill.deliverbillNo,
		readOnly : true
	}, {
		name : 'weightTotal',
		fieldLabel : '总重量(公斤)',
		columnWidth : .33,
		readOnly : true
	}, {
		name : 'volumeTotal',
		fieldLabel : '总体积(方)',
		columnWidth : .33,
		readOnly : true
	}, {
		name : 'vehicleLoad',
		fieldLabel : '可载重量(公斤)',
		columnWidth : .33,
		readOnly : true
	}, {
		name : 'vehicleVolume',
		fieldLabel : '可载体积(方)',
		columnWidth : .33,
		readOnly : true
	}, {
		name : 'loadRate',
		fieldLabel : '装载率(容量/体积) ',
		columnWidth : .33,
		readOnly : true
	}, {
		name : 'waybillQtyTotal',
		fieldLabel : '总票数',
		columnWidth : .33,
		readOnly : true
	}, {
		name : 'goodsQtyTotal',
		fieldLabel : '总件数',
		columnWidth : .33,
		readOnly : true
	}, {
		name : 'payAmountTotal',
		fieldLabel : '到付金额',
		columnWidth : .33,
		readOnly : true
	} ]
});

// 定义送货方式store
Ext.define('Foss.predeliver.editDeliverbill.ReceiveMethodStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.predeliver.editDeliverbill.DataDictionaryModel',
	data : {
		'items' : [ {
			'code' : '',
			'name' : '全部'
		}, {
			'code' : 'DELIVERY',
			'name' : '派送'
		}, {
			'code' : 'SELF_PICKUP',
			'name' : '自提'
		} ]
	},
	proxy : {
		type : 'memory',
		reader : {
			type : 'json',
			root : 'items'
		}
	},
	autoLoad : true
});

// 查询运单form
Ext
		.define(
				'Foss.predeliver.editDeliverbill.QueryWaybillForm',
				{
					title : '货物信息查询',
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
								name : 'receiveCustomerDistCode',
								fieldLabel : '行政区域 ',
								columnWidth : .33
							},
							{
								name : 'deliverRegionCode',
								fieldLabel : '定人定区',
								columnWidth : .33
							},
							{
								name : 'waybillNo',
								fieldLabel : '运单号',
								columnWidth : .33
							},
							{
								xtype : 'combobox',
								name : 'receiveMethod',
								fieldLabel : '送货方式',
								columnWidth : .33,
								displayField : 'name',
								valueField : 'code',
								queryMode : 'local',
								triggerAction : 'all',
								editable : false,
								store : Ext
										.create('Foss.predeliver.editDeliverbill.ReceiveMethodStore'),
								value : ''
							},
							{
								name : 'receiveCustomerName',
								fieldLabel : '收货人名称',
								columnWidth : .33
							},
							{
								name : 'receiveCustomerPhone',
								fieldLabel : '收货人电话',
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
											text : predeliver.editDeliverbill
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
											text : predeliver.editDeliverbill
													.i18n('foss.pickup.predeliver.queryButton'), // 查询
											handler : function() {
												var pagingToolbar = this
														.up('window')
														.getWaybillToArrangeGrid()
														.getPagingToolbar();

												pagingToolbar.show();
												pagingToolbar.moveFirst();
											}
										} ]
							} ]
				});

// 定义待排单货物信息的Store
Ext
		.define(
				'Foss.predeliver.editDeliverbill.WaybillToArrangeStore',
				{
					extend : 'Ext.data.Store',
					autoLoad : false,
					// 绑定一个模型
					model : 'Foss.predeliver.editDeliverbill.DeliverbillDetailModel',
					pageSize : 5,
					// 定义一个代理对象
					proxy : {
						type : 'ajax',
						actionMethods : 'POST',
						url : predeliver
								.realPath('queryWaybillToArrange.action'),
						// 定义一个读取器
						reader : {
							// 以JSON的方式读取
							type : 'json',
							// 定义读取JSON数据的根对象
							root : 'deliverbillVo.waybillToArrangeDtoList',
							totalProperty : 'totalCount'
						}
					},
					constructor : function(config) {
						var me = this, cfg = Ext.apply({}, config);
						me.callParent([ cfg ]);
					},
					listeners : {
						beforeload : function(store, operation, eOpts) {
							var queryWaybillForm = Ext
									.getCmp(
											'T_predeliver-editDeliverbillIndex_content')
									.getArrangeDeliverbillWindow()
									.getQueryWaybillForm();

							if (queryWaybillForm != null) {
								var queryParams = queryWaybillForm.getValues();
								Ext
										.apply(
												operation,
												{
													params : {
														'deliverbillVo.waybillToArrangeDto.receiveCustomerDistCode' : queryParams.receiveCustomerDistCode,
														'deliverbillVo.waybillToArrangeDto.deliverRegionCode' : queryParams.deliverRegionCode,
														'deliverbillVo.waybillToArrangeDto.waybillNo' : queryParams.waybillNo,
														'deliverbillVo.waybillToArrangeDto.receiveMethod' : queryParams.receiveMethod,
														'deliverbillVo.waybillToArrangeDto.receiveCustomerName' : queryParams.receiveCustomerName,
														'deliverbillVo.waybillToArrangeDto.receiveCustomerPhone' : queryParams.receiveCustomerPhone
													}
												});
							}
						},
						load : function(store, records, successful, oOpts) {
							selectedRecords = new Array();

							for ( var i = 0; i < records.length; i++) {
								predeliver.editDeliverbill.waybillsToArrange
										.each(function(item, index, length) {
											if (item.id == records[i].data.id) {
												// 需要调整records[i]的排单件数/体积/重量
												records[i]
														.set(
																'preArrangeGoodsQty',
																item.preArrangeGoodsQty);
												records[i].set(
														'arrangeGoodsQty',
														item.arrangeGoodsQty);
												records[i].set(
														'arrangedWeight',
														item.arrangedWeight);
												records[i]
														.set(
																'arrangedGoodsVolume',
																item.arrangedGoodsVolume);
												selectedRecords
														.push(records[i]);

												return false;
											}
										})
							}

							predeliver.editDeliverbill.arrangeDeliverbillWindow
									.getWaybillToArrangeGrid().selModel
									.select(selectedRecords);
						}
					}
				});

// 定义运单checkbox model
Ext.define('Foss.predeliver.editDeliverbill.CheckboxModel', {
	extend : 'Ext.selection.CheckboxModel',
	listeners : {
		select : function(row, record, index, eOpts) {
			predeliver.editDeliverbill.waybillsToArrange.add(record.data.id,
					record.data);

		},
		deselect : function(row, record, index, eOpts) {
			predeliver.editDeliverbill.waybillsToArrange
					.removeAtKey(record.data.id);
		}
	}
})

// 定义货物信息查询结果的表格
Ext
		.define(
				'Foss.predeliver.editDeliverbill.WaybillToArrangeGrid',
				{
					extend : 'Ext.grid.Panel',
					frame : true,
					title : '货物信息查询结果',
					emptyText : '查询结果为空',
					bodyCls : 'autoHeight',
					cls : 'autoHeight',
					autoScroll : true,
					collapsible : true,
					animCollapse : true,
					cellEditor : null,
					getCellEditor : function() {
						if (this.cellEditor == null) {
							// 对单元格级别进行编辑
							this.cellEditor = Ext.create(
									'Ext.grid.plugin.CellEditing', {
										// 设置鼠标点击多少次，触发编辑
										clicksToEdit : 1
									});
						}
						return this.cellEditor;
					},
					columns : [
							{
								dataIndex : 'id',
								align : 'center',
								flex : 1,
								hidden : true
							},
							{
								dataIndex : 'waybillNo',
								align : 'center',
								flex : 1,
								header : '运单号'
							},
							{
								dataIndex : 'goodsName',
								align : 'center',
								flex : 1,
								header : '货物名称'
							},
							{
								dataIndex : 'arrangedWeight',
								align : 'center',
								flex : 1,
								header : '排单重量'
							},
							{
								dataIndex : 'dimension',
								align : 'center',
								flex : 1,
								header : '体积'
							},
							{
								dataIndex : 'waybillGoodsQty',
								align : 'center',
								flex : 1,
								header : '件数'
							},
							{
								dataIndex : 'preArrangeGoodsQty',
								align : 'center',
								flex : 1,
								header : '排单件数',
								editor : {
									// 定义编辑框的类型，编辑框可以是表单中的所有类型
									xtype : 'numberfield',
									allowBlank : false,
									listeners : {
										'afterrender' : function(field, event,
												eOpts) {
											var waybillToArrangeGrid = field
													.up('gridpanel');

											var editor = waybillToArrangeGrid
													.getCellEditor().activeEditor;
											var selectRecord = null;

											editor
													.on(
															'startedit',
															function() {
																var selectRow = waybillToArrangeGrid
																		.getSelectionModel()
																		.getSelection();
																if (selectRow == null
																		|| selectRow.length <= 0)
																	return;
																selectRecord = selectRow[0];
															}, this);

											editor
													.on(
															'beforecomplete',
															function() {
																if (selectRecord == null) {
																	return;
																}

																if (field.lastValue > selectRecord
																		.get('arrangableGoodsQty')) {
																	Ext.ux.Toast
																			.msg(
																					"提示信息",
																					"排单件数不能大于当前到达件数",
																					'error',
																					4000);
																	editor
																			.cancelEdit();
																	return;
																}

																if (field.lastValue <= 0) {
																	Ext.ux.Toast
																			.msg(
																					"提示信息",
																					"排单件数必须大于0",
																					'error',
																					4000);
																	editor
																			.cancelEdit();
																	return;
																}

																selectRecord
																		.set(
																				'preArrangeGoodsQty',
																				field.lastValue);
																selectRecord
																		.set(
																				'arrangeGoodsQty',
																				field.lastValue);
																selectRecord
																		.set(
																				'arrangedWeight',
																				selectRecord.data.weight
																						* field.lastValue
																						/ selectRecord.data.waybillGoodsQty);

																selectRecord
																		.set(
																				'arrangedGoodsVolume',
																				selectRecord.data.goodsVolumeTotal
																						* field.lastValue
																						/ selectRecord.data.waybillGoodsQty);
															}, this);
										}
									}
								}
							}, {
								dataIndex : 'transportType',
								align : 'center',
								flex : 1,
								// renderer : function(value) {
								// var store = Ext
								// .create('Foss.pickup.DeliverbillStatusStore');
								// var index = store.findBy(function(record, id)
								// {
								// return record.get('code') == value;
								// });
								//
								// return store.getAt(index).get('name');
								// },
								header : '运输方式'
							}, {
								dataIndex : 'arriveTime',
								align : 'center',
								flex : 1,
								header : '到达时间',
								xtype : 'datecolumn',
								format : 'Y-m-d H:i:s'
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
					/*
					 * plugins : [{ header : true, // 定义行可展开的插件ID pluginId :
					 * 'rowexpander_plugin_id', // 定义插件的类型 ptype :
					 * 'rowexpander', // 定义行展开模式（单行与多行），默认是多行展开(值true)
					 * rowsExpander : false, // 行体内容 rowBodyElement :
					 * 'Foss.predeliver.editDeliverbill.DeliverbillDetailForm'
					 * }],
					 */
					viewConfig : {
						stripeRows : false,
						getRowClass : function(record, rowIndex, rp, ds) {
							// SR-4 排单件数与库存件数不同，系统用红色标识此行
							if (record.get('isSentRequired') == 'Y') {
								return 'predeliver_row_pink';
							}
						}
					},
					constructor : function(config) {
						var me = this, cfg = Ext.apply({}, config);
						me.store = Ext
								.create('Foss.predeliver.editDeliverbill.WaybillToArrangeStore');
						me.selModel = Ext
								.create('Foss.predeliver.editDeliverbill.CheckboxModel');

						me.plugins = [
								me.getCellEditor(),
								{
									header : true,
									// 定义行可展开的插件ID
									pluginId : 'rowexpander_plugin_id',
									// 定义插件的类型
									ptype : 'rowexpander',
									// 定义行展开模式（单行与多行），默认是多行展开(值true)
									rowsExpander : false,
									// 行体内容
									rowBodyElement : 'Foss.predeliver.editDeliverbill.DeliverbillDetailForm'
								} ]
						me.bbar = me.getPagingToolbar();
						me.callParent([ cfg ]);
					}
				});

// 确认运单form
Ext
		.define(
				'Foss.predeliver.editDeliverbill.ConfirmForm',
				{
					extend : 'Ext.form.Panel',
					frame : false,
					// bodyCls : 'autoHeight',
					// cls : 'autoHeight',
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
										if (predeliver.editDeliverbill.waybillsToArrange.length == 0) {
											Ext.ux.Toast.msg("提示信息", "请选中待排运单",
													'ok', 4000);
											return;
										}

										Ext.Ajax
												.request({
													url : predeliver
															.realPath('addWaybillToArrange.action'),
													jsonData : {
														'deliverbillVo' : {
															'waybillToArrangeDtoList' : predeliver.editDeliverbill.waybillsToArrange.items,
															'deliverbill' : {
																'id' : predeliver.editDeliverbill.deliverbillId
															}
														}
													},
													success : function(response) {
														var result = Ext
																.decode(response.responseText);

														var alertMsg = "";
														var failedList = result.deliverbillVo.waybillToArrangeDtoList;
														for ( var i = 0; i < failedList.length; i++) {
															alertMsg = alertMsg
																	+ "\n运单"
																	+ failedList[i].waybillNo
																	+ " "
																	+ failedList[i].failedToArrangeReason;
														}

														predeliver.editDeliverbill.arrangeDeliverbillWindow
																.close();

														// 刷新派送单信息
														Ext
																.getCmp(
																		'T_predeliver-editDeliverbillIndex_content')
																.refreshDeliverbillInfoForm();

														predeliver.editDeliverbill.deliverbillDetailGrid.store
																.load();

														if (alertMsg == "") {
															Ext.ux.Toast
																	.msg(
																			"提示信息",
																			result.message);
														} else {
															Ext.ux.Toast.msg(
																	"提示信息",
																	alertMsg,
																	'error',
																	4000);
														}
													}
												})
									}
								} ]
					} ]
				});

// 测试输出
/*
 * function toString(waybillsToArrange) { var result = "";
 * 
 * waybillsToArrange.each(function(item, index, length) { result += item.id + " " +
 * item.preArrangeGoodsQty + "\n"; })
 * 
 * return result; }
 */

// 排单窗口
Ext
		.define(
				'Foss.predeliver.editDeliverbill.ArrangeDeliverbillWindow',
				{
					extend : 'Ext.window.Window',
					// title : '货物信息查询',
					bodyCls : 'autoHeight',
					cls : 'autoHeight',
					modal : true,
					closeAction : 'hide',
					width : 1000,
					// 查询条件FORM
					queryWaybillForm : null,
					getQueryWaybillForm : function() {
						if (this.queryWaybillForm == null) {
							this.queryWaybillForm = Ext
									.create('Foss.predeliver.editDeliverbill.QueryWaybillForm');
						}
						return this.queryWaybillForm;
					},
					// 待排单货物信息GRID
					waybillToArrangeGrid : null,
					getWaybillToArrangeGrid : function() {
						if (this.waybillToArrangeGrid == null) {
							this.waybillToArrangeGrid = Ext
									.create('Foss.predeliver.editDeliverbill.WaybillToArrangeGrid');
						}
						return this.waybillToArrangeGrid;
					},
					// 确认运单FORM
					confirmForm : null,
					getConfirmForm : function() {
						if (this.confirmForm == null) {
							this.confirmForm = Ext
									.create('Foss.predeliver.editDeliverbill.ConfirmForm');
						}
						return this.confirmForm;
					},
					constructor : function(config) {
						var me = this, cfg = Ext.apply({}, config);
						me.items = [ me.getQueryWaybillForm(),
								me.getWaybillToArrangeGrid(),
								me.getConfirmForm() ];
						me.callParent([ cfg ]);
					}
				});

// 保存确定form
Ext
		.define(
				'Foss.predeliver.editDeliverbill.SubmitForm',
				{
					extend : 'Ext.form.Panel',
					frame : false,
					// bodyCls : 'autoHeight',
					// cls : 'autoHeight',
					height : 80,
					defaults : {
						margin : '0 20 0 0'
					},
					layout : 'column',
					// 排单前验证
					validateOnArrange : function() {
						// 11a 12a 排单员点击“保存”或“提交”时，如果未有选车辆。 系统提示“请选择车辆”
						if (!predeliver.editDeliverbill.deliverbillBasicInfoForm
								.getForm().isValid()) {
							Ext.ux.Toast.msg("提示信息", "请请选择车辆");
							return false;
						}

						return true;
					},
					// 保存/提交时验证
					validateOnSaveOrSubmit : function() {
						// 11a 12a 排单员点击“保存”或“提交”时，如果未有选车辆。 系统提示“请选择车辆”
						if (!predeliver.editDeliverbill.deliverbillBasicInfoForm
								.getForm().isValid()) {
							Ext.ux.Toast.msg("提示信息", "请请选择车辆");
							return false;
						}

						// 11b 12b
						// 排单员点击“保存”或“提交”时，如果未有运单货物。系统则提示“待排单货物为空，请选择待排单货物！”
						if (predeliver.editDeliverbill.deliverbillDetailGrid.store.data
								.getCount() == 0) {
							Ext.ux.Toast.msg("提示信息", "请选择待排单货物");
							return false;
						}

						return true;
					},
					// 保存/提交派送单
					saveOrSubmit : function(deliverbillStatus, button) {
						// TODO 需要验证车牌号/司机是否有效
						if (!predeliver.editDeliverbill.submitForm
								.validateOnSaveOrSubmit()) {
							return;
						}

						var deliverbillBasicInfo = predeliver.editDeliverbill.deliverbillBasicInfoForm
								.getValues();

						// 更新派送单
						Ext.Ajax
								.request({
									url : predeliver
											.realPath('saveDeliverbill.action'),
									jsonData : {
										'deliverbillVo' : {
											'deliverbill' : {
												'id' : predeliver.editDeliverbill.deliverbillId,
												'vehicleNo' : deliverbillBasicInfo.vehicleNo,
												'driverCode' : deliverbillBasicInfo.driverCode,
												'driverName' : predeliver.editDeliverbill.deliverbillBasicInfoForm
														.getForm().findField(
																"driverName").rawValue,
												'status' : deliverbillStatus
											}
										}
									},
									success : function(response) {
										if (typeof (button) != "undefined"
												&& button != null) {
											button.setText('修改');
										}

										var deliverbill = predeliver.editDeliverbill.deliverbillInfoForm
												.getRecord();

										var confirmTitle = deliverbillStatus == 'SAVED' ? '保存派送单成功'
												: '提交派送单成功';

										var confirmMsg = confirmTitle
												+ ',单号为'
												+ deliverbillBasicInfo.deliverbillNo
												+ '<br/>总重量(千克)：'
												+ deliverbill
														.get('weightTotal')
												+ '<br/>总体积(立方米)：'
												+ deliverbill
														.get('volumeTotal')
												+ '<br/>总票数：'
												+ deliverbill
														.get('waybillQtyTotal')
												+ '<br/>总件数：'
												+ deliverbill
														.get('goodsQtyTotal')
												+ '<br/>总到付金额：'
												+ deliverbill
														.get('payAmountTotal')
												+ '<br/>装载率(容量/体积)：'
												+ deliverbill.get('loadRate')
												+ '<br/>是否打印预派送单?'

										Ext.Msg
												.confirm(
														confirmTitle,
														confirmMsg,
														function(btn, text) {
															if (btn == "yes") {
																// 打印预派送单
																var printWin = Ext
																		.create(
																				'Foss.predeliver.deliverbill.PrintDeliverbillWindow',
																				{
																					'deliverbillId' : predeliver.editDeliverbill.deliverbillId
																				});

																// 获取打印的预排送单基本信息
																Ext.Ajax
																		.request({
																			url : predeliver
																					.realPath('queryDeliverbill.action'),
																			params : {
																				'deliverbillVo.deliverbillDto.id' : predeliver.editDeliverbill.deliverbillId
																			},
																			success : function(
																					response) {
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
																						.setDeliverbillId(predeliver.editDeliverbill.deliverbillId);
																				printWin
																						.getDeliverPrintGrid()
																						.getPagingToolbar()
																						.moveFirst();
																				printWin
																						.show();
																			}
																		});
															}
														})

										if (deliverbillStatus == 'SUBMITED') {
											Ext
													.getCmp(
															'T_predeliver-editDeliverbillIndex')
													.close();
											if (typeof (predeliver.deliverbill.deliverbillGrid) != "undefined"
													&& predeliver.deliverbill.deliverbillGrid != null) {
												predeliver.deliverbill.deliverbillGrid.store
														.load();
											}
										}

									}
								});
					},
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
									columnWidth : .84,
									html : '&nbsp;'
								},
								{
									columnWidth : .08,
									xtype : 'button',
									cls : 'yellow_button',
									text : predeliver.editDeliverbill.deliverbillId != '' ? '修改'
											: '保存',
									handler : function(button, e) {
										button.up('form').saveOrSubmit('SAVED',
												button);
									}
								},
								{
									columnWidth : .08,
									xtype : 'button',
									cls : 'yellow_button',
									text : '提交',
									handler : function(button, e) {
										button.up('form').saveOrSubmit(
												'SUBMITED');
									}
								} ]
					} ]
				});

Ext
		.onReady(function() {
			Ext.QuickTips.init();

			predeliver.editDeliverbill.deliverbillBasicInfoForm = Ext
					.create('Foss.predeliver.editDeliverbill.DeliverbillBasicInfoForm');
			predeliver.editDeliverbill.deliverbillDetailGrid = Ext
					.create('Foss.predeliver.editDeliverbill.DeliverbillDetailGrid');
			predeliver.editDeliverbill.deliverbillInfoForm = Ext
					.create('Foss.predeliver.editDeliverbill.DeliverbillInfoForm');
			predeliver.editDeliverbill.submitForm = Ext
					.create('Foss.predeliver.editDeliverbill.SubmitForm');

			predeliver.editDeliverbill.arrangeDeliverbillWindow = null;

			// ---- 重量/体积精度
			predeliver.editDeliverbill.numberPrecision = 2;
			predeliver.editDeliverbill.waybillsToArrange = new Ext.util.MixedCollection(); // 选中的待排运单集合

			Ext
					.create(
							'Ext.panel.Panel',
							{
								id : 'T_predeliver-editDeliverbillIndex_content',
								cls : "panelContentNToolbar",
								bodyCls : 'panelContentNToolbar-body',
								layout : 'auto',
								refreshVehicleLoadInfo : function(vehicleNo,
										vehicleNoField, oldVehicleNo) {
									if (vehicleNo != '') {
										Ext.Ajax
												.request({
													url : predeliver
															.realPath('queryVehicleByVehicleNo.action'),
													params : {
														'deliverbillVo.deliverbillDto.vehicleNo' : vehicleNo
													},
													success : function(response) {
														var result = Ext
																.decode(response.responseText);

														var driverNameField = predeliver.editDeliverbill.deliverbillBasicInfoForm
																.getForm()
																.findField(
																		'driverName');

														var vehicle = result.deliverbillVo.vehicleAssociationDto;

														if (vehicle != null) {
															var deliverbillInfo = predeliver.editDeliverbill.deliverbillInfoForm
																	.getRecord();

															// 更新派送单统计信息
															deliverbillInfo
																	.set(
																			'vehicleNo',
																			vehicle.vehicleNo);
															deliverbillInfo
																	.set(
																			'vehicleType',
																			vehicle.vehicleLengthValue);
															deliverbillInfo
																	.set(
																			'vehicleLoad',
																			vehicle.vehicleDeadLoad);
															deliverbillInfo
																	.set(
																			'vehicleVolume',
																			vehicle.vehicleSelfVolume);

															// 计算装载率
															var vehicleLoadRate = deliverbillInfo
																	.get('weightTotal')
																	/ vehicle.vehicleDeadLoad;
															var vehicleVolumeRate = deliverbillInfo
																	.get('volumeTotal')
																	/ vehicle.vehicleSelfVolume;

															if (vehicleLoadRate > 1) {
																// 10a
																// 点击“确认”时，如果预派送单中货物总体积或总重量超出车辆基础信息中的车辆额定载重或额定净空。系统则弹出“排单总体积或总重量超出车辆额定载重或额定净空”警告对话框。
																Ext.ux.Toast
																		.msg(
																				"警告信息",
																				"排单总重量超出车辆额定载重",
																				'error',
																				4000);
															}

															if (vehicleVolumeRate > 1) {
																// 10a
																// 点击“确认”时，如果预派送单中货物总体积或总重量超出车辆基础信息中的车辆额定载重或额定净空。系统则弹出“排单总体积或总重量超出车辆额定载重或额定净空”警告对话框。
																Ext.ux.Toast
																		.msg(
																				"警告信息",
																				"排单总体积超出车辆额定净空",
																				'error',
																				4000);
															}

															deliverbillInfo
																	.set(
																			'loadRate',
																			Ext.util.Format
																					.number(
																							vehicleLoadRate * 100,
																							'0')
																					+ "%"
																					+ " "
																					+ Ext.util.Format
																							.number(
																									vehicleVolumeRate * 100,
																									'0')
																					+ "%");

															/*
															 * SR9 1.
															 * 若排班（和处理订单中排班是同一个排班）中存在该车牌号，则用户在选择司机时自动带出对应的司机
															 * 2.
															 * 若PDA绑定中存在该车牌号，则系统自动带出对应的司机且不可修改
															 * 3.
															 * 当排班和PDA绑定同时存在时，以PDA绑定为准)
															 */
															var driver = result.deliverbillVo.driverDto;

															if (driver != null) {
																// 更新driverName的value和rawValue
																driverNameField
																		.setValue(driver.empCode);

																driverNameField
																		.setRawValue(driver.empName);

																predeliver.editDeliverbill.deliverbillBasicInfoForm
																		.getForm()
																		.findField(
																				'driverCode')
																		.setValue(
																				driver.empCode);

																if (driver.pdaSigned == 'Y') {
																	// 若PDA绑定中存在该车牌号，则系统自动带出对应的司机且不可修改
																	driverNameField
																			.setReadOnly(true);
																} else {
																	// 司机设置为可选择
																	driverNameField
																			.setReadOnly(false);
																}

																// 更新统计信息中的司机信息
																deliverbillInfo
																		.set(
																				'driverName',
																				driver.empName);
															} else {
																// 司机设置为可选择
																driverNameField
																		.setReadOnly(false);
															}

															predeliver.editDeliverbill.deliverbillInfoForm
																	.loadRecord(deliverbillInfo);
														} else {
															Ext.ux.Toast.msg(
																	"提示信息",
																	"车牌号车辆不存在",
																	'error',
																	4000);
															if (typeof (vehicleNoField) != "undefined"
																	&& vehicleNoField != null) {
																vehicleNoField
																		.setValue(oldVehicleNo);
															}
															driverNameField
																	.setReadOnly(false);
														}
													}
												});
									}
								},
								refreshDeliverbillInfoForm : function() {
									if (predeliver.editDeliverbill.deliverbillId != '') {
										// 若派送单已保存，则刷新统计信息和车载信息
										Ext.Ajax
												.request({
													url : predeliver
															.realPath('queryDeliverbill.action'),
													params : {
														'deliverbillVo.deliverbillDto.id' : predeliver.editDeliverbill.deliverbillId
													},
													success : function(response) {
														var result = Ext
																.decode(response.responseText);
														var deliverbill = Ext.ModelManager
																.create(
																		result.deliverbillVo.deliverbill,
																		'Foss.predeliver.editDeliverbill.DeliverbillModel');

														predeliver.editDeliverbill.deliverbillBasicInfoForm
																.loadRecord(deliverbill);
														predeliver.editDeliverbill.deliverbillInfoForm
																.loadRecord(deliverbill);

														Ext
																.getCmp(
																		'T_predeliver-editDeliverbillIndex_content')
																.refreshVehicleLoadInfo(
																		deliverbill
																				.get('vehicleNo'));
													}
												});
									}
								},
								getArrangeDeliverbillWindow : function() {
									if (predeliver.editDeliverbill.arrangeDeliverbillWindow == null) {
										predeliver.editDeliverbill.arrangeDeliverbillWindow = Ext
												.create('Foss.predeliver.editDeliverbill.ArrangeDeliverbillWindow');
									}

									return predeliver.editDeliverbill.arrangeDeliverbillWindow;
								},
								items : [
										predeliver.editDeliverbill.deliverbillBasicInfoForm,
										predeliver.editDeliverbill.deliverbillDetailGrid,
										predeliver.editDeliverbill.deliverbillInfoForm,
										predeliver.editDeliverbill.submitForm ],
								renderTo : 'T_predeliver-editDeliverbillIndex-body'
							});

			Ext.getCmp('T_predeliver-editDeliverbillIndex_content')
					.refreshDeliverbillInfoForm();
		});