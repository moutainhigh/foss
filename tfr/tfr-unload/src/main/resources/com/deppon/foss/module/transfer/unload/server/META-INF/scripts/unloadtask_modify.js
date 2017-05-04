//定义左上方form
Ext.define('Foss.unload.unloadtaskmodify.billAddForm', {
	extend : 'Ext.form.Panel',
	layout : 'column',
	items : [{
		xtype : 'textfield',
		readOnly : true,
		labelWidth : 115,
		name : 'unloadTaskNo',
		fieldLabel : unload.unloadtaskmodify.i18n('foss.unload.unloadtaskmodify.basicInfoForm.unloadTaskNoLabel'),//卸车任务编号
		margin : '5 10 5 0',
		columnWidth : .4
	}, {
		fieldLabel : unload.unloadtaskmodify.i18n('foss.unload.unloadtaskaddnew.billInfoGrid.vehicleNoColumn')/*'车牌号'*/,
		name : 'vehicleNo',
		xtype : 'commontruckselector',
		labelWidth : 60,
		readOnly : true,
		xtype : 'textfield',
		columnWidth : .30,
		margin : '5 10 5 0'
	}, {
		xtype : 'checkbox',
		boxLabel : '是否商务专递',
		name: 'isAir',
		margin: '5 0 0 0',
		columnWidth : .30,
		handler: function() {
			var form = this.up('form').getForm();
			var values = form.getValues();
			if (!values.isAir) {
				form.findField('isAir').setDisabled(false);	
			} else {
				form.findField('isAir').setDisabled(true);
			}
		}
	},Ext.create('Foss.commonSelector.PlatformSelector', {
			fieldLabel : unload.unloadtaskmodify.i18n('foss.unload.unloadtaskaddnew.billInfoForm.platformNoLabel')/*'月台号'*/,
			displayField : 'platformCode',// 显示名称
			valueField : 'platformCode',// 值
			queryParam : 'platformVo.platformEntity.platformCode',// 查询参数
			showContent : '{platformCode}',// 显示表格列
			columnWidth : .35,
			orgCode : unload.unloadtaskmodify.superOrgCode,
			labelWidth : 85,
			name : 'platformCode',
			margin : '5 10 5 10',
			constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				me.store = Ext.create('Foss.commonSelector.PlatformStore');
				me.callParent([cfg]);
			}
		}),{
		fieldLabel : unload.unloadtaskmodify.i18n('foss.unload.unloadtaskaddnew.billInfoForm.billNoLabel'),//'交接单/配载单编号',
		name : 'billNo',
		xtype : 'textfield',
		labelWidth : 115,
		columnWidth : .40,
		margin : '5 10 15 0'
	}, {
		xtype : 'button',
		text : unload.unloadtaskmodify.i18n('foss.unload.unloadtaskaddnew.billInfoForm.addButtonText')/*'添加'*/,
		margin : '0 0 10 0',
		id : 'Foss_unload_unloadtaskmodify_addButton_ID',
		cls : 'btnAfterTextfield',
		columnWidth : .13,
		handler : function(){
			var form = this.up('form').getForm();
			//获取车牌号控件
			var vehicleNoCmp = form.findField('vehicleNo');
			//获取月台号控件
			var plaCmp = form.findField('platformCode');
			//获取单据编号控件
			var billNoCmp = form.findField('billNo');
			//获取编号
			var billNo = billNoCmp.getValue();
			if(Ext.isEmpty(billNo)){
				Ext.ux.Toast.msg(unload.unloadtaskmodify.i18n('foss.unload.unloadtaskaddnew.alertInfo.alertTitle')/*'提示'*/, 
				unload.unloadtaskmodify.i18n('foss.unload.unloadtaskaddnew.alertInfo.inputBillNoAlertInfo'), 'error', 2000);
				billNoCmp.reset();
				billNoCmp.focus();
			}else{
				var store = unload.unloadtaskmodify.billInfoGrid.store;
				//看该单号是否已经被添加
				if(store.findRecord('billNo',billNo,0,false,true,true) != null){
					Ext.ux.Toast.msg(unload.unloadtaskmodify.i18n('foss.unload.unloadtaskaddnew.alertInfo.alertTitle')/*'提示'*/, 
					unload.unloadtaskmodify.i18n('foss.unload.unloadtaskaddnew.alertInfo.billAlreadyAddedAlertInfo'), 'error', 2000);
					billNoCmp.focus();
					return;
				}
				var myMask = new Ext.LoadMask(unload.unloadtaskmodify.billAddForm, {
					msg : '...'
				});
				myMask.show();
				//获取单据信息
				Ext.Ajax.request({
					url : unload.realPath('queryBillInfoByBillNo.action'),
					params : {'unloadTaskVo.billNo': billNo,
						      'unloadTaskVo.unloadTaskNo':unload.unloadtaskmodify.unloadTaskNo,
						      'unloadTaskVo.unloadType':unload.unloadtaskmodify.unloadType},
					success : function(response){
						var result = Ext.decode(response.responseText);
						//获取月台号
						var platformCode = result.unloadTaskVo.platformCode;
						plaCmp.setValue(platformCode);
						//获取单据信息
						var billInfo = result.unloadTaskVo.billInfo;
						if(billInfo == null){
							Ext.ux.Toast.msg(unload.unloadtaskmodify.i18n('foss.unload.unloadtaskaddnew.alertInfo.alertTitle')/*'提示'*/, 
							unload.unloadtaskmodify.i18n('foss.unload.unloadtaskaddnew.alertInfo.billNotFoundAlertInfo'), 'error', 2500);
							billNoCmp.focus();
							myMask.hide();
							return;
						}
						//修改时不允许更改车牌号，所以不允许添加车牌号不同的单据
						if(billInfo.vehicleNo != vehicleNoCmp.getValue()){
							Ext.ux.Toast.msg(unload.unloadtaskmodify.i18n('foss.unload.unloadtaskaddnew.alertInfo.alertTitle')/*'提示'*/, 
							unload.unloadtaskmodify.i18n('foss.unload.unloadtaskmodify.alertInfo.vehicleNoNotTheSameAlertInfo'), 'error', 2500);
							billNoCmp.focus();
							myMask.hide();
							return;
						}
						//不允许添加集配交接单
						if(billInfo.billType == 'HANDOVER'
							&& billInfo.handOverType == 'LONG_DISTANCE_HANDOVER'){
							Ext.ux.Toast.msg(unload.unloadtaskmodify.i18n('foss.unload.unloadtaskaddnew.alertInfo.alertTitle')/*'提示'*/, 
							unload.unloadtaskmodify.i18n('foss.unload.unloadtaskaddnew.alertInfo.canNotAddLongDisHandOverBillAlertInfo')/*'提示'*/,  'error', 2500);
							billNoCmp.focus();
							myMask.hide();
							return;
						}
						//如果列表内不为空，则取出一条，对比单据类型、车牌号
						if(store.getCount() != 0){
							var record = store.getAt(0);
							//如果单据类型不一致
							if(record.get('billType') != billInfo.billType){
								Ext.ux.Toast.msg(unload.unloadtaskmodify.i18n('foss.unload.unloadtaskaddnew.alertInfo.alertTitle')/*'提示'*/, 
								unload.unloadtaskmodify.i18n('foss.unload.unloadtaskaddnew.alertInfo.billTypeNotTheSameAlertInfo'), 'error', 2500);
								billNoCmp.focus();
								myMask.hide();
								return;
							}
							if(record.get('vehicleNo') != billInfo.vehicleNo){
								Ext.ux.Toast.msg(unload.unloadtaskmodify.i18n('foss.unload.unloadtaskaddnew.alertInfo.alertTitle')/*'提示'*/, 
								unload.unloadtaskmodify.i18n('foss.unload.unloadtaskaddnew.alertInfo.vehicleNoNotTheSameAlertInfo'), 'error', 2500);
								billNoCmp.focus();
								myMask.hide();
								return;
							}
							// 2015/9/11  272681  如果商务专递单据的路线不一致，对比路线
							if(record.get('orgCode') != billInfo.orgCode || record.get('expressArriveCode') != billInfo.expressArriveCode){
								Ext.ux.Toast.msg(unload.unloadtaskmodify.i18n('foss.unload.unloadtaskaddnew.alertInfo.alertTitle'), 
								unload.unloadtaskmodify.i18n('foss.unload.unloadtaskaddnew.alertInfo.billLineNotTheSameAlertInfo'), 'error', 2500);
								billNoCmp.focus();
								myMask.hide();
								return;
							}
						}
						//create一条model
						var billRecord = Ext.ModelManager.create(billInfo, 'Foss.unload.unloadtaskmodify.billInfoModel');
						//插入列表
						store.insert(store.getCount(),billRecord);
						//若修改前没有，则视为新增
						if(unload.unloadtaskmodify.oldBillMap.get(billNo) == null){
							unload.unloadtaskmodify.addedBillMap.add(billNo,billInfo);
						}
						//回到单据编号
						billNoCmp.focus();
						billNoCmp.reset();
						myMask.hide();
					},
					exception : function(response){
						var result = Ext.decode(response.responseText);
				    	top.Ext.MessageBox.alert(unload.unloadtaskaddnew.i18n('foss.unload.unloadtaskaddnew.alertInfo.alertTitle'),
				    	result.message);
				    	myMask.hide();
					}
				});
			}
		}
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
});

//定义左下方列表之Model
Ext.define('Foss.unload.unloadtaskmodify.billInfoModel', {
	extend : 'Ext.data.Model',
	//定义字段
	fields : [{
		name : 'vehicleNo',
		type : 'string'
	}, {
		name : 'billNo',
		type : 'string'
	}, {
		name : 'billType',
		type : 'string'
	}, {
		name : 'volume',
		type : 'number'
	}, {
		name : 'weight',
		type : 'number'
	}, {
		name : 'waybillTotal',
		type : 'number'
	}, {
		name : 'pieces',
		type : 'number'
	}, {
		name : 'orgCode',
		type : 'string'
	}, {
		name : 'expressArriveCode',
		type : 'string'
	}]
});

//定义左下方列表之store
Ext.define('Foss.unload.unloadtaskmodify.billInfoStore', {
	extend : 'Ext.data.Store',
	// 绑定一个模型
	model : 'Foss.unload.unloadtaskmodify.billInfoModel',
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
});

//定义左下方列表之grid
Ext.define('Foss.unload.unloadtaskmodify.billInfoGrid', {
	extend : 'Ext.grid.Panel',
	height : 550,
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.unload.unloadtaskmodify.billInfoStore'),
		me.callParent([cfg]);
	},
	columns : [{
		dataIndex : 'vehicleNo',
		align : 'center',
		flex : 1,
		xtype : 'ellipsiscolumn',
		text : unload.unloadtaskmodify.i18n('foss.unload.unloadtaskaddnew.billInfoGrid.vehicleNoColumn')/*'车牌号'*/
	}, {
		dataIndex : 'billNo',
		align : 'center',
		flex : 1,
		text : unload.unloadtaskmodify.i18n('foss.unload.unloadtaskaddnew.billInfoGrid.billNoColumn')//'交接单/配载单编号'
	}, {
		dataIndex : 'volume',
		align : 'center',
		flex : 1,
		text : unload.unloadtaskmodify.i18n('foss.unload.unloadtaskaddnew.billInfoGrid.volumeColumn')//'体积(立方米)'
	}, {
		dataIndex : 'weight',
		align : 'center',
		flex : 1,
		text : unload.unloadtaskmodify.i18n('foss.unload.unloadtaskaddnew.billInfoGrid.weightColumn')//'重量(千克)'
	}, {
		dataIndex : 'waybillTotal',
		align : 'center',
		flex : 1,
		text : unload.unloadtaskmodify.i18n('foss.unload.unloadtaskaddnew.billInfoGrid.totalWaybillColumn')/*'票数'*/
	}, {
		dataIndex : 'pieces',
		align : 'center',
		flex : 1,
		text : unload.unloadtaskmodify.i18n('foss.unload.unloadtaskaddnew.billInfoGrid.piecesColumn')/*'件数'*/
	},{
		xtype : 'actioncolumn',
		width : 60,
		text : unload.unloadtaskmodify.i18n('foss.unload.unloadtaskaddnew.loadManGrid.actionColumn')/*'操作'*/,
		align : 'center',
		items : [ {
			tooltip : unload.unloadtaskmodify.i18n('foss.unload.unloadtaskaddnew.loadManGrid.deleteTipText')/*'删除'*/,
			iconCls : 'deppon_icons_remove',
			handler : function(grid, rowIndex, colIndex) {
				var record = grid.store.getAt(rowIndex);
				grid.store.removeAt(rowIndex);
				//如果修改前有此单据，则视为删除的bill
				if(unload.unloadtaskmodify.oldBillMap.get(record.get('billNo')) != null){
					unload.unloadtaskmodify.deletedBillMap.add(record.get('billNo'),record.data);
				}
			}
		}]
	}]
});

//左上方form
unload.unloadtaskmodify.billAddForm = Ext.create('Foss.unload.unloadtaskmodify.billAddForm');
//左下方grid
unload.unloadtaskmodify.billInfoGrid = Ext.create('Foss.unload.unloadtaskmodify.billInfoGrid');

//定义左侧控件Panel
Ext.define('Foss.unload.unloadtaskmodify.billInfoPanel',{
	extend : 'Ext.panel.Panel',
	title : unload.unloadtaskmodify.i18n('foss.unload.unloadtaskaddnew.billInfoForm.title'),//'交接单/配载单信息',
	layout : 'auto',
	frame : true,
	collapsible : true,
	animCollapse : true,
	items : [
		unload.unloadtaskmodify.billAddForm,
		unload.unloadtaskmodify.billInfoGrid
	]
});

//定义右上方form
Ext.define('Foss.unload.unloadtaskmodify.loadManForm', {
	extend : 'Ext.form.Panel',
	layout : 'column',
	items : [{
		xtype : 'container',
		margin : '20 0 0 0',
		html : unload.unloadtaskmodify.i18n('foss.unload.unloadtaskaddnew.loadManForm.loadManLabel'),//'卸车员:',
		columnWidth : 1
	},{
		name : 'loaderCode',
		fieldLabel : '',
		xtype : 'commonemployeeselector',
		parentOrgCode : unload.unloadtaskmodify.superOrgCode,
		displayField : 'empName',// 显示名称
		valueField : 'empCode',// 值
		columnWidth : .45,
		margin : '5 10 15 0'
	},{
		xtype : 'button',
		text : unload.unloadtaskmodify.i18n('foss.unload.unloadtaskaddnew.loadManForm.addLoadManButtonText')/*'添加卸车员'*/,
		margin : '0 0 10 0',
		columnWidth : .30,
		id : 'Foss_unload_unloadtaskmodify_addLoaderButton_ID',
		cls : 'btnAfterTextfield',
		handler : function(){
			var loadManCmp = this.up('form').getForm().findField('loaderCode');
			if(!Ext.isEmpty(loadManCmp.getValue())){
				var loaderCode = loadManCmp.getValue();
				var store = unload.unloadtaskmodify.loadManGrid.store;
				if(store.findRecord('loaderCode',loaderCode,0,false,true,true) == null){
					var loadMan = {
						'loaderCode' : loaderCode,
						'loaderName' : loadManCmp.store.findRecord('empCode',loaderCode,0,false,true,true).get('empName')
					}
					var loadManRecord = Ext.ModelManager.create(loadMan,'Foss.unload.unloadtaskmodify.loadManModel');
					//插入下方表格
					store.insert(store.getCount(),loadManRecord);
					//如果修改前的map中无此loader，则视为新增
					if(unload.unloadtaskmodify.oldLoaderMap.get(loaderCode) == null){
						unload.unloadtaskmodify.addedLoaderMap.add(loaderCode,loadMan);
					}
					//卸车员重新获取焦点，方便反复添加
					loadManCmp.reset();
					var countCmp = Ext.getCmp('Foss_unload_unloadtaskmodify_loaderTotalCount');
					countCmp.setValue(countCmp.getValue() + 1);
				}else{
					Ext.ux.Toast.msg(unload.unloadtaskmodify.i18n('foss.unload.unloadtaskaddnew.alertInfo.alertTitle')/*'提示'*/, 
					unload.unloadtaskmodify.i18n('foss.unload.unloadtaskaddnew.alertInfo.loadManAlreadyAddedAlertInfo'), 'error', 2000);
				}
				loadManCmp.focus();
			}else{
				Ext.ux.Toast.msg(unload.unloadtaskmodify.i18n('foss.unload.unloadtaskaddnew.alertInfo.alertTitle')/*'提示'*/, 
				unload.unloadtaskmodify.i18n('foss.unload.unloadtaskaddnew.alertInfo.inputLoadManAlertInfo'), 'error', 2000);
			}
		}
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
});

//定义右下方列表之Model
Ext.define('Foss.unload.unloadtaskmodify.loadManModel', {
	extend : 'Ext.data.Model',
	//定义字段
	fields : [{
		name : 'loaderName',
		type : 'string'
	}, {
		name : 'loaderCode',
		type : 'string'
	}]
});

//定义右下方列表之store
Ext.define('Foss.unload.unloadtaskmodify.loadManStore', {
	extend : 'Ext.data.Store',
	// 绑定一个模型
	model : 'Foss.unload.unloadtaskmodify.loadManModel',
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
});

//定义右下方列表之grid
Ext.define('Foss.unload.unloadtaskmodify.loadManGrid', {
	extend : 'Ext.grid.Panel',
	height : 550,
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.unload.unloadtaskmodify.loadManStore'),
		me.callParent([cfg]);
	},
	dockedItems: [{
    xtype: 'toolbar',
    dock: 'bottom',
    defaults: {
		xtype: 'textfield',
		readOnly:true,
		labelWidth:80
	},
	items: [{
		fieldLabel : unload.unloadtaskmodify.i18n('foss.unload.unloadtaskaddnew.loadManGrid.totalLoadManLabel')/*'卸车人数'*/,
		xtype : 'numberfield',
		readOnly : true,
		value : 0,
		id : 'Foss_unload_unloadtaskmodify_loaderTotalCount'
		}]
  	}],
	columns : [{
		dataIndex : 'loaderName',
		align : 'center',
		flex : 1,
		xtype : 'ellipsiscolumn',
		text : unload.unloadtaskmodify.i18n('foss.unload.unloadtaskaddnew.loadManGrid.loadManNameColumn')/*'卸车员姓名'*/
	}, {
		dataIndex : 'loaderCode',
		align : 'center',
		flex : 1,
		text : unload.unloadtaskmodify.i18n('foss.unload.unloadtaskaddnew.loadManGrid.loadManCodeColumn')/*'卸车员工号'*/
	},{
		xtype : 'actioncolumn',
		width : 60,
		text : unload.unloadtaskmodify.i18n('foss.unload.unloadtaskaddnew.loadManGrid.actionColumn')/*'操作'*/,
		align : 'center',
		items : [ {
			tooltip : unload.unloadtaskmodify.i18n('foss.unload.unloadtaskaddnew.loadManGrid.deleteTipText')/*'删除'*/,
			iconCls : 'deppon_icons_remove',
			handler : function(grid, rowIndex, colIndex) {
				var record = grid.store.getAt(rowIndex);
				grid.store.removeAt(rowIndex);
				var countCmp = Ext.getCmp('Foss_unload_unloadtaskmodify_loaderTotalCount');
				countCmp.setValue(countCmp.getValue() - 1);
				//如果修改前存在该卸车员，则视为删除操作
				if(unload.unloadtaskmodify.oldLoaderMap.get(record.get('loaderCode')) != null){
						unload.unloadtaskmodify.deletedLoaderMap.add(record.get('loaderCode'),record.data);
					}
			}
		}]
	}]
});

//卸车员form
unload.unloadtaskmodify.loadManForm = Ext.create('Foss.unload.unloadtaskmodify.loadManForm');
//卸车员grid
unload.unloadtaskmodify.loadManGrid = Ext.create('Foss.unload.unloadtaskmodify.loadManGrid');

//定义右侧控件Panel
Ext.define('Foss.unload.unloadtaskmodify.loadManPanel',{
	extend : 'Ext.panel.Panel',
	title : unload.unloadtaskmodify.i18n('foss.unload.unloadtaskaddnew.loadManForm.title')/*'卸车员信息'*/,
	layout : 'auto',
	frame : true,
	collapsible : true,
	animCollapse : true,
	items : [
		unload.unloadtaskmodify.loadManForm,
		unload.unloadtaskmodify.loadManGrid
	]
});

/*Maps*/
//存储修改前的单据
unload.unloadtaskmodify.oldBillMap = new Ext.util.HashMap();
//存储修改前的卸车员
unload.unloadtaskmodify.oldLoaderMap = new Ext.util.HashMap();
//存储新增的单据
unload.unloadtaskmodify.addedBillMap = new Ext.util.HashMap();
//存储删除的单据
unload.unloadtaskmodify.deletedBillMap = new Ext.util.HashMap();
//存储新增的卸车员
unload.unloadtaskmodify.addedLoaderMap = new Ext.util.HashMap();
//存储删除的卸车员
unload.unloadtaskmodify.deletedLoaderMap = new Ext.util.HashMap();

Ext.onReady(function() {
	console.log(unload.unloadtaskmodify.superOrgCode);
	console.log(unload.unloadtaskmodify.beTransferCenter);
	Ext.create('Ext.panel.Panel', {
		id : 'T_unload-unloadtaskmodifyindex_content',
		cls : "panelContentNToolbar",
		bodyCls : 'panelContent-body',
		layout : 'column',
		items : [{
			columnWidth : .68,
			items : [Ext.create('Foss.unload.unloadtaskmodify.billInfoPanel')]
		},{
			columnWidth : .32,
			items : [Ext.create('Foss.unload.unloadtaskmodify.loadManPanel')]
		},{
			xtype : 'container',
			columnWidth : 1,
			layout : 'column',
			items : [ {
				columnWidth : .90,
				xtype : 'container',
				html : '&nbsp'
			}, {
				xtype : 'button',
				text : unload.unloadtaskmodify.i18n('foss.unload.unloadtaskmodify.loadManGrid.saveButtonText'),//'保存',
				columnWidth : .1,
				id : 'Foss_unload_unloadtaskmodify_saveButton_ID',
				cls : 'yellow_button',
				handler : function(){
					//车牌号、月台号不能为空
					var form = unload.unloadtaskmodify.billAddForm.getForm();
					var vehicleNo = form.findField('vehicleNo').getValue();
					var platformCode = form.findField('platformCode').getValue();
					var unloadTaskNo = form.findField('unloadTaskNo').getValue();
					if(vehicleNo.trim() == ''){
						Ext.ux.Toast.msg(unload.unloadtaskmodify.i18n('foss.unload.unloadtaskaddnew.alertInfo.alertTitle')/*'提示'*/, 
						unload.unloadtaskmodify.i18n('foss.unload.unloadtaskaddnew.alertInfo.vehicleNoNotAllowBlankAlertInfo'), 'error', 2000);
						return;
					}
					//如果当前部门为外场，则“月台号”必须输入
					if(unload.unloadtaskmodify.beTransferCenter == 'Y'){
						if(Ext.isEmpty(platformCode)){
							Ext.ux.Toast.msg(unload.unloadtaskmodify.i18n('foss.unload.unloadtaskaddnew.alertInfo.alertTitle')/*'提示'*/, 
							unload.unloadtaskmodify.i18n('foss.unload.unloadtaskaddnew.alertInfo.platformNoNotAllowBlankAlertInfo'), 'error', 2000);
							return;
						}
						//根据月台号获取ID
						var platformStore = form.findField('platformCode').store;
						var platformId = platformStore.findRecord('platformCode',platformCode,0,false,true,true).get('id');
					}
					//至少要有一个单据、一个卸车员
					var billStore = unload.unloadtaskmodify.billInfoGrid.getStore();
					var loaderStore = unload.unloadtaskmodify.loadManGrid.store;
					if(billStore.getCount() == 0){
						Ext.ux.Toast.msg(unload.unloadtaskmodify.i18n('foss.unload.unloadtaskaddnew.alertInfo.alertTitle')/*'提示'*/, 
						unload.unloadtaskmodify.i18n('foss.unload.unloadtaskaddnew.alertInfo.atLeastOneBillAlertInfo'), 'error', 2000);
						return;
					}
					if(loaderStore.getCount() == 0){
						Ext.ux.Toast.msg(unload.unloadtaskmodify.i18n('foss.unload.unloadtaskaddnew.alertInfo.alertTitle')/*'提示'*/, 
						unload.unloadtaskmodify.i18n('foss.unload.unloadtaskaddnew.alertInfo.atLeastOneLoadManAlertInfo'), 'error', 2000);
						return;
					}
					
					//获取新增的单据list
					var addedBillList = unload.unloadtaskmodify.addedBillMap.getValues();
					//获取删除的单据list
					var deletedBillList = unload.unloadtaskmodify.deletedBillMap.getValues();
					//获取新增的卸车员list
					var addedLoaderList = unload.unloadtaskmodify.addedLoaderMap.getValues();
					//获取删除的卸车员List
					var deletedLoaderList = unload.unloadtaskmodify.deletedLoaderMap.getValues();
					
					//构造jsondata
					var data = {
						'unloadTaskVo' : {
							'unloadTaskModifyDto' : {
								'platformNo' : platformCode,
								'platformId' : platformId,
								'unloadTaskId' : unload.unloadtaskmodify.unloadTaskId,
								'unloadTaskNo' : unloadTaskNo,
								'addedBillList' : addedBillList,
								'deletedBillList' : deletedBillList,
								'addedLoaderList' : addedLoaderList,
								'deletedLoaderList' : deletedLoaderList
							}
						}
					}
					var myMask = new Ext.LoadMask(Ext.getCmp('T_unload-unloadtaskmodifyindex_content'),{
						msg:"加载中，请稍后..."
							});
					myMask.show();
					//保存数据
					Ext.Ajax.request({
						url : unload.realPath('updateUnloadTask.action'),
						jsonData : data,
						success : function(response){
							Ext.Msg.show({
							     title : unload.unloadtaskmodify.i18n('foss.unload.unloadtaskaddnew.alertInfo.alertTitle')/*'提示'*/,
							     msg : '修改成功！',
							     buttons : Ext.Msg.OK,
							     icon : Ext.Msg.INFO
							});
							Ext.getCmp('Foss_unload_unloadtaskmodify_addButton_ID').setDisabled(true);
							Ext.getCmp('Foss_unload_unloadtaskmodify_addLoaderButton_ID').setDisabled(true);
							Ext.getCmp('Foss_unload_unloadtaskmodify_saveButton_ID').setDisabled(true);
							myMask.hide();
						},
						exception : function(response){
							var result = Ext.decode(response.responseText);
					    	top.Ext.MessageBox.alert(unload.unloadtaskmodify.i18n('foss.unload.unloadtaskaddnew.alertInfo.alertTitle')/*'提示'*/,unload.unloadtaskmodify.i18n('foss.unload.unloadtaskaddnew.alertInfo.saveFailureAlertInfo')/*'操作失败，'*/ + result.message);
					    	myMask.hide();
						}
					});
				}
			} ]
		} ],
		renderTo : 'T_unload-unloadtaskmodifyindex-body'
	});
	/**加载三部分数据，
	 * 1、基本信息：编号、车牌、月台；
	 * 2、单据list，
	 * 3、卸车员list
	 */
	//弹出数据加载，禁止操作
	var myMask = new Ext.LoadMask(Ext.getCmp('T_unload-unloadtaskmodifyindex_content'),{
		msg:"加载中，请稍后..."
			});
	myMask.show();
	Ext.Ajax.request({
		url : unload.realPath('loadUnloadTaskInfo.action'),
		params : {'unloadTaskVo.unloadTaskId' : unload.unloadtaskmodify.unloadTaskId},
		success : function(response){
			var unloadTaskVo = Ext.decode(response.responseText).unloadTaskVo;
			//获取基本信息
			var baseEntity = unloadTaskVo.baseEntity;
			
			/**
			 * 这里把卸车任务编号及卸车类型定义为全局变量，以供以后使用
			 * by 272681
			 * 2015-09-11
			 * */
			//unload.unloadtaskconfirm.unloadTaskNo=baseEntity.unloadTaskNo;
			unload.unloadtaskmodify.unloadTaskNo = baseEntity.unloadTaskNo;
			unload.unloadtaskmodify.unloadType = baseEntity.unloadType;
			
			
			//若卸车任务已被取消或者已完成
			if(baseEntity == null){
				Ext.ux.Toast.msg('提示', '卸车任务不存在或者已经被取消！', 'error', 4000);
				Ext.getCmp('mainAreaPanel').remove('T_unload-unloadtaskmodifyindex',true);
				myMask.hide();
				return;
			}
			if(baseEntity.taskState == 'FINISHED'){
				Ext.ux.Toast.msg('提示', '卸车任务已经结束，无法修改！', 'error', 4000);
				Ext.getCmp('mainAreaPanel').remove('T_unload-unloadtaskmodifyindex',true);
				myMask.hide();
				return;
			}
			//获取两个列表
			var billDetailList = unloadTaskVo.billDetailList;
			//给车牌号赋值
			for(var i=0;i<billDetailList.length;i++){
				if(billDetailList[i].vehicleNo==""||billDetailList[i].vehicleNo==null){
					billDetailList[i].vehicleNo=baseEntity.vehicleNo;
				}
			}
			var loaderDetailList = unloadTaskVo.loaderDetailList;
			var form = unload.unloadtaskmodify.billAddForm.getForm();
			//给车牌号、月台号、卸车任务编号赋值
			form.findField('unloadTaskNo').setValue(baseEntity.unloadTaskNo);
			//月台号
			var platForm =  form.findField('platformCode');
			platForm.setValue(baseEntity.platformNo);	
			platForm.getStore().load({params:{'platformVo.platformEntity.platformCode' : baseEntity.platformNo}});
			//车牌号
			form.findField('vehicleNo').setValue(baseEntity.vehicleNo);
			//单据列表赋值
			unload.unloadtaskmodify.billInfoGrid.store.loadData(billDetailList);
			//卸车员列表赋值
			unload.unloadtaskmodify.loadManGrid.store.loadData(loaderDetailList);
			Ext.getCmp('Foss_unload_unloadtaskmodify_loaderTotalCount').setValue(loaderDetailList.length);
			//将两个列表中的数据置于Map中
			for(var i in unloadTaskVo.billDetailList){
				unload.unloadtaskmodify.oldBillMap.add(billDetailList[i].billNo,billDetailList[i]);
			}
			for(var i in loaderDetailList){
				unload.unloadtaskmodify.oldLoaderMap.add(loaderDetailList[i].loaderCode,loaderDetailList[i]);
			}
			myMask.hide();
		}
	});
});