/**
 * 折扣始发报表
 */
/**
 * 声明账期model
 */
Ext.define('Foss.mvrDco.PeriodModel', {
    extend: 'Ext.data.Model',
    fields: ['name', 'code']
});

/**
 * 声明账期store
 */
Ext.define('Foss.mvrDco.PeriodStore', {
    extend: 'Ext.data.Store',
    model: 'Foss.mvrDco.PeriodModel',
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
closing.mvrDco.query = function (form, me) {
    var m = Ext.getCmp('T_closing-mvrDco_content');
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
                        if (!!result.vo.resultDto) {
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
closing.mvrDco.exportDco = function () {
    //获取主面板、查询GRID
    var mainPane = Ext.getCmp('T_closing-mvrDco_content');
    var queryGrid = mainPane.getQueryGrid();

    //提示是否导出
    Ext.Msg.confirm('温馨提示', '确定导出折扣调整始发月报表吗?', function (btn, text) {
        if ('yes' == btn) {

            var params = mainPane.getQueryForm().getValues();

            Ext.apply(params, {
                'vo.queryDto.productCodeList': stl.convertProductCode(mainPane.getQueryForm().getForm().findField('vo.queryDto.productCodeList').getValue())
            });

            //创建一个form
            if (!Ext.fly('exportMvrDcoForm')) {
                var frm = document.createElement('form');
                frm.id = 'exportMvrDcoForm';
                frm.style.display = 'none';
                document.body.appendChild(frm);
            }

            //导出Ajax请求
            Ext.Ajax.request({
                url: closing.realPath('exportMvrDco.action'),
                form: Ext.fly('exportMvrDcoForm'),
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
closing.mvrDco.getComboPeriodStore = function () {
    return Ext.create('Foss.common.PeriodStore');
}

/**
 * 获取业务类型下拉框Store
 */
closing.mvrDco.getComboProductTypeStore = function () {
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
Ext.define('Foss.closing.MvrDcoComboModel', {
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

// 折扣调整始发月报表数据模型
Ext.define('Foss.closing.MvrDcoModel', {
    extend: 'Ext.data.Model',
    fields: [
        {name: 'id', type: 'string'},
        {name: 'period', type: 'string'},
        {name: 'invoiceMark', type: 'string'},
        {name: 'productCode', type: 'string'},
        {name: 'customerCode', type: 'string'},
        {name: 'customerName', type: 'string'},
        {name: 'origOrgCode', type: 'string'},
        {name: 'origOrgName', type: 'string'},
        {name: 'destOrgCode', type: 'string'},
        {name: 'destOrgName', type: 'string'},
        {name: 'origUnifiedCode', type: 'string'},
        {name: 'destUnifiedCode', type: 'string'},
        {name: 'customerType', type: 'string'},
        {name: 'unifiedSettlementType', type: 'string'},
        {name: 'contractOrgCode', type: 'string'},
        {name: 'contractOrgName', type: 'string'},
        {name: 'dcOrigPredctPodTransport', type: 'decimal'},
        {name: 'dcOrigPredctPodPickup', type: 'decimal'},
        {name: 'dcOrigPredctPodDelivery', type: 'decimal'},
        {name: 'dcOrigPredctPodPackaging', type: 'decimal'},
        {name: 'dcOrigPredctPodCod', type: 'decimal'},
        {name: 'dcOrigPredctPodInsurance', type: 'decimal'},
        {name: 'dcOrigPredctPodOther', type: 'decimal'},
        {name: 'dcDestPredctPodTransport', type: 'decimal'},
        {name: 'dcDestPredctPodPickup', type: 'decimal'},
        {name: 'dcDestPredctPodDelivery', type: 'decimal'},
        {name: 'dcDestPredctPodPackaging', type: 'decimal'},
        {name: 'dcDestPredctPodCod', type: 'decimal'},
        {name: 'dcDestPredctPodInsurance', type: 'decimal'},
        {name: 'dcDestPredctPodOther', type: 'decimal'},
        {name: 'dcOrigDctDifPodTransport', type: 'decimal'},
        {name: 'dcOrigDctDifPodPickup', type: 'decimal'},
        {name: 'dcOrigDctDifPodDelivery', type: 'decimal'},
        {name: 'dcOrigDctDifPodPackaging', type: 'decimal'},
        {name: 'dcOrigDctDifPodCod', type: 'decimal'},
        {name: 'dcOrigDctDifPodInsurance', type: 'decimal'},
        {name: 'dcOrigDctDifPodOther', type: 'decimal'},
        {name: 'dcDestDctDifPodTransport', type: 'decimal'},
        {name: 'dcDestDctDifPodPickup', type: 'decimal'},
        {name: 'dcDestDctDifPodDelivery', type: 'decimal'},
        {name: 'dcDestDctDifPodPackaging', type: 'decimal'},
        {name: 'dcDestDctDifPodCod', type: 'decimal'},
        {name: 'dcDestDctDifPodInsurance', type: 'decimal'},
        {name: 'dcDestDctDifPodOther', type: 'decimal'},
        {name: 'dcOrigActdctPodTransport', type: 'decimal'},
        {name: 'dcOrigActdctPodPickup', type: 'decimal'},
        {name: 'dcOrigActdctPodDelivery', type: 'decimal'},
        {name: 'dcOrigActdctPodPackaging', type: 'decimal'},
        {name: 'dcOrigActdctPodCod', type: 'decimal'},
        {name: 'dcOrigActdctPodInsurance', type: 'decimal'},
        {name: 'dcOrigActdctPodOther', type: 'decimal'},
        {name: 'dcDestActdctPodTransport', type: 'decimal'},
        {name: 'dcDestActdctPodPickup', type: 'decimal'},
        {name: 'dcDestActdctPodDelivery', type: 'decimal'},
        {name: 'dcDestActdctPodPackaging', type: 'decimal'},
        {name: 'dcDestActdctPodCod', type: 'decimal'},
        {name: 'dcDestActdctPodInsurance', type: 'decimal'},
        {name: 'dcDestActdctPodOther', type: 'decimal'},
        {name: 'dcOrigActdctNpodTransport', type: 'decimal'},
        {name: 'dcOrigActdctNpodPickup', type: 'decimal'},
        {name: 'dcOrigActdctNpodDelivery', type: 'decimal'},
        {name: 'dcOrigActdctNpodPackaging', type: 'decimal'},
        {name: 'dcOrigActdctNpodCod', type: 'decimal'},
        {name: 'dcOrigActdctNpodInsurance', type: 'decimal'},
        {name: 'dcOrigActdctNpodOther', type: 'decimal'},
        {name: 'dcDestActdctNpodTransport', type: 'decimal'},
        {name: 'dcDestActdctNpodPickup', type: 'decimal'},
        {name: 'dcDestActdctNpodDelivery', type: 'decimal'},
        {name: 'dcDestActdctNpodPackaging', type: 'decimal'},
        {name: 'dcDestActdctNpodCod', type: 'decimal'},
        {name: 'dcDestActdctNpodInsurance', type: 'decimal'},
        {name: 'dcDestActdctNpodOther', type: 'decimal'},
        {name: 'dcOrigPredctRpodTransport', type: 'decimal'},
        {name: 'dcOrigPredctRpodPickup', type: 'decimal'},
        {name: 'dcOrigPredctRpodDelivery', type: 'decimal'},
        {name: 'dcOrigPredctRpodPackaging', type: 'decimal'},
        {name: 'dcOrigPredctRpodCod', type: 'decimal'},
        {name: 'dcOrigPredctRpodInsurance', type: 'decimal'},
        {name: 'dcOrigPredctRpodOther', type: 'decimal'},
        {name: 'dcDestPredctRpodTransport', type: 'decimal'},
        {name: 'dcDestPredctRpodPickup', type: 'decimal'},
        {name: 'dcDestPredctRpodDelivery', type: 'decimal'},
        {name: 'dcDestPredctRpodPackaging', type: 'decimal'},
        {name: 'dcDestPredctRpodCod', type: 'decimal'},
        {name: 'dcDestPredctRpodInsurance', type: 'decimal'},
        {name: 'dcDestPredctRpodOther', type: 'decimal'},
        {name: 'dcOrigDctDifRpodTransport', type: 'decimal'},
        {name: 'dcOrigDctDifRpodPickup', type: 'decimal'},
        {name: 'dcOrigDctDifRpodDelivery', type: 'decimal'},
        {name: 'dcOrigDctDifRpodPackaging', type: 'decimal'},
        {name: 'dcOrigDctDifRpodCod', type: 'decimal'},
        {name: 'dcOrigDctDifRpodInsurance', type: 'decimal'},
        {name: 'dcOrigDctDifRpodOther', type: 'decimal'},
        {name: 'dcDestDctDifRpodTransport', type: 'decimal'},
        {name: 'dcDestDctDifRpodPickup', type: 'decimal'},
        {name: 'dcDestDctDifRpodDelivery', type: 'decimal'},
        {name: 'dcDestDctDifRpodPackaging', type: 'decimal'},
        {name: 'dcDestDctDifRpodCod', type: 'decimal'},
        {name: 'dcDestDctDifRpodInsurance', type: 'decimal'},
        {name: 'dcDestDctDifRpodOther', type: 'decimal'},
        {name: 'dcOrigActdctRpodTransport', type: 'decimal'},
        {name: 'dcOrigActdctRpodPickup', type: 'decimal'},
        {name: 'dcOrigActdctRpodDelivery', type: 'decimal'},
        {name: 'dcOrigActdctRpodPackaging', type: 'decimal'},
        {name: 'dcOrigActdctRpodCod', type: 'decimal'},
        {name: 'dcOrigActdctRpodInsurance', type: 'decimal'},
        {name: 'dcOrigActdctRpodOther', type: 'decimal'},
        {name: 'dcDestActdctRpodTransport', type: 'decimal'},
        {name: 'dcDestActdctRpodPickup', type: 'decimal'},
        {name: 'dcDestActdctRpodDelivery', type: 'decimal'},
        {name: 'dcDestActdctRpodPackaging', type: 'decimal'},
        {name: 'dcDestActdctRpodCod', type: 'decimal'},
        {name: 'dcDestActdctRpodInsurance', type: 'decimal'},
        {name: 'dcDestActdctRpodOther', type: 'decimal'},
        {name: 'dcOrigActdctRnpodTransport', type: 'decimal'},
        {name: 'dcOrigActdctRnpodPickup', type: 'decimal'},
        {name: 'dcOrigActdctRnpodDelivery', type: 'decimal'},
        {name: 'dcOrigActdctRnpodPackaging', type: 'decimal'},
        {name: 'dcOrigActdctRnpodCod', type: 'decimal'},
        {name: 'dcOrigActdctRnpodInsurance', type: 'decimal'},
        {name: 'dcOrigActdctRnpodOther', type: 'decimal'},
        {name: 'dcDestActdctRnpodTransport', type: 'decimal'},
        {name: 'dcDestActdctRnpodPickup', type: 'decimal'},
        {name: 'dcDestActdctRnpodDelivery', type: 'decimal'},
        {name: 'dcDestActdctRnpodPackaging', type: 'decimal'},
        {name: 'dcDestActdctRnpodCod', type: 'decimal'},
        {name: 'dcDestActdctRnpodInsurance', type: 'decimal'},
        {name: 'dcDestActdctRnpodOther', type: 'decimal'},
        {name: 'dcOrigIncomet', type: 'decimal'},
        {name: 'dcOrigCostt', type: 'decimal'},
        {name: 'dcDestApplyT', type: 'decimal'},
        {name: 'dcExOrigRcvtPod', type: 'decimal'},
        {name: 'dcExDestRcvtPod', type: 'decimal'},
        {name: 'ldcExtOrNwo', type: 'decimal'},
        {name: 'ldcExtOrWo', type: 'decimal'},
        {name: 'ldcOroNwoActPodTransport', type: 'decimal'},
        {name: 'ldcOroNwoActPodPickup', type: 'decimal'},
        {name: 'ldcOroNwoActPodDelivery', type: 'decimal'},
        {name: 'ldcOroNwoActPodPackaging', type: 'decimal'},
        {name: 'ldcOroNwoActPodInsurance', type: 'decimal'},
        {name: 'ldcOroNwoActPodCod', type: 'decimal'},
        {name: 'ldcOroNwoActPodOther', type: 'decimal'},
        {name: 'ldcOroWoActPodTransport', type: 'decimal'},
        {name: 'ldcOroWoActPodPickup', type: 'decimal'},
        {name: 'ldcOroWoActPodDelivery', type: 'decimal'},
        {name: 'ldcOroWoActPodPackaging', type: 'decimal'},
        {name: 'ldcOroWoActPodInsurance', type: 'decimal'},
        {name: 'ldcOroWoActPodCod', type: 'decimal'},
        {name: 'ldcOroWoActPodOther', type: 'decimal'},
        {name: 'ldcOroNwoActRpodTransport', type: 'decimal'},
        {name: 'ldcOroNwoActRpodPickup', type: 'decimal'},
        {name: 'ldcOroNwoActRpodDelivery', type: 'decimal'},
        {name: 'ldcOroNwoActRpodPackaging', type: 'decimal'},
        {name: 'ldcOroNwoActRpodInsurance', type: 'decimal'},
        {name: 'ldcOroNwoActRpodCod', type: 'decimal'},
        {name: 'ldcOroNwoActRpodOther', type: 'decimal'},
        {name: 'ldcOroWoActRpodTransport', type: 'decimal'},
        {name: 'ldcOroWoActRpodPickup', type: 'decimal'},
        {name: 'ldcOroWoActRpodDelivery', type: 'decimal'},
        {name: 'ldcOroWoActRpodPackaging', type: 'decimal'},
        {name: 'ldcOroWoActRpodInsurance', type: 'decimal'},
        {name: 'ldcOroWoActRpodCod', type: 'decimal'},
        {name: 'ldcOroWoActRpodOther', type: 'decimal'},
        {name: 'ldcOrigIncomeo', type: 'decimal'},
        {name: 'ldcOrigCosto', type: 'decimal'},
        {name: 'ldcDestIncomeo', type: 'decimal'},
        {name: 'ldcExoOrPod', type: 'decimal'},
        {name: 'ldcExoOrNwo', type: 'decimal'},
        {name: 'ldcExoOrWo', type: 'decimal'}
    ]
})

//Store
Ext.define('Foss.closing.MvrDcoStore', {
    extend: 'Ext.data.Store',
    model: 'Foss.closing.MvrDcoModel',
    pageSize: 100,
    proxy: {
        type: 'ajax',
        actionMethods: 'post',
        url: closing.realPath('queryMvrDco.action'),
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
                var queryForm = Ext.getCmp('T_closing-mvrDco_content').getQueryForm();
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

closing.mvrStore = Ext.create('Foss.closing.MvrDcoStore');

// 定义查询Form
Ext.define('Foss.closing.MvrDcoQueryForm', {
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
        store: Ext.create('Foss.mvrDco.PeriodStore'),
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
        value: closing.mvrDco.getComboProductTypeStore().first()
            .get('code'),
        store: closing.mvrDco.getComboProductTypeStore()
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
                    closing.mvrDco.query(form, me);
                } else {
                    Ext.Msg.alert("温馨提醒", "请检查输入条件是否合法");
                    return false;
                }
            }
        }]
    }]
})

// 查询Grid
Ext.define('Foss.closing.MvrDcoQueryGrid', {
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
                handler: closing.mvrDco.exportDco,
                disabled: !closing.mvrDco.isPermission('/stl-web/closing/exportMvrDco.action'),
                hidden: !closing.mvrDco.isPermission('/stl-web/closing/exportMvrDco.action')
            });
        }
        return me.exportButton;
    },

    columns: [
        {
            text: '数据统计维度',
            defaults: {
                style: "text-align:center"
            },
            columns: [{
                text: '序号',
                xtype: 'rownumberer',
                width: 40
            }, {
                text: 'ID',
                width: 10,
                dataIndex: 'id',
                hidden: true
            }, {
                text: '期间',
                width: 95,
                dataIndex: 'period'
            }, {
                text: '发票标记',
                width: 95,
                dataIndex: 'invoiceMark',
                renderer:function(value){
                    var displayField = FossDataDictionary. rendererSubmitToDisplay (value,'SETTLEMENT_INVOICE_MARK');
                    return displayField;
                }
            }, {
                text: '运输性质',
                width: 95,
                dataIndex: 'productCode',
                renderer: function (value) {
                    return Foss.pkp.ProductData.rendererSubmitToDisplay(value);

                }
            }, {
                text: '客户名称',
                width: 95,
                dataIndex: 'customerName'
            }, {
                text: '客户编码',
                width: 95,
                dataIndex: 'customerCode'
            }, {
                text: '客户类型',
                width: 95,
                dataIndex: 'customerType',
                hidden:true
            }, {
                text: '始发部门编码',
                width: 95,
                dataIndex: 'origOrgCode'
            }, {
                text: '始发部门名称',
                width: 95,
                dataIndex: 'origOrgName'
            }, {
                text: '到达部门编码',
                width: 95,
                dataIndex: 'destOrgCode'
            }, {
                text: '到达部门名称',
                width: 95,
                dataIndex: 'destOrgName'
            }, {
                text: '统一结算类型',
                width: 110,
                dataIndex: 'unifiedSettlementType',
                renderer: function (value, metaData, record, rowIndex, colIndex, store) {
                    if (rowIndex != store.data.length - 1) {
                        return value == 'ORIG' ? "始发统一结算" : value == 'DEST' ? "到达统一结算" : "非统一结算";
                    }
                }
            }, {
                text: '合同部门名称',
                width: 110,
                dataIndex: 'contractOrgCode'
            }, {
                text: '合同部门编码',
                width: 110,
                dataIndex: 'contractOrgName'
            }]
        },
        {
            text: '事后折扣【02】-收入调整（签收单）',
            columns: [
                {
                    text: '签收时始发应收未核销预先折调整', columns: [
                    {dataIndex: 'dcOrigPredctPodTransport', text: '公布价运费'},
                    {dataIndex: 'dcOrigPredctPodPickup', text: '接货费'},
                    {dataIndex: 'dcOrigPredctPodDelivery', text: '送货费'},
                    {dataIndex: 'dcOrigPredctPodPackaging', text: '包装费'},
                    {dataIndex: 'dcOrigPredctPodCod', text: '保价费'},
                    {dataIndex: 'dcOrigPredctPodInsurance', text: '代收货款手续费'},
                    {dataIndex: 'dcOrigPredctPodOther', text: '其他费用'}
                ]
                },
                {
                    text: '签收时到达应收未核销预先折调整', columns: [
                    {dataIndex: 'dcDestPredctPodTransport', text: ' 公布价运费'},
                    {dataIndex: 'dcDestPredctPodPickup', text: ' 接货费'},
                    {dataIndex: 'dcDestPredctPodDelivery', text: ' 送货费'},
                    {dataIndex: 'dcDestPredctPodPackaging', text: ' 包装费'},
                    {dataIndex: 'dcDestPredctPodCod', text: ' 保价费'},
                    {dataIndex: 'dcDestPredctPodInsurance', text: ' 代收货款手续费'},
                    {dataIndex: 'dcDestPredctPodOther', text: ' 其他费用'}
                ]
                },
                {
                    text: '签收时始发应收未核销预先折与实际折差额调整', columns: [
                    {dataIndex: 'dcOrigDctDifPodTransport', text: ' 公布价运费'},
                    {dataIndex: 'dcOrigDctDifPodPickup', text: ' 接货费'},
                    {dataIndex: 'dcOrigDctDifPodDelivery', text: ' 送货费'},
                    {dataIndex: 'dcOrigDctDifPodPackaging', text: ' 包装费'},
                    {dataIndex: 'dcOrigDctDifPodCod', text: ' 保价费'},
                    {dataIndex: 'dcOrigDctDifPodInsurance', text: ' 代收货款手续费'},
                    {dataIndex: 'dcOrigDctDifPodOther', text: ' 其他费用'}
                ]
                },
                {
                    text: '签收时到达应收未核销预先折与实际折差额调整', columns: [
                    {dataIndex: 'dcDestDctDifPodTransport', text: ' 公布价运费'},
                    {dataIndex: 'dcDestDctDifPodPickup', text: ' 接货费'},
                    {dataIndex: 'dcDestDctDifPodDelivery', text: ' 送货费'},
                    {dataIndex: 'dcDestDctDifPodPackaging', text: ' 包装费'},
                    {dataIndex: 'dcDestDctDifPodCod', text: ' 保价费'},
                    {dataIndex: 'dcDestDctDifPodInsurance', text: ' 代收货款手续费'},
                    {dataIndex: 'dcDestDctDifPodOther', text: ' 其他费用'}
                ]
                },
                {
                    text: '签收时始发应收未核销实际折调整', columns: [
                    {dataIndex: 'dcOrigActdctPodTransport', text: ' 公布价运费'},
                    {dataIndex: 'dcOrigActdctPodPickup', text: ' 接货费'},
                    {dataIndex: 'dcOrigActdctPodDelivery', text: ' 送货费'},
                    {dataIndex: 'dcOrigActdctPodPackaging', text: ' 包装费'},
                    {dataIndex: 'dcOrigActdctPodCod', text: ' 保价费'},
                    {dataIndex: 'dcOrigActdctPodInsurance', text: ' 代收货款手续费'},
                    {dataIndex: 'dcOrigActdctPodOther', text: ' 其他费用'}
                ]
                },
                {
                    text: '签收时到达应收未核销实际折调整', columns: [
                    {dataIndex: 'dcDestActdctPodTransport', text: ' 公布价运费'},
                    {dataIndex: 'dcDestActdctPodPickup', text: ' 接货费'},
                    {dataIndex: 'dcDestActdctPodDelivery', text: ' 送货费'},
                    {dataIndex: 'dcDestActdctPodPackaging', text: ' 包装费'},
                    {dataIndex: 'dcDestActdctPodCod', text: ' 保价费'},
                    {dataIndex: 'dcDestActdctPodInsurance', text: ' 代收货款手续费'},
                    {dataIndex: 'dcDestActdctPodOther', text: ' 其他费用'}
                ]
                },
                {
                    text: '签收时始发应收已核销实际折调整', columns: [
                    {dataIndex: 'dcOrigActdctNpodTransport', text: ' 公布价运费'},
                    {dataIndex: 'dcOrigActdctNpodPickup', text: ' 接货费'},
                    {dataIndex: 'dcOrigActdctNpodDelivery', text: ' 送货费'},
                    {dataIndex: 'dcOrigActdctNpodPackaging', text: ' 包装费'},
                    {dataIndex: 'dcOrigActdctNpodCod', text: ' 保价费'},
                    {dataIndex: 'dcOrigActdctNpodInsurance', text: ' 代收货款手续费'},
                    {dataIndex: 'dcOrigActdctNpodOther', text: ' 其他费用'}
                ]
                },
                {
                    text: '签收时到达应收已核销实际折调整', columns: [
                    {dataIndex: 'dcDestActdctNpodTransport', text: ' 公布价运费'},
                    {dataIndex: 'dcDestActdctNpodPickup', text: ' 接货费'},
                    {dataIndex: 'dcDestActdctNpodDelivery', text: ' 送货费'},
                    {dataIndex: 'dcDestActdctNpodPackaging', text: ' 包装费'},
                    {dataIndex: 'dcDestActdctNpodCod', text: ' 保价费'},
                    {dataIndex: 'dcDestActdctNpodInsurance', text: ' 代收货款手续费'},
                    {dataIndex: 'dcDestActdctNpodOther', text: ' 其他费用'}
                ]
                }
            ]
        }, {
            text: '事后折扣【02】-收入调整（反签收单）',
            columns: [
                {
                    text: '反签收时始发应收未核销预先折调整', columns: [
                    {dataIndex: 'dcOrigPredctRpodTransport', text: ' 公布价运费'},
                    {dataIndex: 'dcOrigPredctRpodPickup', text: ' 接货费'},
                    {dataIndex: 'dcOrigPredctRpodDelivery', text: ' 送货费'},
                    {dataIndex: 'dcOrigPredctRpodPackaging', text: ' 包装费'},
                    {dataIndex: 'dcOrigPredctRpodCod', text: ' 保价费'},
                    {dataIndex: 'dcOrigPredctRpodInsurance', text: ' 代收货款手续费'},
                    {dataIndex: 'dcOrigPredctRpodOther', text: ' 其他费用'}
                ]
                },
                {
                    text: '反签收时到达应收未核销预先折调整', columns: [
                    {dataIndex: 'dcDestPredctRpodTransport', text: ' 公布价运费'},
                    {dataIndex: 'dcDestPredctRpodPickup', text: ' 接货费'},
                    {dataIndex: 'dcDestPredctRpodDelivery', text: ' 送货费'},
                    {dataIndex: 'dcDestPredctRpodPackaging', text: ' 包装费'},
                    {dataIndex: 'dcDestPredctRpodCod', text: ' 保价费'},
                    {dataIndex: 'dcDestPredctRpodInsurance', text: ' 代收货款手续费'},
                    {dataIndex: 'dcDestPredctRpodOther', text: ' 其他费用'}
                ]
                },
                {
                    text: '反签收时始发应收未核销预先折与实际折差额调整', columns: [
                    {dataIndex: 'dcOrigDctDifRpodTransport', text: ' 公布价运费'},
                    {dataIndex: 'dcOrigDctDifRpodPickup', text: ' 接货费'},
                    {dataIndex: 'dcOrigDctDifRpodDelivery', text: ' 送货费'},
                    {dataIndex: 'dcOrigDctDifRpodPackaging', text: ' 包装费'},
                    {dataIndex: 'dcOrigDctDifRpodCod', text: ' 保价费'},
                    {dataIndex: 'dcOrigDctDifRpodInsurance', text: ' 代收货款手续费'},
                    {dataIndex: 'dcOrigDctDifRpodOther', text: ' 其他费用'}
                ]
                },
                {
                    text: '反签收时到达应收未核销预先折与实际折差额调整', columns: [
                    {dataIndex: 'dcDestDctDifRpodTransport', text: ' 公布价运费'},
                    {dataIndex: 'dcDestDctDifRpodPickup', text: ' 接货费'},
                    {dataIndex: 'dcDestDctDifRpodDelivery', text: ' 送货费'},
                    {dataIndex: 'dcDestDctDifRpodPackaging', text: ' 包装费'},
                    {dataIndex: 'dcDestDctDifRpodCod', text: ' 保价费'},
                    {dataIndex: 'dcDestDctDifRpodInsurance', text: ' 代收货款手续费'},
                    {dataIndex: 'dcDestDctDifRpodOther', text: ' 其他费用'}
                ]
                },
                {
                    text: '反签收时始发应收未核销实际折调整', columns: [
                    {dataIndex: 'dcOrigActdctRpodTransport', text: ' 公布价运费'},
                    {dataIndex: 'dcOrigActdctRpodPickup', text: ' 接货费'},
                    {dataIndex: 'dcOrigActdctRpodDelivery', text: ' 送货费'},
                    {dataIndex: 'dcOrigActdctRpodPackaging', text: ' 包装费'},
                    {dataIndex: 'dcOrigActdctRpodCod', text: ' 保价费'},
                    {dataIndex: 'dcOrigActdctRpodInsurance', text: ' 代收货款手续费'},
                    {dataIndex: 'dcOrigActdctRpodOther', text: ' 其他费用'}
                ]
                },
                {
                    text: '反签收时到达应收未核销实际折调整', columns: [
                    {dataIndex: 'dcDestActdctRpodTransport', text: ' 公布价运费'},
                    {dataIndex: 'dcDestActdctRpodPickup', text: ' 接货费'},
                    {dataIndex: 'dcDestActdctRpodDelivery', text: ' 送货费'},
                    {dataIndex: 'dcDestActdctRpodPackaging', text: ' 包装费'},
                    {dataIndex: 'dcDestActdctRpodCod', text: ' 保价费'},
                    {dataIndex: 'dcDestActdctRpodInsurance', text: ' 代收货款手续费'},
                    {dataIndex: 'dcDestActdctRpodOther', text: ' 其他费用'}
                ]
                },
                {
                    text: '反签收时始发应收已核销实际折调整', columns: [
                    {dataIndex: 'dcOrigActdctRnpodTransport', text: ' 公布价运费'},
                    {dataIndex: 'dcOrigActdctRnpodPickup', text: ' 接货费'},
                    {dataIndex: 'dcOrigActdctRnpodDelivery', text: ' 送货费'},
                    {dataIndex: 'dcOrigActdctRnpodPackaging', text: ' 包装费'},
                    {dataIndex: 'dcOrigActdctRnpodCod', text: ' 保价费'},
                    {dataIndex: 'dcOrigActdctRnpodInsurance', text: ' 代收货款手续费'},
                    {dataIndex: 'dcOrigActdctRnpodOther', text: ' 其他费用'}
                ]
                },
                {
                    text: '反签收时到达应收已核销实际折调整', columns: [
                    {dataIndex: 'dcDestActdctRnpodTransport', text: ' 公布价运费'},
                    {dataIndex: 'dcDestActdctRnpodPickup', text: ' 接货费'},
                    {dataIndex: 'dcDestActdctRnpodDelivery', text: ' 送货费'},
                    {dataIndex: 'dcDestActdctRnpodPackaging', text: ' 包装费'},
                    {dataIndex: 'dcDestActdctRnpodCod', text: ' 保价费'},
                    {dataIndex: 'dcDestActdctRnpodInsurance', text: ' 代收货款手续费'},
                    {dataIndex: 'dcDestActdctRnpodOther', text: ' 其他费用'}
                ]
                }
            ]
        }, {
            text: '事后折扣【02】-特殊业务调整',
            columns: [
                {
                    text: '理赔-出发部门申请', columns: [
                    {dataIndex: 'dcOrigIncomet', text: '02理赔冲收入调整'},
                    {dataIndex: 'dcOrigCostt', text: '02理赔入成本调整'}
                ]
                },
                {
                    text: '理赔-到达部门申请', columns: [
                    {dataIndex: 'dcDestApplyT', text: '02理赔冲收入调整'}
                ]
                },
                {
                    text: '异常冲收入', columns: [
                    {dataIndex: 'dcExOrigRcvtPod', text: '02应收始发运费已签收调整'},
                    {dataIndex: 'dcExDestRcvtPod', text: '02应收到付运费已签收调整'}
                ]
                },
                {
                    text: '弃货、违禁品、全票丢货', columns: [
                    {dataIndex: 'ldcExtOrNwo', text: '开单且为月结临时欠款网上支付未核销'},
                    {dataIndex: 'ldcExtOrWo', text: '开单且为月结临时欠款网上支付已核销'}
                ]
                }
            ]
        },{
            text: '事后折扣【01】-收入调整（签收单）',
            columns: [
                {
                    text: '签收时始发应收未核销实际折调整', columns: [
                    {dataIndex: 'ldcOroNwoActPodTransport', text: '公布价运费'},
                    {dataIndex: 'ldcOroNwoActPodPickup', text: '接货费'},
                    {dataIndex: 'ldcOroNwoActPodDelivery', text: '送货费'},
                    {dataIndex: 'ldcOroNwoActPodPackaging', text: '包装费'},
                    {dataIndex: 'ldcOroNwoActPodInsurance', text: '保价费'},
                    {dataIndex: 'ldcOroNwoActPodCod', text: '代收货款手续费'},
                    {dataIndex: 'ldcOroNwoActPodOther', text: '其他费用'}
                ]
                },
                {
                    text: '签收时始发应收已核销实际折调整', columns: [
                    {dataIndex: 'ldcOroWoActPodTransport', text: '公布价运费'},
                    {dataIndex: 'ldcOroWoActPodPickup', text: '接货费'},
                    {dataIndex: 'ldcOroWoActPodDelivery', text: '送货费'},
                    {dataIndex: 'ldcOroWoActPodPackaging', text: '包装费'},
                    {dataIndex: 'ldcOroWoActPodInsurance', text: '保价费'},
                    {dataIndex: 'ldcOroWoActPodCod', text: '代收货款手续费'},
                    {dataIndex: 'ldcOroWoActPodOther', text: '其他费用'}
                ]
                }
            ]
        }, {
            text: '事后折扣【01】-收入调整（反签收单）',
            columns: [
                {
                    text: '反签收时始发应收未核销实际折调整', columns: [
                    {dataIndex: 'ldcOroNwoActRpodTransport', text: '公布价运费'},
                    {dataIndex: 'ldcOroNwoActRpodPickup', text: '接货费'},
                    {dataIndex: 'ldcOroNwoActRpodDelivery', text: '送货费'},
                    {dataIndex: 'ldcOroNwoActRpodPackaging', text: '包装费'},
                    {dataIndex: 'ldcOroNwoActRpodInsurance', text: '保价费'},
                    {dataIndex: 'ldcOroNwoActRpodCod', text: '代收货款手续费'},
                    {dataIndex: 'ldcOroNwoActRpodOther', text: '其他费用'}
                ]
                },
                {
                    text: '反签收时始发应收已核销实际折调整', columns: [
                    {dataIndex: 'ldcOroWoActRpodTransport', text: '公布价运费'},
                    {dataIndex: 'ldcOroWoActRpodPickup', text: '接货费'},
                    {dataIndex: 'ldcOroWoActRpodDelivery', text: '送货费'},
                    {dataIndex: 'ldcOroWoActRpodPackaging', text: '包装费'},
                    {dataIndex: 'ldcOroWoActRpodInsurance', text: '保价费'},
                    {dataIndex: 'ldcOroWoActRpodCod', text: '代收货款手续费'},
                    {dataIndex: 'ldcOroWoActRpodOther', text: '其他费用'}
                ]
                }
            ]
        }, {
            text: '事后折扣【01】-特殊业务调整',
            columns: [
                {
                    text: '理赔-出发部门申请', columns: [
                    {dataIndex: 'ldcOrigIncomeo', text: '01理赔冲收入调整'},
                    {dataIndex: 'ldcOrigCosto', text: '01理赔入成本调整'}
                ]
                },
                {
                    text: '理赔-到达部门申请', columns: [
                    {dataIndex: 'ldcDestIncomeo', text: '01理赔冲收入调整'}
                ]
                },
                {
                    text: '异常冲收入', columns: [
                    {dataIndex: 'ldcExoOrPod', text: '01应收始发运费已签收调整'}
                ]
                },
                {
                    text: '弃货、违禁品、全票丢货', columns: [
                    {dataIndex: 'ldcExoOrNwo', text: '开单且为月结临时欠款网上支付未核销'},
                    {dataIndex: 'ldcExoOrWo', text: '开单且为月结临时欠款网上支付已核销'}
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

    if (Ext.getCmp('T_closing-mvrDco_content')) {
        return;
    }

    // 查询FORM
    var queryForm = Ext.create('Foss.closing.MvrDcoQueryForm');

    //显示grid
    var queryGrid = Ext.create('Foss.closing.MvrDcoQueryGrid');

    // 显示到JSP页面
    Ext.create('Ext.panel.Panel', {
        id: 'T_closing-mvrDco_content',
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
        renderTo: 'T_closing-mvrDco-body'
    });
});