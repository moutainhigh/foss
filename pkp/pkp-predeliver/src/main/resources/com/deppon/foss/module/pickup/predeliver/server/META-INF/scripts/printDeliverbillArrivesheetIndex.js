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
predeliver.printDeliverbillArrivesheet.getDate = function(date, hours, minutes,
		seconds, milliseconds) {
	date.setHours(hours);
	date.setMinutes(minutes);
	date.setSeconds(seconds);
	date.setMilliseconds(milliseconds);

	return date;
};

// predeliver.printDeliverbillArrivesheet.id = '';

// 定义预派送单明细的模型
Ext
		.define(
				'Foss.predeliver.printDeliverbillArrivesheet.DeliverbillDetailModel',
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
								name : 'goodsVolumeTotal', // 运单体积   
								type : 'string'
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
													2);
								}
							}, {
								name : 'transportType',convert:function(value) {
									return Foss.pkp.ProductData.rendererSubmitToDisplay(value);
								}//运输性质
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
								name : 'waybillrfcStatus', // 更改状态
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
							},{
								name : 'destroyed', // 作废
								type : 'string'
							} ]
				});

// 定义预派送单明细的Store
Ext.define('Foss.predeliver.printDeliverbillArrivesheet.DeliverbillDetailStore',
				{
					extend : 'Ext.data.Store',
					autoLoad : false,
					// 绑定一个模型
					model : 'Foss.predeliver.printDeliverbillArrivesheet.DeliverbillDetailModel',
					// 定义一个代理对象
					proxy : {
						type : 'ajax',
						actionMethods : 'POST',
						url : predeliver
								.realPath('queryDeliverbillArrivesheetListForSale.action'),
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
//派车类型集合
Ext.define('Foss.predeliver.printDeliverbillArrivesheet.store.DeliverTypeStore',{
	extend: 'Ext.data.Store',
	fields: [
		{name: 'valueCode',  type: 'string'},
		{name: 'valueName',  type: 'string'}
	],
	data: {
		'items':[
		    {'valueCode':'','valueName':predeliver.printDeliverbillArrivesheet.i18n('pkp.predeliver.deliverbillIndex.all')},// '全部'
			{'valueCode':'NOMAL','valueName':predeliver.printDeliverbillArrivesheet.i18n('pkp.predeliver.editDeliverbillIndex.nomal')},// '正常'
			{'valueCode':'SPECIAL','valueName':predeliver.printDeliverbillArrivesheet.i18n('pkp.predeliver.editDeliverbillIndex.special')},// '专车'
			{'valueCode':'MANNED','valueName':predeliver.printDeliverbillArrivesheet.i18n('pkp.predeliver.editDeliverbillIndex.manned')}//'带人'
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

// 定义派送单明细的表格
Ext.define('Foss.predeliver.printDeliverbillArrivesheet.DeliverbillDetailGrid',
				{
					extend : 'Ext.grid.Panel',
					frame : true,
					title : predeliver.printDeliverbillArrivesheet.i18n('pkp.predeliver.printDeliverbillArrivesheetIndex.arriveSheet'), //到达联,
					emptyText : predeliver.printDeliverbillArrivesheet.i18n('pkp.predeliver.printDeliverbillArrivesheetIndex.empty'), //查询结果为空,
					bodyCls : 'autoHeight',
					cls : 'autoHeight',
					autoScroll : true,
					collapsible : true,
					animCollapse : true,
					printWindow : null,
					getPrintWindow : function() {
						var me = this;
						if (this.printWindow == null) {
							me.printWindow = Ext.create(
									'Foss.printArriveSheet.printOneWindow', me);
						}
						return me.printWindow;
					},
					viewConfig : {
						// 显示重复样式，不用隔行显示
						stripeRows : false,
						enableTextSelection : true,
						getRowClass : function(record, rowIndex, p, ds) {
							// 如果存在未受理的更改，标记为粉红色
							if (record.get('waybillrfcStatus') == '') {
								return null;
							} else {
								return 'printDeliver_mark_color';
							}
						}
					},
					dockedItems : [ {
						xtype : 'toolbar',
						dock : 'top',
						layout : 'column',
						items : [ {
							xtype : 'image',
							margin : '0 0 0 780',
							imgCls : 'foss_icons_pkp_3daysNoPicking'
						}, {
							xtype : 'label',
							margin : '0 0 0 10',
							text : predeliver.printDeliverbillArrivesheet.i18n('pkp.predeliver.printDeliverbillArrivesheetIndex.notSdmissibleRFC') //存在未受理的更改单
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
								dataIndex : 'arrivesheetNo',
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
								dataIndex : 'waybillNo',
								align : 'center',
								flex : 1,
								header : predeliver.printDeliverbillArrivesheet.i18n('pkp.predeliver.printDeliverbillArrivesheetIndex.waybillNo') //运单号
							},
							{
								dataIndex : 'goodsName',
								align : 'center',
								flex : 1,
								header : predeliver.printDeliverbillArrivesheet.i18n('pkp.predeliver.printDeliverbillArrivesheetIndex.goodsName') //货物名称
							},
							{
								dataIndex : 'arrangeGoodsQty',
								align : 'center',
								flex : 1,
								header : predeliver.printDeliverbillArrivesheet.i18n('pkp.predeliver.printDeliverbillArrivesheetIndex.arrangeGoodsGty')//排单件数
							},
							{
								dataIndex : 'weight',
								align : 'center',
								flex : 1,
								header : predeliver.printDeliverbillArrivesheet.i18n('pkp.predeliver.printDeliverbillArrivesheetIndex.weight') //排单重量
							},
							{
								dataIndex : 'goodsVolumeTotal',
								align : 'center',
								flex : 1,
								header : predeliver.printDeliverbillArrivesheet.i18n('pkp.predeliver.printDeliverbillArrivesheetIndex.goodsVolumeTotal') //体积
							},
							{
								dataIndex : 'dimension',
								align : 'center',
								flex : 1,
								header : predeliver.printDeliverbillArrivesheet.i18n('pkp.predeliver.editDeliverbillIndex.goodsSize') //尺寸
							},
							{
								dataIndex : 'transportType',
								align : 'center',
								flex : 1,
								header : predeliver.printDeliverbillArrivesheet.i18n('pkp.predeliver.printDeliverbillArrivesheetIndex.transportType'),//运输性质,
								setValue : function(value) {
									this.setRawValue(Foss.pkp.ProductData.rendererSubmitToDisplay(value));
								}
							},
							{
								dataIndex : 'consignee',
								align : 'center',
								flex : 1,
								header : predeliver.printDeliverbillArrivesheet.i18n('pkp.predeliver.printDeliverbillArrivesheetIndex.receiveCustomerContact') //收货人
							},
							{
								dataIndex : 'consigneeContact',
								align : 'center',
								flex : 1,
								header : predeliver.printDeliverbillArrivesheet.i18n('pkp.predeliver.printDeliverbillArrivesheetIndex.consigneeContact') //联系方式
							},
							{
								dataIndex : 'consigneeAddress',
								align : 'center',
								flex : 1,
								header : predeliver.printDeliverbillArrivesheet.i18n('pkp.predeliver.printDeliverbillArrivesheetIndex.consigneeAddress') //收货地址
							},
							{
								dataIndex : 'deliverRequire',
								align : 'center',
								flex : 1,
								header : predeliver.printDeliverbillArrivesheet.i18n('pkp.predeliver.printDeliverbillArrivesheetIndex.deliverRequire') //送货要求
							},
							{
								dataIndex : 'goodsStatus',
								align : 'center',
								flex : 1,
								header : predeliver.printDeliverbillArrivesheet.i18n('pkp.predeliver.printDeliverbillArrivesheetIndex.goodsStatus') //货物状态
							},
							{
								dataIndex : 'isAlreadyContact',
								align : 'center',
								flex : 1,
								header : predeliver.printDeliverbillArrivesheet.i18n('pkp.predeliver.printDeliverbillArrivesheetIndex.isAlreadyContact'), //是否已联系客户,
								renderer : function(value) {
									if (value == 'Y') {
										return predeliver.printDeliverbillArrivesheet.i18n('pkp.predeliver.printDeliverbillArrivesheetIndex.yes');
									} else {
										return predeliver.printDeliverbillArrivesheet.i18n('pkp.predeliver.printDeliverbillArrivesheetIndex.no');
									}
								}
							},
							{
								dataIndex : 'estimatedDeliverTime',
								align : 'center',
								flex : 1,
								header : predeliver.printDeliverbillArrivesheet.i18n('pkp.predeliver.printDeliverbillArrivesheetIndex.estimatedDeliverTime'), //预计送货时间,
								xtype : 'datecolumn',
								format : 'Y-m-d H:i:s'
							},
							{
								dataIndex : 'isException',
								align : 'center',
								flex : 1,
								header : predeliver.printDeliverbillArrivesheet.i18n('pkp.predeliver.printDeliverbillArrivesheetIndex.isException'), //是否异常,
								renderer : function(value) {
									if (value == 'Y') {
										return predeliver.printDeliverbillArrivesheet.i18n('pkp.predeliver.printDeliverbillArrivesheetIndex.yes');
									} else {
										return predeliver.printDeliverbillArrivesheet.i18n('pkp.predeliver.printDeliverbillArrivesheetIndex.no');
									}
								}
							},
							{
								dataIndex : 'isNeedInvoice',
								align : 'center',
								flex : 1,
								header : predeliver.printDeliverbillArrivesheet.i18n('pkp.predeliver.printDeliverbillArrivesheetIndex.isNeedInvoice'), //是否需要发票,
								renderer : function(value) {
									if (value == 'Y') {
										return predeliver.printDeliverbillArrivesheet.i18n('pkp.predeliver.printDeliverbillArrivesheetIndex.yes');
									} else {
										return predeliver.printDeliverbillArrivesheet.i18n('pkp.predeliver.printDeliverbillArrivesheetIndex.no');
									}
								}
							},
							{
								dataIndex : 'paymentType',
								align : 'center',
								flex : 1,
								header : predeliver.printDeliverbillArrivesheet.i18n('pkp.predeliver.printDeliverbillArrivesheetIndex.paymentType'), //付款方式,
								renderer : function(value) {
									return FossDataDictionary
											.rendererSubmitToDisplay(value,
													'SETTLEMENT__PAYMENT_TYPE');
								}
							},
							{
								dataIndex : 'notes',
								align : 'center',
								flex : 1,
								header : predeliver.printDeliverbillArrivesheet.i18n('pkp.predeliver.printDeliverbillArrivesheetIndex.notes') //备注
							},
							{
								dataIndex : 'deliverType',
								align : 'center',
								flex : 1,
								header : predeliver.printDeliverbillArrivesheet.i18n('pkp.predeliver.printDeliverbillArrivesheetIndex.deliverType'), //送货方式,
								renderer : function(value) {
									var v = FossDataDictionary.rendererSubmitToDisplay(value, 'PICKUPGOODSHIGHWAYS');
									if(Ext.isEmpty(v) || value == v){
										v = FossDataDictionary.rendererSubmitToDisplay(value, 'PICKUPGOODSSPECIALDELIVERYTYPE');
									}
									return v;
								}
							}, {
								dataIndex : 'arrangeStatus',
								align : 'center',
								flex : 1,
								header : predeliver.printDeliverbillArrivesheet.i18n('pkp.predeliver.printDeliverbillArrivesheetIndex.arrangementTotal') //排单状态
							}, {
								dataIndex : 'waybillrfcStatus',
								align : 'center',
								flex : 1,
								hidden : true,
								header : predeliver.printDeliverbillArrivesheet.i18n('pkp.predeliver.printDeliverbillArrivesheetIndex.waybillrfcStatus') //更改状态
							} ],
					constructor : function(config) {
						var me = this, cfg = Ext.apply({}, config);
						me.store = Ext
								.create('Foss.predeliver.printDeliverbillArrivesheet.DeliverbillDetailStore');
						me.selModel = Ext.create('Ext.selection.CheckboxModel');
						me.callParent([ cfg ]);
					}
				});

// 基础信息Form
Ext.define('Foss.predeliver.printDeliverbillArrivesheet.DeliverbillBasicInfoForm',
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
								value : predeliver.printDeliverbillArrivesheet.deliverbillId
							},
							{
								name : 'deliverbillNo',
								fieldLabel : predeliver.printDeliverbillArrivesheet
										.i18n('foss.pickup.predeliver.deliverbillNo'), // 预派送单编号
								columnWidth : .33,
								readOnly : true
							},
							{
								name : 'vehicleNo',
								fieldLabel : predeliver.printDeliverbillArrivesheet
										.i18n('foss.pickup.predeliver.vehicleNo'), // 车辆
								columnWidth : .33,
								readOnly : true
							},
							{
								name : 'driverName',
								fieldLabel : predeliver.printDeliverbillArrivesheet
										.i18n('foss.pickup.predeliver.driverName'), // 司机姓名
								columnWidth : .33,
								readOnly : true
							} ]
				});

// 打印到达联Form
Ext.define('Foss.predeliver.printDeliverbillArrivesheet.PrintArrivesheetForm',
		{
			extend : 'Ext.form.Panel',
			collapsible : false,
			defaults : {
				margin : '5 15 5 15',
				labelWidth : 90
			},
			layout : 'column',
			items : [
					{
						columnWidth : .16,
						xtype : 'button',
						cls : 'yellow_button',
						text : predeliver.printDeliverbillArrivesheet.i18n('pkp.predeliver.printDeliverbillArrivesheetIndex.printChanged'), //打印已选,
						handler : function(button, e) {
							var deliverbillDetailGrid = button.up('window')
									.getDeliverbillDetailGrid();

							var selectRow = deliverbillDetailGrid
									.getSelectionModel().getSelection();

							if (selectRow.length == 0) {
								Ext.ux.Toast.msg(predeliver.printDeliverbillArrivesheet.i18n('pkp.predeliver.printDeliverbillArrivesheetIndex.tip'), "请选则要打印的到达联");
								return;
							}

							deliverbillDetailGrid.getPrintWindow().show();
						}
					}, {
						columnWidth : .08,
						xtype : 'button',
						cls : 'yellow_button',
						text : predeliver.printDeliverbillArrivesheet.i18n('pkp.predeliver.printDeliverbillArrivesheetIndex.cancel'), //取消,
						handler : function(button, e) {
							button.up('window').close();
						}
					}, {
						border : false,
						columnWidth : .76,
						html : '&nbsp;'
					} ]
		});

// 打印到达联窗口
Ext.define('Foss.predeliver.printDeliverbillArrivesheet.PrintArrivesheetWindow',
				{
					extend : 'Ext.window.Window',
					modal : true,
					closeAction : 'hide',
					width : 1000,
					title : predeliver.printDeliverbillArrivesheet.i18n('pkp.predeliver.printDeliverbillArrivesheetIndex.accordingPredeliverPrintArriveSheet'), //根据预派送单打印到达联,
					deliverbillBasicInfoForm : null,
					getDeliverbillBasicInfoForm : function() {
						if (this.deliverbillBasicInfoForm == null) {
							this.deliverbillBasicInfoForm = Ext
									.create('Foss.predeliver.printDeliverbillArrivesheet.DeliverbillBasicInfoForm');
						}
						return this.deliverbillBasicInfoForm;
					},
					// 派送单明细GRID
					deliverbillDetailGrid : null,
					getDeliverbillDetailGrid : function() {
						if (this.deliverbillDetailGrid == null) {
							this.deliverbillDetailGrid = Ext
									.create('Foss.predeliver.printDeliverbillArrivesheet.DeliverbillDetailGrid');
						}
						return this.deliverbillDetailGrid;
					},
					printArrivesheetForm : null,
					getPrintArrivesheetForm : function() {
						if (this.printArrivesheetForm == null) {
							this.printArrivesheetForm = Ext
									.create('Foss.predeliver.printDeliverbillArrivesheet.PrintArrivesheetForm');
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
Ext.define('Foss.predeliver.printDeliverbillArrivesheet.QueryDeliverbillForm',
				{
					extend : 'Ext.form.Panel',
					title : predeliver.printDeliverbillArrivesheet
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
								value : 'P',
								fieldLabel : predeliver.printDeliverbillArrivesheet
										.i18n('foss.pickup.predeliver.deliverbillNo'), // 预派送单编号
								columnWidth : .25
							},
							{
								name : 'waybillNo',			
								fieldLabel : predeliver.printDeliverbillArrivesheet
										.i18n('pkp.predeliver.deliverbillIndex.waybillNo'),   // 运单号
								columnWidth : .25
							}, 
							{
								name : 'vehicleNo',
								fieldLabel : predeliver.printDeliverbillArrivesheet
										.i18n('foss.pickup.predeliver.vehicleNo'), // 车辆
								columnWidth : .25
							},
							{
								name : 'driverName',
								fieldLabel : predeliver.printDeliverbillArrivesheet
										.i18n('foss.pickup.predeliver.driverName'), // 司机姓名
								columnWidth : .25
							}, {
								xtype : 'combobox',
								name : 'deliverType',
								fieldLabel : predeliver.printDeliverbillArrivesheet.i18n('pkp.predeliver.editDeliverbillIndex.deliveryType'), //派车类型,
								columnWidth : .25,
								displayField : 'valueName',
								valueField : 'valueCode',
								queryMode : 'local',
								triggerAction : 'all',
								editable : false,
								value : '',
								store : Ext.create('Foss.predeliver.printDeliverbillArrivesheet.store.DeliverTypeStore')
							},
							{
								xtype : 'combobox',
								name : 'status',
								fieldLabel : predeliver.printDeliverbillArrivesheet
										.i18n('foss.pickup.predeliver.deliverbillStatus'), // 预排单状态
								columnWidth : .25,
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
								fieldId : 'predeliver_printDeliverbillArrivesheet_submitTime',
								fieldLabel : predeliver.printDeliverbillArrivesheet.i18n('pkp.predeliver.printDeliverbillArrivesheetIndex.createTime'), //创建时间, // 创建时间
								dateType : 'datetimefield_date97',
								fromName : 'submitTimeBegin',
								toName : 'submitTimeEnd',
								fromValue : Ext.Date
										.format(
												predeliver.printDeliverbillArrivesheet
														.getDate(new Date(), 0,
																0, 0, 0),
												'Y-m-d H:i:s'),
								toValue : Ext.Date
										.format(
												predeliver.printDeliverbillArrivesheet
														.getDate(new Date(),
																23, 59, 59, 999),
												'Y-m-d H:i:s'),
								columnWidth : .5,
								disallowBlank : true,
								editable : false
							},
							{
								xtype : 'rangeDateField',
								fieldId : 'predeliver_printDeliverbillArrivesheet_loadEndTime',
								fieldLabel : '装车完成时间', //创建时间, // 创建时间
								dateType : 'datetimefield_date97',
								fromName : 'loadTimeBegin',
								toName : 'loadTimeEnd',
								//fromValue : Ext.Date
								///.format(
									//	predeliver.printDeliverbillArrivesheet
									//			.getDate(new Date(), 0,
									///					0, 0, 0),
									///	'Y-m-d H:i:s'),
								//toValue : Ext.Date
								//.format(
									//	predeliver.printDeliverbillArrivesheet
									//			.getDate(new Date(),
									//					23, 59, 59, 999),
									//	'Y-m-d H:i:s'),
								columnWidth : .5,
								disallowBlank : false,
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
											text : predeliver.printDeliverbillArrivesheet
													.i18n('foss.pickup.predeliver.resetButton'), // 重置
											handler : function() {
												this.up('form').getForm()
														.reset();

												predeliver.printDeliverbillArrivesheet.queryDeliverbillForm
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
																					predeliver.printDeliverbillArrivesheet
																							.getDate(
																									new Date(),
																									0,
																									0,
																									0,
																									0),
																					'Y-m-d H:i:s'),
																	'submitTimeEnd' : Ext.Date
																			.format(
																					predeliver.printDeliverbillArrivesheet
																							.getDate(
																									new Date(),
																									23,
																									59,
																									59,
																									999),
																					'Y-m-d H:i:s')
																});
											/*	this
														.up('form')
														.getForm()
														.setValues(
																{
																	'loadTimeBegin' : Ext.Date
																			.format(
																					predeliver.printDeliverbillArrivesheet
																							.getDate(
																									new Date(),
																									0,
																									0,
																									0,
																									0),
																					'Y-m-d H:i:s'),
																	'loadTimeEnd' : Ext.Date
																			.format(
																					predeliver.printDeliverbillArrivesheet
																							.getDate(
																									new Date(),
																									23,
																									59,
																									59,
																									999),
																					'Y-m-d H:i:s')
																});*/
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
											text : predeliver.printDeliverbillArrivesheet.i18n('foss.pickup.predeliver.queryButton'), // 查询
											disabled:!predeliver.printDeliverbillArrivesheet.isPermission('printdeliverbillarrivesheetindex/printdeliverbillarrivesheetindexquerybutton'),
											hidden:!predeliver.printDeliverbillArrivesheet.isPermission('printdeliverbillarrivesheetindex/printdeliverbillarrivesheetindexquerybutton'),
											handler : function() {
												predeliver.printDeliverbillArrivesheet.deliverbillGrid
														.getPagingToolbar()
														.moveFirst();
											}
										} ]
							} ]
				});

// 定义预派送单的模型
Ext.define('Foss.predeliver.printDeliverbillArrivesheet.DeliverbillModel', {
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
		name : 'driverTel',
		type : 'string'
	}, {
		name : 'payAmountTotal',
		type : 'string'
	}, {
		name : 'deliverType',
		type : 'string'
	}, {
		name : 'status',
		type : 'string'
	}, {
		name : 'createUserName',
		type : 'string'
	}, {
		name : 'submitTime'
	},{
		name:'loadEndTime',
		type : 'date',
		convert : dateConvert
	}  ]
});

// 定义预派送单的Store
Ext.define('Foss.predeliver.printDeliverbillArrivesheet.DeliverbillStore',
				{
					extend : 'Ext.data.Store',
					// autoLoad : true,
					// 绑定一个模型
					model : 'Foss.predeliver.printDeliverbillArrivesheet.DeliverbillModel',
					pageSize : 10,
					// 定义一个代理对象
					proxy : {
						type : 'ajax',
						actionMethods : 'POST',
						url : predeliver
								.realPath('queryDeliverbillListForSale.action'),
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
							var queryDeliverbillForm = predeliver.printDeliverbillArrivesheet.queryDeliverbillForm;
							
							if (!predeliver.printDeliverbillArrivesheet.queryDeliverbillForm
									.getForm().isValid()) {
								Ext.ux.Toast.msg(predeliver.printDeliverbillArrivesheet.i18n('pkp.predeliver.printDeliverbillArrivesheetIndex.tip'),
										predeliver.printDeliverbillArrivesheet.i18n('pkp.predeliver.printDeliverbillArrivesheetIndex.creatTimeIsNotAllowNull'),
										'error', 4000);

								return false;
							}
							
							// 时间验证
							var submitTimeBegin = queryDeliverbillForm.getValues().submitTimeBegin, submitTimeEnd = queryDeliverbillForm.getValues().submitTimeEnd;
							if (!Ext.isEmpty(submitTimeBegin) && !Ext.isEmpty(submitTimeEnd)) {	
								var result = Ext.Date.parse(submitTimeEnd,'Y-m-d H:i:s') - Ext.Date.parse(submitTimeBegin,'Y-m-d H:i:s');	
								if(result / (24 * 60 * 60 * 1000) >= 30){	
									Ext.ux.Toast.msg(predeliver.printDeliverbillArrivesheet.i18n('pkp.predeliver.printDeliverbillArrivesheetIndex.tip'),
											predeliver.printDeliverbillArrivesheet.i18n('pkp.predeliver.deliverbillIndex.submitTimeDateIntervalError'),
											'error', 4000);
									return false;
								}	
							}else {
								Ext.ux.Toast.msg(predeliver.printDeliverbillArrivesheet.i18n('pkp.predeliver.printDeliverbillArrivesheetIndex.tip'),
										predeliver.printDeliverbillArrivesheet.i18n('pkp.predeliver.printDeliverbillArrivesheetIndex.creatTimeIsNotAllowNull'),
										'error', 4000);
								return false;
							}
							
							var loadTimeBegin = queryDeliverbillForm.getValues().loadTimeBegin, loadTimeEnd = queryDeliverbillForm.getValues().loadTimeEnd;
							if (!Ext.isEmpty(loadTimeBegin) && !Ext.isEmpty(loadTimeEnd)) {	
								var result1 = Ext.Date.parse(loadTimeEnd,'Y-m-d H:i:s') - Ext.Date.parse(loadTimeBegin,'Y-m-d H:i:s');	
								if(result1 / (24 * 60 * 60 * 1000) >= 30){	
									Ext.ux.Toast.msg(predeliver.printDeliverbillArrivesheet.i18n('pkp.predeliver.printDeliverbillArrivesheetIndex.tip'),'装车完成起止日期不能超过30天',
											'error', 4000);
									return false;
								}	
							}
							if((!Ext.isEmpty(loadTimeBegin) && Ext.isEmpty(loadTimeEnd))||(!Ext.isEmpty(loadTimeEnd) && Ext.isEmpty(loadTimeBegin))){
								Ext.ux.Toast.msg(predeliver.printDeliverbillArrivesheet.i18n('pkp.predeliver.printDeliverbillArrivesheetIndex.tip'),'装车完成起止日期不能超过30天',
											'error', 4000);
									return false;
							}
							
							if (queryDeliverbillForm != null) {
								var queryParams = queryDeliverbillForm.getValues();
																							
								Ext.apply(operation,
												{
													params : {
														'deliverbillVo.deliverbillDto.deliverbillNo' : queryParams.deliverbillNo,
														'deliverbillVo.deliverbillDto.waybillNo' : queryParams.waybillNo,
														'deliverbillVo.deliverbillDto.vehicleNo' : queryParams.vehicleNo,
														'deliverbillVo.deliverbillDto.driverName' : queryParams.driverName,
														'deliverbillVo.deliverbillDto.deliverType' : queryParams.deliverType,
														'deliverbillVo.deliverbillDto.status' : queryParams.status,
														'deliverbillVo.deliverbillDto.submitTimeBegin' : queryParams.submitTimeBegin,
														'deliverbillVo.deliverbillDto.submitTimeEnd' : queryParams.submitTimeEnd,
														'deliverbillVo.deliverbillDto.loadTimeBegin' : loadTimeBegin,
														'deliverbillVo.deliverbillDto.loadTimeEnd' : loadTimeEnd
													}
												});
							}
						}
					}
				});

// 定义一个派送单的表格
Ext.define('Foss.predeliver.printDeliverbillArrivesheet.DeliverbillGrid',
				{
					extend : 'Ext.grid.Panel',
					frame : true,
					title : predeliver.printDeliverbillArrivesheet.i18n('pkp.predeliver.printDeliverbillArrivesheetIndex.deliverBill'), //派送单, // 派送单历史
					emptyText : predeliver.printDeliverbillArrivesheet.i18n('pkp.predeliver.printDeliverbillArrivesheetIndex.empty'), //查询结果为空,
					height:500,
					autoScroll : true,
					collapsible : true,
					animCollapse : true,
					//selModel:Ext.create('Ext.selection.CheckboxModel'),
				    viewConfig: {
				        enableTextSelection: true
				    },
					columns : [
							{
								dataIndex : 'id',
								align : 'center',
								hidden : true
							},
							{
								xtype : 'actioncolumn',
								align : 'center',
								flex : 0.5,
								header : predeliver.printDeliverbillArrivesheet.i18n('foss.pickup.predeliver.printArriveSheet'),
								items : [ 
								{
									//查看派送单明细
									iconCls: 'deppon_icons_showdetail',
									//tooltip标准的快捷提示实现，用于悬浮在目标元素之上时出现的提示信息。
									tooltip: predeliver.printDeliverbillArrivesheet.i18n('pkp.predeliver.exceptionProcess.showdetail'),//查看,
									handler: function(grid, rowIndex, colIndex) {
										var selection = grid.getStore().getAt(rowIndex);
										var deliverId = selection.get('id');
										
										Ext.Ajax.request({
											url:predeliver.realPath('queryDeliverbillDetailsList.action'),
											params:{'deliverbillVo.deliverbillDto.id':deliverId},
											success:function(response){
												var result = Ext.decode(response.responseText);	
												predeliver.printDeliverbillArrivesheet.DeliverbillDetailsGrid.store.loadData(result.deliverbillVo.deliverbillDetailList);
												var queryWin = Ext.create('Foss.predeliver.printDeliverbillArrivesheet.QueryDeliverDetailsWindow').show();
											}
										});
									}
								},{
									// 打印到达联
									iconCls : 'deppon_icons_print',
									tooltip : predeliver.printDeliverbillArrivesheet.i18n('pkp.predeliver.printDeliverbillArrivesheetIndex.printArriveSheet'), //打印到达联,
									disabled:!predeliver.printDeliverbillArrivesheet.isPermission('printdeliverbillarrivesheetindex/printdeliverbillarrivesheetindexprintbutton'),
									handler : function(grid, rowIndex, colIndex) {
										// 打印到达联
										var printArrivesheetWindow = Ext.getCmp('T_predeliver-printDeliverbillArrivesheetIndex_content')
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
															.realPath('queryDeliverbillForSale.action'),
													params : {
														'deliverbillVo.deliverbillDto.id' : deliverbillId
													},
													success : function(response) {
														var result = Ext
																.decode(response.responseText);
														var deliverbill = Ext.ModelManager
																.create(
																		result.deliverbillVo.deliverbill,
																		'Foss.predeliver.printDeliverbillArrivesheet.DeliverbillModel');

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
								dataIndex : 'deliverbillNo',
								align : 'center',
								flex : 1,
								header : predeliver.printDeliverbillArrivesheet
										.i18n('foss.pickup.predeliver.deliverbillNo')
							// 派送单号
							},
							{
								dataIndex : 'vehicleNo',
								align : 'center',
								flex : 1,
								header : predeliver.printDeliverbillArrivesheet
										.i18n('foss.pickup.predeliver.vehicleNo')
							// 车辆
							},
							{
								dataIndex : 'driverName',
								align : 'center',
								flex : 1,
								header : predeliver.printDeliverbillArrivesheet
										.i18n('foss.pickup.predeliver.driverName')
							// 司机姓名
							},
							{
								dataIndex : 'driverTel',
								align : 'center',
								flex : 1,
								header : predeliver.printDeliverbillArrivesheet
										.i18n('pkp.predeliver.confirmDeliverbill.empPhone')
							// 司机电话
							},
							{
								dataIndex : 'loadEndTime',
								align:'center',
								flex:1.5,
								header:'装车完成时间',
								renderer : function(value) {
									if (value != null) {
										var date = Ext.Date.format(new Date(
												value), 'Y-m-d H:i:s');
										return date;
									} else {
										return null;
									}
								}
							},
							{
								dataIndex : 'payAmountTotal',
								align : 'center',
								flex : 1,
								header : predeliver.printDeliverbillArrivesheet.i18n('pkp.predeliver.notifyCustomer.toTotalPayAmount') // 到付金额
							},
							{
								dataIndex : 'deliverType',
								align : 'center',
								flex : 1,
								renderer:function(value){
									if(value=='NOMAL'){
										return predeliver.printDeliverbillArrivesheet.i18n('pkp.predeliver.editDeliverbillIndex.nomal');
									}else if(value=='SPECIAL'){
										return predeliver.printDeliverbillArrivesheet.i18n('pkp.predeliver.editDeliverbillIndex.special');
									}else if(value=='MANNED'){
										return predeliver.printDeliverbillArrivesheet.i18n('pkp.predeliver.editDeliverbillIndex.manned');
									}
								},
								header : predeliver.printDeliverbillArrivesheet
										.i18n('pkp.predeliver.editDeliverbillIndex.deliveryType')
							// 派车类型
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
								header : predeliver.printDeliverbillArrivesheet
										.i18n('foss.pickup.predeliver.deliverbillStatus')
							// 状态
							},
							{
								dataIndex : 'createUserName',
								align : 'center',
								width:60,
								header : predeliver.printDeliverbillArrivesheet.i18n('pkp.predeliver.printDeliverbillArrivesheetIndex.createUserName') //创建人
							},
							{
								dataIndex : 'submitTime',
								align : 'center',
								flex : 2,
								header : predeliver.printDeliverbillArrivesheet.i18n('pkp.predeliver.printDeliverbillArrivesheetIndex.createTime'), //创建时间,
								renderer : function(value) {
									if (value != null) {
										var date = Ext.Date.format(new Date(
												value), 'Y-m-d H:i:s');
										return date;
									} else {
										return null;
									}
								}
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
					constructor : function(config) {
						var me = this, cfg = Ext.apply({}, config);
						me.store = Ext.create('Foss.predeliver.printDeliverbillArrivesheet.DeliverbillStore');
						me.dockedItems = [{
							xtype : 'toolbar',
							dock : 'top',
							layout : 'column',
							defaults:{
								margin:'0 0 5 3',
								allowBlank:true
							},							
							items: [{
									xtype : 'container',
									border : false,
									columnWidth : .77,
									html : '&nbsp;'
							},{
								xtype:'button',
								allowBlank:true,
								name:'exportDeliverbillDetail',
								columnWidth:.13,
								text:predeliver.printDeliverbillArrivesheet.i18n('pkp.predeliver.exceptionProcess.exportDeliverbillDetail'),//导出派送单明细,
								handler : function(){
								//获取查询出来的异常信息
								var queryDeliverbillForm = predeliver.printDeliverbillArrivesheet.queryDeliverbillForm;
								if (!predeliver.printDeliverbillArrivesheet.queryDeliverbillForm
										.getForm().isValid()) {
									Ext.ux.Toast.msg(predeliver.printDeliverbillArrivesheet.i18n('pkp.predeliver.printDeliverbillArrivesheetIndex.tip'),
											predeliver.printDeliverbillArrivesheet.i18n('pkp.predeliver.printDeliverbillArrivesheetIndex.creatTimeIsNotAllowNull'),
											'error', 4000);
									return ;
								}
								// 时间验证
								var submitTimeBegin = queryDeliverbillForm.getValues().submitTimeBegin, submitTimeEnd = queryDeliverbillForm.getValues().submitTimeEnd;
								if (!Ext.isEmpty(submitTimeBegin) && !Ext.isEmpty(submitTimeEnd)) {	
									var result = Ext.Date.parse(submitTimeEnd,'Y-m-d H:i:s') - Ext.Date.parse(submitTimeBegin,'Y-m-d H:i:s');	
									if(result / (24 * 60 * 60 * 1000) >= 30){	
										Ext.ux.Toast.msg(predeliver.printDeliverbillArrivesheet.i18n('pkp.predeliver.printDeliverbillArrivesheetIndex.tip'),
												predeliver.printDeliverbillArrivesheet.i18n('pkp.predeliver.deliverbillIndex.submitTimeDateIntervalError'),
												'error', 4000);
										return ;
									}	
								}else {
									Ext.ux.Toast.msg(predeliver.printDeliverbillArrivesheet.i18n('pkp.predeliver.printDeliverbillArrivesheetIndex.tip'),
											predeliver.printDeliverbillArrivesheet.i18n('pkp.predeliver.printDeliverbillArrivesheetIndex.creatTimeIsNotAllowNull'),
											'error', 4000);

									return ;
								}
								
								var loadTimeBegin = queryDeliverbillForm.getValues().loadTimeBegin, loadTimeEnd = queryDeliverbillForm.getValues().loadTimeEnd;
								if (!Ext.isEmpty(loadTimeBegin) && !Ext.isEmpty(loadTimeEnd)) {	
									var result1 = Ext.Date.parse(loadTimeEnd,'Y-m-d H:i:s') - Ext.Date.parse(loadTimeBegin,'Y-m-d H:i:s');	
									if(result1 / (24 * 60 * 60 * 1000) >= 30){	
										Ext.ux.Toast.msg(predeliver.printDeliverbillArrivesheet.i18n('pkp.predeliver.printDeliverbillArrivesheetIndex.tip'),'装车完成起止日期不能超过30天',
												'error', 4000);
										return false;
									}	
								}
								if((!Ext.isEmpty(loadTimeBegin) && Ext.isEmpty(loadTimeEnd))||(!Ext.isEmpty(loadTimeEnd) && Ext.isEmpty(loadTimeBegin))){
									Ext.ux.Toast.msg(predeliver.printDeliverbillArrivesheet.i18n('pkp.predeliver.printDeliverbillArrivesheetIndex.tip'),'装车完成起止日期不能超过30天',
												'error', 4000);
										return false;
								}
								if (queryDeliverbillForm != null) {
									var queryParams = queryDeliverbillForm.getValues();
									if(!Ext.fly('downloadAttachFileForm')){
									    var frm = document.createElement('form');
									    frm.id = 'downloadAttachFileForm';
									    frm.style.display = 'none';
									    document.body.appendChild(frm);
									}
									//获取查询出来的异常信息
									var deliverGridStore = predeliver.printDeliverbillArrivesheet.deliverbillGrid.getStore();	
									//若异常信息不为空
									if(deliverGridStore.getCount()!=0){
									Ext.Ajax.request({
										url:predeliver.realPath('exportDeliverbillDetail.action'),
										form: Ext.fly('downloadAttachFileForm'),
										method : 'POST',
										params : {
													'deliverbillVo.deliverbillDto.deliverbillNo' : queryParams.deliverbillNo,
													'deliverbillVo.deliverbillDto.vehicleNo' : queryParams.vehicleNo,
													'deliverbillVo.deliverbillDto.driverName' : queryParams.driverName,
													'deliverbillVo.deliverbillDto.waybillNo' : queryParams.waybillNo,
													'deliverbillVo.deliverbillDto.deliverType' : queryParams.deliverType,
													'deliverbillVo.deliverbillDto.status' : queryParams.status,
													'deliverbillVo.deliverbillDto.submitTimeBegin' : queryParams.submitTimeBegin,
													'deliverbillVo.deliverbillDto.submitTimeEnd' : queryParams.submitTimeEnd,
													'deliverbillVo.deliverbillDto.loadTimeBegin' : loadTimeBegin,
													'deliverbillVo.deliverbillDto.loadTimeEnd' : loadTimeEnd
												},
										isUpload: true
										});
									}
								}else {
									//或者提示不能导出
									Ext.ux.Toast.msg(predeliver.printDeliverbillArrivesheet.i18n('pkp.predeliver.printDeliverbillArrivesheetIndex.tip'),predeliver.printDeliverbillArrivesheet.i18n('pkp.predeliver.exceptionProcess.exceptionInfo.notExport'), 'error', 3000);
								}
							}
						},{
									xtype:'button',
									allowBlank:true,
									name:'exportDeliverbill',
									columnWidth:.1,
									text:predeliver.printDeliverbillArrivesheet.i18n('pkp.predeliver.exceptionProcess.exportDeliverbill'),//导出派送单,
									handler : function(){

									var queryDeliverbillForm = predeliver.printDeliverbillArrivesheet.queryDeliverbillForm;
								
									var loadTimeBegin = queryDeliverbillForm.getValues().loadTimeBegin, loadTimeEnd = queryDeliverbillForm.getValues().loadTimeEnd;
									if (!Ext.isEmpty(loadTimeBegin) && !Ext.isEmpty(loadTimeEnd)) {	
										var result1 = Ext.Date.parse(loadTimeEnd,'Y-m-d H:i:s') - Ext.Date.parse(loadTimeBegin,'Y-m-d H:i:s');	
										if(result1 / (24 * 60 * 60 * 1000) >= 30){	
											Ext.ux.Toast.msg(predeliver.printDeliverbillArrivesheet.i18n('pkp.predeliver.printDeliverbillArrivesheetIndex.tip'),'装车完成起止日期不能超过30天',
													'error', 4000);
											return false;
										}	
									}
									if((!Ext.isEmpty(loadTimeBegin) && Ext.isEmpty(loadTimeEnd))||(!Ext.isEmpty(loadTimeEnd) && Ext.isEmpty(loadTimeBegin))){
										Ext.ux.Toast.msg(predeliver.printDeliverbillArrivesheet.i18n('pkp.predeliver.printDeliverbillArrivesheetIndex.tip'),'装车完成起止日期不能超过30天',
													'error', 4000);
											return false;
									}
									if (queryDeliverbillForm != null) {
										var queryParams = queryDeliverbillForm.getValues();
										if(!Ext.fly('downloadAttachFileForm')){
										    var frm = document.createElement('form');
										    frm.id = 'downloadAttachFileForm';
										    frm.style.display = 'none';
										    document.body.appendChild(frm);
										}	
										//获取查询出来的异常信息
										var deliverGridStore = predeliver.printDeliverbillArrivesheet.deliverbillGrid.getStore();	
										//若异常信息不为空
										if(deliverGridStore.getCount()!=0){
											Ext.Ajax.request({
												url:predeliver.realPath('exportDeliverbill.action'),
												form: Ext.fly('downloadAttachFileForm'),
												method : 'POST',
												params : {
													'deliverbillVo.deliverbillDto.deliverbillNo' : queryParams.deliverbillNo,
													'deliverbillVo.deliverbillDto.vehicleNo' : queryParams.vehicleNo,
													'deliverbillVo.deliverbillDto.driverName' : queryParams.driverName,
													'deliverbillVo.deliverbillDto.waybillNo' : queryParams.waybillNo,
													'deliverbillVo.deliverbillDto.deliverType' : queryParams.deliverType,
													'deliverbillVo.deliverbillDto.status' : queryParams.status,
													'deliverbillVo.deliverbillDto.submitTimeBegin' : queryParams.submitTimeBegin,
													'deliverbillVo.deliverbillDto.submitTimeEnd' : queryParams.submitTimeEnd,
													'deliverbillVo.deliverbillDto.loadTimeBegin' : loadTimeBegin,
													'deliverbillVo.deliverbillDto.loadTimeEnd' : loadTimeEnd
												},
												isUpload: true
											});
										}else{
											//或者提示不能导出
											Ext.ux.Toast.msg(predeliver.printDeliverbillArrivesheet.i18n('pkp.predeliver.printDeliverbillArrivesheetIndex.tip'),predeliver.printDeliverbillArrivesheet.i18n('pkp.predeliver.exceptionProcess.exceptionInfo.notExport'), 'error', 3000);
										}
									}
								}
							}]	
						},{
							xtype: 'toolbar',
							dock: 'bottom',
							layout:'column',
							defaults:{
								margin:'0 0 5 3',
								allowBlank:true
							}
						}],
						me.bbar = me.getPagingToolbar();
						me.callParent([ cfg ]);
					}
				});
		

// 定义预派送单明细（待排运单复用）的模型
Ext.define('Foss.predeliver.printDeliverbillArrivesheet.DeliverbillDetailsModel',
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
													2);
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
							} ]
				});				
				
				
// 定义预派送单明细的Store
Ext.define('Foss.predeliver.printDeliverbillArrivesheet.DeliverbillDetailsStore',
				{
					extend : 'Ext.data.Store',
					// 绑定一个模型
					model : 'Foss.predeliver.printDeliverbillArrivesheet.DeliverbillDetailsModel',
					//是否自动查询
					autoLoad: false,
					proxy: {
				        type: 'memory',
				        reader: {
				            type: 'json',
				            root: 'items'
				        }
					}
				});
				
				
				
// 定义派送单明细的表格
Ext.define('Foss.predeliver.editDeliverbill.DeliverbillDetailsGrid',
				{
					extend : 'Ext.grid.Panel',
					frame : true,
					title : predeliver.printDeliverbillArrivesheet.i18n('pkp.predeliver.confirmDeliverbill.deliverbillInfo'), // 排单信息
					emptyText : predeliver.printDeliverbillArrivesheet.i18n('pkp.predeliver.editDeliverbillIndex.emptyText'), //查询结果为空,
					height : 500,
					autoScroll : true,
					collapsible : true,
					animCollapse : true,
					store: Ext.create('Foss.predeliver.printDeliverbillArrivesheet.DeliverbillDetailsStore'),	
					viewConfig :{
						//显示重复样式，不用隔行显示
					 	stripeRows: false,
					 	enableTextSelection: true
					},
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
								dataIndex : 'waybillNo',
								align : 'center',
								flex : 1,
								header : predeliver.printDeliverbillArrivesheet.i18n('pkp.predeliver.editDeliverbillIndex.waybillNo') //运单号
							},
							{
								dataIndex : 'goodsName',
								align : 'center',
								flex : 1,
								header : predeliver.printDeliverbillArrivesheet.i18n('pkp.predeliver.editDeliverbillIndex.goodsName') //货物名称
							},
							{
								dataIndex : 'weight',
								align : 'center',
								flex : 1,
								header : predeliver.printDeliverbillArrivesheet.i18n('pkp.predeliver.editDeliverbillIndex.weight') //排单重量
							},
							{
								dataIndex : 'dimension',
								align : 'center',
								flex : 1,
								header : predeliver.printDeliverbillArrivesheet.i18n('pkp.predeliver.editDeliverbillIndex.goodsSize') //尺寸
							},
							{
								dataIndex : 'goodsVolumeTotal',
								align : 'center',
								flex : 1,
								header : predeliver.printDeliverbillArrivesheet.i18n('pkp.predeliver.editDeliverbillIndex.dimension') //体积
							},
							{
								dataIndex : 'goodsPackage',
								align : 'center',
								flex : 1,
								header : predeliver.printDeliverbillArrivesheet.i18n('pkp.predeliver.editDeliverbillIndex.goodsPackage') //包装
							},
							{
								dataIndex : 'waybillGoodsQty',
								align : 'center',
								flex : 1,
								header : predeliver.printDeliverbillArrivesheet.i18n('pkp.predeliver.editDeliverbillIndex.pieces') //件数
							},
							{
								dataIndex : 'arrangeGoodsQty',
								align : 'center',
								flex : 1,
								header : predeliver.printDeliverbillArrivesheet.i18n('pkp.predeliver.editDeliverbillIndex.arrangeGoodsQty') //排单件数
							},
							{
								dataIndex : 'transportType',
								align : 'center',
								flex : 1,
								header : predeliver.printDeliverbillArrivesheet.i18n('pkp.predeliver.editDeliverbillIndex.transportType'), //运输方式,
								renderer : function(value) {
									return Foss.pkp.ProductData.rendererSubmitToDisplay(value);
								}
							}, {
								dataIndex : 'arriveTime',
								align : 'center',
								flex : 1,
								header : predeliver.printDeliverbillArrivesheet.i18n('pkp.predeliver.editDeliverbillIndex.arriveTime'), //到达时间,
								xtype : 'datecolumn',
								format : 'Y-m-d H:i:s'
							}, {
								dataIndex : 'consignee',
								align : 'center',
								flex : 1,
								header : predeliver.printDeliverbillArrivesheet.i18n('pkp.predeliver.editDeliverbillIndex.consignee') //收货人
							}, {
								dataIndex : 'consigneeContact',
								align : 'center',
								xtype: 'ellipsiscolumn',
								flex : 1,
								header : predeliver.printDeliverbillArrivesheet.i18n('pkp.predeliver.editDeliverbillIndex.consigneeContact') //联系方式
							}, {
								dataIndex : 'consigneeAddress',
								align : 'center',
								xtype: 'ellipsiscolumn',
								flex : 1,
								header : predeliver.printDeliverbillArrivesheet.i18n('pkp.predeliver.editDeliverbillIndex.consigneeAddress') //送货地址
							}]
				});				
				
predeliver.printDeliverbillArrivesheet.DeliverbillDetailsGrid = Ext.create('Foss.predeliver.editDeliverbill.DeliverbillDetailsGrid');		
//定义查询详细派送单明细window
Ext.define('Foss.predeliver.printDeliverbillArrivesheet.QueryDeliverDetailsWindow', {
	extend : 'Ext.window.Window',
	closeAction : 'hide',
	resizable : false,
	modal : 'true',
	width : 1024,
	height : 600,
	items : [predeliver.printDeliverbillArrivesheet.DeliverbillDetailsGrid],
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

Ext.onReady(function() {
			Ext.QuickTips.init();

			predeliver.printDeliverbillArrivesheet.queryDeliverbillForm = Ext
					.create('Foss.predeliver.printDeliverbillArrivesheet.QueryDeliverbillForm');

			predeliver.printDeliverbillArrivesheet.deliverbillGrid = Ext
					.create('Foss.predeliver.printDeliverbillArrivesheet.DeliverbillGrid');

			// 派送单下拉框增加“全部”
			var deliverbillStatusCombo = predeliver.printDeliverbillArrivesheet.queryDeliverbillForm
					.getForm().findField('status');

			deliverbillStatusCombo.store.add({
				'valueCode' : '',
				'valueName' : predeliver.printDeliverbillArrivesheet.i18n('pkp.predeliver.printDeliverbillArrivesheetIndex.all')
			});

			deliverbillStatusCombo.select('');

			// 打印到达联窗口
			predeliver.printDeliverbillArrivesheet.printArrivesheetWindow = null;

			Ext.create('Ext.panel.Panel',
							{
								id : 'T_predeliver-printDeliverbillArrivesheetIndex_content',
								cls : "panelContentNToolbar",
								bodyCls : 'panelContentNToolbar-body',
								layout : 'auto',
								getPrintArrivesheetWindow : function() {
									if (predeliver.printDeliverbillArrivesheet.printArrivesheetWindow == null) {
										predeliver.printDeliverbillArrivesheet.printArrivesheetWindow = Ext
												.create('Foss.predeliver.printDeliverbillArrivesheet.PrintArrivesheetWindow');
									}

									return predeliver.printDeliverbillArrivesheet.printArrivesheetWindow;
								},
								items : [
										predeliver.printDeliverbillArrivesheet.queryDeliverbillForm,
										predeliver.printDeliverbillArrivesheet.deliverbillGrid ],
								renderTo : 'T_predeliver-printDeliverbillArrivesheetIndex-body'
							});
		});
