/**
 * @author 038590-foss-wangfei
 * page ：处理快递订单
 */
Ext.apply(order.expressOrderHandle, {
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
//		if(Ext.isEmpty(order.expressOrderHandle.vehicleTypeList)){
//			return null;
//		} else {
//			var json = {
//					fields : [ 'vehicleLengthName','vehicleLengthCode' ],
//					data : order.expressOrderHandle.vehicleTypeList
//			};
//			return Ext.create('Ext.data.Store', json);
//		}
//	},
	dateFormat : function(date){
		return Ext.Date.format(new Date(date),'H:i');
	}
});


(function() {
//	if(FossUserContext.getCurrentDept().transDepartment == 'Y' 
//		|| FossUserContext.getCurrentDept().dispatchTeam == 'Y'
//		|| FossUserContext.getCurrentDept().transTeam == 'Y'){
//		// 车队
//		order.expressOrderHandle.dept = 'N';
//	} else {
//		// 营业部
//		order.expressOrderHandle.dept = 'Y';
//	}
	order.expressOrderHandle.orderStatusStore =FossDataDictionary.getDataDictionaryStore('PKP_ORDER_STATUS', null);
	order.expressOrderHandle.orderStatusStore.removeAt(order.expressOrderHandle.orderStatusStore.find('valueCode','DISPATCH_VEHICLE'));
	order.expressOrderHandle.orderStatusStore.removeAt(order.expressOrderHandle.orderStatusStore.find('valueCode','PDA_ACCEPT'));
	order.expressOrderHandle.orderStatusStore.removeAt(order.expressOrderHandle.orderStatusStore.find('valueCode','PICKUPING'));
	order.expressOrderHandle.orderStatusStore.removeAt(order.expressOrderHandle.orderStatusStore.find('valueCode','RETURN'));
	order.expressOrderHandle.orderStatusStore.removeAt(order.expressOrderHandle.orderStatusStore.find('valueCode','PICKUP_FAILURE'));
	order.expressOrderHandle.orderStatusStore.removeAt(order.expressOrderHandle.orderStatusStore.find('valueCode','WAYBILL'));
	order.expressOrderHandle.orderStatusStore.removeAt(order.expressOrderHandle.orderStatusStore.find('valueCode','CANCEL'));
	order.expressOrderHandle.orderTypeStore = FossDataDictionary.getDataDictionaryStore('PKP_ORDER_TYPE',null,{'valueCode' : null, 'valueName' : '全部'})
	//order.expressOrderHandle.productCode = 'PACKAGE';
	order.expressOrderHandle.productCode_none = 'N/A';
	Ext.Ajax.request({
		url : order.realPath('queryCountyCodes.action'),
		async : false,
		success : function(response) {
			var json = Ext.decode(response.responseText);
			if(Ext.isEmpty(json.countyCodes)){
				Ext.apply(order, {
					countyCodes : order.expressOrderHandle.productCode_none
				});
			} else {
				Ext.apply(order, {
					countyCodes : json.countyCodes
				});
			}
		}
	});
//	if(order.expressOrderHandle.dept != 'Y'){
//		// 获得当前组织对应的车队
//		Ext.Ajax.request({
//			url : order.realPath('querySuperOrg.action'),
//			async : false,
//			success : function(response) {
//				var json = Ext.decode(response.responseText);
//				// 如果找不到对应的顶级车队
//				if(Ext.isEmpty(json.fleetCode)){
//					// 判定为营业部，权限不放大
//					order.expressOrderHandle.dept = 'Y';
//				}
//				Ext.apply(order, {
//					fleetCode : json.fleetCode
//				});
//			},
//			exception : function(response) {
//				var result = Ext.decode(response.responseText);
//				Ext.ux.Toast.msg(order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.warning'), result.message, 'error', 3000);
//			}
//		});
//		// 获得车队下的车队组
//		Ext.Ajax.request({
//			url : order.realPath('querySubOrg.action'),
//			async : false,
//			jsonData :{fleetCode : order.fleetCode},
//			success : function(response) {
//				var json = Ext.decode(response.responseText);
//				Ext.apply(expressOrder, {
//					fleetCode : json.dispatchOrderConditionVo.dispatchOrderConditionDto.serviceFleetList
//				});
//			},
//			exception : function(response) {
//				var result = Ext.decode(response.responseText);
//				Ext.ux.Toast.msg(order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.warning'), result.message, 'error', 3000);
//			}
//		});
//	} else {
	Ext.apply(order.expressOrderHandle, {
		fleetCode : FossUserContext.getCurrentDept().code
	});
//	};
	// 获得车型
//	Ext.Ajax.request({
//		url : order.realPath('queryVehcileType.action'),
//		async : false,
//		success : function(response) {
//			var json = Ext.decode(response.responseText);
//			Ext.apply(expressOrder, {
//				vehicleTypeList : json.dispatchOrderConditionVo.vehicleTypeList
//			});
//		},
//		exception : function(response) {
//			var result = Ext.decode(response.responseText);
//			Ext.ux.Toast.msg(order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.warning'), result.message, 'error', 3000);
//		}
//	});
})();


// 订单结果Model
Ext.define('Foss.order.expressOrderHandle.expressOrderHandleResultModel', {
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
		// 建议小区
		name : 'smallZoneNameSuggested'
	}, {
		// 建议小区
		name : 'smallZoneCodeSuggested'
	}, {
		// AUTO-195
		name : 'delGPS'
	}, {
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
		name : 'orderReturnReason'
	}, {
		name : 'assignedRecord'
	},{
		name : 'orderSource'
	},{
		name : 'productCode'
	},{
		name : 'startDate',
		type:'Date'	
	},{
		name : 'endDate',
		type:'Date'	
	},{
		name : 'productCode'
	},{
		//订单类型
		name : 'serviceType'
	}]
});
// 订单结果Store
Ext.define('Foss.order.expressOrderHandle.expressOrderHandleResultStore', {
	extend : 'Ext.data.Store',
	autoLoad : false,
	model : 'Foss.order.expressOrderHandle.expressOrderHandleResultModel',
	pageSize : 10,
	proxy : {
		type : 'ajax',
		actionMethods : 'POST',
		url : order.realPath('queryExpressOrder.action'),
		reader : {
			type : 'json',
			root : 'dispatchOrderVo.orderList',
			totalProperty : 'totalCount'
		}
	},
	listeners : {
		beforeload : function(store, operation, eOpts) {
			var queryForm = order.expressOrderHandle.queryForm.getForm();
			if (queryForm != null) {
				var queryParams = queryForm.getValues();
//					districtList = queryForm.findField('districtList').getValue(),
//					smallZoneList = queryForm.findField('smallZoneList').getValue(),
//					businessAreaList = queryForm.findField('businessAreaList').getValue(),
//					salesDepartmentCodes = queryForm.findField('salesDepartmentCode').getValue();
//				if(order.expressOrderHandle.dept != 'Y'){
//					var params = {
//							'dispatchOrderConditionVo.dispatchOrderConditionDto.orderNo' : queryParams.orderNo,
//							'dispatchOrderConditionVo.dispatchOrderConditionDto.vehicleType' : queryParams.vehicleType,
//							'dispatchOrderConditionVo.dispatchOrderConditionDto.orderStatus' : queryParams.orderStatus,
//							'dispatchOrderConditionVo.dispatchOrderConditionDto.orderType' : queryParams.orderType,
//							'dispatchOrderConditionVo.dispatchOrderConditionDto.fleetCode' : order.fleetCode
//					};
//				} else {
//					var params = {
//							'dispatchOrderConditionVo.dispatchOrderConditionDto.orderNo' : queryParams.orderNo,
//							'dispatchOrderConditionVo.dispatchOrderConditionDto.vehicleType' : queryParams.vehicleType,
//							'dispatchOrderConditionVo.dispatchOrderConditionDto.orderStatus' : queryParams.orderStatus,
//							'dispatchOrderConditionVo.dispatchOrderConditionDto.orderType' : queryParams.orderType
//					};
//				}
				
				var grid = order.expressOrderHandle.queryForm.getExpressOrderAreaGrid(), 
					records = grid.getSelectionModel().getSelection(),expressOrderCountyCodes = '';
				if (records.length > 0) {
					for(var i = 0;i < records.length; i ++){
						expressOrderCountyCodes += records[i].get("expressOrderCountyCode") + ",";
					}
				}
				var params = {
					'dispatchOrderConditionVo.dispatchOrderConditionDto.orderNo' : queryParams.orderNo,
					'dispatchOrderConditionVo.dispatchOrderConditionDto.orderStatus' : queryParams.orderStatus,
					// 最小行政区域code
					'dispatchOrderConditionVo.dispatchOrderConditionDto.expressOrderAreas' : expressOrderCountyCodes
				};
				Ext.apply(operation,{
					params : params
				});
			}
		}
	}
});
//订单所属区域明显MODEL
Ext.define('Foss.order.expressOrderHandle.ExpressOrderAreaModel', {
	extend : 'Ext.data.Model',
	fields : [{
				name : 'expressOrderArea',
				type : 'string'
			}, {
				name : 'expressOrderAreaCode',
				type : 'string'
			}, {
				name : 'expressOrderCountyCode',
				type : 'string'
			}
	]
});

// 订单查询Form
Ext.define('Foss.order.expressOrderHandle.expressOrderHandleQueryForm', {
	id : 'Foss_order_expressOrderHandle_expressOrderHandleQueryForm_Id',
	extend : 'Ext.form.Panel',
	cls:'autoHeight',
	bodyCls:'autoHeight',	
	collapsible: true,
	title: order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.queryCondition'),
	frame:true,
	layout : {type : 'table', columns: 4},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.items  = [{
			fieldLabel : order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.orderNo'),
			name : 'orderNo',
			xtype : 'textfield'
		},{
			fieldLabel : order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.orderStatus'),
			name : 'orderStatus',
			xtype : 'combobox',
			store : order.expressOrderHandle.orderStatusStore,
			displayField : 'valueName',
			valueField : 'valueCode',
			queryMode:'local',
			triggerAction:'all'
	   	},{
			xtype: 'container',
			border : false,
			columnWidth:1,
			html: '&nbsp;'
		}, {
			xtype: 'container',
			border : false,
			columnWidth:1,
			html: '&nbsp;'
		}, {
	   		xtype:'linkregincombselector',
	   		type : 'P-C-C',
	   		allowBlank:false,
			nationIsBlank:true,
			provinceIsBlank:false,
			cityIsBlank:false,
			areaIsBlank:false,
		    colspan : 3,
//		    allowBlank:false,
		    id : 'Foss_order_expressOrderHandle_linkregincombselector_Id',
		    fieldLabel : order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.area')
		}, {
			text : '新增',
			margin:'0 0 0 0',
			width: 70,
			xtype : 'button',
			disabled : !order.expressOrderHandle.isPermission('expressOrderHandleindex/expressOrderHandleindexquerybutton'),
			hidden : !order.expressOrderHandle.isPermission('expressOrderHandleindex/expressOrderHandleindexquerybutton'),
			handler : function() {
				var form = order.expressOrderHandle.queryForm, 
					linkregin = form.items.items[4].items;
					grid = form.getExpressOrderAreaGrid(), areas = new Array();
				var area;
//				var nationCode = linkregin.items[0].getValue(), nationName = linkregin.items[0].rawValue;
				var proCode = linkregin.items[0].getValue(), proName = linkregin.items[0].rawValue;
				var cityCode = linkregin.items[1].getValue(), cityName = linkregin.items[1].rawValue;
				var countyCode = linkregin.items[2].getValue(), countyName = linkregin.items[2].rawValue;
				if (Ext.isEmpty(proCode) || Ext.isEmpty(cityCode) || Ext.isEmpty(countyCode)) {
					Ext.ux.Toast.msg(order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.warning'), '请先选择订单所属区域省市区！', 'error', 3000);
					return;
				}
				var expressOrderArea = proName + cityName + countyName, 
					expressOrderAreaCode = proCode + '_' + cityCode + '_' + countyCode,
					expressOrderCountyCode = countyCode;
				var oldStorelen = grid.getStore().data.length;
				if (oldStorelen > 0) {
					for (var len = 0; len < oldStorelen; len ++) {
						if (expressOrderArea == grid.getStore().data.items[len].data.expressOrderArea) {
							Ext.ux.Toast.msg(order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.warning'), '该区域已经新增！', 'error', 3000);
							return;
						}
						area = {
							'expressOrderArea' : grid.getStore().data.items[len].data.expressOrderArea,
							'expressOrderAreaCode' : grid.getStore().data.items[len].data.expressOrderAreaCode,
							'expressOrderCountyCode' : grid.getStore().data.items[len].data.expressOrderCountyCode
						}
						areas.push(area);
					}
				}
				area = {
					'expressOrderArea' : expressOrderArea,
					'expressOrderAreaCode' : expressOrderAreaCode,
					'expressOrderCountyCode' : expressOrderCountyCode
				}
				areas.push(area);
				grid.getStore().loadData(areas);
				// 清空区域
				for (var i = 0; i < 4; i ++) {
					linkregin.items[i].setValue('');
					linkregin.items[i].rawValue = '';
				}
			}
		}, {xtype : 'container',
			colspan : 3,
			items : [me.getExpressOrderAreaGrid()]
		}, {
			text : '删除',
			margin:'-40 0 0 0',
			width: 70,
			xtype : 'button',
			hidden : !order.expressOrderHandle.isPermission('expressOrderHandleindex/expressOrderHandleindexquerybutton'),
			handler : function() {
				var form = order.expressOrderHandle.queryForm, grid = form.getExpressOrderAreaGrid();
				var rowObjs = grid.getSelectionModel().getSelection();
				if (rowObjs.length > 0) {
					grid.getStore().remove(rowObjs);
					grid.getView().refresh()
				} else {
					Ext.ux.Toast.msg(order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.warning'), '请先选择订单所选区域列表行', 'error', 3000);  //提示', '请选择您要清空的运单！
				} 
			}
		}];
		setInterval(function() {
			Ext.Ajax.request({
				url : order
						.realPath('queryExpressOrder.action?'
								+ 'dispatchOrderConditionVo.dispatchOrderConditionDto.orderStatus=NONE_HANDLE'),
				async : true,
				reader : {
					type : 'json',
					root : 'dispatchOrderVo.orderList',
					totalProperty : 'totalCount'
				},
				success : function(response) {
					var json = Ext.decode(response.responseText);
					var length = json.dispatchOrderVo.orderList;
					// 判断orderList集合中是否有值，如果有则弹窗提醒。
					if (length != null) {
						var popWin = window.open('','_top','alwaysRaised =yes,height=30,width=90,height=no,menubar =no,scrollbars =no,titlebar =no,toolbar =no,z-look=yes'); // 获取弹出窗口对象，以便进行操作 
						popWin .focus();
						alert('您有订单未处理，请及时处理');
					}
				}
			});
		}, 300000);
		me.buttons = [{
			text : order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.reset'),
			handler : function() {
				var me = this;
				var form = me.up('form');
				form.getForm().reset();
				form.getExpressOrderAreaGrid().selModel.deselectAll();	
			}
		},'->', {
			cls : 'yellow_button',
			text : order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.query'),
			plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin', {
				seconds: 3
			}),
			hidden : !order.expressOrderHandle.isPermission('expressOrderHandleindex/expressOrderHandleindexquerybutton'),
			handler : function() {
//				if (order.expressOrderHandle.queryForm.getForm().isValid()) {
				order.expressOrderHandle.pagingBar.moveFirst();
				order.expressOrderHandle.userVehicleGrid.getStore().load();
//				} else {
//					Ext.ux.Toast.msg(order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.warning'), '请输入查询条件！', 'error', 3000);
//				}
			}
		}]
		me.callParent([cfg]);
	},
	// 订单所属区域列表
	expressOrderAreaGrid : null,
	getExpressOrderAreaGrid : function() {
		if (this.expressOrderAreaGrid == null) {
			this.expressOrderAreaGrid = Ext.create('Ext.grid.Panel', {
				columnWidth : 1,
				autoScroll : true,
				selModel : Ext.create('Ext.selection.CheckboxModel'),
				height : 120,
				width : 300,
				store : Ext.create('Ext.data.Store', {
					model : 'Foss.order.expressOrderHandle.ExpressOrderAreaModel',
					proxy : {
						type : 'memory',
						reader : {
							type : 'json'
						}
					}
				}),
				columns : [{
					xtype : 'gridcolumn',
					dataIndex : 'expressOrderArea',
					flex : 1,
					text : '订单所属区域'
				}, {
					dataIndex : 'expressOrderAreaCode',
					hidden : true
				}, {
					dataIndex : 'expressOrderCountyCode',
					hidden : true
				}]
			});
			}
			return this.expressOrderAreaGrid;
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
Ext.define('Foss.order.expressOrderHandle.expressOrderHandleModel', {
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

Ext.define('Foss.order.expressOrderHandle.RejectOrderForm', {
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
			boxLabel : order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.unknowAddress'),
			name : 'rejectReason',
			inputValue : order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.unknowAddress')
		}, {
			boxLabel : order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.else'),
			name : 'rejectReason',
			inputValue : order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.else'),
			checked : true
		} ],
		listeners : {
			change : function(field, newValue, oldValue, eOpts) {
				var t = field.nextSibling();
				if (newValue.rejectReason == order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.else')) {
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
					plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin', {
						seconds: 3
					}),
					text : order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.confirm'),//确定
					margin : '0 0 0 250',
					handler : function() {
						var me = this;
						if(me.up('form').down('radiogroup').getChecked()[0].getGroupValue()== order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.else') && Ext.isEmpty(me.up('form').down('textfield').getValue())){
							Ext.ux.Toast.msg(order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.warning'), order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.requireInput'), 'error',
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
							url : order.realPath('rejectExpressOrder.action'),
							async : true,
							jsonData : params,
							success : function(response) {
								var json = Ext.decode(response.responseText);
								if (json.success) {
									Ext.MessageBox.alert(order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.warning'), json.message,
											function() {
												me.up('window').close();
												order.expressOrderHandle.resultGrid.store.load();
											});
								}
							},
							exception : function(response) {
								var result = Ext.decode(response.responseText);
								Ext.ux.Toast.msg(order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.warning'), result.message, 'error',
										3000);
							}
						});
					}
				}, {
					xtype : 'button',
					text : order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.return'),//返回
					margin : '0 0 0 20',
					handler : function() {
						var me = this;
						me.up('window').close();
					}
				} ]
	} ]
});

// 退回原因
Ext.define('Foss.order.expressOrderHandle.RejectOrderWindow', {
	extend : 'Ext.window.Window',
	title : order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.reason'), // 退回原因
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
				.create('Foss.order.expressOrderHandle.expressOrderHandleModel');
		reasonModel.set('id', me.orderId);
		reasonModel.set('orderNo',me.orderNo);
		reasonModel.set('originOrderStatus', me.orderStatus);
		reasonModel.set('orderType',me.orderType);
		var rejectOrderForm = Ext.create(
				'Foss.order.expressOrderHandle.RejectOrderForm', {
					record : reasonModel
				});
		rejectOrderForm.loadRecord(reasonModel);
		me.items = rejectOrderForm;
		me.callParent();
	}
});

// 预处理建议表单Form
Ext.define('Foss.order.expressOrderHandle.PreprocessForm', {
	extend : 'Ext.form.Panel',
	defaults : {
		width : 240
	},
	items : [{
//		fieldLabel : order.orderHandle.i18n('pkp.order.orderHandle.smallZone'),//定人定区
		fieldLabel : '收派接货小区',
		name : 'smallZoneNameSuggested',
		xtype : 'dynamicexpressSmallzonecombselector',
		regionType:'PK',
//		active:'Y',
//		type : 'ORG',
		showContent : '{regionName}&nbsp;&nbsp;&nbsp;{regionCode}'
//		listeners : {
//			'change' : function(field, event, eOpts) {
//				var form = field.up('form').getForm(),
//					vehicleNo = form.findField('vehicleNoSuggested');
//				if (field.value != '' && field.value != null) {
//					vehicleNo.setReadOnly(false);
//					vehicleNo.getStore().un('beforeload');
//					vehicleNo.getStore().on('beforeload', function(store,operation,eOpts) {
//						var searchParams = operation.params;
//						if (Ext.isEmpty(searchParams)) {
//							searchParams = {};
//							Ext.apply(operation, {
//								params : searchParams
//							});
//						}
//						delete searchParams['truckVo.truck.loopOrgCode'];
//						searchParams['truckVo.truck.reginName'] = field.rawValue;
//					});
//					vehicleNo.getStore().load();
//					vehicleNo.expand();
//				}
//				else
//				{
//					vehicleNo.setValue('');
//				}
//			}
//		}
	}, {
		fieldLabel : order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.vehicleNo'),//车牌号
		name : 'vehicleNoSuggested',
		xtype : 'commonOldExpressEmpselector',
		districtCodes : order.countyCodes,
        myQueryParam : 'vehicleNo',
        active : 'Y',
        showContent : '{vehicleNo}&nbsp;&nbsp;&nbsp;{empName}',
    	listeners : {
			select : function(combo, record, eOpts){
				var model = record[0];
				this.up('form').query('combobox[name="driverMobile"]')[0].setValue(model);
				this.up('form').query('combobox[name="driverNameSuggested"]')[0].setValue(model);
			}
		}
	}, {
		fieldLabel : order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.driverName'),//司机姓名
		name : 'driverNameSuggested',
		xtype : 'commonOldExpressEmpselector',      
//		myQueryParam : 'empName',
		displayField : 'empName',// 显示名称
		valueField : 'empName',// 值
		active : 'Y',
		districtCodes : order.countyCodes,
		showContent : '{empCode}&nbsp;&nbsp;&nbsp;{empName}&nbsp;&nbsp;&nbsp;{ownDeptName}',
		listeners : {
			select : function(combo, record, eOpts){
				var model = record[0];
				this.up('form').query('combobox[name="driverMobile"]')[0].setValue(model);
				this.up('form').query('combobox[name="vehicleNoSuggested"]')[0].setValue(model);
			}
		}
	}, {
		fieldLabel : order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.mobile'),
		name : 'driverMobile',
		xtype : 'commonOldExpressEmpselector',
		myQueryParam : 'mobilePhone',
		displayField : 'mobilePhone',// 显示名称
		valueField : 'mobilePhone',// 值
		active : 'Y',
		districtCodes : order.countyCodes,
		showContent : '{empName}&nbsp;&nbsp;&nbsp;{mobilePhone}',
		listeners : {
			select : function(combo, record, eOpts){
				var model = record[0];
				this.up('form').query('combobox[name="vehicleNoSuggested"]')[0].setValue(model);
				this.up('form').query('combobox[name="driverNameSuggested"]')[0].setValue(model);
			}
		}
	} ],
	fbar : [ {
		text : order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.save'),
		handler : function(){
			var me = this;
			var smallZoneCode = me.up('form').query('combobox[name="smallZoneNameSuggested"]')[0].getValue();
			var smallZoneName = me.up('form').query('combobox[name="smallZoneNameSuggested"]')[0].getRawValue();
//			if(smallZoneName == smallName){
//				smallZoneCode = smallCode;
//			}
			var vehicleNo = me.up('form').query('combobox[name="vehicleNoSuggested"]')[0].getValue();
			var comboDriver = me.up('form').query('combobox[name="driverNameSuggested"]');
			var driverModel = Ext.isEmpty(comboDriver) ? null : comboDriver[0].valueModels[0];
			var record = me.up('form').getForm().getRecord();
			record.set('vehicleNoSuggested',vehicleNo);
			if(!Ext.isEmpty(driverModel)){
				record.set('driverNameSuggested',driverModel.get('empName'));
				record.set('driverCodeSuggested',driverModel.get('empCode'));
			}
			record.set('driverMobile',me.up('form').query('combobox[name="driverMobile"]')[0].getValue());
			//14.9.23 gcl 小区为空做判断
			if(Ext.isEmpty(smallZoneCode)){
				Ext.ux.Toast.msg(order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.warning'), order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.smallZoneNotNull'), 
						'error', 4000);
				return false;
			}else{
				if(record.get('smallZoneNameSuggested')!=null&&!Ext.isEmpty(record.get('smallZoneNameSuggested'))){
					if(Ext.isEmpty(smallZoneCode)||Ext.isEmpty(smallZoneName)||smallZoneCode!=smallZoneName&&smallZoneName!=record.get('smallZoneNameSuggested')&&smallZoneCode!=record.get('smallZoneNameSuggested')){
						//14.7.23 gcl AUTO-195
						Ext.Msg.confirm(order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.syswarning'), order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.changeSmallZone'), function(btn,text){
							if(btn=="yes"){
								record.set('delGPS',"YES");
							}
						});
					}
				}
				if(Ext.isEmpty(smallZoneCode)||Ext.isEmpty(smallZoneName)||smallZoneCode!=smallZoneName){ //14.7.21 gcl AUTO-185 无修改时不重新赋值
					//14.7.23 gcl AUTO-195
					//if(record.get('smallZoneNameSuggested')!=null&&!Ext.isEmpty(record.get('smallZoneNameSuggested'))){
					//	Ext.Msg.confirm(order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.syswarning'), order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.changeSmallZone'), function(btn,text){
					//		if(btn=="yes"){
					//			record.set('delGPS',"YES");
					//		}
					//	});
					//}
					record.set('smallZoneCodeSuggested',smallZoneCode);
					record.set('smallZoneNameSuggested',smallZoneName);
				}
				//}
				//me.up('form').getForm().updateRecord(record);
				me.up('window').close();
			}
		}
	}, {
		text : order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.cancel'),
		handler : function(){
			var me = this;
			me.up('window').close();
		}
	} ]
});

Ext.define('Foss.order.expressOrderHandle.SmsWindow',{
	extend : 'Ext.window.Window',
	title : order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.sms'),
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
		fieldLabel : '短信',
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
			text : '复制',
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

//var smallCode = null;
//var smallName = null;
// 预处理建议window
Ext.define('Foss.order.expressOrderHandle.PreprocessWindow', {
	extend : 'Ext.window.Window',
	title : order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.preprocessModified'),
	modal : true,
	closeAction : 'hide',
	width : 300,
	height : 200,
	preprocessForm : null,
	getPreprocessForm : function(){
		if(this.preprocessForm == null){
			this.preprocessForm = Ext.create('Foss.order.expressOrderHandle.PreprocessForm');
		}
		return this.preprocessForm;
	},
	bindData : function(record, cellIndex, rowIndex){
		order.expressOrderHandle.preprocessForm = this.getPreprocessForm().getForm();
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
						Ext.ux.Toast.msg(order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.warning'), result.message, 'error',
								3000);
					}
				});
				
//				if(!Ext.isEmpty(component.down('form').getRecord().get('smallZoneCodeSuggested'))
//						|| !Ext.isEmpty(component.down('form').getRecord().get('smallZoneNameSuggested')))
//				{
//					smallCode = component.down('form').getRecord().get('smallZoneCodeSuggested');
//					smallName = component.down('form').getRecord().get('smallZoneNameSuggested');
//					component.down('form').query('combobox[name="smallZoneNameSuggested"]')[0].setCombValue(component.down('form').getRecord().get('smallZoneNameSuggested'),component.down('form').getRecord().get('smallZoneCodeSuggested'));
//				}
			}
		}
	}
});

Ext.define('Foss.order.expressOrderHandle.GisMap',{
	extend : 'Ext.window.Window',
	title : order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.electronicMap'),
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

// 订单结果列表
Ext.define('Foss.order.expressOrderHandle.expressOrderHandleResultGrid',{
	extend : 'Ext.grid.Panel',
	frame : true,
	collapsible : true,
	animCollapse : true,
	title : order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.orderTask'),
	cls : 'autoHeight',
	bodyCls : 'autoHeight',
	autoScroll : true,
	store : null,
	selModel : null,
	emptyText: order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.queryResultNull'),
	smsWindow: null,
	getSmsWindow: function(){
		if(this.smsWindow==null){
			this.smsWindow = Ext.create('Foss.order.expressOrderHandle.SmsWindow');
		}
		return this.smsWindow;
	},
	columns : [
//		{
//			text : '',
//			sortable : true,
//			dataIndex : 'timeStatus',
//			width : 40,
//			renderer : function(value, metaData, record) {
//				var result1 = calculateMin(record.get('earliestPickupTime'), 1), 
//					result2 = calculateMin(record.get('latestPickupTime'), 2), content = '';
//				if(record.get('orderType') == FossDataDictionary.rendererDisplayToSubmit('接货订单','PKP_ORDER_TYPE')){
//					if (result1 === -1)
//						content += '<div class="foss_icons_pkp_warning" width="20" height="18"></div>';
//					} 
//					if (result2 === 0) {
//						content += '<div class="foss_icons_pkp_serWarning" width="20" height="18"></div>';
//					}
//				return content;
//			}
//		},
		{
			xtype : 'actioncolumn',
			text : order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.operate'),
			width : 75,
			aglin : 'center',
			items : [
				{
					iconCls : 'deppon_icons_return',
					tooltip : order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.orderReject'),
					width : 10,
					disabled : !order.expressOrderHandle.isPermission('expressOrderHandleindex/expressOrderHandleindexreturnbutton'),
					handler : function(grid, rowIndex, colIndex) {
						var record = grid.getStore().getAt(rowIndex);
						// 订单状态非未处理或待改接，则不允许拒绝订单
						if (record.get('orderStatus') !== 'NONE_HANDLE' && record.get('orderStatus') !== 'AGAIN_PICKUP') {
							Ext.ux.Toast.msg(order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.warning'),
									order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.notAllowReject'),
									'error', 3000);
						} else {
							Ext.create('Foss.order.expressOrderHandle.RejectOrderWindow', {
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
					tooltip : order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.orderPosition'),
					width : 10,
					handler : function(grid, rowIndex, colIndex){
						var record = grid.getStore().getAt(rowIndex);
						var map = Ext.create('Foss.order.expressOrderHandle.GisMap');
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
					tooltip : order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.copySms'),
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
						record.data['driverName'] = record.get('driverNameSuggested');
//						if(record.get('orderType') == 'PICKUP_ORDER'){
//							record.data['pickupTime'] = order.expressOrderHandle.dateFormat(record.get('earliestPickupTime'))
//							+ '-'
//							+ order.expressOrderHandle.dateFormat(record.get('latestPickupTime'));
//						} else {
//							record.data['pickupTime'] = order.expressOrderHandle.dateFormat(record.get('latestPickupTime'));
//						}
						var params = {
								'orderHandleVo' : {
									'orderHandleDto' : record.data
								}
						}
						var smsWin = grid.getSmsWindow();
						Ext.Ajax.request({
							url : order.realPath('queryExpressSmsContent.action'),
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
								Ext.ux.Toast.msg(order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.warning'), result.message, 'error',
										3000);
							}
						});
						smsWin.show();
					}
				} ]
		},
		{
			text : order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.orderNo'),
			dataIndex : 'orderNo',
			xtype: 'ellipsiscolumn',
			width : 80
		},
		{
			text : order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.orderType'),
			xtype : 'linebreakcolumn',
			dataIndex : 'orderType',
			renderer : function(value) {
				return FossDataDictionary.rendererSubmitToDisplay(value, 'PKP_ORDER_TYPE');
			},
			flex : 1
		},
		{
			text : order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.weightVolume'),
			xtype : 'templatecolumn',
			dataIndex : 'weight',
			tpl : '{weight}KG/<br/>{volume}方',
			width : 60
		},
		{
			text : order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.pickupAddress'),
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
				return address;
			},
			width : 240
		},/**{
			text : order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.deptInfo'),
			xtype : 'linebreakcolumn',
			dataIndex : 'salesDepartmentName',
			width : 100
		},
		{
			text : order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.createDatetime'),
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
		},
		{
			text : order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.useCarTime'),
			xtype : 'linebreakcolumn',
			sortable : true,
			dataIndex : 'latestPickupTime',
		}*/
		{
			text : order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.createDatetime'),
			dataIndex : 'orderVehicleTime',
			xtype : 'ellipsiscolumn',
			width : 100,
			renderer : function(value,metaData,record,colIndex) {
				var content = Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
				return content;
			}
		},
		{
			text : order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.preprocess'), // 预处理建议
			windowClassName : 'Foss.order.expressOrderHandle.PreprocessWindow',
			xtype : 'openwindowcolumn',
			dataIndex : 'preprocess',
			renderer : function(value, meta, record) {
				var content = '';
				if(!Ext.isEmpty(record.get('vehicleNoSuggested'))){
					content += record.get('vehicleNoSuggested');
				}
				if(!Ext.isEmpty(record.get('driverNameSuggested'))){
					if(content.length>0){
						content += '<br/>/';
					}
					content += record.get('driverNameSuggested');
				}
				if(!Ext.isEmpty(record.get('smallZoneNameSuggested'))){//14.7.21 gcl AUTO-186
					if(content.length>0){
						content += '<br/>/';
					}
					content += record.get('smallZoneNameSuggested');////14.7.17 gcl AUTO-165
				}
				return content;
			},
			width: 70
		}, {
			//订单退回原因
			text : order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.orderReturnReason'),
			dataIndex : 'orderReturnReason',
			xtype: 'ellipsiscolumn',
			width : 60,
			renderer : function(value) {
				var result = FossDataDictionary.rendererSubmitToDisplay(value, 'PKP_COMPANY_REJECT_REASON');
				if(value == result){
					result = FossDataDictionary.rendererSubmitToDisplay(value, 'PKP_CUSTOMER_REJECT_REASON');
				}
				return result;
			}
		}, {
			//已分配记录
			text : order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.assignedRecord'),
			dataIndex : 'assignedRecord',
			xtype: 'ellipsiscolumn',
			width : 70
		}, {
			text : order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.orderStatus'),
			dataIndex : 'orderStatus',
			xtype : 'linebreakcolumn',
			renderer : function(value) {
				return FossDataDictionary.rendererSubmitToDisplay(value, 'PKP_ORDER_STATUS');
			},
			width : 60
		}, {
			text : order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.customerName'),
			dataIndex : 'customerName',
			xtype : 'ellipsiscolumn',
			width : 100
		}, {
			text : order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.tel'),
			dataIndex : 'tel',
			width : 80
		}, {
			text : order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.mobile'),
			dataIndex : 'mobile',
			width : 100
		},/** {
			text : order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.vehicleType'),
			xtype : 'linebreakcolumn',
			dataIndex : 'vehicleType',
			width : 60
		} ,*/ {
			text : order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.consigneeAddress'),
			xtype : 'linebreakcolumn',
			dataIndex : 'consigneeAddress',
			width : 100
		}, /**{
			text : order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.goodsPackage'),
			xtype : 'linebreakcolumn',
			dataIndex : 'goodsPackage',
			width : 100
		},*/ {
			text : order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.orderNotes'),
			xtype : 'linebreakcolumn',
			dataIndex : 'orderNotes',
			width : 100
		}],
	viewConfig : {
		stripeRows : false,
		getRowClass : function(record, rowIndex, rp, ds) {
			var result = calculateMin(record
					.get('orderVehicleTime'), 3);
			//当订单类型为12（裹裹订单）时，行背景为红色
			if(record.get('serviceType') == '12'){
				return 'order-dispatchOrder-row-deepred';
			}else if(record.get('orderStatus') == FossDataDictionary.rendererDisplayToSubmit('待改接','PKP_ORDER_STATUS')){
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
		rowBodyElement : 'Foss.order.expressOrderHandle.ExpanderFormPanel'
	} ],
	tbar : [
			{
				xtype : 'image',
				imgCls : 'foss_icons_pkp_handleOvertime'
			},
			{
				xtype : 'label',
				text : order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.timeout')
			},
			/**{
				xtype : 'image',
				imgCls : 'foss_icons_pkp_warning'
			},
			{
				xtype : 'label',
				text : order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.15minutes')
			},
			{
				xtype : 'image',
				imgCls : 'foss_icons_pkp_serWarning'
			},
			{
				xtype : 'label',
				text : order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.end15minutes')
			},*/
			{
				xtype : 'image',
				imgCls : 'foss_icons_pkp_waitToBeDisposed'
			},
			{
				xtype : 'label',
				text : order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.againOrder')
			},
			{
				xtype : 'image',
				imgCls : 'foss_icons_pkp_gougouOrder'
			},
			{
				xtype : 'label',
				text : order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.gougouOrder')
			},
			{
				xtype : 'tbspacer',
				width : 100
			},
			'->',
			{
				xtype : 'button',
				plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin', {
					seconds: 3
				}),
				text : order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.refresh'),
				hidden : !order.expressOrderHandle.isPermission('expressOrderHandleindex/expressOrderHandleindexquerybutton'),
				handler : function() {
//					if (order.expressOrderHandle.queryForm.getForm().isValid()) {
						order.expressOrderHandle.pagingBar.moveFirst();
//					} else {
//						Ext.ux.Toast.msg(order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.warning'), '请输入查询条件！',
//								'error', 3000);
//					}
				}
			} ],
	initComponent : function() {
		var me = this;
		me.store = Ext.create('Foss.order.expressOrderHandle.expressOrderHandleResultStore');
		me.selModel = Ext.create('Ext.selection.CheckboxModel',
		{
			checkOnly: true,
			listeners : {
				select : function(rowModel, record, index, eOpts) {
					var activeTab = order.expressOrderHandle.userTab.getActiveTab(), selectTruck;
//					if(activeTab.isXType('tabpanel')){
//						var selectTruck = activeTab.getActiveTab().down('grid').getSelectionModel().getSelection()[0];
//					} else {
//						var selectTruck = activeTab.down('grid').getSelectionModel().getSelection()[0];
//					}
					if (activeTab.down('grid') != null) {
						selectTruck = activeTab.down('grid').getSelectionModel().getSelection()[0];
					} else {
						selectTruck = activeTab.getSelectionModel().getSelection()[0];
					}
					if(!Ext.isEmpty(selectTruck)){
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
						record.set('preprocessId',selectTruck.get('ownedZoneCode'));
						// 营业部门
						record.set('salesDepartmentName',selectTruck.get('ownedZoneName'));
						// 营业部门编码
						record.set('salesDepartmentCode',selectTruck.get('ownedZoneCode'));
					}
					if (/**Ext.isEmpty(record.get('vehicleNoSuggested')) && */Ext.isEmpty(record.get('driverNameSuggested'))) {
						Ext.ux.Toast
						.msg(order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.warning'), 
								order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.noPreprocess'),
								'error',
								3000);
					}
//					var params = {
//							'vehicleVo' : {
//								'ownTruckConditionDto' : {
//									'vehicleNo' : record.get('vehicleNoSuggested')
//								}
//							}
//					};
//					Ext.Ajax.request({
//						url : order.realPath('queryVolumeByVehicleNo.action'),
//						async : true,
//						jsonData : params,
//						success : function(response) {
//							var json = Ext.decode(response.responseText);
//							if (json.success) {
//								var ownTruck = json.vehicleVo.ownTruckDto;
//								if(!Ext.isEmpty(ownTruck)){
//									if(ownTruck.remainingVolume < record.get('volume')){
//										Ext.ux.Toast.msg(order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.warning'), 
//												order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.vehicleVolumeWarning'),'error',
//												3000);
//									}
//								}
//							}
//						},
//						exception : function(response) {
//							var result = Ext.decode(response.responseText);
//							Ext.ux.Toast.msg(order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.warning'), result.message, 'error',
//									3000);
//						}
//					});
				}
			}
		});
		me.bbar = Ext.create('Deppon.StandardPaging', {
			store : me.store,
			plugins: 'pagesizeplugin',
			displayInfo: true
		});
		order.expressOrderHandle.pagingBar = me.bbar;
		me.callParent();
	}
});

// 订单结果列表双击展开
Ext.define('Foss.order.expressOrderHandle.ExpanderFormPanel',
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
					boxLabel : order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.pdaSend'),
					checked : true,
					margin : '10',
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
					boxLabel : order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.smsSend'),
					margin : '10',
					tipConfig : {
						title : order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.smsPreview'),
						height : 150,
						width : 220,
						trackMouse : true,
						hideDelay : 2000,
						depth : 2,
						tpl : new Ext.XTemplate(
//								'<tpl if="orderType == \'PICKUP_ORDER\'">',
								'<tpl>',
								'<p>[{orderNo}]接货：快递员你好，客户信息：{customerName}，{tel}/{mobile}，地址：{pickupProvince}{pickupCity}{pickupCounty}{pickupElseAddress}，货物信息：{goodsQty}件/{weight}公斤/{volume}方，备注：{orderNotes}。</p>',
								'</tpl>'
//								,
//								'<tpl if="orderType == \'TRANSFER_ORDER\'">',
//								'<p>{orderNo}转货： {preprocess}，货物信息：{weight}公斤/{volume}方，备注：{orderNotes}</p>',
//								'</tpl>'
								),
						buttons : [ {
							text : order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.copy'),
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
					xtype : 'container',
					items : [ {
						text : order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.accept'),
						xtype : 'button',
						plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin', {
							seconds: 3
						}),
						width: 70,
						hidden : !order.expressOrderHandle.isPermission('expressOrderHandleindex/expressOrderHandleindexacceptbutton'),
						handler : function() {
							var me = this;
							var pda = me.up('checkboxgroup').getComponent(0);
							var sms = me.up('checkboxgroup').getComponent(1);
							if (pda.getValue() === false && sms.getValue() === false) {
								Ext.ux.Toast.msg(
												order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.warning'),
												order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.chooseOneMethod'),
												'error',
												3000);
								return;
							}
							if(Ext.getCmp(order.expressOrderHandle.mainPanelId).acceptValidate()){
								Ext.getCmp(order.expressOrderHandle.mainPanelId).acceptOrder();
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
Ext.define('Foss.order.expressOrderHandle.UsedUserModel', {
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
		// 司机编码
		name : 'deviceNo',
		type : 'string'
	} ]
});

// 可用车辆Store
Ext.define('Foss.order.expressOrderHandle.UsedUserStore', {
	extend : 'Ext.data.Store',
	autoLoad : false,
	model : 'Foss.order.expressOrderHandle.UsedUserModel',
	pageSize : 10,
	proxy : {
		type : 'ajax',
		actionMethods : 'POST',
		url : order.realPath('queryExpressUsedUser.action'),
		reader : {
			type : 'json',
			root : 'vehicleVo.usedVehicleDtos',
			totalProperty : 'totalCount'
		}
	},
//	sorters : [ {
//		property : 'remainingVolume',
//		direction : 'DESC'
//	} ],
	listeners : {
		beforeload : function(store, operation, eOpts){
			var grid = order.expressOrderHandle.queryForm.getExpressOrderAreaGrid(), 
			records = grid.getSelectionModel().getSelection(),expressOrderCountyCode = '';
			if (records.length > 0) {
				for(var i = 0;i < records.length; i ++){
					expressOrderCountyCode += records[i].get("expressOrderCountyCode") + ",";
				}
			}
			Ext.apply(operation, {
				params : {
					'vehicleVo.ownTruckConditionDto.expressOrderCountyCode' : expressOrderCountyCode
				}
			});
		}
	}
});


// 外请车Model
Ext.define('Foss.order.expressOrderHandle.LeasedTruckModel', {
	extend : 'Ext.data.Model',
	fields : [ {
		name : 'vehicleNo',
		type : 'string'
	}, {
		// 司机编码
		name : 'driverCode',
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
		name : 'ownedZoneName',
		type : 'string'
	}, {
		name : 'openVehicle',
		type : 'boolean'
	} ]
});

// 外请车Store
Ext.define('Foss.order.expressOrderHandle.LeasedTruckStore', {
	extend : 'Ext.data.Store',
	autoLoad : false,
	model : 'Foss.order.expressOrderHandle.LeasedTruckModel',
	pageSize : 10,
	proxy : {
		type : 'ajax',
		actionMethods : 'POST',
		url : order.realPath('queryExpressAllUser.action'),
		reader : {
			type : 'json',
			root : 'vehicleVo.usedVehicleDtos',
			totalProperty : 'totalCount'
		}
	},
	listeners : {
		beforeload : function(store, operation, eOpts) {
			var queryForm = order.expressOrderHandle.availableUserForm,
				linkregin = queryForm.items.items[4].items;
			var countyCode = linkregin.items[2].getValue(), countyName = linkregin.items[2].rawValue;
			if (queryForm != null) {
				var queryParams = queryForm.getValues();
				Ext.apply(operation, {
					params : {
						'vehicleVo.ownTruckConditionDto.vehicleNo' : queryParams.vehicleNo,
						'vehicleVo.ownTruckConditionDto.vehicleType' : queryParams.vehicleType,
						'vehicleVo.ownTruckConditionDto.driverCode' : queryParams.driverCode,
						'vehicleVo.ownTruckConditionDto.driverMobile' : queryParams.driverMobile,
						'vehicleVo.ownTruckConditionDto.expressOrderCountyCode' : countyCode
					}
				});
			}
		}
	}
});

// 自有车查询条件Model
Ext.define('Foss.order.expressOrderHandle.OwnTruckQueryModel', {
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
Ext.define('Foss.order.expressOrderHandle.ServiceFleetModel', {
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
Ext.define('Foss.order.expressOrderHandle.ModifyVehicleModel', {
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
Ext.define('Foss.order.expressOrderHandle.ModifyVehicleForm', {
	extend : 'Ext.form.Panel',
	defaultType : 'numberfield',
	items : [ {
		fieldLabel : order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.remainingVolume'),
		allowBlank : false,
		blankText : order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.remainingVolumeNull'),
		minValue: 0,
		hideTrigger: true,
		name : 'remainingWeight'
	}, {
		fieldLabel : order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.remainingWeight'),
		allowBlank : false,
		blankText : order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.remainingWeightNull'),
		minValue: 0,
		hideTrigger: true,
		name : 'remainingVolume'
	} ],
	fbar : [
			{
				text : order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.save'),
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
												order.expressOrderHandle.userVehicleGrid.store
														.load();
											});
								}
							},
							exception : function(response) {
								var result = Ext.decode(response.responseText);
								Ext.ux.Toast.msg(order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.warning'), result.message, 'error',
										3000);
							}
						});
					} else {
						Ext.ux.Toast.msg(order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.warning'), '输入不合法，请重输！', 'error', 3000);
					}
				}
			}, {
				text : order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.cancel'),
				handler : function() {
					var me = this;
					me.up('window').close();
				}
			} ]
});

// 修改车辆窗口
Ext.define('Foss.order.expressOrderHandle.ModifyVehicleWindow', {
	extend : 'Ext.window.Window',
	title : order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.modifyVehicle'),
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
				.create('Foss.order.expressOrderHandle.ModifyVehicleModel');
		// 设置model中车牌号
		userVehicleModel.set('vehicleNo', me.vehicleNo);
		// 修改车辆表单
		var modifyVehicleForm = Ext.create(
				'Foss.order.expressOrderHandle.ModifyVehicleForm', {
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

// 可用人员查询
Ext.define('Foss.order.expressOrderHandle.UsedUserGrid', {
	extend : 'Ext.grid.Panel',
	id : 'Foss_order_expressOrderHandle_usedUserGrid_Id',
	title : order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.usedUser'),
	cls : 'autoHeight',
	bodyCls : 'autoHeight',
	store : null,
	selModel : null,
	autoScroll : true,
	modifyVehicle : null,
	emptyText: order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.queryResultNull'),
	padding : 10,
	// 定义tab的宽度
	tabConfig : {
		width : 120
	},
	// 获得修改车辆window
	getModifyVehicleWindow : function(record) {
		return Ext.create('Foss.order.expressOrderHandle.ModifyVehicleWindow', {
			vehicleNo : record.get('vehicleNo')
		});
	},
	columns : [ /**{
		xtype : 'actioncolumn',
		text : order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.driverPosition'),
		flex : 1,
		align : 'center',
		items : [ {
			iconCls : 'foss_icons_pkp_viewDriverlocation',
			tooltip : order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.driverPosition'),
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
				order.expressOrderHandle.queryGpsUrl(params);
			}
		} ]
	}, {
		xtype : 'actioncolumn',
		text : order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.modifyVehicle'),
		flex : 1,
		align : 'center',
		items : [ {
			iconCls : 'deppon_icons_edit',
			tooltip : order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.modifyVehicle'),
			width : 20,
			handler : function(grid, rowIndex, colIndex) {
				var me = this;
				// 获得选中
				var record = grid.getStore().getAt(rowIndex);
				me.up('grid').getModifyVehicleWindow(record).show();
			}
		} ]
	},*/ {
		text : order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.vehicleNo'),
		flex : 1,
		dataIndex : 'vehicleNo'
	}, {
		text : order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.driverName'),
		flex : 1,
		dataIndex : 'driverName'
	}, {
		text : order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.pdaBundle'),
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
	}/**, {
		text : order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.remainingVolume'),
		flex : 1,
		dataIndex : 'remainingVolume'
	}, {
		text : order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.remainingWeight'),
		flex : 1,
		dataIndex : 'remainingWeight'
	}*/, {
		text : order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.vehicleType'),
		flex : 1,
		dataIndex : 'vehicleType'
	}/**, {
		text : order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.netWeight'),
		flex : 1,
		dataIndex : 'netWeight'
	}, {
		text : order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.ownRegion'),
		flex : 1,
		dataIndex : 'ownedZoneName'
	}*/, {
		text : order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.mobile'),
		flex : 1,
		dataIndex : 'driverMobile'
	}, {
		text : order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.ownedZoneName'),
		flex : 1,
		dataIndex : 'ownedZoneName'
	} ],
	viewConfig: {
        enableTextSelection: true
    },
	tbar : [
			/*{
				xtype : 'combobox',
				id : 'serviceFleet',
				store : order.expressOrderHandle.getFleetStore(),
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
									order.expressOrderHandle.userVehicleGrid.getStore().load();
								},
								exception : function(response) {
									var result = Ext.decode(response.responseText);
									Ext.MessageBox.alert(order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.warning'), result.message);
								}
							});
						}
						order.expressOrderHandle.userVehicleGrid.getStore().load();
					}
				}
			},*/ '->', {
				xtype : 'button',
				text : order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.refresh'),
				plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin', {
					seconds: 3
				}),
				hidden : !order.expressOrderHandle.isPermission('expressOrderHandleindex/expressOrderHandleindexqueryavaliabletruckbutton'),
				handler : function(){
					order.expressOrderHandle.userVehicleGrid.getStore().load();
				}
			} ],
	initComponent : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.order.expressOrderHandle.UsedUserStore');
		me.selModel = Ext.create('Ext.selection.RadioModel', {
			mode : 'SINGLE',
			showHeaderCheckbox : false,
			listeners : {
				select : function(rowModel, record, index, eOpts){
					// 所选择的订单
					var selectRecord = order.expressOrderHandle.resultGrid.getSelectionModel().getSelection();
					Ext.each(selectRecord, function(item, index, allItems){
						item.set('vehicleNoSuggested',record.get('vehicleNo'));
						item.set('driverNameSuggested',record.get('driverName'));
						item.set('driverCodeSuggested',record.get('driverCode'));
						item.set('driverMobile',record.get('driverMobile'));
//						item.set('isPda',record.get('isPdaBundle'));
						item.set('isPda','Y');
						item.set('pickupRegionName',record.get('ownedZoneName'));
						// 如果PDA未绑定，设置短信发送
						if(record.get('isPdaBundle') === 'N'){
							item.set('isSms','Y');
						};
						item.set('preprocessId',record.get('ownedZoneCode'));
						// 营业部门
						item.set('salesDepartmentName',record.get('ownedZoneName'));
						// 营业部门编码
						item.set('salesDepartmentCode',record.get('ownedZoneCode'));
					});
				}
			}
		});
		me.callParent([ cfg ]);
	},
	listeners : {
		beforerender:function(component, eOpts){
			var grid = Ext.getCmp('Foss_order_expressOrderHandle_usedUserGrid_Id');
			if(order.expressOrderHandle.dept=='Y')
			{
				grid.down('#serviceFleet').setVisible(false);
			}
		}
	}
});

//Ext.define('Foss.order.expressOrderHandle.OwnTruckStore',
//{
//	extend : 'Ext.data.Store',
//	autoLoad : false,
//	model : 'Foss.order.expressOrderHandle.UsedUserModel',
//	pageSize : 10,
//	proxy : {
//		type : 'ajax',
//		actionMethods : 'POST',
//		url : order.realPath('queryOwnTruck.action'),
//		reader : {
//			type : 'json',
//			root : 'vehicleVo.ownTruckDtos',
//			totalProperty : 'totalCount'
//		}
//	},
//	listeners : {
//		beforeload : function(store, operation, eOpts) {
//			var ownTruckForm = order.expressOrderHandle.ownTruckForm;
//			if (ownTruckForm != null) {
//				var queryParams = ownTruckForm.getValues();
//				Ext
//						.apply(
//								operation,
//								{
//									params : {
//										'vehicleVo.ownTruckConditionDto.vehicleNo' : queryParams.vehicleNo,
//										'vehicleVo.ownTruckConditionDto.vehicleType' : queryParams.vehicleType,
//										'vehicleVo.ownTruckConditionDto.serviceFleetCode' : queryParams.serviceFleet,
//										'vehicleVo.ownTruckConditionDto.driverCode' : queryParams.driverCode
//									}
//								});
//			}
//		}
//	}
//});

// 自有车表单Form
//Ext.define('Foss.order.expressOrderHandle.OwnTruckForm', {
//	extend : 'Ext.form.Panel',
//	layout : 'column',
//	defaults : {
//		labelWidth : 90
//	},
//	items : [ {
//		fieldLabel : order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.vehicleNo'),
//		xtype : 'commonowntruckselector',
//		parentOrgCode : order.fleetCode,
//		columnWidth : 0.5,
//		name : 'vehicleNo'
//	}, {
//		fieldLabel : order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.vehicleType'),
//		xtype : 'commonvehicletypeselector',
//		name : 'vehicleType',
//		displayField : 'vehicleLengthName',
//		valueField : 'vehicleLengthCode',
//		columnWidth : 0.5
//	}, {
//		fieldLabel : order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.vehicleGroup'),
//		xtype : 'commonmotorcadeselector',
//		loopOrgCode : order.fleetCode,
//		fleetTypes : 'FLEET_TYPE__SHUTTLE_GOODS,TYPE__LONG_DISTANCE',
//		columnWidth : 0.5,
//		name : 'serviceFleet'
//	}, {
//		fieldLabel : order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.driverName'),
//		xtype : 'commonowndriverselector',
//		parentOrgCode : order.fleetCode,
//		columnWidth : 0.5,
//		name : 'driverCode'
//	} ],
//	fbar : [ {
//		xtype : 'button',
//		text : order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.reset'),
//		handler : function() {
//			var me = this;
//			var form = me.up('form');
//			form.getForm().reset();
//		}
//	}, '->', {
//		xtype : 'button',
//		text : order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.query'),
//		hidden : !order.expressOrderHandle.isPermission('expressOrderHandleindex/expressOrderHandleindexqueryavaliabletruckbutton'),
//		cls : 'yellow_button',
//		handler : function() {
//			if (order.expressOrderHandle.ownTruckForm.getForm().isValid()) {
//				order.expressOrderHandle.ownTruckGrid.getStore().load();
//			} else {
//				Ext.ux.Toast.msg(order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.warning'), '请输入查询条件！', 'error', 3000);
//			}
//		}
//	} ]
//});

// GPS电子地图
Ext.define('Foss.order.expressOrderHandle.GPSTruckMap',{
	extend : 'Ext.window.Window',
	title : order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.electronicMap'),
	modal : true,
	closeAction : 'hide',
	height : 500,
	width : 600,
	gpsUrl : null,
	initComponent : function(){
		var me = this;
		me.items = [{
			xtype : 'panel',
			height : 450,
			layout : 'fit',
			html : '<iframe width="100%" height="100%" FRAMEBORDER=0 SCROLLING=NO src="' + me.gpsUrl + '"></iframe>'
		}];
		me.callParent();
	}
});

// 请求后台获取GPS地址，并弹出window
Ext.apply(order.expressOrderHandle,{
	queryGpsUrl : function(params){
		Ext.Ajax.request({
			url : order.realPath('queryGpsUrl.action'),
			async : false,
			jsonData : params,
			success : function(response) {
				var json = Ext.decode(response.responseText);
				Ext.create('Foss.order.expressOrderHandle.GPSTruckMap',{
					gpsUrl : json.vehicleVo.gpsUrl
				}).show();
			},
			exception : function(response) {
				var result = Ext.decode(response.responseText);
				Ext.ux.Toast.msg(order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.warning'), result.message, 'error', 3000);
			}
		});
	}
});


// 自有车查询Grid
//Ext.define('Foss.order.expressOrderHandle.OwnTruckGrid', {
//	extend : 'Ext.grid.Panel',
//	store : null,
//	selModel : null,
//	autoScroll : true,
//	emptyText: order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.queryResultNull'),
//	columns : [ {
//		xtype : 'actioncolumn',
//		text : order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.viewMap'),
//		flex : 1,
//		align : 'center',
//		items : [ {
//			tooltip : order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.viewMap'),
//			iconCls : 'foss_icons_pkp_viewDriverlocation',
//			width : 20,
//			handler : function(grid, rowIndex, colIndex){
//				var record = grid.getStore().getAt(rowIndex);
//				var params = {
//						'vehicleVo' : {
//							'ownTruckDto' : {
//								'vehicleNo' : record.get('vehicleNo'),
//								'driverName' : record.get('driverName')
//							}
//						}
//				};
//				order.expressOrderHandle.queryGpsUrl(params);
//			}
//		} ]
//	}, {
//		text : order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.vehicleNo'),
//		dataIndex : 'vehicleNo',
//		flex : 1
//	}, {
//		text : order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.vehicleType'),
//		dataIndex : 'vehicleType',
//		flex : 1
//	}, {
//		text : order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.netVolume'),
//		dataIndex : 'netVolume',
//		flex : 1
//	}, {
//		text : order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.netWeight'),
//		dataIndex : 'netWeight',
//		flex : 1
//	}, {
//		text : order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.region'),
//		dataIndex : 'ownedZoneName',
//		flex : 1
//	} ],
//	viewConfig: {
//        enableTextSelection: true
//    },
//	initComponent : function() {
//		var me = this;
//		me.store = Ext.create('Foss.order.expressOrderHandle.OwnTruckStore');
//		me.selModel = Ext.create('Ext.selection.RadioModel', {
//			mode : 'SINGLE',
//			listeners : {
//				select : function(rowModel, record, index, eOpts){
//					// 所选择的订单
//					var selectRecord = order.expressOrderHandle.resultGrid.getSelectionModel().getSelection();
//					Ext.each(selectRecord, function(item, index, allItems){
//						item.set('vehicleNoSuggested',record.get('vehicleNo'));
//						item.set('driverNameSuggested',record.get('driverName'));
//						item.set('driverCodeSuggested',record.get('driverCode'));
//						item.set('driverMobile',record.get('driverMobile'));
////						item.set('isPda',record.get('isPdaBundle'));
//						item.set('isPda','Y');
//						item.set('pickupRegionName',record.get('ownedZoneName'));
//						// 如果PDA未绑定，设置短信发送
//						if(record.get('isPdaBundle') === 'N'){
//							item.set('isSms','Y');
//						};
//						item.set('preprocessId',record.get('ownedZoneCode'));
//					});
//				}
//			}
//		});
//		me.bbar = Ext.create('Deppon.StandardPaging', {
//			store : me.store
//		});
//		me.callParent();
//	}
//});

//// 自有车Panel
//Ext.define('Foss.order.expressOrderHandle.OwnTruckPanel', {
//	extend : 'Ext.panel.Panel',
//	title : order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.ownTruck'),
//	// 定义tab的宽度
//	tabConfig : {
//		width : 120
//	},
//	initComponent : function() {
//		var me = this;
//		order.expressOrderHandle.ownTruckForm = Ext.create('Foss.order.expressOrderHandle.OwnTruckForm',
//				{
//					flex : 3
//				});
//		order.expressOrderHandle.ownTruckGrid = Ext.create('Foss.order.expressOrderHandle.OwnTruckGrid');
//		me.items = [ {
//			xtype : 'container',
//			layout : {
//				type : 'hbox',
//				align : 'center'
//			},
//			items : [ {
//				html : '&nbsp',
//				flex : 1
//			}, order.expressOrderHandle.ownTruckForm, {
//				html : '&nbsp',
//				flex : 1
//			} ]
//		}, order.expressOrderHandle.ownTruckGrid ];
//		me.callParent();
//	}
//});

// 可用人员Form
Ext.define('Foss.order.expressOrderHandle.AvailableUserForm', {
	extend : 'Ext.form.Panel',
	layout : {type : 'table', columns: 4},
	defaults : {
		labelWidth : 50
	},
	items : [ {
		fieldLabel : order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.driverName'),
		xtype : 'commonOldExpressEmpselector', //wutao BUG:老：commonOldExpressEmpselector；新：commonExpressemployeeselector 
//		myQueryParam : 'empName',
		displayField : 'empName',// 显示名称
		valueField : 'empCode',// 值
		districtCodes : order.countyCodes,
		showContent : '{empCode}&nbsp;&nbsp;&nbsp;{empName}&nbsp;&nbsp;&nbsp;{ownDeptName}',
		active : 'Y',
		name : 'driverCode'
	}, {
		fieldLabel : order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.vehicleNo'),
		xtype : 'commonOldExpressEmpselector',
		districtCodes : order.countyCodes,
		showContent : '{vehicleNo}&nbsp;&nbsp;&nbsp;{empName}',
        myQueryParam : 'vehicleNo',
        active : 'Y',
		name : 'vehicleNo'
	}, {
		fieldLabel : order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.vehicleType'),
		xtype : 'commonvehicletypeselector',
		displayField : 'vehicleLengthName',
		valueField : 'vehicleLengthCode',
		name : 'vehicleType'
	}, {
		fieldLabel : order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.mobile'),
		xtype : 'textfield',
		name : 'driverMobile'
	}, {
   		xtype:'linkregincombselector',
   		type : 'P-C-C',
   		allowBlank:true,
		nationIsBlank:true,
		provinceIsBlank:false,
		cityIsBlank:false,
		areaIsBlank:false,
//	    allowBlank:true,
	    fieldLabel : '快递员所属行政区域',
	    labelWidth : 120,
	    colspan : 4
	}],
	fbar : [ {
		xtype : 'button',
		text : order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.reset'),
		handler : function() {
			var me = this;
			var form = me.up('form');
			form.getForm().reset();
		}
	}, '->', {
		xtype : 'button',
		plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin', {
			seconds: 3
		}),
		text : order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.query'),
		cls : 'yellow_button',
		hidden : !order.expressOrderHandle.isPermission('expressOrderHandleindex/expressOrderHandleindexqueryleasedtruckbutton'),
		handler : function() {
			var form = order.expressOrderHandle.availableUserForm,
				linkregin = form.items.items[4].items;
			var proCode = linkregin.items[0].getValue(), 
				cityCode = linkregin.items[1].getValue(), 
				countyCode = linkregin.items[2].getValue()
				codeEmpty = 0;
			if (Ext.isEmpty(proCode)) {
				codeEmpty --;
			} else {
				codeEmpty ++;
			}
			if (Ext.isEmpty(cityCode)) {
				codeEmpty --;
			} else {
				codeEmpty ++;
			}
			if (Ext.isEmpty(countyCode)) {
				codeEmpty --;
			} else {
				codeEmpty ++;
			}
			if (codeEmpty == 3 || codeEmpty == -3) {
				order.expressOrderHandle.leasedPagingBar.moveFirst();
			} else {
				Ext.ux.Toast.msg(order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.warning'), '省市区必须全部输入！', 'error', 3000);
			}
		}
	} ]
});

// 外请车查询Grid
Ext.define('Foss.order.expressOrderHandle.AvailableUserGrid', {
	extend : 'Ext.grid.Panel',
	store : null,
	selModel : null,
	autoScroll : true,
	emptyText: order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.queryResultNull'),
	columns : [ {
		text : order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.vehicleNo'),
		dataIndex : 'vehicleNo',
		flex : 1
	}, {
		text : order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.vehicleType'),
		dataIndex : 'vehicleType',
		flex : 1
	}, {
		text : order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.driverName'),
		dataIndex : 'driverName',
		flex : 1
	}, {
		text : order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.driverMobile'),
		dataIndex : 'driverMobile',
		flex : 1
	}, {
		text : order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.ownedZoneName'),
		flex : 1,
		dataIndex : 'ownedZoneName'
	}  ],
	viewConfig: {
        enableTextSelection: true
    },
	initComponent : function() {
		var me = this;
		me.store = Ext.create('Foss.order.expressOrderHandle.LeasedTruckStore');
		me.selModel = Ext.create('Ext.selection.RadioModel', {
			mode : 'SINGLE',
			showHeaderCheckbox : false,
			listeners : {
				select : function(rowModel, record, index, eOpts){
					// 所选择的订单
					var selectRecord = order.expressOrderHandle.resultGrid.getSelectionModel().getSelection();
					Ext.each(selectRecord, function(item, index, allItems){
						item.set('vehicleNoSuggested',record.get('vehicleNo'));
						item.set('driverNameSuggested',record.get('driverName'));
						item.set('driverCodeSuggested',record.get('driverCode'));
						item.set('driverMobile',record.get('driverMobile'));
						// 营业部门
						item.set('salesDepartmentName',record.get('ownedZoneName'));
						// 营业部门编码
						item.set('salesDepartmentCode',record.get('ownedZoneCode'));
						item.set('isPda','Y');
						// 如果PDA未绑定，设置短信发送
						if(record.get('isPdaBundle') === 'N'){
							item.set('isSms','Y');
						}
					});
				}
			}
		});
		me.bbar = Ext.create('Deppon.StandardPaging', {
			store : me.store,
			plugins: 'pagesizeplugin',
			displayInfo: true
		});
		order.expressOrderHandle.leasedPagingBar = me.bbar;
		me.callParent();
	}
});

// 外请车Panel
Ext.define('Foss.order.expressOrderHandle.avaiableUserPanel', {
	extend : 'Ext.panel.Panel',
	tabConfig : {
		width : 120
	},
	title : order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.availableUser'),
	initComponent : function() {
		var me = this;
		order.expressOrderHandle.availableUserForm = Ext.create(
				'Foss.order.expressOrderHandle.AvailableUserForm', {
					flex : 10
				});
		order.expressOrderHandle.availableUserGrid = Ext.create('Foss.order.expressOrderHandle.AvailableUserGrid');
		me.items = [ {
			xtype : 'container',
			layout : {
				type : 'hbox',
				align : 'center'
			},
			items : [ {
				html : '&nbsp',
				flex : 1
			}, order.expressOrderHandle.availableUserForm, {
				html : '&nbsp',
				flex : 1
			} ]
		}, order.expressOrderHandle.availableUserGrid ];
		me.callParent();
	}
});

// 查询人员Tab
//Ext.define('Foss.order.expressOrderHandle.ManualQueryTab', {
//	extend : 'Ext.tab.Panel',
//	title : order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.availableUser'),
//	frame : false,
//	bodyCls : 'autoHeight',
//	cls : 'innerTabPanel',
//	// 定义tab的宽度
//	tabConfig : {
//		width : 120
//	},
//	activeTab : 0,
//	items : [ Ext.create('Foss.order.expressOrderHandle.OwnTruckPanel'),
//			Ext.create('Foss.order.expressOrderHandle.avaiableUserPanel') ],
//	listeners : {
//		tabchange : function(tabPanel, newCard, oldCard, eOpts){
//			// 切换tabpanel后，根据类型设置grid中全部未选择
//			if(oldCard.isXType('tabpanel')){
//				oldCard.getActiveTab().down('grid').getSelectionModel().deselectAll();
//			} else {
//				oldCard.down('grid').getSelectionModel().deselectAll();
//			}
//		}
//	}
//});

// 车辆查询Tab
Ext.define('Foss.order.expressOrderHandle.UserTab', {
	extend : 'Ext.tab.Panel',
	frame : false,
	bodyCls : 'autoHeight',
	cls : 'innerTabPanel',
	activeTab : 0,
	usedUserTab : null,
	avaiableUserTab : null,
	initComponent : function() {
		var me = this;
		// 可用人员表格
		me.usedUserTab = Ext.create('Foss.order.expressOrderHandle.UsedUserGrid');
		// 赋值给order对象
		order.expressOrderHandle.userVehicleGrid = me.usedUserTab;
		// 手动查询Tab页
		me.avaiableUserTab = Ext.create('Foss.order.expressOrderHandle.avaiableUserPanel');
		// 赋值给order对象
		order.expressOrderHandle.avaiableUserTab = me.avaiableUserTab;
		// 初始化车辆查询items
		me.items = [ me.usedUserTab, me.avaiableUserTab ];
		me.callParent();
	},
	listeners : {
		tabchange : function(tabPanel, newCard, oldCard, eOpts){
			// 切换tabpanel后，根据类型设置grid中全部未选择
//			if(oldCard.isXType('tabpanel')){
//				oldCard.getActiveTab().down('grid').getSelectionModel().deselectAll();
//			} else {
//				oldCard.getSelectionModel().deselectAll();
//			}
			if (oldCard.down('grid') != null) {
				oldCard.down('grid').getSelectionModel().deselectAll();
			} else {
				oldCard.getSelectionModel().deselectAll();
			}
		}
	}
});

Ext.apply(order.expressOrderHandle,{
	requestAcceptOrder : function(params){
		// 受理订单
		Ext.Ajax.request({
			url : order.realPath('acceptExpressOrder.action'),
			async : true,
			timeout : 60000,
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
				Ext.ux.Toast.msg(order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.warning'),
						message, 'error',
						5000);
				order.expressOrderHandle.resultGrid.store.load();
			},
			exception : function(response) {
				var result = Ext.decode(response.responseText);
				Ext.ux.Toast.msg(order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.warning'),
						result.message, 'error',
						3000);
			}
		});
		// 所有的车辆grid全部设置为未选择
		order.expressOrderHandle.userVehicleGrid.getSelectionModel().deselectAll();
		order.expressOrderHandle.availableUserGrid.getSelectionModel().deselectAll();
//		order.expressOrderHandle.ownTruckGrid.getSelectionModel().deselectAll();
	}
});

Ext.onReady(function() {
			// 1.禁止使用全局变量,可以使用module标签生成的模块名的object对象{}
			// 用法：模块名.自定义变量
			// 2.对象都是用Ext.define定义的方式声明
			Ext.QuickTips.init();
			var orderQueryForm = Ext
					.create('Foss.order.expressOrderHandle.expressOrderHandleQueryForm');
			var orderResultGrid = Ext
					.create('Foss.order.expressOrderHandle.expressOrderHandleResultGrid');
			var userTab = Ext.create('Foss.order.expressOrderHandle.UserTab');
			order.expressOrderHandle.queryForm = orderQueryForm;
			order.expressOrderHandle.resultGrid = orderResultGrid;
			order.expressOrderHandle.userTab = userTab;
			order.expressOrderHandle.mainPanelId = 'T_order-expressOrderHandleIndex_content';
			Ext.create('Ext.panel.Panel',
				{
					id : order.expressOrderHandle.mainPanelId,
					cls : "panelContentNToolbar",
					bodyCls : 'panelContentNToolbar-body',
					layout : 'auto',
					// 校验受理是否合法
					acceptValidate : function() {
						if (order.expressOrderHandle.resultGrid.getSelectionModel().hasSelection()) {
							// 所选择的订单
							var record = order.expressOrderHandle.resultGrid.getSelectionModel().getSelection();
							// 校验是否失败标识
							var errorFlag = true;
							// 所选择运单的总重量
//							var weight = 0;
//							// 所选择运单的总体积
//							var volume = 0;
							//var receiveOrgCode = Ext.getCmp('Foss_order_expressOrderHandle_receiveOrgCode_Id').getValue();
							/*// 判断是否选择收入部门
							if (Ext.isEmpty(receiveOrgCode) === true) {
								Ext.ux.Toast.msg(
										order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.warning'),'收入部门必须选择',
										'error',
										3000);
								return false;
							}*/
							Ext.each(record,function(item, index,allItems) {
								// 判断建议车辆和司机是否为空
//								if (Ext.isEmpty(item.get('vehicleNoSuggested')) === true
//										&& Ext.isEmpty(item.get('driverNameSuggested')) === true) {
//									Ext.ux.Toast.msg(
//													order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.warning'),
//													order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.assignVehicle',item.get('orderNo')),
//													'error',
//													3000);
//									errorFlag = false;
//									weight += item.get('weight');
//									volume += item.get('volume');
//									return false;
//								}
								// 判断快递员是否为空
								if(Ext.isEmpty(item.get('vehicleNoSuggested')) === true || Ext.isEmpty(item.get('driverNameSuggested')) === true){
									Ext.ux.Toast.msg(
											order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.warning'),
											order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.assignVehicle1') + item.get('orderNo') + order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.assignVehicle2'),
											'error',
											3000);
									errorFlag = false;
									return false;
								}
								// 判断 DEFECT-4576
								else if(Ext.isEmpty(item.get('smallZoneCodeSuggested')) === true){
									Ext.ux.Toast.msg(order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.warning'), order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.smallZoneNotNull'), 
											'error', 4000);
									errorFlag = false;
									return false;
								}
							});
						} else {
							Ext.ux.Toast.msg(order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.warning'),
									order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.chooseOrder'), 'error',
									3000);
							errorFlag = false;
							return false;
						}
						return errorFlag;
					},
					// 受理订单
					acceptOrder : function() {
						// 所选择的订单
							var record = order.expressOrderHandle.resultGrid.getSelectionModel().getSelection();
							// 拼装Json对象
							var params = new Array();
							// 待改接订单处理结果与上次相同，警告
							var warning = new Array();/*receiveOrgCode = Ext.getCmp('Foss_order_expressOrderHandle_receiveOrgCode_Id').getValue(), 
								receiveOrgName = Ext.getCmp('Foss_order_expressOrderHandle_receiveOrgCode_Id').rawValue;*/
							Ext.each(record, function(item, index, allItems) {
								var pickupTime = Ext.Date.format(new Date(item.get('earliestPickupTime')),'H:i') + '-' +  Ext.Date.format(new Date(item.get('latestPickupTime')),'H:i');
								var owsPickupTime = Ext.Date.format(new Date(item.get('earliestPickupTime')),'Y-m-d H:i') + '-' +  Ext.Date.format(new Date(item.get('latestPickupTime')),'Y-m-d H:i');
								params.push({
									id : item.get('id'),//订单id
									orderNo : item.get('orderNo'),// 订单号
									orderType : item.get('orderType'),// 订单类型
									originOrderStatus : item.get('orderStatus'),// 订单状态
									isPda : item.get('isPda'),// 是否发送PDA
									isSms : item.get('isSms'),// 是否发送短信
									smallZoneCodeSuggested : item.get('smallZoneCodeSuggested'),// 预处理小区 14.7.21 gcl AUTO-185
									smallZoneNameSuggested : item.get('smallZoneNameSuggested'),// 预处理小区
									smallZoneCodeActual : Ext.isEmpty(item.get('smallZoneCodeSuggested'))?' ':item.get('smallZoneCodeSuggested'),// 实际处理小区 14.8.11 gcl 
									delGPS : item.get('delGPS'),// 14.7.23 gcl AUTO-195
									driverName : item.get('driverNameSuggested'),// 司机姓名
									driverCode : item.get('driverCodeSuggested'),// 司机编码
									vehicleNo : item.get('vehicleNoSuggested'),// 车牌号
									driverMobile : item.get('driverMobile'),// 司机手机
									deviceNo : item.get('deviceNo'),// PDA设备号
									pickupRegionCode : item.get('preprocessId'), // 接送货小区编码
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
									/*receiveOrgCode : receiveOrgCode,
									receiveOrgName : receiveOrgName,*/
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
							});
							if(!Ext.isEmpty(warning)){
								Ext.Msg.confirm(order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.warning'),'以下订单处理结果与上次相同：'+ warning.join('，') + ' 确认要提交么？',function(btn){
									if(btn === 'yes'){
										order.expressOrderHandle.requestAcceptOrder(params);
									}
								} );
							} else {
								order.expressOrderHandle.requestAcceptOrder(params);
							}
					},
					items : [
						orderQueryForm,
						orderResultGrid,
						userTab,
						{
							border : false,
							xtype : 'container',
							columnWidth : 1,
							layout : 'column',
							defaults : {
								margin : '5 0 5 0'
							},
							items : [
								/*{
									border : false,
									columnWidth : .7,
									html : '&nbsp;'
								},{
									xtype : 'dynamicorgcombselector', // 出发部门 这里应该使用公共选择器
									columnWidth : .3,
									name : 'receiveOrgCode',
									allowBlank : false,
									id : 'Foss_order_expressOrderHandle_receiveOrgCode_Id',
									fieldLabel : '收入部门' //出发部门// 字段标题
								},*/{
									border : false,
									columnWidth : .92,
									html : '&nbsp;'
								},{
									columnWidth : .08,
									xtype : 'button',
									cls : 'yellow_button',
									plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin', {
										seconds: 3
									}),
									hidden : !order.expressOrderHandle.isPermission('expressOrderHandleindex/expressOrderHandleindexacceptbutton'),
									text : order.expressOrderHandle.i18n('pkp.order.expressOrderHandle.accept'),
									handler : function() {
										// 受理订单
										if(Ext.getCmp(order.expressOrderHandle.mainPanelId).acceptValidate()){
											Ext.getCmp(order.expressOrderHandle.mainPanelId).acceptOrder();
										}
									}
								} ]
						} ],
					renderTo : 'T_order-expressOrderHandleIndex-body'
				})
		});