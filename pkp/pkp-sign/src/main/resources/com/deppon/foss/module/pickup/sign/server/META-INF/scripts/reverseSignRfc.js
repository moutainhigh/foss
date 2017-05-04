//是否合伙人
partnerBillingLogo = null;
// 定义付款model
Ext.define('Foss.sign.reverseSignRfc.RepaymentModel', {
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
						name : 'paymentType', // 付款方式
						type : 'string'
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
Ext.define('Foss.sign.reverseSignRfc.ArriveSheetModel', {
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
						name : 'identifyType', // 证件类型
						type : 'string'
					}, {
						name : 'identifyCode', // 证件号码
						type : 'string'
					}, {
						name : 'situation', // 签收情况
						type : 'string'
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
// 定义运单model
Ext.define('Foss.sign.reverseSignRfc.WaybillEntityModel', {
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
					},  {
						name : 'productCode',
						type : 'string',
						convert : function(value) {
						return Foss.pkp.ProductData.rendererSubmitToDisplay(value);
					}
					},{
						name : 'receiveMethod',
						convert : function(value,record) {
							if (record.get('transportType') == 'TRANS_AIRCRAFT') {// 空运
								return FossDataDictionary
										.rendererSubmitToDisplay(
												value,
												'PICKUPGOODSAIR');
							} else {
								var v = FossDataDictionary.rendererSubmitToDisplay(value, 'PICKUPGOODSHIGHWAYS');
								if(Ext.isEmpty(v) || value == v){
									v = FossDataDictionary.rendererSubmitToDisplay(value, 'PICKUPGOODSSPECIALDELIVERYTYPE');
							}
								return v;
						}
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
						name : 'transportType'
						
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
// 定义签收结果model
Ext.define('Foss.sign.reverseSignRfc.WaybillSignResultModel', {
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
						name : 'signSituation', // 签收情况
						type : 'string'
					}, {
						name : 'deliverymanName', // 提货人名称
						type : 'string'
					}, {
						name : 'identifyType', // 证件类型
						type : 'string'
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
// 定义付款store
Ext.define('Foss.sign.reverseSignRfc.RepaymentStore', {
			extend : 'Ext.data.Store',
			model : 'Foss.sign.reverseSignRfc.RepaymentModel'
		});
// 定义到达联store
Ext.define('Foss.sign.reverseSignRfc.ArriveSheetStore', {
			extend : 'Ext.data.Store',
			model : 'Foss.sign.reverseSignRfc.ArriveSheetModel'
		});
// 文件上传
Ext.define('Foss.reverseSignRfc.Grid.FileUploadGrid', {
			extend : 'Deppon.ux.FileUploadGrid',
			modulePath : 'pkp-sign',
			fileTypes: ['jpg','jpeg','gif','bmp','png'],
			title : '上传附件', // 上传凭证附件,
			uploadUrl : ContextPath.PKP_DELIVER + '/sign/uploadFiles.action',
			// fileTypes: '*.*',
			downLoadUrl : ContextPath.PKP_DELIVER
					+ '/sign/downLoadFiles.action',
			deleteUrl : ContextPath.PKP_DELIVER + '/sign/deleteFile.action',
			imgReviewUrl : ContextPath.PKP_DELIVER + '/sign/reviewImg.action'
		});
// 文件上传
Ext.define('Foss.reverseSignRfc.Grid.FileUploadGrid2', {
			extend : 'Deppon.ux.FileUploadGrid',
			modulePath : 'pkp-sign',
			fileTypes: ['jpg','jpeg','gif','bmp','png'],
			title : '上传附件', // 上传凭证附件,
			uploadUrl : ContextPath.PKP_DELIVER + '/sign/uploadFiles.action',
			// fileTypes: '*.*',
			downLoadUrl : ContextPath.PKP_DELIVER
					+ '/sign/downLoadFiles.action',
			deleteUrl : ContextPath.PKP_DELIVER + '/sign/deleteFile.action',
			imgReviewUrl : ContextPath.PKP_DELIVER + '/sign/reviewImg.action'
		});

// 反签收
Ext.define('Foss.sign.reverseSignRfc.DedicatedPanel', {
	extend : 'Ext.form.Panel',
	cls : 'autoHeight',
	bodyCls : 'autoHeight',
	defaults : {
		margin : '5 10 5 10',
		anchor : '100%'
	},
	fileUploadGrid : null,
	getFileUploadGrid : function() {
		if (this.fileUploadGrid == null) {
			this.fileUploadGrid = Ext.create(
					'Foss.reverseSignRfc.Grid.FileUploadGrid', {
						columnWidth : 1,
						autoScroll : true
					});
		}
		return this.fileUploadGrid;
	},
	// 付款GRID
	repaymentGrid : null,
	getRepaymentGrid : function() {
		if (this.repaymentGrid == null) {
			var sm = Ext.create('Ext.selection.CheckboxModel');
			this.repaymentGrid = Ext.create('Ext.grid.Panel', {
				emptyText : sign.reverseSignRfc
						.i18n('pkp.sign.reverseSignRfc.empty'), // 没有数据,
				height : 150,
				id : 'reverseSignRepaymentGridId',
				autoScroll : true,
				store : Ext.create('Foss.sign.reverseSignRfc.RepaymentStore'),
				selModel : sm,
				columns : [{
					xtype : 'gridcolumn',
					dataIndex : 'repaymentNo',
					flex : 1,
					text : sign.reverseSignRfc
							.i18n('pkp.sign.reverseSignRfc.repaymentNo')
						// 付款编号
				}, {
					xtype : 'gridcolumn',
					dataIndex : 'paymentType',
					flex : 1,
					text : sign.reverseSignRfc
							.i18n('pkp.sign.reverseSignRfc.paidMethod'),// 付款方式,
					renderer : function(value) {
						return FossDataDictionary.rendererSubmitToDisplay(
								value, 'SETTLEMENT__PAYMENT_TYPE');
					}
				}, {
					xtype : 'gridcolumn',
					dataIndex : 'actualFreight',
					flex : 1,
					text : sign.reverseSignRfc
							.i18n('pkp.sign.reverseSignRfc.actualFreight')
						// 实收到付运费
				}, {
					xtype : 'gridcolumn',
					dataIndex : 'codAmount',
					flex : 1,
					text : sign.reverseSignRfc
							.i18n('pkp.sign.reverseSignRfc.codAmount1')
						// 实收代收货款
				}, {
					xtype : 'templatecolumn',
					dataIndex : 'totMoney',
					flex : 1,
					text : sign.reverseSignRfc
							.i18n('pkp.sign.reverseSignRfc.totMoney'), // 实收到付总额,
					tpl : '{[parseInt(values.actualFreight)+parseInt(values.codAmount)]}'
				}, {
					xtype : 'gridcolumn',
					dataIndex : 'consigneeName',
					flex : 1,
					text : sign.reverseSignRfc
							.i18n('pkp.sign.reverseSignRfc.consigneeCode')
						// 提货客户
				}, {
					xtype : 'datecolumn',
					format : 'Y-m-d H:i:s',
					dataIndex : 'paymentTime',
					flex : 1,
					text : sign.reverseSignRfc
							.i18n('pkp.sign.reverseSignRfc.paymentTime')
						// 付款时间
				}],
				listeners : {
					'select' : function(rowModel, record, index, eOpts) {
						var selectRecords = rowModel.getSelection();
						if (selectRecords.length > 0) {
							Ext.getCmp('reverseSignArriveSheetGridId')
									.getSelectionModel().selectAll(true);
						}
					},
					'deselect':function(rowModel, record, index, eOpts) {
						//如果是合伙人不让取消选中-239284
						if ('Y' == partnerBillingLogo) {
							Ext.getCmp('reverseSignRepaymentGridId') .getSelectionModel().selectAll(true);
							return;
						}
					}
				},
				dockedItems:{
					xtype: 'toolbar',
					dock: 'bottom',
					hidden: true,
					items:{
						xtype : 'container',
						border : false,
						html : '<span style="color:red;">&nbsp;&nbsp;&nbsp;'+
								sign.reverseSignRfc.i18n('pkp.sign.reverseSignRfc.sonNotAllRfc')+
							'</span>'
					}
				}
			});
		}
		return this.repaymentGrid;
	},
	// 到达联GRID
	arriveSheetGrid : null,
	getArriveSheetGrid : function() {
		if (this.arriveSheetGrid == null) {
			this.arriveSheetGrid = Ext.create('Ext.grid.Panel', {
				emptyText : sign.reverseSignRfc
						.i18n('pkp.sign.reverseSignRfc.empty'), // 没有数据,
				height : 150,
				id : 'reverseSignArriveSheetGridId',
				autoScroll : true,
				store : Ext.create('Foss.sign.reverseSignRfc.ArriveSheetStore'),
				multiSelect : false,
				selModel : Ext.create('Ext.selection.CheckboxModel', {
							listeners : {
								'deselect' : function(SelectionModel, record,
										rowIndex, eOpts) {
									//如果是合伙人不让取消选中-239284
									if ('Y' == partnerBillingLogo) {
										Ext.getCmp('reverseSignArriveSheetGridId') .getSelectionModel().selectAll(true);
										return;
									}
									Ext.getCmp('reverseSignRepaymentGridId')
											.getSelectionModel()
											.deselectAll(true);
								}
							}
						}),
				columns : [{
					xtype : 'gridcolumn',
					dataIndex : 'arrivesheetNo',
					flex : 1,
					text : sign.reverseSignRfc
							.i18n('pkp.sign.reverseSignRfc.arrivesheetNo')
						// 到达联编号
				}, {
					xtype : 'gridcolumn',
					dataIndex : 'deliverymanName',
					flex : 1,
					text : sign.reverseSignRfc
							.i18n('pkp.sign.reverseSignRfc.deliverymanName')
						// 到达提货人
				}, {
					xtype : 'gridcolumn',
					dataIndex : 'identifyType',
					flex : 1,
					text : sign.reverseSignRfc
							.i18n('pkp.sign.reverseSignRfc.identifyType'), // 证件类型,
					renderer : function(value) {
						return FossDataDictionary.rendererSubmitToDisplay(
								value, 'PKP_CREDENTIAL_TYPE');
					}
				}, {
					xtype : 'gridcolumn',
					dataIndex : 'identifyCode',
					flex : 1,
					text : sign.reverseSignRfc
							.i18n('pkp.sign.reverseSignRfc.identifyCode')
						// 证件号码
				}, {
					xtype : 'gridcolumn',
					dataIndex : 'signGoodsQty',
					flex : 1,
					text : sign.reverseSignRfc
							.i18n('pkp.sign.reverseSignRfc.signGoodsQty')
						// 签收件数
				}, {
					xtype : 'gridcolumn',
					dataIndex : 'situation',
					flex : 1,
					text : sign.reverseSignRfc
							.i18n('pkp.sign.reverseSignRfc.situation'), // 签收情况,
					renderer : function(value) {
						return FossDataDictionary.rendererSubmitToDisplay(
								value, 'PKP_SIGN_SITUATION');
					}
				}, {
					xtype : 'gridcolumn',
					dataIndex : 'signNote',
					flex : 1,
					text : sign.reverseSignRfc
							.i18n('pkp.sign.reverseSignRfc.signNote')
						// 签收备注
				}, {
					xtype : 'datecolumn',
					format : 'Y-m-d H:i:s',
					dataIndex : 'signTime',
					flex : 2,
					text : sign.reverseSignRfc
							.i18n('pkp.sign.reverseSignRfc.signTime')
						// 签收时间
				}],
				listeners : {
					'select' : function(rowModel, record, index, eOpts) {
						var selectRecords = rowModel.getSelection();
						var grid = Ext.getCmp('reverseSignArriveSheetGridId');
						if (selectRecords.length != grid.getStore().data.length) {
							Ext.getCmp('reverseSignRepaymentGridId')
									.getSelectionModel().deselectAll(true);
						}
					}
				}
			});
		}
		return this.arriveSheetGrid;
	},

	initComponent : function() {
		var me = this;
		Ext.applyIf(me, {
			items : [{
				xtype : 'panel',
				frame : true,
				collapsible : true,
				animCollapse : true,
				cls : 'autoHeight',
				bodyCls : 'autoHeight',
				layout : 'column',
				title : sign.reverseSignRfc
						.i18n('pkp.sign.reverseSignRfc.searchWaybill'), // 查询运单,
				items : [{
							xtype : 'container',
							border : false,
							columnWidth : .31,
							html : '&nbsp;'
						}, {
							xtype : 'textfield',
							fieldLabel : sign.reverseSignRfc
									.i18n('pkp.sign.reverseSignRfc.waybillNo'), // 运单号,
							name : 'searchWaybillNo',
							allowBlank : false,
							vtype: 'waybill',
							columnWidth : .30
						}, {
							xtype : 'container',
							border : false,
							columnWidth : .31,
							html : '&nbsp;'
						}, {
							xtype : 'button',
							text : sign.reverseSignRfc
									.i18n('pkp.sign.reverseSignRfc.search'), // 查询,
									disabled:!sign.reverseSignRfc.isPermission('reversesignrfcindex/reversesignrfcindexqueryzbutton'),
							hidden:!sign.reverseSignRfc.isPermission('reversesignrfcindex/reversesignrfcindexqueryzbutton'),
							cls : 'yellow_button',
							plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin', {
								  //设定间隔秒数,如果不设置，默认为2秒
								  seconds: 3
							}),
							columnWidth : .08,
							handler : function() {
								var myform = this.up('form').getForm();
								var serachParms = myform.getValues();
								
								var searchWaybillNo = serachParms.searchWaybillNo;
								myform.reset();
								me.getFileUploadGrid().getStore().removeAll();
								me.getRepaymentGrid().getStore().removeAll();
								me.getArriveSheetGrid().getStore().removeAll();
								myform.findField('searchWaybillNo').setValue(searchWaybillNo);
								
								//反结清 可编辑
								me.getRepaymentGrid().setDisabled(false);
								//提示信息 隐藏
								me.getRepaymentGrid().getDockedItems('toolbar[dock=bottom]')[0].hide();
								
								if (serachParms.searchWaybillNo == '') {
									Ext.ux.Toast.msg('提示', '请输入运单号', 'error',
											1000);
									return;
								}
								if (myform.findField('searchWaybillNo').isValid()) {
									Ext.Ajax.request({
										url : sign
												.realPath('queryReverseSignRfcDedicated.action'),
										params : {
											'vo.signResultDto.signRfcEntity.waybillNo' : serachParms.searchWaybillNo
										},
										success : function(response) {
											var result = Ext
													.decode(response.responseText);
											var repaymentList = result.vo.signResultDto.repaymentArrivesheetDto.repaymentEntityList;
											var arriveSheetList = result.vo.signResultDto.repaymentArrivesheetDto.arriveSheetEntityList;
											var waybillEntity = result.vo.signResultDto.repaymentArrivesheetDto.waybillEntity;
											var isAllReverse = result.vo.signResultDto.repaymentArrivesheetDto.isAllReverse;
											partnerBillingLogo = result.vo.signResultDto.repaymentArrivesheetDto.partnerBillingLogo;//是否合伙人-239284
											if(repaymentList != null){
												me.getRepaymentGrid().getStore().loadData(repaymentList);
												//子母件未全部反签收，不可以反结清
												if(isAllReverse == 'N'){
													//反结清 不可编辑
													me.getRepaymentGrid().setDisabled(true);
													//提示信息 显示
													me.getRepaymentGrid().getDockedItems('toolbar[dock=bottom]')[0].show();
												}else{
													//反结清 可编辑
													me.getRepaymentGrid().setDisabled(false);
													//提示信息 隐藏
													me.getRepaymentGrid().getDockedItems('toolbar[dock=bottom]')[0].hide();
												}
											}
											if(arriveSheetList != null){
												me.getArriveSheetGrid().getStore().loadData(arriveSheetList);
											}
											if(waybillEntity != null){
												me.getForm().loadRecord(new Foss.sign.reverseSignRfc.WaybillEntityModel(waybillEntity));
											}
											//如果是合伙人，则货款结清信息和到达联信息全部选中，不可修改 -239284
											if ('Y' == partnerBillingLogo) {
												//设置货款结清信息全部选中
												Ext.getCmp('reverseSignRepaymentGridId').getSelectionModel().selectAll(true);
												//设置到达联信息全部选中
												Ext.getCmp('reverseSignArriveSheetGridId').getSelectionModel().selectAll(true);
											}
										},
										exception : function(response) {
											partnerBillingLogo = null;
											var result = Ext
													.decode(response.responseText);
											Ext.ux.Toast.msg('提示',
													result.message, 'error',
													1000);
										}
									});
								}
								else {
									Ext.ux.Toast.msg('提示', '请输入正确的运单号', 'error',
											1000);
									return;
								}
							}
						}]
			}, {
				xtype : 'panel',
				frame : true,
				collapsible : true,
				animCollapse : true,
				cls : 'autoHeight',
				bodyCls : 'autoHeight',
				defaults : {
					margin : '5 10 5 10',
					labelWidth : 80
				},
				layout : 'column',
				title : sign.reverseSignRfc
						.i18n('pkp.sign.reverseSignRfc.waybillInfo'), // 运单信息,
				items : [{
					xtype : 'textfield',
					fieldLabel : sign.reverseSignRfc
							.i18n('pkp.sign.reverseSignRfc.waybillNo'), // 运单号,
					readOnly : true,
					labelWidth : 60,
					columnWidth : 1 / 4,
					name : 'waybillNo'
				}, {
					xtype : 'textfield',
					fieldLabel : sign.reverseSignRfc
							.i18n('pkp.sign.reverseSignRfc.goodsName'), // 货物名称,
					readOnly : true,
					labelWidth : 90,
					columnWidth : 1 / 4,
					name : 'goodsName'
				}, {
					xtype : 'textfield',
					fieldLabel : sign.reverseSignRfc
							.i18n('pkp.sign.reverseSignRfc.goodsQtyTotal'), // 件数,
					readOnly : true,
					labelWidth : 90,
					columnWidth : 1 / 4,
					name : 'goodsQtyTotal'
				}, {
					xtype : 'textfield',
					fieldLabel : sign.reverseSignRfc
							.i18n('pkp.sign.reverseSignRfc.goodsWeightTotal'), // 总量,
					readOnly : true,
					labelWidth : 90,
					columnWidth : 1 / 4,
					name : 'goodsWeightTotal'
				}, {
					xtype : 'textfield',
					fieldLabel : sign.reverseSignRfc
							.i18n('pkp.sign.reverseSignRfc.goodsVolumeTotal'), // 体积,
					readOnly : true,
					labelWidth : 60,
					columnWidth : 1 / 4,
					name : 'goodsVolumeTotal'
				}, {
					xtype : 'textfield',
					fieldLabel : sign.reverseSignRfc
							.i18n('pkp.sign.reverseSignRfc.deliveryCustomerName'), // 发货人,
					readOnly : true,
					labelWidth : 90,
					columnWidth : 1 / 4,
					name : 'deliveryCustomerContact'
				}, {
					xtype : 'textfield',
					fieldLabel : sign.reverseSignRfc
							.i18n('pkp.sign.reverseSignRfc.receiveOrgCode'), // 发货部门,
					readOnly : true,
					labelWidth : 90,
					columnWidth : 1 / 4,
					name : 'receiveOrgCode'
				}, {
					xtype : 'textfield',
					fieldLabel : sign.reverseSignRfc
							.i18n('pkp.sign.reverseSignRfc.receiveCustomerName'), // 收货人,
					readOnly : true,
					labelWidth : 90,
					columnWidth : 1 / 4,
					name : 'receiveCustomerContact'
				}, {
					xtype : 'textfield',
					fieldLabel : sign.reverseSignRfc
							.i18n('pkp.sign.reverseSignRfc.lastLoadOrgCode'), // 到达部门,
					readOnly : true,
					labelWidth : 60,
					columnWidth : 1 / 4,
					name : 'lastLoadOrgCode'
				}, {
					xtype : 'textfield',
					fieldLabel : sign.reverseSignRfc
							.i18n('pkp.sign.reverseSignRfc.prePayAmount'), // 预付金额,
					readOnly : true,
					labelWidth : 90,
					columnWidth : 1 / 4,
					name : 'prePayAmount'
				}, {
					xtype : 'textfield',
					fieldLabel : sign.reverseSignRfc
							.i18n('pkp.sign.reverseSignRfc.toPayAmount'), // 到付金额,
					readOnly : true,
					labelWidth : 90,
					columnWidth : 1 / 4,
					name : 'toPayAmount'
				}, {
					xtype : 'textfield',
					fieldLabel : sign.reverseSignRfc
							.i18n('pkp.sign.reverseSignRfc.codAmount'), // 代收货款,
					readOnly : true,
					labelWidth : 90,
					columnWidth : 1 / 4,
					name : 'codAmount'
				}]
			}, {
				xtype : 'panel',
				frame : true,
				collapsible : true,
				animCollapse : true,
				cls : 'autoHeight',
				bodyCls : 'autoHeight',
				title : sign.reverseSignRfc
						.i18n('pkp.sign.reverseSignRfc.repaymentInfo'), // 货款结清信息,
				defaults : {
					margin : '5 10 5 10',
					labelWidth : 80
				},
				items : [me.getRepaymentGrid()]
			}, {
				xtype : 'panel',
				frame : true,
				collapsible : true,
				animCollapse : true,
				cls : 'autoHeight',
				bodyCls : 'autoHeight',
				title : sign.reverseSignRfc
						.i18n('pkp.sign.reverseSignRfc.arriveSheetInfo'), // 到达联信息,
				defaults : {
					margin : '5 10 5 10',
					labelWidth : 80
				},
				items : [me.getArriveSheetGrid()]
			}, {
				xtype : 'panel',
				// 收缩
				collapsible : true,
				// 是否有框
				frame : true,
				// 动画收缩
				animCollapse : true,
				cls : 'autoHeight',
				bodyCls : 'autoHeight',
				defaults : {
					margin : '5 10 5 10',
					labelWidth : 80
				},
				layout : 'column',
				title : sign.reverseSignRfc
						.i18n('pkp.sign.reverseSignRfc.uploadFile'), // 上传凭证,
				items : [{
					xtype : 'panel',
					cls : 'autoHeight',
					bodyCls : 'autoHeight',
					defaults : {
						margin : '5 10 5 10',
						labelWidth : 80
					},
					layout : 'column',
					items : [{
						border : 1,
						xtype : 'container',
						columnWidth : 1,
						layout : 'column',
						items : [{
							xtype : 'textareafield',
							name : 'reason',
							columnWidth : 1,
							maxLength:200,
							allowBlank : false,
							fieldLabel : sign.reverseSignRfc
									.i18n('pkp.sign.reverseSignRfc.notes')
								// 变更原因
						}, me.getFileUploadGrid()]

					}, {
						border : 1,
						xtype : 'container',
						columnWidth : 1,
						defaultType : 'button',
						layout : 'column',
						items : [{
									xtype : 'container',
									border : false,
									columnWidth : .36,
									html : '&nbsp;'
								}, {
									text : sign.reverseSignRfc
											.i18n('pkp.sign.reverseSignRfc.submit'), // 提交,
											disabled:!sign.reverseSignRfc.isPermission('reversesignrfcindex/reversesignrfcindexsavepbutton'),
									hidden:!sign.reverseSignRfc.isPermission('reversesignrfcindex/reversesignrfcindexsavepbutton'),
									columnWidth : .08,
									handler : function() {
										var parms = this.up('form').getForm()
												.getValues();
										var fileGrid = me.getFileUploadGrid();
//										var totalCount = fileGrid.getStore().data.length;
//										if (totalCount < 1) {
//											Ext.ux.Toast.msg('提示', '请上传反签收的凭证',
//													'error', 1000);
//											return;
//										}
										
										var selectRepayment = me.getRepaymentGrid().getSelectionModel().getSelection();
										var selectRepaymentIds = '';
										var selectArriveSheet = me.getArriveSheetGrid().getSelectionModel().getSelection();
										var selectArriveSheetIds = '';
										if(parms.searchWaybillNo.trim()==""){
											Ext.ux.Toast.msg('提示', '运单号不能为空！',
													'error', 3000);
											return;
										}
										if (selectRepayment.length == 0 && selectArriveSheet.length == 0) {
											Ext.ux.Toast.msg("提示信息","请选择反签收的数据！");
											return;
										} 
										var filearray = new Array();
										fileGrid.getStore().each(
												function(record) {
													filearray.push({
														'id' : record.get('id')
													});
												});

										if (parms.reason.trim()=="") {
											Ext.ux.Toast.msg('提示', '请输入反签收原因',
													'error', 1000);
											return;
										}else if(parms.reason.length > 200){
											Ext.ux.Toast.msg('提示', '反签收原因的长度不能大于200汉字',
													'error', 1000);
											return;
										}
										for (var i = 0; i < selectRepayment.length; i++) {
											if (selectRepaymentIds.length == 0) {// 拼接选择的id
												selectRepaymentIds = selectRepayment[i].data.id;
											} else {
												selectRepaymentIds = selectRepaymentIds
														+ ","
														+ selectRepayment[i].data.id;
											}
										}
										for (var i = 0; i < selectArriveSheet.length; i++) {
											if (selectArriveSheetIds.length == 0) {// 拼接选择的id
												selectArriveSheetIds = selectArriveSheet[i].data.id;
											} else {
												selectArriveSheetIds = selectArriveSheetIds
														+ ","
														+ selectArriveSheet[i].data.id;
											}
										}
										Ext.MessageBox.confirm('确认框', '是否确认反结清货款或反签收到达联操作？',
											function(btn) {
												if (btn == 'yes') {
													Ext.Ajax.request({
														url : sign
																.realPath('saveReverseSignRfcDedicated.action'),
														method : 'POST',
														jsonData : {
															'vo' : {
																'signResultDto' : {
																	'reverseSignRfcFiles' : filearray,
																	'repaymentArrivesheetDto' : {
																		'repaymentIds' : selectRepaymentIds,
																		'arriveSheetIds' : selectArriveSheetIds
																	},
																	'signRfcEntity' : {
																		'waybillNo' : parms.waybillNo,
																		'reason' : parms.reason
																	}
																}
															}
														},
														success : function(
																response) {
															Ext.ux.Toast
																	.msg(
																			'提示',
																			'保存成功!',
																			'ok',
																			1000);
															me
																	.getRepaymentGrid()
																	.getStore()
																	.removeAll();
															me
																	.getArriveSheetGrid()
																	.getStore()
																	.removeAll();
															Ext
																	.getCmp('reverseSignRepaymentGridId')
																	.getSelectionModel()
																	.deselectAll(true);
															Ext
																	.getCmp('reverseSignArriveSheetGridId')
																	.getSelectionModel()
																	.deselectAll(true);
															me
																	.getForm()
																	.reset();
															me
																	.getFileUploadGrid()
																	.getStore()
																	.removeAll();
															//反结清 可编辑
															me.getRepaymentGrid().setDisabled(false);
															//提示信息 隐藏
															me.getRepaymentGrid().getDockedItems('toolbar[dock=bottom]')[0].hide();
														},
														exception : function(
																response) {
															var result = Ext
																	.decode(response.responseText);
															Ext.ux.Toast
																	.msg(
																			'提示',
																			result.message,
																			'error',
																			1000);
														}
													});
												}
											});
										}
								}, {
									text : sign.reverseSignRfc
											.i18n('pkp.sign.reverseSignRfc.cancel'), // 取消,
									columnWidth : .08,
									handler : function() {
										var myform = this.up('form');
										myform.getForm().reset();
									}
								}, {
									xtype : 'container',
									border : false,
									columnWidth : .36,
									html : '&nbsp;'
								}]
					}]
				}]
			}]
		});
		me.callParent(arguments);
	}
});

// 空运和偏线
Ext.define('Foss.sign.reverseSignRfc.AirliftPartialLineForm', {
	extend : 'Ext.form.Panel',
	defaults : {
		margin : '5 10 5 10'
	},
	width : 1024,
	fileUploadGrid : null,
	getFileUploadGrid : function() {
		if (this.fileUploadGrid == null) {
			this.fileUploadGrid = Ext.create(
					'Foss.reverseSignRfc.Grid.FileUploadGrid2', {
						columnWidth : 1,
						autoScroll : true
					});
		}
		return this.fileUploadGrid;
	},
	layout : 'column',
	initComponent : function() {
		var me = this;
		Ext.applyIf(me, {
			items : [{
				xtype : 'form',
				frame : true,
				width : 1024,
				collapsible : true,
				animCollapse : true,
				layout : 'column',
				title : sign.reverseSignRfc
						.i18n('pkp.sign.reverseSignRfc.searchWaybill'), // 查询运单,
				items : [{
							xtype : 'container',
							border : false,
							columnWidth : .31,
							html : '&nbsp;'
						}, {
							xtype : 'textfield',
							fieldLabel : sign.reverseSignRfc
									.i18n('pkp.sign.reverseSignRfc.waybillNo'), // 运单号,
							name : 'searchWaybillNo',
							allowBlank : false,
							vtype: 'waybill',
							columnWidth : .30
						}, {
							xtype : 'container',
							border : false,
							columnWidth : .31,
							html : '&nbsp;'
						}, {
							xtype : 'button',
							text : sign.reverseSignRfc
									.i18n('pkp.sign.reverseSignRfc.search'), // 查询,
									disabled:!sign.reverseSignRfc.isPermission('reversesignrfcindex/reversesignrfcindexqueryzbutton'),
							hidden:!sign.reverseSignRfc.isPermission('reversesignrfcindex/reversesignrfcindexqueryzbutton'),
							cls : 'yellow_button',
							plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin', {
								  //设定间隔秒数,如果不设置，默认为2秒
								  seconds: 3
							}),
							columnWidth : .08,
							handler : function() {
								var myform = this.up('form').getForm();
								var serachParms = myform.getValues();
								var waybillSignResultForm = Ext
										.getCmp('waybillSignResultFormId');
								var waybillForm = Ext.getCmp('waybillFormId');
								var uploadForm = Ext.getCmp('uploadFormId');
								waybillSignResultForm.getForm().reset();
								waybillForm.getForm().reset();
								if (serachParms.searchWaybillNo == '') {
									Ext.ux.Toast.msg('提示', '请输入运单号', 'error',
											1000);
									return;
								}
								// uploadForm.getForm.reset();
								if (myform.findField('searchWaybillNo').isValid()) {
									Ext.Ajax.request({
										url : sign
												.realPath('queryReverseSignRfcAirPartial.action'),
										params : {
											'vo.signResultDto.signRfcEntity.waybillNo' : serachParms.searchWaybillNo
										},
										success : function(response) {
											var result = Ext
													.decode(response.responseText);
											var repaymentArrivesheetDto = result.vo.signResultDto.repaymentArrivesheetDto;
											var waybillEntity = repaymentArrivesheetDto.waybillEntity;
											var waybillSignResultEntity = repaymentArrivesheetDto.waybillSignResultEntity;
											var waybillModel = new Foss.sign.reverseSignRfc.WaybillEntityModel(waybillEntity);
											var waybillSignResultModel = new Foss.sign.reverseSignRfc.WaybillSignResultModel(waybillSignResultEntity);
											waybillSignResultForm
													.loadRecord(waybillSignResultModel);
											waybillForm
													.loadRecord(waybillModel);
													waybillForm.getForm().findField('xxxx').setValue('已出库');
										},
										exception : function(response) {
											var result = Ext
													.decode(response.responseText);
											Ext.ux.Toast.msg('提示',
													result.message, 'error',
													1000);
										}
									});
								}else {
									Ext.ux.Toast.msg('提示', '请输入正确的运单号', 'error',
											1000);
									return;
								}
							}
						}]
			}, {
				xtype : 'form',
				id : 'waybillSignResultFormId',
				frame : true,
				width : 1024,
				collapsible : true,
				animCollapse : true,
				layout : 'column',
				height : 160,
				defaults : {
					margin : '5 10 5 10',
					anchor : '100%',
					readOnly : true
				},
				layout : {
					type : 'column'
				},
				title : sign.reverseSignRfc
						.i18n('pkp.sign.reverseSignRfc.signInformation'), // 签收信息,
				items : [{
							xtype : 'hiddenfield',
							name : 'id' // ID
						}, {
							xtype : 'hiddenfield',
							name : 'waybillNo' // 运单号
						}, {
							xtype : 'textfield',
							name : 'deliverymanName',
							columnWidth : 1 / 3,
							fieldLabel : sign.reverseSignRfc
									.i18n('pkp.sign.reverseSignRfc.deliverymanName1')
							// 提货人名称
						}, {
							xtype : 'combobox',
							name : 'identifyType',
							labelWidth : 60,
							columnWidth : 1 / 3,
							fieldLabel : sign.reverseSignRfc
									.i18n('pkp.sign.reverseSignRfc.identifyType'), // 证件类型,
							queryModel : 'local',
							displayField : 'valueName',
							valueField : 'valueCode',
							editable : false,
							store : FossDataDictionary.getDataDictionaryStore(
									'PKP_CREDENTIAL_TYPE', null)
						}, {
							xtype : 'textfield',
							name : 'identifyCode',
							columnWidth : 1 / 3,
							fieldLabel : sign.reverseSignRfc
									.i18n('pkp.sign.reverseSignRfc.identifyCode')
							// 证件号码
						}, {
							xtype : 'combobox',
							name : 'signSituation',
							columnWidth : 1 / 3,
							fieldLabel : sign.reverseSignRfc
									.i18n('pkp.sign.reverseSignRfc.situation'), // 签收情况,
							forceSelection : true, // 只允许从下拉列表中进行选择，不能输入文本
							editable : false, // 不可编辑
							forceSelection : true,// 必须选择一个。
							valueField : 'valueCode',
							displayField : 'valueName',
							queryMode : 'local',
							triggerAction : 'all',
							store : FossDataDictionary.getDataDictionaryStore(
									'PKP_SIGN_SITUATION', null)
						}, {
							xtype : 'textfield',
							name : 'signGoodsQty',
							columnWidth : 1 / 3,
							fieldLabel : sign.reverseSignRfc
									.i18n('pkp.sign.reverseSignRfc.signGoodsQty')
							// 签收件数
						}, {
							xtype : 'datefield',// 核定时间
							format : 'Y-m-d H:i:s',
							fieldLabel : sign.reverseSignRfc
									.i18n('pkp.sign.reverseSignRfc.signTime'), // 签收时间,
							// //
							// 字段标题
							columnWidth : 1 / 3,
							name : 'signTime'
						}, {
							xtype : 'textfield',
							name : 'signNote',
							columnWidth : 1 / 3,
							fieldLabel : sign.reverseSignRfc
									.i18n('pkp.sign.reverseSignRfc.signNote')
							// 签收备注
						}]
			}, {
				xtype : 'form',
				id : 'waybillFormId',
				frame : true,
				width : 1024,
				collapsible : true,
				animCollapse : true,
				height : 250,
				defaults : {
					margin : '5 10 5 10',
					anchor : '100%',
					readOnly : true
				},
				layout : {
					type : 'column'
				},
				title : sign.reverseSignRfc
						.i18n('pkp.sign.reverseSignRfc.waybillBasicInformation'), // 运单基本信息,
				items : [{
					xtype : 'textfield',
					name : 'waybillNo',
					columnWidth : 1 / 4,
					fieldLabel : sign.reverseSignRfc
							.i18n('pkp.sign.reverseSignRfc.waybillNo')
						// 运单号
				}, {
					xtype : 'textfield',
					name : 'receiveCustomerName',
					columnWidth : 1 / 4,
					fieldLabel : sign.reverseSignRfc
							.i18n('pkp.sign.reverseSignRfc.receiveCustomerName')
						// 收货人
				}, {
					xtype : 'textfield',
					name : 'receiveCustomerAddress',
					columnWidth : 1 / 4,
					fieldLabel : sign.reverseSignRfc
							.i18n('pkp.sign.reverseSignRfc.receiveCustomerAddress')
						// 收货人地址
				}, {
					xtype : 'textfield',
					name : 'receiveCustomerPhone',
					columnWidth : 1 / 4,
					fieldLabel : sign.reverseSignRfc
							.i18n('pkp.sign.reverseSignRfc.receiveCustomerPhone')
						// 收货人电话
				}, {
					xtype : 'textfield',
					name : 'paidMethod',
					columnWidth : 1 / 4,
					fieldLabel : sign.reverseSignRfc
							.i18n('pkp.sign.reverseSignRfc.paidMethod'), // 付款方式,
					renderer : function(value) {
						return FossDataDictionary.rendererSubmitToDisplay(
								value, 'SETTLEMENT__PAYMENT_TYPE');
					}
				}, {
					xtype : 'textfield',
					name : 'toPayAmount',
					columnWidth : 1 / 4,
					fieldLabel : sign.reverseSignRfc
							.i18n('pkp.sign.reverseSignRfc.toPayAmount')
						// 到付金额
				}, {
					xtype : 'textfield',
					name : 'prePayAmount',
					columnWidth : 1 / 4,
					fieldLabel : sign.reverseSignRfc
							.i18n('pkp.sign.reverseSignRfc.prePayAmount')
						// 预付金额
				}, {
					xtype : 'textfield',
					name : 'codAmount',
					columnWidth : 1 / 4,
					fieldLabel : sign.reverseSignRfc
							.i18n('pkp.sign.reverseSignRfc.codAmount')
						// 代收货款
				}, {
					xtype : 'textfield',
					name : 'goodsName',
					columnWidth : 1 / 4,
					fieldLabel : sign.reverseSignRfc
							.i18n('pkp.sign.reverseSignRfc.goodsName')
						// 货物名称
				}, {
					xtype : 'textfield',
					name : 'insuranceAmount',
					columnWidth : 1 / 4,
					fieldLabel : sign.reverseSignRfc
							.i18n('pkp.sign.reverseSignRfc.insuranceAmount')
						// 保险价值
				}, {
					xtype : 'textfield',
					name : 'totalFee',
					columnWidth : 1 / 4,
					fieldLabel : sign.reverseSignRfc
							.i18n('pkp.sign.reverseSignRfc.totFreight')
						// 总费用
				}, {
					xtype : 'textfield',
					name : 'receiveMethod',
					columnWidth : 1 / 4,
					fieldLabel : sign.reverseSignRfc
							.i18n('pkp.sign.reverseSignRfc.receiveMethod')// 派送方式,
				}, {
					xtype : 'textfield',
					name : 'goodsWeightTotal',
					columnWidth : 1 / 4,
					fieldLabel : sign.reverseSignRfc
							.i18n('pkp.sign.reverseSignRfc.goodsWeightTotal')
						// 总量
				}, {
					xtype : 'textfield',
					name : 'goodsQtyTotal',
					columnWidth : 1 / 4,
					fieldLabel : sign.reverseSignRfc
							.i18n('pkp.sign.reverseSignRfc.goodsQtyTotal')
						// 件数
				}, {
					xtype : 'textfield',
					name : 'goodsVolumeTotal',
					columnWidth : 1 / 4,
					fieldLabel : sign.reverseSignRfc
							.i18n('pkp.sign.reverseSignRfc.goodsVolumeTotal')
						// 体积
				}, {
					xtype : 'textfield',
					name : 'goodsPackage',
					columnWidth : 1 / 4,
					fieldLabel : sign.reverseSignRfc
							.i18n('pkp.sign.reverseSignRfc.goodsPackage')
						// 包装
				}, {
					xtype : 'textfield',
					name : 'receiveOrgCode',
					columnWidth : 1 / 4,
					fieldLabel : sign.reverseSignRfc
							.i18n('pkp.sign.reverseSignRfc.receiveOrgCode')
						// 发货部门
				}, {
					xtype : 'textfield',
					name : 'deliveryCustomerContact',
					columnWidth : 1 / 4,
					fieldLabel : sign.reverseSignRfc
							.i18n('pkp.sign.reverseSignRfc.deliveryCustomerName')
						// 发货人
				}, {
					xtype : 'textfield',
					name : 'lastLoadOrgCode',
					columnWidth : 1 / 4,
					fieldLabel : sign.reverseSignRfc
							.i18n('pkp.sign.reverseSignRfc.lastLoadOrgCode')
						// 到达部门
				}, {
					xtype : 'datefield',
					format: 'Y-m-d H:i:s',
					name : 'preArriveTime',
					columnWidth : 1 / 4,
					fieldLabel : sign.reverseSignRfc
							.i18n('pkp.sign.reverseSignRfc.arriveTime')
						// 到达时间
				}, {
					xtype : 'textfield',
					name : 'productCode',
					columnWidth : 1 / 4,
					fieldLabel : sign.reverseSignRfc
							.i18n('pkp.sign.reverseSignRfc.productCode')// 运输性质,
					/*renderer : function(value) {
						return Foss.pkp.ProductData.rendererSubmitToDisplay(value);
					}*/
						// 运输性质,
				}, {
					name : 'transportType',
					columnWidth : 1 / 4,
					fieldLabel : sign.reverseSignRfc
							.i18n('pkp.sign.reverseSignRfc.transportType'),
					xtype: 'combobox',
					valueField:'valueCode', 
					displayField: 'valueName',
					queryMode:'local',
					triggerAction:'all',
					store : FossDataDictionary.getDataDictionaryStore('TRANS_TYPE', null)
						// 运输类型,
				}, {
					xtype : 'textfield',
					name : 'xxxx',
					columnWidth : 1 / 4,
					fieldLabel : sign.reverseSignRfc
							.i18n('pkp.sign.reverseSignRfc.stockStatus') // 库存状态,
				}, {
					xtype : 'datefield',
					name : 'preDepartureTime',
					format: 'Y-m-d H:i:s',
					columnWidth : 1 / 4,
					fieldLabel : sign.reverseSignRfc
							.i18n('pkp.sign.reverseSignRfc.createTime')
						// 出发时间
				}]
			}, {
				xtype : 'form',
				id : 'uploadFormId',
				frame : true,
				width : 1024,
				collapsible : true,
				animCollapse : true,
				height : 400,
				defaults : {
					margin : '5 10 5 10',
					labelWidth : 80
				},
				layout : 'column',
				title : sign.reverseSignRfc
						.i18n('pkp.sign.reverseSignRfc.uploadFile'), // 上传凭证,
				items : [{
					xtype : 'panel',
					cls : 'autoHeight',
					bodyCls : 'autoHeight',
					defaults : {
						margin : '5 10 5 10',
						labelWidth : 80
					},
					layout : 'column',
					items : [{
						border : 1,
						xtype : 'container',
						columnWidth : 1,
						layout : 'column',
						items : [{
							xtype : 'textareafield',
							name : 'reason',
							columnWidth : 1,
							maxLength:200,
							allowBlank : false,
							fieldLabel : sign.reverseSignRfc
									.i18n('pkp.sign.reverseSignRfc.notes')
								// 变更原因
						}, me.getFileUploadGrid()]

					}, {
						border : 1,
						xtype : 'container',
						columnWidth : 1,
						defaultType : 'button',
						layout : 'column',
						items : [{
									xtype : 'container',
									border : false,
									columnWidth : .36,
									html : '&nbsp;'
								}, {
									text : sign.reverseSignRfc
											.i18n('pkp.sign.reverseSignRfc.submit'), // 提交,
											disabled:!sign.reverseSignRfc.isPermission('reversesignrfcindex/reversesignrfcindexsavezbutton'),
									hidden:!sign.reverseSignRfc.isPermission('reversesignrfcindex/reversesignrfcindexsavezbutton'),
									columnWidth : .08,
									handler : function() {
										var parms = this.up('form').getForm()
												.getValues();
										var fileGrid = me.getFileUploadGrid();
//										var totalCount = fileGrid.getStore().data.length;
//										if (totalCount < 1) {
//											Ext.ux.Toast.msg('提示', '请上传反签收的凭证',
//													'error', 1000);
//											return;
//										}
										var waybillSignResultForm = Ext
												.getCmp('waybillSignResultFormId')
												.getValues();;
										if (waybillSignResultForm.waybillNo == '') {
											Ext.ux.Toast.msg('提示', '请录入运单信息',
													'error', 1000);
											return;
										}		
										var filearray = new Array();
										fileGrid.getStore().each(
												function(record) {
													filearray.push({
														'id' : record.get('id')
													});
												});

										if (parms.reason.trim()=="") {
											Ext.ux.Toast.msg('提示', '请输反签收原因',
													'error', 1000);
											return;
										}else if(parms.reason.length > 200){
											Ext.ux.Toast.msg('提示', '反签收原因的长度不能大于200汉字',
													'error', 1000);
											return;
										}
										
										Ext.MessageBox.confirm('确认框',
												'是否确认反签收空运偏线操作？',
												function(btn) {
													if (btn == 'yes') {
														Ext.Ajax.request({
															url : sign
																	.realPath('saveReverseSignRfcAirPartial.action'),
															method : 'POST',
															jsonData : {
																'vo' : {
																	'signResultDto' : {
																		'reverseSignRfcFiles' : filearray,
																		'signRfcEntity' : {
																			'waybillNo' : waybillSignResultForm.waybillNo,
																			'reason' : parms.reason,
																			'tSrvWaybillSignResultId' : waybillSignResultForm.id
																		}
																	}
																}
															},
															success : function(
																	response) {
																Ext.ux.Toast
																		.msg(
																				'提示',
																				'保存成功!',
																				'ok',
																				1000);
																me
																		.getForm()
																		.reset();
																me
																		.getFileUploadGrid()
																		.getStore()
																		.removeAll();
															},
															exception : function(
																	response) {
																var result = Ext
																		.decode(response.responseText);
																Ext.ux.Toast
																		.msg(
																				'提示',
																				result.message,
																				'error',
																				1000);
															}
														});
													}
												});

									}
								}, {
									text : sign.reverseSignRfc
											.i18n('pkp.sign.reverseSignRfc.cancel'), // 取消,
									columnWidth : .08,
									handler : function() {
										var myform = this.up('form');
										myform.getForm().reset();
									}
								}, {
									xtype : 'container',
									border : false,
									columnWidth : .36,
									html : '&nbsp;'
								}]
					}]
				}]
			}]
		});
		me.callParent(arguments);
	}
});

Ext.define('Foss.sign.reverseSignRfc.ReverseSignPanel', {
			extend : 'Ext.tab.Panel',
			cls : 'innerTabPanel',
			activeTab : 0,
			dedicatedPanel: null,
			getDedicatedPanel: function(){
				if(Ext.isEmpty(this.dedicatedPanel)){
					this.dedicatedPanel = Ext.create(
						'Foss.sign.reverseSignRfc.DedicatedPanel', {
							tabConfig : {
								width : 120
							},
							title : sign.reverseSignRfc
									.i18n('pkp.sign.reverseSignRfc.dedicated')
							// 专线
					});
					sign.reverseSignRfc.DedicatedPanel = this.dedicatedPanel;
				}
				return this.dedicatedPanel;
			},
			airliftPartialLineForm: null,
			getAirliftPartialLineForm: function(){
				if(Ext.isEmpty(this.airliftPartialLineForm)){
					this.airliftPartialLineForm = Ext.create(
						'Foss.sign.reverseSignRfc.AirliftPartialLineForm', {
							tabConfig : {
								width : 120
							},
							title : sign.reverseSignRfc
									.i18n('pkp.sign.reverseSignRfc.airliftPartialLine')
							// 空运/偏线
					});
					sign.reverseSignRfc.AirliftPartialLineForm = this.airliftPartialLineForm;
				}
				return this.airliftPartialLineForm;
			},
			initComponent : function() {
				var me = this;
				Ext.applyIf(me, {
							items : [
							         me.getDedicatedPanel(),
									 me.getAirliftPartialLineForm()
								 ]
						});

				me.callParent(arguments);
			}
		});
Ext.onReady(function() {
		Ext.QuickTips.init();
		var panel = Ext.create("Foss.sign.reverseSignRfc.ReverseSignPanel");
		Ext.create('Ext.panel.Panel', {
			id : 'T_sign-reverseSignRfcIndex_content',
			cls : "panelContentNToolbar",
			bodyCls : 'panelContentNToolbar-body',
			layout : 'auto',
			getPanel : function() {
				return panel;
			},
			items : [panel],
			renderTo : 'T_sign-reverseSignRfcIndex-body'
		});
	});
