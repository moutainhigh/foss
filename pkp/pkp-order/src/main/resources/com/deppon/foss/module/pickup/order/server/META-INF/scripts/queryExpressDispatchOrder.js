/**
 * 查询处理订单任务
 * @author 097972-foss-dengtingting
 */
//定义派车记录model
(function() {
	order.queryExpressDispatchOrder.productCode_none = 'N/A';
	Ext.Ajax.request({
		url : order.realPath('queryCountyCodes.action'),
		async : false,
		success : function(response) {
			var json = Ext.decode(response.responseText);
			if(Ext.isEmpty(json.countyCodes)){
				Ext.apply(order, {
					countyCodes : order.queryExpressDispatchOrder.productCode_none
				});
			} else {
				Ext.apply(order, {
					countyCodes : json.countyCodes
				});
			}
		}
	});
})();
Ext.define('Foss.order.queryExpressDispatchOrder.vehicleRecord',{
	extend: 'Ext.data.Model',
	fields: [
	        {name: 'id',type: 'string'}, //派车记录ID
	        {name: 'orderId',type: 'string'}, //调度订单ID
	        {name: 'orderNo',type: 'string'}, //订单号
	        {name: 'orderType',type: 'string'}, //订单类型
	        {name: 'pickupAddress',type: 'string'}, //接货地址
	        {name: 'orderVehicleOrgName',type: 'string'}, //约车部门名称
	        {name: 'orderSendStatus',type: 'string'}, // 订单任务发送状态
	        {name: 'orderStatus',type: 'string'}, //订单任务完成状态
	        {name: 'earliestPickupTime',type: 'date',convert : dateConvert}, //接货最早时间 ==用车时间
	        {name: 'latestPickupTime',type: 'date',convert : dateConvert}, // 接货最晚时间
	        {name: 'orderVehicleTime',type: 'date',convert : dateConvert}, // 约车时间
	        {name: 'acceptStatus',type: 'String'}, // 分配方式   2014-11-15 cdl DMANA-6209快递派车记录界面增加分配方式 
	        {name: 'operateTime',type: 'date',convert : dateConvert}, // 操作时间
	        
	        {name: 'pdaStatus',type: 'string',
	        	convert:function(value){
					 if(value=='NO'){
						 return order.queryExpressDispatchOrder.i18n('pkp.order.queryExpressDispatchOrder.none');
					 }else if(value == 'EXCEPTION'){
						 return order.queryExpressDispatchOrder.i18n('pkp.order.queryExpressDispatchOrder.exception');
					 }else{
						 return order.queryExpressDispatchOrder.i18n('pkp.order.queryExpressDispatchOrder.normal')
					 }
				  }}, // PDA使用状态
	        {name: 'driverName',type: 'string'}, // 司机姓名
	        {name: 'driverMobile',type: 'string'}, // 司机手机
	        {name: 'vehicleNo',type: 'string'}, // 车牌号
	        {name : 'customerName',type : 'string'},// 客户姓名
	        {name : 'mobile',type : 'string'}, // 手机号
	        {name : 'tel',type: 'string'}, // 电话
	        {name : 'operateNotes',type: 'string'}, // 操作备注
	        {name: 'orderNotes',type: 'string'},// 订单备注
	        {name: 'deliverTime',type: 'date',convert : dateConvert},// 派车时间 == 用车时间
	        {name: 'processStatus',type: 'string'}// 处理状态
	]
});

//定义到达联数据store
Ext.define('Foss.order.queryExpressDispatchOrder.vehicleRecordStore', {
	 extend: 'Ext.data.Store',
     model: 'Foss.order.queryExpressDispatchOrder.vehicleRecord',
     pageSize: 10,
     proxy: {
   	//代理的类型为ajax代理
 		type: 'ajax',
 		//提交方式
 		actionMethods:'POST',
 		url: order.realPath('queryExpressVehicleRecord.action'),
 		//定义一个读取器
 		reader: {
 			//以JSON的方式读取
 			type: 'json',
 			//定义读取JSON数据的根对象
 			root: 'dispatchOrderConditionVo.vehicleDtos',
 			//返回总数
 			totalProperty : 'totalCount'
 		}
     },
     listeners: {
   		//查询事件
		beforeload : function(s, operation, eOpts) {
			//执行查询，首先查询条件，为全局变量，在查询条件的FORM创建时生成
			var form = Ext.getCmp('T_order-queryExpressDispatchOrderIndex_content').getQueryForm(),
				searchParams = form.getValues(),
				linkregin = form.items.items[7].items;;
			var proCode = linkregin.items[0].getValue(), 
				cityCode = linkregin.items[1].getValue(), 
				countyCode = linkregin.items[2].getValue();
			var dto = '';
			var params = {
				    'dispatchOrderConditionVo.dispatchOrderVehicleDto.deliverBeginTime':searchParams.deliverBeginTime,
				    'dispatchOrderConditionVo.dispatchOrderVehicleDto.deliverEndTime':searchParams.deliverEndTime,
//				    'dispatchOrderConditionVo.dispatchOrderVehicleDto.pickupRegionCode':searchParams.pickupRegionCode,
//				    'dispatchOrderConditionVo.dispatchOrderVehicleDto.orgCode':order.queryExpressDispatchOrder.fleetCode,
				    'dispatchOrderConditionVo.dispatchOrderVehicleDto.orderSendStatus':searchParams.orderSendStatus,
				    'dispatchOrderConditionVo.dispatchOrderVehicleDto.orderStatus':searchParams.orderStatus,
				    'dispatchOrderConditionVo.dispatchOrderVehicleDto.vehicleNo':searchParams.vehicleNo,
				    'dispatchOrderConditionVo.dispatchOrderVehicleDto.driverCode':searchParams.driverCode,
				    'dispatchOrderConditionVo.dispatchOrderVehicleDto.orderNo':searchParams.orderNo,
				    'dispatchOrderConditionVo.dispatchOrderVehicleDto.orderVehicleBeginTime':searchParams.orderVehicleBeginTime,
				    'dispatchOrderConditionVo.dispatchOrderVehicleDto.orderVehicleEndTime':searchParams.orderVehicleEndTime,
				    'dispatchOrderConditionVo.dispatchOrderVehicleDto.proCode':proCode,
				    'dispatchOrderConditionVo.dispatchOrderVehicleDto.cityCode':cityCode,
				    'dispatchOrderConditionVo.dispatchOrderVehicleDto.countyCode':countyCode
			};
//			var districtList = Ext.getCmp('T_order-queryExpressDispatchOrderIndex_content').getQueryForm().getForm().findField('districtCode').getValue(),
//				salesDepartmentCodes = Ext.getCmp('T_order-queryExpressDispatchOrderIndex_content').getQueryForm().getForm().findField('salesDepartmentCode').getValue();
//			for(var i=0;i<districtList.length;i++){
//				params['dispatchOrderConditionVo.dispatchOrderVehicleDto.districtList['+i+']'] = districtList[i];
//			}
//			for(var i=0;i<salesDepartmentCodes.length;i++){
//				params['dispatchOrderConditionVo.dispatchOrderVehicleDto.salesDepartmentCodes['+i+']'] = salesDepartmentCodes[i];
//			}
			Ext.apply(operation, {
				params : params
			});	
		}
	}
 });

order.queryExpressDispatchOrder.getTargetDateStart = function(date, day) {
	var d, s, t, t2;
	var MinMilli = 1000 * 60;
	var HrMilli = MinMilli * 60;
	var DyMilli = HrMilli * 24;
	t = Date.parse(date);
	if(typeof(day) == 'undefined' || day == 0){
		t2 =  new Date(t);
	}else{
		t2 =  new Date(t+day*DyMilli);
	}
	t2.setHours(0);
	t2.setMinutes(0);
	t2.setSeconds(0);
	t2.setMilliseconds(0);
	return t2;
};
order.queryExpressDispatchOrder.getTargetDateEnd = function(date, day) {	
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

//查询条件
Ext.define('Foss.order.queryExpressDispatchOrder.QueryForm', {
    extend: 'Ext.form.Panel',
    id : 'Foss_order_queryExpressDispatchOrder_QueryForm_Id',
    title: order.queryExpressDispatchOrder.i18n('pkp.order.queryExpressDispatchOrder.queryCondition'),
    frame: true,
    cls:'autoHeight',
	bodyCls:'autoHeight',
	//收缩
	collapsible: true,
	//动画收缩
	animCollapse: true,
	layout : 'column',
    defaults: {
    	xtype: 'textfield',
		margin:'2',
		labelWidth : 120
	},
    initComponent: function() {
        var me = this;
        Ext.applyIf(me, {
            items: [
				{
                    xtype: 'combobox',
                    name: 'orderSendStatus',
                    columnWidth: .25,
                    fieldLabel: order.queryExpressDispatchOrder.i18n('pkp.order.queryExpressDispatchOrder.orderSendStatus'),
                    queryModel:'local',
					displayField:'valueName',
					valueField:'valueCode',
					editable:false,
					labelWidth : 110,
                    store: FossDataDictionary.getDataDictionaryStore('PKP_ORDER_SEND_STATUS',null,{
					 'valueCode': '',
               		 'valueName': '全部'
					})
                },
                {
                    xtype: 'combobox',
                    name: 'orderStatus',
                    columnWidth: .25,
                    fieldLabel: order.queryExpressDispatchOrder.i18n('pkp.order.queryExpressDispatchOrder.orderCompleteStatus'),
                    queryModel:'local',
					displayField:'valueName',
					valueField:'valueCode',
					editable:false,
					labelWidth : 110,
                    store: FossDataDictionary.getDataDictionaryStore('PKP_ORDER_STATUS',null,{
					 'valueCode': '',
               		 'valueName': '全部'
					})
                },
                {
					xtype: 'rangeDateField',
					fieldId: 'Foss_order_QueryForm_expressOrderHandle_ID',
					fieldLabel: order.queryExpressDispatchOrder.i18n('pkp.order.queryExpressDispatchOrder.beginendTime'),
					dateType: 'datetimefield_date97',
					fromName: 'deliverBeginTime',
					toName: 'deliverEndTime',
					allowFromBlank: false,
					allowToBlank: false,
					dateRange : 7,
					fromValue: Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()
							,0,0,0),'Y-m-d H:i:s'),
					toValue: Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()
							,23,59,59),'Y-m-d H:i:s'),
					columnWidth: .5
				},
                {
                    fieldLabel: order.queryExpressDispatchOrder.i18n('pkp.order.queryExpressDispatchOrder.vehicleNo'),
                    columnWidth: .25,
                    xtype : 'commonOldExpressEmpselector',
                    districtCodes : order.countyCodes,
                    myQueryParam : 'vehicleNo',
                    active : 'Y',
                    showContent : '{vehicleNo}&nbsp;&nbsp;&nbsp;{empName}',
                    name:'vehicleNo',
					labelWidth:110
                },
                {
                    fieldLabel: order.queryExpressDispatchOrder.i18n('pkp.order.queryExpressDispatchOrder.orderNo'),
                    columnWidth: .25,
                    name: 'orderNo',
					labelWidth:110
                },
                {
					xtype: 'rangeDateField',
					fieldId: 'Foss_order_QueryForm_expressOrderHandle_create_ID',
					fieldLabel: order.queryExpressDispatchOrder.i18n('pkp.order.queryExpressDispatchOrder.creat.beginendTime'),
					dateType: 'datetimefield_date97',
					fromName: 'orderVehicleBeginTime',
					toName: 'orderVehicleEndTime',
					allowFromBlank: false,
					allowToBlank: false,
					dateRange : 7,
					fromValue: Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()
							,0,0,0),'Y-m-d H:i:s'),
					toValue: Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()
							,23,59,59),'Y-m-d H:i:s'),
					columnWidth: .5
				},
				{
            		fieldLabel : order.queryExpressDispatchOrder.i18n('pkp.order.queryExpressDispatchOrder.driver'),
            		name : 'driverCode',
            		xtype : 'commonOldExpressEmpselector',
//            		myQueryParam : 'empName',
            		displayField : 'empName',// 显示名称
        			valueField : 'empCode',// 值
        			active : 'Y',
        			districtCodes : order.countyCodes,
        			showContent : '{empCode}&nbsp;&nbsp;&nbsp;{empName}',
            		columnWidth: .25,
					labelWidth:110
            	},
				{
			   		xtype:'linkregincombselector',
			   		type : 'P-C-C',
			   		allowBlank:true,
					nationIsBlank:true,
					provinceIsBlank:true,
					cityIsBlank:true,
					areaIsBlank:true,
//				    allowBlank:true,
				    fieldLabel : order.queryExpressDispatchOrder.i18n('pkp.order.queryExpressDispatchOrder.district'),
				    labelWidth : 110,
				    columnWidth: .75
				},
//                {
//                    fieldLabel: order.queryExpressDispatchOrder.i18n('pkp.order.orderHandle.smallZone'),
//                    columnWidth: .33,
//                    xtype : 'commonsmallzoneselector',
//                    parentOrgCode : order.queryExpressDispatchOrder.fleetCode,
//            		regionType : 'PK',
//                    name: 'pickupRegionCode'
//                },
//                {
//            		fieldLabel : order.queryExpressDispatchOrder.i18n('pkp.order.queryExpressDispatchOrder.salesDepartment'),
//            		xtype : 'commonmotorcadesaledeptselector',
//            		motorcadeCode : order.queryExpressDispatchOrder.fleetCode,
//            		name : 'salesDepartmentCode',
//            		columnWidth:.34
//            	},
//                {
//                    fieldLabel: order.queryExpressDispatchOrder.i18n('pkp.order.orderHandle.district'),
//                    columnWidth: .33,
//                    xtype : 'commonmotorcadedistrictselector',
//                    motorcadeCode : order.queryExpressDispatchOrder.fleetCode,
//                    name: 'districtCode'
//                },
                {
        			border: 1,
        			xtype:'container',
        			columnWidth:1,
        			defaultType:'button',
        			layout:'column',
        			items:[{
        				text:order.queryExpressDispatchOrder.i18n('pkp.order.queryExpressDispatchOrder.reset'),
        				columnWidth:.08,
        				handler:function(){
        					var myform = this.up('form').getForm();
        					myform.reset(); 
        					myform.findField('deliverBeginTime').setValue(Ext.Date.format(order.queryExpressDispatchOrder.getTargetDateStart(new Date(),0),'Y-m-d H:i:s'));
        					myform.findField('deliverEndTime').setValue(Ext.Date.format(order.queryExpressDispatchOrder.getTargetDateEnd(new Date(),0),'Y-m-d H:i:s'));
        					myform.findField('orderVehicleBeginTime').setValue(Ext.Date.format(order.queryExpressDispatchOrder.getTargetDateStart(new Date(),0),'Y-m-d H:i:s'));
        					myform.findField('orderVehicleEndTime').setValue(Ext.Date.format(order.queryExpressDispatchOrder.getTargetDateEnd(new Date(),0),'Y-m-d H:i:s'));
        				}
        		},{
        			xtype: 'container',
        			border : false,
        			columnWidth:.84,
        			html: '&nbsp;'
        		},{
        			text: order.queryExpressDispatchOrder.i18n('pkp.order.queryExpressDispatchOrder.query'),
        			cls:'yellow_button',
        			plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin', {
						seconds: 3
					}),
					disabled : !order.queryExpressDispatchOrder.isPermission('queryExpressDispatchOrderindex/queryExpressDispatchOrderindexquerybutton'),
        			hidden : !order.queryExpressDispatchOrder.isPermission('queryExpressDispatchOrderindex/queryExpressDispatchOrderindexquerybutton'),
        			columnWidth:.08,
        			handler:function(btn){
        				var page = Ext.getCmp('T_order-queryExpressDispatchOrderIndex_content'), 
        					form = page.getQueryForm(), resultGrid = page.getResultGrid(),
        					searchParams = form.getValues(),
        					linkregin = form.items.items[7].items;
						var proCode = linkregin.items[0].getValue(), 
							cityCode = linkregin.items[1].getValue(), 
							countyCode = linkregin.items[2].getValue(),notEmptyCount = 0;
						var thisForm = btn.up('form').getForm();
						if (!thisForm.isValid()) {
							return ;
						}
						if (!Ext.isEmpty(proCode)) {
							notEmptyCount ++;
						}
						if (!Ext.isEmpty(cityCode)) {
							notEmptyCount ++;
						}
						if (!Ext.isEmpty(countyCode)) {
							notEmptyCount ++;
						}
						if (notEmptyCount > 0 && notEmptyCount < 3) {
							Ext.ux.Toast.msg(order.queryExpressDispatchOrder.i18n('pkp.order.expressOrderHandle.warning'), '省市区必须全部输入！', 'error', 3000);
							return;
						}
        				resultGrid.getPagingToolbar().moveFirst();
        			}
        		}]
        	}
            ]
        });
        me.callParent(arguments);
    }
//    listeners : {
//    	boxready:function(component, eOpts){
//			var form = Ext.getCmp('Foss_order_queryExpressDispatchOrder_QueryForm_Id').getForm();
//			if(order.queryExpressDispatchOrder.dept=='Y')
//			{
//				Ext.suspendLayouts();
//				form.findField('districtCode').setVisible(false);
//				form.findField('pickupRegionCode').setVisible(false);
//				form.findField('salesDepartmentCode').setVisible(false);
//				Ext.resumeLayouts(true);
//			}
//		}
//	}
});

Ext.define('Foss.order.queryExpressDispatchOrder.editPanel',{
	extend: 'Ext.form.Panel',
	layout: 'column',
	cls:'autoHeight',
	bodyCls:'autoHeight',
	defaults: {
		margin:'5 10 5 10',
		xtype : 'textfield',
		labelWidth:120
	},
	constructor: function(config){
		var me = this, cfg = Ext.apply({}, config);
		me.items = [{
			fieldLabel:'订单ID',
			columnWidth: 1,
			name: 'orderId',
			readOnly: true,
			hidden: true
		},
		{
			fieldLabel:'订单类型',
			columnWidth: 1,
			name: 'orderType',
			readOnly: true,
			hidden: true
		},
		{
			fieldLabel:order.queryExpressDispatchOrder.i18n('pkp.order.queryExpressDispatchOrder.orderNo'),
			columnWidth: 1,
			name: 'orderNo',
			readOnly: true
		},
		{
			fieldLabel:order.queryExpressDispatchOrder.i18n('pkp.order.queryExpressDispatchOrder.vehicleTime'),
			columnWidth: 1,
			name: 'orderVehicleTime',
			readOnly: true,
			xtype: 'datefield',
			format : 'Y-m-d H:i:s'
		},
		{
	        xtype: 'combobox',
	        name: 'orderStatus',
	        columnWidth: 1,
	        fieldLabel: order.queryExpressDispatchOrder.i18n('pkp.order.queryExpressDispatchOrder.orderCompleteStatus'),
	        queryModel:'local',
			displayField:'valueName',
			valueField:'valueCode',
			editable:false,
			labelWidth:120
		},
		{
			xtype: 'button',
			plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin', {
				seconds: 3
			}),
			text: order.queryExpressDispatchOrder.i18n('pkp.order.queryExpressDispatchOrder.confirm'),
			columnWidth: .25,
			handler:function(){
				 var win = this.up('window');
				 var myForm = this.up('form').getForm();
				 var oldOrderStatus = myForm.getRecord().data.orderStatus;
				 var orderStatus = myForm.findField('orderStatus').getValue();
				 if(oldOrderStatus == orderStatus){
				 	 win.hide();
				 	 return;
				 }
				Ext.Msg.confirm(order.queryExpressDispatchOrder.i18n('pkp.order.queryExpressDispatchOrder.warning'), order.queryExpressDispatchOrder.i18n('pkp.order.queryExpressDispatchOrder.isEdit'), function(btn,text){
					if(btn=="yes"){
	    				var dispatchOrder = myForm.getValues();
						Ext.Ajax.request({
						    url: order.realPath('updateExpressDispatchOrderStatusById.action'),
						    jsonData:{"dispatchOrderConditionVo":{"dispatchOrderVehicleDto":dispatchOrder}},
						    success: function(response){
						        var text = response.responseText;
						        var result = Ext.decode(response.responseText);
						        win.hide();
						        var vehicleGridStore = Ext.getCmp('Foss_order_queryExpressDispatchOrder_vehicleGrid_GridPanel_Id').store;
						        vehicleGridStore.load();
						        Ext.ux.Toast.msg(order.queryExpressDispatchOrder.i18n('pkp.order.queryExpressDispatchOrder.warning'),result.message);
						    }
					});
				}
			});
		}
		},{
			xtype: 'container',
			html: '&nbsp;',
			columnWidth: .5
		},{
			xtype: 'button',
			text: order.queryExpressDispatchOrder.i18n('pkp.order.queryExpressDispatchOrder.cancel'),
			columnWidth: .25,
			handler:function(){
				this.up('window').hide();
			}
		}];
		me.callParent([cfg]);
	}
});

//编辑任务订单状态window
Ext.define('Foss.order.queryExpressDispatchOrder.editWindow', {
	extend: 'Ext.window.Window',
	width: 420,
	height : 210,
	title: order.queryExpressDispatchOrder.i18n('pkp.order.queryExpressDispatchOrder.edit'),
	layout:'column',
	border: false,
	//将window的关闭事件close 设成 hide
	closeAction : 'hide',
	editPanel : null,
	getEditPanel : function() {
		if (this.editPanel == null) {
			this.editPanel = Ext.create('Foss.order.queryExpressDispatchOrder.editPanel');
		}
		return this.editPanel;
	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.items = [me.getEditPanel()];
		me.callParent([cfg]);
	}
});

Ext.define('Foss.order.queryExpressDispatchOrder.vehicleGrid', {
    extend: 'Ext.grid.Panel',
    title: order.queryExpressDispatchOrder.i18n('pkp.order.queryExpressDispatchOrder.dispatchVehicleRecord'),
    frame: true,
    cls:'autoHeight',
	bodyCls:'autoHeight',
	//收缩
	collapsible: true,
	//动画收缩
	animCollapse: true,
	emptyText: order.queryExpressDispatchOrder.i18n('pkp.order.queryExpressDispatchOrder.queryResultNull'),
	selModel:Ext.create('Ext.selection.CheckboxModel',{
		listeners:{
			'beforerenderer':function(record, store) {
		        if(record.data.pdaStatus == '无' || record.data.pdaStatus == '异常' 
		        || record.data.orderStatus == 'NONE_HANDLE'  //未处理
		        || record.data.orderStatus == 'RETURN'  //已退回
		        || record.data.orderStatus == 'AGAIN_PICKUP' //待改接
		        || record.data.orderStatus == 'WAYBILL' //已开单
		        || record.data.orderStatus == 'CANCEL' //已作废
		        || record.data.orderStatus == 'PICKUP_FAILURE'){   //揽货失败
		            return true;  //不能进行选择
		        }else{   
		           return false;
		         }
			}
		}
	 }),
	 editWindow: null,
	 getEditWindow: function(){
		if(this.editWindow==null){
			this.editWindow = Ext.create('Foss.order.queryExpressDispatchOrder.editWindow');
		}
		return this.editWindow;
	 },
     columns: [{
    	xtype:'actioncolumn',
		width:40,
		text: order.queryExpressDispatchOrder.i18n('pkp.order.queryExpressDispatchOrder.edit'),
		disabled : !order.queryExpressDispatchOrder.isPermission('queryExpressDispatchOrderindex/queryExpressDispatchOrderindexeditbutton'),
		align: 'center',
		items: [{
			iconCls: 'deppon_icons_edit',
		tooltip: order.queryExpressDispatchOrder.i18n('pkp.order.queryExpressDispatchOrder.edit'),
		handler: function(grid, rowIndex, colIndex) {
			var selection = grid.getStore().getAt(rowIndex), 
				editWindow = grid.up('grid').getEditWindow(),
				editPanel = editWindow.getEditPanel();
				var record = grid.getStore().getAt(rowIndex);
				if(record.get('orderStatus')=='DISPATCH_VEHICLE'||record.get('orderStatus')=='PDA_ACCEPT'||record.get('orderStatus')=='PICKUPING') {
					editPanel.down('combobox').store.removeAll();
					editPanel.down('combobox').bindStore(
							FossDataDictionary.getDataDictionaryStore('PKP_ORDER_STATUS', null, null, [ 
							       "DISPATCH_VEHICLE","PDA_ACCEPT","PICKUPING",
					               "RETURN","PICKUP_FAILURE","WAYBILL"]));
				}else {
					editPanel.down('combobox').store.removeAll();
					editPanel.down('combobox').bindStore(
							FossDataDictionary.getDataDictionaryStore('PKP_ORDER_STATUS', null, null, [ 
							       "NONE_HANDLE", "DISPATCH_VEHICLE","PDA_ACCEPT","PICKUPING",
					               "AGAIN_PICKUP","RETURN","PICKUP_FAILURE","WAYBILL"]));
				}
				editPanel.loadRecord(selection);
				editWindow.show();
		},
		getClass:function(v,m,r,rowIndex){
			if(r.get('orderStatus') == 'PICKUP_FAILURE'
		|| r.get('orderStatus') == 'CANCEL'
		|| r.get('orderStatus') == 'WAYBILL'){
		return 'deppon_icons_edit_hidden';
		}else{
			return 'deppon_icons_edit';
				}
		}
		}]
     },
//		{ header: order.queryExpressDispatchOrder.i18n('pkp.order.queryExpressDispatchOrder.vehicleDept'), dataIndex: 'orderVehicleOrgName', width: 220 },
	{ header: order.queryExpressDispatchOrder.i18n('pkp.order.queryExpressDispatchOrder.orderNo'), dataIndex: 'orderNo', width: 120 },
	{ header: order.queryExpressDispatchOrder.i18n('pkp.order.queryExpressDispatchOrder.orderSendStatus'), dataIndex: 'orderSendStatus', width: 80,
		renderer:function(value){
	    			return FossDataDictionary.rendererSubmitToDisplay(value,'PKP_ORDER_SEND_STATUS');
				}
	},
	{ header: order.queryExpressDispatchOrder.i18n('pkp.order.queryExpressDispatchOrder.orderCompleteStatus'), dataIndex: 'orderStatus', width: 80,
		renderer:function(value){
	    			return FossDataDictionary.rendererSubmitToDisplay(value,'PKP_ORDER_STATUS');
				}
	},
	{ header: order.queryExpressDispatchOrder.i18n('pkp.order.orderHandle.pickupAddress'), dataIndex: 'pickupAddress', width: 150},
	{ header: order.queryExpressDispatchOrder.i18n('pkp.order.queryExpressDispatchOrder.acceptStatus'),//分配方式  2014-11-15 cdl DMANA-6209快递派车记录界面增加分配方式 
		dataIndex: 'acceptStatus', 
		align: 'center',
		renderer : function(value) {
			if (value == 'SYS') {
				return '系统分配';
			} else if(value == 'HANDLE'){
				return '人工分配';
			} else {
				return '';
			}
		},
		width:100
	},
	{ header: order.queryExpressDispatchOrder.i18n('pkp.order.queryExpressDispatchOrder.operateTime'),//操作时间
		dataIndex: 'operateTime', 
		align: 'center',
		renderer : function(value) {
			if (value != null) {
				var date = Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
				return date;
			} else {
				return null;
			}
		},
		width:140
	},
	//{ header: order.queryExpressDispatchOrder.i18n('pkp.order.queryExpressDispatchOrder.operateTime'), dataIndex: 'operateTime', width: 60},		
//		{ text : order.queryExpressDispatchOrder.i18n('pkp.order.orderHandle.tel'), dataIndex : 'tel', width : 80 }, 
//		{ header: order.queryExpressDispatchOrder.i18n('pkp.order.queryExpressDispatchOrder.usecarTime'), dataIndex: 'deliverTime', width: 200,xtype : 'datecolumn',format : 'Y-m-d H:i:s' },
	{ header: order.queryExpressDispatchOrder.i18n('pkp.order.queryExpressDispatchOrder.useStatus'), dataIndex: 'pdaStatus', width: 60},
//		{ header: order.queryExpressDispatchOrder.i18n('pkp.order.queryExpressDispatchOrder.operateType'), dataIndex: 'processStatus', width: 100, 
//			renderer : function(value) {
//				return FossDataDictionary.rendererSubmitToDisplay(value, 'PKP_PROCESS_STATUS');
//			}
//		},
	{ header: order.queryExpressDispatchOrder.i18n('pkp.order.queryExpressDispatchOrder.driver'), dataIndex: 'driverName', width: 60},
	{ header: order.queryExpressDispatchOrder.i18n('pkp.order.queryExpressDispatchOrder.vehicleNo'), dataIndex: 'vehicleNo', width: 80},
	{ header: order.queryExpressDispatchOrder.i18n('pkp.order.queryExpressDispatchOrder.driverMobile'), dataIndex: 'driverMobile', width: 100},
	{ header: order.queryExpressDispatchOrder.i18n('pkp.order.queryExpressDispatchOrder.customerName'), dataIndex: 'customerName', width: 80},
	{ header: order.queryExpressDispatchOrder.i18n('pkp.order.queryExpressDispatchOrder.telphone'), dataIndex: 'tel', width: 100},
	{ header: order.queryExpressDispatchOrder.i18n('pkp.order.queryExpressDispatchOrder.mobile'), dataIndex: 'mobile', width: 100},	
	//		 {
//			text : order.queryExpressDispatchOrder.i18n('pkp.order.orderHandle.customerName'),
//			dataIndex : 'customerName',
//			xtype : 'ellipsiscolumn',
//			width : 100}, 
//		{
//			text : order.queryExpressDispatchOrder.i18n('pkp.order.orderHandle.mobile'),
//			dataIndex : 'mobile',
//			width : 100
//		},
	{ header: order.queryExpressDispatchOrder.i18n('pkp.order.queryExpressDispatchOrder.notes'), dataIndex: 'orderNotes', width: 60 },
	{ header: order.queryExpressDispatchOrder.i18n('pkp.order.queryExpressDispatchOrder.reason'), dataIndex: 'operateNotes', width: 80, xtype :'linebreakcolumn', 
		renderer : function(value) {
			if(value!=null&&value.length>1) { 
				if(value.charAt(value.length-1)=='-') {  
					value = value.substring(0,value.length-1);
				}
			}
			var result = FossDataDictionary.rendererSubmitToDisplay(value, 'PKP_COMPANY_REJECT_REASON');
			if(value == result){
				result = FossDataDictionary.rendererSubmitToDisplay(value, 'PKP_CUSTOMER_REJECT_REASON');
			}
			return result;
		}
	}
    ],
    viewConfig: {
        stripeRows: false,
        enableTextSelection: true,
		getRowClass: function(record, rowIndex, rp, ds) {
			//如果订单状态为"未处理"或"已派车" 且大于15分钟 则标黄
			var orderStatus = record.get('orderStatus');
			if(orderStatus == 'NONE_HANDLE' || orderStatus == 'DISPATCH_VEHICLE'){
				var deliverTime = record.get('deliverTime');
				//var date = Date.parseDate(deliverTime,'Y-m-d h:i:s');
				var newDate =new Date();
				var result = (newDate - deliverTime)- (15*60*1000);
				if (parseInt(result)>=0) {
					return 'order-dispatchOrder-row-yellow';
				}
			}
		}
    },
    pagingToolbar : null,
  	getPagingToolbar : function() {
  		if (this.pagingToolbar == null) {
  			this.pagingToolbar = Ext.create('Deppon.StandardPaging', {
  				store : this.store,
				plugins: 'pagesizeplugin',
				displayInfo: true
  			});
  		}
  		return this.pagingToolbar;
  	},
    constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.order.queryExpressDispatchOrder.vehicleRecordStore');
		me.tbar = ['->', {
					xtype : 'button',
					plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin', {
						seconds: 3
					}),
					text : order.queryExpressDispatchOrder.i18n('pkp.order.queryExpressDispatchOrder.export'),
					disabled : !order.queryExpressDispatchOrder.isPermission('queryExpressDispatchOrderindex/queryExpressDispatchOrderexportbutton'),
					hidden : !order.queryExpressDispatchOrder.isPermission('queryExpressDispatchOrderindex/queryExpressDispatchOrderexportbutton'),
					handler : function(){
						if(!Ext.fly('downloadAttachFileForm')){
						    var frm = document.createElement('form');
						    frm.id = 'downloadAttachFileForm';
						    frm.style.display = 'none';
						    document.body.appendChild(frm);
						}
						var form = Ext.getCmp('T_order-queryExpressDispatchOrderIndex_content').getQueryForm(),
						searchParams = form.getValues(),
						linkregin = form.items.items[7].items;;
						var proCode = linkregin.items[0].getValue(), 
							cityCode = linkregin.items[1].getValue(), 
							countyCode = linkregin.items[2].getValue();
						var result = Ext.getCmp('Foss_order_queryExpressDispatchOrder_vehicleGrid_GridPanel_Id').store;
						//若异常信息不为空
						if(result.getCount()!=0){
							var searchParams = Ext.getCmp('T_order-queryExpressDispatchOrderIndex_content').getQueryForm().getValues(),
								params = {
									'dispatchOrderConditionVo.dispatchOrderVehicleDto.deliverBeginTime':searchParams.deliverBeginTime,
									'dispatchOrderConditionVo.dispatchOrderVehicleDto.deliverEndTime':searchParams.deliverEndTime,
									'dispatchOrderConditionVo.dispatchOrderVehicleDto.orderSendStatus':searchParams.orderSendStatus,
								    'dispatchOrderConditionVo.dispatchOrderVehicleDto.orderStatus':searchParams.orderStatus,
								    'dispatchOrderConditionVo.dispatchOrderVehicleDto.vehicleNo':searchParams.vehicleNo,
								    'dispatchOrderConditionVo.dispatchOrderVehicleDto.driverCode':searchParams.driverCode,
								    'dispatchOrderConditionVo.dispatchOrderVehicleDto.orderNo':searchParams.orderNo,
								    'dispatchOrderConditionVo.dispatchOrderVehicleDto.orderVehicleBeginTime':searchParams.orderVehicleBeginTime,
								    'dispatchOrderConditionVo.dispatchOrderVehicleDto.orderVehicleEndTime':searchParams.orderVehicleEndTime,
								    'dispatchOrderConditionVo.dispatchOrderVehicleDto.proCode':proCode,
								    'dispatchOrderConditionVo.dispatchOrderVehicleDto.cityCode':cityCode,
								    'dispatchOrderConditionVo.dispatchOrderVehicleDto.countyCode':countyCode
							}
							Ext.Ajax.request({
								url:order.realPath('exportExpressVehicleRecord.action'),
								form: Ext.fly('downloadAttachFileForm'),
								method : 'POST',
								params : params,
								isUpload: true
							});
						}else{
							//或者提示不能导出
							Ext.ux.Toast.msg(order.queryExpressDispatchOrder.i18n('pkp.order.queryExpressDispatchOrder.warning'),order.queryExpressDispatchOrder.i18n('pkp.order.queryExpressDispatchOrder.notExport'), 'error', 3000);
						}
					}
		}];
		me.dockedItems = [{
			xtype: 'toolbar',
			dock: 'bottom',
			layout:'column',
			defaults:{
				margin:'0 0 5 3'
			},		
			items: [{
				xtype: 'button',
				plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin', {
					seconds: 3
				}),
		        text: order.queryExpressDispatchOrder.i18n('pkp.order.queryExpressDispatchOrder.pdaException'),
		        disabled : !order.queryExpressDispatchOrder.isPermission('queryExpressDispatchOrderindex/queryExpressDispatchOrderindexexceptionbutton'),
		        hidden : !order.queryExpressDispatchOrder.isPermission('queryExpressDispatchOrderindex/queryExpressDispatchOrderindexexceptionbutton'),
		        handler:function(){
		        	var mygrid = this.up('gridpanel');
		        	var selectRow = mygrid.getSelectionModel().getSelection();
		        	if(selectRow.length ==0){
		        		 Ext.ux.Toast.msg(order.queryExpressDispatchOrder.i18n('pkp.order.queryExpressDispatchOrder.warning'),order.queryExpressDispatchOrder.i18n('pkp.order.queryExpressDispatchOrder.chooseRow'));
		        	}else{
		        	Ext.Msg.confirm(order.queryExpressDispatchOrder.i18n('pkp.order.queryExpressDispatchOrder.warning'), order.queryExpressDispatchOrder.i18n('pkp.order.queryExpressDispatchOrder.isModify'), function(btn,text){
						if(btn=="yes"){
							var orderIds =  new Array();
							for(var i=0;i<selectRow.length;i++){
								orderIds.push({id : selectRow[i].data.id,orderId:selectRow[i].data.orderId});
							}
							Ext.Ajax.request({
							    url: order.realPath('updateExpressIspdaByVehicleId.action'),
							    jsonData:{'dispatchOrderConditionVo':{'orderIdsAndVehicleIds':orderIds}},
							    success: function(response){
							        var result = Ext.decode(response.responseText);
							        mygrid.store.load();
							        Ext.ux.Toast.msg(order.queryExpressDispatchOrder.i18n('pkp.order.queryExpressDispatchOrder.warning'),result.message);
							    }
						});
					}
				});
		       }
		       }  	
		    }]
        }];
		me.bbar = me.getPagingToolbar();
        me.callParent(arguments);
    }
});

Ext.onReady(function() {
	Ext.QuickTips.init();
    var queryForm = Ext.create("Foss.order.queryExpressDispatchOrder.QueryForm");
    var vehicleGrid = Ext.create("Foss.order.queryExpressDispatchOrder.vehicleGrid",{
    	id: "Foss_order_queryExpressDispatchOrder_vehicleGrid_GridPanel_Id"
    });
    
	Ext.create('Ext.panel.Panel',{
		id: 'T_order-queryExpressDispatchOrderIndex_content',
		cls:"panelContentNToolbar",
		bodyCls:'panelContentNToolbar-body',
		layout:'auto',
		getQueryForm: function(){
			return queryForm;
		},
		getResultGrid: function(){
			return vehicleGrid;
		},
		items: [queryForm,vehicleGrid],
		renderTo: 'T_order-queryExpressDispatchOrderIndex-body'
	});
});
