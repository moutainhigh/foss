//是否全部放行
departure.beDepartureAll ='N';
//车辆业务类型
Ext.define('Foss.departure.BusinessTypeStore', {
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
							code : 'DELIVER',
							name : '派送'
						}, {
							code : 'PARTIALLINE',
							name : '偏线'
						}, {
							code : 'LONG_DISTANCE',
							name : '长途装车'
						}, {
							code : 'SHORT_DISTANCE',
							name : '短途装车'
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
		

		
//申请方式
Ext.define('Foss.departure.ApplyTypeStore', {
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
							code : 'MANUAL',
							name : '手动'
						}, {
							code : 'AUTO',
							name : '自动'
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
//申请状态
Ext.define('Foss.departure.StatusStore', {
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
						}, {
							code : '1',
							name : '已取消 '
						}, {
							code : '2',
							name : '已失效 '
						}, {
							code : '5',
							name : '待放行 '
						}, {
							code : '9',
							name : '已出发'
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

//车辆当前状态
Ext.define('Foss.departure.VehicleCurrentStatusStore', {
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
							code : 'RUN',
							name : '运行'
						}, {
							code : 'STOP',
							name : '停止'
						}, {
							code : 'ACCIDENTS',
							name : '事故车辆'
						}, {
							code : 'OFFLINE',
							name : '离线'
						},{
							code : 'TRAFFIC',
							name : '堵车'
						},{
							code : 'SLOWRUN',
							name : '缓行'
						},{
							code : 'DETAINING',
							name : '扣车'
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

//车辆放行事项
Ext.define('Foss.departure.DepartItemsShowStore', {
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
							code : 'NO_TASK_KEEP',
							name : '保养'
						}, {
							code : 'NO_TASK_YEAR_VERIFICATION',
							name : '年审/季审 '
						}, {
							code : 'NO_TASK_APPOINT',
							name : '临时放空出发/约车 '
						}, {
							code : 'NO_TASK_OIL',
							name : '加油 '
						}, {
							code : 'NO_TASK_REPAIR',
							name : '维修'
						}, {
							code : 'NO_TASK_OTHERS',
							name : '其他任务出发'
						}, {
							code : 'LONG_DISTANCE',
							name : '长途'
						}, {
							code : 'SHORT_DISTANCE',
							name : '短途'
						}, {
							code : 'DELIVER',
							name : '接送货'
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
		
//任务车辆类型
Ext.define('Foss.departure.DepartItemsStore', {
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
						}, {
							code : 'LONG_DISTANCE',
							name : '长途'
						}, {
							code : 'SHORT_DISTANCE',
							name : '短途'
						}, {
							code : 'DELIVER',
							name : '接送货'
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

//车辆放行类型
Ext.define('Foss.departure.DepartStore', {
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
						}, {
							code : '1',
							name : '纸质确认放行 '
						}, {
							code : '2',
							name : '保安PDA放行 '
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
//车辆申请记录的模版
Ext.define('Foss.departure.WaybillModel', {
			extend : 'Ext.data.Model',
//			idProperty : 'id',
			fields : [{
						name : 'id',
						type : 'string',
						hiddenField : true
					}, {
						name : 'vehicleNo',
						type : 'string'
					}, {
						name : 'departType',
						type : 'string'
					}, {
						name : 'status',
						type : 'string'
					}, {
						name : 'applyDepartTime',
						convert : dateConvert,
						type : 'date'
					}, {
						name : 'applyOrgName',
						type : 'string'
					}, {
						name : 'applyUserName',
						type : 'string'
					}, {
						name : 'manualDepartUserName',
						type : 'string'
					}, {
						name : 'driverName',
						type : 'string'
					}, {
						name : 'driverPhone',
						type : 'string'
					}, {
						name : 'manualDepartTime',
						type : 'date',
						convert : dateConvert
					}, {
						name : 'pdaDepartTime',
						type : 'date',
						convert : dateConvert
					}, {
						name : 'applyNotes',
						type : 'string'
					}, {
						name : 'containerNo',
						type : 'string'
					}, {
						name : 'departItems',
						type : 'string'
					}, {
						name : 'actualDepartType',
						type : 'string'
					}, {
						name : 'truckTaskId',
						type : 'string'
					}, {
						name : 'actualDepartTime',
						type : 'date',
						convert : dateConvert
					}

			]
		});
Ext.define('Foss.departure.WaybillStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.departure.WaybillModel',
//	pageSize : 10,
	autoLoad : true,
	proxy : {
		// 以JSON的方式加载数据
		type : 'ajax',
		actionMethods : 'POST',
		url : departure.realPath('queryDepartStock.action'),
		reader : {
			type : 'json',
			root : 'departureVO.departList',
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
//		setValue('Foss_departure_QueryForm_Status_ID', '5');
	},
	listeners : {
		beforeload : function(store, operation, eOpts) {
			var queryParams = departure.queryform.getValues();
			Ext.apply(operation, {
				// jsonData: {'departureVO':{'queryEntity':queryParams}}
				params : {
					'departureVO.queryEntity.vehicleNo' : queryParams.vehicleNo,
					'departureVO.queryEntity.status' : queryParams.status,
					'departureVO.queryEntity.applyOrgCode' : queryParams.applyOrgCode,
					'departureVO.queryEntity.departType' : queryParams.departType,
					'departureVO.queryEntity.beginDate' : queryParams.beginTime
							+ " 00:00:00",
					'departureVO.queryEntity.endDate' : queryParams.endTime
							+ " 23:59:59",
					'departureVO.queryEntity.departTypeIds' : queryParams.departTypeIds,
					'departureVO.queryEntity.driverCode' : queryParams.driverCode,
					'departureVO.queryEntity.departItems' : queryParams.departItems,
					'departureVO.queryEntity.driverName' : queryParams.driverName
				}
			});
		}
	}
});

Ext.define('Foss.departure.QueryForm', {
			extend : 'Ext.form.Panel',
			id : 'Foss_departure_QueryForm_ID',
			layout : 'column',
			frame : true,
			border : false,
			title : departure.i18n('tfr.departure.QueryForm.form.title'),//'查询车辆放行',
			defaults : {
				margin : '5 5 5 5',
				columns : 4
			},
			items : [{
				xtype : 'commontruckselector',
				fieldLabel : departure.i18n('tfr.departure.DepartGrid.vehicle.label'),//'车牌号',
				id : 'Foss_departure_QueryForm_DriverPhone_ID',
				name : 'vehicleNo',
				columnWidth : .25
					// labelWidth: 80
				}, {
				xtype : 'container',
				columnWidth : .05,
				html : '&nbsp;'
			}, {
				xtype : 'commondriverselector',
				id : 'Foss_departure_QueryForm_DriverName_ID',
				fieldLabel : departure.i18n('tfr.departure.DepartGrid.driver.name.label'),//'司机姓名',
				name : 'driverCode',
				columnWidth : .25
			}, {
				xtype : 'container',
				columnWidth : .05,
				html : '&nbsp;'
			}, {
				xtype : 'combo',
				fieldLabel : departure.i18n('tfr.departure.DepartGrid.apply.depart.status.label'),//'放行状态',
				value : '',
				id : 'Foss_departure_QueryForm_Status_ID',
				name : 'status',
				displayField : 'name',
				valueField : 'code',
				columnWidth : .25,
				queryMode : 'local',
				// 默认为”all”,当设置成”query”的时候，你选择某个值后，再此下拉时，只出现匹配选项，如果设为”all”的话，每次下拉均显示全部选项
				triggerAction : 'all',
				editable : false,
				store : Ext.create('Foss.departure.StatusStore')
					// labelWidth: 60
				}, {
				xtype : 'dynamicorgcombselector',
				fieldLabel : departure.i18n('tfr.departure.DepartGrid.apply.org.name.label'),//'申请部门',
				name : 'applyOrgCode',
				columnWidth : .25,
				listeners: {
					'select': function(field, records, eOpts) {
//						var items = this.up('window').items.items[0].items.items[0].items;
						var record = records[0];
						departure.queryform.getForm().findField('applyOrgName').setValue(record.get('name'));
						departure.queryform.getForm().findField('applyOrgCode').setValue(record.get('code'));
					}
				}
			}, {
				xtype : 'container',
				columnWidth : .05,
				html : '&nbsp;'
			}, {
				xtype : 'rangeDateField',
				fieldLabel :departure.i18n('tfr.departure.QueryForm.form.operate.time.label'),// '操作时间',
				columnWidth : .5,
				allowBlank:false,
				disallowBlank:true,
				// 类型为datetimefield_date97需要配置fieldId,可以赋予此属性任何唯一标 //识的String值
				id : 'Foss_departure_QueryForm_createTime_Id',
				fieldId:'Foss_departure_QueryForm_createTime_Id',
				 dateType : 'datetimefield_date97',
				fromName : 'beginTime',
				fromValue : Ext.Date.format(new Date(new Date().getFullYear(),
								new Date().getMonth(), new Date().getDate(),
								'00', '00', '00'), 'Y-m-d H:i:s'),
				toName : 'endTime',
				dateRange:7,
				toValue : Ext.Date.format(new Date(new Date().getFullYear(),
								new Date().getMonth(), new Date().getDate(),
								'23', '59', '59'), 'Y-m-d H:i:s')
			}, {
				xtype : 'combo',
				fieldLabel : '任务车辆类型',//departure.i18n('任务车辆类型'),//'放行状态',
				value : '',
//				id : 'Foss_departure_QueryForm_Status_ID',
				name : 'departItems',
				displayField : 'name',
				valueField : 'code',
				columnWidth : .25,
				queryMode : 'local',
				// 默认为”all”,当设置成”query”的时候，你选择某个值后，再此下拉时，只出现匹配选项，如果设为”all”的话，每次下拉均显示全部选项
				triggerAction : 'all',
				editable : false,
				store : Ext.create('Foss.departure.DepartItemsStore')
					// labelWidth: 60
				}, {
				xtype : 'checkboxgroup', // 复选框组
				columns : 2, // 3列来存放选择框项
				vertical : true, // 按照columns中指定的列数来分配复选框组件
				// 验证表单输入不能为空，属性值：true表示可以为空，false表示不可以为空
				allowBlank : false,
				columnWidth : .25,
				blankText : departure.i18n('tfr.departure.PrintManualForm.no.item.label'),//'没有选择项',// 当没有选择时的提示信息
				items : [{
							boxLabel : departure.i18n('tfr.departure.QueryForm.form.task.label'),//'任务车辆',
							name : 'departTypeIds', // 表单的参数名
							inputValue : 'TASK', // 表单的参数值
							checked : true, // 选中
							id : 'checkboxgroup_sports'
						}, {
							boxLabel :departure.i18n('tfr.departure.QueryForm.form.not.task.label'),// '临时任务车辆',
							name : 'departTypeIds', // 表单的参数名
							inputValue : 'NO_TASK', // 表单的参数值
							checked : true, // 选中
							id : 'checkboxgroup_music'
						}]
			}, {
				border : 1,
				xtype : 'container',
				columnWidth : .39,
				defaultType : 'button',
				layout : 'column',
				items : [{
					text : departure.i18n('tfr.departure.QueryForm.form.reset.button'),//'重置',
					columnWidth : .3,
					handler : function() {
						this.up('form').getForm().reset();
				this.up('form').getForm().findField('beginTime').setValue(Ext.Date.format(new Date(new Date().getFullYear(),
								new Date().getMonth(), new Date().getDate(),
								'00', '00', '00'), 'Y-m-d H:i:s'));
				this.up('form').getForm().findField('endTime').setValue(Ext.Date.format(new Date(new Date().getFullYear(),
								new Date().getMonth(), new Date().getDate(),
								'23', '59', '59'), 'Y-m-d H:i:s')); 
					}
				}, {
					xtype : 'container',
					columnWidth : .1,
					html : '&nbsp;'
				}, {
					text : departure.i18n('tfr.departure.QueryForm.form.query.button'),//'查询',
					columnWidth : .3,
					cls:'yellow_button',
					handler : function() {
						if (!departure.queryform.getForm().isValid())
						return false;
						departure.pagingBar.moveFirst();
					}
				}]
			}],
			constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				me.callParent([cfg]);
			}
		});

// 打印纸质放行条的form
Ext.define('Foss.departure.PrintManualForm', {
	extend : 'Ext.form.Panel',
	layout : 'column',
	frame : true,
	border : true,
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
				fieldLabel : departure.i18n('tfr.departure.DepartGrid.vehicle.label'),//'车牌号',
				name : 'vehicleNo',
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
									var manualWindow = Ext.getCmp('T_departure-index_content')
							         .getManualWindow();
									var result = Ext
											.decode(response.responseText);
									manualWindow.getManualForm().getForm().findField('driverName').setValue(result.artificialDepartVO.relationInfoEntity.driverName);
									manualWindow.getManualForm().getForm().findField('driverCode').setValue(result.artificialDepartVO.relationInfoEntity.driverCode);
								},
								exception :function(response)
								{
								    var result = Ext.decode(response.responseText);
								     Ext.MessageBox.alert(departure.i18n('tfr.departure.DepartGrid.tips.label'),result.message);
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
				fieldLabel : departure.i18n('tfr.departure.PrintManualForm.driver.name.label'),//'司机（公司车必填）',
				name : 'driverName',
				columnWidth : .4,
				labelWidth : 120,
				allowBlank : false,
				listeners: {
					'select': function(field, records, eOpts) {
						var manualWindow = Ext.getCmp('T_departure-index_content')
							         .getManualWindow();
						var record = records[0];
						manualWindow.getManualForm().getForm().findField('driverName').setValue(record.get('empName'));
						manualWindow.getManualForm().getForm().findField('driverCode').setValue(record.get('empCode'));
					}
				}
			}, {
				xtype : 'hiddenfield',
				name : 'driverCode'
			},{
				xtype : 'hiddenfield',
				name : 'truckTaskId'
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
				fieldLabel : departure.i18n('tfr.departure.PrintManualForm.containerNo.label'),//'货柜号',
				name : 'containerNo',
				columnWidth : .25,
				labelWidth : 60
			}, {
				xtype : 'radiogroup', // 单选框组
				fieldLabel : departure.i18n('tfr.departure.PrintManualForm.temp.depart.type.label'),//'临时放行类型', // 复选框组的字段标签
				columns : 3, // 3列来存放选择框项
				vertical : true, // 按照columns中指定的列数来分配单选框组
				// 验证表单输入不能为空，属性值：true表示可以为空，false表示不可以为空
				// allowBlank : false,
				frame : true,
				columnWidth : .99,
				name:'departItems1',
				blankText : departure.i18n('tfr.departure.PrintManualForm.no.item.label'),//'没有选择项',// 当没有选择时的提示信息
				items : [{
							boxLabel : departure.i18n('tfr.departure.PrintManualForm.keep.label'),//'保养',
							name : 'departItems', // 表单的参数名
							inputValue : 'NO_TASK_KEEP' // 表单的参数值
						}, {
							boxLabel : departure.i18n('tfr.departure.PrintManualForm.year.verification.label'),//'年审/季审',
							name : 'departItems', // 表单的参数名
							inputValue : 'NO_TASK_YEAR_VERIFICATION' // 表单的参数值
						}, {
							boxLabel : departure.i18n('tfr.departure.PrintManualForm.appoint.label'),//'临时放空出发/约车',
							name : 'departItems', // 表单的参数名
							inputValue : 'NO_TASK_APPOINT' // 表单的参数值
						}, {
							boxLabel : departure.i18n('tfr.departure.PrintManualForm.oil.label'),//'加油',
							name : 'departItems', // 表单的参数名
							inputValue : 'NO_TASK_OIL', // 表单的参数值
							checked : true
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
				xtype : 'radiogroup', // 单选框组
				fieldLabel : departure.i18n('tfr.departure.PrintManualForm.task.label'),//'任务车辆类型', // 复选框组的字段标签
				columns : 3, // 3列来存放选择框项
				vertical : true, // 按照columns中指定的列数来分配单选框组
				// 验证表单输入不能为空，属性值：true表示可以为空，false表示不可以为空
				// allowBlank : false,
				frame : true,
				name:'departItems2',
				columnWidth : .99,
				blankText : departure.i18n('tfr.departure.DepartGrid.apply.depart.status.label'),//'没有选择项',// 当没有选择时的提示信息
				items : [{
							boxLabel :departure.i18n('tfr.departure.PrintManualForm.long.label'),// '长途',
							name : 'departItems', // 表单的参数名
							inputValue : 'LONG_DISTANCE'// 表单的参数值
						}, {
							boxLabel :departure.i18n('tfr.departure.PrintManualForm.short.label'),// '短途',
							name : 'departItems', // 表单的参数名
							inputValue : 'SHORT_DISTANCE' // 表单的参数值
							// 默认选中
					}	, {
							boxLabel :departure.i18n('tfr.departure.PrintManualForm.deliver.label'),// '接送货',
							name : 'departItems', // 表单的参数名
							inputValue : 'DELIVER' // 表单的参数值
						}]
			}, {
				xtype : 'textarea',
				// 当内容超过文本域的范围时，出现滚动条
				autoScroll : true,
				// 指定文本中的值
				fieldLabel :departure.i18n('tfr.departure.DepartGrid.apply.notes.label'),// '备注',
				// 指定数据绑定与数据提交时的属性名
				name : 'manualDepartNotes',
				maxLength:100,
				maxLengthText: '输入的字符数不能超过100个',
				columnWidth : 1

			}, {
				xtype : 'container',
				columnWidth : .7,
				html : '&nbsp;'
			}, {
				xtype : 'button',
				text : departure.i18n('tfr.departure.PrintManualForm.save.button'),//'保存',
				columnWidth : .3,
				handler : function() {
					var manualWindow = Ext.getCmp('T_departure-index_content')
							.getManualWindow();
					var formParams = manualWindow.manualForm.getValues();
					if (!manualWindow.getManualForm().getForm().isValid())
						return false;
					var departureVO = {
						'departureVO.manualEntity.id' : formParams.id,
						'departureVO.manualEntity.truckTaskId' : formParams.truckTaskId,
						'departureVO.manualEntity.vehicleNo' : formParams.vehicleNo,
						'departureVO.manualEntity.driverName' : formParams.driverName,
						'departureVO.manualEntity.driverCode' : formParams.driverCode,
						'departureVO.manualEntity.containerNo' : formParams.containerNo,
						'departureVO.manualEntity.departItems' : formParams.departItems,
						'departureVO.manualEntity.manualDepartNotes' : formParams.manualDepartNotes,
						'departureVO.manualEntity.beDepartureAll':departure.beDepartureAll
					};
					Ext.Ajax.request({
								url : departure.realPath('saveOrAddManual.action'),
								params : departureVO,
								success : function(response) {
									var result = Ext
											.decode(response.responseText);
									Ext.getCmp('Foss_departure_WaybillGrid_ID').store
											.load();
									Ext.ux.Toast.msg(departure.i18n('tfr.departure.DepartGrid.tips.label'), departure.i18n('tfr.departure.PrintManualForm.save.manual.tips.label'), 'ok', 3000);
									manualWindow.getManualForm().getForm().reset();
									if(formParams.id)
									{
										manualWindow.hide();
									}
									
									departure.beDepartureAll='N';
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
	}
});

Ext.define('Foss.departure.DepartGrid', {
	extend : 'Ext.grid.Panel',
	// 指定grid对象在DOM树中的唯一值
	id : 'Foss_departure_WaybillGrid_ID',
	height : 390,
	autoScroll : true,
	emptyText:departure.i18n('tfr.departure.DepartGrid.result.is.empty'),
	// 增加表格列的分割线
	columnLines : true,
	// 表格对象增加一个边框
	frame : true,
//	viewConfig: {
//        stripeRows: false,
//		getRowClass: function(record, rowIndex, rp, ds) {
//			var status = record.get('status');
//			if(status=='2')
//			{
//    			return 'predeliver_notice_customer_row_purole';
//			}else
//			{
//			    return 'predeliver_notice_customer_row_white';
//			}
//		}
//	},
	// 定义表格列信息
	columns : [{
		// 字段标题
		header : departure.i18n('tfr.departure.DepartGrid.depart.type.label'),
		// 关联model中的字段名
		width : 100,
		dataIndex : 'departType',
		renderer : function(value) {
			var store = Ext.create('Foss.departure.DepartTypeStore'), record = store
					.findRecord('code', value);
			if (record != null) {
				return record.get('name');
			} else {
				return '';
			}
		}
	}, {
		// 字段标题
		header : departure.i18n('tfr.departure.DepartGrid.vehicle.label'),
		width : 80,
		// 关联model中的字段名
		dataIndex : 'vehicleNo',
		windowClassName : 'Foss.departure.VehicleInfoWindow',
		// 定义列类型为扩展的openwindowcolumn类型
		xtype : 'opentipwindowcolumn',
		tipConfig: {
		        //如果要设置宽度，一定要修改maxWidth值，因为tip的maxWidth最大只有300
				width: 150,
				height: 30,
		        html : departure.i18n('tfr.departure.DepartGrid.vehicle.detail.tip'),
				hideDelay: 2000
			}
	}, {
		// 字段标题
		header : departure.i18n('tfr.departure.DepartGrid.apply.depart.time.label'),
		width : 140,
		// 关联model中的字段名
		xtype : 'datecolumn',
		format : 'Y-m-d H:i:s',
		dataIndex : 'applyDepartTime'
	}, {
		// 字段标题
		header :departure.i18n('tfr.departure.DepartGrid.apply.depart.status.label'),// '放行状态',
		width : 80,
		// 关联model中的字段名
		dataIndex : 'status',
		renderer : function(value) {
			var store = Ext.create('Foss.departure.StatusStore'), record = store
					.findRecord('code', value);
			if (record != null) {
				return record.get('name');
			} else {
				return '';
			}
		}
	}, {
		// 字段标题
		header : departure.i18n('tfr.departure.DepartGrid.apply.org.name.label'),//'申请部门',
		width : 100,
		// 关联model中的字段名
		dataIndex : 'applyOrgName'
	}, {
		// 字段标题
		header : departure.i18n('tfr.departure.DepartGrid.apply.user.name.label'),//'申请人',
		width : 100,
		// 关联model中的字段名
		dataIndex : 'applyUserName'
	}, {
		// 字段标题
		header : departure.i18n('tfr.departure.DepartGrid.driver.name.label'),//'司机姓名',
		width : 100,
		// 关联model中的字段名
		dataIndex : 'driverName'
	}, {
		// 字段标题
		header : departure.i18n('tfr.departure.DepartGrid.driver.phone.label'),//'联系方式',
		width : 100,
		// 关联model中的字段名
		dataIndex : 'driverPhone'
	}, {
		// 字段标题
		header : departure.i18n('tfr.departure.DepartGrid.actual.depart.type.label'),//'放行方式',
		width : 100,
		// 关联model中的字段名
		dataIndex : 'actualDepartType'
	}, {
		// 字段标题
		header : departure.i18n('tfr.departure.DepartGrid.actual.depart.time.label'),//'放行时间',
		xtype : 'datecolumn',
		width : 140,
		format : 'Y-m-d H:i:s',
		// 关联model中的字段名
		dataIndex : 'actualDepartTime'
	}, {
		// 字段标题
		header : '纸质放行人',//'放行时间',
		width : 140,
		// 关联model中的字段名
		dataIndex : 'manualDepartUserName'
	}, {
		// 字段标题
		header : departure.i18n('tfr.departure.DepartGrid.apply.notes.label'),//'备注',
		width : 200,
		// 关联model中的字段名
		dataIndex : 'applyNotes'
	}, {
		width : 0,
		dataIndex : 'truckTaskId'
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.departure.WaybillStore');
		me.selModel = Ext.create('Ext.selection.CheckboxModel');

		me.tbar = [{
					xtype : 'button',
					text : departure.i18n('tfr.departure.DepartGrid.apply.depart.button'),//'申请车辆放行',
					gridContainer : this,
					disabled:!departure.isPermission('departure/artificialDepartbutton.action'),
					hidden:!departure.isPermission('departure/artificialDepartbutton.action'),
					handler : function() {
						addTab('T_departure-artificialDepartindex',// 对应打开的目标页面js的onReady里定义的renderTo
								departure.i18n('tfr.departure.DepartGrid.apply.depart.manual.button'),//'申请车辆放行（人工）',// 打开的Tab页的标题
								ContextPath.TFR_EXECUTION+'/departure/artificialDepartindex.action');// 对应的页面URL，可以在url后使用?x=123这种形式传参
					}
				}, {
					xtype : 'button',
					text : departure.i18n('tfr.departure.DepartGrid.manual.depart.button'),//'手工放行',
					gridContainer : this,
					disabled:!departure.isPermission('departure/manualdepartbutton.action'),
					hidden:!departure.isPermission('departure/manualdepartbutton.action'),
					handler : function() {
						var manualWindow = Ext
								.getCmp('T_departure-index_content')
								.getManualWindow();
						var rowObjs = me.getSelectionModel().getSelection();
						if (rowObjs.length > 1) {
							Ext.MessageBox.alert(departure.i18n('tfr.departure.DepartGrid.tips.label'),departure.i18n('tfr.departure.DepartGrid.please.input.one.tips.label'));
							return false;
						} else if (rowObjs.length == 1) {// 已选中一条进行编辑
							// 校验状态
							var params = {
								'departureVO.manualEntity.id' : rowObjs[0]
										.get('id')
							};
							Ext.Ajax.request({
								url : departure.realPath('validStatus.action'),
								params : params,
								success : function(response) {
									var result = Ext
											.decode(response.responseText);
									if (result.departureVO.operatStatus == '1') {
										Ext.MessageBox.alert('提示','只能放行待出发的车辆！');
										
									} else {
										if(result.departureVO.deliverbills != null && result.departureVO.deliverbills != '') {
											Ext.Msg.confirm('提示'/*'提示'*/, 
													'今日车牌号'+rowObjs[0].get('vehicleNo')+'还有派送单号'+result.departureVO.deliverbills+'未做系统放行，请确认是否全部放行', 
													function(optional){
				    								if(optional == 'yes'){
				    									departure.beDepartureAll='Y';
				    									manualWindow.getManualForm().getForm().findField('departItems1')
																.setValue({'departItems' : rowObjs[0].get('departItems')});
													    manualWindow.getManualForm().getForm().findField('truckTaskId')
																.setValue(rowObjs[0].get('truckTaskId'));
														manualWindow.getManualForm().getForm().findField('departItems2')
																.setValue({'departItems' : rowObjs[0].get('departItems')});
						//								Ext.getCmp('Foss.departure.PrintManualForm.DEPART_ITEMS_ID')
						//										.setValue({'departItems' : rowObjs[0].get('departItems')});
						//								Ext.getCmp('Foss.departure.PrintManualForm.TRUCKTASKID_ID')
						//										.setValue(rowObjs[0].get('truckTaskId'));
						//								Ext.getCmp('Foss.departure.PrintManualForm.DEPART_ITEMS_ID2')
						//										.setValue({'departItems' : rowObjs[0].get('departItems')});
														manualWindow.getManualForm().getForm().loadRecord(rowObjs[0]);
														manualWindow.getManualForm().getForm().findField('vehicleNo')
																.setReadOnly(true);
														manualWindow.getManualForm().getForm().findField('driverName')
																.setReadOnly(true);
														manualWindow.getManualForm().getForm().findField('containerNo')
																.setReadOnly(true);
														manualWindow.getManualForm().getForm().findField('departItems1')
																.setReadOnly(true);
														manualWindow.getManualForm().getForm().findField('departItems2')
																.setReadOnly(true);
						//								Ext
						//										.getCmp('Foss.departure.PrintManualForm.DEPART_ITEMS_ID')
						//										.setReadOnly(true);
						//								Ext
						//										.getCmp('Foss.departure.PrintManualForm.DEPART_ITEMS_ID2')
						//										.setReadOnly(true);
														manualWindow.show();
				    								}else if(optional == 'no'){
				    									departure.beDepartureAll='N';
				    									manualWindow.getManualForm().getForm().findField('departItems1')
																.setValue({'departItems' : rowObjs[0].get('departItems')});
													    manualWindow.getManualForm().getForm().findField('truckTaskId')
																.setValue(rowObjs[0].get('truckTaskId'));
														manualWindow.getManualForm().getForm().findField('departItems2')
																.setValue({'departItems' : rowObjs[0].get('departItems')});
						//								Ext.getCmp('Foss.departure.PrintManualForm.DEPART_ITEMS_ID')
						//										.setValue({'departItems' : rowObjs[0].get('departItems')});
						//								Ext.getCmp('Foss.departure.PrintManualForm.TRUCKTASKID_ID')
						//										.setValue(rowObjs[0].get('truckTaskId'));
						//								Ext.getCmp('Foss.departure.PrintManualForm.DEPART_ITEMS_ID2')
						//										.setValue({'departItems' : rowObjs[0].get('departItems')});
														manualWindow.getManualForm().getForm().loadRecord(rowObjs[0]);
														manualWindow.getManualForm().getForm().findField('vehicleNo')
																.setReadOnly(true);
														manualWindow.getManualForm().getForm().findField('driverName')
																.setReadOnly(true);
														manualWindow.getManualForm().getForm().findField('containerNo')
																.setReadOnly(true);
														manualWindow.getManualForm().getForm().findField('departItems1')
																.setReadOnly(true);
														manualWindow.getManualForm().getForm().findField('departItems2')
																.setReadOnly(true);
						//								Ext
						//										.getCmp('Foss.departure.PrintManualForm.DEPART_ITEMS_ID')
						//										.setReadOnly(true);
						//								Ext
						//										.getCmp('Foss.departure.PrintManualForm.DEPART_ITEMS_ID2')
						//										.setReadOnly(true);
														manualWindow.show();
				    								}
												});
										}else {
											manualWindow.getManualForm().getForm().findField('departItems1')
													.setValue({'departItems' : rowObjs[0].get('departItems')});
										    manualWindow.getManualForm().getForm().findField('truckTaskId')
													.setValue(rowObjs[0].get('truckTaskId'));
											manualWindow.getManualForm().getForm().findField('departItems2')
													.setValue({'departItems' : rowObjs[0].get('departItems')});
			//								Ext.getCmp('Foss.departure.PrintManualForm.DEPART_ITEMS_ID')
			//										.setValue({'departItems' : rowObjs[0].get('departItems')});
			//								Ext.getCmp('Foss.departure.PrintManualForm.TRUCKTASKID_ID')
			//										.setValue(rowObjs[0].get('truckTaskId'));
			//								Ext.getCmp('Foss.departure.PrintManualForm.DEPART_ITEMS_ID2')
			//										.setValue({'departItems' : rowObjs[0].get('departItems')});
											manualWindow.getManualForm().getForm().loadRecord(rowObjs[0]);
											manualWindow.getManualForm().getForm().findField('vehicleNo')
													.setReadOnly(true);
											manualWindow.getManualForm().getForm().findField('driverName')
													.setReadOnly(true);
											manualWindow.getManualForm().getForm().findField('containerNo')
													.setReadOnly(true);
											manualWindow.getManualForm().getForm().findField('departItems1')
													.setReadOnly(true);
											manualWindow.getManualForm().getForm().findField('departItems2')
													.setReadOnly(true);
			//								Ext
			//										.getCmp('Foss.departure.PrintManualForm.DEPART_ITEMS_ID')
			//										.setReadOnly(true);
			//								Ext
			//										.getCmp('Foss.departure.PrintManualForm.DEPART_ITEMS_ID2')
			//										.setReadOnly(true);
											manualWindow.show();
										}
										
									}
								}
							});

						} else {
							manualWindow.getManualForm().getForm().findField('vehicleNo')
									.setValue('');
							manualWindow.getManualForm().getForm().findField('driverName')
									.setValue('');
							manualWindow.getManualForm().getForm().findField('containerNo')
									.setValue('');
							manualWindow.getManualForm().getForm().findField('manualDepartNotes')
									.setValue('');
							manualWindow.getManualForm().getForm().findField('departItems2')
									.setValue({
												'departItems' : 'NO_TASK_OIL'
											});
							manualWindow.getManualForm().getForm().findField('vehicleNo')
									.setReadOnly(false);
							manualWindow.getManualForm().getForm().findField('driverName')
									.setReadOnly(false);
							manualWindow.getManualForm().getForm().findField('containerNo')
									.setReadOnly(false);
							manualWindow.getManualForm().getForm().findField('departItems1')
									.setReadOnly(false);
							manualWindow.getManualForm().getForm().findField('departItems2')
									.setReadOnly(false);
							manualWindow.show();
						}
					}
				}, {
					xtype : 'button',
					text : departure.i18n('tfr.departure.DepartGrid.cancle.apply.depart.button'),//'取消申请',
					disabled:!departure.isPermission('departure/cancledepartbutton.action'),
					hidden:!departure.isPermission('departure/cancledepartbutton.action'),
					gridContainer : this,
					handler : function() {
						var rowObjs = me.getSelectionModel().getSelection();
						var ids = '';
						if (rowObjs.length < 1) {
							Ext.MessageBox.alert(departure.i18n('tfr.departure.DepartGrid.tips.label'),departure.i18n('tfr.departure.DepartGrid.at.least.choose.one.tips.label'));
							return false;
						}
						Ext.Msg.confirm(departure.i18n('tfr.departure.DepartGrid.info.tips.label'),departure.i18n('tfr.departure.DepartGrid.confirm.cancle.apply.tips.label'), function(optional){
							if(optional == 'yes'){
								for (var i = 0; i < rowObjs.length; i++) {
									var _status = rowObjs[i].get("status");
									if (!(_status == '5'||_status == '9')) {
										Ext.MessageBox.alert(departure.i18n('tfr.departure.DepartGrid.tips.label'),departure.i18n('tfr.departure.DepartGrid.only.cancle.waited.truck.tips.label'));
										return false;
									}
									ids += rowObjs[i].get("id") + ",";
								}
								ids = ids.substring(0, ids.length - 1);
								var array = new Array();
								for (var i = 0; i < rowObjs.length; i++) {
									array.push(rowObjs[i].data);
								}
								var params = {
									departureVO : {
										activeList : array
									}
								};
								Ext.Ajax.request({
											url : departure.realPath('cancleDepart.action'),
											jsonData : params,
											success : function(response) {
												var result = Ext
														.decode(response.responseText);
												if (result.departureVO.operatStatus == '3') {
													Ext.MessageBox.alert(departure.i18n('tfr.departure.DepartGrid.tips.label'),departure.i18n('tfr.departure.DepartGrid.status.expenseed.tips.label'));
												} else {
													Ext.ux.Toast.msg(departure.i18n('tfr.departure.DepartGrid.tips.label'), departure.i18n('tfr.departure.DepartGrid.cancle.apply.ok.tips.label'), 'ok', 3000);
													departure.pagingBar.moveFirst();
												}
											},
											exception :function(response)
											{
											    var result = Ext.decode(response.responseText);
												     Ext.MessageBox.alert(departure.i18n('tfr.departure.DepartGrid.tips.label'),result.message);
											}
										});
							}
						})
					}
				}, {
					xtype : 'button',
					text : departure.i18n('tfr.departure.DepartGrid.active.apply.depart.button'),//'重新激活',
					disabled:!departure.isPermission('departure/activedepartbutton.action'),
					hidden:!departure.isPermission('departure/activedepartbutton.action'),
					gridContainer : this,
					handler : function() {
						var rowObjs = me.getSelectionModel().getSelection();
						var ids = '';
						if (rowObjs.length < 1) {
							Ext.MessageBox.alert(departure.i18n('tfr.departure.DepartGrid.tips.label'),departure.i18n('tfr.departure.DepartGrid.at.least.choose.one.cancled.tips.label'));
							return false;
						}
						for (var i = 0; i < rowObjs.length; i++) {
							var _status = rowObjs[i].get("status");
							if (_status != '2') {
								Ext.MessageBox.alert(departure.i18n('tfr.departure.DepartGrid.tips.label'),departure.i18n('tfr.departure.DepartGrid.only.active.cancled.tips.label'));
								return false;
							}
							ids += rowObjs[i].get("id") + ",";
						}
						ids = ids.substring(0, ids.length - 1);
						var params = {
							'departureVO.queryEntity.ids' : ids
						};
						Ext.Ajax.request({
									url : departure.realPath('activeDepart.action'),
									params : params,
									success : function(response) {
										var result = Ext
												.decode(response.responseText);
										if (result.departureVO.operatStatus == '4') {
											Ext.MessageBox.alert(departure.i18n('tfr.departure.DepartGrid.tips.label'),departure.i18n('tfr.departure.DepartGrid.status.expenseed.tips.label'));
										} else {
											Ext.ux.Toast.msg(departure.i18n('tfr.departure.DepartGrid.tips.label'), departure.i18n('tfr.departure.DepartGrid.active.apply.ok.tips.label'), 'ok', 3000);
											departure.pagingBar.moveFirst();
										}
									}
								});
					}
				}, {
					xtype : 'button',
					text : departure.i18n('tfr.departure.DepartGrid.print.vehicle.no.button'),//'打印车牌',
					gridContainer : this,
					handler : function() {
						var vurl = "http://localhost:8077/print";
						var rowObjs = me.getSelectionModel().getSelection();
						var ids = '';
						if (rowObjs.length > 1) {
							Ext.MessageBox.alert(departure.i18n('tfr.departure.DepartGrid.tips.label'),departure.i18n('tfr.departure.DepartGrid.only.choose.one.tips.label'));
							return false;
						}
						if(rowObjs.length==0)
						{
							var manualWindow = Ext
								.getCmp('T_departure-index_content').getVehicleWindow();
								manualWindow.show();
								return false;
						}
						var params = {
								'departureVO.vehiclePrintDTO.vehicleNo' : rowObjs[0]
										.get('vehicleNo')
							};
						Ext.Ajax.request({
									url : departure.realPath('queryVehicleInfoForPrint.action'),
									params : params,
									success : function(response) {
										var result = Ext
												.decode(response.responseText);
										var data  ={
												  	 lblprtworker:"VehicleLabelPrintWorker",
													 optusernum:result.departureVO.vehiclePrintDTO.userCode,
													 printdate:result.departureVO.vehiclePrintDTO.printTime,
													 serialnos:result.departureVO.vehiclePrintDTO.vehicleCode,
													 carnos:result.departureVO.vehiclePrintDTO.vehicleNo,
													 teams:result.departureVO.vehiclePrintDTO.vehicleMotorcadeName,
												     groups:result.departureVO.vehiclePrintDTO.vehicleOrganizationName
												    }
												    Ext.data.JsonP.request({
											        url: vurl,
											        callbackKey: 'callback',
												    params: data,
													callback: function() {
											 			//回调函数，不管请求成功与否都执行
											 			//alert("callback");
													},   	    
												    success: function(result, request) {
														var ret_code = result.data.code; // 1=,0=
														alert(result.data.msg);
												    },
												    exception : function (result, request) {
												    	var ret_code = result.data.code;
														alert(result.data.msg);
												    }
												});
									}
								});
					}
				},{
				xtype : 'button',
				text : '导出',
				name : 'export',
				disabled:!departure.isPermission('departure/departexportbutton.action'),
				hidden:!departure.isPermission('departure/departexportbutton.action'),
				handler : function(){
					var records = me.getStore().getRange(0,
							me.getStore().data.items.length);
					if(records.length<1)
					{
					  Ext.MessageBox.alert('提示','没有查询到记录，不能导出');
					  return false;
					}
					var queryParams = departure.queryform.getValues();
//					var queryParams = unload.unloadtaskdetailquery.loadTaskForm.getForm().getValues();
					if(!Ext.fly('downloadAttachFileForm')){
						    var frm = document.createElement('form');
						    frm.id = 'downloadAttachFileForm';
						    frm.style.display = 'none';
						    document.body.appendChild(frm);
					}
					Ext.Ajax.request({
						url:departure.realPath('exportUnloadWayBillByTaskNo.action'),
						form: Ext.fly('downloadAttachFileForm'),
						method : 'POST',
						params : {
							'departureVO.queryEntity.vehicleNo' : queryParams.vehicleNo,
							'departureVO.queryEntity.status' : queryParams.status,
							'departureVO.queryEntity.applyOrgCode' : queryParams.applyOrgCode,
							'departureVO.queryEntity.departType' : queryParams.departType,
							'departureVO.queryEntity.beginDate' : queryParams.beginTime
									+ " 00:00:00",
							'departureVO.queryEntity.endDate' : queryParams.endTime
									+ " 23:59:59",
							'departureVO.queryEntity.departTypeIds' : queryParams.departTypeIds,
							'departureVO.queryEntity.driverCode' : queryParams.driverCode,
							'departureVO.queryEntity.departItems' : queryParams.departItems,
							'departureVO.queryEntity.driverName' : queryParams.driverName
						},
		    			isUpload: true,
		    			exception : function(response) {
		    				var result = Ext.decode(response.responseText);
		    				top.Ext.MessageBox.alert('提示',result.message);
		    			}
					});
				}
			}];

		me.bbar = Ext.create('Deppon.StandardPaging', {
					store : me.store,
					plugins: 'pagesizeplugin'
			//	设置分页记录最大值，防止输入过大的数值
				});
		departure.pagingBar = me.bbar;
		me.callParent([cfg]);
	}
});

// 当没有选中记录时，弹出框让用户自己选择车牌号
Ext.define('Foss.departure.VehicleForm', {
	extend : 'Ext.form.Panel',
	layout : 'column',
	frame : true,
	border : true,
	defaults : {
		margin : '5 5 5 5',
		columns : 4
	},
	items : [{
				xtype : 'commontruckselector',
				fieldLabel :'车牌号',// '车牌号',
				name : 'vehicleNo',
				columnWidth : 1,
				allowBlank : false
			}, {
				xtype : 'container',
				columnWidth : .6,
				html : '&nbsp;'
			}, {
				xtype : 'button',
				text :'打印',// '打印',
				columnWidth : .4,
				handler : function() {
					if (!Ext.getCmp('T_departure-index_content').getVehicleWindow().getVehicleForm().getForm().isValid())
						return false;
					var params = {
								'departureVO.vehiclePrintDTO.vehicleNo' : Ext
								.getCmp('T_departure-index_content').getVehicleWindow().getVehicleForm().getValues().vehicleNo
							};
							
						Ext.Ajax.request({
									url : departure.realPath('queryVehicleInfoForPrint.action'),
									params : params,
									success : function(response) {
										var result = Ext
												.decode(response.responseText);
										var vurl = "http://localhost:8077/print";
										var data  ={
												  	 lblprtworker:"VehicleLabelPrintWorker",
													 optusernum:result.departureVO.vehiclePrintDTO.userCode,
													 printdate:result.departureVO.vehiclePrintDTO.printTime,
													 serialnos:result.departureVO.vehiclePrintDTO.vehicleCode,
													 carnos:result.departureVO.vehiclePrintDTO.vehicleNo,
													 teams:result.departureVO.vehiclePrintDTO.vehicleMotorcadeName,
												     groups:result.departureVO.vehiclePrintDTO.vehicleOrganizationName
												    }
												    Ext.data.JsonP.request({
											        url: vurl,
											        callbackKey: 'callback',
												    params: data,
													callback: function() {
											 			//回调函数，不管请求成功与否都执行
											 			//alert("callback");
													},   	    
												    success: function(result, request) {
														var ret_code = result.data.code; // 1=,0=
														alert(result.data.msg);
												    },
												    failure : function (result, request) {
												    	var ret_code = result.data.code;
														alert(result.data.msg);
												    }
												});
									}
								});
				}
			}],

	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});
Ext.define('Foss.departure.VehicleWindow', {
			extend : 'Ext.window.Window',
//			id : 'Foss.departure.VehicleWindow_ID',
			// title: lineinfo.i18n('foss.lineinfo.windowTitle'),
			title : '请选择一个车牌号',//'请填写司机信息',
			bodyCls : 'autoHeight',
			cls : 'autoHeight',
			modal : true,
			closeAction : 'hide',
			width : 400,
			vehicleForm : null,
			getVehicleForm : function() {
				if (this.vehicleForm == null) {
					this.vehicleForm = Ext
							.create('Foss.departure.VehicleForm')
				}
				return this.vehicleForm;
			},
			constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				me.items = [me.getVehicleForm()];
				me.callParent([cfg]);
			}
		});
Ext.define('Foss.departure.PrintManualWindow', {
			extend : 'Ext.window.Window',
			// title: lineinfo.i18n('foss.lineinfo.windowTitle'),
			title : departure.i18n('tfr.departure.DepartGrid.manual.depart.button'),//'手工放行',
			bodyCls : 'autoHeight',
			cls : 'autoHeight',
			modal : true,
			closeAction : 'hide',
			width : 800,
			manualForm : null,
			getManualForm : function() {
				if (this.manualForm == null) {
					this.manualForm = Ext
							.create('Foss.departure.PrintManualForm')
				}
				return this.manualForm;
			},
			close : function(){
				if(this.getManualForm().getValues().vehicleNo)
				{
					Ext.Msg.confirm(departure.i18n('tfr.departure.DepartGrid.info.tips.label'), departure.i18n('tfr.departure.DepartGrid.confirm.shut.tips.label'), function(optional){
					if(optional == 'yes'){
						Ext.getCmp('T_departure-index_content')
							.getManualWindow().hide();
							manualWindow.getManualForm().getForm().findField('vehicleNo').allowBlank = true;
							manualWindow.getManualForm().getForm().findField('vehicleNo').reset();
							manualWindow.getManualForm().getForm().findField('driverName').allowBlank = true;
							manualWindow.getManualForm().getForm().findField('driverName').reset();
					}
				})
				}
				else
				{
					this.getManualForm().getForm().findField('vehicleNo').allowBlank = true;
					this.getManualForm().getForm().findField('vehicleNo').reset();
					this.getManualForm().getForm().findField('driverName').allowBlank = true;
					this.getManualForm().getForm().findField('driverName').reset();
					this.hide();
				}
			},
			beforeclose : function(){
				this.getManualForm.getForm().reset();
			},
			constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				me.items = [me.getManualForm()];
				departure.manualForm = me.getManualForm();
				me.callParent([cfg]);
			}
		});
		
Ext.onReady(function() {
			Ext.QuickTips.init();
			var queryform = Ext.create('Foss.departure.QueryForm');
			var querygrid = Ext.create('Foss.departure.DepartGrid');
			departure.queryform = queryform;
			Ext.create('Ext.panel.Panel', {
						id : 'T_departure-index_content',
						cls : "panelContentNToolbar",
						bodyCls : 'panelContent-body',
						manualWindow : null,
						getManualWindow : function() {
							if (this.manualWindow == null) {
								this.manualWindow = Ext
										.create('Foss.departure.PrintManualWindow');
							}
							return this.manualWindow;
						},
						vehicleWindow : null,
						getVehicleWindow : function() {
							if (this.vehicleWindow == null) {
								this.vehicleWindow = Ext
										.create('Foss.departure.VehicleWindow');
							}
							return this.vehicleWindow;
						},
						items : [queryform, querygrid],
						renderTo : 'T_departure-index-body'
					});
		});