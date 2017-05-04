// 状态常量，用于表示窗口状态；新增、修改、查看
platform.tfrCtrPersonnelBudget.viewState = {
	add : 'ADD',
	update : 'UPDATE',
	view : 'VIEW'
};

// 模块常量，用于区分是预算人员还是实际人员
platform.tfrCtrPersonnelBudget.modulType = {
	budget : 'BUDGET',
	actual : 'ACTUAL'
};

Ext.define('Foss.platform.tfrCtrPersonnelBudget.formModel', {
			extend : 'Ext.data.Model',
			fields : [{
						name : 'id',
						type : 'string'
					}, {
						name : 'modifyTime',
						type : 'long'
					}, {
						name : 'transferCenterCode',
						type : 'string'
					}, {
						name : 'transferCenterName',
						type : 'string'
					}, {
						name : 'director',
						type : 'int'
					}, {
						name : 'directorStatistician',
						type : 'int'
					}, {
						name : 'seniorManager',
						type : 'int'
					}, {
						name : 'manager',
						type : 'int'
					}, {
						name : 'subManager',
						type : 'int'
					}, {
						name : 'dispatch',
						type : 'int'
					}, {
						name : 'forkliftSecurityGuard',
						type : 'int'
					}, {
						name : 'goodsAreaGuard',
						type : 'int'
					}, {
						name : 'platformGuard',
						type : 'int'
					}, {
						name : 'civilService',
						type : 'int'
					}, {
						name : 'partialMember',
						type : 'int'
					}, {
						name : 'tallyClerk',
						type : 'int'
					}, {
						name : 'electricForkliftDriver',
						type : 'int'
					}, {
						name : 'machineForkliftDriver',
						type : 'int'
					}, {
						name : 'carpenter',
						type : 'int'
					}, {
						name : 'repairer',
						type : 'int'
					}, {
						name : 'sorter',
						type : 'int'
					}, {
						name : 'other',
						type : 'int'
					}, {
						name : 'dimission',
						type : 'int'
					}, {
						name : 'beginEffectiveDate',
						type : 'date',
						convert : dateConvert
					}]
		});

platform.tfrCtrPersonnelBudget.createFormModel = function(modulType, viewState,
		formRecord, selectedTfrCtrCode) {
	var id = null, modifyTime = null, beginEffectiveDate = null, transferCenterCode = null, transferCenterName = null, director = null;
	var directorStatistician = null, seniorManager = null, manager = null, subManager = null, dispatch = null;
	var forkliftSecurityGuard = null, goodsAreaGuard = null, platformGuard = null, civilService = null;
	var partialMember = null, tallyClerk = null, electricForkliftDriver = null, machineForkliftDriver = null;
	var carpenter = null, repairer = null, sorter = null, other = null, dimission = null;

	if (viewState === platform.tfrCtrPersonnelBudget.viewState.add) {
		if (modulType === platform.tfrCtrPersonnelBudget.modulType.budget) {
			if (!Ext.isEmpty(selectedTfrCtrCode)) {
				Ext.Ajax.request({
					async : false,
					url : platform.realPath('selectBudgetLast.action'),
					jsonData : {
						'tfrCtrPersonnelBudgetVo' : {
							'tfrCtrPersonnelBudgetEntity' : {
								'transferCenterCode' : selectedTfrCtrCode
							}
						}
					},
					success : function(response) {
						var result = Ext.decode(response.responseText), tfrCtrPersonnelBudgetEntity = result.tfrCtrPersonnelBudgetVo.tfrCtrPersonnelBudgetEntity;
						if (Ext.isEmpty(tfrCtrPersonnelBudgetEntity)) {
							return;
						}
						transferCenterCode = tfrCtrPersonnelBudgetEntity.transferCenterCode;
						transferCenterName = tfrCtrPersonnelBudgetEntity.transferCenterName;
						director = tfrCtrPersonnelBudgetEntity.director;
						directorStatistician = tfrCtrPersonnelBudgetEntity.directorStatistician;
						seniorManager = tfrCtrPersonnelBudgetEntity.seniorManager;
						manager = tfrCtrPersonnelBudgetEntity.manager;
						subManager = tfrCtrPersonnelBudgetEntity.subManager;
						dispatch = tfrCtrPersonnelBudgetEntity.dispatch;
						forkliftSecurityGuard = tfrCtrPersonnelBudgetEntity.forkliftSecurityGuard;
						goodsAreaGuard = tfrCtrPersonnelBudgetEntity.goodsAreaGuard;
						platformGuard = tfrCtrPersonnelBudgetEntity.platformGuard;
						civilService = tfrCtrPersonnelBudgetEntity.civilService;
						partialMember = tfrCtrPersonnelBudgetEntity.partialMember;
						tallyClerk = tfrCtrPersonnelBudgetEntity.tallyClerk;
						electricForkliftDriver = tfrCtrPersonnelBudgetEntity.electricForkliftDriver;
						machineForkliftDriver = tfrCtrPersonnelBudgetEntity.machineForkliftDriver;
						carpenter = tfrCtrPersonnelBudgetEntity.carpenter;
						repairer = tfrCtrPersonnelBudgetEntity.repairer;
						sorter = tfrCtrPersonnelBudgetEntity.sorter;
						other = tfrCtrPersonnelBudgetEntity.other;
					}
				});
			}
		} else if (modulType === platform.tfrCtrPersonnelBudget.modulType.actual) {
			Ext.Ajax.request({
				async : false,
				url : platform.realPath('selectActualLast.action'),
				jsonData : {
					'tfrCtrPersonnelBudgetVo' : {
						'tfrCtrPersonnelActualEntity' : {
							'transferCenterCode' : platform.tfrCtrPersonnelBudget.parentTfrCtrCode
						}
					}
				},
				success : function(response) {
					var result = Ext.decode(response.responseText), tfrCtrPersonnelActualEntity = result.tfrCtrPersonnelBudgetVo.tfrCtrPersonnelActualEntity;
					if (Ext.isEmpty(tfrCtrPersonnelActualEntity)) {
						return;
					}
					transferCenterCode = tfrCtrPersonnelActualEntity.transferCenterCode;
					transferCenterName = tfrCtrPersonnelActualEntity.transferCenterName;
					director = tfrCtrPersonnelActualEntity.director;
					directorStatistician = tfrCtrPersonnelActualEntity.directorStatistician;
					seniorManager = tfrCtrPersonnelActualEntity.seniorManager;
					manager = tfrCtrPersonnelActualEntity.manager;
					subManager = tfrCtrPersonnelActualEntity.subManager;
					dispatch = tfrCtrPersonnelActualEntity.dispatch;
					forkliftSecurityGuard = tfrCtrPersonnelActualEntity.forkliftSecurityGuard;
					goodsAreaGuard = tfrCtrPersonnelActualEntity.goodsAreaGuard;
					platformGuard = tfrCtrPersonnelActualEntity.platformGuard;
					civilService = tfrCtrPersonnelActualEntity.civilService;
					partialMember = tfrCtrPersonnelActualEntity.partialMember;
					tallyClerk = tfrCtrPersonnelActualEntity.tallyClerk;
					electricForkliftDriver = tfrCtrPersonnelActualEntity.electricForkliftDriver;
					machineForkliftDriver = tfrCtrPersonnelActualEntity.machineForkliftDriver;
					carpenter = tfrCtrPersonnelActualEntity.carpenter;
					repairer = tfrCtrPersonnelActualEntity.repairer;
					sorter = tfrCtrPersonnelActualEntity.sorter;
					other = tfrCtrPersonnelActualEntity.other;
					dimission = tfrCtrPersonnelActualEntity.dimission;
				}
			});
		}
	} else {
		if (modulType === platform.tfrCtrPersonnelBudget.modulType.budget) {
			id = formRecord.get('idBudget');
			modifyTime = formRecord.get('modifyTimeBudget');
			beginEffectiveDate = formRecord.get('beginEffectiveDateBudget');
			transferCenterCode = formRecord.get('transferCenterCode');
			transferCenterName = formRecord.get('transferCenterName');
			director = formRecord.get('directorBudget');
			directorStatistician = formRecord.get('directorStatisticianBudget');
			seniorManager = formRecord.get('seniorManagerBudget');
			manager = formRecord.get('managerBudget');
			subManager = formRecord.get('subManagerBudget');
			dispatch = formRecord.get('dispatchBudget');
			forkliftSecurityGuard = formRecord
					.get('forkliftSecurityGuardBudget');
			goodsAreaGuard = formRecord.get('goodsAreaGuardBudget');
			platformGuard = formRecord.get('platformGuardBudget');
			civilService = formRecord.get('civilServiceBudget');
			partialMember = formRecord.get('partialMemberBudget');
			tallyClerk = formRecord.get('tallyClerkBudget');
			electricForkliftDriver = formRecord
					.get('electricForkliftDriverBudget');
			machineForkliftDriver = formRecord
					.get('machineForkliftDriverBudget');
			carpenter = formRecord.get('carpenterBudget');
			repairer = formRecord.get('repairerBudget');
			sorter = formRecord.get('sorterBudget');
			other = formRecord.get('otherBudget');
		} else if (modulType === platform.tfrCtrPersonnelBudget.modulType.actual) {
			id = formRecord.get('idActual');
			modifyTime = formRecord.get('modifyTimeActual');
			beginEffectiveDate = formRecord.get('beginEffectiveDateActual');
			transferCenterCode = formRecord.get('transferCenterCode');
			transferCenterName = formRecord.get('transferCenterName');
			director = formRecord.get('directorActual');
			directorStatistician = formRecord.get('directorStatisticianActual');
			seniorManager = formRecord.get('seniorManagerActual');
			manager = formRecord.get('managerActual');
			subManager = formRecord.get('subManagerActual');
			dispatch = formRecord.get('dispatchActual');
			forkliftSecurityGuard = formRecord
					.get('forkliftSecurityGuardActual');
			goodsAreaGuard = formRecord.get('goodsAreaGuardActual');
			platformGuard = formRecord.get('platformGuardActual');
			civilService = formRecord.get('civilServiceActual');
			partialMember = formRecord.get('partialMemberActual');
			tallyClerk = formRecord.get('tallyClerkActual');
			electricForkliftDriver = formRecord
					.get('electricForkliftDriverActual');
			machineForkliftDriver = formRecord
					.get('machineForkliftDriverActual');
			carpenter = formRecord.get('carpenterActual');
			repairer = formRecord.get('repairerActual');
			sorter = formRecord.get('sorterActual');
			other = formRecord.get('otherActual');
			dimission = formRecord.get('dimission');
		}
	}

	if (transferCenterCode === null) {
		return;
	}

	return Ext.create('Foss.platform.tfrCtrPersonnelBudget.formModel', {
				id : id,
				modifyTime : modifyTime,
				beginEffectiveDate : beginEffectiveDate,
				transferCenterCode : transferCenterCode,
				transferCenterName : transferCenterName,
				director : director,
				directorStatistician : directorStatistician,
				seniorManager : seniorManager,
				manager : manager,
				subManager : subManager,
				dispatch : dispatch,
				forkliftSecurityGuard : forkliftSecurityGuard,
				goodsAreaGuard : goodsAreaGuard,
				platformGuard : platformGuard,
				civilService : civilService,
				partialMember : partialMember,
				tallyClerk : tallyClerk,
				electricForkliftDriver : electricForkliftDriver,
				machineForkliftDriver : machineForkliftDriver,
				carpenter : carpenter,
				repairer : repairer,
				sorter : sorter,
				other : other,
				dimission : dimission
			});
};

platform.tfrCtrPersonnelBudget.initViewWindow = function(window, modulType,
		viewState, formRecord) {

	window.modulType = modulType;
	window.editForm.modulType = modulType;
	window.viewState = viewState;
	window.editForm.viewState = viewState;

	// 获取window中的button
	var buttons = window.query('button');

	var editForm = window.editForm.getForm(), cmpBeginEffectiveDate = editForm
			.findField('beginEffectiveDate'), cmpDimission = editForm
			.findField('dimission');

	if (modulType === platform.tfrCtrPersonnelBudget.modulType.budget) {

		editForm.findField('transferCenter').setReadOnly(false);

		if (viewState === platform.tfrCtrPersonnelBudget.viewState.add) {
			window.setTitle('新增外场人员预算信息');
			for (var i = 0; i < buttons.length; i++) {
				buttons[i].setVisible(true);
			}
			
			editForm.findField('transferCenter').setReadOnly(false);

		} else if (viewState === platform.tfrCtrPersonnelBudget.viewState.update) {
			window.setTitle('修改外场人员预算信息');
			for (var i = 0; i < buttons.length; i++) {
				buttons[i].setVisible(i !== 1 && i !== 2);
			}
			
			editForm.findField('transferCenter').setReadOnly(true);
		}

		window.editForm.setTitle('外场人员预算信息')

		cmpBeginEffectiveDate.setVisible(true);
		cmpDimission.setVisible(false);
		cmpDimission.allowBlank = true;
	} else if (modulType === platform.tfrCtrPersonnelBudget.modulType.actual) {
		if (viewState === platform.tfrCtrPersonnelBudget.viewState.add) {
			window.setTitle('新增外场人员实际信息');
			for (var i = 0; i < buttons.length; i++) {
				buttons[i].setVisible(true);
			}

			editForm.findField('transferCenter').setReadOnly(true);

		} else if (viewState === platform.tfrCtrPersonnelBudget.viewState.update) {
			window.setTitle('修改外场人员实际信息');
			for (var i = 0; i < buttons.length; i++) {
				buttons[i].setVisible(i !== 1 && i !== 2);
			}

			editForm.findField('transferCenter').setReadOnly(true);
		}

		window.editForm.setTitle('外场人员实际信息')

		cmpBeginEffectiveDate.setVisible(false);
		cmpDimission.setVisible(true);
		cmpDimission.allowBlank = false;
	}

	var record = platform.tfrCtrPersonnelBudget.createFormModel(modulType,
			viewState, formRecord);

	if (!Ext.isEmpty(record)) {
		editForm.loadRecord(record)
		editForm.findField('transferCenter').setCombValue(
				record.get('transferCenterName'),
				record.get('transferCenterCode'));
	}

	return window;
};

platform.tfrCtrPersonnelBudget.submitTfrCtrPersonnelBudget = function(window) {
	var viewState = window.viewState, modulType = window.modulType, editForm = window.editForm
			.getForm();

	if (!editForm.isValid()) {
		return;
	}

	// 提交参数
	var param = null, url = null, formValues = editForm.getValues(), transferCenterCmp = editForm
			.findField('transferCenter');

	if (viewState === platform.tfrCtrPersonnelBudget.viewState.add) {
		if (modulType === platform.tfrCtrPersonnelBudget.modulType.actual) {
			url = platform.realPath('insertActual.action');
			param = {
				'tfrCtrPersonnelBudgetVo' : {
					'tfrCtrPersonnelActualEntity' : formValues
				}
			};
			var transferCenterCode = formValues.transferCenterCode;
			param.tfrCtrPersonnelBudgetVo.tfrCtrPersonnelActualEntity.transferCenterName = transferCenterCmp.store
					.findRecord('code', transferCenterCode, false, true, true)
					.get('name');
		} else if (modulType === platform.tfrCtrPersonnelBudget.modulType.budget) {
			url = platform.realPath('insertBudget.action');
			param = {
				'tfrCtrPersonnelBudgetVo' : {
					'tfrCtrPersonnelBudgetEntity' : formValues
				}
			};
			var transferCenterCode = formValues.transferCenterCode;
			param.tfrCtrPersonnelBudgetVo.tfrCtrPersonnelBudgetEntity.transferCenterName = transferCenterCmp.store
					.findRecord('code', transferCenterCode, false, true, true)
					.get('name');
		}
	} else if (viewState === platform.tfrCtrPersonnelBudget.viewState.update) {

		if (modulType === platform.tfrCtrPersonnelBudget.modulType.actual) {
			url = platform.realPath('updateActual.action');
			param = {
				'tfrCtrPersonnelBudgetVo' : {
					'tfrCtrPersonnelActualEntity' : formValues
				}
			};
			var date = new Date();
			date.setTime(formValues.modifyTime)
			param.tfrCtrPersonnelBudgetVo.tfrCtrPersonnelActualEntity.modifyTime = date;
			var transferCenterCode = formValues.transferCenterCode;
			param.tfrCtrPersonnelBudgetVo.tfrCtrPersonnelActualEntity.transferCenterName = transferCenterCmp.store
					.findRecord('code', transferCenterCode, false, true, true)
					.get('name');
		} else if (modulType === platform.tfrCtrPersonnelBudget.modulType.budget) {
			url = platform.realPath('updateBudget.action');
			param = {
				'tfrCtrPersonnelBudgetVo' : {
					'tfrCtrPersonnelBudgetEntity' : formValues
				}
			};
			var date = new Date();
			date.setTime(formValues.modifyTime)
			param.tfrCtrPersonnelBudgetVo.tfrCtrPersonnelBudgetEntity.modifyTime = date;
			var transferCenterCode = formValues.transferCenterCode;
			param.tfrCtrPersonnelBudgetVo.tfrCtrPersonnelBudgetEntity.transferCenterName = transferCenterCmp.store
					.findRecord('code', transferCenterCode, false, true, true)
					.get('name');
		}
	}

	// 总数
	var totalQty = parseInt(formValues.director)
			+ parseInt(formValues.directorStatistician)
			+ parseInt(formValues.seniorManager) + parseInt(formValues.manager)
			+ parseInt(formValues.subManager) + parseInt(formValues.dispatch)
			+ parseInt(formValues.forkliftSecurityGuard)
			+ parseInt(formValues.goodsAreaGuard)
			+ parseInt(formValues.platformGuard)
			+ parseInt(formValues.civilService)
			+ parseInt(formValues.partialMember)
			+ parseInt(formValues.tallyClerk)
			+ parseInt(formValues.electricForkliftDriver)
			+ parseInt(formValues.machineForkliftDriver)
			+ parseInt(formValues.carpenter) + parseInt(formValues.repairer)
			+ parseInt(formValues.sorter) + parseInt(formValues.other);

	if (modulType === platform.tfrCtrPersonnelBudget.modulType.actual) {
		param.tfrCtrPersonnelBudgetVo.tfrCtrPersonnelActualEntity.totalQty = totalQty;
	} else if (modulType === platform.tfrCtrPersonnelBudget.modulType.budget) {
		param.tfrCtrPersonnelBudgetVo.tfrCtrPersonnelBudgetEntity.totalQty = totalQty;
	}

	var windowMask = new Ext.LoadMask(window, {
				msg : "保存中，请稍后..."
			});
	windowMask.show();

	Ext.Ajax.request({
		url : url,
		jsonData : param,
		success : function(response) {
			Ext.ux.Toast.msg('提示', '保存成功！', 'info', 1500);
			windowMask.hide();
			if (viewState === platform.tfrCtrPersonnelBudget.viewState.add) {
				window.resetData();
				window.initData();
			} else if (viewState === platform.tfrCtrPersonnelBudget.viewState.update) {
				window.sourceGrid.store.load();
			}
		},
		exception : function(response) {
			var result = Ext.decode(response.responseText);
			windowMask.hide();
			Ext.ux.Toast.msg('提示', '保存失败，' + result.message, 'error', 2500);
		}
	});
};

Ext.define('Foss.platform.tfrCtrPersonnelBudget.queryForm', {
			id : 'Foss_platform_tfrCtrPersonnelBudget_queryForm_id',
			extend : 'Ext.form.Panel',
			title : '查询条件',
			frame : true,
			collapsible : false,
			animCollapse : false,
			defaults : {
				margin : '5 10 5 10',
				labelWidth : 85,
				columnWidth : 1 / 4
			},
			layout : 'column',
			items : [{
						fieldLabel : '外场',
						name : 'transferCenter',
						xtype : 'dynamicorgcombselector',
						types : 'ORG',
						transferCenter : 'Y'
					}, {
						xtype : 'datefield',
						fieldLabel : '生效日期',
						name : 'effectiveDate',
						format : 'Y-m-d',
						value : new Date()
					}],
			buttons : [{
				xtype : 'button',
				columnWidth : .08,
				text : '重置',
				handler : function() {
					var form = this.up('form').getForm();
					form.reset();
					if (platform.tfrCtrPersonnelBudget.parentTfrCtrCode) {
						form
								.findField('transferCenter')
								.setCombValue(
										platform.tfrCtrPersonnelBudget.parentTfrCtrName,
										platform.tfrCtrPersonnelBudget.parentTfrCtrCode);
					}
				}
			}, '->', {
				columnWidth : .08,
				xtype : 'button',
				cls : 'yellow_button',
				text : '查询',
				handler : function() {
					var form = this.up('form').getForm();
					if (form.isValid()) {
						platform.tfrCtrPersonnelBudget.resultGrid.store.load();
					}
				}
			}],
			constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				me.callParent([cfg]);
			}
		});

Ext.define('Foss.platform.tfrCtrPersonnelBudget.gridModel', {
			extend : 'Ext.data.Model',
			fields : [{
						name : 'idBudget',
						type : 'string'
					}, {
						name : 'idActual',
						type : 'string'
					}, {
						name : 'createTimeBudget',
						type : 'date',
						convert : dateConvert
					}, {
						name : 'createTimeActual',
						type : 'date',
						convert : dateConvert
					}, {
						name : 'modifyTimeBudget',
						type : 'long'
					}, {
						name : 'modifyTimeActual',
						type : 'long'
					}, {
						name : 'beginEffectiveDateBudget',
						type : 'date',
						convert : dateConvert
					}, {
						name : 'beginEffectiveDateActual',
						type : 'date',
						convert : dateConvert
					}, {
						name : 'endEffectiveDateBudget',
						type : 'date',
						convert : dateConvert
					}, {
						name : 'endEffectiveDateActual',
						type : 'date',
						convert : dateConvert
					}, {
						name : 'transferCenterCode',
						type : 'string'
					}, {
						name : 'transferCenterName',
						type : 'string'
					}, {
						name : 'directorBudget',
						type : 'string'
					}, {
						name : 'directorActual',
						type : 'string'
					}, {
						name : 'directorRemainder',
						type : 'string'
					}, {
						name : 'directorStatisticianBudget',
						type : 'string'
					}, {
						name : 'directorStatisticianActual',
						type : 'string'
					}, {
						name : 'directorStatisticianRemainder',
						type : 'string'
					}, {
						name : 'seniorManagerBudget',
						type : 'string'
					}, {
						name : 'seniorManagerActual',
						type : 'string'
					}, {
						name : 'seniorManagerRemainder',
						type : 'string'
					}, {
						name : 'managerBudget',
						type : 'string'
					}, {
						name : 'managerActual',
						type : 'string'
					}, {
						name : 'managerRemainder',
						type : 'string'
					}, {
						name : 'subManagerBudget',
						type : 'string'
					}, {
						name : 'subManagerActual',
						type : 'string'
					}, {
						name : 'subManagerRemainder',
						type : 'string'
					}, {
						name : 'dispatchBudget',
						type : 'string'
					}, {
						name : 'dispatchActual',
						type : 'string'
					}, {
						name : 'dispatchRemainder',
						type : 'string'
					}, {
						name : 'forkliftSecurityGuardBudget',
						type : 'string'
					}, {
						name : 'forkliftSecurityGuardActual',
						type : 'string'
					}, {
						name : 'forkliftSecurityGuardRemainder',
						type : 'string'
					}, {
						name : 'goodsAreaGuardBudget',
						type : 'string'
					}, {
						name : 'goodsAreaGuardActual',
						type : 'string'
					}, {
						name : 'goodsAreaGuardRemainder',
						type : 'string'
					}, {
						name : 'platformGuardBudget',
						type : 'string'
					}, {
						name : 'platformGuardActual',
						type : 'string'
					}, {
						name : 'platformGuardRemainder',
						type : 'string'
					}, {
						name : 'civilServiceBudget',
						type : 'string'
					}, {
						name : 'civilServiceActual',
						type : 'string'
					}, {
						name : 'civilServiceRemainder',
						type : 'string'
					}, {
						name : 'partialMemberBudget',
						type : 'string'
					}, {
						name : 'partialMemberActual',
						type : 'string'
					}, {
						name : 'partialMemberRemainder',
						type : 'string'
					}, {
						name : 'tallyClerkBudget',
						type : 'string'
					}, {
						name : 'tallyClerkActual',
						type : 'string'
					}, {
						name : 'tallyClerkRemainder',
						type : 'string'
					}, {
						name : 'electricForkliftDriverBudget',
						type : 'string'
					}, {
						name : 'electricForkliftDriverActual',
						type : 'string'
					}, {
						name : 'electricForkliftDriverRemainder',
						type : 'string'
					}, {
						name : 'machineForkliftDriverBudget',
						type : 'string'
					}, {
						name : 'machineForkliftDriverActual',
						type : 'string'
					}, {
						name : 'machineForkliftDriverRemainder',
						type : 'string'
					}, {
						name : 'carpenterBudget',
						type : 'string'
					}, {
						name : 'carpenterActual',
						type : 'string'
					}, {
						name : 'carpenterRemainder',
						type : 'string'
					}, {
						name : 'repairerBudget',
						type : 'string'
					}, {
						name : 'repairerActual',
						type : 'string'
					}, {
						name : 'repairerRemainder',
						type : 'string'
					}, {
						name : 'sorterBudget',
						type : 'string'
					}, {
						name : 'sorterActual',
						type : 'string'
					}, {
						name : 'sorterRemainder',
						type : 'string'
					}, {
						name : 'otherBudget',
						type : 'string'
					}, {
						name : 'otherActual',
						type : 'string'
					}, {
						name : 'otherRemainder',
						type : 'string'
					}, {
						name : 'totalQtyBudget',
						type : 'string'
					}, {
						name : 'totalQtyActual',
						type : 'string'
					}, {
						name : 'totalQtyRemainder',
						type : 'string'
					}, {
						name : 'dimission',
						type : 'string'
					}, {
						name : 'dimissionAccumulated',
						type : 'string'
					}]
		});

Ext.define('Foss.platform.tfrCtrPersonnelBudget.gridStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.platform.tfrCtrPersonnelBudget.gridModel',
	proxy : {
		type : 'ajax',
		actionMethods : 'POST',
		url : platform.realPath('selectTfrCtrPersonnelBudget.action'),
		reader : {
			type : 'json',
			root : 'tfrCtrPersonnelBudgetVo.tfrCtrPersonnelBudgetDtos',
			successProperty : 'success'
		}
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	},
	listeners : {
		'beforeload' : function(store, operation, eOpts) {
			var queryForm = Ext
					.getCmp('Foss_platform_tfrCtrPersonnelBudget_queryForm_id')
					.getForm();
			if (!Ext.isEmpty(queryForm)) {
				var queryParams = queryForm.getValues();
				Ext.apply(operation, {
					params : {
						'tfrCtrPersonnelBudgetVo.tfrCtrPersonnelBudgetQcDto.transferCenterCode' : queryForm
								.findField('transferCenter').getValue(),
						'tfrCtrPersonnelBudgetVo.tfrCtrPersonnelBudgetQcDto.effectiveDate' : queryParams.effectiveDate
					}
				});
			}
		}
	}
});

Ext.define('Foss.platform.tfrCtrPersonnelBudget.resultGrid', {
	id : 'Foss_platform_tfrCtrPersonnelBudget_resultGrid_id',
	extend : 'Ext.grid.Panel',
	frame : true,
	columnLines : true,
	title : '外场人员预算信息',
	height : 600,
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	emptyText : '查询结果为空',
	sortableColumns : false,
	enableColumnHide : false,
	autoScroll : false,
	collapsible : false,
	animCollapse : false,
	viewWindow : null,// 对应新增、修改弹出窗口
	getViewWindow : function(modulType, viewState, formRecord) {
		var me = this;
		if (Ext.isEmpty(me.viewWindow)) {
			me.viewWindow = Ext.create(
					'Foss.platform.tfrCtrPersonnelBudget.viewWindow', {
						'modulType' : modulType,
						'viewState' : viewState,
						'sourceGrid' : me
					});
		}
		return platform.tfrCtrPersonnelBudget.initViewWindow(me.viewWindow,
				modulType, viewState, formRecord);
	},
	resultWindow : null,// 对应查看窗口
	getResultWindow : function(formRecord) {
		var me = this;
		if (Ext.isEmpty(me.resultWindow)) {
			me.resultWindow = Ext
					.create('Foss.platform.tfrCtrPersonnelBudget.resultWindow');
		}
		if (!Ext.isEmpty(formRecord)) {
			var form = me.resultWindow.editForm.getForm();
			form.loadRecord(formRecord);
		}
		return me.resultWindow;
	},
	exportWindow : null,// 对应导入弹出窗口
	getExportWindow : function() {
		var me = this;
		if (Ext.isEmpty(me.exportWindow)) {
			me.exportWindow = Ext
					.create('Foss.platform.tfrCtrPersonnelBudget.importWindow');
		}
		return me.exportWindow;
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.platform.tfrCtrPersonnelBudget.gridStore');
		me.tbar = [{
			xtype : 'button',
			text : '预算新增',
			handler : function() {
				me.getViewWindow(
						platform.tfrCtrPersonnelBudget.modulType.budget,
						platform.tfrCtrPersonnelBudget.viewState.add).show();
			}
		}, {
			xtype : 'button',
			text : '实际新增',
			handler : function() {
				me.getViewWindow(
						platform.tfrCtrPersonnelBudget.modulType.actual,
						platform.tfrCtrPersonnelBudget.viewState.add).show();
			}
		}, {
			xtype : 'button',
			text : '预算导入',
			handler : function() {
				me.getExportWindow().show();
			}
		}, {
			xtype : 'button',
			text : '导出',
			handler : function() {
				var form = Ext
						.getCmp('Foss_platform_tfrCtrPersonnelBudget_queryForm_id')
						.getForm(), queryParams = form.getValues();

				if (!form.isValid()) {
					Ext.ux.Toast.msg('提示', '请输入合法的导出条件！', 'error', 2000);
					return;
				}

				var params = {
					'tfrCtrPersonnelBudgetVo.tfrCtrPersonnelBudgetQcDto.transferCenterCode' : form
							.findField('transferCenter').getValue(),
					'tfrCtrPersonnelBudgetVo.tfrCtrPersonnelBudgetQcDto.effectiveDate' : queryParams.effectiveDate
				};

				if (!Ext.fly('downloadAttachFileForm')) {
					var frm = document.createElement('form');
					frm.id = 'downloadAttachFileForm';
					frm.style.display = 'none';
					document.body.appendChild(frm);
				}

				Ext.Ajax.request({
							url : platform
									.realPath('exportTfrCtrPersonnelBudgets.action'),
							form : Ext.fly('downloadAttachFileForm'),
							method : 'POST',
							params : params,
							isUpload : true,
							success : function(response) {

							},
							exception : function(response) {
								top.Ext.MessageBox
										.alert('导出失败', result.message);
							}
						});
			}
		}, '->', {
			xtype : 'button',
			text : '模版下载',
			iconCls : 'deppon_icons_downloadOnBtn',
			handler : function() {
				platform.tfrCtrPersonnelBudget.downloadTemplate();
			}
		}];
		me.columns = [{
			xtype : 'actioncolumn',
			width : 150,
			text : '操作',
			align : 'center',
			menuDisabled : true,
			hideable : false,
			sortable : false,
			items : [{
				tooltip : '预算修改',
				iconCls : 'deppon_icons_edit',
				handler : function(grid, rowIndex, colIndex) {
					var formRecord = grid.getStore().getAt(rowIndex);
					if (Ext.isEmpty(formRecord.get('idBudget'))) {
						Ext.ux.Toast.msg('提示', '当天没有外场预算人员信息！', 'info', 2000);
						return;
					}
					me.getViewWindow(
							platform.tfrCtrPersonnelBudget.modulType.budget,
							platform.tfrCtrPersonnelBudget.viewState.update,
							formRecord).show();
				}
			}, {
				tooltip : '实际修改',
				iconCls : 'deppon_icons_edit',
				handler : function(grid, rowIndex, colIndex) {
					var formRecord = grid.getStore().getAt(rowIndex);
					if (Ext.isEmpty(formRecord.get('idActual'))) {
						Ext.ux.Toast.msg('提示', '今天尚未新增实际人员信息，只能修改当天的实际人员信息！', 'info', 2000);
						return;
					}
					//实际修改只能修改当天的记录
					var startDate = formRecord.get('beginEffectiveDateActual'),
						startDateStr = startDate.getFullYear() + '' + startDate.getMonth() + '' + startDate.getDate();
					var nowDate = new Date(),
						nowDateStr = nowDate.getFullYear() + '' + nowDate.getMonth() + '' + nowDate.getDate();
					if(startDateStr !== nowDateStr){
						Ext.ux.Toast.msg('提示', '今天尚未新增实际人员信息，只能修改当天的实际人员信息！', 'info', 2000);
						return;
					}
					me.getViewWindow(
							platform.tfrCtrPersonnelBudget.modulType.actual,
							platform.tfrCtrPersonnelBudget.viewState.update,
							formRecord).show();
				}
			}, {
				tooltip : '查看',
				iconCls : 'deppon_icons_showdetail',
				handler : function(grid, rowIndex, colIndex) {
					var formRecord = grid.getStore().getAt(rowIndex);
					me.getResultWindow(formRecord).show();
				}
			}]
		}, {
			dataIndex : 'beginEffectiveDateBudget',
			align : 'center',
			width : 110,
			text : '生效日期',
			xtype : 'datecolumn',
			format : 'Y-m-d'
		}, {
			dataIndex : 'endEffectiveDateBudget',
			align : 'center',
			width : 110,
			text : '截至日期',
			xtype : 'datecolumn',
			format : 'Y-m-d'
		}, {
			dataIndex : 'transferCenterCode',
			align : 'center',
			width : 110,
			text : '外场编码'
		}, {
			dataIndex : 'transferCenterName',
			align : 'center',
			width : 120,
			text : '外场名称'
		}, {
			text : '总监',
			columns : [{
						dataIndex : 'directorBudget',
						align : 'center',
						text : '预算',
						width : 40
					}, {
						dataIndex : 'directorActual',
						align : 'center',
						text : '实际',
						width : 40
					}, {
						dataIndex : 'directorRemainder',
						align : 'center',
						text : '差额',
						width : 40
					}]
		}, {
			text : '总监统计员',
			columns : [{
						dataIndex : 'directorStatisticianBudget',
						align : 'center',
						text : '预算',
						width : 40
					}, {
						dataIndex : 'directorStatisticianActual',
						align : 'center',
						text : '实际',
						width : 40
					}, {
						dataIndex : 'directorStatisticianRemainder',
						align : 'center',
						text : '差额',
						width : 40
					}]
		}, {
			text : '高级经理',
			columns : [{
						dataIndex : 'seniorManagerBudget',
						align : 'center',
						text : '预算',
						width : 40
					}, {
						dataIndex : 'seniorManagerActual',
						align : 'center',
						text : '实际',
						width : 40
					}, {
						dataIndex : 'seniorManagerRemainder',
						align : 'center',
						text : '差额',
						width : 40
					}]
		}, {
			text : '经理',
			columns : [{
						dataIndex : 'managerBudget',
						align : 'center',
						text : '预算',
						width : 40
					}, {
						dataIndex : 'managerActual',
						align : 'center',
						text : '实际',
						width : 40
					}, {
						dataIndex : 'managerRemainder',
						align : 'center',
						text : '差额',
						width : 40
					}]
		}, {
			text : '副经理',
			columns : [{
						dataIndex : 'subManagerBudget',
						align : 'center',
						text : '预算',
						width : 40
					}, {
						dataIndex : 'subManagerActual',
						align : 'center',
						text : '实际',
						width : 40
					}, {
						dataIndex : 'subManagerRemainder',
						align : 'center',
						text : '差额',
						width : 40
					}]
		}, {
			text : '外场调度',
			columns : [{
						dataIndex : 'dispatchBudget',
						align : 'center',
						text : '预算',
						width : 40
					}, {
						dataIndex : 'dispatchActual',
						align : 'center',
						text : '实际',
						width : 40
					}, {
						dataIndex : 'dispatchRemainder',
						align : 'center',
						text : '差额',
						width : 40
					}]
		}, {
			text : '叉车安全员',
			columns : [{
						dataIndex : 'forkliftSecurityGuardBudget',
						align : 'center',
						text : '预算',
						width : 40
					}, {
						dataIndex : 'forkliftSecurityGuardActual',
						align : 'center',
						text : '实际',
						width : 40
					}, {
						dataIndex : 'forkliftSecurityGuardRemainder',
						align : 'center',
						text : '差额',
						width : 40
					}]
		}, {
			text : '货区管理员',
			columns : [{
						dataIndex : 'goodsAreaGuardBudget',
						align : 'center',
						text : '预算',
						width : 40
					}, {
						dataIndex : 'goodsAreaGuardActual',
						align : 'center',
						text : '实际',
						width : 40
					}, {
						dataIndex : 'goodsAreaGuardRemainder',
						align : 'center',
						text : '差额',
						width : 40
					}]
		}, {
			text : '货台管理员',
			columns : [{
						dataIndex : 'platformGuardBudget',
						align : 'center',
						text : '预算',
						width : 40
					}, {
						dataIndex : 'platformGuardActual',
						align : 'center',
						text : '实际',
						width : 40
					}, {
						dataIndex : 'platformGuardRemainder',
						align : 'center',
						text : '差额',
						width : 40
					}]
		}, {
			text : '文职',
			columns : [{
						dataIndex : 'civilServiceBudget',
						align : 'center',
						text : '预算',
						width : 40
					}, {
						dataIndex : 'civilServiceActual',
						align : 'center',
						text : '实际',
						width : 40
					}, {
						dataIndex : 'civilServiceRemainder',
						align : 'center',
						text : '差额',
						width : 40
					}]
		}, {
			text : '偏线外发员',
			columns : [{
						dataIndex : 'partialMemberBudget',
						align : 'center',
						text : '预算',
						width : 40
					}, {
						dataIndex : 'partialMemberActual',
						align : 'center',
						text : '实际',
						width : 40
					}, {
						dataIndex : 'partialMemberRemainder',
						align : 'center',
						text : '差额',
						width : 40
					}]
		}, {
			text : '理货员(含组长)',
			columns : [{
						dataIndex : 'tallyClerkBudget',
						align : 'center',
						text : '预算',
						width : 40
					}, {
						dataIndex : 'tallyClerkActual',
						align : 'center',
						text : '实际',
						width : 40
					}, {
						dataIndex : 'tallyClerkRemainder',
						align : 'center',
						text : '差额',
						width : 40
					}]
		}, {
			text : '电叉司机(含组长)',
			columns : [{
						dataIndex : 'electricForkliftDriverBudget',
						align : 'center',
						text : '预算',
						width : 40
					}, {
						dataIndex : 'electricForkliftDriverActual',
						align : 'center',
						text : '实际',
						width : 40
					}, {
						dataIndex : 'electricForkliftDriverRemainder',
						align : 'center',
						text : '差额',
						width : 40
					}]
		}, {
			text : '机叉司机',
			columns : [{
						dataIndex : 'machineForkliftDriverBudget',
						align : 'center',
						text : '预算',
						width : 40
					}, {
						dataIndex : 'machineForkliftDriverActual',
						align : 'center',
						text : '实际',
						width : 40
					}, {
						dataIndex : 'machineForkliftDriverRemainder',
						align : 'center',
						text : '差额',
						width : 40
					}]
		}, {
			text : '木工',
			columns : [{
						dataIndex : 'carpenterBudget',
						align : 'center',
						text : '预算',
						width : 40
					}, {
						dataIndex : 'carpenterActual',
						align : 'center',
						text : '实际',
						width : 40
					}, {
						dataIndex : 'carpenterRemainder',
						align : 'center',
						text : '差额',
						width : 40
					}]
		}, {
			text : '修理工',
			columns : [{
						dataIndex : 'repairerBudget',
						align : 'center',
						text : '预算',
						width : 40
					}, {
						dataIndex : 'repairerActual',
						align : 'center',
						text : '实际',
						width : 40
					}, {
						dataIndex : 'repairerRemainder',
						align : 'center',
						text : '差额',
						width : 40
					}]
		}, {
			text : '分拣员',
			columns : [{
						dataIndex : 'sorterBudget',
						align : 'center',
						text : '预算',
						width : 40
					}, {
						dataIndex : 'sorterActual',
						align : 'center',
						text : '实际',
						width : 40
					}, {
						dataIndex : 'sorterRemainder',
						align : 'center',
						text : '差额',
						width : 40
					}]
		}, {
			text : '其他',
			columns : [{
						dataIndex : 'otherBudget',
						align : 'center',
						text : '预算',
						width : 40
					}, {
						dataIndex : 'otherActual',
						align : 'center',
						text : '实际',
						width : 40
					}, {
						dataIndex : 'otherRemainder',
						align : 'center',
						text : '差额',
						width : 40
					}]
		}, {
			text : '合计',
			columns : [{
						dataIndex : 'totalQtyBudget',
						align : 'center',
						text : '预算',
						width : 40
					}, {
						dataIndex : 'totalQtyActual',
						align : 'center',
						text : '实际',
						width : 40
					}, {
						dataIndex : 'totalQtyRemainder',
						align : 'center',
						text : '差额',
						width : 40
					}]
		}, {
			dataIndex : 'dimission',
			align : 'center',
			text : '离职数',
			width : 45
		}, {
			dataIndex : 'dimissionAccumulated',
			align : 'center',
			text : '月累计离职数'
		}];
		me.callParent([cfg]);
		platform.tfrCtrPersonnelBudget.resultGrid = me;
	}
});

Ext.define('Foss.platform.tfrCtrPersonnelBudget.viewForm', {
			extend : 'Ext.form.Panel',
			frame : true,
			collapsible : false,
			animCollapse : false,
			defaults : {
				margin : '5 10 5 10',
				labelWidth : 85,
				columnWidth : 1 / 2,
				xtype : 'numberfield'
			},
			layout : 'column',
			modulType : null,// 模块类别，预算、实际；对应platform.tfrCtrPersonnelBudget.modulType
			viewState : null,// 状态，新增、修改、查看；对应platform.tfrCtrPersonnelBudget.viewState
			items : [{
						fieldLabel : 'id',
						xtype : 'textfield',
						name : 'id',
						hidden : true
					}, {
						fieldLabel : '修改时间',
						xtype : 'numberfield',
						name : 'modifyTime',
						hidden : true
					}, {
						fieldLabel : '外场名称',
						name : 'transferCenter',
						xtype : 'dynamicorgcombselector',
						types : 'ORG',
						transferCenter : 'Y',
						allowBlank : false,
						listeners : {
							change : function(field, newValue, oldValue) {
								if (newValue !== oldValue) {
									this.up('form').getForm()
											.findField('transferCenterCode')
											.setValue(newValue);
								}
							}
						}
					}, {
						xtype : 'textfield',
						fieldLabel : '外场编码',
						name : 'transferCenterCode',
						allowBlank : false,
						readOnly : true
					}, {
						fieldLabel : '总监',
						name : 'director',
						allowDecimals : false,
						allowBlank : false,
						minValue : 0,
						maxValue : 10000
					}, {
						fieldLabel : '总监统计员',
						name : 'directorStatistician',
						allowDecimals : false,
						allowBlank : false,
						minValue : 0,
						maxValue : 10000
					}, {
						fieldLabel : '高级经理',
						name : 'seniorManager',
						allowDecimals : false,
						allowBlank : false,
						minValue : 0,
						maxValue : 10000
					}, {
						fieldLabel : '经理',
						name : 'manager',
						allowDecimals : false,
						allowBlank : false,
						minValue : 0,
						maxValue : 10000
					}, {
						fieldLabel : '副经理',
						name : 'subManager',
						allowDecimals : false,
						allowBlank : false,
						minValue : 0,
						maxValue : 10000
					}, {
						fieldLabel : '外场调度',
						name : 'dispatch',
						allowDecimals : false,
						allowBlank : false,
						minValue : 0,
						maxValue : 10000
					}, {
						fieldLabel : '叉车安全员',
						name : 'forkliftSecurityGuard',
						allowDecimals : false,
						allowBlank : false,
						minValue : 0,
						maxValue : 10000
					}, {
						fieldLabel : '货区管理员',
						name : 'goodsAreaGuard',
						allowDecimals : false,
						allowBlank : false,
						minValue : 0,
						maxValue : 10000
					}, {
						fieldLabel : '货台管理员',
						name : 'platformGuard',
						allowDecimals : false,
						allowBlank : false,
						minValue : 0,
						maxValue : 10000
					}, {
						fieldLabel : '文职',
						name : 'civilService',
						allowDecimals : false,
						allowBlank : false,
						minValue : 0,
						maxValue : 10000
					}, {
						fieldLabel : '偏线外发员',
						name : 'partialMember',
						allowDecimals : false,
						allowBlank : false,
						minValue : 0,
						maxValue : 10000
					}, {
						fieldLabel : '理货员(含组长)',
						name : 'tallyClerk',
						allowDecimals : false,
						allowBlank : false,
						minValue : 0,
						maxValue : 10000
					}, {
						fieldLabel : '电叉司机(含组长)',
						name : 'electricForkliftDriver',
						allowDecimals : false,
						allowBlank : false,
						minValue : 0,
						maxValue : 10000
					}, {
						fieldLabel : '机叉司机',
						name : 'machineForkliftDriver',
						allowDecimals : false,
						allowBlank : false,
						minValue : 0,
						maxValue : 10000
					}, {
						fieldLabel : '木工',
						name : 'carpenter',
						allowDecimals : false,
						allowBlank : false,
						minValue : 0,
						maxValue : 10000
					}, {
						fieldLabel : '修理工',
						name : 'repairer',
						allowDecimals : false,
						allowBlank : false,
						minValue : 0,
						maxValue : 10000
					}, {
						fieldLabel : '分拣员',
						name : 'sorter',
						allowDecimals : false,
						allowBlank : false,
						minValue : 0,
						maxValue : 10000
					}, {
						fieldLabel : '其他',
						name : 'other',
						allowDecimals : false,
						allowBlank : false,
						minValue : 0,
						maxValue : 10000
					}, {
						xtype : 'datefield',
						fieldLabel : '生效日期',
						name : 'beginEffectiveDate',
						allowBlank : false,
						format : 'Y-m-d'
					}, {
						xtype : 'datefield',
						fieldLabel : '生效截至日期',
						name : 'endEffectiveDate',
						hidden : true
					}, {
						fieldLabel : '离职数量',
						name : 'dimission',
						allowDecimals : false,
						minValue : 0,
						maxValue : 10000
					}]
		});

Ext.define('Foss.platform.tfrCtrPersonnelBudget.viewWindow', {
	extend : 'Ext.window.Window',
	closable : true,
	modal : true,
	resizable : false,
	closeAction : 'hide',
	width : 650,
	height : 560,
	editForm : null,// 对应Foss.platform.tfrCtrPersonnelBudget.viewForm，
	modulType : null,// 模块类别，预算、实际；对应platform.tfrCtrPersonnelBudget.modulType
	viewState : null,// 状态，新增、修改、查看；对应platform.tfrCtrPersonnelBudget.viewState
	sourceGrid : null,// 对应Foss.platform.tfrCtrPersonnelBudget.tfrCtrPersonnelBudgetGrid
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	resetData : function() {
		var me = this, form = me.editForm.getForm();
		form.reset();
	},
	initData : function() {
		var me = this;
		if (me.viewState === platform.tfrCtrPersonnelBudget.viewState.add) {
			var form = me.editForm.getForm();
			if (platform.tfrCtrPersonnelBudget.parentTfrCtrCode) {
				form.findField('transferCenter').setCombValue(
						platform.tfrCtrPersonnelBudget.parentTfrCtrName,
						platform.tfrCtrPersonnelBudget.parentTfrCtrCode);
				form
						.findField('transferCenterCode')
						.setValue(platform.tfrCtrPersonnelBudget.parentTfrCtrCode);
				form.findField('beginEffectiveDate').setValue(new Date());
			}
		}
	},
	listeners : {
		beforehide : function(me) {
			me.resetData();
		},
		beforeshow : function(me) {
			me.initData();
		}
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.fbar = [{
					text : '取消',
					handler : function() {
						me.hide();
					}
				}, {
					text : '复制 ',
					handler : function() {
						if (me.viewState === platform.tfrCtrPersonnelBudget.viewState.add) {
							var form = me.editForm.getForm();
							var transferCenterCode = form
									.findField('transferCenter').getValue();
							if (Ext.isEmpty(transferCenterCode)) {
								Ext.ux.Toast.msg('提示', '请先选择外场！', 'info', 2000);
								return;
							}
							var record = platform.tfrCtrPersonnelBudget
									.createFormModel(me.modulType,
											me.viewState, null,
											transferCenterCode);

							if (!Ext.isEmpty(record)) {
								form.loadRecord(record)
								if (me.modulType === platform.tfrCtrPersonnelBudget.modulType.actual) {
									me.initData();
								} else if (me.modulType === platform.tfrCtrPersonnelBudget.modulType.budget) {
									form
											.findField('transferCenter')
											.setCombValue(
													record
															.get('transferCenterName'),
													record
															.get('transferCenterCode'));
								}
							}
						}
					}
				}, {
					text : '重置',
					handler : function() {
						me.resetData();
						me.initData();
					}
				}, {
					text : '保存',
					cls : 'yellow_button',
					handler : function() {
						platform.tfrCtrPersonnelBudget
								.submitTfrCtrPersonnelBudget(me);

					}
				}];
		me.viewState = config.viewState;
		me.modulType = config.modulType;
		me.editForm = Ext
				.create('Foss.platform.tfrCtrPersonnelBudget.viewForm');
		me.items = [me.editForm];
		me.callParent([cfg]);
	}
});

Ext.define('Foss.platform.tfrCtrPersonnelBudget.resultForm', {
			extend : 'Ext.form.Panel',
			frame : true,
			collapsible : false,
			animCollapse : false,
			defaults : {
				margin : '5 10 5 0',
				labelWidth : 85,
				columnWidth : .25,
				xtype : 'textfield'
			},
			layout : 'column',
			items : [{
						xtype : 'label',
						text : '职位'
					}, {
						xtype : 'label',
						text : '预算'
					}, {
						xtype : 'label',
						text : '实际'
					}, {
						xtype : 'label',
						text : '差额'
					}, {
						xtype : 'label',
						text : '总监'
					}, {
						hideLabel : true,
						readOnly : true,
						name : 'directorBudget'
					}, {
						hideLabel : true,
						readOnly : true,
						name : 'directorActual'
					}, {
						hideLabel : true,
						readOnly : true,
						name : 'directorRemainder'
					}, {
						xtype : 'label',
						text : '总监统计员'
					}, {
						hideLabel : true,
						readOnly : true,
						name : 'directorStatisticianBudget'
					}, {
						hideLabel : true,
						readOnly : true,
						name : 'directorStatisticianActual'
					}, {
						hideLabel : true,
						readOnly : true,
						name : 'directorStatisticianRemainder'
					}, {
						xtype : 'label',
						text : '高级经理'
					}, {
						hideLabel : true,
						readOnly : true,
						name : 'seniorManagerBudget'
					}, {
						hideLabel : true,
						readOnly : true,
						name : 'seniorManagerActual'
					}, {
						hideLabel : true,
						readOnly : true,
						name : 'seniorManagerRemainder'
					}, {
						xtype : 'label',
						text : '经理'
					}, {
						hideLabel : true,
						readOnly : true,
						name : 'managerBudget'
					}, {
						hideLabel : true,
						readOnly : true,
						name : 'managerActual'
					}, {
						hideLabel : true,
						readOnly : true,
						name : 'managerRemainder'
					}, {
						xtype : 'label',
						text : '副经理'
					}, {
						hideLabel : true,
						readOnly : true,
						name : 'subManagerBudget'
					}, {
						hideLabel : true,
						readOnly : true,
						name : 'subManagerActual'
					}, {
						hideLabel : true,
						readOnly : true,
						name : 'subManagerRemainder'
					}, {
						xtype : 'label',
						text : '外场调度'
					}, {
						hideLabel : true,
						readOnly : true,
						name : 'dispatchBudget'
					}, {
						hideLabel : true,
						readOnly : true,
						name : 'dispatchActual'
					}, {
						hideLabel : true,
						readOnly : true,
						name : 'dispatchRemainder'
					}, {
						xtype : 'label',
						text : '叉车安全员'
					}, {
						hideLabel : true,
						readOnly : true,
						name : 'forkliftSecurityGuardBudget'
					}, {
						hideLabel : true,
						readOnly : true,
						name : 'forkliftSecurityGuardActual'
					}, {
						hideLabel : true,
						readOnly : true,
						name : 'forkliftSecurityGuardRemainder'
					}, {
						xtype : 'label',
						text : '货区管理员'
					}, {
						hideLabel : true,
						readOnly : true,
						name : 'goodsAreaGuardBudget'
					}, {
						hideLabel : true,
						readOnly : true,
						name : 'goodsAreaGuardActual'
					}, {
						hideLabel : true,
						readOnly : true,
						name : 'goodsAreaGuardRemainder'
					}, {
						xtype : 'label',
						text : '货台管理员'
					}, {
						hideLabel : true,
						readOnly : true,
						name : 'platformGuardBudget'
					}, {
						hideLabel : true,
						readOnly : true,
						name : 'platformGuardActual'
					}, {
						hideLabel : true,
						readOnly : true,
						name : 'platformGuardRemainder'
					}, {
						xtype : 'label',
						text : '文职'
					}, {
						hideLabel : true,
						readOnly : true,
						name : 'civilServiceBudget'
					}, {
						hideLabel : true,
						readOnly : true,
						name : 'civilServiceActual'
					}, {
						hideLabel : true,
						readOnly : true,
						name : 'civilServiceRemainder'
					}, {
						xtype : 'label',
						text : '偏线外发员'
					}, {
						hideLabel : true,
						readOnly : true,
						name : 'partialMemberBudget'
					}, {
						hideLabel : true,
						readOnly : true,
						name : 'partialMemberActual'
					}, {
						hideLabel : true,
						readOnly : true,
						name : 'partialMemberRemainder'
					}, {
						xtype : 'label',
						text : '理货员(含组长)'
					}, {
						hideLabel : true,
						readOnly : true,
						name : 'tallyClerkBudget'
					}, {
						hideLabel : true,
						readOnly : true,
						name : 'tallyClerkActual'
					}, {
						hideLabel : true,
						readOnly : true,
						name : 'tallyClerkRemainder'
					}, {
						xtype : 'label',
						text : '电叉司机(含组长)',
						labelWidth : 95
						,
					}, {
						hideLabel : true,
						readOnly : true,
						name : 'electricForkliftDriverBudget'
					}, {
						hideLabel : true,
						readOnly : true,
						name : 'electricForkliftDriverActual'
					}, {
						hideLabel : true,
						readOnly : true,
						name : 'electricForkliftDriverRemainder'
					}, {
						xtype : 'label',
						text : '机叉司机'
					}, {
						hideLabel : true,
						readOnly : true,
						name : 'machineForkliftDriverBudget'
					}, {
						hideLabel : true,
						readOnly : true,
						name : 'machineForkliftDriverActual'
					}, {
						hideLabel : true,
						readOnly : true,
						name : 'machineForkliftDriverRemainder'
					}, {
						xtype : 'label',
						text : '木工'
					}, {
						hideLabel : true,
						readOnly : true,
						name : 'carpenterBudget'
					}, {
						hideLabel : true,
						readOnly : true,
						name : 'carpenterActual'
					}, {
						hideLabel : true,
						readOnly : true,
						name : 'carpenterRemainder'
					}, {
						xtype : 'label',
						text : '修理工'
					}, {
						hideLabel : true,
						readOnly : true,
						name : 'repairerBudget'
					}, {
						hideLabel : true,
						readOnly : true,
						name : 'repairerActual'
					}, {
						hideLabel : true,
						readOnly : true,
						name : 'repairerRemainder'
					}, {
						xtype : 'label',
						text : '分拣员'
					}, {
						hideLabel : true,
						readOnly : true,
						name : 'sorterBudget'
					}, {
						hideLabel : true,
						readOnly : true,
						name : 'sorterActual'
					}, {
						hideLabel : true,
						readOnly : true,
						name : 'sorterRemainder'
					}, {
						xtype : 'label',
						text : '其他'
					}, {
						hideLabel : true,
						readOnly : true,
						name : 'otherBudget'
					}, {
						hideLabel : true,
						readOnly : true,
						name : 'otherActual'
					}, {
						hideLabel : true,
						readOnly : true,
						name : 'otherRemainder'
					}, {
						xtype : 'label',
						text : '合计'
					}, {
						hideLabel : true,
						readOnly : true,
						name : 'totalQtyBudget'
					}, {
						hideLabel : true,
						readOnly : true,
						name : 'totalQtyActual'
					}, {
						hideLabel : true,
						readOnly : true,
						name : 'totalQtyRemainder'
					}, {
						xtype : 'label',
						text : '离职数量'
					}, {
						hideLabel : true,
						readOnly : true,
						name : 'dimission'
					}, {
						xtype : 'label',
						text : '月累计离职数'
					}, {
						hideLabel : true,
						readOnly : true,
						name : 'dimissionAccumulated'
					}]
		});

Ext.define('Foss.platform.tfrCtrPersonnelBudget.resultWindow', {
			extend : 'Ext.window.Window',
			title : '查看人员预算/实际信息',
			closable : true,
			modal : true,
			resizable : false,
			closeAction : 'hide',
			width : 550,
			height : 840,
			editForm : null,// 对应Foss.platform.tfrCtrPersonnelBudget.resultForm
			layout : {
				type : 'vbox',
				align : 'stretch'
			},
			listeners : {
				beforehide : function(me) {
					var me = this, form = me.editForm.getForm();
					form.reset()
				}
			},
			constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				me.fbar = [{
							text : '关闭',
							handler : function() {
								me.hide();
							}
						}];
				me.editForm = Ext
						.create('Foss.platform.tfrCtrPersonnelBudget.resultForm');
				me.items = [me.editForm];
				me.callParent([cfg]);
			}
		});

// 导入表单
Ext.define('Foss.platform.tfrCtrPersonnelBudget.importForm', {
	extend : 'Ext.form.Panel',
	cls : 'autoHeight',
	defaultType : 'textfield',
	layout : 'column',
	defaults : {
		margin : '5 5 5 5',
		anchor : '98%',
		labelWidth : 90
	},
	standardSubmit : true,
	items : [{
				xtype : 'filefield',
				name : 'uploadFile',
				readOnly : false,
				buttonOnly : false,
				fieldLabel : '请选择附件',
				msgTarget : 'side',
				cls : 'uploadFile',
				allowBlank : false,
				buttonText : '浏览',
				columnWidth : 1
			}, {
				readOnly : true,
				columnWidth : .6
			}, {
				columnWidth : .2,
				xtype : 'button',
				text : '导入',
				handler : function() {
					var form = this.up('form').getForm();
					if (form.isValid()) {
						var myMask = new Ext.LoadMask(this.up('form'), {
									msg : '导入中，请稍后...'
								});
						myMask.show();

						form.submit({
							url : platform
									.realPath('importTfrCtrPersonnelBudgets.action'),
							success : function(form, action) {
								myMask.hide();
								Ext.ux.Toast.msg('提示', '导入成功', 'info', 1000);
							},
							exception : function(form, action) {
								myMask.hide();
								Ext.ux.Toast.msg('提示', '导入失败，'
												+ action.result.message,
										'error', 2500);
							},
							failure : function(form, action) {
								myMask.hide();
								Ext.ux.Toast.msg('提示', '导入失败，'
												+ action.result.message,
										'error', 2500);
							}
						});
					}
				}
			}, {
				xtype : 'button',
				columnWidth : .2,
				text : '取消',
				handler : function() {
					this.up('window').hide();
				}
			}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
})

// 导入窗口
Ext.define('Foss.platform.tfrCtrPersonnelBudget.importWindow', {
			extend : 'Ext.window.Window',
			title : '导入外场人员预算信息',
			modal : true,
			closeAction : 'hide',
			width : 550,
			bodyCls : 'autoHeight',
			layout : 'auto',
			listeners : {
				beforehide : function(comp, eOpts) {
					this.down('form').getForm().findField('uploadFile').reset();
				}
			},
			items : [Ext
					.create('Foss.platform.tfrCtrPersonnelBudget.importForm')]
		});

// 入口函数
Ext.onReady(function() {
	// 创建查询form
	var queryForm = Ext.create('Foss.platform.tfrCtrPersonnelBudget.queryForm');
	// 创建查询grid
	var resultGird = Ext
			.create('Foss.platform.tfrCtrPersonnelBudget.resultGrid');

	var buttons = resultGird.query('button');

	// 判断当前部门是否外场或外场子部门，对查询form外场控件初始化
	if (platform.tfrCtrPersonnelBudget.parentTfrCtrCode) {
		var transferCenterCmp = queryForm.getForm().findField('transferCenter');

		transferCenterCmp.setReadOnly(true);
		transferCenterCmp.setCombValue(
				platform.tfrCtrPersonnelBudget.parentTfrCtrName,
				platform.tfrCtrPersonnelBudget.parentTfrCtrCode);

		for (var i = 0; i < buttons.length; i++) {
			buttons[i].setVisible(i === 1 || i === 3);
		}
		// 预算修改按钮不可见
		resultGird.columns[0].items[0].disabled = true;
	} else {
		for (var i = 0; i < buttons.length; i++) {
			buttons[i].setVisible(i !== 1);
		}
		// 实际修改按钮不可见
		resultGird.columns[0].items[1].disabled = true;
	}

	Ext.create('Ext.panel.Panel', {
				id : 'T_platform-tfrCtrPersonnelBudgetIndex_content',
				cls : "panelContentNToolbar",
				bodyCls : 'panelContent-body',
				layout : 'auto',
				items : [queryForm, resultGird],
				renderTo : 'T_platform-tfrCtrPersonnelBudgetIndex-body'
			});
});