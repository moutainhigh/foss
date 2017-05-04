/**
 * 车辆明细信息
 */
//放行类型
Ext.define('Foss.ontheway.DepartTypeStore', {
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
Ext.define('Foss.ontheway.VehicleCurrentStatusStore', {
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
Ext.define('Foss.ontheway.IsGPSStore', {
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
Ext.define('Foss.ontheway.DepartItemsStore', {
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
//打开车辆详细页面
Ext.define('Foss.ontheway.TaskBillSummaryGridPanel', {
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
				text : '运输性质',//运输性质
				dataIndex : 'productName',
				flex : 1
			}, {
				text : '总票数',//'总票数
				dataIndex : 'waybillQtyTotal',
				flex : 1
			}, {
				text : '总重量(千克)',//总重量(千克)
				dataIndex : 'weightTotal',
				flex : 1
			}, {
				text : '总体积(方)',//'总体积(方)
				dataIndex : 'volumeTotal',
				flex : 1
			}],
			constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				me.store = Ext.create('Foss.ontheway.TaskBillSummaryStore');
				me.callParent([cfg]);
			}
		});


Ext.define('Foss.ontheway.TaskBillSummaryModel', {
	extend : 'Ext.data.Model',
	fields : [{
				name : 'productName',
				type : 'string'
			}, {
				name : 'waybillQtyTotal',
				type : 'int'
			}, {
				name : 'weightTotal',
				type : 'double'
			}, {
				name : 'volumeTotal',
				type : 'double'
			}]
});

//运单流水store
Ext.define('Foss.ontheway.TaskBillSummaryStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.ontheway.TaskBillSummaryModel'
});


//打开车辆详细页面
Ext.define('Foss.ontheway.TaskBillGridPanel', {
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
		text : ontheway.i18n('tfr.ontheway.TaskBillGridPanel.grid.billNo.label'),//'交接编号',
		dataIndex : 'billNo',
		flex : 1
	}, {
		text : ontheway.i18n('tfr.ontheway.TaskBillGridPanel.grid.destOrgName.label'),//'目的站',
		dataIndex : 'destOrgName',
		flex : 1
	}, {
		text : ontheway.i18n('tfr.ontheway.TaskBillGridPanel.grid.containerNo.label'),//'货物柜号',
		dataIndex : 'containerNo',
		flex : 1
	}, {
		text : ontheway.i18n('tfr.ontheway.TaskBillGridPanel.grid.loaderName.label'),//'装车人',
		dataIndex : 'loaderName',
		flex : 1
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});
Ext.define('Foss.ontheway.TaskBillModel', {
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

Ext.define('Foss.ontheway.VehicleInfoModel', {
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
Ext.define('Foss.ontheway.RelationInfoModel', {
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
Ext.define('Foss.ontheway.BusinessInfoModel', {
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

Ext.define('Foss.ontheway.DepartInfoModel', {
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

Ext.define('Foss.ontheway.ArrivalInfoModel', {
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

Ext.define('Foss.ontheway.OnTheWayInfoModel', {
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

Ext.define('Foss.ontheway.VehicleInfoForm', {
	extend : 'Ext.form.Panel',
	layout : 'column',
	title :ontheway.i18n('tfr.ontheway.VehicleInfoForm.form.title'),// '车辆信息',
	frame : false,
	defaults : {
		margin : '5 5 5 5',
		allowBlank : true,
		xtype : 'displayfield'
	},
	items : [{
				name : 'vehicleNo',
				columnWidth : .33,
				fieldLabel : ontheway.i18n('tfr.ontheway.VehicleInfoForm.form.vehicleNo.label')//'车牌号'
			}, {
				name : 'truckType',
				columnWidth : .33,
				fieldLabel : ontheway.i18n('tfr.ontheway.VehicleInfoForm.form.truckType.label')//'车辆归属类型'
			}, {
				name : 'businessType',
				columnWidth : .33,
				fieldLabel : ontheway.i18n('tfr.ontheway.VehicleInfoForm.form.businessType.label')//'车辆业务类型'
			}, {
				name : 'truckOrgName',
				columnWidth : .33,
				fieldLabel : ontheway.i18n('tfr.ontheway.VehicleInfoForm.form.truckOrgName.label')//'车辆归属部门'
			}, {
				name : 'vehicleLength',
				columnWidth : .33,
				fieldLabel : ontheway.i18n('tfr.ontheway.VehicleInfoForm.form.vehicleLength.label')//'车型'
			}, {
				name : 'selfcubage',
				columnWidth : .33,
				fieldLabel : ontheway.i18n('tfr.ontheway.VehicleInfoForm.form.selfcubage.label')//'载重/净空'
			}, {

				name : 'hasGPS',
				columnWidth : .80,
				fieldLabel : ontheway.i18n('tfr.ontheway.VehicleInfoForm.form.hasGPS.label'),//'是否安装GPS',
				renderer : function(value) {
					var store = Ext.create('Foss.ontheway.IsGPSStore'), record = store
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

Ext.define('Foss.ontheway.RelationInfoForm', {
	extend : 'Ext.form.Panel',
	layout : 'column',
	frame : false,
	title : ontheway.i18n('tfr.ontheway.RelationInfoForm.form.title'),//'联系方式',
	defaults : {
		margin : '5 5 5 5',
		allowBlank : true,
		xtype : 'displayfield'
	},
	items : [{
				name : 'driverName',
				columnWidth : .33,
				fieldLabel :ontheway.i18n('tfr.ontheway.RelationInfoForm.form.driverName.label')// '司机姓名'
			}, {
				name : 'driverPhone',
				columnWidth : .33,
				fieldLabel : ontheway.i18n('tfr.ontheway.RelationInfoForm.form.driverPhone.label')//'司机电话'
			}, {
				name : 'fleetManagerPhone',
				columnWidth : .33,
				fieldLabel : ontheway.i18n('tfr.ontheway.RelationInfoForm.form.fleetManagerPhone.label')//'车队经理电话'
			}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

Ext.define('Foss.ontheway.BusinessInfoForm', {
	extend : 'Ext.form.Panel',
	layout : 'column',
	frame : false,
	title : ontheway.i18n('tfr.ontheway.BusinessInfoForm.form.title'),//'车辆业务信息',
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
		fieldLabel : ontheway.i18n('tfr.ontheway.BusinessInfoForm.form.lineNo.label')//'线路'
	}, {
		name : 'runningTime',
		columnWidth : .34,
		fieldLabel : ontheway.i18n('tfr.ontheway.BusinessInfoForm.form.runningTime.label')//'运行时长(小时)'
	}, {
		name : 'weight',
		columnWidth : .33,
		fieldLabel :ontheway.i18n('tfr.ontheway.BusinessInfoForm.form.weight.label')// '重量(千克)'
	}, {
		name : 'volume',
		columnWidth : .33,
		fieldLabel : ontheway.i18n('tfr.ontheway.BusinessInfoForm.form.volume.label')//'体积(方)'
	}, {
		name : 'waybill',
		columnWidth : .34,
		fieldLabel : ontheway.i18n('tfr.ontheway.BusinessInfoForm.form.waybill.label')//'件数'
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

Ext.define('Foss.ontheway.VehicleStatusStore', {
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

Ext.define('Foss.ontheway.DepartInfoForm', {
	extend : 'Ext.form.Panel',
	layout : 'column',
	frame : false,
	title : ontheway.i18n('tfr.ontheway.DepartInfoForm.form.title'),//'申请放行',
	defaults : {
		margin : '5 5 5 5',
		allowBlank : true,
		xtype : 'displayfield'
	},
	items : [{
				xtype : 'displayfield',
				name : 'departType',
				columnWidth : .33,
				fieldLabel :ontheway.i18n('tfr.ontheway.DepartInfoForm.form.departType.label'),// '出发放行类型',
				renderer : function(value) {
					var store = Ext.create('Foss.ontheway.DepartTypeStore'), record = store
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
				fieldLabel :ontheway.i18n('tfr.ontheway.DepartInfoForm.form.applyOrgCode.label')// '申请部门'
			}, {
				xtype : 'displayfield',
				name : 'applyUserCode',
				columnWidth : .33,
				fieldLabel :ontheway.i18n('tfr.ontheway.DepartInfoForm.form.applyUserCode.label')// '申请人'
			}, {
				xtype : 'displayfield',
				name : 'applyDepartTime',
				columnWidth : .33,
				fieldLabel :ontheway.i18n('tfr.ontheway.DepartInfoForm.form.applyDepartTime.label')// '申请时间'
			}, {
				xtype : 'displayfield',
				name : 'departItems',
				columnWidth : .33,
				fieldLabel :ontheway.i18n('tfr.ontheway.DepartInfoForm.form.departItems.label'),// '放行事项',
				renderer : function(value) {
					var store = Ext.create('Foss.ontheway.DepartItemsStore'), record = store
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
				fieldLabel :ontheway.i18n('tfr.ontheway.DepartInfoForm.form.pdaDepartTime.label')// '保安PDA放行时间'
			}
			, {
				xtype : 'displayfield',
				name : 'manualDepartTime',
				columnWidth : .33,
				fieldLabel : ontheway.i18n('tfr.ontheway.DepartInfoForm.form.manualDepartTime.label')//'打印放行条时间'
			}, {
				xtype : 'container',
				columnWidth : .33,
				html : '&nbsp;'
			}, {
				xtype : 'textareafield',
				name : 'manualDepartNotes',
				columnWidth : .30,
				draggable : true,
				fieldLabel : ontheway.i18n('tfr.ontheway.DepartInfoForm.form.manualDepartNotes.label')//'备注'
			}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

Ext.define('Foss.ontheway.ArrivalInfoForm', {
	extend : 'Ext.form.Panel',
	layout : 'column',
	frame : false,
	title : ontheway.i18n('tfr.ontheway.ArrivalInfoForm.form.title'),//'出发/到达信息',
	defaults : {
		margin : '5 5 5 5',
		allowBlank : true,
		xtype : 'displayfield'
	},
	items : [{
				xtype : 'displayfield',
				name : 'planDepartTime',
				columnWidth : .33,
				fieldLabel : ontheway.i18n('tfr.ontheway.ArrivalInfoForm.form.planDepartTime.label')//'计划出发时间'
			}, {
				xtype : 'displayfield',
				name : 'origOrgName',
				columnWidth : .33,
				fieldLabel : ontheway.i18n('tfr.ontheway.ArrivalInfoForm.form.origOrgnName.label')//'出发部门'
			}, {
				xtype : 'displayfield',
				name : 'actualDepartTime',
				columnWidth : .33,
				fieldLabel :ontheway.i18n('tfr.ontheway.ArrivalInfoForm.form.actualDepartTime.label')// '实际出发时间'
			}, {
				xtype : 'displayfield',
				name : 'planArriveTime',
				columnWidth : .33,
				fieldLabel : ontheway.i18n('tfr.ontheway.ArrivalInfoForm.form.planArriveTime.label')//'计划到达时间'
			}, {
				xtype : 'displayfield',
				name : 'destOrgName',
				columnWidth : .33,
				fieldLabel : ontheway.i18n('tfr.ontheway.ArrivalInfoForm.form.destOrgName.label')//'到达部门'
			}, {
				xtype : 'displayfield',
				name : 'actualArriveTime',
				columnWidth : .33,
				fieldLabel : ontheway.i18n('tfr.ontheway.ArrivalInfoForm.form.actualArriveTime.label')//'实际到达时间'
			}, {
				xtype : 'displayfield',
				name : 'status',
				columnWidth : .33,
				fieldLabel : ontheway.i18n('tfr.ontheway.ArrivalInfoForm.form.status.label'),//'车辆状态',
				renderer : function(value) {
					var store = Ext.create('Foss.ontheway.VehicleStatusStore'), record = store
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

Ext.define('Foss.ontheway.OnTheWayInfoForm', {
	extend : 'Ext.form.Panel',
	layout : 'column',
	frame : false,
	title : ontheway.i18n('tfr.ontheway.OnTheWayInfoForm.form.title'),//'在途信息',
	defaults : {
		margin : '5 5 5 5',
		allowBlank : true,
		xtype : 'displayfield'
	},
	items : [{
				xtype : 'displayfield',
				name : 'currentPlace',
				columnWidth : .33,
				fieldLabel : ontheway.i18n('tfr.ontheway.OnTheWayInfoForm.form.currentPlace.label')//'车辆所处路段'
			}, {
				xtype : 'displayfield',
				name : 'trackingTime',
				columnWidth : .33,
				fieldLabel :ontheway.i18n('tfr.ontheway.OnTheWayInfoForm.form.trackingTime.label')// '跟踪时间'
			}, {
				xtype : 'displayfield',
				name : 'estimateDepartTime',
				columnWidth : .33,
				fieldLabel :ontheway.i18n('tfr.ontheway.OnTheWayInfoForm.form.estimateDepartTime.label')// '预计到达时间'
			}, {
				xtype : 'displayfield',
				name : 'curentStatus',
				columnWidth : .33,
				renderer : function(value) {
					var store = Ext
							.create('Foss.ontheway.VehicleCurrentStatusStore'), record = store
							.findRecord('code', value);
					if (record != null) {
						return record.get('name');
					} else {
						return '';
					}
				},
				fieldLabel : ontheway.i18n('tfr.ontheway.OnTheWayInfoForm.form.curentStatus.label')//'车辆当前状态'
			}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

/** -----------------------------------------车辆明细信息----------------------------------------- */
Ext.define('Foss.ontheway.VehicleInfoWindow', {
	extend : 'Ext.window.Window',
	width : 900,
	height : 600,
	// 设置窗口是否可关闭
	closable : true,
	// 设置窗口关闭后是隐藏还是销毁
	closeAction : 'hide',
	title : ontheway.i18n('tfr.ontheway.VehicleInfoWindow.window.title'),//'车辆明细',
	modal : true,
	autoScroll : true,
	defaults : {
		margin : '10 10 10 30'
	},
	vehicleInfo : null,
	getVehicleInfo : function() {
		if (this.vehicleInfo == null) {
			this.vehicleInfo = Ext.create('Foss.ontheway.VehicleInfoForm');
		}
		return this.vehicleInfo;
	},
	relationInfo : null,
	getRelationInfo : function() {
		if (this.relationInfo == null) {
			this.relationInfo = Ext.create('Foss.ontheway.RelationInfoForm');
		}
		return this.relationInfo;
	},
	businessInfo : null,
	getBusinessInfo : function() {
		if (this.businessInfo == null) {
			this.businessInfo = Ext.create('Foss.ontheway.BusinessInfoForm');
		}
		return this.businessInfo;
	},
	taskBillSummaryGrid : null,
	getTaskBillSummaryGrid : function() {
		if (this.taskBillSummaryGrid == null) {
			this.taskBillSummaryGrid = Ext.create('Foss.ontheway.TaskBillSummaryGridPanel');
		}
		return this.taskBillSummaryGrid;
	},
	taskBillGrid : null,
	getTaskBillGrid : function() {
		if (this.taskBillGrid == null) {
			this.taskBillGrid = Ext.create('Foss.ontheway.TaskBillGridPanel');
		}
		return this.taskBillGrid;
	},
	departInfo : null,
	getDepartInfo : function() {
		if (this.departInfo == null) {
			this.departInfo = Ext.create('Foss.ontheway.DepartInfoForm');
		}
		return this.departInfo;
	},
	onthewayInfo : null,
	getArrivalInfo : function() {
		if (this.onthewayInfo == null) {
			this.onthewayInfo = Ext.create('Foss.ontheway.ArrivalInfoForm');
		}
		return this.onthewayInfo;
	},
	onTheWayInfo : null,
	getOnTheWayInfo : function() {
		if (this.onTheWayInfo == null) {
			this.onTheWayInfo = Ext.create('Foss.ontheway.OnTheWayInfoForm');
		}
		return this.onTheWayInfo;
	},
	bindData : function(record, cellIndex, rowIndex) {
//		var me = this;
//		var params = {
//			'vehicleInfoVO.queryEntity.id' : record.get('truckTaskDetailId'),
//			'vehicleInfoVO.queryEntity.vehicleNo' : record.get('vehicleNo'),
//			'vehicleInfoVO.queryEntity.isDepart' : 'no',
//			'vehicleInfoVO.queryEntity.driverCode' : record.get('driverCode')
//		}
//		Ext.Ajax.request({
//					url : ContextPath.TFR_EXECUTION+ '/departure/queryVehicleInfoInfo.action',
//					params : params,
//					success : function(response) {
//						var result = Ext.decode(response.responseText);
//						var model = Ext.ModelManager.create(
//								result.vehicleInfoVO.vehicleInfoEntity,
//								'Foss.ontheway.VehicleInfoModel');
//						// me.getForm().loadRecord(model);
//						me.getVehicleInfo().loadRecord(model);
//					}
//				});
//		Ext.Ajax.request({
//					url : ContextPath.TFR_EXECUTION+ '/departure/queryRelationInfo.action',
//					params : params,
//					success : function(response) {
//						var result = Ext.decode(response.responseText);
//						var model = Ext.ModelManager.create(
//								result.vehicleInfoVO.relationInfoEntity,
//								'Foss.ontheway.RelationInfoModel');
//						me.getRelationInfo().loadRecord(model);
//					}
//				});
//		Ext.Ajax.request({
//					url : ContextPath.TFR_EXECUTION+ '/departure/queryBusinessInfo.action',
//					params : params,
//					success : function(response) {
//						var result = Ext.decode(response.responseText);
//						var model = Ext.ModelManager.create(
//								result.vehicleInfoVO.businessInfoEntity,
//								'Foss.ontheway.BusinessInfoModel');
//						me.getBusinessInfo().loadRecord(model);
//					}
//				});
//		Ext.Ajax.request({
//			url : ContextPath.TFR_EXECUTION+ '/departure/queryTaskBill.action',
//			params : params,
//			success : function(response) {
//				var result = Ext.decode(response.responseText);
//				me.getTaskBillGrid().store.model = 'Foss.ontheway.TaskBillModel';
//				me.getTaskBillGrid().store.remoteSort = 'false';
//				me.getTaskBillGrid().store
//						.loadData(result.vehicleInfoVO.taskBillList);
//			}
//		});
//		Ext.Ajax.request({
//					url : ContextPath.TFR_EXECUTION+ '/departure/queryDepartInfo.action',
//					params : params,
//					success : function(response) {
//						var result = Ext.decode(response.responseText);
//						var model = Ext.ModelManager.create(
//								result.vehicleInfoVO.departInfoEntity,
//								'Foss.ontheway.DepartInfoModel');
//						me.getDepartInfo().loadRecord(model);
//					}
//				});
//		Ext.Ajax.request({
//					url : ContextPath.TFR_EXECUTION+ '/departure/queryArrivalInfo.action',
//					params : params,
//					success : function(response) {
//						var result = Ext.decode(response.responseText);
//						var model = Ext.ModelManager.create(
//								result.vehicleInfoVO.arrivalInfoEntity,
//								'Foss.ontheway.ArrivalInfoModel');
//						me.getArrivalInfo().loadRecord(model);
//					}
//				});
//		Ext.Ajax.request({
//					url : ContextPath.TFR_EXECUTION+ '/departure/queryOnTheWayInfo.action',
//					params : params,
//					success : function(response) {
//						var result = Ext.decode(response.responseText);
//						var model = Ext.ModelManager.create(
//								result.vehicleInfoVO.onTheWayInfoEntity,
//								'Foss.ontheway.OnTheWayInfoModel');
//						me.getOnTheWayInfo().loadRecord(model);
//					}
//				});

	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.items = [me.getVehicleInfo(), me.getRelationInfo(),
				me.getBusinessInfo(), {
					border : false,
					xtype : 'container',
					layout: {
						type: 'hbox',
						align: 'stretch'
					},
					defaults : {
						flex :1
					},
					items : [me.getTaskBillSummaryGrid(), me.getTaskBillGrid()]
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
								text : ontheway.i18n('tfr.ontheway.VehicleInfoWindow.window.button'),//'关闭',
								handler : function() {
									me.hide();
								}
							}]
				}]
		me.callParent([cfg]);
	}
});