Ext.define('Foss.ontheway.VehicleStatisticsModel',{
	extend: 'Ext.data.Model',
	fields: [
	     {name: 'longVehicleQuantity', type: 'string'},
	     {name: 'pkpVehicleQuantity', type: 'string'},
	     {name: 'shortVehicleQuantity', type: 'string'}
	]
});
Ext.define('Foss.ontheway.TrackingTypeStore', {
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
							code : 'HANDLE',
							name : '手工跟踪'
						},{
							code : 'GPS',
							name : 'GPS跟踪'
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

Ext.define('Foss.ontheway.PlanArrivalStatusStore', {
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
		
Ext.define('Foss.ontheway.CurrentStatusStore', {
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
							code : 'RUN',
							name : '运行'
						},{
							code : 'STOP',
							name : '停止'
						},{
							code : 'ACCIDENTS',
							name : '事故车辆'
						},{
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
						},{
							code : 'OTHERS',
							name : '其他'
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
Ext.define('Foss.ontheway.VehicleTypeStore', {//车辆归属类型
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
							code : 'Leased',
							name : '外请车'
						},{
							code : 'Company',
							name : '公司车'
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
							code : 'ALL',
							name : '全部'
						},{
							code : 'ONTHEWAY',
							name : '在途'
						},{
							code : 'ARRIVED',
							name : '到达'
						},{
							code : 'UNLOADED',
							name : '卸车'
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
//车辆申请记录的模版
Ext.define('Foss.ontheway.OnthewayModel', {
			extend : 'Ext.data.Model',
			fields : [ {
						name : 'vehicleNo',
						type : 'string'
					}, {
						name : 'currentStatusName',
						type : 'string'
					}, {
						name : 'currentStatus',
						type : 'string'
					}, {
						name : 'planArrivalStatus',
						type : 'string'
					}, {
						name : 'lineName',
						type : 'string'
					}, {
						name : 'origOrgName',
						type : 'string'
					}, {
						name : 'destOrgName',
						type : 'string'
					}, {
						name : 'planArriveTime',
						type : 'date',
						convert : dateConvert
					}, {
						name : 'actualDepartTime',
						hiddenField : true,
						type : 'date',
						convert : dateConvert
					}, {
						name : 'currentPlace',
						type : 'string'
					}, {
						name : 'trackingTime',
						type : 'date',
						convert : dateConvert
					}, {
						name : 'vehicleOrgName',
						type : 'string'
					}, {
						name : 'trackingType',
						type : 'string'
					}, {
						name : 'trackingUserName',
						type : 'string'
					}, {
						name : 'notes',
						type : 'string'
					}, {
						name : 'truckTaskDetailId',
						type : 'string'
					}, {
						name : 'vehicleStatus',
						type : 'string'
					}
			]
		});
Ext.define('Foss.ontheway.OnthewayStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.ontheway.OnthewayModel',
	pageSize : 10,
	autoLoad : true,
	proxy : {
		// 以JSON的方式加载数据
		type : 'ajax',
		actionMethods : 'POST',
		url : ontheway.realPath('queryOnthewayGrid.action'),
		reader : {
			type : 'json',
			root : 'onthewayVO.onthewayList',
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
			var queryParams = ontheway.queryform.getValues();
			Ext.apply(operation, {
				// jsonData: {'departureVO':{'queryEntity':queryParams}}
				params : {
					'onthewayVO.queryEntity.vehicleNo' : queryParams.vehicleNo,
					'onthewayVO.queryEntity.vehicleStatus' : queryParams.vehicleStatus,
					'onthewayVO.queryEntity.origOrgCode' : queryParams.origOrgCode,
					'onthewayVO.queryEntity.destOrgCode' : queryParams.destOrgCode,
					'onthewayVO.queryEntity.businessType' : queryParams.businessType,
					'onthewayVO.queryEntity.startTime' : queryParams.startTime,
					'onthewayVO.queryEntity.endTime' : queryParams.endTime,
					'onthewayVO.queryEntity.departStartTime' : queryParams.departstartTime,
					'onthewayVO.queryEntity.departEndTime' : queryParams.departendTime,
					'onthewayVO.queryEntity.accidents' : queryParams.accidents,
					'onthewayVO.queryEntity.truckType' : queryParams.truckType,
					'onthewayVO.queryEntity.truckOrgCode' : queryParams.truckOrgCode,
					'onthewayVO.queryEntity.trackingType' : queryParams.trackingType,
					'onthewayVO.queryEntity.runStatus' : queryParams.runStatus,
					'onthewayVO.queryEntity.filterCondition' :queryParams.filterCondition,
					'onthewayVO.queryEntity.planArrivalStatus' : queryParams.planArrivalStatus
				}
			});
		}
	}
});

Ext.define('Foss.ontheway.OnthewayGrid', {
	extend : 'Ext.grid.Panel',
	// 指定grid对象在DOM树中的唯一值
	height : 390,
	// 指定表格的宽度
	// 自动增加滚动条
	autoScroll : true,
	// 增加表格列的分割线
	columnLines : true,
	// 表格对象增加一个边框
	frame : true,
	emptyText:ontheway.i18n('tfr.ontheway.OnthewayGrid.grid.empty.title'),//"查询结果为空",
	// 定义表格列信息
	columns : [{
		// 字段标题
		xtype:'actioncolumn',
		header : ontheway.i18n('tfr.ontheway.OnthewayGrid.grid.tips'),//'查看',
		// 关联model中的字段名
		width : 50,
		align:'center',
		dataIndex : 'departType',
		  items: [{
                iconCls: 'deppon_icons_showdetail',
                tooltip: ontheway.i18n('tfr.ontheway.OnthewayGrid.grid.detail.label'),//'查看跟踪明细',
                handler: function(grid, rowIndex, colIndex) {
                  var showDetailWindow = Ext
								.getCmp('T_ontheway-index_content')
								.getShowDetailWindow();
				  var params = {'onthewayVO.truckTaskDetailId' : grid.getStore().getAt(rowIndex).get('truckTaskDetailId')}
				  Ext.Ajax.request({
					url : ContextPath.TFR_EXECUTION+ '/ontheway/queryOnthewayGridById.action',
					params : params,
					success : function(response) {
						var result = Ext.decode(response.responseText);
						showDetailWindow.getDetailGrid().store.model = 'Foss.ontheway.DetailGridModel';
						showDetailWindow.getDetailGrid().store.remoteSort = 'false';
						showDetailWindow.getDetailGrid().store
								.loadData(result.onthewayVO.detailList);
								showDetailWindow.show();
				}
		});
                }

		  }]
		}, {
		// 字段标题
		header :ontheway.i18n('tfr.ontheway.OnthewayGrid.grid.vehicleNo.label'),// '车牌号',
		width : 70,
		// 关联model中的字段名
		dataIndex : 'vehicleNo',
//		windowClassName : 'Foss.ontheway.VehicleInfoWindow',
		// 定义列类型为扩展的openwindowcolumn类型
//		xtype : 'opentipwindowcolumn',
		renderer : function(value, cellmeta, record, rowIndex, columnIndex, store){
			var id = record.get('truckTaskDetailId');
			var vehicleNo = record.get('vehicleNo');
			var driverCode = record.get('driverCode');
			return '<a href="javascript:ontheway.vehicleInfoWindow('+"'" + id + "','" + vehicleNo + "','" + driverCode +"'" +')">' + value + '</a>';
		}
//		tipConfig: {
//		        //如果要设置宽度，一定要修改maxWidth值，因为tip的maxWidth最大只有300
//				width: 150,
//				height: 30,
//		        html : ontheway.i18n('tfr.ontheway.OnthewayGrid.grid.vehicleNo.detail.label'),//'请查看车辆明细信息',
//				hideDelay: 2000
//			}
	}, {
		// 字段标题
		header : ontheway.i18n('tfr.ontheway.OnthewayGrid.grid.lineNo.label'),//'当前状态',
		width : 100,
		// 关联model中的字段名
		dataIndex : 'currentStatus',
		renderer : function(value) {
			var vstore = Ext.create('Foss.ontheway.CurrentStatusStore'), 
			srecord = vstore.findRecord('code', value);
			if (srecord != null) {
				return srecord.get('name');
			} else {
				return '';
			}
		}
	}, {
		// 字段标题
		header : '预计到达状态', //预计到达状态
		width : 100,
		// 关联model中的字段名
		dataIndex : 'planArrivalStatus',
		renderer : function(value) {
			var vstore = Ext.create('Foss.ontheway.PlanArrivalStatusStore'),
				srecord = vstore.findRecord('code', value);
			if (srecord != null) {
				return srecord.get('name');
			} else {
				return '';
			}
		}
	}, {
		// 字段标题
		header : ontheway.i18n('tfr.ontheway.OnthewayGrid.grid.origOrgName.label'),//'线路',
		width : 80,
		// 关联model中的字段名
		dataIndex : 'lineName'
	}, {
		// 字段标题
		header : ontheway.i18n('tfr.ontheway.OnthewayGrid.grid.destOrgName.label'),//'出发部门',
		width : 140,
		// 关联model中的字段名
		dataIndex : 'origOrgName'
	}, {
		// 字段标题
		header : ontheway.i18n('tfr.ontheway.OnthewayGrid.grid.departTime.label'),//'到达部门',
		width : 140,
		// 关联model中的字段名
		dataIndex : 'destOrgName'
	},  {
		// 字段标题
		header :ontheway.i18n('tfr.ontheway.OnthewayGrid.grid.onthewayTime.label'),// '出发时间',
		width : 140,
		// 关联model中的字段名
		dataIndex : 'actualDepartTime',
		renderer : function(value) {
			if (value != null && value != '') {
				var date = new Date(value);
				return Ext.Date.format(date, 'Y-m-d H:i:s');
			} else {
				return null;
			}
		}
	},  {
		// 字段标题
		header :ontheway.i18n('tfr.ontheway.OnthewayGrid.grid.planArrivalTime.label'),// '预计到达时间',
//		xtype : 'string',
//		format : 'Y-m-d H:i:s',
		width : 140,
		// 关联model中的字段名
		dataIndex : 'planArriveTime',
		renderer : function(value) {
			if (value != null && value != '') {
				var date = new Date(value);
				return Ext.Date.format(date, 'Y-m-d H:i:s');
			} else {
				return null;
			}
		}
	}, {
		// 字段标题
		header : ontheway.i18n('tfr.ontheway.OnthewayGrid.grid.onthewayStatus.label'),//'当前车辆所处路段',
		width : 120,
		// 关联model中的字段名
		dataIndex : 'currentPlace'
	}, {
		// 字段标题
		header :ontheway.i18n('tfr.ontheway.OnthewayGrid.grid.notArrive.label'),// '最后一次跟踪时间',
//		xtype : 'datecolumn',
		width : 120,
		// 关联model中的字段名
//		format : 'Y-m-d H:i:s',
		dataIndex : 'trackingTime',
		renderer : function(value) {
							if (value != null && value != '') {
								var date = new Date(value);
								return Ext.Date.format(date, 'Y-m-d H:i:s');
							} else {
								return null;
							}
						}
	}, {
		// 字段标题
		header :ontheway.i18n('tfr.ontheway.OnthewayGrid.grid.certificteBagStatus.label'),// '车辆归属部门',
		width : 140,
		// 关联model中的字段名
		dataIndex : 'vehicleOrgName'
	}, {
		// 字段标题
		header : ontheway.i18n('tfr.ontheway.OnthewayGrid.grid.platformStatus.label'),//'跟踪方式',
		width : 80,
		// 关联model中的字段名
		dataIndex : 'trackingType',
		renderer : function(value) {
			var vstore = Ext.create('Foss.ontheway.TrackingTypeStore'), srecord = vstore
					.findRecord('code', value);
			if (srecord != null) {
				return srecord.get('name');
			} else {
				return '';
			}
		}
	}, {
		// 字段标题
		header : ontheway.i18n('tfr.ontheway.OnthewayGrid.grid.fee.label'),//'车辆状态',
		width : 80,
		// 关联model中的字段名
		dataIndex : 'vehicleStatus',
		renderer : function(value) {
			var vstore = Ext.create('Foss.ontheway.VehicleStatusStore'), srecord = vstore
					.findRecord('code', value);
			if (srecord != null) {
				return srecord.get('name');
			} else {
				return '';
			}
		}
	}, {
		// 字段标题
		header : ontheway.i18n('tfr.ontheway.OnthewayGrid.grid.loader.label'),//'跟踪人',
		width : 80,
		// 关联model中的字段名
		dataIndex : 'trackingUserName'
	}, {
		width : 0,
		// 关联model中的字段名
		dataIndex : 'truckTaskDetailId'
	}, {
		// 字段标题
		header : ontheway.i18n('tfr.ontheway.OnthewayGrid.grid.note.label'),//'备注',
		width : 80,
		// 关联model中的字段名
		dataIndex : 'notes'
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.selModel = Ext.create('Ext.selection.RadioModel');
		me.store = Ext.create('Foss.ontheway.OnthewayStore');

		me.aaa = function(flag){
			 Ext.MessageBox.alert(ontheway.i18n('tfr.ontheway.OnthewayGrid.tips.label'),flag);
		};
		me.tbar = [{
			xtype : 'button',
			layout : 'column',
			text : ontheway.i18n('tfr.ontheway.OnthewayGrid.grid.gpsurlbutton.button'),//'回放轨迹',
			disabled:!ontheway.isPermission('ontheway/querygpsurlbutton.action'),
			hidden:!ontheway.isPermission('ontheway/querygpsurlbutton.action'),
			gridContainer : this,
			handler:function(){
				var manualWindow = Ext
						.getCmp('T_ontheway-index_content')
						.getManualWindow();
				var rowObjs = me.getSelectionModel().getSelection();
				if (rowObjs.length != 1) {
					Ext.MessageBox.alert(ontheway.i18n('tfr.ontheway.OnthewayGrid.tips.label'),ontheway.i18n('tfr.ontheway.OnthewayGrid.grid.atleastOne.tips'));
					return false;
				} else if (rowObjs.length == 1) {// 已选中一条进行编辑
					// 校验状态
				  manualWindow.getManualForm().loadRecord(rowObjs[0]);
				  var params = {
						'onthewayVO' : {
								'vehicleNo' : rowObjs[0].get('vehicleNo'),
								'actualDepartTime' : rowObjs[0].get('actualDepartTime'),
								'planArriveTime' : rowObjs[0].get('planArriveTime')
						}
				};
			  ontheway.queryGpsUrl(params);
				
				}
			}
		},{
				allowBlank : true,
			    xtype : 'displayfield'
		},{
			xtype : 'button',
			text :ontheway.i18n('tfr.ontheway.OnthewayGrid.grid.longCount.button'),// '待跟踪长途车辆',
			gridContainer : this,
			handler:function(){
				var queryParams = ontheway.queryform.getValues();
				var	params = {
					'onthewayVO.queryEntity.vehicleNo' : queryParams.vehicleNo,
					'onthewayVO.queryEntity.vehicleStatus' : queryParams.vehicleStatus,
					'onthewayVO.queryEntity.origOrgCode' : queryParams.origOrgCode,
					'onthewayVO.queryEntity.destOrgCode' : queryParams.destOrgCode,
					'onthewayVO.queryEntity.businessType' : queryParams.businessType,
					'onthewayVO.queryEntity.startTime' : queryParams.startTime,
					'onthewayVO.queryEntity.endTime' : queryParams.endTime,
					'onthewayVO.queryEntity.departStartTime' : queryParams.departstartTime,
					'onthewayVO.queryEntity.departEndTime' : queryParams.departendTime,
					'onthewayVO.queryEntity.accidents' : queryParams.accidents,
					'onthewayVO.queryEntity.truckType' : queryParams.truckType,
					'onthewayVO.queryEntity.truckOrgCode' : queryParams.truckOrgCode,
					'onthewayVO.queryEntity.trackingType' : queryParams.trackingType
				}
				Ext.Ajax.request({
									url : ontheway.realPath('queryLongCount.action'),
									params : params,
									success : function(response) {
										var result = Ext.decode(response.responseText);
										var model = Ext.ModelManager.create(
												result.onthewayVO.vehicleStatistics,
												'Foss.ontheway.VehicleStatisticsModel');
										Ext.getCmp('Foss.ontheway.OnthewayGrid.LONG_VEHICLE_QUANTITY_ID').setValue(result.onthewayVO.vehicleStatistics.longVehicleQuantity);
										Ext.getCmp('Foss_ontheway_QueryForm_filterCondition_Id').setValue("LONG_COUNT");
										ontheway.pagingBar.moveFirst();
									}
								});
			}
		}, {
			style: {
				width:'30px',
				top: '7px'
	        },
			text:ontheway.i18n('tfr.ontheway.OnthewayGrid.grid.longVehicleQuantity.tips'),//'车辆数量',
			name : 'longVehicleQuantity',
			readOnly: true,
			id:'Foss.ontheway.OnthewayGrid.LONG_VEHICLE_QUANTITY_ID',
		    xtype : 'textfield'
		},{
			xtype : 'button',
			text :ontheway.i18n('tfr.ontheway.OnthewayGrid.grid.ShortGPSCount.button'),// '短途GPS离线车辆',
			gridContainer : this,
			handler:function(){
				var queryParams = ontheway.queryform.getValues();
			var	params = {
			'onthewayVO.queryEntity.vehicleNo' : queryParams.vehicleNo,
			'onthewayVO.queryEntity.vehicleStatus' : queryParams.vehicleStatus,
			'onthewayVO.queryEntity.origOrgCode' : queryParams.origOrgCode,
			'onthewayVO.queryEntity.destOrgCode' : queryParams.destOrgCode,
			'onthewayVO.queryEntity.businessType' : queryParams.businessType,
			'onthewayVO.queryEntity.startTime' : queryParams.startTime,
			'onthewayVO.queryEntity.endTime' : queryParams.endTime,
			'onthewayVO.queryEntity.departStartTime' : queryParams.departstartTime,
			'onthewayVO.queryEntity.departEndTime' : queryParams.departendTime,
			'onthewayVO.queryEntity.accidents' : queryParams.accidents,
			'onthewayVO.queryEntity.truckType' : queryParams.truckType,
			'onthewayVO.queryEntity.truckOrgCode' : queryParams.truckOrgCode,
			'onthewayVO.queryEntity.trackingType' : queryParams.trackingType
		}
				Ext.Ajax.request({
									url : ontheway.realPath('queryShortGPSCount.action'),
									params : params,
									success : function(response) {
										var result = Ext.decode(response.responseText);
										var model = Ext.ModelManager.create(
												result.onthewayVO.vehicleStatistics,
												'Foss.ontheway.VehicleStatisticsModel');
										Ext.getCmp('Foss.ontheway.OnthewayGrid.SHORT_VEHICLE_QUANTITY_ID').setValue(result.onthewayVO.vehicleStatistics.shortVehicleQuantity);
									    Ext.getCmp('Foss_ontheway_QueryForm_filterCondition_Id').setValue("SHORT_GPS");
										ontheway.pagingBar.moveFirst();
									}
								});
			}
		},{
				name : 'shortVehicleQuantity',
				columnWidth : .25,
				width:30,
				id:'Foss.ontheway.OnthewayGrid.SHORT_VEHICLE_QUANTITY_ID',
				style: {
					width:'30px',
					top: '7px'
		        },
		        readOnly:true,
			    xtype : 'textfield'
			},{
			xtype : 'button',
			text : ontheway.i18n('tfr.ontheway.OnthewayGrid.grid.PkpGPSCount.button'),//'接送货GPS离线车辆',
			gridContainer : this,
			handler:function(){
				var queryParams = ontheway.queryform.getValues();
			var	params = {
			'onthewayVO.queryEntity.vehicleNo' : queryParams.vehicleNo,
			'onthewayVO.queryEntity.vehicleStatus' : queryParams.vehicleStatus,
			'onthewayVO.queryEntity.origOrgCode' : queryParams.origOrgCode,
			'onthewayVO.queryEntity.destOrgCode' : queryParams.destOrgCode,
			'onthewayVO.queryEntity.businessType' : queryParams.businessType,
			'onthewayVO.queryEntity.startTime' : queryParams.startTime,
			'onthewayVO.queryEntity.endTime' : queryParams.endTime,
			'onthewayVO.queryEntity.departStartTime' : queryParams.departstartTime,
			'onthewayVO.queryEntity.departEndTime' : queryParams.departendTime,
			'onthewayVO.queryEntity.accidents' : queryParams.accidents,
			'onthewayVO.queryEntity.truckType' : queryParams.truckType,
			'onthewayVO.queryEntity.truckOrgCode' : queryParams.truckOrgCode,
			'onthewayVO.queryEntity.trackingType' : queryParams.trackingType,
		}
				Ext.Ajax.request({
									url : ontheway.realPath('queryPkpGPSCount.action'),
									params : params,
									success : function(response) {
										var result = Ext.decode(response.responseText);
										var model = Ext.ModelManager.create(
												result.onthewayVO.vehicleStatistics,
												'Foss.ontheway.VehicleStatisticsModel');
										Ext.getCmp('Foss.ontheway.OnthewayGrid.PKP_VEHICLE_QUANTITY_ID').setValue(result.onthewayVO.vehicleStatistics.pkpVehicleQuantity);
									    Ext.getCmp('Foss_ontheway_QueryForm_filterCondition_Id').setValue("PKP_GPS");
										ontheway.pagingBar.moveFirst();
									}
								});
			}
		},{
				name : 'pkpVehicleQuantity',
				width:30,
				id:'Foss.ontheway.OnthewayGrid.PKP_VEHICLE_QUANTITY_ID',
				style: {
					width:'30px',
					top: '7px'
		        },
		        readOnly:true,
			    xtype : 'textfield'
			}, {
				xtype : 'container',
				columnWidth : .1,
				html : '&nbsp;'
			},{
			xtype : 'button',
			text : ontheway.i18n('tfr.ontheway.OnthewayGrid.grid.manualinputbutton.button'),//'手动跟踪录入',
			hidden:!ontheway.isPermission('ontheway/manualinputbutton.action'),
			gridContainer : this,
			handler:function(){
			  var manualWindow = Ext
						.getCmp('T_ontheway-index_content')
						.getManualWindow();
				var rowObjs = me.getSelectionModel().getSelection();
				if (rowObjs.length != 1) {
					Ext.MessageBox.alert(ontheway.i18n('tfr.ontheway.OnthewayGrid.tips.label'),ontheway.i18n('tfr.ontheway.OnthewayGrid.grid.atleastOne.tips'));
					return false;
				} else if (rowObjs.length == 1) {// 已选中一条进行编辑
					// 校验状态
				  manualWindow.getManualForm().loadRecord(rowObjs[0]);
				  manualWindow.getManualForm().getForm().findField('planArriveTime').setValue(Ext.Date.format(rowObjs[0].get('planArriveTime'), 'Y-m-d H:i:s'));
				  manualWindow.getManualForm().getForm().findField('trackingTime').setValue(Ext.Date.format(new Date(), 'Y-m-d H:i:s'));
				  manualWindow.getManualForm().getForm().findField('notes').setValue('');
				  manualWindow.show();
				}
				
				}
		},{
				xtype : 'button',
				text : '导出',
				name : 'export',
				handler : function(){
					var records = me.getStore().getRange(0,
							me.getStore().data.items.length);
					if(records.length<1)
					{
					  Ext.MessageBox.alert(ontheway.i18n('tfr.arrival.DepartGrid.tips.label'),'没有查询到记录，不能导出');
					  return false;
					}
					var queryParams = ontheway.queryform.getValues();
					if(!Ext.fly('downloadAttachFileForm')){
						    var frm = document.createElement('form');
						    frm.id = 'downloadAttachFileForm';
						    frm.style.display = 'none';
						    document.body.appendChild(frm);
					}		
					var array = new Array();
					for (var i = 0; i < records.length; i++) {
						array.push(records[i].data.truckTaskDetailId);
					}
					Ext.Ajax.request({
						url:ontheway.realPath('exportUnloadWayBillByTaskNo.action'),
						form: Ext.fly('downloadAttachFileForm'),
						method : 'POST',
						params : {
							'onthewayVO.ids' : array
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
					plugins: 'pagesizeplugin'
				});
		ontheway.pagingBar = me.bbar;
		me.callParent([cfg]);
	}
});

//zyx
/**
 * id 车辆任务明细ID
 * vehicle 车牌号
 * driverCode 司机编号(不要问我这个司机编号干嘛用的, 以前的参数中有)
 */
javascript:ontheway.vehicleInfoWindow  = function(id, vehicleNo, driverCode) {
	var params = {
			'vehicleInfoVO.queryEntity.id' : id,
			'vehicleInfoVO.queryEntity.vehicleNo' : vehicleNo,
			'vehicleInfoVO.queryEntity.isDepart' : 'no',
			'vehicleInfoVO.queryEntity.driverCode' : driverCode
		}
	
	var me = Ext.create('Foss.ontheway.VehicleInfoWindow');
	
	Ext.Ajax.request({
		url : ContextPath.TFR_EXECUTION+ '/departure/queryVehicleInfoInfo.action',
		params : params,
		success : function(response) {
			var result = Ext.decode(response.responseText);
			var model = Ext.ModelManager.create(
					result.vehicleInfoVO.vehicleInfoEntity,
					'Foss.ontheway.VehicleInfoModel');
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
						'Foss.ontheway.RelationInfoModel');
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
						'Foss.ontheway.BusinessInfoModel');
				me.getBusinessInfo().loadRecord(model);
			}
		});
	Ext.Ajax.request({
	url : ContextPath.TFR_EXECUTION+ '/departure/queryTaskBill.action',
	params : params,
	success : function(response) {
		var result = Ext.decode(response.responseText);
		me.getTaskBillGrid().store.model = 'Foss.ontheway.TaskBillModel';
		me.getTaskBillGrid().store.remoteSort = 'false';
		me.getTaskBillGrid().store.loadData(result.vehicleInfoVO.taskBillList);
		
		//车辆任务下运单统计信息zyx
		//(只查询运输性质为“精准卡航”、“精准城运”货物<业务部门要求>)
		me.getTaskBillSummaryGrid().store.loadData(result.vehicleInfoVO.taskBillSummaryList);
	}
	});
	Ext.Ajax.request({
			url : ContextPath.TFR_EXECUTION+ '/departure/queryDepartInfo.action',
			params : params,
			success : function(response) {
				var result = Ext.decode(response.responseText);
				var model = Ext.ModelManager.create(
						result.vehicleInfoVO.departInfoEntity,
						'Foss.ontheway.DepartInfoModel');
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
						'Foss.ontheway.ArrivalInfoModel');
				me.getArrivalInfo().loadRecord(model);
			}
		});
	Ext.Ajax.request({
			url : ContextPath.TFR_EXECUTION+ '/departure/queryOnTheWayInfo.action',
			params : params,
			success : function(response) {
				var result = Ext.decode(response.responseText);
				var model = Ext.ModelManager.create(
						result.vehicleInfoVO.onTheWayInfoEntity,
						'Foss.ontheway.OnTheWayInfoModel');
				me.getOnTheWayInfo().loadRecord(model);
			}
		});
	me.show();
}
//zyx end

// GPS电子地图
Ext.define('Foss.ontheway.GPSTruckMap',{
	extend : 'Ext.window.Window',
	title : ontheway.i18n('tfr.ontheway.GPSTruckMap.form.title'),//'电子地图',
	modal : true,
	closeAction : 'hide',
	height : 600,
	width : 1000,
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
Ext.apply(ontheway,{
	queryGpsUrl : function(params){
		Ext.Ajax.request({
			url : ontheway.realPath('queryGpsUrl.action'),
			async : false,
			jsonData : params,
			success : function(response) {
				var json = Ext.decode(response.responseText);
				Ext.create('Foss.ontheway.GPSTruckMap',{
					gpsUrl : json.onthewayVO.gpsUrl
				}).show();
			},
			failure : function(response) {
				var result = Ext.decode(response.responseText);
				Ext.ux.Toast.msg(ontheway.i18n('tfr.ontheway.OnthewayGrid.tips.label'), result.message, 'error', 1000);
			}
		});
	}
});
Ext.define('Foss.ontheway.QueryForm', {
			extend : 'Ext.form.Panel',
			layout : 'column',
//			height:150,
			frame : true,
			border : false,
			title :ontheway.i18n('tfr.ontheway.QueryForm.form.title'),// '查询在途车辆',
			defaults : {
				margin : '5 5 5 5',
				columns : 4
			},
			items : [{
				xtype : 'commontruckselector',
				fieldLabel : ontheway.i18n('tfr.ontheway.QueryForm.form.vehicleNo.label'),//'车牌号',
				name : 'vehicleNo',
				displayField : 'name',
				valueField : 'code',
				columnWidth : .25,
				listeners: {
					'select': function(field, records, eOpts) {
						var record = records[0];
						ontheway.queryform.getForm().findField('vehicleNo').setValue(record.get('vehicleNo'));
					}
				}
			}, {
				xtype : 'dynamicorgcombselector',
				fieldLabel : ontheway.i18n('tfr.ontheway.QueryForm.form.origOrgName.label'),//'出发部门',
				type : 'ORG',
				salesDepartment : 'Y',// 查询营业部 配置此值
				transferCenter : 'Y',// 查询外场 配置此值
				transDepartment : 'Y',
				name : 'origOrgCode',
				columnWidth : .25
			}, {
				xtype : 'dynamicorgcombselector',
				fieldLabel : ontheway.i18n('tfr.ontheway.QueryForm.form.destOrgName.label'),//'到达部门',
				type : 'ORG',
//				salesDepartment : 'Y',// 查询营业部 配置此值
//				transferCenter : 'Y',// 查询外场 配置此值
//				transDepartment : 'Y',
				name : 'destOrgCode',
				columnWidth : .25,
				listeners :{
					render : function(panel,text){
						var cmbOrgCode = this.up('form').getForm().findField('destOrgCode');
						Ext.Ajax.request({
							url : ontheway.realPath('querySuperiorOrgByOrgCode.action'),
							success : function(response) {
								var result = Ext
										.decode(response.responseText);
									cmbOrgCode.getStore().load({params:{'commonOrgVo.name' : result.onthewayVO.arrivalEntity.origOrgName}});
					                cmbOrgCode.setValue(result.onthewayVO.arrivalEntity.origOrgCode);
							}
						});
				}
				}
			}, {//用于判断条件的类型
				xtype : 'hiddenfield',
				id : 'Foss_ontheway_QueryForm_filterCondition_Id',
				name : 'filterCondition',
				columnWidth : .25
			},  {
				xtype : 'combo',
				fieldLabel : ontheway.i18n('tfr.ontheway.QueryForm.form.businessType.label'),//'车辆业务类型',
				value : 'ALL',
				name : 'businessType',
				displayField : 'valueName',
				valueField : 'valueCode',
				columnWidth : .25,
				queryMode : 'local',
				triggerAction : 'all',
				editable : false,
				store : FossDataDictionary. getDataDictionaryStore('ARRIVE_TYPE_TFR','Foss_login_language_store_Id',{
                                                                                           'valueCode': 'ALL',
                                                                                           'valueName': '全部'
                                                                                    })
				},  {
				xtype : 'combo',
				fieldLabel :ontheway.i18n('tfr.ontheway.QueryForm.form.truckType.label'),// '车辆归属类型',
				value : 'ALL',
				name : 'truckType',
				displayField : 'name',
				valueField : 'code',
				columnWidth : .25,
				queryMode : 'local',
				triggerAction : 'all',
				editable : false,
				store : Ext.create('Foss.ontheway.VehicleTypeStore')
				}, {
				xtype : 'combo',
				fieldLabel : ontheway.i18n('tfr.ontheway.QueryForm.form.vehicleStatus.label'),//'车辆状态',
				value : 'ONTHEWAY',
				name : 'vehicleStatus',
				displayField : 'name',
				valueField : 'code',
				columnWidth : .25,
				queryMode : 'local',
				triggerAction : 'all',
				editable : false,
				store : Ext.create('Foss.ontheway.VehicleStatusStore')
				}, {
				xtype : 'rangeDateField',
				fieldLabel :ontheway.i18n('tfr.ontheway.QueryForm.form.departstartTime.label'),// '出发时间',
				columnWidth : .5,
				// 类型为datetimefield_date97需要配置fieldId,可以赋予此属性任何唯一标 //识的String值
				fieldId:'Foss_ontheway_QueryForm_departTime_Id',
				 dateType : 'datetimefield_date97',
				 disallowBlank:true,
				 allowBlank:false,
				 dateRange:30,
				fromName : 'departstartTime',
				fromValue : Ext.Date.format(new Date(new Date().getFullYear(),
								new Date().getMonth(), new Date().getDate(),
								'00', '00', '00'), 'Y-m-d H:i:s'),
				toName : 'departendTime',
				toValue : Ext.Date.format(new Date(new Date().getFullYear(),
								new Date().getMonth(), new Date().getDate(),
								'23', '59', '59'), 'Y-m-d H:i:s')
			}, {
				xtype : 'rangeDateField',
				fieldLabel : ontheway.i18n('tfr.ontheway.QueryForm.form.startTime.label'),//'最后一次跟踪时间',
				columnWidth : .5,
				labelWidth:150,
				// 类型为datetimefield_date97需要配置fieldId,可以赋予此属性任何唯一标 //识的String值
				fieldId:'Foss_ontheway_QueryForm_createTime_Id',
				 dateType : 'datetimefield_date97',
				 disallowBlank:false,
				 allowBlank:true,
				 dateRange:30,
				fromName : 'startTime',
				toName : 'endTime'
			},  {
				xtype : 'combo',
				fieldLabel : ontheway.i18n('tfr.ontheway.QueryForm.form.runStatus.label'),//'运行状态',
				value : 'ALL',
				name : 'runStatus',
				displayField : 'name',
				valueField : 'code',
				columnWidth : .25,
				queryMode : 'local',
				triggerAction : 'all',
				editable : false,
				store : Ext.create('Foss.ontheway.CurrentStatusStore')
				}, {
				xtype : 'dynamicorgcombselector',
				fieldLabel : ontheway.i18n('tfr.ontheway.QueryForm.form.truckOrgName.label'),//'车辆归属部门',
				name : 'truckOrgCode',
				transDepartment:'Y',
				type : 'ORG',
				columnWidth : .25
			}, {
				xtype : 'combo',
				fieldLabel :ontheway.i18n('tfr.ontheway.QueryForm.form.trackingType.label'),// '跟踪方式',
				value : 'ALL',
				name : 'trackingType',
				displayField : 'name',
				valueField : 'code',
				columnWidth : .25,
				queryMode : 'local',
				triggerAction : 'all',
				editable : false,
				store : Ext.create('Foss.ontheway.TrackingTypeStore')
			}, {
				xtype : 'combo',
				fieldLabel : '预计到达状态',// 预计到达状态
				value : 'ALL',
				name : 'planArrivalStatus',
				displayField : 'name',
				valueField : 'code',
				columnWidth : .25,
				queryMode : 'local',
				triggerAction : 'all',
				editable : false,
				store : Ext.create('Foss.ontheway.PlanArrivalStatusStore')
			}, {
				border : 1,
				xtype : 'container',
				columnWidth : 1,
				defaultType : 'button',
				layout : 'column',
				items : [ {
					text :ontheway.i18n('tfr.ontheway.QueryForm.form.reset.button'),// '重置',
					columnWidth : .1,
					handler : function() {
						this.up('form').getForm().reset();
						this.up('form').getForm().findField('departstartTime').setValue(Ext.Date.format(new Date(new Date().getFullYear(),
								new Date().getMonth(), new Date().getDate(),
								'00', '00', '00'), 'Y-m-d H:i:s'));
						this.up('form').getForm().findField('departendTime').setValue(Ext.Date.format(new Date(new Date().getFullYear(),
										new Date().getMonth(), new Date().getDate(),
										'23', '59', '59'), 'Y-m-d H:i:s')); 
						
						
						var cmbOrgCode = this.up('form').getForm().findField('destOrgCode');
						Ext.Ajax.request({
							url : ontheway.realPath('querySuperiorOrgByOrgCode.action'),
							success : function(response) {
								var result = Ext
										.decode(response.responseText);
									cmbOrgCode.getStore().load({params:{'commonOrgVo.name' : result.onthewayVO.arrivalEntity.origOrgName}});
					                cmbOrgCode.setValue(result.onthewayVO.arrivalEntity.origOrgCode);
							}
						});
					}
				}, {
					xtype : 'container',
					columnWidth : .78,
					html : '&nbsp;'
				}, {
					text :ontheway.i18n('tfr.ontheway.QueryForm.form.query.button'),// '查询',
					columnWidth : .1,
					cls:'yellow_button',
					handler : function() {
						Ext.getCmp('Foss_ontheway_QueryForm_filterCondition_Id').setValue("");
						ontheway.pagingBar.moveFirst();
					}
				}]
			}],
			constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				me.callParent([cfg]);
			}
		});
Ext.define('Foss.ontheway.PrintManualWindow', {
			extend : 'Ext.window.Window',
//			id : 'Foss.ontheway.PrintManualWindow_ID',
			// title: lineinfo.i18n('foss.lineinfo.windowTitle'),
			title : ontheway.i18n('tfr.ontheway.PrintManualWindow.form.label'),//'手动跟踪录入',
			bodyCls : 'autoHeight',
			cls : 'autoHeight',
			modal : true,
			closeAction : 'hide',
			width : 800,
			manualForm : null,
			getManualForm : function() {
				if (this.manualForm == null) {
					this.manualForm = Ext
							.create('Foss.ontheway.PrintManualForm')
				}
				return this.manualForm;
			},
			constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				me.items = [me.getManualForm()];
				ontheway.manualForm = me.getManualForm();
				me.callParent([cfg]);
			}
		});
// 打印纸质放行条的form
Ext.define('Foss.ontheway.PrintManualForm', {
	extend : 'Ext.form.Panel',
	layout : 'column',
	frame : true,
	border : true,
	defaults : {
		margin : '5 5 5 5',
		columns : 4
	},
	items : [{
				xtype : 'hiddenfield',
				name : 'truckTaskDetailId'
			},{
				xtype : 'textfield',
				fieldLabel : ontheway.i18n('tfr.ontheway.PrintManualForm.form.vehicleNo.label'),// '车牌号',
				readOnly:true,
				name : 'vehicleNo',
				columnWidth : .5,
				labelWidth : 60,
				labelWidth : 100,
				allowBlank : false
			}, {
				xtype : 'textfield',
				fieldLabel : ontheway.i18n('tfr.ontheway.PrintManualForm.form.currentPlace.label'),// '车辆所处路段',
				name : 'currentPlace',
				id : 'Foss.ontheway.PrintManualForm.DRIVER_CODE_ID',
				columnWidth : .5,
				maxLength:50,
				maxLengthText: '输入的字符数不能超过50个',
				labelWidth : 120
			}, {
				xtype : 'radiogroup', // 单选框组
				fieldLabel : ontheway.i18n('tfr.ontheway.PrintManualForm.form.radiogroup.label'),// '临时放行类型', // 复选框组的字段标签
				columns : 3, // 3列来存放选择框项
				vertical : true, // 按照columns中指定的列数来分配单选框组
				frame : true,
				columnWidth : 1,
				blankText : ontheway.i18n('tfr.ontheway.PrintManualForm.form.noitems.label'),// '没有选择项',// 当没有选择时的提示信息
				items : [{
							boxLabel :  ontheway.i18n('tfr.ontheway.PrintManualForm.form.RUN.label'),//'正常运行',
							name : 'currentStatus', // 表单的参数名
							inputValue : 'RUN', // 表单的参数值
							checked : true
						}, {
							boxLabel :  ontheway.i18n('tfr.ontheway.PrintManualForm.form.ACCIDENTS.label'),//'事故车辆',
							name : 'currentStatus', // 表单的参数名
							inputValue : 'ACCIDENTS' // 表单的参数值
						}, {
							boxLabel :  ontheway.i18n('tfr.ontheway.PrintManualForm.form.TRAFFIC.label'),//'堵车',
							name : 'currentStatus', // 表单的参数名
							inputValue : 'TRAFFIC' // 表单的参数值
						}, {
							boxLabel :  ontheway.i18n('tfr.ontheway.PrintManualForm.form.SLOWRUN.label'),//'缓行',
							name : 'currentStatus', // 表单的参数名
							inputValue : 'SLOWRUN' // 表单的参数值
						}, {
							boxLabel :  ontheway.i18n('tfr.ontheway.PrintManualForm.form.DETAINING.label'),//'扣车',
							name : 'currentStatus', // 表单的参数名
							inputValue : 'DETAINING' // 表单的参数值
						}, {
							boxLabel :  ontheway.i18n('tfr.ontheway.PrintManualForm.form.OTHERS.label'),//'扣车',
							name : 'currentStatus', // 表单的参数名
							inputValue : 'OTHERS' // 表单的参数值
						}]
			}, {
				xtype : 'datetimefield_date97',
				fieldLabel :  ontheway.i18n('tfr.ontheway.PrintManualForm.form.planArriveTime.label'),//'预计到达时间',
				id:'Foss_ontheway_PrintManualForm_planArriveTime_ID',
				name : 'planArriveTime',
				columnWidth : .5,
				format : 'Y-m-d H:i:s',
				dateConfig: {
					el: 'Foss_ontheway_PrintManualForm_planArriveTime_ID-inputEl',
					dateFmt: 'Y-m-d H:i:s'
				}
				}, {
				xtype : 'textfield',
				fieldLabel :  ontheway.i18n('tfr.ontheway.PrintManualForm.form.trackingTime.label'),//'最后一次跟踪时间',
				name : 'trackingTime',
				readOnly:true,
				columnWidth : .5,
				value:Ext.Date.format(new Date(new Date().getFullYear(),
								new Date().getMonth(), new Date().getDate(),
								'00', '00', '00'), 'Y-m-d H:i:s'),
				labelWidth : 120
			}, {
				xtype : 'textarea',
				// 当内容超过文本域的范围时，出现滚动条
				autoScroll : true,
				// 指定文本中的值
				fieldLabel : ontheway.i18n('tfr.ontheway.PrintManualForm.form.notes.label'),// '备注（途中异常才备注）',
				labelWidth : 160,
				maxLength:100,
				maxLengthText: '输入的字符数不能超过100个',
				// 指定数据绑定与数据提交时的属性名
				name : 'notes',
				id : 'Foss.ontheway.PrintManualForm.MANUAL_NOTE_ID',
				columnWidth : 1

			}, {
				xtype : 'container',
				columnWidth : .6,
				html : '&nbsp;'
			},{
				xtype : 'button',
					text :  ontheway.i18n('tfr.ontheway.PrintManualForm.form.reset.button'),//'清空',
					columnWidth : .2,
					handler : function() {
						var vehicleNo = this.up('form').getValues().vehicleNo;
						var truckTaskDetailId = this.up('form').getValues().truckTaskDetailId;
						this.up('form').getForm().reset();
						this.up('form').getForm().findField('vehicleNo').setValue(vehicleNo);
						this.up('form').getForm().findField('truckTaskDetailId').setValue(truckTaskDetailId);
					}
				}, {
				xtype : 'button',
				text :  ontheway.i18n('tfr.ontheway.PrintManualForm.form.save.button'),//'保存',
				columnWidth : .2,
				handler : function() {
					var manualWindow = Ext.getCmp('T_ontheway-index_content')
							.getManualWindow();
					var formParams = manualWindow.manualForm.getValues();
					if (!manualWindow.getManualForm().getForm().isValid())
						return false;
					var onthewayVO = {
						'onthewayVO.manualEntity.truckTaskDetailId' : formParams.truckTaskDetailId,
						'onthewayVO.manualEntity.vehicleNo' : formParams.vehicleNo,
						'onthewayVO.manualEntity.currentPlace' : formParams.currentPlace,
						'onthewayVO.manualEntity.planArriveTime' : formParams.planArriveTime,
						'onthewayVO.manualEntity.currentStatus' : formParams.currentStatus,
						'onthewayVO.manualEntity.trackingTime' : formParams.trackingTime,
						'onthewayVO.manualEntity.notes' : formParams.notes
					};
					Ext.Ajax.request({
								url : ontheway.realPath('addManual.action'),
								params : onthewayVO,
								success : function(response) {
									Ext.ux.Toast.msg( ontheway.i18n('tfr.ontheway.OnthewayGrid.tips.label'),  ontheway.i18n('tfr.ontheway.PrintManualForm.form.ok.tips'), 'ok', 3000);
									manualWindow.hide();
									ontheway.pagingBar.moveFirst();
								}
							});
				}
			}],

	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

// 跟踪明细
Ext.define('Foss.ontheway.DetailGridModel', {
			extend : 'Ext.data.Model',
			fields : [{
						name : 'currentPlace',
						type : 'string'
					}, {
						name : 'currentStatus',
						type : 'string'
					}, {
						name : 'currentSppend',
						type : 'string'
					}, {
						name : 'planArriveTime',
						type : 'date',
						convert : dateConvert
					}, {
						name : 'trackingType',
						type : 'string'
					}, {
						name : 'trackingUserName',
						type : 'string'
					}, {
						name : 'trackingTime',
						type : 'date',
						convert : dateConvert
					}]
		});

// 跟踪明细
Ext.define('Foss.ontheway.DetailGridPanel', {
			extend : 'Ext.grid.Panel',
			frame : true,
			columnWidth : 1,
			height:400,
			autoScroll : true,
			columns : [{
						text :ontheway.i18n('tfr.ontheway.DetailGridPanel.grid.currentStatus.label'),// '当前状态',
						dataIndex : 'currentStatus',
						flex : 1,
						renderer : function(value) {
							var vstore = Ext.create('Foss.ontheway.CurrentStatusStore'), srecord = vstore
									.findRecord('code', value);
							if (srecord != null) {
								return srecord.get('name');
							} else {
								return '';
							}
						}
					}, {
						text : ontheway.i18n('tfr.ontheway.DetailGridPanel.grid.trackingTime.label'),//'跟踪时间',
						dataIndex : 'trackingTime',
						flex : 1,
						renderer : function(value) {
							if (value != null && value != '') {
								var date = new Date(value);
								return Ext.Date.format(date, 'Y-m-d H:i:s');
							} else {
								return null;
							}
						}
					}, {
						text :ontheway.i18n('tfr.ontheway.DetailGridPanel.grid.currentPlace.detail.label'),// '当前路段',
						dataIndex : 'currentPlace',
						flex : 1
					}, {
						text : ontheway.i18n('tfr.ontheway.DetailGridPanel.grid.currentSppend.label'),//'当前速度',
						dataIndex : 'currentSppend',
						flex : 1
					}, {
						text : ontheway.i18n('tfr.ontheway.DetailGridPanel.grid.planArriveTime.label'),//'预计到达时间',
						dataIndex : 'planArriveTime',
						flex : 1,
						renderer : function(value) {
							if (value != null && value != '') {
								var date = new Date(value);
								return Ext.Date.format(date, 'Y-m-d H:i:s');
							} else {
								return null;
							}
						}
					}, {
						text :ontheway.i18n('tfr.ontheway.DetailGridPanel.grid.trackingType.label'),// '跟踪方式',
						dataIndex : 'trackingType',
						flex : 1,
						renderer : function(value) {
							var vstore = Ext.create('Foss.ontheway.TrackingTypeStore'), srecord = vstore
									.findRecord('code', value);
							if (srecord != null) {
								return srecord.get('name');
							} else {
								return '';
							}
						}
					}, {
						text : ontheway.i18n('tfr.ontheway.DetailGridPanel.grid.trackingUserName.label'),//'跟踪人',
						dataIndex : 'trackingUserName',
						flex : 1
					}],
			constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				me.callParent([cfg]);
			}
		});

//显示跟踪明细
Ext.define('Foss.ontheway.ShowDetailWindow', {
			extend : 'Ext.window.Window',
			id : 'Foss.ontheway.ShowDetailWindow_ID',
			// title: lineinfo.i18n('foss.lineinfo.windowTitle'),
			title : ontheway.i18n('tfr.ontheway.ShowDetailWindow.window.label'),//'任务跟踪明细',
//			bodyCls : 'autoHeight',
			cls : 'autoHeight',
			modal : true,
			closeAction : 'hide',
			width : 1000,
			height:500,
			detailGrid : null,
			getDetailGrid : function() {
				if (this.detailGrid == null) {
					this.detailGrid = Ext
							.create('Foss.ontheway.DetailGridPanel')
				}
				return this.detailGrid;
			},
			constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				me.items = [me.getDetailGrid()];
				me.callParent([cfg]);
			}
		});		
Ext.onReady(function() {
			Ext.QuickTips.init();
			var queryform = Ext.create('Foss.ontheway.QueryForm');
			var querygrid = Ext.create('Foss.ontheway.OnthewayGrid');
			ontheway.queryform = queryform;
			Ext.create('Ext.panel.Panel', {
						id : 'T_ontheway-index_content',
						cls : "panelContentNToolbar",
						bodyCls : 'panelContent-body',
						manualWindow : null,
						getManualWindow : function() {
							if (this.manualWindow == null) {
								this.manualWindow = Ext
										.create('Foss.ontheway.PrintManualWindow');
							}
							return this.manualWindow;
						},
						showDetailWindow:null,
						getShowDetailWindow : function() {
							if (this.showDetailWindow == null) {
								this.showDetailWindow = Ext
										.create('Foss.ontheway.ShowDetailWindow');
							}
							return this.showDetailWindow;
						},
						items : [queryform,querygrid],
						renderTo : 'T_ontheway-index-body'
					});
		});