//期间model
Ext.define('Foss.closing.mvrUnifiedSettlement.periodModel',{
    extend:'Ext.data.Model',
    fields:['name','code']
})

Ext.define('Foss.closing.mvrUnifiedSettlement.periodStore',{
    extend:'Ext.data.Store',
    model:'Foss.closing.mvrUnifiedSettlement.periodModel',
    proxy:{
        type:'ajax',
        actionMethod:'post',
        url:closing.realPath('queryClosingPeriod.action'),
        reader:{
            type:'json',
            root:'periodList'
        }

    },
    listeners:{
        load:function(store,operation,eOpt){
            if(operation.length==0){
                Ext.Msg.alert('Tips','没有生成对应的期间');
                return;
            }
        }
    }
})
//期间store
//定义查询Form
Ext.define('Foss.closing.mvrUnifiedSettlement.form',{
    extend:'Ext.form.Panel',
    frame:true,
    title:'查询条件',
    bodyCls:'autoHeight',
    defaults:{
        margin:'10 5 10 5',
        labelWith:90,
        colspan:1,
        xtype:'textfield',
        columnWidth:.3
    },
    layout:'column',
    items:[{
        xtype:'combo',
        name:'period',
        fieldLabel:'查询期间',
        queryModel:'remote',
        store:Ext.create('Foss.closing.mvrUnifiedSettlement.periodStore'),
        displayField:'name',
        valueField:'name',
        allowBlank:false
    },{
        xtype:'combo',
        fieldLabel:'客户类型',
        name:'customerType',
        queryModel:'local',
        store:FossDataDictionary.getDataDictionaryStore('SETTLEMENT__CUSTOMER_TYPE',null,null,
            ['LC','AA','A']),
        labelWidth:85,
        displayField:'valueName',
        valueField:'valueCode',
        value:'LC',
        listeners:{
            'change':function(th,newValue,oldValue){
                //获取表单等控件
                var form,//表单
                    customerCode,
                    customerDetail,
                    agencyDetial;
                //获取表单
                form= this.up('form').getForm();
                //获取下面组件
                lineCustomer = form.findField('lineCustomerCode');
                airCustomer = form.findField('airCustomerCode');
                agencyCustomer = form.findField('agencyCustomerCode');
                if( newValue=='LC'){
                    lineCustomer.show();
                    airCustomer.hide();
                    airCustomer.setValue("");
                    agencyCustomer.hide();
                    agencyCustomer.setValue("");
                }else if(newValue=='A'){
                    lineCustomer.hide();
                    lineCustomer.setValue("");
                    airCustomer.show();
                    agencyCustomer.hide();
                    agencyCustomer.setValue("");
                }else if(newValue=='AA'){
                    lineCustomer.hide();
                    lineCustomer.setValue("");
                    airCustomer.hide();
                    airCustomer.setValue("");
                    agencyCustomer.show();
                }
            }
        }
    },{
        xtype:'commoncustomerselector',
        name:'lineCustomerCode',
        fieldLabel:'客户信息',
        singlePeopleFlag : 'Y',
        listWidth:300,//设置下拉框宽度
        isPaging:true, //分页
        columnWidth : .3
    },{
        xtype : 'commonairlinesselector',
        fieldLabel : '航空公司',
        name : 'airCustomerCode',
        hidden:true,
        listWidth:300,//设置下拉框宽度
        isPaging:true, //分页
        columnWidth : .3
    }, {
        xtype : 'commonallagentselector',
        fieldLabel : '空运代理',
        name : 'agencyCustomerCode',
        hidden:true,
        listWidth:300,//设置下拉框宽度
        isPaging:true, //分页
        columnWidth : .3
    },{
        xtype:'combo',
        name:'orgType',
        fieldLabel:'始发/到达',
        queryModel:'local',
        store:FossDataDictionary.getDataDictionaryStore('VOUCHER__ORG_TYPE',null,[{'valueCode': 'C',
            'valueName': '合同'},{
            'valueCode': '',
            'valueName': '全部'
        }]),
        displayField:'valueName',
        valueField:'valueCode'
    },{
        xtype:'dynamicorgcombselector',
        name:'department',
        fieldLabel:'部门',
        allowBlank:true,
        isPaging:true,
        listWidth:300
    },{
        xtype:'container',
        border:false,
        html:'&nbsp'
    },{
        xtype:'container',
        border:1,
        columnWidth:1,
        colspan:3,
        defaultType:'button',
        layout:'column',
        items:[{
            text:'重置',
            columnWidth:.1,
            handler:function(){
                this.up('form').getForm().reset();
            }
        },{
            xtype:'container',
            html:'&nbsp',
            columnWidth:.65
        },{
            itemId:'queryBtn',
            text:'查询',
            name:'queryBtn',
            columnWidth:.1,
            cls:'yellow_button',
            handler:function(){
                var form = this.up('form').getForm();
                var me = this;
                if(form.isValid()){
                    closing.mvrUnifiedSettlement.getQueryResult(me,form);
                }else{
                    Ext.Msg.alert("温馨提示","请检查参数是否合法！");
                    return;
                }
            }
        }]
    }]
})
//查询并显示数据方法
closing.mvrUnifiedSettlement.getQueryResult = function(me,form){
    var grid = Ext.getCmp('T_closing-mvrUnifiedSettlement_content').getQueryGrid();
    var queryBtn = me;
    //期间
    //closing.mvrUnifiedSettlement.period = form.findField('period').getValue();
    ////客户编码
    //closing.mvrUnifiedSettlement.customerCode = form.findField('lineCustomerCode').getValue()||
    //    form.findField('airCustomerCode').getValue()||form.findField('agencyCustomerCode').getValue();
    ////部门类型 出发/到达
    //closing.mvrUnifiedSettlement.orgType = form.findField('orgType').getValue();
    ////部门编码
    //closing.mvrUnifiedSettlement.orgCode = form.findField('department').getValue();
    //var params = {
    //    'vo.dto.periodId':closing.mvrUnifiedSettlement.period,
    //    'vo.dto.costomerCode':closing.mvrUnifiedSettlement.customerCode,
    //    'vo.dto.orgType':closing.mvrUnifiedSettlement.orgType,
    //    'vo.dto.orgCode':closing.mvrUnifiedSettlement.orgCode
    //};
    //grid.store.on('beforeLoad',function(store, operation, eOpts){
    //    Ext.apply(operation,{
    //        params :params
    //    });
    //});
    //grid.setSubmitParams(params);
    //设置该按钮灰掉
    //queryBtn.disable(false);
    ////30秒后自动解除灰掉效果
    //setTimeout(function() {
    //    queryBtn.enable(true);
    //}, 30000);
    grid.store.removeAll();
    grid.store.loadPage(1,{
        callback: function(records, operation, success) {
            var result =   Ext.decode(operation.response.responseText);
            if (!success && result.isException) {
                Ext.Msg.alert("提示", result.message);
                queryBtn.enable(true);
                return false;
            }
            queryBtn.enable(true);
        }
    });
}
//显示数据Model
Ext.define('Foss.closing.mvrUnifiedSettlement.model',{
    extend:'Ext.data.Model',
    fields:[
        {name:'id' , type:'String'},
        {name:'period',type:'String'},
        {name:'invoiceMark',type:'String'},
        {name:'customerCode',type:'String'},
        {name:'customerName',type:'String'},
        {name:'orgType' ,type:'String'},
        {name:'orgCode',type:'String'},
        {name:'orgName',type:'String'},
        {name:'productCode',type:'String'},
        {name:'uroDestCdNpod' ,type:'String'},
        {name:'uroDestCdPod',type:'String'},
        {name:'urtDestCdNpod',type:'String'},
        {name:'urtDestCdPod',type:'String'},
        {name:'custDroWoOrigRcvtNpod',type:'String'},
        {name:'custDroWoOrigRcvtPod',type:'String'},
        {name:'codPayWoOrigRcvtPod',type:'String'},
        {name:'codPayWoOrigRcvtNpod',type:'String'},
        {name:'codPayWoOthRcvt',type:'String'},
        {name:'claimDestoIncome',type:'String'},
        {name:'claimOrigoWoOrigRcvtPod',type:'String'},
        {name:'claimOrigtOrigRcvoPod',type:'String'},
        {name:'claimOrigoWoOrigRcvtNpod',type:'String'},
        {name:'claimOrigtWoOrigRcvoNpod',type:'String'},
        {name:'claimDesttIncome',type:'String'},
        {name:'claimDestoWoDestRcvoPod',type:'String'},
        {name:'claimDesttWoDestRcvoPod',type:'String'},
        {name:'claimDestoWoDestRcvoNpod',type:'String'},
        {name:'claimDesttWoDestRcvoNpod',type:'String'},
        {name:'claimDestoWoDestRcvtPod',type:'String'},
        {name:'claimDesttWoDestRcvtPod',type:'String'},
        {name:'claimDestoWoDestRcvtNpod',type:'String'},
        {name:'claimDesttWoDestRcvtNpod',type:'String'},
        {name:'claimDestoPayApply',type:'String'},
        {name:'orClaimPayoWoRcvt',type:'String'},
        {name:'orClaimPaytWoRcvo',type:'String'},
        {name:'orCustDroWoRcvt',type:'String'},
        {name:'rdDestoIncome',type:'String'},
        {name:'rdDesttIncome',type:'String'},
        {name:'rdOrigoWoOrigRcvtPod',type:'String'},
        {name:'rdOrigtWoOrigRcvoPod',type:'String'},
        {name:'rdOrigoWoOrigRcvtNpod',type:'String'},
        {name:'rdOrigtWoOrigRcvoNpod',type:'String'},
        {name:'rdDestoPayApply',type:'String'},
        {name:'rdDestoDestRcvoPod',type:'String'},
        {name:'rdDesttWoDestRcvoPod',type:'String'},
        {name:'rdDestoWoDestRcvoNpod',type:'String'},
        {name:'rdDesttWoDestRcvoNpod',type:'String'},
        {name:'rdDestoDestRcvtPod',type:'String'},
        {name:'rdDesttWoDestRcvtPod',type:'String'},
        {name:'rdDestoWoDestRcvtNpod',type:'String'},
        {name:'rdDesttWoDestRcvtNpod',type:'String'},
        {name:'custDrtWoDestRcvoNpod',type:'String'},
        {name:'custDroWoDestRcvoNpod',type:'String'},
        {name:'custDroWoDestRcvoPod',type:'String'},
        {name:'custDrtWoDestRcvoPod',type:'String'},
        {name:'custDroWoDestRcvtNpod',type:'String'},
        {name:'custDrtWoDestRcvtNpod',type:'String'},
        {name:'custDroWoDestRcvtPod',type:'String'},
        {name:'custDrtWoDestRcvtPod',type:'String'},
        {name:'codPayWoDestRcvoPod',type:'String'},
        {name:'codPayWoDestRcvoNpod',type:'String'},
        {name:'codPayWoDestRcvtPod',type:'String'},
        {name:'codPayWoDestRcvtNpod',type:'String'}
    ]
})
//定义store
Ext.define('Foss.closing.mvrUnifiedSettlement.store',{
    extend:'Ext.data.Store',
    model:'Foss.closing.mvrUnifiedSettlement.model',
    paqeSize:100,
    proxy:{
        type:'ajax',
        actionMethods:'post',
        url:closing.realPath('queryByCondition.action'),
        timeout:10*60*1000,
        reader:{
            type:'json',
            root:'vo.dto.list',
            tatalProperty:'totalCount'
        }
    },
    constructor:function(config){
        var me = this;
        var cfg = Ext.apply({},config);
        me.listeners = {
            'beforeLoad':function(store,opration,eOpts){
                var form = Ext.getCmp('T_closing-mvrUnifiedSettlement_content').getQueryForm().getForm();
                //期间
                closing.mvrUnifiedSettlement.period = form.findField('period').getValue();
                //客户编码
                closing.mvrUnifiedSettlement.customerCode = form.findField('lineCustomerCode').getValue()||
                form.findField('airCustomerCode').getValue()||form.findField('agencyCustomerCode').getValue();
                //部门类型 出发/到达
                closing.mvrUnifiedSettlement.orgType = form.findField('orgType').getValue();
                //部门编码
                closing.mvrUnifiedSettlement.orgCode = form.findField('department').getValue();
                var params = {
                    'vo.dto.periodId':closing.mvrUnifiedSettlement.period,
                    'vo.dto.costomerCode':closing.mvrUnifiedSettlement.customerCode,
                    'vo.dto.orgType':closing.mvrUnifiedSettlement.orgType,
                    'vo.dto.orgCode':closing.mvrUnifiedSettlement.orgCode
                };
                Ext.apply(opration,{params:params});

            }
        };
        me.callParent([cfg]);
    }
})

Ext.define('Foss.closing.mvrUnifiedSettlement.resultGrid',{
    extend:'Ext.grid.Panel',
    title:'结果',
    store:Ext.create('Foss.closing.mvrUnifiedSettlement.store',{
        storeId:'UnifiedSettlementstore'
    }),
    emptyText:'查询结果为空',
    cls:'autoHeight',
    autoScroll:true,
    frame:true,
    height:650,
    viewConfig : {
        enableTextSelection : true,
        // 设置行可以选择，进而可以复制
        getRowClass : function(record,rowIndex,rowParams,store){
            if(record.data.period == '汇总'){ // 汇总的样式
                return 'closing-totalBgColor';
            }else{
                return '';
            }
        }
    },
    columns:[{
        text:'统计纬度',
        defaults:{
            style:"text-align:center"
        },
        menuDisabled:true,
        sortable:false,
        columns:[
            {   text:'期间',
                dataIndex:'period'
            },{
                text:'发票标记',
                dataIndex:'invoiceMark',
                renderer:function(value){
                    var displayValue = FossDataDictionary.rendererSubmitToDisplay(
                        value, 'SETTLEMENT_INVOICE_MARK');
                    return displayValue;
                }
            },{
                text:'客户名称',
                dataIndex:'customerName'
            },{
                text:'客户编码',
                dataIndex:'customerCode'
            },{
                text:'产品类型',
                dataIndex:'productCode',
                renderer:Foss.pkp.ProductData.rendererSubmitToDisplay
            },{
                text:'部门类型',
                dataIndex:'orgType',
                renderer:function(value){
                    var displayValue = FossDataDictionary.rendererSubmitToDisplay(value,'VOUCHER__ORG_TYPE');
                    if(value=='C'){
                        displayValue = '合同';
                    }
                    return displayValue;
                }
            },{
                text:'部门编码',
                dataIndex:'orgCode'
            },{
                text:'部门名称',
                dataIndex:'orgName'
            }
        ]
    },{
        text:'还款运单总运费（到付）【01】',
        defaults:{
            style:"text-align:center"
        },
        menuDisabled:true,
        sortable:false,
        columns:[{
            text:'还款银行未签收',
            dataIndex:'uroDestCdNpod'
        },{
            text:'还款银行已签收',
            dataIndex:'uroDestCdPod'
        }]
    },{
        text:'还款运单总运费（到付）【02】',
        defaults:{
            style:'text-align:center'
        },
        menuDisabled:true,
        sortable:false,
        columns:[
            {
                text:'还款银行未签收',
                dataIndex:'urtDestCdNpod'
            },{
                text:'还款银行已签收',
                dataIndex:'urtDestCdPod'
            }
        ]
    },{
        text:'代收货款',
        defaults:{
            style:"text-align:center"
        },
        menuDisabled:true,
        sortable:false,
        columns:[
            {text:'应付代收货款冲01应收到付运费已签收',
                dataIndex:'codPayWoDestRcvoPod'
            },{
                text:'应付代收货款冲01应收到付运费未签收',
                dataIndex:'codPayWoDestRcvoNpod'
            },{
                text:'应付代收货款冲02应收到付运费已签收',
                dataIndex:'codPayWoDestRcvtPod'
            },{
                text:'应付代收货款冲02应收到付运费未签收',
                dataIndex:'codPayWoDestRcvtNpod'
            },
            {
                text:'应付代收货款冲02应收始发运费已签收',
                dataIndex:'codPayWoOrigRcvtPod'
            },{
                text:'应付代收货款冲02应收始发运费未签收',
                dataIndex:'codPayWoOrigRcvtNpod'
            },{
                text:'应付代收货款冲02小票应收',
                dataIndex:'codPayWoOthRcvt'
            }
        ]
    },{
        text:'理赔',
        defaults:{
            style:"text-align:center"
        },
        menuDisabled:true,
        sortable:false,
        columns:[{
            text:'出发部门申请',
            menuDisabled:true,
            sortable:false,
            columns:[{
                text:'01理赔冲02始发应收已签收',
                dataIndex:'claimOrigoWoOrigRcvtPod'
            },{
                text:'02理赔冲01始发应收已签收',
                dataIndex:'claimOrigtOrigRcvoPod'
            },{
                text:'01理赔冲02始发应收未签收',
                dataIndex:'claimOrigoWoOrigRcvtNpod'
            },{
                text:'02理赔冲01始发应收未签收',
                dataIndex:'claimOrigtWoOrigRcvoNpod'
            }]
        },{
            text:'到达部门申请',
            menuDisabled:true,
            sortable:false,
            columns:[{
                text:'01理赔冲收入',
                dataIndex:'claimDestoIncome'
            },{
                text:'02理赔冲收入',
                dataIndex:'claimDesttIncome'
            },{
                text:'01理赔冲01到达应收已签收',
                dataIndex:'claimDestoWoDestRcvoPod'
            },{
                text:'02理赔冲01到达应收已签收',
                dataIndex:'claimDesttWoDestRcvoPod'
            },{
                text:'01理赔冲01到达应收未签收',
                dataIndex:'claimDestoWoDestRcvoNpod'
            },{
                text:'02理赔冲01到达应收未签收',
                dataIndex:'claimDesttWoDestRcvoNpod'
            },{
                text:'01理赔冲02到达应收已签收',
                dataIndex:'claimDestoWoDestRcvtPod'
            },{
                text:'02理赔冲02到达应收已签收',
                dataIndex:'claimDesttWoDestRcvtPod'
            },{
                text:'01理赔冲02到达应收未签收',
                dataIndex:'claimDestoWoDestRcvtNpod'
            },{
                text:'02理赔冲02到达应收未签收',
                dataIndex:'claimDesttWoDestRcvtNpod'
            },{
                text:'01理赔付款申请',
                dataIndex:'claimDestoPayApply'
            }]
        }]
    },{
        text:'预收客户',
        defaults:{
            style:"text-align:center"
        },
        menuDisabled:true,
        sortable:false,
        columns:[{
            text:'01预收客户冲02应收始发运费未签收',
            dataIndex:'custDroWoOrigRcvtNpod'
        },{
            text:'01预收客户冲02应收始发运费已签收',
            dataIndex:'custDroWoOrigRcvtPod'
        },{
            text:'01预收客户冲01应收到付运费未签收',
            dataIndex:'custDroWoDestRcvoNpod'
        },{
            text:'01预收客户冲02应收到付运费未签收',
            dataIndex:'custDroWoDestRcvtNpod'
        },{
            text:'02预收客户冲01应收到付运费未签收',
            dataIndex:'custDrtWoDestRcvoNpod'
        },{
            text:'02预收客户冲02应收到付运费未签收',
            dataIndex:'custDrtWoDestRcvtNpod'
        },{
            text:'01预收客户冲01应收到付运费已签收',
            dataIndex:'custDroWoDestRcvoPod'
        },{
            text:'01预收客户冲02应收到付运费已签收',
            dataIndex:'custDroWoDestRcvtPod'
        },{
            text:'02预收客户冲01应收到付运费已签收',
            dataIndex:'custDrtWoDestRcvoPod'
        },{
            text:'02预收客户冲02应收到付运费已签收',
            dataIndex:'custDrtWoDestRcvtPod'
        }]
    },{
        text:'小票',
        defaults:{
            style:"text-align:center"
        },
        menuDisabled:true,
        sortable:false,
        columns:[
            {
                text:'小票核销',
                menuDisabled:true,
                sortable:false,
                columns:[
                    {
                        text:'01应付理赔冲02小票应收',
                        dataIndex:'orClaimPayoWoRcvt'
                    },{
                        text:'02应付理赔冲01小票应收',
                        dataIndex:'orClaimPaytWoRcvo'
                    },{
                        text:'01预收客户冲02小票应收',
                        dataIndex:'orCustDroWoRcvt'
                    }
                ]
            }
        ]
    },{
        text:'退运费',
        defaults:{
            style:"text-align:center"
        },
        menuDisabled:true,
        sortable:false,
        columns:[{
            text:'出发部门申请',
            menuDisabled:true,
            sortable:false,
            columns:[{
                text:'01退运费冲02始发应收已签收',
                dataIndex:'rdOrigoWoOrigRcvtPod'
            },{
                text:'02退运费冲01始发应收已签收',
                dataIndex:'rdOrigtWoOrigRcvoPod'
            },{
                text:'01退运费冲02始发应收未签收',
                dataIndex:'rdOrigoWoOrigRcvtNpod'
            },{
                text:'02退运费冲01始发应收未签收',
                dataIndex:'rdOrigtWoOrigRcvoNpod'
            }]
        },{
            text:'到达部门申请',
            menuDisabled:true,
            sortable:false,
            columns:[{
                text:'01退运费冲收入',
                dataIndex:'rdDestoIncome'
            },{
                text:'02退运费冲收入',
                dataIndex:'rdDesttIncome'
            },{
                text:'01退运费付款申请',
                dataIndex:'rdDestoPayApply'
            },{
                text:'01退运费冲01到达应收已签收',
                dataIndex:'rdDestoDestRcvoPod'
            },{
                text:'02退运费冲01到达应收已签收',
                dataIndex:'rdDesttWoDestRcvoPod'
            },{
                text:'01退运费冲01到达应收未签收',
                dataIndex:'rdDestoWoDestRcvoNpod'
            },{
                text:'02退运费冲01到达应收未签收',
                dataIndex:'rdDesttWoDestRcvoNpod'
            },{
                text:'01退运费冲02到达应收已签收',
                dataIndex:'rdDestoDestRcvtPod'
            },{
                text:'02退运费冲02到达应收已签收',
                dataIndex:'rdDesttWoDestRcvtPod'
            },{
                text:'01退运费冲02到达应收未签收',
                dataIndex:'rdDestoWoDestRcvtNpod'
            },{
                text:'02退运费冲02到达应收未签收',
                dataIndex:'rdDesttWoDestRcvtNpod'
            }]
        }]
    }],
    bbar:['->',{
        text:'导出',
        height:20,
        xtype:'button',
        handler:function(){
            var me = this;

            var grid = me.up('grid');
            var params= grid.submmitParams;
            //创建新Form
            if(!Ext.fly('mvrUnifiedSettlmentExport')){
                var frm = document.createElement('form');
                frm.id = 'mvrUnifiedSettlmentExport';
                frm.style.display='none';
                document.body.appendChild(frm);
            }
            Ext.Ajax.request({
                url:closing.realPath('exportToExcel.action'),
                form:'mvrUnifiedSettlmentExport',
                method:'post',
                params:params,
                isUpload:true,
                success:function(response){
                    var jsonText = Ext.decode(response.responseText.trim());
                    if(!Ext.isEmpty(jsonText.message)){
                        Ext.Msg.alert('温馨提示',jsonText.message);
                        return ;
                    }
                },
                failure:function(response){
                    var jsonText = Ext.decode(response.responseText.trim());
                    Ext.MessageBox.alert('温馨提示',jsonText.message);
                    return;
                },
                exception:function(response){
                    var jsonText = Ext.decode(response.responseText.trim());
                    Ext.MessageBox.alert('温馨提示',jsonText.message);
                    return;
                }
            });

        }
    },{
        xtype:'standardpaging',
        store:'UnifiedSettlementstore',
        pageSize:100,
        maximumSize:500,
        plugins:'pagesizeplugin'
    }],
    submmitParams:{},
    setSubmitParams:function(submmitParams){
        this.submmitParams = submmitParams;
    }
})
Ext.onReady(function(){
    Ext.QuickTips.init();
    if(Ext.getCmp('T_closing-mvrUnifiedSettlement_content')){
        return;
    }

    //form创建
    var queryForm = Ext.create('Foss.closing.mvrUnifiedSettlement.form');
    //grid 创建
    var queryGrid = Ext.create('Foss.closing.mvrUnifiedSettlement.resultGrid');

    Ext.create('Ext.panel.Panel',{
        id:'T_closing-mvrUnifiedSettlement_content',
        cls : "panelContentNToolbar",
        bodyCls : 'panelContentNToolbar-body',
        layout : 'auto',
        items : [queryForm,queryGrid],
        getQueryForm:function(){
            return queryForm;
        },
        getQueryGrid:function(){
            return queryGrid;
        },
        renderTo:'T_closing-mvrUnifiedSettlement-body'
    });
});


