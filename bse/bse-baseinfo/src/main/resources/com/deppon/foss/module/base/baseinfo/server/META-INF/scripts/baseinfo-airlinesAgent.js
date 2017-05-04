//根据ISSUE-3779航空公司代理人基础数据中增加是否为外部代理的标识 13-8-13 
/*
 * 转换long类型为日期
 */
baseinfo.changeLongToDate = function (value) {
	if (value != null) {
		var date = new Date(value);
		return date;
	} else {
		return null;
	}
};
/*
 * Ajax请求
 */
baseinfo.requestJsonAjax = function (url, params, successFn, failFn) {
	Ext.Ajax.request({
		url : url,
		jsonData : params,
		success : function (response) {
			var result = Ext.decode(response.responseText);
			if (result.success) {
				successFn(result);
			} else {
				failFn(result);
			}
		},
		failure : function (response) {
			var result = Ext.decode(response.responseText);
			failFn(result);
		},
		exception : function (response) {
			var result = Ext.decode(response.responseText);
			failFn(result);
		}
	});
};
/**
 * 公共方法，通过storeId和model创建STORE
 * @param {Object} storeId
 * @param {Object} model store所用到的model名
 * @param {Object} fields store所用到的fields
 * @param {Object} data
 * @return {Object} 返回创建的store
 */
baseinfo.getStore = function (storeId, model, fields, data) {
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
baseinfo.yes = 'Y'; //是
baseinfo.no = 'N'; //否
baseinfo.ALL = 'ALL'; //全部
//--------------------------------------baseinfo----------------------------------------
//航空公司代理人MODE;
Ext.define('Foss.baseinfo.AirlinesAgentEntity', {
	extend : 'Ext.data.Model',
	fields : [{
			name : 'id',
			type : 'string'
		}, {
			name : 'assemblyDeptId', //配载部门
			type : 'string'
		}, {
			name : 'airlinesCode', //航空公司代码
			type : 'string'
		}, {
			name : 'agentCode', //代理人编码
			type : 'string'
		}, {
			name : 'agentName', //代理人名称
			type : 'string'
		}, {
			name : 'isOutAgent', //代理人名称
			type : 'string'
		}, {
			name : 'active', //是否启用
			type : 'string'
		}
	]
});
//航空公司代理人DTO;
Ext.define('Foss.baseinfo.AirlinesAgentDto', {
	extend : 'Ext.data.Model',
	fields : [{
			name : 'id',
			type : 'string'
		}, {
			name : 'dispatchDepartment' //配载部门名称
		}, {
			name : 'originatingCity', //始发城市
			type : 'string'
		}, {
			name : 'airlinesName', //航空公司
			type : 'string'
		}, {
			name : 'isOutAgent', //始发城市
			type : 'string'
		}, {
			name : 'airlinesAgent' //代理人信息
		}
	]
});
//航空单类型：model 根据ISSUE-3779航空公司代理人基础数据中增加是否为外部代理的标识
Ext.define('Foss.Messages.IsOutAgentModel',{
	extend:'Ext.data.Model',
	fields:[{
		name:'isOutAgentCode'
	},{
		name:'isOutAgentName'
	}]
	
});
//航空单类型：store
Ext.define('Foss.Messages.IsOutAgentStore',{
	extend:'Ext.data.Store',
	model:'Foss.Messages.IsOutAgentModel',
	data:{
		'items':[
			{isOutAgentCode:'Y',isOutAgentName:'是'},
			{isOutAgentCode:'N',isOutAgentName:'否'},
			{isOutAgentCode:'',isOutAgentName:'全部'}
		]
	},
	proxy:{
		type:'memory',
		reader:{
			type:'json',
			root:'items'
		}
	}
});
//------------------------------------model---------------------------------------------------
/**
 * 航空公司代理人Store（Foss.baseinfo.AirlinesAgentDto）
 */
Ext.define('Foss.baseinfo.AirlinesAgentStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.baseinfo.AirlinesAgentDto', //航空公司代理人的MODEL
	pageSize : 20,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url : baseinfo.realPath('queryAirlinesAgentListBySelectiveCondition.action'), //请求地址
		reader : {
			type : 'json',
			root : 'airlinesAgentVo.airlinesAgentDtoList', //获取的数据
			totalProperty : 'totalCount' //总个数
		}
	}
});

//----------------------------------------store---------------------------------

/**
 * 航空公司代理人表单
 */
Ext.define('Foss.baseinfo.QueryAirlinesAgentForm', {
	extend : 'Ext.form.Panel',
	title : baseinfo.airlinesAgent.i18n('foss.baseinfo.queryCondition'), //查询条件
	frame : true,
	collapsible : true,
	layout : {
		type : 'table',
		columns : 3
	},
	defaults : {
		colspan : 1,
		margin : '8 10 5 10'
	},
	height : 180,
	defaultType : 'textfield',
	constructor : function (config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.items = [{
				name : 'agentName',
				fieldLabel : baseinfo.airlinesAgent.i18n('foss.baseinfo.agentName'), //代理人名称
				xtype : 'textfield'
			}, {
				name : 'agentCode',
				fieldLabel : baseinfo.airlinesAgent.i18n('foss.baseinfo.agentCoding'), //代理人编码
				xtype : 'textfield'
			}, {
				name : 'airlinesCode',
				fieldLabel : baseinfo.airlinesAgent.i18n('foss.baseinfo.nameOfTheAirline'), //航空公司
				xtype : 'commonairlinesselector',
				editable : false,
				displayField : 'name'
			}, {
				name : 'assemblyDeptId',
				colspan:1,
				fieldLabel : baseinfo.airlinesAgent.i18n('foss.baseinfo.stowageSector'), //配载部门
				xtype : 'dynamicorgcombselector',
				editable : true,
				doAirDispatch : 'Y'
			},{
			  	xtype:'combobox',
		    	name:'isOutAgent',
		    	fieldLabel: baseinfo.airlinesAgent.i18n('foss.baseinfo.isOutAgent'),//是否外部代理
		    	colspan:2,
				store:Ext.create('Foss.Messages.IsOutAgentStore'),
				queryModel:'local',
				editable:false,
				displayField:'isOutAgentName',
				valueField:'isOutAgentCode',
				columnWidth:.1
	 	  },
//	 	  {
//			     xtype: 'radiogroup', 
//			     name:'isOutAgent',
//			     fieldLabel: baseinfo.airlinesAgent.i18n('foss.baseinfo.isOutAgent'),//是否外部代理
//				 //allowBlank:false,
//				 height: 24,
//				 colspan:2,
//				 columnWidth:.4,
//				 items: [
//				    {boxLabel: baseinfo.airlinesAgent.i18n('foss.baseinfo.yes'), name: 'isHotCity', inputValue: 'Y'},
//					{boxLabel: baseinfo.airlinesAgent.i18n('foss.baseinfo.no'), name: 'isHotCity', inputValue: 'N'}
//				  ]
//				},
				{
				border: 1,
				xtype:'container',
				columnWidth:1,
				colspan:3,
				defaultType:'button',
				layout:'column',
				items:[{
					  text : baseinfo.airlinesAgent.i18n('foss.baseinfo.reset'), //重置
					  columnWidth:.12,
					  disabled:!baseinfo.airlinesAgent.isPermission('airlinesAgent/airlinesAgentQueryButton'),
					  hidden:!baseinfo.airlinesAgent.isPermission('airlinesAgent/airlinesAgentQueryButton'),
					  handler : function () {
							me.getForm().reset();
						}
				  	},{
						xtype:'container',
						border:false,
						html:'&nbsp;',
						columnWidth:.76
					},{
					  text : baseinfo.airlinesAgent.i18n('foss.baseinfo.query'), //查询
					  columnWidth:.12,
					  cls:'yellow_button', 
					  disabled:!baseinfo.airlinesAgent.isPermission('airlinesAgent/airlinesAgentQueryButton'),
					  hidden:!baseinfo.airlinesAgent.isPermission('airlinesAgent/airlinesAgentQueryButton'),
					  handler : function () {
							if (me.getForm().isValid()) {
								me.up().getAirlinesAgentGrid().getPagingToolbar().moveFirst();
							}
							
						}
				  	}]
			 }];
		me.callParent([cfg]);
	}
});
/**
 * 航空公司代理人列表
 */
Ext.define('Foss.baseinfo.AirlinesAgentGrid', {
	extend : 'Ext.grid.Panel',
	title : baseinfo.airlinesAgent.i18n('foss.baseinfo.airlineAgentInformation'), //航空公司代理人信息
	frame : true,
	flex : 1,
	sortableColumns : false,
	enableColumnHide : false,
	enableColumnMove : false,
	stripeRows : true, // 交替行效果
	selType : "rowmodel", // 选择类型设置为：行选择
	emptyText : baseinfo.airlinesAgent.i18n('foss.baseinfo.queryResultIsNull'), //查询结果为空
	//得到bbar
	pagingToolbar : null,
	getPagingToolbar : function () {
		if (this.pagingToolbar == null) {
			this.pagingToolbar = Ext.create('Deppon.StandardPaging', {
					store : this.store,
					pageSize : 20
				});
		}
		return this.pagingToolbar;
	},
	//航空公司代理人新增WINDOW
	airlinesAgentAddWindow : null,
	getAirlinesAgentAddWindow : function () {
		if (this.airlinesAgentAddWindow == null) {
			this.airlinesAgentAddWindow = Ext.create('Foss.baseinfo.AirlinesAgentAddWindow');
			this.airlinesAgentAddWindow.parent = this; //父元素
		}
		return this.airlinesAgentAddWindow;
	},
	//修改航空公司代理人WINDOW
	airlinesAgentUpdateWindow : null,
	getAirlinesAgentUpdateWindow : function () {
		if (this.airlinesAgentUpdateWindow == null) {
			this.airlinesAgentUpdateWindow = Ext.create('Foss.baseinfo.AirlinesAgentUpdateWindow');
			this.airlinesAgentUpdateWindow.parent = this; //父元素
		}
		return this.airlinesAgentUpdateWindow;
	},
	//作废航空公司代理人
	toVoidAirlinesAgent : function (btn) {
		var me = this;
		var selections = me.getSelectionModel().getSelection(); //获取选中的数据
		if (selections.length < 1) { //判断是否至少选中了一条
			baseinfo.showWoringMessage(baseinfo.airlinesAgent.i18n('foss.baseinfo.selectVoidedOperation')); //请选择一条进行作废操作！
			return; //没有则提示并返回
		}
		baseinfo.showQuestionMes(baseinfo.airlinesAgent.i18n('foss.baseinfo.whetherToVoidTheseAirlinesAgent'), function (e) { //是否要作废这些航空公司代理人？
			if (e == 'yes') { //询问是否删除，是则发送请求
				var ids = new Array(); //航空公司代理人
				for (var i = 0; i < selections.length; i++) {
					ids.push(selections[i].get('airlinesAgent').id);
				}
				var params = {
					'airlinesAgentVo' : {
						'ids' : ids
					}
				};
				var successFun = function (json) {
					baseinfo.showInfoMes(json.message);
					me.getPagingToolbar().moveFirst();
				};
				var failureFun = function (json) {
					if (Ext.isEmpty(json)) {
						baseinfo.showErrorMes(baseinfo.airlinesAgent.i18n('foss.baseinfo.requestTimeout')); //请求超时
					} else {
						baseinfo.showErrorMes(json.message);
					}
				};
				var url = baseinfo.realPath('deleteAirlinesAgent.action');
				baseinfo.requestJsonAjax(url, params, successFun, failureFun);
			}
		})
		
	},
	constructor : function (config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.columns = [{
				xtype : 'rownumberer',
				width : 40,
				text : baseinfo.airlinesAgent.i18n('foss.baseinfo.noSequence') //序号
			}, {
				text : baseinfo.airlinesAgent.i18n('foss.baseinfo.operate'), //操作
				//dataIndex : 'id',
				xtype : 'actioncolumn',
				align : 'center',
				width : 80,
				items : [{
						iconCls : 'deppon_icons_edit',
						tooltip : baseinfo.airlinesAgent.i18n('foss.baseinfo.update'), //修改
						width : 42,
						disabled:!baseinfo.airlinesAgent.isPermission('airlinesAgent/airlinesAgentEditButton'),
						handler : function (grid, rowIndex, colIndex) {
							//获取选中的数据
							var record = grid.getStore().getAt(rowIndex);
							var id = record.get('airlinesAgent').id; //站点组虚拟编码
							var params = {
								'airlinesAgentVo' : {
									'airlinesAgentEntity' : {
										'id' : id
									}
								}
							};
							var successFun = function (json) {
								var updateWindow = me.getAirlinesAgentUpdateWindow(); //获得修改窗口
								updateWindow.airlinesAgentEntity = json.airlinesAgentVo.airlinesAgentEntity; //站点组
								updateWindow.show(); //显示修改窗口
							};
							var failureFun = function (json) {
								if (Ext.isEmpty(json)) {
									baseinfo.showErrorMes(baseinfo.airlinesAgent.i18n('foss.baseinfo.requestTimeout')); //请求超时
								} else {
									baseinfo.showErrorMes(json.message);
								}
							};
							var url = baseinfo.realPath('queryAirlinesAgent.action');
							baseinfo.requestJsonAjax(url, params, successFun, failureFun);
						}
					}, {
						iconCls : 'deppon_icons_cancel',
						tooltip : baseinfo.airlinesAgent.i18n('foss.baseinfo.void'), //作废
						width : 42,
						disabled :!baseinfo.airlinesAgent.isPermission('airlinesAgent/airlinesAgentVoidButton'),
						handler : function (grid, rowIndex, colIndex) {
							//获取选中的数据
							var record = grid.getStore().getAt(rowIndex);
							baseinfo.showQuestionMes(baseinfo.airlinesAgent.i18n('foss.baseinfo.whetherToVoidTheAirlineAgent'), function (e) { //是否要作废这个航空公司代理人？
								if (e == 'yes') { //询问是否删除，是则发送请求
									var ids = new Array(); //航空公司代理人
									ids.push(record.get('airlinesAgent').id);
									var params = {
										'airlinesAgentVo' : {
											'ids' : ids
										}
									};
									var successFun = function (json) {
										baseinfo.showInfoMes(json.message);
										me.getPagingToolbar().moveFirst();
									};
									var failureFun = function (json) {
										if (Ext.isEmpty(json)) {
											baseinfo.showErrorMes(baseinfo.airlinesAgent.i18n('foss.baseinfo.requestTimeout')); //请求超时
										} else {
											baseinfo.showErrorMes(json.message);
										}
									};
									var url = baseinfo.realPath('deleteAirlinesAgent.action');
									baseinfo.requestJsonAjax(url, params, successFun, failureFun);
								}
							})
						}
					}
				]
			}, {
				text : baseinfo.airlinesAgent.i18n('foss.baseinfo.stowageSector'), //配载部门
				dataIndex : 'dispatchDepartment',
				flex : 1
			}, {
				text : baseinfo.airlinesAgent.i18n('foss.baseinfo.airlineCode'), //外场名称
				dataIndex : 'airlinesAgent',
				renderer : function (value) {
					if (!Ext.isEmpty(value)) {
						return value.airlinesCode
					}
				},
				flex : 0.8
			}, {
				text : baseinfo.airlinesAgent.i18n('foss.baseinfo.nameOfTheAirline'), //航空公司名称
				dataIndex : 'airlinesName',
				flex : 1.2
			}, {
				text : baseinfo.airlinesAgent.i18n('foss.baseinfo.originationCity'), //始发城市
				dataIndex : 'originatingCity',
				flex : 0.8
			}, {
				text : baseinfo.airlinesAgent.i18n('foss.baseinfo.agentCoding'), //代理人编码
				dataIndex : 'airlinesAgent',
				renderer : function (value) {
					if (!Ext.isEmpty(value)) {
						return value.agentCode
					}
				},
				flex : 0.8
			}, {
				text : baseinfo.airlinesAgent.i18n('foss.baseinfo.agentName'), //代理人名称
				dataIndex : 'airlinesAgent',
				renderer : function (value) {
					if (!Ext.isEmpty(value)) {
						return value.agentName
					}
				},
				flex : 1.4
			}, {
				text : baseinfo.airlinesAgent.i18n('foss.baseinfo.isOutAgent'), //是否外部代理
				dataIndex : 'isOutAgent',
				flex : 0.8,
				renderer:function(value){
					if(!Ext.isEmpty(value)){
						if(value == baseinfo.yes){
							return value ='是';
						}if(value == baseinfo.no){
							return value ='否';
						}
					}
				
				}
			}
		];
		me.store = Ext.create('Foss.baseinfo.AirlinesAgentStore', {
				autoLoad : false, //不自动加载
				pageSize : 20,
				listeners : {
					beforeload : function (store, operation, eOpts) {
						var queryForm = me.up().getQueryAirlinesAgentForm();
						if (queryForm != null) {
							Ext.apply(operation, {
								params : { //航空公司代理人大查询，查询条件组织
									'airlinesAgentVo.airlinesAgentEntity.agentName' : queryForm.getForm().findField('agentName').getValue(), //代理人名称
									'airlinesAgentVo.airlinesAgentEntity.agentCode' : queryForm.getForm().findField('agentCode').getValue(), //代理人编码
									'airlinesAgentVo.airlinesAgentEntity.airlinesCode' : queryForm.getForm().findField('airlinesCode').getValue(), //航空公司CODE
									'airlinesAgentVo.airlinesAgentEntity.isOutAgent' : queryForm.getForm().findField('isOutAgent').getValue(), //配载部门ID
									'airlinesAgentVo.airlinesAgentEntity.assemblyDeptId' : queryForm.getForm().findField('assemblyDeptId').getValue() //配载部门ID
								}
							});
						}
					}
				}
			});
		me.listeners = {
			scrollershow : function (scroller) {
				if (scroller && scroller.scrollEl) {
					scroller.clearManagedListeners();
					scroller.mon(scroller.scrollEl, 'scroll', scroller.onElScroll, scroller);
				}
			}
		},
		me.selModel = Ext.create('Ext.selection.CheckboxModel', { //多选框
				mode : 'MULTI',
				checkOnly : true
			});
		me.tbar = [{
				text : baseinfo.airlinesAgent.i18n('foss.baseinfo.add'), //新增
				width : 80,
				disabled:!baseinfo.airlinesAgent.isPermission('airlinesAgent/airlinesAgentAddButton'),
				hidden:!baseinfo.airlinesAgent.isPermission('airlinesAgent/airlinesAgentAddButton'),
				handler : function () {
					me.getAirlinesAgentAddWindow().show();
				}
			}, '-', {
				text : baseinfo.airlinesAgent.i18n('foss.baseinfo.void'), //作废
				width : 80,
				disabled:!baseinfo.airlinesAgent.isPermission('airlinesAgent/airlinesAgentVoidButton'),
				hidden:!baseinfo.airlinesAgent.isPermission('airlinesAgent/airlinesAgentVoidButton'),
				handler : function () {
					me.toVoidAirlinesAgent();
				}
			}
		];
		me.bbar = me.getPagingToolbar();
		me.callParent([cfg]);
	}
});

/**
 * @description 航空公司代理人主页
 */
Ext.onReady(function () {
	Ext.QuickTips.init();
	if (Ext.getCmp('T_baseinfo-airlinesAgent_content')) {
		return;
	};
	var queryAirlinesAgentForm = Ext.create('Foss.baseinfo.QueryAirlinesAgentForm'); //查询FORM
	var airlinesAgentGrid = Ext.create('Foss.baseinfo.AirlinesAgentGrid'); //查询结果GRID
	Ext.getCmp('T_baseinfo-airlinesAgent').add(Ext.create('Ext.panel.Panel', {
		id : 'T_baseinfo-airlinesAgent_content',
		cls : 'panelContentNToolbar',
		bodyCls : 'panelContentNToolbar-body',
		//获得查询FORM
		getQueryAirlinesAgentForm : function () {
			return queryAirlinesAgentForm;
		},
		//获得查询结果GRID
		getAirlinesAgentGrid : function () {
			return airlinesAgentGrid;
		},
		items : [queryAirlinesAgentForm, airlinesAgentGrid]
	}));
});
//----------------------------------------------上面是整体布局，下面是弹出窗口----------------------------------
/**
 * 新增航空公司代理人信息
 */
Ext.define('Foss.baseinfo.AirlinesAgentAddWindow', {
	extend : 'Ext.window.Window',
	title : baseinfo.airlinesAgent.i18n('foss.baseinfo.newAirlineAgent'), //新增航空公司代理人
	closable : true,
	parent : null, //父元素（弹出这个window的gird——Foss.baseinfo.AirlinesAgentGrid）
	modal : true,
	resizable : false, //可以调整窗口的大小
	closeAction : 'hide', //点击关闭是隐藏窗口
	width : 570,
	height : 350,
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	listeners : {
		beforeshow : function (me) { //显示WINDOW的时候清除数据
			me.getAirlinesAgentForm().getForm().reset(); //表格重置
		}
	},
	//新增航空公司代理人FORM
	airlinesAgentForm : null,
	getAirlinesAgentForm : function () {
		if (Ext.isEmpty(this.airlinesAgentForm)) {
			this.airlinesAgentForm = Ext.create('Foss.baseinfo.AirlinesAgentForm');
		}
		return this.airlinesAgentForm;
	},
	//提交航空公司代理人数据
	commitAirlinesAgent : function () {
		var me = this;
		if (me.getAirlinesAgentForm().getForm().isValid()) { //校验form是否通过校验
			var airlinesAgentModel = new Foss.baseinfo.AirlinesAgentEntity();
			me.getAirlinesAgentForm().getForm().updateRecord(airlinesAgentModel); //将FORM中数据设置到MODEL里面
			var params = {
				'airlinesAgentVo' : {
					'airlinesAgentEntity' : airlinesAgentModel.data
				}
			}; //组织新增数据
			var successFun = function (json) {
				baseinfo.showInfoMes(json.message); //提示新增成功
				me.close();
				me.parent.getPagingToolbar().moveFirst(); //成功之后重新查询刷新结果集
			};
			var failureFun = function (json) {
				if (Ext.isEmpty(json)) {
					baseinfo.showErrorMes(baseinfo.airlinesAgent.i18n('foss.baseinfo.requestTimeout')); //请求超时
				} else {
					baseinfo.showErrorMes(json.message); //提示失败原因
				}
			};
			var url = baseinfo.realPath('addAirlinesAgent.action'); //请求航空公司代理人新增
			baseinfo.requestJsonAjax(url, params, successFun, failureFun); //发送AJAX请求
		} else {
			top.Ext.MessageBox.show({
				title : baseinfo.airlinesAgent.i18n('foss.baseinfo.failureInformationTips'),
				msg : (function () {
					var message = "<ul>";
					me.airlinesAgentForm.getForm().getFields().filterBy(function (value) {
						return value.getErrors().length > 0;
					}).each(function (item, index, length) {
						message += "<li>" + item.getErrors() + "</li>";
					});
					return message + "</ul>";
				})(),
				width : 350,
				buttons : Ext.Msg.OK,
				icon : Ext.MessageBox.WARNING
			});
		}
	},
	constructor : function (config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.fbar = [{
				text : baseinfo.airlinesAgent.i18n('foss.baseinfo.cancel'), //取消
				handler : function () {
					me.close();
				}
			}, {
				text : baseinfo.airlinesAgent.i18n('foss.baseinfo.reset'), //重置
				handler : function () {
					me.getAirlinesAgentForm().getForm().loadRecord(new Foss.baseinfo.AirlinesAgentEntity());
				}
			}, {
				text : baseinfo.airlinesAgent.i18n('foss.baseinfo.save'), //保存
				cls : 'yellow_button',
				margin : '0 0 0 305',
				handler : function () {
					me.commitAirlinesAgent();
				}
			}
		];
		me.items = [me.getAirlinesAgentForm()];
		me.callParent([cfg]);
	},
	operationUrl : 'add'
});
/**
 * 修改航空公司代理人
 */
Ext.define('Foss.baseinfo.AirlinesAgentUpdateWindow', {
	extend : 'Ext.window.Window',
	title : baseinfo.airlinesAgent.i18n('foss.baseinfo.modifyAirlinesAgent'), //修改航空公司代理人
	closable : true,
	modal : true,
	resizable : false,
	airlinesAgentEntity : null, //修改航空公司代理人数据
	parent : null, //父元素（弹出这个window的Foss.baseinfo.AirlinesAgentGrid）
	closeAction : 'hide',
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	width : 570,
	height : 350,
	listeners : {
		beforehide : function (me) {
			me.getAirlinesAgentForm().getForm().reset(); //表格重置
		},
		beforeshow : function (me) {
			if (!Ext.isEmpty(me.airlinesAgentEntity)) {
				var model = new Foss.baseinfo.AirlinesAgentEntity(me.airlinesAgentEntity);
				me.getAirlinesAgentForm().getForm().loadRecord(model);
			}
		}
	},
	//修改航空公司代理人FORM
	airlinesAgentForm : null,
	getAirlinesAgentForm : function () {
		if (Ext.isEmpty(this.airlinesAgentForm)) {
			this.airlinesAgentForm = Ext.create('Foss.baseinfo.AirlinesAgentForm');
		}
		return this.airlinesAgentForm;
	},
	//修改航空公司代理人
	commitAirlinesAgent : function () {
		var me = this;
		if (me.getAirlinesAgentForm().getForm().isValid()) { //校验form是否通过校验
			var airlinesAgentModel = new Foss.baseinfo.AirlinesAgentEntity(me.airlinesAgentEntity);
			me.getAirlinesAgentForm().getForm().updateRecord(airlinesAgentModel); //将FORM中数据设置到MODEL里面
			var params = {
				'airlinesAgentVo' : {
					'airlinesAgentEntity' : airlinesAgentModel.data
				}
			}; //组织新增数据
			var successFun = function (json) {
				baseinfo.showInfoMes(json.message); //提示新增成功
				me.close();
				me.parent.getPagingToolbar().moveFirst(); //成功之后重新查询刷新结果集
			};
			var failureFun = function (json) {
				if (Ext.isEmpty(json)) {
					baseinfo.showErrorMes(baseinfo.airlinesAgent.i18n('foss.baseinfo.requestTimeout')); //请求超时
				} else {
					baseinfo.showErrorMes(json.message); //提示失败原因
				}
			};
			var url = baseinfo.realPath('updateAirlinesAgent.action'); //请求航空公司代理人新增
			baseinfo.requestJsonAjax(url, params, successFun, failureFun); //发送AJAX请求
		}
	},
	constructor : function (config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.fbar = [{
				text : baseinfo.airlinesAgent.i18n('foss.baseinfo.cancel'), //取消
				handler : function () {
					me.close();
				}
			}, {
				text : baseinfo.airlinesAgent.i18n('foss.baseinfo.reset'), //重置
				handler : function () {
					me.getAirlinesAgentForm().getForm().loadRecord(new Foss.baseinfo.AirlinesAgentEntity(me.airlinesAgentEntity));
				}
			}, {
				text : baseinfo.airlinesAgent.i18n('foss.baseinfo.save'), //保存
				cls : 'yellow_button',
				margin : '0 0 0 305',
				handler : function () {
					me.commitAirlinesAgent();
				}
			}
		];
		me.items = [me.getAirlinesAgentForm()];
		me.callParent([cfg]);
	},
	operationUrl : 'upd'
});
/**
 * 航空公司代理人组-FORM
 */
Ext.define('Foss.baseinfo.AirlinesAgentForm', {
	extend : 'Ext.form.Panel',
	title : baseinfo.airlinesAgent.i18n('foss.baseinfo.airlineAgentInformation'), //航空公司代理人信息
	frame : true,
	flex : 1,
	collapsible : true,
	defaults : {
		margin : '5 5 5 5',
		labelWidth : 90,
		//width:200,
		colspan : 1
	},
	layout : {
		type : 'table',
		columns : 2
	},
	constructor : function (config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.items = [{
			//师锦涛--改动新增航空代理人页面 配载部门可编辑，选择查询。
			name : 'assemblyDeptId',
			colspan:1,
			fieldLabel : baseinfo.airlinesAgent.i18n('foss.baseinfo.stowageSector'), //配载部门
			xtype : 'dynamicorgcombselector',
			editable : true,
			doAirDispatch : 'Y',
			//type : 'ORG',
			listeners:{
				select : function (me, records, eOpts) {
				if (Ext.isEmpty(records.length <= 0)) {
					me.setValue(null);
					Ext.MessageBox.show({
						title : baseinfo.airlinesAgent.i18n('foss.baseinfo.failureInformationTips'),
						msg : baseinfo.airlinesAgent.i18n('foss.baseinfo.warningStowageSectorDataErrorMessage'),
						width : 350,
						buttons : Ext.Msg.OK,
						icon : Ext.MessageBox.WARNING
					});
					return;
				} else {
					if (Ext.isEmpty(records)) {
						return;
					} else {
						var cityCode = records[0].data.cityCode;
						if (Ext.isEmpty(cityCode)) {
							me.setValue(null);
							Ext.MessageBox.show({
								title : baseinfo.airlinesAgent.i18n('foss.baseinfo.failureInformationTips'),
								msg : baseinfo.airlinesAgent.i18n('foss.baseinfo.warningStowageSectorDataErrorMessage'),
								width : 350,
								buttons : Ext.Msg.OK,
								icon : Ext.MessageBox.WARNING
							});
							return;
						} else {
							//查询所在城市
							Ext.Ajax.request({
								url : baseinfo.realPath('queryAdministrativeRegionsByCode.action'),
								jsonData : {
									'administrativeRegionsVo' : {
										'administrativeRegionsDetail' : {
											'code' : cityCode
										}
									}
								},
								success : function (response) {
									var json = Ext.decode(response.responseText);
									if (!Ext.isEmpty(json.administrativeRegionsVo)) {
										var administrativeRegionsDetail = json.administrativeRegionsVo.administrativeRegionsDetail;
										if (!Ext.isEmpty(administrativeRegionsDetail)) {
											var originatingCity = me.up('form').getForm().findField('originatingCity');
											if (!Ext.isEmpty(originatingCity)) {
												originatingCity.setValue(administrativeRegionsDetail.name);
											}
											originatingCity = null;
										} else {
											me.setValue(null);
											Ext.MessageBox.show({
												title : baseinfo.airlinesAgent.i18n('foss.baseinfo.failureInformationTips'),
												msg : baseinfo.airlinesAgent.i18n('foss.baseinfo.warningStowageSectorDataErrorMessage'),
												width : 350,
												buttons : Ext.Msg.OK,
												icon : Ext.MessageBox.WARNING
											});
										}
										administrativeRegionsDetail = null;
									}
									json = null;
								},
								exception : function (response) {
									var json = Ext.decode(response.responseText);
									Ext.MessageBox.show({
										title : baseinfo.airlinesAgent.i18n('foss.baseinfo.failureInformationTips'),
										msg : json.message,
										width : 300,
										buttons : Ext.Msg.OK,
										icon : Ext.MessageBox.WARNING
									});
								}
							});
						}
					}
				}
			},
			}
		}, {
				name : 'originatingCity', //始发城市
				readOnly : true,
				fieldLabel : baseinfo.airlinesAgent.i18n('foss.baseinfo.originationCity'),
				xtype : 'textfield'
			}, {
				name : 'airlinesCode', //航空公司代码
				allowBlank : false,
				fieldLabel : baseinfo.airlinesAgent.i18n('foss.baseinfo.airlineCode'),
				xtype : 'commonairlinesselector',
				editable : true,
				displayField : 'code',
				
				listeners : {
					change : function (me, newValue, oldValue) {
						me.changes = function (me, newValue, oldValue) {
							var airlinesName = me.findRecordByValue(me.getValue());
							if (!Ext.isBoolean(airlinesName)) {
								if (!Ext.isEmpty(airlinesName)) {
									me.up('form').getForm().findField('airlinesName').setValue(airlinesName.get('name'));
								}
							}
							airlinesName = null;
						}
						if (me.getStore().data.length <= 0) {
							me.getStore().load({
								callback : function (records, operation, success) {
									me.changes(me, newValue, oldValue);
								}
							});
						} else {
							me.changes(me, newValue, oldValue);
						}
					},
				}
			}, {
				name : 'airlinesName', //航空公司名称
				readOnly : true,
				fieldLabel : baseinfo.airlinesAgent.i18n('foss.baseinfo.nameOfTheAirline'),
				xtype : 'textfield'
			}, {
				name : 'agentCode', //代理人编码
				allowBlank : false,
				fieldLabel : baseinfo.airlinesAgent.i18n('foss.baseinfo.agentCoding'),
				xtype : 'textfield',
				blankText : baseinfo.airlinesAgent.i18n('foss.baseinfo.agentCodingCanNotBeEmpty'),
				maxLength : 20,
				validateOnBlur : true,
				validateOnChange : true,
				validator : function (val) {
					var me = this;
					if (Ext.isEmpty(val)) {
						me.validatorMessage.message = me.blankText;
						return true;
					} else {
						if (!Ext.isEmpty(me.validatorMessage.validity) && me.validatorMessage.validity) {
							return me.validatorMessage.validity;
						} else {
							return me.validatorMessage.message;
						}
					}
				},
				validatorMessage : {
					validity : true,
					message : '',
					value : null
				},
				listeners : {
					focus : function (me, event, eOpts) {
						if (Ext.isEmpty(me.getValue())) {
							return;
						} else {
							me.validatorMessage.value = Ext.String.trim(me.getValue().toString()); ;
						}
					},
					/**
					 * 模拟选择器：失去焦点获取外请厢式车信息
					 * @param {Ext.Component} me 当前对象
					 * @param {Ext.EventObject} event 当前触发事件对象
					 */
					blur : function (me, event) {
						//临时参数
						var isBreak = false;
						var agentCode = null;
						if (Ext.isEmpty(me.getValue())) {
							isBreak = true; ;
						} else {
							agentCode = Ext.String.trim(me.getValue().toString());
							if (Ext.isEmpty(agentCode) || me.readOnly) {
								me.setValue(null);
								isBreak = true;
							} else {
								if (Ext.isEmpty(me.validatorMessage.value)) {
									me.validatorMessage.value = agentCode;
								} else {
									if (me.validatorMessage.value === agentCode) {
										isBreak = true;
									} else {
										me.validatorMessage.value = agentCode;
									}
								}
							}
						}
						//不存在baseinfo.airlinesAgent.i18n('foss.baseinfo.airlineCode')
						var airlinesCode = me.up('form').getForm().findField('airlinesCode');
						if (Ext.isEmpty(airlinesCode) || Ext.isEmpty(airlinesCode.getValue())) {
							isBreak = true;
						}
						//判断是否验证是否需要继续
						if (isBreak) {
							return;
						}
						//执行验证
						Ext.Ajax.request({
							url : baseinfo.realPath('queryAirlinesAgent.action'),
							jsonData : {
								'airlinesAgentVo' : {
									'airlinesAgentEntity' : {
										'agentCode' : agentCode,
										'airlinesCode' : airlinesCode.getValue()
									}
								}
							},
							success : function (response) {
								//检测baseinfo.airlinesAgent.i18n('foss.baseinfo.LToperatingCardNumber')是否已经存在
								var json = Ext.decode(response.responseText);
								var airlinesAgent = json.airlinesAgentVo.airlinesAgentEntity;
								//数据"已存在"
								if (Ext.isEmpty(airlinesAgent)) {
									me.validatorMessage.validity = true;
								} else {
									//初始化：错误数据
									me.validatorMessage.validity = false;
									me.validatorMessage.message = baseinfo.airlinesAgent.i18n('foss.baseinfo.theAgentCodeAirlineCodeAlreadyExists');
									/*
									 * "修改动作"的特殊验证逻辑
									 */
									if (me.up('window').operationUrl === 'upd') {
										var formModel = me.up('window').getAddUpdateFormModel();
										if (!Ext.isEmpty(formModel)) {
											if (airlinesAgent.id.toString() === formModel.get('id').toString()) {
												me.validatorMessage.validity = true;
												me.validatorMessage.message = null;
											}
										}
										formModel = null;
									}
								}
								/*
								 * 错误信息提示方式
								 */
								if (Ext.isBoolean(me.validatorMessage.validity) && me.validatorMessage.validity) {
									me.validatorMessage.validity = true;
									me.validatorMessage.message = null;
									me.clearInvalid();
								} else {
									me.validatorMessage.validity = false;
									me.markInvalid(me.validatorMessage.message);
									Ext.MessageBox.show({
										title : baseinfo.airlinesAgent.i18n('foss.baseinfo.failureInformationTips'),
										msg : me.validatorMessage.message,
										width : 350,
										buttons : Ext.Msg.OK,
										icon : Ext.MessageBox.WARNING
									});
								}
								json = null;
								airlinesAgent = null;
								me.validatorMessage.value = null;
							},
							exception : function (response) {
								var json = Ext.decode(response.responseText);
								me.validatorMessage.validity = false;
								me.validatorMessage.message = json.message;
								me.validatorMessage.value = null;
								Ext.MessageBox.show({
									title : baseinfo.airlinesAgent.i18n('foss.baseinfo.failureInformationTips'),
									msg : json.message,
									width : 300,
									buttons : Ext.Msg.OK,
									icon : Ext.MessageBox.WARNING
								});
							}
						});
						airlinesCode = null;
						agentCode = null;
					}
				}
			}, {
				name : 'agentName', //代理人名称
				allowBlank : false,
				fieldLabel : baseinfo.airlinesAgent.i18n('foss.baseinfo.agentName'),
				xtype : 'textfield',
				blankText : baseinfo.airlinesAgent.i18n('foss.baseinfo.agentNameCanNotBeEmtpy')
			},{
			     xtype: 'radiogroup',
			     name : 'isOutAgent',
			     fieldLabel: baseinfo.airlinesAgent.i18n('foss.baseinfo.isOutAgent'),//是否外部代理
				 allowBlank:false,
				 height: 24,
				 columnWidth:.4,
				 items: [
				    {boxLabel: baseinfo.airlinesAgent.i18n('foss.baseinfo.yes'), name: 'isOutAgent', inputValue: 'Y'},
					{boxLabel: baseinfo.airlinesAgent.i18n('foss.baseinfo.no'), name: 'isOutAgent', inputValue: 'N', checked: true}
				  ]
				}
		];
		me.callParent([cfg]);
	}
});



