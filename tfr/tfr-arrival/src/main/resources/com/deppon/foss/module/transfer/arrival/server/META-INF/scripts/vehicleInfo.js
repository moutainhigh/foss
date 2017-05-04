/**
 * 车辆明细信息
 */
//放行类型
Ext.define('Foss.arrival.DepartTypeStore', {
			extend : 'Ext.data.Store',
			fields : [{
						name : 'code',
						type : 'string'
					}, {
						name : 'name',
						type : 'string'
					}],
			data : {
				'items' : [{
							code : 'NO_TASK',
							name : '临时任务车辆'
						}, {
							code : 'TASK',
							name : '任务车辆'
						}]
			},
			proxy : {
				type : 'memory',
				reader : {
					type : 'json',
					root : 'items'
				}
			}
		});
//车辆当前状态
Ext.define('Foss.arrival.VehicleCurrentStatusStore', {
			extend : 'Ext.data.Store',
			fields : [{
						name : 'code',
						type : 'string'
					}, {
						name : 'name',
						type : 'string'
					}],
			data : {
				'items' : [{
							code : 'RUN',
							name : '运行'
						}, {
							code : 'STOP',
							name : '停止'
						}, {
							code : 'ACCIDENTS',
							name : '事故车辆'
						}, {
							code : 'OFFLINE',
							name : '离线'
						},{
							code : 'TRAFFIC',
							name : '堵车'
						},{
							code : 'SLOWRUN',
							name : '缓行'
						},{
							code : 'DETAINING',
							name : '扣车'
						}]
			},
			proxy : {
				type : 'memory',
				reader : {
					type : 'json',
					root : 'items'
				}
			}
		});
//放行类型
Ext.define('Foss.arrival.IsGPSStore', {
			extend : 'Ext.data.Store',
			fields : [{
						name : 'code',
						type : 'string'
					}, {
						name : 'name',
						type : 'string'
					}],
			data : {
				'items' : [{
							code : 'false',
							name : '否'
						}, {
							code : 'true',
							name : '是'
						}]
			},
			proxy : {
				type : 'memory',
				reader : {
					type : 'json',
					root : 'items'
				}
			}
		});		
		//车辆放行事项
Ext.define('Foss.arrival.DepartItemsStore', {
			extend : 'Ext.data.Store',
			fields : [{
						name : 'code',
						type : 'string'
					}, {
						name : 'name',
						type : 'string'
					}],
			data : {
				'items' : [{
							code : 'NO_TASK_KEEP',
							name : '保养'
						}, {
							code : 'NO_TASK_YEAR_VERIFICATION',
							name : '年审/季审 '
						}, {
							code : 'NO_TASK_APPOINT',
							name : '临时放空出发/约车 '
						}, {
							code : 'NO_TASK_OIL',
							name : '加油 '
						}, {
							code : 'NO_TASK_REPAIR',
							name : '维修'
						}, {
							code : 'NO_TASK_OTHERS',
							name : '其他任务出发'
						}, {
							code : 'LONG_DISTANCE',
							name : '长途'
						}, {
							code : 'SHORT_DISTANCE',
							name : '短途'
						}, {
							code : 'DELIVER',
							name : '接送货'
						}]
			},
			proxy : {
				type : 'memory',
				reader : {
					type : 'json',
					root : 'items'
				}
			}
		});
// 打开车辆详细页面
Ext.define('Foss.arrival.TaskBillGridPanel', {
			extend : 'Ext.grid.Panel',
			frame : true,
			columnWidth : 1,
			bodyCls : 'autoHeight',
			cls : 'autoHeight',
			sortableColumns : false,
			enableColumnHide : false,
			enableColumnMove : false,
			autoScroll : true,
			columns : [{
				text : arrival.i18n('tfr.arrival.TaskBillGridPanel.grid.billNo.label'),//'交接编号',
				dataIndex : 'billNo',
				flex : 1
			}, {
				text : arrival.i18n('tfr.arrival.TaskBillGridPanel.grid.destOrgName.label'),//'目的站',
				dataIndex : 'destOrgName',
				flex : 1
			}, {
				text : arrival.i18n('tfr.arrival.TaskBillGridPanel.grid.containerNo.label'),//'货物柜号',
				dataIndex : 'containerNo',
				flex : 1
			}, {
				text : arrival.i18n('tfr.arrival.TaskBillGridPanel.grid.loaderName.label'),//'装车人',
				dataIndex : 'loaderName',
				flex : 1
			}],
			constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				me.callParent([cfg]);
			}
		});
Ext.define('Foss.arrival.TaskBillModel', {
			extend : 'Ext.data.Model',
			idProperty : 'billNo',
			fields : [{
						name : 'billNo',
						type : 'string'
					}, {
						name : 'destOrgName',
						type : 'string'
					}, {
						name : 'containerNo',
						type : 'string'
					}, {
						name : 'loaderName',
						type : 'string'
					}]
		});

Ext.define('Foss.arrival.VehicleInfoModel', {
			extend : 'Ext.data.Model',
			fields : [{
						name : 'vehicleNo',
						type : 'string'
					}, {
						name : 'truckType',
						type : 'string'
					}, {
						name : 'businessType',
						type : 'string'
					}, {
						name : 'truckOrgName',
						type : 'string'
					}, {
						name : 'vehicleLength',
						type : 'string'
					}, {
						name : 'selfcubage',
						type : 'string'
					}, {
						name : 'hasGPS',
						type : 'string'
					}]
		});
Ext.define('Foss.arrival.RelationInfoModel', {
			extend : 'Ext.data.Model',
			fields : [{
						name : 'driverName',
						type : 'string'
					}, {
						name : 'driverPhone',
						type : 'string'
					}, {
						name : 'fleetManagerPhone',
						type : 'string'
					}]
		});
Ext.define('Foss.arrival.BusinessInfoModel', {
			extend : 'Ext.data.Model',
			fields : [{
						name : 'businessType',
						type : 'string'
					}, {
						name : 'lineNo',
						type : 'string'
					}, {
						name : 'runningTime',
						type : 'string'
					}, {
						name : 'weight',
						type : 'string'
					}, {
						name : 'volume',
						type : 'string'
					}, {
						name : 'waybill',
						type : 'string'
					}]
		});

Ext.define('Foss.arrival.DepartInfoModel', {
			extend : 'Ext.data.Model',
			fields : [{
						name : 'id',
						type : 'string'
					}, {
						name : 'departType',
						type : 'string'
					}, {
						name : 'applyOrgCode',
						type : 'string'
					}, {
						name : 'applyUserCode',
						type : 'string'
					}, {
						name : 'applyDepartTime',
						convert : function(value) {
							if (value != null && value != '') {
								var date = new Date(value);
								return Ext.Date.format(date, 'Y-m-d H:i');
							} else {
								return null;
							}
						},
						type : 'string'
					}, {
						name : 'departItems',
						type : 'string'
					}, {
						name : 'pdaDepartTime',
						convert : function(value) {
							if (value != null && value != '') {
								var date = new Date(value);
								return Ext.Date.format(date, 'Y-m-d H:i');
							} else {
								return null;
							}
						},
						type : 'string'
					}, {
						name : 'applyType',
						type : 'string'
					}, {
						name : 'manualDepartTime',
						convert : function(value) {
							if (value != null && value != '') {
								var date = new Date(value);
								return Ext.Date.format(date, 'Y-m-d H:i');
							} else {
								return null;
							}
						},
						type : 'string'
					}, {
						name : 'manualDepartNotes',
						type : 'string'
					}]
		});

Ext.define('Foss.arrival.ArrivalInfoModel', {
			extend : 'Ext.data.Model',
			fields : [{
						name : 'planDepartTime',
						convert : function(value) {
							if (value != null && value != '') {
								var date = new Date(value);
								return Ext.Date.format(date, 'Y-m-d H:i');
							} else {
								return null;
							}
						},
						type : 'string'
					}, {
						name : 'origOrgName',
						type : 'string'
					}, {
						name : 'actualDepartTime',
						convert : function(value) {
							if (value != null && value != '') {
								var date = new Date(value);
								return Ext.Date.format(date, 'Y-m-d H:i');
							} else {
								return null;
							}
						},
						type : 'string'
					}, {
						name : 'planArriveTime',
						convert : function(value) {
							if (value != null && value != '') {
								var date = new Date(value);
								return Ext.Date.format(date, 'Y-m-d H:i');
							} else {
								return null;
							}
						},
						type : 'string'
					}, {
						name : 'destOrgName',
						type : 'string'
					}, {
						name : 'actualArriveTime',
						convert : function(value) {
							if (value != null && value != '') {
								var date = new Date(value);
								return Ext.Date.format(date, 'Y-m-d H:i');
							} else {
								return null;
							}
						},
						type : 'string'
					}, {
						name : 'status',
						type : 'string'
					}]
		});

Ext.define('Foss.arrival.OnTheWayInfoModel', {
			extend : 'Ext.data.Model',
			fields : [{
						name : 'currentPlace',
						type : 'string'
					}, {
						name : 'trackingTime',
						convert : function(value) {
							if (value != null && value != '') {
								var date = new Date(value);
								return Ext.Date.format(date, 'Y-m-d H:i');
							} else {
								return null;
							}
						},
						type : 'string'
					}, {
						name : 'estimateDepartTime',
						convert : function(value) {
							if (value != null && value != '') {
								var date = new Date(value);
								return Ext.Date.format(date, 'Y-m-d H:i');
							} else {
								return null;
							}
						},
						type : 'string'
					}, {
						name : 'curentStatus',
						type : 'string'
					}]
		});

Ext.define('Foss.arrival.VehicleInfoForm', {
	extend : 'Ext.form.Panel',
	layout : 'column',
	title :arrival.i18n('tfr.arrival.VehicleInfoForm.form.title'),// '车辆信息',
	frame : false,
	defaults : {
		margin : '5 5 5 5',
		allowBlank : true,
		xtype : 'displayfield'
	},
	items : [{
				name : 'vehicleNo',
				columnWidth : .33,
				fieldLabel : arrival.i18n('tfr.arrival.VehicleInfoForm.form.vehicleNo.label'),//'车牌号'
			}, {
				name : 'truckType',
				columnWidth : .33,
				fieldLabel : arrival.i18n('tfr.arrival.VehicleInfoForm.form.truckType.label'),//'车辆归属类型'
			}, {
				name : 'businessType',
				columnWidth : .33,
				fieldLabel : arrival.i18n('tfr.arrival.VehicleInfoForm.form.businessType.label'),//'车辆业务类型'
			}, {
				name : 'truckOrgName',
				columnWidth : .33,
				fieldLabel : arrival.i18n('tfr.arrival.VehicleInfoForm.form.truckOrgName.label'),//'车辆归属部门'
			}, {
				name : 'vehicleLength',
				columnWidth : .33,
				fieldLabel : arrival.i18n('tfr.arrival.VehicleInfoForm.form.vehicleLength.label'),//'车型'
			}, {
				name : 'selfcubage',
				columnWidth : .33,
				fieldLabel : arrival.i18n('tfr.arrival.VehicleInfoForm.form.selfcubage.label'),//'载重/净空'
			}, {

				name : 'hasGPS',
				columnWidth : .80,
				fieldLabel : arrival.i18n('tfr.arrival.VehicleInfoForm.form.hasGPS.label'),//'是否安装GPS',
				renderer : function(value) {
					var store = Ext.create('Foss.arrival.IsGPSStore'), record = store
							.findRecord('code', value);
					if (record != null) {
						return record.get('name');
					} else {
						return '';
					}
				}
			}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([cfg]);

	}
});

Ext.define('Foss.arrival.RelationInfoForm', {
	extend : 'Ext.form.Panel',
	layout : 'column',
	frame : false,
	title : arrival.i18n('tfr.arrival.RelationInfoForm.form.title'),//'联系方式',
	defaults : {
		margin : '5 5 5 5',
		allowBlank : true,
		xtype : 'displayfield'
	},
	items : [{
				name : 'driverName',
				columnWidth : .33,
				fieldLabel :arrival.i18n('tfr.arrival.RelationInfoForm.form.driverName.label'),// '司机姓名'
			}, {
				name : 'driverPhone',
				columnWidth : .33,
				fieldLabel : arrival.i18n('tfr.arrival.RelationInfoForm.form.driverPhone.label'),//'司机电话'
			}, {
				name : 'fleetManagerPhone',
				columnWidth : .33,
				fieldLabel : arrival.i18n('tfr.arrival.RelationInfoForm.form.fleetManagerPhone.label'),//'车队经理电话'
			}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

Ext.define('Foss.arrival.BusinessInfoForm', {
	extend : 'Ext.form.Panel',
	layout : 'column',
	frame : false,
	title : arrival.i18n('tfr.arrival.BusinessInfoForm.form.title'),//'车辆业务信息',
	defaults : {
		margin : '5 5 5 5',
		allowBlank : true,
		xtype : 'displayfield'
	},
	items : [{
		name : 'businessType',
		columnWidth : .33,
		fieldLabel : '任务车辆类型',
		renderer : function(value) {
			var store = FossDataDictionary. getDataDictionaryStore('ARRIVE_TYPE_TFR','Foss_login_language_store_Id'), record = store
					.findRecord('valueCode', value);
			if (record != null) {
				return record.get('valueName');
			} else {
				return '';
			}
		}
	},  {
		name : 'lineNo',
		columnWidth : .33,
		fieldLabel : arrival.i18n('tfr.arrival.BusinessInfoForm.form.lineNo.label'),//'线路'
	}, {
		name : 'runningTime',
		columnWidth : .34,
		fieldLabel : arrival.i18n('tfr.arrival.BusinessInfoForm.form.runningTime.label'),//'运行时长(小时)'
	}, {
		name : 'weight',
		columnWidth : .33,
		fieldLabel :arrival.i18n('tfr.arrival.BusinessInfoForm.form.weight.label'),// '重量(千克)'
	}, {
		name : 'volume',
		columnWidth : .33,
		fieldLabel : arrival.i18n('tfr.arrival.BusinessInfoForm.form.volume.label'),//'体积(方)'
	}, {
		name : 'waybill',
		columnWidth : .34,
		fieldLabel : arrival.i18n('tfr.arrival.BusinessInfoForm.form.waybill.label'),//'件数'
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

Ext.define('Foss.arrival.VehicleStatusStore', {
			extend : 'Ext.data.Store',
			fields : [{
						name : 'code',
						type : 'string'
					}, {
						name : 'name',
						type : 'string'
					}],
			data : {
				'items' : [{
							code : 'UNDEPART',
							name : '待出发'
						}, {
							code : 'ONTHEWAY',
							name : '在途'
						}, {
							code : 'ARRIVED',
							name : '已到达'
						}, {
							code : 'CANCLED',
							name : '作废 '
						}]
			},
			proxy : {
				type : 'memory',
				reader : {
					type : 'json',
					root : 'items'
				}
			}
		});

Ext.define('Foss.arrival.DepartInfoForm', {
	extend : 'Ext.form.Panel',
	layout : 'column',
	frame : false,
	title : arrival.i18n('tfr.arrival.DepartInfoForm.form.title'),//'申请放行',
	defaults : {
		margin : '5 5 5 5',
		allowBlank : true,
		xtype : 'displayfield'
	},
	items : [{
				xtype : 'displayfield',
				name : 'departType',
				columnWidth : .33,
				fieldLabel :arrival.i18n('tfr.arrival.DepartInfoForm.form.departType.label'),// '出发放行类型',
				renderer : function(value) {
					var store = Ext.create('Foss.arrival.DepartTypeStore'), record = store
							.findRecord('code', value);
					if (record != null) {
						return record.get('name');
					} else {
						return '';
					}
				}
			},  {
				xtype : 'displayfield',
				name : 'applyOrgCode',
				columnWidth : .33,
				fieldLabel :arrival.i18n('tfr.arrival.DepartInfoForm.form.applyOrgCode.label'),// '申请部门'
			}, {
				xtype : 'displayfield',
				name : 'applyUserCode',
				columnWidth : .33,
				fieldLabel :arrival.i18n('tfr.arrival.DepartInfoForm.form.applyUserCode.label'),// '申请人'
			}, {
				xtype : 'displayfield',
				name : 'applyDepartTime',
				columnWidth : .33,
				fieldLabel :arrival.i18n('tfr.arrival.DepartInfoForm.form.applyDepartTime.label'),// '申请时间'
			}, {
				xtype : 'displayfield',
				name : 'departItems',
				columnWidth : .33,
				fieldLabel :arrival.i18n('tfr.arrival.DepartInfoForm.form.departItems.label'),// '放行事项',
				renderer : function(value) {
					var store = Ext.create('Foss.arrival.DepartItemsStore'), record = store
							.findRecord('code', value);
					if (record != null) {
						return record.get('name');
					} else {
						return '';
					}
				}
			}, {
				xtype : 'displayfield',
				name : 'pdaDepartTime',
				columnWidth : .33,
				labelWidth : 120,
				fieldLabel :arrival.i18n('tfr.arrival.DepartInfoForm.form.pdaDepartTime.label'),// '保安PDA放行时间'
			}
			, {
				xtype : 'displayfield',
				name : 'manualDepartTime',
				columnWidth : .33,
				fieldLabel : arrival.i18n('tfr.arrival.DepartInfoForm.form.manualDepartTime.label'),//'打印放行条时间'
			}, {
				xtype : 'container',
				columnWidth : .33,
				html : '&nbsp;'
			}, {
				xtype : 'textareafield',
				name : 'manualDepartNotes',
				columnWidth : .30,
				draggable : true,
				fieldLabel : arrival.i18n('tfr.arrival.DepartInfoForm.form.manualDepartNotes.label'),//'备注'
			}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

Ext.define('Foss.arrival.ArrivalInfoForm', {
	extend : 'Ext.form.Panel',
	layout : 'column',
	frame : false,
	title : arrival.i18n('tfr.arrival.ArrivalInfoForm.form.title'),//'出发/到达信息',
	defaults : {
		margin : '5 5 5 5',
		allowBlank : true,
		xtype : 'displayfield'
	},
	items : [{
				xtype : 'displayfield',
				name : 'planDepartTime',
				columnWidth : .33,
				fieldLabel : arrival.i18n('tfr.arrival.ArrivalInfoForm.form.planDepartTime.label'),//'计划出发时间'
			}, {
				xtype : 'displayfield',
				name : 'origOrgName',
				columnWidth : .33,
				fieldLabel : arrival.i18n('tfr.arrival.ArrivalInfoForm.form.origOrgnName.label'),//'出发部门'
			}, {
				xtype : 'displayfield',
				name : 'actualDepartTime',
				columnWidth : .33,
				fieldLabel :arrival.i18n('tfr.arrival.ArrivalInfoForm.form.actualDepartTime.label'),// '实际出发时间'
			}, {
				xtype : 'displayfield',
				name : 'planArriveTime',
				columnWidth : .33,
				fieldLabel : arrival.i18n('tfr.arrival.ArrivalInfoForm.form.planArriveTime.label'),//'计划到达时间'
			}, {
				xtype : 'displayfield',
				name : 'destOrgName',
				columnWidth : .33,
				fieldLabel : arrival.i18n('tfr.arrival.ArrivalInfoForm.form.destOrgName.label'),//'到达部门'
			}, {
				xtype : 'displayfield',
				name : 'actualArriveTime',
				columnWidth : .33,
				fieldLabel : arrival.i18n('tfr.arrival.ArrivalInfoForm.form.actualArriveTime.label'),//'实际到达时间'
			}, {
				xtype : 'displayfield',
				name : 'status',
				columnWidth : .33,
				fieldLabel : arrival.i18n('tfr.arrival.ArrivalInfoForm.form.status.label'),//'车辆状态',
				renderer : function(value) {
					var store = Ext.create('Foss.arrival.VehicleStatusStore'), record = store
							.findRecord('code', value);
					if (record != null) {
						return record.get('name');
					} else {
						return '';
					}
				}
			}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

Ext.define('Foss.arrival.OnTheWayInfoForm', {
	extend : 'Ext.form.Panel',
	layout : 'column',
	frame : false,
	title : arrival.i18n('tfr.arrival.OnTheWayInfoForm.form.title'),//'在途信息',
	defaults : {
		margin : '5 5 5 5',
		allowBlank : true,
		xtype : 'displayfield'
	},
	items : [{
				xtype : 'displayfield',
				name : 'currentPlace',
				columnWidth : .33,
				fieldLabel : arrival.i18n('tfr.arrival.OnTheWayInfoForm.form.currentPlace.label'),//'车辆所处路段'
			}, {
				xtype : 'displayfield',
				name : 'trackingTime',
				columnWidth : .33,
				fieldLabel :arrival.i18n('tfr.arrival.OnTheWayInfoForm.form.trackingTime.label'),// '跟踪时间'
			}, {
				xtype : 'displayfield',
				name : 'estimateDepartTime',
				columnWidth : .33,
				fieldLabel :arrival.i18n('tfr.arrival.OnTheWayInfoForm.form.estimateDepartTime.label'),// '预计到达时间'
			}, {
				xtype : 'displayfield',
				name : 'curentStatus',
				columnWidth : .33,
				renderer : function(value) {
					var store = Ext
							.create('Foss.arrival.VehicleCurrentStatusStore'), record = store
							.findRecord('code', value);
					if (record != null) {
						return record.get('name');
					} else {
						return '';
					}
				},
				fieldLabel : arrival.i18n('tfr.arrival.OnTheWayInfoForm.form.curentStatus.label'),//'车辆当前状态'
			}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});
/** -----------------------------------------车辆明细信息----------------------------------------- */
Ext.define('Foss.arrival.VehicleInfoWindow', {
	extend : 'Ext.window.Window',
	width : 900,
	height : 600,
	// 设置窗口是否可关闭
	closable : true,
	// 设置窗口关闭后是隐藏还是销毁
	closeAction : 'hide',
	title : arrival.i18n('tfr.arrival.VehicleInfoWindow.window.title'),//'车辆明细',
	modal : true,
	autoScroll : true,
	defaults : {
		margin : '10 10 10 30'
	},
	vehicleInfo : null,
	getVehicleInfo : function() {
		if (this.vehicleInfo == null) {
			this.vehicleInfo = Ext.create('Foss.arrival.VehicleInfoForm');
		}
		return this.vehicleInfo;
	},
	relationInfo : null,
	getRelationInfo : function() {
		if (this.relationInfo == null) {
			this.relationInfo = Ext.create('Foss.arrival.RelationInfoForm');
		}
		return this.relationInfo;
	},
	businessInfo : null,
	getBusinessInfo : function() {
		if (this.businessInfo == null) {
			this.businessInfo = Ext.create('Foss.arrival.BusinessInfoForm');
		}
		return this.businessInfo;
	},
	taskBillGrid : null,
	getTaskBillGrid : function() {
		if (this.taskBillGrid == null) {
			this.taskBillGrid = Ext.create('Foss.arrival.TaskBillGridPanel');
		}
		return this.taskBillGrid;
	},
	departInfo : null,
	getDepartInfo : function() {
		if (this.departInfo == null) {
			this.departInfo = Ext.create('Foss.arrival.DepartInfoForm');
		}
		return this.departInfo;
	},
	arrivalInfo : null,
	getArrivalInfo : function() {
		if (this.arrivalInfo == null) {
			this.arrivalInfo = Ext.create('Foss.arrival.ArrivalInfoForm');
		}
		return this.arrivalInfo;
	},
	onTheWayInfo : null,
	getOnTheWayInfo : function() {
		if (this.onTheWayInfo == null) {
			this.onTheWayInfo = Ext.create('Foss.arrival.OnTheWayInfoForm');
		}
		return this.onTheWayInfo;
	},
	bindData : function(record, cellIndex, rowIndex) {
		var me = this;
		var params = {
			'vehicleInfoVO.queryEntity.id' : record.get('id'),
			'vehicleInfoVO.queryEntity.vehicleNo' : record.get('vehicleNo'),
			'vehicleInfoVO.queryEntity.isDepart' : 'no',
			'vehicleInfoVO.queryEntity.driverCode' : record.get('driverCode')
		}
		Ext.Ajax.request({
					url : ContextPath.TFR_EXECUTION+ '/departure/queryVehicleInfoInfo.action',
					params : params,
					success : function(response) {
						var result = Ext.decode(response.responseText);
						var model = Ext.ModelManager.create(
								result.vehicleInfoVO.vehicleInfoEntity,
								'Foss.arrival.VehicleInfoModel');
						// me.getForm().loadRecord(model);
						me.getVehicleInfo().loadRecord(model);
					}
				});
		Ext.Ajax.request({
					url : ContextPath.TFR_EXECUTION+ '/departure/queryRelationInfo.action',
					params : params,
					success : function(response) {
						var result = Ext.decode(response.responseText);
						var model = Ext.ModelManager.create(
								result.vehicleInfoVO.relationInfoEntity,
								'Foss.arrival.RelationInfoModel');
						me.getRelationInfo().loadRecord(model);
					}
				});
		Ext.Ajax.request({
					url : ContextPath.TFR_EXECUTION+ '/departure/queryBusinessInfo.action',
					params : params,
					success : function(response) {
						var result = Ext.decode(response.responseText);
						var model = Ext.ModelManager.create(
								result.vehicleInfoVO.businessInfoEntity,
								'Foss.arrival.BusinessInfoModel');
						me.getBusinessInfo().loadRecord(model);
					}
				});
		Ext.Ajax.request({
			url : ContextPath.TFR_EXECUTION+ '/departure/queryTaskBill.action',
			params : params,
			success : function(response) {
				var result = Ext.decode(response.responseText);
				me.getTaskBillGrid().store.model = 'Foss.arrival.TaskBillModel';
				me.getTaskBillGrid().store.remoteSort = 'false';
				me.getTaskBillGrid().store
						.loadData(result.vehicleInfoVO.taskBillList);
			}
		});
		Ext.Ajax.request({
					url : ContextPath.TFR_EXECUTION+ '/departure/queryDepartInfo.action',
					params : params,
					success : function(response) {
						var result = Ext.decode(response.responseText);
						var model = Ext.ModelManager.create(
								result.vehicleInfoVO.departInfoEntity,
								'Foss.arrival.DepartInfoModel');
						me.getDepartInfo().loadRecord(model);
					}
				});
		Ext.Ajax.request({
					url : ContextPath.TFR_EXECUTION+ '/departure/queryArrivalInfo.action',
					params : params,
					success : function(response) {
						var result = Ext.decode(response.responseText);
						var model = Ext.ModelManager.create(
								result.vehicleInfoVO.arrivalInfoEntity,
								'Foss.arrival.ArrivalInfoModel');
						me.getArrivalInfo().loadRecord(model);
					}
				});
		Ext.Ajax.request({
					url :ContextPath.TFR_EXECUTION+ '/departure/queryOnTheWayInfo.action',
					params : params,
					success : function(response) {
						var result = Ext.decode(response.responseText);
						var model = Ext.ModelManager.create(
								result.vehicleInfoVO.onTheWayInfoEntity,
								'Foss.arrival.OnTheWayInfoModel');
						me.getOnTheWayInfo().loadRecord(model);
					}
				});

	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.items = [me.getVehicleInfo(), me.getRelationInfo(),
				me.getBusinessInfo(), {
					border : false,
					xtype : 'container',
					columnWidth : 0.9,
					layout : 'column',
					defaults : {
						margin : '0 80 0 300'
					},
					items : [me.getTaskBillGrid()]
				}, me.getDepartInfo(), me.getArrivalInfo(),
				me.getOnTheWayInfo(), {
					border : false,
					xtype : 'container',
					columnWidth : 1,
					layout : 'column',
					defaults : {
						margin : '5 0 5 0'
					},
					items : [{
								border : false,
								columnWidth : .73
							},{
								border : false,
								columnWidth : .02
							}, {
								xtype : 'button',
								columnWidth : .2,
								margin : '5 0 0 0',
								text : arrival.i18n('tfr.arrival.VehicleInfoWindow.window.button'),//'关闭',
								handler : function() {
									me.hide();
								}
							}]
				}]
		me.callParent([cfg]);
	}
});