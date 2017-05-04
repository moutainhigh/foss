/**
 * 声明账期model
 */
Ext.define('Foss.mvrHi.PeriodModel',{
    extend:'Ext.data.Model',
    fields:['name','code']
});

/**
 * 声明账期store
 */
Ext.define('Foss.mvrHi.PeriodStore',{
    extend:'Ext.data.Store',
    model:'Foss.mvrHi.PeriodModel',
    proxy:{
        type:'ajax',
        url:closing.realPath('queryClosingPeriod.action'),
        actionMethods:'post',
        reader:{
            type:'json',
            root:'periodList'
        }
    },
    constructor:function(config){
        var me = this,
            cfg = Ext.apply({}, config);
        me.listeners = {
            'load': function(store, operation, eOpts){

                if(operation.length == 0){
                    Ext.Msg.alert("提示", "没有生成凭证报表数据，凭证期间为空");
                    return false;
                }
            }
        };
        me.callParent([ cfg ]);
    }
});

/**
 * 查询家装业务月报表
 */
closing.mvrHi.querymvrHi = function(f,me) {
    var form = f.getForm();
    var grid = Ext.getCmp('T_closing-mvrHi_content').getQueryGrid();

    if (form.isValid()) {
        //定义查询参数
        var paramsV= form.getValues();

        closing.mvrHi.setParamsValue(paramsV,form);
        // 设置参数
        grid.store.setSubmitParams(paramsV);
        //设置该按钮灰掉
        me.disable(false);
        //30秒后自动解除灰掉效果
        setTimeout(function() {
            me.enable(true);
        }, 30000);
        // 设置统计值
        grid.store.loadPage(1, {
            callback : function(records, operation, success) {
                var result = Ext.decode(operation.response.responseText);
                me.enable(true);
            }
        });
    } else {
        Ext.Msg.alert('温馨提示', '请检查输入条件是否合法');
    }
}

/**
 * 设置参数
 */
closing.mvrHi.setParamsValue = function(paramsV,form){
    var customerCode = form.findField('mvrHiDto.customerCode').getValue();
    Ext.apply(paramsV,{
        'mvrHiDto.customerCode':customerCode
    });
}

/**
 * 获取期间控件下拉框Store
 */
closing.mvrHi.getComboPeriodStore = function() {

    return Ext.create('Foss.common.PeriodStore');
}

/**
 * 获取业务类型下拉框Store
 */
closing.mvrHi.getComboProductCodeStore = function() {
    return Ext.create('Foss.pkp.ProductStore');
}

/** 数据模型 */

// 家装业务月报表数据模型
Ext.define('Foss.closing.mvrHiModel', {
    extend : 'Ext.data.Model',
    fields : [{
        name : 'id',
        type : 'String'
    },{
        name : 'period',
        type : 'String'
    },{
        name : 'businessType',
        type : 'String'
    },{
        name : 'customerCode',
        type : 'String'
    },{
        name : 'customerName',
        type : 'String'
    },{
        name : 'origOrgCode',
        type : 'String'
    },{
        name : 'origOrgName',
        type : 'String'
    },{
        name : 'voucherBeginTime',
        type:'Date',
        convert:function(value) {
            if (value != null) {
                var date = new Date(value);
                return date;
            } else {
                return null;
            }
        }
    },{
        name : 'voucherEndTime',
        type:'Date',
        convert:function(value) {
            if (value != null) {
                var date = new Date(value);
                return date;
            } else {
                return null;
            }
        }
    },{name:'hiCost',type : 'decimal' }
        ,{ name:'hiRentry',type : 'decimal' }
        ,{ name:'hiPwr',type : 'decimal' }
        ,{ name:'hiPr',type : 'decimal' }]
})

// Store
Ext.define('Foss.closing.mvrHiStore', {
    extend : 'Ext.data.Store',
    model : 'Foss.closing.mvrHiModel',
    pageSize : 100,
    proxy : {
        type : 'ajax',
        actionMethods : 'post',
        url : closing.realPath('mvrHiQuery.action'),
        timeout:10*60*1000,
        reader : {
            type : 'json',
            root : 'mvrHiDto.queryList',
            totalProperty : 'mvrHiDto.count'
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
        me.callParent([ cfg ]);
    }
});

// 定义查询Form
Ext.define('Foss.closing.mvrHiQueryForm', {
    extend : 'Ext.form.Panel',
    frame : true,
    title : '查询条件',
    items : [{
        xtype:'container',
        layout:'column',
        width:800,
        bodyCls : 'autoHeight',
        defaultType : 'textfield',
        defaults : {
            margin : '10 5 10 5',
            labelWidth : 85,
            colspan : 1
        },
        items:[{
            xtype : 'combo',
            name : 'mvrHiDto.period',
            fieldLabel : '期间',
            queryMode: 'remote',
            store:Ext.create('Foss.mvrHi.PeriodStore'),
            displayField:'name',
            valueField:'name',
            allowBlank : false,
            columnWidth : .33
        },{
            xtype : 'supplierSelector',
            name : 'mvrHiDto.customerCode',
            fieldLabel : '家装代理',
            allowblank : false,
            listWidth : 300,// 设置下拉框宽度
            isPaging : true,
            columnWidth : .33
        }, {
        	 xtype : 'dynamicorgcombselector',
             name : 'mvrHiDto.origOrgCode',
             fieldLabel : '始发部门',
             allowblank : true,
             listWidth : 300,// 设置下拉框宽度
             isPaging : true,
             columnWidth : .33
         }, {
            border : 1,
            xtype : 'container',
            columnWidth : 1,
            colspan : 3,
            defaultType : 'button',
            layout : 'column',
            items : [ {
                text : '重置',
                columnWidth : .1,
                handler : function(){
                    var form = this.up('form');
                    form.getForm().reset();
                }
            }, {
                xtype : 'container',
                border : false,
                html : '&nbsp;',
                columnWidth : .79
            }, {
                text : '查询',
                columnWidth : .1,
                cls : 'yellow_button',
                handler:function(){
                    var form = this.up('form');
                    var me = this;
                    closing.mvrHi.querymvrHi(form,me);
                }
            } ]
        } ]
    }]
})

// 家装月报表查询Grid
Ext.define('Foss.closing.mvrHiQueryGrid', {
    extend : 'Ext.grid.Panel',
    title : '报表明细',
    columnWidth : 1,
    stripeRows : true,
    columnLines : true,
    collapsible : false,
    bodyCls : 'autoHeight',
    frame : true,
    cls : 'autoHeight',
    store : null,
    autoScroll : true,
    height : 650,
    emptyText : '查询结果为空',
    viewConfig : {
        enableTextSelection : true,
        // 设置行可以选择，进而可以复制
        getRowClass:function(record, rowIndex, rowParams, store){
            count = store.getCount();
            if(count > 0 && rowIndex == count - 1){
                return 'closing-totalBgColor';
            }
        }
    },
    pagingToolbar : null,
    getPagingToolbar : function() {
        var me = this;
        if (Ext.isEmpty(me.pagingToolbar)) {
            me.pagingToolbar = Ext.create('Deppon.StandardPaging', {
                store : me.store,
                pageSize : 100,
                maximumSize : 500,
                plugins : 'pagesizeplugin'/*,
                items:[me.getExportButton()]*/
            });
        }
        return me.pagingToolbar;
    },
    /*exportButton:null,
    getExportButton:function(){
        var me = this;
        if(Ext.isEmpty(me.exportButton)){
            me.exportButton = Ext.create('Ext.Button',{
                text:'导出',
                height:20,
                handler:closing.mvrLdd.exportmvrHi,
                hidden:!closing.mvrLdd.isPermission('/stl-web/closing/mvrHiExport.action')
            });
        }
        return me.exportButton;
    },
*/    columns : [ {
        text : '数据统计维度',
        columns : [{
            text:'序号',
            xtype:'rownumberer',
            width:40,
            align:'center'
        }, {
            text : 'ID',
            width : 100,
            dataIndex : 'id',
            align:'center',
            hidden:true
        }, {
            text : '期间',
            width : 100,
            dataIndex : 'period',
            align:'center'
        }, {
            text : '业务类型',
            width : 100,
            dataIndex : 'businessType',
            align:'center',
            renderer:Foss.pkp.ProductData.rendererSubmitToDisplay
        }, {
            text : '供应商名称',
            width : 100,
            dataIndex : 'customerName',
            align:'center'
        }, {
            text : '供应商编码',
            width : 100,
            dataIndex : 'customerCode',
            align:'center'
        }, {
            text : '部门名称',
            width : 100,
            dataIndex : 'origOrgName',
            align:'center'
        }, {
            text : '部门编码',
            width : 100,
            dataIndex : 'origOrgCode',
            align:'center'
        }]
    },{
    	text : '家装业务',
    	columns : [{
	    		text : '家装入成本',
	            width : 85,
	            dataIndex : 'hiCost',
	            align:'center'
    		},{
    			text : '家装应收录入',
                width : 85,
                dataIndex : 'hiRentry',
                align:'center'
    		},{
    			text : '应收冲应付',
                width : 85,
                dataIndex : 'hiPwr',
                align:'center'
    		},{
    			text : '家装付款申请',
                width : 85,
                dataIndex : 'hiPr',
                align:'center'
    	}]
    }
    ],
    constructor : function(config) {
        var me = this, cfg = Ext.apply({}, config);

        me.store = Ext.create('Foss.closing.mvrHiStore');

        me.bbar = me.getPagingToolbar();
        me.getPagingToolbar().store = me.store;
        me.callParent([ cfg ]);
    }
});

// 显示
Ext.onReady(function() {
    Ext.QuickTips.init();

    if (Ext.getCmp('T_closing-mvrHi_content')) {
        return;
    }

    // 查询FORM
    var queryForm = Ext.create('Foss.closing.mvrHiQueryForm');

    // 显示grid
    var queryGrid = Ext.create('Foss.closing.mvrHiQueryGrid');

    // 显示到JSP页面
    Ext.create('Ext.panel.Panel', {
        id : 'T_closing-mvrHi_content',
        cls : "panelContentNToolbar",
        bodyCls : 'panelContentNToolbar-body',
        layout : 'auto',
        items : [ queryForm, queryGrid ],
        renderTo : 'T_closing-mvrHi-body',
        getQueryGrid : function() {
            return queryGrid;
        },
        getQueryForm:function(){
            return queryForm;
        }
    });
});
