//申请状态
Ext.define('Foss.scheduling.leadTruck.ContainerStructureStore', {
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
							code : 'FLAT',
							name : '平板'
						}, {
							code : 'HIGH_HURDLES',
							name : '高栏 '
						}, {
							code : 'CABINET',
							name : '柜式'
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
Ext.define('Foss.scheduling.leadTruck.LeadTruckModel', {
			extend : 'Ext.data.Model',
			idProperty : 'id',
			fields : [{
						name : 'id',
						type : 'string'
					},{
						name : 'inquiryTime',
						convert : dateConvert,
						type : 'date'
					},{
						name : 'inquiryTimeDate',
						convert : dateConvert,
						type : 'date'
					},{
						name : 'origOrgName',
						type : 'string'
					},{
						name : 'destOrgName',
						type : 'string'
					},{
						name : 'origOrgCode',
						type : 'string'
					},{
						name : 'destOrgCode',
						type : 'string'
					},{
						name : 'vehicleLength',
						type : 'string'
					},{
						name : 'containerStructure',
						type : 'string'
					},{
						name : 'fee',
						type : 'string'
					},{
						name : 'deadLoad',
						type : 'string'
					},{
						name : 'selfVolumn',
						type : 'string'
					},{
						name : 'notes',
						type : 'string'
					},{
						name : 'tele',
						type : 'string'
					},{
						name : 'sourceOfInformation',
						type : 'string'
					},{
						name : 'createUserName',
						type : 'string'
					},{
						name : 'createUserCode',
						type : 'string'
					},{
						name : 'createTime',
						convert : dateConvert,
						type : 'date'
					},{
						name : 'updateTime',
						convert : dateConvert,
						type : 'date'
					},{
						name : 'isContractTruck',
						type : 'string'
					},{
						name : 'vehicleLengthName',
						type : 'string'
					}

			]
		});
Ext.define('Foss.scheduling.leadTruck.LeadTruckStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.scheduling.leadTruck.LeadTruckModel',
//	pageSize : 10,
	autoLoad : false,
	proxy : {
		// 以JSON的方式加载数据
		type : 'ajax',
		actionMethods : 'POST',
		url : scheduling.realPath('queryLeadTruckGrid.action'),
		reader : {
			type : 'json',
			root : 'leadTruckVO.leadTruckList',
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
			var queryParams = scheduling.leadTruck.queryform.getValues();
			Ext.apply(operation, {
				params : {
					'leadTruckVO.queryEntity.origOrgCode' : queryParams.origOrgCode,
					'leadTruckVO.queryEntity.destOrgCode' : queryParams.destOrgCode,
					'leadTruckVO.queryEntity.startTime' : queryParams.startTime,
					'leadTruckVO.queryEntity.endTime' : queryParams.endTime
				}
			});
		},
		load: function( store, records, successful, eOpts ){
				var data = store.getProxy().getReader().rawData;
				if(!data.success &&(!data.isException)){
				Ext.MessageBox.alert(scheduling.leadTruck.i18n('Foss.scheduling.leadTruck.QueryForm.tips'),data.message);
				}
			}
	}
});

Ext.define('Foss.scheduling.leadTruck.QueryForm', {
			extend : 'Ext.form.Panel',
			id : 'Foss_scheduling_QueryForm_ID',
			layout : 'column',
//			height:150,
			frame : true,
			border : false,
			title : scheduling.leadTruck.i18n('Foss.scheduling.leadTruck.QueryForm.title'),//'查询外请车询价',
			defaults : {
				margin : '5 5 5 5',
				columns : 4
			},
			items : [ {
				xtype : 'dynamicorgcombselector',
				fieldLabel : scheduling.leadTruck.i18n('Foss.scheduling.leadTruck.QueryForm.origOrgName.label'),//'出发外场',
				name : 'origOrgCode',
				type : 'ORG',
//				transferCenter : 'Y',// 查询外场 配置此值
				columnWidth : .25,
				listeners :{
					render : function(panel,text){
					var cmbOrgCode = this.up('form').getForm().findField('origOrgCode');
					if(FossUserContext.getCurrentDept().transferCenter=='Y')
					{
					cmbOrgCode.getStore().load({params:{'commonOrgVo.name' : FossUserContext.getCurrentDept().name}});
					cmbOrgCode.setValue(FossUserContext.getCurrentDept().code);
					}
//					if(!(FossUserContext.getCurrentDept().transferCenter=='Y'))
//					{//不是外场
//						Ext.Ajax.request({
//									url : scheduling.realPath('queryOrgByTruckOrgCode.action'),
//	//								jsonData : leadTruckVO,
//									success : function(response) {
//										var result = Ext
//												.decode(response.responseText);
//										if(result.leadTruckVO.motorcadeEntity)
//										{//车队所属外场存在的情况
//											cmbOrgCode.getStore().load({params:{'commonOrgVo.name' : result.leadTruckVO.motorcadeEntity.transferCenterName}});
//							                cmbOrgCode.setValue(result.leadTruckVO.motorcadeEntity.transferCenter);
//										}
//									}
//								});
//					}
//					this.up('form').getForm().findField('origOrgName').setValue(FossUserContext.getCurrentDept().name);
				}
				}
			}, {
				xtype : 'dynamicorgcombselector',
				fieldLabel : scheduling.leadTruck.i18n('Foss.scheduling.leadTruck.QueryForm.destOrgName.label'),//'到达外场',
				name : 'destOrgCode',
				type : 'ORG',
//				transferCenter: 'Y',
				columnWidth : .25
			}, {
				xtype : 'rangeDateField',
				fieldLabel :scheduling.leadTruck.i18n('Foss.scheduling.leadTruck.QueryForm.startTime.label'),// '询价日期',
				columnWidth : .5,
				allowBlank:false,
				disallowBlank:true,
				// 类型为datetimefield_date97需要配置fieldId,可以赋予此属性任何唯一标 //识的String值
				id : 'Foss_scheduling_QueryForm_createTime_Id',
				fieldId:'Foss_scheduling_QueryForm_createTime_Id',
				 dateType : 'datetimefield_date97',
				fromName : 'startTime',
				fromValue : Ext.Date.format(new Date(new Date().getFullYear(),
								new Date().getMonth(), new Date().getDate(),
								'00', '00', '00'), 'Y-m-d H:i:s'),
				toName : 'endTime',
				dateRange:30,
				toValue : Ext.Date.format(new Date(new Date().getFullYear(),
								new Date().getMonth(), new Date().getDate(),
								'23', '59', '59'), 'Y-m-d H:i:s')
			}, {
				border : 1,
				xtype : 'container',
				defaultType : 'button',
				layout : 'column',
				columnWidth : 1,
				items : [{
					text : scheduling.leadTruck.i18n('Foss.scheduling.leadTruck.QueryForm.reset.label'),//'重置',
					columnWidth : .1,
					handler : function() {
						this.up('form').getForm().reset();
				this.up('form').getForm().findField('startTime').setValue(Ext.Date.format(new Date(new Date().getFullYear(),
								new Date().getMonth(), new Date().getDate(),
								'00', '00', '00'), 'Y-m-d H:i:s'));
				this.up('form').getForm().findField('endTime').setValue(Ext.Date.format(new Date(new Date().getFullYear(),
								new Date().getMonth(), new Date().getDate(),
								'23', '59', '59'), 'Y-m-d H:i:s')); 
								var cmbOrgCode = this.up('form').getForm().findField('origOrgCode');
					cmbOrgCode.getStore().load({params:{'commonOrgVo.name' : FossUserContext.getCurrentDept().name}});
					cmbOrgCode.setValue(FossUserContext.getCurrentDept().code);
					}
				}, {
					xtype : 'container',
					columnWidth : .7,
					html : '&nbsp;'
				}, {
					text : scheduling.leadTruck.i18n('Foss.scheduling.leadTruck.QueryForm.query.label'),//'查询',
					columnWidth : .1,
					cls:'yellow_button',
					handler : function() {
						if (!scheduling.leadTruck.queryform.getForm().isValid())
						return false;
						var queryParams = scheduling.leadTruck.queryform.getValues();
//						if((queryParams.origOrgCode==''&&queryParams.destOrgCode=='')||(queryParams.origOrgCode=='undefined'&&queryParams.destOrgCode=='undefined'))
//						{//出发外城和到达外城不能相同
//							Ext.MessageBox.alert(scheduling.leadTruck.i18n('Foss.scheduling.leadTruck.QueryForm.tips'),scheduling.leadTruck.i18n('Foss.scheduling.leadTruck.QueryForm.notallempty.tips'));
//							return false;
//						}else if(queryParams.origOrgCode==queryParams.destOrgCode)
//						{//出发外城和到达外城不能相同
//							Ext.MessageBox.alert(scheduling.leadTruck.i18n('Foss.scheduling.leadTruck.QueryForm.tips'),scheduling.leadTruck.i18n('Foss.scheduling.leadTruck.QueryForm.notsame.tips'));
//							return false;
//						}
						scheduling.pagingBar.moveFirst();
					}
				}]
			}],
			constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
//			me.getForm().findField('origOrgCode').getStore().load({params:{'commonOrgVo.name' : FossUserContext.getCurrentDept().name}});
				me.callParent([cfg]);
			}
		});

// 打印纸质放行条的form
Ext.define('Foss.scheduling.leadTruck.PrintManualForm', {
	extend : 'Ext.form.Panel',
	layout : 'column',
	frame : true,
	border : true,
	defaults : {
		margin : '5 5 5 5',
		columns : 4
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.items = [ {
				xtype : 'dynamicorgcombselector',
				type : 'ORG',
				transferCenter : 'Y',// 查询外场 配置此值
				fieldLabel :scheduling.leadTruck.i18n('Foss.scheduling.leadTruck.ViewManualForm.origOrgName.label'),// '出发外场',
				name : 'origOrgName',
				transferCenter: 'Y',
				columnWidth : .5,
				allowBlank : false,
				listeners: {
					'select': function(field, records, eOpts) {
						var manualWindow = Ext
								.getCmp('T_scheduling-leadTruckindex_content')
								.getManualWindow();
						var record = records[0];
						manualWindow.getManualForm().getForm().findField('origOrgName').setValue(record.get('name'));
						manualWindow.getManualForm().getForm().findField('origOrgCode').setValue(record.get('code'));
					}
				}
			}, {
				xtype : 'hiddenfield',
				name : 'origOrgCode',
				columnWidth : .5
			}, {
				xtype : 'dynamicorgcombselector',
				type : 'ORG',
				transferCenter : 'Y',// 查询外场 配置此值
				fieldLabel : scheduling.leadTruck.i18n('Foss.scheduling.leadTruck.ViewManualForm.destOrgName.label'),//'到达外场',
				name : 'destOrgName',
				transferCenter: 'Y',
				allowBlank : false,
				columnWidth : .5,
				listeners: {
					'select': function(field, records, eOpts) {
						var record = records[0];
						var manualWindow = Ext
								.getCmp('T_scheduling-leadTruckindex_content')
								.getManualWindow();
						manualWindow.getManualForm().getForm().findField('destOrgName').setValue(record.get('name'));
						manualWindow.getManualForm().getForm().findField('destOrgCode').setValue(record.get('code'));
					}
				}
			}, {
				xtype : 'hiddenfield',
				name : 'destOrgCode',
				columnWidth : .5
			}, {
				xtype : 'commonvehicletypeselector',
				fieldLabel : scheduling.leadTruck.i18n('Foss.scheduling.leadTruck.ViewManualForm.vehicleLengthName.label'),//'车长（米）',
				name : 'vehicleLengthName',
				columnWidth : .5,
				allowBlank : false,
				listeners: {
					'select': function(field, records, eOpts) {
						var record = records[0];
						var manualWindow = Ext
								.getCmp('T_scheduling-leadTruckindex_content')
								.getManualWindow();
						manualWindow.getManualForm().getForm().findField('vehicleLength').setValue(record.get('vehicleLength'));
						manualWindow.getManualForm().getForm().findField('vehicleLengthName').setValue(record.get('vehicleLengthName')+" "+record.get('vehicleLength'));
					}
				}
			}, {
				xtype : 'hiddenfield',
				name : 'vehicleLength',
				columnWidth : .5
			}, {
				xtype : 'combo',
				fieldLabel :scheduling.leadTruck.i18n('Foss.scheduling.leadTruck.ViewManualForm.containerStructure.label'),// '货箱结构',
				name : 'containerStructure',
				displayField : 'name',
				valueField : 'code',
				value : '',
				columnWidth : .5,
				allowBlank : false,
				editable:false,
				store : Ext.create('Foss.scheduling.leadTruck.ContainerStructureStore')
			}, {
				xtype : 'numberfield',
				fieldLabel :scheduling.leadTruck.i18n('Foss.scheduling.leadTruck.ViewManualForm.deadLoad.label'),// '载重（吨）',
				name : 'deadLoad',
				columnWidth : .5,
				allowDecimals: true, // 是否与许小数 
				decimalPrecision: 2, // 小数位精度 
				value:'0.00',
				allowBlank:false,			
				maxLength : 5,
				regex:/^\d+(\.\d+)?$/,
				maxLengthText: '长度已超过最大限制!'//长度已超过最大限制!

			}, {
				xtype : 'numberfield',
				fieldLabel : scheduling.leadTruck.i18n('Foss.scheduling.leadTruck.ViewManualForm.selfVolumn.label'),//'净空（m&sup3;）',
				name : 'selfVolumn',
				columnWidth : .5,
				allowDecimals: true, // 是否与许小数 
				decimalPrecision: 2, // 小数位精度 
				value:'0.00',
				regex:/^\d+(\.\d+)?$/,
				allowBlank:false,			
				maxLength : 5,
				maxLengthText: '长度已超过最大限制!'//长度已超过最大限制!
			}, {
				xtype : 'numberfield',
				fieldLabel :scheduling.leadTruck.i18n('Foss.scheduling.leadTruck.ViewManualForm.fee.label'),// '运价（元）',
				name : 'fee',
				columnWidth : .5,
				allowDecimals: false, // 是否与许小数 
				decimalPrecision: 2, // 小数位精度 
				value:'0.00',
				regex:/^\d+(\.\d+)?$/,
				allowBlank:false,			
				maxLength : 5,
				maxLengthText: '长度已超过最大限制!'//长度已超过最大限制!
			}, {
				xtype : 'datetimefield_date97',
				fieldLabel : scheduling.leadTruck.i18n('Foss.scheduling.leadTruck.ViewManualForm.inquiryTime.tips'),//'询价日期',
				name : 'inquiryTime',
				columnWidth : .5,
//				format : 'Y-m-d H:i:s',
				id:'Foss_leadtruck_PrintManualForm_trackingTime_ID',
				allowBlank : false,
				value:Ext.Date.format(new Date(new Date().getFullYear(),
								new Date().getMonth(), new Date().getDate(),
								new Date().getHours(), new Date().getMinutes(), new Date().getSeconds()), 'Y-m-d H:i:s'),
				dateConfig: {
					el: 'Foss_leadtruck_PrintManualForm_trackingTime_ID-inputEl',
					dateFmt: 'Y-m-d H:i:s'
				}
			}, {
				xtype : 'textfield',
				fieldLabel :scheduling.leadTruck.i18n('Foss.scheduling.leadTruck.ViewManualForm.sourceOfInformation.tips'),// '信息来源',
				name : 'sourceOfInformation',
				maxLength:200,
				maxLengthText: '输入的字符数不能超过200个',
				columnWidth : .5
			}, {
				xtype : 'textfield',
				fieldLabel :scheduling.leadTruck.i18n('Foss.scheduling.leadTruck.ViewManualForm.tele.label'),// '联系电话',
				name : 'tele',
				maxLength:50,
				maxLengthText: '输入的字符数不能超过50个',
				columnWidth : .5
			}, {
				xtype : 'container',
				columnWidth : .1,
				html : '&nbsp;'
			}, {
				xtype : 'checkboxgroup', // 复选框组
				vertical : true, // 按照columns中指定的列数来分配复选框组件
				// 验证表单输入不能为空，属性值：true表示可以为空，false表示不可以为空
				allowBlank : true,
				columnWidth : .5,
				blankText : scheduling.leadTruck.i18n('Foss.scheduling.leadTruck.ViewManualForm.isContractTruck.label'),//'是否合同车',// 当没有选择时的提示信息
				items : [ {
							boxLabel :scheduling.leadTruck.i18n('Foss.scheduling.leadTruck.ViewManualForm.isContractTruck.label'),// '是否合同车',
							name : 'isContractTruck', // 表单的参数名
							inputValue : 'Y', // 表单的参数值
							checked : false // 选中
						}]
			}, {
				xtype : 'textarea',
				// 当内容超过文本域的范围时，出现滚动条
				autoScroll : true,
				// 指定文本中的值
				maxLength:500,
				maxLengthText: '输入的字符数不能超过500个',
				fieldLabel : scheduling.leadTruck.i18n('Foss.scheduling.leadTruck.ViewManualForm.notes.label'),//'备注',
				// 指定数据绑定与数据提交时的属性名
				name : 'notes',
				columnWidth : 1

			}, {
				xtype : 'hiddenfield',
				name : 'id',
				columnWidth : .5
			}, {
				xtype : 'container',
				columnWidth : .6,
				html : '&nbsp;'
			},{
				xtype : 'button',
					text : scheduling.leadTruck.i18n('Foss.scheduling.leadTruck.ViewManualForm.reset.button'),//'清空',
					columnWidth : .2,
					handler : function() {
						this.up('form').getForm().reset();
					}
				}, {
				xtype : 'button',
				text : scheduling.leadTruck.i18n('Foss.scheduling.leadTruck.ViewManualForm.save.button'),//'保存',
				columnWidth : .2,
				handler : function() {
					var self = this;
					
					var manualWindow = Ext.getCmp('T_scheduling-leadTruckindex_content')
							.getManualWindow();
					var formParams = manualWindow.manualForm.getValues();
					if (!manualWindow.getManualForm().getForm().isValid())
						return false;
//					formParams.inquiryTime = new Date(formParams.inquiryTime.format("yyyy-MM-dd hh:mm:ss"));
					var leadTruckVO = {
						leadTruckVO: {manualEntity : formParams}
					};
					
					self.getEl().mask(scheduling.leadTruck.i18n('Foss.scheduling.saving'));
					Ext.Ajax.request({
								url : scheduling.realPath('saveOrAddLeadTruck.action'),
								jsonData : leadTruckVO,
								success : function(response) {
									var result = Ext
											.decode(response.responseText);
									Ext.getCmp('Foss_scheduling_WaybillGrid_ID').store
											.load();
									Ext.ux.Toast.msg(scheduling.leadTruck.i18n('Foss.scheduling.leadTruck.QueryForm.tips'), scheduling.leadTruck.i18n('Foss.scheduling.leadTruck.ViewManualForm.notsame.tips'), 'ok', 3000);
									manualWindow.getManualForm().getForm().reset();

									manualWindow.hide();
									
									self.getEl().unmask();
									
								},exception :function(response)
								{
									self.getEl().unmask();
								    var result = Ext.decode(response.responseText);
									Ext.MessageBox.alert(scheduling.leadTruck.i18n('Foss.scheduling.leadTruck.QueryForm.tips'),result.message);
								}
							});
				}
			}],
		me.callParent([cfg]);
	}
});
//显示表单
Ext.define('Foss.scheduling.leadTruck.ViewManualForm', {
	extend : 'Ext.form.Panel',
	layout : 'column',
	frame : true,
	border : true,
	defaults : {
		margin : '5 5 5 5',
		columns : 4
	},
	items : [ {
				xtype : 'textfield',
				fieldLabel :  scheduling.leadTruck.i18n('Foss.scheduling.leadTruck.ViewManualForm.origOrgName.label'),//'出发部门',
				name : 'origOrgName',
				columnWidth : .5,
				readOnly:true
			}, {
				xtype : 'textfield',
				fieldLabel : scheduling.leadTruck.i18n('Foss.scheduling.leadTruck.ViewManualForm.destOrgName.label'),// '到达部门',
				name : 'destOrgName',
				columnWidth : .5,
				readOnly:true
			}, {
				xtype : 'textfield',
				fieldLabel : scheduling.leadTruck.i18n('Foss.scheduling.leadTruck.ViewManualForm.vehicleLengthName.label'),// '车长（米）',
				name : 'vehicleLengthName',
				columnWidth : .5,
				readOnly:true
			}, {
				xtype : 'combo',
				fieldLabel :  scheduling.leadTruck.i18n('Foss.scheduling.leadTruck.ViewManualForm.containerStructure.label'),//'货箱结构',
				name : 'containerStructure',
				displayField : 'name',
				valueField : 'code',
				value : '',
				columnWidth : .5,
				readOnly:true,
				store : Ext.create('Foss.scheduling.leadTruck.ContainerStructureStore')
			}, {
				xtype : 'numberfield',
				fieldLabel : scheduling.leadTruck.i18n('Foss.scheduling.leadTruck.ViewManualForm.deadLoad.label'),//'载重（吨）',
				name : 'deadLoad',
				columnWidth : .5,
				readOnly:true
			}, {
				xtype : 'numberfield',
				fieldLabel :  scheduling.leadTruck.i18n('Foss.scheduling.leadTruck.ViewManualForm.selfVolumn.label'),//'净空（m&sup3;）',
				name : 'selfVolumn',
				columnWidth : .5,
				readOnly:true
			}, {
				xtype : 'numberfield',
				fieldLabel : scheduling.leadTruck.i18n('Foss.scheduling.leadTruck.ViewManualForm.fee.label'),// '运价（元）',
				name : 'fee',
				columnWidth : .5,
				readOnly:true
			}, {
				xtype : 'datetimefield_date97',
				fieldLabel :  scheduling.leadTruck.i18n('Foss.scheduling.leadTruck.ViewManualForm.inquiryTime.tips'),//'询价日期',
				name : 'inquiryTime',
				columnWidth : .5,
				format : 'Y-m-d H:i:s',
				readOnly:true
			}, {
				xtype : 'textfield',
				fieldLabel : scheduling.leadTruck.i18n('Foss.scheduling.leadTruck.ViewManualForm.sourceOfInformation.tips'),// '信息来源',
				name : 'sourceOfInformation',
				readOnly:true,
				columnWidth : .5
			}, {
				xtype : 'textfield',
				fieldLabel :scheduling.leadTruck.i18n('Foss.scheduling.leadTruck.ViewManualForm.tele.label'),// '联系电话',
				name : 'tele',
				readOnly:true,
				columnWidth : .5
			}, {
				xtype : 'container',
				columnWidth : .1,
				html : '&nbsp;'
			}, {
				xtype : 'checkboxgroup', // 复选框组
				vertical : true, // 按照columns中指定的列数来分配复选框组件
				// 验证表单输入不能为空，属性值：true表示可以为空，false表示不可以为空
				readOnly:true,
				columnWidth : .5,
				blankText :  scheduling.leadTruck.i18n('Foss.scheduling.leadTruck.ViewManualForm.isContractTruck.label'),//'是否合同车',// 当没有选择时的提示信息
				items : [ {
							boxLabel :  scheduling.leadTruck.i18n('Foss.scheduling.leadTruck.ViewManualForm.isContractTruck.label'),//'是否合同车',
							name : 'isContractTruck', // 表单的参数名
							inputValue : 'Y', // 表单的参数值
							readOnly:true,
							checked : true // 选中
						}]
			}, {
				xtype : 'textarea',
				// 当内容超过文本域的范围时，出现滚动条
				autoScroll : true,
				// 指定文本中的值
				fieldLabel :  scheduling.leadTruck.i18n('Foss.scheduling.leadTruck.ViewManualForm.notes.label'),//'备注',
				readOnly:true,
				// 指定数据绑定与数据提交时的属性名
				name : 'notes',
				columnWidth : 1

			}, {
				xtype : 'hiddenfield',
				name : 'id',
				columnWidth : .5
			}, {
				xtype : 'container',
				columnWidth : .6,
				html : '&nbsp;'
			}],

	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

Ext.define('Foss.scheduling.leadTruck.LeadTruckGrid', {
	extend : 'Ext.grid.Panel',
	// 指定grid对象在DOM树中的唯一值
	id : 'Foss_scheduling_WaybillGrid_ID',
	height : 390,
	autoScroll : true,
	emptyText:scheduling.leadTruck.i18n('Foss.scheduling.leadTruck.LeadTruckGrid.origOrgName.title'),//"查询结果为空",
	// 增加表格列的分割线
	columnLines : true,
	// 表格对象增加一个边框
	frame : true,
	// 定义表格列信息
	columns : [{
		// 字段标题
		xtype:'actioncolumn',
		header : scheduling.leadTruck.i18n('Foss.scheduling.leadTruck.LeadTruckGrid.operate.label'),//'操作',
		// 关联model中的字段名
		width : 50,
		dataIndex : 'departType',
		  items: [{
                iconCls: 'deppon_icons_showdetail',
                tooltip: scheduling.leadTruck.i18n('Foss.scheduling.leadTruck.LeadTruckGrid.view.label'),//'查看',
                handler: function(grid, rowIndex, colIndex) {
                  var showManualWindow = Ext
								.getCmp('T_scheduling-leadTruckindex_content')
								.getShowManualWindow();
                  showManualWindow.getViewManualForm().loadRecord(grid.getStore().getAt(rowIndex));
                  showManualWindow.getViewManualForm().getForm().findField('inquiryTime').setValue(Ext.Date.format(grid.getStore().getAt(rowIndex).get('inquiryTimeDate'), 'Y-m-d H:i:s'));
								showManualWindow.show();
                }

		  },{
                iconCls: 'deppon_icons_edit',
                tooltip:scheduling.leadTruck.i18n('Foss.scheduling.leadTruck.LeadTruckGrid.edit.label'),// '编辑',
                handler: function(grid, rowIndex, colIndex) {
                  //非本人录入无法修改
                  if(grid.getStore().getAt(rowIndex).get('createUserCode')!=FossUserContext.getCurrentUser().employee.empCode)
                  {
                    Ext.MessageBox.alert(scheduling.leadTruck.i18n('foss.scheduling.borrowvehicle.button.submit'),scheduling.leadTruck.i18n('Foss.scheduling.leadTruck.QueryForm.notcurrent.tips'), 'error', 1000);	//提示
                    return false;
                  }
                  var manualWindow = Ext
								.getCmp('T_scheduling-leadTruckindex_content')
								.getManualWindow();
                  manualWindow.getManualForm().loadRecord(grid.getStore().getAt(rowIndex));
                  manualWindow.getManualForm().getForm().findField('inquiryTime').setValue(Ext.Date.format(grid.getStore().getAt(rowIndex).get('inquiryTimeDate'), 'Y-m-d H:i:s'));
								manualWindow.show();
                }
		  }]
	},{
		// 字段标题
		header : scheduling.leadTruck.i18n('Foss.scheduling.leadTruck.LeadTruckGrid.inquiryTime.label'),//'询价日期',
		// 关联model中的字段名
		width : 100,
		xtype : 'datecolumn',
		dataIndex : 'inquiryTimeDate',
		format : 'Y-m-d H:i:s'
	}, {
		// 字段标题
		header :scheduling.leadTruck.i18n('Foss.scheduling.leadTruck.LeadTruckGrid.origOrgName.label'),// '出发外场',
		width : 80,
		// 关联model中的字段名
		dataIndex : 'origOrgName'
	}, {
		// 字段标题
		header : scheduling.leadTruck.i18n('Foss.scheduling.leadTruck.LeadTruckGrid.destOrgName.label'),//'到达外场',
		width : 140,
		// 关联model中的字段名
		dataIndex : 'destOrgName'
	}, {
		// 字段标题
		header : scheduling.leadTruck.i18n('Foss.scheduling.leadTruck.LeadTruckGrid.vehicleLength.tips'),//'车长',
		width : 0,
		// 关联model中的字段名
		dataIndex : 'vehicleLength'
	}, {
		// 字段标题
		header : scheduling.leadTruck.i18n('Foss.scheduling.leadTruck.LeadTruckGrid.vehicleLength.tips'),//'车长',
		width : 80,
		// 关联model中的字段名
		dataIndex : 'vehicleLengthName'
	}, {
		// 字段标题
		header : scheduling.leadTruck.i18n('Foss.scheduling.leadTruck.LeadTruckGrid.containerStructure.tips'),//'货箱结构',
		width : 100,
		// 关联model中的字段名
		dataIndex : 'containerStructure',
		renderer : function(value) {
			var store = Ext.create('Foss.scheduling.leadTruck.ContainerStructureStore'), record = store
					.findRecord('code', value);
			if (record != null) {
				return record.get('name');
			} else {
				return '';
			}
		}
	}, {
		// 字段标题
		header : scheduling.leadTruck.i18n('Foss.scheduling.leadTruck.LeadTruckGrid.fee.label'),//'运价',
		width : 100,
		// 关联model中的字段名
		dataIndex : 'fee'
	}, {
		// 字段标题
		header : scheduling.leadTruck.i18n('Foss.scheduling.leadTruck.LeadTruckGrid.deadLoad.label'),//'载重',
		width : 100,
		// 关联model中的字段名
		dataIndex : 'deadLoad'
	}, {
		// 字段标题
		header :scheduling.leadTruck.i18n('Foss.scheduling.leadTruck.LeadTruckGrid.notes.label'),// '净空',
		width : 100,
		// 关联model中的字段名
		dataIndex : 'selfVolumn'
	}, {
		// 字段标题
		header : scheduling.leadTruck.i18n('Foss.scheduling.leadTruck.LeadTruckGrid.createUserName.button'),//'录入人',
		width : 140,
		dataIndex:'createUserName'
	}, {
		// 字段标题
		width : 0,
		dataIndex:'createUserCode'
	}, {
		// 字段标题
		header : scheduling.leadTruck.i18n('Foss.scheduling.leadTruck.LeadTruckGrid.createTime.label'),//'录入时间',
		width : 200,
		xtype : 'datecolumn',
		// 关联model中的字段名
		dataIndex : 'createTime',
		format : 'Y-m-d H:i:s'
	}, {
		// 字段标题
		header : scheduling.leadTruck.i18n('Foss.scheduling.leadTruck.LeadTruckGrid.updateTime.label'),//'修改时间',
		width : 200,
		// 关联model中的字段名
		xtype : 'datecolumn',
		dataIndex : 'updateTime',
		format : 'Y-m-d H:i:s'
	}, {
				dataIndex : 'id',
				width:0
			}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.scheduling.leadTruck.LeadTruckStore');
//		me.selModel = Ext.create('Ext.selection.CheckboxModel');

		me.tbar = [{
					xtype : 'button',
					text : scheduling.leadTruck.i18n('Foss.scheduling.leadTruck.LeadTruckGrid.add.button'),//'新增',
					gridContainer : this,
					handler : function() {
						var manualWindow = Ext
								.getCmp('T_scheduling-leadTruckindex_content')
								.getManualWindow();
								manualWindow.show();
						manualWindow.getManualForm().getForm().reset();		
					}
				
				
				},{
				xtype : 'button',
				text : '导出',
				name : 'export',
				handler : function(){
					var records = me.getStore().getRange(0,
							me.getStore().data.items.length);
					if(records.length<1)
					{
					  Ext.MessageBox.alert('提示','没有查询到记录，不能导出');
					  return false;
					}
					var queryParams = scheduling.leadTruck.queryform.getValues();
					if(!Ext.fly('downloadAttachFileForm')){
						    var frm = document.createElement('form');
						    frm.id = 'downloadAttachFileForm';
						    frm.style.display = 'none';
						    document.body.appendChild(frm);
					}		
					var array = new Array();
					for (var i = 0; i < records.length; i++) {
						array.push(records[i].data.id);
					}
					Ext.Ajax.request({
						url:scheduling.realPath('exportUnloadWayBillByTaskNo.action'),
						form: Ext.fly('downloadAttachFileForm'),
						method : 'POST',
						params : {
							'leadTruckVO.ids' : array
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
		scheduling.pagingBar = me.bbar;
		me.callParent([cfg]);
	}
});
Ext.define('Foss.scheduling.leadTruck.PrintManualWindow', {
			extend : 'Ext.window.Window',
//			id : 'Foss.scheduling.PrintManualWindow_ID',
			// title: lineinfo.i18n('foss.lineinfo.windowTitle'),
			title :scheduling.leadTruck.i18n('Foss.scheduling.leadTruck.PrintManualWindow.title'),// '外请车询价登记',
			bodyCls : 'autoHeight',
			cls : 'autoHeight',
			modal : true,
			closeAction : 'hide',
			width : 800,
			manualForm : null,
			getManualForm : function() {
				if (this.manualForm == null) {
					this.manualForm = Ext
							.create('Foss.scheduling.leadTruck.PrintManualForm')
				}
				return this.manualForm;
			},
			viewManualForm : null,
			getViewManualForm : function() {
				if (this.viewManualForm == null) {
					this.viewManualForm = Ext
							.create('Foss.scheduling.leadTruck.ViewManualForm')
				}
				return this.viewManualForm;
			},
			constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				me.items = [me.getManualForm()];
				scheduling.manualForm = me.getManualForm();
				me.callParent([cfg]);
			}
		});
Ext.define('Foss.scheduling.leadTruck.ShowManualWindow', {
			extend : 'Ext.window.Window',
			id : 'Foss.scheduling.ShowManualWindow_ID',
			// title: lineinfo.i18n('foss.lineinfo.windowTitle'),
			title :scheduling.leadTruck.i18n('Foss.scheduling.leadTruck.ShowManualWindow.title'),// '外请车询价查询',
			bodyCls : 'autoHeight',
			cls : 'autoHeight',
			modal : true,
			closeAction : 'hide',
			width : 800,
			viewManualForm : null,
			getViewManualForm : function() {
				if (this.viewManualForm == null) {
					this.viewManualForm = Ext
							.create('Foss.scheduling.leadTruck.ViewManualForm')
				}
				return this.viewManualForm;
			},
			constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				me.items = [me.getViewManualForm()];
				scheduling.viewManualForm = me.getViewManualForm();
				me.callParent([cfg]);
			}
		});
		
Ext.onReady(function() {
			Ext.QuickTips.init();
			var queryform = Ext.create('Foss.scheduling.leadTruck.QueryForm');
			var querygrid = Ext.create('Foss.scheduling.leadTruck.LeadTruckGrid');
			scheduling.leadTruck.queryform = queryform;
			scheduling.leadTruck.querygrid = querygrid;
			// var querygrid = Ext.create('Foss.scheduling.WaybillGrid');
			Ext.create('Ext.panel.Panel', {
						id : 'T_scheduling-leadTruckindex_content',
						cls : "panelContentNToolbar",
						bodyCls : 'panelContent-body',
						manualWindow : null,
						getManualWindow : function() {
							if (this.manualWindow == null) {
								this.manualWindow = Ext
										.create('Foss.scheduling.leadTruck.PrintManualWindow');
							}
							scheduling.leadTruck.manualWindow = this.manualWindow;
							return this.manualWindow;
						},
						showManualWindow : null,
						getShowManualWindow : function() {
							if (this.showManualWindow == null) {
								this.showManualWindow = Ext
										.create('Foss.scheduling.leadTruck.ShowManualWindow');
							}
							return this.showManualWindow;
						},
						items : [queryform, querygrid],
						renderTo : 'T_scheduling-leadTruckindex-body'
					});
		});