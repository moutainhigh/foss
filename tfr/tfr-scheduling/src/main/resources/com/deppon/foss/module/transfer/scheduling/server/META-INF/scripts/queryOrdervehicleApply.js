/**
 * 查询约车申请界面，中转流程，集中营业部转货约车的流程不在此处处理
 * 
 */
// 查询条件区域
Ext.define('Foss.scheduling.ordervehicle.OrderVehicleApplyQueryForm', {
	extend : 'Ext.form.Panel',
	title : scheduling.orderVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyQueryForm.title'), // 查询条件
	frame : true,
	collapsible : true,
	animCollapse : true,
	defaultType : 'textfield',
	layout : 'column',
	defaults : {
		labelWidth : 85,
		margin : '5 10 5 10'
	},
	items : [{
		name : 'orderType',
		fieldLabel : scheduling.orderVehicle.i18n('Foss.scheduling.ordervehicle.OrderVehicleApplyQueryForm.orderType'), // 预约类型
		xtype : 'combobox',
		store : FossDataDictionary
				.getDataDictionaryStore('ORDERVEHICLE_ORDERTYPE'),
		queryMode : 'local',
		displayField : 'valueName',
		valueField : 'valueCode',
		editable : false,
		forceSelection : true,
		triggerAction : 'all',
		columnWidth : .25
	}, {
		name : 'status',
		fieldLabel : scheduling.orderVehicle
				.i18n('Foss.scheduling.ordervehicle.OrderVehicleApplyQueryForm.status'), // 操作状态
		xtype : 'combobox',
		store : FossDataDictionary
				.getDataDictionaryStore('ORDERVEHICLE_STATUS'),
		queryMode : 'local',
		displayField : 'valueName',
		valueField : 'valueCode',
		forceSelection : true,
		triggerAction : 'all',
		columnWidth : .25
	}, {
		xtype : 'rangeDateField',
		dateType : 'datetimefield_date97',
		fieldLabel : scheduling.orderVehicle
				.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyQueryForm.predictUseTime'), // 用车时间
		fieldId : 'Foss_ordervehicle_OrderVehicleApplyQueryForm_PredictUseTime_ID',
		fromName : 'beginPredictUseTime',
		toName : 'endPredictUseTime',
		// 设置开始时间默认值
		fromValue : Ext.Date.format(
				new Date(new Date().getFullYear(), new Date().getMonth(),
						new Date().getDate(), 00, 00, 00), 'Y-m-d H:i:s'),
		// 设置结束时间默认值
		toValue : Ext.Date.format(new Date(new Date().getFullYear(), new Date()
								.getMonth(), new Date().getDate(), 23, 59, 59),
				'Y-m-d H:i:s'),
		columnWidth : .5
	}, {
		name : 'useVehicleOrgCode', // 用车部门
		xtype : 'dynamicorgcombselector',
		salesDepartment : 'Y',// 查询营业部 配置此值
		transferCenter : 'Y',// 查询外场 配置此值
		doAirDispatch : 'Y',// 查询空运配载 配置此值
		transDepartment : 'Y',// 查询车队 配置此值
		dispatchTeam : 'Y',// 查询车队调度组 配置此值
		transTeam : 'Y',// 查询车队组 配置此值
		active : 'Y',
		type : 'ORG',
		fieldLabel : scheduling.orderVehicle
				.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyQueryForm.applyOrgCode'), // 用车部门
		columnWidth : .25
	}, {
		name : 'orderNo',
		fieldLabel : scheduling.orderVehicle
				.i18n('Foss.scheduling.ordervehicle.OrderVehicleApplyQueryForm.orderNo'), // 约车单号
		columnWidth : .25
	}, {
		xtype : 'rangeDateField',
		dateType : 'datetimefield_date97',
		fieldId : 'Foss_ordervehicle_OrderVehicleApplyQueryForm_ApplyTime_ID',
		fieldLabel : scheduling.orderVehicle
				.i18n('Foss.scheduling.inviteVehicle.InviteVehicleDetailWindow.inviteVehiclepplyInfo.applyTime'), // 申请时间
		fromName : 'beginApplyTime',
		toName : 'endApplyTime',
		editable : false,
		columnWidth : .5
	}, {
		border : 1,
		xtype : 'container',
		columnWidth : 1,
		defaultType : 'button',
		layout : 'column',
		items : [{
			text : scheduling.orderVehicle.i18n('Foss.scheduling.button.reset'), // 重置
			columnWidth : .08,
			handler : function() {
				scheduling.orderVehicle.ordertruckApplyQueryForm.getForm()
						.reset();
				this.ownerCt.ownerCt.resetQueryCondition();
			}
		}, {
			xtype : 'container',
			columnWidth : .84,
			html : '&nbsp;'
		}, {
			text : scheduling.orderVehicle.i18n('Foss.scheduling.button.search'), // 查询
			disabled : !scheduling.orderVehicle.isPermission('scheduling/queryOrderVehicleApplyButton'),
			hidden : !scheduling.orderVehicle.isPermission('scheduling/queryOrderVehicleApplyButton'),
			cls : 'yellow_button',
			columnWidth : .08,
			handler : function() {
				var form = scheduling.orderVehicle.ordertruckApplyQueryForm
						.getForm();
				if (!form.isValid()) {
					Ext.Msg
							.alert(
									scheduling.orderVehicle
											.i18n('Foss.scheduling.validation.alert.title'),
									scheduling.orderVehicle
											.i18n('Foss.scheduling.validation.tip.search')); // 请输入合法的查询条件!
					return false;
				}
				scheduling.orderVehicle.pagingBar.moveFirst();
				scheduling.orderVehicle.ordertruckApplyQueryGrid.show();
			}
		}]
	}],
	resetQueryCondition : function() {
		var form = this.getForm();
		var findField = function(name) {
			return form.findField(name);
		}

		findField('beginPredictUseTime').setValue(Ext.Date.format(new Date(
						new Date().getFullYear(), new Date().getMonth(),
						new Date().getDate(), 00, 00, 00), 'Y-m-d H:i:s'));
		findField('endPredictUseTime').setValue(Ext.Date.format(new Date(
						new Date().getFullYear(), new Date().getMonth(),
						new Date().getDate(), 23, 59, 59), 'Y-m-d H:i:s'));
	}
});

// combobox 绑定的值 转换为map， map的key位combobox value , value位text
scheduling.orderVehicle.getComboboxValueMap = function(from, name) {
	var fields = from.getForm().getFields();
	var map = new Ext.util.HashMap();
	if (!fields)
		return map;
	var items = fields.items;
	if (!items)
		return map;
	for (var i = 0; i < items.length; i++) {
		var item = items[i];
		// xtype为 combobox
		if ("combobox" != item.xtype)
			continue;
		if (item.name != name)
			continue;
		var dispalyKey = item.displayField;
		if (!dispalyKey)
			return map;
		var valueKey = item.valueField;
		if (!valueKey)
			return map;
		var store = item.getStore();
		if (!store)
			return map;
		store.each(function(record) {
					map.add(record.get(valueKey), record.get(dispalyKey))
				})
		return map;
	}
	return map;
}

// model
Ext.define('Foss.scheduling.ordervehicle.OrderVehicleApplyQueryModel', {
	extend : 'Ext.data.Model',
	fields : [{
				// 主键id
				name : 'id',
				type : 'string'
			}, {
				// 约车编号
				name : 'orderNo',
				type : 'string'
			}, {
				// 预约类型
				name : 'orderType',
				type : 'string',
				convert : function(value) {
					return scheduling.orderVehicle.orderTypeValueMap.get(value);
				}
			}, {
				// 车型
				name : 'orderVehicleModel',
				type : 'string'
			}, {
				// 状态
				name : 'status',
				type : 'string',
				convert : function(value) {
					return scheduling.orderVehicle.statusValueMap.get(value);
				}
			}, {
				// 用车部门
				name : 'useVehicleOrgName',
				type : 'string'
			}, {
				// 预计用车时间
				name : 'predictUseTime',
				type : 'date',
				convert : dateConvert
			}, {
				// 申请时间
				name : 'applyTime',
				type : 'date',
				convert : dateConvert
			}, {
				name : 'isGroupZone',
				type : 'string'
			}, {
				name : 'dispatchTransDept',
				type : 'string'
			}, {
				name : 'applyOrgName',
				type : 'string'
			}, {
				name : 'applyOrgCode',
				type : 'string'
			}, {
				name : 'topFleetCode',
				type : 'string'
			}, {
				//备注
				name : 'notes',
				type : 'string'
			}, {
				//确认到达时间
				name : 'arrivalTime',
				type : 'date',
				convert : dateConvert
			}, {
				//受理时间
				name : 'passTime',
				type : 'date',
				convert : dateConvert
			}]

});

// store
Ext.define('Foss.scheduling.ordervehicle.OrderVehicleApplyQueryStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.scheduling.ordervehicle.OrderVehicleApplyQueryModel',
	proxy : {
		// 以JSON的方式加载数据
		type : 'ajax',
		actionMethods : 'POST',
		url : scheduling.realPath('queryOrderVehicleApply.action'),
		reader : {
			type : 'json',
			root : 'orderVehicleVo.orderVehicleList',
			totalProperty : 'totalCount',
			successProperty : 'success'
		}
	},
	listeners : {
		beforeload : function(store, operation) {
			var ordertruckApplyQueryForm = scheduling.orderVehicle.ordertruckApplyQueryForm
					.getValues();
			Ext.apply(operation, {
				params : {
					// 预约类型
					'orderVehicleVo.orderVehicleDto.orderVehicleEntity.orderType' : ordertruckApplyQueryForm.orderType,
					// 操作状态
					'orderVehicleVo.orderVehicleDto.orderVehicleEntity.status' : ordertruckApplyQueryForm.status,
					// 用车开始时间
					'orderVehicleVo.orderVehicleDto.beginPredictUseTime' : Ext.Date
							.parse(
									ordertruckApplyQueryForm.beginPredictUseTime,
									'Y-m-d H:i:s'),
					// 用车结束时间
					'orderVehicleVo.orderVehicleDto.endPredictUseTime' : Ext.Date
							.parse(ordertruckApplyQueryForm.endPredictUseTime,
									'Y-m-d H:i:s'),
					// 用车部门名称
					'orderVehicleVo.orderVehicleDto.orderVehicleEntity.useVehicleOrgCode' : ordertruckApplyQueryForm.useVehicleOrgCode,
					// 约车编号
					'orderVehicleVo.orderVehicleDto.orderVehicleEntity.orderNo' : ordertruckApplyQueryForm.orderNo,
					// 申请开始时间
					'orderVehicleVo.orderVehicleDto.beginApplyTime' : ordertruckApplyQueryForm.beginApplyTime,
					// 申请结束时间
					'orderVehicleVo.orderVehicleDto.endApplyTime' : ordertruckApplyQueryForm.endApplyTime

				}
			});
		}
	}
});

// 查询结果grid
Ext.define('Foss.scheduling.ordervehicle.OrderVehicleApplyQueryGrid', {
	extend : 'Ext.grid.Panel',
	frame : true,
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	emptyText : scheduling.orderVehicle
			.i18n('Foss.scheduling.operation.tip.resultNull'), // 查询结果为空
	title : scheduling.orderVehicle
			.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyQueryGrid.title'),
	// 提交请求
	ajaxRequest : function(url, submitBeforeMsg) {
		// 提交前confirm message
		if (!submitBeforeMsg)
			submitBeforeMsg = scheduling.orderVehicle
					.i18n('Foss.scheduling.ordervehicle.OrderVehicleApplyQueryGrid.confirm.message');
		var grid = this;
		Ext.MessageBox.confirm(scheduling.orderVehicle.i18n('Foss.scheduling.validation.alert.title'),
				submitBeforeMsg, function(button) {
					// 确定按钮
					if (button == 'yes') {
						// grid 选中的行
						var dataList = grid.getSelectionModel().getSelection();
						var idList = new Array();
						// 处理id
						Ext.each(dataList, function(object) {
									idList.push(object.get('id'));
								})
						Ext.Ajax.request({
							url : scheduling.realPath(url),
							params : {
								'orderVehicleVo.orderVehicleApplyIdList' : idList
							},
							success : function(response) {
								Ext.ux.Toast.msg(scheduling.orderVehicle.i18n('Foss.scheduling.leadTruck.QueryForm.tips'), scheduling.orderVehicle.i18n('foss.scheduling.borrowvehicle.msg.operatorSuccess'));
								scheduling.orderVehicle.pagingBar.moveFirst();
							},
							// 异常message
							exception : function(response) {
								var result = Ext.decode(response.responseText);
								Ext.MessageBox.alert(scheduling.orderVehicle.i18n('Foss.scheduling.leadTruck.QueryForm.tips'), result.message);
							}
						});
					}
				});
	},
	// 申请 撤销 确认到达前 检查状态
	checkStatus : function(map, errorMessage, noSelectMsg, groupZoneMsg) {
		// 状态错误mesage
		if (!errorMessage)
			errorMessage = scheduling.orderVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyQueryGrid.errorMessage');//'状态错误!';
		// 没有选中行时的message
		if (!noSelectMsg)
			noSelectMsg = scheduling.orderVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyQueryGrid.noSelectMsg');//'请选择您要操作的数据行';
		// 集中转货申请 不能操作message
		if (!groupZoneMsg)
			groupZoneMsg = scheduling.orderVehicle.i18n('foss.scheduling.orderVehicle.groupZoneMsg');//'集中区域转货申请不能在此操作';
		var grid = this;
		if (!grid.getSelectionModel().hasSelection()) {
			Ext.MessageBox.alert(scheduling.orderVehicle.i18n('Foss.scheduling.leadTruck.QueryForm.tips'), noSelectMsg);
			return false;
		}
		var flag = true;
		if (grid.getSelectionModel().hasSelection()) {
			// grid 选中行list
			var dataList = grid.getSelectionModel().getSelection();
			Ext.each(dataList, function(object) {
						var isGroupZone = object.data.isGroupZone;
						var orderNo = object.data.orderNo;
						/*
						 * if(isGroupZone == 'Y') { groupZoneMsg = groupZoneMsg + ',
						 * 约车单号:' + orderNo; Ext.MessageBox.alert(scheduling.orderVehicle.i18n('Foss.scheduling.leadTruck.QueryForm.tips'),
						 * groupZoneMsg); flag = false; return false; }
						 */
						var objStatus = object.raw.status;
						// 如果map中不包含 当前行的状态 ， 认为状态错误.
						if (!map.containsKey(objStatus)) {
							errorMessage = errorMessage + ', '+scheduling.orderVehicle.i18n('Foss.scheduling.ordervehicle.OrderVehicleApplyQueryForm.orderNo')+':' + orderNo;
							Ext.MessageBox.alert(scheduling.orderVehicle.i18n('Foss.scheduling.leadTruck.QueryForm.tips'), errorMessage);
							flag = false;
							return false;
						}
					})
		}
		return flag;
	},
	dockedItems : [{
		xtype : 'toolbar',
		dock : 'top',
		layout : 'column',
		defaultType : 'button',
		items : [{
					xtype : 'container',
					html : '&nbsp;',
					columnWidth : .5
				}, {
					text : scheduling.orderVehicle.i18n('foss.scheduling.borrowvehicle.button.apply'),//'申请',
					columnWidth : .08,
					id : 'Foss.scheduling.ordervehicle.OrderVehicleApplyQueryGrid.toolbar.button.apply.id',
					handler : function() {
						var grid = this.ownerCt.ownerCt;
						var orderVehicleApplyId = null;
						// 选中的行
						if (grid.getSelectionModel().hasSelection()) {
							var dataList = grid.getSelectionModel()
									.getSelection();
							if (dataList.length > 1) {
								Ext.MessageBox.alert(scheduling.orderVehicle.i18n('Foss.scheduling.leadTruck.QueryForm.tips'), scheduling.orderVehicle.i18n('foss.scheduling.borrowvehicle.msg.cannotOperatorAtOneTime'));//'该动作不能支持多条目同时操作!');
								return false;
							}
							var map = new Ext.util.HashMap();
							// 暂存
							map.add('STAGING', 'STAGING');
							// 未审核
							map.add('UNAPPROVED', 'UNAPPROVED');
							// 退回
							map.add('RETURN', 'RETURN');
							// 撤销
							map.add('UNDO', 'UNDO');
							var status = dataList[0].raw.status;
							// 只有 暂存，未审核, 退回状态的才能做修改操作
							var message = scheduling.orderVehicle.i18n('foss.scheduling.borrowvehicle.msg.thisStatusCannotEdit');//"状态不支持编辑!";
							if (!grid.checkStatus(map, message)) {
								return false;
							}
							if (dataList[0].get('applyOrgCode') != FossUserContext
									.getCurrentDept().code) {
								Ext.MessageBox.alert(scheduling.orderVehicle.i18n('Foss.scheduling.leadTruck.QueryForm.tips'), scheduling.orderVehicle.i18n('foss.scheduling.inviteVehicle.applyOrgCode.not.m'));//'申请部门不为本部门,不能修改!');
								return false;
							}
							// 当前选中行的主键id
							orderVehicleApplyId = dataList[0].get('id');
						}
						// 约车申请window
						if (!scheduling.orderVehicle.ordertruckApplyWindow) {
							scheduling.orderVehicle.ordertruckApplyWindow = Ext
									.create('Foss.scheduling.ordervehicle.OrderVehicleApplyWindow');
						}
						// 选中行的id
						scheduling.orderVehicle.ordertruckApplyWindow.orderVehicleApplyId = orderVehicleApplyId;
						scheduling.orderVehicle.ordertruckApplyWindow.show();
						// 如果是修改操作 load数据
						scheduling.orderVehicle.ordertruckApplyWindow
								.initData();

						//隐藏暂存按钮(修改的时候要要隐藏暂存按钮)
						if(orderVehicleApplyId != null && 'STAGING' != status)
						{
							//不可用
							scheduling.orderVehicle.ordertruckApplyWindow.buttonArea.items.items[2].setDisabled(true);
						}else{
							//可用
							scheduling.orderVehicle.ordertruckApplyWindow.buttonArea.items.items[2].setDisabled(false);
						}
						//scheduling.orderVehicle.ordertruckApplyWindow.operateForm.doLayout();
					}
				}, {
					text : scheduling.orderVehicle.i18n('Foss.scheduling.button.cancel'),//'撤销',
					columnWidth : .08,
					id : 'Foss.scheduling.ordervehicle.OrderVehicleApplyQueryGrid.toolbar.button.cancel.id',
					handler : function() {
						var grid = this.ownerCt.ownerCt;
						var message = scheduling.orderVehicle.i18n('foss.scheduling.orderVehicle.message');//'只有暂存,未审核状态的才能撤销!';
						var map = new Ext.util.HashMap();
						// 暂存
						map.add('STAGING', 'STAGING');
						// 未审核
						map.add('UNAPPROVED', 'UNAPPROVED');
						// 只有 暂存，未审核才可以做撤销操作
						if (!grid.checkStatus(map, message)) {
							return false;
						}
						var currentDept = FossUserContext.getCurrentDept();
						var isApplyDept = true;
						// 只有申请部门才可以做撤销动作
						Ext.Array.each(grid.getSelectionModel().getSelection(),
								function(record, i) {
									if (currentDept.code != record.data.applyOrgCode) {
										Ext.MessageBox
												.alert(
														scheduling.orderVehicle.i18n('Foss.scheduling.leadTruck.QueryForm.tips'),
														scheduling.orderVehicle.i18n('foss.scheduling.orderVehicle.currentDept.code')
																+ record.data.applyOrgName);
										isApplyDept = false;
										return false;
									}
								});

						if (!isApplyDept) {
							return;
						}

						grid.ajaxRequest('doUndoOrderVehicleApply.action',
								scheduling.orderVehicle.i18n('foss.scheduling.borrowvehicle.msg.sureUndo'));
					}
				}, {
					text : scheduling.orderVehicle.i18n('foss.scheduling.borrowvehicle.button.approvedAccepted'),//'审核受理',
					columnWidth : .08,
					id : 'Foss.scheduling.ordervehicle.OrderVehicleApplyQueryGrid.toolbar.button.audit.id',
					handler : function() {
						var grid = this.ownerCt.ownerCt;
						var message = scheduling.orderVehicle.i18n('foss.scheduling.orderVehicle.unapproved');//'只有未审核状态的才能进行审核受理操作!';
						var map = new Ext.util.HashMap();
						// 未审核
						map.add('UNAPPROVED', 'UNAPPROVED');
						// 只有未审核,已受理状态的才能进行审核受理操作
						if (!grid.checkStatus(map, message)) {
							return false;
						}
						// grid 选中的行
						var dataList = grid.getSelectionModel().getSelection();
						var motorcadeCode = dataList[0].data.dispatchTransDept;
						// BUG-3924
						if (dataList.length > 1) {
							Ext.MessageBox.alert(scheduling.orderVehicle.i18n('Foss.scheduling.leadTruck.QueryForm.tips'), scheduling.orderVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyQueryGrid.button.apply.message'));//"该动作不能支持多条目同时操作 ");
							return false;
						}
						// 判断当前部门是否为所选约车信息对应的派车顶级车队或者此顶级车队下的调度组
						Ext.Ajax.request({
							actionMethods : 'POST',
							async : true,
							url : scheduling.realPath('checkTopFleet.action'),
							params : {
								'orderVehicleVo.motorcadeCode' : motorcadeCode
							},
							success : function(response) {
								var result = Ext.decode(response.responseText);
								var paramOrderIdList = new Array();
								// 处理id
								Ext.each(dataList, function(object) {
											paramOrderIdList.push(object
													.get('id'));
										})
								// 审核受理tab
								var passOrderVehicleIndexId = 'T_scheduling-passOrderVehicleIndex';
								/*
								 * var mainTabPanel =
								 * Ext.getCmp('mainAreaPanel'); var tab =
								 * mainTabPanel.child('#' +
								 * passOrderVehicleIndexId);
								 * mainTabPanel.remove(tab);
								 */
								removeTab(passOrderVehicleIndexId);
								addTab(
										passOrderVehicleIndexId,
										scheduling.orderVehicle.i18n('foss.scheduling.borrowvehicle.button.approvedAccepted'),
										scheduling
												.realPath('passOrderVehicleIndex.action')
												+ '?paramOrderIdList='
												+ paramOrderIdList
												+ '&isLoadAll=true');
							},
							// 异常message
							exception : function(response) {
								var result = Ext.decode(response.responseText);
								Ext.MessageBox
										.alert(
												scheduling.orderVehicle.i18n('Foss.scheduling.validation.alert.title'),
												result.message);
							}
						});
					}
				}, {
					text : scheduling.orderVehicle.i18n('foss.scheduling.borrowvehicle.button.confirmArrive'),//'确认到达',
					columnWidth : .08,
					id : 'Foss.scheduling.ordervehicle.OrderVehicleApplyQueryGrid.toolbar.button.arrive.id',
					handler : function() {
						var grid = this.ownerCt.ownerCt;
						var message = scheduling.orderVehicle.i18n('foss.scheduling.borrowvehicle.msg.cannotConfirmArrive');//'该条目没有达到已受理状态,无法确认到达!';
						var map = new Ext.util.HashMap();
						// 已受理状态
						map.add('ACCEPTED', 'ACCEPTED');
						if (!grid.checkStatus(map, message)) {
							return false;
						}
						// grid 选中的行
						var dataList = grid.getSelectionModel().getSelection();
						if (dataList[0].get('applyOrgCode') != FossUserContext
								.getCurrentDept().code) {
							Ext.MessageBox.alert(scheduling.orderVehicle.i18n('Foss.scheduling.leadTruck.QueryForm.tips'), scheduling.orderVehicle.i18n('foss.scheduling.orderVehicle.accepted'));//'申请部门不为本部门,不能做到达确认!');
							return false;
						}
						grid.ajaxRequest('doConfirmTo.action');
					}
				}, {
					text : scheduling.orderVehicle.i18n('foss.scheduling.borrowvehicle.button.vehicleGiveBack'),//'车辆归还',
					columnWidth : .08,
					id : 'Foss.scheduling.ordervehicle.OrderVehicleApplyQueryGrid.toolbar.button.return.id',
					handler : function() {
						var grid = this.ownerCt.ownerCt;
						var message = scheduling.orderVehicle.i18n('foss.scheduling.borrowvehicle.msg.cannotGiveBack');//'该条目没有达到确认到达状态,无法车辆归还!';
						var map = new Ext.util.HashMap();
						map.add('CONFIRMTO', 'CONFIRMTO');
						if (!grid.checkStatus(map, message)) {
							return false;
						}
						grid.ajaxRequest('doOrderVehicleGiveBack.action');
					}
				},{
					text : scheduling.orderVehicle.i18n('foss.scheduling.invite.order.Vehicle.sms.title'),//'短信信息',
					columnWidth : .08,
					id : 'Foss.scheduling.ordervehicle.OrderVehicleApplyQueryGrid.toolbar.button.sms.id',
					handler : function() {
						var grid = this.ownerCt.ownerCt;
						var message = scheduling.orderVehicle.i18n('foss.scheduling.invite.order.Vehicle.sms.accepted');///只有已受理的才能生成短信信息!
						var map = new Ext.util.HashMap();
						// 已受理
						map.add('ACCEPTED', 'ACCEPTED');
						// 只有已受理的才会有短信
						if (!grid.checkStatus(map, message)) {
							return false;
						}
						// grid 选中的行
						var dataList = grid.getSelectionModel().getSelection();
						var id = dataList[0].data.id;
						var orderNo = dataList[0].data.orderNo;
						
						var params = {"orderVehicleVo.orderVehicleEntity.id":id};
						
						Ext.Ajax.request({
							url:scheduling.realPath('queryOrderVehicleApplyDetail.action'),
							params:params,
							success:function(response) {
								var result = Ext.decode(response.responseText);
								var orderVehicleVo = result.orderVehicleVo;
								var orderVehicleApplyRecord = Ext.create('Foss.scheduling.ordervehicle.OrderVehicleApplyDetailFormModel', orderVehicleVo.orderVehicleEntity);
								
								var passOrderApplyDto = orderVehicleVo.passOrderApplyDto;
								var auditorderapplyList = passOrderApplyDto.auditOrderApplyDtoList;
								var date = new Date(auditorderapplyList[auditorderapplyList.length-1].perdictArriveTime);						
								var notes = auditorderapplyList[auditorderapplyList.length-1].notes;						
								 var perdictArriveTime = Ext.Date.format(date, 'Y-m-d H:i:s');
								
				                var sms=orderVehicleApplyRecord.get('orderType') + '：' + orderVehicleApplyRecord.get('useVehicleOrgName')
				                + '，'+scheduling.orderVehicle.i18n('foss.scheduling.orderVehicle.GoodsInfo')+'：'
				                + orderVehicleApplyRecord.get('weight') + scheduling.orderVehicle.i18n('foss.scheduling.forecastQuantity.weightUnit') 
				                + '/' + orderVehicleApplyRecord.get('volume') + scheduling.orderVehicle.i18n('foss.scheduling.forecastQuantity.volumeUnit')
				                + '，'+scheduling.orderVehicle.i18n('foss.scheduling.adjustTransportationPath.adjustPath.arriveTime')+'：' 
				                +perdictArriveTime
				                + '，'+scheduling.orderVehicle.i18n('foss.scheduling.passOrderVehicle.orderNo')+'：' 
				                + orderNo;
				                
				                //如果有备注则添加备注
				                if(notes){
				                	sms+='<br/>'+scheduling.orderVehicle.i18n('foss.longscheduledesign.gird.plandetail.truckInfoNotes.lable')+'：'+ notes;
				                }
				                
				                Ext.MessageBox.alert(scheduling.orderVehicle.i18n('foss.scheduling.invite.order.Vehicle.sms.title'),sms);
							},
							exception:function(response) {
								var result = Ext.decode(response.responseText);
								Ext.MessageBox.alert(scheduling.orderVehicle.i18n('Foss.scheduling.leadTruck.QueryForm.tips'), result.message);
							}
						});

						
					}
				}]
	}],
	columns : [{
		header : scheduling.orderVehicle.i18n('Foss.scheduling.ordervehicle.OrderVehicleApplyQueryForm.orderNo'),//'约车单号',
		dataIndex : 'orderNo',
		windowClassName : 'Foss.scheduling.ordervehicle.OrderVehicleDetailWindow',
		xtype : 'openwindowcolumn',
		flex : 1.5
	}, {
		header : scheduling.orderVehicle.i18n('foss.scheduling.orderVehicle.orderType'),//'约车类型',
		dataIndex : 'orderType',
		flex : 1.5
	}, {
		header : scheduling.orderVehicle.i18n('foss.shortScheduleDesign.gird.PlanDetail.truckModelValue.lable'),//'车型',
		dataIndex : 'orderVehicleModel',
		flex : 1.5
	}, {
		header : scheduling.orderVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyQueryForm.status'),//'操作状态',
		dataIndex : 'status',
		flex : 1.5
	}, {
		header : scheduling.orderVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyQueryForm.applyOrgCode'),//'用车部门',
		dataIndex : 'useVehicleOrgName',
		flex : 2
	}, {
		header : scheduling.orderVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyQueryGrid.predictUseTime'),//'用车时间',
		dataIndex : 'predictUseTime',
		xtype : 'datecolumn',
		format : 'Y-m-d H:i:s',
		flex : 1.8
	}, {
		header : scheduling.orderVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleDetailWindow.inviteVehiclepplyInfo.applyTime'),//'申请时间',
		dataIndex : 'applyTime',
		xtype : 'datecolumn',
		format : 'Y-m-d H:i:s',
		flex : 1.7
	}, {
		header : scheduling.orderVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleDetailWindow.inviteVehiclepplyInfo.arrivalTime'),//'确认到达时间',
		dataIndex : 'arrivalTime',
		xtype : 'datecolumn',
		format : 'Y-m-d H:i:s',
		flex : 1.7
	}, {
		header : scheduling.orderVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleDetailWindow.inviteVehiclepplyInfo.passTime'),//'受理时间',
		dataIndex : 'passTime',
		xtype : 'datecolumn',
		format : 'Y-m-d H:i:s',
		flex : 1.7
	}, {
		header : scheduling.orderVehicle.i18n('foss.longscheduledesign.gird.plandetail.truckInfoNotes.lable'),//'备注',
		dataIndex : 'notes',
		flex : 2
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext
				.create('Foss.scheduling.ordervehicle.OrderVehicleApplyQueryStore');
		me.selModel = Ext.create('Ext.selection.CheckboxModel');
		me.bbar = Ext.create('Deppon.StandardPaging', {
			store : me.store,
			plugins: Ext.create('Deppon.ux.PageSizePlugin', { 
            limitWarning: 200    })
		});
		scheduling.orderVehicle.pagingBar = me.bbar;
		me.callParent([cfg]);
	}
});

// 处理日期文本框不可编辑
scheduling.orderVehicle.settingDateFieldReadOnly = function(id) {
	var getObject = function(id) {
		return document.getElementById(id);
	};
	var settingReadOnly = function(id) {
		getObject(id).readOnly = true;
	}
	settingReadOnly(id + '_second-inputEl');
	settingReadOnly(id + '_first-inputEl');
}

Ext.onReady(function() {
	Ext.QuickTips.init();
	// 查询条件
	var ordertruckApplyQueryForm = Ext
			.create('Foss.scheduling.ordervehicle.OrderVehicleApplyQueryForm');
	// grid
	var ordertruckApplyQueryGrid = Ext
			.create('Foss.scheduling.ordervehicle.OrderVehicleApplyQueryGrid');
	// 保存到全局变量中
	scheduling.orderVehicle.ordertruckApplyQueryForm = ordertruckApplyQueryForm;
	scheduling.orderVehicle.ordertruckApplyQueryGrid = ordertruckApplyQueryGrid;
	Ext.create('Ext.panel.Panel', {
				id : 'T_scheduling-orderVehicleIndex_content',
				cls : "panelContentNToolbar",
				bodyCls : 'panelContentNToolbar-body',
				layout : 'auto',
				items : [ordertruckApplyQueryForm, ordertruckApplyQueryGrid],
				renderTo : 'T_scheduling-orderVehicleIndex-body'
			});
	// 处理combox按文本显示
	scheduling.orderVehicle.orderTypeValueMap = scheduling.orderVehicle
			.getComboboxValueMap(
					scheduling.orderVehicle.ordertruckApplyQueryForm,
					'orderType');
	scheduling.orderVehicle.statusValueMap = scheduling.orderVehicle
			.getComboboxValueMap(
					scheduling.orderVehicle.ordertruckApplyQueryForm, 'status');
	scheduling.orderVehicle.ordertruckApplyQueryForm.resetQueryCondition();

	// 权限控制
	var currentDept = FossUserContext.getCurrentDept();
	// 申请按钮 车队、调度、车队组不能进行申请
	if (!scheduling.orderVehicle
					.isPermission('scheduling/newOrderVehicleApplyButton')) {
		Ext
				.getCmp('Foss.scheduling.ordervehicle.OrderVehicleApplyQueryGrid.toolbar.button.apply.id')
				.hide();
	}
	// 撤销按钮 车队、调度、车队组不能进行撤销
	if (!scheduling.orderVehicle
					.isPermission('scheduling/doUndoOrderVehicleApplyButton')) {
		Ext
				.getCmp('Foss.scheduling.ordervehicle.OrderVehicleApplyQueryGrid.toolbar.button.cancel.id')
				.hide();
	}
	// 审批受理
	if (!scheduling.orderVehicle
					.isPermission('scheduling/autitVehicleIndexButton')) {
		Ext
				.getCmp('Foss.scheduling.ordervehicle.OrderVehicleApplyQueryGrid.toolbar.button.audit.id')
				.hide();
	}
	// 确认到达
	if (!scheduling.orderVehicle
			.isPermission('scheduling/doConfirmToButton')) {
		Ext
				.getCmp('Foss.scheduling.ordervehicle.OrderVehicleApplyQueryGrid.toolbar.button.arrive.id')
				.hide();
	}
	// 车辆归还
	if (!scheduling.orderVehicle
			.isPermission('scheduling/doOrderVehicleGiveBackButton')) {
		Ext
				.getCmp('Foss.scheduling.ordervehicle.OrderVehicleApplyQueryGrid.toolbar.button.return.id')
				.hide();
	}
});

/**
 * 约车申请Model
 */
Ext.define('Foss.scheduling.ordervehicle.OrderVehicleApplyFormModel', {
			extend : 'Ext.data.Model',
			fields : [{
						// 约车编号
						name : 'orderNo',
						type : 'string'
					}, {
						// 申请时间
						name : 'applyTime',
						type : 'date',
						convert : function(value) {
							if (!value)
								return '';
							var date = new Date(value);
							var formatStr = 'Y-m-d H:i:s';
							return Ext.Date.format(date, formatStr);
						}
					}, {
						// 预约类型
						name : 'orderType',
						type : 'string'
					}, {
						// 车型
						name : 'orderVehicleModel',
						type : 'string'
					}, {
						// 派车车队
						name : 'dispatchTransDept',
						type : 'string'
					}, {
						// 用车地址
						name : 'useAddress',
						type : 'string'
					}, {
						// a b货
						name : 'goodsType',
						type : 'string'
					}, {
						// 是否尾班车
						name : 'isTailboard',
						type : 'string'
					}, {
						// 预计用车时间
						name : 'predictUseTime',
						type : 'date',
						convert : function(value) {
							if (!value)
								return '';
							var date = new Date(value);
							var formatStr = 'Y-m-d H:i:s';
							return Ext.Date.format(date, formatStr);
						}
					}, {
						// 货物名称
						name : 'goodsName',
						type : 'string'
					}, {
						// 货物重量
						name : 'weight',
						type : 'string'
					}, {
						// 货物体积
						name : 'volume',
						type : 'string'
					}, {
						// 货物件数
						name : 'goodsQty',
						type : 'string'
					}, {
						// 备注
						name : 'notes',
						type : 'string'
					}, {
						// 状态
						name : 'status',
						type : 'string'
					}, {
						// 用车部门
						name : 'useVehicleOrgCode',
						type : 'string'
					}, {
						// 用车部门 名称
						name : 'useVehicleOrgName',
						type : 'string'
					}, {
						// 申请网点编码
						name : 'applyOrgCode',
						type : 'string'
					}, {
						// 申请网点编码
						name : 'applyOrgCode',
						type : 'string'
					}, {
						// 申请人员名称
						name : 'applyEmpName',
						type : 'string'
					}, {
						// 申请人员编码
						name : 'applyEmpCode',
						type : 'string'
					}, {
						// 主键id
						name : 'id',
						type : 'string'
					}, {
						// 电话
						name : 'telephoneNo',
						type : 'string'
					}, {
						// 移动电话
						name : 'mobilephoneNo',
						type : 'string'
					}, {
						name : 'dispatchTransDeptName',
						type : 'string'
					}]
		});

/**
 * 约车申请window
 */
Ext.define('Foss.scheduling.ordervehicle.OrderVehicleApplyWindow', {
	extend : 'Ext.window.Window',
	title : scheduling.orderVehicle.i18n('foss.scheduling.orderVehicle.OrderVehicleApply'),//'约车申请',
	width : 700,
	height : 510,
	resizable : false,
	orderType : null,
	carInfo : null,
	goodsInfo : null,
	buttonArea : null,
	operateForm : null,
	// 用于保存 从约车申请查询页面传入的主键id
	orderVehicleApplyId : null,
	modal : true,
	closable : true,
	closeAction : 'hide',
	// 预约类型
	createOrderType : function() {
		var tempType = this.orderType;
		if (tempType) {
			return tempType;
		}
		tempType = Ext.create('Ext.form.FieldSet', {
					defaultType : 'textfield',
					width : 655,
					title : scheduling.orderVehicle.i18n('Foss.scheduling.ordervehicle.OrderVehicleApplyQueryForm.orderType'), //'预约类型',
					items : [{
								xtype : 'radiogroup',
								columns : 5,
								vertical : true,
								items : [{
											boxLabel : scheduling.orderVehicle.i18n('foss.scheduling.borrowvehicle.options.transferringGoods'), //'转货',
											name : 'orderType',
											inputValue : 'TRANSIT_GOODS',
											checked : true
										}, {
											boxLabel : scheduling.orderVehicle.i18n('foss.scheduling.orderVehicle.remote.goods'), //'偏线',
											name : 'orderType',
											inputValue : 'REMOTE_GOODS'
										}, {
											boxLabel : scheduling.orderVehicle.i18n('foss.scheduling.borrowvehicle.options.deliversGoods'), //'送货',
											name : 'orderType',
											inputValue : 'SEND_GOODS'
										}]
							}]
				})
		this.orderType = tempType;
		return this.orderType;
	},
	// 车辆信息
	createCarInfo : function() {
		var tempType = this.carInfo;
		if (tempType) {
			return tempType;
		}
		tempType = Ext.create('Ext.form.FieldSet', {
			defaultType : 'textfield',
			title : scheduling.orderVehicle.i18n('foss.shortScheduleDesign.form.ShortInnerCar.fieldset.carinfo.lable'), //'车辆信息',
			width : 655,
			items : [{
				xtype : 'container',
				layout : 'column',
				defaultType : 'textfield',
				defaults : {
					labelWidth : 85,
					margin : '5 10 5 10'
				},
				items : [{
							name : 'orderVehicleModel',
							xtype : 'commonvehicletypeselector',
							fieldLabel : scheduling.orderVehicle.i18n('foss.shortScheduleDesign.form.ShortInnerCar.truckModel.lable'), //'车型',
							columnWidth : .34,
							allowBlank : false
						}, {
							name : 'useVehicleOrgCode',
							xtype : 'dynamicorgcombselector',
							active : 'Y',
							type : 'ORG',
							salesDepartment : 'Y',// 查询营业部 配置此值
							transferCenter : 'Y',// 查询外场 配置此值
							doAirDispatch : 'Y',// 查询空运配载 配置此值
							transDepartment : 'Y',// 查询车队 配置此值
							dispatchTeam : 'Y',// 查询车队调度组 配置此值
							transTeam : 'Y',// 查询车队组 配置此值
							fieldLabel : scheduling.orderVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyQueryForm.applyOrgCode'),//'用车部门',
							columnWidth : .33,
							allowBlank : false,
							listeners : {
								'change' : function(ths, newValue, oldValue,
										eOpts) {
									if (Ext.isEmpty(ths.getRawValue())) {
										ths.setValue(null);
									}
								},
								'select' : function(combo, records, eOpts) {
									var record = records[0].data;
									var orgCode = record.code;
									scheduling.orderVehicle.ordertruckApplyWindow
											.setDefaultMotorcade(orgCode);
								}
							}
						}, {
							name : 'dispatchTransDept',
							xtype : 'commonmotorcadeselector',
							topFleetOrgCode : FossUserContext.getCurrentDept().code,
							fieldLabel : scheduling.orderVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.inviteVehicleApplyInfo.dispatchTransDept'),//'派车车队',
							columnWidth : .33,
							allowBlank : false
						}, {
							name : 'useAddress',
							fieldLabel : scheduling.orderVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.inviteVehicleApplyInfo.useAddress'),//'用车地址',
							allowBlank : false,
							maxLength : 300,
							maxLengthText : scheduling.orderVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.inviteVehicleApplyInfo.useAddress.message'),//'用车地址已超过最大限制!',
							columnWidth : .67
						}, {
							xtype : 'container',
							columnWidth : .33
						}, {
							name : 'telephoneNo',
							fieldLabel : scheduling.orderVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleDetailWindow.inviteVehiclepplyInfo.telephoneNo'),//'固定电话',
							maxLength : 20,
							maxLengthText : scheduling.orderVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.inviteVehicleApplyInfo.telephoneNo.message'),//'固话号码已超过最大限制!',
							columnWidth : .5
						}, {
							name : 'mobilephoneNo',
							fieldLabel : scheduling.orderVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.inviteVehicleApplyInfo.mobilephoneNo'),//'手机',
							maxLength : 20,
							maxLengthText : scheduling.orderVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.inviteVehicleApplyInfo.mobilephoneNo.message'),//'手机号码已超过最大限制!',
							columnWidth : .5
						}, {
							columnWidth : .5,
							fieldLabel : scheduling.orderVehicle.i18n('foss.scheduling.orderVehicle.predictUseTime'),//'预约用车时间',
							name : 'predictUseTime',
							xtype : 'datetimefield_date97',
							editable : false,
							time : true,
							id : 'Foss_scheduling_ordervehicle_predictUseTime_ID',
							allowBlank : false,
							dateConfig : {
								el : 'Foss_scheduling_ordervehicle_predictUseTime_ID-inputEl'
							}
						}, {
							name : 'isTailboard',
							xtype : 'checkboxfield',
							boxLabel : scheduling.orderVehicle.i18n('foss.scheduling.passOrderVehicle.tailboard'),//'尾板车',
							inputValue : 'Y',
							columnWidth : .33
						}]
			}]
		})
		this.carInfo = tempType;
		return this.carInfo;
	},
	// 货物信息
	createGoodsInfo : function() {
		var tempType = this.goodsInfo;
		if (tempType) {
			return tempType;
		}
		tempType = Ext.create('Ext.form.FieldSet', {
					defaultType : 'textfield',
					title : scheduling.orderVehicle.i18n('foss.scheduling.orderVehicle.GoodsInfo'),//'货物信息',
					width : 655,
					items : [{
								xtype : 'container',
								layout : 'column',
								defaultType : 'textfield',
								defaults : {
									labelWidth : 85,
									margin : '5 10 5 10'
								},
								items : [{
											name : 'goodsName',
											fieldLabel : scheduling.orderVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.customerAndGoodsInfo.goodsName'),//'货物名称',
											maxLength : 66,
											maxLengthText : scheduling.orderVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.customerAndGoodsInfo.goodsName.message'),//'货物名称已超过最大限制!',
											columnWidth : .34
										}, {
											name : 'weight',
											fieldLabel : scheduling.orderVehicle.i18n('Foss.PassInviteVehicleIndex.Grid.Tip.CarInfo.weight'),//'重量(吨)',
											columnWidth : .33,
											regex : /^(\d{1,9})(\.\d{1,3})?$/,
											regexText : scheduling.orderVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.customerAndGoodsInfo.weight.message'),//"重量格式输入有误.必须是数字!",
											maxLength : 13,
											maxLengthText : scheduling.orderVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.customerAndGoodsInfo.weight.message1'),//'重量已超过最大限制!',
											allowBlank : false
										}, {
											name : 'volume',
											fieldLabel : scheduling.orderVehicle.i18n('Foss.PassInviteVehicleIndex.Grid.Tip.CarInfo.volume'),//'体积(方)',
											columnWidth : .33,
											regex : /^(\d{1,5})(\.\d{1,3})?$/,
											regexText : scheduling.orderVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.customerAndGoodsInfo.volume.message'),//"体积格式输入有误.必须是数字!",
											maxLength : 9,
											maxLengthText : scheduling.orderVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.customerAndGoodsInfo.volume.message1'),//'体积已超过最大限制!',
											allowBlank : false
										}, {
											name : 'goodsQty',
											fieldLabel : scheduling.orderVehicle.i18n('foss.scheduling.passOrderVehicle.goodsQty'),//'件数',
											columnWidth : .34,
											regex : /^\d+$/,
											regexText : scheduling.orderVehicle.i18n('foss.scheduling.passOrderVehicle.goodsQty.Message'),//"件数输入有误.必须是数字!",
											maxLength : 9,
											maxLengthText : scheduling.orderVehicle.i18n('foss.scheduling.passOrderVehicle.goodsQty.Message1'),//'件数已超过最大限制!',
											allowBlank : false
										}, {
											columnWidth : .33,
											xtype : 'radiogroup',
											columns : 2,
											vertical : true,
											items : [{
														boxLabel : scheduling.orderVehicle.i18n('foss.scheduling.passOrderVehicle.goodsType.A'),//'A货',
														name : 'goodsType',
														inputValue : 'A'
													}, {
														boxLabel : scheduling.orderVehicle.i18n('foss.scheduling.passOrderVehicle.goodsType.B'),//'B货',
														name : 'goodsType',
														inputValue : 'B'
													}]
										}, {
											columnWidth : .33,
											xtype : 'container'
										}, {
											name : 'notes',
											fieldLabel : scheduling.orderVehicle.i18n('foss.longscheduledesign.gird.plandetail.truckInfoNotes.lable'),//'备注',
											maxLength : 333,
											maxLengthText : scheduling.orderVehicle.i18n('foss.scheduling.orderVehicle.notes.Message'),//'备注已超过最大限制!',
											columnWidth : .67
										}]
							}]
				})
		this.goodsInfo = tempType;
		return this.goodsInfo;
	},
	// 按钮区域
	createButtonArea : function() {
		var temp = this.buttonArea;
		if (temp) {
			return temp;
		}
		temp = Ext.create('Ext.container.Container', {
			defaultType : 'button',
			name : 'buttonArea',
			defaults : {
				margin : '2 7 2 7'
			},
			// 新增 修改操作
			saveVehicleApply : function(url, status, callback) {
				// form object
				var orderVehicleApplyForm = scheduling.orderVehicle.orderVehicleApplyForm;
				var operateForm = orderVehicleApplyForm.getForm();
				var findField = function(name) {
					return operateForm.findField(name);
				}
				// 验证
				if (!operateForm.isValid()) {
					Ext.MessageBox.alert(scheduling.orderVehicle.i18n('Foss.scheduling.leadTruck.QueryForm.tips'), scheduling.orderVehicle.i18n('Foss.scheduling.validation.tip.input'));//'有必填项未完成!');
					return false;
				}
				// 固话 与 手机号码必须输入一个
				var telephoneNo = findField('telephoneNo').getValue();
				var mobilephoneNo = findField('mobilephoneNo').getValue();
				if (Ext.isEmpty(telephoneNo) && Ext.isEmpty(mobilephoneNo)) {
					Ext.MessageBox.alert(scheduling.orderVehicle.i18n('Foss.scheduling.leadTruck.QueryForm.tips'), scheduling.orderVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.customerAndGoodsInfo.submit.validation.message'));//'固话 与 手机号码必须输入一个!');
					return false;
				}
				var nowDate = new Date();
				var predictUseTime = findField('predictUseTime').getValue();
				var predictUseTimeDate = Ext.Date.parse(predictUseTime,
						"Y-m-d H:i:s", true);
				var result = nowDate.getTime() >= predictUseTimeDate.getTime();
				if (result) {
					Ext.MessageBox.alert(scheduling.orderVehicle.i18n('Foss.scheduling.leadTruck.QueryForm.tips'),scheduling.orderVehicle.i18n('foss.scheduling.orderVehicle.predictUseTimeDate'));//'预计用车时间必须大于当前时间!');
					return false;
				}
				var record = operateForm.getRecord();
				operateForm.updateRecord(record);
				record.data.predictUseTime = predictUseTime;
				// 状态
				record.data.status = status;
				// 是否尾班车
				record.data.isTailboard = (record.data.isTailboard == 'true'
						? 'Y'
						: 'N');
				// 申请时间
				if (!record.data.applyTime || record.data.applyTime == '') {
					record.data.applyTime = nowDate;
				}
				// 车型
				record.data.orderVehicleModel = findField('orderVehicleModel')
						.getRawValue();
				// 封装vo
				var orderVehicleVo = {
					orderVehicleVo : {
						orderVehicleEntity : record.data
					}
				};
				orderVehicleApplyForm.getEl().mask(scheduling.orderVehicle
						.i18n('Foss.scheduling.saving'));
				Ext.Ajax.request({
					url : scheduling.realPath(url),
					jsonData : orderVehicleVo,
					success : function(response) {
						orderVehicleApplyForm.getEl().unmask();
						var result = Ext.decode(response.responseText);
						var orderNo = result.orderVehicleVo.orderVehicleEntity.orderNo;
						Ext.MessageBox.alert(scheduling.orderVehicle.i18n('Foss.scheduling.leadTruck.QueryForm.tips'), scheduling.orderVehicle.i18n('foss.scheduling.orderVehicle.orderNo') + orderNo);

						scheduling.orderVehicle.ordertruckApplyWindow.close();
						scheduling.orderVehicle.pagingBar.moveFirst();
					},
					exception : function(response) {
						orderVehicleApplyForm.getEl().unmask();
						var result = Ext.decode(response.responseText);
						Ext.MessageBox.alert(scheduling.orderVehicle.i18n('Foss.scheduling.leadTruck.QueryForm.tips'), result.message);
					}
				});

			},
			layout : 'column',
			items : [{
						xtype : 'container',
						html : '&nbsp;',
						columnWidth : .29
					}, {
						text : scheduling.orderVehicle.i18n('Foss.scheduling.button.submit'), //'提交',
						disabled : !scheduling.orderVehicle.isPermission('scheduling/saveVehicleApplyButton'),
						hidden : !scheduling.orderVehicle.isPermission('scheduling/saveVehicleApplyButton'),
						columnWidth : .14,
						handler : function() {
							// 提交 状态为UNAPPROVED 未审核
							var url = "saveVehicleApply.action";
							this.ownerCt.saveVehicleApply(url, 'UNAPPROVED');
						}
					}, {
						name : 'doTemporarySaveBtn',
						text : scheduling.orderVehicle.i18n('Foss.scheduling.button.keepin'), //'暂存',
						disabled : !scheduling.orderVehicle.isPermission('scheduling/doTemporarySaveButton'),
						hidden : !scheduling.orderVehicle.isPermission('scheduling/doTemporarySaveButton'),
						columnWidth : .14,
						handler : function() {
							// 暂存 状态为 STAGING
							var url = "doTemporarySave.action";
							this.ownerCt.saveVehicleApply(url, 'STAGING');
						}
					}, {
						name : 'resetBtn',
						text : scheduling.orderVehicle.i18n('Foss.scheduling.button.reset'), // 重置
						columnWidth : .14,
						handler : function() {
							var id = this.ownerCt.ownerCt.ownerCt.orderVehicleApplyId;
							if (!id) {
								// 新增重置
								scheduling.orderVehicle.orderVehicleApplyForm
										.getForm().reset();
								// 默认值
								scheduling.orderVehicle.ordertruckApplyWindow
										.setDefaultValue();
								return;
							}
							// 修改重置, 重新从数据库load数据
							this.ownerCt.ownerCt.ownerCt.initData();
						}
					}, {
						xtype : 'container',
						html : '&nbsp;',
						columnWidth : .29
					}]
		});
		this.buttonArea = temp;
		return this.buttonArea;
	},
	// 初始化全局form
	createOperateForm : function() {
		var form = this.operateForm;
		if (form) {
			return form;
		}
		form = Ext.create('Ext.form.Panel', {
					frame : false
				});
		this.operateForm = form;
		return this.operateForm;
	},
	// 初始化所有组件
	initAllComponent : function() {
		this.createOrderType();
		this.createCarInfo();
		this.createGoodsInfo();
		this.createButtonArea();
	},
	setDefaultValue : function() {
		var orderVehicleApplyForm = scheduling.orderVehicle.orderVehicleApplyForm;
		var operateForm = orderVehicleApplyForm.getForm();
		var address = FossUserContext.getCurrentDept().address;
		var deptName = FossUserContext.getCurrentDept().name;
		var deptCode = FossUserContext.getCurrentDept().code;
		operateForm.findField('useVehicleOrgCode').setCombValue(deptName,
				deptCode);
		operateForm.findField('useAddress').setValue(address);
		//this.setDefaultMotorcade();
	},
	setDefaultMotorcade : function(useVehicleOrgCode) {
		if (!useVehicleOrgCode) {
			useVehicleOrgCode = FossUserContext.getCurrentDept().code;
		}
		var params = {
			"orderVehicleVo.salesdeptCode" : useVehicleOrgCode
		};
		Ext.Ajax.request({
			url : scheduling.realPath('queryMotorcade.action'),
			params : params,
			success : function(response) {
				var result = Ext.decode(response.responseText);
				var data = result.orderVehicleVo.salesMotorcadeList;
				var motorcadeObject;
				if (data && data.length != 0) {
					motorcadeObject = data[0];
				}
				var orderVehicleApplyForm = scheduling.orderVehicle.orderVehicleApplyForm;
				var dispatchTransDeptObj = orderVehicleApplyForm.getForm()
						.findField('dispatchTransDept');
				if (motorcadeObject) {
					dispatchTransDeptObj.setCombValue(
							motorcadeObject.motorcadeName,
							motorcadeObject.motorcadeCode);
				}
			},
			exception : function(response) {
				var result = Ext.decode(response.responseText);
				Ext.MessageBox.alert(scheduling.orderVehicle.i18n('Foss.scheduling.leadTruck.QueryForm.tips'), result.message);
			}
		});
	},
	// 编辑操作 初始化数据.
	initData : function() {
		var id = this.orderVehicleApplyId;
		var form = this.operateForm;
		if (!id) {
			form
					.loadRecord(Ext
							.create('Foss.scheduling.ordervehicle.OrderVehicleApplyFormModel'));
			scheduling.orderVehicle.orderVehicleApplyForm.getForm().reset();
			this.setDefaultValue();
			return;
		}
		// 获得暂存按钮object
		var items = form.items.items;
		var buttonAreaItem = items[3].items.items;
		var doTemporarySaveBtn = buttonAreaItem[2];
		// 隐藏暂存按钮
		doTemporarySaveBtn.hidden = !doTemporarySaveBtn.hidden;
		var params = {
			"orderVehicleVo.orderVehicleEntity.id" : id
		};
		Ext.Ajax.request({
			url : scheduling.realPath('queryOrderVehicleApplyDetail.action'),
			params : params,
			success : function(response) {
				var result = Ext.decode(response.responseText);
				var record = Ext
						.create(
								'Foss.scheduling.ordervehicle.OrderVehicleApplyFormModel',
								result.orderVehicleVo.orderVehicleEntity);
				var status = record.data.status;
				var map = new Ext.util.HashMap();
				// 暂存
				map.add('STAGING', 'STAGING');
				// 未审核
				map.add('UNAPPROVED', 'UNAPPROVED');
				// 退回
				map.add('RETURN', 'RETURN');
				// 撤销
				map.add('UNDO', 'UNDO');
				if (!map.containsKey(status)) {
					Ext.MessageBox.alert(scheduling.orderVehicle.i18n('Foss.scheduling.leadTruck.QueryForm.tips'), scheduling.orderVehicle.i18n('foss.scheduling.orderVehicle.undo'));//"单据状态发生改变，请刷新数据!");
					scheduling.orderVehicle.ordertruckApplyWindow.close();
					return false;
				}
				form.loadRecord(record);
				// 派车车队 公共选择器
				var orderVehicleApplyForm = scheduling.orderVehicle.orderVehicleApplyForm;
				orderVehicleApplyForm.getForm().findField('dispatchTransDept')
						.setCombValue(record.data.dispatchTransDeptName,
								record.data.dispatchTransDept);
				orderVehicleApplyForm.getForm().findField('useVehicleOrgCode')
						.setCombValue(record.data.useVehicleOrgName,
								record.data.useVehicleOrgCode);

			},
			exception : function(response) {
				var result = Ext.decode(response.responseText);
				Ext.MessageBox.alert(scheduling.orderVehicle.i18n('Foss.scheduling.leadTruck.QueryForm.tips'), result.message);
			}
		});
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([cfg]);
		// 初始化所有组件
		me.initAllComponent();
		// 初始化全局form
		var form = me.createOperateForm();
		form.add([me.orderType, me.carInfo, me.goodsInfo, me.buttonArea]);
		// 保存form到全局变量中
		scheduling.orderVehicle.orderVehicleApplyForm = form;
		me.add([form]);
	}
});
