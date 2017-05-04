Ext.define('Foss.discountAdd.GridModel', {
    extend : 'Ext.data.Model',
    fields : [ {
        name : 'id'
    },{
        name : 'productCode'

    }, {
        name:'dunningOrgCode'
    },{
        name:'dunningOrgName'
    },{
        name : 'waybillNo'
    },{
        name:'receivableNo'
    }, {
        name : 'customerCode'
    },{
        name : 'customerName'
    }, {
        name : 'amount',
        type:'double'
    }, {
        name : 'transportFee',
        type:'double'
    }, {
        name : 'codFee',
        type:'double'
    }, {
        name : 'insuranceFee',
        type:'double'
    }]
});

Ext.define('Foss.discountAdd.GridStore',{
    extend:'Ext.data.Store',
    model:'Foss.discountAdd.GridModel',
    pageSize:50,
    proxy:{
        type:'ajax',
        url:pay.realPath('qeuryReicevableBill.action'),
        actionMethods:'POST',
        reader:{
            type:'json',
            root:'vo.receivableList',
            totalProperty:'totalCount'
        }
    },
    listeners:{
        'beforeLoad':function(store, operation, eOpts){
            var form = Ext.getCmp('T_pay-discountAdd_content').getQueryForm().getForm();
            var startDate = form.findField('periodBeginDate').getValue();
            var endDate = form.findField('periodEndDate').getValue();
            var customerCode = form.findField('customerCode').getValue();
            var searchParams = {
                'vo.dto.startDate':startDate,
                'vo.dto.endDate':endDate,
                'vo.dto.customerCode':customerCode
            };
            //设置查询条件
            Ext.apply(operation,{
                params :searchParams
            });
        }
    }
});

Ext.define('Foss.discountAdd.Grid',{
    extend:'Ext.grid.Panel',
    title:pay.discountAdd.i18n('foss.stl.pay.discountAdd.receivableInfo'),
    bodyCls: 'autoHeight',
    cls: 'autoHeight',
    emptyText:pay.discountAdd.i18n('foss.stl.pay.common.noResult'),
    frame:true,
    detailWin:null,
    store:Ext.create('Foss.discountAdd.GridStore'),
    //selModel:Ext.create('Ext.selection.CheckboxModel'),
    height:500,
    viewConfig:{
        enableTextSelection : true//设置行可以选择，进而可以复制
    },
    defaults:{
        align:'center',
        sortable:false
    },
    columns: [{
        header:pay.discountAdd.i18n('foss.stl.pay.discountAdd.dunningOrgCode'),
        dataIndex:'dunningOrgCode'

    },{
        header:pay.discountAdd.i18n('foss.stl.pay.discountAdd.dunningOrgName'),
        dataIndex:'dunningOrgName'
    },{
        header:pay.discountAdd.i18n('foss.stl.pay.discountAdd.productName'),
        dataIndex:'productCode',
        renderer : function(value) {
            return Foss.pkp.ProductData.rendererSubmitToDisplay(value);
        }

    },{
        header:pay.discountAdd.i18n('foss.stl.pay.discountAdd.waybillNo'),
        dataIndex:'waybillNo'
    },{
        header:pay.discountAdd.i18n('foss.stl.writeoff.woodenStatementAdd.customerName'),
        dataIndex:'customerName'
    },{
        header:pay.discountAdd.i18n('foss.stl.writeoff.woodenStatementAdd.customerCode'),
        dataIndex:'customerCode'
    },{
        header:pay.discountAdd.i18n('foss.stl.pay.discountAdd.totalAmount'),
        dataIndex:'amount'
    },{
        header:pay.discountAdd.i18n('foss.stl.pay.discountAdd.transportAmount'),
        dataIndex:'transportFee'
    },{
        header:pay.discountAdd.i18n('foss.stl.pay.discountAdd.codAmount'),
        dataIndex:'codFee'
    },{
        header:pay.discountAdd.i18n('foss.stl.pay.discountAdd.insuranceAmount'),
        dataIndex:'insuranceFee'
    }],
    //暂存查询参数
    QuerystartDate: null,
    QueryendDate:null,
    QuerycustomerCode:null,
    initComponent:function(){
        var me = this;
        me.dockedItems = [{
            xtype: 'toolbar',
            dock: 'bottom',
            layout:'column',
            defaults:{
                margin:'5 0 5 3',
                readOnly:true,
                labelWidth:130
            },
            items: [ {
                height: 5,
                columnWidth: 1
            },{
                xtype:'displayfield',
                allowBlank: true,
                id: 'Foss_discountGrid_codAmount',
                columnWidth: .3,
                fieldLabel: pay.discountAdd
                    .i18n('foss.stl.pay.discountAdd.codTotalAmount')

            },{
                xtype:'displayfield',
                allowBlank: true,
                id: 'Foss_discountGrid_transportAmount',
                columnWidth: .3,
                fieldLabel: pay.discountAdd
                    .i18n('foss.stl.pay.discountAdd.transportTotalAmount')

            },{
                xtype:'displayfield',
                allowBlank: true,
                id: 'Foss_discountGrid_insuranceAmount',
                columnWidth: .3,
                fieldLabel: pay.discountAdd
                    .i18n('foss.stl.pay.discountAdd.insuranceTotalAmount')

            },{
                xtype:'standardpaging',
                store:me.store,
                columnWidth:.9,
                pageSize:50,
                plugins: Ext.create('Deppon.ux.PageSizePlugin', {
                    //设置分页记录最大值，防止输入过大的数值
                    maximumSize: 1000,
                    sizeList: [['10',10],['25',25],['50',50],['100',100],['200', 200],['500', 500],['1000', 1000]]
                })
            },{
                xtype:'button',
                text:pay.discountAdd.i18n('foss.stl.pay.discountAdd.createDiscont'),
                columnWidth:.1,
                disabled:!pay.discountAdd.isPermission('/stl-web/pay/createDiscount.action'),
                hidden:!pay.discountAdd.isPermission('/stl-web/pay/createDiscount.action'),
                handler:function(){
                    me = this;
                    var grid = this.up('grid');
                    var startDate = grid.QuerystartDate;
                    var endDate = grid.QueryendDate;
                    var customerCode = grid.QuerycustomerCode;
                    var searchParams = {
                        'vo.dto.startDate':startDate,
                        'vo.dto.endDate':endDate,
                        'vo.dto.customerCode':customerCode
                    };
                    if(startDate == null){
                        Ext.Msg.alert(pay.discountAdd.i18n('foss.stl.pay.common.alert'), pay.discountAdd.i18n('foss.stl.pay.discountAdd.startDateIsNull'));
                        return false;
                    }else if(endDate == null){
                        Ext.Msg.alert(pay.discountAdd.i18n('foss.stl.pay.common.alert'), pay.discountAdd.i18n('foss.stl.pay.discountAdd.endDateIsNull'));
                        return false;
                    }else if(customerCode == null){
                        Ext.Msg.alert(pay.discountAdd.i18n('foss.stl.pay.common.alert'), pay.discountAdd.i18n('foss.stl.pay.discountAdd.customerisNull'));
                        return false;
                    }
                    me.disable(false);
                    // 30秒后自动解除灰掉效果
                    setTimeout(function() {
                        me.enable(true);
                    }, 30000);
                    Ext.Ajax.request({
                        url : pay.realPath('createDiscount.action'),
                        params:searchParams,
                        actionMethods : 'post',
                        success : function(response) {
                            var result = Ext.decode(response.responseText);
                            Ext.Msg.alert(pay.discountAdd.i18n('foss.stl.pay.common.alert'), '生成的折扣单号：'+result.vo.dto.discountNo);
                        },
                        exception : function(response) {
                            me.enable(true);
                            var result = Ext.decode(response.responseText);
                            Ext.Msg.alert(pay.discountAdd.i18n('foss.stl.pay.common.alert'), result.message);
                            return false;
                        }
                    });
                }
            }, {
                xtype:'button',
                text:pay.discountAdd.i18n('foss.stl.pay.discountAdd.createDiscontFD'),
                //columnWidth:.1,
                disabled:!pay.discountAdd.isPermission('/stl-web/pay/createDiscountFD.action'),
                hidden:!pay.discountAdd.isPermission('/stl-web/pay/createDiscountFD.action'),
                width: 120,
                handler:function(){
                        me = this;
                        var grid = this.up('grid');
                        var startDate = grid.QuerystartDate;
                        var endDate = grid.QueryendDate;
                        var customerCode = grid.QuerycustomerCode;
                        var searchParams = {
                                'vo.dto.startDate':startDate,
                        'vo.dto.endDate':endDate,
                        'vo.dto.customerCode':customerCode
                    };
                    if(startDate == null){
                        Ext.Msg.alert(pay.discountAdd.i18n('foss.stl.pay.common.alert'), pay.discountAdd.i18n('foss.stl.pay.discountAdd.startDateIsNull'));
                        return false;
                    }else if(endDate == null){
                        Ext.Msg.alert(pay.discountAdd.i18n('foss.stl.pay.common.alert'), pay.discountAdd.i18n('foss.stl.pay.discountAdd.endDateIsNull'));
                        return false;
                    }else if(customerCode == null){
                        Ext.Msg.alert(pay.discountAdd.i18n('foss.stl.pay.common.alert'), pay.discountAdd.i18n('foss.stl.pay.discountAdd.customerisNull'));
                        return false;
                    }
                    me.disable(false);
                    // 30秒后自动解除灰掉效果
                    setTimeout(function() {
                        me.enable(true);
                    }, 30000);
                    Ext.Ajax.request({
                            url : pay.realPath('createDiscountFD.action'),
                            params:searchParams,
                            actionMethods : 'post',
                            success : function(response) {
                                var result = Ext.decode(response.responseText);
                                Ext.Msg.alert(pay.discountAdd.i18n('foss.stl.pay.common.alert'), '生成的折扣单号：'+result.vo.dto.discountNo);
                            },
                            exception : function(response) {
                                me.enable(true);
                                var result = Ext.decode(response.responseText);
                                Ext.Msg.alert(pay.discountAdd.i18n('foss.stl.pay.common.alert'), result.message);
                                return false;
                            }
                        });
                }
            }]
        }];
        me.callParent();
    }
});

Ext.define('Foss.discountAdd.queryForm',{
    extend:'Ext.form.Panel',
    frame:true,
    bodyCls: 'autoHeight',
    height:150,
    title:pay.discountAdd.i18n('foss.stl.pay.discountAdd.queryFrom'),
    layout:'column',
    defaults:{
        margin:'10 10 0 10',
        labelWidth:80
    },
    items:[{    xtype:'datefield',
        fieldLabel:pay.discountAdd.i18n('foss.stl.pay.discountAdd.beginTime'),
        allowBlank:false,
        name:'periodBeginDate',
        columnWidth: .3,
        format:'Y-m-d',
        value:Ext.Date.add(Ext.Date.getFirstDateOfMonth(new Date()),Ext.Date.MONTH,-1)
    },{
        xtype:'datefield',
        fieldLabel:pay.discountAdd.i18n('foss.stl.pay.discountAdd.endTime'),
        labelWidth:80,
        name:'periodEndDate',
        format:'Y-m-d',
        columnWidth: .3,
        value:Ext.Date.getLastDateOfMonth(Ext.Date.add(new Date(),Ext.Date.MONTH,-1))
    },{
        xtype:'commoncustomerselector',
        listWidth:300,
        name:'customerCode',
        active:'Y',
        fieldLabel:'<span style="color:red;">*</span>'+pay.discountAdd.i18n('foss.stl.pay.discountAdd.customerInfo'),
        columnWidth:.3
    },{
        xtype:'container',
        columnWidth:1
    },{
        border: 1,
        xtype:'container',
        columnWidth:1,
        defaultType:'button',
        layout:'column',
        items:[{
            text:pay.discountAdd.i18n('foss.stl.pay.common.reset'),
            columnWidth:.08,
            handler:function(){
                this.up('form').getForm().reset();
            }
        },{
            xtype: 'container',
            border : false,
            columnWidth:.74,
            html: '&nbsp;'
        },{
            text:pay.discountAdd.i18n('foss.stl.pay.common.query'),
            cls:'yellow_button',
            columnWidth:.08,
            handler:function(){

                var grid = Ext.getCmp('T_pay-discountAdd_content').getGrid();
                var form=this.up('form');
                var startDate = form.getForm().findField('periodBeginDate').getValue();
                var endDate = form.getForm().findField('periodEndDate').getValue();
                var customerCode = form.getForm().findField('customerCode').getValue();
                if(startDate == null){
                    Ext.Msg.alert(pay.discountAdd.i18n('foss.stl.pay.common.alert'), pay.discountAdd.i18n('foss.stl.pay.discountAdd.startDateIsNull'));
                    return false;
                }else if(endDate == null){
                    Ext.Msg.alert(pay.discountAdd.i18n('foss.stl.pay.common.alert'), pay.discountAdd.i18n('foss.stl.pay.discountAdd.endDateIsNull'));
                    return false;
                }else if(customerCode == null){
                    Ext.Msg.alert(pay.discountAdd.i18n('foss.stl.pay.common.alert'), pay.discountAdd.i18n('foss.stl.pay.discountAdd.customerisNull'));
                    return false;
                }
                if(startDate.getMilliseconds()>endDate.getMilliseconds()){
                    Ext.Msg.alert("提示","开始日期不能大于结束日期！");
                    return false;
                }
                var me = this;
                if(!form.getForm().isValid()){
                    Ext.Msg.alert("提示", '请输入合法条件');
                    return false;
                }
                // 设置该按钮灰掉
                me.disable(false);
                // 30秒后自动解除灰掉效果
                setTimeout(function () {
                    me.enable(true);
                }, 30000);
                grid.store.loadPage(1,{
                    callback: function(records, operation, success) {
                        var rawData = grid.store.proxy.reader.rawData;
                        if (!success && !rawData.isException) {
                            Ext.Msg.alert("提示", rawData.message);
                            me.enable(true);
                            return false;
                        }
                        if (success) {
                            var result = Ext
                                .decode(operation.response.responseText);
                            Ext.getCmp('Foss_discountGrid_codAmount')
                                .setValue(result.vo.codAmount);
                            Ext.getCmp('Foss_discountGrid_insuranceAmount')
                                .setValue(result.vo.insuranceAmount);
                            Ext.getCmp('Foss_discountGrid_transportAmount')
                                .setValue(result.vo.transportAmount);
                        }
                        me.enable(true);
                    }
                });
                //设置参数
                grid.QuerystartDate = form.getForm().findField('periodBeginDate').getValue();
                grid.QueryendDate = form.getForm().findField('periodEndDate').getValue();
                grid.QuerycustomerCode = form.getForm().findField('customerCode').getValue();
            }
        }]
    }]

});

Ext.onReady(function() {
    Ext.Ajax.timeout = 60000*30;
    Ext.QuickTips.init();
    //查询表单
    var queryForm = Ext.create('Foss.discountAdd.queryForm');
    //查询结果列表
    var grid = Ext.create('Foss.discountAdd.Grid');

    //创建panel
    Ext.create('Ext.panel.Panel',{
        id: 'T_pay-discountAdd_content',
        cls: "panelContentNToolbar",
        bodyCls: 'panelContentNToolbar-body',
        layout: 'auto',
        getGrid:function(){
            return grid;
        },
        getQueryForm:function(){
            return queryForm;
        },
        items: [queryForm,grid],
        renderTo: 'T_pay-discountAdd-body'
    });
});