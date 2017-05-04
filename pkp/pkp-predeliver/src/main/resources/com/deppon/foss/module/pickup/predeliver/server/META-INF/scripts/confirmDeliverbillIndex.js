// 定义预派送单的模型
Ext.define('Foss.predeliver.confirmDeliverbill.DeliverbillModel', {
	extend : 'Ext.data.Model',
	// 定义字段
	fields : [ {
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

// 定义预派送单明细的模型
Ext
		.define(
				'Foss.predeliver.confirmDeliverbill.DeliverbillDetailModel',
				{
					extend : 'Ext.data.Model',
					// 定义字段
					fields : [
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
													predeliver.confirmDeliverbill.numberPrecision);
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
													predeliver.confirmDeliverbill.numberPrecision);
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

// 装车差异报告Model
Ext.define('Foss.predeliver.confirmDeliverbill.LoadGaprepModel', {
	extend : 'Ext.data.Model',
	fields : [ {
		name : 'wayBillNo',
		type : 'string'
	}, {
		name : 'gapType',
		type : 'string'
	}, {
		name : 'planLoadQty',
		type : 'int'
	}, {
		name : 'actualLoadQty',
		type : 'int'
	}, {
		name : 'lackGoodsQty',
		type : 'int'
	}, {
		name : 'notes',
		type : 'string'
	}, {
		name : 'transportType',
		type : 'string'
	}, {
		name : 'reportId',
		type : 'string'
	}, {
		name : 'overGoodsQty',
		type : 'int'
	} ]
});

// 装车任务明细Model
Ext.define('Foss.predeliver.confirmDeliverbill.LoadTaskDetailModel', {
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
		name : 'origOrgCode',
		type : 'string'
	}, {
		name : 'reachOrgName',
		type : 'string'
	}, {
		name : 'waybillNo',
		type : 'string'
	}, {
		name : 'transportType',
		type : 'string'
	}, {
		name : 'stockQty',
		type : 'string'
	}, {
		name : 'scanQty',
		type : 'string'
	}, {
		name : 'loadQty',
		type : 'string'
	}, {
		name : 'stockWeight',
		type : 'string'
	}, {
		name : 'stockVolume',
		type : 'string'
	}, {
		name : 'goodsName',
		type : 'string'
	}, {
		name : 'pack',
		type : 'string'
	} ]
});

// 少货明细Model
Ext.define('Foss.predeliver.confirmDeliverbill.LoadGaprepDetailModel', {
	extend : 'Ext.data.Model',
	fields : [ {
		name : 'wayBillNo',
		type : 'string'
	}, {
		name : 'serialNo',
		type : 'string'
	}, {
		name : 'scanState',
		type : 'string',
		convert : function(value) {
			if (value == 'SCANED') {
				return predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.scaned');
			} else if (value == 'UNSCANED' || value == 'N/A') {
				return predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.unscaned');
			} else if (value == 'BY_HAND') {
				return predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.byhand');
			}
		}
	}, {
		name : 'goodsState',
		type : 'string',
		convert : function(value) {
			if (value == 'NOT_LOADING') {
				return predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.notloading');
			} else if (value == 'EXTRA_UNPACKAGE') {
				return predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.extraunpackage');
			} else if (value == 'EXTRA_MODIFY') {
				return predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.extramodify');
			} else if (value == 'EXTRA_PACKAGE_UNOUT_STORAGE') {
				return predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.extrapackageunoutstorage');
			} else if (value == 'EXTRA_VALUABLES_UNOUT_STORAGE') {
				return predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.extravaluablesunoutstorage');
			}
		}
	}, {
		name : 'scanTime',
		type : 'string'
	} ]
});

// 装车任务扫描明细Model
Ext.define('Foss.predeliver.confirmDeliverbill.ScanDetailModel', {
	extend : 'Ext.data.Model',
	fields : [ {
		name : 'id',
		type : 'string'
	}, {
		name : 'serialNo',
		type : 'string'
	}, {
		name : 'scanState',
		type : 'string'
	}, {
		name : 'goodsState',
		type : 'string'
	}, {
		name : 'beLoaded',
		type : 'string'
	}, {
		name : 'loadTime',
		type : 'string',
		convert : function(value) {
			if (value != null) {
				var date = new Date(value);
				return Ext.Date.format(date, 'Y-m-d H:i:s');
			} else {
				return null;
			}
		}
	} ]
});

// 定义预派送单明细的Store
Ext
		.define(
				'Foss.predeliver.confirmDeliverbill.DeliverbillDetailStore',
				{
					extend : 'Ext.data.Store',
					autoLoad : true,
					// 绑定一个模型
					model : 'Foss.predeliver.confirmDeliverbill.DeliverbillDetailModel',
					pageSize : 5,
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
													'deliverbillVo.deliverbillDto.id' : predeliver.confirmDeliverbill.deliverbillId
												}
											});
						}
					}
				});

// 装车差异报告Store
Ext.define(
				'Foss.predeliver.confirmDeliverbill.LoadGaprepStore',
				{
					extend : 'Ext.data.Store',
					model : 'Foss.predeliver.confirmDeliverbill.LoadGaprepModel',
					proxy : {
						type : 'ajax',
						url : predeliver.realPath('queryLoadGaprep.action'),
						actionMethods : 'post',
						reader : {
							type : 'json',
							root : 'deliverbillVo.loadGaprepWaybillList'
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
													'deliverbillVo.loadTaskDto.loadGaprepId' : predeliver.confirmDeliverbill.loadGaprepId,
													'deliverbillVo.deliverbillDto.deliverbillNo' : predeliver.confirmDeliverbill.deliverbillInfoForm.getRecord().get('deliverbillNo')
												}
											});
						}
					}
				});

// 装车任务明细Store
Ext
		.define(
				'Foss.predeliver.confirmDeliverbill.LoadTaskDetailStore',
				{
					extend : 'Ext.data.Store',
					model : 'Foss.predeliver.confirmDeliverbill.LoadTaskDetailModel',
					proxy : {
						type : 'ajax',
						actionMethods : 'POST',
						url : predeliver.realPath('queryLoadTaskDetail.action'),
						// 定义一个读取器
						reader : {
							// 以JSON的方式读取
							type : 'json',
							// 定义读取JSON数据的根对象
							root : 'deliverbillVo.loadTaskDetailList'
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
													'deliverbillVo.loadTaskDto.taskNo' : predeliver.confirmDeliverbill.taskNo
												}
											});
						}
					}
				});

// 装车任务扫描明细Store
Ext.define('Foss.predeliver.confirmDeliverbill.ScanDetailStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.predeliver.confirmDeliverbill.ScanDetailModel',
	autoLoad : false,
	// 装车任务明细ID
	loadTaskDetailId : '',
	setLoadTaskDetailId : function(loadTaskDetailId) {
		this.loadTaskDetailId = loadTaskDetailId;
	},
	getLoadTaskDetailId : function() {
		return this.loadTaskDetailId;
	},
	proxy : {
		type : 'ajax',
		actionMethods : 'POST',
		url : predeliver.realPath('queryScanDetailList.action'),
		// 定义一个读取器
		reader : {
			// 以JSON的方式读取
			type : 'json',
			// 定义读取JSON数据的根对象
			root : 'deliverbillVo.scanDetailList'
		}
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	},
	listeners : {
		beforeload : function(store, operation, eOpts) {
			Ext.apply(operation, {
				params : {
					'deliverbillVo.loadTaskDetailId' : store
							.getLoadTaskDetailId()
				}
			});
		}
	}
});

// 统计信息Form
Ext.define('Foss.predeliver.confirmDeliverbill.DeliverbillInfoForm', {
	extend : 'Ext.form.Panel',
	title : predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.statistics'),//统计信息,
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
		name : 'deliverbillNo',
		fieldLabel : predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.deliverbill'),//预派送单,
		columnWidth : .66,
		readOnly : true
	}, {
		name : 'vehicleNo',
		fieldLabel : predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.vehicle'),//车辆,
		columnWidth : .33,
		readOnly : true
	}, {
		name : 'vehicleType',
		fieldLabel : predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.vehicleType'),//车型,
		columnWidth : .33,
		readOnly : true
	}, {
		name : 'vehicleLoad',
		fieldLabel : predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.vehicleLoad'),//可载重量(公斤),
		columnWidth : .33,
		readOnly : true
	}, {
		name : 'vehicleVolume',
		fieldLabel : predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.vehicleVolume'),//可载体积(方),
		columnWidth : .33,
		readOnly : true
	}, {
		name : 'weightTotal',
		fieldLabel : predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.weightTotal'),//总重量(公斤),
		columnWidth : .33,
		readOnly : true
	}, {
		name : 'volumeTotal',
		fieldLabel : predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.volumeTotal'),//总体积(方),
		columnWidth : .33,
		readOnly : true
	}, {
		name : 'loadRate',
		fieldLabel : predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.loadRate'),//装载率(容量/体积) ,
		columnWidth : .33,
		readOnly : true
	}, {
		name : 'waybillQtyTotal',
		fieldLabel : predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.waybillQtyTotal'),//总票数,
		columnWidth : .33,
		readOnly : true
	}, {
		name : 'goodsQtyTotal',
		fieldLabel : predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.goodsQtyTotal'),//总件数,
		columnWidth : .33,
		readOnly : true
	}, {
		name : 'payAmountTotal',
		fieldLabel : predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.payAmountTotal'),//到付金额,
		columnWidth : .33,
		readOnly : true
	} ]
});

// 定义派送单明细的表格
Ext
		.define(
				'Foss.predeliver.confirmDeliverbill.DeliverbillDetailGrid',
				{
					extend : 'Ext.grid.Panel',
					frame : true,
					title : predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.deliverbillInfo'),//排单信息,
					emptyText : predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.emptyText'),//查询结果为空,
					bodyCls : 'autoHeight',
					cls : 'autoHeight',
					autoScroll : true,
					collapsible : true,
					animCollapse : true,
					columns : [ {
						dataIndex : 'id',
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
						header : predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.waybillNo')//运单号
					}, {
						dataIndex : 'goodsName',
						align : 'center',
						flex : 1,
						header : predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.goodsNames')//货物名称
					}, {
						dataIndex : 'weight',
						align : 'center',
						flex : 1,
						header : predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.weight')//排单重量
					}, {
						dataIndex : 'goodsVolumeTotal',
						align : 'center',
						flex : 1,
						header : predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.dimension')//体积
					}, {
						dataIndex : 'preArrangeGoodsQty',
						align : 'center',
						flex : 1,
						header : predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.arrangeGoodsQty')//排单件数
					}, {
						dataIndex : 'estimatedDeliverTime',
						align : 'center',
						flex : 1,
						header : predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.estimatedDeliverTime'),//预计送货时间,
						xtype : 'datecolumn',
						format : 'Y-m-d H:i:s'
					}, {
						dataIndex : 'consignee',
						align : 'center',
						flex : 1,
						header : predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.consignee')//收货人
					}, {
						dataIndex : 'consigneeContact',
						align : 'center',
						flex : 1,
						header : predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.consigneeContact')//联系方式
					}, {
						dataIndex : 'consigneeAddress',
						align : 'center',
						flex : 1,
						header : predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.consigneeAddress')//收货地址
					}, {
						dataIndex : 'deliverRequire',
						align : 'center',
						flex : 1,
						header : predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.deliverRequire')//收货人注意事项
					}, {
						dataIndex : 'isAlreadyContact',
						align : 'center',
						flex : 1,
						renderer:function(value){
							if(value!='N'){
								return predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.yes');;
							}else{
								return predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.not');
							}
						}, 
						header : predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.isAlreadyContact')//是否已联系客户
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
								.create('Foss.predeliver.confirmDeliverbill.DeliverbillDetailStore');
						me.bbar = me.getPagingToolbar();
						me.callParent([ cfg ]);
					}
				});

// 装车差异报告GRID
Ext.define(
				'Foss.predeliver.confirmDeliverbill.LoadGaprepGrid',
				{
					extend : 'Ext.grid.Panel',
					emptyText : predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.emptyText'),//查询结果为空,
					frame : true,
					title : predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.loadDifferenceReport'),//装车差异报告,
					bodyCls : 'autoHeight',
					cls : 'autoHeight',
					autoScroll : true,
					collapsible : true,
					animCollapse : true,
					/*
					 * plugins: [{ pluginId :
					 * 'deliverloadgapreport_rowexpander_plugin_Id', ptype:
					 * 'rowexpander', rowsExpander: false, rowBodyElement:
					 * 'Foss.Load.DeliverLoadGapRep.SerialGrid' }],
					 */
					columns : [
							{
								text : predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.waybillNo'),//运单号,
								dataIndex : 'wayBillNo',
								flex : 1.2,
								windowClassName : 'Foss.predeliver.confirmDeliverbill.LoadGaprepDetailWindow',
								// 少货明细窗口
								// 定义列类型为扩展的openwindowcolumn类型
								xtype : 'openwindowcolumn'
							}, {
								text : predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.gapType'),//差异类型,
								dataIndex : 'gapType',
								flex : 1.2
							}, {
								text : predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.planLoadQty'),//预计装车件数,
								dataIndex : 'planLoadQty',
								flex : 1.4
							}, {
								text : predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.actualLoadQty'),//实际装车件数,
								dataIndex : 'actualLoadQty',
								flex : 1.4
							}, {
								text : predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.lackGoodsQty'),//差异件数(包含多货、少货两种情况),
								dataIndex : 'lackGoodsQty',
								renderer : function(value, metaData, record) {
									return Math.abs(value);
								},
								flex : 1.2
							}, {
								text : predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.transportType'),//运输性质,
								dataIndex : 'transportType',
								flex : 1.2
							}, {
								text : predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.notes'),//备注,
								dataIndex : 'notes',
								flex : 1.2
							} ],
					// 重新加载数据
					reloadStore : function() {
						this.store
								.load(function(records, operation, success) {
									// 加载差异报告统计信息
									var planLoadQty = 0;
									var actualLoadQty = 0;
									var lackGoodsQty = 0; // 少货件数
									var overGoodsQty = 0; // 多货件数

									for ( var i = 0; i < records.length; i++) {
										planLoadQty += records[i]
												.get('planLoadQty');
										actualLoadQty += records[i]
												.get('actualLoadQty');
										

										if (records[i].get('planLoadQty') > records[i].get('actualLoadQty')){
											// 少货
											lackGoodsQty += records[i].get('planLoadQty') - records[i].get('actualLoadQty');
										} else {
											// 多货
											overGoodsQty += records[i].get('actualLoadQty') - records[i].get('planLoadQty');
										}	
									}

									var loadGaprep = Ext.ModelManager
											.create({},
													'Foss.predeliver.confirmDeliverbill.LoadGaprepModel');

									loadGaprep.set('planLoadQty', planLoadQty);
									loadGaprep.set('actualLoadQty',
											actualLoadQty);
									loadGaprep
											.set('lackGoodsQty', '' + lackGoodsQty);
									loadGaprep
											.set('overGoodsQty', '' + overGoodsQty);

									predeliver.confirmDeliverbill.loadGaprepForm
											.loadRecord(loadGaprep);
								});
					},
					constructor : function(config) {
						var me = this, cfg = Ext.apply({}, config);
						me.store = Ext
								.create('Foss.predeliver.confirmDeliverbill.LoadGaprepStore');
						me.callParent([ cfg ]);
					}
				});

// 装车任务明细GRID
Ext
		.define(
				'Foss.predeliver.confirmDeliverbill.LoadTaskDetailGrid',
				{
					extend : 'Ext.grid.Panel',
					stripeRows : true,
					frame : true,
					title : predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.loadTaskDetail'),//装车任务明细,
					emptyText : predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.emptyText'),//查询结果为空,
					bodyCls : 'autoHeight',
					cls : 'autoHeight',
					autoScroll : true,
					collapsible : true,
					animCollapse : true,
					columns : [
							{
								dataIndex : 'id',
								flex : 1,
								hidden : true
							},
							/** 对于派送装车，出发、到达部门没有意义
							{
								header : predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.origOrgCode'),//出发部门,
								dataIndex : 'origOrgCode',
								flex : 1
							},
							{
								header : predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.reachOrgName'),//到达部门,
								dataIndex : 'reachOrgName',
								flex : 1
							},*/
							{
								header : predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.waybillNo'),//运单号,
								dataIndex : 'waybillNo',
								flex : 1,
								windowClassName : 'Foss.predeliver.confirmDeliverbill.ScanDetailWindow',
								// 定义列类型为扩展的openwindowcolumn类型
								xtype : 'openwindowcolumn'
							}, {
								header : predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.transportType'),//运输性质,
								dataIndex : 'transportType',
								flex : 1
							}, {
								header : predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.planLoadQty'),//预计装车件数,
								dataIndex : 'stockQty',
								flex : 1
							}, {
								header : predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.scanQty'),//已扫描件数,
								dataIndex : 'scanQty',
								flex : 1
							}, {
								header : predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.loadQty'),//已装车件数,
								dataIndex : 'loadQty',
								flex : 1
							}, {
								header : predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.stockWeight'),//库存重量(公斤),
								dataIndex : 'stockWeight',
								flex : 1.8
							}, {
								header : predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.stockVolume'),//库存体积(方),
								dataIndex : 'stockVolume',
								flex : 1.8
							}, {
								header : predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.goodsName'),//货名,
								dataIndex : 'goodsName',
								flex : 1.8
							}, {
								header : predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.pack'),//包装,
								dataIndex : 'pack',
								flex : 1.8
							} ],
					constructor : function(config) {
						var me = this, cfg = Ext.apply({}, config);
						me.store = Ext
								.create('Foss.predeliver.confirmDeliverbill.LoadTaskDetailStore');
						me.callParent([ cfg ]);
					}
				});

// 装车差异统计信息Form
Ext.define('Foss.predeliver.confirmDeliverbill.LoadGaprepForm', {
	extend : 'Ext.form.Panel',
	frame : true,
	collapsible : false,
	defaults : {
		margin : '5 15 5 15',
		labelWidth : 120
	},
	defaultType : 'textfield',
	layout : 'column',
	items : [ {
		name : 'planLoadQty',
		fieldLabel : predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.planLoadQtyTotal'),//预计装车总件数,
		columnWidth : .25,
		readOnly : true
	}, {
		name : 'actualLoadQty',
		fieldLabel : predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.actualLoadGoodsQty'),//实际装车总件数,
		columnWidth : .25,
		readOnly : true
	}, {
		name : 'lackGoodsQty',
		fieldLabel : predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.lackGoodsQtyTotal'),//少货总件数,
		columnWidth : .25,
		readOnly : true
	}, {
		name : 'overGoodsQty',
		fieldLabel : predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.overGoodsQtyTotal'),//多货总件数,
		columnWidth : .25,
		readOnly : true
	} ]
});

// 装车报告
Ext
		.define(
				'Foss.predeliver.confirmDeliverbill.LoadReportForm',
				{
					extend : 'Ext.panel.Panel',
					title : predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.loadReport'),//装车报告,
					frame : true,
					collapsible : true,
					animCollapse : true,
					cls : 'autoHeight',
					bodyCls : 'autoHeight',
					layout : 'auto',
					loadGaprepGrid : null,
					dockedItems : [ {
						xtype : 'toolbar',
						dock : 'bottom',
						layout : 'column',
						items : [
								{
									border : 1,
									xtype : 'button',
									columnWidth : .16,
									text : predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.queryHisLoadReport'),//查看历史装车差异报告,
									handler : function() {
										// 跳转到“查询装车差异报告”界面，参见“查询派送装车差异报告（SUC-601）”系统用例
										Ext
												.getCmp(
														'T_predeliver-confirmDeliverbillIndex_content')
												.getHistoryLoadGaprepWindow()
												.show();
									}
								},
								{
									border : 1,
									xtype : 'button',
									columnWidth : .08,
									text : predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.queryLoadingPeople'),//查看装车人,
									handler : function() {
										Ext
												.getCmp(
														'T_predeliver-confirmDeliverbillIndex_content')
												.getLoaderWindow().show();
									}
								},
								{
									xtype : 'container',
									border : false,
									columnWidth : .64,
									html : '&nbsp;'
								}, 
								{
									border : 1,
									xtype : 'button',
									columnWidth : .12,
									text : predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.returnLoadReport'),//退回装车报告,
									handler : function(rejectBtn) {
										if (predeliver.confirmDeliverbill.loadGaprepId == '') {
											Ext.ux.Toast.msg(predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.tip'),
													predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.tasknotLoading'), 'error',
													4000);
											return;
										}

										Ext.Msg
												.confirm(
														predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.tip'),
														predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.sureReturnLoadReport'),
														function(btn, text) {
															if (btn == "yes") {
																Ext.Ajax
																		.request({
																			url : predeliver
																					.realPath('rejectLoadTask.action'),
																			params : {
																				'deliverbillVo.deliverbillDto.deliverbillNo' : predeliver.confirmDeliverbill.deliverbillInfoForm
																						.getRecord()
																						.get('deliverbillNo')
																			},
																			success : function(
																					response) {
																				rejectBtn
																						.disable();

																				var result = Ext
																						.decode(response.responseText);
																				Ext.ux.Toast
																						.msg(
																								predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.tip'),
																								result.message);

																				// 关闭TAB
																				Ext
																						.getCmp(
																								'T_predeliver-confirmDeliverbillIndex')
																						.close();

																				// 如果派送单(新)列表TAB处于打开状态中，则刷新派送单列表
																				if (predeliver.confirmDeliverbill.createType=='Y') {
																					if (typeof (predeliver.deliverbillnew.deliverbillNewGrid) != "undefined"
																						&& predeliver.deliverbillnew.deliverbillNewGrid != null) {
																					predeliver.deliverbillnew.deliverbillNewGrid.store
																							.load();
																					}
																				}else{
																					// 如果派送单列表TAB处于打开状态中，则刷新派送单列表
																					if (typeof (predeliver.deliverbill.deliverbillGrid) != "undefined"
																							&& predeliver.deliverbill.deliverbillGrid != null) {
																						predeliver.deliverbill.deliverbillGrid.store
																								.load();
																					}
																				}
																			},
																			error : function(
																					response) {
																				var result = Ext
																						.decode(response.responseText);
																				Ext.ux.Toast
																						.msg(
																								predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.tip'),
																								result.message,
																								'error',
																								4000);
																			},
																			exception : function(
																					response) {
																				var result = Ext
																						.decode(response.responseText);
																				Ext.ux.Toast
																						.msg(
																								predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.tip'),
																								result.message,
																								'error',
																								4000);
																			}
																		});
															}
														});

									}
								} ]
					} ],
					getLoadGaprepGrid : function() {
						if (this.loadGaprepGrid == null) {
							this.loadGaprepGrid = Ext
									.create('Foss.predeliver.confirmDeliverbill.LoadGaprepGrid');
						}

						return this.loadGaprepGrid;
					},
					loadGaprepForm : null,
					getLoadGaprepForm : function() {
						if (this.loadGaprepForm == null) {
							this.loadGaprepForm = Ext
									.create('Foss.predeliver.confirmDeliverbill.LoadGaprepForm');
						}

						return this.loadGaprepForm;
					},
					loadTaskDetailGrid : null,
					getLoadTaskDetailGrid : function() {
						if (this.loadTaskDetailGrid == null) {
							this.loadTaskDetailGrid = Ext
									.create('Foss.predeliver.confirmDeliverbill.LoadTaskDetailGrid');
						}

						return this.loadTaskDetailGrid;
					},
					constructor : function(config) {
						var me = this, cfg = Ext.apply({}, config);
						me.items = [ me.getLoadGaprepGrid(),
								me.getLoadGaprepForm(),
								me.getLoadTaskDetailGrid() ];
						me.callParent([ cfg ]);
					}
				});

// 装车差异报告少货明细Store
Ext.define('Foss.predeliver.confirmDeliverbill.LoadGaprepDetailStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.predeliver.confirmDeliverbill.LoadGaprepDetailModel',
	autoLoad : false,
	// 差异报告ID
	loadGaprepId : '',
	setLoadGaprepId : function(loadGaprepId) {
		this.loadGaprepId = loadGaprepId;
	},
	getLoadGaprepId : function() {
		return this.loadGaprepId;
	},
	// 运单号
	waybillNo : '',
	setWaybillNo : function(waybillNo) {
		this.waybillNo = waybillNo;
	},
	getWaybillNo : function() {
		return this.waybillNo;
	},
	proxy : {
		type : 'ajax',
		actionMethods : 'POST',
		url : predeliver.realPath('queryLoadGaprepDetailList.action'),
		// 定义一个读取器
		reader : {
			// 以JSON的方式读取
			type : 'json',
			// 定义读取JSON数据的根对象
			root : 'deliverbillVo.loadGaprepDetailList'
		}
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	},
	listeners : {
		beforeload : function(store, operation, eOpts) {
			Ext.apply(operation, {
				params : {
					'deliverbillVo.loadTaskDto.loadGaprepId' : store
							.getLoadGaprepId(),
					'deliverbillVo.waybillNo' : store.getWaybillNo()
				}
			});
		}
	}
});

// 装车差异报告少货明细GRID
Ext
		.define(
				'Foss.predeliver.confirmDeliverbill.LoadGaprepDetailGrid',
				{
					extend : 'Ext.grid.Panel',
					title : predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.lackDetail'),//差异明细,
					emptyText : predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.emptyText'),//查询结果为空,
					frame : true,
					bodyCls : 'autoHeight',
					cls : 'autoHeight',
					autoScroll : true,
					collapsible : true,
					animCollapse : true,
					columns : [ {
						text : predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.serialNo'),//流水号,
						dataIndex : 'serialNo',
						flex:1
					}, {
						text : predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.scanState'),//扫描状态,
						dataIndex : 'scanState',
						flex:1
					}, {
						text : predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.scanTime'),//扫描时间,
						dataIndex : 'scanTime',
						flex:2
					}, {
						text : predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.goodsState'),//货物状态,
						dataIndex : 'goodsState',
						flex:1
					} ],
					constructor : function(config) {
						var me = this, cfg = Ext.apply({}, config);
						me.store = Ext
								.create('Foss.predeliver.confirmDeliverbill.LoadGaprepDetailStore');
						me.callParent([ cfg ]);
					}
				});

// 扫描明细GRID
Ext.define('Foss.predeliver.confirmDeliverbill.ScanDetailGrid', {
	extend : 'Ext.grid.Panel',
	title : predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.scanDetail'),//扫描明细,
	frame : true,
	emptyText : predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.emptyText'),//查询结果为空,
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	autoScroll : true,
	collapsible : true,
	animCollapse : true,
	columns : [ {
		header : predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.serialNo'),//流水号,
		dataIndex : 'serialNo',
		flex : 1
	}, {
		header : predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.scanState'),//扫描状态,
		dataIndex : 'scanState',
		flex : 1,
		renderer : function(value) {
			switch (value) {
			case 'SCANED':
				return predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.scaned');
			case 'UNSCANED':
				return predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.unscaned');
			case 'BY_HAND':
				return predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.byhand');
			default:
				return value;
			}
		}
	}, {
		header : predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.goodsState'),//货物状态,
		dataIndex : 'goodsState',
		flex : 1,
		renderer : function(value) {
			switch (value) {
			case 'NORMAL':
				return predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.normal');
			case 'NOT_LOADING':
				return predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.notloading');
			case 'MORE_ENTRAINED':
				return predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.moreentrained');
			case 'MORE_ALLOPATRY_ENTRAINED':
				return predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.moreallopatryentrained');
			case 'EXTRA':
				return predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.extra');
			case 'EXTRA_ENTRAINED':
				return predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.extraentrained');
			case 'EXTRA_ALLOPATRY_ENTAINED':
				return predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.extraallopatryentained');
			case 'EXTRA_UNPACKAGE':
				return predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.extraunpackage');
			case 'EXTRA_MODIFY':
				return predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.extramodify');
			case 'EXTRA_PACKAGE_UNOUT_STORAGE':
				return predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.extrapackageunoutstorage');
			case 'EXTRA_VALUABLES_UNOUT_STORAGE':
				return predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.extravaluablesunoutstorage');
			case 'EXTRA_UNPREWIRED':
				return predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.extraunprewired');
			case 'LACK':
				return predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.lack');
			default:
				return value;
			}
		}
	}, {
		header : predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.beLoaded'),//是否装车,
		dataIndex : 'beLoaded',
		flex : 1,
		renderer : function(value) {
			switch (value) {
			case 'N':
				return predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.not');
			case 'Y':
				return predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.yes');
			default:
				return value;
			}
		}
	}, {
		header : predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.loadTime'),//操作时间,
		dataIndex : 'loadTime',
		flex : 1.8
	} ],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext
				.create('Foss.predeliver.confirmDeliverbill.ScanDetailStore');
		me.callParent([ cfg ]);
	}
});

// 装车任务扫描明细窗口
Ext
		.define(
				'Foss.predeliver.confirmDeliverbill.ScanDetailWindow',
				{
					extend : 'Ext.window.Window',
					// title : predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.queryGoodsInfo'),//货物信息查询,
					modal : true,
					closeAction : 'hide',
					width : 1000,
					// 装车任务扫描明细GRID
					scanDetailGrid : null,
					getScanDetailGrid : function() {
						if (this.scanDetailGrid == null) {
							this.scanDetailGrid = Ext
									.create('Foss.predeliver.confirmDeliverbill.ScanDetailGrid');
						}
						return this.scanDetailGrid;
					},
					bindData : function(record, cellIndex, rowIndex) {
						// 设置装车任务明细ID
						this.getScanDetailGrid().store
								.setLoadTaskDetailId(record.get('id'));
					},
					listeners : {
						// 将重载数据的方法从bindData中移至beforeshow事件中，减少数据库交互
						beforeshow : function(cmp, eOpts) {
							this.getScanDetailGrid().store.load();
						}
					},
					constructor : function(config) {
						var me = this, cfg = Ext.apply({}, config);
						me.items = [ me.getScanDetailGrid() ];
						me.callParent([ cfg ]);
					}
				});

// 装车差异报告少货明细窗口
Ext
		.define(
				'Foss.predeliver.confirmDeliverbill.LoadGaprepDetailWindow',
				{
					extend : 'Ext.window.Window',
					// title : predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.queryGoodsInfo'),//货物信息查询,
					modal : true,
					closeAction : 'hide',
					width : 1000,
					// 少货明细GRID
					loadGaprepDetailGrid : null,
					getLoadGaprepDetailGrid : function() {
						if (this.loadGaprepDetailGrid == null) {
							this.loadGaprepDetailGrid = Ext
									.create('Foss.predeliver.confirmDeliverbill.LoadGaprepDetailGrid');
						}
						return this.loadGaprepDetailGrid;
					},
					bindData : function(record, cellIndex, rowIndex) {
						// 设置差异报告ID和运单号
						this.getLoadGaprepDetailGrid().store
								.setLoadGaprepId(predeliver.confirmDeliverbill.loadGaprepId);
						this.getLoadGaprepDetailGrid().store
								.setWaybillNo(record.get('wayBillNo'));
					},
					listeners : {
						// 将重载数据的方法从bindData中移至beforeshow事件中，减少数据库交互
						beforeshow : function(cmp, eOpts) {
							this.getLoadGaprepDetailGrid().store.load();
						}
					},
					constructor : function(config) {
						var me = this, cfg = Ext.apply({}, config);
						me.items = [ me.getLoadGaprepDetailGrid() ];
						me.callParent([ cfg ]);
					}
				});

// 装车人Model
Ext.define('Foss.predeliver.confirmDeliverbill.LoaderModel', {
	extend : 'Ext.data.Model',
	fields : [ {
		name : 'loaderCode',
		type : 'string'
	}, {
		name : 'loaderName',
		type : 'string'
	}, {
		name : 'joinTime',
		type : 'string',
		convert : function(value) {
			if (value != null) {
				var date = new Date(value);
				return Ext.Date.format(date, 'Y-m-d H:i:s');
			} else {
				return null;
			}
		}
	}, {
		name : 'leaveTime',
		type : 'string',
		convert : function(value) {
			if (value != null) {
				var date = new Date(value);
				return Ext.Date.format(date, 'Y-m-d H:i:s');
			} else {
				return null;
			}
		}
	} ]
});

// 装车人Store
Ext
		.define(
				'Foss.predeliver.confirmDeliverbill.LoaderStore',
				{
					extend : 'Ext.data.Store',
					model : 'Foss.predeliver.confirmDeliverbill.LoaderModel',
					autoLoad : true,
					proxy : {
						type : 'ajax',
						actionMethods : 'POST',
						url : predeliver.realPath('queryLoaderList.action'),
						// 定义一个读取器
						reader : {
							// 以JSON的方式读取
							type : 'json',
							// 定义读取JSON数据的根对象
							root : 'deliverbillVo.loaderList'
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
													'deliverbillVo.loadTaskDto.taskId' : predeliver.confirmDeliverbill.taskId
												}
											});
						}
					}
				});

// 装车人GRID
Ext
		.define(
				'Foss.predeliver.confirmDeliverbill.LoaderGrid',
				{
					extend : 'Ext.grid.Panel',
					title : predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.queryLoadingPeople'),//查看装车人,
					frame : true,
					emptyText : predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.emptyText'),//查询结果为空,
					bodyCls : 'autoHeight',
					cls : 'autoHeight',
					autoScroll : true,
					collapsible : true,
					animCollapse : true,
					columns : [ {
						header : predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.loaderCode'),//工号,
						dataIndex : 'loaderCode',
						flex : 1
					}, {
						header : predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.loaderName'),//姓名,
						dataIndex : 'loaderName',
						flex : 1
					}, {
						header : predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.joinTime'),//加入时间,
						dataIndex : 'joinTime',
						flex : 1.8
					}, {
						header : predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.leaveTime'),//退出时间,
						dataIndex : 'leaveTime',
						flex : 1.8
					} ],
					constructor : function(config) {
						var me = this, cfg = Ext.apply({}, config);
						me.store = Ext
								.create('Foss.predeliver.confirmDeliverbill.LoaderStore');
						me.callParent([ cfg ]);
					}
				});

// 装车人窗口
Ext.define('Foss.predeliver.confirmDeliverbill.LoaderWindow', {
	extend : 'Ext.window.Window',
	modal : true,
	closeAction : 'hide',
	width : 1000,
	// 装车人GRID
	loaderGrid : null,
	getLoaderGrid : function() {
		if (this.loaderGrid == null) {
			this.loaderGrid = Ext
					.create('Foss.predeliver.confirmDeliverbill.LoaderGrid');
		}
		return this.loaderGrid;
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.items = [ me.getLoaderGrid() ];
		me.callParent([ cfg ]);
	}
});

// 历史差异报告Model
Ext.define('Foss.predeliver.confirmDeliverbill.HistoryLoadGaprepModel', {
	extend : 'Ext.data.Model',
	fields : [ {
		name : 'id',
		type : 'string'
	}, {
		name : 'taskNo',
		type : 'string'
	}, {
		name : 'reportNo',
		type : 'string'
	}, {
		name : 'deliverBillNo',
		type : 'string'
	}, {
		name : 'createTime',
		type : 'string'
	}, {
		name : 'state',
		type : 'string',
		convert : function(value) {
			if (value == 'GENERATED') {
				return predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.generated');//已生成
			} else if (value == 'BACK') {
				return predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.back');//已打回
			} else if (value == 'AFFIRM') {
				return predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.affirm');//已确认
			}
		}
	}, {
		name : 'planLoadGoodsQty',
		type : 'int'
	}, {
		name : 'actualLoadGoodsQty',
		type : 'int'
	}, {
		name : 'lackGoodsQty',
		type : 'int'
	}, {
		name : 'vehicleNo',
		type : 'string'
	}, {
		name : 'beValid',
		type : 'string'
	} ]
});

// 历史差异报告Store
Ext
		.define(
				'Foss.predeliver.confirmDeliverbill.HistoryLoadGaprepStore',
				{
					extend : 'Ext.data.Store',
					model : 'Foss.predeliver.confirmDeliverbill.HistoryLoadGaprepModel',
					autoLoad : true,
					proxy : {
						type : 'ajax',
						actionMethods : 'POST',
						url : predeliver.realPath('queryLoadGaprepList.action'),
						// 定义一个读取器
						reader : {
							// 以JSON的方式读取
							type : 'json',
							// 定义读取JSON数据的根对象
							root : 'deliverbillVo.loadGaprepList'
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
													'deliverbillVo.loadTaskDto.taskId' : predeliver.confirmDeliverbill.taskId,
													'deliverbillVo.deliverbillDto.deliverbillNo' : predeliver.confirmDeliverbill.deliverbillInfoForm
															.getRecord()
															.get('deliverbillNo')
												}
											});
						}
					}
				});

// 历史差异报告GRID
Ext
		.define(
				'Foss.predeliver.confirmDeliverbill.HistoryLoadGaprepGrid',
				{
					extend : 'Ext.grid.Panel',
					title : predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.hisLoadReport'),//历史装车差异报告,
					emptyText : predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.emptyText'),//查询结果为空,
					frame : true,
					emptyText : predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.emptyText'),//查询结果为空,
					bodyCls : 'autoHeight',
					cls : 'autoHeight',
					autoScroll : true,
					collapsible : true,
					animCollapse : true,
					columns : [ {
						text : predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.reportNo'),//差异报告编号,
						dataIndex : 'reportNo',
						flex : 0.4
					}, {
						text : predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.taskNo'),//装车任务编号,
						dataIndex : 'taskNo',
						flex : 0.4
					}, {
						text : predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.deliverbillNo'),//派送单号,
						dataIndex : 'reportNo',
						flex : 0.4
					}, {
						text : predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.state'),//差异报告状态,
						dataIndex : 'state',
						flex : 0.4
					}, {
						text : predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.planLoadQtyTotal'),//预计装车总件数,
						dataIndex : 'planLoadGoodsQty',
						flex : 0.4
					}, {
						text : predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.actualLoadGoodsQty'),//实际装车总件数,
						dataIndex : 'actualLoadGoodsQty',
						flex : 0.4
					}, {
						text : predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.lackGoodsQtyTotal'),//少货总件数,
						dataIndex : 'lackGoodsQty',
						flex : 0.4
					}, {
						text : predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.vehicleNo'),//车牌号,
						dataIndex : 'vehicleNo',
						flex : 0.3
					}, {
						text : predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.createTime'),//报告生成时间,
						dataIndex : 'createTime',
						flex : 0.5
					} ],
					constructor : function(config) {
						var me = this, cfg = Ext.apply({}, config);
						me.store = Ext
								.create('Foss.predeliver.confirmDeliverbill.HistoryLoadGaprepStore');
						me.callParent([ cfg ]);
					}
				});

// 历史差异报告窗口
Ext
		.define(
				'Foss.predeliver.confirmDeliverbill.HistoryLoadGaprepWindow',
				{
					extend : 'Ext.window.Window',
					modal : true,
					closeAction : 'hide',
					width : 1000,
					// 历史差异报告GRID
					historyLoadGaprepGrid : null,
					getHistoryLoadGaprepGrid : function() {
						if (this.historyLoadGaprepGrid == null) {
							this.historyLoadGaprepGrid = Ext
									.create('Foss.predeliver.confirmDeliverbill.HistoryLoadGaprepGrid');
						}
						return this.historyLoadGaprepGrid;
					},
					constructor : function(config) {
						var me = this, cfg = Ext.apply({}, config);
						me.items = [ me.getHistoryLoadGaprepGrid() ];
						me.callParent([ cfg ]);
					}
				});

// 确认/取消派送排单form
Ext
		.define(
				'Foss.predeliver.confirmDeliverbill.SubmitForm',
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
					// 确认排单前验证
					validateOnConfirm : function() {
						//SUC缺少对于排单确认条件的描述
						return true;
					},
					// 取消排单前验证
					validateOnReject : function() {
						// 预派送单状态为“装车中”，系统提示“装车中不允许取消预派送单！”
						return true;
					},
					// 库存件数不一致&到达联件数不一致提示页面
					queryQtyInconsistent : function() {
						Ext.Ajax
								.request({
									url : predeliver
											.realPath('queryQtyInconsistentDeliverbillDetailList.action'),
									params : {
										'deliverbillVo.deliverbillDto.id' : predeliver.confirmDeliverbill.deliverbillId
									},
									success : function(response) {
										var result = Ext
												.decode(response.responseText);

										// 获取到达联件数与排单件数不一致列表
										var arrivesheetQtyInconsistentDeliverbillDetailList = result.deliverbillVo.deliverbillDetailDtoList;
										// 获取库存件数与排单件数不一致列表
										var inStockQtyInconsistentDeliverbillDetailList = result.deliverbillVo.qtyInconsistentDeliverbillDetailList;

										if (arrivesheetQtyInconsistentDeliverbillDetailList.length == 0
												&& inStockQtyInconsistentDeliverbillDetailList.length == 0) {
											// 确认派送单
											Ext
													.getCmp(
															'T_predeliver-confirmDeliverbillIndex_content')
													.confirmDeliverbill();
										} else {
											//
											var qtyInconsistentDeliverbillDetailWindow = Ext
													.getCmp(
															'T_predeliver-confirmDeliverbillIndex_content')
													.getQtyInconsistentDeliverbillDetailWindow();

											qtyInconsistentDeliverbillDetailWindow
													.getArrivesheetQtyInconsistentDeliverbillDetailGrid()
													.getStore()
													.loadData(
															arrivesheetQtyInconsistentDeliverbillDetailList);

											qtyInconsistentDeliverbillDetailWindow
													.getInStockQtyInconsistentDeliverbillDetailGrid()
													.getStore()
													.loadData(
															inStockQtyInconsistentDeliverbillDetailList);

											qtyInconsistentDeliverbillDetailWindow
													.show();
										}
									},
									error : function(response) {
										var result = Ext
												.decode(response.responseText);
										Ext.ux.Toast.msg(predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.tip'),
												result.message, 'error', 4000);
									},
									exception : function(response) {
										var result = Ext
												.decode(response.responseText);
										Ext.ux.Toast.msg(predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.tip'),
												result.message, 'error', 4000);
									}
								});
					},
					// 查询未通知客户列表
					queryUnnotifiedDeliverbillDetailList : function() {
						// 扩展事件 4c
						// 派单信息中存在运单未通知客户，则系统弹出提示框
						Ext.Ajax
								.request({
									url : predeliver
											.realPath('queryUnnotifiedDeliverbillDetailList.action'),
									params : {
										'deliverbillVo.deliverbillDto.id' : predeliver.confirmDeliverbill.deliverbillId
									},
									success : function(response) {
										var result = Ext
												.decode(response.responseText);

										// 获取未通知客户列表
										var deliverbillDetailDtoList = result.deliverbillVo.deliverbillDetailDtoList;
										if (deliverbillDetailDtoList.length > 0) {
											var unnotifiedDeliverbillDetailWindow = Ext
													.getCmp(
															'T_predeliver-confirmDeliverbillIndex_content')
													.getUnnotifiedDeliverbillDetailWindow();

											unnotifiedDeliverbillDetailWindow
													.getUnnotifiedDeliverbillDetailGrid()
													.getStore()
													.loadData(
															deliverbillDetailDtoList);

											unnotifiedDeliverbillDetailWindow
													.show();
										} else {
											// 确认派送单
											Ext
													.getCmp(
															'T_predeliver-confirmDeliverbillIndex_content')
													.confirmDeliverbill();
										}
									},
									error : function(response) {
										var result = Ext
												.decode(response.responseText);
										Ext.ux.Toast.msg(predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.tip'),
												result.message, 'error', 4000);
									},
									exception : function(response) {
										var result = Ext
												.decode(response.responseText);
										Ext.ux.Toast.msg(predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.tip'),
												result.message, 'error', 4000);
									}
								});
					},
					// 车辆装载率校验
					validateLoadRate : function() {
						var hasConfirmed = false;

						// SR-2
						// 车辆实际重量装载率或车辆实际体积装载率（当一车多个派送单情况时，以车辆总的重量装载率和体积装载率为准）没有达到60%，
						// 系统提示“重量装载率未达到60%，是否确认预派送单？”或“体积装载率未达到60%，是否确认预派送单？”，60%提供参数配置，可部门设置。
						var deliverbillInfo = predeliver.confirmDeliverbill.deliverbillInfoForm
								.getRecord();

						// 计算装载率
						var vehicleLoadRate = 0;
						if(deliverbillInfo.get('vehicleLoad')==0||deliverbillInfo.get('vehicleLoad')==null)
						{
							vehicleLoadRate = 0.00;
						}else
						{
							vehicleLoadRate = deliverbillInfo
							.get('weightTotal')
							/ deliverbillInfo.get('vehicleLoad');
						}	
						
						var vehicleVolumeRate = 0;
						if(deliverbillInfo.get('vehicleVolume')==0||deliverbillInfo.get('vehicleVolume')==null)
						{
							vehicleVolumeRate = 0.00;
						}else
						{
							vehicleVolumeRate = deliverbillInfo
							.get('volumeTotal')
							/ deliverbillInfo.get('vehicleVolume');
						}	

						if (vehicleLoadRate < predeliver.confirmDeliverbill.weightLowerThreshold) {
							Ext.Msg
									.confirm(
											predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.tip'),
											predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.weightNotStandards')
													+ predeliver.confirmDeliverbill.weightLowerThreshold
													* 100 + predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.sureDeliverBill'),
											function(btn, text) {
												if (btn == "no") {
													return;
												} else if (vehicleVolumeRate < predeliver.confirmDeliverbill.volumeLowerThreshold) {
													Ext.Msg
															.confirm(
																	predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.tip'),
																	predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.volumeNotStandards')
																			+ predeliver.confirmDeliverbill.volumeLowerThreshold
																			* 100
																			+ predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.sureDeliverBill'),
																	function(
																			btn,
																			text) {
																		if (btn == "no") {
																			return;
																		} else {
																			// 库存件数不一致&到达联件数不一致提示页面
																			predeliver.confirmDeliverbill.submitForm
																					.queryQtyInconsistent();
																		}
																	});
												} else {
													// 库存件数不一致&到达联件数不一致提示页面
													predeliver.confirmDeliverbill.submitForm
															.queryQtyInconsistent();
												}
											});
						} else if (vehicleVolumeRate < predeliver.confirmDeliverbill.volumeLowerThreshold) {
							Ext.Msg
									.confirm(
											predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.tip'),
											predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.volumeNotStandards')
													+ predeliver.confirmDeliverbill.volumeLowerThreshold
													* 100 + predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.sureDeliverBill'),
											function(btn, text) {
												if (btn == "no") {
													return;
												} else {
													// 库存件数不一致&到达联件数不一致提示页面
													predeliver.confirmDeliverbill.submitForm
															.queryQtyInconsistent();
												}
											});
						} else {
							Ext.Msg.confirm(predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.tip'), predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.confirmdDeliverbill'), function(btn,
									text) {
								if (btn == "yes") {
									// 库存件数不一致&到达联件数不一致提示页面
									predeliver.confirmDeliverbill.submitForm
											.queryQtyInconsistent();
								}
							});
						}
					},
					/*
					 * items : [ { border : false, xtype : 'container',
					 * columnWidth : 1, layout : 'column', defaults : { margin :
					 * '0 0 0 0' },
					 */
					items : [
							{
								xtype : 'textfield',
								name : 'driverCode',
								hidden : true
							},
							{
								xtype : 'textfield',
								border : false,
								columnWidth : .20,
								labelWidth : 50,
								fieldLabel : predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.drivers'),//司机,
								name : 'driverName',
								listeners : {
									focus : function(me, eventObj, eOpts) {
										var selectDriverWindow = Ext
												.getCmp(
														'T_predeliver-confirmDeliverbillIndex_content')
												.getSelectDriverWindow();

										selectDriverWindow
												.setDeliverbillId(predeliver.confirmDeliverbill.deliverbillId);

										selectDriverWindow.show();
									}
								}
							},
							{
								border : false,
								columnWidth : .56,
								html : '&nbsp;'
							},
							{
								columnWidth : .14,
								xtype : 'button',
								cls : 'yellow_button',
								text : predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.confirmDeliverbill'),//确认派送单,
                plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin', {
                  seconds: 6
                }),
								handler : function(button, e) {
									/*
									 * if
									 * (predeliver.confirmDeliverbill.loadGaprepId ==
									 * '') { Ext.ux.Toast.msg('温馨提示',
									 * '该派送任务未装车，不能确认派送', 'error', 4000);
									 * return; }
									 */
									if(predeliver.confirmDeliverbill.submitForm.getForm().findField('driverName').value==null
									 ||predeliver.confirmDeliverbill.submitForm.getForm().findField('driverName').value==""){
										Ext.ux.Toast.msg(predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.tip'), predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.choseOneDriver'), 'error', 3000);
										return;
									}

									// 车辆装载率校验
									predeliver.confirmDeliverbill.submitForm
											.validateLoadRate();
								}
							},
							{
								columnWidth : .14,
								xtype : 'button',
								cls : 'yellow_button',
								text : predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.cancellDeliver'),//取消派送单,
								handler : function(button, e) {
									Ext.Msg
											.confirm(
													predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.tip'),
													predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.confirmdCancellDeliverbill'),
													function(btn, text) {
														if (btn == "yes") {
															Ext.Ajax
																	.request({
																		url : predeliver
																				.realPath('cancelDeliverbill.action'),
																		params : {
																			'deliverbillVo.deliverbillDto.id' : predeliver.confirmDeliverbill.deliverbillId,
																			'deliverbillVo.deliverbillDto.deliverbillNo' : predeliver.confirmDeliverbill.deliverbillInfoForm
																					.getRecord()
																					.get('deliverbillNo')
																		},
																		success : function(
																				response) {
																			var result = Ext
																					.decode(response.responseText);
																			Ext.ux.Toast
																					.msg(
																							predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.tip'),
																							result.message);

																			// 关闭TAB
																			Ext
																					.getCmp(
																							'T_predeliver-confirmDeliverbillIndex')
																					.close();

																			// 如果派送单(新)列表TAB处于打开状态中，则刷新派送单列表
																			if (predeliver.confirmDeliverbill.createType=='Y') {
																				if (typeof (predeliver.deliverbillnew.deliverbillNewGrid) != "undefined"
																					&& predeliver.deliverbillnew.deliverbillNewGrid != null) {
																				predeliver.deliverbillnew.deliverbillNewGrid.store
																						.load();
																				}
																			}else{
																				// 如果派送单列表TAB处于打开状态中，则刷新派送单列表
																				if (typeof (predeliver.deliverbill.deliverbillGrid) != "undefined"
																						&& predeliver.deliverbill.deliverbillGrid != null) {
																					predeliver.deliverbill.deliverbillGrid.store
																							.load();
																				}
																			}
																		},
																		error : function(
																				response) {
																			var result = Ext
																					.decode(response.responseText);
																			Ext.ux.Toast
																					.msg(
																							predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.tip'),
																							result.message,
																							'error',
																							4000);
																		},
																		exception : function(
																				response) {
																			var result = Ext
																					.decode(response.responseText);
																			Ext.ux.Toast
																					.msg(
																							predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.tip'),
																							result.message,
																							'error',
																							4000);
																		}
																	});
														}
													});
								}
							} ]
				// } ]
				});

// 查询司机form
Ext
		.define(
				'Foss.predeliver.confirmDeliverbill.QueryDriverForm',
				{
					title : predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.queryCondition'),//查询条件,
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
								fieldLabel : predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.loaderCode'),//工号 ,
								columnWidth : .33
							},
							{
								name : 'empName',
								fieldLabel : predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.loaderName'),//姓名,
								columnWidth : .33
							},
							{
								name : 'empPhone',
								fieldLabel : predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.empPhone'),//电话号码,
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
											text : predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.reset'),//重置,  
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
											text : predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.queryButton'),//查询,
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
Ext.define('Foss.predeliver.confirmDeliverbill.DriverModel', {
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
				'Foss.predeliver.confirmDeliverbill.DriverStore',
				{
					extend : 'Ext.data.Store',
					autoLoad : false,
					// 绑定一个模型
					model : 'Foss.predeliver.confirmDeliverbill.DriverModel',
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
							var queryDriverForm = Ext
									.getCmp(
											'T_predeliver-confirmDeliverbillIndex_content')
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
Ext
		.define(
				'Foss.predeliver.confirmDeliverbill.DriverGrid',
				{
					extend : 'Ext.grid.Panel',
					frame : true,
					title : predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.comDrivers'),//公司司机,
					emptyText : predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.emptyText'),//查询结果为空,
					autoScroll : true,
					height : 450,
					collapsible : true,
					animCollapse : true,
					columns : [ {
						dataIndex : 'empCode',
						align : 'center',
						flex : 1,
						header : predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.loaderCode')//工号
					}, {
						dataIndex : 'empName',
						align : 'center',
						flex : 1,
						header : predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.loaderName')//姓名
					}, {
						dataIndex : 'orgId',
						align : 'center',
						flex : 1,
						header : predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.orgId')//部门
					}, {
						dataIndex : 'empPhone',
						align : 'center',
						flex : 1,
						header : predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.empPhone')//电话号码
					} ],
					viewConfig : {
						enableTextSelection : true
					},
					constructor : function(config) {
						var me = this, cfg = Ext.apply({}, config);
						me.store = Ext
								.create('Foss.predeliver.confirmDeliverbill.DriverStore');
						me.selModel = Ext.create('Ext.selection.RadioModel', {
							showHeaderCheckbox : false
						});
						me.callParent([ cfg ]);
					}
				});

// 确认司机form
Ext
		.define(
				'Foss.predeliver.confirmDeliverbill.ConfirmDriverForm',
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
									text : predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.confirm'),//确认,
									handler : function() {
										var selectRow = predeliver.confirmDeliverbill.selectDriverWindow
												.getDriverGrid()
												.getSelectionModel()
												.getSelection();

										if (selectRow.length == 0) {
											Ext.ux.Toast.msg(predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.tip'), predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.choseDriver'),
													'error', 4000);
											return;
										}
										;
										var driverName = selectRow[0]
												.get('empName');
										Ext.Msg
												.confirm(
														predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.tip'),
														predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.confirmAllotDriver')
																+ driverName
																+ '?',
														function(btn) {
															if (btn == 'yes') {
																// 将选中的司机填入司机textfield中
																predeliver.confirmDeliverbill.submitForm
																		.getForm()
																		.findField(
																				'driverCode')
																		.setValue(
																				selectRow[0]
																						.get('empCode'));

																predeliver.confirmDeliverbill.submitForm
																		.getForm()
																		.findField(
																				'driverName')
																		.setValue(
																				selectRow[0]
																						.get('empName'));

																predeliver.confirmDeliverbill.selectDriverWindow
																		.close();
															}
														});
									}
								} ]
					} ]
				});

// 分配派送任务(司机)窗口
Ext
		.define(
				'Foss.predeliver.confirmDeliverbill.SelectDriverWindow',
				{
					extend : 'Ext.window.Window',

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
									.create('Foss.predeliver.confirmDeliverbill.QueryDriverForm');
						}
						return this.queryDriverForm;
					},
					// 公司司机信息GRID
					driverGrid : null,
					getDriverGrid : function() {
						if (this.driverGrid == null) {
							this.driverGrid = Ext
									.create('Foss.predeliver.confirmDeliverbill.DriverGrid');
						}
						return this.driverGrid;
					},
					// 确认司机FORM
					confirmDriverForm : null,
					getConfirmDriverForm : function() {
						if (this.confirmDriverForm == null) {
							this.confirmDriverForm = Ext
									.create('Foss.predeliver.confirmDeliverbill.ConfirmDriverForm');
						}
						return this.confirmDriverForm;
					},
					constructor : function(config) {
						var me = this, cfg = Ext.apply({}, config);
						me.items = [ me.getQueryDriverForm(),
								me.getDriverGrid(), me.getConfirmDriverForm() ];
						me.callParent([ cfg ]);
					}
				});

// 定义未通知客户Model
Ext.define(
		'Foss.predeliver.confirmDeliverbill.UnnotifiedDeliverbillDetailModel',
		{
			extend : 'Ext.data.Model',
			fields : [ {
				name : 'waybillNo',
				type : 'string'
			}, {
				name : 'consignee',
				type : 'string'
			}, {
				name : 'consigneeContact',
				type : 'string'
			}, {
				name : 'payAmount',
				type : 'number'
			} ]
		});

// 定义未通知客户store
Ext
		.define(
				'Foss.predeliver.confirmDeliverbill.UnnotifiedDeliverbillDetailStore',
				{
					extend : 'Ext.data.Store',
					model : 'Foss.predeliver.confirmDeliverbill.UnnotifiedDeliverbillDetailModel',
					data : {
						'items' : []
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

// 未通知客户GRID
Ext
		.define(
				'Foss.predeliver.confirmDeliverbill.UnnotifiedDeliverbillDetailGrid',
				{
					extend : 'Ext.grid.Panel',
					frame : true,
					title : predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.noticeCustomer'),//通知客户,
					emptyText : predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.emptyText'),//查询结果为空,
					autoScroll : true,
					bodyCls : 'autoHeight',
					cls : 'autoHeight',
					collapsible : true,
					animCollapse : true,
					columns : [ {
						dataIndex : 'waybillNo',
						align : 'center',
						flex : 1,
						header : predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.waybillNo')//运单号
					}, {
						dataIndex : 'consignee',
						align : 'center',
						flex : 1,
						header : predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.consignee')//收货人
					}, {
						dataIndex : 'consigneeContact',
						hidden : true
					}, {
						dataIndex : 'payAmount',
						hidden : true
					} ],
					constructor : function(config) {
						var me = this, cfg = Ext.apply({}, config);
						me.store = Ext
								.create('Foss.predeliver.confirmDeliverbill.UnnotifiedDeliverbillDetailStore');
						me.callParent([ cfg ]);
					}
				});

// 确认通知客户form
Ext
		.define(
				'Foss.predeliver.confirmDeliverbill.ConfirmNotificationForm',
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
									columnWidth : .26,
									html : '&nbsp;'
								},
								{
									columnWidth : .18,
									xtype : 'button',
									cls : 'yellow_button',
									text : predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.confirm'),//确认,
									handler : function() {
										// 关闭窗口
										predeliver.confirmDeliverbill.unnotifiedDeliverbillDetailWindow
												.close();

										var deliverbillDetailDtoArray = [];
										var i = 0;

										predeliver.confirmDeliverbill.unnotifiedDeliverbillDetailWindow
												.getUnnotifiedDeliverbillDetailGrid().store
												.each(function(
														deliverbillDetailDto) {
													deliverbillDetailDtoArray[i] = deliverbillDetailDto.data;
													i++;
													return true;
												});

										/*
										 * var deliverbillDetailDtoListJsonData =
										 * Ext.encode(deliverbillDetailDtoArray);
										 * alert("deliverbillDetailDtoListJsonData : " +
										 * deliverbillDetailDtoListJsonData);
										 */

										var deliverbillDetailDtoListJsonData = {
											'deliverbillVo' : {
												'deliverbillDetailDtoList' : deliverbillDetailDtoArray
											}
										};

										// 通知客户
										Ext.Ajax
												.request({
													url : predeliver
															.realPath('notifyUnnotifiedCustomer.action'),
													jsonData : deliverbillDetailDtoListJsonData,
													success : function(response) {
														// 确认派送单
														Ext
																.getCmp(
																		'T_predeliver-confirmDeliverbillIndex_content')
																.confirmDeliverbill();
													},
													error : function(response) {
														var result = Ext
																.decode(response.responseText);
														Ext.ux.Toast.msg(
																predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.tip'),
																result.message,
																'error', 4000);
													},
													exception : function(
															response) {
														var result = Ext
																.decode(response.responseText);
														Ext.ux.Toast.msg(
																predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.tip'),
																result.message,
																'error', 4000);
													}
												});
									}
								},
								{
									border : false,
									columnWidth : .10,
									html : '&nbsp;'
								},
								{
									columnWidth : .18,
									xtype : 'button',
									cls : 'yellow_button',
									text : predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.cancell'),//取消,
									handler : function() {
										// 关闭窗口
										predeliver.confirmDeliverbill.unnotifiedDeliverbillDetailWindow
												.close();

										Ext
												.getCmp(
														'T_predeliver-confirmDeliverbillIndex_content')
												.confirmDeliverbill();
									}
								}, {
									border : false,
									columnWidth : .26,
									html : '&nbsp;'
								} ]
					} ]
				});

// 通知客户窗口
Ext
		.define(
				'Foss.predeliver.confirmDeliverbill.UnnotifiedDeliverbillDetailWindow',
				{
					extend : 'Ext.window.Window',
					// title : predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.noticeCustomer'),//通知客户,
					bodyCls : 'autoHeight',
					cls : 'autoHeight',
					modal : true,
					closeAction : 'hide',
					width : 500,
					// 未通知客户GRID
					unnotifiedDeliverbillDetailGrid : null,
					getUnnotifiedDeliverbillDetailGrid : function() {
						if (this.unnotifiedDeliverbillDetailGrid == null) {
							this.unnotifiedDeliverbillDetailGrid = Ext
									.create('Foss.predeliver.confirmDeliverbill.UnnotifiedDeliverbillDetailGrid');
						}
						return this.unnotifiedDeliverbillDetailGrid;
					},
					// 确认通知FORM
					confirmNotificationForm : null,
					getConfirmNotificationForm : function() {
						if (this.confirmNotificationForm == null) {
							this.confirmNotificationForm = Ext
									.create('Foss.predeliver.confirmDeliverbill.ConfirmNotificationForm');
						}
						return this.confirmNotificationForm;
					},
					constructor : function(config) {
						var me = this, cfg = Ext.apply({}, config);
						me.items = [ me.getUnnotifiedDeliverbillDetailGrid(),
								me.getConfirmNotificationForm() ];
						me.callParent([ cfg ]);
					}
				});

// 到达联件数，库存件数与排单件数不一致Model
Ext
		.define(
				'Foss.predeliver.confirmDeliverbill.QtyInconsistentDeliverbillDetailModel',
				{
					extend : 'Ext.data.Model',
					fields : [ {
						name : 'waybillNo',
						type : 'string'
					}, {
						name : 'arrangeGoodsQty',
						type : 'int'
					}, {
						name : 'arriveSheetGoodsQty',
						type : 'int'
					}, {
						name : 'inStockGoodsQty',
						type : 'int'
					} ]
				});

// 到达联件数，库存件数与排单件数不一致store
Ext
		.define(
				'Foss.predeliver.confirmDeliverbill.QtyInconsistentDeliverbillDetailStore',
				{
					extend : 'Ext.data.Store',
					model : 'Foss.predeliver.confirmDeliverbill.QtyInconsistentDeliverbillDetailModel',
					data : {
						'items' : []
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

// 到达联件数与排单件数不一致GRID
Ext
		.define(
				'Foss.predeliver.confirmDeliverbill.ArrivesheetQtyInconsistentDeliverbillDetailGrid',
				{
					extend : 'Ext.grid.Panel',
					frame : true,
					title : predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.arrivesheetQtyError'),//到达联件数不一致,
					emptyText : predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.emptyText'),//查询结果为空,
					autoScroll : true,
					bodyCls : 'autoHeight',
					cls : 'autoHeight',
					collapsible : true,
					animCollapse : true,
					columns : [ {
						dataIndex : 'waybillNo',
						align : 'center',
						flex : 1,
						header : predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.waybillNo')//运单号
					}, {
						dataIndex : 'arrangeGoodsQty',
						align : 'center',
						flex : 1,
						header : predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.arrangeGoodsQty')//排单件数
					}, {
						dataIndex : 'arriveSheetGoodsQty',
						align : 'center',
						flex : 1,
						header : predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.arriveSheetGoodsQty')//到达联件数
					} ],
					constructor : function(config) {
						var me = this, cfg = Ext.apply({}, config);
						me.store = Ext
								.create('Foss.predeliver.confirmDeliverbill.QtyInconsistentDeliverbillDetailStore');
						me.callParent([ cfg ]);
					}
				});

// 库存件数与排单件数不一致GRID
Ext
		.define(
				'Foss.predeliver.confirmDeliverbill.InStockQtyInconsistentDeliverbillDetailGrid',
				{
					extend : 'Ext.grid.Panel',
					frame : true,
					title : predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.storeQtyError'),//库存件数不一致,
					emptyText : predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.emptyText'),//查询结果为空,
					autoScroll : true,
					bodyCls : 'autoHeight',
					cls : 'autoHeight',
					collapsible : true,
					animCollapse : true,
					columns : [ {
						dataIndex : 'waybillNo',
						align : 'center',
						flex : 1,
						header : predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.waybillNo')//运单号
					}, {
						dataIndex : 'arrangeGoodsQty',
						align : 'center',
						flex : 1,
						header : predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.arrangeGoodsQty')//排单件数
					}, {
						dataIndex : 'inStockGoodsQty',
						align : 'center',
						flex : 1,
						header : predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.stockQty')//库存件数
					} ],
					constructor : function(config) {
						var me = this, cfg = Ext.apply({}, config);
						me.store = Ext
								.create('Foss.predeliver.confirmDeliverbill.QtyInconsistentDeliverbillDetailStore');
						me.callParent([ cfg ]);
					}
				});

// 到达联件数，库存件数与排单件数不一致确认表单
Ext
		.define(
				'Foss.predeliver.confirmDeliverbill.ConfirmQtyInconsistentForm',
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
									columnWidth : .26,
									html : '&nbsp;'
								},
								{
									columnWidth : .18,
									xtype : 'button',
									cls : 'yellow_button',
									text : predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.confirm'),//确认,
									handler : function() {
										// 关闭窗口
										predeliver.confirmDeliverbill.qtyInconsistentDeliverbillDetailWindow
												.close();

										// 打开通知客户窗口
										predeliver.confirmDeliverbill.submitForm
												.queryUnnotifiedDeliverbillDetailList();
									}
								},
								{
									border : false,
									columnWidth : .10,
									html : '&nbsp;'
								},
								{
									columnWidth : .18,
									xtype : 'button',
									cls : 'yellow_button',
									text : predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.cancell'),//取消,
									handler : function() {
										// 关闭窗口
										predeliver.confirmDeliverbill.qtyInconsistentDeliverbillDetailWindow
												.close();
									}
								}, {
									border : false,
									columnWidth : .26,
									html : '&nbsp;'
								} ]
					} ]
				});

// 到达联件数，库存件数与排单件数不一致窗口
Ext
		.define(
				'Foss.predeliver.confirmDeliverbill.QtyInconsistentDeliverbillDetailWindow',
				{
					extend : 'Ext.window.Window',
					bodyCls : 'autoHeight',
					cls : 'autoHeight',
					modal : true,
					closeAction : 'hide',
					width : 500,
					// 到达联件数不一致GRID
					arrivesheetQtyInconsistentDeliverbillDetailGrid : null,
					getArrivesheetQtyInconsistentDeliverbillDetailGrid : function() {
						if (this.arrivesheetQtyInconsistentDeliverbillDetailGrid == null) {
							this.arrivesheetQtyInconsistentDeliverbillDetailGrid = Ext
									.create('Foss.predeliver.confirmDeliverbill.ArrivesheetQtyInconsistentDeliverbillDetailGrid');
						}
						return this.arrivesheetQtyInconsistentDeliverbillDetailGrid;
					},
					// 到达联件数不一致GRID
					inStockQtyInconsistentDeliverbillDetailGrid : null,
					getInStockQtyInconsistentDeliverbillDetailGrid : function() {
						if (this.inStockQtyInconsistentDeliverbillDetailGrid == null) {
							this.inStockQtyInconsistentDeliverbillDetailGrid = Ext
									.create('Foss.predeliver.confirmDeliverbill.InStockQtyInconsistentDeliverbillDetailGrid');
						}
						return this.inStockQtyInconsistentDeliverbillDetailGrid;
					},
					// 确认件数不一致FORM
					confirmQtyInconsistentForm : null,
					getConfirmQtyInconsistentForm : function() {
						if (this.confirmQtyInconsistentForm == null) {
							this.confirmQtyInconsistentForm = Ext
									.create('Foss.predeliver.confirmDeliverbill.ConfirmQtyInconsistentForm');
						}
						return this.confirmQtyInconsistentForm;
					},
					constructor : function(config) {
						var me = this, cfg = Ext.apply({}, config);
						me.items = [
								me
										.getArrivesheetQtyInconsistentDeliverbillDetailGrid(),
								me
										.getInStockQtyInconsistentDeliverbillDetailGrid(),
								me.getConfirmQtyInconsistentForm() ];
						me.callParent([ cfg ]);
					}
				});

Ext
		.onReady(function() {
			Ext.QuickTips.init();

			predeliver.confirmDeliverbill.deliverbillDetailGrid = Ext
					.create('Foss.predeliver.confirmDeliverbill.DeliverbillDetailGrid');
			predeliver.confirmDeliverbill.deliverbillInfoForm = Ext
					.create('Foss.predeliver.confirmDeliverbill.DeliverbillInfoForm');
			predeliver.confirmDeliverbill.loadReportForm = Ext
					.create('Foss.predeliver.confirmDeliverbill.LoadReportForm');
			predeliver.confirmDeliverbill.loadGaprepGrid = predeliver.confirmDeliverbill.loadReportForm
					.getLoadGaprepGrid();
			predeliver.confirmDeliverbill.loadTaskDetailGrid = predeliver.confirmDeliverbill.loadReportForm
					.getLoadTaskDetailGrid();
			predeliver.confirmDeliverbill.loadGaprepForm = predeliver.confirmDeliverbill.loadReportForm
					.getLoadGaprepForm();
			predeliver.confirmDeliverbill.submitForm = Ext
					.create('Foss.predeliver.confirmDeliverbill.SubmitForm');
			predeliver.confirmDeliverbill.loaderWindow = null;
			predeliver.confirmDeliverbill.historyLoadGaprepWindow = null;

			predeliver.confirmDeliverbill.taskId = ''; // 装车任务ID
			predeliver.confirmDeliverbill.taskNo = ''; // 装车任务编号
			predeliver.confirmDeliverbill.loadGaprepId = ''; // 装车差异报告ID

			// ---- 重量/体积精度
			predeliver.confirmDeliverbill.numberPrecision = 2;

			// 分配派送司机窗口
			predeliver.confirmDeliverbill.selectDriverWindow = null;

			// 通知客户窗口
			predeliver.confirmDeliverbill.unnotifiedDeliverbillDetailWindow = null;

			// 到达联件数，库存件数与排单件数不一致窗口
			predeliver.confirmDeliverbill.qtyInconsistentDeliverbillDetailWindow = null;

			// 车辆重量/体积装载率阈值(下限)
			predeliver.confirmDeliverbill.volumeLowerThreshold = 0.6;
			predeliver.confirmDeliverbill.weightLowerThreshold = 0.6;

			Ext
					.create(
							'Ext.panel.Panel',
							{
								id : 'T_predeliver-confirmDeliverbillIndex_content',
								cls : "panelContentNToolbar",
								bodyCls : 'panelContentNToolbar-body',
								layout : 'auto',
								getLoaderWindow : function() {
									if (predeliver.confirmDeliverbill.loaderWindow == null) {
										predeliver.confirmDeliverbill.loaderWindow = Ext
												.create('Foss.predeliver.confirmDeliverbill.LoaderWindow');
									}

									return predeliver.confirmDeliverbill.loaderWindow;
								},
								getHistoryLoadGaprepWindow : function() {
									if (predeliver.confirmDeliverbill.historyLoadGaprepWindow == null) {
										predeliver.confirmDeliverbill.historyLoadGaprepWindow = Ext
												.create('Foss.predeliver.confirmDeliverbill.HistoryLoadGaprepWindow');
									}

									return predeliver.confirmDeliverbill.historyLoadGaprepWindow;
								},
								refreshVehicleLoadInfo : function(vehicleNo) {
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

														var driverNameField = predeliver.confirmDeliverbill.submitForm
																.getForm()
																.findField('driverName');

														var vehicle = result.deliverbillVo.vehicleAssociationDto;

														if (vehicle != null) {
															var deliverbillInfo = predeliver.confirmDeliverbill.deliverbillInfoForm.getRecord();
															var vehicleLoad = vehicle.vehicleDeadLoad*1000 - deliverbillInfo.get('weightTotal');
															var vehicleVolume =vehicle.vehicleSelfVolume - deliverbillInfo.get('volumeTotal');
															
															if(vehicleLoad < 0){
																vehicleLoad = 0;
															}
															if(vehicleVolume < 0){
																vehicleVolume = 0;
															}
															
															// 更新派送单统计信息
															deliverbillInfo.set('vehicleNo',vehicle.vehicleNo);
															deliverbillInfo.set('vehicleType',vehicle.vehicleLengthValue);
															deliverbillInfo.set('vehicleLoad',vehicleLoad);
															deliverbillInfo.set('vehicleVolume',vehicleVolume);
															
															// 计算装载率
															var vehicleLoadRate = 0;
															if (vehicle.vehicleDeadLoad == 0||vehicle.vehicleDeadLoad==null) {
																vehicleLoadRate = 0.00;
															} else {
																//需要先将吨位转换为千克
																vehicleLoadRate = deliverbillInfo
																		.get('weightTotal')
																		/ (vehicle.vehicleDeadLoad*1000);
																
															}
															var vehicleVolumeRate = 0;
															if (vehicle.vehicleSelfVolume == 0||vehicle.vehicleSelfVolume==null) {
																vehicleVolumeRate = 0.00;
															} else {
																vehicleVolumeRate = deliverbillInfo
																		.get('volumeTotal')
																		/ vehicle.vehicleSelfVolume;
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

															predeliver.confirmDeliverbill.deliverbillInfoForm
																	.loadRecord(deliverbillInfo);

															/*
															 * SR9 1.
															 * 若排班（和处理订单中排班是同一个排班）中存在该车牌号，则用户在选择司机时自动带出对应的司机
															 * 2.
															 * 若PDA绑定中存在该车牌号，则系统自动带出对应的司机且不可修改
															 * 3.
															 * 当排班和PDA绑定同时存在时，以PDA绑定为准)
															 */
/*	BUG-55619 应黄帅要求 再确认派单时 不再进行司机的绑定校验
 * 															var driver = result.deliverbillVo.driverDto;

															if (driver != null) {
																// 更新driverName和driverCode
																driverNameField
																		.setValue(driver.empName);

																predeliver.confirmDeliverbill.submitForm
																		.getForm()
																		.findField(
																				'driverCode')
																		.setValue(
																				driver.empCode);

																if (driver.pdaSigned == 'Y') {
																	// 若PDA绑定中存在该车牌号，则系统自动带出对应的司机且不可修改
																	driverNameField
																			.setReadOnly(true);
																	driverNameField
																			.clearListeners();
																	Ext.ux.Toast
																			.msg(
																					predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.tip'),
																					predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.existBind'),
																					'ok',
																					3000);
																}
															}*/
														} else {
															Ext.ux.Toast.msg(
																	predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.tip'),
																	predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.vehicleNoExist'),
																	'error',
																	4000);
														}
													}
												});
									}
								},
								refreshDeliverbillInfoForm : function() {
									if (predeliver.confirmDeliverbill.deliverbillId != '') {
										// 若派送单已保存，则刷新统计信息和车载信息
										Ext.Ajax
												.request({
													url : predeliver
															.realPath('queryDeliverbill.action'),
													params : {
														'deliverbillVo.deliverbillDto.id' : predeliver.confirmDeliverbill.deliverbillId
													},
													success : function(response) {
														var result = Ext
																.decode(response.responseText);
														var deliverbill = Ext.ModelManager
																.create(
																		result.deliverbillVo.deliverbill,
																		'Foss.predeliver.confirmDeliverbill.DeliverbillModel');

														// 设置车辆重量/体积装载率阈值(下限)
														predeliver.confirmDeliverbill.volumeLowerThreshold = result.deliverbillVo.volumeLowerThreshold;
														predeliver.confirmDeliverbill.weightLowerThreshold = result.deliverbillVo.weightLowerThreshold;

														// 刷新预排送单信息
														predeliver.confirmDeliverbill.deliverbillInfoForm
																.loadRecord(deliverbill);

														// 刷新司机信息
														predeliver.confirmDeliverbill.submitForm
																.loadRecord(deliverbill);

														// 刷新车载信息
														Ext
																.getCmp(
																		'T_predeliver-confirmDeliverbillIndex_content')
																.refreshVehicleLoadInfo(
																		deliverbill
																				.get('vehicleNo'));

														// 加载车辆任务信息
														Ext
																.getCmp(
																		'T_predeliver-confirmDeliverbillIndex_content')
																.initLoadTaskInfo();
													}
												});
									}
								},
								// 初始化派送装车信息
								initLoadTaskInfo : function() {
									var deliverbill = predeliver.confirmDeliverbill.deliverbillInfoForm
											.getRecord();

									Ext.Ajax
											.request({
												url : predeliver
														.realPath('queryLoadTask.action'),
												params : {
													'deliverbillVo.deliverbillDto.deliverbillNo' : deliverbill
															.get('deliverbillNo')
												},
												success : function(response) {
													var result = Ext
															.decode(response.responseText);

													if (result.deliverbillVo.loadTaskDto != null) {
														predeliver.confirmDeliverbill.taskId = result.deliverbillVo.loadTaskDto.taskId;
														predeliver.confirmDeliverbill.taskNo = result.deliverbillVo.loadTaskDto.taskNo;
														predeliver.confirmDeliverbill.loadGaprepId = result.deliverbillVo.loadTaskDto.loadGaprepId;

														// 加载装车差异报告GRID
														predeliver.confirmDeliverbill.loadGaprepGrid
																.reloadStore();

														// 加载装车任务明细GRID
														predeliver.confirmDeliverbill.loadTaskDetailGrid.store
																.load();
													} else {
														// 若无装车任务，则需要DISABLE掉“退回装车报告”和“确认派送单”
													}
												}
											});
								},
								// 获得分配司机的窗口
								getSelectDriverWindow : function() {
									if (predeliver.confirmDeliverbill.selectDriverWindow == null) {
										predeliver.confirmDeliverbill.selectDriverWindow = Ext
												.create('Foss.predeliver.confirmDeliverbill.SelectDriverWindow');
									}

									return predeliver.confirmDeliverbill.selectDriverWindow;
								},
								// 获得通知客户窗口
								getUnnotifiedDeliverbillDetailWindow : function() {
									if (predeliver.confirmDeliverbill.unnotifiedDeliverbillDetailWindow == null) {
										predeliver.confirmDeliverbill.unnotifiedDeliverbillDetailWindow = Ext
												.create('Foss.predeliver.confirmDeliverbill.UnnotifiedDeliverbillDetailWindow');
									}

									return predeliver.confirmDeliverbill.unnotifiedDeliverbillDetailWindow;
								},
								// 到达联件数，库存件数与排单件数不一致窗口
								getQtyInconsistentDeliverbillDetailWindow : function() {
									if (predeliver.confirmDeliverbill.qtyInconsistentDeliverbillDetailWindow == null) {
										predeliver.confirmDeliverbill.qtyInconsistentDeliverbillDetailWindow = Ext
												.create('Foss.predeliver.confirmDeliverbill.QtyInconsistentDeliverbillDetailWindow');
									}

									return predeliver.confirmDeliverbill.qtyInconsistentDeliverbillDetailWindow;
								},
								// 确认派送单
								confirmDeliverbill : function() {
									Ext.Ajax.request({
												url : predeliver.realPath('confirmDeliverbill.action'), timeout: 90000,
												params : {
													//'deliverbillVo.deliverbillNewDto.id' : deliverbillId//306548
													//'deliverbillVo.deliverbillDto.taskNo' : predeliver.confirmDeliverbill.taskNo
													'deliverbillVo.loadTaskDto.taskNo' : predeliver.confirmDeliverbill.taskNo,
													'deliverbillVo.deliverbillDto.id' : predeliver.confirmDeliverbill.deliverbillId,
													'deliverbillVo.deliverbillDto.deliverbillNo' : predeliver.confirmDeliverbill.deliverbillInfoForm.getRecord().get('deliverbillNo'),
													'deliverbillVo.deliverbillDto.driverCode' : predeliver.confirmDeliverbill.submitForm
															.getForm().findField('driverCode').value,
													'deliverbillVo.deliverbillDto.driverName' : predeliver.confirmDeliverbill.submitForm
															.getForm().findField('driverName').value
												},
												success : function(response) {
													var result = Ext.decode(response.responseText);
													Ext.ux.Toast.msg(predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.tip'),result.message);
													//通知已发送！predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.noticeHasSent')
													//Ext.ux.Toast.msg(predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.tip'),result.message); 
													// 关闭TAB
													Ext.getCmp('T_predeliver-confirmDeliverbillIndex').close();
													
													// 如果派送单(新)列表TAB处于打开状态中，则刷新派送单列表
													if (predeliver.confirmDeliverbill.createType=='Y') {
														if (typeof (predeliver.deliverbillnew.deliverbillNewGrid) != "undefined"
															&& predeliver.deliverbillnew.deliverbillNewGrid != null) {
														predeliver.deliverbillnew.deliverbillNewGrid.store.load();
														}
													}else{
														// 如果派送单列表TAB处于打开状态中，则刷新派送单列表
														if (typeof (predeliver.deliverbill.deliverbillGrid) != "undefined"
																&& predeliver.deliverbill.deliverbillGrid != null) {
															predeliver.deliverbill.deliverbillGrid.store.load();
														}
													}
												},
												error : function(response) {
													var result = Ext
															.decode(response.responseText);
													Ext.ux.Toast.msg(predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.tip'),
															result.message,
															'error', 4000);
													//Ext.ux.Toast.msg(predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.tip'),result.message,'error',4000);
												},
												exception : function(response) {
													var result = Ext
															.decode(response.responseText);
													Ext.ux.Toast.msg(predeliver.confirmDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.tip'),
															result.message,
															'error', 4000);
													//Ext.ux.Toast.msg(predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.tip'),result.message,'error',4000);
												}
											});
									
									Ext.Ajax.request({
												url : predeliver.realPath('isSendSMSToConsumer.action'),//306548在确认派送单时，当状态为“已装车”时发送短信通知客户
												params : {
													'deliverbillVo.loadTaskDto.taskNo' : predeliver.confirmDeliverbill.taskNo,
													'deliverbillVo.deliverbillDto.id' : predeliver.confirmDeliverbill.deliverbillId
												},
												success : function(response) {
													var result = Ext.decode(response.responseText);
													//通知已发送！
													Ext.ux.Toast.msg(predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.tip'),predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.noticeHasSent')); 
												},
												error : function(response) {
													var result = Ext.decode(response.responseText);
													Ext.ux.Toast.msg(predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.tip'),result.message,'error',4000);
												},
												exception : function(response) {
													var result = Ext.decode(response.responseText);
													Ext.ux.Toast.msg(predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.tip'),result.message,'error',4000);
												}
											});
								},
								items : [
										predeliver.confirmDeliverbill.deliverbillInfoForm,
										predeliver.confirmDeliverbill.deliverbillDetailGrid,
										predeliver.confirmDeliverbill.loadReportForm,
										predeliver.confirmDeliverbill.submitForm ],
								renderTo : 'T_predeliver-confirmDeliverbillIndex-body'
							});

			Ext.getCmp('T_predeliver-confirmDeliverbillIndex_content')
					.refreshDeliverbillInfoForm();
		});
