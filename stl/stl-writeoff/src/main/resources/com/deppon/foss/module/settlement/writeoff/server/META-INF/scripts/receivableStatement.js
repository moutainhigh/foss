/**
 * Created by 306698 on 2016/1/19.
 */
//付款方式的转换
writeoff.receivableStatement.paymentType=function(val){
    switch (val) {
        case 'CT':
            return '月结';
        case "CH":
            return '现金';
        case "CD":
            return '银行卡';
        case "TT":
            return '电汇';
        case "NT":
            return '支票';
        case "OL":
            return '网上支付';
        case "DT":
            return '临时欠款';
        case "FC":
            return '到付';
    }
};

//产品类型转换(运输性质)
writeoff.receivableStatement.productCode=function(val){
    switch (val) {
        case 'AF':
            return '精准空运';
        case "FLF":
            return '精准卡航';
        case "FSF":
            return '精准城运';
        case "LRF":
            return '精准汽运(长途)';
        case 'PLF':
            return '汽运偏线';
        case "SRF":
            return '精准汽运(短途)';
        case "WVH":
            return '整车（三级）';
        case "PACKAGE":
            return '标准快递';
        case 'RCP':
            return '3.60特惠件';
        case "BGFLF":
            return '精准大票卡航';
        case "BGLRF":
            return '精准大票汽运(长)';
        case "BGFSF":
            return '精准大票城运';
        case 'BGSRF':
            return '精准大票汽运(短)';
        case "DTD":
            return '精准大票.经济件';
        case "YTY":
            return '精准大票.标准件';
        case "EPEP":
            return '电商尊享';
        case "DEAP":
            return '商务专递';
    }
};

//合伙人单据子类型
writeoff.receivableStatement.billType=function(val){
    switch (val) {
        case  "CR":
            return '代收货款应收';
        case  "POR":
            return "合伙人始发提成应收";
        case  "PFCR":
            return "合伙人到付运费应收";
        case  "PODR":
            return "合伙人始发提成和委托派费应收";
        case  "PDFR":
            return "合伙人委托派费应收";
        case  "PFCCR":
            return "合伙人部分到付部分现付运费应收";
        case  "PP":
            return "合伙人罚款应收";
        case  "PTF":
            return "合伙人培训会务应收";
        case  "PER":
            return "合伙人差错应收";
    }
};

//提货方式
writeoff.receivableStatement.receiveMethod=function(val){
    switch(val){
        case "SELF_PICKUP":
            return "自提";
        case "DELIVER_INGA":
            return "送货进仓";
        case "DELIVER_NOUP":
            return "送货(不含上楼)";
        case "DELIVER_FLOOR":
            return "送货上楼安装（家居)";
        case "LARGE_DELIVER_UP":
            return "大件上楼";
        case "DELIVER":
            return "免费送货";
        case "DELIVER_UP":
            return "送货上楼";
        case "INNER_PICKUP":
            return "内部带货自提";
    }
};



//定义按合伙人制作查询的form
Ext.define('Foss.writeoff.receivableStatement.producedByPartnerForm',{
    extend:'Ext.form.Panel',
    frame:false,
    defaults:{
        margin :'5 5 5 0',
        labelWidth :100
    },
    defaultType:'textfield',
    layout:{
        type :'table',
        columns :3
    },
    items:[
        {
            xtype:'linkagecomboselector',
            eventType : ['focus'],// 一般callparent包含focus事件，如果有callparent,则focus事件可不用传递
            itemId : 'Foss_baseinfo_BigRegion_ID',
            store : Ext.create('Foss.baseinfo.commonSelector.BigRegionStore'),// 从外面传入
            columnWidth:.3,
            fieldLabel :writeoff.receivableStatement.i18n('foss.stl.writeoff.customersNotReconciled.largeRegion'),               //大区
            name : 'largeRegion',
            isPaging: true,
            allowBlank : true,
            value:'',
            minChars : 0,
            displayField : 'name',// 显示名称
            valueField : 'code',
            minChars : 0,
            queryParam : 'commonOrgVo.name'
        },
        {
            xtype:'linkagecomboselector',
            itemId : 'Foss_baseinfo_SmallRegion_ID',
            eventType : ['callparent'],// 一般callparent包含focus事件，如果有callparent,则focus事件可不用传递
            store : Ext.create('Foss.baseinfo.commonSelector.SmallRegionStore'),// 从外面传入
            columnWidth:.3,
            fieldLabel : writeoff.receivableStatement.i18n('foss.stl.writeoff.customersNotReconciled.smallRegion'),//小区
            name : 'smallRegion',
            allowBlank : true,
            isPaging: true,
            parentParamsAndItemIds : {
                'commonOrgVo.code' : 'Foss_baseinfo_BigRegion_ID'
            },// 此处城市不需要传入
            minChars : 0,
            displayField : 'name',// 显示名称
            valueField : 'code',
            minChars : 0,
            queryParam : 'commonOrgVo.name'
        },
        {
            xtype : 'dynamicorgcombselector',
            fieldLabel :  writeoff.receivableStatement.i18n('foss.stl.writeoff.common.statementOrgName'), //部门
            name : 'createOrgCode',
            value : stl.currentDept.name,// 显示名称
            columnWidth:.3,
            labelWidth : 85,
            listWidth : 300,// 设置下拉框宽度
            allowBlank : true,
            isPaging : true
        },
        {
            xtype:'datefield',
            fieldLabel:writeoff.receivableStatement.i18n('foss.stl.writeoff.airPaymentPayable.startDate'),  //开始日期
            allowBlank:false,
            name:'periodBeginDate',
            format:'Y-m-d',
            value:stl.getTargetDate(stl.getLastMonthLastDay(new Date()),+1)
        },{
            xtype:'datefield',
            fieldLabel:writeoff.receivableStatement.i18n('foss.stl.writeoff.airPaymentPayable.endDate'),      //结束日期
            name:'periodEndDate',
            format:'Y-m-d',
            value:stl.getTargetDate(new Date(),+1)
        },
        {
            xtype: 'commonsaledepartmentselector',
            name:'customerCode',
            fieldLabel:writeoff.receivableStatement.i18n('foss.stl.writeoff.partnerPayStatementEdit.partnerName'),       //合伙人名称
            allowBlank: true,
            listWidth:300,//设置下拉框宽度
            isPaging:true //分页
        },
        {
            xtype : 'combobox',
            name : 'statementStatus',
            fieldLabel :writeoff.receivableStatement.i18n('foss.stl.writeoff.statementEdit.statementStatus'),//对账单状态
             store:Ext.create('Ext.data.Store', {
                 fields: ['valueName', 'valueCode'],
                 data : [
                     {"valueName":"全部", "valueCode":""},
                     {"valueName":"已确认", "valueCode":"Y"},
                     {"valueName":"未确认", "valueCode":"N"}
                 ]
             }),
            queryModel : 'local',
            displayField : 'valueName',
            valueField : 'valueCode',
            editable:false,
            value:''
        },
        {
            xtype : 'combobox',
            name : 'settleStatus',
            fieldLabel : writeoff.receivableStatement.i18n('foss.stl.writeoff.statementEdit.settleStatus'),//结账状态
             store:Ext.create('Ext.data.Store', {
                 fields: ['valueName', 'valueCode'],
                 data : [
                     {"valueName":"全部", "valueCode":""},
                     {"valueName":"已结清", "valueCode":"statementSettleStatus"},
                     {"valueName":"未结清", "valueCode":"statementUnSettleStatus"}
                 ]
             }),
            queryModel : 'local',
            displayField : 'valueName',
            valueField : 'valueCode',
            editable:false,
            value:''
        },
        {
            xtype:'container'
        },
        {
            xtype:'button',
            align:'center',
            width:80,
            text: writeoff.receivableStatement.i18n('foss.stl.writeoff.common.reset'), //重置
            handler:function(){
                this.up('form').getForm().reset();
            }
        },
        {
            xtype:'container'
        },
        {
            text:writeoff.receivableStatement.i18n('foss.stl.writeoff.common.query'),//查询
            width:80,
            cls:'yellow_button',
            xtype:'button',
            handler:function(){
                var form  = this.up('form').getForm();
                //校验日期
                var periodBeginDate = form.findField('periodBeginDate').getValue();
                var periodEndDate = form.findField('periodEndDate').getValue();
                //校验日期
                if(Ext.isEmpty(periodBeginDate)||Ext.isEmpty(periodEndDate)){
                    //'温馨提示','开始时间不能为空'
                    Ext.Msg.alert(writeoff.receivableStatement.i18n('foss.stl.writeoff.common.alert'),writeoff.receivableStatement.i18n('foss.stl.writeoff.statementAdd.quryDateIsNullWarning'));
                    return false;
                }
                //比较起始日期和结束日期
                var compareTwoDate = stl.compareTwoDate(periodBeginDate,periodEndDate);
                if(compareTwoDate>stl.DATELIMITDAYS_MONTH){
                    //'温馨提示','起始日期和结束日期间隔不能超过31天!'
                    Ext.Msg.alert(writeoff.receivableStatement.i18n('foss.stl.writeoff.common.alert'),writeoff.receivableStatement.i18n('foss.stl.writeoff.statementEdit.queryDateMaxLimit'));
                    return false;
                }else if(compareTwoDate<1){
                    //温馨提示','结束日期不能早于起始日期!
                    Ext.Msg.alert(writeoff.receivableStatement.i18n('foss.stl.writeoff.common.alert'),writeoff.receivableStatement.i18n('foss.stl.writeoff.receivableWriteOffPayble.endDateErrorWarning'));
                    return false;
                }
                //点击查询触发事件
                writeoff.receivableStatement.store.moveFirst();
            }
        }
    ]

});


//按对账单号制作
Ext.define('Foss.writeoff.receivableStatement.productionOrderForm',{
    extend:'Ext.form.Panel',
    frame:false,
    defaults:{
        margin :'15 5 5 0',
        labelWidth :100,
        colspan : 1
    },
    defaultType:'textfield',
    layout:{
        type :'table',
        columns :3
    },
    items:[
        {
            xtype: 'textarea',
            autoScroll:false,
            fieldLabel:writeoff.receivableStatement.i18n('foss.stl.writeoff.receivableWriteOffPayble.statementNo'),//对账单号
            name: 'waybillNo',
            height : 80,
            width : 600,
            allowBlank:false,
            colspan:2,
            columnWidth:.4
        },
        {
            xtype:'container',
            columnWidth:.35,
            layout:'vbox',
            items:[{
                xtype:'component',
                padding:'0 0 0 10'/*,
                autoEl:{
                    tag:'div',
                    html:'<span style="color:red;">'+'（备注包括：运单、应收单）'+'</span>'
                }*/
            }]
        },
        {
            border: 1,
            xtype:'container',
            columnWidth:1,
            colspan:3,
            defaultType:'button',
            layout:'column',
            items:[{
                text:writeoff.receivableStatement.i18n('foss.stl.writeoff.common.reset'), //重置
                columnWidth:.1,
                handler:function(){
                    this.up('form').getForm().reset();
                }
            },{
                xtype:'container',
                border:false,
                html:'&nbsp;',
                columnWidth:.1
            },{
                xtype:'container',
                border:false,
                html:'&nbsp;',
                columnWidth:.1
            },{
                xtype:'container',
                border:false,
                html:'&nbsp;',
                columnWidth:.1
            },{
                    text:writeoff.receivableStatement.i18n('foss.stl.writeoff.common.query'),//查询
                    columnWidth:.1,
                    cls:'yellow_button',
                    handler:function(){
                        var form  = this.up('form').getForm();
                        var waybillNo = form.findField('waybillNo').getValue();
                        var waybillNo = form.findField('waybillNo').getValue();     //单号
                        //判断传入单号是否为null或''
                        if(Ext.String.trim(waybillNo)==null || Ext.String.trim(waybillNo)==""){
                           // '温馨提示','单号不能为空！'
                            Ext.Msg.alert(writeoff.receivableStatement.i18n('foss.stl.writeoff.common.alert'),writeoff.receivableStatement.i18n('foss.stl.writeoff.common.billNosIsNullWarning'));
                            return false;
                        }
                        //点击查询触发事件
                        writeoff.receivableStatement.store.moveFirst();
                    }

                }]
        }
    ]

});


//按对运单号制作
Ext.define('Foss.writeoff.receivableStatement.waybillOrderForm',{
    extend:'Ext.form.Panel',
    frame:false,
    defaults:{
        margin :'15 5 5 0',
        labelWidth :100,
        colspan : 1
    },
    defaultType:'textfield',
    layout:{
        type :'table',
        columns :3
    },
    items:[
        {
            xtype: 'textarea',
            autoScroll:false,
            fieldLabel:writeoff.receivableStatement.i18n('foss.stl.writeoff.common.waybillNo'),//运单号
            name: 'waybillNo',
            height : 80,
            width : 600,
            allowBlank:false,
            colspan:2,
            columnWidth:.4
        },
        {
            xtype:'container',
            columnWidth:.35,
            layout:'vbox',
            items:[{
                xtype:'component',
                padding:'0 0 0 10'/*,
                 autoEl:{
                 tag:'div',
                 html:'<span style="color:red;">'+'（备注包括：运单、应收单）'+'</span>'
                 }*/
            }]
        },
        {
            border: 1,
            xtype:'container',
            columnWidth:1,
            colspan:3,
            defaultType:'button',
            layout:'column',
            items:[{
                text:writeoff.receivableStatement.i18n('foss.stl.writeoff.common.reset'), //重置
                columnWidth:.1,
                handler:function(){
                    this.up('form').getForm().reset();
                }
            },{
                xtype:'container',
                border:false,
                html:'&nbsp;',
                columnWidth:.1
            },{
                xtype:'container',
                border:false,
                html:'&nbsp;',
                columnWidth:.1
            },{
                xtype:'container',
                border:false,
                html:'&nbsp;',
                columnWidth:.1
            },{
                text:writeoff.receivableStatement.i18n('foss.stl.writeoff.common.query'),//查询
                columnWidth:.1,
                cls:'yellow_button',
                handler:function(){
                    var form  = this.up('form').getForm();
                    var waybillNo = form.findField('waybillNo').getValue();
                    //判断传入单号是否为null或''
                    if(Ext.String.trim(waybillNo)==null || Ext.String.trim(waybillNo)==""){
                        //'温馨提示','单号不能为空！'
                        Ext.Msg.alert(writeoff.receivableStatement.i18n('foss.stl.writeoff.common.alert'),writeoff.receivableStatement.i18n('foss.stl.writeoff.common.billNosIsNullWarning'));
                        return false;
                    }
                    //点击查询触发事件
                    writeoff.receivableStatement.store.moveFirst();
                }

            }]
        }
    ]

});

//按部门查询
Ext.define('Foss.writeoff.receivableStatement.departmentForm',{
    extend:'Ext.form.Panel',
    frame:false,
    defaults:{
        margin :'15 5 5 0',
        labelWidth :100,
        colspan : 1
    },
    defaultType:'textfield',
    layout:{
        type :'table',
        columns :1
    },
    items:[
        {
            xtype : 'dynamicorgcombselector',
            fieldLabel : '按部门查询',
            name : 'createOrgCode',
            value : stl.currentDept.name,
            columnWidth:.3,
            labelWidth : 85,
            listWidth : 300,// 设置下拉框宽度
            isPaging : true
        },
        {
            border: 1,
            xtype:'container',
            columnWidth:1,
            colspan:3,
            defaultType:'button',
            layout:'column',
            items:[{
                text:'重置', //重置
                columnWidth:.200,
                handler:function(){
                    this.up('form').getForm().reset();
                }
            },{
                text:'查询',//查询
                columnWidth:.200,
                cls:'yellow_button',
                handler:function(){
                    var form  = this.up('form').getForm();
                    var waybillNo = form.findField('createOrgCode').getValue();
                    //判断传入单号是否为null或''
                    if(Ext.String.trim(waybillNo)==null || Ext.String.trim(waybillNo)==""){
                        Ext.Msg.alert('温馨提示','部门不能为空！');
                        return false;
                    }
                    //点击查询触发事件
                    writeoff.receivableStatement.store.moveFirst();
                }

            }]
        }
    ]

});

//定义全局的查询form
//按日期查询
writeoff.receivableStatement.producedByPartnerForm = Ext.create('Foss.writeoff.receivableStatement.producedByPartnerForm');
//按对账查询
writeoff.receivableStatement.productionOrderForm = Ext.create('Foss.writeoff.receivableStatement.productionOrderForm');
//按运单查询
writeoff.receivableStatement.waybillOrderForm = Ext.create('Foss.writeoff.receivableStatement.waybillOrderForm');
//按部门查询
writeoff.receivableStatement.departmentForm = Ext.create('Foss.writeoff.receivableStatement.departmentForm');
//定义Tab
Ext.define('Foss.writeoff.receivableStatement.receivableStatementTab',{
    extend:'Ext.tab.Panel',
    frame : false,
    bodyCls : 'autoHeight',
    cls : 'innerTabPanel',
    activeTab : 0,
    columnWidth: 1,
    //columnHeight: 'autoHeight',
    height:230,
    items: [{
        title:writeoff.receivableStatement.i18n('foss.stl.writeoff.partnerPayStatementEdit.queryByPartner') ,   //按合伙人查询
        itemId:'producedByStatementPartnerId',
        tabConfig : {
            width : 120
        },
        layout : 'fit',
        items : [
            writeoff.receivableStatement.producedByPartnerForm
        ]
    },{
        title:writeoff.receivableStatement.i18n('foss.stl.writeoff.statementEdit.queryByStatement'),//按对账单号查询
        itemId:'productionStatementOrderId',
        tabConfig : {
            width : 120
        },
        layout : 'fit',
        items : [
            writeoff.receivableStatement.productionOrderForm
        ]
    },{
        title:writeoff.receivableStatement.i18n('foss.stl.writeoff.statementEdit.queryByWaybillNo'),//按运单号查询
        itemId:'waybillStatementOrderFormId',
        tabConfig : {
            width : 120
        },
        layout : 'fit',
        items : [
            writeoff.receivableStatement.waybillOrderForm
        ]
    },{
        title:writeoff.receivableStatement.i18n('foss.stl.writeoff.common.failingInvoiceOrgName'),//按部门查询
        itemId:'departmentStatementFormId',
        tabConfig : {
            width : 120
        },
        layout : 'fit',
        items : [
            writeoff.receivableStatement.departmentForm
        ]
    }]
});

//定义model
Ext.define('Foss.writeoff.receivableStatement.receivableStatementModel',{
    extend:'Ext.data.Model',
    fields:[
        //对账单单号
        {name:'statementBillNo'},
        //部门编码
        {name:'createOrgCode'},
        //部门名称
        {name:'createOrgName'},
        //公司编码
        {name:'companyCode'},
        //公司名称
        {name:'companyName'},
        //部门标杆编码
        {name:'unifiedCode'},
        //客户编码
        {name:'customerCode'},
        //客户名称
        {name:'customerName'},
        //本期发生金额
        {name:'periodAmount'},
        //本期应收金额
        {name:'periodRecAmount'},
        //本期剩余应收金额
        {name:'periodUnverifyRecAmount'},
        //账期开始日期
        {name:'periodBeginDate',type:'date',
            convert:function(value){
                if(value!=null){
                    var date = new Date(value);
                    return date;
                }else{
                    return null;
                }
            }},
        //账期结束日期
        {name:'periodEndDate',type:'date',
            convert:function(value){
                if(value!=null){
                    var date = new Date(value);
                    return date;
                }else{
                    return null;
                }
            }},
        //结账次数
        {name:'settleNum'},
        //确认时间
        {name:'confirmTime',type:'date',
            convert:function(value){
                if(value!=null){
                    var date = new Date(value);
                    return date;
                }else{
                    return null;
                }
            }},
        //子公司账号
        {name:'companyAccountBankNo'},
        //开户名
        {name:'accountUserName'},
        //支行名称
        {name:'bankBranchName'},
        //对账单状态
        {name:'statementStatus'},
        //本期已还金额
        {name:'periodRrpayAmount'},
        //本期未还金额
        {name:'periodNpayAmount'},
        //对账单描述
        {name:'statementDes'},
        //创建时间
        {name:'createTime',type:'date',
            convert:function(value){
                if(value!=null){
                    var date = new Date(value);
                    return date;
                }else{
                    return null;
                }
            }}
    ]
});


//定义store
Ext.define('Foss.writeoff.receivableStatement.receivableStatementStore',{
    extend:'Ext.data.Store',
    model:'Foss.writeoff.receivableStatement.receivableStatementModel',
    //是否自动查询
    autoLoad: false,
    pageSize:20,
    proxy: {
        //代理的类型为内存代理
        type: 'ajax',
        //提交方式
        actionMethods:'POST',
        url:writeoff.realPath('queryPartnerReceivalbeStatement.action'),
        //定义一个读取器
        reader: {
            //以JSON的方式读取
            type: 'json',
            //定义读取JSON数据的根对象
            root: 'statementRecivaleVo.statementRecivableDto.statementRecivableEntityList',
            //返回总数
            totalProperty : 'totalCount'
        }
    },
    listeners:{
        beforeload : function(store, operation, eOpts){//查询事件
            //查询条件form中的值
            var queryParams;
            var params;
            var waybillNoList = new Array();       //订单号的集合
            var flowType = writeoff.receivableStatement.queryTab.getActiveTab().itemId;
            //按合伙人查询
            if(flowType=='producedByStatementPartnerId'){
                queryParams = writeoff.receivableStatement.producedByPartnerForm.getValues();
                var tab = 'partner';
                params={
                   'statementRecivaleVo.statementRecivableDto.largeRegion': queryParams.largeRegion,
                    'statementRecivaleVo.statementRecivableDto.smallRegion': queryParams.smallRegion,
                    'statementRecivaleVo.statementRecivableDto.createOrgCode':queryParams.createOrgCode,
                    'statementRecivaleVo.statementRecivableDto.businessStartDate':queryParams.periodBeginDate,
                    'statementRecivaleVo.statementRecivableDto.businessEndDate':queryParams.periodEndDate,
                    'statementRecivaleVo.statementRecivableDto.contractOrgCode':queryParams.customerCode,
                    'statementRecivaleVo.statementRecivableDto.statementStatus':queryParams.statementStatus,
                    'statementRecivaleVo.statementRecivableDto.settleStatus':queryParams.settleStatus,
                    'statementRecivaleVo.statementRecivableDto.tab':tab
                }
            }
            //按照对账单号
            if(flowType=='productionStatementOrderId'){
                queryParams = writeoff.receivableStatement.productionOrderForm.getValues();
                waybillNoList = queryParams.waybillNo.split(",");
                var tab ='productionStatement';
                params={
                    'statementRecivaleVo.statementRecivableDto.waybillNoList': waybillNoList,
                    'statementRecivaleVo.statementRecivableDto.tab':tab
                }
            }
            //按运单号
            if(flowType=='waybillStatementOrderFormId'){
                queryParams = writeoff.receivableStatement.waybillOrderForm.getValues();
                waybillNoList = queryParams.waybillNo.split(",");
                var tab ='waybillStatement';
                params={
                    'statementRecivaleVo.statementRecivableDto.waybillNoList': waybillNoList,
                    'statementRecivaleVo.statementRecivableDto.tab':tab
                }
            }
            //按部门查询
            if(flowType=='departmentStatementFormId'){
                queryParams = writeoff.receivableStatement.departmentForm.getValues();
                createOrgCode = queryParams.createOrgCode;
                var tab ='department';
                params={
                    'statementRecivaleVo.statementRecivableDto.createOrgCode': createOrgCode,
                    'statementRecivaleVo.statementRecivableDto.tab':tab
                }
            }
            Ext.apply(operation, {
                params :params
            });
        },
        load : function(store, records, successful, eOpts){//表格数据加载事件
            var data = store.getProxy().getReader().rawData;
            if(data==undefined){
                //"温馨提示","系统错误"
                Ext.MessageBox.alert(writeoff.receivableStatement.i18n('foss.stl.writeoff.common.alert'),writeoff.receivableStatement.i18n('foss.stl.writeoff.partnerPayStatementEdit.serverErrTryAgain'));
                return ;
            }
            if(data.success==false){
                Ext.MessageBox.alert(writeoff.receivableStatement.i18n('foss.stl.writeoff.common.alert'),data.message);
                return ;
            }
            //总条数
            if(data.totalCount!=null) {
                Ext.getCmp('Foss.receivableStatement.statement.total').setValue(data.totalCount);
            }else{
                Ext.getCmp('Foss.receivableStatement.statement.total').setValue('0');
            }
        }
    }
});


/**
 * 单据子类型的model
 */
Ext.define('Foss.statementbill.queryComboModel', {
    extend: 'Ext.data.Model',
    fields: [{
        name: 'name'
    }, {
        name: 'value'
    }]
});

/**
 * 对账单表格操作列store
 */
Ext.define('Foss.statementbill.operateColumnStore',{
    extened:'Ext.data.Store',
    model:'Foss.statementbill.queryComboModel',
    data:{
        'items':[
            {name:'反确认',value:'1'},
            {name:'确认',value:'3'},
            {name:'明细',value:'4'},
            {name:'还款',value:'5'}
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



/**
 * 基本对账单信息
 */
Ext.define('Foss.receivableStatement.BaseInfo',{
    extend:'Ext.form.Panel',
    layout:'column',
    frame:true,
    //hidden:true,
    defaultType:'textfield',
    layout:'column',
    defaults:{
        labelWidth:65,
        margin:'5 5 5 10',
        readOnly:true
    },
    items:[{
        fieldLabel:writeoff.receivableStatement.i18n('foss.stl.writeoff.statementAdd.billType'),//对账单类型
        name:'billType',
        xtype:'combo',
        labelWidth:80,
        columnWidth:.22,
        id:'Foss.receivableStatement.datil.billType',
        store:null,
        queryModel:'local',
        displayField:'valueName',
        valueField:'valueCode'
    },{
        xtype:'datefield',
        fieldLabel:writeoff.receivableStatement.i18n('foss.stl.writeoff.statementAdd.createTime'), //制单时间
        name:'createTime',
        id:'Foss.receivableStatement.datil.createTime',
        format:'Y-m-d H:i:s',
        columnWidth:.28
    },{
        xtype:'commoncustomerselector',
        //listWidth:380,
        fieldLabel:writeoff.receivableStatement.i18n('foss.stl.writeoff.partnerPayStatementEdit.customerCode'), //合伙人编码
        labelWidth:80,
        singlePeopleFlag:'Y',
        name:'contractOrgCode',
        id:'Foss.receivableStatement.datil.contractOrgCode',
        columnWidth:.26
    },{
        fieldLabel:writeoff.receivableStatement.i18n('foss.stl.writeoff.statementAdd.confirmStatus'),//确认状态
        name:'confirmStatus',
        id:'Foss.receivableStatement.datil.confirmStatus',
        xtype:'combo',
        store:null,
        queryModel:'local',
        displayField:'valueName',
        valueField:'valueCode',
        columnWidth:.24,
        value:''
    },{
        fieldLabel:writeoff.receivableStatement.i18n('foss.stl.writeoff.statementEdit.periodStartDate'),   //账单开始日期
        // listWidth:380,
        labelWidth:100,
        name:'periodBeginDate',
        id:'Foss.receivableStatement.datil.periodBeginDate',
        xtype:'datefield',
        format:'Y-m-d',
        columnWidth:.22
    },{
        fieldLabel:writeoff.receivableStatement.i18n('foss.stl.writeoff.statementAdd.companyName'),  //所属公司
        name:'companyName',
        id:'Foss.receivableStatement.datil.companyName',
        columnWidth:.28
    },{
        fieldLabel: writeoff.receivableStatement.i18n('foss.stl.writeoff.statementAdd.companyCode'),//所属公司编码
        name: 'companyCode',
        id: 'Foss.receivableStatement.datil.companyCode',
        hidden:true,
        columnWidth: .28
    },{
        fieldLabel:writeoff.receivableStatement.i18n('foss.stl.writeoff.partnerPayStatementEdit.partnerName'), //合伙人名称
        name:'contractOrgName',
        labelWidth:80,
        id:'Foss.receivableStatement.datil.contractOrgName',
        columnWidth:.30
    },{
        fieldLabel:writeoff.receivableStatement.i18n('foss.stl.writeoff.statementAdd.confirmTime'),//确认时间
        name:'confirmTime',
        id:'Foss.receivableStatement.datil.confirmTime',
        format:'Y-m-d',
        xtype:'datefield',
        labelWidth:80,
        columnWidth:.20
    },{
        fieldLabel:writeoff.receivableStatement.i18n('foss.stl.writeoff.statementEdit.periodEndAmount'),//账期结束时间
        name:'periodEndDate',
        labelWidth:100,
        id:'Foss.receivableStatement.datil.periodEndDate',
        xtype:'datefield',
        format:'Y-m-d',
        columnWidth:.30
    },{
        fieldLabel:writeoff.receivableStatement.i18n('foss.stl.writeoff.statementAdd.createOrgName'),//所属部门
        name:'createOrgName',
        id:'Foss.receivableStatement.datil.createOrgName',
        columnWidth:.28
    },{
        fieldLabel:writeoff.receivableStatement.i18n('foss.stl.writeoff.statementAdd.createOrgName'),//所属部门编码隐藏的
        name:'createOrgCode',
        id:'Foss.receivableStatement.datil.createOrgCode',
        hidden:true,
        columnWidth:.28
    },{
        fieldLabel:writeoff.receivableStatement.i18n('foss.stl.writeoff.statementAdd.statementBillNo'),//对账单号
        name:'statementBillNo',
        id:'Foss.receivableStatement.datil.statementBillNo',
        columnWidth:.30
    },{
        xtype: 'component',
        border:true,
        autoEl: {
            tag: 'hr'
        },
        columnWidth:1
    },{
        fieldLabel:writeoff.receivableStatement.i18n('foss.stl.writeoff.statementAdd.unifiedCode'),//部门标杆编码
        labelWidth:85,
        name:'unifiedCode',
        id:'Foss.receivableStatement.datil.unifiedCode',
        columnWidth:.20
    },{
        xtype:'container',
        columnWidth:.30,
        layout:'vbox',
        items:[{
            xtype:'component',
            padding:'0 0 0 0',
            autoEl:{
                tag:'div',
                html:'<span style="color:red;">'+writeoff.receivableStatement.i18n('foss.stl.writeoff.statementAdd.unifiedCodeNotes')+'</span>'//请在汇款时备注我司的部门标杆编码
            }
        }]
    },{
        fieldLabel:writeoff.receivableStatement.i18n('foss.stl.writeoff.statementAdd.companyAccountBankNo'),//账号
        name:'companyAccountBankNo',
        id:'Foss.receivableStatement.datil.companyAccountBankNo',
        columnWidth:.22
    },{
        xtype:'container',
        columnWidth:.28,
        layout:'vbox',
        items:[{
            xtype:'component',
            padding:'0 0 0 0',
            autoEl:{
                tag:'div',
                html:'<span style="color:red;">'+writeoff.receivableStatement.i18n('foss.stl.writeoff.statementAdd.companyAccountBankNoNotes')+'</span>'//此账号为我司唯一的还款账号
            }
        }]
    },{
        fieldLabel:writeoff.receivableStatement.i18n('foss.stl.writeoff.statementAdd.accountUserName'),//开户名
        name:'accountUserName',
        id:'Foss.receivableStatement.datil.accountUserName',
        columnWidth:.5
    },{
        fieldLabel:writeoff.receivableStatement.i18n('foss.stl.writeoff.statementAdd.bankBranchName'),//省市支行
        name:'bankBranchName',
        id:'Foss.receivableStatement.datil.bankBranchName',
        columnWidth:.48
    },{
        xtype: 'component',
        border:true,
        autoEl: {
            tag: 'hr'
        },
        columnWidth:1
    },{
        fieldLabel:writeoff.receivableStatement.i18n('foss.stl.writeoff.statementAdd.unpaidAmount'),//本期剩余未还款金额
        labelWidth:110,
        name:'unpaidAmount',
        id:'Foss.receivableStatement.datil.unpaidAmount',
        xtype:'numberfield',
        decimalPrecision:2,
        columnWidth:.5,
        value:0
    },{
        fieldLabel:writeoff.receivableStatement.i18n('foss.stl.writeoff.statementAdd.settleNum'),//结账次数
        columnWidth:.48,
        name:'settleNum',
        id:'Foss.receivableStatement.datil.settleNum',
        xtype:'numberfield',
        value:0
    }]
});

/**
 * 对账单操作按钮区域
 */
Ext.define('Foss.receivableStatement.OperateButtonPanel', {
    extend: 'Ext.panel.Panel',
    layout: 'column',
    defaultType: 'button',
    defaults: {
        columnWidth: .1
    },
    items: [{
        xtype: 'container',
        border: false,
        html: '&nbsp;',
        columnWidth: .25
    }, {
        text: writeoff.receivableStatement.i18n('foss.stl.writeoff.statementEdit.confirm'),//确认
        handler: function(){
            queren();

        }
    },{
        text: writeoff.receivableStatement.i18n('foss.stl.writeoff.statementEdit.repayment'),//还款
        handler: function () {
            huankuan();
        }
    },{
        text: writeoff.receivableStatement.i18n('foss.stl.writeoff.statementEdit.unConfirm'),//反确认
        handler: function(){
            fanqueren();

        }
    },{
        text:writeoff.receivableStatement.i18n('foss.stl.writeoff.statementEdit.exportStatement'),//导出对账单
       handler: function(){

           var	columns,
               arrayColumns,
               arrayColumnNames;
           var statementBillNo = new Array();
           //对账单grid
           var statementList = Ext.getCmp('Foss.receivableStatement.datil.statementBillNo').getValue();
           if(undefined!=statementList){
               statementBillNo.push(statementList);
           }
           //转化列头和列明
           columns = writeoff.receivableStatement.grid.columns;
           arrayColumns = [];
           arrayColumnNames = [];
           //将前台对应列头传入到后台去
           for(var i=2;i<columns.length;i++){
               if(columns[i].isHidden()==false){
                   var hederName = columns[i].text;
                   var dataIndex = columns[i].dataIndex;
                   arrayColumns.push(dataIndex);
                   arrayColumnNames.push(hederName);
               }
           }
           //拼接vo，注入到后台
           searchParams = {
               'statementRecivaleVo.statementRecivableDto.waybillNoList':statementBillNo,
               'statementRecivaleVo.statementRecivableDto.arrayColumns':arrayColumns,
               'statementRecivaleVo.statementRecivableDto.arrayColumnNames':arrayColumnNames
           };
           if(!Ext.fly('downloadAttachFileForm')){
               var frm = document.createElement('form');
               frm.id = 'downloadAttachFileForm';
               frm.style.display = 'none';
               document.body.appendChild(frm);
           }
           Ext.Ajax.request({
               url: writeoff.realPath('receivableStatemenExl.action'),
               form: Ext.fly('downloadAttachFileForm'),
               method : 'POST',
               params : searchParams,
               isUpload: true,
               success : function(response,options){
                   //'温馨提示', '导出成功！'
                   Ext.Msg.alert(writeoff.receivableStatement.i18n('foss.stl.writeoff.common.alert'), writeoff.receivableStatement.i18n('foss.stl.writeoff.partnerPayStatementEdit.exportSuccess'));
               }
           });
       }
    }, {
        text: writeoff.receivableStatement.i18n('foss.stl.writeoff.partnerPayStatementEdit.exportDetail'), //导出对账单明细
        handler: function(){

            var	columns,
                arrayColumns,
                arrayColumnNames;
            var statementBillNo = new Array();
            //对账单grid
            var statementList = Ext.getCmp('Foss.receivableStatement.datil.statementBillNo').getValue();
            if(undefined!=statementList){
                statementBillNo.push(statementList);
            }
            //转化列头和列明
            columns = writeoff.receivableStatement.detail.columns;
            arrayColumns = [];
            arrayColumnNames = [];
            //将前台对应列头传入到后台去
            for(var i=2;i<columns.length;i++){
                if(columns[i].isHidden()==false){
                    var hederName = columns[i].text;
                    var dataIndex = columns[i].dataIndex;
                    arrayColumns.push(dataIndex);
                    arrayColumnNames.push(hederName);
                }
            }
            //拼接vo，注入到后台
            searchParams = {
                'statementRecivaleVo.statementRecivableDto.waybillNoList':statementBillNo,
                'statementRecivaleVo.statementRecivableDto.arrayColumns':arrayColumns,
                'statementRecivaleVo.statementRecivableDto.arrayColumnNames':arrayColumnNames
            };
            if(!Ext.fly('downloadAttachFileForm')){
                var frm = document.createElement('form');
                frm.id = 'downloadAttachFileForm';
                frm.style.display = 'none';
                document.body.appendChild(frm);
            }
            Ext.Ajax.request({
                url: writeoff.realPath('receivableStatemenDetailExl.action'),
                form: Ext.fly('downloadAttachFileForm'),
                method : 'POST',
                params : searchParams,
                isUpload: true,
                success : function(response,options){
                    Ext.Msg.alert(writeoff.receivableStatement.i18n('foss.stl.writeoff.common.alert'), writeoff.receivableStatement.i18n('foss.stl.writeoff.partnerPayStatementEdit.exportSuccess'));
                }
            });
        }
    }]
});

//对账单明细的model
Ext.define('Foss.receivableStatement.detailModel',{
    extend:'Ext.data.Model',
    fields:[
        //业务日期
        {name:'businessDate'},
        //运单号
        {name: 'waybillNo'},
        //目的站
        {name:'arrvRegionCode'},
        //产品类型
        {name:'proType'},
        //件数
        {name:'qty'},
        //计费体积
        {name:'billingVolume'},
        //计费重量
        {name:'billWeight'},
        //付款方式
        {name:'paymentType'},
        //提货方式
        {name:'receiveMethod'},
        //原始未核销金额
        {name:'unverifyAmount'},
        //总金额
        {name:'amount'},
        //单号
        {name:'sourceBillNo'},
        //单据子类型
        {name:'billType'},
        //客户代理编码
        {name:'customerCode'},
        //客户代理名称
        {name:'customerName'},
        //已核销金额
        {name:'verifyAmount'},
        //部门编码
        {name:'orgCode'},
        //部门名称
        {name:'orgName'},
        //记账日期
        {name:'accountDate'},
        //提货网点
        {name:'customerPickupOrgName'},
        //货物名称
        {name:'goodsName'},
        //始发网点编码
        {name:'origOrgCode'},
        //始发网点名称
        {name:'origOrgName'},
        //到达部门编码
        {name:'destOrgCode'},
        //到达部门名称
        {name:'destOrgName'},
        //发货客户编码
        {name:'deliveryCustomerCode'},
        //签收日期
        {name:'signDate'},
        //审核状态
        {name:'auditStatus'},
        //备注
        {name:'notes'},
        //是否删除
        {name:'isDelete'},
        //作废时间
        {name:'cancelTime'},
        //运单开单时间
        {name:'billBeginTime'},
        //单操作费
        {name:'singleOperationFee'},
        //包装费提成
        {name:'packageFee'},
        //保价费提成
        {name:'insuranceFee'},
        //代收货款手续费提成
        {name:'disbursementFee'},
        //送货费提成（不含上楼）
        {name:'deliveryFee'},
        //基础送货费提成
        {name:'baseDeliveryFee'},
        //床垫操作费提成
        {name:'mattressOperationFee'},
        //代理报关费提成
        {name:'agentDeclarationFee'},
        //拆包装费提成
        {name:'removePackingFee'},
        //登记费提成
        {name:'registrationFee'},
        //停车费提成
        {name:'parkingFee'},
        //其他费用提成
        {name:'otherFee'},
        //送货上楼费提成
        {name:'upstairsFee'},
        //送货进仓费提成
        {name:'warehouseDeliveryFee'},
        //大件上楼费提成
        {name:'largeUpstairsFee'},
        //超远派送费提成
        {name:'superLongFee'},
        //签收单返回提成
        {name:'singleReturnFee'},
        /**
         * 创建时间
         */
        {name:'createTime'},
        /**
         * 对账单单号
         */
        {name:'billStatementNo'}
    ]
});

//对账单明细的store
Ext.define('Foss.receivableStatement.detailStore',{
    extend:'Ext.data.Store',
    pageSize:20,
    model:'Foss.receivableStatement.detailModel',
    proxy: {
        //代理的类型为内存代理
        type: 'ajax',
        //提交方式
        actionMethods:'POST',
        url:writeoff.realPath('queryReceivalbeStatementByBillNo.action'),
        //定义一个读取器
        reader: {
            //以JSON的方式读取
            type: 'json',
            //定义读取JSON数据的根对象
            root: 'statementRecivaleVo.statementRecivableDto.statementRecivableDEntityList',
            //返回总数
            totalProperty : 'totalCount'
        }
    },
    listeners:{
        'beforeLoad': function(store, operation, eOpts){//查询事件
            //获取对账单选中行的值
            var params;
            var queryParams = writeoff.receivableStatement.grid.getSelectionModel().getLastSelected();

            //给面板赋值(进来之前先清空)
            Ext.getCmp('Foss.receivableStatement.datil.billType').setValue(writeoff.receivableStatement.i18n('foss.stl.writeoff.partnerPayStatementEdit.hehuorenshoukuan'));
            //制单时间
            Ext.getCmp('Foss.receivableStatement.datil.createTime').setValue(queryParams.get('createTime'));
            //合伙人编码
            Ext.getCmp('Foss.receivableStatement.datil.contractOrgCode').setValue(queryParams.get('customerCode'));
            //确认状态
            Ext.getCmp('Foss.receivableStatement.datil.confirmStatus').setValue(queryParams.get('statementStatus'));
            //账单开始日期
            Ext.getCmp('Foss.receivableStatement.datil.periodBeginDate').setValue(queryParams.get('periodBeginDate'));
            //所属公司
            Ext.getCmp('Foss.receivableStatement.datil.companyName').setValue(queryParams.get('companyName'));
            //所属公司编码
            Ext.getCmp('Foss.receivableStatement.datil.companyCode').setValue(queryParams.get('companyCode'));
            //合伙人名称
            Ext.getCmp('Foss.receivableStatement.datil.contractOrgName').setValue(queryParams.get('customerName'));
            //确认时间
            Ext.getCmp('Foss.receivableStatement.datil.confirmTime').setValue(queryParams.get('confirmTime'));
            //账期结束时间
            Ext.getCmp('Foss.receivableStatement.datil.periodEndDate').setValue(queryParams.get('periodEndDate'));
            //所属部门
            Ext.getCmp('Foss.receivableStatement.datil.createOrgName').setValue(queryParams.get('createOrgName'));
            //对账单号
            Ext.getCmp('Foss.receivableStatement.datil.statementBillNo').setValue(queryParams.get('statementBillNo'));
            //部门标杆编码
            Ext.getCmp('Foss.receivableStatement.datil.unifiedCode').setValue(queryParams.get('unifiedCode'));
            //账号
            Ext.getCmp('Foss.receivableStatement.datil.companyAccountBankNo').setValue(queryParams.get('companyAccountBankNo'));
            //开户名
            Ext.getCmp('Foss.receivableStatement.datil.accountUserName').setValue(queryParams.get('accountUserName'));
            //省市支行
            Ext.getCmp('Foss.receivableStatement.datil.bankBranchName').setValue(queryParams.get('bankBranchName'));
            //本期剩余还款金额
            Ext.getCmp('Foss.receivableStatement.datil.unpaidAmount').setValue(queryParams.get('periodNpayAmount'));
            //结账次数
            Ext.getCmp('Foss.receivableStatement.datil.settleNum').setValue(queryParams.get('settleNum'));

            var statementBillNo = queryParams.get('statementBillNo');
            params={
                'statementRecivaleVo.statementRecivableDto.statementBillNo':statementBillNo
            };
            Ext.apply(operation, {
                params :params
            });
        },
        load : function(store, records, successful, eOpts){//表格数据加载事件
        var data = store.getProxy().getReader().rawData;
        if(data==undefined){
            //"温馨提示","系统错误"
            Ext.MessageBox.alert(writeoff.receivableStatement.i18n('foss.stl.writeoff.common.alert'),writeoff.receivableStatement.i18n('foss.stl.writeoff.partnerPayStatementEdit.serverErrTryAgain'));
            return ;
        }
        if(data.success==false){
            Ext.MessageBox.alert(writeoff.receivableStatement.i18n('foss.stl.writeoff.common.alert'),data.message);
            return ;
        }
        //总条数
        if(data.totalCount!=null) {
            Ext.getCmp('Foss.mingxi.totalCount').setValue(data.totalCount);
        }else{
            Ext.getCmp('Foss.mingxi.totalCount').setValue('0');
        }
    }
    }
});


//对账单按客户制作的Tab的form
Ext.define('Foss.writeoff.receivableStatement.producedByPartnerAddForm',{
    extend:'Ext.form.Panel',
    frame:false,
    defaults:{
        margin :'5 5 5 0',
        labelWidth :100
    },
    defaultType:'textfield',
    layout:{
        type :'table',
        columns :3
    },
    items:[{
            xtype: 'textfield',
            name:'settmentNo',
            fieldLabel: writeoff.receivableStatement.i18n('foss.stl.writeoff.statementAdd.statementBillNo') ,       //对账单单号
            readOnly:true,
            allowBlank: false
        },{
            xtype:'datefield',
            fieldLabel:writeoff.receivableStatement.i18n('foss.stl.writeoff.billDepWriteOffBillRec.startDate'),  //开始日期
            allowBlank:false,
            name:'periodBeginDate',
            format:'Y-m-d',
            value:stl.getTargetDate(stl.getLastMonthLastDay(new Date()),+1)
        },{
            xtype:'datefield',
            fieldLabel:writeoff.receivableStatement.i18n('foss.stl.writeoff.billDepWriteOffBillRec.endDate'),      //结束日期
            name:'periodEndDate',
            format:'Y-m-d',
            value:stl.getTargetDate(new Date(),+1)
        },
        {
            xtype: 'textfield',
            name:'customerName',
            fieldLabel: writeoff.receivableStatement.i18n('foss.stl.writeoff.customersNotReconciled.customerName'),//客户名称
            readOnly:true,
            allowBlank: false
        },
        {
            xtype: 'textfield',
            name:'customerCode',
            fieldLabel:  writeoff.receivableStatement.i18n('foss.stl.writeoff.customersNotReconciled.customerCode'),       //客户编码
            readOnly:true,
            allowBlank: false
        },
        {
            xtype:'container'
        },
        {
            xtype:'button',
            align:'center',
            width:80,
            text:writeoff.receivableStatement.i18n('foss.stl.writeoff.common.reset')   //重置
        },
        {
            xtype:'container'
        },
        {
            text:writeoff.receivableStatement.i18n('foss.stl.writeoff.common.query'),//查询
            width:80,
            cls:'yellow_button',
            xtype:'button',
            handler:function(){
                var form  = this.up('form').getForm();
                //校验日期
                var periodBeginDate = form.findField('periodBeginDate').getValue();
                var periodEndDate = form.findField('periodEndDate').getValue();
                //校验日期
                if(Ext.isEmpty(periodBeginDate)||Ext.isEmpty(periodEndDate)){
                    //'温馨提示','开始时间不能为空'
                    Ext.Msg.alert(writeoff.receivableStatement.i18n('foss.stl.writeoff.common.alert'),writeoff.receivableStatement.i18n('foss.stl.writeoff.statementAdd.quryDateIsNullWarning'));
                    return false;
                }
                //比较起始日期和结束日期
                var compareTwoDate = stl.compareTwoDate(periodBeginDate,periodEndDate);
                if(compareTwoDate>stl.DATELIMITDAYS_MONTH){
                    //'温馨提示','起始日期和结束日期间隔不能超过31天!'
                    Ext.Msg.alert(writeoff.receivableStatement.i18n('foss.stl.writeoff.common.alert'),writeoff.receivableStatement.i18n('foss.stl.writeoff.statementEdit.queryDateMaxLimit'));
                    return false;
                }else if(compareTwoDate<1){
                    //'温馨提示','结束日期不能早于起始日期!
                    Ext.Msg.alert(writeoff.receivableStatement.i18n('foss.stl.writeoff.common.alert'),writeoff.receivableStatement.i18n('foss.stl.writeoff.receivableWriteOffPayble.endDateErrorWarning'));
                    return false;
                }
                //点击查询触发事件
                writeoff.receivableStatement.messageGrid.moveFirst();
            }
        }
    ]

});

//对账单详细按单号制作
Ext.define('Foss.writeoff.receivableStatement.productionOrderAddForm',{
    extend:'Ext.form.Panel',
    frame:false,
    defaults:{
        margin :'15 5 5 0',
        labelWidth :100,
        colspan : 1
    },
    defaultType:'textfield',
    layout:{
        type :'table',
        columns :3
    },
    items:[
        {
            xtype: 'textarea',
            autoScroll:false,
            fieldLabel:writeoff.receivableStatement.i18n('foss.stl.writeoff.common.waybillNo'),//运单号
            name: 'waybillNo',
            height : 40,
            width : 600,
            allowBlank:false,
            colspan:2,
            columnWidth:.4
        },
        {
            xtype:'container',
            columnWidth:.35,
            layout:'vbox',
            items:[{
                xtype:'component',
                padding:'0 0 0 10',
                autoEl:{
                    tag:'div',
                    html:'<span style="color:red;">'+'（备注包括：运单、应收单）'+'</span>'
                }
            }]
        },
        {
            border: 1,
            xtype:'container',
            columnWidth:1,
            colspan:3,
            defaultType:'button',
            layout:'column',
            items:[{
                text:writeoff.receivableStatement.i18n('foss.stl.writeoff.common.reset'), //重置
                columnWidth:.1,
                handler:function(){
                    this.up('form').getForm().reset();
                }
            },,{
                xtype:'container',
                border:false,
                html:'&nbsp;',
                columnWidth:.1
            },
                {
                    text:writeoff.receivableStatement.i18n('foss.stl.writeoff.common.query'),//查询
                    columnWidth:.1,
                    cls:'yellow_button',
                    handler:function(){
                        var me = this;
                        var form = this.up('form').getForm();
                        //需要传入后台的参数
                        var waybillNo = form.findField('waybillNo').getValue();     //单号
                        if(waybillNo==null||waybillNo==""){
                            //"温馨提示","单号不能为空！"
                            Ext.Msg.alert(writeoff.receivableStatement.i18n('foss.stl.writeoff.common.alert'),writeoff.receivableStatement.i18n('foss.stl.writeoff.common.billNosIsNullWarning'));
                            return;
                        }
                        //点击查询触发事件
                        writeoff.receivableStatement.messageGrid.moveFirst();
                    }

                }]
        }
    ]

});


writeoff.receivableStatement.producedByPartnerAddForm=Ext.create('Foss.writeoff.receivableStatement.producedByPartnerAddForm');
writeoff.receivableStatement.productionOrderAddForm=Ext.create('Foss.writeoff.receivableStatement.productionOrderAddForm');

/***
 * 添加对账单明细Model
 */
Ext.define('Foss.writeoff.receivableStatementModel',{
    extend:'Ext.data.Model',
    fields:[
        {name:'receivableNo'},//应收单号
        {name:'waybillNo'},//运单号
        {name:'waybillId'},//运单ID
        {name:'createType'},//系统生成方式
        {name:'sourceBillNo'},//来源单据单号
        {name:'sourceBillType'},//来源单据类型
        {name:'billType'},//单据子类型
        {name:'receivableOrgCode'},// 应收部门编码
        {name:'receivableOrgName'},// 应收部门名称
        {name:'generatingOrgCode'},//收入部门编码
        {name:'generatingOrgName'},//收入部门名称
        {name:'generatingComCode'},//收入子公司编码
        {name:'generatingComName'},//收入子公司名称
        {name:'dunningOrgCode'},//催款部门编码
        {name:'dunningOrgName'},//催款部门名称
        {name:'origOrgCode'},//出发部门编码
        {name:'origOrgName'},//出发部门名称
        {name:'destOrgCode'},//到达部门编码
        {name:'destOrgName'},//到达部门名称
        {name:'customerCode'},//客户编码/应收代理编码
        {name:'customerName'},//客户名称/应收代理名称
        {name:'deliveryCustomerCode'},//发货客户编码
        {name:'deliveryCustomerName'},//发货客户名称
        {name:'receiveCustomerCode'},//收货客户编码
        {name:'receiveCustomerName'},//收货客户名称
        {name:'amount'},//金额 BigDecimal存储，单位（元）；数据库以Long存储，单位（分）
        {name:'verifyAmount'},//已核销金额 BigDecimal存储，单位（元）；数据库以Long存储，单位（分）
        {name:'unverifyAmount'},//未核销金额 BigDecimal存储，单位（元）；数据库以Long存储，单位（分）
        {name:'currencyCode'},//币种
        //业务日期
        {name:'businessDate',type:'date',
            convert:function(value){
                if(value!=null){
                    var date = new Date(value);
                    return date;
                }else{
                    return null;
                }
            }},
        //记账日期
        {name:'accountDate',type:'date',
            convert:function(value){
                if(value!=null){
                    var date = new Date(value);
                    return date;
                }else{
                    return null;
                }
            }},
        //确认收入日期
        {name:'conrevenDate',type:'date',
            convert:function(value){
                if(value!=null){
                    var date = new Date(value);
                    return date;
                }else{
                    return null;
                }
            }},
        {name:'paymentType'},//付款方式
        {name:'productCode'},//运输性质
        {name:'productName'},//运输名称
        {name:'productId'},//产品ID
        {name:'transportFee'},//公布价运费 BigDecimal存储，单位（元）；数据库以Long存储，单位（分）
        {name:'pickupFee'},//接货费 BigDecimal存储，单位（元）；数据库以Long存储，单位（分）
        {name:'deliveryGoodsFee'},//送货费 BigDecimal存储，单位（元）；数据库以Long存储，单位（分）
        {name:'packagingFee'},//包装手续费 BigDecimal存储，单位（元）；数据库以Long存储，单位（分）
        {name:'codFee'},//代收货款手续费 BigDecimal存储，单位（元）；数据库以Long存储，单位（分）
        {name:'insuranceFee'},//保价费 BigDecimal存储，单位（元）；数据库以Long存储，单位（分）
        {name:'otherFee'},//其他费用 BigDecimal存储，单位（元）；数据库以Long存储，单位（分）
        {name:'valueAddFee'},//增值费用 BigDecimal存储，单位（元）；数据库以Long存储，单位（分）
        {name:'promotionsFee'},//优惠费用 BigDecimal存储，单位（元）；数据库以Long存储，单位（分）
        {name:'goodsName'},//货物名称
        {name:'goodsVolumeTotal'},//货物总体积
        {name:'billWeight'},//计费重量
        {name:'receiveMethod'},//提货方式
        {name:'customerPickupOrgCode'},//提货网点
        {name:'goodsQtyTotal'},//货物总件数
        {name:'targetOrgCode'},//目的站
        {name:'versionNo'},//版本号
        {name:'active'},//是否有效
        {name:'isRedBack'},//是否红单
        {name:'isInit'},//是否初始化
        {name:'stamp'},//是否标记
        {name:'createUserCode'},//制单人编码
        {name:'createUserName'},//制单人名称
        {name:'createOrgCode'},//制单部门编码
        {name:'createOrgName'},//制单部门名称
        {name:'createTime',type:'date',
            convert:function(value){
                if(value!=null){
                    var date = new Date(value);
                    return date;
                }else{
                    return null;
                }
            }},//创建时间
        {name:'modifyTime',type:'date',
            convert:function(value){
                if(value!=null){
                    var date = new Date(value);
                    return date;
                }else{
                    return null;
                }
            }},//修改时间
        {name:'modifyUserCode'},//修改人编码
        {name:'modifyUserName'},//修改人名称
        {name:'statementBillNo'},//对账单号
        //解锁时间
        {name:'unlockDateTime',type:'date',
            convert:function(value){
                if(value!=null){
                    var date = new Date(value);
                    return date;
                }else{
                    return null;
                }
            }},
        {name:'lockCustomerCode'},//锁定客户编码
        {name:'lockCustomerName'},//锁定客户名称
        {name:'collectionType'},//收款类别
        {name:'collectionName'},//收款名称
        {name:'auditUserCode'},//审核人编码
        {name:'auditUserName'},//审核人名称
        {name:'approveStatus'},//审批状态
        {name:'disableUserCode'},//作废人编码
        {name:'disableUserName'},//作废人名称
        //审核日期
        {name:'auditDate',type:'date',
            convert:function(value){
                if(value!=null){
                    var date = new Date(value);
                    return date;
                }else{
                    return null;
                }
            }},
        {name:'notes'},//备注
        {name:'BigDecimal unitPrice'},//运费计费费率
        {name:'insuranceAmount'},//保价声明价值
        {name:'deliveryCustomerContact'},// 发货联系人
        {name:'expressOrigOrgCode'},//出发部门映射快递点部编码
        {name:'expressOrigOrgName'},//出发部门映射快递点部名称
        {name:'expressDestOrgCode'},//到达部门映射快递点部编码
        {name:'expressDestOrgName'},//到达部门映射快递点部名称
        {name:'invoiceMark'},//发票标记
        {name:'unifiedSettlement'},//是否统一结算
        {name:'contractOrgCode'},//合同部门编码
        {name:'contractOrgName'},   //合同部门名称
        {name:'isDiscount'},        //是否折扣
        {name:'withholdStatus'}    //扣款状态
    ]
});



/**
 * 添加对账单明细store
 */
Ext.define('Foss.writeoff.detail.receivableStatementStore',{
    extend:'Ext.data.Store',
    model:'Foss.writeoff.receivableStatementModel',
    //是否自动查询
    autoLoad: false,
    pageSize:20,
    proxy: {
        //代理的类型为内存代理
        type: 'ajax',
        //提交方式
        actionMethods:'POST',
        url:writeoff.realPath('queryReceivableStatement.action'),
        //定义一个读取器
        reader: {
            //以JSON的方式读取
            type: 'json',
            //定义读取JSON数据的根对象
            root: 'statementRecivaleVo.statementRecivableDto.billReceivableEntityList',
            //返回总数
            totalProperty : 'totalCount'
        }
    },
    listeners:{
        beforeload : function(store, operation, eOpts){//查询事件
            //查询条件form中的值
            var queryParams;
            var params;
            var waybillNoList = new Array();       //订单号的集合
            var flowType = writeoff.receivableStatement.detailAdd.getActiveTab().itemId;
            if(flowType=='detailProducedByStatementPartnerId'){
                queryParams =   writeoff.receivableStatement.producedByPartnerAddForm.getValues();
                params={
                    'statementRecivaleVo.statementRecivableDto.businessStartDate': queryParams.periodBeginDate,
                    'statementRecivaleVo.statementRecivableDto.businessEndDate': queryParams.periodEndDate,
                    'statementRecivaleVo.statementRecivableDto.contractOrgName':queryParams.customerName,
                    'statementRecivaleVo.statementRecivableDto.contractOrgCode':queryParams.customerCode
                }
            }
            if(flowType=='detailProductionStatementOrderId'){
                queryParams = writeoff.receivableStatement.productionOrderAddForm.getValues();
                waybillNoList = queryParams.waybillNo.split(",");
                params={ 'statementRecivaleVo.statementRecivableDto.waybillNoList': waybillNoList}
            }
            Ext.apply(operation, {
                params :params
            });
        },
        load : function(store, records, successful, eOpts){//表格数据加载事件
            var data = store.getProxy().getReader().rawData;
            if(data==undefined){
                //"温馨提示","系统错误"
                Ext.MessageBox.alert(writeoff.receivableStatement.i18n('foss.stl.writeoff.common.alert'),writeoff.receivableStatement.i18n('foss.stl.writeoff.partnerPayStatementEdit.serverErrTryAgain'));
                return ;
            }
            if(data.success==false){
                Ext.MessageBox.alert(writeoff.receivableStatement.i18n('foss.stl.writeoff.common.alert'),data.message);
                return ;
            }
            //总条数
            if(data.totalCount!=null) {
                Ext.getCmp('Foss.detail.receivableStatement.totalRows').setValue(data.totalCount);
            }else{
                Ext.getCmp('Foss.detail.receivableStatement.totalRows').setValue('0');
            }
        }
    }
});


/**
 * 添加对账单明细grid
 */
Ext.define('Foss.writeoff.datile.receivableStatementGrid',{
    extend: 'Ext.grid.Panel',
    title: writeoff.receivableStatement.i18n('foss.stl.writeoff.statementCommon.statementEntry'), //对账单明细
    frame: true,
    height: 600,
    //selModel: Ext.create('Ext.selection.CheckboxModel'),
    //store: Ext.create('Foss.receivableStatement.detailStore'),
    bodyCls: 'autoHeight',
    cls: 'autoHeight',
    defaults: {
        align: 'center',
        margin: '5 0 5 0'
    },
    viewConfig: {
        enableTextSelection: true//设置行可以选择，进而可以复制
    },
    dockedItems: [{
        xtype: 'toolbar',
        dock: 'bottom',
        layout:'column',
        items: [{
            xtype:'textfield',
            readOnly:true,
            name:'totalRows',
            id:'Foss.detail.receivableStatement.totalRows',
            columnWidth:.9,
            labelWidth:60,
            fieldLabel:writeoff.receivableStatement.i18n('foss.stl.writeoff.statementAdd.totalCount')    //总条数
        }]
    }],
    columns: [{
        xtype: 'actioncolumn',
        width: 50,
        text: writeoff.receivableStatement.i18n('foss.stl.writeoff.common.actionColumn'),//操作列
        align: 'center',
        items: [{
            iconCls: 'statementBill_add',
            tooltip:writeoff.receivableStatement.i18n('foss.stl.writeoff.woodenStatementEdit.addWoodenStatementD'),//添加
            handler: function (grid, rowIndex, colIndex) {
                //获取对账单号
               var  statementBillNo =   writeoff.receivableStatement.producedByPartnerAddForm.getValues().settmentNo;
                var selection = grid.getStore().getAt(rowIndex);
                var receivableNo;
                for (var i = 0; i < selection.stores.length; i++) {
                    if(selection.store.getAt(rowIndex).data.id!=null){
                        receivableNo = selection.store.getAt(rowIndex).data.receivableNo;
                    }
                }
                //调用后台的方法
                Ext.Ajax.request({
                    url:writeoff.realPath('addReceivableStatementDetail.action'),
                    jsonData:{
                        'statementRecivaleVo':{
                            'statementRecivableDto':{
                                'statementBillNo':statementBillNo,
                                'receivableNo':receivableNo
                            }
                        }
                    },
                    success:function(response){
                        grid.store.load();
                        writeoff.receivableStatement.detail.store.load();
                        writeoff.receivableStatement.grid.store.load();
                        //温馨提示","添加成功"
                        Ext.ux.Toast.msg(writeoff.receivableStatement.i18n('foss.stl.writeoff.common.alert'),writeoff.receivableStatement.i18n('foss.stl.writeoff.partnerPayStatementEdit.addSuccess'), 'ok', 1000);
                    },
                    exception:function(response){
                        var result = Ext.decode(response.responseText);
                        Ext.Msg.alert(writeoff.receivableStatement.i18n('foss.stl.writeoff.common.alert'),result.message);
                    }
                });
            }
        }]
    },{
            header:writeoff.receivableStatement.i18n('foss.stl.writeoff.common.businessDate'),//业务日期
            dataIndex:'businessDate',
            xtype : 'datecolumn',
            format : 'Y-m-d H:i:s'
        },{
            header:writeoff.receivableStatement.i18n('foss.stl.writeoff.common.waybillNo'),//运单号
            dataIndex:'waybillNo'
        },{
            header:writeoff.receivableStatement.i18n('foss.stl.writeoff.common.receivableNoCol'),//应收单号
            dataIndex:'receivableNo',
            hidden:true
        },{
            header:writeoff.receivableStatement.i18n('foss.stl.writeoff.statementAdd.arrvRegionCode'),//目的站
            dataIndex:'targetOrgCode'
        },{
            header:writeoff.receivableStatement.i18n('foss.stl.writeoff.statementAdd.productCode'),//产品类型
            dataIndex:'proType',
            renderer:writeoff.receivableStatement.productCode
        },{
            header:writeoff.receivableStatement.i18n('foss.stl.writeoff.statementAdd.qty'),//件数
            dataIndex:'goodsQtyTotal'
        },{
            header:writeoff.receivableStatement.i18n('foss.stl.writeoff.statementAdd.billWeight'),//计费重量
            dataIndex:'billWeight'
        },{
            header:writeoff.receivableStatement.i18n('foss.stl.writeoff.statementAdd.billingVolume'),//计费体积
            dataIndex:'goodsVolumeTotal'
        },{
            header:writeoff.receivableStatement.i18n('foss.stl.writeoff.statementAdd.receiveMethod'),//提货方式
            dataIndex:'receiveMethod',
            renderer:writeoff.receivableStatement.receiveMethod
        },{
            header:writeoff.receivableStatement.i18n('foss.stl.writeoff.statementAdd.paymentType'),//付款方式
            dataIndex:'paymentType',
            renderer:writeoff.receivableStatement.paymentType
        },{
            header:writeoff.receivableStatement.i18n('foss.stl.writeoff.common.originalUnverifyAmount'),//原始未核销金额
            dataIndex:'unverifyAmount'
        },{
            header:writeoff.receivableStatement.i18n('foss.stl.writeoff.common.totalAmount'),//总金额
            dataIndex:'amount'
        },{
            header:writeoff.receivableStatement.i18n('foss.stl.writeoff.common.sourceBillNo'),//来源单据编号
            dataIndex:'sourceBillNo'
        },{
            header:writeoff.receivableStatement.i18n('foss.stl.writeoff.common.billType'),//单据子类型
            dataIndex:'billType',
            renderer:writeoff.receivableStatement.billType
        },{
            header:writeoff.receivableStatement.i18n('foss.stl.writeoff.common.customerCode'),//客户编码
            dataIndex:'customerCode'
        },{
            header:writeoff.receivableStatement.i18n('foss.stl.writeoff.common.customerName'),//客户名称
            dataIndex:'customerName'
        },{
            header:writeoff.receivableStatement.i18n('foss.stl.writeoff.common.verifyAmount'),//已核销金额
            dataIndex:'verifyAmount'
        },{
            header:writeoff.receivableStatement.i18n('foss.stl.writeoff.woodenStatementEdit.createOrgCode'),//部门编码
            dataIndex:'createOrgName'
        },{
            header:writeoff.receivableStatement.i18n('foss.stl.writeoff.woodenStatementEdit.createOrgName'),//部门名称
            dataIndex:'createOrgCode'
        },{
            header:writeoff.receivableStatement.i18n('foss.stl.writeoff.common.accountDate'),//记账日期
            dataIndex:'accountDate',
            xtype : 'datecolumn',
            format : 'Y-m-d H:i:s'
        },{
            header:writeoff.receivableStatement.i18n('foss.stl.writeoff.statementAdd.customerPickupOrgName'),//提货网点
            dataIndex:'customerPickupOrgCode'
        },{
            header:writeoff.receivableStatement.i18n('foss.stl.writeoff.statementAdd.goodsName'),//货物名称
            dataIndex:'goodsName'
        },{
            header:writeoff.receivableStatement.i18n('foss.stl.writeoff.statementAdd.origOrgCode'),//始发网点编码
            dataIndex:'origOrgCode'
        },{
            header:writeoff.receivableStatement.i18n('foss.stl.writeoff.statementAdd.origOrgName'),//始发网点名称
            dataIndex:'origOrgName'
        },{
            header:writeoff.receivableStatement.i18n('foss.stl.writeoff.statementAdd.destOrgName'),//到达部门名称
            dataIndex:'destOrgCode'
        },{
            header:writeoff.receivableStatement.i18n('foss.stl.writeoff.statementAdd.destOrgCode'),//到达部门编码
            dataIndex:'destOrgName'
        },{
            header:writeoff.receivableStatement.i18n('foss.stl.writeoff.statementAdd.deliveryCustomerCode'),//发货客户编码
            dataIndex:'deliveryCustomerCode'
        },{
            header:writeoff.receivableStatement.i18n('foss.stl.writeoff.statementAdd.signDate'),//签收日期
            dataIndex:'conrevenDate',
            xtype : 'datecolumn',
            format : 'Y-m-d H:i:s'
        },{
            header:writeoff.receivableStatement.i18n('foss.stl.writeoff.common.approveStatus'),//审核状态
            dataIndex:'statementStatus'
        },{
            header:writeoff.receivableStatement.i18n('foss.stl.writeoff.common.notes'),//备注
            dataIndex:'notes'
        },{
            header:writeoff.receivableStatement.i18n('foss.stl.writeoff.statementAdd.isDelete'),//是否删除
            dataIndex:'isDelete'
        },{
            header:writeoff.receivableStatement.i18n('foss.stl.writeoff.receivableWriteOffPayble.statementNo'),//对账单号
            dataIndex:'statementBillNo'
        },{
            header:writeoff.receivableStatement.i18n('foss.stl.writeoff.common.createTime'),//运单开单时间
            dataIndex:'createTime',
            xtype : 'datecolumn',
            format : 'Y-m-d H:i:s'
        }],
        initComponent:function(config) {
        var me = this;
        cfg = Ext.apply({}, config);
        me.store = Ext.create('Foss.writeoff.detail.receivableStatementStore');
        //自定义分页控件
        me.bbar = Ext.create('Deppon.StandardPaging',{
            store : me.store,
            pageSize : 20,
            maximumSize : 100,
            plugins : Ext.create('Deppon.ux.PageSizePlugin', {
                sizeList : [['20', 20], ['50', 50],  ['100', 100]]
            })
        });
        writeoff.receivableStatement.messageGrid =me.bbar;
        me.callParent([cfg]);
    }
});

/**
 * 添加到对账单明细页面的查询tab
 */
Ext.define('Foss.statementbill.receivable.QueryInfoTab',{
    extend:'Ext.tab.Panel',
    frame : false,
    bodyCls : 'autoHeight',
    cls : 'innerTabPanel',
    activeTab : 0,
    columnWidth: 1,
    //columnHeight: 'autoHeight',
    height:180,
    items: [{
        title:writeoff.receivableStatement.i18n('foss.statementbill.MakeByCustomer'),   //按客户制作
        itemId:'detailProducedByStatementPartnerId',
        tabConfig : {
            width : 120
        },
        layout : 'fit',
        items : [
            writeoff.receivableStatement.producedByPartnerAddForm
        ]
    },{
        title:writeoff.receivableStatement.i18n('foss.stl.writeoff.statementAdd.makeByNumber'),//按单号制作
        itemId:'detailProductionStatementOrderId',
        tabConfig : {
            width : 120
        },
        layout : 'fit',
        items : [
            writeoff.receivableStatement.productionOrderAddForm
        ]
    }]

});


/**
 * 还款单页面的对账单信息
 */
Ext.define('Foss.statementbill.RepaymentStatementForm', {
    extend: 'Ext.form.Panel',
    title: writeoff.receivableStatement.i18n('foss.stl.writeoff.statementCommon.statementInfo'),//对账单信息
    frame: true,
    layout: {
        type: 'table',
        columns: 3
    },
    defaultType: 'textfield',
    defaults: {
        readOnly: true,
        labelWidth: 80,
        width: 240
    },
    items: [{
        fieldLabel: writeoff.receivableStatement.i18n('foss.stl.writeoff.partnerPayStatementEdit.partnerName'),//合伙人名称
        style: 'margin-left:100px',
        name: 'customerName',
        colspan: 2
    }, {
        fieldLabel: writeoff.receivableStatement.i18n('foss.stl.writeoff.partnerPayStatementEdit.customerCode'),//合伙人编码
        name: 'customerCode'
    },{
        colspan: 3
    }, {
        fieldLabel: writeoff.receivableStatement.i18n('foss.stl.writeoff.statementCommon.statementBillNo'),//账单编号
        name: 'statementBillNo',
        style: 'margin-left:100px',
        colspan: 3
    }, {
        fieldLabel: writeoff.receivableStatement.i18n('foss.stl.writeoff.statementAdd.periodAmount'),//本期发生金额
        style: 'margin-left:100px',
        labelWidth: 95,
        xtype: 'numberfield',
        name: 'currentAmount',
        decimalPrecision: 2,
        colspan: 2
    }, {
        fieldLabel:writeoff.receivableStatement.i18n('foss.stl.writeoff.statementCommon.currentRemainAmount'),//剩余未还金额
        name: 'currentRemainAmount',
        labelWidth: 95,
        xtype: 'numberfield',
        decimalPrecision: 2
    }, {
        xtype: 'textareafield',
        name: 'notes',
        fieldLabel:writeoff.receivableStatement.i18n('foss.stl.writeoff.statementCommon.billDescription'),//对账单描述
        autoScroll: true,
        style: 'margin-left:100px',
        format: 'Y-m-d',
        readOnly: false,
        colspan: 3,
        width: (stl.SCREENWIDTH * 0.7 - 180) * 3 / 4
    }]
});

//还款方法
writeoff.receivableStatement.statementRepaymentComplete=function(repaymentForm){

    var statementRecivaleVo,
        billStatementToPaymentQueryDto;
    //还款单FORM
    var form=repaymentForm.getForm();
    if(form.isValid()){
        //处理对账单号
       // var grid = Ext.getCmp('T_writeoff-receivableStatement_content').getGrid();
        //还款页面
       var repaymentStatementForm =  writeoff.receivableStatement.RepaymentStatementForm.getForm() ;
      //  writeoff.receivableStatement.RepaymentForm = Ext.create('Foss.statementbill.RepaymentForm');
        //对账单单号
        var statementBillNo= repaymentStatementForm.findField('statementBillNo').getValue();
        var statementBillNos = [];
        statementBillNos = statementBillNo.split(",");


        //设置对账单参数
        billStatementToPaymentQueryDto = new Object();
        billStatementToPaymentQueryDto.customerCode = repaymentStatementForm.findField('customerCode').getValue();
        billStatementToPaymentQueryDto.customerName = repaymentStatementForm.findField('customerName').getValue();
        billStatementToPaymentQueryDto.repaymentType = form.findField('repaymentType').getValue();
        billStatementToPaymentQueryDto.repaymentAmount = form.findField('repaymentAmount').getValue();
        billStatementToPaymentQueryDto.description = form.findField('description').getValue();
        billStatementToPaymentQueryDto.remittanceNumber = form.findField('remittanceNumber').getValue();
        billStatementToPaymentQueryDto.remittanceName = form.findField('remittanceName').getValue();
        billStatementToPaymentQueryDto.statementBillNos = statementBillNos;
        statementRecivaleVo = new Object();
        statementRecivaleVo.billStatementToPaymentQueryDto = billStatementToPaymentQueryDto;
        //将数据传递给后台
        Ext.Ajax.request({
            url:writeoff.realPath('partnerRepayment.action'),
            actionMethods:'POST',
            jsonData:{
                'statementRecivaleVo':statementRecivaleVo
            },
            success:function(response){
                //获取返回结果
                var result = Ext.decode(response.responseText);
                //获取付款单号
                var repaymentNo = result.statementRecivaleVo.billRepaymentManageDto.repaymentNo;
                //'温馨提示', '还款单提交成功,还款单号
                Ext.Msg.alert(writeoff.receivableStatement.i18n('foss.stl.writeoff.common.alert'),writeoff.receivableStatement.i18n('foss.stl.writeoff.woodenStatementEdit.addRepaymentSuccess')+repaymentNo);
                //关闭窗口
                if(writeoff.receivableStatement.win){
                    writeoff.receivableStatement.win.close();
                }
               if(writeoff.receivableStatement.detail.win){
                   writeoff.receivableStatement.win.close();
               }
                writeoff.receivableStatement.grid.store.load();
            },
            exception:function(response){
                var result = Ext.decode(response.responseText);
                Ext.Msg.alert(writeoff.receivableStatement.i18n('foss.stl.writeoff.common.alert'),result.message);
            }
        });
    }else{
        //'温馨提示', '请检查输入条件是否合法!
        Ext.Msg.alert(writeoff.receivableStatement.i18n('foss.stl.writeoff.common.alert'),writeoff.receivableStatement.i18n('foss.stl.writeoff.woodenStatementEdit.validateFailAlert'));
    }
};
/**
 * 还款单信息
 */
Ext.define('Foss.statementbill.RepaymentForm', {
    extend: 'Ext.form.Panel',
    title: writeoff.receivableStatement.i18n('foss.stl.writeoff.statementCommon.repaymentInfo'),//还款单信息
    frame: true,
    layout: {
        type: 'table',
        columns: 3
    },
    defaultType: 'textfield',
    defaults: {
        labelWidth: 70,
        width: (stl.SCREENWIDTH * 0.7 - 180) / 3
    },
    items: [{
        xtype: 'combo',
        name: 'repaymentType',
        fieldLabel:writeoff.receivableStatement.i18n('foss.stl.writeoff.statementCommon.repaymentType'),//还款方式
        allowBlank: false,
        style: 'margin-left:100px',
        store: Ext.create('Ext.data.Store', {
        fields: ['valueName', 'valueCode'],
        data : [
            {"valueName":"电汇", "valueCode":"TT"}
           /* {"valueName":"现金", "valueCode":"CH"},
            {"valueName":"银行卡", "valueCode":"CD"},
            {"valueName":"支票", "valueCode":"NT"},
            {"valueName":"网上支付", "valueCode":"OL"}*/
            ]
         }),
        queryModel: 'local',
        displayField: 'valueName',
        valueField: 'valueCode',
        value: 'TT',
        editable: false,
         listeners: {
             'select': function (combo) {
                 if (combo.value == 'TT'|| combo.value =='NT'|| combo.value == 'OL') {

                     var currentRemainAmount = this.up('window').items.items[0].getForm().findField('currentRemainAmount').getValue();

                     this.up('form').getForm().findField('remittanceNumber').enable();
                     this.up('form').getForm().findField('remittanceNumber').labelEl.update('<span style="color:red;">*</span>' + '汇款编号' + ':');
                     this.up('form').getForm().findField('remittanceNumber').regex = '';
                     this.up('form').getForm().findField('remittanceNumber').regexText = '';

                     this.up('form').getForm().findField('repaymentAmount').setValue(currentRemainAmount);
                     this.up('form').getForm().findField('repaymentAmount').disable();

                 } else if (combo.value == 'CH') {
                     var currentRemainAmount = this.up('window').items.items[0].getForm().findField('currentRemainAmount').getValue();

                     this.up('form').getForm().findField('remittanceNumber').disable();
                     this.up('form').getForm().findField('remittanceNumber').labelEl.update('&nbsp;&nbsp;' + '汇款编号' + ':');
                     this.up('form').getForm().findField('remittanceNumber').setValue(null);
                     this.up('form').getForm().findField('remittanceName').setValue(null);
                     this.up('form').getForm().findField('remittanceDate').setValue(null);
                     this.up('form').getForm().findField('availableAmount').setValue(null);
                     this.up('form').getForm().findField('repaymentAmount').setValue(currentRemainAmount);
                     this.up('form').getForm().findField('repaymentAmount').enable();
                 } else if (combo.value == 'CD') {
                     this.up('form').getForm().findField('remittanceNumber').enable();
                     this.up('form').getForm().findField('remittanceNumber').labelEl.update('<span style="color:red;">*</span>' + '银联交易流水号' + ':');
                     this.up('form').getForm().findField('remittanceNumber').regex = /^\d*$/;
                     this.up('form').getForm().findField('remittanceNumber').regexText = '请输入银联交易流水号，格式为11位数字';

                     this.up('form').getForm().findField('repaymentAmount').setValue(currentRemainAmount);
                     this.up('form').getForm().findField('repaymentAmount').enable();
                 }
             }
         }
    }, {
        fieldLabel: '<span style="color:red;">*</span>' + '汇款编号',
        name: 'remittanceNumber',
        listeners: {
        'blur': function (th) {
        if (th.getValue() != null && th.getValue() != '') {
            var form = this.up('form').getForm()
            var repaymentAmount = form.findField('repaymentAmount').getValue();
            var repaymentType = form.findField('repaymentType').getValue();

            /** 付款方式为银行卡时不需要获取远程数据 */
            if(repaymentType == 'CD'){
                return false;
            }

            //设置参数体
            var billRepaymentManageVo,
                billStatementToPaymentQueryDto;
            billStatementToPaymentQueryDto = new Object();
            billRepaymentManageVo = new Object();
            billStatementToPaymentQueryDto.remittanceNumber = th.getValue();
            billStatementToPaymentQueryDto.repaymentType = repaymentType;
            billRepaymentManageVo.billStatementToPaymentQueryDto = billStatementToPaymentQueryDto;

            //调用后台接口根据输入汇款编号获取汇款人、汇款日期，汇款可用金额、
            Ext.Ajax.request({
                url: ContextPath.STL_WEB + '/pay/queryRemittanceDetail.action',
                jsonData: {
                    'billRepaymentManageVo': billRepaymentManageVo
                },
                method: 'post',
                success: function (response) {
                    var result = Ext.decode(response.responseText);


                    var remittanceDate = stl.dateFormat(result.billRepaymentManageVo.billStatementToPaymentResultDto.remittanceDate, 'Y-m-d');

                    form.findField('remittanceName').setValue(result.billRepaymentManageVo.billStatementToPaymentResultDto.remittanceName);
                    form.findField('remittanceDate').setValue(remittanceDate);
                    form.findField('availableAmount').setValue(result.billRepaymentManageVo.billStatementToPaymentResultDto.availableAmount);
                    //比较还款金额和当前电汇、支票的可用金额，显示两者中较小的
                    if (result.billRepaymentManageVo.billStatementToPaymentResultDto.availableAmount != null
                        && result.billRepaymentManageVo.billStatementToPaymentResultDto.availableAmount != '') {
                        if (result.billRepaymentManageVo.billStatementToPaymentResultDto.availableAmount < repaymentAmount) {
                            form.findField('repaymentAmount').setValue(result.billRepaymentManageVo.billStatementToPaymentResultDto.availableAmount);
                        }
                    }
                },
                exception: function (response) {
                    var result = Ext.decode(response.responseText);
                    Ext.Msg.alert(writeoff.receivableStatement.i18n('foss.stl.writeoff.common.alert'), result.message);
                    form.findField('remittanceNumber').setValue(null);
                }
            });
        }
    }
}
    }, {
        fieldLabel:writeoff.receivableStatement.i18n('foss.stl.writeoff.statementCommon.remittanceName'),//汇款人名称
        name: 'remittanceName',
        labelWidth: 90,
        disabled: true
    }, {
        fieldLabel:writeoff.receivableStatement.i18n('foss.stl.writeoff.statementCommon.repaymentAmount'),//还款金额
        style: 'margin-left:100px',
        name: 'repaymentAmount',
        disabled: true,
        xtype: 'numberfield',
        allowBlank: false
    }, {
        fieldLabel: writeoff.receivableStatement.i18n('foss.stl.writeoff.statementCommon.remittanceDate'),//到账日期
        name: 'remittanceDate',
        format: 'Y-m-d',
        xtype: 'datefield',
        disabled: true
    }, {
        fieldLabel:writeoff.receivableStatement.i18n('foss.stl.writeoff.statementCommon.availableAmount'),//可用金额
        name: 'availableAmount',
        disabled: true,
        labelWidth: 90
    }, {
        xtype: 'textareafield',
        name: 'description',
        fieldLabel: writeoff.receivableStatement.i18n('foss.stl.writeoff.statementCommon.description'),//备注
        autoScroll: true,
        style: 'margin-left:100px',
        format: 'Y-m-d',
        colspan: 3,
        width: (stl.SCREENWIDTH * 0.7 - 180) * 2 / 3
    }, {
        fieldLabel:writeoff.receivableStatement.i18n('foss.stl.writeoff.statementCommon.statementBillNo'),//账单编号
        name: 'statementBillNo',
        hidden: true
    }, {
        fieldLabel:writeoff.receivableStatement.i18n('foss.stl.writeoff.woodenStatementEdit.versionNo'),//版本号
        name: 'versionNos'
        , hidden: true
    }, {
        fieldLabel: writeoff.receivableStatement.i18n('foss.stl.writeoff.woodenStatementEdit.customerCode'),//客户编码
        name: 'customerCode',
        hidden: true
    }, {
        fieldLabel: writeoff.receivableStatement.i18n('foss.stl.writeoff.woodenStatementEdit.customerName'),//客户名称
        name: 'customerName',
        hidden: true
    }, {
        xtype: 'container',
        colspan: 3,
        width: 660,
        style: 'margin-left:100px',
        defaultType: 'button',
        layout: 'table',
        items: [{
            xtype: 'container',
            html: '&nbsp;',
            width: 180
        }, {
            text: writeoff.receivableStatement.i18n('foss.stl.writeoff.statementCommon.return'),//返回
            width: 70,
            cls: 'yellow_button',
            handler: function () {
                this.up('form').up('panel').hide();
            }
        }, {
            text:writeoff.receivableStatement.i18n('foss.stl.writeoff.statementEdit.repayment'),//还款
            width: 70,
            style: 'margin-left:20px',
            cls: 'yellow_button',
            handler: function () {
                var form = this.up('form');
                var me = this;
                var currentRemainAmount = this.up('window').items.items[0].getForm().findField('currentRemainAmount').getValue();
                var repaymentAmount = form.getForm().findField('repaymentAmount').getValue();
                var availableAmount = form.getForm().findField('availableAmount').getValue();
                var repaymentType = form.getForm().findField('repaymentType').getValue();
                var remittanceNumber = form.getForm().findField('remittanceNumber').getValue();
                var repaymentTypeStr = writeoff.receivableStatement.i18n('foss.stl.writeoff.statementCommon.ticket');//电汇
                var description = this.up('window').items.items[0].getForm().findField('notes').getValue();

                if (repaymentAmount == null || repaymentAmount <= 0) {
                    //温馨提示', '还款金额不能小于0!
                    Ext.Msg.alert(writeoff.receivableStatement.i18n('foss.stl.writeoff.common.alert'), writeoff.receivableStatement.i18n('foss.stl.writeoff.statementCommon.repaymentAmountMin'));
                    return false;
                }
                if (repaymentAmount > currentRemainAmount) {
                   //温馨提示', '还款金额不能大于本期未还金额!
                    Ext.Msg.alert(writeoff.receivableStatement.i18n('foss.stl.writeoff.common.alert'), writeoff.receivableStatement.i18n('foss.stl.writeoff.statementCommon.repaymentAmountMax'));
                    return false;
                }
                if (Ext.isEmpty(repaymentType)) {
                    //温馨提示', '付款方式不可以为空
                    Ext.Msg.alert(writeoff.receivableStatement.i18n('foss.stl.writeoff.common.alert'), writeoff.receivableStatement.i18n('foss.stl.writeoff.statementCommon.repaymentTypeIsNull'));
                    return false;
                }
                if (repaymentType == 'TT') {
                    repaymentTypeStr = writeoff.receivableStatement.i18n('foss.stl.writeoff.statementCommon.ticket');//电汇
                    if (remittanceNumber == null || remittanceNumber == '') {
                        //温馨提示', '汇款编号错误，不能进行电汇/支票还款!
                        Ext.Msg.alert(writeoff.receivableStatement.i18n('foss.stl.writeoff.common.alert'),writeoff.receivableStatement.i18n('foss.stl.writeoff.statementCommon.checkRemittanceNumberIsNull'));
                        return false;
                    }
                    if (repaymentAmount > availableAmount) {
                        //'温馨提示', '使用电汇还款时,电汇可用金额不足，无法还款!
                        Ext.Msg.alert(writeoff.receivableStatement.i18n('foss.stl.writeoff.common.alert'),writeoff.receivableStatement.i18n('foss.stl.writeoff.statementCommon.ticketRepaymentAmountMax'));
                        return false;
                    }
                }
                if (repaymentType == 'OL') {
                    repaymentTypeStr = writeoff.receivableStatement.i18n('foss.stl.writeoff.statementCommon.online');//网上支付
                    if (remittanceNumber == null || remittanceNumber == '') {
                      //  网上支付编号错误，不能进行还款!
                        Ext.Msg.alert(writeoff.receivableStatement.i18n('foss.stl.writeoff.common.alert'), writeoff.receivableStatement.i18n('foss.stl.writeoff.statementCommon.checkOnlineRemittanceNumberIsNull'));
                        return false;
                    }
                    if (repaymentAmount > availableAmount) {
                        ///温馨提示', '使用网上支付还款时,可用金额不足，无法还款
                        Ext.Msg.alert(writeoff.receivableStatement.i18n('foss.stl.writeoff.common.alert'), writeoff.receivableStatement.i18n('foss.stl.writeoff.statementCommon.onlineRepaymentAmountMax'));
                        return false;
                    }
                }
                if (repaymentType == 'NT') {
                    repaymentTypeStr =writeoff.receivableStatement.i18n('foss.stl.writeoff.statementCommon.check');//支票
                    if (remittanceNumber == null || remittanceNumber == '') {
                        //温馨提示', '汇款编号错误，不能进行电汇/支票还款!
                        Ext.Msg.alert(writeoff.receivableStatement.i18n('foss.stl.writeoff.common.alert'), writeoff.receivableStatement.i18n('foss.stl.writeoff.statementCommon.checkRemittanceNumberIsNull'));
                        return false;
                    }
                    if (repaymentAmount > availableAmount) {
                        //温馨提示', '使用支票还款时,电汇可用金额不足，无法还款!'
                        Ext.Msg.alert(writeoff.receivableStatement.i18n('foss.stl.writeoff.common.alert'), writeoff.receivableStatement.i18n('foss.stl.writeoff.statementCommon.checkRepaymentAmountMax'));
                        return false;
                    }
                }
                if (repaymentType == 'CH') {
                    repaymentTypeStr = writeoff.receivableStatement.i18n('foss.stl.writeoff.statementCommon.cash');//现金
                }
                if (repaymentType == 'CD') {
                    repaymentTypeStr = writeoff.receivableStatement.i18n('foss.stl.writeoff.statementCommon.bankcard');//银行卡
                    if (remittanceNumber == null || remittanceNumber == '') {
                        //'温馨提示', '银联交易流水号编号错误，不能进行银行卡还款!
                        Ext.Msg.alert(writeoff.receivableStatement.i18n('foss.stl.writeoff.common.alert'), writeoff.receivableStatement.i18n('foss.stl.writeoff.statementCommon.checkBatchNumberIsNull'));
                        return false;
                    }
                }
                if (repaymentAmount < currentRemainAmount && description == "") {
                    //温馨提示', '请先填写对账单描述信息
                    Ext.Msg.alert(writeoff.receivableStatement.i18n('foss.stl.writeoff.common.alert'), writeoff.receivableStatement.i18n('foss.stl.writeoff.statementCommon.takeBillDescription'));
                    return false;
                }

                if (repaymentAmount < currentRemainAmount && description != "") {
                    Ext.MessageBox.show({
                        title:writeoff.receivableStatement.i18n('foss.stl.writeoff.common.alert'),//温馨提示
                        msg:writeoff.receivableStatement.i18n('foss.stl.writeoff.statementCommon.currentRepaymentType')+                                  //当前还款方式为
                        repaymentTypeStr+writeoff.receivableStatement.i18n('foss.stl.writeoff.statementCommon.repaymentAlert'),//且没有全额还款,是否继续还款？
                        buttons:Ext.MessageBox.YESCANCEL,
                        buttonText:{
                            yes: writeoff.receivableStatement.i18n('foss.stl.writeoff.common.ok'),//确定
                            cancel:writeoff.receivableStatement.i18n('foss.stl.writeoff.common.cancel')//取消
                        },
                        fn: function(e){
                            if(e=='yes'){
                                //设置该按钮灰掉
                                me.disable(false);
                                //10秒后自动解除灰掉效果
                                setTimeout(function() {
                                    me.enable(true);
                                }, 10000);
                                writeoff.receivableStatement.statementRepaymentComplete(form);
                            }else if(e=='cancel'){
                                return false;
                            }
                        }
                    });

                } else if (repaymentAmount == currentRemainAmount) {
                    Ext.MessageBox.show({
                        title:writeoff.receivableStatement.i18n('foss.stl.writeoff.common.alert'),
                        msg:writeoff.receivableStatement.i18n('foss.stl.writeoff.statementCommon.currentRepaymentType')+
                        repaymentTypeStr+writeoff.receivableStatement.i18n('foss.stl.writeoff.statementCommon.isRepaymentAlert'),
                        buttons:Ext.MessageBox.YESCANCEL,
                        buttonText:{
                            yes: writeoff.receivableStatement.i18n('foss.stl.writeoff.common.ok'),//确定
                            cancel:writeoff.receivableStatement.i18n('foss.stl.writeoff.common.cancel')//取消
                        },
                        fn: function(e){
                            if(e=='yes'){
                                //设置该按钮灰掉
                                me.disable(false);
                                //10秒后自动解除灰掉效果
                                setTimeout(function() {
                                    me.enable(true);
                                }, 10000);
                                writeoff.receivableStatement.statementRepaymentComplete(form);
                            }else if(e=='cancel'){
                                return false;
                            }
                        }
                    });
                }
            }
        }]
    }]
});




/**
 * 对账单明细记录
 */
Ext.define('Foss.receivableStatement.StatementEntryEditGrid', {
    extend: 'Ext.grid.Panel',
    title:  writeoff.receivableStatement.i18n('foss.stl.writeoff.statementCommon.statementEntry'),//对账单明细
    frame: true,
    height: 600,
    selModel: Ext.create('Ext.selection.CheckboxModel'),
    store: Ext.create('Foss.receivableStatement.detailStore'),
    bodyCls: 'autoHeight',
    cls: 'autoHeight',
    defaults: {
        align: 'center',
        margin: '5 0 5 0'
    },
    viewConfig: {
        enableTextSelection: true//设置行可以选择，进而可以复制
    },
    dockedItems: [{
        xtype: 'toolbar',
        dock: 'top',
        layout: 'column',
        items: [{
            xtype: 'container',
            border: false,
            html: '&nbsp;',
            columnWidth: .9
        }, {
            xtype: 'button',
            text: writeoff.receivableStatement.i18n('foss.stl.writeoff.statementCommon.addStatementEntry'),//添加对账单明细
            columnWidth: .1,
            handler: function () {
                //如果对账单为已确认，则弹出提示
                var statu =Ext.getCmp('Foss.receivableStatement.datil.confirmStatus').getValue();
               if (statu=='Y') {
                   //'对账单已经确认，不能添加明细
                    Ext.Msg.alert(writeoff.receivableStatement.i18n('foss.stl.writeoff.common.alert'),writeoff.receivableStatement.i18n('foss.stl.writeoff.statementCommon.auditToAddStatementEntryWarning'));
                    return false;
                }
                //获得对账单添加明细需要的信息
               var form =  writeoff.receivableStatement.producedByPartnerAddForm.form;
                //设置对账单单号
                form.findField('settmentNo').setValue(Ext.getCmp('Foss.receivableStatement.datil.statementBillNo').getValue());
                //设置客户名称
                form.findField('customerName').setValue(Ext.getCmp('Foss.receivableStatement.datil.contractOrgName').getValue());
                //设置客户编码
                form.findField('customerCode').setValue(Ext.getCmp('Foss.receivableStatement.datil.contractOrgCode').getValue());
                //弹出添加明细窗口
                var win= Ext.create('Ext.window.Window',{
                    width: stl.SCREENWIDTH * 0.9,
                    modal: true,
                    constrainHeader: true,
                    closeAction: 'destory',
                    items: [writeoff.receivableStatement.detailAdd,writeoff.receivableStatement.Message]
                });
                win.show();

            }
        }]
    }, {
        xtype: 'toolbar',
        dock: 'bottom',
        layout: 'column',
        items: [/*{
            xtype: 'button',
            text: 'X ' + '删除',
            columnWidth: .06,
            handler: function () {
                var statu =Ext.getCmp('Foss.receivableStatement.datil.confirmStatus').getValue();
                if (statu=='Y') {
                    Ext.Msg.alert('温馨提示','对账单已经确认，不能添加明细！');
                    return false;
                }
               shanchu();
            }
        },*/ {
            xtype: 'textfield',
            readOnly: true,
            name: 'totalRows',
            columnWidth: 1,
            id:'Foss.mingxi.totalCount',
            labelWidth: 60,
            fieldLabel: writeoff.receivableStatement.i18n('foss.stl.writeoff.statementAdd.totalCount') //总条数
        }]
    }],
    columns: [{
        xtype: 'actioncolumn',
        width: 50,
        text: writeoff.receivableStatement.i18n('foss.stl.writeoff.common.actionColumn'),//操作列
        align: 'center',
        items: [{
            iconCls: 'statementBill_remove',
            tooltip: writeoff.receivableStatement.i18n('foss.stl.writeoff.statementAdd.delete'),//删除
            handler: function (grid, rowIndex, colIndex) {
               var selection = grid.getStore().getAt(rowIndex);
                var array = new Array();
                for (var i = 0; i < selection.stores.length; i++) {
                    if(selection.store.getAt(rowIndex).data.id!=null){
                        array.push(selection.store.getAt(rowIndex).data.id);
                    }
                }
                //调用后台的方法
                Ext.Ajax.request({
                    url:writeoff.realPath('deleteReceivableStatementById.action'),
                    jsonData:{
                        'statementRecivaleVo':{
                            'statementRecivableDto':{
                                'statementBillNoList':array
                            }
                        }
                    },
                    success:function(response){
                        writeoff.receivableStatement.detail.store.load();
                        writeoff.receivableStatement.grid.store.load();
                        Ext.ux.Toast.msg(writeoff.receivableStatement.i18n('foss.stl.writeoff.common.alert'),writeoff.receivableStatement.i18n('foss.stl.writeoff.statementCommon.deleteSuccess'), 'ok', 1000);
                    },
                    exception:function(response){
                        var result = Ext.decode(response.responseText);
                        Ext.Msg.alert(writeoff.receivableStatement.i18n('foss.stl.writeoff.common.alert'),result.message);
                    }
                });
            }
        }]
    }, {
        header: writeoff.receivableStatement.i18n('foss.stl.writeoff.statementAdd.businessDate'),//业务日期
        dataIndex: 'businessDate',
        renderer: function (value) {
            if (value != null) {
                return Ext.Date.format(new Date(value), 'Y-m-d');
            } else {
                return null;
            }
        }
    }, {
        header: writeoff.receivableStatement.i18n('foss.stl.writeoff.common.waybillNo'),//运单号
        dataIndex: 'waybillNo'
    }, {
        header: writeoff.receivableStatement.i18n('foss.stl.writeoff.statementAdd.arrvRegionCode'),//目的站
        dataIndex: 'arrvRegionCode'
    }, {
        header: writeoff.receivableStatement.i18n('foss.stl.writeoff.statementAdd.productCode'),//产品类型
        dataIndex: 'proType',
        renderer:writeoff.receivableStatement.productCode
       }, {
        header: writeoff.receivableStatement.i18n('foss.stl.writeoff.statementAdd.qty'),//件数
        dataIndex: 'qty'
    }, {
        header: writeoff.receivableStatement.i18n('foss.stl.writeoff.statementAdd.billingVolume'),//计费体积
        dataIndex: 'billingVolume'
    }, {
        header: writeoff.receivableStatement.i18n('foss.stl.writeoff.statementAdd.billWeight'),//计费重量
        dataIndex: 'billWeight'
    }, {
        header: writeoff.receivableStatement.i18n('foss.stl.writeoff.statementAdd.paymentType'),//付款方式
        dataIndex: 'paymentType',
        renderer:writeoff.receivableStatement.paymentType
    }, {
        header: writeoff.receivableStatement.i18n('foss.stl.writeoff.statementAdd.receiveMethod'),//提货方式
        dataIndex: 'receiveMethod',
        renderer:writeoff.receivableStatement.receiveMethod
    }, {
        header: writeoff.receivableStatement.i18n('foss.stl.writeoff.common.originalUnverifyAmount'),//原始未核销金额
        dataIndex: 'unverifyAmount'
    },{
        header: writeoff.receivableStatement.i18n('foss.stl.writeoff.common.totalAmount'),//总金额
        dataIndex: 'amount'
    }, {
        header:  writeoff.receivableStatement.i18n('foss.stl.writeoff.modifyBillWriteoffReverse.billNo'),//单号
        dataIndex: 'sourceBillNo'
    }, {
        header: writeoff.receivableStatement.i18n('foss.stl.writeoff.common.billType'),//单据子类型
        dataIndex: 'billType',
        renderer:writeoff.receivableStatement.billType
    }, {
        header: writeoff.receivableStatement.i18n('foss.stl.writeoff.woodenStatementEdit.customerCode'),//客户编码
        dataIndex: 'customerCode'
    }, {
        header:writeoff.receivableStatement.i18n('foss.stl.writeoff.woodenStatementEdit.customerName'),//客户名称
        dataIndex: 'customerName'//客户名称
    }, {
        header: writeoff.receivableStatement.i18n('foss.stl.writeoff.common.verifyAmount'),//已核销金额
        dataIndex: 'verifyAmount'
    }, {
        header: writeoff.receivableStatement.i18n('foss.stl.writeoff.woodenStatementEdit.createOrgCode'),//部门编码
        dataIndex: 'orgCode'
    }, {
        header: writeoff.receivableStatement.i18n('foss.stl.writeoff.woodenStatementEdit.createOrgName'),//部门名称
        dataIndex: 'orgName'
    }, {
        header: writeoff.receivableStatement.i18n('foss.stl.writeoff.common.accountDate'),//记账日期
        dataIndex: 'accountDate',
        renderer: function (value) {
            if (value != null) {
                return Ext.Date.format(new Date(value), 'Y-m-d');
            } else {
                return null;
            }
        }
    }, {
        header: writeoff.receivableStatement.i18n('foss.stl.writeoff.statementAdd.customerPickupOrgName'),//提货网点
        dataIndex: 'customerPickupOrgName'
    }, {
        header:writeoff.receivableStatement.i18n('foss.stl.writeoff.statementAdd.goodsName'),//货物名称
        dataIndex: 'goodsName'
    }, {
        header: writeoff.receivableStatement.i18n('foss.stl.writeoff.statementAdd.origOrgCode'),//始发网点编码
        dataIndex: 'origOrgCode'
    }, {
        header: writeoff.receivableStatement.i18n('foss.stl.writeoff.statementAdd.origOrgName'),//始发网点名称
        dataIndex: 'origOrgName'
    }, {
        header: writeoff.receivableStatement.i18n('foss.stl.writeoff.statementAdd.destOrgCode'),//到达部门编码
        dataIndex: 'destOrgCode'
    }, {
        header: writeoff.receivableStatement.i18n('foss.stl.writeoff.statementAdd.destOrgName'),//到达部门名称
        dataIndex: 'destOrgName'
    }, {
        header: writeoff.receivableStatement.i18n('foss.stl.writeoff.statementAdd.deliveryCustomerCode'),//发货客户编码
        dataIndex: 'deliveryCustomerCode'
    }, {
        header: writeoff.receivableStatement.i18n('foss.stl.writeoff.statementAdd.signDate'),//签收日期
        dataIndex: 'signDate',
        renderer: function (value) {
            if (value != null) {
                return Ext.Date.format(new Date(value), 'Y-m-d');
            } else {
                return null;
            }
        }
    }, {
        header:writeoff.receivableStatement.i18n('foss.stl.writeoff.common.approveStatus'),//审核状态
        dataIndex: 'auditStatus'
    }, {
        header: writeoff.receivableStatement.i18n('foss.stl.writeoff.statementCommon.description'),//备注
        dataIndex: 'notes'
    }, {
        header: writeoff.receivableStatement.i18n('foss.stl.writeoff.common.createTime'),//运单开单时间
        dataIndex: 'billBeginTime',
        renderer: function (value) {
            if (value != null) {
                return Ext.Date.format(new Date(value), 'Y-m-d');
            } else {
                return null;
            }
        }
    }, {
        header: writeoff.receivableStatement.i18n('foss.stl.writeoff.receivableStatement.singleOperationFee'),//开单操作费
        dataIndex: 'singleOperationFee'
    }, {
        header: writeoff.receivableStatement.i18n('foss.stl.writeoff.receivableStatement.packageFee'),//包装费提成
        dataIndex: 'packageFee'
    }, {
        header:writeoff.receivableStatement.i18n('foss.stl.writeoff.receivableStatement.insuranceFee'),//保价费提成
        dataIndex: 'insuranceFee'
    }, {
        header: writeoff.receivableStatement.i18n('foss.stl.writeoff.receivableStatement.disbursementFee'),//代收货款手续费提成
        dataIndex: 'disbursementFee'
    }, {
        header: writeoff.receivableStatement.i18n('foss.stl.writeoff.receivableStatement.deliveryFee'),//送货费提成（不含上楼）
        dataIndex: 'deliveryFee'
    }, {
        header: writeoff.receivableStatement.i18n('foss.stl.writeoff.receivableStatement.baseDeliveryFee'),//基础送货费提成
        dataIndex: 'baseDeliveryFee'
    }, {
        header:writeoff.receivableStatement.i18n('foss.stl.writeoff.receivableStatement.mattressOperationFee'),//床垫操作费提成
        dataIndex: 'mattressOperationFee'
    }, {
        header: writeoff.receivableStatement.i18n('foss.stl.writeoff.receivableStatement.agentDeclarationFee'),//代理报关费提成
        dataIndex: 'agentDeclarationFee'
    }, {
        header: writeoff.receivableStatement.i18n('foss.stl.writeoff.receivableStatement.removePackingFee'),//拆包装费提成
        dataIndex: 'removePackingFee'
    }, {
        header:writeoff.receivableStatement.i18n('foss.stl.writeoff.receivableStatement.registrationFee'),//登记费提成
        dataIndex: 'registrationFee'
    }, {
        header: writeoff.receivableStatement.i18n('foss.stl.writeoff.receivableStatement.parkingFee'),//停车费提成
        dataIndex: 'parkingFee'
    },{
        header: writeoff.receivableStatement.i18n('foss.stl.writeoff.receivableStatement.otherFee'),//其他费用提成
        dataIndex: 'otherFee'
    },{
        header: writeoff.receivableStatement.i18n('foss.stl.writeoff.receivableStatement.upstairsFee'),//送货上楼费提成
        dataIndex: 'upstairsFee'
    },{
        header: writeoff.receivableStatement.i18n('foss.stl.writeoff.receivableStatement.warehouseDeliveryFee'),//送货进仓费提成
        dataIndex: 'warehouseDeliveryFee'
    },{
        header: writeoff.receivableStatement.i18n('foss.stl.writeoff.receivableStatement.largeUpstairsFee'),//大件上楼费提成
        dataIndex: 'largeUpstairsFee'
    },{
        header: writeoff.receivableStatement.i18n('foss.stl.writeoff.receivableStatement.superLongFee'),//超远派送费提成
        dataIndex: 'superLongFee'
    },{
        header: writeoff.receivableStatement.i18n('foss.stl.writeoff.receivableStatement.singleReturnFee'),//签收单返回提成
        dataIndex: 'singleReturnFee'
    },{
        header: writeoff.receivableStatement.i18n('foss.stl.writeoff.common.createTime'),//创建时间
        dataIndex: 'createTime',
        renderer: function (value) {
            if (value != null) {
                return Ext.Date.format(new Date(value), 'Y-m-d');
            } else {
                return null;
            }
        }
    },{
        header: writeoff.receivableStatement.i18n('foss.stl.writeoff.receivableWriteOffPayble.statementNo'),//对账单单号
        dataIndex: 'billStatementNo'
    },{
            header: 'id',
            dataIndex: 'id',
            hidden: true
        }],
    initComponent:function(config) {
    var me = this;
    cfg = Ext.apply({}, config);
    writeoff.receivableStatement.datilStore =me.store;
    me.callParent([cfg]);
}
});

//确认方法
var queren=function(){
    //获取选中的数据
    var sel = writeoff.receivableStatement.grid.getSelectionModel().getSelection();
    var statu = 'Y';
    var array = new Array();
    if(sel.length>0){
        for(var i=0;i<sel.length;i++){
            array.push(sel[i].data.statementBillNo);
        }
    }else{
        var statementBillNo = Ext.getCmp('Foss.receivableStatement.datil.statementBillNo').getValue();
        array.push(statementBillNo);
    }
    //调用后台的方法
    Ext.Ajax.request({
        url:writeoff.realPath('updateStatementByStatementStatus.action'),
        jsonData:{
            'statementRecivaleVo':{
                'statementRecivableDto':{
                    'statementStatus':statu,
                    'statementBillNoList':array
                }
            }
        },
        success:function(response){
            Ext.getCmp('Foss.receivableStatement.datil.confirmStatus').setValue('Y');
            writeoff.receivableStatement.grid.store.load();
            //"温馨提示","更新成功"
            Ext.ux.Toast.msg(writeoff.receivableStatement.i18n('foss.stl.writeoff.common.alert'),writeoff.receivableStatement.i18n('foss.statementbill.Success'), 'ok', 1000);
        },
        exception:function(response){
            var result = Ext.decode(response.responseText);
            Ext.Msg.alert(writeoff.receivableStatement.i18n('foss.stl.writeoff.common.alert'),result.message);
        }
    });
};

//反确认方法
var fanqueren=function(){
    //获取选中的数据
    var sel = writeoff.receivableStatement.grid.getSelectionModel().getSelection();
    var statu = 'N';
    var array = new Array();
    if(sel.length>0){
        for(var i=0;i<sel.length;i++){
            array.push(sel[0].data.statementBillNo);
        }
    }else{
        var statementBillNo = Ext.getCmp('Foss.receivableStatement.datil.statementBillNo').getValue();
        array.push(statementBillNo);
    }
    //调用后台的方法
    Ext.Ajax.request({
        url:writeoff.realPath('updateStatementByStatementStatus.action'),
        jsonData:{
            'statementRecivaleVo':{
                'statementRecivableDto':{
                    'statementStatus':statu,
                    'statementBillNoList':array
                }
            }
        },
        success:function(response){
            Ext.getCmp('Foss.receivableStatement.datil.confirmStatus').setValue('N');
            writeoff.receivableStatement.grid.store.load();
            Ext.ux.Toast.msg(writeoff.receivableStatement.i18n('foss.stl.writeoff.common.alert'),writeoff.receivableStatement.i18n('foss.statementbill.Success'), 'ok', 1000);
        },
        exception:function(response){
            var result = Ext.decode(response.responseText);
            Ext.Msg.alert(writeoff.receivableStatement.i18n('foss.stl.writeoff.common.alert'),result.message);
        }
    });
};

//删除方法
var shanchu=function(){
    //获取选中的数据
    var sel = writeoff.receivableStatement.detail.getSelectionModel().getSelection();
    var array = new Array();
    if(sel.length>0){
        for(var i=0;i<sel.length;i++){
            array.push(sel[i].data.id);
        }
    }
    //调用后台的方法
    Ext.Ajax.request({
        url:writeoff.realPath('deleteReceivableStatementById.action'),
        jsonData:{
            'statementRecivaleVo':{
                'statementRecivableDto':{
                    'statementBillNoList':array
                }
            }
        },
        success:function(response){
            writeoff.receivableStatement.detail.store.load();
            //删除成功
            Ext.ux.Toast.msg(writeoff.receivableStatement.i18n('foss.stl.writeoff.common.alert'),writeoff.receivableStatement.i18n('foss.stl.writeoff.statementCommon.deleteSuccess'), 'ok', 1000);
        },
        exception:function(response){
            var result = Ext.decode(response.responseText);
            Ext.Msg.alert(writeoff.receivableStatement.i18n('foss.stl.writeoff.common.alert'),result.message);
        }
    });
};

//还款的方法
var huankuan=function(){

    //设置还款页面的值
    var queryParams = writeoff.receivableStatement.grid.getSelectionModel().selected;
    var form = writeoff.receivableStatement.RepaymentStatementForm.form;
    var array = new Array();
    //添加对账单号
    if(queryParams.length>0){
        for(var i=0;i<queryParams.length;i++){
            array.push(queryParams.items[i].data.statementBillNo);
        }
    }else{
        var statementBillNo = Ext.getCmp('Foss.receivableStatement.datil.statementBillNo').getValue();
        array=statementBillNo.split(",");
    }
    //调用后台的方法
    Ext.Ajax.request({
        url:writeoff.realPath('repaymentBillofStatementNo.action'),
        jsonData:{
            'statementRecivaleVo':{
                'statementRecivableDto':{
                    'statementBillNoList':array
                }
            }
        },
        success:function(response){
            var result = Ext.decode(response.responseText);
            //设置合伙人名称
            form.findField('customerName').setValue(result.statementRecivaleVo.statementRecivableDto.statementRecivableEntity.customerName);
            //设置合伙人编码
            form.findField('customerCode').setValue(result.statementRecivaleVo.statementRecivableDto.statementRecivableEntity.customerCode);
            //设置对账单单号
            form.findField('statementBillNo').setValue(result.statementRecivaleVo.statementRecivableDto.statementRecivableEntity.statementBillNo);
            //设置本期发生额
            form.findField('currentAmount').setValue(result.statementRecivaleVo.statementRecivableDto.statementRecivableEntity.periodAmount);
            //设置剩余未还金额
            form.findField('currentRemainAmount').setValue(result.statementRecivaleVo.statementRecivableDto.statementRecivableEntity.periodNpayAmount);
            //设置还款金额
            writeoff.receivableStatement.RepaymentForm.form.findField('repaymentAmount').setValue(result.statementRecivaleVo.statementRecivableDto.statementRecivableEntity.periodNpayAmount);

            //还款的页面
            writeoff.receivableStatement.win= Ext.create('Ext.window.Window',{
                title: writeoff.receivableStatement.i18n('foss.stl.writeoff.statementCommon.repaymentInfo'),//还款单
                width: stl.SCREENWIDTH * 0.7,
                modal: true,
                constrainHeader: true,
                closeAction: 'hide',
                items: [
                    writeoff.receivableStatement.RepaymentStatementForm ,
                    writeoff.receivableStatement.RepaymentForm
                ]
            }).show();
        },
        exception:function(response){
            var result = Ext.decode(response.responseText);
            Ext.Msg.alert(writeoff.receivableStatement.i18n('foss.stl.writeoff.common.alert'),result.message);
        }
    });
};

//还款页面
writeoff.receivableStatement.RepaymentStatementForm = Ext.create('Foss.statementbill.RepaymentStatementForm');
writeoff.receivableStatement.RepaymentForm = Ext.create('Foss.statementbill.RepaymentForm');


/**
 * 对账单操作列的下拉框
 */
Ext.define('Foss.statementbill.operateColumn', {
    extend:'Ext.form.field.ComboBox',
    typeAhead: true,
    emptyText:writeoff.receivableStatement.i18n('foss.stl.writeoff.common.actionColumn'),//操作列
    triggerAction: 'all',
    queryMode: 'local',
    store: Ext.create('Foss.statementbill.operateColumnStore'),
    valueField: 'value',
    displayField: 'name',
    forceSelection :true,
    listeners:{
        'change':function(combo){
            if(Ext.isEmpty(combo.getValue())){
                combo.setValue("");
            }
        },//改方法为了根据不同状态显示不同下拉
        'expand':function(field){
            var selection=Ext.getCmp('T_writeoff-receivableStatement_content').getStatementQueryGrid().getSelectionModel().getSelection()[0];

            this.store.removeAll();
            this.store.add(
                {name:'反确认',value:'1'},
                {name:'确认',value:'3'},
                {name:'明细',value:'4'},
                {name:'还款',value:'5'});
            //反确认record
            var unConfirmRecord = this.store.getAt(0);
            //确认
            var confirmReocrd = this.store.getAt(1);
            //明细
            var detailed = this.store.getAt(2);
            //还款
            var huankuan = this.store.getAt(3);
            //判断是否已确认
            if(selection.get('statementStatus')=='N'){
                this.store.remove(unConfirmRecord);
                this.store.remove(huankuan);
            }else{
                this.store.remove(confirmReocrd);
            }
            //如果本期未还金额为零则移除还款金额
            if(selection.get('periodNpayAmount')==0||selection.get('periodNpayAmount')==null){
                this.store.remove(huankuan);
            }
        },
        'select':function(combo){
             if(combo.value=='1'){
                //反确认   是否确定
                 Ext.MessageBox.confirm(writeoff.receivableStatement.i18n('foss.stl.writeoff.common.alert'),writeoff.receivableStatement.i18n('foss.stl.writeoff.receivableStatement.isfouqueren'),function(val){
                     if(val=='yes'){
                       fanqueren();
                     }else{
                         return false;
                     }
                 });
            }else if(combo.value=='3'){
                 //确认
                 Ext.MessageBox.confirm(writeoff.receivableStatement.i18n('foss.stl.writeoff.common.alert'),writeoff.receivableStatement.i18n('foss.stl.writeoff.receivableStatement.isfouqueren'),function(val){
                     if(val=='yes'){
                        queren();
                     }else{
                         return false;
                     }
                 });
             }else if(combo.value=='4'){
                 //明细   是否查看明细
                 Ext.MessageBox.confirm(writeoff.receivableStatement.i18n('foss.stl.writeoff.common.alert'),writeoff.receivableStatement.i18n('foss.stl.writeoff.statementEdit.isWatchEntry'),function(val){
                     if(val=='yes'){
                         combo.store.removeAll();

                         writeoff.receivableStatement.detail.win= Ext.create('Ext.window.Window',{
                             width: stl.SCREENWIDTH * 0.9,
                             modal: true,
                             constrainHeader: true,
                             closeAction: 'destory',
                             items:[writeoff.receivableStatement.BaseInfo2,writeoff.receivableStatement.detail,writeoff.receivableStatement.OperateButtonPanel]
                             // 仅仅用来显示一个头部。没有数据，
                         });
                         writeoff.receivableStatement.detail.win.show();
                         writeoff.receivableStatement.datilStore.load({
                             scope: this,
                             callback:function(store, records, successful, eOpts){//表格数据加载事件
                             //总条数
                              /*if(store.length<=0){
                                  Ext.getCmp('Foss.receivableStatement.statement.total').setValue("0");
                              }else{
                                 Ext.getCmp('Foss.receivableStatement.statement.total').setValue(store[0].store.totalCount);
                             }*/
                         }});
                     }else{
                         return ;
                     }
                 });
            }else if(combo.value=='5'){
                 //还款
                 huankuan();
             }
        }
    }
});
//操作列下拉框
var operateColumn = Ext.create('Foss.statementbill.operateColumn');

//编辑器
var SoperateColumnEditing = Ext.create('Ext.grid.plugin.CellEditing', {
    clicksToEdit : 1,
    isObservable : false
}) ;


//定义grid
Ext.define('Foss.writeoff.receivableStatement.receivableStatementGrid',{
    extend:'Ext.grid.Panel',
    title:writeoff.receivableStatement.i18n('foss.stl.writeoff.statementEdit.statementBill'),//对账单
    frame:true,
    height:600,
    store:null,
    bodyCls: 'autoHeight',
    cls: 'autoHeight',
    plugins:SoperateColumnEditing,
    selModel:Ext.create('Ext.selection.CheckboxModel'),
    defaults:{
        align:'center',
        margin:'5 0 5 0'
    },
    viewConfig:{
        enableTextSelection : true//设置行可以选择，进而可以复制
    },
    dockedItems: [{
        xtype: 'toolbar',
        dock: 'bottom',
        layout:'column',
        items: [{
            xtype:'textfield',
            readOnly:true,
            id:'Foss.receivableStatement.statement.total',
            name:'totalRows',
            columnWidth:.1,
            labelWidth:60,
            fieldLabel:writeoff.receivableStatement.i18n('foss.stl.writeoff.statementAdd.totalCount') //总条数
        },{
            xtype:'container',
            border:false,
            html:'&nbsp;',
            columnWidth:.9
        },{
            xtype:'button',
            text:writeoff.receivableStatement.i18n('foss.stl.writeoff.statementEdit.batchRepayment'),//批量还款
            columnWidth:.15,
            handler:function(){
                var sel = writeoff.receivableStatement.grid.getSelectionModel().getSelection();
                if(sel.length<=0){
                    Ext.Msg.alert(writeoff.receivableStatement.i18n('foss.stl.writeoff.common.alert'),writeoff.receivableStatement.i18n('foss.stl.writeoff.statementEdit.unSelectedWarning'));
                    return false;
                }
                huankuan();
            }
        },{
            xtype:'button',
            text:writeoff.receivableStatement.i18n('foss.stl.writeoff.statementEdit.confirmStatement'),//确认对账
            columnWidth:.15,
            handler:function(){
                var sel = writeoff.receivableStatement.grid.getSelectionModel().getSelection();
                if(sel.length<=0){
                    Ext.Msg.alert(writeoff.receivableStatement.i18n('foss.stl.writeoff.common.alert'),writeoff.receivableStatement.i18n('foss.stl.writeoff.statementEdit.unSelectedWarning'));
                    return false;
                }
                queren();
            }
        }]
    },{
        xtype: 'toolbar',
        dock: 'top',
        layout:'column',
        items: [{
            xtype:'button',
            text:writeoff.receivableStatement.i18n('foss.stl.writeoff.statementEdit.batchRepayment'), //批量还款
            columnWidth:.15,
            handler:function(){
                var sel = writeoff.receivableStatement.grid.getSelectionModel().getSelection();
                if(sel.length<=0){
                    Ext.Msg.alert(writeoff.receivableStatement.i18n('foss.stl.writeoff.common.alert'),writeoff.receivableStatement.i18n('foss.stl.writeoff.statementEdit.unSelectedWarning'));
                    return false;
                }
                huankuan();
            }
        },{
            xtype:'button',
            text:writeoff.receivableStatement.i18n('foss.stl.writeoff.statementEdit.confirmStatement'),//确认对账单
            columnWidth:.15,
            handler:function(){
                var sel = writeoff.receivableStatement.grid.getSelectionModel().getSelection();
                if(sel.length<=0){
                    Ext.Msg.alert(writeoff.receivableStatement.i18n('foss.stl.writeoff.common.alert'),writeoff.receivableStatement.i18n('foss.stl.writeoff.statementEdit.unSelectedWarning'));
                    return false;
                }
                queren();
            }
        },{
            xtype:'component',
             autoEl:{
             tag:'div',
             html:'点击表格操作列'+'<span style="color:red;">'+'空白处'+'</span>'+'可进行查看明细等操作！'
             }
        },{
            xtype:'button',
            text:writeoff.receivableStatement.i18n('foss.stl.writeoff.statementEdit.exportStatement'),//导出对账单
            columnWidth:.15,
            handler:function(){
                var	columns,
                    arrayColumns,
                    arrayColumnNames;
                var statementBillNo = new Array();
                //对账单grid
                var statementList = writeoff.receivableStatement.grid.getSelectionModel().getSelection();
                if(statementList.length==0){
                    Ext.Msg.alert(writeoff.receivableStatement.i18n('foss.stl.writeoff.common.alert'),writeoff.receivableStatement.i18n('foss.stl.writeoff.statementEdit.unSelectedWarning'));
                    return false;
                }else {
                    for(var i=0;i<statementList.length;i++){
                        statementBillNo.push(statementList[i].data.statementBillNo);
                    }
                }
                //转化列头和列明
                columns = writeoff.receivableStatement.grid.columns;
                arrayColumns = [];
                arrayColumnNames = [];
                //将前台对应列头传入到后台去
                for(var i=2;i<columns.length;i++){
                    if(columns[i].isHidden()==false){
                        var hederName = columns[i].text;
                        var dataIndex = columns[i].dataIndex;
                        arrayColumns.push(dataIndex);
                        arrayColumnNames.push(hederName);
                    }
                }
                //拼接vo，注入到后台
                searchParams = {
                    'statementRecivaleVo.statementRecivableDto.waybillNoList':statementBillNo,
                    'statementRecivaleVo.statementRecivableDto.arrayColumns':arrayColumns,
                    'statementRecivaleVo.statementRecivableDto.arrayColumnNames':arrayColumnNames
                };
                if(!Ext.fly('downloadAttachFileForm')){
                    var frm = document.createElement('form');
                    frm.id = 'downloadAttachFileForm';
                    frm.style.display = 'none';
                    document.body.appendChild(frm);
                }
                Ext.Ajax.request({
                    url: writeoff.realPath('receivableStatemenExl.action'),
                    form: Ext.fly('downloadAttachFileForm'),
                    method : 'POST',
                    params : searchParams,
                    isUpload: true,
                    success : function(response,options){
                        //导出成功
                        Ext.Msg.alert(writeoff.receivableStatement.i18n('foss.stl.writeoff.common.alert'),writeoff.receivableStatement.i18n('foss.stl.writeoff.partnerPayStatementEdit.exportSuccess'));
                    }
                });

            }
        }]
    }],
    columns: [
        {
            header:writeoff.receivableStatement.i18n('foss.stl.writeoff.common.actionColumn'),//操作列
            //dataIndex:'isRight',
            editor:operateColumn,
            renderer: function(value){
                var record = operateColumn.findRecord(operateColumn.valueField, value);
                return record ? record.get(operateColumn.displayField): operateColumn.valueNotFoundText;
            },
            sortable: true
        },{
            header:writeoff.receivableStatement.i18n('foss.stl.writeoff.receivableWriteOffPayble.statementNo'),//对账单号
            dataIndex:'statementBillNo'
        },{
            header:writeoff.receivableStatement.i18n('foss.stl.writeoff.woodenStatementEdit.createOrgCode'),//部门编码
            dataIndex:'createOrgCode'
        },{
            header:writeoff.receivableStatement.i18n('foss.stl.writeoff.woodenStatementEdit.createOrgName'),//部门名称
            dataIndex:'createOrgName'
        },{
            header:writeoff.receivableStatement.i18n('foss.stl.writeoff.woodenStatementEdit.companyCode'),//公司编码
            dataIndex:'companyCode'
        },{
            header:writeoff.receivableStatement.i18n('foss.stl.writeoff.woodenStatementEdit.companyName'),//公司名称
            dataIndex:'companyName'
        },{
            header:writeoff.receivableStatement.i18n('foss.stl.writeoff.statementAdd.unifiedCode'),//部门标杆编码
            dataIndex:'unifiedCode'
        },{
            header:writeoff.receivableStatement.i18n('foss.stl.writeoff.woodenStatementEdit.customerCode'),//客户编码
            dataIndex:'customerCode'
        },{
            header:writeoff.receivableStatement.i18n('foss.stl.writeoff.woodenStatementEdit.customerName'),//客户名称
            dataIndex:'customerName'
        },{
            header:writeoff.receivableStatement.i18n('foss.stl.writeoff.statementAdd.periodAmount'),//本期发生金额
            dataIndex:'periodAmount'
        },{
            header:writeoff.receivableStatement.i18n('foss.stl.writeoff.statementAdd.periodRecAmount'),//本期应收
            dataIndex:'periodRecAmount'
        },{
            header:writeoff.receivableStatement.i18n('foss.stl.writeoff.statementEdit.periodUnverifyRecAmount'),//本期剩余应收金额
            dataIndex:'periodUnverifyRecAmount'
        },{
            header:writeoff.receivableStatement.i18n('foss.stl.writeoff.statementEdit.periodStartDate'),//账期开始日期
            dataIndex:'periodBeginDate',
            xtype : 'datecolumn',
            format : 'Y-m-d H:i:s'
        },{
            header:writeoff.receivableStatement.i18n('foss.stl.writeoff.statementEdit.periodEndAmount'),//账期结束日期
            dataIndex:'periodEndDate',
            xtype : 'datecolumn',
            format : 'Y-m-d H:i:s'
        },{
            header:writeoff.receivableStatement.i18n('foss.stl.writeoff.statementAdd.settleNum'),//结账次数
            dataIndex:'settleNum'
        },{
            header:writeoff.receivableStatement.i18n('foss.stl.writeoff.statementAdd.confirmTime'),//确认时间
            dataIndex:'confirmTime',
            xtype : 'datecolumn',
            format : 'Y-m-d H:i:s'
        },{
            header:writeoff.receivableStatement.i18n('foss.stl.writeoff.statementEdit.companyAccountBankNo'),//子公司账号
            dataIndex:'companyAccountBankNo'
        },{
            header:writeoff.receivableStatement.i18n('foss.stl.writeoff.statementAdd.accountUserName'),//开户名
            dataIndex:'accountUserName'
        },{
            header:writeoff.receivableStatement.i18n('foss.stl.writeoff.statementEdit.bankBranchName'),//支行名称
            dataIndex:'bankBranchName'
        },{
            header:writeoff.receivableStatement.i18n('foss.stl.writeoff.statementEdit.statementStatus'),//对账单状态
            dataIndex:'statementStatus',
            renderer:function(val){
                switch(val){
                    case "Y":
                        return "已确认";
                    case "N":
                        return "未确认";
                }
            }
        },{
            header:writeoff.receivableStatement.i18n('foss.stl.writeoff.statementEdit.paidAmount'),//本期已还金额
            dataIndex:'periodRrpayAmount'
        },{
            header:writeoff.receivableStatement.i18n('foss.stl.writeoff.statementEdit.unpaidAmount'),//本期未还金额
            dataIndex:'periodNpayAmount'
        },{
            header:writeoff.receivableStatement.i18n('foss.stl.writeoff.statementCommon.billDescription'),//对账单描述
            dataIndex:'statementDes'
        },{
            header:writeoff.receivableStatement.i18n('foss.stl.writeoff.common.createTime'),//创建时间
            dataIndex:'createTime',
            xtype : 'datecolumn',
            format : 'Y-m-d H:i:s'
        }],
        initComponent:function(config) {
            var me = this;
            cfg = Ext.apply({}, config);
            me.store = Ext.create('Foss.writeoff.receivableStatement.receivableStatementStore');
            //自定义分页控件
            me.bbar = Ext.create('Deppon.StandardPaging',{
                store : me.store,
                pageSize : 20,
                maximumSize : 100,
                plugins : Ext.create('Deppon.ux.PageSizePlugin', {
                    sizeList : [['20', 20], ['50', 50],  ['100', 100]]
                })
            });
            writeoff.receivableStatement.store =me.bbar;
            me.callParent([cfg]);
        }
});

//定义主函数
Ext.onReady(function(){
    Ext.QuickTips.init();
    if(Ext.getCmp('T_writeoff-receivableStatement_content')){
        return;
    }

    //判断id是否存在如果存在就销毁
    if(Ext.getCmp('Foss.receivableStatement.datil.billType')!=undefined){
        Ext.destroy(Ext.getCmp('Foss.receivableStatement.datil.billType'));
    }
    if(Ext.getCmp('Foss.receivableStatement.datil.createTime')!=undefined){
        Ext.destroy(Ext.getCmp('Foss.receivableStatement.datil.createTime'));
    }
    if(Ext.getCmp('Foss.receivableStatement.datil.contractOrgCode')!=undefined){
        Ext.destroy(Ext.getCmp('Foss.receivableStatement.datil.contractOrgCode'));
    }
    if( Ext.getCmp('Foss.receivableStatement.datil.confirmStatus')!=undefined){
        Ext.destroy( Ext.getCmp('Foss.receivableStatement.datil.confirmStatus'));
    }
    if( Ext.getCmp('Foss.receivableStatement.datil.periodBeginDate')!=undefined){
        Ext.destroy(Ext.getCmp('Foss.receivableStatement.datil.periodBeginDate'));
    }
    if(Ext.getCmp('Foss.receivableStatement.datil.companyName')!=undefined){
        Ext.destroy(Ext.getCmp('Foss.receivableStatement.datil.companyName'));
    }
    if(Ext.getCmp('Foss.receivableStatement.datil.companyCode')!=undefined){
        Ext.destroy(Ext.getCmp('Foss.receivableStatement.datil.companyCode'));
    }
    if(Ext.getCmp('Foss.receivableStatement.datil.contractOrgName')!=undefined){
        Ext.destroy(Ext.getCmp('Foss.receivableStatement.datil.contractOrgName'));
    }
    if(Ext.getCmp('Foss.receivableStatement.datil.confirmTime')!=undefined){
        Ext.destroy(Ext.getCmp('Foss.receivableStatement.datil.confirmTime'));
    }
    if(Ext.getCmp('Foss.receivableStatement.datil.periodEndDate')!=undefined){
        Ext.destroy(Ext.getCmp('Foss.receivableStatement.datil.periodEndDate'));
    }
    if(Ext.getCmp('Foss.receivableStatement.datil.createOrgName')!=undefined){
        Ext.destroy(Ext.getCmp('Foss.receivableStatement.datil.createOrgName'));
    }
    if(Ext.getCmp('Foss.receivableStatement.datil.statementBillNo')!=undefined){
        Ext.destroy(Ext.getCmp('Foss.receivableStatement.datil.statementBillNo'));
    }
    if(Ext.getCmp('Foss.receivableStatement.datil.unifiedCode')!=undefined){
        Ext.destroy(Ext.getCmp('Foss.receivableStatement.datil.unifiedCode'));
    }
    if( Ext.getCmp('Foss.receivableStatement.datil.companyAccountBankNo')!=undefined){
        Ext.destroy(Ext.getCmp('Foss.receivableStatement.datil.companyAccountBankNo'));
    }
    if(Ext.getCmp('Foss.receivableStatement.datil.accountUserName')!=undefined){
        Ext.destroy(Ext.getCmp('Foss.receivableStatement.datil.accountUserName'));
    }
    if(Ext.getCmp('Foss.receivableStatement.datil.bankBranchName')!=undefined){
        Ext.destroy(Ext.getCmp('Foss.receivableStatement.datil.bankBranchName'));
    }
    if(Ext.getCmp('Foss.receivableStatement.datil.unpaidAmount')!=undefined){
        Ext.destroy(Ext.getCmp('Foss.receivableStatement.datil.unpaidAmount'));
    }
    if(Ext.getCmp('Foss.receivableStatement.datil.settleNum')!=undefined){
        Ext.destroy(Ext.getCmp('Foss.receivableStatement.datil.settleNum'));
    }
    if(Ext.getCmp('Foss.receivableStatement.datil.createOrgCode')!=undefined){
        Ext.destroy(Ext.getCmp('Foss.receivableStatement.datil.createOrgCode'));
    }
    if(Ext.getCmp('Foss.detail.receivableStatement.totalRows')!=undefined){
        Ext.destroy(Ext.getCmp('Foss.detail.receivableStatement.totalRows'));
    }
    if(Ext.getCmp('Foss.receivableStatement.statement.total')!=undefined){
        Ext.destroy(Ext.getCmp('Foss.receivableStatement.statement.total'));
    }
    if(Ext.getCmp('Foss.mingxi.totalCount')!=undefined){
        Ext.destroy(Ext.getCmp('Foss.mingxi.totalCount'));
    }

    //创建一个Tab
    writeoff.receivableStatement.queryTab=Ext.create('Foss.writeoff.receivableStatement.receivableStatementTab');
    //对账单明细的基本信息
    writeoff.receivableStatement.BaseInfo2=Ext.create('Foss.receivableStatement.BaseInfo');
    //按钮面板
    writeoff.receivableStatement.OperateButtonPanel=Ext.create('Foss.receivableStatement.OperateButtonPanel');
    //对账单明细
    writeoff.receivableStatement.detail=Ext.create('Foss.receivableStatement.StatementEntryEditGrid');
    //添加明细的tab
    writeoff.receivableStatement.detailAdd = Ext.create('Foss.statementbill.receivable.QueryInfoTab');
    //创建grid
    writeoff.receivableStatement.grid = Ext.create('Foss.writeoff.receivableStatement.receivableStatementGrid');
    //添加对账单明细grid
    writeoff.receivableStatement.Message =Ext.create('Foss.writeoff.datile.receivableStatementGrid');


    //创建整个对账单面板，将上面几块包含在一起
    Ext.create('Ext.panel.Panel',{
        id: 'T_writeoff-receivableStatement_content',
        cls: "panelContentNToolbar",
        bodyCls: 'panelContentNToolbar-body',
        layout: 'auto',
        getStatementQueryGrid : function(){
           return   writeoff.receivableStatement.grid;
        },
        items: [writeoff.receivableStatement.queryTab,writeoff.receivableStatement.grid],
        renderTo: 'T_writeoff-receivableStatement-body',
        listeners:{
            'boxready':function(th){
                var roles = stl.currentUserDepts;
                var queryByDateTab = writeoff.receivableStatement.queryTab.items.items[0].items.items[0];
                var dept = queryByDateTab.getForm().findField("createOrgCode");
                if(!Ext.isEmpty(stl.currentDept.code)){
                    var displayText = stl.currentDept.name
                    var valueText = stl.currentDept.code;
                    var store = dept.store;
                    var  key = dept.displayField + '';
                    var value = dept.valueField+ '';

                    var m = Ext.create(store.model.modelName);
                    m.set(key, displayText);
                    m.set(value, valueText);

                    store.loadRecords([m]);
                    dept.setValue(valueText);
                }

                var queryByFailingInvoice =  writeoff.receivableStatement.queryTab.items.items[3].items.items[0];
                var failingInvoiceDept = queryByFailingInvoice.getForm().findField("createOrgCode");
                if(!Ext.isEmpty(stl.currentDept.code)){
                    var displayText = stl.currentDept.name
                    var valueText = stl.currentDept.code;
                    var store = failingInvoiceDept.store;
                    var  key = failingInvoiceDept.displayField + '';
                    var value = failingInvoiceDept.valueField+ '';

                    var m = Ext.create(store.model.modelName);
                    m.set(key, displayText);
                    m.set(value, valueText);

                    store.loadRecords([m]);
                    failingInvoiceDept.setValue(valueText);
                }
            }
        }
    });
})