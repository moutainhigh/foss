/**
 * Created by 073615 on 2014/12/23.
 */
/**
 * 定义数据展示model
 */
Ext.define('Foss.debitWillOver.debitWillOverModel',{
    extend:'Ext.data.Model',
    fields:[{
        name:'id',
        type:'String'
    },{
        name:'bigRegionName',
        type:'String'
    },{
        name:'smallRegionName',
        type:'String'
    },{
        name:'deptName',
        type:'String'
    },{
        name:'customerCode',
        type:'String'
    },{
        name:'customerName',
        type:'String'
    },{
        name:'customerType',
        type:'String'
    },{
        name:'paymentType',
        type:'String'
    },{
        name:'minDebitTime',
        type:'date',
        convert:function(value){
            if(value != null){
                return new Date(value);
            }else {
                return null;
            }
        }
    },{
        name:'debitDays',
        type:'int'
    },{
        name:'remainDays',
        type:'int'
    }]
});
/**
 * 定义store
 */
Ext.define('Foss.debitWillOver.debitWillOverStore',{
    extend:'Ext.data.Store',
    model:'Foss.debitWillOver.debitWillOverModel',
    proxy:{
        type:'ajax',
        url:pay.realPath('debitWillOverQuery.action'),
        actionMethods:'post',
        reader:{
            type:'json',
            root:'vo.list',
            totalProperty:'totalCount'
        },
        timeout:10*60*1000,
        constructor:function(config){
            var me = this;
            var cfg = Ext.apply({},config);
            me.listeners = {
                'beforeLoad':function(store,opration,eOpts){
                    var grid = Ext.getCmp('T_pay-debitWillOver_content').getQueryForm().getForm();
                    var queryBtn = me;
                    //大区
                    bigRegin = form.findField('bigArea').getValue();
                    //小区
                    smallRegin = form.findField('smallArea').getValue();
                    //部门
                    deptCode = form.findField('department').getValue();
                    //客户编码
                    customerCode= form.findField('customerName').getValue()
                    ||form.findField('vehAgencyCode').getValue()
                    ||form.findField('airAgenyCode').getValue()
                    ||form.findField('airlineName').getValue()
                    ||form.findField('landStowage').getValue()
                    ||form.findField('packagingCode').getValue();
                    var params = {
                        'vo.dto.bigRegion':bigRegin,
                        'vo.dto.smallRegion':smallRegin,
                        'vo.dto.deptCode':deptCode,
                        'vo.dto.customerCode':customerCode
                    };
                    Ext.apply(opration,{params:params});
                }
            };
            me.callParent([cfg]);
        }
    },listeners: {
        'beforeLoad': function (store, operation, eOpts) {
            var form = Ext.getCmp('T_pay-debitWillOver_content').getQueryForm().getForm();
            //大区
            bigRegin = form.findField('bigArea').getValue();
            //小区
            smallRegin = form.findField('smallArea').getValue();
            //部门
            deptCode = form.findField('department').getValue();
            //客户编码
            customerCode= form.findField('customerName').getValue()
            ||form.findField('vehAgencyCode').getValue()
            ||form.findField('airAgenyCode').getValue()
            ||form.findField('airlineName').getValue()
            ||form.findField('landStowage').getValue()
            ||form.findField('packagingCode').getValue();
            var params = {
                'vo.dto.bigRegion':bigRegin,
                'vo.dto.smallRegion':smallRegin,
                'vo.dto.deptCode':deptCode,
                'vo.dto.customerCode':customerCode
            };

            Ext.apply(operation, {
                params: params
            });
        }
    }
});

Ext.define('Foss.debitWillOver.CustomerTypeStore',
    {extend: 'Ext.data.Store',
        fields: ['customerTypeCode', 'customerTypeName'],
        data: {
            'items': [
                {customerTypeCode: '00',customerTypeName: pay.debitWillOver.i18n('foss.stl.pay.debitWillOver.all')},
                {customerTypeCode: '01',customerTypeName: pay.debitWillOver.i18n('foss.stl.pay.debitWillOver.customerType')},
                {customerTypeCode: '02',customerTypeName: pay.debitWillOver.i18n('foss.stl.pay.debitWillOver.PLagent')},
                {customerTypeCode: '03',customerTypeName: pay.debitWillOver.i18n('foss.stl.pay.debitWillOver.AIRagent')},
                {customerTypeCode: '04',customerTypeName: pay.debitWillOver.i18n('foss.stl.pay.debitWillOver.airCompany')},
                {customerTypeCode: '05',customerTypeName: pay.debitWillOver.i18n('foss.stl.pay.debitWillOver.epxressAgent')},
                {customerTypeCode: '06',customerTypeName: pay.debitWillOver.i18n('foss.stl.pay.debitWillOver.packageAgent')}]},
        proxy: {type: 'memory',reader: {type: 'json',root: 'items'}}});

pay.debitWillOver.getQueryResult = function(me,form){
    var grid = Ext.getCmp('T_pay-debitWillOver_content').getQueryGrid();
    var queryBtn = me;
    //大区
    bigRegin = form.findField('bigArea').getValue();
    //小区
    smallRegin = form.findField('smallArea').getValue();
    //部门
    deptCode = form.findField('department').getValue();
    //客户编码
    customerCode= form.findField('customerName').getValue()
    ||form.findField('vehAgencyCode').getValue()
    ||form.findField('airAgenyCode').getValue()
    ||form.findField('airlineName').getValue()
    ||form.findField('landStowage').getValue()
    ||form.findField('packagingCode').getValue();
    var params = {
        'vo.dto.bigRegion':bigRegin,
        'vo.dto.smallRegion':smallRegin,
        'vo.dto.deptCode':deptCode,
        'vo.dto.customerCode':customerCode
    };
    grid.setSubmitParams(params);
    grid.store.loadPage(1,{
        callback: function(records, operation, success) {
            var rawData = grid.store.proxy.reader.rawData;
            if (!success && rawData.isException) {
                Ext.Msg.alert("提示", rawData.message);
                return false;
            }
        }
    });
};

/**
 * 定义查询Form
 */
Ext.define('Foss.debitWillOver.debitWillOverForm',{
    extend:'Ext.form.Panel',
    frame:true,
    title:pay.debitWillOver.i18n('foss.stl.pay.common.query'),
    bodyCls:'autoHeight',
    default:{
        margin:'10 5 10 5',
        labelWidth:90,
        colspan:1,
        xtype:'textfield',
        columnWidth:.3
    },
    layout:'column',
    items:[{
        //大区
        xtype: 'linkagecomboselector',
        eventType: ['focus'],
        itemId: 'Foss_baseinfo_BigRegion_ID',
        store: Ext.create('Foss.baseinfo.commonSelector.BigRegionStore'),
        fieldLabel: pay.debitWillOver.i18n('foss.stl.pay.debitWillOver.bigRegin'),
        value: '',
        minChars: 0,
        displayField: 'name',
        valueField: 'code',
        queryParam: 'commonOrgVo.name',
        name: 'bigArea',
        allowBlank: true,
        columnWidth: .3,
        listWidth: 300,
        isPaging: true},{
        //小区
        xtype: 'linkagecomboselector',
        eventType: ['callparent'],
        store: Ext.create('Foss.baseinfo.commonSelector.SmallRegionStore'),
        name: 'smallArea',
        fieldLabel: pay.debitWillOver.i18n('foss.stl.pay.debitWillOver.smallRegin'),
        parentParamsAndItemIds: {'commonOrgVo.code': 'Foss_baseinfo_BigRegion_ID'},
        minChars: 0,
        displayField: 'name',
        valueField: 'code',
        queryParam: 'commonOrgVo.name',
        allowBlank: true,
        columnWidth: .3,
        listWidth: 300,
        isPaging: true},{
        //部门
        xtype: 'dynamicorgcombselector',
        name: 'department',
        fieldLabel: pay.debitWillOver.i18n('foss.stl.pay.debitWillOver.department'),
        allowBlank: true,
        columnWidth: .3,
        value:stl.currentDept.code,
        rawValue:stl.currentDept.name,
        isPaging: true},{
        xtype:'container',
        height: 12,
        columnWidth:1
    },{
        xtype: 'combobox',
        name: 'customerType',
        columnWidth: .3,
        fieldLabel: pay.debitWillOver.i18n('foss.stl.pay.debitWillOver.customerStyle'),
        store: Ext.create('Foss.debitWillOver.CustomerTypeStore'),
        queryModel: 'local',
        value: '00',
        displayField: 'customerTypeName',
        valueField: 'customerTypeCode',
        listeners: {'change': function(th, newValue, oldValue) {
            var form, customerCode, vehAgencyCode, airAgenyCode, airlineName, landStowage, packagingCode;
            form = th.up('form').getForm();
            customerCode = form.findField('customerName');
            vehAgencyCode = form.findField('vehAgencyCode');
            airAgenyCode = form.findField('airAgenyCode');
            airlineName = form.findField('airlineName');
            landStowage = form.findField('landStowage');
            packagingCode = form.findField('packagingCode');
            var arrCode = ['01', '02', '03', '04', '05', '06'];
            var arrEl = [customerCode, vehAgencyCode, airAgenyCode, airlineName, landStowage, packagingCode];
            for (var i = 0; i < arrCode.length; i++) {
                if (newValue == '00') {
                    if (arrCode[i] == '01') {
                        arrEl[i].show();
                        arrEl[i].setValue("");
                        arrEl[i].setReadOnly(true);
                    } else {
                        arrEl[i].hide();
                        arrEl[i].setValue("");
                        arrEl[i].setReadOnly(true);
                    }
                } else {
                    if (arrCode[i] == newValue) {
                        arrEl[i].show();
                        arrEl[i].setValue("");
                        arrEl[i].setReadOnly(false);
                    } else {
                        arrEl[i].hide();
                        arrEl[i].setValue("");
                        arrEl[i].setReadOnly(true);
                    }
                }
            }
        }}},{
        //客户
        xtype: 'commoncustomerselector',
        all: 'true',
        name: 'customerName',
        fieldLabel: pay.debitWillOver.i18n('foss.stl.pay.debitWillOver.customerType'),
        allowBlank: true,
        isPaging: true,
        readOnly: true,
        columnWidth: .3,
        height: 24,
        singlePeopleFlag: 'Y'},{
        xtype: 'commonLdpAgencyCompanySelector',
        fieldLabel: pay.debitWillOver.i18n('foss.stl.pay.debitWillOver.epxressAgent'),
        name: 'landStowage',
        isPaging: true,
        columnWidth: .3,
        height: 24,
        hidden: true},{xtype: 'commonairagencycompanyselector',
        all: 'true',
        fieldLabel: pay.debitWillOver.i18n('foss.stl.pay.debitWillOver.AIRagent'),
        name: 'airAgenyCode',
        isPaging: true,
        columnWidth: .3,
        height: 24,
        hidden: true},{
        xtype: 'commonvehagencycompselector',
        all: 'true',
        fieldLabel: pay.debitWillOver.i18n('foss.stl.pay.debitWillOver.PLagent'),
        name: 'vehAgencyCode',
        isPaging: true,
        columnWidth: .3,
        height: 24,
        hidden: true},{
        xtype: 'commonairlinesselector',
        all: 'true',
        fieldLabel: pay.debitWillOver.i18n('foss.stl.pay.debitWillOver.airCompany'),
        name: 'airlineName',
        isPaging: true,
        columnWidth: .3,
        height: 24,
        hidden: true},{
        xtype: 'dynamicPackagingSupplierSelector',
        name: 'packagingCode',
        fieldLabel: pay.debitWillOver.i18n('foss.stl.pay.debitWillOver.packageAgent'),
        active: 'Y',
        columnWidth: .3,
        height: 24,
        hidden: true},
        {
            xtype:'container',
            height: 24,
            columnWidth:1
        }, {
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
                    this.up('form').getForm().findField('department').setCombValue(stl.currentDept.name,stl.currentDept.code);

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
                        pay.debitWillOver.getQueryResult(me,form);
                    }else{
                        Ext.Msg.alert("温馨提示","请检查参数是否合法！");
                        return;
                    }
                }
            }]
        }
    ]
});
/**
 * 定义展示grid
 */
Ext.define('Foss.debitWillOver.debitWillOverGrid',{
    extend:'Ext.grid.Panel',
    title:'结果',
    emptyText:'查询结果为空',
    cls:'autoHeight',
    autoScroll:true,
    frame:true,
    height:650,
    store:Ext.create('Foss.debitWillOver.debitWillOverStore',
        {storeId:'debitWillOverStoreID'}),
    columns:[{
        header:pay.debitWillOver.i18n('foss.stl.pay.debitWillOver.bigRegin'),
        dataIndex:'bigRegionName'
    },{
        header:pay.debitWillOver.i18n('foss.stl.pay.debitWillOver.smallRegin'),
        dataIndex:'smallRegionName'
    },{
        header:pay.debitWillOver.i18n('foss.stl.pay.debitWillOver.department'),
        dataIndex:'deptName'
    },{
        header:pay.debitWillOver.i18n('foss.stl.pay.debitWillOver.customerCode'),
        dataIndex:'customerCode'
    },{
        header:pay.debitWillOver.i18n('foss.stl.pay.debitWillOver.customerName'),
        dataIndex:'customerName'
    },{
        header:pay.debitWillOver.i18n('foss.stl.pay.debitWillOver.customerType'),
        dataIndex:'customerType',
        renderer:function(value){
            if(value=='DT'){
                return pay.debitWillOver.i18n('foss.stl.pay.debitWillOver.debitCustomer');
            }else if(value=='CT'){
                return pay.debitWillOver.i18n('foss.stl.pay.debitWillOver.creditCustomer');
            }else{
                return value;
            }

        }

    },{
        header:pay.debitWillOver.i18n('foss.stl.pay.debitWillOver.paymentType'),
        dataIndex:'paymentType',
        renderer: function(value) {
            var displayField = FossDataDictionary.rendererSubmitToDisplay(value, 'SETTLEMENT__PAYMENT_TYPE');
            return displayField;
        }
    },{
        header:pay.debitWillOver.i18n('foss.stl.pay.debitWillOver.minDebitDate'),
        dataIndex:'minDebitTime',
        xtype: 'datecolumn',
        format: 'Y-m-d H:i:s'
    },{
        header:pay.debitWillOver.i18n('foss.stl.pay.debitWillOver.debtDays'),
        dataIndex:'debitDays'
    },{
        header:pay.debitWillOver.i18n('foss.stl.pay.debitWillOver.remindDays'),
        dataIndex:'remainDays'
    }],
    bbar:['->',
        {
            text:'导出',
            height:20,
            xtype:'button',
            handler:function(){
                var me = this;

                var grid = me.up('grid');
                var params= grid.submmitParams;
                //创建新Form
                if(!Ext.fly('debitWillOverExport')){
                    var frm = document.createElement('form');
                    frm.id = 'debitWillOverExport';
                    frm.style.display='none';
                    document.body.appendChild(frm);
                }
                Ext.Ajax.request({
                    url:pay.realPath('debitWillOverExport.action'),
                    form:'debitWillOverExport',
                    method:'post',
                    params:params,
                    isUpload:true,
                    success:function(response){
                        var jsonText = Ext.decode(response.responseText.trim());
                        if(!Ext.isEmpty(jsonText.message)){
                            Ext.Msg.alert(pay.debitWillOver.i18n('foss.stl.pay.common.alert'),jsonText.message);
                            return ;
                        }
                    },
                    failure:function(response){
                        var jsonText = Ext.decode(response.responseText.trim());
                        Ext.MessageBox.alert(pay.debitWillOver.i18n('foss.stl.pay.common.alert'),jsonText.message);
                        return;
                    },
                    exception:function(response){
                        var jsonText = Ext.decode(response.responseText.trim());
                        Ext.MessageBox.alert(pay.debitWillOver.i18n('foss.stl.pay.common.alert'),jsonText.message);
                        return;
                    }
                });

            }
        },
        {
            xtype:'standardpaging',
            store:'debitWillOverStoreID',
            pageSize:25,
            plugins:Ext.create('Deppon.ux.PageSizePlugin', {
                // 设置分页记录最大值，防止输入过大的数值
                maximumSize : 200,
                sizeList : [ [ '25', 25 ], [ '50', 50 ], [ '100', 100 ], [ '200', 200 ] ]
            })

        }
    ],
    submmitParams:{},
    setSubmitParams:function(submmitParams){
        this.submmitParams = submmitParams;
    }

});
/**
 * 渲染位置
 */
Ext.onReady(function(){
    Ext.QuickTips.init();
    var queryForm = Ext.create('Foss.debitWillOver.debitWillOverForm');
    var queryGrid = Ext.create('Foss.debitWillOver.debitWillOverGrid');
    //设置默认值
    queryForm.getForm().findField('department').setCombValue(stl.currentDept.name,stl.currentDept.code);

    Ext.create('Ext.panel.Panel',{
        id: 'T_pay-debitWillOver_content',
        cls: "panelContentNToolbar",
        bodyCls: 'panelContentNToolbar-body',
        layout: 'auto',
        getQueryForm:function(){
            return queryForm;
        },
        getQueryGrid:function(){
            return queryGrid;
        },
        items: [queryForm,queryGrid],
        renderTo: 'T_pay-debitWillOver-body'
    });

});
