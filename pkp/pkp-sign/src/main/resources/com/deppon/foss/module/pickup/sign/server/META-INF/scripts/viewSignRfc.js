sign.viewSignRfc.getTargetDate = function(date, day) {
	var d, s, t, t2;
	var MinMilli = 1000 * 60;
	var HrMilli = MinMilli * 60;
	var DyMilli = HrMilli * 24;
	t = Date.parse(date);
	t2 = new Date(t + day * DyMilli);
	t2.setHours(0);
	t2.setMinutes(0);
	t2.setSeconds(0);
	t2.setMilliseconds(0);
	return t2;
};

sign.viewSignRfc.getTargetDate1 = function(date, day) {
	var d, s, t, t2;
	var MinMilli = 1000 * 60;
	var HrMilli = MinMilli * 60;
	var DyMilli = HrMilli * 24;
	t = Date.parse(date);
	t2 = new Date(t + day * DyMilli);
	t2.setHours(23);
	t2.setMinutes(59);
	t2.setSeconds(59);
	t2.setMilliseconds(0);
	return t2;
};
// 定义签收变更model
Ext.define('Foss.sign.viewSignRfc.signRfcModel', {
			extend : 'Ext.data.Model',
			idgen : 'uuid',// EXT在前台为每个模型额外以UUID方式生成主键
			idProperty : 'extid',// 以上生成的主键用在名叫“extid”的列
			fields : [{
						name : ' extid ',
						type : 'string'
					},// 额外的用于生成的EXT使用的列
					{
						name : 'id',
						type : 'string'
					}, {
						name : 'waybillNo',
						type : 'string'
					}, {
						name : 'rfcType',
						type : 'string'

					}, {
						name : 'draftOrgName',
						type : 'string'
					}, {
						name : 'draftOrgCode',
						type : 'string'
					}, {
						name : 'drafter',
						type : 'string'
					}, {
						name : 'drafterCode',
						type : 'string'
					}, {
						name : 'draftTime',
						type : 'date',
						convert : dateConvert
					}, {
						name : 'reason',
						type : 'string'
					}, {
						name : 'notes',
						type : 'string'
					}, {
						name : 'status',
						convert : function(value) {
							return FossDataDictionary.rendererSubmitToDisplay(
									value, 'PKP_SIGN_RFC_APPROVE_STATUS');
						}
					}, {
						name : 'operator',
						type : 'string'
					}, {
						name : 'operatorCode',
						type : 'string'
					}, {
						name : 'operateOrgName',
						type : 'string'
					}, {
						name : 'operateOrgCode',
						type : 'string'
					}, {
						name : 'operateTime',
						type : 'date',
						convert : dateConvert
					}, {
						name : 'tSrvArrivesheetId',
						type : 'string'
					}, {
						name : 'tSrvRepaymentId',
						type : 'string'
					}, {
						name : 'tSrvWaybillSignResultId',
						type : 'string'
					}, {
						name : 'rfcNo',
						type : 'string'
					}]
		});
// 定义付款model
Ext.define('Foss.sign.viewSignRfc.RepaymentModel', {
			extend : 'Ext.data.Model',
			idgen : 'uuid',// EXT在前台为每个模型额外以UUID方式生成主键
			idProperty : 'extid',// 以上生成的主键用在名叫“extid”的列
			fields : [{
						name : ' extid ',
						type : 'string'
					},// 额外的用于生成的EXT使用的列
					{
						name : 'id', // ID
						type : 'string'
					}, {
						name : 'waybillNo', // 运单号
						type : 'string'
					}, {
						name : 'repaymentNo', // 付款编号
						type : 'string'
					}, {
						name : 'active', // 是否有效
						type : 'string'
					}, {
						name : 'consigneeCode', // 客户编码
						type : 'string'
					}, {
						name : 'consigneeName', // 客户名称
						type : 'string'
					}, {
						name : 'paymentType',
						convert : function(value) {// 付款方式
							return FossDataDictionary.rendererSubmitToDisplay(
									value, 'SETTLEMENT__PAYMENT_TYPE');
						}

					}, {
						name : 'claimNo', // 款项认领编号
						type : 'string'
					}, {
						name : 'actualFreight', // 实付运费
						type : 'Number'
					}, {
						name : 'codAmount', // 代收货款
						type : 'Number'
					}, {
						name : 'totMoney', // 货款总额
						type : 'Number'
					}, {
						name : 'paymentTime', // 付款时间
						type : 'date',
						convert : dateConvert
					}, {
						name : 'storageFee', // 仓储费
						type : 'Number'
					}, {
						name : 'operator', // 操作人
						type : 'string'
					}, {
						name : 'operatorCode', // 操作人编码
						type : 'string'
					}, {
						name : 'operateOrgName', // 操作部门
						type : 'string'
					}, {
						name : 'operateOrgCode', // 操作部门编码
						type : 'string'
					}, {
						name : 'currencyCode', // 币种 RMB
						type : 'string'
					}, {
						name : 'isRfcing', // 是否审批中
						type : 'string'
					}, {
						name : 'stlbillGeneratedStatus', // 是否已有财务单据
						type : 'string'
					}]

		});
// 定义到达联model
Ext.define('Foss.sign.viewSignRfc.ArrivesheetModel', {
			extend : 'Ext.data.Model',
			idgen : 'uuid',// EXT在前台为每个模型额外以UUID方式生成主键
			idProperty : 'extid',// 以上生成的主键用在名叫“extid”的列
			fields : [{
						name : ' extid ',
						type : 'string'
					},// 额外的用于生成的EXT使用的列
					{
						name : 'id', // ID
						type : 'string'
					}, {
						name : 'waybillNo', // 运单号
						type : 'string'
					}, {
						name : 'arrivesheetNo', // 到达联编号
						type : 'string'
					}, {
						name : 'deliverymanName', // 提货人名称
						convert : function(value) {//
							if (value == 'N/A') {
								return "";
							} else {
								return value;
							}
						}
					}, {
						name : 'identifyType',
						convert : function(value) {// 证件类型
							return FossDataDictionary.rendererSubmitToDisplay(
									value, 'PKP_CREDENTIAL_TYPE');
						}
					}, {
						name : 'identifyCode', // 证件号码
						type : 'string'
					}, {
						name : 'situation',
						convert : function(value) {// 签收情况
							return FossDataDictionary.rendererSubmitToDisplay(
									value, 'PKP_SIGN_SITUATION');
						}

					}, {
						name : 'arriveSheetGoodsQty', // 到达联件数
						type : 'Number'
					}, {
						name : 'signGoodsQty', // 签收件数
						type : 'Number'
					}, {
						name : 'signNote', // 签收备注
						type : 'string'
					}, {
						name : 'signTime', // 签收时间
						type : 'date',
						convert : dateConvert
					}, {
						name : 'isPrinted', // 是否打印
						type : 'string'
					}, {
						name : 'printtimes', // 打印次数
						type : 'Number'
					}, {
						name : 'createUserName', // 创建人
						type : 'string'
					}, {
						name : 'createUserCode', // 创建人编码
						type : 'string'
					}, {
						name : 'createOrgName', // 创建部门
						type : 'string'
					}, {
						name : 'createOrgCode', // 创建部门编码
						type : 'string'
					}, {
						name : 'createTime', // 创建时间
						type : 'date',
						convert : dateConvert
					}, {
						name : 'status', // 到达联状态
						type : 'string'
					}, {
						name : 'isPdaSign', // 是否PDA签到
						type : 'string'
					}, {
						name : 'active', // 是否有效
						type : 'string'
					}, {
						name : 'isSentRequired', // 是否必送货
						type : 'string'
					}, {
						name : 'isNeedInvoice', // 是否需要发票
						type : 'string'
					}, {
						name : 'preNoticeContent', // 提前通知内容
						type : 'string'
					}, {
						name : 'deliverRequire', // 送货要求
						type : 'string'
					}, {
						name : 'isRfcing', // 是否审批中
						type : 'string'
					}, {
						name : 'tagNumber', // 标签编号
						type : 'string'
					}]

		});
// 定义签收结果model
Ext.define('Foss.sign.viewSignRfc.WaybillSignResultModel', {
			extend : 'Ext.data.Model',
			idgen : 'uuid',// EXT在前台为每个模型额外以UUID方式生成主键
			idProperty : 'extid',// 以上生成的主键用在名叫“extid”的列
			fields : [{
						name : ' extid ',
						type : 'string'
					},// 额外的用于生成的EXT使用的列
					{
						name : ' signRfcId ',
						type : 'string'
					},// 申请变更ID
					{
						name : 'id', // ID
						type : 'string'
					}, {
						name : 'waybillNo', // 运单号
						type : 'string'
					}, {
						name : 'signSituation',// 签收情况
						convert : function(value) {
							return FossDataDictionary.rendererSubmitToDisplay(
									value, 'PKP_SIGN_SITUATION');
						}
					}, {
						name : 'deliverymanName', // 提货人名称
						type : 'string'
					}, {
						name : 'identifyType',// 证件类型
						convert : function(value) {
							return FossDataDictionary.rendererSubmitToDisplay(
									value, 'PKP_CREDENTIAL_TYPE');
						}
					}, {
						name : 'identifyCode', // 证件号码
						type : 'string'
					}, {
						name : 'settleStatus', // 结清状态
						type : 'string'
					}, {
						name : 'signGoodsQty', // 签收件数
						type : 'Number'
					}, {
						name : 'signNote', // 签收备注
						type : 'string'
					}, {
						name : 'signTime', // 签收时间
						type : 'date',
						convert : dateConvert
					}, {
						name : 'createTime', // 生效时间
						type : 'date',
						convert : dateConvert
					}, {
						name : 'modifyTime', // 时效时间
						type : 'date',
						convert : dateConvert
					}, {
						name : 'active', // 是否有效
						type : 'string'
					}, {
						name : 'isPdaSign', // 是否PDA签到
						type : 'string'
					}, {
						name : 'signStatus', // 签收状态
						type : 'string'
					}, {
						name : 'agentCode',
						type : 'string'
					}, {
						name : 'agentName',
						type : 'string'
					}, {
						name : 'isRfcing', // 是否审批中
						type : 'string'
					}]

		});
// 定义变更明细MODEL
Ext.define('Foss.sign.viewSignRfc.ChangeSignDetailModel', {
			extend : 'Ext.data.Model',
			idgen : 'uuid',// EXT在前台为每个模型额外以UUID方式生成主键
			idProperty : 'extid',// 以上生成的主键用在名叫“extid”的列
			fields : [{
						name : ' extid ',
						type : 'string'
					},// 额外的用于生成的EXT使用的列
					{
						name : 'rfcType',
						type : 'string'
					}, {
						name : 'rfcItems',
						type : 'string'
					},// 变更项
					{
						name : 'rfcItemsCode',
						type : 'string'
					},// 变更CODE
					{
						name : 'beforeRfcinfo',
						type : 'string'
					},// 变更前
					{
						name : 'afterRfcinfo',
						type : 'string'
					}// 变更后
			]
		});
// 定义签收变更数据store
Ext.define('Foss.sign.viewSignRfc.SignRfcStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.sign.viewSignRfc.signRfcModel',
	pageSize : 10,
	proxy : {
		// 代理的类型为内存代理
		type : 'ajax',
		actionMethods : 'POST',
		// url : '../sign/queryViewSignRfcList.action',
		url : sign.realPath('queryViewSignRfcList.action'),
		// 定义一个读取器
		reader : {
			// 以JSON的方式读取
			type : 'json',
			// 定义读取JSON数据的根对象
			root : 'vo.signRfcList',
			// 返回总数
			totalProperty : 'totalCount'
		}
	},// 事件监听
	listeners : {
		// 查询事件
		beforeload : function(s, operation, eOpts) {
			var  queryForm =Ext.getCmp('T_sign-viewSignRfcIndex_content').getQueryForm();
			// 执行查询，为全局变量，在查询条件的FORM创建时生成
			var queryParams = queryForm.getValues();
			var myform = queryForm.getForm();
			if (!myform.isValid()) {
				return false;
			}
			var waybillNo = queryParams.waybillNo;
			Ext.apply(operation, {
				params : {
					'vo.signResultDto.signRfcEntity.waybillNo' : queryParams.waybillNo,
					'vo.signResultDto.signRfcEntity.draftOrgCode' : queryParams.draftOrgCode,
					'vo.signResultDto.signRfcEntity.status' : queryParams.status,
					'vo.signResultDto.signRfcEntity.arrivesheetNo' : queryParams.arrivesheetNo,
					'vo.signResultDto.signRfcEntity.draftTimeStart' : queryParams.draftTimeStart,
					'vo.signResultDto.signRfcEntity.draftTimeEnd' : queryParams.draftTimeEnd

				}
			});
		}
	}
		// autoLoad: true
});
// 定义付款store
Ext.define('Foss.sign.viewSignRfc.RepaymentStore', {
			extend : 'Ext.data.Store',
			model : 'Foss.sign.viewSignRfc.RepaymentModel'
		});
// 定义到达联store
Ext.define('Foss.sign.viewSignRfc.ArriveSheetStore', {
			extend : 'Ext.data.Store',
			model : 'Foss.sign.viewSignRfc.ArrivesheetModel'
		});
// 定义签收结果store
Ext.define('Foss.sign.viewSignRfc.WaybillSignResultStore', {
			extend : 'Ext.data.Store',
			model : 'Foss.sign.viewSignRfc.WaybillSignResultModel'
		});
// 定义更改类型model
Ext.define('Foss.sign.viewSignRfc.RfcType', {
			extend : 'Ext.data.Model',
			idgen : 'uuid',// EXT在前台为每个模型额外以UUID方式生成主键
			idProperty : 'extid',// 以上生成的主键用在名叫“extid”的列
			fields : [{
						name : ' extid ',
						type : 'string'
					},// 额外的用于生成的EXT使用的列
					{
						name : 'code',
						type : 'string'
					}, {
						name : 'name',
						type : 'string'
					}]
		});
// 定义更改类型store
Ext.define('Foss.sign.viewSignRfc.RfcTypeStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.sign.viewSignRfc.RfcType',
	data : {
		'rfcTypeItems' : [{
					'code' : '',
					'name' : '请选择'
				}, {
					'code' : '0',
					'name' : '空运偏线'
				}, {
					'code' : '1',
					'name' : '付款'
				}, {
					'code' : '2',
					'name' : '到达联'
				}, {
					'code' : '3',
					'name' : '反签收'
				}]
	},
	proxy : {
		type : 'memory',
		// url: '/users.json',
		reader : {
			type : 'json',
			root : 'statusItems'
		}
	}
		// autoLoad: true
	});
// 定义运单model
Ext.define('Foss.sign.viewSignRfc.WaybillEntityModel', {
			extend : 'Ext.data.Model',
			idgen : 'uuid',// EXT在前台为每个模型额外以UUID方式生成主键
			idProperty : 'extid',// 以上生成的主键用在名叫“extid”的列
			fields : [{
						name : ' extid ',
						type : 'string'
					},// 额外的用于生成的EXT使用的列
					{
						name : 'id',
						type : 'string'
					}, {
						name : 'waybillNo',
						type : 'string'
					}, {
						name : 'orderNo',
						type : 'string'
					}, {
						name : 'deliveryCustomerId',
						type : 'string'
					}, {
						name : 'deliveryCustomerCode',
						type : 'string'
					}, {
						name : 'deliveryCustomerName',
						type : 'string'
					}, {
						name : 'deliveryCustomerMobilephone',
						type : 'string'
					}, {
						name : 'deliveryCustomerPhone',
						type : 'string'
					}, {
						name : 'deliveryCustomerContact',
						type : 'string'
					}, {
						name : 'deliveryCustomerNationCode',
						type : 'string'
					}, {
						name : 'deliveryCustomerProvCode',
						type : 'string'
					}, {
						name : 'deliveryCustomerCityCode',
						type : 'string'
					}, {
						name : 'deliveryCustomerDistCode',
						type : 'string'
					}, {
						name : 'deliveryCustomerAddress',
						type : 'string'
					}, {
						name : 'receiveCustomerId',
						type : 'string'
					}, {
						name : 'receiveCustomerCode',
						type : 'string'
					}, {
						name : 'receiveCustomerName',
						type : 'string'
					}, {
						name : 'receiveCustomerMobilephone',
						type : 'string'
					}, {
						name : 'receiveCustomerPhone',
						type : 'string'
					}, {
						name : 'receiveCustomerContact',
						type : 'string'
					}, {
						name : 'receiveCustomerNationCode',
						type : 'string'
					}, {
						name : 'receiveCustomerProvCode',
						type : 'string'
					}, {
						name : 'receiveCustomerCityCode',
						type : 'string'
					}, {
						name : 'receiveCustomerDistCode',
						type : 'string'
					}, {
						name : 'receiveCustomerAddress',
						type : 'string'
					}, {
						name : 'receiveOrgCode',
						type : 'string'
					}, {
						name : 'productId',
						type : 'string'
					}, {
						name : 'productCode',
						convert : function(value) {// 运输性质,
							return Foss.pkp.ProductData
									.rendererSubmitToDisplay(value);
						}
					}, {
						name : 'receiveMethod',
						convert : function(value) {// 派送方式,
							var v = FossDataDictionary.rendererSubmitToDisplay(value, 'PICKUPGOODSHIGHWAYS');
							if(Ext.isEmpty(v) || value == v){
								v = FossDataDictionary.rendererSubmitToDisplay(value, 'PICKUPGOODSSPECIALDELIVERYTYPE');
							}
							return v;
						}
					}, {
						name : 'customerPickupOrgCode',
						type : 'string'
					}, {
						name : 'loadMethod',
						type : 'string'
					}, {
						name : 'targetOrgCode',
						type : 'string'
					}, {
						name : 'pickupToDoor',
						type : 'string'
					}, {
						name : 'driverCode',
						type : 'string'
					}, {
						name : 'pickupCentralized',
						type : 'string'
					}, {
						name : 'loadLineCode',
						type : 'string'
					}, {
						name : 'loadOrgCode',
						type : 'string'
					}, {
						name : 'lastLoadOrgCode',
						type : 'string'
					}, {
						name : 'preDepartureTime',
						type : 'date',
						convert : dateConvert
					}, {
						name : 'preCustomerPickupTime',
						type : 'date',
						convert : dateConvert
					}, {
						name : 'carDirectDelivery',
						type : 'string'
					}, {
						name : 'goodsName',
						type : 'string'
					}, {
						name : 'goodsQtyTotal',
						type : 'int'
					}, {
						name : 'goodsWeightTotal',
						type : 'Number'
					}, {
						name : 'goodsVolumeTotal',
						type : 'Number'
					}, {
						name : 'goodsSize',
						type : 'string'
					}, {
						name : 'goodsTypeCode',
						type : 'string'
					}, {
						name : 'preciousGoods',
						type : 'string'
					}, {
						name : 'specialShapedGoods',
						type : 'string'
					}, {
						name : 'outerNotes',
						type : 'string'
					}, {
						name : 'innerNotes',
						type : 'string'
					}, {
						name : 'goodsPackage',
						type : 'string'
					}, {
						name : 'insuranceAmount',
						type : 'Number'
					}, {
						name : 'insuranceRate',
						type : 'Number'
					}, {
						name : 'insuranceFee',
						type : 'Number'
					}, {
						name : 'codAmount',
						type : 'Number'
					}, {
						name : 'codRate',
						type : 'Number'
					}, {
						name : 'codFee',
						type : 'Number'
					}, {
						name : 'refundType',
						type : 'string'
					}, {
						name : 'returnBillType',
						type : 'string'
					}, {
						name : 'secretPrepaid',
						type : 'string'
					}, {
						name : 'toPayAmount',
						type : 'Number'
					}, {
						name : 'prePayAmount',
						type : 'Number'
					}, {
						name : 'deliveryGoodsFee',
						type : 'Number'
					}, {
						name : 'otherFee',
						type : 'Number'
					}, {
						name : 'packageFee',
						type : 'Number'
					}, {
						name : 'promotionsFee',
						type : 'Number'
					}, {
						name : 'billingType',
						type : 'string'
					}, {
						name : 'unitPrice',
						type : 'Number'
					}, {
						name : 'transportFee',
						type : 'Number'
					}, {
						name : 'valueAddFee',
						type : 'Number'
					}, {
						name : 'paidMethod',
						convert : function(value) {// 付款方式
							return FossDataDictionary.rendererSubmitToDisplay(
									value, 'SETTLEMENT__PAYMENT_TYPE');
						}
					}, {
						name : 'arriveType',
						type : 'string'
					}, {
						name : 'active',
						type : 'string'
					}, {
						name : 'forbiddenLine',
						type : 'string'
					}, {
						name : 'freightMethod',
						type : 'string'
					}, {
						name : 'flightShift',
						type : 'string'
					}, {
						name : 'totalFee',
						type : 'Number'
					}, {
						name : 'promotionsCode',
						type : 'string'
					}, {
						name : 'createTime',
						type : 'date',
						convert : dateConvert
					}, {
						name : 'modifyTime',
						type : 'date',
						convert : dateConvert
					}, {
						name : 'billTime',
						type : 'date',
						convert : dateConvert
					}, {
						name : 'createUserCode',
						type : 'string'
					}, {
						name : 'modifyUserCode',
						type : 'string'
					}, {
						name : 'createOrgCode',
						type : 'string'
					}, {
						name : 'modifyOrgCode',
						type : 'string'
					}, {
						name : 'refId',
						type : 'string'
					}, {
						name : 'refCode',
						type : 'string'
					}, {
						name : 'currencyCode',
						type : 'string'
					}, {
						name : 'isWholeVehicle',
						type : 'string'
					}, {
						name : 'wholeVehicleAppfee',
						type : 'Number'
					}, {
						name : 'wholeVehicleActualfee',
						type : 'Number'
					}, {
						name : 'accountName',
						type : 'string'
					}, {
						name : 'accountCode',
						type : 'string'
					}, {
						name : 'accountBank',
						type : 'string'
					}, {
						name : 'billWeight',
						type : 'Number'
					}, {
						name : 'pickupFee',
						type : 'Number'
					}, {
						name : 'serviceFee',
						type : 'Number'
					}, {
						name : 'preArriveTime',
						type : 'date',
						convert : dateConvert
					}, {
						name : 'transportType',
						convert : function(value) {// 运输方式
							return FossDataDictionary.rendererSubmitToDisplay(
									value, 'TRANS_TYPE');
						}
					}, {
						name : 'printTimes',
						type : 'int'
					}, {
						name : 'addTime',
						type : 'date',
						convert : dateConvert
					}, {
						name : 'isPdaBill',
						type : 'string'
					}, {
						name : 'contactAddressId',
						type : 'string'
					}]

		});
Ext.define('Foss.sign.viewSignRfc.WaybillSignResultDetailForm', {
	extend : 'Ext.form.Panel',
	frame : false,
	hidden : true,
	defaults : {
		columnWidth : .5,
		margin : '5 5 5 5',
		anchor : '100%'
	},
	layout : 'column',
	fileUploadWaybillSignResultGrid : null,
	getFileUploadWaybillSignResultGrid : function() {
		if (this.fileUploadWaybillSignResultGrid == null) {
			this.fileUploadWaybillSignResultGrid = Ext.create(
					'Foss.viewSignRfc.Grid.FileUploadWaybillSignResultGrid', {
						columnWidth : 0.4,
						autoScroll : true
					});
		}
		return this.fileUploadWaybillSignResultGrid;
	},
	// 变更明细项数据
	changeDetailGrid : null,
	getChangeDetailGrid : function() {
		if (this.changeDetailGrid == null) {
			this.changeDetailGrid = Ext.create('Ext.grid.Panel', {
				columnWidth : .50,
				autoScroll : true,
				height : 120,
				store : Ext.create('Ext.data.Store', {
							model : 'Foss.sign.viewSignRfc.ChangeSignDetailModel',
							proxy : {
								type : 'memory',
								reader : {
									type : 'json'
								}
							}
						}),
				columns : [{
					xtype : 'gridcolumn',
					dataIndex : 'rfcType',
					text : sign.viewSignRfc
							.i18n('pkp.sign.viewSignRfc.rfcType'), // 变更类型,
					renderer : function(value) {
						return FossDataDictionary.rendererSubmitToDisplay(
								value, 'PKP_SIGN_RFC_DETAIL_TYPE');
					}
				}, {
					xtype : 'gridcolumn',
					dataIndex : 'rfcItems',
					text : sign.viewSignRfc
							.i18n('pkp.sign.viewSignRfc.rfcItems')
						// 变更项
				}, {
					xtype : 'gridcolumn',
					hidden : true,
					dataIndex : 'rfcItemsCode',
					text : sign.viewSignRfc
							.i18n('pkp.sign.viewSignRfc.rfcItemsCode')
						// 变更项CODE
				}, {
					xtype : 'gridcolumn',
					flex : 1,
					dataIndex : 'beforeRfcinfo',
					text : sign.viewSignRfc
							.i18n('pkp.sign.viewSignRfc.beforeRfcinfo'), // 变更前信息,
					renderer : function(value, metadata, record, rowIndex,
							colIndex, store) {
						if (record.get("rfcItemsCode") == 'paymentType') {
							return FossDataDictionary.rendererSubmitToDisplay(
									value, 'SETTLEMENT__PAYMENT_TYPE');
						} else if (record.get("rfcItemsCode") == 'identifyType') {
							return FossDataDictionary.rendererSubmitToDisplay(
									value, 'PKP_CREDENTIAL_TYPE');
						} else if (record.get("rfcItemsCode") == 'signSituation') {
							return FossDataDictionary.rendererSubmitToDisplay(
									value, 'PKP_SIGN_SITUATION');
						} else {
							return value;
						}
					}
				}, {
					xtype : 'gridcolumn',
					dataIndex : 'afterRfcinfo',
					flex : 1,
					text : sign.viewSignRfc
							.i18n('pkp.sign.viewSignRfc.afterRfcinfo'), // 变更后信息,
					renderer : function(value, metadata, record, rowIndex,
							colIndex, store) {
						if (record.get("rfcItemsCode") == 'paymentType') {
							return FossDataDictionary.rendererSubmitToDisplay(
									value, 'SETTLEMENT__PAYMENT_TYPE');
						} else if (record.get("rfcItemsCode") == 'identifyType') {
							return FossDataDictionary.rendererSubmitToDisplay(
									value, 'PKP_CREDENTIAL_TYPE');
						} else if (record.get("rfcItemsCode") == 'signSituation') {
							return FossDataDictionary.rendererSubmitToDisplay(
									value, 'PKP_SIGN_SITUATION');
						} else {
							return value;
						}
					}
				}]
			});
		}
		return this.changeDetailGrid;
	},
	initComponent : function() {
		var me = this;
		Ext.applyIf(me, {
			items : [{
						xtype : 'hiddenfield',
						name : 'id' // 变更签收结果ID
					}, {
						xtype : 'textfield',
						readOnly : true,
						fieldLabel : sign.viewSignRfc
								.i18n('pkp.sign.viewSignRfc.waybillNo'), // 运单号,
						columnWidth : 0.25,
						name : 'waybillNo'
					}, {
						xtype : 'textfield',
						readOnly : true,
						fieldLabel : sign.viewSignRfc
								.i18n('pkp.sign.viewSignRfc.situation'), // 签收情况,
						columnWidth : 0.25,
						name : 'signSituation'
					}, {
						xtype : 'textfield',
						readOnly : true,
						fieldLabel : sign.viewSignRfc
								.i18n('pkp.sign.viewSignRfc.deliverymanName2'), // 提货人名称,
						columnWidth : 0.25,
						name : 'deliverymanName'
					}, {
						xtype : 'textfield',
						readOnly : true,
						fieldLabel : sign.viewSignRfc
								.i18n('pkp.sign.viewSignRfc.identifyType'), // 证件类型,
						columnWidth : 0.25,
						name : 'identifyType'
					}, {
						xtype : 'textfield',
						readOnly : true,
						fieldLabel : sign.viewSignRfc
								.i18n('pkp.sign.viewSignRfc.identifyCode'), // 证件号码,
						columnWidth : 0.25,
						name : 'identifyCode'
					}, {
						xtype : 'textfield',
						readOnly : true,
						fieldLabel : sign.viewSignRfc
								.i18n('pkp.sign.viewSignRfc.settleStatus'), // 结清状态,
						columnWidth : 0.25,
						name : 'settleStatus'
					}, {
						xtype : 'textfield',
						readOnly : true,
						fieldLabel : sign.viewSignRfc
								.i18n('pkp.sign.viewSignRfc.signGoodsQty'), // 签收件数,
						columnWidth : 0.25,
						name : 'signGoodsQty'
					}, {
						xtype : 'textfield',
						readOnly : true,
						fieldLabel : sign.viewSignRfc
								.i18n('pkp.sign.viewSignRfc.signNote'), // 签收备注,
						columnWidth : 0.25,
						name : 'signNote'
					}, {
						xtype : 'datefield',
						format: 'Y-m-d H:i:s',
						readOnly : true,
						fieldLabel : sign.viewSignRfc
								.i18n('pkp.sign.viewSignRfc.signTime'), // 签收时间,
						columnWidth : 0.25,
						name : 'signTime'
					}, {
						xtype : 'datefield',
						format: 'Y-m-d H:i:s',
						readOnly : true,
						fieldLabel : sign.viewSignRfc
								.i18n('pkp.sign.viewSignRfc.createTime'), // 生效时间,
						columnWidth : 0.25,
						name : 'createTime'
					}, {
						xtype : 'datefield',
						format: 'Y-m-d H:i:s',
						readOnly : true,
						fieldLabel : sign.viewSignRfc
								.i18n('pkp.sign.viewSignRfc.modifyTime'), // 时效时间,
						columnWidth : 0.25,
						name : 'modifyTime'
					}, {
						xtype : 'textfield',
						fieldLabel : '申请原因',
						readOnly : true,
						columnWidth : 1,
						name : 'reason'
					}, {
						xtype : 'textfield',
						fieldLabel : '审核批注',
						readOnly : true,
						columnWidth : 1,
						name : 'notes'
					}, {
						border : 1,
						xtype : 'container',
						columnWidth : 1,
						defaultType : 'button',
						layout : 'column',
						items : [
								{
									xtype : 'label',
									columnWidth : 0.1,
									text : sign.viewSignRfc
											.i18n('pkp.sign.viewSignRfc.changeDetail')
									// 变更明细
								}, me.getChangeDetailGrid(),
								me.getFileUploadWaybillSignResultGrid()]
					}]
		});
		me.callParent(arguments);
	}
});
// 付款FORM
Ext.define('Foss.sign.viewSignRfc.RepaymentDetailForm', {
	extend : 'Ext.form.Panel',
	frame : false,
	hidden : true,
	defaults : {
		columnWidth : .5,
		margin : '5 5 5 5',
		anchor : '100%'
	},
	layout : 'column',
	fileUploadRepaymentGrid : null,
	getFileUploadRepaymentGrid : function() {
		if (this.fileUploadRepaymentGrid == null) {
			this.fileUploadRepaymentGrid = Ext.create(
					'Foss.viewSignRfc.Grid.FileUploadRepaymentGrid', {
						columnWidth : 0.4,
						autoScroll : true
					});
		}
		return this.fileUploadRepaymentGrid;
	},
	// 变更明细项数据
	changeDetailGrid : null,
	getChangeDetailGrid : function() {
		if (this.changeDetailGrid == null) {
			this.changeDetailGrid = Ext.create('Ext.grid.Panel', {
				columnWidth : 0.5,
				autoScroll : true,
				height : 120,
				store : Ext.create('Ext.data.Store', {
							model : 'Foss.sign.viewSignRfc.ChangeSignDetailModel',
							proxy : {
								type : 'memory',
								reader : {
									type : 'json'
								}
							}
						}),
				columns : [{
					xtype : 'gridcolumn',
					dataIndex : 'rfcType',
					text : sign.viewSignRfc
							.i18n('pkp.sign.viewSignRfc.rfcType'), // 变更类型,
					renderer : function(value) {
						return FossDataDictionary.rendererSubmitToDisplay(
								value, 'PKP_SIGN_RFC_DETAIL_TYPE');
					}
				}, {
					xtype : 'gridcolumn',
					dataIndex : 'rfcItems',
					text : sign.viewSignRfc
							.i18n('pkp.sign.viewSignRfc.rfcItems')
						// 变更项
				}, {
					xtype : 'gridcolumn',
					hidden : true,
					dataIndex : 'rfcItemsCode',
					text : sign.viewSignRfc
							.i18n('pkp.sign.viewSignRfc.rfcItemsCode')
						// 变更项CODE
				}, {
					xtype : 'gridcolumn',
					dataIndex : 'beforeRfcinfo',
					text : sign.viewSignRfc
							.i18n('pkp.sign.viewSignRfc.beforeRfcinfo'), // 变更前信息,
					renderer : function(value, metadata, record, rowIndex,
							colIndex, store) {
						if (record.get("rfcItemsCode") == 'paymentType') {
							return FossDataDictionary.rendererSubmitToDisplay(
									value, 'SETTLEMENT__PAYMENT_TYPE');
						} else if (record.get("rfcItemsCode") == 'identifyType') {
							return FossDataDictionary.rendererSubmitToDisplay(
									value, 'PKP_CREDENTIAL_TYPE');
						} else if (record.get("rfcItemsCode") == 'situation') {
							if(value.indexOf('.')>=0){
								var tmpValueArr=value.split('.');
								return tmpValueArr[0]+"  "+FossDataDictionary.rendererSubmitToDisplay(
									tmpValueArr[1], 'PKP_SIGN_SITUATION');
							}else{
								return FossDataDictionary.rendererSubmitToDisplay(
									value, 'PKP_SIGN_SITUATION');
							}
						} else {
							return value;
						}
					}
				}, {
					xtype : 'gridcolumn',
					dataIndex : 'afterRfcinfo',
					text : sign.viewSignRfc
							.i18n('pkp.sign.viewSignRfc.afterRfcinfo'), // 变更后信息,
					renderer : function(value, metadata, record, rowIndex,
							colIndex, store) {
						if (record.get("rfcItemsCode") == 'paymentType') {
							return FossDataDictionary.rendererSubmitToDisplay(
									value, 'SETTLEMENT__PAYMENT_TYPE');
						} else if (record.get("rfcItemsCode") == 'identifyType') {
							return FossDataDictionary.rendererSubmitToDisplay(
									value, 'PKP_CREDENTIAL_TYPE');
						} else if (record.get("rfcItemsCode") == 'situation') {
							if(value.indexOf('.')>=0){
								var tmpValueArr=value.split('.');
								return tmpValueArr[0]+"  "+FossDataDictionary.rendererSubmitToDisplay(
									tmpValueArr[1], 'PKP_SIGN_SITUATION');
							}else{
								return FossDataDictionary.rendererSubmitToDisplay(
									value, 'PKP_SIGN_SITUATION');
							}
						} else {
							return value;
						}
					}
				}]
			});
		}
		return this.changeDetailGrid;
	},
	initComponent : function() {
		var me = this;
		Ext.applyIf(me, {
			items : [{
						xtype : 'hiddenfield',
						name : 'id' // 变更签收结果ID
					}, {
						xtype : 'textfield',
						readOnly : true,
						fieldLabel : sign.viewSignRfc
								.i18n('pkp.sign.viewSignRfc.waybillNo'), // 运单号,
						columnWidth : 0.25,
						name : 'waybillNo'
					}, {
						xtype : 'textfield',
						readOnly : true,
						fieldLabel : sign.viewSignRfc
								.i18n('pkp.sign.viewSignRfc.repaymentNo'), // 付款编号,
						columnWidth : 0.25,
						name : 'repaymentNo'
					}, {
						xtype : 'textfield',
						readOnly : true,
						fieldLabel : sign.viewSignRfc
								.i18n('pkp.sign.viewSignRfc.consigneeName'), // 客户名称,
						columnWidth : 0.25,
						name : 'consigneeName'
					}, {
						xtype : 'textfield',
						readOnly : true,
						fieldLabel : sign.viewSignRfc
								.i18n('pkp.sign.viewSignRfc.paymentType'), // 付款方式,
						columnWidth : 0.25,
						name : 'paymentType'
					}, {
						xtype : 'textfield',
						readOnly : true,
						fieldLabel : sign.viewSignRfc
								.i18n('pkp.sign.viewSignRfc.claimNo'), // 款项认领编号,
						columnWidth : 0.25,
						name : 'claimNo'
					}, {
						xtype : 'textfield',
						readOnly : true,
						fieldLabel : sign.viewSignRfc
								.i18n('pkp.sign.viewSignRfc.actualFreight1'), // 实付运费,
						columnWidth : 0.25,
						name : 'actualFreight'
					}, {
						xtype : 'textfield',
						readOnly : true,
						fieldLabel : sign.viewSignRfc
								.i18n('pkp.sign.viewSignRfc.codAmount'), // 代收货款,
						columnWidth : 0.25,
						name : 'codAmount'
					}, {
						xtype : 'datefield',
						format: 'Y-m-d H:i:s',
						readOnly : true,
						fieldLabel : sign.viewSignRfc
								.i18n('pkp.sign.viewSignRfc.paymentTime'), // 付款时间,
						columnWidth : 0.25,
						name : 'paymentTime'
					}, {
						xtype : 'textfield',
						readOnly : true,
						fieldLabel : sign.viewSignRfc
								.i18n('pkp.sign.viewSignRfc.storageFee'), // 仓储费,
						columnWidth : 0.25,
						name : 'storageFee'
					}, {
						xtype : 'textfield',
						readOnly : true,
						fieldLabel : sign.viewSignRfc
								.i18n('pkp.sign.viewSignRfc.deliverymanName1'), // 提货人姓名,
						columnWidth : 0.25,
						name : 'deliverymanName'
					}, {
						xtype : 'textfield',
						readOnly : true,
						fieldLabel : sign.viewSignRfc
								.i18n('pkp.sign.viewSignRfc.identifyType'), // 证件类型,
						columnWidth : 0.25,
						name : 'identifyType',
						renderer : function(value) {
							return FossDataDictionary.rendererSubmitToDisplay(
									value, 'PKP_CREDENTIAL_TYPE');
						}
					}, {
						xtype : 'textfield',
						readOnly : true,
						fieldLabel : sign.viewSignRfc
								.i18n('pkp.sign.viewSignRfc.identifyCode'), // 证件号码,
						columnWidth : 0.25,
						name : 'identifyCode'
					}, {
						xtype : 'textfield',
						fieldLabel : '申请原因',
						readOnly : true,
						columnWidth : 1,
						name : 'reason'
					}, {
						xtype : 'textfield',
						fieldLabel : '审核批注',
						readOnly : true,
						columnWidth : 1,
						name : 'notes'
					}, {
						border : 1,
						xtype : 'container',
						columnWidth : 1,
						defaultType : 'button',
						layout : 'column',
						items : [
								{
									xtype : 'label',
									columnWidth : 0.1,
									text : sign.viewSignRfc
											.i18n('pkp.sign.viewSignRfc.changeDetail')
									// 变更明细
								}, me.getChangeDetailGrid(),
								me.getFileUploadRepaymentGrid()]
					}]
		});

		me.callParent(arguments);
	}

});
// 到达联FORM
Ext.define('Foss.sign.viewSignRfc.ArriveSheetDetailForm', {
	extend : 'Ext.form.Panel',
	frame : false,
	hidden : true,
	defaults : {
		columnWidth : .5,
		margin : '5 5 5 5',
		anchor : '100%'
	},
	layout : 'column',
	fileUploadArriveSheetGrid : null,
	getFileUploadArriveSheetGrid : function() {
		if (this.fileUploadArriveSheetGrid == null) {
			this.fileUploadArriveSheetGrid = Ext.create(
					'Foss.viewSignRfc.Grid.FileUploadArriveSheetGrid', {
						columnWidth : 0.4,
						autoScroll : true
					});
		}
		return this.fileUploadArriveSheetGrid;
	},
	// 变更明细项数据
	changeDetailGrid : null,
	getChangeDetailGrid : function() {
		if (this.changeDetailGrid == null) {
			this.changeDetailGrid = Ext.create('Ext.grid.Panel', {
				columnWidth : 0.4,
				autoScroll : true,
				height : 120,
				store : Ext.create('Ext.data.Store', {
							model : 'Foss.sign.viewSignRfc.ChangeSignDetailModel',
							proxy : {
								type : 'memory',
								reader : {
									type : 'json'
								}
							}
						}),
				columns : [{
					xtype : 'gridcolumn',
					dataIndex : 'rfcType',
					text : sign.viewSignRfc
							.i18n('pkp.sign.viewSignRfc.rfcType'), // 变更类型,
					renderer : function(value) {
						return FossDataDictionary.rendererSubmitToDisplay(
								value, 'PKP_SIGN_RFC_DETAIL_TYPE');
					}
				}, {
					xtype : 'gridcolumn',
					dataIndex : 'rfcItems',
					text : sign.viewSignRfc
							.i18n('pkp.sign.viewSignRfc.rfcItems')
						// 变更项
				}, {
					xtype : 'gridcolumn',
					hidden : true,
					dataIndex : 'rfcItemsCode',
					text : sign.viewSignRfc
							.i18n('pkp.sign.viewSignRfc.rfcItemsCode')
						// 变更项CODE
				}, {
					xtype : 'gridcolumn',
					dataIndex : 'beforeRfcinfo',
					text : sign.viewSignRfc
							.i18n('pkp.sign.viewSignRfc.beforeRfcinfo'), // 变更前信息,
					renderer : function(value, metadata, record, rowIndex,
							colIndex, store) {
						if (record.get("rfcItemsCode") == 'paymentType') {
							return FossDataDictionary.rendererSubmitToDisplay(
									value, 'SETTLEMENT__PAYMENT_TYPE');
						} else if (record.get("rfcItemsCode") == 'identifyType') {
							return FossDataDictionary.rendererSubmitToDisplay(
									value, 'PKP_CREDENTIAL_TYPE');
						} else if (record.get("rfcItemsCode") == 'situation') {
							if(value.indexOf('.')>=0){
								var tmpValueArr=value.split('.');
								return tmpValueArr[0]+"  "+FossDataDictionary.rendererSubmitToDisplay(
									tmpValueArr[1], 'PKP_SIGN_SITUATION');
							}else{
								return FossDataDictionary.rendererSubmitToDisplay(
									value, 'PKP_SIGN_SITUATION');
							}
						} else {
							return value;
						}
					}
				}, {
					xtype : 'gridcolumn',
					dataIndex : 'afterRfcinfo',
					text : sign.viewSignRfc
							.i18n('pkp.sign.viewSignRfc.afterRfcinfo'), // 变更后信息,
					renderer : function(value, metadata, record, rowIndex,
							colIndex, store) {
						if (record.get("rfcItemsCode") == 'paymentType') {
							return FossDataDictionary.rendererSubmitToDisplay(
									value, 'SETTLEMENT__PAYMENT_TYPE');
						} else if (record.get("rfcItemsCode") == 'identifyType') {
							return FossDataDictionary.rendererSubmitToDisplay(
									value, 'PKP_CREDENTIAL_TYPE');
						} else if (record.get("rfcItemsCode") == 'situation') {
							if(value.indexOf('.')>=0){
								var tmpValueArr=value.split('.');
								return tmpValueArr[0]+"  "+FossDataDictionary.rendererSubmitToDisplay(
									tmpValueArr[1], 'PKP_SIGN_SITUATION');
							}else{
								return FossDataDictionary.rendererSubmitToDisplay(
									value, 'PKP_SIGN_SITUATION');
							}
						} else {
							return value;
						}
					}
				}]
			});
		}
		return this.changeDetailGrid;
	},
	initComponent : function() {
		var me = this;
		me.items = [{
					xtype : 'hiddenfield',
					name : 'id' // 变更签收结果ID
				}, {
					xtype : 'textfield',
					readOnly : true,
					fieldLabel : sign.viewSignRfc
							.i18n('pkp.sign.viewSignRfc.waybillNo'), // 运单号,
					columnWidth : 0.25,
					name : 'waybillNo'
				}, {
					xtype : 'textfield',
					readOnly : true,
					fieldLabel : sign.viewSignRfc
							.i18n('pkp.sign.viewSignRfc.arrivesheetNo'), // 到达联编号,
					columnWidth : 0.25,
					name : 'arrivesheetNo'
				}, {
					xtype : 'textfield',
					readOnly : true,
					fieldLabel : sign.viewSignRfc
							.i18n('pkp.sign.viewSignRfc.deliverymanName2'), // 提货人名称,
					columnWidth : 0.25,
					name : 'deliverymanName'
				}, {
					xtype : 'textfield',
					readOnly : true,
					fieldLabel : sign.viewSignRfc
							.i18n('pkp.sign.viewSignRfc.identifyType'), // 证件类型,
					columnWidth : 0.25,
					name : 'identifyType',
					renderer : function(value) {
						return FossDataDictionary.rendererSubmitToDisplay(
								value, 'PKP_CREDENTIAL_TYPE');
					}
				}, {
					xtype : 'textfield',
					readOnly : true,
					fieldLabel : sign.viewSignRfc
							.i18n('pkp.sign.viewSignRfc.identifyCode'), // 证件号码,
					columnWidth : 0.25,
					name : 'identifyCode'
				}, {
					xtype : 'textfield',
					readOnly : true,
					fieldLabel : sign.viewSignRfc
							.i18n('pkp.sign.viewSignRfc.situation'), // 签收情况,
					columnWidth : 0.25,
					name : 'situation'
				}, {
					xtype : 'textfield',
					readOnly : true,
					fieldLabel : sign.viewSignRfc
							.i18n('pkp.sign.viewSignRfc.arriveSheetGoodsQty'), // 到达联件数,
					columnWidth : 0.25,
					name : 'arriveSheetGoodsQty'
				}, {
					xtype : 'textfield',
					readOnly : true,
					fieldLabel : sign.viewSignRfc
							.i18n('pkp.sign.viewSignRfc.signGoodsQty'), // 签收件数,
					columnWidth : 0.25,
					name : 'signGoodsQty'
				}, {
					xtype : 'textfield',
					readOnly : true,
					fieldLabel : sign.viewSignRfc
							.i18n('pkp.sign.viewSignRfc.signNote'), // 签收备注,
					columnWidth : 0.25,
					name : 'signNote'
				}, {
					xtype : 'datefield',
					format: 'Y-m-d H:i:s',
					readOnly : true,
					fieldLabel : sign.viewSignRfc
							.i18n('pkp.sign.viewSignRfc.signTime'), // 签收时间,
					columnWidth : 0.25,
					name : 'signTime'
				}, {
					xtype : 'textfield',
					readOnly : true,
					fieldLabel : sign.viewSignRfc
							.i18n('pkp.sign.viewSignRfc.deliverRequire'), // 送货要求,
					columnWidth : 0.25,
					name : 'deliverRequire'
				}, {
					xtype : 'textfield',
					readOnly : true,
					fieldLabel : sign.viewSignRfc
							.i18n('pkp.sign.viewSignRfc.printtimes'), // 打印次数,
					columnWidth : 0.25,
					name : 'printtimes'
				}, {
					xtype : 'textfield',
					fieldLabel : '申请原因',
					readOnly : true,
					columnWidth : 1,
					name : 'reason'
				}, {
					xtype : 'textfield',
					fieldLabel : '审核批注',
					readOnly : true,
					columnWidth : 1,
					name : 'notes'
				}, {
					border : 1,
					xtype : 'container',
					columnWidth : 1,
					defaultType : 'button',
					layout : 'column',
					items : [
							{
								xtype : 'label',
								columnWidth : 0.1,
								text : sign.viewSignRfc
										.i18n('pkp.sign.viewSignRfc.changeDetail')
								// 变更明细
							}, me.getChangeDetailGrid(),
							me.getFileUploadArriveSheetGrid()]

				}];
		me.callParent(arguments);
	}
});
// 反签收FORM
Ext.define('Foss.sign.viewSignRfc.ReverseSignRfcDetailForm', {
	extend : 'Ext.form.Panel',
	frame : false,
	hidden : true,
	cls : 'autoHeight',
	bodyCls : 'autoHeight',
	defaults : {
		columnWidth : .5,
		margin : '5 5 5 5',
		anchor : '100%'
	},
	fileUploadReverseSignRfcGrid : null,
	getFileUploadReverseSignRfcGrid : function() {
		if (this.fileUploadReverseSignRfcGrid == null) {
			this.fileUploadReverseSignRfcGrid = Ext.create(
					'Foss.viewSignRfc.Grid.FileUploadReverseSignRfcGrid', {
						columnWidth : 1,
						autoScroll : true
					});
		}
		return this.fileUploadReverseSignRfcGrid;
	},
	// 付款GRID
	repaymentGrid : null,
	getRepaymentGrid : function() {
		if (this.repaymentGrid == null) {
			this.repaymentGrid = Ext.create('Ext.grid.Panel', {
				emptyText : sign.viewSignRfc.i18n('pkp.sign.viewSignRfc.empty'), // 没有数据,
				autoScroll : true,
				columnWidth : 4 / 5,
				store : Ext.create('Foss.sign.viewSignRfc.RepaymentStore'),
				columns : [{
					xtype : 'gridcolumn',
					dataIndex : 'repaymentNo',
					text : sign.viewSignRfc
							.i18n('pkp.sign.viewSignRfc.repaymentNo')
						// 付款编号
				}, {
					xtype : 'gridcolumn',
					dataIndex : 'paymentType',
					text : sign.viewSignRfc
							.i18n('pkp.sign.viewSignRfc.paymentType'), // 付款方式,
					renderer : function(value) {
						return FossDataDictionary.rendererSubmitToDisplay(
								value, 'SETTLEMENT__PAYMENT_TYPE');
					}
				}, {
					xtype : 'gridcolumn',
					dataIndex : 'actualFreight',
					text : sign.viewSignRfc
							.i18n('pkp.sign.viewSignRfc.actualFreight')
						// 实收到付运费
				}, {
					xtype : 'gridcolumn',
					dataIndex : 'codAmount',
					text : sign.viewSignRfc
							.i18n('pkp.sign.viewSignRfc.codAmount1')
						// 实收代收货款
				}, {
					xtype : 'templatecolumn',
					dataIndex : 'totMoney',
					text : sign.viewSignRfc
							.i18n('pkp.sign.viewSignRfc.totMoney'), // 实收到付总额,
					tpl : '{[parseInt(values.actualFreight)+parseInt(values.codAmount)]}'
				}, {
					xtype : 'gridcolumn',
					dataIndex : 'consigneeName',
					text : sign.viewSignRfc
							.i18n('pkp.sign.viewSignRfc.consigneeCode')
						// 提货客户
				}, {
					xtype : 'datecolumn',
					format : 'Y-m-d H:i:s',
					dataIndex : 'paymentTime',
					text : sign.viewSignRfc
							.i18n('pkp.sign.viewSignRfc.paymentTime')
						// 付款时间
				}]
			});
		}
		return this.repaymentGrid;
	},
	// 到达联GRID
	arriveSheetGrid : null,
	getArriveSheetGrid : function() {
		if (this.arriveSheetGrid == null) {
			this.arriveSheetGrid = Ext.create('Ext.grid.Panel', {
				emptyText : sign.viewSignRfc.i18n('pkp.sign.viewSignRfc.empty'), // 没有数据,
				columnWidth : 4 / 5,
				autoScroll : true,
				store : Ext.create('Foss.sign.viewSignRfc.ArriveSheetStore'),
				columns : [{
					xtype : 'gridcolumn',
					dataIndex : 'arrivesheetNo',
					text : sign.viewSignRfc
							.i18n('pkp.sign.viewSignRfc.arrivesheetNo')
						// 到达联编号
				}, {
					xtype : 'gridcolumn',
					dataIndex : 'deliverymanName',
					text : sign.viewSignRfc
							.i18n('pkp.sign.viewSignRfc.deliverymanName')
						// 到达提货人
				}, {
					xtype : 'gridcolumn',
					dataIndex : 'identifyType',
					text : sign.viewSignRfc
							.i18n('pkp.sign.viewSignRfc.identifyType'), // 证件类型,
					renderer : function(value) {
						return FossDataDictionary.rendererSubmitToDisplay(
								value, 'PKP_CREDENTIAL_TYPE');
					}
				}, {
					xtype : 'gridcolumn',
					dataIndex : 'identifyCode',
					text : sign.viewSignRfc
							.i18n('pkp.sign.viewSignRfc.identifyCode')
						// 证件号码
				}, {
					xtype : 'gridcolumn',
					dataIndex : 'signGoodsQty',
					text : sign.viewSignRfc
							.i18n('pkp.sign.viewSignRfc.signGoodsQty')
						// 签收件数
				}, {
					xtype : 'gridcolumn',
					dataIndex : 'situation',
					text : sign.viewSignRfc
							.i18n('pkp.sign.viewSignRfc.situation'), // 签收情况,
					renderer : function(value) {
						return FossDataDictionary.rendererSubmitToDisplay(
								value, 'PKP_SIGN_SITUATION');
					}
				}, {
					xtype : 'gridcolumn',
					dataIndex : 'signNote',
					text : sign.viewSignRfc
							.i18n('pkp.sign.viewSignRfc.signNote')
						// 签收备注
				}, {
					xtype : 'datecolumn',
					format : 'Y-m-d H:i:s',
					dataIndex : 'signTime',
					text : sign.viewSignRfc
							.i18n('pkp.sign.viewSignRfc.signTime')
						// 签收时间
				}]
			});
		}
		return this.arriveSheetGrid;
	},
	layout : 'column',
	initComponent : function() {
		var me = this;
		Ext.applyIf(me, {
			items : [{
						xtype : 'hiddenfield',
						name : 'id' // 变更签收结果ID
					}, {
						xtype : 'textfield',
						fieldLabel : sign.viewSignRfc
								.i18n('pkp.sign.viewSignRfc.waybillNo'), // 运单号,
						readOnly : true,
						labelWidth : 60,
						columnWidth : 1 / 4,
						name : 'waybillNo'
					}, {
						xtype : 'textfield',
						fieldLabel : sign.viewSignRfc
								.i18n('pkp.sign.viewSignRfc.goodsName'), // 货物名称,
						readOnly : true,
						labelWidth : 90,
						columnWidth : 1 / 4,
						name : 'goodsName'
					}, {
						xtype : 'textfield',
						fieldLabel : sign.viewSignRfc
								.i18n('pkp.sign.viewSignRfc.goodsQtyTotal'), // 件数,
						readOnly : true,
						labelWidth : 90,
						columnWidth : 1 / 4,
						name : 'goodsQtyTotal'
					}, {
						xtype : 'textfield',
						fieldLabel : sign.viewSignRfc
								.i18n('pkp.sign.viewSignRfc.goodsWeightTotal'), // 总量,
						readOnly : true,
						labelWidth : 90,
						columnWidth : 1 / 4,
						name : 'goodsWeightTotal'
					}, {
						xtype : 'textfield',
						fieldLabel : sign.viewSignRfc
								.i18n('pkp.sign.viewSignRfc.goodsVolumeTotal'), // 体积,
						readOnly : true,
						labelWidth : 60,
						columnWidth : 1 / 4,
						name : 'goodsVolumeTotal'
					}, {
						xtype : 'textfield',
						fieldLabel : sign.viewSignRfc
								.i18n('pkp.sign.viewSignRfc.deliveryCustomerContact'), // 发货人,
						readOnly : true,
						labelWidth : 90,
						columnWidth : 1 / 4,
						name : 'deliveryCustomerContact'
					}, {
						xtype : 'textfield',
						fieldLabel : sign.viewSignRfc
								.i18n('pkp.sign.viewSignRfc.receiveOrgCode'), // 发货部门,
						readOnly : true,
						labelWidth : 90,
						columnWidth : 1 / 4,
						name : 'receiveOrgCode'
					}, {
						xtype : 'textfield',
						fieldLabel : sign.viewSignRfc
								.i18n('pkp.sign.viewSignRfc.receiveCustomerContact'), // 收货人,
						readOnly : true,
						labelWidth : 90,
						columnWidth : 1 / 4,
						name : 'receiveCustomerContact'
					}, {
						xtype : 'textfield',
						fieldLabel : sign.viewSignRfc
								.i18n('pkp.sign.viewSignRfc.lastLoadOrgCode'), // 到达部门,
						readOnly : true,
						labelWidth : 60,
						columnWidth : 1 / 4,
						name : 'lastLoadOrgCode'
					}, {
						xtype : 'textfield',
						fieldLabel : sign.viewSignRfc
								.i18n('pkp.sign.viewSignRfc.prePayAmount'), // 预付金额,
						readOnly : true,
						labelWidth : 90,
						columnWidth : 1 / 4,
						name : 'prePayAmount'
					}, {
						xtype : 'textfield',
						fieldLabel : sign.viewSignRfc
								.i18n('pkp.sign.viewSignRfc.toPayAmount'), // 到付金额,
						readOnly : true,
						labelWidth : 90,
						columnWidth : 1 / 4,
						name : 'toPayAmount'
					}, {
						xtype : 'textfield',
						fieldLabel : sign.viewSignRfc
								.i18n('pkp.sign.viewSignRfc.codAmount'), // 代收货款,
						readOnly : true,
						labelWidth : 90,
						columnWidth : 1 / 4,
						name : 'codAmount'
					}, {
						border : 1,
						xtype : 'container',
						columnWidth : 1,
						defaultType : 'button',
						layout : 'column',
						items : [{
							xtype : 'label',
							columnWidth : 1 / 5,
							text : sign.viewSignRfc
									.i18n('pkp.sign.viewSignRfc.repaymentInfo')
								// 货款结清信息
						}, me.getRepaymentGrid()]
					}, {
						border : 1,
						xtype : 'container',
						columnWidth : 1,
						defaultType : 'button',
						layout : 'column',
						items : [{
							xtype : 'label',
							columnWidth : 1 / 5,
							text : sign.viewSignRfc
									.i18n('pkp.sign.viewSignRfc.arriveSheetInfo')
								// 到达联信息
						}, me.getArriveSheetGrid(),{
						xtype : 'textfield',
						fieldLabel : '申请原因',
						readOnly : true,
						columnWidth : 1,
						name : 'reason'
					}, {
						xtype : 'textfield',
						fieldLabel : '审核批注',
						readOnly : true,
						columnWidth : 1,
						name : 'notes'
					}]
					}, {
						border : 1,
						xtype : 'container',
						columnWidth : 1,
						defaultType : 'button',
						layout : 'column',
						items : [{
							xtype : 'label',
							columnWidth : 1 / 5,
							text : sign.viewSignRfc
									.i18n('pkp.sign.viewSignRfc.changeFile')
								// 变更凭证
						}, me.getFileUploadReverseSignRfcGrid()]
					}]
		});
		me.callParent(arguments);
	}
});
// 反签收运单签收结果FORM
Ext.define('Foss.sign.viewSignRfc.ReverseWaybillSignResultDetailForm', {
	extend : 'Ext.form.Panel',
	frame : false,
	hidden : true,
	defaults : {
		columnWidth : .5,
		margin : '5 5 5 5',
		anchor : '100%'
	},
	layout : 'column',
	// 变更明细项数据
	fileUploadReverseWaybillSignResultGrid : null,
	getFileUploadReverseWaybillSignResultGrid : function() {
		if (this.fileUploadReverseWaybillSignResultGrid == null) {
			this.fileUploadReverseWaybillSignResultGrid = Ext
					.create(
							'Foss.viewSignRfc.Grid.FileUploadReverseAirliftPartialSignRfcGrid',
							{
								columnWidth : 0.4,
								autoScroll : true
							});
		}
		return this.fileUploadReverseWaybillSignResultGrid;
	},

	initComponent : function() {
		var me = this;
		Ext.applyIf(me, {
					items : [{
								xtype : 'hiddenfield',
								name : 'signRfcId' // 变更签收结果ID
							}, {
								xtype : 'hiddenfield',
								name : 'id' //
							}, {
								xtype : 'textfield',
								readOnly : true,
								fieldLabel : sign.viewSignRfc
										.i18n('pkp.sign.viewSignRfc.waybillNo'), // 运单号,
								columnWidth : 0.25,
								name : 'waybillNo'
							}, {
								xtype : 'textfield',
								readOnly : true,
								fieldLabel : sign.viewSignRfc
										.i18n('pkp.sign.viewSignRfc.situation'), // 签收情况,
								columnWidth : 0.25,
								name : 'signSituation',
								renderer : function(value) {
									return FossDataDictionary
											.rendererSubmitToDisplay(value,
													'PKP_SIGN_SITUATION');
								}
							}, {
								xtype : 'textfield',
								readOnly : true,
								fieldLabel : sign.viewSignRfc
										.i18n('pkp.sign.viewSignRfc.deliverymanName2'), // 提货人名称,
								columnWidth : 0.25,
								name : 'deliverymanName'
							}, {
								xtype : 'textfield',
								readOnly : true,
								fieldLabel : sign.viewSignRfc
										.i18n('pkp.sign.viewSignRfc.identifyType'), // 证件类型,
								columnWidth : 0.25,
								name : 'identifyType',
								renderer : function(value) {
									return FossDataDictionary
											.rendererSubmitToDisplay(value,
													'PKP_CREDENTIAL_TYPE');
								}
							}, {
								xtype : 'textfield',
								readOnly : true,
								fieldLabel : sign.viewSignRfc
										.i18n('pkp.sign.viewSignRfc.identifyCode'), // 证件号码,
								columnWidth : 0.25,
								name : 'identifyCode'
							}, {
								xtype : 'textfield',
								readOnly : true,
								fieldLabel : sign.viewSignRfc
										.i18n('pkp.sign.viewSignRfc.settleStatus'), // 结清状态,
								columnWidth : 0.25,
								name : 'settleStatus'
							}, {
								xtype : 'textfield',
								readOnly : true,
								fieldLabel : sign.viewSignRfc
										.i18n('pkp.sign.viewSignRfc.signGoodsQty'), // 签收件数,
								columnWidth : 0.25,
								name : 'signGoodsQty'
							}, {
								xtype : 'textfield',
								readOnly : true,
								fieldLabel : sign.viewSignRfc
										.i18n('pkp.sign.viewSignRfc.signNote'), // 签收备注,
								columnWidth : 0.25,
								name : 'signNote'
							}, {
								xtype : 'datefield',
						        format: 'Y-m-d H:i:s',
								readOnly : true,
								fieldLabel : sign.viewSignRfc
										.i18n('pkp.sign.viewSignRfc.signTime'), // 签收时间,
								columnWidth : 0.25,
								name : 'signTime'
							}, {
								xtype : 'datefield',
						        format: 'Y-m-d H:i:s',
								readOnly : true,
								fieldLabel : sign.viewSignRfc
										.i18n('pkp.sign.viewSignRfc.createTime'), // 生效时间,
								columnWidth : 0.25,
								name : 'createTime'
							}, {
								xtype : 'datefield',
						        format: 'Y-m-d H:i:s',
								readOnly : true,
								fieldLabel : sign.viewSignRfc
										.i18n('pkp.sign.viewSignRfc.modifyTime'), // 时效时间,
								columnWidth : 0.25,
								name : 'modifyTime'
							}, {
								xtype : 'textfield',
								fieldLabel : '申请原因',
								readOnly : true,
								columnWidth : 1,
								name : 'reason'
							}, {
								xtype : 'textfield',
								fieldLabel : '审核批注',
								readOnly : true,
								columnWidth : 1,
								name : 'notes'
							}, {
								border : 1,
								xtype : 'container',
								columnWidth : 1,
								defaultType : 'button',
								layout : 'column',
								items : [
										{
											xtype : 'label',
											columnWidth : 0.1,
											text : sign.viewSignRfc
													.i18n('pkp.sign.viewSignRfc.changeFile')
											// 变更凭证
										},
										me.getFileUploadReverseWaybillSignResultGrid()]
							}]
				});

		me.callParent(arguments);
	}
});
// 审核明细数据
Ext.define('Foss.sign.viewSignRfc.ViewSignRfcDetailForm', {
	extend : 'Ext.form.FormPanel',
	cls : 'autoHeight',
	bodyCls : 'autoHeight',
	defaults : {
		xtype : 'textfield',
		readOnly : true,
		columnWidth : .5,
		margin : '5 5 5 5',
		anchor : '100%'
	},
	waybillResultDetailForm : null,
	getWaybillResultDetailForm : function() {
		if (this.waybillResultDetailForm == null) {
			this.waybillResultDetailForm = Ext
					.create('Foss.sign.viewSignRfc.WaybillSignResultDetailForm');
		}
		return this.waybillResultDetailForm;
	},
	repaymentDetailForm : null,
	getRepaymentDetailForm : function() {
		if (this.repaymentDetailForm == null) {
			this.repaymentDetailForm = Ext
					.create('Foss.sign.viewSignRfc.RepaymentDetailForm');
		}
		return this.repaymentDetailForm;
	},
	arrivesheetDetailForm : null,
	getArrivesheetDetailForm : function() {
		if (this.arrivesheetDetailForm == null) {
			this.arrivesheetDetailForm = Ext
					.create('Foss.sign.viewSignRfc.ArriveSheetDetailForm');
		}
		return this.arrivesheetDetailForm;
	},
	reverseSignRfcDetailForm : null,
	getReverseSignRfcDetailForm : function() {
		if (this.reverseSignRfcDetailForm == null) {
			this.reverseSignRfcDetailForm = Ext
					.create('Foss.sign.viewSignRfc.ReverseSignRfcDetailForm');
		}
		return this.reverseSignRfcDetailForm;
	},
	reverseWaybillResultDetailForm : null,
	getReverseWaybillResultDetailForm : function() {
		if (this.reverseWaybillResultDetailForm == null) {
			this.reverseWaybillResultDetailForm = Ext
					.create('Foss.sign.viewSignRfc.ReverseWaybillSignResultDetailForm');
		}
		return this.reverseWaybillResultDetailForm;
	},
	bindData : function(record, grid, rowBodyElement) {
		// 绑定表格数据到表单上
		Ext.Ajax.request({
			url : sign.realPath('queryViewSignRfcDetail.action'),
			params : {
				'vo.signResultDto.signRfcEntity.id' : record.get('id')
			},
			success : function(response) {
				var result = Ext.decode(response.responseText);
				var reason = record.get('reason');
				var notes = record.get('notes');
				if (record.get('rfcType') == 'SIGN_RFC_TYPE_WAYBILL') {
					var model = Ext.ModelManager
							.create(
									result.vo.signResultDto.changeDto.waybillSignResultEntity,
									'Foss.sign.viewSignRfc.WaybillSignResultModel');
					model.set('reason', reason);
					model.set('notes', notes);
					rowBodyElement.getWaybillResultDetailForm().show();
					rowBodyElement.getWaybillResultDetailForm()
							.loadRecord(model);
					rowBodyElement
							.getWaybillResultDetailForm()
							.getChangeDetailGrid()
							.getStore()
							.loadData(result.vo.signResultDto.changeDetailentity);
					rowBodyElement
							.getWaybillResultDetailForm()
							.getFileUploadWaybillSignResultGrid()
							.getStore()
							.loadData(result.vo.signResultDto.waybillSignResultFiles);
				} else if (record.get('rfcType') == 'SIGN_RFC_TYPE_REPAYMENT') {
					var model = Ext.ModelManager.create(
							result.vo.signResultDto.changeDto.repaymentEntity,
							'Foss.sign.viewSignRfc.RepaymentModel');
					model.set('reason', reason);
					model.set('notes', notes);
					rowBodyElement.getRepaymentDetailForm().show();
					rowBodyElement.getRepaymentDetailForm().loadRecord(model);
					rowBodyElement
							.getRepaymentDetailForm()
							.getChangeDetailGrid()
							.getStore()
							.loadData(result.vo.signResultDto.changeDetailentity);
					rowBodyElement.getRepaymentDetailForm()
							.getFileUploadRepaymentGrid().getStore()
							.loadData(result.vo.signResultDto.repaymentFiles);
				} else if (record.get('rfcType') == 'SIGN_RFC_TYPE_ARRIVESHEET') {
					var model = Ext.ModelManager
							.create(
									result.vo.signResultDto.changeDto.arriveSheetEntity,
									'Foss.sign.viewSignRfc.ArrivesheetModel');
					model.set('reason', reason);
					model.set('notes', notes);
					rowBodyElement.getArrivesheetDetailForm().show();
					rowBodyElement.getArrivesheetDetailForm().loadRecord(model);
					rowBodyElement
							.getArrivesheetDetailForm()
							.getChangeDetailGrid()
							.getStore()
							.loadData(result.vo.signResultDto.changeDetailentity);
					rowBodyElement.getArrivesheetDetailForm()
							.getFileUploadArriveSheetGrid().getStore()
							.loadData(result.vo.signResultDto.arrivesheetFiles);
				} else if (record.get('rfcType') == 'SIGN_RFC_TYPE_REVERSESIGN_DEDICATED') {
					var model = Ext.ModelManager
							.create(
									result.vo.signResultDto.repaymentArrivesheetDto.waybillEntity,
									'Foss.sign.viewSignRfc.WaybillEntityModel');
					model.set('reason', reason);
					model.set('notes', notes);
					rowBodyElement.getReverseSignRfcDetailForm().show();
					rowBodyElement.getReverseSignRfcDetailForm()
							.loadRecord(model);
					rowBodyElement
							.getReverseSignRfcDetailForm()
							.getRepaymentGrid()
							.getStore()
							.loadData(result.vo.signResultDto.repaymentArrivesheetDto.repaymentEntityList);
					rowBodyElement
							.getReverseSignRfcDetailForm()
							.getArriveSheetGrid()
							.getStore()
							.loadData(result.vo.signResultDto.repaymentArrivesheetDto.arriveSheetEntityList);
					rowBodyElement
							.getReverseSignRfcDetailForm()
							.getFileUploadReverseSignRfcGrid()
							.getStore()
							.loadData(result.vo.signResultDto.reverseSignRfcFiles);
				} else {
					var model = Ext.ModelManager
							.create(
									result.vo.signResultDto.repaymentArrivesheetDto.waybillSignResultEntity,
									'Foss.sign.viewSignRfc.WaybillSignResultModel');
					model.set('reason', reason);
					model.set('notes', notes);
					rowBodyElement.getReverseWaybillResultDetailForm().show();
					rowBodyElement.getReverseWaybillResultDetailForm()
							.loadRecord(model);
					rowBodyElement
							.getReverseWaybillResultDetailForm()
							.getFileUploadReverseWaybillSignResultGrid()
							.getStore()
							.loadData(result.vo.signResultDto.reverseSignRfcFiles);
				}
			}
		});
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.items = [me.getWaybillResultDetailForm(),
				me.getRepaymentDetailForm(), me.getArrivesheetDetailForm(),
				me.getReverseSignRfcDetailForm(),
				me.getReverseWaybillResultDetailForm()];
		me.callParent([cfg]);
	}
});
// 查询条件
Ext.define('Foss.sign.viewSignRfc.queryForm', {
	extend : 'Ext.form.Panel',
	frame : true,
	collapsible : true,
	animCollapse : true,
	cls : 'autoHeight',
	bodyCls : 'autoHeight',
	layout : {
		type : 'column'
	},
	bodyPadding : 10,
	title : sign.viewSignRfc.i18n('pkp.sign.viewSignRfc.searchCondition'), // 查询条件,
	defaults : {
		margin : '5 10 5 10',
		labelWidth : 90
	},
	initComponent : function() {
		var me = this;
		Ext.applyIf(me, {
			items : [{
				xtype : 'textfield',
				columnWidth : 0.3,
				fieldLabel : sign.viewSignRfc
						.i18n('pkp.sign.viewSignRfc.waybillNo'), // 运单号,
				vtype: 'waybill',
				name : 'waybillNo'
			}, {
				xtype : 'combobox',
				name : 'status',
				columnWidth : 0.3,
				fieldLabel : sign.viewSignRfc
						.i18n('pkp.sign.viewSignRfc.status'), // 受理状态,
				queryModel : 'local',
				displayField : 'valueName',
				valueField : 'valueCode',
				editable : false,
				value : '',
				store : FossDataDictionary.getDataDictionaryStore(
						'PKP_SIGN_RFC_APPROVE_STATUS', null, {
							'valueCode' : '',
							'valueName' : '全部'
						})
			}, {
				xtype : 'dynamicorgcombselector',
				columnWidth : 0.3,
				fieldLabel : sign.viewSignRfc
						.i18n('pkp.sign.viewSignRfc.draftOrgCode'), // 变更申请部门,
				name : 'draftOrgCode'
			}, /*{
				xtype : 'textfield',
				columnWidth : 0.3,
				fieldLabel : sign.viewSignRfc
						.i18n('pkp.sign.viewSignRfc.arrivesheetNo1'), // 到达联号,
				name : 'arrivesheetNo'
			},*/ {
				xtype : 'rangeDateField',
				columnWidth : 0.5,
				fieldLabel : sign.viewSignRfc
						.i18n('pkp.sign.viewSignRfc.draftTime'), // 变更申请时间,
				fieldId : 'FOSS_SignRfcViewTime_Id',
				dateType : 'datetimefield_date97',
				allowBlank : false,
				allowFromBlank : false,
				allowToBlank : false,
				fromName : 'draftTimeStart',
				toName : 'draftTimeEnd',
				dateRange : 30,
				fromValue : Ext.Date.format(sign.viewSignRfc.getTargetDate(
								new Date(), 0), 'Y-m-d H:i:s'),
				toValue : Ext.Date.format(sign.viewSignRfc.getTargetDate1(
								new Date(), 0), 'Y-m-d H:i:s')
			}, {
				border : 1,
				xtype : 'container',
				columnWidth : 1,
				defaultType : 'button',
				layout : 'column',
				items : [{
							text : sign.viewSignRfc
									.i18n('pkp.sign.viewSignRfc.reset'), // 重置,
							columnWidth : .08,
							handler : function() {
								var myform = this.up('form');
//								myform.getForm().reset();
								myform.getForm().findField('waybillNo').setValue('');
								myform.getForm().findField('status').setValue('');
								myform.getForm().findField('draftOrgCode').setCombValue(FossUserContext.getCurrentDept().name,FossUserContext.getCurrentDeptCode());
								myform.getForm().findField('draftTimeStart').setValue(Ext.Date.format(sign.viewSignRfc.getTargetDate(new Date(), 0), 'Y-m-d H:i:s'));
				                myform.getForm().findField('draftTimeEnd').setValue(Ext.Date.format(sign.viewSignRfc.getTargetDate1(new Date(), 0), 'Y-m-d H:i:s'));
							}
						}, {
							xtype : 'container',
							border : false,
							columnWidth : .84,
							html : '&nbsp;'
						}, {
							text : sign.viewSignRfc
									.i18n('pkp.sign.viewSignRfc.search'), // 查询,
									disabled:!sign.viewSignRfc.isPermission('viewsignrfcindex/viewsignrfcindexquerybutton'),
							hidden:!sign.viewSignRfc.isPermission('viewsignrfcindex/viewsignrfcindexquerybutton'),
							cls : 'yellow_button',
							columnWidth : .08,
							handler : function() {
									Ext
											.getCmp('T_sign-viewSignRfcIndex_content')
											.getSignGrid().getPagingToolbar()
											.moveFirst();
							}
						}]
			}]
		});
		me.callParent(arguments);
		me.getForm().findField('draftOrgCode').setCombValue(FossUserContext.getCurrentDept().name,FossUserContext.getCurrentDeptCode());
	}
});
// 查询结果
Ext.define('Foss.sign.viewSignRfc.gridPanel', {
	extend : 'Ext.grid.Panel',
	title : sign.viewSignRfc.i18n('pkp.sign.viewSignRfc.acceptApplication'), // 受理申请,
	frame : true,
	collapsible : true,
	animCollapse : true,
	cls : 'autoHeight',
	bodyCls : 'autoHeight',
	viewConfig : {
		// 单元格可复制
		enableTextSelection: true
	},
	columns : [{
				header : 'ID',
				align : 'center',
				dataIndex : 'id',
				flex : 1,
				hidden : true
			}, {
				header : sign.viewSignRfc.i18n('pkp.sign.viewSignRfc.rfcNo'), // 申请批次号,
				align : 'center',
				dataIndex : 'rfcNo',
				flex : 1
			}, {
				header : sign.viewSignRfc
						.i18n('pkp.sign.viewSignRfc.waybillNo'), // 运单号,
				align : 'center',
				dataIndex : 'waybillNo',
				flex : 1
			}, {
				header : sign.viewSignRfc.i18n('pkp.sign.viewSignRfc.status'), // 受理状态,
				align : 'center',
				dataIndex : 'status',
				flex : 1
			}, {
				header : sign.viewSignRfc.i18n('pkp.sign.viewSignRfc.rfcType1'), // 更改类型,
				align : 'center',
				dataIndex : 'rfcType',
				flex : 1,
				renderer : function(value) {
					return FossDataDictionary.rendererSubmitToDisplay(value,
							'PKP_SIGN_RFC_TYPE');
				}
			}, {
				header : sign.viewSignRfc
						.i18n('pkp.sign.viewSignRfc.draftOrgCode'), // 变更申请部门,
				align : 'center',
				dataIndex : 'draftOrgName',
				flex : 1
			}, {
				header : sign.viewSignRfc.i18n('pkp.sign.viewSignRfc.drafter'), // 变更申请人,
				align : 'center',
				dataIndex : 'drafter',
				flex : 1
			}, {
				xtype : 'datecolumn',
				header : sign.viewSignRfc
						.i18n('pkp.sign.viewSignRfc.draftTime'), // 变更申请时间,
				align : 'center',
				dataIndex : 'draftTime',
				format : 'Y-m-d H:i:s',
				flex : 2
				
			}],
	// 表格行可展开的插件
	plugins : [{
				header : true,
				// 定义行可展开的插件ID
				pluginId : 'plugin_id',
				// 定义插件的类型
				ptype : 'rowexpander',
				// 定义行展开模式（单行与多行），默认是多行展开(值true)
				rowsExpander : false,
				// 行体内容
				rowBodyElement : 'Foss.sign.viewSignRfc.ViewSignRfcDetailForm'

			}],
	pagingToolbar : null,
	getPagingToolbar : function() {
		if (this.pagingToolbar == null) {
			this.pagingToolbar = Ext.create('Deppon.StandardPaging', {
						store : this.store,
		  				plugins: 'pagesizeplugin',
						displayInfo: true
					});
		}
		return this.pagingToolbar;
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.sign.viewSignRfc.SignRfcStore');
		me.bbar = me.getPagingToolbar();
		me.callParent([cfg]);
	}
});

// 上传付款文件的panel
Ext.define('Foss.viewSignRfc.Grid.FileUploadRepaymentGrid', {
			extend : 'Deppon.ux.FileUploadGrid',
			reviewFlag : true,
			modulePath : 'pkp-sign',
			fileTypes: ['jpg','jpeg','gif','bmp','png'],
			title : '凭证列表', // 凭证列表,
			uploadUrl : ContextPath.PKP_DELIVER + '/sign/uploadFiles.action',
			// fileTypes: '*.*',
			downLoadUrl : ContextPath.PKP_DELIVER
					+ '/sign/downLoadFiles.action',
			deleteUrl : ContextPath.PKP_DELIVER + '/sign/deleteFile.action',
			imgReviewUrl : ContextPath.PKP_DELIVER + '/sign/reviewImg.action'
		});
// 上传到达联文件的panel
Ext.define('Foss.viewSignRfc.Grid.FileUploadArriveSheetGrid', {
			extend : 'Deppon.ux.FileUploadGrid',
			reviewFlag : true,
			fileTypes: ['jpg','jpeg','gif','bmp','png'],
			modulePath : 'pkp-sign',
			title : '凭证列表', // 凭证列表,
			uploadUrl : ContextPath.PKP_DELIVER + '/sign/uploadFiles.action',
			// fileTypes: '*.*',
			downLoadUrl : ContextPath.PKP_DELIVER
					+ '/sign/downLoadFiles.action',
			deleteUrl : ContextPath.PKP_DELIVER + '/sign/deleteFile.action',
			imgReviewUrl : ContextPath.PKP_DELIVER + '/sign/reviewImg.action'
		});
// 上传运单签收结果文件的panel
Ext.define('Foss.viewSignRfc.Grid.FileUploadWaybillSignResultGrid', {
			extend : 'Deppon.ux.FileUploadGrid',
			reviewFlag : true,
			fileTypes: ['jpg','jpeg','gif','bmp','png'],
			modulePath : 'pkp-sign',
			title : '凭证列表', // 凭证列表,
			uploadUrl : ContextPath.PKP_DELIVER + '/sign/uploadFiles.action',
			// fileTypes: '*.*',
			downLoadUrl : ContextPath.PKP_DELIVER
					+ '/sign/downLoadFiles.action',
			deleteUrl : ContextPath.PKP_DELIVER + '/sign/deleteFile.action',
			imgReviewUrl : ContextPath.PKP_DELIVER + '/sign/reviewImg.action'
		});
// 反签收文件上传
Ext.define('Foss.viewSignRfc.Grid.FileUploadReverseSignRfcGrid', {
			extend : 'Deppon.ux.FileUploadGrid',
			reviewFlag : true,
			fileTypes: ['jpg','jpeg','gif','bmp','png'],
			modulePath : 'pkp-sign',
			title : '凭证列表', // 凭证列表,
			uploadUrl : ContextPath.PKP_DELIVER + '/sign/uploadFiles.action',
			// fileTypes: '*.*',
			downLoadUrl : ContextPath.PKP_DELIVER
					+ '/sign/downLoadFiles.action',
			deleteUrl : ContextPath.PKP_DELIVER + '/sign/deleteFile.action',
			imgReviewUrl : ContextPath.PKP_DELIVER + '/sign/reviewImg.action'
		});
// 反签收-空运偏线文件上传
Ext.define('Foss.viewSignRfc.Grid.FileUploadReverseAirliftPartialSignRfcGrid', {
			extend : 'Deppon.ux.FileUploadGrid',
			reviewFlag : true,
			fileTypes: ['jpg','jpeg','gif','bmp','png'],
			modulePath : 'pkp-sign-viewSignRfc',
			title : '凭证列表', // 凭证列表,
			uploadUrl : ContextPath.PKP_DELIVER + '/sign/uploadFiles.action',
			// fileTypes: '*.*',
			downLoadUrl : ContextPath.PKP_DELIVER
					+ '/sign/downLoadFiles.action',
			deleteUrl : ContextPath.PKP_DELIVER + '/sign/deleteFile.action',
			imgReviewUrl : ContextPath.PKP_DELIVER + '/sign/reviewImg.action'
		});
Ext.onReady(function() {
			Ext.QuickTips.init();
			var queryForm = Ext.create("Foss.sign.viewSignRfc.queryForm");
			var signGrid = Ext.create("Foss.sign.viewSignRfc.gridPanel", {
						id : "Foss_signRfc_viewSignRfcGridPanel_Id"
					});
			Ext.create('Ext.panel.Panel', {
						id : 'T_sign-viewSignRfcIndex_content',
						cls : "panelContentNToolbar",
						bodyCls : 'panelContentNToolbar-body',
						layout : 'auto',
						getSignGrid : function() {
							return signGrid;
						},
						getQueryForm : function() {
							
							return queryForm;
						},
						items : [queryForm, signGrid],
						renderTo : 'T_sign-viewSignRfcIndex-body'
					});
		});
