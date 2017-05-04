load.outsidevehiclecharge.query = '';
load.outsidevehiclecharge.awardMaxFee = '';//
load.outsidevehiclecharge.awardMinFee = '';
load.outsidevehiclecharge.fineMaxFee = '';
load.outsidevehiclecharge.fineMinFee = '';
load.outsidevehiclecharge.timelinessAwardMaxFee = '';// 时效协议奖励最大值
load.outsidevehiclecharge.timelinessFineMaxFee = '';// 时效协议罚款最大值

/**
 * 外请车Model
 */
Ext.define('Foss.load.outsideVehicleChargeModel', {
	extend: 'Ext.data.Model',
	//定义字段
	fields: [
			{name: 'id',type:'string'},
			{name: 'vehicleassembleNo',type:'string'},//配载车次号
			{name: 'assembleType',type:'string'},//配载类型
			{name: 'vehicleNo',type:'string'},//车牌号
			{name: 'driverCode',type:'string'},//主驾驶编号
			{name: 'driverName',type:'string'},//主驾驶姓名
			{name: 'handoverTime',type: 'date',
				convert: function(value) {
					if (value != null) {
						var date = new Date(value);	
						return Ext.Date.format(date,'Y-m-d');
					} else {
						return null;
					}
				}},	//交接日期
			{name: 'origOrgCode',type:'string'},//出发部门编号
			{name: 'origOrgName',type:'string'},//出发部门名称
			{name: 'destOrgCode',type:'string'},//到达部门编号
			{name: 'destOrgName',type:'string'},//到达部门名称
			{name: 'feeTotal',type:'string'},//总运费
			{name: 'prepaidFeeTotal'},//预付运费总额
			{name: 'arriveFeeTotal'},//到付运费总额
			{name: 'awardType',type:'string'},//奖罚类型
			{name: 'initAmt'},//初始金额
			{name: 'adjustFee'},//调整费用
			{name: 'timelinessClause'},//是否时效条款
			{name: 'timelinessDuration'},//变化时长
			{name: 'cause'},//产生原因
			{name: 'timelinessDurationStr'},//变化时长(中文)
			{name: 'adjustReason',type:'string'},//调整原因
			{name: 'otherReason',type:'string'},//其他原因
			{name: 'auditorCode',type:'string'},//审核人编号
			{name: 'auditorName',type:'string'},//审核人名称
			{name: 'auditState',type:'string'},//部门经理审核状态
			{name: 'auditOpinion',type:'string'},//审批意见
			{name: 'driverMobilePhone',type:'string'},//司机电话
			{name: 'loadFeeTotal'},//装车总金额
			{name: 'goodsType',type:'string'}//货物类型		
			
			
	]
});
/**
 * 外请车store
 */
Ext.define('Foss.load.outsideVehicleChargeStore', {
	extend: 'Ext.data.Store',
	model: 'Foss.load.outsideVehicleChargeModel',
	pageSize:10,
	autoLoad: true,
	proxy: {
		type: 'ajax',
		url: load.realPath('queryOutsideVehicleChargeList.action'),
		actionMethods: {read: 'POST'},
		reader: {
			type: 'json',
			root: 'vo.adjustOutVehicleFeeList',
			totalProperty : 'totalCount',
			successProperty: 'success'
		}
	},
	listeners : {
		beforeload : function(store, operation, eOpts) {
			// undefined
			var queryForm = load.queryForm;
			// 2013年12月11日 15:14:13 by zyx begin
			// TR 为本页面的查询
			// 为空则来自出发到达界面中调整外请车费用按钮
			// end
			if (load.outsidevehiclecharge.query == 'TR') {
				if (queryForm != null) {
					var queryParams = queryForm.getValues();
					Ext.apply(operation, {
						params : {
							'vo.adjustOutVehicleFeeDto.vehicleassembleNo' : queryParams.vehicleassembleNo, // 配载车次号
							'vo.adjustOutVehicleFeeDto.driverName' : queryParams.driverName, // 主驾驶姓名
							// 'vo.adjustOutVehicleFeeDto.handoverTime' : queryParams.handoverTime, //日期
							'vo.adjustOutVehicleFeeDto.beginTime' : queryParams.beginTime, // 开始日期
							'vo.adjustOutVehicleFeeDto.endTime' : queryParams.endTime, // 结束日期
							'vo.adjustOutVehicleFeeDto.auditState' : queryParams.auditState, // 审核状态
							'vo.adjustOutVehicleFeeDto.vehicleNo' : queryParams.vehicleNo, // 车牌号
							'vo.adjustOutVehicleFeeDto.origOrgCode' : queryParams.origOrgCode, // 出发部门
							'vo.adjustOutVehicleFeeDto.destOrgCode' : queryParams.destOrgCode, // 到达部门
							'vo.adjustOutVehicleFeeDto.awardType' : queryParams.awardType, // 奖罚类型
							'vo.adjustOutVehicleFeeDto.assembleType' : queryParams.assembleType // 配载类型
						}
					});
					// 点击查询按钮时候，进行未审核以及审批中的数据统计操作
					auditCount(queryParams);
				}
			} else {
				// 由出发到达确认界面跳转而来，此时根据车牌号查询
				var vehicleNo=load.outsidevehiclecharge.vehicleNo;
				if(vehicleNo!=null&&vehicleNo!='' &&vehicleNo!=undefined){
					load.queryForm.getForm().findField('vehicleNo').setValue(vehicleNo);
					Ext.apply(operation, {
						params : {	
							'vo.adjustOutVehicleFeeDto.vehicleNo' : vehicleNo //车牌号													
						}
					});	
					//由出发到达确认界面跳转而来，进行未审核以及审批中的数据统计操作
					auditCount(queryParams);

				} else {
					// 2013年12月11日 15:14:13 by zyx begin
					// 页面左侧菜单打开是则进入此处
					// 如果不进行控制就会查询出所有的数据了
					Ext.apply(operation, {
								params : {
									'vo.adjustOutVehicleFeeDto.vehicleNo' : 'N/A' // 车牌号
								}
							});
					// end
				}

			}
		},
		load : function(store, records, successful, epots) {
		}
	}
});

//产生原因store
Ext.define('Foss.load.outsideVehicleCharge.cause.Store',{
	extend: 'Ext.data.Store',
	fields: [
		{name: 'code',  type: 'string'},
		{name: 'name',  type: 'string'}
	],
	proxy: {
		type: 'memory',
		reader: {
			type: 'json',
			root: 'items'
		}
	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

// 查询面板
Ext.define('Foss.load.outsideVehicleChargeQueryForm', {
	extend : 'Ext.form.Panel',
	layout : 'column',
	frame : true,
	border : false,
	title : load.outsidevehiclecharge.i18n('foss.load.assignLoadTask.queryCondition'),
	fieldDefaults : {
		msgTarget : 'side',
		labelWidth : 75
	},
	layout : 'column',
	defaults : {
		margin : '5 5 5 5',
		columns : 2
	},
	items : [{
		xtype : 'textfield',
		fieldLabel : load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.vehicleassembleNo'),// 配载车次号
		name : 'vehicleassembleNo',
		maxLength : 20,
		maxLengthText : load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.maxLengthText'),
		columnWidth : .25
	}, {
		xtype : 'dynamicorgcombselector',
		type : 'ORG',
		salesDepartment : 'Y',
		transferCenter : 'Y',
		airDispatch : 'Y',
		doAirDispatch : 'Y',
		fieldLabel : load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.origOrgCode'),// 出发部门
		name : 'origOrgCode',
		columnWidth : .25
	}, {
		xtype : 'commontruckselector',
		fieldLabel : load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.vehicleNo'),// 车牌号
		name : 'vehicleNo',
		columnWidth : .25
	}, 
	FossDataDictionary.getDataDictionaryCombo('ADJUST_AUDIT_STATE', {
				name : 'auditState',
				fieldLabel : load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.auditState'),// 审核状态
				queryMode : 'local',
				forceSelection : true,
				editable : false,
				value : '',
				columnWidth : .25
			}, {
				'valueCode' : '',
				'valueName' : load.outsidevehiclecharge.i18n('foss.load.assignLoadTask.ALL')
			}), {
		xtype : 'textfield',
		fieldLabel : load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.driverName'),// 主驾驶姓名
		name : 'driverName',
		maxLength : 20,
		maxLengthText : load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.maxLengthText'),
		columnWidth : .25
	}, {
		xtype : 'dynamicorgcombselector',
		types : 'ORG,PX',
		salesDepartment : 'Y',
		transferCenter : 'Y',
		airDispatch : 'Y',
		doAirDispatch : 'Y',
		fieldLabel : load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.destOrgCode'),// 到达部门
		name : 'destOrgCode',
		columnWidth : .25
	}
			// ,{
			// xtype: 'datefield',
			// fieldLabel:
			// load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.handoverTime'),//交接日期
			// name: 'handoverTime',
			// altFormats: 'Y,m,d|Y.m.d',
			// format: 'Y-m-d',
			// invalidText:
			// load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.invalidText'),
			// columnWidth: .2
			// }
	, FossDataDictionary.getDataDictionaryCombo('AWARD_TYPE', {
				name : 'awardType',
				fieldLabel : load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.awardType'),// 奖罚类型
				queryMode : 'local',
				forceSelection : true,
				editable : false,
				value : '',
				columnWidth : .25
			}, {
				'valueCode' : '',
				'valueName' : load.outsidevehiclecharge.i18n('foss.load.assignLoadTask.ALL')
			}), {
		name : 'assembleType',
		value : 'ALL',
		fieldLabel : load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.assembleType')/* '配载类型' */,
		xtype : 'combobox',
		queryMode : 'local',
		displayField : 'value',
		valueField : 'key',
		editable : false,
		store : Ext.create('Ext.data.Store', {
					fields : ['key', 'value'],
					data : [{
								"key" : "ALL",
								"value" : "全部"
							}, {
								"key" : "OWNER_LINE",
								"value" : "专线"
							}, {
								"key" : "CAR_LOAD",
								"value" : "整车"
							}]
				}),
		columnWidth : .25
	}, {
		xtype : 'rangeDateField',
		fieldLabel : load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.handoverTime'),// 交接日期
		// 类型为datetimefield_date97需要配置fieldId,可以赋予此属性任何唯一标 //识的String值
		fieldId : 'Foss_load_outsidevehiclecharge_handoverTime_Id',
		dateType : 'datetimefield_date97',
		// dateType: 'datefield',
		// dateType: 'timefield',
		fromName : 'beginTime',
		dateRange : 25,
		fromValue : Ext.Date.format(new Date(new Date(new Date().getFullYear(),new Date().getMonth(), new Date().getDate(), '00','00', '00')- 1000 * 60 * 60 * 24 * 7), 'Y-m-d H:i:s'),
		toName : 'endTime',
		toValue : Ext.Date.format(new Date(new Date().getFullYear(), new Date().getMonth(), new Date().getDate(), '23', '59','59'), 'Y-m-d H:i:s'),
		toName : 'endTime',
		allowBlank : false,
		disallowBlank : true,
		columnWidth : .66
	}, {
		xtype : 'container',
		border : false,
		columnWidth : .34,
		html : '&nbsp;'
	}, {
		border : 1,
		xtype : 'container',
		columnWidth : 1,
		defaultType : 'button',
		layout : 'column',
		items : [{
			text : load.outsidevehiclecharge.i18n('foss.load.assignLoadTask.reset'),// 重置
			columnWidth : .08,
			handler : function() {
				this.up('form').getForm().reset();
				load.queryForm.getForm().findField('vehicleNo').setValue(load.outsidevehiclecharge.vehicleNo);
				// 重新初始化交接时间
				this.up('form').getForm().findField('beginTime')
						.setValue(Ext.Date.format(new Date(new Date(new Date()
												.getFullYear(), new Date()
												.getMonth(), new Date()
												.getDate(), '00', '00', '00')
										- 1000 * 60 * 60 * 24 * 7),
								'Y-m-d H:i:s'));
				this.up('form').getForm().findField('endTime')
						.setValue(Ext.Date.format(new Date(new Date()
												.getFullYear(), new Date()
												.getMonth(), new Date()
												.getDate(), '23', '59', '59'),
								'Y-m-d H:i:s'));
			}
		}, {
			xtype : 'container',
			columnWidth : .835,
			html : '&nbsp;'
		}, {
			text : load.outsidevehiclecharge.i18n('foss.load.assignLoadTask.query'),// 查询
			disabled : !load.outsidevehiclecharge.isPermission('load/queryOutsideVehicleChargeListButton'),
			hidden : !load.outsidevehiclecharge.isPermission('load/queryOutsideVehicleChargeListButton'),
			cls : 'yellow_button',
			columnWidth : .08,
			handler : function() {
				var form = this.up('form').getForm();
				if (form.isValid()) {
					load.outsidevehiclecharge.query = 'TR';
					// 点击“查询”按钮时，更新未审核、审批中记录的条数
					Ext.Ajax.request({
						url : load.realPath('noAuditAndAuditInCount.action'),
						async : false,// 同步调用；
						params : {
							'vo.adjustOutVehicleFeeDto.vehicleassembleNo' : form.getValues().vehicleassembleNo, // 配载车次号
							'vo.adjustOutVehicleFeeDto.driverName' : form.getValues().driverName, // 主驾驶姓名
							'vo.adjustOutVehicleFeeDto.beginTime' : form.getValues().beginTime, // 开始日期
							'vo.adjustOutVehicleFeeDto.endTime' : form.getValues().endTime, // 结束日期
							'vo.adjustOutVehicleFeeDto.auditState' : form.getValues().auditState, // 审核状态
							'vo.adjustOutVehicleFeeDto.vehicleNo' : form.getValues().vehicleNo, // 车牌号
							'vo.adjustOutVehicleFeeDto.origOrgCode' : form.getValues().origOrgCode, // 出发部门
							'vo.adjustOutVehicleFeeDto.destOrgCode' : form.getValues().destOrgCode, // 到达部门
							'vo.adjustOutVehicleFeeDto.awardType' : form.getValues().awardType, // 奖罚类型
							'vo.adjustOutVehicleFeeDto.assembleType' : form.getValues().assembleType// 配载类型
						},
						success : function(response) {
							var result = Ext.decode(response.responseText);
							if (result.vo != null) {
								noAuditCount = result.vo.noAuditCount; // 当前未审核记录条数
								auditInCount = result.vo.auditInCount; // 审批中记录条数
								// 未审核记录条数（审核人：外场部门经理）：XX 审批中记录条数（审批人：整车解决方案管理组）：XX
								Ext.getCmp('auditCount').setValue('未审核记录条数(审核人:外场部门经理):'
												+ noAuditCount
												+ '     审批中记录条数(审批人:整车解决方案管理组):'
												+ auditInCount);
							}
						},
						exception : function(response) {
							var result = Ext.decode(response.responseText);
							Ext.MessageBox.alert(load.queryloadtask.i18n('foss.load.queryLoadingProgress.alert'),result.message);
						}
					});

					load.pagingBar.moveFirst();
				}
			}
		}]
	}],
	constructor : function(config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

// 审核面板
Ext.define('Foss.load.auditGrid', {
	extend : 'Ext.form.Panel',
	layout : 'column',
	defaults : {
		margin : '5 5 5 5',
		columns : 4
	},
	items : [{
				xtype : 'container',
				columnWidth : .835,
				html : '&nbsp;'
			}, {
				xtype : 'button',
				text : load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.audit'),// 审核
				disabled : !load.outsidevehiclecharge.isPermission('load/adjustOutVehicleFeeButton'),
				hidden : !load.outsidevehiclecharge.isPermission('load/adjustOutVehicleFeeButton'),
				cls : 'yellow_button',
				columnWidth : .08,
				handler : function() {
					var records = Ext.getCmp('Foss_load_outsidevehiclecharge_outsideVehicleChargeGrid_Id').getSelectionModel().getSelection();
					if (records.length == 1) {
						// ↓↓ 269701(lln)-2015-07-09 上午 8:20 ↓↓
						if (records[0].data.auditState == 'AUDITIN') {
							// 系统提示“审批中的记录不可审核”
							Ext.Msg.alert(load.outsidevehiclecharge.i18n('foss.load.assignLoadTask.prompt'),load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.auditInNo'));
							// ↑↑ 269701(lln)-2015-07-09 上午 8:20 ↑↑

						} else if (records[0].data.auditState == 'AUDITPASS') {
							Ext.Msg.alert(load.outsidevehiclecharge.i18n('foss.load.assignLoadTask.prompt'),load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.auditPassNo'));
						} else {
							var auditInfoWindow = Ext.getCmp('T_load-outsidevehiclechargeindex_content').getAuditInfoWindow();
							var auditForm = auditInfoWindow.getAuditInfoWindowForm().form;
							auditForm.findField('id').setValue(records[0].data.id);
							auditForm.findField('vehicleassembleNo').setValue(records[0].data.vehicleassembleNo);
							auditForm.findField('awardType').setValue(records[0].data.awardType);
							auditForm.findField('adjustFee').setValue(records[0].data.adjustFee);

							var timelinessClause = records[0].get('timelinessClause');
							var adjustReasonCombo = auditForm.findField('adjustReason');
							adjustReasonCombo.clearValue();
							if (timelinessClause == 'Y') {
								if (records[0].get('cause') == 'AGINGINADVANCE') {
									// 调整原因-时效提前原因
									adjustReasonCombo.bindStore(FossDataDictionary.getDataDictionaryStore('CAUSE_AGINGINADVANCE_REASON'));
								} else {
									// 调整原因-时效延误原因
									adjustReasonCombo.bindStore(FossDataDictionary.getDataDictionaryStore('CAUSE_AGINGDELAY_REASON'));
								}
							} else {
								if (records[0].get('awardType') == 'FEE_AUGMENT') {
									// 如果为费用增加则绑定费用增加原因的store
									adjustReasonCombo.bindStore(FossDataDictionary.getDataDictionaryStore('FEE_AUGMENT_REASON'));
								} else {
									// 如果为费用减少则绑定费用减少原因的store
									adjustReasonCombo.bindStore(FossDataDictionary.getDataDictionaryStore('FEE_REDUCE_REASON'));
								}
							}

							adjustReasonCombo.setValue(records[0].get('adjustReason'));
							// 展示window
							auditInfoWindow.show();
						}
					} else if (records.length > 1) {
						Ext.Msg.alert(load.outsidevehiclecharge.i18n('foss.load.assignLoadTask.prompt'),load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.exception.auditSelectOne'));
					} else if (records.length == 0) {
						Ext.Msg.alert(load.outsidevehiclecharge.i18n('foss.load.assignLoadTask.prompt'),load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.exception.auditSelectOne'));
					}
				}
			}, {
				// ↓↓ 269701(lln)-2015-07-09 上午 8:20 ↓↓
				xtype : 'button',
				text : load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.approvalBtn'),// 审批
				disabled : !load.outsidevehiclecharge.isPermission('load/adjustOutVehicleFeeCarLoadApprovalBtn'),// 该按钮只有整车基础资料维护员可操作，其他部门不可见
				hidden : !load.outsidevehiclecharge.isPermission('load/adjustOutVehicleFeeCarLoadApprovalBtn'),// 该按钮只有整车基础资料维护员可操作，其他部门不可见
				cls : 'yellow_button',
				columnWidth : .08,
				// 审批事件
				handler : function() {
					var records = Ext.getCmp('Foss_load_outsidevehiclecharge_outsideVehicleChargeGrid_Id').getSelectionModel().getSelection();
					if (records.length == 1) {
						// 只有审核状态为“审批中”中的整车配载单，才可以点击审批按钮
						if (records[0].data.auditState == 'AUDITIN') {
							// 如果审核状态是“审批中”，点击审批按钮，进入审批意见界面
							var approvalInfoWindow = Ext.getCmp('T_load-outsidevehiclechargeindex_content').getApprovalInfoWindow();
							var approvalForm = approvalInfoWindow.getApprovalOutsideVehicleChargeForm().form;
							// ID
							approvalForm.findField('id').setValue(records[0].data.id);
							// 配载车次号
							approvalForm.findField('vehicleassembleNo').setValue(records[0].data.vehicleassembleNo);
							var params = {
								vo : {
									adjustOutVehicleFeeDto : {
										id : records[0].data.id
									}
								}
							}; // id
							// 装车总金额
							Ext.Ajax.request({
								url : load.realPath('queryApprovalById.action'),
								jsonData : params,
								success : function(response, opts) {
									var result = Ext.decode(response.responseText); // 查询的结果
									var adjustOutVehicleFeeDto = result.vo.adjustOutVehicleFeeDto;
									if (null == adjustOutVehicleFeeDto.loadFeeTotal) {
										// 查询结果为空时 装车总金额=0
										approvalForm.findField('loadFeeTotal').setValue('0');
									} else {
										// 装车总金额=DB值
										approvalForm.findField('loadFeeTotal').setValue(adjustOutVehicleFeeDto.loadFeeTotal);
									}
								}

							});
							// 出发部门
							approvalForm.findField('origOrgName').setValue(records[0].data.origOrgName);
							// 主驾驶姓名
							approvalForm.findField('driverName').setValue(records[0].data.driverName);
							// 到达部门
							approvalForm.findField('destOrgName').setValue(records[0].data.destOrgName);
							// 司机电话
							approvalForm.findField('driverMobilePhone').setValue(records[0].data.driverMobilePhone);
							// 走货日期
							approvalForm.findField('handoverTime').setValue(records[0].data.handoverTime);
							// 预付运费总额
							approvalForm.findField('prepaidFeeTotal').setValue(records[0].data.prepaidFeeTotal);
							// 总运费
							approvalForm.findField('feeTotal').setValue(records[0].data.feeTotal);
							// 到付运费总额
							approvalForm.findField('arriveFeeTotal').setValue(records[0].data.arriveFeeTotal);
							// 增减类型
							var adjustTypeCombo = approvalForm.findField('awardType');
							adjustTypeCombo.clearValue();
							adjustTypeCombo.setValue(records[0].get('awardType'));
							// 调整费用
							approvalForm.findField('adjustFee').setValue(records[0].data.adjustFee);
							// 是否时效条款
							var timelinessClause = records[0].get('timelinessClause');
							// 调整原因
							var adjustReasonCombo = approvalForm.findField('adjustReason');
							adjustReasonCombo.clearValue();
							if (timelinessClause == 'Y') {
								if (records[0].get('cause') == 'AGINGINADVANCE') {
									// 调整原因-时效提前原因
									adjustReasonCombo.bindStore(FossDataDictionary.getDataDictionaryStore('CAUSE_AGINGINADVANCE_REASON'));
								} else {
									// 调整原因-时效延误原因
									adjustReasonCombo.bindStore(FossDataDictionary.getDataDictionaryStore('CAUSE_AGINGDELAY_REASON'));
								}
							} else {
								if (records[0].get('awardType') == 'FEE_AUGMENT') {
									// 如果为费用增加则绑定费用增加原因的store
									adjustReasonCombo.bindStore(FossDataDictionary.getDataDictionaryStore('FEE_AUGMENT_REASON'));
								} else {
									// 如果为费用减少则绑定费用减少原因的store
									adjustReasonCombo.bindStore(FossDataDictionary.getDataDictionaryStore('FEE_REDUCE_REASON'));
								}
							}
							adjustReasonCombo.setValue(records[0].get('adjustReason'));
							// 展示window
							approvalInfoWindow.show();

						} else {
							// 系统提示：只有审核状态为“审批中”中的整车配载单，才可以点击审批按钮
							Ext.Msg.alert(load.outsidevehiclecharge.i18n('foss.load.assignLoadTask.prompt'),load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.exception.approvalIn'));
						}
					} else if (records.length > 1) {
						Ext.Msg.alert(load.outsidevehiclecharge.i18n('foss.load.assignLoadTask.prompt'),load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.exception.approvalSelectOne'));
					} else if (records.length == 0) {
						Ext.Msg.alert(load.outsidevehiclecharge.i18n('foss.load.assignLoadTask.prompt'),load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.exception.approvalSelectOne'));
					}
				}
			}
	// ↑↑ 269701(lln)-2015-07-09 上午 8:20 ↑↑
	],
	constructor : function(config) {
		var me = this, 
		cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

load.outsidevehiclecharge.rendererAdjustReason = function(value) {
	if (value == 'AGINGDELAY') {
		return load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.AGINGDELAY');
	}
	if (value == 'OTHER') {
		return load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.OTHER');
	}
	if (value == 'AGINGINADVANCE') {
		return load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.AGINGINADVANCE');
	}

	// 2013年12月17日 15:04:08 by zyx
	// 费用增加原因Store
	var feeAugmentReasonStore = FossDataDictionary.getDataDictionaryStore('FEE_AUGMENT_REASON');
	for (var i = 0; i < feeAugmentReasonStore.getCount(); i++) {
		if (feeAugmentReasonStore.getAt(i).get('valueCode') == value) {
			return feeAugmentReasonStore.getAt(i).get('valueName');
		}
	}

	// 费用减少原因Store
	var feeReduceReasonStore = FossDataDictionary.getDataDictionaryStore('FEE_REDUCE_REASON');
	for (var i = 0; i < feeReduceReasonStore.getCount(); i++) {
		if (feeReduceReasonStore.getAt(i).get('valueCode') == value) {
			return feeReduceReasonStore.getAt(i).get('valueName');
		}
	}

	// 时效提前原因Store
	var causeAginginadvanceReasonStore = FossDataDictionary.getDataDictionaryStore('CAUSE_AGINGINADVANCE_REASON');
	for (var i = 0; i < causeAginginadvanceReasonStore.getCount(); i++) {
		if (causeAginginadvanceReasonStore.getAt(i).get('valueCode') == value) {
			return causeAginginadvanceReasonStore.getAt(i).get('valueName');
		}
	}

	// 时效延误原因Store
	var causeAgingdelayReasonStore = FossDataDictionary.getDataDictionaryStore('CAUSE_AGINGDELAY_REASON');
	for (var i = 0; i < causeAgingdelayReasonStore.getCount(); i++) {
		if (causeAgingdelayReasonStore.getAt(i).get('valueCode') == value) {
			return causeAgingdelayReasonStore.getAt(i).get('valueName');
		}
	}
	// end
	return null;
}

/**
 * 外请车费用信息列表
 */
Ext.define('Foss.load.outsideVehicleChargeGrid', {
	extend : 'Ext.grid.Panel',
	// 增加表格列的分割线
	columnLines : true,
	cls : 'autoHeight',
	bodyCls : 'autoHeight',
	emptyText : load.outsidevehiclecharge.i18n('foss.load.deliverLoadGapReport.dataNotFind'),// 查询结果为空
	id : 'Foss_load_outsidevehiclecharge_outsideVehicleChargeGrid_Id',
	stripeRows : true,
	frame : true,
	animCollapse : true,
	autoScroll : true,
	height : 500,
	selModel : null,
	store : null,
	title : load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.outsideVehicleChargeGrid.title'),// 外请车费用列表信息
	columns : [{
		xtype : 'actioncolumn',
		width : 80,
		text : load.outsidevehiclecharge.i18n('foss.load.assignLoadTask.operate'),// 操作,
		items : [{
			iconCls : 'deppon_icons_edit',
			tooltip : load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.button.edit'),// 编辑
			// 编辑事件
			handler : function(grid, rowIndex, colIndex) {
				var record = grid.getStore().getAt(rowIndex);
				var auditState = record.get('auditState');
				// ↓↓ 269701(lln)-2015-07-09 上午 8:20 ↓↓
				if (auditState == 'AUDITIN') {
					// 系统提示“审批中的记录不可修改”
					Ext.Msg.alert(load.outsidevehiclecharge.i18n('foss.load.assignLoadTask.prompt'),load.outsidevehiclecharge.i18n('foss.load.assignLoadTask.cannotAuditInUpdate'));
					// ↑↑ 269701(lln)-2015-07-09 上午 8:20 ↑↑
				} else if (auditState == 'AUDITPASS') {
					Ext.Msg.alert(load.outsidevehiclecharge.i18n('foss.load.assignLoadTask.prompt'),load.outsidevehiclecharge.i18n('foss.load.assignLoadTask.cannotUpdate'));
				} else {
					var params = {
						vo : {
							adjustOutVehicleFeeDto : {
								id : record.get('id')
							}
						}
					};
					Ext.Ajax.request({
						url : load.realPath('queryById.action'),
						jsonData : params,
						success : function(response, opts) {
							var result = Ext.decode(response.responseText); // 查询的结果
							var adjustOutVehicleFeeDto = result.vo.adjustOutVehicleFeeDto;
							var recode = Ext.create('Foss.load.outsideVehicleChargeModel');
							if (adjustOutVehicleFeeDto != null) {
								recode.data = adjustOutVehicleFeeDto;
								if (recode.data.handoverTime != null&& recode.data.handoverTime != '') {
									recode.data.handoverTime = new Date(record.get('handoverTime'));
								}
							}
							var timelinessClause = recode.get('timelinessClause');
							var editOutsideVehicleChargeWindow, editForm;
							if (timelinessClause == 'Y') {
								// 有时效条款
								// 则打开时效条款的修改界面
								editOutsideVehicleChargeWindow = Ext.getCmp('T_load-outsidevehiclechargeindex_content').getEditDetailInfoTimelinessClauseWindow();
								editForm = editOutsideVehicleChargeWindow.getEditOutsideVehicleChargeTimelinessClauseForm();
								var adjustReasonCombo = editForm.getForm().findField('adjustReason');
								var rewardMaxMoney = result.vo.rewardOrPunishAgreementDto.rewardMaxMoney;
								var fineMaxMoney = result.vo.rewardOrPunishAgreementDto.fineMaxMoney;
								load.outsidevehiclecharge.timelinessAwardMaxFee = rewardMaxMoney;// 时效协议奖励最大值
								load.outsidevehiclecharge.timelinessFineMaxFee = fineMaxMoney;// 时效协议罚款最大值
								Ext.getCmp('T_load-outsidevehiclecharge_awardMaxFeeField').setValue(rewardMaxMoney);
								Ext.getCmp('T_load-outsidevehiclecharge_fineMaxFeeField').setValue(fineMaxMoney);
								// 时效条款中最高奖励金额(配载单接口中获取)
								if (rewardMaxMoney == 0) {
									Ext.getCmp('T_load-outsidevehiclecharge_awardMaxFeeField').setVisible(false);
								}
								if (fineMaxMoney == 0) {
									Ext.getCmp('T_load-outsidevehiclecharge_fineMaxFeeField').setVisible(false);
								}
								adjustReasonCombo.clearValue();
								if (recode.get('cause') == 'AGINGINADVANCE') {
									// 调整原因-时效提前原因
									adjustReasonCombo.bindStore(FossDataDictionary.getDataDictionaryStore('CAUSE_AGINGINADVANCE_REASON'));
								} else {
									// 调整原因-时效延误原因
									adjustReasonCombo.bindStore(FossDataDictionary.getDataDictionaryStore('CAUSE_AGINGDELAY_REASON'));
								}
								// 时效条款信息 这里是一个list
								var rewardOrPunishAgreements = result.vo.rewardOrPunishAgreementDto.rewardOrPunishAgreementEntity;
								editOutsideVehicleChargeWindow.getTimelinessClauseGridPanel().store.loadData(rewardOrPunishAgreements);
							} else {
								editOutsideVehicleChargeWindow = Ext.getCmp('T_load-outsidevehiclechargeindex_content').getEditDetailInfoWindow();
								editForm = editOutsideVehicleChargeWindow.getEditOutsideVehicleChargeForm();
								var adjustReasonCombo = editForm.getForm().findField('adjustReason');
								adjustReasonCombo.clearValue();
								if (recode.get('awardType') == 'FEE_AUGMENT') {
									// 如果为费用增加则绑定费用增加原因的store
									adjustReasonCombo.bindStore(FossDataDictionary.getDataDictionaryStore('FEE_AUGMENT_REASON'));
								} else {
									// 如果为费用减少则绑定费用减少原因的store
									adjustReasonCombo.bindStore(FossDataDictionary.getDataDictionaryStore('FEE_REDUCE_REASON'));
								}
								adjustReasonCombo.setValue(recode.get('adjustReason'));// 调整原因
							}
							editForm.getForm().reset();
							editForm.getForm().loadRecord(recode);
							editOutsideVehicleChargeWindow.show();
						},
						failure : function(response, opts) {
							Ext.Msg.alert(load.outsidevehiclecharge.i18n('foss.load.assignLoadTask.prompt'),load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.exception.saveFailure'));
						}
					});
				}
			}
		}, {
			iconCls : 'deppon_icons_delete',
			tooltip : load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.button.delete'),// 删除
			handler : function(grid, rowIndex, colIndex) {
				var record = grid.getStore().getAt(rowIndex);
				var auditState = record.get('auditState');
				// ↓↓ 269701(lln)-2015-07-09 上午 8:20 ↓↓
				if (auditState == 'AUDITIN') {
					// 系统提示“审批中的记录不可删除”
					Ext.Msg.alert(load.outsidevehiclecharge.i18n('foss.load.assignLoadTask.prompt'),
									load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.exception.deleteNotAuditIn'));
					// ↑↑ 269701(lln)-2015-07-09 上午 8:20 ↑↑
				} else if (auditState == 'NOAUDIT') {
					Ext.Msg.confirm(load.outsidevehiclecharge.i18n('foss.load.assignLoadTask.prompt'),
									load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.exception.delete'),
									function(btn, text) {
										// 询问是否删除，是则发送请求
										if (btn == 'yes') {
											Ext.Ajax.request({
												params : {
													'vo.adjustOutVehicleFeeDto.id' : grid.getStore().getAt(rowIndex).get('id')
												},
												url : load.realPath('deleteOutsideVehicleCharge.action'),
												success : function(response) {
													Ext.ux.Toast.msg(load.outsidevehiclecharge.i18n('foss.load.assignLoadTask.prompt'),load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.exception.deleteSuccess'),'ok', 1000);
													load.pagingBar.moveFirst();
												},
												failure : function(response) {
													var result = Ext.decode(response.responseText);
													Ext.Msg.alert(load.outsidevehiclecharge.i18n('foss.load.assignLoadTask.prompt'),load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.exception.deleteFailure')+ result);
												},
												exception : function(response) {
													var json = Ext.decode(response.responseText);
													Ext.Msg.alert(load.outsidevehiclecharge.i18n('foss.load.assignLoadTask.prompt'),json.message);
												}
											});
										}
									});
				} else {
					Ext.Msg.alert(load.outsidevehiclecharge.i18n('foss.load.assignLoadTask.prompt'),load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.exception.deleteNotAudit'));
				}
			}
		}],
		renderer : function(value, metadata, record) {
			if (record.data.timelinessClause == 'Y') {
				// 有时效条款的不能够删除
				this.items[1].iconCls = '';
			} else {
				this.items[1].iconCls = 'deppon_icons_delete';
			}
		}
	}, {
		header : load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.auditState'),// 部门经理审核状态 ---改为审核状态
		// 关联model中的字段名
		dataIndex : 'auditState',
		renderer : function(value, metadata, record) {
			if (!Ext.isEmpty(value)) {
				if (value == 'NOAUDIT') {
					return load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.noaudit');
				} else if (value == 'AUDITNOTPASS') {
					return '<span style="color:red">'
							+ load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.auditnotpass')
							+ '</span>';
				} else if (value == 'AUDITPASS') {
					return '<span style="color:green">'
							+ load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.auditpass')
							+ '</span>';
					// ↓↓↓↓↓ 269701(lln) 2015-07-08 ↓↓↓↓↓
				} else if (value == 'AUDITIN') {
					return load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.auditin');
				}
				// ↑↑↑↑↑↑ 269701(lln) 2015-07-08 ↑↑↑↑↑↑
			}
		},
		width : 100
	}, {
		// 字段标题
		header : load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.vehicleassembleNo'),// 配载车次号
		// 关联model中的字段名
		dataIndex : 'vehicleassembleNo',
		width : 80
	}, {
		// 字段标题
		header : load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.assembleType'),// 配载类型
		// 关联model中的字段名
		dataIndex : 'assembleType',
		width : 80,
		renderer : function(value) {
			if (value == 'CAR_LOAD') {
				return '整车';
			} else if (value == 'OWNER_LINE') {
				return '专线';
			}else if(value == 'ALL'){
				return '全部';
			}else{
				return '';	
			}
		}
	}, {
		// 字段标题
		header : load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.vehicleNo'),// 车牌号
		// 关联model中的字段名
		dataIndex : 'vehicleNo',
		width : 80
	}, {
		header : load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.driverName'),// 主驾驶姓名
		// 关联model中的字段名
		dataIndex : 'driverName',
		width : 80
	}, {
		header : load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.driverMobilePhone'),// 司机电话
		// 关联model中的字段名
		dataIndex : 'driverMobilePhone',
		width : 80
	}, {
		header : load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.handoverTime'),// 交接日期
		// 关联model中的字段名
		dataIndex : 'handoverTime',
		width : 100
	}, {
		header : load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.origOrgName'),// 出发部门名称
		// 关联model中的字段名
		dataIndex : 'origOrgName',
		width : 100
	}, {
		header : load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.destOrgName'),// 到达部门名称
		// 关联model中的字段名
		dataIndex : 'destOrgName',
		width : 100
	}, {
		header : load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.feeTotal'),// 总运费
		// 关联model中的字段名
		dataIndex : 'feeTotal',
		width : 60
	}, {
		header : load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.prepaidFeeTotal'),// 预付运费总额
		// 关联model中的字段名
		dataIndex : 'prepaidFeeTotal',
		width : 80
	}, {
		header : load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.arriveFeeTotal'),// 到付运费总额
		// 关联model中的字段名
		dataIndex : 'arriveFeeTotal',
		width : 80
	}, {
		header : load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.initAmt'),// 初始金额
		// 关联model中的字段名
		dataIndex : 'initAmt',
		renderer : function(value, metadata, record) {
			if (value > 0) {
				return '+' + value;
			}
			return value;
		},
		width : 80
	}, {
		header : load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.adjustFee'),// 调整费用
		// 关联model中的字段名
		dataIndex : 'adjustFee',
		renderer : function(value, metadata, record) {
			if (!Ext.isEmpty(record)) {
				if (record.data.awardType == 'REWARD') {
					return '+' + value;
				}
				if (record.data.awardType == 'FINE') {
					return '-' + value;
				}
				if (record.data.awardType == 'FEE_AUGMENT') {
					// 费用增加
					return '+' + value;
				}
				if (record.data.awardType == 'FEE_REDUCE') {
					// 费用减少
					return '-' + value;
				}
			}
			return value;
		},
		width : 80
	}, {
		header : load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.timelinessClause'),// 时效条款
		// 关联model中的字段名
		dataIndex : 'timelinessClause',
		renderer : function(value, metadata, record) {
			if (!Ext.isEmpty(value)) {
				if (value == 'Y') {
					return '是';
				} else if (value == 'N') {
					return '否';
				}
				return '';
			}
		},
		width : 60
	}, {
		header : load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.timelinessDuration'),// 变化时长
		// 关联model中的字段名
		dataIndex : 'timelinessDurationStr',
		width : 100
	}, {
		header : load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.adjustReason'),// 调整原因
		// 关联model中的字段名
		dataIndex : 'adjustReason',
		width : 80,
		renderer : function(value, metadata, record) {
			if (!Ext.isEmpty(value)) {
				var adjustReason = load.outsidevehiclecharge.rendererAdjustReason(value);
				if (!Ext.isEmpty(adjustReason)) {
					return adjustReason;
				}
				return value;
			}
		}
	}, {
		header : load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.otherReason'),// 其他原因
		// 关联model中的字段名
		dataIndex : 'otherReason',
		width : 80
	}, {
		header : load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.auditorName'),// 审核人名称
		// 关联model中的字段名
		dataIndex : 'auditorName',
		width : 80
	}],
	// 269701--lln--2015-07-14 begin
	// 选中该条记录时，查询修改记录
	listeners : {
		beforeitemclick : function(view, record) {
				// 查询本条外请车费用的修改日志
				load.outsidevehiclecharge.loadUpdateLog();
		}
	},
	// 269701--lln--2015-07-14 end
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.load.outsideVehicleChargeStore');
		me.selModel = Ext.create('Ext.selection.CheckboxModel');
		me.tbar = [{
			xtype : 'button',
			text : load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.button.add'),// 新增
			disabled : !load.outsidevehiclecharge.isPermission('load/addOutsideVehicleChargeButton'),
			hidden : !load.outsidevehiclecharge.isPermission('load/addOutsideVehicleChargeButton'),
			handler : function() {
				var record = Ext.create('Foss.load.outsideVehicleChargeModel');
				// 获取弹出窗口
				var outsideVehicleChargeWindow = Ext.getCmp('T_load-outsidevehiclechargeindex_content').getAddInfoWindow();
				var infoForm = outsideVehicleChargeWindow.getOutsideVehicleChargeFrom();
				// 加载grid中的数据到弹出的window中
				infoForm.getForm().reset();
				infoForm.getForm().findField('awardType').setValue('FEE_AUGMENT');// 费用增加
				infoForm.getForm().findField('adjustReason').setValue('ESCORT_FEE');// 费用增加调整原因-押车费
				// 展示window
				outsideVehicleChargeWindow.show();
			}
		}, {
			xtype : 'button',
			text : load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.button.export'),// 导出
			name : 'export',
			handler : function() {
				var queryParams = load.queryForm.getValues();
				if (!Ext.fly('downloadAttachFileForm')) {
					var frm = document.createElement('form');
					frm.id = 'downloadAttachFileForm';
					frm.style.display = 'none';
					document.body.appendChild(frm);
				}
				Ext.Ajax.request({
					url : load.realPath('exportOutsideVehicleCharge.action'),
					form : Ext.fly('downloadAttachFileForm'),
					method : 'POST',
					params : {
						'vo.adjustOutVehicleFeeDto.vehicleassembleNo' : queryParams.vehicleassembleNo, // 配载车次号
						'vo.adjustOutVehicleFeeDto.driverName' : queryParams.driverName, // 主驾驶姓名
						'vo.adjustOutVehicleFeeDto.beginTime' : queryParams.beginTime, // 开始日期
						'vo.adjustOutVehicleFeeDto.endTime' : queryParams.endTime, // 结束日期
						'vo.adjustOutVehicleFeeDto.auditState' : queryParams.auditState, // 审核状态
						'vo.adjustOutVehicleFeeDto.vehicleNo' : queryParams.vehicleNo, // 车牌号
						'vo.adjustOutVehicleFeeDto.origOrgCode' : queryParams.origOrgCode, // 出发部门
						'vo.adjustOutVehicleFeeDto.destOrgCode' : queryParams.destOrgCode, // 到达部门
						'vo.adjustOutVehicleFeeDto.awardType' : queryParams.awardType, // 奖罚类型
						'vo.adjustOutVehicleFeeDto.assembleType' : queryParams.assembleType// 配载类型
					},
					isUpload : true,
					exception : function(response) {
						var result = Ext.decode(response.responseText);
						top.Ext.MessageBox.alert(load.queryloadtask.i18n('foss.load.queryloadtask.exportFail'),result.message);
					}
				});
			}
		},
				// 269701--lln--2015-07-16 begin
				{
					xtype : 'container',
					width : 200,
					html : '&nbsp;'
				}, { // 调整外请车费用界面增加“未审核记录条数（审核人：外场部门经理）：XX 审批中记录条数（审批人：整车解决方案管理组）：XX”
					fieldLabel : null,
					xtype : 'textfield',
					readOnly : true,
					width : 1000,
					value : null,
					id : 'auditCount'
				}
		// 269701--lln--2015-07-16 end
		];

		me.bbar = Ext.create('Deppon.StandardPaging', {
					store : me.store,
					plugins : 'pagesizeplugin'
				});
		load.pagingBar = me.bbar;
		me.callParent([cfg]);
	}
});

/**
 * 未审核记录条数 以及 审批中记录条数的统计 
 * 269701--lln--2015-07-16 begin
 */
function auditCount(queryParams) {
	// 点击查询按钮，进行统计
	if (load.outsidevehiclecharge.query == 'TR') {
		Ext.Ajax.request({
			url : load.realPath('noAuditAndAuditInCount.action'),
			async : false,// 同步调用；
			params : {
				'vo.adjustOutVehicleFeeDto.vehicleassembleNo' : queryParams.vehicleassembleNo, // 配载车次号
				'vo.adjustOutVehicleFeeDto.driverName' : queryParams.driverName, // 主驾驶姓名
				'vo.adjustOutVehicleFeeDto.beginTime' : queryParams.beginTime, // 开始日期
				'vo.adjustOutVehicleFeeDto.endTime' : queryParams.endTime, // 结束日期
				'vo.adjustOutVehicleFeeDto.auditState' : queryParams.auditState, // 审核状态
				'vo.adjustOutVehicleFeeDto.vehicleNo' : queryParams.vehicleNo, // 车牌号
				'vo.adjustOutVehicleFeeDto.origOrgCode' : queryParams.origOrgCode, // 出发部门
				'vo.adjustOutVehicleFeeDto.destOrgCode' : queryParams.destOrgCode, // 到达部门
				'vo.adjustOutVehicleFeeDto.awardType' : queryParams.awardType, // 奖罚类型
				'vo.adjustOutVehicleFeeDto.assembleType' : queryParams.assembleType// 配载类型
			},
			success : function(response) {
				var result = Ext.decode(response.responseText);
				if (result.vo != null) {
					noAuditCount = result.vo.noAuditCount; // 当前未审核记录条数
					auditInCount = result.vo.auditInCount; // 审批中记录条数
					// 未审核记录条数（审核人：外场部门经理）：XX 审批中记录条数（审批人：整车解决方案管理组）：XX
					Ext.getCmp('auditCount').setValue('未审核记录条数(审核人:外场部门经理):' 
							+ noAuditCount + '     审批中记录条数(审批人:整车解决方案管理组):' 
							+ auditInCount);

				}
			},
			exception : function(response) {
				var result = Ext.decode(response.responseText);
				Ext.MessageBox.alert(load.queryloadtask.i18n('foss.load.queryLoadingProgress.alert'),result.message);
			}
		});
	}else{
		//出发到达确认界面跳转而来，进行统计操作
		Ext.Ajax.request({
			url : load.realPath('noAuditAndAuditInCount.action'),
			async : false,// 同步调用；
			params : {
				'vo.adjustOutVehicleFeeDto.vehicleNo' : queryParams.vehicleNo // 车牌号
			},
			success : function(response) {
				var result = Ext.decode(response.responseText);
				if (result.vo != null) {
					noAuditCount = result.vo.noAuditCount; // 当前未审核记录条数
					auditInCount = result.vo.auditInCount; // 审批中记录条数
					// 未审核记录条数（审核人：外场部门经理）：XX 审批中记录条数（审批人：整车解决方案管理组）：XX
					Ext.getCmp('auditCount').setValue('未审核记录条数(审核人:外场部门经理):' 
							+ noAuditCount + '     审批中记录条数(审批人:整车解决方案管理组):' 
							+ auditInCount);

				}
			},
			exception : function(response) {
				var result = Ext.decode(response.responseText);
				Ext.MessageBox.alert(load.queryloadtask.i18n('foss.load.queryLoadingProgress.alert'),result.message);
			}
		});
	}
}
// 269701--lln--2015-07-16 begin

// 定义新增From
Ext.define('Foss.load.addOutsideVehicleChargeFrom', {
	extend : 'Ext.form.Panel',
	layout : 'column',
	frame : false,
	defaults : {
		xtype : 'textfield',
		margin : '10 10 10 10',
		anchor : '90%'
	},
	items : [{
		name : 'vehicleassembleNo',
		fieldLabel : load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.vehicleassembleNo'),// 配载车次号
		allowBlank : false,
		labelWidth : 80,
		columnWidth : .5,
		listeners : {
			blur : function(text, op) {
				if (text.getValue().trim() != null
						&& text.getValue().trim() != '') {
					var params = {
						vo : {
							adjustOutVehicleFeeDto : {
								vehicleassembleNo : text.getValue()
							}
						}
					}; // 配载车次号
					Ext.Ajax.request({
						url : load.realPath('queryByVehicleassembleNo.action'),
						jsonData : params,
						success : function(response, opts) {
							var result = Ext.decode(response.responseText); // 查询的结果
							var adjustOutVehicleFeeDto = result.vo.adjustOutVehicleFeeDto;
							var recode = Ext.create('Foss.load.outsideVehicleChargeModel');
							if (adjustOutVehicleFeeDto == null) {// 查询结果为null，输入的车次号不对
								Ext.Msg.alert(
												load.outsidevehiclecharge.i18n('foss.load.assignLoadTask.prompt'),
												load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.exception.enterVehicleassembleNo'));
							} else {
								recode.data = adjustOutVehicleFeeDto;
								if (recode.data.handoverTime != null && recode.data.handoverTime != '') {
									recode.data.handoverTime = Ext.Date.format(new Date(recode.data.handoverTime),'Y-m-d');
								}
							}
							var addFrom = Ext.getCmp('T_load-outsidevehiclechargeindex_content').getAddInfoWindow();
							var initFrom = addFrom.getOutsideVehicleChargeFrom();
							initFrom.loadRecord(recode);
							if (null == recode.data.loadFeeTotal) {
								initFrom.getForm().findField('loadFeeTotal').setValue('0');
							}
							initFrom.getForm().findField('awardType').setValue('FEE_AUGMENT');// 默认加载出来为费用增加
							initFrom.getForm().findField('adjustReason').setValue('ESCORT_FEE');// 默认为押车费
						},
						failure : function(response, opts) {
							Ext.Msg.alert(
											load.outsidevehiclecharge.i18n('foss.load.assignLoadTask.prompt'),
											load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.exception.saveFailure'));
						},
						exception : function(response) {
							var addFrom = Ext.getCmp('T_load-outsidevehiclechargeindex_content').getAddInfoWindow();
							var initFrom = addFrom.getOutsideVehicleChargeFrom();
							initFrom.getForm().findField('vehicleassembleNo').setValue('');// 清空当前文本框内容
							var json = Ext.decode(response.responseText);
							Ext.Msg.alert(load.outsidevehiclecharge.i18n('foss.load.assignLoadTask.prompt'),json.message);
						}
					});
				}
			}
		}
	}, {
		name : 'origOrgName',
		fieldLabel : load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.origOrgCode'),// 出发部门
		readOnly : true,
		labelWidth : 80,
		columnWidth : .5
	}, {
		name : 'origOrgCode',
		xtype : 'hiddenfield'
	}, {
		name : 'driverName',
		fieldLabel : load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.driverName'),// 主驾驶姓名
		readOnly : true,
		labelWidth : 80,
		columnWidth : .5
	}, {
		name : 'driverCode',
		xtype : 'hiddenfield'
	}, {
		name : 'destOrgName',
		fieldLabel : load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.destOrgCode'),// 到达部门
		readOnly : true,
		labelWidth : 80,
		columnWidth : .5
	}, {
		name : 'destOrgCode',
		xtype : 'hiddenfield'
	}, {
		name : 'driverMobilePhone',
		fieldLabel : load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.driverMobilePhone'),// 司机电话
		readOnly : true,
		labelWidth : 90,
		columnWidth : .5
	}, {
		xtype : 'datefield',
		fieldLabel : load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.handoverTime'),// 走货日期
		readOnly : true,
		name : 'handoverTime',
		altFormats : 'Y,m,d|Y.m.d',
		format : 'Y-m-d',
		invalidText : load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.invalidText'),
		allowBlank : false,
		columnWidth : .5
	}, {
		name : 'prepaidFeeTotal',
		fieldLabel : load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.prepaidFeeTotal'),// 预付运费总额
		readOnly : true,
		labelWidth : 90,
		columnWidth : .5
	}, {
		name : 'feeTotal',
		fieldLabel : load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.feeTotal'),// 总运费
		readOnly : true,
		labelWidth : 90,
		columnWidth : .5
	}, {
		name : 'loadFeeTotal',
		fieldLabel : load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.loadFeeTotal'),// 装车总金额
		readOnly : true,
		labelWidth : 90,
		columnWidth : .5
	}, {
		name : 'arriveFeeTotal',
		fieldLabel : load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.arriveFeeTotal'),// 到付运费总额
		readOnly : true,
		labelWidth : 90,
		columnWidth : .5
	}, {
		xtype : 'combo',
		fieldLabel : load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.adjustType'),// 增减类型
		name : 'awardType',
		columnWidth : .5,
		// 因为data已经取数据到本地了，所以’local’,默认为”remote”,当为remote时，下拉框将在被点击的第一次自动加载store，
		// 如果不需要自动加载，则可以把它设置为local并手动的加载store
		queryMode : 'local',
		// 默认为”all”,当设置成”query”的时候，你选择某个值后，再此下拉时，只出现匹配选项，如果设为”all”的话，每次下拉均显示全部选项
		triggerAction : 'all',
		// 延时查询，与下面的参数配合 typeAhead:true, 默认250 typeAheadDelay:3000, false则不可编辑，默认为 true
		editable : false,
		displayField : 'valueName',
		valueField : 'valueCode',
		store : FossDataDictionary.getDataDictionaryStore('ADJUST_TYPE'),
		tipConfig : {
			title : load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.prompt'),
			width : 250,
			height : 150,
			// 是否随鼠标滑动
			trackMouse : true,
			// 隐藏的延迟时间,默认为500ms hideDelay: 2000,普通Form上必须配置tipType(区分普通Form和行展开的Form)
			tipType : 'normal',
			// tipBodyElement
			tipBodyElement : 'Foss.load.awardForm'
		},
		listeners : {
			change : function(text, op) {
				var adjustReasonCombo = this.up('form').form.findField('adjustReason');
				adjustReasonCombo.clearValue();
				if (text.getValue() == 'FEE_AUGMENT') {
					adjustReasonCombo.bindStore(FossDataDictionary.getDataDictionaryStore('FEE_AUGMENT_REASON'));
					adjustReasonCombo.setValue('ESCORT_FEE');// 费用增加调整原因-押车费
				} else {
					adjustReasonCombo.bindStore(FossDataDictionary.getDataDictionaryStore('FEE_REDUCE_REASON'));
					adjustReasonCombo.setValue('UNFULLLOAD');// 费用减少原因-大车小用
				}
			}
		}
	}, {
		xtype : 'combo',
		fieldLabel : load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.adjustReason'),// 调整原因
		name : 'adjustReason',
		columnWidth : .5,
		// 因为data已经取数据到本地了，所以’local’,默认为”remote”,当为remote时，下拉框将在被点击的第一次自动加载store，
		// 如果不需要自动加载，则可以把它设置为local并手动的加载store
		queryMode : 'local',
		// 默认为”all”,当设置成”query”的时候，你选择某个值后，再此下拉时，只出现匹配选项，如果设为”all”的话，每次下拉均显示全部选项
		triggerAction : 'all',
		// 延时查询，与下面的参数配合typeAhead:true, 默认250 typeAheadDelay:3000, false则不可编辑，默认为 true
		emptyText : load.outsidevehiclecharge.i18n('foss.load.assignLoadTask.ALL'),
		editable : false,
		displayField : 'valueName',
		valueField : 'valueCode',
		store : FossDataDictionary.getDataDictionaryStore('FEE_AUGMENT_REASON')
			// 费用增加原因
	}, {
		name : 'adjustFee',
		fieldLabel : load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.adjustFee'),// 调整费用
		regex : /^\d{0,30}$/,
		allowBlank : false,
		minValue : 100,
		maxValue : 800,
		labelWidth : 90,
		columnWidth : .5,
		listeners : {
			blur : function(text, op) {
				if (text.getValue().trim() == '') {
					return;
				}

				var initAddFrom = Ext.getCmp('T_load-outsidevehiclechargeindex_content').getAddInfoWindow().getOutsideVehicleChargeFrom();
				var vehicleassembleNo = initAddFrom.getForm().findField('vehicleassembleNo').getValue();
				if (vehicleassembleNo.trim() == '') {
					Ext.Msg.alert(load.outsidevehiclecharge.i18n('foss.load.assignLoadTask.prompt'),
									load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.exception.enterVehicleassembleNo'));
					initAddFrom.getForm().findField('adjustFee').setValue('');
					return;
				}
				var num = Ext.Number.from(text.getValue(), 0);
				var awardType = initAddFrom.getForm().findField('awardType').getValue();// 得到奖罚类型
				var adjustReason = initAddFrom.getForm().findField('adjustReason').getValue(); // 调整原因
				// ↓↓ 269701--lln--20150708 ↓↓
				var adjustFee = initAddFrom.getForm().findField('adjustFee').getValue(); // 调整费用
				var assembleType=initAddFrom.getForm().findField('assembleType').getValue()//配载类型
				// 如果该配载车次号为整车配载车次号,则调整费用文本框不受配置参数的限制，该文本框中只可输入正整数
				if (assembleType == 'CAR_LOAD') {
					// 该文本框中只可输入正整数
					if (!/^[1-9]\d*$/.test(adjustFee)) {
						// 系统消息提示
						Ext.Msg.alert(load.outsidevehiclecharge.i18n('foss.load.assignLoadTask.prompt'),
										load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.adjustFeeGtZero'));
					}
				} else {
					// ↑↑ 269701--lln--20150708 ↑↑
					// 选择其他时，不用校验配置项
					if (adjustReason != 'OTHER') {
						if (awardType == 'REWARD') {
							if (num <= load.outsidevehiclecharge.awardMaxFee
									&& num >= load.outsidevehiclecharge.awardMinFee) {
								// 输入金额正确可以调整
							} else {
								Ext.Msg.alert(load.outsidevehiclecharge.i18n('foss.load.assignLoadTask.prompt'),
												load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.adjustFeeOutofrangeAwardFirstPart')
														+ load.outsidevehiclecharge.awardMinFee
														+ '-'
														+ load.outsidevehiclecharge.awardMaxFee
														+ load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.adjustFeeOutofrangeLastPart'));
								initAddFrom.getForm().findField('adjustFee').setValue('');
								return;
							}
						} else {
							if (num <= load.outsidevehiclecharge.fineMaxFee
									&& num >= load.outsidevehiclecharge.fineMinFee) {
								// 输入金额正确可以调整
							} else {
								Ext.Msg.alert(
												load.outsidevehiclecharge.i18n('foss.load.assignLoadTask.prompt'),
												load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.adjustFeeOutofrangeFineFirstPart')
														+ load.outsidevehiclecharge.fineMinFee
														+ '-'
														+ load.outsidevehiclecharge.fineMaxFee
														+ load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.adjustFeeOutofrangeLastPart'));
								initAddFrom.getForm().findField('adjustFee').setValue('');
								return;
							}
						}
						var params = {
							vo : {
								adjustOutVehicleFeeDto : {
									vehicleassembleNo : vehicleassembleNo
								}
							}
						}; // 配载车次号
						// 此处判断应该放在后台, 但是前人已经这样写了, 不敢随意改动 zyx
						Ext.Ajax.request({
							url : load.realPath('queryOutsideVehicleChargeByVehicleassembleNo.action'),
							jsonData : params,
							success : function(response, opts) {
								var result = Ext.decode(response.responseText); // 查询的结果
								var adjustOutVehicleFeeDtoList = result.vo.adjustOutVehicleFeeDtoList;
								var awardFee = 0;
								var fineFee = 0;
								if (adjustOutVehicleFeeDtoList.length > 0) {
									for (var i = 0; i < adjustOutVehicleFeeDtoList.length; i++) {
										var timelinessClause = adjustOutVehicleFeeDtoList[i].timelinessClause;
										if (adjustOutVehicleFeeDtoList[i].awardType == 'FEE_AUGMENT'
												&& timelinessClause != 'Y') {
											// 该配载单奖励总金额
											awardFee = awardFee + adjustOutVehicleFeeDtoList[i].adjustFee;
											continue;
										}
										if (adjustOutVehicleFeeDtoList[i].awardType == 'FEE_REDUCE'
												&& timelinessClause != 'Y') {
											// 该配载单罚款总金额
											fineFee = fineFee + adjustOutVehicleFeeDtoList[i].adjustFee;
											continue;
										}
									}
								}
								var str = initAddFrom.getForm().findField('awardType').getValue();
								var adjustFeeCount = initAddFrom.getForm().findField('adjustFee').getValue();// 调整的费用
								var adjustFeeSum = Ext.Number.from(adjustFeeCount, 0);
								var sum = 0;
								if (initAddFrom.getForm().findField('awardType').getValue() == 'FEE_AUGMENT') {
									// 费用增加时, 调整总费用 = 本次调整费用 + 该单据所有增加的费用 -费用减少的费用(该逻辑为以前的逻辑并未修改) zyx
									sum = adjustFeeSum + awardFee - fineFee;
								} else {
									// 费用减少时, 调整总费用 = 该单据所有增加的费用 - 费用减少的费用 - 本次调整费用(该逻辑为以前的逻辑并未修改) zyx
									sum = awardFee - fineFee - adjustFeeSum;
								}
								if (sum >= -load.outsidevehiclecharge.fineMaxFee
										&& sum <= load.outsidevehiclecharge.awardMaxFee) {
									// 总费用效验通过
								} else {
									Ext.Msg.alert(
													load.outsidevehiclecharge.i18n('foss.load.assignLoadTask.prompt'),
													load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.adjustFeeOutofrangeFirstPart')
															+ (-load.outsidevehiclecharge.fineMaxFee)
															+ '-'
															+ load.outsidevehiclecharge.awardMaxFee
															+ load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.adjustFeeOutofrangeLastPart'));
									initAddFrom.getForm().findField('adjustFee').setValue('');
									return;
								}
							},
							failure : function(response, opts) {
								Ext.Msg.alert(load.outsidevehiclecharge.i18n('foss.load.assignLoadTask.prompt'),
												load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.exception.saveFailure'));
							},
							exception : function(response) {
								var json = Ext.decode(response.responseText);
								Ext.Msg.alert(load.outsidevehiclecharge.i18n('foss.load.assignLoadTask.prompt'),json.message);
							}
						});
					}
				}
			}
		}
	}, {
		name : 'vehicleNo',
		xtype : 'hiddenfield'
	}, {
		name : 'auditState',
		xtype : 'hiddenfield'

	},{//269701--lln-2015-09-09 begin
		//配载类型--区分整车和专线
		name : 'assembleType',
		xtype : 'hiddenfield'
	}//269701--lln-2015-09-09 end
	],
	buttons : [{
		text : load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.button.save'),// 保存
		cls : 'yellow_button',
		handler : function() {
			var form = this.up('form').getForm();
			var vals = this.up('form').getForm().getValues();
			vals.handoverTime = new Date(vals.handoverTime);
			vals.auditState = 'NOAUDIT';
			vals.feeTotal = Ext.Number.from(vals.feeTotal, 0) * 100;
			vals.prepaidFeeTotal = Ext.Number.from(vals.prepaidFeeTotal, 0)* 100;
			vals.arriveFeeTotal = Ext.Number.from(vals.arriveFeeTotal, 0) * 100;
			// 以分为单位
			vals.adjustFee = Ext.Number.from(vals.adjustFee, 0) * 100;
			vals.timelinessClause = 'N';
			Ext.MessageBox.confirm(load.outsidevehiclecharge.i18n('foss.load.assignLoadTask.prompt'),
					"确认保存费用调整吗", function(button, text) {
						if (button == 'yes') {
							if (form.isValid()) {
								var params = {
									vo : {
										adjustOutVehicleFeeEntity : vals
									}
								};
								Ext.Ajax.request({
									url : load.realPath('queryBillPayableIsWriteOff.action'),// 效验车辆能否进行费用调整
									jsonData : params,
									success : function(response, opts) {
										var result = Ext.decode(response.responseText); // 查询的结果
										var isAdjust = result.vo.isAdjust;
										Ext.Ajax.request({
											url : load.realPath('addOutsideVehicleCharge.action'),
											jsonData : params,
											success : function(response, opts) {
												// modify by liangfuxiang BUG-7221 2013-04-15 begin
												load.pagingBar.moveFirst();
												// modify by liangfuxiang BUG-7221 2013-04-15 end
												form.reset();
												Ext.getCmp('T_load-outsidevehiclechargeindex_content').getAddInfoWindow().close();
												Ext.ux.Toast.msg(load.outsidevehiclecharge.i18n('foss.load.assignLoadTask.prompt'),
																load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.exception.saveSuccess'),'ok', 1000);
											},
											failure : function(response, opts) {
												Ext.Msg.alert(load.outsidevehiclecharge.i18n('foss.load.assignLoadTask.prompt'),load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.exception.saveFailure'));
											},
											exception : function(response) {
												var json = Ext.decode(response.responseText);
												Ext.Msg.alert(load.outsidevehiclecharge.i18n('foss.load.assignLoadTask.prompt'),json.message);
											}
										});
									},
									failure : function(response, opts) {
										Ext.Msg.alert(load.outsidevehiclecharge.i18n('foss.load.assignLoadTask.prompt'),load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.exception.saveFailure'));
									},
									exception : function(response) {
										var json = Ext.decode(response.responseText);
										Ext.Msg.alert(load.outsidevehiclecharge.i18n('foss.load.assignLoadTask.prompt'),json.message);
									}
								});
							}
						}
					});
		}
	}],
	constructor : function(config) {
		var me = this, 
		cfg = Ext.apply({}, config);
		me.callParent([cfg]);

	}
});

// 定义edit detail里的车辆信息的panel
Ext.define('Foss.load.EditOutsideVehicleChargeForm', {
	extend : 'Ext.form.Panel',
	layout : 'column',
	frame : false,
	defaults : {
		xtype : 'textfield',
		margin : '10 10 10 10',
		anchor : '90%'
	},
	items : [{
		name : 'vehicleassembleNo',
		fieldLabel : load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.vehicleassembleNo'),// 配载车次号
		readOnly : true,
		labelWidth : 80,
		columnWidth : .5

	}, {
		name : 'origOrgName',
		fieldLabel : load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.origOrgCode'),// 出发部门
		readOnly : true,
		labelWidth : 80,
		columnWidth : .5
	}, {
		name : 'origOrgCode',
		xtype : 'hiddenfield'
	}, {
		name : 'driverName',
		fieldLabel : load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.driverName'),// 主驾驶姓名
		readOnly : true,
		labelWidth : 80,
		columnWidth : .5
	}, {
		name : 'driverCode',
		xtype : 'hiddenfield'
	}, {
		name : 'destOrgName',
		fieldLabel : load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.destOrgCode'),// 到达部门
		readOnly : true,
		labelWidth : 80,
		columnWidth : .5
	}, {
		name : 'destOrgCode',
		xtype : 'hiddenfield'
	}, {
		name : 'driverMobilePhone',
		fieldLabel : load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.driverMobilePhone'),// 司机电话
		readOnly : true,
		labelWidth : 90,
		columnWidth : .5
	}, {
		xtype : 'datefield',
		fieldLabel : load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.handoverTime'),// 走货日期
		readOnly : true,
		name : 'handoverTime',
		altFormats : 'Y,m,d|Y.m.d',
		format : 'Y-m-d',
		invalidText : load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.invalidText'),
		allowBlank : false,
		columnWidth : .5
	}, {
		name : 'prepaidFeeTotal',
		fieldLabel : load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.prepaidFeeTotal'),// 预付运费总额
		readOnly : true,
		labelWidth : 90,
		columnWidth : .5
	}, {
		name : 'feeTotal',
		fieldLabel : load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.feeTotal'),// 总运费
		readOnly : true,
		labelWidth : 90,
		columnWidth : .5
	}, {
		name : 'loadFeeTotal',
		fieldLabel : load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.loadFeeTotal'),// 装车总金额
		readOnly : true,
		labelWidth : 90,
		columnWidth : .5
	}, {
		name : 'arriveFeeTotal',
		fieldLabel : load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.arriveFeeTotal'),// 到付运费总额
		readOnly : true,
		labelWidth : 90,
		columnWidth : .5
	}, {
		xtype : 'combo',
		fieldLabel : load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.adjustType'),// 增减类型
		name : 'awardType',
		columnWidth : .5,
		// 因为data已经取数据到本地了，所以’local’,默认为”remote”,当为remote时，下拉框将在被点击的第一次自动加载store，
		// 如果不需要自动加载，则可以把它设置为local并手动的加载store
		queryMode : 'local',
		// 默认为”all”,当设置成”query”的时候，你选择某个值后，再此下拉时，只出现匹配选项，如果设为”all”的话，每次下拉均显示全部选项
		triggerAction : 'all',
		// 延时查询，与下面的参数配合 typeAhead:true, 默认250 typeAheadDelay:3000, false则不可编辑，默认为 true
		emptyText : load.outsidevehiclecharge.i18n('foss.load.assignLoadTask.ALL'),
		store : FossDataDictionary.getDataDictionaryStore('ADJUST_TYPE'),
		editable : false,
		displayField : 'valueName',
		valueField : 'valueCode',
		tipConfig : {
			title : load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.prompt'),
			width : 250,
			height : 150,
			// 是否随鼠标滑动
			trackMouse : true,
			// 隐藏的延迟时间,默认为500ms hideDelay: 2000,普通Form上必须配置tipType(区分普通Form和行展开的Form)
			tipType : 'normal',
			// tipBodyElement
			tipBodyElement : 'Foss.load.awardForm'
		},
		listeners : {
			change : function(text, op) {
				var adjustReasonCombo = this.up('form').form.findField('adjustReason');
				adjustReasonCombo.clearValue();
				if (text.getValue() == 'FEE_AUGMENT') {
					adjustReasonCombo.bindStore(FossDataDictionary.getDataDictionaryStore('FEE_AUGMENT_REASON'));
					adjustReasonCombo.setValue('ESCORT_FEE');// 费用增加调整原因-押车费
				} else {
					adjustReasonCombo.bindStore(FossDataDictionary.getDataDictionaryStore('FEE_REDUCE_REASON'));
					adjustReasonCombo.setValue('UNFULLLOAD');// 费用减少原因-大车小用
				}
			}
		}
	}, {
		xtype : 'combo',
		fieldLabel : load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.adjustReason'),// 调整原因
		name : 'adjustReason',
		editable : false,
		columnWidth : .5,
		displayField : 'valueName',
		valueField : 'valueCode',
		// 因为data已经取数据到本地了，所以’local’,默认为”remote”,当为remote时，下拉框将在被点击的第一次自动加载store,
		// 如果不需要自动加载，则可以把它设置为local并手动的加载store
		store : FossDataDictionary.getDataDictionaryStore('FEE_AUGMENT_REASON')
	}, {
		name : 'adjustFee',
		fieldLabel : load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.adjustFee'),// 调整费用
		regex : /^\d{0,30}$/,
		allowBlank : false,
		minValue : 100,
		maxValue : 800,
		labelWidth : 90,
		columnWidth : .5,
		listeners : {
			blur : function(text, op) {
				if (text.getValue().trim() == '') {
					return;
				}
				var initEditFrom = Ext.getCmp('T_load-outsidevehiclechargeindex_content').getEditDetailInfoWindow().getEditOutsideVehicleChargeForm();
				var vehicleassembleNo = initEditFrom.getForm().findField('vehicleassembleNo').getValue();
				if (vehicleassembleNo.trim() == '') {
					Ext.Msg.alert(load.outsidevehiclecharge.i18n('foss.load.assignLoadTask.prompt'),'请输入配载车次号!');
					initEditFrom.getForm().findField('adjustFee').setValue('');
					return;
				}
				var num = Ext.Number.from(text.getValue(), 0);
				var str = initEditFrom.getForm().findField('awardType').getValue();// 得到奖罚类型
				var adjustReason = initEditFrom.getForm().findField('adjustReason').getValue(); // 调整原因
				// ↓↓ 269701--lln--20150708 ↓↓
				var adjustFee = initEditFrom.getForm().findField('adjustFee').getValue(); // 调整费用
				var assembleType=initEditFrom.getForm().findField('assembleType').getValue()//配载类型
				// 如果该配载车次号为整车配载车次号,则调整费用文本框不受配置参数的限制，该文本框中只可输入正整数
				if (assembleType == 'CAR_LOAD') {
					// 该文本框中只可输入正整数
					if (!/^[1-9]\d*$/.test(adjustFee)) {
						// 系统消息提示
						Ext.Msg.alert(load.outsidevehiclecharge.i18n('foss.load.assignLoadTask.prompt'),load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.adjustFeeGtZero'));
					}
				} else {
					// ↑↑ 269701--lln--20150708 ↑↑
					if (adjustReason != 'OTHER') {
						if (str == 'REWARD') {
							if (num <= load.outsidevehiclecharge.awardMaxFee
									&& num >= load.outsidevehiclecharge.awardMinFee) {
								// 输入金额正确可以调整
							} else {
								Ext.Msg.alert(load.outsidevehiclecharge.i18n('foss.load.assignLoadTask.prompt'),load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.adjustFeeOutofrangeAwardFirstPart')+ load.outsidevehiclecharge.awardMinFee+ '-'+ load.outsidevehiclecharge.awardMaxFee+ load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.adjustFeeOutofrangeLastPart'));
								initEditFrom.getForm().findField('adjustFee').setValue('');
								return;
							}
						} else {
							if (num <= load.outsidevehiclecharge.fineMaxFee
									&& num >= load.outsidevehiclecharge.fineMinFee) {
								// 输入金额正确可以调整
							} else {
								Ext.Msg.alert(load.outsidevehiclecharge.i18n('foss.load.assignLoadTask.prompt'),load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.adjustFeeOutofrangeFineFirstPart')+ load.outsidevehiclecharge.fineMinFee+ '-'+ load.outsidevehiclecharge.fineMaxFee+ load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.adjustFeeOutofrangeLastPart'));
								initEditFrom.getForm().findField('adjustFee').setValue('');
								return;
							}
						}
						var params = {
							vo : {
								adjustOutVehicleFeeDto : {
									vehicleassembleNo : vehicleassembleNo
								}
							}
						}; // 配载车次号
						Ext.Ajax.request({
							url : load.realPath('queryOutsideVehicleChargeByVehicleassembleNo.action'),
							jsonData : params,
							success : function(response, opts) {
								var result = Ext.decode(response.responseText); // 查询的结果
								var adjustOutVehicleFeeDtoList = result.vo.adjustOutVehicleFeeDtoList;
								var initEditFrom = Ext.getCmp('T_load-outsidevehiclechargeindex_content').getEditDetailInfoWindow().getEditOutsideVehicleChargeForm();
								var awardFee = 0;
								var fineFee = 0;
								if (adjustOutVehicleFeeDtoList.length > 0) {
									for (var i = 0; i < adjustOutVehicleFeeDtoList.length; i++) {
										// modify by zyx 2013年12月26日 17:22:09
										// 为什么是取第0个费用的ID? 完全搞不懂, 无力吐槽了
										// if(adjustOutVehicleFeeDtoList[0].id != initEditFrom.getForm().findField('id').getValue()){//判断
										if (adjustOutVehicleFeeDtoList[i].id != initEditFrom.getForm().findField('id').getValue()) {// 判断
											var timelinessClause = adjustOutVehicleFeeDtoList[i].timelinessClause;
											if (adjustOutVehicleFeeDtoList[i].awardType == 'FEE_AUGMENT'
													&& timelinessClause != 'Y') {
												// 该配载单奖励总金额
												awardFee = awardFee+ adjustOutVehicleFeeDtoList[i].adjustFee;
												continue;
											}
											if (adjustOutVehicleFeeDtoList[i].awardType == 'FEE_REDUCE'
													&& timelinessClause != 'Y') {
												// 该配载单罚款总金额
												fineFee = fineFee+ adjustOutVehicleFeeDtoList[i].adjustFee;
												continue;
											}
										}
									}
								}
								var str = initEditFrom.getForm().findField('awardType').getValue();
								var adjustFeeCount = initEditFrom.getForm().findField('adjustFee').getValue();// 调整的费用
								var adjustFeeSum = Ext.Number.from(adjustFeeCount, 0);
								var sum = 0;
								if (initEditFrom.getForm().findField('awardType').getValue() == 'FEE_AUGMENT') {
									// 费用增加时, 调整总费用 = 本次调整费用 + 该单据所有增加的费用 -
									// 费用减少的费用(该逻辑为以前的逻辑并未修改) zyx
									sum = adjustFeeSum + awardFee - fineFee;
								} else {
									// 费用减少时, 调整总费用 = 该单据所有增加的费用 - 费用减少的费用 -
									// 本次调整费用(该逻辑为以前的逻辑并未修改) zyx
									sum = awardFee - fineFee - adjustFeeSum;
								}
								if (sum >= -load.outsidevehiclecharge.fineMaxFee
										&& sum <= load.outsidevehiclecharge.awardMaxFee) {
									// 总费用效验通过
								} else {
									Ext.Msg.alert(load.outsidevehiclecharge.i18n('foss.load.assignLoadTask.prompt'),
													load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.adjustFeeOutofrangeFirstPart')
															+ (-load.outsidevehiclecharge.fineMaxFee)
															+ '-'
															+ load.outsidevehiclecharge.awardMaxFee
															+ load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.adjustFeeOutofrangeLastPart'));
									initEditFrom.getForm().findField('adjustFee').setValue('');
									return;
								}
							},
							failure : function(response, opts) {
								Ext.Msg.alert(load.outsidevehiclecharge.i18n('foss.load.assignLoadTask.prompt'),load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.exception.saveFailure'));
							},
							exception : function(response) {
								var json = Ext.decode(response.responseText);
								Ext.Msg.alert(load.outsidevehiclecharge.i18n('foss.load.assignLoadTask.prompt'),json.message);
							}
						});
					}
				}
			}
		}
	}, {
		name : 'vehicleNo',
		xtype : 'hiddenfield'
	}, {
		name : 'id',
		xtype : 'hiddenfield'
	}, {//269701--lln-2015-09-09 begin
		//配载类型--区分整车和专线
		name : 'assembleType',
		xtype : 'hiddenfield'
	}//269701--lln-2015-09-09 end
	],
	buttons : [{
		text : load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.button.update'),// 修改
		disabled : !load.outsidevehiclecharge.isPermission('load/updateOutsideVehicleChargeButton'),
		hidden : !load.outsidevehiclecharge.isPermission('load/updateOutsideVehicleChargeButton'),
		cls : 'yellow_button',
		handler : function() {
			var form = this.up('form').getForm();
			var vals = this.up('form').getForm().getValues();
			// modify by liangfuxiang BUG-6974 2013-04-12 begin
			var adjustFeeNum = Ext.Number.from(vals.adjustFee, 0);
			var awardType = form.findField('awardType').getValue();// 得到奖罚类型
			var adjustReason = form.findField('adjustReason').getValue(); // 调整原因
			//269701--lln--2015-09-09 begin
			var assembleType = form.findField('assembleType').getValue()//配载类型
			var adjustFee = form.findField('adjustFee').getValue();//调整费用
			// 如果该配载车次号为整车配载车次号,则调整费用文本框不受配置参数的限制，该文本框中只可输入正整数
			if (assembleType == 'CAR_LOAD') {
				// 该文本框中只可输入正整数
				if (!/^[1-9]\d*$/.test(adjustFee)) {
					// 系统消息提示
					Ext.Msg.alert(load.outsidevehiclecharge.i18n('foss.load.assignLoadTask.prompt'),
									load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.adjustFeeGtZero'));
				return;
				}
			}else{ //269701--lln--2015-09-09 end
				if (adjustReason != 'OTHER') {
					if (awardType == 'REWARD') {
						if (adjustFeeNum > load.outsidevehiclecharge.awardMaxFee
								|| adjustFeeNum < load.outsidevehiclecharge.awardMinFee) {
							// modify by liangfuxiang 2013-04-15 BUG-7228 begin
							Ext.Msg.alert(
											load.outsidevehiclecharge.i18n('foss.load.assignLoadTask.prompt'),
											load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.adjustFeeOutofrangeAwardFirstPart')
													+ load.outsidevehiclecharge.awardMinFee
													+ '-'
													+ load.outsidevehiclecharge.awardMaxFee
													+ load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.adjustFeeOutofrangeLastPart'));
							// modify by liangfuxiang 2013-04-15 BUG-7228 begin
							form.findField('adjustFee').setValue('');
							return;
						}
					} else {
						if (adjustFeeNum > load.outsidevehiclecharge.fineMaxFee
								|| adjustFeeNum < load.outsidevehiclecharge.fineMinFee) {
							// modify by liangfuxiang 2013-04-15 BUG-7228 begin
							Ext.Msg.alert(
											load.outsidevehiclecharge.i18n('foss.load.assignLoadTask.prompt'),
											load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.adjustFeeOutofrangeFineFirstPart')
													+ load.outsidevehiclecharge.fineMinFee
													+ '-'
													+ load.outsidevehiclecharge.fineMaxFee
													+ load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.adjustFeeOutofrangeLastPart'));
							// modify by liangfuxiang 2013-04-15 BUG-7228 begin
							form.findField('adjustFee').setValue('');
							return;
						}
					}
				}
			}
			// modify by liangfuxiang BUG-6974 2013-04-12 end
			vals.handoverTime = new Date(vals.handoverTime);
			vals.feeTotal = Ext.Number.from(vals.feeTotal, 0) * 100;
			vals.prepaidFeeTotal = Ext.Number.from(vals.prepaidFeeTotal, 0)* 100;
			vals.arriveFeeTotal = Ext.Number.from(vals.arriveFeeTotal, 0) * 100;
			// 以分为单位
			vals.adjustFee = Ext.Number.from(vals.adjustFee, 0) * 100;
			Ext.MessageBox.confirm(
							load.outsidevehiclecharge.i18n('foss.load.assignLoadTask.prompt'),
							load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.saveFee'),
							function(button, text) {
								if (button == 'yes') {
									if (form.isValid()) {
										var params = {
											vo : {
												adjustOutVehicleFeeEntity : vals
											}
										};
										Ext.Ajax.request({
											url : load.realPath('updateOutsideVehicleCharge.action'),
											jsonData : params,
											success : function(response, opts) {
												form.reset();
												Ext.getCmp('T_load-outsidevehiclechargeindex_content').getEditDetailInfoWindow().close();
												Ext.ux.Toast.msg(load.outsidevehiclecharge.i18n('foss.load.assignLoadTask.prompt'),
																load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.exception.updateSuccess'),'ok', 1000);
												load.pagingBar.moveFirst();
											},
											failure : function(response, opts) {
												Ext.Msg.alert(load.outsidevehiclecharge.i18n('foss.load.assignLoadTask.prompt'),load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.exception.updateFailure'));
											},
											exception : function(response) {
												var json = Ext.decode(response.responseText);
												Ext.Msg.alert(load.outsidevehiclecharge.i18n('foss.load.assignLoadTask.prompt'),json.message);
											}
										});
									}
								}
							});
		}
	}],
	constructor : function(config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.callParent([cfg]);

	}
});

// 定义edit detail里的车辆信息的panel
Ext.define('Foss.load.editOutsideVehicleChargeTimelinessClauseForm', {
	extend : 'Ext.form.Panel',
	layout : 'column',
	frame : false,
	defaults : {
		xtype : 'textfield',
		margin : '10 10 10 10',
		anchor : '90%'
	},
	items : [{
		name : 'vehicleassembleNo',
		fieldLabel : load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.vehicleassembleNo'),// 配载车次号
		readOnly : true,
		labelWidth : 80,
		columnWidth : .5

	}, {
		name : 'origOrgName',
		fieldLabel : load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.origOrgCode'),// 出发部门
		readOnly : true,
		labelWidth : 80,
		columnWidth : .5
	}, {
		name : 'origOrgCode',
		xtype : 'hiddenfield'
	}, {
		name : 'driverName',
		fieldLabel : load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.driverName'),// 主驾驶姓名
		readOnly : true,
		labelWidth : 80,
		columnWidth : .5
	}, {
		name : 'driverCode',
		xtype : 'hiddenfield'
	}, {
		name : 'destOrgName',
		fieldLabel : load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.destOrgCode'),// 到达部门
		readOnly : true,
		labelWidth : 80,
		columnWidth : .5
	}, {
		name : 'destOrgCode',
		xtype : 'hiddenfield'
	}, {
		name : 'driverMobilePhone',
		fieldLabel : load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.driverMobilePhone'),// 司机电话
		readOnly : true,
		labelWidth : 90,
		columnWidth : .5
	}, {
		xtype : 'datefield',
		fieldLabel : load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.handoverTime'),// 走货日期
		readOnly : true,
		name : 'handoverTime',
		altFormats : 'Y,m,d|Y.m.d',
		format : 'Y-m-d',
		invalidText : load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.invalidText'),
		allowBlank : false,
		columnWidth : .5
	}, {
		name : 'prepaidFeeTotal',
		fieldLabel : load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.prepaidFeeTotal'),// 预付运费总额
		readOnly : true,
		labelWidth : 90,
		columnWidth : .5
	}, {
		name : 'feeTotal',
		fieldLabel : load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.feeTotal'),// 总运费
		readOnly : true,
		labelWidth : 90,
		columnWidth : .5
	}, {
		name : 'loadFeeTotal',
		fieldLabel : load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.loadFeeTotal'),// 装车总金额
		readOnly : true,
		labelWidth : 90,
		columnWidth : .5
	}, {
		name : 'arriveFeeTotal',
		fieldLabel : load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.arriveFeeTotal'),// 到付运费总额
		readOnly : true,
		labelWidth : 90,
		columnWidth : .5
	}, {
		name : 'timelinessDurationStr',
		fieldLabel : load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.timelinessDuration'),// 变化时长
		readOnly : true,
		labelWidth : 90,
		columnWidth : 1
	}, {
		xtype : 'combo',
		fieldLabel : load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.cause'),// 产生原因
		name : 'cause',
		columnWidth : .5,
		queryMode : 'local',
		emptyText : load.outsidevehiclecharge.i18n('foss.load.assignLoadTask.ALL'),
		store : Ext.create('Foss.load.outsideVehicleCharge.cause.Store', {
			data : {
				'items' : [{
					'code' : 'AGINGINADVANCE',
					'name' : load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.cause.aginginadvance')
				}, {
					'code' : 'AGINGDELAY',
					'name' : load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.cause.agingdelay')
				}]
			}
		}),
		editable : false,
		displayField : 'name',
		valueField : 'code',
		listeners : {
			change : function(text, op) {
				var awardTypeCombo = this.up('form').form.findField('awardType');
				var adjustReasonCombo = this.up('form').form.findField('adjustReason');
				awardTypeCombo.clearValue();
				adjustReasonCombo.clearValue();
				if (text.getValue() == 'AGINGINADVANCE') {
					// 产生原因-时效提前
					awardTypeCombo.setValue('REWARD');// 奖励
					// 调整原因-时效提前原因
					adjustReasonCombo.bindStore(FossDataDictionary.getDataDictionaryStore('CAUSE_AGINGINADVANCE_REASON'));
				} else {
					awardTypeCombo.setValue('FINE');// 罚款
					// 调整原因-时效延误原因
					adjustReasonCombo.bindStore(FossDataDictionary.getDataDictionaryStore('CAUSE_AGINGDELAY_REASON'));
				}

				var firstValue = adjustReasonCombo.store.getAt(0).get('valueCode');
				// 设置默认选中第一个值
				adjustReasonCombo.setValue(firstValue);
			}
		}
	}, {
		xtype : 'combo',
		fieldLabel : load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.awardType'),// 奖罚类型
		name : 'awardType',
		columnWidth : .5,
		queryMode : 'local',
		// 默认为”all”,当设置成”query”的时候，你选择某个值后，再此下拉时，只出现匹配选项，如果设为”all”的话，每次下拉均显示全部选项
		triggerAction : 'all',
		emptyText : load.outsidevehiclecharge.i18n('foss.load.assignLoadTask.ALL'),
		store : FossDataDictionary.getDataDictionaryStore('AWARD_TYPE'),
		editable : false,
		readOnly : true,
		displayField : 'valueName',
		valueField : 'valueCode'
	}, {
		xtype : 'combo',
		fieldLabel : load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.adjustReason'),// 调整原因
		name : 'adjustReason',
		editable : false,
		columnWidth : .5,
		queryMode : 'local',
		// 默认为”all”,当设置成”query”的时候，你选择某个值后，再此下拉时，只出现匹配选项，如果设为”all”的话，每次下拉均显示全部选项
		triggerAction : 'all',
		displayField : 'valueName',
		valueField : 'valueCode',
		// 因为data已经取数据到本地了，所以’local’,默认为”remote”,当为remote时，下拉框将在被点击的第一次自动加载store,
		// 如果不需要自动加载，则可以把它设置为local并手动的加载store
		store : FossDataDictionary.getDataDictionaryStore('FEE_AUGMENT_REASON')
	}, {
		name : 'adjustFee',
		fieldLabel : load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.adjustFee'),// 调整费用
		regex : /^\d{0,30}$/,
		allowBlank : false,
		minValue : 100,
		maxValue : 800,
		labelWidth : 90,
		columnWidth : .5,
		listeners : {
			blur : function(text, op) {
				if (text.getValue().trim() == '') {
					return;
				}
				var initEditFrom = this.up('form');
				var vehicleassembleNo = initEditFrom.getForm().findField('vehicleassembleNo').getValue();
				if (vehicleassembleNo.trim() == '') {
					Ext.Msg.alert(load.outsidevehiclecharge.i18n('foss.load.assignLoadTask.prompt'),'请输入配载车次号!');
					initEditFrom.getForm().findField('adjustFee').setValue('');
					return;
				}
				var num = Ext.Number.from(text.getValue(), 0);
				var awardType = initEditFrom.getForm().findField('awardType').getValue();// 得到奖罚类型
				var adjustReason = initEditFrom.getForm().findField('adjustReason').getValue(); // 调整原因
				// ↓↓ 269701--lln--20150708 ↓↓
				var adjustFee = initEditFrom.getForm().findField('adjustFee').getValue(); // 调整费用
				var assembleType=initEditFrom.getForm().findField('assembleType').getValue();//配载类型
				// 如果该配载车次号为整车配载车次号,则调整费用文本框不受配置参数的限制，该文本框中只可输入正整数
				if (assembleType == 'CAR_LOAD') {
					// 该文本框中只可输入正整数
					if (!/^[1-9]\d*$/.test(adjustFee)) {
						// 系统消息提示
						Ext.Msg.alert(load.outsidevehiclecharge.i18n('foss.load.assignLoadTask.prompt'),load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.adjustFeeGtZero'));
					}
				} else {
				// ↑↑ 269701--lln--20150708 ↑↑
					if (awardType == 'REWARD') {
						if (num <= load.outsidevehiclecharge.timelinessAwardMaxFee && num >= 0) {
							// 输入金额正确可以调整
						} else {
							Ext.Msg.alert(load.outsidevehiclecharge.i18n('foss.load.assignLoadTask.prompt'),
											load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.adjustFeeOutofrangeAwardFirstPart')
													+ 0
													+ '-'
													+ load.outsidevehiclecharge.timelinessAwardMaxFee
													+ load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.adjustFeeOutofrangeLastPart'));
							initEditFrom.getForm().findField('adjustFee').setValue('');
							return;
						}
					} else {
						if (num <= load.outsidevehiclecharge.timelinessFineMaxFee && num >= 0) {
							// 输入金额正确可以调整
						} else {
							Ext.Msg.alert(load.outsidevehiclecharge.i18n('foss.load.assignLoadTask.prompt'),
											load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.adjustFeeOutofrangeFineFirstPart')
													+ 0
													+ '-'
													+ load.outsidevehiclecharge.timelinessFineMaxFee
													+ load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.adjustFeeOutofrangeLastPart'));
							initEditFrom.getForm().findField('adjustFee').setValue('');
							return;
						}
					}
				}
			}
		}
	}, {
		name : 'vehicleNo',
		xtype : 'hiddenfield'
	}, {
		name : 'id',
		xtype : 'hiddenfield'
	}, {//269701--lln-2015-09-09 begin
		//配载类型--区分整车和专线
		name : 'assembleType',
		xtype : 'hiddenfield'
	}//269701--lln-2015-09-09 end
	],
	buttons : [{
		text : load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.button.update'),// 修改
		disabled : !load.outsidevehiclecharge.isPermission('load/updateOutsideVehicleChargeButton'),
		hidden : !load.outsidevehiclecharge.isPermission('load/updateOutsideVehicleChargeButton'),
		cls : 'yellow_button',
		handler : function() {
			var form = this.up('form').getForm();
			var vals = this.up('form').getForm().getValues();
			// modify by liangfuxiang BUG-6974 2013-04-12 begin
			var adjustFeeNum = Ext.Number.from(vals.adjustFee, 0);
			var str = form.findField('awardType').getValue();// 得到奖罚类型
			var adjustReason = form.findField('adjustReason').getValue(); // 调整原因
			var assembleType = form.findField('assembleType').getValue();//配载类型
			// if(adjustReason!='OTHER'){
			// if (str=='REWARD') {
			// if (adjustFeeNum>load.outsidevehiclecharge.awardMaxFee ||
			// adjustFeeNum<load.outsidevehiclecharge.awardMinFee) {
			// //modify by liangfuxiang 2013-04-15 BUG-7228 begin
			// Ext.Msg.alert(load.outsidevehiclecharge.i18n('foss.load.assignLoadTask.prompt'),load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.adjustFeeOutofrangeAwardFirstPart')+load.outsidevehiclecharge.awardMinFee+'-'+load.outsidevehiclecharge.awardMaxFee+load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.adjustFeeOutofrangeLastPart'));
			// //modify by liangfuxiang 2013-04-15 BUG-7228 begin
			// form.findField('adjustFee').setValue('');
			// return ;
			// }
			// }else{
			// if (adjustFeeNum>load.outsidevehiclecharge.fineMaxFee ||
			// adjustFeeNum<load.outsidevehiclecharge.fineMinFee) {
			// //modify by liangfuxiang 2013-04-15 BUG-7228 begin
			// Ext.Msg.alert(load.outsidevehiclecharge.i18n('foss.load.assignLoadTask.prompt'),load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.adjustFeeOutofrangeFineFirstPart')+load.outsidevehiclecharge.fineMinFee+'-'+load.outsidevehiclecharge.fineMaxFee+load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.adjustFeeOutofrangeLastPart'));
			// //modify by liangfuxiang 2013-04-15 BUG-7228 begin
			// form.findField('adjustFee').setValue('');
			// return;
			// }
			// }
			// }
			// modify by liangfuxiang BUG-6974 2013-04-12 end
			vals.handoverTime = new Date(vals.handoverTime);
			vals.feeTotal = Ext.Number.from(vals.feeTotal, 0) * 100;
			vals.prepaidFeeTotal = Ext.Number.from(vals.prepaidFeeTotal, 0) * 100;
			vals.arriveFeeTotal = Ext.Number.from(vals.arriveFeeTotal, 0) * 100;
			// 以分为单位
			vals.adjustFee = Ext.Number.from(vals.adjustFee, 0) * 100;
			Ext.MessageBox.confirm(load.outsidevehiclecharge.i18n('foss.load.assignLoadTask.prompt'),
							load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.saveFee'),
							function(button, text) {
								if (button == 'yes') {
									if (form.isValid()) {
										var params = {
											vo : {
												adjustOutVehicleFeeEntity : vals
											}
										};
										Ext.Ajax.request({
											url : load.realPath('updateOutsideVehicleCharge.action'),
											jsonData : params,
											success : function(response, opts) {
												form.reset();
												Ext.getCmp('T_load-outsidevehiclechargeindex_content').getEditDetailInfoTimelinessClauseWindow().close();
												Ext.ux.Toast.msg(load.outsidevehiclecharge.i18n('foss.load.assignLoadTask.prompt'),
																load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.exception.updateSuccess'),'ok', 1000);
												load.pagingBar.moveFirst();
											},
											failure : function(response, opts) {
												Ext.Msg.alert(
																load.outsidevehiclecharge.i18n('foss.load.assignLoadTask.prompt'),
																load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.exception.updateFailure'));
											},
											exception : function(response) {
												var json = Ext.decode(response.responseText);
												Ext.Msg.alert(load.outsidevehiclecharge.i18n('foss.load.assignLoadTask.prompt'),json.message);
											}
										});
									}
								}
							});

		}
	}],
	constructor : function(config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.callParent([cfg]);

	}
});

// ↓↓↓↓↓↓↓↓ 269701(lln) 2015-07-09 下午 14:25 ↓↓↓↓↓↓↓↓
// 定义审批From
Ext.define('Foss.load.ApprovalOutsideVehicleChargeForm', {
	extend : 'Ext.form.Panel',
	layout : 'column',
	frame : false,
	defaults : {
		xtype : 'textfield',
		margin : '10 10 10 10',
		anchor : '90%'
	},
	items : [{
		name : 'vehicleassembleNo',
		fieldLabel : load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.vehicleassembleNo'),// 配载车次号
		readOnly : true,
		labelWidth : 80,
		columnWidth : .5

	}, {
		name : 'origOrgName',
		fieldLabel : load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.origOrgCode'),// 出发部门
		readOnly : true,
		labelWidth : 80,
		columnWidth : .5
	}, {
		name : 'origOrgCode',
		xtype : 'hiddenfield'
	}, {
		name : 'driverName',
		fieldLabel : load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.driverName'),// 主驾驶姓名
		readOnly : true,
		labelWidth : 80,
		columnWidth : .5
	}, {
		name : 'driverCode',
		xtype : 'hiddenfield'
	}, {
		name : 'destOrgName',
		fieldLabel : load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.destOrgCode'),// 到达部门
		readOnly : true,
		labelWidth : 80,
		columnWidth : .5
	}, {
		name : 'destOrgCode',
		xtype : 'hiddenfield'
	}, {
		name : 'driverMobilePhone',
		fieldLabel : load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.driverMobilePhone'),// 司机电话
		readOnly : true,
		labelWidth : 90,
		columnWidth : .5
	}, {
		xtype : 'datefield',
		fieldLabel : load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.handoverTime'),// 走货日期
		readOnly : true,
		name : 'handoverTime',
		altFormats : 'Y,m,d|Y.m.d',
		format : 'Y-m-d',
		invalidText : load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.invalidText'),
		allowBlank : false,
		columnWidth : .5
	}, {
		name : 'prepaidFeeTotal',
		fieldLabel : load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.prepaidFeeTotal'),// 预付运费总额
		readOnly : true,
		labelWidth : 90,
		columnWidth : .5
	}, {
		name : 'feeTotal',
		fieldLabel : load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.feeTotal'),// 总运费
		readOnly : true,
		labelWidth : 90,
		columnWidth : .5
	}, {
		name : 'loadFeeTotal',
		fieldLabel : load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.loadFeeTotal'),// 装车总金额
		readOnly : true,
		labelWidth : 90,
		columnWidth : .5
	}, {
		name : 'arriveFeeTotal',
		fieldLabel : load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.arriveFeeTotal'),// 到付运费总额
		readOnly : true,
		labelWidth : 90,
		columnWidth : .5
	}, {
		xtype : 'combo',
		name : 'awardType',
		fieldLabel : load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.adjustType'),// 增减类型
		labelWidth : 90,
		columnWidth : .5,
		queryMode : 'local',
		// 默认为”all”,当设置成”query”的时候，你选择某个值后，再此下拉时，只出现匹配选项，如果设为”all”的话，每次下拉均显示全部选项
		triggerAction : 'all',
		// 延时查询，与下面的参数配合 typeAhead:true,默认250 typeAheadDelay:3000, false则不可编辑，默认为 true
		emptyText : load.outsidevehiclecharge.i18n('foss.load.assignLoadTask.ALL'),
		store : FossDataDictionary.getDataDictionaryStore('ADJUST_TYPE'),
		editable : false,
		readOnly : true,
		displayField : 'valueName',
		valueField : 'valueCode'
	}, {
		xtype : 'combo',
		name : 'adjustReason',
		fieldLabel : load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.adjustReason'),// 调整原因
		readOnly : true,
		labelWidth : 90,
		columnWidth : .5,
		editable : false,
		displayField : 'valueName',
		valueField : 'valueCode',
		store : FossDataDictionary.getDataDictionaryStore('ADJUST_REASON')
	}, {
		name : 'adjustFee',
		fieldLabel : load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.adjustFee'),// 调整费用
		readOnly : true,
		labelWidth : 90,
		columnWidth : .5
	}, {
		xtype : 'container',
		border : false,
		labelWidth : 90,
		columnWidth : .5,
		html : '&nbsp;'
	}, {
		xtype : 'textarea',
		name : 'approvalOpinion',
		height : '30',
		fieldLabel : load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.approvalOpinion'),// 审批意见
		labelWidth : 90,
		columnWidth : .8,
		maxLength : 80,
		maxLengthText : load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.maxLengthText'),
		rowspan : 3
	}, {
		name : 'vehicleNo',
		xtype : 'hiddenfield'
	}, {
		name : 'id',
		xtype : 'hiddenfield'
	}],
	buttons : [{
		text : load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.button.notPass'),// 不通过
		cls : 'yellow_button',
		handler : function() {
			var form = this.up('form').getForm();
			var vals = this.up('form').getForm().getValues();
			vals.auditState = 'AUDITNOTPASS';
			// 当点击“不通过”时，审批意见文本框不可为空
			if (vals.approvalOpinion == '') {
				Ext.Msg.alert(
								load.outsidevehiclecharge.i18n('foss.load.assignLoadTask.prompt'),
								load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.enterApprovalState'));
			} else {
				if (form.isValid()) {
					var params = {
						vo : {
							adjustOutVehicleFeeEntity : vals
						}
					};
					Ext.Ajax.request({
						url : load.realPath('approvalOutsideVehicleCharge.action'),
						jsonData : params,
						success : function(response, opts) {
							form.reset();
							Ext.getCmp('T_load-outsidevehiclechargeindex_content').getApprovalInfoWindow().close();
							load.pagingBar.moveFirst();
							Ext.ux.Toast.msg(
											load.outsidevehiclecharge.i18n('foss.load.assignLoadTask.prompt'),
											load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.exception.approvalDoNotPass'),'ok', 1000);// 审批不通过
						},
						failure : function(response, opts) {
							Ext.Msg.alert(
											load.outsidevehiclecharge.i18n('foss.load.assignLoadTask.prompt'),
											load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.exception.approvalFailure'));// 审批失败
						},
						exception : function(response) {
							var json = Ext.decode(response.responseText);
							Ext.Msg.alert(load.outsidevehiclecharge.i18n('foss.load.assignLoadTask.prompt'),json.message);
						}
					});
				}
			}
		}
	}, {
		text : load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.button.pass'),// 通过
		cls : 'yellow_button',
		handler : function() {
			var form = this.up('form').getForm();
			var vals = this.up('form').getForm().getValues();
			var btnPass = this;
			vals.auditState = 'AUDITPASS';
			if (form.isValid()) {
				btnPass.setDisabled(true);
				var paramsDto = {
					vo : {
						adjustOutVehicleFeeDto : vals
					}
				};
				Ext.Ajax.request({
					url : load.realPath('approvalAdjustOutVehicleFee.action'),
					jsonData : paramsDto,
					success : function(response, opts) {
						var result = Ext.decode(response.responseText); // 查询的结果
						var num = result.vo.isNum;
						if (num == 1) {
							var params = {
								vo : {
									adjustOutVehicleFeeEntity : vals
								}
							};
							Ext.Ajax.request({
								url : load.realPath('approvalOutsideVehicleCharge.action'),
								jsonData : params,
								success : function(response, opts) {
									btnPass.setDisabled(false);
									form.reset();
									Ext.getCmp('T_load-outsidevehiclechargeindex_content').getApprovalInfoWindow().close();
									load.pagingBar.moveFirst();
									Ext.ux.Toast.msg(load.outsidevehiclecharge.i18n('foss.load.assignLoadTask.prompt'),
													load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.exception.approvalSuccess'),
													'ok', 1000);// 审批成功
								},
								failure : function(response, opts) {
									btnPass.setDisabled(false);
									Ext.Msg.alert(load.outsidevehiclecharge.i18n('foss.load.assignLoadTask.prompt'),
													load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.exception.approvalFailure'));// 审批失败
								}
							});
						} else {
							btnPass.setDisabled(false);
							Ext.Msg.alert(
											load.outsidevehiclecharge.i18n('foss.load.assignLoadTask.prompt'),
											load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.exception.approvalDoNotPass'));// 审批不通过
						}
					},
					failure : function(response, opts) {
						btnPass.setDisabled(false);
						Ext.Msg.alert(
										load.outsidevehiclecharge.i18n('foss.load.assignLoadTask.prompt'),
										load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.exception.approvalFailure'));// 审批失败
					},
					exception : function(response) {
						btnPass.setDisabled(false);
						var json = Ext.decode(response.responseText);
						Ext.Msg.alert(load.outsidevehiclecharge.i18n('foss.load.assignLoadTask.prompt'),json.message);
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

// 修改日志
Ext.define('Foss.load.outsideVehicleChargeLogGrid', {
	extend : 'Ext.grid.Panel',
	title : load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.outsideVehicleChargeLogGrid.title'),// 修改日志
	frame : true,
	animCollapse : false,
	cls : 'autoHeight',
	bodyCls : 'autoHeight',
	store : null,
	selModel : null,
	autoScroll : true,
	columns : [

	{		// 操作人为当前记录的操作人
		header : load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.createUserName'),// 操作人
		minWidth : 100,
		dataIndex : 'createUserName'
	}, {	// 操作时间为当前记录的操作时间
		header : load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.createTime'),// 操作时间
		minWidth : 130,
		dataIndex : 'createTime'
	}, {
		header : load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.logType'),// 操作类别
		minWidth : 100,
		dataIndex : 'logType',
		renderer : function(val) {
				// 当某条记录新增之后，操作类别记录为“新增”；
				if (val == 'ADDLOG') {
					return load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.addLog');// 新增
						// 当某条记录修改之后，操作类别记录为“修改”
					} else if (val == 'UPDATELOG') {
						return load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.updateLog');// 修改
						// 当某条记录审核通过之后，操作类别记录为“审核”
					} else if (val == 'AUDITLOG') {
						return load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.auditLog');// 审核
						// 当某条记录审批通过之后，操作类别记录为“审批”
					} else if (val == 'APPROVALLOG') {
						return load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.approvalLog');// 审批
						// 当某条记录审核或审批不通过之后，操作类别记录为“退回”
					} else if (val == 'BACKLOG') {
						return load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.backLog');// 退回
					}else if (val == 'DELETELOG'){
						return load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.deleteLog');// 删除
					}
				}
			}, {// 当审核/审批 通过/不通过时，操作内容显示为审核/审批 通过/不通过意见；
				xtype : 'linebreakcolumn',
				text : load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.modifyContent'),// 操作内容
				sortable : true,
				flex : 1,
				dataIndex : 'modifyContent'
			}, {
				header : 'updateLogId',
				dataIndex : 'dataIndex',
				hidden : true
			}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		// 创建数据源
		me.store = Ext.create('Foss.load.outsidevehiclecharge.outsideVehicleChargeLogGridStore');
		// 为获取的数据源分页
		me.bbar = Ext.create('Deppon.StandardPaging', {
					store : me.store,
					pageSize : 5
				});
		load.outsidevehiclecharge.updateLogPagebar = me.bbar;
		me.callParent([cfg]);
	}
});

// 修改日志 Model
Ext.define('Foss.load.outsidevehiclecharge.outsideVehicleChargeLogGridModel', {
			extend : 'Ext.data.Model',
			idgen : 'uuid',
			idProperty : 'uuid',
			fields : [{	name : 'id',type : 'string'},// ID
					{name : 'createUserName',type : 'string'},// 操作人
					{name : 'createTime',type : 'string'},// 操作时间=======操作时间为当前记录的操作时间
					{name : 'logType',type : 'string'},// 操作类别
					{name : 'modifyContent',type : 'string'}// 操作内容
			]
		});

// 修改日志 数据源Store
Ext.define('Foss.load.outsidevehiclecharge.outsideVehicleChargeLogGridStore', {
	extend : 'Ext.data.Store',
	// 绑定一个模型
	model : 'Foss.load.outsidevehiclecharge.outsideVehicleChargeLogGridModel',
	pageSize : 8,
	proxy : {
		type : 'ajax',
		url : load.realPath('queryUpdateLogs.action'),
		actionMethods : 'post',
		reader : {
			type : 'json',
			root : 'vo.logList',
			totalProperty : 'totalCount'
		}
	},
	listeners : {
		beforeload : function(store, operation, eOpts) {
			// 选中行数据
			var records = Ext.getCmp('Foss_load_outsidevehiclecharge_outsideVehicleChargeGrid_Id').getSelectionModel().getSelection();
			// 调整外请车费用ID
			var adjustOutVehicleFeeId='1';
			if(!Ext.isEmpty(records)){
				// 调整外请车费用ID
				adjustOutVehicleFeeId = records[0].data.id;
			}else{
				// 调整外请车费用ID
				adjustOutVehicleFeeId = '1';
			}
			
			var queryParams = {
				'vo.adjustOutVehicleFeeLogDto.adjustOutVehicleFeeId' : adjustOutVehicleFeeId
				// 调整外请车费用ID
			};

			Ext.apply(operation, {
						params : queryParams
					});
		}
	},
	constructor : function(config) {
		var me = this, 
		cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

// 查询修改记录
load.outsidevehiclecharge.loadUpdateLog = function() {
	if (load.outsidevehiclecharge.outsideVehicleChargeLogGrid) {
		load.outsidevehiclecharge.updateLogPagebar.moveFirst();
	}
}
// ↑↑↑↑↑↑↑↑ 269701(lln) 2015-07-11 ↑↑↑↑↑↑↑↑

Ext.define('Foss.load.outsidevehiclecharge.timelinessClauseGridModel', {
			extend : 'Ext.data.Model',
			fields : [{
						name : 'rewardOrPunishType',type : 'string'
					}, {
						name : 'timeLimit',type : 'string'
					}, {
						name : 'agreementMoney',type : 'string'
					}]
		});
// 外请车时效条款表格Store
Ext.define('Foss.load.outsidevehiclecharge.timelinessClauseGridStore', {
			extend : 'Ext.data.Store',
			model : 'Foss.load.outsidevehiclecharge.timelinessClauseGridModel',
			proxy : {
				type : 'memory',
				reader : {
					type : 'json',
					root : 'items'
				}
			}
		});
// 外请车时效条款表格panel
Ext.define('Foss.load.outsidevehiclecharge.timelinessClauseGridPanel', {
	extend : 'Ext.grid.Panel',
	height : 250,
	stripeRows : true,
	frame : true,
	animCollapse : true,
	autoScroll : true,
	store : null,
	columns : [{
				header : '条款类型',
				dataIndex : 'rewardOrPunishType',
				flex : 1
			}, {
				header : '时间段',
				dataIndex : 'timeLimit',
				flex : 1
			}, {
				header : '条款金额',
				dataIndex : 'agreementMoney',
				flex : 1
			}],
	dockedItems : [{
				xtype : 'toolbar',
				dock : 'bottom',
				layout : 'column',
				defaults : {
					margin : '5 5 5 5',
					xtype : 'textfield',
					readOnly : true,
					anchor : '100%',
					labelWidth : 80
				},
				items : [{
							id : 'T_load-outsidevehiclecharge_awardMaxFeeField',
							fieldLabel : '本次奖励封顶费用',
							columnWidth : 1 / 2,
							labelWidth : 120,
							xtype : 'numberfield',
							value : 0
						}, {
							id : 'T_load-outsidevehiclecharge_fineMaxFeeField',
							fieldLabel : '本次惩罚封顶费用',
							columnWidth : 1 / 2,
							labelWidth : 120,
							xtype : 'numberfield',
							value : 0
						}]
			}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.load.outsidevehiclecharge.timelinessClauseGridStore');
		me.callParent([cfg]);
	}
});

// 定义edit detail里的车辆信息的panel
Ext.define('Foss.load.auditForm', {
	extend : 'Ext.form.Panel',
	layout : 'column',
	frame : false,
	defaults : {
		xtype : 'textfield',
		margin : '5 5 10 5',
		anchor : '90%'
	},
	items : [{
				name : 'id',xtype : 'hiddenfield'
			}, {
				name : 'vehicleassembleNo',xtype : 'hiddenfield'
			}, {
				name : 'awardType',xtype : 'hiddenfield'
			}, {
				name : 'adjustFee',xtype : 'hiddenfield'
			}, {
				xtype : 'combo',
				fieldLabel : load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.adjustReason'),// 调整原因
				name : 'adjustReason',
				columnWidth : .8,
				readOnly : true,
				// 因为data已经取数据到本地了，所以’local’,默认为”remote”,当为remote时，下拉框将在被点击的第一次自动加载store，
				// 如果不需要自动加载，则可以把它设置为local并手动的加载store
				queryMode : 'local',
				// 默认为”all”,当设置成”query”的时候，你选择某个值后，再此下拉时，只出现匹配选项，如果设为”all”的话，每次下拉均显示全部选项
				triggerAction : 'all',
				// 延时查询，与下面的参数配合
				// typeAhead:true,
				// 默认250
				// typeAheadDelay:3000,
				// false则不可编辑，默认为 true
				editable : false,
				displayField : 'valueName',
				valueField : 'valueCode',
				store : FossDataDictionary.getDataDictionaryStore('ADJUST_REASON')
			}, {
				xtype : 'textarea',
				name : 'auditOpinion',
				height : '30',
				fieldLabel : load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.auditOpinion'),// 审核意见
				labelWidth : 90,
				columnWidth : .9,
				maxLength : 80,
				maxLengthText : load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.maxLengthText'),
				rowspan : 3
			}],
	buttons : [{
		text : load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.button.notPass'),// 不通过
		cls : 'yellow_button',
		handler : function() {
			var form = this.up('form').getForm();
			var vals = this.up('form').getForm().getValues();
			vals.auditState = 'AUDITNOTPASS';
			if (vals.auditOpinion == '') {
				Ext.Msg.alert(
								load.outsidevehiclecharge.i18n('foss.load.assignLoadTask.prompt'),
								load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.enterAuditState'));
			} else {
				if (form.isValid()) {
					var params = {
						vo : {
							adjustOutVehicleFeeEntity : vals
						}
					};
					Ext.Ajax.request({
						url : load.realPath('auditOutsideVehicleCharge.action'),
						jsonData : params,
						success : function(response, opts) {
							form.reset();
							Ext.getCmp('T_load-outsidevehiclechargeindex_content').getAuditInfoWindow().close();
							load.pagingBar.moveFirst();
							Ext.ux.Toast.msg(
											load.outsidevehiclecharge.i18n('foss.load.assignLoadTask.prompt'),
											load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.exception.auditDoNotPass'),
											'ok', 1000);
						},
						failure : function(response, opts) {
							Ext.Msg.alert(load.outsidevehiclecharge.i18n('foss.load.assignLoadTask.prompt'),
											load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.exception.auditFailure'));
						},
						exception : function(response) {
							var json = Ext.decode(response.responseText);
							Ext.Msg.alert(load.outsidevehiclecharge.i18n('foss.load.assignLoadTask.prompt'),json.message);
						}
					});
				}
			}
		}
	}, {
		text : load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.button.pass'),// 通过
		cls : 'yellow_button',
		handler : function() {
			var form = this.up('form').getForm();
			var vals = this.up('form').getForm().getValues();
			var btnPass = this;
			vals.auditState = 'AUDITPASS';
			if (form.isValid()) {
				btnPass.setDisabled(true);
				var paramsDto = {
					vo : {
						adjustOutVehicleFeeDto : vals
					}
				};
				Ext.Ajax.request({
					url : load.realPath('adjustOutVehicleFee.action'),
					jsonData : paramsDto,
					success : function(response, opts) {
						var result = Ext.decode(response.responseText); // 查询的结果
						var num = result.vo.isNum;
						if (num == 1) {
							var params = {
								vo : {
									adjustOutVehicleFeeEntity : vals
								}
							};
							Ext.Ajax.request({
								url : load.realPath('auditOutsideVehicleCharge.action'),
								jsonData : params,
								success : function(response, opts) {
									btnPass.setDisabled(false);
									form.reset();
									Ext.getCmp('T_load-outsidevehiclechargeindex_content').getAuditInfoWindow().close();
									load.pagingBar.moveFirst();
									Ext.ux.Toast.msg(load.outsidevehiclecharge.i18n('foss.load.assignLoadTask.prompt'),
													load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.exception.auditSuccess'),
													'ok', 1000);
								},
								failure : function(response, opts) {
									btnPass.setDisabled(false);
									Ext.Msg.alert(load.outsidevehiclecharge.i18n('foss.load.assignLoadTask.prompt'),
													load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.exception.auditFailure'));
								}
							});
						} else {
							btnPass.setDisabled(false);
							Ext.Msg.alert(load.outsidevehiclecharge.i18n('foss.load.assignLoadTask.prompt'),
											load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.exception.auditDoNotPass'));
						}
					},
					failure : function(response, opts) {
						btnPass.setDisabled(false);
						Ext.Msg.alert(load.outsidevehiclecharge.i18n('foss.load.assignLoadTask.prompt'),
										load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.exception.auditFailure'));
					},
					exception : function(response) {
						btnPass.setDisabled(false);
						var json = Ext.decode(response.responseText);
						Ext.Msg.alert(load.outsidevehiclecharge.i18n('foss.load.assignLoadTask.prompt'),json.message);
					}
				});
			}
		}
	}],
	constructor : function(config) {
		var me = this, 
		cfg = Ext.apply({}, config);
		me.callParent([cfg]);

	}
});

// 奖罚panel
Ext.define('Foss.load.awardForm', {
	extend : 'Ext.form.Panel',
	layout : 'column',
	frame : false,
	defaults : {
		xtype : 'textfield',
		margin : '0 5 5 5',
		anchor : '90%'
	},
	items : [{
		name : 'otherReason',
		fieldLabel : '<span style="color:red">'
				+ load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.rewardRange')
				+ ' </span>',
		labelWidth : 70,
		readOnly : true,
		fieldStyle : 'color:red',
		// fieldStyle:'<span fieldStyle="color:red"></span>',
		columnWidth : .9
	}, {
		name : 'otherReason',
		fieldLabel : '<span style="color:red">'
				+ load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.punishRange')
				+ '</span>',
		labelWidth : 70,
		readOnly : true,
		columnWidth : .9
	}],
	bindData : function(record) {
		me = this;
		me.items.items[0].setValue('('
						+ load.outsidevehiclecharge.awardMinFee
						+ '-'
						+ load.outsidevehiclecharge.awardMaxFee
						+ load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.betweenNum'));
		me.items.items[1].setValue('('
						+ load.outsidevehiclecharge.fineMinFee
						+ '-'
						+ load.outsidevehiclecharge.fineMaxFee
						+ load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.betweenNum'));
		me.items.items[0].setFieldStyle('color:red');
		me.items.items[1].setFieldStyle('color:red');
	},
	constructor : function(config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.callParent([cfg]);

	}
});

// 定义弹出的窗口
Ext.define('Foss.load.addInfoWindows', {
			extend : 'Ext.window.Window',
			width : 550,
			height : 420,
			title : load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.adjustFee'),// 调整费用
			closable : true,
			closeAction : 'hide',
			modal : true,
			outsideVehicleChargeFrom : null,
			getOutsideVehicleChargeFrom : function() {
				if (this.outsideVehicleChargeFrom == null) {
					this.outsideVehicleChargeFrom = Ext.create('Foss.load.addOutsideVehicleChargeFrom');
				}
				return this.outsideVehicleChargeFrom;
			},
			constructor : function(config) {
				var me = this,
				cfg = Ext.apply({}, config);
				me.items = [me.getOutsideVehicleChargeFrom()];
				me.callParent([cfg]);
			}
		});

// 定义弹出的编辑窗口
Ext.define('Foss.load.EditDetailInfoWindows', {
			extend : 'Ext.window.Window',
			closeAction : 'hide',
			width : 550,
			height : 420,
			title : load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.updateAuditFee'),
			closable : true,
			closeAction : 'hide',
			modal : true,
			bodyCls : 'autoHeight',
			resizable : false,
			editOutsideVehicleChargeForm : null,
			getEditOutsideVehicleChargeForm : function() {
				if (this.editOutsideVehicleChargeForm == null) {
					this.editOutsideVehicleChargeForm = Ext.create('Foss.load.EditOutsideVehicleChargeForm');
				}
				return this.editOutsideVehicleChargeForm;
			},
			constructor : function(config) {
				var me = this,
				cfg = Ext.apply({}, config);
				me.items = [me.getEditOutsideVehicleChargeForm()];
				me.callParent([cfg]);
			}
		});

// 定义弹出的编辑窗口
Ext.define('Foss.load.EditDetailInfoTimelinessClauseWindows', {
	extend : 'Ext.window.Window',
	closeAction : 'hide',
	width : 550,
	height : 720,
	// 时效费用调整
	title : load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.updateAuditFeeTimelinessClause'),
	closable : true,
	closeAction : 'hide',
	modal : true,
	bodyCls : 'autoHeight',
	resizable : false,
	editOutsideVehicleChargeTimelinessClauseForm : null,
	getEditOutsideVehicleChargeTimelinessClauseForm : function() {
		if (this.editOutsideVehicleChargeTimelinessClauseForm == null) {
			this.editOutsideVehicleChargeTimelinessClauseForm = Ext.create('Foss.load.editOutsideVehicleChargeTimelinessClauseForm');
		}
		return this.editOutsideVehicleChargeTimelinessClauseForm;
	},
	timelinessClauseGridPanel : null,
	getTimelinessClauseGridPanel : function() {
		if (this.timelinessClauseGridPanel == null) {
			this.timelinessClauseGridPanel = Ext.create('Foss.load.outsidevehiclecharge.timelinessClauseGridPanel');
		}
		return this.timelinessClauseGridPanel;
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.items = [me.getEditOutsideVehicleChargeTimelinessClauseForm(),me.getTimelinessClauseGridPanel()];
		me.callParent([cfg]);
	}
});

// 审核窗口
Ext.define('Foss.load.auditInfoWindow', {
			extend : 'Ext.window.Window',
			closeAction : 'hide',
			title : load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.auditOpinion'),// 审核意见
			closable : true,
			closeAction : 'hide',
			width : 350,
			modal : true,
			bodyCls : 'autoHeight',
			resizable : false,
			auditInfoWindowForm : null,
			getAuditInfoWindowForm : function() {
				if (this.auditInfoWindowForm == null) {
					this.auditInfoWindowForm = Ext.create('Foss.load.auditForm');
				}
				return this.auditInfoWindowForm;
			},
			constructor : function(config) {
				var me = this,
				cfg = Ext.apply({}, config);
				me.items = [me.getAuditInfoWindowForm()];
				me.callParent([cfg]);
			}
		});

// 定义弹出的奖罚窗口
Ext.define('Foss.load.awardInfoWindow', {
			extend : 'Ext.window.Window',
			closeAction : 'hide',
			title : load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.promptInfo'),
			closable : true,
			closeAction : 'hide',
			width : 350,
			height : 150,
			modal : true,
			bodyCls : 'autoHeight',
			resizable : false,
			awardInfoWindowForm : null,
			getAwardInfoWindowForm : function() {
				if (this.awardInfoWindowForm == null) {
					this.awardInfoWindowForm = Ext.create('Foss.load.awardForm');
				}
				return this.awardInfoWindowForm;
			},
			constructor : function(config) {
				var me = this,
				cfg = Ext.apply({}, config);
				me.items = [me.getAwardInfoWindowForm()];
				me.callParent([cfg]);
			}
		});

// ↓↓↓↓↓↓ 269701(lln) 2015-07-09 下午 15:20 ↓↓↓↓↓↓
// 定义弹出的审批窗口
Ext.define('Foss.load.approvalInfoWindows', {
			extend : 'Ext.window.Window',
			closeAction : 'hide',
			width : 550,
			height : 500,
			// 审批意见
			title : load.outsidevehiclecharge.i18n('foss.load.outsidevehiclecharge.label.approvalOpinion'),
			closable : true,
			closeAction : 'hide',
			modal : true,
			bodyCls : 'autoHeight',
			resizable : false,
			approvalOutsideVehicleChargeForm : null,
			getApprovalOutsideVehicleChargeForm : function() {
				if (this.approvalOutsideVehicleChargeForm == null) {
					this.approvalOutsideVehicleChargeForm = Ext.create('Foss.load.ApprovalOutsideVehicleChargeForm');
				}
				return this.approvalOutsideVehicleChargeForm;
			},
			constructor : function(config) {
				var me = this,
				cfg = Ext.apply({}, config);
				me.items = [me.getApprovalOutsideVehicleChargeForm()];
				me.callParent([cfg]);
			}
		});
// ↑↑↑↑↑↑↑ 269701(lln) 2015-07-09 下午 15:20 ↑↑↑↑↑↑↑

// 初始入口函数
Ext.onReady(function() {
	Ext.QuickTips.init();
	var queryForm = Ext.create('Foss.load.outsideVehicleChargeQueryForm');
	load.queryForm = queryForm;
	var outsideVehicleChargeGrid = Ext.create('Foss.load.outsideVehicleChargeGrid');
	var auditGrid = Ext.create('Foss.load.auditGrid');
	// 创建修改日志grid 269701(lln) 2015-07-11
	var outsideVehicleChargeLogGrid = Ext.create('Foss.load.outsideVehicleChargeLogGrid');
	load.outsidevehiclecharge.outsideVehicleChargeLogGrid = outsideVehicleChargeLogGrid;

	Ext.Ajax.request({
		url : load.realPath('queryConfigurationParamsExactByEntity.action'),
		success : function(response, opts) {
			var result = Ext.decode(response.responseText); // 查询的结果
			var configurationParamsList = result.vo.configurationParamsList;
			if (configurationParamsList != null) {
				for (var i = 0; i < configurationParamsList.length; i++) {
					if (configurationParamsList[i].code == 'DEFAULT_AWARD_MAXFEE') {
						load.outsidevehiclecharge.awardMaxFee = Ext.Number.from(configurationParamsList[i].confValue, 0);
					} else if (configurationParamsList[i].code == 'DEFAULT_AWARD_MINFEE') {
						load.outsidevehiclecharge.awardMinFee = Ext.Number.from(configurationParamsList[i].confValue, 0);
					} else if (configurationParamsList[i].code == 'DEFAULT_FINE_MAXFEE') {
						load.outsidevehiclecharge.fineMaxFee = Ext.Number.from(configurationParamsList[i].confValue, 0);
					} else if (configurationParamsList[i].code == 'DEFAULT_FINE_MINFEE') {
						load.outsidevehiclecharge.fineMinFee = Ext.Number.from(configurationParamsList[i].confValue, 0);
					}
				}
			}
		}
	});

	Ext.create('Ext.panel.Panel', {
		id : 'T_load-outsidevehiclechargeindex_content',
		// cls : "panelContentNToolbar",
		// bodyCls : 'panelContent-body',
		cls : 'panelContent',
		bodyCls : 'panelContent-body',
		layout : 'auto',
		addInfoWindow : null,
		editDetailInfoWindow : null,
		editDetailInfoTimelinessClauseWindow : null,
		auditInfoWindow : null,
		awardInfoWindow : null,
		approvalInfoWindow : null,
		getAddInfoWindow : function() {
			if (this.addInfoWindow == null) {
				this.addInfoWindow = Ext.create('Foss.load.addInfoWindows');
			}
			return this.addInfoWindow;
		},
		getEditDetailInfoWindow : function() {
			if (this.editDetailInfoWindow == null) {
				this.editDetailInfoWindow = Ext.create('Foss.load.EditDetailInfoWindows');
			}
			return this.editDetailInfoWindow;
		},
		getEditDetailInfoTimelinessClauseWindow : function() {
			if (this.editDetailInfoTimelinessClauseWindow == null) {
				this.editDetailInfoTimelinessClauseWindow = Ext.create('Foss.load.EditDetailInfoTimelinessClauseWindows');
			}
			return this.editDetailInfoTimelinessClauseWindow;
		},
		getAuditInfoWindow : function() {
			if (this.auditInfoWindow == null) {
				this.auditInfoWindow = Ext.create('Foss.load.auditInfoWindow');
			}
			return this.auditInfoWindow;
		},
		getAwardInfoWindow : function() {
			if (this.awardInfoWindow == null) {
				this.awardInfoWindow = Ext.create('Foss.load.awardInfoWindow');
			}
			return this.awardInfoWindow;
		},
		getResultGrid : function() {
			return this.outsideVehicleChargeGrid;
		},
		getAuditGrid : function() {
			return this.outsideVehicleChargeGrid;
		},
		// ↓↓↓↓↓↓ 269701(lln) 2015-07-09 下午 14:20 ↓↓↓↓↓↓
		getApprovalInfoWindow : function() {
			if (this.approvalInfoWindow == null) {
				this.approvalInfoWindow = Ext.create('Foss.load.approvalInfoWindows');
			}
			return this.approvalInfoWindow;
		},
		// ↑↑↑↑↑↑↑ 269701(lln) 2015-07-11 下午 14:20 ↑↑↑↑↑↑↑
		margin : '0 0 0 0',
		items : [queryForm, outsideVehicleChargeGrid, auditGrid,outsideVehicleChargeLogGrid],
		renderTo : 'T_load-outsidevehiclechargeindex-body'
	});
	// 查询修改记录
	//load.outsidevehiclecharge.loadUpdateLog();
});