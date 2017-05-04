/**
 * @author 189284-foss-ZhangXu
 */
/***************************************************  定义"展馆关键字"列表查询条件窗口 *************************************************/
/*
 * 查询条件表单FORM中的KEY/VALUE的参数MAP存储器
 */
Ext.define('Foss.baseinfo.exhibitionKeyword.QueryFormParameterStore', {
	extend : 'Ext.data.Store',
	fields : [{
			name : 'valueCode',
			type : 'string'
		}, {
			name : 'valueName',
			type : 'string'
		}
	],
	proxy : {
		type : 'memory',
		reader : {
			type : 'json',
			root : 'items' //定义读取JSON数据的根对象
		}
	},
	constructor : function (config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

/*
 * 定义查询条件的表单FORM
 */
Ext.define('Foss.baseinfo.exhibitionKeyword.QueryForm', {
	extend : 'Ext.form.Panel',
	title : '查询条件',
	id : 'Foss_baseinfo_exhibitionKeyword_QueryForm_Id',
	frame : true,
	collapsible : true,
	layout : {
		type : 'table',
		columns : 3
	},
	defaults : {
		labelSeparator : ':',
		margin : '10 0 0 0',
		anchor : '100%',
		labelWidth : 100,
		width : 250
	},
	height : 180,
	defaultType : 'textfield',
	layout : 'column',
	items : [{
			//colspan:8,
			//nationName:'nationCode',
			provinceName:'provinceCode',// 省份名称—对应name
			cityName:'cityCode',// 城市name
			areaName:'countyCode',// 县name
			//nationLabel : '国家',
			provinceLabel : '省份',
			cityLabel : '城市',
			areaLabel : '区/县',
			width:660,
			hideLabel:true,
			//nationIsBlank:true,
			provinceIsBlank:true,
			cityIsBlank:true,
			areaIsBlank:true,
			//nationWidth : 220,
			provinceWidth : 288,
			cityWidth : 288,
			areaWidth : 328,
			labelWid : 100,
			type : 'P-C-C',
			columnWidth:1,
			xtype : 'linkregincombselector'
		}, {
			name : 'exhibitionKeyword',
			fieldLabel : '展馆关键字',
			readOnly : false,
			columnWidth:0.29,
			maxLength:15
		},{
		    //colspan:2,
			xtype : 'combobox',
			name : 'type',
			fieldLabel : '货物类型',
			displayField : 'valueName',
			valueField : 'valueCode',
			queryMode : 'local',
			triggerAction : 'all',
			columnWidth:0.29,
			editable : false,
			store : FossDataDictionary.getDataDictionaryStore('BSE_EXHIBITIONKEYWORD_TYPE', 'Foss_baseinfo_exhibitionKeyword_ExhibitionKeywordSortStore_Id'//, {
				//'valueCode' : 'EXHIBITION_GOODS',
				//'valueName' : '展会货'
			//}
			),
			value : 'EXHIBITION_GOODS'
		},{
			name : 'exhibitionAddress',
			fieldLabel : '展馆详细地址',
			readOnly : false,
			columnWidth:0.33,
			maxLength:200
		}
	],
	constructor : function (config) {
		var me = this,
		cfg = Ext.apply({}, config);
		//me.items[2].value='EXHIBITION_GOODS';
		me.fbar = [{
				xtype : 'button',
				width : 70,
				text : '重置', //重置
				disabled:!baseinfo.exhibitionKeyword.isPermission('exhibitionKeyword/exhibitionKeywordQueryButton'),
				hidden:!baseinfo.exhibitionKeyword.isPermission('exhibitionKeyword/exhibitionKeywordQueryButton'),
				margin : '0 0 0 20',
				handler : function () {
					me.getForm().reset();
				}
			},'->', {
				xtype : 'button',
				width : 70,
				margin : '0 20 0 0',
				text : '查询', //查询
				cls : 'yellow_button',
				disabled:!baseinfo.exhibitionKeyword.isPermission('exhibitionKeyword/exhibitionKeywordQueryButton'),
				hidden:!baseinfo.exhibitionKeyword.isPermission('exhibitionKeyword/exhibitionKeywordQueryButton'),
				handler : function () {
					if (me.getForm().isValid()) {
						me.up().getExhibitionKeywordGrid().getPagingToolbar().moveFirst();
					}
				}
			}
		];
		me.callParent([cfg]);
	}
});

/*******************************************************  定义"展馆关键字信息"列表结果窗口 *****************************************************/
/*
 * 定义：一个"展馆关键字信息"的数据模型"Model"
 */
Ext.define('Foss.baseinfo.exhibitionKeyword.ExhibitionKeywordModel', {
	extend : 'Ext.data.Model',
	idProperty : 'extid',
	idgen : 'uuid',
	fields : [{
			name : 'extid'
		}, {
			name : 'id', //ID标识
			type : 'string'
		}, {
			name : 'type', //货物类型
			type : 'string',
			defaultValue : 'EXHIBITION_GOODS'
		}, {
			name : 'provinceCode', //省编码
			type : 'string'
		}, {
			name : 'provinceName', //省名称
			type : 'string'
		}, {
			name : 'cityCode', //城市编码
			type : 'string'
		}, {
			name : 'cityName', //城市名称
			type : 'string'
		}, {
			name : 'countyCode', //县编码
			type : 'string'
		}, {
			name : 'countyName', //县名称
			type : 'string'
		}, {
			name : 'exhibitionKeyword', //展馆关键字
			type : 'string'
		}, {
			name : 'exhibitionAddress', //(展馆)详细地址
			type : 'string'
		}, {
			name : 'active', //是否作废
			type : 'string'
		}, {
			name : 'createUser', //创建者
			type : 'string'
		}, {
			name : 'modifyUser', //修改者
			type : 'string'
		}, {
			name : 'createDate', //创建时间
			type : 'date',
			convert : function (value) {
				if (Ext.isEmpty(value)) {
					return null;
				}
				return new Date(value);
			}
		},{
			name : 'modifyDate', //修改时间
			type : 'date',
			convert : function (value) {
				if (Ext.isEmpty(value)) {
					return null;
				}
				return new Date(value);
			}
		}
	]
});

/*
 * 定义：一个"展馆关键字信息"的查询数据模型"Store"交互后台
 */
Ext.define('foss.baseinfo.exhibitionKeyword.ExhibitionKeywordStore', {
	extend : 'Ext.data.Store',
	autoLoad : false,
	//页面条数定义
	pageSize : 10,
	//绑定model
	model : 'Foss.baseinfo.exhibitionKeyword.ExhibitionKeywordModel',
	proxy : {
		//以JSON的方式加载数据
		type : 'ajax',
		actionMethods : 'POST',
		url : baseinfo.realPath('queryExhibitionKeywordList.action'),
		reader : {
			type : 'json',
			root : 'exhibitionKeywordVo.exhibitionKeywordList',
			totalProperty : 'totalCount',
			successProperty : 'success'
		}
	},
	//构造函数
	constructor : function (config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	},
	//监听器
	listeners : {
		beforeload : function (store, operation, eOpts) {
			var queryForm = Ext.getCmp('Foss_baseinfo_exhibitionKeyword_QueryForm_Id').getForm();
			if (queryForm != null) {
				var queryParams = queryForm.getValues();
				Ext.apply(operation, {
					params : {
						'exhibitionKeywordVo.exhibitionKeyword.type' : queryParams.type,
						'exhibitionKeywordVo.exhibitionKeyword.provinceCode' : queryParams.provinceCode,
						'exhibitionKeywordVo.exhibitionKeyword.cityCode' : queryParams.cityCode,
						'exhibitionKeywordVo.exhibitionKeyword.countyCode' : queryParams.countyCode,
						'exhibitionKeywordVo.exhibitionKeyword.exhibitionKeyword' : queryParams.exhibitionKeyword,
						'exhibitionKeywordVo.exhibitionKeyword.exhibitionAddress' : queryParams.exhibitionAddress						
					}
				});
			}
		}
	}
});

/*
 * 查询结果列表窗口
 */
Ext.define('Foss.baseinfo.exhibitionKeyword.ExhibitionKeywordGrid', {
	extend : 'Ext.grid.Panel',
	columnLines : true, // 增加表格列的分割线
	id : 'Foss_baseinfo_exhibitionKeyword_ExhibitionKeywordGrid_Id',
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	frame : true, // 表格对象增加一个边框
	stripeRows : true,
	columnLines : true, // 增加表格列的分割线
	title : '展馆关键字信息', // 定义表格的标题
	collapsible : true,
	animCollapse : true,
	//表格多选插件
	selModel : Ext.create('Ext.selection.CheckboxModel'),
	store : null,
	//表格行可展开的插件
	/**plugins : [{
			ptype : 'rowexpander',
			rowsExpander : false, // 定义行展开模式（单行与多行），默认是多行展开(值true)
			rowBodyElement : 'Foss.baseinfo.exhibitionKeyword.ExhibitionKeywordDetailPanel' // 行体内容
		}
	],**/
	addUpdateWindow : null,
	/**
	 * 获取"作废、申请可用、不可用"的窗口的对象
	 * @param {String} windowTitle 窗口的标题
	 * @param {Boolean} isNewApply true：作废，false：申请可用/不可用
	 * @param {String} actionType apply/available/unavailable
	 * @return {Ext.Component} 申请窗口的对象
	 */
	getAddUpdateWindow : function (windowTitle, actionType) {
		var me = this;
		if (this.addUpdateWindow == null) {
			this.addUpdateWindow = Ext.create('Foss.baseinfo.exhibitionKeyword.ExhibitionKeywordAddUpdateWindow');
		}
		this.addUpdateWindow.actionType = actionType;
		this.addUpdateWindow.setOperationUrl((function () {
				var operationUrl = null;
				if (actionType === 'add') {
					operationUrl = baseinfo.realPath('addExhibitionKeyword.action');
				} else {}
				if (actionType === 'upd') {
					operationUrl = baseinfo.realPath('updateExhibitionKeyword.action');
				} else {}
				return operationUrl;
			})(actionType));
		this.addUpdateWindow.getAddUpdateForm().setTitle(windowTitle);
		if (actionType && actionType === 'add') {
			me.addUpdateWindow.getAddUpdateForm().getForm().reset();
			me.addUpdateWindow.getAddUpdateForm().getForm().findField('type').setValue('EXHIBITION_GOODS');
		} else {}
		if (actionType && actionType === 'upd') {
			//			me.addUpdateWindow.getAddUpdateForm().getForm().findField('idCard').setReadOnly(true);
		} else {}
		return this.addUpdateWindow;
	},
	//列表分页组件对象
	pagingToolbar : null,
	getPagingToolbar : function () {
		var me = this;
		if (Ext.isEmpty(me.pagingToolbar)) {
			me.pagingToolbar = Ext.create('Deppon.StandardPaging', {
					store : me.store,
					pageSize : 10,
					prependButtons : true,
					plugins: Ext.create('Deppon.ux.PageSizePlugin', {
                    limitWarning:'最大查询记录不能超过'
                    }),
					defaults : {
						margin : '0 0 15 3'
					}
				});
		}
		return me.pagingToolbar;
	},
	// 定义表格列信息
	columns : [{
			xtype : 'actioncolumn',
			width : 60,
			text : '操作',
			align : 'center',
			items : [{
					iconCls : 'deppon_icons_edit',
					disabled:!baseinfo.exhibitionKeyword.isPermission('exhibitionKeyword/exhibitionKeywordUpdateButton'),
					tooltip : '修改',
					/**
					 * "修改"响应事件
					 * @param {Object} grid 当前表格
					 * @param {Number} rowIndex 行索引
					 * @param {Number} colIndex 列索引
					 * @param {Object} grid 当前按钮
					 */
					handler : function (grid, rowIndex, colIndex, item) {
						var record = grid.getStore().getAt(rowIndex);
						var addUpdateWindow = grid.up().getAddUpdateWindow(item.tooltip, 'upd');
						addUpdateWindow.loadAddUpdateForm(record);
						addUpdateWindow.down('linkregincombselector').setReginValue(record.get('nationName'),record.get('nationCode'),0);
						addUpdateWindow.down('linkregincombselector').setReginValue(record.get('provinceName'),record.get('provinceCode'),1);
						addUpdateWindow.down('linkregincombselector').setReginValue(record.get('cityName'),record.get('cityCode'),2);
						addUpdateWindow.down('linkregincombselector').setReginValue(record.get('countyName'),record.get('countyCode'),3);
						addUpdateWindow.show();
						addUpdateWindow = null;
						tempForm = null;
				
					}
				}, {
					iconCls : 'deppon_icons_delete',
					disabled:!baseinfo.exhibitionKeyword.isPermission('exhibitionKeyword/exhibitionKeywordDeleteButton'),
					visible:!baseinfo.exhibitionKeyword.isPermission('exhibitionKeyword/exhibitionKeywordDeleteButton'),
					tooltip : '作废',
					/**
					 * "修改"响应事件
					 * @param {Object} grid 当前表格
					 * @param {Number} rowIndex 行索引
					 * @param {Number} colIndex 列索引
					 */
					handler : function (grid, rowIndex, colIndex) {
						Ext.MessageBox.show({
							title : '确认提示',
							msg : '作废（展馆关键字信息）后不可恢复，确认是否继续？',
							buttons : Ext.Msg.YESNO,
							icon : Ext.MessageBox.QUESTION,
							fn : function (btn) {
								if (btn == 'yes') {
									//获取结果表格对象
									var record = grid.getStore().getAt(rowIndex);
									Ext.Ajax.request({
										url : baseinfo.realPath('deleteExhibitionKeyword.action'),
										jsonData : {
											'exhibitionKeywordVo' : {
												'batchIds' : new Array(record.data.id)
											}
										},
										//"作废"成功
										success : function (response) {
											grid.up().getPagingToolbar().moveFirst();
											var json = Ext.decode(response.responseText);
											Ext.MessageBox.show({
												title : '信息（成功）提示',
												msg : json.message,
												width : 300,
												buttons : Ext.Msg.OK,
												icon : Ext.MessageBox.INFO
											});
										},
										//"作废"失败
										exception : function (response) {
											var json = Ext.decode(response.responseText);
											Ext.MessageBox.show({
												title : '信息（失败）提示',
												msg : json.message,
												width : 300,
												buttons : Ext.Msg.OK,
												icon : Ext.MessageBox.WARNING
											});
										}
									});
								}
							}
						});
					}
				}
			]
		}, {
			header : '货物类型',
			dataIndex : 'type',
			width: 80,
			renderer : function (value) {
				var val = FossDataDictionary.rendererSubmitToDisplay(value, 'BSE_EXHIBITIONKEYWORD_TYPE');
				if (Ext.isEmpty(value)) {
					return '-';
				} else {
					return val;
				}
			}
		}, {
			header : '省份',
			width: 80,
			dataIndex : 'provinceName'
		}, {
			header : '城市',
			width: 80,
			dataIndex : 'cityName'
		}, {
			header : '区/县',
			width: 80,
			dataIndex : 'countyName'
		}, {
			xtype : 'ellipsiscolumn',
			header : '展会关键字',
			flex: 3,
			dataIndex : 'exhibitionKeyword'
		}, {
			xtype : 'ellipsiscolumn',
			header : '展会详细地址',
			flex: 3,
			dataIndex : 'exhibitionAddress'
		}
		
	],
	/**
	 * 构造函数
	 * @param {Object} config 构造函数配置项
	 */
	constructor : function (config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.store = Ext.create('foss.baseinfo.exhibitionKeyword.ExhibitionKeywordStore');
		me.bbar = me.getPagingToolbar();
		me.getPagingToolbar().store = me.store;
		me.dockedItems = [{
				xtype : 'toolbar',
				dock : 'top',
				layout : 'column',
				defaults : {
					margin : '0 0 5 3'
				},
				items : [{
						xtype : 'button',
						text : '新增',
						width : 80,
						disabled:!baseinfo.exhibitionKeyword.isPermission('exhibitionKeyword/exhibitionKeywordAddButton'),
						hidden:!baseinfo.exhibitionKeyword.isPermission('exhibitionKeyword/exhibitionKeywordAddButton'),
						//弹出"新增"的展馆关键字信息的窗口
						handler : function (mine) {
							me.getAddUpdateWindow(mine.text, 'add').show();
						}
					}, {
						xtype : 'button',
						text : '作废',
						disabled:!baseinfo.exhibitionKeyword.isPermission('exhibitionKeyword/exhibitionKeywordDeleteButton'),
						hidden:!baseinfo.exhibitionKeyword.isPermission('exhibitionKeyword/exhibitionKeywordDeleteButton'),
						width : 80,
						/**
						 * 作废展馆关键字信息
						 */
						handler : function () {
							var selectionRecord = me.getSelectionModel().getSelection();
							if (selectionRecord && selectionRecord.length > 0) {
								var ids = new Array();
								for (var i = 0; i < selectionRecord.length; i++) {
									Ext.Array.include(ids, selectionRecord[i].data.id);
								}
								Ext.MessageBox.show({
									title : '确认提示',
									msg : '作废（展馆关键字信息）后不可恢复，确认是否继续？',
									buttons : Ext.Msg.YESNO,
									icon : Ext.MessageBox.QUESTION,
									fn : function (btn) {
										if (btn == 'yes') {
											//获取结果表格对象
											Ext.Ajax.request({
												url : baseinfo.realPath('deleteExhibitionKeyword.action'),
												jsonData : {
													'exhibitionKeywordVo' : {
														'batchIds' : ids
													}
												},
												//"作废"成功
												success : function (response) {
													me.getPagingToolbar().moveFirst();
													var json = Ext.decode(response.responseText);
													Ext.MessageBox.show({
														title : '信息（成功）提示',
														msg : json.message,
														width : 300,
														buttons : Ext.Msg.OK,
														icon : Ext.MessageBox.INFO
													});
												},
												//"作废"失败
												exception : function (response) {
													var json = Ext.decode(response.responseText);
													Ext.MessageBox.show({
														title : '信息（失败）提示',
														msg : json.message,
														width : 300,
														buttons : Ext.Msg.OK,
														icon : Ext.MessageBox.WARNING
													});
												}
											});
										}
									}
								});
							} else {
								Ext.MessageBox.show({
									title : '信息（失败）提示',
									msg : '无任何选中记录！',
									width : 300,
									buttons : Ext.Msg.OK,
									icon : Ext.MessageBox.WARNING
								});
							}
						}
					}
				]
			}
		];
		me.callParent([cfg]);
	}
});

/*****************************************************  定义"展馆关键字信息"申请窗口 *****************************************************/
/*
 * 定义："展馆关键字信息"新增/修改的窗口表单
 */
Ext.define('Foss.baseinfo.exhibitionKeyword.ExhibitionKeywordAddUpdateForm', {
	extend : 'Ext.form.Panel',
	frame : true,
	collapsible : true,
	isSearchComb : true,
	defaults : {
		margin : '10 0 0 0',
		anchor : '100%',
		labelWidth : 100,
		width : 280
	},
	defaultType : 'textfield',
	layout : {
		type : 'table',
		columns : 3
	},
	items : [{
			colspan:3,
			//nationName:'nationCode',
			provinceName:'provinceCode',// 省份名称—对应name
			cityName:'cityCode',// 城市name
			areaName:'countyCode',// 县name
			//nationLabel : '国家',
			provinceLabel : '省份',
			cityLabel : '城市',
			areaLabel : '区/县',
			width:880,
			hideLabel:true,
			//nationIsBlank:false,
			provinceIsBlank:false,
			cityIsBlank:false,
			areaIsBlank:false,
			//nationWidth : 220,
			provinceWidth : 280,
			cityWidth : 280,
			areaWidth : 280,
			labelWid : 100,
			type : 'P-C-C',
			xtype : 'linkregincombselector'
		}, {
			//colspan:2,
			xtype : 'combobox',
			name : 'type',
			fieldLabel : '货物类型',
			allowBlank : false,
			blankText : '货物类型不能为空',
			//defaultValue:FossDataDictionary.rendererSubmitToDisplay('EXHIBITION_GOODS', 'BSE_EXHIBITIONKEYWORD_TYPE'),
			displayField : 'valueName',
			valueField : 'valueCode',
			queryMode : 'local',
			triggerAction : 'all',
			columnWidth:0.29,
			editable : false,
			value : 'EXHIBITION_GOODS',
			store : FossDataDictionary.getDataDictionaryStore('BSE_EXHIBITIONKEYWORD_TYPE', 'Foss_baseinfo_exhibitionKeyword_ExhibitionKeywordSortStore_Id')
		}, {
			//colspan:2,
			name : 'exhibitionKeyword',
			fieldLabel : '展会关键字',
			labelWidth : 100,
			columnWidth:0.30,
			//allowBlank : false,
			//blankText : '展会关键字不能为空',
			maxLength : 15,
			readOnly : false
		}, {
			//colspan:2,
			name : 'exhibitionAddress',
			fieldLabel : '展会详细地址',
			//allowBlank : false,
			//blankText : '详细不能为空',
			maxLength : 200,
			labelWidth : 100,
			columnWidth:0.30,
			readOnly : false
		}, {
			name : 'id',
			fieldLabel : 'ID',
			readOnly : true,
			hidden : true
			//colspan : 8
		}
	],
	constructor : function (config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

/*
 * 定义"展馆关键字信息"申请的表单窗口
 */
Ext.define('Foss.baseinfo.exhibitionKeyword.ExhibitionKeywordAddUpdateWindow', {
	extend : 'Ext.window.Window',
	title : '新增/修改展馆关键字信息',
	width : 950,
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	modal : true,
	closeAction : 'hide',
	addUpdateForm : null,
	actionType:'add',
	getAddUpdateForm : function () {
		if (null == this.addUpdateForm) {
			this.addUpdateForm = Ext.create('Foss.baseinfo.exhibitionKeyword.ExhibitionKeywordAddUpdateForm');
		}
		return this.addUpdateForm;
	},
	loadAddUpdateForm : function (record) {
		this.getAddUpdateForm().getForm().loadRecord(record);
		this.addUpdateFormModel = record;
	},
	addUpdateFormModel : null,
	getAddUpdateFormModel : function () {
		if (null == this.addUpdateFormModel) {
			this.addUpdateFormModel = Ext.create("Foss.baseinfo.exhibitionKeyword.ExhibitionKeywordModel");
		}
		return this.addUpdateFormModel;
	},
	buttons : null,
	getButtons : function (index) {
		this.buttons = new Array();
		if (Ext.isEmpty(this.buttons)) {
			Ext.Array.include(this.buttons, this.items.items[1].items.items[0]);
			Ext.Array.include(this.buttons, this.items.items[1].items.items[2]);
			Ext.Array.include(this.buttons, this.items.items[1].items.items[4]);
		}
		if (arguments.length === 0) {
			return this.buttons;
		} else {
			return this.buttons[index];
		}
	},
	operationUrl : null,
	setOperationUrl : function (url) {
		this.operationUrl = url;
	},
	constructor : function (config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.items = [
			me.getAddUpdateForm(), {
				border : false,
				xtype : 'container',
				layout : 'column',
				margin : '15 0 15 220',
				defaults : {
					margin : '0 0 30 0'
				},
				items : [{
						xtype : 'button',
						columnWidth : .16,
						text : '取消',
						handler : function () {
							me.hide().close();
						}
					}, {
						border : false,
						columnWidth : .10,
						html : '&nbsp;'
					}, {
						xtype : 'button',
						columnWidth : .16,
						text : '重置',
						handler : function () {
							if (me.operationUrl.toString().indexOf('updateExhibitionKeyword') != -1) {
								me.loadAddUpdateForm(me.getAddUpdateFormModel());
							} else {}
							
							if (me.operationUrl.toString().indexOf('addExhibitionKeyword') != -1) {
								me.getAddUpdateForm().getForm().reset();
							} else {}
						}
					}, {
						border : false,
						columnWidth : .10,
						html : '&nbsp;'
					}, {
						columnWidth : .16,
						xtype : 'button',
						text : '保存',
						plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin', {
							seconds: 3
						}),
						listeners : {
							click : function (mine, event, options) {
								var addUpdateForm = me.getAddUpdateForm().getForm();
								
								if (addUpdateForm.isValid()) { //校验FORM是否通过校验
									
									//获取model实例
									var record = null;
									if(me.actionType == 'upd'){
										record= addUpdateForm.getRecord();//修改
									}else{
										record = Ext.create('Foss.baseinfo.exhibitionKeyword.ExhibitionKeywordModel');
									}
									addUpdateForm.updateRecord(record);
									var jsonData = {'exhibitionKeywordVo':{'exhibitionKeyword':record.data}};
									
									Ext.Ajax.request({
										url : me.operationUrl.toString(),
										jsonData : jsonData,
										success : function (response) {
											var json = Ext.decode(response.responseText);
											Ext.getCmp('T_baseinfo-exhibitionKeywordIndex_content').getExhibitionKeywordGrid().store.load();
											Ext.MessageBox.show({
												title : '信息（成功）提示',
												msg : json.message,
												width : 300,
												buttons : Ext.Msg.OK,
												callback : function () {
													me.getAddUpdateForm().up('window').hide();
												},
												icon : Ext.MessageBox.INFO
											});
											addUpdateForm = null;
										},
										exception : function (response) {
											var json = Ext.decode(response.responseText);
											Ext.MessageBox.show({
												title : '信息（失败）提示',
												msg : json.message,
												width : 300,
												buttons : Ext.Msg.OK,
												callback : function () {
													//me.getAddUpdateForm().getForm().updateRecord(recordOld);	
												Ext.getCmp('T_baseinfo-exhibitionKeywordIndex_content').getExhibitionKeywordGrid().store.load();
												me.getAddUpdateForm().up('window').hide();
												},
												icon : Ext.MessageBox.WARNING
											});
											addUpdateForm = null;
										}
									});
									tempModel = null;
								} else {
									top.Ext.MessageBox.show({
										title : '信息（失败）提示',
										msg : (function () {
											var message = "<ul>";
											/*
											var idCardError = addUpdateForm.findField('idCard').validatorMessage.message;
											if(!Ext.isEmpty(idCardError)){
											message = "<li>" + addUpdateForm.findField('idCard').validatorMessage.message + "</li>";
											}
											 */
											//addUpdateForm.getFields().filterBy(function (value) {
										   //		return value.getErrors().length > 0;
											//}).each(function (item, index, length) {
											  //  var itemId = addUpdateForm.getFields().items.itemId;
												message += "<li>" + "请检测数据是否填写完全并填写正确！" + "</li>";
											//});
											return message + "</ul>";
										})(),
										width : 300,
										buttons : Ext.Msg.OK,
										icon : Ext.MessageBox.WARNING
									});
									addUpdateForm = null;
								}
							}
						}
					}
				]
			}
		];
		me.callParent([cfg]);
	},
	listeners : {
		beforeshow : function(me, eOpts){
			var fielsds = me.getAddUpdateForm().getForm().getFields();
			if(!Ext.isEmpty(fielsds)){
				fielsds.each(function (item, index, length) {
					item.clearInvalid();
					item.unsetActiveError();
				});
			}
			fielsds = null;
		}
	}
});

/****************************************************  窗口初始化 *****************************************************/
Ext.onReady(function () {
	Ext.QuickTips.init();
	if (Ext.getCmp('T_baseinfo-exhibitionKeywordIndex_content')) {
		return;
	};
	/*
	 * 创建查询表单"FORM"
	 */
	var queryForm = Ext.create('Foss.baseinfo.exhibitionKeyword.QueryForm');
	/*
	 * 创建查询"展馆关键字信息"结果列表结果窗口
	 */
	var queryResult = Ext.create('Foss.baseinfo.exhibitionKeyword.ExhibitionKeywordGrid');
	/*
	 * 执行页面的初始化布局
	 */
	Ext.getCmp('T_baseinfo-exhibitionKeywordIndex').add(Ext.create('Ext.panel.Panel', {
		id : 'T_baseinfo-exhibitionKeywordIndex_content',
		cls : 'panelContentNToolbar',
		bodyCls : 'panelContentNToolbar-body',
		layout : 'auto',
		//获得查询表单"FORM"
		getQueryForm : function () {
			return queryForm;
		},
		//获得查询"展馆关键字信息"结果列表结果窗口
		getExhibitionKeywordGrid : function () {
			return queryResult;
		},
		items : [
			queryForm, //加入查询表单"FORM"
			queryResult //加入结果列表
		]
	}));
});
