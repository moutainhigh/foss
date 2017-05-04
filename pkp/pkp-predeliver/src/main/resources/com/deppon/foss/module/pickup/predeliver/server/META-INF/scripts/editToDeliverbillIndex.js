/**
 * @author ibm-wangxiexu page 新增/编辑派送单
 */
// 定义数据字典model
predeliver.editToDeliverbill.getDate = function(date, hours, minutes, seconds,
		milliseconds) {
	date.setHours(hours);
	date.setMinutes(minutes);
	date.setSeconds(seconds);
	date.setMilliseconds(milliseconds);

	return date;
};

Ext.define('Foss.predeliver.editToDeliverbill.DataDictionaryModel', {
	extend : 'Ext.data.Model',
	fields : [ {
		name : 'code',
		type : 'string'
	}, {
		name : 'name',
		type : 'string'
	} ]
});

//提货方式集合
Ext.define('Foss.predeliver.store.ReceiveMethodStore',{
	extend: 'Ext.data.Store',
	fields: [
		{name: 'valueCode',  type: 'string'},
		{name: 'valueName',  type: 'string'}
	],
	data: {
		'items':[
			{'valueCode':'','valueName':predeliver.editToDeliverbill.i18n('pkp.predeliver.notifyCustomer.complete')},// '全部'
			{'valueCode':'DELIVER','valueName':predeliver.editToDeliverbill.i18n('pkp.predeliver.notifyCustomer.deliver')},// '送货'
			{'valueCode':'PICKUP','valueName':predeliver.editToDeliverbill.i18n('pkp.predeliver.notifyCustomer.pickup')}//'自提'
		]
	},
	//定义一个代理对象
	proxy: {
		//代理的类型为内存代理
		type: 'memory',
		//定义一个读取器
		reader: {
			//以JSON的方式读取
			type: 'json',
			//定义读取JSON数据的根对象
			root: 'items'
		}
	}
});

//派车类型集合
Ext.define('Foss.predeliver.store.DeliverTypeStore',{
	extend: 'Ext.data.Store',
	fields: [
		{name: 'valueCode',  type: 'string'},
		{name: 'valueName',  type: 'string'}
	],
	data: {
		'items':[
			{'valueCode':'NOMAL','valueName':predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.nomal')},// '正常'
			{'valueCode':'SPECIAL','valueName':predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.special')},// '专车'
			{'valueCode':'MANNED','valueName':predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.manned')}//'带人'
		]
	},
	//定义一个代理对象
	proxy: {
		//代理的类型为内存代理
		type: 'memory',
		//定义一个读取器
		reader: {
			//以JSON的方式读取
			type: 'json',
			//定义读取JSON数据的根对象
			root: 'items'
		}
	}
});


// 获得当前部门是否营业部
var dept = FossUserContext.getCurrentUserDept().salesDepartment;
(function() {
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
				Ext.ux.Toast.msg(predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.tip'), result.message, 'error', 3000);
			}
		});
})();

(function() {
		// 获得派送单sequence
		Ext.Ajax.request({
			url : predeliver.realPath('querySequence.action'),
			async : false,
			success : function(response) {
				var json = Ext.decode(response.responseText);
				Ext.apply(predeliver, {
					sequence : json.sequence
				});
			},
			exception : function(response) {
				var result = Ext.decode(response.responseText);
				Ext.ux.Toast.msg(predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.tip'), result.message, 'error', 3000);
			}
		})
})();

var deptCode = FossUserContext.getCurrentUserDept().code;
var deptName = FossUserContext.getCurrentUserDept().name;

// 基础信息Form
Ext.define('Foss.predeliver.editToDeliverbill.DeliverbillBasicInfoForm',
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
								value : predeliver.editToDeliverbill.deliverbillId
							},
							{
								name : 'deliverbillNo',
								fieldLabel : predeliver.editToDeliverbill
										.i18n('foss.pickup.predeliver.deliverbillNo'), // 预派送单编号
								columnWidth : .33,
								readOnly : true,
								value : predeliver.editToDeliverbill.deliverbillNo
							},
							{
								name : 'vehicleType',
								fieldLabel : predeliver.editToDeliverbill
										.i18n('foss.pickup.predeliver.vehicleType'), // 车辆组别
								columnWidth : .33,
								xtype : 'commonmotorcadeselector',
								loopOrgCode : predeliver.fleetCode,
								listeners : {
									'change' : function(field, event, eOpts) {
										var form = field.up('form').getForm(),
											vehicleNo = form.findField('vehicleNo');
										if (field.value != '' && field.value != null) {
											vehicleNo.setReadOnly(false);
											vehicleNo.getStore().un('beforeload');
											vehicleNo.getStore().on('beforeload', function(store, operation,eOpts) {
												var searchParams = operation.params;
												if (Ext.isEmpty(searchParams)) {
													searchParams = {};
													Ext.apply(operation, {
														params : searchParams
													});
												}
												searchParams['truckVo.truck.loopOrgCode'] = field.value;
											});
											vehicleNo.getStore().load();
											vehicleNo.expand();
										}
										else
										{
											vehicleNo.setValue('');
											vehicleNo.setReadOnly(true);
										}
									}
								}
							},
							{
								name : 'vehicleNo',
								fieldLabel : predeliver.editToDeliverbill.i18n('foss.pickup.predeliver.vehicleNo'), // 车辆
								columnWidth : .34,
								xtype : 'commontruckselector', 
								allowBlank : false,
								queryAllFlag : true,
								readOnly : true,
 								listeners : {
									'blur' : function(field, event, eOpts) {
										if (field.value != '') {
											if (predeliver.editToDeliverbill.deliverbillId != '') {
												// 车牌号不为空，且派送单已保存时，刷新派送单统计信息
												var deliverbillInfo = predeliver.editToDeliverbill.deliverbillInfoForm
														.getRecord();
												var oldVehicleNo = deliverbillInfo
														.get('vehicleNo');

												if (field.value != oldVehicleNo) {
													Ext.getCmp('T_predeliver-editToDeliverbillIndex_content')
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
								fieldLabel : predeliver.editToDeliverbill.i18n('foss.pickup.predeliver.driverName'), // 司机姓名
								columnWidth : .33,
								xtype : 'commondriverselector',
								parentOrgCode:predeliver.fleetCode,//上级车队组织
								waybillFlag:'N',//是否是集中接送区域  如果设置值为Y,N
								fleetType:'FLEET_TYPE__SHUTTLE,FLEET_TYPE__SHUTTLE_GOODS,FLEET_TYPE__LONG_DISTANCE',//车队组类型(包含集中接送货和物流班车)
								listeners : {
									blur : function(field, event, eOpts) {
										// 公共选择器的value为driverCode,rawValue为driverName
										// 更新driverCode
										predeliver.editToDeliverbill.deliverbillBasicInfoForm
												.getForm().findField(
														'driverCode').setValue(
														field.value);

										// 若修改了司机，且派送单已保存，则更新“统计信息”中的司机姓名
										if (field.value != '') {
											if (predeliver.editToDeliverbill.deliverbillId != '') {
												var deliverbillInfo = predeliver.editToDeliverbill.deliverbillInfoForm
														.getRecord();
												var oldDriverName = deliverbillInfo
														.get('driverName');

												// 更新“统计信息”中的司机姓名
												if (field.rawValue != oldDriverName) {
													deliverbillInfo.set(
															'driverName',
															field.rawValue);
													predeliver.editToDeliverbill.deliverbillInfoForm
															.loadRecord(deliverbillInfo);
												}
											}
										}
									}
								}
							}, {
								xtype : 'combobox',
								name : 'deliverType',
								fieldLabel : predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.deliveryType'), //派车类型,
								columnWidth : .33,
								displayField : 'valueName',
								valueField : 'valueCode',
								queryMode : 'local',
								triggerAction : 'all',
								editable : false,
								value : 'NOMAL',
								store : Ext.create('Foss.predeliver.store.DeliverTypeStore')
							},{
								name : 'driverCode',
								hidden : true
							} ],
							constructor : function(config) {
								var me = this, 
								cfg = Ext.apply({}, config);
								me.callParent([ cfg ]);
								var vehicleType = me.getForm().findField('vehicleType'),
									vehicleTypeStore = vehicleType.getStore();
								vehicleTypeStore.on('load',function(th,re){
										var model = Ext.ModelManager.create({ 
										  'code' : deptCode,
										  'name' : deptName
										},'Foss.baseinfo.commonSelector.MotorcadeModel');
										th.insert(0, model);
								});
							}
				});

// 定义预派送单明细（待排运单复用）的模型
Ext.define('Foss.predeliver.editToDeliverbill.DeliverbillDetailModel',
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
													predeliver.editToDeliverbill.numberPrecision);
								}
							},
							{
								name : 'dimension', // 运单尺寸
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
													predeliver.editToDeliverbill.numberPrecision);
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
								name : 'consigneeAddress', // 送货地址
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
							/*
							 * convert:function(value){ if(value!=null && value ==
							 * 'N'){ return predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.no'); }else { return predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.yes'); } }
							 */
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
Ext.define('Foss.predeliver.editToDeliverbill.DeliverbillDetailStore',
				{
					extend : 'Ext.data.Store',
					autoLoad : true,
					// 绑定一个模型
					model : 'Foss.predeliver.editToDeliverbill.DeliverbillDetailModel',
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
													'deliverbillVo.deliverbillDto.id' : predeliver.editToDeliverbill.deliverbillId
												}
											});
						}
					}
				});

// 定义预派送单的模型
Ext.define('Foss.predeliver.editToDeliverbill.DeliverbillModel', {
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
	},{
		name : 'deliverType', //派车类型
		type : 'string'
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


Ext.define('Foss.predeliver.editToDeliverbill.DeliverbillDetailForm', {
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
	items : [
			{
				name : 'waybillNo',
				fieldLabel : predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.waybillNo'), //运单号,
				columnWidth : .33,
				readOnly : true
			},
			{
				name : 'goodsName',
				fieldLabel : predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.goodsName'), //货物名称,
				columnWidth : .33,
				readOnly : true
			},
			{
				name : 'arrangeGoodsQty',
				fieldLabel : predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.arrangeGoodsQty'), //排单件数,
				columnWidth : .33,
				readOnly : true
			},
			{
				name : 'arrangedWeight',
				fieldLabel : predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.weight'), //排单重量,
				columnWidth : .33,
				readOnly : true
			},
			{
				name : 'transportType',
				fieldLabel : predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.transportType'), //运输方式,
				columnWidth : .33,
				readOnly : true,
				setValue : function(value) {
					this.setRawValue(Foss.pkp.ProductData.rendererSubmitToDisplay(value));
				}
			},
			{
				name : 'dimension',
				fieldLabel : predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.goodsSize'), //尺寸,
				columnWidth : .33,
				readOnly : true
			},
			{
				name : 'consignee',
				fieldLabel : predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.consignee'), //收货人,
				columnWidth : .33,
				readOnly : true
			},
			{
				name : 'consigneeContact',
				fieldLabel : predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.consigneeContact'), //联系方式,
				columnWidth : .33,
				readOnly : true
			},
			{
				name : 'consigneeAddress',
				fieldLabel : predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.consigneeAddress'), //送货地址,
				columnWidth : .33,
				readOnly : true
			},
			{
				name : 'deliverRequire',
				fieldLabel : predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.deliverRequire'), //注意事项,
				columnWidth : .33,
				readOnly : true
			},
			{
				name : 'goodsStatus',
				fieldLabel : predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.goodsStatus'), //货物状态,
				columnWidth : .33,
				readOnly : true
			},
			{
				name : 'isAlreadyContact',
				fieldLabel : predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.isAlreadyContact'), //是否已联系客户,
				columnWidth : .33,
				readOnly : true,
				setValue : function(value) {
					if (value == 'Y') {
						this.setRawValue(predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.yes'));
					} else {
						this.setRawValue(predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.no'));
					}
				}
			},
			{
				name : 'estimatedDeliverTime',
				fieldLabel : predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.estimatedDeliverTime'), //预计送货时间,
				columnWidth : .33,
				readOnly : true,
				xtype : 'datefield',
				format : 'Y-m-d H:i:s'
			},
			{
				name : 'isException',
				fieldLabel : predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.isException'), //是否异常,
				columnWidth : .33,
				readOnly : true,
				setValue : function(value) {
					if (value == 'Y') {
						this.setRawValue(predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.yes'));
					} else {
						this.setRawValue(predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.no'));
					}
				}
			},
			{
				name : 'isNeedInvoice',
				fieldLabel : predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.isNeedInvoice'), //是否需要发票,
				columnWidth : .33,
				readOnly : true,
				setValue : function(value) {
					if (value == 'Y') {
						this.setRawValue(predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.yes'));
					} else {
						this.setRawValue(predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.no'));
					}
				}
			},
			{
				name : 'paymentType',
				fieldLabel : predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.paymentType'), //付款方式,
				columnWidth : .33,
				readOnly : true,
				setValue : function(value) {
					this.setRawValue(FossDataDictionary
							.rendererSubmitToDisplay(value, 'SETTLEMENT__PAYMENT_TYPE'));
				}
			},
			{
				name : 'notes',
				fieldLabel : predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.notes'), //备注,
				columnWidth : .33,
				readOnly : true
			},
			{
				name : 'deliverType',
				fieldLabel : predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.deliverType'), //送货方式,
				columnWidth : .33,
				readOnly : true,
				setValue : function(value) {
					var v = FossDataDictionary.rendererSubmitToDisplay(value, 'PICKUPGOODSHIGHWAYS');
					if(Ext.isEmpty(v) || value == v){
						v = FossDataDictionary.rendererSubmitToDisplay(value, 'PICKUPGOODSSPECIALDELIVERYTYPE');
					}
					this.setRawValue(v);
				}
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
Ext.define('Foss.predeliver.editToDeliverbill.DeliverbillDetailGrid',
				{
					extend : 'Ext.grid.Panel',
					frame : true,
					title : predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.deliverbillDetailInfo'), // 待排单信息
					emptyText : predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.emptyText'), //查询结果为空,
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
									xtype : 'image',
									margin : '0 0 0 30',
									imgCls : 'foss_icons_pkp_flaggreen'
								},
								{
									xtype : 'label',
									margin : '0 0 0 10',
									text : predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.waybillGoodsQtyNotPreArrangeGoodsQty') //排单货物的件数与开单的件数不同
								},
								{
									xtype : 'image',
									margin : '0 0 0 30',
									imgCls : 'foss_icons_pkp_goodsNumDisac'
								},
								{
									xtype : 'label',
									margin : '0 0 0 10',
									text : predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.preArrangeGoodsQtyNotStockGoodQty') //排单件数与库存件数不同
								},
								{
									xtype : 'container',
									border : false,
									columnWidth : .80,
									html : '&nbsp;'
								},
								{
									border : 1,
									xtype : 'button',
									columnWidth : .08,
									text : predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.arrange'), //排单,
									handler : function() {
										// 排单前需验证车辆信息
										if (predeliver.editToDeliverbill.deliverbillId == "") {
											// 排单前，若还未保存派送单，则需先保存派送单
											// 验证车辆信息
											if (!predeliver.editToDeliverbill.submitForm.validateOnArrange()) {
												return;
											}
										}
										predeliver.editToDeliverbill.deliverbillDetailGrid
										.showArrangeWaybillWindow();
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
								dataIndex : 'waybillNo',
								align : 'center',
								flex : 1,
								header : predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.waybillNo') //运单号
							},
							{
								dataIndex : 'goodsName',
								align : 'center',
								flex : 1,
								header : predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.goodsName') //货物名称
							},
							{
								dataIndex : 'weight',
								align : 'center',
								flex : 1,
								header : predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.weight') //排单重量
							},
							{
								dataIndex : 'dimension',
								align : 'center',
								flex : 1,
								header : predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.goodsSize') //尺寸
							},
							{
								dataIndex : 'goodsVolumeTotal',
								align : 'center',
								flex : 1,
								header : predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.dimension') //体积
							},
							{
								dataIndex : 'goodsPackage',
								align : 'center',
								flex : 1,
								header : predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.goodsPackage') //包装
							},
							{
								dataIndex : 'waybillGoodsQty',
								align : 'center',
								flex : 1,
								header : predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.pieces') //件数
							},
							{
								dataIndex : 'preArrangeGoodsQty',
								align : 'center',
								flex : 1,
								header : predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.arrangeGoodsQty') //排单件数
							},
							{
								dataIndex : 'transportType',
								align : 'center',
								flex : 1,
								header : predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.transportType'), //运输方式,
								renderer : function(value) {
									return Foss.pkp.ProductData.rendererSubmitToDisplay(value);
								}
							}, {
								dataIndex : 'arriveTime',
								align : 'center',
								flex : 1,
								header : predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.arriveTime'), //到达时间,
								xtype : 'datecolumn',
								format : 'Y-m-d H:i:s'
							}, {
								dataIndex : 'consignee',
								align : 'center',
								flex : 1,
								header : predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.consignee') //收货人
							}, {
								dataIndex : 'consigneeContact',
								align : 'center',
								flex : 1,
								header : predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.consigneeContact') //联系方式
							}, {
								dataIndex : 'consigneeAddress',
								align : 'center',
								flex : 1,
								header : predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.consigneeAddress') //送货地址
							} ],
					pagingToolbar : null,
					getPagingToolbar : function() {
						if (this.pagingToolbar == null) {
							this.pagingToolbar = Ext.create(
									'Deppon.StandardPaging', {
										store : this.store,
						  				plugins: 'pagesizeplugin',
										displayInfo: true
									});
						}
						return this.pagingToolbar;
					},
					// 弹出排单窗口
					showArrangeWaybillWindow : function() {
						var arrangeDeliverbillWindow = Ext.getCmp(
								'T_predeliver-editToDeliverbillIndex_content')
								.getArrangeDeliverbillWindow();
						// 初始化界面
						arrangeDeliverbillWindow.getWaybillToArrangeGrid().store
								.removeAll();
						arrangeDeliverbillWindow.getWaybillToArrangeGrid()
								.getPagingToolbar().hide();
						// 清空选中的运单
						predeliver.editToDeliverbill.waybillsToArrange.clear();

						arrangeDeliverbillWindow.show();
					},
					deleteDeliverbillDetails : function(deliverbillDetailIds) {
						Ext.Ajax
								.request({
									url : predeliver
											.realPath('deleteDeliverbillDetails.action'),
									params : {
										'deliverbillVo.deliverbillDto.id' : predeliver.editToDeliverbill.deliverbillId,
										'deliverbillVo.deliverbillDetailIds' : deliverbillDetailIds
									},
									success : function(response) {
										var result = Ext
												.decode(response.responseText);
										var deliverbill = Ext
												.create(
														'Foss.predeliver.editToDeliverbill.DeliverbillModel',
														result.deliverbillVo.deliverbill);

										predeliver.editToDeliverbill.deliverbillDetailGrid.store
												.load();

										predeliver.editToDeliverbill.deliverbillInfoForm
												.loadRecord(deliverbill);

										Ext
												.getCmp(
														'T_predeliver-editToDeliverbillIndex_content')
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
						rowBodyElement : 'Foss.predeliver.editToDeliverbill.DeliverbillDetailForm'
					} ],
					viewConfig : {
						stripeRows : false,
						enableTextSelection : true,
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
								.create('Foss.predeliver.editToDeliverbill.DeliverbillDetailStore');
						me.selModel = Ext.create('Ext.selection.CheckboxModel');

						me.bbar = me.getPagingToolbar();
						me.callParent([ cfg ]);
					}
				});

// 统计信息Form
Ext.define('Foss.predeliver.editToDeliverbill.DeliverbillInfoForm', {
	extend : 'Ext.form.Panel',
	title : predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.statistics'), //统计信息,
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
		fieldLabel : predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.vehicleNo'), //车辆,
		columnWidth : .33,
		readOnly : true
	}, {
		name : 'vehicleType',
		fieldLabel : predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.vehicleType'), //车型,
		columnWidth : .33,
		readOnly : true
	}, {
		name : 'driverName',
		fieldLabel : predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.driver'), //司机,
		columnWidth : .33,
		readOnly : true
	}, {
		name : 'deliverbillNo',
		fieldLabel : predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.deliverbillNo'), //对应预派送单,
		columnWidth : .33,
		value : predeliver.editToDeliverbill.deliverbillNo,
		readOnly : true
	}, {
		name : 'weightTotal',
		fieldLabel : predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.weightTotal'), //总重量(公斤),
		columnWidth : .33,
		readOnly : true
	}, {
		name : 'volumeTotal',
		fieldLabel : predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.volumeTotal'), //总体积(方),
		columnWidth : .33,
		readOnly : true
	}, {
		name : 'vehicleLoad',
		fieldLabel : predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.vehicleLoad'), //可载重量(公斤),
		columnWidth : .33,
		readOnly : true
	}, {
		name : 'vehicleVolume',
		fieldLabel : predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.vehicleVolume'), //可载体积(方),
		columnWidth : .33,
		readOnly : true
	}, {
		name : 'loadRate',
		fieldLabel : predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.loadRate'),//装载率(容量/体积)
		columnWidth : .33,
		readOnly : true
	}, {
		name : 'waybillQtyTotal',
		fieldLabel : predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.waybillQtyTotal'), //总票数,
		columnWidth : .33,
		readOnly : true
	}, {
		name : 'goodsQtyTotal',
		fieldLabel : predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.arriveSheetGoodsQty'), //总件数,
		columnWidth : .33,
		readOnly : true
	}, {
		name : 'payAmountTotal',
		fieldLabel : predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.toPayAmount'), //到付金额,
		columnWidth : .33,
		readOnly : true
	} ]
});

// 查询运单form
Ext.define('Foss.predeliver.editToDeliverbill.QueryWaybillForm',
				{
					title : predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.queryGoodsInfo'), //货物信息查询,
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
					    		name : 'waybillNos',
					    		labelWidth : 50,
					    		xtype : 'textarea',
					    		fieldLabel : predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.waybillNo'), //运单号,
					    		columnWidth : .25,
					    		emptyText : predeliver.editToDeliverbill.i18n('pkp.predeliver.notifyCustomer.waybillNo.valitation'),
					    		regex : /^([0-9]{8,9}\n?)+$/i,
					    		regexText : predeliver.editToDeliverbill.i18n('pkp.predeliver.notifyCustomer.waybillNo.valitation')
					    	
					        },
							{
								name : 'receiveCustomerName',
								fieldLabel : predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.consigneeName'), //收货人名称,
								columnWidth : .25
							},
							{
								name : 'receiveCustomerPhone',
								fieldLabel : predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.receiveCustomerPhone'), //收货人电话,
								columnWidth : .25
							},
							{
								xtype : 'combobox',
								name : 'receiveMethod',
								fieldLabel : predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.deliverType'), //送货方式,
								columnWidth : .25,
								displayField : 'valueName',
								valueField : 'valueCode',
								queryMode : 'local',
								triggerAction : 'all',
								editable : false,
								value : '',
								store : Ext.create('Foss.predeliver.store.ReceiveMethodStore')
							},
							{
								xtype : 'rangeDateField',
								fieldId : 'predeliver_editToDeliverbill_arriveTime',
								fieldLabel :predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.arriveTime'),   // 到达时间
								dateType : 'datetimefield_date97',
								fromName : 'arriveTimeBegin',
								toName : 'arriveTimeEnd',
								fromValue : Ext.Date.format(predeliver.editToDeliverbill.getDate(
												new Date(), 0, 0, 0, 0), 'Y-m-d H:i:s'),
								toValue : Ext.Date.format(predeliver.editToDeliverbill.getDate(
												new Date(), 23, 59, 59, 999), 'Y-m-d H:i:s'),
								columnWidth : .48,
								disallowBlank : true,
								editable : false
							},
							{
								xtype: 'datetimefield_date97',
								editable : true,
								format : 'Y-m-d H:i:s',
								id: 'Foss_EditDeliverbill_QueryWaybillForm_DeliverTime_ID',
								fieldLabel: predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.deliverTime'), //预计送货时间,	
								labelWidth : 85,
								allowBlank:false,
								value:Ext.Date.format(new Date(),'Y-m-d H:i:s'),
								columnWidth:.27,
								name: 'deliverTime',
								time : true,
								dateConfig: { 
									el : 'Foss_EditDeliverbill_QueryWaybillForm_DeliverTime_ID-inputEl',
									minDate: '%y-%M-%d 00:00:00'
								}
							},
							{
								name : 'receiveCustomerDistCode',
								xtype : 'commonmotorcadedistrictselector',
								fieldLabel : predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.administrativeRegions'),//行政区域
								columnWidth : .25
							},
							{
								name : 'deliverRegionCode',
								xtype : 'commonsmallzoneselector',
								fieldLabel : predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.regionVehicleCode'), //定人定区,
								columnWidth : .25
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
											text : predeliver.editToDeliverbill
													.i18n('foss.pickup.predeliver.resetButton'), // 重置
											handler : function() {
												this.up('form').getForm()
														.reset();
												this.up('form').getForm().setValues({
													'arriveTimeBegin' : Ext.Date.format(
															predeliver.editToDeliverbill.getDate(new Date(),
																	0, 0, 0, 0), 'Y-m-d H:i:s'),
													'arriveTimeEnd' : Ext.Date.format(
															predeliver.editToDeliverbill.getDate(new Date(),
																	23, 59, 59, 999), 'Y-m-d H:i:s')
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
											text : predeliver.editToDeliverbill
													.i18n('foss.pickup.predeliver.queryButton'), // 查询
											handler : function() {
												var form = this.up('form').getForm(),waybillNos = form.getValues().waybillNos;
												
												// 验证运单号输入的行数
												if (!Ext.isEmpty(waybillNos)) {
													var arrayWaybillNo = waybillNos.split('\n');
													if (arrayWaybillNo.length > 50) {
														Ext.ux.Toast.msg(predeliver.editToDeliverbill.i18n('pkp.predeliver.notifyCustomer.tip'), predeliver.editToDeliverbill.i18n('pkp.predeliver.notifyCustomer.waybillNo.valitation'), 'error', 3000); 
														return;	
													}
													for (var i = 0; i < arrayWaybillNo.length; i++) {
														if (Ext.isEmpty(arrayWaybillNo[i])) {
															Ext.ux.Toast.msg(predeliver.editToDeliverbill.i18n('pkp.predeliver.notifyCustomer.tip'), predeliver.editToDeliverbill.i18n('pkp.predeliver.notifyCustomer.waybillNo.valitation'), 'error', 3000); 
															return;	
														}
													}
												}
												
												var arriveTimeBegin = form.getValues().arriveTimeBegin;
												var arriveTimeEnd = form.getValues().arriveTimeEnd;
												var result = Ext.Date.parse(arriveTimeEnd,'Y-m-d H:i:s') - Ext.Date.parse(arriveTimeBegin,'Y-m-d H:i:s');
												if(result / (24 * 60 * 60 * 1000) >= 30){
													Ext.ux.Toast.msg(predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.tip'), predeliver.editToDeliverbill.i18n('pkp.predeliver.deliverbillIndex.submitTimeDateIntervalError'), 'error', 3000);
													return;
												}
												var pagingToolbar = this
														.up('window')
														.getWaybillToArrangeGrid()
														.getPagingToolbar();

												pagingToolbar.show();
												pagingToolbar.moveFirst();
											}
										} ]
							} ],
							listeners : { 
								'render' : function(_this, eOpts){
									_this.getForm().findField('waybillNos').setHeight(100);
								}
							}
				});

// 定义待排单货物信息的Store
Ext.define('Foss.predeliver.editToDeliverbill.WaybillToArrangeStore',
				{
					extend : 'Ext.data.Store',
					autoLoad : false,
					// 绑定一个模型
					model : 'Foss.predeliver.editToDeliverbill.DeliverbillDetailModel',
					pageSize : 10,
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
											'T_predeliver-editToDeliverbillIndex_content')
									.getArrangeDeliverbillWindow()
									.getQueryWaybillForm();

							if (queryWaybillForm != null) {
								var queryParams = queryWaybillForm.getValues();
								
								var params = {
										'deliverbillVo.waybillToArrangeDto.deliverRegionCode' : queryParams.deliverRegionCode,
										'deliverbillVo.waybillToArrangeDto.waybillNo' : queryParams.waybillNos,
										'deliverbillVo.waybillToArrangeDto.receiveMethod' : queryParams.receiveMethod,
										'deliverbillVo.waybillToArrangeDto.receiveCustomerName' : queryParams.receiveCustomerName,
										'deliverbillVo.waybillToArrangeDto.receiveCustomerPhone' : queryParams.receiveCustomerPhone,
										'deliverbillVo.waybillToArrangeDto.arriveTimeBegin' : queryParams.arriveTimeBegin,
										'deliverbillVo.waybillToArrangeDto.arriveTimeEnd' : queryParams.arriveTimeEnd,
										'deliverbillVo.waybillToArrangeDto.deliverTime' : queryParams.deliverTime
									};
									var districtList = queryWaybillForm.getForm().findField('receiveCustomerDistCode').getValue();
									for(var i=0;i<districtList.length;i++){
										params['deliverbillVo.waybillToArrangeDto.districtList['+i+']'] = districtList[i];
									}
									
									Ext.apply(operation,
											{
												params : params
											});
							}
						},
						load : function(store, records, successful, oOpts) {
							
							var data = store.getProxy().getReader().rawData;
							if(!data.success &&(!data.isException)){
								Ext.ux.Toast.msg(predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.tip'), data.message, 'error', 2000);	//提示			
								return;
							}
							
							
							selectedRecords = new Array();

							for ( var i = 0; i < records.length; i++) {
								predeliver.editToDeliverbill.waybillsToArrange
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

							predeliver.editToDeliverbill.arrangeDeliverbillWindow
									.getWaybillToArrangeGrid().selModel
									.select(selectedRecords);
						}
					}
				});

// 定义运单checkbox model
Ext.define('Foss.predeliver.editToDeliverbill.CheckboxModel', {
	extend : 'Ext.selection.CheckboxModel',
	listeners : {
		select : function(row, record, index, eOpts) {
			predeliver.editToDeliverbill.waybillsToArrange.add(record.data.id,
					record.data);

		},
		deselect : function(row, record, index, eOpts) {
			predeliver.editToDeliverbill.waybillsToArrange
					.removeAtKey(record.data.id);
		}
	}
})

var flag = true;

// 定义货物信息查询结果的表格
Ext.define('Foss.predeliver.editToDeliverbill.WaybillToArrangeGrid',
				{
					extend : 'Ext.grid.Panel',
					frame : true,
					title : predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.goodsInfoResult'), //货物信息查询结果,
					id :'Foss_predeliver_editToDeliverbill_WaybillToArrangeGrid_Id',
					emptyText : predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.emptyText'), //查询结果为空,
					bodyCls : 'autoHeight',
					cls : 'autoHeight',
					autoScroll : true,
					collapsible : true,
					animCollapse : true,
					cellEditor : null,
					height : 500,
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
					dockedItems : [ {
						xtype : 'toolbar',
						dock : 'top',
						layout : 'column',
						items : [ {
							xtype : 'image',
							margin : '0 0 0 0',
							imgCls : 'foss_icons_pkp_flagred'
						}, {
							xtype : 'label',
							margin : '0 0 0 10',
							text : predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.whether') //是否必送货
						}, {
							xtype : 'image',
							margin : '0 0 0 30',
							imgCls : 'foss_icons_pkp_flaggreen'
						}, {
							xtype : 'label',
							margin : '0 0 0 10',
							text : predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.waybillGoodsQtyNotPreArrangeGoodsQty') //排单货物的件数与开单的件数不同
						}, {
							xtype : 'image',
							margin : '0 0 0 30',
							imgCls : 'foss_icons_pkp_goodsNumDisac'
						}, {
							xtype : 'label',
							margin : '0 0 0 10',
							text : predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.preArrangeGoodsQtyNotStockGoodQty') //排单件数与库存件数不同
						} ]
					} ],
					columns : [
							{
								xtype : 'actioncolumn',
								width : 50,
								align : 'center',
								items : [
										{
											iconCls : 'foss_icons_pkp_exceptionSec',
											getClass : function(value,
													metadata, record, rowIndex,
													colIndex, store) {
												if (record
														.get('waybillGoodsQty') != record
														.get('preArrangeGoodsQty')) {
													return 'foss_icons_pkp_exceptionSec';
												} else {
													return 'foss_icons_pkp_exceptionSec_hide';
												}
											}
										},
										{
											iconCls : 'foss_icons_pkp_exception',
											getClass : function(value,
													metadata, record, rowIndex,
													colIndex, store) {
												if (record
														.get('isSentRequired') == 'Y') {
													return 'foss_icons_pkp_exception';
												} else {
													return 'foss_icons_pkp_exception_hide';
												}
											}
										} ]
							},
							{
								dataIndex : 'id',
								align : 'center',
								flex : 1,
								hidden : true
							},
							{
								dataIndex : 'waybillNo',
								align : 'center',
								width : 80,
								header : predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.waybillNo') //运单号
							},
							{
								dataIndex : 'goodsName',
								align : 'center',
								width : 80,
								header : predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.goodsName') //货物名称
							},
							{
								dataIndex : 'consigneeAddress',
								align : 'center',
								width : 130,
								xtype: 'ellipsiscolumn',
								header : predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.consigneeAddress') //送货地址
							},
							{
								dataIndex : 'arrangedWeight',
								align : 'center',
								width : 60,
								header : predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.weight') //排单重量
							},
							{
								dataIndex : 'dimension',
								align : 'center',
								width : 80,
								header : predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.goodsSize') //尺寸
							},
							{
								dataIndex : 'goodsVolumeTotal',
								align : 'center',
								width : 60,
								header : predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.dimension') //体积
							},
							{
								dataIndex : 'goodsPackage',
								align : 'center',
								width : 80,
								xtype: 'ellipsiscolumn',
								header : predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.goodsPackage') //包装
							},
							{
								dataIndex : 'waybillGoodsQty',
								align : 'center',
								width : 60,
								header : predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.pieces') //件数
							},
							{
								dataIndex : 'stockGoodQty',
								align : 'center',
								width : 60,
								header : predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.stockQty'),//库存件数,
							}, 
							{
								dataIndex : 'isAlreadyContact',
								align : 'center',
								width : 60,
								hidden : true,
								header : predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.isAlreadyNotice') //是否通知
							},
							{
								dataIndex : 'preArrangeGoodsQty',
								align : 'center',
								width : 60,
								header : predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.arrangeGoodsQty'), //排单件数,
								editor : {
									// 定义编辑框的类型，编辑框可以是表单中的所有类型
									xtype : 'numberfield',
									allowBlank : false,
									allowDecimals : false,
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
																	flag = false;
																	Ext.ux.Toast
																			.msg(
																					predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.tip'),
																					predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.preArrangeGoodsQtynotArrangableGoodsQty'),
																					'error',
																					4000);
																	editor
																			.cancelEdit();
																	return;
																} else {
																	flag = true;
																}

																if (field.lastValue <= 0) {
																	flag = false;
																	Ext.ux.Toast
																			.msg(
																					predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.tip'),
																					predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.preArrangeGoodsQtyGTZerp'),
																					'error',
																					4000);
																	editor
																			.cancelEdit();
																	return;
																} else {
																	flag = true;
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
							},
							{
								dataIndex : 'transportType',
								align : 'center',
								width : 100,
								header : predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.transportType'), //运输方式,
								renderer : function(value) {
									return Foss.pkp.ProductData.rendererSubmitToDisplay(value);
								}
							}, {
								dataIndex : 'arriveTime',
								align : 'center',
								width : 130,
								header : predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.arriveTime'), //到达时间,
								xtype : 'datecolumn',
								format : 'Y-m-d H:i:s'
							}, {
								dataIndex : 'consignee',
								align : 'center',
								width : 60,
								xtype: 'ellipsiscolumn',
								header : predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.consignee') //收货人
							}, {
								dataIndex : 'consigneeContact',
								align : 'center',
								width : 100,
								xtype: 'ellipsiscolumn',
								header : predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.consigneeContact') //联系方式
							}, {
								dataIndex : 'isSentRequired',
								align : 'center',
								width : 60,
								header : predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.whether'), //是否必送货,
								hidden : true
							}],
					pagingToolbar : null,
					getPagingToolbar : function() {
						if (this.pagingToolbar == null) {
							this.pagingToolbar = Ext.create(
									'Deppon.StandardPaging', {
										store : this.store,
										plugins: 'pagesizeplugin',
										displayInfo: true
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
						enableTextSelection : true,
						getRowClass : function(record, rowIndex, rp, ds) {
							if (record.get('preArrangeGoodsQty') != record
									.get('stockGoodQty')) {
								return 'predeliver_row_red';
							}
						}
					},
					constructor : function(config) {
						var me = this, cfg = Ext.apply({}, config);
						me.store = Ext
								.create('Foss.predeliver.editToDeliverbill.WaybillToArrangeStore');
						me.selModel = Ext
								.create('Foss.predeliver.editToDeliverbill.CheckboxModel');

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
									rowBodyElement : 'Foss.predeliver.editToDeliverbill.DeliverbillDetailForm'
								} ]
						me.bbar = me.getPagingToolbar();
						me.callParent([ cfg ]);
					}
				});
predeliver.editToDeliverbill.queryIsExsitsWayBillRfcs = function( selectRowGrid, selectWaybillNos) {
	// 传入运单号或者运单号数组集合，判断传入的运单号是否存在未处理的更改单
	Ext.Ajax.request({
		url : predeliver.realPath('queryIsExsitsWayBillRfcs.action'),
		async : false,
		jsonData:{
			'deliverbillVo' : {
				'waybillNos':selectWaybillNos
			}
		},
		success : function(response) {
			var json = Ext.decode(response.responseText);
			var waybillNos = json.deliverbillVo.waybillNos;
			if(waybillNos.length>0){
				var waybillString = '';
				for(var j = 0 ;j<waybillNos.length;j++){
					waybillString +=waybillNos[j]+'<br/>';
				}
				//运单号{0},此单货物状态发生了变更，是否剔除此运单？点击“是”，则从派单列表中剔除，点击“否”
				var confirmMsgBox2 = Ext.Msg.confirm(predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.tip'),
				Ext.String.format(predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.waybillRfc'), waybillString),function(btn1) {
					if (btn1 == 'yes') {
						for ( var i = 0; i < waybillNos.length; i++) {
							predeliver.editToDeliverbill.waybillsToArrange
								.each(function(item, index,length) {
									if (item.waybillNo == waybillNos[i]) {
										predeliver.editToDeliverbill.waybillsToArrange.removeAtKey(item.id);
									}
								});
							}
							if(predeliver.editToDeliverbill.waybillsToArrange.length == 0){
								selectRowGrid.pagingToolbar.moveFirst();
								return;
							}
							Ext.getCmp('T_predeliver-editToDeliverbillIndex_content').getArrangeDeliverbillWindow().getConfirmForm().addWaybillToArrange();
					}else {
						return;
					}
				});
			}else {
				Ext.getCmp('T_predeliver-editToDeliverbillIndex_content').getArrangeDeliverbillWindow().getConfirmForm().addWaybillToArrange();
			}
		}
	});
}
predeliver.editToDeliverbill.addWaybillToArrange =function(id){
	Ext.Ajax.request({
		url : predeliver.realPath('addWaybillToArrange.action'),
		jsonData : {
			'deliverbillVo' : {
				'waybillToArrangeDtoList' : predeliver.editToDeliverbill.waybillsToArrange.items,
				'deliverbill' : {
					'id' : id
				}
			}
		},
		success : function(response) {
			var result = Ext.decode(response.responseText);
			var alertMsg = "";
			var failedList = result.deliverbillVo.waybillToArrangeDtoList;
			for ( var i = 0; i < failedList.length; i++) {
				alertMsg = alertMsg
						+ predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.waybillbr')
						+ "</br>" 
						+ failedList[i].waybillNo
						+ " "
						+ failedList[i].failedToArrangeReason;
			}

			predeliver.editToDeliverbill.arrangeDeliverbillWindow.close();

			// 刷新派送单信息
			Ext.getCmp('T_predeliver-editToDeliverbillIndex_content').refreshDeliverbillInfoForm();
			predeliver.editToDeliverbill.deliverbillDetailGrid.store.load();
			if (alertMsg == "") {
				Ext.ux.Toast.msg(predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.tip'),
						result.message);
			} else {
				Ext.ux.Toast.msg(predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.tip'), alertMsg,
						'error', 4000);
			}
		}
	});
}
// 确认运单form
Ext.define('Foss.predeliver.editToDeliverbill.ConfirmForm',{
	extend : 'Ext.form.Panel',
	frame : false,
	height : 80,
	defaults : {
		margin : '0 45 0 25'
	},
	layout : 'column',
	addWaybillToArrange : function() {
		// 排单前需验证车辆信息
		if (predeliver.editToDeliverbill.deliverbillId == "") {
			var deliverbillBasicInfo = predeliver.editToDeliverbill.deliverbillBasicInfoForm
			.getValues();
			// 保存派送单
			Ext.Ajax.request({
					url : predeliver.realPath('saveDeliverbill.action'),
					jsonData : {
						'deliverbillVo' : {
							'deliverbill' : {
								'id' : predeliver.editToDeliverbill.deliverbillId,
								'deliverbillNo' : deliverbillBasicInfo.deliverbillNo,
								'vehicleNo' : deliverbillBasicInfo.vehicleNo,
								'driverCode' : deliverbillBasicInfo.driverCode,
								'driverName' : predeliver.editToDeliverbill.deliverbillBasicInfoForm.getForm().findField("driverName").rawValue
							}
						}
					},
					success : function(response) {
						var result = Ext.decode(response.responseText);
						predeliver.editToDeliverbill.deliverbillId = result.deliverbillVo.deliverbill.id;
						predeliver.editToDeliverbill.addWaybillToArrange(predeliver.editToDeliverbill.deliverbillId);
				}
			});
		}else {
			predeliver.editToDeliverbill.addWaybillToArrange(predeliver.editToDeliverbill.deliverbillId);
		}
		
	},
	items : [ {
		border : false,
		xtype : 'container',
		columnWidth : 1,
		layout : 'column',
		defaults : {
			margin : '0 0 0 0'
		},
		items : [{
					border : false,
					columnWidth : .92,
					html : '&nbsp;'
				},{
					columnWidth : .08,
					xtype : 'button',
					cls : 'yellow_button',
					text : predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.confirm'), //确认,
					plugins : Ext.create('Deppon.ux.ButtonLimitingPlugin', {
						// 设定间隔秒数,如果不设置，默认为2秒
						seconds : 2
					}),
					handler : function() {
						if (predeliver.editToDeliverbill.waybillsToArrange.length == 0) {
							Ext.ux.Toast.msg(predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.tip'), predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.choseDeliverDetail'),
									'ok', 4000);
							return;
						}
						var waybills = '';
						var selectWaybillNos =new Array();//存放复选框选中的运单
						var selectRowGrid =Ext
								.getCmp(
										'T_predeliver-editToDeliverbillIndex_content')
								.getArrangeDeliverbillWindow()
								.getWaybillToArrangeGrid(),
							selectRowModel = selectRowGrid.getSelectionModel(),
						    selectRow = selectRowModel.getSelection();
						predeliver.editToDeliverbill.waybillsToArrange
								.each(function(item, index,
										length) {
										selectWaybillNos.push(item.waybillNo);//得到复选框选中的运单号集合
									if (item.isAlreadyContact == 'N') {
										waybills += item.waybillNo
												+ '<br/>';
									}
								});
						
						var deliverStore = Ext.getCmp('Foss_predeliver_editToDeliverbill_WaybillToArrangeGrid_Id').store;
						var map = new Ext.util.HashMap(), flg = false;
						for (var i = 0; i < selectRow.length; i++) {
							map.add(selectRow[i].get("waybillNo"), selectRow[i].get("waybillNo"));
						}
						deliverStore.each(function(record, index, allRecords){
							if (Ext.isEmpty(map.get(record.get('waybillNo')))) {
								if (record.get('isSentRequired')=='Y') {
									flg = true;
								}
							}
						});
						if (flg == true) {
							var confirmMsg = Ext.Msg.confirm(predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.tip'), predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.isSentRequired'),function(btn) {
									if (btn == 'yes') {
										if (flag == false) {
											flag = true;
											return;
										}
										if (waybills == '') {
											predeliver.editToDeliverbill.queryIsExsitsWayBillRfcs(selectRowGrid,selectWaybillNos);
										} else {
											var confirmMsgBox = Ext.Msg.confirm(predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.tip'), predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.attention')+ waybills+ predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.noNotice'),function(btn) {
												if (btn == 'yes') {
													predeliver.editToDeliverbill.queryIsExsitsWayBillRfcs(selectRowGrid,selectWaybillNos);
												}
											},predeliver.editToDeliverbill.arrangeDeliverbillWindow);
											confirmMsgBox.getEl().setStyle('z-index', predeliver.editToDeliverbill.arrangeDeliverbillWindow.getEl().getStyle('z-index')+100);
										}
									}
								});
						} else {
							if (flag == false) {
								flag = true;
								return;
							}
							if (waybills == '') {
								predeliver.editToDeliverbill.queryIsExsitsWayBillRfcs(selectRowGrid,selectWaybillNos);
							} else {
								var confirmMsgBox = Ext.Msg.confirm(predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.tip'), predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.attention')+ waybills+ predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.noNotice'),function(btn) {
									if (btn == 'yes') {
										predeliver.editToDeliverbill.queryIsExsitsWayBillRfcs(selectRowGrid,selectWaybillNos);
									}
								},predeliver.editToDeliverbill.arrangeDeliverbillWindow);
								confirmMsgBox.getEl().setStyle('z-index', predeliver.editToDeliverbill.arrangeDeliverbillWindow.getEl().getStyle('z-index')+100);
							}
						}
					}
				} ]
	} ]
});


// 排单窗口
Ext.define('Foss.predeliver.editToDeliverbill.ArrangeDeliverbillWindow',
				{
					extend : 'Ext.window.Window',
					// title : predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.queryGoodsInfo'), //货物信息查询,
					bodyCls : 'autoHeight',
					cls : 'autoHeight',
					//modal : true,
					closeAction : 'hide',
					width : 1100,
					// 查询条件FORM
					queryWaybillForm : null,
					getQueryWaybillForm : function() {
						if (this.queryWaybillForm == null) {
							this.queryWaybillForm = Ext
									.create('Foss.predeliver.editToDeliverbill.QueryWaybillForm');
						}
						return this.queryWaybillForm;
					},
					// 待排单货物信息GRID
					waybillToArrangeGrid : null,
					getWaybillToArrangeGrid : function() {
						if (this.waybillToArrangeGrid == null) {
							this.waybillToArrangeGrid = Ext
									.create('Foss.predeliver.editToDeliverbill.WaybillToArrangeGrid');
						}
						return this.waybillToArrangeGrid;
					},
					// 确认运单FORM
					confirmForm : null,
					getConfirmForm : function() {
						if (this.confirmForm == null) {
							this.confirmForm = Ext
									.create('Foss.predeliver.editToDeliverbill.ConfirmForm');
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
Ext.define('Foss.predeliver.editToDeliverbill.SubmitForm',
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
						if (!predeliver.editToDeliverbill.deliverbillBasicInfoForm
								.getForm().isValid()) {
							Ext.ux.Toast.msg(predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.tip'), predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.choseTheCar'));
							return false;
						}

						return true;
					},
					// 保存/提交时验证
					validateOnSaveOrSubmit : function() {
						// 11a 12a 排单员点击“保存”或“提交”时，如果未有选车辆。 系统提示“请选择车辆”
						if (!predeliver.editToDeliverbill.deliverbillBasicInfoForm
								.getForm().isValid()) {
							Ext.ux.Toast.msg(predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.tip'), predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.choseTheCar'));
							return false;
						}

						// 11b 12b
						// 排单员点击“保存”或“提交”时，如果未有运单货物。系统则提示“待排单货物为空，请选择待排单货物！”
						if (predeliver.editToDeliverbill.deliverbillDetailGrid.store.data
								.getCount() == 0) {
							Ext.ux.Toast.msg(predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.tip'), predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.choseTheGoods'));
							return false;
						}

						return true;
					},
					// 保存/提交派送单
					saveOrSubmit : function(deliverbillStatus, button) {
						// TODO 需要验证车牌号/司机是否有效
						if (!predeliver.editToDeliverbill.submitForm
								.validateOnSaveOrSubmit()) {
							return;
						}

						var deliverbillBasicInfo = predeliver.editToDeliverbill.deliverbillBasicInfoForm
								.getValues();

						// 更新派送单
						Ext.Ajax
								.request({
									url : predeliver
											.realPath('saveDeliverbill.action'),
									jsonData : {
										'deliverbillVo' : {
											'deliverbill' : {
												'id' : predeliver.editToDeliverbill.deliverbillId,
												'deliverbillNo' : deliverbillBasicInfo.deliverbillNo,
												'vehicleNo' : deliverbillBasicInfo.vehicleNo,
												'driverCode' : deliverbillBasicInfo.driverCode,
												'driverName' : predeliver.editToDeliverbill.deliverbillBasicInfoForm
														.getForm().findField(
																"driverName").rawValue,
												'status' : deliverbillStatus
											}
										}
									},
									success : function(response) {

										var deliverbill = predeliver.editToDeliverbill.deliverbillInfoForm
												.getRecord();

										var confirmTitle = deliverbillStatus == 'SAVED' ? predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.saveDeliverSuccess')
												: predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.submitDeliverSuccess');

										var confirmMsg = confirmTitle
												+ predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.theNumberIs')
												+ deliverbillBasicInfo.deliverbillNo
												+ predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.weightTotalbr')
												+ deliverbill
														.get('weightTotal')
												+ predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.volumeTotalbr')
												+ deliverbill
														.get('volumeTotal')
												+ predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.waybillQtyTotalbr')
												+ deliverbill
														.get('waybillQtyTotal')
												+ predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.piecesTotalbr')
												+ deliverbill
														.get('goodsQtyTotal')
												+ predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.payAmountTotalbr')
												+ deliverbill
														.get('payAmountTotal')
												+ predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.loadRatebr')
												+ deliverbill.get('loadRate')
												+ predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.surePrintDeliverBill')

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
																					'deliverbillId' : predeliver.editToDeliverbill.deliverbillId
																				});

																// 获取打印的预排送单基本信息
																Ext.Ajax
																		.request({
																			url : predeliver
																					.realPath('queryDeliverbill.action'),
																			params : {
																				'deliverbillVo.deliverbillDto.id' : predeliver.editToDeliverbill.deliverbillId
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
																						.setDeliverbillId(predeliver.editToDeliverbill.deliverbillId);
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
															'T_predeliver-editToDeliverbillIndex')
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
									text : predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.save'), //保存,
									handler : function(button, e) {
										button.up('form').saveOrSubmit('SAVED',
												button);
									}
								},
								{
									columnWidth : .08,
									xtype : 'button',
									cls : 'yellow_button',
									text : predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.submit'), //提交,
									handler : function(button, e) {
										button.up('form').saveOrSubmit(
												'SUBMITED');
									}
								} ]
					} ]
				});

Ext.onReady(function() {
			Ext.QuickTips.init();

			predeliver.editToDeliverbill.deliverbillBasicInfoForm = Ext
					.create('Foss.predeliver.editToDeliverbill.DeliverbillBasicInfoForm');
			predeliver.editToDeliverbill.deliverbillDetailGrid = Ext
					.create('Foss.predeliver.editToDeliverbill.DeliverbillDetailGrid');
			predeliver.editToDeliverbill.deliverbillInfoForm = Ext
					.create('Foss.predeliver.editToDeliverbill.DeliverbillInfoForm');
			predeliver.editToDeliverbill.submitForm = Ext
					.create('Foss.predeliver.editToDeliverbill.SubmitForm');

			predeliver.editToDeliverbill.arrangeDeliverbillWindow = null;

			// ---- 重量/体积精度
			predeliver.editToDeliverbill.numberPrecision = 2;
			predeliver.editToDeliverbill.waybillsToArrange = new Ext.util.MixedCollection(); // 选中的待排运单集合

			Ext.create('Ext.panel.Panel',
							{id : 'T_predeliver-editToDeliverbillIndex_content',
								cls : "panelContentNToolbar",
								bodyCls : 'panelContentNToolbar-body',
								layout : 'auto',
								refreshVehicleLoadInfo : function(vehicleNo,
										vehicleNoField, oldVehicleNo) {
									if (vehicleNo != '' && vehicleNo != null) {
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

														var driverNameField = predeliver.editToDeliverbill.deliverbillBasicInfoForm
																.getForm()
																.findField(
																		'driverName');

														var vehicle = result.deliverbillVo.vehicleAssociationDto;

														if (vehicle != null) {
															var deliverbillInfo = predeliver.editToDeliverbill.deliverbillInfoForm
																	.getRecord();
															var vehicleLoad = vehicle.vehicleDeadLoad*1000 - deliverbillInfo.get('weightTotal');
															var vehicleVolume =vehicle.vehicleSelfVolume - deliverbillInfo.get('volumeTotal');
															if(vehicleLoad < 0){
																vehicleLoad = 0;
															}
															if(vehicleVolume < 0){
																vehicleVolume = 0;
															}
															// 更新派送单统计信息
															deliverbillInfo
																	.set('vehicleNo',
																			vehicle.vehicleNo);
															deliverbillInfo
																	.set('vehicleType',
																			vehicle.vehicleLengthValue);											
															deliverbillInfo
																	.set('vehicleLoad',vehicleLoad);
															deliverbillInfo
																	.set('vehicleVolume',vehicleVolume);
															// 计算装载率
															var vehicleLoadRate = 0;
															if (vehicle.vehicleDeadLoad == 0||vehicle.vehicleDeadLoad == null) {
																vehicleLoadRate = 0.00;
															} else {
																vehicleLoadRate = deliverbillInfo
																		.get('weightTotal')
																		/ (vehicle.vehicleDeadLoad*1000);
															}
															var vehicleVolumeRate = 0;
															if (vehicle.vehicleSelfVolume == 0||vehicle.vehicleSelfVolume == null) {
																vehicleVolumeRate = 0.00;
															} else {
																vehicleVolumeRate = deliverbillInfo
																		.get('volumeTotal')
																		/ vehicle.vehicleSelfVolume;
															}

															if (vehicleLoadRate > 1) {
																// 10a
																// 点击“确认”时，如果预派送单中货物总体积或总重量超出车辆基础信息中的车辆额定载重或额定净空。系统则弹出“排单总体积或总重量超出车辆额定载重或额定净空”警告对话框。
																Ext.ux.Toast
																		.msg(
																				predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.warningMessage'),
																				predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.arrangedWeightBTLoadWeight'),
																				'error',
																				4000);
															}

															if (vehicleVolumeRate > 1) {
																// 10a
																// 点击“确认”时，如果预派送单中货物总体积或总重量超出车辆基础信息中的车辆额定载重或额定净空。系统则弹出“排单总体积或总重量超出车辆额定载重或额定净空”警告对话框。
																Ext.ux.Toast
																		.msg(
																				predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.warningMessage'),
																				predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.goodsVolumeTotalBTClearance'),
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

																predeliver.editToDeliverbill.deliverbillBasicInfoForm
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

															predeliver.editToDeliverbill.deliverbillInfoForm
																	.loadRecord(deliverbillInfo);
														} else {
															Ext.ux.Toast.msg(
																	predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.tip'),
																	predeliver.editToDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.vehicleNotExist'),
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
									if (predeliver.editToDeliverbill.deliverbillId != '') {
										// 若派送单已保存，则刷新统计信息和车载信息
										Ext.Ajax.request({
													url : predeliver
															.realPath('queryDeliverbill.action'),
													params : {
														'deliverbillVo.deliverbillDto.id' : predeliver.editToDeliverbill.deliverbillId
													},
													success : function(response) {
														var result = Ext.decode(response.responseText);
														var deliverbill = Ext.ModelManager.create(
																		result.deliverbillVo.deliverbill,
																		'Foss.predeliver.editToDeliverbill.DeliverbillModel');

														predeliver.editToDeliverbill.deliverbillBasicInfoForm
																.loadRecord(deliverbill);
														predeliver.editToDeliverbill.deliverbillInfoForm
																.loadRecord(deliverbill);

														Ext.getCmp('T_predeliver-editToDeliverbillIndex_content')
																.refreshVehicleLoadInfo(deliverbill.get('vehicleNo'));
													}
												});
									}
								},
								getArrangeDeliverbillWindow : function() {
									if (predeliver.editToDeliverbill.arrangeDeliverbillWindow == null) {
										predeliver.editToDeliverbill.arrangeDeliverbillWindow = Ext
										.create('Foss.predeliver.editToDeliverbill.ArrangeDeliverbillWindow');
									}

									return predeliver.editToDeliverbill.arrangeDeliverbillWindow;
								},
								items : [
										predeliver.editToDeliverbill.deliverbillBasicInfoForm,
										predeliver.editToDeliverbill.deliverbillDetailGrid,
										predeliver.editToDeliverbill.deliverbillInfoForm,
										predeliver.editToDeliverbill.submitForm ],
								renderTo : 'T_predeliver-editToDeliverbillIndex-body'
							});

			Ext.getCmp('T_predeliver-editToDeliverbillIndex_content').refreshDeliverbillInfoForm();
		});
