/**
 * 发票合并运单新增页面
 * Created by 322906 on 2016/5/28.
 */
//定义上线日期
var MINDATE = '2016-12-01';
// 发票标记
Ext.define('Foss.invoiceMergeWaybill.InvoiceStore', {
    extend: 'Ext.data.Store',
    fields: [{
        name: 'valueCode',
        type: 'string'
    }, {
        name: 'valueName',
        type: 'string'
    }],
    data: {
        'items': [{
            valueCode: 'INVOICE_TYPE_01',
            valueName: consumer.invoiceMergeWaybillAdd.i18n('foss.stl.consumer.invoiceMergeWaybill.invoiceMarkOne')// 01—运输专票11%
        }, {
            valueCode: 'INVOICE_TYPE_02',
            valueName: consumer.invoiceMergeWaybillAdd.i18n('foss.stl.consumer.invoiceMergeWaybill.invoiceMarkTwo')// 02—非运输专票
        }
        ]
    },
    proxy: {
        type: 'memory',
        reader: {
            type: 'json',
            root: 'items'
        }
    }
});

//统一结算
Ext.define('Foss.invoiceMergeWaybill.UnifiedSettlementStore', {
    extend: 'Ext.data.Store',
    fields: [{
        name: 'valueCode',
        type: 'string'
    }, {
        name: 'valueName',
        type: 'string'
    }],
    data: {
        'items': [{
            valueCode: 'N',
            valueName: consumer.invoiceMergeWaybillAdd.i18n('foss.stl.consumer.common.no')//否
        },{
            valueCode: 'Y',
            valueName: consumer.invoiceMergeWaybillAdd.i18n('foss.stl.consumer.common.yes')//是
        }
        ]
    },
    proxy: {
        type: 'memory',
        reader: {
            type: 'json',
            root: 'items'
        }
    }
});

// 业务类型
Ext.define('Foss.invoiceMergeWaybill.BusinessCaseStore', {
    extend: 'Ext.data.Store',
    fields: [{
        name: 'valueCode',
        type: 'string'
    }, {
        name: 'valueName',
        type: 'string'
    }],
    data: {
        'items': [{
            valueCode: '02',
            valueName: consumer.invoiceMergeWaybillAdd.i18n('foss.stl.consumer.invoiceMergeWaybill.breakbulk')// 零担
        }, {
            valueCode: '01',
            valueName: consumer.invoiceMergeWaybillAdd.i18n('foss.stl.consumer.invoiceMergeWaybill.express')// 快递
        }
        ]
    },
    proxy: {
        type: 'memory',
        reader: {
            type: 'json',
            root: 'items'
        }
    }
});
// 单据类型（统一结算时，可以选择all,不是统一结算只能选始发或者到达）
Ext.define('Foss.invoiceMergeWaybill.CustomerType', {
    extend: 'Ext.data.Store',
    fields: [{
        name: 'valueCode',
        type: 'string'
    }, {
        name: 'valueName',
        type: 'string'
    }],
    data: {
        'items': [{
            valueCode: 'ALL',
            valueName: consumer.invoiceMergeWaybillAdd.i18n('foss.stl.consumer.common.all')//全部
        },{
            valueCode: 'OC',
            valueName: consumer.invoiceMergeWaybillAdd.i18n('foss.stl.consumer.invoiceMergeWaybill.original')// 始发
        }, {
            valueCode: 'DC',
            valueName: consumer.invoiceMergeWaybillAdd.i18n('foss.stl.consumer.invoiceMergeWaybill.delivery')// 到达
        }
        ]
    },
    proxy: {
        type: 'memory',
        reader: {
            type: 'json',
            root: 'items'
        }
    }
});

/**
 * Form重置方法
 */
consumer.invoiceMergeWaybillAdd.reset= function(){
    this.up('form').getForm().reset();
}

/**
 * 设置查询参数
 * @param form
 * @param queryType
 * @returns {*}
 */
consumer.invoiceMergeWaybillAdd.setParams = function(form) {
    // 定义查询参数
    var params = {};
    var customerCodes = form.findField('customerCodes').getValue();
    var startDate = form.findField('startDate').getValue();
    var endDate = form.findField('endDate').getValue();
    var invoiceTitle = form.findField('invoiceTitle').getValue();
    var taxId = form.findField('taxId').getValue();
    var product = form.findField('product').getValue();

    var orgCode = form.findField('orgCode').getValue();

    var invoiceMark = form.findField('invoiceMark').getValue();
    var customerType = form.findField('customerType').getValue();
    var unifiedSettlement = form.findField('unifiedSettlement').getValue();
    var paidMethods = form.findField('paidMethods').getValue();

    var customerCodesArray = [];//客户编码数组
    if(Ext.String.trim(customerCodes)==null || Ext.String.trim(customerCodes)==''){
        Ext.Msg.alert(consumer.invoiceMergeWaybillAdd.i18n('foss.stl.consumer.common.alert'),consumer.invoiceMergeWaybillAdd.i18n('foss.stl.consumer.invoiceMergeWaybill.customerCodeNotNull'));
        return false;
    }else{
        var reg = /[,\n]/;
        var array = customerCodes.split(reg);
        for (var i = 0; i < array.length; i++) {
            if (Ext.String.trim(array[i]) != '') {
                customerCodesArray.push(array[i]);
            }
        }
        if(customerCodesArray.length>5){
            Ext.Msg.alert(consumer.invoiceMergeWaybillAdd.i18n('foss.stl.consumer.common.alert'),consumer.invoiceMergeWaybillAdd.i18n('foss.stl.consumer.invoiceMergeWaybill.customerNosLimit'));
            return false;
        }
    }

    if(invoiceTitle==null || invoiceTitle=='' || taxId ==null ||taxId==''){
        Ext.Msg.alert(consumer.invoiceMergeWaybillAdd.i18n('foss.stl.consumer.common.alert'),consumer.invoiceMergeWaybillAdd.i18n('foss.stl.consumer.invoiceMergeWaybill.correctCustomer'));//请正确填写客户编码，获取纳税人信息后再查询
        return ;
    }

    if(unifiedSettlement=='N' && customerType=='ALL'){
        Ext.Msg.alert(consumer.invoiceMergeWaybillAdd.i18n('foss.stl.consumer.common.alert'),consumer.invoiceMergeWaybillAdd.i18n('foss.stl.consumer.invoiceMergeWaybill.settlementNotAll'));//非统一结算时单据类型不能选择全部
        return ;
    }

    if (!startDate) {
        // 开始日期不能为空
        Ext.Msg.alert(consumer.invoiceMergeWaybillAdd.i18n('foss.stl.consumer.common.warmTips'), consumer.invoiceMergeWaybillAdd
            .i18n('foss.stl.consumer.invoiceMergeWaybill.createStartDateCannotBeNull'));
        return null;
    }
    if (!endDate) {
        // 结束日期不能为空
        Ext.Msg.alert(consumer.invoiceMergeWaybillAdd.i18n('foss.stl.consumer.common.warmTips'), consumer.invoiceMergeWaybillAdd
            .i18n('foss.stl.consumer.invoiceMergeWaybill.createEndDateCannotBeNull'));
        return null;
    }


    // 转化成日期对象
    startDate = Ext.Date.parse(startDate, "Y-m-d H:i:s", true);
    endDate = Ext.Date.parse(endDate, "Y-m-d H:i:s", true);

    var diffDays = stl.compareTwoDate(startDate, endDate);
    if (diffDays > stl.DATELIMITDAYS_MONTH) {

        // 录入起始日期和结束日期间隔不能超过{0}天
        // stl.DATELIMITDAYS_MONTH
        var promptMsg = consumer.invoiceMergeWaybillAdd.i18n('foss.stl.consumer.invoiceMergeWaybill.createDateDiffCannotExceedSomeDays')
        Ext.Msg.alert(consumer.invoiceMergeWaybillAdd.i18n('foss.stl.consumer.common.warmTips'), promptMsg);
        return null;
    } else if (diffDays < 1) {

        // 录入开始日期不能晚于结束日期
        Ext.Msg.alert(consumer.invoiceMergeWaybillAdd.i18n('foss.stl.consumer.common.warmTips'), consumer.invoiceMergeWaybillAdd
            .i18n('foss.stl.consumer.invoiceMergeWaybill.createStartDateCannotGtEndDate'));
        return null;
    }
    // 获取FORM所有值
    params = {
        'vo.waybillQueryDto.customerCodes': customerCodesArray,
        'vo.waybillQueryDto.startDate' : startDate,
        'vo.waybillQueryDto.endDate' : endDate,
        'vo.waybillQueryDto.invoiceTitle' : invoiceTitle,
        'vo.waybillQueryDto.taxId' : taxId,
        'vo.waybillQueryDto.product' : product,
        'vo.waybillQueryDto.orgCode' : orgCode,
        'vo.waybillQueryDto.invoiceMark' : invoiceMark,
        'vo.waybillQueryDto.customerType' : customerType,
        'vo.waybillQueryDto.unifiedSettlement' : unifiedSettlement,
        'vo.waybillQueryDto.paidMethods' : paidMethods
    };

    return params;
}


/**
 * 查询运单
 */
consumer.invoiceMergeWaybillAdd.query=function(f,me){
    var form = f.getForm();
    var grid = Ext.getCmp('T_consumer-invoiceMergeWaybillAdd_content').getWaybillGrid();
    if (form.isValid()) {
        var params = consumer.invoiceMergeWaybillAdd.setParams(form);
        if (null == params) {
            return;
        }
        // 设置查询参数
        grid.store.setSubmitParams(params);
        // 设置该按钮灰掉
        me.disable(false);
        // 3秒后自动解除灰掉效果
        setTimeout(function() {
            me.enable(true);
        }, 3000);

        grid.store.load();
    } else {
        // 请检查输入条件是否合法
        Ext.Msg.alert(consumer.invoiceMergeWaybillAdd.i18n('foss.stl.consumer.common.warmTips'), consumer.invoiceMergeWaybillAdd
            .i18n('foss.stl.consumer.note.pleaseCheckConditionLegal'));
    }

};

/**
 * 根据客户编码查询客户信息
 */
consumer.invoiceMergeWaybillAdd.queryCustomerInfoByCodes = function(customerCodes, form) {
    if(customerCodes!=null && customerCodes!=''){
        var codeArray = [];
        if(Ext.String.trim(customerCodes)==null || Ext.String.trim(customerCodes)==''){
            Ext.Msg.alert(consumer.invoiceMergeWaybillAdd.i18n('foss.stl.consumer.common.alert'),consumer.invoiceMergeWaybillAdd.i18n('foss.stl.consumer.invoiceMergeWaybill.customerCodeNotNull'));
            return false;
        }else{
            var reg = /[,\n]/;
            var array = customerCodes.split(reg);
            for(var i=0;i<array.length;i++){
                if(Ext.String.trim(array[i])!=''){
                    codeArray.push(array[i]);
                }
            }
            if(codeArray.length>5){
                Ext.Msg.alert(consumer.invoiceMergeWaybillAdd.i18n('foss.stl.consumer.common.alert'),consumer.invoiceMergeWaybillAdd.i18n('foss.stl.consumer.invoiceMergeWaybill.customerNosLimit'));
                return false;
            }
        }

        Ext.Ajax.request({
            url : consumer.realPath('queryCustomerInfoFromCrm.action'),
            timeout:60000,
            params : {
                'vo.waybillQueryDto.customerCodes' : codeArray
            },
            method : 'post',
            success : function(response) {
                var result = Ext.decode(response.responseText);
                var taxPayer = result.vo.taxPayer;//纳税人信息map
                if (taxPayer) {
                    var invoiceTitle = taxPayer.invoiceTitle;
                    var taxId = taxPayer.taxId;
                    form.getForm().findField('invoiceTitle').setValue(invoiceTitle);
                    form.getForm().findField('taxId').setValue(taxId);
                }

            },
            error:function(response){
                var result = Ext.decode(response.responseText);
                Ext.Msg.alert(consumer.invoiceMergeWaybillAdd.i18n('foss.stl.consumer.common.warmTips'), result.message);
            },
            exception : function(response) {
                var result = Ext.decode(response.responseText);
                Ext.Msg.alert(consumer.invoiceMergeWaybillAdd.i18n('foss.stl.consumer.common.warmTips'), result.message);
            }
        });
    }

}
/**
 * 付款方式
 */

consumer.invoiceMergeWaybillAdd.PaymentTypeStore= new Ext.data.SimpleStore({
    fields:['code','name'],
    data:[[
        'CH', consumer.invoiceMergeWaybillAdd.i18n('foss.stl.consumer.invoiceMergeWaybill.paymentType.CH')// 现金
    ], [
        'CD', consumer.invoiceMergeWaybillAdd.i18n('foss.stl.consumer.invoiceMergeWaybill.paymentType.CD')// 银行卡
    ], [
        'CT', consumer.invoiceMergeWaybillAdd.i18n('foss.stl.consumer.invoiceMergeWaybill.paymentType.CT')// 月结
    ], [
        'DT', consumer.invoiceMergeWaybillAdd.i18n('foss.stl.consumer.invoiceMergeWaybill.paymentType.DT')// 临时欠款
    ],[
        'OL', consumer.invoiceMergeWaybillAdd.i18n('foss.stl.consumer.invoiceMergeWaybill.paymentType.OL')// 网上支付
    ], [
        'FC', consumer.invoiceMergeWaybillAdd.i18n('foss.stl.consumer.invoiceMergeWaybill.paymentType.FC')// 到付
    ]
    ]
});

//按客户合并查询 FORM
Ext.define('Foss.invoiceMergeWaybill.QueryForm',{
    extend:'Ext.form.Panel',
    title:consumer.invoiceMergeWaybillAdd.i18n('foss.stl.consumer.invoiceMergeWaybill.mergeByCustomerCode'),//按客户编码合并
    frame:true,
    columnWidth:1,
    collapsible:true,
    defaults:{
        margin :'5 10 5 0',
        labelWidth :85,
        colspan :1
    },
    defaultType:'textfield',
    layout:{
        type :'table',
        columns :3
    },
    items:[
        {
            xtype: 'textareafield',
            name: 'customerCodes',
            fieldLabel: consumer.invoiceMergeWaybillAdd.i18n('foss.stl.consumer.invoiceMergeWaybill.customerCode'), // 客户编码
            emptyText : consumer.invoiceMergeWaybillAdd.i18n('foss.stl.consumer.invoiceMergeWaybill.customerCodeNotice'),//请输入客户编码,以“,”分隔，最多5个
            allowBlank: false,//不能为空
            //regex:/^([A-Za-z0-9\\-]+[,])*([A-Za-z0-9\\-]+[,]?)$/i,
            regexText:'请输入1-5个客户编码，以,符号隔开',
            maxLength:1000,//长度最大1000
            height:50,
            width:300,
            listeners : {
                blur:function(_this, the, eOpts ){
                    var customerCode = _this.value;
                    var form = this.up('form');
                    consumer.invoiceMergeWaybillAdd.queryCustomerInfoByCodes(customerCode, form);
                },
                focus:function(_this,the,eOpts){
                    var form = this.up('form');
                    form.getForm().findField('invoiceTitle').setValue("");
                    form.getForm().findField('taxId').setValue("");
                }
            }
        },
        {
            xtype : 'datetimefield_date97',
            fieldLabel : consumer.invoiceMergeWaybillAdd.i18n('foss.stl.consumer.invoiceMergeWaybill.businessDate'),//业务日期
            id : 'FOSS_Consumer_invoiceMergeWaybillAdd_StartDate_ID',
            time : true,
            name : 'startDate',
            editable : 'false',
            value : stl.dateFormat(new Date(), stl.FORMAT_DATE) + stl.START_PREFIX,
            dateConfig : {
                el : 'FOSS_Consumer_invoiceMergeWaybillAdd_StartDate_ID-inputEl',
                dateFmt : 'yyyy-MM-dd HH:mi:ss',
                minDate: stl.dateFormat(MINDATE, stl.FORMAT_DATE) + stl.START_PREFIX
            }
        }, {
            xtype : 'datetimefield_date97',
            fieldLabel : consumer.invoiceMergeWaybillAdd.i18n('foss.stl.consumer.invoiceMergeWaybill.to'),//至（结束日期）
            id : 'FOSS_Consumer_invoiceMergeWaybillAdd_EndDate_ID',
            time : true,
            name : 'endDate',
            editable : 'false',
            value : stl.dateFormat(new Date(), stl.FORMAT_DATE) + stl.END_PREFIX,
            dateConfig : {
                el : 'FOSS_Consumer_invoiceMergeWaybillAdd_EndDate_ID-inputEl',
                dateFmt : 'yyyy-MM-dd HH:mi:ss',
                maxDate : stl.dateFormat(new Date(), stl.FORMAT_DATE) + stl.END_PREFIX,
                minDate: stl.dateFormat(MINDATE, stl.FORMAT_DATE) + stl.END_PREFIX
            }
        },
        {
            name: 'invoiceTitle',
            fieldLabel: consumer.invoiceMergeWaybillAdd.i18n('foss.stl.consumer.invoiceMergeWaybill.invoiceTitle'),// 发票抬头
            allowBlank: false,
            readOnly:true,
            cls: 'readonlyhaveborder'
        },
        {
            name: 'taxId',
            fieldLabel: consumer.invoiceMergeWaybillAdd.i18n('foss.stl.consumer.invoiceMergeWaybill.taxId'),// 税务登记号
            allowBlank: false,
            readOnly:true,
            cls: 'readonlyhaveborder'
        },
        {
            xtype: 'combo',
            name: 'product',
            fieldLabel: consumer.invoiceMergeWaybillAdd.i18n('foss.stl.consumer.invoiceMergeWaybill.businessType'),// 业务类型
            autoSelect:true,
            value:'02',
            valueField: 'valueCode',
            displayField: 'valueName',
            queryMode: 'local',
            store: Ext.create('Foss.invoiceMergeWaybill.BusinessCaseStore')
        },
        {
            name:'orgCode',
            value:stl.currentDept.code,
            hidden:true
        },
        {
            name: 'orgName',
            fieldLabel: consumer.invoiceMergeWaybillAdd.i18n('foss.stl.consumer.invoiceMergeWaybill.department'),// 所属部门
            cls: 'readonlyhaveborder',
            readOnly:true,
            value:stl.currentDept.name

        }, {// 发票标记默认全部
            xtype: 'combo',
            name: 'invoiceMark',
            fieldLabel: consumer.invoiceMergeWaybillAdd.i18n('foss.stl.consumer.invoiceMergeWaybill.invoiceMark'),// 发票标记
            valueField: 'valueCode',
            displayField: 'valueName',
            autoSelect:true,
            value:'INVOICE_TYPE_02',
            queryMode: 'local',
            store: Ext.create('Foss.invoiceMergeWaybill.InvoiceStore')
        },
        {
            xtype: 'combo',
            name: 'unifiedSettlement',
            fieldLabel: consumer.invoiceMergeWaybillAdd.i18n('foss.stl.consumer.invoiceMergeWaybill.unifiedSettlement'),// 是否统一结算
            valueField: 'valueCode',
            value:'Y',
            displayField: 'valueName',
            queryMode: 'local',
            store: Ext.create('Foss.invoiceMergeWaybill.UnifiedSettlementStore'),
            listeners:{
                change: function(me, newValue, oldValue, eOpts){//是否同一结算的值为是的时候才可选单据类型
                    //var form = me.up('form').getForm();
                    var form = me.up('form').getForm();
                    var value = me.value;
                    if(value =='Y'){
                        if(form.findField("customerType").store.getAt(0).valueCode !='ALL'){
                            form.findField("customerType").store.insert(0,[{'valueCode':'ALL','valueName':'全部'}]);
                        }
                    }else{
                        if(form.findField("customerType").store.getAt(0).valueCode ='ALL'){
                            form.findField("customerType").store.removeAt(0);
                        }
                    }
                }
            }
        },
        {
            xtype: 'combo',
            name: 'customerType',
            fieldLabel: consumer.invoiceMergeWaybillAdd.i18n('foss.stl.consumer.invoiceMergeWaybill.billType'),// 单据类型（始发，到达）
            editable: false,
            value:'ALL',
            valueField: 'valueCode',
            displayField: 'valueName',
            queryMode: 'local',
            store: Ext.create('Foss.invoiceMergeWaybill.CustomerType'),
            listeners:{
                select:function(me){
                    consumer.invoiceMergeWaybillAdd.PaymentTypeStore.load();
                    var form = me.up('form').getForm();
                    form.findField("paidMethods").expand();
                    form.findField("paidMethods").setValue('');
                    consumer.invoiceMergeWaybillAdd.PaymentTypeStore.removeAll();
                    if(me.value =='DC'){
                        var data = [[
                            'FC',consumer.invoiceMergeWaybillAdd.i18n('foss.stl.consumer.invoiceMergeWaybill.paymentType.FC')
                        ]];
                        consumer.invoiceMergeWaybillAdd.PaymentTypeStore.loadData(data);
                    }
                    if(me.value=='OC'){
                        var data = [
                            [
                                'CH',consumer.invoiceMergeWaybillAdd.i18n('foss.stl.consumer.invoiceMergeWaybill.paymentType.CH')
                            ], [
                                'CD',consumer.invoiceMergeWaybillAdd.i18n('foss.stl.consumer.invoiceMergeWaybill.paymentType.CD')
                            ], [
                                'CT', consumer.invoiceMergeWaybillAdd.i18n('foss.stl.consumer.invoiceMergeWaybill.paymentType.CT')// 月结
                            ], [
                                'DT',consumer.invoiceMergeWaybillAdd.i18n('foss.stl.consumer.invoiceMergeWaybill.paymentType.DT')// 临时欠款
                            ],[
                                'OL', consumer.invoiceMergeWaybillAdd.i18n('foss.stl.consumer.invoiceMergeWaybill.paymentType.OL')// 网上支付
                            ]
                        ];
                        consumer.invoiceMergeWaybillAdd.PaymentTypeStore.loadData(data);
                    }
                    if(me.value =='ALL'){

                        var data = [
                            [
                                'CH', consumer.invoiceMergeWaybillAdd.i18n('foss.stl.consumer.invoiceMergeWaybill.paymentType.CH')// 现金
                            ], [
                                'CD', consumer.invoiceMergeWaybillAdd.i18n('foss.stl.consumer.invoiceMergeWaybill.paymentType.CD')// 银行卡
                            ], [
                                'CT', consumer.invoiceMergeWaybillAdd.i18n('foss.stl.consumer.invoiceMergeWaybill.paymentType.CT')// 月结
                            ], [
                                'DT', consumer.invoiceMergeWaybillAdd.i18n('foss.stl.consumer.invoiceMergeWaybill.paymentType.DT')// 临时欠款
                            ],[
                                'OL', consumer.invoiceMergeWaybillAdd.i18n('foss.stl.consumer.invoiceMergeWaybill.paymentType.OL')// 网上支付
                            ], [
                                'FC', consumer.invoiceMergeWaybillAdd.i18n('foss.stl.consumer.invoiceMergeWaybill.paymentType.FC')// 到付
                            ]
                        ];
                        consumer.invoiceMergeWaybillAdd.PaymentTypeStore.loadData(data);
                    }
                }
            }
        },
        {
            xtype : 'combo',
            name : 'paidMethods',
            fieldLabel : consumer.invoiceMergeWaybillAdd.i18n('foss.stl.consumer.invoiceMergeWaybill.paymentType'),// 付款方式
            multiSelect: true,
            allowBlank:false,
            store:consumer.invoiceMergeWaybillAdd.PaymentTypeStore,
            valueField : 'code',
            displayField : 'name',
            autoShow:true
        }, {
            xtype:'container',
            border:false,
            height:24,
            html:'&nbsp;',
            columnWidth:.33
        },
        {
            border: 1,
            xtype:'container',
            columnWidth:1,
            colspan:3,
            defaultType:'button',
            layout:'column',
            items:[{
                text:consumer.invoiceMergeWaybillAdd.i18n('foss.stl.consumer.common.reset'),
                columnWidth:.12,
                handler:consumer.invoiceMergeWaybillAdd.reset
            },{
                xtype:'container',
                border:false,
                html:'&nbsp;',
                columnWidth:.76
            }, {
                text:consumer.invoiceMergeWaybillAdd.i18n('foss.stl.consumer.common.query'),
                columnWidth:.12,
                cls:'yellow_button',
                handler:function(_this){
                    var form=_this.up('form');
                    consumer.invoiceMergeWaybillAdd.query(form,_this)
                }
            }]
        }
    ]
});


Ext.define('Foss.invoiceMergeWaybill.waybillModel', {
    extend :'Ext.data.Model',
    fields :[
        {
            name :'id',
            type :'string'
        }, {
            name :'billTime',//开单时间
            type :'date',
            convert:function(value) {
                if (value != null) {
                    var date = new Date(value);
                    return date;
                } else {
                    return null;
                }
            }
        }, {
            name :'waybillNo',  //运单号
            type :'string'
        }, {
            name :'product',  //业务类型
            type :'string',
            convert:function(value) {
                if (value == '01') {
                    return '快递';
                } else if (value == '02') {
                    return '零担';
                }
            }
        }, {
            name :'prePayAmount',  //预付金额
            type :'long'
        }, {
            name :'toPayAmount',  //到付金额
            type :'long'
        }, {
            name :'deliveryCustomerCode',  //发货方客户编码
            type :'string'
        }, {
            name :'receiveCustomerCode',  //收货方客户编码
            type :'string'
        }, {
            name :'invoiceMark',
            type :'string'
        },{
            name :'receiveOrgCode',  //收货部门编码
            type :'string'
        }, {
            name :'receiveOrgName',  //收货部门名称
            type :'string'
        }, {
            name :'destOrgCode',  //到达部门编码
            type :'string'
        }, {
            name :'destOrgName',  //到达部门名称
            type :'string'
        }, {
            name :'origDunningOrgName',  //出发催款部门
            type :'string'
        }, {
            name :'destDunningOrgName',  //到达催款部门
            type :'string'
        }, {
            name :'origContractOrgName',  //出发合同部门
            type :'string'
        }, {
            name :'destContractOrgName',  //到达合同部门
            type :'string'
        }
    ]
});

/**
 * 运单store
 */
Ext.define('Foss.invoiceMergeWaybill.waybillStore',{
    extend : 'Ext.data.Store',
    model : 'Foss.invoiceMergeWaybill.waybillModel',
    pageSize : 150,
    proxy : {
        type : 'ajax',
        actionMethods : 'post',
        url : consumer.realPath('queryWaybillByCondition.action'),
        reader : {
            type : 'json',
            root : 'vo.waybillDetailEntityList',
            totalProperty : 'totalCount'
        }
    },

    submitParams : {},
    setSubmitParams : function(submitParams) {
        this.submitParams = submitParams;
    },
    constructor : function(config) {
        var me = this, cfg = Ext.apply({}, config);
        me.listeners = {
            'beforeload' : function(store, operation, eOpts) {
                var value = Ext.getCmp('T_consumer-invoiceMergeWaybillAdd_content').getQueryForm().getForm().findField("taxId").getValue();
                if(value==null || value==''){
                    Ext.ux.Toast.msg(consumer.invoiceMergeWaybillAdd.i18n('foss.stl.consumer.common.warmTips'), '请输入客户编码，获取税务号'
                        ,'ok',1000);
                    return false;
                }
                Ext.apply(me.submitParams, {
                    "limit" : operation.limit,
                    "page" : operation.page,
                    "start" : operation.start
                });
                Ext.apply(operation, {
                    params : me.submitParams
                });
            }
        };
        me.callParent([cfg]);
    }
});
/**
 * 运单合并操作
 */
consumer.invoiceMergeWaybillAdd.mergeWaybill = function(){
    var me=Ext.getCmp('T_consumer-invoiceMergeWaybillAdd_content');
    var grid,paidMethods,unifiedSettlement,customerType,taxId,invoiceTitle,product,invoiceMark;
    var number;
    paidMethods = me.getQueryForm().getForm().findField("paidMethods").value;
    unifiedSettlement = me.getQueryForm().getForm().findField("unifiedSettlement").value;
    customerType = me.getQueryForm().getForm().findField("customerType").value;
    taxId = me.getQueryForm().getForm().findField("taxId").value;
    invoiceTitle = me.getQueryForm().getForm().findField("invoiceTitle").value;
    product = me.getQueryForm().getForm().findField("product").value;
    invoiceMark = me.getQueryForm().getForm().findField("invoiceMark").value;
    grid = me.getWaybillGrid();
    var selections=grid.getSelectionModel().getSelection();
    number = selections.length;
    if(number<150 || number>1000 ){
        Ext.Msg.alert(consumer.invoiceMergeWaybillAdd.i18n('foss.stl.consumer.common.warmTips')
            , consumer.invoiceMergeWaybillAdd.i18n('foss.stl.consumer.invoiceMergeWaybill.mergeNumber'));//合并条数必须大于150,小于1000
    }
    var waybillNos = [];
    for(var i = 0;i < selections.length;i++){
        waybillNos.push(selections[i].data.waybillNo)
    }
    Ext.Msg.confirm( consumer.invoiceMergeWaybillAdd.i18n('foss.stl.consumer.common.warmTips'),
        consumer.invoiceMergeWaybillAdd.i18n('foss.stl.consumer.invoiceMergeWaybill.sureToMerge',[number]), //已选择【{0}】条,确定合并吗？
        function(btn,text){
            if(btn == 'yes'){
                Ext.Ajax.request({
                    url:consumer.realPath('mergeWaybill.action'),
                    params:{
                        'vo.waybillQueryDto.waybillNos':waybillNos,
                        'vo.waybillQueryDto.paidMethods':paidMethods,
                        'vo.waybillQueryDto.unifiedSettlement':unifiedSettlement,
                        'vo.waybillQueryDto.customerType':customerType,
                        'vo.waybillQueryDto.taxId':taxId,
                        'vo.waybillQueryDto.product':product,
                        'vo.waybillQueryDto.invoiceMark':invoiceMark,
                        'vo.waybillQueryDto.invoiceTitle':invoiceTitle
                    },
                    method:'post',
                    success:function(response){
                        grid.store.load({
                            callback: function(records, operation, success) {
                                var result =   Ext.decode(operation.response.responseText);
                                if(success){
                                    if(result.isException){
                                        Ext.Msg.alert(consumer.invoiceMergeWaybillAdd.i18n('foss.stl.consumer.common.warmTips'),result.message);
                                        return false;
                                    }
                                }
                            }
                        });
                        Ext.Msg.alert(consumer.invoiceMergeWaybillAdd.i18n('foss.stl.consumer.common.warmTips'),"合并成功！");
                    },
                    exception:function(response){
                        var result = Ext.decode(response.responseText);
                        Ext.Msg.alert(consumer.invoiceMergeWaybillAdd.i18n('foss.stl.consumer.common.warmTips'),result.message);
                    },
                    failure:function(response){
                        var result = Ext.decode(response.responseText);
                        Ext.Msg.alert(consumer.invoiceMergeWaybillAdd.i18n('foss.stl.consumer.common.warmTips'),result.message);
                    }
                });
            }
        });
};
//运单明细grid
Ext.define('Foss.invoiceMergeWaybill.waybillGrid',{
    extend:'Ext.grid.Panel',
    title:consumer.invoiceMergeWaybillAdd.i18n('foss.stl.consumer.invoiceMergeWaybill.waybillDetail'),//运单明细列表
    columnWidth:1,
    selModel:Ext.create('Ext.selection.CheckboxModel'),
    stripeRows:true,
    columnLines:true,
    collapsible:false,
    bodyCls:'autoHeight',
    emptyText: consumer.invoiceMergeWaybillAdd.i18n('foss.stl.consumer.common.emptyText'),
    viewConfig:{
        enableTextSelection : true//设置行可以选择，进而可以复制
    },
    frame:true,//增加表格列的分割线
    cls:'autoHeight',
    store :null,
    autoScroll :true,
    height: 650,
    pagingToolbar:null,//分页
    getPagingToolbar:function(){
        var me = this;
        if(Ext.isEmpty(me.pagingToolbar)){
            me.pagingToolbar = Ext.create('Deppon.StandardPaging',{
                store:me.store,
                pageSize:150,
                maximumSize:1000,
                plugins : Ext.create('Deppon.ux.PageSizePlugin', {
                    sizeList : [['150', 150], ['300', 300], ['500', 500],['1000',1000]]
                })
            });
        }
        return me.pagingToolbar;
    },
    dockedItems:[{
        xtype :'toolbar',
        dock :'top',
        layout :'column',
        defaults :{
            margin :'0 0 5 3'
        },
        items :[ {
            xtype :'button',
            text :consumer.invoiceMergeWaybillAdd.i18n('foss.stl.consumer.invoiceMergeWaybill.merge'),//合并
            columnWidth :.1,
            handler : consumer.invoiceMergeWaybillAdd.mergeWaybill
        }]
    }],
    columns :[
        { xtype : 'rownumberer',width:48},
        {
            text:consumer.invoiceMergeWaybillAdd.i18n('foss.stl.consumer.invoiceMergeWaybill.billTime'), //开单日期
            dataIndex :'billTime',
            renderer:function(value){
                if(value!=null){
                    return Ext.Date.format(new Date(value), 'Y-m-d');
                }else{
                    return null;
                }
            }
        },
        {
            text:consumer.invoiceMergeWaybillAdd.i18n('foss.stl.consumer.invoiceMergeWaybill.waybillNo'), //运单号
            dataIndex :'waybillNo'
        },
        {
            text:consumer.invoiceMergeWaybillAdd.i18n('foss.stl.consumer.invoiceMergeWaybill.businessType'), //业务类型
            dataIndex :'product'
        },
        {
            text:consumer.invoiceMergeWaybillAdd.i18n('foss.stl.consumer.invoiceMergeWaybill.invoiceMark'), //发票标记
            dataIndex :'invoiceMark'
        },
        {
            text:consumer.invoiceMergeWaybillAdd.i18n('foss.stl.consumer.invoiceMergeWaybill.prePayAmount'), //预付金额
            dataIndex :'prePayAmount'
        },
        {
            text:consumer.invoiceMergeWaybillAdd.i18n('foss.stl.consumer.invoiceMergeWaybill.toPayAmount'), //到付金额
            dataIndex :'toPayAmount'
        },
        {
            text:consumer.invoiceMergeWaybillAdd.i18n('foss.stl.consumer.invoiceMergeWaybill.deliveryCustomerCode'), //发货方客户编码
            dataIndex :'deliveryCustomerCode'
        },
        {
            text:consumer.invoiceMergeWaybillAdd.i18n('foss.stl.consumer.invoiceMergeWaybill.receiveCustomerCode'), //收货方客户编码
            dataIndex :'receiveCustomerCode'
        },
        {
            text:consumer.invoiceMergeWaybillAdd.i18n('foss.stl.consumer.invoiceMergeWaybill.receiveOrgCode'), //收货部门编码
            dataIndex :'receiveOrgCode'
        },
        {
            text:consumer.invoiceMergeWaybillAdd.i18n('foss.stl.consumer.invoiceMergeWaybill.receiveOrgName'), //收货部门名称
            dataIndex :'receiveOrgName'
        },
        {
            text:consumer.invoiceMergeWaybillAdd.i18n('foss.stl.consumer.invoiceMergeWaybill.arriveOrgCode'), //到达部门编码
            dataIndex :'destOrgCode'
        },
        {
            text:consumer.invoiceMergeWaybillAdd.i18n('foss.stl.consumer.invoiceMergeWaybill.arriveOrgName'), //到达部门名称
            dataIndex :'destOrgName'
        },
        {
            text:consumer.invoiceMergeWaybillAdd.i18n('foss.stl.consumer.invoiceMergeWaybill.originalDunningOrgName'), //出发催款部门
            dataIndex :'origDunningOrgName'
        },
        {
            text:consumer.invoiceMergeWaybillAdd.i18n('foss.stl.consumer.invoiceMergeWaybill.originalContractOrgName'), //出发合同部门
            dataIndex :'origContractOrgName'
        },
        {
            text:consumer.invoiceMergeWaybillAdd.i18n('foss.stl.consumer.invoiceMergeWaybill.arriveDunningOrgName'), //到达催款部门
            dataIndex :'destDunningOrgName'
        },
        {
            text:consumer.invoiceMergeWaybillAdd.i18n('foss.stl.consumer.invoiceMergeWaybill.arriveContractOrgName'), //到达合同名称
            dataIndex :'destContractOrgName'
        }

    ],
    constructor:function(config){
        var me = this, cfg = Ext.apply({}, config);
        me.store = Ext.create('Foss.invoiceMergeWaybill.waybillStore');
        me.bbar = me.getPagingToolbar();
        me.getPagingToolbar().store = me.store;
        me.callParent([ cfg ]);
    }
});



Ext.onReady(function() {
    Ext.QuickTips.init();
    if (Ext.getCmp('T_consumer-invoiceMergeWaybillAdd_content')) {
        return;
    }
    var queryForm = Ext.create('Foss.invoiceMergeWaybill.QueryForm');
    var waybillGrid = Ext.create('Foss.invoiceMergeWaybill.waybillGrid');
    Ext.create('Ext.panel.Panel', {
        id :'T_consumer-invoiceMergeWaybillAdd_content',
        cls:"panelContentNToolbar", //必须添加,内容定位用。
        bodyCls:'panelContentNToolbar-body', //必须添加,内容定位用。
        layout :'auto',
        getQueryForm : function() {
            return queryForm;
        },
        //获得查询结果GRID
        getWaybillGrid : function() {
            return waybillGrid;
        },
        items:[ queryForm,waybillGrid],
        renderTo :'T_consumer-invoiceMergeWaybillAdd-body'
    });
});