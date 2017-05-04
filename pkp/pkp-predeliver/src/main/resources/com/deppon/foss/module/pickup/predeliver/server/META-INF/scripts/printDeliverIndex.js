Ext.define('Foss.predeliver.deliverbill.PrintModel', {
	extend : 'Ext.data.Model',
	fields : [ {
		name : 'deliverbillNo' // 派送单号
	}, {
		name : 'driverName' // 司机姓名
	}, {
		name : 'vehicleNo'// 车牌号
	}, {
		name : 'driverTel'// 司机电话号码
	}, {
		name : 'deliveryDepartment'// 派送部
	}, {
		name : 'motorcade'// 车队
	}, {
		name : 'weightTotal'// 总重量
	}, {
		name : 'waybillQtyTotal'// 总票数
	}, {
		name : 'payAmountTotal'// 总到付
	}, {
		name : 'volumeTotal'// 总体积
	}, {
		name : 'goodsQtyTotal'// 总件数
	}, {
		name : 'loadingRate'// 装载率
	} ]
});

Ext.define('Foss.predeliver.deliverbill.PrintDeliverbillModel', {
	extend : 'Ext.data.Model',
	fields : [ {
		name : 'waybillNo', // 运单号
		type : 'string'
	}, {
		name : 'goodsName', // 货物名称
		type : 'string'
	}, {
		name : 'goodsPackage',// 包装
		type : 'string'
	}, {
		name : 'singleSignStatus',// 签收单
		type : 'string'
	}, {
		name : 'goodsInfo',// 重量/体积/件数
		type : 'string'
	}, {
		name : 'payAmount', // 到付金额
		type : 'number'
	}, {
		name : 'transportType',convert:function(value) { // 运输性质
			return Foss.pkp.ProductData.rendererSubmitToDisplay(value);
		}
	}, {
		name : 'districtName',// 行政区域 DMANA-4290
		type : 'string'
	}, {
		name : 'dimension',// 货物尺寸
		type : 'string'
	}, {
		name : 'consigneeInfo',// 送货地址
		type : 'string'
	}, {
		name : 'deliverRequire',// 送货要求
		type : 'string'
	} ]
});

Ext.define('Foss.predeliver.deliverbill.PrintHeadForm', {
	extend : 'Ext.form.Panel',
	frame : true,
	flex : 1,
	layout : {
		type : 'table',
		columns : 3
	},
	defaults : {
		xtype : 'displayfield',
		margin : '5 5 5 5',
		allowBlank : true,
		labelWidth : 125,
		width : 300
	},
	items : [ {
		name : 'deliverbillNo',
		fieldLabel : '派送单编号'
	}, {
		name : 'vehicleNo',
		fieldLabel : '车牌号'
	}, {
		name : 'deliveryDepartment',
		fieldLabel : '派送部'
	}, {
		name : 'driverName',
		fieldLabel : '司机姓名'
	}, {
		name : 'driverTel',
		fieldLabel : '司机电话号码'
	}, {
		name : 'motorcade',
		fieldLabel : '车队'
	} ],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
});

Ext.define('Foss.predeliver.deliverbill.PrintGridPanel', {
	extend : 'Ext.grid.Panel',
	columnWidth : 1,
	height : 300,
	autoScroll : false,
	sortableColumns : false,
	enableColumnHide : false,
	enableColumnMove : false,
	autoScroll : false,
	columns : [
			{
				text : '运单号',
				dataIndex : 'waybillNo',
				width : 80
			},
			{
				text : '货物名称',
				dataIndex : 'goodsName',
				flex : .5
			},
			{
				text : '包装',
				dataIndex : 'goodsPackage',
				flex : .5
			},
			{
				text : '签收单',
				dataIndex : 'singleSignStatus',
				width : 50
			},
			{
				text : '重量/体积/件数',
				dataIndex : 'goodsInfo',
				flex : 1.5
			},
			{
				text : '到付金额',
				dataIndex : 'payAmount',
				width : 60
			},
			{
				text : '运输性质',
				dataIndex : 'transportType',
				width : 80,
				setValue : function(value) {
					this.setRawValue(Foss.pkp.ProductData.rendererSubmitToDisplay(value));
				}
			},
			{
				text : '行政区域', //DMANA-4290
				width : 80,
				dataIndex : 'districtName'
			}, {
				text : '货物尺寸',
				dataIndex : 'dimension',
				flex : 1.5,
				xtype : 'ellipsiscolumn'
			}, {
				text : '收货人/联系方式/收货人地址',
				dataIndex : 'consigneeInfo',
				flex : 1.5,
				xtype : 'ellipsiscolumn'
			}, {
				text : '送货要求',
				dataIndex : 'deliverRequire',
				flex : 1.5,
				xtype : 'ellipsiscolumn'
			} ],
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
		me.store = Ext.create('Foss.predeliver.deliverbill.PrintDeliverStore');
		// me.bbar = me.getPagingToolbar();
		me.bbar = Ext.create('Deppon.StandardPaging', {
			store : me.store,
  				plugins: 'pagesizeplugin',
				displayInfo: true
		});
		me.callParent(cfg);
	}
})

Ext.define('Foss.predeliver.deliverbill.PrintBottomForm', {
	extend : 'Ext.form.Panel',
	frame : true,
	flex : 1,
	layout : {
		type : 'table',
		columns : 3
	},
	defaults : {
		xtype : 'displayfield',
		margin : '5 5 5 5',
		allowBlank : true,
		labelWidth : 125,
		width : 300
	},
	items : [ {
		name : 'weightTotal',
		fieldLabel : '总重量'
	}, {
		name : 'volumeTotal',
		fieldLabel : '总体积',
		colspan : 2
	}, {
		name : 'waybillQtyTotal',
		fieldLabel : '总票数'
	}, {
		name : 'goodsQtyTotal',
		fieldLabel : '总件数',
		colspan : 2
	}, {
		name : 'payAmountTotal',
		fieldLabel : '总到付金额'
	}, {
		name : 'loadingRate',
		fieldLabel : '装载率(重量/体积)',
		colspan : 2
	} ],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
});

Ext.define('Foss.predeliver.deliverbill.PrintDeliverStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.predeliver.deliverbill.PrintDeliverbillModel',
	pageSize : 10,
	proxy : {
		type : 'ajax',
		actionMethods : 'POST',
		// url : '../predeliver/queryDeliverbillDetailList.action',
		url : predeliver.realPath('queryDeliverbillDetailList.action'),
		reader : {
			type : 'json',
			root : 'deliverbillVo.deliverbillDetailList',
			totalProperty : 'totalCount'
		}
	},
	deliverbillId : '',
	setDeliverbillId : function(deliverbillId) {
		this.deliverbillId = deliverbillId;
	},
	getDeliverbillId : function() {
		return this.deliverbillId;
	},
	listeners : {
		beforeload : function(store, operation, eOpts) {
			Ext.apply(operation, {
				params : {
					// 'deliverbillVo.deliverbillDto.id' :
					// predeliver.deliverbill.id
					'deliverbillVo.deliverbillDto.id' : store
							.getDeliverbillId()
				}
			});
		}
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
})

/** -----------------------------------------打印页面信息----------------------------------------- */
Ext.define('Foss.predeliver.deliverbill.PrintDeliverbillWindow', {
	extend : 'Ext.window.Window',
	width : 1000,
	modal : true,
	closeAction : 'hide',
	title : '预派送单据',
	deliverPrintHeadInfo : null,
	layout : 'auto',
	getDeliverPrintHeadInfo : function() {
		if (this.deliverPrintHeadInfo == null) {
			this.deliverPrintHeadInfo = Ext
					.create('Foss.predeliver.deliverbill.PrintHeadForm');
		}
		return this.deliverPrintHeadInfo;
	},
	deliverPrintGrid : null,
	getDeliverPrintGrid : function() {
		if (this.deliverPrintGrid == null) {
			this.deliverPrintGrid = Ext
					.create('Foss.predeliver.deliverbill.PrintGridPanel');
		}
		return this.deliverPrintGrid;
	},
	deliverPrintBottomInfo : null,
	getDeliverPrintBottomInfo : function() {
		if (this.deliverPrintBottomInfo == null) {
			this.deliverPrintBottomInfo = Ext
					.create('Foss.predeliver.deliverbill.PrintBottomForm');
		}
		return this.deliverPrintBottomInfo;
	},
	deliverbillId : '',
	setDeliverbillId : function(deliverbillId) {
		this.deliverbillId = deliverbillId;
	},
	getDeliverbillId : function() {
		return this.deliverbillId;
	},
	// bindData : function(record, cellIndex, rowIndex) {
	// var me = this;
	// var params = {
	// 'deliverbillVo.deliverbillDto.id' : record.get('id')
	// };
	// predeliver.deliverbill.id = record.get('id');
	// 获取打印的预排送单基本信息
	// Ext.Ajax.request({
	// url : '../predeliver/queryDeliverbill.action',
	// params :params,
	// success : function(response) {
	// var result = Ext.decode(response.responseText);
	// var model = Ext.ModelManager.create(
	// result.deliverbillVo.deliverbill,
	// 'Foss.predeliver.deliverbill.PrintModel');
	// me.getDeliverPrintHeadInfo().loadRecord(model);
	// me.getDeliverPrintBottomInfo().loadRecord(model);
	// }
	// });
	// 获取打印的预排送单明细信息
	// Ext.Ajax.request({
	// url : '../predeliver/queryDeliverbillDetailList.action',
	// params :params,
	// success : function(response) {
	// var result = Ext.decode(response.responseText);
	// me.getDeliverPrintGrid().store.model =
	// 'Foss.predeliver.deliverbill.DeliverbillModel';
	// me.getDeliverPrintGrid().store.remoteSort = 'false';
	// me.getDeliverPrintGrid().store.loadData(result.deliverbillVo.deliverbillDetailList);
	// }
	// })
	// },
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.items = [
				me.getDeliverPrintHeadInfo(),
				me.getDeliverPrintGrid(),
				me.getDeliverPrintBottomInfo(),
				{
					border : false,
					xtype : 'container',
					layout : 'column',
					defaults : {
						margin : '5 0 5 0'
					},
					items : [
							{
								border : false,
								columnWidth : .3,
								items : [{
									xtype : 'checkbox',
									boxLabel : '通知司机',
									name: 'isNoticeDriver',
									margin: '5px 0 0 15px',
									inputValue : 'Y',
									uncheckedValue : 'N',
									checked : false,
									hidden : true
								}]
							},
							{
								xtype : 'button',
								columnWidth : .15,
								margin : '5 0 0 0',
								text : '打印',
								plugins : Ext.create(
										'Deppon.ux.ButtonLimitingPlugin', {
											seconds : 3
										}),
								handler : function(butto) {
									do_printpreview('deliverbill', {
										// 'deliverbillId' :
										// predeliver.deliverbill.id
										'deliverbillId' : config.deliverbillId
									}, ContextPath.PKP_DELIVER);
									//如果短信通知司机勾选，通知司机
									if (butto.up('window').down('checkbox[name="isNoticeDriver"]').checked) {
										Ext.Ajax.request({
											url : predeliver.realPath('isSendSMSToDriver.action'),
											params : {
												'deliverbillVo.deliverbillNewDto.id' : config.deliverbillId
											},
											success : function(response) {

												//Ext.ux.Toast.msg(predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.tip'),result.message);
											},
											error : function(response) {
												//var result = Ext.decode(response.responseText);
												//Ext.ux.Toast.msg(predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.tip'),result.message,'error', 4000);
											},
											exception : function(response) {
												//var result = Ext.decode(response.responseText);
												//Ext.ux.Toast.msg(predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.tip'),result.message,'error', 4000);
											}
										})
									}
									
								}
							},{
								border : false,
								columnWidth : .15
							} ,{
								xtype : 'button',
								columnWidth : .15,
								margin : '5 0 0 0',
								text : '关闭',
								handler : function() {
									this.up("window").hide();
								}
							}, {
								border : false,
								columnWidth : .3
							} ]
				} ];
		me.callParent([ cfg ]);
	}
})
