/**
 * @author ibm-wangxiexu page 新增/编辑派送单
 */
// 定义数据字典model
predeliver.editDeliverbill.getDate = function(date, hours, minutes, seconds,
		milliseconds) {
	date.setHours(hours);
	date.setMinutes(minutes);
	date.setSeconds(seconds);
	date.setMilliseconds(milliseconds);

	return date;
};

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

//提货方式集合
Ext.define('Foss.predeliver.store.ReceiveMethodStore',{
	extend: 'Ext.data.Store',
	fields: [
		{name: 'valueCode',  type: 'string'},
		{name: 'valueName',  type: 'string'}
	],
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
			{'valueCode':'NOMAL','valueName':predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.nomal')},// '正常'
			{'valueCode':'SPECIAL','valueName':predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.special')},// '专车'
			{'valueCode':'MANNED','valueName':predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.manned')}//'带人'
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
var dept = FossUserContext.getCurrentDept().salesDepartment;
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
				Ext.ux.Toast.msg(predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.tip'), result.message, 'error', 3000);
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
				Ext.ux.Toast.msg(predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.tip'), result.message, 'error', 3000);
			}
		})
})();

(function() {
	// 获得当前部门所在省市
	Ext.Ajax.request({
		url : predeliver.realPath('queryProv.action'),
		async : false,
		success : function(response) {
			var json = Ext.decode(response.responseText);
			Ext.apply(predeliver, {
				provName : json.provName,
				cityName : json.cityName
			});
		},
		exception : function(response) {
			var result = Ext.decode(response.responseText);
			Ext.ux.Toast.msg(predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.tip'), result.message, 'error', 3000);
		}
	})
})();

var deptCode = FossUserContext.getCurrentDept().code;
var deptName = FossUserContext.getCurrentDept().name;

var provCode = FossUserContext. getCurrentDept().provCode;
var cityCode = FossUserContext. getCurrentDept().cityCode;
predeliver.editDeliverbill.vehicleNoTruckType = null;


// 基础信息Form
Ext.define('Foss.predeliver.editDeliverbill.DeliverbillBasicInfoForm',
				{
				    title : predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.vehicleDriver'), //车辆司机信息,
					extend : 'Ext.form.Panel',
					frame : true,
					height:110,
					collapsible : true,
					animCollapse : true,
					defaults : {
						margin : '1 15 1 15', //上右下左
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
								name : 'status',
								xtype : 'hidden',
								value : predeliver.editDeliverbill.status
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
								name : 'vehicleType',
								fieldLabel : predeliver.editDeliverbill
										.i18n('foss.pickup.predeliver.vehicleType'), // 车辆组别
								columnWidth : .33,
								xtype : 'commonmotorcadeselector',
								loopOrgCodes : predeliver.fleetCode,
								listeners : {
									'change' : function(field, event, eOpts) {
										var form = field.up('form').getForm(),
											vehicleNo = form.findField('vehicleNo');
											driverName = form.findField('driverName');
										if (field.value != '' && field.value != null) {
											vehicleNo.setReadOnly(false);
											vehicleNo.getStore().un('beforeload');
											vehicleNo.getStore().on('beforeload', function(store,operation,eOpts) {
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
											driverName.setValue('');
						
										}
									}
								}
							},
							{
								name : 'vehicleNo',
								fieldLabel : predeliver.editDeliverbill.i18n('foss.pickup.predeliver.vehicleNo'), // 车辆
								columnWidth : .34,
								xtype : 'commontruckselector', 
								allowBlank : false,
								queryAllFlag : true,
								loopOrgCode : predeliver.fleetCode,
 								listeners : {
 									
 									'select': function(field, records, eOpts) {
										var record = records[0];
										predeliver.editDeliverbill.vehicleNoTruckType = record.get('truckType');
 									},
 									
									'blur' : function(field, event, eOpts) {
										if (field.value != '') {
											if (predeliver.editDeliverbill.deliverbillId != '') {
												// 车牌号不为空，且派送单已保存时，刷新派送单统计信息
												var deliverbillInfo = predeliver.editDeliverbill.deliverbillInfoForm
														.getRecord();
												var oldVehicleNo = deliverbillInfo
														.get('vehicleNo');

												if (field.value != oldVehicleNo) {
													Ext.getCmp('T_predeliver-editDeliverbillIndex_content')
															 	.refreshVehicleLoadInfo(
																	field.value,
																	field,
																	oldVehicleNo);
												}
											}else{
												Ext.getCmp('T_predeliver-editDeliverbillIndex_content').getDriverName(field.value);
											}
										}
									} 
								}
							},
							{
								name : 'driverName',
								fieldLabel : predeliver.editDeliverbill.i18n('foss.pickup.predeliver.driverName'), // 司机姓名
								columnWidth : .33,
								xtype : 'commondriverselector',								
								waybillFlag:'N',//是否是集中接送区域  如果设置值为Y,N
								fleetType:'FLEET_TYPE__SHUTTLE,FLEET_TYPE__SHUTTLE_GOODS,FLEET_TYPE__LONG_DISTANCE',//车队组类型(包含集中接送货和物流班车)
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
								xtype : 'combobox',
								name : 'deliverType',
								fieldLabel : predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.deliveryType'), //派车类型,
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
Ext.define('Foss.predeliver.editDeliverbill.DeliverbillDetailModel',
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
								name : 'deliverDate', // 送货日期
								type : 'date',
								//convert : dateConvert
								convert:function(value){
									 if(value!=null){
										 var date = new Date(value);
										 var dd =Ext.Date.format(date,'Y-m-d H:i:s');
										 var deliverTime ='1900-01-01 00:00:00';
										 if(dd== deliverTime ){
											return null;
										 }
										 return date;
									 }else{
										 return null;
									 }
								}
							},{
								name : 'consignee', // 收货人
								type : 'string'
							},{
								name : 'receiveBigCustomer', // 收货人大客户标记
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
							} , {
								name : 'deliverRegionName', // 送货小区
								type : 'string'
							} , {
								name : 'deliverSuggest', // 带人建议
								type : 'string'
							}, {
								name : 'togetherSendCode', // 客户编码
								type : 'string'
							}  ]
				});

// 定义预派送单明细的Store
Ext.define('Foss.predeliver.editDeliverbill.DeliverbillDetailStore',
				{
					extend : 'Ext.data.Store',
					autoLoad : true,
					// 绑定一个模型
					model : 'Foss.predeliver.editDeliverbill.DeliverbillDetailModel',
					pageSize : 50,
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
	items : [
			{
				name : 'waybillNo',
				fieldLabel : predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.waybillNo'), //运单号,
				columnWidth : .33,
				readOnly : true
			},
			{
				name : 'goodsName',
				fieldLabel : predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.goodsName'), //货物名称,
				columnWidth : .33,
				readOnly : true
			},
			{
				name : 'arrangeGoodsQty',
				fieldLabel : predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.arrangeGoodsQty'), //排单件数,
				columnWidth : .33,
				readOnly : true
			},
			{
				name : 'arrangedWeight',
				fieldLabel : predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.weight'), //排单重量,
				columnWidth : .33,
				readOnly : true
			},
			{
				name : 'transportType',
				fieldLabel : predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.transportType'), //运输方式,
				columnWidth : .33,
				readOnly : true,
				setValue : function(value) {
					this.setRawValue(Foss.pkp.ProductData.rendererSubmitToDisplay(value));
				}
			},
			{
				name : 'dimension',
				fieldLabel : predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.goodsSize'), //尺寸,
				columnWidth : .33,
				readOnly : true
			},
			{
				name : 'consignee',
				fieldLabel : predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.consignee'), //收货人,
				columnWidth : .33,
				readOnly : true
			},
			{
				name : 'consigneeContact',
				fieldLabel : predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.consigneeContact'), //联系方式,
				columnWidth : .33,
				readOnly : true
			},
			{
				name : 'consigneeAddress',
				fieldLabel : predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.consigneeAddress'), //送货地址,
				columnWidth : .33,
				readOnly : true
			},
			{
				name : 'deliverRequire',
				fieldLabel : predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.deliverRequire'), //注意事项,
				columnWidth : .33,
				readOnly : true
			},
			{
				name : 'goodsStatus',
				fieldLabel : predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.goodsStatus'), //货物状态,
				columnWidth : .33,
				readOnly : true
			},
			{
				name : 'isAlreadyContact',
				fieldLabel : predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.isAlreadyContact'), //是否已联系客户,
				columnWidth : .33,
				readOnly : true,
				setValue : function(value) {
					if (value == 'Y') {
						this.setRawValue(predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.yes'));
					} else {
						this.setRawValue(predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.no'));
					}
				}
			},
			{
				name : 'estimatedDeliverTime',
				fieldLabel : predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.estimatedDeliverTime'), //预计送货时间,
				columnWidth : .33,
				readOnly : true,
				xtype : 'datefield',
				format : 'Y-m-d H:i:s'
			},
			{
				name : 'isException',
				fieldLabel : predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.isException'), //是否异常,
				columnWidth : .33,
				readOnly : true,
				setValue : function(value) {
					if (value == 'Y') {
						this.setRawValue(predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.yes'));
					} else {
						this.setRawValue(predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.no'));
					}
				}
			},
			{
				name : 'isNeedInvoice',
				fieldLabel : predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.isNeedInvoice'), //是否需要发票,
				columnWidth : .33,
				readOnly : true,
				setValue : function(value) {
					if (value == 'Y') {
						this.setRawValue(predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.yes'));
					} else {
						this.setRawValue(predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.no'));
					}
				}
			},
			{
				name : 'paymentType',
				fieldLabel : predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.paymentType'), //付款方式,
				columnWidth : .33,
				readOnly : true,
				setValue : function(value) {
					this.setRawValue(FossDataDictionary
							.rendererSubmitToDisplay(value, 'SETTLEMENT__PAYMENT_TYPE'));
				}
			},
			{
				name : 'notes',
				fieldLabel : predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.notes'), //备注,
				columnWidth : .33,
				readOnly : true
			},
			{
				name : 'deliverType',
				fieldLabel : predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.deliverType'), //送货方式,
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
Ext.define('Foss.predeliver.editDeliverbill.DeliverbillDetailGrid',
				{
					extend : 'Ext.grid.Panel',
					frame : true,
					title : predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.deliverbillDetailInfo'), // 待排单信息
					//emptyText : predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.emptyText'), //查询结果为空,
					//bodyCls : 'autoHeight',
					//cls : 'autoHeight',
					autoScroll : true,
					collapsible : true,
					animCollapse : true,
					dockedItems : [ {
						xtype : 'toolbar',
						dock : 'top',
						layout : 'column',
						items : [
								// {
									// border : 1,
									// xtype : 'button',
									// columnWidth : .08,
									// text : predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.remove'), //移除,
									// handler : function() {
										// var selectRow = predeliver.editDeliverbill.deliverbillDetailGrid
												// .getSelectionModel()
												// .getSelection();

										// if (selectRow.length == 0) {
											// Ext.ux.Toast.msg(predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.tip'),
													// predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.choseRemoveDetail'));
											// return;
										// }

										// Ext.Msg.confirm(predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.tip'),
														// predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.sureRemoveDetail'),
														// function(btn, text) {
															// if (btn == "yes") {
																// var ids = '';
																// for ( var i = 0; i < selectRow.length; i++) {
																	// ids = selectRow[i].data.id
																			// + ","
																			// + ids;
																// }

																// predeliver.editDeliverbill.deliverbillDetailGrid
																		// .deleteDeliverbillDetails(ids);
															// }
														// })
									// }
								// },
								{
									xtype : 'image',
									margin : '0 0 0 0',
									imgCls : 'foss_icons_pkp_flaggreen'
								},
								{
									xtype : 'label',
									margin : '0 0 0 10',
									text : predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.waybillGoodsQtyNotPreArrangeGoodsQty') //排单货物的件数与开单的件数不同
								},
								{
									xtype : 'image',
									margin : '0 0 0 30',
									imgCls : 'foss_icons_pkp_goodsNumDisac'
								},
								{
									xtype : 'label',
									margin : '0 0 0 10',
									text : predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.preArrangeGoodsQtyNotStockGoodQty') //排单件数与库存件数不同
								}]
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
								text : predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.operate'), //操作,
								align : 'center',
								items : [
										{
											iconCls : 'deppon_icons_up',
											tooltip : predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.moveUp'), //上移,
											handler : function(grid, rowIndex,
													colIndex) {
												// alert(grid.getStore().getAt(rowIndex).get('id'));
												if (grid.getStore().getAt(
														rowIndex).get(
														'serialNo') == 1) {
													Ext.ux.Toast.msg(predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.tip'),
															predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.theFirst'),
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
													Ext.ux.Toast.msg(predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.tip'),
															predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.theFirst'),
															'error', 4000);
													return;
												}
											}
										},
										{
											iconCls : 'deppon_icons_down',
											tooltip : predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.down'), //下移,
											handler : function(grid, rowIndex,
													colIndex) {
												// alert(grid.getStore().getAt(rowIndex).get('id'));
												if (grid.getStore().getAt(
														rowIndex).get(
														'serialNo') == grid
														.getStore()
														.getTotalCount()) {
													Ext.ux.Toast.msg(predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.tip'),
															predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.theEnd'),
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
											tooltip : predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.remove'), //移除,
											handler : function(grid, rowIndex,
													colIndex) {
												Ext.Msg
														.confirm(
																predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.tip'),
																predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.sureRemoveThisDetail'),
																function(btn,
																		text) {
																	if (btn == "yes") {
																		predeliver.editDeliverbill.deliverbillDetailGrid
																				.deleteDeliverbillDetails(grid.getStore().getAt(rowIndex).get('id'));
																	}
																});
											}
										} ]
							},
							{
								dataIndex : 'waybillNo',
								align : 'center',
								width : 80,
								header : predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.waybillNo') //运单号
							},
							{
								dataIndex : 'goodsName',
								align : 'center',
								width : 80,
								header : predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.goodsName') //货物名称
							},
							{
								dataIndex : 'weight',
								align : 'center',
								flex : 1,
								header : predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.weight') //排单重量
							},
							{
								dataIndex : 'dimension',
								align : 'center',
								flex : 1,
								header : predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.goodsSize') //尺寸
							},
							{
								dataIndex : 'goodsVolumeTotal',
								align : 'center',
								flex : 1,
								header : predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.dimension') //体积
							},
							{
								dataIndex : 'goodsPackage',
								align : 'center',
								flex : 1,
								header : predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.goodsPackage') //包装
							},
							{
								dataIndex : 'waybillGoodsQty',
								align : 'center',
								flex : 1,
								header : predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.pieces') //件数
							},
							{
								dataIndex : 'preArrangeGoodsQty',
								align : 'center',
								flex : 1,
								header : predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.arrangeGoodsQty') //排单件数
							},
							{
								dataIndex : 'transportType',
								align : 'center',
								flex : 1,
								header : predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.transportType'), //运输方式,
								renderer : function(value) {
									return Foss.pkp.ProductData.rendererSubmitToDisplay(value);
								}
							}, {
								dataIndex : 'arriveTime',
								align : 'center',
								flex : 1,
								header : predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.arriveTime'), //到达时间,
								xtype : 'datecolumn',
								format : 'Y-m-d H:i:s'
							}, {
								dataIndex : 'consignee',
								align : 'center',
								flex : 1,
								header : predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.consignee') //收货人
								,width : 80,
								renderer : function(value, cellmeta, record, rowIndex, columnIndex, store){
									//标示大客户
								  	if(record.data.receiveBigCustomer == 'Y'){
								  		value = '<div class="big_Customer_pic_common"></div>' + value  ;
								  	}
								  	return value;
								}
							}, {
								dataIndex : 'consigneeContact',
								align : 'center',
								flex : 1,
								header : predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.consigneeContact') //联系方式
							}, {
								dataIndex : 'consigneeAddress',
								align : 'center',
								flex : 1,
								header : predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.consigneeAddress') //送货地址
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
								'T_predeliver-editDeliverbillIndex_content')
								.getArrangeDeliverbillWindow();
						// 初始化界面
						arrangeDeliverbillWindow.getWaybillToArrangeGrid().store
								.removeAll();
						arrangeDeliverbillWindow.getWaybillToArrangeGrid()
								.getPagingToolbar().hide();
						Ext.getCmp('receiveCustomer_Prov').setReginValue(predeliver.provName, provCode);	
						Ext.getCmp('receiveCustomer_City').setReginValue(predeliver.cityName, cityCode);	
						Ext.getCmp('Foss_editDeliverbill_QueryGoodsGrid_totalWeight_ID').setValue('');
						Ext.getCmp('Foss_editDeliverbill_QueryGoodsGrid_totalVolume_ID').setValue('');
						Ext.getCmp('Foss_editDeliverbill_QueryGoodsGrid_totalQty_ID').setValue('');
							
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
										predeliver.editDeliverbill.waybillToArrangeGrid.store.load();
										Ext.getCmp('T_predeliver-editDeliverbillIndex_content')
												.refreshVehicleLoadInfo(deliverbill.get('vehicleNo'));
									},
						    		exception: function(response){
										var json = Ext.decode(response.responseText);
				              			Ext.ux.Toast.msg(predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.tip'), json.message, 'error', 3000);
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
								.create('Foss.predeliver.editDeliverbill.DeliverbillDetailStore');
						me.selModel = Ext.create('Ext.selection.CheckboxModel');

						me.bbar = me.getPagingToolbar();
						me.callParent([ cfg ]);
					}
				});

// 统计信息Form
Ext.define('Foss.predeliver.editDeliverbill.DeliverbillInfoForm', {
	extend : 'Ext.form.Panel',
	title : predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.statistics'), //统计信息,
	frame : true,
	collapsible : true,
	animCollapse : true,
	height:200,
	defaults : {
		margin : '5 15 5 15',
		labelWidth : 120
	},
	defaultType : 'textfield',
	layout : 'column',
	items : [ {
		name : 'vehicleNo',
		fieldLabel : predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.vehicleNo'), //车辆,
		columnWidth : .33,
		readOnly : true
	}, {
		name : 'vehicleType',
		fieldLabel : predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.vehicleType'), //车型,
		columnWidth : .33,
		readOnly : true
	}, {
		name : 'driverName',
		fieldLabel : predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.driver'), //司机,
		columnWidth : .33,
		readOnly : true
	}, {
		name : 'deliverbillNo',
		fieldLabel : predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.deliverbillNo'), //对应预派送单,
		columnWidth : .33,
		value : predeliver.editDeliverbill.deliverbillNo,
		readOnly : true
	}, {
		name : 'weightTotal',
		fieldLabel : predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.weightTotal'), //总重量(公斤),
		columnWidth : .33,
		readOnly : true
	}, {
		name : 'volumeTotal',
		fieldLabel : predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.volumeTotal'), //总体积(方),
		columnWidth : .33,
		readOnly : true
	}, {
		name : 'vehicleLoad',
		fieldLabel : predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.vehicleLoad'), //可载重量(公斤),
		columnWidth : .33,
		readOnly : true
	}, {
		name : 'vehicleVolume',
		fieldLabel : predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.vehicleVolume'), //可载体积(方),
		columnWidth : .33,
		readOnly : true
	}, {
		name : 'loadRate',
		fieldLabel : predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.loadRate'),//装载率(容量/体积)
		columnWidth : .33,
		readOnly : true
	}, {
		name : 'waybillQtyTotal',
		fieldLabel : predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.waybillQtyTotal'), //总票数,
		columnWidth : .33,
		readOnly : true
	}, {
		name : 'goodsQtyTotal',
		fieldLabel : predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.arriveSheetGoodsQty'), //总件数,
		columnWidth : .33,
		readOnly : true
	}, {
		name : 'payAmountTotal',
		fieldLabel : predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.toPayAmount'), //到付金额,
		columnWidth : .33,
		readOnly : true
	} ]
});

// 查询运单form
Ext.define('Foss.predeliver.editDeliverbill.QueryWaybillForm',
				{
					title : predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.queryGoodsInfo'), //货物信息查询,
					extend : 'Ext.form.Panel',
					id : 'Foss_predeliver_editDeliverbill_QueryWaybillForm_id',
					frame : true,
					collapsible : true,
					animCollapse : true,
					bodyCls : 'autoHeight',
					cls : 'autoHeight',
					defaults : {
						margin : '1 10 1 10',
						labelWidth : 80
					},
					defaultType : 'textfield',
					layout : 'column',
					items : [
					        {
					    		name : 'waybillNos',
					    		labelWidth : 50,
					    		xtype : 'textarea',
					    		fieldLabel : predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.waybillNo'), //运单号,
					    		columnWidth : .25,
					    		emptyText : predeliver.editDeliverbill.i18n('pkp.predeliver.notifyCustomer.waybillNo.valitation'),
					    		regex : /^([0-9]{8,9}\n?)+$/i,
					    		regexText : predeliver.editDeliverbill.i18n('pkp.predeliver.notifyCustomer.waybillNo.valitation')
					    	
					        },
							{
								name : 'receiveCustomerName',
								fieldLabel : predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.consigneeName'), //收货人名称,
								columnWidth : .25
							},
							{
								name : 'receiveCustomerPhone',
								fieldLabel : predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.receiveCustomerPhone'), //收货人电话,
								columnWidth : .25
							},
							{
								xtype : 'combobox',
								name : 'receiveMethod',
								fieldLabel : predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.deliverType'), //送货方式,
								columnWidth : .25,
								displayField : 'valueName',
								valueField : 'valueCode',
								queryMode : 'local',
								triggerAction : 'all',
								editable : false,
								value : '',
								store : Ext.create('Foss.predeliver.store.ReceiveMethodStore',
								{
									data: {
										'items':[
											{'valueCode':'','valueName':predeliver.editDeliverbill.i18n('pkp.predeliver.notifyCustomer.complete')},// '全部'
											{'valueCode':'DELIVER','valueName':predeliver.editDeliverbill.i18n('pkp.predeliver.notifyCustomer.deliver')},// '送货'
											{'valueCode':'PICKUP','valueName':predeliver.editDeliverbill.i18n('pkp.predeliver.notifyCustomer.pickup')}//'自提'
										]
									}
								})
							},
							{
								xtype : 'rangeDateField',
								fieldId : 'predeliver_editDeliverbill_arriveTime',
								fieldLabel :predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.arriveTime'),   // 到达时间
								dateType : 'datetimefield_date97',
								fromName : 'arriveTimeBegin',
								toName : 'arriveTimeEnd',
								fromValue : Ext.Date.format(predeliver.editDeliverbill.getDate(
												new Date(), 0, 0, 0, 0), 'Y-m-d H:i:s'),
								toValue : Ext.Date.format(predeliver.editDeliverbill.getDate(
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
								fieldLabel: predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.deliverTime'), //预计送货时间,	
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
								fieldLabel : predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.prov'),//省
								id : 'receiveCustomer_Prov',
								name : 'receiveCustomerProv',
								xtype : 'commonreginbyconditionselector',
								degree: 'DISTRICT_PROVINCE',
								columnWidth : .25,
								listeners : {
									'change' : function(field, newValue, oldValue, eOpts) {
										var form = field.up('form').getForm(),
										receiveCustomerCity = form.findField('receiveCustomerCity');
										if (!Ext.isEmpty(newValue)) {
											receiveCustomerCity.getStore().un('beforeload');
											receiveCustomerCity.getStore().on('beforeload', function(store,operation,eOpts) {
												var searchParams = operation.params;
												if (Ext.isEmpty(searchParams)) {
													searchParams = {};
													Ext.apply(operation, {
														params : searchParams
													});
												}
												searchParams['cityVo.parentId'] = newValue;
											});
											receiveCustomerCity.getStore().load();
											receiveCustomerCity.expand();
										}
										else
										{
											receiveCustomerCity.setValue('');
										}
									}
								}
							},
							{
								fieldLabel : predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.city'),//市
								id : 'receiveCustomer_City',
								name : 'receiveCustomerCity',
								xtype : 'commonreginbyconditionselector',
								degree: 'CITY',
								columnWidth : .25,
								listeners : {
									'change' : function(field, newValue, oldValue, eOpts) {
										var form = field.up('form').getForm(),
										receiveCustomerDistCode = form.findField('receiveCustomerDistCode');
										if (!Ext.isEmpty(newValue)) {
											receiveCustomerDistCode.getStore().un('beforeload');
											receiveCustomerDistCode.getStore().on('beforeload', function(store,operation,eOpts) {
												var searchParams = operation.params;
												if (Ext.isEmpty(searchParams)) {
													searchParams = {};
													Ext.apply(operation, {
														params : searchParams
													});
												}
												searchParams['cityVo.parentId'] = newValue;
											});
											receiveCustomerDistCode.getStore().load();
											receiveCustomerDistCode.expand();
										}
										else
										{
											receiveCustomerDistCode.setValue('');
										}
									}
								}
							},
							{
								id : 'receiveCustomerDist_Code',
								name : 'receiveCustomerDistCode',
								fieldLabel : predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.administrativeRegions'),//行政区域
								xtype : 'commonareaselector',
								columnWidth : .25
							},
							{
								name : 'deliverRegionCode',
								xtype : 'commonmultismallzoneselector',
								regionType : 'DE',
								parentOrgCodes : predeliver.fleetCode,
								fieldLabel : predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.regionVehicleCode'), //定人定区,
								columnWidth : .25
							},
							{
								xtype : 'combobox',
								name : 'noticeMethod',
								fieldLabel : predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.isAlreadyNotice'), //是否通知,
								columnWidth : .25,
								displayField : 'valueName',
								valueField : 'valueCode',
								queryMode : 'local',
								triggerAction : 'all',
								editable : false,
								value : '',
								store : Ext.create('Foss.predeliver.store.ReceiveMethodStore',
								{
									data: {
										'items':[
											{'valueCode':'','valueName':predeliver.editDeliverbill.i18n('pkp.predeliver.notifyCustomer.complete')},// '全部'
											{'valueCode':'SUCCESS','valueName':predeliver.editDeliverbill.i18n('pkp.predeliver.exceptionProcess.noticeSuccess')},// '通知成功'
											{'valueCode':'FAILURE','valueName':predeliver.editDeliverbill.i18n('pkp.predeliver.notifyCustomer.noNoticing')}//'未通知'
										]
									}
								})
							},{
								xtype : 'checkboxfield',
								name : 'isStoring',
								inputValue : 'Y',
								uncheckedValue: 'N',
								columnWidth : 0.25,
								boxLabel : '是否已到库位'
							},{
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
												this.up('form').getForm().reset();
												Ext.getCmp('receiveCustomer_Prov').setReginValue(predeliver.provName, provCode);	
												Ext.getCmp('receiveCustomer_City').setReginValue(predeliver.cityName, cityCode);
												this.up('form').getForm().setValues({
													'arriveTimeBegin' : Ext.Date.format(
															predeliver.editDeliverbill.getDate(new Date(),
																	0, 0, 0, 0), 'Y-m-d H:i:s'),
													'arriveTimeEnd' : Ext.Date.format(
															predeliver.editDeliverbill.getDate(new Date(),
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
											text : predeliver.editDeliverbill.i18n('foss.pickup.predeliver.queryButton'), // 查询
											disabled:!predeliver.editDeliverbill.isPermission('editdeliverbillindex/editdeliverbillindexquerybutton'),
											hidden:!predeliver.editDeliverbill.isPermission('editdeliverbillindex/editdeliverbillindexquerybutton'),
											handler : function() {											
												var form = this.up('form').getForm(),waybillNos = form.getValues().waybillNos;
												Ext.getCmp('deliverbill_query_bywaybillNumber').reset();
												// 验证运单号输入的行数
												if (!Ext.isEmpty(waybillNos)) {
													var arrayWaybillNo = waybillNos.split('\n');
													if (arrayWaybillNo.length > 50) {
														Ext.ux.Toast.msg(predeliver.editDeliverbill.i18n('pkp.predeliver.notifyCustomer.tip'), predeliver.editDeliverbill.i18n('pkp.predeliver.notifyCustomer.waybillNo.valitation'), 'error', 3000); 
														return;	
													}
													for (var i = 0; i < arrayWaybillNo.length; i++) {
														if (Ext.isEmpty(arrayWaybillNo[i])) {
															Ext.ux.Toast.msg(predeliver.editDeliverbill.i18n('pkp.predeliver.notifyCustomer.tip'), predeliver.editDeliverbill.i18n('pkp.predeliver.notifyCustomer.waybillNo.valitation'), 'error', 3000); 
															return;	
														}
													}
												}
												
												var arriveTimeBegin = form.getValues().arriveTimeBegin;
												var arriveTimeEnd = form.getValues().arriveTimeEnd;
												var result = Ext.Date.parse(arriveTimeEnd,'Y-m-d H:i:s') - Ext.Date.parse(arriveTimeBegin,'Y-m-d H:i:s');
												if(result / (24 * 60 * 60 * 1000) >= 7){
													Ext.ux.Toast.msg(predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.tip'), predeliver.editDeliverbill.i18n('pkp.predeliver.queryGoods.dateintervalError'), 'error', 3000);
													return;
												}
												if(!form.isValid())
												{
													Ext.ux.Toast.msg(predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.tip'), predeliver.editDeliverbill.i18n('pkp.predeliver.queryGoods.checkQueryInformation'), 'error', 3000);
													return;
												}
												var pagingToolbar = predeliver.editDeliverbill.waybillToArrangeGrid
														.getPagingToolbar();

												pagingToolbar.show();
												pagingToolbar.moveFirst();
											}
										} ]
							} ],
							listeners : { 
								'render' : function(_this, eOpts){
									_this.getForm().findField('waybillNos').setHeight(120);
								}
							}
				});

// 定义待排单货物信息的Store
Ext.define('Foss.predeliver.editDeliverbill.WaybillToArrangeStore',
				{
					extend : 'Ext.data.Store',
					autoLoad : false,
					// 绑定一个模型
					model : 'Foss.predeliver.editDeliverbill.DeliverbillDetailModel',
					pageSize : 50,
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
							var queryWaybillForm = predeliver.editDeliverbill.queryWaybillForm;
							
							var form = Ext.getCmp('Foss_predeliver_editDeliverbill_QueryWaybillForm_id').getForm(),waybillNos = form.getValues().waybillNos;
							Ext.getCmp('deliverbill_query_bywaybillNumber').reset();
							// 验证运单号输入的行数
							if (!Ext.isEmpty(waybillNos)) {
								var arrayWaybillNo = waybillNos.split('\n');
								if (arrayWaybillNo.length > 50) {
									Ext.ux.Toast.msg(predeliver.editDeliverbill.i18n('pkp.predeliver.notifyCustomer.tip'), predeliver.editDeliverbill.i18n('pkp.predeliver.notifyCustomer.waybillNo.valitation'), 'error', 3000); 
									return false;	
								}
								for (var i = 0; i < arrayWaybillNo.length; i++) {
									if (Ext.isEmpty(arrayWaybillNo[i])) {
										Ext.ux.Toast.msg(predeliver.editDeliverbill.i18n('pkp.predeliver.notifyCustomer.tip'), predeliver.editDeliverbill.i18n('pkp.predeliver.notifyCustomer.waybillNo.valitation'), 'error', 3000); 
										return false;	
									}
								}
							}
							
							var arriveTimeBegin = form.getValues().arriveTimeBegin;
							var arriveTimeEnd = form.getValues().arriveTimeEnd;
							var result = Ext.Date.parse(arriveTimeEnd,'Y-m-d H:i:s') - Ext.Date.parse(arriveTimeBegin,'Y-m-d H:i:s');
							if(result / (24 * 60 * 60 * 1000) >= 7){
								Ext.ux.Toast.msg(predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.tip'), predeliver.editDeliverbill.i18n('pkp.predeliver.queryGoods.dateintervalError'), 'error', 3000);
								return false;
							}
							if(!form.isValid())
							{
								Ext.ux.Toast.msg(predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.tip'), predeliver.editDeliverbill.i18n('pkp.predeliver.queryGoods.checkQueryInformation'), 'error', 3000);
								return false;
							}
							

							if (queryWaybillForm != null) {
								var queryParams = queryWaybillForm.getValues();
								
								var params = {
										'deliverbillVo.waybillToArrangeDto.waybillNo' : queryParams.waybillNos,
										'deliverbillVo.waybillToArrangeDto.receiveMethod' : queryParams.receiveMethod,
										'deliverbillVo.waybillToArrangeDto.receiveCustomerName' : queryParams.receiveCustomerName,
										'deliverbillVo.waybillToArrangeDto.receiveCustomerPhone' : queryParams.receiveCustomerPhone,
										'deliverbillVo.waybillToArrangeDto.arriveTimeBegin' : queryParams.arriveTimeBegin,
										'deliverbillVo.waybillToArrangeDto.arriveTimeEnd' : queryParams.arriveTimeEnd,
										'deliverbillVo.waybillToArrangeDto.deliverTime' : queryParams.deliverTime,
										'deliverbillVo.waybillToArrangeDto.isNotification' : queryParams.noticeMethod,
										'deliverbillVo.waybillToArrangeDto.isStoring' : queryParams.isStoring
									};
									var districtList = queryWaybillForm.getForm().findField('receiveCustomerDistCode').getValue();
									for(var i=0;i<districtList.length;i++){
										params['deliverbillVo.waybillToArrangeDto.districtList['+i+']'] = districtList[i];
									};
									
									var deliverRegionList = queryWaybillForm.getForm().findField('deliverRegionCode').getValue();
									for(var i=0;i<deliverRegionList.length;i++){
										params['deliverbillVo.waybillToArrangeDto.deliverRegionList['+i+']'] = deliverRegionList[i];
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
								Ext.ux.Toast.msg(predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.tip'), data.message, 'error', 2000);	//提示			
								return;
							}

							
							var selectedRecords = new Array();

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
						},		
						load: function( store, records, successful, eOpts ){
							var data = store.getProxy().getReader().rawData;
							if(store.currentPage==1)
							{
								Ext.getCmp('Foss_editDeliverbill_QueryGoodsGrid_totalWeight_ID').setValue(data.deliverbillVo.deliverbillDto.totalGoodsWeight);
								Ext.getCmp('Foss_editDeliverbill_QueryGoodsGrid_totalVolume_ID').setValue(data.deliverbillVo.deliverbillDto.totalGoodsVolume);
								Ext.getCmp('Foss_editDeliverbill_QueryGoodsGrid_totalQty_ID').setValue(data.deliverbillVo.deliverbillDto.totalGoodsQty);
							}
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


Ext.define('Foss.predeliver.ButtonPanelRole',{
  		extend :'Ext.form.Panel',
  		buttonAlign : 'center',
  		layout : 'column',
  		items : [{
  			columnWidth : 1,
  			xtype : 'container',
  			style : 'padding-top:100px;border:none',
  			layout:'column',
  			items : [{
  				xtype:'container',
  				html:'&nbsp;',
  				columnWidth:.1
  			},{
  				xtype : 'button',
  				id : 'turnright_add_deliverbill',
  				iconCls:'deppon_icons_turnright',
				tooltip: '添加至派送单',
  				height:23,
  				columnWidth : .8,
  				// 添加至派送单
  				handler : function(){
						if (predeliver.editDeliverbill.deliverbillId == "") {
							// 排单前，若还未保存派送单，则需先保存派送单
							// 验证车辆信息
							if (!predeliver.editDeliverbill.submitForm.validateOnArrange()) {
								return;
							}
						}
						if (predeliver.editDeliverbill.deliverbillId == "") {
							// 排单前，若还未保存派送单，则需先保存派送单
							// 验证车辆信息
							if (!predeliver.editDeliverbill.submitForm.validateOnDriver()) {
								return;
							}
						}
						
						if (predeliver.editDeliverbill.waybillsToArrange.length == 0) {
							Ext.ux.Toast.msg(predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.tip'), predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.choseDeliverDetail'),
									'ok', 4000);
							return;
						}
						var waybills = '';
						var selectWaybillNos =new Array();//存放复选框选中的运单
						var selectWaybillNosWVH =new Array();//存放复选框选中的运单
						var selectRowGrid =predeliver.editDeliverbill.waybillToArrangeGrid,
							selectRowModel = selectRowGrid.getSelectionModel(),
						    selectRow = selectRowModel.getSelection();
						predeliver.editDeliverbill.waybillsToArrange
								.each(function(item, index,length) {
										selectWaybillNos.push(item.waybillNo);//得到复选框选中的运单号集合
									if (item.isAlreadyContact == 'N') {
										waybills += item.waybillNo
												+ '<br/>';
									}
									if (item.transportType == 'WVH') {//如果运输性质为整车
										selectWaybillNosWVH.push(item.waybillNo);//得到复选框选中的运单号集合
									}
								});
						
						var deliverStore = Ext.getCmp('Foss_predeliver_editDeliverbill_WaybillToArrangeGrid_Id').store;
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
							var confirmMsg = Ext.Msg.confirm(predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.tip'), predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.isSentRequired'),function(btn) {
									if (btn == 'yes') {
										if (flag == false) {
											flag = true;
											return;
										}
										if(selectWaybillNosWVH.length>0){
											var checkResult=predeliver.editDeliverbill.checkWayBillNosWVH(selectWaybillNosWVH);
											if(!checkResult){
												return;
											}
										}
										if (waybills == '') {
											predeliver.editDeliverbill.queryIsExsitsWayBillRfcs(selectRowGrid,selectWaybillNos);
										} else {
											var confirmMsgBox = Ext.Msg.confirm(predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.tip'), predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.attention')+ waybills+ predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.noNotice'),function(btn) {
												if (btn == 'yes') {
													predeliver.editDeliverbill.queryIsExsitsWayBillRfcs(selectRowGrid,selectWaybillNos);
												}
											},predeliver.editDeliverbill.arrangeDeliverbillWindow);
										}
									}
								});
						} else {
							if (flag == false) {
								flag = true;
								return;
							}
							if(selectWaybillNosWVH.length>0){
								var checkResult=predeliver.editDeliverbill.checkWayBillNosWVH(selectWaybillNosWVH);
								if(!checkResult){
									return;
								}
							}
							if (waybills == '') {
								predeliver.editDeliverbill.queryIsExsitsWayBillRfcs(selectRowGrid,selectWaybillNos);
							} else {
								var confirmMsgBox = Ext.Msg.confirm(predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.tip'), predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.attention')+ waybills+ predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.noNotice'),function(btn) {
									if (btn == 'yes') {
										predeliver.editDeliverbill.queryIsExsitsWayBillRfcs(selectRowGrid,selectWaybillNos);
									}
								},predeliver.editDeliverbill.arrangeDeliverbillWindow);
								
							}
						}
					
        			}
  			}]
  		},{
  			columnWidth : 1,
  			xtype : 'container',
  			style : 'padding-top:100px;border:none',
  			layout:'column',
  			items : [{
  				xtype:'container',
  				html:'&nbsp;',
  				columnWidth:.1
  			},{
  				xtype : 'button',
				id : 'turnleft_remove_deliverbill',
  				iconCls:'deppon_icons_turnleft',
				tooltip: '移除派送单',
  				height:23,
  				columnWidth : .8,
  				//移除排单信息
  				handler : function(){
				var selectRow = predeliver.editDeliverbill.deliverbillDetailGrid.getSelectionModel().getSelection();

				if (selectRow.length == 0) {
					Ext.ux.Toast.msg(predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.tip'),
							predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.choseRemoveDetail'));
					return;
				}

				Ext.Msg.confirm(predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.tip'),
								predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.sureRemoveDetail'),
						function(btn, text) {
							if (btn == "yes") {
								var ids = '';
								for ( var i = 0; i < selectRow.length; i++) {
									ids = selectRow[i].data.id+ ","+ ids;
								}

								predeliver.editDeliverbill.deliverbillDetailGrid.deleteDeliverbillDetails(ids);
							}
						})
		    	}
  			}]
  		},{
  			columnWidth : 1,
  			xtype : 'container',
  			style : 'padding-top:50px;border:none',
  			layout:'column',
  			items : [{
  				xtype:'container',
  				html:'&nbsp;',
  				columnWidth: 1
  			},{
  				xtype : 'textfield',
  				name : 'waybillNo',
				id : 'deliverbill_query_bywaybillNumber',
				allowBlank:false,
				vtype: 'waybill',
				columnWidth: 1,
				listeners:{
					change:function(field,new_v,old_v){
						if(!Ext.isEmpty(new_v)&& new_v.length >=9){
							//先去两端的空格
							var new_value = new_v.trim();
							//再把值设置给文本框
							field.setValue(new_value.substring(0,new_value.length>=9?9:new_value.length));
						}
					},
					specialkey: function(field, e){
						if (e.getKey() == e.ENTER) {
							var form = this.up('form').getForm();
							if(!form.isValid())
							{
								Ext.ux.Toast.msg(predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.tip'), predeliver.editDeliverbill.i18n('pkp.predeliver.queryGoods.checkQueryInformation'), 'error', 3000);
								return;
							}
							
							Ext.Ajax.request({
								url:predeliver.realPath('queryWaybillToArrangeByWaybillNo.action'),
								params: {
									'deliverbillVo.waybillToArrangeDto.waybillNo': field.value,
									'start' : '0',
									'limit' : '1'
								},
								success: function(response){
									var result = Ext.decode(response.responseText);
									predeliver.editDeliverbill.waybillToArrangeGrid.store.removeAll(),
									Ext.getCmp('Foss_editDeliverbill_QueryGoodsGrid_totalWeight_ID').setValue('0');
									Ext.getCmp('Foss_editDeliverbill_QueryGoodsGrid_totalVolume_ID').setValue('0');
									Ext.getCmp('Foss_editDeliverbill_QueryGoodsGrid_totalQty_ID').setValue('0');
									predeliver.editDeliverbill.waybillToArrangeGrid.store.loadData(result.deliverbillVo.waybillToArrangeDtoList);
									Ext.getCmp('deliverbill_query_bywaybillNumber').setValue('');
									Ext.getCmp('Foss_editDeliverbill_QueryGoodsGrid_totalWeight_ID').setValue(result.deliverbillVo.deliverbillDto.totalGoodsWeight);
									Ext.getCmp('Foss_editDeliverbill_QueryGoodsGrid_totalVolume_ID').setValue(result.deliverbillVo.deliverbillDto.totalGoodsVolume);
									Ext.getCmp('Foss_editDeliverbill_QueryGoodsGrid_totalQty_ID').setValue(result.deliverbillVo.deliverbillDto.totalGoodsQty);
									if(predeliver.editDeliverbill.waybillToArrangeGrid.store.data.length>0)
									{
										record = predeliver.editDeliverbill.waybillToArrangeGrid.store.getRange()[0];
										predeliver.editDeliverbill.waybillToArrangeGrid.getSelectionModel().select(record);
										if (predeliver.editDeliverbill.deliverbillId == "") {
											// 排单前，若还未保存派送单，则需先保存派送单
											// 验证车辆信息
											if (!predeliver.editDeliverbill.submitForm.validateOnArrange()) {
												return;
											}
										}
										var waybills = '';
										var selectWaybillNos =new Array();//存放复选框选中的运单
										var selectWaybillNosWVH =new Array();//存放复选框选中的运单
										predeliver.editDeliverbill.waybillsToArrange.each(function(item, index,length) {
											selectWaybillNos.push(item.waybillNo);//得到复选框选中的运单号集合
											if (item.isAlreadyContact == 'N') {
												waybills += item.waybillNo
														+ '<br/>';
											}
											if (item.transportType == 'WVH') {//如果运输性质为整车
												selectWaybillNosWVH.push(item.waybillNo);//得到复选框选中的运单号集合
											}
										});
										if(selectWaybillNosWVH.length>0){
											var checkResult=predeliver.editDeliverbill.checkWayBillNosWVH(selectWaybillNosWVH);
											if(!checkResult){
												return;
											}
										}
										predeliver.editDeliverbill.queryIsExsitsWayBillRfcs(predeliver.editDeliverbill.waybillToArrangeGrid,selectWaybillNos);
									}else{
										predeliver.editDeliverbill.waybillToArrangeGrid.getSelectionModel().deselectAll();
									}
							   },
								exception: function(response){
									var json = Ext.decode(response.responseText);
									predeliver.editDeliverbill.waybillToArrangeGrid.store.removeAll(),
									Ext.getCmp('Foss_editDeliverbill_QueryGoodsGrid_totalWeight_ID').setValue('0');
									Ext.getCmp('Foss_editDeliverbill_QueryGoodsGrid_totalVolume_ID').setValue('0');
									Ext.getCmp('Foss_editDeliverbill_QueryGoodsGrid_totalQty_ID').setValue('0');
									predeliver.editDeliverbill.waybillToArrangeGrid.getSelectionModel().deselectAll();
									Ext.ux.Toast.msg(predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.tip'), json.message, 'error', 3000);
								}
							});
						}
					}
				}
				}
			]
  		},{
  			columnWidth : 1,
  			xtype : 'container',
  			style : 'padding-top:1px;border:none',
  			layout:'column',
  			items : [{
  				xtype:'container',
  				html:'&nbsp;',
  				columnWidth: 1
  			},{
  				xtype : 'label',
				columnWidth: 1,
				text : '定位右移排单'
			  }
			]
  		}]
  	});



var flag = true;

// 定义货物信息查询结果的表格
Ext.define('Foss.predeliver.editDeliverbill.WaybillToArrangeGrid',
				{
					extend : 'Ext.grid.Panel',
					frame : true,
					title : predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.goodsInfoResult'), //货物信息查询结果,
					id :'Foss_predeliver_editDeliverbill_WaybillToArrangeGrid_Id',
					//emptyText : predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.emptyText'), //查询结果为空,
					//bodyCls : 'autoHeight',
					//cls : 'autoHeight',
					autoScroll : true,
					columnLines: true,
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
					dockedItems : [{
						xtype : 'toolbar',
						dock : 'top',
						layout : 'column',
						items : [ {
							xtype : 'image',
							margin : '0 0 0 0',
							imgCls : 'foss_icons_pkp_flagred'
						}, {
							xtype : 'label',
							margin : '0 0 0 0',
							text : predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.whether') //是否必送货
						}, {
							xtype : 'image',
							margin : '0 0 0 0',
							imgCls : 'foss_icons_pkp_flaggreen'
						}, {
							xtype : 'label',
							margin : '0 0 0 0',
							text : predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.waybillGoodsQtyNotPreArrangeGoodsQty') //排单货物的件数与开单的件数不同
						}, {
							xtype : 'image',
							margin : '0 0 0 0',
							imgCls : 'foss_icons_pkp_flagblue'
						}, {
							xtype : 'label',
							margin : '0 0 0 0',
							text : '合送'
						}, {
							xtype : 'image',
							margin : '0 0 0 3',
							imgCls : 'foss_icons_pkp_goodsNumDisac'
						}, {
							xtype : 'label',
							margin : '0 0 0 0',
							text : predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.preArrangeGoodsQtyNotStockGoodQty') //排单件数与库存件数不同
						} ]
					}, {
						xtype: 'toolbar',
						dock: 'bottom',
						layout:'column',
						defaults:{
							margin:'0 0 0 0',
							allowBlank:true
						},		
						items: [{
							xtype:'displayfield',
							allowBlank:true,
							labelWidth : 60,
							name:'totalQty',
							id:'Foss_editDeliverbill_QueryGoodsGrid_totalQty_ID',
							columnWidth:.1,
							fieldLabel: predeliver.editDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.waybillQtyTotal')//总票数
						},{
							xtype: 'container',
							border : false,
							columnWidth:.05,
							html: '&nbsp;'
						},{
							xtype:'displayfield',
							allowBlank:true,
							name:'totalWeight',
							labelWidth : 95,
							id:'Foss_editDeliverbill_QueryGoodsGrid_totalWeight_ID',
							columnWidth:.1,
							fieldLabel: predeliver.editDeliverbill.i18n('pkp.predeliver.queryGoods.totalWeight')//总重量
						},{
							xtype: 'container',
							border : false,
							columnWidth:.05,
							html: '&nbsp;'
						},{
							xtype:'displayfield',
							allowBlank:true,
							name:'totalVolume',
							id:'Foss_editDeliverbill_QueryGoodsGrid_totalVolume_ID',
							columnWidth:.1,
							fieldLabel: predeliver.editDeliverbill.i18n('pkp.predeliver.queryGoods.totalVolume')//总体积
						}]
					}],
					
						addWaybillToArrange : function() {
						// 排单前需验证车辆信息
						if (predeliver.editDeliverbill.deliverbillId == "") {
							var deliverbillBasicInfo = predeliver.editDeliverbill.deliverbillBasicInfoForm
							.getValues();
							// 保存派送单
							Ext.Ajax.request({
									url : predeliver.realPath('saveDeliverbill.action'),
									jsonData : {									
										'deliverbillVo' : {
											'deliverbill' : {
												'id' : predeliver.editDeliverbill.deliverbillId,
												'deliverbillNo' : deliverbillBasicInfo.deliverbillNo,
												'vehicleNo' : deliverbillBasicInfo.vehicleNo,
												'delStatus' :"",
												'driverCode' : deliverbillBasicInfo.driverCode,
												'deliverType' : deliverbillBasicInfo.deliverType,
												'driverName' : predeliver.editDeliverbill.deliverbillBasicInfoForm.getForm().findField("driverName").rawValue
											}
										}
									},
									success : function(response) {
										var result = Ext.decode(response.responseText);
										predeliver.editDeliverbill.deliverbillId = result.deliverbillVo.deliverbill.id;
										predeliver.editDeliverbill.addWaybillToArrange(predeliver.editDeliverbill.deliverbillId);
								},
									exception : function(response) {
										var result = Ext.decode(response.responseText);
										Ext.ux.Toast.msg(predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.tip'), result.message, 'error', 3000);
								}
							});
						}else {
							predeliver.editDeliverbill.addWaybillToArrange(predeliver.editDeliverbill.deliverbillId);
						}
						
					},
					
					columns : [/*{
								xtype : 'actioncolumn',
								width : 55,
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
							},	*/
							{width : 60,align:'left',margin : '0 0 0 0',padding:'0 0 0 0',
								renderer : function(value, cellmeta, record, rowIndex, columnIndex, store){
									var result = '';
									if (record.get('isSentRequired') == 'Y') {
										result = result +'<span class="foss_icons_pkp_flagred" style="margin:0px 0px 0px 0px;padding:0px 0px 0px 0px;width:18px; height:18px;align:left;"></span>';
									}
									if (record.get('waybillGoodsQty') != record.get('preArrangeGoodsQty')) {
										result=result + '<span class="foss_icons_pkp_flaggreen" style="margin:0px 0px 0px 0px;padding:0px 0px 0px 0px;width:18px; height:18px;"></span>';
									} 
					    		  	if (record.get('togetherSendCode') != null &&record.get('togetherSendCode') !='' ) {
					    		  		result = result+'<span class="foss_icons_pkp_flagblue" style="margin:0px 0px 0px 0px;padding:0px 0px 0px 0px;width:18px; height:18px;"></span>';
									}
									return result;
								}
							},
							{
								dataIndex : 'id',
								align : 'center',
								flex : 1,
								hidden : true
							},{
								dataIndex : 'togetherSendCode',
								align : 'center',
								width : 60,
								header : '合送编码' 
							},{
							dataIndex : 'consignee',
								align : 'center',
								width : 60,
								xtype: 'ellipsiscolumn',
								header : predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.consignee') //收货人
								,
								renderer : function(value, cellmeta, record, rowIndex, columnIndex, store){
									//标示大客户
								  	if(record.data.receiveBigCustomer == 'Y'){
								  		value = '<div class="big_Customer_pic_common"></div>' + value  ;
								  	}
								  	return value;
								}
							},{
								dataIndex : 'consigneeAddress',
								align : 'center',
								width : 130,
								xtype: 'ellipsiscolumn',
								header : predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.consigneeAddress') //送货地址
							},
							{
								dataIndex : 'waybillNo',
								align : 'center',
								width : 80,
								header : predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.waybillNo') //运单号
							},
							{
								dataIndex : 'goodsName',
								align : 'center',
								width : 80,
								header : predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.goodsName') //货物名称
							},
							{
								dataIndex : 'arrangedWeight',
								align : 'center',
								width : 60,
								header : predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.weight') //排单重量
							},
							{
								dataIndex : 'dimension',
								align : 'center',
								width : 80,
								xtype: 'ellipsiscolumn',
								header : predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.goodsSize') //尺寸
							},
							{
								dataIndex : 'goodsVolumeTotal',
								align : 'center',
								width : 60,
								header : predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.dimension') //体积
							},
							{
								dataIndex : 'goodsPackage',
								align : 'center',
								width : 80,
								xtype: 'ellipsiscolumn',
								header : predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.goodsPackage') //包装
							},
							{
								dataIndex : 'waybillGoodsQty',
								align : 'center',
								width : 60,
								header : predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.pieces') //件数
							},
							{
								dataIndex : 'stockGoodQty',
								align : 'center',
								width : 60,
								header : predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.stockQty') //库存件数,
							},
							{
								dataIndex : 'preArrangeGoodsQty',
								align : 'center',
								width : 60,
								header : predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.arrangeGoodsQty'), //排单件数,
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
																					predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.tip'),
																					predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.preArrangeGoodsQtynotArrangableGoodsQty'),
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
																					predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.tip'),
																					predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.preArrangeGoodsQtyGTZerp'),
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
								dataIndex : 'isAlreadyContact',
								align : 'center',
								width : 60,
								header : predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.isAlreadyNotice'), //是否通知
								renderer : function(value, metadata, record, rowIndex, columnIndex, store){
									if(record.get('isAlreadyContact')=='Y'){
										return predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.yes');
									}else{
										return predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.no');
									}
								}
							},
							{
								dataIndex : 'transportType',
								align : 'center',
								width : 100,
								header : predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.transportType'), //运输方式,
								renderer : function(value) {
									return Foss.pkp.ProductData.rendererSubmitToDisplay(value);
								}
							}, {
								dataIndex : 'arriveTime',
								align : 'center',
								width : 130,
								header : predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.arriveTime'), //到达时间,
								xtype : 'datecolumn',
								format : 'Y-m-d H:i:s'
							}, {
								dataIndex : 'deliverDate',
								align : 'center',
								width : 130,
								header : predeliver.editDeliverbill.i18n('pkp.predeliver.confirmDeliverbill.deliverDate'), //送货日期,
								xtype : 'datecolumn',
								format : 'Y-m-d H:i:s'								
							},{
								dataIndex : 'deliverRequire',
								align : 'center',
								width : 60,
								xtype: 'ellipsiscolumn',
								header : predeliver.editDeliverbill.i18n('pkp.predeliver.deliverbillIndex.deliverRequire') //送货需求
							}, {
								dataIndex : 'consigneeContact',
								align : 'center',
								width : 100,
								xtype: 'ellipsiscolumn',
								header : predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.consigneeContact') //联系方式
							}, 
							{
								dataIndex : 'deliverRegionName',
								align : 'center',
								width : 130,
								xtype: 'ellipsiscolumn',
								header : predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.deliverRegionName') //送货小区
							},
							{
								dataIndex : 'deliverSuggest',
								align : 'center',
								width : 130,
								xtype: 'ellipsiscolumn',
								header : predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.deliverSuggest') //带人建议
							},
							{
								dataIndex : 'isSentRequired',
								align : 'center',
								width : 60,
								header : predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.whether'), //是否必送货,
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
								.create('Foss.predeliver.editDeliverbill.WaybillToArrangeStore');
						me.selModel = Ext
								.create('Foss.predeliver.editDeliverbill.CheckboxModel');
						//添加列表按钮及分页工具条
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
								}]
						me.bbar = me.getPagingToolbar();
						me.callParent([ cfg ]);
					}
				});
predeliver.editDeliverbill.queryIsExsitsWayBillRfcs = function( selectRowGrid, selectWaybillNos) {
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
			var notWaybillrfcNos = json.deliverbillVo.notWaybillrfcNos;
			var notPayByOLWaybillNos =json.deliverbillVo.notPayByOLWaybillNos;
			var waybillString = '';
			var notWaybillrfcString ='';
			if(notWaybillrfcNos.length>0){
				for(var j = 0 ;j<notWaybillrfcNos.length;j++){
					notWaybillrfcString +=notWaybillrfcNos[j]+'<br/>';
				}
				var confirmMsgBox3 = Ext.Msg.confirm(predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.tip'),
						　"运单号:" +notWaybillrfcString+"有更改单申请未受理，是否剔除此运单？",function(btn1) {
							if (btn1 == 'yes') {
								for ( var i = 0; i < notWaybillrfcNos.length; i++) {
									predeliver.editDeliverbill.waybillsToArrange
										.each(function(item, index,length) {
											if (item.waybillNo == notWaybillrfcNos[i]) {
												predeliver.editDeliverbill.waybillsToArrange.removeAtKey(item.id);
											}
										});
									}
									if(predeliver.editDeliverbill.waybillsToArrange.length == 0){
										selectRowGrid.pagingToolbar.moveFirst();
										return;
									}else{
										if(waybillNos.length>0){
											for(var j = 0 ;j<waybillNos.length;j++){
												waybillString +=waybillNos[j]+'<br/>';
											}
											//运单号{0},此单货物状态发生了变更，是否剔除此运单？点击“是”，则从派单列表中剔除，点击“否”
											var confirmMsgBox2 = Ext.Msg.confirm(predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.tip'),
											Ext.String.format(predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.waybillRfc'), waybillString),function(btn1) {
												if (btn1 == 'yes') {
													for ( var i = 0; i < waybillNos.length; i++) {
														predeliver.editDeliverbill.waybillsToArrange
															.each(function(item, index,length) {
																if (item.waybillNo == waybillNos[i]) {
																	predeliver.editDeliverbill.waybillsToArrange.removeAtKey(item.id);
																}
															});
														}
														if(predeliver.editDeliverbill.waybillsToArrange.length == 0){
															selectRowGrid.pagingToolbar.moveFirst();
															return;
														}else {
															if(notPayByOLWaybillNos!= null &&notPayByOLWaybillNos.length>0){
																waybillString='';
																if(notPayByOLWaybillNos.length>=4){
																	for(var j = 0 ;j<3;j++){
																		waybillString +=notPayByOLWaybillNos[j]+',';
																	}
																	waybillString = waybillString.substring(0,waybillString.length-1);
																	waybillString+='...';
																	//{0}为网上支付运单，网上支付未完成，无法创建派送单！多条运单显示全部运单号，若提示框内无法列全所有号运单则以省略号代替；
																	Ext.ux.Toast.msg(predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.tip'),
																			Ext.String.format(predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.hasWaybillNosNotPayByOL'), waybillString),
																			'error', 6000);
																}else {
																	for(var j = 0 ;j<notPayByOLWaybillNos.length;j++){
																		waybillString +=notPayByOLWaybillNos[j]+',';
																	}
																	waybillString = waybillString.substring(0,waybillString.length-1);
																	//暂时不用 该提示  运单号'+waybillString+'网上支付未支付完成，需出发部门联系客户完成网上支付或更改付款方式
																	//{0}为网上支付运单，网上支付未完成，无法创建派送单！多条运单显示全部运单号，若提示框内无法列全所有号运单则以省略号代替；
																	Ext.ux.Toast.msg(predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.tip'),
																			Ext.String.format(predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.hasWaybillNosNotPayByOL'), waybillString),
																			'error', 6000);
																}
																for ( var i = 0; i < notPayByOLWaybillNos.length; i++) {
																	predeliver.editDeliverbill.waybillsToArrange
																		.each(function(item, index,length) {
																			if (item.waybillNo == notPayByOLWaybillNos[i]) {
																				predeliver.editDeliverbill.waybillsToArrange.removeAtKey(item.id);
																			}
																		});
																	}
																if(predeliver.editDeliverbill.waybillsToArrange.length == 0){
																	selectRowGrid.pagingToolbar.moveFirst();
																	return;
																}
															}
														}
														Ext.getCmp('T_predeliver-editDeliverbillIndex_content').getWaybillToArrangeGrid().addWaybillToArrange();
												}else {
													return;
												}
											});
										}else {
											if(notPayByOLWaybillNos!= null &&notPayByOLWaybillNos.length>0){
												if(notPayByOLWaybillNos.length>=4){
													for(var j = 0 ;j<3;j++){
														waybillString +=notPayByOLWaybillNos[j]+',';
													}
													waybillString = waybillString.substring(0,waybillString.length-1);
													waybillString+='...';
													////{0}为网上支付运单，网上支付未完成，无法创建派送单！多条运单显示全部运单号，若提示框内无法列全所有号运单则以省略号代替；
													Ext.ux.Toast.msg(predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.tip'),
															Ext.String.format(predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.hasWaybillNosNotPayByOL'), waybillString),
															'error', 6000);
												}else {
													for(var j = 0 ;j<notPayByOLWaybillNos.length;j++){
														waybillString +=notPayByOLWaybillNos[j]+',';
													}
													waybillString = waybillString.substring(0,waybillString.length-1);
													////{0}为网上支付运单，网上支付未完成，无法创建派送单！多条运单显示全部运单号，若提示框内无法列全所有号运单则以省略号代替；
													Ext.ux.Toast.msg(predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.tip'),
															Ext.String.format(predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.hasWaybillNosNotPayByOL'), waybillString),
															'error', 6000);
												}
												for ( var i = 0; i < notPayByOLWaybillNos.length; i++) {
													predeliver.editDeliverbill.waybillsToArrange
														.each(function(item, index,length) {
															if (item.waybillNo == notPayByOLWaybillNos[i]) {
																predeliver.editDeliverbill.waybillsToArrange.removeAtKey(item.id);
															}
														});
													}
												if(predeliver.editDeliverbill.waybillsToArrange.length == 0){
													selectRowGrid.pagingToolbar.moveFirst();
													return;
												}
											}
											Ext.getCmp('T_predeliver-editDeliverbillIndex_content').getWaybillToArrangeGrid().addWaybillToArrange();
										}
									}
				
								}
							});
			}
			else if(waybillNos.length>0){
				for(var j = 0 ;j<waybillNos.length;j++){
					waybillString +=waybillNos[j]+'<br/>';
				}
				//运单号{0},此单货物状态发生了变更，是否剔除此运单？点击“是”，则从派单列表中剔除，点击“否”
				var confirmMsgBox2 = Ext.Msg.confirm(predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.tip'),
				Ext.String.format(predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.waybillRfc'), waybillString),function(btn1) {
					if (btn1 == 'yes') {
						for ( var i = 0; i < waybillNos.length; i++) {
							predeliver.editDeliverbill.waybillsToArrange
								.each(function(item, index,length) {
									if (item.waybillNo == waybillNos[i]) {
										predeliver.editDeliverbill.waybillsToArrange.removeAtKey(item.id);
									}
								});
							}
							if(predeliver.editDeliverbill.waybillsToArrange.length == 0){
								selectRowGrid.pagingToolbar.moveFirst();
								return;
							}else {
								if(notPayByOLWaybillNos!= null &&notPayByOLWaybillNos.length>0){
									waybillString='';
									if(notPayByOLWaybillNos.length>=4){
										for(var j = 0 ;j<3;j++){
											waybillString +=notPayByOLWaybillNos[j]+',';
										}
										waybillString = waybillString.substring(0,waybillString.length-1);
										waybillString+='...';
										//{0}为网上支付运单，网上支付未完成，无法创建派送单！多条运单显示全部运单号，若提示框内无法列全所有号运单则以省略号代替；
										Ext.ux.Toast.msg(predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.tip'),
												Ext.String.format(predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.hasWaybillNosNotPayByOL'), waybillString),
												'error', 6000);
									}else {
										for(var j = 0 ;j<notPayByOLWaybillNos.length;j++){
											waybillString +=notPayByOLWaybillNos[j]+',';
										}
										waybillString = waybillString.substring(0,waybillString.length-1);
										//暂时不用 该提示  运单号'+waybillString+'网上支付未支付完成，需出发部门联系客户完成网上支付或更改付款方式
										//{0}为网上支付运单，网上支付未完成，无法创建派送单！多条运单显示全部运单号，若提示框内无法列全所有号运单则以省略号代替；
										Ext.ux.Toast.msg(predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.tip'),
												Ext.String.format(predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.hasWaybillNosNotPayByOL'), waybillString),
												'error', 6000);
									}
									for ( var i = 0; i < notPayByOLWaybillNos.length; i++) {
										predeliver.editDeliverbill.waybillsToArrange
											.each(function(item, index,length) {
												if (item.waybillNo == notPayByOLWaybillNos[i]) {
													predeliver.editDeliverbill.waybillsToArrange.removeAtKey(item.id);
												}
											});
										}
									if(predeliver.editDeliverbill.waybillsToArrange.length == 0){
										selectRowGrid.pagingToolbar.moveFirst();
										return;
									}
								}
							}
							Ext.getCmp('T_predeliver-editDeliverbillIndex_content').getWaybillToArrangeGrid().addWaybillToArrange();
					}else {
						return;
					}
				});
			}else {
				if(notPayByOLWaybillNos!= null &&notPayByOLWaybillNos.length>0){
					if(notPayByOLWaybillNos.length>=4){
						for(var j = 0 ;j<3;j++){
							waybillString +=notPayByOLWaybillNos[j]+',';
						}
						waybillString = waybillString.substring(0,waybillString.length-1);
						waybillString+='...';
						////{0}为网上支付运单，网上支付未完成，无法创建派送单！多条运单显示全部运单号，若提示框内无法列全所有号运单则以省略号代替；
						Ext.ux.Toast.msg(predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.tip'),
								Ext.String.format(predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.hasWaybillNosNotPayByOL'), waybillString),
								'error', 6000);
					}else {
						for(var j = 0 ;j<notPayByOLWaybillNos.length;j++){
							waybillString +=notPayByOLWaybillNos[j]+',';
						}
						waybillString = waybillString.substring(0,waybillString.length-1);
						////{0}为网上支付运单，网上支付未完成，无法创建派送单！多条运单显示全部运单号，若提示框内无法列全所有号运单则以省略号代替；
						Ext.ux.Toast.msg(predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.tip'),
								Ext.String.format(predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.hasWaybillNosNotPayByOL'), waybillString),
								'error', 6000);
					}
					for ( var i = 0; i < notPayByOLWaybillNos.length; i++) {
						predeliver.editDeliverbill.waybillsToArrange
							.each(function(item, index,length) {
								if (item.waybillNo == notPayByOLWaybillNos[i]) {
									predeliver.editDeliverbill.waybillsToArrange.removeAtKey(item.id);
								}
							});
						}
					if(predeliver.editDeliverbill.waybillsToArrange.length == 0){
						selectRowGrid.pagingToolbar.moveFirst();
						return;
					}
				}
				Ext.getCmp('T_predeliver-editDeliverbillIndex_content').getWaybillToArrangeGrid().addWaybillToArrange();
			}
		}
	});
}
predeliver.editDeliverbill.checkWayBillNosWVH = function(selectWVHWaybillNos) {
	if(selectWVHWaybillNos.length<=0){
		return true;
	}
	var result1 =true;
	//判断传入的整车运单号是否做配载和到达
	Ext.Ajax.request({
		url : predeliver.realPath('checkWayBillNosWVH.action'),
		async : false,
		jsonData:{
			'deliverbillVo' : {
				'waybillNos':selectWVHWaybillNos
			}
		},
		success : function(response) {
			
		},exception : function(response) {
			var result = Ext.decode(response.responseText);
			Ext.ux.Toast.msg(predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.tip'), result.message, 'error', 3000);
			result1 =false; 
		}
	});
	return 	result1;

}
predeliver.editDeliverbill.addWaybillToArrange =function(id){
	Ext.Ajax.request({
		url : predeliver.realPath('addWaybillToArrange.action'),
		timeout: 600000,
		jsonData : {
			'deliverbillVo' : {
				'waybillToArrangeDtoList' : predeliver.editDeliverbill.waybillsToArrange.items,
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
						+ predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.waybillbr')
						+ "</br>" 
						+ failedList[i].waybillNo
						+ " "
						+ failedList[i].failedToArrangeReason;
			}
			
			predeliver.editDeliverbill.waybillToArrangeGrid.store.load();
			// 刷新派送单信息
			Ext.getCmp('T_predeliver-editDeliverbillIndex_content').refreshDeliverbillInfoForm();
			predeliver.editDeliverbill.deliverbillDetailGrid.store.load();
			if (alertMsg == "") {
				Ext.ux.Toast.msg(predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.tip'),
						result.message);
			} else {
				Ext.ux.Toast.msg(predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.tip'), alertMsg,
						'error', 4000);
			}
		},
		exception : function(response) {
			var result = Ext.decode(response.responseText);
			Ext.ux.Toast.msg(predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.tip'), result.message, 'error', 3000);
		}
	});
}
// 确认运单form
Ext.define('Foss.predeliver.editDeliverbill.ConfirmForm',{
	extend : 'Ext.form.Panel',
	frame : false,
	height : 80,
	defaults : {
		margin : '0 45 0 25'
	},
	layout : 'column',
	addWaybillToArrange : function() {
		// 排单前需验证车辆信息
		if (predeliver.editDeliverbill.deliverbillId == "") {
			var deliverbillBasicInfo = predeliver.editDeliverbill.deliverbillBasicInfoForm
			.getValues();
			// 保存派送单
			Ext.Ajax.request({
					url : predeliver.realPath('saveDeliverbill.action'),
					jsonData : {
						'deliverbillVo' : {
							'deliverbill' : {
								'id' : predeliver.editDeliverbill.deliverbillId,
								'deliverbillNo' : deliverbillBasicInfo.deliverbillNo,
								'vehicleNo' : deliverbillBasicInfo.vehicleNo,
								'driverCode' : deliverbillBasicInfo.driverCode,
								'deliverType' : deliverbillBasicInfo.deliverType,
								'driverName' : predeliver.editDeliverbill.deliverbillBasicInfoForm.getForm().findField("driverName").rawValue
							}
						}
					},
					success : function(response) {
						var result = Ext.decode(response.responseText);
						predeliver.editDeliverbill.deliverbillId = result.deliverbillVo.deliverbill.id;
						predeliver.editDeliverbill.addWaybillToArrange(predeliver.editDeliverbill.deliverbillId);
				},
					exception : function(response) {
						var result = Ext.decode(response.responseText);
						Ext.ux.Toast.msg(predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.tip'), result.message, 'error', 3000);
				}
			});
		}else {
			predeliver.editDeliverbill.addWaybillToArrange(predeliver.editDeliverbill.deliverbillId);
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
					text : predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.confirm'), //确认,
					plugins : Ext.create('Deppon.ux.ButtonLimitingPlugin', {
						// 设定间隔秒数,如果不设置，默认为2秒
						seconds : 2
					}),
					handler : function() {
						if (predeliver.editDeliverbill.waybillsToArrange.length == 0) {
							Ext.ux.Toast.msg(predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.tip'), predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.choseDeliverDetail'),
									'ok', 4000);
							return;
						}
						var waybills = '';
						var selectWaybillNos =new Array();//存放复选框选中的运单
						var selectRowGrid =Ext
								.getCmp(
										'T_predeliver-editDeliverbillIndex_content')
								.getArrangeDeliverbillWindow()
								.getWaybillToArrangeGrid(),
							selectRowModel = selectRowGrid.getSelectionModel(),
						    selectRow = selectRowModel.getSelection();
						predeliver.editDeliverbill.waybillsToArrange
								.each(function(item, index,
										length) {
										selectWaybillNos.push(item.waybillNo);//得到复选框选中的运单号集合
									if (item.isAlreadyContact == 'N') {
										waybills += item.waybillNo
												+ '<br/>';
									}
								});
						
						var deliverStore = Ext.getCmp('Foss_predeliver_editDeliverbill_WaybillToArrangeGrid_Id').store;
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
							var confirmMsg = Ext.Msg.confirm(predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.tip'), predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.isSentRequired'),function(btn) {
									if (btn == 'yes') {
										if (flag == false) {
											flag = true;
											return;
										}
										if (waybills == '') {
											predeliver.editDeliverbill.queryIsExsitsWayBillRfcs(selectRowGrid,selectWaybillNos);
										} else {
											var confirmMsgBox = Ext.Msg.confirm(predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.tip'), predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.attention')+ waybills+ predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.noNotice'),function(btn) {
												if (btn == 'yes') {
													predeliver.editDeliverbill.queryIsExsitsWayBillRfcs(selectRowGrid,selectWaybillNos);
												}
											},predeliver.editDeliverbill.arrangeDeliverbillWindow);
										}
									}
								});
						} else {
							if (flag == false) {
								flag = true;
								return;
							}
							if (waybills == '') {
								predeliver.editDeliverbill.queryIsExsitsWayBillRfcs(selectRowGrid,selectWaybillNos);
							} else {
								var confirmMsgBox = Ext.Msg.confirm(predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.tip'), predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.attention')+ waybills+ predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.noNotice'),function(btn) {
									if (btn == 'yes') {
										predeliver.editDeliverbill.queryIsExsitsWayBillRfcs(selectRowGrid,selectWaybillNos);
									}
								},predeliver.editDeliverbill.arrangeDeliverbillWindow);
							}
						}
					}
				} ]
	} ]
});


// 排单窗口
Ext.define('Foss.predeliver.editDeliverbill.ArrangeDeliverbillWindow',
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
Ext.define('Foss.predeliver.editDeliverbill.SubmitForm',
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
							Ext.ux.Toast.msg(predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.tip'), predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.choseTheCar'));
							return false;
						}

						return true;
					},
					// 排单前验证
					validateOnDriver : function() {
						// 11a 12a 排单员点击“保存”或“提交”时，如果外请车没有对应的司机
						if (predeliver.editDeliverbill.vehicleNoTruckType == '外请车'&& predeliver.editDeliverbill.deliverbillBasicInfoForm.getForm().findField('driverName').getRawValue() == '') {
							Ext.ux.Toast.msg(predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.tip'), predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.drivercantblank'));
							return false;
						}
						return true;
					},
					// 保存/提交时验证
					validateOnSaveOrSubmit : function() {
						// 11a 12a 排单员点击“保存”或“提交”时，如果未有选车辆。 系统提示“请选择车辆”
						if (!predeliver.editDeliverbill.deliverbillBasicInfoForm
								.getForm().isValid()) {
							Ext.ux.Toast.msg(predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.tip'), predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.choseTheCar'));
							return false;
						}

						// 11b 12b
						// 排单员点击“保存”或“提交”时，如果未有运单货物。系统则提示“待排单货物为空，请选择待排单货物！”
						if (predeliver.editDeliverbill.deliverbillDetailGrid.store.data
								.getCount() == 0) {
							Ext.ux.Toast.msg(predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.tip'), predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.choseTheGoods'));
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
												'deliverbillNo' : deliverbillBasicInfo.deliverbillNo,
												'vehicleNo' : deliverbillBasicInfo.vehicleNo,
												'delStatus' :predeliver.editDeliverbill.status,
												'driverCode' : deliverbillBasicInfo.driverCode,
												'deliverType' : deliverbillBasicInfo.deliverType,
												'driverName' : predeliver.editDeliverbill.deliverbillBasicInfoForm.getForm().findField("driverName").rawValue,
												'status' : deliverbillStatus
											}
										}
									},
									success : function(response) {

										var deliverbill = predeliver.editDeliverbill.deliverbillInfoForm
												.getRecord();

										var confirmTitle = deliverbillStatus == 'SAVED' ? predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.saveDeliverSuccess')
												: predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.submitDeliverSuccess');

										var confirmMsg = confirmTitle
												+ predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.theNumberIs')
												+ deliverbillBasicInfo.deliverbillNo
												+ predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.weightTotalbr')
												+ deliverbill
														.get('weightTotal')
												+ predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.volumeTotalbr')
												+ deliverbill
														.get('volumeTotal')
												+ predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.waybillQtyTotalbr')
												+ deliverbill
														.get('waybillQtyTotal')
												+ predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.piecesTotalbr')
												+ deliverbill
														.get('goodsQtyTotal')
												+ predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.payAmountTotalbr')
												+ deliverbill
														.get('payAmountTotal')
												+ predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.loadRatebr')
												+ deliverbill.get('loadRate')
												+ predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.surePrintDeliverBill')

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
																				var driverName = result.deliverbillVo.deliverbill.driverName;
																				if(driverName!=null && driverName!=""){//如果司机姓名不为空，通知司机按钮显示、可操作
																					printWin.query('checkbox[name="isNoticeDriver"]')[0].setValue('Y');
																					printWin.query('checkbox[name="isNoticeDriver"]')[0].setVisible(true); 
																				}else{//如果司机姓名为空，通知司机按钮显示、不可操作
																					printWin.query('checkbox[name="isNoticeDriver"]')[0].setVisible(true); 
																					printWin.query('checkbox[name="isNoticeDriver"]')[0].setDisabled (true); 
																				}
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

									},
									exception : function(response) {
											var result = Ext.decode(response.responseText);
											Ext.ux.Toast.msg(predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.tip'), result.message, 'error', 3000);
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
									text : predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.save'), //保存,
									handler : function(button, e) {
										button.up('form').saveOrSubmit('SAVED',
												button);
									}
								},
								{
									columnWidth : .08,
									xtype : 'button',
									id : 'deliverbill_submit',
									disabled : true,
									cls : 'yellow_button',
									text : predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.submit'), //提交,
									disabled:!predeliver.editDeliverbill.isPermission('editdeliverbillindex/editdeliverbillindexsubmitbutton'),
									hidden:!predeliver.editDeliverbill.isPermission('editdeliverbillindex/editdeliverbillindexsubmitbutton'),
									handler : function(button, e) {
										button.up('form').saveOrSubmit(
												'SUBMITED');
									}
								} ]
					} ]
				});

Ext.onReady(function() {
			Ext.QuickTips.init();

			predeliver.editDeliverbill.deliverbillBasicInfoForm = Ext
					.create('Foss.predeliver.editDeliverbill.DeliverbillBasicInfoForm');
			predeliver.editDeliverbill.deliverbillDetailGrid = Ext
					.create('Foss.predeliver.editDeliverbill.DeliverbillDetailGrid',
					{height:480,columnWidth:.46});
			predeliver.editDeliverbill.queryWaybillForm = Ext
					.create('Foss.predeliver.editDeliverbill.QueryWaybillForm',{columnWidth:1});
			predeliver.editDeliverbill.waybillToArrangeGrid = Ext
					.create('Foss.predeliver.editDeliverbill.WaybillToArrangeGrid',
					{height:480,columnWidth:.46});
			Foss.predeliver.ButtonPanelRole = Ext
					.create('Foss.predeliver.ButtonPanelRole',
					{height:480,columnWidth:.08});
			predeliver.editDeliverbill.deliverbillInfoForm = Ext
					.create('Foss.predeliver.editDeliverbill.DeliverbillInfoForm');
			predeliver.editDeliverbill.submitForm = Ext
					.create('Foss.predeliver.editDeliverbill.SubmitForm');
								
			Ext.define('FOSS.predeliver.ContainerPanel',{
				  extend: 'Ext.panel.Panel',
				  bodyCls : 'autoHeight',
				  cls : 'autoHeight',
				  layout: 'column',
				  frame: true,
				  	items:[predeliver.editDeliverbill.queryWaybillForm,
						   predeliver.editDeliverbill.waybillToArrangeGrid,
						   Foss.predeliver.ButtonPanelRole,
					       predeliver.editDeliverbill.deliverbillDetailGrid]
			}); 
			predeliver.editDeliverbill.ContainerPanel = Ext
					.create('FOSS.predeliver.ContainerPanel');
			predeliver.editDeliverbill.arrangeDeliverbillWindow = null;
			
			Ext.getCmp('receiveCustomer_Prov').setReginValue(predeliver.provName, provCode);	
			Ext.getCmp('receiveCustomer_City').setReginValue(predeliver.cityName, cityCode);
			if(predeliver.editDeliverbill.status == 'SAVED' || predeliver.editDeliverbill.deliverbillId == '')
			{
				Ext.getCmp('deliverbill_submit').setDisabled(false);
			}					
			if(predeliver.editDeliverbill.status == 'LOADED' || predeliver.editDeliverbill.status == 'CONFIRMED')
			{
				Ext.getCmp('turnleft_remove_deliverbill').setDisabled(true);
				Ext.getCmp('turnright_add_deliverbill').setDisabled(true);
				var driverNameField  = predeliver.editDeliverbill.deliverbillBasicInfoForm.getForm().findField('vehicleNo');
				var vehicleTypeField = predeliver.editDeliverbill.deliverbillBasicInfoForm.getForm().findField('vehicleType');
				var deliverTypeField = predeliver.editDeliverbill.deliverbillBasicInfoForm.getForm().findField('deliverType');
				driverNameField.setReadOnly(true);
				vehicleTypeField.setReadOnly(true);
				deliverTypeField.setReadOnly(true);
				
			}
						
			// ---- 重量/体积精度
			predeliver.editDeliverbill.numberPrecision = 2;
			predeliver.editDeliverbill.waybillsToArrange = new Ext.util.MixedCollection(); // 选中的待排运单集合

			Ext.create('Ext.panel.Panel',
							{id : 'T_predeliver-editDeliverbillIndex_content',
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

														var driverNameField = predeliver.editDeliverbill.deliverbillBasicInfoForm
																.getForm()
																.findField('driverName');

														var vehicle = result.deliverbillVo.vehicleAssociationDto;

														if (vehicle != null) {
															var deliverbillInfo = predeliver.editDeliverbill.deliverbillInfoForm.getRecord();
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
																				predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.warningMessage'),
																				predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.arrangedWeightBTLoadWeight'),
																				'error',
																				4000);
															}

															if (vehicleVolumeRate > 1) {
																// 10a
																// 点击“确认”时，如果预派送单中货物总体积或总重量超出车辆基础信息中的车辆额定载重或额定净空。系统则弹出“排单总体积或总重量超出车辆额定载重或额定净空”警告对话框。
																Ext.ux.Toast
																		.msg(
																				predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.warningMessage'),
																				predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.goodsVolumeTotalBTClearance'),
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

															predeliver.editDeliverbill.deliverbillInfoForm
																	.loadRecord(deliverbillInfo);
														} else {
															Ext.ux.Toast.msg(
																	predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.tip'),
																	predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.vehicleNotExist'),
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
										Ext.Ajax.request({
													url : predeliver
															.realPath('queryDeliverbill.action'),
													params : {
														'deliverbillVo.deliverbillDto.id' : predeliver.editDeliverbill.deliverbillId
													},
													success : function(response) {
														var result = Ext.decode(response.responseText);
														var deliverbill = Ext.ModelManager.create(result.deliverbillVo.deliverbill,
																		'Foss.predeliver.editDeliverbill.DeliverbillModel');

														predeliver.editDeliverbill.deliverbillBasicInfoForm
																.loadRecord(deliverbill);
														predeliver.editDeliverbill.deliverbillInfoForm
																.loadRecord(deliverbill);

														Ext.getCmp('T_predeliver-editDeliverbillIndex_content')
																.refreshVehicleLoadInfo(deliverbill.get('vehicleNo'));
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
								
								getWaybillToArrangeGrid : function() {
									if (predeliver.editDeliverbill.waybillToArrangeGrid == null) {
										predeliver.editDeliverbill.waybillToArrangeGrid = Ext
										.create('predeliver.editDeliverbill.waybillToArrangeGrid');
									}
									return predeliver.editDeliverbill.waybillToArrangeGrid;
								},
								
								getDriverName : function(vehicleNo) {
									
									if (vehicleNo != '' && vehicleNo != null) {
										Ext.Ajax
												.request({
													url : predeliver
															.realPath('queryVehicleByVehicleNo.action'),
													params : {
														'deliverbillVo.deliverbillDto.vehicleNo' : vehicleNo
													},
													success : function(response) {
														var deliverbillInfo = predeliver.editDeliverbill.deliverbillInfoForm.getRecord();
														
														var result = Ext.decode(response.responseText);

														var driverNameField = predeliver.editDeliverbill.deliverbillBasicInfoForm.getForm().findField('driverName');

														var vehicle = result.deliverbillVo.vehicleAssociationDto;

														if (vehicle != null) {
												

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
																driverNameField.setReadOnly(false);
															}

															predeliver.editDeliverbill.deliverbillInfoForm
																	.loadRecord(deliverbillInfo);
														} else {
															Ext.ux.Toast.msg(
																	predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.tip'),
																	predeliver.editDeliverbill.i18n('pkp.predeliver.editDeliverbillIndex.vehicleNotExist'),
																	'error',
																	4000);
															if (typeof (vehicleNoField) != "undefined"
																	&& vehicleNoField != null) {
																vehicleNoField
																		.setValue(oldVehicleNo);
															}
															driverNameField.setReadOnly(false);
														}
													}
												});
									}
								
																
								},
								
								items : [
										predeliver.editDeliverbill.deliverbillBasicInfoForm,
								
										predeliver.editDeliverbill.ContainerPanel,
										predeliver.editDeliverbill.deliverbillInfoForm,
										predeliver.editDeliverbill.submitForm ],
								renderTo : 'T_predeliver-editDeliverbillIndex-body'
							});

			Ext.getCmp('T_predeliver-editDeliverbillIndex_content').refreshDeliverbillInfoForm();
		});
