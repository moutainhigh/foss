//加载字段时英文转汉字
//付款方式的转换
consumer.billRecivableMonthly.paymentTypePaymentType=function(val){
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
consumer.billRecivableMonthly.withholdStatus = function(val){
    switch (val) {
        case 'UWH':
            return '未扣款';
        case "WHS":
            return '扣款成功';
        case "WHF":
            return '扣款失败';
    }
};
//单据子类型的转换
consumer.billRecivableMonthly.billType = function(val){
	switch(val){
		case 'POR':
			return '始发提成';
		case 'PDFR':
			return '委托派费';
	}
};

//运输性质
consumer.billRecivableMonthly.productCode=function(val){
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
}

//定义按日期查询Form
Ext.define('Foss.consumer.queryBillRecivableMonthlyDateForm',{
    extend:'Ext.form.Panel',
    frame:false,
    columnWidth:1,
    height:120,
    defaults:{
        margin :'25 5 10 50',
        labelWidth :85,
        colspan : 1
    },
    defaultType:'textfield',
    layout:{
        type :'column',
        columns :2
    },
    items:[
        {
			xtype : 'combo',
			name : 'period',
			fieldLabel : '期间',
			labelWidth: 40,
	    	store:Ext.create('Foss.common.PeriodStore'),
		    queryMode: 'local', 	
			displayField:'name',
			valueField:'value',
			margin :'15 20 -10 50',
			allowBlank : false,//必选项
			columnWidth:.33
		},{
            xtype: 'dynamicorgcombselector',
            fieldLabel: '对接营业部',
            name:'receivableOrgCode',
            value: stl.currentDept.name,//设置默认值为登录部门
            margin :'15 0 -10 30',
            listWidth:300,//设置下拉框宽度
            columnWidth:.34,
            allowBlank : false,//必选项
            labelWidth : 85,
            isPaging: true //分页
        },
        {
            xtype: 'commonsaledepartmentselector',
            name:'contractOrgCode',
            fieldLabel: "<span style='color:red;'>*</span>"+consumer.billRecivableMonthly.i18n('foss.stl.consumer.recivableMonthly.contractOrgCode'),//合伙人部门
            isLeagueSaleDept:'Y',
            margin :'15 0 -10 30',
            listWidth:500,//设置下拉框宽度
            allowBlank : true,//非必选项
            isPaging:true, //分页
            columnWidth:.33
        },{
			xtype : 'combo',
			name : 'billType',
			fieldLabel : consumer.billRecivableMonthly.i18n('foss.stl.consumer.queryReceivableBill.billType'),//单据子类型
			store:Ext.create('Ext.data.Store', {
				fields: ['valueName', 'valueCode'],
				data : [
				        {"valueName":"全部", "valueCode":""},
				        {"valueName":"始发提成", "valueCode":"POR"},
				        {"valueName":"委托派费", "valueCode":"PDFR"}
				       ]
			}),
			queryMode: 'local', 	
			displayField : 'valueName',
	        valueField : 'valueCode',
			editable: false,
			value:'',	//默认值
			margin :'15 20 -10 50',
			allowBlank : false,//必选项
			columnWidth:.25,
			width: 20
		},{
            xtype:'container',
            border:false,
            html:'&nbsp;',
            columnWidth:.3
        },
        {
            border: 1,
            xtype:'container',
            columnWidth:.45,  
            //colspan:3,
            defaultType:'button',
            layout:'column',
            items:[{
                text:consumer.billRecivableMonthly.i18n('foss.stl.consumer.recivableMonthly.reset'), //重置
                columnWidth:.2,
                width:100,
                handler:function(){
                    this.up('form').getForm().reset();
                }
            },{
                xtype:'container',
                border:false,
                html:'&nbsp;',
                columnWidth:.05
            },{
               text:consumer.billRecivableMonthly.i18n('foss.stl.consumer.recivableMonthly.select'),//查询
               columnWidth:.2,
               cls:'yellow_button',
               width:100,
               handler:function(){
                   //校验
                   var me = this.up('form');
                   if(me.getForm().isValid()){
                	   //校验通过则调用方法
    				   if(consumer.billRecivableMonthly.queryTab.getActiveTab().itemId == 'billRecivableMonthlyDateForm'){
    						consumer.billRecivableMonthly.bbar.moveFirst();
    				   }
                   }
               }
            }]
        }
    ]
});

consumer.billRecivableMonthly.DateForm = Ext.create('Foss.consumer.queryBillRecivableMonthlyDateForm');//日期查询

//定义Tab
Ext.define('Foss.consumer.billRecivableMonthlyyTab',{
    extend:'Ext.tab.Panel',
    frame : false,
    bodyCls : 'autoHeight',
    cls : 'innerTabPanel',
    activeTab : 0,
    columnWidth: 1,
    //columnHeight: 'autoHeight',
    height:150,
    items: [{
        title:consumer.billRecivableMonthly.i18n('foss.stl.consumer.recivableMonthly.DateForm'),   //按日期查询
        itemId:'billRecivableMonthlyDateForm',
        tabConfig : {
            width : 80
        },
        layout : 'fit',
        items : [
            consumer.billRecivableMonthly.DateForm
        ]
    }]
});

//定义加载需要的字段model
Ext.define('Foss.consumer.recivableMonthlyModel',{
    extend:'Ext.data.Model',
    fields: [
        //出发合伙人部门
        {name: 'customerName'},
        //单号
        {name: 'waybillNo'},
        //开单日期
        {name: 'createTime',type:'date',//扣款日期
            convert:function(value){
            if(value!=null){
                var date = new Date(value);
                return date;
            }else{
                return null;
            }
        }},
        //发货客户编码
        {name: 'customerCode'},
        //付款方式
        {name: 'paymentType'},
        //开单金额
        {name: 'amount'},
        //运输性质
        {name: 'productCode'},
        //贷款状态
        {name: 'withholdStatus'},
        //单据子类型
        {name: 'billType'}
    ]
});

//定义要加载的数据Store
Ext.define('Foss.consumer.recivableMonthlyStore',{
    extend:'Ext.data.Store',
    model:'Foss.consumer.recivableMonthlyModel',
    //默认每页数据大小
    pageSize:20,
    //是否自动查询
    autoLoad: false,
    //定义一个代理对象
    proxy: {
        //代理的类型为内存代理
        type: 'ajax',
        //提交方式
        actionMethods:'POST',
        url:consumer.realPath('billReceivableMonthlySelect.action'),
        //定义一个读取器
        reader: {
            //以JSON的方式读取
            type: 'json',
            //定义读取JSON数据的根对象
            root: 'billReceivableVo.billReceivableList',
            //返回总数
            totalProperty : 'totalCount'
        }
    },
    listeners:{
        beforeload : function(store, operation, eOpts){//查询事件
            var tabItemId = consumer.billRecivableMonthly.queryTab.getActiveTab().itemId;
            var queryParams ;
            var params;         //用于传递参数
            //获取日期查询form的参数
            if(tabItemId=='billRecivableMonthlyDateForm'){
                queryParams = consumer.billRecivableMonthly.DateForm.getValues();
                params={
                	'billReceivableVo.dto.period':queryParams.period,//期间
                	'billReceivableVo.dto.receivableOrgCode':queryParams.receivableOrgCode,//对接营业部(应收部门)
                    'billReceivableVo.dto.contractOrgCode':queryParams.contractOrgCode,//合伙人部门
                    'billReceivableVo.dto.billType':queryParams.billType,//单据子类型
                    'billReceivableVo.dto.empCode':FossUserContext.getCurrentUserEmp().empCode//工号
                }
            }
            Ext.apply(operation, {
                params :params
            });
        },
        load : function(store, records, successful, eOpts){//表格数据加载事件
            var data = store.getProxy().getReader().rawData;
            //设置总金额的值
            Ext.getCmp('GridStore_totalMoney_ID').setValue(data.billReceivableVo.totalAmount==null?0+ '元':data.billReceivableVo.totalAmount + '元');
        }
    }
});

//定义要Grid
Ext.define('Foss.consumer.recivableMonthlyGrid',{
    extend:'Ext.grid.Panel',
    title:consumer.billRecivableMonthly.i18n('foss.stl.consumer.recivableMonthly.message'),
    stripeRows: true,
    columnLines: true,
    collapsible: false,
    sortableColumns: false,
    bodyCls: 'autoHeight',
    frame: true,
    cls: 'autoHeight',
    store : null,
    autoScroll : true,
    height: 450,
    emptyText:consumer.billRecivableMonthly.i18n('foss.stl.consumer.recivableMonthly.emptyText'), //没有数据
    pagingToolbar:null,
    viewConfig : {
        forceFit : true //让grid的列自动填满grid的整个宽度，不用一列一列的设定宽度。
    },
    columns: [
        //出发合伙人部门
        { header: consumer.billRecivableMonthly.i18n('foss.stl.consumer.recivableMonthly.contractOrgName'), dataIndex: 'customerName',flex: 1},
        //单号
        { header: consumer.billRecivableMonthly.i18n('foss.stl.consumer.recivableMonthly.waybillNo'), dataIndex: 'waybillNo',flex: 1},
        //开单日期
        { header: consumer.billRecivableMonthly.i18n('foss.stl.consumer.recivableMonthly.createTime'), dataIndex: 'createTime',xtype : 'datecolumn',format : 'Y-m-d H:i:s'},
        //发货客户编码
        { header: consumer.billRecivableMonthly.i18n('foss.stl.consumer.recivableMonthly.deliveryCustomerCode'), dataIndex: 'customerCode'},
        //付款方式
        { header: consumer.billRecivableMonthly.i18n('foss.stl.consumer.recivableMonthly.paymentType'), dataIndex: 'paymentType',renderer:consumer.billRecivableMonthly.paymentTypePaymentType},
        //开单金额
        { header: consumer.billRecivableMonthly.i18n('foss.stl.consumer.recivableMonthly.amount'), dataIndex: 'amount'},
        //运输性质
        { header:consumer.billRecivableMonthly.i18n('foss.stl.consumer.recivableMonthly.productCode'),dataIndex: 'productCode',renderer:consumer.billRecivableMonthly.productCode},
        //货款状态
        { header:consumer.billRecivableMonthly.i18n('foss.stl.consumer.recivableMonthly.withholdStatus'),dataIndex: 'withholdStatus',renderer:consumer.billRecivableMonthly.withholdStatus},
        //单据子类型
        { header:consumer.billRecivableMonthly.i18n('foss.stl.consumer.queryReceivableBill.billType'),dataIndex: 'billType',renderer:consumer.billRecivableMonthly.billType}
    ],
    height: 450,
    initComponent:function(config){
        var me = this;
        cfg = Ext.apply({}, config);
        me.store = Ext.create('Foss.consumer.recivableMonthlyStore');
        //自定义分页控件
        me.bbar = Ext.create('Deppon.StandardPaging',{
            store : me.store,
            pageSize : 20,
            maximumSize : 100,
            plugins : Ext.create('Deppon.ux.PageSizePlugin', {
                sizeList : [['20', 20], ['50', 50],  ['100', 100]]
            })
        });
        me.dockedItems = [{
        	xtype: 'toolbar',
        	layout:'column',
            dock: 'top',
        	defaults:{
                margin:'0 0 5 3',
                allowBlank:true
            },
            items: [{
            	xtype:'button',
            	width: 96,
                text:consumer.billRecivableMonthly.i18n('foss.stl.consumer.common.export'),//导出
            	handler: function(){
            		//1.定义变量
            		var	columns;//获取grid中的columns内容
                    var arrayColumns;//获取每列下面表格中的值
                    var arrayColumnNames;//获取每列的头
                    var tabItemId = consumer.billRecivableMonthly.queryTab.getActiveTab().itemId;
                    var tabParams;
                    //获取日期查询form的参数
                    if(tabItemId=='billRecivableMonthlyDateForm'){
                    	tabParams = consumer.billRecivableMonthly.DateForm.getValues();
                    }
                    //2.判断grid下面是否有数据
                    var recivableMonthlyList = consumer.billRecivableMonthly.queryGrid.store.data;
                    if(recivableMonthlyList.length==0){
                    	//提示：没有信息要导出！
                    	Ext.Msg.alert(consumer.billRecivableMonthly.i18n('foss.stl.consumer.common.alert'),consumer.billRecivableMonthly.i18n('foss.stl.consumer.common.noDataToExport'));
                    	return false;
                    }
                    //3.转化列头和列名
                    columns = consumer.billRecivableMonthly.queryGrid.columns;
                    arrayColumns = [];
                    arrayColumnNames = [];
                    //将前台对应的列头传入到后台去
                    for(var i=0;i<columns.length;i++){
                    	if(columns[i].isHidden()==false){
                    		var header = columns[i].text;
                        	var dataIndex = columns[i].dataIndex;
                        	arrayColumnNames.push(header);
                        	arrayColumns.push(dataIndex);
                    	}
                    }
                    
                    //拼接vo,注入到后台
                    searchParams = {
                    		'billReceivableVo.dto.period':tabParams.period,//期间
                        	'billReceivableVo.dto.receivableOrgCode':tabParams.receivableOrgCode,//对接营业部(应收部门)
                            'billReceivableVo.dto.contractOrgCode':tabParams.contractOrgCode,//合伙人部门
                            'billReceivableVo.dto.billType':tabParams.billType,//单据子类型
                            'billReceivableVo.dto.empCode':FossUserContext.getCurrentUserEmp().empCode,//工号
                            'billReceivableVo.dto.arrayColumns':arrayColumns,
                            'billReceivableVo.dto.arrayColumnNames':arrayColumnNames
                    };
                    
                    if(!Ext.fly('downloadAttachFileForm')){
                        var frm = document.createElement('form');
                        frm.id = 'downloadAttachFileForm';
                        frm.style.display = 'none';
                        document.body.appendChild(frm);
                    }
                    
                    Ext.Ajax.request({
                        url: consumer.realPath('billRecivableMonthlyToExcel.action'),
                        form: Ext.fly('downloadAttachFileForm'),
                        method : 'POST',
                        params : searchParams,
                        isUpload: true,
                        success : function(response,options){
                        	//导出成功 
                            Ext.Msg.alert(consumer.billRecivableMonthly.i18n('foss.stl.consumer.common.alert'),
                            		consumer.billRecivableMonthly.i18n('foss.stl.consumer.queryReceivableBill.exportSuccess'));
                        }
                    });  
            	}
            },{
                xtype:'component',
                autoEl:{
                tag:'div',
                html:'<span style="color:red;">'+'说明：导出的是满足查询条件下的所有数据！'+'</span>'
                }
           }]
        	
        },{
            xtype: 'toolbar',
            layout:'column',
            dock: 'bottom',
            defaults:{
                margin:'0 0 5 3',
                allowBlank:true
            },
            items: [{
                xtype:'displayfield',
                labelSeparator:'',
                width: 150,
                columnWidth:1,
                fieldStyle:'color:red;padding-top:5px;',
                name:'totalMoney',//总金额
                id:'GridStore_totalMoney_ID',
//                itemId:'GridStore_totalMoney_ID',
                fieldLabel:consumer.billRecivableMonthly.i18n('foss.stl.consumer.recivableMonthly.amountCount'),     //明细提成总金额
                value:''
            }]
        }];
        consumer.billRecivableMonthly.bbar = me.bbar;
        me.callParent([cfg]);
    }
});

//主函数
Ext.onReady(function(){
    Ext.Loader.setConfig({enabled:true});
    Ext.QuickTips.init();

    consumer.billRecivableMonthly.queryTab = Ext.create('Foss.consumer.billRecivableMonthlyyTab');//查询TAB
    consumer.billRecivableMonthly.queryGrid = Ext.create('Foss.consumer.recivableMonthlyGrid');//查询结果GRID

    Ext.create('Ext.panel.Panel',{
        id: 'T_consumer-billRecivableMonthly_content',
        cls: "panelContentNToolbar",
        bodyCls: 'panelContentNToolbar-body',
        layout: 'auto',
        //获得查询FORM
        getQueryTab : function() {
            return  consumer.billRecivableMonthly.queryTab;
        },
       //获得查询结果GRID
        getQueryGrid : function() {
            return consumer.billRecivableMonthly.queryGrid;
        },
        items: [consumer.billRecivableMonthly.queryTab,consumer.billRecivableMonthly.queryGrid],
        renderTo: 'T_consumer-billRecivableMonthly-body',
      //默认部门编码
        listeners:{
            'boxready':function(th){
                var roles = stl.currentUserDepts;
                var queryByDateTab = consumer.billRecivableMonthly.queryTab.items.items[0].items.items[0];
                var dept = queryByDateTab.getForm().findField("receivableOrgCode");
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
            }
        }
    });
});

