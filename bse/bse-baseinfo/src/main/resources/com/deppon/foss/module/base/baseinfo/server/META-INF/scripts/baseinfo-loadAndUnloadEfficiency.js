/**
 * 装卸车标准-吨-人天_页面(装卸车人力效率标准)
 */
baseinfo.loadAndUnloadEfficiency.loadAndUnloadEfficiencyMan = new Object();
baseinfo.loadAndUnloadEfficiency.loadAndUnloadEfficiencyMan.deleteLoadAndUnloadEfficiencyMan = function (isMore, p_code) {
	Ext.MessageBox.buttonText.yes = baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.sure');
	Ext.MessageBox.buttonText.no = baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.cancel');
	// 获得国际化字符串
	var tip1 = baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.tipInfo');
	var tip2 = baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.areYouSureToVoid');
	Ext.Msg.confirm(tip1, tip2,
		function (btn, text) {
		if (btn == 'yes') {
			var ids = '';
			if (isMore) {
				var a_grid = Ext.getCmp('Foss_baseinfo_loadAndUnloadEfficiencyMan_LoadAndUnloadEfficiencyManGrid_Id');
				// 获取选中的记录
				var rowObjs = a_grid.getSelectionModel().getSelection();

				if (rowObjs.length <= 0) {
					Ext.Msg.alert(baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.tip'), baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.pleaseSelectDeletedRecord'));
					return;
				}
				// 将id通过,分隔
				for (var i = 0; i < rowObjs.length; i++) {
					ids = ids + rowObjs[i].data.orgCode + ",";
				}
				ids = ids.substring(0, ids.length - 1);
			} else {
				ids = p_code
			}
			//发送作废结点的Ajax请求.
			var loadAndUnloadEfficiencyManVo = new Object();
			loadAndUnloadEfficiencyManVo.loadAndUnloadEfficiencyManEntity = new Object();
			loadAndUnloadEfficiencyManVo.loadAndUnloadEfficiencyManEntity.orgCode = ids;
			var params = {
				'loadAndUnloadEfficiencyManVo' : loadAndUnloadEfficiencyManVo
			};
			// "../baseinfo/deleteLoadAndUnloadEfficiencyManMore.action"
			var r_url = "deleteLoadAndUnloadEfficiencyManMore.action";
			r_url = baseinfo.realPath(r_url);
			Ext.Ajax.request({
				url : r_url,
				jsonData : params,
				//作废成功
				success : function (response) {
					var json = Ext.decode(response.responseText);
					//删除完成后，将“从表格删除”改为刷新表格
					//a_grid.getStore().remove(rowObjs);
					baseinfo.loadAndUnloadEfficiency.loadAndUnloadEfficiencyMan.pagingBar.moveFirst();
					var tipMsg = baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.voidSuccessNo');
					if (json.message) {
						tipMsg = json.message;
					}
					top.Ext.MessageBox.alert(baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.tips'), baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.voidSuccessNo'));
				},
				//作废失败
				exception : function (response) {
					var json = Ext.decode(response.responseText);
					top.Ext.MessageBox.alert(baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.deleteFail'), json.message);
				}
			});
		}
	});
}
////////////////////////////////////////////////////////////////////
// 全局变量（模块名loadAndUnloadEfficiencyMan） end
////////////////////////////////////////////////////////////////////

// 装卸车人力效率标准查询-主面板
Ext.define('Foss.baseinfo.loadAndUnloadEfficiencyMan.SelectForm', {
	extend : 'Ext.form.Panel',
	id : 'Foss_baseinfo_loadAndUnloadEfficiencyMan_SelectForm_Id',
	itemId : 'Foss_baseinfo_loadAndUnloadEfficiencyMan_SelectForm_ItemId',
	//height: 80,
	cls : 'autoHeight',
	bodyCls : 'autoHeight',
	defaultType : 'textfield',
	layout : 'column',
	frame : true,
	columnWidth : 0.72,
	defaults : {
		readOnly : false,
		margin : '5 5 5 10',
		anchor : '90%',
		columnWidth : 1,
		labelWidth : 120
	},
	initComponent : function (config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.items = [
			Ext.create('Foss.baseinfo.loadAndUnloadEfficiencyMan.SelectConditionForm'),
			Ext.create('Foss.baseinfo.loadAndUnloadEfficiencyMan.SelectResultForm')
		];
		me.callParent([cfg]);
	}
});

// 装卸车人力效率标准新增表单
Ext.define('Foss.baseinfo.loadAndUnloadEfficiencyMan.UpdateLoadAndUnloadEfficiencyManForm', {
	extend : 'Ext.form.Panel',
//	id : 'Foss_baseinfo_loadAndUnloadEfficiencyMan_UpdateLoadAndUnloadEfficiencyManForm_Id',
//	title: baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.addLoadAndUnloadManEfficiencyStandard'),
	frame : true,
	hidden : false,
	defaultType : 'textfield',
	layout : {
		type : 'table',
		columns : 1
	},
	columnWidth : 0.99,
	defaults : {
		readOnly : false,
		margin : '5 5 5 10',
		anchor : '90%',
		columnWidth : 1,
		labelWidth : 120
	},
	//取消按钮
	cancelButton : null,
	getCancelButton : function (me) {
		if (this.cancelButton == null) {
			this.cancelButton = Ext.create('Ext.button.Button', {
					xtype : 'button',
					//cls:'yellow_button',
					text : baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.cancel'),
					columnWidth : .12,
					width : 55,
					hidden : false,
					handler : function () {
//						var a_window = Ext.getCmp('Foss_baseinfo_loadAndUnloadEfficiencyMan_UpdateLoadAndUnloadEfficiencyManEntityWindow_Id');
//						var loadAndUnloadEfficiencyManForm = Ext.getCmp('Foss_baseinfo_loadAndUnloadEfficiencyMan_UpdateLoadAndUnloadEfficiencyManForm_Id');
						var a_window = baseinfo.loadAndUnloadEfficiency.loadAndUnloadEfficiencyMan.windowz;
						a_window.down('form').getForm().reset();
						a_window.hide();
					}
				});
		}
		return this.cancelButton;
	},
	//重置按钮
	resetButton : null,
	getResetButton : function (me) {
		if (this.resetButton == null) {
			this.resetButton = Ext.create('Ext.button.Button', {
					xtype : 'button',
					hidden : false,
					cls : 'yellow_button',
					name : 'reset',
					text : baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.reset'),
					columnWidth : .72,
					width : 55,
					margin : '5 5 5 540',
					handler : function () {
//						var a_updateForm = Ext.getCmp('Foss_baseinfo_loadAndUnloadEfficiencyMan_UpdateLoadAndUnloadEfficiencyManForm_Id');
						var a_updateForm = baseinfo.loadAndUnloadEfficiency.loadAndUnloadEfficiencyMan.windowz.down('form');
						a_updateForm.getForm().reset();
						if (baseinfo.loadAndUnloadEfficiency.loadAndUnloadEfficiencyMan.flagAddModify == 'modify') {
							var a_formInfo = baseinfo.loadAndUnloadEfficiency.loadAndUnloadEfficiencyMan.rawRecord;
							a_updateForm.getForm().loadRecord(a_formInfo);
						}
					}
				});
		}
		return this.resetButton;

	},
	//保存按钮
	saveButton : null,
	getSaveButton : function () {
		if (this.saveButton == null) {
			this.saveButton = Ext.create('Ext.button.Button', {
					cls : 'yellow_button',
					text : baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.save'),
					columnWidth : .72,
					width : 55,
					margin : '5 5 5 5',
					handler : function () {
						var a_isChangeView = true;
						// "../baseinfo/updateLoadAndUnloadEfficiencyMan.action"
						var r_url = "updateLoadAndUnloadEfficiencyMan.action";
						r_url = baseinfo.realPath(r_url);
						var a_url = r_url;
//						var a_form = Ext.getCmp("Foss_baseinfo_loadAndUnloadEfficiencyMan_UpdateLoadAndUnloadEfficiencyManForm_Id");
						var a_form = baseinfo.loadAndUnloadEfficiency.loadAndUnloadEfficiencyMan.windowz.down('form');
						var a_model = Ext.create('Foss.baseinfo.loadAndUnloadEfficiencyMan.LoadAndUnloadEfficiencyManModel', a_form.getForm().getValues());
						// 请求合法性验证
						if (!a_form.getForm().isValid()) {
							return;
						}
						var loadAndUnloadEfficiencyManVo = new Object();
						loadAndUnloadEfficiencyManVo.loadAndUnloadEfficiencyManEntity = new Object();
						loadAndUnloadEfficiencyManVo.loadAndUnloadEfficiencyManEntity = a_model.data
							var params = {
							'loadAndUnloadEfficiencyManVo' : loadAndUnloadEfficiencyManVo
						};
						// 如果是新增
						if (baseinfo.loadAndUnloadEfficiency.loadAndUnloadEfficiencyMan.flagAddModify == 'add') {
							// "../baseinfo/addLoadAndUnloadEfficiencyMan.action"
							r_url = "addLoadAndUnloadEfficiencyMan.action";
							r_url = baseinfo.realPath(r_url);
							a_url = r_url;
							var loadAndUnloadEfficiencyManVoCondition = new Object();
							loadAndUnloadEfficiencyManVoCondition.loadAndUnloadEfficiencyManEntity = new Object();
							loadAndUnloadEfficiencyManVoCondition.loadAndUnloadEfficiencyManEntity.orgCode = loadAndUnloadEfficiencyManVo.loadAndUnloadEfficiencyManEntity.orgCode;
							var a_checkParam = {
								'loadAndUnloadEfficiencyManVo' : loadAndUnloadEfficiencyManVoCondition
							};
							// "../baseinfo/queryLoadAndUnloadEfficiencyManExactByEntity.action"
							r_url = "queryLoadAndUnloadEfficiencyManExactByEntity.action";
							r_url = baseinfo.realPath(r_url);
							// 重复性验证
							var a_isRepeat = false;
							Ext.Ajax.request({
								url : r_url,
								jsonData : a_checkParam,
								async : false, //ASYNC 是否异步( TRUE 异步 , FALSE 同步)
								success : function (response) {
									var json = Ext.decode(response.responseText);
									var a_list = json.loadAndUnloadEfficiencyManVo.loadAndUnloadEfficiencyManList;
									if (a_list == null || a_list.length == 0) {
										a_isRepeat = false;
									} else {
										a_isRepeat = true;
										a_isChangeView = false;
									}
								},
								exception : function (response) {
									var json = Ext.decode(response.responseText);
								}
							});
							if (a_isRepeat) {
								//var a_field = Ext.getCmp("Foss_baseinfo_loadAndUnloadEfficiencyMan_AddLoadAndUnloadEfficiencyManFormSecondCodeName_Id");
								var a_orgName = "";
								if (a_form) {
									var a_org = a_form.getForm().findField("orgCode");
									if (a_org) {
										a_orgName = a_org.getRawValue();
									}
								}
								Ext.Msg.alert(baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.tips'), a_orgName + baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.humanEfficiencyStandardsExist'));
								return "";
							}
						}
						//发送新增或修改的Ajax请求.
						Ext.Ajax.request({
							url : a_url,
							jsonData : params,
							//作废成功
							success : function (response) {
								var json = Ext.decode(response.responseText);
								baseinfo.loadAndUnloadEfficiency.loadAndUnloadEfficiencyMan.pagingBar.moveFirst();

								var tipMsg = baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.saveSuccessful');
								if (json.message) {
									tipMsg = json.message;
								}
								json.message = baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.addLoadAndUnloadEfficiencyVehicleIsSuccessed');
								top.Ext.MessageBox.alert(baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.saveSuccessful'), json.message);
							},
							//保存失败
							exception : function (response) {
								a_isChangeView = false;
								var json = Ext.decode(response.responseText);
								top.Ext.MessageBox.alert(baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.saveFail'), json.message);
							}
						});
						if (a_isChangeView) {
//							var a_conditionForm = Ext.getCmp("Foss_baseinfo_loadAndUnloadEfficiencyMan_UpdateLoadAndUnloadEfficiencyManForm_Id");
							// 将表单中的数据清空
//							a_conditionForm.getForm().reset();
//							var a_win = Ext.getCmp("Foss_baseinfo_loadAndUnloadEfficiencyMan_UpdateLoadAndUnloadEfficiencyManEntityWindow_Id");
							var a_win = baseinfo.loadAndUnloadEfficiency.loadAndUnloadEfficiencyMan.windowz;
							a_win.down('form').getForm().reset();
							a_win.setVisible(false)
						}

					}
				});
		}
		return this.saveButton;
	},
	initComponent : function (config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.items = [{
				allowBlank : false,
				xtype : 'dynamicorgcombselector',
				type : 'ORG',
				name : 'orgCode',
				fieldLabel : baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.orgName'),
				salesDepartment : 'Y',
				transferCenter : 'Y',
				margin : '5 20 5 10',
				columnWidth : .48,
				labelWidth : 160
			}, {
				allowBlank : false,
				xtype : 'numberfield',
				decimalPrecision : 4,
				name : 'loadWeightStd',
				fieldLabel : baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.loadWeightStandardDPRT'),
				margin : '5 20 5 10',
				minValue : 0,
				columnWidth : .9,
				labelWidth : 160
			}, {
				allowBlank : false,
				xtype : 'numberfield',
				decimalPrecision : 4,
				name : 'loadVolumeStd',
				fieldLabel : baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.unloadWeightStandard'),
				margin : '5 20 5 10',
				minValue : 0,
				columnWidth : .9,
				labelWidth : 160
			},
			Ext.create('Ext.container.Container', {
				width : 740,
				items : [
					me.getCancelButton(me),
					me.getResetButton(me),
					me.getSaveButton()
				]
			})
		];
		me.callParent([cfg]);
	}
});

//==============================================================================================
// 下面是详细实现
//==============================================================================================


/**
 * 装卸车标准-吨-人天 界面数据模型定义
 */
Ext.define('Foss.baseinfo.loadAndUnloadEfficiencyMan.LoadAndUnloadEfficiencyManModel', {
	extend : 'Ext.data.Model',
	fields : [
		// ID
		{
			name : 'id',
			type : 'string'
		},
		// 部门编码
		{
			name : 'orgCode',
			type : 'string'
		},
		// 部门名称
		{
			name : 'orgName',
			type : 'string'
		},
		// 平均人天装车吨数
		{
			name : 'loadWeightStd',
			type : 'string',
			defaultValue : null
		},
		// 平均人天卸车吨数
		{
			name : 'loadVolumeStd',
			type : 'string',
			defaultValue : null
		},
		// 创建时间
		{
			name : 'createTime',
			type : 'date'
		},
		// 更新时间
		{
			name : 'modifyTime',
			type : 'date'
		},
		// 是否启用
		{
			name : 'active',
			type : 'string'
		},
		// 创建人
		{
			name : 'createUserCode',
			type : 'string'
		},
		// 更新人
		{
			name : 'modifyUserCode',
			type : 'string'
		}
	]
});

//查询条件面板
Ext.define('Foss.baseinfo.loadAndUnloadEfficiencyMan.SelectConditionForm', {
	extend : 'Ext.form.Panel',
	id : 'Foss_baseinfo_loadAndUnloadEfficiencyMan_SelectConditionForm_Id',
	itemId : 'Foss_baseinfo_loadAndUnloadEfficiencyMan_SelectConditionForm_ItemId',
	layout : 'column',
	frame : true,
	columnWidth : 0.98,
	title : baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.queryCondition'),
	defaults : {
		xtype : 'textfield',
		readOnly : false,
		margin : '5 5 5 10',
		anchor : '90%'
	},

	initComponent : function (config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.items = [
			Ext.create('Ext.container.Container', {
				columnWidth : .99,
				layout : 'column',
				defaultType : 'textfield',
				items : [{
						xtype : 'dynamicorgcombselector',
						type : 'ORG',
						name : 'orgCode',
						fieldLabel : baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.orgName'),
						salesDepartment : 'Y',
						transferCenter : 'Y',
						margin : '5 20 5 0',
						columnWidth : .48,
						labelWidth : 160
					}, Ext.create('Ext.container.Container', {
						columnWidth : .4,
						layout : 'column',
						items : [{
								columnWidth : 0.00075,
								height : 0,
								xtype : 'container',
								style : 'padding-top:20px;border:none',
								hide : true
							}, Ext.create('Ext.button.Button', {
								xtype : 'button',
								cls : 'yellow_button',
								name : 'buttonReset',
								text : baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.reset'),
								disabled:!baseinfo.loadAndUnloadEfficiency.isPermission('loadAndUnloadEfficiencyindex/loadAndUnloadPeopleQueryButton'),
								hidden:!baseinfo.loadAndUnloadEfficiency.isPermission('loadAndUnloadEfficiencyindex/loadAndUnloadPeopleQueryButton'),
								columnWidth : .12,
								margin : '0,10,0,0',
								width : 20,
								handler : function () {
									var a_conditionForm = Ext.getCmp("Foss_baseinfo_loadAndUnloadEfficiencyMan_SelectConditionForm_Id");
									// 将表单中的数据清空
									a_conditionForm.getForm().reset();
								}
							}), {
								columnWidth : 0.010,
								height : 0,
								xtype : 'container',
								style : 'padding-top:20px;border:none',
								hide : true
							}, Ext.create('Ext.button.Button', {
								xtype : 'button',
								cls : 'yellow_button',
								name : 'buttonSelect',
								text : baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.query'),
								disabled:!baseinfo.loadAndUnloadEfficiency.isPermission('loadAndUnloadEfficiencyindex/loadAndUnloadPeopleQueryButton'),
								hidden:!baseinfo.loadAndUnloadEfficiency.isPermission('loadAndUnloadEfficiencyindex/loadAndUnloadPeopleQueryButton'),
								columnWidth : .12,
								margin : '0,10,0,30',
								width : 20,

								//查询按钮的响应事件
								handler : function () {
									baseinfo.loadAndUnloadEfficiency.loadAndUnloadEfficiencyMan.pagingBar.moveFirst();
								}
							})
						]
					})
				]
			})
		];
		me.callParent([cfg]);
	}
});

//查询的显示表格
Ext.define('Foss.baseinfo.loadAndUnloadEfficiencyMan.LoadAndUnloadEfficiencyManGrid', {
	extend : 'Ext.grid.Panel',
	id : 'Foss_baseinfo_loadAndUnloadEfficiencyMan_LoadAndUnloadEfficiencyManGrid_Id',
	cls : 'autoHeight',
	bodyCls : 'autoHeight',
	columnWidth : .99,

	// 设置表格不可排序
	sortableColumns : false,
	// 去掉排序的倒三角
	enableColumnHide : false,
	// 设置“如果查询的结果为空，则提示”
	emptyText : baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.queryResultIsNull'),

	//增加滚动条
	autoScroll : false,
	stripeRows : true, // 交替行效果
	collapsible : true,
	animCollapse : true,
	selType : "rowmodel", // 选择类型设置为行选择
	store : null,
	//selModel : Ext.create('Ext.selection.RowModel'),
	selModel : Ext.create('Ext.selection.CheckboxModel'),

	columns : [{
			xtype : 'actioncolumn',
			flex : 1,
			text : baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.operate'),
			align : 'center',
			items : [{
					iconCls : 'deppon_icons_edit',
					tooltip : baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.update'),
					disabled:!baseinfo.loadAndUnloadEfficiency.isPermission('loadAndUnloadEfficiencyindex/loadAndUnloadPeopleUpdateButton'),
					handler : function (grid, rowIndex, colIndex) {
						baseinfo.loadAndUnloadEfficiency.loadAndUnloadEfficiencyMan.flagAddModify = 'modify';
//						var a_updateForm = Ext.getCmp('Foss_baseinfo_loadAndUnloadEfficiencyMan_UpdateLoadAndUnloadEfficiencyManForm_Id');
						var a_updateForm = baseinfo.loadAndUnloadEfficiency.loadAndUnloadEfficiencyMan.windowz.down('form');
						var rowInfo = grid.getStore().getAt(rowIndex);
						a_updateForm.getForm().findField('orgCode').setReadOnly(true);
						a_updateForm.getForm().findField('orgCode').store.add({
							'code' : rowInfo.data.orgCode,
							'name' : rowInfo.data.orgName
						});
						a_updateForm.loadRecord(rowInfo);
						//将表格里面被选中的信息先保存起来，重置的时候使用
						baseinfo.loadAndUnloadEfficiency.loadAndUnloadEfficiencyMan.rawRecord = rowInfo;

						var a_win = baseinfo.loadAndUnloadEfficiency.loadAndUnloadEfficiencyMan.windowz;
						a_win.setTitle(baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.updateLoadManEfficiencyStandard'));
						a_win.show();
					}
				}, {
					iconCls : 'deppon_icons_cancel',
					tooltip : baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.void'),
					disabled:!baseinfo.loadAndUnloadEfficiency.isPermission('loadAndUnloadEfficiencyindex/loadAndUnloadPeopleVoidButton'),
					handler : function (grid, rowIndex, colIndex) {
						Ext.MessageBox.buttonText.yes = baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.sure');
						Ext.MessageBox.buttonText.no = baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.cancel');
						Ext.Msg.confirm(baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.tipInfo'), baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.sureDelete'), function (btn, text) {
							if (btn == 'yes') {
								var msgTip = baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.deleteSuccess');

								// 获得当前选择的数据
								var rowInfo = grid.getStore().getAt(rowIndex).data;
								//发送作废结点的Ajax请求.
								var loadAndUnloadEfficiencyManVo = new Object();
								loadAndUnloadEfficiencyManVo.loadAndUnloadEfficiencyManEntity = new Object();
								loadAndUnloadEfficiencyManVo.loadAndUnloadEfficiencyManEntity.orgCode = rowInfo.orgCode;
								var params = {
									'loadAndUnloadEfficiencyManVo' : loadAndUnloadEfficiencyManVo
								};

								// "../baseinfo/deleteLoadAndUnloadEfficiencyMan.action"
								r_url = "deleteLoadAndUnloadEfficiencyMan.action";
								r_url = baseinfo.realPath(r_url);
								Ext.Ajax.request({
									url : r_url,
									jsonData : params,
									success : function (response) {
										var json = Ext.decode(response.responseText);
										grid.getStore().removeAt(rowIndex);
										baseinfo.loadAndUnloadEfficiency.loadAndUnloadEfficiencyMan.pagingBar.moveFirst();
									},
									exception : function (response) {
										var json = Ext.decode(response.responseText);

										msgTip = baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.deleteFailure');
									}
								});
								Ext.Msg.alert(baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.tips'), msgTip);
							}
						});

					}
				}
			]
		}, {
			hidden : true,
			dataIndex : 'orgCode',
			text : baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.deptCode'),
			margin : '5 20 5 10'
		}, {
			dataIndex : 'orgName',
			text : baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.orgName'),
			xtype : 'ellipsiscolumn',
			align : 'left',
			flex : 3
		}, {
			dataIndex : 'loadWeightStd',
			text : baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.loadWeightStandardDPRT'),
			xtype : 'ellipsiscolumn',
			align : 'center',
			flex : 2
		}, {
			dataIndex : 'loadVolumeStd',
			text : baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.unloadWeightStandard'),
			xtype : 'ellipsiscolumn',
			align : 'center',
			flex : 2
		}
	],

	getTbar : function () {
		var me = this;
		return [{
				text : baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.add'),
				disabled:!baseinfo.loadAndUnloadEfficiency.isPermission('loadAndUnloadEfficiencyindex/loadAndUnloadPeopleAddButton'),
				hidden:!baseinfo.loadAndUnloadEfficiency.isPermission('loadAndUnloadEfficiencyindex/loadAndUnloadPeopleAddButton'),
				//hidden:!pricing.isPermission('../pricing/saveRole.action'),
				handler : function () {
					baseinfo.loadAndUnloadEfficiency.loadAndUnloadEfficiencyMan.flagAddModify = 'add'
//					var a_win = Ext.getCmp("Foss_baseinfo_loadAndUnloadEfficiencyMan_UpdateLoadAndUnloadEfficiencyManEntityWindow_Id");
					var a_win = baseinfo.loadAndUnloadEfficiency.loadAndUnloadEfficiencyMan.windowz;
					a_win.setTitle(baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.addLoadAndUnloadManEfficiencyStandard'));
					a_win.setVisible(true);
//					var a_form = Ext.getCmp("Foss_baseinfo_loadAndUnloadEfficiencyMan_UpdateLoadAndUnloadEfficiencyManForm_Id");
					var a_form = a_win.down('form');
					// 设置部门编码可以修改
					a_form.getForm().findField('orgCode').setReadOnly(false);
					a_form.getForm().reset();
				}
			}, '-', {
				text : baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.void'), //作废
				disabled:!baseinfo.loadAndUnloadEfficiency.isPermission('loadAndUnloadEfficiencyindex/loadAndUnloadPeopleVoidButton'),
				hidden:!baseinfo.loadAndUnloadEfficiency.isPermission('loadAndUnloadEfficiencyindex/loadAndUnloadPeopleVoidButton'),
				//hidden:!pricing.isPermission('../pricing/deleteRole.action'),
				handler : function () {
					baseinfo.loadAndUnloadEfficiency.loadAndUnloadEfficiencyMan.deleteLoadAndUnloadEfficiencyMan(true, null);
				}
			}
		];
	},

	constructor : function (config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.store = Ext.create('Ext.data.Store', {
				model : 'Foss.baseinfo.loadAndUnloadEfficiencyMan.LoadAndUnloadEfficiencyManModel',
				pageSize : 20,
				autoLoad : false,
				proxy : {
					type : 'ajax',
					actionMethods : 'POST',
					// '../baseinfo/queryLoadAndUnloadEfficiencyManExactByEntity.action'
					url : baseinfo.realPath("queryLoadAndUnloadEfficiencyManExactByEntity.action"),
					//定义一个读取器
					reader : {
						type : 'json',
						root : 'loadAndUnloadEfficiencyManVo.loadAndUnloadEfficiencyManList',
						totalProperty : 'totalCount'
					}
				},
				constructor : function (config) {
					var me = this,
					cfg = Ext.apply({}, config);
					me.callParent([cfg]);
				},
				listeners : {
					beforeload : function (store, operation, eOpts) {
						var queryForm = baseinfo.loadAndUnloadEfficiency.loadAndUnloadEfficiencyMan.queryForm;
						if (queryForm != null) {
							var queryParams = queryForm.getValues();
							Ext.apply(operation, {
								params : {
									'loadAndUnloadEfficiencyManVo.loadAndUnloadEfficiencyManEntity.orgCode' : queryParams.orgCode
								}
							});
						}
					}
				}
			});
		me.bbar = Ext.create('Deppon.StandardPaging', {
				store : me.store
			});
		//添加头部按钮
		me.tbar = me.getTbar();
		baseinfo.loadAndUnloadEfficiency.loadAndUnloadEfficiencyMan.pagingBar = me.bbar;
		me.callParent([cfg]);
	}
});

//右边模块-查询结果面板
Ext.define('Foss.baseinfo.loadAndUnloadEfficiencyMan.SelectResultForm', {
	extend : 'Ext.form.Panel',
	id : 'Foss_baseinfo_loadAndUnloadEfficiencyMan_SelectResultForm_Id',
	itemId : 'Foss_baseinfo_loadAndUnloadEfficiencyMan_SelectResultForm_ItemId',
	layout : 'column',
	frame : true,
	hidden : false,
	columnWidth : 0.98,
	cls : 'autoHeight',
	bodyCls : 'autoHeight',
	title : baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.loadAndUploadManEfficiencyStandard'),
	defaults : {
		readOnly : false,
		margin : '5 5 5 10',
		anchor : '90%'
	},

	initComponent : function (config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.items = [
			Ext.create('Ext.container.Container', {
				columnWidth : .99,
				layout : 'column',
				items : [
					Ext.create('Foss.baseinfo.loadAndUnloadEfficiencyMan.LoadAndUnloadEfficiencyManGrid'),
//					Ext.create('Ext.button.Button', {
//						xtype : 'button',
//						hidden : false,
//						//cls:'yellow_button',
//						name : 'loadAndUnloadEfficiencyMan_deletes',
//						text : baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.void'),
//						hidden:!baseinfo.loadAndUnloadEfficiency.isPermission('loadAndUnloadEfficiencyindex/loadAndUnloadPeopleVoidButton'),
//						columnWidth : .12,
//						margin : '0,10,0,0',
//						width : 30,
//						handler : function () {
//							baseinfo.loadAndUnloadEfficiency.loadAndUnloadEfficiencyMan.deleteLoadAndUnloadEfficiencyMan(true, null);
//						}
//					})
				]
			})
		];
		me.callParent([cfg]);
	}
});

Ext.define('Foss.baseinfo.loadAndUnloadEfficiencyMan.MainPanel', {
	extend : 'Ext.form.Panel',
	id : 'Foss_baseinfo_loadAndUnloadEfficiencyMan_MainPanel_Id',
	itemId : 'Foss_baseinfo_loadAndUnloadEfficiencyMan_MainPanel_ItemId',
	title : baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.loadManEfficiencyStandard'),
	cls : 'autoHeight',
	bodyCls : 'autoHeight',
	defaultType : 'textfield',
	layout : 'column',
	frame : true,
	columnWidth : 0.72,
	margin : '50 2 2 2',
	defaults : {
		readOnly : false,
		margin : '5 5 5 10',
		anchor : '90%',
		columnWidth : 1,
		labelWidth : 120
	},
	initComponent : function (config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.items = [
			Ext.create('Foss.baseinfo.loadAndUnloadEfficiencyMan.SelectConditionForm'),
			Ext.create('Foss.baseinfo.loadAndUnloadEfficiencyMan.SelectResultForm')
		];
		me.callParent([cfg]);
	}
});

//==============================================================================================
// 装卸车人力效率标准新增，修改的窗口
//==============================================================================================
Ext.define('Foss.baseinfo.loadAndUnloadEfficiencyMan.UpdateLoadAndUnloadEfficiencyManEntityWindow', {
	extend : 'Ext.window.Window',
//	id : 'Foss_baseinfo_loadAndUnloadEfficiencyMan_UpdateLoadAndUnloadEfficiencyManEntityWindow_Id',
	width : 800,
	closeAction : 'hide',
	layout : 'column',
	modal : true,
	constructor : function (config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.items = [
			Ext.create('Foss.baseinfo.loadAndUnloadEfficiencyMan.UpdateLoadAndUnloadEfficiencyManForm')
		];
		me.callParent([cfg]);
	}
})

/**
 * 装卸车标准-吨-时间_主页面
 */
baseinfo.loadAndUnloadEfficiency.loadAndUnloadEfficiencyVehicle = new Object();
baseinfo.loadAndUnloadEfficiency.loadAndUnloadEfficiencyVehicle.vehicleTypeStore = null;

baseinfo.loadAndUnloadEfficiency.loadAndUnloadEfficiencyVehicle.getDataDictionaryCombo = function () {
	var config = {
		allowBlank : false,
		forceSelection : true,
		name : 'vehicleTypeLength',
		fieldLabel : baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.vehicleLength'),
		margin : '5 20 5 10',
		columnWidth : .48,
		labelWidth : 160
	};
	var store = baseinfo.loadAndUnloadEfficiency.loadAndUnloadEfficiencyVehicle.getVehicleTypeStore(),
	cfg = Ext.apply(config, {
			store : store,
			displayField : 'vehicleLength',
			valueField : 'vehicleLengthCode'
		});
	return Ext.create('Ext.form.ComboBox', config);

},

/**

 * 获取 车型长信息 store
 *
 * @param
 * @returns
 */

baseinfo.loadAndUnloadEfficiency.loadAndUnloadEfficiencyVehicle.getVehicleTypeStore = function () {
	if (baseinfo.loadAndUnloadEfficiency.loadAndUnloadEfficiencyVehicle.vehicleTypeStore != null) {
		return baseinfo.loadAndUnloadEfficiency.loadAndUnloadEfficiencyVehicle.vehicleTypeStore;
	}

	var a_vehicleTypeEntityList;

	// "../baseinfo/queryLeasedVehicleTypeAll.action"
	var r_url = "queryLeasedVehicleTypeAll.action";
	r_url = baseinfo.realPath(r_url);
	//Ajax请求车型长信息
	Ext.Ajax.request({
		url : r_url,
		async : false,
		success : function (response) {
			var result = Ext.decode(response.responseText);
			a_vehicleTypeEntityList = result.loadAndUnloadEfficiencyVehicleVo.vehicleTypeEntityList;

		},
		exception : function (response) {
			var result = Ext.decode(response.responseText);
			Ext.MessageBox.alert(baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.tip'), baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.deleteFail') + result.message);
		}
	});

	var data = a_vehicleTypeEntityList;
	if (!Ext.isEmpty(data)) {
		var json = {
			fields : ['vehicleLengthCode', 'vehicleLength'],
			data : data
		};

		/**
		if(!Ext.isEmpty(id)){
		json["id"]=id;
		}*/
		baseinfo.loadAndUnloadEfficiency.loadAndUnloadEfficiencyVehicle.vehicleTypeStore = Ext.create('Ext.data.Store', json);
		return baseinfo.loadAndUnloadEfficiency.loadAndUnloadEfficiencyVehicle.vehicleTypeStore;
	} else {
		baseinfo.loadAndUnloadEfficiency.loadAndUnloadEfficiencyVehicle.vehicleTypeStore = [];
		return baseinfo.loadAndUnloadEfficiency.loadAndUnloadEfficiencyVehicle.vehicleTypeStore;
	}
},

baseinfo.loadAndUnloadEfficiency.loadAndUnloadEfficiencyVehicle.deleteLoadAndUnloadEfficiencyVehicle = function (isMore, p_code) {
	Ext.MessageBox.buttonText.yes = baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.sure');
	Ext.MessageBox.buttonText.no = baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.cancel');

	// 获得国际化字符串
	var tip1 = baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.tipInfo');
	var tip2 = baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.areYouSureToVoid');
	Ext.Msg.confirm(tip1, tip2,
		function (btn, text) {
		if (btn == 'yes') {
			var ids = '';
			if (isMore) {
				var a_grid = Ext.getCmp('Foss_baseinfo_loadAndUnloadEfficiencyVehicle_LoadAndUnloadEfficiencyVehicleGrid_Id');
				// 获取选中的记录
				var rowObjs = a_grid.getSelectionModel().getSelection();

				if (rowObjs.length <= 0) {
					Ext.Msg.alert(baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.tip'), baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.pleaseSelectDeletedRecord'));
					return;
				}
				// 将id通过,分隔
				for (var i = 0; i < rowObjs.length; i++) {
					ids = ids + rowObjs[i].data.virtualCode + ",";
				}
				ids = ids.substring(0, ids.length - 1);
			} else {
				ids = p_code
			}

			//发送作废结点的Ajax请求.
			var loadAndUnloadEfficiencyVehicleVo = new Object();
			loadAndUnloadEfficiencyVehicleVo.loadAndUnloadEfficiencyVehicleEntity = new Object();
			loadAndUnloadEfficiencyVehicleVo.loadAndUnloadEfficiencyVehicleEntity.virtualCode = ids;
			var params = {
				'loadAndUnloadEfficiencyVehicleVo' : loadAndUnloadEfficiencyVehicleVo
			};

			// "../baseinfo/deleteLoadAndUnloadEfficiencyVehicleMore.action"
			var r_url = "deleteLoadAndUnloadEfficiencyVehicleMore.action";
			r_url = baseinfo.realPath(r_url);
			Ext.Ajax.request({
				url : r_url,
				jsonData : params,
				//作废成功
				success : function (response) {
					var json = Ext.decode(response.responseText);
					//删除完成后，将“从表格删除”改为刷新表格
					//a_grid.getStore().remove(rowObjs);
					baseinfo.loadAndUnloadEfficiency.loadAndUnloadEfficiencyVehicle.pagingBar.moveFirst();

					var tipMsg = baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.voidSuccessNo');
					if (json.message) {
						tipMsg = json.message;
					}
					top.Ext.MessageBox.alert(baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.tips'), baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.voidSuccessNo'));
				},
				//保存失败
				exception : function (response) {
					var json = Ext.decode(response.responseText);
					top.Ext.MessageBox.alert(baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.deleteFailure'), json.message);
				}
			});
		}
	});
}
////////////////////////////////////////////////////////////////////
// 全局变量（模块名loadAndUnloadEfficiencyVehicle） end
////////////////////////////////////////////////////////////////////

// 装卸车效率标准查询-主面板
Ext.define('Foss.baseinfo.loadAndUnloadEfficiencyVehicle.SelectForm', {
	extend : 'Ext.form.Panel',
	id : 'Foss_baseinfo_loadAndUnloadEfficiencyVehicle_SelectForm_Id',
	itemId : 'Foss_baseinfo_loadAndUnloadEfficiencyVehicle_SelectForm_ItemId',
	//height: 80,
	cls : 'autoHeight',
	bodyCls : 'autoHeight',
	defaultType : 'textfield',
	layout : 'column',
	frame : true,
	columnWidth : 0.72,
	defaults : {
		readOnly : false,
		margin : '5 5 5 10',
		anchor : '90%',
		columnWidth : 1,
		labelWidth : 120
	},
	initComponent : function (config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.items = [
			Ext.create('Foss.baseinfo.loadAndUnloadEfficiencyVehicle.SelectConditionForm'),
			Ext.create('Foss.baseinfo.loadAndUnloadEfficiencyVehicle.SelectResultForm')
		];
		me.callParent([cfg]);
	}
});

// 装卸车标准时间新增表单
Ext.define('Foss.baseinfo.loadAndUnloadEfficiencyVehicle.UpdateLoadAndUnloadEfficiencyVehicleForm', {
	extend : 'Ext.form.Panel',
//	id : 'Foss_baseinfo_loadAndUnloadEfficiencyVehicle_UpdateLoadAndUnloadEfficiencyVehicleForm_Id',
	// title: baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.addLoadAndUploadEfficiencyStandard'),
	frame : true,
	hidden : false,
	defaultType : 'textfield',
	layout : 'column',
	// layout: {type: 'table',columns: 1},
	columnWidth : 0.99,
	defaults : {
		readOnly : false,
		margin : '5 5 5 10',
		anchor : '90%',
		columnWidth : 1,
		labelWidth : 120
	},

	//取消按钮
	cancelButton : null,
	getCancelButton : function (me) {
		if (this.cancelButton == null) {
			this.cancelButton = Ext.create('Ext.button.Button', {
					xtype : 'button',
					//cls:'yellow_button',
					text : baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.cancel'),
					columnWidth : .12,
					width : 55,
					hidden : false,
					handler : function () {
//						var a_window = Ext.getCmp('Foss_baseinfo_loadAndUnloadEfficiencyVehicle_UpdateLoadAndUnloadEfficiencyVehicleEntityWindow_Id');
//						var loadAndUnloadEfficiencyVehicleForm = Ext.getCmp('Foss_baseinfo_loadAndUnloadEfficiencyVehicle_UpdateLoadAndUnloadEfficiencyVehicleForm_Id');
						var a_window = baseinfo.loadAndUnloadEfficiency.loadAndUnloadEfficiencyVehicle.windowz;
						a_window.down('form').getForm().reset();
						a_window.hide();
					}
				});
		}
		return this.cancelButton;
	},
	//重置按钮
	resetButton : null,
	getResetButton : function (me) {
		if (this.resetButton == null) {
			this.resetButton = Ext.create('Ext.button.Button', {
					xtype : 'button',
					hidden : false,
					cls : 'yellow_button',
					name : 'reset',
					text : baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.reset'),
					columnWidth : .72,
					width : 55,
					margin : '5 5 5 440',
					handler : function () {
//						var a_updateForm = Ext.getCmp('Foss_baseinfo_loadAndUnloadEfficiencyVehicle_UpdateLoadAndUnloadEfficiencyVehicleForm_Id');
						var a_updateForm = baseinfo.loadAndUnloadEfficiency.loadAndUnloadEfficiencyVehicle.windowz.down('form');
						a_updateForm.getForm().reset();
						if (baseinfo.loadAndUnloadEfficiency.loadAndUnloadEfficiencyVehicle.flagAddModify == 'modify') {
							var a_formInfo = baseinfo.loadAndUnloadEfficiency.loadAndUnloadEfficiencyVehicle.rawRecord;
							a_updateForm.getForm().loadRecord(a_formInfo);
						}
					}
				});
		}
		return this.resetButton;

	},
	//保存按钮
	saveButton : null,
	getSaveButton : function () {
		if (this.saveButton == null) {
			this.saveButton = Ext.create('Ext.button.Button', {
					cls : 'yellow_button',
					text : baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.save'),
					columnWidth : .72,
					width : 55,
					margin : '5 5 5 5',
					handler : function () {
						var a_isChangeView = true;
						var a_url = '../baseinfo/updateLoadAndUnloadEfficiencyVehicle.action';

						// "../baseinfo/updateLoadAndUnloadEfficiencyVehicle.action"
						var r_url = "updateLoadAndUnloadEfficiencyVehicle.action";
						r_url = baseinfo.realPath(r_url);
						a_url = r_url;

//						var a_form = Ext.getCmp("Foss_baseinfo_loadAndUnloadEfficiencyVehicle_UpdateLoadAndUnloadEfficiencyVehicleForm_Id");
						var a_form = baseinfo.loadAndUnloadEfficiency.loadAndUnloadEfficiencyVehicle.windowz.down('form');
						var a_model = Ext.create('Foss.baseinfo.loadAndUnloadEfficiencyVehicle.LoadAndUnloadEfficiencyVehicleModel', a_form.getForm().getValues());
						// 请求合法性验证
						if (!a_form.getForm().isValid()) {
							return;
						}

						var loadAndUnloadEfficiencyVehicleVo = new Object();
						loadAndUnloadEfficiencyVehicleVo.loadAndUnloadEfficiencyVehicleEntity = new Object();
						loadAndUnloadEfficiencyVehicleVo.loadAndUnloadEfficiencyVehicleEntity = a_model.data

							var params = {
							'loadAndUnloadEfficiencyVehicleVo' : loadAndUnloadEfficiencyVehicleVo
						};

						r_url = "updateLoadAndUnloadEfficiencyVehicle.action";
						r_url = baseinfo.realPath(r_url);
						a_url = r_url;
						// 如果是新增
						if (baseinfo.loadAndUnloadEfficiency.loadAndUnloadEfficiencyVehicle.flagAddModify == 'add') {
							// "../baseinfo/addLoadAndUnloadEfficiencyVehicle.action"
							var r_url = "addLoadAndUnloadEfficiencyVehicle.action";
							r_url = baseinfo.realPath(r_url);
							a_url = r_url;

							var loadAndUnloadEfficiencyVehicleVoCondition = new Object();
							loadAndUnloadEfficiencyVehicleVoCondition.loadAndUnloadEfficiencyVehicleEntity = new Object();
							loadAndUnloadEfficiencyVehicleVoCondition.loadAndUnloadEfficiencyVehicleEntity.orgCode = loadAndUnloadEfficiencyVehicleVo.loadAndUnloadEfficiencyVehicleEntity.orgCode;
							loadAndUnloadEfficiencyVehicleVoCondition.loadAndUnloadEfficiencyVehicleEntity.vehicleTypeLength = loadAndUnloadEfficiencyVehicleVo.loadAndUnloadEfficiencyVehicleEntity.vehicleTypeLength;

							var a_checkParams = {
								'loadAndUnloadEfficiencyVehicleVo' : loadAndUnloadEfficiencyVehicleVoCondition
							};

							// "../baseinfo/queryLoadAndUnloadEfficiencyVehicleExactByEntity.action"
							var r_url = "queryLoadAndUnloadEfficiencyVehicleExactByEntity.action";
							r_url = baseinfo.realPath(r_url);
							// 重复性验证
							var a_isRepeat = false;
							Ext.Ajax.request({
								url : r_url,
								jsonData : a_checkParams,
								async : false, //ASYNC 是否异步( TRUE 异步 , FALSE 同步)
								success : function (response) {
									var json = Ext.decode(response.responseText);
									var a_list = json.loadAndUnloadEfficiencyVehicleVo.loadAndUnloadEfficiencyVehicleList;
									if (a_list == null || a_list.length == 0) {
										a_isRepeat = false;
									} else {
										a_isRepeat = true;
										a_isChangeView = false;
									}
								},
								exception : function (response) {
									var json = Ext.decode(response.responseText);
								}
							});

							if (a_isRepeat) {
								//var a_field = Ext.getCmp("Foss_baseinfo_loadAndUnloadEfficiencyVehicle_AddLoadAndUnloadEfficiencyVehicleFormSecondCodeName_Id");
								var a_orgName = "";
								var a_vehicleType = "";
								if (a_form) {
									var a_org = a_form.getForm().findField("orgCode");
									if (a_org) {
										a_orgName = a_org.getRawValue();
									}
									var a_vehicle = a_form.getForm().findField("vehicleTypeLength");
									var a_vehicleType = a_vehicle.getRawValue();

								}
								Ext.Msg.alert(baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.tips'), a_orgName + baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.de') + a_vehicleType + baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.timeEfficiencyStandardsExist'));
								return "";
							}
						}

						//发送新增结点的Ajax请求.
						Ext.Ajax.request({
							url : a_url,
							jsonData : params,
							//作废成功
							success : function (response) {
								var json = Ext.decode(response.responseText);
								baseinfo.loadAndUnloadEfficiency.loadAndUnloadEfficiencyVehicle.pagingBar.moveFirst();
								var tipMsg = baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.saveSuccessful');
								if (json.message) {
									tipMsg = json.message;
								}
								json.message = baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.addLoadAndUnloadEfficiencyTonIsSuccessed');
								top.Ext.MessageBox.alert(baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.saveSuccessful'), json.message);
							},
							//保存失败
							exception : function (response) {
								a_isChangeView = false;
								var json = Ext.decode(response.responseText);
								top.Ext.MessageBox.alert(baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.saveFail'), json.message);
							}
						});

						if (a_isChangeView) {
//							var a_conditionForm = Ext.getCmp("Foss_baseinfo_loadAndUnloadEfficiencyVehicle_UpdateLoadAndUnloadEfficiencyVehicleForm_Id");
							// 将表单中的数据清空
//							a_conditionForm.getForm().reset();
//							var a_win = Ext.getCmp("Foss_baseinfo_loadAndUnloadEfficiencyVehicle_UpdateLoadAndUnloadEfficiencyVehicleEntityWindow_Id");
							var a_win = baseinfo.loadAndUnloadEfficiency.loadAndUnloadEfficiencyVehicle.windowz;
							a_win.down('form').getForm().reset();
							a_win.setVisible(false)
						}

					}
				});
		}
		return this.saveButton;
	},

	initComponent : function (config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.items = [{
				hidden : true,
				name : 'virtualCode',
				fieldLabel : baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.virtualCode'),
				margin : '5 20 5 10',
				columnWidth : .4
			}, {
				columnWidth : .98,
				height : 22,
				layout : 'column',
				xtype : 'container',
				items : [{
						allowBlank : false,
						xtype : 'dynamicorgcombselector',
						type : 'ORG',
						name : 'orgCode',
						fieldLabel : baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.orgName'),
						salesDepartment : 'Y',
						transferCenter : 'Y',
						margin : '5 20 5 0',
						columnWidth : .48,
						labelWidth : 160
					}
				]
			},
			baseinfo.loadAndUnloadEfficiency.loadAndUnloadEfficiencyVehicle.getDataDictionaryCombo(), {
				xtype : 'fieldset',
				columnWidth : 0.5,
				title : '敞篷车（带高栏）',
				collapsible : true,
				defaultType : 'textfield',
				layout : 'column',
				columnWidth : .9,
				items : [{
						allowBlank : false,
						name : 'glCpLoadHours',
						fieldLabel : baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.loadStandardTime'),
						xtype : 'numberfield',
						decimalPrecision : 0,
						minValue : 0,
						margin : '5 20 5 10',
						value : '0',
						labelWidth : 160,
						columnWidth : .4
					}, {
						xtype : 'label',
						text : '(小时)',
						margin : '5 20 5 10',
						columnWidth : .15
					}, {
						allowBlank : false,
						hideLabel : true,
						name : 'glCpLoadMins',
						fieldLabel : '',
						xtype : 'numberfield',
						decimalPrecision : 0,
						margin : '5 20 5 10',
						value : '0',
						maxValue : 59,
						minValue : 0,
						columnWidth : .15
					}, {
						xtype : 'label',
						text : '(分钟)',
						margin : '5 20 5 10',
						columnWidth : .15
					}, {
						allowBlank : false,
						name : 'glCpUnloadHours',
						fieldLabel : baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.unloadStandardTime'),
						xtype : 'numberfield',
						decimalPrecision : 0,
						minValue : 0,
						margin : '5 20 5 10',
						value : '0',
						columnWidth : .4,
						labelWidth : 160
					}, {
						xtype : 'label',
						text : '(小时)',
						margin : '5 20 5 10',
						columnWidth : .15
					}, {
						allowBlank : false,
						hideLabel : true,
						name : 'glCpUnloadMins',
						fieldLabel : '',
						xtype : 'numberfield',
						decimalPrecision : 0,
						margin : '5 20 5 10',
						value : '0',
						maxValue : 59,
						minValue : 0,
						columnWidth : .15
					}, {
						xtype : 'label',
						text : '(分钟)',
						margin : '5 20 5 10',
						columnWidth : .15
					}
				]
			}, {
				xtype : 'fieldset',
				columnWidth : 0.5,
				title : baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.runaboutNoHighHurdles'),
				collapsible : true,
				defaultType : 'textfield',
				layout : 'column',
				columnWidth : .9,
				items : [{
						allowBlank : false,
						name : 'nglCpLoadHours',
						fieldLabel : baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.loadStandardTime'),
						xtype : 'numberfield',
						decimalPrecision : 0,
						minValue : 0,
						margin : '5 20 5 10',
						value : '0',
						labelWidth : 160,
						columnWidth : .4,
						labelWidth : 160
					}, {
						xtype : 'label',
						text : '(小时)',
						margin : '5 20 5 10',
						columnWidth : .15
					}, {
						allowBlank : false,
						hideLabel : true,
						name : 'nglCpLoadMins',
						fieldLabel : '',
						xtype : 'numberfield',
						decimalPrecision : 0,
						margin : '5 20 5 10',
						value : '0',
						maxValue : 59,
						minValue : 0,
						columnWidth : .15
					}, {
						xtype : 'label',
						text : '(分钟)',
						margin : '5 20 5 10',
						columnWidth : .15
					}, {
						allowBlank : false,
						name : 'nglCpUnloadHours',
						fieldLabel : baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.unloadStandardUserTime'),
						xtype : 'numberfield',
						decimalPrecision : 0,
						minValue : 0,
						margin : '5 20 5 10',
						value : '0',
						columnWidth : .4,
						labelWidth : 160
					}, {
						xtype : 'label',
						text : '(小时)',
						margin : '5 20 5 10',
						columnWidth : .15
					}, {
						allowBlank : false,
						hideLabel : true,
						name : 'nglCpUnloadMins',
						fieldLabel : '',
						xtype : 'numberfield',
						decimalPrecision : 0,
						margin : '5 20 5 10',
						value : '0',
						maxValue : 59,
						minValue : 0,
						columnWidth : .15
					}, {
						xtype : 'label',
						text : '(分钟)',
						margin : '5 20 5 10',
						columnWidth : .15
					}
				]
			}, {
				xtype : 'fieldset',
				columnWidth : 0.5,
				title : baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.noRunabout'),
				collapsible : true,
				defaultType : 'textfield',
				layout : 'column',
				columnWidth : .9,
				items : [{
						allowBlank : false,
						name : 'ncpLoadHours',
						fieldLabel : baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.loadStandardTime'),
						xtype : 'numberfield',
						decimalPrecision : 0,
						minValue : 0,
						margin : '5 20 5 10',
						value : '0',
						labelWidth : 160,
						columnWidth : .4
					}, {
						xtype : 'label',
						text : '(小时)',
						margin : '5 20 5 10',
						value : '0',
						columnWidth : .15
					}, {
						allowBlank : false,
						hideLabel : true,
						name : 'ncpLoadMins',
						fieldLabel : '',
						xtype : 'numberfield',
						decimalPrecision : 0,
						margin : '5 20 5 10',
						value : '0',
						maxValue : 59,
						minValue : 0,
						columnWidth : .15
					}, {
						xtype : 'label',
						text : '(分钟)',
						margin : '5 20 5 10',
						columnWidth : .15
					}, {
						allowBlank : false,
						name : 'ncpUnloadHours',
						fieldLabel : baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.unloadStandardTime'),
						xtype : 'numberfield',
						decimalPrecision : 0,
						minValue : 0,
						margin : '5 20 5 10',
						value : '0',
						columnWidth : .4,
						labelWidth : 160
					}, {
						xtype : 'label',
						text : '(小时)',
						margin : '5 20 5 10',
						columnWidth : .15
					}, {
						allowBlank : false,
						hideLabel : true,
						name : 'ncpUnloadMins',
						fieldLabel : '',
						xtype : 'numberfield',
						decimalPrecision : 0,
						margin : '5 20 5 10',
						value : '0',
						maxValue : 59,
						minValue : 0,
						columnWidth : .15
					}, {
						xtype : 'label',
						text : '(分钟)',
						margin : '5 20 5 10',
						columnWidth : .15
					}
				]
			},
			Ext.create('Ext.container.Container', {
				width : 740,
				items : [
					me.getCancelButton(me),
					me.getResetButton(me),
					me.getSaveButton()
				]
			})
		];
		me.callParent([cfg]);
	}
});

//==============================================================================================
// 下面是详细实现
//==============================================================================================


/**
 * 装卸车标准-车-时间 界面数据模型定义
 */
Ext.define('Foss.baseinfo.loadAndUnloadEfficiencyVehicle.LoadAndUnloadEfficiencyVehicleModel', {
	extend : 'Ext.data.Model',
	fields : [
		// ID
		{
			name : 'id',
			type : 'string'
		},
		// 虚拟编码
		{
			name : 'virtualCode',
			type : 'string'
		},
		// 部门编码
		{
			name : 'orgCode',
			type : 'string'
		},
		// 部门名称
		{
			name : 'orgName',
			type : 'string'
		},
		// 车型长编码
		{
			name : 'vehicleTypeLength',
			type : 'string'
		},
		// 车型长度
		{
			name : 'vehicleLength',
			type : 'string'
		},
		// 带高栏敞篷车装车标准用时
		{
			name : 'glCpLoadHours',
			type : 'number'
		},
		// 带高栏敞篷车装车标准用分
		{
			name : 'glCpLoadMins',
			type : 'number'
		},
		// 带高栏敞篷车卸车标准用时
		{
			name : 'glCpUnloadHours',
			type : 'number'
		},
		// 带高栏敞篷车卸车标准用分
		{
			name : 'glCpUnloadMins',
			type : 'number'
		},
		// 不带高栏敞篷车装车标准用时
		{
			name : 'nglCpLoadHours',
			type : 'number'
		},
		// 不带高栏敞篷车装车标准用分
		{
			name : 'nglCpLoadMins',
			type : 'number'
		},
		// 不带高栏敞篷车卸车标准用时
		{
			name : 'nglCpUnloadHours',
			type : 'number'
		},
		// 不带高栏敞篷车卸车标准用分
		{
			name : 'nglCpUnloadMins',
			type : 'number'
		},
		// 非敞篷车装车标准用时
		{
			name : 'ncpLoadHours',
			type : 'number'
		},
		// 非敞篷车装车标准用分
		{
			name : 'ncpLoadMins',
			type : 'number'
		},
		// 非敞篷车卸车标准用时
		{
			name : 'ncpUnloadHours',
			type : 'number'
		},
		// 非敞篷车卸车标准用分
		{
			name : 'ncpUnloadMins',
			type : 'number'
		},
		// 创建时间
		{
			name : 'createTime',
			type : 'date'
		},
		// 更新时间
		{
			name : 'modifyTime',
			type : 'date'
		},
		// 是否启用
		{
			name : 'active',
			type : 'string'
		},
		// 创建人
		{
			name : 'createUserCode',
			type : 'string'
		},
		// 更新人
		{
			name : 'modifyUserCode',
			type : 'string'
		}
	]
});

//查询条件面板
Ext.define('Foss.baseinfo.loadAndUnloadEfficiencyVehicle.SelectConditionForm', {
	extend : 'Ext.form.Panel',
	id : 'Foss_baseinfo_loadAndUnloadEfficiencyVehicle_SelectConditionForm_Id',
	itemId : 'Foss_baseinfo_loadAndUnloadEfficiencyVehicle_SelectConditionForm_ItemId',
	layout : 'column',
	frame : true,
	columnWidth : 0.98,
	title : baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.queryCondition'),
	defaults : {
		xtype : 'textfield',
		readOnly : false,
		margin : '5 5 5 10',
		anchor : '90%'
	},

	initComponent : function (config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.items = [
			Ext.create('Ext.container.Container', {
				columnWidth : .99,
				layout : 'column',
				defaultType : 'textfield',
				items : [{
						xtype : 'dynamicorgcombselector',
						type : 'ORG',
						name : 'orgCode',
						fieldLabel : baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.orgName'),
						salesDepartment : 'Y',
						transferCenter : 'Y',
						margin : '5 20 5 10',
						columnWidth : .48,
						labelWidth : 160
					}, Ext.create('Ext.container.Container', {
						columnWidth : .4,
						layout : 'column',
						items : [{
								columnWidth : 0.00075,
								height : 0,
								xtype : 'container',
								style : 'padding-top:20px;border:none',
								hide : true
							}, Ext.create('Ext.button.Button', {
								xtype : 'button',
								cls : 'yellow_button',
								name : 'buttonReset',
								text : baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.reset'),
								disabled:!baseinfo.loadAndUnloadEfficiency.isPermission('loadAndUnloadEfficiencyindex/loadAndUnloadEfficiencyTimeQueryButton'),
								hidden:!baseinfo.loadAndUnloadEfficiency.isPermission('loadAndUnloadEfficiencyindex/loadAndUnloadEfficiencyTimeQueryButton'),
								columnWidth : .12,
								margin : '0,10,0,0',
								width : 20,
								handler : function () {
									var a_conditionForm = Ext.getCmp("Foss_baseinfo_loadAndUnloadEfficiencyVehicle_SelectConditionForm_Id");
									// 将表单中的数据清空
									a_conditionForm.getForm().reset();
								}
							}), {
								columnWidth : 0.010,
								height : 0,
								xtype : 'container',
								style : 'padding-top:20px;border:none',
								hide : true
							}, Ext.create('Ext.button.Button', {
								xtype : 'button',
								cls : 'yellow_button',
								name : 'buttonSelect',
								text : baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.query'),
								disabled:!baseinfo.loadAndUnloadEfficiency.isPermission('loadAndUnloadEfficiencyindex/loadAndUnloadEfficiencyTimeQueryButton'),
								hidden:!baseinfo.loadAndUnloadEfficiency.isPermission('loadAndUnloadEfficiencyindex/loadAndUnloadEfficiencyTimeQueryButton'),
								columnWidth : .12,
								margin : '0,10,0,30',
								width : 20,

								//查询按钮的响应事件
								handler : function () {
									baseinfo.loadAndUnloadEfficiency.loadAndUnloadEfficiencyVehicle.pagingBar.moveFirst();
								}
							})
						]
					})
				]
			})
		];
		me.callParent([cfg]);
	}
});

//查询的显示表格
Ext.define('Foss.baseinfo.loadAndUnloadEfficiencyVehicle.LoadAndUnloadEfficiencyVehicleGrid', {
	extend : 'Ext.grid.Panel',
	id : 'Foss_baseinfo_loadAndUnloadEfficiencyVehicle_LoadAndUnloadEfficiencyVehicleGrid_Id',
	cls : 'autoHeight',
	bodyCls : 'autoHeight',
	columnWidth : .99,
	//增加滚动条
	autoScroll : true,
	stripeRows : true, // 交替行效果
	collapsible : true,
	animCollapse : true,
	selType : "rowmodel", // 选择类型设置为行选择
	store : null,
	//selModel : Ext.create('Ext.selection.RowModel'),
	selModel : Ext.create('Ext.selection.CheckboxModel'),

	columns : [{
			xtype : 'actioncolumn',
			flex : 1,
			text : baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.operate'),
			align : 'center',
			items : [{
					iconCls : 'deppon_icons_edit',
					tooltip : baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.update'),
					disabled:!baseinfo.loadAndUnloadEfficiency.isPermission('loadAndUnloadEfficiencyindex/loadAndUnloadEfficiencyTimeUpdateButton'),
					handler : function (grid, rowIndex, colIndex) {
						baseinfo.loadAndUnloadEfficiency.loadAndUnloadEfficiencyVehicle.flagAddModify = 'modify';
//						var a_updateForm = Ext.getCmp('Foss_baseinfo_loadAndUnloadEfficiencyVehicle_UpdateLoadAndUnloadEfficiencyVehicleForm_Id');
						var a_updateForm = baseinfo.loadAndUnloadEfficiency.loadAndUnloadEfficiencyVehicle.windowz.down('form');
						var rowInfo = grid.getStore().getAt(rowIndex);
						a_updateForm.getForm().findField('orgCode').setReadOnly(true);
						a_updateForm.getForm().findField('orgCode').store.add({
							'code' : rowInfo.data.orgCode,
							'name' : rowInfo.data.orgName
						});
						a_updateForm.getForm().findField('vehicleTypeLength').setReadOnly(true);
						a_updateForm.getForm().findField('vehicleTypeLength').store.add({
							'code' : rowInfo.data.vehicleTypeLength,
							'name' : rowInfo.data.vehicleLength
						});
						a_updateForm.loadRecord(rowInfo);
						//将表格里面被选中的信息先保存起来，重置的时候使用
						baseinfo.loadAndUnloadEfficiency.loadAndUnloadEfficiencyVehicle.rawRecord = rowInfo;
						var a_win = baseinfo.loadAndUnloadEfficiency.loadAndUnloadEfficiencyVehicle.windowz;
						a_win.show();
						a_win.setTitle(baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.updateLoadAndUnloadEfficiencyStandardTime'));
					}
				}, {
					iconCls : 'deppon_icons_cancel',
					tooltip : baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.void'),
					disabled:!baseinfo.loadAndUnloadEfficiency.isPermission('loadAndUnloadEfficiencyindex/loadAndUnloadEfficiencyTimeVoidButton'),
					handler : function (grid, rowIndex, colIndex) {
						Ext.MessageBox.buttonText.yes = baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.sure');
						Ext.MessageBox.buttonText.no = baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.cancel');
						Ext.Msg.confirm(baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.tipInfo'), baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.sureDelete'), function (btn, text) {
							if (btn == 'yes') {
								var msgTip = baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.deleteSuccess');

								// 获得当前选择的数据
								var rowInfo = grid.getStore().getAt(rowIndex).data;
								//发送作废结点的Ajax请求.
								var loadAndUnloadEfficiencyVehicleVo = new Object();
								loadAndUnloadEfficiencyVehicleVo.loadAndUnloadEfficiencyVehicleEntity = new Object();
								loadAndUnloadEfficiencyVehicleVo.loadAndUnloadEfficiencyVehicleEntity.virtualCode = rowInfo.virtualCode;
								var params = {
									'loadAndUnloadEfficiencyVehicleVo' : loadAndUnloadEfficiencyVehicleVo
								};

								// "../baseinfo/deleteLoadAndUnloadEfficiencyVehicle.action"
								var r_url = "deleteLoadAndUnloadEfficiencyVehicle.action";
								r_url = baseinfo.realPath(r_url);
								Ext.Ajax.request({
									url : r_url,
									jsonData : params,
									success : function (response) {
										var json = Ext.decode(response.responseText);
										grid.getStore().removeAt(rowIndex);
										baseinfo.loadAndUnloadEfficiency.loadAndUnloadEfficiencyVehicle.pagingBar.moveFirst();
									},
									exception : function (response) {
										var json = Ext.decode(response.responseText);

										msgTip = baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.deleteFailure');
									}
								});
								Ext.Msg.alert(baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.tips'), msgTip);
							}
						});

					}
				}
			]
		}, {
			hidden : true,
			dataIndex : 'virtualCode',
			text : baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.virtualCode'),
			margin : '5 20 5 10'
		}, {
			hidden : true,
			dataIndex : 'orgCode',
			text : baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.deptCode'),
			margin : '5 20 5 10'
		}, {
			dataIndex : 'orgName',
			text : baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.orgName'),
			xtype : 'ellipsiscolumn',
			align : 'left',
			width : 100,
			flex : 3
		}, {
			dataIndex : 'vehicleLength',
			text : baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.vehicleTypeLength'),
			xtype : 'ellipsiscolumn',
			align : 'center',
			width : 151,
			flex : 2
		}, {
			dataIndex : 'glCpLoadHours',
			text : baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.runaboutHighHurdlesLoadStandarTime'),
			xtype : 'ellipsiscolumn',
			align : 'center',
			flex : 2,
			renderer : function (value, cellmeta, record, rowIndex, columnIndex, store) {
				var aimStr = value;
				if (record && record.raw) {
					aimStr = '';
					if (record.raw.glCpLoadHours) {
						aimStr += record.raw.glCpLoadHours + baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.hours');
					}
					if (record.raw.glCpLoadMins) {
						aimStr += record.raw.glCpLoadMins + baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.minutes');
					}
				}
				return aimStr;
			}
		}, {
			dataIndex : 'glCpUnloadHours',
			text : baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.runaboutHighHurdlesUnloadStandarTime'),
			xtype : 'ellipsiscolumn',
			align : 'center',
			width : 151,
			flex : 2,
			renderer : function (value, cellmeta, record, rowIndex, columnIndex, store) {
				var aimStr = value;
				if (record && record.raw) {
					aimStr = '';
					if (record.raw.glCpUnloadHours) {
						aimStr += record.raw.glCpUnloadHours + baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.hours');
					}
					if (record.raw.glCpUnloadMins) {
						aimStr += record.raw.glCpUnloadMins + baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.minutes');
					}
				}
				return aimStr;
			}
		}, {
			dataIndex : 'nglCpLoadHours',
			text : baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.runaboutNoHighHurdlesLoadStandarTime'),
			xtype : 'ellipsiscolumn',
			align : 'center',
			width : 151,
			flex : 2,
			renderer : function (value, cellmeta, record, rowIndex, columnIndex, store) {
				var aimStr = value;
				if (record && record.raw) {
					aimStr = '';
					if (record.raw.nglCpLoadHours) {
						aimStr += record.raw.nglCpLoadHours + baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.hours');
					}
					if (record.raw.nglCpLoadMins) {
						aimStr += record.raw.nglCpLoadMins + baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.minutes');
					}
				}
				return aimStr;
			}
		}, {
			dataIndex : 'nglCpUnloadHours',
			text : baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.runaboutNoHighHurdlesUnloadStandarTime'),
			xtype : 'ellipsiscolumn',
			align : 'center',
			width : 151,
			flex : 2,
			renderer : function (value, cellmeta, record, rowIndex, columnIndex, store) {
				var aimStr = value;
				if (record && record.raw) {
					aimStr = '';
					if (record.raw.nglCpUnloadHours) {
						aimStr += record.raw.nglCpUnloadHours + baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.hours');
					}
					if (record.raw.nglCpUnloadMins) {
						aimStr += record.raw.nglCpUnloadMins + baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.minutes');
					}
				}
				return aimStr;
			}
		}, {
			dataIndex : 'ncpLoadHours',
			text : baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.noRunaboutLoadStandarTime'),
			xtype : 'ellipsiscolumn',
			align : 'center',
			width : 151,
			flex : 2,
			renderer : function (value, cellmeta, record, rowIndex, columnIndex, store) {
				var aimStr = value;
				if (record && record.raw) {
					aimStr = '';
					if (record.raw.ncpLoadHours) {
						aimStr += record.raw.ncpLoadHours + baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.hours');
					}
					if (record.raw.ncpLoadMins) {
						aimStr += record.raw.ncpLoadMins + baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.minutes');
					}
				}
				return aimStr;
			}
		}, {
			dataIndex : 'ncpUnloadHours',
			text : baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.noRunaboutUnloadStandarTime'),
			xtype : 'ellipsiscolumn',
			align : 'center',
			width : 151,
			flex : 2,
			renderer : function (value, cellmeta, record, rowIndex, columnIndex, store) {
				var aimStr = value;
				if (record && record.raw) {
					aimStr = '';
					if (record.raw.ncpUnloadHours) {
						aimStr += record.raw.ncpUnloadHours + baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.hours');
					}
					if (record.raw.ncpUnloadMins) {
						aimStr += record.raw.ncpUnloadMins + baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.minutes');
					}
				}
				return aimStr;
			}
		}
	],

	getTbar : function () {
		var me = this;
		return [{
				text : baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.add'), //新增
				disabled:!baseinfo.loadAndUnloadEfficiency.isPermission('loadAndUnloadEfficiencyindex/loadAndUnloadEfficiencyTimeAddButton'),
				hidden:!baseinfo.loadAndUnloadEfficiency.isPermission('loadAndUnloadEfficiencyindex/loadAndUnloadEfficiencyTimeAddButton'),
				//hidden:!pricing.isPermission('../pricing/saveRole.action'),
				handler : function () {
					baseinfo.loadAndUnloadEfficiency.loadAndUnloadEfficiencyVehicle.flagAddModify = 'add'
//					var a_win = Ext.getCmp("Foss_baseinfo_loadAndUnloadEfficiencyVehicle_UpdateLoadAndUnloadEfficiencyVehicleEntityWindow_Id");
					var a_win = baseinfo.loadAndUnloadEfficiency.loadAndUnloadEfficiencyVehicle.windowz;
					a_win.setTitle(baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.newLoadingAndUnloadingTrucksStandardTime'));
					a_win.setVisible(true);
//					var a_form = Ext.getCmp("Foss_baseinfo_loadAndUnloadEfficiencyVehicle_UpdateLoadAndUnloadEfficiencyVehicleForm_Id");
					var a_form = a_win.down('form');
					// 设置部门编码可以修改
					a_form.getForm().findField('orgCode').setReadOnly(false);
					a_form.getForm().findField('vehicleTypeLength').setReadOnly(false);
					a_form.getForm().reset();
				}
			}, '-', {
				text : baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.void'), //作废
				disabled:!baseinfo.loadAndUnloadEfficiency.isPermission('loadAndUnloadEfficiencyindex/loadAndUnloadEfficiencyTimeVoidButton'),
				hidden:!baseinfo.loadAndUnloadEfficiency.isPermission('loadAndUnloadEfficiencyindex/loadAndUnloadEfficiencyTimeVoidButton'),
				//hidden:!pricing.isPermission('../pricing/deleteRole.action'),
				handler : function () {
					baseinfo.loadAndUnloadEfficiency.loadAndUnloadEfficiencyVehicle.deleteLoadAndUnloadEfficiencyVehicle(true, null);
				}
			}
		];
	},

	constructor : function (config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.store = Ext.create('Ext.data.Store', {
				model : 'Foss.baseinfo.loadAndUnloadEfficiencyVehicle.LoadAndUnloadEfficiencyVehicleModel',
				pageSize : 20,
				autoLoad : false,
				proxy : {
					type : 'ajax',
					actionMethods : 'POST',
					// '../baseinfo/queryLoadAndUnloadEfficiencyVehicleExactByEntity.action'
					url : baseinfo.realPath("queryLoadAndUnloadEfficiencyVehicleExactByEntity.action"),
					//定义一个读取器
					reader : {
						type : 'json',
						root : 'loadAndUnloadEfficiencyVehicleVo.loadAndUnloadEfficiencyVehicleList',
						totalProperty : 'totalCount'
					}
				},
				constructor : function (config) {
					var me = this,
					cfg = Ext.apply({}, config);
					me.callParent([cfg]);
				},
				listeners : {
					beforeload : function (store, operation, eOpts) {
						var queryForm = baseinfo.loadAndUnloadEfficiency.loadAndUnloadEfficiencyVehicle.queryForm;
						if (queryForm != null) {
							var queryParams = queryForm.getValues();
							Ext.apply(operation, {
								params : {
									'loadAndUnloadEfficiencyVehicleVo.loadAndUnloadEfficiencyVehicleEntity.orgCode' : queryParams.orgCode
								}
							});
						}
					}
				}
			});
		me.bbar = Ext.create('Deppon.StandardPaging', {
				store : me.store
			});
		//添加头部按钮
		me.tbar = me.getTbar();
		baseinfo.loadAndUnloadEfficiency.loadAndUnloadEfficiencyVehicle.pagingBar = me.bbar;
		me.callParent([cfg]);
	}
});

//右边模块-查询结果面板
Ext.define('Foss.baseinfo.loadAndUnloadEfficiencyVehicle.SelectResultForm', {
	extend : 'Ext.form.Panel',
	id : 'Foss_baseinfo_loadAndUnloadEfficiencyVehicle_SelectResultForm_Id',
	itemId : 'Foss_baseinfo_loadAndUnloadEfficiencyVehicle_SelectResultForm_ItemId',
	layout : 'column',
	frame : true,
	hidden : false,
	autoScroll : true,
	columnWidth : 0.98,
	cls : 'autoHeight',
	bodyCls : 'autoHeight',
	title : baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.unloadEfficiencyStandardList'),
	defaults : {
		readOnly : false,
		margin : '5 5 5 10',
		anchor : '90%'
	},

	initComponent : function (config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.items = [
			Ext.create('Ext.container.Container', {
				columnWidth : .99,
				layout : 'column',
				items : [
					Ext.create('Foss.baseinfo.loadAndUnloadEfficiencyVehicle.LoadAndUnloadEfficiencyVehicleGrid'),
//					Ext.create('Ext.button.Button', {
//						xtype : 'button',
//						hidden : false,
//						//cls:'yellow_button',
//						name : 'loadAndUnloadEfficiencyVehicle_deletes',
//						text : baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.void'),
//						hidden:!baseinfo.loadAndUnloadEfficiency.isPermission('loadAndUnloadEfficiencyindex/loadAndUnloadEfficiencyTimeVoidButton'),
//						columnWidth : .12,
//						margin : '0,10,0,0',
//						width : 30,
//						handler : function () {
//							baseinfo.loadAndUnloadEfficiency.loadAndUnloadEfficiencyVehicle.deleteLoadAndUnloadEfficiencyVehicle(true, null);
//						}
//					})
				]
			})
		];
		me.callParent([cfg]);
	}
});

Ext.define('Foss.baseinfo.loadAndUnloadEfficiencyVehicle.MainPanel', {
	extend : 'Ext.form.Panel',
	id : 'Foss_baseinfo_loadAndUnloadEfficiencyVehicle_MainPanel_Id',
	itemId : 'Foss_baseinfo_loadAndUnloadEfficiencyVehicle_MainPanel_ItemId',
	title : baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.loadEfficiencyStandardTime'),
	cls : 'autoHeight',
	bodyCls : 'autoHeight',
	defaultType : 'textfield',
	layout : 'column',
	frame : true,
	columnWidth : 0.72,
	margin : '50 2 2 2',
	defaults : {
		readOnly : false,
		margin : '5 5 5 10',
		anchor : '90%',
		columnWidth : 1,
		labelWidth : 120
	},
	initComponent : function (config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.items = [
			Ext.create('Foss.baseinfo.loadAndUnloadEfficiencyVehicle.SelectConditionForm'),
			Ext.create('Foss.baseinfo.loadAndUnloadEfficiencyVehicle.SelectResultForm')
		];
		me.callParent([cfg]);
	}
});

//==============================================================================================
// 装卸车效率标准新增，修改的窗口
//==============================================================================================
Ext.define('Foss.baseinfo.loadAndUnloadEfficiencyVehicle.UpdateLoadAndUnloadEfficiencyVehicleEntityWindow', {
	extend : 'Ext.window.Window',
//	id : 'Foss_baseinfo_loadAndUnloadEfficiencyVehicle_UpdateLoadAndUnloadEfficiencyVehicleEntityWindow_Id',
	width : 800,
	closeAction : 'hide',
	layout : 'column',
	modal : true,
	constructor : function (config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.items = [
			Ext.create('Foss.baseinfo.loadAndUnloadEfficiencyVehicle.UpdateLoadAndUnloadEfficiencyVehicleForm')
		];
		me.callParent([cfg]);
	}
})
// loadAndUnloadEfficiencyVehicle end


/**
 * 装卸车标准-吨-时间_页面
 */
baseinfo.loadAndUnloadEfficiency.loadAndUnloadEfficiencyTon = new Object();

baseinfo.loadAndUnloadEfficiency.loadAndUnloadEfficiencyTon.deleteLoadAndUnloadEfficiencyTon = function (isMore, p_code) {
	Ext.MessageBox.buttonText.yes = baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.sure');
	Ext.MessageBox.buttonText.no = baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.cancel');

	// 获得国际化字符串
	var tip1 = baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.tipInfo');
	var tip2 = baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.areYouSureToVoid');
	Ext.Msg.confirm(tip1, tip2,
		function (btn, text) {
		if (btn == 'yes') {
			var ids = '';
			if (isMore) {
				var a_grid = Ext.getCmp('Foss_baseinfo_loadAndUnloadEfficiencyTon_LoadAndUnloadEfficiencyTonGrid_Id');
				// 获取选中的记录
				var rowObjs = a_grid.getSelectionModel().getSelection();

				if (rowObjs.length <= 0) {
					Ext.Msg.alert(baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.tip'), baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.pleaseSelectDeletedRecord'));
					return;
				}
				// 将id通过,分隔
				for (var i = 0; i < rowObjs.length; i++) {
					ids = ids + rowObjs[i].data.orgCode + ",";
				}
				ids = ids.substring(0, ids.length - 1);
			} else {
				ids = p_code
			}

			//发送作废结点的Ajax请求.
			var loadAndUnloadEfficiencyTonVo = new Object();
			loadAndUnloadEfficiencyTonVo.loadAndUnloadEfficiencyTonEntity = new Object();
			loadAndUnloadEfficiencyTonVo.loadAndUnloadEfficiencyTonEntity.orgCode = ids;
			var params = {
				'loadAndUnloadEfficiencyTonVo' : loadAndUnloadEfficiencyTonVo
			};

			// "../baseinfo/deleteLoadAndUnloadEfficiencyTonMore.action"
			var r_url = "deleteLoadAndUnloadEfficiencyTonMore.action";
			r_url = baseinfo.realPath(r_url);
			Ext.Ajax.request({
				url : r_url,
				jsonData : params,
				//作废成功
				success : function (response) {
					var json = Ext.decode(response.responseText);
					//删除完成后，将“从表格删除”改为刷新表格
					//a_grid.getStore().remove(rowObjs);
					baseinfo.loadAndUnloadEfficiency.loadAndUnloadEfficiencyTon.pagingBar.moveFirst();

					var tipMsg = baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.voidSuccessNo');
					if (json.message) {
						tipMsg = json.message;
					}
					top.Ext.MessageBox.alert(baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.tips'), baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.voidSuccessNo'));
				},
				//保存失败
				exception : function (response) {
					var json = Ext.decode(response.responseText);
					top.Ext.MessageBox.alert(baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.deleteFailure'), json.message);
				}
			});
		}
	});
}
////////////////////////////////////////////////////////////////////
// 全局变量（模块名loadAndUnloadEfficiencyTon） end
////////////////////////////////////////////////////////////////////

// 装卸车效率标准查询-主面板
Ext.define('Foss.baseinfo.loadAndUnloadEfficiencyTon.SelectForm', {
	extend : 'Ext.form.Panel',
	id : 'Foss_baseinfo_loadAndUnloadEfficiencyTon_SelectForm_Id',
	itemId : 'Foss_baseinfo_loadAndUnloadEfficiencyTon_SelectForm_ItemId',
	//height: 80,
	cls : 'autoHeight',
	bodyCls : 'autoHeight',
	defaultType : 'textfield',
	layout : 'column',
	frame : true,
	columnWidth : 0.72,
	defaults : {
		readOnly : false,
		margin : '5 5 5 10',
		anchor : '90%',
		columnWidth : 1,
		labelWidth : 120
	},
	initComponent : function (config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.items = [
			Ext.create('Foss.baseinfo.loadAndUnloadEfficiencyTon.SelectConditionForm'),
			Ext.create('Foss.baseinfo.loadAndUnloadEfficiencyTon.SelectResultForm')
		];
		me.callParent([cfg]);
	}
});

// 装卸车效率标准新增表单
Ext.define('Foss.baseinfo.loadAndUnloadEfficiencyTon.UpdateLoadAndUnloadEfficiencyTonForm', {
	extend : 'Ext.form.Panel',
//	id : 'Foss_baseinfo_loadAndUnloadEfficiencyTon_UpdateLoadAndUnloadEfficiencyTonForm_Id',
//	title: baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.addLoadAndUploadEfficiencyStandard'),
	frame : true,
	hidden : false,
	defaultType : 'textfield',
	layout : {
		type : 'table',
		columns : 1
	},
	columnWidth : 0.99,
	defaults : {
		readOnly : false,
		margin : '5 5 5 10',
		anchor : '90%',
		columnWidth : 1,
		labelWidth : 120
	},

	//取消按钮
	cancelButton : null,
	getCancelButton : function (me) {
		if (this.cancelButton == null) {
			this.cancelButton = Ext.create('Ext.button.Button', {
					xtype : 'button',
					//cls:'yellow_button',
					text : baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.cancel'),
					columnWidth : .12,
					width : 55,
					hidden : false,
					handler : function () {
//						var a_window = Ext.getCmp('Foss_baseinfo_loadAndUnloadEfficiencyTon_UpdateLoadAndUnloadEfficiencyTonEntityWindow_Id');
						var a_window = baseinfo.loadAndUnloadEfficiency.loadAndUnloadEfficiencyTon.windowz;
//						var loadAndUnloadEfficiencyTonForm = Ext.getCmp('Foss_baseinfo_loadAndUnloadEfficiencyTon_UpdateLoadAndUnloadEfficiencyTonForm_Id');
//						loadAndUnloadEfficiencyTonForm.getForm().reset();
//						a_window.hide();
						a_window.down('form').getForm().reset();
						a_window.hide();
					}
				});
		}
		return this.cancelButton;
	},
	//重置按钮
	resetButton : null,
	getResetButton : function (me) {
		if (this.resetButton == null) {
			this.resetButton = Ext.create('Ext.button.Button', {
					xtype : 'button',
					hidden : false,
					cls : 'yellow_button',
					name : 'reset',
					text : baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.reset'),
					columnWidth : .72,
					width : 55,
					margin : '5 5 5 540',
					handler : function () {
//						var a_updateForm = Ext.getCmp('Foss_baseinfo_loadAndUnloadEfficiencyTon_UpdateLoadAndUnloadEfficiencyTonForm_Id');
						var a_updateForm = baseinfo.loadAndUnloadEfficiency.loadAndUnloadEfficiencyTon.windowz.down('form');
						a_updateForm.getForm().reset();
						if (baseinfo.loadAndUnloadEfficiency.loadAndUnloadEfficiencyTon.flagAddModify == 'modify') {
							var a_formInfo = baseinfo.loadAndUnloadEfficiency.loadAndUnloadEfficiencyTon.rawRecord;
							a_updateForm.getForm().loadRecord(a_formInfo);
						}
					}
				});
		}
		return this.resetButton;

	},
	//保存按钮
	saveButton : null,
	getSaveButton : function () {
		if (this.saveButton == null) {
			this.saveButton = Ext.create('Ext.button.Button', {
					cls : 'yellow_button',
					text : baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.save'),
					columnWidth : .72,
					width : 55,
					margin : '5 5 5 5',
					handler : function () {
						var a_changeView = true;

						// "../baseinfo/updateLoadAndUnloadEfficiencyTon.action"
						var r_url = "updateLoadAndUnloadEfficiencyTon.action";
						r_url = baseinfo.realPath(r_url);
						var a_url = r_url;

//						var a_form = Ext.getCmp("Foss_baseinfo_loadAndUnloadEfficiencyTon_UpdateLoadAndUnloadEfficiencyTonForm_Id");
						var a_form = baseinfo.loadAndUnloadEfficiency.loadAndUnloadEfficiencyTon.windowz.down('form');
						var a_model = Ext.create('Foss.baseinfo.loadAndUnloadEfficiencyTon.LoadAndUnloadEfficiencyTonModel', a_form.getForm().getValues());
						// 请求合法性验证
						if (!a_form.getForm().isValid()) {
							return;
						}

						var loadAndUnloadEfficiencyTonVo = new Object();
						loadAndUnloadEfficiencyTonVo.loadAndUnloadEfficiencyTonEntity = new Object();
						loadAndUnloadEfficiencyTonVo.loadAndUnloadEfficiencyTonEntity = a_model.data

							var params = {
							'loadAndUnloadEfficiencyTonVo' : loadAndUnloadEfficiencyTonVo
						};

						// 如果是新增
						if (baseinfo.loadAndUnloadEfficiency.loadAndUnloadEfficiencyTon.flagAddModify == 'add') {
							// "../baseinfo/addLoadAndUnloadEfficiencyTon.action"
							var r_url = "addLoadAndUnloadEfficiencyTon.action";
							r_url = baseinfo.realPath(r_url);
							a_url = r_url;

							var loadAndUnloadEfficiencyTonVoCondition = new Object();
							loadAndUnloadEfficiencyTonVoCondition.loadAndUnloadEfficiencyTonEntity = new Object();
							loadAndUnloadEfficiencyTonVoCondition.loadAndUnloadEfficiencyTonEntity.orgCode = loadAndUnloadEfficiencyTonVo.loadAndUnloadEfficiencyTonEntity.orgCode;

							var a_checkParams = {
								'loadAndUnloadEfficiencyTonVo' : loadAndUnloadEfficiencyTonVoCondition
							};

							// "../baseinfo/queryLoadAndUnloadEfficiencyTonExactByEntity.action"
							var r_url = "queryLoadAndUnloadEfficiencyTonExactByEntity.action";
							r_url = baseinfo.realPath(r_url);
							// 重复性验证
							var a_isRepeat = false;
							Ext.Ajax.request({
								url : r_url,
								jsonData : a_checkParams,
								async : false, //ASYNC 是否异步( TRUE 异步 , FALSE 同步)
								success : function (response) {
									var json = Ext.decode(response.responseText);
									var a_list = json.loadAndUnloadEfficiencyTonVo.loadAndUnloadEfficiencyTonList;
									if (a_list == null || a_list.length == 0) {
										a_isRepeat = false;
									} else {
										a_isRepeat = true;
										a_changeView = false;
									}
								},
								exception : function (response) {
									a_changeView = false;
									var json = Ext.decode(response.responseText);
								}
							});

							if (a_isRepeat) {
								//var a_field = Ext.getCmp("Foss_baseinfo_loadAndUnloadEfficiencyTon_AddLoadAndUnloadEfficiencyTonFormSecondCodeName_Id");
								var a_orgName = "";
								if (a_form) {
									var a_org = a_form.getForm().findField("orgCode");
									if (a_org) {
										a_orgName = a_org.getRawValue();
									}
								}
								Ext.Msg.alert(baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.tips'), a_orgName + baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.loadingUnloadingEfficiencyInputNotRepeatedEntry'));
								return "";
							}
						}

						Ext.Ajax.request({
							url : a_url,
							jsonData : params,
							success : function (response) {
								var json = Ext.decode(response.responseText);
								baseinfo.loadAndUnloadEfficiency.loadAndUnloadEfficiencyTon.pagingBar.moveFirst();

								var tipMsg = baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.saveSuccessful');
								if (json.message) {
									tipMsg = json.message;
								}
								json.message = baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.addLoadAndUnloadEfficiencyManIsSuccessed');
								top.Ext.MessageBox.alert(baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.saveSuccessful'), json.message);
							},
							//保存失败
							exception : function (response) {
								a_changeView = false;
								var json = Ext.decode(response.responseText);
								top.Ext.MessageBox.alert(baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.saveFail'), json.message);
							}
						});

						if (a_changeView) {
//							var a_conditionForm = Ext.getCmp("Foss_baseinfo_loadAndUnloadEfficiencyTon_UpdateLoadAndUnloadEfficiencyTonForm_Id");
							// 将表单中的数据清空
//							a_conditionForm.getForm().reset();
							baseinfo.loadAndUnloadEfficiency.loadAndUnloadEfficiencyTon.windowz.down('form');
//							var a_win = Ext.getCmp("Foss_baseinfo_loadAndUnloadEfficiencyTon_UpdateLoadAndUnloadEfficiencyTonEntityWindow_Id");
//							a_win.setVisible(false)
							baseinfo.loadAndUnloadEfficiency.loadAndUnloadEfficiencyTon.windowz.setVisible(false);
						}

					}
				});
		}
		return this.saveButton;
	},

	initComponent : function (config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.items = [{
				allowBlank : false,
				xtype : 'dynamicorgcombselector',
				type : 'ORG',
				name : 'orgCode',
				fieldLabel : baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.orgName'),
				salesDepartment : 'Y',
				transferCenter : 'Y',
				margin : '5 20 5 10',
				columnWidth : .48,
				labelWidth : 160
			}, {
				allowBlank : false,
				name : 'loadWeightStd',
				fieldLabel : baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.loadWeightStandardDPM'),
				xtype : 'numberfield',
				// 小数位数
				decimalPrecision : 0,
				value : 12,
				minValue : 0,
				minValue:0,
				maxValue:99999999,
				margin : '5 20 5 10',
				columnWidth : .9,
				labelWidth : 160
			}, {
				allowBlank : false,
				name : 'loadVolumeStd',
				fieldLabel : baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.loadVolumnStandard'),
				xtype : 'numberfield',
				// 小数位数
				decimalPrecision : 0,
				value : 3,
				minValue : 0,
				maxValue:99999999,
				margin : '5 20 5 10',
				labelWidth : 160,
				columnWidth : .9,
				labelWidth : 160
			}, {
				allowBlank : false,
				name : 'unloadWeightStd',
				fieldLabel : baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.unloadWeightStandardTM'),
				xtype : 'numberfield',
				// 小数位数
				decimalPrecision : 0,
				value : 12,
				minValue : 0,
				maxValue:99999999,
				margin : '5 20 5 10',
				columnWidth : .9,
				labelWidth : 160
			}, {
				allowBlank : false,
				name : 'unloadVolumeStd',
				fieldLabel : baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.unloadVolumnStandardFM'),
				xtype : 'numberfield',
				// 小数位数
				decimalPrecision : 0,
				value : 3,
				minValue : 0,
				maxValue:99999999,
				margin : '5 20 5 10',
				columnWidth : .9,
				labelWidth : 160
			},
			Ext.create('Ext.container.Container', {
				width : 740,
				items : [
					me.getCancelButton(me),
					me.getResetButton(me),
					me.getSaveButton()
				]
			})
		];
		me.callParent([cfg]);
	}
});

//==============================================================================================
// 下面是详细实现
//==============================================================================================


/**
 * 装卸车标准-吨-时间 界面数据模型定义
 */
Ext.define('Foss.baseinfo.loadAndUnloadEfficiencyTon.LoadAndUnloadEfficiencyTonModel', {
	extend : 'Ext.data.Model',
	fields : [
		// ID
		{
			name : 'id',
			type : 'string'
		},
		// 部门编码
		{
			name : 'orgCode',
			type : 'string'
		},
		// 部门名称
		{
			name : 'orgName',
			type : 'string'
		},
		// 装车重量标准
		{
			name : 'loadWeightStd',
			type : 'integer'
		},
		// 装车体积标准
		{
			name : 'loadVolumeStd',
			type : 'integer'
		},
		// 卸车重量标准
		{
			name : 'unloadWeightStd',
			type : 'integer'
		},
		// 卸车体积标准
		{
			name : 'unloadVolumeStd',
			type : 'integer'
		},
		// 创建时间
		{
			name : 'createTime',
			type : 'date'
		},
		// 更新时间
		{
			name : 'modifyTime',
			type : 'date'
		},
		// 是否启用
		{
			name : 'active',
			type : 'string'
		},
		// 创建人
		{
			name : 'createUserCode',
			type : 'string'
		},
		// 更新人
		{
			name : 'modifyUserCode',
			type : 'string'
		}
	]
});

//查询条件面板
Ext.define('Foss.baseinfo.loadAndUnloadEfficiencyTon.SelectConditionForm', {
	extend : 'Ext.form.Panel',
	id : 'Foss_baseinfo_loadAndUnloadEfficiencyTon_SelectConditionForm_Id',
	itemId : 'Foss_baseinfo_loadAndUnloadEfficiencyTon_SelectConditionForm_ItemId',
	layout : 'column',
	frame : true,
	columnWidth : 0.98,
	title : baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.queryCondition'),
	defaults : {
		xtype : 'textfield',
		readOnly : false,
		margin : '5 5 5 10',
		anchor : '90%'
	},

	initComponent : function (config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.items = [
			Ext.create('Ext.container.Container', {
				columnWidth : .99,
				layout : 'column',
				defaultType : 'textfield',
				items : [{
						xtype : 'dynamicorgcombselector',
						type : 'ORG',
						name : 'orgCode',
						fieldLabel : baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.orgName'),
						salesDepartment : 'Y',
						transferCenter : 'Y',
						margin : '5 20 5 10',
						columnWidth : .48,
						labelWidth : 160
					}, Ext.create('Ext.container.Container', {
						columnWidth : .4,
						layout : 'column',
						items : [{
								columnWidth : 0.00075,
								height : 0,
								xtype : 'container',
								style : 'padding-top:20px;border:none',
								hide : true
							}, Ext.create('Ext.button.Button', {
								xtype : 'button',
								cls : 'yellow_button',
								name : 'buttonReset',
								text : baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.reset'),
								disabled:!baseinfo.loadAndUnloadEfficiency.isPermission('loadAndUnloadEfficiencyindex/loadAndUnloadEfficiencyQueryButton'),
								hidden:!baseinfo.loadAndUnloadEfficiency.isPermission('loadAndUnloadEfficiencyindex/loadAndUnloadEfficiencyQueryButton'),
								columnWidth : .12,
								margin : '0,10,0,0',
								width : 20,
								handler : function () {
									var a_conditionForm = Ext.getCmp("Foss_baseinfo_loadAndUnloadEfficiencyTon_SelectConditionForm_Id");
									// 将表单中的数据清空
									a_conditionForm.getForm().reset();
								}
							}), {
								columnWidth : 0.010,
								height : 0,
								xtype : 'container',
								style : 'padding-top:20px;border:none',
								hide : true
							}, Ext.create('Ext.button.Button', {
								xtype : 'button',
								cls : 'yellow_button',
								name : 'buttonSelect',
								text : baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.query'),
								disabled:!baseinfo.loadAndUnloadEfficiency.isPermission('loadAndUnloadEfficiencyindex/loadAndUnloadEfficiencyQueryButton'),
								hidden:!baseinfo.loadAndUnloadEfficiency.isPermission('loadAndUnloadEfficiencyindex/loadAndUnloadEfficiencyQueryButton'),
								columnWidth : .12,
								margin : '0,10,0,30',
								width : 20,

								//查询按钮的响应事件
								handler : function () {
									baseinfo.loadAndUnloadEfficiency.loadAndUnloadEfficiencyTon.pagingBar.moveFirst();
								}
							})
						]
					})
				]
			})
		];
		me.callParent([cfg]);
	}
});

//查询的显示表格
Ext.define('Foss.baseinfo.loadAndUnloadEfficiencyTon.LoadAndUnloadEfficiencyTonGrid', {
	extend : 'Ext.grid.Panel',
	id : 'Foss_baseinfo_loadAndUnloadEfficiencyTon_LoadAndUnloadEfficiencyTonGrid_Id',
	cls : 'autoHeight',
	bodyCls : 'autoHeight',
	columnWidth : .99,
	//增加滚动条
	autoScroll : false,
	stripeRows : true, // 交替行效果
	collapsible : true,
	animCollapse : true,
	selType : "rowmodel", // 选择类型设置为行选择
	store : null,
	//selModel : Ext.create('Ext.selection.RowModel'),
	selModel : Ext.create('Ext.selection.CheckboxModel'),

	columns : [{
			xtype : 'actioncolumn',
			flex : 1,
			text : baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.operate'),
			align : 'center',
			items : [{
					iconCls : 'deppon_icons_edit',
					tooltip : baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.update'),
					disabled:!baseinfo.loadAndUnloadEfficiency.isPermission('loadAndUnloadEfficiencyindex/loadAndUnloadEfficiencyUpdateButton'),
					handler : function (grid, rowIndex, colIndex) {
						baseinfo.loadAndUnloadEfficiency.loadAndUnloadEfficiencyTon.flagAddModify = 'modify';
//						var a_updateForm = Ext.getCmp('Foss_baseinfo_loadAndUnloadEfficiencyTon_UpdateLoadAndUnloadEfficiencyTonForm_Id');
						var a_updateForm = baseinfo.loadAndUnloadEfficiency.loadAndUnloadEfficiencyTon.windowz.down('form');
						var rowInfo = grid.getStore().getAt(rowIndex);

						a_updateForm.getForm().findField('orgCode').setReadOnly(true);
						a_updateForm.getForm().findField('orgCode').store.add({
							'code' : rowInfo.data.orgCode,
							'name' : rowInfo.data.orgName
						});

						a_updateForm.loadRecord(rowInfo);
						//将表格里面被选中的信息先保存起来，重置的时候使用
						baseinfo.loadAndUnloadEfficiency.loadAndUnloadEfficiencyTon.rawRecord = rowInfo;

						var a_win = baseinfo.loadAndUnloadEfficiency.loadAndUnloadEfficiencyTon.windowz;
						a_win.show();
						a_win.setTitle(baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.updateLoadAndUnloadEfficiencyStandard'));
					}
				}, {
					iconCls : 'deppon_icons_cancel',
					tooltip : baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.void'),
					disabled:!baseinfo.loadAndUnloadEfficiency.isPermission('loadAndUnloadEfficiencyindex/loadAndUnloadEfficiencyVoidButton'),
					handler : function (grid, rowIndex, colIndex) {
						Ext.MessageBox.buttonText.yes = baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.sure');
						Ext.MessageBox.buttonText.no = baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.cancel');
						Ext.Msg.confirm(baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.tipInfo'), baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.sureDelete'), function (btn, text) {
							if (btn == 'yes') {
								var msgTip = baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.deleteSuccess');

								// 获得当前选择的数据
								var rowInfo = grid.getStore().getAt(rowIndex).data;
								//发送作废结点的Ajax请求.
								var loadAndUnloadEfficiencyTonVo = new Object();
								loadAndUnloadEfficiencyTonVo.loadAndUnloadEfficiencyTonEntity = new Object();
								loadAndUnloadEfficiencyTonVo.loadAndUnloadEfficiencyTonEntity.orgCode = rowInfo.orgCode;
								var params = {
									'loadAndUnloadEfficiencyTonVo' : loadAndUnloadEfficiencyTonVo
								};

								// "../baseinfo/deleteLoadAndUnloadEfficiencyTon.action"
								var r_url = "deleteLoadAndUnloadEfficiencyTon.action";
								r_url = baseinfo.realPath(r_url);
								Ext.Ajax.request({
									url : r_url,
									jsonData : params,
									success : function (response) {
										var json = Ext.decode(response.responseText);
										grid.getStore().removeAt(rowIndex);
										baseinfo.loadAndUnloadEfficiency.loadAndUnloadEfficiencyTon.pagingBar.moveFirst();
									},
									exception : function (response) {
										var json = Ext.decode(response.responseText);

										msgTip = baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.deleteFailure');
									}
								});
								Ext.Msg.alert(baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.tips'), msgTip);
							}
						});

					}
				}
			]
		}, {
			hidden : true,
			dataIndex : 'orgCode',
			text : baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.deptCode'),
			margin : '5 20 5 10'
		}, {
			dataIndex : 'orgName',
			text : baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.orgName'),
			xtype : 'ellipsiscolumn',
			align : 'left',
			flex : 3
		}, {
			dataIndex : 'loadWeightStd',
			text : baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.loadWeightBrStandard'),
			xtype : 'ellipsiscolumn',
			align : 'center',
			flex : 2
		}, {
			dataIndex : 'loadVolumeStd',
			text : baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.loadVolumnBrStandard'),
			xtype : 'ellipsiscolumn',
			align : 'center',
			flex : 2
		}, {
			dataIndex : 'unloadWeightStd',
			text : baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.unloadWeightBrStandard'),
			xtype : 'ellipsiscolumn',
			align : 'center',
			flex : 2
		}, {
			dataIndex : 'unloadVolumeStd',
			text : baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.unloadVolumnBrStandard'),
			xtype : 'ellipsiscolumn',
			align : 'center',
			flex : 2
		}
	],

	getTbar : function () {
		var me = this;
		return [{
				text : baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.add'), //新增
				disabled:!baseinfo.loadAndUnloadEfficiency.isPermission('loadAndUnloadEfficiencyindex/loadAndUnloadEfficiencyAddButton'),
				hidden:!baseinfo.loadAndUnloadEfficiency.isPermission('loadAndUnloadEfficiencyindex/loadAndUnloadEfficiencyAddButton'),
				//hidden:!pricing.isPermission('../pricing/saveRole.action'),
				handler : function () {
					baseinfo.loadAndUnloadEfficiency.loadAndUnloadEfficiencyTon.flagAddModify = 'add'
//					var a_win = Ext.getCmp("Foss_baseinfo_loadAndUnloadEfficiencyTon_UpdateLoadAndUnloadEfficiencyTonEntityWindow_Id");
					var a_win = baseinfo.loadAndUnloadEfficiency.loadAndUnloadEfficiencyTon.windowz;
					a_win.setTitle(baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.addLoadAndUploadEfficiencyStandard'));
					a_win.setVisible(true);
//					var a_form = Ext.getCmp("Foss_baseinfo_loadAndUnloadEfficiencyTon_UpdateLoadAndUnloadEfficiencyTonForm_Id");
					var a_form = baseinfo.loadAndUnloadEfficiency.loadAndUnloadEfficiencyTon.windowz.down('form');
					// 设置部门编码可以修改
					a_form.getForm().findField('orgCode').setReadOnly(false);
					a_form.getForm().reset();
				}
			}, '-', {
				text : baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.void'), //作废
				disabled:!baseinfo.loadAndUnloadEfficiency.isPermission('loadAndUnloadEfficiencyindex/loadAndUnloadEfficiencyVoidButton'),
				hidden:!baseinfo.loadAndUnloadEfficiency.isPermission('loadAndUnloadEfficiencyindex/loadAndUnloadEfficiencyVoidButton'),
				//hidden:!pricing.isPermission('../pricing/deleteRole.action'),
				handler : function () {
					baseinfo.loadAndUnloadEfficiency.loadAndUnloadEfficiencyTon.deleteLoadAndUnloadEfficiencyTon(true, null);

				}
			}
		];
	},

	constructor : function (config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.store = Ext.create('Ext.data.Store', {
				model : 'Foss.baseinfo.loadAndUnloadEfficiencyTon.LoadAndUnloadEfficiencyTonModel',
				pageSize : 20,
				autoLoad : false,
				proxy : {
					type : 'ajax',
					actionMethods : 'POST',
					// '../baseinfo/queryLoadAndUnloadEfficiencyTonExactByEntity.action'
					url : baseinfo.realPath("queryLoadAndUnloadEfficiencyTonExactByEntity.action"),
					//定义一个读取器
					reader : {
						type : 'json',
						root : 'loadAndUnloadEfficiencyTonVo.loadAndUnloadEfficiencyTonList',
						totalProperty : 'totalCount'
					}
				},
				constructor : function (config) {
					var me = this,
					cfg = Ext.apply({}, config);
					me.callParent([cfg]);
				},
				listeners : {
					beforeload : function (store, operation, eOpts) {
						var queryForm = baseinfo.loadAndUnloadEfficiency.loadAndUnloadEfficiencyTon.queryForm;
						if (queryForm != null) {
							var queryParams = queryForm.getValues();
							Ext.apply(operation, {
								params : {
									'loadAndUnloadEfficiencyTonVo.loadAndUnloadEfficiencyTonEntity.orgCode' : queryParams.orgCode
								}
							});
						}
					}
				}
			});
		me.bbar = Ext.create('Deppon.StandardPaging', {
				store : me.store
			});
		//添加头部按钮
		me.tbar = me.getTbar();
		baseinfo.loadAndUnloadEfficiency.loadAndUnloadEfficiencyTon.pagingBar = me.bbar;
		me.callParent([cfg]);
	}
});

//右边模块-查询结果面板
Ext.define('Foss.baseinfo.loadAndUnloadEfficiencyTon.SelectResultForm', {
	extend : 'Ext.form.Panel',
	id : 'Foss_baseinfo_loadAndUnloadEfficiencyTon_SelectResultForm_Id',
	itemId : 'Foss_baseinfo_loadAndUnloadEfficiencyTon_SelectResultForm_ItemId',
	layout : 'column',
	frame : true,
	hidden : false,
	columnWidth : 0.98,
	cls : 'autoHeight',
	bodyCls : 'autoHeight',
	title : baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.unloadEfficiencyStandardList'),
	defaults : {
		readOnly : false,
		margin : '5 5 5 10',
		anchor : '90%'
	},

	initComponent : function (config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.items = [
			Ext.create('Ext.container.Container', {
				columnWidth : .99,
				layout : 'column',
				items : [
					Ext.create('Foss.baseinfo.loadAndUnloadEfficiencyTon.LoadAndUnloadEfficiencyTonGrid'),
//					Ext.create('Ext.button.Button', {
//						xtype : 'button',
//						hidden : false,
//						//cls:'yellow_button',
//						name : 'loadAndUnloadEfficiencyTon_deletes',
//						text : baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.void'),
//						hidden:!baseinfo.loadAndUnloadEfficiency.isPermission('loadAndUnloadEfficiencyindex/loadAndUnloadEfficiencyVoidButton'),
//						columnWidth : .12,
//						margin : '0,10,0,0',
//						width : 30,
//						handler : function () {
//							baseinfo.loadAndUnloadEfficiency.loadAndUnloadEfficiencyTon.deleteLoadAndUnloadEfficiencyTon(true, null);
//
//						}
//					})
				]
			})
		];
		me.callParent([cfg]);
	}
});

Ext.define('Foss.baseinfo.loadAndUnloadEfficiencyTon.MainPanel', {
	extend : 'Ext.form.Panel',
	id : 'Foss_baseinfo_loadAndUnloadEfficiencyTon_MainPanel_Id',
	itemId : 'Foss_baseinfo_loadAndUnloadEfficiencyTon_MainPanel_ItemId',
	title : baseinfo.loadAndUnloadEfficiency.i18n('foss.baseinfo.loadAndUnloadEfficiencyStandard'),
	cls : 'autoHeight',
	bodyCls : 'autoHeight',
	defaultType : 'textfield',
	layout : 'column',
	frame : true,
	columnWidth : 0.72,
	margin : '50 2 2 2',
	defaults : {
		readOnly : false,
		margin : '5 5 5 10',
		anchor : '90%',
		columnWidth : 1,
		labelWidth : 120
	},
	initComponent : function (config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.items = [
			Ext.create('Foss.baseinfo.loadAndUnloadEfficiencyTon.SelectConditionForm'),
			Ext.create('Foss.baseinfo.loadAndUnloadEfficiencyTon.SelectResultForm')
		];
		me.callParent([cfg]);
	}
});

//==============================================================================================
// 装卸车效率标准新增，修改的窗口
//==============================================================================================
Ext.define('Foss.baseinfo.loadAndUnloadEfficiencyTon.UpdateLoadAndUnloadEfficiencyTonEntityWindow', {
	extend : 'Ext.window.Window',
//	id : 'Foss_baseinfo_loadAndUnloadEfficiencyTon_UpdateLoadAndUnloadEfficiencyTonEntityWindow_Id',
	width : 800,
	closeAction : 'hide',
	layout : 'column',
	modal : true,
	constructor : function (config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.items = [
			Ext.create('Foss.baseinfo.loadAndUnloadEfficiencyTon.UpdateLoadAndUnloadEfficiencyTonForm')
		];
		me.callParent([cfg]);
	}
})

/**
 * 程序入口方法
 */
Ext.onReady(function () {
	Ext.QuickTips.init();
	baseinfo.loadAndUnloadEfficiency.loadAndUnloadEfficiencyTon.windowz = Ext.create('Foss.baseinfo.loadAndUnloadEfficiencyTon.UpdateLoadAndUnloadEfficiencyTonEntityWindow');
	baseinfo.loadAndUnloadEfficiency.loadAndUnloadEfficiencyVehicle.windowz = Ext.create('Foss.baseinfo.loadAndUnloadEfficiencyVehicle.UpdateLoadAndUnloadEfficiencyVehicleEntityWindow');
	baseinfo.loadAndUnloadEfficiency.loadAndUnloadEfficiencyMan.windowz = Ext.create('Foss.baseinfo.loadAndUnloadEfficiencyMan.UpdateLoadAndUnloadEfficiencyManEntityWindow');
	if (Ext.getCmp('T_baseinfo-loadAndUnloadEfficiencyindex_content')) {
		return;
	}
	Ext.getCmp('T_baseinfo-loadAndUnloadEfficiencyindex').add(Ext.create('Ext.tab.Panel', {
		flex : 1,
		cls : 'innerTabPanel',
		bodyStyle : {
			height : '100%',
			margin : '0 10 0 0',
			width : 100
		},
		items : [
			Ext.create('Foss.baseinfo.loadAndUnloadEfficiencyTon.MainPanel'),
			Ext.create('Foss.baseinfo.loadAndUnloadEfficiencyVehicle.MainPanel'),
			Ext.create('Foss.baseinfo.loadAndUnloadEfficiencyMan.MainPanel')
		]
	}));
	baseinfo.loadAndUnloadEfficiency.loadAndUnloadEfficiencyTon.queryForm = Ext.getCmp('Foss_baseinfo_loadAndUnloadEfficiencyTon_SelectConditionForm_Id');
	baseinfo.loadAndUnloadEfficiency.loadAndUnloadEfficiencyVehicle.queryForm = Ext.getCmp('Foss_baseinfo_loadAndUnloadEfficiencyVehicle_SelectConditionForm_Id');
	baseinfo.loadAndUnloadEfficiency.loadAndUnloadEfficiencyMan.queryForm = Ext.getCmp('Foss_baseinfo_loadAndUnloadEfficiencyMan_SelectConditionForm_Id');

});
