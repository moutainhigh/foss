/**
 * author : 043258-foss-meiying page : 派送系统管理
 */
predeliver.PICKUPGOODSHIGHWAYS = 'PICKUPGOODSHIGHWAYS'; // 派送方式词条
predeliver.arriveDeliverManager.transportType = 'TRANS_TYPE';// 运输方式词条
predeliver.arriveDeliverManager.RETURN_BILL_TYPE = 'RETURNBILLTYPE';// 返单类型
predeliver.arriveDeliverManager.PKP_SIGN_SITUATION = 'PKP_SIGN_SITUATION';// 签收情况词条
predeliver.arriveDeliverManager.PKP_SIGN_STATUS = 'PKP_SIGN_STATUS';//到达联签收状态
predeliver.productCodeWVH = 'WVH'; // 运输性质为整车

(function() {
	predeliver.arriveDeliverManager.returnBillTypeStore = FossDataDictionary.getDataDictionaryStore(predeliver.arriveDeliverManager.RETURN_BILL_TYPE,
		null, {
			'valueCode' : '',
			'valueName' : predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.all')
		});
	predeliver.arriveDeliverManager.situationStore = FossDataDictionary.getDataDictionaryStore(predeliver.arriveDeliverManager.PKP_SIGN_SITUATION,
		null, {
			'valueCode' : '',
			'valueName' : predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.all')
		});
	predeliver.arriveDeliverManager.situationStore.removeAt(predeliver.arriveDeliverManager.situationStore.find('valueCode', 'GOODS_BACK'));
	predeliver.arriveDeliverManager.situationStore.removeAt(predeliver.arriveDeliverManager.situationStore.find('valueCode', 'UNNORMAL_BREAK'));// 异常-破损
	predeliver.arriveDeliverManager.situationStore.removeAt(predeliver.arriveDeliverManager.situationStore.find('valueCode', 'UNNORMAL_DAMP'));// 异常-潮湿
	predeliver.arriveDeliverManager.situationStore.removeAt(predeliver.arriveDeliverManager.situationStore.find('valueCode', 'UNNORMAL_POLLUTION'));// 异常-污染
	predeliver.arriveDeliverManager.situationStore.removeAt(predeliver.arriveDeliverManager.situationStore.find('valueCode', 'UNNORMAL_ELSE'));// 异常-其他
	predeliver.arriveDeliverManager.situationStore.removeAt(predeliver.arriveDeliverManager.situationStore.find('valueCode', 'UNNORMAL_LOSTCARGO'));// 异常-丢货
	predeliver.arriveDeliverManager.situationStore.removeAt(predeliver.arriveDeliverManager.situationStore.find('valueCode', 'UNNORMAL_CONTRABAND'));// 移除异常-违禁品
	predeliver.arriveDeliverManager.situationStore.removeAt(predeliver.arriveDeliverManager.situationStore.find('valueCode', 'PARTIAL_SIGN'));//移除部分签收
	predeliver.arriveDeliverManager.situationStore
		.removeAt(predeliver.arriveDeliverManager.situationStore.find('valueCode', 'UNNORMAL_ABANDONGOODS'));// 移除异常-弃货
	/*
	 * predeliver.arriveDeliverManager.returnBillTypeStore .removeAt(predeliver.arriveDeliverManager.returnBillTypeStore.find( 'valueCode', 'NONE'));
	 */
	predeliver.arriveDeliverManager.signStatusStore = FossDataDictionary.getDataDictionaryStore(predeliver.arriveDeliverManager.PKP_SIGN_STATUS ,
			null, {
				'valueCode' : '',
				'valueName' : predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.all')
			});
})();
// 签收情况集合
Ext.define('Foss.predeliver.arriveDeliverManager.situationStore', {
		extend : 'Ext.data.Store',
		fields : [{
				name : 'code',
				type : 'string'
			}, {
				name : 'name',
				type : 'string'
			}],
		data : {
			'items' : [{
					'code' : '',
					'name' : predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.all')
				},// 全部
				{
					'code' : 'Yes',
					'name' : predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.isSignYes')
				},// 已签收
				{
					'code' : 'No',
					'name' : predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.isSignNo')
				} // 未签收
			]
		},
		// 定义一个代理对象
		proxy : {
			// 代理的类型为内存代理
			type : 'memory',
			// 定义一个读取器
			reader : {
				// 以JSON的方式读取
				type : 'json',
				// 定义读取JSON数据的根对象
				root : 'items'
			}
		}
	});
// 返单状态集合
Ext.define('Foss.predeliver.arriveDeliverManager.returnBillStatusStore', {
		extend : 'Ext.data.Store',
		fields : [{
				name : 'code',
				type : 'string'
			}, {
				name : 'name',
				type : 'string'
			}],
		data : {
			'items' : [{
					'code' : '',
					'name' : predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.all')
				},// 全部
				{
					'code' : 'ALREADY_RETURN_BILL',
					'name' : predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.returnBillStatusAleradyRetur')
				},// 已返单
				{
					'code' : 'NONE_RETURN_BILL',
					'name' : predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.returnBillStatusnoneReturnBi')
				} // 未返单
			]
		},
		// 定义一个代理对象
		proxy : {
			// 代理的类型为内存代理
			type : 'memory',
			// 定义一个读取器
			reader : {
				// 以JSON的方式读取
				type : 'json',
				// 定义读取JSON数据的根对象
				root : 'items'
			}
		}
	});
// 提货方式集合
Ext.define('Foss.predeliver.arriveDeliverManager.ReceiveMethodStore', {
		extend : 'Ext.data.Store',
		fields : [{
				name : 'valueCode',
				type : 'string'
			}, {
				name : 'valueName',
				type : 'string'
			}],
		data : {
			'items' : [{
					'valueCode' : '',
					'valueName' : predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.all')
				},// 全部
				{
					'valueCode' : 'DELIVER',
					'valueName' : predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.receiveMethodDeliver')
				},// '送货'
				{
					'valueCode' : 'PICKUP',
					'valueName' : predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.receiveMethodPickup')
				} // '自提'
			]
		},
		// 定义一个代理对象
		proxy : {
			// 代理的类型为内存代理
			type : 'memory',
			// 定义一个读取器
			reader : {
				// 以JSON的方式读取
				type : 'json',
				// 定义读取JSON数据的根对象
				root : 'items'
			}
		}
	});
// 入库情况集合
Ext.define('Foss.predeliver.arriveDeliverManager.inStockSituationStore', {
		extend : 'Ext.data.Store',
		fields : [{
				name : 'code',
				type : 'string'
			}, {
				name : 'name',
				type : 'string'
			}],
		data : {
			'items' : [{
					'code' : '',
					'name' : predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.all')
				},// 全部
				{
					'code' : 'NOT_IN_STOCK',
					'name' : predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.inStockSituationNotInStock')
				},// 未入库
				{
					'code' : 'IN_STOCK',
					'name' : predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.inStockSituationInStock')
				},// 库存中
				{
					'code' : 'OUT_STOCK',
					'name' : predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.inStockSituationOutStock')
				} // 已出库
			]
		},
		// 定义一个代理对象
		proxy : {
			// 代理的类型为内存代理
			type : 'memory',
			// 定义一个读取器
			reader : {
				// 以JSON的方式读取
				type : 'json',
				// 定义读取JSON数据的根对象
				root : 'items'
			}
		}
	});
// 定义到达派送模型
Ext.define('Foss.arriveDeliverManager.Model.arriveDeliverInfoModel', {
		extend : 'Ext.data.Model',
		fields : [{
				name : 'waybillNo',
				type : 'string'
			}, // 单号
			{
				name : 'arrivesheetNo',
				type : 'string'
			}, // 到达联编号
			{
				name : 'receiveMethod',
				convert : function(value, record) {
					if (record.get('transportType') == 'TRANS_AIRCRAFT') {// 空运
						return FossDataDictionary.rendererSubmitToDisplay(value, 'PICKUPGOODSAIR');
					} else {// 汽运
						var v = FossDataDictionary.rendererSubmitToDisplay(value, 'PICKUPGOODSHIGHWAYS');
						if(Ext.isEmpty(v) || value == v){
							v = FossDataDictionary.rendererSubmitToDisplay(value, 'PICKUPGOODSSPECIALDELIVERYTYPE');
						}
						return v;
					}
				}
			}, // 开单提货方式
			{
				name : 'arriveTime',
				type : 'date',
				convert : dateConvert
			}, // 到达时间
			{
				name : 'inStockTime',
				type : 'date',
				convert : dateConvert
			}, // 入库时间
			{
				name : 'receiveCustomerContact',
				type : 'string'
			}, // 收货人
			{
				name : 'goodsQtyTotal',
				type : 'string'
			}, // 开单件数
			{
				name : 'goodsWeightTotal',
				type : 'string'
			}, // 开单重量
			{
				name : 'goodsVolumeTotal',
				type : 'string'
			}, // 开单体积
			{
				name : 'codAmount',
				type : 'string'
			}, // 代收货款
			{
				name : 'toPayAmount',
				type : 'string'
			}, // 到付金额
			{
				name : 'repaymentCodeAmount',
				type : 'string'
			}, // 已付金额
			// 结清货款付的金额，如有多次是多次的总和，没有就是显示为0
			{
				name : 'settleStatus',
				type : 'string',
				convert : function(value) {
					if (value != null && value == 'Y') {
						return predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.yes');// 是
					} else {
						return predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.no');// 否
					}
				}
			}, // 是否已结清
			{
				name : 'receiveOrgCode',
				type : 'string'
			}, // 出发部门
			{
				name : 'driverName',
				type : 'string'
			}, // 送货人或代理
			{
				name : 'billTime',
				type : 'date',
				convert : dateConvert
			}, // 收货日期
			{
				name : 'receivePCDAddress',
				type : 'string'
			}, // 收货人地址
			{
				name : 'receiveCustomerPhone',
				type : 'string'
			}, // 收货人电话
			{
				name : 'receiveCustomerMobilePhone',
				type : 'string'
			}, // 收货人手机
			{
				name : 'arriveGoodsQty',
				type : 'string'
			}, // 到达件数
			{
				name : 'stockType',
				type : 'string'
			}, // 库存状态
			{
				name : 'orgCode',
				type : 'string'
			}, // 库存部门
			{
				name : 'stockGoodsQty',
				type : 'string'
			}, // 库存件数
			{
				name : 'notificationResult',
				type : 'string',
				convert : function(value) {
					if (value != null && value == 'SUCCESS') {
						return predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.yes');// 是
					} else {
						return predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.no');// 否
					}
				}
			}, // 是否已经通知
			{
				name : 'deliveryMan',
				type : 'string'
			}, // 取货人
			{
				name : 'returnBillType',
				type : 'string',
				convert : function(value, record) {
					return FossDataDictionary.rendererSubmitToDisplay(value, predeliver.arriveDeliverManager.RETURN_BILL_TYPE);
				}
			}, // 返单情况
			{
				name : 'returnbillStatus',
				type : 'string',
				convert : function(value, record) {
					if (value != null && value == 'ALREADY_RETURN_BILL') {
						return predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.returnBillStatusAleradyRetur');// 已返单
					} else if (value != null && value == 'NONE_RETURN_BILL') {
						return predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.returnBillStatusnoneReturnBi');// 未返单
					} else {
						return '';
					}
				}
			}, // 返单状态
			{
				name : 'isArrange',
				type : 'string'
			}, // 是否已排单
			{
				name : 'transportType',
				type : 'string'
			}, // 运输类型
			{
				name : 'deliverbillNo',
				type : 'string'
			}, // 派送单单号
			{
				name : 'isSign',
				type : 'string'
			}, // 是否签收
			{
				name : 'signTime',
				type : 'date',
				convert : dateConvert
			}, // 签收时间
			{
				name : 'situation',
				type : 'string',
				convert : function(value) {
					return FossDataDictionary.rendererSubmitToDisplay(value, 'PKP_SIGN_SITUATION');
				}
			}, // 签收情况
			{
				name : 'signNote',
				type : 'string'
			}, // 签收备注
			{
				name : 'deliverGoodsFee',
				type : 'string'
			}, // 送货费
			{
				name : 'otherFee',
				type : 'string'
			}, // 其他费用
			{
				name : 'cargoName',
				type : 'string'
			}, // 货物品名
			{
				name : 'productCode',
				type : 'string'
			}, // 运输性质
			{
				name : 'insuranceValue',
				type : 'string'
			}, // 保价声明价值
			{
				name : 'insuranceFee',
				type : 'string'
			}, // 保价费

			{
				name : 'deliverRequire',
				type : 'string'
			}, // 送货要求
			{
				name : 'prePayAmount',
				type : 'string'
			}, // 预付金额
			{
				name : 'operateTime',
				type : 'date',
				convert : dateConvert
			}, // 送货时间
			{
				name : 'targetOrgCode',
				type : 'string'
			}, // 目的站

			{
				name : 'deliverbillStatus',
				type : 'string',
				convert : function(value, record) {
					return FossDataDictionary.rendererSubmitToDisplay(value, 'PKP_DELIVERBILL_STATUS');
				}
			}, // 派送情况
			{
				name : 'codToPayAmount',
				type : 'string'
			} // 收款总额
		]
	});

// 定义到达派送合计模型
Ext.define('Foss.arriveDeliverManager.Model.arriveDeliverTotalInfoModel', {
		extend : 'Ext.data.Model',
		fields : [{
				name : 'totalVotes',
				type : 'string'
			}, // 总票数
			{
				name : 'goodsWeightTotal',
				type : 'string'
			}, // 总重量（千克）
			{
				name : 'goodsVolumeTotal',
				type : 'string'
			}, // 总体积（立方）
			{
				name : 'goodsQtyTotal',
				type : 'string'
			}, // 总件数
			{
				name : 'arrivegoodsQtyTotal',
				type : 'string'
			}, // 到达总件数
			{
				name : 'stockgoodsQtyTotal',
				type : 'string'
			}, // 库存总件数
			{
				name : 'prePayAmountTotal',
				type : 'string'
			}, // 预付金额（总）
			{
				name : 'toPayAmountTotal',
				type : 'string'
			}, // 到付金额（总）
			{
				name : 'codAmountTotal',
				type : 'string'
			}, // 代收货款（总）
			{
				name : 'allAmountTotal',
				type : 'string'
			} // 收款总额（总）
		]
	});

// 创建一个查询货量store
Ext.define('Foss.predeliver.arriveDeliverManager.Store.arriveDeliverManagerStore', {
		extend : 'Ext.data.Store',
		// 绑定一个模型
		model : 'Foss.arriveDeliverManager.Model.arriveDeliverInfoModel',
		// 是否自动查询
		autoLoad : false,
		pageSize : 2000,
		proxy : {
			type : 'ajax',
			url : predeliver.realPath('queryArriveDeliverByParams.action'),
			actionMethods : 'post',
			timeout : 600000,
			reader : {
				type : 'json',
				root : 'arriveDeliverVo.arriveDeliverDtoList',
				totalProperty : 'totalCount' // 总数
			}
		},// 事件监听
		listeners : {
			// 查询事件
			beforeload : function(s, operation, eOpts) {
				// 执行查询
				var queryParams = predeliver.arriveDeliverManager.queryInfoForPkpForm.getValues();
				Ext.apply(operation, {
						params : {
							'arriveDeliverVo.arriveDeliverQueryDto.billTimeStart' : queryParams.billTimeStart,/* 开单时间起 */
							'arriveDeliverVo.arriveDeliverQueryDto.billTimeEnd' : queryParams.billTimeEnd,/* 开单时间止 */
							'arriveDeliverVo.arriveDeliverQueryDto.arriveTimeStart' : queryParams.arriveTimeStart,/* 到达时间起 */
							'arriveDeliverVo.arriveDeliverQueryDto.arriveTimeEnd' : queryParams.arriveTimeEnd,/* 到达时间止 */
							'arriveDeliverVo.arriveDeliverQueryDto.inStockTimeStart' : queryParams.inStockTimeStart,/* 入库时间起 */
							'arriveDeliverVo.arriveDeliverQueryDto.inStockTimeEnd' : queryParams.inStockTimeEnd,/* 入库时间止 */
							'arriveDeliverVo.arriveDeliverQueryDto.signTimeStart' : queryParams.signTimeStart,/* 签收时间起 */
							'arriveDeliverVo.arriveDeliverQueryDto.signTimeEnd' : queryParams.signTimeEnd,/* 签收时间止 */
							'arriveDeliverVo.arriveDeliverQueryDto.operateTimeStart' : queryParams.operateTimeStart,/* 送货时间起 */
							'arriveDeliverVo.arriveDeliverQueryDto.operateTimeEnd' : queryParams.operateTimeEnd,/* 送货时间止 */
							'arriveDeliverVo.arriveDeliverQueryDto.deliverStatus' : queryParams.deliverStatus,/* 派送情况 */
							'arriveDeliverVo.arriveDeliverQueryDto.receiveCustomerContact' : queryParams.receiveCustomerContact,/* 收货人姓名 */
							'arriveDeliverVo.arriveDeliverQueryDto.receiveCustomerAddress' : queryParams.receiveCustomerAddress,/* 地址 */
							'arriveDeliverVo.arriveDeliverQueryDto.receiveCustomerPhone' : queryParams.receiveCustomerPhone,/* 电话 */
							'arriveDeliverVo.arriveDeliverQueryDto.receiveCustomerMobilephone' : queryParams.receiveCustomerMobilephone,/* 手机 */
							'arriveDeliverVo.arriveDeliverQueryDto.waybillNo' : queryParams.waybillNo,/* 运单号 */
							'arriveDeliverVo.arriveDeliverQueryDto.targetOrgCode' : queryParams.targetOrgCode,/* 目的站 */
							'arriveDeliverVo.arriveDeliverQueryDto.returnbillStatus' : queryParams.returnbillStatus,/* 返单状态 */
							'arriveDeliverVo.arriveDeliverQueryDto.returnbillType' : queryParams.returnbillType,/* 返单类型 */
							'arriveDeliverVo.arriveDeliverQueryDto.productCode' : queryParams.productCode,/* 运输性质 */
							'arriveDeliverVo.arriveDeliverQueryDto.transportType' : queryParams.transportType,/* 运输类型 */
							'arriveDeliverVo.arriveDeliverQueryDto.inStockSituation' : queryParams.inStockSituation,/* 入库情况 */
							'arriveDeliverVo.arriveDeliverQueryDto.receiveMethod' : queryParams.receiveMethod,/* 提货方式（全部、派送和自提） */
							'arriveDeliverVo.arriveDeliverQueryDto.situation' : queryParams.situation,/* 签收情况（全部签收，正常签收，部分签收，异常签收） */
							'arriveDeliverVo.arriveDeliverQueryDto.isSign' : queryParams.isSign,/* 是否签收（已签收、未签收） */
							'arriveDeliverVo.arriveDeliverQueryDto.signStatus' : queryParams.signStatus,/* 到达联签收状态 */
							'arriveDeliverVo.arriveDeliverQueryDto.isPassOwnDepartment' : queryParams.isPassOwnDepartment
							/* 是否经过到达部门 */

						}
					});
			},
			load : function(store, records, successful, eOpts) {
				var arriveDeliverGrid = Ext.getCmp('Foss_arriveDeliverManager_arriveDeliverManagerGrid_Id');
				var arriveDeliverTotalForm = Ext.getCmp('Foss_arriveDeliverManager_arriveDeliverTotalManagerForm_Id');
				var data = store.getProxy().getReader().rawData;
				if (!data.success && (!data.isException)) {
					arriveDeliverGrid.getStore().removeAll();// 清除所有
					arriveDeliverTotalForm.getForm().reset();
					Ext.ux.Toast.msg(predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.tip'), data.message, 'error',
						2000); // 提示
				}
				if (data.success) {
					// var json = Ext.decode(response.responseText);
					// arriveDeliverGrid.getStore().removeAll();//清除所有信息
					arriveDeliverTotalForm.getForm().reset();
					if (data.arriveDeliverVo.arriveDeliverDtoList != null) {
						// 加载到达派送信息到GridPanel里
						// arriveDeliverGrid.getStore().loadData(json.arriveDeliverVo.arriveDeliverDtoList);
						// 得到总计信息
						var arriveDeliverTotalModel = Ext.ModelManager.create(data.arriveDeliverVo.arriveDeliverTotalDto,
							'Foss.arriveDeliverManager.Model.arriveDeliverTotalInfoModel');
						arriveDeliverTotalForm.loadRecord(arriveDeliverTotalModel);
					}
				}
			}
		},
		sorters : [{
				property : 'isSign',
				direction : 'ASC'
			}]
	});

// 查询条件录入
Ext.define('Foss.predeliver.arriveDeliverManager.Form.QueryInfoForPkpForm', {
	extend : 'Ext.form.Panel',
	id : 'Foss_predeliver_arriveDeliverManager_Form_QueryInfoForPkpForm_Id',
	title : predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.queryParams'), // 查询条件
	frame : true,
	defaults : {
		margin : '0 0 0 0',
		// padding:'1 1 1 1',
		labelWidth : 80
	},
	defaultType : 'textfield',
	layout : 'column',
	timeValidate : function() {
		var form = Ext.getCmp('Foss_predeliver_arriveDeliverManager_Form_QueryInfoForPkpForm_Id').getForm(), signTimeStart = form
			.findField('signTimeStart'), signTimeEnd = form.findField('signTimeEnd'), productCode = form.findField('productCode').getValue();
		if (!Ext.isEmpty(signTimeStart.getValue()) && (!Ext.isEmpty(signTimeEnd.getValue()))) {
			if (!Ext.isEmpty(productCode) && predeliver.productCodeWVH == productCode) {
				form.findField('isPassOwnDepartment').setDisabled(false);// "是否经过到达部门“可编辑
			} else {
				form.findField('isPassOwnDepartment').setDisabled(true);// "是否经过到达部门“不可编辑
			}
		} else {
			form.findField('isPassOwnDepartment').setDisabled(true);// "是否经过到达部门“不可编辑
		}
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.items = [{
				name : 'receiveCustomerContact',
				fieldLabel : predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.receiveCustomerContact'),// 收货人姓名
				columnWidth : 0.25
			}, {
				name : 'receiveCustomerAddress',
				fieldLabel : predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.receiveCustomerAddress'),// 收货人地址
				columnWidth : 0.25
			}, {
				name : 'receiveCustomerPhone',
				fieldLabel : predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.receiveCustomerPhone'),// 收货人电话
				columnWidth : 0.25
			}, {
				name : 'receiveCustomerMobilephone',
				fieldLabel : predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.receiveCustomerMobilephone'),// 收货人手机
				columnWidth : 0.25
			}, {
				name : 'waybillNo',
				fieldLabel : predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.waybillNo'),// 单号
				vtype : 'waybill',
				columnWidth : 0.25
			}, {
				name : 'targetOrgCode',
				fieldLabel : predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.targetOrgCode'),// 目的站
				columnWidth : 0.25
			}, {
				fieldLabel : predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.deliverStatus'),// 派送情况
				xtype : 'combobox',
				name : 'deliverStatus',
				columnWidth : .25,
				store : FossDataDictionary.getDataDictionaryStore('PKP_DELIVERBILL_STATUS', null, {
						'valueCode' : '',
						'valueName' : predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.all')
					}),
				value : '',
				displayField : 'valueName',
				valueField : 'valueCode',
				queryMode : 'local',
				triggerAction : 'all',
				editable : false
			}, {
				xtype : 'combobox',
				displayField : 'name',
				valueField : 'code',
				queryMode : 'local',
				triggerAction : 'all',
				editable : false,
				name : 'returnbillStatus',
				fieldLabel : predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.returnbillStatus'),// 返单状态
				store : Ext.create('Foss.predeliver.arriveDeliverManager.returnBillStatusStore'),
				columnWidth : 0.25,
				value : ''
			}, {
				xtype : 'combobox',
				displayField : 'valueName',
				valueField : 'valueCode',
				queryMode : 'local',
				triggerAction : 'all',
				editable : false,
				name : 'returnbillType',
				fieldLabel : predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.returnbillType'),// 返单类型
				store : predeliver.arriveDeliverManager.returnBillTypeStore,
				columnWidth : 0.25,
				value : ''
			}, {
				xtype : 'combobox',
				displayField : 'name',
				valueField : 'code',
				queryMode : 'local',
				triggerAction : 'all',
				editable : false,
				name : 'inStockSituation',
				fieldLabel : predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.inStockSituation'),// 入库情况
				columnWidth : 0.25,
				value : '',
				store : Ext.create('Foss.predeliver.arriveDeliverManager.inStockSituationStore')
			}, {
				xtype : 'combobox',
				displayField : 'name',
				valueField : 'code',
				queryMode : 'local',
				triggerAction : 'all',
				editable : false,
				name : 'isSign',
				fieldLabel : predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.isSign'),// 是否签收（已签收、未签收）
				columnWidth : 0.25,
				value : '',
				store : Ext.create('Foss.predeliver.arriveDeliverManager.situationStore')
			}, {
				name : 'receiveMethod',
				fieldLabel : predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.receiveMethod'),// 提货方式（全部、派送和自提）
				xtype : 'combo',
				displayField : 'valueName',
				valueField : 'valueCode',
				queryMode : 'local',
				triggerAction : 'all',
				value : '',
				editable : false,
				columnWidth : 0.25,
				store : Ext.create('Foss.predeliver.arriveDeliverManager.ReceiveMethodStore')

			}, {
				xtype : 'rangeDateField',
				fieldLabel : predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.billTime'),// 开单时间
				fieldId : 'Foss_predeliver_arriveDeliverManager_Form_QueryInfoForPkpForm_billTime_ID',
				dateType : 'datetimefield_date97',
				fromName : 'billTimeStart',
				dateRange : 7,
				toName : 'billTimeEnd',
				columnWidth : .5
			}, {
				xtype : 'rangeDateField',
				fieldLabel : predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.arriveTime'),// 到达时间
				fieldId : 'Foss_predeliver_arriveDeliverManager_Form_QueryInfoForPkpForm_arriveTime_ID',
				dateType : 'datetimefield_date97',
				fromName : 'arriveTimeStart',
				dateRange : 7,
				toName : 'arriveTimeEnd',
				columnWidth : .495
			}, {
				xtype : 'rangeDateField',
				fieldLabel : predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.inStockTime'),// 入库时间
				fieldId : 'Foss_predeliver_arriveDeliverManager_Form_QueryInfoForPkpForm_inStockTime_ID',
				dateType : 'datetimefield_date97',
				dateRange : 7,
				fromName : 'inStockTimeStart',
				toName : 'inStockTimeEnd',
				columnWidth : .5
			}, {
				xtype : 'rangeDateField',
				fieldLabel : predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.operateTime'),// 送货时间
				fieldId : 'Foss_predeliver_arriveDeliverManager_Form_QueryInfoForPkpForm_operateTime_ID',
				dateType : 'datetimefield_date97',
				dateRange : 7,
				fromName : 'operateTimeStart',
				toName : 'operateTimeEnd',
				columnWidth : .495
			}, {
				xtype : 'fieldcontainer',
				columnWidth : .5,
				layout : 'column',
				fieldLabel : predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.signTime'),// 签收时间
				items : [{
						itemId : 'first',
						id : 'Foss_predeliver_arriveDeliverManager_Form_QueryInfoForPkpForm_signTime_ID_first',
						time : true,
						xtype : 'datetimefield_date97',
						name : 'signTimeStart',
						columnWidth : .5,
						dateConfig : {
							el : "Foss_predeliver_arriveDeliverManager_Form_QueryInfoForPkpForm_signTime_ID_first-inputEl",
							errDealMode : 1,
							maxDate : "#F{$dp.$D('Foss_predeliver_arriveDeliverManager_Form_QueryInfoForPkpForm_signTime_ID_second-inputEl')}",
							minDate : "#F{$dp.$D('Foss_predeliver_arriveDeliverManager_Form_QueryInfoForPkpForm_signTime_ID_second-inputEl', {d: -3})}",
							onpicked : function() {
								me.timeValidate();
							},
							oncleared : function() {
								me.timeValidate();
							},
							ychanged : function() {
								me.timeValidate();
							},
							Mchanged : function() {
								me.timeValidate();
							},
							dchanged : function() {
								me.timeValidate();
							},
							Hchanged : function() {
								me.timeValidate();
							},
							mchanged : function() {
								me.timeValidate();
							},
							schanged : function() {
								me.timeValidate();
							}
						}
					}, {
						xtype : 'label',
						html : '&nbsp;&nbsp;至&nbsp;&nbsp;',
						style : {
							textAlign : 'center',
							marginTop : '5px'
						}
					}, {
						itemId : 'second',
						id : 'Foss_predeliver_arriveDeliverManager_Form_QueryInfoForPkpForm_signTime_ID_second',
						time : true,
						xtype : 'datetimefield_date97',
						name : 'signTimeEnd',
						columnWidth : .5,
						dateConfig : {
							el : "Foss_predeliver_arriveDeliverManager_Form_QueryInfoForPkpForm_signTime_ID_second-inputEl",
							errDealMode : 1,
							maxDate : "#F{$dp.$D('Foss_predeliver_arriveDeliverManager_Form_QueryInfoForPkpForm_signTime_ID_first-inputEl', {d: 3})}",
							minDate : "#F{$dp.$D('Foss_predeliver_arriveDeliverManager_Form_QueryInfoForPkpForm_signTime_ID_first-inputEl')}",
							onpicked : function() {
								me.timeValidate();
							},
							oncleared : function() {
								me.timeValidate();
							},
							ychanged : function() {
								me.timeValidate();
							},
							Mchanged : function() {
								me.timeValidate();
							},
							dchanged : function() {
								me.timeValidate();
							},
							Hchanged : function() {
								me.timeValidate();
							},
							mchanged : function() {
								me.timeValidate();
							},
							schanged : function() {
								me.timeValidate();
							}
						}
					}]
			}, {
				name : 'productCode',
				fieldLabel : predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.productCode'),// 运输性质
				columnWidth : 0.25,
				xtype : 'combo',
				displayField : 'name',
				valueField : 'code',
				queryMode : 'local',
				triggerAction : 'all',
				value : '',
				editable : false,
				store : Ext.create('Foss.pkp.ProductStore'),
				listeners : {
					select : function(combo, records, eopts) {
						var form = this.up('form').getForm();
						var productCode = records[0].get('code');
						if (!Ext.isEmpty(productCode) && predeliver.productCodeWVH == productCode) {
							if ((!Ext.isEmpty(form.findField('signTimeEnd').getValue()))
								&& (!Ext.isEmpty(form.findField('signTimeStart').getValue()))) {
								form.findField('isPassOwnDepartment').setDisabled(false);// "是否经过到达部门“可编辑
							} else {
								form.findField('isPassOwnDepartment').setDisabled(true);// "是否经过到达部门“不可编辑
							}
						} else {
							form.findField('isPassOwnDepartment').setDisabled(true);// "是否经过到达部门“不可编辑
						}
					}
				}
			}, {
				name : 'transportType',
				fieldLabel : predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.transportType'),// 运输类型
				columnWidth : 0.25,
				xtype : 'combobox',
				valueField : 'valueCode',
				displayField : 'valueName',
				value : '',
				queryMode : 'local',
				triggerAction : 'all',
				store : FossDataDictionary.getDataDictionaryStore(predeliver.arriveDeliverManager.transportType, null, {
						'valueCode' : '',
						'valueName' : predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.all')
					})
			}, {
				xtype : 'combo',
				name : 'signStatus',
				fieldLabel : '到达联签收状态',
				forceSelection : true, // 只允许从下拉列表中进行选择，不能输入文本
				editable : false, // 不可编辑
				forceSelection : true,// 必须选择一个。
				valueField : 'valueCode',
				displayField : 'valueName',
				queryMode : 'local',
				margin : '5 0 0 0',
				triggerAction : 'all',
				value : '',
				columnWidth : .25,
				labelWidth: 100, 
				store :predeliver.arriveDeliverManager.signStatusStore
			},{
				xtype : 'combo',
				name : 'situation',
				fieldLabel : predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManager.situation'),// 签收情况
				forceSelection : true, // 只允许从下拉列表中进行选择，不能输入文本
				editable : false, // 不可编辑
				forceSelection : true,// 必须选择一个。
				valueField : 'valueCode',
				displayField : 'valueName',
				queryMode : 'local',
				margin : '5 0 0 0',
				triggerAction : 'all',
				value : '',
				columnWidth : .25,
				store : predeliver.arriveDeliverManager.situationStore
			}, {
				fieldLabel : predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.isPassOwnDepartment'),// 是否经过到达部门
				columnWidth : 0.26,
				name : 'isPassOwnDepartment',
				labelWidth : 120,
				allowBlank : true,
				margin : '5 0 0 5',
				inputValue : 'YES',
				xtype : 'checkbox',
				disabled : true
			}, {
				text : predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.reset'),// 重置
				columnWidth : .08,
				margin : '5 0 0 0',
				xtype : 'button',
				handler : function() {
					var form = this.up('form').getForm();
					form.reset();
					form.findField('isPassOwnDepartment').setDisabled(true);// "是否经过到达部门“不可编辑
				}
			}, {
				xtype : 'container',
				border : false,
				margin : '5 0 0 0',
				columnWidth : .06,
				html : '&nbsp;'
			}, {
				text : predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.search'),// 查询
				// hidden:!predeliver.arriveDeliverManager.isPermission('arriveDeliverManagerindex/arriveDeliverManagerindexquerybutton'),
				cls : 'yellow_button',
				margin : '5 0 0 0',
				xtype : 'button',
				columnWidth : .08,
				plugins : Ext.create('Deppon.ux.ButtonLimitingPlugin', {
						// 设定间隔秒数,如果不设置，默认为2秒
						seconds : 5
					}),
				handler : function() {
					var queryForm = this.up('form').getForm(), queryParams = queryForm.getValues();
					var billTimeStart = queryParams.billTimeStart, billTimeEnd = queryParams.billTimeEnd, arriveTimeStart = queryParams.arriveTimeStart, arriveTimeEnd = queryParams.arriveTimeEnd, inStockTimeStart = queryParams.inStockTimeStart, inStockTimeEnd = queryParams.inStockTimeEnd, signTimeStart = queryParams.signTimeStart, signTimeEnd = queryParams.signTimeEnd, operateTimeStart = queryParams.operateTimeStart, operateTimeEnd = queryParams.operateTimeEnd;
					if (!queryForm.findField('waybillNo').isValid()) {
						Ext.ux.Toast.msg(predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.tip'),
							predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.waybillIsNotValid'), 'error', 3000);// 运单号输入有误，不能进行查询
						return;
					}
					if (Ext.isEmpty(queryParams.waybillNo) && Ext.isEmpty(billTimeStart) && Ext.isEmpty(billTimeEnd) && Ext.isEmpty(arriveTimeStart)
						&& Ext.isEmpty(arriveTimeEnd) && Ext.isEmpty(inStockTimeStart) && Ext.isEmpty(inStockTimeEnd) && Ext.isEmpty(signTimeStart)
						&& Ext.isEmpty(signTimeEnd) && Ext.isEmpty(operateTimeStart) && Ext.isEmpty(operateTimeEnd)) {
						Ext.ux.Toast.msg(predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.tip'),
							predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.DateChooseLessOne'), 'error', 3000);// 日期区间至少选择一条进行查询
						return;
					} else if ((Ext.isEmpty(billTimeStart) && (!Ext.isEmpty(billTimeEnd)))
						|| (!Ext.isEmpty(billTimeStart) && (Ext.isEmpty(billTimeEnd)))) {
						Ext.ux.Toast.msg(predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.tip'),
							predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.billTimeNotNull'), 'error', 3000);// 开单起止日期不能为空
						return;
					} else if ((Ext.isEmpty(arriveTimeStart) && (!Ext.isEmpty(arriveTimeEnd)))
						|| (!Ext.isEmpty(arriveTimeStart) && (Ext.isEmpty(arriveTimeEnd)))) {
						Ext.ux.Toast.msg(predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.tip'),
							predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.arriveTimeNotNull'), 'error', 3000);// 到达起止日期不能为空
						return;
					} else if ((Ext.isEmpty(inStockTimeStart) && (!Ext.isEmpty(inStockTimeEnd)))
						|| (!Ext.isEmpty(inStockTimeStart) && (Ext.isEmpty(inStockTimeEnd)))) {
						Ext.ux.Toast.msg(predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.tip'),
							predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.inStockTimeNotNull'), 'error', 3000);// 入库起止日期不能为空
						return;
					} else if ((Ext.isEmpty(signTimeStart) && (!Ext.isEmpty(signTimeEnd)))
						|| (!Ext.isEmpty(signTimeStart) && (Ext.isEmpty(signTimeEnd)))) {
						Ext.ux.Toast.msg(predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.tip'),
							predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.signTimeNotnull'), 'error', 3000);// 签收起止日期不能为空
						return;
					} else if ((Ext.isEmpty(operateTimeStart) && (!Ext.isEmpty(operateTimeEnd)))
						|| (!Ext.isEmpty(operateTimeStart) && (Ext.isEmpty(operateTimeEnd)))) {
						Ext.ux.Toast.msg(predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.tip'),
							predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.operateTimeNotNull'), 'error', 3000);// 派送起止日期不能为空
						return;
					} else if (!Ext.isEmpty(operateTimeStart) && (!Ext.isEmpty(operateTimeEnd)) && Ext.isEmpty(arriveTimeStart)) {
						Ext.ux.Toast.msg(predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.tip'),
							predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.operateTimeArriveTimeNotnull'), 'error',
							3000);// 送货时间不为空时到达起止日期也不能为空
						return;
					}

					var arriveDeliverGrid = Ext.getCmp('Foss_arriveDeliverManager_arriveDeliverManagerGrid_Id');
					// 更新查询结果里表格的记录
					Ext.getCmp('Foss_arriveDeliverManager_arriveDeliverManagerGrid_Id').getPagingToolbar().moveFirst();

				}
			}];
		me.callParent([cfg]);
	}
});

// 到达派送信息
Ext.define('Foss.arriveDeliverManager.arriveDeliverManagerGrid', {
	extend : 'Ext.grid.Panel',
	// 增加表格列的分割线
	columnLines : true,
	id : 'Foss_arriveDeliverManager_arriveDeliverManagerGrid_Id',
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	height : 400,
	emptyText : predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.queryEmpty'),// 查询结果为空
	// 表格对象增加一个边框
	frame : true,
	stripeRows : true,
	// 增加表格列的分割线
	columnLines : true,
	// 定义表格的标题
	title : predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.arriveDeliverMess'),// 到达派送信息
	collapsible : true,
	animCollapse : true,
	// 自动增加滚动条
	autoScroll : true,
	store : null,
	viewConfig : {
		stripeRows : false,
		enableTextSelection : true,
		getRowClass : function(record, rowIndex, rp, ds) {
			if (record.get('isSign') == FossDataDictionary.rendererDisplayToSubmit('是', 'PKP_IS_SIGN')) {
				return 'predeliver_row_blue';
			}
		}
	},
	// 定义表格列信息
	columns : [{
			header : predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.rownumberer'),// 序列号
			xtype : 'rownumberer',
			width : 47
		}, {
			xtype : 'actioncolumn',
			header : predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.Mark'),
			width : 40,
			align : 'center',
			items : [{
				iconCls : 'foss_icons_pkp_exceptionSec',
				getClass : function(value, metadata, record, rowIndex, colIndex, store) {
					if (record.get('stockType') == FossDataDictionary.rendererSubmitToDisplay('库存中', 'PKP_IN_STOCK')
						&& record.get('stockGoodsQty') != record.get('goodsQtyTotal')) {
						return 'foss_icons_pkp_exceptionSec';
					} else {
						return 'foss_icons_pkp_exceptionSec_hide';
					}
				}
			}]
		}, {
			// 字段标题
			header : predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.waybillNo'),// 单号
			// 关联model中的字段名
			dataIndex : 'waybillNo',
			width : 90
		}, {
			// 字段标题
			header : predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.arrivesheetNo'),// 到达联编号
			// 关联model中的字段名
			dataIndex : 'arrivesheetNo',
			xtype : 'ellipsiscolumn',
			width : 100
		}, {
			// 字段标题
			header : predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.receiveMethod'),// 开单提货方式
			// 关联model中的字段名
			dataIndex : 'receiveMethod',
			xtype : 'ellipsiscolumn',
			width : 105
		}, {
			// 字段标题
			header : predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.arriveTime'),// 到达时间
			// 关联model中的字段名
			dataIndex : 'arriveTime',
			align : 'center',
			renderer : function(value) {
				if (value != null) {
					var date = Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
					return date;
				} else {
					return null;
				}
			},
			width : 130
		}, {
			// 字段标题
			header : predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.inStockTime'),// 入库时间
			// 关联model中的字段名
			dataIndex : 'inStockTime',
			align : 'center',
			renderer : function(value) {
				if (value != null) {
					var date = Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
					return date;
				} else {
					return null;
				}
			},
			width : 130
		}, {
			// 字段标题
			header : predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.receiveCustomerContact'),// 收货人姓名
			// 关联model中的字段名
			xtype : 'ellipsiscolumn',
			dataIndex : 'receiveCustomerContact',
			width : 90
		}, {
			// 字段标题 开单件数
			header : predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.goodsQtyTotal'),// 开单件数
			// 关联model中的字段名
			dataIndex : 'goodsQtyTotal',
			width : 80
		}, {
			// 字段标题
			header : predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.goodsWeightTotal'),// 开单重量
			// 关联model中的字段名
			dataIndex : 'goodsWeightTotal',
			width : 105
		}, {
			// 字段标题
			header : predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.goodsVolumeTotal'),// 开单体积
			// 关联model中的字段名
			dataIndex : 'goodsVolumeTotal',
			width : 100
		}, {
			// 字段标题
			header : predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.codAmount'),// 代收货款
			// 关联model中的字段名
			dataIndex : 'codAmount',
			width : 80
		}, {
			// 字段标题
			header : predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.toPayAmount'),// 到付金额
			// 关联model中的字段名
			dataIndex : 'toPayAmount',
			width : 75
		}, {
			// 字段标题
			header : predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.repaymentCodeAmount'),// 已付金额
			// 关联model中的字段名
			dataIndex : 'repaymentCodeAmount',
			width : 95
		}, {
			// 字段标题
			header : predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.prePayAmount'),// 预付金额
			// 关联model中的字段名
			dataIndex : 'prePayAmount',
			width : 75
		}, {
			// 字段标题
			header : predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.codToPayAmount'),// 收款总额
			// 关联model中的字段名
			dataIndex : 'codToPayAmount',
			width : 75
		}, {
			// 字段标题
			header : predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.settleStatus'),// 是否已结清
			// 关联model中的字段名
			dataIndex : 'settleStatus',
			width : 85
		}, {
			// 字段标题
			header : predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.receiveOrgCode'),// 出发部门
			// 关联model中的字段名
			dataIndex : 'receiveOrgCode',
			xtype : 'ellipsiscolumn',
			width : 120
		}, {
			// 字段标题
			header : predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.driverName'),// 送货人或代理
			// 关联model中的字段名
			dataIndex : 'driverName',
			xtype : 'ellipsiscolumn',
			width : 110
		}, {
			// 字段标题
			header : predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.billTime'),// 收货日期
			// 关联model中的字段名
			dataIndex : 'billTime',
			align : 'center',
			renderer : function(value) {
				if (value != null) {
					var date = Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
					return date;
				} else {
					return null;
				}
			},
			width : 130
		}, {
			// 字段标题
			header : predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.receiveCustomerAddress'),// 收货人地址
			// 关联model中的字段名
			dataIndex : 'receivePCDAddress',
			xtype : 'ellipsiscolumn',
			width : 180
		}, {
			// 字段标题
			header : predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.receiveCustomerPhone'),// 收货人电话
			// 关联model中的字段名
			dataIndex : 'receiveCustomerPhone',
			xtype : 'ellipsiscolumn',
			width : 120
		}, {
			// 字段标题
			header : predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.receiveCustomerMobilephone'),// 收货人手机
			// 关联model中的字段名
			dataIndex : 'receiveCustomerMobilePhone',
			xtype : 'ellipsiscolumn',
			width : 100
		}, {
			// 字段标题
			header : predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.arriveGoodsQty'),// 到达件数
			// 关联model中的字段名
			dataIndex : 'arriveGoodsQty',
			width : 80
		}, {
			// 字段标题
			header : predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.stockType'),// 库存状态
			// 关联model中的字段名
			dataIndex : 'stockType',
			width : 80
		}, {
			// 字段标题
			header : predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.orgCode'),// 库存部门
			// 关联model中的字段名
			dataIndex : 'orgCode',
			xtype : 'ellipsiscolumn',
			width : 100
		}, {
			// 字段标题
			header : predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.stockGoodsQty'),// 库存件数
			// 关联model中的字段名
			dataIndex : 'stockGoodsQty',
			xtype : 'ellipsiscolumn',
			width : 80
		}, {
			// 字段标题
			header : predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.notificationResult'),// 是否通知成功(最新通知状态)
			// 关联model中的字段名
			dataIndex : 'notificationResult',
			width : 100
		}, {
			// 字段标题
			header : predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.deliveryMan'),// 取货人
			// 关联model中的字段名
			dataIndex : 'deliveryMan',
			xtype : 'ellipsiscolumn',
			width : 80
		}, {
			// 字段标题
			header : predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.returnBillType'),// 返单类型
			// 关联model中的字段名
			dataIndex : 'returnBillType',
			xtype : 'ellipsiscolumn',
			width : 95
		}, {
			// 字段标题
			header : predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.returnBillStatus'),// 返单情况
			// 关联model中的字段名
			dataIndex : 'returnBillStatus',
			xtype : 'ellipsiscolumn',
			width : 95
		}, {
			// 字段标题
			header : predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.isArrange'),// 是否已排单
			// 关联model中的字段名
			dataIndex : 'isArrange',
			width : 85
		}, {
			// 字段标题
			header : predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.deliverbillNo'),// 派送单单号
			// 关联model中的字段名
			dataIndex : 'deliverbillNo',
			xtype : 'ellipsiscolumn',
			width : 95
		}, {
			// 字段标题
			header : predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.deliverbillStatus'),// 派送情况
			// 关联model中的字段名
			dataIndex : 'deliverbillStatus',
			xtype : 'ellipsiscolumn',
			width : 85
		}, {
			// 字段标题
			header : predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.isSign'),// 是否签收
			// 关联model中的字段名
			dataIndex : 'isSign',
			sortable : true,
			width : 80
		}, {
			// 字段标题
			header : predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.signTime'),// 签收时间
			// 关联model中的字段名
			dataIndex : 'signTime',
			align : 'center',
			renderer : function(value) {
				if (value != null) {
					var date = Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
					return date;
				} else {
					return null;
				}
			},
			width : 130
		}, {
			// 字段标题
			header : predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManager.situation'),// 签收情况
			// 关联model中的字段名
			dataIndex : 'situation',
			xtype : 'ellipsiscolumn',
			width : 100
		}, {
			// 字段标题
			header : predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManager.signNote'),// 签收备注
			// 关联model中的字段名
			dataIndex : 'signNote',
			xtype : 'ellipsiscolumn',
			width : 80
		}, {
			// 字段标题
			header : predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.cargoName'),// 货物品名
			// 关联model中的字段名
			dataIndex : 'cargoName',
			width : 80
		}, {
			// 字段标题
			header : predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.deliverGoodsFee'),// 送货费
			// 关联model中的字段名
			dataIndex : 'deliverGoodsFee',
			width : 80
		}, {
			// 字段标题
			header : predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.insuranceValue'),// 保价声明价值
			// 关联model中的字段名
			dataIndex : 'insuranceValue',
			width : 80
		}, {
			// 字段标题
			header : predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.insuranceFee'),// 保价费
			// 关联model中的字段名
			dataIndex : 'insuranceFee',
			width : 80
		}, {
			// 字段标题
			header : predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.otherFee'),// 其他费用
			// 关联model中的字段名
			dataIndex : 'otherFee',
			width : 80
		}, {
			// 字段标题
			header : predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.deliverRequire'),// 送货要求
			// 关联model中的字段名
			dataIndex : 'deliverRequire',
			xtype : 'ellipsiscolumn',
			// flex: 1
			width : 80
		}, {
			// 字段标题
			header : predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.operateTime'),// 送货时间
			// 关联model中的字段名
			dataIndex : 'operateTime',
			renderer : function(value) {
				if (value != null) {
					var date = Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
					return date;
				} else {
					return null;
				}
			},
			xtype : 'ellipsiscolumn',
			// flex: 1
			width : 130
		}, {
			// 字段标题
			header : predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.productCode'),// 运输性质
			// 关联model中的字段名
			dataIndex : 'productCode',
			width : 80
		}, {
			// 字段标题
			header : predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.targetOrgCode'),// 目的站
			// 关联model中的字段名
			dataIndex : 'targetOrgCode',
			xtype : 'ellipsiscolumn',
			// flex: 1
			width : 180
		}],
	pagingToolbar : null,
	getPagingToolbar : function() {
		if (this.pagingToolbar == null) {
			this.pagingToolbar = Ext.create('Deppon.StandardPaging', {
					store : this.store
				});
		}
		return this.pagingToolbar;
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.predeliver.arriveDeliverManager.Store.arriveDeliverManagerStore');
		me.bbar = me.getPagingToolbar();
		me.dockedItems = [{
			xtype : 'toolbar',
			dock : 'top',
			layout : 'hbox',
			defaults : {
				margin : '0 0 5 3',
				allowBlank : true
			},
			items : [{
					xtype : 'label',
					text : ' ',
					width : 20,
					height : 20,
					cls : 'predeliver_row_blue'
				}, {
					xtype : 'label',
					text : predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.isSigned')
				}, {
					xtype : 'image',
					imgCls : 'foss_icons_pkp_exceptionSec'
				}, {
					xtype : 'label',
					text : predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.storeNotMatch')
				}, '->', {
					xtype : 'button',
					allowBlank : true,
					name : 'exportDeliverbillDetail',
					width : 80,
					text : predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.exportDeliverbillDetail'),// 导出
					handler : function() {
						// 获取查询出来的异常信息
						var deliverGridStore = this.up('grid').getStore();
						if (deliverGridStore.getCount() != 0) {
							var queryForm = Ext.getCmp('Foss_predeliver_arriveDeliverManager_Form_QueryInfoForPkpForm_Id').getForm(), queryParams = queryForm
								.getValues();
							var billTimeStart = queryParams.billTimeStart;
							billTimeEnd = queryParams.billTimeEnd;
							arriveTimeStart = queryParams.arriveTimeStart;
							arriveTimeEnd = queryParams.arriveTimeEnd;
							inStockTimeStart = queryParams.inStockTimeStart;
							inStockTimeEnd = queryParams.inStockTimeEnd;
							signTimeStart = queryParams.signTimeStart;
							signTimeEnd = queryParams.signTimeEnd;
							operateTimeStart = queryParams.operateTimeStart;
							operateTimeEnd = queryParams.operateTimeEnd;
							if (!queryForm.findField('waybillNo').isValid()) {
								Ext.ux.Toast
									.msg(predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.tip'),
										predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.waybillIsNotValid'), 'error',
										3000);// 运单号输入有误，不能进行查询
								return;
							}
							if (Ext.isEmpty(queryParams.waybillNo) && Ext.isEmpty(billTimeStart) && Ext.isEmpty(billTimeEnd)
								&& Ext.isEmpty(arriveTimeStart) && Ext.isEmpty(arriveTimeEnd) && Ext.isEmpty(inStockTimeStart)
								&& Ext.isEmpty(inStockTimeEnd) && Ext.isEmpty(signTimeStart) && Ext.isEmpty(signTimeEnd)
								&& Ext.isEmpty(operateTimeStart) && Ext.isEmpty(operateTimeEnd)) {
								Ext.ux.Toast
									.msg(predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.tip'),
										predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.DateChooseLessOne'), 'error',
										3000);// 日期区间至少选择一条进行查询
								return;
							} else if ((Ext.isEmpty(billTimeStart) && (!Ext.isEmpty(billTimeEnd)))
								|| (!Ext.isEmpty(billTimeStart) && (Ext.isEmpty(billTimeEnd)))) {
								Ext.ux.Toast.msg(predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.tip'),
									predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.billTimeNotNull'), 'error', 3000);// 开单起止日期不能为空
								return;
							} else if ((Ext.isEmpty(arriveTimeStart) && (!Ext.isEmpty(arriveTimeEnd)))
								|| (!Ext.isEmpty(arriveTimeStart) && (Ext.isEmpty(arriveTimeEnd)))) {
								Ext.ux.Toast
									.msg(predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.tip'),
										predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.arriveTimeNotNull'), 'error',
										3000);// 到达起止日期不能为空
								return;
							} else if ((Ext.isEmpty(inStockTimeStart) && (!Ext.isEmpty(inStockTimeEnd)))
								|| (!Ext.isEmpty(inStockTimeStart) && (Ext.isEmpty(inStockTimeEnd)))) {
								Ext.ux.Toast.msg(predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.tip'),
									predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.inStockTimeNotNull'), 'error',
									3000);// 入库起止日期不能为空
								return;
							} else if ((Ext.isEmpty(signTimeStart) && (!Ext.isEmpty(signTimeEnd)))
								|| (!Ext.isEmpty(signTimeStart) && (Ext.isEmpty(signTimeEnd)))) {
								Ext.ux.Toast.msg(predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.tip'),
									predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.signTimeNotnull'), 'error', 3000);// 签收起止日期不能为空
								return;
							} else if ((Ext.isEmpty(operateTimeStart) && (!Ext.isEmpty(operateTimeEnd)))
								|| (!Ext.isEmpty(operateTimeStart) && (Ext.isEmpty(operateTimeEnd)))) {
								Ext.ux.Toast.msg(predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.tip'),
									predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.operateTimeNotNull'), 'error',
									3000);// 派送起止日期不能为空
								return;
							} else if (!Ext.isEmpty(operateTimeStart) && (!Ext.isEmpty(operateTimeEnd)) && Ext.isEmpty(arriveTimeStart)) {
								Ext.ux.Toast.msg(predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.tip'),
									predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.operateTimeArriveTimeNotnull'),
									'error', 3000);// 送货时间不为空时到达起止日期也不能为空
								return;
							}
							var params = {
								'arriveDeliverVo.arriveDeliverQueryDto.billTimeStart' : queryParams.billTimeStart,/* 开单时间起 */
								'arriveDeliverVo.arriveDeliverQueryDto.billTimeEnd' : queryParams.billTimeEnd,/* 开单时间止 */
								'arriveDeliverVo.arriveDeliverQueryDto.arriveTimeStart' : queryParams.arriveTimeStart,/* 到达时间起 */
								'arriveDeliverVo.arriveDeliverQueryDto.arriveTimeEnd' : queryParams.arriveTimeEnd,/* 到达时间止 */
								'arriveDeliverVo.arriveDeliverQueryDto.inStockTimeStart' : queryParams.inStockTimeStart,/* 入库时间起 */
								'arriveDeliverVo.arriveDeliverQueryDto.inStockTimeEnd' : queryParams.inStockTimeEnd,/* 入库时间止 */
								'arriveDeliverVo.arriveDeliverQueryDto.signTimeStart' : queryParams.signTimeStart,/* 签收时间起 */
								'arriveDeliverVo.arriveDeliverQueryDto.signTimeEnd' : queryParams.signTimeEnd,/* 签收时间止 */
								'arriveDeliverVo.arriveDeliverQueryDto.operateTimeStart' : queryParams.operateTimeStart,/* 送货时间起 */
								'arriveDeliverVo.arriveDeliverQueryDto.operateTimeEnd' : queryParams.operateTimeEnd,/* 送货时间止 */
								'arriveDeliverVo.arriveDeliverQueryDto.deliverStatus' : queryParams.deliverStatus,/* 派送情况 */
								'arriveDeliverVo.arriveDeliverQueryDto.receiveCustomerContact' : queryParams.receiveCustomerContact,/* 收货人姓名 */
								'arriveDeliverVo.arriveDeliverQueryDto.receiveCustomerAddress' : queryParams.receiveCustomerAddress,/* 地址 */
								'arriveDeliverVo.arriveDeliverQueryDto.receiveCustomerPhone' : queryParams.receiveCustomerPhone,/* 电话 */
								'arriveDeliverVo.arriveDeliverQueryDto.receiveCustomerMobilephone' : queryParams.receiveCustomerMobilephone,/* 手机 */
								'arriveDeliverVo.arriveDeliverQueryDto.waybillNo' : queryParams.waybillNo,/* 运单号 */
								'arriveDeliverVo.arriveDeliverQueryDto.targetOrgCode' : queryParams.targetOrgCode,/* 目的站 */
								'arriveDeliverVo.arriveDeliverQueryDto.returnbillStatus' : queryParams.returnbillStatus,/* 返单状态 */
								'arriveDeliverVo.arriveDeliverQueryDto.returnbillType' : queryParams.returnbillType,/* 返单类型 */
								'arriveDeliverVo.arriveDeliverQueryDto.productCode' : queryParams.productCode,/* 运输性质 */
								'arriveDeliverVo.arriveDeliverQueryDto.transportType' : queryParams.transportType,/* 运输类型 */
								'arriveDeliverVo.arriveDeliverQueryDto.inStockSituation' : queryParams.inStockSituation,/* 入库情况 */
								'arriveDeliverVo.arriveDeliverQueryDto.receiveMethod' : queryParams.receiveMethod,/* 提货方式（全部、派送和自提） */
								'arriveDeliverVo.arriveDeliverQueryDto.isSign' : queryParams.isSign,/* 是否签收（已签收、未签收） */
								'arriveDeliverVo.arriveDeliverQueryDto.situation' : queryParams.situation,/* 签收情况（全部签收，正常签收，部分签收，异常签收） */
								'start' : deliverGridStore.proxy.reader.jsonData.start,/* 当前页数 */
								'arriveDeliverVo.arriveDeliverQueryDto.isPassOwnDepartment' : queryParams.isPassOwnDepartment,/* 是否经过到达部门 */
								'arriveDeliverVo.arriveDeliverQueryDto.signStatus' : queryParams.signStatus,/* 到达联签收状态 */
								'limit' : deliverGridStore.proxy.reader.jsonData.limit
								/* 每页显示 */
							}
							// 得到选中的流水号信息
							if (!Ext.fly('downloadAttachFileForm')) {
								var frm = document.createElement('form');
								frm.id = 'downloadAttachFileForm';
								frm.style.display = 'none';
								document.body.appendChild(frm);
							}
							Ext.Ajax.request({
									url : predeliver.realPath('exportArriveDeliverInfo.action'),
									form : Ext.fly('downloadAttachFileForm'),
									method : 'POST',
									params : params,
									isUpload : true
								});
						} else {
							// 或者提示不能导出
							Ext.ux.Toast
								.msg(predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.tip'),
									predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.resultIsNullNotExport'), 'error',
									3000);
						}
					}
				}]
		}], me.callParent([cfg]);
	}
});
// 合计
Ext.define('Foss.arriveDeliverManager.arriveDeliverTotalManagerForm.TotalForm', {
		extend : 'Ext.form.Panel',
		alias : 'widget.totalform',
		id : 'Foss_arriveDeliverManager_arriveDeliverTotalManagerForm_Id',
		items : [{
				xtype : 'fieldset',
				title : predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.allTotal'), // 合计
				defaultType : 'numberfield',
				defaults : {
					readOnly : true
				},
				layout : {
					type : 'table',
					columns : 4
				},
				items : [{
						fieldLabel : predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.totalVotes'), // 总票数
						name : 'totalVotes'
					}, {
						fieldLabel : predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.goodsQtyTotal'), // 总件数
						name : 'goodsQtyTotal'
					}, {
						fieldLabel : predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.goodsWeightTotal'), // 总重量（千克）
						name : 'goodsWeightTotal'
					}, {
						fieldLabel : predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.goodsVolumeTotal'), // 总体积（立方）
						name : 'goodsVolumeTotal'
					}, {
						fieldLabel : predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.arrivegoodsQtyTotal'), // 到达总件数
						name : 'arrivegoodsQtyTotal'
					}, {
						fieldLabel : predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.stockgoodsQtyTotal'), // 库存总件数
						name : 'stockgoodsQtyTotal'
					}, {
						fieldLabel : predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.prePayAmountTotal'), // 预付金额
						name : 'prePayAmountTotal'
					}, {
						fieldLabel : predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.toPayAmountTotal'), // 到付金额（总）
						name : 'toPayAmountTotal'
					}, {
						fieldLabel : predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.codAmountTotal'), // 代收货款（总）
						name : 'codAmountTotal'
					}, {
						fieldLabel : predeliver.arriveDeliverManager.i18n('pkp.predeliver.arriveDeliverManagerIndex.allAmountTotal'), // 收款总额（总）
						name : 'allAmountTotal'
					}]
			}]
	});
// 查询PKPform
predeliver.arriveDeliverManager.queryInfoForPkpForm = Ext.create('Foss.predeliver.arriveDeliverManager.Form.QueryInfoForPkpForm');
// 查询结果
predeliver.arriveDeliverManager.queryResult = Ext.create('Foss.arriveDeliverManager.arriveDeliverManagerGrid');

Ext.onReady(function() {
		Ext.QuickTips.init();
		Ext.create('Ext.panel.Panel', {
				id : 'T_predeliver-arriveDeliverIndex_content',
				cls : "panelContentNToolbar",
				bodyCls : 'panelContentNToolbar-body',
				layout : 'auto',
				items : [predeliver.arriveDeliverManager.queryInfoForPkpForm, predeliver.arriveDeliverManager.queryResult, {
						xtype : 'totalform'
					}],
				renderTo : 'T_predeliver-arriveDeliverIndex-body'
			});
	});
