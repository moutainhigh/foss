/**
 * 这个类用来显示人工申请车辆的操作
 */
Ext.define('Foss.departure.TemporaryAssignmentsModel', {
			extend : 'Ext.data.Model',
			idProperty : 'id',
			fields : [{
						name : 'id',
						type : 'string',
						hiddenField : true
					}, {
						name : 'vehicleNo',
						type : 'string'
					}, {
						name : 'truckOrgName',
						type : 'string'
					}, {
						name : 'truckType',
						type : 'string'
					}, {
						name : 'departPlanType',
						type : 'string'
					}, {
						name : 'planDepartTime',
						convert : dateConvert,
						type : 'date'
					}, {
						name : 'planEndTime',
						convert : dateConvert,
						type : 'date'
					}, {
						name : 'driverName',
						type : 'string'
					}, {
						name : 'driverPhone',
						type : 'string'
					}, {
						name : 'isChecked',
						type : 'string'
					}]
		});

Ext.define('Foss.departure.VehicleOwnerTypeStore', {
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
					code : '',
					name : '全部'
				},{
					code : 'Company',
					name : '自有'
				}, {
					code : 'Leased',
					name : '外请'
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
Ext.define('Foss.departure.TemporaryAssignmentsStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.departure.TemporaryAssignmentsModel',
	pageSize : 10,
	autoLoad : true,
	proxy : {
		// 以JSON的方式加载数据
		type : 'ajax',
		actionMethods : 'POST',
		url : departure.realPath('queryTemporaryAssignments.action'),
		reader : {
			type : 'json',
			root : 'artificialDepartVO.departList',
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
			var queryParams = departure.temporaryPanel.getQueryForm().getForm()
					.getValues();
			Ext.apply(operation, {
				params : {
					'artificialDepartVO.queryEntity.vehicleNo' : queryParams.vehicleNo,
					'artificialDepartVO.queryEntity.truckOrgCode' : queryParams.truckOrgCode,
					'artificialDepartVO.queryEntity.applyOrgCode' : queryParams.applyOrgCode,
					'artificialDepartVO.queryEntity.departPlanType' : queryParams.departPlanType,
					'artificialDepartVO.queryEntity.beginTime' : queryParams.beginTime
				}
			});
		},
		load :function(store, operation, eOpts){
		  if(store.totalCount==0)
		  {
		     Ext.ux.Toast.msg(departure.i18n('tfr.departure.DepartGrid.tips.label'), departure.i18n('tfr.departure.TemporaryAssignmentsStore.result.empty.tips'), 'ok', 3000);
		  }
		}
	}
});

// 打开车辆详细页面
Ext.define('Foss.departure.TemporaryAssignmentsGridPanel', {
	extend : 'Ext.grid.Panel',
	sortableColumns : false,
	enableColumnHide : false,
	enableColumnMove : false,
	hidden:true,
	id:'Foss_departure_TemporaryAssignmentsGridPanel_ID',
	// 自动增加滚动条
	autoScroll : true,
	// 增加表格列的分割线
	columnLines : true,
	bodyStyle : 'width:100%;',
	// 表格对象增加一个边框
	frame : true,
	selModel : Ext.create('Ext.selection.RadioModel'),
	columns : [{
				text :  departure.i18n('tfr.departure.TemporaryAssignmentsGridPanel.grid.vehicleNo.label'),//'车牌号',
				dataIndex : 'vehicleNo',
				width : 150
			}, {
				text :  departure.i18n('tfr.departure.TemporaryAssignmentsGridPanel.grid.truckOrgName.label'),//'车辆所属部门',
				dataIndex : 'truckOrgName',
				width : 150
			}, {
				text :  departure.i18n('tfr.departure.TemporaryAssignmentsGridPanel.grid.truckType.label'),//'车辆归属类型',
				dataIndex : 'truckType',
				width : 150,
				renderer : function(value) {
					var store = Ext.create('Foss.departure.VehicleOwnerTypeStore'), record = store
							.findRecord('code', value);
					if (record != null) {
						return record.get('name');
					} else {
						return '';
					}
				}
			}, {
				text :  departure.i18n('tfr.departure.TemporaryAssignmentsGridPanel.grid.departPlanType.label'),//'车辆业务类型',
				dataIndex : 'departPlanType',
				width : 150,
				renderer : function(value) {
					var store = Ext.create('Foss.departure.DepartItemsStore'), record = store
							.findRecord('code', value);
					if (record != null) {
						return record.get('name');
					} else {
						return '';
					}
				}
			}, {
				text :  departure.i18n('tfr.departure.TemporaryAssignmentsGridPanel.grid.planDepartTime.label'),//'起始日期',
				dataIndex : 'planDepartTime',
				xtype : 'datecolumn',
				format : 'Y-m-d',
				width : 150
			}, {
				text :  departure.i18n('tfr.departure.TemporaryAssignmentsGridPanel.grid.planEndTime.label'),//'截至日期',
				dataIndex : 'planEndTime',
				xtype : 'datecolumn',
				format : 'Y-m-d',
				width : 150
			}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.departure.TemporaryAssignmentsStore');
		me.bbar = Ext.create('Deppon.StandardPaging', {
					store : me.store
				});
		departure.temPagingBar = me.bbar;
		me.tbar = [{
			xtype : 'button',
			// text: lineinfo.i18n('foss.lineinfo.new'),
			text :  departure.i18n('tfr.departure.TemporaryAssignmentsGridPanel.grid.button'),//'任务确认',
			gridContainer : this,
			handler : function() {
				var me = departure.temporaryPanel.getTemporaryGrid();
			    var rowObjs = me.getSelectionModel().getSelection();
			    if(rowObjs.length<=0)
			    {
			    	Ext.MessageBox.alert(departure.i18n('tfr.departure.DepartGrid.tips.label'),departure.i18n('tfr.departure.TemporaryAssignmentsGridPanel.grid.tips'));
			    	return false;
			    }
			    departure.temporaryPanel.getTaskConfirmWindow().getTaskConfirmForm().getForm().reset();
				departure.temporaryPanel.getTaskConfirmWindow().show();
			}
		}];
		me.callParent([cfg]);
	},
	listeners : {
		select : function(rowModel, record, index, eOpts) {
			record.set('isChecked', '1');
		},
		deselect : function(rowModel, record, index, eOpts) {
			record.set('isChecked', '0');
		}
	}

});

Ext.define('Foss.departure.TaskConfirmWindow', {
			extend : 'Ext.window.Window',
			id : 'Foss.departure.TaskConfirmWindow_ID',
			// title: lineinfo.i18n('foss.lineinfo.windowTitle'),
			title : departure.i18n('tfr.departure.TaskConfirmWindow.window.title'),//'请填写司机信息',
			bodyCls : 'autoHeight',
			cls : 'autoHeight',
			modal : true,
			closeAction : 'hide',
			width : 400,
			taskConfirmForm : null,
			getTaskConfirmForm : function() {
				if (this.taskConfirmForm == null) {
					this.taskConfirmForm = Ext
							.create('Foss.departure.TaskConfirmForm')
				}
				return this.taskConfirmForm;
			},
			constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				me.items = [me.getTaskConfirmForm()];
				departure.taskConfirmForm = me.getTaskConfirmForm();
				me.callParent([cfg]);
			}
		});
// 打印纸质放行条的form
Ext.define('Foss.departure.TaskConfirmForm', {
	extend : 'Ext.form.Panel',
	layout : 'column',
	frame : true,
	border : true,
	defaults : {
		margin : '5 5 5 5',
		columns : 4
	},
	items : [{
				xtype : 'commondriverselector',
				fieldLabel :departure.i18n('tfr.departure.TaskConfirmForm.grid.driverCode.label'),// '司机姓名',
				name : 'driverCode',
				id:'Foss.departure.TaskConfirmForm.DRIVER_CODE',
				columnWidth : 1,
				allowBlank : false,
				listeners: {
					'select': function(field, records, eOpts) {
						var record = records[0];
						this.up('form').getForm().findField('driverName').setValue(record.get('empName'));
						this.up('form').getForm().findField('driverCode').setValue(record.get('empCode'));
					}
				}
			},  {
				xtype : 'textfield',
				name : 'driverName',
				hidden:true,
				columnWidth : 1
			},  {
				xtype : 'textfield',
				fieldLabel : departure.i18n('tfr.departure.TaskConfirmForm.grid.driverName.label'),//'司机电话',
				id:'Foss.departure.TaskConfirmForm.DRIVER_PHONE',
				name : 'driverPhone',
				maxLength:20,
				maxLengthText: '输入的字符数不能超过20个',
				columnWidth : 1
			}, {
				xtype : 'container',
				columnWidth : .6,
				html : '&nbsp;'
			}, {
				xtype : 'button',
				text :departure.i18n('tfr.departure.TemporaryAssignmentsGridPanel.grid.button'),// '确认',
				columnWidth : .4,
				handler : function() {
					var me = departure.temporaryPanel.getTemporaryGrid();
					if (!departure.temporaryPanel.getTaskConfirmWindow().getTaskConfirmForm().getForm()
							.isValid())
						return false;
					var temporaryStore = me.getStore(), array = new Array();
					var records = temporaryStore.getRange(0,
							temporaryStore.data.items.length);
					var rowObjs = me.getSelectionModel().getSelection();
					if (rowObjs.length < 1) {
						Ext.MessageBox.alert(departure.i18n('tfr.departure.TaskConfirmForm.grid.button'));
						return false;
					}
					
					for (var i = 0; i < rowObjs.length; i++) {
						array.push(rowObjs[i].data);
					}
					var taskParams = departure.taskForm.getForm().getValues();
					var newEntity = {
						artificialDepartVO : {
							departList : array,
							driverCode:this.up('form').getForm().getValues().driverCode,
							driverName:this.up('form').getForm().getValues().driverName,
							driverPhone:this.up('form').getForm().getValues().driverPhone
						}
					};
					Ext.Ajax.request({
								url : departure.realPath('lmsDepart.action'),
								// params : params,
								jsonData : newEntity,
								success : function(response) {
									var result = Ext.decode(response.responseText);
									if (result.artificialDepartVO.operatStatus == '5') {
										Ext.MessageBox.alert(departure.i18n('tfr.departure.DepartGrid.tips.label'),departure.i18n('tfr.departure.TaskConfirmForm.grid.notToday.tips'));
									} else {
										Ext.ux.Toast.msg(departure.i18n('tfr.departure.DepartGrid.tips.label'), departure.i18n('tfr.departure.TaskConfirmForm.grid.ok.tips'), 'ok', 3000);
										departure.temPagingBar.moveFirst();
										departure.temporaryPanel.getTaskConfirmWindow().hide();
									}
								}
							});
				}
			}],

	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});
Ext.define('Foss.departure.TemporaryAssignments.QueryLMSForm', {
	extend : 'Ext.form.Panel',
	layout : 'column',
	frame : true,
	border : false,
	height : 110,
	defaults : {
		margin : '5 5 5 5',
		columns : 4
	},
	items : [{
		xtype : 'commontruckselector',
		fieldLabel : departure.i18n('tfr.departure.TemporaryAssignments.QueryLMSForm.grid.vehicleNo.label'),//'车牌号',
		name : 'vehicleNo',
		id : 'Foss.departure.TemporaryAssignments.VEHICLE_NO_ID',
		columnWidth : .25
			// labelWidth: 80
		}, {
		xtype : 'dynamicorgcombselector',
		fieldLabel : departure.i18n('tfr.departure.TemporaryAssignments.QueryLMSForm.grid.truckOrgCode.label'),//'车辆所属部门',
		id : 'Foss.departure.TemporaryAssignments.TRUCK_ORG_ID',
		name : 'truckOrgCode',
		columnWidth : .25
	}, {
		xtype : 'datefield',
		fieldLabel : departure.i18n('tfr.departure.TemporaryAssignments.QueryLMSForm.grid.beginTime.label'),//'操作时间',
		id : 'Foss_departure_TemporaryAssignments_RANGEDATE_ID',
		name : 'beginTime',
		allowBlank : false,
		// dateRange : 30,
		value : Ext.Date.format(new Date(new Date().getFullYear(),
						new Date().getMonth(), new Date().getDate()), 'Y-m-d'),
		format : 'Y-m-d',
		columnWidth : .25
	}, {
		xtype : 'checkboxgroup', // 复选框组
		columns : 2, // 3列来存放选择框项
		vertical : true, // 按照columns中指定的列数来分配复选框组件
		allowBlank : false,
		columnWidth : .25,
		blankText : departure.i18n('tfr.departure.PrintManualForm.no.item.label'),//'没有选择项',// 当没有选择时的提示信息
		items : [{
			boxLabel : departure.i18n('tfr.departure.PrintManualForm.year.verification.label'),//'年审/季审',
			name : 'departPlanType', // 表单的参数名
			inputValue : 'NO_TASK_YEAR_VERIFICATION', // 表单的参数值
			checked : true
				// 选中
			}, {
			boxLabel : departure.i18n('tfr.departure.PrintManualForm.keep.label'),//'保养',
			name : 'departPlanType', // 表单的参数名
			inputValue : 'NO_TASK_KEEP', // 表单的参数值
			checked : true
				// 选中
			}]
	}, {
		border : 1,
		xtype : 'container',
		columnWidth : 1,
		defaultType : 'button',
		layout : 'column',
		items : [ {
					text : departure.i18n('tfr.departure.QueryForm.form.reset.button'),//'重置',
					columnWidth : .15,
					handler : function() {
						departure.temporaryPanel.getQueryForm().getForm()
								.reset();
						Ext
								.getCmp("Foss_departure_TemporaryAssignments_RANGEDATE_ID")
								.setDefaultValue();
					}
				},{
					xtype : 'container',
					columnWidth : .6,
					html : '&nbsp;'
				}, {
					xtype : 'container',
					columnWidth : .1,
					html : '&nbsp;'
				}, {
					text : departure.i18n('tfr.departure.QueryForm.form.query.button'),//'查询',
					cls:'yellow_button',
					columnWidth : .15,
					handler : function() {
						if (!departure.temporaryPanel.getQueryForm().getForm()
							.isValid())
							return false;
						departure.temPagingBar.moveFirst();
						departure.temporaryPanel.getTemporaryGrid().show();
					}
				}]
	}]
});

Ext.define('Foss.departure.TemporaryAssignments.AddForm', {
	extend : 'Ext.form.Panel',
	layout : 'column',
	frame : true,
	title : departure.i18n('tfr.departure.TemporaryAssignments.AddForm.form.title'),//"录入框",
	border : false,
	defaults : {
		margin : '5 5 5 5',
		columns : 4
	},
	items : [{
				xtype : 'textfield',
				name : 'id',
				hidden : true,
				hideLabel : true
			}, {
				xtype : 'commonowntruckselector',
				fieldLabel :departure.i18n('tfr.departure.TemporaryAssignments.AddForm.form.vehicleNo.label'),// '车牌号',
				name : 'vehicleNo',
				id : 'Foss.departure.TemporaryAssignments.VEHICLENO_ID',
				columnWidth : .25,
				labelWidth : 60,
				allowBlank : false
//				listeners: {
//					'select': function(field, records, eOpts) {
//						var record = records[0],
//						vehicleNo = record.get('vehicleNo');
//						//查询该车牌号是不是外请车的，是的话需要给外请车司机赋值
//						var artificialDepartVO = {
//						'artificialDepartVO.vehicleNo' : vehicleNo
//					};
////						Ext.Ajax.request({//通过车牌号查找司机信息
////								url : departure.realPath('queryDriverByVehicleNo.action'),
////								params : artificialDepartVO,
////								success : function(response) {
////									var result = Ext
////											.decode(response.responseText);
////								    if(result.artificialDepartVO.relationInfoEntity)
////								    {
////									departure.temporaryPanel.getAddForm().getForm().findField('driverName').setValue(result.artificialDepartVO.relationInfoEntity.driverName);
////									departure.temporaryPanel.getAddForm().getForm().findField('driverCode').setValue(result.artificialDepartVO.relationInfoEntity.driverCode);
////								    }
////								}
////							});
//					}
//////					specialKey:function(textfield, e){ //将英文车牌号转换为中文（YUE-X-20000------>粤X20000）modify by liangfuxiang 2013-04-09
//////					if(e.getKey() == Ext.EventObject.ENTER){
//////						var oldVehicleNo=this.up('form').getForm().findField('vehicleNo').getValue();
//////						if(oldVehicleNo!=null && oldVehicleNo!=''){
//////							if(oldVehicleNo.indexOf("-")!=-1){//包含两个'-'
//////								departure.convertVehicleNo(this,oldVehicleNo);
//////							}
//////						}
//////				}
//////			}
//				}

			}, {
				xtype : 'container',
				columnWidth : .05,
				html : '&nbsp;'
			}, {
				xtype : 'commondriverselector',
				fieldLabel : departure.i18n('tfr.departure.TemporaryAssignments.AddForm.form.driverName.label'),//'司机（公司车必填）',
				name : 'driverName',
				id : 'Foss.departure.TemporaryAssignments.DRIVER_NAME_ID',
				columnWidth : .4,
				labelWidth : 120,
				allowBlank : false,
				listeners: {
					'select': function(field, records, eOpts) {
						var record = records[0];
						departure.temporaryPanel.getAddForm().getForm().findField('driverName').setValue(record.get('empName'));
						departure.temporaryPanel.getAddForm().getForm().findField('driverCode').setValue(record.get('empCode'));
					}
				}
			}, {
				xtype : 'hiddenfield',
				name : 'driverCode',
				id : 'Foss.departure.TemporaryAssignments.DRIVER_CODE_ID',
				columnWidth : .4,
				labelWidth : 120,
				allowBlank : false
			}, {
				xtype : 'container',
				columnWidth : .05,
				html : '&nbsp;'
			}, {
				xtype : 'commonowntruckselector',
//				deptCode:'Y',
				myQueryParam : 'containerCode',
				showContent : '{containerCode}',
				displayField : 'containerCode',// 显示名称
				valueField : 'containerCode',
				fieldLabel : departure.i18n('tfr.departure.TemporaryAssignments.AddForm.form.containerNo.label'),//'货柜号',
				name : 'containerNo',
				id : 'Foss.departure.TemporaryAssignments.CONTAINER_NO_ID',
				columnWidth : .25,
				labelWidth : 60
			}, {
				xtype : 'radiogroup', // 单选框组
				fieldLabel : departure.i18n('tfr.departure.PrintManualForm.temp.depart.type.label'),//'临时放行类型', // 复选框组的字段标签
				columns : 3, // 3列来存放选择框项
				id : 'Foss.departure.TemporaryAssignments.DEPART_ITEMS_ID',
				vertical : true, // 按照columns中指定的列数来分配单选框组
				// 验证表单输入不能为空，属性值：true表示可以为空，false表示不可以为空
				// allowBlank : false,
				frame : true,
				columnWidth : .97,
				blankText : departure.i18n('tfr.departure.PrintManualForm.no.item.label'),//'没有选择项',// 当没有选择时的提示信息
				items : [{
							boxLabel :departure.i18n('tfr.departure.PrintManualForm.keep.label'),// '保养',
							name : 'departItems', // 表单的参数名
							inputValue : 'NO_TASK_KEEP', // 表单的参数值
							checked : true
						}, {
							boxLabel : departure.i18n('tfr.departure.PrintManualForm.year.verification.label'),//'年审/季审',
							name : 'departItems', // 表单的参数名
							inputValue : 'NO_TASK_YEAR_VERIFICATION' // 表单的参数值
						}, {
							boxLabel :departure.i18n('tfr.departure.PrintManualForm.appoint.label'),// '临时放空出发/约车',
							name : 'departItems', // 表单的参数名
							inputValue : 'NO_TASK_APPOINT' // 表单的参数值
						}, {
							boxLabel : departure.i18n('tfr.departure.PrintManualForm.oil.label'),//'加油',
							name : 'departItems', // 表单的参数名
							inputValue : 'NO_TASK_OIL' // 表单的参数值
						}, {
							boxLabel :departure.i18n('tfr.departure.PrintManualForm.repair.label'),// '维修',
							name : 'departItems', // 表单的参数名
							inputValue : 'NO_TASK_REPAIR' // 表单的参数值
						}, {
							boxLabel :departure.i18n('tfr.departure.PrintManualForm.others.label'),// '其他任务出发',
							name : 'departItems', // 表单的参数名
							inputValue : 'NO_TASK_OTHERS' // 表单的参数值
						}]
			}, {
				xtype : 'textarea',
				// 当内容超过文本域的范围时，出现滚动条
				autoScroll : true,
				// 指定文本中的值
				fieldLabel : departure.i18n('tfr.departure.TemporaryAssignments.AddForm.form.manualDepartNotes.label'),//'备注',
				id : 'Foss.departure.TemporaryAssignments.MANUALDEPART_NOTES_ID',
				// 指定数据绑定与数据提交时的属性名
				name : 'manualDepartNotes',
				maxLength:100,
				maxLengthText: '输入的字符数不能超过100个',
				columnWidth : 1

			}, {
				xtype : 'container',
				columnWidth : .8,
				html : '&nbsp;'
			}, {
				xtype : 'button',
				text : departure.i18n('tfr.departure.TemporaryAssignments.AddForm.form.button'),//'任务确认',
				columnWidth : .1,
				handler : function() {
					var addParams = departure.temporaryPanel.getAddForm()
							.getForm().getValues();
					if (!departure.temporaryPanel.getAddForm().getForm()
							.isValid())
						return false;
					var artificialDepartVO = {
						'artificialDepartVO.temporaryEntity.id' : addParams.id,
						'artificialDepartVO.temporaryEntity.vehicleNo' : addParams.vehicleNo,
						'artificialDepartVO.temporaryEntity.driverName' : addParams.driverName,
						'artificialDepartVO.temporaryEntity.driverCode' : addParams.driverCode,
						'artificialDepartVO.temporaryEntity.containerNo' : addParams.containerNo,
						'artificialDepartVO.temporaryEntity.departItems' : addParams.departItems,
						'artificialDepartVO.temporaryEntity.manualDepartNotes' : addParams.manualDepartNotes
					};
					Ext.Ajax.request({
								url : departure.realPath('addTemporary.action'),
								params : artificialDepartVO,
								success : function(response) {
									var result = Ext
											.decode(response.responseText);
									Ext.ux.Toast.msg(departure.i18n('tfr.departure.DepartGrid.tips.label'), departure.i18n('tfr.departure.TemporaryAssignments.AddForm.form.tips'), 'ok', 3000);
												departure.temporaryPanel
														.getAddForm().getForm()
														.reset();
								},
								exception :function(response)
								{
								    var result = Ext.decode(response.responseText);
								     Ext.MessageBox.alert(departure.i18n('tfr.departure.DepartGrid.tips.label'),result.message);
								}
							});
				}
			}],	
		 constructor : function(config) {
		   		var me = this, cfg = Ext.apply({}, config);
				me.callParent([cfg]);
				comkey=me.getForm().findField('vehicleNo');
			    comkey.removeListener('keypress');
			    comkey.on('keypress',function(textfield, e){
					if(e.getKey() == Ext.EventObject.ENTER){
						var oldVehicleNo=this.up('form').getForm().findField('vehicleNo').getValue();
						if(oldVehicleNo!=null && oldVehicleNo!=''){
							if(oldVehicleNo.indexOf("-")!=-1){//包含两个'-'
								departure.convertVehicleNo(this,oldVehicleNo);
								if(comkey.getValue()){
								    comkey.getKeyPress(comkey, e);   
								}
							}	
						}
					}
			});
			comkey.addListener('select',function(field, records, eOpts) {
						var record = records[0],
						vehicleNo = record.get('vehicleNo');
						//查询该车牌号是不是外请车的，是的话需要给外请车司机赋值
						var artificialDepartVO = {
						'artificialDepartVO.vehicleNo' : vehicleNo
					};
						Ext.Ajax.request({//通过车牌号查找司机信息
								url : departure.realPath('queryDriverByVehicleNo.action'),
								params : artificialDepartVO,
								success : function(response) {
									var result = Ext
											.decode(response.responseText);
								    if(result.artificialDepartVO.relationInfoEntity)
								    {
									departure.temporaryPanel.getAddForm().getForm().findField('driverName').setValue(result.artificialDepartVO.relationInfoEntity.driverName);
									departure.temporaryPanel.getAddForm().getForm().findField('driverCode').setValue(result.artificialDepartVO.relationInfoEntity.driverCode);
								    }
								}
							});
					});
	     }
});

Ext.define('Foss.departure.TaskForm', {
	extend : 'Ext.form.Panel',
	layout : 'column',
	frame : true,
	title : departure.i18n('tfr.departure.TemporaryAssignments.AddForm.form.title'),//"录入框",
	border : false,
	defaults : {
		margin : '5 5 5 5',
		columns : 4
	},
	items : [{
				xtype : 'textfield',
				name : 'id',
				hidden : true,
				hideLabel : true
			}, {
				xtype : 'commontruckselector',
				fieldLabel : departure.i18n('tfr.departure.TemporaryAssignments.AddForm.form.vehicleNo.label'),//'车牌号',
				name : 'vehicleNo',
				id : 'Foss.departure.TaskForm.VEHICLENO_ID',
				columnWidth : .25,
				labelWidth : 60,
				allowBlank : false,
				listeners: {
					'select': function(field, records, eOpts) {
						var record = records[0],
						vehicleNo = record.get('vehicleNo');
						//查询该车牌号是不是外请车的，是的话需要给外请车司机赋值
						var artificialDepartVO = {
						'artificialDepartVO.vehicleNo' : vehicleNo
					};
						Ext.Ajax.request({//通过车牌号查找司机信息
								url : departure.realPath('queryDriverByVehicleNo.action'),
								params : artificialDepartVO,
								success : function(response) {
									var result = Ext
											.decode(response.responseText);
									departure.taskForm.getForm().findField('driverName').setValue(result.artificialDepartVO.relationInfoEntity.driverName);
									departure.taskForm.getForm().findField('driverCode').setValue(result.artificialDepartVO.relationInfoEntity.driverCode);
								}
							});
				}
				}
			}, {
				xtype : 'container',
				columnWidth : .05,
				html : '&nbsp;'
			}, {
				xtype : 'commondriverselector',
				fieldLabel : departure.i18n('tfr.departure.TemporaryAssignments.AddForm.form.driverName.label'),//'司机（公司车必填）',
				name : 'driverName',
				id : 'Foss.departure.TaskForm.DRIVER_CODE_ID',
				columnWidth : .4,
				labelWidth : 120,
				allowBlank : false,
				listeners: {
					'select': function(field, records, eOpts) {
						var record = records[0];
						departure.taskForm.getForm().findField('driverName').setValue(record.get('empName'));
						departure.taskForm.getForm().findField('driverCode').setValue(record.get('empCode'));
					}
				}
			}, {
				xtype : 'container',
				columnWidth : .05,
				html : '&nbsp;'
			}, {
				xtype : 'commonowntruckselector',
				fieldLabel : departure.i18n('tfr.departure.TemporaryAssignments.AddForm.form.containerNo.label'),//'货柜号',
				name : 'containerNo',
				displayField : 'containerCode',// 显示名称
				valueField : 'containerCode',
				myQueryParam : 'containerCode',
				showContent : '{containerCode}',
//				deptCode:'Y',
				id : 'Foss.departure.TaskForm.CONTAINER_NO_ID',
				columnWidth : .25,
				labelWidth : 60
			}, {
				xtype : 'radiogroup', // 单选框组
				fieldLabel : departure.i18n('tfr.departure.PrintManualForm.task.label'),//'放行类型', // 复选框组的字段标签
				columns : 3, // 3列来存放选择框项
				id : 'Foss.departure.TaskForm.DEPART_ITEMS_ID',
				vertical : true, // 按照columns中指定的列数来分配单选框组
				// 验证表单输入不能为空，属性值：true表示可以为空，false表示不可以为空
				// allowBlank : false,
				frame : true,
				columnWidth : .97,
				blankText : departure.i18n('tfr.departure.PrintManualForm.no.item.label'),//'没有选择项',// 当没有选择时的提示信息
				items : [{
							boxLabel :departure.i18n('tfr.departure.PrintManualForm.long.label'),// '长途',
							name : 'departItems', // 表单的参数名
							inputValue : 'LONG_DISTANCE', // 表单的参数值
							checked : true
						}, {
							boxLabel :departure.i18n('tfr.departure.PrintManualForm.short.label'),// '短途',
							name : 'departItems', // 表单的参数名
							inputValue : 'SHORT_DISTANCE' // 表单的参数值
							// 默认选中
					}	, {
							boxLabel : departure.i18n('tfr.departure.PrintManualForm.deliver.label'),//'接送货',
							name : 'departItems', // 表单的参数名
							inputValue : 'DELIVER' // 表单的参数值
						}]
			}, {
				xtype : 'textarea',
				// 当内容超过文本域的范围时，出现滚动条
				autoScroll : true,
				// 指定文本中的值
				fieldLabel :departure.i18n('tfr.departure.TemporaryAssignments.AddForm.form.manualDepartNotes.label'),// '备注',
				id : 'Foss.departure.TaskForm.MANUALDEPART_NOTES_ID',
				// 指定数据绑定与数据提交时的属性名
				name : 'manualDepartNotes',
				maxLength:100,
				maxLengthText: '输入的字符数不能超过100个',
				columnWidth : 1

			}, {
				xtype : 'container',
				columnWidth : .8,
				html : '&nbsp;'
			}, {
				xtype : 'button',
				text : departure.i18n('tfr.departure.TemporaryAssignments.AddForm.form.button'),//'任务确认',
				columnWidth : .1,
				handler : function() {
					var taskParams = departure.taskForm.getForm().getValues();
					if (!departure.taskForm.getForm().isValid())
						return false;
					var artificialDepartVO = {
						'artificialDepartVO.taskEntity.id' : taskParams.id,
						'artificialDepartVO.taskEntity.vehicleNo' : taskParams.vehicleNo,
						'artificialDepartVO.taskEntity.driverName' : taskParams.driverName,
						'artificialDepartVO.taskEntity.driverCode' : taskParams.driverCode,
						'artificialDepartVO.taskEntity.containerNo' : taskParams.containerNo,
						'artificialDepartVO.taskEntity.departItems' : taskParams.departItems,
						'artificialDepartVO.taskEntity.manualDepartNotes' : taskParams.manualDepartNotes
					};
					Ext.Ajax.request({
								url : departure.realPath('addTaskDepart.action'),
								params : artificialDepartVO,
								success : function(response) {
									var result = Ext
									.decode(response.responseText);
									Ext.ux.Toast.msg(departure.i18n('tfr.departure.DepartGrid.tips.label'), departure.i18n('tfr.departure.TemporaryAssignments.AddForm.form.tips'), 'ok', 3000);
										departure.temporaryPanel
												.getAddForm().getForm()
												.reset();
								},
								exception :function(response)
								{
								    var result = Ext.decode(response.responseText);
								    Ext.MessageBox.alert(departure.i18n('tfr.departure.DepartGrid.tips.label'),result.message);
								}
							});
				}
			}]
});

Ext.define('Foss.departure.TemporaryAssignmentsPanel', {
	extend : 'Ext.container.Container',
	frame : true,
	autoScroll : true,
	taskConfirmWindow : null,
	getTaskConfirmWindow : function() {
		if (this.taskConfirmWindow == null) {
			this.taskConfirmWindow = Ext
					.create('Foss.departure.TaskConfirmWindow');
		}
		return this.taskConfirmWindow;
	},
	// 定义查询条件
	queryForm : null,
	getQueryForm : function() {
		if (this.queryForm == null) {
			this.queryForm = Ext
					.create('Foss.departure.TemporaryAssignments.QueryLMSForm');
		}
		return this.queryForm;
	},
	// 录入框
	addForm : null,
	getAddForm : function() {
		if (this.addForm == null) {
			this.addForm = Ext
					.create('Foss.departure.TemporaryAssignments.AddForm');
		}
		return this.addForm;
	},
	temporaryGrid : null,
	getTemporaryGrid : function() {
		if (this.temporaryGrid == null) {
			this.temporaryGrid = Ext
					.create('Foss.departure.TemporaryAssignmentsGridPanel');
		}
		return this.temporaryGrid;
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.items = [me.getQueryForm(), me.getTemporaryGrid(), me.getAddForm()]
		me.callParent([cfg]);
	}
});
//将英文车牌号转换为中文车牌号 
departure.convertVehicleNo=function(me,vehicleNo){
	Ext.Ajax.request({
		url : departure.realPath('convertVehicleCode2Name.action'),
		params : {'artificialDepartVO.vehicleNo':vehicleNo},
		async: false,
		success : function(response) {
				var result=Ext.decode(response.responseText);
				me.up('form').getForm().findField('vehicleNo').setValue(result.artificialDepartVO.relationInfoEntity.vehicleNo);//返回转换后的车牌号
				me.up('form').getForm().findField('driverName').setValue(result.artificialDepartVO.relationInfoEntity.driverName);//返回转换后的车牌号
				me.up('form').getForm().findField('driverCode').setValue(result.artificialDepartVO.relationInfoEntity.driverCode);//返回转换后的车牌号
		},
		exception:function(response){
			me.up('form').getForm().findField('vehicleNo').setValue( vehicleNo); //异常，则返回未转换的车牌号
		}
	});
}
Ext.onReady(function() {
	Ext.tip.QuickTipManager.init();
	Ext.QuickTips.init();
	var temporaryPanel = Ext.create('Foss.departure.TemporaryAssignmentsPanel');
	var taskForm = Ext.create('Foss.departure.TaskForm');
	departure.temporaryPanel = temporaryPanel;
	departure.taskForm = taskForm;
	// Ext.getCmp("T_departure-artificialDepartindex_content");
	Ext.create('Ext.panel.Panel', {
				id : 'T_departure-artificialDepartindex_content',
				cls : "panelContentNToolbar",
				bodyCls : 'panelContentNToolbar-body',
				layout : 'auto',
				// 定义容器中的项
				items : [{
							xtype : 'tabpanel',
							frame : false,
							bodyCls : 'autoHeight',
							cls : 'innerTabPanel',
							activeTab : 0,
							items : [{
										title : departure.i18n('tfr.departure.QueryForm.form.not.task.label'),//'临时任务',
										tabConfig : {
											width : 120
										},
										itemId : 'TemporaryAssignments',
										items : temporaryPanel
									}, {
										title :departure.i18n('tfr.departure.QueryForm.form.task.label'),// '任务车辆',
										tabConfig : {
											width : 120
										},
										itemId : 'TaskAssignments',
										items : taskForm
									}]
						}],
				renderTo : 'T_departure-artificialDepartindex-body'
			});
});