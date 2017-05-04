/**
 * 外请车约车查询
 * 
 * 1、查询页面Panel： T_scheduling-inviteVehicleIndex_content
 * 1.1、查询条件：Foss.scheduling.inviteVehicle.InviteVehicleApplyQueryForm
 * 1.1.1、Store：Foss.scheduling.inviteVehicle.InviteVehicleApplyQueryStore
 * 1.2、查询结果：Foss.scheduling.inviteVehicle.InviteVehicleApplyQueryGrid
 * 
 */

Ext
		.define(
				'Foss.scheduling.inviteVehicle.InviteVehicleApplyQueryForm.useStatus.store',
				{
					extend : 'Ext.data.Store',
					fields : ['value', 'name'],
					data : [{
								"value" : "",
								"name" : ''
							}, {
								"value" : "USING",
								"name" : scheduling.inviteVehicle.i18n('Foss.scheduling.inviteVehicle.useStatus.using')//'已使用'
							}, {
								"value" : "UNUSED",
								"name" : scheduling.inviteVehicle.i18n('Foss.scheduling.inviteVehicle.useStatus.unused')//'未使用'
							}]
				});

// 查询条件
Ext.define('Foss.scheduling.inviteVehicle.InviteVehicleApplyQueryForm', {
	extend : 'Ext.form.Panel',
	title : scheduling.inviteVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyQueryForm.title'), // 查询条件
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
		name : 'usePurpose',
		fieldLabel : scheduling.inviteVehicle
				.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyQueryForm.usePurpose'), // 使用类型
		xtype : 'combobox',
		store : Ext.create('Ext.data.Store', {
			fields : ['valueName', 'valueCode'],
			data : [{
				'valueName' : scheduling.inviteVehicle
						.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.inviteVehicleApplyInfo.usePurpose.samecityorder'),
				'valueCode' : 'SAMECITY_ORDER'
			},		// 同城外租车
			        {
				'valueName' : scheduling.inviteVehicle
						.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyQueryForm.usePurpose.store.inviteorder'),
				'valueCode' : 'INVITE_ORDER'
			},		// 外请车约车
			{
				'valueName' : scheduling.inviteVehicle
						.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.inviteVehicleApplyInfo.usePurpose.wholecarorder'),
				'valueCode' : 'WHOLECAR_ORDER'
			}		// 整车约车
			]
		}),
		queryMode : 'local', // 本地
		displayField : 'valueName',
		valueField : 'valueCode',
		editable : false,
		forceSelection : true,
		triggerAction : 'all',
		columnWidth : .25,
		listeners : {
			select : {
				fn : function(combo, records, eOpts) {
					var form = scheduling.inviteVehicle.inviteVehicleApplyQueryForm
							.getForm();
					var useType = form.findField('useType');
					useType.reset();
					var useTypeArray = new Array();
					useTypeArray.push({
						'valueName' : scheduling.inviteVehicle
								.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyQueryForm.useType.store.tosalesdepart'),
						'valueCode' : 'TO_SALES_DEPARTMENT'
					}); // 到营业部
					useTypeArray.push({
						'valueName' : scheduling.inviteVehicle
								.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyQueryForm.useType.store.toclient'),
						'valueCode' : 'TO_CLIENT'
					}); // 到客户处
					// 外请车约车
					var record = records[0];
					var usePurpose = record.get('valueCode');
					if (usePurpose == 'INVITE_ORDER'|| usePurpose == 'SAMECITY_ORDER') {
						useTypeArray.push({
							'valueName' : scheduling.inviteVehicle
									.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyQueryForm.useType.store.totransit'),
							'valueCode' : 'TO_TRANSIT'
						}); // 到中转场
					}
					useType.store.loadData(useTypeArray);
				}
			}
		}
	}, {
		name : 'useType',
		fieldLabel : scheduling.inviteVehicle
				.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyQueryForm.useType'), // 用车类型
		xtype : 'combobox',
		store : Ext.create('Ext.data.Store', {
			fields : ['valueName', 'valueCode'],
			data : [{
				'valueName' : scheduling.inviteVehicle
						.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyQueryForm.useType.store.tosalesdepart'),
				'valueCode' : 'TO_SALES_DEPARTMENT'
			},		// 到营业部
			{
				'valueName' : scheduling.inviteVehicle
						.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyQueryForm.useType.store.toclient'),
				'valueCode' : 'TO_CLIENT'
			},		// 到客户处
			{
				'valueName' : scheduling.inviteVehicle
						.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyQueryForm.useType.store.totransit'),
				'valueCode' : 'TO_TRANSIT'
			}		// 到中转场
			]
		}),
		queryMode : 'local',
		displayField : 'valueName',
		valueField : 'valueCode',
		editable : false,
		forceSelection : true,
		triggerAction : 'all',
		columnWidth : .25
	}, {
		name : 'beginInviteCost',
		fieldLabel : scheduling.inviteVehicle
				.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyQueryForm.beginInviteCost'), // 请车价格
		regex : /^-?\d+\.?\d{0,10}$/,
		regexText : scheduling.inviteVehicle
				.i18n('Foss.scheduling.validation.tip.number'), // 格式输入有误.必须是数字!
		columnWidth : .25,
		validator : function(value) {
			if(value.length > 11){
				return scheduling.inviteVehicle.i18n('Foss.PassInviteVehicleIndex.Grid.Tip.CarInfo.maxLengthText');
			}
			return true;
		}
	}, {
		name : 'endInviteCost',
		fieldLabel : '-',
		regex : /^-?\d+\.?\d{0,10}$/,
		regexText : scheduling.inviteVehicle
				.i18n('Foss.scheduling.validation.tip.number'), // 格式输入有误.必须是数字!
		columnWidth : .25,
		validator : function(value) {
			if(value.length > 11){
				return scheduling.inviteVehicle.i18n('Foss.PassInviteVehicleIndex.Grid.Tip.CarInfo.maxLengthText');
			}
			return true;
		}
	}, {
		name : 'orderVehicleModel',
		xtype : 'commonvehicletypeselector',
		fieldLabel : scheduling.inviteVehicle
				.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyQueryForm.orderVehicleModel'), // 用车车型
		columnWidth : .25
	}, {
		name : 'vehicleNo',
		xtype : 'commonleasedvehicleselector',
		fieldLabel : scheduling.inviteVehicle
				.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyQueryForm.vehicleNo'), // 安排车牌号
		columnWidth : .25
	}, {
		xtype : 'rangeDateField',
		dateType : 'datetimefield_date97',
		fieldLabel : scheduling.inviteVehicle
				.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyQueryForm.auditTime'), // 受理时间
		fieldId : 'Foss_invitetruck_InviteVehicleApplyQueryForm_AuditTime_ID',
		fromName : 'beginAuditTime',
		toName : 'endAuditTime',
		columnWidth : .5
	}, {
		name : 'inviteNo',
		fieldLabel : scheduling.inviteVehicle
				.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyQueryForm.inviteNo'), // 申请编号
		columnWidth : .25
	}, {
		name : 'applyOrgCode',
		xtype : 'dynamicorgcombselector',
		type : 'ORG',
		salesDepartment : 'Y',// 查询营业部 配置此值
		transferCenter : 'Y',// 查询外场 配置此值
		doAirDispatch : 'Y',// 查询空运配载 配置此值
		transDepartment : 'Y',// 查询车队 配置此值
		dispatchTeam : 'Y',// 查询车队调度组 配置此值
		transTeam : 'Y',// 查询车队组 配置此值
		fieldLabel : scheduling.inviteVehicle
				.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyQueryForm.applyOrgCode'), // 用车部门
		columnWidth : .25
	}, {
		xtype : 'rangeDateField',
		dateType : 'datetimefield_date97',
		fieldLabel : scheduling.inviteVehicle
				.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyQueryForm.predictUseTime'), // 用车时间
		fieldId : 'Foss_invitetruck_InviteVehicleApplyQueryForm_PredictUseTime_ID',
		fromName : 'beginPredictUseTime',
		toName : 'endPredictUseTime',
		/*BUG-15538 -> BUG-15541*/
		fromValue: Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth()-1,new Date().getDate()
				,'00','00','00'), 'Y-m-d H:i:s'),
		toValue: Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth()+1,new Date().getDate()
		,'23','59','59'), 'Y-m-d H:i:s'),
		columnWidth : .5
	}, {
		name : 'status',
		fieldLabel : scheduling.inviteVehicle
				.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyQueryForm.status'), // 操作状态
		xtype : 'combobox',
		store : FossDataDictionary
				.getDataDictionaryStore('INVITEVEHICLE_STATUS'),
		queryMode : 'local',
		displayField : 'valueName',
		valueField : 'valueCode',
		editable : false,
		forceSelection : true,
		triggerAction : 'all',
		columnWidth : .25
	}, {
		name : 'isReturn',
		fieldLabel : scheduling.inviteVehicle
				.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyQueryForm.isReturn'), // 是否回程
		xtype : 'combobox',
		store : Ext.create('Ext.data.Store', {
			fields : ['valueName', 'valueCode'],
			data : [{
				'valueName' : scheduling.inviteVehicle
						.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyQueryForm.isReturn.store.all'),
				'valueCode' : ''
			},		// 全部
			{
				'valueName' : scheduling.inviteVehicle
						.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyQueryForm.isReturn.store.yes'),
				'valueCode' : 'Y'
			},		// 是
			{
				'valueName' : scheduling.inviteVehicle
						.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyQueryForm.isReturn.store.no'),
				'valueCode' : 'N'
			}		// 否
			]
		}),
		queryMode : 'local',
		displayField : 'valueName',
		valueField : 'valueCode',
		editable : false,
		forceSelection : true,
		triggerAction : 'all',
		columnWidth : .25
	}, {
		name : 'beginReturnCostStr',
		fieldLabel : scheduling.inviteVehicle
				.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyQueryForm.beginReturnCostStr'), // 回程价格
		regex : /^-?\d+\.?\d{0,10}$/,
		regexText : scheduling.inviteVehicle
				.i18n('Foss.scheduling.validation.tip.number'), // 格式输入有误.必须是数字!
		columnWidth : .25,
		validator : function(value) {
			if(value.length > 11){
				return scheduling.inviteVehicle.i18n('Foss.PassInviteVehicleIndex.Grid.Tip.CarInfo.maxLengthText');
			}
			return true;
		}
	}, {
		name : 'endReturnCostStr',
		fieldLabel : '-',
		regex : /^-?\d+\.?\d{0,10}$/,
		regexText : scheduling.inviteVehicle
				.i18n('Foss.scheduling.validation.tip.number'), // 格式输入有误.必须是数字!
		columnWidth : .25,
		validator : function(value) {
			if(value.length > 11){
				return scheduling.inviteVehicle.i18n('Foss.PassInviteVehicleIndex.Grid.Tip.CarInfo.maxLengthText');
			}
			return true;
		}
	}, {
		name : 'isContractVehicle',
		fieldLabel : scheduling.inviteVehicle
				.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyQueryForm.isContractVehicle'), // 是否合同车
		xtype : 'combobox',
		store : Ext.create('Ext.data.Store', {
			fields : ['valueName', 'valueCode'],
			data : [{
				'valueName' : scheduling.inviteVehicle
						.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyQueryForm.isContractVehicle.store.all'),
				'valueCode' : ''
			},		// 全部
			{
				'valueName' : scheduling.inviteVehicle
						.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyQueryForm.isContractVehicle.store.yes'),
				'valueCode' : 'Y'
			},		// 是
			{
				'valueName' : scheduling.inviteVehicle
						.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyQueryForm.isContractVehicle.store.no'),
				'valueCode' : 'N'
			}		// 否
			]
		}),
		queryMode : 'local',
		displayField : 'valueName',
		valueField : 'valueCode',
		editable : false,
		forceSelection : true,
		triggerAction : 'all',
		columnWidth : .25
	}, {
		name : 'contractLineCode',
		xtype : 'commonlineselector',
		fieldLabel : scheduling.inviteVehicle
				.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyQueryForm.contractLineCode'), // 合同线路
		columnWidth : .25
	}, {
		name : 'useStatus',
		xtype : 'combo',
		store : Ext
				.create('Foss.scheduling.inviteVehicle.InviteVehicleApplyQueryForm.useStatus.store'),
		queryMode : 'local',
		displayField : 'name',
		valueField : 'value',
		value : '',
		editable : false,
		fieldLabel : scheduling.inviteVehicle
				.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyQueryForm.useStatus'), // 使用状态
		columnWidth : .25
	},{//269701--lln--2016-01-18 begin
		name : 'inquiryNo',
		fieldLabel : scheduling.inviteVehicle
				.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyQueryForm.inquiryNo'), // 询价编号
		columnWidth : .25
		//269701--lln--2016-01-18 end
	}, {
		border : 1,
		xtype : 'container',
		columnWidth : 1,
		defaultType : 'button',
		layout : 'column',
		items : [{
			text : scheduling.inviteVehicle
					.i18n('Foss.scheduling.button.reset'), // 重置
			columnWidth : .08,
			handler : function() {
				scheduling.inviteVehicle.inviteVehicleApplyQueryForm.getForm()
						.reset();
				
				scheduling.inviteVehicle.inviteVehicleApplyQueryForm.getForm().findField('beginPredictUseTime').setValue(Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth()-1,new Date().getDate()
						,'00','00','00'), 'Y-m-d H:i:s'));
				scheduling.inviteVehicle.inviteVehicleApplyQueryForm.getForm().findField('endPredictUseTime').setValue(Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth()+1,new Date().getDate()
						,'23','59','59'), 'Y-m-d H:i:s'));
			}
		}, {
			xtype : 'container',
			columnWidth : .84,
			html : '&nbsp;'
		}, {
			text : scheduling.inviteVehicle
					.i18n('Foss.scheduling.button.search'), // 查询
			cls : 'yellow_button',
			columnWidth : .08,
			handler : function() {
				var form = scheduling.inviteVehicle.inviteVehicleApplyQueryForm
						.getForm();
				if (!form.isValid()) {
					Ext.Msg
							.alert(
									scheduling.inviteVehicle
											.i18n('Foss.scheduling.validation.alert.title'),
									scheduling.inviteVehicle
											.i18n('Foss.scheduling.validation.tip.search'));
					return false;
				}
				scheduling.inviteVehicle.pagingBar.moveFirst();
				scheduling.inviteVehicle.inviteVehicleApplyQueryGrid.show();
			}
		}]
	}]
});

scheduling.inviteVehicle.getComboboxValueMap = function(from, name) {
	var fields = from.getForm().getFields();
	var map = new Ext.util.HashMap();
	if (!fields)
		return map;
	var items = fields.items;
	if (!items)
		return map;
	for (var i = 0; i < items.length; i++) {
		var item = items[i];
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
Ext.define('Foss.scheduling.inviteVehicle.InviteVehicleApplyQueryModel', {
			extend : 'Ext.data.Model',
			fields : [{
						name : 'status',
						type : 'string'
					}, {
						name : 'inviteNo',
						type : 'string'
					}, {
						name : 'applyTime',
						type : 'string',
						convert : function(value) {
							if (value != null) {
								var date = new Date(value);
								return Ext.Date.format(date, 'Y-m-d H:i:s');
							} else {
								return null;
							}
						}
					}, {
						name : 'usePurpose',
						type : 'string'
					}, {
						name : 'useType',
						type : 'string'
					}, {
						name : 'orderVehicleModel',
						type : 'string'
					}, {
						name : 'predictUseTime',
						type : 'string',
						convert : function(value) {
							if (value != null) {
								var date = new Date(value);
								return Ext.Date.format(date, 'Y-m-d H:i:s');
							} else {
								return null;
							}
						}
					}, {
						name : 'useAddress',
						type : 'string'
					},  {
						name : 'arrivedAddress',
						type : 'string'
					},{
						name : 'clientName',
						type : 'string'
					}, {
						name : 'clientContactPhone',
						type : 'string'
					}, {
						name : 'goodsPackege',
						type : 'string'
					}, {
						name : 'goodsQty',
						type : 'string'
					}, {
						name : 'weight',
						type : 'string'
					}, {
						name : 'volume',
						type : 'string'
					}, {
						name : 'requirment',
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
						name : 'applyEmpName',
						type : 'string'
					}, {
						name : 'applyEmpCode',
						type : 'string'
					}, {
						name : 'businessType',
						type : 'string'
					},{
						name : 'telephoneNo',
						type : 'string'
					}, {
						name : 'mobilephoneNo',
						type : 'string'
					}, {
						name : 'id',
						type : 'string'
					}, {
						name : 'vehicleNo',
						type : 'string'
					}, {
						name : 'inviteCost',
						type : 'string'
					}, {
						name : 'auditTime',
						type : 'string',
						convert : function(value) {
							if (value != null) {
								var date = new Date(value);
								return Ext.Date.format(date, 'Y-m-d H:i:s');
							} else {
								return null;
							}
						}
					}, {
						name : 'vehicleLengthName',
						type : 'string'
					}, {
						name : 'isReturn',
						type : 'string'
					}, {
						name : 'returnCost',
						type : 'string'
					}, {
						name : 'isContractVehicle',
						type : 'string'
					}, {
						name : 'contractLine',
						type : 'string'
					}, {
						name : 'contractLineCode',
						type : 'string'
					}, {
						name : 'useStatus',
						type : 'string'
					}, {
						name : 'arrivedDeptName',
						type : 'string'
					}, {
						name : 'topFleetCode',
						type : 'string'
					}, {//269701--lln--2016-01-18 begin
						name : 'inquiryNo',
						type : 'string'
						//269701--lln--2016-01-18 end
					}, {
						name : 'arrivalTime',
						type : 'string',
						convert : function(value) {
							if (value != null) {
								var date = new Date(value);
								return Ext.Date.format(date, 'Y-m-d H:i:s');
							} else {
								return null;
							}
						}
					}]
		});

// store
Ext.define('Foss.scheduling.inviteVehicle.InviteVehicleApplyQueryStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.scheduling.inviteVehicle.InviteVehicleApplyQueryModel',
	proxy : {
		// 以JSON的方式加载数据
		type : 'ajax',
		actionMethods : 'POST',
		url : scheduling.realPath('queryInviteVehicleApply.action'),
		reader : {
			type : 'json',
			root : 'inviteVehicleVo.inviteVehicleList',
			totalProperty : 'totalCount',
			successProperty : 'success'
		}
	},
	listeners : {
		beforeload : function(store, operation) {
			var inviteVehicleApplyQueryForm = scheduling.inviteVehicle.inviteVehicleApplyQueryForm
					.getValues();
			Ext.apply(operation, {
				params : {
					// 使用类别
					'inviteVehicleVo.inviteVehicleDto.usePurpose' : inviteVehicleApplyQueryForm.usePurpose,
					// 用车类别
					'inviteVehicleVo.inviteVehicleDto.useType' : inviteVehicleApplyQueryForm.useType,
					// 请车价格
					'inviteVehicleVo.inviteVehicleDto.beginInviteCostStr' : inviteVehicleApplyQueryForm.beginInviteCost,
					'inviteVehicleVo.inviteVehicleDto.endInviteCostStr' : inviteVehicleApplyQueryForm.endInviteCost,
					// 用车车型
					'inviteVehicleVo.inviteVehicleDto.orderVehicleModel' : inviteVehicleApplyQueryForm.orderVehicleModel,
					// 安排车牌号
					'inviteVehicleVo.inviteVehicleDto.vehicleNo' : inviteVehicleApplyQueryForm.vehicleNo,
					// 受理开始时间
					'inviteVehicleVo.inviteVehicleDto.beginAuditTime' : Ext.Date
							.parse(inviteVehicleApplyQueryForm.beginAuditTime,
									'Y-m-d H:i:s'),
					// 受理结束时间
					'inviteVehicleVo.inviteVehicleDto.endAuditTime' : Ext.Date
							.parse(inviteVehicleApplyQueryForm.endAuditTime,
									'Y-m-d H:i:s'),
					// 外请车编号
					'inviteVehicleVo.inviteVehicleDto.inviteNo' : inviteVehicleApplyQueryForm.inviteNo,
					// 用车部门
					'inviteVehicleVo.inviteVehicleDto.applyOrgCode' : inviteVehicleApplyQueryForm.applyOrgCode,
					// 用车时间
					'inviteVehicleVo.inviteVehicleDto.beginPredictUseTime' : Ext.Date
							.parse(
									inviteVehicleApplyQueryForm.beginPredictUseTime,
									'Y-m-d H:i:s'),
					'inviteVehicleVo.inviteVehicleDto.endPredictUseTime' : Ext.Date
							.parse(
									inviteVehicleApplyQueryForm.endPredictUseTime,
									'Y-m-d H:i:s'),
					// 操作状态
					'inviteVehicleVo.inviteVehicleDto.status' : inviteVehicleApplyQueryForm.status,
					'inviteVehicleVo.inviteVehicleDto.isReturn' : inviteVehicleApplyQueryForm.isReturn,
					'inviteVehicleVo.inviteVehicleDto.beginReturnCostStr' : inviteVehicleApplyQueryForm.beginReturnCostStr,
					'inviteVehicleVo.inviteVehicleDto.endReturnCostStr' : inviteVehicleApplyQueryForm.endReturnCostStr,
					'inviteVehicleVo.inviteVehicleDto.isContractVehicle' : inviteVehicleApplyQueryForm.isContractVehicle,
					'inviteVehicleVo.inviteVehicleDto.contractLineCode' : inviteVehicleApplyQueryForm.contractLineCode,
					'inviteVehicleVo.inviteVehicleDto.useStatus' : inviteVehicleApplyQueryForm.useStatus,
					'inviteVehicleVo.inviteVehicleDto.inquiryNo' : inviteVehicleApplyQueryForm.inquiryNo //询价编号
				}
			});
		}
	}
});

// grid
Ext.define('Foss.scheduling.inviteVehicle.InviteVehicleApplyQueryGrid', {
	extend : 'Ext.grid.Panel',
	frame : true,
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	title : scheduling.inviteVehicle
			.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyQueryGrid.title'), // 查询结果
	ajaxRequest : function(url, submitBeforeMsg) {
		if (!submitBeforeMsg)
			submitBeforeMsg = scheduling.inviteVehicle
					.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyQueryGrid.submitBeforeMsg');
		var grid = this;
		Ext.MessageBox.confirm(scheduling.inviteVehicle
						.i18n('Foss.scheduling.validation.alert.title'),
				submitBeforeMsg, function(button) {
					if (button == 'yes') {
						var dataList = grid.getSelectionModel().getSelection();
						var inviteNoList = new Array();
						Ext.each(dataList, function(object) {
									inviteNoList.push(object.get('inviteNo'));
								})
						Ext.Ajax.request({
							url : scheduling.realPath(url),
							params : {
								'inviteVehicleVo.inviteNoList' : inviteNoList
							},
							success : function(response) {
								Ext.ux.Toast
										.msg(
												scheduling.inviteVehicle
														.i18n('Foss.scheduling.validation.alert.title'),
												scheduling.inviteVehicle
														.i18n('Foss.scheduling.operation.tip.save'));
								scheduling.inviteVehicle.pagingBar.moveFirst();
							},
							exception : function(response) {
								var result = Ext.decode(response.responseText);
								var tip = result.message.replace('error:', '');
								Ext.MessageBox
										.alert(
												scheduling.inviteVehicle
														.i18n('Foss.scheduling.validation.alert.title'),
												tip);
							}
						});
					}
				});
	},
	checkStatus : function(map, errorMessage, noSelectMsg) {
		if (!errorMessage)
			errorMessage = scheduling.inviteVehicle
					.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyQueryGrid.errorMessage'); // 状态错误!
		if (!noSelectMsg)
			noSelectMsg = scheduling.inviteVehicle
					.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyQueryGrid.noSelectMsg'); // 请选择您要操作的数据行!
		var grid = this;
		if (!grid.getSelectionModel().hasSelection()) {
			Ext.MessageBox.alert(scheduling.inviteVehicle
							.i18n('Foss.scheduling.validation.alert.title'),
					noSelectMsg); // 提示
			return false;
		}
		var flag = true;
		if (grid.getSelectionModel().hasSelection()) {
			var dataList = grid.getSelectionModel().getSelection();
			Ext.each(dataList, function(object) {
				var objStatus = object.raw.status;
				var inviteNo = object.data.inviteNo;
				if (!map.containsKey(objStatus)) {
					Ext.MessageBox
							.alert(
									scheduling.inviteVehicle.i18n('Foss.scheduling.validation.alert.title'),
									errorMessage + ", "+scheduling.inviteVehicle.i18n('foss.scheduling.inviteVehicle.inviteNo')+":" + inviteNo);
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
					text : scheduling.inviteVehicle
							.i18n('Foss.scheduling.button.apply'), // 申请
					columnWidth : .08,
					id : 'Foss.scheduling.inviteVehicle.InviteVehicleApplyQueryGrid.toolbar.button.apply.id',
					handler : function() {
						var grid = this.ownerCt.ownerCt;
						var inviteNo = null;
						if (grid.getSelectionModel().hasSelection()) {
							var dataList = grid.getSelectionModel()
									.getSelection();
							if (dataList.length > 1) {
								Ext.MessageBox
										.alert(
												scheduling.inviteVehicle
														.i18n('Foss.scheduling.validation.alert.title'),
												scheduling.inviteVehicle
														.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyQueryGrid.button.apply.message')); // 该动作不能支持多条目同时操作!
								return false;
							}
							var map = new Ext.util.HashMap();
							// 暂存
							map.add('STAGING', 'STAGING');
							// 未审核
							map.add('UNCOMMITTED', 'UNCOMMITTED');
							// 退回
							map.add('RETURN', 'RETURN');
							// 取消
							map.add('UNDO', 'UNDO');
							var status = dataList[0].raw.status;
							if (!map.containsKey(status)) {
								Ext.MessageBox
										.alert(
												scheduling.inviteVehicle
														.i18n('Foss.scheduling.validation.alert.title'),
												scheduling.inviteVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyQueryGrid.button.apply.message1')); // 该状态不支持编辑
								return false;
							}
							if (dataList[0].get('applyOrgCode') != FossUserContext
									.getCurrentDept().code) {
								Ext.MessageBox.alert(scheduling.inviteVehicle.i18n('foss.scheduling.adjustTransportationPath.hint'), scheduling.inviteVehicle.i18n('foss.scheduling.inviteVehicle.applyOrgCode.not.m'));//'申请部门不为本部门,不能修改!');
								return false;
							}
							inviteNo = dataList[0].get('inviteNo');
						}
						if (!scheduling.inviteVehicle.inviteVehicleApplyWindow) {
							scheduling.inviteVehicle.inviteVehicleApplyWindow = Ext
									.create('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow');
						}
						scheduling.inviteVehicle.inviteVehicleApplyWindow.inviteNo = inviteNo;
						scheduling.inviteVehicle.inviteVehicleApplyWindow
								.show();
						scheduling.inviteVehicle.inviteVehicleApplyWindow
								.initData();
					}
				}, {
					text : scheduling.inviteVehicle
							.i18n('Foss.scheduling.button.cancel'), // 撤销
					columnWidth : .08,
					id : 'Foss.scheduling.inviteVehicle.InviteVehicleApplyQueryGrid.toolbar.button.cancel.id',
					handler : function() {
						var grid = this.ownerCt.ownerCt;
						var message = scheduling.inviteVehicle
								.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyQueryGrid.button.apply.message2'); // 未审核,暂存,退回状态的才能撤销
						var map = new Ext.util.HashMap();
						// 暂存
						map.add('STAGING', 'STAGING');
						// 未审核
						map.add('UNCOMMITTED', 'UNCOMMITTED');
						// 退回
						map.add('RETURN', 'RETURN');
						if (!grid.checkStatus(map, message)) {
							return false;
						}
						var dataList = grid.getSelectionModel().getSelection();
						if (dataList[0].get('applyOrgCode') != FossUserContext
								.getCurrentDept().code) {
							Ext.MessageBox.alert(scheduling.inviteVehicle.i18n('foss.scheduling.adjustTransportationPath.hint'), scheduling.inviteVehicle.i18n('foss.scheduling.inviteVehicle.applyOrgCode.not.u'));//'申请部门不为本部门,不能撤销!');
							return false;
						}
						grid
								.ajaxRequest(
										'doUndoInviteVehicleApply.action',
										scheduling.inviteVehicle
												.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyQueryGrid.button.apply.message3'));
					}
				}, {
					text : scheduling.inviteVehicle
							.i18n('Foss.scheduling.button.audit'), // 审核受理
					columnWidth : .08,
					id : 'Foss.scheduling.inviteVehicle.InviteVehicleApplyQueryGrid.toolbar.button.audit.id',
					handler : function() {
						var grid = this.ownerCt.ownerCt;
						var message = scheduling.inviteVehicle
								.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyQueryGrid.button.audit.message'); // 只有未审核状态的才能进行审核受理操作
						var map = new Ext.util.HashMap();
						// 未审核
						map.add('UNCOMMITTED', 'UNCOMMITTED');
						// 只有未审核的才能进行审核受理操作
						if (!grid.checkStatus(map, message)) {

							return false;
						}
						// grid 选中的行
						var dataList = grid.getSelectionModel().getSelection();

						if (dataList.length != 1) {
							Ext.Msg.alert(scheduling.inviteVehicle.i18n('foss.scheduling.adjustTransportationPath.hint'), scheduling.inviteVehicle.i18n('foss.scheduling.inviteVehicle.not.lengths'));//'请单选一条外请车约车记录进行审核受理');

							return false;
						}

						var motorcadeCode = dataList[0].data.dispatchTransDept;

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
								var paramInviteNoList = new Array();
								// 处理id
								Ext.each(dataList, function(object) {
											paramInviteNoList.push(object
													.get('inviteNo'));
										})
								// 审核受理tab
								var passInviteVehicleIndexId = 'T_scheduling-passInviteVehicleIndex';
								removeTab(passInviteVehicleIndexId);
								addTab(
										passInviteVehicleIndexId,
										scheduling.inviteVehicle
												.i18n('Foss.scheduling.button.auditoutvehicle'),
										scheduling
												.realPath('passInviteVehicleIndex.action')
												+ '?paramInviteNoList='
												+ paramInviteNoList
												+ '&inviteVehicleIsLoadAll=true'); // 审核外请车
							},
							// 异常message
							exception : function(response) {
								var result = Ext.decode(response.responseText);
								Ext.MessageBox
										.alert(
												scheduling.inviteVehicle
														.i18n('Foss.scheduling.validation.alert.title'),
												result.message);
							}
						});
					}
				}, {
					text : scheduling.inviteVehicle
							.i18n('Foss.scheduling.button.arrive'), // 报到
					columnWidth : .08,
					id : 'Foss.scheduling.inviteVehicle.InviteVehicleApplyQueryGrid.toolbar.button.arrive.id',
					handler : function() {
						var grid = this.ownerCt.ownerCt;
						var inviteNo = null;
						if (grid.getSelectionModel().hasSelection()) {
							var dataList = grid.getSelectionModel()
									.getSelection();
							if (dataList.length > 1) {
								Ext.MessageBox
										.alert(
												scheduling.inviteVehicle
														.i18n('Foss.scheduling.validation.alert.title'),
												scheduling.inviteVehicle
														.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyQueryGrid.button.arrive.message')); // 该动作不能支持多条目同时操作!
								return false;
							}
							var map = new Ext.util.HashMap();
							map.add('COMMITTED', 'COMMITTED');
							var status = dataList[0].raw.status;
							var message = scheduling.inviteVehicle
									.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyQueryGrid.button.arrive.message1'); // 该条目没有达到已受理状态,无法报到
							if (!map.containsKey(status)) {
								Ext.MessageBox
										.alert(
												scheduling.inviteVehicle
														.i18n('Foss.scheduling.validation.alert.title'),
												message);
								return false;
							}
							inviteNo = dataList[0].get('inviteNo');
							//269701--2016-03-02 begin 
							//判断TPS车型与FOSS车型是否一致
							vehicleNo = dataList[0].get('vehicleNo');//车牌号
							usePurpose=dataList[0].get('usePurpose');//使用类型
							//269701--2016-03-02 end
							//269701--2016-03-15 begin 
							//传入询价编码 区分FOSS约车和TPS约车
							inquiryNo=dataList[0].get('inquiryNo');//询价编码
							//269701--2016-03-15 end
						} else {
							Ext.MessageBox
									.alert(
											scheduling.inviteVehicle
													.i18n('Foss.scheduling.validation.alert.title'),
											scheduling.inviteVehicle
													.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyQueryGrid.button.arrive.message2')); // 请选择您要操作的数据行!
							return false;
						}
						if (!scheduling.inviteVehicle.verifyArriveWindow) {
							scheduling.inviteVehicle.verifyArriveWindow = Ext
									.create('Foss.scheduling.inviteVehicle.VerifyArriveWindow');
						}
						// 报到前，需判断，释放存在其他外请约车，状态为"已报到"，并且"未使用"状态的约车记录，需提示进行"释放"动作
						Ext.Ajax.request({
							url : scheduling
									.realPath('checkVehicleArriveRule.action'),
							params : {
								'inviteVehicleVo.inviteVehicleDto.inviteNo' : inviteNo
							},
							success : function(response) {
								scheduling.inviteVehicle.verifyArriveWindow.inviteNo = inviteNo;
								scheduling.inviteVehicle.verifyArriveWindow.vehicleNo = vehicleNo;//车牌号
								scheduling.inviteVehicle.verifyArriveWindow.usePurpose = usePurpose;//使用类型
								//传入询价编码 区分FOSS约车和TPS约车
								scheduling.inviteVehicle.verifyArriveWindow.inquiryNo = inquiryNo;//询价编码
								scheduling.inviteVehicle.verifyArriveWindow
										.show();
								scheduling.inviteVehicle.verifyArriveWindow
										.initData();
							},
							exception : function(response) {
								var result = Ext.decode(response.responseText);
								var tip = result.message.replace(/R/g, '<br>')
										.replace('error:', '');

								Ext.MessageBox
										.alert(
												scheduling.inviteVehicle
														.i18n('Foss.scheduling.validation.alert.title'),
												tip);
							}
						});
					}
				}, {
					text : scheduling.inviteVehicle
							.i18n('Foss.scheduling.button.unlock'), // 释放
					columnWidth : .08,
					id : 'Foss.scheduling.inviteVehicle.InviteVehicleApplyQueryGrid.toolbar.button.release.id',
					handler : function() {
						var grid = this.ownerCt.ownerCt;
						var message = scheduling.inviteVehicle
								.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyQueryGrid.button.unlock.message'); // 操作状态不是已报到状态，不能释放
						var map = new Ext.util.HashMap();
						map.add('VERIFY_ARRIVE', 'VERIFY_ARRIVE'); // 已到达、报到
						if (!grid.checkStatus(map, message)) {
							return false;
						}
						grid.ajaxRequest('doReleaseInviteVehicleApply.action');
					}
				},{
					text : scheduling.inviteVehicle.i18n('foss.scheduling.invite.order.Vehicle.sms.title'),//'短信信息',
					columnWidth : .08,
					id : 'Foss.scheduling.inviteVehicle.InviteVehicleApplyQueryGrid.toolbar.button.sms.id',
					handler : function() {
						var grid = this.ownerCt.ownerCt;
						var message = scheduling.inviteVehicle.i18n('foss.scheduling.invite.order.Vehicle.sms.accepted');///只有已受理的才能生成短信信息!
						var map = new Ext.util.HashMap();
						// 已受理
						map.add('COMMITTED', 'COMMITTED');
						// 只有已受理的才会有短信
						if (!grid.checkStatus(map, message)) {
							return false;
						}
						// grid 选中的行
						var dataList = grid.getSelectionModel().getSelection();
						var inviteNo = dataList[0].data.inviteNo;
						
						var params = {"inviteVehicleVo.inviteVehicleDto.inviteNo" : inviteNo};
						
						Ext.Ajax.request({
							url:scheduling.realPath('queryInviteVehicleApplyDetail.action'),
							params:params,
							success:function(response) {
								var result = Ext.decode(response.responseText);
								var inviteVehicleVo = result.inviteVehicleVo;
								var record = Ext.create('Foss.scheduling.inviteVehicle.InviteVehicleApplyFormModel',result.inviteVehicleVo.inviteVehicleDto);
				               
								var auditInviteVehicleList = inviteVehicleVo.inviteVehicleDto.auditInviteVehicleList;
								var vehicleDriverWithDto = inviteVehicleVo.inviteVehicleDto.vehicleDriverWithDto;

								
								
								
								// 使用类型scheduling.inviteVehicle.usePurposeMap.get(usePurpose)
								// 用车类型 scheduling.inviteVehicle.useTypeMap.get(value);
								var usePurpose = scheduling.inviteVehicle.usePurposeMap.get(record.get('usePurpose'));
								var useType = scheduling.inviteVehicle.useTypeMap.get(record.get('useType'));
								//日志
								var notes = auditInviteVehicleList[0].notes;
								//到达时间
								var date = new Date(vehicleDriverWithDto.predictArriveTime);	
								var perdictArriveTime = Ext.Date.format(date, 'Y-m-d H:i:s');
								 var sms=usePurpose + '：' + useType
					                + '，'+scheduling.inviteVehicle.i18n('foss.scheduling.orderVehicle.GoodsInfo')+'：'
					                + record.get('weight') + scheduling.inviteVehicle.i18n('foss.scheduling.forecastQuantity.weightUnit') 
					                + '/' + record.get('volume') + scheduling.inviteVehicle.i18n('foss.scheduling.forecastQuantity.volumeUnit')
					                + '，'+scheduling.inviteVehicle.i18n('foss.scheduling.adjustTransportationPath.adjustPath.arriveTime')+'：' 
					                +perdictArriveTime
					                + '，'+scheduling.inviteVehicle.i18n('foss.scheduling.passOrderVehicle.orderNo')+'：' 
					                + record.get('inviteNo');
									
									//如果有备注则添加备注
					                if(notes){
					                	sms+='<br/>'+scheduling.inviteVehicle.i18n('foss.longscheduledesign.gird.plandetail.truckInfoNotes.lable')+'：'+ notes;
					                }
					                
				                Ext.MessageBox.alert(scheduling.inviteVehicle.i18n('foss.scheduling.invite.order.Vehicle.sms.title'),sms);
							},
							exception:function(response) {
								var result = Ext.decode(response.responseText);
								Ext.MessageBox.alert(scheduling.inviteVehicle.i18n('Foss.scheduling.leadTruck.QueryForm.tips'), result.message);
							}
						});
					}
				}]
	}],
	columns : [{
		xtype : 'ellipsiscolumn',
		header : scheduling.inviteVehicle
				.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyQueryGrid.inviteNo'), // 申请单号
		dataIndex : 'inviteNo',
		windowClassName : 'Foss.scheduling.inviteVehicle.InviteVehicleDetailWindow',
		xtype : 'openwindowcolumn',
		flex : 0.8
	}, {
		header : scheduling.inviteVehicle
				.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyQueryGrid.status'), // 操作状态
		dataIndex : 'status',
		renderer : function(value) {
			return FossDataDictionary.rendererSubmitToDisplay(value,
					'INVITEVEHICLE_STATUS');
		},
		flex : 0.6
	}, {
		header : scheduling.inviteVehicle
				.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyQueryGrid.usePurpose'), // 使用类型
		dataIndex : 'usePurpose',
		renderer : function(value) {
			return scheduling.inviteVehicle.usePurposeMap.get(value);
		},
		flex : 1
	}, {
		header : scheduling.inviteVehicle
				.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyQueryGrid.useType'), // 用车类型
		dataIndex : 'useType',
		renderer : function(value) {
			return scheduling.inviteVehicle.useTypeMap.get(value);
		},
		flex : 1
	}, {
		xtype : 'ellipsiscolumn',
		header : scheduling.inviteVehicle
				.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyQueryGrid.applyOrgName'), // 用车部门
		dataIndex : 'applyOrgName',
		flex : 1.5
	}, {
		xtype : 'ellipsiscolumn',
		header : scheduling.inviteVehicle
		.i18n('Foss.scheduling.inviteVehicle.InviteVehicleDetailWindow.inviteVehiclepplyInfo.arrivedDeptName'), // 用车部门
		dataIndex : 'arrivedDeptName',
		flex : 1.5
	}, {
		xtype : 'ellipsiscolumn',
		header : scheduling.inviteVehicle
				.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyQueryGrid.vehicleLengthName'), // 车辆类别
		dataIndex : 'vehicleLengthName',
		flex : 1
	}, {
		xtype : 'ellipsiscolumn',
		header : scheduling.inviteVehicle
				.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyQueryGrid.predictUseTime'), // 用车时间
		dataIndex : 'predictUseTime',
		flex : 1.5
	}, {
		xtype : 'ellipsiscolumn',
		header : scheduling.inviteVehicle
				.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyQueryGrid.vehicleNo'), // 安排车牌号
		dataIndex : 'vehicleNo',
		flex : 1
	}, {
		xtype : 'ellipsiscolumn',
		header : scheduling.inviteVehicle
				.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyQueryGrid.auditTime'), // 受理时间
		dataIndex : 'auditTime',
		flex : 1.5
	}, {
		xtype : 'ellipsiscolumn',
		header : scheduling.inviteVehicle
		.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyQueryGrid.arrivalTime'), // 报到时间
		dataIndex : 'arrivalTime',
		flex : 1.5
	}, {
		xtype : 'ellipsiscolumn',
		header : scheduling.inviteVehicle
				.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyQueryGrid.inviteCost'), // 请车价格
		dataIndex : 'inviteCost',
		flex : 0.8
	}, {
		header : scheduling.inviteVehicle
				.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyQueryGrid.isReturn'), // 是否回程
		dataIndex : 'isReturn',
		renderer : function(value) {
			if (value == 'Y') {
				return scheduling.inviteVehicle
						.i18n('Foss.scheduling.common.yes'); // 是
			} else if (value == 'N') {
				return scheduling.inviteVehicle
						.i18n('Foss.scheduling.common.no'); // 否
			} else {

			}
		},
		flex : 0.6
	}, {
		header : scheduling.inviteVehicle
		.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyQueryForm.useStatus'), // 使用状态
		dataIndex : 'useStatus',
		renderer : function(value){
			if (value == 'USING') {
				return scheduling.inviteVehicle.i18n('Foss.scheduling.inviteVehicle.useStatus.using');//'已使用'
			} else if (value == 'UNUSED') {
				return scheduling.inviteVehicle.i18n('Foss.scheduling.inviteVehicle.useStatus.unused');//'未使用'
			}
			return value;
		},
		flex : 0.6
	}, {
		xtype : 'ellipsiscolumn',
		header : scheduling.inviteVehicle
				.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyQueryGrid.returnCost'), // 回程价格
		dataIndex : 'returnCost',
		flex : 0.7
	}, {
		header : scheduling.inviteVehicle
				.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyQueryGrid.isContractVehicle'), // 是否合同车
		dataIndex : 'isContractVehicle',
		renderer : function(value) {
			if (value == 'Y') {
				return scheduling.inviteVehicle
						.i18n('Foss.scheduling.common.yes'); // 是
			} else if (value == 'N') {
				return scheduling.inviteVehicle
						.i18n('Foss.scheduling.common.no'); // 否
			} else {

			}
		},
		flex : 0.6
	}, {
		xtype : 'ellipsiscolumn',
		header : scheduling.inviteVehicle
				.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyQueryGrid.contractLine'), // 合同线路
		dataIndex : 'contractLine',
		flex : 1
	},{//269701--lln--2016-01-19 begin
		xtype : 'ellipsiscolumn',
		header : scheduling.inviteVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.customerAndGoodsInfo.inquiryNo'), // '询价编号',
		dataIndex : 'inquiryNo',
		flex : 1
		//269701--lln--2016-01-19 end
	},{
		xtype : 'ellipsiscolumn',
		header : scheduling.inviteVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.customerAndGoodsInfo.remark'), // '备注',
		dataIndex : 'requirment',
		flex : 1
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext
				.create('Foss.scheduling.inviteVehicle.InviteVehicleApplyQueryStore');
		me.selModel = Ext.create('Ext.selection.CheckboxModel');
		me.bbar = Ext.create('Deppon.StandardPaging', {
					store : me.store
				});
		scheduling.inviteVehicle.pagingBar = me.bbar;
		me.callParent([cfg]);

	},
	viewConfig : {
		enableTextSelection : true
	}
});

Ext.onReady(function() {
	Ext.QuickTips.init();
	var inviteVehicleApplyQueryForm = Ext
			.create('Foss.scheduling.inviteVehicle.InviteVehicleApplyQueryForm');
	var inviteVehicleApplyQueryGrid = Ext
			.create('Foss.scheduling.inviteVehicle.InviteVehicleApplyQueryGrid');
	scheduling.inviteVehicle.inviteVehicleApplyQueryGrid = inviteVehicleApplyQueryGrid;
	scheduling.inviteVehicle.inviteVehicleApplyQueryForm = inviteVehicleApplyQueryForm;
	Ext.create('Ext.panel.Panel', {
				id : 'T_scheduling-inviteVehicleIndex_content',
				cls : "panelContentNToolbar",
				bodyCls : 'panelContentNToolbar-body',
				layout : 'auto',
				items : [inviteVehicleApplyQueryForm,
						inviteVehicleApplyQueryGrid],
				renderTo : 'T_scheduling-inviteVehicleIndex-body'
			});
	scheduling.inviteVehicle.usePurposeMap = scheduling.inviteVehicle
			.getComboboxValueMap(
					scheduling.inviteVehicle.inviteVehicleApplyQueryForm,
					'usePurpose');
	scheduling.inviteVehicle.useTypeMap = scheduling.inviteVehicle
			.getComboboxValueMap(
					scheduling.inviteVehicle.inviteVehicleApplyQueryForm,
					'useType');
	// 权限控制
	var currentDept = FossUserContext.getCurrentDept();
	// 申请按钮
	if (!scheduling.inviteVehicle
					.isPermission('scheduling/applyInviteVehicleApplyButton')) {
		Ext
				.getCmp('Foss.scheduling.inviteVehicle.InviteVehicleApplyQueryGrid.toolbar.button.apply.id')
				.hide();
	}
	// 撤销按钮
	if (!scheduling.inviteVehicle
					.isPermission('scheduling/doUndoInviteVehicleApplyButton')) {
		Ext
				.getCmp('Foss.scheduling.inviteVehicle.InviteVehicleApplyQueryGrid.toolbar.button.cancel.id')
				.hide();
	}
	// 审批受理
	if (!scheduling.inviteVehicle
					.isPermission('scheduling/auditInviteVehicleIndexButton')) {
		Ext
				.getCmp('Foss.scheduling.inviteVehicle.InviteVehicleApplyQueryGrid.toolbar.button.audit.id')
				.hide();
	}
	// 报到
	if (!scheduling.inviteVehicle
			.isPermission('scheduling/doVerifyArriveInviteVehicleApplyButton')) {
		Ext
				.getCmp('Foss.scheduling.inviteVehicle.InviteVehicleApplyQueryGrid.toolbar.button.arrive.id')
				.hide();
	}
	// 释放
	if (!scheduling.inviteVehicle
			.isPermission('scheduling/doReleaseInviteVehicleApplyButton')) {
		Ext
				.getCmp('Foss.scheduling.inviteVehicle.InviteVehicleApplyQueryGrid.toolbar.button.release.id')
				.hide();
	}
});

// 外请约车报到 window
Ext.define('Foss.scheduling.inviteVehicle.VerifyArriveWindow', {
	extend : 'Ext.window.Window',
	title : scheduling.inviteVehicle
			.i18n('Foss.scheduling.inviteVehicle.VerifyArriveWindow.title'), // 外请约车报到
	width : 530,
	height : 210,
	resizable : false,
	buttonArea : null,
	operateForm : null,
	inviteNo : null,
	rerurnAndContractVehicleInfo : null,
	modal : true,
	closable : true,
	closeAction : 'hide',
	contractLineCode : null,
	initData : function() {
		this.operateForm.getForm().reset();
	},
	createRerurnAndContractVehicle : function() {
		if (this.rerurnAndContractVehicleInfo) {
			return this.rerurnAndContractVehicleInfo;
		}
		this.rerurnAndContractVehicleInfo = Ext.create('Ext.form.FieldSet', {
			defaultType : 'textfield',
			title : '',
			width : 500,
			items : [{
				xtype : 'container',
				layout : 'column',
				defaultType : 'textfield',
				defaults : {
					labelWidth : 85,
					margin : '5 10 5 10'
				},
				items : [{
					name : 'isReturn',
					xtype : 'checkboxfield',
					boxLabel : scheduling.inviteVehicle
							.i18n('Foss.scheduling.inviteVehicle.VerifyArriveWindow.returnAndContractVehicleInfo.isReturn'), // 是否回程
					inputValue : 'Y',
					columnWidth : .3,
					listeners : {
						change : {
							fn : function(src, newValue, oldValue, eOpts) {
								var value = (newValue && true == newValue);
								var operateForm = scheduling.inviteVehicle.verifyArriveWindow.operateForm
										.getForm();
								operateForm.findField('returnCost')
										.setDisabled(!(value));
								operateForm.findField('returnCost').setValue('');
							}
						}
					}
				}, {
					name : 'returnCost',
					fieldLabel : scheduling.inviteVehicle
							.i18n('Foss.scheduling.inviteVehicle.VerifyArriveWindow.returnAndContractVehicleInfo.returnCost'), // 回程价格
					regex : /^\d+\.?\d{0,3}$/,
					regexText : scheduling.inviteVehicle
							.i18n('Foss.scheduling.validation.tip.number'), // 格式输入有误.必须是数字!
					maxLength : 9,
					maxLengthText : scheduling.inviteVehicle
							.i18n('Foss.scheduling.inviteVehicle.VerifyArriveWindow.returnAndContractVehicleInfo.returnCost.maxLengthText'), // 货物件数已超过最大限制!
					disabled : true,
					columnWidth : .7
				}, {
					name : 'isContractVehicle',
					xtype : 'checkboxfield',
					boxLabel : scheduling.inviteVehicle
							.i18n('Foss.scheduling.inviteVehicle.VerifyArriveWindow.returnAndContractVehicleInfo.isContractVehicle'), // 是否合同车
					inputValue : 'Y',
					columnWidth : .3,
					listeners : {
						change : {
							fn : function(src, newValue, oldValue, eOpts) {
								var operateForm = scheduling.inviteVehicle.verifyArriveWindow.operateForm
										.getForm();
								var value = (newValue && true == newValue);
								if (!value) {
									// 清空文本框
									operateForm.findField('contractLineName')
											.setValue('');
									scheduling.inviteVehicle.verifyArriveWindow.contractLineCode = null;;
									return false;
								}

								var inviteNo = scheduling.inviteVehicle.verifyArriveWindow.inviteNo;
								var params = {
									'inviteVehicleVo.inviteVehicleDto.inviteNo' : inviteNo
								};
								Ext.Ajax.request({
									url : scheduling
											.realPath('queryBargainLine.action'),
									params : params,
									success : function(response) {
										var result = Ext
												.decode(response.responseText);
										if (result
												&& result.inviteVehicleVo.lineEntity) {
											var lineEntity = result.inviteVehicleVo.lineEntity;
											operateForm
													.findField('contractLineName')
													.setValue(lineEntity.lineName);
											scheduling.inviteVehicle.verifyArriveWindow.contractLineCode = lineEntity.simpleCode;
										}
									},
									exception : function(response) {
										var result = Ext
												.decode(response.responseText);
										Ext.MessageBox
												.alert(
														scheduling.inviteVehicle
																.i18n('Foss.scheduling.validation.alert.title'),
														result.message);
									}
								});

								//						            
							}
						}
					}
				}, {
					name : 'contractLineName',
					fieldLabel : scheduling.inviteVehicle
							.i18n('Foss.scheduling.inviteVehicle.VerifyArriveWindow.returnAndContractVehicleInfo.contractLineName'), // 合同线路
					disabled : true,
					columnWidth : .7
				}]
			}]
		})
		return this.rerurnAndContractVehicleInfo;
	},
	createButtonArea : function() {
		if (this.buttonArea) {
			return this.buttonArea;
		}
		this.buttonArea = Ext.create('Ext.container.Container', {
			defaultType : 'button',
			name : 'buttonArea',
			defaults : {
				margin : '2 7 2 7'
			},
			layout : 'column',
			items : [{
						xtype : 'container',
						html : '&nbsp;',
						columnWidth : .29
					}, {
						text : scheduling.inviteVehicle
								.i18n('Foss.scheduling.button.arrive'),
						columnWidth : .24,
						handler : function() {
							var operateForm = scheduling.inviteVehicle.verifyArriveWindow.operateForm
									.getForm();
							var findField = function(name) {
								return operateForm.findField(name);
							};
							var getValue = function(name) {
								return findField(name).getValue();
							};
							var isReturn = getValue('isReturn');
							var returnCost = getValue('returnCost');
							var isContractVehicle = getValue('isContractVehicle');
							var contractLineCode = scheduling.inviteVehicle.verifyArriveWindow.contractLineCode;
							var inviteNo = scheduling.inviteVehicle.verifyArriveWindow.inviteNo;
							//269701--2016-03-02 begin 
							var vehicleNo = scheduling.inviteVehicle.verifyArriveWindow.vehicleNo;//车牌号
							var usePurpose = scheduling.inviteVehicle.verifyArriveWindow.usePurpose;//使用类型
							//传入询价编码 区分FOSS约车和TPS约车
							var inquiryNo = scheduling.inviteVehicle.verifyArriveWindow.inquiryNo;//询价编码
							//269701--2016-03-02 begin 
							if (isReturn == true && Ext.isEmpty(returnCost)) {
								Ext.MessageBox
										.alert(
												scheduling.inviteVehicle
														.i18n('Foss.scheduling.validation.alert.title'),
												scheduling.inviteVehicle
														.i18n('Foss.scheduling.inviteVehicle.VerifyArriveWindow.returnAndContractVehicleInfo.returnCost.message')); // 请输入回程价格!
								return false;
							}
							if (!operateForm.isValid()) {
								Ext.MessageBox
										.alert(
												scheduling.inviteVehicle
														.i18n('Foss.scheduling.validation.alert.title'),
												scheduling.inviteVehicle
														.i18n('Foss.scheduling.inviteVehicle.VerifyArriveWindow.returnAndContractVehicleInfo.validation.message')); // 请输入合法的数字!
								return false;
							}
							if (isContractVehicle == true
									&& Ext.isEmpty(contractLineCode)) {
								Ext.MessageBox
										.alert(
												scheduling.inviteVehicle
														.i18n('Foss.scheduling.validation.alert.title'),
												scheduling.inviteVehicle
														.i18n('Foss.scheduling.inviteVehicle.VerifyArriveWindow.returnAndContractVehicleInfo.contractLineName.message')); // 请配置合同线路!
								return false;
							}
							var maxValue = 99999999;
							if (isReturn == true && returnCost > maxValue) {
								Ext.MessageBox
										.alert(
												scheduling.inviteVehicle
														.i18n('Foss.scheduling.validation.alert.title'),
												scheduling.inviteVehicle
														.i18n(
																'Foss.scheduling.inviteVehicle.VerifyArriveWindow.returnAndContractVehicleInfo.returnCost.message1',
																[maxValue + ''])); // 回程价格最大值不能超过99999999!
								return false;
							}

							isReturn = (true == isReturn ? 'Y' : 'N');
							isContractVehicle = (true == isContractVehicle
									? 'Y'
									: 'N');

							if (!returnCost) {
								returnCost = 0;
							}

							var params = {
								'inviteVehicleVo.inviteVehicleDto.inviteNo' : inviteNo,
								'inviteVehicleVo.inviteVehicleDto.isReturn' : isReturn,
								'inviteVehicleVo.inviteVehicleDto.returnCost' : returnCost,
								'inviteVehicleVo.inviteVehicleDto.isContractVehicle' : isContractVehicle,
								'inviteVehicleVo.inviteVehicleDto.contractLineCode' : contractLineCode,
								'inviteVehicleVo.inviteVehicleDto.vehicleNo' : vehicleNo, //新增查询条件--车牌号
								'inviteVehicleVo.inviteVehicleDto.usePurpose' : usePurpose, //新增查询条件--使用类型
								'inviteVehicleVo.inviteVehicleDto.inquiryNo' : inquiryNo //新增查询条件--询价编码---传入询价编码 区分FOSS约车和TPS约车
								
							};
							Ext.MessageBox
									.confirm(
											scheduling.inviteVehicle
													.i18n('Foss.scheduling.validation.alert.title'),
											scheduling.inviteVehicle
													.i18n('Foss.scheduling.inviteVehicle.VerifyArriveWindow.returnAndContractVehicleInfo.validation.message1'),
											function(button) { // 确定要报到吗？
												if (button == 'yes') {
													Ext.Ajax.request({
														url : scheduling
																.realPath('doVerifyArriveInviteVehicleApply.action'),
														params : params,
														success : function(
																response) {
															Ext.ux.Toast
																	.msg(
																			scheduling.inviteVehicle
																					.i18n('Foss.scheduling.validation.alert.title'),
																			scheduling.inviteVehicle
																					.i18n('Foss.scheduling.operation.tip.save')); // 操作成功
															scheduling.inviteVehicle.verifyArriveWindow
																	.close();
															scheduling.inviteVehicle.pagingBar
																	.moveFirst();
														},
														exception : function(
																response) {
															var result = Ext
																	.decode(response.responseText);
															Ext.MessageBox
																	.alert(
																			scheduling.inviteVehicle
																					.i18n('Foss.scheduling.validation.alert.title'),
																			result.message);
														}
													});
												}
											});
						}
					}, {
						text : scheduling.inviteVehicle
								.i18n('Foss.scheduling.button.abort'), // 取消
						columnWidth : .24,
						handler : function() {
							scheduling.inviteVehicle.verifyArriveWindow.close();
						}
					}, {
						xtype : 'container',
						html : '&nbsp;',
						columnWidth : .29
					}]
		});
		return this.buttonArea;
	},
	createOperateForm : function() {
		if (this.operateForm) {
			return this.operateForm;
		}
		this.operateForm = Ext.create('Ext.form.Panel', {
					frame : false
				});
		return this.operateForm;
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([cfg]);
		me.createRerurnAndContractVehicle();
		me.createButtonArea();
		me.createOperateForm();
		this.operateForm.add([me.rerurnAndContractVehicleInfo, me.buttonArea]);
		me.add([this.operateForm]);
	}
});

//外请约车申请
Ext.define('Foss.scheduling.inviteVehicle.InviteVehicleApplyFormModel', {
			extend : 'Ext.data.Model',
			fields : [{
						name : 'status',
						type : 'string'
					}, {
						name : 'inviteNo',
						type : 'string'
					}, {
						name : 'applyTime',
						type : 'string',
						convert : function(value) {
							if (!value)
								return '';
							var date = new Date(value);
							var formatStr = 'Y-m-d H:i:s';
							return Ext.Date.format(date, formatStr);
						}
					}, {
						name : 'usePurpose',
						type : 'string'
					}, {
						name : 'useType',
						type : 'string'
					}, {
						name : 'orderVehicleModel',
						type : 'string'
					}, {
						name : 'predictUseTime',
						type : 'string',
						convert : function(value) {
							if (!value)
								return '';
							var date = new Date(value);
							var formatStr = 'Y-m-d H:i:s';
							return Ext.Date.format(date, formatStr);
						}
					}, {
						name : 'useAddress',
						type : 'string'
					}, {
						name : 'arrivedAddress',
						type : 'string'
					}, {
						name : 'bearFeesDeptName',
						type : 'string'
					},{
						name : 'bearFeesDeptName1',
						type : 'string'
					}, {
						name : 'bearFeesDeptName2',
						type : 'string'
					}, {
						name : 'bearFeesDeptCode',
						type : 'string'
					},{     
						name : 'clientName',
						type : 'string'
					}, {
						name : 'clientContactPhone',
						type : 'string'
					}, {
						name : 'goodsPackege',
						type : 'string'
					}, {
						name : 'goodsQty',
						type : 'string'
					}, {
						name : 'weight',
						type : 'string'
					}, {
						name : 'volume',
						type : 'string'
					}, {
						name : 'requirment',
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
						name : 'applyEmpName',
						type : 'string'
					}, {
						name : 'applyEmpCode',
						type : 'string'
					}, {
						name : 'telephoneNo',
						type : 'string'
					}, {
						name : 'mobilephoneNo',
						type : 'string'
					}, {
						name : 'id',
						type : 'string'
					}, {
						name : 'vehicleNo',
						type : 'string'
					},{
						name : 'applyFees',
						type : 'string'
					}, {
						name : 'inviteCost',
						type : 'string'
					}, {
						name : 'auditTime',
						type : 'string'
					}, {
						name : 'isScaneSeeGoods',
						type : 'string'
					}, {
						name : 'isGoodsOver',
						type : 'string'
					}, {
						name : 'vehicleLengthName',
						type : 'string'
					}, {
						name : 'isPassByArrivedDept',
						type : 'string'
					}, {
						name : 'arrivedDeptCode',
						type : 'string'
					}, {
						name : 'arrivedDeptName',
						type : 'string'
					}, {
						name : 'dispatchTransDeptName',
						type : 'string'
					}, {
						name : 'loadGoodsTime',
						type : 'string',
						convert : function(value) {
							if (!value)
								return '';
							var date = new Date(value);
							var formatStr = 'Y-m-d H:i:s';
							return Ext.Date.format(date, formatStr);
						}
					}, {
						name : 'goodsName',
						type : 'string'
					}]
		});
/**
 * 声明费用承担部门的model
 */
Ext.define('Foss.scheduling.inviteVehicle.OrgModel',{
	extend:'Ext.data.Model',
	fields:['applyPath','ministryinformationCode']
});
/**
 * 声明费用承担部门的store
 */
Ext.define('Foss.scheduling.inviteVehicle.OrgStore',{
	extend:'Ext.data.Store',
	model:'Foss.scheduling.inviteVehicle.OrgModel',
	proxy:{
		type:'ajax',
		reader:{
			type:'json',
			root:'inviteVehicleVo.comboList'
		}
	}
});

Ext.define('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow', {
	extend : 'Ext.window.Window',
	
/*	269701--lln--2016/01/20 title由--申请约车改为 同城外租车申请
 * title : scheduling.inviteVehicle
			.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.title'), // 外请约车申请
*/		
	title : scheduling.inviteVehicle
			.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.newTitle'), // 同城外租车申请
	width : 710,
	height : 680,
	inviteVehicleApplyInfo : null,
	customerAndGoodsInfo : null,
	resizable : false,
	buttonArea : null,
	operateForm : null,
	inviteNo : null,
	maxWeightVehicle : null,
	minWeightVehicle : null,
	maxVolumeVehicle : null,
	minVolumeVehicle : null,
	modal : true,
	closable : true,
	closeAction : 'hide',
	createInviteVehicleConfigurationParams : function(){
		if(this.maxWeightVehicle && this.minWeightVehicle && this.maxVolumeVehicle && this.minVolumeVehicle){
			return true;
		}
		var self = this;
		Ext.Ajax.request({
			url : scheduling.realPath('inviteVehicleConfigurationParams.action'),
			params : {},
			success : function(response) {
				var result = Ext.decode(response.responseText);
				//设置初始化配置参数
				//由于重量是吨做表示的,而配置的参数去是千克所以这边需要除以1000
				self.maxWeightVehicle = result.inviteVehicleVo.maxWeightVehicle/1000;
				self.minWeightVehicle = result.inviteVehicleVo.minWeightVehicle/1000;
				//体积与配置参数一致所以这边不需要转换
				self.maxVolumeVehicle = result.inviteVehicleVo.maxVolumeVehicle;
				self.minVolumeVehicle = result.inviteVehicleVo.minVolumeVehicle;
			},
			exception : function(response) {
				var result = Ext.decode(response.responseText);
				Ext.MessageBox
						.alert(
								scheduling.inviteVehicle
										.i18n('Foss.scheduling.validation.alert.title'),
								result.message);
			}
		});
		
		
		
	},
	createInviteVehicleApplyInfo : function() {
		if (this.inviteVehicleApplyInfo) {
			return this.inviteVehicleApplyInfo;
		}
		this.inviteVehicleApplyInfo = Ext.create('Ext.form.FieldSet', {
			defaultType : 'textfield',
			width : 675,
			frame:true,
			title : scheduling.inviteVehicle
					.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.inviteVehicleApplyInfo.title'), // 申请信息
			items : [{
				xtype : 'container',
				layout : 'column',
				bodyStyle: 'overflow-y:auto;',
				defaultType : 'textfield',
				defaults : {
					labelWidth : 105,
					margin : '5 10 5 10'
				},
				items : [{
					/*name : 'usePurpose',
					fieldLabel : scheduling.inviteVehicle
							.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.inviteVehicleApplyInfo.usePurpose'), // 使用类型
					columnWidth : .5,
					value :'同城外租车',
					allowBlank : false*/
					
					name : 'usePurpose',
					fieldLabel : scheduling.inviteVehicle
							.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.inviteVehicleApplyInfo.usePurpose'), // 使用类型
					
				     store : Ext.create('Ext.data.Store', {
						fields : ['valueName', 'valueCode'],
						data : [{
							'valueName' : scheduling.inviteVehicle
									.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.inviteVehicleApplyInfo.usePurpose.samecityorder'),
							'valueCode' : 'SAMECITY_ORDER'
						},		// 同城外租车
						 {
							'valueName' : scheduling.inviteVehicle
									.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.inviteVehicleApplyInfo.usePurpose.inviteorder'),
							'valueCode' : 'INVITE_ORDER'
						},		// 外请车约车
						{
							'valueName' : scheduling.inviteVehicle
									.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.inviteVehicleApplyInfo.usePurpose.wholecarorder'),
							'valueCode' : 'WHOLECAR_ORDER'
						}		// 整车约车
						]
					}),
					columnWidth : .5,
					xtype : 'combo',
					queryMode : 'local',
					displayField : 'valueName',
					valueField : 'valueCode',
					editable : false,
					allowBlank : false,
					listeners : {
						select : {
							fn : function(combo, records, eOpts) {
								var usePurpose = combo.getValue();
								var allowBlank = true;
								var allowBlankStr = '&nbsp;&nbsp;';
								var arrivedDeptHidden = false;
								scheduling.inviteVehicle.inviteOrderTemp=true;
								if (usePurpose == 'WHOLECAR_ORDER') {
									var allowBlank = false;
									arrivedDeptHidden = true;
									allowBlankStr = '*';
									scheduling.inviteVehicle.inviteOrderTemp=false;
								}
								var operateForm = scheduling.inviteVehicle.inviteVehicleApplyWindow.operateForm
										.getForm();
								
								//269701--2016/01/26--begin
								//FOSS系统只能进行同城约车；其他性质约车 请至TPS系统约车
								//269701--2016/03/15--begin
								//0327上线版本--FOSS放开整车约车功能，但整车约车数据不同步给TPS；
								if(usePurpose=='INVITE_ORDER'){
									Ext.MessageBox.alert(scheduling.inviteVehicle.i18n('Foss.scheduling.validation.alert.title'),
											'FOSS系统只能进行同城约车和整车约车；其他性质约车 请至TPS系统约车');
									scheduling.inviteVehicle.inviteVehicleApplyWindow.close();
								}
								//269701--2016/01/26--end
								var findField = function(name) {
									return operateForm.findField(name);
								};
								var $ = function(id) {
									return document.getElementById(id);
								};
								//269701--foss--lln  2015--08--05 begin
								//由于省市区联动控件+详细地址 为了设置间距，设置了id属性，所以不能再使用网页的id，故作出判断处理
								var settingAllowBlank = function(object) {
									if(object.getId()=='arrivedAdressId'){
										$(object.getId()).innerHTML = allowBlankStr;
									}else if(object.getId()=='detailAdressId'){
										$(object.getId()).innerHTML = allowBlankStr;
										//269701--foss--lln  2015--08--05 end
									}else{
										$(object.getId() + '-inputEl-prefix').innerHTML = allowBlankStr;
									}
									
								};
								
								
								//同城外租车是显示判断条件
								if (usePurpose == 'SAMECITY_ORDER') {
									findField('applyFees').setVisible(true);
									findField('bearFeesDept').setVisible(true);
									findField('businessType').setVisible(true);
									findField('bearFeesDept1').setVisible(false);
									findField('bearFeesDept2').setVisible(false);
									var applyFeesObject = findField('applyFees');
								        applyFeesObject.allowBlank =false; 
								    var bearFeesDeptNameObject = findField('bearFeesDept');
								     bearFeesDeptNameObject.allowBlank =false; 
								    var bearFeesDeptName1Object = findField('bearFeesDept1');
								     bearFeesDeptName1Object.allowBlank =true;
								    var bearFeesDeptName2Object = findField('bearFeesDept2');
								     bearFeesDeptName2Object.allowBlank =true; 
								    var businessTypeObject = findField('businessType');
								     businessTypeObject.allowBlank =false; 
								     findField('businessType').setValue(null);
								}else{
									findField('bearFeesDept1').setVisible(false);
									 var bearFeesDeptName1Object = findField('bearFeesDept1');
								     bearFeesDeptName1Object.allowBlank =true;
								     findField('bearFeesDept2').setVisible(false);
									 var bearFeesDeptName2Object = findField('bearFeesDept2');
								     bearFeesDeptName2Object.allowBlank =true; 
									findField('applyFees').setVisible(false);
									findField('applyFees').setValue(null);
									findField('businessType').setVisible(false);
									findField('businessType').setValue(null);
									findField('bearFeesDept').setVisible(false);
									findField('bearFeesDept').setValue(null);
									findField('applyFees').allowBlank=true;
									findField('bearFeesDept').allowBlank=true;
									findField('businessType').allowBlank=true;
								}

								var goodsNameObject = findField('goodsName');
								goodsNameObject.allowBlank = allowBlank;
								
								var weightObject = findField('weight');
								weightObject.allowBlank = allowBlank;

								var goodsPackegeObject = findField('goodsPackege');
								goodsPackegeObject.allowBlank = allowBlank;

								var goodsQtyObject = findField('goodsQty');
								goodsQtyObject.allowBlank = allowBlank;

								var volumeObject = findField('volume');
								volumeObject.allowBlank = allowBlank;
								
								var loadGoodsTimeObject = findField('loadGoodsTime');
								loadGoodsTimeObject.allowBlank = allowBlank;
								
								var isScaneSeeGoodsObject = findField('isScaneSeeGoods');
								isScaneSeeGoodsObject.allowBlank = allowBlank;
								
								var isGoodsOverObject = findField('isGoodsOver');
								isGoodsOverObject.allowBlank = allowBlank;
								
								var clientNameObject = findField('clientName');
								clientNameObject.allowBlank = allowBlank;

								var clientContactPhoneObject = findField('clientContactPhone');
								clientContactPhoneObject.allowBlank = allowBlank;
								//269701--foss--lln  2015--08--05 begin
								//var arrivedAddressObject = findField('arrivedAddress');
								//通过id获取控件
								
								//到达地址--省市区
								var arrivedAddressObject = Ext.getCmp('arrivedAdressId');
								//到达地址--详细地址
								var detailAddressObject = Ext.getCmp('detailAdressId');
								//269701--foss--lln  2015--08--05 end
								
								settingAllowBlank(goodsNameObject);
								settingAllowBlank(weightObject);
								settingAllowBlank(goodsPackegeObject);
								settingAllowBlank(goodsQtyObject);
								settingAllowBlank(volumeObject);
								settingAllowBlank(loadGoodsTimeObject);
								settingAllowBlank(isScaneSeeGoodsObject);
								settingAllowBlank(isGoodsOverObject);
								settingAllowBlank(clientNameObject);
								settingAllowBlank(clientContactPhoneObject);
								//269701--foss--lln  2015--08--05 begin
								//设置该属性时，会导致省市区联动控件不显示,故注释
								//settingAllowBlank(arrivedAddressObject);
								
								//到达地址--省
								var provinceName=findField('provinceName');
								//到达地址--市
								var cityName=findField('cityName');
								//到达地址--区/县
								var areaName=findField('areaName');
								//到达地址--详细地址
								var detailAdress=findField('detailAdress');
								//269701--foss--lln  2015--08--05 end
								// 当使用类型为”整车约车”时,用车类型无”到中转场”这一项.
								var useType = findField('useType');
								useType.reset();
								var useTypeArray = new Array();
								useTypeArray.push({
									'valueName' : scheduling.inviteVehicle
											.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyQueryForm.useType.store.tosalesdepart'),
									'valueCode' : 'TO_SALES_DEPARTMENT'
								}); // 到营业部
								useTypeArray.push({
									'valueName' : scheduling.inviteVehicle
											.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyQueryForm.useType.store.toclient'),
									'valueCode' : 'TO_CLIENT'
								}); // 到客户处
								// 外请
								if (usePurpose == 'INVITE_ORDER'||usePurpose == 'SAMECITY_ORDER') {
									useTypeArray.push({
										'valueName' : scheduling.inviteVehicle
												.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyQueryForm.useType.store.totransit'),
										'valueCode' : 'TO_TRANSIT'
									}); // 到中转场
									//269701--foss--lln  2015--08--05 begin
									provinceName.setValue('');
									cityName.setValue('');
									areaName.setValue('');
									detailAdress.setValue('');
									//269701--foss--lln  2015--08--05 end
									isScaneSeeGoodsObject.setValue('');
									isGoodsOverObject.setValue('');
									findField('isPassByArrivedDept').setValue('N');
								}
								useType.store.loadData(useTypeArray);
								
								// 派车车队
								//派车车队是否是营业部,如果营业部且不是整车则只能下拉出当前部门对应的顶级车队,否则能下拉出全部的车队
								//修改公共选择器的条件
								var dispatchTransDeptObject = findField('dispatchTransDept');
								dispatchTransDeptObject.store.removeAll();
								dispatchTransDeptObject.store.addListener('beforeload', function(store, operation, eOpts) {
									var searchParams = operation.params;
									if (Ext.isEmpty(searchParams)) {
										searchParams = {};
										Ext.apply(operation, {
											params : searchParams
										});
									}
									//营业部 　&&　整成　=> 下拉全部的顶级车队
									if(FossUserContext.getCurrentDept().salesDepartment === 'Y' && !scheduling.inviteVehicle.inviteOrderTemp){
										searchParams['commonMotorcadeVo.commonMotorcadeDto.isFullFleetOrgFlag'] = 'Y';
									}//else{
										//searchParams['commonMotorcadeVo.commonMotorcadeDto.isFullFleetOrgFlag'] = 'N';
									//}
									searchParams['commonMotorcadeVo.commonMotorcadeDto.topFleetOrgCode'] = FossUserContext.getCurrentDept().code;
									
								});
								dispatchTransDeptObject.store.loadPage(1);
								
								// 是否经过到达部门
								var isPassByArrivedDeptObject = findField('isPassByArrivedDept');
								isPassByArrivedDeptObject
										.setVisible(arrivedDeptHidden);

								// 到达部门 恰面占位符
								var arrivedDeptCodeContainer36Object = findField('arrivedDeptCodeContainer36');
								arrivedDeptCodeContainer36Object.setVisible(!arrivedDeptHidden);
								
								// 到达部门
								var arrivedDeptCodeObject=null;

								arrivedDeptCodeObject = findField('arrivedDeptCodeWholecarOrder');
								//269701-lln-2015-08-28 begin											
								//整车时 到达部门始终可编辑；不管是否勾选 是否经过到达部门，到达部门都是可以编辑，并且必须录入的。			
								arrivedDeptCodeObject.setVisible(arrivedDeptHidden);			
								arrivedDeptCodeObject.allowBlank = !arrivedDeptHidden;			
								settingAllowBlank(arrivedDeptCodeObject);			
//								arrivedDeptCodeObject.setDisabled(arrivedDeptHidden);			
//								var isPassByArrivedDept = findField('isPassByArrivedDept').getValue();			
//								if(isPassByArrivedDept==true){			
//									arrivedDeptCodeObject.setDisabled(!arrivedDeptHidden);		
//									arrivedDeptCodeObject.allowBlank = !arrivedDeptHidden;		
//								}			
								//269701-lln-2015-08-28 begin	
								//0327版本 恢复FOSS整车约车
								arrivedDeptCodeObject = findField('arrivedDeptCode');			
								arrivedDeptCodeObject.setVisible(!arrivedDeptHidden);			
								//269701--foss--lln  2015--08--05 begin			
								//到达地址--省市区联动部分			
								arrivedAddressObject.setVisible(arrivedDeptHidden);			
								//到达地址--详细地址			
								detailAdress.setVisible(arrivedDeptHidden);			
								//269701--foss--lln  2015--08--05 end								
								isScaneSeeGoodsObject.setVisible(arrivedDeptHidden);
								isGoodsOverObject.setVisible(arrivedDeptHidden);
								
								scheduling.inviteVehicle.inviteVehicleApplyWindow.operateForm
										.doLayout();
							}
						}
					}
				}, {
					name : 'useType',
					fieldLabel : scheduling.inviteVehicle
							.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyQueryForm.useType'), // 用车类型
					columnWidth : .5,
					store : Ext.create('Ext.data.Store', {
						fields : ['valueName', 'valueCode'],
						data : [{
							'valueName' : scheduling.inviteVehicle
									.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyQueryForm.useType.store.tosalesdepart'),
							'valueCode' : 'TO_SALES_DEPARTMENT'
						},		// 到营业部
						{
							'valueName' : scheduling.inviteVehicle
									.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyQueryForm.useType.store.toclient'),
							'valueCode' : 'TO_CLIENT'
						},		// 到客户处
						{
							'valueName' : scheduling.inviteVehicle
							.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyQueryForm.useType.store.totransit'),
							'valueCode' : 'TO_TRANSIT'
						}     //到中转场
						]
					}),
					columnWidth : .5,
					xtype : 'combo',
					queryMode : 'local',
					displayField : 'valueName',
					valueField : 'valueCode',
					editable : false,
					forceSelection : true,
					triggerAction : 'all',
					allowBlank : false,
					listeners : {
						select : {
							fn : function(combo, records, eOpts) {
								var record = records[0];
								var operateForm = scheduling.inviteVehicle.inviteVehicleApplyWindow.operateForm
										.getForm();
								var findField = function(name) {
									return operateForm.findField(name);
								};
								var useType = record.get('valueCode');
								if (!useType || useType == 'TO_CLIENT') {
									findField('useAddress').setValue('');
									return;
								}
								// 带出默认值 用车部门
								var applyOrgCode = findField('applyOrgCode')
										.getValue();
								var inviteVehicleVo = {
									"inviteVehicleVo.inviteVehicleDto.useType" : useType,
									"inviteVehicleVo.inviteVehicleDto.applyOrgCode" : applyOrgCode
								};
								Ext.Ajax.request({
									url : scheduling
											.realPath('queryDepartmentAddress.action'),
									params : inviteVehicleVo,
									success : function(response) {
										var result = Ext
												.decode(response.responseText);
										findField('useAddress')
												.setValue(result.inviteVehicleVo.useVehicleAddress);
									},
									exception : function(response) {
										var result = Ext
												.decode(response.responseText);
										Ext.MessageBox
												.alert(
														scheduling.inviteVehicle
																.i18n('Foss.scheduling.validation.alert.title'),
														result.message);
									}
								});
						
							}
						}
					}
				}, {
					name : 'orderVehicleModel',
					fieldLabel : scheduling.inviteVehicle
							.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyQueryForm.orderVehicleModel'), // 用车车型
					columnWidth : .5,
					xtype : 'commonvehicletypeselector',
					allowBlank : false
				}, {
					name : 'applyOrgCode',
					xtype : 'dynamicorgcombselector',
					fieldLabel : scheduling.inviteVehicle
							.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyQueryForm.applyOrgCode'), // 用车部门
					type : 'ORG',
					salesDepartment : 'Y',// 查询营业部 配置此值
					transferCenter : 'Y',// 查询外场 配置此值
					doAirDispatch : 'Y',// 查询空运配载 配置此值
					transDepartment : 'Y',// 查询车队 配置此值
					dispatchTeam : 'Y',// 查询车队调度组 配置此值
					transTeam : 'Y',// 查询车队组 配置此值
					allowBlank : false,
					columnWidth : .5
				}, {
					columnWidth : .5,
					fieldLabel : scheduling.inviteVehicle
							.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyQueryForm.predictUseTime'), // 用车时间
					name : 'predictUseTime',
					xtype : 'datetimefield_date97',
					editable : false,
					time : true,
					id : 'Foss_scheduling_inviteVehicleApplyWindow_predictUseTime_ID',
					allowBlank : false,
					dateConfig : {
						el : 'Foss_scheduling_inviteVehicleApplyWindow_predictUseTime_ID-inputEl'
					}
				}, {
					name : 'dispatchTransDept',
					xtype : 'commonmotorcadeselector',
					topFleetOrgCode : FossUserContext.getCurrentDept().code,
					fieldLabel : scheduling.inviteVehicle
							.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.inviteVehicleApplyInfo.dispatchTransDept'), // 派车车队
					allowBlank : false,
					columnWidth : .5
				}, {
					name : 'applyEmpCode',
					xtype : 'commonemployeeselector',
					fieldLabel : scheduling.inviteVehicle
							.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.inviteVehicleApplyInfo.applyEmpName'), // 联系人姓名
					allowBlank : false,
					columnWidth : .5
				},{//310248
					name : 'businessType',
					fieldLabel : scheduling.inviteVehicle
					.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.inviteVehicleApplyInfo.businessType'), // 业务类型
					columnWidth : .5,
					xtype : 'combobox',
					store : Ext.create('Ext.data.Store', {
						fields : ['valueName', 'valueCode'],
						data : [{
							'valueName' : scheduling.inviteVehicle
									.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.inviteVehicleApplyInfo.pickDelivery'),
							'valueCode' : 'Pick_Delivery'
						},		// 接送货
								
						{
							'valueName' : scheduling.inviteVehicle
								         	.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.inviteVehicleApplyInfo.transfer'),
							'valueCode' : 'Transfer'
						},
							    // 转货
						{
							'valueName' : scheduling.inviteVehicle
								         	.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.inviteVehicleApplyInfo.bigCustomer'),
							'valueCode' : 'bigCustomer'
							// 大客户接货
						},
						{
							'valueName' : scheduling.inviteVehicle
								         	.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.inviteVehicleApplyInfo.expressWaybill'),
							'valueCode' : 'expressage'
								// 快递
						}
						
						]
					}),
					columnWidth : .5,
					xtype : 'combo',
					queryMode : 'local',
					displayField : 'valueName',
					valueField : 'valueCode',
					editable : false,
					forceSelection : true,
					triggerAction : 'all',
					allowBlank : false,
					hidden : true,
					listeners : {
						select : {
							fn : function(combo, records, eOpts) {
								var record = records[0];
								var operateForm = scheduling.inviteVehicle.inviteVehicleApplyWindow.operateForm
										.getForm();
								var findField = function(name) {
									return operateForm.findField(name);
								};
								
								var businessType = record.get('valueCode');
								if (!businessType || businessType == 'Transfer') {
							         findField('bearFeesDept1').setValue(null);
							         findField('bearFeesDept1').setVisible(false);
							         findField('bearFeesDept1').setValue(null);
							         findField('bearFeesDept1').allowBlank=true;
							         findField('bearFeesDept2').setValue(null);
							         findField('bearFeesDept2').setVisible(false);
							         findField('bearFeesDept2').setValue(null);
							         findField('bearFeesDept2').allowBlank=true;
							         findField('bearFeesDept').setVisible(true);
							         findField('bearFeesDept').allowBlank=false;
							         
							         return;
							     } else if ( businessType == 'bigCustomer'){
							         Ext.Msg.alert('系统提示','大客户接货需要满足:①车上仅装一个快递大客户货;②车价占整车收入占比<12%,方可发车。');
							         findField('bearFeesDept1').setValue(null);
							         findField('bearFeesDept1').setVisible(false);
							         findField('bearFeesDept1').setValue(null);
							         findField('bearFeesDept1').allowBlank=true;
							         findField('bearFeesDept2').setValue(null);
							         findField('bearFeesDept2').setVisible(false);
							         findField('bearFeesDept2').setValue(null);
							         findField('bearFeesDept2').allowBlank=true;
							         findField('bearFeesDept').setVisible(true);
							         findField('bearFeesDept').allowBlank=false;
							         return;
							     } else if ( businessType == 'expressage'){
							    	 findField('bearFeesDept1').setValue(null);
							         findField('bearFeesDept1').setVisible(false);
							         findField('bearFeesDept1').setValue(null);
							         findField('bearFeesDept1').allowBlank=true;
							         findField('bearFeesDept').setVisible(false);
							         findField('bearFeesDept').setValue(null);
							         findField('bearFeesDept').allowBlank=true;
							         findField('bearFeesDept2').setVisible(true);
							         findField('bearFeesDept2').allowBlank=false;
							         return;
							     } else {
							         findField('bearFeesDept').setVisible(false);
							         findField('bearFeesDept').setValue(null);
							         findField('bearFeesDept').allowBlank=true;
							         findField('bearFeesDept2').setValue(null);
							         findField('bearFeesDept2').setVisible(false);
							         findField('bearFeesDept2').setValue(null);
							         findField('bearFeesDept2').allowBlank=true;
							         findField('bearFeesDept1').setVisible(true);
							         findField('bearFeesDept1').allowBlank=false;
							     }
								
								// 带出默认值 用车部门
								var applyOrgCode = findField('applyOrgCode')
										.getValue();
								var applyOrgName = findField('applyOrgCode')
								        .getRawValue();
								var dispatchTransDept = findField('dispatchTransDept')
						        		.getValue();
								var inviteVehicleVo = {
										"inviteVehicleVo.inviteVehicleDto.applyOrgCode" : applyOrgCode,
										"inviteVehicleVo.inviteVehicleDto.applyOrgName" : applyOrgName,
										"inviteVehicleVo.inviteVehicleDto.dispatchTransDept" : dispatchTransDept
								};       
								Ext.Ajax.request({
									url : scheduling
											.realPath('queryBearFeesDept.action'),
									params : inviteVehicleVo,
									success : function(response) {
										var result = Ext.decode(response.responseText);
										/*if(Ext.isEmpty(result.inviteVehicleVo.comboList)){
											findField('bearFeesDept1').setValue(applyOrgCode);
											findField('bearFeesDept1').setRawValue(applyOrgName);
											
										};*/
										if(!Ext.isEmpty(result.inviteVehicleVo.comboList)){
											findField('bearFeesDept1').setValue(null);
											findField('bearFeesDept1').store.loadData(result.inviteVehicleVo.comboList);
										    findField('bearFeesDept1').expand();
										}
										
										
									},
									exception : function(response) {
										var result = Ext
												.decode(response.responseText);
										Ext.MessageBox
												.alert(
														scheduling.inviteVehicle
																.i18n('Foss.scheduling.validation.alert.title'),
														result.message);
									}
								});
						
							}
						}
					}
				}, {
					name : 'telephoneNo',
					fieldLabel : scheduling.inviteVehicle
							.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.inviteVehicleApplyInfo.telephoneNo'), // 固定电话
					maxLength : 20,
					maxLengthText : scheduling.inviteVehicle
							.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.inviteVehicleApplyInfo.telephoneNo.message'), // 固话号码已超过最大限制!
					columnWidth : .5
				}, {
					name : 'mobilephoneNo',
					fieldLabel : scheduling.inviteVehicle
							.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.inviteVehicleApplyInfo.mobilephoneNo'), // 手机
					maxLength : 20,
					maxLengthText : scheduling.inviteVehicle
							.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.inviteVehicleApplyInfo.mobilephoneNo.message'), // 手机号码已超过最大限制!
					columnWidth : .5
				},{//269701--foss--lln  2015--08--05 begin
					fieldLabel : scheduling.inviteVehicle
					.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.inviteVehicleApplyInfo.arrivedAddress'),//到达地址
					id:'arrivedAdressId',
					provinceWidth : 102,
					provinceLabel : '',
					provinceName:'provinceName',//省名称
					cityWidth : 102,
					cityLabel : '市',
					cityName : 'cityName',//名称
					areaLabel : '县',
					areaName : 'areaName',// 县名称
					areaWidth : 102,
					type : 'P-C-C',
					columnWidth:0.67,
					margin :'5,0,5,10',
					hidden: true,
					xtype : 'linkregincombselector', 
					areaLabelWidth: null,
				    provinceIsBlank: true,
				    cityIsBlank: true,
				    areaIsBlank: true   
				},{
					name:'detailAdress',
					id:'detailAdressId',
					xtype : 'textfield',
					margin :'5,10,5,0',
					hidden: true,
					columnWidth : .33
				},
				
//					{
//					name : 'arrivedAddress',
//					fieldLabel : scheduling.inviteVehicle
//							.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.inviteVehicleApplyInfo.arrivedAddress'), // 到达地址
//					hidden :true,
//					maxLength : 300,
//					maxLengthText : scheduling.inviteVehicle
//							.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.inviteVehicleApplyInfo.arrivedAddress.message'), // 到达地址已超过最大限制!
//					columnWidth : 1
//				},
				{//269701--foss--lln  2015--08--05 begin
					xtype : 'container',
					columnWidth : .14
					}, {
					name : 'arrivedDeptCodeContainer36',
					columnWidth : .36,
					value : '',
					readOnly : true
				},{
					name : 'isPassByArrivedDept',
					xtype : 'checkboxfield',
					hidden : true,
					boxLabel : scheduling.inviteVehicle
							.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.inviteVehicleApplyInfo.isPassByArrivedDept'), // 是否经过到达部门
					columnWidth : .36,
					inputValue : 'Y'//,269701-lln-2015-08-29 begin
						//不管有没有点 是否经过到达部门，到达部门都是可以编辑的
//					listeners : {
//						change : {
//							fn : function(src, newValue, oldValue, eOpts) {
//								var operateForm = scheduling.inviteVehicle.inviteVehicleApplyWindow.operateForm.getForm();
//								var findField = function(name) {
//									return operateForm.findField(name);
//								};
//								var value = (newValue && true == newValue);
//								var allowBlankStr = '&nbsp;&nbsp;';
//								if (value == true) {
//									allowBlankStr = '*';
//								}
//								var $ = function(id) {
//									return document.getElementById(id);
//								};
//								var settingAllowBlank = function(object) {
//									$(object.getId() + '-inputEl-prefix').innerHTML = allowBlankStr;
//								};
//								
//								arrivedDeptCodeObject = findField('arrivedDeptCodeWholecarOrder');
//								arrivedDeptCodeObject.setDisabled(!value);
//								if(value==false){
//									arrivedDeptCodeObject.setValue('');
//								}
//								arrivedDeptCodeObject.allowBlank = !value;
//								settingAllowBlank(arrivedDeptCodeObject);
//							}
//						}
//					}
						//269701-lln-2015-08-29 end
				}, {
					name : 'arrivedDeptCodeWholecarOrder',
					xtype:'commonsaledeptandouterbranchselector',
				    truckArrive:'Y',//可汽运到达
				    emptyText:scheduling.inviteVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.inviteVehicleApplyInfo.GPSAndChoose'),//请先用电子地图确认后再选择
					arrive : 'Y',//可到达
					productCode:'WVH',//整车（三级），“可自提”和“可派送”二选择一
					fieldLabel : scheduling.inviteVehicle
							.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.inviteVehicleApplyInfo.arrivedDeptCode'), // 到达部门
					columnWidth : .5,
					hidden : true,
					//269701--foss--lln 2015-08-11 begin
					listeners : {//监听到达部门控件失去焦点事件
						'select' : function(cmp,newValue,oldValue) {
							var operateForm = scheduling.inviteVehicle.inviteVehicleApplyWindow.operateForm
								.getForm();
							var findField = function(name) {
								return operateForm.findField(name);
							};
							//到达地址--省	
							var provinceNameCmp = findField('provinceName');
							//到达地址--市
							var cityNameCmp = findField('cityName');
							//到达地址--区/县
							var areaNameCmp = findField('areaName');
							//到达地址--详细地址
							var detailAdressCmp =findField('detailAdress');
							//使用类型
							var usePurpose=findField('usePurpose');
							//如果是整车约车，必须先输入到达地址
							if(usePurpose.getValue() == 'WHOLECAR_ORDER'){
								if(cmp.disabled==false &&( Ext.isEmpty(provinceNameCmp.getValue()) || Ext.isEmpty(cityNameCmp.getValue())
									|| Ext.isEmpty(areaNameCmp.getValue()) || Ext.isEmpty(detailAdressCmp.getValue()))){
									Ext.ux.Toast.msg(scheduling.inviteVehicle.i18n('Foss.scheduling.validation.alert.title')/*'提示'*/, '请先输入到达地址！', 'error', 3000);
									cmp.setValue('');
									detailAdressCmp.focus();
									return;
								}
								//通过部门code 查询该部门对应的省市区code
								var arrivedDeptCode = cmp.getValue();
								var arrivedDeptName = cmp.getRawValue();
								var inviteVehicleVo = {
										"inviteVehicleVo.inviteVehicleDto.arrivedDeptCode" : arrivedDeptCode,
										"inviteVehicleVo.inviteVehicleDto.arrivedDeptName" : arrivedDeptName
									};
								Ext.Ajax.request({
									url : scheduling.realPath('queryDepartmentCityCode.action'),
									params : inviteVehicleVo,
									success : function(response) {
										var result = Ext.decode(response.responseText);
										//到达部门对应的省市区的相应的code
										var provinceCode = result.inviteVehicleVo.inviteVehicleDto.provinceCode;//省
										var cityCode = result.inviteVehicleVo.inviteVehicleDto.cityCode;//市
										
										//到达地址与到达部门是不同一个省,不同一个市
										if(provinceNameCmp.getValue() != provinceCode
											&& cityNameCmp.getValue() != cityCode){
											Ext.MessageBox.alert(
													scheduling.inviteVehicle.i18n('Foss.scheduling.validation.alert.title'),
													scheduling.inviteVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.arrivedAdressNoarrivedDept')); // 请选择到达地址所在省/市的到达部门
											cmp.setValue('');//清空到达部门
											cmp.focus(false,100);//获取输入焦点
											return;
										}
									},
									exception : function(response) {
										var result = Ext.decode(response.responseText);
										Ext.MessageBox.alert(
												scheduling.inviteVehicle
														.i18n('Foss.scheduling.validation.alert.title'),
												result.message);
							}
						});
							}
					}
					}
				//269701--foss--lln 2015-08-11 end
				}, {
					name : 'arrivedDeptCode',
					xtype : 'dynamicorgcombselector',
					types : 'ORG,PX',
					salesDepartment : 'Y',// 查询营业部
					transferCenter : 'Y',// 查询外场
					fieldLabel : scheduling.inviteVehicle .i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.inviteVehicleApplyInfo.arrivedDeptCode'), // 到达部门
					columnWidth : .5			
				}, {//310248--foss 
					name : 'applyFees',
					fieldLabel : scheduling.inviteVehicle
							.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.inviteVehicleApplyInfo.applyFees'), // 外请车费用
					blankText: scheduling.inviteVehicle
					.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.inviteVehicleApplyInfo.applyFees.message1'), // 填写要求数字单位元!
					regex: /^\d+(\.\d{1,2})?$/,
					regexText: scheduling.inviteVehicle
					.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.inviteVehicleApplyInfo.applyFees.message'), // 请输入正确的数据类型!
					allowBlank : false,
					hidden : true,
					columnWidth : .5
				}, {//310248
					name : 'bearFeesDept',
					xtype : 'dynamicorgcombselector',
					fieldLabel : scheduling.inviteVehicle
							.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.inviteVehicleApplyInfo.bearFeesDept'), //费用承担部门
					type : 'ORG',
					allowBlank : false,
					hidden : true,
					columnWidth : .5
				}, {//332219
					name : 'bearFeesDept2',
					xtype : 'commonexpressageselector',
					fieldLabel : scheduling.inviteVehicle
							.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.inviteVehicleApplyInfo.bearFeesDept'), //费用承担部门
					type : 'ORG',
					allowBlank : false,
					hidden : true,
					columnWidth : .5
				},{//310248
			    	xtype:'combobox',
			    	name:'bearFeesDept1',
			    	fieldLabel : scheduling.inviteVehicle
					.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.inviteVehicleApplyInfo.bearFeesDept'), //费用承担部门
			    	queryMode: 'local', 
			    	allowBlank : false,
					hidden : true,
			    	editable:false,
			    	store:Ext.create('Foss.scheduling.inviteVehicle.OrgStore'),
					displayField:'applyPath',
					columnWidth : .5,
					valueField:'ministryinformationCode'
			    }, {
					name : 'useAddress',
					fieldLabel : scheduling.inviteVehicle
							.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.inviteVehicleApplyInfo.useAddress'), // 用车地址
					allowBlank : false,
					maxLength : 300,
					maxLengthText : scheduling.inviteVehicle
							.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.inviteVehicleApplyInfo.useAddress.message'), // 用车地址已超过最大限制!
					columnWidth : 1
				}]
			}]
		})
		return this.inviteVehicleApplyInfo;
	},
	createCustomerAndGoodsInfo : function() {
		if (this.customerAndGoodsInfo) {
			return this.customerAndGoodsInfo;
		}
		this.customerAndGoodsInfo = Ext.create('Ext.form.FieldSet', {
			defaultType : 'textfield',
			title : scheduling.inviteVehicle
					.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.customerAndGoodsInfo.title'), // 客户和货物信息
			width : 675,
			items : [{
				xtype : 'container',
				layout : 'column',
				defaultType : 'textfield',
				defaults : {
					labelWidth : 105,
					margin : '5 10 5 10'
				},
				items : [{
					name : 'goodsName',
					fieldLabel : scheduling.inviteVehicle
							.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.customerAndGoodsInfo.goodsName'), // 货物名称
					maxLength : 60,
					maxLengthText : scheduling.inviteVehicle
							.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.customerAndGoodsInfo.goodsName.message'), // 货物名称已超过最大限制!
					columnWidth : .5
				}, {
					name : 'weight',
					fieldLabel : scheduling.inviteVehicle
							.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.customerAndGoodsInfo.weight'), // 货物重量
					//regex : /^(\d{1,5})(\.\d{1,3})?$/,
					//regexText : scheduling.inviteVehicle
					//		.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.customerAndGoodsInfo.weight.message'), // 重量格式输入有误.必须是数字!
					maxLength : 9,
					maxLengthText : scheduling.inviteVehicle
							.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.customerAndGoodsInfo.weight.message1'), // 重量已超过最大限制!
					columnWidth : .5,
					validator : function(value){
						if(Ext.isEmpty(value)){
							value=null;
						}
						if(null != value){
							value = value*1;
						}
						scheduling.inviteVehicle.inviteVehicleApplyWindow.minWeightVehicle=scheduling.inviteVehicle.inviteVehicleApplyWindow.minWeightVehicle*1;
						scheduling.inviteVehicle.inviteVehicleApplyWindow.maxWeightVehicle=scheduling.inviteVehicle.inviteVehicleApplyWindow.maxWeightVehicle*1;

						if(this.allowBlank && (null == value || 0 == value)){
							return true;
						}else if(!(/^(\d{1,5})(\.\d{1,3})?$/.test(value))){
							return scheduling.inviteVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.customerAndGoodsInfo.weight.message'); //重量格式输入有误.必须是数字!
						}else if(value >= scheduling.inviteVehicle.inviteVehicleApplyWindow.minWeightVehicle && value <= scheduling.inviteVehicle.inviteVehicleApplyWindow.maxWeightVehicle){
							Ext.MessageBox
							.alert(
									scheduling.inviteVehicle
											.i18n('Foss.scheduling.validation.alert.title'),
									scheduling.inviteVehicle
											.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.minWeightVehicle.message')); // 约车重量偏高，检查确认是否录入正确值
							return true;
						}else if(value > scheduling.inviteVehicle.inviteVehicleApplyWindow.maxWeightVehicle){
							return scheduling.inviteVehicle
							.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.customerAndGoodsInfo.weight.message1');// 重量已超过最大限制!
						}else if(null == value || 0 == value){
							return scheduling.inviteVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.customerAndGoodsInfo.weight.message'); //重量格式输入有误.必须是数字!
						}
						return true;
					}
				}, {
					name : 'goodsPackege',
					fieldLabel : scheduling.inviteVehicle
							.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.customerAndGoodsInfo.goodsPackage'), // 货物包装
					maxLength : 60,
					maxLengthText : scheduling.inviteVehicle
							.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.customerAndGoodsInfo.goodsPackage.message'), // 货物包装已超过最大限制!
					columnWidth : .5
				}, {
					name : 'goodsQty',
					fieldLabel : scheduling.inviteVehicle
							.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.customerAndGoodsInfo.goodsQty'), // 货物件数
					/* BUG-15164 */
					regex : /^\d+$/,
					regexText : scheduling.inviteVehicle
							.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.customerAndGoodsInfo.goodsQty.message'), // 货物件数格式输入有误.必须是数字!
					maxLength : 9,
					maxLengthText : scheduling.inviteVehicle
							.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.customerAndGoodsInfo.goodsQty.message1'), // 货物件数已超过最大限制!
					columnWidth : .5
				}, {
					name : 'volume',
					fieldLabel : scheduling.inviteVehicle
							.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.customerAndGoodsInfo.volume'), // 货物体积
					//regex : /^(\d{1,5})(\.\d{1,3})?$/,
					//regexText : scheduling.inviteVehicle
					//		.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.customerAndGoodsInfo.volume.message'), // 体积格式输入有误.必须是数字!
					maxLength : 9,
					maxLengthText : scheduling.inviteVehicle
							.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.customerAndGoodsInfo.volume.message1'), // 体积已超过最大限制!
					columnWidth : .5,
					validator : function(value){
						if(Ext.isEmpty(value)){
							value=null;
						}
						if(null != value){
							value = value*1;
						}
						scheduling.inviteVehicle.inviteVehicleApplyWindow.minVolumeVehicle=scheduling.inviteVehicle.inviteVehicleApplyWindow.minVolumeVehicle*1;
						scheduling.inviteVehicle.inviteVehicleApplyWindow.maxVolumeVehicle=scheduling.inviteVehicle.inviteVehicleApplyWindow.maxVolumeVehicle*1;
						if(this.allowBlank && (null == value || 0 == value)){
							return true;
						}else if(!(/^(\d{1,5})(\.\d{1,3})?$/.test(value))){
							return scheduling.inviteVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.customerAndGoodsInfo.volume.message'); // 体积格式输入有误.必须是数字!
						}else if(value >= scheduling.inviteVehicle.inviteVehicleApplyWindow.minVolumeVehicle && value <= scheduling.inviteVehicle.inviteVehicleApplyWindow.maxVolumeVehicle){
							Ext.MessageBox
							.alert(
									scheduling.inviteVehicle
											.i18n('Foss.scheduling.validation.alert.title'),
									scheduling.inviteVehicle
											.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.minVolumeVehicle.message')); // 约车体积偏高，检查确认是否录入正确值
							return true;
						}else if(value > scheduling.inviteVehicle.inviteVehicleApplyWindow.maxVolumeVehicle){
							return scheduling.inviteVehicle
							.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.customerAndGoodsInfo.volume.message1'); // 体积已超过最大限制
						}else if(null == value || 0 == value){
							return scheduling.inviteVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.customerAndGoodsInfo.volume.message'); // 体积格式输入有误.必须是数字!
						}
						return true;
					}
				}, {
					columnWidth : .5,
					fieldLabel : scheduling.inviteVehicle
							.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.customerAndGoodsInfo.loadGoodsTime'), // 装货时间
					name : 'loadGoodsTime',
					xtype : 'datetimefield_date97',
					editable : false,
					time : true,
					id : 'Foss_scheduling_inviteVehicleApplyWindow_loadGoodsTime_ID',
					dateConfig : {
						el : 'Foss_scheduling_inviteVehicleApplyWindow_loadGoodsTime_ID-inputEl'
					}
				},
				{
					name : 'isScaneSeeGoods',
					fieldLabel : scheduling.inviteVehicle
							.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.customerAndGoodsInfo.isScaneSeeGoods'), // 是否现场看货
					xtype : 'combobox',
					store : Ext.create('Ext.data.Store', {
						fields : ['valueName', 'valueCode'],
						data : [{
							'valueName' : scheduling.inviteVehicle
									.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.customerAndGoodsInfo.isScaneSeeGoods.store.yes'),
							'valueCode' : 'Y'
						},		// 是
						{
							'valueName' : scheduling.inviteVehicle
									.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.customerAndGoodsInfo.isScaneSeeGoods.store.no'),
							'valueCode' : 'N'
						}		// 否
						]
					}),
					queryMode : 'local',
					displayField : 'valueName',
					valueField : 'valueCode',
					hidden :true,
					editable : false,
					forceSelection : true,
					triggerAction : 'all',
					columnWidth : .5
				},
				{
					name : 'isGoodsOver',
					fieldLabel : scheduling.inviteVehicle
							.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.customerAndGoodsInfo.isGoodsOver'), // 货物是否可叠加
					xtype : 'combobox',
					store : Ext.create('Ext.data.Store', {
						fields : ['valueName', 'valueCode'],
						data : [{
							'valueName' : scheduling.inviteVehicle
									.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.customerAndGoodsInfo.isGoodsOver.store.yes'),
							'valueCode' : 'Y'
						},		// 是
						{
							'valueName' : scheduling.inviteVehicle
									.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.customerAndGoodsInfo.isGoodsOver.store.no'),
							'valueCode' : 'N'
						}		// 否
						]
					}),
					queryMode : 'local',
					displayField : 'valueName',
					valueField : 'valueCode',
					hidden :true,
					editable : false,
					forceSelection : true,
					triggerAction : 'all',
					columnWidth : .5
				},{
					name : 'requirment',
					fieldLabel : scheduling.inviteVehicle.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.customerAndGoodsInfo.remark'), // '备注',
					maxLength : 60,
					maxLengthText : scheduling.inviteVehicle
							.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.customerAndGoodsInfo.remark.message'), // 备注输入长度已超过最大限制!
					columnWidth : 1
				}, {
					name : 'clientName',
					fieldLabel : scheduling.inviteVehicle
							.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.customerAndGoodsInfo.clientName'),
					maxLength : 60,
					maxLengthText : scheduling.inviteVehicle
							.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.customerAndGoodsInfo.clientName.message'),
					columnWidth : .5
				}, {
					name : 'clientContactPhone',
					fieldLabel : scheduling.inviteVehicle
							.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.customerAndGoodsInfo.clientContactPhone'), // 发货人电话
					maxLength : 20,
					maxLengthText : scheduling.inviteVehicle
							.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.customerAndGoodsInfo.clientContactPhone.message'), // 发货人电话已超过最大限制!
					columnWidth : .5
				}]
			}]
		})
		return this.customerAndGoodsInfo;
	},
	createButtonArea : function() {
		if (this.buttonArea) {
			return this.buttonArea;
		}
		this.buttonArea = Ext.create('Ext.container.Container', {
			defaultType : 'button',
			name : 'buttonArea',
			defaults : {
				margin : '2 7 2 7'
			},
			saveVehicleApply : function(url, status, callback) {
				var inviteVehicleApplyForm = scheduling.inviteVehicle.inviteVehicleApplyForm;
				var operateForm = inviteVehicleApplyForm.getForm();
				var findField = function(name) {
					return operateForm.findField(name);
				}
				
				var arrivedDeptCodeAndWholecarOrder=null
				if(findField('arrivedDeptCode').hidden){
					arrivedDeptCodeAndWholecarOrder=findField('arrivedDeptCodeWholecarOrder');
				}else{
					arrivedDeptCodeAndWholecarOrder = findField('arrivedDeptCode');
					findField('arrivedDeptCodeWholecarOrder').allowBlank = true;
				}
				
				if (!operateForm.isValid()) {
					Ext.MessageBox
							.alert(
									scheduling.inviteVehicle
											.i18n('Foss.scheduling.validation.alert.title'),
									scheduling.inviteVehicle
											.i18n('Foss.scheduling.validation.tip.input')); // 有必填项未完成!
					return false;
				}
				// 固话 与 手机号码必须输入一个
				var telephoneNo = findField('telephoneNo').getValue();
				var mobilephoneNo = findField('mobilephoneNo').getValue();
				if (Ext.isEmpty(telephoneNo) && Ext.isEmpty(mobilephoneNo)) {
					Ext.MessageBox
							.alert(
									scheduling.inviteVehicle
											.i18n('Foss.scheduling.validation.alert.title'),
									scheduling.inviteVehicle
											.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.customerAndGoodsInfo.submit.validation.message')); // 固话 与
																																								// 手机号码必须输入一个!
					return false;
				}
				// 到达部门
				var isPassByArrivedDept = findField('isPassByArrivedDept').getValue();

				var record = operateForm.getRecord();
				operateForm.updateRecord(record);
				record.data.status = status;
				record.data.isPassByArrivedDept = (true == isPassByArrivedDept
						? 'Y'
						: 'N');
				record.data.applyOrgName = findField('applyOrgCode').getRawValue();
				record.data.arrivedDeptName = findField('arrivedDeptCode').getRawValue(); 
				record.data.dispatchTransDeptName = findField('dispatchTransDept').getRawValue(); 

				//校验到达部门不能与用车部门一样
				if(record.data.applyOrgName == arrivedDeptCodeAndWholecarOrder.getRawValue()){
					Ext.MessageBox
					.alert(
							scheduling.inviteVehicle
									.i18n('Foss.scheduling.validation.alert.title'),
							scheduling.inviteVehicle
									.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.arrivalcarnotsame')); // 用车部门与到达部门不能相同!
					return false;
				}
				
				if(findField('arrivedDeptCode').hidden){
					record.data.arrivedDeptCode=findField('arrivedDeptCodeWholecarOrder').getValue();
					record.data.arrivedDeptName=findField('arrivedDeptCodeWholecarOrder').getRawValue();
				}
				
				//310248
				if(!findField('bearFeesDept').hidden){
					record.data.bearFeesDeptCode=findField('bearFeesDept').getValue();
					record.data.bearFeesDeptName=findField('bearFeesDept').getRawValue();
				}
				//310248
				if(!findField('bearFeesDept1').hidden){
					record.data.bearFeesDeptCode=findField('bearFeesDept1').getValue();
					record.data.bearFeesDeptName1=findField('bearFeesDept1').getRawValue();
				}
				//332219
				if(!findField('bearFeesDept2').hidden){
					record.data.bearFeesDeptCode=findField('bearFeesDept2').getValue();
					record.data.bearFeesDeptName2=findField('bearFeesDept2').getRawValue();
				}
				if(!findField('businessType').hidden){
					//record.data.bearFeesDeptNameCode=findField('bearFeesDeptName').getValue();
					record.data.businessType=findField('businessType').getValue();
				}
				//269701--foss--lln  2015-08-12 begin
				//整车约车时，获取到达地址信息
				var usePurpose = findField('usePurpose').getValue();
				if (usePurpose == 'WHOLECAR_ORDER'){
					//到达地址--省
					record.data.provinceCode=findField('provinceName').getValue();
					record.data.provinceName=findField('provinceName').getRawValue();
					//到达地址--市
					record.data.cityCode=findField('cityName').getValue();
					record.data.cityName=findField('cityName').getRawValue();
					//到达地址--市
					record.data.areaCode=findField('areaName').getValue();
					record.data.areaName=findField('areaName').getRawValue();
					//到达地址--详细地址
					record.data.detailAdress=findField('detailAdress').getValue();
					//269701--foss--lln  2015-08-12 end
				}
				record.data.applyEmpName = findField('applyEmpCode')
						.getRawValue();
				record.data.predictUseTime = Ext.Date.parse(
						findField('predictUseTime').getValue(), 'Y-m-d H:i:s');

				if (!record.data.weight) {
					record.data.weight = 0;
				}
				if (!record.data.goodsQty) {
					record.data.goodsQty = 0;
				}
				if (!record.data.volume) {
					record.data.volume = 0;
				}
				if (findField('loadGoodsTime').getValue() != null
						&& findField('loadGoodsTime').getValue() != '') {
					record.data.loadGoodsTime = Ext.Date.parse(
							findField('loadGoodsTime').getValue(),
							'Y-m-d H:i:s');
				}
				// 校验整数部分
				var weight = parseInt(record.data.weight) / 10000;
				if (weight >= 10) {
					Ext.MessageBox
							.alert(
									scheduling.inviteVehicle
											.i18n('Foss.scheduling.validation.alert.title'),
									scheduling.inviteVehicle
											.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.customerAndGoodsInfo.submit.validation.message1')); // 货物重量整数部分最多9位!
					return false;
				}
				var volume = parseInt(record.data.volume) / 10000;
				if (volume >= 10) {
					Ext.MessageBox
							.alert(
									scheduling.inviteVehicle.i18n('Foss.scheduling.validation.alert.title'),
									scheduling.inviteVehicle
											.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.customerAndGoodsInfo.submit.validation.message1')); // 货物体积整数部分最多5位!
					return false;
				}
				var inviteVehicleVo = {
					inviteVehicleVo : {
						inviteVehicleDto : record.data
					}
				};
				inviteVehicleApplyForm.getEl().mask(scheduling.inviteVehicle
						.i18n('Foss.scheduling.saving'));
				Ext.Ajax.request({
					url : scheduling.realPath(url),
					jsonData : inviteVehicleVo,
					success : function(response) {
						inviteVehicleApplyForm.getEl().unmask();
						var result = Ext.decode(response.responseText);
						var inviteVehicleNo = result.inviteVehicleVo.inviteVehicleDto.inviteNo;
						//超过标记金额提示  332219
						var vehicleCost = result.inviteVehicleVo.inviteVehicleDto.vehicleCost;
						
						if(vehicleCost=='' || vehicleCost==null){
							Ext.MessageBox.alert(scheduling.inviteVehicle.i18n('foss.scheduling.adjustTransportationPath.hint'), scheduling.inviteVehicle.i18n('foss.scheduling.passOrderVehicle.vehicleInputForm.orderNo.l') + inviteVehicleNo);
						}else{
							Ext.MessageBox.alert(scheduling.inviteVehicle.i18n('foss.scheduling.adjustTransportationPath.hint'), "<center>" +scheduling.inviteVehicle.i18n('foss.scheduling.passOrderVehicle.vehicleInputForm.orderNo.l') + inviteVehicleNo + "</center><br>" + scheduling.inviteVehicle.i18n('Foss.scheduling.inviteVehicle.error')+vehicleCost+scheduling.inviteVehicle.i18n('Foss.scheduling.inviteVehicle.vehicleCost'));
						};
						
						scheduling.inviteVehicle.inviteVehicleApplyWindow
							.close();
						
						scheduling.inviteVehicle.pagingBar.moveFirst();
					},
					exception : function(response) {
						inviteVehicleApplyForm.getEl().unmask();
						var result = Ext.decode(response.responseText);
						Ext.MessageBox
								.alert(
										scheduling.inviteVehicle
												.i18n('Foss.scheduling.validation.alert.title'),
										result.message);
					}
				});
			
			},
			layout : 'column',
			items : [{
						xtype : 'container',
						html : '&nbsp;',
						columnWidth : .29
					}, {
						disabled : !scheduling.inviteVehicle.isPermission('scheduling/submitInviteVehicleApplyButton'),
						hidden : !scheduling.inviteVehicle.isPermission('scheduling/submitInviteVehicleApplyButton'),
						text : scheduling.inviteVehicle
								.i18n('Foss.scheduling.button.submit'), // 提交
						columnWidth : .14,
						handler : function() {
							var url = "saveInviteVehicleApply.action";
							var inviteVehicleApplyForms = scheduling.inviteVehicle.inviteVehicleApplyForm;
							var operateForms = inviteVehicleApplyForms.getForm();
							var usePurpose = operateForms.findField('usePurpose').getValue();
							var isPassByArrivedDept = operateForms.findField('isPassByArrivedDept').getValue();
							var buttonPanel=this.ownerCt;
							//269701--foss--lln 2015-08-11 begin
							//到达地址--省
							var provinceName = operateForms.findField('provinceName').getValue();
							//到达地址--市
							var cityName = operateForms.findField('cityName').getValue();
							//到达地址--区
							var areaName = operateForms.findField('areaName').getValue();
							//到达地址--详细地址
							var detailAdress = operateForms.findField('detailAdress').getValue();
							//269701--foss--lln 2015-08-11 end
							if (!operateForms.isValid()) {
								Ext.MessageBox.alert(scheduling.inviteVehicle.i18n('Foss.scheduling.validation.alert.title'),
										scheduling.inviteVehicle.i18n('Foss.scheduling.validation.tip.input')); // 有必填项未完成!
								return false;
							}
							if (usePurpose == 'WHOLECAR_ORDER'){
								//269701--foss--lln 2015-08-11 begin
								//整车时候，做出校验--省市区联动+详细地址 必须入力，不可为空
								if(Ext.isEmpty(provinceName) || Ext.isEmpty(cityName) 
										|| Ext.isEmpty(areaName) || Ext.isEmpty(detailAdress)){
									//message:整车约车时,到达地址（省市区、详细地址）均不能为空!
									Ext.MessageBox.alert(
											scheduling.inviteVehicle.i18n('Foss.scheduling.validation.alert.title'),
											scheduling.inviteVehicle
											.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.arrivedAdressNoInput')); // 整车约车时,到达地址（省市区、详细地址）均不能为空!
									return false;
								} 
								//269701--foss--lln 2015-08-11 end
								if(isPassByArrivedDept==false){
									Ext.Msg.confirm(scheduling.inviteVehicle.i18n('Foss.scheduling.validation.alert.title'), 
											scheduling.inviteVehicle.i18n('Foss.scheduling.validation.wholeCarOrder.tips'), 
											function(optional){
											if(optional == 'yes'){
												buttonPanel.saveVehicleApply(url, 'UNCOMMITTED');
											}else{
												return ;
											}
									});
								}else{
									buttonPanel.saveVehicleApply(url, 'UNCOMMITTED');
								}
							}else{
								buttonPanel.saveVehicleApply(url, 'UNCOMMITTED');
							};
							
						}
					}, {
						name : 'doTemporarySaveBtn',
						disabled : !scheduling.inviteVehicle.isPermission('scheduling/saveInviteVehicleApplyButton'),
						hidden : !scheduling.inviteVehicle.isPermission('scheduling/saveInviteVehicleApplyButton'),
						text : scheduling.inviteVehicle
								.i18n('Foss.scheduling.button.keepin'), // 暂存
						columnWidth : .14,
						handler : function() {
							var url = "saveInviteVehicleApply.action";
							this.ownerCt.saveVehicleApply(url, 'STAGING');
						}
					}, {
						name : 'resetBtn',
						text : scheduling.inviteVehicle
								.i18n('Foss.scheduling.button.reset'), // 重置
						columnWidth : .14,
						handler : function() {
							var inviteNo = this.ownerCt.ownerCt.ownerCt.inviteNo;
							if (!inviteNo) {
								// 新增重置
								scheduling.inviteVehicle.inviteVehicleApplyForm
										.getForm().reset();
								// 默认值
								scheduling.inviteVehicle.inviteVehicleApplyWindow
										.setDefaultValue();
								return;
							}
							// 修改重置
							this.ownerCt.ownerCt.ownerCt.initData();
						}
					}, {
						xtype : 'container',
						html : '&nbsp;',
						columnWidth : .29
					}]
		});
		return this.buttonArea;
	},
	createOperateForm : function() {
		if (this.operateForm) {
			return this.operateForm;
		}
		this.operateForm = Ext.create('Ext.form.Panel', {
					frame : false
				});
		return this.operateForm;
	},
	initAllComponent : function() {
		this.createInviteVehicleConfigurationParams();
		this.createInviteVehicleApplyInfo();
		this.createCustomerAndGoodsInfo();
		this.createButtonArea();
	},
	setDefaultValue : function() {
		// 用户姓名
		var userName = FossUserContext.getCurrentUser().employee.empName;
		var userCode = FossUserContext.getCurrentUser().employee.empCode;
		// 部门名称 编码
		var deptName = FossUserContext.getCurrentDept().name;
		var deptcode = FossUserContext.getCurrentDept().code;
		// 手机号码
		var mobilePhone = FossUserContext.getCurrentUserEmp().mobilePhone;
		var form = scheduling.inviteVehicle.inviteVehicleApplyWindow.operateForm
				.getForm();
		var findField = function(name) {
			return form.findField(name);
		};
		// 用车部门选择器
		findField('applyOrgCode').setCombValue(deptName, deptcode);
		// 联系人姓名
		findField('applyEmpCode').setCombValue(userName, userCode);
		// 手机
		findField('mobilephoneNo').setValue(mobilePhone);
		// 隐藏到达部门选项
		this.setArrivedDeptVisible(false);
	},
	setArrivedDeptVisible : function(hidden) {
		var form = scheduling.inviteVehicle.inviteVehicleApplyWindow.operateForm
				.getForm();
		var findField = function(name) {
			return form.findField(name);
		};
		var isPassByArrivedDeptObject = findField('isPassByArrivedDept');
		isPassByArrivedDeptObject.setVisible(hidden);
		
		//269701--foss--lln  2015--08--05 begin
		//到达地址--省市区联动部分
		var arrivedAddressObject = Ext.getCmp('arrivedAdressId');
		arrivedAddressObject.setVisible(hidden);
		//到达地址--省市区后面的详细地址
		var detailAddressObject = findField('detailAdress');
		detailAddressObject.setVisible(hidden);
		//设置省市区联动控件和详细地址的间距，省市区地址本身控件的原因，会有5px的间隙
		Ext.fly("arrivedAdressId").setStyle('margin-right','0px');
		Ext.fly("arrivedAdressId").setStyle('margin-left','10px');
		Ext.fly("detailAdressId").setStyle('margin-left','0px');
		//269701--foss--lln  2015--08--05 end
		
		var arrivedDeptCodeContainer36Object = findField('arrivedDeptCodeContainer36');
		arrivedDeptCodeContainer36Object.setVisible(!hidden);
		//var arrivedDeptCodeObject = findField('arrivedDeptCode');
		//arrivedDeptCodeObject.setVisible(hidden);
	},
	initData : function() {
		var inviteNo = this.inviteNo;
		var form = this.operateForm;
		if (!inviteNo) {
			form.getForm().findField('orderVehicleModel').setValue(null);
			form.getForm().findField('applyOrgCode').setValue(null);
			form.getForm().findField('dispatchTransDept').setValue(null);
			form
					.loadRecord(Ext
							.create('Foss.scheduling.inviteVehicle.InviteVehicleApplyFormModel'));
			scheduling.inviteVehicle.inviteVehicleApplyForm.getForm().reset();
			this.setDefaultValue();
			return;
		}
		var setArrivedDeptVisible = this.setArrivedDeptVisible;
		var items = form.items.items;
		var buttonAreaItem = items[2].items.items;
		var doTemporarySaveBtn = buttonAreaItem[2];
		doTemporarySaveBtn.hidden = !doTemporarySaveBtn.hidden;
		var params = {
			"inviteVehicleVo.inviteVehicleDto.inviteNo" : inviteNo
		};
		Ext.Ajax.request({
			url : scheduling.realPath('queryInviteVehicleApplyDetail.action'),
			params : params,
			success : function(response) {
				var result = Ext.decode(response.responseText);
				var record = Ext
						.create(
								'Foss.scheduling.inviteVehicle.InviteVehicleApplyFormModel',
								result.inviteVehicleVo.inviteVehicleDto);
				form.loadRecord(record);
				var operateForm = form.getForm();
				var findField = function(name) {
					return operateForm.findField(name);
				};
				var data = record.data;
				var hidden = false;
				if (data.usePurpose == 'WHOLECAR_ORDER') {
					findField('usePurpose').fireEvent('select',
							findField('usePurpose'));
					hidden = true;
				}
				setArrivedDeptVisible(hidden);
				// 用车车型
				findField('orderVehicleModel').setCombValue(
						data.vehicleLengthName, data.orderVehicleModel);
				// 用车部门选择器
				findField('applyOrgCode').setCombValue(data.applyOrgName,
						data.applyOrgCode);
				// 派车车队
				findField('dispatchTransDept').setCombValue(
						data.dispatchTransDeptName, data.dispatchTransDept);
				// 到达部门
				findField('arrivedDeptCode').setCombValue(data.arrivedDeptName,
						data.arrivedDeptCode);
			},
			exception : function(response) {
				var result = Ext.decode(response.responseText);
				Ext.MessageBox
						.alert(
								scheduling.inviteVehicle
										.i18n('Foss.scheduling.validation.alert.title'),
								result.message);
			}
		});
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([cfg]);
		me.initAllComponent();
		var form = me.createOperateForm();
		form.add([me.inviteVehicleApplyInfo, me.customerAndGoodsInfo,
				me.buttonArea]);
		scheduling.inviteVehicle.inviteVehicleApplyForm = form;
		me.add([form]);
	}
});