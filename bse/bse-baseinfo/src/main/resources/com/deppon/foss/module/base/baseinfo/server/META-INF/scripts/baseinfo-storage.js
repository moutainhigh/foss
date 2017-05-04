// 转换long类型为日期
baseinfo.changeLongToDate = function(value) {
	if (value != null) {
		var date = new Date(value);
		return date;
	} else {
		return null;
	}
};
// Ajax请求--json
baseinfo.requestJsonAjax = function(url, params, successFn, failFn) {
	Ext.Ajax.request({
				url : url,
				jsonData : params,
				success : function(response) {
					var result = Ext.decode(response.responseText);
					if (result.success) {
						successFn(result);
					} else {
						failFn(result);
					}
				},
				failure : function(response) {
					var result = Ext.decode(response.responseText);
					failFn(result);
				},
				exception : function(response) {
					var result = Ext.decode(response.responseText);
					failFn(result);
				}
			});
};
// --------------------------------------baseinfo----------------------------------------
// 库位MODEL
Ext.define('Foss.baseinfo.storage.StorageEntity', {
			extend : 'Ext.data.Model',
			fields : [{
						name : 'id',
						type : 'string'
					}, {
						name : 'modifyDate',
						type : 'date',
						defaultValue : null,
						convert : baseinfo.changeLongToDate
					}, {
						name : 'organizationCode',// 组织
						type : 'String'
					}, {
						name : 'organizationName',// 组织名称
						type : 'String'
					}, {
						name : 'transferCode',// 外场编码
						type : 'String'
					}, {
						name : 'storageCode',// 库位编码
						type : 'String'
					}, {
						name : 'virtualCode',// 虚拟编码
						type : 'String'
					}, {
						name : 'active',// 是否有效
						type : 'String'
					}, {
						name : 'notes',// 备注
						type : 'String'
					}, {
						name : 'goodsAreaVirtualCode',// 库区虚拟编码
						type : 'String'
					}, {
						name : 'goodsAreaName',// 库区名称
						type : 'String'
					}, {
						name : 'goodsAreaCode',// 库区编码
						type : 'String'
					}, {
						name : 'distanceList'// // 到各个月台的距离列表
					}, {
						name : 'transferCode',// 外场编码
						type : 'String'
					}, {
						type : 'String',
						name : 'storageLength'// 库位长度				
					}, {
						type : 'String',
						name : 'storageWidth'// 库位宽度
					}, {
						type : 'String',
						name : 'storageAbscissa'// 库位横坐标
					}, {
						type : 'String',
						name : 'storageOrdinate'// 库位 纵坐标
					}]
		});
// 库位到月台的距离实体Model
Ext.define('Foss.baseinfo.storage.DistanceEntity', {
			extend : 'Ext.data.Model',
			fields : [{
						name : 'id',
						type : 'string'
					}, {
						name : 'storageVirtualCode',// // 库位虚拟代码
						type : 'string'
					}, {
						name : 'platformVirtualCode',// 月台虚拟代码
						type : 'string'
					}, {
						name : 'distance'// 库位到月台的距离

					}, {
						name : 'active', // 是否有效
						type : 'string'
					}]
		});

// ------------------------------------model---------------------------------------------------
/**
 * 库位Store（Foss.baseinfo.storage.StorageEntity）
 */
Ext.define('Foss.baseinfo.storage.StorageStore', {
			extend : 'Ext.data.Store',
			model : 'Foss.baseinfo.storage.StorageEntity',// 库位的MODEL
			pageSize : 20,
			proxy : {
				type : 'ajax',
				actionMethods : 'post',
				url : baseinfo.realPath('queryStorageListByCondition.action'),// 请求地址
				reader : {
					type : 'json',
					root : 'storageVo.storageEntityList',// 获取的数据
					totalProperty : 'totalCount'// 总个数
				}
			}
		});

// ----------------------------------------store---------------------------------
/**
 * .
 * <p>
 * 公共方法，通过storeId和model创建STORE<br/>
 * <p>
 * 
 * @param storeId
 * @param model
 *            store所用到的model名
 * @param fields
 *            store所用到的fields
 * @returns store 返回创建的store
 * @author 张斌
 * @时间 2012-8-31
 */
baseinfo.getStore = function(storeId, model, fields, data) {
	var store = null;
	if (!Ext.isEmpty(storeId)) {
		store = Ext.data.StoreManager.lookup(storeId);
	}
	if (Ext.isEmpty(data)) {
		data = [];
	}
	if (!Ext.isEmpty(model)) {
		if (Ext.isEmpty(store)) {
			store = Ext.create('Ext.data.Store', {
						storeId : storeId,
						model : model,
						data : data
					});
		}
	}
	if (!Ext.isEmpty(fields)) {
		if (Ext.isEmpty(store)) {
			store = Ext.create('Ext.data.Store', {
						storeId : storeId,
						fields : fields,
						data : data
					});
		}
	}
	return store;
};

/**
 * 查询库位表单
 */
Ext.define('Foss.baseinfo.storage.QueryStorageForm', {
	extend : 'Ext.form.Panel',
	title : baseinfo.storage.i18n('foss.baseinfo.locationInformationInquiry'),// 库位信息查询
	frame : true,
	collapsible : true,
	layout : {
		type : 'table',
		columns : 2
	},
	defaults : {
		margin : '8 10 5 10',
		anchor : '100%'
	},
	height : 150,
	defaultType : 'textfield',
	layout : 'column',
	items : [{
		xtype : 'commontransfercenterselector',
		userCode : Ext.Array.contains(FossUserContext.getCurrentUser().roleids,
				'FOSS_SYSTEM_ADMIN') ? null : FossUserContext
				.getCurrentUserEmp().empCode,
		currentOrgCode : Ext.Array.contains(
				FossUserContext.getCurrentUser().roleids, 'FOSS_SYSTEM_ADMIN')
				? null
				: FossUserContext.getCurrentDeptCode(),
		name : 'organizationCode',// 外场名称
		organizationCode : null,// 组织编码
		fieldLabel : baseinfo.storage.i18n('foss.baseinfo.outfield'),
		listeners : {
			select : function(comb, records, empo) {
				comb.organizationCode = records[0].get('orgCode');
			},
		change:function(text, records, eops){
							if(Ext.isEmpty(text.getValue())){
							 text.up('form').getForm().findField('organizationCode')
									.setValue(null);
							text.up('form').getForm().findField('goodsAreaCode')
									.setValue(null);
							text.up('form').getForm().findField('goodsAreaVirtualCode')
									.setValue(null);
							}
			}
		}
	}, {
		name : 'storageCode',
		fieldLabel : baseinfo.storage.i18n('foss.baseinfo.locationNumber'),// 站点组名称
		xtype : 'textfield'
	},{
					xtype : 'commongoodsareaselectorBycode',//'commongoodsareaselector',// 所属库区名称
					fieldLabel : baseinfo.storage
							.i18n('foss.baseinfo.storage.goodsAreaCode'),//所属库区编号
					name :'goodsAreaCode', //'goodsAreaName',
					forceSelection : true,
					listeners : {
						expand : function(field, eOpts) {
						var organizationCode = field.up('form').getForm().findField('organizationCode').organizationCode;							
						field.getStore().addListener('beforeload', function(store, operation, eOpts) {
							var searchParams = operation.params;
							if (Ext.isEmpty(searchParams)) {
								searchParams = {};
								Ext.apply(operation, {
											params : searchParams
										});
							}
							searchParams['goodsAreaVo.goodsAreaEntity.organizationCode'] = organizationCode;
						})
						field.getStore().load({
									params : {
										'goodsAreaVo.goodsAreaEntity.organizationCode': organizationCode
									},
									callback : function(records, operation,
											success) {
										// console.log(records);

									}
								});
						},
						select : function(text, records, eops) {
							 text.up('form').getForm().findField('goodsAreaVirtualCode')
									.setValue(records[0].get('virtualCode'));
						},
						change:function(text, records, eops){
							if(Ext.isEmpty(text.getValue())){
							 text.up('form').getForm().findField('goodsAreaVirtualCode')
									.setValue(null);
							}
						}
					}
				
	},{
		name : 'goodsAreaVirtualCode',//  所属库区虚拟编号
		readOnly : true,
		hidden : true,
		xtype : 'textfield'
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.fbar = [{
			xtype : 'button',
			width : 70,
			text : baseinfo.storage.i18n('foss.baseinfo.reset'),// 重置
			disabled : !baseinfo.storage
			.isPermission('storage/storageQueryButton'),
			hidden : !baseinfo.storage
					.isPermission('storage/storageQueryButton'),
			margin : '0 800 0 0',
			handler : function() {
				me.getForm().reset();
				me.getForm().findField('organizationCode').organizationCode = null;
				me.getForm().findField('organizationCode').goodsAreaVirtualCode = null;
				me.getForm().findField('goodsAreaCode').setValue(null);
			}
		}, {
			xtype : 'button',
			width : 70,
			cls : 'yellow_button',
			text : baseinfo.storage.i18n('foss.baseinfo.query'),// 查询
			disabled : !baseinfo.storage
			.isPermission('storage/storageQueryButton'),
			hidden : !baseinfo.storage
					.isPermission('storage/storageQueryButton'),
			handler : function() {
				if (me.getForm().isValid()) {
					if (Ext
							.isEmpty(me.getForm().findField('organizationCode').rawValue)) {
						me.getForm().findField('organizationCode').organizationCode = null;
						me.up().getStorageGrid().getPagingToolbar().moveFirst();
					} else {
						me.up().getStorageGrid().getPagingToolbar().moveFirst();
					}
				}

			}
		}]
		me.callParent([cfg]);
	}
});
/**
 * 库位列表
 */
Ext.define('Foss.baseinfo.storage.StorageGrid', {
	extend : 'Ext.grid.Panel',
	title : baseinfo.storage.i18n('foss.baseinfo.kuLocation'),// 库位信息
	frame : true,
	flex : 1,
	sortableColumns : false,
	enableColumnHide : false,
	enableColumnMove : false,
	stripeRows : true, // 交替行效果
	selType : "rowmodel", // 选择类型设置为：行选择
	emptyText : baseinfo.storage.i18n('foss.baseinfo.queryResultIsNull'),// 查询结果为空
	// 得到bbar
	pagingToolbar : null,
	getPagingToolbar : function() {
		if (this.pagingToolbar == null) {
			this.pagingToolbar = Ext.create('Deppon.StandardPaging', {
						store : this.store,
						pageSize : 20
					});
		}
		return this.pagingToolbar;
	},
	// 库位新增WINDOW
	storageAddWindow : null,
	getStorageAddWindow : function() {
		if (this.storageAddWindow == null) {
			this.storageAddWindow = Ext
					.create('Foss.baseinfo.storage.StorageAddWindow');
			this.storageAddWindow.parent = this;// 父元素
		}
		return this.storageAddWindow;
	},
	// 修改库位WINDOW
	storageUpdateWindow : null,
	getStorageUpdateWindow : function() {
		if (this.storageUpdateWindow == null) {
			this.storageUpdateWindow = Ext
					.create('Foss.baseinfo.storage.StorageUpdateWindow');
			this.storageUpdateWindow.parent = this;// 父元素
		}
		return this.storageUpdateWindow;
	},
//	// 查看库位WINDOW
//	storageShowWindow : null,
//	getStorageShowWindow : function() {
//		if (this.storageShowWindow == null) {
//			this.storageShowWindow = Ext
//					.create('Foss.baseinfo.storage.StorageShowWindow');
//			this.storageShowWindow.parent = this;// 父元素
//		}
//		this.storageShowWindow.getStorageForm().getForm().getFields().each(
//				function(item) {
//					item.setReadOnly(true);
//				});
//		return this.storageShowWindow;
//	},
	// 作废库位
	toVoidStorage : function(btn) {
		var me = this;
		var selections = me.getSelectionModel().getSelection();// 获取选中的数据
		if (selections.length < 1) {// 判断是否至少选中了一条
			baseinfo.showWoringMessage('请选择一条进行作废操作！');// 请选择一条进行作废操作！
			return;// 没有则提示并返回
		}
		baseinfo.showQuestionMes(baseinfo.storage
						.i18n('foss.baseinfo.wantSetAsideTheseLocations'),
				function(e) {// 是否要作废这些库位？
					if (e == 'yes') {// 询问是否删除，是则发送请求
						var ids = new Array();// 库位ID数组
						for (var i = 0; i < selections.length; i++) {
							ids.push(selections[i]
									.get('id'));
						}
						var params = {
							'storageVo' : {
								'ids' : ids
							}
						};
						var successFun = function(json) {
							baseinfo.showInfoMes(json.message);
							me.getPagingToolbar().moveFirst();
						};
						var failureFun = function(json) {
							if (Ext.isEmpty(json)) {
								baseinfo.showErrorMes(baseinfo.storage
										.i18n('foss.baseinfo.requestTimeout'));// 请求超时
							} else {
								baseinfo.showErrorMes(json.message);
							}
						};
						var url = baseinfo.realPath('deleteStorage.action');
						baseinfo.requestJsonAjax(url, params, successFun,
								failureFun);
					}
				})

	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.columns = [{
					xtype : 'rownumberer',
					width : 40,
					text : '序号'// 序号
				}, {
					text : baseinfo.storage.i18n('foss.baseinfo.operate'),// 操作
					// dataIndex : 'id',
					xtype : 'actioncolumn',
					align : 'center',
					width : 80,
					items : [{
						iconCls : 'deppon_icons_edit',
						tooltip : baseinfo.storage.i18n('foss.baseinfo.update'),// 修改
						disabled : !baseinfo.storage
								.isPermission('storage/storageUpdateButton'),
						width : 42,
						handler : function(grid, rowIndex, colIndex) {
							// 获取选中的数据
							var record = grid.getStore().getAt(rowIndex);
						//	var virtualCode = record.get('virtualCode');// 库位虚拟编码
							//6.1改的bug  以前的逻辑是库位编码不会重复，现在有重复的可能，所以改为ID查
							var id = record.get('id');
							var params = {
								'storageVo' : {
									'storageEntity' : {
										'id' : id
									}
								}
							};
							var successFun = function(json) {
								var updateWindow = me.getStorageUpdateWindow();// 获得修改窗口
								json.storageVo.storageEntity.goodsAreaName=record.get('goodsAreaName');
								updateWindow.storageEntity = json.storageVo.storageEntity;// 库位								
								updateWindow.show();// 显示修改窗口
							};
							var failureFun = function(json) {
								if (Ext.isEmpty(json)) {
									baseinfo
											.showErrorMes(baseinfo.storage
													.i18n('foss.baseinfo.requestTimeout'));// 请求超时
								} else {
									baseinfo.showErrorMes(json.message);
								}
							};
							var url = baseinfo
									.realPath('queryStorageById.action');
							baseinfo.requestJsonAjax(url, params, successFun,
									failureFun);
						}
					}, {
						iconCls : 'deppon_icons_cancel',
						tooltip : baseinfo.storage.i18n('foss.baseinfo.void'),// 作废
						disabled : !baseinfo.storage
								.isPermission('storage/storageVoidButton'),
						width : 42,
						handler : function(grid, rowIndex, colIndex) {
							// 获取选中的数据
							var record = grid.getStore().getAt(rowIndex);
							baseinfo
									.showQuestionMes(
											baseinfo.storage
													.i18n('foss.baseinfo.wantSetAsideThisLocation'),
											function(e) {// 是否要作废这个库位？
												if (e == 'yes') {// 询问是否删除，是则发送请求
													var ids = new Array();// 库位ID数组
													//6.1改的bug  以前的逻辑是库位编码不会重复，现在有重复的可能，所以改为ID查
													ids
															.push(record
																	.get('id'));
													var params = {
														'storageVo' : {
															'ids' : ids
														}
													};
													var successFun = function(
															json) {
														baseinfo
																.showInfoMes(json.message);
														me.getPagingToolbar()
																.moveFirst();
													};
													var failureFun = function(
															json) {
														if (Ext.isEmpty(json)) {
															baseinfo
																	.showErrorMes(baseinfo.storage
																			.i18n('foss.baseinfo.requestTimeout'));// 请求超时
														} else {
															baseinfo
																	.showErrorMes(json.message);
														}
													};
													var url = baseinfo
															.realPath('deleteStorage.action');
													baseinfo.requestJsonAjax(
															url, params,
															successFun,
															failureFun);
												}
											})
						}
					}
					// ,{
					// iconCls: 'deppon_icons_showdetail',
					// tooltip:
					// baseinfo.storage.i18n('foss.baseinfo.details'),//查看详情
					// width:42,
					// handler: function(grid, rowIndex, colIndex) {
					// //获取选中的数据
					// var record=grid.getStore().getAt(rowIndex);
					// var virtualCode = record.get('virtualCode');//库位虚拟编码
					// var params =
					// {'storageVo':{'storageEntity':{'virtualCode':virtualCode}}};
					// var successFun = function(json){
					// var showWindow = me.getStorageShowWindow();//获得修改窗口
					// showWindow.storageEntity =
					// json.storageVo.storageEntity;//库位
					// showWindow.show();//显示修改窗口
					// };
					// var failureFun = function(json){
					// if(Ext.isEmpty(json)){
					// baseinfo.showErrorMes(baseinfo.storage.i18n('foss.baseinfo.requestTimeout'));//请求超时
					// }else{
					// baseinfo.showErrorMes(json.message);
					// }
					// };
					// var url =
					// baseinfo.realPath('queryStorageByVirtualCode.action');
					// baseinfo.requestJsonAjax(url,params,successFun,failureFun);
					//                	
					// }
					// }
					]
				}, {
					text : baseinfo.storage.i18n('foss.baseinfo.fieldID'),// 外场编号
					dataIndex : 'transferCode'
				}, {
					text : baseinfo.storage.i18n('foss.baseinfo.fieldName'),// 外场名称
					dataIndex : 'organizationName'
				}, {
					text : baseinfo.storage
							.i18n('foss.baseinfo.locationNumber'),// 库位编号
					dataIndex : 'storageCode'
				}, {
					text : baseinfo.storage
							.i18n('foss.baseinfo.reservoirAreaNumber'),// 库区编号
					dataIndex : 'goodsAreaCode'
				}, {
					text : baseinfo.storage
							.i18n('foss.baseinfo.reservoirAreaName'),// 库区名称
					dataIndex : 'goodsAreaName'
				}
				, {
					text : baseinfo.storage
							.i18n('foss.baseinfo.storage.storageLength'),// 库位长度
					dataIndex : 'storageLength'
				}, {
					text : baseinfo.storage
							.i18n('foss.baseinfo.storage.storageWidth'),// 库位宽度
					dataIndex : 'storageWidth'
				}
				, {
					text : baseinfo.storage
							.i18n('foss.baseinfo.storage.storageAbscissa'),// 库为横坐标
					dataIndex : 'storageAbscissa'
				}, {
					text : baseinfo.storage
							.i18n('foss.baseinfo.storage.storageOrdinate'),// 库位纵坐标
					dataIndex : 'storageOrdinate'
				}, {
					text : baseinfo.storage
							.i18n('foss.baseinfo.airagencycompany.remark'),// 备注
					dataIndex : 'notes'
				}];
		me.store = Ext.create('Foss.baseinfo.storage.StorageStore', {
			autoLoad : false,// 不自动加载
			pageSize : 20,
			listeners : {
				beforeload : function(store, operation, eOpts) {
					var queryForm = me.up().getQueryStorageForm();
					if (queryForm != null) {
						Ext.apply(operation, {
							params : {// 查询库位大查询，查询条件组织
								'storageVo.storageEntity.organizationCode' : queryForm
										.getForm().findField('organizationCode').organizationCode,
							    'storageVo.storageEntity.goodsAreaVirtualCode' : queryForm
										.getForm().findField('goodsAreaVirtualCode').getValue(),
								'storageVo.storageEntity.storageCode' : queryForm
										.getForm().findField('storageCode')
										.getValue()
							}
						});
					}
				}
			}
		});
		me.listeners = {
			scrollershow : function(scroller) {
				if (scroller && scroller.scrollEl) {
					scroller.clearManagedListeners();
					scroller.mon(scroller.scrollEl, 'scroll',
							scroller.onElScroll, scroller);
				}
			}
		}, me.selModel = Ext.create('Ext.selection.CheckboxModel', {// 多选框
			mode : 'MULTI',
			checkOnly : true
		});
		me.tbar = [{
			text : baseinfo.storage.i18n('foss.baseinfo.add'),// 新增
			disabled : !baseinfo.storage.isPermission('storage/storageAddButton'),
			hidden : !baseinfo.storage.isPermission('storage/storageAddButton'),
			handler : function() {
				me.getStorageAddWindow().show();
			}
		}, '-', {
			text : baseinfo.storage.i18n('foss.baseinfo.void'),// 作废
			disabled : !baseinfo.storage
			.isPermission('storage/storageVoidButton'),
			hidden : !baseinfo.storage
					.isPermission('storage/storageVoidButton'),
			handler : function() {
				me.toVoidStorage();
			}
		}];
		me.bbar = me.getPagingToolbar();
		me.callParent([cfg]);
	}
});

/**
 * @description 站点组主页
 */
Ext.onReady(function() {
	Ext.QuickTips.init();
	if (Ext.getCmp('T_baseinfo-storage_content')) {
		return;
	};
	var queryStorageForm = Ext.create('Foss.baseinfo.storage.QueryStorageForm');// 查询FORM
	var storageGrid = Ext.create('Foss.baseinfo.storage.StorageGrid');// 查询结果GRID
	Ext.getCmp('T_baseinfo-storage').add(Ext.create('Ext.panel.Panel', {
				id : 'T_baseinfo-storage_content',
				cls : 'panelContentNToolbar',
				bodyCls : 'panelContentNToolbar-body',
				// 获得查询FORM
				getQueryStorageForm : function() {
					return queryStorageForm;
				},
				// 获得查询结果GRID
				getStorageGrid : function() {
					return storageGrid;
				},
				items : [queryStorageForm, storageGrid]
			}));
});
// ----------------------------------------------上面是整体布局，下面是弹出窗口----------------------------------
/**
 * 新增库位信息
 */
Ext.define('Foss.baseinfo.storage.StorageAddWindow', {
			extend : 'Ext.window.Window',
			title : baseinfo.storage.i18n('foss.baseinfo.addLocation'),// 新增库位
			closable : true,
			parent : null,// 父元素（弹出这个window的gird——Foss.baseinfo.storage.PlatformGrid）
			resizable : true,// 可以调整窗口的大小
			closeAction : 'hide',// 点击关闭是隐藏窗口
			width : 660,
			height : 450,
			layout : {
				type : 'fit',
				align : 'stretch'
			},
			listeners : {
				beforehide : function(me) {// 隐藏WINDOW的时候清除数据
					me.resetData();
				},
				beforeshow : function(me) {// 显示WINDOW的时候清除数据

				}
			},
			// 新增库位FORM
			storageForm : null,
			getStorageForm : function() {
				if (Ext.isEmpty(this.storageForm)) {
					this.storageForm = Ext.create(
							'Foss.baseinfo.storage.StorageForm', {
								'isUpdate' : false
								// 证明是修改
						});
				}
				return this.storageForm;
			},
			// 提交库位数据
			commitStorage : function(button) {
				var me = this;
				var form = me.getStorageForm();
				if (form.getForm().isValid()) {// 校验form是否通过校验
					// Foss.baseinfo.storage.StorageEntity
					// Foss.baseinfo.storage.DistanceEntity
					var storageModel = new Foss.baseinfo.storage.StorageEntity();
					// var distanceList = new Array();// 到各个月台的距离列表
					form.getForm().updateRecord(storageModel);// 将FORM中数据设置到MODEL里面
					storageModel.set('goodsAreaCode',form.getForm().findField('goodsAreaCode').getRawValue());
					// if(!Ext.isEmpty(form.oldItems)){
					// for(var i = 0;i<form.oldItems.length;i++){
					// if(!Ext.isEmpty(form.oldItems[i].getValue())){
					// var distance =
					// {'platformVirtualCode':form.oldItems[i].platformEntity.virtualCode
					// ,'distance':form.oldItems[i].getValue()};
					// distanceList.push(distance);
					// }
					// }
					// }
					// storageModel.set('distanceList',distanceList);
					var params = {
						'storageVo' : {
							'storageEntity' : storageModel.data
						}
					};// 组织新增数据
					var successFun = function(json) {
						button.setDisabled(false);
						baseinfo.showInfoMes(json.message);// 提示新增成功
						me.close();
						me.parent.getPagingToolbar().moveFirst();// 成功之后重新查询刷新结果集
					};
					var failureFun = function(json) {
						button.setDisabled(false);
						if (Ext.isEmpty(json)) {
							baseinfo.showErrorMes(baseinfo.storage
									.i18n('foss.baseinfo.requestTimeout'));// 请求超时
						} else {
							baseinfo.showErrorMes(json.message);// 提示失败原因
						}
					};
					var url = baseinfo.realPath('addStorage.action');// 请求库位新增
					button.setDisabled(true);
					baseinfo.requestJsonAjax(url, params, successFun,
							failureFun);// 发送AJAX请求
				}
			},
			resetData : function() {
				var me = this;
				me.getStorageForm().getForm().reset();// 表格重置
				var form = me.getStorageForm();
//				if (!Ext.isEmpty(form.oldItems)) {// 将多余的元素清掉
//					for (var i = 0; i < form.oldItems.length; i++) {
//						form.remove(form.oldItems[i]);
//					}
//				}
			},
			constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				me.fbar = [{
							text : baseinfo.storage
									.i18n('foss.baseinfo.cancel'),// 取消
							handler : function() {
								me.close();
							}
						}, {
							text : baseinfo.storage.i18n('foss.baseinfo.reset'),// 重置
							handler : function() {
								me.resetData();
							}
						}, {
							text : baseinfo.storage.i18n('foss.baseinfo.save'),// 保存
							cls : 'yellow_button',
							margin : '0 0 0 305',
							handler : function() {
								me.commitStorage(this);
							}
						}];
				me.items = [me.getStorageForm()];
				me.callParent([cfg]);
			}
		});
/**
 * 修改库位
 */
Ext.define('Foss.baseinfo.storage.StorageUpdateWindow', {
	extend : 'Ext.window.Window',
	title : baseinfo.storage.i18n('foss.baseinfo.modifyLocation'),// 修改库位
	closable : true,
	resizable : false,
	storageEntity : null,// 修改库位数据
	parent : null,// 父元素（弹出这个window的gird——Foss.baseinfo.storage.SiteGroupGrid）
	closeAction : 'hide',
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	width : 660,
	height : 450,
	listeners : {
		beforehide : function(me) {
			me.resetData();// 清除掉这次的数据
		},
		beforeshow : function(me) {
			var updateStorageForm=me.getStorageForm().getForm();
			updateStorageForm.findField('organizationName')
					.setCombValue(me.storageEntity.organizationName,
							me.storageEntity.transferCode);
			updateStorageForm.findField('organizationCode')
					.setValue(me.storageEntity.organizationCode);
			updateStorageForm.findField('transferCode')
					.setValue(me.storageEntity.transferCode);
					
			updateStorageForm.findField('goodsAreaVirtualCode')
			         .setValue(me.storageEntity.goodsAreaVirtualCode);         
		    if(!Ext.isEmpty(me.storageEntity.goodsAreaCode)){
		     updateStorageForm.findField('goodsAreaCode')
			         .setCombValue(me.storageEntity.goodsAreaCode,me.storageEntity.goodsAreaName);
		    }
			updateStorageForm.findField('goodsAreaName')
			         .setValue(me.storageEntity.goodsAreaName);
			updateStorageForm.findField('storageLength')
			         .setValue(me.storageEntity.storageLength);
			updateStorageForm.findField('storageWidth')
			         .setValue(me.storageEntity.storageWidth);
			updateStorageForm.findField('storageAbscissa')
			         .setValue(me.storageEntity.storageAbscissa);
		    updateStorageForm.findField('storageOrdinate')
			         .setValue(me.storageEntity.storageOrdinate);
			var storageModel = new Foss.baseinfo.storage.StorageEntity(me.storageEntity);
			me.getStorageForm().getForm().updateRecord(storageModel);
			me.loadValue();
		}
	},
	// 库位FORM
	storageForm : null,
	getStorageForm : function() {
		if (Ext.isEmpty(this.storageForm)) {
			this.storageForm = Ext.create('Foss.baseinfo.storage.StorageForm',
					{
						'isUpdate' : true
						// 证明是修改
				});
			this.storageForm.getForm().findField('organizationName')
					.setReadOnly(true);
		}
		return this.storageForm;
	},
	// 修改库位
	commitStorage : function(button) {
		var me = this;
		var form = me.getStorageForm();
		if (form.getForm().isValid()) {// 校验form是否通过校验
			var storageModel = new Foss.baseinfo.storage.StorageEntity(me.storageEntity);
			//var distanceList = me.storageEntity.distanceList;// 到各个月台的距离列表（要比较的数据）
			var commintDistanceList = new Array();// 要提交的数据
			form.getForm().updateRecord(storageModel);// 将FORM中数据设置到MODEL里面
			storageModel.set('goodsAreaCode',form.getForm().findField('goodsAreaCode').getRawValue());
//			if (!Ext.isEmpty(form.oldItems)) {
//				for (var i = 0; i < form.oldItems.length; i++) {
//					var isOld = false;// 是否是先前就有的数据
//					var distanceOld = null;
//					for (var j = 0; j < distanceList.length; j++) {// 再循环其原来有的距离
//						if (form.oldItems[i].platformEntity.virtualCode == distanceList[j].platformVirtualCode) {
//							isOld = true;
//							distanceOld = distanceList[j];
//							break;
//						} else {
//							isOld = false
//						}
//					}
//					if (!Ext.isEmpty(form.oldItems[i].getValue())) {// 数据不为空，则分两种情况1、是原来就有的修改值2、原来没有的新增值
//						var distance = null;
//						if (isOld) {
//							distanceOld.distance = form.oldItems[i].getValue();
//							distance = distanceOld;
//						} else {
//							distance = {
//								'platformVirtualCode' : form.oldItems[i].platformEntity.virtualCode,
//								'distance' : form.oldItems[i].getValue()
//							};
//						}
//						commintDistanceList.push(distance);
//					} else {// 数据为空，原来就有的传空值
//						if (isOld) {
//							distanceOld.distance = null;
//							commintDistanceList.push(distanceOld);
//						}
//					}
//				}
//			}
//			storageModel.set('distanceList', commintDistanceList);
			var params = {
				'storageVo' : {
					'storageEntity' : storageModel.data
				}
			};// 组织新增数据
			var successFun = function(json) {
				button.setDisabled(false);
				baseinfo.showInfoMes(json.message);// 提示修改成功
				me.close();
				me.parent.getPagingToolbar().moveFirst();// 成功之后重新查询刷新结果集
			};
			var failureFun = function(json) {
				button.setDisabled(false);
				if (Ext.isEmpty(json)) {
					baseinfo.showErrorMes(baseinfo.storage
							.i18n('foss.baseinfo.requestTimeout'));// 请求超时
				} else {
					baseinfo.showErrorMes(json.message);// 提示失败原因
				}
			};
			var url = baseinfo.realPath('updateStorage.action');// 请求库位修改
			button.setDisabled(true);
			baseinfo.requestJsonAjax(url, params, successFun, failureFun);// 发送AJAX请求
		}
	},
	// 清楚数据
	resetData : function() {
		var me = this;
		me.getStorageForm().getForm().reset();// 表格重置
		var form = me.getStorageForm();
		if (!Ext.isEmpty(form.oldItems)) {// 将多余的元素清掉
			for (var i = 0; i < form.oldItems.length; i++) {
				form.remove(form.oldItems[i]);
			}
		}
	},
	// 加载原有数据
	loadValue : function() {// 外场名称和外场code不进行处理
		var me = this;
		var storageModel = new Foss.baseinfo.storage.StorageEntity(me.storageEntity);
		var updateStorageForm=me.getStorageForm().getForm();
		updateStorageForm.findField('notes').setValue(storageModel
				.get('notes'));
		updateStorageForm.findField('storageCode')
				.setValue(storageModel.get('storageCode'));
		if(!Ext.isEmpty(storageModel.get('goodsAreaCode'))){
		     updateStorageForm.findField('goodsAreaCode')
			         .setCombValue(storageModel.get('goodsAreaCode'),storageModel.get('goodsAreaName'));
		  }
			updateStorageForm.findField('goodsAreaName')
			         .setValue(storageModel.get('goodsAreaName'));
			updateStorageForm.findField('storageLength')
			         .setValue(storageModel.get('storageLength'));
			updateStorageForm.findField('storageWidth')
			         .setValue(storageModel.get('storageWidth'));
			updateStorageForm.findField('storageAbscissa')
			         .setValue(storageModel.get('storageAbscissa'));
		    updateStorageForm.findField('storageOrdinate')
			         .setValue(storageModel.get('storageOrdinate'));
		updateStorageForm.updateRecord(storageModel);
		
//		if (!Ext.isEmpty(me.getStorageForm().oldItems)) {
//			for (var i = 0; i < me.getStorageForm().oldItems.length; i++) {
//				me.getStorageForm().oldItems[i].reset();
//			}
//		}
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.fbar = [{
					text : baseinfo.storage.i18n('foss.baseinfo.cancel'),// 取消
					handler : function() {
						me.close();
					}
				}, {
					text : baseinfo.storage.i18n('foss.baseinfo.reset'),// 重置
					handler : function() {
						me.getStorageForm().getForm()
								.findField('organizationName').setCombValue(
										me.storageEntity.organizationName,
										me.storageEntity.transferCode);
						me.getStorageForm().getForm()
								.findField('organizationCode')
								.setValue(me.storageEntity.organizationCode);
						me.loadValue();
					}
				}, {
					text : baseinfo.storage.i18n('foss.baseinfo.save'),// 保存
					cls : 'yellow_button',
					margin : '0 0 0 305',
					handler : function() {
						me.commitStorage(this);
					}
				}];
		me.items = [me.getStorageForm()];
		me.callParent([cfg]);
	}
});
/**
 * 查看库位
 */
//Ext.define('Foss.baseinfo.storage.StorageShowWindow', {
//	extend : 'Ext.window.Window',
//	title : baseinfo.storage.i18n('foss.baseinfo.viewLocation'),// 查看库位
//	closable : true,
//	resizable : false,
//	storageEntity : null,// 修改库位数据
//	parent : null,// 父元素（弹出这个window的gird——Foss.baseinfo.storage.SiteGroupGrid）
//	closeAction : 'hide',
//	layout : {
//		type : 'vbox',
//		align : 'stretch'
//	},
//	width : 570,
//	height : 450,
//	listeners : {
//		beforehide : function(me) {
//			me.resetData();// 清除掉这次的数据
//		},
//		beforeshow : function(me) {
//			me.getStorageForm().getForm().findField('organizationName')
//					.setCombValue(me.storageEntity.organizationName,
//							me.storageEntity.transferCode);
//			me.getStorageForm().getForm().findField('organizationCode')
//					.setValue(me.storageEntity.organizationCode);
//			me.getStorageForm().getForm().findField('transferCode')
//					.setValue(me.storageEntity.transferCode);
//			me.loadValue();
//		}
//	},
//	// 库位FORM
//	storageForm : null,
//	getStorageForm : function() {
//		if (Ext.isEmpty(this.storageForm)) {
//			this.storageForm = Ext.create('Foss.baseinfo.storage.StorageForm',
//					{
//						'isUpdate' : true,// 证明是修改
//						'isShow' : true
//					});
//			this.storageForm.getForm().findField('organizationName')
//					.setReadOnly(true);
//		}
//		return this.storageForm;
//	},
//	// 清楚数据
//	resetData : function() {
//		var me = this;
//		me.getStorageForm().getForm().reset();// 表格重置
//		var form = me.getStorageForm();
//		if (!Ext.isEmpty(form.oldItems)) {// 将多余的元素清掉
//			for (var i = 0; i < form.oldItems.length; i++) {
//				form.remove(form.oldItems[i]);
//			}
//		}
//	},
//	// 加载原有数据
//	loadValue : function() {// 外场名称和外场code不进行处理
//		var me = this;
//		var storageModel = new Foss.baseinfo.storage.StorageEntity(me.storageEntity);
//		me.getStorageForm().getForm().findField('notes').setValue(storageModel
//				.get('notes'));
//		me.getStorageForm().getForm().findField('storageCode')
//				.setValue(storageModel.get('storageCode'));
//		if (!Ext.isEmpty(me.getStorageForm().oldItems)) {
//			for (var i = 0; i < me.getStorageForm().oldItems.length; i++) {
//				me.getStorageForm().oldItems[i].reset();
//			}
//		}
//	},
//	constructor : function(config) {
//		var me = this, cfg = Ext.apply({}, config);
//		me.fbar = [{
//					text : baseinfo.storage.i18n('foss.baseinfo.cancel'),// 取消
//					handler : function() {
//						me.close();
//					}
//				}];
//		me.items = [me.getStorageForm()];
//		me.callParent([cfg]);
//	}
//});
/**
 * 库位组-FORM
 */
Ext.define('Foss.baseinfo.storage.StorageForm', {
			extend : 'Ext.form.Panel',
			title : baseinfo.storage.i18n('foss.baseinfo.kuLocation'),// 库位信息
			frame : true,
			autoScroll : true,
			height : 330,
			oldItems : new Array(),// 上一次的库位到月台元素
			isUpdate : false,// 是否为修改
			// flex:1,
			collapsible : true,
			defaults : {
				margin : '5 5 5 5',
				labelWidth : 100,
				// width:200,
				colspan : 1
			},
			layout : {
				type : 'table',
				columns : 2
			},
			constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				me.items = [{
					xtype : 'commontransfercenterselector',
					userCode : FossUserContext.getCurrentUserEmp().empCode,
					currentOrgCode : FossUserContext.getCurrentDeptCode(),
					allowBlank : false,
					forceSelection : true,
					name : 'organizationName',// 外场名称
					fieldLabel : baseinfo.storage
							.i18n('foss.baseinfo.outfield'),
					listeners : {
						select : function(text, records, eops) {
							me.getForm().findField('organizationCode')
									.setValue(records[0].get('orgCode'));
							me.getForm().findField('transferCode')
									.setValue(records[0].get('code'));
						},
						change:function(text, records, eops){
							if(Ext.isEmpty(text.getValue())){
							 me.getForm().findField('organizationCode')
									.setValue(null);
							me.getForm().findField('goodsAreaCode')
									.setValue(null);
							 me.getForm().findField('transferCode')
									.setValue(null);
							}
						}
					}
				}, {
					name : 'transferCode',// 外场编号
					readOnly : true,
					allowBlank : false,
					fieldLabel : baseinfo.storage.i18n('foss.baseinfo.fieldID'),
					xtype : 'textfield'
				}, {
					name : 'organizationCode',// 外场编号
					readOnly : true,
					hidden : true,
					xtype : 'textfield'
				}, {
					name : 'storageCode',// 库位编号
					allowBlank : false,
					maxLength : '20',
					colspan : 2,
					fieldLabel : baseinfo.storage
							.i18n('foss.baseinfo.locationNumber'),
					xtype : 'textfield'
				},{
					xtype : 'commongoodsareaselectorBycode',//'commongoodsareaselector',// 所属库区名称
					fieldLabel : baseinfo.storage
							.i18n('foss.baseinfo.storage.goodsAreaCode'),//所属库区编号
					name :'goodsAreaCode', //'goodsAreaName',
					allowBlank : false,
					forceSelection : true,
					listeners : {
						expand : function(field, eOpts) {
						var organizationCode = me.getForm().findField('organizationCode').getValue();
						if (null == organizationCode
								|| "" ==organizationCode) {
							Ext.MessageBox.alert('提示', '外场不能为空！');
							return;
						}
						field.getStore().addListener('beforeload', function(store, operation, eOpts) {
							var searchParams = operation.params;
							if (Ext.isEmpty(searchParams)) {
								searchParams = {};
								Ext.apply(operation, {
											params : searchParams
										});
							}
							searchParams['goodsAreaVo.goodsAreaEntity.organizationCode'] = organizationCode;
						})
						field.getStore().load({
									params : {
										'goodsAreaVo.goodsAreaEntity.organizationCode': organizationCode
									},
									callback : function(records, operation,
											success) {
										// console.log(records);

									}
								});
						},
						select : function(text, records, eops) {
							me.getForm().findField('goodsAreaName')
									.setValue(records[0].get('goodsAreaName'));
							me.getForm().findField('goodsAreaVirtualCode')
									.setValue(records[0].get('virtualCode'));
						},
						change:function(text, records, eops){
							if(Ext.isEmpty(text.getValue())){
							 me.getForm().findField('goodsAreaName')
									.setValue(null);
							 me.getForm().findField('goodsAreaVirtualCode')
									.setValue(null);
							}
						}
					}
				},{
					name : 'goodsAreaVirtualCode',//  所属库区虚拟编号
					readOnly : true,
					hidden : true,
					xtype : 'textfield'
				},{
					name : 'goodsAreaName',// 所属库区名称
					readOnly : true,
					allowBlank : false,
					fieldLabel : baseinfo.storage
							.i18n('foss.baseinfo.storage.goodsAreaName'),
					xtype : 'textfield'
				}, {
					xtype : 'numberfield',
					allowBlank : false,
					name : 'storageLength',// 库位长度
					fieldLabel : baseinfo.storage
							.i18n('foss.baseinfo.storage.storageLength'),
					maxValue : 999999.99,
					minValue : 0,
					step : 0.01,
					decimalPrecision : 2
				}, {
					xtype : 'numberfield',
					allowBlank : false,
					name : 'storageWidth',// 库位宽度
					fieldLabel : baseinfo.storage
							.i18n('foss.baseinfo.storage.storageWidth'),
					maxValue : 999999.99,
					minValue : 0,
					step : 0.01,
					decimalPrecision : 2
				}, {
					xtype : 'numberfield',
					allowBlank : false,
					name : 'storageAbscissa',// 库位横坐标
					fieldLabel : baseinfo.storage
							.i18n('foss.baseinfo.storage.storageAbscissa'),
					maxValue : 999999.99,
					minValue : -999999.99,
					step : 0.01,
					decimalPrecision : 2
				}, {
					xtype : 'numberfield',
					allowBlank : false,
					name : 'storageOrdinate',// 库位 纵坐标
					fieldLabel : baseinfo.storage
							.i18n('foss.baseinfo.storage.storageOrdinate'),
					maxValue : 999999.99,
					minValue : -999999.99,
					step : 0.01,
					decimalPrecision : 2
				}, {
					name : 'notes',// 备注
					fieldLabel : baseinfo.storage
							.i18n('foss.baseinfo.airagencycompany.remark'),
					colspan : 2,
					maxLength : 200,
					width : 400,
					xtype : 'textareafield'
				}
				// ,{
				// colspan : 2,
				// xtype:'displayfield',
				// value:baseinfo.storage.i18n('foss.baseinfo.awayFromPlatformDistance(meters)')//离月台距离（米）
				// }
				];
				me.callParent([cfg]);
				// if(me.isUpdate){//修改
				// me.getForm().findField('organizationCode').on('change',function(text,newValue,oldValue){
				// if(!Ext.isEmpty(me.oldItems)){
				// for(var i = 0;i<me.oldItems.length;i++){
				// me.remove(me.oldItems[i]);
				// }
				// }
				// if(!Ext.isEmpty(newValue)){//设置的值不为空时才执行操作
				// var organizationCode = newValue;//查询该外场下的所有月台
				// var params =
				// {'platformVo':{'platformEntity':{'organizationCode':organizationCode}}};
				// var successFun = function(json){
				// var platformEntityList = json.platformVo.platformEntityList;
				// if(!Ext.isEmpty(platformEntityList)){//根据月台构建numberfiled
				// var items = new Array();
				// for(var i = 0;i<platformEntityList.length;i++ ){
				// var distanceList =
				// me.up('window').storageEntity.distanceList;
				// var numberfieldValue = null;
				// if(!Ext.isEmpty(distanceList)){//distanceList不为空表示设置了部分距离月台的距离
				// for(var j = 0;j<distanceList.length;j++){
				// if(distanceList[j].platformVirtualCode==platformEntityList[i].virtualCode){//如果相同则表示这个语态以前被赋值
				// numberfieldValue = distanceList[j].distance;
				// }else{
				//											
				// }
				// }
				// }else{
				//									
				// }
				// var item = null;//月台对应的元素
				// if(me.isShow){
				// if(Ext.isEmpty(numberfieldValue)){
				// item = {
				// xtype:'numberfield',
				// fieldLabel:platformEntityList[i].platformCode,
				// platformEntity:platformEntityList[i],//月台相关数据
				// maxValue:999999.999,
				// readOnly:true,
				// minValue:0,
				// step:0.001,
				// decimalPrecision:3
				// };
				// }else{
				// item = {
				// xtype:'numberfield',
				// fieldLabel:platformEntityList[i].platformCode,
				// platformEntity:platformEntityList[i],//月台相关数据
				// maxValue:999999.999,
				// minValue:0,
				// readOnly:true,
				// value:numberfieldValue,//这样设置值，点击reset的时候就不用再重新赋值了
				// step:0.001,
				// decimalPrecision:3
				// };
				// }
				// }else{
				// if(Ext.isEmpty(numberfieldValue)){
				// item = {
				// xtype:'numberfield',
				// fieldLabel:platformEntityList[i].platformCode,
				// platformEntity:platformEntityList[i],//月台相关数据
				// maxValue:999999.999,
				// minValue:0,
				// step:0.001,
				// decimalPrecision:3
				// };
				// }else{
				// item = {
				// xtype:'numberfield',
				// fieldLabel:platformEntityList[i].platformCode,
				// platformEntity:platformEntityList[i],//月台相关数据
				// maxValue:999999.999,
				// minValue:0,
				// value:numberfieldValue,//这样设置值，点击reset的时候就不用再重新赋值了
				// step:0.001,
				// decimalPrecision:3
				// };
				// }
				// }
				// items.push(item);
				// }
				// me.oldItems = me.add(items);
				// }
				// };
				// var failureFun = function(json){
				// if(Ext.isEmpty(json)){
				// baseinfo.showErrorMes(baseinfo.storage.i18n('foss.baseinfo.requestTimeout'));//请求超时
				// }else{
				// baseinfo.showErrorMes(json.message);//提示失败原因
				// }
				// }
				// var url =
				// baseinfo.realPath('searchPlatformByOrganizationCode.action');//查询该外场的月台
				// baseinfo.requestJsonAjax(url,params,successFun,failureFun);//发送AJAX请求
				// }
				// });
				// }else{
				// me.getForm().findField('organizationName').on('blur',function(text,obj){
				// if(!Ext.isEmpty(me.oldItems)){//清除掉原先的元素
				// for(var i = 0;i<me.oldItems.length;i++){
				// me.remove(me.oldItems[i]);
				// }
				// }
				// var organizationCode =
				// me.getForm().findField('organizationCode').getValue();//查询该外场下的所有月台
				// var params =
				// {'platformVo':{'platformEntity':{'organizationCode':organizationCode}}};
				// var successFun = function(json){
				// var platformEntityList = json.platformVo.platformEntityList;
				// if(!Ext.isEmpty(platformEntityList)){
				// var items = new Array();
				// for(var i = 0;i<platformEntityList.length;i++ ){
				// var item = {//根据月台构建numberfiled
				// xtype:'numberfield',
				// fieldLabel:platformEntityList[i].platformCode,
				// platformEntity:platformEntityList[i],//月台相关数据
				// maxValue:999999.999,
				// minValue:0,
				// step:0.001,
				// decimalPrecision:3
				// };
				// items.push(item);
				// }
				// me.oldItems = me.add(items);
				// }
				// };
				// var failureFun = function(json){
				// if(Ext.isEmpty(json)){
				// baseinfo.showErrorMes(baseinfo.storage.i18n('foss.baseinfo.requestTimeout'));//请求超时
				// }else{
				// baseinfo.showErrorMes(json.message);//提示失败原因
				// }
				// }
				// var url =
				// baseinfo.realPath('searchPlatformByOrganizationCode.action');//查询该外场的月台
				// baseinfo.requestJsonAjax(url,params,successFun,failureFun);//发送AJAX请求
				// });
				// }
			}
		});
