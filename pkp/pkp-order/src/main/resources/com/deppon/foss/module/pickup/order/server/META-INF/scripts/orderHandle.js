/**
 * @author 038590-foss-wanghui
 * page ：处理订单
 */
Ext.define('pickupDateModel', {
	extend: 'Ext.data.Model',
	fields: [
		{type: 'string', name: 'code'},
		{type: 'string', name: 'name'}
	]
});

var pickupDateStore = Ext.create('Ext.data.Store', {
	model: 'pickupDateModel',
	data: [ 
		{"code":'Y',"name":"当日"},
		{"code":'N',"name":"非当日"},
	]
});



Ext.apply(order.orderHandle, {
	getFleetStore : function() {
		if(Ext.isEmpty(order.fleet)){
			return null;
		} else {
			var json = {
					fields : [ 'name', 'code' ],
					data : order.fleet
			};
			return Ext.create('Ext.data.Store', json);
		}
	},
//	getVehicleTypeStore : function(){
//		if(Ext.isEmpty(order.vehicleTypeList)){
//			return null;
//		} else {
//			var json = {
//					fields : [ 'vehicleLengthName','vehicleLengthCode' ],
//					data : order.vehicleTypeList
//			};
//			return Ext.create('Ext.data.Store', json);
//		}
//	},
	dateFormat : function(date){
		return Ext.Date.format(new Date(date),'H:i');
	}
});


(function() {
	if(FossUserContext.getCurrentDept().transDepartment == 'Y' 
		|| FossUserContext.getCurrentDept().dispatchTeam == 'Y'
		|| FossUserContext.getCurrentDept().transTeam == 'Y'){
		// 车队
		order.orderHandle.dept = 'N';
	} else {
		// 营业部
		order.orderHandle.dept = 'Y';
	}
	order.orderHandle.orderStatusStore =FossDataDictionary.getDataDictionaryStore('PKP_ORDER_STATUS', null);
	order.orderHandle.orderStatusStore.removeAt(order.orderHandle.orderStatusStore.find('valueCode','DISPATCH_VEHICLE'));
	order.orderHandle.orderStatusStore.removeAt(order.orderHandle.orderStatusStore.find('valueCode','PDA_ACCEPT'));
	order.orderHandle.orderStatusStore.removeAt(order.orderHandle.orderStatusStore.find('valueCode','PICKUPING'));
	order.orderHandle.orderStatusStore.removeAt(order.orderHandle.orderStatusStore.find('valueCode','RETURN'));
	order.orderHandle.orderStatusStore.removeAt(order.orderHandle.orderStatusStore.find('valueCode','PICKUP_FAILURE'));
	order.orderHandle.orderStatusStore.removeAt(order.orderHandle.orderStatusStore.find('valueCode','WAYBILL'));
	order.orderHandle.orderTypeStore = FossDataDictionary.getDataDictionaryStore('PKP_ORDER_TYPE',null,{'valueCode' : null, 'valueName' : '全部'})
	if(order.orderHandle.dept != 'Y'){
		// 获得当前组织对应的车队
		Ext.Ajax.request({
			url : order.realPath('querySuperOrg.action'),
			async : false,
			success : function(response) {
				var json = Ext.decode(response.responseText);
				// 如果找不到对应的顶级车队
				if(Ext.isEmpty(json.fleetCode)){
					// 判定为营业部，权限不放大
					order.orderHandle.dept = 'Y';
				}
				Ext.apply(order, {
					fleetCode : json.fleetCode
				});
			},
			exception : function(response) {
				var result = Ext.decode(response.responseText);
				Ext.ux.Toast.msg(order.orderHandle.i18n('pkp.order.orderHandle.warning'), result.message, 'error', 3000);
			}
		});
		// 获得车队下的车队组
		Ext.Ajax.request({
			url : order.realPath('querySubOrg.action'),
			async : false,
			jsonData : {fleetCode : order.fleetCode},
			success : function(response) {
				var json = Ext.decode(response.responseText);
				Ext.apply(order, {
					fleet : json.dispatchOrderConditionVo.dispatchOrderConditionDto.serviceFleetList
				});
			},
			exception : function(response) {
				var result = Ext.decode(response.responseText);
				Ext.ux.Toast.msg(order.orderHandle.i18n('pkp.order.orderHandle.warning'), result.message, 'error', 3000);
			}
		});
	} else {
		Ext.apply(order, {
			fleetCode : FossUserContext.getCurrentDept().code
		});
	};
	// 获得车型
//	Ext.Ajax.request({
//		url : order.realPath('queryVehcileType.action'),
//		async : false,
//		success : function(response) {
//			var json = Ext.decode(response.responseText);
//			Ext.apply(order, {
//				vehicleTypeList : json.dispatchOrderConditionVo.vehicleTypeList
//			});
//		},
//		exception : function(response) {
//			var result = Ext.decode(response.responseText);
//			Ext.ux.Toast.msg(order.orderHandle.i18n('pkp.order.orderHandle.warning'), result.message, 'error', 3000);
//		}
//	});
})();


// 订单结果Model
Ext.define('Foss.order.orderHandle.orderHandleResultModel', {
	extend : 'Ext.data.Model',
	idgen : 'uuid',// EXT在前台为每个模型额外以UUID方式生成主键
	idProperty : 'extid',// 以上生成的主键用在名叫“extid”的列
	fields : [ {
		// 额外的用于生成的EXT使用的列
		name : ' extid ',
		type : 'string'
	}, {
		name : 'id'
	}, {
		// 订单号
		name : 'orderNo'
	}, {
		// 订单类型
		name : 'orderType'
	}, {
		// 重量
		name : 'weight',
		type : 'float'
	}, {
		// 体积
		name : 'volume',
		type : 'float'
	}, {
		// 件数
		name : 'goodsQty',
		type : 'int'
	}, {
		// 接货省
		name : 'pickupProvince'
	}, {
		// 接货市
		name : 'pickupCity'
	}, {
		// 接货区
		name : 'pickupCounty'
	}, {
		// 其他地址信息
		name : 'pickupElseAddress'           
	}, {
		// 接货区
		name : 'deliveryCustomerAddressNote'
	}, {
		// 特殊地质类型
		name : 'addressType'             
	},{
		// 特殊地质类型Code
		name : 'addressTypeCode'             
	},{
		// 特殊车牌
		name : 'specialVehicleNo'             
	},{
		// 用车时间
		name : 'earliestPickupTime'
	}, {
		// 用车时间
		name : 'latestPickupTime'
	}, {
		// 用车时间
		name : 'orderVehicleTime'
	}, {
		// 预处理建议id
		name : 'preprocess'
	}, {
		// 调度状态
		name : 'orderStatus'
	}, {
		// 客户姓名
		name : 'customerName'
	}, {
		// 电话
		name : 'tel'
	}, {
		// 手机
		name : 'mobile'
	}, {
		// 车型
		name : 'vehicleType'
	}, {
		// 营业部门
		name : 'salesDepartmentName'
	}, {
		// 营业部门编码
		name : 'salesDepartmentCode'
	}, {
		// 收货地址
		name : 'consigneeAddress'
	}, {
		// 建议车辆
		name : 'vehicleNoSuggested'
	}, {
		// 建议司机
		name : 'driverNameSuggested'
	}, {
		// 建议司机code
		name : 'driverCodeSuggested'
	}, {
		// 预处理建议小区code
		name : 'smallZoneCodeSuggested'
	},{
		//预处理建议小区name 
		name : 'smallZoneNameSuggested'
	}, {
		// AUTO-195
		name : 'delGPS'
	}, {
		name : 'assignedRecord'
	},{
		// 是否PDA发送
		name : 'isPda'
	}, {
		// 是否短信发送
		name : 'isSms'
	}, {
		// 是否发送客户
		name : 'isCustomer'
	}, {
		// 设备号
		name : 'deviceNo'
	}, {
		// 手机
		name : 'driverMobile'
	}, {
		// 预处理建议id -- 接货小区code
		name : 'preprocessId'
	}, {
		name : 'vehicleNo'
	}, {
		name : 'driverCode'
	}, {
		name : 'pickupRegionName'
	}, {
		// 订单备注
		name : 'orderNotes'
	}, {
		name : 'goodsPackage'
	}, {
		name : 'operateNotes'
	}, {
		name : 'depTelephone'
	},{
		//运输性质
		name : 'productCode'
	},{
		//订单来源
		name : 'orderSource'
	},{
		//约车车队是否约车部门
		name : 'selfVehicle'
	}]
});

// 订单结果Store
Ext.define(
				'Foss.order.orderHandle.orderHandleResultStore',
				{
					extend : 'Ext.data.Store',
					autoLoad : false,
					model : 'Foss.order.orderHandle.orderHandleResultModel',
					pageSize : 10,
					proxy : {
						type : 'ajax',
						actionMethods : 'POST',
						url : order.realPath('queryOrder.action'),
						reader : {
							type : 'json',
							root : 'dispatchOrderVo.orderList',
							totalProperty : 'totalCount'
						}
					},
					listeners : {
						beforeload : function(store, operation, eOpts) {
							var queryForm = order.queryForm.getForm();
							if (queryForm != null) {
								var queryParams = queryForm.getValues(),
									districtList = queryForm.findField('districtList').getValue(),
									smallZoneList = queryForm.findField('smallZoneList').getValue(),
									businessAreaList = queryForm.findField('businessAreaList').getValue(),
									salesDepartmentCodes = queryForm.findField('salesDepartmentCode').getValue();
								if(order.orderHandle.dept != 'Y'){
									var params = {
											'dispatchOrderConditionVo.dispatchOrderConditionDto.orderNo' : Ext.util.Format.trim(queryParams.orderNo),
											'dispatchOrderConditionVo.dispatchOrderConditionDto.vehicleType' : queryParams.vehicleType,
											'dispatchOrderConditionVo.dispatchOrderConditionDto.orderStatus' : queryParams.orderStatus,
											'dispatchOrderConditionVo.dispatchOrderConditionDto.orderType' : queryParams.orderType,
											'dispatchOrderConditionVo.dispatchOrderConditionDto.fleetCode' : order.fleetCode,
											//新增查询参数
											'dispatchOrderConditionVo.dispatchOrderConditionDto.productCode' : queryParams.productCode,
											'dispatchOrderConditionVo.dispatchOrderConditionDto.isToday' : queryParams.isToday
									};
								} else {
									var params = {
											'dispatchOrderConditionVo.dispatchOrderConditionDto.orderNo' : Ext.util.Format.trim(queryParams.orderNo),
											'dispatchOrderConditionVo.dispatchOrderConditionDto.vehicleType' : queryParams.vehicleType,
											'dispatchOrderConditionVo.dispatchOrderConditionDto.orderStatus' : queryParams.orderStatus,
											'dispatchOrderConditionVo.dispatchOrderConditionDto.orderType' : queryParams.orderType,
											//新增查询参数
											'dispatchOrderConditionVo.dispatchOrderConditionDto.productCode' : queryParams.productCode,
											'dispatchOrderConditionVo.dispatchOrderConditionDto.isToday' : queryParams.isToday
									};
								}
								for(var i=0;i<districtList.length;i++){
									params['dispatchOrderConditionVo.dispatchOrderConditionDto.districtList['+i+']'] = districtList[i];
								}
								if(!Ext.isEmpty(smallZoneList)){
									for(var i=0;i<smallZoneList.length;i++){
										params['dispatchOrderConditionVo.dispatchOrderConditionDto.smallZoneList['+i+']'] = smallZoneList[i];
									}
								}
								for(var i=0;i<businessAreaList.length;i++){
									params['dispatchOrderConditionVo.dispatchOrderConditionDto.businessAreaList['+i+']'] = businessAreaList[i];
								}
								for(var i=0;i<salesDepartmentCodes.length;i++){
									params['dispatchOrderConditionVo.dispatchOrderConditionDto.salesDepartmentCodes['+i+']'] = salesDepartmentCodes[i];
								}
								Ext.apply(operation,{
									params : params
								});
							}
						}
					}
				});

// 订单查询Form
Ext.define('Foss.order.orderHandle.orderHandleQueryForm', {
	id : 'Foss_order_orderHandle_orderHandleQueryForm_Id',
	extend : 'Ext.form.Panel',
	cls:'autoHeight',
	bodyCls:'autoHeight',	
	collapsible: true,
	animCollapse: true,
	title: order.orderHandle.i18n('pkp.order.orderHandle.queryCondition'),
	frame:true,
	defaults : {
		width : 300,
		labelWidth : 90
	},
	layout:'column',
	items : [ {
		fieldLabel : order.orderHandle.i18n('pkp.order.orderHandle.orderNo'),
		name : 'orderNo',
		xtype : 'textfield',
		columnWidth:.25
	}, {
		fieldLabel : order.orderHandle.i18n('pkp.order.orderHandle.district'),
		name : 'districtList',
		xtype : 'commonmotorcadedistrictselector',
		motorcadeCode : order.fleetCode,
		columnWidth:.25
	}, {
		fieldLabel : order.orderHandle.i18n('pkp.order.orderHandle.smallZone'),
		name : 'smallZoneList',
		xtype : 'commonmultismallzoneselector',
		parentOrgCodes : order.fleetCode,
		regionType : 'PK',
		valueField : 'virtualCode',
		columnWidth:.25
	}, {
		fieldLabel : order.orderHandle.i18n('pkp.order.orderHandle.businessZone'),
		name : 'businessAreaList',
		xtype : 'commonmotorcadesalesareaselector',
		motorcadeCode : order.fleetCode,
		columnWidth:.25
	}, {
		fieldLabel : order.orderHandle.i18n('pkp.order.orderHandle.vehicleType'),
		name : 'vehicleType',
		xtype : 'commonvehicletypeselector',
		displayField : 'vehicleLengthName',
		valueField : 'vehicleLengthName',
		columnWidth:.25
	}, {
		fieldLabel : order.orderHandle.i18n('pkp.order.orderHandle.orderStatus'),
		name : 'orderStatus',
		xtype : 'combobox',
		store : order.orderHandle.orderStatusStore,
		displayField : 'valueName',
		valueField : 'valueCode',
		queryMode:'local',
		triggerAction:'all',
		columnWidth:.25
	}, {
		fieldLabel : order.orderHandle.i18n('pkp.order.orderHandle.queryOrderType'),
		name : 'orderType',
		xtype : 'combobox',
		store : order.orderHandle.orderTypeStore,
		displayField : 'valueName',
		valueField : 'valueCode',
		queryMode:'local',
		triggerAction:'all',
		columnWidth:.25
	}, {
		fieldLabel : order.orderHandle.i18n('pkp.order.orderHandle.salesDepartment'),
		xtype : 'commonmotorcadesaledeptselector',
		motorcadeCode : order.fleetCode,
		name : 'salesDepartmentCode',
		columnWidth:.25
	}, {
		//新增
		fieldLabel : order.orderHandle.i18n('pkp.order.expressWorkerStatus.transportType'),
		 xtype:'combobox',
		displayField:'name',
		valueField:'code',
		queryMode:'local',
		triggerAction:'all',
		editable:false,
		name: 'productCode',
		store : Ext.create('Foss.pkp.ProductStore'),
		columnWidth:.25
	}, {
		fieldLabel : '是否当日',
		name : 'isToday',
		xtype : 'combobox',
		store : pickupDateStore,
		displayField : 'name',
		valueField : 'code',
		queryMode:'local',
		triggerAction:'all',
		value : 'Y',
		columnWidth:.25
	}, {
		border: 1,
		xtype:'container',
		columnWidth:1,
		defaultType:'button',
		layout:'column',
		items:[{
			xtype : 'button',
			columnWidth:.08,
			text : order.orderHandle.i18n('pkp.order.orderHandle.reset'),
			handler : function() {
				var me = this;
				var form = me.up('form');
				form.getForm().reset();
				if(order.orderHandle.dept=='Y')
				{
					var currentDept = Ext.create('Foss.baseinfo.commonSelector.MotorcadeSaleDeptModel');
					currentDept.set('salesdeptCode',FossUserContext.getCurrentDept().code);
					currentDept.set('salesdeptName',FossUserContext.getCurrentDept().name);
					currentDept.set('active',FossUserContext.getCurrentDept().active);
					form.getForm().findField('salesDepartmentCode').setValue(currentDept);
				}
			}
		}, {
			xtype: 'container',
			border : false,
			columnWidth:.84,
			html: '&nbsp;'
		}, {
			xtype : 'button',
			columnWidth:.08,
			cls : 'yellow_button',
			text : order.orderHandle.i18n('pkp.order.orderHandle.query'),
			disabled : !order.orderHandle.isPermission('orderhandleindex/orderhandleindexquerybutton'),
			hidden : !order.orderHandle.isPermission('orderhandleindex/orderhandleindexquerybutton'),
			handler : function() {
				if(Ext.Date.format(new Date(),'H:i:s') > "08:05:00") {//9点后才允许查询订单
					if (order.queryForm.getForm().isValid()) {
						order.pagingBar.moveFirst();
					} else {
						Ext.ux.Toast.msg(order.orderHandle.i18n('pkp.order.orderHandle.warning'), order.orderHandle.i18n('pkp.order.orderHandle.inputCondition'), 'error', 3000);
					}
				}else {
					Ext.ux.Toast.msg(order.orderHandle.i18n('pkp.order.orderHandle.warning'), '8:05之前请点击刷新按钮查询!', 'error', 3000);
				}
			}
		}]
	}],
	listeners : {
		boxready:function(component, eOpts){
			var form = Ext.getCmp('Foss_order_orderHandle_orderHandleQueryForm_Id').getForm();
			if(order.orderHandle.dept=='Y')
			{
				form.findField('districtList').setVisible(false);
				form.findField('smallZoneList').setVisible(false);
				form.findField('businessAreaList').setVisible(false);
				form.findField('salesDepartmentCode').setVisible(false);
				var currentDept = Ext.create('Foss.baseinfo.commonSelector.MotorcadeSaleDeptModel');
				currentDept.set('salesdeptCode',FossUserContext.getCurrentDept().code);
				currentDept.set('salesdeptName',FossUserContext.getCurrentDept().name);
				currentDept.set('active',FossUserContext.getCurrentDept().active);
				form.findField('salesDepartmentCode').setValue(currentDept);
			}
		}
	}
});


// 计算分钟
function calculateMin(ms, type) {
	var now = new Date().getTime();
	var difference = (now - ms) / (1000 * 60);
	// 离用车时间还有15分钟
	if (type === 1) {
		// 离用车时间还有15分钟
		if (difference > -15) {
			return -1;
		}

	} else if (type === 2) {
		// 结束用车时间还有15分钟
		if (difference > -15) {
			return 0;
		}
	} else {
		// 处理超时
		if (difference > 15) {
			return 1;
		}
	}
}

// 处理订单Model
Ext.define('Foss.order.orderHandle.orderHandleModel', {
	extend : 'Ext.data.Model',
	fields : [ {
		name : 'rejectReason' // 拒绝原因
	}, {
		name : 'desc' // 其他原因描述
	}, {
		name : 'id' // id
	}, {
		name : 'originOrderStatus' // 原始订单状态
	} ]
});

Ext.define('Foss.order.orderHandle.RejectOrderForm', {
	extend : 'Ext.form.Panel',
	layout : 'column',
	record : null,
	items : [ {
		xtype : 'radiogroup',
		fieldLabel : '',
		lableSeparator : '',
		columns : [ 100, 100 ],
		vertical : true,
		items : [ {
			boxLabel : order.orderHandle.i18n('pkp.order.orderHandle.unknowAddress'),
			name : 'rejectReason',
			inputValue : order.orderHandle.i18n('pkp.order.orderHandle.unknowAddress')
		}, {
			boxLabel : order.orderHandle.i18n('pkp.order.orderHandle.else'),
			name : 'rejectReason',
			inputValue : order.orderHandle.i18n('pkp.order.orderHandle.else'),
			checked : true
		} ],
		listeners : {
			change : function(field, newValue, oldValue, eOpts) {
				var t = field.nextSibling();
				if (newValue.rejectReason == order.orderHandle.i18n('pkp.order.orderHandle.else')) {
					t.setVisible(true);
				} else {
					t.setVisible(false);
				}
			}
		}
	}, {
		fieldLabel : '',
		lableSeparator : '',
		xtype : 'textfield',
		name : 'desc'
	} ],
	dockedItems : [ {
		xtype : 'toolbar',
		dock : 'bottom',
		buttonAlign : 'center',
		items : [
				{
					xtype : 'button',
					text : order.orderHandle.i18n('pkp.order.orderHandle.confirm'),//确定
					margin : '0 0 0 250',
					handler : function() {
						var me = this;
						if(me.up('form').down('radiogroup').getChecked()[0].getGroupValue()== order.orderHandle.i18n('pkp.order.orderHandle.else') && Ext.isEmpty(me.up('form').down('textfield').getValue())){
							Ext.ux.Toast.msg(order.orderHandle.i18n('pkp.order.orderHandle.warning'), order.orderHandle.i18n('pkp.order.orderHandle.requireInput'), 'error',
									3000);
							return ;
						}
						me.up('form').getForm().updateRecord(
								me.up('form').record);
						var params = {
							'orderHandleVo' : {
								'orderHandleDto' : me.up('form').record.data
							}
						};
						Ext.Ajax.request({
							url : order.realPath('rejectOrder.action'),
							async : true,
							jsonData : params,
							success : function(response) {
								var json = Ext.decode(response.responseText);
								if (json.success) {
									Ext.MessageBox.alert(order.orderHandle.i18n('pkp.order.orderHandle.warning'), json.message,
											function() {
												me.up('window').close();
												order.resultGrid.store.load();
											});
								}
							},
							exception : function(response) {
								var result = Ext.decode(response.responseText);
								Ext.ux.Toast.msg(order.orderHandle.i18n('pkp.order.orderHandle.warning'), result.message, 'error',
										3000);
							}
						});
					}
				}, {
					xtype : 'button',
					text : order.orderHandle.i18n('pkp.order.orderHandle.return'),//返回
					margin : '0 0 0 20',
					handler : function() {
						var me = this;
						me.up('window').close();
					}
				} ]
	} ]
});

// 退回原因
Ext.define('Foss.order.orderHandle.RejectOrderWindow', {
	extend : 'Ext.window.Window',
	title : order.orderHandle.i18n('pkp.order.orderHandle.reason'), // 退回原因
	modal : true,
	width : 400,
	height : 150,
	layout : 'column',
	orderId : null,
	orderStatus : null,
	orderNo : null,
	orderType : null,
	initComponent : function() {
		var me = this;
		var reasonModel = Ext
				.create('Foss.order.orderHandle.orderHandleModel');
		reasonModel.set('id', me.orderId);
		reasonModel.set('orderNo',me.orderNo);
		reasonModel.set('originOrderStatus', me.orderStatus);
		reasonModel.set('orderType',me.orderType);
		var rejectOrderForm = Ext.create(
				'Foss.order.orderHandle.RejectOrderForm', {
					record : reasonModel
				});
		rejectOrderForm.loadRecord(reasonModel);
		me.items = rejectOrderForm;
		me.callParent();
	}
});

// 预处理建议表单Form
Ext.define('Foss.order.orderHandle.PreprocessForm', {
	extend : 'Ext.form.Panel',
	defaults : {
		width : 240
	},
	items : [ {
		fieldLabel : order.orderHandle.i18n('pkp.order.orderHandle.smallZone'),//定人定区
		fieldLabel : '实际接货小区',
		name : 'smallZoneNameSuggested',
		xtype : 'commonsmallzoneselector',
//		parentOrgCode : order.fleetCode
		regionType : 'PK',
		management : order.fleetCode,
		listeners : {
			'change' : function(field, event, eOpts) {
				var form = field.up('form').getForm(),
					vehicleNo = form.findField('vehicleNoSuggested');
				if (field.value != '' && field.value != null) {
					vehicleNo.setReadOnly(false);
					vehicleNo.getStore().un('beforeload');
					vehicleNo.getStore().on('beforeload', function(store,operation,eOpts) {
						var searchParams = operation.params;
						if (Ext.isEmpty(searchParams)) {
							searchParams = {};
							Ext.apply(operation, {
								params : searchParams
							});
						}
						delete searchParams['truckVo.truck.loopOrgCode'];
						searchParams['truckVo.truck.reginName'] = field.rawValue;
					});
					vehicleNo.getStore().load();
					vehicleNo.expand();
				}
				else
				{
					vehicleNo.setValue('');
				}
			}
		}
	},{
		fieldLabel : order.orderHandle.i18n('pkp.order.orderHandle.vehicleGroup'),//车辆组别
		name : 'vehicleGroup',
		xtype : 'commonmotorcadeselector',
		loopOrgCode : order.fleetCode,
		fleetTypes : 'FLEET_TYPE__SHUTTLE_GOODS,TYPE__LONG_DISTANCE',
		listeners : {
			'change' : function(field, event, eOpts) {
				var form = field.up('form').getForm(),
					vehicleNo = form.findField('vehicleNoSuggested');
				if(order.orderHandle.dept == 'N'){
					if (field.value != '' && field.value != null) {
						vehicleNo.setDisabled(false);
						vehicleNo.getStore().un('beforeload');
						vehicleNo.getStore().on('beforeload', function(store, operation,eOpts) {
							var searchParams = operation.params;
							if (Ext.isEmpty(searchParams)) {
								searchParams = {};
								Ext.apply(operation, {
									params : searchParams
								});
							}
							searchParams['truckVo.truck.loopOrgCode'] = field.value;
						});
						vehicleNo.getStore().load();
						vehicleNo.expand();
					}
					else
					{
						vehicleNo.setValue('');
						vehicleNo.setDisabled(true);
					}
				}
			}
		}
	}, {
		fieldLabel : order.orderHandle.i18n('pkp.order.orderHandle.vehicleNo'),//车牌号
		name : 'vehicleNoSuggested',
		xtype : 'commontruckselector',
		queryAllFlag : true,
		//loopOrgCode : order.fleetCode,
		listeners : {
/*			'change' : function(field, event, eOpts) {
				var vehicleNo = field.up('form').getForm().findField('vehicleNoSuggested');
				vehicleNo.store.removeListener('beforeload');
				vehicleNo.store.addListener('beforeload', function(store, operation, eOpts) {
					var searchParams = operation.params;
					if (Ext.isEmpty(searchParams)) {
						searchParams = {};
						Ext.apply(operation, {
							params : searchParams
						});
					}
					delete searchParams['truckVo.truck.reginName'];
					searchParams['truckVo.truck.loopOrgCode'] = order.fleetCode;
				});
				vehicleNo.getStore().load();
				vehicleNo.expand();
			},*/
			'select' : function(combo, record, eOpts){
				var model = record[0];
				var params = {
						'vehicleVo' : {
							'ownTruckConditionDto' : {
								'vehicleNo' : model.get('vehicleNo')
							}
						}
				};
				Ext.Ajax.request({
					url : order.realPath('querySchedule.action'),
					async : true,
					jsonData : params,
					success : function(response) {
						var json = Ext.decode(response.responseText);
						if (json.success) {
							var ownTruck = json.vehicleVo.ownTruckDto;
							if(!Ext.isEmpty(ownTruck)){
								var ownDriver = Ext.create('Foss.baseinfo.commonSelector.OwnDriverModel');
								ownDriver.set('empCode',ownTruck.driverCode);
								ownDriver.set('empName',ownTruck.driverName);
								ownDriver.set('empPhone',ownTruck.driverMobile);
								combo.up('form').query('combobox[name="driverMobile"]')[0].setValue(ownDriver);
								combo.up('form').query('combobox[name="driverNameSuggested"]')[0].setValue(ownDriver);
							}
						}
					},
					exception : function(response) {
						var result = Ext.decode(response.responseText);
						Ext.ux.Toast.msg(order.orderHandle.i18n('pkp.order.orderHandle.warning'), result.message, 'error',
								3000);
					}
				});
			}
		}
	}, {
		fieldLabel : order.orderHandle.i18n('pkp.order.orderHandle.driverName'),//司机姓名
		name : 'driverNameSuggested',
		xtype : 'commonowndriverselector',
		displayField : 'empName',// 显示名称
		valueField : 'empName',
		parentOrgCode : order.fleetCode,
		listeners : {
			select : function(combo, record, eOpts){
				var model = record[0];
				this.up('form').query('combobox[name="driverMobile"]')[0].setValue(model);
			}
		}
	}, {
		fieldLabel : order.orderHandle.i18n('pkp.order.orderHandle.mobile'),
		name : 'driverMobile',
		myQueryParam:'empPhone',
		valueField : 'empPhone',
		displayField : 'empPhone',
		xtype : 'commonowndriverselector',
		parentOrgCode : order.fleetCode,
		listeners : {
			select : function(combo, record, eOpts){
				var model = record[0]; 
				this.up('form').query('combobox[name="driverNameSuggested"]')[0].setValue(model);
			}
		}
	} ],
	fbar : [ {
		text : order.orderHandle.i18n('pkp.order.orderHandle.save'),
		handler : function(){
			var me = this;
			var smallZoneCode = me.up('form').query('combobox[name="smallZoneNameSuggested"]')[0].getValue();
			var smallZoneName = me.up('form').query('combobox[name="smallZoneNameSuggested"]')[0].getRawValue();
			if(smallZoneName == smallName){
				smallZoneCode = smallCode;
			}
			var vehicleNo = me.up('form').query('combobox[name="vehicleNoSuggested"]')[0].getValue();
			var comboDriver = me.up('form').query('combobox[name="driverNameSuggested"]');
			var driverModel = Ext.isEmpty(comboDriver) ? null : comboDriver[0].valueModels[0];
			var record = me.up('form').getForm().getRecord();
			record.set('vehicleNoSuggested',vehicleNo);
			if(!Ext.isEmpty(driverModel)){
				record.set('driverNameSuggested',driverModel.get('empName'));
				record.set('driverCodeSuggested',driverModel.get('empCode'));
			}
			//me.up('form').getForm().updateRecord(record);
			if(Ext.isEmpty(smallZoneCode)){
				Ext.ux.Toast.msg(order.orderHandle.i18n('pkp.order.orderHandle.warning'), order.orderHandle.i18n('pkp.order.orderHandle.smallZoneNotNull'), 
						'error', 4000);
				return false;
			}else{
				//14.7.23 gcl AUTO-195
				//if(record.get('smallZoneNameSuggested')!=null&&!Ext.isEmpty(record.get('smallZoneNameSuggested'))){
				var successFun = function(json){
					record.set('vehicleNoSuggested','');
				};
				var failFun = function(json){};
				if(!Ext.isEmpty(record.get('smallZoneNameSuggested')) && smallZoneCode!=smallZoneName&&smallZoneName!=record.get('smallZoneNameSuggested')&&smallZoneCode!=record.get('smallZoneNameSuggested')){
					Ext.Msg.confirm(order.orderHandle.i18n('pkp.order.orderHandle.syswarning'), order.orderHandle.i18n('pkp.order.orderHandle.changeSmallZone'), function(btn,text){
						if(btn=="yes"){
							record.set('delGPS',"YES");
							//14.7.24 gcl AUTO-200
						}
						//14.7.24 gcl AUTO-200
						if(record.get('vehicleNoSuggested')!=null&&!Ext.isEmpty(record.get('vehicleNoSuggested'))){
							order.requestJsonAjax(record.get('vehicleNoSuggested'),successFun,failFun);
						}
					});
				}else{
					//14.7.24 gcl AUTO-200
					if(record.get('vehicleNoSuggested')!=null&&!Ext.isEmpty(record.get('vehicleNoSuggested'))){
						order.requestJsonAjax(record.get('vehicleNoSuggested'),successFun,failFun);
					}
				}
				record.set('smallZoneNameSuggested',smallZoneName);
				record.set('smallZoneCodeSuggested',smallZoneCode);//14.7.29 gcl AUTO-212实际接货小区 
				record.set('pickupRegionName',smallZoneName);
				record.set('driverMobile',me.up('form').query('combobox[name="driverMobile"]')[0].getValue());
				//}
				me.up('window').close();
			}
		}
	}, {
		text : order.orderHandle.i18n('pkp.order.orderHandle.cancel'),
		handler : function(){
			var me = this;
			me.up('window').close();
		}
	} ]
});

Ext.define('Foss.order.orderHandle.SmsWindow',{
	extend : 'Ext.window.Window',
	title : order.orderHandle.i18n('pkp.order.orderHandle.sms'),
	modal : true,
	closeAction : 'hide',
	width : 300,
	height : 230,
	layout: 'column',
	defaults: {
		margin:'5 10 5 10',
		anchor: '100%',
		labelWidth:80
	},
	items : [{
		xtype : 'textareafield',
		name : 'sms',
		fieldLabel : order.orderHandle.i18n('pkp.order.orderHandle.sms'), // 短信
		labelWidth : 50,
		grow : true,
		margin : '10 5 10 10',
		columnWidth : .99
	},{
		xtype : 'container',
		border : false,
		columnWidth : .99,
		layout:'column',
		items : [{
			border : false,
			columnWidth:.78,
			html: '&nbsp;'
		}, {
			xtype : 'button',
			columnWidth : .2,
			text : order.orderHandle.i18n('pkp.order.orderHandle.copy'), // 复制
			plugins: Ext.create('Deppon.ux.ZeroClipboardPlugin', {
				targetFun: function(scope) {
					return scope.up('window').query('textarea[name="sms"]')[0].getValue();
				}
			})
		}]
	}],
	initComponent : function(){
		var me = this;
//		me.items = [{
//			xtype : 'textareafield',
//			name : 'sms',
//			fieldLabel : '短信',
//			labelWidth : 50,
//			grow : true,
//			margin : '10 5 10 10',
//			columnWidth : .99
//		},{
//			xtype : 'container',
//			columnWidth : .99,
//			items : [{
//				border : false,
//				columnWidth:.78,
//				html: '&nbsp;'
//			}, {
//				xtype : 'button',
//				columnWidth : .2,
//				text : '复制',
//				plugins: Ext.create('Deppon.ux.ZeroClipboardPlugin', {
//					targetFun: function(scope) {
//						return scope.up('window').query('textarea[name="sms"]')[0].getValue();
//					}
//				})
//			}]
//		}];
//		me.bbar = ['->',{
//			xtype : 'button',
//			text : '复制',
//			plugins: Ext.create('Deppon.ux.ZeroClipboardPlugin', {
//				targetFun: function(scope) {
//					return scope.up('window').query('textarea[name="sms"]')[0].getValue();
//				}
//			})
//		}];
		me.callParent();
	}
});

var smallCode = null;
var smallName = null;
// 预处理建议window
Ext.define('Foss.order.orderHandle.PreprocessWindow', {
	extend : 'Ext.window.Window',
	title : order.orderHandle.i18n('pkp.order.orderHandle.preprocessModified'),
	modal : true,
	closeAction : 'hide',
	width : 300,
	height : 250,
	preprocessForm : null,
	getPreprocessForm : function(){
		if(this.preprocessForm == null){
			this.preprocessForm = Ext.create('Foss.order.orderHandle.PreprocessForm');
		}
		return this.preprocessForm;
	},
	bindData : function(record, cellIndex, rowIndex){
		order.orderHandle.preprocessForm = this.getPreprocessForm().getForm();
		this.getPreprocessForm().getForm().loadRecord(record);
	},
	initComponent :function(){
		var me = this;
		me.items = [ me.getPreprocessForm() ];
		me.callParent();
	},
	listeners : {
		beforeshow:function(component, eOpts){
			if(!Ext.isEmpty(component.down('form').getRecord().get('vehicleNoSuggested'))
					|| Ext.isEmpty(component.down('form').getRecord().get('driverNameSuggested'))){
				var params = {
						'vehicleVo' : {
							'ownTruckConditionDto' : {
								'vehicleNo' : component.down('form').getRecord().get('vehicleNoSuggested')
							}
						}
				};
				Ext.Ajax.request({
					url : order.realPath('querySchedule.action'),
					async : true,
					jsonData : params,
					success : function(response) {
						var json = Ext.decode(response.responseText);
						if (json.success) {
							var ownTruck = json.vehicleVo.ownTruckDto;
							if(!Ext.isEmpty(ownTruck)){
								var ownDriver = Ext.create('Foss.baseinfo.commonSelector.OwnDriverModel');
								ownDriver.set('empCode',ownTruck.driverCode);
								ownDriver.set('empName',ownTruck.driverName);
								ownDriver.set('empPhone',ownTruck.driverMobile);
								component.down('form').query('combobox[name="driverMobile"]')[0].setValue(ownDriver);
								component.down('form').query('combobox[name="driverNameSuggested"]')[0].setValue(ownDriver);
							}
						}
					},
					exception : function(response) {
						var result = Ext.decode(response.responseText);
						Ext.ux.Toast.msg(order.orderHandle.i18n('pkp.order.orderHandle.warning'), result.message, 'error',
								3000);
					}
				});
			};
			if(!Ext.isEmpty(component.down('form').getRecord().get('smallZoneCodeSuggested'))
					|| !Ext.isEmpty(component.down('form').getRecord().get('smallZoneNameSuggested'))) //14.7.18 gcl AUTO-179
			{
				smallCode = component.down('form').getRecord().get('smallZoneCodeSuggested');
				smallName = component.down('form').getRecord().get('smallZoneNameSuggested');
				component.down('form').query('combobox[name="smallZoneNameSuggested"]')[0].setCombValue(component.down('form').getRecord().get('smallZoneNameSuggested'),component.down('form').getRecord().get('smallZoneCodeSuggested'));
			}
		}
	}
});

Ext.define('Foss.order.orderHandle.GisMap',{
	extend : 'Ext.window.Window',
	title : order.orderHandle.i18n('pkp.order.orderHandle.electronicMap'),
	modal : true,
	closeAction : 'hide',
	height : 500,
	width : 600,
	items : [ {
		xtype : 'panel',
		height : 450,
		layout : 'fit'
	}]
});


var markWin = null;
// 订单结果列表
Ext.define('Foss.order.orderHandle.orderHandleResultGrid',{
					extend : 'Ext.grid.Panel',
					id : 'Foss_order_orderHandle_orderHandleResultGrid_Id',
					frame : true,
					collapsible : true,
					animCollapse : true,
					title : order.orderHandle.i18n('pkp.order.orderHandle.orderTask'),
					height:850,
					cls : 'autoHeight',
					bodyCls : 'autoHeight',
					autoScroll : true,
					store : null,
					selModel : null,
					emptyText: order.orderHandle.i18n('pkp.order.orderHandle.queryResultNull'),
					smsWindow: null,
					getSmsWindow: function(){
						if(this.smsWindow==null){
							this.smsWindow = Ext.create('Foss.order.orderHandle.SmsWindow');
						}
						return this.smsWindow;
					},
					columns : [
							{
								text : '',
								sortable : true,
								dataIndex : 'timeStatus',
								width : 40,
								renderer : function(value, metaData, record) {
									var result1 = calculateMin(record
											.get('earliestPickupTime'), 1), result2 = calculateMin(
											record.get('latestPickupTime'), 2), content = '';
									if(record.get('orderType') == FossDataDictionary.rendererDisplayToSubmit('接货订单','PKP_ORDER_TYPE')){
										if (result1 === -1)
											content += '<div class="foss_icons_pkp_warning" width="20" height="18"></div>';
									}
									if (result2 === 0)
										content += '<div class="foss_icons_pkp_serWarning" width="20" height="18"></div>';
									return content;
								}
							},
							{
								xtype : 'actioncolumn',
								text : order.orderHandle.i18n('pkp.order.orderHandle.operate'),
								width : 75,
								aglin : 'center',
								items : [
										{
											iconCls : 'deppon_icons_return',
											tooltip : order.orderHandle.i18n('pkp.order.orderHandle.orderReject'),
											width : 10,
											disabled : !order.orderHandle.isPermission('orderhandleindex/orderhandleindexreturnbutton'),
											handler : function(grid, rowIndex,
													colIndex) {
												var record = grid.getStore().getAt(rowIndex);
												// 订单状态非未处理或待改接，则不允许拒绝订单
												if (record.get('orderStatus') !== 'NONE_HANDLE'
														&& record.get('orderStatus') !== 'AGAIN_PICKUP') {
													Ext.ux.Toast.msg(order.orderHandle.i18n('pkp.order.orderHandle.warning'),
															order.orderHandle.i18n('pkp.order.orderHandle.notAllowReject'),
															'error', 3000);
												} else {
													Ext.create('Foss.order.orderHandle.RejectOrderWindow',
															{
																orderId : record.get('id'),
																orderNo : record.get('orderNo'),
																orderType : record.get('orderType'),
																orderStatus : record.get('orderStatus')
															}).show();
												}
											}
										},
										{
											iconCls : 'foss_icons_pkp_viewOrderlocation',
											tooltip : order.orderHandle.i18n('pkp.order.orderHandle.orderPosition'),
											width : 10,
											handler : function(grid, rowIndex, colIndex){
												var record = grid.getStore().getAt(rowIndex);
												var map = Ext.create('Foss.order.orderHandle.GisMap');
												var address = "";
												if(!Ext.isEmpty(record.get('pickupProvince'))){
													address += record.get('pickupProvince')
												}
												if(!Ext.isEmpty(record.get('pickupCity'))){
													address += record.get('pickupCity')
												}
												if(!Ext.isEmpty(record.get('pickupCounty'))){
													address += record.get('pickupCounty')
												}
												if(!Ext.isEmpty(record.get('pickupElseAddress'))){
													address += record.get('pickupElseAddress')
												}
												map.show();
												var aa = new DPMap.MyMap('VIEW', map.down('panel').getId()+"-body",{center : "上海市", zoom : "STREET" },function(m) {
													var a =  DMap.PointFeature(m,{isAddable:true});
													a.showNon_modifiablePointByAddress(address);
												});
											}
										},
										{
											iconCls : 'deppon_icons_notice',
											tooltip : order.orderHandle.i18n('pkp.order.orderHandle.copySms'),
											width : 10,
											handler : function(gridView, rowIndex, colIndex){
												var grid = gridView.up('grid'),
													record = gridView.getStore().getAt(rowIndex);
//												var sms = new Array();
												record.data['pickupAddress'] = record.get('pickupProvince') 
																+ record.get('pickupCity') 
																+ record.get('pickupCounty')
																+ record.get('pickupElseAddress');
												record.data['customerMobile'] = record.get('mobile');
												if(record.get('orderType') == 'PICKUP_ORDER'){
													record.data['pickupTime'] = order.orderHandle.dateFormat(record.get('earliestPickupTime'))
													+ '-'
													+ order.orderHandle.dateFormat(record.get('latestPickupTime'));
												} else {
													record.data['pickupTime'] = order.orderHandle.dateFormat(record.get('latestPickupTime'));
												}
												var params = {
														'orderHandleVo' : {
															'orderHandleDto' : record.data
														}
												}
												var smsWin = grid.getSmsWindow();
												Ext.Ajax.request({
													url : order.realPath('querySmsContent.action'),
													async : false,
													jsonData : params,
													success : function(response) {
														var json = Ext.decode(response.responseText);
														if (json.success) {
															smsWin.query('textarea[name="sms"]')[0].setValue(json.smsContent);
														}
													},
													exception : function(response) {
														var result = Ext.decode(response.responseText);
														Ext.ux.Toast.msg(order.orderHandle.i18n('pkp.order.orderHandle.warning'), result.message, 'error',
																3000);
													}
												});
//												if(record.get('orderType') == 'PICKUP_ORDER'){
//													sms.push('[');
//													sms.push(record.get('orderNo'));
//													sms.push(']接货： ');
//													sms.push(record.get('salesDepartmentName'));
//													sms.push(',');
//													sms.push(record.get('customerName'));
//													sms.push(',');
//													sms.push(record.get('tel'));
//													sms.push('/');
//													sms.push(record.get('mobile'));
//													sms.push('，地址：');
//													sms.push(record.get('pickupProvince'));
//													sms.push(record.get('pickupCity'));
//													sms.push(record.get('pickupCounty'));
//													sms.push(record.get('pickupElseAddress'));
//													sms.push('，货物信息：');
//													sms.push(record.get('goodsQty'));
//													sms.push('件/');
//													sms.push(record.get('weight'));
//													sms.push('公斤/');
//													sms.push(record.get('volume'));
//													sms.push('方，接货时间段:');
//													sms.push(order.orderHandle.dateFormat(record.get('earliestPickupTime')));
//													sms.push('-');
//													sms.push(order.orderHandle.dateFormat(record.get('latestPickupTime')));
//													sms.push(record.get('orderNotes'));
//												} else {
//													sms.push('[');
//													sms.push(record.get('orderNo'));
//													sms.push(']转货：');
//													sms.push(record.get('salesDepartmentName'));
//													sms.push(',货物信息：');
//													sms.push(record.get('weight'));
//													sms.push('公斤/');
//													sms.push(record.get('volume'));
//													sms.push('方，接货时间段:');
//													sms.push(order.orderHandle.dateFormat(record.get('latestPickupTime')));
//													sms.push(',约车编号：');
//													sms.push(record.get('orderNo'));
//													sms.push(record.get('orderNotes'));
//												}
//												smsWin.query('textarea[name="sms"]')[0].setValue(sms.join(''));
												smsWin.show();
											}
										} ]
							},
							{
								text : order.orderHandle.i18n('pkp.order.orderHandle.orderNo'),
								dataIndex : 'orderNo',
								xtype: 'ellipsiscolumn',
								width : 120
							},
							{
								text : order.orderHandle.i18n('pkp.order.orderHandle.orderType'),
								xtype : 'linebreakcolumn',
								dataIndex : 'orderType',
								renderer : function(value) {
									return FossDataDictionary.rendererSubmitToDisplay(value, 'PKP_ORDER_TYPE');
								},
								flex : 1
							},
							{
								text : order.orderHandle.i18n('pkp.order.orderHandle.preprocess'), // 预处理建议
								windowClassName : 'Foss.order.orderHandle.PreprocessWindow',
								xtype : 'openwindowcolumn',
								dataIndex : 'preprocess',
								renderer : function(value, meta, record) {
									var content = '';
									if(!Ext.isEmpty(record.get('smallZoneNameSuggested')) && !Ext.isEmpty(record.get('vehicleNoSuggested')) && !Ext.isEmpty(record.get('driverNameSuggested'))){
										content = record.get('smallZoneNameSuggested') + '<br/>/' + record.get('vehicleNoSuggested') + '<br/>/' + record.get('driverNameSuggested');
									} else if(!Ext.isEmpty(record.get('smallZoneNameSuggested')) && !Ext.isEmpty(record.get('vehicleNoSuggested'))){
										content = record.get('smallZoneNameSuggested') + '<br/>/' + record.get('vehicleNoSuggested');
									} else if(!Ext.isEmpty(record.get('smallZoneNameSuggested')) && !Ext.isEmpty(record.get('driverNameSuggested'))){
										content = record.get('smallZoneNameSuggested') + '<br/>/' + record.get('driverNameSuggested');
									} else if(!Ext.isEmpty(record.get('vehicleNoSuggested')) && !Ext.isEmpty(record.get('driverNameSuggested'))){
										content = record.get('vehicleNoSuggested') + '<br/>/' + record.get('driverNameSuggested');
									} else if(!Ext.isEmpty(record.get('smallZoneNameSuggested'))){
										content = record.get('smallZoneNameSuggested');
									} else if(!Ext.isEmpty(record.get('vehicleNoSuggested'))){
										content = record.get('vehicleNoSuggested');
									} else if(!Ext.isEmpty(record.get('driverNameSuggested'))){
										content = record.get('driverNameSuggested');
									}
									return content;
								},
								width: 120
							}, 
							{
								text : order.orderHandle.i18n('pkp.order.orderHandle.weightVolume'),
								xtype : 'templatecolumn',
								dataIndex : 'weight',
								tpl : '{weight}KG/<br/>{volume}方',
								width : 60
							},
							{
								text : order.orderHandle.i18n('pkp.order.orderHandle.pickupAddress'),
								xtype : 'linebreakcolumn',
								sortable : true,
								dataIndex : 'pickupProvince',
								doSort: function(state) {
							        var ds = this.up('tablepanel').store;
							        ds.sort({
							        	direction: state,
							        	sorterFn: function(o1, o2){
							                var getRank = function(record){
							                	var address = "";
												if(!Ext.isEmpty(record.get('pickupProvince'))){
													address += record.get('pickupProvince')
												}
												if(!Ext.isEmpty(record.get('pickupCity'))){
													address += record.get('pickupCity')
												}
												if(!Ext.isEmpty(record.get('pickupCounty'))){
													address += record.get('pickupCounty')
												}
												if(!Ext.isEmpty(record.get('pickupElseAddress'))){
													address += record.get('pickupElseAddress')
												}
												return address;
							                },
							                rank1 = getRank(o1),
							                rank2 = getRank(o2);

							                if (rank1 === rank2) {
							                    return 0;
							                }

							                return rank1 < rank2 ? -1 : 1;
							            }
					        		});
							    },
								renderer : function(value,metaData,record,colIndex) {
									var address = "";
									if(!Ext.isEmpty(record.get('pickupProvince'))){
										address += record.get('pickupProvince')
									}
									if(!Ext.isEmpty(record.get('pickupCity'))){
										address += record.get('pickupCity')
									}
									if(!Ext.isEmpty(record.get('pickupCounty'))){
										address += record.get('pickupCounty')
									}
									if(!Ext.isEmpty(record.get('pickupElseAddress'))){
										address += record.get('pickupElseAddress')
									}
									if(!Ext.isEmpty(record.get('deliveryCustomerAddressNote'))){
										address += "("+record.get('deliveryCustomerAddressNote')+")"
									}
									if(!Ext.isEmpty(record.get('addressType')) && !Ext.isEmpty(record.get('specialVehicleNo'))){
										address = address + '<br/>/' + record.get('addressType')+ '<br/>/' + record.get('specialVehicleNo');
									}else if(!Ext.isEmpty(record.get('addressType'))){
										address = address + '<br/>/' + record.get('addressType')
									}
									return address;
								},
								width : 240
							},{
								text : order.orderHandle.i18n('pkp.order.orderHandle.orderNotes'),
								xtype: 'ellipsiscolumn',
								dataIndex : 'orderNotes',
								width : 100
							},{
								text : order.orderHandle.i18n('pkp.order.orderHandle.deptInfo'),
								xtype : 'linebreakcolumn',
								dataIndex : 'salesDepartmentName',
								renderer : function(value,metaData,record,colIndex) {
									var content = '';
									if(!Ext.isEmpty(record.get('salesDepartmentName'))){
										content=record.get('salesDepartmentName');
									}
									if(!Ext.isEmpty(record.get('depTelephone'))){
										content+='/'+record.get('depTelephone');
									}
									return content;
								},
								width : 240
							},
							{
								text : order.orderHandle.i18n('pkp.order.orderHandle.useCarTime'),
								xtype : 'linebreakcolumn',
								sortable : true,
								dataIndex : 'latestPickupTime',
								renderer : function(value,metaData,record,colIndex) {
									if(!Ext.isEmpty(record.get('earliestPickupTime'))){
										var earliestPickupTime = Ext.Date.format(new Date(record.get('earliestPickupTime')), 'Y-m-d H:i:s');
									}
									if(!Ext.isEmpty(record.get('latestPickupTime'))){
										var latestPickupTime = Ext.Date.format(new Date(record.get('latestPickupTime')), 'Y-m-d H:i:s');
									}
									var content = '';
									if(!Ext.isEmpty(earliestPickupTime) && !Ext.isEmpty(latestPickupTime)){
										content = earliestPickupTime + '至' + latestPickupTime;
									} else if(!Ext.isEmpty(earliestPickupTime)){
										content = earliestPickupTime;
									} else if(!Ext.isEmpty(latestPickupTime)){
										content = latestPickupTime;
									}
									return content;
								},
								width : 160
							}, {
								text : order.orderHandle.i18n('pkp.order.orderHandle.orderStatus'),
								hidden: true,
								dataIndex : 'orderStatus',
								xtype : 'linebreakcolumn',
								renderer : function(value) {
									return FossDataDictionary.rendererSubmitToDisplay(value, 'PKP_ORDER_STATUS');
								},
								width : 60
							}, {
								text : order.orderHandle.i18n('pkp.order.queryDispatchOrder.vehicleTime'),
								dataIndex : 'orderVehicleTime',
								width : 150,
								renderer : function(value,metaData,record,colIndex) {
									var content = Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
									return content;
								}
							}, {
								//已分配记录
								text : order.orderHandle.i18n('pkp.order.expressOrderHandle.assignedRecord'),
								dataIndex : 'assignedRecord',
								xtype: 'ellipsiscolumn',
								width : 100
							}, {
								text : '司机退回原因',
								dataIndex : 'operateNotes',
								xtype : 'linebreakcolumn',
								renderer : function(value, metadata, record, rowIndex, columnIndex, store){
									var result = FossDataDictionary.rendererSubmitToDisplay(value, 'PKP_COMPANY_REJECT_REASON');
									if(value == result){
										result = FossDataDictionary.rendererSubmitToDisplay(value, 'PKP_CUSTOMER_REJECT_REASON');
									}
									return result;
								},
								width : 100
							},{
								text : order.orderHandle.i18n('pkp.order.orderHandle.customerName'),
								hidden: true,
								dataIndex : 'customerName'//,
								//xtype : 'ellipsiscolumn',<!--14.7.22 gcl AUTO-188 对于隐藏列去掉宽度和类型设置-->
								//width : 100
							}, {
								text : order.orderHandle.i18n('pkp.order.orderHandle.tel'),
								hidden: true,
								dataIndex : 'tel'//,
								//xtype : 'ellipsiscolumn',
								//width : 80
							}, {
								text : order.orderHandle.i18n('pkp.order.orderHandle.mobile'),
								hidden: true,
								dataIndex : 'mobile'//,
								//xtype : 'ellipsiscolumn',
								//width : 100
							}, {
								text : order.orderHandle.i18n('pkp.order.orderHandle.vehicleType'),
								hidden: true,
								//xtype : 'linebreakcolumn',
								dataIndex : 'vehicleType'//,
								//width : 60
							} , {
								text : order.orderHandle.i18n('pkp.order.orderHandle.consigneeAddress'),
								hidden: true,
								//xtype : 'linebreakcolumn',
								dataIndex : 'consigneeAddress'//,
								//width : 100
							}, {
								text : order.orderHandle.i18n('pkp.order.orderHandle.goodsPackage'),
								hidden: true,
								//xtype : 'linebreakcolumn',
								dataIndex : 'goodsPackage'//,
								//width : 100
							}, {
								text : '车队是否为约车部门',
								hidden: true,
								dataIndex : 'selfVehicle',
							}],
					viewConfig : {
						stripeRows : false,
						getRowClass : function(record, rowIndex, rp, ds) {
							var result = calculateMin(record
									.get('orderVehicleTime'), 3);
							if(record.get('orderStatus') == FossDataDictionary.rendererDisplayToSubmit('待改接','PKP_ORDER_STATUS')){
								return 'order-dispatchOrder-row-blue';
							}else if(result === 1){
								return 'order-dispatchOrder-row-pink';
							}
						},
						enableTextSelection : true
					},
					plugins : [ {
						ptype : 'rowexpander',
						rowsExpander : false,
						rowBodyElement : 'Foss.order.orderHandle.ExpanderFormPanel'
					} ],
					tbar : [
							{
								xtype : 'tbspacer',
								width : 200
							},
							{
								xtype : 'image',
								imgCls : 'foss_icons_pkp_handleOvertime'
							},
							{
								xtype : 'label',
								text : order.orderHandle.i18n('pkp.order.orderHandle.timeout')
							},
							{
								xtype : 'image',
								imgCls : 'foss_icons_pkp_warning'
							},
							{
								xtype : 'label',
								text : order.orderHandle.i18n('pkp.order.orderHandle.15minutes')
							},
							{
								xtype : 'image',
								imgCls : 'foss_icons_pkp_serWarning'
							},
							{
								xtype : 'label',
								text : order.orderHandle.i18n('pkp.order.orderHandle.end15minutes')
							},
							{
								xtype : 'image',
								imgCls : 'foss_icons_pkp_waitToBeDisposed'
							},
							{
								xtype : 'label',
								text : order.orderHandle.i18n('pkp.order.orderHandle.againOrder')
							},
							{
								xtype : 'tbspacer',
								width : 100
							},
							'->',
							{
								xtype : 'button',
								text : order.orderHandle.i18n('pkp.order.orderHandle.refresh'),
								disabled : !order.orderHandle.isPermission('orderhandleindex/orderhandleindexquerybutton'),
								hidden : !order.orderHandle.isPermission('orderhandleindex/orderhandleindexquerybutton'),
								handler : function() {
									if (order.queryForm.getForm().isValid()) {
										order.pagingBar.moveFirst();
									} else {
										Ext.ux.Toast.msg(order.orderHandle.i18n('pkp.order.orderHandle.warning'), order.orderHandle.i18n('pkp.order.orderHandle.inputCondition'),
												'error', 3000);
									}
								}
							} ],
					initComponent : function() {
						var me = this;
						me.store = Ext
								.create('Foss.order.orderHandle.orderHandleResultStore');
						me.selModel = Ext
								.create(
										'Ext.selection.CheckboxModel',
										{
											checkOnly: true,
											listeners : {
												select : function(rowModel,
														record, index, eOpts) {
													var activeTab = order.carTab.getActiveTab();
													if(activeTab.isXType('tabpanel')){
														var selectTruck = activeTab.getActiveTab().down('grid').getSelectionModel().getSelection()[0];
													} else {
														var selectTruck = activeTab.getSelectionModel().getSelection()[0];
													}
													if(!Ext.isEmpty(selectTruck)){
														record.set('smallZoneNameSuggested',selectTruck.get('smallZone'));
														record.set('vehicleNoSuggested',selectTruck.get('vehicleNo'));
														record.set('driverNameSuggested',selectTruck.get('driverName'));
														record.set('driverCodeSuggested',selectTruck.get('driverCode'));
														record.set('driverMobile',selectTruck.get('driverMobile'));
//														record.set('isPda',selectTruck.get('isPdaBundle'));
														record.set('isPda','Y');
														// 如果PDA未绑定，设置短信发送
														if(selectTruck.get('isPdaBundle') === 'N'){
															record.set('isSms','Y');
														};
														record.set('smallZoneCodeSuggested',selectTruck.get('ownedZoneCode'));
													}
													if (Ext.isEmpty(record.get('vehicleNoSuggested'))
															&& Ext.isEmpty(record.get('driverNameSuggested'))) {
														Ext.ux.Toast
														.msg(
																order.orderHandle.i18n('pkp.order.orderHandle.warning'),
																order.orderHandle.i18n('pkp.order.orderHandle.noPreprocess'),
																'error',
																3000);
													}
													var params = {
															'vehicleVo' : {
																'ownTruckConditionDto' : {
																	'vehicleNo' : record.get('vehicleNoSuggested')
																}
															}
													};
													Ext.Ajax.request({
														url : order.realPath('queryVolumeByVehicleNo.action'),
														async : true,
														jsonData : params,
														success : function(response) {
															var json = Ext.decode(response.responseText);
															if (json.success) {
																var ownTruck = json.vehicleVo.ownTruckDto;
																if(!Ext.isEmpty(ownTruck)){
																	if(ownTruck.remainingVolume < record.get('volume')){
																		Ext.ux.Toast.msg(order.orderHandle.i18n('pkp.order.orderHandle.warning'), order.orderHandle.i18n('pkp.order.orderHandle.vehicleVolumeWarning'),'error',
																				3000);
																	}
																}
															}
														},
														exception : function(response) {
															var result = Ext.decode(response.responseText);
															Ext.ux.Toast.msg(order.orderHandle.i18n('pkp.order.orderHandle.warning'), result.message, 'error',
																	3000);
														}
													});
												}
											}
										});
						me.dockedItems = [ {
							xtype : 'toolbar',
							dock : 'bottom',
							layout : 'column',
							defaults : {
								margin : '0 0 5 3'
							},
							items : [ {
								xtype : 'button',
								text : order.orderHandle.i18n('pkp.order.specialPickupAddress.markSpecialAddress'),
								handler : function() {
									var records = order.resultGrid.getSelectionModel().getSelection();
									if(records.length != 1){
										Ext.ux.Toast.msg(order.orderHandle.i18n('pkp.order.orderHandle.warning'), 
												order.orderHandle.i18n('pkp.order.specialPickupAddress.choseOneRow'), 'error', 3000);
										return;
									}	
									if(!Ext.isEmpty(records[0].data.addressTypeCode)&&!Ext.isEmpty(records[0].data.specialVehicleNo))
									{
										Ext.getCmp('pkp_order_orderHandle_addressType_Id').setValue(records[0].data.addressTypeCode);
										Ext.getCmp('pkp_order_orderHandle_vehicleNo_Id').setValue(records[0].data.specialVehicleNo);
									}else if(!Ext.isEmpty(records[0].data.addressTypeCode))
									{
										Ext.getCmp('pkp_order_orderHandle_addressType_Id').setValue(records[0].data.addressTypeCode);
										Ext.getCmp('pkp_order_orderHandle_vehicleNo_Id').setValue('');
									}else{
										Ext.getCmp('pkp_order_orderHandle_addressType_Id').setValue('');
										Ext.getCmp('pkp_order_orderHandle_vehicleNo_Id').setValue('');
									}
									markWin = Ext.create('Foss.order.orderHandle.MarkSpecialAddressWindow').show();
								}
							} ]
						} ]
						me.bbar = Ext.create('Deppon.StandardPaging', {
							store : me.store,
							plugins: 'pagesizeplugin',
							displayInfo: true
						});
						order.pagingBar = me.bbar;
						me.callParent();
					}
				});


Ext.define('Foss.order.orderHandle.markSpecialAddressForm',{
	extend:'Ext.form.Panel',	
	frame:true,
	collapsible: true,
	animCollapse: true,
	defaults: {
		margin: '5 10 5 10',
		labelWidth: 120
	},
	defaultType: 'textfield',//14.7.15 gcl AUTO-160 统一为下拉框控件
	layout: 'column',
	initComponent : function(){
		var me = this;
		me.items = [{
			xtype : 'combobox',
			name : 'addressType',
			id : 'pkp_order_orderHandle_addressType_Id',
			fieldLabel : order.orderHandle.i18n('pkp.order.specialPickupAddress.specialPickupAddressType'),
			queryModel : 'local',
			displayField : 'valueName',
			valueField : 'valueCode',
			editable : false,
			listWidth : 80,
			store : FossDataDictionary.getDataDictionaryStore('SPECIAL_ADDRESS_TYPE')
		},{
			fieldLabel : order.orderHandle.i18n('pkp.order.orderHandle.vehicleNo'),
			xtype : 'commontruckselector',
			id : 'pkp_order_orderHandle_vehicleNo_Id',
			showContent : '{vehicleNo}&nbsp;&nbsp;&nbsp;{orgName}',
			name : 'vehicleNo'
		}];
		me.callParent();
	}
});	

order.orderHandle.markSpecialAddressForm = Ext.create('Foss.order.orderHandle.markSpecialAddressForm');
//标记特殊地质
Ext.define('Foss.order.orderHandle.MarkSpecialAddressWindow', {
	extend : 'Ext.window.Window',
	title: '标记特殊接货地址',
	resizable : false,
	closeAction : 'hide',
	width : 350,
	height : 250,
	items : [order.orderHandle.markSpecialAddressForm],
	buttons : [{
		cls : 'yellow_button',
		text:order.orderHandle.i18n('pkp.order.orderHandle.save'),//保存,
		handler : function() {
			var markAddress = "";
			var record = Ext.getCmp('Foss_order_orderHandle_orderHandleResultGrid_Id').getSelectionModel().getSelection();
			if(!Ext.isEmpty(record[0].data.pickupProvince)){
				markAddress += record[0].data.pickupProvince
			}
			if(!Ext.isEmpty(record[0].data.pickupCity)){
				markAddress += record[0].data.pickupCity
			}
			if(!Ext.isEmpty(record[0].data.pickupCounty)){
				markAddress += record[0].data.pickupCounty
			}
			if(!Ext.isEmpty(record[0].data.pickupElseAddress)){
				markAddress += record[0].data.pickupElseAddress
			}
			var addressType = Ext.getCmp('pkp_order_orderHandle_addressType_Id').getValue();
			var vehicleNo = Ext.getCmp('pkp_order_orderHandle_vehicleNo_Id').getRawValue();
			var vehicleDeptName = null;
			var vehicleDeptCode = null;
			if(addressType==''||addressType==null){
				Ext.ux.Toast.msg(order.orderHandle.i18n('pkp.order.orderHandle.warning'), 
						order.orderHandle.i18n('pkp.order.specialPickupAddress.AddressTypeNotNull'), 'error', 3000);
				return;
			}
			if(vehicleNo==''||vehicleNo==null){
				vehicleDeptName = '';
				vehicleDeptCode = '';
			}else{
				vehicleDeptName = Ext.getCmp('pkp_order_orderHandle_vehicleNo_Id').valueModels[0].data.orgName;
				vehicleDeptCode = Ext.getCmp('pkp_order_orderHandle_vehicleNo_Id').valueModels[0].data.orgId;
			}
			
			Ext.Ajax.request({
				url:order.realPath('savePickupAddressInfo.action'),
				params : {
					'specialAddressVo.specialAddressEntity.address': markAddress, //地址
					'specialAddressVo.specialAddressEntity.addressType': addressType, //地址类型
					'specialAddressVo.specialAddressEntity.vehicleNo': vehicleNo, //车牌号
					'specialAddressVo.specialAddressEntity.vehicleDept': vehicleDeptName, //车队
					'specialAddressVo.specialAddressEntity.vehicleDeptCode': vehicleDeptCode //车队code
				},
				success : function(response) {
					var result = Ext.decode(response.responseText);
					Ext.ux.Toast.msg(order.orderHandle.i18n('pkp.order.orderHandle.warning'),result.message);
					markWin.close();
				},
				exception : function(response) {
					var result = Ext.decode(response.responseText);
					Ext.ux.Toast.msg(order.orderHandle.i18n('pkp.order.orderHandle.warning'),result.message,'error',4000);
				}
		    });
		}
	}],
	listeners : {
		beforeshow:function(component, eOpts){
			var vehicleNo = component.down('form').getForm().findField('vehicleNo');
			vehicleNo.store.removeListener('beforeload');
			vehicleNo.store.addListener('beforeload', function(store, operation, eOpts) {
				var searchParams = operation.params;
				if (Ext.isEmpty(searchParams)) {
					searchParams = {};
					Ext.apply(operation, {
						params : searchParams
					});
				}
				searchParams['truckVo.truck.vehicleNo'] = vehicleNo.rawValue;
			});
			vehicleNo.getStore().load();
			//vehicleNo.expand();
		}
	}
});	

// 订单结果列表双击展开
Ext
		.define(
				'Foss.order.orderHandle.ExpanderFormPanel',
				{
					extend : 'Ext.form.FormPanel',
					cls : 'autoHeight',
					bodyCls : 'autoHeight',
					layout : 'column',
					bindData : function(record, grid, rowBodyElement) {
						this.loadRecord(record);
						// 判断是否PDA发送
//						if(record.get('isPda') ===  'Y'){
//							rowBodyElement.getMainOpt().getComponent(0).setValue(true);
//						} else {
//						}
						rowBodyElement.getMainOpt().getComponent(0).setValue(true);
						rowBodyElement.getMainOpt().getComponent(0).setReadOnly(true);
						// 判断是否短信发送
						if(record.get('isSms') ===  'Y'){
							rowBodyElement.getMainOpt().getComponent(1).setValue(true);
						} else {
							rowBodyElement.getMainOpt().getComponent(1).setValue(false);
						}
						// 判断是否发送客户
						if(record.get('isCustomer') ===  'Y'){
							rowBodyElement.getMainOpt().getComponent(2).setValue(true);
						} else {
							rowBodyElement.getMainOpt().getComponent(2).setValue(false);
						}
					},
					tipData : null,
					setTipData : function(record) {
						this.tipData = record.data;
					},
					mainOpt : null,
					getMainOpt : function(){
						var me = this;
						if(this.mainOpt == null){
							this.mainOpt = Ext.create('Ext.form.CheckboxGroup',{
								columnWidth : .4,
								columns : 4,
								labelAlign : 'right',
								items : [
										{
											boxLabel : order.orderHandle.i18n('pkp.order.orderHandle.pdaSend'),
											checked : true,
											listeners : {
												change : function(field, newValue,oldValue, eOpts){
													var me = this;
													// 获得form的record
													var record = me.up('form').getRecord();
													// 根据选取值同步更新record中值
													if(newValue){
														newValue = 'Y';
													} else {
														newValue = 'N';
													}
													record.set('isPda',newValue);
												}
											}
										},
										{
											boxLabel : order.orderHandle.i18n('pkp.order.orderHandle.smsSend'),
											tipConfig : {
												title : order.orderHandle.i18n('pkp.order.orderHandle.smsPreview'),
												height : 150,
												width : 220,
												trackMouse : true,
												hideDelay : 2000,
												depth : 2,
												tpl : new Ext.XTemplate(
														'<tpl if="orderType == \'PICKUP_ORDER\'">',
														'<p>[{orderNo}]接货： {salesDepartmentName}，{customerName}，{tel}/{mobile}，地址：{pickupProvince}{pickupCity}{pickupCounty}{pickupElseAddress}，货物信息：{goodsQty}件/{weight}公斤/{volume}方，接货时间段:{[this.dateFormat(values.earliestPickupTime)]}-{[this.dateFormat(values.latestPickupTime)]} {orderNotes}</p>',
														'</tpl>',
														'<tpl if="orderType == \'TRANSFER_ORDER\'">',
														'<p>{orderNo}转货： {salesDepartmentName}，货物信息：{weight}公斤/{volume}方，接货时间段:{[this.dateFormat(values.latestPickupTime)]}，约车编号：{orderNo} {orderNotes}</p>',
														'</tpl>',{
															dateFormat : function(date){
																return Ext.Date.format(new Date(date),'H:i');
															}
														}),
												buttons : [ {
													text : order.orderHandle.i18n('pkp.order.orderHandle.copy'),
													plugins: Ext.create('Deppon.ux.ZeroClipboardPlugin', {
														targetFun: function(scope) {
															var el = scope.up(
																	'tooltip')
																	.getEl();
															var p = el.query('p');
															if(p[0].innerText){
																return p[0].innerText;
															} else {
																return p[0].innerHTML;
															}
														}
													})
												} ]
											},
											listeners : {
												change : function(field, newValue,oldValue, eOpts){
													var me = this;
													// 获得form的record
													var record = me.up('form').getRecord();
													// 根据选取值同步更新record中值
													if(newValue){
														newValue = 'Y';
													} else {
														newValue = 'N';
													}
													record.set('isSms',newValue);
												}
											}
										},
										{
											boxLabel : order.orderHandle.i18n('pkp.order.orderHandle.sendToCustomer'),
											listeners : {
												change : function(field, newValue,oldValue, eOpts){
													var me = this;
													// 获得form的record
													var record = me.up('form').getRecord();
													// 根据选取值同步更新record中值
													if(newValue){
														newValue = 'Y';
													} else {
														newValue = 'N';
													}
													record.set('isCustomer',newValue);
												}
											}
										},
										{
											xtype : 'container',
											items : [ {
												text : order.orderHandle.i18n('pkp.order.orderHandle.accept'),
												xtype : 'button',
												disabled : !order.orderHandle.isPermission('orderhandleindex/orderhandleindexacceptbutton'),
												hidden : !order.orderHandle.isPermission('orderhandleindex/orderhandleindexacceptbutton'),
												handler : function() {
													var me = this;
													var pda = me.up(
															'checkboxgroup')
															.getComponent(0);
													var sms = me.up(
															'checkboxgroup')
															.getComponent(1);
													if (pda.getValue() === false
															&& sms.getValue() === false) {
														Ext.ux.Toast
																.msg(
																		order.orderHandle.i18n('pkp.order.orderHandle.warning'),
																		order.orderHandle.i18n('pkp.order.orderHandle.chooseOneMethod'),
																		'error',
																		3000);
														return;
													}
													if(Ext.getCmp(order.mainPanelId).acceptValidate()){
														Ext.getCmp(order.mainPanelId).acceptOrder();
													}
												}
											} ]
										} ]
							});
						}
						return this.mainOpt;
					},
							initComponent : function(){
								var me = this;
								me.items = [{
												html : '&nbsp',
												columnWidth : .2
											},,me.getMainOpt()];
								me.callParent();
							}
				});

// 已用车辆Model
Ext.define('Foss.order.orderHandle.UsedVehicleModel', {
	extend : 'Ext.data.Model',
	fields : [ {
		// 车牌号
		name : 'vehicleNo',
		type : 'string'
	}, {
		// 剩余重量
		name : 'remainingWeight',
		type : 'float'
	}, {
		// 剩余体积
		name : 'remainingVolume',
		type : 'float'
	}, {
		// 车型
		name : 'vehicleType',
		type : 'string'
	}, {
		// 净重
		name : 'netWeight',
		type : 'float'
	}, {
		// 净空
		name : 'netVolume',
		type : 'float'
	}, {
		// 司机姓名
		name : 'driverName',
		type : 'string'
	}, {
		// 司机编码
		name : 'driverCode',
		type : 'string'
	}, {
		// 是否PDA绑定
		name : 'isPdaBundle',
		type : 'string'
	}, {
		// 所属区域名称
		name : 'ownedZoneName',
		type : 'string'
	}, {
		// 所属区域编码
		name : 'ownedZoneCode',
		type : 'string'
	}, {
		// 司机手机
		name : 'driverMobile',
		type : 'string'
	}, {
		// 司机手机
		name : 'deviceNo',
		type : 'string'
	} ]
});

// 已用车辆Store
Ext.define('Foss.order.orderHandle.UsedVehicleStore', {
	extend : 'Ext.data.Store',
	autoLoad : false,
	model : 'Foss.order.orderHandle.UsedVehicleModel',
	pageSize : 10,
	proxy : {
		type : 'ajax',
		actionMethods : 'POST',
		url : order.realPath('queryUsedVehicle.action'),
		reader : {
			type : 'json',
			root : 'vehicleVo.usedVehicleDtos',
			totalProperty : 'totalCount'
		}
	},
	sorters : [ {
		property : 'remainingVolume',
		direction : 'DESC'
	} ],
	listeners : {
		beforeload : function(store, operation, eOpts){
			Ext.apply(operation, {
				params : {
					'vehicleVo.ownTruckConditionDto.serviceFleetCode' : order.userVehicleGrid.down('combobox').getValue()
				}
			});
		}
	}
});


// 外请车Model
Ext.define('Foss.order.orderHandle.LeasedTruckModel', {
	extend : 'Ext.data.Model',
	fields : [ {
		name : 'vehicleNo',
		type : 'string'
	}, {
		name : 'vehicleType',
		type : 'string'
	}, {
		name : 'driverName',
		type : 'string'
	}, {
		name : 'driverMobile',
		type : 'string'
	}, {
		name : 'tailBoard',
		type : 'boolean'
	}, {
		name : 'openVehicle',
		type : 'boolean'
	} ]
});

// 外请车Store
Ext.define('Foss.order.orderHandle.LeasedTruckStore', {
	extend : 'Ext.data.Store',
	autoLoad : false,
	model : 'Foss.order.orderHandle.LeasedTruckModel',
	pageSize : 10,
	proxy : {
		type : 'ajax',
		actionMethods : 'POST',
		url : order.realPath('queryLeasedTruck.action'),
		reader : {
			type : 'json',
			root : 'vehicleVo.leasedTruckDtos',
			totalProperty : 'totalCount'
		}
	},
	listeners : {
		beforeload : function(store, operation, eOpts) {
			var queryForm = order.leasedTruckForm;
			if (queryForm != null) {
				var queryParams = queryForm.getValues();
				Ext.apply(operation, {
					params : {
						'vehicleVo.leasedTruckConditionDto.vehicleNo' : queryParams.vehicleNo,
						'vehicleVo.leasedTruckConditionDto.vehicleType' : queryParams.vehicleType,
						'vehicleVo.leasedTruckConditionDto.driverName' : queryParams.driverName,
						'vehicleVo.leasedTruckConditionDto.driverMobile' : queryParams.driverMobile,
						'vehicleVo.leasedTruckConditionDto.openVehicle' : queryParams.openVehicle
					}
				});
			}
		}
	}
});
//add by 329757
//已绑定外请车Model
Ext.define('Foss.order.orderHandle.BundleLeasedTruckModel', {
	extend : 'Ext.data.Model',
	fields : [ {
		name : 'vehicleNo',
		type : 'string'
	}, {
		name : 'vehicleLengthName',
		type : 'string'
	}, {
		name : 'driverName',
		type : 'string'
	}, {
		name : 'driverPhone',
		type : 'string'
	}, {
		name : 'tailBoard',
		type : 'string'
	}, {
		name : 'openVehicle',
		type : 'string'
	} ]
});
//add by 329757
//已绑定外请车Store
Ext.define('Foss.order.orderHandle.BundleLeasedTruckStore', {
	extend : 'Ext.data.Store',
	autoLoad : false,
	model : 'Foss.order.orderHandle.BundleLeasedTruckModel',
	pageSize : 10,
	proxy : {
		type : 'ajax',
		actionMethods : 'POST',
		url : order.realPath('queryBundLeasedTruck.action'),
		reader : {
			type : 'json',
			root : 'vehicleVo.bindingLeasedTruckDtos',
			totalProperty : 'totalCount'
		}
	},
	listeners : {
		beforeload : function(store, operation, eOpts) {
			var queryForm = order.BundleLeasedTruckForm;
			if (queryForm != null) {
				var queryParams = queryForm.getValues();
				Ext.apply(operation, {
					params : {
						'vehicleVo.bindingLeasedTruckDto.vehicleNo' : queryParams.vehicleNo,
						'vehicleVo.bindingLeasedTruckDto.vehicleLengthName' : queryParams.vehicleLengthName,
						'vehicleVo.bindingLeasedTruckDto.driverName' : queryParams.driverName,
						'vehicleVo.bindingLeasedTruckDto.driverPhone' : queryParams.driverPhone,
						'vehicleVo.bindingLeasedTruckDto.openVehicle' : queryParams.openVehicle,
					}
				});
			}
		}
	}
});
// 自有车查询条件Model
Ext.define('Foss.order.orderHandle.OwnTruckQueryModel', {
	extend : 'Ext.data.Model',
	fields : [ {
		name : 'vehicleNo',
		type : 'string'
	}, {
		name : 'vehicleType',
		type : 'string'
	}, {
		name : 'vehicleGroupOrgCode',
		type : 'string'
	}, {
		name : 'driverCode',
		type : 'string'
	} ]
});

// 接送货车队Model
Ext.define('Foss.order.orderHandle.ServiceFleetModel', {
	extend : 'Ext.data.Model',
	fields : [ {
		name : 'name',
		type : 'string'
	}, {
		name : 'code',
		type : 'string'
	} ]
});

// 修改车辆Model
Ext.define('Foss.order.orderHandle.ModifyVehicleModel', {
	extend : 'Ext.data.Model',
	fields : [ {
		// 车牌号
		name : 'vehicleNo'
	}, {
		// 剩余重量
		name : 'remainingWeight',
		type : 'float'
	}, {
		// 剩余体积
		name : 'remainingVolume',
		type : 'float'
	}, {
		// 已接票数
		name : 'alreadyPickupGoodsQty',
		type : 'int'
	}, {
		// 未接票数
		name : 'nonePickupGoodsQty',
		type : 'int'
	}, {
		// 已送票数
		name : 'alreadyDeliverGoodsQty',
		type : 'int'
	}, {
		// 未送票数
		name : 'noneDeliverGoodsQty',
		type : 'int'
	} ]
});

// 修改车辆表单
Ext.define('Foss.order.orderHandle.ModifyVehicleForm', {
	extend : 'Ext.form.Panel',
	defaultType : 'numberfield',
	items : [ {
		fieldLabel : order.orderHandle.i18n('pkp.order.orderHandle.remainingVolume'),
		allowBlank : false,
		blankText : order.orderHandle.i18n('pkp.order.orderHandle.remainingVolumeNull'),
		minValue: 0,
		hideTrigger: true,
		name : 'remainingVolume'
	}, {
		fieldLabel : order.orderHandle.i18n('pkp.order.orderHandle.remainingWeight'),
		allowBlank : false,
		blankText : order.orderHandle.i18n('pkp.order.orderHandle.remainingWeightNull'),
		minValue: 0,
		hideTrigger: true,
		name : 'remainingWeight'
	} ],
	fbar : [
			{
				text : order.orderHandle.i18n('pkp.order.orderHandle.save'),
				handler : function() {
					var me = this;
					var formPanel = me.up('form');
					// 校验form表单是否合法
					if (formPanel.getForm().isValid()) {
						// 更新界面record至form表单内存record
						formPanel.getForm().updateRecord(formPanel.record);
						var params = {
							'vehicleVo' : {
								'situationDto' : formPanel.record.data
							}
						};
						// 请求后台修改车辆数据
						Ext.Ajax.request({
							url : order.realPath('modifyVehicle.action'),
							async : true,
							jsonData : params,
							success : function(response) {
								var json = Ext.decode(response.responseText);
								if (json.success) {
									Ext.MessageBox.alert('温馨提示', json.message,
											function() {
												me.up('window').close();
												order.userVehicleGrid.store
														.load();
											});
								}
							},
							exception : function(response) {
								var result = Ext.decode(response.responseText);
								Ext.ux.Toast.msg(order.orderHandle.i18n('pkp.order.orderHandle.warning'), result.message, 'error',
										3000);
							}
						});
					} else {
						Ext.ux.Toast.msg(order.orderHandle.i18n('pkp.order.orderHandle.warning'), '输入不合法，请重输！', 'error', 3000);
					}
				}
			}, {
				text : order.orderHandle.i18n('pkp.order.orderHandle.cancel'),
				handler : function() {
					var me = this;
					me.up('window').close();
				}
			} ]
});

// 修改车辆窗口
Ext.define('Foss.order.orderHandle.ModifyVehicleWindow', {
	extend : 'Ext.window.Window',
	title : order.orderHandle.i18n('pkp.order.orderHandle.modifyVehicle'),
	modal : true,
	width : 400,
	height : 180,
	layout : 'column',
	vehicleNo : null,
	items : null,
	initComponent : function() {
		var me = this;
		// 使用已用车辆Model
		var userVehicleModel = Ext
				.create('Foss.order.orderHandle.ModifyVehicleModel');
		// 设置model中车牌号
		userVehicleModel.set('vehicleNo', me.vehicleNo);
		// 修改车辆表单
		var modifyVehicleForm = Ext.create(
				'Foss.order.orderHandle.ModifyVehicleForm', {
					columnWidth : .8,
					record : userVehicleModel
				});
		// 加载record
		modifyVehicleForm.loadRecord(userVehicleModel);
		me.items = [ {
			html : '&nbsp',
			columnWidth : .1
		}, modifyVehicleForm ];
		me.callParent();
	}
});

// 已用车辆查询
Ext.define('Foss.order.orderHandle.UsedVehicleGrid', {
	extend : 'Ext.grid.Panel',
	id : 'Foss_order_orderHandle_usedVehicleGrid_Id',
	title : order.orderHandle.i18n('pkp.order.orderHandle.usedVehicle'),
	cls : 'autoHeight',
	bodyCls : 'autoHeight',
	store : null,
	selModel : null,
	autoScroll : true,
	modifyVehicle : null,
	emptyText: order.orderHandle.i18n('pkp.order.orderHandle.queryResultNull'),
	padding : 10,
	// 定义tab的宽度
	tabConfig : {
		width : 120
	},
	// 获得修改车辆window
	getModifyVehicleWindow : function(record) {
		return Ext.create('Foss.order.orderHandle.ModifyVehicleWindow', {
			vehicleNo : record.get('vehicleNo')
		});
	},
	columns : [ {
		xtype : 'actioncolumn',
		text : order.orderHandle.i18n('pkp.order.orderHandle.driverPosition'),
		flex : 1,
		align : 'center',
		items : [ {
			iconCls : 'foss_icons_pkp_viewDriverlocation',
			tooltip : order.orderHandle.i18n('pkp.order.orderHandle.driverPosition'),
			width : 20,
			handler : function(grid, rowIndex, colIndex){
				var record = grid.getStore().getAt(rowIndex);
				var params = {
						'vehicleVo' : {
							'ownTruckDto' : {
								'vehicleNo' : record.get('vehicleNo')
							}
						}
				};
				order.queryGpsUrl(params);
			}
		} ]
	}, {
		xtype : 'actioncolumn',
		text : order.orderHandle.i18n('pkp.order.orderHandle.modifyVehicle'),
		flex : 1,
		align : 'center',
		items : [ {
			iconCls : 'deppon_icons_edit',
			tooltip : order.orderHandle.i18n('pkp.order.orderHandle.modifyVehicle'),
			width : 20,
			handler : function(grid, rowIndex, colIndex) {
				var me = this;
				// 获得选中
				var record = grid.getStore().getAt(rowIndex);
				me.up('grid').getModifyVehicleWindow(record).show();
			}
		} ]
	}, {
		text : order.orderHandle.i18n('pkp.order.orderHandle.vehicleNo'),
		flex : 1,
		dataIndex : 'vehicleNo',
	}, {
		text : order.orderHandle.i18n('pkp.order.orderHandle.remainingVolume'),
		flex : 1,
		dataIndex : 'remainingVolume'
	}, {
		text : order.orderHandle.i18n('pkp.order.orderHandle.remainingWeight'),
		flex : 1,
		dataIndex : 'remainingWeight'
	}, {
		text : order.orderHandle.i18n('pkp.order.orderHandle.vehicleType'),
		flex : 1,
		dataIndex : 'vehicleType'
	}, {
		text : order.orderHandle.i18n('pkp.order.orderHandle.netWeight'),
		flex : 1,
		dataIndex : 'netWeight'
	}, {
		text : order.orderHandle.i18n('pkp.order.orderHandle.driverName'),
		flex : 1,
		dataIndex : 'driverName'
	}, {
		text : order.orderHandle.i18n('pkp.order.orderHandle.pdaBundle'),
		flex : 1,
		dataIndex : 'isPdaBundle',
		xtype : 'booleancolumn',
		renderer : function(value){
			if(value == 'Y'){
				return '是';
			} else {
				return '否';
			}
		}
	}, {
		text : order.orderHandle.i18n('pkp.order.orderHandle.ownRegion'),
		flex : 1,
		dataIndex : 'ownedZoneName'
	} ],
	viewConfig: {
        enableTextSelection: true
    },
	tbar : [
			{
				xtype : 'combobox',
				id : 'serviceFleet',
				store : order.orderHandle.getFleetStore(),
				displayField : 'name',
				valueField : 'code',
				listeners : {
					select : function(combo, records, eOpts) {
						var me = this;
						if(records.length > 0 ){
							params = {
									vehicleVo : {
										ownTruckConditionDto : {
											serviceFleetCode : records[0].get('code')
										}
									}
							};
							// 请求过滤不同车队组下的车
							Ext.Ajax.request({
								url : order.realPath('queryUsedVehicle.action'),
								async : true,
								jsonData : params,
								success : function(response) {
									var json = Ext.decode(response.responseText);
									order.userVehicleGrid.getStore().load();
								},
								exception : function(response) {
									var result = Ext.decode(response.responseText);
									Ext.MessageBox.alert(order.orderHandle.i18n('pkp.order.orderHandle.warning'), result.message);
								}
							});
						}
						order.userVehicleGrid.getStore().load();
					}
				}
			}, '->', {
				xtype : 'button',
				text : order.orderHandle.i18n('pkp.order.orderHandle.refresh'),
				disabled : !order.orderHandle.isPermission('orderhandleindex/orderhandleindexqueryavaliabletruckbutton'),
				hidden : !order.orderHandle.isPermission('orderhandleindex/orderhandleindexqueryavaliabletruckbutton'),
				handler : function(){
					order.userVehicleGrid.getStore().load();
				}
			} ],
	initComponent : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.order.orderHandle.UsedVehicleStore');
		me.selModel = Ext.create('Ext.selection.RadioModel', {
			mode : 'SINGLE',
			showHeaderCheckbox : false,
			listeners : {
				select : function(rowModel, record, index, eOpts){
					//14.7.24 gcl AUTO-200
					var successFun = function(state){
						order.vehicleSuccessFun(order,index,1);
					};
					var failFun = function(state){
						order.vehicleFailFun(order,record,1);
					};
					if(record.get('vehicleNo')!=null&&!Ext.isEmpty(record.get('vehicleNo'))){
						order.requestJsonAjax(record.get('vehicleNo'),successFun,failFun);
					}
				}
			}
		});
		me.callParent([ cfg ]);
	},
	listeners : {
		beforerender:function(component, eOpts){
			var grid = Ext.getCmp('Foss_order_orderHandle_usedVehicleGrid_Id');
			if(order.orderHandle.dept=='Y')
			{
				grid.down('#serviceFleet').setVisible(false);
			}
		}
	}
});

Ext
		.define(
				'Foss.order.orderHandle.OwnTruckStore',
				{
					extend : 'Ext.data.Store',
					autoLoad : false,
					model : 'Foss.order.orderHandle.UsedVehicleModel',
					pageSize : 10,
					proxy : {
						type : 'ajax',
						actionMethods : 'POST',
						url : order.realPath('queryOwnTruck.action'),
						reader : {
							type : 'json',
							root : 'vehicleVo.ownTruckDtos',
							totalProperty : 'totalCount'
						}
					},
					listeners : {
						beforeload : function(store, operation, eOpts) {
							var ownTruckForm = order.ownTruckForm;
							if (ownTruckForm != null) {
								var queryParams = ownTruckForm.getValues();
								Ext
										.apply(
												operation,
												{
													params : {
														'vehicleVo.ownTruckConditionDto.vehicleNo' : queryParams.vehicleNo,
														'vehicleVo.ownTruckConditionDto.vehicleType' : queryParams.vehicleType,
														'vehicleVo.ownTruckConditionDto.serviceFleetCode' : queryParams.serviceFleet,
														'vehicleVo.ownTruckConditionDto.driverCode' : queryParams.driverCode
													}
												});
							}
						}
					}
				});

// 自有车表单Form
Ext.define('Foss.order.orderHandle.OwnTruckForm', {
	extend : 'Ext.form.Panel',
	layout : 'column',
	defaults : {
		labelWidth : 90
	},
	items : [ {
		fieldLabel : order.orderHandle.i18n('pkp.order.orderHandle.vehicleNo'),
		xtype : 'commonowntruckselector',
		parentOrgCode : order.fleetCode,
		columnWidth : 0.5,
		name : 'vehicleNo'
	}, {
		fieldLabel : order.orderHandle.i18n('pkp.order.orderHandle.vehicleType'),
		xtype : 'commonvehicletypeselector',
		name : 'vehicleType',
		displayField : 'vehicleLengthName',
		valueField : 'vehicleLengthCode',
		columnWidth : 0.5
	}, {
		fieldLabel : order.orderHandle.i18n('pkp.order.orderHandle.vehicleGroup'),
		xtype : 'commonmotorcadeselector',
		loopOrgCode : order.fleetCode,
		fleetTypes : 'FLEET_TYPE__SHUTTLE_GOODS,TYPE__LONG_DISTANCE',
		columnWidth : 0.5,
		name : 'serviceFleet'
	}, {
		fieldLabel : order.orderHandle.i18n('pkp.order.orderHandle.driverName'),
		xtype : 'commonowndriverselector',
		parentOrgCode : order.fleetCode,
		columnWidth : 0.5,
		name : 'driverCode'
	} ],
	fbar : [ {
		xtype : 'button',
		text : order.orderHandle.i18n('pkp.order.orderHandle.reset'),
		handler : function() {
			var me = this;
			var form = me.up('form');
			form.getForm().reset();
		}
	}, '->', {
		xtype : 'button',
		text : order.orderHandle.i18n('pkp.order.orderHandle.query'),
		disabled : !order.orderHandle.isPermission('orderhandleindex/orderhandleindexqueryavaliabletruckbutton'),
		hidden : !order.orderHandle.isPermission('orderhandleindex/orderhandleindexqueryavaliabletruckbutton'),
		cls : 'yellow_button',
		handler : function() {
			if (order.ownTruckForm.getForm().isValid()) {
				order.ownTruckGrid.getStore().load();
			} else {
				Ext.ux.Toast.msg(order.orderHandle.i18n('pkp.order.orderHandle.warning'), order.orderHandle.i18n('pkp.order.orderHandle.inputCondition'), 'error', 3000);
			}
		}
	} ]
});

// GPS电子地图
Ext.define('Foss.order.orderHandle.GPSTruckMap',{
	extend : 'Ext.window.Window',
	title : order.orderHandle.i18n('pkp.order.orderHandle.electronicMap'),
	modal : true,
	closeAction : 'hide',
	height : 800,
	width : 1100,
	gpsUrl : null,
	initComponent : function(){
		var me = this;
		me.items = [{
			xtype : 'panel',
			height : 800,
			width : 1100,
			layout : 'fit',
			html : '<iframe id="gpsIframe" name="gpsIframe" width="100%" height="100%" FRAMEBORDER=0 SCROLLING=YES src="' + me.gpsUrl + '"></iframe>'
		}];
		me.callParent();
	}
});

// 请求后台获取GPS地址，并弹出window
Ext.apply(order,{
	queryGpsUrl : function(params){
		Ext.Ajax.request({
			url : order.realPath('queryGpsUrl.action'),
			async : false,
			jsonData : params,
			success : function(response) {
				var json = Ext.decode(response.responseText);
				
				Ext.create('Foss.order.orderHandle.GPSTruckMap',{
					gpsUrl : json.vehicleVo.gpsUrl
					
				}).show();
			},
			exception : function(response) {
				var result = Ext.decode(response.responseText);
				Ext.ux.Toast.msg(order.orderHandle.i18n('pkp.order.orderHandle.warning'), result.message, 'error', 3000);
			}
		});
	}
});


// 自有车查询Grid
Ext.define('Foss.order.orderHandle.OwnTruckGrid', {
	extend : 'Ext.grid.Panel',
	store : null,
	selModel : null,
	autoScroll : true,
	emptyText: order.orderHandle.i18n('pkp.order.orderHandle.queryResultNull'),
	columns : [ {
		xtype : 'actioncolumn',
		text : order.orderHandle.i18n('pkp.order.orderHandle.viewMap'),
		flex : 1,
		align : 'center',
		items : [ {
			tooltip : order.orderHandle.i18n('pkp.order.orderHandle.viewMap'),
			iconCls : 'foss_icons_pkp_viewDriverlocation',
			width : 20,
			handler : function(grid, rowIndex, colIndex){
				var record = grid.getStore().getAt(rowIndex);
				var params = {
						'vehicleVo' : {
							'ownTruckDto' : {
								'vehicleNo' : record.get('vehicleNo'),
								'driverName' : record.get('driverName')
							}
						}
				};
				order.queryGpsUrl(params);
			}
		} ]
	}, {
		text : order.orderHandle.i18n('pkp.order.orderHandle.vehicleNo'),
		dataIndex : 'vehicleNo',
		flex : 1
	}, {
		text : order.orderHandle.i18n('pkp.order.orderHandle.vehicleType'),
		dataIndex : 'vehicleType',
		flex : 1
	}, {
		text : order.orderHandle.i18n('pkp.order.orderHandle.netVolume'),
		dataIndex : 'netVolume',
		flex : 1
	}, {
		text : order.orderHandle.i18n('pkp.order.orderHandle.netWeight'),
		dataIndex : 'netWeight',
		flex : 1
	}, {
		text : order.orderHandle.i18n('pkp.order.orderHandle.region'),
		dataIndex : 'ownedZoneName',
		flex : 1
	} ],
	viewConfig: {
        enableTextSelection: true
    },
	initComponent : function() {
		var me = this;
		me.store = Ext.create('Foss.order.orderHandle.OwnTruckStore');
		me.selModel = Ext.create('Ext.selection.RadioModel', {
			mode : 'SINGLE',
			listeners : {
				select : function(rowModel, record, index, eOpts){
					//14.7.24 gcl AUTO-200
					var successFun = function(state){
						order.vehicleSuccessFun(order,index,2);
					};
					var failFun = function(state){
						order.vehicleFailFun(order,record,2);
					};
					if(record.get('vehicleNo')!=null&&!Ext.isEmpty(record.get('vehicleNo'))){
						order.requestJsonAjax(record.get('vehicleNo'),successFun,failFun);
					}
				}
			}
		});
		me.bbar = Ext.create('Deppon.StandardPaging', {
			store : me.store
		});
		me.callParent();
	}
});

// 自有车Panel
Ext.define('Foss.order.orderHandle.OwnTruckPanel', {
	extend : 'Ext.panel.Panel',
	title : order.orderHandle.i18n('pkp.order.orderHandle.ownTruck'),
	// 定义tab的宽度
	tabConfig : {
		width : 120
	},
	initComponent : function() {
		var me = this;
		order.ownTruckForm = Ext.create('Foss.order.orderHandle.OwnTruckForm',
				{
					flex : 3
				});
		order.ownTruckGrid = Ext.create('Foss.order.orderHandle.OwnTruckGrid');
		me.items = [ {
			xtype : 'container',
			layout : {
				type : 'hbox',
				align : 'center'
			},
			items : [ {
				html : '&nbsp',
				flex : 1
			}, order.ownTruckForm, {
				html : '&nbsp',
				flex : 1
			} ]
		}, order.ownTruckGrid ];
		me.callParent();
	}
});

// 外请车表单Form
Ext.define('Foss.order.orderHandle.LeasedTruckForm', {
	extend : 'Ext.form.Panel',
	layout : 'column',
	defaults : {
		labelWidth : 90
	},
	items : [ {
		fieldLabel : order.orderHandle.i18n('pkp.order.orderHandle.vehicleNo'),
		xtype : 'commonleasedvehicleselector',
		columnWidth : 0.5,
		name : 'vehicleNo'
	}, {
		fieldLabel : order.orderHandle.i18n('pkp.order.orderHandle.driverName'),
		xtype : 'commonleaseddriverselector',
		valueField : 'driverName',
		columnWidth : 0.5,
		name : 'driverName'
	}, {
		fieldLabel : order.orderHandle.i18n('pkp.order.orderHandle.vehicleType'),
		xtype : 'commonvehicletypeselector',
		columnWidth : 0.5,
		displayField : 'vehicleLengthName',
		valueField : 'vehicleLengthCode',
		name : 'vehicleType'
	}, {
		fieldLabel : order.orderHandle.i18n('pkp.order.orderHandle.mobile'),
		xtype : 'textfield',
		columnWidth : 0.5,
		name : 'driverMobile'
	}, {
		fieldLabel : order.orderHandle.i18n('pkp.order.orderHandle.isOpenVehicle'),
		xtype : 'radiogroup',
		columnWidth : 0.5,
		items : [ {
			boxLabel : order.orderHandle.i18n('pkp.order.orderHandle.yes'),
			name : 'openVehicle',
			inputValue : true,
			checked : true
		}, {
			boxLabel : order.orderHandle.i18n('pkp.order.orderHandle.no'),
			name : 'openVehicle',
			inputValue : false
		} ]
	} ],
	fbar : [ {
		xtype : 'button',
		text : order.orderHandle.i18n('pkp.order.orderHandle.reset'),
		handler : function() {
			var me = this;
			var form = me.up('form');
			form.getForm().reset();
		}
	}, '->', {
		xtype : 'button',
		text : order.orderHandle.i18n('pkp.order.orderHandle.query'),
		cls : 'yellow_button',
		disabled : !order.orderHandle.isPermission('orderhandleindex/orderhandleindexqueryleasedtruckbutton'),
		hidden : !order.orderHandle.isPermission('orderhandleindex/orderhandleindexqueryleasedtruckbutton'),
		handler : function() {
			if (order.leasedTruckForm.getForm().isValid()) {
				order.orderHandle.leasedPagingBar.moveFirst();
			} else {
				Ext.ux.Toast.msg(order.orderHandle.i18n('pkp.order.orderHandle.warning'), order.orderHandle.i18n('pkp.order.orderHandle.inputCondition'), 'error', 3000);
			}
		}
	} ]
});

// 外请车查询Grid
Ext.define('Foss.order.orderHandle.LeasedTruckGrid', {
	extend : 'Ext.grid.Panel',
	store : null,
	selModel : null,
	autoScroll : true,
	emptyText: order.orderHandle.i18n('pkp.order.orderHandle.queryResultNull'),
	columns : [ {
		text : order.orderHandle.i18n('pkp.order.orderHandle.vehicleNo'),
		dataIndex : 'vehicleNo',
		flex : 1
	}, {
		text : order.orderHandle.i18n('pkp.order.orderHandle.vehicleType'),
		dataIndex : 'vehicleType',
		flex : 1
	}, {
		text : order.orderHandle.i18n('pkp.order.orderHandle.driverName'),
		dataIndex : 'driverName',
		flex : 1
	}, {
		text : order.orderHandle.i18n('pkp.order.orderHandle.driverMobile'),
		dataIndex : 'driverMobile',
		flex : 1
	}, {
		text : order.orderHandle.i18n('pkp.order.orderHandle.isTailBoard'),
		dataIndex : 'tailBoard',
		flex : 1,
		xtype : 'booleancolumn',
		trueText : order.orderHandle.i18n('pkp.order.orderHandle.yes'),
		falseText : order.orderHandle.i18n('pkp.order.orderHandle.no')
	}, {
		text : order.orderHandle.i18n('pkp.order.orderHandle.isOpenVehicle'),
		dataIndex : 'openVehicle',
		flex : 1,
		xtype : 'booleancolumn',
		trueText : order.orderHandle.i18n('pkp.order.orderHandle.yes'),
		falseText : order.orderHandle.i18n('pkp.order.orderHandle.no')
	} ],
	viewConfig: {
        enableTextSelection: true
    },
	initComponent : function() {
		var me = this;
		me.store = Ext.create('Foss.order.orderHandle.LeasedTruckStore');
		me.selModel = Ext.create('Ext.selection.RadioModel', {
			mode : 'SINGLE',
			showHeaderCheckbox : false,
			listeners : {
				select : function(rowModel, record, index, eOpts){
					//14.7.24 gcl AUTO-200
					var successFun = function(state){
						order.vehicleSuccessFun(order,index,3);
					};
					var failFun = function(state){
						order.vehicleFailFun(order,record,3);
					};
					if(record.get('vehicleNo')!=null&&!Ext.isEmpty(record.get('vehicleNo'))){
						order.requestJsonAjax(record.get('vehicleNo'),successFun,failFun);
					}
				}
			}
		});
		me.bbar = Ext.create('Deppon.StandardPaging', {
			store : me.store,
			plugins: 'pagesizeplugin',
			displayInfo: true
		});
		order.orderHandle.leasedPagingBar = me.bbar;
		me.callParent();
	}
});

// 外请车Panel
Ext.define('Foss.order.orderHandle.LeasedTruckPanel', {
	extend : 'Ext.panel.Panel',
	title : order.orderHandle.i18n('pkp.order.orderHandle.leasedTruck'),
	// 定义tab的宽度
	tabConfig : {
		width : 120
	},
	initComponent : function() {
		var me = this;
		order.leasedTruckForm = Ext.create(
				'Foss.order.orderHandle.LeasedTruckForm', {
					flex : 3
				});
		order.leasedTruckGrid = Ext
				.create('Foss.order.orderHandle.LeasedTruckGrid');
		me.items = [ {
			xtype : 'container',
			layout : {
				type : 'hbox',
				align : 'center'
			},
			items : [ {
				html : '&nbsp',
				flex : 1
			}, order.leasedTruckForm, {
				html : '&nbsp',
				flex : 1
			} ]
		}, order.leasedTruckGrid ];
		me.callParent();
	}
});

//add by 329757
//已绑定外请车表单Form
Ext.define('Foss.order.orderHandle.BundleLeasedTruckForm', {
	extend : 'Ext.form.Panel',
	layout : 'column',
	defaults : {
		labelWidth : 90
	},
	items : [ {
		fieldLabel : order.orderHandle.i18n('pkp.order.orderHandle.vehicleNo'),
		xtype : 'commonleasedvehicleselector',
		columnWidth : 0.5,
		name : 'vehicleNo'
	}, {
		fieldLabel : order.orderHandle.i18n('pkp.order.orderHandle.driverName'),
		xtype : 'commonleaseddriverselector',
		valueField : 'driverName',
		columnWidth : 0.5,
		name : 'driverName'
	}, {
		fieldLabel : order.orderHandle.i18n('pkp.order.orderHandle.vehicleType'),
		xtype : 'commonvehicletypeselector',
		columnWidth : 0.5,
		//displayField : 'vehicleLengthName',
		valueField : 'vehicleLengthName',
		name : 'vehicleLengthName'
	}, {
		fieldLabel : order.orderHandle.i18n('pkp.order.orderHandle.mobile'),
		xtype : 'textfield',
		columnWidth : 0.5,
		name : 'driverPhone'
	}, {
		fieldLabel : order.orderHandle.i18n('pkp.order.orderHandle.isOpenVehicle'),
		xtype : 'radiogroup',
		columnWidth : 0.5,
		items : [ {
			boxLabel : order.orderHandle.i18n('pkp.order.orderHandle.yes'),
			name : 'openVehicle',
			inputValue : 'Y',
			checked : 'Y'
		}, {
			boxLabel : order.orderHandle.i18n('pkp.order.orderHandle.no'),
			name : 'openVehicle',
			inputValue : 'N'
		} ]
	} ],
	fbar : [ {
		xtype : 'button',
		text : order.orderHandle.i18n('pkp.order.orderHandle.reset'),
		handler : function() {
			var me = this;
			var form = me.up('form');
			form.getForm().reset();
		}
	}, '->', {
		xtype : 'button',
		text : order.orderHandle.i18n('pkp.order.orderHandle.query'),
		cls : 'yellow_button',
		disabled : !order.orderHandle.isPermission('orderhandleindex/orderhandleindexquerybundleleasedtruckbutton'),
		hidden : !order.orderHandle.isPermission('orderhandleindex/orderhandleindexquerybundleleasedtruckbutton'),
		handler : function() {
			if (order.BundleLeasedTruckForm.getForm().isValid()) {
				order.orderHandle.bundleasedPagingBar.moveFirst();
			} else {
				Ext.ux.Toast.msg(order.orderHandle.i18n('pkp.order.orderHandle.warning'), order.orderHandle.i18n('pkp.order.orderHandle.inputCondition'), 'error', 3000);
			}
		}
	} ]
});

// 已绑定外请车查询Grid
Ext.define('Foss.order.orderHandle.BundleLeasedTruckGrid', {
	extend : 'Ext.grid.Panel',
	store : null,
	selModel : null,
	autoScroll : true,
	emptyText: order.orderHandle.i18n('pkp.order.orderHandle.queryResultNull'),
	columns : [ {
		text : order.orderHandle.i18n('pkp.order.orderHandle.vehicleNo'),
		dataIndex : 'vehicleNo',
		flex : 1
	}, {
		text : order.orderHandle.i18n('pkp.order.orderHandle.vehicleType'),
		dataIndex : 'vehicleLengthName',
		flex : 1
	}, {
		text : order.orderHandle.i18n('pkp.order.orderHandle.driverName'),
		dataIndex : 'driverName',
		flex : 1
	}, {
		text : order.orderHandle.i18n('pkp.order.orderHandle.driverMobile'),
		dataIndex : 'driverPhone',
		flex : 1
	}, {
		text : order.orderHandle.i18n('pkp.order.orderHandle.isTailBoard'),
		dataIndex : 'tailBoard',
		flex : 1,
		//xtype : 'booleancolumn',
		//trueText : order.orderHandle.i18n('pkp.order.orderHandle.yes'),
		//falseText : order.orderHandle.i18n('pkp.order.orderHandle.no'),
		renderer:function(value){
			if(!Ext.isEmpty(value)){
				if(value=='Y'){
					return '是';
				}
				if(value=='N'){
					return '否';
				}
			}else{
				return "";
				}
			}
	}, {
		text : order.orderHandle.i18n('pkp.order.orderHandle.isOpenVehicle'),
		dataIndex : 'openVehicle',
		flex : 1,
		//xtype : 'booleancolumn',
		//trueText : order.orderHandle.i18n('pkp.order.orderHandle.yes'),
		//falseText : order.orderHandle.i18n('pkp.order.orderHandle.no'),
		renderer:function(value){
			if(!Ext.isEmpty(value)){
				if(value=='Y'){
					return '是';
				}
				if(value=='N'){
					return '否';
				}
			}else{
				return "";
				}
			}
	} ],
	viewConfig: {
        enableTextSelection: true
    },
	initComponent : function() {
		var me = this;
		me.store = Ext.create('Foss.order.orderHandle.BundleLeasedTruckStore');
		me.selModel = Ext.create('Ext.selection.RadioModel', {
			mode : 'SINGLE',
			showHeaderCheckbox : false,
			listeners : {
				select : function(rowModel, record, index, eOpts){
					//14.7.24 gcl AUTO-200
					var successFun = function(state){
						order.vehicleSuccessFun(order,index,3);
					};
					var failFun = function(state){
						order.vehicleFailFun(order,record,3);
					};
					if(record.get('vehicleNo')!=null&&!Ext.isEmpty(record.get('vehicleNo'))){
						order.requestJsonAjax(record.get('vehicleNo'),successFun,failFun);
					}
				}
			}
		});
		me.bbar = Ext.create('Deppon.StandardPaging', {
			store : me.store,
			plugins: 'pagesizeplugin',
			displayInfo: true
		});
		order.orderHandle.bundleasedPagingBar = me.bbar;
		me.callParent();
	}
});
//已绑定外请车Panel
Ext.define('Foss.order.orderHandle.BundleLeasedTruckPanel', {
	extend : 'Ext.panel.Panel',
	title : order.orderHandle.i18n('pkp.order.orderHandle.bundleLeasedTruck'),
	// 定义tab的宽度
	tabConfig : {
		width : 120
	},
	initComponent : function() {
		var me = this;
		order.BundleLeasedTruckForm = Ext.create(
				'Foss.order.orderHandle.BundleLeasedTruckForm', {
					flex : 3
				});
		order.BundleLeasedTruckGrid = Ext
				.create('Foss.order.orderHandle.BundleLeasedTruckGrid');
		me.items = [ {
			xtype : 'container',
			layout : {
				type : 'hbox',
				align : 'center'
			},
			items : [ {
				html : '&nbsp',
				flex : 1
			}, order.BundleLeasedTruckForm, {
				html : '&nbsp',
				flex : 1
			} ]
		}, order.BundleLeasedTruckGrid ];
		me.callParent();
	}
});
// 可用车辆查询Tab
Ext.define('Foss.order.orderHandle.ManualQueryTab', {
	extend : 'Ext.tab.Panel',
	title : order.orderHandle.i18n('pkp.order.orderHandle.availableVehicle'),
	frame : false,
	bodyCls : 'autoHeight',
	cls : 'innerTabPanel',
	// 定义tab的宽度
	tabConfig : {
		width : 120
	},
	activeTab : 0,
	items : [ Ext.create('Foss.order.orderHandle.OwnTruckPanel'),
			Ext.create('Foss.order.orderHandle.LeasedTruckPanel'),
			//添加已绑定外请车panel
			Ext.create('Foss.order.orderHandle.BundleLeasedTruckPanel') ],
	listeners : {
			tabchange : function(tabPanel, newCard, oldCard, eOpts){
				// 切换tabpanel后，根据类型设置grid中全部未选择
				if(oldCard.isXType('tabpanel')){
					oldCard.getActiveTab().down('grid').getSelectionModel().deselectAll();
				} else {
					oldCard.down('grid').getSelectionModel().deselectAll();
				}
			}
		}
});

// 车辆查询Tab
Ext.define('Foss.order.orderHandle.VehicleTab', {
	extend : 'Ext.tab.Panel',
	frame : false,
	bodyCls : 'autoHeight',
	cls : 'innerTabPanel',
	activeTab : 0,
	usedVehicleTab : null,
	avaiableVehicleTab : null,
	initComponent : function() {
		var me = this;
		// 已用车辆表格
		me.usedVehicleTab = Ext
				.create('Foss.order.orderHandle.UsedVehicleGrid');
		// 赋值给order对象
		order.userVehicleGrid = me.usedVehicleTab;
		// 手动查询Tab页
		me.avaiableVehicleTab = Ext
				.create('Foss.order.orderHandle.ManualQueryTab');
		// 赋值给order对象
		order.avaiableVehicleTab = me.avaiableVehicleTab;
		// 初始化车辆查询items
		me.items = [ me.usedVehicleTab, me.avaiableVehicleTab ];
		me.callParent();
	},
	listeners : {
		tabchange : function(tabPanel, newCard, oldCard, eOpts){
			// 切换tabpanel后，根据类型设置grid中全部未选择
			if(oldCard.isXType('tabpanel')){
				oldCard.getActiveTab().down('grid').getSelectionModel().deselectAll();
			} else {
				oldCard.getSelectionModel().deselectAll();
			}
		}
	}
});

Ext.apply(order,{
	requestAcceptOrder : function(params){
		// 受理订单
		Ext.Ajax.request({
			url : order.realPath('acceptOrder.action'),
			async : true,
			jsonData : {
				'orderHandleVo' : {
					'orderHandleDtoList' : params
				}
			},
			success : function(response) {
				var json = Ext.decode(response.responseText);
				var length = json.dispatchOrderVo.failOrderList.length;
				var failOrderList = json.dispatchOrderVo.failOrderList;
				var message = '';
				for(var i = 0; i < length; i ++){
					message += '订单号：';
					message += failOrderList[i].orderNo;
					message += ' ';
					message += unescape(failOrderList[i].failReason.replace(/\\/g, "%"));
					message += '<br />';
				}
				if(Ext.isEmpty(message)){
					message = json.message;
				}
				Ext.ux.Toast.msg(order.orderHandle.i18n('pkp.order.orderHandle.warning'),
						message, 'error',
						5000);
				order.resultGrid.store.load();
			},
			exception : function(response) {
				var result = Ext.decode(response.responseText);
				Ext.ux.Toast.msg(order.orderHandle.i18n('pkp.order.orderHandle.warning'),
						result.message, 'error',
						3000);
			}
		});
		// 所有的车辆grid全部设置为未选择
		order.userVehicleGrid.getSelectionModel().deselectAll();
		order.leasedTruckGrid.getSelectionModel().deselectAll();
		order.ownTruckGrid.getSelectionModel().deselectAll();
	}
});

Ext.onReady(function() {
			// 1.禁止使用全局变量,可以使用module标签生成的模块名的object对象{}
			// 用法：模块名.自定义变量
			// 2.对象都是用Ext.define定义的方式声明
			Ext.QuickTips.init();
			var orderQueryForm = Ext
					.create('Foss.order.orderHandle.orderHandleQueryForm');
			var orderResultGrid = Ext
					.create('Foss.order.orderHandle.orderHandleResultGrid');
			var carTab = Ext.create('Foss.order.orderHandle.VehicleTab');
			order.queryForm = orderQueryForm;
			order.resultGrid = orderResultGrid;
			order.carTab = carTab;
			order.mainPanelId = 'T_order-orderHandleIndex_content';
			Ext
					.create(
							'Ext.panel.Panel',
							{
								id : order.mainPanelId,
								cls : "panelContentNToolbar",
								bodyCls : 'panelContentNToolbar-body',
								layout : 'auto',
								// 校验受理是否合法
								acceptValidate : function() {
									if (order.resultGrid.getSelectionModel()
											.hasSelection()) {
										// 所选择的订单
										var record = order.resultGrid
												.getSelectionModel()
												.getSelection();
										// 校验是否失败标识
										var errorFlag = true;
										// 所选择运单的总重量
										var weight = 0;
										// 所选择运单的总体积
										var volume = 0;
										Ext.each(record,function(item, index,
																allItems) {
															// 判断建议小区是否为空(车队为约车部门时可为空)
															if(item.get('fleetIsSaleDept')=='N'&&Ext.isEmpty(item.get('smallZoneNameSuggested')) === true){
																	Ext.ux.Toast.msg(order.orderHandle.i18n('pkp.order.orderHandle.warning'), order.orderHandle.i18n('pkp.order.orderHandle.smallZoneNotNull'), 
																			'error', 4000);
																	errorFlag = false;
																	return false;
															}
															// 判断建议车辆和司机是否为空
															if (Ext.isEmpty(item.get('vehicleNoSuggested')) === true
																	&& Ext.isEmpty(item.get('driverNameSuggested')) === true) {
																Ext.ux.Toast.msg(
																				order.orderHandle.i18n('pkp.order.orderHandle.warning'),
																				order.orderHandle.i18n('pkp.order.orderHandle.assignVehicle',[item.get('orderNo')]),
																				'error',
																				3000);
																errorFlag = false;
																weight += item.get('weight');
																volume += item.get('volume');
																return false;
															}
															// 判断
															else if(Ext.isEmpty(item.get('vehicleNoSuggested')) === true
																	|| Ext.isEmpty(item.get('driverNameSuggested')) === true){
																Ext.ux.Toast.msg(
																		order.orderHandle.i18n('pkp.order.orderHandle.warning'),
																		order.orderHandle.i18n('pkp.order.orderHandle.notBundle'),
																		'error',
																		3000);
																errorFlag = false;
																return false;
															}
															// 判断
															else if(Ext.isEmpty(item.get('smallZoneNameSuggested')) === true){
																	Ext.ux.Toast.msg(order.orderHandle.i18n('pkp.order.orderHandle.warning'), order.orderHandle.i18n('pkp.order.orderHandle.smallZoneNotNull'), 
																			'error', 4000);
																	errorFlag = false;
																	return false;
															}		
														});
									} else {
										Ext.ux.Toast.msg(order.orderHandle.i18n('pkp.order.orderHandle.warning'),
												order.orderHandle.i18n('pkp.order.orderHandle.chooseOrder'), 'error',
												3000);
										errorFlag = false;
										return false;
									}
									return errorFlag;
								},
								// 受理订单
								acceptOrder : function() {
									// 所选择的订单
										var record = order.resultGrid
												.getSelectionModel()
												.getSelection();
										// 拼装Json对象
										var params = new Array();
										// 待改接订单处理结果与上次相同，警告
										var warning = new Array();
										Ext.each(record, function(item, index,
												allItems) {
											if(Ext.Date.format(new Date(),'Y-m-d')>=Ext.Date.format(new Date(item.get('earliestPickupTime')),'Y-m-d')) {
												var pickupTime = Ext.Date.format(new Date(item.get('earliestPickupTime')),'H:i') + '-' +  Ext.Date.format(new Date(item.get('latestPickupTime')),'H:i');
												var owsPickupTime = Ext.Date.format(new Date(item.get('earliestPickupTime')),'Y-m-d H:i') + '-' +  Ext.Date.format(new Date(item.get('latestPickupTime')),'Y-m-d H:i');
												params.push({
													id : item.get('id'),//订单id
													orderNo : item.get('orderNo'),// 订单号
													orderType : item.get('orderType'),// 订单类型
													originOrderStatus : item.get('orderStatus'),// 订单状态
													isPda : item.get('isPda'),// 是否发送PDA
													isSms : item.get('isSms'),// 是否发送短信
													isCustomer : item.get('isCustomer'),// 是否发送给客户
													delGPS : item.get('delGPS'),// 14.7.23 gcl AUTO-195
													driverName : item.get('driverNameSuggested'),// 司机姓名
													driverCode : item.get('driverCodeSuggested'),// 司机编码
													vehicleNo : item.get('vehicleNoSuggested'),// 车牌号
													driverMobile : item.get('driverMobile'),// 司机手机
													deviceNo : item.get('deviceNo'),// PDA设备号
													pickupRegionCode : item.get('smallZoneCodeSuggested'), // 接送货小区编码 gcl 14.7.29 AUTO-212
													pickupRegionName : item.get('pickupRegionName'),
													weight : item.get('weight'),  // 重量
													volume : item.get('volume'), // 体积
													customerMobile : item.get('mobile'),
													tel : item.get("tel"),
													customerName : item.get("customerName"),
													pickupAddress : item.get('pickupProvince')+item.get('pickupCity')+item.get('pickupCounty')+item.get('pickupElseAddress'),
													salesDepartmentName : item.get('salesDepartmentName'),
													salesDepartmentCode : item.get('salesDepartmentCode'),
													orderNotes : item.get('orderNotes'),
													pickupTime : pickupTime,
													goodsQty : item.get('goodsQty'),
													goodsPackage : item.get('goodsPackage'),
													smallZoneCodeActual : item.get('smallZoneCodeSuggested'),
													orderSource : item.get('orderSource'),  //订单来源
													pickupCity : item.get('pickupCity'),  //出发城市
													productCode : item.get('productCode'),
													owsPickupTime : owsPickupTime 
												});
												if(item.get('orderStatus') === 'AGAIN_PICKUP'){
													if(item.get('driverCodeSuggested') == item.get('driverCode') && item.get('vehicleNoSuggested') == item.get('vehicleNo')){
														warning.push(item.get('orderNo'));
													}
												}
											}else {
												Ext.ux.Toast.msg(
														order.orderHandle.i18n('pkp.order.orderHandle.warning'),
														'非当日订单['+item.get('orderNo')+']不予受理!',
														'error',
												3000);
											}
										});
										if(params.length>0) { 
											if(!Ext.isEmpty(warning)){
												Ext.Msg.confirm(order.orderHandle.i18n('pkp.order.orderHandle.warning'),'以下订单处理结果与上次相同：'+ warning.join('，') + ' 确认要提交么？',function(btn){
													if(btn === 'yes'){
														order.requestAcceptOrder(params);
													}
												} );
											} else {
												order.requestAcceptOrder(params);
											}
										}
								},
								items : [
										orderQueryForm,
										orderResultGrid,
										carTab,
										{
											border : false,
											xtype : 'container',
											columnWidth : 1,
											layout : 'column',
											defaults : {
												margin : '5 0 5 0',
											},
											items : [
													{
														border : false,
														columnWidth : .92,
														html : '&nbsp;'
													},
													{
														columnWidth : .08,
														xtype : 'button',
														cls : 'yellow_button',
														disabled : !order.orderHandle.isPermission('orderhandleindex/orderhandleindexacceptbutton'),
														hidden : !order.orderHandle.isPermission('orderhandleindex/orderhandleindexacceptbutton'),
														text : order.orderHandle.i18n('pkp.order.orderHandle.accept'),
														handler : function() {
															// 受理订单
															if(Ext.getCmp(order.mainPanelId).acceptValidate()){
																Ext.getCmp(order.mainPanelId).acceptOrder();
															}
														}
													} ]
										} ],
								renderTo : 'T_order-orderHandleIndex-body'
							})
		});
/**
 * 车辆暂停 订单中 设置预处理车为空
 * @param {} order 
 * @param {} index  radio序列
 * @param {} vehicleType     车辆类型
 */
order.vehicleSuccessFun=function(order,index,vehicleType){
	var activeTab = order.carTab.getActiveTab();
	if(activeTab.isXType('tabpanel')){
		activeTab.getActiveTab().down('grid').getSelectionModel().deselect(index);//取消选中;
	} else {
		activeTab.getSelectionModel().deselect(index);
	}
	// 所选择的订单
	var selectRecord = order.resultGrid.getSelectionModel().getSelection();
	Ext.each(selectRecord, function(item, index, allItems){
		item.set('vehicleNoSuggested','');
		item.set('driverNameSuggested','');
		item.set('driverCodeSuggested','');
		item.set('driverMobile','');
//		item.set('isPda',record.get('isPdaBundle'));
		item.set('isPda','');
		if(vehicleType!=3){
			item.set('pickupRegionName','');
			item.set('smallZoneCodeSuggested','');
			item.set('smallZoneNameSuggested','');
		}
		item.set('isSms','');
	});
}
order.vehicleFailFun=function(order,record,vehicleType){
	// 所选择的订单
	var selectRecord = order.resultGrid.getSelectionModel().getSelection();
	Ext.each(selectRecord, function(item, index, allItems){
		item.set('vehicleNoSuggested',record.get('vehicleNo'));
		item.set('driverNameSuggested',record.get('driverName'));
		item.set('driverCodeSuggested',record.get('driverCode'));
		item.set('driverMobile',record.get('driverMobile'));
//		item.set('isPda',record.get('isPdaBundle'));
		item.set('isPda','Y');
		if(vehicleType!=3){
			item.set('pickupRegionName',record.get('ownedZoneName'));
			item.set('smallZoneCodeSuggested',record.get('ownedZoneCode'));
			item.set('smallZoneNameSuggested',record.get('ownedZoneName'));
		}
		// 如果PDA未绑定，设置短信发送
		if(record.get('isPdaBundle') === 'N'){
			item.set('isSms','Y');
		};
	});
}
/**
 * Ajax请求--json
 * @param {} vehicleno  车牌号
 * @param {} successFn 成功回调服务
 * @param {} failFn    失败回调服务
 */
order.requestJsonAjax = function(vehicleno,successFn,failFn)
{
	var params = {
			'vehicleVo' : {
				'ownTruckConditionDto' : {
					'vehicleNo' : vehicleno
				}
			}
	};
	Ext.Ajax.request({
		url : order.realPath('queryVehicleNoStatus.action'),
		async : true,
		jsonData : params,
		success : function(response) {
			var json = Ext.decode(response.responseText);
			if (json.success) {
				var status = json.vehicleVo.gpsUrl;
				if("1"==status){
					Ext.Msg.confirm(order.orderHandle.i18n('pkp.order.orderHandle.syswarning'), order.orderHandle.i18n('pkp.order.orderHandle.vehicleNoStop'), function(btn,text){
						if(btn!="yes"){
							successFn(status);//record.set('vehicleNoSuggested',"");
						} else {
							failFn(status);
						}
					});
				} else {
					failFn(status);
				}
			} else {
				failFn(status);
			}
		},
		exception : function(response) {
			var result = Ext.decode(response.responseText);
			Ext.ux.Toast.msg(order.orderHandle.i18n('pkp.order.orderHandle.warning'), result.message, 'error',
					3000);
		}
	});
};