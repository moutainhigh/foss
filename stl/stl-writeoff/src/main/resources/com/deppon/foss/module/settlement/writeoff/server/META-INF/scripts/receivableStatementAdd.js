/**
 * Created by 306698 on 2016/1/19.
 */
//付款方式的转换
writeoff.receivableStatementAdd.paymentType=function(val){
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
writeoff.receivableStatementAdd.productCode=function(val){
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
writeoff.receivableStatementAdd.billType=function(val){
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
writeoff.receivableStatementAdd.receiveMethod=function(val){
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

/**
 * 生成对账单的方法
 */
writeoff.receivableStatementAdd.statementSave=function(){
    //点击后按钮置为不可用
    Ext.getCmp('Foss.slt.receivableStatementAdd').disable("true");
    var store =  writeoff.receivableStatementAdd.grid.getStore();
    var data = store.data.items;
    if(data.length<=0){
        Ext.getCmp('Foss.slt.receivableStatementAdd').enable();
        //有数据不能生成对账单
        Ext.Msg.alert(writeoff.receivableStatementAdd.i18n('foss.stl.writeoff.common.alert') ,writeoff.receivableStatementAdd.i18n('foss.stl.writeoff.receivableStatementAdd.isNotMessage'));
        return;
    }
    //查询条件form中的值
    var queryParams;
    var params;
    var contractOrgName;        //定义合伙人部门
    var waybillNoList = new Array();       //订单号的集合
    //var queryParams = bondm.paymentDetail.queryPaymentDetailForm.getValues();
    //扣款类型（payment:已扣款，unpayment：待扣款）
    var flowType = writeoff.receivableStatementAdd.queryTab.getActiveTab().itemId;
        if(flowType=='producedByPartnerId') {
            queryParams = writeoff.receivableStatementAdd.producedByPartnerForm.getValues();
            contractOrgName = writeoff.receivableStatementAdd.producedByPartnerForm.form.findField('contractOrgCode').getRawValue();
            params = {
                'statementRecivaleVo': {
                    'statementRecivableDto': {
                        'businessStartDate': queryParams.businessStartDate,
                        'businessEndDate': queryParams.businessEndDate,
                        'contractOrgName': contractOrgName,
                        'contractOrgCode': queryParams.contractOrgCode
                    }
                }
            }
        }
        if (flowType == 'productionOrderId') {
            queryParams = writeoff.receivableStatementAdd.productionOrderForm.getValues();
            waybillNoList = queryParams.waybillNo.split(",");
            params = {
                'statementRecivaleVo': {
                    'statementRecivableDto': {
                        'waybillNoList': waybillNoList
                    }
                }
            }
        }
        //生成对账单的方法
        Ext.Ajax.request({
            url: writeoff.realPath('addReceivableStatement.action'),//老的url格式：
            jsonData: params,
            success: function (response) {
                var json = Ext.decode(response.responseText);
                Ext.getCmp('Foss.slt.receivableStatementAdd').enable();
                store.load();
                //生成对账成功
                Ext.Msg.alert(writeoff.receivableStatementAdd.i18n('foss.stl.writeoff.common.alert'),writeoff.receivableStatementAdd.i18n('foss.stl.writeoff.partnerPayStatementEdit.addSuccess')+json.statementRecivaleVo.statementRecivableDto.statementBillNo);
            },
            exception: function (response) {
                var json = Ext.decode(response.responseText);
                Ext.getCmp('Foss.slt.receivableStatementAdd').enable();
                //生成对账失败
                Ext.Msg.alert(writeoff.receivableStatementAdd.i18n('foss.stl.writeoff.common.alert'),json.message);
            }
        });

};




//定义model
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


//定义store
Ext.define('Foss.writeoff.receivableStatementStore',{
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
        var contractOrgName;        //定义合伙人部门
        var waybillNoList = new Array();       //订单号的集合
        //var queryParams = bondm.paymentDetail.queryPaymentDetailForm.getValues();
        //扣款类型（payment:已扣款，unpayment：待扣款）
        var flowType = writeoff.receivableStatementAdd.queryTab.getActiveTab().itemId;
        if(flowType=='producedByPartnerId'){
            queryParams = writeoff.receivableStatementAdd.producedByPartnerForm.getValues();
            contractOrgName =  writeoff.receivableStatementAdd.producedByPartnerForm.form.findField('contractOrgCode').getRawValue();
            params={
                'statementRecivaleVo.statementRecivableDto.businessStartDate': queryParams.businessStartDate,
                'statementRecivaleVo.statementRecivableDto.businessEndDate': queryParams.businessEndDate,
                'statementRecivaleVo.statementRecivableDto.contractOrgName':contractOrgName,
                'statementRecivaleVo.statementRecivableDto.contractOrgCode':queryParams.contractOrgCode
            }
        }
        if(flowType=='productionOrderId'){
            queryParams = writeoff.receivableStatementAdd.productionOrderForm.getValues();
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
            Ext.MessageBox.alert(writeoff.receivableStatementAdd.i18n('foss.stl.writeoff.common.alert'),"系统错误");
            return ;
        }
       if(data.success==false){
         Ext.MessageBox.alert(writeoff.receivableStatementAdd.i18n('foss.stl.writeoff.common.alert'),data.message);
           return ;
        }
        if(data.statementRecivaleVo.statementRecivableDto !=null){
            writeoff.receivableStatementAdd.BaseInfo.show();
                //对账客户类型 默认合伙人收款对账单
                Ext.getCmp('Foss.receivableStatement.billType').setValue('合伙人收款对账单');

                     //设置合伙人编码
                    Ext.getCmp('Foss.receivableStatement.contractOrgCode').setValue("");
                    //账单开始日期
                    Ext.getCmp('Foss.receivableStatement.periodBeginDate').setValue("");
                    //账单结束日期
                    Ext.getCmp('Foss.receivableStatement.periodEndDate').setValue("");
                    //所属公司
                    Ext.getCmp('Foss.receivableStatement.companyName').setValue("");
                    //所属公司编码
                    Ext.getCmp('Foss.receivableStatement.companyCode').setValue("");
                     //所属部门
                    Ext.getCmp('Foss.receivableStatement.createOrgName').setValue("");
                    //所属部门编码
                    Ext.getCmp('Foss.receivableStatement.createOrgCode').setValue("")
                     //合伙人名称
                    Ext.getCmp('Foss.receivableStatement.contractOrgName').setValue("");
                     //部门标杆编码
                    Ext.getCmp('Foss.receivableStatement.unifiedCode').setValue("");
                    //开户名
                    Ext.getCmp('Foss.receivableStatement.accountUserName').setValue("");
                     //账号
                    Ext.getCmp('Foss.receivableStatement.companyAccountBankNo').setValue("");
                    //省市支行
                    Ext.getCmp('Foss.receivableStatement.bankBranchName').setValue("");
                    //本期未还金额
                    Ext.getCmp('Foss.receivableStatement.unpaidAmount').setValue("");
                    Ext.getCmp('Foss.receivableStatement.settleNum').setValue("");
                if(data.statementRecivaleVo.statementRecivableDto.contractOrgCode!=null){
                    Ext.getCmp('Foss.receivableStatement.contractOrgCode').setValue(data.statementRecivaleVo.statementRecivableDto.contractOrgCode);
                }
                //确认状态不用管
                //账单开始日期
                if(data.statementRecivaleVo.statementRecivableDto.businessStartDate!=null) {
                    Ext.getCmp('Foss.receivableStatement.periodBeginDate').setValue(new Date(data.statementRecivaleVo.statementRecivableDto.businessStartDate));
                }
                //账单结束日期
                if(data.statementRecivaleVo.statementRecivableDto.businessEndDate!=null) {
                    Ext.getCmp('Foss.receivableStatement.periodEndDate').setValue(new Date(data.statementRecivaleVo.statementRecivableDto.businessEndDate));
                }
                //所属公司
                if(data.statementRecivaleVo.statementRecivableDto.statementOfAccountEntity.companyName!=null) {
                    Ext.getCmp('Foss.receivableStatement.companyName').setValue(data.statementRecivaleVo.statementRecivableDto.statementOfAccountEntity.companyName);
                    Ext.getCmp('Foss.receivableStatement.companyCode').setValue(data.statementRecivaleVo.statementRecivableDto.statementOfAccountEntity.companyCode);
                }
                //所属部门
                if(data.statementRecivaleVo.statementRecivableDto.statementOfAccountEntity.createOrgName!=null) {
                    Ext.getCmp('Foss.receivableStatement.createOrgName').setValue(data.statementRecivaleVo.statementRecivableDto.statementOfAccountEntity.createOrgName);
                    Ext.getCmp('Foss.receivableStatement.createOrgCode').setValue(data.statementRecivaleVo.statementRecivableDto.statementOfAccountEntity.createOrgCode);
                }
                //合伙人名称
                if(data.statementRecivaleVo.statementRecivableDto.contractOrgName!=null) {
                    Ext.getCmp('Foss.receivableStatement.contractOrgName').setValue(data.statementRecivaleVo.statementRecivableDto.contractOrgName);
                }
                //部门标杆编码
                if(data.statementRecivaleVo.statementRecivableDto.statementOfAccountEntity.unifiedCode!=null) {
                    Ext.getCmp('Foss.receivableStatement.unifiedCode').setValue(data.statementRecivaleVo.statementRecivableDto.statementOfAccountEntity.unifiedCode);
                }
                //开户名
                if(data.statementRecivaleVo.statementRecivableDto.statementOfAccountEntity.accountUserName!=null) {
                    Ext.getCmp('Foss.receivableStatement.accountUserName').setValue(data.statementRecivaleVo.statementRecivableDto.statementOfAccountEntity.accountUserName);
                }
                //账号
                if(data.statementRecivaleVo.statementRecivableDto.statementOfAccountEntity.companyAccountBankNo!=null) {
                    Ext.getCmp('Foss.receivableStatement.companyAccountBankNo').setValue(data.statementRecivaleVo.statementRecivableDto.statementOfAccountEntity.companyAccountBankNo);
                }
                //省市支行
                if(data.statementRecivaleVo.statementRecivableDto.statementOfAccountEntity.bankBranchName!=null) {
                    Ext.getCmp('Foss.receivableStatement.bankBranchName').setValue(data.statementRecivaleVo.statementRecivableDto.statementOfAccountEntity.bankBranchName);
                }
                //本期未还金额
                if(data.statementRecivaleVo.statementRecivableDto.statementOfAccountEntity.unpaidAmount!=null) {
                    Ext.getCmp('Foss.receivableStatement.unpaidAmount').setValue(data.statementRecivaleVo.statementRecivableDto.statementOfAccountEntity.unpaidAmount);
                }
                //结账次数
                if(data.statementRecivaleVo.statementRecivableDto.statementOfAccountEntity.settleNum!=null) {
                    Ext.getCmp('Foss.receivableStatement.settleNum').setValue(data.statementRecivaleVo.statementRecivableDto.statementOfAccountEntity.settleNum);
                }
                //总条数
            if(data.totalCount!=null) {
                Ext.getCmp('Foss.receivableStatement.totalRows').setValue(data.totalCount);
            }else{
                Ext.getCmp('Foss.receivableStatement.totalRows').setValue('0');
            }
            }
    }
}
});

//定义按合伙人制作查询的form
Ext.define('Foss.writeoff.producedByPartnerForm',{
    extend:'Ext.form.Panel',
    frame:false,
    defaults:{
        margin :'5 5 5 0',
        labelWidth :100
    },
    defaultType:'textfield',
    layout:{
        type :'table',
        columns :2
    },
    items:[
        {
            xtype:'datefield',
            fieldLabel:writeoff.receivableStatementAdd.i18n('foss.stl.writeoff.statementAdd.accountPeriod'),  //账期日期
            allowBlank:false,
            name:'businessStartDate',
            format:'Y-m-d',
            value:stl.getTargetDate(stl.getLastMonthLastDay(new Date()),+1)
        },{
            xtype:'datefield',
            allowBlank:false,
            fieldLabel:writeoff.receivableStatementAdd.i18n('foss.stl.writeoff.statementAdd.to'),      //至
            name:'businessEndDate',
            format:'Y-m-d',
            value:stl.getTargetDate(new Date(),+1)
        },{
            xtype: 'commonsaledepartmentselector',
            name:'contractOrgCode',
            fieldLabel: writeoff.receivableStatementAdd.i18n('foss.stl.writeoff.partnerPayStatementEdit.partnerName'),       //合伙人名称
            allowBlank: false,
            listWidth:300,//设置下拉框宽度
            isPaging:true //分页
        },
        {
            xtype:'container'
        },
        {
            xtype:'button',
            align:'center',
            width:80,
            text:writeoff.receivableStatementAdd.i18n('foss.stl.writeoff.common.reset'), //重置
            handler:function(){
                this.up('form').getForm().reset();
            }
        },
        {
            text:writeoff.receivableStatementAdd.i18n('foss.stl.writeoff.common.query'),//查询
            width:80,
            cls:'yellow_button',
            xtype:'button',
            handler:function(){
                //点击查询触发事件
                //非空校验
                //校验
                var me = this;
                var form = this.up('form').getForm();
                //需要传入后台的参数
                var businessStartDate = form.findField('businessStartDate').getValue();     //开始日期
                var businessEndDate = form.findField('businessEndDate').getValue();       //结束日期
                var contractOrgCode = form.findField('contractOrgCode').getValue();        //合伙人信息
                if(!businessStartDate){
                    //开始日期不能为空
                    Ext.Msg.alert(writeoff.receivableStatementAdd.i18n('foss.stl.writeoff.common.alert'),writeoff.receivableStatementAdd.i18n('foss.stl.writeoff.airPaymentPayable.dateIsNotNull'));
                    return false;
                }
                if(!businessEndDate){
                    //结束日期不能为空
                    Ext.Msg.alert(writeoff.receivableStatementAdd.i18n('foss.stl.writeoff.common.alert'),writeoff.receivableStatementAdd.i18n('foss.stl.writeoff.airPaymentPayable.dateIsNotNull'));
                    return false;
                }
                //比较起始日期和结束日期
                var compareTwoDate = stl.compareTwoDate(businessStartDate,businessEndDate);
                if(compareTwoDate>stl.DATELIMITMAXDAYS_WEEK){
                    //'温馨提示','起始日期和结束日期间隔不能超过90天'
                    Ext.Msg.alert(writeoff.receivableStatementAdd.i18n('foss.stl.writeoff.common.alert'),writeoff.receivableStatementAdd.i18n('foss.stl.writeoff.statementAdd.queryDateMaxLimit'));
                    return false;
                }
                 if(compareTwoDate<1){
                    //'温馨提示','结束日期不能早于起始日期!'
                    Ext.Msg.alert(writeoff.receivableStatementAdd.i18n('foss.stl.writeoff.common.alert'),writeoff.receivableStatementAdd.i18n('foss.stl.writeoff.receivableWriteOffPayble.endDateErrorWarning'));
                    return false;
                }
                if(!contractOrgCode){
                    //合伙人信息不能为空
                    Ext.Msg.alert(writeoff.receivableStatementAdd.i18n('foss.stl.writeoff.common.alert'),writeoff.receivableStatementAdd.i18n('foss.stl.writeoff.receivableStatementAdd.hehuorenMessage'));
                    return false;
                }
                writeoff.receivableStatementAdd.store.moveFirst();
            }
        }
    ]

});


//按单号制作
Ext.define('Foss.writeoff.productionOrderForm',{
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
            fieldLabel:writeoff.receivableStatementAdd.i18n('foss.stl.writeoff.common.waybillNo'),//运单单号
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
                text:writeoff.receivableStatementAdd.i18n('foss.stl.writeoff.common.reset'), //重置
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
                    text:writeoff.receivableStatementAdd.i18n('foss.stl.writeoff.common.query'),//查询
                    columnWidth:.1,
                    cls:'yellow_button',
                    handler:function(){
                        var me = this;
                        var form = this.up('form').getForm();
                        //需要传入后台的参数
                        var waybillNo = form.findField('waybillNo').getValue();     //单号
                        if(waybillNo==null||waybillNo==""){
                            Ext.Msg.alert(writeoff.receivableStatementAdd.i18n('foss.stl.writeoff.common.alert'),"单号不能为空！");
                            return;
                        }
                        //点击查询触发事件
                        writeoff.receivableStatementAdd.store.moveFirst();
                    }

                }]
        }
    ]

});


//定义全局的查询form
//按日期查询
writeoff.receivableStatementAdd.producedByPartnerForm = Ext.create('Foss.writeoff.producedByPartnerForm');
//按单号查询
writeoff.receivableStatementAdd.productionOrderForm = Ext.create('Foss.writeoff.productionOrderForm');

//定义Tab
Ext.define('Foss.writeoff.receivableStatementAddTab',{
    extend:'Ext.tab.Panel',
    frame : false,
    bodyCls : 'autoHeight',
    cls : 'innerTabPanel',
    activeTab : 0,
    columnWidth: 1,
    //columnHeight: 'autoHeight',
    height:230,
    items: [{
        title:writeoff.receivableStatementAdd.i18n('foss.stl.writeoff.partnerStatementOfAwardAdd.queryByPartner'),   //按合伙人制作
        itemId:'producedByPartnerId',
        tabConfig : {
            width : 120
        },
        layout : 'fit',
        items : [
            writeoff.receivableStatementAdd.producedByPartnerForm
        ]
    },{
        title:writeoff.receivableStatementAdd.i18n('foss.stl.writeoff.statementAdd.makeByNumber'),//按单号制作
        itemId:'productionOrderId',
        tabConfig : {
            width : 120
        },
        layout : 'fit',
        items : [
            writeoff.receivableStatementAdd.productionOrderForm
        ]
    }]
});


/**
 * 基本对账单信息
 */
Ext.define('Foss.receivableStatement.BaseInfo',{
    extend:'Ext.form.Panel',
    layout:'column',
    frame:true,
    hidden:true,
    defaultType:'textfield',
    layout:'column',
    defaults:{
        labelWidth:65,
        margin:'5 5 5 10',
        readOnly:true
    },
    items:[{
        fieldLabel:writeoff.receivableStatementAdd.i18n('foss.stl.writeoff.statementAdd.billType'),//对账单类型
        name:'billType',
        xtype:'combo',
        labelWidth:80,
        columnWidth:.22,
        id:'Foss.receivableStatement.billType',
        store:null,
        queryModel:'local',
        displayField:'valueName',
        valueField:'valueCode'
    },{
        xtype:'datefield',
        fieldLabel:writeoff.receivableStatementAdd.i18n('foss.stl.writeoff.statementAdd.createTime'),//制单时间
        name:'createTime',
        format:'Y-m-d H:i:s',
        columnWidth:.28
    },{
        xtype:'commoncustomerselector',
        //listWidth:380,
        fieldLabel:writeoff.receivableStatementAdd.i18n('foss.stl.writeoff.partnerPayStatementEdit.customerCode'),//合伙人编码
        labelWidth:80,
        singlePeopleFlag:'Y',
        name:'contractOrgCode',
        id:'Foss.receivableStatement.contractOrgCode',
        columnWidth:.26
    },{
        fieldLabel:writeoff.receivableStatementAdd.i18n('foss.stl.writeoff.statementAdd.confirmStatus'),//确认状态
        name:'confirmStatus',
        id:'Foss.receivableStatement.confirmStatus',
        xtype:'combo',
        store:null,
        queryModel:'local',
        displayField:'valueName',
        valueField:'valueCode',
        columnWidth:.24,
        value:''
    },{
        fieldLabel:writeoff.receivableStatementAdd.i18n('foss.stl.writeoff.statementAdd.periodBeginDate'),//账单开始日期
       // listWidth:380,
        labelWidth:100,
        name:'periodBeginDate',
        id:'Foss.receivableStatement.periodBeginDate',
        xtype:'datefield',
        format:'Y-m-d',
        columnWidth:.22
    },{
        fieldLabel:writeoff.receivableStatementAdd.i18n('foss.stl.writeoff.statementAdd.companyName'),//所属公司
        name:'companyName',
        id:'Foss.receivableStatement.companyName',
        columnWidth:.28
    },{
        fieldLabel: writeoff.receivableStatementAdd.i18n('foss.stl.writeoff.statementAdd.companyCode'),//所属公司编码
        name: 'companyCode',
        id: 'Foss.receivableStatement.companyCode',
        hidden:true,
        columnWidth: .28
    },{
        fieldLabel:writeoff.receivableStatementAdd.i18n('foss.stl.writeoff.partnerPayStatementEdit.partnerName'),//合伙人名称
        name:'contractOrgName',
        labelWidth:80,
        id:'Foss.receivableStatement.contractOrgName',
        columnWidth:.30
    },{
        fieldLabel:writeoff.receivableStatementAdd.i18n('foss.stl.writeoff.statementAdd.confirmTime'),//确认时间
        name:'customerName',
        id:'Foss.receivableStatement.customerName',
        labelWidth:95,
        columnWidth:.20
    },{
        fieldLabel:writeoff.receivableStatementAdd.i18n('foss.stl.writeoff.statementAdd.periodEndDate'),//账期结束时间
        name:'periodEndDate',
        labelWidth:100,
        id:'Foss.receivableStatement.periodEndDate',
       // listWidth:380,
        xtype:'datefield',
        format:'Y-m-d',
        columnWidth:.30
    },{
        fieldLabel:writeoff.receivableStatementAdd.i18n('foss.stl.writeoff.statementAdd.createOrgName'),//所属部门
        name:'createOrgName',
        id:'Foss.receivableStatement.createOrgName',
        columnWidth:.28
    },{
        fieldLabel:writeoff.receivableStatementAdd.i18n('foss.stl.writeoff.statementAdd.createOrgName'),//所属部门
        name:'createOrgCode',
        id:'Foss.receivableStatement.createOrgCode',
        hidden:true,
        columnWidth:.28
    },{
        fieldLabel:writeoff.receivableStatementAdd.i18n('foss.stl.writeoff.statementAdd.statementBillNo'),//对账单号
        name:'statementBillNo',
        id:'Foss.receivableStatement.statementBillNo',
        columnWidth:.30
    },{
        xtype: 'component',
        border:true,
        autoEl: {
            tag: 'hr'
        },
        columnWidth:1
    },{
        fieldLabel:writeoff.receivableStatementAdd.i18n('foss.stl.writeoff.statementAdd.unifiedCode'),//部门标杆编码
        labelWidth:85,
        name:'unifiedCode',
        id:'Foss.receivableStatement.unifiedCode',
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
                html:'<span style="color:red;">'+'请在汇款时备注我司的部门标杆编码'+'</span>'
            }
        }]
    },{
        fieldLabel:writeoff.receivableStatementAdd.i18n('foss.stl.writeoff.statementAdd.companyAccountBankNo'),//账号
        name:'companyAccountBankNo',
        id:'Foss.receivableStatement.companyAccountBankNo',
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
                html:'<span style="color:red;">'+'此账号为我司唯一的还款账号'+'</span>'
            }
        }]
    },{
        fieldLabel:writeoff.receivableStatementAdd.i18n('foss.stl.writeoff.statementAdd.accountUserName'),//开户名
        name:'accountUserName',
        id:'Foss.receivableStatement.accountUserName',
        columnWidth:.5
    },{
        fieldLabel:writeoff.receivableStatementAdd.i18n('foss.stl.writeoff.statementAdd.bankBranchName'),//省市支行
        name:'bankBranchName',
        id:'Foss.receivableStatement.bankBranchName',
        columnWidth:.48
    },{
        xtype: 'component',
        border:true,
        autoEl: {
            tag: 'hr'
        },
        columnWidth:1
    },{
        fieldLabel:writeoff.receivableStatementAdd.i18n('foss.stl.writeoff.statementAdd.unpaidAmount'),//本期剩余还款金额
        labelWidth:110,
        name:'unpaidAmount',
        id:'Foss.receivableStatement.unpaidAmount',
        xtype:'numberfield',
        decimalPrecision:2,
        columnWidth:.5,
        value:0
    },{
        fieldLabel:writeoff.receivableStatementAdd.i18n('foss.stl.writeoff.statementAdd.settleNum'),//结账次数
        columnWidth:.48,
        name:'settleNum',
        id:'Foss.receivableStatement.settleNum',
        xtype:'numberfield',
        value:0
    }]
});

//定义grid
Ext.define('Foss.writeoff.receivableStatementGrid',{
    extend:'Ext.grid.Panel',
    title:writeoff.receivableStatementAdd.i18n('foss.stl.writeoff.statementAdd.periodCurrentEntry'),//对账单本期明细
    //hidden:true,
    frame:true,
    height:600,
    store:null,//,
    bodyCls: 'autoHeight',
    cls: 'autoHeight',
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
            name:'totalRows',
            id:'Foss.receivableStatement.totalRows',
            columnWidth:.9,
            labelWidth:60,
            fieldLabel:writeoff.receivableStatementAdd.i18n('foss.stl.writeoff.statementAdd.totalCount') //总条数
        },{
            xtype:'container',
            border:false,
            html:'&nbsp;',
            columnWidth:.9
        },{
            xtype:'button',
            text:writeoff.receivableStatementAdd.i18n('foss.stl.writeoff.statementAdd.createStatement'),//生成对账单
            id:'Foss.slt.receivableStatementAdd',
            columnWidth:.1,
            handler:writeoff.receivableStatementAdd.statementSave
            //,
            // hidden:!writeoff.statementAdd.isPermission('/stl-web/writeoff/addStatement.action')
        }]
    }],
    columns: [{
            header:writeoff.receivableStatementAdd.i18n('foss.stl.writeoff.statementAdd.businessDate'),//业务日期
            dataIndex:'businessDate',
            xtype : 'datecolumn',
            format : 'Y-m-d H:i:s'
        },{
            header:writeoff.receivableStatementAdd.i18n('foss.stl.writeoff.common.waybillNo'),//运单号
            dataIndex:'waybillNo'
        },{
            header:writeoff.receivableStatementAdd.i18n('foss.stl.writeoff.common.receivableNoCol'),//应收单号
            dataIndex:'receivableNo',
            hidden:true
         },{
            header:writeoff.receivableStatementAdd.i18n('foss.stl.writeoff.statementAdd.arrvRegionCode'),//目的站
            dataIndex:'targetOrgCode'
        },{
            header:writeoff.receivableStatementAdd.i18n('foss.stl.writeoff.statementAdd.productCode'),//产品类型
            dataIndex:'productCode',
            renderer:writeoff.receivableStatementAdd.productCode
        },{
            header:writeoff.receivableStatementAdd.i18n('foss.stl.writeoff.statementAdd.qty'),//件数
            dataIndex:'goodsQtyTotal'
        },{
            header:writeoff.receivableStatementAdd.i18n('foss.stl.writeoff.statementAdd.billWeight'),//计费重量
            dataIndex:'billWeight'
        },{
            header:writeoff.receivableStatementAdd.i18n('foss.stl.writeoff.statementAdd.billingVolume'),//计费体积
            dataIndex:'goodsVolumeTotal'
        },{
            header:writeoff.receivableStatementAdd.i18n('foss.stl.writeoff.statementAdd.receiveMethod'),//提货方式
            dataIndex:'receiveMethod',
            renderer:writeoff.receivableStatementAdd.receiveMethod
        },{
            header:writeoff.receivableStatementAdd.i18n('foss.stl.writeoff.statementAdd.paymentType'),//付款方式
            dataIndex:'paymentType',
            renderer:writeoff.receivableStatementAdd.paymentType
        },{
            header:writeoff.receivableStatementAdd.i18n('foss.stl.writeoff.common.originalUnverifyAmount'),//原始未核销金额
            dataIndex:'unverifyAmount'
        },{
            header:writeoff.receivableStatementAdd.i18n('foss.stl.writeoff.common.totalAmount'),//总金额
            dataIndex:'amount'
        },{
            header:writeoff.receivableStatementAdd.i18n('foss.stl.writeoff.homeStatementAdd.payableNo'),//单号
            dataIndex:'sourceBillNo'
        },{
            header:writeoff.receivableStatementAdd.i18n('foss.stl.writeoff.homeStatementAdd.billOrgName'),//单据子类型
            dataIndex:'billType',
            renderer:writeoff.receivableStatementAdd.billType
        },{
            header:writeoff.receivableStatementAdd.i18n('foss.stl.writeoff.customersNotReconciled.customerCode'),//客户编码
            dataIndex:'customerCode'
        },{
            header:writeoff.receivableStatementAdd.i18n('foss.stl.writeoff.customersNotReconciled.customerName'),//客户名称
            dataIndex:'customerName'
        },{
            header:writeoff.receivableStatementAdd.i18n('foss.stl.writeoff.woodenStatementAdd.verifyAmount'),//已核销金额
            dataIndex:'verifyAmount'
        },{
            header:writeoff.receivableStatementAdd.i18n('foss.stl.writeoff.woodenStatementEdit.createOrgCode'),//部门编码
            dataIndex:'createOrgName'
        },{
            header:writeoff.receivableStatementAdd.i18n('foss.stl.writeoff.woodenStatementEdit.createOrgName'),//部门名称
            dataIndex:'createOrgCode'
        },{
            header:writeoff.receivableStatementAdd.i18n('foss.stl.writeoff.common.accountDate'),//记账日期
            dataIndex:'accountDate',
            xtype : 'datecolumn',
            format : 'Y-m-d H:i:s'
        },{
            header:writeoff.receivableStatementAdd.i18n('foss.stl.writeoff.statementAdd.customerPickupOrgName'),//提货网点
            dataIndex:'customerPickupOrgCode'
        },{
            header:writeoff.receivableStatementAdd.i18n('foss.stl.writeoff.statementAdd.goodsName'),//货物名称
            dataIndex:'goodsName'
        },{
            header:writeoff.receivableStatementAdd.i18n('foss.stl.writeoff.statementAdd.origOrgCode'),//始发网点编码
            dataIndex:'origOrgCode'
        },{
            header:writeoff.receivableStatementAdd.i18n('foss.stl.writeoff.statementAdd.origOrgName'),//始发网点名称
            dataIndex:'origOrgName'
        },{
            header:writeoff.receivableStatementAdd.i18n('foss.stl.writeoff.statementAdd.destOrgName'),//到达部门名称
            dataIndex:'destOrgCode'
        },{
            header:writeoff.receivableStatementAdd.i18n('foss.stl.writeoff.statementAdd.destOrgCode'),//到达部门编码
            dataIndex:'destOrgName'
        },{
            header:writeoff.receivableStatementAdd.i18n('foss.stl.writeoff.statementAdd.deliveryCustomerCode'),//发货客户编码
            dataIndex:'deliveryCustomerCode'
        },{
            header:writeoff.receivableStatementAdd.i18n('foss.stl.writeoff.statementAdd.signDate'),//签收日期
            dataIndex:'conrevenDate',
            xtype : 'datecolumn',
            format : 'Y-m-d H:i:s'
        },{
            header:writeoff.receivableStatementAdd.i18n('foss.stl.writeoff.common.approveStatus'),//审核状态
            dataIndex:'statementStatus'
        },{
            header:writeoff.receivableStatementAdd.i18n('foss.stl.writeoff.common.notes'),//备注
            dataIndex:'notes'
        },{
            header:writeoff.receivableStatementAdd.i18n('foss.stl.writeoff.statementAdd.isDelete'),//是否删除
            dataIndex:'isDelete'
        },{
            header:writeoff.receivableStatementAdd.i18n('foss.stl.writeoff.statementAdd.statementBillNo'),//对账单号
            dataIndex:'statementBillNo'
        },{
            header:writeoff.receivableStatementAdd.i18n('foss.stl.writeoff.common.createTime'),//运单开单时间
            dataIndex:'createTime',
            xtype : 'datecolumn',
            format : 'Y-m-d H:i:s'
        }],
    initComponent:function(config) {
        var me = this;
        cfg = Ext.apply({}, config);
        me.store = Ext.create('Foss.writeoff.receivableStatementStore');
        //自定义分页控件
        me.bbar = Ext.create('Deppon.StandardPaging',{
            store : me.store,
            pageSize : 20,
            maximumSize : 100,
            plugins : Ext.create('Deppon.ux.PageSizePlugin', {
                sizeList : [['20', 20], ['50', 50],  ['100', 100]]
            })
        });
        writeoff.receivableStatementAdd.store =me.bbar;
        me.callParent([cfg]);
    }
});

//定义主函数
Ext.onReady(function(){
    Ext.QuickTips.init();
    if(Ext.getCmp('T_writeoff-receivableStatementAdd_content')){
        return;
    }
    //创建一个Tab
    writeoff.receivableStatementAdd.queryTab=Ext.create('Foss.writeoff.receivableStatementAddTab');
    //基本对账单信息
    writeoff.receivableStatementAdd.BaseInfo = Ext.create('Foss.receivableStatement.BaseInfo');
    //创建grid
    writeoff.receivableStatementAdd.grid = Ext.create('Foss.writeoff.receivableStatementGrid');
    //创建整个对账单面板，将上面几块包含在一起
    Ext.create('Ext.panel.Panel',{
        id: 'T_writeoff-receivableStatementAdd_content',
        cls: "panelContentNToolbar",
        bodyCls: 'panelContentNToolbar-body',
        layout: 'auto',
        items: [writeoff.receivableStatementAdd.queryTab,writeoff.receivableStatementAdd.BaseInfo,writeoff.receivableStatementAdd.grid],
        renderTo: 'T_writeoff-receivableStatementAdd-body'
    });
})