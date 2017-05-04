Ext.define('Foss.scheduling.platformDistribute.queryForm', {
			extend : 'Ext.form.Panel',
			title : '查询条件',
			frame : true,
			collapsible : false,
			animCollapse : false,
			defaults : {
				margin : '5 10 5 10',
				labelWidth : 85,
				columnWidth : 1 / 4,
				xtype : 'textfield'
			},
			layout : 'column',
			items : [{
						fieldLabel : '预计时间',
						name : 'minEstimatedArrive',
						xtype : 'combobox',
						queryMode : 'local',
						displayField : 'value',
						valueField : 'key',
						value : 15,
						editable : false,
						store : Ext.create('Ext.data.Store', {
									fields : ['key', 'value'],
									data : [{
												"key" : 15,
												"value" : "15分钟"
											}, {
												"key" : 30,
												"value" : "30分钟"
											}, {
												"key" : 45,
												"value" : "45分钟"
											}, {
												"key" : 60,
												"value" : "60分钟"
											}]
								})
					}, {
						fieldLabel : '交接编号',
						name : 'handoverNo'
					}, {
						xtype : 'commontruckselector',
						fieldLabel : '车牌',
						name : 'vehicleNo'
					}, {
						xtype : 'commonlineselector',
						fieldLabel : '线路',
						valueField : 'orginalOrganizationCode',
						destinationOrganizationCode : scheduling.platformDistribute.parentTfrCtrCode,
						name : 'origDeptCode'
					}],
			buttons : [{
						xtype : 'button',
						columnWidth : .08,
						text : '重置',
						handler : function() {
							this.up('form').getForm().reset();
						}
					}, '->', {
						columnWidth : .08,
						xtype : 'button',
						cls : 'yellow_button',
						text : '查询',
						handler : function() {
							var form = this.up('form').getForm();
							if (form.isValid()) {
								scheduling.platformDistribute.companyOnTheWayPagingBar
										.moveFirst();
								scheduling.platformDistribute.leasedOnTheWayPagingBar
										.moveFirst();
								scheduling.platformDistribute.arrivedPagingBar
										.moveFirst();
							}
						}
					}],
			constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				me.callParent([cfg]);
			}
		});

Ext.define('Foss.scheduling.platformDistribute.companyOnTheWayModel', {
			extend : 'Ext.data.Model',
			fields : [{
						name : 'truckTaskId',
						type : 'string'
					},{
						name : 'taskDetailId',
						type : 'string'
					}, {
						name : 'destDeptCode',
						type : 'string'
					}, {
						name : 'businessType',
						type : 'string'
					}, {
						name : 'vehicleNo',
						type : 'string'
					}, {
						name : 'estimatedArriveTime',
						type : 'date',
						convert : dateConvert
					}, {
						name : 'voteTransferInTime',
						type : 'string'
					},{
						name : 'prePlatformCode',
						type : 'string'
					}, {
						name : 'voteFlf',
						type : 'string'
					}, {
						name : 'voteFsf',
						type : 'string'
					}, {
						name : 'voteAf',
						type : 'string'
					}, {
						name : 'line',
						type : 'string'
					}, {
						name : 'late',
						type : 'string'
					}, {
						name : 'departTime',
						type : 'date',
						convert : dateConvert
					}, {
						name : 'origDeptName',
						type : 'string'
					}, {
						name : 'weightAndVolumn',
						type : 'string'
					}, {
						name : 'time4UnloadTake',
						type : 'string'
					}, {
						name : 'vehicleLengthName',
						type : 'string'
					}, {
						name : 'vehicleLengthCode',
						type : 'string'
					}, {
						name : 'estimatedDepartTime',
						type : 'date',
						convert : dateConvert
					}]
		});

Ext.define('Foss.scheduling.platformDistribute.leasedOnTheWayModel', {
			extend : 'Ext.data.Model',
			fields : [{
						name : 'truckTaskId',
						type : 'string'
					},{
						name : 'taskDetailId',
						type : 'string'
					}, {
						name : 'destDeptCode',
						type : 'string'
					}, {
						name : 'businessType',
						type : 'string'
					}, {
						name : 'vehicleNo',
						type : 'string'
					}, {
						name : 'estimatedArriveTime',
						type : 'date',
						convert : dateConvert
					}, {
						name : 'voteTransferInTime',
						type : 'string'
					},{
						name : 'prePlatformCode',
						type : 'string'
					}, {
						name : 'voteFlf',
						type : 'string'
					}, {
						name : 'voteFsf',
						type : 'string'
					}, {
						name : 'voteAf',
						type : 'string'
					}, {
						name : 'line',
						type : 'string'
					}, {
						name : 'late',
						type : 'string'
					}, {
						name : 'departTime',
						type : 'date',
						convert : dateConvert
					}, {
						name : 'origDeptName',
						type : 'string'
					}, {
						name : 'weightAndVolumn',
						type : 'string'
					}, {
						name : 'time4UnloadTake',
						type : 'string'
					}, {
						name : 'vehicleLengthName',
						type : 'string'
					}, {
						name : 'vehicleLengthCode',
						type : 'string'
					}, {
						name : 'estimatedDepartTime',
						type : 'date',
						convert : dateConvert
					}]
		});

Ext.define('Foss.scheduling.platformDistribute.arrivedModel', {
			extend : 'Ext.data.Model',
			fields : [{
						name : 'truckTaskId',
						type : 'string'
					},{
						name : 'taskDetailId',
						type : 'string'
					}, {
						name : 'destDeptCode',
						type : 'string'
					}, {
						name : 'businessType',
						type : 'string'
					}, {
						name : 'vehicleNo',
						type : 'string'
					}, {
						name : 'actualArriveTime',
						type : 'date',
						convert : dateConvert
					}, {
						name : 'voteTransferInTime',
						type : 'string'
					},{
						name : 'prePlatformCode',
						type : 'string'
					}, {
						name : 'voteFlf',
						type : 'string'
					}, {
						name : 'voteFsf',
						type : 'string'
					}, {
						name : 'voteAf',
						type : 'string'
					}, {
						name : 'timeWait',
						type : 'string'
					}, {
						name : 'departTime',
						type : 'date',
						convert : dateConvert
					}, {
						name : 'line',
						type : 'string'
					}, {
						name : 'vehicleOwnerType',
						type : 'string'
					}, {
						name : 'weightAndVolumn',
						type : 'string'
					}, {
						name : 'time4UnloadTake',
						type : 'string'
					}, {
						name : 'vehicleLengthName',
						type : 'string'
					}, {
						name : 'vehicleLengthCode',
						type : 'string'
					}, {
						name : 'estimatedDepartTime',
						type : 'date',
						convert : dateConvert
					}, {
						name : 'sealCheckTime',
						type : 'date',
						convert : dateConvert
					}]
		});

Ext.define('Foss.scheduling.platformDistribute.companyOnTheWayStore', {
	extend : 'Ext.data.Store',
	pageSize : 20,
	model : 'Foss.scheduling.platformDistribute.companyOnTheWayModel',
	proxy : {
		type : 'ajax',
		actionMethods : 'POST',
		url : scheduling.realPath('queryCompanyOnTheWay.action'),
		reader : {
			type : 'json',
			root : 'platformDistributeVo.vehicleInfosCompanyOnTheWay',
			successProperty : 'success',
			totalProperty : 'totalCount'
		}
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	},
	listeners : {
		'beforeload' : function(store, operation, eOpts) {
			var queryForm = scheduling.platformDistribute.queryForm.getForm();
			if (!Ext.isEmpty(queryForm)) {
				var queryParams = queryForm.getValues();
				Ext.apply(operation, {
					params : {
						'platformDistributeVo.platformDistributeQcDto.minEstimatedArrive' : queryParams.minEstimatedArrive,
						'platformDistributeVo.platformDistributeQcDto.handoverNo' : queryParams.handoverNo,
						'platformDistributeVo.platformDistributeQcDto.vehicleNo' : queryParams.vehicleNo,
						'platformDistributeVo.platformDistributeQcDto.origDeptCode' : queryParams.origDeptCode,
						'platformDistributeVo.platformDistributeQcDto.destDeptCode' : scheduling.platformDistribute.parentTfrCtrCode
					}
				});
			}
		}
	}
});

Ext.define('Foss.scheduling.platformDistribute.leasedOnTheWayStore', {
	extend : 'Ext.data.Store',
	pageSize : 20,
	model : 'Foss.scheduling.platformDistribute.leasedOnTheWayModel',
	proxy : {
		type : 'ajax',
		actionMethods : 'POST',
		url : scheduling.realPath('queryLeasedOnTheWay.action'),
		reader : {
			type : 'json',
			root : 'platformDistributeVo.vehicleInfosLeasedOnTheWay',
			successProperty : 'success',
			totalProperty : 'totalCount'
		}
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	},
	listeners : {
		'beforeload' : function(store, operation, eOpts) {
			var queryForm = scheduling.platformDistribute.queryForm.getForm();
			if (!Ext.isEmpty(queryForm)) {
				var queryParams = queryForm.getValues();
				Ext.apply(operation, {
					params : {
						'platformDistributeVo.platformDistributeQcDto.minEstimatedArrive' : queryParams.minEstimatedArrive,
						'platformDistributeVo.platformDistributeQcDto.handoverNo' : queryParams.handoverNo,
						'platformDistributeVo.platformDistributeQcDto.vehicleNo' : queryParams.vehicleNo,
						'platformDistributeVo.platformDistributeQcDto.origDeptCode' : queryParams.origDeptCode,
						'platformDistributeVo.platformDistributeQcDto.destDeptCode' : scheduling.platformDistribute.parentTfrCtrCode
					}
				});
			}
		}
	}
});

Ext.define('Foss.scheduling.platformDistribute.arrivedStore', {
	extend : 'Ext.data.Store',
	pageSize : 20,
	model : 'Foss.scheduling.platformDistribute.arrivedModel',
	proxy : {
		type : 'ajax',
		actionMethods : 'POST',
		url : scheduling.realPath('queryArrived.action'),
		reader : {
			type : 'json',
			root : 'platformDistributeVo.vehicleInfosArrived',
			successProperty : 'success',
			totalProperty : 'totalCount'
		}
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	},
	listeners : {
		'beforeload' : function(store, operation, eOpts) {
			var queryForm = scheduling.platformDistribute.queryForm.getForm();
			if (!Ext.isEmpty(queryForm)) {
				var queryParams = queryForm.getValues();
				Ext.apply(operation, {
					params : {
						'platformDistributeVo.platformDistributeQcDto.handoverNo' : queryParams.handoverNo,
						'platformDistributeVo.platformDistributeQcDto.vehicleNo' : queryParams.vehicleNo,
						'platformDistributeVo.platformDistributeQcDto.origDeptCode' : queryParams.origDeptCode,
						'platformDistributeVo.platformDistributeQcDto.destDeptCode' : scheduling.platformDistribute.parentTfrCtrCode
					}
				});
			}
		}
	}
});

Ext.define('Foss.scheduling.platformDistribute.companyOnTheWayGrid', {
	extend : 'Ext.grid.Panel',
	frame : true,
	columnLines : true,
	title : '未到达公司车',
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	emptyText : '查询结果为空',
	autoScroll : true,
	collapsible : false,
	animCollapse : false,
	viewConfig: {
        stripeRows: false,
		getRowClass: function(record, rowParams, rp, store) {
			var prePlatformCode = record.get('prePlatformCode');
			if(Ext.isEmpty(prePlatformCode)) {
				return 'platformDistribute_query_background';
			}
		}
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext
				.create('Foss.scheduling.platformDistribute.companyOnTheWayStore');
		me.tbar = ['->', {
					text : '<p style="color:red">注:可及时中转票数只预计优势业务是否可以在下次中转时间前卸出;未预分配月台车辆标记为灰色，请及时分配。</p>',
					disabled : true
				}];
		me.columns = [{
			xtype : 'actioncolumn',
			width : 60,
			text : '分配月台',
			align : 'center',
			menuDisabled : true,
			hideable : false,
			sortable : false,
			items : [{
				tooltip : '分配月台',
				iconCls : 'deppon_icons_dispose',
				handler : function(grid, rowIndex, colIndex) {
					var record = grid.getStore().getAt(rowIndex);
					if (Ext.isEmpty(record)) {
						Ext.ux.Toast.msg('提示', '车辆任务明细不存在！', 'info', 1500);
						return;
					}
					Ext.getCmp('T_scheduling-platformDistributeIndex_content')
							.getDistributeWindow(record).show();
					
					scheduling.platformDistribute.truckTaskId = record.get('truckTaskId');
				}
			}]
		}, {
			dataIndex : 'vehicleNo',
			align : 'center',
			width : 80,
			text : '车牌'
		}, {
			dataIndex : 'estimatedArriveTime',
			align : 'center',
			width : 130,
			text : '预计到达时间',
			xtype : 'datecolumn',
			format : 'Y-m-d H:i:s'
		}, {
			dataIndex : 'voteTransferInTime',
			align : 'center',
			width : 140,
			text : '预计可及时中转票数'
		}, {
			dataIndex : 'prePlatformCode',
			align : 'center',
			width : 80,
			text : '预分配月台号'
		}, {
			dataIndex : 'voteFlf',
			align : 'center',
			width : 80,
			text : '卡航票数'
		}, {
			dataIndex : 'voteFsf',
			align : 'center',
			width : 80,
			text : '城运票数'
		}, {
			dataIndex : 'voteAf',
			align : 'center',
			width : 80,
			text : '空运票数'
		}, {
			dataIndex : 'line',
			align : 'center',
			width : 250,
			text : '线路'
		}, {
			dataIndex : 'late',
			align : 'center',
			width : 85,
			text : '预计到达情况',
			renderer : function(value) {
				if (value == 'Y') {
					return "晚到";
				}
				if (value == 'N') {
					return '';
				}
				return value;
			}
		}, {
			dataIndex : 'departTime',
			align : 'center',
			width : 130,
			text : '出发时间',
			xtype : 'datecolumn',
			format : 'Y-m-d H:i:s'
		}, {
			dataIndex : 'origDeptName',
			align : 'center',
			width : 150,
			text : '出发部门'
		}];
		me.bbar = Ext.create('Deppon.StandardPaging', {
					store : me.store,
					pageSize : 20,
					maximumSize : 50,
					plugins : Ext.create('Deppon.ux.PageSizePlugin', {
								sizeList : [['20', 20], ['30', 30], ['40', 40],
										['50', 50]]
							})
				});
		scheduling.platformDistribute.companyOnTheWayPagingBar = me.bbar;
		me.callParent([cfg]);
	}
});

Ext.define('Foss.scheduling.platformDistribute.leasedOnTheWayGrid', {
	extend : 'Ext.grid.Panel',
	frame : true,
	columnLines : true,
	title : '未到达外请车',
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	emptyText : '查询结果为空',
	autoScroll : true,
	collapsible : false,
	animCollapse : false,
	viewConfig: {
        stripeRows: false,
		getRowClass: function(record, rowParams, rp, store) {
			var prePlatformCode = record.get('prePlatformCode');
			if(Ext.isEmpty(prePlatformCode)) {
				return 'platformDistribute_query_background';
			}
		}
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext
				.create('Foss.scheduling.platformDistribute.leasedOnTheWayStore');
		me.columns = [{
			xtype : 'actioncolumn',
			width : 60,
			text : '分配月台',
			align : 'center',
			menuDisabled : true,
			hideable : false,
			sortable : false,
			items : [{
				tooltip : '分配月台',
				iconCls : 'deppon_icons_dispose',
				handler : function(grid, rowIndex, colIndex) {
					var record = grid.getStore().getAt(rowIndex);
					if (Ext.isEmpty(record.get('taskDetailId'))) {
						Ext.ux.Toast.msg('提示', '车辆任务明细不存在！', 'info', 1500);
						return;
					}
					Ext.getCmp('T_scheduling-platformDistributeIndex_content')
							.getDistributeWindow(record).show();
					scheduling.platformDistribute.truckTaskId = record.get('truckTaskId');
				}
			}]
		}, {
			dataIndex : 'vehicleNo',
			align : 'center',
			width : 80,
			text : '车牌'
		}, {
			dataIndex : 'estimatedArriveTime',
			align : 'center',
			width : 130,
			text : '预计到达时间',
			xtype : 'datecolumn',
			format : 'Y-m-d H:i:s'
		}, {
			dataIndex : 'voteTransferInTime',
			align : 'center',
			width : 140,
			text : '预计可及时中转票数'
		}, {
			dataIndex : 'prePlatformCode',
			align : 'center',
			width : 80,
			text : '预分配月台号'
		}, {
			dataIndex : 'voteFlf',
			align : 'center',
			width : 80,
			text : '卡航票数'
		}, {
			dataIndex : 'voteFsf',
			align : 'center',
			width : 80,
			text : '城运票数'
		}, {
			dataIndex : 'voteAf',
			align : 'center',
			width : 80,
			text : '空运票数'
		}, {
			dataIndex : 'line',
			align : 'center',
			width : 250,
			text : '线路'
		}, {
			dataIndex : 'late',
			align : 'center',
			width : 85,
			text : '预计到达情况',
			renderer : function(value) {
				if (value == 'Y') {
					return "晚到";
				}
				if (value == 'N') {
					return '';
				}
				return value;
			}
		}, {
			dataIndex : 'departTime',
			align : 'center',
			width : 130,
			text : '出发时间',
			xtype : 'datecolumn',
			format : 'Y-m-d H:i:s'
		}, {
			dataIndex : 'origDeptName',
			align : 'center',
			width : 150,
			text : '出发部门'
		}];
		me.bbar = Ext.create('Deppon.StandardPaging', {
					store : me.store,
					pageSize : 20,
					maximumSize : 50,
					plugins : Ext.create('Deppon.ux.PageSizePlugin', {
								sizeList : [['20', 20], ['30', 30], ['40', 40],
										['50', 50]]
							})
				});
		scheduling.platformDistribute.leasedOnTheWayPagingBar = me.bbar;
		me.callParent([cfg]);
	}
});

Ext.define('Foss.scheduling.platformDistribute.arrivedGrid', {
			extend : 'Ext.grid.Panel',
			frame : true,
			columnLines : true,
			title : '到达未卸车',
			bodyCls : 'autoHeight',
			cls : 'autoHeight',
			emptyText : '查询结果为空',
			autoScroll : true,
			collapsible : false,
			animCollapse : false,
			viewConfig: {
		        stripeRows: false,
				getRowClass: function(record, rowParams, rp, store) {
					var prePlatformCode = record.get('prePlatformCode');
					if(Ext.isEmpty(prePlatformCode)) {
						return 'platformDistribute_query_background';
					}
				}
			},
			constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				me.store = Ext
						.create('Foss.scheduling.platformDistribute.arrivedStore');
				me.columns = [{
					xtype : 'actioncolumn',
					width : 60,
					text : '分配月台',
					align : 'center',
					menuDisabled : true,
					hideable : false,
					sortable : false,
					items : [{
						tooltip : '分配月台',
						iconCls : 'deppon_icons_dispose',
						handler : function(grid, rowIndex, colIndex) {
							var record = grid.getStore().getAt(rowIndex);
							if (Ext.isEmpty(record.get('taskDetailId'))) {
								Ext.ux.Toast.msg('提示', '车辆任务明细不存在！', 'info',
										1500);
								return;
							}
							Ext
									.getCmp('T_scheduling-platformDistributeIndex_content')
									.getDistributeWindow(record).show();

							scheduling.platformDistribute.truckTaskId = record.get('truckTaskId');
						}
					}]
				}, {
					dataIndex : 'vehicleNo',
					align : 'center',
					width : 80,
					text : '车牌'
				}, {
					dataIndex : 'actualArriveTime',
					align : 'center',
					width : 130,
					text : '到达时间',
					xtype : 'datecolumn',
					format : 'Y-m-d H:i:s'
				},{
					dataIndex : 'sealCheckTime',
					align : 'center',
					width : 130,
					text : '入场时间',
					xtype : 'datecolumn',
					format : 'Y-m-d H:i:s'
				},  {
					dataIndex : 'voteTransferInTime',
					align : 'center',
					width : 140,
					text : '预计可及时中转票数'
				}, {
					dataIndex : 'prePlatformCode',
					align : 'center',
					width : 80,
					text : '预分配月台号'
				}, {
					dataIndex : 'voteFlf',
					align : 'center',
					width : 80,
					text : '卡航票数'
				}, {
					dataIndex : 'voteFsf',
					align : 'center',
					width : 80,
					text : '城运票数'
				}, {
					dataIndex : 'voteAf',
					align : 'center',
					width : 80,
					text : '空运票数'
				}, {
					dataIndex : 'line',
					align : 'center',
					width : 250,
					text : '线路'
				}, {
					dataIndex : 'timeWait',
					align : 'center',
					text : '等待时长'
				}, {
					dataIndex : 'departTime',
					align : 'center',
					width : 130,
					text : '出发时间',
					xtype : 'datecolumn',
					format : 'Y-m-d H:i:s'
				}, {
					dataIndex : 'vehicleOwnerType',
					align : 'center',
					width : 90,
					text : '车辆归属类型',
					renderer : function(value) {
						if (value == 'Company') {
							return "公司车";
						}
						if (value == 'Leased') {
							return '外请车';
						}
						return value;
					}
				}];
				me.bbar = Ext.create('Deppon.StandardPaging', {
							store : me.store,
							pageSize : 20,
							maximumSize : 50,
							plugins : Ext.create('Deppon.ux.PageSizePlugin', {
										sizeList : [['20', 20], ['30', 30],
												['40', 40], ['50', 50]]
									})
						});
				scheduling.platformDistribute.arrivedPagingBar = me.bbar;
				me.callParent([cfg]);
			}
		});

Ext.define('Foss.scheduling.platformDistribute.vehicleInfoForm', {
			extend : 'Ext.form.Panel',
			title : '车辆信息',
			frame : false,
			collapsible : false,
			animCollapse : false,
			defaults : {
				margin : '5 10 5 10',
				labelWidth : 85,
				columnWidth : 1 / 2,
				xtype : 'displayfield',
				allowBlank : true
			},
			layout : 'column',
			items : [{
						fieldLabel : '线路',
						name : 'line'
					}, {
						fieldLabel : '载重体积',
						name : 'weightAndVolumn'
					}, {
						fieldLabel : '车型',
						name : 'vehicleLengthName'
					}, {
						fieldLabel : '预计卸车时长',
						name : 'time4UnloadTake'
					}, {
						fieldLabel : '预计到达时间',
						name : 'estimatedArriveTime',
						xtype : 'datefield',
						format : 'Y-m-d H:i:s',
						readOnly : true
					}],
			constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				me.callParent([cfg]);
			}
		});

Ext.define('Foss.scheduling.platformDistribute.acquireForm', {
			extend : 'Ext.form.Panel',
			title : '月台分配',
			frame : false,
			collapsible : false,
			animCollapse : false,
			defaults : {
				margin : '5 10 5 10',
				labelWidth : 85,
				xtype : 'hiddenfield',
				allowBlank : true
			},
			layout : 'column',
			items : [{
						fieldLabel : '车辆任务明细id',
						name : 'taskDetailId'
					}, {
						fieldLabel : '类型(长途/短途)',
						name : 'businessType'
					}, {
						fieldLabel : '到达部门(及当前外场)',
						name : 'destDeptCode'
					}, {
						fieldLabel : '车型code',
						name : 'vehicleLengthCode'
					}, {
						fieldLabel : '状态(在途/到达)',
						name : 'status'
					}, {
						fieldLabel : '预计到达时间',
						name : 'estimatedArriveTime'
					}, {
						xtype : 'textfield',
						readOnly : true,
						fieldLabel : '车牌',
						name : 'vehicleNo',
						columnWidth : .5
					}, {
						xtype : 'container',
						columnWidth : .2,
						html : '&nbsp;'
					}, {
						xtype : 'checkboxfield',
						boxLabel : '只显示空月台',
						name : 'onlyFree',
						columnWidth : .3
					}, {
						xtype : 'button',
						text : '获取月台',
						cls : 'yellow_button',
						handler : function() {
							var form = this.up('form').getForm();
							if (form.isValid()) {
								var platformGrid = scheduling.platformDistribute.distributeWindow.getPlatformGrid();
								platformGrid.store.load(function(records, operation, success){
									if(Ext.isEmpty(records)){
										Ext.ux.Toast.msg('提示', '系统无合适建议月台！', 'info', 1500);
									}
								});
							}
						}
					}],
			constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				me.callParent([cfg]);
			}
		});

Ext.define('Foss.scheduling.platformDistribute.platformModel', {
			extend : 'Ext.data.Model',
			fields : [{
						name : 'virtualCode',
						type : 'string'
					}, {
						name : 'platformCode',
						type : 'string'
					}, {
						name : 'platformType',
						type : 'string'
					}, {
						name : 'vehicleLengthName',
						type : 'string'
					}, {
						name : 'status',
						type : 'string'
					}, {
						name : 'hasLift',
						type : 'string'
					}]
		});

Ext.define('Foss.scheduling.platformDistribute.platformStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.scheduling.platformDistribute.platformModel',
	proxy : {
		type : 'ajax',
		actionMethods : 'POST',
		url : scheduling.realPath('queryOptimalPlatform.action'),
		timeout : 300000,
		reader : {
			type : 'json',
			root : 'platformDistributeVo.platformDtos',
			successProperty : 'success'
		}
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	},
	listeners : {
		'beforeload' : function(store, operation, eOpts) {
			var acquireForm = scheduling.platformDistribute.distributeWindow
					.getAcquireForm().getForm();
			if (!Ext.isEmpty(acquireForm)) {
				var acquireParams = acquireForm.getValues();
				Ext.apply(operation, {
					params : {
						'platformDistributeVo.platformQcDto.taskDetailId' : acquireParams.taskDetailId,
						'platformDistributeVo.platformQcDto.vehicleNo' : acquireParams.vehicleNo,
						'platformDistributeVo.platformQcDto.businessType' : acquireParams.businessType,
						'platformDistributeVo.platformQcDto.destDeptCode' : acquireParams.destDeptCode,
						'platformDistributeVo.platformQcDto.vehicleLengthCode' : acquireParams.vehicleLengthCode
					}
				});
			}
		}
	}
});

Ext.define('Foss.scheduling.platformDistribute.platformGrid', {
	extend : 'Ext.grid.Panel',
	height : 200,
	frame : true,
	columnLines : true,
	title : '系统建议月台',
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	emptyText : '',
	autoScroll : true,
	collapsible : false,
	animCollapse : false,
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext
				.create('Foss.scheduling.platformDistribute.platformStore');
		me.columns = [{
			xtype : 'actioncolumn',
			width : 60,
			text : '选择月台',
			align : 'center',
			menuDisabled : true,
			hideable : false,
			sortable : false,
			items : [{
				tooltip : '选择月台',
				iconCls : 'deppon_icons_affirm',
				handler : function(grid, rowIndex, colIndex) {
					var record = grid.getStore().getAt(rowIndex);
					if (Ext.isEmpty(record)) {
						Ext.ux.Toast.msg('提示', '月台不存在！', 'info', 1500);
						return;
					}
					var distributeForm = scheduling.platformDistribute.distributeWindow
							.getDistributeForm().getForm();
					var platformField = distributeForm.findField('platform');
					platformField.setCombValue(record.get('platformCode'),
							record.get('virtualCode'));
				}
			}]
		}, {
			dataIndex : 'platformCode',
			align : 'center',
			text : '月台号'
		}, {
			dataIndex : 'platformType',
			align : 'center',
			text : '月台类型'
		}, {
			dataIndex : 'vehicleLengthName',
			align : 'center',
			text : '停用车型'
		}, {
			dataIndex : 'status',
			align : 'center',
			text : '月台状态',
			renderer : function(value) {
				if (value === '1') {
					return "空闲";
				}

				if (value === '2') {
					return "预分配";
				}

				return value;
			}
		}, {
			dataIndex : 'hasLift',
			align : 'center',
			text : '是否有升降平台',
			renderer : function(value) {
				if (value === 'Y') {
					return "有";
				}
				return '无';
			}
		}];
		me.callParent([cfg]);
	}
});

Ext.define('Foss.scheduling.platformDistribute.distributeForm', {
	extend : 'Ext.form.Panel',
	layout : 'column',
	frame : false,
	columnWidth : .4,
	defaults : {
		margin : '5 5 5 5',
		xtype : 'hiddenfield',
		allowBlank : false
	},
	items : [{
				fieldLabel : '车牌',
				name : 'vehicleNo'
			}, {
				fieldLabel : '车型',
				name : 'vehicleLengthCode'
			}, {
				xtype : 'commonplatformselector',
				fieldLabel : '月台',
				name : 'platform',
				orgCode : scheduling.platformDistribute.parentTfrCtrCode,
				valueField : 'virtualCode',
				columnWidth : .5
			}, {
				xtype : 'container',
				columnWidth : .5,
				html : '&nbsp;'
			}, {
				xtype : 'rangeDateField',
				fieldLabel : '预分配时间',
				columnWidth : .8,
				disallowBlank : true,
				allowBlank : false,
				fieldId : 'Foss_scheduling_platformDistribute_predistributeTime_Id',
				dateType : 'datetimefield_date97',
				fromName : 'useStartTime',
				toName : 'useEndTime'
			}, {
				xtype : 'button',
				text : '确认分配',
				// margin : '5 5 5 5',
				cls : 'yellow_button',
				columnWidth : .2,
				handler : function() {
					var form = this.up('form').getForm();
					if (form.isValid()) {
						var windowMask = new Ext.LoadMask(
								scheduling.platformDistribute.distributeWindow,
								{
									msg : "分配中，请稍后..."
								});
						windowMask.show();
						var form = this.up('form').getForm(),
							values = form.getValues(),
							platformField = form.findField('platform');
						var param = {
							'platformDistributeVo' : {
								'platformPreAssignEntity' : {
									'truckTaskId' : scheduling.platformDistribute.truckTaskId,
									'tfrCtrCode' : scheduling.platformDistribute.parentTfrCtrCode,
									'tfrCtrName' : scheduling.platformDistribute.parentTfrCtrName,
									'platformCode' : platformField.store.findRecord('virtualCode', values.platform, false, true, true)
										.get('platformCode'),
									'vehicleNo' : values.vehicleNo,
									'useStartTime' : values.useStartTime,
									'useEndTime' : values.useEndTime,
									'vehicleModel' : values.vehicleLengthCode,
									'platformVirtualCode' : values.platform
								}
							}
						};

						Ext.Ajax.request({
									url : scheduling
											.realPath('predistribute.action'),
									jsonData : param,
									success : function(response) {
										Ext.ux.Toast.msg('提示', '保存成功！', 'info',
												1500);
										windowMask.hide();
										scheduling.platformDistribute.distributeWindow.close();
									},
									exception : function(response) {
										var result = Ext
												.decode(response.responseText);
										windowMask.hide();
										Ext.ux.Toast.msg('提示', '分配中失败，'
														+ result.message,
												'error', 2000);
									}
								});

					}
				}
			}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

Ext.define('Foss.scheduling.platformDistribute.distributeWindow', {
	extend : 'Ext.window.Window',
	title : '预分配月台',
	width : 800,
	height : 540,
	modal : true,
	closeAction : 'hide',
	vehicleInfoForm : null,
	getVehicleInfoForm : function() {
		if (this.vehicleInfoForm == null) {
			this.vehicleInfoForm = Ext
					.create('Foss.scheduling.platformDistribute.vehicleInfoForm');
		}
		return this.vehicleInfoForm;
	},
	acquireForm : null,
	getAcquireForm : function() {
		if (this.acquireForm == null) {
			this.acquireForm = Ext
					.create('Foss.scheduling.platformDistribute.acquireForm');
		}
		return this.acquireForm;
	},
	platformGrid : null,
	getPlatformGrid : function() {
		if (this.platformGrid == null) {
			this.platformGrid = Ext
					.create('Foss.scheduling.platformDistribute.platformGrid');
		}
		return this.platformGrid;
	},
	distributeForm : null,
	getDistributeForm : function() {
		if (this.distributeForm == null) {
			this.distributeForm = Ext
					.create('Foss.scheduling.platformDistribute.distributeForm');
		}
		return this.distributeForm;
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.items = [{
					border : false,
					columnWidth : 1,
					items : [me.getVehicleInfoForm()]
				}, {
					border : false,
					columnWidth : 1,
					items : [me.getAcquireForm()]
				}, {
					border : false,
					columnWidth : 1,
					items : [me.getPlatformGrid()]
				}, {
					border : false,
					columnWidth : 1,
					items : [me.getDistributeForm()]
				}]
		me.callParent([cfg]);
	},
	listeners : {
		'beforehide' : function(cmp, eOpts){
			var platformGrid = cmp.getPlatformGrid();
			platformGrid.store.removeAll();
		}
	}
})

Ext.onReady(function() {

	scheduling.platformDistribute.queryForm = Ext
			.create('Foss.scheduling.platformDistribute.queryForm');
	scheduling.platformDistribute.companyOnTheWayGrid = Ext
			.create('Foss.scheduling.platformDistribute.companyOnTheWayGrid');
	scheduling.platformDistribute.leasedOnTheWayGrid = Ext
			.create('Foss.scheduling.platformDistribute.leasedOnTheWayGrid');
	scheduling.platformDistribute.arrivedGrid = Ext
			.create('Foss.scheduling.platformDistribute.arrivedGrid');

	Ext.create('Ext.panel.Panel', {
		id : 'T_scheduling-platformDistributeIndex_content',
		cls : "panelContentNToolbar",
		bodyCls : 'panelContent-body',
		layout : 'auto',
		distributeWindow : null,
		getDistributeWindow : function(record) {
			var me = this;
			if (Ext.isEmpty(me.distributeWindow)) {
				me.distributeWindow = Ext
						.create('Foss.scheduling.platformDistribute.distributeWindow');
			}
			scheduling.platformDistribute.distributeWindow = me.distributeWindow;

			var vehicleInfoForm = scheduling.platformDistribute.distributeWindow
					.getVehicleInfoForm().getForm();
			var acquireForm = scheduling.platformDistribute.distributeWindow
					.getAcquireForm().getForm();
			var distributeForm = scheduling.platformDistribute.distributeWindow
					.getDistributeForm().getForm();
			if (!Ext.isEmpty(record)) {
				vehicleInfoForm.loadRecord(record);
				acquireForm.loadRecord(record);
				distributeForm.loadRecord(record);

				var useStartTime = record.get('estimatedArriveTime')
						|| new Date(), useEndTime = record
						.get('estimatedDepartTime');
				
				if(!Ext.isEmpty(useStartTime)){
					distributeForm.findField('useStartTime').setValue(Ext.Date
							.format(new Date(useStartTime.getFullYear(),
									useStartTime.getMonth(), useStartTime
									.getDate(), useStartTime
									.getHours(), useStartTime
									.getMinutes(), useStartTime
									.getSeconds()), 'Y-m-d H:i:s'));
				}
				if(!Ext.isEmpty(useEndTime)){
					distributeForm.findField('useEndTime').setValue(Ext.Date
							.format(new Date(useEndTime.getFullYear(), useEndTime
									.getMonth(), useEndTime
									.getDate(), useEndTime
									.getHours(), useEndTime
									.getMinutes(), useEndTime
									.getSeconds()), 'Y-m-d H:i:s'));
				}
			}
			return scheduling.platformDistribute.distributeWindow;
		},
		items : [scheduling.platformDistribute.queryForm,
				scheduling.platformDistribute.companyOnTheWayGrid,
				scheduling.platformDistribute.leasedOnTheWayGrid,
				scheduling.platformDistribute.arrivedGrid],
		renderTo : 'T_scheduling-platformDistributeIndex-body'
	});
});