/**
 * @param {} date--比较日期   day--比较日期之前或之后多少天的日期 day>0表示比较日期之后，day<0表示比较日期之前
 * @return {} 返回目标日期
 */
order.bigCustomersPlayTag.getBillTimeFrom = function(date, day) {
    var d, s, t, t2;
    var MinMilli = 1000 * 60;
    var HrMilli = MinMilli * 60;
    var DyMilli = HrMilli * 24;
    t = Date.parse(date);
    t2 =  new Date(t+day*DyMilli);
    t2.setHours(0);
    t2.setMinutes(0);
    t2.setSeconds(0);
    t2.setMilliseconds(0);
    return t2;
};

order.bigCustomersPlayTag.getBillTimeTo = function(date, day) {
    var d, s, t, t2;
    var MinMilli = 1000 * 60;
    var HrMilli = MinMilli * 60;
    var DyMilli = HrMilli * 24;
    t = Date.parse(date);
    t2 =  new Date(t+day*DyMilli);
    t2.setHours(23);
    t2.setMinutes(59);
    t2.setSeconds(59);
    t2.setMilliseconds(0);
    return t2;
};

//查询显示列表MODEL
Ext.define('Foss.order.bigCustomersPlayTag.QueryResultTackingWayBillModel',{
    extend: 'Ext.data.Model',
    fields: [
    	{ name: 'waybillno',type:'string' },//运单号
        { name: 'erplogisticid',type:'string' },//erp单号
        { name: 'customerlablenum',type:'string' },//标签条码号
        { name: 'arrivestorenum',type:'string' },//收货门店编号
        { name: 'receiveraddress',type:'string' },//收货地址
        { name: 'receivername',type:'string' }, //收货人
        { name: 'paytype',type:'string',
            convert:function(value){
                if(value=='CD'){
                    return order.bigCustomersPlayTag.i18n('pkp.order.bigCustomersPlayTag.common.CD');	//银行卡
                }else if(value=='NT'){
                    return order.bigCustomersPlayTag.i18n('pkp.order.bigCustomersPlayTag.common.NT');//支票
                }else if(value=='DT'){
                    return order.bigCustomersPlayTag.i18n('pkp.order.bigCustomersPlayTag.common.DT');//临时欠款
                }else if(value=='FC'){
                    return order.bigCustomersPlayTag.i18n('pkp.order.bigCustomersPlayTag.common.FC');//到付
                }else if(value=='CH'){
                    return order.bigCustomersPlayTag.i18n('pkp.order.bigCustomersPlayTag.common.CH');//现金
                }else if(value=='TT'){
                    return order.bigCustomersPlayTag.i18n('pkp.order.bigCustomersPlayTag.common.TT');//电汇
                }else if(value=='OL'){
                    return order.bigCustomersPlayTag.i18n('pkp.order.bigCustomersPlayTag.common.OL');//网上支付
                }else if(value=='CT'){
                    return order.bigCustomersPlayTag.i18n('pkp.order.bigCustomersPlayTag.common.CT');//月结
                }else{
                    return order.bigCustomersPlayTag.i18n('pkp.order.bigCustomersPlayTag.common.null');//null
                }
            }
        }, //付款方式
        { name: 'transporttype',type:'string',
            convert:function(value){
                if(value=='FLF'){
                    return order.bigCustomersPlayTag.i18n('pkp.order.bigCustomersPlayTag.common.flf');	//精准卡航
                }else if(value=='AF'){
                    return order.bigCustomersPlayTag.i18n('pkp.order.bigCustomersPlayTag.common.af');//精准空运
                }else if(value=='FSF'){
                    return order.bigCustomersPlayTag.i18n('pkp.order.bigCustomersPlayTag.common.fsf');//精准城运
                }else if(value=='LRF'){
                    return order.bigCustomersPlayTag.i18n('pkp.order.bigCustomersPlayTag.common.lrf');//精准汽运(长途)
                }else if(value=='SRF'){
                    return order.bigCustomersPlayTag.i18n('pkp.order.bigCustomersPlayTag.common.srf');//精准汽运(短途)
                }else{
                    return order.bigCustomersPlayTag.i18n('pkp.order.bigCustomersPlayTag.common.null');//null
                }
            }
        },  //运输性质
        { name: 'deliverytype',type:'string',
            convert:function(value){
                if(value=='DELIVER'){
                    return order.bigCustomersPlayTag.i18n('pkp.order.bigCustomersPlayTag.common.DELIVER');	//免费送货
                }else if(value=='DELIVER_INGA'){
                    return order.bigCustomersPlayTag.i18n('pkp.order.bigCustomersPlayTag.common.DELIVER_INGA');//送货进仓
                }else if(value=='DELIVER_NOUP'){
                    return order.bigCustomersPlayTag.i18n('pkp.order.bigCustomersPlayTag.common.DELIVER_NOUP');//送货(不含上楼)
                }else if(value=='DELIVER_UP'){
                    return order.bigCustomersPlayTag.i18n('pkp.order.bigCustomersPlayTag.common.DELIVER_UP');//送货上楼
                }else if(value=='INNER_PICKUP'){
                    return order.bigCustomersPlayTag.i18n('pkp.order.bigCustomersPlayTag.common.INNER_PICKUP');//内部带货自提
                }else if(value=='LARGE_DELIVER_UP'){
                    return order.bigCustomersPlayTag.i18n('pkp.order.bigCustomersPlayTag.common.LARGE_DELIVER_UP');//大件上楼
                }else if(value=='SELF_PICKUP'){
                    return order.bigCustomersPlayTag.i18n('pkp.order.bigCustomersPlayTag.common.SELF_PICKUP');//自提
                }else{
                    return order.bigCustomersPlayTag.i18n('pkp.order.bigCustomersPlayTag.common.null');//null
                }
            }
        },  //提货方式
        { name: 'totalnumber',type:'int' },//件数
        { name: 'weight',type:'double' },//重量
        { name: 'volume',type:'double' }//体积
    ]

});

//定义到达联数据store
Ext.define('Foss.order.bigCustomersPlayTag.QueryResultTackingWayBillStore', {
    extend: 'Ext.data.Store',
    model: 'Foss.order.bigCustomersPlayTag.QueryResultTackingWayBillModel',
    pageSize: 100,
    proxy: {
        //代理的类型为内存代理
        type: 'ajax',
        //提交方式
        timeout:30000,
        actionMethods:'POST',
        url:order.realPath('queryBigCustome.action'),
        //定义一个读取器
        reader: {
            //以JSON的方式读取
            type: 'json',
            //定义读取JSON数据的根对象f
            root: 'vo.bigCustomeDtoList',
            //返回总数
            totalProperty : 'totalCount'
        }
    },//事件监听
    listeners: {
        //查询事件
        beforeload : function(s, operation, eOpts) {
            //执行查询，首先load查询条件，为全局变量，在查询条件的FORM创建时生成
            var queryParams = Ext.getCmp('T_order-bigCustomersPlayTagIndex_content').getQueryForm().getValues();
            Ext.apply(operation, {
                params:{
                    'vo.billTimeFrom':queryParams.billTimeFrom,// 开单时间起/收货日期起
                    'vo.billTimeTo':queryParams.billTimeTo//开单时间止/收货日期止
                }
            });
        }
    }
    // autoLoad: true
});



//查询条件
Ext.define('Foss.order.bigCustomersPlayTag.TrackingWayBillQueryForm',{
    extend: 'Ext.form.Panel',
    title: order.bigCustomersPlayTag.i18n('pkp.order.bigCustomersPlayTag.query.condition'),	 // 查询条件
    defaultType : 'textfield',
    collapsible: true,
    layout: 'column',
    cls:'autoHeight',
    bodyCls:'autoHeight',
    frame:true,
    defaults: {
        margin:'5 10 5 10',
        anchor: '90%',
        labelWidth:110
    },
    items: [{
        xtype: 'rangeDateField',
        fieldLabel: order.bigCustomersPlayTag.i18n('pkp.order.bigCustomersPlayTag.receive.time'),	 // 起止日期
        dateType: 'datefield',
        fromName: 'billTimeFrom',
        toName: 'billTimeTo',
        dateType : 'datetimefield_date97',
        fieldId : 'Foss_order_billTime_Id',
        editable:false,
        disallowBlank: true,
        fromValue: Ext.Date.format(order.bigCustomersPlayTag.getBillTimeFrom(new Date(),0),'Y-m-d H:i:s'),
        toValue: Ext.Date.format(order.bigCustomersPlayTag.getBillTimeTo(new Date(),0),'Y-m-d H:i:s'),
        columnWidth: .66
    },{
        border: 1,
        xtype:'container',
        columnWidth:1,
        defaultType:'button',
        layout:'column',
        items:[{
            text:order.bigCustomersPlayTag.i18n('pkp.order.bigCustomersPlayTag.reset'),	 // 重置
            columnWidth:.08,
            handler:function(){
                var myForm = this.up('form').getForm();
                myForm.reset();
                myForm.findField('billTimeFrom').setValue(Ext.Date.format(order.bigCustomersPlayTag.getBillTimeFrom(new Date(),0),'Y-m-d H:i:s'));
                myForm.findField('billTimeTo').setValue(Ext.Date.format(order.bigCustomersPlayTag.getBillTimeTo(new Date(),0),'Y-m-d H:i:s'));
            }
        },{
            xtype: 'container',
            border : false,
            columnWidth:.15,
            html: '&nbsp;'
        },{
            text:order.bigCustomersPlayTag.i18n('pkp.order.bigCustomersPlayTag.query'),	 // 查询
            disabled:!order.bigCustomersPlayTag.isPermission('bigCustomersPlayTagindex/bigCustomersPlayTagindexquerybutton'),
            hidden:!order.bigCustomersPlayTag.isPermission('bigCustomersPlayTagindex/bigCustomersPlayTagindexquerybutton'),
            columnWidth:.08,
            cls:'yellow_button',
            handler:function(){
                var myForm = this.up('form').getForm();
                var billTimeFrom = myForm.getValues().billTimeFrom, billTimeTo = myForm.getValues().billTimeTo;
                var result = Ext.Date.parse(billTimeTo,'Y-m-d H:i:s') - Ext.Date.parse(billTimeFrom,'Y-m-d H:i:s');
                if(result / (24 * 60 * 60 * 1000) >1){
                    Ext.ux.Toast.msg(order.bigCustomersPlayTag.i18n('pkp.order.bigCustomersPlayTag.tip'),
                        order.bigCustomersPlayTag.i18n('pkp.order.bigCustomersPlayTag.goods.time.must.within.one.days'), 'error', 3000); // '起止日期相隔不能超过1天！'
                    return;
                }
                if(myForm.isValid()){
                    var grid = Ext.getCmp('T_order-bigCustomersPlayTagIndex_content').getResultGridPanel();
                    grid.getPagingToolbar().moveFirst();
                }
            }
        },{
            xtype: 'container',
            border : false,
            columnWidth:.15,
            html: '&nbsp;'
        },{
            text:order.bigCustomersPlayTag.i18n('pkp.order.bigCustomersPlayTag.closeticket'),	 // 合票
            disabled:!order.bigCustomersPlayTag.isPermission('bigCustomersPlayTagindex/bigCustomersPlayTagindexquerybutton'),
            hidden:!order.bigCustomersPlayTag.isPermission('bigCustomersPlayTagindex/bigCustomersPlayTagindexquerybutton'),
            columnWidth:.08,
            cls:'yellow_button',
            handler:function(){
               //获得查询条件的值
                var bigCustomersPlayTagsForm = Ext.getCmp('T_order-bigCustomersPlayTagIndex_content').getQueryForm();
                if (bigCustomersPlayTagsForm != null) {
                    var queryParams = bigCustomersPlayTagsForm.getValues();
                    if(!Ext.fly('ticketAttachFileForm')){
                        var frm = document.createElement('form');
                        frm.id = 'ticketAttachFileForm';
                        frm.style.display = 'none';
                        document.body.appendChild(frm);
                    }
                    var billTimeFrom = queryParams.billTimeFrom, billTimeTo = queryParams.billTimeTo;
                    var result = Ext.Date.parse(billTimeTo,'Y-m-d H:i:s') - Ext.Date.parse(billTimeFrom,'Y-m-d H:i:s');
                    if(result / (24 * 60 * 60 * 1000) >1){
                        Ext.ux.Toast.msg(order.bigCustomersPlayTag.i18n('pkp.order.bigCustomersPlayTag.tip'),
                            order.bigCustomersPlayTag.i18n('pkp.order.bigCustomersPlayTag.goods.time.must.within.one.days'),
                            'error', 3000); // '起止日期相隔不能超过1天！'
                        return;
                    }
                    //获取查询出来的异常信息
                    var bigCustomersPlayTagStore = Ext.getCmp('T_order-bigCustomersPlayTagIndex_content').
                        getResultGridPanel().getStore();
                    //若异常信息不为空
                    if(bigCustomersPlayTagStore.getCount()!=0){
                        var params = {
							'vo' : {
								'billTimeFrom':queryParams.billTimeFrom,// 开单时间起/收货日期起
                    			'billTimeTo':queryParams.billTimeTo//开单时间止/收货日期止
							}
						};
                    	Ext.Ajax.request({
							url : order.realPath('combineBigCustome.action'),
							async : false,
							timeout: 600000,
							jsonData : params,
							success : function(response) {
								var json = Ext.decode(response.responseText);
								if (json.success) {
									Ext.MessageBox.alert(order.bigCustomersPlayTag.i18n('pkp.order.bigCustomersPlayTag.tip'), json.message);
								}
							},
							exception : function(response) {
								var result = Ext.decode(response.responseText);
								Ext.ux.Toast.msg(order.bigCustomersPlayTag.i18n('pkp.order.bigCustomersPlayTag.tip'), result.message, 'error',3000);
							}
							});
                    	}else{
                        	//或者提示不能合票
                        	Ext.ux.Toast.msg(order.bigCustomersPlayTag.i18n('pkp.order.bigCustomersPlayTag.tip'),
                            	order.bigCustomersPlayTag.i18n('pkp.order.bigCustomersPlayTag.ticketAttachFileForm.notExport'), 'error', 3000);
                    	}
                }
            }
        },{
            xtype: 'container',
            border : false,
            columnWidth:.15,
            html: '&nbsp;'
        },{
            text:order.bigCustomersPlayTag.i18n('pkp.order.bigCustomersPlayTag.notcloseticket'),	 // 反合票
            disabled:!order.bigCustomersPlayTag.isPermission('bigCustomersPlayTagindex/bigCustomersPlayTagindexquerybutton'),
            hidden:!order.bigCustomersPlayTag.isPermission('bigCustomersPlayTagindex/bigCustomersPlayTagindexquerybutton'),
            columnWidth:.08,
            cls:'yellow_button',
            handler:function(){
                //获得查询条件的值
                var bigCustomersPlayTagsForm = Ext.getCmp('T_order-bigCustomersPlayTagIndex_content').getQueryForm();
                //提示是否导出
    			Ext.Msg.confirm('温馨提示', '是否反合票?', function (btn, text) {
        			if ('yes' == btn) {
        			if (bigCustomersPlayTagsForm != null) {
                    var queryParams = bigCustomersPlayTagsForm.getValues();
                    if(!Ext.fly('CloseticketAttachFileForm')){
                        var frm = document.createElement('form');
                        frm.id = 'CloseticketAttachFileForm';
                        frm.style.display = 'none';
                        document.body.appendChild(frm);
                    }
                    var billTimeFrom = queryParams.billTimeFrom, billTimeTo = queryParams.billTimeTo;
                    var result = Ext.Date.parse(billTimeTo,'Y-m-d H:i:s') - Ext.Date.parse(billTimeFrom,'Y-m-d H:i:s');
                    if(result / (24 * 60 * 60 * 1000) >1){
                        Ext.ux.Toast.msg(order.bigCustomersPlayTag.i18n('pkp.order.bigCustomersPlayTag.tip'),
                            order.bigCustomersPlayTag.i18n('pkp.order.bigCustomersPlayTag.goods.time.must.within.one.days'),
                            'error', 3000); // '起止日期相隔不能超过1天！'
                        return;
                    }
                    //获取查询出来的异常信息
                    var bigCustomersPlayTagStore = Ext.getCmp('T_order-bigCustomersPlayTagIndex_content').
                        getResultGridPanel().getStore();
                    //若异常信息不为空
                    if(bigCustomersPlayTagStore.getCount()!=0){
                    	var params = {
							'vo' : {
								'billTimeFrom':queryParams.billTimeFrom,// 开单时间起/收货日期起
                    			'billTimeTo':queryParams.billTimeTo//开单时间止/收货日期止
							}
						};
                    	Ext.Ajax.request({
							url : order.realPath('disCombineBigCustome.action'),
							async : false,
							timeout: 600000,
							jsonData : params,
							success : function(response) {
								var json = Ext.decode(response.responseText);
								if (json.success) {
									Ext.MessageBox.alert(order.bigCustomersPlayTag.i18n('pkp.order.bigCustomersPlayTag.tip'), json.message);
								}
							},
							exception : function(response) {
								var result = Ext.decode(response.responseText);
								Ext.ux.Toast.msg(order.bigCustomersPlayTag.i18n('pkp.order.bigCustomersPlayTag.tip'), result.message, 'error',3000);
							}
							});
                    }else{
                        //或者提示不能反合票
                        Ext.ux.Toast.msg(order.bigCustomersPlayTag.i18n('pkp.order.bigCustomersPlayTag.tip'),
                            order.bigCustomersPlayTag.i18n('pkp.order.bigCustomersPlayTag.CloseticketAttachFileForm.notExport'), 'error', 3000);
                    }
                }
                }
    			});
            }
        },{
            xtype: 'container',
            border : false,
            columnWidth:.15,
            html: '&nbsp;'
        },{
            xtype : 'button',
            text :order.bigCustomersPlayTag.i18n('pkp.order.bigCustomersPlayTag.export'),//导出
            columnWidth:.08,
            cls:'yellow_button',
            handler : function(button) {
                //获得查询条件的值
                var bigCustomersPlayTagsForm = Ext.getCmp('T_order-bigCustomersPlayTagIndex_content').getQueryForm();
                if (bigCustomersPlayTagsForm != null) {
                    var queryParams = bigCustomersPlayTagsForm.getValues();
                    if(!Ext.fly('downloadAttachFileForm')){
                        var frm = document.createElement('form');
                        frm.id = 'downloadAttachFileForm';
                        frm.style.display = 'none';
                        document.body.appendChild(frm);
                    }
                    var billTimeFrom = queryParams.billTimeFrom, billTimeTo = queryParams.billTimeTo;
                    var result = Ext.Date.parse(billTimeTo,'Y-m-d H:i:s') - Ext.Date.parse(billTimeFrom,'Y-m-d H:i:s');
                    if(result / (24 * 60 * 60 * 1000) >1){
                        Ext.ux.Toast.msg(order.bigCustomersPlayTag.i18n('pkp.order.bigCustomersPlayTag.tip'),
                            order.bigCustomersPlayTag.i18n('pkp.order.bigCustomersPlayTag.goods.time.must.within.one.days'),
                            'error', 3000); // '起止日期相隔不能超过1天！'
                        return;
                    }
                    //获取查询出来的异常信息
                    //var bigCustomersPlayTagStore = Ext.getCmp('T_order-bigCustomersPlayTagIndex_content').
                        //getResultGridPanel().getStore();
                    //若异常信息不为空
                    //if(bigCustomersPlayTagStore.getCount()!=0){
                        Ext.Ajax.request({
                            url:order.realPath('exportBigCustome.action'),
                            form: Ext.fly('downloadAttachFileForm'),
                            method : 'POST',
                            isUpload: true,
                            params : {
                                'vo.billTimeFrom':queryParams.billTimeFrom,// 开单时间起/收货日期起
                                'vo.billTimeTo':queryParams.billTimeTo	//开单时间止/收货日期止
                            }
                        });
                    //}else{
                        //或者提示不能导出
                        //Ext.ux.Toast.msg(order.bigCustomersPlayTag.i18n('pkp.order.bigCustomersPlayTag.tip'),
                            //order.bigCustomersPlayTag.i18n('pkp.order.bigCustomersPlayTag.exceptionInfo.notExport'), 'error', 3000);
                    //}
                }
            }
        }]
    }]
});

// 结果显示区域
Ext.define('Foss.order.bigCustomersPlayTag.TrackingWayBillGridPanel',{
    extend: 'Ext.grid.Panel',
    columnLines: true,
    cls : 'autoHeight',
    bodyCls : 'autoHeight',
    // 增加滚动条
    emptyText: order.bigCustomersPlayTag.i18n('pkp.order.bigCustomersPlayTag.query.result.empty'),	 // 查询结果为空
    frame: true,
    title:order.bigCustomersPlayTag.i18n('pkp.order.bigCustomersPlayTag.result.display.area'),	 // 结果显示区域
    collapsible: true,
    autoScroll:true,
    columns:[
    	{text: order.bigCustomersPlayTag.i18n('pkp.order.bigCustomersPlayTag.waybillno'),
            dataIndex : 'waybillno', align: 'center', width : 90,xtype : 'ellipsiscolumn'},	 //运单号
        {text: order.bigCustomersPlayTag.i18n('pkp.order.bigCustomersPlayTag.erplogisticid'),
            dataIndex : 'erplogisticid', align: 'center', width : 90,xtype : 'ellipsiscolumn'},	 //ERP单号
        {text: order.bigCustomersPlayTag.i18n('pkp.order.bigCustomersPlayTag.customerlablenum'),
            dataIndex : 'customerlablenum', align: 'center', width : 90,xtype : 'ellipsiscolumn'},	 //标签条码号
        {text: order.bigCustomersPlayTag.i18n('pkp.order.bigCustomersPlayTag.arrivestorenum'),
            dataIndex : 'arrivestorenum', align: 'center', width : 90,xtype : 'ellipsiscolumn'},	 //收货门店编号
        {text: order.bigCustomersPlayTag.i18n('pkp.order.bigCustomersPlayTag.receiveraddress'),
            dataIndex : 'receiveraddress', align: 'center', width : 90,xtype : 'ellipsiscolumn'},	 //收货地址
        {text: order.bigCustomersPlayTag.i18n('pkp.order.bigCustomersPlayTag.receivername'),
            dataIndex : 'receivername', align: 'center', width : 90,xtype : 'ellipsiscolumn'},	 //收货人
        {text: order.bigCustomersPlayTag.i18n('pkp.order.bigCustomersPlayTag.paytype'),
            dataIndex : 'paytype', align: 'center', width : 90,xtype : 'ellipsiscolumn'},	 //付款方式
        {text: order.bigCustomersPlayTag.i18n('pkp.order.bigCustomersPlayTag.transporttype'),
            dataIndex : 'transporttype', align: 'center', width : 90,xtype : 'ellipsiscolumn'},	 //运输性质
        {text: order.bigCustomersPlayTag.i18n('pkp.order.bigCustomersPlayTag.deliverytype'),
            dataIndex : 'deliverytype', align: 'center', width : 90,xtype : 'ellipsiscolumn'},	 //提货方式
        {text: order.bigCustomersPlayTag.i18n('pkp.order.bigCustomersPlayTag.totalnumber'),
            dataIndex : 'totalnumber', align: 'center', width : 90,xtype : 'ellipsiscolumn'},	 //件数
        {text: order.bigCustomersPlayTag.i18n('pkp.order.bigCustomersPlayTag.weight'),
            dataIndex : 'weight', align: 'center', width : 90,xtype : 'ellipsiscolumn'},	 //重量
        {text: order.bigCustomersPlayTag.i18n('pkp.order.bigCustomersPlayTag.volume'),
            dataIndex : 'volume', align: 'center', width : 90,xtype : 'ellipsiscolumn'}	 //体积
    ],
    viewConfig: {
        //表格可复制
        enableTextSelection: true,
        stripeRows: false
    },
    plugins: [{
        ptype: 'rowexpander',
        rowsExpander: false,
        //有头部加号
        pluginId : 'Foss.order.bigCustomersPlayTag.TrackingWayBillGrid_Id',
        rowBodyElement: 'Foss.order.bigCustomersPlayTag.TrackingWayBillTaskForm'
    }],
    pagingToolbar : null,
    getPagingToolbar : function() {
        if (this.pagingToolbar == null) {
            this.pagingToolbar = Ext.create('Deppon.StandardPaging', {
                store : this.store,
                plugins: 'pagesizeplugin',
                plugins: {
                    ptype: 'pagesizeplugin',
                    // 超出输入最大限制是否提示，true则提示，默认不提示
                    alertOperation: true,
                    // 自定义分页comobo数据
                    sizeList: [['50', 50], ['100', 100], ['200', 200], ['500', 500], ['1000', 1000]],
                    // 入最大限制，默认为200
                    maximumSize: 1000
                },
                displayInfo: true
            });
        }
        return this.pagingToolbar;
    },
    constructor: function(config){
        var me = this,
            cfg = Ext.apply({}, config);
        me.store = Ext.create('Foss.order.bigCustomersPlayTag.QueryResultTackingWayBillStore');
        me.bbar = me.getPagingToolbar();
        me.callParent([cfg]);
    }
});
/********合并明细信息******/
/*Ext.define('Foss.order.bigCustomersPlayTag.TrackingFormDetailWindow',{
    extend: 'Ext.window.Window',
    layout: 'column',
    title: order.bigCustomersPlayTag.i18n('pkp.order.bigCustomersPlayTag.track.waybill.detail'),	 // 跟踪运单明细信息
    width: 900,
    closeAction:'hide',
    defaults: {
        margin:'5 10 5 10',
        anchor: '90%',
        labelWidth:90
    },
    trackingGridInfo : null,
    getTrackingGridInfo: function(){
        if(this.trackingGridInfo == null){
            this.trackingGridInfo = Ext.create('Foss.order.bigCustomersPlayTag.TrackingGridInfo',{
                columnWidth : 1
            });
        }
        return this.trackingGridInfo;
    },

    autoStowGridInfo : null,
    getAutoStowGridInfo: function(){
        if(this.autoStowGridInfo == null){
            this.autoStowGridInfo = Ext.create('Foss.order.bigCustomersPlayTag.AutoStowGridInfo',{
                columnWidth : 1
            });
        }
        return this.autoStowGridInfo;
    },
    associateGridInfo : null,
    getAssociateGridInfo: function(){
        if(this.associateGridInfo == null){
            this.associateGridInfo = Ext.create('Foss.order.bigCustomersPlayTag.AssociateGridInfo',{
                columnWidth : 1
            });
        }
        return this.associateGridInfo;
    },
    storeDetailGridPanel : null,
    getStoreDetailGridPanel: function(){
        if(this.storeDetailGridPanel == null){this.storeDetailGridPanel =
            Ext.create('Foss.order.bigCustomersPlayTag.StoreDetailGridPanel',{
            columnWidth : 1
        });}
        return this.storeDetailGridPanel;
    }


});*/

Ext.onReady(function(){
    Ext.QuickTips.init();
    var  queryForm = Ext.create('Foss.order.bigCustomersPlayTag.TrackingWayBillQueryForm');
    var  resultGridPanel = Ext.create('Foss.order.bigCustomersPlayTag.TrackingWayBillGridPanel');

    Ext.create('Ext.panel.Panel',{
        id: 'T_order-bigCustomersPlayTagIndex_content',
        cls: "panelContentNToolbar",
        bodyCls: 'panelContentNToolbar-body',
        layout: 'auto',
        getQueryForm: function(){
            return queryForm;
        },
        getResultGridPanel: function(){
            return resultGridPanel;
        },
        items: [queryForm,resultGridPanel],
        renderTo: 'T_order-bigCustomersPlayTagIndex-body'
    });
})