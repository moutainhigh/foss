/***
 * @author ibm-wangfei
 * page:客户通知管理
 */
 
 predeliver.EXCEPTIONREASON = 'PKP_NOTICE_EXCEPTION_REASON';  //异常原因词条
 predeliver.INVOICETYPE = 'PKP_RECEIPT_INVOICE_TYPE'; //收货发票类型
 
predeliver.notifyCustomer.getInStockTimeStart = function(date, day) {	
	var d, s, t, t2;	
	var MinMilli = 1000 * 60;	
	var HrMilli = MinMilli * 60;	
	var DyMilli = HrMilli * 24;	
	t = Date.parse(date);	
	t2 =  new Date(t+day*DyMilli);	
	t2.setHours(0);	
	t2.setMinutes(0);	
	t2.setSeconds(0);	
	t2.setMilliseconds(0);		
	return t2;	
};	
	
predeliver.notifyCustomer.getInStockTimeEnd = function(date, day) {	
	var d, s, t, t2;	
	var MinMilli = 1000 * 60;	
	var HrMilli = MinMilli * 60;	
	var DyMilli = HrMilli * 24;	
	t = Date.parse(date);	
	t2 =  new Date(t+day*DyMilli);	
	t2.setHours(23);	
	t2.setMinutes(59);	
	t2.setSeconds(59);	
	t2.setMilliseconds(0);		
	return t2;	
};	

predeliver.notifyCustomer.compareDate = function(date1, date2) {	
	var _date1 = predeliver.notifyCustomer.getInStockTimeStart(date1,0),
	_date2 = predeliver.notifyCustomer.getInStockTimeStart(date2,0);
	var diff = _date1.getTime() - _date2.getTime();
	if(diff == 0){
		return 0
	} else if(diff < 0){
		return -1;
	} else {
		return 1;
	}
};

 predeliver.NOTIFY_TYPE = 'PKP_NOTIFY_CUSTOMER_TYPE'; // 通知方式词条
 predeliver.NOTIFY_STATUS = 'PKP_NOTIFY_CUSTOMER_STATUS'; // 通知结果词条
 predeliver.PICKUPGOODSHIGHWAYS = 'PICKUPGOODSHIGHWAYS'; // 派送方式词条
 predeliver.PICKUPGOODSSPECIALDELIVERYTYPE = 'PICKUPGOODSSPECIALDELIVERYTYPE';//特殊增值服务对应的提货方式词条
 predeliver.PAYMENTMODE = 'SETTLEMENT__PAYMENT_TYPE'; // 付款方式词条
 predeliver.CLEARING_TYPE = 'CLEARING_TYPE'; // 结算方式词条
 predeliver.FAILURE = 'FAILURE'; // 通知状态-失败
 predeliver.SUCCESS = 'SUCCESS'; // 通知状态-成功
 predeliver.ARRIVED = 'ARRIVED'; // 车辆到达
 predeliver.UNLOADED = 'UNLOADED'; // 已卸车
 predeliver.FC = 'FC'; // 付款方式-到付
 predeliver.CLEARING_TYPE_MONTH = 'MONTH_END'; // 客户付款方式-月结
 predeliver.CLEARING_TYPE_HALF_MONTH = 'HALF_MONTH'; // 付款方式-半月结
 predeliver.SELECT_TYPE = ''; // 查询方式
 predeliver.PAYMENT_TYPE__ONLINE = 'OL';  // 网上支付
 predeliver.PCC_PROV = '1';  // P-C-C控件定义省
 predeliver.PCC_CITY = '2';  // P-C-C控件定义市
 predeliver.PCC_DIST = '3';  // P-C-C控件定义区

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

var provCode = FossUserContext. getCurrentDept().provCode;
var cityCode = FossUserContext. getCurrentDept().cityCode;
 
// 根据免费库存天数，获取在库天数的store
predeliver.notifyCustomer.getStorageDayStore = function() {
	var data = new Array();
	if (predeliver.notifyCustomer.warehouseFreeSafeDataNum > 0) {
		data.unshift({'valueCode': '-' + predeliver.notifyCustomer.warehouseFreeSafeDataNum, 'valueName': predeliver.notifyCustomer.warehouseFreeSafeDataNum + predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.day.over')});
	}
	for (var len = predeliver.notifyCustomer.warehouseFreeSafeDataNum; len > 0; len --) {
		data.unshift({'valueCode': len, 'valueName': len + predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.day')});
	}
	data.unshift({'valueCode': '', 'valueName': predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.complete')});
	var json={
		fields:['valueCode','valueName'],
	    data : data
	};
	return Ext.create('Ext.data.Store',json);
}

var toPayAmountHidden;

Ext.define('predeliver.notifyCustomer.tipPanel', {
	extend: 'Ext.panel.Panel',
	cls: 'autoHeight',
	bodyCls: 'autoHeight',
	//回调方法
	bindData : function(value){
		this.update(value);
	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});
Ext.define('Foss.predeliver.model.NoticeCustomer', {
	extend : 'Ext.data.Model',
	fields : [
		{name : 'noticeResult' , type: 'string'}, /** 通知状态 */
		{name : 'waybillNo'}, /** 运单号 */
		{name : 'receiveCustomerContact'}, /** 收货人姓名 */
		/**{name: 'receiveCustomerCode'}, 客户名称*/
		{name: 'receiveCustomerName'}, /**客户代码*/
		{name : 'receiveCustomerPhone'}, /** 收货人电话 */
		{name : 'receiveBigCustomer'}, /** 收货人大客户标示 */
		{name : 'receiveCustomerMobilephone'}, /** 收货人手机 */
		{name : 'receiveMethod',
			convert:function(value) {
				var v = FossDataDictionary.rendererSubmitToDisplay(value, predeliver.PICKUPGOODSHIGHWAYS);
				if(Ext.isEmpty(v) || value == v){
					v = FossDataDictionary.rendererSubmitToDisplay(value, predeliver.PICKUPGOODSSPECIALDELIVERYTYPE);
				}
				return v;
			}
		}, /** 派送方式 */
		{name : 'receiveCustomerAddress'}, /** 送货地址 */
		{name : 'inStockTime', convert:dateConvert}, /** 入库时间 */
		{name : 'arriveTime', convert:dateConvert}, /** 到达时间 */
		{name : 'planArriveTime', convert:dateConvert}, /** 预计到达时间 */
		{name : 'goodsQtyTotal', type : 'int'}, /** 开单件数 */
		{name : 'arriveGoodsQty', type : 'int'}, /** 到达件数 */
		{name : 'handoverGoodsQty', type : 'int'}, /** 交接件数 */
		{name : 'storageDay', type : 'int'}, /** 在库天数 */
		{name : 'handoverNo'}, /** 交接单号 */
		{name : 'deliveryCustomerName'}, /** 发货人 */
		{name : 'deliveryCustomerContact'}, /** 发货人 */
		{name : 'transportationRemark'}, // 储运事项
		{name : 'deliveryCustomerMobilephone'}, //发货客户手机
		{name : 'deliveryCustomerPhone'}, // 发货客户电话
		{name : 'goodsName'}, /** 货物名称 */
		{name : 'insuranceAmount', type : 'float'}, /** 货物价值 */
		{name : 'paidMethod',
			convert:function(value) {
				return FossDataDictionary.rendererSubmitToDisplay(value, predeliver.PAYMENTMODE);
			}
		}, /** 付款方式 */
		{name : 'receiveOrgName'}, /** 始发部门 */
		{name : 'productCode',
			convert:function(value) {
				return Foss.pkp.ProductData.rendererSubmitToDisplay(value);
			}
		}, /** 运输性质 */
		{name : 'stockStatus', type : 'string'}, /** 库存状态 */
		{name : 'stockQty', type : 'int'}, /** 库存件数 */
		{name : 'goodsWeightTotal', type : 'float'}, /** 总量 */
		{name : 'goodsVolumeTotal', type : 'float'}, /** 体积 */
		{name : 'goodsPackage'}, /** 包装 */
		{name : 'goodsSize'}, /** 尺寸 */
		{name : 'transportFee', type : 'float'}, /** 运费 */
		{name : 'deliveryGoodsFee', type : 'float'}, /** 送货费 */
		{name : 'otherFee', type : 'float'}, /** 其他费用 */
		{name : 'insuranceFee', type : 'float'}, /** 保价费 */
		{name : 'codAmount', type : 'float'}, /** 代收费 */
		{name : 'toPayAmount', type : 'float'}, /** 到付费 */
		{name : 'storageCharge', type : 'float'}, /** 存储费 */
		{name : 'billTime', type : 'date'}, /** 出发日期 */
		{name : 'notificationTime', type : 'date', convert:dateConvert}, /** 上次通知日期 */
		{name : 'creditLimit', type : 'float'}, /** 可用信用额度 */
		{name : 'receivingHabits'}, /** 收货习惯 */
		{name : 'selectType'}, /** 查询方式 */
		{name : 'notificationTimeSpace'}, /** 最后通知日期与当前日期的间隔天数 */
		{name : 'receiveOrgName'}, /** 始发部门*/
		{name : 'taskStatus'}, /** 车辆状态*/
		{name : 'customerQulification'}, /** 客户资质*/
		{name : 'creditAmount'}, /** 信用额度*/
		{name : 'receiveCustomerCode'}, /** 发送部门code*/
		{name : 'isBackFlg'}, /** 客户是否有银行账户列表*/
		{name : 'createOrgCode'}, /** 创建部门code*/
		{name: 'isPay',type: 'string'},//是否已付款
		{name : 'isVoiceSuccess'}, // 是否语音通知成功
		{name : 'isSmsSuccess'}, // 是否短信通知成功
		{name : 'vehicleNo'}, // 车牌号字段
		{name : 'vehicleAssembleNo'}, // 车次号
		{name: 'deliverDate',type:'date',
			convert: function(value) {
				if (value != null) {
					var date = new Date(value);
					return Ext.Date.format(date,'Y-m-d H:i:s');
				} else {
					return null;
				}
			}
		},
		{name : 'positionQty'}, // 库位件数
		{name : 'batchStowageNOtHere'}, //是否是分批配载未到齐
		{name : 'pendingType'}, // 运单处理类型
		{name : 'isOrPayStatus'}, // 到付的是否网上支付
		{name : 'isExhibitCargo'},//是否会展货
		{name : 'specialValueAddedService'}
	]
});

Ext.define('Foss.predeliver.model.NoticeInfo', {
	extend : 'Ext.data.Model',
	fields : [
		{name: 'waybillNo',type:'string'},
		{name: 'receiveCustomerContact',type:'string'},
		{name: 'deliverDate',type:'date',
			convert: function(value) {
				if (value != null) {
					var date = new Date(value);
					return Ext.Date.format(date,'Y-m-d H:i:s');
				} else {
					return null;
				}
			}},
		{name: 'customerQulification'},// 客户资质
		{name: 'deliverRequire',type:'string'},
		{name: 'creditLimit',type:'string'},
		{name: 'smallTicketFee',type:'number'},
		{name: 'contactType',type:'string'},
		{name: 'container',type:'string'},
		{name: 'toPayAmount',type:'number'},
		{name: 'isStorageCharge',type:'string'},
		{name: 'arriveGoodsQty',type:'string'},
		{name: 'estimatedPickupTime',type:'string'},
		{name: 'noticeContent',type:'string'},
		{name: 'noticeContentVoice',type:'string'},
		{name: 'noticeResult',type:'string'},
		{name: 'paymentType',type:'string'},
		{name: 'isNeedInvoice',type:'string'},
		{name: 'isSentRequired',type:'string'},
		{name: 'taxNo',type:'string'},
		{name: 'companyName',type:'string'},
		{name: 'tel',type:'string'},
		{name: 'account',type:'string'},
		{name: 'bank',type:'string'},
		{name: 'address',type:'string'}
	]
});
//定义通知客户搜索结果的统计信息模型
Ext.define('Foss.predeliver.Model.SearchResultTotalModel', {
	extend: 'Ext.data.Model',
	fields: [
	    { name: 'totalVotes',type:'string' }, //总票数
		{ name: 'goodsWeightTotals',type:'string' }, //总重量（吨）
		{ name: 'goodsvolumeTotals',type:'string' }, //总体积（方）
		{ name: 'goodsQtyTotals',type:'string' }  //总件数
		]
});
Ext.define('Foss.predeliver.model.WarehouseCharge', {
	extend : 'Ext.data.Model',
	fields : [
		{name : 'waybillNo'}, 
		{name : 'storageDay',type : 'number'}, 
		{name : 'overdueDay',type : 'number'}, 
		{name : 'storageCharge',type : 'number'}, 
		{name : 'exceptionType'}, 
		{name : 'exceptionNotes'}]
});

//创建一个查询枚举store
Ext.define('Foss.predeliver.model.QueryNoticeInfo',{
	extend: 'Ext.data.Model',
	fields: [
		{name: 'code',  type: 'string'},
		{name: 'name',  type: 'string'}
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
// 运单通知历史信息model
Ext.define('Foss.predeliver.model.HistoryPanel', {
	extend : 'Ext.data.Model',
	fields : [
	  {name : 'operateTime', type: 'date',
		  convert: function(value) {
			if (value != null) {
				var date = new Date(value);
				return Ext.Date.format(date,'Y-m-d H:i:s');
			} else {
				return null;
			}
		}},
	  {name : 'consignee'}, 
	  {name : 'mobile'}, 
	  {name : 'deliverType'},
	  {name : 'noticeType', type: 'string'}, /** 通知状态 */
	  {name : 'noticeResult', type: 'string'}, /** 通知状态 */
	  {name : 'operator'},
	  {name : 'noticeContent'},
	  {name : 'deliverDate'},
	  {name : 'deliverRequire'},
	  {name : 'smallTicketFee'},
	  {name : 'noticeContentVoice'},
	  {name : 'exceptionNotes'}
]
});

/**
 * 为对象属性添加前缀
 * @param obj 传入对象
 * @param prevName 前缀名称
 * @returns
 */
function addPrev(obj, prevName){
  if (Ext.isObject(obj)){
    for (var attr in obj){
      var keyName = prevName + '.' + attr;
      obj[keyName] = obj[attr];
      delete obj[attr];
    }
  } 
}

/**
 * 添加收货习惯Window
 */
Ext.define('Foss.predeliver.beforeNotice.AddReceiptHabitWindow',{
	extend : 'Ext.window.Window',
	title : '客户收货习惯',
	modal : true,
	resizable : false,
	items : [{
		xtype : 'form',
		defaults: {
			margin: '5 20 5 10',
			labelWidth: 90
		},
		defaultType: 'textfield',
		layout: {
		    type : 'table',
		    columns : 2
		},
		items : [ {
			fieldLabel: '收货客户名称',//客户
			allowBlank : false,
			readOnly : true,
			name : 'customerName' 
			},{
			fieldLabel : '联系人手机',
			regex : /^\d+$/,
			regexText : '只可以填入数字',
			name : 'customerMobilePhone',
			readOnly : true,
			listeners : {
				blur : function (text) {
					if (text.getValue().length > 0){
						text.nextSibling('textfield[name=customerPhone]').allowBlank= true;
					} else {
						text.nextSibling('textfield[name=customerPhone]').allowBlank= false;
					}
				}
			}
		}, {
			fieldLabel : '联系人电话',
			regex : /^[\d\-]+$/,
			regexText : '只可以填入数字和-',
			readOnly : true,
			name : 'customerPhone',
			listeners : {
				blur : function (text) {
					if (text.getValue().length > 0){
						text.previousSibling('textfield[name=customerMobilePhone]').allowBlank= true;
					} else {
						text.previousSibling('textfield[name=customerMobilePhone]').allowBlank= false;
					}
				}
			}
		}, {
			fieldLabel : '收货联系人',
			allowBlank : false,
			readOnly : true,
			name : 'customerContactName'
		}, {
			fieldLabel : '送货时间段',
			xtype : 'combobox',
			queryMode : 'local',
		    displayField : 'name',
		    valueField : 'name',
		    editable:false,
		    name : 'deliveryTimeInterval',
		    value : '全天',
			store : Ext.create('Ext.data.Store', {
				fields : ['name'],
				data : [ 
				         {name : '全天'},
				         {name : '上午'},
				         {name : '下午'}
				       ]
			})
		}, {
			xtype : 'container',
			layout : 'hbox',
			width : 300,
			items : [ {
		        xtype: 'timefield',
		        name: 'deliveryTimeStart',
		        fieldLabel: '送货时间点',
		        labelWidth : 90,
		        width : 180,
		        editable:true,
		        increment: 30,
		        submitFormat : 'H:i',
		        format : 'H:i',
						listeners : {
							'blur' : function (timefield) {
								var val = timefield.getValue();
								if (val) {
									timefield.nextSibling().allowBlank = false;
								} else if (!val && !timefield.nextSibling().getValue()) {
									timefield.reset();
									timefield.nextSibling().reset();
									timefield.allowBlank = true;
									timefield.nextSibling().allowBlank = true;
								} else {
									timefield.nextSibling().allowBlank = true;
								}
							},
				        	 select : function(combo, records, eOpts) {
				        		 var val = combo.getValue() ;
				        		 if (val) {
				        			 combo.nextSibling().setMinValue(val);
				        		 }
				        	 }
						}
		     }, {
		        xtype: 'timefield',
		        name: 'deliveryTimeOver',
		        fieldLabel: '至',
		        labelWidth : 20,
		        width : 100,
		        editable:true,
		        labelSeparator : '', 
		        increment: 30,
		        submitFormat : 'H:i',
		        format : 'H:i',
						listeners : {
							'blur' : function (timefield) {
								var val = timefield.getValue();
								if (val) {
									timefield.previousSibling().allowBlank = false;
								} else if (!val && !timefield.previousSibling().getValue()) {
									timefield.reset();
									timefield.previousSibling().reset();
									timefield.allowBlank = true;
									timefield.previousSibling().allowBlank = true;
								} else {
									timefield.previousSibling().allowBlank = true;
								}
							},
				        	 select : function(combo, records, eOpts) {
				        		 var val = combo.getValue() ;
				        		 if (val) {
				        			 combo.previousSibling().setMaxValue(val);
				        		 }
				        	 }
						}
		   } ]
		},{
				xtype : 'combobox',
				labelWidth : 90,
				fieldLabel : '发票类型',
				name : 'invoiceType',
				displayField:'valueName',
				valueField:'valueCode',
				triggerAction:'all',
				queryMode:'local',
				editable:false,
				value:'',
				store:FossDataDictionary.getDataDictionaryStore('PKP_RECEIPT_INVOICE_TYPE',null,{
					'valueCode': '',
		            'valueName': '无'
				})	
			},{
				labelWidth: 90,
				fieldLabel : '发票备注',
				xtype : 'textfield',
				width : 280,
				maxLength: 30,
				maxLengthText: '已超出字数最大限制!',
				name : 'invoiceDetail'
			}, {
			colspan: 2,
			xtype : 'textarea',
			width : 580,
			maxLength : 80,
			name: 'receiptHabitRemark',
			fieldLabel : '收货习惯备注'
		}, {
			xtype : 'hidden',name : 'customerCode'
		} ]
	}],
	buttons : [ {
		text : '返回',
		handler : function (btn) {
			var win = btn.up('window');
			win.close();
		}
	},  '->' , {
		text : '保存',
		handler : function (btn) {
			var win = btn.up('window');
			var form = win.down('form');
			if (!form.getForm().isValid()){
				return ;
			}
			var values = form.getForm().getValues();
			if (values.customerPhone == '' && values.customerMobilePhone == '') {
				Ext.ux.Toast.msg("提示信息", "联系人手机和联系人电话至少有一个不为空", 'success', 3000);
				return ;
			}
			//送货时间点限制
			if((!Ext.isEmpty(values.deliveryTimeStart)) &&(!Ext.isEmpty(values.deliveryTimeOver))){
				if(values.deliveryTimeStart>values.deliveryTimeOver){
					 Ext.ux.Toast.msg('提示','最早送货时间点不能大于最晚送货时间点!', 'error', 2000);
					return ;
				}
			}
				//values['customerName'] = form.down('commoncustomerselector').getRawValue();
				addPrev(values,'customerReceiptHabitEntity');
				Ext.Ajax.request({
						url : predeliver.realPath("insertReceiptHabit.action"),
				    params : values,
				    success: function(response, opts) {
				        var obj = Ext.decode(response.responseText);
				        if(obj) {
				        	if(obj.success) {
				        		Ext.ux.Toast.msg("提示信息", "添加成功", 'success', 3000);
				        		win.close();
				        	} else {
				        		Ext.ux.Toast.msg("提示信息", json.message, 'error', 3000);
				        	}
				        }
				    },
				    exception: function(response){
						var json = Ext.decode(response.responseText);
              			Ext.ux.Toast.msg("提示信息", json.message, 'error', 3000);
					}
				});
		}
	} ]
});


/**
 * 历史收货地址model
 */
Ext.define('Foss.predeliver.noticeCustomer.ReceiptAddressModel',{
	extend : 'Ext.data.Model',
	fields : ['customerCode', 'customerContactName', 'customerMobilePhone',
	      	'customerName', 'customerPhone', 'id', 'receiveAddressDetails',
	      	'receiveCityCode', 'receiveDistCode', 'receiveProvCode', 'receiveStreet',
	      	'receiveProvName', 'receiveCityName', 'receiveDistName']
});

/**
 * 历史收货地址store
 */
var receiptAddressStore = Ext.create('Ext.data.Store',{
	model : 'Foss.predeliver.noticeCustomer.ReceiptAddressModel',
	proxy: {
        type : 'ajax',
				url : predeliver.realPath("findReceiptAddress.action"),
        actionMethods:{
            create: "POST", read: "POST", update: "POST", destroy: "POST"
        },
        reader: {
            type: 'json',
            root: 'customerReceiptAddresses'
        }
    }
});

var addressGrid = new Array();
/**
 * 历史收货地址Window
 */
Ext.define('Foss.predeliver.noticeCustomer.ReceiptAddressWindow', {
	extend : 'Ext.window.Window',
	title : '收货人地址历史信息',
	modal : true,
	width : 800,
	resizable : false,
	items : [ {
		xtype : 'grid',
		store : receiptAddressStore,
		hideHeaders : true,
		multiSelect : false,
		columns : [ {
			xtype : 'actioncolumn',
			align: 'center',
			width :60,
			items : [ {
				iconCls: 'deppon_icons_delete',
				tooltip :  '删除',
				handler: function(grid, rowIndex, colIndex) {
					var rec = grid.getStore().getAt(rowIndex);
					var id = "";
					if(rec.data.id) {
						id = rec.data.id;
					}
					Ext.Msg.show({
					     title:'提示信息',
					     msg: '确定要作废该客户收货地址吗？', 
					     buttons: Ext.Msg.OKCANCEL,
					     icon: Ext.Msg.QUESTION,
					     fn : function (btnText) {
					    	 if (btnText == 'ok'){
									 addressGrid = new Array();
					    		 Ext.Ajax.request({
											url : predeliver.realPath("deleteReceiptAddress.action"),
									    params : {
									    	id : id
									    },
									    success: function(response, opts) {
									    	var obj = Ext.decode(response.responseText);
									        if(obj) {
									        	if(obj.success) {
									        		grid.getStore().load();
									        		Ext.ux.Toast.msg('提示信息','删除成功', 'success', 3000);/*删除成功 */
									        	} else {
									        		Ext.ux.Toast.msg('提示信息', obj.message, 'error', 3000);
									        	}
									        }
									    },
									    exception: function(response){
											var json = Ext.decode(response.responseText);
					              			Ext.ux.Toast.msg('提示信息', json.message, 'error', 3000);
										}
									});
					    	 }
					     }
					});
				}
			}]
		}, {
			xtype : 'templatecolumn',
			width :18,
			tpl : '<input type="radio" name="select" id="{id}" />'
		}, {
			xtype : 'gridcolumn',
			flex : 1,
			renderer: function(value, metaData, model) {
						var obj = model.data;
						var str = "";
						str += "客户编码:" + obj.customerCode;
						str += "&nbsp;&nbsp;联系人:" + obj.customerContactName;
						str += "&nbsp;&nbsp;手机:" + obj.customerMobilePhone;
						str += "&nbsp;&nbsp;电话:" + obj.customerPhone;
						str += "&nbsp;&nbsp;地址:" + obj.receiveProvName + obj.receiveCityName + obj.receiveDistName + obj.receiveAddressDetails + (obj.receiveStreet == null ? "": obj.receiveStreet);
						
            if(str.length > 65){
							var strAddress = obj.receiveProvName + obj.receiveCityName + obj.receiveDistName + obj.receiveAddressDetails + (obj.receiveStreet == null ? "": obj.receiveStreet);
                  metaData.tdAttr = 'data-qtip="'+strAddress+'"';
            }
						return str;
      }
		}],
		listeners : {
			'select' : function (rowModel,record,index) {
				Ext.getDom(record.data.id).checked = 'checked';
				addressGrid = record;
			}
		}
	} ],
	buttons : [ {
		text : '返回',
		handler : function (btn) {
			var win = btn.up('window');
			win.down('grid').getStore().removeAll(true);
			win.close();
		}
	},  '->' , {
		text : '确定', 
		handler : function (btn) {
			var record = addressGrid.data;
			if (record) {
				var noticeForm = Ext.getCmp('T_predeliver-notifyCustomerIndex_content').down('#noticeInfoFormPanel_Id').getForm();
				//由历史地址带出的默认的收货省
				Ext.getCmp('actualAddress_ID').setReginValue(record.receiveProvName, record.receiveProvCode, predeliver.PCC_PROV);
				//由历史地址带出的默认的收货市
				Ext.getCmp('actualAddress_ID').setReginValue(record.receiveCityName, record.receiveCityCode, predeliver.PCC_CITY);
				//由历史地址带出的默认的收货县
				Ext.getCmp('actualAddress_ID').setReginValue(record.receiveDistName, record.receiveDistCode, predeliver.PCC_DIST);
				noticeForm.findField('actualStreetn').setValue(record.receiveStreet);
				noticeForm.findField('actualAddressDetail').setValue(record.receiveAddressDetails);
				
				btn.up('window').down('grid').getStore().removeAll(true);
				btn.up('window').close();
			} else {
				addressGrid.length = 0;
				Ext.Msg.alert('提示', '请选择收货人地址信息!');
			}
		}
	} ],
	listeners : {
		'close': function() {
			addressGrid = new Array();
		}
	}
});

Ext.define('Foss.predeliver.store.NoticeCustomer', {
	extend : 'Ext.data.Store',
	model : 'Foss.predeliver.model.NoticeCustomer',
	pageSize : 50,
	proxy : {
		type : 'ajax',
		actionMethods : 'POST',
		// url : '../predeliver/queryStayNotificationBill.action',
		url:predeliver.realPath('queryStayNotificationBill.action'),
		reader : {
			type : 'json',
			root : 'vo.dtoList',
			totalProperty : 'totalCount',
			successProperty : 'success'
		}
	},
	listeners : {
		beforeload : function(store, operation, eOpts) {
			var myForm = predeliver.notifyCustomer.queryform.getForm(), waybillNo = myForm.getValues().waybillNo;
			var vehicleAssembleNo = myForm.getValues().vehicleAssembleNo;
			// 验证运单号输入的行数
			if (!Ext.isEmpty(waybillNo)) {
				var arrayWaybillNo = waybillNo.split('\n');
				if (arrayWaybillNo.length > 50) {
					Ext.ux.Toast.msg(predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.tip'), predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.waybillNo.valitation'), 'error', 3000); // '起止日期相隔不能超过30天！'
					return false;	
				}
				for (var i = 0; i < arrayWaybillNo.length; i++) {
					if (Ext.isEmpty(arrayWaybillNo[i])) {
						Ext.ux.Toast.msg(predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.tip'), predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.waybillNo.valitation'), 'error', 3000); // '起止日期相隔不能超过30天！'
						return false;	
					}
				}
			}
			// 验证车次号输入输入的行数
			if (!Ext.isEmpty(vehicleAssembleNo)) {
				var vehicleAssembleNos = vehicleAssembleNo.split('\n');
				if (vehicleAssembleNos.length > 10) {
					Ext.ux.Toast.msg(predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.tip'), predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.vehicleAssembleNo.valitation'), 'error', 3000); 
					return false;	
				}
				for (var i = 0; i < vehicleAssembleNos.length; i++) {
					if (Ext.isEmpty(vehicleAssembleNos[i])) {
						Ext.ux.Toast.msg(predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.tip'), predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.vehicleAssembleNo.valitation'), 'error', 3000);
						return false;	
					}
				}
			}
			// 入库时间验证
			var inStockTimeFrom = myForm.getValues().inStockTimeFrom, inStockTimeTo = myForm.getValues().inStockTimeTo;	
				arriveTimeFrom = myForm.getValues().arriveTimeFrom, arriveTimeTo = myForm.getValues().arriveTimeTo;	
			if (!Ext.isEmpty(inStockTimeFrom) && !Ext.isEmpty(inStockTimeTo)) {	
				var result = Ext.Date.parse(inStockTimeTo,'Y-m-d H:i:s') - Ext.Date.parse(inStockTimeFrom,'Y-m-d H:i:s');	
				if(result / (24 * 60 * 60 * 1000) >= 30){	
					Ext.ux.Toast.msg(predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.tip'), predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.the.date.range.cannot.be.more.than.30.days'), 'error', 3000); // '起止日期相隔不能超过30天！'
					return false;	
				}	
			}
			// 到达时间验证
			if (!Ext.isEmpty(arriveTimeFrom) && !Ext.isEmpty(arriveTimeTo)) {	
				var result = Ext.Date.parse(arriveTimeTo,'Y-m-d H:i:s') - Ext.Date.parse(arriveTimeFrom,'Y-m-d H:i:s');	
				if(result / (24 * 60 * 60 * 1000) >= 30){	
					Ext.ux.Toast.msg(predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.tip'), predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.the.date.range.cannot.be.more.than.30.days'), 'error', 3000); // '起止日期相隔不能超过30天！'
					return false;	
				}	
			}
			if((Ext.isEmpty(inStockTimeFrom) && (!Ext.isEmpty(inStockTimeTo))) ||(!Ext.isEmpty(inStockTimeFrom) && (Ext.isEmpty(inStockTimeTo)))){
				Ext.ux.Toast.msg(predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.tip'), predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.inStockTimeNull'), 'error', 3000);//入库起止时间不能为空
					return false;
			}else if((Ext.isEmpty(arriveTimeFrom) && (!Ext.isEmpty(arriveTimeTo))) ||(!Ext.isEmpty(arriveTimeFrom) && (Ext.isEmpty(arriveTimeTo)))){
				Ext.ux.Toast.msg(predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.tip'), predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.arriveTimeNull'), 'error', 3000);//车辆实际到达起止时间不能为空
				return false;
			}
			if(!myForm.isValid()){
				return false;
			}
			var handoverNo = myForm.getValues().handoverNo,vehicleAssembleNo = myForm.getValues().vehicleAssembleNo;
			// 未录入运单号、交接单号的情况下必须录入时间
			if (Ext.isEmpty(waybillNo) && Ext.isEmpty(handoverNo) && Ext.isEmpty(vehicleAssembleNo)) {
				if ((Ext.isEmpty(inStockTimeFrom) || Ext.isEmpty(inStockTimeTo))
					&& (Ext.isEmpty(arriveTimeFrom) || Ext.isEmpty(arriveTimeTo))) {
					Ext.ux.Toast.msg(predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.tip'), '入库时间、车辆实际到达时间必须输入一组起止日期', 'error', 3000); // '入库时间、预计到达时间必须输入'
					return false;	
				}
			}
			//执行查询，首先load查询条件，为全局变量，在查询条件的FORM创建时生成	
			var queryParams = predeliver.notifyCustomer.queryform.getValues();
			Ext.apply(operation, {	
				params:{	
					'vo.conditionDto.waybillNo':queryParams.waybillNo,  //运单号	
					'vo.conditionDto.handoverNo':queryParams.handoverNo,// 交接单编号	
					'vo.conditionDto.vehicleAssembleNo':queryParams.vehicleAssembleNo,	//车牌号
					'vo.conditionDto.receiveMethod':queryParams.receiveMethod,		//派送方式
					'vo.conditionDto.position':queryParams.position,		//按库区
					'vo.conditionDto.batchStowage':queryParams.batchStowage, //分批配载
					'vo.conditionDto.productCode':queryParams.productCode, //运输性质
					'vo.conditionDto.noticeResult':queryParams.noticeResult,	//通知结果
					'vo.conditionDto.storageDay':queryParams.storageDay,	//在库天数
					'vo.conditionDto.inStockTimeFrom':queryParams.inStockTimeFrom, 		//入库时间开始
					'vo.conditionDto.inStockTimeTo':queryParams.inStockTimeTo,	//入库时间结束
					'vo.conditionDto.arriveTimeFrom':queryParams.arriveTimeFrom,	//到达时间开始
					'vo.conditionDto.arriveTimeTo':queryParams.arriveTimeTo,		//到达时间结束
					'vo.conditionDto.receiveMethodCon':queryParams.deliverMethod,   //提货方式
					'vo.conditionDto.deliverProv':queryParams.receiveCustomerProv,   //省
					'vo.conditionDto.deliverCity':queryParams.receiveCustomerCity,   //市
					'vo.conditionDto.deliverDistCode':queryParams.receiveCustomerDistCode  //区域
					}	
			});		
		},
		load : function(store, records, successful,eOpts) {
			// 根据后台传入的值动态显示列
			var columns = Ext.getCmp('Foss_predeliver_grid_NoticeCustomerGrid_Id').columns;
			var searchTotalForm = Ext.getCmp('Foss_notifyCustomer_TotalForm_Id'), 
				transportInfoForm = Ext.getCmp('Foss.predeliver.noticeCustomer.TransportInfoFormPanel_id'),
				noticeInfoForm   = Ext.getCmp('Foss.predeliver.noticeCustomer.NoticeInfoFormPanel_Id'),
				historyPanelGrid =Ext.getCmp('Foss.predeliver.noticeCustomer.HistoryPanelGrid_id');
			searchTotalForm.getForm().reset();
			transportInfoForm.getForm().reset();
			noticeInfoForm.getForm().reset();
			historyPanelGrid.store.removeAll();
			if (records.length == 0) {
				return;	
			}
			var data = store.getProxy().getReader().rawData;
			if(data.success){
				if(data.vo.conditionDto != null){
					//得到统计信息
					var searchResultTotalModel = Ext.ModelManager.create(data.vo.conditionDto,
					'Foss.predeliver.Model.SearchResultTotalModel');
					searchTotalForm.loadRecord(searchResultTotalModel);
				}
			}
			for (var len = 0; len < columns.length; len ++) {
				if (records[0].data.selectType == '0') {
				// 按库存查询
				if (columns[len].dataIndex == 'inStockTime' 
				    	|| columns[len].dataIndex == 'stockQty'
				    	|| columns[len].dataIndex == 'positionQty'
				    	|| columns[len].dataIndex == 'storageDay') {
				    // 入库时间、库存件数、库位件数、在库天数
					columns[len].setVisible(true);  
				}
				if (columns[len].dataIndex == 'arriveTime' 
				    	|| columns[len].dataIndex == 'planArriveTime'
				    	|| columns[len].dataIndex == 'handoverGoodsQty'
				    	|| columns[len].dataIndex == 'handoverNo'
				    	|| columns[len].dataIndex == 'vehicleNo'
				    	|| columns[len].dataIndex == 'vehicleAssembleNo'
				    	) {
				    // 到达时间、预计到达时间、交接单件数、交接单号、车牌号、车次号
					columns[len].setVisible(false);  
				}
				if (columns[len].dataIndex == 'selectType' 
				    	|| columns[len].dataIndex == 'notificationTimeSpace'
				    	|| columns[len].dataIndex == 'taskStatus'
				    	) {
				    // 隐藏列
					columns[len].setVisible(false);  
				}
			} else {
				// 按交接单查询
				if (columns[len].dataIndex == 'inStockTime' 
				    	|| columns[len].dataIndex == 'stockQty'
				    	|| columns[len].dataIndex == 'positionQty'
				    	|| columns[len].dataIndex == 'storageDay') {
				    // 入库时间、库存件数、库位件数、在库天数
					columns[len].setVisible(false);  
				}
				if (columns[len].dataIndex == 'arriveTime' 
				    	|| columns[len].dataIndex == 'planArriveTime'
				    	|| columns[len].dataIndex == 'handoverGoodsQty'
				    	|| columns[len].dataIndex == 'handoverNo'
				    	|| columns[len].dataIndex == 'vehicleNo'
				    	|| columns[len].dataIndex == 'vehicleAssembleNo'
				    	) {
				    // 到达时间、预计到达时间、到达件数、交接单件数、交接单号、车牌号、车次号
					columns[len].setVisible(true);  
				}
				if (columns[len].dataIndex == 'selectType' 
				    	|| columns[len].dataIndex == 'notificationTimeSpace'
				    	|| columns[len].dataIndex == 'taskStatus'
				    	) {
				    // 隐藏列
					columns[len].setVisible(false);  
				}
			}
		}
		predeliver.SELECT_TYPE = records[0].data.selectType;
		var grid=Ext.getCmp('Foss_predeliver_grid_NoticeCustomerGrid_Id');
		if(store.data.length>0)
		{
			grid.getSelectionModel().select(0);
		}
	}
}});

//派送方式集合
Ext.define('Foss.predeliver.store.ReceiveMethodStore',{
	extend: 'Ext.data.Store',
	fields: [
		{name: 'valueCode',  type: 'string'},
		{name: 'valueName',  type: 'string'}
	],
	data: {
		'items':[
			{'valueCode':'','valueName':predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.complete')},// '全部'
			{'valueCode':'DELIVER','valueName':predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.deliver')},// '送货'
			{'valueCode':'PICKUP','valueName':predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.pickup')},//'自提'
			{'valueCode':'EQUIP','valueName':predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.equip')}//'家装送装'
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

Ext.define('Foss.predeliver.form.NoticeCustomerSearch', {
	extend : 'Ext.form.Panel',
	title : predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.query'),
	collapsible : true,
	animCollapse : true,
	defaults : {
		margin : '0 2 2 0',
		labelWidth : 60
	},
	cls : 'autoHeight',
	bodyCls : 'autoHeight',
	defaultType : 'textfield',
	layout : 'column',
	frame : true,
	//id : 'Foss_predeliver_form_noticeCustomerSearch_Id',
	items : [{
		name : 'waybillNo',
		xtype : 'textarea',
		fieldLabel : predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.waybillNo'),
		columnWidth : .2,
		emptyText : predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.waybillNo.valitation'),
		regex : /^([0-9]{8,10}\n?)+$/i,
		regexText : predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.waybillNo.valitation')
	},{
		name : 'vehicleAssembleNo',
		xtype : 'textarea',
		fieldLabel : predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.vehicleAssembleNo'),
		columnWidth : .2,
		height: 140,
		emptyText : predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.vehicleAssembleNo.valitation')
	}, {
		name : 'handoverNo',
		fieldLabel : predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.handoverNo'),
		columnWidth : .2
	},{
		xtype:'combo',
		displayField:'valueName',
		valueField:'valueCode',
		queryMode:'local',
		triggerAction:'all',
		value : '',
		editable:false,
		name : 'receiveMethod',
		fieldLabel : predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.receiveMethod'),
		columnWidth : .2,
		store : Ext.create('Foss.predeliver.store.ReceiveMethodStore')
	},
	{//提货方式
		xtype:'dynamicmulticomboselector',
		fieldLabel: '提货方式',
		labelWidth:60,
		name: 'deliverMethod',
		columnWidth : .2,
		delimiter: ' ',
		store:FossDataDictionary.getDataDictionaryStore('PICKUPGOODSHIGHWAYS'),// 从外面传入
		displayField:'valueName',// 显示名称
		valueField:'valueCode',// 值
		queryMode:'local',
		triggerAction:'all',
		showContent:'{valueName}',// 显示表格列 {valueName}&nbsp;&nbsp;{valueCode}
		isPaging:false,// 分页
		isEnterQuery:true// 回车查询
	},
	{
		xtype : 'checkbox' ,
		name : 'position',
		boxLabel : predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.isPosition'),
		columnWidth : .2, 
		margin : '0 2 0 20',
		inputValue : 'Y'
		
	},   
	{
		xtype:'checkbox',
		name :'batchStowage',
		boxLabel:predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.isBatchStowage'),
		columnWidth:.2,
		margin:'0 2 0 20',
		inputValue:'Y',
		listeners:{
			'change':function(){
					var myForm = this.up('form').getForm();
					myForm.findField('inStockTimeFrom').setValue('');
					myForm.findField('inStockTimeTo').setValue('');
					myForm.findField('arriveTimeFrom').setValue(Ext.Date.format(predeliver.notifyCustomer.getInStockTimeStart(new Date(),0),'Y-m-d H:i:s'));
					myForm.findField('arriveTimeTo').setValue(Ext.Date.format(predeliver.notifyCustomer.getInStockTimeEnd(new Date(),0),'Y-m-d H:i:s'));
			}
		}
	},{//运输性质
		xtype:'dynamicmulticomboselector',
		displayField:'name',
		valueField:'code',
		queryMode:'remote',
		triggerAction:'all',
		name : 'productCode',
		fieldLabel : predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.productCode'),
		columnWidth : .2,
		store : Ext.create('Ext.data.Store', {
			fields : ['code','name'],
			proxy : {
				type : 'ajax',
				url :predeliver.realPath('queryThreeLevelProductAll.action'),
				reader: {
             type: 'json',
             root: 'vo.productEntitys'
         }
			}
		}),
		showContent:'{name}',// 显示表格列 {valueName}&nbsp;&nbsp;{valueCode}
		isPaging:false,// 分页
		isEnterQuery:true// 回车查询
	}, {
		xtype : 'rangeDateField',
		fieldLabel : predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.inStockTime'),
		//labelWidth: 80,
		margin:'0 0 0 0',
		fieldId : 'Foss_predeliver_notifyCustomer_inStockTime_Id',
		dateType : 'datetimefield_date97',
		fromName : 'inStockTimeFrom',
		toName : 'inStockTimeTo',
		fromValue: Ext.Date.format(predeliver.notifyCustomer.getInStockTimeStart(new Date(),0),'Y-m-d H:i:s'),	
		toValue: Ext.Date.format(predeliver.notifyCustomer.getInStockTimeEnd(new Date(),0),'Y-m-d H:i:s'),
		editable:false,
		columnWidth : .4
	}, {
		xtype:'combo',
		displayField:'valueName',
		valueField:'valueCode',
		queryMode:'local',
		triggerAction:'all',
		value : '',
		editable:false,
		name : 'noticeResult',
		fieldLabel : predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.noticeResultInfo'),
		columnWidth : .2,
		store : FossDataDictionary.getDataDictionaryStore(predeliver.NOTIFY_STATUS, null, {
			'valueCode': '',
            'valueName': predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.complete')
		})
	}, {
		xtype : 'rangeDateField',
		fieldLabel : predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.arriveTime'),
		//labelWidth:110,
		margin:'0 0 0 0',
		labelWidth: 110,
		height: 24,
		fieldId : 'Foss_predeliver_notifyCustomer_arriveTime_Id',
		dateType : 'datetimefield_date97',
		fromName : 'arriveTimeFrom',
		toName : 'arriveTimeTo',
		editable:false,
		columnWidth : .4
	},{
		xtype:'combo',
		displayField:'valueName',
		valueField:'valueCode',
		queryMode:'local',
		triggerAction:'all',
		value : '',
		editable:false,
		name : 'storageDay',
		fieldLabel : predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.storageDay'),
		columnWidth : .2,
		store : predeliver.notifyCustomer.getStorageDayStore()
	}, 
	{
		fieldLabel : predeliver.notifyCustomer.i18n('pkp.predeliver.editDeliverbillIndex.prov'),//省
		id : 'receiveCustomer_Prov_Noti',
		name : 'receiveCustomerProv',
		xtype : 'commonreginbyconditionselector',
		degree: 'DISTRICT_PROVINCE',
		margin:'0 0 0 0',
		height:24,
		labelWidth:25,
		columnWidth : .2,
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
		fieldLabel : predeliver.notifyCustomer.i18n('pkp.predeliver.editDeliverbillIndex.city'),//市
		id : 'receiveCustomer_City_Noti',
		name : 'receiveCustomerCity',
		xtype : 'commonreginbyconditionselector',
		degree: 'CITY',
		columnWidth : .2,
		height:24,
		labelWidth: 25,
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
					//receiveCustomerDistCode.expand();
				}
				else
				{
					receiveCustomerDistCode.setValue('');
				}
			}
		}
	},
	{
		id : 'receiveCustomerDist_Code_Noti',
		name : 'receiveCustomerDistCode',
		//value: FossUserContext.getCurrentDept().countyCode,
		fieldLabel : predeliver.notifyCustomer.i18n('pkp.predeliver.editDeliverbillIndex.administrativeRegions'),//行政区域
		xtype : 'commonareaselector',
		columnWidth : .2
		//height:24
		/*,
		listeners : {
			'beforerender':function(receiveCustomerDistCode){
				//receiveCustomerDistCode.getStore().load(FossUserContext.getCurrentDept().countyCode);
				receiveCustomerDistCode.getStore().load({
					scope: this,
				    callback: function(records, operation, success) {
				    	receiveCustomerDistCode.setValue(FossUserContext.getCurrentDept().countyCode);
				    	//receiveCustomerDistCode.collapse();
				    }
				}); 
				return true;
			}
		} */
	}, 
	{
		border: 1,
		xtype:'container',
		columnWidth:1,
		defaultType:'button',
		layout:'column',
		items:[{
			text : predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.resetButton'),
			columnWidth:.08,
			handler : function() {
				var myForm = this.up('form').getForm();
				myForm.reset();
				myForm.findField('inStockTimeFrom').setValue(Ext.Date.format(predeliver.notifyCustomer.getInStockTimeStart(new Date(),0),'Y-m-d H:i:s'));
				myForm.findField('inStockTimeTo').setValue(Ext.Date.format(predeliver.notifyCustomer.getInStockTimeEnd(new Date(),0),'Y-m-d H:i:s'));
				Ext.getCmp('receiveCustomer_Prov_Noti').setReginValue(predeliver.provName, provCode);	
				Ext.getCmp('receiveCustomer_City_Noti').setReginValue(predeliver.cityName, cityCode);
			}
		}, {
			xtype: 'container',
			border : false,
			columnWidth:.84,
			html: '&nbsp;'
		}, {
			text : predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.queryButton'),
			disabled:!predeliver.notifyCustomer.isPermission('notifycustomerindex/notifycustomerindexquerybutton'),
			hidden:!predeliver.notifyCustomer.isPermission('notifycustomerindex/notifycustomerindexquerybutton'),
			columnWidth:.08,
			cls : 'yellow_button',
			plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin', {
				seconds: 3
			}),
			handler : function() {
				Ext.getCmp('Foss_predeliver_grid_NoticeCustomerGrid_Id').getPagingToolbar().moveFirst();
			}
		}]
	}],
	listeners : { 
		'render' : function(_this, eOpts){
			_this.getForm().findField('waybillNo').setHeight(140);
		}
	}
});
//通知明细表格
Ext.define('Foss.predeliver.grid.NoticeCustomerGrid',{
	extend:'Ext.grid.Panel',
	title : predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.selectResult'),
	cls : 'autoHeight',
	bodyCls : 'autoHeight',
	frame : true,
	emptyText: predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.selectResultEmpty'),
//	id : 'Foss_predeliver_grid_NoticeCustomerGrid_Id',
	// 增加滚动条
	autoScroll : false,
	collapsible : true,
	//动画收缩
	animCollapse: true,
	//高
	height: 865,
	store: null, 
	selModel : Ext.create('Ext.selection.CheckboxModel',{
		checkOnly: false //限制只有点击checkBox后才能选中行
	}),
	viewConfig : {
		// 单元格可复制
		enableTextSelection: true,
		//显示重复样式，不用隔行显示
		stripeRows : false,
		getRowClass : function(record, rowIndex, rp, ds) {
			// 取消未通知的颜色显示 2013-2-28 15:59:57
			var notificationStatus = record.get('noticeResult');
//			if (notificationStatus == '') {
//				//未通知-白色
//				return 'predeliver_notice_customer_row_white';
//			}
			var selectType = record.get('selectType'),goodsQtyTotal = record.get('goodsQtyTotal'),
				stockQty = record.get('stockQty');
			if (selectType == '0' && goodsQtyTotal != stockQty) {
				// 开单库存件数不一致 - 粉色
				return 'predeliver_notice_customer_row_pink';
			}
			var notificationTimeSpace = record.get('notificationTimeSpace');
			if (notificationStatus ==  predeliver.SUCCESS && stockQty > 0) {
				// 通知成功并且库存数量大于0
				if (notificationTimeSpace != null && notificationTimeSpace > predeliver.notifyCustomer.warehouseFreeSafeDataNum) {
					// 通知时间后X天且库存中仍有该票运单，则表明是通知X天未提货 - 紫色
					return 'predeliver_notice_customer_row_purole';
				}
			}
		}
	},
	tbar : [{
		xtype : 'button',
		text : predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.batchNotify'),
		disabled:!predeliver.notifyCustomer.isPermission('notifycustomerindex/notifycustomerindexlistbutton'),
		hidden:!predeliver.notifyCustomer.isPermission('notifycustomerindex/notifycustomerindexlistbutton'),
		plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin', {
			seconds: 3
		}),
		width:68,
		handler : function() {
			var waybillNos = '', array = new Array(), notificationEntity = '',taskStatus = '',selectType = '',
				records = Ext.getCmp('Foss_predeliver_grid_NoticeCustomerGrid_Id').getSelectionModel().getSelection(),
				hasNoMobile = false,isDeliver = false, isNoticing = false, isSuccess = false,isStorageCharge=false, isTodaySuccess = false, isBatchStowageNOtHere=false;
			for (var i = 0; i < records.length; i++) {
				// 批量通知时，没有手机号码，退出本次循环
				if (Ext.isEmpty(records[i].get("receiveCustomerMobilephone"))) {
					hasNoMobile = true;
					continue;
				}
				if(records[i].get('noticeResult') == 'VOICE_NOTICING' || records[i].get('noticeResult') == 'SMS_NOTICING'){
					isNoticing = true;
					continue;
				}
				// 当天通知过，不再通知
				if(records[i].get('isVoiceSuccess') == 'Y' && records[i].get('isSmsSuccess') == 'Y'){
					isSuccess = true;
					continue;
				}
				// 通知成功且不是电话通知，今天不能再次通知
				if(records[i].get('isSmsSuccess') == 'Y' 
					&& predeliver.notifyCustomer.compareDate(records[i].get('notificationTime'),new Date()) == 0){
					isTodaySuccess = true;
					continue ;
				}
				//过滤掉分批配载未到齐的运单，不进行通知
				if(records[i].get('batchStowageNOtHere') == 'Y'){
					isBatchStowageNOtHere = true;
					continue;
				}
				//过滤派送方式为送货的运单，不进行通知--306548
				if(records[i].get('receiveMethod') == '送货进仓'||records[i].get('receiveMethod') == '送货(不含上楼)'||records[i].get('receiveMethod') == '免费送货'||records[i].get('receiveMethod') == '送货上楼'||records[i].get('receiveMethod') == '大件上楼'||records[i].get('receiveMethod') == '内部带货送货'){
					isDeliver = true;
					continue;
				}
				waybillNos += records[i].get("waybillNo") + ",";
				selectType = records[i].get('selectType');
				if (selectType == '0') {
					// 按库存方式查询时，通知的数量为库存数量
					notificationEntity = {'waybillNo' : records[i].data.waybillNo, 'arriveGoodsQty' : records[i].data.stockQty};
				} else {
					// 按交接单、车次、预计到达时间查询时，通知的数量为交接单数量
					notificationEntity = {'waybillNo' : records[i].data.waybillNo, 'arriveGoodsQty' : records[i].data.handoverGoodsQty};
				}
				array.push(notificationEntity);
				//批量通知时，如果选择的运单中包含有派送方式为自提的运单，则是否征收保管费默认选择为是
				if(records[i].get('receiveMethod') == '自提'){   
					isStorageCharge = 'Y';
	          	}
				if (selectType == '0') {
					// 对按照库存查询不执行车辆状态验证
					continue;
				}
				
				taskStatus = records[i].get("taskStatus");
				if (!(taskStatus ==  predeliver.ARRIVED || taskStatus == predeliver.UNLOADED)) {
					// 如果选择的运单车辆状态不是已到达或已卸车，不能进行批量通知
					Ext.ux.Toast.msg(predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.tip')/*提示*/, predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.vehicleNotArriveMessage'), 'error', 1000);//'您选择的运单，车辆还没有到达，不能进行语音短信通知，请重新选择。'
					return;
				}
			}
			if (waybillNos == "") {
				// 因为所选择的运单号没有手机号被过滤
				if (records.length > 0 && isSuccess == true) {
					Ext.ux.Toast.msg(predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.tip')/*提示*/, predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.noticeAlreadySuccess'), 'error', 1000);//'您所选择的运单当天已经语音、短信通知成功，无需再次批量通知'
					return;
				} else if (records.length > 0 && isNoticing == true) {
					Ext.ux.Toast.msg(predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.tip')/*提示*/, predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.isNoticing'), 'error', 1000);//'你选择的运单正在通知中，不能多次通知'
					return;
				} else if (records.length > 0 && hasNoMobile == true) {
					Ext.ux.Toast.msg(predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.tip')/*提示*/, '您选择的运单没有手机号，无法进行批量通知。', 'error', 1000);//'您选择的运单没有手机号，无法进行批量通知。'
					return;
				} else if (records.length > 0 && isTodaySuccess == true) {
					Ext.ux.Toast.msg(predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.tip')/*提示*/, predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.cantNotice'), 'error', 1000);//'已通知成功，当天不能再次通知。'
					return;
				} else if (records.length > 0 && isBatchStowageNOtHere == true){
					Ext.ux.Toast.msg(predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.tip')/*提示*/, predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.noticeExistBatchStowageNOtHere'), 'error', 1000);//'存有【分批配载未到齐】的运单，请货物到齐后再短信通知'
					return;
				}else if (records.length > 0 && isDeliver == true){
					Ext.ux.Toast.msg(predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.tip')/*提示*/, '派送方式为送货的运单在该环节不再发送短信', 'error', 1000);//'派送方式为送货的运单在该环节不再发送短信'306548
					//Ext.ux.Toast.msg(predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.tip')/*提示*/, predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.noticeDeliver'), 'error', 1000);//'派送方式为送货的运单在该环节不再发送短信'306548
					return;
				} else {
					Ext.ux.Toast.msg(predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.tip')/*提示*/, predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.please.select.waybill'), 'error', 1000);//'请选择运单。'
					return;
				}
			} else {
				if (records.length > 0 && isBatchStowageNOtHere == true){
					Ext.ux.Toast.msg(predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.tip')/*提示*/, predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.noticeExistBatchStowageNOtHere'), 'error', 1000);//'存有【分批配载未到齐】的运单，请货物到齐后再短信通知'
				}
			}
			var newVo = {
				'vo': {
					'conditionDto' : {
						'waybillNos': waybillNos,
						'notificationEntityList' : array
					}
				}
			}
			Ext.Ajax.request({
				// url: '../predeliver/batchNotifyList.action',
				url:predeliver.realPath('batchNotifyList.action'),
				jsonData: newVo,
				success: function(response){
					var result = Ext.decode(response.responseText);
					predeliver.notifyCustomer.initBatchNoticeWinForm(result,records);
					//如果选择的记录中有包含自提的运单 则默认选中是否征收保管费
		             if(isStorageCharge){
		            	 Ext.getCmp('Foss_predeliver_isStorageCharge_Id').setValue('Y');
		             }
				},
				exception: function(response) {
	                var json = Ext.decode(response.responseText);
	                Ext.ux.Toast.msg(predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.batchNotify.failuer'), json.message, 'error', 2000);//'批量通知失败'
	            }
			});
		}
	} ,{
		xtype : 'button',
		plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin', {
			seconds: 3
		}),
		width:80,
		text : predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.printArriveSheet'),//打印到达联
		disabled:!predeliver.notifyCustomer.isPermission('notifycustomerindex/notifycustomerindexlistbutton'),
		hidden:!predeliver.notifyCustomer.isPermission('notifycustomerindex/notifycustomerindexlistbutton'),
		handler : function() {
			var records = Ext.getCmp('Foss_predeliver_grid_NoticeCustomerGrid_Id').getSelectionModel().getSelection();
			//var waybillNos = [];
			var mygrid = this.up('gridpanel');
			if(records.length==0){
    			Ext.ux.Toast.msg(predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.tip')/*提示*/, predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.please.select.waybill'), 'error', 1000);//'请选择运单。'
    			return;
    		}
			if(records.length>50){
		        Ext.ux.Toast.msg('提示信息', "批量打印到达联时，勾选的条数不能大于50条！", 'error', 4000);
		        return;
		    }
    		mygrid.getPrintWindow().show();
    		mygrid.getPrintWindow().setSource('PKP_NOTIFY');
		}
	}, {
			xtype : 'button',
			plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin', {
				seconds: 3
			}),
			width:80,
			text : predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.storage.fee.report'),//保管费报表
			disabled:!predeliver.notifyCustomer.isPermission('notifycustomerindex/notifycustomerindexquerystoragebutton'),
			hidden:!predeliver.notifyCustomer.isPermission('notifycustomerindex/notifycustomerindexquerystoragebutton'),
			handler : function() {
				var records = Ext.getCmp('Foss_predeliver_grid_NoticeCustomerGrid_Id').getSelectionModel().getSelection();
				var waybillNos = '';
				for (var i = 0; i < records.length; i++) {
					waybillNos += records[i].get("waybillNo") + ",";
				}
				if (waybillNos == "") {
					Ext.ux.Toast.msg(predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.tip')/*提示*/, predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.please.select.waybill'), 'error', 1000);//'请选择运单。'
					return;
				}
				Ext.Ajax.request({
					// url: '../predeliver/queryStorageList.action',
					url:predeliver.realPath('queryStorageList.action'),
					params: {
						'vo.conditionDto.waybillNos': waybillNos
					},
					success: function(response){
						var result = Ext.decode(response.responseText);
						predeliver.notifyCustomer.initWarehouseChargeWinForm(result);
					}
				});
			}
		}
	,{
			xtype:'button',
			width:40,
			plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin', {
				seconds: 3
			}),
			name:'exportNotifyInfo',
			text:'导出',
			handler : function(){
				var queryNotifyCustomerform = predeliver.notifyCustomer.queryform,
				myForm = queryNotifyCustomerform.getForm(),
				waybillNo = myForm.getValues().waybillNo,
				vehicleAssembleNo = myForm.getValues().vehicleAssembleNo;
				if (queryNotifyCustomerform != null) {
					// 验证运单号输入的行数
					if (!Ext.isEmpty(waybillNo)) {
						var arrayWaybillNo = waybillNo.split('\n');
						if (arrayWaybillNo.length > 50) {
							Ext.ux.Toast.msg(predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.tip'), predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.waybillNo.valitation'), 'error', 3000); // '起止日期相隔不能超过30天！'
							return false;	
						}
						for (var i = 0; i < arrayWaybillNo.length; i++) {
							if (Ext.isEmpty(arrayWaybillNo[i])) {
								Ext.ux.Toast.msg(predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.tip'), predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.waybillNo.valitation'), 'error', 3000); // '起止日期相隔不能超过30天！'
								return false;	
							}
						}
					}
					// 验证车次号输入输入的行数
					if (!Ext.isEmpty(vehicleAssembleNo)) {
						var vehicleAssembleNos = vehicleAssembleNo.split('\n');
						if (vehicleAssembleNos.length > 10) {
							Ext.ux.Toast.msg(predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.tip'), predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.vehicleAssembleNo.valitation'), 'error', 3000); 
							return false;	
						}
						for (var i = 0; i < vehicleAssembleNos.length; i++) {
							if (Ext.isEmpty(vehicleAssembleNos[i])) {
								Ext.ux.Toast.msg(predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.tip'), predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.vehicleAssembleNo.valitation'), 'error', 3000);
								return false;	
							}
						}
					}
					// 入库时间验证
					var inStockTimeFrom = myForm.getValues().inStockTimeFrom, inStockTimeTo = myForm.getValues().inStockTimeTo;	
						arriveTimeFrom = myForm.getValues().arriveTimeFrom, arriveTimeTo = myForm.getValues().arriveTimeTo;	
					if (!Ext.isEmpty(inStockTimeFrom) && !Ext.isEmpty(inStockTimeTo)) {	
						var result = Ext.Date.parse(inStockTimeTo,'Y-m-d H:i:s') - Ext.Date.parse(inStockTimeFrom,'Y-m-d H:i:s');	
						if(result / (24 * 60 * 60 * 1000) >= 30){	
							Ext.ux.Toast.msg(predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.tip'), predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.the.date.range.cannot.be.more.than.30.days'), 'error', 3000); // '起止日期相隔不能超过30天！'
							return false;	
						}	
					}
					// 到达时间验证
					if (!Ext.isEmpty(arriveTimeFrom) && !Ext.isEmpty(arriveTimeTo)) {	
						var result = Ext.Date.parse(arriveTimeTo,'Y-m-d H:i:s') - Ext.Date.parse(arriveTimeFrom,'Y-m-d H:i:s');	
						if(result / (24 * 60 * 60 * 1000) >= 30){	
							Ext.ux.Toast.msg(predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.tip'), predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.the.date.range.cannot.be.more.than.30.days'), 'error', 3000); // '起止日期相隔不能超过30天！'
							return false;	
						}	
					}
					if((Ext.isEmpty(inStockTimeFrom) && (!Ext.isEmpty(inStockTimeTo))) ||(!Ext.isEmpty(inStockTimeFrom) && (Ext.isEmpty(inStockTimeTo)))){
						Ext.ux.Toast.msg(predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.tip'), predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.inStockTimeNull'), 'error', 3000);//入库起止时间不能为空
							return false;
					}else if((Ext.isEmpty(arriveTimeFrom) && (!Ext.isEmpty(arriveTimeTo))) ||(!Ext.isEmpty(arriveTimeFrom) && (Ext.isEmpty(arriveTimeTo)))){
						Ext.ux.Toast.msg(predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.tip'), predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.arriveTimeNull'), 'error', 3000);//车辆实际到达起止时间不能为空
						return false;
					}
					if(!myForm.isValid()){
						return false;
					}
					var handoverNo = myForm.getValues().handoverNo,vehicleAssembleNo = myForm.getValues().vehicleAssembleNo;
					// 未录入运单号、交接单号的情况下必须录入时间
					if (Ext.isEmpty(waybillNo) && Ext.isEmpty(handoverNo) && Ext.isEmpty(vehicleAssembleNo)) {
						if ((Ext.isEmpty(inStockTimeFrom) || Ext.isEmpty(inStockTimeTo))
							&& (Ext.isEmpty(arriveTimeFrom) || Ext.isEmpty(arriveTimeTo))) {
							Ext.ux.Toast.msg(predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.tip'), '入库时间、车辆实际到达时间必须输入一组起止日期', 'error', 3000); // '入库时间、预计到达时间必须输入'
							return false;	
					}
				}
				var queryParams = queryNotifyCustomerform.getValues();
				if(!Ext.fly('downloadAttachFileForm')){
					var frm = document.createElement('form');
					frm.id = 'downloadAttachFileForm';
					frm.style.display = 'none';
					document.body.appendChild(frm);
				}	
				//获取查询出来的异常信息
				var noticeCustomerGridStore = Ext.getCmp('Foss_predeliver_grid_NoticeCustomerGrid_Id').getStore();	
				//若异常信息不为空
				if(noticeCustomerGridStore.getCount()!=0){
					Ext.Ajax.request({
						url:predeliver.realPath('exportNoticeCustomer.action'),
						form: Ext.fly('downloadAttachFileForm'),
						method : 'POST',
						params : {
							'notifyCustomerVo.conditionDto.waybillNo':queryParams.waybillNo,  //运单号	
							'notifyCustomerVo.conditionDto.handoverNo':queryParams.handoverNo,// 交接单编号	
							'notifyCustomerVo.conditionDto.vehicleAssembleNo':queryParams.vehicleAssembleNo,	//车牌号
							'notifyCustomerVo.conditionDto.receiveMethod':queryParams.receiveMethod,		//派送方式
							'notifyCustomerVo.conditionDto.productCode':queryParams.productCode, //运输性质
							'notifyCustomerVo.conditionDto.noticeResult':queryParams.noticeResult,	//通知结果
							'notifyCustomerVo.conditionDto.storageDay':queryParams.storageDay,	//在库天数
							'notifyCustomerVo.conditionDto.inStockTimeFrom':queryParams.inStockTimeFrom, 		//入库时间开始
							'notifyCustomerVo.conditionDto.inStockTimeTo':queryParams.inStockTimeTo,	//入库时间结束	
							'notifyCustomerVo.conditionDto.arriveTimeFrom':queryParams.arriveTimeFrom,	//到达时间开始
							'notifyCustomerVo.conditionDto.arriveTimeTo':queryParams.arriveTimeTo		//到达时间结束	
						},
						isUpload: true
					});
				}else{
					//或者提示不能导出
					Ext.ux.Toast.msg(predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.tip'),predeliver.notifyCustomer.i18n('pkp.predeliver.exceptionProcess.exceptionInfo.notExport'), 'error', 3000);
				}
			}}
	}],
	columns : [
	    {
			header: '序号',//序列号
			xtype:'rownumberer',
			width:25
		   },
		{text : predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.status'),width : 70,dataIndex : 'noticeResult', 
			renderer : function(value, cellmeta, record, rowIndex, columnIndex, store){
				var result = '';
				/**if(value == 'VOICE_NOTICING') {
					//语音通知中
					result = '<div class="foss_icons_pkp_telnoticing"></div>';
				} else if(value == 'SMS_NOTICING') {
					//短信通知中
					result = '<div class="foss_icons_pkp_mailnoticing"></div>';
				} else*/ if(value == 'SUCCESS') {
					//通知成功 
					result = '<div class="foss_icons_pkp_noticeOk"></div>';
				} else if(value == 'FAILURE') {
					//通知失败
					result = '<div class="foss_icons_pkp_noticeError"></div>';
				}/** else if(value == 'NOTICING_UNSUCCESSFUL') {
					//通知未果
					result = '<div class="foss_icons_pkp_noticeNoResult"></div>';
				}*/
				var pendingType = record.get('pendingType');
				 //增加支付状态类型，可以筛选出"网上支付"未付款运单； 2、对"网上支付"未付款的运单，在查询出来之后进行颜色标识
    		  	if(record.data.isPay == 'N'){
    			  	result = result + '<div class="foss_icons_pkp_flagblue"></div>';
    		  	}
    		  	//运单未补录
                if (pendingType != null &&pendingType!='PC_ACTIVE'&& pendingType!='PDA_ACTIVE' && pendingType!='EWAYBILL_ACTIVE') {
                	result = result + '<div class="foss_icons_pkp_flagred"style="width:18px;height:18px;"></div>';
                }
    		  	
				return result;
			}
		},
		{text : predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.waybillNo'),width : 85,dataIndex : 'waybillNo', xtype : 'ellipsiscolumn'},
		{text : predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.receiveCustomerContact'),width : 75,dataIndex : 'receiveCustomerContact', xtype : 'ellipsiscolumn',
			renderer : function(value, cellmeta, record, rowIndex, columnIndex, store){
				//标示大客户
    		  	if(record.data.receiveBigCustomer == 'Y'){
    		  		value = '<div class="big_Customer_pic_common"></div>' + value  ;
    		  	}
    		  	return value;
			}
		},
		{text : predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.receiveCustomerPhone'),width : 75,dataIndex : 'receiveCustomerPhone', xtype : 'ellipsiscolumn'},
		{text : predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.receiveCustomerMobilephone'),width : 90,dataIndex : 'receiveCustomerMobilephone', xtype : 'ellipsiscolumn'},
		{text : predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.receiveMethod'),width : 94,dataIndex : 'receiveMethod'},
		{text : predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.productCode'),width : 94,dataIndex : 'productCode'},
		{text : predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.paidMethod'),width : 94,dataIndex : 'paidMethod'},
		{text : predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.receiveCustomerAddress'),width : 100,xtype : 'ellipsiscolumn',dataIndex : 'receiveCustomerAddress'},//送货地址
		{text : predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.inStockTime'),width : 130, dataIndex : 'inStockTime',
			renderer : function(value) {
				if (value != null) {
					var date = Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
					return date;
				} else {
					return null;
				}
			} 
		},
		{text : predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.arriveTime'),width : 130, dataIndex : 'arriveTime',hidden : true,
			renderer : function(value) {
				if (value != null) {
					var date = Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
					return date;
				} else {
					return null;
				}
			} 		
		},
		{text : predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.planArriveTime'),width : 130, dataIndex : 'planArriveTime',hidden : true,
			renderer : function(value) {
				if (value != null) {
					var date = Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
					return date;
				} else {
					return null;
				}
			} 		
		},
		{text : predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.create') + '<br>' + predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.pieces'),width : 40,xtype : 'numbercolumn',dataIndex : 'goodsQtyTotal',format:'0'},
		{text : predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.arrive')  + '<br>' + predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.pieces'),width : 40,xtype : 'numbercolumn',dataIndex : 'arriveGoodsQty',format:'0'},
		{text : predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.stock')  + '<br>' + predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.pieces'),width : 40,xtype : 'numbercolumn',dataIndex : 'stockQty',format:'0'},
		{text : predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.position')  + '<br>' + predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.pieces'),width : 40,xtype : 'numbercolumn',dataIndex : 'positionQty',format:'0'},
		{text : predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.onStock')  + '<br>' + predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.dayNum'),width : 40,xtype : 'numbercolumn',dataIndex : 'storageDay',format:'0'},
		{text : predeliver.notifyCustomer.i18n('pkp.predeliver.arriveDeliverManagerIndex.goodsWeightTotal') ,width : 40,dataIndex : 'goodsWeightTotal'}, //总重量
		{text : predeliver.notifyCustomer.i18n('pkp.predeliver.arriveDeliverManagerIndex.goodsVolumeTotal') ,width : 40,dataIndex : 'goodsVolumeTotal'}, //总体积
		{text : predeliver.notifyCustomer.i18n('pkp.predeliver.arriveDeliverManagerIndex.toPayAmountTotal'),width : 40,dataIndex : 'toPayAmount'}, //到付总金额 
		{text : predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.handover') + '<br>&nbsp;' + predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.pieces'),width : 40,dataIndex : 'handoverGoodsQty',hidden : true},
		{text : predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.handoverNo'),width : 85,dataIndex : 'handoverNo',hidden : true, xtype : 'ellipsiscolumn'},
		{text : predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.vehicleNo'),width : 85,dataIndex : 'vehicleNo',hidden : true, xtype : 'ellipsiscolumn'},
		{text : predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.vehicleAssembleNo'),width : 85,dataIndex : 'vehicleAssembleNo',hidden : true, xtype : 'ellipsiscolumn'},
		{text : predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.receivingHabits') ,width : 70,dataIndex : 'receivingHabits', xtype : 'ellipsiscolumn'},
		{dataIndex : 'selectType',hidden : true},
		{dataIndex : 'notificationTimeSpace',hidden : true},
		{dataIndex : 'taskStatus',hidden : true},
		{dataIndex : 'batchStowageNOtHere',hidden : true}
	],
	pagingToolbar : null,
  	getPagingToolbar : function() {
  		if (this.pagingToolbar == null) {
  			this.pagingToolbar = Ext.create('Deppon.StandardPaging', {
  				store : this.store,
  				maximumSize : 200,
  				layout : 'column',
  				//plugins: 'pagesizeplugin',
  				plugins : Ext.create('Deppon.ux.PageSizePlugin', {
  					sizeList : [['50', 50], ['100', 100], ['200', 200]]
  				}),
				displayInfo: true
  			});
  		}
  		return this.pagingToolbar;
  	},
  	printWindow: null,
    	getPrintWindow: function(){
    		var me = this;
    		if(this.printWindow==null){
    			me.printWindow = Ext.create('Foss.printArriveSheet.printWindow',me);
    		}
    		return me.printWindow;
    	},
	 listeners: {
        select:function(view, record,item, index){
			var transportInfoForm = Ext.getCmp('Foss.predeliver.noticeCustomer.TransportInfoFormPanel_id'),
				noticeInfoForm   = Ext.getCmp('Foss.predeliver.noticeCustomer.NoticeInfoFormPanel_Id'),
				historyPanelGrid =Ext.getCmp('Foss.predeliver.noticeCustomer.HistoryPanelGrid_id');
			var saveButton = Ext.getCmp('Foss_predeliver_notifyCustomer_NoticeInfoFormPanel_saveButten_Id');
			var recordsModel = Ext.getCmp('Foss_predeliver_grid_NoticeCustomerGrid_Id').getSelectionModel(),
				records=recordsModel.getSelection();
			if(records.length==2){
				// 设置grid的record
				 noticeInfoForm.getForm().reset();
				 transportInfoForm.getForm().reset();
				 historyPanelGrid.store.removeAll();
				 saveButton.setDisabled(true);
				 return;
			}
			if(records.length>=3){
				 return;
			}
			selectRecord = records[0].data;
			// 设置grid的record
		    noticeInfoForm.getForm().reset();
		    transportInfoForm.getForm().reset();
			historyPanelGrid.store.removeAll();
		    saveButton.setDisabled(true);
		/*deliverDate.setDateConfig({
			el: deliverDate.id+'-inputEl',
			minDate: Ext.Date.format(new Date, 'Y-m-d H:i:s')
		});*/
		// 设置grid的record
		//this.noticeInfoForm.setGridRecord(record);
		//setFormEditAble(transportInfoForm, false);
		// 绑定表格数据到表单上
		Ext.Ajax.request({
			// url: '../predeliver/queryWaybillInfo.action',
			url:predeliver.realPath('queryWaybillInfo.action'),
			params: {
				'vo.conditionDto.waybillNo': record.get('waybillNo'),
				'vo.conditionDto.notifyCustomerDto.selectType': record.get('selectType'),
				'vo.conditionDto.notifyCustomerDto.arriveGoodsQty': record.get('arriveGoodsQty'),
				'vo.conditionDto.notifyCustomerDto.stockQty': record.get('stockQty'),
				'vo.conditionDto.notifyCustomerDto.handoverGoodsQty': record.get('handoverGoodsQty'),
				'vo.conditionDto.notifyCustomerDto.taskStatus': record.get('taskStatus'),
				'vo.conditionDto.notifyCustomerDto.paidMethodVir': record.get('paidMethod') //239
			},
			success: function(response){
				var result = Ext.decode(response.responseText),
					 model = Ext.ModelManager.create(result.vo.conditionDto.notifyCustomerDto,'Foss.predeliver.model.NoticeCustomer'),
					 transportInfoFormT = transportInfoForm.getForm(),
					 noticeInfoFormT = noticeInfoForm.getForm(),
					 paymentTypeGroup = noticeInfoFormT.findField('paymentTypeName'),
					 toPayAmount = noticeInfoFormT.findField('toPayAmount').getValue();
				transportInfoFormT.loadRecord(model);
				noticeInfoFormT.loadRecord(model);
				historyPanelGrid.getStore().loadData(result.vo.conditionDto.notificationEntityList);
				saveButton.setDisabled(false);
				Ext.suspendLayouts();
				// 动态显示运行信息页面元素
				var selectType = model.data.selectType, // 查询类型
					paidMethod = FossDataDictionary.rendererDisplayToSubmit(model.data.paidMethod, predeliver.PAYMENTMODE), // 运单付款方式
					customerQulification = model.data.customerQulification;// 客户付款方式
				if (selectType == '0') {
					transportInfoFormT.findField('stockStatus').setValue(predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.inStock'));
					// 根据交接单号、车次号、预计到货时间显示的结果内容
					transportInfoFormT.findField('stockStatus').setFieldLabel('&nbsp;&nbsp;'+predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.stockStatus'));//库存状态
					transportInfoFormT.findField('goodsQtyTotal').setFieldLabel('&nbsp;&nbsp;'+ predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.goodsQtyTotal'));// 开单件数
					transportInfoFormT.findField('arriveGoodsQty').setFieldLabel('&nbsp;&nbsp;'+predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.arriveGoodsQty'));
					transportInfoFormT.findField('stockQty').setFieldLabel('&nbsp;&nbsp;'+predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.stockQty'));
					transportInfoFormT.findField('storageDay').setFieldLabel('&nbsp;&nbsp;'+predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.storageDay'));
					transportInfoFormT.findField('storageCharge').setFieldLabel('&nbsp;&nbsp;'+predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.custodial.fee'));
					
				} else {
					// 根据交接单号、车次号、预计到货时间显示的结果内容
					//开单件数
					transportInfoFormT.findField('stockStatus').setFieldLabel('&nbsp;&nbsp;'+predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.goodsQtyTotal'));
					transportInfoFormT.findField('stockStatus').setValue(model.data.goodsQtyTotal);
					//到达件数
					transportInfoFormT.findField('goodsQtyTotal').setFieldLabel('&nbsp;&nbsp;'+ predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.arriveGoodsQty'));
					transportInfoFormT.findField('goodsQtyTotal').setValue(model.data.arriveGoodsQty);
					transportInfoFormT.findField('arriveGoodsQty').setFieldLabel('&nbsp;&nbsp;'+predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.handover') + predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.pieces'));// 交接单件数
					transportInfoFormT.findField('arriveGoodsQty').setValue(model.data.handoverGoodsQty);
					transportInfoFormT.findField('stockQty').setFieldLabel("");
					transportInfoFormT.findField('stockQty').setValue("");
					transportInfoFormT.findField('storageDay').setFieldLabel("");
					transportInfoFormT.findField('storageDay').setValue("");
					transportInfoFormT.findField('storageCharge').setFieldLabel("");
					transportInfoFormT.findField('storageCharge').setValue("");
				}
				
				//通知信息-开单到付且已网上付款-239284
				if(!Ext.isEmpty(model.data.isOrPayStatus)) {
					noticeInfoFormT.findField('toPayAmount').setValue(model.data.toPayAmount + "【" + model.data.isOrPayStatus+"】");
					toPayAmountHidden = model.data.toPayAmount;
				} else {
					noticeInfoFormT.findField('toPayAmount').setValue(model.data.toPayAmount);
					toPayAmountHidden = model.data.toPayAmount;
				}
				//运单信息-运单重量≥500kg标记红色-239284
				if(!Ext.isEmpty(model.data.goodsWeightTotal)) {
					if (model.data.goodsWeightTotal >= 500) {
						transportInfoFormT.findField('goodsWeightTotal').setFieldStyle('color:red');
				  } else {
						transportInfoFormT.findField('goodsWeightTotal').setFieldStyle('color:black');
					}
				}
				
				//运单信息-运单体积≥2.5F标记红色-239284
				if(!Ext.isEmpty(model.data.goodsVolumeTotal)) {
					if (model.data.goodsVolumeTotal >= 2.5) {
						transportInfoFormT.findField('goodsVolumeTotal').setFieldStyle('color:red');
				  } else {
						transportInfoFormT.findField('goodsVolumeTotal').setFieldStyle('color:black');
					}
				}
				
				if(model.data.deliverDate==null ||model.data.deliverDate==''){
					noticeInfoFormT.findField('deliverDate').setValue(new Date());
				}else{
					noticeInfoFormT.findField('deliverDate').setValue(Ext.Date.parse(model.data.deliverDate, "Y-m-d H:i:s", true));
				}
				var noticeTypeName = noticeInfoFormT.findField('noticeTypeName');
				noticeTypeName.getComponent('VOICE_NOTICE').setDisabled(false);
				noticeTypeName.getComponent('SMS_NOTICE').setDisabled(false);
				// 设置送货地址的高度
				//transportInfoFormT.findField('receiveCustomerAddress').inputEl.setHeight(120, true);
				// 初始化通知信息页面元素
				if (selectType == "1") {
					// 按照车次、交接单、预计到达时间查询，如果车辆状态不是已到达或者已卸车，营业员可以通过电话提前通知,不能通过语音或者短信通知客户。
					var taskStatus = model.data.taskStatus;
					if (!(taskStatus ==  predeliver.ARRIVED || taskStatus == predeliver.UNLOADED)) {
						noticeTypeName.getComponent('VOICE_NOTICE').setDisabled(true);
						noticeTypeName.getComponent('SMS_NOTICE').setDisabled(true);
					}
					// 设置通知信息上的件数为交接单件数
					noticeInfoFormT.findField('arriveGoodsQty').setValue(model.data.handoverGoodsQty);
					//开单件数和交接单件数不一致时
					if (transportInfoFormT.findField('arriveGoodsQty').getValue() != transportInfoFormT.findField('stockStatus').getValue()) {
						transportInfoFormT.findField('arriveGoodsQty').setFieldStyle('color:red');
						transportInfoFormT.findField('stockStatus').setFieldStyle('color:red');
					} else {
						transportInfoFormT.findField('arriveGoodsQty').setFieldStyle('color:black');
						transportInfoFormT.findField('stockStatus').setFieldStyle('color:black');
					}
				} else {
					// 设置通知信息上的件数为库存件数
					noticeInfoFormT.findField('arriveGoodsQty').setValue(model.data.stockQty);
					//开单件数和库存件数不一致时
					if (transportInfoFormT.findField('stockQty').getValue() != transportInfoFormT.findField('goodsQtyTotal').getValue()) {
						transportInfoFormT.findField('stockQty').setFieldStyle('color:red');
						transportInfoFormT.findField('goodsQtyTotal').setFieldStyle('color:red');
					} else {
						transportInfoFormT.findField('stockQty').setFieldStyle('color:black');
						transportInfoFormT.findField('goodsQtyTotal').setFieldStyle('color:black');
					}
				}
				// 付款方式的验证
				//if (paidMethod != predeliver.FC) {
			  if (toPayAmount > 0) {
					// ibm-wangfei 付款方式验证修改为到付金额验证 2013-2-2 15:45:31
					// 运单的付款方式不等于到付，隐藏付款方式
					paymentTypeGroup.setVisible(false);
				} else {
					if (customerQulification != null && (customerQulification == predeliver.CLEARING_TYPE_MONTH || customerQulification == predeliver.CLEARING_TYPE_HALF_MONTH)) {
						//客户资质为半月结、月结时，显示到付、月结，默认显示月结
						paymentTypeGroup.setVisible(true);
						paymentTypeGroup.getComponent('CT').setDisabled(false);
						paymentTypeGroup.getComponent('DT').setDisabled(false);
						paymentTypeGroup.setValue({'paymentType' : 'CT'});
					} else {
						// 客户资质为空或非月结时，只显示到付
					}
				}
			      // 初始化时，如果派送方式为自提 默认选中是否征收保管费
			    if(model.data.receiveMethod=='自提'){
                noticeInfoFormT.findField('isStorageCharge').setValue(true);
              	}
				// 初始化时，通知结果默认为成功
				noticeInfoFormT.findField('noticeResultName').setValue({'noticeResult' : 'SUCCESS'});
				// 联系方式手机没有时，显示电话
				if (Ext.isEmpty(model.data.receiveCustomerMobilephone) && !Ext.isEmpty(model.data.receiveCustomerPhone)) {
					noticeInfoFormT.findField('receiveCustomerMobilephone').setValue(model.data.receiveCustomerPhone);
				}
				noticeInfoForm.viewNoticeTypeInfo(noticeInfoForm);
				// 设置页面客户资质显示
				noticeInfoFormT.findField('customerQulification').setValue(FossDataDictionary.rendererSubmitToDisplay(model.data.customerQulification, 'CLEARING_TYPE'));
				// 初始化时，通知信息发票内容自动隐藏
				//predeliver.notifyCustomer.setInvoice(noticeInfoFormT, false);
				
				//设置省、市、区, 街道， 详细地址
//				noticeInfoFormT.findField('actualProvN').setValue(result.vo.conditionDto.notificationEntity.actualProvN); //result.vo.conditionDto.notifyCustomerDto.billTime
				noticeInfoFormT.findField('actualStreetn').setValue(result.vo.conditionDto.notificationEntity.actualStreetn); 
				noticeInfoFormT.findField('actualAddressDetail').setValue(result.vo.conditionDto.notificationEntity.actualAddressDetail); 
//				noticeInfoFormT.findField('actualProvCode').setValue(result.vo.conditionDto.notifyCustomerDto.receiveCustomerProvCode); 
//				noticeInfoFormT.findField('actualCityCode').setValue(result.vo.conditionDto.notifyCustomerDto.receiveCustomerCityCode); 
//				noticeInfoFormT.findField('actualDistrictCode').setValue(result.vo.conditionDto.notifyCustomerDto.receiveCustomerDistCode);
				
				if (record.get('isExhibitCargo') && record.get('isExhibitCargo') == 'Y') {
					noticeInfoForm.down('checkbox[name=isExhibition]').setValue(true);
				}
				
				// 会展货
				if (result.vo.conditionDto.notificationEntity.isExhibition) {
					if (result.vo.conditionDto.notificationEntity.isExhibition == 'Y') {
						noticeInfoForm.down('checkbox[name=isExhibition]').setValue(true);
					} else {
						noticeInfoForm.down('checkbox[name=isExhibition]').setValue(false);
					}
				}
				// 空车出
				if (result.vo.conditionDto.notificationEntity.isEmptyCar) {
					noticeInfoForm.down('checkbox[name=isEmptyCar]').setValue(result.vo.conditionDto.notificationEntity.isEmptyCar);
				}
				
				//由运单带出的默认的收货省
				if (result.vo.conditionDto.notifyCustomerDto.receiveCustomerProvCode != null && result.vo.conditionDto.notifyCustomerDto.receiveCustomerProvCode != "" ) {
					Ext.getCmp('actualAddress_ID').setReginValue(result.vo.conditionDto.notificationEntity.actualProvN, result.vo.conditionDto.notifyCustomerDto.receiveCustomerProvCode , predeliver.PCC_PROV);
				}
				//由运单带出的默认的收货市
				if (result.vo.conditionDto.notifyCustomerDto.receiveCustomerCityCode != null && result.vo.conditionDto.notifyCustomerDto.receiveCustomerCityCode != "" ) {
					Ext.getCmp('actualAddress_ID').setReginValue(result.vo.conditionDto.notificationEntity.actualCityN, result.vo.conditionDto.notifyCustomerDto.receiveCustomerCityCode , predeliver.PCC_CITY);
				}
				//由运单带出的默认的收货县
				if (result.vo.conditionDto.notifyCustomerDto.receiveCustomerDistCode != null && result.vo.conditionDto.notifyCustomerDto.receiveCustomerDistCode != "" ) {
					Ext.getCmp('actualAddress_ID').setReginValue(result.vo.conditionDto.notificationEntity.actualDistrictN, result.vo.conditionDto.notifyCustomerDto.receiveCustomerDistCode , predeliver.PCC_DIST);
				}
				
				//带出的收货习惯-时间段
				if (!Ext.isEmpty(result.vo.conditionDto.notificationEntity.deliveryTimeInterval)){
					not_habit_deliveryTimeInterval = result.vo.conditionDto.notificationEntity.deliveryTimeInterval;
					noticeInfoFormT.findField('deliveryTimeInterval').setValue(result.vo.conditionDto.notificationEntity.deliveryTimeInterval);
				} else {
					not_habit_deliveryTimeInterval = null;
				}
				//带出的收货习惯-送货时间点开始
				if (!Ext.isEmpty(result.vo.conditionDto.notificationEntity.deliveryTimeStart)){
					not_habit_deliveryTimeStart = result.vo.conditionDto.notificationEntity.deliveryTimeStart;
					noticeInfoFormT.findField('deliveryTimeStart').setValue(result.vo.conditionDto.notificationEntity.deliveryTimeStart);
				} else {
					not_habit_deliveryTimeStart = null;
				}
				//带出的收货习惯-送货时间点结束
				if (!Ext.isEmpty(result.vo.conditionDto.notificationEntity.deliveryTimeOver)){
					not_habit_deliveryTimeOver = result.vo.conditionDto.notificationEntity.deliveryTimeOver;
					noticeInfoFormT.findField('deliveryTimeOver').setValue(result.vo.conditionDto.notificationEntity.deliveryTimeOver);
				} else {
					not_habit_deliveryTimeOver = null;
				}
				//带出的收货习惯-发票类型
				if (!Ext.isEmpty(result.vo.conditionDto.notificationEntity.invoiceType)){
					not_habit_invoiceType = result.vo.conditionDto.notificationEntity.invoiceType;
					noticeInfoFormT.findField('invoiceType').setValue(result.vo.conditionDto.notificationEntity.invoiceType);
				} else {
					not_habit_invoiceType = null;
				}
				//带出的收货习惯-发票类型备注
				if (!Ext.isEmpty(result.vo.conditionDto.notificationEntity.invoiceDetail)){
					not_habit_invoiceDetail = result.vo.conditionDto.notificationEntity.invoiceDetail;
					noticeInfoFormT.findField('invoiceDetail').setValue(result.vo.conditionDto.notificationEntity.invoiceDetail);
				} else {
					not_habit_invoiceDetail = null;
				}
				//带出的收货习惯-发票类型备注
				if (!Ext.isEmpty(result.vo.conditionDto.notificationEntity.receiptHabitRemark)){
					not_habit_receiptHabitRemark = result.vo.conditionDto.notificationEntity.receiptHabitRemark;
					noticeInfoFormT.findField('receiptHabitRemark').setValue(result.vo.conditionDto.notificationEntity.receiptHabitRemark);
				} else {
					not_habit_receiptHabitRemark = null;
				}
				
				Ext.resumeLayouts(true);
			}
		});
		}
	},
  	constructor : function(config) {
  		var me = this,
			cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.predeliver.store.NoticeCustomer');
		me.bbar = me.getPagingToolbar();
		me.callParent([cfg]);
	}
});
//搜索结果的统计信息
Ext.define('Foss.notifyCustomer.TotalForm', {
	extend : 'Ext.form.Panel',
	alias : 'widget.notifyCustomerTotalform',
	id:'Foss_notifyCustomer_TotalForm_Id',
	items:[{
		defaultType: 'numberfield',
		defaults: {readOnly:true},
		layout: {
		    type: 'table',
		    columns: 4
		},
		items :[{
		    fieldLabel:predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.totalVotes'),  //总票数
		    name: 'totalVotes'
		}, {
		    fieldLabel: predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.goodsQtyTotals'),  //总件数
		    name: 'goodsQtyTotals'
		}, {
		    fieldLabel: predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.goodsWeightTotals'),  //总重量（吨）
		    name: 'goodsWeightTotals'
		}, {
		    fieldLabel: predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.goodsVolumeTotals'),  //总体积（方）
		    name: 'goodsvolumeTotals'
		}]
	}]
});
// ====================batch notice window and grid start================
predeliver.notifyCustomer.initBatchNoticeWinForm = function(data,gridRecords) {
	if (Ext.getCmp('Foss_predeliver_window_batchNotice_Id')) {
		Ext.getCmp('Foss_predeliver_window_batchNotice_Id').show();
		return;
	}
	var containArray = new Array();
	Ext.create('Ext.window.Window', {
		id : 'Foss_predeliver_window_batchNotice_Id',
		width : 1000,
		title : predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.batchNotify'),//批量通知
		modal:true,
		// 动态进行通知内容赋值
		changeNotice : function(_this, newValue, oldValue, eOpts) {
			// 预计提货时间
			if (_this.name == 'estimatedPickupTime') {
				var estimatedPickupTimeH = Ext.getCmp('Foss_predeliver_estimatedPickupTimeH_Id'), newValue = _this.getValue();
				// 判断输入小时，如果大于99，不处理
				if (newValue > 99) {
					return;
				}
				// 判断现在输入的值和未变更前的值是否相等
				
				if (newValue == null) {
					newValue = '';
				}
				if (estimatedPickupTimeH.getValue() == newValue) {
					return;
				}
				estimatedPickupTimeH.setValue(newValue);
			}
			var noticeType = Ext.getCmp('Foss_predeliver_noticeType_Id').getValue().noticeType,
				isStorageChargeCheck = Ext.getCmp('Foss_predeliver_isStorageCharge_Id').getSubmitData(),isStorageCharge,
				estimatedPickupTime = Ext.getCmp('Foss_predeliver_estimatedPickupTime_Id').getValue(), notificationEntity = '', 
				array = new Array(), notificationEntityT = '';
			// 是否征收保管费
			if (isStorageChargeCheck != null) {
				isStorageCharge = isStorageChargeCheck.isStorageCharge;
			} else {
				isStorageCharge = '';
			}
			var store = Ext.data.StoreManager.lookup('Foss_predeliver_model_BatchNoticeID'),
				batchNoticeStoreItems = store.data.items, waybillNos = '';
			for (var i = 0; i < batchNoticeStoreItems.length; i++) {
				waybillNos +=  batchNoticeStoreItems[i].data.waybillNo  + ",";
				notificationEntity = {'waybillNo' : batchNoticeStoreItems[i].data.waybillNo, 'arriveGoodsQty' : batchNoticeStoreItems[i].data.arriveGoodsQty};
				array.push(notificationEntity);
			}
			
			notificationEntity = { 'noticeType' : noticeType,'isStorageCharge' : isStorageCharge,'estimatedPickupTime' : estimatedPickupTime};
			var newVo = {
						'vo': {
								'conditionDto' : {
								'notificationEntity' :notificationEntity, 
								'waybillNos' : waybillNos,
								'notificationEntityList' : array
							}
						}
					};
			// 取消多选框选择状态
			Ext.getCmp('Foss_noticeCustomer_batchNotice_ID').getSelectionModel().deselectAll();
			Ext.Ajax.request({
				// url: '../predeliver/batchNotifyList.action',
				url:predeliver.realPath('batchNotifyList.action'),
				jsonData : newVo,
				success: function(response){
					var result = Ext.decode(response.responseText);
					// 取消多选框选择状态
//					Ext.getCmp('Foss_noticeCustomer_batchNotice_ID').getSelectionModel().deselectAll();
					Ext.getCmp('Foss_noticeCustomer_batchNotice_ID').getStore().loadData(result.vo.dtoList);
				}
			});
		},
		items : [predeliver.notifyCustomer.initBatchNoticeGrid(data.vo.dtoList)],
			buttons : [{
				xtype : 'label',
				text : predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.description.onlyIn')/*'说明:只能在'*/ + data.vo.informationReceiveTimeRange + predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.send.voiceAndSMS'),//'发送语音与短信通知'
				width : 250,
				margin: '4 45 0 0'
			}, {
				xtype : 'container',
				border: false,
				html : '<div><font color="#FF0000">' + predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.notice') + '</font></div>'
			},{
				cls : 'yellow_button',
				text : predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.confirm.notify'),//确定通知
				disabled:!predeliver.notifyCustomer.isPermission('notifycustomerindex/notifycustomerindexnotifybutton'),
				hidden:!predeliver.notifyCustomer.isPermission('notifycustomerindex/notifycustomerindexnotifybutton'),
				plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin', {
					seconds: 3
				}),
				style : {
					float : 'right'
				},
				handler : function() {
					var records = Ext.getCmp('Foss_noticeCustomer_batchNotice_ID').getSelectionModel().getSelection(),
						voiceArray = new Array(),
						smsArray = new Array();
					if (records.length == 0) {
						Ext.ux.Toast.msg(predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.tip')/*提示*/, predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.select.least.one.record'), 'error', 1000);//'至少选择一条记录。'
						return;
					}
					var array = new Array(), notificationEntity = '',noticeType = Ext.getCmp('Foss_predeliver_noticeType_Id').getValue().noticeType,
						isStorageChargeCheck = Ext.getCmp('Foss_predeliver_isStorageCharge_Id').getSubmitData(),isStorageCharge,
						estimatedPickupTime = Ext.getCmp('Foss_predeliver_estimatedPickupTime_Id').getValue();
					if (isStorageChargeCheck != null) {
						isStorageCharge = isStorageChargeCheck.isStorageCharge;
					} else {
						isStorageCharge = '';
					}
					var length = gridRecords.length;
					for (var i = 0; i < records.length; i++) {
						// 判断选择的记录有没有手机号码
						if(Ext.isEmpty(records[i].data.receiveCustomerMobilephone)) {
							Ext.ux.Toast.msg(predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.error')/*'错误'*/, predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.must.have.mobile.phone'), 'error', 2000);//选择的运单必须有手机号码。
							return;
						}
						
						//判断在必选的情况下是否选中是否征收保管费
		                if(records[i].data.receiveMethod=='自提'&& isStorageChargeCheck==null){
		                 Ext.ux.Toast.msg(predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.error')/*'错误'*/, predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.must.have.storage.charge'), 'error', 2000);
		                 return;  
		                }
						notificationEntity = {'waybillNo' : records[i].data.waybillNo,
							'receiveCustomerContact' : records[i].data.receiveCustomerContact,
							'receiveCustomerMobilephone' : records[i].data.receiveCustomerMobilephone,
							'receiveMethod' : records[i].data.receiveMethod,
							'noticeType' : noticeType, 
							'isStorageCharge' : isStorageCharge,
							'estimatedPickupTime' : estimatedPickupTime,
							'noticeContent' : records[i].data.noticeContent,
							'noticeContentVoice' : records[i].data.noticeContentVoice,
							'arriveGoodsQty' : records[i].data.arriveGoodsQty,
							'specialValueAddedService':records[i].data.specialValueAddedService
							};
						array.push(notificationEntity);
						var j = 0;
						for(j = 0;j < length; j ++){
							if(gridRecords[j].get('waybillNo') == records[i].data.waybillNo){
								containArray.push(gridRecords[j]);
								if(gridRecords[j].get('isVoiceSuccess') == 'Y' && Ext.getCmp('Foss_noticeCustomer_batchNotice_ID').down('radiogroup').getValue().noticeType == 'VOICE_NOTICE'){
									voiceArray.push(gridRecords[j].get('waybillNo'));
								}
								if(gridRecords[j].get('isSmsSuccess') == 'Y' && Ext.getCmp('Foss_noticeCustomer_batchNotice_ID').down('radiogroup').getValue().noticeType == 'SMS_NOTICE'){
									smsArray.push(gridRecords[j].get('waybillNo'));
								}
								break;
							}
						}
						//Ext.Array.remove(gridRecords,gridRecords[j]);
					}
					var divHtml = '<div style="white-space: normal; overflow: visible; word-break: break-all;">';
					if(!Ext.isEmpty(voiceArray) && Ext.getCmp('Foss_noticeCustomer_batchNotice_ID').down('radiogroup').getValue().noticeType == 'VOICE_NOTICE'){
						Ext.ux.Toast.msg(predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.error')/*'错误'*/, divHtml + predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.voiceNoticeAlready') + voiceArray.join() + '</div>', 'error', 2000);//选择的运单必须有手机号码。
						return;
					}
					if(!Ext.isEmpty(smsArray) && Ext.getCmp('Foss_noticeCustomer_batchNotice_ID').down('radiogroup').getValue().noticeType == 'SMS_NOTICE'){
						Ext.ux.Toast.msg(predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.error')/*'错误'*/, divHtml + predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.smsNoticeAlready') + smsArray.join() + '</div>', 'error', 2000);//选择的运单必须有手机号码。
						return;
					}
					var newVo = {
						'vo': {
							'conditionDto' : {'notificationEntityList' :array}
						}
					};
					Ext.Ajax.request({
						// url: '../predeliver/batchNotify.action',
						url:predeliver.realPath('batchNotify.action'),
						jsonData : newVo,
						success: function(response){
							Ext.ux.Toast.msg(predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.tip')/*提示*/, predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.batch.notify.success'), 'ok', 1000);//'批量通知成功。'
//							var result;
//							if(Ext.getCmp('Foss_predeliver_noticeType_Id').getValue().noticeType == 'VOICE_NOTICE'){
//								result = 'VOICE_NOTICING';
//							} else {
//								result = 'SMS_NOTICING';
//							}
							Ext.each(containArray, function(item, index, allItems){
								item.set('noticeResult','SUCCESS');
								item.set('notificationTime',new Date());
								if(Ext.getCmp('Foss_noticeCustomer_batchNotice_ID').down('radiogroup').getValue().noticeType == 'SMS_NOTICE'){
									item.set('isSmsSuccess','Y');
								} else {
									item.set('isVoiceSuccess','Y');
								}
							});
							Ext.getCmp('Foss_predeliver_window_batchNotice_Id').close();
						},
						 exception: function(response) {
			                var json = Ext.decode(response.responseText);
			                Ext.ux.Toast.msg(predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.batch.notify.failuer'), json.message, 'error', 3000);//'批量通知失败'
		                }
					});
					
				}
			}],
		constructor: function(config){
			var me = this,
				cfg = Ext.apply({}, config);
			me.callParent([cfg]);
		}
	}).show();
}

predeliver.notifyCustomer.initWarehouseChargeWinForm = function(data) {
	if (Ext.getCmp('Foss_predeliver_window_warehouseCharge_Id')) {
		Ext.getCmp('Foss_predeliver_window_warehouseCharge_Id').show();
		return;
	}
	Ext.create('Ext.window.Window', {
		id : 'Foss_predeliver_window_warehouseCharge_Id',
		width : 800,
		title : predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.storage.fee.report'),//保管费报表
		modal:true,
		items : [predeliver.notifyCustomer.initWarehouseChargeGrid(data.vo.dtoList)]
	}).show();
}
predeliver.notifyCustomer.initBatchNoticeGrid = function(data) {
	Ext.define('Foss.predeliver.model.BatchNotice', {
		extend : 'Ext.data.Model',
		fields : [
		  {name : 'waybillNo'},
		  {name : 'receiveCustomerContact'},
		  {name : 'receiveCustomerMobilephone'},
		  {name : 'receiveMethod',
			convert:function(value) {
				var v = FossDataDictionary.rendererSubmitToDisplay(value, predeliver.PICKUPGOODSHIGHWAYS);
				if(Ext.isEmpty(v) || value == v){
					v = FossDataDictionary.rendererSubmitToDisplay(value, predeliver.PICKUPGOODSSPECIALDELIVERYTYPE);
				}
				return v;
			}
		  }, /** 派送方式 */
		  {name : 'noticeContent'},
		  {name : 'noticeContentVoice'},
		  {name : 'arriveGoodsQty', type:'number'},
		  {name : 'specialValueAddedService'} //特殊增值服务类型
		]
		});
	var batchNoticeStore = Ext.create('Ext.data.Store', {
			model : 'Foss.predeliver.model.BatchNotice',
			storeId : 'Foss_predeliver_model_BatchNoticeID',
			data : data
		});
	return Ext.create('Ext.grid.Panel', {
		id : 'Foss_noticeCustomer_batchNotice_ID',
		store : batchNoticeStore,
		bodyCls : 'autoHeight',
		autoScroll : false,
		selModel : Ext.create('Ext.selection.CheckboxModel'),
		columns : [{
			text : predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.waybillNo'),// 运单号
			width : 100,
			dataIndex : 'waybillNo'
		}, {
			text : predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.consignee'),//收货人
			flex : .8,
			dataIndex : 'receiveCustomerContact'
		}, {
			text : predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.consigneeMobilephone'),//手机号
			width : 120,
			dataIndex : 'receiveCustomerMobilephone'
		}, {
			text : predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.receiveMethod'),//派送方式
			flex : 1,
			dataIndex : 'receiveMethod',
			xtype : 'ellipsiscolumn'
		},
		{
			text : predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.information.content'),//信息内容
			flex : 3.6,
			dataIndex : 'noticeContent',
			xtype : 'ellipsiscolumn'
		} , {
			dataIndex : 'arriveGoodsQty',
			hidden : true
		}, {
			dataIndex : 'noticeContentVoice',
			hidden : true
		},{
			dataIndex : 'specialValueAddedService',
			hidden : true
		}],
		dockedItems: {
			xtype : 'toolbar',
			dock : 'bottom',
			layout : 'column',
			items : [
				{xtype : 'radiogroup' , vertical : true , columnWidth : .4,id : 'Foss_predeliver_noticeType_Id',
				items : [
						{boxLabel : predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.voiceNotice') , name : 'noticeType' , inputValue : 'VOICE_NOTICE'} , 
						{boxLabel : predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.smsNotice') , name : 'noticeType' , inputValue : 'SMS_NOTICE', checked : true}
					],
				listeners : { 
						change:function(_this, newValue, oldValue, eOpts){
							this.up('window').changeNotice(_this, newValue, oldValue, eOpts);
						}
					}
				}, 
				{xtype : 'checkbox' , name : 'isStorageCharge' , boxLabel : predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.isStorageCharge'),columnWidth : .3, id : 'Foss_predeliver_isStorageCharge_Id', inputValue : 'Y',
					listeners : { //是否征收保管费
						change:function(_this, newValue, oldValue, eOpts){
							this.up('window').changeNotice(_this, newValue, oldValue, eOpts);
						}
					}}, 
				{xtype : 'container' ,  layout: 'column',columnWidth : .3,
					items : [
						{xtype : 'label' , text : predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.plan'), margin: '4 0 0 9'} , // 预计
						{xtype : 'numberfield' , name : 'estimatedPickupTime', width : 30,id : 'Foss_predeliver_estimatedPickupTime_Id',
						hideTrigger : true, maxValue: 99, minValue: 1, maxLength :2, allowDecimals : false, msgTarget: 'qtip',
						listeners : { 
							blur : function (_this, The, eOpts) {
								this.up('window').changeNotice(_this, null, null, eOpts);
							}
						}} , 
						{xtype : 'label',  text : predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.hours.after.delivery')/*小时后提货*/, margin: '4 0 0 0'}
					]
				},
				{name : 'estimatedPickupTimeH' , xtype : 'hiddenfield', id : 'Foss_predeliver_estimatedPickupTimeH_Id'}]
		}
	});
}
// ====================batch notice window and grid end================
// ====================warehosue charge report window and grid
// start================
predeliver.notifyCustomer.initWarehouseChargeGrid = function(data) {
	var store = Ext.create('Ext.data.Store', {
		model : 'Foss.predeliver.model.WarehouseCharge',
		data : data
	});
	return Ext.create('Ext.grid.Panel', {
		id : 'Foss.noticeCustomer.panel.WarehouseChargeGrid',
		autoScroll : false,
		stripeRows : true,
		collapsible : true,
		selType : 'rowmodel',
		store : store,
		bodyCls : 'autoHeight',
		columns : [{
			dataIndex : 'waybillNo',
			text : predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.waybillNo'),//运单号
			flex : 0.8
		}, {
			dataIndex : 'storageDay',
			text : predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.storageDay'),//'库存天数'
			flex : 0.8
		}, {
			dataIndex : 'overdueDay',
			text : predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.overdueDay'),//'逾期天数'
			flex : 0.8
		}, {
			dataIndex : 'storageCharge',
			text : predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.storageCharge'),//保管费金额
			flex : 0.8
		}, {
			dataIndex : 'exceptionType',
			text : predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.exceptionType'),//货物异常类型
			flex : 1
		}, {
			dataIndex : 'exceptionNotes',
			text : predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.exceptionNotes'),//异常备注
			flex : 2,
			xtype : 'ellipsiscolumn'
		}]
	})
}
//运单信息  右下二
Ext.define('Foss.predeliver.noticeCustomer.TransportInfoFormPanel',{
	extend: 'Ext.form.Panel',
	title : predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.waybillInfo'),//运单信息
	//收缩
	collapsible: true,
	//是否有框
	//frame:true,
	height:315,
	//动画收缩
	animCollapse: true,
	defaults: {
		margin:'1 1 1 1',
		labelWidth:65,
		readOnly : true
	},
	defaultType : 'textfield',
	//自动收缩高度
	//cls:'autoHeight',
	//bodyCls:'autoHeight',
	layout:'column',
	items : [
		{name : 'waybillNo' ,  fieldLabel : predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.waybillNo') ,  columnWidth : .4} ,  
		{name : 'receiveOrgName' , fieldLabel : predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.receiveOrgName') ,
			tipConfig: {
				cls: 'autoHeight',
				bodyCls: 'autoHeight',
				width: 150,
				//是否随鼠标滑动
				trackMouse: true,
				//普通Form上必须配置tipType(区分普通Form和行展开的Form)
				tipType: 'normal',
				//当值为空的时候，不显示tip
				isShowByData: true,
				tipBodyElement: 'predeliver.notifyCustomer.tipPanel'
			},columnWidth : .3 } , 
		{name : 'productCode' ,labelWidth:65, fieldLabel : predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.productCode') ,
			tipConfig: {
				cls: 'autoHeight',
				bodyCls: 'autoHeight',
				width: 150,
				//是否随鼠标滑动
				trackMouse: true,
				//普通Form上必须配置tipType(区分普通Form和行展开的Form)
				tipType: 'normal',
				//当值为空的时候，不显示tip
				isShowByData: true,
				tipBodyElement: 'predeliver.notifyCustomer.tipPanel'
			},columnWidth : .3} ,
		{name : 'receiveCustomerContact' , labelWidth:75, fieldLabel : predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.receiveCustomerContact') , columnWidth : .4} ,   
		{name : 'goodsName' , fieldLabel : predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.goodsName') ,
			tipConfig: {
				cls: 'autoHeight',
				bodyCls: 'autoHeight',
				width: 150,
				//是否随鼠标滑动
				trackMouse: true,
				//普通Form上必须配置tipType(区分普通Form和行展开的Form)
				tipType: 'normal',
				//当值为空的时候，不显示tip
				isShowByData: true,
				tipBodyElement: 'predeliver.notifyCustomer.tipPanel'
			},columnWidth : .3} ,  //货物名称
		{name : 'receiveMethod' , labelWidth:65,fieldLabel : predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.receiveMethod') , columnWidth : .3} ,  
		{name : 'receiveCustomerPhone' , labelWidth:75, fieldLabel : predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.receiveCustomerPhone') , columnWidth : .4} ,  
		{name : 'stockStatus' ,labelWidth:65, fieldLabel : predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.stockStatus') , columnWidth : .3} ,  
		{name : 'transportFee' ,labelWidth:65, fieldLabel : predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.transportFee') , columnWidth : .3} ,  
		{name : 'receiveCustomerMobilephone' , labelWidth:75, fieldLabel : predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.receiveCustomerMobilephone') ,
			tipConfig: {
				cls: 'autoHeight',
				bodyCls: 'autoHeight',
				width: 150,
				//是否随鼠标滑动
				trackMouse: true,
				//普通Form上必须配置tipType(区分普通Form和行展开的Form)
				tipType: 'normal',
				//当值为空的时候，不显示tip
				isShowByData: true,
				tipBodyElement: 'predeliver.notifyCustomer.tipPanel'
			},columnWidth : .4} ,
		
		{name : 'goodsQtyTotal' ,labelWidth:65, fieldLabel : predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.goodsQtyTotal') , columnWidth : .3} ,  
		{name : 'deliveryGoodsFee' ,labelWidth:65, fieldLabel : predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.deliveryGoodsFee') , columnWidth : .3} ,
		{name : 'receiveCustomerAddress' , fieldLabel : predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.receiveCustomerAddress') ,
			tipConfig: {
				cls: 'autoHeight',
				bodyCls: 'autoHeight',
				width: 150,
				//是否随鼠标滑动
				trackMouse: true,
				//普通Form上必须配置tipType(区分普通Form和行展开的Form)
				tipType: 'normal',
				//当值为空的时候，不显示tip
				isShowByData: true,
				tipBodyElement: 'predeliver.notifyCustomer.tipPanel'
			},columnWidth : .4 ,height:95,labelWidth:65, xtype : 'textarea'} ,  // 送货地址
		{name : 'arriveGoodsQty' , labelWidth:75,fieldLabel : predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.arriveGoodsQty') , columnWidth : .3} ,  // 到达件数
		{name : 'otherFee' ,labelWidth:65, fieldLabel : predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.otherFee') , columnWidth : .3} ,  //其他费用
		
		{name : 'stockQty' , fieldLabel : predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.stockQty') , columnWidth : .3} ,  //'库存件数'
		{name : 'insuranceAmount' ,labelWidth:65, fieldLabel : predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.insuranceAmount') , columnWidth : .3} ,  //'货物价值'
		{name : 'storageDay' , fieldLabel : predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.storageDay') , columnWidth : .3} ,  
		
		{name : 'insuranceFee' ,labelWidth:65, fieldLabel : predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.insuranceFee') , columnWidth : .3} ,  //  保价费
		{name : 'goodsWeightTotal' , labelWidth:80, fieldLabel : predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.weight.kg') , columnWidth : .3} , //'重量(千克)'
		{name : 'codAmount' , labelWidth:65,fieldLabel : predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.codAmount') , columnWidth : .3} ,  //'代收货款'

		{name : 'deliveryCustomerContact' , labelWidth:55,fieldLabel : predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.deliveryCustomerName') , columnWidth : .4} ,  //'发货人'
		{name : 'goodsVolumeTotal', labelWidth:85 , fieldLabel : predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.volume.stere') , columnWidth : .3} ,  //体积(立方米)
		{name : 'toPayAmount' ,labelWidth:65, fieldLabel : predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.toPayAmount') , columnWidth : .3} ,  //到付金额
		{name : 'toPayAmountHidden' , fieldLabel : '' ,labelSeparator:'',hidden:true} ,  //到付金额隐藏
		
		{name : 'deliveryCustomerPhone' , fieldLabel :'发货人电话' , labelWidth:75, columnWidth : .4} ,  
		{name : 'goodsPackage' , fieldLabel : predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.goodsPackage'), columnWidth : .3} , //'包装'
		{name : 'storageCharge' ,fieldLabel : predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.custodial.fee') , columnWidth : .3} ,//'保管费'  
		
		{name : 'deliveryCustomerMobilephone' , fieldLabel :'发货人手机' , labelWidth:75,
			tipConfig: {
				cls: 'autoHeight',
				bodyCls: 'autoHeight',
				width: 150,
				//是否随鼠标滑动
				trackMouse: true,
				//普通Form上必须配置tipType(区分普通Form和行展开的Form)
				tipType: 'normal',
				//当值为空的时候，不显示tip
				isShowByData: true,
				tipBodyElement: 'predeliver.notifyCustomer.tipPanel'
			},columnWidth : .4} ,
		{name : 'goodsSize', labelWidth:80 , fieldLabel : predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.goodsSize.cm') , columnWidth : .3} ,  //'尺寸(厘米)'
		
		{name : 'paidMethod' ,labelWidth:65, fieldLabel : predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.paidMethod') , columnWidth : .3},  //'付款方式'
		
		{name : 'notificationTime' , xtype: 'datefield', format : 'Y-m-d', fieldLabel : predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.notificationTime') ,labelWidth:85, columnWidth : .4},//'上次通知日期'
		{name : 'transportationRemark' ,fieldLabel : predeliver.notifyCustomer.i18n('pkp.predeliver.beforeNotice.transportationRemark') , columnWidth : .6,tipConfig: {
			cls: 'autoHeight',
			bodyCls: 'autoHeight',
			width: 150,
			//是否随鼠标滑动
			trackMouse: true,
			//普通Form上必须配置tipType(区分普通Form和行展开的Form)
			tipType: 'normal',
			//当值为空的时候，不显示tip
			isShowByData: true,
			tipBodyElement: 'predeliver.notifyCustomer.tipPanel'
		}}  //储运事项
	]	
});
Ext.define('Foss.predeliver.noticeCustomer.HistoryPanelGrid', {
	extend : 'Ext.grid.Panel',
	title : predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.history.notify'),//'历史通知'
	height : 315,
	store : null,
	//frame: true, //tab 如果加上，会把标题历史通知标题覆盖
	//stripeRows : true,
	collapsible : true,
	viewConfig: {
		// 单元格可复制
        enableTextSelection: true
    },
	selType : 'rowmodel',
	columns : [{
		text : predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.notifyTime'),//时间
		width : 140,
		dataIndex : 'operateTime',
		xtype : 'datecolumn',
		format : 'Y-m-d H:i:s',
		xtype : 'ellipsiscolumn'
	}, {
		text : predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.consignee'),//收货人
		width : 60,
		dataIndex : 'consignee',
		xtype : 'ellipsiscolumn'
	}, {
		text : predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.consigneeMobilephone'),// 手机号
		width : 100,
		dataIndex : 'mobile',
		xtype : 'ellipsiscolumn'
	}, {
		text : predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.receiveMethod'),//派送方式
		width : 70,
		dataIndex : 'deliverType'
	}, {
		text : predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.notificationMethod'),//'通知方式'
		width : 70,
		dataIndex : 'noticeType',
		renderer:function(value){
    		return FossDataDictionary.rendererSubmitToDisplay(value, predeliver.NOTIFY_TYPE);
		}
	}, {
		text : predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.noticeResult'),// 通知结果
		width : 80,
		dataIndex : 'noticeResult',
		renderer:function(value){
    		return FossDataDictionary.rendererSubmitToDisplay(value, predeliver.NOTIFY_STATUS);
		}
	}, {
		text : predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.contact'),//联系人
		width : 60,
		dataIndex : 'operator',
		xtype : 'ellipsiscolumn'
	},{
		text :'预计送货日期',//预计送货日期
		width : 140,
		renderer:function(value){
			if(value!=null){
				return value.substring(0,10);
			}else{
				return null;
			}
		},
		dataIndex : 'deliverDate',
		xtype : 'ellipsiscolumn'
	},{
		text : predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.deliverRequire'),//送货要求
		width : 70,
		dataIndex : 'deliverRequire',
		xtype : 'ellipsiscolumn'
	},{
		text : predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.smallTicketFee'),//小票费用
		width : 70,
		dataIndex : 'smallTicketFee',
		xtype : 'ellipsiscolumn'
	}, {
		text : predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.messageInfo'),//'短信内容'
		width : 200,
		dataIndex : 'noticeContent',
		xtype : 'ellipsiscolumn'
	}, {
		text : predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.exceptionRemark'),//'异常备注'
		width : 100,
		dataIndex : 'exceptionNotes',
		xtype : 'ellipsiscolumn'
	}],
	constructor : function(config) {
		var me = this;
		Ext.apply(this, config);
		me.store = Ext.create('Ext.data.Store', {
			model: 'Foss.predeliver.model.HistoryPanel',
			data: []
		});
		this.callParent(arguments);
	}
});
/**
 * 设置发票相关信息的显示方式
 * 
 */
//predeliver.notifyCustomer.setInvoice = function(form, isVisible) {
//	var taxNo = form.findField('taxNo'), companyName = form.findField('companyName'), tel = form.findField('tel'),
//		account = form.findField('account'), bank = form.findField('bank'), address = form.findField('address');
	// ISSUE-3178 取消通知客户，选择需要发票时的必填项
	// 税号
//	taxNo.setVisible(isVisible);
//	taxNo.allowBlank = !isVisible;
	// 公司名称
//	companyName.setVisible(isVisible);
//	companyName.allowBlank = !isVisible;
	// 电话
//	tel.setVisible(isVisible);
//	tel.allowBlank = !isVisible;
	// 帐号
//	account.setVisible(isVisible);
//	account.allowBlank = !isVisible;
	// 开户行
//	bank.setVisible(isVisible);
//	bank.allowBlank = !isVisible;
	// 地址
//	address.setVisible(isVisible);
//	address.allowBlank = !isVisible;
    
//}

var not_habit_deliveryTimeInterval;
var not_habit_deliveryTimeStart;
var not_habit_deliveryTimeOver;
var not_habit_invoiceType;
var not_habit_invoiceDetail;
var not_habit_receiptHabitRemark;
//通知信息   右一
Ext.define('Foss.predeliver.noticeCustomer.NoticeInfoFormPanel',{
	extend: 'Ext.form.Panel',
	title : predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.noticeResultInformation'),
	//收缩
	collapsible: true,
	itemId : 'noticeInfoFormPanel_Id',
	//是否有框
	frame:true,
	//动画收缩
	animCollapse: true,
	defaults: {
		margin:'2 0 2 0',
		labelWidth:100
	},
	layout:'column',
	defaultType : 'textfield',
	viewNoticeTypeInfo : function(formPanel) {
		var form = formPanel.getForm();
		// 通知方式radio变更时
		var noticeType = form.findField('noticeType').getGroupValue(), isVisible = false, 
			paidMethod = FossDataDictionary.rendererDisplayToSubmit(form.findField('paidMethod').getValue(), 'CLEARING_TYPE'),
			receiveMethod = FossDataDictionary.rendererDisplayToSubmit(form.findField('receiveMethod').getValue(),'PICKUPGOODSHIGHWAYS'),//通知方式（语音，短信，电话）306548
			toPayAmount = form.findField('toPayAmount').getValue();
		if (noticeType == "TEL_NOTICE") {
			isVisible = true;
		} else {
			isVisible = false;
		}
		// 选择语音、短信通知方式时，通知状态、付款方式、是否需要发票、是否必须送货、送货日期、送货要求、小票费用、税号、公司名称、电话、账号、开户行、地址控件不可见
		
		form.findField('noticeResultName').setVisible(isVisible);
		form.findField('paymentTypeName').setVisible(isVisible);
		form.findField('isSentRequiredName').setVisible(isVisible);
		form.findField('deliverDate').setVisible(isVisible);
		form.findField('smallTicketFee').setVisible(isVisible);
		form.findField('deliveryTimeInterval').setVisible(isVisible);
		
		form.findField('deliveryTimeStart').setVisible(isVisible);
		form.findField('deliveryTimeOver').setVisible(isVisible);
		//form.findField('actualProvN').setVisible(isVisible);
		form.findField('actualStreetn').setVisible(isVisible);
		form.findField('actualAddressDetail').setVisible(isVisible);
		form.findField('isException').setVisible(isVisible);
		form.findField('exceptionReason').setVisible(isVisible);
		form.findField('invoiceType').setVisible(isVisible);
		form.findField('isExhibition').setVisible(isVisible);
		form.findField('isEmptyCar').setVisible(isVisible);
		form.findField('invoiceDetail').setVisible(isVisible);
		Ext.getCmp('address_Query_ID').setVisible(isVisible);
		Ext.getCmp('actualAddress_ID').setVisible(isVisible);
		Ext.getCmp('notify_ReceiptHabit_ID').setVisible(isVisible);
		form.findField('receiptHabitRemark').setVisible(isVisible); 
		formPanel.down('panel').setVisible(isVisible); 
	
		if (toPayAmount > 0) {
			// ibm-wangfei 付款方式验证修改为到付金额验证 2013-2-2 15:45:31
			// 付款方式不等于到付时，隐藏付款方式
			form.findField('paymentTypeName').setVisible(isVisible);
		}
		//306548派送方式为送货的运单，短信功能置灰
		if(receiveMethod == 'DELIVER_NOUP'||receiveMethod == 'DELIVER_UP'||receiveMethod == 'DELIVER'||receiveMethod == 'DELIVER_INGA'){
			form.findField('noticeTypeName').getComponent('SMS_NOTICE').setDisabled(true);
		}
	},
	viewNoticeContent : function(form, controlName) {
		// 通知方式radio变更时，如果是短信、语音，自动获取模版对应内容，并不可编辑
		var noticeType = form.findField('noticeType').getGroupValue(), notificationEntity = '',
			isStorageChargeCheck = form.findField('isStorageCharge').getSubmitData(), notificationEntity = '',
			estimatedPickupTime = form.findField('estimatedPickupTime').getValue(),
			estimatedPickupTimeH = form.findField('estimatedPickupTimeH'), isStorageCharge = '', newVo = '',
			noticeContentVoice = form.findField('noticeContentVoice');
			
		if (controlName == 'estimatedPickupTime') {
			// 判断输入小时，如果大于99，不处理
			if (estimatedPickupTime > 99) {
				return;
			}
			if (estimatedPickupTime == null) {
				estimatedPickupTime = '';
			}
			// 判断现在输入的值和未变更前的值是否相等
			if (estimatedPickupTimeH.getValue() == estimatedPickupTime) {
				return;
			}
			estimatedPickupTimeH.setValue(estimatedPickupTime);
		}
		if (noticeType == "TEL_NOTICE") {
			form.findField('noticeContent').setReadOnly(false);// 设置只读属性为false
			form.findField('noticeResultName').setValue({'noticeResult' : 'SUCCESS'});
			form.findField('noticeContent').setValue('通知成功！'); // 设置通知内容为默认值：“通知成功！”
			//form.findField('noticeContent').setHeight(64, true);// 恢复通知内容多行文本框高度设置
			return;
		} else {
			form.findField('noticeContent').setReadOnly(true);
		}
		// 是否征收保管费
		if (isStorageChargeCheck != null) {
			isStorageCharge = isStorageChargeCheck.isStorageCharge;
		} else {
			isStorageCharge = '';
		}
		notificationEntity = { 
			'waybillNo' : form.findField('waybillNo').getValue(),
			'noticeType' : noticeType,
			'isStorageCharge' : isStorageCharge,
			'estimatedPickupTime' : estimatedPickupTime,
			'arriveGoodsQty' : form.findField('arriveGoodsQty').getValue()
		};
		newVo = {
			'vo': {
				'conditionDto' : {'notificationEntity' :notificationEntity}
			}
		};
		form.findField('noticeContentVoice').setValue('');
		form.findField('noticeContent').setValue('');
		//form.findField('noticeContent').inputEl.setHeight(230, true);
		//form.findField('noticeContent').inputEl.setWidth(120, true);
		
		Ext.Ajax.request({
		    // url: '../predeliver/queryNoticeContent.action',
		    url:predeliver.realPath('queryNoticeContent.action'),
		    jsonData: newVo,
		    success: function(response){
		    	var result = Ext.decode(response.responseText);
		    	// 设置通知内容的值、高度和只读属性
		    //	form.findField('noticeContent').inputEl.setHeight(230, true);
			//	form.findField('noticeContent').inputEl.setWidth(120, true);
		    	form.findField('noticeContent').setValue(result.vo.conditionDto.notificationEntity.noticeContent);
		    	form.findField('noticeContentVoice').setValue(result.vo.conditionDto.notificationEntity.noticeContentVoice);
		    },
		    exception: function(response) {
                var json = Ext.decode(response.responseText);
                Ext.ux.Toast.msg(predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.change.notification.method.fails'), json.message, 'error', 2000);//'变更通知方式失败'
            }
		});
	},
	viewNoticeResult : function(form) {
		// 通知结果radio变更时
		var noticeResult = form.findField('noticeResult').getGroupValue();
		if (noticeResult == predeliver.FAILURE) {
			form.findField('noticeContent').setValue('');
			form.findField('noticeContentVoice').setValue('');
		} else {
			form.findField('noticeContent').setValue(predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.noticeSuccess'));//'通知成功！'
		}
	},
	items : [ {
		name : 'receiveCustomerContact',
		labelWidth:50,
		fieldLabel : predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.contact'),
		readOnly : true,
		columnWidth : .3
	}, {
		name : 'receiveCustomerMobilephone',
		columnWidth : .4, 
		labelWidth:70, 
		fieldLabel : predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.recvisits'),//联系方式
		readOnly : true
	}, {
		name : 'customerQulification',
		columnWidth : .3, 
		labelWidth:70, 
		fieldLabel : predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.customerQulification'), 
		readOnly : true, 
		setValue : function(value) {
			this.setRawValue(FossDataDictionary.rendererSubmitToDisplay(value, predeliver.CLEARING_TYPE));
		}
	}, {
		name : 'creditAmount', 
		fieldLabel : predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.creditLimit'), //可用信用额度
		readOnly : true,
		columnWidth : .3
	}, {//通知方式
		xtype : 'radiogroup',
		labelWidth:70, 
		columnWidth : .7,
		vertical : true,
		fieldLabel : predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.notificationMethod'), 
		name : 'noticeTypeName', 
		listeners : { 
			change:function(_this, newValue, oldValue, eOpts){
				_this.up('panel').viewNoticeTypeInfo(_this.up('form'));
				_this.up('panel').viewNoticeContent(_this.up('form').getForm(), '');
			}
		},
		items : [ {// 语音
			boxLabel : predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.voice'), 
			name : 'noticeType' , 
			inputValue : 'VOICE_NOTICE' , 
			margin : '5 0 0 0',
			itemId:'VOICE_NOTICE'
		}, { // 短信
			boxLabel : predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.sms'), 
			name : 'noticeType' , 
			inputValue : 'SMS_NOTICE' ,
			margin : '5 0 0 0',
			itemId:'SMS_NOTICE'
		}, {// 电话
			boxLabel : predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.tel'),
			name : 'noticeType' , 
			inputValue : 'TEL_NOTICE',
			margin : '5 0 0 0',
			itemId:'TEL_NOTICE' , 
			checked : true
		} ]
	}, {
		xtype : 'container',  
		layout : 'hbox',
		columnWidth : 1,
		items : [ {
			xtype : 'checkbox',
			margin: '0 0 0 10px',
			name : 'isStorageCharge',
			boxLabel : predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.whether.levy.custodial.fees'), 
			inputValue : 'Y',
			listeners : { //是否征收保管费
				change:function(_this, newValue, oldValue, eOpts){
					this.up('panel').viewNoticeContent(this.up('form').getForm(), '');
				}
			}
		}, {
			xtype : 'numberfield', 
			labelSeparator : '',
			name : 'estimatedPickupTime', 
			width : 70, 
			labelWidth : 32,
			margin: '0 0 0 70px',
			fieldLabel : predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.estimate'), // '预计'
			hideTrigger : true, 
			maxValue: 999, 
			minValue: 1,
			allowDecimals : false, 
			msgTarget: 'qtip',
			listeners : { 
				blur : function (_this, The, eOpts) {
					this.up('panel').viewNoticeContent(this.up('form').getForm(), 'estimatedPickupTime');
				}
			}
		}, {
			xtype : 'label', 
			text : predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.hours.after.delivery'),// '小时后提货'
			margin: '4 0 0 4'
		}, {
			name : 'smallTicketFee',
			margin: '0 0 0 110px',
			labelWidth:60,
			width : 180, 
			fieldLabel : predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.smallTicketFee'),//小票费用
			xtype: 'numberfield',
			hideTrigger : true,
			maxValue: 99999999,
			minValue: 1, 
			allowDecimals : false 
		} ]
	}, {
		xtype : 'panel',
		columnWidth: 1,
		collapsed : true,
		collapsible : true,
		title : '实际收货地址(如需修改地址，请点击右边箭头展开)',
		height : 75,
		layout:'column',
		items : [ {
			xtype : 'container',
			columnWidth: 0.9,
			layout:'column',
			items : [ {
				fieldLabel : ' ',
				columnWidth: 1,
				allowBlank:true,
				labelSeparator : '',
				id:'actualAddress_ID',
				labelWidth:85,
				provinceWidth : 151,
				cityWidth : 151,
				cityLabel : '',
				cityName : 'cityName',//名称
				provinceLabel : '',
				provinceName:'privateName',//省名称
				areaLabel : '',
				areaName : 'areaName',// 县名称
				areaWidth : 151,
				areaIsBlank : true,
				cityIsBlank : true,
				provinceIsBlank : true,
				type : 'P-C-C',
				xtype : 'linkregincombselector'
			
			}, {
				xtype : 'textfield',
				name: 'actualAddressDetail',//实际收货地址
				columnWidth: .55,
				labelWidth:0,
				maxLength:166,
				margin: '0 0 0 90',
				fieldLabel : '',
				labelSeparator:''
			
			}, {
				xtype : 'textfield',
				name: 'actualStreetn',//实际收货详细地址
				columnWidth: .445,
				labelWidth:0,
				margin: '0 0 0 0',
				fieldLabel : '',
				labelWidth:100,
				labelSeparator:'',
				maxLength:166,
				emptyText : '地址备注(XX旁边,XX路口等)非必填'
			} ]
		}, {
			xtype : 'button',
			id: 'address_Query_ID',
			text : '查询',
			name: 'btnQueryAddress',
			height : 45,
			margin: '2 0 0 0',
			columnWidth : 0.1,
			handler: function(btnQueryAddress) {
				var obj = {};
				if (btnQueryAddress.up('form').down('textfield[name=receiveCustomerContact]').getValue()) {
					obj['customerContactName'] = btnQueryAddress.up('form').down('textfield[name=receiveCustomerContact]').getValue();
					obj['customerCode'] = btnQueryAddress.up('form').down('hiddenfield[name=receiveCustomerCode]').getValue();
					obj['customerName'] = btnQueryAddress.up('form').down('hiddenfield[name=receiveCustomerName]').getValue();
					obj['customerPhone'] = btnQueryAddress.up('form').down('hiddenfield[name=receiveCustomerPhone]').getValue();
					obj['customerMobilePhone'] = btnQueryAddress.up('form').down('textfield[name=receiveCustomerMobilephone]').getValue();
					addPrev(obj,'customerReceiptAddressEntity');
					Ext.create('Foss.predeliver.noticeCustomer.ReceiptAddressWindow').show();
					receiptAddressStore.proxy.extraParams = obj;
					receiptAddressStore.load();
				}
			}
		
		} ]
	}, {
		name : 'noticeContent' ,
		columnWidth : 1,
		labelWidth:85, 
		fieldLabel : predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.contentsNotice.or.reasonsFailure') ,
		allowBlank : false, //'通知内容<br>(失败原因)'
		xtype : 'textarea', 
		value : predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.noticeSuccess'), // '通知成功！'
		maxLength : 500,
		blankText : predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.contentsNotice.or.reasonsFailure.NotEmpty'),//'通知内容(失败原因)不能为空'
		listeners : { 
			focus:function(_this, The, eOpts){
				var noticeResult = _this.up('form').getForm().findField('noticeResult').getGroupValue();
				if (noticeResult == predeliver.SUCCESS && _this.getValue() == predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.noticeSuccess')) {//'通知成功！'
					_this.setValue('');
				}
			}
		}
	}, {
		name : 'deliverDate',
		xtype: 'datefield',
		columnWidth : .5,
		fieldStyle: 'color:red;font-weight:bold;',
		labelWidth:85, 
		format : 'Y-m-d',
		editable:false,
		minValue: new Date(),
		fieldLabel : predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.delivery.date') //'预计送货日期' 
	}, {
		xtype : 'combobox',
		fieldLabel : '送货时间段',//送货时间段
		name: 'deliveryTimeInterval',
		columnWidth: .5,
		labelWidth:85,
		displayField: 'name',
		valueField: 'name',
		value: '全天',
		triggerAction: 'all',
		store: Ext.create('Ext.data.Store',{
			fields : ['name'],
			data : [ {
				name : '全天'
			}, {
				name : '上午'
			}, {
				name : '下午'
			} ]
		}),
		editable: false,
		mode: 'local'
	}, {
		xtype : 'container',
		columnWidth : .5,
		layout : 'hbox',
		items : [ {
			xtype: 'timefield',
		    name: 'deliveryTimeStart',
		    labelWidth : 85,
		    width : 175,
			editable:true,
		    format : 'H:i',
		    fieldLabel: '送货时间点',
		    increment: 30,
		    submitFormat: 'H:i',
			listeners : {
				'blur' : function (timefield) {
					var val = timefield.getValue();
					if (val) {
						timefield.nextSibling().allowBlank = false;
					} else if (!val && !timefield.nextSibling().getValue()) {
						timefield.reset();
						timefield.nextSibling().reset();
						timefield.allowBlank = true;
						timefield.nextSibling().allowBlank = true;
					} else {
						timefield.nextSibling().allowBlank = true;

					}
				},
	        	 select : function(combo, records, eOpts) {
	        		 var val = combo.getValue() ;
	        		 if (val) {
	        			 combo.nextSibling().setMinValue(val);
	        		 }
	        	 }
			}
		}, {
			xtype: 'timefield',
		    name: 'deliveryTimeOver',
		    format : 'H:i',
		    fieldLabel: '至',
		    labelWidth : 20,
		    width : 110,
			editable:true,
		    labelSeparator: '',
		    increment: 30,
		    submitFormat: 'H:i',
			listeners : {
				'blur' : function (timefield) {
					var val = timefield.getValue();
					if (val) {
						timefield.previousSibling().allowBlank = false;
					} else if (!val && !timefield.previousSibling().getValue()) {
						timefield.reset();
						timefield.previousSibling().reset();
						timefield.allowBlank = true;
						timefield.previousSibling().allowBlank = true;
					} else {
						timefield.previousSibling().allowBlank = true;
					}
				},
	        	 select : function(combo, records, eOpts) {
	        		 var val = combo.getValue() ;
	        		 if (val) {
	        			 combo.previousSibling().setMaxValue(val);
	        		 }
	        	 }
			}
		} ]
	}, {
		xtype : 'radiogroup',
		labelWidth:80,
		columnWidth : .45,
		name : 'isSentRequiredName', 
		defaultType:'radio',
		fieldLabel : predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.whether.will.delivery'),//'是否必送货'
		items : [ {
			boxLabel : predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.yes'),
			name : 'isSentRequired',//'是'
			margin : '5px 10px 0 0',
			inputValue : 'Y'
		}, {
			boxLabel : predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.no'),
			name : 'isSentRequired',//'否'
			inputValue : 'N',
			margin : '5px 10px 0 0',
			checked : true
		} ]
	}, {
		xtype : 'container',
		columnWidth : 1,
		layout : 'column',
		items : [ {
			xtype : 'radiogroup',
			labelWidth:85,
			columnWidth : 0.5,
			name : 'noticeResultName', 
			fieldLabel : predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.noticeStatus'),//通知状态
			listeners : { // '通知状态'
				change:function(_this, newValue, oldValue, eOpts){
					_this.up('panel').viewNoticeResult(_this.up('form').getForm());
					var form = _this.up('form').getForm();
					if ('SUCCESS' == newValue.noticeResult) {
						form.findField('isException').setDisabled(false);
						form.findField('exceptionReason').setDisabled(true);
					} else {
						form.setValues({exceptionReason: ''});
						form.setValues({isException: ''});
						form.findField('isException').setDisabled(true);
						form.findField('exceptionReason').setDisabled(true);
					}
				}
			},
			items : [ {
				boxLabel : predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.success'),//'通知成功'
				name : 'noticeResult',
				inputValue : 'SUCCESS', 
				margin : '5px 0 0 10px',
				checked : true
			}, {
				boxLabel : predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.failuer'),//'通知失败'
				name : 'noticeResult',
				inputValue : 'FAILURE',
				margin : '5px 0 0 10px'
			} ]
		}, {
			xtype : 'checkbox',
			name: 'isExhibition',
			boxLabel : '会展货', //会展货
			margin: '5px 0 0 10px',
			columnWidth : 0.15,
			listeners : {
				change : function(checkbox, newValue, oldValue, eOpts) {
					var form = checkbox.up('form').getForm();
					if (newValue) {
						form.findField('isEmptyCar').setDisabled(false);
					} else {
						form.findField('isEmptyCar').reset();
						form.findField('isEmptyCar').setDisabled(true);
					}
				}
			}
		}, {
			xtype : 'checkbox',
			name: 'isEmptyCar',
			boxLabel : '空车出',
			disabled: true,
			inputValue : 'Y',
			columnWidth : 0.2,
			margin: '5px 0 0 30px'
		} ]
	}, {
		xtype : 'checkbox',
		boxLabel : '是否异常',
		name: 'isException',
		labelWidth : 60,
		margin: '8px 0 0 10px',
		columnWidth : 0.2,
		handler: function() {
			var form = this.up('form').getForm();
			var values = form.getValues();
			if (!values.isException) {
				form.setValues({exceptionReason: ' '});
				form.findField('exceptionReason').setDisabled(true);
			} else {
				form.findField('exceptionReason').setDisabled(false);
			}
		}
	}, {
		xtype : 'combobox',
		fieldLabel : '异常原因',//异常原因
		name: 'exceptionReason', 
		labelWidth : 60,
		disabled: true,
		columnWidth : 0.8,
		displayField: 'valueName',
		valueField: 'valueCode',
		store: FossDataDictionary.getDataDictionaryStore(predeliver.EXCEPTIONREASON, null),
		editable: false
	}, {
		xtype : 'radiogroup',
		vertical : true,
		labelWidth:85,
		columnWidth : 0.5,
		name : 'paymentTypeName',
		fieldLabel : predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.paidMethod'),
		labelStype : {
			width : '70px'
		},
		items : [ {
			boxLabel : predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.freightCollect'),
			name : 'paymentType',//'到付'
			inputValue : 'CH',
			itemId:'CH', 
			checked : true,
			style: {
				marginTop: '5px'
			}
		}, {
			boxLabel : predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.paidByMonth'),
			name : 'paymentType',//'月结'
			inputValue : 'CT', 
			itemId:'CT',  
			disabled : true,
			style: {
				marginTop: '5px'
			}
		}, {
			boxLabel : predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.temporaryDebt'),
			name : 'paymentType',//'临欠'
			inputValue : 'DT', 
			itemId:'DT', 
			disabled : true,
			style: {
				marginTop: '5px'
			}
		} ]
	}, {
		name : 'toPayAmount' ,  
		fieldStyle:'color:red;',
		labelWidth:60,
		columnWidth : 0.5,// 到付总额
		fieldLabel : predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.toTotalPayAmount'), 
		readOnly : true
	}, {
		xtype : 'container',
		columnWidth: 1,
		layout : 'column',
		items : [ {
			xtype : 'combobox',
			fieldLabel : '发票类型',//发票类型
			name: 'invoiceType',
			labelWidth : 85,
			columnWidth: 0.45,
			displayField: 'valueName',
			valueField: 'valueCode',
			store: FossDataDictionary.getDataDictionaryStore(predeliver.INVOICETYPE, null, {
					'valueCode': '', 'valueName':'无'
			}),
			editable: false,
			listeners : {
				blur : function(combobox) {
					var formPanel = combobox.up('form');
					var noticeContentField = formPanel.down('textarea[name=noticeContent]');
					var noticeContentValue = noticeContentField.getValue();
					if (combobox.getRawValue()) {
						noticeContentField.setValue(noticeContentValue + '【' + combobox.getRawValue() + '】');
					} else {
						noticeContentField.setValue(noticeContentValue);
					}
				}
			}
		}, {
			fieldLabel : '发票备注',
			labelWidth : 60,
			xtype : 'textfield',
			margin: '0 0 0 30px',
			name: 'invoiceDetail',
			columnWidth: 0.55,
			maxLength: 30,
			maxLengthText: '已超出字数最大限制!',
			listeners : {
				blur : function(text, newValue, oldValue, eOpts ) {
					if (!text.isValid()) {
						return ;
					}
					var formPanel = text.up('form');
					var invoiceTypeField = formPanel.down('combobox[name=invoiceType]');
					var noticeContentField = formPanel.down('textarea[name=noticeContent]');
					var noticeContentValue = noticeContentField.getValue();
					if (text.getValue()) {
						noticeContentField.setValue(noticeContentValue + '【' + text.getValue() + '】');
					}
					
				}
			}
		} ]
	}, {
		xtype : 'textarea',
		height:50,
		name: 'receiptHabitRemark',
		fieldLabel : '收货习惯备注',//收货习惯备注
		readOnlyCls:'.x-form-readonly input, .x-form-readonly textarea {background: transparent!important;}',
		maxLength : 200,
		readOnly: true,
		columnWidth: 1,			
		labelWidth:85
	},
	{name : 'waybillNo' , xtype : 'hiddenfield'} , 
	{name : 'receiveMethod' , xtype : 'hiddenfield'},
	{name : 'receiveCustomerCode' , xtype : 'hiddenfield'},
	{name : 'createOrgCode' , xtype : 'hiddenfield'},
	{name : 'estimatedPickupTimeH' , xtype : 'hiddenfield'},
	{name : 'noticeContentVoice' , xtype : 'hiddenfield'},
	{name : 'paidMethod' , xtype : 'hiddenfield'},
	{name : 'isBackFlg' , xtype : 'hiddenfield'},
	{name : 'arriveGoodsQty' , xtype : 'hiddenfield' } , 
	{name : 'receiveCustomerName' , xtype : 'hiddenfield'},
	{name : 'receiveCustomerPhone' , xtype : 'hiddenfield'},
	{name : 'taskStatus' , xtype : 'hiddenfield'},
	{name : 'specialValueAddedService' ,xtype : 'hiddenfield' }
	],
	constructor: function(config){ 
		var me = this,
			cfg = Ext.apply({}, config);
		me.bbar = [{
			  text : '收货习惯',  //收货习惯按钮
			  id:'notify_ReceiptHabit_ID',
		      handler :  function(btn) {
							var win = Ext.create('Foss.predeliver.beforeNotice.AddReceiptHabitWindow').show();
							var receiveCustomerContact = btn.up('form').down('textfield[name=receiveCustomerContact]').getValue();
							var receiveCustomerCode = btn.up('form').down('hiddenfield[name=receiveCustomerCode]').getValue();
							var receiveCustomerName = btn.up('form').down('hiddenfield[name=receiveCustomerName]').getValue();
							var receiveCustomerPhone = btn.up('form').down('hiddenfield[name=receiveCustomerPhone]').getValue();
							var receiveCustomerMobilephone = btn.up('form').down('textfield[name=receiveCustomerMobilephone]').getValue();
							//var deliveryTimeInterval = btn.up('form').down('combobox[name=deliveryTimeInterval]').getValue();
							//var deliveryTimeStart = btn.up('form').getForm().findField('deliveryTimeStart').getRawValue();
							//var deliveryTimeOver = btn.up('form').getForm().findField('deliveryTimeOver').getRawValue();
							//var invoiceType = btn.up('form').down('combobox[name=invoiceType]').getValue();
							//var invoiceDetail = btn.up('form').down('textfield[name=invoiceDetail]').getValue();
							//var receiptHabitRemark = btn.up('form').down('textarea[name=receiptHabitRemark]').getValue();
							if (receiveCustomerPhone == receiveCustomerMobilephone) {
								receiveCustomerMobilephone = '';
							}
							win.down('textfield[name=customerMobilePhone]').setValue(receiveCustomerMobilephone);
							win.down('textfield[name=customerContactName]').setValue(receiveCustomerContact);
							win.down('textfield[name=customerPhone]').setValue(receiveCustomerPhone);
							win.down('textfield[name=customerName]').setValue(receiveCustomerName);
							win.down('hidden[name=customerCode]').setValue(receiveCustomerCode);
							if (not_habit_deliveryTimeInterval) {
								win.down('textfield[name=deliveryTimeInterval]').setValue(not_habit_deliveryTimeInterval);
							}
							if (not_habit_deliveryTimeStart) {
								win.down('timefield[name=deliveryTimeStart]').setValue(not_habit_deliveryTimeStart);
							}
							if (not_habit_deliveryTimeOver) {
								win.down('timefield[name=deliveryTimeOver]').setValue(not_habit_deliveryTimeOver);
							}
							if (not_habit_invoiceType) {
								win.down('combobox[name=invoiceType]').setValue(not_habit_invoiceType);
							}
							if (not_habit_invoiceDetail) {
								win.down('textfield[name=invoiceDetail]').setValue(not_habit_invoiceDetail);
							}
							if (not_habit_receiptHabitRemark) {
								win.down('textarea[name=receiptHabitRemark]').setValue(not_habit_receiptHabitRemark);
							}
		      }
		},'->',{
			text : predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.save'),//'保存'
			disabled:!predeliver.notifyCustomer.isPermission('notifycustomerindex/notifycustomerindexsavebutton'),
			hidden:!predeliver.notifyCustomer.isPermission('notifycustomerindex/notifycustomerindexsavebutton'),
			id:'Foss_predeliver_notifyCustomer_NoticeInfoFormPanel_saveButten_Id',
			plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin', {
				seconds: 3
			}),
			handler : function() {
				var form = this.up('form').getForm(), values = form.getValues();
				var recordsModel = Ext.getCmp('Foss_predeliver_grid_NoticeCustomerGrid_Id').getSelectionModel(),
					records=recordsModel.getSelection(),
					noticeInfoForm   = Ext.getCmp('Foss.predeliver.noticeCustomer.TransportInfoFormPanel_id').getForm();
				if(records.length!=1){
	    			Ext.ux.Toast.msg(predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.tip')/*提示*/, predeliver.notifyCustomer.i18n('pkp.predeliver.beforeNotice.has.choose.one'), 'error', 1000);//请选择一条运单信息
	    			return;
	    		}
				if(noticeInfoForm.findField('waybillNo').getValue() != records[0].data['waybillNo']){
	    			Ext.ux.Toast.msg(predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.tip')/*提示*/,'当前运单加载的信息与选中的运单信息不一致，请重新选择', 'error', 2000);//当前运单加载的信息与选中的运单信息不一致，请重新选择
	    			return;
	    		}
			/*	if (values.isNeedInvoice == 'Y' && values.isBackFlg == 'Y') {
					// 添加合同客户的发票必须选择的验证wo
					if (Ext.isEmpty(values.taxNo) && Ext.isEmpty(values.companyName) &&  Ext.isEmpty(values.tel) &&
						Ext.isEmpty(values.account) && Ext.isEmpty(values.bank) &&  Ext.isEmpty(values.address)) {
						Ext.ux.Toast.msg(predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.tip')提示, predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.contract.customers.must.select.bank'), 'error', 2000);//'合同客户必须选择银行信息'
						return;
					}
				}*/
				if (values.noticeType == "TEL_NOTICE" && Ext.isEmpty(values.noticeResult)) {
					// BUG-32289  通知客户页面选择短信通知总是提示'通知状态必须选择'
					// 添加验证条件，只有在电话通知的时候进行验证
					// 通知结果为空
					Ext.ux.Toast.msg(predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.tip')/*提示*/, '通知状态必须选择', 'error', 2000);//'合同客户必须选择银行信息'
					return;
				}
				if (Ext.isEmpty(values.noticeContent)) {
					Ext.ux.Toast.msg(predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.tip')/*提示*/, predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.mest.have.notify.info'), 'error', 2000);//'请录入/生成通知内容再进行保存。'
					return;
				}
				if (form.isValid()) {
					this.up('form').query('button')[0].setDisabled(false);//设置提交按钮可编辑
					var array = {'vo':{'conditionDto':{'notificationEntity':values,'invoiceInfomationEntity':values, 
																						 'notifyCustomerDto':{'receiveCustomerName':'','receiveCustomerPhone':''}}}};
					// 语音通知中或短信通知中，不可再次通知，需等待结果后再次通知
					if(values.noticeType != 'TEL_NOTICE' 
						&& (records[0].data['noticeResult'] == 'VOICE_NOTICING' 
							||records[0].data['noticeResult'] == 'SMS_NOTICING')) {
						Ext.ux.Toast.msg(predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.tip')/*提示*/, predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.notAllowNotice'), 'ok', 1000);//'通知中，不可再次通知，请重新查询获取通知结果'
						return ;
					}
					// 通知成功且不是电话通知，今天不能再次通知
					if(values.noticeType == 'VOICE_NOTICE' 
						&&  records[0].data['isVoiceSuccess'] == 'Y' 
						&& predeliver.notifyCustomer.compareDate(records[0].data['notificationTime'],new Date()) == 0){
						Ext.ux.Toast.msg(predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.tip')/*提示*/, predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.cantNotice'), 'ok', 1000);//通知成功且不是电话通知，今天不能再次通知
						return ;
					}
					// 通知成功且不是电话通知，今天不能再次通知
					if(values.noticeType == 'SMS_NOTICE' 
						&& records[0].data['isSmsSuccess'] == 'Y' 
						&& predeliver.notifyCustomer.compareDate(records[0].data['notificationTime'],new Date()) == 0){
						Ext.ux.Toast.msg(predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.tip')/*提示*/, predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.cantNotice'), 'ok', 1000);//通知成功且不是电话通知，今天不能再次通知
						return ;
					}
					
					//转换到付总额-去除【网上支付（已付）】
					array.vo.conditionDto.invoiceInfomationEntity.toPayAmount = toPayAmountHidden;
					array.vo.conditionDto.notificationEntity.toPayAmount = toPayAmountHidden;
					
					//保存转换实际收货地址
					if (Ext.getCmp('actualAddress_ID').getProvince().value) {
						array.vo.conditionDto.notificationEntity.actualProvCode = Ext.getCmp('actualAddress_ID').getProvince().value;
					}
					if (Ext.getCmp('actualAddress_ID').getCity().value) {
						array.vo.conditionDto.notificationEntity.actualCityCode = Ext.getCmp('actualAddress_ID').getCity().value;
					}
					if (Ext.getCmp('actualAddress_ID').getCounty().value) {
						array.vo.conditionDto.notificationEntity.actualDistrictCode = Ext.getCmp('actualAddress_ID').getCounty().value;
					}
					
					if (!Ext.isEmpty(values.receiveCustomerName)) {
						 array.vo.conditionDto.notifyCustomerDto.receiveCustomerName = values.receiveCustomerName;
					}
					if (!Ext.isEmpty(values.receiveCustomerPhone)) {
						 array.vo.conditionDto.notifyCustomerDto.receiveCustomerPhone = values.receiveCustomerPhone;
					}
					//分批配载未到齐的货物，不进行通知
					if(records[0].data['batchStowageNOtHere'] == 'Y'  && values.noticeType == 'SMS_NOTICE'){
						Ext.ux.Toast.msg(predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.tip')/*提示*/, predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.noticeBatchStowageNOtHere'), 'error', 1000);//'分批配载的货物，在货物到齐后才可短信通知'
						return ;
					}
					//送货时间点限制
					if((!Ext.isEmpty(values.deliveryTimeStart)) &&(!Ext.isEmpty(values.deliveryTimeOver))){
						if(values.deliveryTimeStart>values.deliveryTimeOver){
							 Ext.ux.Toast.msg('提示','最早送货时间点不能大于最晚送货时间点!', 'error', 2000);
							return false;
						}
					}
					Ext.Ajax.request({
					    // url: '../predeliver/addNotifyCustomer.action',
					    url:predeliver.realPath('addNotifyCustomer.action'),
					    jsonData: array,
					    success: function(response){
					    	if (values.noticeType == 'TEL_NOTICE') {
						    	Ext.ux.Toast.msg(predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.tip')/*提示*/, predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.save.success'), 'ok', 1000);//'保存成功！'
						    	records[0].set('noticeResult',values.noticeResult);
								records[0].set('notificationTime',new Date());
					    	} else {
						    	Ext.ux.Toast.msg(predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.tip')/*提示*/, predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.save.success1'), 'ok', 1000);//'保存成功！'
						    	if(values.noticeType == 'VOICE_NOTICE'){
									records[0].set('isVoiceSuccess','Y');
								}
								if(values.noticeType == 'SMS_NOTICE'){
									records[0].set('isSmsSuccess','Y');
								}
								records[0].set('noticeResult','SUCCESS');
								records[0].set('notificationTime',new Date());
								
					    	}
							noticeInfoForm.findField('notificationTime').setValue(Ext.Date.format(new Date(), 'Y-m-d'));
					    	this.up('form').query('button')[2].setDisabled(true);
					        var historyPanelGrid =Ext.getCmp('Foss.predeliver.noticeCustomer.HistoryPanelGrid_id');
					        historyPanelGrid.store.removeAll();
					    	Ext.Ajax.request({
								url : predeliver
										.realPath('queryNoticeListByWaybillNo.action'),
								params : {
									'vo.conditionDto.waybillNo': values.waybillNo
								},
								success : function(response) {
									var result = Ext.decode(response.responseText);
									historyPanelGrid.getStore().loadData(result.vo.conditionDto.notificationEntityList);
								},
								exception : function(response) {
								}
							});
					    },
					    exception: function(response) {
			                var json = Ext.decode(response.responseText);
			                Ext.ux.Toast.msg(predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.save.failuer'), json.message, 'error', 2000);//'保存失败'
			                this.up('form').query('button')[2].setDisabled(true);
		                },
						scope : this
					});
				}
			}}];
		me.callParent([cfg]);
	}
});
Ext.define('Foss.predeliver.notifyCustomer.warn', {
		extend : 'Ext.panel.Panel',
		cls : 'autoHeight',
		bodyCls : 'autoHeight',
		layout : 'column',
			defaults:{
			margin:'0 0 0 15'
		},	
		initComponent : function() {
			var me = this;
			Ext.applyIf(me, {
			items : [ {
					xtype : 'label',
					text : predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.success')
				}, {
					xtype : 'image',
					imgCls : 'foss_icons_pkp_noticeOk'
				}, {
					xtype : 'label',
					text : predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.failuer')
				}, {
					xtype : 'image',
					imgCls : 'foss_icons_pkp_noticeError'
				}, /**{
					xtype : 'label',
					text : predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.noticingUnsuccessful')
				},{
					xtype : 'image',
					imgCls : 'foss_icons_pkp_noticeNoResult'
				},*/  {
					 xtype: 'label',
					 text: predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.online.not.payment')
				}, {
					 xtype: 'image',
					 imgCls: 'foss_icons_pkp_flagblue'
				}, 
			//	{
			//		xtype : 'label',
			//		text : predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.noNoticing')
			//	}, {
			//		xtype : 'image',
			//		imgCls : 'foss_icons_pkp_noNotice'
			//	},
				{
					xtype : 'label',
					text : predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.noticing') + predeliver.notifyCustomer.warehouseFreeSafeDataNum + predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.dayNotdelivery')
				}, {
					xtype : 'image',
					imgCls : 'foss_icons_pkp_3daysNoPicking'
				}, {
					xtype : 'label',
					text : predeliver.notifyCustomer.i18n('pkp.predeliver.notifyCustomer.goodsNumDisac')
				}, {
					xtype : 'image',
					imgCls : 'foss_icons_pkp_goodsNumDisac'
				},  {
			    	 xtype: 'label',
			         text:'运单未补录'
			    }, {
			    	 xtype: 'image',
			    	 imgCls: 'foss_icons_pkp_flagred'
			    }, {
					xtype : 'tbspacer',
					flex : 2
				}]
				});
			me.callParent(arguments);
		}

	});
//下面大panel
Ext.define('Foss.predeliver.notifyCustomer.BigDownPanel',{
	extend:'Ext.panel.Panel',
	bodyCls: 'autoHeight',
	cls: 'autoHeight',
	layout: 'column',
	noticeDetailGrid : null,
	getNoticeDetailGrid :function(){
		var me = this;
		if(this.noticeDetailGrid==null){
			this.noticeDetailGrid = Ext.create('Foss.predeliver.grid.NoticeCustomerGrid',{
				columnWidth:.37,
				id:'Foss_predeliver_grid_NoticeCustomerGrid_Id'
				
			});
		}
		return this.noticeDetailGrid;
	},
	noticeInfoForm : null,
	getNoticeInfoForm : function(){
		if(this.noticeInfoForm==null){
			this.noticeInfoForm = Ext.create('Foss.predeliver.noticeCustomer.NoticeInfoFormPanel',{
				id:'Foss.predeliver.noticeCustomer.NoticeInfoFormPanel_Id'
			});
		}
		return this.noticeInfoForm;
	},
	historyPanel:null,
	getHistoryPanel : function() {
		if (!this.historyPanel) {
			this.historyPanel = Ext.create('Foss.predeliver.noticeCustomer.HistoryPanelGrid',{
				id:'Foss.predeliver.noticeCustomer.HistoryPanelGrid_id',
				tabConfig : {
					width : 100
				}
			});
		}
		return this.historyPanel;
	},
	wayBillInfoForm : null,
	getWayBillInfoForm : function(){
		if(this.wayBillInfoForm==null){
			this.wayBillInfoForm = Ext.create('Foss.predeliver.noticeCustomer.TransportInfoFormPanel',{
				id:'Foss.predeliver.noticeCustomer.TransportInfoFormPanel_id',
					tabConfig : {
					width : 100
				}
				
			});
		}
		return this.wayBillInfoForm;
	},
	initNoticeTabPanel:null,
	getInitNoticeTabPanel : function() {
	if (!this.initNoticeTabPanel) {
		this.initNoticeTabPanel = Ext.create('Ext.tab.Panel', {
			bodyCls: 'autoHeight',
			cls: 'innerTabPanel',
			flex : 5.5,
			items : [this.getWayBillInfoForm(),this.getHistoryPanel()]	
			
		});
	}
	return this.initNoticeTabPanel;
	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.items = [me.getNoticeDetailGrid(),{
			border: 1,
			xtype:'container',
			columnWidth:.63,
			items : [
				me.getNoticeInfoForm(),me.getInitNoticeTabPanel()
			]
		}];
		me.callParent([cfg]);
	}
});

Ext.onReady(function() {
	Ext.QuickTips.init();
	if (Ext.getCmp('T_predeliver-notifyCustomer_content')) {
		return;
	}
	
	var queryForm = Ext.create('Foss.predeliver.form.NoticeCustomerSearch'), bigNOticeDownPanel = Ext
			.create('Foss.predeliver.notifyCustomer.BigDownPanel'), warnPanel = Ext.create("Foss.predeliver.notifyCustomer.warn"),
		totalForm=Ext.create('Foss.notifyCustomer.TotalForm');
	predeliver.notifyCustomer.queryform = queryForm;
	
	//Ext.getCmp('receiveCustomer_Prov_Noti').setReginValue(predeliver.provName, provCode);	//设置查询条件默认值省
	//Ext.getCmp('receiveCustomer_City_Noti').setReginValue(predeliver.cityName, cityCode); //设置查询条件默认值市
	
	Ext.create('Ext.panel.Panel', {
		id : 'T_predeliver-notifyCustomerIndex_content',
		cls : 'panelContentNToolbar',
		bodyCls : 'panelContentNToolbar-body',
		layout : 'auto',
		margin : '2 2 2 2',
		items : [queryForm,warnPanel, bigNOticeDownPanel,totalForm],
		renderTo : 'T_predeliver-notifyCustomerIndex-body'
	});
});
