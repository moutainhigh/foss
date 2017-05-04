order.currentDeptCode = FossUserContext.getCurrentDeptCode();// 获取当前用户设置的当前部门编码
order.emp = FossUserContext.getCurrentUserEmp();// 获得当前用户对应的职员信息

(function() {
	if (FossUserContext.getCurrentDept().transDepartment == 'Y'
			|| FossUserContext.getCurrentDept().dispatchTeam == 'Y'
			|| FossUserContext.getCurrentDept().transTeam == 'Y') {
		// 车队
		order.orderVehView.dept = 'N';
	} else {
		// 营业部
		order.orderVehView.dept = 'Y';
	}
	if (order.orderVehView.dept != 'Y') {
		// 获得当前组织对应的车队
		Ext.Ajax.request({
					url : order.realPath('querySuperOrg.action'),
					async : false,
					success : function(response) {
						var json = Ext.decode(response.responseText);
						// 如果找不到对应的顶级车队
						if (Ext.isEmpty(json.fleetCode)) {
							// 判定为营业部，权限不放大
							order.orderVehView.dept = 'Y';
						}
						Ext.apply(order, {
									fleetCode : json.fleetCode
								});
					},
					exception : function(response) {
						var result = Ext.decode(response.responseText);
						Ext.ux.Toast.msg(order.orderVehView
										.i18n('pkp.order.orderHandle.warning'),
								result.message, 'error', 3000);
					}
				});
		// 获得车队下的车队组
		Ext.Ajax.request({
			url : order.realPath('querySubOrg.action'),
			async : false,
			jsonData : {
				fleetCode : order.fleetCode
			},
			success : function(response) {
				var json = Ext.decode(response.responseText);
				Ext.apply(order, {
					fleet : json.dispatchOrderConditionVo.dispatchOrderConditionDto.serviceFleetList
				});
			},
			exception : function(response) {
				var result = Ext.decode(response.responseText);
				Ext.ux.Toast.msg(order.orderVehView
								.i18n('pkp.order.orderHandle.warning'),
						result.message, 'error', 3000);
			}
		});
	} else {
		Ext.apply(order, {
					fleetCode : FossUserContext.getCurrentDept().code
				});
	};
	// 获得车型
	// Ext.Ajax.request({
	// url : order.realPath('queryVehcileType.action'),
	// async : false,
	// success : function(response) {
	// var json = Ext.decode(response.responseText);
	// Ext.apply(order, {
	// vehicleTypeList : json.dispatchOrderConditionVo.vehicleTypeList
	// });
	// },
	// exception : function(response) {
	// var result = Ext.decode(response.responseText);
	// Ext.ux.Toast.msg(order.orderHandle.i18n('pkp.order.orderHandle.warning'),
	// result.message, 'error', 3000);
	// }
	// });
})();

// 可视化数据Medel
Ext.define('Foss.order.orderVehView.OrderViewModel', {
			extend : 'Ext.data.Model',
			// 定义字段
			fields : [{
						name : 'vehicleNo',
						type : 'string'
					}, {
						name : 'vehicleNoType',
						type : 'string'
					}, {
						name : 'recieveOrderStatus',
						type : 'string'
					}, {
						name : 'regionCode',
						type : 'string'
					}, {
						name : 'regionName',
						type : 'string'
					}, {
						name : 'driverCode',
						type : 'string'
					}, {
						name : 'driverName',
						type : 'string'
					}, {
						name : 'receiveOrderTotal',
						type : 'int'
					}, {
						name : 'receiveWaybillOrderTotal',
						type : 'int'
					}, {
						name : 'signWaybillTotal',
						type : 'int'
					}, {
						name : 'deliverWaybillTotal',
						type : 'int'
					}, {
						name : 'weightAndVolume',
						type : 'string'
					}]
		});

// 可视化store
Ext.define('Foss.order.orderVehView.OrderViewStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.order.orderVehView.OrderViewModel',
	pageSize : 10,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url : order.realPath('queryDriverRecords.action'),
		timeout : 300000,
		reader : {
			type : 'json',
			root : 'orderVehVo.ownTruckSignList',
			totalProperty : 'totalCount'
		}
	},
	listeners : {
		// 查询事件
		beforeload : function(s, operation, eOpts) {
			// 执行查询，首先查询条件，为全局变量，在查询条件的FORM创建时生成
			var serachParms = Ext.getCmp('T_order-orderVehView_content')
					.getQueryForm().getValues();
			var params = {
				'orderVehVo.queryDto.cadeCode' : serachParms.cadeCode,
				'orderVehVo.queryDto.bigZoneCodes' : serachParms.bigZoneCode
						.toString().split(","),
				'orderVehVo.queryDto.smallZoneCode' : serachParms.smallZoneCode,
				'orderVehVo.queryDto.orderNo' : serachParms.orderNo,
				'orderVehVo.queryDto.vehicleNo' : serachParms.vehicleNo,
				'orderVehVo.queryDto.driverCode' : serachParms.driverCode,
				'orderVehVo.queryDto.recieveOrderStatus' : serachParms.recieveOrderStatus
			};
			Ext.apply(operation, {
						params : params
					});
		}
	}
});

// 地图模式model
Ext.define('Foss.order.orderVehView.GlobalViewModel', {
			extend : 'Ext.data.Model',
			// 定义字段
			fields : [{
						name : 'name',
						type : 'string'
					}, {
						name : 'value',
						type : 'string'
					}]
		});

// 地图模式store
Ext.define('Foss.order.orderVehView.GlobalViewStore', {
			extend : 'Ext.data.Store',
			model : 'Foss.order.orderVehView.GlobalViewModel',
			data : {
				'items' : [{
							name : '全部',
							value : 'all'
						}, {
							name : '车辆',
							value : 'vehicle'
						}, {
							name : '订单',
							value : 'order'
						}]
			},
			proxy : {
				type : 'memory',
				reader : {
					type : 'json',
					root : 'items'
				}
			},
			constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				me.callParent([cfg]);
			}
		});

// 接收订单状态store
Ext.define('Foss.order.orderVehView.RecieveOrderStore', {
			extend : 'Ext.data.Store',
			model : 'Foss.order.orderVehView.GlobalViewModel',
			data : {
				'items' : [{
							name : '全部',
							value : 'ALL'
						}, {
							name : '开启',
							value : 'OPEN'
						}, {
							name : '暂停',
							value : 'STOP'
						}]
			},
			proxy : {
				type : 'memory',
				reader : {
					type : 'json',
					root : 'items'
				}
			},
			constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				me.callParent([cfg]);
			}
		});

/**
 * 查询FORM
 */
Ext.define('Foss.order.orderVehView.queryForm', {
	extend : 'Ext.form.Panel',
	cls : 'autoHeight',
	// bodyCls:'autoHeight',
	height : 'auto',
	layout : 'column',
	collapsible : true,
	animCollapse : true,
	title : '查询条件',
	frame : true,
	defaults : {
		width : 600,
		labelWidth : 90
	},
	layout : 'column',
	initComponent : function() {
		var me = this;
		Ext.applyIf(me, {
			items : [Ext.create('Ext.panel.Panel', {
				title : '接货大区',
				frame : true,
				columnWidth : .24,
				height : 170,
				autoScroll : true,
				layout : 'column',
				items : [{
					name : 'bigZoneCode',
					xtype : 'commonbigregionsselector',
					management : order.fleetCode,
					displayField : 'regionName',// 显示名称
					valueField : 'regionCode',// 值
					columnWidth : 1,
					listeners : {
						'blur' : function(f) {
							// var smallZone = Ext
							// .getCmp('smallZone');
							var smallZone = f.ownerCt.ownerCt.items.getAt(2).items
									.getAt(1);
							if (f.getValue().length > 0) {
								smallZone.setEditable(false);
								smallZone.clearValue();
								smallZone.disabled = true;
							} else {
								smallZone.setEditable(true);
								smallZone.disabled = false;
							}
						}
					}
				}]
			}), {
				xtype : 'container',
				border : false,
				columnWidth : .01,
				height : 170,
				html : '&nbsp;'
			}, Ext.create('Ext.panel.Panel', {
				columnWidth : .75,
				height : 'auto',
				layout : 'column',
				autoScroll : true,
				items : [{
							xtype : 'container',
							border : false,
							columnWidth : 1,
							height : 15,
							html : '&nbsp;'
						}, {
							// id : 'smallZone',
							fieldLabel : '接货小区',
							name : 'smallZoneCode',
							xtype : 'commonsmallregionsselector',
							management : order.fleetCode,
							displayField : 'regionName',// 显示名称
							valueField : 'regionCode',// 值
							columnWidth : .34
						}, {
							fieldLabel : '车队',
							name : 'cadeCode',
							xtype : 'commonmotorcadeselector',
							// topFleetOrgCode:order.fleetCode,
							columnWidth : .33
						}, {
							fieldLabel : '订单号',
							name : 'orderNo',
							xtype : 'textfield',
							maxLength : 20,
							columnWidth : .33
						}, {
							xtype : 'container',
							border : false,
							columnWidth : 1,
							height : 15,
							html : '&nbsp;'
						}, {
							fieldLabel : '车牌号',
							name : 'vehicleNo',
							xtype : 'commontruckselector',
							columnWidth : .34
						}, {
							fieldLabel : '司机',
							name : 'driverCode',
							xtype : 'commondriverselector',
							waybillFlag : 'Y',// 是否是集中接送区域 如果设置值为Y,N
							// parentOrgCode:order.fleetCode,//上级车队组织
							columnWidth : .33
						}, {
							fieldLabel : '地图模式',
							name : 'orderStatus',
							xtype : 'combobox',
							displayField : 'name',
							valueField : 'value',
							queryMode : 'local',
							triggerAction : 'all',
							store : Ext
									.create('Foss.order.orderVehView.GlobalViewStore'),
							columnWidth : .33
						}, {
							xtype : 'container',
							border : false,
							columnWidth : 1,
							height : 15,
							html : '&nbsp;'
						}, {
							fieldLabel : '接收状态',
							name : 'recieveOrderStatus',
							xtype : 'combobox',
							displayField : 'name',
							valueField : 'value',
							queryMode : 'local',
							triggerAction : 'all',
							store : Ext
									.create('Foss.order.orderVehView.RecieveOrderStore'),
							columnWidth : .34
						}, {
							xtype : 'container',
							border : false,
							columnWidth : .66,
							html : '&nbsp;'
						}, {
							xtype : 'container',
							border : false,
							columnWidth : 1,
							height : 30,
							html : '&nbsp;'
						}, {
							border : 1,
							xtype : 'container',
							columnWidth : 1,
							defaultType : 'button',
							layout : 'column',
							items : [{
								xtype : 'button',
								columnWidth : .1,
								text : '重置',
								handler : function() {
									var me = this;
									var form = me.up('form');
									form.getForm().reset();
									// var smallZone = Ext
									// .getCmp('smallZone');
									var smallZone = this.ownerCt.ownerCt.items
											.getAt(1);
									smallZone.setEditable(true);
									smallZone.disabled = false;
								}
							}, {
								xtype : 'container',
								border : false,
								columnWidth : .8,
								html : '&nbsp;'
							}, {
								xtype : 'button',
								columnWidth : .1,
								cls : 'yellow_button',
								text : '查询',
								handler : function() {
									var myForm = this.up('form').getForm();
									if (myForm.isValid()) {
										var me = this;
										var resultGrid = Ext
												.getCmp('T_order-orderVehView_content')
												.getResultGrid();
										var store = resultGrid.getStore();
										Ext.getBody().mask();
										Ext.getCmp('mainNav').mask();
										store.loadPage(1, {
											callback : function(records,
													operation, success) {
												Ext.getBody().unmask();
												Ext.getCmp('mainNav').unmask();
												// 抛出异常
												var rawData = resultGrid.store.proxy.reader.rawData;
												if (!success
														&& !rawData.isException) {
													Ext.Msg.alert('提示',
															rawData.message);
													return false;
												}
												// 正常返回
												if (success) {
													var result = Ext
															.decode(operation.response.responseText), ownTruckSignList = result.orderVehVo.ownTruckSignList, vehSmallZones = result.orderVehVo.vehSmallZones, orderVehViewSignList = result.orderVehVo.orderVehViewSignList;
													// 判断查询的订单号是否为空，如果不为空
													if (!Ext
															.isEmpty(myForm
																	.getValues().orderNo)) {
														ownTruckSignList = null;
													}
													if (!Ext
															.isEmpty(ownTruckSignList)
															&& ownTruckSignList.length > 0) {
														resultGrid.show();
													}
													order.orderVehView.gisMap
															.removeAllOverlays();
													// 判断地图模式是否为全部，只显示订单，只显示小区
													if (Ext
															.isEmpty(myForm
																	.getValues().orderStatus)
															|| myForm
																	.getValues().orderStatus == 'all') {
														// 显示订单、车辆信息
														order.orderVehView
																.showOrder(orderVehViewSignList);
														order.orderVehView
																.showNewVehicle(vehSmallZones);
													} else if (myForm
															.getValues().orderStatus == 'vehicle') {
														// 只显示车辆信息
														order.orderVehView
																.showNewVehicle(vehSmallZones);
													} else if (myForm
															.getValues().orderStatus == 'order') {
														// 只显示订单信息
														order.orderVehView
																.showOrder(orderVehViewSignList);
													}
												}
											}
										});
									}
								}
							}]
						}]
			})]
		});
		me.callParent(arguments);
	}
});

Ext.define('Foss.order.orderVehView.orderVehGrid', {
			extend : 'Ext.grid.Panel',
			title : '显示结果',
			frame : true,
			cls : 'autoHeight',
			bodyCls : 'autoHeight',
			collapsible : true,
			sortableColumns : true,
			enableColumnHide : false,
			enableColumnMove : false,
			stripeRows : true, // 交替行效果
			selType : "rowmodel", // 选择类型设置为：行选择
			emptyText : '查询结果为空',

			viewConfig : {
				stripeRows : false,
				enableTextSelection : true,
				getRowClass : function(record, rowIndex, rp, ds) {
					// 接收/暂停 值为 暂停的 红色突出显示
					var orderStatus = record.get('recieveOrderStatus');
					if (orderStatus == '暂停') {
						return 'order-dispatchOrder-row-red';
					} else {
						return '';
					}
				}
			},

			// 得到bbar
			pagingToolbar : null,
			getPagingToolbar : function() {
				var me = this;
				if (this.pagingToolbar == null) {
					me.bbar = Ext.create('Deppon.StandardPaging', {
								store : this.store,
								pageSize : 10,
								plugins : Ext.create(
										'Deppon.ux.PageSizePlugin', {
											maximumSize : 100,
											sizeList : [['10', 10], ['20', 20],
													['50', 50], ['100', 100]]
										})
							});
				}
				return me.bbar;
			},
			// 暂停接受订单
			stopReceiveOrder : function() {
				var me = this;
				var workerStatus = new Array();
				var selector = me.getSelectionModel().getSelection();
				if (selector.length < 1) {
					Ext.ux.Toast.msg('提示', '请选中一行', 'error', 2000);
					return;
				};
				for (var i = 0; i < selector.length; i++) {
					workerStatus.push(selector[i].get('vehicleNo'));
				};
				var params = {
					'orderVehVo.vehicleNos' : workerStatus
				};
				// 提交对应的数据
				Ext.Ajax.request({
							url : order.realPath('stopDriver.action'),
							params : params,
							success : function(response) {
								Ext.ux.Toast.msg('提示', '暂停成功！');
							},
							exception : function(response) {
								var result = Ext.decode(response.responseText);
								Ext.MessageBox.alert('警告', result.message);
								return;
							}
						});
				this.store.load();
			},
			// 开启接受订单
			openReceiveOrder : function() {
				var me = this;
				var workerStatus = new Array();
				var selector = me.getSelectionModel().getSelection();
				if (selector.length < 1) {
					Ext.ux.Toast.msg('提示', '请选中一行', 'error', 2000);
					return;
				};
				for (var i = 0; i < selector.length; i++) {
					workerStatus.push(selector[i].get('vehicleNo'));
				};
				var params = {
					'orderVehVo.vehicleNos' : workerStatus
				};
				// 提交对应的数据
				Ext.Ajax.request({
							url : order.realPath('openDriver.action'),
							params : params,
							success : function(response) {
								Ext.ux.Toast.msg('提示', '开启成功！');
							},
							exception : function(response) {
								var result = Ext.decode(response.responseText);
								Ext.MessageBox.alert('警告', result.message);
							}
						});
				this.store.load();
			},
			constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				me.tbar = ['->', {
							text : '暂停分配',// 暂停分配
							handler : function() {
								me.stopReceiveOrder();
							}
						}, {
							text : '开启分配',// 开启分配
							handler : function() {
								me.openReceiveOrder();
							}
						}];

				me.columns = [{
							text : '车牌号',
							width : 75,
							align : 'center',
							dataIndex : 'vehicleNo'
						}, {
							text : '车型',
							width : 50,
							align : 'center',
							dataIndex : 'vehicleNoType'
						}, {
							text : '接收/暂停',
							width : 82,
							align : 'center',
							dataIndex : 'recieveOrderStatus'
						}, {
							text : '小区/大区',
							width : 105,
							dataIndex : 'regionName',
							xtype : 'ellipsiscolumn'
						}, {
							text : '司机工号',
							width : 75,
							align : 'center',
							dataIndex : 'driverCode',
							xtype : 'ellipsiscolumn'
						}, {
							text : '司机姓名',
							width : 75,
							dataIndex : 'driverName'
						}, {
							text : '接货订单总重量/体积',
							dataIndex : 'weightAndVolume',
							width : 140
						}, {
							text : '接货订单总数',
							flex : 1,
							align : 'center',
							dataIndex : 'receiveOrderTotal'
						}, {
							text : '未接货订单数',
							flex : 1,
							align : 'center',
							dataIndex : 'receiveWaybillOrderTotal'
						}, {
							text : '派送运单总数',
							flex : 1,
							align : 'center',
							dataIndex : 'deliverWaybillTotal'
						}, {
							text : '未派送运单数',
							flex : 1,
							align : 'center',
							dataIndex : 'signWaybillTotal'
						}];

				me.store = Ext.create('Foss.order.orderVehView.OrderViewStore',
						{
							autoLoad : false,
							pageSize : 10
						});

				me.selModel = Ext.create('Ext.selection.CheckboxModel', {// 多选框
					mode : 'MULTI',
					checkOnly : true
				});
				me.bbar = me.getPagingToolbar();
				me.callParent([cfg]);
			}
		});

/**
 * 订单ID 唯一标识符 ordernum 订单号 address 订单地址 tel 客户电话 vehicleDept 部门 deptPhone 部门电话
 */
order.orderVehView.Order = function(id, ordernum, address, tel, vehicleDept,
		deptPhone) {
	this.id = id;
	this.ordernum = ordernum;
	this.address = address;
	this.tel = tel;
	this.vehicleDept = vehicleDept;
	this.deptPhone = deptPhone;
}

/**
 * 小区类 ID 唯一标识符 name 小区名字 polygonID 范围ID vehicleArry 所含车辆的数组,元素类型为Vehicle
 */
order.orderVehView.Smallzone = function(id, name, polygonID, vehicleArry) {
	this.id = id;
	this.name = name;
	this.polygonID = polygonID;
	this.vehicleArry = vehicleArry;
}

/**
 * vehicle_no 运行车牌 vehicle_type 车辆类型 ：公司车/外请车 driver_name 出车司机 ：司机名字 driver_tel
 * 司机电话 smallzone 所属小区 ：小区对象：包含polygonID与小区名字 bigzone 所属大区 ： 大区名字 status 接收状态 ：
 * 接收中，暂停接收 responsibletype 职责类型 ：一级车/二级车
 */
order.orderVehView.Vehicle = function(vehicle_no, vehicle_type, driver_name,
		driver_tel, smallzone, bigzone, status, responsibletype) {
	this.vehicle_no = vehicle_no;
	this.vehicle_type = vehicle_type;
	this.driver_name = driver_name;
	this.driver_tel = driver_tel;
	this.smallzone = smallzone;
	this.bigzone = bigzone;
	this.status = status;
	this.responsibletype = responsibletype;
}

/** 显示订单信息* */
order.orderVehView.showOrder = function(orderVehViewSignList) {
	if (!Ext.isEmpty(orderVehViewSignList) && orderVehViewSignList.length > 0) {
		// 定义所以订单数组集合
		var allOrderArray = new Array(), pointArry = new Array();

		// 循环定义对应的订单对象
		for (var i in orderVehViewSignList) {
			var orderInfo = new order.orderVehView.Order(
					orderVehViewSignList[i].orderNo,
					orderVehViewSignList[i].orderNo,
					orderVehViewSignList[i].pickupAddress,
					orderVehViewSignList[i].customerTelephone,
					orderVehViewSignList[i].orderVehicleOrgName,
					orderVehViewSignList[i].depTelephone);
			allOrderArray.push(orderInfo);
		}
		// 判断组装的的对象数组是否为空
		if (!Ext.isEmpty(allOrderArray) && allOrderArray.length > 0) {
			for (var j in allOrderArray) {
				(function(j) {

					var orderInfo = allOrderArray[j], address = orderInfo.address;
					order.orderVehView.gisMap.geoCodeAddress(address, function(
									point) {
								var option = {
									color : 'yellow',
									imgUrl : 'images/',
									labelMsg : '订单号：' + orderInfo.ordernum,
									infoMsg : {
										'订单号' : orderInfo.ordernum,
										'接货地址' : orderInfo.address,
										'客户电话' : orderInfo.tel,
										'约车部门' : orderInfo.vehicleDept,
										'部门电话' : orderInfo.deptPhone
									}
								};
								order.orderVehView.gisMap.displayMarker(point,
										option);
								pointArry.push(point);
								order.orderVehView.gisMap
										.setViewport(pointArry);
							});
				})(j);
			}
		}
	}
};

/** 显示车队所有小区车辆信息* */
order.orderVehView.showNewVehicle = function(vehSmallZones) {
	// 判断对应车辆小区信息是否存在
	if (!Ext.isEmpty(vehSmallZones) && vehSmallZones.length > 0) {
		var smallzoneList = vehSmallZones;
		var allOverlayArray = new Array();
		// 判断集合是否为空
		if (!Ext.isEmpty(smallzoneList) && smallzoneList.length > 0) {
			for (var i in smallzoneList) {
				(function(i) {
					var smallzoneInfo = smallzoneList[i];
					order.orderVehView.gisMap.addPolygonsFromDbByIds(
							[smallzoneInfo.polygonID], function(polyArry) {
								var polygon = polyArry[0];
								smallzoneInfo.overlayID = polygon.ID;
								var options = new Array();
								var vehicleArray = smallzoneInfo.vehicleArry;
								for (var j in vehicleArray) {
									var _color = null;
									if (vehicleArray[j].status == "开启") {
										_color = 'blue';
									} else {
										_color = 'red';
									}
									var option = {
										infoMsg : {
											"运行车辆" : vehicleArray[j].vehicle_no,
											"车辆类型" : vehicleArray[j].vehicle_type,
											'出车司机' : vehicleArray[j].driver_name,
											'司机电话' : vehicleArray[j].driver_tel,
											'所属小区' : vehicleArray[j].smallzone,
											'所属大区' : null,
											'接收状态' : vehicleArray[j].status

										},
										color : _color,
										imgUrl : 'images/',
										labelMsg : vehicleArray[j].vehicle_no
												+ " "
												+ vehicleArray[j].responsibletype
									};
									options.push(option);
								}
								order.orderVehView.gisMap
										.displayPointsInPolygon(polygon,
												options, function(arry) {

												});
							});

				})(i);
			}

		}
	}
};

Ext.define('Foss.order.orderVehView.GisMap', {
	extend : 'Ext.panel.Panel',
	title : '地图显示列表',
	frame : true,
	height : 600,
	collapsible : true,
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.items = [{
			xtype : 'container',
			height : 700,
			width : 1000,
			listeners : {
				// 显示之前的事件
				beforerender : function(panel) {
					Ext.defer(function() {
						var mapLocation = '上海';
						var centerGisMap = new DpMap(panel.getId(), {
									center : mapLocation,
									zoom : 13
								}, function(map) {
									if (order.orderVehView.gisMap == null) {
										order.orderVehView.gisMap = new DpMap.common1(map);
									}
								});
					}, 1000, this);
				}
			}
		}];
		me.callParent([cfg]);
	}
});

Ext.onReady(function() {
			Ext.QuickTips.init();
			order.orderVehView.gisMap = null;
			order.orderVehView.queryForm = Ext
					.create('Foss.order.orderVehView.queryForm');
			order.orderVehView.resultGird = Ext
					.create('Foss.order.orderVehView.orderVehGrid');
			order.orderVehView.mapPanel = Ext
					.create('Foss.order.orderVehView.GisMap');
			Ext.create('Ext.panel.Panel', {
						id : 'T_order-orderVehView_content',
						cls : "panelContentNToolbar",
						bodyCls : 'panelContentNToolbar-body',
						layout : 'auto',
						getQueryForm : function() {
							return order.orderVehView.queryForm;
						},
						getResultGrid : function() {
							return order.orderVehView.resultGird;
						},
						getMapPanel : function() {
							return order.orderVehView.mapPanel;
						},
						items : [order.orderVehView.queryForm,
								order.orderVehView.resultGird,
								order.orderVehView.mapPanel],
						renderTo : 'T_order-orderVehView-body'
					});
		});
