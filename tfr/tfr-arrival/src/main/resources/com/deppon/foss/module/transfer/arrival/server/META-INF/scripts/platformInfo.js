/**
 * 分配月台界面
 */
Ext.define('Foss.arrival.ProductType', {
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
							code : 'NORMAL',
							name : '普车'
						}, {
							code : 'FAST',
							name : '卡车'
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
// 车辆申请记录的模版
Ext.define('Foss.arrival.PlatformModel', {
			extend : 'Ext.data.Model',
			idProperty : 'id',
			fields : [{
						name : 'platformNo',
						type : 'string'
					}, {
						name : 'truckArriveId',
						type : 'string'
					}, {
						name : 'truckTaskDetailId',
						type : 'string'
					}, {
						name : 'id',
						type : 'string'
					}, {
						name : 'vehicleType',
						type : 'string'
					}, {
						name : 'platformStatus',
						type : 'string'
					}, {
						name : 'effectiveTime',
						type : 'string'
					}, {
						name : 'hasLift',
						type : 'string'
					}, {
						name : 'virtualCode',
						type : 'string'
					}]
		});

Ext.define('Foss.arrival.platform.VehicleInfoModel', {
			extend : 'Ext.data.Model',
			fields : [{
						name : 'weight',
						type : 'string'
					}, {
						name : 'vehicleLengthName',
						type : 'string'
					}, {
						name : 'truckTaskDetailId',
						type : 'string'
					}, {
						name : 'lineName',
						type : 'string'
					}, {
						name : 'virtualCode',
						type : 'string'
					},{
					    name : 'platformCode',
					    type :'string'
					}, {
						name : 'unloadTime',
						type : 'string'
					}, {
						name : 'productType',
						type : 'string'
					}, {
						name : 'planArrivalTime',
						convert : function(value) {
							if (value != null && value != '') {
								var date = new Date(value);
								return Ext.Date.format(date, 'Y-m-d H:i:s');
							} else {
								return null;
							}
						}
					}, {
						name : 'platformUserStartTime',
						convert : function(value) {
							if (value != null && value != '') {
								var date = new Date(value);
								return Ext.Date.format(date, 'Y-m-d H:i:s');
							} else {
								return null;
							}
						}
					}, {
						name : 'platformUserEndTime',
						convert : function(value) {
							if (value != null && value != '') {
								var date = new Date(value);
								return Ext.Date.format(date, 'Y-m-d H:i:s');
							} else {
								return null;
							}
						}
					}]
		});
Ext.define('Foss.arrival.PlatformStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.arrival.PlatformModel',
	pageSize : 10,
	autoLoad : true,
	proxy : {
		// 以JSON的方式加载数据
		type : 'ajax',
		actionMethods : 'POST',
		url : arrival.realPath('queryPlatformGrid.action'),
		reader : {
			type : 'json',
			root : 'arrivalVO.platformList',
			totalProperty : 'totalCount',
			successProperty : 'success'
		}
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([cfg]);
		var setValue = function(id, value) {
			Ext.getCmp(id).setValue(value);
		};
	},
	listeners : {
		beforeload : function(store, operation, eOpts) {
			var assignPlatformWindow = Ext.getCmp('T_arrival-index_content')
					.getAssignPlatformWindow();
			var queryParams = assignPlatformWindow.getPlatform().getValues();
			Ext.apply(operation, {
				params : {
					'arrivalVO.queryPlatformDTO.vehicleNo' : queryParams.vehicleNo,
					'arrivalVO.queryPlatformDTO.onlyNullPlatform' : queryParams.onlyNullPlatform
				}
			});
		}
	}
});

Ext.define('Foss.arrival.PlatformForm', {
	extend : 'Ext.form.Panel',
	layout : 'column',
	frame : false,
	columnWidth : .4,
	title : arrival
			.i18n('tfr.arrival.ArrivalGrid.grid.vehicleforplatform.button'),// '查询最优月台条件',
	defaults : {
		margin : '5 5 5 5',
		allowBlank : true,
		xtype : 'displayfield'
	},
	items : [{
				xtype : 'hiddenfield',
				name : 'truckTaskDetailId'
			}, {
				xtype : 'hiddenfield',
				name : 'id'
			}, {
				xtype : 'hiddenfield',
				name : 'truckArriveId'
			}, {
				xtype : 'textfield',
				name : 'vehicleNo',
				readOnly : true,
				fieldLabel : arrival
						.i18n('tfr.arrival.ArrivalGrid.grid.vehicleNo.label'),// '交接编号/车牌号',
				columnWidth : .5,
				labelWidth : 120
			}, {
				xtype : 'container',
				columnWidth : .1,
				html : '&nbsp;'
			}, {
				xtype : 'checkboxgroup', // 复选框组
				vertical : true, // 按照columns中指定的列数来分配复选框组件
				// 验证表单输入不能为空，属性值：true表示可以为空，false表示不可以为空
				allowBlank : true,
				columnWidth : .2,
				items : [{
					boxLabel : arrival
							.i18n('tfr.arrival.PlatformForm.form.onlyNullPlatform.label'),// '只显示空月台',
					name : 'onlyNullPlatform', // 表单的参数名
					inputValue : '1', // 表单的参数值
					checked : true
				}]
			}, {
				xtype : 'button',
				text : arrival
						.i18n('tfr.arrival.PlatformForm.form.estimateDepartTime.button'),// '获取月台',
				cls : 'yellow_button',
				columnWidth : .2,
				plugins : Ext.create('Deppon.ux.ButtonLimitingPlugin', {
							seconds : 5
						}),
				gridContainer : this,
				handler : function() {
					// arrival.platformBar.moveFirst();
					var assignPlatformWindow = Ext
							.getCmp('T_arrival-index_content')
							.getAssignPlatformWindow();
					var queryParams = assignPlatformWindow.getPlatform()
							.getValues();
					var params = {
						'arrivalVO.queryPlatformDTO.vehicleNo' : queryParams.vehicleNo,
						'arrivalVO.queryPlatformDTO.truckTaskDetailId' : queryParams.id,
						'arrivalVO.queryPlatformDTO.onlyNullPlatform' : queryParams.onlyNullPlatform
					};
					Ext.Ajax.request({
						url : arrival.realPath('queryPlatformGrid.action'),
						params : params,
						success : function(response) {
							var result = Ext.decode(response.responseText);
							var assignPlatformWindow = Ext
									.getCmp('T_arrival-index_content')
									.getAssignPlatformWindow();
							assignPlatformWindow.getPlatformGrid().store.model = 'Foss.arrival.PlatformModel';
							assignPlatformWindow.getPlatformGrid().store.remoteSort = 'false';
							assignPlatformWindow.getPlatformGrid().store
									.loadData(result.arrivalVO.platformList);
						},
						exception : function(response) {
							var result = Ext.decode(response.responseText);
							Ext.MessageBox
									.alert(
											arrival
													.i18n('tfr.arrival.DepartGrid.tips.label'),
											result.message);
						}
					});

				}
			}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

/**
 * 车辆信息
 */
Ext.define('Foss.arrival.VehicleInfo', {
			extend : 'Ext.form.Panel',
			layout : 'column',
			frame : false,
			title : arrival.i18n('tfr.arrival.VehicleInfo.form.title'),// '车辆信息显示',
			columnWidth : .4,
			defaults : {
				margin : '5 5 5 5',
				allowBlank : true,
				xtype : 'displayfield'
			},
			items : [{
				name : 'lineName',
				columnWidth : .5,
				fieldLabel : arrival
						.i18n('tfr.arrival.VehicleInfo.form.lineName.label'),// '线路',
				labelWidth : 140
			}, {
				name : 'weight',
				columnWidth : .5,
				fieldLabel : arrival
						.i18n('tfr.arrival.VehicleInfo.form.weight.label')
					// '载重体积'
			}, {
				name : 'vehicleLengthName',
				columnWidth : .5,
				fieldLabel : arrival
						.i18n('tfr.arrival.VehicleInfo.form.vehicleLengthName.label'),// '车型',
				labelWidth : 140
			}, {
				name : 'productType',
				columnWidth : .5,
				xtype : 'combo',
				readOnly : true,
				displayField : 'name',
				valueField : 'code',
				fieldLabel : arrival
						.i18n('tfr.arrival.VehicleInfo.form.productType.label'),// '运输性质'
				store : Ext.create('Foss.arrival.ProductType')
			}, {
				name : 'unloadTime',
				columnWidth : .5,
				fieldLabel : arrival
						.i18n('tfr.arrival.VehicleInfo.form.unloadTime.label'),// '预计卸车时长（小时）',
				labelWidth : 140
			}, {
				name : 'planArrivalTime',
				columnWidth : .5,
				fieldLabel : arrival
						.i18n('tfr.arrival.VehicleInfo.form.planArrivalTime.label')
					// '预计到达时间'
			}],
			constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				me.callParent([cfg]);
			}
		});

Ext.define('Foss.arrival.PlatformGrid', {
	extend : 'Ext.grid.Panel',
	frame : true,
	columnWidth : 1,
	height : 200,
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	sortableColumns : false,
	title : arrival.i18n('tfr.arrival.PlatformGrid.grid.title'),// '系统建议月台',
	enableColumnHide : false,
	enableColumnMove : false,
	autoScroll : true,
	columns : [{
				text : arrival
						.i18n('tfr.arrival.PlatformGrid.grid.platformNo.label'),// '月台号',
				dataIndex : 'platformNo',
				columnWidth : .15,
				flex : 1
			}, {
				text : arrival
						.i18n('tfr.arrival.PlatformGrid.grid.virtualCode.label'),// '虚拟编码（隐藏）',
				dataIndex : 'virtualCode',
				hidden : true,
				columnWidth : 0,
				flex : 1
			}, {
				text : arrival
						.i18n('tfr.arrival.PlatformGrid.grid.vehicleType.label'),// '停用车型',
				dataIndex : 'vehicleType',
				columnWidth : .2,
				flex : 1
			}, {
				text : arrival
						.i18n('tfr.arrival.PlatformGrid.grid.platformStatus.label'),// '月台状态',
				dataIndex : 'platformStatus',
				columnWidth : .15,
				flex : 1,
				renderer : function(value) {
					if (value == 'Y') {
						return "可用";
					} else {
						return '不可用';
					}
				}
			}, {
				text : arrival
						.i18n('tfr.arrival.PlatformGrid.grid.effectiveTime.label'),// '可用时间',
				dataIndex : 'effectiveTime',
				columnWidth : .3,
				flex : 1
			}, {
				text : arrival
						.i18n('tfr.arrival.PlatformGrid.grid.hasLift.label'),// '是否有升降平台',
				columnWidth : .2,
				dataIndex : 'hasLift',
				flex : 1,
				renderer : function(value) {
					if (value == 'Y') {
						return "有";
					} else {
						return '无';
					}
				}
			}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});
Ext.define('Foss.arrival.OperateForm', {
	extend : 'Ext.form.Panel',
	layout : 'column',
	frame : false,
	columnWidth : .4,
	defaults : {
		margin : '5 5 5 5',
		allowBlank : true,
		xtype : 'displayfield'
	},
	items : [{
				xtype : 'hiddenfield',
				name : 'virtualCode'
			},{
				xtype : 'commonplatformselector',
				fieldLabel : '月台',// '月台',
				name : 'platformCode',
				orgCode: FossUserContext.getCurrentDept().code,
				columnWidth : .5,
				listeners : {
					'select' : function(field, records, eOpts) {
						 var record = records[0];
						 this.up('form').getForm().findField('virtualCode').setValue(record.get('virtualCode'));
					}
				}
				}, {
					xtype : 'container',
					columnWidth : .5,
					html : '&nbsp;'
				}, {
				xtype : 'rangeDateField',
				fieldLabel : arrival
						.i18n('tfr.arrival.OperateForm.form.platformUserStartTime.label'),// '预分配时间',
				columnWidth : .8,
				disallowBlank : true,
				allowBlank : false,
				fieldId : 'Foss_arrival_OperateForm_createTime_Id',
				dateType : 'datetimefield_date97',
				fromName : 'platformUserStartTime',
				fromValue : Ext.Date.format(
						new Date(new Date().getFullYear(), new Date()
										.getMonth(), new Date().getDate(),
								'00', '00'), 'Y-m-d H:i:s'),
				toName : 'platformUserEndTime',
				toValue : Ext.Date.format(
						new Date(new Date().getFullYear(), new Date()
										.getMonth(), new Date().getDate(),
								'23', '59'), 'Y-m-d H:i:s')
			}, {
				xtype : 'button',
				text : arrival
						.i18n('tfr.arrival.OperateForm.form.trackingTime.button'),// '确认分配',
				margin : '5 5 5 5',
				cls : 'yellow_button',
				columnWidth : .2,
				gridContainer : this,
				handler : function() {
					// 取得月台号
					if (!this.up('form').getForm().isValid())
						return false;
					var assignPlatformWindow = Ext
							.getCmp('T_arrival-index_content')
							.getAssignPlatformWindow();
					var rowObjs = assignPlatformWindow.getPlatformGrid()
							.getSelectionModel().getSelection();
					if (rowObjs.length <= 0) {
						if(!this.up('form').getForm().getValues().virtualCode||!this.up('form').getForm().getValues().platformCode)
						{
							Ext.MessageBox
								.alert(arrival.i18n('tfr.arrival.DepartGrid.tips.label'),'请选择一条月台,如果没有查询到最优月台的分配，请手动输入一个可选月台');
								return false;
						}
					}
					var params = {
						'arrivalVO.vehicleInfoDTO.platformUserStartTime' : this
								.up('form').getForm().getValues().platformUserStartTime,
						'arrivalVO.vehicleInfoDTO.platformUserEndTime' : this
								.up('form').getForm().getValues().platformUserEndTime,
						'arrivalVO.vehicleInfoDTO.truckArriveId' : assignPlatformWindow
								.getPlatform().getValues().truckArriveId,
						'arrivalVO.vehicleInfoDTO.vehicleNo' : assignPlatformWindow
								.getPlatform().getValues().vehicleNo,
						'arrivalVO.vehicleInfoDTO.truckTaskDetailId' : assignPlatformWindow
								.getPlatform().getValues().id,
						'arrivalVO.vehicleInfoDTO.platformNo' : rowObjs[0]?rowObjs[0].get('platformNo'):this.up('form').getForm().getValues().platformCode,
						'arrivalVO.vehicleInfoDTO.virtualCode' : rowObjs[0]?rowObjs[0].get('virtualCode'):this.up('form').getForm().getValues().virtualCode
					}
					// 给显示车辆明细信息赋值
					Ext.Ajax.request({
						url : arrival.realPath('confirmPlatform.action'),
						params : params,
						success : function(response) {
							Ext.ux.Toast
									.msg(
											arrival
													.i18n('tfr.arrival.DepartGrid.tips.label'),
											arrival
													.i18n('tfr.arrival.OperateForm.form.ok.tips'),
											'ok', 3000);
							assignPlatformWindow.hide();
							arrival.pagingBar.moveFirst();
						},
						exception : function(response) {
							var result = Ext.decode(response.responseText);
							Ext.MessageBox
									.alert(
											arrival
													.i18n('tfr.arrival.DepartGrid.tips.label'),
											result.message);
						}
					});
				}
			}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});
/** ---------------------------------------------------------------------------------- */
Ext.define('Foss.arrival.AssignPlatformWindow', {
			extend : 'Ext.window.Window',
			title : arrival
					.i18n('tfr.arrival.AssignPlatformWindow.form.currentPlace.label'),// '预分配月台',
			width : 800,
			height : 540,
			// closable : true,
			modal : true,
			closeAction : 'hide',
			// bodyCls : 'autoHeight',
			// cls : 'autoHeight',
			// layout:'column',
			defaults : {
				columns : 2
			},
			platform : null,
			getPlatform : function() {
				if (this.platform == null) {
					this.platform = Ext.create('Foss.arrival.PlatformForm');
				}
				return this.platform;
			},
			vehicleInfo : null,
			getVehicleInfo : function() {
				if (this.vehicleInfo == null) {
					this.vehicleInfo = Ext.create('Foss.arrival.VehicleInfo');
				}
				return this.vehicleInfo;
			},
			platformGrid : null,
			getPlatformGrid : function() {
				if (this.platformGrid == null) {
					this.platformGrid = Ext.create('Foss.arrival.PlatformGrid');
				}
				return this.platformGrid;
			},
			operateForm : null,
			getOperateForm : function() {
				if (this.operateForm == null) {
					this.operateForm = Ext.create('Foss.arrival.OperateForm');
				}
				return this.operateForm;
			},
			constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				me.items = [{
							border : false,
							columnWidth : 1,
							items : [me.getVehicleInfo()]
						}, {
							border : false,
							columnWidth : 1,
							items : [me.getPlatform()]
						}, {
							border : false,
							columnWidth : 1,
							items : [me.getPlatformGrid()]
						}, {
							border : false,
							columnWidth : 1,
							items : [me.getOperateForm()]
						}]
				me.callParent([cfg]);
			}
		})