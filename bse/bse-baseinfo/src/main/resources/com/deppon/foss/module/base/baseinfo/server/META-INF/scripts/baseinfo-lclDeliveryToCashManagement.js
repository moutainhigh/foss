var addSplit=function(value){
	return value=Ext.util.Format.substr(value,0,2)+Ext.util.Format.substr(value,3,1);
}
//查询条件面板
Ext.define('Foss.baseinfo.lclDeliveryToCashManagementDelivery.SelectConditionForm', {
	extend : 'Ext.form.Panel',
	id : 'Foss_baseinfo_lclDeliveryToCashManagementDelivery_SelectConditionForm_Id',
	itemId : 'Foss_baseinfo_lclDeliveryToCashManagementDelivery_SelectConditionForm_ItemId',
	layout : 'column',
	frame : true,
	columnWidth : 0.98,
	title : baseinfo.lclDeliveryToCashManagement.i18n('foss.baseinfo.queryCondition'),//查询条件
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
				columns: 1,
				defaultType : 'textfield',
				items : [{
					    xtype : 'commonsaledepartmentselector',
						name : 'orgCode',
						fieldLabel : '派送部门',
						delivery:'Y',
						margin : '5 20 5 0',
						columnWidth : .48,
						labelWidth : 160
					}, Ext.create('Ext.container.Container', {
						columnWidth : 1,
						layout : 'column',
						items : [{
								columnWidth : 0.00075,
								height : 0,
								xtype : 'container',
								style : 'padding:30px 0 0 650px;border:none',
								hide : true
							}, Ext.create('Ext.button.Button', {
								xtype : 'button',
								cls : 'yellow_button',
								name : 'buttonReset',
								text : baseinfo.lclDeliveryToCashManagement.i18n('foss.baseinfo.reset'),
								disabled:!baseinfo.lclDeliveryToCashManagement.isPermission('lclDeliveryToCashManagementindex/lclDeliveryToCashManagementDeliveryQueryButton'),
								hidden:!baseinfo.lclDeliveryToCashManagement.isPermission('lclDeliveryToCashManagementindex/lclDeliveryToCashManagementDeliveryQueryButton'),
								columnWidth : .12,
								margin : '0,10,0,0',
								width : 20,
								handler : function () {
									var a_conditionForm = Ext.getCmp("Foss_baseinfo_lclDeliveryToCashManagementDelivery_SelectConditionForm_Id");
									// 将表单中的数据清空
									a_conditionForm.getForm().reset();
								}
							}), {
								columnWidth : 0.010,
								height : 0,
								xtype : 'container',
								style : 'padding-top:30px;border:none',
								hide : true
							}, Ext.create('Ext.button.Button', {
								xtype : 'button',
								cls : 'yellow_button',
								name : 'buttonSelect',
								text : baseinfo.lclDeliveryToCashManagement.i18n('foss.baseinfo.query'),
								disabled:!baseinfo.lclDeliveryToCashManagement.isPermission('lclDeliveryToCashManagementindex/lclDeliveryToCashManagementDeliveryQueryButton'),
								hidden:!baseinfo.lclDeliveryToCashManagement.isPermission('lclDeliveryToCashManagementindex/lclDeliveryToCashManagementDeliveryQueryButton'),
								columnWidth : .12,
								margin : '0,10,0,30',
								width : 20,
								//查询按钮的响应事件
								handler : function () {
									baseinfo.lclDeliveryToCashManagement.lclDeliveryToCashManagementDelivery.pagingBar.moveFirst();
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
// 下面是详细实现
//==============================================================================================
/**
 * 送兑现时效管理 界面数据模型定义
 */
Ext.define('Foss.baseinfo.lclDeliveryToCashManagementDelivery.lclDeliveryToCashManagementDeliveryModel', {
	extend : 'Ext.data.Model',
	fields : [
		// ID
		{
			name : 'id',
			type : 'string'
		},
		// 派送部门编码
		{
			name : 'orgCode',
			type : 'string'
		},
		// 派送部门名称
		{
			name : 'orgName',
			type : 'string'
		},
		// 卸出开始时间
		{
			name : 'startDate',
			type : 'date',
			convert : dateConvert
		},
		// 卸出结束时间 
		{
			name : 'endDate',
			type : 'date',
			convert : dateConvert
		
		},
		// 卸出时间 
		{
			name:'lastDate',
			type:'String',
			convert:function(v,record){
				if(Ext.isEmpty(record.data.startDate)||Ext.isEmpty(record.data.endDate)){
					return null;
				}
				return Ext.Date.format(record.data.startDate,'H:i')+'-'+Ext.Date.format(record.data.endDate,'H:i')
			}
		},
		// 规定兑现时间时分
		{
			name : 'deliverOnTime',
			type : 'string'
		},
		// 规定兑现时间天
		{
			name : 'deliverOnDay',
			type : 'string'
		},
		// 创建时间
		{
			name : 'createDate',
			type : 'date',			
			convert: dateConvert
		},
		// 更新时间
		{
			name : 'modifyDate',
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
		// 创建姓名
		{
			name : 'createUser',
			type : 'string'
		},
		// 更新人
		{
			name : 'modifyUserCode',
			type : 'string'
		},
		// 备注
		{
			name : 'reMark',
			type : 'string'
		}
	]
});
//右边模块-查询结果面板
Ext.define('Foss.baseinfo.lclDeliveryToCashManagementDelivery.SelectResultForm', {
	extend : 'Ext.form.Panel',
	id : 'Foss_baseinfo_lclDeliveryToCashManagementDelivery_SelectResultForm_Id',
	itemId : 'Foss_baseinfo_lclDeliveryToCashManagementDelivery_SelectResultForm_ItemId',
	layout : 'column',
	frame : true,
	hidden : false,
	columnWidth : 0.98,
	cls : 'autoHeight',
	bodyCls : 'autoHeight',
	title : '派送兑现列表',
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
					Ext.create('Foss.baseinfo.lclDeliveryToCashManagementDelivery.lclDeliveryToCashManagementDeliveryGrid')
				]
			})
		];
		me.callParent([cfg]);
	}
});
//查询的显示表格
Ext.define('Foss.baseinfo.lclDeliveryToCashManagementDelivery.lclDeliveryToCashManagementDeliveryGrid', {
	extend : 'Ext.grid.Panel',
	id : 'Foss_baseinfo_lclDeliveryToCashManagementDelivery_lclDeliveryToCashManagementDeliveryGrid_Id',
	cls : 'autoHeight',
	bodyCls : 'autoHeight',
	//columnWidth : .99,
	columnWidth : 1,
	// 设置表格不可排序
	sortableColumns : false,
	// 去掉排序的倒三角
	enableColumnHide : false,
	// 设置“如果查询的结果为空，则提示”
	emptyText : baseinfo.lclDeliveryToCashManagement.i18n('foss.baseinfo.queryResultIsNull'),
	//增加滚动条
	autoScroll : true,
	stripeRows : true, // 交替行效果
	collapsible : true,
	animCollapse : true,
	selType : "rowmodel", // 选择类型设置为行选择
	store : null,
	selModel : Ext.create('Ext.selection.CheckboxModel'),
	columns : [{
			xtype : 'actioncolumn',
			flex : 1,
			text : baseinfo.lclDeliveryToCashManagement.i18n('foss.baseinfo.operate'),
			align : 'center',
			items : [{
					iconCls : 'deppon_icons_edit',
					tooltip : baseinfo.lclDeliveryToCashManagement.i18n('foss.baseinfo.update'),
					disabled:!baseinfo.lclDeliveryToCashManagement.isPermission('lclDeliveryToCashManagementindex/lclDeliveryToCashManagementUpdateButton'),
					handler : function (grid, rowIndex, colIndex) {
						baseinfo.lclDeliveryToCashManagement.lclDeliveryToCashManagementDelivery.flagAddModify = 'modify';
						var a_updateForm = baseinfo.lclDeliveryToCashManagement.lclDeliveryToCashManagementDelivery.windowz.down('form');
						var rowInfo = grid.getStore().getAt(rowIndex);
						a_updateForm.getForm().findField('orgCode').setReadOnly(true);
						a_updateForm.getForm().findField('orgCode').store.add({
							'code' : rowInfo.data.orgCode,
							'name' : rowInfo.data.orgName
						});
						a_updateForm.loadRecord(rowInfo);
						//将表格里面被选中的信息先保存起来，重置的时候使用
						baseinfo.lclDeliveryToCashManagement.lclDeliveryToCashManagementDelivery.rawRecord = rowInfo;

						var a_win = baseinfo.lclDeliveryToCashManagement.lclDeliveryToCashManagementDelivery.windowz;
						a_win.cashManagementDeliveryEntity=rowInfo;
						a_win.setTitle('修改派送兑现');
						a_win.show();
					}
				}, {
					iconCls : 'deppon_icons_cancel',
					tooltip : baseinfo.lclDeliveryToCashManagement.i18n('foss.baseinfo.void'),
					disabled:!baseinfo.lclDeliveryToCashManagement.isPermission('lclDeliveryToCashManagementindex/lclDeliveryToCashManagementVoidButton'),
					handler : function (grid, rowIndex, colIndex) {
						Ext.MessageBox.buttonText.yes = baseinfo.lclDeliveryToCashManagement.i18n('foss.baseinfo.sure');
						Ext.MessageBox.buttonText.no = baseinfo.lclDeliveryToCashManagement.i18n('foss.baseinfo.cancel');
						Ext.Msg.confirm(baseinfo.lclDeliveryToCashManagement.i18n('foss.baseinfo.tipInfo'), baseinfo.lclDeliveryToCashManagement.i18n('foss.baseinfo.sureDelete'), function (btn, text) {
							if (btn == 'yes') {
								var msgTip = baseinfo.lclDeliveryToCashManagement.i18n('foss.baseinfo.deleteSuccess');

								// 获得当前选择的数据
								var rowInfo = grid.getStore().getAt(rowIndex).data;
								//发送作废结点的Ajax请求.
								var lclDeliveryToCashManagementDeliveryVo = new Object();
								lclDeliveryToCashManagementDeliveryVo.lclDeliveryToCashManagementDeliveryEntity = new Object();
								lclDeliveryToCashManagementDeliveryVo.lclDeliveryToCashManagementDeliveryEntity.id = rowInfo.id;
								lclDeliveryToCashManagementDeliveryVo.lclDeliveryToCashManagementDeliveryEntity.orgCode = rowInfo.orgCode;
								var params = {
									'lclDeliveryToCashManagementDeliveryVo' : lclDeliveryToCashManagementDeliveryVo
								};
								r_url = "deletelclDeliveryToCashManagementDelivery.action";
								r_url = baseinfo.realPath(r_url);
								Ext.Ajax.request({
									url : r_url,
									jsonData : params,
									success : function (response) {
										var json = Ext.decode(response.responseText);
										grid.getStore().removeAt(rowIndex);
										baseinfo.lclDeliveryToCashManagement.lclDeliveryToCashManagementDelivery.pagingBar.moveFirst();
									},
									exception : function (response) {
										var json = Ext.decode(response.responseText);

										msgTip = baseinfo.lclDeliveryToCashManagement.i18n('foss.baseinfo.deleteFailure');
									}
								});
								Ext.Msg.alert(baseinfo.lclDeliveryToCashManagement.i18n('foss.baseinfo.tips'), msgTip);
							}
						});

					}
				}
			]
		}, {
			hidden : true,
			dataIndex : 'orgCode',
			text : baseinfo.lclDeliveryToCashManagement.i18n('foss.baseinfo.deptCode'),
			margin : '5 20 5 10'
		}, {
			hidden : true,
			dataIndex : 'id',
			text :'ID',
			margin : '5 20 5 10'
		},{
			dataIndex : 'orgName',
			text : '派送部门',
			xtype : 'ellipsiscolumn',
			align : 'left',
			flex : 3.5
		}, {
			dataIndex : 'lastDate',
			text : '卸出时间段',
			xtype : 'datecolumn',
			align : 'center',
			flex : 2,
			renderer:function(value){
			if(value instanceof Date){   
            return new Date(value).format("H:i");   
             }else{   
             return value;   
             }
			}
		}, {
			dataIndex : 'deliverOn',
			text : '规定兑现时间',
			xtype : 'datecolumn',
			align : 'center',
			flex : 2,
				renderer : function (value, cellmeta, record, rowIndex, columnIndex, store) {
				var aimStr = value;
				if (record && record.raw) {
				aimStr = '';
				aimStr += record.raw.deliverOnTime +'+' +record.raw.deliverOnDay+'天';
				}
				return aimStr;
			}
		},{
			dataIndex : 'reMark',
			text : '备注',
			xtype : 'ellipsiscolumn',
			align : 'center',
			flex : 2
		},{
			dataIndex : 'createUser',
			text : '操作人',
			xtype : 'ellipsiscolumn',
			align : 'center',
			flex : 1.5
		},{
			dataIndex : 'createUserCode',
			text : '工号',
			xtype : 'ellipsiscolumn',
			align : 'center',
			flex : 1.5
		},{
			dataIndex : 'createDate', 
			text : '创建时间',
			xtype : 'datecolumn',
			align : 'center',
			flex : 3,
			format:'Y-m-d H:i:s'
		}
	],

	getTbar : function () {
		var me = this;
		return [{
				text : baseinfo.lclDeliveryToCashManagement.i18n('foss.baseinfo.add'),
				disabled:!baseinfo.lclDeliveryToCashManagement.isPermission('lclDeliveryToCashManagementindex/lclDeliveryToCashManagementDeliveryAddButton'),
				hidden:!baseinfo.lclDeliveryToCashManagement.isPermission('lclDeliveryToCashManagementindex/lclDeliveryToCashManagementDeliveryAddButton'),
				handler : function () {
					baseinfo.lclDeliveryToCashManagement.lclDeliveryToCashManagementDelivery.flagAddModify = 'add'
					var a_win = baseinfo.lclDeliveryToCashManagement.lclDeliveryToCashManagementDelivery.windowz;
					a_win.setTitle('新增派送兑现');
					a_win.setVisible(true);
					var a_form = a_win.down('form');
					// 设置部门编码可以修改
					a_form.getForm().findField('orgCode').setReadOnly(false);
					a_form.getForm().reset();
				}
			}, '-', {
				text : baseinfo.lclDeliveryToCashManagement.i18n('foss.baseinfo.void'), //作废
				disabled:!baseinfo.lclDeliveryToCashManagement.isPermission('lclDeliveryToCashManagementindex/lclDeliveryToCashManagementVoidButton'),
				hidden:!baseinfo.lclDeliveryToCashManagement.isPermission('lclDeliveryToCashManagementindex/lclDeliveryToCashManagementVoidButton'),
				handler : function () {
					baseinfo.lclDeliveryToCashManagement.lclDeliveryToCashManagementDelivery.deletelclDeliveryToCashManagementDelivery(true, null);
				}
			}
		];
	},

	constructor : function (config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.store = Ext.create('Ext.data.Store', {
				model : 'Foss.baseinfo.lclDeliveryToCashManagementDelivery.lclDeliveryToCashManagementDeliveryModel',
				pageSize : 25,
				autoLoad : false,
				proxy : {
					type : 'ajax',
					actionMethods : 'POST',
					url : baseinfo.realPath("querylclDeliveryToCashManagementDeliveryExactByEntity.action"),
					//定义一个读取器
					reader : {
						type : 'json',
						root : 'lclDeliveryToCashManagementDeliveryVo.lclDeliveryToCashManagementDeliveryList',
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
						var queryForm = baseinfo.lclDeliveryToCashManagement.lclDeliveryToCashManagementDelivery.queryForm;
						if (queryForm != null) {
							var queryParams = queryForm.getValues();
							Ext.apply(operation, {
								params : {
									'lclDeliveryToCashManagementDeliveryVo.lclDeliveryToCashManagementDeliveryEntity.orgCode' : queryParams.orgCode
								}
							});
						}
					}
				}
			});
		me.bbar = Ext.create('Deppon.StandardPaging', {
				store : me.store,
				plugins: 'pagesizeplugin',
				pageSize:25,
				prependButtons: true,
				defaults : {
					margin : '0 0 15 3'
				}
			});
		//添加头部按钮
		me.tbar = me.getTbar();
		baseinfo.lclDeliveryToCashManagement.lclDeliveryToCashManagementDelivery.pagingBar = me.bbar;
		me.callParent([cfg]);
	}
});
//定义主面板
Ext.define('Foss.baseinfo.lclDeliveryToCashManagementDelivery.MainPanel', {
	extend : 'Ext.form.Panel',
	id : 'Foss_baseinfo_lclDeliveryToCashManagementDelivery_MainPanel_Id',
	itemId : 'Foss_baseinfo_lclDeliveryToCashManagementDelivery_MainPanel_ItemId',
	title : '派送兑现时效管理',
	cls : 'autoHeight',
	bodyCls : 'autoHeight',
	defaultType : 'textfield',
	layout : 'column',
//	frame : true,
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
			Ext.create('Foss.baseinfo.lclDeliveryToCashManagementDelivery.SelectConditionForm'),
			Ext.create('Foss.baseinfo.lclDeliveryToCashManagementDelivery.SelectResultForm')
		];
		me.callParent([cfg]);
	}
});
//==============================================================================================
//派送兑现时效新增，修改的窗口
//==============================================================================================
Ext.define('Foss.baseinfo.lclDeliveryToCashManagementDelivery.UpdatelclDeliveryToCashManagementDeliveryEntityWindow', {
	extend : 'Ext.window.Window',
	width : 590,
	cashManagementDeliveryEntity:null,
	closeAction : 'hide',
	layout : 'column',
	modal : true,
	constructor : function (config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.items = [
			Ext.create('Foss.baseinfo.lclDeliveryToCashManagementDelivery.UpdatelclDeliveryToCashManagementDeliveryForm')
		];
		me.callParent([cfg]);
	}
});
//派送兑现时效管理新增表单
Ext.define('Foss.baseinfo.lclDeliveryToCashManagementDelivery.UpdatelclDeliveryToCashManagementDeliveryForm', {
	extend : 'Ext.form.Panel',
	frame : true,
	hidden : false,
	defaultType : 'textfield',
	layout : {
		//type:'column',
		type : 'table',
		columns : 4
	},
	columnWidth : 0.99,
	defaults : {
		readOnly : false,
		margin : '5 5 5 10',
		anchor : '90%',
		//colspan:4,
		columnWidth : 1,
		labelWidth : 120
	},
	//取消按钮
	cancelButton : null,
	getCancelButton : function (me) {
		if (this.cancelButton == null) {
			this.cancelButton = Ext.create('Ext.button.Button', {
					xtype : 'button',
					text : baseinfo.lclDeliveryToCashManagement.i18n('foss.baseinfo.cancel'),
					columnWidth : .12,
					width : 55,
					hidden : false,
					handler : function () {
						var a_window = baseinfo.lclDeliveryToCashManagement.lclDeliveryToCashManagementDelivery.windowz;
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
					//cls : 'yellow_button',
					name : 'reset',
					text : baseinfo.lclDeliveryToCashManagement.i18n('foss.baseinfo.reset'),
					columnWidth : .72,
					width : 55,
					margin : '5 5 5 260',
					handler : function () {
						var a_updateForm = baseinfo.lclDeliveryToCashManagement.lclDeliveryToCashManagementDelivery.windowz.down('form');
						a_updateForm.getForm().reset();
						if (baseinfo.lclDeliveryToCashManagement.lclDeliveryToCashManagementDelivery.flagAddModify == 'modify') {
							var a_formInfo = baseinfo.lclDeliveryToCashManagement.lclDeliveryToCashManagementDelivery.rawRecord;
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
					text : baseinfo.lclDeliveryToCashManagement.i18n('foss.baseinfo.save'),
					columnWidth : .72,
					width : 55,
					margin : '5 5 5 5',
					handler : function () {
						var a_isChangeView = true;
						var r_url = "updatelclDeliveryToCashManagementDelivery.action";
						r_url = baseinfo.realPath(r_url);
						var a_url = r_url;
						var a_form = baseinfo.lclDeliveryToCashManagement.lclDeliveryToCashManagementDelivery.windowz.down('form');
						var a_model = Ext.create('Foss.baseinfo.lclDeliveryToCashManagementDelivery.lclDeliveryToCashManagementDeliveryModel', a_form.getForm().getValues());
							
					    var form =a_form.getForm();
					    var startDate=form.findField('startDate').getValue();
					    var endDate = form.findField('endDate').getValue();
					    if(Ext.isEmpty(startDate)||Ext.isEmpty(endDate)){
			            return;
		                 }
		                var startDate1=Ext.Date.format(startDate,'H:i');
		                var endDate1=Ext.Date.format(endDate,'H:i');
					    var value1=parseInt(addSplit(startDate1)); 
					    var value2=parseInt(addSplit(endDate1)); 
					    if(value2!=0){
					     if(value1>value2||value1==value2){
							Ext.MessageBox.show({
							title : 'FOSS提示',
							msg : '卸出时间段结束时间必须大于开始时间',
							width : 300,
							buttons : Ext.Msg.OK,
							icon : Ext.MessageBox.WARNING
										});

								return;
								}	
					    }
					  
						// 请求合法性验证
						if (!a_form.getForm().isValid()) {
						
			             return;
		               }
					
						var lclDeliveryToCashManagementDeliveryVo = new Object();
						lclDeliveryToCashManagementDeliveryVo.lclDeliveryToCashManagementDeliveryEntity = new Object();
						lclDeliveryToCashManagementDeliveryVo.lclDeliveryToCashManagementDeliveryEntity = a_model.data;
							var params = {
							'lclDeliveryToCashManagementDeliveryVo' : lclDeliveryToCashManagementDeliveryVo     
						};
						params.lclDeliveryToCashManagementDeliveryVo.lclDeliveryToCashManagementDeliveryEntity.startDate=a_form.getForm().findField('startDate').getValue();
						params.lclDeliveryToCashManagementDeliveryVo.lclDeliveryToCashManagementDeliveryEntity.endDate=a_form.getForm().findField('endDate').getValue();
						params.lclDeliveryToCashManagementDeliveryVo.lclDeliveryToCashManagementDeliveryEntity.deliverOnTime=a_form.getForm().findField('deliverOnTime').getRawValue();
						// 如果是新增
						if (baseinfo.lclDeliveryToCashManagement.lclDeliveryToCashManagementDelivery.flagAddModify == 'add') {
							r_url = "addlclDeliveryToCashManagementDelivery.action";
							r_url = baseinfo.realPath(r_url);
							a_url = r_url;
						}
						//发送新增或修改的Ajax请求.
						Ext.Ajax.request({
							url : a_url,
							jsonData : params,
							//作废成功
							success : function (response) {
								var json = Ext.decode(response.responseText);
								baseinfo.lclDeliveryToCashManagement.lclDeliveryToCashManagementDelivery.pagingBar.moveFirst();
								var tipMsg = baseinfo.lclDeliveryToCashManagement.i18n('foss.baseinfo.saveSuccessful');
								if (json.message) {
									tipMsg = json.message;
								}
								json.message = '操作成功';
								top.Ext.MessageBox.alert(baseinfo.lclDeliveryToCashManagement.i18n('foss.baseinfo.saveSuccessful'), json.message);
							},
							//保存失败
							exception : function (response) {
								a_isChangeView = false;
								var json = Ext.decode(response.responseText);
								top.Ext.MessageBox.alert(baseinfo.lclDeliveryToCashManagement.i18n('foss.baseinfo.saveFail'), json.message);
							}
						});
						if (a_isChangeView) {
							var a_win = baseinfo.lclDeliveryToCashManagement.lclDeliveryToCashManagementDelivery.windowz;
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
				name : 'id',
				xtype : 'hiddenfield',
				hidden : true
		   },{
				allowBlank : false,
				xtype : 'commonsaledepartmentselector',
				name : 'orgCode',
				fieldLabel : '派送部门',
				delivery:'Y',
				margin : '5 20 5 10',
				colspan:4,
				columnWidth : .50
			}/*, {
				xtype: 'rangeDateField',
				fieldLabel:'卸出时间段',
				dateType: 'timefield',
				fromName: 'startDate',
				toName: 'endDate',
				allowBlank:false,
				margin : '5 20 5 10',
				colspan:4,
				columnWidth : .50,
				width:456
			}*/, 
			   {
				fieldLabel : '卸出时间段',
		        name: 'startDate',
		        format: 'H:i',//24小时制
		        xtype : 'timefield',
		        increment: 30,
		        editable:false,
		        allowBlank:false,
				margin : '5 20 5 10',
				columnWidth : .18
			},{
				text:'至',
				xtype:'label',
				margin:'5 20 5 0',
				columnWidth:.05	
			},{
		        name: 'endDate',
		        format: 'H:i',//24小时制
		        xtype : 'timefield',
		        increment: 30,
		        editable:false,
		        allowBlank:false,
		        colspan:2,
				margin : '0 20 5 0',
				columnWidth : .008
			},
				{
				fieldLabel : '规定兑现时间时分',
		        name: 'deliverOnTime',
		        format: 'H:i',//24小时制
		        xtype : 'timefield',
		        increment: 30,
		        editable:false,
		        allowBlank:false,
				margin : '5 20 5 10',
				columnWidth : .18
			},{
				text:'+',
				xtype:'label',
				margin:'5 20 5 0',
				columnWidth:.05	
			},{
		        name: 'deliverOnDay',
				minValue : 0,
				maxValue : 9,
				value : '0',		
		        margin : '5 20 5 0',
		        xtype:'numberfield',
		        width:95,
		        allowBlank:false
			},{
				text:'天',
				xtype:'label',
				margin:'5 20 5 0',
				columnWidth:.05	
			},{
				xtype : 'textareafield',
				name : 'reMark',
				readOnly : false,
				fieldLabel : '备注',
				colspan:4,
				maxLength : 200,
				width : 480
			},
			Ext.create('Ext.container.Container', {
				width : 450,
				colspan:4,
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
/**
 * 派送兑现时效管理_页面(派送兑现时效管理)
 */
baseinfo.lclDeliveryToCashManagement.lclDeliveryToCashManagementDelivery = new Object();
baseinfo.lclDeliveryToCashManagement.lclDeliveryToCashManagementDelivery.deletelclDeliveryToCashManagementDelivery = function (isMore, p_code) {
	Ext.MessageBox.buttonText.yes = baseinfo.lclDeliveryToCashManagement.i18n('foss.baseinfo.sure');
	Ext.MessageBox.buttonText.no = baseinfo.lclDeliveryToCashManagement.i18n('foss.baseinfo.cancel');
	// 获得国际化字符串
	var tip1 = baseinfo.lclDeliveryToCashManagement.i18n('foss.baseinfo.tipInfo');
	var tip2 = baseinfo.lclDeliveryToCashManagement.i18n('foss.baseinfo.areYouSureToVoid');
	var ids = '';
	if (isMore) {
		var a_grid = Ext
				.getCmp('Foss_baseinfo_lclDeliveryToCashManagementDelivery_lclDeliveryToCashManagementDeliveryGrid_Id');
		// 获取选中的记录
		var rowObjs = a_grid.getSelectionModel().getSelection();

		if (rowObjs.length <= 0) {
			Ext.Msg.alert(baseinfo.lclDeliveryToCashManagement.i18n('foss.baseinfo.tip'),baseinfo.lclDeliveryToCashManagement.i18n('foss.baseinfo.pleaseSelectDeletedRecord'));
			return;
		}
		// 将id通过,分隔
		for (var i = 0; i < rowObjs.length; i++) {
			ids = ids + rowObjs[i].data.id + ",";
		}
		ids = ids.substring(0, ids.length - 1);
	} else {
		ids = p_code
	}
	Ext.Msg.confirm(tip1, tip2,
		function (btn, text) {
		if (btn == 'yes') {
			//发送作废结点的Ajax请求.
			var lclDeliveryToCashManagementDeliveryVo = new Object();
			lclDeliveryToCashManagementDeliveryVo.lclDeliveryToCashManagementDeliveryEntity = new Object();
			lclDeliveryToCashManagementDeliveryVo.lclDeliveryToCashManagementDeliveryEntity.id = ids;
			var params = {
				'lclDeliveryToCashManagementDeliveryVo' : lclDeliveryToCashManagementDeliveryVo
			};
			var r_url = "deletelclDeliveryToCashManagementDeliveryMore.action";
			r_url = baseinfo.realPath(r_url);
			Ext.Ajax.request({
				url : r_url,
				jsonData : params,
				//作废成功
				success : function (response) {
					var json = Ext.decode(response.responseText);
					//删除完成后，将“从表格删除”改为刷新表格
					baseinfo.lclDeliveryToCashManagement.lclDeliveryToCashManagementDelivery.pagingBar.moveFirst();
					var tipMsg = baseinfo.lclDeliveryToCashManagement.i18n('foss.baseinfo.voidSuccessNo');
					if (json.message) {
						tipMsg = json.message;
					}
					top.Ext.MessageBox.alert(baseinfo.lclDeliveryToCashManagement.i18n('foss.baseinfo.tips'), baseinfo.lclDeliveryToCashManagement.i18n('foss.baseinfo.voidSuccessNo'));
				},
				//作废失败
				exception : function (response) {
					var json = Ext.decode(response.responseText);
					top.Ext.MessageBox.alert(baseinfo.lclDeliveryToCashManagement.i18n('foss.baseinfo.deleteFail'), json.message);
				}
			});
		}
	});
}
//派送兑现时效管理查询-主面板
Ext.define('Foss.baseinfo.lclDeliveryToCashManagementDelivery.SelectForm', {
	extend : 'Ext.form.Panel',
	id : 'Foss_baseinfo_lclDeliveryToCashManagementDelivery_SelectForm_Id',
	itemId : 'Foss_baseinfo_lclDeliveryToCashManagementDelivery_SelectForm_ItemId',
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
			Ext.create('Foss.baseinfo.lclDeliveryToCashManagementDelivery.SelectConditionForm'),
			Ext.create('Foss.baseinfo.lclDeliveryToCashManagementDelivery.SelectResultForm')
		];
		me.callParent([cfg]);
	}
});
//==============================================================================================
// Delivery end
//==============================================================================================
/*
 * 规定卸出时间管理   页面
 */
    baseinfo.regCompCodeLimit = {vehicleType:/^([\u4e00-\u9fa5]|[0-9])+$/};
    baseinfo.lclDeliveryToCashManagement.lclDeliveryToCashManagementUnloading = new Object();
    baseinfo.lclDeliveryToCashManagement.lclDeliveryToCashManagementUnloading.deletelclDeliveryToCashManagementUnloading = function (isMore, p_code) {
	Ext.MessageBox.buttonText.yes = baseinfo.lclDeliveryToCashManagement.i18n('foss.baseinfo.sure');
	Ext.MessageBox.buttonText.no = baseinfo.lclDeliveryToCashManagement.i18n('foss.baseinfo.cancel');

	// 获得国际化字符串
	var tip1 = baseinfo.lclDeliveryToCashManagement.i18n('foss.baseinfo.tipInfo');
	var tip2 = baseinfo.lclDeliveryToCashManagement.i18n('foss.baseinfo.areYouSureToVoid');
	var ids = '';
	if (isMore) {
		var a_grid = Ext
				.getCmp('Foss_baseinfo_lclDeliveryToCashManagementUnloading_lclDeliveryToCashManagementUnloadingGrid_Id');
		// 获取选中的记录
		var rowObjs = a_grid.getSelectionModel().getSelection();

		if (rowObjs.length <= 0) {
			Ext.Msg.alert(baseinfo.lclDeliveryToCashManagement.i18n('foss.baseinfo.tip'),baseinfo.lclDeliveryToCashManagement.i18n('foss.baseinfo.pleaseSelectDeletedRecord'));
			return;
		}
		// 将id通过,分隔
		for (var i = 0; i < rowObjs.length; i++) {
			ids = ids + rowObjs[i].data.id + ",";
		}
		ids = ids.substring(0, ids.length - 1);
	} else {
		ids = p_code
	}
	Ext.Msg.confirm(tip1, tip2,
		function (btn, text) {
		if (btn == 'yes') {
			//发送作废结点的Ajax请求.
			var lclDeliveryToCashManagementUnloadingVo = new Object();
			lclDeliveryToCashManagementUnloadingVo.lclDeliveryToCashManagementUnloadingEntity = new Object();
			lclDeliveryToCashManagementUnloadingVo.lclDeliveryToCashManagementUnloadingEntity.id = ids;
			var params = {
				'lclDeliveryToCashManagementUnloadingVo' : lclDeliveryToCashManagementUnloadingVo
			};
			var r_url = "deletelclDeliveryToCashManagementUnloadingMore.action";
			r_url = baseinfo.realPath(r_url);
			Ext.Ajax.request({
				url : r_url,
				jsonData : params,
				//作废成功
				success : function (response) {
					var json = Ext.decode(response.responseText);
					//删除完成后，将“从表格删除”改为刷新表格
					baseinfo.lclDeliveryToCashManagement.lclDeliveryToCashManagementUnloading.pagingBar.moveFirst();

					var tipMsg = baseinfo.lclDeliveryToCashManagement.i18n('foss.baseinfo.voidSuccessNo');
					if (json.message) {
						tipMsg = json.message;
					}
					top.Ext.MessageBox.alert(baseinfo.lclDeliveryToCashManagement.i18n('foss.baseinfo.tips'), baseinfo.lclDeliveryToCashManagement.i18n('foss.baseinfo.voidSuccessNo'));
				},
				//保存失败
				exception : function (response) {
					var json = Ext.decode(response.responseText);
					top.Ext.MessageBox.alert(baseinfo.lclDeliveryToCashManagement.i18n('foss.baseinfo.deleteFailure'), json.message);
				}
			});
		}
	});
}
////////////////////////////////////////////////////////////////////
// 全局变量（模块名lclDeliveryToCashManagementUnloading） end
////////////////////////////////////////////////////////////////////

// 规定卸出时间管理查询-主面板
Ext.define('Foss.baseinfo.lclDeliveryToCashManagementUnloading.SelectForm', {
	extend : 'Ext.form.Panel',
	id : 'Foss_baseinfo_lclDeliveryToCashManagementUnloading_SelectForm_Id',
	itemId : 'Foss_baseinfo_lclDeliveryToCashManagementUnloading_SelectForm_ItemId',
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
			Ext.create('Foss.baseinfo.lclDeliveryToCashManagementUnloading.SelectConditionForm'),
			Ext.create('Foss.baseinfo.lclDeliveryToCashManagementUnloading.SelectResultForm')
		];
		me.callParent([cfg]);
	}
});

// 装卸车标准时间新增表单
Ext.define('Foss.baseinfo.lclDeliveryToCashManagementUnloading.UpdatelclDeliveryToCashManagementUnloadingForm', {
	extend : 'Ext.form.Panel',

	frame : true,
	hidden : false,
	defaultType : 'textfield',
	layout : {
		type:'column'
		//type : 'table',
		//columns : 4
	},
	//layout : 'column',
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
					text : baseinfo.lclDeliveryToCashManagement.i18n('foss.baseinfo.cancel'),
					columnWidth : .12,
					width : 55,
					hidden : false,
					handler : function () {
						var a_window = baseinfo.lclDeliveryToCashManagement.lclDeliveryToCashManagementUnloading.windowz;
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
					text : baseinfo.lclDeliveryToCashManagement.i18n('foss.baseinfo.reset'),
					columnWidth : .72,
					width : 55,
					margin : '5 5 5 400',
					handler : function () {
						var a_updateForm = baseinfo.lclDeliveryToCashManagement.lclDeliveryToCashManagementUnloading.windowz.down('form');
						a_updateForm.getForm().reset();
						if (baseinfo.lclDeliveryToCashManagement.lclDeliveryToCashManagementUnloading.flagAddModify == 'modify') {
							var a_formInfo = baseinfo.lclDeliveryToCashManagement.lclDeliveryToCashManagementUnloading.rawRecord;
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
					text : baseinfo.lclDeliveryToCashManagement.i18n('foss.baseinfo.save'),
					columnWidth : .72,
					width : 55,
					margin : '5 5 5 5',
					handler : function () {
						var a_isChangeView = true;
						var r_url = "updatelclDeliveryToCashManagementUnloading.action";
						var a_win = Ext.getCmp('Foss_baseinfo_lclDeliveryToCashManagementUnloading_addLclDeliveryToCashManagementWindow_Id'),
						r_url = baseinfo.realPath(r_url);
						a_url = r_url;
						var a_form = baseinfo.lclDeliveryToCashManagement.lclDeliveryToCashManagementUnloading.windowz.down('form');
						var a_model = Ext.create('Foss.baseinfo.lclDeliveryToCashManagementUnloading.lclDeliveryToCashManagementUnloadingModel', a_form.getForm().getValues());
						// 请求合法性验证
			      if (!a_form.getForm().isValid()) {
				      return;
				
//						top.Ext.MessageBox.show({
//							title : '失败提示',
//							msg :'填写完整信息',
//							width : 350,
//							buttons : Ext.Msg.OK,
//							icon : Ext.MessageBox.WARNING
//						});
//						a_form = null;
					
			}

						var lclDeliveryToCashManagementUnloadingVo = new Object();
						lclDeliveryToCashManagementUnloadingVo.lclDeliveryToCashManagementUnloadingEntity = new Object();
						lclDeliveryToCashManagementUnloadingVo.lclDeliveryToCashManagementUnloadingEntity = a_model.data
							var params = {
							'lclDeliveryToCashManagementUnloadingVo' : lclDeliveryToCashManagementUnloadingVo
						}; 
						params.lclDeliveryToCashManagementUnloadingVo.lclDeliveryToCashManagementUnloadingEntity.scheduleTime=a_form.getForm().findField('scheduleTime').getRawValue();
						params.lclDeliveryToCashManagementUnloadingVo.lclDeliveryToCashManagementUnloadingEntity.unloadingTimeOut=a_form.getForm().findField('unloadingTimeOut').getRawValue();
						params.lclDeliveryToCashManagementUnloadingVo.lclDeliveryToCashManagementUnloadingEntity.reachOnTime=a_form.getForm().findField('reachOnTime').getRawValue();
						params.lclDeliveryToCashManagementUnloadingVo.lclDeliveryToCashManagementUnloadingEntity.unloadingTime=a_form.getForm().findField('unloadingTime').getRawValue();
						r_url = "updatelclDeliveryToCashManagementUnloading.action";
						r_url = baseinfo.realPath(r_url);
						a_url = r_url;
						// 如果是新增
						if (baseinfo.lclDeliveryToCashManagement.lclDeliveryToCashManagementUnloading.flagAddModify == 'add') {
							var r_url = "addlclDeliveryToCashManagementUnloading.action";
							r_url = baseinfo.realPath(r_url);
							a_url = r_url;

						}

						//发送新增结点的Ajax请求.
						Ext.Ajax.request({
							url : a_url,
							jsonData : params,
							//作废成功
							success : function (response) {
								var json = Ext.decode(response.responseText);
								
								//baseinfo.lclDeliveryToCashManagement.lclDeliveryToCashManagementUnloading.pagingBar.moveFirst();
								var tipMsg = baseinfo.lclDeliveryToCashManagement.i18n('foss.baseinfo.saveSuccessful');
								if (json.message) {
									tipMsg = json.message;
								}
								json.message = '操作成功';
								
								top.Ext.MessageBox.alert(baseinfo.lclDeliveryToCashManagement.i18n('foss.baseinfo.saveSuccessful'), json.message);
								baseinfo.lclDeliveryToCashManagement.lclDeliveryToCashManagementUnloading.pagingBar.moveFirst();
								if(!Ext.isEmpty(a_win)){
									a_win.hide();
									}
							},
							//保存失败
							exception : function (response) {
								a_isChangeView = false;
								var json = Ext.decode(response.responseText);
								top.Ext.MessageBox.alert(baseinfo.lclDeliveryToCashManagement.i18n('foss.baseinfo.saveFail'), json.message);
							}
						});
//						if (a_isChangeView) {
//							var a_win = baseinfo.lclDeliveryToCashManagement.lclDeliveryToCashManagementUnloading.windowz;
//							a_win.down('form').getForm().findField('scheduleTime').setValue('');
//							a_win.down('form').getForm().findField('scheduleHours').setValue('');
//						    a_win.down('form').getForm().findField('scheduleMins').setValue('');
//							a_win.down('form').getForm().findField('unloadingTimeOut').setValue('');
//							a_win.down('form').getForm().reset();
//							a_win.setVisible(false)
//						}

					}
				});
		}
		return this.saveButton;
	},
				//计算时间
	countTime : function(){
		var a_form = baseinfo.lclDeliveryToCashManagement.lclDeliveryToCashManagementUnloading.windowz.down('form');
		var form =a_form.getForm();
		//计算 规定到达时间点
		var beginTime=form.findField('scheduleTime').getValue();//规定出发时间
		if(Ext.isEmpty(beginTime))return;
		var beginTimeHour=beginTime.getHours();
		var beginTimeMin=beginTime.getMinutes();
		var scheduleHour = form.findField('scheduleHours').getValue(); 
		var scheduleMin = form.findField('scheduleMins').getValue();
		if(Ext.isEmpty(scheduleHour)||Ext.isEmpty(scheduleMin)){
			return;
		}
		var minOffseta=parseInt(scheduleHour*60)+parseInt(scheduleMin);
		var minOffseta1=parseInt(scheduleHour*60)+parseInt(scheduleMin)+parseInt(beginTimeHour*60)+parseInt(beginTimeMin);
		var endTimea=Ext.Date.add(beginTime,Ext.Date.MINUTE,minOffseta);
		var dayOffseta=Math.floor((minOffseta1)/(1440));//间隔天数
		var arriveTa=Ext.Date.format(endTimea,'H:i');//到达时间
		form.findField('reachOnTimeDay').setValue(dayOffseta);
		form.findField('reachOnTime').setValue(arriveTa);
		//计算 规定卸出时间点
		var unloadingTimeOut=form.findField('unloadingTimeOut').getValue();
		if(Ext.isEmpty(unloadingTimeOut)){
			return;
		}
		var unloadingTimeOutHour=unloadingTimeOut.getHours();
		var unloadingTimeOutMin=unloadingTimeOut.getMinutes();
		var minOffsetb=minOffseta+parseInt(unloadingTimeOutHour*60)+parseInt(unloadingTimeOutMin);
		var minOffsetb1=minOffseta1+parseInt(unloadingTimeOutHour*60)+parseInt(unloadingTimeOutMin);
		var endTimeb=Ext.Date.add(beginTime,Ext.Date.MINUTE,minOffsetb);
		var arriveTb=Ext.Date.format(endTimeb,'H:i');//到达时间
		var dayOffsetb=Math.floor((minOffsetb1)/(1440));//间隔天数
		form.findField('unloadingTimeDay').setValue(dayOffsetb);
		form.findField('unloadingTime').setValue(arriveTb);		
	},
   
	initComponent : function (config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.items = [{
				name : 'id',
				xtype : 'hiddenfield',
				hidden : true
		   },{
					allowBlank : false,
					xtype : 'dynamicorgcombselector',
					type : 'ORG',
					name : 'startOrgCode',
					fieldLabel : '出发部门',
					//salesDepartment : 'Y',
					transferCenter : 'Y',
					margin : '5 20 5 10',
					columnWidth : .48,
					colspan : 1
					//labelWidth : 150
			}, {
					allowBlank : false,
					xtype : 'dynamicorgcombselector',
					type : 'ORG',
					name : 'reachOrgCode',
					fieldLabel : '到达部门',
					//salesDepartment : 'Y',
					transferCenter : 'Y',
					margin : '5 20 5 0',
					columnWidth : .45,
					labelWidth : 100,
					colspan : 3
				},{
					allowBlank : false,
					name : 'vehicleNumber',
					fieldLabel : '班次',
					xtype : 'numberfield',
					decimalPrecision : 0,
					maxValue : 50,
					minValue : 0,
					margin : '5 20 5 10',
					colspan : 1,
					columnWidth : .4
					},{
						allowBlank : true,
						name : 'vehicleType',
						fieldLabel : '车型',
						xtype : 'textfield',
						margin : '5 20 5 0',
					   regex :baseinfo.regCompCodeLimit.vehicleType,
					   regexText : "只能输入数字及汉字，数字、汉字可组合",
						width:50,
						labelWidth : 50,
						colspan : 1,
						columnWidth : .25
					},{
						allowBlank : true,
						name : 'selfVolume',
						fieldLabel : '规定净空',
						//xtype : 'textfield',
						xtype : 'numberfield',
						decimalPrecision : 0,
						margin : '5 20 5 0',
						labelWidth : 60,
						width:150,
						columnWidth : .28
					},{
						fieldLabel : '规定出发时间点',
				        name: 'scheduleTime',
				        format:'H:i',//24小时制
				        xtype : 'timefield',
				        increment: 5,
				        editable:false,
				        allowBlank:false,
						margin : '5 20 5 10',
						colspan : 4,
						columnWidth : .51,
						//labelWidth : 160
								listeners:{
					change:function(me,newValue,oldValue,eOpts){
						 me.up().countTime();
					}}
					}, {
						allowBlank : false,
						name : 'scheduleHours',
						fieldLabel : '规定运行时间',
						xtype : 'numberfield',
						decimalPrecision : 0,
						maxValue : 72,
						minValue : 0,
						margin : '5 20 5 10',
						value : '0',
						columnWidth : .5,
						//labelWidth : 160
						listeners:{
					change:function(me,newValue,oldValue,eOpts){
						 me.up().countTime();
					}}
					}, {
						xtype : 'label',
						text : '时',
						margin : '10 20 5 0',
						columnWidth : .05
					}, {
						allowBlank : false,
						hideLabel : true,
						name : 'scheduleMins',
						fieldLabel : '',
						xtype : 'numberfield',
						decimalPrecision : 0,
						margin : '5 20 5 0',
						value : '0',
						maxValue : 55,
						minValue : 0,
						step:5,
						//labelWidth : 1,
						columnWidth : .25,
					listeners:{
					change:function(me,newValue,oldValue,eOpts){
//					var form =me.up().getForm();
						me.up().countTime();
					}}
					}, {
						xtype : 'label',
						text : '分',
						margin : '10 20 5 0',
						columnWidth : .05
					},{
						allowBlank : false,
						name : 'reachOnTime',
						fieldLabel : '规定到达时间点',
						format:'H:i',//24小时制
				        xtype : 'timefield',
						decimalPrecision : 0,
						readOnly : true,
						margin : '5 20 5 10',
						columnWidth : .5
					}, {
						xtype : 'label',
						text : '+',
						margin : '10 20 5 0',
						columnWidth : .05
					}, {
						allowBlank : false,
						hideLabel : true,
						name : 'reachOnTimeDay',
						fieldLabel : '',
						xtype : 'numberfield',
						readOnly : true,
						decimalPrecision : 0,
						margin : '5 20 5 0',
						//value : '0',
						maxValue : 59,
						minValue : 0,
						columnWidth : .25
					}, {
						xtype : 'label',
						text : '天',
						margin : '10 20 5 0',
						columnWidth : .05
					},{
						fieldLabel : '规定卸车时长',
				        name: 'unloadingTimeOut',
				        format:'H:i',//24小时制
				        xtype : 'timefield',
				        increment: 30,
				       // editable:false,
				        allowBlank:false,
				        colspan : 4,
						margin : '5 20 5 10',
						columnWidth : .51,
						//labelWidth : 160
						listeners:{
					change:function(me,newValue,oldValue,eOpts){
					  me.up().countTime();
					}}
						
					},{
						allowBlank : false,
						name : 'unloadingTime',
						fieldLabel : '规定卸出时间点',
						format:'H:i',//24小时制
				        xtype : 'timefield',
				        readOnly : true,
						margin : '5 20 5 10',
						columnWidth : .5
					}, {
						xtype : 'label',
						text : '+',
						margin : '5 20 5 0',
						columnWidth : .05
					}, {
						allowBlank : false,
						hideLabel : true,
						name : 'unloadingTimeDay',
						fieldLabel : '',
						xtype : 'numberfield',
						readOnly : true,
						decimalPrecision : 0,
						margin : '5 20 5 0',
						columnWidth : .25
					}, {
						xtype : 'label',
						text : '天',
						margin : '10 20 5 0',
						columnWidth : .05
					}, {
						xtype : 'textareafield',
						name : 'reMark',
						readOnly : false,
						fieldLabel : '备注',
						colspan : 3,
						labelWidth : 80,
						maxLength : 200,
						width : 300

					},
			  Ext.create('Ext.container.Container', {
				width : 650,
				colspan : 4,
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
 * 规定卸出时间管理 界面数据模型定义
 */
Ext.define('Foss.baseinfo.lclDeliveryToCashManagementUnloading.lclDeliveryToCashManagementUnloadingModel', {
	extend : 'Ext.data.Model',
	fields : [
		// ID
		{
			name : 'id',
			type : 'string'
		},
		// 出发部门编码
		{
			name : 'startOrgCode',
			type : 'string'
		},
		// 出发部门名称
		{
			name : 'startOrgName',
			type : 'string'
		},
		// 到达部门编码
		{
			name : 'reachOrgCode',
			type : 'string'
		},
		// 到达部门名称
		{
			name : 'reachOrgName',
			type : 'string'
		},
		// 班次
		{
			name : 'vehicleNumber',
			type : 'string'
		},
		// 车型
		{
			name : 'vehicleType',
			type : 'string'
		},
		// 规定净空
		{
			name : 'selfVolume',
			type : 'string'
		},
		//规定出发时间点
		{
			name : 'scheduleTime',
			type : 'string'
		},
		// 规定运行时间用时
		{
			name : 'scheduleHours',
			type : 'string'
		},
		// 规定运行时间用时用分
		{
			name : 'scheduleMins',
			type : 'string'
		},
		// 规定到达时间点
		{
			name : 'reachOnTime',
			type : 'string'
		},
		// 规定到达时间点(天)
		{
			name : 'reachOnTimeDay',
			type : 'string'
		},
		// 规定卸车时长
		{
			name : 'unloadingTimeOut',
			type : 'string'
		},
		// 规定卸出时间点
		{
			name : 'unloadingTime',
			type : 'string'
		},
		// 规定卸出时间点（天）
		{
			name : 'unloadingTimeDay',
			type : 'string'
		},
		// 备注
		{
			name : 'reMark',
			type : 'string'
		},
		// 创建时间
		{
			name : 'createDate',
			type : 'date',
			convert: dateConvert
		},
		// 更新时间
		{
			name : 'modifyDate',
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
		},// 创建人项姓名
		{
			name : 'createUser',
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
Ext.define('Foss.baseinfo.lclDeliveryToCashManagementUnloading.SelectConditionForm', {
	extend : 'Ext.form.Panel',
	id : 'Foss_baseinfo_lclDeliveryToCashManagementUnloading_SelectConditionForm_Id',
	itemId : 'Foss_baseinfo_lclDeliveryToCashManagementUnloading_SelectConditionForm_ItemId',
	layout : 'column',
	frame : true,
	columnWidth : 0.98,
	title : baseinfo.lclDeliveryToCashManagement.i18n('foss.baseinfo.queryCondition'),
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
				columns: 1,
				defaultType : 'textfield',
				items : [{
						xtype : 'dynamicorgcombselector',
						type : 'ORG',
						name : 'startOrgCode',
						fieldLabel : '出发部门',
						//salesDepartment : 'Y',
						transferCenter : 'Y',
						margin : '5 20 5 10',
						columnWidth : .38,
						labelWidth : 120
					}, {
						xtype : 'dynamicorgcombselector',
						type : 'ORG',
						name : 'reachOrgCode',
						fieldLabel :'到达部门',
						//salesDepartment : 'Y',
						transferCenter : 'Y',
						margin : '5 20 5 10',
						columnWidth : .38,
						labelWidth : 120
					},Ext.create('Ext.container.Container', {
						columnWidth : 1,
						layout : 'column',
						items : [{
								columnWidth : 0.00075,
								height : 0,
								xtype : 'container',
								style : 'padding:30px 0 0 650px;border:none',
								hide : true
							}, Ext.create('Ext.button.Button', {
								xtype : 'button',
								cls : 'yellow_button',
								name : 'buttonReset',
								text : baseinfo.lclDeliveryToCashManagement.i18n('foss.baseinfo.reset'),
								disabled:!baseinfo.lclDeliveryToCashManagement.isPermission('lclDeliveryToCashManagementindex/lclDeliveryToCashManagementUnloadingQueryButton'),
								hidden:!baseinfo.lclDeliveryToCashManagement.isPermission('lclDeliveryToCashManagementindex/lclDeliveryToCashManagementUnloadingQueryButton'),
								columnWidth : .12,
								margin : '0,10,0,0',
								width : 20,
								handler : function () {
									var a_conditionForm = Ext.getCmp("Foss_baseinfo_lclDeliveryToCashManagementUnloading_SelectConditionForm_Id");
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
								text : baseinfo.lclDeliveryToCashManagement.i18n('foss.baseinfo.query'),
								disabled:!baseinfo.lclDeliveryToCashManagement.isPermission('lclDeliveryToCashManagementindex/lclDeliveryToCashManagementUnloadingQueryButton'),
								hidden:!baseinfo.lclDeliveryToCashManagement.isPermission('lclDeliveryToCashManagementindex/lclDeliveryToCashManagementUnloadingQueryButton'),
								columnWidth : .12,
								margin : '0,10,0,30',
								width : 20,
								//查询按钮的响应事件
								handler : function () {
								
									baseinfo.lclDeliveryToCashManagement.lclDeliveryToCashManagementUnloading.pagingBar.moveFirst();
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
Ext.define('Foss.baseinfo.lclDeliveryToCashManagementUnloading.lclDeliveryToCashManagementUnloadingGrid', {
	extend : 'Ext.grid.Panel',
	id : 'Foss_baseinfo_lclDeliveryToCashManagementUnloading_lclDeliveryToCashManagementUnloadingGrid_Id',
	cls : 'autoHeight',
	bodyCls : 'autoHeight',
	columnWidth : 1,
	//增加滚动条
	autoScroll : true,
		// 设置表格不可排序
	sortableColumns : false,
	// 去掉排序的倒三角
	enableColumnHide : false,
	stripeRows : true, // 交替行效果
	collapsible : true,
	animCollapse : true,
	selType : "rowmodel", // 选择类型设置为行选择
	store : null,
	selModel : Ext.create('Ext.selection.CheckboxModel'),

	columns : [{
			xtype : 'actioncolumn',
			//flex : 1,
			width : 70,
			text : baseinfo.lclDeliveryToCashManagement.i18n('foss.baseinfo.operate'),
			align : 'center',
			items : [{
					iconCls : 'deppon_icons_edit',
					tooltip : baseinfo.lclDeliveryToCashManagement.i18n('foss.baseinfo.update'),
					disabled:!baseinfo.lclDeliveryToCashManagement.isPermission('lclDeliveryToCashManagementindex/lclDeliveryToCashManagementUnloadingUpdateButton'),
					handler : function (grid, rowIndex, colIndex) {
						baseinfo.lclDeliveryToCashManagement.lclDeliveryToCashManagementUnloading.flagAddModify = 'modify';
						var a_updateForm = baseinfo.lclDeliveryToCashManagement.lclDeliveryToCashManagementUnloading.windowz.down('form');
						var rowInfo = grid.getStore().getAt(rowIndex);
						a_updateForm.getForm().findField('startOrgCode').setReadOnly(true);
						a_updateForm.getForm().findField('startOrgCode').store.add({
							'code' : rowInfo.data.startOrgCode,
							'name' : rowInfo.data.startOrgName
						});
						a_updateForm.getForm().findField('reachOrgCode').setReadOnly(true);
						a_updateForm.getForm().findField('reachOrgCode').store.add({
							'code' : rowInfo.data.reachOrgCode,
							'name' : rowInfo.data.reachOrgName
						});
						a_updateForm.loadRecord(rowInfo);
						//将表格里面被选中的信息先保存起来，重置的时候使用
						baseinfo.lclDeliveryToCashManagement.lclDeliveryToCashManagementUnloading.rawRecord = rowInfo;
						var a_win = baseinfo.lclDeliveryToCashManagement.lclDeliveryToCashManagementUnloading.windowz;
						a_win.show();
						a_win.setTitle('修改卸出时间管理');
					}
				}, {
					iconCls : 'deppon_icons_cancel',
					tooltip : baseinfo.lclDeliveryToCashManagement.i18n('foss.baseinfo.void'),
					disabled:!baseinfo.lclDeliveryToCashManagement.isPermission('lclDeliveryToCashManagementindex/lclDeliveryToCashManagementUnloadingVoidButton'),
					handler : function (grid, rowIndex, colIndex) {
						Ext.MessageBox.buttonText.yes = baseinfo.lclDeliveryToCashManagement.i18n('foss.baseinfo.sure');
						Ext.MessageBox.buttonText.no = baseinfo.lclDeliveryToCashManagement.i18n('foss.baseinfo.cancel');
						Ext.Msg.confirm(baseinfo.lclDeliveryToCashManagement.i18n('foss.baseinfo.tipInfo'), baseinfo.lclDeliveryToCashManagement.i18n('foss.baseinfo.sureDelete'), function (btn, text) {
							if (btn == 'yes') {
								var msgTip = baseinfo.lclDeliveryToCashManagement.i18n('foss.baseinfo.deleteSuccess');

								// 获得当前选择的数据
								var rowInfo = grid.getStore().getAt(rowIndex).data;
								//发送作废结点的Ajax请求.
								var lclDeliveryToCashManagementUnloadingVo = new Object();
								lclDeliveryToCashManagementUnloadingVo.lclDeliveryToCashManagementUnloadingEntity = new Object();
								lclDeliveryToCashManagementUnloadingVo.lclDeliveryToCashManagementUnloadingEntity.id = rowInfo.id;
								var params = {
									'lclDeliveryToCashManagementUnloadingVo' : lclDeliveryToCashManagementUnloadingVo
								};
								var r_url = "deletelclDeliveryToCashManagementUnloading.action";
								r_url = baseinfo.realPath(r_url);
								Ext.Ajax.request({
									url : r_url,
									jsonData : params,
									success : function (response) {
										var json = Ext.decode(response.responseText);
										grid.getStore().removeAt(rowIndex);
										baseinfo.lclDeliveryToCashManagement.lclDeliveryToCashManagementUnloading.pagingBar.moveFirst();
									},
									exception : function (response) {
										var json = Ext.decode(response.responseText);

										msgTip = baseinfo.lclDeliveryToCashManagement.i18n('foss.baseinfo.deleteFailure');
									}
								});
								Ext.Msg.alert(baseinfo.lclDeliveryToCashManagement.i18n('foss.baseinfo.tips'), msgTip);
							}
						});

					}
				}
			]
		}, {
			hidden : true,
			dataIndex : 'startOrgCode',
			text : baseinfo.lclDeliveryToCashManagement.i18n('foss.baseinfo.deptCode'),
			margin : '5 20 5 10'
		}, {
			dataIndex : 'startOrgName',
			text : '出发部门',
			xtype : 'ellipsiscolumn',
			align : 'left',
			width : 160
//			flex : 3
		},{
			hidden : true,
			dataIndex : 'reachOrgCode',
			text : baseinfo.lclDeliveryToCashManagement.i18n('foss.baseinfo.deptCode'),
			margin : '5 20 5 10'
		}, {
			dataIndex : 'reachOrgName',
			text : '到达部门',
			xtype : 'ellipsiscolumn',
			align : 'left',
			width : 160
//			flex : 3
		},
		{
			dataIndex : 'vehicleNumber',
			text : '班次',
			xtype : 'ellipsiscolumn',
			align : 'center',
			width : 51
//			flex : 2
		}, 
		{
			dataIndex : 'vehicleType',
			text : '车型',
			xtype : 'ellipsiscolumn',
			align : 'center',
			width : 80
//			flex : 2
		},
		{
			dataIndex : 'selfVolume',
			text : '规定净空',
			xtype : 'ellipsiscolumn',
			align : 'center',
			width : 80
//			flex : 3
		},{
			dataIndex : 'scheduleTime',
			text : '规定出发时间点',
			xtype : 'ellipsiscolumn',
			align : 'center',
			width : 100
//			flex : 4
		},
		{
			dataIndex : 'scheduleHours',
			text : '规定运行时间',
			xtype : 'ellipsiscolumn',
			align : 'center',
			width : 100,
//			flex : 4,
			renderer : function (value, cellmeta, record, rowIndex, columnIndex, store) {
				var aimStr = value;
				if (record && record.raw) {
					aimStr = '';
					if (record.raw.scheduleHours) {
						aimStr += record.raw.scheduleHours + '时';
					}
					if (record.raw.scheduleMins) {
						aimStr += record.raw.scheduleMins + '分';
					}
				}
				return aimStr;
			}
		}, {
			dataIndex : 'reachOnTime',
			text : '规定到达时间点',
			xtype : 'ellipsiscolumn',
			align : 'center',
			width : 100,
//			flex : 4,
			renderer : function (value, cellmeta, record, rowIndex, columnIndex, store) {
				var aimStr = value;
				if (record && record.raw) {
					aimStr = '';
					if (record.raw.reachOnTime) {
						aimStr += record.raw.reachOnTime + '+';
					}
					if (record.raw.reachOnTimeDay) {
						aimStr += record.raw.reachOnTimeDay + '天';
					}
				}
				return aimStr;
			}
		}, {
			dataIndex : 'unloadingTimeOut',
			text : '规定卸车时长',
			xtype : 'ellipsiscolumn',
			align : 'center',
			width : 100
//			flex : 3
		},
		{
			dataIndex : 'unloadingTime',
			text : '规定卸出时间点',
			xtype : 'ellipsiscolumn',
			align : 'center',
			width : 100,
//			flex : 4,
			renderer : function (value, cellmeta, record, rowIndex, columnIndex, store) {
				var aimStr = value;
				if (record && record.raw) {
					aimStr = '';
					if (record.raw.unloadingTime) {
						aimStr += record.raw.unloadingTime + '+';
					}
					if (record.raw.unloadingTimeDay) {
						aimStr += record.raw.unloadingTimeDay + '天';
					}
				}
				return aimStr;
			}
		},{
			dataIndex : 'reMark',
			text : '备注',
			xtype : 'ellipsiscolumn',
			align : 'center'
//			flex : 2
		},{
			dataIndex : 'createUser',
			text : '操作人',
			xtype : 'ellipsiscolumn',
			align : 'center'
//			flex : 3
		},{
			dataIndex : 'createUserCode',
			text : '工号',
			xtype : 'ellipsiscolumn',
			align : 'center'
//			flex : 2
		},{
			dataIndex : 'createDate', 
			text : '创建时间',
			xtype : 'datecolumn',
			align : 'left',
//			flex : 3,
			width : 150,
			format:'Y-m-d H:i:s'
		}
	],

	getTbar : function () {
		var me = this;
		return [{
				text : baseinfo.lclDeliveryToCashManagement.i18n('foss.baseinfo.add'), //新增
				disabled:!baseinfo.lclDeliveryToCashManagement.isPermission('lclDeliveryToCashManagementindex/lclDeliveryToCashManagementUnloadingAddButton'),
				hidden:!baseinfo.lclDeliveryToCashManagement.isPermission('lclDeliveryToCashManagementindex/lclDeliveryToCashManagementUnloadingAddButton'),
				handler : function () {
					baseinfo.lclDeliveryToCashManagement.lclDeliveryToCashManagementUnloading.flagAddModify = 'add'
					var a_win = baseinfo.lclDeliveryToCashManagement.lclDeliveryToCashManagementUnloading.windowz;
					a_win.setTitle('新增规定卸出时间');
					a_win.setVisible(true);
					var a_form = a_win.down('form');
					// 设置部门编码可以修改
					a_form.getForm().findField('startOrgCode').setReadOnly(false);
					a_form.getForm().findField('reachOrgCode').setReadOnly(false);
					a_form.getForm().reset();
				}
			}, '-', {
				text : baseinfo.lclDeliveryToCashManagement.i18n('foss.baseinfo.void'), //作废
				disabled:!baseinfo.lclDeliveryToCashManagement.isPermission('lclDeliveryToCashManagementindex/lclDeliveryToCashManagementUnloadingVoidButton'),
				hidden:!baseinfo.lclDeliveryToCashManagement.isPermission('lclDeliveryToCashManagementindex/lclDeliveryToCashManagementUnloadingVoidButton'),
				handler : function () {
					baseinfo.lclDeliveryToCashManagement.lclDeliveryToCashManagementUnloading.deletelclDeliveryToCashManagementUnloading(true, null);
				}
			}
		];
	},

	constructor : function (config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.store = Ext.create('Ext.data.Store', {
				model : 'Foss.baseinfo.lclDeliveryToCashManagementUnloading.lclDeliveryToCashManagementUnloadingModel',
				pageSize : 25,
				autoLoad : false,
				proxy : {
					type : 'ajax',
					actionMethods : 'POST',
					url : baseinfo.realPath("querylclDeliveryToCashManagementUnloadingExactByEntity.action"),
					//定义一个读取器
					reader : {
						type : 'json',
						root : 'lclDeliveryToCashManagementUnloadingVo.lclDeliveryToCashManagementUnloadingList',
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
						var queryForm = baseinfo.lclDeliveryToCashManagement.lclDeliveryToCashManagementUnloading.queryForm;
						if (queryForm != null) {
							var queryParams = queryForm.getValues();
							Ext.apply(operation, {
								params : {
									'lclDeliveryToCashManagementUnloadingVo.lclDeliveryToCashManagementUnloadingEntity.startOrgCode' : queryParams.startOrgCode,
									'lclDeliveryToCashManagementUnloadingVo.lclDeliveryToCashManagementUnloadingEntity.reachOrgCode' : queryParams.reachOrgCode
								}
							});
						}
					}
				}
			});
		me.bbar = Ext.create('Deppon.StandardPaging', {
				store : me.store,
				plugins: 'pagesizeplugin',
				pageSize:25,
				prependButtons: true,
				defaults : {
					margin : '0 0 15 3'
				}
			});
		//添加头部按钮
		me.tbar = me.getTbar();
		baseinfo.lclDeliveryToCashManagement.lclDeliveryToCashManagementUnloading.pagingBar = me.bbar;
		me.callParent([cfg]);
	}
});

//右边模块-查询结果面板
Ext.define('Foss.baseinfo.lclDeliveryToCashManagementUnloading.SelectResultForm', {
	extend : 'Ext.form.Panel',
	id : 'Foss_baseinfo_lclDeliveryToCashManagementUnloading_SelectResultForm_Id',
	itemId : 'Foss_baseinfo_lclDeliveryToCashManagementUnloading_SelectResultForm_ItemId',
	layout : 'column',
	frame : true,
	hidden : false,
	autoScroll : true,
	columnWidth : 0.98,
	cls : 'autoHeight',
	bodyCls : 'autoHeight',
	title : '规定卸出时间列表',
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
					Ext.create('Foss.baseinfo.lclDeliveryToCashManagementUnloading.lclDeliveryToCashManagementUnloadingGrid')
				]
			})
		];
		me.callParent([cfg]);
	}
});

Ext.define('Foss.baseinfo.lclDeliveryToCashManagementUnloading.MainPanel', {
	extend : 'Ext.form.Panel',
	id : 'Foss_baseinfo_lclDeliveryToCashManagementUnloading_MainPanel_Id',
	itemId : 'Foss_baseinfo_lclDeliveryToCashManagementUnloading_MainPanel_ItemId',
	title : '规定卸出时间管理',
	cls : 'autoHeight',
	bodyCls : 'autoHeight',
	defaultType : 'textfield',
	layout : 'column',
//	frame : true,
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
			Ext.create('Foss.baseinfo.lclDeliveryToCashManagementUnloading.SelectConditionForm'),
			Ext.create('Foss.baseinfo.lclDeliveryToCashManagementUnloading.SelectResultForm')
		];
		me.callParent([cfg]);
	}
});

//==============================================================================================
// 规定卸出时间管理新增，修改的窗口
//==============================================================================================
Ext.define('Foss.baseinfo.lclDeliveryToCashManagementUnloading.UpdatelclDeliveryToCashManagementUnloadingEntityWindow', {
	extend : 'Ext.window.Window',
	id : 'Foss_baseinfo_lclDeliveryToCashManagementUnloading_addLclDeliveryToCashManagementWindow_Id',
	width : 680,
	closeAction : 'hide',
	//closeAction : 'close',
	layout : 'column',
	modal : true,
	addUpdateForm:null,
	getAddUpdateForm : function(){
		if(Ext.isEmpty(this.addUpdateForm)){
			this.addUpdateForm=Ext.create('Foss.baseinfo.lclDeliveryToCashManagementUnloading.UpdatelclDeliveryToCashManagementUnloadingForm');
		}
		return this.addUpdateForm;
	},
	listeners:{
		beforehide:function(me){
			me.getAddUpdateForm().getForm().findField('scheduleTime').setValue('');
			me.getAddUpdateForm().getForm().findField('scheduleHours').setValue('');
			me.getAddUpdateForm().getForm().findField('scheduleMins').setValue('');
			me.getAddUpdateForm().getForm().findField('unloadingTimeOut').setValue('');
			me.getAddUpdateForm().getForm().reset();
		}
	},
	constructor : function (config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.items = [me.getAddUpdateForm()];
		me.callParent([cfg]);
	}
//	constructor : function (config) {
//		var me = this,
//		cfg = Ext.apply({}, config);
//		me.items = [
//			Ext.create('Foss.baseinfo.lclDeliveryToCashManagementUnloading.UpdatelclDeliveryToCashManagementUnloadingForm')
//		];
//		me.callParent([cfg]);
//	}
})
/**
 * 程序入口方法
 */
Ext.onReady(function () {
	Ext.QuickTips.init();
	baseinfo.lclDeliveryToCashManagement.lclDeliveryToCashManagementDelivery.windowz = Ext.create('Foss.baseinfo.lclDeliveryToCashManagementDelivery.UpdatelclDeliveryToCashManagementDeliveryEntityWindow');
	baseinfo.lclDeliveryToCashManagement.lclDeliveryToCashManagementUnloading.windowz = Ext.create('Foss.baseinfo.lclDeliveryToCashManagementUnloading.UpdatelclDeliveryToCashManagementUnloadingEntityWindow');
	if (Ext.getCmp('T_baseinfo-lclDeliveryToCashManagementindex_content')) {
		return;
	}
	Ext.getCmp('T_baseinfo-lclDeliveryToCashManagementindex').add(Ext.create('Ext.tab.Panel', {
		flex : 1,
		cls : 'innerTabPanel',
		frame : true,
		bodyStyle : {
			height : '100%',
			margin : '0 10 0 0',
			width : 100
		},
		items : [
            Ext.create('Foss.baseinfo.lclDeliveryToCashManagementDelivery.MainPanel'),
            Ext.create('Foss.baseinfo.lclDeliveryToCashManagementUnloading.MainPanel')
		
		]
	}));
	baseinfo.lclDeliveryToCashManagement.lclDeliveryToCashManagementDelivery.queryForm = Ext.getCmp('Foss_baseinfo_lclDeliveryToCashManagementDelivery_SelectConditionForm_Id');
	baseinfo.lclDeliveryToCashManagement.lclDeliveryToCashManagementUnloading.queryForm = Ext.getCmp('Foss_baseinfo_lclDeliveryToCashManagementUnloading_SelectConditionForm_Id');
});