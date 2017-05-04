/***
 * @author FOSS-liuxiangcheng
 * page:计划到达通知
 */
 predeliver.PICKUPGOODSHIGHWAYS = 'PICKUPGOODSHIGHWAYS'; // 派送方式词条
 predeliver.PAYMENTMODE = 'SETTLEMENT__PAYMENT_TYPE'; // 付款方式词条
 predeliver.CLEARING_TYPE = 'CLEARING_TYPE'; // 结算方式词条
 predeliver.ARRIVED = 'ARRIVED'; // 车辆到达
 predeliver.UNLOADED = 'UNLOADED'; // 已卸车
 predeliver.CLEARING_TYPE_MONTH = 'MONTH_END'; // 客户付款方式-月结
 predeliver.CLEARING_TYPE_HALF_MONTH = 'HALF_MONTH'; // 付款方式-半月结
 predeliver.arriveNotice.SELECT_TYPE = ''; // 查询方式
 predeliver.EXCEPTIONREASON = 'PKP_NOTICE_EXCEPTION_REASON';  //异常原因词条
 predeliver.INVOICETYPE = 'PKP_RECEIPT_INVOICE_TYPE'; //收货发票类型
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
 
Ext.define('predeliver.arriveNotice.tipPanel', {
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
Ext.define('Foss.predeliver.model.arriveNotice', {
	extend : 'Ext.data.Model',
	fields : [
		{name : 'noticeResult' , type: 'string'}, /** 通知状态 */
		{name : 'waybillNo'}, /** 运单号 */
		{name : 'receiveCustomerContact'}, /** 收货人姓名 */
		{name: 'receiveCustomerName'}, /**收获客户名称*/
		{name : 'receiveCustomerPhone'}, /** 收货人电话 */
		{name : 'receiveCustomerMobilephone'}, /** 收货人手机 */
		{name : 'receiveMethod',
			convert:function(value) {
				var v = FossDataDictionary.rendererSubmitToDisplay(value, predeliver.PICKUPGOODSHIGHWAYS);
				if(Ext.isEmpty(v) || value == v){
					v = FossDataDictionary.rendererSubmitToDisplay(value, 'PICKUPGOODSSPECIALDELIVERYTYPE');
				}
				return v;
			}
		}, /** 派送方式 */
		{name : 'receiveCustomerAddress'}, /** 送货地址 */
		{name : 'planArriveTime', convert:dateConvert}, /** 预计到达时间 */
		{name : 'goodsQtyTotal', type : 'int'}, /** 开单件数 */
		{name : 'arriveGoodsQty', type : 'int'}, /** 到达件数 */
		{name : 'handoverGoodsQty', type : 'int'}, /** 交接件数 */
		{name : 'deliveryCustomerName'}, /** 发货人 */
		{name : 'deliveryCustomerContact'}, /** 发货人 */
		{name : 'goodsName'}, /** 货物名称 */
		{name : 'insuranceAmount', type : 'float'}, /** 货物价值 */
		{name : 'paidMethod',
			convert:function(value) {
				return FossDataDictionary.rendererSubmitToDisplay(value, predeliver.PAYMENTMODE);
			}
		}, /** 付款方式 */
		{name:'paidMethodVir'},
		{name : 'receiveOrgName'}, /** 始发部门 */
		{name : 'productCode',
			convert:function(value) {
				return Foss.pkp.ProductData.rendererSubmitToDisplay(value);
			}
		}, /** 运输性质 */
		{name : 'stockQty', type : 'int'}, /** 库存件数 */
		{name : 'goodsWeightTotal', type : 'float'}, /** 总量 */
		{name : 'goodsVolumeTotal', type : 'float'}, /** 体积 */
		{name : 'goodsPackage'}, /** 包装 */
		{name : 'goodsSize'}, /** 尺寸 */
		{name : 'transportFee', type : 'float'}, /** 运费 */
		{name : 'deliveryGoodsFee', type : 'float'}, /** 送货费 */
		{name : 'otherFee', type : 'float'}, /** 其他费用 */
		{name : 'pickupFee',type : 'float'},//接货费
		{name : 'insuranceFee', type : 'float'}, /** 保价费 */
		{name : 'codAmount', type : 'float'}, /** 代收费 */
		{name : 'toPayAmount', type : 'float'}, /** 到付费 */
		{name : 'billTime', type : 'date'}, /** 出发日期 */
		{name : 'notificationTime', type : 'date', convert:dateConvert}, /** 上次通知日期 */
		{name : 'receivingHabits'}, /** 收货习惯 */
		{name : 'selectType'}, /** 查询方式 */
		{name : 'receiveOrgName'}, /** 始发部门*/
		{name : 'customerQulification'}, /** 客户资质*/
		{name : 'receiveCustomerCode'}, /** 发送部门code*/
		{name : 'createOrgCode'}, /** 创建部门code*/
		{name: 'isPay',type: 'string'},//是否已付款
		{name : 'vehicleNo'}, // 车牌号字段
		{name: 'deliverDate',type:'date',
			convert: function(value) {
				if (value != null) {
					var date = new Date(value);
					return Ext.Date.format(date,'y-m-d');
				} else {
					return null;
				}
			}
		},
		{name : 'packageFee'}, // 包装手续费
		{name : 'transportationRemark'}, // 储运事项
		{name : 'deliveryCustomerMobilephone'}, //发货客户手机
		{name : 'deliveryCustomerPhone'}, // 发货客户电话
		{name : 'isPrinted'}, // 是否打印
		{name : 'isNoticeSuccess'}, // 是否通知成功过
		{name : 'rfcStatus'}, // 更改单状态
		{name : 'taskStatus'}, /** 车辆状态*/
		{name : 'pendingType'}, // 运单处理类型
		{name : 'isOrPayStatus'},  // 到付的是否网上支付
		{name : 'isExhibitCargo'}, //是否会展货
		{name : 'goodsStatus'}, //货物状态
		{name : 'nowAddress'},//当前位置 add by 329757
		{name : 'actualArriveTime', type : 'date',convert:dateConvert},//实际到达时间 add by 329757
		{name : 'preCustomerPickupTime', type : 'date',convert:dateConvert},//承诺到达时间 add by 329757
		{name : 'notificationOrgCode'}, //通知部门 add by 329757
		{name : 'destOrgCode'} //到达部门 add  by 329757
	]
});
/*通知信息*/
Ext.define('Foss.predeliver.arriveNotice.model.NoticeInfo', {
	extend : 'Ext.data.Model',
	fields : [
		{name: 'waybillNo',type:'string'},
		{name: 'receiveCustomerContact',type:'string'},
		{name: 'deliverDate',type:'date',
			convert: function(value) {
				if (value != null) {
					var date = new Date(value);
					return Ext.Date.format(date,'y-m-d');
				} else {
					return null;
				}
			}},
		{name: 'customerQulification'},// 客户资质
		{name: 'deliverRequire',type:'string'},
		{name: 'toPayAmount',type:'number'},
		{name: 'noticeResult',type:'string'},
		{name: 'paymentType',type:'string'},
		{name: 'isSentRequired',type:'string'}
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
Ext.define('Foss.predeliver.arriveNotice.AddReceiptHabitWindow',{
	extend : 'Ext.window.Window',
	title : '新增客户收货习惯',
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
		}, {
				xtype : 'combobox',
				labelWidth : 90,
				width : 230,
				fieldLabel : '发票类型',
				name : 'invoiceType',
				displayField: 'valueName',
				valueField: 'valueCode',
				store: FossDataDictionary.getDataDictionaryStore(predeliver.INVOICETYPE, null, {
							'valueCode': '', 'valueName':'无'
						}),
				editable: false
			}, {
				fieldLabel : '发票备注',
				labelWidth : 90,
				xtype : 'textfield',
				width : 270,
				name : 'invoiceDetail',
				maxLength: 30,
				maxLengthText: '已超出字数最大限制!'
			} , {
			colspan: 2,
			xtype : 'textarea',
			width : 580,
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
Ext.define('Foss.predeliver.arriveNotice.localeNotice.ReceiptAddressModel',{
	extend : 'Ext.data.Model',
	fields : ['customerCode', 'customerContactName', 'customerMobilePhone',
	      	'customerName', 'customerPhone', 'id', 'receiveAddressDetails',
	      	'receiveCityCode', 'receiveDistCode', 'receiveProvCode', 'receiveStreet',
	      	'receiveProvName', 'receiveCityName', 'receiveDistName']
});

/**
 * 历史收货地址store
 */
var beforeReceiptAddressStore = Ext.create('Ext.data.Store',{
	model : 'Foss.predeliver.arriveNotice.localeNotice.ReceiptAddressModel',
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
Ext.define('Foss.predeliver.arriveNotice.localeNotice.ReceiptAddressWindow', {
	extend : 'Ext.window.Window',
	title : '收货人地址历史信息',
	modal : true,
	width : 800,
	resizable : false,
	items : [ {
		xtype : 'grid',
		store : beforeReceiptAddressStore,
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
		handler : function (btn) {//239
			var record = addressGrid.data;
			if (record) {
				var noticeForm = Ext.getCmp('T_predeliver-arriveNotice_content').down('#arriveNotice_NoticeInfoFormPanel_ID').getForm();
				//由历史地址带出的默认的收货省
				Ext.getCmp('arrive_ActualAddress_ID').setReginValue(record.receiveProvName, record.receiveProvCode, predeliver.PCC_PROV);
				//由历史地址带出的默认的收货市
				Ext.getCmp('arrive_ActualAddress_ID').setReginValue(record.receiveCityName, record.receiveCityCode, predeliver.PCC_CITY);
				//由历史地址带出的默认的收货县
				Ext.getCmp('arrive_ActualAddress_ID').setReginValue(record.receiveDistName, record.receiveDistCode, predeliver.PCC_DIST);
				noticeForm.findField('actualStreetn').setValue(record.receiveStreet);
				noticeForm.findField('actualAddressDetail').setValue(record.receiveAddressDetails);
				
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

/*多票信息*/
Ext.define('Foss.predeliver.arriveNotice.model.MultiBillInfo', {
	extend : 'Ext.data.Model',
	fields : [
		{name: 'waybillNo',type:'string'},
		{name: 'receiveCustomerCode',type:'string'},
		{name: 'receiveCustomerContact',type:'string'},
		{name: 'planArriveTime',type:'date',convert:dateConvert},
		{name: 'paidMethod',type:'string'},
		{name: 'receiveCustomerMobilephone',type:'string',convert:function(value,record) {
			if(value==null||value ==''){
				return record.get('receiveCustomerPhone');			
			}else{
				return value;
			}
		}},
		{name: 'receiveCustomerPhone',type:'string'},
		{name: 'togetherSendCode',type:'string'},
		{name: 'stockGoodsQty',type:'string'},
		{name: 'receiveCustomerAddress',type:'string'},
		{name: 'receiveCustomerCityCode',type:'string'},
		{name: 'receiveCustomerDistCode',type:'string'},
		{name: 'taskStatus',type:'string'},
	//	{name: 'receiveMethod',type:'string'},
		{name : 'receiveMethod',
			convert:function(value) {
				var v = FossDataDictionary.rendererSubmitToDisplay(value, predeliver.PICKUPGOODSHIGHWAYS);
				if(Ext.isEmpty(v) || value == v){
					v = FossDataDictionary.rendererSubmitToDisplay(value, 'PICKUPGOODSSPECIALDELIVERYTYPE');
				}
				return v;
			}
		}, /** 提货方式 */
		{name: 'togetherSendMark',type:'string'},
		{name : 'pendingType'} // 运单处理类型
	]
});
//多票信息 store
Ext.define('Foss.predeliver.arriveNotice.model.MultiBillStore',{
	extend:'Ext.data.Store',
	model:'Foss.predeliver.arriveNotice.model.MultiBillInfo',
	autoLoad : false,
	proxy : {
		type : 'ajax',
		actionMethods : 'POST',
		url : predeliver.realPath("queryMultibillListByCustomer.action"),
		reader : {
			type : 'json',
			root : 'vo.multibillList'
		}
	},
	listeners:{
		load: function(store,records){
			//当数据加载完之后 
				var flag =false,
				mergebutton = Ext.getCmp('Foss_predeliver_arriveNotice_multiBillGrid_mergeButton_Id'),
				relieveButton = Ext.getCmp('Foss_predeliver_arriveNotice_multiBillGrid_relieveButton_Id'),
				choose = [],
				multiBillGrid = Ext.getCmp('Foss.predeliver.grid.multiBillGrid_id2');
				if (records.length == 0) {
					return;	
				}
				for(i=0;i<records.length;i++){
					if(records[i].data.togetherSendMark || records[i].data.togetherSendCode) {
						 choose.push(records[i]);
						 flag =true;
					}
				}
				multiBillGrid.getSelectionModel().select(choose);
				relieveButton.setDisabled(!flag); //设置解绑按钮
				mergebutton.setDisabled(flag); //设置合送按钮 
		}
	}
});
Ext.define('Foss.predeliver.store.arriveNotice', {
	extend : 'Ext.data.Store',
	model : 'Foss.predeliver.model.arriveNotice',
	pageSize : 50,
	proxy : {
		type : 'ajax',
		timeout: 300000,
		actionMethods : 'POST',
		url:predeliver.realPath('queryArriveNotices.action'),
		reader : {
			type : 'json',
			root : 'vo.dtoList',
			totalProperty:'totalCount'
		}
	},
	listeners : {
		beforeload : function(store, operation, eOpts) {
			var queryForm = Ext.getCmp('T_predeliver-arriveNotice_content').getQueryForm(),
				myForm =queryForm.getForm(),
				waybillNo = myForm.getValues().waybillNo,
				receiveCustomerProv = myForm.getValues().receiveCustomerProv,
				receiveCustomerCity = myForm.getValues().receiveCustomerCity,
				receiveCustomerDistCode = myForm.getValues().receiveCustomerDistCode,
				//add by 329757 添加到达时间状态：计划到达时间，实际到达时间，通知时间
				arriveTimeStatus = myForm.getValues().arriveTimeStatus,
				planArriveTimeFrom = myForm.getValues().planArriveTimeFrom,
				planArriveTimeTo = myForm.getValues().planArriveTimeTo,
				productCode= myForm.getValues().productCode,
				receiveMethod = myForm.getValues().receiveMethod,
				noticeResult = myForm.getValues().noticeResult,
				actualArriveTime=myForm.getValues().actualArriveTime,//add by 329757 实际到达时间
				preCustomerPickupTime=myForm.getValues().preCustomerPickupTime,//add by 329757 承诺到达时间
				notificationTime=myForm.getValues().notificationTime;//add by 329757 通知时间
			
			// 验证运单号输入的行数
			if (!Ext.isEmpty(waybillNo)) {
				var arrayWaybillNo = waybillNo.split('\n');
				if (arrayWaybillNo.length > 50) {
					Ext.ux.Toast.msg(predeliver.arriveNotice.i18n('pkp.predeliver.notifyCustomer.tip'), predeliver.arriveNotice.i18n('pkp.predeliver.notifyCustomer.waybillNo.valitation'), 'error', 3000); // 运单号为8到10位数字，以回车输入，最多输入50个运单号。
					return false;	
				}
				for (var i = 0; i < arrayWaybillNo.length; i++) {
					if (Ext.isEmpty(arrayWaybillNo[i])) {
						Ext.ux.Toast.msg(predeliver.arriveNotice.i18n('pkp.predeliver.notifyCustomer.tip'), predeliver.arriveNotice.i18n('pkp.predeliver.notifyCustomer.waybillNo.valitation'), 'error', 3000); // 运单号为8到10位数字，以回车输入，最多输入50个运单号。
						return false;	
					}
				}
			}else{
					//时间验证
				if (!Ext.isEmpty(planArriveTimeFrom) && !Ext.isEmpty(planArriveTimeTo)) {	
					var result = Ext.Date.parse(planArriveTimeTo,'Y-m-d H:i:s') - Ext.Date.parse(planArriveTimeFrom,'Y-m-d H:i:s');	
					if(result / (24 * 60 * 60 * 1000) > 1){	
						Ext.ux.Toast.msg(predeliver.arriveNotice.i18n('pkp.predeliver.notifyCustomer.tip'), predeliver.arriveNotice.i18n('pkp.predeliver.arriveNotice.the.date.range.cannot.be.more.than.1.days'), 'error', 3000); // '起止日期相隔不能超过1天！'
						return false;	
					}	
				}
				if((Ext.isEmpty(planArriveTimeFrom)) || (Ext.isEmpty(planArriveTimeTo))){
						Ext.ux.Toast.msg(predeliver.arriveNotice.i18n('pkp.predeliver.notifyCustomer.tip'), predeliver.arriveNotice.i18n('pkp.predeliver.notifyCustomer.TimeNull'), 'error', 3000);//起止时间不能为空
						return false;
				}
			}
			
			

			if(!myForm.isValid()){
				return false;
			}
			//执行查询，首先load查询条件，为全局变量，在查询条件的FORM创建时生成	
			var queryParams = queryForm.getValues();
			Ext.apply(operation, {	
				params:{	
					'vo.conditionDto.waybillNo':queryParams.waybillNo,  //运单号	
					'vo.conditionDto.deliverProv':receiveCustomerProv,     //省
					'vo.conditionDto.deliverCity':receiveCustomerCity,      //市
					'vo.conditionDto.deliverDistCode':receiveCustomerDistCode, //行政区域 
					'vo.conditionDto.arriveTimeStatus':queryParams.arriveTimeStatus, //到达时间状态
					'vo.conditionDto.planArriveTimeFrom':queryParams.planArriveTimeFrom,	//计划到达时间开始
					'vo.conditionDto.planArriveTimeTo':queryParams.planArriveTimeTo,		//计划到达时间结束
					'vo.conditionDto.productCode':queryParams.productCode, //运输性质
					'vo.conditionDto.receiveMethodCon':queryParams.receiveMethodCon,         //派送方式
					'vo.conditionDto.noticeResult':queryParams.noticeResult      //通知状态
				}	
			});	
		},
		load : function(store, records, successful,eOpts) {
			// 根据后台传入的值动态显示列
			var columns = Ext.getCmp('Foss.predeliver.grid.arriveNoticeGrid_ID').columns;
			var wayBillInfoForm = Ext.getCmp('Foss.predeliver.arriveNotice.WayBillInfoFormPanel_id'),
				multiBillGrid = Ext.getCmp('Foss.predeliver.grid.multiBillGrid_id2'),
				noticeInfoForm = Ext.getCmp('Foss.predeliver.arriveNotice.NoticeInfoFormPanel_Id');			
			 noticeInfoForm.getForm().reset();
			 wayBillInfoForm.getForm().reset();
			 multiBillGrid.getSelectionModel().deselectAll();
			 multiBillGrid.getStore().removeAll();
			 var data = store.getProxy().getReader().rawData;
			// '运单目的站非本部门或者运单未开单!'
			 if(!data.success){
				 Ext.ux.Toast.msg(predeliver.arriveNotice.i18n('pkp.predeliver.notifyCustomer.tip'), data.message, 'error', 3000);
				 store.removeAll();
				 return;
			 }
			if (data.vo.dtoList==null) {
				return;	
			}
			for (var len = 0; len < columns.length; len ++) {
				if (data.vo.dtoList[0].selectType == '4') {
				if ( columns[len].dataIndex == 'planArriveTime'
						|| columns[len].dataIndex == 'handoverGoodsQty'
				    	|| columns[len].dataIndex == 'handoverNo'
				    	|| columns[len].dataIndex == 'vehicleNo'
				    	) {
				    // 预计到达时间、交接单件数、交接单号、车牌号
					columns[len].setVisible(false);  
				}
			} else {				
				if ( columns[len].dataIndex == 'planArriveTime'
						|| columns[len].dataIndex == 'handoverGoodsQty'
				    	|| columns[len].dataIndex == 'handoverNo'
				    	|| columns[len].dataIndex == 'vehicleNo'
				    	) {
				    // 预计到达时间、交接单件数、交接单号、车牌号
					columns[len].setVisible(true);  
				}
			}
		}
		predeliver.arriveNotice.SELECT_TYPE = data.vo.dtoList[0].selectType;
	}
}});

//派送方式data
Ext.define('Foss.predeliver.store.DeliverTypeDataStore',{
	extend: 'Ext.data.Store',
	fields: [
		{name: 'valueCode',  type: 'string'},
		{name: 'valueName',  type: 'string'}
	],
	data: {
		'items':[
      {'valueCode':'','valueName':'全部'},
			{'valueCode':'DELIVER_INGA','valueName':'送货进仓'},
			{'valueCode':'LARGE_DELIVER_UP','valueName':'大件上楼'},
			{'valueCode':'DELIVER_UP','valueName':'送货上楼'},
			{'valueCode':'DELIVER_NOUP','valueName':'送货(不含上楼)'}
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
//运输性质data
Ext.define('Foss.predeliver.store.DeliverTypeProductStore',{
	extend: 'Ext.data.Store',
	fields: [
	         {name: 'valueCode',  type: 'string'},
	         {name: 'valueName',  type: 'string'}
	         ],
	         data: {
	        	 'items':[
	        	          {'valueCode':'YTY','valueName':'精准大票.标准件'},
	        	          {'valueCode':'BGLRF','valueName':'精准大票汽运(长)'},
	        	          {'valueCode':'BGFLF','valueName':'精准大票卡航'},
	        	          {'valueCode':'DTD','valueName':'精准大票.经济件'},
	        	          {'valueCode':'FSF','valueName':'精准城运'},
	        	          {'valueCode':'BGFSF','valueName':'精准大票城运'},
	        	          {'valueCode':'BGSRF','valueName':'精准大票汽运(短)'},
	        	          {'valueCode':'FLF','valueName':'精准卡航'},
	        	          {'valueCode':'LRF','valueName':'精准汽运(长途)'},
	        	          {'valueCode':'SRF','valueName':'精准汽运(短途)'},
	        	          {'valueCode':'WVH','valueName':'整车(三级)'}
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
startDate = function(date, day) {
	var d, s, t, t2;
	var MinMilli = 1000 * 60;
	var HrMilli = MinMilli * 60;
	var DyMilli = HrMilli * 24;
	t = Date.parse(date);
	t2 =  new Date(t+day*DyMilli);
	t2.setHours(12);
	t2.setMinutes(0);
	t2.setSeconds(0);
	t2.setMilliseconds(0);	
	return t2;
};

endDate = function(date, day) {
	var d, s, t, t2;
	var MinMilli = 1000 * 60;
	var HrMilli = MinMilli * 60;
	var DyMilli = HrMilli * 24;
	t = Date.parse(date);
	t2 =  new Date(t+day*DyMilli);
	t2.setHours(12);
	t2.setMinutes(0);
	t2.setSeconds(0);
	t2.setMilliseconds(0);	
	return t2;
};


/*查询条件*/
Ext.define('Foss.predeliver.form.ArriveNoticeSearch', {  
	extend : 'Ext.form.Panel',
	title : predeliver.arriveNotice.i18n('pkp.predeliver.notifyCustomer.query'),
	collapsible : true,
	animCollapse : true,
	defaults : {
		margin : '4 2 4 2',
		labelWidth : 60
	},
	cls : 'autoHeight',
	bodyCls : 'autoHeight',
	defaultType : 'textfield',
	layout : 'column',
	frame : true,
	id : 'Foss_predeliver_form_arriveNoticeSearch_Id',
	items : [{//运单号
		name : 'waybillNo',
		xtype : 'textarea',
		fieldLabel : predeliver.arriveNotice.i18n('pkp.predeliver.notifyCustomer.waybillNo'),
		height: 100,
		columnWidth : .25,
		emptyText : predeliver.arriveNotice.i18n('pkp.predeliver.notifyCustomer.waybillNo.valitation'),
		regex : /^([0-9]{8,10}\n?)+$/i,
		regexText : predeliver.arriveNotice.i18n('pkp.predeliver.notifyCustomer.waybillNo.valitation')
	}, {
		fieldLabel : predeliver.arriveNotice.i18n('pkp.predeliver.editDeliverbillIndex.prov'),//省
		id : 'receiveCustomer_Prov2',
		name : 'receiveCustomerProv',
		xtype : 'commonreginbyconditionselector',
		degree: 'DISTRICT_PROVINCE',
		columnWidth : .25,
		labelWidth:20,
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
				//  如果不注释掉这个,将无法手动输入
				//	receiveCustomerCity.expand();
				}
				else
				{
					receiveCustomerCity.setValue('');
				}
			}
		}
	},
	{
		fieldLabel : predeliver.arriveNotice.i18n('pkp.predeliver.editDeliverbillIndex.city'),//市
		id : 'receiveCustomer_City2',
		name : 'receiveCustomerCity',
		xtype : 'commonreginbyconditionselector',
		degree: 'CITY',
		columnWidth : .25,
		labelWidth:20,
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
	},{
		id : 'receiveCustomerDist_Code2',
		name : 'receiveCustomerDistCode',
		fieldLabel : predeliver.arriveNotice.i18n('pkp.predeliver.editDeliverbillIndex.administrativeRegions'),//行政区域
		xtype : 'commonareaselector',
		labelWidth:60,
		columnWidth : .25
	},{//计划到达时间
		xtype : 'combobox',
		queryMode : 'local',
	    displayField : 'name',
	    valueField : 'name',
	    editable:false,
	    name : 'arriveTimeStatus',
	    columnWidth : .25,
	    value : '计划到达时间',
		store : Ext.create('Ext.data.Store', {
			fields : ['name'],
			data : [ 
			         {name : '计划到达时间'},
			         {name : '实际到达时间'},
			         {name : '通知时间'}
			       ]})
	},{//加个冒号
		xtype: 'container',
		border : false,
		columnWidth:.01,
		html: ':'
	},{
		xtype : 'rangeDateField',
		margin : '4 2 4 2',
		labelWidth:110,
		fieldId : 'Foss_predeliver_arriveNotice_planArriveTime_Id',
		dateType : 'datetimefield_date97',
		fromName : 'planArriveTimeFrom',
		toName : 'planArriveTimeTo',
		fromValue: Ext.Date.format(startDate(new Date(),0),'Y-m-d H:i:s'),
		toValue: Ext.Date.format(endDate(new Date(),1),'Y-m-d H:i:s'),
        //不允许为空
        disallowBlank: true,
		editable:false,
		columnWidth : .49	
	},{//运输性质
		xtype :'dynamicmulticomboselector',
		fieldLabel: predeliver.arriveNotice.i18n('pkp.predeliver.notifyCustomer.productCode'),
		labelWidth:60,
		name: 'productCode',
		columnWidth : .25,
		delimiter: ' ',
		// listWidth:500,//设置下拉框宽度
		store : Ext.create('Foss.predeliver.store.DeliverTypeProductStore'),// 从外面传入
		displayField:'valueName',// 显示名称
		valueField:'valueCode',// 值
		//queryParam:'deptQueryVo.deptName',// 查询参数
		queryMode:'local',
		triggerAction:'all',
		showContent:'{valueName}',// 显示表格列
		isPaging:false,// 分页
		isEnterQuery:true// 回车查询
	},{//派送方式
		xtype:'dynamicmulticomboselector',
		fieldLabel: predeliver.arriveNotice.i18n('pkp.predeliver.notifyCustomer.deliverCode'),
		labelWidth:60,
		name: 'receiveMethodCon',
		columnWidth : .25,
		delimiter: ' ',
		store:Ext.create('Foss.predeliver.store.DeliverTypeDataStore'),// 从外面传入
		displayField:'valueName',// 显示名称
		valueField:'valueCode',// 值
		queryMode:'local',
		triggerAction:'all',
		showContent:'{valueName}',// 显示表格列 {valueName}&nbsp;&nbsp;{valueCode}
		isPaging:false,// 分页
		isEnterQuery:true// 回车查询
	},{//通知状态
		fieldLabel : '通知状态',
		xtype : 'combobox',
		queryMode : 'local',
	    displayField : 'name',
	    valueField : 'name',
	    columnWidth : .25,
	    editable:false,
	    name : 'noticeResult',
	    value : '全部',
		store : Ext.create('Ext.data.Store', {
			fields : ['name'],
			data : [ 
			         {name : '全部'},
			         {name : '通知成功'},
			         {name : '通知失败'},
			         {name : '未通知'}
			       ]
		})
	},{//重置按钮
		border: 1,
		xtype:'container',
		columnWidth:1,
		defaultType:'button',
		margin : '4 2 0 2',
		layout:'column',
		items:[ {
			text : predeliver.arriveNotice.i18n('pkp.predeliver.notifyCustomer.resetButton'),
			columnWidth:.09,
			handler : function() {
				var myForm = this.up('form').getForm();	
				myForm.reset();	
				//Ext.getCmp('receiveCustomer_Prov2').setReginValue(predeliver.provName, provCode);	
				//Ext.getCmp('receiveCustomer_City2').setReginValue(predeliver.cityName, cityCode);
			}
		}, {
			xtype: 'container',
			border : false,
			columnWidth:.82,
			html: '&nbsp;'
		},{//查询按钮
			text : predeliver.arriveNotice.i18n('pkp.predeliver.notifyCustomer.queryButton'),
			disabled:!predeliver.arriveNotice.isPermission('arriveNotice/queryArriveNoticebutton'),
			hidden:!predeliver.arriveNotice.isPermission('arriveNotice/queryArriveNoticebutton'),
			columnWidth:.09,
			cls : 'yellow_button',
			plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin', {
				seconds: 3
			}),
			handler : function() {
				Ext.getCmp('Foss.predeliver.grid.arriveNoticeGrid_ID').getPagingToolbar().moveFirst();
				
			}
		}
		]
	}],
	listeners : { 
		'render' : function(_this, eOpts){
			_this.getForm().findField('waybillNo').setHeight(80);
		}
	}
});

//下面大panel
Ext.define('Foss.predeliver.arriveNotice.BigDownPanel',{
	extend:'Ext.panel.Panel',
	bodyCls: 'autoHeight',
	cls: 'autoHeight',
	layout: 'column',
	noticeDetailGrid : null,
	getNoticeDetailGrid :function(){
		var me = this;
		if(this.noticeDetailGrid==null){
			this.noticeDetailGrid = Ext.create('Foss.predeliver.grid.arriveNoticeGrid',{
				columnWidth:.4,
				id:'Foss.predeliver.grid.arriveNoticeGrid_ID'
				
			});
		}
		return this.noticeDetailGrid;
	},
	noticeInfoForm : null,
	getNoticeInfoForm : function(){
		if(this.noticeInfoForm==null){
			this.noticeInfoForm = Ext.create('Foss.predeliver.arriveNotice.NoticeInfoFormPanel',{
				id:'Foss.predeliver.arriveNotice.NoticeInfoFormPanel_Id'
			});
		}
		return this.noticeInfoForm;
	},
	wayBillInfoForm : null,
	getWayBillInfoForm : function(){
		if(this.wayBillInfoForm==null){
			this.wayBillInfoForm = Ext.create('Foss.predeliver.arriveNotice.WayBillInfoFormPanel',{
				id:'Foss.predeliver.arriveNotice.WayBillInfoFormPanel_id'
			});
		}
		return this.wayBillInfoForm;
	},
	
	multiBillGrid : null,
	getMultiBillGrid : function(){
		if(this.multiBillGrid==null){
			this.multiBillGrid = Ext.create('Foss.predeliver.grid.multiBillGrid',{
				id:'Foss.predeliver.grid.multiBillGrid_id2'
			});
		}
		return this.multiBillGrid;
	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.items = [me.getNoticeDetailGrid(),{
			border: 1,
			xtype:'container',
			columnWidth:.6,
			items : [
				me.getNoticeInfoForm(),me.getMultiBillGrid(),me.getWayBillInfoForm()
			]
		}];
		me.callParent([cfg]);
	}
});
var habit_deliveryTimeInterval;
var habit_deliveryTimeStart;
var habit_deliveryTimeOver;
var habit_invoiceType;
var habit_invoiceDetail;
var habit_receiptHabitRemark;
//通知明细表格
Ext.define('Foss.predeliver.grid.arriveNoticeGrid',{
	extend:'Ext.grid.Panel',
	//收缩
	collapsible: true,
	emptyText: predeliver.arriveNotice.i18n('pkp.predeliver.notifyCustomer.selectResultEmpty'),
	title : predeliver.arriveNotice.i18n('pkp.predeliver.notifyCustomer.selectResult'),//搜索结果
	//是否有框
	frame:true,
	//动画收缩
	animCollapse: true,
	//自动增加滚动条
	autoScroll:true,
	//高
	height: 1100,//
	store: null, 
	bodyCls: 'autoHeight',
	cls: 'autoHeight',
	selModel : Ext.create('Ext.selection.CheckboxModel',{
		checkOnly: false //限制只有点击checkBox后才能选中行
	}),
	tbar : [ {
		xtype : 'button',
		plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin', {
			seconds: 3
		}),
		text : predeliver.arriveNotice.i18n('pkp.predeliver.notifyCustomer.printArriveSheet'),//打印到达联
		disabled:!predeliver.arriveNotice.isPermission('arriveNotice/arriveNoticeprintbutton'),
		hidden:!predeliver.arriveNotice.isPermission('arriveNotice/arriveNoticeprintbutton'),
		handler : function() {
			var records = Ext.getCmp('Foss.predeliver.grid.arriveNoticeGrid_ID').getSelectionModel().getSelection();
			var mygrid = this.up('gridpanel');
			if(records.length==0){
    			Ext.ux.Toast.msg(predeliver.arriveNotice.i18n('pkp.predeliver.notifyCustomer.tip'), predeliver.arriveNotice.i18n('pkp.predeliver.notifyCustomer.please.select.waybill'), 'error', 1000);//'请选择运单。'
    			return;
    		}
    		if(records.length>50){
		        Ext.ux.Toast.msg(predeliver.arriveNotice.i18n('pkp.predeliver.notifyCustomer.tip'),predeliver.arriveNotice.i18n('pkp.predeliver.arriveNotice.select.count.not.greater.than.50'), 'error', 4000);//批量打印到达联时，勾选的条数不能大于50条！
		        return;
		    }
    		var notNoticeOKWaybillNos =new Array();
    		for (var i = 0; i < records.length; i++) {
				var waybillrfcStatus = records[i].data.rfcStatus;
				var isNoticeSuccess = records[i].data.isNoticeSuccess;
				if (!Ext.isEmpty(waybillrfcStatus) 
					&& (waybillrfcStatus == 'PRE_AUDIT' || waybillrfcStatus == 'PRE_ACCECPT')) {
					Ext.ux.Toast.msg(predeliver.arriveNotice.i18n('pkp.predeliver.notifyCustomer.tip'),predeliver.arriveNotice.i18n('pkp.predeliver.arriveNotice.waybillrfc.not.accept'));//运单有未受理更改单，不能打印！
					return;
					break;
				}
				if (Ext.isEmpty(isNoticeSuccess)|| (isNoticeSuccess != 'Y')) {
					notNoticeOKWaybillNos.push(records[i].data.waybillNo);
				}
    		}
    		if(notNoticeOKWaybillNos.length>0){
    			// 查询不在库存的运单号
    			Ext.Ajax.request({
					url : predeliver.realPath('queryNotInStockWaybillNos.action'),
					async : false,
					jsonData:{
					'vo' : {
						'conditionDto':{
							'arrayWaybillNos':notNoticeOKWaybillNos
							}
						}
					},
					success : function(response) {
						var result = Ext.decode(response.responseText),
						arrayWaybillNos =result.vo.conditionDto.arrayWaybillNos;
						if(arrayWaybillNos!=null &&arrayWaybillNos.length>0){
							var waybillString='';
							for(var j = 0 ;j<arrayWaybillNos.length;j++){
								waybillString +=arrayWaybillNos[j]+',';
							}
							waybillString = waybillString.substring(0,waybillString.length-1);
							Ext.ux.Toast.msg(predeliver.arriveNotice.i18n('pkp.predeliver.notifyCustomer.tip'),predeliver.arriveNotice.i18n('pkp.predeliver.arriveNotice.hasNotPrint')+waybillString, 'error', 3000);// '存在未通知成功且不在库的运单，不能打印。运单号为：'
							return;
						}
							mygrid.getPrintWindow().show();
    						mygrid.getPrintWindow().setSource('PKP_NOTIFY');
    				},
					exception : function(response) {
						var result = Ext.decode(response.responseText);
						Ext.ux.Toast.msg(predeliver.arriveNotice.i18n('pkp.predeliver.notifyCustomer.tip'), result.message, 'error', 3000);
					}
    			});
    		}else {
					mygrid.getPrintWindow().show();
    				mygrid.getPrintWindow().setSource('PKP_NOTIFY');
			}
		}
	}, {//导出按钮
		xtype : 'button',
		text : predeliver.arriveNotice.i18n('pkp.predeliver.notifyDetailsAction.result.exportNoticeRecord'),
		handler : function(){
			if(!Ext.fly('downloadAttachFileForm')){
			    var frm = document.createElement('form');
			    frm.id = 'downloadAttachFileForm';
			    frm.style.display = 'none';
			    document.body.appendChild(frm);
			}
			var records = Ext.getCmp('Foss.predeliver.grid.arriveNoticeGrid_ID').getSelectionModel().getSelection();
			//若选中了行数不为0
			if(records.length!=0){
				var queryParams = Ext.getCmp('T_predeliver-arriveNotice_content').getQueryForm().getValues(),
	    			params = {
							'vo.conditionDto.waybillNo':queryParams.waybillNo,  //运单号	
							'vo.conditionDto.deliverProv':queryParams.receiveCustomerProv,     //省
							'vo.conditionDto.deliverCity':queryParams.receiveCustomerCity,      //市
							'vo.conditionDto.deliverDistCode':queryParams.receiveCustomerDistCode, //行政区域 
							'vo.conditionDto.arriveTimeStatus':queryParams.arriveTimeStatus, //到达时间状态 
							'vo.conditionDto.planArriveTimeFrom':queryParams.planArriveTimeFrom,	//计划到达时间开始
							'vo.conditionDto.planArriveTimeTo':queryParams.planArriveTimeTo,		//计划到达时间结束
							'vo.conditionDto.productCode':queryParams.productCode, //运输性质
							'vo.conditionDto.receiveMethodCon':queryParams.receiveMethodCon,         //派送方式
							'vo.conditionDto.noticeResult':queryParams.noticeResult      //通知状态
	    				}
	    			Ext.Ajax.request({
	    				url:predeliver.realPath('exportArriveNotices.action'),
	    				form: Ext.fly('downloadAttachFileForm'),
	    				method : 'POST',
	    				params : params,
	    				isUpload: true
	    			});
			}else{
				//或者提示不能导出
				Ext.ux.Toast.msg(predeliver.arriveNotice.i18n('pkp.predeliver.notifyCustomer.tip'),predeliver.arriveNotice.i18n('pkp.predeliver.exceptionProcess.exceptionInfo.notExport'), 'error', 3000);
			}
		}
}],
		//给表格行涂层
	viewConfig : {
		stripeRows : false,
		enableTextSelection : true,
		getRowClass : function(record, rowIndex, rp, ds) {
			var isPrinted = record.get('isPrinted');
			if (isPrinted!=null && isPrinted =='Y') {
				return 'predeliver-beforeNoticeIndex-row-purole';
			}
		}
	},
	columns : [
	    {	
			header: '序号',//序列号
			xtype:'rownumberer',
			width:28
           },
		{text : predeliver.arriveNotice.i18n('pkp.predeliver.notifyCustomer.status'),width : 68,dataIndex : 'noticeResult', 
			renderer : function(value, cellmeta, record, rowIndex, columnIndex, store){
				var result = '';
				if(value == 'SUCCESS' && record.get("destOrgCode")==record.get("notificationOrgCode")) {
					//通知成功 
					result = '<div class="foss_icons_pkp_noticeOk"></div>';
				} else if(value == 'FAILURE' && record.get("destOrgCode")==record.get("notificationOrgCode")) {
					//通知失败
					result = '<div class="foss_icons_pkp_noticeError"></div>';
				}
				return result;
			}
		},
		{text : predeliver.arriveNotice.i18n('pkp.predeliver.notifyCustomer.waybillNo'),width : 85,dataIndex : 'waybillNo', xtype : 'ellipsiscolumn'},//运单号
		{text : predeliver.arriveNotice.i18n('pkp.predeliver.editDeliverbillIndex.toPayAmount'),width : 94,dataIndex : 'toPayAmount'},//到付总额
		{text : predeliver.arriveNotice.i18n('pkp.predeliver.notifyCustomer.planeArriveTime2'),width : 130, dataIndex : 'preCustomerPickupTime',
			renderer : function(value) {
				if (value != null) {
					var date = Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
					return date;
				} else {
					return null;
				}
			} 		
		},//计划到达时间
		{text : predeliver.arriveNotice.i18n('pkp.predeliver.notifyCustomer.arriveTime2'),width : 75, dataIndex : 'actualArriveTime',
			renderer : function(value) {
				if (value != null) {
					var date = Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
					return date;
				} else {
					return null;
				}
			} 		
		},//实际到达时间
		{text : predeliver.arriveNotice.i18n('pkp.predeliver.notifyDetailsAction.query.noticeTime'),width : 75,dataIndex : 'notificationTime',
				renderer : function(value) {
					if (value != null) {
						var date = Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
						return date;
					} else {
						return null;
					}
				} 		
		},//通知时间
		{text : predeliver.arriveNotice.i18n('pkp.predeliver.notifyDetailsAction.query.nowAddress'),width : 75,dataIndex : 'nowAddress', xtype : 'ellipsiscolumn'},//当前位置
		{text : predeliver.arriveNotice.i18n('pkp.predeliver.printDeliverbillArrivesheetIndex.goodsStatus'),width : 75,dataIndex : 'goodsStatus', xtype : 'ellipsiscolumn'},//货物状态
		{text : predeliver.arriveNotice.i18n('pkp.predeliver.arriveDeliverManagerIndex.goodsQtyTotal'),width : 75,dataIndex : 'goodsQtyTotal', xtype : 'ellipsiscolumn'},//开单件数
		{text : predeliver.arriveNotice.i18n('pkp.predeliver.arriveDeliverManagerIndex.goodsWeightTotal'),width : 75,dataIndex : 'goodsWeightTotal', xtype : 'ellipsiscolumn'},//开单重量
		{text : predeliver.arriveNotice.i18n('pkp.predeliver.arriveDeliverManagerIndex.goodsVolumeTotal'),width : 75,dataIndex : 'goodsVolumeTotal', xtype : 'ellipsiscolumn'},//开单体积
		{text : predeliver.arriveNotice.i18n('pkp.predeliver.notifyCustomer.receiveMethod'),width : 94,dataIndex : 'receiveMethod'},//派送方式
		{text : predeliver.arriveNotice.i18n('pkp.predeliver.notifyCustomer.productCode'),width : 94,dataIndex : 'productCode'}//运输性质
	],
	 //分页
	pagingToolbar : null,
  	getPagingToolbar : function() {
  		if (this.pagingToolbar == null) {
  			this.pagingToolbar = Ext.create('Deppon.StandardPaging', {
  				store : this.store,
  				plugins: {
  		    		ptype: 'pagesizeplugin',
  		   	 		//超出输入最大限制是否提示，true则提示，默认不提示
  		    		alertOperation: true,
  		    		//自定义分页comobo数据
  		    		sizeList: [ ['20', 20], ['30', 30], ['50', 50] ],
  		    		//入最大限制，默认为50
  		    		maximumSize: 50
  		    	},
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
		beforeselect: function(rowModel, record, index, eOpts) {
            var pendingType = record.get('pendingType');
            if (pendingType != null &&pendingType!='PC_ACTIVE'&& pendingType!='PDA_ACTIVE') {
                return false;
            }
		}
		,
        select:function(view, record,item, index){
			var wayBillInfoForm = Ext.getCmp('Foss.predeliver.arriveNotice.WayBillInfoFormPanel_id'),
			noticeInfoForm = Ext.getCmp('Foss.predeliver.arriveNotice.NoticeInfoFormPanel_Id');
			var saveButton = Ext.getCmp('Foss_predeliver_arriveNotice_NoticeInfoFormPanel_saveButten_Id');
			var recordsModel = Ext.getCmp('Foss.predeliver.grid.arriveNoticeGrid_ID').getSelectionModel(),
				records=recordsModel.getSelection(),
				multiBillGrid = Ext.getCmp('Foss.predeliver.grid.multiBillGrid_id2');
			if(records.length==2){
				// 设置grid的record
				 noticeInfoForm.getForm().reset();
				 wayBillInfoForm.getForm().reset();
				 multiBillGrid.getSelectionModel().deselectAll();
				 multiBillGrid.getStore().removeAll();
				 saveButton.setDisabled(true);
				 return;
			}
			if(records.length>=3){
				 return;
			}
			selectRecord = records[0].data;
			// 设置grid的record
		    noticeInfoForm.getForm().reset();
		    wayBillInfoForm.getForm().reset();
			multiBillGrid.getSelectionModel().deselectAll();
			multiBillGrid.getStore().removeAll();
		    saveButton.setDisabled(true);
			// 绑定表格数据到表单上
			Ext.Ajax.request({
				url:predeliver.realPath('queryBeforeNoticeWaybillInfo.action'),
				params: {
					'vo.conditionDto.waybillNo': selectRecord.waybillNo,
					'vo.conditionDto.notifyCustomerDto.arriveGoodsQty':selectRecord.arriveGoodsQty,
					'vo.conditionDto.notifyCustomerDto.taskStatus': selectRecord.taskStatus,
					'vo.conditionDto.notifyCustomerDto.paidMethodVir': selectRecord.paidMethodVir
				},
				success: function(response){
					var result = Ext.decode(response.responseText),
						 model = Ext.ModelManager.create(result.vo.conditionDto.notifyCustomerDto,'Foss.predeliver.model.arriveNotice'),
						 wayBillInfoFormT = wayBillInfoForm.getForm(),
						 noticeInfoFormT = noticeInfoForm.getForm(),
						 paymentTypeGroup = noticeInfoFormT.findField('paymentTypeName'),
						 rfcStatus =selectRecord.rfcStatus,
						 multiBillGrid = Ext.getCmp('Foss.predeliver.grid.multiBillGrid_id2'),
						 multiBillGridStore=multiBillGrid.getStore(),
						 mergebutton = Ext.getCmp('Foss_predeliver_arriveNotice_multiBillGrid_mergeButton_Id'),
						relieveButton = Ext.getCmp('Foss_predeliver_arriveNotice_multiBillGrid_relieveButton_Id');
					wayBillInfoFormT.loadRecord(model);
					noticeInfoFormT.loadRecord(model);
					
					//通知信息-开单到付且已网上付款-239284
					if(!Ext.isEmpty(model.data.isOrPayStatus)) {
						noticeInfoFormT.findField('toPayAmountHiddenBefore').setValue(model.data.toPayAmount + "【" + model.data.isOrPayStatus+"】");
					} else {
						noticeInfoFormT.findField('toPayAmountHiddenBefore').setValue(model.data.toPayAmount);
					}
					
					//如果客户代码为空，隐藏收货习惯按钮
					if(!noticeInfoFormT.findField('receiveCustomerCode').getValue()) {
						Ext.getCmp('arriveNotice_ReceiptHabit_ID').setVisible(false);
					} else {
						Ext.getCmp('arriveNotice_ReceiptHabit_ID').setVisible(true);
					}
					
					//运单信息-运单重量≥500kg标记红色-239284
					if(!Ext.isEmpty(model.data.goodsWeightTotal)) {
							if (model.data.goodsWeightTotal >= 500) {
						     wayBillInfoFormT.findField('goodsWeightTotal').setFieldStyle('color:red');
					    } else {
								 wayBillInfoFormT.findField('goodsWeightTotal').setFieldStyle('color:black');
							}
					}
					
					//运单信息-运单体积≥2.5F标记红色-239284
					if(!Ext.isEmpty(model.data.goodsVolumeTotal)) {
							if (model.data.goodsVolumeTotal >= 2.5) {
						     wayBillInfoFormT.findField('goodsVolumeTotal').setFieldStyle('color:red');
					    } else {
								 wayBillInfoFormT.findField('goodsVolumeTotal').setFieldStyle('color:black');
							}
					}
					//开单件数与库存件数不一致时  两者的标记色为红色
					if(!Ext.isEmpty(model.data.goodsQtyTotal)&&!Ext.isEmpty(model.data.stockQty)){
						if(model.data.goodsQtyTotal==model.data.stockQty){
							wayBillInfoFormT.findField('goodsQtyTotal').setFieldStyle('color:black');
							wayBillInfoFormT.findField('stockQty').setFieldStyle('color:black');
						}else{
							wayBillInfoFormT.findField('goodsQtyTotal').setFieldStyle('color:red');
							wayBillInfoFormT.findField('stockQty').setFieldStyle('color:red');
						}
					}
					mergebutton.setDisabled(false); //设置合送按钮 
					if(rfcStatus!=null&&rfcStatus !=''){
						saveButton.setDisabled(true);
						Ext.ux.Toast.msg(predeliver.arriveNotice.i18n('pkp.predeliver.notifyCustomer.save.failuer'), '存在更改单未受理', 'error', 2000);//存在更改单未受理
					}else {
						saveButton.setDisabled(false);
					}
					/*****
					 * 添加多票信息
					 */
					if(result.vo.conditionDto.notifyCustomerDto.receiveCustomerCode){ //多票信息有数据
						var searchParams ={
						'vo.conditionDto.notifyCustomerDto.receiveCustomerCode':result.vo.conditionDto.notifyCustomerDto.receiveCustomerCode,
						'vo.conditionDto.notifyCustomerDto.billTime':Ext.Date.format(new Date(result.vo.conditionDto.notifyCustomerDto.billTime),'Y-m-d H:i:s')
						};
						Ext.apply(multiBillGridStore.proxy.extraParams,searchParams);
						//加载数据
						multiBillGridStore.load();	
					}
					// 初始化通知信息页面元素
					/*if (predeliver.arriveNotice.SELECT_TYPE == "4") {
						// 设置通知信息上的件数为库存件数
						noticeInfoFormT.findField('arriveGoodsQty').setValue(model.data.stockQty);
						wayBillInfoFormT.findField('handoverGoodsQty').setVisible(false);
					} else {
						// 设置通知信息上的件数为交接单件数
						noticeInfoFormT.findField('arriveGoodsQty').setValue(selectRecord.handoverGoodsQty);
						wayBillInfoFormT.findField('handoverGoodsQty').setVisible(true);
						wayBillInfoFormT.findField('handoverGoodsQty').setValue(selectRecord.handoverGoodsQty);
						//开单件数与交接件数不一致时
						if (wayBillInfoFormT.findField('goodsQtyTotal').getValue() != wayBillInfoFormT.findField('handoverGoodsQty').getValue()) {
								wayBillInfoFormT.findField('goodsQtyTotal').setFieldStyle('color:red');
								wayBillInfoFormT.findField('handoverGoodsQty').setFieldStyle('color:red');
						} else {
								wayBillInfoFormT.findField('goodsQtyTotal').setFieldStyle('color:black');
								wayBillInfoFormT.findField('handoverGoodsQty').setFieldStyle('color:black');
						}
					}*/
					
					// 动态显示运行信息页面元素
					paymentType = FossDataDictionary.rendererDisplayToSubmit(model.data.paidMethod, predeliver.PAYMENTMODE), // 运单付款方式
					customerQulification = model.data.customerQulification;// 客户付款方式
					 var toPayAmount = noticeInfoFormT.findField('toPayAmount').getValue();
					// 付款方式的验证
					if (toPayAmount > 0) {	
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
					// 初始化时，通知结果默认为成功
					noticeInfoFormT.findField('noticeResultName').setValue({'noticeResult' : 'SUCCESS'});
					// 联系方式手机没有时，显示电话
					if (Ext.isEmpty(model.data.receiveCustomerMobilephone) && !Ext.isEmpty(model.data.receiveCustomerPhone)) {
						noticeInfoFormT.findField('receiveCustomerMobilephone').setValue(model.data.receiveCustomerPhone);
					}
					// 设置页面客户资质显示
					noticeInfoFormT.findField('customerQulification').setValue(FossDataDictionary.rendererSubmitToDisplay(model.data.customerQulification, 'CLEARING_TYPE'));
					
					//设置省、市、区, 街道， 详细地址
					//noticeInfoFormT.findField('actualProvN').setValue(result.vo.conditionDto.notificationEntity.actualProvN); //result.vo.conditionDto.notifyCustomerDto.billTime
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
					var currentDate = new Date();
					if (result.vo.conditionDto.notificationEntity.deliverDate) {
						var dateStr = result.vo.conditionDto.notificationEntity.deliverDate.replace(/-/g,"/");
						currentDate = new Date(dateStr);
					} else {
						if (currentDate.getHours() > 11) {
							currentDate.setDate(currentDate.getDate() + 1);
						}
					}
					noticeInfoFormT.findField('deliverDate').setValue(currentDate);
					//由运单带出的默认的收货省
					if (result.vo.conditionDto.notifyCustomerDto.receiveCustomerProvCode != null && result.vo.conditionDto.notifyCustomerDto.receiveCustomerProvCode != "" ) {
						Ext.getCmp('arrive_ActualAddress_ID').setReginValue(result.vo.conditionDto.notificationEntity.actualProvN, result.vo.conditionDto.notifyCustomerDto.receiveCustomerProvCode , predeliver.PCC_PROV);
					}
					//由运单带出的默认的收货市
					if (result.vo.conditionDto.notifyCustomerDto.receiveCustomerCityCode != null && result.vo.conditionDto.notifyCustomerDto.receiveCustomerCityCode != "" ) {
						Ext.getCmp('arrive_ActualAddress_ID').setReginValue(result.vo.conditionDto.notificationEntity.actualCityN, result.vo.conditionDto.notifyCustomerDto.receiveCustomerCityCode , predeliver.PCC_CITY);
					}
					//由运单带出的默认的收货县
					if (result.vo.conditionDto.notifyCustomerDto.receiveCustomerDistCode != null && result.vo.conditionDto.notifyCustomerDto.receiveCustomerDistCode != "" ) {
						Ext.getCmp('arrive_ActualAddress_ID').setReginValue(result.vo.conditionDto.notificationEntity.actualDistrictN, result.vo.conditionDto.notifyCustomerDto.receiveCustomerDistCode , predeliver.PCC_DIST);
					}
					
					noticeInfoFormT.findField('actualStreetn').setValue(result.vo.conditionDto.notificationEntity.actualStreetn); 
					noticeInfoFormT.findField('actualAddressDetail').setValue(result.vo.conditionDto.notificationEntity.actualAddressDetail); 
					//noticeInfoFormT.findField('actualProvCode').setValue(result.vo.conditionDto.notifyCustomerDto.receiveCustomerProvCode); 
					//noticeInfoFormT.findField('actualCityCode').setValue(result.vo.conditionDto.notifyCustomerDto.receiveCustomerCityCode); 
					//noticeInfoFormT.findField('actualDistrictCode').setValue(result.vo.conditionDto.notifyCustomerDto.receiveCustomerDistCode);
					
					//带出的收货习惯-时间段
					if (!Ext.isEmpty(result.vo.conditionDto.notificationEntity.deliveryTimeInterval)){
						habit_deliveryTimeInterval = result.vo.conditionDto.notificationEntity.deliveryTimeInterval;
						noticeInfoFormT.findField('deliveryTimeInterval').setValue(result.vo.conditionDto.notificationEntity.deliveryTimeInterval);
					} else {
						habit_deliveryTimeInterval = null;
					}
					//带出的收货习惯-送货时间点开始
					if (!Ext.isEmpty(result.vo.conditionDto.notificationEntity.deliveryTimeStart)){
						habit_deliveryTimeStart = result.vo.conditionDto.notificationEntity.deliveryTimeStart;
						noticeInfoFormT.findField('deliveryTimeStart').setValue(result.vo.conditionDto.notificationEntity.deliveryTimeStart);
					} else {
						habit_deliveryTimeStart = null;
					}
					//带出的收货习惯-送货时间点结束
					if (!Ext.isEmpty(result.vo.conditionDto.notificationEntity.deliveryTimeOver)){
						habit_deliveryTimeOver = result.vo.conditionDto.notificationEntity.deliveryTimeOver;
						noticeInfoFormT.findField('deliveryTimeOver').setValue(result.vo.conditionDto.notificationEntity.deliveryTimeOver);
					} else {
						habit_deliveryTimeOver = null;
					}
					//带出的收货习惯-发票类型
					if (!Ext.isEmpty(result.vo.conditionDto.notificationEntity.invoiceType)){
						habit_invoiceType = result.vo.conditionDto.notificationEntity.invoiceType;
						noticeInfoFormT.findField('invoiceType').setValue(result.vo.conditionDto.notificationEntity.invoiceType);
					} else {
						habit_invoiceType = null;
					}
					//带出的收货习惯-发票类型备注
					if (!Ext.isEmpty(result.vo.conditionDto.notificationEntity.invoiceDetail)){
						habit_invoiceDetail = result.vo.conditionDto.notificationEntity.invoiceDetail;
						noticeInfoFormT.findField('invoiceDetail').setValue(result.vo.conditionDto.notificationEntity.invoiceDetail);
					} else {
						habit_invoiceDetail = null;
					}
					//带出的收货习惯-发票类型备注
					if (!Ext.isEmpty(result.vo.conditionDto.notificationEntity.receiptHabitRemark)){
						habit_receiptHabitRemark = result.vo.conditionDto.notificationEntity.receiptHabitRemark;
						noticeInfoFormT.findField('receiptHabitRemark').setValue(result.vo.conditionDto.notificationEntity.receiptHabitRemark);
					} else {
						habit_receiptHabitRemark = null;
					}
					//带出历史送货要求
					if (!Ext.isEmpty(result.vo.conditionDto.notifyCustomerDto.deliverRequire)) {
						noticeInfoFormT.findField('deliverRequire').setValue(result.vo.conditionDto.notifyCustomerDto.deliverRequire);
					} else {
						noticeInfoFormT.findField('deliverRequire').setValue('预计');
					}
				}
			});
		}
	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.predeliver.store.arriveNotice');
		me.bbar = me.getPagingToolbar();
		me.callParent([cfg]);
	}
});

//多票信息
Ext.define('Foss.predeliver.grid.multiBillGrid',{
	extend:'Ext.grid.Panel',
	//收缩
	collapsible: true,
	emptyText: predeliver.arriveNotice.i18n('pkp.predeliver.notifyCustomer.selectResultEmpty'),
	title : "多票信息",
	//是否有框
	frame:true,
	//动画收缩
	animCollapse: true,
	//自动增加滚动条
	autoScroll:true,
	//高
	height: 238,//
	store: null, 
	bodyCls: 'autoHeight',
	cls: 'autoHeight',
	selModel : Ext.create('Ext.selection.CheckboxModel',{
		checkOnly: false ////限制只有点击checkBox后才能选中行
	}),
		//给表格行涂层
	viewConfig : {
		stripeRows : false,
		enableTextSelection : true
	},
	columns : [
	         /*  {
	   			header: predeliver.arriveNotice.i18n('pkp.predeliver.notifyCustomer.rownumberer'),//序列号
	   			xtype:'rownumberer',
	   			width:47
	              },*/
	    {
			header: "客户编码",//序列号
			dataIndex : 'receiveCustomerCode', 
			xtype : 'ellipsiscolumn',
			width:77
           },
        {text : predeliver.arriveNotice.i18n('pkp.predeliver.notifyCustomer.waybillNo'),width : 85,dataIndex : 'waybillNo', xtype : 'ellipsiscolumn'},
   		{text : predeliver.arriveNotice.i18n('pkp.predeliver.notifyCustomer.receiveCustomerContact'),width : 75,dataIndex : 'receiveCustomerContact', xtype : 'ellipsiscolumn'},
   		{text : predeliver.arriveNotice.i18n('pkp.predeliver.notifyCustomer.recvisits'),width : 75,dataIndex : 'receiveCustomerMobilephone', xtype : 'ellipsiscolumn',
   			renderer : function(value, cellmeta, record, rowIndex, columnIndex, store){
   				return value ? value :record.get("receiveCustomerPhone");
   			}},
   		{text : "提货方式",width : 75,xtype : 'ellipsiscolumn',dataIndex : 'receiveMethod'},//提货方式
   		{text : predeliver.arriveNotice.i18n('pkp.predeliver.notifyCustomer.receiveCustomerAddress'),width : 100,xtype : 'ellipsiscolumn',dataIndex : 'receiveCustomerAddress'},//送货地址
   		{text : "库存状态",width : 60,dataIndex : 'receiveCustomerMobilephone', xtype : 'ellipsiscolumn',dataIndex : 'stockGoodsQty'
   			,renderer:function(value){
   				if(!isNaN(value) && parseInt(value) > 0 ) return "库存中";
   				else return "未入库";
   			}},
   		{text : predeliver.arriveNotice.i18n('pkp.predeliver.notifyCustomer.planArriveTime'),width : 130, dataIndex : 'planArriveTime',
			renderer : function(value) {
				if (value != null) {
					var date = Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
					return date;
				} else {
					return null;
				}
			} 		
		},
		{
			header: "合送编码",
			dataIndex : 'togetherSendCode', 
			hidden:true
           },
           {
   			header: "合送标识",
   			dataIndex : 'togetherSendMark', 
   			hidden:true
              },	{dataIndex : 'taskStatus',hidden : true}
		
	],
  	listeners:{
  		beforeselect: function(rowModel, record, index, eOpts) {
            if(Ext.getCmp('Foss_predeliver_arriveNotice_multiBillGrid_mergeButton_Id').isDisabled())
  			{
                return false;
            }
			var pendingType = record.get('pendingType');
            if (pendingType != null &&pendingType!='PC_ACTIVE'&& pendingType!='PDA_ACTIVE') {
                return false;
            }
		},
		beforedeselect: function(rowModel, record, index, eOpts) {
            if(Ext.getCmp('Foss_predeliver_arriveNotice_multiBillGrid_mergeButton_Id').isDisabled())
  			{
                return false;
            }
			
		}
  	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.bbar = ['->',{
			text : '合送',
			id:'Foss_predeliver_arriveNotice_multiBillGrid_mergeButton_Id',
			handler : function() {
				var records = Ext.getCmp('Foss.predeliver.grid.multiBillGrid_id2').getSelectionModel().getSelection(),
				wayBillInfoForm = Ext.getCmp('Foss.predeliver.arriveNotice.WayBillInfoFormPanel_id').getForm(),
				waybillNos =new Array(),currentWaybillNo=false,
				mergebutton = Ext.getCmp('Foss_predeliver_arriveNotice_multiBillGrid_mergeButton_Id'),
				relieveButton = Ext.getCmp('Foss_predeliver_arriveNotice_multiBillGrid_relieveButton_Id');
				mergebutton.setDisabled(true); //设置合送按钮
				if(records.length<=1){
					mergebutton.setDisabled(false); //设置合送按钮
	    			Ext.ux.Toast.msg(predeliver.arriveNotice.i18n('pkp.predeliver.notifyCustomer.tip')/*提示*/, "请至少选择两条运单信息进行合送!", 'error', 2000);
	    			return;
	    		}
	    		for (var i = 0; i < records.length; i++) {
					if(records[i].data.waybillNo===wayBillInfoForm.findField('waybillNo').getValue()){
						currentWaybillNo=true;
					}
					waybillNos.push(records[i].data.waybillNo);
	    		}
				if(!currentWaybillNo){
					mergebutton.setDisabled(false); //设置合送按钮
					Ext.ux.Toast.msg(predeliver.arriveNotice.i18n('pkp.predeliver.notifyCustomer.tip')/*提示*/, "合送时必须勾选当前运单!", 'error', 2000);
	    			return;
				}
				// 发送合送请求
				Ext.Ajax.request({
					url : predeliver.realPath('mergeNoticeWaybill.action'),
					async : false,
					jsonData:{
					'vo' : {
						'conditionDto':{
							'arrayWaybillNos':waybillNos
							}
						}
					},
					success : function(response) {
						var result = Ext.decode(response.responseText),
						arrayWaybillNos =result.vo.conditionDto.arrayWaybillNos;
						if(arrayWaybillNos!=null &&arrayWaybillNos.length>0){
							var waybillString='';
							for(var j = 0 ;j<arrayWaybillNos.length;j++){
								waybillString +=arrayWaybillNos[j]+',';
							}
							waybillString = waybillString.substring(0,waybillString.length-1);
							mergebutton.setDisabled(false); //设置合送按钮
							Ext.ux.Toast.msg(predeliver.arriveNotice.i18n('pkp.predeliver.notifyCustomer.tip'),"存在未受理的更改单："+waybillString, 'error', 3000);
							return;
						}
						relieveButton.setDisabled(false); //设置解绑按钮
						mergebutton.setDisabled(true); //设置合送按钮 
						Ext.ux.Toast.msg(predeliver.arriveNotice.i18n('pkp.predeliver.notifyCustomer.tip'),"合送成功", 'ok', 3000);	
					},
					exception : function(response) {
						var result = Ext.decode(response.responseText);
						mergebutton.setDisabled(false); //设置合送按钮
						Ext.ux.Toast.msg(predeliver.arriveNotice.i18n('pkp.predeliver.notifyCustomer.tip'), result.message, 'error', 3000);
					}
				});
			}
		},{
			text : "解绑",
			id:'Foss_predeliver_arriveNotice_multiBillGrid_relieveButton_Id',
			handler : function() {
				var  multiBillGrid = Ext.getCmp('Foss.predeliver.grid.multiBillGrid_id2').getSelectionModel(),
						   records = multiBillGrid.getSelection(),
				   wayBillInfoForm = Ext.getCmp('Foss.predeliver.arriveNotice.WayBillInfoFormPanel_id').getForm(),
				        waybillNos = new Array(),
				  currentWaybillNo = false,
				       mergebutton = Ext.getCmp('Foss_predeliver_arriveNotice_multiBillGrid_mergeButton_Id'),
					 relieveButton = Ext.getCmp('Foss_predeliver_arriveNotice_multiBillGrid_relieveButton_Id');
					 relieveButton.setDisabled(true); //设置解绑按钮
				if(records.length==0){
					relieveButton.setDisabled(false); //设置解绑按钮
	    			Ext.ux.Toast.msg(predeliver.arriveNotice.i18n('pkp.predeliver.notifyCustomer.tip')/*提示*/, "没有要解绑的运单", 'error', 1000);//'请选择运单。'
	    			return;
	    		}
	    		for (var i = 0; i < records.length; i++) {
					if(records[i].data.waybillNo===wayBillInfoForm.findField('waybillNo').getValue()){
						currentWaybillNo=true;
					}
					waybillNos.push(records[i].data.waybillNo);
	    		}
				if(!currentWaybillNo){
					relieveButton.setDisabled(false); //设置解绑按钮
					Ext.ux.Toast.msg(predeliver.arriveNotice.i18n('pkp.predeliver.notifyCustomer.tip')/*提示*/, "当前运单合送时才可解绑!", 'error', 2000);
	    			return;
				}
				// 发送 请求
				Ext.Ajax.request({
					url : predeliver.realPath('relieveNoticeWaybill.action'),
					async : false,
					jsonData:{
					'vo' : {
						'conditionDto':{
							'arrayWaybillNos':waybillNos
							}
						}
					},
					success : function(response) {
						relieveButton.setDisabled(true); //设置解绑按钮
						mergebutton.setDisabled(false); //设置合送按钮 
						multiBillGrid.deselectAll(); 
						Ext.ux.Toast.msg(predeliver.arriveNotice.i18n('pkp.predeliver.notifyCustomer.tip'),"解绑成功", 'ok', 3000);
					},
					exception : function(response) {
						var result = Ext.decode(response.responseText);
						relieveButton.setDisabled(false); //设置解绑按钮
						Ext.ux.Toast.msg(predeliver.arriveNotice.i18n('pkp.predeliver.notifyCustomer.tip'), result.message, 'error', 3000);
					}
				});
			}
		}];
		me.store = Ext.create('Foss.predeliver.arriveNotice.model.MultiBillStore');

		me.callParent([cfg]);
	}
});

var toPayAmountHiddenBefore;
//运单信息  右下三
Ext.define('Foss.predeliver.arriveNotice.WayBillInfoFormPanel',{
	extend: 'Ext.form.Panel',
	title : predeliver.arriveNotice.i18n('pkp.predeliver.notifyCustomer.waybillInfo'),//运单信息
	//收缩
	collapsible: true,
	//是否有框
	frame:true,
	height:260,
	//动画收缩
	animCollapse: true,
	defaults: {
		margin:'2 2 2 2',
		labelWidth:75,
		readOnly : true
	},
	defaultType : 'textfield',
	//自动收缩高度
	cls:'autoHeight',
	bodyCls:'autoHeight',
	layout:'column',
	items : [
		{name : 'waybillNo' ,  fieldLabel : predeliver.arriveNotice.i18n('pkp.predeliver.notifyCustomer.waybillNo') ,  columnWidth : .33} ,  
		{name : 'receiveOrgName' ,  fieldLabel : predeliver.arriveNotice.i18n('pkp.predeliver.notifyCustomer.receiveOrgName') ,
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
				tipBodyElement: 'predeliver.arriveNotice.tipPanel'
			},columnWidth : .33 } ,  //始发部门
		{name : 'productCode' , fieldLabel : predeliver.arriveNotice.i18n('pkp.predeliver.notifyCustomer.productCode') ,
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
					tipBodyElement: 'predeliver.arriveNotice.tipPanel'
				},columnWidth : .33} ,  //运输性质
		{name : 'receiveCustomerContact' , fieldLabel : predeliver.arriveNotice.i18n('pkp.predeliver.notifyCustomer.receiveCustomerContact') , columnWidth : .33} ,  //收货人姓名
		{name : 'goodsName' , fieldLabel : predeliver.arriveNotice.i18n('pkp.predeliver.notifyCustomer.goodsName') ,
			tipConfig: {
				cls: 'autoHeight',
				bodyCls: 'autoHeight',
				width: 100,
				//是否随鼠标滑动
				trackMouse: true,
				//普通Form上必须配置tipType(区分普通Form和行展开的Form)
				tipType: 'normal',
				//当值为空的时候，不显示tip
				isShowByData: true,
				tipBodyElement: 'predeliver.arriveNotice.tipPanel'
			},columnWidth : .33} ,  //货物名称
		{name : 'receiveMethod' , fieldLabel : predeliver.arriveNotice.i18n('pkp.predeliver.notifyCustomer.receiveMethod') , columnWidth : .33} ,  //派送方式
		{name : 'receiveCustomerPhone' , fieldLabel : predeliver.arriveNotice.i18n('pkp.predeliver.notifyCustomer.receiveCustomerPhone'),
			tipConfig: {
			cls: 'autoHeight',
			bodyCls: 'autoHeight',
			width: 100,
			//是否随鼠标滑动
			trackMouse: true,
			//普通Form上必须配置tipType(区分普通Form和行展开的Form)
			tipType: 'normal',
			//当值为空的时候，不显示tip
			isShowByData: true,
			tipBodyElement: 'predeliver.arriveNotice.tipPanel'
		}, columnWidth : .33} ,   //收货人电话
		{name : 'insuranceAmount' , fieldLabel : predeliver.arriveNotice.i18n('pkp.predeliver.notifyCustomer.insuranceAmount') , columnWidth : .33} ,  //'货物价值'
		{name : 'transportFee' , fieldLabel : predeliver.arriveNotice.i18n('pkp.predeliver.notifyCustomer.transportFee') , columnWidth : .33} ,  //运费
		{name : 'receiveCustomerMobilephone' , fieldLabel : predeliver.arriveNotice.i18n('pkp.predeliver.notifyCustomer.receiveCustomerMobilephone') ,
			tipConfig: {
				cls: 'autoHeight',
				bodyCls: 'autoHeight',
				width: 100,
				//是否随鼠标滑动
				trackMouse: true,
				//普通Form上必须配置tipType(区分普通Form和行展开的Form)
				tipType: 'normal',
				//当值为空的时候，不显示tip
				isShowByData: true,
				tipBodyElement: 'predeliver.arriveNotice.tipPanel'
			},columnWidth : .33} ,  //收货人手机
		{name : 'goodsQtyTotal' , fieldLabel : predeliver.arriveNotice.i18n('pkp.predeliver.notifyCustomer.goodsQtyTotal') , columnWidth : .33} , //开单件数 
		{name : 'pickupFee' , fieldLabel : predeliver.arriveNotice.i18n('pkp.predeliver.notifyCustomer.pickupFee') , columnWidth : .33} ,//接货费
		{name : 'receiveCustomerAddress' , fieldLabel : predeliver.arriveNotice.i18n('pkp.predeliver.notifyCustomer.receiveCustomerAddress') ,
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
				tipBodyElement: 'predeliver.arriveNotice.tipPanel'
			},columnWidth : .33 , xtype : 'textarea'} ,  // 送货地址
		{name : 'stockQty' , fieldLabel : predeliver.arriveNotice.i18n('pkp.predeliver.notifyCustomer.stockQty') , columnWidth : .33} ,  //'库存件数'
		{name : 'deliveryGoodsFee' , fieldLabel : predeliver.arriveNotice.i18n('pkp.predeliver.notifyCustomer.deliveryGoodsFee') , columnWidth : .33} ,//送货费
		{name : 'goodsWeightTotal' , fieldLabel : predeliver.arriveNotice.i18n('pkp.predeliver.notifyCustomer.weight.kg') , columnWidth : .33} , //'重量(千克)'
		{name : 'otherFee' , fieldLabel : predeliver.arriveNotice.i18n('pkp.predeliver.notifyCustomer.otherFee') , columnWidth : .33} , //其他费用
		{name : 'goodsVolumeTotal' , fieldLabel : predeliver.arriveNotice.i18n('pkp.predeliver.notifyCustomer.volume.stere') , columnWidth : .33,labelWidth:85} ,  //体积(立方米)
		{name : 'insuranceFee' , fieldLabel : predeliver.arriveNotice.i18n('pkp.predeliver.notifyCustomer.insuranceFee') , columnWidth : .33} ,  //  保价费
		
		{name : 'deliveryCustomerContact' , fieldLabel : predeliver.arriveNotice.i18n('pkp.predeliver.notifyCustomer.deliveryCustomerName') ,  
			tipConfig: {
				cls: 'autoHeight',
				bodyCls: 'autoHeight',
				width: 100,
				//是否随鼠标滑动
				trackMouse: true,
				//普通Form上必须配置tipType(区分普通Form和行展开的Form)
				tipType: 'normal',
				//当值为空的时候，不显示tip
				isShowByData: true,
				tipBodyElement: 'predeliver.arriveNotice.tipPanel'
			},columnWidth : .33} ,//'发货人'
        {name : 'goodsPackage' , fieldLabel : predeliver.arriveNotice.i18n('pkp.predeliver.notifyCustomer.goodsPackage'), columnWidth : .33} , //'包装'
        {name : 'codAmount' , fieldLabel : predeliver.arriveNotice.i18n('pkp.predeliver.notifyCustomer.codAmount') , columnWidth : .33} ,  //'代收货款'
      
        {name : 'deliveryCustomerPhone' , fieldLabel : '发货客户电话', columnWidth : .33,labelWidth:85} , //发货客户电话
		{name : 'goodsSize' , fieldLabel : predeliver.arriveNotice.i18n('pkp.predeliver.notifyCustomer.goodsSize.cm') ,  
        	tipConfig: {
				cls: 'autoHeight',
				bodyCls: 'autoHeight',
				width: 100,
				//是否随鼠标滑动
				trackMouse: true,
				//普通Form上必须配置tipType(区分普通Form和行展开的Form)
				tipType: 'normal',
				//当值为空的时候，不显示tip
				isShowByData: true,
				tipBodyElement: 'predeliver.arriveNotice.tipPanel'
			},columnWidth : .33} ,//'尺寸(厘米)'
		{name : 'toPayAmount' , fieldLabel : predeliver.arriveNotice.i18n('pkp.predeliver.notifyCustomer.toPayAmount') , columnWidth : .33} ,  //到付金额 
		{name : 'deliveryCustomerMobilephone' , fieldLabel : '发货客户手机', columnWidth : .33,labelWidth:85} , //发货客户手机 
		{name : 'transportationRemark' , fieldLabel :predeliver.arriveNotice.i18n('pkp.predeliver.arriveNotice.transportationRemark'), columnWidth : .33, tipConfig : {
			cls: 'autoHeight',
			bodyCls: 'autoHeight',
			width: 150,
			//是否随鼠标滑动
			trackMouse: true,
			//普通Form上必须配置tipType(区分普通Form和行展开的Form)
			tipType: 'normal',
			//当值为空的时候，不显示tip
			isShowByData: true,
			tipBodyElement: 'predeliver.arriveNotice.tipPanel'
		}} , //储运事项
		{name : 'packageFee' , fieldLabel :predeliver.arriveNotice.i18n('pkp.predeliver.arriveNotice.packageFee') , columnWidth : .33} ,  //包装费
		{name : 'notificationTime' , xtype: 'datefield', format : 'Y-m-d', fieldLabel : predeliver.arriveNotice.i18n('pkp.predeliver.notifyCustomer.notificationTime') ,labelWidth:85, columnWidth : .66},//'上次通知日期'
		{name : 'paidMethod' , fieldLabel : predeliver.arriveNotice.i18n('pkp.predeliver.notifyCustomer.paidMethod') , columnWidth : .33}  //'付款方式'
	]		
});

planDate = function(date, day) {
	var d, s, t, t2;
	var MinMilli = 1000 * 60;
	var HrMilli = MinMilli * 60;
	var DyMilli = HrMilli * 24;
	t = Date.parse(date);
	t2 =  new Date(t+day*DyMilli);
	return t2;
};
//通知信息   右一
Ext.define('Foss.predeliver.arriveNotice.NoticeInfoFormPanel',{
	extend: 'Ext.form.Panel',
	title : predeliver.arriveNotice.i18n('pkp.predeliver.notifyCustomer.noticeResultInformation'),
	//收缩
	collapsible: true,
	//是否有框
	frame:true,
	//动画收缩
	animCollapse: true,
	itemId : 'arriveNotice_NoticeInfoFormPanel_ID',
	defaults: {
		margin:'2 0 2 0',
		labelWidth:80
	},
	height: 455,
	layout:'column',
	defaultType : 'textfield',
	items : [
		{name : 'receiveCustomerContact' , labelWidth:55,fieldLabel : predeliver.arriveNotice.i18n('pkp.predeliver.notifyCustomer.contact'), readOnly : true,columnWidth: .34} , 
		{name : 'receiveCustomerMobilephone' ,labelWidth:60, fieldLabel : predeliver.arriveNotice.i18n('pkp.predeliver.notifyCustomer.recvisits'), readOnly : true,columnWidth: .34} , 
		{name : 'customerQulification' , fieldLabel : predeliver.arriveNotice.i18n('pkp.predeliver.notifyCustomer.customerQulification'), readOnly : true ,columnWidth: .32,
			setValue : function(value) {
				this.setRawValue(FossDataDictionary.rendererSubmitToDisplay(value, predeliver.CLEARING_TYPE));
			}
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
					id:'arrive_ActualAddress_ID',
					labelWidth:84,
					provinceWidth : 142,
					cityWidth : 142,
					cityLabel : '',
					cityName : 'cityName',//名称
					provinceLabel : '',
					provinceName:'privateName',//省名称
					areaLabel : '',
					areaName : 'areaName',// 县名称
					areaWidth : 142,
					areaIsBlank : true,
					cityIsBlank : true,
					provinceIsBlank : true,
					type : 'P-C-C',
					xtype : 'linkregincombselector'
				
				}, {
					xtype : 'textfield',
					name: 'actualAddressDetail',
					columnWidth: 0.55,//实际收货地址
					labelWidth:0,
					width: 130,
					maxLength: 166,
					margin: '0 0 0 89',
				    fieldLabel : '',
				    labelSeparator:''
				}, {
					xtype : 'textfield',
					name: 'actualStreetn',
					columnWidth: 0.445,
					labelWidth:0,
					width: 130,
					maxLength: 166,
					margin: '0 0 0 0',
					fieldLabel : '',
					labelSeparator:'',
					emptyText : '地址备注(XX旁边,XX路口等)非必填' // 实际收货详细地址
				} ]
			}, {
				xtype : 'button',
				text : '查询',
				name: 'btnQueryAddress',
				columnWidth : 0.1,
				height : 45,
				margin: '2 0 0 0',
				handler: function(btnQueryAddress) {
					var obj = {};
					if (btnQueryAddress.up('form').down('textfield[name=receiveCustomerContact]').getValue()) {
						obj['customerContactName'] = btnQueryAddress.up('form').down('textfield[name=receiveCustomerContact]').getValue();
						obj['customerCode'] = btnQueryAddress.up('form').down('hiddenfield[name=receiveCustomerCode]').getValue();
						obj['customerName'] = btnQueryAddress.up('form').down('hiddenfield[name=receiveCustomerName]').getValue();
						obj['customerPhone'] = btnQueryAddress.up('form').down('hiddenfield[name=receiveCustomerPhone]').getValue();
						obj['customerMobilePhone'] = btnQueryAddress.up('form').down('textfield[name=receiveCustomerMobilephone]').getValue();
						addPrev(obj,'customerReceiptAddressEntity');
						Ext.create('Foss.predeliver.arriveNotice.localeNotice.ReceiptAddressWindow').show();
						beforeReceiptAddressStore.proxy.extraParams = obj;
						beforeReceiptAddressStore.load();
					}
				}
			
			} ]
		},
		{name : 'deliverRequire' ,xtype : 'textarea',labelWidth:84,height:50, fieldLabel : predeliver.arriveNotice.i18n('pkp.predeliver.notifyCustomer.deliverRequire'), maxLength : 200,columnWidth: 1,value : '预计!'} , //送货要求
			{
			name : 'deliverDate' ,
			xtype : 'datefield',
			fieldStyle: 'color:red;font-weight:bold;',
			format: 'Y-m-d',
			editable:true,
			value: planDate(new Date(),1),
			labelWidth:84,
			minValue: planDate(new Date(),1),
			columnWidth: .55 ,
			editable:false,
			fieldLabel : predeliver.arriveNotice.i18n('pkp.predeliver.notifyCustomer.delivery.date')
		},//'预计送货日期'
		{
			xtype : 'combobox',
			fieldLabel : '送货时间段',
			name: 'deliveryTimeInterval',
			columnWidth: .45,
			displayField: 'name',
			valueField: 'name',
			value: '全天',
			triggerAction: 'all',
			store: Ext.create('Ext.data.Store',{
				fields : ['name'],
				data : [ 
				         {name : '全天'},
				         {name : '上午'},
				         {name : '下午'}
				       ]
				}),
			editable: false,
			mode: 'local'
		},  //送货时间段
		{
			xtype : 'container',
			columnWidth : .55,
			layout : 'hbox',
			items : [{
		        xtype: 'timefield',
		        name: 'deliveryTimeStart',
		        labelWidth : 84,
		        width : 185,
				editable:true,
		        format : 'H:i',
		        fieldLabel: '送货时间点',
		        increment: 30,
		        submitFormat: 'H:i',
		        anchor: '100%',
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
		        labelWidth : 20,
		        width : 120,
				editable:true,
		        format : 'H:i',
		        fieldLabel: '至',
		        labelSeparator: '',
		        increment: 30,
		        submitFormat: 'H:i',
		        anchor: '100%',
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
		   }]
		},  //送货时间点
		{xtype : 'container',layout : 'fit',columnWidth: .45,
			items : [
						{xtype : 'radiogroup',vertical : true,name : 'isSentRequiredName', defaultType:'radio',fieldLabel : predeliver.arriveNotice.i18n('pkp.predeliver.notifyCustomer.whether.will.delivery'),//'是否必送货'
						items : [
							{boxLabel : predeliver.arriveNotice.i18n('pkp.predeliver.notifyCustomer.yes'),name : 'isSentRequired',inputValue : 'Y',style: {marginTop: '5px'}},//'是'
							{boxLabel : predeliver.arriveNotice.i18n('pkp.predeliver.notifyCustomer.no'),name : 'isSentRequired',inputValue : 'N',checked : true,style: {marginTop: '5px'}}//'否'
						]}
					]
		 },{xtype : 'radiogroup',columnWidth: .55 ,vertical : true,name : 'noticeResultName', defaultType:'radio',fieldLabel : predeliver.arriveNotice.i18n('pkp.predeliver.notifyCustomer.noticeResult'),labelStype : {width : '70px'},//通知状态
				items : [
					{boxLabel : predeliver.arriveNotice.i18n('pkp.predeliver.notifyCustomer.success'),name : 'noticeResult',inputValue : 'SUCCESS', checked : true,style: {marginTop: '5px'}},//'通知成功'
					{boxLabel : predeliver.arriveNotice.i18n('pkp.predeliver.notifyCustomer.failuer'),name : 'noticeResult',inputValue : 'FAILURE',style: {marginTop: '5px'}}//'通知失败'
				],
				listeners: {
					'change': function(noticeResultName, newValue) {
						var form = this.up('form').getForm();
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
				}
		 }
		,{
			xtype : 'checkbox',
			name: 'isExhibition',
			boxLabel : '会展货', //会展货
			margin: '5px 0 0 8px',
			columnWidth : 0.17,
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
			margin: '5px 0 0 10px',
			columnWidth : 0.2
		},
		{
			xtype: 'container',
			columnWidth : 1,
			layout: 'column',
			items: [ {
				xtype : 'checkbox',
				boxLabel : '是否异常',
				name: 'isException',
				margin: '5px 0 0 10px',
				columnWidth: 0.2,
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
			}, {//异常原因
				xtype : 'combobox',
				fieldLabel : '异常原因',
				queryMode : 'local',
				name: 'exceptionReason', 
				disabled: true,
				columnWidth : 0.8,
				displayField: 'valueName',
				valueField: 'valueName',
				//store: FossDataDictionary.getDataDictionaryStore(predeliver.EXCEPTIONREASON, null),
				value : '',
				store : Ext.create('Ext.data.Store', {
					fields : ['valueName'],
					data : [ 
					         {valueName : '电话问题'},
					         {valueName : '费用问题'},
					         {valueName : '地址问题（地址错误，超派，转货）'},
					         {valueName : '送货时间问题'},
					         {valueName : '发票问题'},
					         {valueName : '预约进仓'},
					         {valueName : '待某日再联系'},
					         {valueName : '等客户通知'},
					         {valueName : '改自提'},
					         {valueName : '其他'}
					       ]
				}),
				editable: false
				/*listeners:{
					'beforequery': function(exceptionReason) {
						var values = this.up('form').getForm().getValues();
						if (values.isException) {
							return true;
						}
						this.up('form').getForm().setValues({exceptionReason: ' '});
						return false;
					} 
				}*/
			} ]
		},
				{xtype : 'radiogroup',columnWidth: .5,vertical : true,name : 'paymentTypeName',fieldLabel : predeliver.arriveNotice.i18n('pkp.predeliver.notifyCustomer.paidMethod'),labelStype : {width : '70px'},
					items : [
						{boxLabel : predeliver.arriveNotice.i18n('pkp.predeliver.notifyCustomer.freightCollect'),name : 'paymentType',inputValue : 'CH', itemId:'CH', checked : true,style: {marginTop: '5px'}},//'到付'
						{boxLabel : predeliver.arriveNotice.i18n('pkp.predeliver.notifyCustomer.paidByMonth'),name : 'paymentType',inputValue : 'CT', itemId:'CT',  disabled : true,style: {marginTop: '5px'}},//'月结'
						{boxLabel : predeliver.arriveNotice.i18n('pkp.predeliver.notifyCustomer.temporaryDebt'),name : 'paymentType',inputValue : 'DT', itemId:'DT', disabled : true,style: {marginTop: '5px'}}//'临欠'
					]},
		{name : 'toPayAmount' , fieldStyle:'color:red;',fieldLabel : predeliver.arriveNotice.i18n('pkp.predeliver.notifyCustomer.toTotalPayAmount'), hidden:true}, // 到付总额
		{name : 'toPayAmountHiddenBefore' , columnWidth: .5 , labelWidth : 60, width : 288, fieldStyle:'color:red;',fieldLabel : predeliver.arriveNotice.i18n('pkp.predeliver.notifyCustomer.toTotalPayAmount'), readOnly : true}, // 到付总额
		{
			xtype : 'container',columnWidth: 1,
			layout : 'column',
			items : [{
				xtype : 'combobox',
				fieldLabel : '发票类型',
				name: 'invoiceType',
				labelWidth : 84,
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
						var deliverRequireField = formPanel.down('textarea[name=deliverRequire]');
						var deliverRequireValue = deliverRequireField.getValue();
						if (combobox.getRawValue()) {
							deliverRequireField.setValue(deliverRequireValue + '【' + combobox.getRawValue() + '】');
						}
					}
				}
			},{
				xtype : 'textfield',
				labelWidth : 60,
				name: 'invoiceDetail',
				fieldLabel : '发票备注',
				margin : '0 0 0 28px',
				columnWidth: 0.55,
				width : 288	,
				maxLength: 30,
				maxLengthText: '已超出字数最大限制!',
				listeners : {
					blur : function(text, newValue, oldValue, eOpts ) {
						if (!text.isValid()) {
							return ;
						}
						var formPanel = text.up('form');
						var invoiceTypeField = formPanel.down('combobox[name=invoiceType]');
						var deliverRequireField = formPanel.down('textarea[name=deliverRequire]');
						var deliverRequireValue = deliverRequireField.getValue();
						if (text.getValue()) {
							deliverRequireField.setValue(deliverRequireValue + '【' + text.getValue() + '】');
						}
					}
				}
			}]
		}, //发票类型
		{
			xtype : 'textarea',
			height:50,
			name: 'receiptHabitRemark',
			fieldLabel : '收货习惯备注',
			maxLength : 200,
			columnWidth: 1,
			readOnly: true,
			readOnlyCls:'.x-form-readonly input, .x-form-readonly textarea {background: transparent!important;}',
			labelWidth:84
		}
		,
		{name : 'waybillNo' , xtype : 'hiddenfield'},
		{name : 'taskStatus',xtype : 'hiddenfield'},
		{name : 'arriveGoodsQty',xtype : 'hiddenfield'} , 
		{name : 'receiveCustomerCode' , xtype : 'hiddenfield'},
		{name : 'receiveCustomerName' , xtype : 'hiddenfield'},
		{name : 'receiveCustomerPhone' , xtype : 'hiddenfield'},
		{name : 'receiveMethod' , xtype : 'hiddenfield'}
		],
	constructor: function(config){ 
		var me = this,
			cfg = Ext.apply({}, config);
		me.bbar = [{
			  text : '收货习惯',  //收货习惯按钮
			  id:'arriveNotice_ReceiptHabit_ID',
		      handler :  function(btn) {
		        var win = Ext.create('Foss.predeliver.arriveNotice.AddReceiptHabitWindow').show();
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
						if (habit_deliveryTimeInterval) {
							win.down('textfield[name=deliveryTimeInterval]').setValue(habit_deliveryTimeInterval);
						}
						if (habit_deliveryTimeStart) {
							win.down('timefield[name=deliveryTimeStart]').setValue(habit_deliveryTimeStart);
						}
						if (habit_deliveryTimeOver) {
							win.down('timefield[name=deliveryTimeOver]').setValue(habit_deliveryTimeOver);
						}
						if (habit_invoiceType) {
							win.down('combobox[name=invoiceType]').setValue(habit_invoiceType);
						}
						if (habit_invoiceDetail) {
							win.down('textfield[name=invoiceDetail]').setValue(habit_invoiceDetail);
						}
						if (habit_receiptHabitRemark) {
							win.down('textarea[name=receiptHabitRemark]').setValue(habit_receiptHabitRemark);
						}
		      }
		},'->',{
			text : predeliver.arriveNotice.i18n('pkp.predeliver.notifyCustomer.save'),//'保存'
			disabled:!predeliver.arriveNotice.isPermission('arriveNotice/savebutton'),
			hidden:!predeliver.arriveNotice.isPermission('arriveNotice/savebutton'),
			id:'Foss_predeliver_arriveNotice_NoticeInfoFormPanel_saveButten_Id',
			plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin', {
				seconds: 3
			}),
			handler : function() {
				var recordsModel = Ext.getCmp('Foss.predeliver.grid.arriveNoticeGrid_ID').getSelectionModel(),
					records=recordsModel.getSelection(),
					 wayBillInfoForm = Ext.getCmp('Foss.predeliver.arriveNotice.WayBillInfoFormPanel_id').getForm(),
					 waybillNos = '', array = new Array(), notificationEntity = '',taskStatus = '',selectType = '',currentWaybillNo=false;
				if(records.length!=1){
	    			Ext.ux.Toast.msg(predeliver.arriveNotice.i18n('pkp.predeliver.notifyCustomer.tip')/*提示*/, predeliver.arriveNotice.i18n('pkp.predeliver.arriveNotice.has.choose.one'), 'error', 1000);//请选择一条运单信息
	    			return;
	    		}
				var form = this.up('form').getForm(), values = form.getValues();
				if ( Ext.isEmpty(values.noticeResult)) {
					// 通知结果为空
					Ext.ux.Toast.msg(predeliver.arriveNotice.i18n('pkp.predeliver.notifyCustomer.tip')/*提示*/, predeliver.arriveNotice.i18n('pkp.predeliver.arriveNotice.noticeResult.has.choose'), 'error', 2000);//通知状态必须选择
					return;
				}
				if (Ext.isEmpty(values.deliverRequire.trim())) {
					Ext.ux.Toast.msg(predeliver.arriveNotice.i18n('pkp.predeliver.notifyCustomer.tip')/*提示*/, '请录入送货要求再进行保存', 'error', 2000);//'请录入/生成通知内容再进行保存。'
					return;
				}
				if (form.isValid()) {
					//如果查询规则是以运单查询，库存件数为0 则为提前通知
					if(predeliver.arriveNotice.SELECT_TYPE!=''&&predeliver.arriveNotice.SELECT_TYPE=='4'){
						var stockQty=wayBillInfoForm.findField('stockQty').getValue();
						if(stockQty==''||stockQty=='0'){
							values.isPreNotify='Y'
						}
					}
					if(wayBillInfoForm.findField('waybillNo').getValue()==null ||wayBillInfoForm.findField('waybillNo').getValue()==''){
						Ext.ux.Toast.msg(predeliver.arriveNotice.i18n('pkp.predeliver.notifyCustomer.tip')/*提示*/, predeliver.arriveNotice.i18n('pkp.predeliver.arriveNotice.load.failure'), 'error', 2000);//运单信息未加载成功，请重新选择一行运单信息
						return;
					}
					if(wayBillInfoForm.findField('waybillNo').getValue() != records[0].data['waybillNo']){
		    			Ext.ux.Toast.msg(predeliver.arriveNotice.i18n('pkp.predeliver.notifyCustomer.tip')/*提示*/,'当前运单加载的信息与选中的运单信息不一致，请重新选择', 'error', 2000);//当前运单加载的信息与选中的运单信息不一致，请重新选择
		    			return;
		    		}
					//判断多票信息
					var multiBillGridModel = Ext.getCmp('Foss.predeliver.grid.multiBillGrid_id2').getSelectionModel(),
					multiBillGrid= multiBillGridModel.getSelection(),
					multiBillGridStoreCount=Ext.getCmp('Foss.predeliver.grid.multiBillGrid_id2').getStore().getCount(),
					containArray = new Array(),
					arriveNoticeGrid=Ext.getCmp('Foss.predeliver.grid.arriveNoticeGrid_ID').getStore().data;
					if(multiBillGridStoreCount>0){
						for (var i = 0; i < multiBillGrid.length; i++) {//判断多票信息里选中的运单包含当前运单否
							if(multiBillGrid[i].data.waybillNo===wayBillInfoForm.findField('waybillNo').getValue()){
								currentWaybillNo=true;
								break;
							}
						}
						if(multiBillGrid.length>0 &&currentWaybillNo ===true){
								for (var i = 0; i < multiBillGrid.length; i++) {
									for (var j = 0; j < arriveNoticeGrid.length; j++){
										if(multiBillGrid[i].data.waybillNo ==arriveNoticeGrid.items[j].data.waybillNo){
											containArray.push(arriveNoticeGrid.items[j]);
											break;
										}
											
									}
									var stockGoodsQty=multiBillGrid[i].data.stockGoodsQty,
										taskStatus=multiBillGrid[i].data.taskStatus,
										isPreNotify='';
									if(stockGoodsQty==''||stockGoodsQty=='0'||taskStatus=='UNDEPART'||taskStatus=='ONTHEWAY'){
										isPreNotify='Y'
									}
									notificationEntity = {'waybillNo' : multiBillGrid[i].data.waybillNo,
										'receiveCustomerMobilephone' : multiBillGrid[i].data.receiveCustomerMobilephone,
										'deliverDate':values.deliverDate,
										'deliverRequire':values.deliverRequire,
										'isSentRequired':values.isSentRequired,
										'paymentType':values.paymentType,
										'noticeResult' : values.noticeResult,
										'isPreNotify':isPreNotify
										};
								if (multiBillGrid[i].data.waybillNo == values.waybillNo) {
									 notificationEntity = values;
									 notificationEntity.receiveCustomerMobilephone = values.receiveCustomerMobilephone;
									 //保存转换实际收货地址
									 if (Ext.getCmp('arrive_ActualAddress_ID').getProvince().value) {
										notificationEntity.actualProvCode = Ext.getCmp('arrive_ActualAddress_ID').getProvince().value;
									 }
									 if (Ext.getCmp('arrive_ActualAddress_ID').getCity().value) {
										notificationEntity.actualCityCode = Ext.getCmp('arrive_ActualAddress_ID').getCity().value;
									 }
									 if (Ext.getCmp('arrive_ActualAddress_ID').getCounty().value) {
										notificationEntity.actualDistrictCode = Ext.getCmp('arrive_ActualAddress_ID').getCounty().value;
									 }
								}
              
									array.push(notificationEntity);
								}
								var newVo = {
										'vo': {
											'conditionDto' : {'notificationEntityList' :array,'isTogetherSend':'Y',
                          'notifyCustomerDto':{'receiveCustomerName':'','receiveCustomerPhone':''}
											}
										}
									};
                  
								if (!Ext.isEmpty(values.receiveCustomerName)) {
									 newVo.vo.conditionDto.notifyCustomerDto.receiveCustomerName = values.receiveCustomerName;
								}
							   if (!Ext.isEmpty(values.receiveCustomerPhone)) {
								 newVo.vo.conditionDto.notifyCustomerDto.receiveCustomerPhone = values.receiveCustomerPhone;
							   }
							   //送货时间点限制
							   if((!Ext.isEmpty(values.deliveryTimeStart)) &&(!Ext.isEmpty(values.deliveryTimeOver))){
                   if(values.deliveryTimeStart>values.deliveryTimeOver){
                      Ext.ux.Toast.msg('提示','最早送货时间点不能大于最晚送货时间点!', 'error', 2000);
                     return false;
                   }
							   }
                  
									Ext.Ajax.request({
										url:predeliver.realPath('arriveNoticeBatchNotify.action'),
										jsonData : newVo,
										success: function(response){
											Ext.each(containArray, function(item, index, allItems){
												item.set('noticeResult',values.noticeResult);
												item.set('notificationTime',new Date());
												if(values.noticeResult=='SUCCESS'){
													//records[0].data.isNoticeSuccess='Y';
													item.set('isNoticeSuccess','Y');
												}
											});
											if(values.noticeResult!='SUCCESS'){
												 var  mergebutton = Ext.getCmp('Foss_predeliver_arriveNotice_multiBillGrid_mergeButton_Id'),
													 relieveButton = Ext.getCmp('Foss_predeliver_arriveNotice_multiBillGrid_relieveButton_Id');
												 	relieveButton.setDisabled(true); //设置解绑按钮
													mergebutton.setDisabled(false); //设置合送按钮 
													multiBillGridModel.deselectAll(); 
													Ext.ux.Toast.msg(predeliver.arriveNotice.i18n('pkp.predeliver.notifyCustomer.tip')/*提示*/, '保存成功！解绑成功！', 'ok', 1000);
											}else{
												Ext.ux.Toast.msg(predeliver.arriveNotice.i18n('pkp.predeliver.notifyCustomer.tip')/*提示*/, '多票信息通知成功！', 'ok', 1000);
											
											}
										},
										 exception: function(response) {
							                var json = Ext.decode(response.responseText);
							                Ext.ux.Toast.msg(predeliver.arriveNotice.i18n('pkp.predeliver.notifyCustomer.batch.notify.failuer'), json.message, 'error', 3000);//'批量通知失败'
						                }
									});
						}
						if(multiBillGrid.length==0 ||currentWaybillNo==false) {
							array.push(values);
							var newVo = {'vo': 
								{
									'conditionDto' :{'notificationEntityList' :array,'isTogetherSend':'N',
										'notifyCustomerDto':{'receiveCustomerName':'','receiveCustomerPhone':''}
									}
								}
							};
              
						  //保存转换实际收货地址
						  if (Ext.getCmp('arrive_ActualAddress_ID').getProvince().value) {
							newVo.vo.conditionDto.notificationEntityList[0].actualProvCode = Ext.getCmp('arrive_ActualAddress_ID').getProvince().value;
						  }
						  if (Ext.getCmp('arrive_ActualAddress_ID').getCity().value) {
							newVo.vo.conditionDto.notificationEntityList[0].actualCityCode = Ext.getCmp('arrive_ActualAddress_ID').getCity().value;
						  }
						  if (Ext.getCmp('arrive_ActualAddress_ID').getCounty().value) {
							newVo.vo.conditionDto.notificationEntityList[0].actualDistrictCode = Ext.getCmp('arrive_ActualAddress_ID').getCounty().value;
						  }
						  
						  if (!Ext.isEmpty(values.receiveCustomerName)) {
							 newVo.vo.conditionDto.notifyCustomerDto.receiveCustomerName = values.receiveCustomerName;
						  }
						  if (!Ext.isEmpty(values.receiveCustomerPhone)) {
							 newVo.vo.conditionDto.notifyCustomerDto.receiveCustomerPhone = values.receiveCustomerPhone;
						  }
						  //送货时间点限制
						  if((!Ext.isEmpty(values.deliveryTimeStart)) &&(!Ext.isEmpty(values.deliveryTimeOver))){
							if(values.deliveryTimeStart>values.deliveryTimeOver){
							   Ext.ux.Toast.msg('提示','最早送货时间点不能大于最晚送货时间点!', 'error', 2000);
							  return false;
							}
						  }
              
							Ext.Ajax.request({
								url:predeliver.realPath('arriveNoticeBatchNotify.action'),
								jsonData: newVo,
								success: function(response){
									Ext.ux.Toast.msg(predeliver.arriveNotice.i18n('pkp.predeliver.notifyCustomer.tip')/*提示*/, predeliver.arriveNotice.i18n('pkp.predeliver.notifyCustomer.save.success'), 'ok', 1000);//'保存成功！'
									records[0].set('noticeResult',values.noticeResult);
									records[0].set('notificationTime',new Date());
									if(values.noticeResult=='SUCCESS'){
										records[0].data.isNoticeSuccess='Y';
									}
								}, 
								exception: function(response) {
									var json = Ext.decode(response.responseText);
									Ext.ux.Toast.msg(predeliver.arriveNotice.i18n('pkp.predeliver.notifyCustomer.save.failuer'), json.message, 'error', 2000);//'保存失败'
								},
								scope : this
							});
						}
					}
					if (multiBillGridStoreCount==0) {
						
						var array = {'vo':{'conditionDto':{'notificationEntity':values,'invoiceInfomationEntity':values, 
																							 'notifyCustomerDto':{'receiveCustomerName':'','receiveCustomerPhone':''}}}};
						
						//保存转换实际收货地址
						if (Ext.getCmp('arrive_ActualAddress_ID').getProvince().value) {
							array.vo.conditionDto.notificationEntity.actualProvCode = Ext.getCmp('arrive_ActualAddress_ID').getProvince().value;
						}
						if (Ext.getCmp('arrive_ActualAddress_ID').getCity().value) {
							array.vo.conditionDto.notificationEntity.actualCityCode = Ext.getCmp('arrive_ActualAddress_ID').getCity().value;
						}
						if (Ext.getCmp('arrive_ActualAddress_ID').getCounty().value) {
							array.vo.conditionDto.notificationEntity.actualDistrictCode = Ext.getCmp('arrive_ActualAddress_ID').getCounty().value;
						}
						
						if (!Ext.isEmpty(values.receiveCustomerName)) {
							 array.vo.conditionDto.notifyCustomerDto.receiveCustomerName = values.receiveCustomerName;
						}
						if (!Ext.isEmpty(values.receiveCustomerPhone)) {
							 array.vo.conditionDto.notifyCustomerDto.receiveCustomerPhone = values.receiveCustomerPhone;
						}
						//送货时间点限制
						if((!Ext.isEmpty(values.deliveryTimeStart)) &&(!Ext.isEmpty(values.deliveryTimeOver))){
							if(values.deliveryTimeStart>values.deliveryTimeOver){
								 Ext.ux.Toast.msg('提示','最早送货时间点不能大于最晚送货时间点!', 'error', 2000);
								return false;
							}
						}
						
						Ext.Ajax.request({
							url:predeliver.realPath('addArriveNotice.action'),
							jsonData: array,
							success: function(response){
								Ext.ux.Toast.msg(predeliver.arriveNotice.i18n('pkp.predeliver.notifyCustomer.tip')/*提示*/, predeliver.arriveNotice.i18n('pkp.predeliver.notifyCustomer.save.success'), 'ok', 1000);//'保存成功！'
								records[0].set('noticeResult',values.noticeResult);
								records[0].set('notificationTime',new Date());
								if(values.noticeResult=='SUCCESS'){
									records[0].data.isNoticeSuccess='Y';
								}
							},
							exception: function(response) {
								var json = Ext.decode(response.responseText);
								Ext.ux.Toast.msg(predeliver.arriveNotice.i18n('pkp.predeliver.notifyCustomer.save.failuer'), json.message, 'error', 2000);//'保存失败'
							},
							scope : this
						});
					}
					
				}else {
					Ext.ux.Toast.msg(predeliver.arriveNotice.i18n('pkp.predeliver.notifyCustomer.tip')/*提示*/, '通知信息填写不正确，保存失败', 'error', 2000);
					return;
				}
			}}];
		me.callParent([cfg]);
	}
});
Ext.define('Foss.predeliver.arriveNotice.warn', {
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
		items : [ , {
			xtype : 'label',
			text : predeliver.arriveNotice.i18n('pkp.predeliver.notifyCustomer.success')
		}, {
			xtype : 'image',
			imgCls : 'foss_icons_pkp_noticeOk'
		},{
			xtype : 'label',
			text : predeliver.arriveNotice.i18n('pkp.predeliver.notifyCustomer.failuer')
		}, {
			xtype : 'image',
			imgCls : 'foss_icons_pkp_noticeError'
		},]
			});
		me.callParent(arguments);
	}
});

Ext.onReady(function() {
	Ext.QuickTips.init();
	var queryForm          = Ext.create('Foss.predeliver.form.ArriveNoticeSearch'),
	    bigNOticeDownPanel = Ext.create('Foss.predeliver.arriveNotice.BigDownPanel'),
	    warnPanel          = Ext.create('Foss.predeliver.arriveNotice.warn'),
	    arriveNoticeGrid   = Ext.create('Foss.predeliver.grid.arriveNoticeGrid');
	
	Ext.create('Ext.panel.Panel', {
		id : 'T_predeliver-arriveNotice_content',
		cls : 'panelContentNToolbar',
		bodyCls : 'panelContentNToolbar-body',
		layout : 'auto',
		getQueryForm: function(){
			return queryForm;
		},
		getBigNOticeDownPanel: function(){
			return bigNOticeDownPanel;
		},
		getWarnPanel: function(){
			return warnPanel;
		},
		getArriveNoticeGrid: function(){
			return arriveNoticeGrid;
		},
		margin : '2 2 2 2',
		items : [queryForm, warnPanel,bigNOticeDownPanel],
		renderTo : 'T_predeliver-arriveNotice-body'
	});
});
