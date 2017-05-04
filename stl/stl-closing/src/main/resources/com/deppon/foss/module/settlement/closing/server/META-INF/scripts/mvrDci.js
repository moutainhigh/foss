/**
 * 折扣往来报表
 */
/**
 * 声明账期model
 */
Ext.define('Foss.mvrDci.PeriodModel', {
    extend: 'Ext.data.Model',
    fields: ['name', 'code']
});

/**
 * 声明账期store
 */
Ext.define('Foss.mvrDci.PeriodStore', {
    extend: 'Ext.data.Store',
    model: 'Foss.mvrDci.PeriodModel',
    proxy: {
        type: 'ajax',
        url: closing.realPath('queryClosingPeriod.action'),
        actionMethods: 'post',
        reader: {
            type: 'json',
            root: 'periodList'
        }
    },
    constructor: function (config) {
        var me = this,
            cfg = Ext.apply({}, config);
        me.listeners = {
            'load': function (store, operation, eOpts) {

                if (operation.length == 0) {
                    Ext.Msg.alert("提示", "没有生成凭证报表数据，凭证期间为空");
                    return false;
                }
            }
        };
        me.callParent([cfg]);
    }
});


/**
 * 查询方法
 */
closing.mvrDci.query = function (form, me) {
    var m = Ext.getCmp('T_closing-mvrDci_content');
    if (m) {
        var grid = m.getQueryGrid();
        var store = grid.getStore();
        if (store) {
            if (grid.isHidden()) {
                grid.show();
            }
            //设置该按钮灰掉
            me.disable(false);
            //30秒后自动解除灰掉效果
            setTimeout(function () {
                me.enable(true);
            }, 10000);
            // 加载第一页数据
            store.loadPage(1, {
                callback: function (records, operation, success) {
                    var result = Ext.decode(operation.response.responseText);
                    if (!success && result.isException) {
                        Ext.Msg.alert("提示", result.message);
                        me.enable(true);
                        return false;
                    } else {
                        if (!!result.vo.resultDto && result.vo.resultDto.count > 0) {
                            //处理汇总行
                            var totalRowData = {};
                            Ext.apply(totalRowData,result.vo.resultDto);
                            totalRowData.entityList = null;
                            totalRowData.period='汇总';
                            closing.mvrStore.loadData([totalRowData],true);
                        }
                    }

                    me.enable(true);
                }
            });
        }
    }

}

/**
 * 导出
 */
closing.mvrDci.exportDci = function () {
    //获取主面板、查询GRID
    var mainPane = Ext.getCmp('T_closing-mvrDci_content');
    var queryGrid = mainPane.getQueryGrid();

    //提示是否导出
    Ext.Msg.confirm('温馨提示', '确定导出折扣调整往来月报表吗?', function (btn, text) {
        if ('yes' == btn) {

            var params = mainPane.getQueryForm().getValues();

            Ext.apply(params, {
                'vo.queryDto.productCodeList': stl.convertProductCode(mainPane.getQueryForm().getForm().findField('vo.queryDto.productCodeList').getValue())
            });

            //创建一个form
            if (!Ext.fly('exportMvrDciForm')) {
                var frm = document.createElement('form');
                frm.id = 'exportMvrDciForm';
                frm.style.display = 'none';
                document.body.appendChild(frm);
            }

            //导出Ajax请求
            Ext.Ajax.request({
                url: closing.realPath('exportMvrDci.action'),
                form: Ext.fly('exportMvrDciForm'),
                params: params,
                method: 'post',
                isUpload: true,
                success: function (response) {
                    //获取响应的json字符串
                    var jsonText = Ext.decode(response.responseText.trim());
                    //导出失败
                    if (jsonText.message != null && jsonText.message != '') {
                        Ext.Msg.alert('温馨提示', jsonText.message);
                    }
                },
                failure: function (response) {
                    var json = Ext.decode(response.responseText);
                    Ext.MessageBox.alert('温馨提示', jsonText.message);
                },
                exception: function (response) {
                    var json = Ext.decode(response.responseText);
                    Ext.MessageBox.alert('温馨提示', jsonText.message);
                }
            });

        }
    });
}

/**
 * 获取期间控件下拉框Store
 */
closing.mvrDci.getComboPeriodStore = function () {
    return Ext.create('Foss.common.PeriodStore');
}

/**
 * 获取业务类型下拉框Store
 */
closing.mvrDci.getComboProductTypeStore = function () {
    var productStore = Ext.create('Foss.pkp.ProductStore');

    /*for(var i=0;i<productStore.data.length;i++){
     var record = productStore.data.items[i];
     if(record.get('code') == 'AF' || record.get('code') == 'PLF'){ //去掉精准空运，汽运偏线
     productStore.remove(record);
     }
     }*/


    return productStore;
}

/** 数据模型 */

// 下拉框(combo)数据模型
Ext.define('Foss.closing.MvrDciComboModel', {
    extend: 'Ext.data.Model',
    fields: [{
        /* 显示名 */
        name: 'name',
        type: 'string'
    }, {
        /* 实际值 */
        name: 'value',
        type: 'string'
    }]
})

// 折扣调整往来月报表数据模型
Ext.define('Foss.closing.MvrDciModel', {
    extend: 'Ext.data.Model',
    fields: [
        {name:'id' , type:'String'},
        {name:'period',type:'String'},
        {name:'invoiceMark',type:'String'},
        {name:'customerCode',type:'String'},
        {name:'customerName',type:'String'},
        {name:'orgType' ,type:'String'},
        {name:'orgCode',type:'String'},
        {name:'orgName',type:'String'},
        {name:'productCode',type:'String'},
        {name: 'dciDestApplyWoIncomeNus', type: 'decimal'},
        {name: 'dciDestApplyWoIncomeUs', type: 'decimal'},
        {name: 'ldciDestIncomeoNus', type: 'decimal'},
        {name: 'ldciDestIncomeoUs', type: 'decimal'}
    ]
})

//Store
Ext.define('Foss.closing.MvrDciStore', {
    extend: 'Ext.data.Store',
    model: 'Foss.closing.MvrDciModel',
    pageSize: 100,
    proxy: {
        type: 'ajax',
        actionMethods: 'post',
        url: closing.realPath('queryMvrDci.action'),
        timeout: 10 * 60 * 1000,
        reader: {
            type: 'json',
            root: 'vo.resultDto.entityList',
            totalProperty: 'vo.resultDto.count'
        }
    },
    submitParams: {},
    setSubmitParams: function (submitParams) {
        this.submitParams = submitParams;
    },
    constructor: function (config) {
        var me = this,
            cfg = Ext.apply({}, config);
        me.listeners = {
            'beforeload': function (store, operation, eOpts) {
                /* Ext.apply(me.submitParams, {
                 "limit":operation.limit,
                 "page":operation.page,
                 "start":operation.start
                 }); */
                var queryForm = Ext.getCmp('T_closing-mvrDci_content').getQueryForm();
                if (queryForm) {
                    var form = queryForm.getForm();
                    var params = form.getValues();

                    Ext.apply(params, {
                        'vo.queryDto.productCodeList': stl.convertProductCode(form.findField('vo.queryDto.productCodeList').getValue())
                    });

                    Ext.apply(operation, {
                        params: params
                    });
                }

            }
        };
        me.callParent([cfg]);
    }
});

closing.mvrStore = Ext.create('Foss.closing.MvrDciStore');

// 定义查询Form
Ext.define('Foss.closing.MvrDciQueryForm', {
    extend: 'Ext.form.Panel',
    frame: true,
    title: '查询条件',
    bodyCls: 'autoHeight',
    defaults: {
        margin: '10 5 10 5',
        labelWidth: 85,
        colspan: 1,
        columnWidth: 0.33
    },
    defaultType: 'textfield',
    layout: {
        type: 'column',
        columns: 3
    },
    items: [{
        xtype: 'combo',
        name: 'vo.queryDto.period',
        fieldLabel: '期间',
        queryMode: 'remote',
        store: Ext.create('Foss.mvrDci.PeriodStore'),
        displayField: 'name',
        valueField: 'name',
        allowBlank: false
    }, {
        xtype: 'combo',
        name: 'vo.queryDto.productCodeList',
        fieldLabel: '运输性质',
        forceSelection: true,
        multiSelect: true,
        displayField: 'name',
        valueField: 'code',
        queryMode: 'local',
        triggerAction: 'all',
        editable: false,
        value: closing.mvrDci.getComboProductTypeStore().first()
            .get('code'),
        store: closing.mvrDci.getComboProductTypeStore()
    }, {
        xtype: 'commoncustomerselector',
        fieldLabel: '客户信息',
        singlePeopleFlag: 'Y',
        name: 'vo.queryDto.customerCode'
    }, {
        xtype: 'dynamicorgcombselector',
        name: 'vo.queryDto.origOrgCode',
        fieldLabel: '始发部门',
        allowblank: true,
        listWidth: 300,// 设置下拉框宽度
        isPaging: true
    }, {
        xtype: 'dynamicorgcombselector',
        name: 'vo.queryDto.destOrgCode',
        fieldLabel: '到达部门',
        allowblank: true,
        listWidth: 300,// 设置下拉框宽度
        isPaging: true
    }, {
        border: 1,
        xtype: 'container',
        columnWidth: 1,
        colspan: 3,
        defaultType: 'button',
        layout: 'column',
        items: [{
            text: '重置',
            columnWidth: .1,
            handler: function () {
                this.up('form').getForm().reset();
            }
        }, {
            xtype: 'container',
            border: false,
            html: '&nbsp;',
            columnWidth: .8
        }, {
            text: '查询',
            columnWidth: .1,
            cls: 'yellow_button',
            handler: function () {
                var form = this.up('form').getForm();
                var me = this;
                if (form.isValid()) {
                    closing.mvrDci.query(form, me);
                } else {
                    Ext.Msg.alert("温馨提醒", "请检查输入条件是否合法");
                    return false;
                }
            }
        }]
    }]
})

// 查询Grid
Ext.define('Foss.closing.MvrDciQueryGrid', {
    extend: 'Ext.grid.Panel',
    title: '报表明细',
    columnWidth: 1,
    stripeRows: true,
    columnLines: true,
    collapsible: false,
    bodyCls: 'autoHeight',
    frame: true,
    cls: 'autoHeight',
    store: null,
    autoScroll: true,
    height: 650,
    emptyText: '查询结果为空',
    viewConfig: {
        enableTextSelection: true, // 设置行可以选择，进而可以复制
        getRowClass: function (record, rowIndex, rowParams, store) {
            if (record.data.period == '汇总') { // 汇总的样式
                return 'closing-totalBgColor';
            } else {
                return '';
            }
        }
    },
    pagingToolbar: null,
    getPagingToolbar: function () {
        var me = this;
        if (Ext.isEmpty(me.pagingToolbar)) {
            me.pagingToolbar = Ext.create('Deppon.StandardPaging', {
                store: me.store,
                pageSize: 100,
                maximumSize: 500,
                plugins: 'pagesizeplugin',
                items: [me.getExportButton()]
            });
        }
        return me.pagingToolbar;
    },
    exportButton: null,
    getExportButton: function () {
        var me = this;
        if (Ext.isEmpty(me.exportButton)) {
            me.exportButton = Ext.create('Ext.Button', {
                text: '导出',
                height: 20,
                handler: closing.mvrDci.exportDci,
                disabled: !closing.mvrDci.isPermission('/stl-web/closing/exportMvrDci.action'),
                hidden: !closing.mvrDci.isPermission('/stl-web/closing/exportMvrDci.action')
            });
        }
        return me.exportButton;
    },

    columns:[{
        text:'数据统计维度',
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
        },
        {
            text: '事后折扣【02】-特殊业务调整',
            columns: [
                {
                    text: '到达部门申请', columns: [
                    {dataIndex: 'dciDestApplyWoIncomeNus', text: '02理赔冲收入调整（往来，非统一）', width:220},
                    {dataIndex: 'dciDestApplyWoIncomeUs', text: '02理赔冲收入调整（往来，统一）', width:220}
                ]
                }
            ]
        },
        {
            text: '事后折扣【01】-特殊业务调整',
            columns: [
                {
                    text: '理赔-到达部门申请', columns: [
                    {dataIndex: 'ldciDestIncomeoNus', text: '01理赔冲收入调整（往来，非统一）', width:220},
                    {dataIndex: 'ldciDestIncomeoUs', text: '01理赔冲收入调整（往来，统一）', width:220}
                ]
                }
            ]
        }
    ],

    constructor: function (config) {
        var me = this, cfg = Ext.apply({}, config);

        me.store = closing.mvrStore;

        me.bbar = me.getPagingToolbar();
        me.getPagingToolbar().store = me.store;
        me.callParent([cfg]);
    }
})
;

// 显示
Ext.onReady(function () {
    Ext.QuickTips.init();

    if (Ext.getCmp('T_closing-mvrDci_content')) {
        return;
    }

    // 查询FORM
    var queryForm = Ext.create('Foss.closing.MvrDciQueryForm');

    //显示grid
    var queryGrid = Ext.create('Foss.closing.MvrDciQueryGrid');

    // 显示到JSP页面
    Ext.create('Ext.panel.Panel', {
        id: 'T_closing-mvrDci_content',
        cls: "panelContentNToolbar",
        bodyCls: 'panelContentNToolbar-body',
        layout: 'auto',
        items: [queryForm, queryGrid],
        //获得查询FORM
        getQueryForm: function () {
            return queryForm;
        },
        //获得查询结果GRID
        getQueryGrid: function () {
            return queryGrid;
        },
        renderTo: 'T_closing-mvrDci-body'
    });
});