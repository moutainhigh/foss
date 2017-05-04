/**
 * 审核状态
 */
Ext.define('Foss.PCpackageAssistPriceManager.AuditStatusStore',{
	extend:'Ext.data.Store',
	fields:['name','value'],
	data:[
		{name:packaging.PCpackageAssistPriceManager.i18n('foss.packaging.querypacked.pdaPackageGrid.auditStatus.waitingaudit'),value:'WAITINGAUDIT'},
		{name:packaging.PCpackageAssistPriceManager.i18n('foss.packaging.querypacked.pdaPackageGrid.auditStatus.hasudited'),value:'HASAUDITED'},
		{name:packaging.PCpackageAssistPriceManager.i18n('foss.packaging.queryUnpack.queryUnpackForm.all'),value:''}
	]
}); 
 

/**
 * 查询PC端辅助包装金额表单
 */
Ext.define('Foss.PCpackageAssistPriceManager.assistPackageQueryForm', {
	extend : 'Ext.form.Panel',
	title : packaging.PCpackageAssistPriceManager
			.i18n('foss.packaging.PCpackageAssistPriceManager.assistPackageQueryForm.title'),// '查询PC端辅助包装金额信息',
	frame : true,
	collapsible : true,
	animCollapse : true,
	defaults : {
		margin : '5 10 5 10',
		// anchor: '90%',
		labelWidth : 85
	},
	defaultType : 'textfield',
	layout : 'column',
	items : [{
		name : 'waybillNo',
		fieldLabel : packaging.PCpackageAssistPriceManager
				.i18n('foss.packaging.PCpackageAssistPriceManager.assistPackageQueryForm.waybillNo'),// '运单号',
		type : 'ORG',
		salesDepartment : 'Y',
		columnWidth : .30,
		maxLength : 20
	}, {
		name : 'packedDept',
		fieldLabel : packaging.PCpackageAssistPriceManager
				.i18n('foss.packaging.PCpackageAssistPriceManager.assistPackageQueryForm.packedDept'),// '包装部门',
		// id:'Foss_PCpackageAssistPriceManagerindex_assistPackageQueryForm_packedDept_ID',
		allowBlank : false,
		readOnly : true,
		columnWidth : .3
	}, {	// 包装部门编码
				name : 'packDeptCode',
				hidden : true,
				columnWidth : .3
			}, {
				xtype : 'dynamicorgcombselector',
				name : 'waybillCreateDept',
				fieldLabel : packaging.PCpackageAssistPriceManager
						.i18n('foss.packaging.PCpackageAssistPriceManager.assistPackageQueryForm.waybillCreateDept'),// '开单部门',
				type : 'ORG',
				salesDepartment : 'Y',
				columnWidth : .3
			}, {  
				xtype : 'dynamicPackagingSupplierSelector',// 找综合要包装供应商选择器
				name : 'packageSupplierCode',
				fieldLabel : packaging.PCpackageAssistPriceManager
						.i18n('foss.packaging.PCpackageAssistPriceManager.assistPackageQueryForm.packageSupplierCode'),// '包装供应商',
				// vtype: 'waybill',
				//orgCodeCode : FossUserContext.getCurrentDeptCode(),
				columnWidth : .3
				/*listeners : {
					beforequery : function(queryEvent, eOpts) {
						Ext.apply(queryEvent.combo, {
									orgCodeCode : packaging.PCpackageAssistPriceManager.getOutfiled()
								});
					}
				}*/
			}, {
				xtype : 'rangeDateField',
				dateType : 'datetimefield_date97',
				fromName : 'packedBeginDate',
				fromValue : Ext.Date.format(new Date(new Date().getFullYear(),
								new Date().getMonth(), new Date().getDate()
										- 14, 00, 00, 00), 'Y-m-d H:i:s'),
				toName : 'packedEndDate',
				toValue : Ext.Date.format(
						new Date(new Date().getFullYear(), new Date()
										.getMonth(), new Date().getDate(), 23,
								59, 59), 'Y-m-d H:i:s'),
				fieldLabel : packaging.PCpackageAssistPriceManager
						.i18n('foss.packaging.PCpackageAssistPriceManager.assistPackageQueryForm.packedEndDate'),// '包装时间',
				disallowBlank : true,
				fieldId : 'Foss_PCpackageAssistPriceManagerindex_assistPackageQueryForm_packDate_ID',
				columnWidth : .5
			}, {
				xtype : 'combobox',
				name : 'auditStatus',
				fieldLabel : packaging.PCpackageAssistPriceManager
						.i18n('foss.packaging.querypacked.pdaPackageGrid.auditStatus'),// '开单部门',
				store:Ext.create('Foss.PCpackageAssistPriceManager.AuditStatusStore'),
				queryMode:'local',
				displayField: 'name',
    			valueField: 'value',
				columnWidth : .3,
				value:''
			}, {
				border : false,
				xtype : 'container',
				columnWidth : 0.9,
				layout : 'column',
				defaults : {
					margin : '5 0 5 0'
				},
				items : [{
					xtype : 'button',
					columnWidth : .08,
					text : packaging.PCpackageAssistPriceManager
							.i18n('foss.packaging.PCpackageAssistPriceManager.assistPackageQueryForm.resetButton'),// '重置',
					handler : function() {
						var form = this.up('form').getForm();
						form.findField('packedBeginDate').setValue(Ext.Date
								.format(new Date(new Date().getFullYear(),
												new Date().getMonth(),
												new Date().getDate() - 14, 0,
												0, 0), 'Y-m-d H:i:s'));
						form.findField('packedEndDate').setValue(Ext.Date
								.format(new Date(new Date().getFullYear(),
												new Date().getMonth(),
												new Date().getDate(), 23, 59,
												59), 'Y-m-d H:i:s'));
						form.findField('waybillNo').setValue(null);
						form.findField('waybillCreateDept').setValue(null);
						form.findField('packageSupplierCode').setValue(null);
					}
				}, {
					xtype : 'container',
					columnWidth : .8,
					html : '&nbsp;'
				}, {
					columnWidth : .08,
					xtype : 'button',
					cls : 'yellow_button',
					text : packaging.PCpackageAssistPriceManager
							.i18n('foss.packaging.PCpackageAssistPriceManager.assistPackageQueryForm.queryButton'),// '查询',
							disabled : (packaging.PCpackageAssistPriceManager
									.isPermission('packaging/PCpackageAssistPriceManagerAllButton'))
									? false
									: true,
					hidden : (packaging.PCpackageAssistPriceManager
							.isPermission('packaging/PCpackageAssistPriceManagerAllButton'))
							? false
							: true,
					handler : function() {

						if (packaging.PCpackageAssistPriceManager
								.isPermission('packaging/PCpackageAssistPriceManagerAllButton')) {
							var beginDate = packaging.assistPackageQueryForm
									.getValues().packedBeginDate;
							var endDate = packaging.assistPackageQueryForm
									.getValues().packedEndDate;
							var difDate = Ext.Date
									.parse(endDate, 'Y-m-d H:i:s')
									- Ext.Date.parse(beginDate, 'Y-m-d H:i:s');
							var difTime = 15 * 24 * 60 * 60 * 1000
									- parseInt(difDate);
							// 查询条件是否合法（非空等相关约束）
							if (packaging.assistPackageQueryForm.getForm()
									.isValid()) {
								if (difTime >= 0) {
									// 自定义分页
									packaging.PCpackageAssistPriceManager.pagingBar
											.moveFirst();
								} else {
									// 警告，时间跨度不能超过15天！
									Ext.Msg
											.alert(
													packaging.PCpackageAssistPriceManager
															.i18n('foss.packaging.querypacked.warning'),
													packaging.PCpackageAssistPriceManager
															.i18n('foss.packaging.PCpackageAssistPriceManager.assistPackageQueryForm.timeHint'));
								}
							} else {
								// 警告，请输入查询条件！
								Ext.Msg
										.alert(
												packaging.PCpackageAssistPriceManager
														.i18n('foss.packaging.querypacked.warning'),
												packaging.PCpackageAssistPriceManager
														.i18n('foss.packaging.PCpackageAssistPriceManager.assistPackageQueryForm.queryHint'));
							}

						}

					}
				}]
			}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

/**
 * 按钮面板
 */
Ext.define('Foss.PCpackageAssistPriceManager.buttonPanel', {
	extend : 'Ext.container.Container',
	width : 1080,
	layout : 'column',
	items : [{
				xtype : 'container',
				margin : '5 5 5 10',
				html : '&nbsp;'
			}, {
				xtype : 'button',
				columnWidth : .1,
				text : packaging.PCpackageAssistPriceManager
						.i18n('foss.packaging.button.addnew'),// 新增
				disabled : (packaging.PCpackageAssistPriceManager
						.isPermission('packaging/addnewPackedButton'))
						? false
						: true,
				hidden : (packaging.PCpackageAssistPriceManager
						.isPermission('packaging/addnewPackedButton'))
						? false
						: true,
				handler : function() {
					var assistPackageAddNewForm = Ext
							.create('Foss.PCpackageAssistPriceManager.assistPackageAddNewForm');
					packaging.PCpackageAssistPriceManager.assistPackageAddNewForm = assistPackageAddNewForm;
					packaging.PCpackageAssistPriceManager.createWindow = 1;
					Ext.create('Ext.window.Window', {
						autoDestroy : true,
						closable : true,
						closeAction : 'hide',
						draggable : true,
						title : packaging.PCpackageAssistPriceManager
								.i18n('foss.packaging.PCpackageAssistPriceManager.assistPackageAddNewForm.window.title'),// '录入包装金额信息',
						cls : 'specialWin',
						height : 400,
						width : 800,
						modal : true,
						layout : 'fit',
						items : [assistPackageAddNewForm],
						listeners : {
							hide : function(field, e) {
								packaging.PCpackageAssistPriceManager.oldValue = '';
								packaging.PCpackageAssistPriceManager.createWindow = 0;
								var data = packaging.PCpackageAssistPriceManager.assistPackageAddData;
								if (data != null) {
									var totalCount = packaging.PCpackageAssistPriceManager.pagingBar.store.totalCount;
									var currentPage = packaging.PCpackageAssistPriceManager.pagingBar.store.currentPage;
									var pageSize = packaging.PCpackageAssistPriceManager.pagingBar.store.__proto__.pageSize;
									// 总数+1
									// packaging.PCpackageAssistPriceManager.pagingBar.store.totalCount
									// = totalCount + 1;
									var store = packaging.PCpackageAssistPriceManager.PackageGrid.store;
									var beSpecify = true;
									var queryParams = packaging.assistPackageQueryForm
											.getValues();
									if (queryParams.waybillNo != null
											&& queryParams.waybillNo != ""
											&& queryParams.waybillNo != data.waybillNo) {
										beSpecify = false;
									}
									if (queryParams.packDeptCode != null
											&& queryParams.packDeptCode != ""
											&& queryParams.packDeptCode != data.packageOrgCode) {
										beSpecify = false;
									}
									if (queryParams.waybillCreateDept != null
											&& queryParams.waybillCreateDept != ""
											&& queryParams.waybillCreateDept != data.billOrgCode) {
										beSpecify = false;
									}
									if (queryParams.packageSupplierCode != null
											&& queryParams.packageSupplierCode != null
											&& queryParams.packageSupplierCode != data.packageSupplierCode) {
										beSpecify = false;
									}
									if (queryParams.packedBeginDate != null
											&& queryParams.packedBeginDate > data.createTime) {
										beSpecify = false;
									}
									if (queryParams.packedEndDate != null
											&& queryParams.packedEndDate < data.createTime) {
										beSpecify = false;
									}
									if (beSpecify) {
										// 把新增数据加到第一行
										store.insert(0, data);
									}
								}
								packaging.PCpackageAssistPriceManager.assistPackageAddData = null;
							},
							close : function(field, e) {
								packaging.PCpackageAssistPriceManager.oldValue = '';
								packaging.PCpackageAssistPriceManager.createWindow = 0;
							}
						}

					}).show()
				}

			}, {
				columnWidth : .1,
				xtype : 'button',
				cls : 'yellow_button',
				text : packaging.PCpackageAssistPriceManager
						.i18n('foss.packaging.button.export'),// '导出',
				hidden : (packaging.PCpackageAssistPriceManager
						.isPermission('packaging/PCpackageAssistPriceManagerexportButton'))
						? false
						: true,
						disabled : (packaging.PCpackageAssistPriceManager
								.isPermission('packaging/PCpackageAssistPriceManagerexportButton'))
								? false
								: true,
				handler : function() {
					if (packaging.PCpackageAssistPriceManager
							.isPermission('packaging/PCpackageAssistPriceManagerexportButton')) {
						var beginDate = packaging.assistPackageQueryForm
								.getValues().packedBeginDate;
						var endDate = packaging.assistPackageQueryForm
								.getValues().packedEndDate;
						var difDate = Ext.Date.parse(endDate, 'Y-m-d H:i:s')
								- Ext.Date.parse(beginDate, 'Y-m-d H:i:s');
						var difTime = 15 * 24 * 60 * 60 * 1000
								- parseInt(difDate);
						// 查询条件是否合法（非空等相关约束）
						if (packaging.assistPackageQueryForm.getForm()
								.isValid()) {
							if (difTime >= 0) {

								packaging.PCpackageAssistPriceManager
										.exportExcelPacked();

							} else {
								// 警告，时间跨度不能超过15天！
								Ext.Msg
										.alert(
												packaging.PCpackageAssistPriceManager
														.i18n('foss.packaging.querypacked.warning'),
												packaging.PCpackageAssistPriceManager
														.i18n('foss.packaging.PCpackageAssistPriceManager.assistPackageQueryForm.timeHint'));
							}
						} else {
							// 警告，请输入查询条件！
							Ext.Msg
									.alert(
											packaging.PCpackageAssistPriceManager
													.i18n('foss.packaging.querypacked.warning'),
											packaging.PCpackageAssistPriceManager
													.i18n('foss.packaging.PCpackageAssistPriceManager.assistPackageQueryForm.queryHint'));
						}

					}
				}
			}]
});

/*******************************************************************************
 * 定义录入包装金额页面
 * 
 ******************************************************************************/
Ext.define('Foss.PCpackageAssistPriceManager.assistPackageAddNewForm', {
	extend : 'Ext.form.Panel',
	frame : true,
	collapsible : true,
	animCollapse : true,
	defaults : {
		margin : '5 10 5 10',
		anchor : '90%',
		labelWidth : 70
	},
	defaultType : 'textfield',
	layout : 'column',
	items : [{
		name : 'waybillNo_add',
		allowBlank : false,
		fieldLabel : packaging.PCpackageAssistPriceManager
				.i18n('foss.packaging.PCpackageAssistPriceManager.packageGrid.waybillNo'),// '运单号',
		columnWidth : .40,
		maxLength : 20
	}, {
		xtype : 'dynamicPackagingSupplierSelector',
		name : 'packageSupplierCode_add',
		displayField : 'packagingSupplier',// 显示名称
		valueField : 'packagingSupplierCode',// 值
		allowBlank : false,
		//orgCodeCode : FossUserContext.getCurrentDeptCode(),
		fieldLabel : packaging.PCpackageAssistPriceManager
				.i18n('foss.packaging.PCpackageAssistPriceManager.assistPackageQueryForm.packageSupplierCode'),// '包装供应商',
		columnWidth : .40,
		listeners : {
	
			/*beforequery : function(queryEvent, eOpts) {
				Ext.apply(queryEvent.combo, {
							//orgCodeCode : FossUserContext.getCurrentDeptCode()
						});
			},*/
				
			select : function(combo, record, index) {

				var assistPackageAddNewForm = packaging.PCpackageAssistPriceManager.assistPackageAddNewForm
						.getForm();
				var supplierCode = record[0].data.packagingSupplierCode;
				var waybillNo = assistPackageAddNewForm
						.findField('waybillNo_add').getValue();
				if (waybillNo == null && waybillNo == '') {
					Ext.Msg.alert("请录入运单号");
					return;
				}
				params = {
					'queryPackedPriceVo.queryAssistPackedDto.waybillNo' : waybillNo,
					'queryPackedPriceVo.queryAssistPackedDto.packageSupplierCode' : supplierCode

				}
				Ext.Ajax.request({
					url : packaging
							.realPath('queryPackageMainPriceByWaybillNoAndpackageSupplierCode.action'),
					params : params,
					// 获取当前登录人的所在部门
					success : function(response) {
						// 回复可编辑
						assistPackageAddNewForm
								.findField('actualFrameVolume_add')
								.setReadOnly(false);
						assistPackageAddNewForm
								.findField('actualWoodenVolume_add')
								.setReadOnly(false);
					},
					exception : function(response) {
						var result = Ext.decode(response.responseText);
						if (result.message == 'isNotExist') {
							Ext.Msg.alert(packaging.PCpackageAssistPriceManager
											.i18n('foss.packaging.error'),
									'运单号不存在');
							return;
						}
						// 打木架、打木箱体积不可编辑
						assistPackageAddNewForm
								.findField('actualFrameVolume_add')
								.setReadOnly(true);
						assistPackageAddNewForm
								.findField('actualWoodenVolume_add')
								.setReadOnly(true);
						var result = Ext.decode(response.responseText);
						Ext.Msg.alert(packaging.PCpackageAssistPriceManager
										.i18n('foss.packaging.error'),
								result.message);
					}
				});

			}
			/*
			 * change : function(ths, newValue, oldValue, eOpts) { var
			 * setComboxValues =
			 * airfreight.airEnteringFlightBill.flightinformationForm.getForm();
			 * if (Ext.isEmpty(ths.getRawValue())) { var findField =
			 * function(name) { return setComboxValues.findField(name); };
			 * ths.setValue(null); findField('flightNo').airLines = null;
			 * findField('flightNo').store.load(); } }
			 */
		}
	}, {
		xtype : 'numberfield',
		name : 'actualFrameVolume_add',
		fieldLabel : packaging.PCpackageAssistPriceManager
				.i18n('foss.packaging.PCpackageAssistPriceManager.packageGrid.actualFrameVolume'),// '实际打木架体积(方)',
		columnWidth : .40,
		allowBlank : false,
		maxValue : 99999999,
		maxLength : 8,
		value : 0,
		allowDecimals : true,
		decimalPrecision : 2
	}, {
		xtype : 'numberfield',
		name : 'actualWoodenVolume_add',
		fieldLabel : packaging.PCpackageAssistPriceManager
				.i18n('foss.packaging.PCpackageAssistPriceManager.packageGrid.actualWoodenVolume'),// '实际打木箱体积(方)',
		columnWidth : .40,
		maxLength : 8,
		value : 0,
		allowBlank : false,
		allowDecimals : true,
		decimalPrecision : 2,
		maxValue : 99999999

	}, {
		xtype : 'numberfield',
		name : 'actualMaskNumber_add',
		fieldLabel : packaging.PCpackageAssistPriceManager
				.i18n('foss.packaging.PCpackageAssistPriceManager.packageGrid.actualMaskNumber'),// '实际打木托个数',
		columnWidth : .40,
		value : 0,
		allowBlank : false,
		allowDecimals : false,
		maxLength : 7
	}, {
		xtype : 'numberfield',
		name : 'woodenBarLong_add',
		fieldLabel : packaging.PCpackageAssistPriceManager
				.i18n('foss.packaging.PCpackageAssistPriceManager.packageGrid.woodenBarLong'),// '加木条长度(米)',
		columnWidth : .40,
		maxLength : 8,
		value : 0,
		allowBlank : false,
		allowDecimals : true,
		decimalPrecision : 2,
		maxValue : 99999999
	}, {
		xtype : 'numberfield',
		name : 'bubbVelamenVolume_add',
		fieldLabel : packaging.PCpackageAssistPriceManager
				.i18n('foss.packaging.PCpackageAssistPriceManager.packageGrid.bubbVelamenVolume'),// '气泡膜体积(方)',
		value : 0,
		columnWidth : .40,
		maxLength : 8,
		allowBlank : false,
		allowDecimals : true,
		decimalPrecision : 2,
		maxValue : 99999999
	}, {
		xtype : 'numberfield',
		name : 'bindVelamenVolume_add',
		fieldLabel : packaging.PCpackageAssistPriceManager
				.i18n('foss.packaging.PCpackageAssistPriceManager.packageGrid.bindVelamenVolume'),// '/缠绕膜体积(方)',
		columnWidth : .40,
		value : 0,
		maxLength : 8,
		allowBlank : false,
		allowDecimals : true,
		decimalPrecision : 2,
		maxValue : 99999999
	}, {
		xtype : 'numberfield',
		name : 'bagBeltNum_add',
		fieldLabel : packaging.PCpackageAssistPriceManager
				.i18n('foss.packaging.PCpackageAssistPriceManager.packageGrid.bagBeltNum'),// '/打包带根数',
		columnWidth : .40,
		maxLength : 8,
		allowBlank : false,
		allowDecimals : false,
		value : 0,
		maxValue : 99999999
	}, {
		border : false,
		xtype : 'container',
		columnWidth : 0.9,
		layout : 'column',
		defaults : {
			margin : '5 0 5 0'
		},
		items : [{
			xtype : 'button',
			// id:'Foss_packaging_PCpackageAssistPriceManager_modifyButton_ID',
			columnWidth : .08,
			text : packaging.PCpackageAssistPriceManager
					.i18n('foss.packaging.PCpackageAssistPriceManager.assistPackageQueryForm.resetButton'),// '重置',
			handler : function() {
				var form = this.up('form').getForm();
				form.findField('packageSupplierCode_add').setValue(null);
				form.findField('actualFrameVolume_add').setValue(null);
				form.findField('actualWoodenVolume_add').setValue(null);
				form.findField('actualMaskNumber_add').setValue(null);
				form.findField('woodenBarLong_add').setValue(null);
				form.findField('bubbVelamenVolume_add').setValue(null);
				form.findField('bindVelamenVolume_add').setValue(null);
				form.findField('bagBeltNum_add').setValue(null);

			}
		}, {
			border : false,
			columnWidth : .84,
			html : '&nbsp;'
		}, {
			columnWidth : .08,
			// id:'Foss_packaging_PCpackageAssistPriceManager_addButton_ID',
			xtype : 'button',
			cls : 'yellow_button',
			text : packaging.PCpackageAssistPriceManager
					.i18n('foss.packaging.PCpackageAssistPriceManager.assistPackageQueryForm.submit'),// '提交',
			handler : function() {
				var form = packaging.PCpackageAssistPriceManager.assistPackageAddNewForm
						.getForm();
				var waybillNo = form.findField('waybillNo_add').getValue();
				if (waybillNo == '') {
					Ext.Msg.alert(packaging.PCpackageAssistPriceManager
									.i18n('foss.packaging.error'), '运单号不能为空');
				}
				var packageSupplierCode_add = form
						.findField('packageSupplierCode_add').getValue();
				if (packageSupplierCode_add == '') {
					Ext.Msg.alert(packaging.PCpackageAssistPriceManager
									.i18n('foss.packaging.error'), '包装供应商不能为空');
				}
				// 木架体积
				var actualFrameVolume_add = form
						.findField('actualFrameVolume_add').getValue();

				// 木箱
				var actualWoodenVolume_add = form
						.findField('actualWoodenVolume_add').getValue();

				// 木托
				var actualMaskNumber_add = form
						.findField('actualMaskNumber_add').getValue();

				// 木条
				var woodenBarLong_add = form.findField('woodenBarLong_add')
						.getValue();
				// 气泡膜
				var bubbVelamenVolume_add = form
						.findField('bubbVelamenVolume_add').getValue();

				// 缠绕膜
				var bindVelamenVolume_add = form
						.findField('bindVelamenVolume_add').getValue();

				// 包带
				var bagBeltNum_add = form.findField('bagBeltNum_add')
						.getValue();

				if (actualFrameVolume_add == 0 && actualWoodenVolume_add == 0
						&& actualMaskNumber_add == 0
						&& bubbVelamenVolume_add == 0 && woodenBarLong_add == 0
						&& bindVelamenVolume_add == 0 && bagBeltNum_add == 0) {
					Ext.Msg.alert(packaging.PCpackageAssistPriceManager
									.i18n('foss.packaging.error'), '运单:{'
									+ waybillNo + '} 所有的录入项不能同时为0！');
					return;
				}
				params = {
					'queryPackedPriceVo.packageAssistPriceEntity.waybillNo' : waybillNo,
					'queryPackedPriceVo.packageAssistPriceEntity.packageSupplierCode' : packageSupplierCode_add,
					'queryPackedPriceVo.packageAssistPriceEntity.actualFrameVolume' : actualFrameVolume_add,
					'queryPackedPriceVo.packageAssistPriceEntity.actualWoodenVolume' : actualWoodenVolume_add,
					'queryPackedPriceVo.packageAssistPriceEntity.actualMaskNumber' : actualMaskNumber_add,
					'queryPackedPriceVo.packageAssistPriceEntity.woodenBarLong' : woodenBarLong_add,
					'queryPackedPriceVo.packageAssistPriceEntity.bubbVelamenVolume' : bubbVelamenVolume_add,
					'queryPackedPriceVo.packageAssistPriceEntity.bindVelamenVolume' : bindVelamenVolume_add,
					'queryPackedPriceVo.packageAssistPriceEntity.bagBeltNum' : bagBeltNum_add
				}

				/*
				 * var myMask = new
				 * Ext.LoadMask(packaging.PCpackageAssistPriceManager.assistPackageAddNewForm,
				 * {msg:"加载中，请稍候..."}); myMask.show();
				 */
				// packaging.PCpackageAssistPriceManager.assistPackageAddNewForm.up('window').hide();
				if (form.isValid()) {

					Ext.Ajax.request({
						url : packaging
								.realPath('addPackageAssistPrice.action'),
						params : params,
						// 获取当前登录人的所在部门
						success : function(response) {
							Ext.ux.Toast
									.msg(
											packaging.PCpackageAssistPriceManager
													.i18n('foss.packaging.hint'),
											packaging.PCpackageAssistPriceManager
													.i18n('foss.packaging.PCpackageAssistPriceManager.addSuccess'));
							var result = Ext.decode(response.responseText);
							packaging.PCpackageAssistPriceManager.assistPackageAddData = result.queryPackedPriceVo.packageAssistPriceEntity;
							var buttonPanel = packaging.PCpackageAssistPriceManager.assistPackageAddNewForm;
							var resetButton = buttonPanel.items.items[9].items.items[0];
							var submitButton = buttonPanel.items.items[9].items.items[2];
							// 隐藏“重置”、“保存”按钮
							resetButton.setVisible(false);
							submitButton.setVisible(false);
							// packaging.PCpackageAssistPriceManager.assistPackageAddNewForm.up('window').hide();
							// myMask.hide();
						},
						exception : function(response) {
							var result = Ext.decode(response.responseText);
							Ext.Msg.alert(packaging.PCpackageAssistPriceManager
											.i18n('foss.packaging.error'),
									result.message);
							/*
							 * packaging.PCpackageAssistPriceManager.assistPackageAddNewForm.up('window').hide();
							 * myMask.hide();
							 */
							return;
						}
					});
				}
			}
		}]
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

/**
 * modifyPanel重置事件
 * 
 */
packaging.PCpackageAssistPriceManager.modifyPanelReset = function(id) {
	var windowMask = new Ext.LoadMask(
			packaging.PCpackageAssistPriceManager.assistPackageModifyWindow, {
				msg : '处理中,请稍候...'
			});
	windowMask.show();
	Ext.Ajax.request({
		url : packaging.realPath('queryPackedAssistPriceById.action'),
		params : {
			'queryPackedPriceVo.id' : id
		},
		success : function(response) {
			var result = Ext.decode(response.responseText), packageAssistPrice = result.queryPackedPriceVo.packageAssistPriceEntity;
			var assistPackageModifyForm = packaging.PCpackageAssistPriceManager.assistPackageModifyForm
					.getForm();
			// id
			assistPackageModifyForm.findField('id_modify')
					.setValue(packageAssistPrice.id);
			// 修改时间
			assistPackageModifyForm.findField('modify_time_modify')
					.setValue(packageAssistPrice.modifyTime);
			// 运单号
			assistPackageModifyForm.findField('waybillNo_modify')
					.setValue(packageAssistPrice.waybillNo);
			// 包装供应商
			assistPackageModifyForm.findField('packageSupplierCode_modify')
					.setValue(packageAssistPrice.packageSupplierCode);
			// 实际打木架
			assistPackageModifyForm.findField('actualFrameVolume_modify')
					.setValue(packageAssistPrice.actualFrameVolume);
			// 实际打木箱
			assistPackageModifyForm.findField('actualWoodenVolume_modify')
					.setValue(packageAssistPrice.actualWoodenVolume);
			// 实际打木托
			assistPackageModifyForm.findField('actualMaskNumber_modify')
					.setValue(packageAssistPrice.actualMaskNumber);
			// 木条长度
			assistPackageModifyForm.findField('woodenBarLong_modify')
					.setValue(packageAssistPrice.woodenBarLong);
			// 气泡膜体积
			assistPackageModifyForm.findField('bubbVelamenVolume_modify')
					.setValue(packageAssistPrice.bubbVelamenVolume);
			// 缠绕膜体积
			assistPackageModifyForm.findField('bindVelamenVolume_modify')
					.setValue(packageAssistPrice.bindVelamenVolume);
			// 包带根数
			assistPackageModifyForm.findField('bagBeltNum_modify')
					.setValue(packageAssistPrice.bagBeltNum);
			// 更新model
			packaging.PCpackageAssistPriceManager
					.assistPackageModifyModelReset();
			windowMask.hide();
		},
		exception : function(response) {
			var result = Ext.decode(response.responseText);
			Ext.ux.Toast.msg('提示', result.message, 'error', 3000);
			windowMask.hide();
		}
	});
	windowMask.hide();
}
Ext.define('Foss.PCpackageAssistPriceManager.assistPackageModifyModel', {
			extend : 'Ext.data.Model',
			fields : [{
						name : ' actualFrameVolume_modify ',
						type : 'string'
					}, {
						name : ' actualWoodenVolume_modify ',
						type : 'string'
					}, {
						name : ' actualMaskNumber_modify ',
						type : 'string'
					}, {
						name : ' woodenBarLong_modify ',
						type : 'string'
					}, {
						name : ' bubbVelamenVolume_modify ',
						type : 'string'
					}, {
						name : ' bindVelamenVolume_modify ',
						type : 'string'
					}, {
						name : ' bagBeltNum_modify ',
						type : 'string'
					}]
		});
packaging.PCpackageAssistPriceManager.assistPackageModifyModel = Ext
		.create('Foss.PCpackageAssistPriceManager.assistPackageModifyModel');
packaging.PCpackageAssistPriceManager.assistPackageModifyModelReset = function() {
	var form = packaging.PCpackageAssistPriceManager.assistPackageModifyForm
			.getForm();
	packaging.PCpackageAssistPriceManager.assistPackageModifyModel.set(
			'actualFrameVolume_modify', form
					.findField('actualFrameVolume_modify').getValue());
	packaging.PCpackageAssistPriceManager.assistPackageModifyModel.set(
			'actualWoodenVolume_modify', form
					.findField('actualWoodenVolume_modify').getValue());
	packaging.PCpackageAssistPriceManager.assistPackageModifyModel.set(
			'actualMaskNumber_modify', form
					.findField('actualMaskNumber_modify').getValue());
	packaging.PCpackageAssistPriceManager.assistPackageModifyModel.set(
			'woodenBarLong_modify', form.findField('woodenBarLong_modify')
					.getValue());
	packaging.PCpackageAssistPriceManager.assistPackageModifyModel.set(
			'bubbVelamenVolume_modify', form
					.findField('bubbVelamenVolume_modify').getValue());
	packaging.PCpackageAssistPriceManager.assistPackageModifyModel.set(
			'bindVelamenVolume_modify', form
					.findField('bindVelamenVolume_modify').getValue());
	packaging.PCpackageAssistPriceManager.assistPackageModifyModel
			.set('bagBeltNum_modify', form.findField('bagBeltNum_modify')
							.getValue());
};
/*******************************************************************************
 * 定义包装金额修改页面
 * 
 ******************************************************************************/
Ext.define('Foss.PCpackageAssistPriceManager.assistPackageModifyForm', {
	extend : 'Ext.form.Panel',
	frame : true,
	collapsible : true,
	animCollapse : true,
	defaults : {
		margin : '5 10 5 10',
		anchor : '90%',
		labelWidth : 70
	},
	defaultType : 'textfield',
	layout : 'column',
	items : [{
				name : 'id_modify',
				readOnly : true,
				hidden : true,
				fieldLabel : 'id'
			}, {
				name : 'modify_time_modify',
				readOnly : true,
				// xtype: 'datefield',
				// format : 'Y-m-d H:i:s:u',
				hidden : true,
				// columnWidth:.40,
				// maxLength : 20,
				fieldLabel : '更新时间'
			}, {
				name : 'waybillNo_modify',
				readOnly : true,
				allowBlank : false,
				fieldLabel : packaging.PCpackageAssistPriceManager
						.i18n('foss.packaging.PCpackageAssistPriceManager.packageGrid.waybillNo'),// '运单号',
				columnWidth : .40,
				maxLength : 20
			}, {
				name : 'packageSupplierCode_modify',
				allowBlank : false,
				readOnly : true,
				fieldLabel : packaging.PCpackageAssistPriceManager
						.i18n('foss.packaging.PCpackageAssistPriceManager.assistPackageQueryForm.packageSupplierCode'),// '包装供应商',
				columnWidth : .40
			}, {
				xtype : 'numberfield',
				name : 'actualFrameVolume_modify',
				fieldLabel : packaging.PCpackageAssistPriceManager
						.i18n('foss.packaging.PCpackageAssistPriceManager.packageGrid.actualFrameVolume'),// '实际打木架体积(方)',
				columnWidth : .40,
				maxValue : 99999999,
				maxLength : 9,
				allowDecimals : true,
				allowBlank : false,
				decimalPrecision : 2
			}, {
				xtype : 'numberfield',
				name : 'actualWoodenVolume_modify',
				fieldLabel : packaging.PCpackageAssistPriceManager
						.i18n('foss.packaging.PCpackageAssistPriceManager.packageGrid.actualWoodenVolume'),// '实际打木箱体积(方)',
				columnWidth : .40,
				maxLength : 8,
				allowDecimals : true,
				allowBlank : false,
				decimalPrecision : 2,
				maxValue : 99999999

			}, {
				xtype : 'numberfield',
				name : 'actualMaskNumber_modify',
				fieldLabel : packaging.PCpackageAssistPriceManager
						.i18n('foss.packaging.PCpackageAssistPriceManager.packageGrid.actualMaskNumber'),// '实际打木托个数',
				columnWidth : .40,
				allowDecimals : false,
				allowBlank : false,
				maxLength : 8
			}, {
				xtype : 'numberfield',
				name : 'woodenBarLong_modify',
				fieldLabel : packaging.PCpackageAssistPriceManager
						.i18n('foss.packaging.PCpackageAssistPriceManager.packageGrid.woodenBarLong'),// '加木条长度(米)',
				columnWidth : .40,
				maxLength : 8,
				allowDecimals : true,
				allowBlank : false,
				decimalPrecision : 2,
				maxValue : 99999999
			}, {
				xtype : 'numberfield',
				name : 'bubbVelamenVolume_modify',
				fieldLabel : packaging.PCpackageAssistPriceManager
						.i18n('foss.packaging.PCpackageAssistPriceManager.packageGrid.bubbVelamenVolume'),// '气泡膜体积(方)',
				columnWidth : .40,
				maxLength : 8,
				allowDecimals : true,
				allowBlank : false,
				decimalPrecision : 2,
				maxValue : 99999999
			}, {
				xtype : 'numberfield',
				name : 'bindVelamenVolume_modify',
				fieldLabel : packaging.PCpackageAssistPriceManager
						.i18n('foss.packaging.PCpackageAssistPriceManager.packageGrid.bindVelamenVolume'),// '/缠绕膜体积(方)',
				columnWidth : .40,
				maxLength : 8,
				allowDecimals : true,
				allowBlank : false,
				decimalPrecision : 2,
				maxValue : 99999999
			}, {
				xtype : 'numberfield',
				name : 'bagBeltNum_modify',
				fieldLabel : packaging.PCpackageAssistPriceManager
						.i18n('foss.packaging.PCpackageAssistPriceManager.packageGrid.bagBeltNum'),// '/打包带根数',
				columnWidth : .40,
				maxLength : 8,
				allowDecimals : false,
				allowBlank : false,
				maxValue : 99999999
			}, {
				border : false,
				xtype : 'container',
				columnWidth : 0.9,
				layout : 'column',
				defaults : {
					margin : '5 0 5 0'
				},
				items : [{
					xtype : 'button',
					columnWidth : .08,
					text : packaging.PCpackageAssistPriceManager
							.i18n('foss.packaging.PCpackageAssistPriceManager.assistPackageQueryForm.resetButton'),// '重置',
					handler : function() {
						packaging.PCpackageAssistPriceManager
								.modifyPanelReset(this.up('form').getForm()
										.findField('id_modify').getValue());
						/*
						 * var form = this.up('form').getForm();
						 * form.findField('actualFrameVolume_modify').setValue(0);
						 * form.findField('actualWoodenVolume_modify').setValue(0);
						 * form.findField('actualMaskNumber_modify').setValue(0);
						 * form.findField('woodenBarLong_modify').setValue(0);
						 * form.findField('bubbVelamenVolume_modify').setValue(0);
						 * form.findField('bindVelamenVolume_modify').setValue(0);
						 * form.findField('bagBeltNum_modify').setValue(0);
						 */

					}
				}, {
					border : false,
					columnWidth : .84,
					html : '&nbsp;'
				}, {
					columnWidth : .08,
					xtype : 'button',
					cls : 'yellow_button',
					text : packaging.PCpackageAssistPriceManager
							.i18n('foss.packaging.PCpackageAssistPriceManager.assistPackageQueryForm.submit'),// '提交',
					handler : function() {
						var windowMask = new Ext.LoadMask(
								packaging.PCpackageAssistPriceManager.assistPackageModifyWindow,
								{
									msg : '处理中,请稍候...'
								});
						windowMask.show();
						var form = this.up('form').getForm();
						var id = form.findField('id_modify').getValue();
						var waybillNo = form.findField('waybillNo_modify')
								.getValue();
						if (waybillNo == '') {
							Ext.Msg.alert(packaging.PCpackageAssistPriceManager
											.i18n('foss.packaging.error'),
									'运单号不能为空');

						}
						var packageSupplierCode_modify = form
								.findField('packageSupplierCode_modify')
								.getValue();
						if (packageSupplierCode_modify == '') {
							Ext.Msg.alert(packaging.PCpackageAssistPriceManager
											.i18n('foss.packaging.error'),
									'包装供应商不能为空');

						}
						// 木架体积
						var actualFrameVolume_modify = form
								.findField('actualFrameVolume_modify')
								.getValue();

						// 木箱
						var actualWoodenVolume_modify = form
								.findField('actualWoodenVolume_modify')
								.getValue();

						// 木托
						var actualMaskNumber_modify = form
								.findField('actualMaskNumber_modify')
								.getValue();

						// 木条
						var woodenBarLong_modify = form
								.findField('woodenBarLong_modify').getValue();
						// 气泡膜
						var bubbVelamenVolume_modify = form
								.findField('bubbVelamenVolume_modify')
								.getValue();

						// 缠绕膜
						var bindVelamenVolume_modify = form
								.findField('bindVelamenVolume_modify')
								.getValue();

						// 包带
						var bagBeltNum_modify = form
								.findField('bagBeltNum_modify').getValue();

						// 修改时间
						var modifyTime = form.findField('modify_time_modify')
								.getValue();

						/*
						 * //实际打木架
						 * actualFrameVolume=packaging.PCpackageAssistPriceManager.actualFrameVolume;
						 * //实际打木箱
						 * actualWoodenVolume=packaging.PCpackageAssistPriceManager.actualWoodenVolume;
						 * //实际打木托
						 * actualMaskNumber=packaging.PCpackageAssistPriceManager.actualMaskNumber;
						 * //木条长度
						 * woodenBarLong=packaging.PCpackageAssistPriceManager.woodenBarLong;
						 * //气泡膜体积
						 * bubbVelamenVolume=packaging.PCpackageAssistPriceManager.bubbVelamenVolume;
						 * //缠绕膜体积
						 * bindVelamenVolume=packaging.PCpackageAssistPriceManager.bindVelamenVolume;
						 * //包带根数
						 * bagBeltNum=packaging.PCpackageAssistPriceManager.bagBeltNum;
						 */

						/*
						 * if(actualFrameVolume_modify==actualFrameVolume
						 * &&actualWoodenVolume_modify==actualWoodenVolume
						 * &&actualMaskNumber_modify==actualMaskNumber
						 * &&woodenBarLong_modify==woodenBarLong
						 * &&bubbVelamenVolume_modify==bubbVelamenVolume
						 * &&bindVelamenVolume_modify==bindVelamenVolume
						 * &&bagBeltNum_modify==bagBeltNum){
						 * Ext.Msg.alert(packaging.PCpackageAssistPriceManager.i18n('foss.packaging.error'),
						 * '运单:{'+waybillNo+'} 的辅助包装金额信息没做任何修改！'); return; }
						 */

						if (actualFrameVolume_modify == 0
								&& actualWoodenVolume_modify == 0
								&& actualMaskNumber_modify == 0
								&& woodenBarLong_modify == 0
								&& bubbVelamenVolume_modify == 0
								&& bindVelamenVolume_modify == 0
								&& bagBeltNum_modify == 0) {
							Ext.Msg.alert(packaging.PCpackageAssistPriceManager
											.i18n('foss.packaging.error'),
									'运单:{' + waybillNo + '} 所有的录入项不能同时为0！');
							windowMask.hide();
							return;
						}
						params = {
							'queryPackedPriceVo.packageAssistPriceEntity.id' : id,
							'queryPackedPriceVo.packageAssistPriceEntity.waybillNo' : waybillNo,
							'queryPackedPriceVo.packageAssistPriceEntity.packageSupplierCode' : packageSupplierCode_modify,
							'queryPackedPriceVo.packageAssistPriceEntity.actualFrameVolume' : actualFrameVolume_modify,
							'queryPackedPriceVo.packageAssistPriceEntity.actualWoodenVolume' : actualWoodenVolume_modify,
							'queryPackedPriceVo.packageAssistPriceEntity.actualMaskNumber' : actualMaskNumber_modify,
							'queryPackedPriceVo.packageAssistPriceEntity.woodenBarLong' : woodenBarLong_modify,
							'queryPackedPriceVo.packageAssistPriceEntity.bubbVelamenVolume' : bubbVelamenVolume_modify,
							'queryPackedPriceVo.packageAssistPriceEntity.bindVelamenVolume' : bindVelamenVolume_modify,
							'queryPackedPriceVo.packageAssistPriceEntity.bagBeltNum' : bagBeltNum_modify,
							'queryPackedPriceVo.packageAssistPriceEntity.modifyTimeMs' : modifyTime
						}

						if (form.isValid()) {
							Ext.Ajax.request({
								url : packaging
										.realPath('updatePackageAssistPrice.action'),
								params : params,
								success : function(response) {
									var result = Ext
											.decode(response.responseText), packageAssistPrice = result.queryPackedPriceVo.packageAssistPriceEntity;
									form
											.findField('modify_time_modify')
											.setValue(packageAssistPrice.modifyTime);
									// 更新model
									packaging.PCpackageAssistPriceManager
											.assistPackageModifyModelReset();
									Ext.ux.Toast
											.msg(
													packaging.PCpackageAssistPriceManager
															.i18n('foss.packaging.hint'),
													packaging.PCpackageAssistPriceManager
															.i18n('foss.packaging.PCpackageAssistPriceManager.modifySuccess'));

								},
								exception : function(response) {
									var result = Ext
											.decode(response.responseText);
									Ext.Msg
											.alert(
													packaging.PCpackageAssistPriceManager
															.i18n('foss.packaging.error'),
													result.message);
									windowMask.hide();
									return;
								}
							});

						}
						windowMask.hide();
					}
				}]
			}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});
packaging.PCpackageAssistPriceManager.assistPackageModifyForm = Ext
		.create('Foss.PCpackageAssistPriceManager.assistPackageModifyForm'// ,
		// {id: 'Foss_packaging_assistPackageModifyForm_Id'}
		);
packaging.PCpackageAssistPriceManager.assistPackageModifyWindow = Ext.create(
		'Ext.window.Window', {
			// id : 'Foss_packaging_assistPackageModifyWindow_Id',
			autoDestroy : true,
			closable : true,
			closeAction : 'hide',
			draggable : true,
			title : packaging.PCpackageAssistPriceManager
					.i18n('foss.packaging.PCpackageAssistPriceManager.assistPackageModifyForm.window.title'),// '修改包装金额信息',
			cls : 'specialWin',
			height : 400,
			width : 800,
			modal : true,
			layout : 'fit',
			items : [packaging.PCpackageAssistPriceManager.assistPackageModifyForm],
			listeners : {
				hide : function(field, e) {
					packaging.PCpackageAssistPriceManager.oldValue = '';
					packaging.PCpackageAssistPriceManager.ModifyWindow = 0;
					// 修改之后重写store
					var form = packaging.PCpackageAssistPriceManager.assistPackageModifyForm
							.getForm(), grid = packaging.PCpackageAssistPriceManager.PackageGrid, store = grid.store, data = packaging.PCpackageAssistPriceManager.assistPackageModifyModel, id = form
							.findField('id_modify').getValue(), modifyTime = form
							.findField('modify_time_modify').getValue();
					store.each(function(record) {
								if (record.get('id') == id
										&& modifyTime != record
												.get('modifyTime')) {

									record.set('modifyTime', modifyTime);
									record
											.set(
													'actualFrameVolume',
													data
															.get('actualFrameVolume_modify'));
									record
											.set(
													'actualWoodenVolume',
													data
															.get('actualWoodenVolume_modify'));
									record
											.set(
													'actualMaskNumber',
													data
															.get('actualMaskNumber_modify'));
									record.set('woodenBarLong',
											data.get('woodenBarLong_modify'));
									record
											.set(
													'bubbVelamenVolume',
													data
															.get('bubbVelamenVolume_modify'));
									record
											.set(
													'bindVelamenVolume',
													data
															.get('bindVelamenVolume_modify'));
									record.set('bagBeltNum', data
													.get('bagBeltNum_modify'));
								}
							});
					data.set('actualFrameVolume_modify', null);
					data.set('actualWoodenVolume_modify', null);
					data.set('actualMaskNumber_modify', null);
					data.set('woodenBarLong_modify', null);
					data.set('bubbVelamenVolume_modify', null);
					data.set('bindVelamenVolume_modify', null);
					data.set('bagBeltNum_modify', null);

				},
				close : function(field, e) {
					packaging.PCpackageAssistPriceManager.oldValue = '';
					packaging.PCpackageAssistPriceManager.ModifyWindow = 0;
				}
			}
		});

/*******************************************************************************
 * 定义辅助包装金额管理model
 ******************************************************************************/
Ext.define('Foss.PCpackageAssistPriceManager.PackageModel', {
			extend : 'Ext.data.Model',
			// 定义字段
			fields : [{
						name : ' id ',
						type : 'string'
					}, {// 额外的用于生成的EXT使用的列
						// 运单号
						name : 'waybillNo',
						type : 'string'
					}, {
						// 开单部门code
						name : 'billOrgCode',
						type : 'string'
					}, {
						// 开单部门名称
						name : 'billOrgName'
					}, {
						// 包装部门code
						name : 'packageOrgCode'
					}, {
						// 包装部门
						name : 'packageOrgName'
					}, {
						// 理论打木架体积
						name : 'theoryFrameVolume'
					}, {
						// 实际打木架体积
						name : 'actualFrameVolume'
					}, {
						// 打木架体积
						name : 'packageFrameVolume'
					}, {
						// 理论打木箱体积
						name : 'theoryWoodenVolume'
					}, {
						// 实际打木箱体积
						name : 'actualWoodenVolume'
					}, {
						// 打木箱体积
						name : 'packageWoodenVolume'
					}, {
						// 理论打木托个数
						name : 'theoryMaskNumber'
					}, {
						// 实际打木托个数
						name : 'actualMaskNumber'
					}, {
						// 打木托个数
						name : 'packageMaskNumber'
					}, {
						// 木条长度
						name : 'woodenBarLong'
					}, {
						// 气泡膜体积
						name : 'bubbVelamenVolume'
					}, {
						// 缠绕膜体积
						name : 'bindVelamenVolume'
					}, {
						// 包带根数
						name : 'bagBeltNum',
						type : 'string'
					}, {
						// 应付金额
						name : 'packagePayableMoney'
					}, {
						// 包装供应商code
						name : 'packageSupplierCode'
					}, {
						// 包装供应商
						name : 'packageSupplierName'
					}, {
						// 包装材料
						name : 'packageMaterial'
					}, {
						// 创建部门code
						name : 'createOrgCode'
					}, {
						// 创建部门
						name : 'createOrgName'
					}, {
						// 创建人code
						name : 'createUserCode'
					}, {
						// 创建人
						name : 'createUserName'
					}, {
						// 创建时间
						name : 'createTime',
						type : 'string',
						convert : function(value) {
							if (value != null) {
								var date = new Date(value);
								return Ext.Date.format(date, 'Y-m-d H:i:s');
							} else {
								return "";
							}
						}
					}, {
						// 创建人code
						name : 'modifyUserCode'
					}, {
						// 创建人
						name : 'modifyUserName'
					}, {
						// 修改时间
						name : 'modifyTime',
						type : 'string'/*
										 * , convert: function(value) { if
										 * (value != null) { var date = new
										 * Date(value); return
										 * Ext.Date.format(date,'Y-m-d
										 * H:i:s:u'); } else { return ""; } }
										 */
					}, {
						// 是否当月单据
						name : 'isNowMonth'
					}, {
						// 审核状态
						name : 'auditStatus'
					}]
		});
/*******************************************************************************
 * 
 * 定义辅助包装金额管理store
 ******************************************************************************/
Ext.define('Foss.PCpackageAssistPriceManager.PackageStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.PCpackageAssistPriceManager.PackageModel',
	pageSize : 10,
	autoLoad : false,
	// 定义一个代理对象
	proxy : {
		// 代理的类型为ajax
		type : 'ajax',
		// 提交方式
		actionMethods : 'POST',
		// 路径
		// url:'../packaging/PCpackageAssistPriceManagerAll.action',
		url : packaging.realPath('queryAssistPirceList.action'),
		// 定义一个读取器
		reader : {
			// 以JSON的方式读取
			type : 'json',
			// 定义读取JSON数据的根对象
			root : 'queryPackedPriceVo.packageAssistPriceEntityList',
			// 返回总数
			totalProperty : 'totalCount',
			successProperty : 'success'
		},
		listeners : {
			exception : function(reader, response, eopts) {
				var result = Ext.decode(response.responseText);
				// 错误
				Ext.Msg.alert(packaging.PCpackageAssistPriceManager
								.i18n('foss.packaging.querypacked.warning'),
						result.message);
			}
		}
	},
	listeners : {
		// 查询事件
		beforeload : function(store, operation, eOpts) {
			var queryParams = packaging.assistPackageQueryForm.getValues();
			Ext.apply(operation, {
				// 设置查询条件与后台实体对应
				params : {
					'queryPackedPriceVo.queryAssistPackedDto.waybillNo' : queryParams.waybillNo,
					'queryPackedPriceVo.queryAssistPackedDto.packDeptCode' : queryParams.packDeptCode,
					'queryPackedPriceVo.queryAssistPackedDto.waybillCreateDept' : queryParams.waybillCreateDept,
					'queryPackedPriceVo.queryAssistPackedDto.packageSupplierCode' : queryParams.packageSupplierCode,
					'queryPackedPriceVo.queryAssistPackedDto.packedBeginDate' : queryParams.packedBeginDate,
					'queryPackedPriceVo.queryAssistPackedDto.packedEndDate' : queryParams.packedEndDate,
					'queryPackedPriceVo.queryAssistPackedDto.auditStatus' : queryParams.auditStatus
				}
			});
		},
		load : function(store, records, successful, eOpts) {
			if (successful) {
				// 统计列表信息
				if (records.length == 0) {
					this.removeAll();
					// 提示，未查询到符合条件的包装数据！
					Ext.ux.Toast
							.msg(
									packaging.PCpackageAssistPriceManager
											.i18n('foss.packaging.querypacked.hint'),
									packaging.PCpackageAssistPriceManager
											.i18n('foss.packaging.querypacked.packageGrid.packageStore.queryHint'),
									'success', 5000);
				}
			} else {
				this.removeAll();
			}

		}
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

/**
 * grid列表
 */
Ext.define('Foss.PCpackageAssistPriceManager.PackageGrid', {
	extend : 'Ext.grid.Panel',
	title : packaging.PCpackageAssistPriceManager
			.i18n('foss.packaging.PCpackageAssistPriceManager.packageGrid.title'),// '包装金额信息查询结果',
	cls : 'autoHeight',
	bodyCls : 'autoHeight',
	frame : true,
	// 增加滚动条
	autoScroll : true,
	stripeRows : true, // 交替行效果
	collapsible : true,
	animCollapse : true,
	selModel : null,
	viewConfig : {
		stripeRows : false,
		getRowClass : function(record, rowIndex, rp, ds) {
			// 是否当前月单据
			var isNowMonth = record.get('isNowMonth');

			if (isNowMonth == 'N') {
				return 'package_row_yellow';
			}
		}
	},
	columns : [{
		xtype : 'actioncolumn',
		flex : 1.2,
		text : packaging.PCpackageAssistPriceManager
				.i18n('foss.packaging.PCpackageAssistPriceManager.packageGrid.operate'),// '操作',
		align : 'center',
		items : [{
			iconCls : 'deppon_icons_edit',
			handler : function(grid, rowIndex, colIndex) {
				// packaging.PCpackageAssistPriceManager.id = id;
				// 运单号
				// packaging.PCpackageAssistPriceManager.waybillNo = waybillNo;
				var assistPackageModifyWindow = packaging.PCpackageAssistPriceManager.assistPackageModifyWindow;
				assistPackageModifyWindow.restore();
				var modifyRecord = grid.store.getAt(rowIndex);
				var assistPackageModifyForm = packaging.PCpackageAssistPriceManager.assistPackageModifyForm;
				// id
				var id = modifyRecord.get('id');
				assistPackageModifyForm.getForm().findField('id_modify')
						.setValue(id);
				// packaging.PCpackageAssistPriceManager.assistPackageModifyForm=assistPackageModifyForm;
				var waybillNo = modifyRecord.get('waybillNo');
				assistPackageModifyForm.getForm().findField('waybillNo_modify')
						.setValue(waybillNo);
				// 修改时间
				var modifyTime = grid.store.getAt(rowIndex).get('modifyTime');
				assistPackageModifyForm.getForm()
						.findField('modify_time_modify').setValue(modifyTime);
				// 包装供应商
				var packageSupplierCode = modifyRecord
						.get('packageSupplierCode');
				assistPackageModifyForm.getForm()
						.findField('packageSupplierCode_modify')
						.setValue(packageSupplierCode);

				// 实际打木架
				var actualFrameVolume = modifyRecord.get('actualFrameVolume');
				// packaging.PCpackageAssistPriceManager.actualFrameVolume=actualFrameVolume;
				assistPackageModifyForm.getForm()
						.findField('actualFrameVolume_modify')
						.setValue(actualFrameVolume);
				// 实际打木箱
				var actualWoodenVolume = modifyRecord.get('actualWoodenVolume');
				// packaging.PCpackageAssistPriceManager.actualWoodenVolume=actualWoodenVolume;
				assistPackageModifyForm.getForm()
						.findField('actualWoodenVolume_modify')
						.setValue(actualWoodenVolume);

				// 实际打木托
				var actualMaskNumber = modifyRecord.get('actualMaskNumber');
				// packaging.PCpackageAssistPriceManager.actualMaskNumber=actualMaskNumber;
				assistPackageModifyForm.getForm()
						.findField('actualMaskNumber_modify')
						.setValue(actualMaskNumber);

				// 木条长度
				var woodenBarLong = modifyRecord.get('woodenBarLong');
				// packaging.PCpackageAssistPriceManager.woodenBarLong=woodenBarLong;
				assistPackageModifyForm.getForm()
						.findField('woodenBarLong_modify')
						.setValue(woodenBarLong);

				// 气泡膜体积
				var bubbVelamenVolume = modifyRecord.get('bubbVelamenVolume');
				// packaging.PCpackageAssistPriceManager.bubbVelamenVolume=bubbVelamenVolume;
				assistPackageModifyForm.getForm()
						.findField('bubbVelamenVolume_modify')
						.setValue(bubbVelamenVolume);

				// 缠绕膜体积
				var bindVelamenVolume = modifyRecord.get('bindVelamenVolume');
				// packaging.PCpackageAssistPriceManager.bindVelamenVolume=bindVelamenVolume;
				assistPackageModifyForm.getForm()
						.findField('bindVelamenVolume_modify')
						.setValue(bindVelamenVolume);

				// 包带根数
				var bagBeltNum = modifyRecord.get('bagBeltNum');
				// packaging.PCpackageAssistPriceManager.bagBeltNum=bagBeltNum;
				assistPackageModifyForm.getForm()
						.findField('bagBeltNum_modify').setValue(bagBeltNum);

				params = {
					'queryPackedPriceVo.queryAssistPackedDto.waybillNo' : waybillNo,
					'queryPackedPriceVo.queryAssistPackedDto.packageSupplierCode' : packageSupplierCode

				}
				Ext.Ajax.request({
					url : packaging
							.realPath('queryPackageMainPriceByWaybillNoAndpackageSupplierCode.action'),
					params : params,
					// 获取当前登录人的所在部门
					success : function(response) {
						// 回复可编辑
						assistPackageModifyForm.getForm()
								.findField('actualFrameVolume_modify')
								.setReadOnly(false);
						assistPackageModifyForm.getForm()
								.findField('actualWoodenVolume_modify')
								.setReadOnly(false);
					},
					exception : function(response) {
						var result = Ext.decode(response.responseText);
						if (result.message == 'isNotExist') {
							Ext.Msg.alert(packaging.PCpackageAssistPriceManager
											.i18n('foss.packaging.error'),
									'运单号不存在');
							return;
						}
						// 打木架、打木箱体积不可编辑
						assistPackageModifyForm.getForm()
								.findField('actualFrameVolume_modify')
								.setValue(0);
						assistPackageModifyForm.getForm()
								.findField('actualFrameVolume_modify')
								.setReadOnly(true);

						assistPackageModifyForm.getForm()
								.findField('actualWoodenVolume_modify')
								.setValue(0);

						assistPackageModifyForm.getForm()
								.findField('actualWoodenVolume_modify')
								.setReadOnly(true);
						/*
						 * var result = Ext.decode(response.responseText);
						 * Ext.Msg.alert(packaging.PCpackageAssistPriceManager.i18n('foss.packaging.error'),
						 * result.message);
						 */
					}
				});

				packaging.PCpackageAssistPriceManager.ModifyWindow = 1;
				assistPackageModifyWindow.show()

			}
		}, {
			iconCls : 'deppon_icons_delete',
			handler : function(grid, rowIndex, colIndex) {
				var id = grid.store.getAt(rowIndex).get('id')
				if (id == '') {
					Ext.Msg.alert('请选择记录');
				}

				Ext.MessageBox.confirm(packaging.PCpackageAssistPriceManager
								.i18n('foss.packaging.hint'), '确认删除？',
						function(btn) {
							if (btn == 'yes') {

								params = {
									'queryPackedPriceVo.packageAssistPriceEntity.id' : id
								}
								Ext.Ajax.request({
									url : packaging
											.realPath('deletePackedAssistPriceById.action'),
									params : params,
									// 获取当前登录人的所在部门
									success : function(response) {
										// 删除前台数据
										var store = grid.getStore();
										store.removeAt(rowIndex);

										Ext.Msg
												.alert(
														packaging.PCpackageAssistPriceManager
																.i18n('foss.packaging.hint'),
														'删除成功');

									},
									exception : function(response) {
										var result = Ext
												.decode(response.responseText);
										Ext.Msg
												.alert(
														packaging.PCpackageAssistPriceManager
																.i18n('foss.packaging.error'),
														result.message);
									}
								});
							}

						});

			}
		}, {

			conCls : 'foss_icons_stl_auditing'
		}]
	}, {
		xtype : 'ellipsiscolumn',
		text : packaging.PCpackageAssistPriceManager
				.i18n('foss.packaging.PCpackageAssistPriceManager.packageGrid.waybillNo'),// '运单号',
		align : 'center',
		width : 80,
		dataIndex : 'waybillNo'
	}, {
		xtype : 'ellipsiscolumn',
		text : packaging.PCpackageAssistPriceManager
				.i18n('foss.packaging.PCpackageAssistPriceManager.packageGrid.createTime'),// '包装时间',
		width : 80,
		align : 'center',
		format : 'Y-m-d H:i:s',
		dataIndex : 'createTime'
	}, {
		text : packaging.PCpackageAssistPriceManager
				.i18n('foss.packaging.PCpackageAssistPriceManager.packageGrid.billOrgName'),// "开单部门",
		align : 'center',
		width : 80,
		xtype : 'ellipsiscolumn',
		dataIndex : 'billOrgName'
	}, {
		text : packaging.PCpackageAssistPriceManager
				.i18n('foss.packaging.PCpackageAssistPriceManager.packageGrid.actualFrameVolume'),// "实际打木架体积",
		width : 80,
		align : 'center',
		dataIndex : 'actualFrameVolume'
	}, {
		text : packaging.PCpackageAssistPriceManager
				.i18n('foss.packaging.PCpackageAssistPriceManager.packageGrid.actualWoodenVolume'),// '实际打木箱体积',
		width : 80,
		align : 'center',
		xtype : 'ellipsiscolumn',
		dataIndex : 'actualWoodenVolume'
	}, {
		xtype : 'ellipsiscolumn',
		text : packaging.PCpackageAssistPriceManager
				.i18n('foss.packaging.PCpackageAssistPriceManager.packageGrid.actualMaskNumber'),// '实际打木托个数',
		width : 80,
		align : 'center',
		dataIndex : 'actualMaskNumber'
	}, {
		xtype : 'ellipsiscolumn',
		text : packaging.PCpackageAssistPriceManager
				.i18n('foss.packaging.PCpackageAssistPriceManager.packageGrid.theoryFrameVolume'),// "理论打木架体积",
		width : 80,
		align : 'center',
		dataIndex : 'theoryFrameVolume'
	}, {
		xtype : 'ellipsiscolumn',
		text : packaging.PCpackageAssistPriceManager
				.i18n('foss.packaging.PCpackageAssistPriceManager.packageGrid.theoryWoodenVolume'),// "理论打木箱体积",
		width : 80,
		align : 'center',
		dataIndex : 'theoryWoodenVolume'
	}, {
		xtype : 'ellipsiscolumn',
		text : packaging.PCpackageAssistPriceManager
				.i18n('foss.packaging.PCpackageAssistPriceManager.packageGrid.theoryMaskNumber'),// "理论打木托个数",
		width : 80,
		align : 'center',
		dataIndex : 'theoryMaskNumber'

	}, {
		xtype : 'ellipsiscolumn',
		text : packaging.PCpackageAssistPriceManager
				.i18n('foss.packaging.PCpackageAssistPriceManager.packageGrid.woodenBarLong'),// "木条长度",
		width : 80,
		align : 'center',
		dataIndex : 'woodenBarLong'
	}, {
		xtype : 'ellipsiscolumn',
		text : packaging.PCpackageAssistPriceManager
				.i18n('foss.packaging.PCpackageAssistPriceManager.packageGrid.bubbVelamenVolume'),// '气泡膜体积',
		width : 80,
		align : 'center',
		dataIndex : 'bubbVelamenVolume'
	}, {
		xtype : 'ellipsiscolumn',
		text : packaging.PCpackageAssistPriceManager
				.i18n('foss.packaging.PCpackageAssistPriceManager.packageGrid.bindVelamenVolume'),// '缠绕膜体积',
		width : 80,
		align : 'center',
		dataIndex : 'bindVelamenVolume'
	}, {
		xtype : 'ellipsiscolumn',
		text : packaging.PCpackageAssistPriceManager
				.i18n('foss.packaging.PCpackageAssistPriceManager.packageGrid.bagBeltNum'),// '包带根数',
		width : 80,
		align : 'center',
		dataIndex : 'bagBeltNum'
	}, {
		xtype : 'ellipsiscolumn',
		text : packaging.PCpackageAssistPriceManager
				.i18n('foss.packaging.PCpackageAssistPriceManager.packageGrid.packagePayableMoney'),// '应付金额',
		width : 80,
		align : 'center',
		dataIndex : 'packagePayableMoney'
	}, {
		xtype : 'ellipsiscolumn',
		text : packaging.PCpackageAssistPriceManager
				.i18n('foss.packaging.PCpackageAssistPriceManager.packageGrid.createUserName'),// '包装人',
		width : 80,
		align : 'center',
		dataIndex : 'createUserName'
	}, {
		xtype : 'ellipsiscolumn',
		text : packaging.PCpackageAssistPriceManager
				.i18n('foss.packaging.PCpackageAssistPriceManager.packageGrid.modifyUserName'),// '更改人',
		width : 80,
		align : 'center',
		dataIndex : 'modifyUserName'
	}, {
		xtype : 'ellipsiscolumn',
		text : packaging.PCpackageAssistPriceManager
				.i18n('foss.packaging.PCpackageAssistPriceManager.packageGrid.packageSupplierName'),// '包装供应商',
		width : 120,
		align : 'center',
		dataIndex : 'packageSupplierName'
	}, {
		xtype : 'ellipsiscolumn',
		text : packaging.PCpackageAssistPriceManager
				.i18n('foss.packaging.PCpackageAssistPriceManager.packageGrid.auditStatus'),// '审核状态',
		width : 80,
		align : 'center',
		dataIndex : 'auditStatus',
		renderer : function(value) {
			if (value == 'WAITINGAUDIT') {// 未审核
				return packaging.PCpackageAssistPriceManager
						.i18n('foss.packaging.PCpackageAssistPriceManager.packageGrid.auditStatus.waitingaudit');
			} else if (value == 'HASAUDITED') { // 已审核
				return packaging.PCpackageAssistPriceManager
						.i18n('foss.packaging.PCpackageAssistPriceManager.packageGrid.auditStatus.hasudited');
			} else if (value == 'BACKAUDIT') {// 反审核
				return packaging.PCpackageAssistPriceManager
						.i18n('foss.packaging.PCpackageAssistPriceManager.packageGrid.auditStatus.backaudit');
			} else {// 失效
				return packaging.PCpackageAssistPriceManager
						.i18n('foss.packaging.PCpackageAssistPriceManager.packageGrid.auditStatus.invalid');
			}
		}
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.PCpackageAssistPriceManager.PackageStore');
		me.selModel = Ext.create('Ext.selection.CheckboxModel');
		// 导出按钮
		me.tbar = [{
			xtype : 'button',
			text : packaging.PCpackageAssistPriceManager
					.i18n('foss.packaging.button.import'),// 导入
			disabled : (packaging.PCpackageAssistPriceManager
							.isPermission('packaging/importExcelPackedButton'))
							? false
							: true,
			hidden : (packaging.PCpackageAssistPriceManager
					.isPermission('packaging/importExcelPackedButton'))
					? false
					: true,
			handler : function() {

				// 弹出导入窗口
				var importWindow = packaging.PCpackageAssistPriceManager.importWindow;
				if (importWindow == null) {
					importWindow = Ext
							.create('Foss.PCpackageAssistPriceManager.window.ImportPackedAssistPirce');
				}
				importWindow.center().show();
			}
		}, {
			xtype : 'button',
			text : packaging.PCpackageAssistPriceManager
					.i18n('foss.packaging.button.loading'),// 导入模版下载
					disabled : (packaging.PCpackageAssistPriceManager
							.isPermission('packaging/importExcelMasterplateButton'))?false:true,
			hidden : (packaging.PCpackageAssistPriceManager
					.isPermission('packaging/importExcelMasterplateButton'))
					? false
					: true,
			handler : function() {

				packaging.PCpackageAssistPriceManager
						.importDownloadMasterplateExcel();

			}
		}, '->', {
			xtype : 'button',
			text : packaging.PCpackageAssistPriceManager
					.i18n('foss.packaging.button.audited'),// 审核
					disabled : (packaging.PCpackageAssistPriceManager
					.isPermission('packaging/auditedPackedButton'))
					? false
					: true,
			hidden : (packaging.PCpackageAssistPriceManager
					.isPermission('packaging/auditedPackedButton'))
					? false
					: true,
			handler : function() {
				packaging.PCpackageAssistPriceManager.auditAndDeaudit('audit');
			}
		}, {
			xtype : 'button',
			text : packaging.PCpackageAssistPriceManager
					.i18n('foss.packaging.button.backaudit'),// 反审核
			disabled : (packaging.PCpackageAssistPriceManager
					.isPermission('packaging/backauditlPackedButton'))
					? false
					: true,
			hidden : (packaging.PCpackageAssistPriceManager
					.isPermission('packaging/backauditlPackedButton'))
					? false
					: true,
			handler : function() {
				packaging.PCpackageAssistPriceManager
						.auditAndDeaudit('deaudit');
			}
		}],
		/*
		 * me.bbar = Ext.create('Ext.PagingToolbar', { store: me.store,
		 * displayInfo: true, displayMsg: '当前显示从{0}到 {1}条记录，共 {2}条', emptyMsg:
		 * "无记录" });
		 */
		// 自定义分页控件
		me.bbar = Ext.create('Deppon.StandardPaging', {
					store : me.store,
					plugins : 'pagesizeplugin'
				});
		packaging.PCpackageAssistPriceManager.pagingBar = me.bbar;
		me.callParent([cfg]);
	}
});

/**
 * 导出到excel
 */
packaging.PCpackageAssistPriceManager.exportExcelPacked = function() {
	// 权限校验
	if (!packaging.PCpackageAssistPriceManager
			.isPermission('packaging/PCpackageAssistPriceManagerexportButton')) {
		return;
	}
	var actionUrl = packaging.realPath("exportPackedAssistPriceExcel.action");
	// 执行查询，首先货物查询条件，packaging.packageQueryForm为全局变量，在查询条件的FORM创建时生成
	var queryParams = packaging.assistPackageQueryForm.getValues();
	params = {
		'queryPackedPriceVo.queryAssistPackedDto.waybillNo' : queryParams.waybillNo,
		'queryPackedPriceVo.queryAssistPackedDto.packDeptCode' : queryParams.packDeptCode,
		'queryPackedPriceVo.queryAssistPackedDto.waybillCreateDept' : queryParams.waybillCreateDept,
		'queryPackedPriceVo.queryAssistPackedDto.packageSupplierCode' : queryParams.packageSupplierCode,
		'queryPackedPriceVo.queryAssistPackedDto.packedBeginDate' : queryParams.packedBeginDate,
		'queryPackedPriceVo.queryAssistPackedDto.packedEndDate' : queryParams.packedEndDate,
		'queryPackedPriceVo.queryAssistPackedDto.auditStatus' : queryParams.auditStatus
	}
	if (!Ext.fly('downloadAttachFileForm')) {
		var frm = document.createElement('form');
		frm.id = 'downloadAttachFileForm';
		frm.style.display = 'none';
		document.body.appendChild(frm);
	}

	Ext.Ajax.request({
				url : actionUrl,
				form : Ext.fly('downloadAttachFileForm'),
				method : 'POST',
				params : params,
				isUpload : true,
				exception : function(response, opts) {
				},
				success : function(response, opts) {
				}
			});
}

/**
 * 导入模版下载
 * 
 */
packaging.PCpackageAssistPriceManager.importDownloadMasterplateExcel = function() {
	// 权限校验
	if (!packaging.PCpackageAssistPriceManager
			.isPermission('packaging/importExcelMasterplateButton')) {
		return;
	}
	var actionUrl = packaging
			.realPath("downloadMasterplateToAssistExcel.action");
	if (!Ext.fly('downloadAttachFileForm')) {
		var frm = document.createElement('form');
		frm.id = 'downloadAttachFileForm';
		frm.style.display = 'none';
		document.body.appendChild(frm);
	}

	Ext.Ajax.request({
				url : actionUrl,
				form : Ext.fly('downloadAttachFileForm'),
				method : 'POST',
				params : null,
				isUpload : true,
				exception : function(response, opts) {
				},
				success : function(response, opts) {
				}
			});
}

/**
 * 审核反审核
 * 
 */
packaging.PCpackageAssistPriceManager.auditAndDeaudit = function(operateType) {// 审核按钮事件
	var assistPackageGrid = packaging.PCpackageAssistPriceManager.PackageGrid, selecttionModel = assistPackageGrid
			.getSelectionModel(), selectedGridList = selecttionModel
			.getSelection();
	if (selectedGridList.length == 0) {
		Ext.ux.Toast.msg('提示', '请选择辅助包装金额信息', 'error', 3000);
	} else {
		Ext.MessageBox.confirm('提示', '确认继续？', function(btn) {
			if (btn == 'yes') {
				var actionName, auditStatus, idList = new Array();
				if (operateType != 'audit' && operateType != 'deaudit') {
					Ext.ux.Toast.msg('提示', '程序出现未知异常', 'error', 3000);
				} else {
					if (operateType == 'audit') {
						actionName = 'auditPackedAssistPrice.action';
						auditStatus = 'HASAUDITED';// 审核
					} else {
						actionName = 'deauditPackedAssistPrice.action';
						auditStatus = 'WAITINGAUDIT';// 未审核
					}
					for (var i = 0; i < selectedGridList.length; i++) {
						idList.push(selectedGridList[i].get('id'));
					}
					var myMask = new Ext.LoadMask(
							packaging.PCpackageAssistPriceManager.PCpackageAssistPriceMainPanel,
							{
								msg : '处理中,请稍候...'
							});
					myMask.show();
					Ext.Ajax.request({
						url : packaging.realPath(actionName),
						jsonData : {
							'queryPackedPriceVo' : {
								'ids' : idList
							}
						},
						success : function(response) {
							myMask.hide();
							Ext.ux.Toast.msg('提示', '操作成功', 'info', 3000);
							packageAssistPriceStore = assistPackageGrid.store;
							packageAssistPriceStore.each(function(record) {
										if (selecttionModel.isSelected(record)) {
											record.set('auditStatus',
													auditStatus)
										}
									});
							selecttionModel.deselectAll();
						},
						exception : function(response) {
							myMask.hide();
							var result = Ext.decode(response.responseText);
							Ext.ux.Toast.msg('提示', result.message, 'error',
									3000);
						}
					});
				}
			}
		});
	}
}

// 导入表单
Ext.define('Foss.PCpackageAssistPriceManager.ImportPackedAssistPirceForm', {
	extend : 'Ext.form.Panel',
	cls : 'autoHeight',
	defaultType : 'textfield',
	layout : 'column',
	defaults : {
		margin : '5 5 5 5',
		anchor : '98%',
		labelWidth : 90
	},
	// standardSubmit: true,
	items : [{
		xtype : 'filefield',
		name : 'uploadFile',
		readOnly : false,
		buttonOnly : false,
		fieldLabel : packaging.PCpackageAssistPriceManager
				.i18n('foss.packing.PCpackageAssistPriceManager.lable.uploadFile'),// '导入文件',
		msgTarget : 'side',
		cls : 'uploadFile',
		allowBlank : false,
		buttonText : packaging.PCpackageAssistPriceManager
				.i18n('foss.packing.PCpackageAssistPriceManager.lable.scan'),// '浏览',
		columnWidth : .85
	}, {
		xtype : 'button',
		columnWidth : .15,
		cls : 'cleanBtn',
		text : packaging.PCpackageAssistPriceManager
				.i18n('foss.packing.PCpackageAssistPriceManager.button.clear'),// '清除',
		handler : function() {
			this.up('form').getForm().findField('uploadFile').reset();
		}
	}, {
		xtype : 'container',
		columnWidth : 1,
		layout : 'column',
		defaults : {
			margin : '5 0 5 0'
		},
		items : [{
			xtype : 'button',
			columnWidth : .15,
			text : packaging.PCpackageAssistPriceManager
					.i18n('foss.packing.PCpackageAssistPriceManager.button.cancel'),// '取消',
			handler : function() {

				packaging.PCpackageAssistPriceManager.importWindow.close();
			}
		}, {
			border : false,
			columnWidth : .7,
			html : '&nbsp;'
		}, {
			columnWidth : .15,
			xtype : 'button',
			text : packaging.PCpackageAssistPriceManager
					.i18n('foss.packing.PCpackageAssistPriceManager.button.import'),// '导入',
			handler : function() {
				var form = this.up('form').getForm();
				if (form.isValid()) {
					var myMask = new Ext.LoadMask(this.up('form'), {
						msg : packaging.PCpackageAssistPriceManager
								.i18n('foss.packaging.PCpackageAssistPriceManager.tip.waiting')
					});// "正在导入，请稍等..."
					myMask.show();
					form.submit({
						url : packaging
								.realPath("importPackedAssistPriceInfo.action"),
						success : function(form, action) {
							myMask.hide();
							// 重新设置查询条件，刷新数据
							/*
							 * if(!Ext.isEmpty(management.fuelStandardInfo.getValues().vehicleNo) &&
							 * !Ext.isEmpty(management.fuelStandardInfo.getValues().fuelTime)) {
							 * packaging.PCpackageAssistPriceManager.moveFirst(); }
							 */
							packaging.PCpackageAssistPriceManager.pagingBar
									.moveFirst();
							var json = action.result;
							Ext.MessageBox
									.alert(
											packaging.PCpackageAssistPriceManager
													.i18n('foss.packaging.hint'),
											'导入成功'
													+ json.queryPackedPriceVo.importTotalCount
													+ '条');
							packaging.PCpackageAssistPriceManager.importWindow
									.hide();
						},
						exception : function(form, action) {
							myMask.hide();
							json = action.result;
							var msg = json.message;
							while (msg.indexOf(';') > -1) {
								msg = msg.replace(';', "\r\n");
							}
							Ext.create('Ext.window.Window', {
								title : packaging.PCpackageAssistPriceManager
										.i18n('foss.packaging.hint'),
								height : 200,
								width : 400,
								layout : 'fit',
								items : {
									xtype : 'form',
									border : false,
									items : [{
												xtype : 'textarea',
												fieldLabel : '',
												height : 130,
												width : 380,
												autoScroll : true,
												readOnly : true,
												name : 'message',
												value : msg
											}]
								}
							}).show();
						},
						failure : function(form, action) {
							myMask.hide();
							json = action.result;
							var msg = json.message;
							while (msg.indexOf(';') > -1) {
								msg = msg.replace(';', "\r\n");
							}
							Ext.create('Ext.window.Window', {
								title : packaging.PCpackageAssistPriceManager
										.i18n('foss.packaging.hint'),
								height : 200,
								width : 400,
								layout : 'fit',
								items : {
									xtype : 'form',
									border : false,
									items : [{
												xtype : 'textarea',
												fieldLabel : '',
												height : 130,
												width : 380,
												autoScroll : true,
												readOnly : true,
												name : 'message',
												value : msg
											}]
								}
							}).show();
						}
					});
				}
			}
		}]
	}],
	dockedItems : [],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

// 导入窗口
Ext.define('Foss.PCpackageAssistPriceManager.window.ImportPackedAssistPirce', {
	extend : 'Ext.window.Window',
	title : packaging.PCpackageAssistPriceManager
			.i18n('foss.packing.PCpackageAssistPriceManager.label.dataexport'),// '辅助包装金额导入',
	modal : true,
	closeAction : 'hide',
	width : 550,
	bodyCls : 'autoHeight',
	layout : 'auto',
	listeners : {
		hide : function(comp, eOpts) {
			this.down('form').getForm().findField('uploadFile').reset();
		}
	},
	items : [Ext
			.create('Foss.PCpackageAssistPriceManager.ImportPackedAssistPirceForm')]
});
packaging.PCpackageAssistPriceManager.importWindow = Ext
		.create('Foss.PCpackageAssistPriceManager.window.ImportPackedAssistPirce');
packaging.PCpackageAssistPriceManager.assistPackageQueryForm = Ext
		.create('Foss.PCpackageAssistPriceManager.assistPackageQueryForm'// ,
		// {id: 'Foss_packaging_assistPackageQueryForm_Id'}
		);
packaging.PCpackageAssistPriceManager.buttonPanel = Ext
		.create('Foss.PCpackageAssistPriceManager.buttonPanel'// ,
		// {id: 'Foss_packaging_buttonPanel_Id'}
		);
packaging.PCpackageAssistPriceManager.PackageGrid = Ext
		.create('Foss.PCpackageAssistPriceManager.PackageGrid'// ,
		// {id: 'Foss_packaging_PackageGrid_Id'}
		);
Ext.define('Foss.PCpackageAssistPriceManager.PCpackageAssistPriceMainPanel', {
			extend : 'Ext.panel.Panel',
			frame : true,
			layout : 'auto',
			// height : 260,
			items : [
					packaging.PCpackageAssistPriceManager.assistPackageQueryForm,
					packaging.PCpackageAssistPriceManager.buttonPanel,
					packaging.PCpackageAssistPriceManager.PackageGrid]
		});

//获取当前部门的等级外场
packaging.PCpackageAssistPriceManager.getOutfiled=function(){
	
	Ext.Ajax.request({
		url : packaging.realPath('queryPackDept.action'),
		// 获取当前登录人的所在部门
		success : function(response) {
			var result = Ext.decode(response.responseText);
			return result.queryUnpackVo.currentDeptDto.deptCode;
		},
		exception : function(response) {
			var result = Ext.decode(response.responseText);
			Ext.Msg.alert(packaging.PCpackageAssistPriceManager
							.i18n('foss.packaging.error'), result.message);
		}
	});
	
}


/*******************************************************************************
 * onReady
 * 
 ******************************************************************************/
Ext.onReady(function() {
	Ext.QuickTips.init();
	packaging.assistPackageQueryForm = packaging.PCpackageAssistPriceManager.assistPackageQueryForm;
	// packaging.assistPackageGrid =
	// Ext.getCmp('Foss_packaging_PackageGrid_Id');
	packaging.PCpackageAssistPriceManager.deptCode = '';
	// 设置新增或修改状态作为全局变量
	/*
	 * Ext.apply(packaging.PCpackageAssistPriceManager,{ editStatus : 'edit' });
	 */
	Ext.Ajax.request({
		// url:'../packaging/queryPackDept.action',
		url : packaging.realPath('queryPackDept.action'),
		// 获取当前登录人的所在部门
		success : function(response) {
			var result = Ext.decode(response.responseText);
			Ext.apply(packaging.PCpackageAssistPriceManager, {
						packedDept : result.queryUnpackVo.currentDeptDto.deptName,
						packDeptCode : result.queryUnpackVo.currentDeptDto.deptCode
					});
			packaging.assistPackageQueryForm.getForm().findField('packedDept')
					.setValue(result.queryUnpackVo.currentDeptDto.deptName);
			packaging.assistPackageQueryForm.getForm()
					.findField('packDeptCode')
					.setValue(result.queryUnpackVo.currentDeptDto.deptCode);
			packaging.PCpackageAssistPriceManager.deptCode = result.queryUnpackVo.currentDeptDto.deptCode;
		},
		exception : function(response) {
			var result = Ext.decode(response.responseText);
			Ext.Msg.alert(packaging.PCpackageAssistPriceManager
							.i18n('foss.packaging.error'), result.message);
		}
	});
	packaging.PCpackageAssistPriceManager.PCpackageAssistPriceMainPanel = Ext
			.create('Foss.PCpackageAssistPriceManager.PCpackageAssistPriceMainPanel'// ,
			// {id: 'Foss_packaging_PCpackageAssistPriceMainPanel_Id'}
			);
	Ext.create('Ext.panel.Panel', {
		id : 'T_packaging-PCpackageAssistPriceManagerindex_content',
		cls : "panelContentNToolbar",
		bodyCls : 'panelContentNToolbar-body',
		layout : 'auto',
		items : [packaging.PCpackageAssistPriceManager.PCpackageAssistPriceMainPanel],
		renderTo : 'T_packaging-PCpackageAssistPriceManagerindex-body'
	});
});