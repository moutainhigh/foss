function getValue(id) {
	return Ext.getCmp(id).getValue();
};

//封签校验类型store
Ext.define('Foss.Seal.QueryForm.SealCheakType.Store',{
	extend: 'Ext.data.Store',
	fields: [
		{name: 'code',  type: 'string'},
		{name: 'name',  type: 'string'}
	],
	proxy: {
		type: 'memory',
		reader: {
			type: 'json',
			root: 'items'
		}
	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

//封签类型store
Ext.define('Foss.Seal.QueryForm.SealType.Store',{
	extend: 'Ext.data.Store',
	fields: [
		{name: 'code',  type: 'string'},
		{name: 'name',  type: 'string'}
	],
	proxy: {
		type: 'memory',
		reader: {
			type: 'json',
			root: 'items'
		}
	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

//差异store
Ext.define('Foss.Seal.QueryForm.SealState.Store',{
	extend: 'Ext.data.Store',
	fields: [
		{name: 'code',  type: 'string'},
		{name: 'name',  type: 'string'}
	],
	proxy: {
		type: 'memory',
		reader: {
			type: 'json',
			root: 'items'
		}
	},
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

Ext.define('Foss.seal.SealModel',{
	extend:'Ext.data.Model',
	fields: [
		{name: 'id', type: 'string'},
		{name: 'truckTaskId', type: 'truckTaskId'},
		{name: 'sealState' , type: 'string'},
		{name: 'vehicleNo' , type: 'string'},
		{name: 'oaErrorNo' , type: 'string'},
		{name: 'driverName' , type: 'string'},
		{name: 'driverCode' , type: 'string'},
		{name: 'lineName' , type: 'string'},
		{name: 'sealType' , type: 'string'},
		{name: 'status' , type: 'string'},
		{name: 'sealTime' , type: 'string', 
			convert: function(value) {
				if (!Ext.isEmpty(value)) {
					var date = new Date(value);						
					return Ext.Date.format(date,'Y-m-d H:i:s');
				} else {
					return value;
				}
			}
		},
		{name: 'sealdOrgCode' , type: 'string'},
		{name: 'sealdOrgName' , type: 'string'},
		{name: 'createTime' , type: 'string', 
			convert: function(value) {
				if (!Ext.isEmpty(value)) {
					var date = new Date(value);						
					return Ext.Date.format(date,'Y-m-d H:i:s');
				} else {
					return null;
				}
			}},
		{name: 'sealerName' , type: 'string'},
		{name: 'checkTime' , type: 'string', 
			convert: function(value) {
				if (!Ext.isEmpty(value)) {
					var date = new Date(value);						
					return Ext.Date.format(date,'Y-m-d H:i:s');
				} else {
					return null;
				}
			}},
		{name: 'sealCheckType' , type: 'string'},
		{name: 'checkOrgName' , type: 'string'},
		{name: 'checkerUser' , type: 'string'},
		{name: 'isTransientState' , type: 'string'}
	]
});

//SealStore
Ext.define('Foss.seal.SealStore',{
	extend: 'Ext.data.Store',
	model: 'Foss.seal.SealModel',
	pageSize: 10,
	autoLoad: false,
	proxy: {
		type : 'ajax',
		actionMethods:'POST',
		url: load.realPath('queryAllvehicleSealList.action'),
		reader : {
			type : 'json',
			root : 'sealVo.sealList',
			totalProperty : 'totalCount',
			successProperty: 'success'
		},
		listeners:{
			exception : function(dataProxy, response, action, options) {
				var result = Ext.decode(response.responseText);
				Ext.ux.Toast.msg(load.seal.i18n('foss.load.seal.prompt'), result.message);
			}
		}
	},
	listeners: {
		beforeload : function(store, operation, eOpts) {
				var queryParams = load.seal.queryform.getValues();
				Ext.apply(operation, {
					params : {
						'sealVo.sealDto.billNo' : queryParams.billNo,
						'sealVo.sealDto.vehicleNo' : queryParams.vehicleNo,
						'sealVo.sealDto.sealType' : queryParams.sealType,
						'sealVo.sealDto.sealState' : queryParams.sealState,
						'sealVo.sealDto.sealerName' : queryParams.sealerName,
						'sealVo.sealDto.origOrgCode' : queryParams.origOrgCode,
						'sealVo.sealDto.destOrgCode' : queryParams.destOrgCode,
						'sealVo.sealDto.beginDate' :queryParams.beginDate,
						'sealVo.sealDto.endDate' : queryParams.endDate,
						'sealVo.sealDto.sealCheckType' : queryParams.sealCheckType

					}
				});	
		}
	},
	exception : function(response) {
		var json = Ext.decode(response.responseText);
		Ext.ux.Toast.msg(load.seal.i18n('foss.load.seal.prompt'), json.message, 'error');
	}
});

//查询条件
Ext.define('Foss.Seal.QueryForm', {
	extend:'Ext.form.Panel',
	title: load.seal.i18n('foss.load.seal.queryParam'),
	frame: true,
	animCollapse: true,
	defaultType: 'textfield',
	layout:'column',//column
	id: 'Foss_Seal_QueryForm_Id', 
	defaults: {
		margin: '5 5 5 5',
		labelWidth: 60,
		columns:4
	},
	headerPanel: null,
	getHeaderPanel: function(){
		if(this.headerPanel==null){
			this.headerPanel = Ext.create('Foss.Seal.BindOrig.HeaderPanel');
			load.seal.bindSealHeaderPanel = this.headerPanel;
		}
		return this.headerPanel;
	},
	sealPanel: null,
	getSealPanel: function(){
		if(this.sealPanel==null){
			this.sealPanel = Ext.create('Foss.Seal.BindOrig.SealPanel');
			load.seal.bindSealSealPanel = this.sealPanel;
		}
		return this.sealPanel;
	},
	items:[{
		name: 'billNo',
		fieldLabel: load.seal.i18n('foss.load.seal.billNo'),
		columnWidth: .25
	},{
		name: 'vehicleNo',
		xtype : 'commontruckselector',
		fieldLabel: load.seal.i18n('foss.load.seal.vehicleNo'),
		showContent : '{vehicleNo}&nbsp;{isTrailer}&nbsp;{brand}&nbsp;&nbsp;&nbsp;{truckType}',// 显示表格列
		columnWidth: .25
	},{
		xtype: 'combobox',
		name:'sealType',
		fieldLabel: load.seal.i18n('foss.load.seal.sealType'),
		columnWidth:.25,
		displayField: 'name',
		valueField:'code', 
		queryMode:'local',
		triggerAction:'all',
		value:'ALL',
		autoSelect: true,
		editable:false,
		store: Ext.create('Foss.Seal.QueryForm.SealType.Store',{
			data: {
				'items':[
					{'code':'ALL','name':load.seal.i18n('foss.load.seal.ALL')},
					{'code':'BIND','name':load.seal.i18n('foss.load.seal.BIND')},
					{'code':'CHECK','name':load.seal.i18n('foss.load.seal.CHECK')},
					{'code':'UNBIND','name':load.seal.i18n('foss.load.seal.UNBIND')}
				]
			}
		})
	},{
		xtype: 'combobox',
		name:'sealState',
		fieldLabel: load.seal.i18n('foss.load.seal.sealState'),
		columnWidth:.25,
		displayField: 'name',
		valueField:'code', 
		queryMode:'local',
		triggerAction:'all',
		value:'ALL',
		editable:false,
		store: Ext.create('Foss.Seal.QueryForm.SealState.Store',{
			data: {
				'items':[
					{'code':'ALL','name':load.seal.i18n('foss.load.seal.ALL')},
					{'code':'NORMAL','name':load.seal.i18n('foss.load.seal.NORMAL')},
					{'code':'EXCEPTION','name':load.seal.i18n('foss.load.seal.EXCEPTION')},
					{'code':'DAMAGED','name':load.seal.i18n('foss.load.seal.DAMAGED')}
				]
			}
		})
	},{
		name:'sealerName',
		xtype: 'commonemployeeselector',
		fieldLabel: load.seal.i18n('foss.load.seal.sealerName'),
		columnWidth:.25,
		html: '&nbsp;'
	},{
		name:'origOrgCode',
		xtype : 'dynamicorgcombselector',
		type : 'ORG',
		transferCenter:'Y',
		salesDepartment:'Y',
		airDispatch:'Y',
		fieldLabel: load.seal.i18n('foss.load.seal.origOrgCode'),
		columnWidth:.25
	},{
		name:'destOrgCode',
		xtype : 'dynamicorgcombselector',
		type : 'ORG',
		transferCenter:'Y',
		salesDepartment:'Y',
		airDispatch:'Y',
		fieldLabel: load.seal.i18n('foss.load.seal.destOrgCode'),
		columnWidth:.25
	},{
		xtype: 'combobox',
		name:'sealCheckType',
		fieldLabel: load.seal.i18n('foss.load.seal.sealCheakType'),
		columnWidth:.25,
		displayField: 'name',
		valueField:'code', 
		queryMode:'local',
		triggerAction:'all',
		value:'ALL',
		autoSelect: true,
		editable:false,
		store: Ext.create('Foss.Seal.QueryForm.SealCheakType.Store',{
			data: {
				'items':[
					{'code':'ALL','name':'全部'},
					{'code':'FOSS','name':'人工校验'},
					{'code':'BY_HAND','name':'PDA手动校验'},
					{'code':'SCANED','name':'PDA扫描'}
				]
			}
		})
	},{
		xtype: 'rangeDateField',
		fieldLabel: load.seal.i18n('foss.load.seal.queryTime'),
		//类型为datetimefield_date97需要配置fieldId,可以赋予此属性任何唯一标		//识的String值
		fieldId: 'Foss_seal_OperateTime_Id',
		dateType: 'datetimefield_date97',
		//dateType: 'datefield',
		//dateType: 'timefield',
		fromName: 'beginDate',
		dateRange : 30,
		fromValue: Ext.Date.format(new Date(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()
				,'00','00','00') - 1000 * 60 * 60 * 24 * 7), 'Y-m-d H:i:s'),
		toName : 'endTime',
		toValue: Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()
				,'23','59','59'), 'Y-m-d H:i:s'),
		toName: 'endDate',
		allowBlank: false,
		disallowBlank : true,
		columnWidth: .50
	},{
		border : 1,
		xtype : 'container',
		columnWidth : 1,
		defaultType : 'button',
		layout : 'column',
		items:[
				{
					text: load.seal.i18n('foss.load.seal.reset'),
					xtype:"button",
					columnWidth:.10,
					height:30,
					handler:function(){
						this.up('form').getForm().reset();
						//重新初始化交接时间
						this.up('form').getForm().findField('beginDate')
							.setValue(Ext.Date.format(new Date(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()
									,'00','00','00') - 1000 * 60 * 60 * 24 * 7), 'Y-m-d H:i:s'));
						this.up('form').getForm().findField('endDate')
							.setValue(Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate(),'23','59','59'), 'Y-m-d H:i:s'));
					}
				},{
					
					xtype: 'container',
					columnWidth:.80,
					html: '&nbsp;'
					
				},{
					
					text: load.seal.i18n('foss.load.seal.query'),
					disabled: !load.seal.isPermission('load/queryAllvehicleSealListButton'),
					hidden: !load.seal.isPermission('load/queryAllvehicleSealListButton'),
					xtype:"button",
					cls:'yellow_button',
					columnWidth:.10,
					height: 30,
					handler:function(){
						var form = this.up('form').getForm();
						if(!form.isValid()){
							return;
						}
						
						var destOrgCode = this.up('form').form.findField('destOrgCode').getValue();
						var origOrgCode = this.up('form').form.findField('origOrgCode').getValue();
						var currentDeptCode = FossUserContext.getCurrentDept().code;
						
//						if(destOrgCode == null && origOrgCode == null) {
//							//出发部门和到达部门必须输入一个，并且其中一个必须是登录人部门。
//							Ext.ux.Toast.msg(load.seal.i18n('foss.load.seal.prompt'), load.seal.i18n('foss.load.seal.wrong.origDeptDestDeptNotNull'),'error');
//							return;
//						}
						var flag = 0;
						if(destOrgCode != null && destOrgCode != currentDeptCode) {
							flag ++;
						}
						if(origOrgCode != null && origOrgCode != currentDeptCode) {
							flag ++;
						}
						if(flag > 1) {
							//出发部门和到达部门其中一个必须是登录人部门。
							Ext.ux.Toast.msg(load.seal.i18n('foss.load.seal.prompt'), load.seal.i18n('foss.load.seal.wrong.origDeptDestDeptMustBeLoginDept'),'error');
							return;
						}
						load.seal.pagingBar.moveFirst();
					}
					
				}
		       
		       
		       
		       ]
		
		
	}]
//	,
//	listeners : {
//		render : function(panel,text){
//			var cmbOrgCode = panel.getForm().findField('origOrgCode');
//			cmbOrgCode.getStore().load({params:{'commonOrgVo.name' : FossUserContext.getCurrentUserDept().name}});
//			cmbOrgCode.setValue(FossUserContext.getCurrentUserDept().code);
//		}
//	}
});

//表格panel
Ext.define('Foss.Seal.QueryResult', {
	extend:'Ext.grid.Panel',
	bodyCls: 'autoHeight',
	cls: 'autoHeight',
	id: 'Foss.Seal.QueryResult.id',
	stripeRows: true,
	frame: true,
	animCollapse: true,
	autoScroll: true,
	height:500,
	selModel:null,
	store: null,
	//查询结果为空。
	emptyText: load.seal.i18n('foss.load.seal.emptyResult'),
	//定义表格列信息
	columns: [{
		xtype:'actioncolumn',
		//操作
		header: load.seal.i18n('foss.load.seal.operate'),
		width: 75,
		items: [{
			iconCls: 'deppon_icons_showdetail',
			tooltip: load.seal.i18n('foss.load.seal.detail'),
			handler: function(grid, rowIndex, colIndex) {
				var sealState = grid.getStore().getAt(rowIndex).get('sealState'),
				sealType = grid.getStore().getAt(rowIndex).get('sealType');
				if(sealType == 'UNBIND') {
					//未绑定任何记录。
					Ext.ux.Toast.msg(load.seal.i18n('foss.load.seal.prompt'), load.seal.i18n('foss.load.seal.wrong.unbindData'),'error');
					return;
				}
				//如果选择差异类型为破损时, 弹出破损对比结果的窗口
				if(sealState == 'DAMAGED') {
					Ext.create('Ext.window.Window', {
						title: load.seal.i18n('foss.load.seal.damagedSealResult'),
						height:800,
						width:700,
						closable:true,
						closeAction:'hide',
						modal: true,
							items: [
								Ext.create('Foss.Seal.Damaged.SealPanel'),
								Ext.create('Foss.load.Seal.Damaged.BillNoGridPanel')
							]
					}).show();
					//访问sealDamaged.action获取窗口中需要的数据
					Ext.Ajax.request({
						url : load.realPath('sealDamaged.action'),
						params : {'sealVo.id' : grid.getStore().getAt(rowIndex).get('id'),
							'sealVo.sealDto.truckTaskId' : grid.getStore().getAt(rowIndex).get('truckTaskId')},
							success : function(response) {
								var result = Ext.decode(response.responseText),
								billInfos = result.sealVo.billInfos;
								//车牌号
								load.seal.sealOrig.items.items[0].setValue(result.sealVo.seal.vehicleNo);
								//操作人
								load.seal.sealOrig.items.items[1].setValue(result.sealVo.seal.sealerName);
								//操作时间
								if(!Ext.isEmpty(result.sealVo.seal.sealTime)) {
									load.seal.sealOrig.items.items[2].setValue(Ext.Date.format(new Date(result.sealVo.seal.sealTime), 'Y-m-d H:i:s'));
								}
								
								load.seal.SealOrigGrid.store.loadData(result.sealVo.sealOrigDetails)
								load.seal.BillNoGridStore.loadData(billInfos);
							}
						});
					} else {
						//如果类型为差异时, 弹出差异对比结果的窗口
						Ext.create('Ext.window.Window', {
							title: load.seal.i18n('foss.load.seal.sealDetail'),
							height:800,
							width:700,
							closable:true,
							closeAction:'hide',
							modal: true,
							items: [
								Ext.create('Foss.Seal.Compare.SealPanel'),
								Ext.create('Foss.load.Seal.Compare.BillNoGridPanel')
							]
						}).show();
						
						//请求sealCompareResults.action, 该action做两个操作, 
						//1,获取界面上需要的数据. 
						//2,做差异对比
						Ext.Ajax.request({
							url : load.realPath('sealCompareResults.action'),
							params : {'sealVo.id' : grid.getStore().getAt(rowIndex).get('id'),
								'sealVo.sealDto.truckTaskId' : grid.getStore().getAt(rowIndex).get('truckTaskId')},
							success : function(response) {
								var result = Ext.decode(response.responseText),
									billInfos = result.sealVo.billInfos;
								//装车车牌号
								load.seal.sealOrig.items.items[0].setValue(result.sealVo.seal.vehicleNo);
								//装车操作人
								load.seal.sealOrig.items.items[1].setValue(result.sealVo.seal.sealerName);
								//装车操作时间
								if(!Ext.isEmpty(result.sealVo.seal.sealTime)) {
									load.seal.sealOrig.items.items[2].setValue(Ext.Date.format(new Date(result.sealVo.seal.sealTime), 'Y-m-d H:i:s'));
								}
								//卸车车牌号
								load.seal.sealDest.items.items[0].setValue(result.sealVo.seal.vehicleNo);
								//卸车检查人
								load.seal.sealDest.items.items[1].setValue(result.sealVo.seal.checkerUser);
								//卸车时间
								if(!Ext.isEmpty(result.sealVo.seal.checkTime)) {
									load.seal.sealDest.items.items[2].setValue(Ext.Date.format(new Date(result.sealVo.seal.checkTime), 'Y-m-d H:i:s'));
								}
								
								load.seal.SealOrigGrid.store.loadData(result.sealVo.sealOrigDetails)
								load.seal.SealDestGrid.store.loadData(result.sealVo.sealDestDetails)
								
								load.seal.BillNoGridStore.loadData(billInfos);
							}
						});
					}
				}
			},{
				iconCls: 'deppon_icons_edit',
				tooltip: load.seal.i18n('foss.load.seal.modify'),
				handler: function(grid, rowIndex, colIndex) {
					load.seal.editSealHeaderPanel = Ext.create('Foss.Seal.EditBindOrig.HeaderPanel');
					load.seal.editSealSealPanel = Ext.create('Foss.Seal.EditBindOrig.SealPanel');
					var editsealwindow = Ext.create('Ext.window.Window', {
						//修改绑定装车封签信息
						title: load.seal.i18n('foss.load.seal.editBindSealOrig'),
						height:450,
						width:1000,
						closable:true,
						closeAction:'hide',
						modal: true,
						items: [
							load.seal.editSealHeaderPanel,
							load.seal.editSealSealPanel
						]
					});
					load.seal.editsealwindow = editsealwindow;
					//请求action获取界面上需要的数据
					Ext.Ajax.request({
						url : load.realPath('loadOrigSeal.action'),
						params : {'sealVo.id' : grid.getStore().getAt(rowIndex).get('id'),
								'sealVo.sealDto.truckTaskId' : grid.getStore().getAt(rowIndex).get('truckTaskId')},
						success : function(response) {
							var result = Ext.decode(response.responseText),
								billInfos = result.sealVo.billInfos;
							var form = load.seal.editSealHeaderPanel.form;
							form.findField('id').setValue(result.sealVo.seal.id);
							form.findField('vehicleNo').setValue(result.sealVo.seal.vehicleNo);
							if(grid.getStore().getAt(rowIndex).get('driverName') != '') {
								var driverCodeField = form.findField('driverCode');
								var driverName = grid.getStore().getAt(rowIndex).get('driverName'),
									driverCode = grid.getStore().getAt(rowIndex).get('driverCode');
								driverCodeField.getStore().load({params:{'driverVo.driverEntity.empName' : driverName}});
								driverCodeField.setValue(driverCode);
								form.findField('driverName').setValue(driverName);
							}
							form.findField('sealType').setValue('BIND');
							form.findField('sealerName').setValue(result.sealVo.seal.sealerName);
							load.seal.editSealCenterPanel.getSealNumGrid().store.loadData(result.sealVo.sealOrigDetails)
							load.seal.editSealFooterPanel.form.findField('memo').setValue(result.sealVo.seal.sealOrgMemo);
							load.seal.editSealCenterPanel.getBillNoGridPanel().store.loadData(billInfos);
							editsealwindow.show();
						}
					});
					
				}
			},{
				iconCls: 'deppon_icons_delete',
				tooltip: load.seal.i18n('foss.load.seal.delete'),
				handler: function(grid, rowIndex, colIndex) {
					//删除当前封签关系, 是否确认?
					var msg = load.seal.i18n('foss.load.seal.deleteCurrentSeal');
					Ext.Msg.confirm(load.seal.i18n('foss.load.seal.prompt'), msg, function(optional){
						if(optional == 'yes'){
							Ext.Ajax.request({
								url : load.realPath('deleteSeal.action'),
								params : {'sealVo.sealDto.id' : grid.getStore().getAt(rowIndex).get('id')},
								success : function(response) {
									var result = Ext.decode(response.responseText);
									Ext.ux.Toast.msg(load.seal.i18n('foss.load.seal.prompt'), load.seal.i18n('foss.load.seal.deleteSealSuccess'));
									load.seal.pagingBar.moveFirst();
								},
								exception : function(response) {
									var json = Ext.decode(response.responseText);
									Ext.ux.Toast.msg(load.seal.i18n('foss.load.seal.deleteSealFail'), json.message, 'error');
								}
							});
						}
					});
				}
			}],
			renderer:function(value, metadata, record){
				if(record.data.sealType == 'BIND' && (record.data.status != 'ARRIVED' && record.data.status != 'UNLOADED')){
					//如果封签类型为绑定封签, 并且车辆出发状态不为(已到达, 已卸车), 才可以修改,删除
					var currentDeptCode = FossUserContext.getCurrentDept().code;
					var sealdOrgCode = record.data.sealdOrgCode;
					//只有封车部门才能修改自己的封签
					if(currentDeptCode == sealdOrgCode) {
						this.items[1].iconCls = 'deppon_icons_edit';
					} else {
						this.items[1].iconCls = '';
					}
					this.items[2].iconCls = 'deppon_icons_delete';
				} else {
					this.items[1].iconCls = '';
					this.items[2].iconCls = '';
				}
			}
		},{
			header: load.seal.i18n('foss.load.seal.sealState'), 
			dataIndex: 'sealState',
			width: 60,
			renderer : function(value) {
				switch(value) {
					case '0':
						//未检查
						return '';
					case 'NORMAL':
						//正　常
						return load.seal.i18n('foss.load.seal.NORMAL');
					case 'EXCEPTION':
						//有差异
						return load.seal.i18n('foss.load.seal.EXCEPTION');
					case 'DAMAGED':
						//破　损;
						return load.seal.i18n('foss.load.seal.DAMAGED');
					default: return null;
				}
			}
		},{
			//车牌号
			header: load.seal.i18n('foss.load.seal.vehicleNo'), 
			dataIndex: 'vehicleNo',
			width:80
		},{
			width: 120,
			//差错报告编号
			header: load.seal.i18n('foss.load.seal.oaErrorNo'), 
			dataIndex: 'oaErrorNo'
		},{
			width:60,
			//司机
			header: load.seal.i18n('foss.load.seal.driverName'), 
			dataIndex: 'driverName' 
		},{
			width:250,
			//线路
			header: load.seal.i18n('foss.load.seal.lineName'), 
			dataIndex: 'lineName' 
		},{
			header: load.seal.i18n('foss.load.seal.sealType'), 
			dataIndex: 'sealType',
			width:60,
			renderer : function(value){
				switch(value) {
					case 'BIND':
						//已绑定
						return load.seal.i18n('foss.load.seal.BIND');
					case 'CHECK':
						//已校验
						return load.seal.i18n('foss.load.seal.CHECK');
					case 'INVALID':
						//已删除
						return load.seal.i18n('foss.load.seal.INVALID');
					case 'UNBIND':
						//未绑定;
						return load.seal.i18n('foss.load.seal.UNBIND');
					default: return value;
				}
			}
		},{
			//车辆出发状态
			header: load.seal.i18n('foss.load.seal.status'), 
			dataIndex: 'status',
			width:85,
			renderer : function(value) {
				switch(value) {
					case 'UNDEPART':
						//未出发
						return load.seal.i18n('foss.load.seal.UNDEPART');
					case 'ONTHEWAY':
						//在途
						return load.seal.i18n('foss.load.seal.ONTHEWAY');
					case 'HALFWAY_ARRIVE':
						//中途到达
						return load.seal.i18n('foss.load.seal.HALFWAYARRIVE');
					case 'ARRIVED':
						//已到达
						return load.seal.i18n('foss.load.seal.ARRIVED');
					case 'CANCLED':
						//作废
						return load.seal.i18n('foss.load.seal.CANCLED');
					case 'UNLOADED':
						//已卸车
						return load.seal.i18n('foss.load.seal.UNLOADED');
					default: return value;
				}
			}
		},{
			header: load.seal.i18n('foss.load.seal.sealTime'), 
			dataIndex: 'sealTime',
			width:150
		},{
			header: load.seal.i18n('foss.load.seal.sealdOrgName'), 
			dataIndex: 'sealdOrgName',
			width:150
		},{
			header: load.seal.i18n('foss.load.seal.createTime'), 
			dataIndex: 'createTime',
			width:150
		},{
			header: load.seal.i18n('foss.load.seal.sealerName'), 
			dataIndex: 'sealerName',
			width:85
		},{
			header: load.seal.i18n('foss.load.seal.checkTime'), 
			dataIndex: 'checkTime',
			width:150
		},{
			header: load.seal.i18n('foss.load.seal.sealCheakType'), 
			dataIndex: 'sealCheckType',
			width:150,
			renderer:function(value){
				
				if(value==''){
					return '未校验';
				}
				else if(value=="FOSS")
				{
					return "人工校验";
				}else if(value=='BY_HAND'){
					return "PDA手动校验";
				}else{
				    return "PDA扫描";
				}
				
				
			}
		},{
			header: load.seal.i18n('foss.load.seal.checkOrgName'), 
			dataIndex: 'checkOrgName',
			width:150
		},{
			header: load.seal.i18n('foss.load.seal.checkerUser'), 
			dataIndex: 'checkerUser',
			width:85
		},{
			header: '是否暂存', 
			dataIndex: 'isTransientState',
			width:85,
			renderer : function(value) {
				switch(value) {
					case 'Y':
						return '是';
					default: return '否';
				}
			}
		}],
		constructor: function(config){
			var me = this,
			cfg = Ext.apply({}, config);
			me.store = Ext.create('Foss.seal.SealStore');
			me.selModel = Ext.create('Ext.selection.CheckboxModel');
			
			me.bbar = Ext.create('Deppon.StandardPaging',{
				store:me.store,
				plugins: 'pagesizeplugin'
			});
			me.tbar = [{
				xtype : 'button',
				text : load.seal.i18n('foss.load.seal.bindSeal'),
				disabled: !load.seal.isPermission('load/saveSealOrigButton'),
				hidden: !load.seal.isPermission('load/saveSealOrigButton'),
				name : 'bindSeal',
				handler : function(){
					var records = me.getSelectionModel().getSelection();
					if(records.length == 0){
						Ext.ux.Toast.msg(load.seal.i18n('foss.load.seal.prompt'), load.seal.i18n('foss.load.seal.wrong.mustSeletedOne'), 'error');
						return;
					}
					if(records.length > 1){
						Ext.ux.Toast.msg(load.seal.i18n('foss.load.seal.prompt'), load.seal.i18n('foss.load.seal.wrong.onlyBindOne'), 'error');
						return;
					}
					var record = records[0];

					//如果封签为'未绑定', 可以绑定封签
					if(record.get('sealType') == 'BIND'){
						//已绑定封签不能再绑定!
						Ext.ux.Toast.msg(load.seal.i18n('foss.load.seal.prompt'), load.seal.i18n('foss.load.seal.wrong.couldNotRepeatBind'), 'error');
						return;
					}
					//如果车辆状态为中途到达, 并且封签状态为已校验, 此时也可以再绑定封签
//					if(record.get('sealType') == 'CHECK' && record.get('status') != 'HALFWAY_ARRIVE'){
//						//车辆出发状态为中途到达,并且封签已被校验才可以继续绑定
//						Ext.ux.Toast.msg(load.seal.i18n('foss.load.seal.prompt'), load.seal.i18n('foss.load.seal.wrong.statusErrorCouldNotBind'), 'error');
//						return;
//					}
					load.seal.bindSealHeaderPanel = Ext.create('Foss.Seal.BindOrig.HeaderPanel');
					load.seal.bindSealSealPanel = Ext.create('Foss.Seal.BindOrig.SealPanel');
					var bindWindow = Ext.create('Ext.window.Window', {
						//'校验卸车封签信息'
						title: load.seal.i18n('foss.load.seal.bindVehicleSealInfo'),
						height: 450,
						width: 1000,
						closable: true,
						closeAction: 'hide',
						modal: true,
						items: [load.seal.bindSealHeaderPanel, load.seal.bindSealSealPanel]
					});
					load.seal.bindWindow = bindWindow;
					load.seal.bindSealHeaderPanel.form.reset();	//清空上方form
					load.seal.bindSealSealPanel.getCenterPanel().getSealNumGrid().store.removeAll();//清空封签表格
					load.seal.bindSealSealPanel.getCenterPanel().getBillNoGridPanel().store.removeAll();//清空单据表格
					load.seal.bindSealSealPanel.getFooterPanel().form.reset();//清空下方form
					load.seal.bindSealHeaderPanel.form.findField('truckTaskDetailId').setValue(record.get('truckTaskId'));
					load.seal.bindSealHeaderPanel.form.findField('id').setValue(record.get('id'));
					load.seal.bindSealHeaderPanel.form.findField('vehicleNo').setValue(record.get('vehicleNo'));
					if(record.get('driverName') != '') {
						var driverCodeField = load.seal.bindSealHeaderPanel.form.findField('driverCode');
						driverCodeField.setValue(record.get('driverCode'));
						driverCodeField.getStore().load({params:{'driverVo.driverEntity.empName' : record.get('driverName')}});
						load.seal.bindSealHeaderPanel.form.findField('driverName').setValue(record.get('driverName'));
					} else {
						Ext.Ajax.request({
							url: load.realPath('queryDriverInfoByTruckTaskId.action'),
							params : {'sealVo.sealDto.truckTaskId' : record.get('truckTaskId')},
							success: function(response){
								var result = Ext.decode(response.responseText),
									driverName = result.sealVo.sealDto.driverName,
									driverCode = result.sealVo.sealDto.driverCode;
								var driverCodeField = load.seal.bindSealHeaderPanel.form.findField('driverCode');
								driverCodeField.getStore().load({params:{'driverVo.driverEntity.empName' : driverName}});
								driverCodeField.setValue(driverCode);
								load.seal.bindSealHeaderPanel.form.findField('driverName').setValue(driverName);
							},
							exception : function(response) {
								var json = Ext.decode(response.responseText);
								Ext.ux.Toast.msg(load.seal.i18n('foss.load.seal.saveFail'), json.message, 'error');
							}
						});
					}
					Ext.Ajax.request({
						url: load.realPath('queryBillInfoByTruckTaskId.action'),
						params : {'sealVo.sealDto.truckTaskId' : record.get('truckTaskId')},
						success: function(response){
							var result = Ext.decode(response.responseText),
								billInfos = result.sealVo.billInfos;
							load.seal.bindSealSealPanel.getCenterPanel().getBillNoGridPanel().store.loadData(billInfos);
							bindWindow.show();
						},
						exception : function(response) {
							var json = Ext.decode(response.responseText);
							Ext.ux.Toast.msg(load.seal.i18n('foss.load.seal.saveFail'), json.message, 'error');
						}
					});
					
				}
			}, {
				xtype : 'button',
				text : load.seal.i18n('foss.load.seal.checkSeal'),
				disabled: !load.seal.isPermission('load/checkSealButton'),
				hidden: !load.seal.isPermission('load/checkSealButton'),
				name : 'checkSeal',
				handler : function(){
					var records = me.getSelectionModel().getSelection();
					if(records.length == 0){
						Ext.ux.Toast.msg(load.seal.i18n('foss.load.seal.prompt'), load.seal.i18n('foss.load.seal.wrong.mustSeletedOne'), 'error');
						return;
					}
					if(records.length > 1){
						//每次只能校验一辆!
						Ext.ux.Toast.msg(load.seal.i18n('foss.load.seal.prompt'), load.seal.i18n('foss.load.seal.wrong.onlyCheckOne'), 'error');
						return;
					}
					var record = records[0];
					//如果封签为'已绑定', 并且车辆状态为中途到达或者到达. 才可以校验封签
					
					if(record.get('sealType') != 'BIND'){
						//只有已经绑定的才能校验!
						Ext.ux.Toast.msg(load.seal.i18n('foss.load.seal.prompt'), load.seal.i18n('foss.load.seal.wrong.onlyBindedCouldBeCheck'), 'error');
						return;
					}
					
					load.seal.checkSealHeaderPanel = Ext.create('Foss.Seal.Checkout.HeaderPanel');
					load.seal.checkSealSealPanel = Ext.create('Foss.Seal.Checkout.SealPanel');
					var checkoutWindow = Ext.create('Ext.window.Window', {
						//'校验卸车封签信息'
						title: load.seal.i18n('foss.load.seal.checkSealInfo'),
						height: 450,
						width: 1000,
						closable: true,
						closeAction: 'hide',
						modal: true,
						items: [load.seal.checkSealHeaderPanel, load.seal.checkSealSealPanel]
					});
					load.seal.checkoutWindow = checkoutWindow;
					var headerForm = load.seal.checkSealHeaderPanel.form;
					headerForm.reset();	//清空上方form
					load.seal.checkSealCenterPanel.getSealNumGrid().store.removeAll();//清空表格
					load.seal.checkSealFooterPanel.form.reset();	//清空下方form
					load.seal.checkSealHeaderPanel.items.eachKey(function(key, item){
						Ext.getCmp(key).readOnly = false;
					});
					headerForm.findField('sealerName').readOnly = true;
					headerForm.loadRecord(record);
					headerForm.findField('sealType').setValue('CHECK');	//操作类型 校验封签
					headerForm.findField('id').setValue(record.get('id'));
					load.seal.checkSealHeaderPanel.items.eachKey(function(key, item){ 
						Ext.getCmp(key).readOnly = true;
					});
					Ext.Ajax.request({
						url: load.realPath('queryBillInfoByTruckTaskId.action'),
						params : {'sealVo.sealDto.truckTaskId' : record.get('truckTaskId')},
						success: function(response){
							var result = Ext.decode(response.responseText),
								billInfos = result.sealVo.billInfos,
								affirmResult = result.sealVo.affirmResult,
								truckTaskDetail = result.sealVo.truckTaskDetail;
							if(affirmResult ==0) {
								var msg = "本车辆出发部门：" + truckTaskDetail.origOrgName + "，最终到达部门为：" + truckTaskDetail.destOrgName + "，本部门不是最终到达部门，请确认是否要做封签校验";
								//如果当前车辆最终到达部门不是当前部门给出提示
								Ext.Msg.confirm(load.seal.i18n('foss.load.seal.prompt'), msg, function(optional){
									if(optional == 'yes'){
										load.seal.checkSealCenterPanel.getBillNoGridPanel().store.loadData(billInfos);
										checkoutWindow.show();
									}
								});
							} else if(affirmResult ==2){
								var msg = "本车辆出发部门：" + truckTaskDetail.origOrgName + "，最终到达部门为：" + truckTaskDetail.destOrgName + "，本部门不是车辆任务到达部门，不能做封签校验";
								top.Ext.MessageBox.alert(load.seal.i18n('foss.load.seal.prompt')/*'提示'*/,msg);
								return;
								
							}else{
								if(record.get('status') != 'HALFWAY_ARRIVE' && record.get('status') != 'ARRIVED' && record.get('status') != 'UNLOADED'){
									//只有中途到达, 已到达的车辆才能校验!
									//增加已卸车也可以校验2013-04-15
									Ext.ux.Toast.msg(load.seal.i18n('foss.load.seal.prompt'), load.seal.i18n('foss.load.seal.wrong.statusErrorCouldNotCheck'), 'error');
									return;
								}
								load.seal.checkSealCenterPanel.getBillNoGridPanel().store.loadData(billInfos);
								checkoutWindow.show();
							}
						},
						exception : function(response) {
							var json = Ext.decode(response.responseText);
							Ext.ux.Toast.msg(load.seal.i18n('foss.load.seal.wrong.saveFail'), json.message, 'error');
						}
					});
				}
			}];
			load.seal.pagingBar = me.bbar;
			me.callParent([cfg]);
		}
});

Ext.onReady(function() {
	Ext.QuickTips.init();
	var queryform = Ext.create('Foss.Seal.QueryForm');
	load.seal.queryform = queryform;
	var queryResult = Ext.create('Foss.Seal.QueryResult');
	Ext.create('Ext.panel.Panel',{
		id: 'T_load-sealindex_content',
		cls: "panelContentNToolbar",
		bodyCls: 'panelContentNToolbar-body',
		layout: 'auto',
		items: [queryform, queryResult],
		renderTo: 'T_load-sealindex-body'
	});
});