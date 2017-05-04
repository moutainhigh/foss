/**
 * @功能：“发票合并运单管理”的页面
 * @author:322906-foss-吴浩
 * @date;2016/5/28.
 */
consumer.invoiceMergeWaybillManage.longToDateConvert = function(value) {
    if (!Ext.isEmpty(value)) {
        //return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
        return Ext.Date.format(new Date(value), 'Y-m-d');
    } else {
        return null;
    }
}
Ext.define('Foss.invoiceMergeWaybill.waybillDetailModel', {
    extend :'Ext.data.Model',
    fields :[
        {
            name :'id',
            type :'string'
        }, {
            name :'billTime',//开单时间
            type :'date',
            convert:consumer.invoiceMergeWaybillManage.longToDateConvert
        }, {
            name :'waybillNo',  //运单号
            type :'string'
        }, {
            name :'product',  //业务类型
            type :'string'
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
Ext.define('Foss.invoiceMergeWaybill.waybillDetailStore',{
    extend : 'Ext.data.Store',
    model : 'Foss.invoiceMergeWaybill.waybillDetailModel'
});
//运单明细grid
Ext.define('Foss.invoiceMergeWaybill.waybillDetailGrid',{
    extend:'Ext.grid.Panel',
    title:consumer.invoiceMergeWaybillManage.i18n('foss.stl.consumer.invoiceMergeWaybill.waybillDetail'),//运单明细列表
    columnWidth:1,
    stripeRows:true,
    columnLines:true,
    collapsible:false,
    bodyCls:'autoHeight',
    emptyText: consumer.invoiceMergeWaybillManage.i18n('foss.stl.consumer.common.emptyText'),
    viewConfig:{
        enableTextSelection : true//设置行可以选择，进而可以复制
    },
    frame:true,//增加表格列的分割线
    cls:'autoHeight',
    store :Ext.create('Foss.invoiceMergeWaybill.waybillDetailStore'),
    autoScroll :true,
    height: 650,
    columns :[
        { xtype : 'rownumberer'},
        {
            text:consumer.invoiceMergeWaybillManage.i18n('foss.stl.consumer.invoiceMergeWaybill.billTime'), //开单日期
            dataIndex :'billTime',
            xtype: 'datecolumn',
            format:'Y-m-d'
        },
        {
            text:consumer.invoiceMergeWaybillManage.i18n('foss.stl.consumer.invoiceMergeWaybill.waybillNo'), //运单号
            dataIndex :'waybillNo'
        },
        {
            text:consumer.invoiceMergeWaybillManage.i18n('foss.stl.consumer.invoiceMergeWaybill.businessType'), //业务类型
            dataIndex :'product'
        },
        {
            text:consumer.invoiceMergeWaybillManage.i18n('foss.stl.consumer.invoiceMergeWaybill.invoiceMark'), //发票标记
            dataIndex :'invoiceMark'
        },
        {
            text:consumer.invoiceMergeWaybillManage.i18n('foss.stl.consumer.invoiceMergeWaybill.prePayAmount'), //预付金额
            dataIndex :'prePayAmount'
        },
        {
            text:consumer.invoiceMergeWaybillManage.i18n('foss.stl.consumer.invoiceMergeWaybill.toPayAmount'), //到付金额
            dataIndex :'toPayAmount'
        },
        {
            text:consumer.invoiceMergeWaybillManage.i18n('foss.stl.consumer.invoiceMergeWaybill.deliveryCustomerCode'), //发货方客户编码
            dataIndex :'deliveryCustomerCode'
        },
        {
            text:consumer.invoiceMergeWaybillManage.i18n('foss.stl.consumer.invoiceMergeWaybill.receiveCustomerCode'), //收货方客户编码
            dataIndex :'receiveCustomerCode'
        },
        {
            text:consumer.invoiceMergeWaybillManage.i18n('foss.stl.consumer.invoiceMergeWaybill.receiveOrgCode'), //收货部门编码
            dataIndex :'receiveOrgCode'
        },
        {
            text:consumer.invoiceMergeWaybillManage.i18n('foss.stl.consumer.invoiceMergeWaybill.receiveOrgName'), //收货部门名称
            dataIndex :'receiveOrgName'
        },
        {
            text:consumer.invoiceMergeWaybillManage.i18n('foss.stl.consumer.invoiceMergeWaybill.arriveOrgCode'), //到达部门编码
            dataIndex :'destOrgCode'
        },
        {
            text:consumer.invoiceMergeWaybillManage.i18n('foss.stl.consumer.invoiceMergeWaybill.arriveOrgName'), //到达部门名称
            dataIndex :'destOrgName'
        },
        {
            text:consumer.invoiceMergeWaybillManage.i18n('foss.stl.consumer.invoiceMergeWaybill.originalDunningOrgName'), //出发催款部门
            dataIndex :'origDunningOrgName'
        },
        {
            text:consumer.invoiceMergeWaybillManage.i18n('foss.stl.consumer.invoiceMergeWaybill.originalContractOrgName'), //出发合同部门
            dataIndex :'origContractOrgName'
        },
        {
            text:consumer.invoiceMergeWaybillManage.i18n('foss.stl.consumer.invoiceMergeWaybill.arriveDunningOrgName'), //到达催款部门
            dataIndex :'destDunningOrgName'
        },
        {
            text:consumer.invoiceMergeWaybillManage.i18n('foss.stl.consumer.invoiceMergeWaybill.arriveContractOrgName'), //到达合同名称
            dataIndex :'destContractOrgName'
        }

    ]
});

consumer.invoiceMergeWaybillManage.getTargetDate = function() {
    var   t2;
    Xmas95 = new Date();
    month = Xmas95.getMonth();

    t2 =  new Date();

    t2.setMonth(month-1);
    t2.setDate(1);
    t2.setHours(0);
    t2.setMinutes(0);
    t2.setSeconds(0);
    t2.setMilliseconds(0);

    return stl.dateFormat(t2, stl.FORMAT_TIME);
};
consumer.invoiceMergeWaybillManage.validateMergeWaybill = function(form,grid){
    var mergeWaybillNos = form.findField('mergeWaybillNos').getValue();
    var mergerWaybillArray = [];
    if(Ext.String.trim(mergeWaybillNos)!=null && Ext.String.trim(mergeWaybillNos)!=''){
        var array=[];
        if(-1!=mergeWaybillNos.indexOf("\n")){
            array = mergeWaybillNos.split("\n");
        }else{
            array = mergeWaybillNos.split(",");
        }
        for(var i=0;i<array.length;i++){
            if(Ext.String.trim(array[i])!=''){
                mergerWaybillArray.push(array[i]);
            }
        }
    }
    if(mergerWaybillArray.length>30){
        Ext.Msg.alert(consumer.invoiceMergeWaybillManage.i18n('foss.stl.consumer.common.alert'),
            consumer.invoiceMergeWaybillManage.i18n('foss.stl.consumer.invoiceMergeWaybill.mergeWaybillNosLimit'));
        return false;
    }
    var params = {'vo.mergeWaybillQueryDto.mergeWaybillNos':mergerWaybillArray};
    grid.store.setSubmitParams(params);
}
consumer.invoiceMergeWaybillManage.queryByMergeWaybill = function(form,me){
    var grid = Ext.getCmp('T_consumer-invoiceMergeWaybillManage_content').WaybillMergeResultGrid();
    if(form.isValid()){
        consumer.invoiceMergeWaybillManage.validateMergeWaybill(form,grid);
        me.disable(false);
        // 3秒后自动解除灰掉效果
        setTimeout(function() {
            me.enable(true);
        }, 3000);
        grid.store.load();
    }else {
        Ext.MessageBox.alert('提示信息','检查输入信息');
    }
};
consumer.invoiceMergeWaybillManage.validateWaybill =function(form,grid){
    var waybillNos = form.findField('waybillNos').getValue();
    var waybillArray = [];
    if(Ext.String.trim(waybillNos)!=null && Ext.String.trim(waybillNos)!=''){
        var array=[];
        if(-1!=waybillNos.indexOf("\n")){
            array = waybillNos.split("\n");
        }else{
            array = waybillNos.split(",");
        }
        for(var i=0;i<array.length;i++){
            if(Ext.String.trim(array[i])!=''){
                waybillArray.push(array[i]);
            }
        }
    }

    if(waybillArray.length>1000){
        Ext.Msg.alert(consumer.invoiceMergeWaybillManage.i18n('foss.stl.consumer.common.alert'),
            consumer.invoiceMergeWaybillManage.i18n('foss.stl.consumer.invoiceMergeWaybill.mergeWaybillNosLimit'));
        return false;
    }
    var params = {'vo.mergeWaybillQueryDto.waybillNos':waybillArray};

    grid.store.setSubmitParams(params);
};
consumer.invoiceMergeWaybillManage.queryByWaybill = function(form,me){
    var grid = Ext.getCmp('T_consumer-invoiceMergeWaybillManage_content').WaybillMergeResultGrid();
    if(form.isValid()){
        consumer.invoiceMergeWaybillManage.validateWaybill(form,grid);
        me.disable(false);
        // 3秒后自动解除灰掉效果
        setTimeout(function() {
            me.enable(true);
        }, 3000);
        grid.store.load();
    }else {
        Ext.MessageBox.alert('提示信息','检查输入信息');
    }
};
/**
 * 按时间查询
 * 设置查询参数
 * @param form
 * @param queryType
 * @returns {*}
 */
consumer.invoiceMergeWaybillManage.setParams = function(form) {

    var params = {};// 定义查询参数
    var customerCodes = form.findField('customerCodes').getValue();
    var startDate = form.findField('startDate').getValue();
    var endDate = form.findField('endDate').getValue();
    var invoiceTitle = form.findField('invoiceTitle').getValue();
    var taxId = form.findField('taxId').getValue();

    var customerCodesArray =[];

    if(Ext.String.trim(customerCodes)==null || Ext.String.trim(customerCodes)==''){
    }else{
        var reg = /[,\n]/;
        var array = customerCodes.split(reg);
        for (var i = 0; i < array.length; i++) {
            if (Ext.String.trim(array[i]) != '') {
                customerCodesArray.push(array[i]);
            }
        }
        if(customerCodesArray.length>5){
            Ext.Msg.alert(consumer.invoiceMergeWaybillManage.i18n('foss.stl.consumer.common.alert'),consumer.invoiceMergeWaybillManage.i18n('foss.stl.consumer.invoiceMergeWaybill.customerNosLimit'));
            return false;
        }
    }

    if (!startDate) {
        // 开始日期不能为空
        Ext.Msg.alert(consumer.invoiceMergeWaybillManage.i18n('foss.stl.consumer.common.warmTips'), consumer.invoiceMergeWaybillManage
            .i18n('foss.stl.consumer.invoiceMergeWaybill.createStartDateCannotBeNull'));
        return false;
    }
    if (!endDate) {
        // 结束日期不能为空
        Ext.Msg.alert(consumer.invoiceMergeWaybillManage.i18n('foss.stl.consumer.common.warmTips'), consumer.invoiceMergeWaybillManage
            .i18n('foss.stl.consumer.invoiceMergeWaybill.createEndDateCannotBeNull'));
        return false;
    }

    // 转化成日期对象
    startDate = Ext.Date.parse(startDate, "Y-m-d H:i:s", true);
    endDate = Ext.Date.parse(endDate, "Y-m-d H:i:s", true);
    params = {
        'vo.mergeWaybillQueryDto.customerCodes': customerCodesArray,
        'vo.mergeWaybillQueryDto.startDate' : startDate,
        'vo.mergeWaybillQueryDto.endDate' : endDate,
        'vo.mergeWaybillQueryDto.invoiceTitle' : invoiceTitle,
        'vo.mergeWaybillQueryDto.taxId' : taxId
    };

    return params;
};
/**
 * 按时间查询
 * @param f
 * @param me
 */
consumer.invoiceMergeWaybillManage.queryByTime = function(f,me){
    var form = f.getForm();
    var grid = Ext.getCmp('T_consumer-invoiceMergeWaybillManage_content').WaybillMergeResultGrid();
    if (form.isValid()) {
        var params = consumer.invoiceMergeWaybillManage.setParams(form);
        if (null == params) {
            return;
        }
        // 设置查询参数
        grid.store.setSubmitParams(params);
        // 设置该按钮灰掉
        me.disable(false);
        // 30秒后自动解除灰掉效果
        setTimeout(function() {
            me.enable(true);
        }, 5000);

        grid.store.load();
    } else {

        // 请检查输入条件是否合法
        Ext.Msg.alert(consumer.invoiceMergeWaybillManage.i18n('foss.stl.consumer.common.warmTips'), consumer.invoiceMergeWaybillManage
            .i18n('foss.stl.consumer.invoiceMergeWaybill.pleaseCheckConditionLegal'));
    }
};


/**
 * 根据客户编码查询客户信息
 */
consumer.invoiceMergeWaybillManage.queryCustomerInfoByCodes = function(customerCodes, form) {
    if(customerCodes!=null && customerCodes!=''){
        var codeArray = [];
        if(Ext.String.trim(customerCodes)==null || Ext.String.trim(customerCodes)==''){
            Ext.Msg.alert(consumer.invoiceMergeWaybillManage.i18n('foss.stl.consumer.common.alert'),consumer.invoiceMergeWaybillManage.i18n('foss.stl.consumer.invoiceMergeWaybill.customerCodeNotNull'));
            return false;
        }else{
            var array = stl.splitToArray(customerCodes);
            for(var i=0;i<array.length;i++){
                if(Ext.String.trim(array[i])!=''){
                    codeArray.push(array[i]);
                }
            }
            if(codeArray.length>5){
                Ext.Msg.alert(consumer.invoiceMergeWaybillManage.i18n('foss.stl.consumer.common.alert'),consumer.invoiceMergeWaybillManage.i18n('foss.stl.consumer.invoiceMergeWaybill.customerNosLimit'));
                return false;
            }
        }

        Ext.Ajax.request({
            url : consumer.realPath('queryCustomerInfoFromCrm.action'),
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
            //exception : function(response) {
            //    var result = Ext.decode(response.responseText);
            //    Ext.ux.Toast.msg(consumer.invoiceMergeWaybillManage.i18n('foss.stl.consumer.common.warmTips'), result.message);
            //}
            error:function(response){
                var result = Ext.decode(response.responseText);
                Ext.Msg.alert(consumer.invoiceMergeWaybillManage.i18n('foss.stl.consumer.common.warmTips'), result.message);
            },
            exception : function(response) {
                var result = Ext.decode(response.responseText);
                Ext.Msg.alert(consumer.invoiceMergeWaybillManage.i18n('foss.stl.consumer.common.warmTips'), result.message);
            }
        });
    }

}
/**
 * 查询tab
 */
Ext.define('Foss.invoiceMergeWaybill.WaybillMergeQueryTab',{
    extend:'Ext.tab.Panel',
    frame:false,
    bodyCls: 'autoHeight',
    cls: 'innerTabPanel',
    activeTab:0,//默认显示按单号制作
    height:240,
    items: [ {
        title: consumer.invoiceMergeWaybillManage.i18n('foss.stl.consumer.invoiceMergeWaybill.queryByTime'),//按时间查询
        tabConfig : {
            width : 120
        },
        items : [{
            xtype : 'form',
            id : 'Foss_Consumer_invoiceMergeWaybillManage_QueryByTime_ID',
            defaults : {
                labelWidth : 75,
                padding:5
            },
            layout : 'column',
            items : [{
                xtype : 'datetimefield_date97',
                fieldLabel : consumer.invoiceMergeWaybillManage.i18n('foss.stl.consumer.invoiceMergeWaybill.businessDate'),//业务日期
                id : 'FOSS_Consumer_invoiceMergeWaybillManage_StartDate_ID',
                //time : true,
                labelWidth : 75,
                columnWidth : .3,
                name : 'startDate',
                editable : 'false',
                value: consumer.invoiceMergeWaybillManage.getTargetDate(),
                dateConfig : {
                    el : 'FOSS_Consumer_invoiceMergeWaybillManage_StartDate_ID-inputEl',
                    dateFmt : 'yyyy-MM-dd HH:mi:ss'
                }
            }, {
                xtype : 'datetimefield_date97',
                fieldLabel : consumer.invoiceMergeWaybillManage.i18n('foss.stl.consumer.invoiceMergeWaybill.to'),//至（结束日期）
                id : 'FOSS_Consumer_invoiceMergeWaybillManage_EndDate_ID',
                //time : true,
                name : 'endDate',
                columnWidth : .3,
                allowBlank : false,
                labelWidth : 35,
                editable : 'false',
                value : stl.dateFormat(new Date(), stl.FORMAT_DATE) + stl.END_PREFIX,
                dateConfig : {
                    el : 'FOSS_Consumer_invoiceMergeWaybillManage_EndDate_ID-inputEl',
                    dateFmt : 'yyyy-MM-dd HH:mi:ss',
                    maxDate : stl.dateFormat(new Date(), stl.FORMAT_DATE) + stl.END_PREFIX
                }
            },{
                xtype: 'textareafield',
                name: 'customerCodes',
                fieldLabel: consumer.invoiceMergeWaybillManage.i18n('foss.stl.consumer.invoiceMergeWaybill.customerCode'), // 客户编码
                emptyText : consumer.invoiceMergeWaybillManage.i18n('foss.stl.consumer.invoiceMergeWaybill.customerCodeNotice'),//请输入客户编码,以“,”分隔，最多5个
                //allowBlank: false,
                //regex:/^([A-Za-z0-9\\-]+[,])*([A-Za-z0-9\\-]+[,]?)$/i,
                regexText:'请输入1-5个客户编码，以,符号隔开',
                maxLength:1000,//长度最大1000
                height:80,
                columnWidth : .3,
                listeners : {
                    blur:function(_this, the, eOpts ){
                        var customerCode = _this.value;
                        var form = this.up('form');
                        consumer.invoiceMergeWaybillManage.queryCustomerInfoByCodes(customerCode, form);
                    },
                    change:function(_this,newValue,oldValue,eOpts){
                        var form = this.up('form');
                        form.getForm().findField('invoiceTitle').value='';
                        form.getForm().findField('taxId').value='';
                    }

                }
            },{xtype: 'textfield',
                name: 'invoiceTitle',
                fieldLabel: consumer.invoiceMergeWaybillManage.i18n('foss.stl.consumer.invoiceMergeWaybill.invoiceTitle'),// 发票抬头
                //allowBlank: false,
                readOnly:true,
                columnWidth:.3,
                cls: 'readonlyhaveborder'
            }, {xtype: 'textfield',
                name: 'taxId',
                fieldLabel: consumer.invoiceMergeWaybillManage.i18n('foss.stl.consumer.invoiceMergeWaybill.taxId'),// 税务登记号
                //allowBlank: false,
                readOnly:true,
                columnWidth:.3,
                cls: 'readonlyhaveborder'
            }, {
                xtype:'container',
                border:false,
                html:'&nbsp;',
                columnWidth:.33
            }, {
                border : 1,
                xtype : 'container',
                columnWidth : 1,
                defaultType : 'button',
                layout : 'column',
                items:[{
                    text:consumer.invoiceMergeWaybillManage.i18n('foss.stl.consumer.common.reset'),
                    columnWidth : .1,
                    handler:function(){
                        this.up('form').getForm().reset();
                    }
                },{
                    xtype : 'container',
                    border : false,
                    columnWidth : .6,
                    html : '&nbsp;'
                }, {
                    text:consumer.invoiceMergeWaybillManage.i18n('foss.stl.consumer.common.query'),
                    cls : 'yellow_button',
                    columnWidth : .1,
                    handler:function(_this){
                        var form=_this.up('form');
                        consumer.invoiceMergeWaybillManage.queryByTime(form,_this)
                    }
                }
                ]
            }]
        }]
    }, {
        title: consumer.invoiceMergeWaybillManage.i18n('foss.stl.consumer.invoiceMergeWaybill.queryByWaybillNo'),//按运单查询
        tabConfig:{
            width:120
        },
        layout:'fit',
        items:[{
            xtype:'form',
            id : 'Foss_Consumer_invoiceMergeWaybillManage_QueryByWaybill_ID',
            defaults:{
                margin:'5,5,5,5'
            },
            layout:'column',
            items:[{
                xtype:'textareafield',
                fieldLabel:consumer.invoiceMergeWaybillManage.i18n('foss.stl.consumer.invoiceMergeWaybill.waybillNo'),//运单号
                columnWidth:.65,
                //regex:/^((\s)*[A-Za-z0-9]{8,20}[,]?)*((\s)*[A-Za-z0-9]{8,20}[,]?)(\s)*$/i,
                regexText:consumer.invoiceMergeWaybillManage.i18n('foss.stl.consumer.invoiceMergeWaybill.billNosQueryRegexText'),
                labelWidth:70,
                labelAlign:'right',
                name:'waybillNos',
                autoScroll:true,
                height:140,
                allowBlank:false
            },{
                xtype:'container',
                columnWidth:1,
                layout:'column',
                defaultType:'button',
                defaults:{
                    width:80
                },
                items:[{
                    text:consumer.invoiceMergeWaybillManage.i18n('foss.stl.consumer.common.reset'),
                    columnWidth:.075,
                    handler:function() {
                        this.up('form').getForm().reset();
                    }
                },{
                    text:consumer.invoiceMergeWaybillManage.i18n('foss.stl.consumer.common.query'),
                    cls:'yellow_button',
                    columnWidth:.075,
                    handler:function(_this){
                        var form = this.up('form').getForm();
                        consumer.invoiceMergeWaybillManage.queryByWaybill(form,_this)//按运单查询操作
                    }
                }]
            }]
        }]
    }, {
        title: consumer.invoiceMergeWaybillManage.i18n('foss.stl.consumer.invoiceMergeWaybill.queryByMergeWaybillNo'),//按合并运单查询
        tabConfig:{
            width:130
        },
        layout:'fit',
        items:[{
            xtype:'form',
            id : 'Foss_Consumer_invoiceMergeWaybillManage_QueryByMergeWaybill_ID',
            defaults:{
                margin:'5,5,5,5'
            },
            layout:'column',
            items:[{
                xtype:'textareafield',
                fieldLabel:consumer.invoiceMergeWaybillManage.i18n('foss.stl.consumer.invoiceMergeWaybill.mergeWaybillNo'),//合并运单号
                columnWidth:.65,
                //regex:/^((HB)[0-9]{9}[,])*((HB)[0-9]{9}[,]?)$/i,//合并运单单号规则
                regexText:consumer.invoiceMergeWaybillManage.i18n('foss.stl.consumer.invoiceMergeWaybill.billNosQueryRegexText'),
                labelWidth:82,
                labelAlign:'right',
                name:'mergeWaybillNos',
                autoScroll:true,
                height:140,
                allowBlank:false
            },{
                xtype:'container',
                columnWidth:1,
                layout:'column',
                defaultType:'button',
                defaults:{
                    width:80
                },
                items:[{
                    text:consumer.invoiceMergeWaybillManage.i18n('foss.stl.consumer.common.reset'),
                    columnWidth:.075,
                    handler:function() {
                        // 重置
                        this.up('form').getForm().reset();
                    }
                },{
                    text:consumer.invoiceMergeWaybillManage.i18n('foss.stl.consumer.common.query'),
                    cls:'yellow_button',
                    columnWidth:.075,
                    handler:function(_this){
                        var form = this.up('form').getForm();
                        consumer.invoiceMergeWaybillManage.queryByMergeWaybill(form,_this);//按合并运单查询
                    }
                }]
            }]
        }]
    }
    ]
});



/**
 * 合并运单model
 */
Ext.define('Foss.invoiceMergeWaybillManage.MergeWaybillModel', {
    extend: 'Ext.data.Model',
    fields: [{
        name: 'mergeWaybillNo'//合并运单号
    }, {
        name: 'product'//业务类型
    }, {
        name: 'invoiceMark',//发票标记
        type :'string',
        convert:function(value) {
            if (value == 'INVOICE_TYPE_01') {
                return '01';
            } else {
                return '02';
            }
        }
    }, {
        name: 'prePayAmount',//预付金额
        type:'long'
    },{
        name: 'toPayAmount',//到付金额
        type:'long'
    },{
        name: 'deliveryCustomerCode'//发货方客户编码
    },{
        name: 'receiveCustomerCode'//收货方客户编码
    },{
        name: 'receiveOrgCode'//收货部门编码
    },{
        name: 'descOrgCode'//到达部门编码
    },{
        name: 'billTime',//开单日期
        type :'date',
        convert:stl.longToDateConvert
    },{
        name: 'transferLine'//运输路线
    },{
        name: 'receiveDunningDeptName'//出发催款部门
    }, {
        name: 'receiveContractDeptName'//出发合同部门
    },{
        name: 'descDunningDeptName'//到达催款部门
    },{
        name: 'descContractDeptName'//到达合同部门
    },{
        name: 'invoiceHeadCode'//发票抬头
    },{
        name: 'taxId'//税务登记号
    },{
        name: 'invoiceCreateDate',//开票时间
        type :'date',
        convert:function(value) {
            if (value != null) {
                var date = new Date(value);
                return date;
            } else {
                return null;
            }
        }
    }
    ]
});

/**
 * 合并运单store
 */
Ext.define('Foss.invoiceMergeWaybillManage.MergeWaybillQueryGridStore', {
    extend:'Ext.data.Store',
    model:'Foss.invoiceMergeWaybillManage.MergeWaybillModel',
    pageSize:50,
    proxy : {
        type : 'ajax',
        actionMethods : 'post',
        url : consumer.realPath('queryMergeWaybillByConditions.action'),
        reader : {
            type : 'json',
            root : 'vo.mergeWaybillDtoList',
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
                var grid = Ext.getCmp('T_consumer-invoiceMergeWaybillManage_content').WaybillMergeResultGrid();
                //var tab =Ext.getCmp('T_consumer-invoiceMergeWaybillManage_content').WaybillMergeQuery().getActiveTab().title;
                var tab =Ext.getCmp('T_consumer-invoiceMergeWaybillManage_content').WaybillMergeQuery().getActiveTab().items.keys[0];
                if(tab=='Foss_Consumer_invoiceMergeWaybillManage_QueryByTime_ID'){
                    var form =Ext.getCmp('Foss_Consumer_invoiceMergeWaybillManage_QueryByTime_ID').getForm();
                    var params = consumer.invoiceMergeWaybillManage.setParams(form);
                    grid.store.setSubmitParams(params);
                }else if(tab=='Foss_Consumer_invoiceMergeWaybillManage_QueryByWaybill_ID'){
                    var form =Ext.getCmp('Foss_Consumer_invoiceMergeWaybillManage_QueryByWaybill_ID').getForm();
                    var waybillNos = form.findField("waybillNos").getValue();
                    if(waybillNos==''){
                        Ext.ux.Toast.msg(consumer.invoiceMergeWaybillManage.i18n('foss.stl.consumer.common.warmTips'), '请填入运单号，再操作'
                            ,'ok',1800);
                        return false;
                    }
                    consumer.invoiceMergeWaybillManage.validateWaybill(form,grid);
                }else{
                    var form =Ext.getCmp('Foss_Consumer_invoiceMergeWaybillManage_QueryByMergeWaybill_ID').getForm();
                    var mergeWaybillNos = form.findField("mergeWaybillNos").getValue();
                    if(mergeWaybillNos==''){
                        Ext.ux.Toast.msg(consumer.invoiceMergeWaybillManage.i18n('foss.stl.consumer.common.warmTips'), '请填入合并运单号，再操作'
                            ,'ok',1800);
                        return false;
                    }
                    consumer.invoiceMergeWaybillManage.validateMergeWaybill(form,grid);
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
var SoperateColumnEditing = Ext.create('Ext.grid.plugin.CellEditing', {
    clicksToEdit : 1
});
//编辑器
var SoperateColumnEditing = Ext.create('Ext.grid.plugin.CellEditing', {
    clicksToEdit : 1,
    isObservable : false
}) ;
//是否进行查看明细操作
consumer.invoiceMergeWaybillManage.DetailConfirmAlert = function(message,yesFn,noFn){
    Ext.Msg.confirm(consumer.invoiceMergeWaybillManage.i18n('foss.stl.consumer.common.alert'),message,function(o){
        if(o=='yes'){
            yesFn();
        }else{
            noFn();
        }
    });
};
/**
 * 查看合并运单明细
 */
consumer.invoiceMergeWaybillManage.viewMergeWaybillDetail = function(){

    var selection=Ext.getCmp('Foss_invoiceMergeWaybillManage_MergeWaybillGrid_ID').getSelectionModel().getSelection()[0];
    var mergeWaybillNo = selection.get('mergeWaybillNo');//合并运单号

    var yesFn=function(){
        Ext.Ajax.request({
            url:consumer.realPath('queryWaybillDetailByMergeWaybillNo.action'),
            params:{
                'vo.mergeWaybillNo':mergeWaybillNo
            },
            success:function(response){
                var result = Ext.decode(response.responseText);

                var win =Ext.getCmp('Foss_consumer_waybill_DetailWindow_ID'); //运单明细窗口
                if(win==null){
                    win = Ext.create('Foss_consumer_waybill_DetailWindow',{
                        id:'Foss_consumer_waybill_DetailWindow_ID'
                    });
                }
                var waybillDetailGrid = win.waybillDetailsGrid;//运单明细grid
                var waybillDetailEntityList = result.vo.waybillDetailEntityList;//从结果集中  获运单明细数据
                waybillDetailGrid.store.loadData(waybillDetailEntityList);
                win.show();
            },
            exception:function(response){
                var result = Ext.decode(response.responseText);
                Ext.Msg.alert(consumer.invoiceMergeWaybillManage.i18n('foss.stl.consumer.common.alert'),result.message);
            }
        });
    };
    var noFn=function(){
        return false;
    };
    //确认按钮/ 取消按钮
    consumer.invoiceMergeWaybillManage.DetailConfirmAlert(consumer.invoiceMergeWaybillManage.i18n('foss.stl.consumer.invoiceMergeWaybill.isWatchEntry'),yesFn,noFn);//是否进行查看明细操作
};
/**
 * 作废合并运单
 */
consumer.invoiceMergeWaybillManage.cancelMergeWaybill = function(){
    var selection=Ext.getCmp('Foss_invoiceMergeWaybillManage_MergeWaybillGrid_ID').getSelectionModel().getSelection()[0];
    var mergeWaybillNo = selection.get('mergeWaybillNo');//合并运单号
    Ext.Msg.confirm( consumer.invoiceMergeWaybillManage.i18n('foss.stl.consumer.common.warmTips'),
        consumer.invoiceMergeWaybillManage.i18n('foss.stl.consumer.invoiceMergeWaybill.confirm2cancel'), //确定要作废吗？
        function(btn,text){
            if(btn == 'yes'){
                Ext.Ajax.request({
                    url:consumer.realPath('cancelMergeWaybill.action'),
                    params:{'vo.mergeWaybillNo':mergeWaybillNo},
                    method:'post',
                    success:function(response){
                        var result = Ext.decode(response.responseText);
                        Ext.Msg.alert(consumer.invoiceMergeWaybillManage.i18n('foss.stl.consumer.common.warmTips'),result.message);
                        var mergeWaybillGrid = Ext.getCmp("Foss_invoiceMergeWaybillManage_MergeWaybillGrid_ID");
                        mergeWaybillGrid.store.load();
                    },
                    exception:function(response){
                        var result = Ext.decode(response.responseText);
                        Ext.Msg.alert(consumer.invoiceMergeWaybillManage.i18n('foss.stl.consumer.common.warmTips'),result.message);
                    },
                    failure:function(response){
                        var result = Ext.decode(response.responseText);
                        Ext.Msg.alert(consumer.invoiceMergeWaybillManage.i18n('foss.stl.consumer.common.warmTips'),result.message);
                    }
                });
            }
        });
};
Ext.define('Foss.invoiceMergeWaybill.queryComboModel',{
    extend:'Ext.data.Model',
    fields:[{
        name:'name'
    },{
        name:'value'
    }]
});

Ext.define('Foss.invoiceMergeWaybill.operateColumnStore',{
    extened:'Ext.data.Store',
    model:'Foss.invoiceMergeWaybill.queryComboModel',
    data:{
        'items':[
            {name:consumer.invoiceMergeWaybillManage.i18n('foss.stl.consumer.invoiceMergeWaybill.lookOver'),value:'1'},
            {name:consumer.invoiceMergeWaybillManage.i18n('foss.stl.consumer.invoiceMergeWaybill.cancel'),value:'2'}
        ]
    },
    proxy:{
        type:'memory',
        reader:{
            type:'json',
            root:'items'
        }
    }
});


//操作列下拉框
Ext.define('Foss.invoiceMergeWaybill.operateColumn', {
    extend:'Ext.form.field.ComboBox',
    typeAhead: true,
    emptyText:'操作列',
    triggerAction: 'all',
    queryMode: 'local',
    store: Ext.create('Foss.invoiceMergeWaybill.operateColumnStore'),
    valueField: 'value',
    displayField: 'name',
    forceSelection :true,
    listeners:{
        'change':function(combo){
            if(Ext.isEmpty(combo.getValue())){
                combo.setValue("");
            }
        },
        'select':function(combo){
            if(combo.value=='1') {
                //明细
                consumer.invoiceMergeWaybillManage.viewMergeWaybillDetail();
            }
            if(combo.value=='2') {
                //作废
                consumer.invoiceMergeWaybillManage.cancelMergeWaybill();
            }
        }
    }
});
var operateColumn = Ext.create('Foss.invoiceMergeWaybill.operateColumn');
/**
 * 合并运单列表
 */
Ext.define('Foss.invoiceMergeWaybill.WaybillMergeResultGrid', {
    id:'Foss_invoiceMergeWaybillManage_MergeWaybillGrid_ID',
    extend:'Ext.grid.Panel',
    title: consumer.invoiceMergeWaybillManage.i18n('foss.stl.consumer.invoiceMergeWaybill.mergeWaybillInfo'),//合并运单信息
    emptyText: consumer.invoiceMergeWaybillManage.i18n('foss.stl.consumer.common.emptyText'),
    frame:true,
    height:600,
    plugins:SoperateColumnEditing,
    selModel:Ext.create('Ext.selection.CheckboxModel'),
    //store: Ext.create('Foss.invoiceMergeWaybillManage.MergeWaybillQueryGridStore'),
    store: null,
    viewConfig:{
        enableTextSelection : true//设置行可以选择，进而可以复制
    },
    pagingToolbar:null,//分页
    getPagingToolbar:function(){
        var me = this;
        if(Ext.isEmpty(me.pagingToolbar)){
            me.pagingToolbar = Ext.create('Deppon.StandardPaging',{
                store:me.store,
                pageSize:20,
                maximumSize:1000,
                plugins : Ext.create('Deppon.ux.PageSizePlugin')
            });
        }
        return me.pagingToolbar;
    },
    //定义表格列信息
    columns: [{
        header:consumer.invoiceMergeWaybillManage.i18n('foss.stl.consumer.invoiceMergeWaybill.actionColumn'),//操作列,
        editor:operateColumn,
        renderer: function(value){
            var record = operateColumn.findRecord(operateColumn.valueField, value);
            return record ? record.get(operateColumn.displayField): operateColumn.valueNotFoundText;
        }

    },{
        header: consumer.invoiceMergeWaybillManage.i18n('foss.stl.consumer.invoiceMergeWaybill.mergeWaybillNo'),//合并运单号
        dataIndex: 'mergeWaybillNo'
    },{
        header: consumer.invoiceMergeWaybillManage.i18n('foss.stl.consumer.invoiceMergeWaybill.businessType'),//业务类型
        dataIndex: 'product',
        renderer:function(value){
            if(value==01){
                return '快递';
            }
            if(value==02){
                return '零担';
            }
        }
    },{
        header: consumer.invoiceMergeWaybillManage.i18n('foss.stl.consumer.invoiceMergeWaybill.invoiceMark'),//发票标记
        dataIndex: 'invoiceMark'
    },{
        header: consumer.invoiceMergeWaybillManage.i18n('foss.stl.consumer.invoiceMergeWaybill.prePayAmount'),//预付金额
        dataIndex: 'prePayAmount'
    },{
        header: consumer.invoiceMergeWaybillManage.i18n('foss.stl.consumer.invoiceMergeWaybill.toPayAmount'),//到付金额
        dataIndex: 'toPayAmount'
    },{
        header: consumer.invoiceMergeWaybillManage.i18n('foss.stl.consumer.invoiceMergeWaybill.deliveryCustomerCode'),//发货方客户编码
        dataIndex: 'deliveryCustomerCode'
    },{
        header: consumer.invoiceMergeWaybillManage.i18n('foss.stl.consumer.invoiceMergeWaybill.receiveCustomerCode'),//收货方客户编码
        dataIndex: 'receiveCustomerCode'
    },{
        header: consumer.invoiceMergeWaybillManage.i18n('foss.stl.consumer.invoiceMergeWaybill.receiveOrgCode'),//收货部门编码
        dataIndex: 'receiveOrgCode'
    },{
        header: consumer.invoiceMergeWaybillManage.i18n('foss.stl.consumer.invoiceMergeWaybill.arriveOrgCode'),//到达部门编码
        dataIndex: 'descOrgCode'
    },{
        header: consumer.invoiceMergeWaybillManage.i18n('foss.stl.consumer.invoiceMergeWaybill.billTime'),//开单日期
        dataIndex: 'billTime',
        xtype: 'datecolumn',
        format:'Y-m-d'
    },{
        header: consumer.invoiceMergeWaybillManage.i18n('foss.stl.consumer.invoiceMergeWaybill.transferLine'),//运输路线
        dataIndex: 'transferLine'
    },{
        header: consumer.invoiceMergeWaybillManage.i18n('foss.stl.consumer.invoiceMergeWaybill.originalDunningOrgName'),//出发催款部门
        dataIndex: 'receiveDunningDeptName'
    },{
        header: consumer.invoiceMergeWaybillManage.i18n('foss.stl.consumer.invoiceMergeWaybill.originalContractOrgName'),//出发合同部门
        dataIndex: 'receiveContractDeptName'
    },{
        header: consumer.invoiceMergeWaybillManage.i18n('foss.stl.consumer.invoiceMergeWaybill.arriveDunningOrgName'),//到达催款部门
        dataIndex: 'descDunningDeptName'
    },{
        header: consumer.invoiceMergeWaybillManage.i18n('foss.stl.consumer.invoiceMergeWaybill.arriveContractOrgName'),//到达合同部门
        dataIndex: 'descContractDeptName'
    },{
        header: consumer.invoiceMergeWaybillManage.i18n('foss.stl.consumer.invoiceMergeWaybill.invoiceTitle'),//发票抬头
        dataIndex: 'invoiceHeadCode'
    },{
        header: consumer.invoiceMergeWaybillManage.i18n('foss.stl.consumer.invoiceMergeWaybill.taxId'),//税务登记号
        dataIndex: 'taxId'
    },{
        header: consumer.invoiceMergeWaybillManage.i18n('foss.stl.consumer.invoiceMergeWaybill.invoiceTime'),//开票时间
        dataIndex: 'invoiceCreateDate',
        renderer:function(value){
            if(value!=null){
                return Ext.Date.format(new Date(value), 'Y-m-d');
            }else{
                return null;
            }
        }
    }
    ],
    constructor:function(config){
        var me = this, cfg = Ext.apply({}, config);
        me.store = Ext.create('Foss.invoiceMergeWaybillManage.MergeWaybillQueryGridStore');
        me.bbar = me.getPagingToolbar();
        me.getPagingToolbar().store = me.store;
        me.callParent([ cfg ]);
    }
});

//运单明细窗口
Ext.define('Foss_consumer_waybill_DetailWindow', {
    extend:'Ext.window.Window',
    id:'Foss_consumer_waybill_DetailWindow_ID',
    width:stl.SCREENWIDTH*0.9,
    modal:true,
    constrainHeader: true,
    closeAction:'destroy',
    columnWidth:1,
    waybillDetailsGrid:null,
    getWaybillDetailsGrid:function(){
        if(Ext.isEmpty(this.waybillDetailsGrid)){
            this.waybillDetailsGrid=Ext.create('Foss.invoiceMergeWaybill.waybillDetailGrid');
        }
        return this.waybillDetailsGrid;
    },
    constructor: function(config){
        var me = this,
            cfg = Ext.apply({}, config);
        me.items = [me.getWaybillDetailsGrid()];
        me.callParent([cfg]);
    }
});


Ext.onReady(function() {
    Ext.QuickTips.init();
    Ext.form.Field.prototype.msgTarget='side'; //提示显示风格

    var WaybillMergeQueryTab = Ext.create('Foss.invoiceMergeWaybill.WaybillMergeQueryTab');
    var WaybillMergeResultGrid = Ext.create('Foss.invoiceMergeWaybill.WaybillMergeResultGrid');
    Ext.create('Ext.panel.Panel',{
        id: 'T_consumer-invoiceMergeWaybillManage_content',
        cls: "panelContentNToolbar",
        bodyCls: 'panelContentNToolbar-body',
        layout: 'auto',
        WaybillMergeQuery:function(){
            return WaybillMergeQueryTab;
        },
        WaybillMergeResultGrid:function(){
            return WaybillMergeResultGrid;
        },
        items: [WaybillMergeQueryTab,WaybillMergeResultGrid],
        renderTo: 'T_consumer-invoiceMergeWaybillManage-body'
    });
});
