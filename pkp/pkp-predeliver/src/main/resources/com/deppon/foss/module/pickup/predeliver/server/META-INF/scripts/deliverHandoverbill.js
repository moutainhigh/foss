predeliver.deliverHandoverbill.getStockTimeStart = function(date, day) {
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
predeliver.deliverHandoverbill.getStockTimeEnd = function(date, day) {
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
predeliver.deliverHandoverbill.checkPreDeliverbillQuery = function(myForm) {
    var waybillNo = myForm.getValues().waybillNo,
    	handoverNo = myForm.getValues().handoverNo,
    	vehicleAssembleNo = myForm.getValues().vehicleAssembleNo,
        stockStatus = myForm.getValues().stockStatus,
        stockTimeFrom= myForm.getValues().stockTimeFrom,
        stockTimeTo= myForm.getValues().stockTimeTo,
        preDeliverDateFrom=myForm.getValues().preDeliverDateFrom,
        preDeliverDateTo=myForm.getValues().preDeliverDateTo;
    if(!myForm.isValid()){
        return false;
    }
    // 验证运单号输入的行数
    if (!Ext.isEmpty(waybillNo)) {
        var arrayWaybillNo = waybillNo.split('\n');
        if (arrayWaybillNo.length > 50) {
            Ext.ux.Toast.msg(predeliver.deliverHandoverbill.i18n('pkp.predeliver.notifyCustomer.tip'), predeliver.deliverHandoverbill.i18n('pkp.predeliver.notifyCustomer.waybillNo.valitation'), 'error', 3000);
            return false;
        }
        for (var i = 0; i < arrayWaybillNo.length; i++) {
            if (Ext.isEmpty(arrayWaybillNo[i])) {
                Ext.ux.Toast.msg(predeliver.deliverHandoverbill.i18n('pkp.predeliver.notifyCustomer.tip'), predeliver.deliverHandoverbill.i18n('pkp.predeliver.notifyCustomer.waybillNo.valitation'), 'error', 3000);
                return false;
            }
        }
    }else if(!Ext.isEmpty(handoverNo)){
    }else if(!Ext.isEmpty(vehicleAssembleNo)){
    }else{
        //时间范围验证
        if (!Ext.isEmpty(stockTimeTo) && !Ext.isEmpty(stockTimeFrom)) {
            var result = Ext.Date.parse(stockTimeTo,'Y-m-d H:i:s') - Ext.Date.parse(stockTimeFrom,'Y-m-d H:i:s');
            if(result / (24 * 60 * 60 * 1000) > 7){
                Ext.ux.Toast.msg(predeliver.deliverHandoverbill.i18n('pkp.predeliver.notifyCustomer.tip'), '时间范围起止日期相隔不能超过7天', 'error', 3000);
                return false;
            }
        }
        //时间范围验证
        if (!Ext.isEmpty(preDeliverDateFrom) && !Ext.isEmpty(preDeliverDateTo)) {
            var result = Ext.Date.parse(preDeliverDateTo,'Y-m-d') - Ext.Date.parse(preDeliverDateFrom,'Y-m-d');
            if(result / (24 * 60 * 60 * 1000) >= 30){
                Ext.ux.Toast.msg(predeliver.deliverHandoverbill.i18n('pkp.predeliver.notifyCustomer.tip'), '预计送货日期相隔不能超过30天', 'error', 3000);
                return false;
            }
        }
    }

    // 未录入运单号、交接单号的情况下必须录入时间
    if (Ext.isEmpty(waybillNo)  && Ext.isEmpty(handoverNo)&& Ext.isEmpty(vehicleAssembleNo)) {
        if (Ext.isEmpty(stockTimeFrom) || Ext.isEmpty(stockTimeTo) ||Ext.isEmpty(stockStatus)
            ) {
            Ext.ux.Toast.msg(predeliver.deliverHandoverbill.i18n('pkp.predeliver.notifyCustomer.tip'), '运单号、交接单号、车次号为空时,时间范围必须选择!', 'error', 3000);
            return false;
        }
        if ((!Ext.isEmpty(preDeliverDateTo) && Ext.isEmpty(preDeliverDateFrom))
            || (!Ext.isEmpty(preDeliverDateFrom) && Ext.isEmpty(preDeliverDateTo))
            ) {
            Ext.ux.Toast.msg(predeliver.deliverHandoverbill.i18n('pkp.predeliver.notifyCustomer.tip'), '预计送货日期相隔不能超过30天', 'error', 3000);
            return false;
        }
    }
    return true;
};
predeliver.deliverHandoverbill.checkDeliverbillQuery = function(myForm) {
    var waybillNo = myForm.getValues().waybillNo,
        preDeliverDateFrom=myForm.getValues().preDeliverDateFrom,
        preDeliverDateTo=myForm.getValues().preDeliverDateTo,
        handoverbillTimeEnd=myForm.getValues().handoverbillTimeEnd,
        handoverbillTimeStart=myForm.getValues().handoverbillTimeStart;
    if(!myForm.isValid()){
        return false;
    }
    // 验证运单号输入的行数
    if (!Ext.isEmpty(waybillNo)) {
        var arrayWaybillNo = waybillNo.split('\n');
        if (arrayWaybillNo.length > 50) {
            Ext.ux.Toast.msg(predeliver.deliverHandoverbill.i18n('pkp.predeliver.notifyCustomer.tip'), predeliver.deliverHandoverbill.i18n('pkp.predeliver.notifyCustomer.waybillNo.valitation'), 'error', 3000);
            return false;
        }
        for (var i = 0; i < arrayWaybillNo.length; i++) {
            if (Ext.isEmpty(arrayWaybillNo[i])) {
                Ext.ux.Toast.msg(predeliver.deliverHandoverbill.i18n('pkp.predeliver.notifyCustomer.tip'), predeliver.deliverHandoverbill.i18n('pkp.predeliver.notifyCustomer.waybillNo.valitation'), 'error', 3000);
                return false;
            }
        }
    }else{
        if (Ext.isEmpty(handoverbillTimeEnd) || Ext.isEmpty(handoverbillTimeStart) ||Ext.isEmpty(preDeliverDateFrom)||Ext.isEmpty(preDeliverDateTo)
            ) {
            Ext.ux.Toast.msg(predeliver.deliverHandoverbill.i18n('pkp.predeliver.notifyCustomer.tip'), '运单号为空时,时间范围必须选择!', 'error', 3000);
            return false;
        }
        if (!Ext.isEmpty(handoverbillTimeEnd) && !Ext.isEmpty(handoverbillTimeStart)) {
            var result = Ext.Date.parse(handoverbillTimeEnd,'Y-m-d H:i:s') - Ext.Date.parse(handoverbillTimeStart,'Y-m-d H:i:s');
            if(result / (24 * 60 * 60 * 1000) > 3){
                Ext.ux.Toast.msg(predeliver.deliverHandoverbill.i18n('pkp.predeliver.notifyCustomer.tip'), '交单起止日期相隔不能超过3天', 'error', 3000);
                return false;
            }
        }
        if (!Ext.isEmpty(preDeliverDateFrom) && !Ext.isEmpty(preDeliverDateTo)) {
            var result = Ext.Date.parse(preDeliverDateTo,'Y-m-d') - Ext.Date.parse(preDeliverDateFrom,'Y-m-d');
            if(result / (24 * 60 * 60 * 1000) >= 30){
                Ext.ux.Toast.msg(predeliver.deliverHandoverbill.i18n('pkp.predeliver.notifyCustomer.tip'), predeliver.deliverHandoverbill.i18n('pkp.predeliver.notifyCustomer.the.date.range.cannot.be.more.than.30.days'), 'error', 3000);
                return false;
            }
        }
    }

    return true;
};
//送货时间段集合
Ext.define('Foss.predeliver.deliverHandoverbill.deliveryTimeIntervalStore',{
    extend: 'Ext.data.Store',
    fields: [
        {name: 'valueCode',  type: 'string'},
        {name: 'valueName',  type: 'string'}
    ],
    data: {
        'items':[
            {'valueCode':'','valueName':'全部'},
            {'valueCode':'全天','valueName':'全天'},
            {'valueCode':'上午','valueName':'上午'},
            {'valueCode':'下午','valueName':'下午'}
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
//送货时间段集合
Ext.define('Foss.predeliver.deliverHandoverbill.deliveryTimeIntervalStore1',{
    extend: 'Ext.data.Store',
    fields: [
        {name: 'valueCode',  type: 'string'},
        {name: 'valueName',  type: 'string'}
    ],
    data: {
        'items':[
            {'valueCode':'N/A','valueName':'无'},
            {'valueCode':'全天','valueName':'全天'},
            {'valueCode':'上午','valueName':'上午'},
            {'valueCode':'下午','valueName':'下午'}
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
// 货物状态集合
Ext.define('Foss.predeliver.deliverHandoverbill.stockSituationStore', {
    extend : 'Ext.data.Store',
    fields : [{
        name : 'code',
        type : 'string'
    }, {
        name : 'name',
        type : 'string'
    }],
    data : {
        'items' : [
            {
                'code' : 'ONTHEWAY',
                'name' : '预计到达'
            },// 未入库
            {
                'code' : 'ARRIVED',
                'name' : '已到达'
            },// 库存中
            {
                'code' : 'IN_STOCK',
                'name' : '库存中'
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
//定义待交单查询结果model
Ext.define('Foss.predeliver.deliverHandoverbill.PreDeliverHandoverbillModel', {
    extend : 'Ext.data.Model',
    idgen : 'uuid',// EXT在前台为每个模型额外以UUID方式生成主键
    idProperty : 'extid',// 以上生成的主键用在名叫“extid”的列
    fields : [
        {
            name : 'id', // ID
            type : 'string'
        },{
            name : 'pendingType', // 运单状态
            type : 'string'
        }, {
            name : 'waybillNo', // 运单号
            type : 'string'
        }, {
            name : 'cashTime', // 规定兑现时间
            type : 'string',
            convert: function(value) {
                if (value != null) {
                    var date = new Date(value);
                    return Ext.Date.format(date,'Y-m-d H:i');
                } else {
                    return null;
                }
            }
        }, {
            name : 'productCode', // 运输性质
            type : 'string'
        }, {
            name : 'receiveMethod', // 提货方式
            type : 'string'
        }, {
            name : 'billQty', // 待交件数
            type : 'int'
        }, {
            name : 'goodsQtyTotal', // 开单件数
            type : 'int'
        }, {
            name : 'arriveGoodsQty', // 到达件数
            type : 'int'
        }, {
            name : 'stockGoodsQty', // 库存件数
            type : 'int'
        }, {
            name : 'goodsWeight', // 重量
            type : 'float'
        }, {
            name : 'goodsVolume', // 体积
            type : 'float'
        }, {
            name : 'stockStatus', // 货物状态
            type : 'string'
        }, {
            name : 'noticeResult', // 通知情况
            type : 'string'
        }, {
            name : 'preDeliverDate', // 预计送货时间
            type : 'date',
            convert: function(value) {
                if (value != null) {
                    var date = new Date(value);
                    return Ext.Date.format(date,'Y-m-d');
                } else {
                    return null;
                }
            }
        }, {
            name : 'deliveryTimeInterval', // 送货时间段
            type : 'string'
        }, {
            name : 'deliveryTimeStart', // 送货时间点(起)
            type : 'string'
        }, {
            name : 'deliveryTimeOver', // 送货时间点(止)
            type : 'string'
        }, {
            name : 'invoiceType', // 发票类型
            type : 'string'
        }, {
            name : 'planArriveTime', // 预计到达时间
            type : 'date',
            convert: function(value) {
                if (value != null) {
                    var date = new Date(value);
                    return Ext.Date.format(date,'Y-m-d H:i:s');
                } else {
                    return null;
                }
            }
        }, {
            name : 'inStockTime', // 入库时间
            type : 'date',
            convert: function(value) {
                if (value != null) {
                    var date = new Date(value);
                    return Ext.Date.format(date,'Y-m-d H:i:s');
                } else {
                    return null;
                }
            }
        }, {
            name : 'deliverReturnReasoNotes', // 调度退回原因备注
            type : 'string'   
        }, {
            name : 'deliverReturnReason', // 调度退回原因
            type : 'string',
			convert : function(value, record) {
				if (value!=null && value!='') {
					var  deliverReturnReason =FossDataDictionary.rendererSubmitToDisplay(value, 'PKP_VISIBLE_WAYBILL_RETURN');
					if((!Ext.isEmpty(record.get('deliverReturnReasoNotes')))&& record.get('deliverReturnReasoNotes')!=' '){
						return deliverReturnReason+'-'+record.get('deliverReturnReasoNotes');
					}else{
						return deliverReturnReason;
					}
				} else {
					if(record.get('deliverReturnReasoNotes') != null){
						return record.get('deliverReturnReasoNotes');
					}
				}
			}
        }, {
            name : 'deliverReturnOperate', // 调度退回操作人
            type : 'string'
        }, {
            name : 'deliverReturnTime', // 调度退回时间
            type : 'date',
            convert: function(value) {
                if (value != null) {
                    var date = new Date(value);
                    return Ext.Date.format(date,'Y-m-d H:i:s');
                } else {
                    return null;
                }
            }
        }, {
            name : 'hasException', // 有异常未处理
            type : 'string'
        }, {
            name : 'waybillRfcStatus', // 更改单未受理
            type : 'string'
        }, {
            name : 'receiveCustomerProvCode', // 收货省份
            type : 'string'
        }, {
            name : 'receiveCustomerCityCode', // 收货市
            type : 'string'
        }, {
            name : 'receiveCustomerDistCode', // 收货区
            type : 'string'
        }, {
            name : 'receiveCustomerAddress', // 收货具体地址
            type : 'string'
        }, {
            name : 'receiveCustomerAddressNote', // 收货人地址备注
            type : 'string'
        }, {
            name : 'toPayAmount', // 到付金额
            type : 'float'
        }, {
            name : 'paidMethod', // 开单付款方式
            type : 'string'
        }, {
            name : 'deliverBillQty', //已交单件数
            type : 'int'
        }, {
            name : 'orgRoleType', //部门角色类型
            type : 'int'
        },{
            name : 'nowDate', //当前时间
            type : 'date',
            convert: function(value) {
                if (value != null) {
                    var date = new Date(value);
                    return Ext.Date.format(date,'Y-m-d');
                } else {
                    return null;
                }
            }
        }, {
            name : 'artificialMark', // 人工能否未入库交单　　　
            type : 'string'
        }, {
            name : 'selectRoleType', // 查询条件类型
            type : 'string'
        },{
            name : 'goodsPackage', // 货物包装
            type : 'string'
        },{
            name : 'goodsSize', // 货物尺寸
            type : 'string'
        },{
            name : 'isOLNotPaid', // 开单付款方式为网上支付且未支付运单   Y
            type : 'string'
        }]

});
//定义已交单查询结果model
Ext.define('Foss.predeliver.deliverHandoverbill.DeliverHandoverbillModel', {
    extend : 'Ext.data.Model',
    idgen : 'uuid',// EXT在前台为每个模型额外以UUID方式生成主键
    idProperty : 'extid',// 以上生成的主键用在名叫“extid”的列
    fields : [
        {
            name : 'id', // ID
            type : 'string'
        },{
            name : 'waybillNo', // 运单号
            type : 'string'
        },{
            name : 'cashTime', // 规定兑现时间
            type : 'string',
            convert: function(value) {
            	if (value != null) {
            		var date = new Date(value);
            		return Ext.Date.format(date,'Y-m-d H:i');
            	} else {
            		return null;
            	}
            }           
        },  {
            name : 'productCode', // 运输性质
            type : 'string',
            convert:function(value) {
               return Foss.pkp.ProductData.rendererSubmitToDisplay(value);
             }
        }, {
            name : 'receiveMethod', // 提货方式
            type : 'string',
            convert:function(value) {
            	var v = FossDataDictionary.rendererSubmitToDisplay(value, 'PICKUPGOODSHIGHWAYS');
				if(Ext.isEmpty(v) || value == v){
					v = FossDataDictionary.rendererSubmitToDisplay(value, 'PICKUPGOODSSPECIALDELIVERYTYPE');
				}
				return v;
            }
        }, {
            name : 'billQty', // 交件件数
            type : 'int'
        }, {
            name : 'goodsQtyTotal', // 开单件数
            type : 'int'
        }, {
            name : 'arrangeGoodsQty', // 排单件数
            type : 'int'
        },  {
            name : 'goodsWeight', // 重量
            type : 'float'
        }, {
            name : 'goodsVolume', // 体积
            type : 'float'
        },{
            name : 'noticeContent', // 通知情况
            type : 'string',
            convert:function(value) {
                return FossDataDictionary.rendererSubmitToDisplay(value, 'PKP_NOTIFY_CUSTOMER_STATUS');
            }
        }, {
            name : 'preDeliverDate', // 预计送货时间
            type : 'date',
            convert: function(value) {
                if (value != null) {
                    var date = new Date(value);
                    return Ext.Date.format(date,'Y-m-d');
                } else {
                    return null;
                }
            }
        }, {
            name : 'deliveryTimeInterval', // 送货时间段
            type : 'string'
        }, {
            name : 'deliveryTimeStart', // 送货时间点(起)
            type : 'string'
        }, {
            name : 'deliveryTimeOver', // 送货时间点(止)
            type : 'string'
        }, {
            name : 'invoiceType', // 发票类型
            type : 'string',
            convert:function(value) {
                return FossDataDictionary.rendererSubmitToDisplay(value, 'PKP_RECEIPT_INVOICE_TYPE');
            }
        }, {
            name : 'billTime', // 交单时间
            type : 'date',
            convert: function(value) {
                if (value != null) {
                    var date = new Date(value);
                    return Ext.Date.format(date,'Y-m-d H:i:s');
                } else {
                    return null;
                }
            }
        }, {
            name : 'orgRoleType', //部门角色类型
            type : 'int'
        }, {
            name : 'billOperateName', //交单人
            type : 'string'
        },{
            name : 'deliverBillStatus', // 排单状态
            type : 'string',
            convert:function(value) {
                return FossDataDictionary.rendererSubmitToDisplay(value, 'PKP_DELIVERBILL_STATUS');
            }
        }, {
            name : 'invoiceDetail', //发票类型备注
            type : 'string'
        }]

});
//定义待交单查询的Store
Ext.define('Foss.predeliver.deliverHandoverbill.PreDeliverHandoverbillStore', {
    extend : 'Ext.data.Store',
    pageSize : 50,
    //autoLoad : false,
    timeout: 600000,
    // 绑定一个模型
    model : 'Foss.predeliver.deliverHandoverbill.PreDeliverHandoverbillModel',
    // 定义一个代理对象
    proxy : {
        type : 'ajax',
        actionMethods : 'POST',
        url : predeliver.realPath('queryPreDeliverHandover.action'),
        // 定义一个读取器
        reader : {
            // 以JSON的方式读取
            type : 'json',
            // 定义读取JSON数据的根对象
            root : 'vo.preDeliverHandoverbillDtos',
            totalProperty : 'totalCount',
            successProperty : 'success'
        }
    },
    /*constructor : function(config) {
     var me = this, cfg = Ext.apply({}, config);
     me.callParent([cfg]);
     },*/
    listeners : {
        beforeload : function(store, operation, eOpts) {
            var queryPreHandoverbillForm = Ext
                .getCmp('Foss_predeliver_deliverHandoverbill_PreHandoverbillQueryPanel_Id').getForm();
            if (queryPreHandoverbillForm != null) {
                var qForm = queryPreHandoverbillForm.getValues();
                if(!predeliver.deliverHandoverbill.checkPreDeliverbillQuery(queryPreHandoverbillForm)){
                    return false;
                }
                Ext.apply(operation, {
                    params : {
                        'vo.preDeliverHandoverbillQueryDto.waybillNo':qForm.waybillNo,
                        'vo.preDeliverHandoverbillQueryDto.handoverNo':qForm.handoverNo,
                        'vo.preDeliverHandoverbillQueryDto.vehicleAssembleNo':qForm.vehicleAssembleNo,
                        'vo.preDeliverHandoverbillQueryDto.productCode':qForm.productCode,
                        'vo.preDeliverHandoverbillQueryDto.receiveMethod':qForm.receiveMethod,
                        'vo.preDeliverHandoverbillQueryDto.position':qForm.position,
                        'vo.preDeliverHandoverbillQueryDto.stockStatus':qForm.stockStatus,
                        'vo.preDeliverHandoverbillQueryDto.stockTimeFrom':qForm.stockTimeFrom,
                        'vo.preDeliverHandoverbillQueryDto.stockTimeTo':qForm.stockTimeTo,
                        'vo.preDeliverHandoverbillQueryDto.noticeResult':qForm.noticeResult,
                        'vo.preDeliverHandoverbillQueryDto.preDeliverDateFrom':qForm.preDeliverDateFrom,
                        'vo.preDeliverHandoverbillQueryDto.preDeliverDateTo':qForm.preDeliverDateTo,
                        'vo.preDeliverHandoverbillQueryDto.deliveryTimeInterval':qForm.deliveryTimeInterval,
                        'vo.preDeliverHandoverbillQueryDto.deliveryTimeStart':qForm.deliveryTimeStart,
                        'vo.preDeliverHandoverbillQueryDto.deliveryTimeOver':qForm.deliveryTimeOver,
                        'vo.preDeliverHandoverbillQueryDto.noDeliverDate':qForm.noDeliverDate
                    }
                });
            }
        },
        load : function(store, records, successful,eOpts)  {
            if(successful==false){
                if(!store.proxy.reader.rawData.success){
                    Ext.ux.Toast.msg('提示', '查询失败，' + store.proxy.reader.rawData.message, 'error');
                }
            }
            Ext.getCmp('Foss_predeliver_deliverHandoverbill_PreHandoverbillGrid_totalCount_Id').setValue(store.totalCount);

        }
    }
});
//定义已交单查询的Store
Ext.define('Foss.predeliver.deliverHandoverbill.DeliverHandoverbillStore', {
    extend : 'Ext.data.Store',
    pageSize : 50,
    //autoLoad : false,
    timeout: 600000,
    // 绑定一个模型
    model : 'Foss.predeliver.deliverHandoverbill.DeliverHandoverbillModel',
    // 定义一个代理对象
    proxy : {
        type : 'ajax',
        actionMethods : 'POST',
        url : predeliver.realPath('queryDeliverHandoverList.action'),
        // 定义一个读取器
        reader : {
            // 以JSON的方式读取
            type : 'json',
            // 定义读取JSON数据的根对象
            root : 'vo.deliverHandoverbillDtos',
            totalProperty : 'totalCount',
            successProperty : 'success'
        }
    },
    /*constructor : function(config) {
     var me = this, cfg = Ext.apply({}, config);
     me.callParent([cfg]);
     },*/
    listeners : {
        beforeload : function(store, operation, eOpts) {
            var queryHandoverbillForm = Ext
                .getCmp('Foss_predeliver_deliverHandoverbill_HandoverbillQueryPanel_Id').getForm();
            if (queryHandoverbillForm != null) {
                var qForm = queryHandoverbillForm.getValues();
                var noArrageBill = '';
                if (!Ext.isEmpty(qForm.noArrageBill)) {
                  noArrageBill = 'Y';
                }
                if(!predeliver.deliverHandoverbill.checkDeliverbillQuery(queryHandoverbillForm)){
                    return false;
                }
                Ext.apply(operation, {
                    params : {
                        'vo.deliverHandoverbillQueryDto.waybillNo':qForm.waybillNo,
                        'vo.deliverHandoverbillQueryDto.productCode':qForm.productCode,
                        'vo.deliverHandoverbillQueryDto.receiveMethod':qForm.receiveMethod,
                        'vo.deliverHandoverbillQueryDto.noticeResult':qForm.noticeResult,
                        'vo.deliverHandoverbillQueryDto.preDeliverDateFrom':qForm.preDeliverDateFrom,
                        'vo.deliverHandoverbillQueryDto.preDeliverDateTo':qForm.preDeliverDateTo,
                        'vo.deliverHandoverbillQueryDto.deliveryTimeInterval':qForm.deliveryTimeInterval,
                        'vo.deliverHandoverbillQueryDto.deliveryTimeStart':qForm.deliveryTimeStart,
                        'vo.deliverHandoverbillQueryDto.deliveryTimeOver':qForm.deliveryTimeOver,
                        'vo.deliverHandoverbillQueryDto.handoverbillTimeStart':qForm.handoverbillTimeStart,
                        'vo.deliverHandoverbillQueryDto.handoverbillTimeEnd':qForm.handoverbillTimeEnd,
                        'vo.deliverHandoverbillQueryDto.deliverbillStatus':qForm.deliverbillStatus,
                        'vo.deliverHandoverbillQueryDto.handoverNo':qForm.handoverNo,
                        'vo.deliverHandoverbillQueryDto.vehicleAssembleNo':qForm.vehicleAssembleNo,
                        'vo.deliverHandoverbillQueryDto.noArrageBill':noArrageBill
                    }
                });
            }
        },
        load : function(store, records, successful,eOpts)  {
            if(successful==false){
                if(!store.proxy.reader.rawData.success){
                    Ext.ux.Toast.msg('提示', '查询失败，' + store.proxy.reader.rawData.message, 'error');
                }
            }
            Ext.getCmp('Foss_predeliver_deliverHandoverbill_HandoverbillGrid_totalCount_Id').setValue(store.totalCount);

        }
    }
});
//待交单运单-查询条件
Ext.define('Foss.predeliver.deliverHandoverbill.PreHandoverbillQueryPanel', {
    extend:'Ext.form.Panel',
    id:'Foss_predeliver_deliverHandoverbill_PreHandoverbillQueryPanel_Id',
    // 指定容器的标题
    title: '查询条件',//查询条件
    frame:true,
    //收缩
    collapsible: true,
    //动画收缩
    animCollapse: true,
    bodyCls: 'autoHeight',
    cls: 'autoHeight',
    labelAlign:'right',
    //默认边距 间隔
    defaults: {
        margin : '2 2 2 0',  //四边距  上右下左
        labelWidth: 100
    },
    layout: 'column',
    defaultType: 'textfield',
    // 定义容器中的项
    items: [{
        name : 'waybillNo',
        xtype : 'textarea',
        fieldLabel :'运单号',
        columnWidth : .18,
        labelWidth: 50,
        height:80,
        emptyText : predeliver.deliverHandoverbill.i18n('pkp.predeliver.notifyCustomer.waybillNo.valitation'),
        regex : /^([0-9]{8,10}\n?)+$/i,
        regexText : predeliver.deliverHandoverbill.i18n('pkp.predeliver.notifyCustomer.waybillNo.valitation')

    },
        {
            name : 'handoverNo',
            fieldLabel :'交接单号',
            columnWidth : .15,
            labelWidth: 70

        },
        {
            name : 'vehicleAssembleNo',
            fieldLabel :'车次号',
            columnWidth : .17,
            labelWidth: 65

        },{
            xtype:'combo',
            displayField:'name',
            valueField:'code',
            queryMode:'local',
            triggerAction:'all',
            value : '',
            editable:false,
            name : 'productCode',
            fieldLabel :'运输性质',
			 labelWidth: 70,
            columnWidth : .19,
            store : Ext.create('Foss.pkp.ProductStore')
        }, {
            xtype:'combo',
            displayField:'valueName',
            valueField:'valueCode',
            queryMode:'local',
            triggerAction:'all',
            value : '',
            editable:false,
            name : 'receiveMethod',
			labelWidth: 70,
            fieldLabel : '提货方式',
            columnWidth : .17,
            store :FossDataDictionary.getDataDictionaryStore('PICKUPGOODSHIGHWAYS', null,[
                {
                    'valueCode': '',
                    'valueName': '全部'
                }
            ])
        }, {
            xtype:'combo',
            displayField:'valueName',
            valueField:'valueCode',
            queryMode:'local',
            triggerAction:'all',
            value : '',
            editable:false,
            name : 'noticeResult',
			labelWidth: 70,
            fieldLabel : '通知情况',
            columnWidth : .14,
            store : FossDataDictionary.getDataDictionaryStore('PKP_NOTIFY_CUSTOMER_STATUS',null, {
                'valueCode': '',
                'valueName': predeliver.deliverHandoverbill.i18n('pkp.predeliver.notifyCustomer.complete')
            },['SUCCESS','FAILURE','NONE_NOTICE'])
        }, {
            xtype : 'combobox',
            displayField : 'name',
            valueField : 'code',
            queryMode : 'local',
            triggerAction : 'all',
            editable : false,
            name : 'stockStatus',
            fieldLabel : '货物状态',
            allowBlank : false,
			labelWidth: 70,
            columnWidth : .15,
            value : 'IN_STOCK',
            store : Ext.create('Foss.predeliver.deliverHandoverbill.stockSituationStore'),
            listeners : {
                'select' : function(combo, records, eOpts)
                {
                    var myForm = this.up('form').getForm();
                    if(records[0].data.code =='ONTHEWAY'){
                        myForm.findField('stockTimeFrom').setValue(Ext.Date.format(new Date(new Date().getFullYear(),
                                new Date().getMonth(),
                                new Date().getDate() ,
                                new Date().getHours(),
                                new Date().getMinutes(),
                                new Date().getSeconds()),
                            'Y-m-d H:i:s'));
                        myForm.findField('stockTimeTo').setValue(Ext.Date.format(new Date(new Date().getFullYear(),
                                new Date().getMonth(),
                                    new Date().getDate()+6,
                                new Date().getHours(),
                                new Date().getMinutes(),
                                new Date().getSeconds()),
                            'Y-m-d H:i:s'));
                    }else{
                        myForm.findField('stockTimeFrom').setValue(Ext.Date.format(new Date(new Date().getFullYear(),
                                new Date().getMonth(),
                                    new Date().getDate() - 6,
                                    new Date().getHours()+1,
                                new Date().getMinutes(),
                                new Date().getSeconds()),
                            'Y-m-d H:i:s'));
                        myForm.findField('stockTimeTo').setValue(Ext.Date.format(new Date(new Date().getFullYear(),
                                new Date().getMonth(),
                                new Date().getDate(),
                                    new Date().getHours()+1,
                                new Date().getMinutes(),
                                new Date().getSeconds()),
                            'Y-m-d H:i:s'));
                    }
                }
            }
        },{
            xtype : 'rangeDateField',
            fieldLabel : '时间范围',
            fieldId : 'Foss_predeliver_deliverHandoverbill_timeRanges_Id',
            dateType : 'datetimefield_date97',
			labelWidth: 65,
            fromName : 'stockTimeFrom',
            toName : 'stockTimeTo',
            fromValue: Ext.Date.format(new Date(new Date().getFullYear(),
                    new Date().getMonth(),
                        new Date().getDate() - 6,
                        new Date().getHours()+1,
                    new Date().getMinutes(),
                    new Date().getSeconds()),
                'Y-m-d H:i:s'),
            toValue: Ext.Date.format(new Date(new Date().getFullYear(),
                    new Date().getMonth(),
                    new Date().getDate(),
                        new Date().getHours()+1,
                    new Date().getMinutes(),
                    new Date().getSeconds()),
                'Y-m-d H:i:s'),
            editable:false,
            columnWidth : .38
        },{
            xtype : 'checkbox' ,
            name : 'position',
            boxLabel : '是否库位确认',
            columnWidth : .1,
            margin : '7 2 5 15',
            inputValue : 'Y'

        }, {
            xtype : 'checkbox' ,
            columnWidth : .12,
            margin : '7 0 5 7',
            name : 'noDeliverDate',
            boxLabel : '无预计送货日期',
            inputValue : 'Y',
			checked:true

        },{
            xtype : 'container',
            columnWidth : .3,
            layout : 'hbox',
            items : [{
                xtype: 'datefield',
                name: 'preDeliverDateFrom',
                labelWidth : 100,
                width : 210,
                //editable:false,
                fieldLabel: '预计送货日期',
                format : 'Y-m-d',
                value:Ext.Date.format(new Date(new Date().getFullYear(),
                        new Date().getMonth(),
                            new Date().getDate() - 27,
                        new Date().getHours()),
                    'Y-m-d')
            }, {
                xtype: 'datefield',
                name: 'preDeliverDateTo',
                fieldLabel: '至',
                labelWidth : 20,
               // editable:false,
                width : 130,
                format : 'Y-m-d',
                value:Ext.Date.format(new Date(new Date().getFullYear(),
                        new Date().getMonth(),
                            new Date().getDate()+2 ,
                        new Date().getHours()),
                    'Y-m-d')
            }]
        },{
            xtype:'combo',
            displayField:'valueName',
            valueField:'valueCode',
            queryMode:'local',
            triggerAction:'all',
            value : '',
            editable:false,
            name : 'deliveryTimeInterval',
            fieldLabel : '送货时间段',
            labelWidth: 85,
            columnWidth : .15,
            store : Ext.create('Foss.predeliver.deliverHandoverbill.deliveryTimeIntervalStore')
        },{
            xtype : 'container',
            columnWidth : .25,
            layout : 'hbox',
            items : [{
                xtype: 'timefield',
                name: 'deliveryTimeStart',
                labelWidth : 80,
                width : 160,
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
                format : 'H:i',
                fieldLabel: '至',
                labelWidth : 20,
                width : 100,
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
        }, {
            xtype: 'container',
            border : false,
            columnWidth:.01,
            html: '&nbsp;'
        },{
            text : predeliver.deliverHandoverbill.i18n('pkp.predeliver.notifyCustomer.queryButton'),
            disabled:!predeliver.deliverHandoverbill.isPermission('deliverHandoverbillIndex/preDeliverHandoverbillQueryButton'),
            hidden:!predeliver.deliverHandoverbill.isPermission('deliverHandoverbillIndex/preDeliverHandoverbillQueryButton'),
            columnWidth:.05,
            xtype :'button',
            cls : 'yellow_button',
            plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin', {
                seconds: 3
            }),
            handler : function() {
                Ext.getCmp('Foss_predeliver_deliverHandoverbill_preHandoverbillPanel_ID').getPreHandoverbillGrid().getPagingToolbar().moveFirst();
            }
        }, {
            xtype: 'container',
            border : false,
            columnWidth:.01,
            html: '&nbsp;'
        },{
            text : predeliver.deliverHandoverbill.i18n('pkp.predeliver.notifyCustomer.resetButton'),
            xtype :'button',
            columnWidth:.05,
            handler : function() {
                var myForm = this.up('form').getForm();
                myForm.reset();
                myForm.findField('stockTimeFrom').setValue(Ext.Date.format(new Date(new Date().getFullYear(),
                        new Date().getMonth(),
                            new Date().getDate() - 6,
                            new Date().getHours()+1,
                        new Date().getMinutes(),
                        new Date().getSeconds()),
                    'Y-m-d H:i:s'));
                myForm.findField('stockTimeTo').setValue(Ext.Date.format(new Date(new Date().getFullYear(),
                        new Date().getMonth(),
                        new Date().getDate(),
                            new Date().getHours()+1,
                        new Date().getMinutes(),
                        new Date().getSeconds()),
                    'Y-m-d H:i:s'));


            }
        }]
});
//已交单运单-查询条件
Ext.define('Foss.predeliver.deliverHandoverbill.HandoverbillQueryPanel', {
    extend:'Ext.form.Panel',
    id:'Foss_predeliver_deliverHandoverbill_HandoverbillQueryPanel_Id',
    // 指定容器的标题
    title: '查询条件',//查询条件
    frame:true,
    //收缩
    collapsible: true,
    //动画收缩
    animCollapse: true,
    bodyCls: 'autoHeight',
    cls: 'autoHeight',
    labelAlign:'right',
    //默认边距 间隔
    defaults: {
        margin : '4 2 3 0',  //四边距  上右下左
        labelWidth: 60
    },
    layout: 'column',
    defaultType: 'textfield',
    // 定义容器中的项
    items: [{
        name : 'waybillNo',
        xtype : 'textarea',
        fieldLabel :'运单号',
        columnWidth : .16,
        labelWidth: 50,
        height:82,
        emptyText : predeliver.deliverHandoverbill.i18n('pkp.predeliver.notifyCustomer.waybillNo.valitation'),
        regex : /^([0-9]{8,10}\n?)+$/i,
        regexText : predeliver.deliverHandoverbill.i18n('pkp.predeliver.notifyCustomer.waybillNo.valitation')

    },{
            name : 'handoverNo',
            fieldLabel :'交接单号',
            columnWidth : .15,
            labelWidth: 70

        },
        {
            name : 'vehicleAssembleNo',
            fieldLabel :'车次号',
            columnWidth : .16,
            labelWidth: 65

        },
        {
            xtype:'combo',
            displayField:'name',
            valueField:'code',
            queryMode:'local',
            triggerAction:'all',
            value : '',
            editable:false,
            name : 'productCode',
            fieldLabel :'运输性质',
            columnWidth : .13,
            store : Ext.create('Foss.pkp.ProductStore')
        }, {
            xtype:'combo',
            displayField:'valueName',
            valueField:'valueCode',
            queryMode:'local',
            triggerAction:'all',
            value : '',
            editable:false,
            name : 'receiveMethod',
            fieldLabel : '提货方式',
            columnWidth : .13,
            store :FossDataDictionary.getDataDictionaryStore('PICKUPGOODSHIGHWAYS', null,[
                {
                    'valueCode': '',
                    'valueName': '全部'
                }
            ])
        }, {
            xtype:'combo',
            displayField:'valueName',
            valueField:'valueCode',
            queryMode:'local',
            triggerAction:'all',
            value : '',
            editable:false,
            name : 'noticeResult',
            fieldLabel : '通知情况',
            columnWidth : .12,
            store :FossDataDictionary.getDataDictionaryStore('PKP_NOTIFY_CUSTOMER_STATUS',null, {
                'valueCode': '',
                'valueName': predeliver.deliverHandoverbill.i18n('pkp.predeliver.notifyCustomer.complete')
            },['SUCCESS','FAILURE','NONE_NOTICE'])
        },  {
            xtype:'combo',
            displayField:'valueName',
            valueField:'valueCode',
            queryMode:'local',
            triggerAction:'all',
            value : '',
            editable:false,
            name : 'deliverbillStatus',
            fieldLabel : '排单状态',
            columnWidth : .12,
            store : FossDataDictionary.getDataDictionaryStore('PKP_DELIVERBILL_STATUS', null, {
                'valueCode': '',
                'valueName': predeliver.deliverHandoverbill.i18n('pkp.predeliver.notifyCustomer.complete')
            })
        },{
            xtype : 'container',
            columnWidth : .3,
            layout : 'hbox',
            items : [{
                xtype: 'datefield',
                name: 'preDeliverDateFrom',
                labelWidth : 85,
                width : 189,
                editable:false,
                fieldLabel: '预计送货日期',
                format : 'Y-m-d',
                value:Ext.Date.format(new Date(new Date().getFullYear(),
                        new Date().getMonth(),
                            new Date().getDate() - 27,
                        new Date().getHours()),
                    'Y-m-d')
            }, {
                xtype: 'datefield',
                name: 'preDeliverDateTo',
                fieldLabel: '至',
                labelWidth : 20,
                editable:false,
                width : 120,
                format : 'Y-m-d',
                value:Ext.Date.format(new Date(new Date().getFullYear(),
                        new Date().getMonth(),
                            new Date().getDate()+2 ,
                        new Date().getHours()),
                    'Y-m-d')
            }]
        }, {
            xtype:'combo',
            displayField:'valueName',
            valueField:'valueCode',
            queryMode:'local',
            triggerAction:'all',
            value : '',
            editable:false,
            name : 'deliveryTimeInterval',
            labelWidth : 80,
            fieldLabel : '送货时间段',
            columnWidth : .16,
            store : Ext.create('Foss.predeliver.deliverHandoverbill.deliveryTimeIntervalStore')
        },{
            xtype : 'container',
            columnWidth : .27,
            layout : 'hbox',
            items : [{
                xtype: 'timefield',
                name: 'deliveryTimeStart',
                labelWidth : 75,
                width : 160,
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
                format : 'H:i',
                fieldLabel: '至',
                labelWidth : 20,
                width : 90,
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
        },{
            xtype : 'checkbox' ,
            name : 'noArrageBill',
            boxLabel : '未排单运单',
            columnWidth : .1,
            margin : '7 2 5 -15',
            inputValue : 'Y'

        }, {
            xtype : 'rangeDateField',
            fieldLabel : '交单时间',
            fieldId : 'Foss_predeliver_deliverHandoverbill_deliverHandoverTime_Id',
            dateType : 'datetimefield_date97',
            fromName : 'handoverbillTimeStart',
            toName : 'handoverbillTimeEnd',
            fromValue: Ext.Date.format(new Date(new Date().getFullYear(),
                    new Date().getMonth(),
                        new Date().getDate() - 3,
                        new Date().getHours()+1,
                    new Date().getMinutes(),
                    new Date().getSeconds()),
                'Y-m-d H:i:s'),	//默认查询3天内的数据,
            toValue: Ext.Date.format(new Date(new Date().getFullYear(),
                    new Date().getMonth(),
                    new Date().getDate(),
                        new Date().getHours()+1,
                    new Date().getMinutes(),
                    new Date().getSeconds()),
                'Y-m-d H:i:s'),
            editable:false,
            columnWidth : .35
        },{
            xtype: 'container',
            border : false,
            columnWidth:.35,
            html: '&nbsp;'
        },{
            text : predeliver.deliverHandoverbill.i18n('pkp.predeliver.notifyCustomer.queryButton'),
            disabled:!predeliver.deliverHandoverbill.isPermission('deliverHandoverbillIndex/deliverHandoverbillQueryButton'),
            hidden:!predeliver.deliverHandoverbill.isPermission('deliverHandoverbillIndex/deliverHandoverbillQueryButton'),
            columnWidth:.05,
            xtype :'button',
            cls : 'yellow_button',
            plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin', {
                seconds: 3
            }),
            handler : function() {
                Ext.getCmp('Foss_predeliver_deliverHandoverbill_handoverbillPanel_ID').getHandoverbillGrid().getPagingToolbar().moveFirst();

            }
        }, {
            xtype: 'container',
            border : false,
            columnWidth:.01,
            html: '&nbsp;'
        },{
            text : predeliver.deliverHandoverbill.i18n('pkp.predeliver.notifyCustomer.resetButton'),
            xtype :'button',
            columnWidth:.05,
            handler : function() {
                var myForm = this.up('form').getForm();
                myForm.reset();
                myForm.findField('handoverbillTimeStart').setValue(Ext.Date.format(new Date(new Date().getFullYear(),
                        new Date().getMonth(),
                            new Date().getDate() - 3,
                            new Date().getHours()+1,
                        new Date().getMinutes(),
                        new Date().getSeconds()),
                    'Y-m-d H:i:s'));
                myForm.findField('handoverbillTimeEnd').setValue(Ext.Date.format(new Date(new Date().getFullYear(),
                        new Date().getMonth(),
                        new Date().getDate(),
                            new Date().getHours()+1,
                        new Date().getMinutes(),
                        new Date().getSeconds()),
                    'Y-m-d H:i:s'));
            }

        }]
});
//修改通知运单信息
Ext.define('Foss.predeliver.deliverHandoverbill.preDeliverHandoverNotifyCustomerPanel', {
    extend:'Ext.form.Panel',
    // 指定容器的标题
    //title: '查询条件',//查询条件
    frame:true,
    //收缩
    collapsible: true,
    id:'Foss_predeliver_deliverHandoverbill_preDeliverHandoverNotifyCustomerPanel_id',
    //动画收缩
    animCollapse: true,
    bodyCls: 'autoHeight',
    cls: 'autoHeight',
    labelAlign:'right',
    //默认边距 间隔
    defaults: {
        margin : '4 2 3 0',  //四边距  上右下左
        labelWidth: 90
    },
    layout: 'column',
    defaultType: 'textfield',
    // 定义容器中的项
    items: [{
        name: "id",
        xtype: "hidden"
    },{
        name: "waybillNo",
        xtype: "hidden"
    }  ,{
        xtype:'combo',
        displayField:'valueName',
        valueField:'valueCode',
        queryMode:'local',
        triggerAction:'all',
        value : '',
        editable:false,
        name : 'invoiceType',
        fieldLabel : '发票类型',
        columnWidth : .3,
        store : FossDataDictionary.getDataDictionaryStore('PKP_RECEIPT_INVOICE_TYPE',null, {
            'valueCode': 'N/A',
            'valueName': '无'
        })
    },
        {
            name : 'deliverDate' ,//预计送货日期
            xtype: 'datefield',
            fieldStyle: 'color:red;font-weight:bold;',
            columnWidth : .3,
            format : 'Y-m-d',
            allowBlank:false,
            editable:false,
            minValue: new Date(),
            fieldLabel : predeliver.deliverHandoverbill.i18n('pkp.predeliver.notifyCustomer.delivery.date')
        },{
            fieldLabel : '实际收货地址',
            columnWidth: 1,
            //labelWidth:90,
            provinceWidth : 150,
            cityWidth : 150,
            allowBlank:false,
            cityLabel : '',
            cityName : 'cityCode',//名称
            provinceLabel : '',
            provinceName:'privateCode',//省名称
            areaLabel : '',
            areaName : 'districtCode',// 县名称
            areaWidth : 150,
            type : 'P-C-C',
            xtype : 'linkregincombselector'
        },
        {
            xtype  : 'label',
            //labelWidth:100,
            labelSeparator:'',
            columnWidth:.13
        },
        {
            name: 'actualAddressDetail',
            columnWidth: .35,
            labelWidth:0,
            margin: '0 0 0 0',
            fieldLabel : '',
            maxLength: 166,
            //labelWidth:100,
            labelSeparator:''
        },  //实际收货街道
        {
            name: 'actualStreetn',
            columnWidth: .35,
            labelWidth:0,
            margin: '0 0 0 0',
            maxLength: 166,
            fieldLabel : '',
            labelSeparator:''
        },  //实际收货详细地址
        {
            xtype:'combo',
            displayField:'valueName',
            valueField:'valueCode',
            queryMode:'local',
            triggerAction:'all',
            value : '',
            editable:false,
            name : 'deliveryTimeInterval',
            fieldLabel : '送货时间段',
            columnWidth : .3,
            store : Ext.create('Foss.predeliver.deliverHandoverbill.deliveryTimeIntervalStore1')
        },{
            xtype : 'container',
            columnWidth : .55,
            layout : 'hbox',
            items : [{
                xtype: 'timefield',
                name: 'deliveryTimeStart',
                labelWidth : 80,
                width : 160,
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
                format : 'H:i',
                fieldLabel: '至',
                labelWidth : 20,
                width : 100,
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
        }, {
        	name : 'deliverRequire' ,
        	xtype : 'textarea',
        	height:50, 
        	allowBlank:false,
        	fieldLabel : '送货要求', 
        	maxLength : 200,
        	columnWidth: 0.9
        }, {
            border: 1,
            xtype:'container',
            columnWidth:1,
            defaultType:'button',
            layout:'column',
            items:[{
                xtype: 'container',
                border : false,
                columnWidth:.8,
                html: '&nbsp;'
            },{
                text : '保存',
                plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin', {
                    seconds: 3
                }),
                columnWidth:.08,
                handler : function() {

                    var form = this.up('form').getForm();
                    record=form.getRecord();
                    formValues= form.getValues();
                    if(!form.isValid()){
                        return false;
                    }
                    if((!Ext.isEmpty(formValues.deliveryTimeStart)) &&(!Ext.isEmpty(formValues.deliveryTimeOver))){
                    	if(formValues.deliveryTimeStart>formValues.deliveryTimeOver){
                    		 Ext.ux.Toast.msg('提示','最早送货时间点不能大于最晚送货时间点!', 'error', 2000);
                    		return false;
                    	}
                    }
                    var array = {
                        'vo': {
                            'notificationEntity' :
                            {
                                'waybillNo' :record.waybillNo,
                                'id' :record.id,
                                'invoiceType' :formValues.invoiceType,
                                'deliverDate' :formValues.deliverDate,
                                'actualProvCode' :formValues.privateCode,
                                'actualProvN' :formValues.privateName,
                                'actualCityCode' :formValues.cityCode,
                                'actualCityN' :formValues.cityName,
                                'actualDistrictCode' :formValues.districtCode,
                                'actualDistrictN' :formValues.districtName,
                                'actualStreetn' :formValues.actualStreetn,
                                'actualAddressDetail' :formValues.actualAddressDetail,
                                'deliveryTimeInterval' :formValues.deliveryTimeInterval,
                                'deliveryTimeStart' :formValues.deliveryTimeStart,
                                'deliveryTimeOver' :formValues.deliveryTimeOver,
                                'deliverRequire' : formValues.deliverRequire
                            }
                        }
                    };
                    Ext.Ajax.request({
                        url:predeliver.realPath('updateLastNotifyByWaybillNo.action'),
                        jsonData: array,
                        success: function(response){
                            var result = Ext.decode(response.responseText);
                            Ext.ux.Toast.msg('提示', '修改成功!', 'info', 2000);
                            Ext.getCmp('Foss_predeliver_deliverHandoverbill_window_notifyCustomer_Id').close();
                            Ext.getCmp('Foss_predeliver_deliverHandoverbill_preHandoverbillPanel_ID').getPreHandoverbillGrid().getPagingToolbar().moveFirst();
                        },
                        exception: function(response) {
                            var json = Ext.decode(response.responseText);
                            Ext.ux.Toast.msg(predeliver.deliverHandoverbill.i18n('pkp.predeliver.notifyCustomer.save.failuer'), result.message, 'error', 2000);
                        },
                        scope : this
                    });
                }
            },{
                xtype: 'container',
                border : false,
                columnWidth:.01,
                html: '&nbsp;'
            },{
                text : '取消',
                xtype :'button',
                columnWidth:.08,
                handler : function() {
                    Ext.getCmp('Foss_predeliver_deliverHandoverbill_window_notifyCustomer_Id').close();
                }
            }]
        }]
});
//修改通知信息
predeliver.deliverHandoverbill.preNotifyWinForm = function() {

    if (Ext.getCmp('Foss_predeliver_deliverHandoverbill_window_notifyCustomer_Id')) {
        Ext.getCmp('Foss_predeliver_deliverHandoverbill_window_notifyCustomer_Id').show();
        return;
    }
    Ext.create('Ext.window.Window', {
        id : 'Foss_predeliver_deliverHandoverbill_window_notifyCustomer_Id',
        width : 800,
        title : '修改通知基本信息',
        modal:true,
        resizable : false,
        items : [Ext.create('Foss.predeliver.deliverHandoverbill.preDeliverHandoverNotifyCustomerPanel')]
    }).show();

}
//修改预计送货日期
Ext.define('Foss.predeliver.deliverHandoverbill.preDeliverDateUpdatePanel', {
    extend:'Ext.form.Panel',
    // 指定容器的标题
    //title: '查询条件',//查询条件
    frame:true,
    //收缩
    collapsible: true,
    //动画收缩
    animCollapse: true,
    bodyCls: 'autoHeight',
    cls: 'autoHeight',
    labelAlign:'right',
    //默认边距 间隔
    defaults: {
        margin : '4 2 3 0',  //四边距  上右下左
        labelWidth: 90
    },
    layout: 'column',
    defaultType: 'textfield',
    // 定义容器中的项
    items: [
        {
            name : 'preDeliverDate' ,//预计送货日期
            xtype: 'datefield',
            columnWidth : .9,
            value:new Date(),
            allowBlank:false,
            format : 'Y-m-d',
            editable:false,
            minValue: new Date(),
            fieldLabel : predeliver.deliverHandoverbill.i18n('pkp.predeliver.notifyCustomer.delivery.date')
        },{
            border: 1,
            xtype:'container',
            columnWidth:1,
            defaultType:'button',
            layout:'column',
            items:[{
                xtype: 'container',
                border : false,
                columnWidth:.55,
                html: '&nbsp;'
            },{
                text : '保存',
                plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin', {
                    seconds: 3
                }),
                columnWidth:.15,
                handler : function() {
                    var records = Ext.getCmp('Foss_predeliver_deliverHandoverbill_preHandoverbillPanel_ID').getPreHandoverbillGrid().getSelectionModel().getSelection(),
                        waybillNoArray = new Array(),
                        form = this.up('form').getForm();
                    if(records.length==0){
                        Ext.ux.Toast.msg(predeliver.deliverHandoverbill.i18n('pkp.predeliver.notifyCustomer.tip')/*提示*/,'请选择一条运单', 'error', 2000);
                        return;
                    }

                    for (var i = 0; i < records.length; i++) {
                        waybillNoArray.push(records[i].data.waybillNo);
                    }
                    var array = {
                        'vo': {
                            'preDeliverHandoverbillQueryDto' :
                            {
                                'waybillNoList' :waybillNoArray,
                                'deliverDate':form.findField('preDeliverDate').getValue()
                            }
                        }
                    };
                    Ext.Ajax.request({
                        url:predeliver.realPath('updatePreDeliverDateByWaybillNo.action'),
                        jsonData: array,
                        success: function(response){
                            var result = Ext.decode(response.responseText);
                            Ext.ux.Toast.msg('提示', '修改成功!', 'info', 2000);
                            Ext.getCmp('Foss_predeliver_deliverHandoverbill_preHandoverbillPanel_ID').getPreHandoverbillGrid().getPagingToolbar().moveFirst();
                            Ext.getCmp('Foss_predeliver_deliverHandoverbill_window_preDeliverDateUpdate_Id').close();
                        },
                        exception: function(response) {
                            var json = Ext.decode(response.responseText);
                            Ext.ux.Toast.msg(predeliver.deliverHandoverbill.i18n('pkp.predeliver.notifyCustomer.save.failuer'), result.message, 'error', 2000);

                        },
                        scope : this
                    });
                }
            },{
                xtype: 'container',
                border : false,
                columnWidth:.05,
                html: '&nbsp;'
            },{
                text : '取消',
                xtype :'button',
                columnWidth:.15,
                handler : function() {
                    Ext.getCmp('Foss_predeliver_deliverHandoverbill_window_preDeliverDateUpdate_Id').close();
                }
            }]
        }]
});
//修改预计送货日期
predeliver.deliverHandoverbill.preDeliverDateUpdateWin = function() {

    if (Ext.getCmp('Foss_predeliver_deliverHandoverbill_window_preDeliverDateUpdate_Id')) {
        Ext.getCmp('Foss_predeliver_deliverHandoverbill_window_preDeliverDateUpdate_Id').show();
        return;
    }
    Ext.create('Ext.window.Window', {
        id : 'Foss_predeliver_deliverHandoverbill_window_preDeliverDateUpdate_Id',
        width:400,
        title : '修改预计送货日期',
        modal:true,
        resizable : false,
        items : [Ext.create('Foss.predeliver.deliverHandoverbill.preDeliverDateUpdatePanel')]
    }).show();

}
//待交单运单-显示查询结果
Ext.define('Foss.predeliver.deliverHandoverbill.PreHandoverbillGrid',{
    extend:'Ext.grid.Panel',
    title:'查询结果',//待处理
    //收缩
    collapsible: true,
    //是否有框
    frame:true,
    //动画收缩
    animCollapse: true,
    //高
    height: 590,
    emptyText:'查询结果为空',//查询结果为空
    store: null,
    bodyCls: 'autoHeight',
    cls: 'autoHeight',
    selModel : Ext.create('Ext.selection.CheckboxModel',{
        checkOnly: false //限制只有点击checkBox后才能选中行
    }),
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
    columns: [
        { header: '异常'+ '<br>' +'情况',  align: 'center',width : 100,
            renderer : function(value, cellmeta, record, rowIndex, columnIndex,
                                store) {
                var result = '';
                //判断是否含有未受理更改单  用红旗表示
                if (!Ext.isEmpty(record.data.waybillRfcStatus)
                    && (record.data.waybillRfcStatus == 'PRE_AUDIT' || record.data.waybillRfcStatus == 'PRE_ACCECPT')) {
                    result = '<div class="foss_icons_pkp_flagred"style="width:18px;height:18px;"></div>';
                }
                //有异常未处理黄旗表示
                if (record.data.hasException == 'Y') {
                    result =  result+'<div class="foss_icons_pkp_flagyellow" style="width:18px;height:18px;"></div>';
                }
				if(!Ext.isEmpty(record.data.selectRoleType) && (record.data.selectRoleType=='ONTHEWAY' ||record.data.selectRoleType=='IN_STOCK')){
					
				}else{
					//开单与到达件数不同 用绿旗表示
					if (record.data.goodsQtyTotal != record.data.arriveGoodsQty) {
						result = result+'<div class="foss_icons_pkp_flaggreen" style="width:18px;height:18px;"></div>';
					}
				}
                
				if(!Ext.isEmpty(record.data.selectRoleType) && (record.data.selectRoleType=='ONTHEWAY' ||record.data.selectRoleType=='ARRIVED')){
					
				}else{
					//进行开单与库存件数不同 颜色标识
					if (record.data.goodsQtyTotal != record.data.stockGoodsQty) {
						result = result+'<div class="foss_icons_pkp_flagblue" style="width:18px;height:18px;"></div>';
					}
				}
                //有异常未补录的打勾表示
                if (!Ext.isEmpty(record.data.pendingType) && (record.data.pendingType =='PC_PENDING'||
                    record.data.pendingType =='PDA_PENDING')) {
                    result =  result+'<div class="foss_icons_pkp_yes" style="width:18px;height:18px;"></div>';
                }
                //有开单付款方式为网上支付且未支付运单   Y
                if (!Ext.isEmpty(record.data.isOLNotPaid)&&record.data.isOLNotPaid =='Y') {
                    result =  result+'<div class="foss_icons_pkp_noticeError" style="width:18px;height:18px;"></div>';
                }

                return result;
            }
        },
        { hidden : true,dataIndex: 'pendingType'},
        { hidden : true,dataIndex: 'toPayAmount'},
        { hidden : true,dataIndex: 'paidMethod'},
        { hidden : true,dataIndex: 'deliverBillQty'},
        { hidden : true,dataIndex: 'hasException'},
        { hidden : true,dataIndex: 'waybillRfcStatus'},
        { hidden : true,dataIndex: 'receiveCustomerProvCode'},
        { hidden : true,dataIndex: 'receiveCustomerCityCode'},
        { hidden : true,dataIndex: 'receiveCustomerDistCode'},
        { hidden : true,dataIndex: 'receiveCustomerAddress'},
        { hidden : true,dataIndex: 'receiveCustomerAddressNote'},
        { hidden : true,dataIndex: 'orgRoleType'},
        { hidden : true,dataIndex: 'nowDate'},
        { hidden : true,dataIndex: 'artificialMark'},
		{ hidden : true,dataIndex: 'selectRoleType'},
		{ hidden : true,dataIndex: 'goodsPackage'},
		{ hidden : true,dataIndex: 'goodsSize'},
		{ hidden : true,dataIndex: 'isOLNotPaid'},
        { header: '运单号',  align: 'center', dataIndex: 'waybillNo',width : 85},
        { header: '规定兑现时间',  align: 'center', dataIndex: 'cashTime',width : 85,xtype : 'ellipsiscolumn'},
        { header: '运输性质',  align: 'center', dataIndex: 'productCode',  width : 80 ,
             renderer : function(value) {
                return Foss.pkp.ProductData.rendererSubmitToDisplay(value);
             }
        },
        { header: '提货方式',  align: 'center', dataIndex: 'receiveMethod',width : 90,
            renderer : function(value) {
            	var v = FossDataDictionary.rendererSubmitToDisplay(value, 'PICKUPGOODSHIGHWAYS');
				if(Ext.isEmpty(v) || value == v){
					v = FossDataDictionary.rendererSubmitToDisplay(value, 'PICKUPGOODSSPECIALDELIVERYTYPE');
				}
				return v;
            }
        },
        { header: '待交'+ '<br>' +'件数',  align: 'center', dataIndex: 'billQty',width : 50,
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
                        editor.on('startedit',
                            function() {
                                var selectRow = waybillToArrangeGrid
                                    .getSelectionModel()
                                    .getSelection();
                                if (selectRow == null|| selectRow.length <= 0)
                                    return;
                                selectRecord = selectRow[0];
                            }, this);
                        editor.on('beforecomplete',
                            function() {
                                if (selectRecord == null) {
                                    return;
                                }
                                if (field.lastValue > selectRecord
                                    .get('goodsQtyTotal')-selectRecord
                                    .get('deliverBillQty')) {
                                    flag = false;
                                    Ext.ux.Toast.msg(predeliver.deliverHandoverbill.i18n('pkp.predeliver.editDeliverbillIndex.tip'),'待交单件数不能大于可交单件数!','error',4000);
                                    editor.cancelEdit();
                                    return;
                                } else {
                                    flag = true;
                                }

                                if (field.lastValue <= 0) {
                                    flag = false;
                                    Ext.ux.Toast.msg(predeliver.deliverHandoverbill.i18n('pkp.predeliver.editDeliverbillIndex.tip'),'交单件数必须大小0','error',4000);
                                    editor.cancelEdit();
                                    return;
                                } else {
                                    flag = true;
                                }
                                selectRecord.set('billQty',field.lastValue);
                            }, this);
                    }
                }
            }},
        { header: '开单'+ '<br>' +'件数',  align: 'center', dataIndex: 'goodsQtyTotal',  width : 50  },
        { header: '到达'+ '<br>' +'件数',  align: 'center', dataIndex: 'arriveGoodsQty',width : 50},
        { header: '库存'+ '<br>' +'件数',  align: 'center', dataIndex: 'stockGoodsQty',  width : 50  },
        { header: '重量',  align: 'center', dataIndex: 'goodsWeight',  width : 70  },
        { header: '体积',  align: 'center', dataIndex: 'goodsVolume',width : 50},
        { header: '货物状态',  align: 'center', dataIndex: 'stockStatus', width : 80  },
        { header: '通知情况',  align: 'center', dataIndex: 'noticeResult',  width : 80 ,
            renderer : function(value) {
                return FossDataDictionary.rendererSubmitToDisplay(value, 'PKP_NOTIFY_CUSTOMER_STATUS');
            }
        },
        { header: '预计'+ '<br>' +'送货日期',  align: 'center', dataIndex: 'preDeliverDate',width : 80},
        { header: '送货'+ '<br>' +'时间段',  align: 'center', dataIndex: 'deliveryTimeInterval',  width : 80  },
        { header: '送货'+ '<br>' +'时间点(起)',  align: 'center', dataIndex: 'deliveryTimeStart',width : 80},
        { header: '送货'+ '<br>' +'时间点(止)',  align: 'center', dataIndex: 'deliveryTimeOver',width : 80},
        { header: '发票类型',  align: 'center', dataIndex: 'invoiceType', width : 80 ,
            renderer : function(value) {
                return FossDataDictionary.rendererSubmitToDisplay(value, 'PKP_RECEIPT_INVOICE_TYPE');
            }
        },
        { header: '预计'+ '<br>' +'到达时间',  align: 'center', dataIndex: 'planArriveTime',width : 130},
        { header: '入库时间',  align: 'center', dataIndex: 'inStockTime',  width : 130 },
        { header: '调度退回'+ '<br>' +'原因',  align: 'center', dataIndex: 'deliverReturnReason',width : 100,xtype : 'ellipsiscolumn'},
        { header: '调度退回'+ '<br>' +'操作人',  align: 'center', dataIndex: 'deliverReturnOperate',  width : 100 ,xtype : 'ellipsiscolumn' },
        { header: '退回时间',  align: 'center', dataIndex: 'deliverReturnTime',width : 130}
    ],
    //enableColumnHide: false,      //取消列头菜单
    //sortableColumns: false,          //取消列头排序功能
    viewConfig: {
        stripeRows : false,
        enableTextSelection : true,
        getRowClass : function(record, rowIndex, rp, ds) {
            if (!Ext.isEmpty(record.data.deliverReturnOperate)||record.data.deliverReturnTime!=null) {
                return 'predeliver-beforeNoticeIndex-row-purole';
            }
        }

    },
    printWindow: null,
    getPrintWindow: function(){
        var me = this;
        if(this.printWindow==null){
            me.printWindow = Ext.create('Foss.printArriveSheet.printWindow',me);
        }
        return me.printWindow;
    },
    notifyCustomerWindow : null,
    getNotifyCustomerWindow : function(){
        if(this.notifyCustomerWindow == null){
            if(Ext.getCmp('Foss_predeliver_deliverHandoverbill_notifyCustomerWin_Id')!=null){
                Ext.destroy(Ext.getCmp('Foss_predeliver_deliverHandoverbill_notifyCustomerWin_Id'));
            }
            this.notifyCustomerWindow = Ext.create('Foss.predeliver.deliverHandoverbill.notifyCustomerWin.SiginOutStoraWindow',{
            });
        }
        return this.notifyCustomerWindow;
    },
    pagingToolbar : null,
    getPagingToolbar : function() {
        if (this.pagingToolbar == null) {
            this.pagingToolbar = Ext.create('Deppon.StandardPaging', {
                store : this.store,
                displayInfo: true,
                totalCountFlag:true,
                plugins: {
                    ptype: 'pagesizeplugin',
                    //超出输入最大限制是否提示，true则提示，默认不提示
                    alertOperation: true,
                    //自定义分页comobo数据
                    sizeList: [['50', 50], ['100', 100], ['200', 200], ['300', 300]],
                    //入最大限制，默认为200
                    maximumSize: 300
                }
            });
        }
        return this.pagingToolbar;
    },
    constructor: function(config){
        var me = this,
            cfg = Ext.apply({}, config);
        me.store = Ext.create('Foss.predeliver.deliverHandoverbill.PreDeliverHandoverbillStore');
        me.bbar = me.getPagingToolbar();
        me.plugins=[
            me.getCellEditor()
        ];
        me.dockedItems =[{
            xtype : 'toolbar',
            dock : 'top',
            layout : 'column',
            items : [  {
                xtype : 'displayfield',
                fieldLabel:'总票数',
                allowBlank:true,
                labelWidth : 65,
                id:'Foss_predeliver_deliverHandoverbill_PreHandoverbillGrid_totalCount_Id',
                margin : '0 30 0 0'
            },  {
                xtype : 'image',
                imgCls : 'foss_icons_pkp_flagred'
            }, {
                xtype : 'label',
                text : '更改单未受理'
            }, {
                xtype: 'image',
                margin : '0 0 0 5',
                imgCls: 'foss_icons_pkp_flagyellow'
            },{
                xtype: 'label',
                text: '异常未处理'
            } ,{
                xtype: 'image',
                margin : '0 0 0 5',
                imgCls: 'foss_icons_pkp_yes'
            },{
                xtype: 'label',
                text: '运单未补录'
            } ,{
                xtype: 'image',
                margin : '0 0 0 5',
                imgCls: 'foss_icons_pkp_noticeError'
            },{
                xtype: 'label',
                text: '网上支付-未付款'
            }, {
                xtype : 'button',
                margin : '0 5 0 2',
                /*plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin', {
                 seconds: 5
                 }),*/
                id:'Foss_predeliver_deliverHandoverbill_PreHandoverbillGrid_executePreDeliverbill_id',
                text : '派送交单',
                disabled:!predeliver.deliverHandoverbill.isPermission('deliverHandoverbillIndex/executePreDeliverbillButton'),
                hidden:!predeliver.deliverHandoverbill.isPermission('deliverHandoverbillIndex/executePreDeliverbillButton'),
                handler : function() {
                    Ext.getCmp('Foss_predeliver_deliverHandoverbill_PreHandoverbillGrid_executePreDeliverbill_id').setDisabled(true);
                    var records = this.up('grid').getSelectionModel().getSelection();
                    var mygrid = this.up('gridpanel');
                    if(records.length==0){
                        Ext.ux.Toast.msg(predeliver.deliverHandoverbill.i18n('pkp.predeliver.notifyCustomer.tip')/*提示*/, predeliver.deliverHandoverbill.i18n('pkp.predeliver.notifyCustomer.please.select.waybill'), 'error', 1000);//'请选择运单。'
                        Ext.getCmp('Foss_predeliver_deliverHandoverbill_PreHandoverbillGrid_executePreDeliverbill_id').setDisabled(false);
                        return;
                    }
                    if(records[0].data.orgRoleType<1){
                        Ext.ux.Toast.msg(predeliver.deliverHandoverbill.i18n('pkp.predeliver.notifyCustomer.tip')/*提示*/,'该部门没有权限进行当前操作!', 'error', 2000);
                        return;
                    }
                    var preDeliverHandoverbillDtos =new Array(),//存放满足条件的运单集合
                        preDeliverHandoverbillDto='',
                        notPayWaybillNos=new Array(),
                        noStockWaybillNos='N';

                    for (var i = 0; i < records.length; i++) {
                        //人工批量交单提交时，若识别选择的运单有运单未补录 直接进行过滤
                        if(!Ext.isEmpty(records[i].data.pendingType) && (records[i].data.pendingType =='PC_PENDING'||
                            records[i].data.pendingType =='PDA_PENDING')) {
                            continue;
                        }
                        //若人工交单运单有更改单未受理 直接进行过滤
                        if(!Ext.isEmpty(records[i].data.waybillRfcStatus) && (records[i].data.waybillRfcStatus == 'PRE_AUDIT' || records[i].data.waybillRfcStatus == 'PRE_ACCECPT')){
                            continue;
                        }
                        //有异常未处理 直接进行过滤
                        if(!Ext.isEmpty(records[i].data.hasException) && records[i].data.hasException == 'Y'){
                            continue;
                        }
                        //有网上支付未支付完成 直接进行过滤
                        if(!Ext.isEmpty(records[i].data.isOLNotPaid) && records[i].data.isOLNotPaid == 'Y'){
                            continue;
                        }
                        //人工可交未入库的运单（Y：是，N否）
                        if(noStockWaybillNos=='N' &&(!Ext.isEmpty(records[i].data.artificialMark)) && records[i].data.artificialMark == 'N'){
                            if(Ext.isEmpty(records[i].data.stockGoodsQty) || records[i].data.stockGoodsQty==0){
                                noStockWaybillNos='Y';
                                continue;
                            }

                        }
                        if(!Ext.isEmpty(records[i].data.paidMethod) && records[i].data.paidMethod=='OL'){
                            notPayWaybillNos.push(records[i].data.waybillNo);
                        }
                        if(Ext.isEmpty(records[i].data.preDeliverDate)){
                            Ext.ux.Toast.msg(predeliver.deliverHandoverbill.i18n('pkp.predeliver.notifyCustomer.tip'), '人工交单送货日期不能为空且只能交送货日期为当前日期及之后的运单!', 'error', 3000);
                            Ext.getCmp('Foss_predeliver_deliverHandoverbill_PreHandoverbillGrid_executePreDeliverbill_id').setDisabled(false);
                            return;
                        }
                        //var result = Ext.Date.parse(records[i].data.preDeliverDate,'Y-m-d')-Ext.Date.parse(records[i].data.nowDate,'Y-m-d') ;
                        if(records[i].data.preDeliverDate<records[i].data.nowDate){
                            Ext.ux.Toast.msg(predeliver.deliverHandoverbill.i18n('pkp.predeliver.notifyCustomer.tip'), '人工交单只能交送货日期为当前日期及之后的运单!', 'error', 3000);
                            Ext.getCmp('Foss_predeliver_deliverHandoverbill_PreHandoverbillGrid_executePreDeliverbill_id').setDisabled(false);
                            return;
                        }
                        preDeliverHandoverbillDto = {
                            'toPayAmount':records[i].data.toPayAmount,
                            'paidMethod':records[i].data.paidMethod,
                            'receiveCustomerAddressNote':records[i].data.receiveCustomerAddressNote,
                            'receiveCustomerProvCode':records[i].data.receiveCustomerProvCode,
                            'receiveCustomerCityCode':records[i].data.receiveCustomerCityCode,
                            'receiveCustomerDistCode':records[i].data.receiveCustomerDistCode,
                            'receiveCustomerAddress':records[i].data.receiveCustomerAddress,
                            'waybillNo':records[i].data.waybillNo,
                            'productCode':records[i].data.productCode,
                            'receiveMethod':records[i].data.receiveMethod,
                            'billQty':records[i].data.billQty,
                            'goodsQtyTotal':records[i].data.goodsQtyTotal,
                            'goodsWeight':records[i].data.goodsWeight,
                            'goodsVolume':records[i].data.goodsVolume,
                            'noticeResult':records[i].data.noticeResult,
                            'preDeliverDate':records[i].data.preDeliverDate,
                            'deliveryTimeInterval':records[i].data.deliveryTimeInterval,
                            'deliveryTimeStart':records[i].data.deliveryTimeStart,
                            'deliveryTimeOver':records[i].data.deliveryTimeOver,
                            'invoiceType':records[i].data.invoiceType,
                            'deliverBillQty':records[i].data.deliverBillQty,
                            'goodsPackage':records[i].data.goodsPackage,
                            'goodsSize':records[i].data.goodsSize
                        };
                        preDeliverHandoverbillDtos.push(preDeliverHandoverbillDto);
                    }
                    
                    if(noStockWaybillNos=='Y'){
                        var confirmMsg = Ext.Msg.confirm(predeliver.deliverHandoverbill.i18n('pkp.predeliver.notifyCustomer.tip'),
                            '该部门设置人工交单未入库运单不能交单，是否直接剔除未入库运单？',function(btn) {
                                if (btn == 'yes') {
									if(preDeliverHandoverbillDtos==null || preDeliverHandoverbillDtos.length<=0){
										Ext.ux.Toast.msg(predeliver.deliverHandoverbill.i18n('pkp.predeliver.notifyCustomer.tip'), '过滤更改单未受理、异常未处理、运单未补录、网上支付-未付款后 没有满足条件的数据,请重新选择!', 'error', 3000);
										Ext.getCmp('Foss_predeliver_deliverHandoverbill_PreHandoverbillGrid_executePreDeliverbill_id').setDisabled(false);
										return ;
									}
                                	var array = {'vo':
                                    {
                                        'preDeliverHandoverbillDtos':preDeliverHandoverbillDtos
                                    }
                                    };
                                    Ext.Ajax.request({
                                        url:predeliver.realPath('executePreDeliverbill.action'),
                                        jsonData: array,
                                        timeout : 600000,
                                        success: function(response){
                                            Ext.ux.Toast.msg(predeliver.deliverHandoverbill.i18n('pkp.predeliver.notifyCustomer.tip'), '派送交单成功!', 'ok', 2000);
                                            Ext.getCmp('Foss_predeliver_deliverHandoverbill_PreHandoverbillGrid_executePreDeliverbill_id').setDisabled(false);
                                            Ext.getCmp('Foss_predeliver_deliverHandoverbill_preHandoverbillPanel_ID').getPreHandoverbillGrid().getPagingToolbar().moveFirst();

                                        },
                                        exception: function(response) {
                                            var json = Ext.decode(response.responseText);
                                            Ext.getCmp('Foss_predeliver_deliverHandoverbill_PreHandoverbillGrid_executePreDeliverbill_id').setDisabled(false);
                                            Ext.ux.Toast.msg(predeliver.deliverHandoverbill.i18n('pkp.predeliver.notifyCustomer.tip'), json.message, 'error', 2000);
                                        },
                                        scope : this
                                    });
                                }else{
									Ext.getCmp('Foss_predeliver_deliverHandoverbill_PreHandoverbillGrid_executePreDeliverbill_id').setDisabled(false);
								}
                            });
                    }else{
						if(preDeliverHandoverbillDtos==null || preDeliverHandoverbillDtos.length<=0){
							Ext.ux.Toast.msg(predeliver.deliverHandoverbill.i18n('pkp.predeliver.notifyCustomer.tip'), '过滤更改单未受理、异常未处理、运单未补录、网上支付-未付款后 没有满足条件的数据,请重新选择!', 'error', 3000);
							Ext.getCmp('Foss_predeliver_deliverHandoverbill_PreHandoverbillGrid_executePreDeliverbill_id').setDisabled(false);
							return ;
						}
                        var array = {'vo':
                        {
                            'preDeliverHandoverbillDtos':preDeliverHandoverbillDtos,
                            'preDeliverHandoverbillQueryDto':{
                                'waybillNos':notPayWaybillNos
                            }
                        }
                        };
                        Ext.Ajax.request({
                            url:predeliver.realPath('executePreDeliverbill.action'),
                            jsonData: array,
                            timeout : 600000,
                            success: function(response){
                                Ext.ux.Toast.msg(predeliver.deliverHandoverbill.i18n('pkp.predeliver.notifyCustomer.tip'), '派送交单成功!', 'ok', 2000);
                                Ext.getCmp('Foss_predeliver_deliverHandoverbill_PreHandoverbillGrid_executePreDeliverbill_id').setDisabled(false);
                                Ext.getCmp('Foss_predeliver_deliverHandoverbill_preHandoverbillPanel_ID').getPreHandoverbillGrid().getPagingToolbar().moveFirst();

                            },
                            exception: function(response) {
                                var json = Ext.decode(response.responseText);
                                Ext.getCmp('Foss_predeliver_deliverHandoverbill_PreHandoverbillGrid_executePreDeliverbill_id').setDisabled(false);
                                Ext.ux.Toast.msg(predeliver.deliverHandoverbill.i18n('pkp.predeliver.notifyCustomer.tip'), json.message, 'error', 2000);
                            },
                            scope : this
                        });
                    }
                }
            }, {
                xtype : 'button',
                plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin', {
                    seconds: 3
                }),
                text : '修改通知信息',
                disabled:!predeliver.deliverHandoverbill.isPermission('deliverHandoverbillIndex/notifyCustomerUpdateButton'),
                hidden:!predeliver.deliverHandoverbill.isPermission('deliverHandoverbillIndex/notifyCustomerUpdateButton'),
                margin : '0 5 0 2',
                handler : function() {
                    var records = this.up('grid').getSelectionModel().getSelection();
                    if(records.length!=1){
                        Ext.ux.Toast.msg(predeliver.deliverHandoverbill.i18n('pkp.predeliver.notifyCustomer.tip')/*提示*/,'请选择一条运单', 'error', 2000);
                        return;
                    }
                    if(records[0].data.orgRoleType<1){
                        Ext.ux.Toast.msg(predeliver.deliverHandoverbill.i18n('pkp.predeliver.notifyCustomer.tip')/*提示*/,'该部门没有权限进行当前操作!', 'error', 2000);
                        return;
                    }
                    var array = {'vo':
                    {'preDeliverHandoverbillDto':
                    {'waybillNo':records[0].data.waybillNo,
                        'receiveCustomerProvCode':records[0].data.receiveCustomerProvCode,
                        'receiveCustomerCityCode':records[0].data.receiveCustomerCityCode,
                        'receiveCustomerDistCode':records[0].data.receiveCustomerDistCode,
                        'receiveCustomerAddress':records[0].data.receiveCustomerAddress,
                        'receiveCustomerAddressNote':records[0].data.receiveCustomerAddressNote
                    }
                    }
                    };
                    Ext.Ajax.request({
                        url:predeliver.realPath('queryLastNotifyByWaybillNo.action'),
                        jsonData: array,
                        success: function(response){
                            var result = Ext.decode(response.responseText),
                                notify =result.vo.notificationEntity;
                            if(notify!= null){
                                predeliver.deliverHandoverbill.preNotifyWinForm();
                                var notifyCustomerForm=Ext.getCmp('Foss_predeliver_deliverHandoverbill_preDeliverHandoverNotifyCustomerPanel_id');
                                notifyCustomerForm.getForm().loadRecord(notify);
                                notifyCustomerForm.query('linkregincombselector')[0].setReginValue(notify.actualProvN,notify.actualProvCode,1);
                                notifyCustomerForm.query('linkregincombselector')[0].setReginValue(notify.actualCityN,notify.actualCityCode,2);
                                notifyCustomerForm.query('linkregincombselector')[0].setReginValue(notify.actualDistrictN,notify.actualDistrictCode,3);
                                notifyCustomerForm.getForm().findField('deliverDate').setValue(Ext.Date.parse(notify.deliverDate, "Y-m-d H:i:s", true));
                                notifyCustomerForm.getForm().findField('deliveryTimeStart').setValue(Ext.Date.parse(notify.deliveryTimeStart, "H:i", true));
                                notifyCustomerForm.getForm().findField('deliveryTimeOver').setValue(Ext.Date.parse(notify.deliveryTimeOver, "H:i", true));
                                notifyCustomerForm.getForm().findField('deliveryTimeInterval').setValue(notify.deliveryTimeInterval);
                                notifyCustomerForm.getForm().findField('invoiceType').setValue(notify.invoiceType);
                                notifyCustomerForm.getForm().findField('actualStreetn').setValue(notify.actualStreetn);
                                notifyCustomerForm.getForm().findField('actualAddressDetail').setValue(notify.actualAddressDetail);
								notifyCustomerForm.getForm().findField('deliverRequire').setValue(notify.deliverRequire);
                            }else{
                                Ext.ux.Toast.msg(predeliver.deliverHandoverbill.i18n('pkp.predeliver.notifyCustomer.save.failuer'), "该运单没有通知信息,不能修改!", 'error', 2000);
                            }
                        },
                        exception: function(response) {
                            var json = Ext.decode(response.responseText);
                            Ext.ux.Toast.msg(predeliver.deliverHandoverbill.i18n('pkp.predeliver.notifyCustomer.save.failuer'), result.message, 'error', 2000);
                        },
                        scope : this
                    });

                }
            }, {
                xtype : 'button',
                plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin', {
                    seconds: 3
                }),
                text : '修改预计送货日期',
                disabled:!predeliver.deliverHandoverbill.isPermission('deliverHandoverbillIndex/preDeliverDateUpdateButton'),
                hidden:!predeliver.deliverHandoverbill.isPermission('deliverHandoverbillIndex/preDeliverDateUpdateButton'),
                handler : function() {
                    var records = this.up('grid').getSelectionModel().getSelection();
                    if(records.length==0){
                        Ext.ux.Toast.msg(predeliver.deliverHandoverbill.i18n('pkp.predeliver.notifyCustomer.tip')/*提示*/,'请选择运单', 'error', 1000);//'请选择运单。'
                        return;
                    }
                    if(records[0].data.orgRoleType<1){
                        Ext.ux.Toast.msg(predeliver.deliverHandoverbill.i18n('pkp.predeliver.notifyCustomer.tip')/*提示*/,'该部门没有权限进行当前操作!', 'error', 2000);
                        return;
                    }
                    predeliver.deliverHandoverbill.preDeliverDateUpdateWin();

                }
            }, {
                xtype : 'button',

                plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin', {
                    seconds: 3
                }),
                text : '打印到达联',
                margin : '0 0 0 5',
                disabled:!predeliver.deliverHandoverbill.isPermission('deliverHandoverbillIndex/preDeliverHandoverbillPrintArriveSheetButton'),
                hidden:!predeliver.deliverHandoverbill.isPermission('deliverHandoverbillIndex/preDeliverHandoverbillPrintArriveSheetButton'),
                handler : function() {
                    var records = this.up('grid').getSelectionModel().getSelection();
                    //var waybillNos = [];
                    var mygrid = this.up('gridpanel');
                    if(records.length==0){
                        Ext.ux.Toast.msg(predeliver.deliverHandoverbill.i18n('pkp.predeliver.notifyCustomer.tip')/*提示*/, predeliver.deliverHandoverbill.i18n('pkp.predeliver.notifyCustomer.please.select.waybill'), 'error', 1000);//'请选择运单。'
                        return;
                    }
                    if(records.length>50){
                        Ext.ux.Toast.msg('提示信息', "批量打印到达联时，勾选的条数不能大于50条！", 'error', 4000);
                        return;
                    }
                    if(records[0].data.orgRoleType<1){
                        Ext.ux.Toast.msg(predeliver.deliverHandoverbill.i18n('pkp.predeliver.notifyCustomer.tip')/*提示*/,'该部门没有权限进行当前操作!', 'error', 2000);
                        return;
                    }
                    for (var i = 0; i < records.length; i++) {
                        if(!Ext.isEmpty(records[i].data.pendingType) && (records[i].data.pendingType =='PC_PENDING'||
                            records[i].data.pendingType =='PDA_PENDING')) {
                            Ext.ux.Toast.msg(predeliver.deliverHandoverbill.i18n('pkp.predeliver.notifyCustomer.error'), '运单号'+records[i].data.waybillNo+'未补录,不能打印到达联!', 'error', 3000);
                            return;
                        }
                    }
                    mygrid.getPrintWindow().show();
                    mygrid.getPrintWindow().setSource('PKP_NOTIFY');
                }
            }, {
                xtype : 'button',
                plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin', {
                    seconds: 3
                }),
                text : '导出',
                disabled:!predeliver.deliverHandoverbill.isPermission('deliverHandoverbillIndex/preDeliverHandoverbillExportButton'),
                hidden:!predeliver.deliverHandoverbill.isPermission('deliverHandoverbillIndex/preDeliverHandoverbillExportButton'),
                margin : '0 5 0 5',
                handler : function() {
                    var queryPreHandoverbillForm = Ext
                            .getCmp('Foss_predeliver_deliverHandoverbill_PreHandoverbillQueryPanel_Id').getForm(),
                        preGridStore = this.up('grid').getStore(),
                        records = this.up('grid').getSelectionModel().getSelection();
                    if(preGridStore.data.length==0){
                        Ext.ux.Toast.msg(predeliver.deliverHandoverbill.i18n('pkp.predeliver.notifyCustomer.tip'), '当前查询结果无记录,不能导出!', 'error', 1000);
                        return;
                    }

                    if (queryPreHandoverbillForm != null) {
                        var qForm = queryPreHandoverbillForm.getValues();
                        if(!predeliver.deliverHandoverbill.checkPreDeliverbillQuery(queryPreHandoverbillForm)){
                            return false;
                        }
                        if(!Ext.fly('downloadAttachFileForm')){
                            var frm = document.createElement('form');
                            frm.id = 'downloadAttachFileForm';
                            frm.style.display = 'none';
                            document.body.appendChild(frm);
                        }

                        Ext.Ajax.request({
                            url:predeliver.realPath('exportPreDeliverHandover.action'),
                            form: Ext.fly('downloadAttachFileForm'),
                            method : 'POST',
                            params : {
                                'deliverHandoverbillVo.preDeliverHandoverbillQueryDto.waybillNo':qForm.waybillNo,
                                'deliverHandoverbillVo.preDeliverHandoverbillQueryDto.handoverNo':qForm.handoverNo,
                                'deliverHandoverbillVo.preDeliverHandoverbillQueryDto.vehicleAssembleNo':qForm.vehicleAssembleNo,
                                'deliverHandoverbillVo.preDeliverHandoverbillQueryDto.productCode':qForm.productCode,
                                'deliverHandoverbillVo.preDeliverHandoverbillQueryDto.receiveMethod':qForm.receiveMethod,
                                'deliverHandoverbillVo.preDeliverHandoverbillQueryDto.position':qForm.position,
                                'deliverHandoverbillVo.preDeliverHandoverbillQueryDto.stockStatus':qForm.stockStatus,
                                'deliverHandoverbillVo.preDeliverHandoverbillQueryDto.stockTimeFrom':qForm.stockTimeFrom,
                                'deliverHandoverbillVo.preDeliverHandoverbillQueryDto.stockTimeTo':qForm.stockTimeTo,
                                'deliverHandoverbillVo.preDeliverHandoverbillQueryDto.noticeResult':qForm.noticeResult,
                                'deliverHandoverbillVo.preDeliverHandoverbillQueryDto.preDeliverDateFrom':qForm.preDeliverDateFrom,
                                'deliverHandoverbillVo.preDeliverHandoverbillQueryDto.preDeliverDateTo':qForm.preDeliverDateTo,
                                'deliverHandoverbillVo.preDeliverHandoverbillQueryDto.deliveryTimeInterval':qForm.deliveryTimeInterval,
                                'deliverHandoverbillVo.preDeliverHandoverbillQueryDto.deliveryTimeStart':qForm.deliveryTimeStart,
                                'deliverHandoverbillVo.preDeliverHandoverbillQueryDto.deliveryTimeOver':qForm.deliveryTimeOver,
                                'deliverHandoverbillVo.preDeliverHandoverbillQueryDto.noDeliverDate':qForm.noDeliverDate,
                                'start':preGridStore.proxy.reader.jsonData.start,
                                'limit':preGridStore.proxy.reader.jsonData.limit
                            },
                            isUpload: true
                        });
                    }else{
                        //或者提示不能导出
                        Ext.ux.Toast.msg(predeliver.deliverHandoverbill.i18n('pkp.predeliver.printDeliverbillArrivesheetIndex.tip'),predeliver.printDeliverbillArrivesheet.i18n('pkp.predeliver.exceptionProcess.exceptionInfo.notExport'), 'error', 3000);

                    }
                }
            }]
        },{
            xtype : 'toolbar',
            dock : 'bottom',
            layout : 'column',
            items : [ {
                xtype: 'image',
                margin : '0 0 0 10',
                imgCls: 'foss_icons_pkp_flaggreen'
            },  {
                xtype : 'label',
                text : '开单与到达件数不同'
            }, {
                xtype: 'image',
                margin : '0 0 0 10',
                imgCls: 'foss_icons_pkp_flagblue'
            },{
                xtype : 'label',
                text : '开单与库存件数不同'
            }, {
                xtype: 'label',
                margin : '0 0 0 5',
                text:'调度退回:'
            }, {
                xtype: 'image',
               // width:40,
                imgCls: 'foss_icons_pkp_purplebg'
            }]
        }];
        me.callParent([cfg]);
    }
});
//已交单运单-显示查询结果
Ext.define('Foss.predeliver.deliverHandoverbill.HandoverbillGrid',{
    extend:'Ext.grid.Panel',
    title:'查询结果',//待处理
    //收缩
    collapsible: true,
    //是否有框
    frame:true,
    //动画收缩
    animCollapse: true,
    //高
    height: 590,
    emptyText:'查询结果为空',//查询结果为空
    store: null,
    bodyCls: 'autoHeight',
    cls: 'autoHeight',
    selModel : Ext.create('Ext.selection.CheckboxModel',{
        checkOnly: false //限制只有点击checkBox后才能选中行
    }),
    tbar : [{
        xtype : 'displayfield',
        fieldLabel:'总票数',
        allowBlank:true,
        labelWidth : 65,
        id:'Foss_predeliver_deliverHandoverbill_HandoverbillGrid_totalCount_Id',
        margin : '0 30 0 50'
    },{
        xtype : 'button',

        plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin', {
            seconds: 3
        }),
        text : '打印到达联',
        margin : '0 25 0 50',
        disabled:!predeliver.deliverHandoverbill.isPermission('deliverHandoverbillIndex/deliverHandoverbillPrintArriveSheetButtonButton'),
        hidden:!predeliver.deliverHandoverbill.isPermission('deliverHandoverbillIndex/deliverHandoverbillPrintArriveSheetButtonButton'),
        handler : function() {
            var records = this.up('grid').getSelectionModel().getSelection();
            var mygrid = this.up('gridpanel');
            if(records.length==0){
                Ext.ux.Toast.msg(predeliver.deliverHandoverbill.i18n('pkp.predeliver.notifyCustomer.tip')/*提示*/, predeliver.deliverHandoverbill.i18n('pkp.predeliver.notifyCustomer.please.select.waybill'), 'error', 1000);//'请选择运单。'
                return;
            }
            if(records[0].data.orgRoleType<1){
                Ext.ux.Toast.msg(predeliver.deliverHandoverbill.i18n('pkp.predeliver.notifyCustomer.tip')/*提示*/,'该部门没有权限进行当前操作!', 'error', 2000);
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
        margin : '0 0 0 25',
        plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin', {
            seconds: 4
        }),
        text : '撤销交单',
        disabled:!predeliver.deliverHandoverbill.isPermission('deliverHandoverbillIndex/revokedDeliverHandoverbillButton'),
        hidden:!predeliver.deliverHandoverbill.isPermission('deliverHandoverbillIndex/revokedDeliverHandoverbillButton'),
        handler : function() {
            var records = this.up('grid').getSelectionModel().getSelection(),
                waybillNoArray = new Array();
            if(records.length==0){
                Ext.ux.Toast.msg(predeliver.deliverHandoverbill.i18n('pkp.predeliver.notifyCustomer.tip')/*提示*/, predeliver.deliverHandoverbill.i18n('pkp.predeliver.notifyCustomer.please.select.waybill'), 'error', 1000);//'请选择运单。'
                return;
            }
            if(records[0].data.orgRoleType<1){
                Ext.ux.Toast.msg(predeliver.deliverHandoverbill.i18n('pkp.predeliver.notifyCustomer.tip')/*提示*/,'该部门没有权限进行当前操作!', 'error', 2000);
                return;
            }
            for (var i = 0; i < records.length; i++) {
                waybillNoArray.push(records[i].data.waybillNo);
            }
            var newVo = {
                'vo': {
                    'preDeliverHandoverbillQueryDto' : {'waybillNoList' :waybillNoArray}
                }
            };
            Ext.Ajax.request({
                url:predeliver.realPath('revokedPreDeliverbill.action'),
                jsonData : newVo,
                success: function(response){
                    Ext.ux.Toast.msg(predeliver.deliverHandoverbill.i18n('pkp.predeliver.notifyCustomer.tip')/*提示*/, '撤销成功!', 'ok', 2000);
                    Ext.getCmp('Foss_predeliver_deliverHandoverbill_handoverbillPanel_ID').getHandoverbillGrid().getPagingToolbar().moveFirst();
                },exception: function(response) {
                    var json = Ext.decode(response.responseText);
                    Ext.ux.Toast.msg('提示', json.message, 'error', 3000);
                }
            });

        }
    }, {
        xtype : 'button',
        plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin', {
            seconds: 3
        }),
        text : '导出',
        disabled:!predeliver.deliverHandoverbill.isPermission('deliverHandoverbillIndex/deliverHandoverbillExportButton'),
        hidden:!predeliver.deliverHandoverbill.isPermission('deliverHandoverbillIndex/deliverHandoverbillExportButton'),
        margin : '0 20 0 25',
        handler : function() {
            var queryPreHandoverbillForm = Ext
                    .getCmp('Foss_predeliver_deliverHandoverbill_HandoverbillQueryPanel_Id').getForm(),
                gridStore = this.up('grid').getStore(),
                records = this.up('grid').getSelectionModel().getSelection();
            if(gridStore.data.length==0){
                Ext.ux.Toast.msg(predeliver.deliverHandoverbill.i18n('pkp.predeliver.notifyCustomer.tip'), '当前查询结果无记录,不能导出!', 'error', 1000);
                return;
            }
            if (queryPreHandoverbillForm != null) {
                var qForm = queryPreHandoverbillForm.getValues();
                if(!predeliver.deliverHandoverbill.checkDeliverbillQuery(queryPreHandoverbillForm)){
                    return false;
                }
                if(!Ext.fly('downloadAttachFileForm')){
                    var frm = document.createElement('form');
                    frm.id = 'downloadAttachFileForm';
                    frm.style.display = 'none';
                    document.body.appendChild(frm);
                }
                Ext.Ajax.request({
                    url:predeliver.realPath('exportDeliverHandover.action'),
                    form: Ext.fly('downloadAttachFileForm'),
                    method : 'POST',
                    params : {
                        'deliverHandoverbillVo.deliverHandoverbillQueryDto.waybillNo':qForm.waybillNo,
                        'deliverHandoverbillVo.deliverHandoverbillQueryDto.handoverNo':qForm.handoverNo,
                        'deliverHandoverbillVo.deliverHandoverbillQueryDto.vehicleAssembleNo':qForm.vehicleAssembleNo,
                        'deliverHandoverbillVo.deliverHandoverbillQueryDto.productCode':qForm.productCode,
                        'deliverHandoverbillVo.deliverHandoverbillQueryDto.receiveMethod':qForm.receiveMethod,
                        'deliverHandoverbillVo.deliverHandoverbillQueryDto.noticeResult':qForm.noticeResult,
                        'deliverHandoverbillVo.deliverHandoverbillQueryDto.preDeliverDateFrom':qForm.preDeliverDateFrom,
                        'deliverHandoverbillVo.deliverHandoverbillQueryDto.preDeliverDateTo':qForm.preDeliverDateTo,
                        'deliverHandoverbillVo.deliverHandoverbillQueryDto.deliveryTimeInterval':qForm.deliveryTimeInterval,
                        'deliverHandoverbillVo.deliverHandoverbillQueryDto.deliveryTimeStart':qForm.deliveryTimeStart,
                        'deliverHandoverbillVo.deliverHandoverbillQueryDto.deliveryTimeOver':qForm.deliveryTimeOver,
                        'deliverHandoverbillVo.deliverHandoverbillQueryDto.handoverbillTimeStart':qForm.handoverbillTimeStart,
                        'deliverHandoverbillVo.deliverHandoverbillQueryDto.handoverbillTimeEnd':qForm.handoverbillTimeEnd,
                        'deliverHandoverbillVo.deliverHandoverbillQueryDto.deliverbillStatus':qForm.deliverbillStatus,
                        'start':gridStore.proxy.reader.jsonData.start,
                        'limit':gridStore.proxy.reader.jsonData.limit
                    },
                    isUpload: true
                });
            }else{
                //或者提示不能导出
                Ext.ux.Toast.msg(predeliver.deliverHandoverbill.i18n('pkp.predeliver.printDeliverbillArrivesheetIndex.tip'),predeliver.printDeliverbillArrivesheet.i18n('pkp.predeliver.exceptionProcess.exceptionInfo.notExport'), 'error', 3000);

            }
        }
    }],
    columns: [

        { hidden : true,dataIndex: 'orgRoleType'},
        { header: '运单号',  align: 'center', dataIndex: 'waybillNo',width : 85},
        { header: '规定兑现时间',  align: 'center', dataIndex: 'cashTime',width : 85,xtype : 'ellipsiscolumn'},
        { header: '运输性质',  align: 'center', dataIndex: 'productCode',  width : 80  },
        { header: '提货方式',  align: 'center', dataIndex: 'receiveMethod',width : 80},
        { header: '交单'+ '<br>' +'件数',  align: 'center', dataIndex: 'billQty',width : 50},
        { header: '开单'+ '<br>' +'件数',  align: 'center', dataIndex: 'goodsQtyTotal',  width : 50  },
        { header: '排单   '+ '<br>' +'件数',  align: 'center', dataIndex: 'arrangeGoodsQty',width : 50},
        { header: '重量',  align: 'center', dataIndex: 'goodsWeight',  width : 70  },
        { header: '体积',  align: 'center', dataIndex: 'goodsVolume',width : 50},
        { header: '通知情况',  align: 'center', dataIndex: 'noticeContent',  width : 80 },
        { header: '预计'+ '<br>' +'送货日期',  align: 'center', dataIndex: 'preDeliverDate',width : 100},
        { header: '送货'+ '<br>' +'时间段',  align: 'center', dataIndex: 'deliveryTimeInterval',  width : 80  },
        { header: '送货'+ '<br>' +'时间点起',  align: 'center', dataIndex: 'deliveryTimeStart',width : 80},
        { header: '送货'+ '<br>' +'时间点止',  align: 'center', dataIndex: 'deliveryTimeOver',width : 80},
        { header: '发票类型',  align: 'center', dataIndex: 'invoiceType', width : 80
        },
        { header: '发票备注',  align: 'center', dataIndex: 'invoiceDetail', width : 80,xtype: 'ellipsiscolumn'},
        { header: '交单人',  align: 'center', dataIndex: 'billOperateName',  width : 100 },
        { header: '交单时间',  align: 'center', dataIndex: 'billTime',width : 100},
        { header: '排单状态',  align: 'center', dataIndex: 'deliverBillStatus',  width : 100  }
    ],
    //enableColumnHide: false,      //取消列头菜单
   // sortableColumns: false,          //取消列头排序功能
    viewConfig: {
        enableTextSelection: true,//设置行可以选择，进而可以复制
        forceFit : true,
        stripeRows: false//显示重复样式，不用隔行显示

    },
    printWindow: null,
    getPrintWindow: function(){
        var me = this;
        if(this.printWindow==null){
            me.printWindow = Ext.create('Foss.printArriveSheet.printWindow',me);
        }
        return me.printWindow;
    },
    pagingToolbar : null,
    getPagingToolbar : function() {
        if (this.pagingToolbar == null) {
            this.pagingToolbar = Ext.create('Deppon.StandardPaging', {
                store : this.store,
                displayInfo: true,
                plugins: {
                    ptype: 'pagesizeplugin',
                    //超出输入最大限制是否提示，true则提示，默认不提示
                    alertOperation: true,
                    //自定义分页comobo数据
                    sizeList: [['50', 50], ['100', 100], ['200', 200], ['300', 300]],
                    //入最大限制，默认为200
                    maximumSize: 300
                }
            });
        }
        return this.pagingToolbar;
    },
    constructor: function(config){
        var me = this,
            cfg = Ext.apply({}, config);
        me.store = Ext.create('Foss.predeliver.deliverHandoverbill.DeliverHandoverbillStore');
        me.bbar = me.getPagingToolbar();
        me.callParent([cfg]);
    }
});
/*待交单运单Tab*/
Ext.define('Foss.predeliver.deliverHandoverbill.preHandoverbillPanel', {
    extend : 'Ext.panel.Panel',
    defaults : {
        margin : '5 10 5 10'
    },
    //width : 1024,
    layout : 'column',
    id:'Foss_predeliver_deliverHandoverbill_preHandoverbillPanel_ID',
    preHandoverbillGrid : null,
    getPreHandoverbillGrid : function() {
        if (this.preHandoverbillGrid == null) {
            this.preHandoverbillGrid = Ext.create(
                'Foss.predeliver.deliverHandoverbill.PreHandoverbillGrid', {
                    columnWidth : 1,
                    autoScroll : true
                });
        }
        return this.preHandoverbillGrid;
    },
    preHandoverbillQueryPanel : null,
    getPreHandoverbillQueryPanel : function() {
        if (this.preHandoverbillQueryPanel == null) {
            this.preHandoverbillQueryPanel = Ext.create(
                'Foss.predeliver.deliverHandoverbill.PreHandoverbillQueryPanel', {
                    columnWidth : 1,
                    autoScroll : true
                });
        }
        return this.preHandoverbillQueryPanel;
    },
    constructor: function(config){
        var me = this,
            cfg = Ext.apply({}, config);
        me.items = [me.getPreHandoverbillQueryPanel(),me.getPreHandoverbillGrid()];
        me.callParent([cfg]);
    }
});

/*已交单运单Tab*/
Ext.define('Foss.predeliver.deliverHandoverbill.HandoverbillPanel', {
    extend : 'Ext.panel.Panel',
    defaults : {
        margin : '5 10 5 10'
    },
    //width : 1024,
    layout : 'column',
    id:'Foss_predeliver_deliverHandoverbill_handoverbillPanel_ID',
    handoverbillGrid : null,
    getHandoverbillGrid : function() {
        if (this.handoverbillGrid == null) {
            this.handoverbillGrid = Ext.create(
                'Foss.predeliver.deliverHandoverbill.HandoverbillGrid', {
                    columnWidth : 1,
                    autoScroll : true
                });
        }
        return this.handoverbillGrid;
    },
    handoverbillQueryPanel : null,
    getHandoverbillQueryPanel : function() {
        if (this.handoverbillQueryPanel == null) {
            this.handoverbillQueryPanel = Ext.create(
                'Foss.predeliver.deliverHandoverbill.HandoverbillQueryPanel', {
                    columnWidth : 1,
                    autoScroll : true
                });
        }
        return this.handoverbillQueryPanel;
    },
    constructor: function(config){
        var me = this,
            cfg = Ext.apply({}, config);
        me.items = [me.getHandoverbillQueryPanel(),me.getHandoverbillGrid()	];
        me.callParent([cfg]);
    }
});

/*待交单运单-已交单运单*/
Ext.define('Foss.predeliver.deliverHandoverbill.handoverTabPanel', {
    extend : 'Ext.tab.Panel',
    cls : 'innerTabPanel',

    activeTab : 0,
    preHandoverbillPanel: null,
    getPreHandoverbillPanel: function(){
        if(Ext.isEmpty(this.preHandoverbillPanel)){
            this.preHandoverbillPanel = Ext.create(
                'Foss.predeliver.deliverHandoverbill.preHandoverbillPanel', {
                    tabConfig : {
                        width : 120
                    },
                    title : '待交单运单'
                });
        }
        return this.preHandoverbillPanel;
    },
    handoverbillPanel: null,
    getHandoverbillPanel: function(){
        if(Ext.isEmpty(this.handoverbillPanel)){
            this.handoverbillPanel = Ext.create(
                'Foss.predeliver.deliverHandoverbill.HandoverbillPanel', {
                    tabConfig : {
                        width : 120
                    },
                    title : '已交单运单'
                });
        }
        return this.handoverbillPanel;
    },
    initComponent : function() {
        var me = this;
        Ext.applyIf(me, {
            items : [
                me.getPreHandoverbillPanel(),
                me.getHandoverbillPanel()
            ]
        });

        me.callParent(arguments);
    }
});

Ext.onReady(function() {
    Ext.QuickTips.init();
    var handoverTabPanel = Ext.create("Foss.predeliver.deliverHandoverbill.handoverTabPanel");
    Ext.create('Ext.panel.Panel', {
        id : 'T_predeliver-deliverHandoverBillIndex_content',
        cls : "panelContent",
        margin:'-10 0 0 0',
        bodyCls : 'panelContent-body',
        layout : 'auto',
        getPanel : function() {
            return handoverTabPanel;
        },
        items : [handoverTabPanel],
        renderTo : 'T_predeliver-deliverHandoverBillIndex-body'
    });
});
