// 状态常量，用于表示窗口状态；新增、修改、查看
platform.tfrCtrAbsenteeInfo.viewState = {
	add : 'ADD',
	update : 'UPDATE',
	view : 'VIEW'
};

/*
 * 删除方法 ids:待删除记录的id集合 grid：存放记录的grid，用于删除记录后刷新表格
 */
platform.tfrCtrAbsenteeInfo.deleteTfrCtrAbsenteeInfos = function(ids, grid) {
	Ext.Msg.show({
		title : '提示',
		msg : '确定删除选中信息?',
		buttons : Ext.Msg.YESNO,
		icon : Ext.Msg.QUESTION,
		fn : function(button) {
			if (button === 'yes') {
				Ext.Ajax.request({
					url : platform.realPath('deleteTfrCtrAbsenteeInfos.action'),
					jsonData : {
						'tfrCtrAbsenteeInfoVo' : {
							'ids' : ids
						}
					},
					success : function(response) {
						grid.store.loadPage(1);
						Ext.ux.Toast.msg('提示', '删除成功！', 'info', 1500);
					},
					exception : function(response) {
						var result = Ext.decode(response.responseText);
						Ext.ux.Toast.msg('提示', '删除失败，' + result.message,
								'error', 2000);
					}
				});
			}
		}
	});
};

/*
 * 保存方法;新增保存，修改保存 window:点击新增/修改按钮时，弹出的窗体
 */
platform.tfrCtrAbsenteeInfo.submitTfrCtrAbsenteeInfos = function(window) {
	var viewState = window.viewState, editForm = window.editForm.getForm();
	if (editForm.isValid()) {
		// 新增时的action
		var url = platform.realPath('insertTfrCtrAbsenteeInfo.action');
		// 修改时的action
		if (viewState === platform.tfrCtrAbsenteeInfo.viewState.update) {
			url = platform.realPath('updateTfrCtrAbsenteeInfo.action');
		}
		var windowMask = new Ext.LoadMask(window, {
					msg : "保存中，请稍后..."
				});
		windowMask.show();

		// 获取form各元素的值
		var editFormValues = editForm.getValues();
		// 复制提交参数
		var param = {
			'tfrCtrAbsenteeInfoVo' : {
				'tfrCtrAbsenteeInfoEntity' : editFormValues
			}
		};

		if (viewState === platform.tfrCtrAbsenteeInfo.viewState.add) {

			// 由于控件的disabled属性导致form.getValues()方法取不到对应disabled控件的值，所以新增时，对这些控件单独取值
			param.tfrCtrAbsenteeInfoVo.tfrCtrAbsenteeInfoEntity.createDate = editForm
					.findField('createDate').getValue();
			param.tfrCtrAbsenteeInfoVo.tfrCtrAbsenteeInfoEntity.absenteeEntryDate = editForm
					.findField('absenteeEntryDate').getValue();
			param.tfrCtrAbsenteeInfoVo.tfrCtrAbsenteeInfoEntity.absenteeName = editForm
					.findField('absenteeName').getValue();
			param.tfrCtrAbsenteeInfoVo.tfrCtrAbsenteeInfoEntity.absenteePostCode = editForm
					.findField('absenteePostCode').getValue();
			param.tfrCtrAbsenteeInfoVo.tfrCtrAbsenteeInfoEntity.absenteePostName = editForm
					.findField('absenteePostName').getValue();

			// 比如一个部门选择器对应code，name两参数
			// 下面对name参数赋值
			var transferCenterCodeField = editForm
					.findField('transferCenterCode'), transferCenterCode = transferCenterCodeField
					.getValue();
			param.tfrCtrAbsenteeInfoVo.tfrCtrAbsenteeInfoEntity.transferCenterCode = transferCenterCode;
			param.tfrCtrAbsenteeInfoVo.tfrCtrAbsenteeInfoEntity.transferCenterName = transferCenterCodeField.store
					.findRecord('code', transferCenterCode, false, true, true)
					.get('name');

			var absenteeOrgCodeField = editForm.findField('absenteeOrgCode'), absenteeOrgCode = absenteeOrgCodeField
					.getValue();

			param.tfrCtrAbsenteeInfoVo.tfrCtrAbsenteeInfoEntity.absenteeOrgCode = absenteeOrgCode;
			param.tfrCtrAbsenteeInfoVo.tfrCtrAbsenteeInfoEntity.absenteeOrgName = absenteeOrgCodeField.store
					.findRecord('code', absenteeOrgCode, false, true, true)
					.get('name');
		} else if (viewState === platform.tfrCtrAbsenteeInfo.viewState.update) {
			var oldFormRecord = window.editForm.oldFormRecord;

			if (oldFormRecord.get('absenteeStatus') === editFormValues.absenteeStatus
					&& Ext.Date.format(oldFormRecord.get('absentBeginDate'),
							'Y-m-d') === editFormValues.absentBeginDate
					&& Ext.Date.format(oldFormRecord.get('absentEndDate'),
							'Y-m-d') === editFormValues.absentEndDate) {
				Ext.ux.Toast.msg('提示', '请先修改数据！', 'info', 1500);
				windowMask.hide();
				return;
			}

			param.tfrCtrAbsenteeInfoVo.tfrCtrAbsenteeInfoEntity.absenteeCode = editForm
					.findField('absenteeCode').getValue();
		}

		Ext.Ajax.request({
			url : url,
			jsonData : param,
			success : function(response) {
				Ext.ux.Toast.msg('提示', '保存成功！', 'info', 1500);
				windowMask.hide();
				if (viewState === platform.tfrCtrAbsenteeInfo.viewState.add) {
					window.resetData();
					window.initData();
				} else if (viewState === platform.tfrCtrAbsenteeInfo.viewState.update) {
					window.sourceGrid.store.loadPage(1);
				}
			},
			exception : function(response) {
				var result = Ext.decode(response.responseText);
				windowMask.hide();
				Ext.ux.Toast.msg('提示', '保存失败，' + result.message, 'error', 2000);
			}
		});
	}
};

// 查看对应的超连接方法
platform.tfrCtrAbsenteeInfo.viewWindowShow = function(rowIndex) {
	var grid = Ext
			.getCmp('Foss_platform_tfrCtrAbsenteeInfo_tfrCtrAbsenteeInfoGrid_id'), formRecord = grid
			.getStore().getAt(rowIndex);
	grid.getViewWindow(platform.tfrCtrAbsenteeInfo.viewState.view, formRecord)
			.show();
};

/*
 * 初始化弹出的window window：点击新增，修改，查看时弹出的window viewState：状态，分别为新增、修改、查看
 */
platform.tfrCtrAbsenteeInfo.initViewWindow = function(window, viewState,
		formRecord) {

	window.viewState = viewState;
	window.editForm.viewState = viewState;

	// 获取window中的button
	var buttons = window.query('button');

	var editForm = window.editForm.getForm();

	// 先将所有form中各字段设为disabled；用于readOnly会导致些css的问题，所以用disabled
	editForm.getFields().each(function(field) {
				if (field.getName() !== 'id') {
					field.setDisabled(true);
				}
			});

	// 如果状态是新增，将员工、异常状态、异常时间设为可编辑，window中所有按钮可用
	if (viewState === platform.tfrCtrAbsenteeInfo.viewState.add) {

		window.setTitle('新增外场异常人员信息');

		var transferCenterCodeField = editForm.findField('transferCenterCode');
		transferCenterCodeField.setDisabled(false);
		editForm.findField('absenteeCode').setDisabled(false);
		editForm.findField('absenteeStatus').setDisabled(false);
		editForm.findField('absentBeginDate').setDisabled(false);
		editForm.findField('absentEndDate').setDisabled(false);

		if (platform.tfrCtrAbsenteeInfo.parentTfrCtrCode) {
			transferCenterCodeField.setDisabled(true);
			transferCenterCodeField.setCombValue(
					platform.tfrCtrAbsenteeInfo.parentTfrCtrName,
					platform.tfrCtrAbsenteeInfo.parentTfrCtrCode);
		}

		for (var i = 0; i < buttons.length; i++) {
			buttons[i].setVisible(true);
		}

		return window;
	}

	// 如果非新增状态，form加载数据
	editForm.loadRecord(formRecord);

	// 单独为外场、部门、员工控件赋值
	editForm.findField('transferCenterCode').setCombValue(
			formRecord.get('transferCenterName'),
			formRecord.get('transferCenterCode'));
	editForm.findField('absenteeOrgCode').setCombValue(
			formRecord.get('absenteeOrgName'),
			formRecord.get('absenteeOrgCode'));
	editForm.findField('absenteeCode').setCombValue(
			formRecord.get('absenteeName'), formRecord.get('absenteeCode'));

	// 修改状态下只有重置按钮不可见，查看状态下只有取消按钮可见
	// 修改状态下异常状态和异常时间可编辑
	if (viewState === platform.tfrCtrAbsenteeInfo.viewState.update) {

		window.setTitle('修改外场异常人员信息');

		window.editForm.oldFormRecord = formRecord;

		for (var i = 0; i < buttons.length; i++) {
			buttons[i].setVisible(i !== 1);
		}

		editForm.findField('absenteeStatus').setDisabled(false);
		editForm.findField('absentBeginDate').setDisabled(false);
		editForm.findField('absentEndDate').setDisabled(false);

	} else if (viewState === platform.tfrCtrAbsenteeInfo.viewState.view) {

		window.setTitle('查看外场异常人员信息');

		for (var i = 0; i < buttons.length; i++) {
			buttons[i].setVisible(i === 0);
		}
	}
	return window;
};

Ext.define('Foss.platform.tfrCtrAbsenteeInfo.queryForm', {
	id : 'Foss_platform_tfrCtrAbsenteeInfo_queryForm_id',
	extend : 'Ext.form.Panel',
	title : '查询条件',
	frame : true,
	collapsible : true,
	animCollapse : true,
	defaults : {
		margin : '5 10 5 10',
		labelWidth : 85,
		columnWidth : 1 / 3,
		xtype : 'textfield'
	},
	layout : 'column',
	items : [{
				fieldLabel : '外场',
				name : 'transferCenterCode',
				xtype : 'dynamicorgcombselector',
				types : 'ORG',
				transferCenter : 'Y'
			}, {
				fieldLabel : '异常状态',
				name : 'absenteeStatus',
				xtype : 'combobox',
				queryMode : 'local',
				displayField : 'value',
				valueField : 'key',
				value : '',
				editable : false,
				store : Ext.create('Ext.data.Store', {
							fields : ['key', 'value'],
							data : [{
										"key" : "",
										"value" : "全部"
									}, {
										"key" : "RESIGNATION",
										"value" : "自离"
									}, {
										"key" : "ABSENTEEISM",
										"value" : "旷工"
									}, {
										"key" : "INDUSTRIAL_INJURY",
										"value" : "工伤"
									}, {
										"key" : "LONG_HOLIDAYS",
										"value" : "长假"
									}]
						})
			}, {
				xtype : 'commonpositionselector',
				fieldLabel : '岗位',
				name : 'absenteePostCode'
			}, {
				xtype : 'rangeDateField',
				fieldLabel : '录入时间',
				columnWidth : 2 / 3,
				fieldId : 'Foss_platform_tfrCtrAbsenteeInfo_queryForm_createDate_id',
				dateType : 'datetimefield_date97',
				dateRange : 31,
				fromName : 'createBeginDate',
				fromValue : Ext.Date.format(new Date(new Date().getFullYear(),
								new Date().getMonth(), new Date().getDate(), 0,
								0, 0), 'Y-m-d H:i:s'),
				toValue : Ext.Date.format(
						new Date(new Date().getFullYear(), new Date()
										.getMonth(), new Date().getDate(), 23,
								59, 59), 'Y-m-d H:i:s'),
				toName : 'createEndDate',
				allowBlank : false,
				disallowBlank : true
			}],
	buttons : [{
		xtype : 'button',
		columnWidth : .08,
		text : '重置',
		handler : function() {
			var form = this.up('form').getForm();
			form.reset();
			if (platform.tfrCtrAbsenteeInfo.parentTfrCtrCode) {
				form.findField('transferCenterCode').setCombValue(
						platform.tfrCtrAbsenteeInfo.parentTfrCtrName,
						platform.tfrCtrAbsenteeInfo.parentTfrCtrCode);
			}
			var now = new Date();
			form.findField('createBeginDate').setValue(Ext.Date.format(
					new Date(now.getFullYear(), now.getMonth(), now.getDate(),
							0, 0, 0), 'Y-m-d H:i:s'));
			form.findField('createEndDate').setValue(Ext.Date.format(new Date(
							now.getFullYear(), now.getMonth(), now.getDate(),
							23, 59, 59), 'Y-m-d H:i:s'));
		}
	}, '->', {
		columnWidth : .08,
		xtype : 'button',
		cls : 'yellow_button',
		text : '查询',
		handler : function() {
			var form = this.up('form').getForm();
			if (form.isValid()) {
				platform.tfrCtrAbsenteeInfo.pagingBar.moveFirst();
			}
		}
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

Ext.define('Foss.platform.tfrCtrAbsenteeInfo.tfrCtrAbsenteeInfoModel', {
			extend : 'Ext.data.Model',
			fields : [{
						name : 'id',
						type : 'string'
					}, {
						name : 'absenteeCode',
						type : 'string'
					}, {
						name : 'absenteeName',
						type : 'string'
					}, {
						name : 'absenteePostName',
						type : 'string'
					}, {
						name : 'absenteeStatus',
						type : 'string'
					}, {
						name : 'absenteeEntryDate',
						type : 'date',
						convert : dateConvert
					}, {
						name : 'absentBeginDate',
						type : 'date',
						convert : dateConvert
					}, {
						name : 'absentEndDate',
						type : 'date',
						convert : dateConvert
					}, {
						name : 'absenteeOrgCode',
						type : 'string'
					}, {
						name : 'absenteeOrgName',
						type : 'string'
					}, {
						name : 'transferCenterCode',
						type : 'string'
					}, {
						name : 'transferCenterName',
						type : 'string'
					}, {
						name : 'createUserName',
						type : 'string'
					}, {
						name : 'modifyUserName',
						type : 'string'
					}, {
						name : 'createDate',
						type : 'date',
						convert : dateConvert
					}, {
						name : 'createUser',
						type : 'string'
					}, {
						name : 'modifyDate',
						type : 'date',
						convert : dateConvert
					}, {
						name : 'modifyUser',
						type : 'string'
					},{
						name : 'restgnationNum',
						type : 'string'
					},{
						name : 'absenteeismNum',
						type : 'string'
					},{
						name : 'industrialInjuryNum',
						type : 'string'
					},{
						name : 'longHolidaysNum',
						type : 'string'
					}]
		});

Ext.define('Foss.platform.tfrCtrAbsenteeInfo.tfrCtrAbsenteeInfoStore', {
	extend : 'Ext.data.Store',
	pageSize : 25,
	model : 'Foss.platform.tfrCtrAbsenteeInfo.tfrCtrAbsenteeInfoModel',
	proxy : {
		type : 'ajax',
		actionMethods : 'POST',
		url : platform.realPath('queryPagingTfrCtrAbsenteeInfos.action'),
		reader : {
			type : 'json',
			root : 'tfrCtrAbsenteeInfoVo.tfrCtrAbsenteeInfoEntities',
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
			var queryForm = Ext
					.getCmp('Foss_platform_tfrCtrAbsenteeInfo_queryForm_id')
					.getForm();
			if (!Ext.isEmpty(queryForm)) {
				var queryParams = queryForm.getValues();
				Ext.apply(operation, {
					params : {
						'tfrCtrAbsenteeInfoVo.tfrCtrAbsenteeInfoQcDto.transferCenterCode' : queryForm
								.findField('transferCenterCode').getValue(),
						'tfrCtrAbsenteeInfoVo.tfrCtrAbsenteeInfoQcDto.createBeginDate' : queryParams.createBeginDate,
						'tfrCtrAbsenteeInfoVo.tfrCtrAbsenteeInfoQcDto.createEndDate' : queryParams.createEndDate,
						'tfrCtrAbsenteeInfoVo.tfrCtrAbsenteeInfoQcDto.absenteeStatus' : queryParams.absenteeStatus,
						'tfrCtrAbsenteeInfoVo.tfrCtrAbsenteeInfoQcDto.absenteePostCode' : queryParams.absenteePostCode
					}
				});
			}
		},
		'datachanged': function(store, operation, epots){
			var queryForm = Ext.getCmp('Foss_platform_tfrCtrAbsenteeInfo_queryForm_id').getForm();
				if (!Ext.isEmpty(queryForm)) {
					var queryParams = queryForm.getValues();
					Ext.Ajax.request({
					  url : platform.realPath('queryPagingTfrCtrAbsenteeInfos.action'),
					  params:{
							'tfrCtrAbsenteeInfoVo.tfrCtrAbsenteeInfoQcDto.transferCenterCode' : queryForm.findField('transferCenterCode').getValue(),
							'tfrCtrAbsenteeInfoVo.tfrCtrAbsenteeInfoQcDto.createBeginDate' : queryParams.createBeginDate,
							'tfrCtrAbsenteeInfoVo.tfrCtrAbsenteeInfoQcDto.createEndDate' : queryParams.createEndDate,
							'tfrCtrAbsenteeInfoVo.tfrCtrAbsenteeInfoQcDto.absenteeStatus' : queryParams.absenteeStatus,
							'tfrCtrAbsenteeInfoVo.tfrCtrAbsenteeInfoQcDto.absenteePostCode' : queryParams.absenteePostCode
						},
						success : function(response) {
						
							var json = Ext.decode(response.responseText);
							//自离人数
							var restgnationNum=json.tfrCtrAbsenteeInfoVo.restgnationNum;
							//旷工人数
						 	var absenteeismNum=json.tfrCtrAbsenteeInfoVo.absenteeismNum;
							//工伤人数
							var industrialInjuryNum=json.tfrCtrAbsenteeInfoVo.industrialInjuryNum;
							//长假人数
							var longHolidaysNum=json.tfrCtrAbsenteeInfoVo.longHolidaysNum;
							Ext.getCmp('Foss.platform.tfrCtrAbsenteeInfo.restgnationNum.id').setValue(restgnationNum);
							Ext.getCmp('Foss.platform.tfrCtrAbsenteeInfo.absenteeismNum.id').setValue(absenteeismNum);
							Ext.getCmp('Foss.platform.tfrCtrAbsenteeInfo.industrialInjuryNum.id').setValue(industrialInjuryNum);
							Ext.getCmp('Foss.platform.tfrCtrAbsenteeInfo.longHolidaysNum.id').setValue(longHolidaysNum);

						}
					});
				}
		
		}
	}
});

Ext.define('Foss.platform.tfrCtrAbsenteeInfo.tfrCtrAbsenteeInfoGrid', {
	id : 'Foss_platform_tfrCtrAbsenteeInfo_tfrCtrAbsenteeInfoGrid_id',
	extend : 'Ext.grid.Panel',
	frame : true,
	columnLines : true,
	title : '外场异常人员列表',
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	emptyText : '查询结果为空',
	autoScroll : true,
	collapsible : true,
	animCollapse : true,
	viewWindow : null,// 对应新增、修改弹出窗口
	getViewWindow : function(viewState, formRecord) {
		var me = this;
		if (Ext.isEmpty(me.viewWindow)) {
			me.viewWindow = Ext.create(
					'Foss.platform.tfrCtrAbsenteeInfo.viewWindow', {
						'viewState' : viewState,
						'sourceGrid' : me
					});
		}
		return platform.tfrCtrAbsenteeInfo.initViewWindow(me.viewWindow,
				viewState, formRecord);
	},
	exportWindow : null,// 对应导入弹出窗口
	getExportWindow : function() {
		var me = this;
		if (Ext.isEmpty(me.exportWindow)) {
			me.exportWindow = Ext
					.create('Foss.platform.tfrCtrAbsenteeInfo.importWindow');
		}
		return me.exportWindow;
	},
	dockedItems:[{
			   xtype:'toolbar',
			   dock:'bottom',
			   layout:'column',
			   defaults:{
				 xtype:'textfield',
				 value:'0',
				 readOnly:true,
				 labelWidth:80,
				 width:30
			   },
			   items:[/*{
				   id:'Foss.platform.tfrCtrAbsenteeInfo.gatherMessage.id',
				   fieldLabel:platform.tfrCtrAbsenteeInfo.i18n('Foss.platform.tfrCtrAbsenteeInfo.gatherMessage'),//汇总信息：	
				   columnWidth:.15,
				   dataIndex: 'restgnationNum'
			   },*/{
				   id:'Foss.platform.tfrCtrAbsenteeInfo.restgnationNum.id',
				   fieldLabel:platform.tfrCtrAbsenteeInfo.i18n('Foss.platform.tfrCtrAbsenteeInfo.restgnationNum'),//自离人数	
				   columnWidth:.15,
				   dataIndex: 'restgnationNum'
			   },{
				   id:'Foss.platform.tfrCtrAbsenteeInfo.absenteeismNum.id',
				   fieldLabel:platform.tfrCtrAbsenteeInfo.i18n('Foss.platform.tfrCtrAbsenteeInfo.absenteeismNum'),//旷工人数
				   columnWidth:.15,
				   dataIndex: 'absenteeismNum'
			   },{
				   id:'Foss.platform.tfrCtrAbsenteeInfo.industrialInjuryNum.id',
				   fieldLabel:platform.tfrCtrAbsenteeInfo.i18n('Foss.platform.tfrCtrAbsenteeInfo.industrialInjuryNum'),//工伤人数
				   columnWidth:.15,
				   dataIndex: 'industrialInjuryNum'
			   },{
				   id:'Foss.platform.tfrCtrAbsenteeInfo.longHolidaysNum.id',
				   fieldLabel:platform.tfrCtrAbsenteeInfo.i18n('Foss.platform.tfrCtrAbsenteeInfo.longHolidaysNum'),//长假人数
				   columnWidth:.15,
				   dataIndex: 'longHolidaysNum'
			   }]
		}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext
				.create('Foss.platform.tfrCtrAbsenteeInfo.tfrCtrAbsenteeInfoStore');
		me.selModel = Ext.create('Ext.selection.CheckboxModel', {
					mode : 'SIMPLE',
					checkOnly : true
				});
		me.tbar = [{
			xtype : 'button',
			text : '新增',
			handler : function() {
				me.getViewWindow(platform.tfrCtrAbsenteeInfo.viewState.add)
						.show();
			}
		}, {
			disabled : !platform.tfrCtrAbsenteeInfo
					.isPermission('queryTfrCtrAbsenteeIndex/tfrCtrAbsenteeInfoGridDeleteButton'),// 权限
			hidden : !platform.tfrCtrAbsenteeInfo
					.isPermission('queryTfrCtrAbsenteeIndex/tfrCtrAbsenteeInfoGridDeleteButton'),// 权限
			xtype : 'button',
			text : '删除',
			handler : function() {
				var grid = this.up('grid'), modelArray = grid
						.getSelectionModel().getSelection();
				if (Ext.isEmpty(modelArray)) {
					Ext.ux.Toast.msg('提示', '请选择要删除的记录!', 'error', 2000);
					return;
				}

				var ids = [];
				for (var index in modelArray) {
					ids.push(modelArray[index].data.id);
				}

				platform.tfrCtrAbsenteeInfo
						.deleteTfrCtrAbsenteeInfos(ids, grid);
			}
		}, {
			xtype : 'button',
			text : '导入',
			handler : function() {
				me.getExportWindow().show();
			}
		}, {
			xtype : 'button',
			text : '导出',
			handler : function() {

				var grid = this.up('grid'), modelArray = grid
						.getSelectionModel().getSelection(), params = null;

				if (Ext.isEmpty(modelArray)) {
					// 获取查询参数
					var form = Ext
							.getCmp('Foss_platform_tfrCtrAbsenteeInfo_queryForm_id')
							.getForm(), queryParams = form.getValues();

					if (!form.isValid()) {
						Ext.ux.Toast.msg('提示', '请输入合法的导出条件！', 'error', 2000);
						return;
					}

					params = {
						'tfrCtrAbsenteeInfoVo.tfrCtrAbsenteeInfoQcDto.transferCenterCode' : form
								.findField('transferCenterCode').getValue(),
						'tfrCtrAbsenteeInfoVo.tfrCtrAbsenteeInfoQcDto.createBeginDate' : queryParams.createBeginDate,
						'tfrCtrAbsenteeInfoVo.tfrCtrAbsenteeInfoQcDto.createEndDate' : queryParams.createEndDate,
						'tfrCtrAbsenteeInfoVo.tfrCtrAbsenteeInfoQcDto.absenteeStatus' : queryParams.absenteeStatus,
						'tfrCtrAbsenteeInfoVo.tfrCtrAbsenteeInfoQcDto.absenteePostCode' : queryParams.absenteePostCode
					};
				} else {
					var ids = [];
					for (var index in modelArray) {
						ids.push(modelArray[index].data.id);
					}

					params = {
						'tfrCtrAbsenteeInfoVo.ids' : ids
					};
				}

				if (!Ext.fly('downloadAttachFileForm')) {
					var frm = document.createElement('form');
					frm.id = 'downloadAttachFileForm';
					frm.style.display = 'none';
					document.body.appendChild(frm);
				}

				Ext.Ajax.request({
					url : platform.realPath('exportTfrCtrAbsenteeInfos.action'),
					form : Ext.fly('downloadAttachFileForm'),
					method : 'POST',
					params : params,
					isUpload : true,
					success : function(response) {

					},
					exception : function(response) {
						top.Ext.MessageBox.alert('导出失败'/* '导出失败' */,
								result.message);
					}
				});
			}
		}, '->', {
			xtype : 'button',
			text : '模版下载',
			iconCls : 'deppon_icons_downloadOnBtn',
			handler : function() {
				platform.tfrCtrAbsenteeInfo.downloadTemplate();
			}
		}];
		me.columns = [{
			xtype : 'actioncolumn',
			width : 40,
			text : '操作',
			align : 'center',
			menuDisabled : true,
			hideable : false,
			sortable : false,
			items : [{
				tooltip : '修改',
				iconCls : 'deppon_icons_edit',
				handler : function(grid, rowIndex, colIndex) {
					var formRecord = grid.getStore().getAt(rowIndex);
					me.getViewWindow(
							platform.tfrCtrAbsenteeInfo.viewState.update,
							formRecord).show();
				}
			} /*
				 * , { tooltip : '删除', iconCls : 'deppon_icons_cancel', handler :
				 * function(grid, rowIndex, colIndex) { ids =
				 * [grid.store.getAt(rowIndex).data.id];
				 * platform.tfrCtrAbsenteeInfo.deleteTfrCtrAbsenteeInfos(ids,
				 * grid); } }
				 */]
		}, {
			dataIndex : 'transferCenterName',
			align : 'center',
			width : 120,
			text : '外场'
		}, {
			dataIndex : 'absenteeOrgName',
			align : 'center',
			width : 180,
			text : '部门'
		}, {
			dataIndex : 'absenteeName',
			align : 'center',
			width : 85,
			text : '姓名',
			renderer : function(value, meta, record, rowIndex, celIndex) {
				var v = "'" + rowIndex + "'";
				return Ext.isEmpty(value)
						? ''
						: '<a href="javascript:platform.tfrCtrAbsenteeInfo.viewWindowShow('
								+ v + ')">' + value + '</a>';
			}
		}, {
			dataIndex : 'absenteeCode',
			align : 'center',
			width : 85,
			text : '工号'
		}, {
			dataIndex : 'absenteePostName',
			align : 'center',
			width : 120,
			text : '岗位'
		}, {
			dataIndex : 'absenteeEntryDate',
			align : 'center',
			width : 100,
			text : '入职时间',
			xtype : 'datecolumn',
			format : 'Y-m-d'
		}, {
			dataIndex : 'absentBeginDate',
			align : 'center',
			width : 110,
			text : '异常开始时间',
			xtype : 'datecolumn',
			format : 'Y-m-d'
		}, {
			dataIndex : 'absentEndDate',
			align : 'center',
			width : 110,
			text : '异常结束时间',
			xtype : 'datecolumn',
			format : 'Y-m-d'
		}, {
			dataIndex : 'absenteeStatus',
			align : 'center',
			width : 85,
			text : '异常状态',
			renderer : function(value) {
				if (value == 'RESIGNATION') {
					return '自离';
				}
				if (value == 'ABSENTEEISM') {
					return '旷工';
				}
				if (value == 'INDUSTRIAL_INJURY') {
					return '工伤';
				}
				if (value == 'LONG_HOLIDAYS') {
					return '长假';
				}
				return value;
			}
		}, {
			dataIndex : 'createUserName',
			align : 'center',
			width : 85,
			text : '创建人'
		}, {
			dataIndex : 'createDate',
			align : 'center',
			width : 150,
			text : '录入时间',
			xtype : 'datecolumn',
			format : 'Y-m-d H:i:s'
		}, {
			dataIndex : 'modifyUserName',
			align : 'center',
			width : 85,
			text : '修改人'
		}, {
			dataIndex : 'modifyDate',
			align : 'center',
			width : 150,
			text : '修改时间',
			xtype : 'datecolumn',
			format : 'Y-m-d H:i:s'
		}];
		
		me.bbar = Ext.create('Deppon.StandardPaging', {
					store : me.store,
					pageSize : 25,
					maximumSize : 200,
					plugins : Ext.create('Deppon.ux.PageSizePlugin', {
								sizeList : [['25', 25], ['50', 50],
										['100', 100], ['200', 200]]
							})
				});
		platform.tfrCtrAbsenteeInfo.pagingBar = me.bbar;
		me.callParent([cfg]);
		
	}
});

Ext.define('Foss.platform.tfrCtrAbsenteeInfo.viewForm', {
	extend : 'Ext.form.Panel',
	title : '外场异常人员信息',
	frame : true,
	collapsible : true,
	animCollapse : true,
	defaults : {
		margin : '5 10 5 10',
		labelWidth : 85,
		columnWidth : 1 / 4
	},
	layout : 'column',
	viewState : null,
	oldFormRecord : null,
	items : [{
				xtype : 'textfield',
				name : 'id',
				hidden : true
			}, {
				fieldLabel : '外场',
				name : 'transferCenterCode',
				xtype : 'dynamicorgcombselector',
				types : 'ORG',
				transferCenter : 'Y',
				allowBlank : false
			}, {
				fieldLabel : '部门',
				name : 'absenteeOrgCode',
				xtype : 'dynamicorgcombselector',
				types : 'ORG',
				allowBlank : false
			}, {
				xtype : 'datefield',
				fieldLabel : '录入时间',
				name : 'createDate',
				value : new Date(),
				format : 'Y-m-d g:i:s',
				allowBlank : false,
				disabled : true
			}, {
				xtype : 'datefield',
				fieldLabel : '入职时间',
				name : 'absenteeEntryDate',
				format : 'Y-m-d',
				allowBlank : false,
				disabled : true
			}, {
				fieldLabel : '工号',
				name : 'absenteeCode',
				xtype : 'commonemployeeselector',
				allowBlank : false,
				displayField : 'empCode',
				listeners : {
					blur : function(field) {
						if (!Ext.isEmpty(field.getValue())) {
							var absenteeCode = field.getValue(), record = field.store
									.findRecord('empCode', absenteeCode, false,
											true, true);
							if (!Ext.isEmpty(record)) {
								var absenteeName = record.get('empName'), absenteePostCode = record
										.get('title'), absenteePostName = record
										.get('titleName'), absenteeOrg = record
										.get('department'), absenteeOrgCode = absenteeOrg.code, absenteeOrgName = absenteeOrg.name, absenteeEntryDate = record
										.get('enterDate'), form = this
										.up('form').getForm();

								form.findField('absenteeName')
										.setValue(absenteeName);
								form.findField('absenteePostCode')
										.setValue(absenteePostCode);
								form.findField('absenteePostName')
										.setValue(absenteePostName);
								form.findField('absenteeEntryDate')
										.setValue(new Date(absenteeEntryDate));
								form.findField('absenteeOrgCode').setCombValue(
										absenteeOrgName, absenteeOrgCode);
							}

						}
					}
				}
			}, {
				fieldLabel : '异常状态',
				name : 'absenteeStatus',
				xtype : 'combobox',
				queryMode : 'local',
				displayField : 'value',
				valueField : 'key',
				editable : false,
				allowBlank : false,
				store : Ext.create('Ext.data.Store', {
							fields : ['key', 'value'],
							data : [{
										"key" : "RESIGNATION",
										"value" : "自离"
									}, {
										"key" : "ABSENTEEISM",
										"value" : "旷工"
									}, {
										"key" : "INDUSTRIAL_INJURY",
										"value" : "工伤"
									}, {
										"key" : "LONG_HOLIDAYS",
										"value" : "长假"
									}]
						})
			}, {
				xtype : 'textfield',
				fieldLabel : '姓名',
				name : 'absenteeName',
				allowBlank : false,
				disabled : true
			}, {
				xtype : 'hiddenfield',
				fieldLabel : '岗位',
				name : 'absenteePostCode',
				allowBlank : false
			}, {
				xtype : 'textfield',
				fieldLabel : '岗位',
				name : 'absenteePostName',
				allowBlank : false,
				disabled : true
			}, {
				xtype : 'rangeDateField',
				fieldLabel : '异常时间',
				columnWidth : 1 / 2,
				dateType : 'datefield',
				fromName : 'absentBeginDate',
				toName : 'absentEndDate',
				fromValue : new Date(),
				toValue : new Date(),
				allowBlank : false,
				disallowBlank : true
			}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

Ext.define('Foss.platform.tfrCtrAbsenteeInfo.viewWindow', {
			extend : 'Ext.window.Window',
			closable : true,
			modal : true,
			resizable : false,
			closeAction : 'hide',
			width : 1200,
			height : 300,
			editForm : null,// 对应Foss.platform.tfrCtrAbsenteeInfo.viewForm，
			viewState : null,// 状态；新增、修改、查看
			sourceGrid : null,// 对应Foss.platform.tfrCtrAbsenteeInfo.tfrCtrAbsenteeInfoGrid
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
				if (me.viewState === platform.tfrCtrAbsenteeInfo.viewState.add) {
					var form = me.editForm.getForm();
					if (platform.tfrCtrAbsenteeInfo.parentTfrCtrCode) {
						form.findField('transferCenterCode').setCombValue(
								platform.tfrCtrAbsenteeInfo.parentTfrCtrName,
								platform.tfrCtrAbsenteeInfo.parentTfrCtrCode);
					}

					var now = new Date();
					form.findField('absentBeginDate').setValue(now);
					form.findField('absentEndDate').setValue(now);
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
							text : '重置',
							handler : function() {
								me.resetData();
								me.initData();
							}
						}, {
							text : '保存',
							cls : 'yellow_button',
							handler : function() {
								platform.tfrCtrAbsenteeInfo
										.submitTfrCtrAbsenteeInfos(me);

							}
						}];
				me.viewState = config.viewState;
				me.editForm = Ext
						.create('Foss.platform.tfrCtrAbsenteeInfo.viewForm');
				me.items = [me.editForm];
				me.callParent([cfg]);
			}
		});

// 导入表单
Ext.define('Foss.platform.tfrCtrAbsenteeInfo.importForm', {
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
									.realPath('importTfrCtrAbsenteeInfos.action'),
							success : function(form, action) {
								myMask.hide();
								Ext.ux.Toast.msg('提示', '导入成功', 'error', 1000);
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
Ext.define('Foss.platform.tfrCtrAbsenteeInfo.importWindow', {
			extend : 'Ext.window.Window',
			title : '导入外场异常人员信息',
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
			items : [Ext.create('Foss.platform.tfrCtrAbsenteeInfo.importForm')]
		});

// 入口函数
Ext.onReady(function() {
			// 创建查询form
			var queryForm = Ext
					.create('Foss.platform.tfrCtrAbsenteeInfo.queryForm');
			// 创建查询grid
			var queryResult = Ext
					.create('Foss.platform.tfrCtrAbsenteeInfo.tfrCtrAbsenteeInfoGrid');

			platform.tfrCtrAbsenteeInfo.tfrCtrAbsenteeInfoGrid=	queryResult;
			// 判断当前部门是否外场或外场子部门，对查询form外场控件初始化
			if (platform.tfrCtrAbsenteeInfo.parentTfrCtrCode) {
				var transferCenterCodeField = queryForm.getForm()
						.findField('transferCenterCode');

				transferCenterCodeField.setDisabled(true);
				transferCenterCodeField.setCombValue(
						platform.tfrCtrAbsenteeInfo.parentTfrCtrName,
						platform.tfrCtrAbsenteeInfo.parentTfrCtrCode);
			} else {
				// 当前部门非外场（子部门）时，只有导出按钮可见
				var buttons = queryResult.query('button');
				for (var i = 0; i < buttons.length; i++) {
					buttons[i].setVisible(i === 3);
				}
				// 当前部门非外场（子部门）时，将第一列操作列隐藏
				queryResult.columns[0].hidden = true;
			}

			Ext.create('Ext.panel.Panel', {
						id : 'T_platform-queryTfrCtrAbsenteeIndex_content',
						cls : "panelContentNToolbar",
						bodyCls : 'panelContent-body',
						layout : 'auto',
						items : [queryForm, queryResult],
						renderTo : 'T_platform-queryTfrCtrAbsenteeIndex-body'
					});
		});