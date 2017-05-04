/**
 * 合伙人德启代付月报表
 */
//期间model
Ext.define('Foss.closing.mvrPtpDqpa.periodModel',{
    extend:'Ext.data.Model',
    fields:['name','code']
});

/**
 * 期间store
 */
Ext.define('Foss.closing.mvrPtpDqpa.periodStore',{
    extend:'Ext.data.Store',
    model:'Foss.closing.mvrPtpDqpa.periodModel',
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
        load:function(store,operation){
            if(operation.length === 0){
                Ext.Msg.alert('Tips','没有生成对应的期间');
                return;
            }
        }
    }
});


/**
 * 获取运输性质下拉框Store
 */
closing.mvrPtpDqpa.getComboProductTypeStore = function() {
    var productStore = Ext.create('Foss.pkp.ProductStore');

    return productStore;
};

//定义查询条件Form
Ext.define('Foss.closing.mvrPtpDqpa.form',{
    extend:'Ext.form.Panel',
    frame:true,
    title:'查询条件',
    bodyCls:'autoHeight',
    defaults:{
        margin:'10 5 10 5',
        labelWith:90,
        //colspan:1,
        xtype:'textfield',
        columnWidth:.33
    },
    layout:'column',
    items:[{
        xtype:'combo',
        name:'period',
        fieldLabel:'期间',
        queryModel:'remote',
        store:Ext.create('Foss.closing.mvrPtpDqpa.periodStore'),
        displayField:'name',
        valueField:'name',
        allowBlank:false,
        columnWidth : .33
    },{
		xtype : 'combo',
		name : 'productCodeList',
		fieldLabel : '运输性质',
		forceSelection : true,
		multiSelect:true,
		displayField : 'name',
		valueField : 'code',
		queryMode : 'local',
		triggerAction : 'all',
		editable : false,
		value : closing.mvrPtpDqpa.getComboProductTypeStore().first()
				.get('code'),
		store : closing.mvrPtpDqpa.getComboProductTypeStore(),
		columnWidth : .33
	},{
		xtype : 'commonsaledepartmentselector',
		fieldLabel : '合伙人信息',
		singlePeopleFlag : 'Y',
		name : 'customerCode',
		columnWidth : .33
	}, {
		xtype : 'dynamicorgcombselector',
		name : 'origOrgCode',
		fieldLabel : '始发部门',
		allowblank : true,
		listWidth : 300,// 设置下拉框宽度
		isPaging : true,
		columnWidth : .33
	},{
		xtype : 'dynamicorgcombselector',
		name : 'destOrgCode',
		fieldLabel : '到达部门',
		allowblank : true,
		listWidth : 300,// 设置下拉框宽度
		isPaging : true,
		columnWidth : .33
	},{
        xtype:'container',
        border:false,
        html:'&nbsp'
    },{
        xtype:'container',
        border:1,
        columnWidth:1,
        //colspan:3,
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
            columnWidth:.8
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
                    closing.mvrPtpDqpa.getQueryResult(me,form);
                }else{
                    Ext.Msg.alert("温馨提示","请检查参数是否合法！");
                    return;
                }
            }
        }]
    }]
});

//查询并显示数据方法
closing.mvrPtpDqpa.getQueryResult = function(me,form){
    var grid = Ext.getCmp('T_closing-mvrPtpDqpa_content').getQueryGrid();
    var queryBtn = me;

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
};

//显示数据Model
Ext.define('Foss.closing.mvrPtpDqpa.model',{
    extend:'Ext.data.Model',
    fields:[
        {name:'id' , type:'String'},
        {name:'period',type:'String'},
        {name:'productCode',type:'String'},
        {name:'customerCode',type:'String'},
        {name:'customerName',type:'String'},
        {name:'origOrgCode' ,type:'String'},
        {name:'origOrgName',type:'String'},
        {name:'destOrgCode',type:'String'},
        {name:'destOrgName' ,type:'String'},
        {name:'voucherBeginTime',type:'date',convert:stl.longToDateConvert},
        {name:'voucherEndTime',type:'date',convert:stl.longToDateConvert},
        {name:'recOrgCode',type:'String'},
        {name:'recOrgName',type:'String'},
        {name:'payOrgCode',type:'String'},
        {name:'payOrgName',type:'String'},
        {name:'depOrgCode',type:'String'},
        {name:'depOrgName',type:'String'},
        {name:'rdDrPayApply',type:'String'},
        {name:'arrivePayApply',type:'String'},
        {name:'deAdvanceApply',type:'String'},
        {name:'destAdvanceApply',type:'String'},
        {name:'adPayApply',type:'String'}
    ]
});

//定义store
Ext.define('Foss.closing.mvrPtpDqpa.store',{
    extend:'Ext.data.Store',
    model:'Foss.closing.mvrPtpDqpa.model',
    paqeSize:100,
    proxy:{
        type:'ajax',
        actionMethods:'post',
        url:closing.realPath('queryMvrPtpDqpa.action'),
        timeout:10*60*1000,
        reader:{
            type:'json',
            root:'vo.mvrPtpDqpaEntityList',
            totalProperty:'totalCount'
        }
    },
    constructor:function(config){
        var me = this;
        var cfg = Ext.apply({},config);
        me.listeners = {
            'beforeLoad':function(store,opration){
                var form = Ext.getCmp('T_closing-mvrPtpDqpa_content').getQueryForm().getForm();
                //期间
                closing.mvrPtpDqpa.period = form.findField('period').getValue();
                //客户编码
                closing.mvrPtpDqpa.customerCode = form.findField('customerCode').getValue();
                //出发部门
                closing.mvrPtpDqpa.origOrgCode = form.findField('origOrgCode').getValue();
                //到达部门
                closing.mvrPtpDqpa.destOrgCode = form.findField('destOrgCode').getValue();
                //运输性质
                closing.mvrPtpDqpa.productCodeList = stl.convertProductCode(form.findField('productCodeList').getValue());
                var params = {
                    'vo.queryDto.period':closing.mvrPtpDqpa.period,
                    'vo.queryDto.customerCode':closing.mvrPtpDqpa.customerCode,
                    'vo.queryDto.productCodeList':closing.mvrPtpDqpa.productCodeList,
                    'vo.queryDto.origOrgCode':closing.mvrPtpDqpa.origOrgCode,
                    'vo.queryDto.destOrgCode':closing.mvrPtpDqpa.destOrgCode
                };
                Ext.apply(opration,{params:params});

            }
        };
        me.callParent([cfg]);
    }
});

Ext.define('Foss.closing.mvrPtpDqpa.resultGrid',{
    extend:'Ext.grid.Panel',
    title:'结果',
    store:Ext.create('Foss.closing.mvrPtpDqpa.store',{
        storeId:'mvrPtpDqpastore'
    }),
    emptyText:'查询结果为空',
    cls:'autoHeight',
    autoScroll:true,
    frame:true,
    height:650,
    viewConfig : {
        enableTextSelection : true,
        // 设置行可以选择，进而可以复制
        getRowClass : function(record){
            if(record.data.period === '汇总'){ // 汇总的样式
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
        columns:[{
                xtype:'rownumberer',
                width:40
            },{
                text:'期间',
                dataIndex:'period'
            },{
                text:'运输性质',
                dataIndex:'productCode',
                renderer:function(value){
                    return Foss.pkp.ProductData.rendererSubmitToDisplay(value);
                }
            },{
                text:'合伙人名称',
                dataIndex:'customerName'
            },{
                text:'合伙人编码',
                dataIndex:'customerCode'
            },{
                text:'始发部门编码',
                dataIndex:'origOrgCode'
            },{
                text:'始发部门名称',
                dataIndex:'origOrgName'
            },/*{
                text:'始发部门标杆编码',
                dataIndex:'orgName'
            },*/{
                text:'到达部门编码',
                dataIndex:'destOrgCode'
            },{
                text:'到达部门名称',
                dataIndex:'destOrgName'
            },/*{
                text:'到达部门标杆编码',
                dataIndex:'orgName'
            },*/{
                text:'应付部门编码',
                dataIndex:'payOrgCode'
            },{
                text:'应付部门名称',
                dataIndex:'payOrgName'
            },{
                text:'应收部门编码',
                dataIndex:'recOrgCode'
            },{
                text:'应收部门名称',
                dataIndex:'recOrgName'
            }
        ]
    },{
        text:'合伙人预存款【H】',
        defaults:{
            style:"text-align:center"
        },
        menuDisabled:true,
        sortable:false,
        columns:[{
            text:'退预收付款申请',
            dataIndex:'rdDrPayApply'
        }]
    },{
        text:'发起付款申请【H】',
        defaults:{
            style:'text-align:center'
        },
        menuDisabled:true,
        sortable:false,
        columns:[
            {
                text:'到达提成付款申请',
                dataIndex:'arrivePayApply'
            },{
                text:'委托派费代付申请',
                dataIndex:'deAdvanceApply'
            },{
                text:'到付运费代付申请',
                dataIndex:'destAdvanceApply'
            }
        ]
    },{
        text:'支付合伙人款项',
        defaults:{
            style:"text-align:center"
        },
        menuDisabled:true,
        sortable:false,
        columns:[
            {
                text:'奖励付款申请',
                dataIndex:'adPayApply'
            }
        ]
    }],
    bbar:['->',{
        text:'导出',
        height:20,
        hidden : true,
        xtype:'button',
        handler:function(){
            var me = this;

            var grid = me.up('grid');
            var params= grid.submmitParams;
            //创建新Form
            if(!Ext.fly('mvrPtpDqpaExport')){
                var frm = document.createElement('form');
                frm.id = 'mvrPtpDqpaExport';
                frm.style.display='none';
                document.body.appendChild(frm);
            }
            Ext.Ajax.request({
                url:closing.realPath('exportToExcel.action'),
                form:'mvrPtpDqpaExport',
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
        store:'mvrPtpDqpastore',
        pageSize:25,
        maximumSize:500,
        plugins:'pagesizeplugin'
    }],
    submmitParams:{},
    setSubmitParams:function(submmitParams){
        this.submmitParams = submmitParams;
    }
});

Ext.onReady(function(){
    Ext.QuickTips.init();
    if(Ext.getCmp('T_closing-mvrPtpDqpa_content')){
        return;
    }

    //form创建
    var queryForm = Ext.create('Foss.closing.mvrPtpDqpa.form');
    //grid 创建
    var queryGrid = Ext.create('Foss.closing.mvrPtpDqpa.resultGrid');

    Ext.create('Ext.panel.Panel',{
        id:'T_closing-mvrPtpDqpa_content',
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
        renderTo:'T_closing-mvrPtpDqpa-body'
    });
});


