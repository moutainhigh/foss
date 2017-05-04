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
 * ADD -ALL
 */
baseinfo.addAll = function (store, all) {
	if (!Ext.isEmpty(store)) {
		store.add(all);
	}
	return store;
};
/*
 * Ajax请求--json
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

//--------------------------------------baseinfo----------------------------------------
/*
 * 库区MODEL
 */
Ext.define('Foss.baseinfo.expressPrintStar.ExpressPrintStarEntity', {
	extend : 'Ext.data.Model',
	fields : [{
			name : 'id',
			type : 'string'
		}, {
			name : 'organizationCode', // 组织
			type : 'string'
		}, {
			name : 'organizationName', // 组织名称
			type : 'string'
		},{
	        name : 'transferCode',//外场编码
	        type : 'string'
	    }, {
			name : 'goodsAreaCode', // 库区编码
			type : 'string'
		}, {
			name : 'virtualCode', // 虚拟编码
			type : 'string'
		}, {
			name : 'goodsAreaName', // 库区名称
			type : 'string'
		}, {
			name : 'goodsAreaType', // 库区类型(卡货库区、普货库区、城际快车库区,混装库区和偏线库区等,贵重物品，待包装等)
			type : 'string'
		}, {
			name : 'goodsType', // 货物类型（A货，B货）
			type : 'string'
		}, {
			name : 'arriveRegionCode', // 目的站
			type : 'string'
		}, {
			name : 'arriveRegionName', // 目的站名称
			type : 'string'
		}, {
			name : 'goodsAreaUsage', // 库区类别（长途，短途）
			type : 'string'
		}, {
			name : 'active', // 是否有效
			type : 'string'
		},{
	        name : 'transferCode',//外场编码
	        type : 'string'
	    },{
	        name : 'asteriskCode',//星标编码
	        type : 'string'
	    }
	]
});

baseinfo.expressPrintStar.goodsAreaType = 'BSE_GOODSAREA_TYPE'; //库区类型   
baseinfo.expressPrintStar.goodsType = 'BSE_GOODS_TYPE'; //货物类型
baseinfo.expressPrintStar.goodsAreaUsage = 'BSE_GOODSAREA_USAGE'; //库区类别
baseinfo.expressPrintStar.asteriskType = 'ASTERISK_TYPE'; //星标类型

baseinfo.expressPrintStar.goodsAreaTypeHZ = 'BSE_GOODSAREA_TYPE_COMMON';//库区类型的 混装库区
baseinfo.expressPrintStar.goodsAreaTypeKH = 'BSE_GOODSAREA_TYPE_FAST';//库区类型的 卡货库区
baseinfo.expressPrintStar.goodsAreaTypePH = 'BSE_GOODSAREA_TYPE_NORMAL';//库区类型的 普货库区
baseinfo.expressPrintStar.goodsAreaTypePX ='BSE_GOODSAREA_TYPE_OTHER'; //库区类型的偏线库区
//------------------------------------model---------------------------------------------------
/**
 * 库区的Store（Foss.baseinfo.expressPrintStar.ExpressPrintStarEntity）
 */
Ext.define('Foss.baseinfo.expressPrintStar.ExpressPrintStarStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.baseinfo.expressPrintStar.ExpressPrintStarEntity', //库区的MODEL
	pageSize : 20,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url : baseinfo.realPath('queryExpressPrintStarByCondition.action'), //请求地址
		reader : {
			type : 'json',
			root : 'expressPrintStarVo.expressPrintStarEntityList', //获取的数据
			totalProperty : 'totalCount' //总个数
		}
	}
});

//----------------------------------------store---------------------------------

/**
 * 库区表单
 */
Ext.define('Foss.baseinfo.expressPrintStar.QueryExpressPrintStarForm', {
	extend : 'Ext.form.Panel',
	title : baseinfo.expressPrintStar.i18n('foss.baseinfo.informationQueryInTheReservoirArea'), //库区信息查询
	frame : true,
	collapsible : true,
	defaults : {
		margin : '8 10 5 10',
		anchor : '100%'
	},
	height : 200,
	defaultType : 'textfield',
	layout : 'column',
	constructor : function (config) {
		var me = this,
		cfg = Ext.apply({}, config);
		var all = {
			'valueName' : '全部',
			'valueCode' : ''
		};
		var goodsAreaTypeStore = baseinfo.addAll(FossDataDictionary.getDataDictionaryStore(baseinfo.expressPrintStar.goodsAreaType), all);
		var goodsAreaUsageStore = baseinfo.addAll(FossDataDictionary.getDataDictionaryStore(baseinfo.expressPrintStar.goodsAreaUsage), all);
		var goodsTypeStore = baseinfo.addAll(FossDataDictionary.getDataDictionaryStore(baseinfo.expressPrintStar.goodsType), all);
		var asteriskTypeStore = baseinfo.addAll(FossDataDictionary.getDataDictionaryStore(baseinfo.expressPrintStar.asteriskType), all);
		me.items = [{
			    xtype : 'textfield',
				name : 'organizationName',
				organizationCode:'D16',//组织编码
				fieldLabel : baseinfo.expressPrintStar.i18n('foss.baseinfo.outfield'),//外场
				columnWidth:0.33,
				readOnly : true,
				value:'青岛转运中心',
				listeners:{
					select:function(comb,records,empo){
							comb.organizationCode = records[0].get('orgCode');
					}
				}
			}, {
				name : 'goodsAreaName',
				fieldLabel : baseinfo.expressPrintStar.i18n('foss.baseinfo.reservoirAreaName'), //库区名称
				xtype : 'textfield',
				columnWidth:0.33,
			}, {
				name : 'arriveRegionCode', //目的站
				fieldLabel : '目的站',
				xtype : 'dynamicorgcombselector',
				type : 'ORG',
				salesDepartment : 'Y',//营业部
				transferCenter : 'Y',//外场
				airDispatch:'Y',//空运总调
				columnWidth:0.33,
			}, {
				name : 'goodsAreaType',
				queryMode : 'local',
				displayField : 'valueName',
				valueField : 'valueCode',
				editable : false,
				value : '',
				columnWidth:0.33,
				store : goodsAreaTypeStore,
				fieldLabel : baseinfo.expressPrintStar.i18n('foss.baseinfo.reservoirAreaType'), //库区类型
				xtype : 'combo'
			}, {
				name : 'goodsType',
				queryMode : 'local',
				displayField : 'valueName',
				valueField : 'valueCode',
				editable : false,
				value : '',
				columnWidth:0.33,
				store : goodsTypeStore,
				fieldLabel : baseinfo.expressPrintStar.i18n('foss.baseinfo.cargoType'), //货物类型
				xtype : 'combo'
			}, {
				name : 'goodsAreaUsage',
				queryMode : 'local',
				displayField : 'valueName',
				valueField : 'valueCode',
				editable : false,
				value : '',
				columnWidth:0.33,
				store : goodsAreaUsageStore,
				fieldLabel : baseinfo.expressPrintStar.i18n('foss.baseinfo.reservoirAreaCategory'), //库区类别
				xtype : 'combo'
			},{
				border: 1,
				xtype:'container',
				columnWidth:1, 
				defaultType:'button',
				layout:'column',
				items:[{
					xtype : 'button',
					width : 70,
					text : baseinfo.expressPrintStar.i18n('foss.baseinfo.reset'), //重置
					disabled:!baseinfo.expressPrintStar.isPermission('expressPrintStar/expressPrintStarQueryButton'),
					hidden:!baseinfo.expressPrintStar.isPermission('expressPrintStar/expressPrintStarQueryButton'),
					columnWidth:.08,
					handler : function () {
						me.getForm().reset();
						//me.getForm().findField('organizationCode').organizationCode = null;
					}
				}, {
					xtype: 'container',
					html:'&nbsp;',
					columnWidth:.84,
				},{
					xtype : 'button',
					width : 70,
					cls : 'yellow_button',
					text : baseinfo.expressPrintStar.i18n('foss.baseinfo.query'), //查询
					disabled:!baseinfo.expressPrintStar.isPermission('expressPrintStar/expressPrintStarQueryButton'),
					hidden:!baseinfo.expressPrintStar.isPermission('expressPrintStar/expressPrintStarQueryButton'),
					columnWidth:.08,
					handler : function () {
						if (me.getForm().isValid()) {
								me.up().getExpressPrintStarGrid().getPagingToolbar().moveFirst();
						}
					}
				}]
			}
		],
		me.callParent([cfg]);
	}
});
/**
 * 库区列表
 */
Ext.define('Foss.baseinfo.expressPrintStar.ExpressPrintStarGrid', {
	extend : 'Ext.grid.Panel',
	title : baseinfo.expressPrintStar.i18n('foss.baseinfo.reservoirAreaInformation'), //库区信息
	frame : true,
	flex : 1,
	sortableColumns : false,
	enableColumnHide : false,
	enableColumnMove : false,
	stripeRows : true, // 交替行效果
	selType : "rowmodel", // 选择类型设置为：行选择
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
	//库区新增WINDOW
	expressPrintStarAddWindow : null,
	getExpressPrintStarAddWindow : function () {
		if (this.expressPrintStarAddWindow == null) {
			this.expressPrintStarAddWindow = Ext.create('Foss.baseinfo.expressPrintStar.ExpressPrintStarAddWindow');
			this.expressPrintStarAddWindow.parent = this; //父元素
		}
		return this.expressPrintStarAddWindow;
	},
	//修改库区WINDOW
	expressPrintStarUpdateWindow : null,
	getExpressPrintStarUpdateWindow : function () {
		if (this.expressPrintStarUpdateWindow == null) {
			this.expressPrintStarUpdateWindow = Ext.create('Foss.baseinfo.expressPrintStar.ExpressPrintStarUpdateWindow');
			this.expressPrintStarUpdateWindow.parent = this; //父元素
		}
		return this.expressPrintStarUpdateWindow;
	},
	//查看库区WINDOW
	expressPrintStarShowWindow : null,
	getExpressPrintStarShowWindow : function () {
		if (this.expressPrintStarShowWindow == null) {
			this.expressPrintStarShowWindow = Ext.create('Foss.baseinfo.expressPrintStar.ExpressPrintStarShowWindow');
			this.expressPrintStarShowWindow.parent = this; //父元素
		}
		this.expressPrintStarShowWindow.getExpressPrintStarForm().getForm().getFields().each(function (item) {
			item.setReadOnly(true);
		});
		return this.expressPrintStarShowWindow;
	},
	//作废库区
	toVoidExpressPrintStar : function (btn) {
		var me = this;
		var selections = me.getSelectionModel().getSelection(); //获取选中的数据
		if (selections.length < 1) { //判断是否至少选中了一条
			baseinfo.showWoringMessage('请至少选择一条进行作废操作！'); //请选择一条进行作废操作！
			return; //没有则提示并返回
		}
		baseinfo.showQuestionMes(baseinfo.expressPrintStar.i18n('foss.baseinfo.wantSetAsideTheseReservoirArea'), function (e) { //是否要作废这些库区？
			if (e == 'yes') { //询问是否删除，是则发送请求
				var expressPrintStarVirtualCodes = new Array; //库区ID数组
				for (var i = 0; i < selections.length; i++) {
					expressPrintStarVirtualCodes.push(selections[i].get('virtualCode'));
				}
				var params = {
					'expressPrintStarVo' : {
						'expressPrintStarVirtualCodes' : expressPrintStarVirtualCodes
					}
				};
				var successFun = function (json) {
					baseinfo.showInfoMes(json.message);
					me.getPagingToolbar().moveFirst();
				};
				var failureFun = function (json) {
					if (Ext.isEmpty(json)) {
						baseinfo.showErrorMes(baseinfo.expressPrintStar.i18n('foss.baseinfo.requestTimeout')); //请求超时
					} else {
						baseinfo.showErrorMes(json.message);
					}
				};
				var url = baseinfo.realPath('deleteExpressPrintStar.action');
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
				text : '序号' //序号
			}, {
				text : baseinfo.expressPrintStar.i18n('foss.baseinfo.operate'), //操作
				//dataIndex : 'id',
				xtype : 'actioncolumn',
				align : 'center',
				width : 80,
				items : [{
						iconCls : 'deppon_icons_edit',
						tooltip : baseinfo.expressPrintStar.i18n('foss.baseinfo.update'), //修改
						disabled:!baseinfo.expressPrintStar.isPermission('expressPrintStar/expressPrintStarUpdateButton'),
						width : 42,
						handler : function (grid, rowIndex, colIndex) {
							//获取选中的数据
							var record = grid.getStore().getAt(rowIndex);
							var virtualCode = record.get('virtualCode'); //库区虚拟编码
							var params = {
								'expressPrintStarVo' : {
									'expressPrintStarEntity' : {
										'virtualCode' : virtualCode
									}
								}
							};
							var successFun = function (json) {
								var updateWindow = me.getExpressPrintStarUpdateWindow(); //获得修改窗口
								updateWindow.expressPrintStarEntity = json.expressPrintStarVo.expressPrintStarEntity; //库区
								updateWindow.show(); //显示修改窗口
							};
							var failureFun = function (json) {
								if (Ext.isEmpty(json)) {
									baseinfo.showErrorMes(baseinfo.expressPrintStar.i18n('foss.baseinfo.requestTimeout')); //请求超时
								} else {
									baseinfo.showErrorMes(json.message);
								}
							};
							var url = baseinfo.realPath('queryExpressPrintStarByVirtualCode.action');
							baseinfo.requestJsonAjax(url, params, successFun, failureFun);
						}
					}, {
						iconCls : 'deppon_icons_cancel',
						tooltip : baseinfo.expressPrintStar.i18n('foss.baseinfo.void'), //作废
						disabled:!baseinfo.expressPrintStar.isPermission('expressPrintStar/expressPrintStarVoidButton'),
						width : 42,
						handler : function (grid, rowIndex, colIndex) {
							//获取选中的数据
							var record = grid.getStore().getAt(rowIndex);
							baseinfo.showQuestionMes(baseinfo.expressPrintStar.i18n('foss.baseinfo.wantVoidReservoirArea'), function (e) { //是否要作废这个库区？
								if (e == 'yes') { //询问是否删除，是则发送请求
									var expressPrintStarVirtualCodes = new Array; //库区ID数组
									expressPrintStarVirtualCodes.push(record.get('virtualCode'));
									var params = {
										'expressPrintStarVo' : {
											'expressPrintStarVirtualCodes' : expressPrintStarVirtualCodes
										}
									};
									var successFun = function (json) {
										baseinfo.showInfoMes(json.message);
										me.getPagingToolbar().moveFirst();
									};
									var failureFun = function (json) {
										if (Ext.isEmpty(json)) {
											baseinfo.showErrorMes(baseinfo.expressPrintStar.i18n('foss.baseinfo.requestTimeout')); //请求超时
										} else {
											baseinfo.showErrorMes(json.message);
										}
									};
									var url = baseinfo.realPath('deleteExpressPrintStar.action');
									baseinfo.requestJsonAjax(url, params, successFun, failureFun);
								}
							})
						}
					}, {
						iconCls : 'deppon_icons_showdetail',
						tooltip : baseinfo.expressPrintStar.i18n('foss.baseinfo.details'), //查看详情
						width : 42,
						handler : function (grid, rowIndex, colIndex) {
							//获取选中的数据
							var record = grid.getStore().getAt(rowIndex);
							var virtualCode = record.get('virtualCode'); //库区虚拟编码
							var params = {
								'expressPrintStarVo' : {
									'expressPrintStarEntity' : {
										'virtualCode' : virtualCode
									}
								}
							};
							var successFun = function (json) {
								var showWindow = me.getExpressPrintStarShowWindow(); //获得修改窗口
								showWindow.expressPrintStarEntity = json.expressPrintStarVo.expressPrintStarEntity; //库区
								showWindow.show(); //显示修改窗口
							};
							var failureFun = function (json) {
								if (Ext.isEmpty(json)) {
									baseinfo.showErrorMes(baseinfo.expressPrintStar.i18n('foss.baseinfo.requestTimeout')); //请求超时
								} else {
									baseinfo.showErrorMes(json.message);
								}
							};
							var url = baseinfo.realPath('queryExpressPrintStarByVirtualCode.action');
							baseinfo.requestJsonAjax(url, params, successFun, failureFun);
							
						}
					}
				]
			}, {
				text : baseinfo.expressPrintStar.i18n('foss.baseinfo.fieldID'), //外场编号
				dataIndex : 'transferCode'
			}, {
				text : baseinfo.expressPrintStar.i18n('foss.baseinfo.fieldName'), //外场名称
				dataIndex : 'organizationName'
			}, {
				text : baseinfo.expressPrintStar.i18n('foss.baseinfo.reservoirAreaNumber'), //库区编号
				dataIndex : 'goodsAreaCode'
			}, {
				text : baseinfo.expressPrintStar.i18n('foss.baseinfo.reservoirAreaName'), //库区名称
				dataIndex : 'goodsAreaName'
			}, {
				text : baseinfo.expressPrintStar.i18n('foss.baseinfo.reservoirAreaType'), //库区类型
				dataIndex : 'goodsAreaType',
				renderer : function (value) {
					return baseinfo.changeCodeToNameStore(FossDataDictionary.getDataDictionaryStore(baseinfo.expressPrintStar.goodsAreaType), value);
				}
			}, {
				text : baseinfo.expressPrintStar.i18n('foss.baseinfo.cargoType'), //货物类型
				dataIndex : 'goodsType',
				renderer : function (value) {
					return baseinfo.changeCodeToNameStore(FossDataDictionary.getDataDictionaryStore(baseinfo.expressPrintStar.goodsType), value);
				}
			}, {
				text : '目的站', //目的站
				dataIndex : 'arriveRegionName'
			}, {
				text : baseinfo.expressPrintStar.i18n('foss.baseinfo.reservoirAreaCategory'), //库区类别
				dataIndex : 'goodsAreaUsage',
				renderer : function (value) {
					return baseinfo.changeCodeToNameStore(FossDataDictionary.getDataDictionaryStore(baseinfo.expressPrintStar.goodsAreaUsage), value);
				}
			}, {
				text : baseinfo.expressPrintStar.i18n('foss.baseinfo.asterisk'), //星标
				dataIndex : 'asteriskCode',
				renderer : function (value) {
					if(value=='N'){
						return baseinfo.expressPrintStar.i18n('foss.baseinfo.null');//无
					}
					return baseinfo.changeCodeToNameStore(FossDataDictionary.getDataDictionaryStore(baseinfo.expressPrintStar.asteriskType), value);
				}
			}
		];
		me.store = Ext.create('Foss.baseinfo.expressPrintStar.ExpressPrintStarStore', {
				autoLoad : false, //不自动加载
				pageSize : 20,
				listeners : {
					beforeload : function (store, operation, eOpts) {
						var queryForm = me.up().getQueryExpressPrintStarForm();
						if (queryForm != null) {
							Ext.apply(operation, {
								params : { //查询站点组大查询，查询条件组织
									'expressPrintStarVo.expressPrintStarEntity.goodsAreaUsage' : queryForm.getForm().findField('goodsAreaUsage').getValue(), //库区类别
									'expressPrintStarVo.expressPrintStarEntity.goodsType' : queryForm.getForm().findField('goodsType').getValue(), //货物类型
									'expressPrintStarVo.expressPrintStarEntity.goodsAreaType' : queryForm.getForm().findField('goodsAreaType').getValue(), //库区类型
									'expressPrintStarVo.expressPrintStarEntity.arriveRegionCode' : queryForm.getForm().findField('arriveRegionCode').getValue(), //目的站
									'expressPrintStarVo.expressPrintStarEntity.organizationCode' : 'D16', //外场queryForm.getForm().findField('organizationCode').organizationCode
									'expressPrintStarVo.expressPrintStarEntity.goodsAreaName' : queryForm.getForm().findField('goodsAreaName').getValue(), //库区名称
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
			xtype : 'button',
			text : baseinfo.expressPrintStar.i18n('foss.baseinfo.add'), //新增
			disabled:!baseinfo.expressPrintStar.isPermission('expressPrintStar/expressPrintStarAddButton'),
			hidden:!baseinfo.expressPrintStar.isPermission('expressPrintStar/expressPrintStarAddButton'),
			handler : function () {
				me.getExpressPrintStarAddWindow().show();
			}
		}, '-',{
				text : baseinfo.expressPrintStar.i18n('foss.baseinfo.void'), //作废
				disabled:!baseinfo.expressPrintStar.isPermission('expressPrintStar/expressPrintStarVoidButton'),
				hidden:!baseinfo.expressPrintStar.isPermission('expressPrintStar/expressPrintStarVoidButton'),
				handler : function () {
					me.toVoidExpressPrintStar();
				}
			} 
		];
		me.bbar = me.getPagingToolbar();
		me.callParent([cfg]);
	}
});

/**
 * @description 站点组主页
 */
Ext.onReady(function () {
	Ext.QuickTips.init();
	if (Ext.getCmp('T_baseinfo-expressPrintStar_content')) {
		return;
	};
	var queryExpressPrintStarForm = Ext.create('Foss.baseinfo.expressPrintStar.QueryExpressPrintStarForm'); //查询FORM
	var expressPrintStarGrid = Ext.create('Foss.baseinfo.expressPrintStar.ExpressPrintStarGrid'); //查询结果GRID
	Ext.getCmp('T_baseinfo-expressPrintStar').add(Ext.create('Ext.panel.Panel', {
		id : 'T_baseinfo-expressPrintStar_content',
		cls : 'panelContentNToolbar',
		bodyCls : 'panelContentNToolbar-body',
		//获得查询FORM
		getQueryExpressPrintStarForm : function () {
			return queryExpressPrintStarForm;
		},
		//获得查询结果GRID
		getExpressPrintStarGrid : function () {
			return expressPrintStarGrid;
		},
		items : [queryExpressPrintStarForm, expressPrintStarGrid]  
	}));
});
//----------------------------------------------上面是整体布局，下面是弹出窗口----------------------------------
/**
 *新增库区信息
 */
Ext.define('Foss.baseinfo.expressPrintStar.ExpressPrintStarAddWindow', {
	extend : 'Ext.window.Window',
	title : baseinfo.expressPrintStar.i18n('foss.baseinfo.newReservoirArea'), //新增库区
	closable : true,
	parent : null, //父元素（弹出这个window的gird——Foss.baseinfo.expressPrintStar.ExpressPrintStarGrid）
	resizable : false, //可以调整窗口的大小
	closeAction : 'hide', //点击关闭是隐藏窗口
	width : 610,
	height : 450,
	listeners : {
		beforehide : function (me) { //隐藏WINDOW的时候清除数据
			me.resetData();
		},
		beforeshow : function (me) { //显示WINDOW的时候清除数据		
		}
	},
	//新增库区FORM
	expressPrintStarForm : null,
	getExpressPrintStarForm : function () {
		if (Ext.isEmpty(this.expressPrintStarForm)) {
			this.expressPrintStarForm = Ext.create('Foss.baseinfo.expressPrintStar.ExpressPrintStarForm', {
					'isUpdate' : false //新增
				});
		}
		return this.expressPrintStarForm;
	},
	//提交库区数据
	commitExpressPrintStar : function (button) {
		var me = this;
		var form = me.getExpressPrintStarForm();
		if (form.getForm().isValid()) { //校验form是否通过校验
			var expressPrintStarModel = new Foss.baseinfo.expressPrintStar.ExpressPrintStarEntity();
			var storageList = new Array(); // 到各个库区所拥有的月台
			form.getForm().updateRecord(expressPrintStarModel); //将FORM中数据设置到MODEL里面
			if (!Ext.isEmpty(form.oldItems)) {
				for (var i = 0; i < form.oldItems.length; i++) {
					if (form.oldItems[i].getValue()) { //选中了
						storageList.push({
							'virtualCode' : form.oldItems[i].storageEntity.virtualCode
						});
					}
				}
			}
			expressPrintStarModel.set('storageList', storageList);
			var params = {
				'expressPrintStarVo' : {
					'expressPrintStarEntity' : expressPrintStarModel.data
				}
			}; //组织新增数据
			var successFun = function (json) {
				button.setDisabled(false);
				baseinfo.showInfoMes(json.message); //提示新增成功
				me.close();
				me.parent.getPagingToolbar().moveFirst(); //成功之后重新查询刷新结果集
			};
			var failureFun = function (json) {
				button.setDisabled(false);
				if (Ext.isEmpty(json)) {
					baseinfo.showErrorMes(baseinfo.expressPrintStar.i18n('foss.baseinfo.requestTimeout')); //请求超时
				} else {
					baseinfo.showErrorMes(json.message); //提示失败原因
				}
			};
			var url = baseinfo.realPath('addExpressPrintStar.action'); //请求库区新增
			button.setDisabled(true);
			baseinfo.requestJsonAjax(url, params, successFun, failureFun); //发送AJAX请求
		}
	},
	resetData : function () {
		var me = this;
		var form = me.getExpressPrintStarForm();
		if (!Ext.isEmpty(form.oldItems)) { //将多余的元素清掉
			for (var i = 0; i < form.oldItems.length; i++) {
				form.remove(form.oldItems[i]);
			}
		}
		form.getForm().reset(); //表格重置
	},
	constructor : function (config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.fbar = [{
				text : baseinfo.expressPrintStar.i18n('foss.baseinfo.cancel'), //取消
				handler : function () {
					me.close();
				}
			}, {
				text : baseinfo.expressPrintStar.i18n('foss.baseinfo.reset'), //重置
				handler : function () {
					me.resetData();
				}
			}, {
				text : baseinfo.expressPrintStar.i18n('foss.baseinfo.save'), //保存
				cls : 'yellow_button',
				margin : '0 0 0 325',
				handler : function () {
					me.commitExpressPrintStar(this);
				}
			}
		];
		me.items = [me.getExpressPrintStarForm()];
		me.callParent([cfg]);
	}
});
/**
 * 查看库区
 */
Ext.define('Foss.baseinfo.expressPrintStar.ExpressPrintStarShowWindow', {
	extend : 'Ext.window.Window',
	title : baseinfo.expressPrintStar.i18n('foss.baseinfo.viewReservoirArea'), //查看库区
	closable : true,
	resizable : false,
	expressPrintStarEntity : null, //查看库区数据
	parent : null, //父元素（弹出这个window的gird——Foss.baseinfo.expressPrintStar.ExpressPrintStarGrid）
	closeAction : 'hide',
	width : 610,
	height : 450,
	listeners : {
		beforehide : function (me) {
			me.resetData(); //清除掉这次的数据
		},
		beforeshow : function (me) {
			me.getExpressPrintStarForm().getForm().findField('organizationName').setValue('青岛转运中心'); //只在加载数据的时处理外场信息，在重置的时候，不进行处理
			me.getExpressPrintStarForm().getForm().findField('organizationCode').setValue('D16'); //只在加载数据的时处理外场信息，在重置的时候，不进行处理
			me.getExpressPrintStarForm().getForm().findField('arriveRegionCode').setCombValue(me.expressPrintStarEntity.arriveRegionName, me.expressPrintStarEntity.arriveRegionCode); //目的站
			me.getExpressPrintStarForm().getForm().findField('transferCode').setValue('D16'); //只在加载数据的时处理外场信息，在重置的时候，不进行处理
			me.loadValue();
		}
	},
	//库区FORM
	expressPrintStarForm : null,
	getExpressPrintStarForm : function () {
		if (Ext.isEmpty(this.expressPrintStarForm)) {
			this.expressPrintStarForm = Ext.create('Foss.baseinfo.expressPrintStar.ExpressPrintStarForm', {
					'isUpdate' : true, //证明是修改
					'isShow' : true
				});
			this.expressPrintStarForm.getForm().findField('organizationName').setReadOnly(true);
		}
		return this.expressPrintStarForm;
	},
	//清除数据
	resetData : function () {
		var me = this;
		var form = me.getExpressPrintStarForm();
		if (!Ext.isEmpty(form.oldItems)) { //将多余的元素清掉
			var oldItems =form.oldItems;
			for (var i = 0; i < oldItems.length; i++) {
				form.remove(oldItems[i]);
			}
		}
		form.getForm().reset(); //表格重置
	},
	//加载原有数据
	loadValue : function () { //外场名称和外场code不进行处理
		var me = this;
		var expressPrintStarModel = new Foss.baseinfo.expressPrintStar.ExpressPrintStarEntity(me.expressPrintStarEntity);
//		me.getExpressPrintStarForm().getForm().findField('organizationCode').setValue('D16');
//		me.getExpressPrintStarForm().getForm().findField('organizationName').setValue('青岛转运中心');
		me.getExpressPrintStarForm().getForm().findField('goodsAreaCode').setValue(expressPrintStarModel.get('goodsAreaCode'));
		me.getExpressPrintStarForm().getForm().findField('goodsAreaName').setValue(expressPrintStarModel.get('goodsAreaName'));
		me.getExpressPrintStarForm().getForm().findField('goodsAreaType').setValue(expressPrintStarModel.get('goodsAreaType'));
		me.getExpressPrintStarForm().getForm().findField('goodsAreaUsage').setValue(expressPrintStarModel.get('goodsAreaUsage'));
		me.getExpressPrintStarForm().getForm().findField('goodsType').setValue(expressPrintStarModel.get('goodsType'));
		me.getExpressPrintStarForm().getForm().findField('arriveRegionCode').setValue(expressPrintStarModel.get('arriveRegionCode'));
		if(expressPrintStarModel.get('asteriskCode')=='N'){
			me.getExpressPrintStarForm().getForm().findField('asteriskCode').setValue(baseinfo.expressPrintStar.i18n('foss.baseinfo.null'));//无
		}else{
			me.getExpressPrintStarForm().getForm().findField('asteriskCode').setValue(expressPrintStarModel.get('asteriskCode'));
		}
	},
	constructor : function (config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.fbar = [{
				text : baseinfo.expressPrintStar.i18n('foss.baseinfo.cancel'), //取消
				handler : function () {
					me.close();
				}
			}
		];
		me.items = [me.getExpressPrintStarForm()];
		me.callParent([cfg]);
	}
});
/**
 * 修改库区
 */
Ext.define('Foss.baseinfo.expressPrintStar.ExpressPrintStarUpdateWindow', {
	extend : 'Ext.window.Window',
	title : baseinfo.expressPrintStar.i18n('foss.baseinfo.modifyReservoirArea'), //修改库区
	closable : true,
	resizable : false,
	expressPrintStarEntity : null, //修改库区数据
	parent : null, //父元素（弹出这个window的gird——Foss.baseinfo.expressPrintStar.ExpressPrintStarGrid）
	closeAction : 'hide',
	width : 590,
	height : 450,
	listeners : {
		beforehide : function (me) {
			me.resetData(); //清除掉这次的数据
		},
		beforeshow : function (me) {
			me.getExpressPrintStarForm().getForm().findField('organizationName').setValue('青岛转运中心'); //只在加载数据的时处理外场信息，在重置的时候，不进行处理
			me.getExpressPrintStarForm().getForm().findField('organizationCode').setValue('D16'); //只在加载数据的时处理外场信息，在重置的时候，不进行处理
			me.getExpressPrintStarForm().getForm().findField('arriveRegionCode').setCombValue(me.expressPrintStarEntity.arriveRegionName, me.expressPrintStarEntity.arriveRegionCode); //目的站
			me.getExpressPrintStarForm().getForm().findField('transferCode').setValue('D16'); //只在加载数据的时处理外场信息，在重置的时候，不进行处理
			me.loadValue();
		}
	},
	//库区FORM
	expressPrintStarForm : null,
	getExpressPrintStarForm : function () {
		if (Ext.isEmpty(this.expressPrintStarForm)) {
			this.expressPrintStarForm = Ext.create('Foss.baseinfo.expressPrintStar.ExpressPrintStarForm', {
					'isUpdate' : true //证明是修改
				});
			this.expressPrintStarForm.getForm().findField('organizationName').setReadOnly(true);
		}
		return this.expressPrintStarForm;
	},
	//修改库区
	commitExpressPrintStar : function (button) {
		var me = this;
		var form = me.getExpressPrintStarForm();
		if (form.getForm().isValid()) { //校验form是否通过校验
			var expressPrintStarModel = new Foss.baseinfo.expressPrintStar.ExpressPrintStarEntity(me.expressPrintStarEntity);
			var storageList = new Array(); // 到各个库区所拥有的月台
			form.getForm().updateRecord(expressPrintStarModel); //将FORM中数据设置到MODEL里面
			if (!Ext.isEmpty(form.oldItems)) {
				for (var i = 0; i < form.oldItems.length; i++) {
					if (form.oldItems[i].getValue()) { //选中了
						storageList.push({
							'virtualCode' : form.oldItems[i].storageEntity.virtualCode
						});
					}
				}
			}
			expressPrintStarModel.set('storageList', storageList);
			var params = {
				'expressPrintStarVo' : {
					'expressPrintStarEntity' : expressPrintStarModel.data
				}
			}; //组织新增数据
			var successFun = function (json) {
				button.setDisabled(false);
				baseinfo.showInfoMes(json.message); //提示新增成功
				me.close();
				me.parent.getPagingToolbar().moveFirst(); //成功之后重新查询刷新结果集
			};
			var failureFun = function (json) {
				button.setDisabled(false);
				if (Ext.isEmpty(json)) {
					baseinfo.showErrorMes(baseinfo.expressPrintStar.i18n('foss.baseinfo.requestTimeout')); //请求超时
				} else {
					baseinfo.showErrorMes(json.message); //提示失败原因
				}
			};
			var url = baseinfo.realPath('updateExpressPrintStar.action'); //请求库区新增
			button.setDisabled(true);
			baseinfo.requestJsonAjax(url, params, successFun, failureFun); //发送AJAX请求
		}
	},
	//清除数据
	resetData : function () {
		var me = this;
		var form = me.getExpressPrintStarForm();
		if (!Ext.isEmpty(form.oldItems)) { //将多余的元素清掉
			var oldItems =form.oldItems;
			for (var i = 0; i < oldItems.length; i++) {
				form.remove(oldItems[i]);
			}
		}
		form.getForm().reset(); //表格重置
		
	},
	//加载原有数据
	loadValue : function () { //外场名称和外场code不进行处理
		var me = this;
		var expressPrintStarModel = new Foss.baseinfo.expressPrintStar.ExpressPrintStarEntity(me.expressPrintStarEntity);
//		me.getExpressPrintStarForm().getForm().findField('organizationCode').setValue('D16');
//		me.getExpressPrintStarForm().getForm().findField('organizationName').setValue('青岛转运中心');
		me.getExpressPrintStarForm().getForm().findField('goodsAreaCode').setValue(expressPrintStarModel.get('goodsAreaCode'));
		me.getExpressPrintStarForm().getForm().findField('goodsAreaName').setValue(expressPrintStarModel.get('goodsAreaName'));
		me.getExpressPrintStarForm().getForm().findField('goodsAreaType').setValue(expressPrintStarModel.get('goodsAreaType'));
		me.getExpressPrintStarForm().getForm().findField('goodsAreaUsage').setValue(expressPrintStarModel.get('goodsAreaUsage'));
		me.getExpressPrintStarForm().getForm().findField('goodsType').setValue(expressPrintStarModel.get('goodsType'));
		me.getExpressPrintStarForm().getForm().findField('arriveRegionCode').setValue(expressPrintStarModel.get('arriveRegionCode'));
		if(expressPrintStarModel.get('asteriskCode')=='N'){
			me.getExpressPrintStarForm().getForm().findField('asteriskCode').setValue(baseinfo.expressPrintStar.i18n('foss.baseinfo.null'));//无
		}else{
			me.getExpressPrintStarForm().getForm().findField('asteriskCode').setValue(expressPrintStarModel.get('asteriskCode'));
		}
	},
	constructor : function (config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.fbar = [{
				text : baseinfo.expressPrintStar.i18n('foss.baseinfo.cancel'), //取消
				handler : function () {
					me.close();
				}
			},{
				text : baseinfo.expressPrintStar.i18n('foss.baseinfo.reset'), //重置
				handler : function () {
					//me.getExpressPrintStarForm().getForm().findField('organizationName').setCombValue(me.expressPrintStarEntity.organizationName
					//,me.expressPrintStarEntity.organizationCode);
					//在充重置时不进行处理
					me.getExpressPrintStarForm().getForm().findField('arriveRegionCode').setCombValue(me.expressPrintStarEntity.arriveRegionName, me.expressPrintStarEntity.arriveRegionCode); //目的站
					me.loadValue();
					var form = me.getExpressPrintStarForm();
					if (!Ext.isEmpty(form.oldItems)) { //将多余的元素清掉
						for (var i = 0; i < form.oldItems.length; i++) {
							form.oldItems[i].setValue(form.oldItems[i].isCheck);
						}
					}
				}
			}, {
				text : baseinfo.expressPrintStar.i18n('foss.baseinfo.save'), //保存
				cls : 'yellow_button',
				margin : '0 0 0 325',
				handler : function () {
					me.commitExpressPrintStar(this);
				}
			}
		];
		me.items = [me.getExpressPrintStarForm()];
		me.callParent([cfg]);
	}
});
/**
 * 库区组-FORM
 */
Ext.define('Foss.baseinfo.expressPrintStar.ExpressPrintStarForm', {
	extend : 'Ext.form.Panel',
	title : baseinfo.expressPrintStar.i18n('foss.baseinfo.reservoirAreaInformation'), //库区信息
	frame : true,
	autoScroll : true,
	height : 330,
	oldItems : new Array(), //上一次的库位元素
	isUpdate : false, //是否为修改
	//flex:1,
	collapsible : true,
	defaults : {
		margin : '5 5 5 5',
		labelWidth : 80,
		//width:200,
		colspan : 3
	},
	layout : {
		type : 'table',
		columns : 6
	},
	constructor : function (config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.items = [{
				name : 'organizationName', //外场名称
				//allowBlank : false,
				readOnly : true,
				xtype : 'textfield',
				value:'青岛转运中心',
				userCode:FossUserContext.getCurrentUserEmp().empCode,
				currentOrgCode:FossUserContext.getCurrentDeptCode(),
				fieldLabel : baseinfo.expressPrintStar.i18n('foss.baseinfo.fieldName'),
//				listeners : {
//					select:function(text,records,eops){
//						me.getForm().findField('organizationCode').setValue(records[0].get('orgCode'));
//		        		me.getForm().findField('transferCode').setValue(records[0].get('code'));
//					}
//				}
			}, {
				name : 'transferCode', //外场编号
				readOnly : true,
				value:'D16',
				fieldLabel : baseinfo.expressPrintStar.i18n('foss.baseinfo.fieldID'),
				xtype : 'textfield'
			}, {
				name : 'organizationCode', //外场编号
				readOnly : true,
				value:'D16',
				hidden:true,
				xtype : 'textfield'
			}, {
				name : 'goodsAreaCode', //库区编号
				allowBlank : false,
				maxLength : '20',
				fieldLabel : baseinfo.expressPrintStar.i18n('foss.baseinfo.reservoirAreaNumber'),
				xtype : 'textfield'
			}, {
				name : 'goodsAreaName', //库区名称
				allowBlank : false,
				maxLength : '20',
				fieldLabel : baseinfo.expressPrintStar.i18n('foss.baseinfo.reservoirAreaName'),
				xtype : 'textfield'
			}, {
				name : 'goodsAreaType',
				queryMode : 'local',
				allowBlank : false,
				displayField : 'valueName',
				valueField : 'valueCode',
				editable : false,
				store : FossDataDictionary.getDataDictionaryStore(baseinfo.expressPrintStar.goodsAreaType),
				fieldLabel : baseinfo.expressPrintStar.i18n('foss.baseinfo.reservoirAreaType'), //库区类型
				xtype : 'combo',
				listeners:{
					change:function(item,newValue){
						if(newValue==baseinfo.expressPrintStar.goodsAreaTypeHZ||newValue==baseinfo.expressPrintStar.goodsAreaTypeKH||newValue==baseinfo.expressPrintStar.goodsAreaTypePH){
							me.getForm().findField('arriveRegionCode').allowBlank = false;
							
						}else{
							me.getForm().findField('arriveRegionCode').allowBlank = true;
							//若库区为偏线库区
							if(newValue ==baseinfo.expressPrintStar.goodsAreaTypePX){
								me.getForm().findField('arriveRegionCode').setValue('');
								me.getForm().findField('arriveRegionCode').setDisabled(true);
							}else{
								me.getForm().findField('arriveRegionCode').setDisabled(false);
							}
						}
					}
				}
			}, {
				name : 'goodsAreaUsage',
				queryMode : 'local',
				displayField : 'valueName',
				valueField : 'valueCode',
				editable : false,
				allowBlank : false,
				store : FossDataDictionary.getDataDictionaryStore(baseinfo.expressPrintStar.goodsAreaUsage),
				fieldLabel : baseinfo.expressPrintStar.i18n('foss.baseinfo.reservoirAreaCategory'), //库区类别
				xtype : 'combo'
			}, {
				name : 'goodsType',
				queryMode : 'local',
				displayField : 'valueName',
				valueField : 'valueCode',
				editable : false,
				store : FossDataDictionary.getDataDictionaryStore(baseinfo.expressPrintStar.goodsType),
				fieldLabel : baseinfo.expressPrintStar.i18n('foss.baseinfo.cargoType'), //货物类型
				xtype : 'combo'
			}, {
				name : 'arriveRegionCode', //目的站
				fieldLabel : '目的站',
				xtype : 'dynamicorgcombselector',
				//forceSelection : true,
				type : 'ORG',
				salesDepartment : 'Y',//营业部
				transferCenter : 'Y',//外场
				airDispatch:'Y'//空运总调
			}, {
				colspan : 3,
				name : 'asteriskCode',
				queryMode : 'local',
				displayField : 'valueName',
				valueField : 'valueCode',
				allowBlank : false,
				editable : false,
				value:'',
				store : FossDataDictionary.getDataDictionaryStore(baseinfo.expressPrintStar.asteriskType,null, {
					'valueCode' : '',
					'valueName' : baseinfo.expressPrintStar.i18n('foss.baseinfo.null'), //无
				}),
				fieldLabel : baseinfo.expressPrintStar.i18n('foss.baseinfo.asterisk'), //星标
				xtype : 'combo'
			}
//			, {
//				colspan : 3,
//				xtype : 'displayfield',
//				value : baseinfo.expressPrintStar.i18n('foss.baseinfo.correspondingLocation') //对应库位
//			}
		];
		me.callParent([cfg]);
		if (me.isUpdate) { //修改
			me.getForm().findField('organizationCode').on('change', function (text, newValue, oldValue) {
				if (!Ext.isEmpty(me.oldItems)) {
					for (var i = 0; i < me.oldItems.length; i++) {
						me.remove(me.oldItems[i]);
					}
				}
				if (!Ext.isEmpty(newValue)) { //设置的值不为空时才执行操作
					var organizationCode = newValue; //查询该外场下的所有月台
					var params = {
						'storageVo' : {
							'storageEntity' : {
								'organizationCode' : organizationCode
							}
						}
					};
					var successFun = function (json) {
						var storageEntityList = json.storageVo.storageEntityList;
						if (!Ext.isEmpty(storageEntityList)) {
							var items = new Array();
							for (var i = 0; i < storageEntityList.length; i++) {
								var isHidden = false; //是否隐藏
								var isChecked = false; //是否选中
								if (!Ext.isEmpty(storageEntityList[i].expressPrintStarVirtualCode)) {
									if (storageEntityList[i].expressPrintStarVirtualCode == me.up('window').expressPrintStarEntity.virtualCode) {
										isHidden = false;
										isChecked = true;
									} else {
										isHidden = true;
										isChecked = false;
									}
								} else {
									isChecked = false;
									isHidden = false;
								}
								//库位编码
								var boxLabel = storageEntityList[i].storageCode;
//								if (isHidden) {
//									boxLabel = '<span style = "color:red">' + storageEntityList[i].storageCode + '</span>'
//								} else {
//									boxLabel = storageEntityList[i].storageCode
//								}
								var item = null;
								if (me.isShow) { //如果仅仅查看则设置为只读
									item = { //根据库位的checkbox
										xtype : 'checkbox',
										colspan : 1,
										isHidden : isHidden, //是否隐藏
										isCheck : isChecked, //是否选中
										readOnly : true,
										boxLabel : boxLabel, //库位编码
										storageEntity : storageEntityList[i], //库位相关数据
										hidden : isHidden,
										checked : isChecked
									};
								} else {
									item = { //根据库位的checkbox
										xtype : 'checkbox',
										colspan : 1,
										isHidden : isHidden, //是否隐藏
										isCheck : isChecked, //是否选中
										readOnly : isHidden,
										boxLabel : boxLabel, //库位编码
										storageEntity : storageEntityList[i], //库位相关数据
										hidden : isHidden,
										checked : isChecked
									};
								}
								items.push(item);
							}
							me.oldItems = me.add(items);
						}
					};
					var failureFun = function (json) {
						if (Ext.isEmpty(json)) {
							baseinfo.showErrorMes(baseinfo.expressPrintStar.i18n('foss.baseinfo.requestTimeout')); //请求超时
						} else {
							baseinfo.showErrorMes(json.message); //提示失败原因
						}
					}
					var url = baseinfo.realPath('queryStorageListByOrganizationCode.action'); //查询该外场的月台
					baseinfo.requestJsonAjax(url, params, successFun, failureFun); //发送AJAX请求
				}
			});
		} else {
			me.getForm().findField('organizationName').on('blur', function (text, obj) {
				if (!Ext.isEmpty(me.oldItems)) { //清除掉原先的元素
					for (var i = 0; i < me.oldItems.length; i++) {
						me.remove(me.oldItems[i]);
					}
				}
				var organizationCode = me.getForm().findField('organizationCode').getValue(); //查询该外场下的所有月台
				var params = {
					'storageVo' : {
						'storageEntity' : {
							'organizationCode' : organizationCode
						}
					}
				};
				var successFun = function (json) {
					var storageEntityList = json.storageVo.storageEntityList;
					if (!Ext.isEmpty(storageEntityList)) {
						var items = new Array();
						for (var i = 0; i < storageEntityList.length; i++) {
							var isHidden = false; //是否隐藏
							if (!Ext.isEmpty(storageEntityList[i].expressPrintStarVirtualCode)) { //如果该库位属于其他库区，则不给显示，不可编辑，默认隐藏
								isHidden = true;
								//boxLabel = '<span style = "color:red">' + storageEntityList[i].storageCode + '</span>';
							} else {
								isHidden = false;
							}
							var  boxLabel = storageEntityList[i].storageCode;
//							var boxLabel = null;
//							if (isHidden) {
//								boxLabel = '<span style = "color:red">' + storageEntityList[i].storageCode + '</span>'
//							} else {
//								boxLabel = storageEntityList[i].storageCode
//							}
							var item = { //根据库位的checkbox
								xtype : 'checkbox',
								colspan : 1,
								isHidden : isHidden, //标记其默认是否隐藏
								readOnly : isHidden,
								boxLabel : boxLabel, //库位编码
								storageEntity : storageEntityList[i], //库位相关数据
								hidden : isHidden
							};
							items.push(item);
						}
						me.oldItems = me.add(items);
					}
				};
				var failureFun = function (json) {
					if (Ext.isEmpty(json)) {
						baseinfo.showErrorMes(baseinfo.expressPrintStar.i18n('foss.baseinfo.requestTimeout')); //请求超时
					} else {
						baseinfo.showErrorMes(json.message); //提示失败原因
					}
				};
				var url = baseinfo.realPath('queryStorageListByOrganizationCode.action'); //查询该外场的库位
				baseinfo.requestJsonAjax(url, params, successFun, failureFun); //发送AJAX请求
			});
		}
	}
});
