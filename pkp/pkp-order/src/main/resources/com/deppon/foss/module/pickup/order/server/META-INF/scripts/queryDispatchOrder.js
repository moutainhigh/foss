/**
 * 查询处理订单任务
 * @author 097972-foss-dengtingting
 */

(function() {
	if(FossUserContext.getCurrentDept().transDepartment == 'Y' 
		|| FossUserContext.getCurrentDept().dispatchTeam == 'Y'
		|| FossUserContext.getCurrentDept().transTeam == 'Y'){
		// 车队
		order.queryDispatchOrder.dept = 'N';
	} else {
		// 营业部
		order.queryDispatchOrder.dept = 'Y';
	}
	if(order.queryDispatchOrder.dept != 'Y'){
		// 获得当前组织对应的车队
		Ext.Ajax.request({
			url : order.realPath('querySuperOrg.action'),
			async : false,
			success : function(response) {
				var json = Ext.decode(response.responseText);
				// 如果找不到对应的顶级车队
				if(Ext.isEmpty(json.fleetCode)){
					// 判定为营业部，权限不放大
					order.queryDispatchOrder.dept = 'Y';
				}
				Ext.apply(order.queryDispatchOrder, {
					fleetCode : json.fleetCode
				});
			},
			exception : function(response) {
				var result = Ext.decode(response.responseText);
				Ext.ux.Toast.msg(order.orderHandle.i18n('pkp.order.orderHandle.warning'), result.message, 'error', 3000);
			}
		});
	} else {
		Ext.apply(order.queryDispatchOrder, {
			fleetCode : FossUserContext.getCurrentDept().code
		});
	};
	
	order.queryDispatchOrder.orderStatusStore = FossDataDictionary.getDataDictionaryStore('PKP_ORDER_STATUS',null);
	order.queryDispatchOrder.orderStatusStore.removeAt(order.queryDispatchOrder.orderStatusStore.find('valueCode','REVOCATION'));//移除撤销
})();

//定义派车记录model
Ext.define('Foss.order.queryDispatchOrder.vehicleRecord',{
	extend: 'Ext.data.Model',
	fields: [
	        {name: 'id',type: 'string'}, //派车记录ID
	        {name: 'orderId',type: 'string'}, //调度订单ID
	        {name: 'orderNo',type: 'string'}, //订单号
	        {name: 'orderType',type: 'string'}, //订单类型
	        {name: 'pickupAddress',type: 'string'}, //接货地址
	        {name: 'pickupRegionName',type: 'string'}, //系统分配小区 PICKUP_REGION_NAME
	        {name: 'actualRegionName',type: 'string'}, //实际接货小区
	        {name: 'orderVehicleOrgName',type: 'string'}, //约车部门名称
	        {name: 'orderSendStatus',type: 'string'}, // 订单任务发送状态
	        {name: 'orderStatus',type: 'string'}, //订单任务完成状态
	        {name: 'earliestPickupTime',type: 'date',convert : dateConvert}, //接货最早时间 ==用车时间
	        {name: 'latestPickupTime',type: 'date',convert : dateConvert}, // 接货最晚时间
	        {name: 'orderVehicleTime',type: 'date',convert : dateConvert}, // 约车时间
	        {name: 'pdaStatus',type: 'string',
	        	convert:function(value){
					 if(value=='NO'){
						 return order.queryDispatchOrder.i18n('pkp.order.queryDispatchOrder.none');
					 }else if(value == 'EXCEPTION'){
						 return order.queryDispatchOrder.i18n('pkp.order.queryDispatchOrder.exception');
					 }else{
						 return order.queryDispatchOrder.i18n('pkp.order.queryDispatchOrder.normal')
					 }
				  }}, // PDA使用状态
	        {name: 'billTime',type: 'date',convert : dateConvert},//PDA开单时间
	        {name: 'driverName',type: 'string'}, // 司机姓名
	        {name: 'driverMobile',type: 'string'}, // 司机手机
	        {name: 'vehicleNo',type: 'string'}, // 车牌号
	        {name : 'customerName',type : 'string'},// 客户姓名
	        {name : 'mobile',type : 'string'}, // 手机号
	        {name : 'tel',type: 'string'}, // 电话
	        {name : 'operateNotes',type: 'string'}, // 操作备注
	        {name: 'orderNotes',type: 'string'},// 订单备注
	        {name: 'deliverTime',type: 'date',convert : dateConvert},// 派车时间 == 用车时间
	        {name: 'processStatus',type: 'string'},// 处理状态
	        {name: 'weight',type:'float'},//重量
	        {name:'volume',type:'float'}, //体积
	        {name:'weightAndVolume',type:'string'},//重量和体积
	        {name: 'usecarTime',type: 'string'}, //用车时间
	        {name: 'acceptStatus',type: 'string'} //自动调度
	]
});

//定义到达联数据store
Ext.define('Foss.order.queryDispatchOrder.vehicleRecordStore', {
	 extend: 'Ext.data.Store',
     model: 'Foss.order.queryDispatchOrder.vehicleRecord',
     pageSize: 10,
     proxy: {
   	//代理的类型为ajax代理
 		type: 'ajax',
 		//提交方式
 		actionMethods:'POST',
 		url: order.realPath('queryVehicleRecord.action'),
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
			var serachParms = Ext.getCmp('T_order-queryDispatchOrderIndex_content').getQueryForm().getValues();
			var dto = '';
			var params = {
				    'dispatchOrderConditionVo.dispatchOrderVehicleDto.deliverBeginTime':serachParms.deliverBeginTime,
				    'dispatchOrderConditionVo.dispatchOrderVehicleDto.deliverEndTime':serachParms.deliverEndTime,
				    'dispatchOrderConditionVo.dispatchOrderVehicleDto.pickupRegionCode':serachParms.pickupRegionCode,
				    'dispatchOrderConditionVo.dispatchOrderVehicleDto.orgCode':order.queryDispatchOrder.fleetCode,
				    'dispatchOrderConditionVo.dispatchOrderVehicleDto.orderSendStatus':serachParms.orderSendStatus,
				    'dispatchOrderConditionVo.dispatchOrderVehicleDto.orderStatus':serachParms.orderStatus,
				    'dispatchOrderConditionVo.dispatchOrderVehicleDto.vehicleNo':serachParms.vehicleNo,
				    'dispatchOrderConditionVo.dispatchOrderVehicleDto.orderNo':serachParms.orderNo,
				    'dispatchOrderConditionVo.dispatchOrderVehicleDto.productCode':serachParms.productCode
			};
			var districtList = Ext.getCmp('T_order-queryDispatchOrderIndex_content').getQueryForm().getForm().findField('districtCode').getValue(),
				salesDepartmentCodes = Ext.getCmp('T_order-queryDispatchOrderIndex_content').getQueryForm().getForm().findField('salesDepartmentCode').getValue();
			for(var i=0;i<districtList.length;i++){
				params['dispatchOrderConditionVo.dispatchOrderVehicleDto.districtList['+i+']'] = districtList[i];
			}
			for(var i=0;i<salesDepartmentCodes.length;i++){
				params['dispatchOrderConditionVo.dispatchOrderVehicleDto.salesDepartmentCodes['+i+']'] = salesDepartmentCodes[i];
			}
			Ext.apply(operation, {
				params : params
			});	
		}
	}
 });

order.queryDispatchOrder.getTargetDate = function(date, day) {
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


//查询条件
Ext.define('Foss.order.queryDispatchOrder.QueryForm', {
    extend: 'Ext.form.Panel',
    id : 'Foss_order_queryDispatchOrder_QueryForm_Id',
    title: order.queryDispatchOrder.i18n('pkp.order.queryDispatchOrder.queryCondition'),
    frame: true,
    cls:'autoHeight',
	bodyCls:'autoHeight',
	//收缩
	collapsible: true,
	//动画收缩
	animCollapse: true,
    layout: {
        type: 'column'
    },
    defaults: {
    	xtype: 'textfield',
		margin:'5 10 5 10',
		labelWidth : 100
	},
    initComponent: function() {
        var me = this;
        Ext.applyIf(me, {
            items: [
                {
                    fieldLabel: order.queryDispatchOrder.i18n('pkp.order.queryDispatchOrder.orderNo'),
                    columnWidth: .33,
                    name: 'orderNo'
                },
                {
                    fieldLabel: order.queryDispatchOrder.i18n('pkp.order.orderHandle.smallZone'),
                    columnWidth: .33,
                    xtype : 'commonsmallzoneselector',
                    parentOrgCode : order.queryDispatchOrder.fleetCode,
            		regionType : 'PK',
                    name: 'pickupRegionCode'
                },
                {
                    fieldLabel: order.queryDispatchOrder.i18n('pkp.order.queryDispatchOrder.vehicleNo'),
                    columnWidth: .34,
                    xtype : 'commontruckselector',
                    name:'vehicleNo'
                },
                {
                    xtype: 'combobox',
                    name: 'orderSendStatus',
                    columnWidth: .33,
                    fieldLabel: order.queryDispatchOrder.i18n('pkp.order.queryDispatchOrder.orderSendStatus'),
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
                    columnWidth: .33,
                    fieldLabel: order.queryDispatchOrder.i18n('pkp.order.queryDispatchOrder.orderCompleteStatus'),
                    queryModel:'local',
					displayField:'valueName',
					valueField:'valueCode',
					editable:false,
					labelWidth : 110,
                    store: FossDataDictionary.getDataDictionaryStore('PKP_ORDER_STATUS',null,{
					 'valueCode': '',
               		 'valueName': '全部'
					})
                },{
            		fieldLabel : order.queryDispatchOrder.i18n('pkp.order.queryDispatchOrder.salesDepartment'),
            		xtype : 'commonmotorcadesaledeptselector',
            		motorcadeCode : order.queryDispatchOrder.fleetCode,
            		name : 'salesDepartmentCode',
            		columnWidth:.34
            	}, {
                    fieldLabel: order.queryDispatchOrder.i18n('pkp.order.orderHandle.district'),
                    columnWidth: .33,
                    xtype : 'commonmotorcadedistrictselector',
                    motorcadeCode : order.queryDispatchOrder.fleetCode,
                    name: 'districtCode'
                },{
					xtype: 'rangeDateField',
					fieldId: 'Foss_order_QueryForm_orderHandle_ID',
					fieldLabel: order.queryDispatchOrder.i18n('pkp.order.queryDispatchOrder.beginendTime'),
					dateType: 'datetimefield_date97',
					fromName: 'deliverBeginTime',
					toName: 'deliverEndTime',
					fromValue: Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()
							,0,0,0),'Y-m-d H:i:s'),
					toValue: Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()
							,23,59,59),'Y-m-d H:i:s'),
					columnWidth: .5,
					labelWidth:120,
					height: 26
				},{
        			//新增
        			fieldLabel : order.queryDispatchOrder.i18n('pkp.order.expressWorkerStatus.transportType'),
        			xtype:'combobox',
        			displayField:'name',
        			valueField:'code',
        			queryMode:'local',
        			triggerAction:'all',
        			editable:true,
        			name: 'productCode',
        			store : Ext.create('Foss.pkp.ProductStore'),
        			columnWidth:.33
        		}, {
        			border: 1,
        			xtype:'container',
        			columnWidth:1,
        			defaultType:'button',
        			layout:'column',
        			items:[{
        				text:order.queryDispatchOrder.i18n('pkp.order.queryDispatchOrder.reset'),
        				columnWidth:.08,
        				handler:function(){
        					var myform = this.up('form').getForm();
        					myform.reset(); 
        					myform.findField('deliverBeginTime').setValue(Ext.Date.format(order.queryDispatchOrder.getTargetDate(new Date()),'Y-m-d H:i:s'));
        					myform.findField('deliverEndTime').setValue(Ext.Date.format(new Date(),'Y-m-d H:i:s'));
        				}
        		},{
        			xtype: 'container',
        			border : false,
        			columnWidth:.84,
        			html: '&nbsp;'
        		},{
        			text: order.queryDispatchOrder.i18n('pkp.order.queryDispatchOrder.query'),
        			cls:'yellow_button',
        			disabled : !order.queryDispatchOrder.isPermission('querydispatchorderindex/querydispatchorderindexquerybutton'),
        			hidden : !order.queryDispatchOrder.isPermission('querydispatchorderindex/querydispatchorderindexquerybutton'),
        			columnWidth:.08,
        			handler:function(){
        				var resultGrid = Ext.getCmp('T_order-queryDispatchOrderIndex_content').getResultGrid();
        				resultGrid.getPagingToolbar().moveFirst();
        			}
        		}]
        	}
            ]
        });
        me.callParent(arguments);
    },
    listeners : {
    	boxready:function(component, eOpts){
			var form = Ext.getCmp('Foss_order_queryDispatchOrder_QueryForm_Id').getForm();
			if(order.queryDispatchOrder.dept=='Y')
			{
				Ext.suspendLayouts();
				form.findField('districtCode').setVisible(false);
				form.findField('pickupRegionCode').setVisible(false);
				form.findField('salesDepartmentCode').setVisible(false);
				Ext.resumeLayouts(true);
			}
		}
	}
});

Ext.define('Foss.order.queryDispatchOrder.editPanel',{
	extend: 'Ext.form.Panel',
	layout: 'column',
	cls:'autoHeight',
	bodyCls:'autoHeight',
	defaults: {
		margin:'5 10 5 10',
		xtype : 'textfield',
		labelWidth:120
	},
	items: [
			{
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
				fieldLabel:order.queryDispatchOrder.i18n('pkp.order.queryDispatchOrder.orderNo'),
				columnWidth: 1,
				name: 'orderNo',
				readOnly: true
			},
			{
				fieldLabel:order.queryDispatchOrder.i18n('pkp.order.queryDispatchOrder.vehicleTime'),
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
                fieldLabel: order.queryDispatchOrder.i18n('pkp.order.queryDispatchOrder.orderCompleteStatus'),
                queryModel:'local',
				displayField:'valueName',
				valueField:'valueCode',
				editable:false,
				labelWidth:120,
                store: order.queryDispatchOrder.orderStatusStore
			},
			{
				xtype: 'button',
				text: order.queryDispatchOrder.i18n('pkp.order.queryDispatchOrder.confirm'),
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
					Ext.Msg.confirm(order.queryDispatchOrder.i18n('pkp.order.queryDispatchOrder.warning'), order.queryDispatchOrder.i18n('pkp.order.queryDispatchOrder.isEdit'), function(btn,text){
						if(btn=="yes"){
							var form = order.queryDispatchOrder.editPanel;
		    				var dispatchOrder = form.getValues();
							Ext.Ajax.request({
							    url: order.realPath('updateDispatchOrderStatusById.action'),
							    jsonData:{"dispatchOrderConditionVo":{"dispatchOrderVehicleDto":dispatchOrder}},
							    success: function(response){
							        var text = response.responseText;
							        var result = Ext.decode(response.responseText);
							        win.hide();
							        var vehicleGridStore = Ext.getCmp('Foss_vehicleGrid_GridPanel_Id').store;
							        vehicleGridStore.load();
							        Ext.ux.Toast.msg(order.queryDispatchOrder.i18n('pkp.order.queryDispatchOrder.warning'),result.message);
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
				text: order.queryDispatchOrder.i18n('pkp.order.queryDispatchOrder.cancel'),
				columnWidth: .25,
				handler:function(){
					this.up('window').hide();
				}
			}]
});

order.queryDispatchOrder.editPanel = Ext.create('Foss.order.queryDispatchOrder.editPanel');
//编辑任务订单状态window
Ext.define('Foss.order.queryDispatchOrder.editWindow', {
		extend: 'Ext.window.Window',
		width: 420,
		height : 210,
		title: order.queryDispatchOrder.i18n('pkp.order.queryDispatchOrder.edit'),
		layout:'column',
		border: false,
		//将window的关闭事件close 设成 hide
		closeAction : 'hide',
		items: [order.queryDispatchOrder.editPanel]
	});

Ext.define('Foss.order.queryDispatchOrder.vehicleGrid', {
    extend: 'Ext.grid.Panel',
    title: order.queryDispatchOrder.i18n('pkp.order.queryDispatchOrder.dispatchVehicleRecord'),
    frame: true,
    cls:'autoHeight',
	bodyCls:'autoHeight',
	//收缩
	collapsible: true,
	//动画收缩
	animCollapse: true,
	emptyText: order.queryDispatchOrder.i18n('pkp.order.queryDispatchOrder.queryResultNull'),
	selModel:Ext.create('Ext.selection.CheckboxModel',{
		listeners:{
			'beforerenderer':function(record, store) {
		        if(record.data.pdaStatus == '无' || record.data.pdaStatus == '异常' 
		        || record.data.orderStatus == 'NONE_HANDLE'  //未处理
		        || record.data.orderStatus == 'RETURN'  //已退回
		        || record.data.orderStatus == 'AGAIN_PICKUP' //待改接
		        || record.data.orderStatus == 'WAYBILL' //已开单
		        || record.data.orderStatus == 'PICKUP_FAILURE'
		        || record.data.orderType == 'EWAYBILL_ORDER'){   //揽货失败
		            return true;  //不能进行选择
		        }else{   
		           return false;
		         }
			}
		}
	 }),
        columns: [
		{xtype:'actioncolumn',
	  			width:60,
				text: order.queryDispatchOrder.i18n('pkp.order.queryDispatchOrder.edit'),
				disabled : !order.queryDispatchOrder.isPermission('querydispatchorderindex/querydispatchorderindexeditbutton'),
				align: 'center',
				items: [{
					iconCls: 'deppon_icons_edit',
					tooltip: order.queryDispatchOrder.i18n('pkp.order.queryDispatchOrder.edit'),
					handler: function(grid, rowIndex, colIndex) {
						var selection = grid.getStore().getAt(rowIndex);
						order.queryDispatchOrder.editPanel.loadRecord(selection);
						var win  = Ext.create('Foss.order.queryDispatchOrder.editWindow').show();
					},
					getClass:function(v,m,r,rowIndex){
						if(r.get('orderStatus') == 'PICKUP_FAILURE'
							|| r.get('orderStatus') == 'WAYBILL'){
								return 'deppon_icons_edit_hidden';
							}else{
								return 'deppon_icons_edit';
							}
					}
				}]},
		{ header: order.queryDispatchOrder.i18n('pkp.order.queryDispatchOrder.vehicleDept'), dataIndex: 'orderVehicleOrgName', width: 220 },
		{ header: order.queryDispatchOrder.i18n('pkp.order.queryDispatchOrder.orderNo'), dataIndex: 'orderNo', width: 180 },
		{
			text : order.queryDispatchOrder.i18n('pkp.order.orderHandle.weightVolume'),
			dataIndex : 'weightAndVolume',
			width : 100
		},
		{ header: order.queryDispatchOrder.i18n('pkp.order.queryDispatchOrder.orderSendStatus'), dataIndex: 'orderSendStatus', width: 80,
			renderer:function(value){
		    			return FossDataDictionary.rendererSubmitToDisplay(value,'PKP_ORDER_SEND_STATUS');
					}
		},
		{ header: order.queryDispatchOrder.i18n('pkp.order.queryDispatchOrder.orderCompleteStatus'), dataIndex: 'orderStatus', width: 80,
			renderer:function(value){
		    			return FossDataDictionary.rendererSubmitToDisplay(value,'PKP_ORDER_STATUS');
					}
		},
		{ header: order.queryDispatchOrder.i18n('pkp.order.orderHandle.pickupAddress'), dataIndex: 'pickupAddress', width: 300},
		{ header: order.queryDispatchOrder.i18n('pkp.order.orderHandle.pickupRegionName'), dataIndex: 'pickupRegionName', width: 200},
		{ header: order.queryDispatchOrder.i18n('pkp.order.orderHandle.actualRegionName'), dataIndex: 'actualRegionName', width: 200},
		{ header: order.queryDispatchOrder.i18n('pkp.order.queryDispatchOrder.usecarTime'), dataIndex: 'deliverTime', width: 135,xtype : 'datecolumn',format : 'Y-m-d H:i:s' },
		{ header: order.queryDispatchOrder.i18n('pkp.order.orderHandle.useCarTime'),dataIndex : 'usecarTime',width : 135,xtype : 'ellipsiscolumn'},
//		{ header: order.queryDispatchOrder.i18n('pkp.order.queryDispatchOrder.useStatus'), dataIndex: 'pdaStatus', width: 100},
		{ header: order.queryDispatchOrder.i18n('pkp.order.queryDispatchOrder.billTime'), dataIndex: 'billTime', width: 135,xtype : 'datecolumn',format : 'Y-m-d H:i:s'},
		{ header: order.queryDispatchOrder.i18n('pkp.order.queryDispatchOrder.operateType'), dataIndex: 'processStatus', width: 100, 
			renderer : function(value) {
				//14.7.30 gcl AUTO-212
				var process_status="";
				if(value=='RETURN'){
					process_status="退回";
				}else if(value=='AGAIN'){
					process_status="改接";
				}else if(value=='ACCEPT'){
					process_status="接收";
				}else{
					process_status=value;
				}
				return process_status;
				//return FossDataDictionary.rendererSubmitToDisplay(value, 'PKP_PROCESS_STATUS');
			}
		},
		{ header: order.queryDispatchOrder.i18n('pkp.order.queryDispatchOrder.driver'), dataIndex: 'driverName', width: 80},
		{ header: order.queryDispatchOrder.i18n('pkp.order.queryDispatchOrder.driverMobile'), dataIndex: 'driverMobile', width: 100},
		{ header: order.queryDispatchOrder.i18n('pkp.order.queryDispatchOrder.vehicleNo'), dataIndex: 'vehicleNo', width: 80},
		 {
			text : order.queryDispatchOrder.i18n('pkp.order.orderHandle.customerName'),
			dataIndex : 'customerName',
			xtype : 'ellipsiscolumn',
			width : 100
		}, {
			text : order.queryDispatchOrder.i18n('pkp.order.orderHandle.tel'),
			dataIndex : 'tel',
			width : 80
		}, {
			text : order.queryDispatchOrder.i18n('pkp.order.orderHandle.mobile'),
			dataIndex : 'mobile',
			width : 100
		},
		{ header: order.queryDispatchOrder.i18n('pkp.order.queryDispatchOrder.notes'), dataIndex: 'orderNotes', width: 80 },
		{ header: order.queryDispatchOrder.i18n('pkp.order.queryDispatchOrder.reason'), dataIndex: 'operateNotes', width: 120, xtype :'linebreakcolumn', 
			renderer : function(value) {
				var result = FossDataDictionary.rendererSubmitToDisplay(value, 'PKP_COMPANY_REJECT_REASON');
				if(value == result){
					result = FossDataDictionary.rendererSubmitToDisplay(value, 'PKP_CUSTOMER_REJECT_REASON');
				}
				return result;
			}
		},{ header: order.queryDispatchOrder.i18n('pkp.order.queryDispatchOrder.acceptStatus'), dataIndex: 'acceptStatus', width: 120, xtype :'linebreakcolumn', 
			renderer : function(value) {
				var result="";
				if(value == "SYS"){
					result = "自动处理";
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
    		me.store = Ext.create('Foss.order.queryDispatchOrder.vehicleRecordStore');
    		me.tbar = ['->', {
						xtype : 'button',
						text : order.queryDispatchOrder.i18n('pkp.order.queryDispatchOrder.export'),
						handler : function(){
							if(!Ext.fly('downloadAttachFileForm')){
							    var frm = document.createElement('form');
							    frm.id = 'downloadAttachFileForm';
							    frm.style.display = 'none';
							    document.body.appendChild(frm);
							}
							var result = Ext.getCmp('Foss_vehicleGrid_GridPanel_Id').store;
							//若异常信息不为空
							if(result.getCount()!=0){
								var searchParams = Ext.getCmp('T_order-queryDispatchOrderIndex_content').getQueryForm().getValues(),
									params = {
										'dispatchOrderConditionVo.dispatchOrderVehicleDto.deliverBeginTime':searchParams.deliverBeginTime,
										'dispatchOrderConditionVo.dispatchOrderVehicleDto.deliverEndTime':searchParams.deliverEndTime,
										'dispatchOrderConditionVo.dispatchOrderVehicleDto.pickupRegionCode':searchParams.pickupRegionCode,
										'dispatchOrderConditionVo.dispatchOrderVehicleDto.orgCode':order.queryDispatchOrder.fleetCode,
										'dispatchOrderConditionVo.dispatchOrderVehicleDto.orderSendStatus':searchParams.orderSendStatus,
										'dispatchOrderConditionVo.dispatchOrderVehicleDto.orderStatus':searchParams.orderStatus,
										'dispatchOrderConditionVo.dispatchOrderVehicleDto.vehicleNo':searchParams.vehicleNo,
										'dispatchOrderConditionVo.dispatchOrderVehicleDto.orderNo':searchParams.orderNo,
										'dispatchOrderConditionVo.dispatchOrderVehicleDto.productCode':searchParams.productCode
								},
									districtList = Ext.getCmp('T_order-queryDispatchOrderIndex_content').getQueryForm().getForm().findField('districtCode').getValue(),
									salesDepartmentCodes = Ext.getCmp('T_order-queryDispatchOrderIndex_content').getQueryForm().getForm().findField('salesDepartmentCode').getValue();
								for(var i=0;i<districtList.length;i++){
									params['dispatchOrderConditionVo.dispatchOrderVehicleDto.districtList['+i+']'] = districtList[i];
								}
								for(var i=0;i<salesDepartmentCodes.length;i++){
									params['dispatchOrderConditionVo.dispatchOrderVehicleDto.salesDepartmentCodes['+i+']'] = salesDepartmentCodes[i];
								}
								Ext.Ajax.request({
									url:order.realPath('exportVehicleRecord.action'),
									form: Ext.fly('downloadAttachFileForm'),
									method : 'POST',
									params : params,
									isUpload: true
								});
							}else{
								//或者提示不能导出
								Ext.ux.Toast.msg(order.queryDispatchOrder.i18n('pkp.order.queryDispatchOrder.warning'),order.queryDispatchOrder.i18n('pkp.order.queryDispatchOrder.notExport'), 'error', 3000);
							}
						}
			} ];
    		me.dockedItems = [{
    			xtype: 'toolbar',
    			dock: 'bottom',
    			layout:'column',
    			defaults:{
    				margin:'0 0 5 3'
    			},		
    			items: [{
    				xtype: 'button',
    		        text: order.queryDispatchOrder.i18n('pkp.order.queryDispatchOrder.pdaException'),
    		        disabled : !order.queryDispatchOrder.isPermission('querydispatchorderindex/querydispatchorderindexexceptionbutton'),
    		        hidden : !order.queryDispatchOrder.isPermission('querydispatchorderindex/querydispatchorderindexexceptionbutton'),
    		        handler:function(){
    		        	var mygrid = this.up('gridpanel');
    		        	var selectRow = mygrid.getSelectionModel().getSelection();
    		        	if(selectRow.length ==0){
    		        		 Ext.ux.Toast.msg(order.queryDispatchOrder.i18n('pkp.order.queryDispatchOrder.warning'),order.queryDispatchOrder.i18n('pkp.order.queryDispatchOrder.chooseRow'));
    		        	}else{
    		        	Ext.Msg.confirm(order.queryDispatchOrder.i18n('pkp.order.queryDispatchOrder.warning'), order.queryDispatchOrder.i18n('pkp.order.queryDispatchOrder.isModify'), function(btn,text){
    						if(btn=="yes"){
    							var orderIds =  new Array();
    							for(var i=0;i<selectRow.length;i++){
    								orderIds.push({id : selectRow[i].data.id,orderId:selectRow[i].data.orderId});
    							}
    							Ext.Ajax.request({
    							    url: order.realPath('updateIspdaByVehicleId.action'),
    							    jsonData:{'dispatchOrderConditionVo':{'orderIdsAndVehicleIds':orderIds}},
    							    success: function(response){
    							        var result = Ext.decode(response.responseText);
    							        mygrid.store.load();
    							        Ext.ux.Toast.msg(order.queryDispatchOrder.i18n('pkp.order.queryDispatchOrder.warning'),result.message);
    							    }
    						});
    					}
    				});
    		       }
    		       }  	
    		    }]
            }]
		me.bbar = me.getPagingToolbar();
		me.store.sort('billTime','ASC');
        me.callParent(arguments);
    }
});

Ext.onReady(function() {
	Ext.QuickTips.init();
    var queryForm = Ext.create("Foss.order.queryDispatchOrder.QueryForm");
    var vehicleGrid = Ext.create("Foss.order.queryDispatchOrder.vehicleGrid",{
    	id: "Foss_vehicleGrid_GridPanel_Id"
    });
    
	Ext.create('Ext.panel.Panel',{
		id: 'T_order-queryDispatchOrderIndex_content',
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
		renderTo: 'T_order-queryDispatchOrderIndex-body'
	});
});
