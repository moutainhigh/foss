//车辆申请记录的模版
Ext.define('Foss.arrival.ArrivalModel', {
			extend : 'Ext.data.Model',
			idProperty : 'id',
			fields : [{
						name : 'id',
						type : 'string',
						hiddenField : true
					}, {
						name : 'truckTaskId',
						type : 'string',
						hiddenField : true
					}, {//车辆归属类型
						name : 'vehicleOwnerType',
						type : 'string',
						hiddenField : true
					}, {
						name : 'vehicleNo',
						type : 'string'
					}, {
						name : 'lineNo', 
						type : 'string'
					}, {
						name : 'origOrgCode',
						type : 'string'
					}, {
						name : 'destOrgCode',
						type : 'string'
					}, {
						name : 'origOrgName',
						type : 'string'
					}, {
						name : 'destOrgName',
						type : 'string'
					}, {
						name : 'departTime',
						type : 'string'
					}, {
						name : 'manualDepartTime',
						convert : dateConvert,
						type : 'date'
					}, {
						name : 'manualArriveTime',
						convert : dateConvert,
						type : 'date'
					}, {
						name : 'manualConfirmDepartTime',
						convert : dateConvert,
						type : 'date'
					}, {
						name : 'manualConfirmDepartTime',
						convert : dateConvert,
						type : 'date'
					}, {
						name : 'pdaDepartTime',
						convert : dateConvert,
						type : 'date'
					}, {
						name : 'gpsDepartTime',
						convert : dateConvert,
						type : 'date'
					}, {
						name : 'pdaArriveTime',
						convert : dateConvert,
						type : 'date'
					}, {
						name : 'gpsArriveTime',
						convert : dateConvert,
						type : 'date'
					}, {
						name : 'arrivalTime',
						type : 'string'
					}, {
						name : 'planArrivalTime',
						convert : dateConvert,
						type : 'date'
					}, {
						name : 'detailStatus',
						type : 'string'
					}, {
						name : 'arrivalStatus',
						type : 'string'
					}, {
						name : 'certificteBagStatus',
						type : 'string'
					}, {
						name : 'platformStatus',
						type : 'string'
					}, {
						name : 'fee',
						type : 'string'
					}, {
						name : 'loader',
						type : 'string'
					}, {
						name : 'truckArriveId',
						type : 'string'
					}, {
						name : 'lineVirtualCode',
						type : 'string'
					}, {
						name : 'frequencyNo',
						type : 'string'
					}, {//出发类型
						name : 'actualDepartType',
						type : 'string'
					}, {//到达类型
						name : 'actualArriveType',
						type : 'string'
					}, {//到达类型
						name : 'beCarLoad',
						type : 'string'
					}, {//到达类型
						name : 'truckOrgCode',
						type : 'string'
					}, {//到达类型
						name : 'businessType',
						type : 'string'
					}, {//到达类型
						name : 'isBright',
						type : 'string'
					},
					{//到达校验时间
						name : 'arrCheckTime',
						convert : dateConvert,
						type : 'date'
					},{//获取月台号
						name : 'platformCode',
						type : 'string'
					}

			]
		});
//车辆申请记录的模版
Ext.define('Foss.arrival.LeasedTruckModel', {
			extend : 'Ext.data.Model',
			idProperty : 'id',
			fields : [ {
						name : 'startTime',
						convert : function(value) {
							if (value != null && value != '') {
								var date = new Date(value);
								return Ext.Date.format(date, 'Y-m-d H:i');
							} else {
								return null;
							}
						}
					}, {
						name : 'totalFee'
					}, {
						name : 'totalTrucks'
					}, {
						name : 'endTime',
						type : 'string',
						convert : function(value) {
							if (value != null && value != '') {
								var date = new Date(value);
								return Ext.Date.format(date, 'Y-m-d H:i');
							} else {
								return null;
							}
						}
					}

			]
		});

Ext.define('Foss.arrival.ArrivalTypeGridStore', {
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
							code : 'ALL',
							name : '全部'
						},{
							code : 'ARRIVED',
							name : '已到'
						},{
							code : 'NOT_ARRIVED',
							name : '未到'
						}, {
							code : 'LATE_ARRIVED',
							name : '晚到'
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
//车辆业务类型
Ext.define('Foss.arrival.TruckBusinessStore', {
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
							code : 'ALL',
							name : '全部'
						},{
							code : 'LONG_DISTANCE',
							name : '长途'
						},{
							code : 'SHORT_DISTANCE',
							name : '短途'
						}, {
							code : 'PARTIALLINE',
							name : '偏线'
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
		
Ext.define('Foss.arrival.VehicleOwnerTypeStore', {
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
							code : '',
							name : '全部'
						},{
							code : 'Company',
							name : '自有'
						}, {
							code : 'Leased',
							name : '外请'
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


Ext.define('Foss.arrival.TimeTypeStore', {
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
							code : '0',
							name : '全部'
						},{
							code : '1',
							name : '实际出发时间'
						},{
							code : '2',
							name : '预计到达时间'
						}, {
							code : '3',
							name : '实际到达时间'
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
Ext.define('Foss.arrival.CertificationBagTypeStore', {
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
							code : '0',
							name : '全部'
						},{
							code : '1',
							name : '未上交'
						},{
							code : '2',
							name : '已上交'
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
		

//结算类型
Ext.define('Foss.arrival.ClearingTypeStore', {
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
							code : '',
							name : '--请选择--'
						},{
							code : '1',
							name : '已结清'
						}, {
							code : '2',
							name : '未结清'
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
		

//车辆状态
Ext.define('Foss.arrival.VehicleStatus', {
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
							code : '',
							name : '全部'
						},{
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
							name : '作废'
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
Ext.define('Foss.arrival.DepartureStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.arrival.ArrivalModel',
	pageSize : 10,
	autoLoad : false,
	proxy : {
		// 以JSON的方式加载数据
		type : 'ajax',
		actionMethods : 'POST',
		url : arrival.realPath('queryDepartureGrid.action'),
		reader : {
			type : 'json',
			root : 'arrivalVO.departureList',
			totalProperty : 'totalCount',
			successProperty : 'success'
		}
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	},
	listeners : {
		beforeload : function(store, operation, eOpts) {
			var queryParams = arrival.queryform.getValues();
			Ext.apply(operation, {
				params : {
					'arrivalVO.queryDepartureEntity.billNo' : queryParams.billNo,
					'arrivalVO.queryDepartureEntity.lineNo' : queryParams.lineNo,
					'arrivalVO.queryDepartureEntity.vehicleNo' : queryParams.vehicleNo,
					'arrivalVO.queryDepartureEntity.certificaterBagStatus' : queryParams.certificaterBagStatus,
					'arrivalVO.queryDepartureEntity.timeType' : queryParams.timeType ,
					'arrivalVO.queryDepartureEntity.startTime' : queryParams.startTime,
					'arrivalVO.queryDepartureEntity.endTime' : queryParams.endTime,
					'arrivalVO.queryDepartureEntity.truckOrgCode' : queryParams.truckOrgCode,
					'arrivalVO.queryDepartureEntity.platformStatus' : queryParams.platformStatus,
					'arrivalVO.queryDepartureEntity.businessType' : queryParams.businessType,
					'arrivalVO.queryDepartureEntity.arrivalStatus' : queryParams.arrivalStatus,
					'arrivalVO.queryDepartureEntity.origOrgCode' : queryParams.origOrgCode,
					'arrivalVO.queryDepartureEntity.destOrgCode' : queryParams.destOrgCode,
					'arrivalVO.queryDepartureEntity.clearingStatus' : queryParams.clearingStatus,
					'arrivalVO.queryDepartureEntity.vehicleOwnerType' : queryParams.vehicleOwnerType
				}
			});
		},
		load :function(store, operation, eOpts){
			var queryParams = arrival.queryform.getValues();
						var params = {
							'arrivalVO.queryDepartureEntity.billNo' : queryParams.billNo,
							'arrivalVO.queryDepartureEntity.lineNo' : queryParams.lineNo,
							'arrivalVO.queryDepartureEntity.vehicleNo' : queryParams.vehicleNo,
							'arrivalVO.queryDepartureEntity.certificaterBagStatus' : queryParams.certificaterBagStatus,
							'arrivalVO.queryDepartureEntity.timeType' : queryParams.timeType ,
							'arrivalVO.queryDepartureEntity.startTime' : queryParams.startTime,
							'arrivalVO.queryDepartureEntity.endTime' : queryParams.endTime,
							'arrivalVO.queryDepartureEntity.truckOrgCode' : queryParams.truckOrgCode,
							'arrivalVO.queryDepartureEntity.platformStatus' : queryParams.platformStatus,
							'arrivalVO.queryDepartureEntity.businessType' : queryParams.businessType,
							'arrivalVO.queryDepartureEntity.arrivalStatus' : queryParams.arrivalStatus,
							'arrivalVO.queryDepartureEntity.origOrgCode' : queryParams.origOrgCode,
							'arrivalVO.queryDepartureEntity.destOrgCode' : queryParams.destOrgCode,
							'arrivalVO.queryDepartureEntity.clearingStatus' : queryParams.clearingStatus,
							'arrivalVO.queryDepartureEntity.vehicleOwnerType' : queryParams.vehicleOwnerType
						}
						Ext.Ajax.request({
											url : arrival.realPath('queryLeasedDepartureTruck.action'),
											params : params,
											success : function(response) {
												var result = Ext.decode(response.responseText);
												var model = Ext.ModelManager.create(
														result.arrivalVO.leasedDepartureTruckDTO,
														'Foss.arrival.LeasedTruckModel');
												arrival.resultForm.loadRecord(model);
											}
										});
		}
	}
});			
Ext.define('Foss.arrival.ArrivalStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.arrival.ArrivalModel',
	pageSize : 10,
	autoLoad : false,
	proxy : {
		// 以JSON的方式加载数据
		type : 'ajax',
		actionMethods : 'POST',
		url : arrival.realPath('queryArrivalGrid.action'),
		reader : {
			type : 'json',
			root : 'arrivalVO.arrivalList',
			totalProperty : 'totalCount',
			successProperty : 'success'
		}
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	},
	listeners : {
		beforeload : function(store, operation, eOpts) {
			var queryParams = arrival.arriveform.getValues();
			Ext.apply(operation, {
				params : {
					'arrivalVO.queryArriveEntity.billNo' : queryParams.billNo,
					'arrivalVO.queryArriveEntity.lineNo' : queryParams.lineNo,
					'arrivalVO.queryArriveEntity.vehicleNo' : queryParams.vehicleNo,
					'arrivalVO.queryArriveEntity.certificaterBagStatus' : queryParams.certificaterBagStatus,
					'arrivalVO.queryArriveEntity.timeType' : queryParams.timeType ,
					'arrivalVO.queryArriveEntity.startTime' : queryParams.startTime,
					'arrivalVO.queryArriveEntity.endTime' : queryParams.endTime,
					'arrivalVO.queryArriveEntity.truckOrgCode' : queryParams.truckOrgCode,
					'arrivalVO.queryArriveEntity.platformStatus' : queryParams.platformStatus,
					'arrivalVO.queryArriveEntity.businessType' : queryParams.businessType,
					'arrivalVO.queryArriveEntity.arrivalStatus' : queryParams.arrivalStatus,
					'arrivalVO.queryArriveEntity.origOrgCode' : queryParams.origOrgCode,
					'arrivalVO.queryArriveEntity.destOrgCode' : queryParams.destOrgCode,
					'arrivalVO.queryArriveEntity.clearingStatus' : queryParams.clearingStatus,
					'arrivalVO.queryArriveEntity.vehicleOwnerType' : queryParams.vehicleOwnerType
				}
			});
		},
		load :function(store, operation, eOpts){
			var queryParams = arrival.queryform.getValues();
						var params = {
							'arrivalVO.queryArriveEntity.billNo' : queryParams.billNo,
							'arrivalVO.queryArriveEntity.lineNo' : queryParams.lineNo,
							'arrivalVO.queryArriveEntity.vehicleNo' : queryParams.vehicleNo,
							'arrivalVO.queryArriveEntity.certificaterBagStatus' : queryParams.certificaterBagStatus,
							'arrivalVO.queryArriveEntity.timeType' : queryParams.timeType ,
							'arrivalVO.queryArriveEntity.startTime' : queryParams.startTime,
							'arrivalVO.queryArriveEntity.endTime' : queryParams.endTime,
							'arrivalVO.queryArriveEntity.truckOrgCode' : queryParams.truckOrgCode,
							'arrivalVO.queryArriveEntity.platformStatus' : queryParams.platformStatus,
							'arrivalVO.queryArriveEntity.businessType' : queryParams.businessType,
							'arrivalVO.queryArriveEntity.arrivalStatus' : queryParams.arrivalStatus,
							'arrivalVO.queryArriveEntity.origOrgCode' : queryParams.origOrgCode,
							'arrivalVO.queryArriveEntity.destOrgCode' : queryParams.destOrgCode,
							'arrivalVO.queryArriveEntity.clearingStatus' : queryParams.clearingStatus,
							'arrivalVO.queryArriveEntity.vehicleOwnerType' : queryParams.vehicleOwnerType
						}
						Ext.Ajax.request({
											url : arrival.realPath('queryLeasedArriveTruck.action'),
											params : params,
											success : function(response) {
												var result = Ext.decode(response.responseText);
												var model = Ext.ModelManager.create(
														result.arrivalVO.leasedArriveTruckDTO,
														'Foss.arrival.LeasedTruckModel');
												arrival.resultarriveForm.loadRecord(model);
											}
										});
		}
	}
});
//车辆出发时间
Ext.define('Foss.arrival.DepartTimeForm',{
	extend : 'Ext.form.Panel',
			layout : 'column',
			frame : false,
			border : false,
			defaults : {
				margin : '0 5 5 5',
				columns : 4,
				xtype : 'displayfield',
				allowBlank : true
			},
			items : [ {
				fieldLabel :'外场手工放行时间',
				name : 'manualDepartTime',
				columnWidth :1,
			    labelWidth: 150,
				format : 'Y-m-d H:i:s'
			}, {
				fieldLabel : '手工发车时间',//'未结清金额合计（元）',
				name : 'manualConfirmDepartTime',
				columnWidth : 1,
			    labelWidth: 150
			}, {
				fieldLabel : '保安PDA放行时间',//'未结清金额合计（元）',
				name : 'pdaDepartTime',
				columnWidth : 1,
			    labelWidth: 150
			}, {
				fieldLabel :'GPS放行时间',//'未结清金额合计（元）',
				name : 'gpsDepartTime',
				columnWidth : 1,
			    labelWidth: 150
			}],
	bindData: function(record){
		//这里和查询装车任务共用一个查询理货员的方法
		this.getForm().findField('manualDepartTime').setValue(record.get('manualDepartTime')==null?'':Ext.Date.format(record.get('manualDepartTime'), 'Y-m-d H:i:s'));
		this.getForm().findField('manualConfirmDepartTime').setValue(record.get('manualConfirmDepartTime')==null?'':Ext.Date.format(record.get('manualConfirmDepartTime'), 'Y-m-d H:i:s'));
		this.getForm().findField('pdaDepartTime').setValue(record.get('pdaDepartTime')==null?'':Ext.Date.format(record.get('pdaDepartTime'), 'Y-m-d H:i:s'));
		this.getForm().findField('gpsDepartTime').setValue(record.get('gpsDepartTime')==null?'':Ext.Date.format(record.get('gpsDepartTime'), 'Y-m-d H:i:s'));
	},
	constructor: function(config){
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});
//车辆出发时间
Ext.define('Foss.arrival.ArriveTimeForm',{
	extend : 'Ext.form.Panel',
			layout : 'column',
			frame : false,
			border : false,
			defaults : {
				margin : '0 5 5 5',
				columns : 4,
				xtype : 'displayfield',
				allowBlank : true
			},
			items : [{
				fieldLabel : '手工到达时间',//'未结清金额合计（元）',
				name : 'manualArriveTime',
				columnWidth : 1,
			    labelWidth: 150
			}, {
				fieldLabel : '保安PDA到达时间',//'未结清金额合计（元）',
				name : 'pdaArriveTime',
				columnWidth : 1,
			    labelWidth: 150
			}, {
				fieldLabel :'GPS到达时间',//'未结清金额合计（元）',
				name : 'gpsArriveTime',
				columnWidth : 1,
			    labelWidth: 150
			}],
	bindData: function(record){
		//这里和查询装车任务共用一个查询理货员的方法
		this.getForm().findField('manualArriveTime').setValue(record.get('manualArriveTime')==null?'':Ext.Date.format(record.get('manualArriveTime'), 'Y-m-d H:i:s'));
		this.getForm().findField('pdaArriveTime').setValue(record.get('pdaArriveTime')==null?'':Ext.Date.format(record.get('pdaArriveTime'), 'Y-m-d H:i:s'));
		this.getForm().findField('gpsArriveTime').setValue(record.get('gpsArriveTime')==null?'':Ext.Date.format(record.get('gpsArriveTime'), 'Y-m-d H:i:s'));
	},
	constructor: function(config){
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

Ext.define('Foss.arrival.TruckActionDetailModel',{
	extend:'Ext.data.Model',
	fields: [
		{name: 'vehicleNo', type: 'string'},
		{name: 'lineName', type: 'string'},
		{name: 'operatorName' , type: 'string'},
		{name: 'operatorCode' , type: 'string'},
		{name: 'orgName' , type: 'string'},
		{name: 'orgCode' , type: 'string'},
		{name: 'createTime' , convert : dateConvert, type : 'date'}
	]
});

//Store
Ext.define('Foss.arrival.TruckActionDetailStore',{
	extend: 'Ext.data.Store',
	model: 'Foss.arrival.TruckActionDetailModel',
	proxy: {
		type: 'memory',
		reader: {
			type: 'json',
			root: 'items'
		}
	}
});

//取消车辆出发到达记录
Ext.define('Foss.arrival.TruckActionDetailGrid', {
	extend : 'Ext.grid.Panel',
	height : 250,
	autoScroll : false,
	emptyText:arrival.i18n('tfr.arrival.ArrivalGrid.grid.empty.title'),//"查询结果为空",
	columnLines : true,
	frame : true,
	columns : [{
		header : arrival.i18n('tfr.arrival.TruckActionDetail.grid.vehicleNo.label'),//'车牌号',
		width : 120,
		dataIndex : 'vehicleNo'
	}, {
		header : arrival.i18n('tfr.arrival.TruckActionDetail.grid.lineName.label'),//'线路',
		width : 240,
		dataIndex : 'lineName'
	}, {
		header : arrival.i18n('tfr.arrival.TruckActionDetail.grid.operatorName.label'),//'操作人',
		width : 100,
		dataIndex : 'operatorName'
	}, {
		header : arrival.i18n('tfr.arrival.TruckActionDetail.grid.orgName.label'),//'操作人部门',
		width : 140,
		dataIndex : 'orgName'
	}, {
		header :arrival.i18n('tfr.arrival.TruckActionDetail.grid.createTime.label'),// '操作时间',
		xtype : 'datecolumn',
		format : 'Y-m-d H:i:s',
		width : 140,
		dataIndex : 'createTime'
	}],
	constructor : function(config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.arrival.TruckActionDetailStore');
		me.callParent([cfg]);
	}
});

//本部门出发的数据
Ext.define('Foss.arrival.DepartureGrid', {
	extend : 'Ext.grid.Panel',
	height : 390,
	autoScroll : false,
	emptyText:arrival.i18n('tfr.arrival.ArrivalGrid.grid.empty.title'),//"查询结果为空",
	columnLines : true,
	frame : true,
	viewConfig: {
        stripeRows: false,
		getRowClass: function(record, rowIndex, rp, ds) {
			var isBright = record.get('isBright');
			if(isBright=='Y')
			{
    			return 'predeliver_notice_customer_row_purole';
			}else
			{
			    return 'predeliver_notice_customer_row_white';
			}
		}
	},
	columns : [ {
		header : arrival.i18n('tfr.arrival.ArrivalGrid.grid.vehicleNo.label'),//'车牌号',
		width : 80,
		dataIndex : 'vehicleNo',
		windowClassName : 'Foss.arrival.VehicleInfoWindow',
		xtype : 'opentipwindowcolumn',
		tipConfig: {
				width: 150,
				height: 30,
		        html : arrival.i18n('tfr.arrival.ArrivalGrid.grid.vehicleInfo.label'),//'请查看车辆明细信息',
				hideDelay: 2000
			}
	}, {
		header : arrival.i18n('tfr.arrival.ArrivalGrid.grid.lineNo.label'),//'线路',
		width : 100,
		dataIndex : 'lineNo'
	}, {
		header : arrival.i18n('tfr.arrival.ArrivalGrid.grid.origOrgName.label'),//'出发部门',
		width : 80,
		dataIndex : 'origOrgName'
	}, {
		header : arrival.i18n('tfr.arrival.ArrivalGrid.grid.destOrgName.label'),//'到达部门',
		width : 100,
		dataIndex : 'destOrgName'
	}, {
		header :arrival.i18n('tfr.arrival.ArrivalGrid.grid.departTime.label'),// '出发时间（获取类型）',
		width : 140,
		dataIndex : 'departTime',
		xtype: 'tipcolumn',
		tipConfig: {
	        //如果要设置宽度，一定要修改maxWidth值，因为tip的maxWidth最大只有300
			maxWidth: 450,
			width: 450,
			height: 150,
	        //Tip的Body是否随鼠标移动
			trackMouse: true,
			//Tip的隐藏延迟时间(单位:ms)
			hideDelay: 2000
		},
		//配置tip内引用的自定义组件类名
		tipBodyElement:'Foss.arrival.DepartTimeForm'
	}, {
		header :arrival.i18n('tfr.arrival.ArrivalGrid.grid.arrivalTime.label'),// '到达时间（获取类型）',
		width : 140,
		dataIndex : 'arrivalTime'
	}, {
		header :arrival.i18n('tfr.arrival.ArrivalGrid.grid.planArrivalTime.label'),// '预计到达时间',
		xtype : 'datecolumn',
		format : 'Y-m-d H:i:s',
		width : 140,
		dataIndex : 'planArrivalTime'
	}, {
		header : arrival.i18n('tfr.arrival.ArrivalGrid.grid.arrivalStatus.label'),//'到达情况',
		width : 80,
		dataIndex : 'arrivalStatus',
		renderer : function(value) {
			var store = Ext.create('Foss.arrival.ArrivalTypeGridStore'), record = store
					.findRecord('code', value);
			if (record != null) {
				return record.get('name');
			} else {
				return arrival.i18n('tfr.arrival.ArrivalGrid.grid.notArrive.label')//'未到'
			}
		}
	}, {
		header :arrival.i18n('tfr.arrival.ArrivalGrid.grid.certificteBagStatus.label'),// '证件包情况',
		width : 80,
		dataIndex : 'certificteBagStatus'
	}, {
		header :arrival.i18n('tfr.arrival.ArrivalGrid.grid.platformStatus.label'),// '月台分配',
		width : 80,
		dataIndex : 'platformStatus'
	}, {
		header : arrival.i18n('tfr.arrival.ArrivalGrid.grid.fee.label'),//'未结清金额',
		width : 80,
		dataIndex : 'fee'
	}, {
		header :arrival.i18n('tfr.arrival.ArrivalGrid.grid.loader.label'),// '装车人',
		width : 80,
		dataIndex : 'loader'
	}, {
		header :arrival.i18n('tfr.arrival.ArrivalGrid.grid.loader.label'),// '装车人',
		width : 0,
		dataIndex : 'id'
	}, {
		width : 0,
		dataIndex : 'manualConfirmDepartTime'
	}, {
		header :arrival.i18n('tfr.arrival.QueryForm.form.vehicleOwnerType.label'),// '车辆归属类型',
		width : 80,
		dataIndex : 'vehicleOwnerType',
		renderer : function(value) {
			var store = Ext.create('Foss.arrival.VehicleOwnerTypeStore'), record = store
					.findRecord('code', value);
			if (record != null) {
				return record.get('name');
			} else {
				return '';//'未到'
			}
		}
	}, {
		header :'是否整车',// '车辆归属类型',
		width : 80,
		dataIndex : 'beCarLoad',
		renderer : function(value) {
			if (value == 'Y') {
				return '是';
			} else {
				return '否';//'未到'
			}
		}
	}, {
		width : 0,
		dataIndex : 'isBright'
	}],
	listeners: {
		'select': function(field, records, eOpts) {
			var params = {
				'arrivalVO.operationDTO.id' : records.get('id')
			};
			Ext.Ajax.request({
				url : arrival.realPath('queryCancelDepartureGrid.action'),
				params : params,
				success : function(response) {
					var result = Ext.decode(response.responseText);
					var truckActionDetailDtos = result.arrivalVO.truckActionDetailDtos;
					arrival.cancelDepartGrid.store.loadData(truckActionDetailDtos);
				},
				exception : function(response) {
					var result = Ext.decode(response.responseText);
					if(result.message){
						Ext.MessageBox.alert(arrival.i18n('tfr.arrival.DepartGrid.tips.label'),result.message);
					}else
					{
						Ext.MessageBox.alert(arrival.i18n('tfr.arrival.DepartGrid.tips.label'),arrival.i18n(result.message));
					}
				}
			});
		}
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.selModel = Ext.create('Ext.selection.RadioModel');
		me.store = Ext.create('Foss.arrival.DepartureStore');
		me.tbar = [{
					xtype : 'button',
					plugins:Ext.create('Deppon.ux.ButtonLimitingPlugin',{
						seconds: 5
					}),
					text : arrival.i18n('tfr.arrival.ArrivalGrid.grid.departconfirm.button'),//'发车确认',
					disabled:!arrival.isPermission('arrival/departconfirmbutton.action'),
					hidden:!arrival.isPermission('arrival/departconfirmbutton.action'),
					gridContainer : this,
					handler:function(){
						var rowObjs = me.getSelectionModel().getSelection();
						if(rowObjs.length!=1)
						{
						  Ext.MessageBox.alert(arrival.i18n('tfr.arrival.DepartGrid.tips.label'),arrival.i18n('tfr.arrival.ArrivalGrid.grid.departconfirm.atleastOne.tips'));
						  return false;
						}
						//出发部门或者到达部门为当前部门时，才能进行证件包的上交
						/*if(rowObjs[0].get('origOrgCode')!=FossUserContext.getCurrentDept().code)
						{
						   Ext.MessageBox.alert(arrival.i18n('tfr.arrival.DepartGrid.tips.label'),arrival.i18n('tfr.arrival.ArrivalGrid.grid.departconfirm.notcurrentorg.tips'));
						   return false;
						}*/
						var params = {
							'arrivalVO.operationDTO.id' : rowObjs[0].get('id'),
							'arrivalVO.operationDTO.vehicleNo' : rowObjs[0].get('vehicleNo'),
							'arrivalVO.operationDTO.truckTaskId' : rowObjs[0].get('truckTaskId'),
							'arrivalVO.operationDTO.planArrivalTime' : rowObjs[0].get('planArrivalTime'),
							'arrivalVO.operationDTO.arrivalStatus' : rowObjs[0].get('arrivalStatus'),
							'arrivalVO.operationDTO.truckArriveId' : rowObjs[0].get('truckArriveId'),
							'arrivalVO.operationDTO.vehicleStatus' : rowObjs[0].get('detailStatus'),
							'arrivalVO.operationDTO.origOrgCode' : rowObjs[0].get('origOrgCode'),
							'arrivalVO.operationDTO.actualDepartType' : rowObjs[0].get('actualDepartType'),
							'arrivalVO.operationDTO.businessType' : rowObjs[0].get('businessType'),
							'arrivalVO.operationDTO.beCarLoad' : rowObjs[0].get('beCarLoad'),
							'arrivalVO.operationDTO.manualConfirmDepartTime' : rowObjs[0].get('manualConfirmDepartTime'),
							'arrivalVO.operationDTO.destOrgCode' : rowObjs[0].get('destOrgCode')
						};
						
						//发车前先校验
						
						//发车确认前校验
						Ext.Ajax.request({
											url : arrival.realPath('departConfirm.action'),
											params : params,
											success : function(response) {
												arrival.departurePagingBar.moveFirst();
												Ext.ux.Toast.msg(arrival.i18n('tfr.arrival.DepartGrid.tips.label'), arrival.i18n('tfr.arrival.ArrivalGrid.grid.departconfirm.ok.tips'), 'ok', 3000);
											},
											exception : function(response) {
							    				var result = Ext.decode(response.responseText);
							    				if(result.message){
							    					Ext.MessageBox.alert(arrival.i18n('tfr.arrival.DepartGrid.tips.label'),result.message);
							    				}else
							    				{
							    					Ext.MessageBox.alert(arrival.i18n('tfr.arrival.DepartGrid.tips.label'),arrival.i18n(result.message));
							    				}
							    			}
										});
					}
				}, {
					xtype : 'button',
					text : arrival.i18n('tfr.arrival.ArrivalGrid.grid.cancleDepart.button'),//'取消出发',
					gridContainer : this,
					disabled:!arrival.isPermission('arrival/cancleDepartButton'),
					hidden:!arrival.isPermission('arrival/cancleDepartButton'),
					handler:function(){
						//拼装条件						
						var rowObjs = me.getSelectionModel().getSelection();
						if(rowObjs.length!=1)
						{
						  Ext.MessageBox.alert(arrival.i18n('tfr.arrival.DepartGrid.tips.label'),arrival.i18n('tfr.arrival.ArrivalGrid.grid.cancle.atleastOne.tips'));
						  return false;
						}
						var params = {
							'arrivalVO.operationDTO.id' : rowObjs[0].get('id'),
							'arrivalVO.operationDTO.vehicleNo' : rowObjs[0].get('vehicleNo'),
							'arrivalVO.operationDTO.truckTaskId' : rowObjs[0].get('truckTaskId'),
							'arrivalVO.operationDTO.planArrivalTime' : rowObjs[0].get('planArrivalTime'),
							'arrivalVO.operationDTO.arrivalStatus' : rowObjs[0].get('arrivalStatus'),
							'arrivalVO.operationDTO.vehicleStatus' : rowObjs[0].get('detailStatus'),
							'arrivalVO.operationDTO.origOrgCode' : rowObjs[0].get('origOrgCode'),
							'arrivalVO.operationDTO.destOrgCode' : rowObjs[0].get('destOrgCode'),
							'arrivalVO.operationDTO.actualDepartType' : rowObjs[0].get('actualDepartType'),
							'arrivalVO.operationDTO.actualArriveType' : rowObjs[0].get('actualArriveType'),
							'arrivalVO.operationDTO.beCarLoad' : rowObjs[0].get('beCarLoad'),
							'arrivalVO.operationDTO.destOrgCode' : rowObjs[0].get('destOrgCode'),
							'arrivalVO.operationDTO.actionType' : 'CANCEL_DEPART'
						};
//						//出发部门或者到达部门为当前部门时，才能进行证件包的上交
						/*if(!(rowObjs[0].get('origOrgCode')==FossUserContext.getCurrentDept().code||rowObjs[0].get('destOrgCode')==FossUserContext.getCurrentDept().code))
						{
						   Ext.MessageBox.alert(arrival.i18n('tfr.arrival.DepartGrid.tips.label'),arrival.i18n('tfr.arrival.ArrivalGrid.grid.cancle.notcurrentorg.tips'));
						   return false;
						}*/
						Ext.Msg.confirm(arrival.i18n('tfr.arrival.DepartGrid.tips.label'), arrival.i18n('tfr.arrival.ArrivalGrid.grid.cancle.confirm.notcurrentorg.tips'), function(optional){
						  if(optional == 'yes'){
							//取消出发前校验
							Ext.Ajax.request({
												url : arrival.realPath('cancleConfirm.action'),
//												timeout: 300000, TODO
												params : params,
												success : function(response) {
													arrival.departurePagingBar.moveFirst();
													Ext.ux.Toast.msg(arrival.i18n('tfr.arrival.DepartGrid.tips.label'), arrival.i18n('tfr.arrival.ArrivalGrid.grid.cancle.ok.tips'), 'ok', 3000);
												},
												exception :function(response)
												{
												    var result = Ext.decode(response.responseText);
												    Ext.MessageBox.alert(arrival.i18n('tfr.arrival.DepartGrid.tips.label'),result.message);
												}
											});
						  }
						});
					}
				},{
					xtype : 'button',
					text : arrival.i18n('tfr.arrival.ArrivalGrid.grid.outsidevehiclecharge.button'),//'调整外请车费用',
					gridContainer : this,
					disabled: !arrival.isPermission('arrival/outsidevehiclechargebutton.action'),
					hidden: !arrival.isPermission('arrival/outsidevehiclechargebutton.action'),
					handler:function(){
						//拼装条件						
						var rowObjs = me.getSelectionModel().getSelection();
						if(rowObjs.length!=1)
						{
						  Ext.MessageBox.alert(arrival.i18n('tfr.arrival.DepartGrid.tips.label'),arrival.i18n('tfr.arrival.ArrivalGrid.grid.outsidevehiclecharge.atleastOne.tips'));
						  return false;
						}
						var vehicleNo;
						if(rowObjs.length>0)
						{
							vehicleNo = rowObjs[0].get('vehicleNo');
						}
//						//出发部门或者到达部门为当前部门时，才能进行证件包的上交
//						if(rowObjs[0].get('destOrgCode')!=FossUserContext.getCurrentDept().code)
//						{
//						   Ext.MessageBox.alert(arrival.i18n('tfr.arrival.DepartGrid.tips.label'),arrival.i18n('tfr.arrival.ArrivalGrid.grid.outsidevehiclecharge.notcurrentorg.tips'));
//						   return false;
//						}
						if(rowObjs[0].get('vehicleOwnerType')!='Leased')
						{
						   Ext.MessageBox.alert(arrival.i18n('tfr.arrival.DepartGrid.tips.label'),arrival.i18n('tfr.arrival.ArrivalGrid.grid.outsidevehicle.notcurrentorg.tips'));
						   return false;
						}
						addTab('T_load-outsidevehiclechargeindex',// 对应打开的目标页面js的onReady里定义的renderTo
								arrival.i18n('tfr.arrival.ArrivalGrid.grid.outsidevehiclecharge.button'),// 打开的Tab页的标题
								ContextPath.TFR_EXECUTION+"/load/outsidevehiclechargeindex.action?vehicleNo="+vehicleNo);// 对应的页面URL，可以在url后使用?x=123这种形式传参
					}
				},{
				xtype : 'button',
				text : '导出',
				name : 'export',
				disabled:!arrival.isPermission('arrival/departexportbutton.action'),
				hidden:!arrival.isPermission('arrival/departexportbutton.action'),
				handler : function(){
					var records = me.getStore().getRange(0,
							me.getStore().data.items.length);
					if(records.length<1)
					{
					  Ext.MessageBox.alert(arrival.i18n('tfr.arrival.DepartGrid.tips.label'),'没有查询到记录，不能导出');
					  return false;
					}
					var queryParams = arrival.queryform.getValues();
//					var queryParams = unload.unloadtaskdetailquery.loadTaskForm.getForm().getValues();
					if(!Ext.fly('downloadAttachFileForm')){
						    var frm = document.createElement('form');
						    frm.id = 'downloadAttachFileForm';
						    frm.style.display = 'none';
						    document.body.appendChild(frm);
					}		
					var array = new Array();
					for (var i = 0; i < records.length; i++) {
						array.push(records[i].data.id);
					}
					Ext.Ajax.request({
						url:arrival.realPath('exportUnloadWayBillByTaskNo.action'),
						form: Ext.fly('downloadAttachFileForm'),
						method : 'POST',
						params : {
							'arrivalVO.ids' : array
						},
		    			isUpload: true,
		    			exception : function(response) {
		    				var result = Ext.decode(response.responseText);
		    				top.Ext.MessageBox.alert('提示',result.message);
		    			}
					});
				}
			}];

		me.bbar = Ext.create('Deppon.StandardPaging', {
					store : me.store,
					pageSize : 10,
					maximumSize : 800,
					plugins : Ext.create('Deppon.ux.PageSizePlugin', {
						sizeList : [['10', 10], ['50', 50], ['100', 100], ['800', 800]]
					})
				});
		arrival.departurePagingBar = me.bbar;
		me.callParent([cfg]);
	}
});


//本部门到达的数据
Ext.define('Foss.arrival.ArrivalGrid', {
	extend : 'Ext.grid.Panel',
	height : 390,
	autoScroll : false,
	emptyText:arrival.i18n('tfr.arrival.ArrivalGrid.grid.empty.title'),//"查询结果为空",
	columnLines : true,
	frame : true,
	columns : [ {
		header : arrival.i18n('tfr.arrival.ArrivalGrid.grid.vehicleNo.label'),//'车牌号',
		width : 80,
		dataIndex : 'vehicleNo',
		windowClassName : 'Foss.arrival.VehicleInfoWindow',
		xtype : 'opentipwindowcolumn',
		tipConfig: {
				width: 150,
				height: 30,
		        html : arrival.i18n('tfr.arrival.ArrivalGrid.grid.vehicleInfo.label'),//'请查看车辆明细信息',
				hideDelay: 2000
			}
	}, {
		header : arrival.i18n('tfr.arrival.ArrivalGrid.grid.lineNo.label'),//'线路',
		width : 100,
		dataIndex : 'lineNo'
	}, {
		header : arrival.i18n('tfr.arrival.ArrivalGrid.grid.origOrgName.label'),//'出发部门',
		width : 80,
		dataIndex : 'origOrgName'
	}, {
		header : arrival.i18n('tfr.arrival.ArrivalGrid.grid.destOrgName.label'),//'到达部门',
		width : 100,
		dataIndex : 'destOrgName'
	}, {
		header :arrival.i18n('tfr.arrival.ArrivalGrid.grid.departTime.label'),// '出发时间（获取类型）',
		width : 140,
		dataIndex : 'departTime',
		xtype: 'tipcolumn',
		tipConfig: {
	        //如果要设置宽度，一定要修改maxWidth值，因为tip的maxWidth最大只有300
			maxWidth: 450,
			width: 450,
			height: 150,
	        //Tip的Body是否随鼠标移动
			trackMouse: true,
			//Tip的隐藏延迟时间(单位:ms)
			hideDelay: 2000
		},
		//配置tip内引用的自定义组件类名
		tipBodyElement:'Foss.arrival.DepartTimeForm'
	}, {
		header :arrival.i18n('tfr.arrival.ArrivalGrid.grid.arrivalTime.label'),// '到达时间（获取类型）',
		width : 140,
		dataIndex : 'arrivalTime'
	}, {
		header :arrival.i18n('tfr.arrival.ArrivalGrid.grid.planArrivalTime.label'),// '预计到达时间',
		xtype : 'datecolumn',
		format : 'Y-m-d H:i:s',
		width : 140,
		dataIndex : 'planArrivalTime'
	}, {
		header :arrival.i18n('tfr.arrival.ArrivalGrid.grid.arrCheckTime.label'),// '入场时间',
		xtype : 'datecolumn',
		format : 'Y-m-d H:i:s',
		width : 140,
		dataIndex : 'arrCheckTime'
	},{
		header : arrival.i18n('tfr.arrival.ArrivalGrid.grid.platformCode.label'),//'获取月台号',
		width : 80,
		dataIndex : 'platformCode'
	},{
		header : arrival.i18n('tfr.arrival.ArrivalGrid.grid.arrivalStatus.label'),//'到达情况',
		width : 80,
		dataIndex : 'arrivalStatus',
		renderer : function(value) {
			var store = Ext.create('Foss.arrival.ArrivalTypeGridStore'), record = store
					.findRecord('code', value);
			if (record != null) {
				return record.get('name');
			} else {
				return arrival.i18n('tfr.arrival.ArrivalGrid.grid.notArrive.label')//'未到'
			}
		}
	}, {
		header :arrival.i18n('tfr.arrival.ArrivalGrid.grid.certificteBagStatus.label'),// '证件包情况',
		width : 80,
		dataIndex : 'certificteBagStatus'
	}, /*{
		header :arrival.i18n('tfr.arrival.ArrivalGrid.grid.platformStatus.label'),// '月台分配',
		width : 80,
		dataIndex : 'platformStatus'
	}, */{
		header : arrival.i18n('tfr.arrival.ArrivalGrid.grid.fee.label'),//'未结清金额',
		width : 80,
		dataIndex : 'fee'
	}, {
		header :arrival.i18n('tfr.arrival.ArrivalGrid.grid.loader.label'),// '装车人',
		width : 80,
		dataIndex : 'loader'
	}, {
		header :arrival.i18n('tfr.arrival.QueryForm.form.vehicleOwnerType.label'),// '车辆归属类型',
		width : 80,
		dataIndex : 'vehicleOwnerType',
		renderer : function(value) {
			var store = Ext.create('Foss.arrival.VehicleOwnerTypeStore'), record = store
					.findRecord('code', value);
			if (record != null) {
				return record.get('name');
			} else {
				return '';//'未到'
			}
		}
	}, {
		header :'是否整车',// '车辆归属类型',
		width : 80,
		dataIndex : 'beCarLoad',
		renderer : function(value) {
			if (value == 'Y') {
				return '是';
			} else {
				return '否';//'未到'
			}
		}
	}, {
		header :arrival.i18n('tfr.arrival.ArrivalGrid.grid.loader.label'),// '装车人',
		width : 0,
		dataIndex : 'id'
	}, {
		width : 0,
		dataIndex : 'manualArriveTime'
	}],
	listeners: {
		'select': function(field, records, eOpts) {
			var params = {
				'arrivalVO.operationDTO.id' : records.get('id')
			};
			Ext.Ajax.request({
				url : arrival.realPath('queryCancelArrivalGrid.action'),
				params : params,
				success : function(response) {
					var result = Ext.decode(response.responseText);
					var truckActionDetailDtos = result.arrivalVO.truckActionDetailDtos;
					arrival.cancelArrivalGrid.store.loadData(truckActionDetailDtos);
				},
				exception : function(response) {
					var result = Ext.decode(response.responseText);
					if(result.message){
						Ext.MessageBox.alert(arrival.i18n('tfr.arrival.DepartGrid.tips.label'),result.message);
					}else
					{
						Ext.MessageBox.alert(arrival.i18n('tfr.arrival.DepartGrid.tips.label'),arrival.i18n(result.message));
					}
				}
			});
		}
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.selModel = Ext.create('Ext.selection.RadioModel');
		me.store = Ext.create('Foss.arrival.ArrivalStore');
		me.tbar = [ {
					xtype : 'button',
					plugins:Ext.create('Deppon.ux.ButtonLimitingPlugin',{
						seconds: 5
					}),
					text : arrival.i18n('tfr.arrival.ArrivalGrid.grid.arriveconfirm.button'),//'到达确认',
					gridContainer : this,
					disabled : !arrival.isPermission('arrival/arriveconfirmbutton.action'),
					hidden : !arrival.isPermission('arrival/arriveconfirmbutton.action'),
					handler:function(){
						//拼装条件						
						var rowObjs = me.getSelectionModel().getSelection();
						if(rowObjs.length!=1)
						{
						  Ext.MessageBox.alert(arrival.i18n('tfr.arrival.DepartGrid.tips.label'),arrival.i18n('tfr.arrival.ArrivalGrid.grid.arriveconfirm.atleastOne.tips'));
						  return false;
						}
						//出发部门或者到达部门为当前部门时，才能进行到达确认
						//if(rowObjs[0].get('destOrgCode')!=FossUserContext.getCurrentDept().code)
						//{
						//   Ext.MessageBox.alert(arrival.i18n('tfr.arrival.DepartGrid.tips.label'),arrival.i18n('tfr.arrival.ArrivalGrid.grid.arriveconfirm.notcurrentorg.tips'));
						//   return false;
						//}
						var params = {
							'arrivalVO.operationDTO.id' : rowObjs[0].get('id'),
							'arrivalVO.operationDTO.vehicleNo' : rowObjs[0].get('vehicleNo'),
							'arrivalVO.operationDTO.truckTaskId' : rowObjs[0].get('truckTaskId'),
							'arrivalVO.operationDTO.planArrivalTime' : rowObjs[0].get('planArrivalTime'),
							'arrivalVO.operationDTO.arrivalStatus' : rowObjs[0].get('arrivalStatus'),
							'arrivalVO.operationDTO.truckArriveId' : rowObjs[0].get('truckArriveId'),
							'arrivalVO.operationDTO.vehicleStatus' : rowObjs[0].get('detailStatus'),
							'arrivalVO.operationDTO.origOrgCode' : rowObjs[0].get('origOrgCode'),
							'arrivalVO.operationDTO.businessType' : rowObjs[0].get('businessType'),
							'arrivalVO.operationDTO.beCarLoad' : rowObjs[0].get('beCarLoad'),
							'arrivalVO.operationDTO.manualArriveTime' : rowObjs[0].get('manualArriveTime'),
							'arrivalVO.operationDTO.destOrgCode' : rowObjs[0].get('destOrgCode')
						};
						//到达确认前校验
						Ext.Ajax.request({
											url : arrival.realPath('arrivalConfirm.action'),
//											timeout: 300000, TODO
											params : params,
											success : function(response) {
												var result = Ext.decode(response.responseText);
												arrival.arrivePagingBar.moveFirst();
												Ext.ux.Toast.msg(arrival.i18n('tfr.arrival.DepartGrid.tips.label'), arrival.i18n('tfr.arrival.ArrivalGrid.grid.arriveconfirm.ok.tips'), 'ok', 3000);
												if(result.arrivalVO.hasAgency=='Y')
												{
												  Ext.Msg.confirm("提示信息",'运单存在代办事项，是否需要继续处理', function(optional){
												    if(optional == 'yes'){
												    	var params = {
															arrivalVO : {
																detailIds : result.arrivalVO.detailIds
															}
														};
												    	Ext.Ajax.request({
															url : arrival.realPath('handleLabeledGoods.action'),
															jsonData : params,
															success : function(response) {
															  addTab('T_unload-changelabelgoodsindex',// 对应打开的目标页面js的onReady里定义的renderTo
																		'查询重贴标签货物',// 打开的Tab页的标题
																		encodeURI(ContextPath.TFR_EXECUTION+ '/unload/changelabelgoodsindex.action'));
															},
															exception :function(response)
															{
															    var result = Ext.decode(response.responseText);
															    Ext.MessageBox.alert(arrival.i18n('tfr.arrival.DepartGrid.tips.label'),result.message);
															}});
												    }
												  });
												}
											},
											exception :function(response)
											{
											    var result = Ext.decode(response.responseText);
											    Ext.MessageBox.alert(arrival.i18n('tfr.arrival.DepartGrid.tips.label'),result.message);
											}
										});
					}
				}, {
					xtype : 'button',
					plugins:Ext.create('Deppon.ux.ButtonLimitingPlugin',{
						seconds: 5
					}),
					text : arrival.i18n('tfr.arrival.ArrivalGrid.grid.cancleArrival.button'),//'取消到达',
					gridContainer : this,
					disabled:!arrival.isPermission('arrival/cancleArrivalButton'),
					hidden:!arrival.isPermission('arrival/cancleArrivalButton'),
					handler:function(){
						//拼装条件						
						var rowObjs = me.getSelectionModel().getSelection();
						if(rowObjs.length!=1)
						{
						  Ext.MessageBox.alert(arrival.i18n('tfr.arrival.DepartGrid.tips.label'),arrival.i18n('tfr.arrival.ArrivalGrid.grid.cancle.atleastOne.tips'));
						  return false;
						}
						var params = {
							'arrivalVO.operationDTO.id' : rowObjs[0].get('id'),
							'arrivalVO.operationDTO.vehicleNo' : rowObjs[0].get('vehicleNo'),
							'arrivalVO.operationDTO.truckTaskId' : rowObjs[0].get('truckTaskId'),
							'arrivalVO.operationDTO.planArrivalTime' : rowObjs[0].get('planArrivalTime'),
							'arrivalVO.operationDTO.arrivalStatus' : rowObjs[0].get('arrivalStatus'),
							'arrivalVO.operationDTO.vehicleStatus' : rowObjs[0].get('detailStatus'),
							'arrivalVO.operationDTO.origOrgCode' : rowObjs[0].get('origOrgCode'),
							'arrivalVO.operationDTO.destOrgCode' : rowObjs[0].get('destOrgCode'),
							'arrivalVO.operationDTO.actualDepartType' : rowObjs[0].get('actualDepartType'),
							'arrivalVO.operationDTO.actualArriveType' : rowObjs[0].get('actualArriveType'),
							'arrivalVO.operationDTO.beCarLoad' : rowObjs[0].get('beCarLoad'),
							'arrivalVO.operationDTO.destOrgCode' : rowObjs[0].get('destOrgCode'),
							'arrivalVO.operationDTO.actionType' : 'CANCEL_ARRIVAL'
						};
//						//出发部门或者到达部门为当前部门时，才能进行证件包的上交
						/*if(!(rowObjs[0].get('origOrgCode')==FossUserContext.getCurrentDept().code||rowObjs[0].get('destOrgCode')==FossUserContext.getCurrentDept().code))
						{
						   Ext.MessageBox.alert(arrival.i18n('tfr.arrival.DepartGrid.tips.label'),arrival.i18n('tfr.arrival.ArrivalGrid.grid.cancle.notcurrentorg.tips'));
						   return false;
						}*/
						Ext.Msg.confirm(arrival.i18n('tfr.arrival.DepartGrid.tips.label'), arrival.i18n('tfr.arrival.ArrivalGrid.grid.cancle.confirm.notcurrentorg.tips'), function(optional){
						  if(optional == 'yes'){
							  var myMask = new Ext.LoadMask(Ext.getBody(),  {msg:'正在取消，请稍等 ...'});
								myMask.show();
							//取消到达前校验
							Ext.Ajax.request({
												url : arrival.realPath('cancleConfirm.action'),
//												timeout: 300000, TODO
												params : params,
												success : function(response) {
													arrival.arrivePagingBar.moveFirst();
													Ext.ux.Toast.msg(arrival.i18n('tfr.arrival.DepartGrid.tips.label'), arrival.i18n('tfr.arrival.ArrivalGrid.grid.cancle.ok.tips'), 'ok', 3000);
													myMask.hide();
												},
												exception :function(response)
												{
												    var result = Ext.decode(response.responseText);
												     Ext.MessageBox.alert(arrival.i18n('tfr.arrival.DepartGrid.tips.label'),result.message);
												     myMask.hide();
												}
											});
						  }
						});
					}
				}, {
					xtype : 'button',
					text : arrival.i18n('tfr.arrival.ArrivalGrid.grid.certificatebag.button'),//'上交证件包',
					gridContainer : this,
					disabled:!arrival.isPermission('arrival/certificatebagbutton.action'),
					hidden:!arrival.isPermission('arrival/certificatebagbutton.action'),
					handler:function(){
						//拼装条件						
						var rowObjs = me.getSelectionModel().getSelection();
						if(rowObjs.length!=1)
						{
						  Ext.MessageBox.alert(arrival.i18n('tfr.arrival.DepartGrid.tips.label'),arrival.i18n('tfr.arrival.ArrivalGrid.grid.certificatebag.atleastOne.tips'));
						  return false;
						}
						var vehicleNo;
						if(rowObjs.length>0)
						{
							vehicleNo = {'vehicleNo':rowObjs[0].get('vehicleNo')};
						}
						//出发部门或者到达部门为当前部门时，才能进行证件包的上交
						/*if(!(rowObjs[0].get('origOrgCode')==FossUserContext.getCurrentDept().code||rowObjs[0].get('destOrgCode')==FossUserContext.getCurrentDept().code))
						{
						   Ext.MessageBox.alert(arrival.i18n('tfr.arrival.DepartGrid.tips.label'),arrival.i18n('tfr.arrival.ArrivalGrid.grid.certificatebag.notcurrentorg.tips'));
						   return false;
						}*/
						addTab('T_management-certificateBagindex',// 对应打开的目标页面js的onReady里定义的renderTo
								arrival.i18n('tfr.arrival.ArrivalGrid.grid.certificatebag.button'),// 打开的Tab页的标题
								encodeURI(ContextPath.TFR_EXECUTION+ '/management/certificateBagindex.action'+"?vehicleNo="+rowObjs[0].get('vehicleNo')));//"../management/certificateBagindex.action?truckTaskId="+truckTaskId);//
					}
				}, {
					xtype : 'button',
					text : arrival.i18n('tfr.arrival.ArrivalGrid.grid.vehicleforplatform.button'),//'分配月台',
					gridContainer : this,
					hidden:false,//!arrival.isPermission('/tfr-execution-web/arrival/vehicleforplatformbutton.action'),
					handler:function(){
						//取得选中的记录
						var rowObjs = me.getSelectionModel().getSelection();
						if(rowObjs.length!=1)
						{
						  Ext.MessageBox.alert(arrival.i18n('tfr.arrival.DepartGrid.tips.label'),arrival.i18n('tfr.arrival.ArrivalGrid.grid.vehicleforplatform.atleastOne.tips'));
						  return false;
						}
						//出发部门或者到达部门为当前部门时，才能进行证件包的上交
						/*if(rowObjs[0].get('destOrgCode')!=FossUserContext.getCurrentDept().code)
						{
						   Ext.MessageBox.alert(arrival.i18n('tfr.arrival.DepartGrid.tips.label'),arrival.i18n('tfr.arrival.ArrivalGrid.grid.vehicleforplatform.notcurrentorg.tips'));
						   return false;
						}*/
						var assignPlatformWindow = Ext
								.getCmp('T_arrival-index_content')
								.getAssignPlatformWindow();
		                //拼装条件					
						var params = {
							'arrivalVO.queryArriveEntity.id' : rowObjs[0].get('id'),
							'arrivalVO.queryArriveEntity.lineVirtualCode' : rowObjs[0].get('lineVirtualCode'),
							'arrivalVO.queryArriveEntity.vehicleNo' : rowObjs[0].get('vehicleNo'),
							'arrivalVO.queryArriveEntity.truckOrgCode' : rowObjs[0].get('truckOrgCode'),
							'arrivalVO.queryArriveEntity.lineName' : rowObjs[0].get('lineNo'),
							'arrivalVO.queryArriveEntity.destOrgCode' : rowObjs[0].get('destOrgCode'),
							'arrivalVO.queryArriveEntity.planArrivalTime' : rowObjs[0].get('planArrivalTime')==null?null:new Date(rowObjs[0].get('planArrivalTime')),
							'arrivalVO.queryArriveEntity.frequencyNo' : rowObjs[0].get('frequencyNo')
						}
						//给显示车辆明细信息赋值
						assignPlatformWindow.getPlatform().loadRecord(rowObjs[0]);
//                        Ext.getCmp('Foss.arrival.LoadMask.ID').show();
						Ext.Ajax.request({
											url : arrival.realPath('queryVehicleForPlatform.action'),
											params : params,
											success : function(response) {
												var result = Ext.decode(response.responseText);
												var model = Ext.ModelManager.create(
														result.arrivalVO.vehicleInfoDTO,
														'Foss.arrival.platform.VehicleInfoModel');
												assignPlatformWindow.getVehicleInfo().loadRecord(model);
												assignPlatformWindow.getOperateForm().loadRecord(model);
												assignPlatformWindow.getPlatformGrid().store.model = 'Foss.arrival.PlatformModel';
												assignPlatformWindow.getPlatformGrid().store.remoteSort = 'false';
												assignPlatformWindow.getPlatformGrid().store.loadData(result.arrivalVO.platformList);
											},
											exception :function(response)
											{
											    var result = Ext.decode(response.responseText);
												     Ext.MessageBox.alert(arrival.i18n('tfr.arrival.DepartGrid.tips.label'),result.message);
											}
										});
						//给查询条件赋值，默认用车牌号来查询
						assignPlatformWindow.show();
					}
				},{
				xtype : 'button',
				text : '导出',
				name : 'export',
				disabled:!arrival.isPermission('arrival/arriveexportbutton.action'),
				hidden:!arrival.isPermission('arrival/arriveexportbutton.action'),
				handler : function(){
					var records = me.getStore().getRange(0,
							me.getStore().data.items.length);
					if(records.length<1)
					{
					  Ext.MessageBox.alert(arrival.i18n('tfr.arrival.DepartGrid.tips.label'),'没有查询到记录，不能导出');
					  return false;
					}
//					var queryParams = unload.unloadtaskdetailquery.loadTaskForm.getForm().getValues();
					if(!Ext.fly('downloadAttachFileForm')){
						    var frm = document.createElement('form');
						    frm.id = 'downloadAttachFileForm';
						    frm.style.display = 'none';
						    document.body.appendChild(frm);
					}		
					var array = new Array();
					for (var i = 0; i < records.length; i++) {
						array.push(records[i].data.id);
					}
					Ext.Ajax.request({
						url:arrival.realPath('exportUnloadWayBillByTaskNo.action'),
						form: Ext.fly('downloadAttachFileForm'),
						method : 'POST',
						params : {
							'arrivalVO.ids' : array
						},
		    			isUpload: true,
		    			exception : function(response) {
		    				var result = Ext.decode(response.responseText);
		    				top.Ext.MessageBox.alert('提示',result.message);
		    			}
					});
				}
			}, {
				xtype : 'button',
				text : '处理待办',//处理待办
				gridContainer : this,
				disabled:!arrival.isPermission('arrival/handleTodo'),
				hidden:!arrival.isPermission('arrival/handleTodo'),
				handler:function(){
					//拼装条件						
					var rowObjs = me.getSelectionModel().getSelection();
					if(rowObjs.length != 1) {
					  Ext.MessageBox.alert(arrival.i18n('tfr.arrival.DepartGrid.tips.label'),arrival.i18n('tfr.arrival.ArrivalGrid.grid.certificatebag.atleastOne.tips'));
					  return false;
					}
					
					var params = {
						'arrivalVO.operationDTO.id' : rowObjs[0].get('id')
					};
					
					//处理待办
					Ext.Ajax.request({
						url : arrival.realPath('handleTodo.action'),
						timeout: 300000,
						params : params,
						success : function(response) {
							var result = Ext.decode(response.responseText);
							var handOverBillNo = result.arrivalVO.handOverBillNo;
							//Ext.ux.Toast.msg(arrival.i18n('tfr.arrival.DepartGrid.tips.label'), '处理完成!');
							arrival.arrivePagingBar.moveFirst();
							Ext.Msg.confirm(arrival.i18n('tfr.arrival.DepartGrid.tips.label'), '待办处理完毕, 是否跳转到待办事项页面?', function(optional){
								if(optional == 'yes'){
								  addTab('T_waybill-todoActionIndex',// 对应打开的目标页面js的onReady里定义的renderTo
										'待办事项',// 打开的Tab页的标题
										encodeURI(ContextPath.PKP_DELIVER+ '/waybill/todoActionIndex.action?handOverBillNo='+handOverBillNo));
								}
							});
						},
						exception :function(response)
						{
							var result = Ext.decode(response.responseText);
							Ext.MessageBox.alert(arrival.i18n('tfr.arrival.DepartGrid.tips.label'), result.message);
						}
					});
				}
			}];

		me.bbar = Ext.create('Deppon.StandardPaging', {
					store : me.store,
					pageSize : 10,
					maximumSize : 800,
					plugins : Ext.create('Deppon.ux.PageSizePlugin', {
						sizeList : [['10', 10], ['50', 50], ['100', 100], ['800', 800]]
					})
				});
		arrival.arrivePagingBar = me.bbar;
		me.callParent([cfg]);
	}
});
Ext.define('Foss.arrival.QueryDepartureForm', {
			extend : 'Ext.form.Panel',
			layout : 'column',
			frame : true,
			border : false,
//			title : arrival.i18n('tfr.arrival.QueryForm.form.title'),//'查询车辆到达',
			defaults : {
				margin : '5 5 5 5',
				columns : 4
			},
			items : [{
				xtype : 'textfield',
				fieldLabel :arrival.i18n('tfr.arrival.QueryForm.form.billNo.label'),// '交接编号',
				name : 'billNo',
				columnWidth : .25,
				regex:/[A-Za-z0-9]+-?[A-Za-z0-9]+/
				}, {
				xtype : 'commonlineselector',
				fieldLabel : arrival.i18n('tfr.arrival.QueryForm.form.lineNo.label'),//'线路',
				name : 'lineNo',
				columnWidth : .25,
				listeners: {
					'select': function(field, records, eOpts) {
						var record = records[0];
						arrival.queryform.getForm().findField('lineNo').setValue(record.get('lineName'));
					}
				}
			}, {
				xtype : 'commontruckselector',
				fieldLabel : arrival.i18n('tfr.arrival.QueryForm.form.vehicleNo.label'),//'车牌号',
				name : 'vehicleNo',
				displayField : 'name',
				valueField : 'code',
				columnWidth : .25,
				listeners: {
					'select': function(field, records, eOpts) {
						var record = records[0];
						arrival.queryform.getForm().findField('vehicleNo').setValue(record.get('vehicleNo'));
					}
				}
			}, {
				xtype : 'combo',
				fieldLabel : arrival.i18n('tfr.arrival.QueryForm.form.certificaterBagStatus.label'),//'证件包情况',
				value : '0',
				name : 'certificaterBagStatus',
				displayField : 'name',
				valueField : 'code',
				columnWidth : .25,
				queryMode : 'local',
				triggerAction : 'all',
				editable : false,
				dateRange:30,
				store : Ext.create('Foss.arrival.CertificationBagTypeStore')
				},  {
				xtype : 'combo',
				fieldLabel : arrival.i18n('tfr.arrival.QueryForm.form.timeType.label'),//'时间类型',
				value : '0',
				name : 'timeType',
				displayField : 'name',
				valueField : 'code',
				columnWidth : .25,
				queryMode : 'local',
				triggerAction : 'all',
				editable : false,
				store : Ext.create('Foss.arrival.TimeTypeStore')
				}, {
				xtype : 'rangeDateField',
				fieldLabel : arrival.i18n('tfr.arrival.QueryForm.form.createTime.label'),//'起止时间',
				columnWidth : .5,
				disallowBlank:true,
				// 类型为datetimefield_date97需要配置fieldId,可以赋予此属性任何唯一标 //识的String值
//				id : 'Foss_arrival_QueryForm_createTime_Id',
				fieldId:'Foss_arrival_QueryDepartureForm_createTime_Id',
				dateType : 'datetimefield_date97',
				fromName : 'startTime',
				allowBlank:false,
				dateRange:30,
				fromValue : Ext.Date.format(new Date(new Date().getFullYear(),
								new Date().getMonth(), new Date().getDate(),
								'00', '00', '00'), 'Y-m-d H:i:s'),
				toName : 'endTime',
				toValue : Ext.Date.format(new Date(new Date().getFullYear(),
								new Date().getMonth(), new Date().getDate(),
								'23', '59', '59'), 'Y-m-d H:i:s')
			}, {
				xtype : 'dynamicorgcombselector',
				fieldLabel : arrival.i18n('tfr.arrival.QueryForm.form.truckOrgName.label'),//'车辆归属部门',
				name : 'truckOrgCode',
				columnWidth : .25
			},  {
				xtype : 'combo',
				fieldLabel : arrival.i18n('tfr.arrival.QueryForm.form.platformStatus.label'),//'月台分配',
				value : 'ALL',
				name : 'platformStatus',
				displayField : 'valueName',
				valueField : 'valueCode',
				columnWidth : .25,
				queryMode : 'local',
				triggerAction : 'all',
				editable : false,
				store : FossDataDictionary. getDataDictionaryStore('PLATFORM_STATUS_TFR','Foss_login_language_store_Id',{
                                                                                           'valueCode': 'ALL',
                                                                                           'valueName': '全部'
                                                                                    })
				},  {
				xtype : 'combo',
				fieldLabel : arrival.i18n('tfr.arrival.QueryForm.form.businessType.label'),//'车辆业务类型',
				value : 'ALL',
				name : 'businessType',
				displayField : 'name',
				valueField : 'code',
				columnWidth : .25,
				queryMode : 'local',
				triggerAction : 'all',
				editable : false,
				store :Ext.create('Foss.arrival.TruckBusinessStore')
				}, {
				xtype : 'combo',
				fieldLabel :arrival.i18n('tfr.arrival.QueryForm.form.arrivalStatus.label'),// '到达情况',
				value : 'ALL',
				name : 'arrivalStatus',
				displayField : 'name',
				valueField : 'code',
				columnWidth : .25,
				queryMode : 'local',
				triggerAction : 'all',
				editable : false,
				store : Ext.create('Foss.arrival.ArrivalTypeGridStore')
				},  {
				xtype : 'combo',
				fieldLabel : arrival.i18n('tfr.arrival.QueryForm.form.clearingStatus.label'),//'结算情况',
				value : '',
				name : 'clearingStatus',
				displayField : 'name',
				valueField : 'code',
				columnWidth : .25,
				queryMode : 'local',
				triggerAction : 'all',
				editable : false,
				store : Ext.create('Foss.arrival.ClearingTypeStore')
				},  {
				xtype : 'combo',
				fieldLabel :arrival.i18n('tfr.arrival.QueryForm.form.vehicleOwnerType.label'),// '车辆归属类型',
				value : '',
				name : 'vehicleOwnerType',
				displayField : 'name',
				valueField : 'code',
				columnWidth : .25,
				queryMode : 'local',
				triggerAction : 'all',
				editable : false,
				store : Ext.create('Foss.arrival.VehicleOwnerTypeStore')
				}, {
				xtype : 'dynamicorgcombselector',
				fieldLabel : arrival.i18n('tfr.arrival.QueryForm.form.origOrgName.label'),//'出发部门',
				type : 'ORG',
				disabled:true,
//				salesDepartment : 'Y',// 查询营业部 配置此值
//				transferCenter : 'Y',// 查询外场 配置此值
				name : 'origOrgCode',
				columnWidth : .25,
				listeners :{
					render : function(panel,text){
											var cmbOrgCode = this.up('form').getForm().findField('origOrgCode');
						Ext.Ajax.request({
									url : arrival.realPath('querySuperiorOrgByOrgCode.action'),
									success : function(response) {
										var result = Ext
												.decode(response.responseText);
											cmbOrgCode.getStore().load({params:{'commonOrgVo.name' : result.arrivalVO.arrivalEntity.origOrgName}});
							                cmbOrgCode.setValue(result.arrivalVO.arrivalEntity.origOrgCode);
									}
								});
//					var cmbOrgCode = this.up('form').getForm().findField('origOrgCode');
//					cmbOrgCode.getStore().load({params:{'commonOrgVo.name' : FossUserContext.getCurrentDept().name}});
//					cmbOrgCode.setValue(FossUserContext.getCurrentDept().code);
				}
				}
			}, {
				xtype : 'dynamicorgcombselector',
				fieldLabel : arrival.i18n('tfr.arrival.QueryForm.form.destOrgName.label'),//'到达部门',
				type : 'ORG',
//				salesDepartment : 'Y',// 查询营业部 配置此值
//				transferCenter : 'Y',// 查询外场 配置此值
				name : 'destOrgCode',
				columnWidth : .25
			}, {
				border : 1,
				xtype : 'container',
				columnWidth : .25,
				defaultType : 'button',
				layout : 'column',
				items : [{
					text : arrival.i18n('tfr.arrival.QueryForm.form.reset.button'),//'重置',
					columnWidth : .4,
					handler : function() {
						this.up('form').getForm().reset();
						this.up('form').getForm().findField('startTime').setValue(Ext.Date.format(new Date(new Date().getFullYear(),
								new Date().getMonth(), new Date().getDate(),
								'00', '00', '00'), 'Y-m-d H:i:s'));
						this.up('form').getForm().findField('endTime').setValue(Ext.Date.format(new Date(new Date().getFullYear(),
										new Date().getMonth(), new Date().getDate(),
										'23', '59', '59'), 'Y-m-d H:i:s')); 

						var cmbOrgCode = this.up('form').getForm().findField('origOrgCode');
						Ext.Ajax.request({
							url : arrival.realPath('querySuperiorOrgByOrgCode.action'),
							success : function(response) {
								var result = Ext
										.decode(response.responseText);
									cmbOrgCode.getStore().load({params:{'commonOrgVo.name' : result.arrivalVO.arrivalEntity.origOrgName}});
					                cmbOrgCode.setValue(result.arrivalVO.arrivalEntity.origOrgCode);
							}
						});
						//var cmbOrgCode = this.up('form').getForm().findField('origOrgCode');
						//cmbOrgCode.getStore().load({params:{'commonOrgVo.name' : FossUserContext.getCurrentDept().name}});
						//cmbOrgCode.setValue(FossUserContext.getCurrentDept().code);
					}
				}, {
					xtype : 'container',
					columnWidth : .1,
					html : '&nbsp;'
				}, {
					text : arrival.i18n('tfr.arrival.QueryForm.form.query.button'),//'查询',
					columnWidth : .4,
					cls:'yellow_button',
					handler : function() {
						//查找外请车的信息
						if (!arrival.queryform.getForm().isValid())
						return false;
						arrival.departurePagingBar.moveFirst();
					}
				}]
			}],
			constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				me.callParent([cfg]);
			}
		});
		
Ext.define('Foss.arrival.QueryArriveForm', {
			extend : 'Ext.form.Panel',
			layout : 'column',
			frame : true,
			border : false,
//			title : arrival.i18n('tfr.arrival.QueryForm.form.title'),//'查询车辆到达',
			defaults : {
				margin : '5 5 5 5',
				columns : 4
			},
			items : [{
				xtype : 'textfield',
				fieldLabel :arrival.i18n('tfr.arrival.QueryForm.form.billNo.label'),// '交接编号',
				name : 'billNo',
				columnWidth : .25,
				regex:/[A-Za-z0-9]+-?[A-Za-z0-9]+/
				}, {
				xtype : 'commonlineselector',
				fieldLabel : arrival.i18n('tfr.arrival.QueryForm.form.lineNo.label'),//'线路',
				name : 'lineNo',
				columnWidth : .25,
				listeners: {
					'select': function(field, records, eOpts) {
						var record = records[0];
						arrival.arriveform.getForm().findField('lineNo').setValue(record.get('lineName'));
					}
				}
			}, {
				xtype : 'commontruckselector',
				fieldLabel : arrival.i18n('tfr.arrival.QueryForm.form.vehicleNo.label'),//'车牌号',
				name : 'vehicleNo',
				displayField : 'name',
				valueField : 'code',
				columnWidth : .25,
				listeners: {
					'select': function(field, records, eOpts) {
						var record = records[0];
						arrival.arriveform.getForm().findField('vehicleNo').setValue(record.get('vehicleNo'));
					}
				}
			},  {
				xtype : 'combo',
				fieldLabel : arrival.i18n('tfr.arrival.QueryForm.form.certificaterBagStatus.label'),//'证件包情况',
				value : '0',
				name : 'certificaterBagStatus',
				displayField : 'name',
				valueField : 'code',
				columnWidth : .25,
				queryMode : 'local',
				triggerAction : 'all',
				editable : false,
				dateRange:30,
				store : Ext.create('Foss.arrival.CertificationBagTypeStore')
				},  {
				xtype : 'combo',
				fieldLabel : arrival.i18n('tfr.arrival.QueryForm.form.timeType.label'),//'时间类型',
				value : '0',
				name : 'timeType',
				displayField : 'name',
				valueField : 'code',
				columnWidth : .25,
				queryMode : 'local',
				triggerAction : 'all',
				editable : false,
				store : Ext.create('Foss.arrival.TimeTypeStore')
				}, {
				xtype : 'rangeDateField',
				fieldLabel : arrival.i18n('tfr.arrival.QueryForm.form.createTime.label'),//'起止时间',
				columnWidth : .5,
				disallowBlank:true,
				// 类型为datetimefield_date97需要配置fieldId,可以赋予此属性任何唯一标 //识的String值
//				id : 'Foss_arrival_QueryForm_createTime_Id',
				fieldId:'Foss_arrival_QueryArriveForm_createTime_Id',
				dateType : 'datetimefield_date97',
				fromName : 'startTime',
				allowBlank:false,
				dateRange:30,
				fromValue : Ext.Date.format(new Date(new Date().getFullYear(),
								new Date().getMonth(), new Date().getDate(),
								'00', '00', '00'), 'Y-m-d H:i:s'),
				toName : 'endTime',
				toValue : Ext.Date.format(new Date(new Date().getFullYear(),
								new Date().getMonth(), new Date().getDate(),
								'23', '59', '59'), 'Y-m-d H:i:s')
			}, {
				xtype : 'dynamicorgcombselector',
				fieldLabel : arrival.i18n('tfr.arrival.QueryForm.form.truckOrgName.label'),//'车辆归属部门',
				name : 'truckOrgCode',
				columnWidth : .25
			},  {
				xtype : 'combo',
				fieldLabel : arrival.i18n('tfr.arrival.QueryForm.form.platformStatus.label'),//'月台分配',
				value : 'ALL',
				name : 'platformStatus',
				displayField : 'valueName',
				valueField : 'valueCode',
				columnWidth : .25,
				queryMode : 'local',
				triggerAction : 'all',
				editable : false,
				store : FossDataDictionary. getDataDictionaryStore('PLATFORM_STATUS_TFR','Foss_login_language_store_Id',{
                                                                                           'valueCode': 'ALL',
                                                                                           'valueName': '全部'
                                                                                    })
				},  {
				xtype : 'combo',
				fieldLabel : arrival.i18n('tfr.arrival.QueryForm.form.businessType.label'),//'车辆业务类型',
				value : 'ALL',
				name : 'businessType',
				displayField : 'name',
				valueField : 'code',
				columnWidth : .25,
				queryMode : 'local',
				triggerAction : 'all',
				editable : false,
				store :Ext.create('Foss.arrival.TruckBusinessStore')
				}, {
				xtype : 'combo',
				fieldLabel :arrival.i18n('tfr.arrival.QueryForm.form.arrivalStatus.label'),// '到达情况',
				value : 'ALL',
				name : 'arrivalStatus',
				displayField : 'name',
				valueField : 'code',
				columnWidth : .25,
				queryMode : 'local',
				triggerAction : 'all',
				editable : false,
				store : Ext.create('Foss.arrival.ArrivalTypeGridStore')
				},  {
				xtype : 'combo',
				fieldLabel : arrival.i18n('tfr.arrival.QueryForm.form.clearingStatus.label'),//'结算情况',
				value : '',
				name : 'clearingStatus',
				displayField : 'name',
				valueField : 'code',
				columnWidth : .25,
				queryMode : 'local',
				triggerAction : 'all',
				editable : false,
				store : Ext.create('Foss.arrival.ClearingTypeStore')
				},  {
				xtype : 'combo',
				fieldLabel :arrival.i18n('tfr.arrival.QueryForm.form.vehicleOwnerType.label'),// '车辆归属类型',
				value : '',
				name : 'vehicleOwnerType',
				displayField : 'name',
				valueField : 'code',
				columnWidth : .25,
				queryMode : 'local',
				triggerAction : 'all',
				editable : false,
				store : Ext.create('Foss.arrival.VehicleOwnerTypeStore')
				}, {
				xtype : 'dynamicorgcombselector',
				fieldLabel : arrival.i18n('tfr.arrival.QueryForm.form.origOrgName.label'),//'出发部门',
				type : 'ORG',
//				salesDepartment : 'Y',// 查询营业部 配置此值
//				transferCenter : 'Y',// 查询外场 配置此值
				name : 'origOrgCode',
				columnWidth : .25
			}, {
				xtype : 'dynamicorgcombselector',
				fieldLabel : arrival.i18n('tfr.arrival.QueryForm.form.destOrgName.label'),//'到达部门',
				type : 'ORG',
				disabled:true,
//				salesDepartment : 'Y',// 查询营业部 配置此值
//				transferCenter : 'Y',// 查询外场 配置此值
				name : 'destOrgCode',
				columnWidth : .25,
				listeners :{
					render : function(panel,text){
						var cmbOrgCode = this.up('form').getForm().findField('destOrgCode');
						Ext.Ajax.request({
							url : arrival.realPath('querySuperiorOrgByOrgCode.action'),
							success : function(response) {
								var result = Ext
										.decode(response.responseText);
									cmbOrgCode.getStore().load({params:{'commonOrgVo.name' : result.arrivalVO.arrivalEntity.origOrgName}});
					                cmbOrgCode.setValue(result.arrivalVO.arrivalEntity.origOrgCode);
							}
						});
				}}
			}, {
				border : 1,
				xtype : 'container',
				columnWidth : .25,
				defaultType : 'button',
				layout : 'column',
				items : [{
					text : arrival.i18n('tfr.arrival.QueryForm.form.reset.button'),//'重置',
					columnWidth : .4,
					handler : function() {
						this.up('form').getForm().reset();
						this.up('form').getForm().findField('startTime').setValue(Ext.Date.format(new Date(new Date().getFullYear(),
								new Date().getMonth(), new Date().getDate(),
								'00', '00', '00'), 'Y-m-d H:i:s'));
						this.up('form').getForm().findField('endTime').setValue(Ext.Date.format(new Date(new Date().getFullYear(),
										new Date().getMonth(), new Date().getDate(),
										'23', '59', '59'), 'Y-m-d H:i:s')); 
						var cmbOrgCode = this.up('form').getForm().findField('destOrgCode');
						Ext.Ajax.request({
							url : arrival.realPath('querySuperiorOrgByOrgCode.action'),
							success : function(response) {
								var result = Ext
										.decode(response.responseText);
									cmbOrgCode.getStore().load({params:{'commonOrgVo.name' : result.arrivalVO.arrivalEntity.origOrgName}});
					                cmbOrgCode.setValue(result.arrivalVO.arrivalEntity.origOrgCode);
							}
						});
					}
				}, {
					xtype : 'container',
					columnWidth : .1,
					html : '&nbsp;'
				}, {
					text : arrival.i18n('tfr.arrival.QueryForm.form.query.button'),//'查询',
					columnWidth : .4,
					cls:'yellow_button',
					handler : function() {
						//查找外请车的信息
						if (!arrival.arriveform.getForm().isValid())
						return false;
						arrival.arrivePagingBar.moveFirst();
					}
				}]
			}],
			constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				me.callParent([cfg]);
			}
		});
Ext.define('Foss.arrival.ResultDepartureForm', {
			extend : 'Ext.form.Panel',
			layout : 'column',
			height:25,
			frame : false,
			border : false,
			defaults : {
				margin : '0 5 5 5',
				columns : 4,
				xtype : 'displayfield',
				allowBlank : true
			},
			items : [{
				fieldLabel : arrival.i18n('tfr.arrival.ResultForm.form.startTime.label'),//'起始时间',
				name : 'startTime',
				columnWidth : .25,
			    labelWidth: 80
				}, {
				fieldLabel : arrival.i18n('tfr.arrival.ResultForm.form.endTime.label'),//'至',
				name : 'endTime',
				columnWidth : .25,
			    labelWidth: 80
			}, {
				fieldLabel : arrival.i18n('tfr.arrival.ResultForm.form.totalTrucks.label'),//'外请车预计到达个数',
				name : 'totalTrucks',
				columnWidth : .25,
			    labelWidth: 150
			}, {
				fieldLabel : arrival.i18n('tfr.arrival.ResultForm.form.totalFee.label'),//'未结清金额合计（元）',
				name : 'totalFee',
				columnWidth : .25,
			    labelWidth: 150
			}],
			constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				me.callParent([cfg]);
			}
		});
Ext.define('Foss.arrival.ResultArriveForm', {
			extend : 'Ext.form.Panel',
			layout : 'column',
			height:25,
			frame : false,
			border : false,
			defaults : {
				margin : '0 5 5 5',
				columns : 4,
				xtype : 'displayfield',
				allowBlank : true
			},
			items : [{
				fieldLabel : arrival.i18n('tfr.arrival.ResultForm.form.startTime.label'),//'起始时间',
				name : 'startTime',
				columnWidth : .25,
			    labelWidth: 80
				}, {
				fieldLabel : arrival.i18n('tfr.arrival.ResultForm.form.endTime.label'),//'至',
				name : 'endTime',
				columnWidth : .25,
			    labelWidth: 80
			}, {
				fieldLabel : arrival.i18n('tfr.arrival.ResultForm.form.totalTrucks.label'),//'外请车预计到达个数',
				name : 'totalTrucks',
				columnWidth : .25,
			    labelWidth: 150
			}, {
				fieldLabel : arrival.i18n('tfr.arrival.ResultForm.form.totalFee.label'),//'未结清金额合计（元）',
				name : 'totalFee',
				columnWidth : .25,
			    labelWidth: 150
			}],
			constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				me.callParent([cfg]);
			}
		});
		
Ext.onReady(function() {
			Ext.QuickTips.init();
			var queryform = Ext.create('Foss.arrival.QueryDepartureForm');
			var resultForm = Ext.create('Foss.arrival.ResultDepartureForm');
			var querygrid = Ext.create('Foss.arrival.DepartureGrid');
			var arriveform = Ext.create('Foss.arrival.QueryArriveForm');
			var arrivegrid = Ext.create('Foss.arrival.ArrivalGrid');
			var resultarriveForm = Ext.create('Foss.arrival.ResultArriveForm');
			var cancelDepartGrid = Ext.create('Foss.arrival.TruckActionDetailGrid', {title : '取消出发记录'});
			arrival.cancelDepartGrid = cancelDepartGrid;
			var cancelArrivalGrid = Ext.create('Foss.arrival.TruckActionDetailGrid', {title : '取消到达记录'});
			arrival.cancelArrivalGrid = cancelArrivalGrid;
			arrival.queryform = queryform;
			arrival.resultForm = resultForm;
			arrival.arriveform = arriveform;
			arrival.resultarriveForm = resultarriveForm;
			Ext.create('Ext.panel.Panel', {
						id : 'T_arrival-index_content',
						cls : "panelContentNToolbar",
						bodyCls : 'panelContentNToolbar-body',
						layout : 'auto',
						assignPlatformWindow : null,
						getAssignPlatformWindow : function() {
							if (this.assignPlatformWindow == null) {
								this.assignPlatformWindow = Ext
										.create('Foss.arrival.AssignPlatformWindow');
							}
							return this.assignPlatformWindow;
						},
						items : [{
							xtype : 'tabpanel',
							frame : false,
							bodyCls : 'autoHeight',
							cls : 'innerTabPanel',
							activeTab : 0,
						    items :[{
										title : "本部门出发",//'临时任务',
										tabConfig : {
											width : 120
										},
										itemId : 'departureTab',
										items : [queryform,resultForm, querygrid, cancelDepartGrid]
									}, {
										title :"到达本部门",// '任务车辆',
										tabConfig : {
											width : 120
										},
										itemId : 'arriveTab',
										items : [arriveform,resultarriveForm, arrivegrid, cancelArrivalGrid]
									}]
							}],
						renderTo : 'T_arrival-index-body'
					});
		});