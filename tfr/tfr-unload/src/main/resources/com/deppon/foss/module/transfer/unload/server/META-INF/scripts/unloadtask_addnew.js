//定义左上方form
Ext.define('Foss.unload.unloadtaskaddnew.billAddForm', {
	extend : 'Ext.form.Panel',
	layout : 'column',
	items : [{
		fieldLabel : unload.unloadtaskaddnew.i18n('foss.unload.unloadtaskaddnew.billInfoForm.vehicleNoLabel'),
		name : 'vehicleNo',//！车牌号
		xtype : 'commontruckselector',
		labelWidth : 115,
		columnWidth : .40,
		margin : '5 10 5 0'
	}, {
		xtype : 'button',
		text : unload.unloadtaskaddnew.i18n('foss.unload.unloadtaskaddnew.billInfoForm.quickAddButtonText'),
		id : 'Foss_unload_unloadtaskaddnew_quickAddButton_ID',
		cls : 'btnAfterTextfield',//！快速添加
		columnWidth : .13,
		handler : function(){
			var form = this.up('form').getForm();
			//获取车牌号控件
			var vehicleNoCmp = form.findField('vehicleNo');
			//获取月台号控件
			var plaCmp = form.findField('platformCode');
			//车牌号不能为空
			if(Ext.isEmpty(vehicleNoCmp.getValue())){
				Ext.ux.Toast.msg(unload.unloadtaskaddnew.i18n('foss.unload.unloadtaskaddnew.alertInfo.alertTitle'),
				unload.unloadtaskaddnew.i18n('foss.unload.unloadtaskaddnew.alertInfo.inputVehicleNoAlertInfo'),
				'error', 2000);
				vehicleNoCmp.reset();
				vehicleNoCmp.focus();
			}else{
				var myMask = new Ext.LoadMask(unload.unloadtaskaddnew.billAddForm, {
						msg : '...'
				});
				myMask.show();
				//根据车牌号获取交接单或者配载单列表
				Ext.Ajax.request({
					url : unload.realPath('queryBillInfoListByVehicleNo.action'),
					params : {'unloadTaskVo.vehicleNo': vehicleNoCmp.getValue()},
					success : function(response){
						var result = Ext.decode(response.responseText);
						//获取月台号
						var platformCode = result.unloadTaskVo.platformCode;
						plaCmp.setValue(platformCode);
						plaCmp.store.loadPage(1);
						//获取单据list
						var billList = result.unloadTaskVo.billList;
						//如果返回的单据数量为0
						if(billList == null || billList.length == 0){
							Ext.ux.Toast.msg(unload.unloadtaskaddnew.i18n('foss.unload.unloadtaskaddnew.alertInfo.alertTitle'), 
							unload.unloadtaskaddnew.i18n('foss.unload.unloadtaskaddnew.alertInfo.vehicleHasNoBillAlertInfo'), 
							'error', 2000);
							vehicleNoCmp.reset();
							vehicleNoCmp.focus();
							myMask.hide();
						}else{
								//单据列表加载数据
								unload.unloadtaskaddnew.billInfoGrid.store.loadData(billList);
								//车牌号不可编辑
								vehicleNoCmp.setReadOnly(true);
								//禁用快速添加按钮
								Ext.getCmp('Foss_unload_unloadtaskaddnew_quickAddButton_ID').setDisabled(true);
								myMask.hide();
							}
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
	}, {
		xtype : 'container',
		html : '&nbsp',
		columnWidth : .12
	}, Ext.create('Foss.commonSelector.PlatformSelector', {
			fieldLabel : unload.unloadtaskaddnew.i18n('foss.unload.unloadtaskaddnew.billInfoForm.platformNoLabel'),
			displayField : 'platformCode',// 显示名称
			valueField : 'platformCode',// 值
			queryParam : 'platformVo.platformEntity.platformCode',// 查询参数
			showContent : '{platformCode}',// 显示表格列
			columnWidth : .35,
			orgCode : unload.unloadtaskaddnew.superOrgCode,
			labelWidth : 85,
			name : 'platformCode',//！月台号
			margin : '5 10 5 10',
			constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				me.store = Ext.create('Foss.commonSelector.PlatformStore');
				me.callParent([cfg]);
			}
		}),{
		fieldLabel : unload.unloadtaskaddnew.i18n('foss.unload.unloadtaskaddnew.billInfoForm.billNoLabel'),
		name : 'billNo',//！交接单/配载单编号
		xtype : 'textfield',
		labelWidth : 115,
		columnWidth : .40,
		margin : '5 10 15 0'
	}, {
		xtype : 'button',
		text : unload.unloadtaskaddnew.i18n('foss.unload.unloadtaskaddnew.billInfoForm.addButtonText'),
		margin : '0 0 10 0',
		id : 'Foss_unload_unloadtaskaddnew_addButton_ID',
		cls : 'btnAfterTextfield',//！添加
		columnWidth : .13,
		handler : function(){
			var form = this.up('form').getForm();
			//获取车牌号控件
			var vehicleNoCmp = form.findField('vehicleNo');
			//获取月台号控件
			var plaCmp = form.findField('platformCode');
			//获取是否商务专递复选框272681
			var isAir = form.findField('isAir');
			//获取单据编号控件
			var billNoCmp = form.findField('billNo');
			if(Ext.isEmpty(billNoCmp.getValue())){
				Ext.ux.Toast.msg(unload.unloadtaskaddnew.i18n('foss.unload.unloadtaskaddnew.alertInfo.alertTitle'), 
				unload.unloadtaskaddnew.i18n('foss.unload.unloadtaskaddnew.alertInfo.inputBillNoAlertInfo'), 
				'error', 2000);
				billNoCmp.reset();
				billNoCmp.focus();
			}else{
				var store = unload.unloadtaskaddnew.billInfoGrid.store;
				if(store.findRecord('billNo',billNoCmp.getValue(),0,false,true,true) != null){
					Ext.ux.Toast.msg(unload.unloadtaskaddnew.i18n('foss.unload.unloadtaskaddnew.alertInfo.alertTitle'), 
					unload.unloadtaskaddnew.i18n('foss.unload.unloadtaskaddnew.alertInfo.billAlreadyAddedAlertInfo'), 
					'error', 2000);
					billNoCmp.focus();
					return;
				}
				var myMask = new Ext.LoadMask(unload.unloadtaskaddnew.billAddForm, {
						msg : '...'
				});
				myMask.show();
				//获取单据信息
				Ext.Ajax.request({
					url : unload.realPath('queryBillInfoByBillNo.action'),
					params : {
						      'unloadTaskVo.billNo': billNoCmp.getValue(),
						      'unloadTaskVo.isAir': isAir.getValue()
						      },
					success : function(response){
						var result = Ext.decode(response.responseText);
						//获取月台号
						var platformCode = result.unloadTaskVo.platformCode;
						plaCmp.setValue(platformCode);
						plaCmp.store.loadPage(1);
						//获取单据信息
						var billInfo = result.unloadTaskVo.billInfo;
						if(billInfo == null){
							Ext.ux.Toast.msg(unload.unloadtaskaddnew.i18n('foss.unload.unloadtaskaddnew.alertInfo.alertTitle'), 
							unload.unloadtaskaddnew.i18n('foss.unload.unloadtaskaddnew.alertInfo.billNotFoundAlertInfo'), 
							'error', 2500);
							billNoCmp.focus();
							myMask.hide();
							return;
						}
						
						var checkIsExpress=function(billNo){
							if(billNo==null||billNo==""){
								return false;
							}
							billNo=billNo.toUpperCase();
							if(((billNo.indexOf('L')!=-1
									&&!isNaN(billNo.replace('L','')))
											||(billNo.indexOf('L')!=-1&&billNo.indexOf('P')!=-1&&!isNaN(billNo.replace('L','').replace('P',''))))
									||((billNo.indexOf('S')!=-1
									&&!isNaN(billNo.replace('S','')))
											||(billNo.indexOf('S')!=-1&&billNo.indexOf('P')!=-1&&!isNaN(billNo.replace('S','').replace('P','')))) 
								    ||((billNo.indexOf('T')!=-1
									&&!isNaN(billNo.replace('T','')))
											||(billNo.indexOf('T')!=-1&&billNo.indexOf('P')!=-1&&!isNaN(billNo.replace('T','').replace('P',''))))){
								return true;
							}
							return false;
						}
						
						//不允许插入集配交接单
						if(billInfo.billType == 'HANDOVER'
							&& billInfo.handOverType == 'LONG_DISTANCE_HANDOVER'
								&&!checkIsExpress(billInfo.billNo)){
							Ext.ux.Toast.msg(unload.unloadtaskaddnew.i18n('foss.unload.unloadtaskaddnew.alertInfo.alertTitle'), 
							unload.unloadtaskaddnew.i18n('foss.unload.unloadtaskaddnew.alertInfo.canNotAddLongDisHandOverBillAlertInfo'), 
							'error', 2500);
							billNoCmp.focus();
							myMask.hide();
							return;
						}
						
						
						//如果列表内不为空，则取出一条，对比单据类型、车牌号
						if(store.getCount() != 0){
							var record = store.getAt(0);
							//如果单据类型不一致
							if(record.get('billType') != billInfo.billType){
								Ext.ux.Toast.msg(unload.unloadtaskaddnew.i18n('foss.unload.unloadtaskaddnew.alertInfo.alertTitle'), 
								unload.unloadtaskaddnew.i18n('foss.unload.unloadtaskaddnew.alertInfo.billTypeNotTheSameAlertInfo'), 
								'error', 2500);
								billNoCmp.focus();
								myMask.hide();
								return;
							}
							if(record.get('vehicleNo') != billInfo.vehicleNo){
								Ext.ux.Toast.msg(unload.unloadtaskaddnew.i18n('foss.unload.unloadtaskaddnew.alertInfo.alertTitle'), 
								unload.unloadtaskaddnew.i18n('foss.unload.unloadtaskaddnew.alertInfo.vehicleNoNotTheSameAlertInfo'), 
								'error', 2500);
								billNoCmp.focus();
								myMask.hide();
								return;
							}
							// 2015/9/11  272681  如果商务专递单据的路线不一致，对比路线
							if(record.get('orgCode') != billInfo.orgCode || record.get('expressArriveCode') != billInfo.expressArriveCode){
								Ext.ux.Toast.msg(unload.unloadtaskaddnew.i18n('foss.unload.unloadtaskaddnew.alertInfo.alertTitle'), 
								unload.unloadtaskaddnew.i18n('foss.unload.unloadtaskaddnew.alertInfo.billLineNotTheSameAlertInfo'), 
								'error', 2500);
								billNoCmp.focus();
								myMask.hide();
								return;
							}
							
						}
						//create一条model
						var billRecord = Ext.ModelManager.create(billInfo, 'Foss.unload.unloadtaskaddnew.billInfoModel');
						//填充车牌号
						vehicleNoCmp.setValue(billInfo.vehicleNo);
						//插入列表
						store.insert(store.getCount(),billRecord);
						//回到单据编号
						billNoCmp.focus();
						billNoCmp.reset();
						//车牌号不可编辑
						vehicleNoCmp.setReadOnly(true);
						//禁用快速添加按钮
						Ext.getCmp('Foss_unload_unloadtaskaddnew_quickAddButton_ID').setDisabled(true);
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
	},{
		xtype : 'container',
		html : '&nbsp',
		columnWidth : .15
	},{
		xtype : 'checkbox',
		boxLabel : '是否商务专递',
		name: 'isAir',
		margin: '5 0 0 0',
		columnWidth : .32,
		handler: function() {
			var form = this.up('form').getForm();
			var values = form.getValues();
			if (!values.isAir) {
				form.findField('vehicleNo').setDisabled(false);	
				Ext.getCmp('Foss_unload_unloadtaskaddnew_quickAddButton_ID').setDisabled(false);
			} else {
				form.findField('vehicleNo').setDisabled(true);
				Ext.getCmp('Foss_unload_unloadtaskaddnew_quickAddButton_ID').setDisabled(true);
			}
		}

			
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
});

//定义左下方列表之Model
Ext.define('Foss.unload.unloadtaskaddnew.billInfoModel', {
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
	},{
		name : 'orgCode',
		type : 'string'
	},{
		name : 'expressArriveCode',
		type : 'string'
	}]
});

//定义左下方列表之store
Ext.define('Foss.unload.unloadtaskaddnew.billInfoStore', {
	extend : 'Ext.data.Store',
	// 绑定一个模型
	model : 'Foss.unload.unloadtaskaddnew.billInfoModel',
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
});

//定义左下方列表之grid
Ext.define('Foss.unload.unloadtaskaddnew.billInfoGrid', {
	extend : 'Ext.grid.Panel',
	height : 550,
	enableColumnHide : false,//配置该属性可取消grid自定义显示列功能
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.unload.unloadtaskaddnew.billInfoStore'),
		me.callParent([cfg]);
	},
	columns : [{
		dataIndex : 'vehicleNo',
		align : 'center',
		flex : 1,
		xtype : 'ellipsiscolumn',
		text : unload.unloadtaskaddnew.i18n('foss.unload.unloadtaskaddnew.billInfoGrid.vehicleNoColumn')
	}, {
		dataIndex : 'billNo',
		align : 'center',
		flex : 1,
		text : unload.unloadtaskaddnew.i18n('foss.unload.unloadtaskaddnew.billInfoGrid.billNoColumn')
	}, {
		dataIndex : 'volume',
		align : 'center',
		flex : 1,
		text : unload.unloadtaskaddnew.i18n('foss.unload.unloadtaskaddnew.billInfoGrid.volumeColumn')
	}, {
		dataIndex : 'weight',
		align : 'center',
		flex : 1,
		text : unload.unloadtaskaddnew.i18n('foss.unload.unloadtaskaddnew.billInfoGrid.weightColumn')
	}, {
		dataIndex : 'waybillTotal',
		align : 'center',
		flex : 1,
		text : unload.unloadtaskaddnew.i18n('foss.unload.unloadtaskaddnew.billInfoGrid.totalWaybillColumn')
	}, {
		dataIndex : 'pieces',
		align : 'center',
		flex : 1,
		text : unload.unloadtaskaddnew.i18n('foss.unload.unloadtaskaddnew.billInfoGrid.piecesColumn')
	},{
		xtype : 'actioncolumn',
		width : 60,
		text : unload.unloadtaskaddnew.i18n('foss.unload.unloadtaskaddnew.billInfoGrid.actionColumn'),
		align : 'center',
		items : [ {
			tooltip : unload.unloadtaskaddnew.i18n('foss.unload.unloadtaskaddnew.billInfoGrid.deleteTipText'),
			iconCls : 'deppon_icons_remove',
			handler : function(grid, rowIndex, colIndex) {
				//如果将全部单据删除，则恢复车牌号为可输入，启用快速添加按钮
				if(grid.store.getCount() == 1){
					var vehicleNoCmp = unload.unloadtaskaddnew.billAddForm.getForm().findField('vehicleNo');
					vehicleNoCmp.reset();
					vehicleNoCmp.setReadOnly(false);
					Ext.getCmp('Foss_unload_unloadtaskaddnew_quickAddButton_ID').setDisabled(false);
				}
				grid.store.removeAt(rowIndex);
			}
		}]
	}]
});

//左上方form
unload.unloadtaskaddnew.billAddForm = Ext.create('Foss.unload.unloadtaskaddnew.billAddForm');
//左下方grid
unload.unloadtaskaddnew.billInfoGrid = Ext.create('Foss.unload.unloadtaskaddnew.billInfoGrid');

//定义左侧控件Panel
Ext.define('Foss.unload.unloadtaskaddnew.billInfoPanel',{
	extend : 'Ext.panel.Panel',
	title : unload.unloadtaskaddnew.i18n('foss.unload.unloadtaskaddnew.billInfoForm.title'),
	layout : 'auto',
	frame : true,
	collapsible : true,
	animCollapse : true,
	items : [
		unload.unloadtaskaddnew.billAddForm,
		unload.unloadtaskaddnew.billInfoGrid
	]
});

//定义右上方form
Ext.define('Foss.unload.unloadtaskaddnew.loadManForm', {
	extend : 'Ext.form.Panel',
	layout : 'column',
	items : [{
		xtype : 'container',
		margin : '20 0 0 0',
		html : unload.unloadtaskaddnew.i18n('foss.unload.unloadtaskaddnew.loadManForm.loadManLabel'),
		columnWidth : 1
	},{
		name : 'loaderCode',//！卸车员
		fieldLabel : '',
		xtype : 'commonemployeeselector',
		parentOrgCode : unload.unloadtaskaddnew.superOrgCode,
		displayField : 'empName',// 显示名称
		valueField : 'empCode',// 值
		columnWidth : .45,
		margin : '5 10 15 0'
	},{
		xtype : 'button',
		text : unload.unloadtaskaddnew.i18n('foss.unload.unloadtaskaddnew.loadManForm.addLoadManButtonText'),
		margin : '0 0 10 0',
		columnWidth : .30,
		id : 'Foss_unload_unloadtaskaddnew_addLoaderButton_ID',
		cls : 'btnAfterTextfield',//！添加卸车员
		handler : function(){
			var loadManCmp = this.up('form').getForm().findField('loaderCode');
			if(!Ext.isEmpty(loadManCmp.getValue())){
				var loaderCode = loadManCmp.getValue();
				var store = unload.unloadtaskaddnew.loadManGrid.store;
				if(store.findRecord('loaderCode',loaderCode,0,false,true,true) == null){
					var loadMan = {
						'loaderCode' : loaderCode,
						'loaderName' : loadManCmp.store.findRecord('empCode',loaderCode,0,false,true,true).get('empName')
					}
					var loadManRecord = Ext.ModelManager.create(loadMan,'Foss.unload.unloadtaskaddnew.loadManModel');
					//插入下方表格
					store.insert(store.getCount(),loadManRecord);
					//卸车员重新获取焦点，方便反复添加
					loadManCmp.reset();
					var countCmp = Ext.getCmp('Foss_unload_unloadtaskaddnew_loaderTotalCount');
					countCmp.setValue(countCmp.getValue() + 1);
				}else{
					Ext.ux.Toast.msg(unload.unloadtaskaddnew.i18n('foss.unload.unloadtaskaddnew.alertInfo.alertTitle'), 
					unload.unloadtaskaddnew.i18n('foss.unload.unloadtaskaddnew.alertInfo.loadManAlreadyAddedAlertInfo'), 
					'error', 2000);
				}
				loadManCmp.focus();
			}else{
				Ext.ux.Toast.msg(unload.unloadtaskaddnew.i18n('foss.unload.unloadtaskaddnew.alertInfo.alertTitle'), 
				unload.unloadtaskaddnew.i18n('foss.unload.unloadtaskaddnew.alertInfo.inputLoadManAlertInfo'), 
				'error', 2000);
			}
		}
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
});

//定义右下方列表之Model
Ext.define('Foss.unload.unloadtaskaddnew.loadManModel', {
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
Ext.define('Foss.unload.unloadtaskaddnew.loadManStore', {
	extend : 'Ext.data.Store',
	// 绑定一个模型
	model : 'Foss.unload.unloadtaskaddnew.loadManModel',
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
});

//定义右下方列表之grid
Ext.define('Foss.unload.unloadtaskaddnew.loadManGrid', {
	extend : 'Ext.grid.Panel',
	height : 550,
	enableColumnHide : false,//配置该属性可取消grid自定义显示列功能
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.unload.unloadtaskaddnew.loadManStore'),
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
			fieldLabel : unload.unloadtaskaddnew.i18n('foss.unload.unloadtaskaddnew.loadManGrid.totalLoadManLabel'),
			xtype : 'numberfield',
			readOnly : true,
			value : 0,
			id : 'Foss_unload_unloadtaskaddnew_loaderTotalCount'
		}]
  	}],
	columns : [{
		dataIndex : 'loaderName',
		align : 'center',
		flex : 1,
		xtype : 'ellipsiscolumn',
		text : unload.unloadtaskaddnew.i18n('foss.unload.unloadtaskaddnew.loadManGrid.loadManNameColumn')
	}, {
		dataIndex : 'loaderCode',
		align : 'center',
		flex : 1,
		text : unload.unloadtaskaddnew.i18n('foss.unload.unloadtaskaddnew.loadManGrid.loadManCodeColumn')
	},{
		xtype : 'actioncolumn',
		width : 60,
		text : unload.unloadtaskaddnew.i18n('foss.unload.unloadtaskaddnew.loadManGrid.actionColumn'),
		align : 'center',
		items : [ {
			tooltip : unload.unloadtaskaddnew.i18n('foss.unload.unloadtaskaddnew.loadManGrid.deleteTipText'),
			iconCls : 'deppon_icons_remove',
			handler : function(grid, rowIndex, colIndex) {
				grid.store.removeAt(rowIndex);
				var countCmp = Ext.getCmp('Foss_unload_unloadtaskaddnew_loaderTotalCount');
				countCmp.setValue(countCmp.getValue() - 1);
			}
		}]
	}]
});

//卸车员form
unload.unloadtaskaddnew.loadManForm = Ext.create('Foss.unload.unloadtaskaddnew.loadManForm');
//卸车员grid
unload.unloadtaskaddnew.loadManGrid = Ext.create('Foss.unload.unloadtaskaddnew.loadManGrid');

//定义右侧控件Panel
Ext.define('Foss.unload.unloadtaskaddnew.loadManPanel',{
	extend : 'Ext.panel.Panel',
	title : unload.unloadtaskaddnew.i18n('foss.unload.unloadtaskaddnew.loadManForm.title'),
	layout : 'auto',
	frame : true,
	collapsible : true,
	animCollapse : true,
	items : [
		unload.unloadtaskaddnew.loadManForm,
		unload.unloadtaskaddnew.loadManGrid
	]
});

Ext.onReady(function() {
	console.log(unload.unloadtaskaddnew.superOrgCode);
	console.log(unload.unloadtaskaddnew.beTransferCenter);
	Ext.create('Ext.panel.Panel', {
		id : 'T_unload-unloadtaskaddnewindex_content',
		cls : "panelContentNToolbar",
		bodyCls : 'panelContent-body',
		layout : 'column',
		items : [{
			columnWidth : .68,
			items : [Ext.create('Foss.unload.unloadtaskaddnew.billInfoPanel')]
		},{
			columnWidth : .32,
			items : [Ext.create('Foss.unload.unloadtaskaddnew.loadManPanel')]
		},{
			xtype : 'container',
			columnWidth : 1,
			layout : 'column',
			items : [ {
				columnWidth : .90,
				xtype : 'container',
				html : '&nbsp'
			}, {
				columnWidth : .10,
				xtype : 'button',
				cls : 'yellow_button',//！生成卸车任务
				name : 'saveButton',
				id : 'Foss_unload_unloadtaskaddnew_saveButton_ID',
				text : unload.unloadtaskaddnew.i18n('foss.unload.unloadtaskaddnew.loadManGrid.createUnloadTask'),
				handler : function() {
					//车牌号、月台号不能为空
					var form = unload.unloadtaskaddnew.billAddForm.getForm();
					var vehicleNo = form.findField('vehicleNo').getValue();
					var platformCode = form.findField('platformCode').getValue();
					if(Ext.isEmpty(vehicleNo)){
						Ext.ux.Toast.msg(unload.unloadtaskaddnew.i18n('foss.unload.unloadtaskaddnew.alertInfo.alertTitle'), 
						unload.unloadtaskaddnew.i18n('foss.unload.unloadtaskaddnew.alertInfo.vehicleNoNotAllowBlankAlertInfo'), 
						'error', 2000);
						return;
					}
					//如果当前部门为外场，则“月台号”必须输入
					var platformId = null;
					if(unload.unloadtaskaddnew.beTransferCenter == 'Y'){
						if(Ext.isEmpty(platformCode)){
									Ext.ux.Toast.msg(unload.unloadtaskaddnew.i18n('foss.unload.unloadtaskaddnew.alertInfo.alertTitle'), 
									unload.unloadtaskaddnew.i18n('foss.unload.unloadtaskaddnew.alertInfo.platformNoNotAllowBlankAlertInfo'),
									'error', 2000);
									return;
						}else{
							//根据月台号获取ID
							var platformStore = form.findField('platformCode').store;
							platformId = platformStore.findRecord('platformCode',platformCode,0,false,true,true).get('id');
						}
					}
					//至少要有一个单据、一个卸车员
					var billStore = unload.unloadtaskaddnew.billInfoGrid.getStore();
					var loaderStore = unload.unloadtaskaddnew.loadManGrid.store;
					if(billStore.getCount() == 0){
						Ext.ux.Toast.msg(unload.unloadtaskaddnew.i18n('foss.unload.unloadtaskaddnew.alertInfo.alertTitle'), 
						unload.unloadtaskaddnew.i18n('foss.unload.unloadtaskaddnew.alertInfo.atLeastOneBillAlertInfo'), 
						'error', 2000);
						return;
					}
					if(loaderStore.getCount() == 0){
						Ext.ux.Toast.msg(unload.unloadtaskaddnew.i18n('foss.unload.unloadtaskaddnew.alertInfo.alertTitle'), 
						unload.unloadtaskaddnew.i18n('foss.unload.unloadtaskaddnew.alertInfo.atLeastOneLoadManAlertInfo'), 
						'error', 2000);
						return;
					}
					//构造jsondata
					//单据list
					var billList = new Array();
					billStore.each(function(record){
						billList.push(record.data);
					});
					var loaderList = new Array();
					//卸车员list
					loaderStore.each(function(record){
						loaderList.push(record.data);
					});
					var data = {
						'unloadTaskVo' : {
							'addnewDto' : {
								'vehicleNo' : vehicleNo,
								'platformCode' : platformCode,
								'platformId' : platformId,
								'billList' : billList,
								'loaderList' : loaderList
							}
						}
					}
					var myMask = new Ext.LoadMask(Ext.getCmp('T_unload-unloadtaskaddnewindex_content'), {
						msg : unload.unloadtaskaddnew.i18n('foss.unload.unloadtaskaddnew.alertInfo.dataCommittingAlertInfo')
					});
					myMask.show();
					//保存数据
					Ext.Ajax.request({
						url : unload.realPath('addUnloadTask.action'),
						jsonData : data,
						success : function(response){
							var result = Ext.decode(response.responseText);
							//获取生成的任务编号
							var unloadTaskNo = result.unloadTaskVo.unloadTaskNo;
							Ext.Msg.show({
							     title : unload.unloadtaskaddnew.i18n('foss.unload.unloadtaskaddnew.alertInfo.alertTitle'),
							     msg : unload.unloadtaskaddnew.i18n('foss.unload.unloadtaskaddnew.alertInfo.saveSuccessAlertInfo') +unloadTaskNo,
							     buttons : Ext.Msg.OK,
							     icon : Ext.Msg.INFO
								});
								//四按钮不可用
								Ext.getCmp('Foss_unload_unloadtaskaddnew_saveButton_ID').setVisible(false);
								Ext.getCmp('Foss_unload_unloadtaskaddnew_quickAddButton_ID').setDisabled(true);
								Ext.getCmp('Foss_unload_unloadtaskaddnew_addButton_ID').setDisabled(true);
								Ext.getCmp('Foss_unload_unloadtaskaddnew_addLoaderButton_ID').setDisabled(true);
								//各控件不可输入
								unload.unloadtaskaddnew.loadManForm.getForm().findField('loaderCode').setDisabled(true);
								var addBillForm = unload.unloadtaskaddnew.billAddForm.getForm();
								addBillForm.findField('platformCode').setDisabled(true);
								addBillForm.findField('vehicleNo').setDisabled(true);
								addBillForm.findField('billNo').setDisabled(true);
								//隐藏两个表格的操作列
								var loadManGrid = unload.unloadtaskaddnew.loadManGrid;
								loadManGrid.columns[loadManGrid.columns.length - 1].setVisible(false);
								var billGrid = unload.unloadtaskaddnew.billInfoGrid;
								billGrid.columns[billGrid.columns.length - 1].setVisible(false);
								myMask.hide();
							},
							exception : function(response){
								var result = Ext.decode(response.responseText);
						    	top.Ext.MessageBox.alert(unload.unloadtaskaddnew.i18n('foss.unload.unloadtaskaddnew.alertInfo.alertTitle'),
						    	unload.unloadtaskaddnew.i18n('foss.unload.unloadtaskaddnew.alertInfo.saveFailureAlertInfo') + result.message);
						    	myMask.hide();
							}
						});
				}
			} ]
		} ],
		renderTo : 'T_unload-unloadtaskaddnewindex-body'
	});
});