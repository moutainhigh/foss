/**
 * 短信广告语model								Foss.common.sMSAdvertisingSlogan.SMSSloganEntityModel
 * 短信广告语store								Foss.common.sMSAdvertisingSlogan.SMSSloganEntityStore
 * 短信广告语form								Foss.common.sMSAdvertisingSlogan.QueryConditionForm
 * 短信广告语grid								Foss.common.sMSAdvertisingSlogan.QueryResultGrid
 * 短信广告语winForm								Foss.common.sMSAdvertisingSlogan.SMSSloganEntityWinForm
 * 短信广告语winGrid								Foss.common.sMSAdvertisingSlogan.SMSSloganEntityWinGrid
 * 短信广告语win									Foss.common.sMSAdvertisingSlogan.SMSSloganEntityyWin
 */
//------------------------------------常量和公用方法----------------------------------
/*
 * 初始化界面数据
 */
common.sMSAdvertisingSlogan.initWinData = function (win, viewState, formRecord, gridData, advertiseType) {
	//短信广告语和部门广告 form 加载数据
	win.down('form').loadRecord(formRecord);
	//短信广告语界面
	if (common.levelType.p === advertiseType) {
		//添加广告语按钮控制
		common.operateWinBtn(win, viewState, '');
		win.down('button').setDisabled(common.viewState.update != viewState);
		if (common.viewState.add != viewState) {
			win.down('grid').store.loadPage(1);
		} else {
			win.down('grid').store.loadData([]);
		}
	}else if(common.levelType.c === advertiseType){
		win.down('form').down('dynamicorgcombselector').setCombValue(formRecord.get('orgName'),formRecord.get('orgCode'));
	}
	return win;
};
/*
 * 保存事件
 */
common.sMSAdvertisingSlogan.submitEntity = function (win, viewState, operatEntity, advertiseType) {
	var aType = common.levelType.p === advertiseType //标识 是短信广告语DG还是部门短信广告语DDG
,
	grid = aType ? Ext.getCmp('T_common-sMSAdvertisingSloganIndex_content').getQueryGrid() : Ext.getCmp('Foss_common_sMSAdvertisingSlogan_SMSSloganEntityWinGrid_ID'),
	url = aType ? common.realPath('addSMSSloganEntity.action') : common.realPath('addSloganAppOrgEntity.action'),
	m_success = common.sMSAdvertisingSlogan.i18n('foss.common.sMSAdvertisingSlogan.MsaveSuccess'),
	m_failure = common.sMSAdvertisingSlogan.i18n('foss.common.sMSAdvertisingSlogan.MsaveFaliur'),
	m_dateError = common.sMSAdvertisingSlogan.i18n('foss.common.sMSAdvertisingSlogan.MdataError'),
	objectVo = {};
	if (aType) {
		objectVo.smssloganEntity = operatEntity;
	} else {
		objectVo.sloganAppOrgEntity = operatEntity;
	}
	//如果是修改 则调用修改 action
	if (common.viewState.update === viewState) {
		url = aType ? common.realPath('updateSMSSloganEntity.action') : common.realPath('updateSloganAppOrgEntity.action');
	}
	common.requestAjaxJson(url, {
		'objectVo' : objectVo
	}, function (result) {
		if (common.operatorCount.successV === result.objectVo.returnInt) {
			//预操作grid加载数据
			grid.store.loadPage(1);
			if (aType) {
				if (common.viewState.add === viewState) {
					//只有保存后，才能点击"添加部门广告语"；
					win.down('button').setDisabled(false);
					// 把虚拟编码 写到 win表单上
					win.formRecord.set('virtualCode', result.objectVo.smssloganEntity.virtualCode);
				}
				//查看状态下 只有 取消按钮可用 [添加部门广告语,取消]按钮分别占 0和1
				common.operateWinBtn(win, viewState, common.operateType.save);
				//form表单元素都设置成只读
				common.formFieldSetReadOnly(true, win.down('form'));
			} else {
				win.hide();
			}
			//提示保存成功
			common.showInfoMsg(common.sMSAdvertisingSlogan.i18n('foss.common.util.TfossAlertU'),m_success);
		} else {
			common.showInfoMsg(common.sMSAdvertisingSlogan.i18n('foss.common.util.TfossAlertU'),result.message);
		}
	}, function (result) {
		grid.store.loadPage(1);
		common.showInfoMsg(common.sMSAdvertisingSlogan.i18n('foss.common.util.TfossAlertU'),result.message);
	});
};
/*
 * 作废事件
 */
common.sMSAdvertisingSlogan.deleteEntityByCode = function (delType, operatRecord, grid, advertiseType) {
	var aType = common.levelType.p === advertiseType //标识 是短信广告语DG还是部门短信广告语DDG
,
	grid = aType ? Ext.getCmp('T_common-sMSAdvertisingSloganIndex_content').getQueryGrid() : Ext.getCmp('Foss_common_sMSAdvertisingSlogan_SMSSloganEntityWinGrid_ID'),
	url = aType ? common.realPath('deleteSMSSloganEntity.action') : common.realPath('deleteSloganAppOrgEntity.action'),
	objectVo = {},
	keyId = aType ? 'virtualCode' : 'id';
	selection = grid.getSelectionModel().getSelection();
	if (selection.length <= 0 && Ext.isEmpty(operatRecord)) {
		Ext.MessageBox.alert('提醒', common.sMSAdvertisingSlogan.i18n('foss.common.sMSAdvertisingSlogan.MmustChooseOne'));
	} else {
		if (!Ext.isEmpty(delType) && common.delType === delType) {
			var codeStr = '';
			//批量作废
			url = aType ? common.realPath('deleteSMSSloganEntity.action') : common.realPath('deleteSloganAppOrgEntity.action');
			for (var j = 0; j < selection.length; j++) {
				codeStr = codeStr + ',' + selection[j].get(keyId + '');
			}
			objectVo.codeStr = codeStr;
		} else {
			objectVo.codeStr = operatRecord.get(keyId + '');
		}
		Ext.MessageBox.buttonText.yes = common.sMSAdvertisingSlogan.i18n('foss.common.share.Ok');
		Ext.MessageBox.buttonText.no = common.sMSAdvertisingSlogan.i18n('foss.common.share.Cancel');
		Ext.Msg.confirm(common.sMSAdvertisingSlogan.i18n('foss.common.sMSAdvertisingSlogan.Malert'), common.sMSAdvertisingSlogan.i18n('foss.common.sMSAdvertisingSlogan.MisDeletRecord'), function (btn, text) {
			if (btn == 'yes') {
				common.requestAjaxJson(url, {
					'objectVo' : objectVo
				}, function (result) {
					grid.store.loadPage(1);
					common.showInfoMsg(common.sMSAdvertisingSlogan.i18n('foss.common.util.TfossAlertU'),common.sMSAdvertisingSlogan.i18n('foss.common.sMSAdvertisingSlogan.MdeletSuccess'));
				}, function (result) {
					common.showInfoMsg(common.sMSAdvertisingSlogan.i18n('foss.common.util.TfossAlertU'),result.message);
				});
			}
		});
	}
};
common.sMSAdvertisingSlogan.entityIsExist = function (field, fieldValue, fieldLabel, fieldNmae) {
	//sloganAppOrgEntityIsExist
	var url = common.realPath('sMSSloganEntityIsExist.action'),
	objectVo = {},
	entytyRecord = Ext.create('Foss.common.sMSAdvertisingSlogan.SMSSloganEntityModel');
	entytyRecord.set(fieldNmae + '', fieldValue);
	objectVo.pickupAndDeliverySmallZoneEntityList = entytyRecord.data;
	common.requestAjaxJson(url, {
		'objectVo' : objectVo
	}, function (result) {
		if (Ext.isEmpty(result.objectVo.pickupAndDeliverySmallZoneEntity)) {
			field.clearInvalid();
		} else {
			field.markInvalid(common.sMSAdvertisingSlogan.i18n('foss.common.sMSAdvertisingSlogan.MrepeatAlter') + fieldLabel + common.sMSAdvertisingSlogan.i18n('foss.common.sMSAdvertisingSlogan.Mend'));
		}
	}, function (result) {
		field.markInvalid(common.sMSAdvertisingSlogan.i18n('foss.common.sMSAdvertisingSlogan.MrepeatAlter') + fieldLabel + common.sMSAdvertisingSlogan.i18n('foss.common.sMSAdvertisingSlogan.Mend'));
	});
};

//------------------------------------MODEL----------------------------------
//短信广告语Model
Ext.define('Foss.common.sMSAdvertisingSlogan.SMSSloganEntityModel', {
	extend : 'Ext.data.Model',
	fields : [{
			name : 'sloganCode',
			type : 'string'
		}, //广告语代码
		{
			name : 'sloganName',
			type : 'string'
		}, //广告语名称
		{
			name : 'subSystem',
			type : 'string'
		}, //所属子系统
		{
			name : 'subSystemModule',
			type : 'string'
		}, //子系统功能模块
		{
			name : 'content',
			type : 'string'
		}, //广告语内容
		{
			name : 'sloganSort',
			type : 'string'
		}, //广告语类别
		{
			name : 'active',
			type : 'string'
		}, //是否启用
		{
			name : 'virtualCode',
			type : 'string'
		} //虚拟编码
	]
});
//部门短信广告语Model
Ext.define('Foss.common.sMSAdvertisingSlogan.SloganAppOrgEntityModel', {
	extend : 'Ext.data.Model',
	fields : [//适用部门Code
		{
			name : 'orgCode',
			type : 'string'
		},
		//部门名称
		{name:'orgName',type : 'string'},
		//部门广告语内容
		{
			name : 'sloganContent',
			type : 'string'
		},
		//广告语类型：1：短信广告语语 2：单据广告语
		{
			name : 'sloganSort',
			type : 'string'
		},
		//是否启用
		{
			name : 'active',
			type : 'string'
		},
		//所属广告语code
		{
			name : 'sloganCode',
			type : 'string'
		}
	]
});
//------------------------------------STORE----------------------------------
//短信广告语STORE
Ext.define('Foss.common.sMSAdvertisingSlogan.SMSSloganEntityStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.common.sMSAdvertisingSlogan.SMSSloganEntityModel',
	pageSize : 20,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url : common.realPath('querySMSSloganEntityByEntity.action'),
		reader : {
			type : 'json',
			root : 'objectVo.smssloganEntityList',
			totalProperty : 'totalCount'
		}
	}
});
//部门短信广告语STORE
Ext.define('Foss.common.sMSAdvertisingSlogan.SloganAppOrgEntityStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.common.sMSAdvertisingSlogan.SloganAppOrgEntityModel',
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url : common.realPath('querySloganAppOrgEntityByEntity.action'),
		reader : {
			type : 'json',
			root : 'objectVo.sloganAppOrgEntityList'
			//				,
			//			totalProperty : 'totalCount'
		}
	}
});
//------------------------------------FORM----------------------------------
//短信广告语 查询条件
Ext.define('Foss.common.sMSAdvertisingSlogan.QueryConditionForm', {
	extend : 'Ext.form.Panel',
	title : common.sMSAdvertisingSlogan.i18n('foss.common.share.queryCondition'),
	frame : true,
	collapsible : true,
	defaults : {
		margin : '8 10 5 10',
		//labelSeparator:'',
		labelWidth : 130
	},
	height : 140,
	defaultType : 'textfield',
	layout : {
		type : 'table',
		columns : 4
	},
	record : null, //绑定的model Foss.common.sMSAdvertisingSlogan.SMSSloganEntityModel
	constructor : function (config) { //构造器
		var me = this,
		cfg = Ext.apply({}, config);
		me.items = me.getItems();
		me.callParent([cfg]);
	},
	getItems : function () {
		var me = this,store = FossDataDictionary.getDataDictionaryStore('SMS_ADVERTISEMENT');
		return [{
				fieldLabel:common.sMSAdvertisingSlogan.i18n('foss.common.sMSAdvertisingSlogan.adCode'),								//广告语代码
				name: 'sloganCode'
			}, {
				fieldLabel : common.sMSAdvertisingSlogan.i18n('foss.common.sMSAdvertisingSlogan.adname'), //模块名称
				name : 'sloganName',
				xtype:'combo',colspan:3,
				store:store,
				displayField: 'valueName',
				valueField: 'valueName'
			}, {
				fieldLabel : common.sMSAdvertisingSlogan.i18n('foss.common.sMSAdvertisingSlogan.subSystem'), //所属子系统
				hidden:true,name : 'subSystem'
			}, {
				fieldLabel : common.sMSAdvertisingSlogan.i18n('foss.common.sMSAdvertisingSlogan.subFnSystem'), //子系统功能模块
				hidden:true,name : 'subSystemModule'
			},{
				xtype:'container',
				colspan:4,
				defaultType:'button',
				layout:'column',
				items:[{
					width: 75,
					columnWidth:.17,
					text : common.sMSAdvertisingSlogan.i18n('foss.common.share.reset'),
					disabled:!common.sMSAdvertisingSlogan.isPermission('sMSAdvertisingSloganIndex/sMSAdvertisingSloganQueryButton'),
					hidden:!common.sMSAdvertisingSlogan.isPermission('sMSAdvertisingSloganIndex/sMSAdvertisingSloganQueryButton'),
					handler : function() {
						this.up('form').getForm().reset();
					}
				},{
					xtype:'container',
					html:'&nbsp;',
					columnWidth:.66
				},{
					xtype:'button',
					columnWidth:.17,
					width: 75,
					text : common.sMSAdvertisingSlogan.i18n('foss.common.share.query'),
					disabled:!common.sMSAdvertisingSlogan.isPermission('sMSAdvertisingSloganIndex/sMSAdvertisingSloganQueryButton'),
					hidden:!common.sMSAdvertisingSlogan.isPermission('sMSAdvertisingSloganIndex/sMSAdvertisingSloganQueryButton'),
					cls:'yellow_button',
					handler : function() {
						var grid = Ext.getCmp('T_common-sMSAdvertisingSloganIndex_content').getQueryGrid(); //得到grid
						grid.store.loadPage(1); //用分页的moveFirst()方法
					}
				}]
			}
		];
	}
});
//短信广告语 界面form
Ext.define('Foss.common.sMSAdvertisingSlogan.SMSSloganEntityWinForm', {
	extend : 'Ext.form.Panel',
	defaultType : 'textfield',
	autoScroll : true,
	frame : true,
	layout : {
		type : 'table',
		columns : 2
	},
	formRecord : null, //绑定的model Foss.common.sMSAdvertisingSlogan.SMSSloganEntityModel
	formStore : null, //绑定的formStore Foss.common.sMSAdvertisingSlogan.PickupAndDeliverySloganAppOrgEntityStore
	viewState : null,
	constructor : function (config) { //构造器
		var me = this,
		cfg = Ext.apply({}, config);
		me.defaults = me.getDefaults(config);
		me.items = me.getItems(config);
		me.callParent([cfg]);
	},
	getDefaults : function (config) {
		var me = this;
		return {
			margin : '8 10 5 10',
			//labelSeparator:'',
			allowBlank : false,
			//			width:300,
			readOnly : (common.viewState.view === config.viewState) ? true : false
		};
	},
	getItems : function (config) {
		var me = this,store = FossDataDictionary.getDataDictionaryStore('SMS_ADVERTISEMENT');
		return [{
				fieldLabel : common.sMSAdvertisingSlogan.i18n('foss.common.sMSAdvertisingSlogan.subSystem'), //短信广告语编码
				allowBlank : true,
				hidden:true,name : 'subSystem'
			}, {
				fieldLabel : common.sMSAdvertisingSlogan.i18n('foss.common.sMSAdvertisingSlogan.subFnSystem'), //短信广告语名称
				allowBlank : true,
				hidden:true,name : 'subSystemModule'
			},{
				fieldLabel:common.sMSAdvertisingSlogan.i18n('foss.common.sMSAdvertisingSlogan.adCode'),								//广告语代码
				name: 'sloganCode',
				readOnly:true
			}, {
				fieldLabel : common.sMSAdvertisingSlogan.i18n('foss.common.sMSAdvertisingSlogan.adname'), //上机部门名称
				name : 'sloganName',
				xtype:'combo',
				store:store,
				forceSelection:true,
				displayField: 'valueName',
				valueField: 'valueName',
		    	listeners:{
		    		select:function(field,recods){
		    			field.up('form').getForm().findField('sloganCode').setValue(recods[0].get('valueCode'));
		    		}
		    	}
			}, {
				colspan : 2,
				xtype : 'textareafield',
				fieldLabel : common.sMSAdvertisingSlogan.i18n('foss.common.sMSAdvertisingSlogan.adContent'),
				name : 'content',
				allowBlank : true,
				width:518,
				height:80,
		    	maxLength:250
			}, {
				name : 'virtualCode',
				allowBlank : true,
				hidden : true
			}
		];
	}
});
//------------------------------------GRID----------------------------------
//短信广告语 查询结果grid
Ext.define('Foss.common.sMSAdvertisingSlogan.QueryResultGrid', {
	extend : 'Ext.grid.Panel',
	title : common.sMSAdvertisingSlogan.i18n('foss.common.sMSAdvertisingSlogan.TresultList'),
	sortableColumns : false,
	enableColumnHide : false,
	enableColumnMove : false,
	bodyCls:'autoHeight',
	stripeRows : true, // 交替行效果
	selType : "rowmodel", // 选择类型设置为：行选择
	emptyText : common.sMSAdvertisingSlogan.i18n('foss.common.share.queryResultIsEmpty'), //查询结果为空
	frame : true,
	//得到BBAR（分页）
	pagingToolbar : null,
	constructor : function (config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.columns = me.getColumns(config);
		me.store = me.getStore();
		me.listeners = me.getMyListeners(config);
		//添加多选框
		me.selModel = Ext.create('Ext.selection.CheckboxModel', {
				mode : 'MULTI',
				checkOnly : true
			});
		//添加头部按钮
		me.tbar = me.getTbar(config);
		//添加分页控件
		me.bbar = me.getPagingToolbar(config);
		//设置分页控件的store属性
		me.getPagingToolbar().store = me.store;
		me.callParent([cfg]);
	},
	getTbar : function (config) {
		var me = this;
		return [{
				text : common.sMSAdvertisingSlogan.i18n('foss.common.sMSAdvertisingSlogan.Badd'), //新增
				hidden:!common.sMSAdvertisingSlogan.isPermission('sMSAdvertisingSloganIndex/sMSAdvertisingSloganAddButton'),
				//hidden:!pricing.isPermission('../pricing/saveRole.action')),
				handler : function () {
					me.addSloganAppOrgEntity({}).show();
				}
			}, '-', {
				text : common.sMSAdvertisingSlogan.i18n('foss.common.sMSAdvertisingSlogan.Bcancel'), //作废
				hidden:!common.sMSAdvertisingSlogan.isPermission('sMSAdvertisingSloganIndex/sMSAdvertisingSloganVoidButton'),
				//hidden:!pricing.isPermission('../pricing/deleteRole.action')),
				handler : function () {
					common.sMSAdvertisingSlogan.deleteEntityByCode(common.delType, null, Ext.getCmp('T_common-sMSAdvertisingSloganIndex_content').getQueryGrid(), common.levelType.p);
				}
			}
		];
	},
	getPagingToolbar : function (config) {
		if (Ext.isEmpty(this.pagingToolbar)) {
			this.pagingToolbar = Ext.create('Deppon.StandardPaging', {
					store : this.store,
					pageSize : 20
				});
		}
		return this.pagingToolbar;
	},
	//得到短信广告语编辑窗体 得到短信广告语编辑窗体,查看状态viewState："ADD"新增,"UPDATE"修改,"VIEW"查看
	getSloganAppOrgEntityWin : function (win, title, viewState, param) {
		var formRecord = Ext.isEmpty(param.formRecord) ? Ext.create('Foss.common.sMSAdvertisingSlogan.SMSSloganEntityModel') : param.formRecord;
		if (Ext.isEmpty(win)) {
			win = Ext.create('Foss.common.sMSAdvertisingSlogan.SMSSloganEntityyWin', {
					'title' : title,
					'viewState' : viewState,
					'sourceGrid' : this,
					'formRecord' : formRecord
				});
		}
		//加载数据
		return common.sMSAdvertisingSlogan.initWinData(win, viewState, formRecord, [], common.levelType.p);
	},
	addSloganAppOrgEntityWin : null, //新增基短信广告语
	addSloganAppOrgEntity : function (param) {
		return this.getSloganAppOrgEntityWin(this.addSloganAppOrgEntityWin, common.sMSAdvertisingSlogan.i18n('foss.common.sMSAdvertisingSlogan.TaddAd'), common.viewState.add, param);
	},
	updateSloganAppOrgEntityWin : null, //修改基短信广告语
	updateSloganAppOrgEntity : function (param) {
		return this.getSloganAppOrgEntityWin(this.updateSloganAppOrgEntityWin, common.sMSAdvertisingSlogan.i18n('foss.common.sMSAdvertisingSlogan.TalterAd'), common.viewState.update, param);
	},
	viewSloganAppOrgEntityWin : null, //查看基短信广告语
	viewSloganAppOrgEntity : function (param) {
		return this.getSloganAppOrgEntityWin(this.viewSloganAppOrgEntityWin, common.sMSAdvertisingSlogan.i18n('foss.common.sMSAdvertisingSlogan.TviewAd'), common.viewState.view, param);
	},
	getMyListeners : function () {
		var me = this;
		return {
			//增加滚动条事件，防止出现滚动条后却不能用
			scrollershow : function (scroller) {
				if (scroller && scroller.scrollEl) {
					scroller.clearManagedListeners();
					scroller.mon(scroller.scrollEl, 'scroll', scroller.onElScroll, scroller);
				}
			},
			//查看 短信广告语
			itemdblclick : function (view, record) {
				var param = {};
				param.formRecord = record;
				me.viewSloganAppOrgEntity(param).show();
			}
		};
	},
	getStore : function () {
		return Ext.create('Foss.common.sMSAdvertisingSlogan.SMSSloganEntityStore', {
			autoLoad : false,
			pageSize : 20,
			listeners : {
				beforeload : function (store, operation, eOpts) {
					var queryForm = Ext.getCmp('T_common-sMSAdvertisingSloganIndex_content').getQueryForm().getForm(); //得到查询的FORM表单
					queryForm.updateRecord(queryForm.record);
					var entity = queryForm.record.data;
					if (queryForm != null) {
						Ext.apply(operation, {
							params : {
								//广告代码
								'objectVo.smssloganEntity.sloganCode' : entity.sloganCode,
								//广告名称
								'objectVo.smssloganEntity.sloganName' : entity.sloganName,
								//所属子系统
								'objectVo.smssloganEntity.subSystem' : entity.subSystem,
								//子系统功能模块
								'objectVo.smssloganEntity.subSystemModule' : entity.subSystemModule
							}
						});
					}
				}
			}
		});
	},
	getColumns : function (config) {
		var me = this;
		return [{
				align : 'center',
				xtype : 'actioncolumn',
				text : common.sMSAdvertisingSlogan.i18n('foss.common.sMSAdvertisingSlogan.Boperator'), //操作
				items : [{
						iconCls : 'deppon_icons_edit',
						tooltip : common.sMSAdvertisingSlogan.i18n('foss.common.sMSAdvertisingSlogan.Bupdate'),
						handler : function (grid, rowIndex, colIndex) {
							var param = {};
							param.formRecord = grid.getStore().getAt(rowIndex);
							me.updateSloganAppOrgEntity(param).show();
						}
					}, {
						iconCls : 'deppon_icons_cancel',
						tooltip : common.sMSAdvertisingSlogan.i18n('foss.common.sMSAdvertisingSlogan.Bcancel'),
						//                disabled:common.actioncolumnDisabled,
						handler : function (grid, rowIndex, colIndex) {
							common.sMSAdvertisingSlogan.deleteEntityByCode(null, grid.getStore().getAt(rowIndex), grid, common.levelType.p);
						}
					}
				]
			}, {
				text : common.sMSAdvertisingSlogan.i18n('foss.common.sMSAdvertisingSlogan.adCode'), //短信广告语编码
				flex : 1,
				dataIndex : 'sloganCode'
			}, {
				text : common.sMSAdvertisingSlogan.i18n('foss.common.sMSAdvertisingSlogan.adname'), //短信广告语名称
				flex : 1,
				dataIndex : 'sloganName'
			}, {
				text : common.sMSAdvertisingSlogan.i18n('foss.common.sMSAdvertisingSlogan.subSystem'), //上机部门名称
				flex : 1,
				hidden:true,dataIndex : 'subSystem'
			}, {
				text : common.sMSAdvertisingSlogan.i18n('foss.common.sMSAdvertisingSlogan.subFnSystem'), //子系统功能模块
				flex : 1,
				hidden:true,dataIndex : 'subSystemModule'
			}
		];
	}
});
// 部门短信广告语  grid
Ext.define('Foss.common.sMSAdvertisingSlogan.SMSSloganEntityWinGrid', {
	extend : 'Ext.grid.Panel',
	id : 'Foss_common_sMSAdvertisingSlogan_SMSSloganEntityWinGrid_ID',
	title : common.sMSAdvertisingSlogan.i18n('foss.common.sMSAdvertisingSlogan.TdeptAd'),
	sortableColumns : false,
	enableColumnHide : false,
	enableColumnMove : false,
	stripeRows : true, // 交替行效果
	selType : "rowmodel", // 选择类型设置为：行选择
	emptyText : common.sMSAdvertisingSlogan.i18n('foss.common.share.queryResultIsEmpty'), //查询结果为空
	viewState : null, //查看状态
	frame : true,
	//得到部门短信广告语编辑窗体 得到部门短信广告语编辑窗体,查看状态viewState："ADD"新增,"UPDATE"修改,"VIEW"查看
	getBillSloganAppOrgWin : function (win, title, viewState, param) {
		var formRecord = Ext.isEmpty(param.formRecord) ? Ext.create('Foss.common.sMSAdvertisingSlogan.SloganAppOrgEntityModel') : param.formRecord;
		var gridData = Ext.isEmpty(param.gridDate) ? [] : param.gridDate;
		if (Ext.isEmpty(win)) {
			win = Ext.create('Foss.common.sMSAdvertisingSlogan.SloganAppOrgEntityWin', {
					'title' : title,
					'sourceGrid' : this,
					'viewState' : viewState,
					'formRecord' : formRecord,
					'gridDate' : gridData
				});
		}
		//加载数据
		return common.sMSAdvertisingSlogan.initWinData(win, viewState, formRecord, gridData, common.levelType.c);
	},
	addBillSloganAppOrgWin : null, //新增基部门短信广告语
	addBillSloganAppOrg : function (param) {
		return this.getBillSloganAppOrgWin(this.addBillSloganAppOrgWin, common.sMSAdvertisingSlogan.i18n('foss.common.sMSAdvertisingSlogan.TaddDeptAd'), common.viewState.add, param);
	},
	updateBillSloganAppOrgWin : null, //修改基部门短信广告语
	updateBillSloganAppOrg : function (param) {
		return this.getBillSloganAppOrgWin(this.updateBillSloganAppOrgWin, common.sMSAdvertisingSlogan.i18n('foss.common.sMSAdvertisingSlogan.TalterDeptAd'), common.viewState.update, param);
	},
	viewBillSloganAppOrgWin : null, //查看基部门短信广告语
	viewBillSloganAppOrg : function (param) {
		return this.getBillSloganAppOrgWin(this.viewBillSloganAppOrgWin, common.sMSAdvertisingSlogan.i18n('foss.common.sMSAdvertisingSlogan.TviewDeptAd'), common.viewState.view, param);
	},
	constructor : function (config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.columns = me.getColumns(config);
		me.store = me.getGridStore(config);
		me.listeners = me.getMyListeners(config);
		//添加头部按钮
		me.tbar = me.getTbar();
	    //添加多选框
		me.selModel = Ext.create('Ext.selection.CheckboxModel',{mode:'MULTI',checkOnly:true});
		//添加底部按钮
		me.dockedItems = me.getMyDockedItems(config);
		me.callParent([cfg]);
	},
	//监听事件
	getMyListeners : function (config) {
		var me = this;
		return {
			//增加滚动条事件，防止出现滚动条后却不能用
			scrollershow : function (scroller) {
				if (scroller && scroller.scrollEl) {
					scroller.clearManagedListeners();
					scroller.mon(scroller.scrollEl, 'scroll', scroller.onElScroll, scroller);
				}
			},
			//查看 部门短信广告语
			itemdblclick : function (view, record) {
				var param = {};
				param.formRecord = record;
				me.viewBillSloganAppOrg(param).show();
			}
		};
	},
	getMyDockedItems : function (config) {
		var me = this;
		return [{
            xtype: 'toolbar',
            dock: 'bottom',
            items: [{
				text:common.sMSAdvertisingSlogan.i18n('foss.common.sMSAdvertisingSlogan.Bcancel'),								//作废
				//hidden:!pricing.isPermission('../pricing/deleteRole.action')),
				handler :function(){
					common.sMSAdvertisingSlogan.deleteEntityByCode(common.delType, null, me, common.levelType.c);
				}
			}]
        }];
	},
	getTbar : function (config) {
		var me = this;
		return [{
				text : common.sMSAdvertisingSlogan.i18n('foss.common.sMSAdvertisingSlogan.BaddDeptAd'), //添加网点
				name : 'grid_addDeptBtn_name',
				//hidden:!pricing.isPermission('../pricing/saveRole.action')),
				handler : function () {
					var param = {},
					formRecord = Ext.create('Foss.common.sMSAdvertisingSlogan.SloganAppOrgEntityModel');
					param.formRecord = formRecord;
					formRecord.set('sloganCode', me.up('window').formRecord.get('virtualCode'));
					me.addBillSloganAppOrg(param).show();
				}
			}
		];
	},
	//表格数据源
	getGridStore : function (config) {
		var me = this;
		return Ext.create('Foss.common.sMSAdvertisingSlogan.SloganAppOrgEntityStore', {
			autoLoad : false,
			listeners : {
				beforeload : function (store, operation, eOpts) {
					Ext.apply(operation, {
						params : {
							//代理公司 虚拟code sloganCode
							'objectVo.sloganAppOrgEntity.sloganCode' : me.up('window').formRecord.get('virtualCode')
						}
					});
				}
			}
		});
	},
	//表格数据列
	getColumns : function (config) {
		var me = this;
		return [{
				align : 'center',
				xtype : 'actioncolumn',
				hidden : (common.viewState.view === config.viewState),
				text : common.sMSAdvertisingSlogan.i18n('foss.common.sMSAdvertisingSlogan.Boperator'), //操作
				items : [{
						iconCls : 'deppon_icons_edit',
						tooltip : common.sMSAdvertisingSlogan.i18n('foss.common.sMSAdvertisingSlogan.Bupdate'),
						//                disabled:common.actioncolumnDisabled,
						handler : function (grid, rowIndex, colIndex) {
							var param = {};
							var record = grid.getStore().getAt(rowIndex); //选中的计费规则数据
							param.formRecord = record;
							me.updateBillSloganAppOrg(param).show();
						}
					}, {
						iconCls : 'deppon_icons_cancel',
						tooltip : common.sMSAdvertisingSlogan.i18n('foss.common.sMSAdvertisingSlogan.Bcancel'),
						//                disabled:common.actioncolumnDisabled,
						handler : function (grid, rowIndex, colIndex) {
							common.sMSAdvertisingSlogan.deleteEntityByCode(null, grid.getStore().getAt(rowIndex), grid, common.levelType.c);
						}
					}
				]
			}, {
				text : common.sMSAdvertisingSlogan.i18n('foss.common.sMSAdvertisingSlogan.deptName'), //适用部门
				flex : 1,
				dataIndex : 'orgName'
			}, {
				text : '广告内容', //广告语内容
				flex : 1,
				dataIndex : 'sloganContent'
			}
		];
	}
});
//------------------------------------ONREADY----------------------------------
/**
 * 程序入口方法
 */
Ext.onReady(function () {
	Ext.QuickTips.init();
	if (Ext.getCmp('T_common-sMSAdvertisingSloganIndex_content')) {
		return;
	}
	var queryForm = Ext.create('Foss.common.sMSAdvertisingSlogan.QueryConditionForm', {
			'record' : Ext.create('Foss.common.sMSAdvertisingSlogan.SMSSloganEntityModel')
		}); //查询FORM
	var queryGrid = Ext.create('Foss.common.sMSAdvertisingSlogan.QueryResultGrid'); //查询结果显示列表
	Ext.getCmp('T_common-sMSAdvertisingSloganIndex').add(Ext.create('Ext.panel.Panel', {
		id : 'T_common-sMSAdvertisingSloganIndex_content',
		cls : 'panelContentNToolbar',
		bodyCls : 'panelContentNToolbar-body',
		//获得查询FORM
		getQueryForm : function () {
			return queryForm;
		},
		//获得查询结果GRID
		getQueryGrid : function () {
			return queryGrid;
		},
		items : [queryForm, queryGrid, {
				xtype : 'button',
				text : common.sMSAdvertisingSlogan.i18n('foss.common.sMSAdvertisingSlogan.Bcancel'), //作废
				hidden:!common.sMSAdvertisingSlogan.isPermission('sMSAdvertisingSloganIndex/sMSAdvertisingSloganVoidButton'),
				//hidden:!pricing.isPermission('../pricing/deleteRole.action')),
				handler : function () {
					common.sMSAdvertisingSlogan.deleteEntityByCode(common.delType, null, queryGrid, common.levelType.p);
				}
			}
		]
	}));
});
//------------------------------------WINDOW--------------------------------
//短信广告语界面win
Ext.define('Foss.common.sMSAdvertisingSlogan.SMSSloganEntityyWin', {
	extend : 'Ext.window.Window',
	title : common.sMSAdvertisingSlogan.i18n('foss.common.sMSAdvertisingSlogan.TaddAd'), //新增短信广告语   默认新增
	closable : true,
	modal : true,
	resizable : false,
	closeAction : 'hide',
	width : 600,
	height : 550,
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	listeners : {
		beforehide : function (me) {
			//清空 有ID的组件
			var winGrid = Ext.getCmp('Foss_common_sMSAdvertisingSlogan_SMSSloganEntityWinGrid_ID');
			if (!Ext.isEmpty(winGrid)) {
				winGrid.destroy();
			}
		}
	},
	viewState : common.viewState.add, //查看状态,默认为新增
	editForm : null, //短信广告语表单Form
	editGrid : null, //短信广告语表格Grid
	formRecord : null, //短信广告语实体 Foss.common.BusinessPartnerModel
	gridDate : null, //短信广告语 网点信息数组  [Foss.common.OuterBranchModel]
	constructor : function (config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.editForm = Ext.create('Foss.common.sMSAdvertisingSlogan.SMSSloganEntityWinForm', {
				'height' : 150,
				'viewState' : config.viewState,
				'formRecord' : config.formRecord
			});
		me.editGrid = Ext.create('Foss.common.sMSAdvertisingSlogan.SMSSloganEntityWinGrid', {
				'height' : 250,
				'viewState' : config.viewState
			});
		me.items = [me.editForm, me.editGrid];
		me.fbar = me.getFbar(config);
		me.callParent([cfg]);
	},
	initComponent : function () {
		var me = this;
		this.callParent();
	},
	//操作界面上的按钮
	getFbar : function (config) {
		var me = this;
		return [{
				text : common.sMSAdvertisingSlogan.i18n('foss.common.share.Cancel'),
				handler : function () {
					me.hide();
				}
			}, {
				text : common.sMSAdvertisingSlogan.i18n('foss.common.share.reset'),
				disabled : (common.viewState.view === config.viewState),
				handler : function () {
					// 重置
					common.formReset([me.editForm.getForm()], [me.formRecord]);
				}
			}, {
				text : common.sMSAdvertisingSlogan.i18n('foss.common.sMSAdvertisingSlogan.Bsave'),
				disabled : (common.viewState.view === config.viewState),
				handler : function () {
					var editForm = me.editForm.getForm();
					//实时校验的 结果是否通过,判断偏线代理必填项是否填写并全部填写合法
					if (!editForm.isValid()) {
						common.showInfoMsg(common.sMSAdvertisingSlogan.i18n('foss.common.util.TfossAlertU'),common.sMSAdvertisingSlogan.i18n('foss.common.sMSAdvertisingSlogan.McheckIsRight'));
						return;
					}
					editForm.updateRecord(me.formRecord);
					common.sMSAdvertisingSlogan.submitEntity(me, me.viewState, me.formRecord.data, common.levelType.p);
				}
			}
		];
	}
});
//部门短信广告语界面win
Ext.define('Foss.common.sMSAdvertisingSlogan.SloganAppOrgEntityWin', {
	extend : 'Ext.window.Window',
	title : common.sMSAdvertisingSlogan.i18n('foss.common.sMSAdvertisingSlogan.TaddDeptAd'), //新增部门广告语   默认新增
	closable : true,
	modal : true,
	resizable : false,
	closeAction : 'hide',
	width : 300,
	height : 200,
	viewState : common.viewState.add, //查看状态,默认为新增
	editForm : null, //短信广告语表单Form
	editGrid : null, //短信广告语表格Grid
	formRecord : null, //短信广告语实体 Foss.common.BusinessPartnerModel
	gridDate : null, //短信广告语 网点信息数组  [Foss.common.OuterBranchModel]
	constructor : function (config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.items = [{
				xtype : 'form',
				layout : 'column',
				defaults : {
					allowBlank : false,
					readOnly : common.viewState.view === config.viewState
				},
				items : [{
						xtype : 'dynamicorgcombselector',
						name : 'orgCode',
						fieldLabel : common.sMSAdvertisingSlogan.i18n('foss.common.sMSAdvertisingSlogan.deptName')
					}, {
						xtype : 'textareafield',
						name : 'sloganContent',
						fieldLabel : common.sMSAdvertisingSlogan.i18n('foss.common.sMSAdvertisingSlogan.adContent'),
						height : 60,
				    	maxLength:250
					}
				]
			}
		];
		me.fbar = me.getFbar(config);
		me.callParent([cfg]);
	},
	initComponent : function () {
		var me = this;
		this.callParent();
	},
	//操作界面上的按钮
	getFbar : function (config) {
		var me = this;
		return [{
				text : common.sMSAdvertisingSlogan.i18n('foss.common.share.Cancel'),
				handler : function () {
					me.hide();
				}
			}, {
				text : common.sMSAdvertisingSlogan.i18n('foss.common.share.reset'),
				
				disabled : (common.viewState.view === config.viewState),
				handler : function () {
					common.formReset([me.down('form').getForm()], [me.formRecord]);
				}
			}, {
				text : common.sMSAdvertisingSlogan.i18n('foss.common.sMSAdvertisingSlogan.Bsave'),
				disabled : (common.viewState.view === config.viewState),
				handler : function () {
					var editForm = me.down('form').getForm();
					//实时校验的 结果是否通过,判断偏线代理必填项是否填写并全部填写合法
					if (!editForm.isValid()) {
						common.showInfoMsg(common.sMSAdvertisingSlogan.i18n('foss.common.util.TfossAlertU'),common.sMSAdvertisingSlogan.i18n('foss.common.sMSAdvertisingSlogan.McheckIsRight'));
						return;
					}
					editForm.updateRecord(me.formRecord);
					common.sMSAdvertisingSlogan.submitEntity(me, me.viewState, me.formRecord.data, common.levelType.c);
				}
			}
		];
	}
});

