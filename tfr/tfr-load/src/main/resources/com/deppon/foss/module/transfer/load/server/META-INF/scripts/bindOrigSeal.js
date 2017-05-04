/****************2.绑定装车封签信息界面  begin**************/
//操作类型store
Ext.define('Foss.Seal.BindOrig.SealType.Store',{
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

Ext.define('Foss.Seal.BindOrig.HeaderPanel',{
	extend: 'Ext.form.Panel',
	layout:'column',
	frame: false,
	defaultType: 'textfield',
	name: 'bindOrigHeaderPanel',
	defaults: {
		margin: '5 20 5 20'
	},
	items:[{
		name: 'vehicleNo',
		xtype : 'commontruckselector',
		fieldLabel: load.seal.i18n('foss.load.seal.vehicleNo'),
		columnWidth: .25,
		allowBlank: false,
		readOnly: true,
		blankText: load.seal.i18n('foss.load.seal.wrong.vehicleNoCouldNotBeNull'),
		listeners: {
			'select': function(field) {
				var me = this;
				Ext.Ajax.request({
					url: load.realPath('queryDriverInfoByVehicleNo.action'),
					jsonData: {'sealVo':{'sealDto':{'vehicleNo':field.getValue()}}},
					success: function(response){
						var result = Ext.decode(response.responseText),
							relationInfo = result.sealVo.relationInfo;
						var driverName = relationInfo.driverName,
							driverCode = relationInfo.driverCode,
							driverPhone = relationInfo.driverPhone;
						var form = load.seal.bindSealHeaderPanel.form;
						form.findField('driverCode').setValue(driverCode);
						form.findField('driverName').setValue(driverName);
						form.findField('driverPhone').setValue(driverPhone);
					},
					exception : function(response) {
						var json = Ext.decode(response.responseText);
						Ext.ux.Toast.msg(load.seal.i18n('foss.load.seal.prompt'), json.message, 'error');
					}
				});
			}
		}
	},{
		name: 'driverCode',
		xtype : 'commondriverselector',
		fieldLabel: load.seal.i18n('foss.load.seal.driverName'),
		columnWidth:.25,
//		blankText: load.seal.i18n('foss.load.seal.wrong.driverNameCouldNotBeNull'),
		listeners: {
			'select': function(field, records, eOpts) {
				var items = this.up('window').items.items[0].items.items[0].items;
				var record = records[0],
					empName = record.get('empName'),
					empPhone = record.get('empPhone');
				var form = load.seal.bindSealHeaderPanel.form;
				form.findField('driverName').setValue(empName);
				form.findField('driverPhone').setValue(empPhone);
			}
		}
	}, {
		name: 'driverName',
		columnWidth:.0,
		hidden: true
	}, {
		name: 'driverPhone',
		columnWidth:.0,
		hidden: true
	},{
		xtype: 'combobox',
		name:'sealType',
		fieldLabel: load.seal.i18n('foss.load.seal.operateType'),
		columnWidth:.25,
		displayField: 'name',
		editable: false,
		readOnly: true,
		valueField:'code',
		value:'BIND',
		queryMode:'local',
		triggerAction:'all',
		autoSelect: true,
		allowBlank: false,
		blankText: load.seal.i18n('foss.load.seal.wrong.mustSeleteOperateType'),
		store: Ext.create('Foss.Seal.BindOrig.SealType.Store',{
			data: {
				'items':[
					 {'code':'BIND','name':load.seal.i18n('foss.load.seal.vehicleSealBind')}
				]
			}
		})
	},{
		name: 'sealerName',
		fieldLabel: load.seal.i18n('foss.load.seal.sealerName'),
		columnWidth:.25,
		readOnly : true,
		value : FossUserContext.getCurrentUser().employee.empName
	}, {
		name: 'truckTaskDetailId',
		columnWidth:.0,
		hidden: true
	}, {
		name: 'id',
		columnWidth:.0,
		hidden: true
	}]
});

Ext.define('Foss.seal.BindOrig.SealModel',{
	extend:'Ext.data.Model',
	fields: [
		{name: 'id', type: 'int'},
		{name: 'sealNo' , type: 'string'}
	]
});

//Store
Ext.define('Foss.seal.BindOrig.SealStore',{
	extend: 'Ext.data.Store',
	model: 'Foss.seal.BindOrig.SealModel',
	proxy: {
		type: 'memory',
		reader: {
			type: 'json',
			root: 'items'
		}
	}
});

//grid
Ext.define('Foss.Seal.BindOrig.SealGrid', {
	extend:'Ext.grid.Panel',
	height : 250,
	stripeRows: true,
	frame: true,
	animCollapse: true,
	autoScroll: true,
	columns: [{
		header: load.seal.i18n('foss.load.seal.sealNo'), 
		dataIndex: 'sealNo',
		flex : 3,
		field: {
			xtype: 'textfield',
			allowBlank:false,
			listeners: {
				'blur': function(field) {
					var value = field.getValue();
					if(value.length < 7 || value.length > 9) {
						//封签号只能为7到9位的数字
						Ext.ux.Toast.msg(load.seal.i18n('foss.load.seal.prompt'), load.seal.i18n('foss.load.seal.wrong.sealNoMustBeSevenOrEightDigit'));
						return;
					}
					if(isNaN(value)) {
						//封签号只能为7到9位的数字
						Ext.ux.Toast.msg(load.seal.i18n('foss.load.seal.prompt'), load.seal.i18n('foss.load.seal.wrong.sealNoMustBeSevenOrEightDigit'));
						return;
					}
					var sealNumGridStore = load.seal.bindSealSealPanel.getCenterPanel().getSealNumGrid().getStore();
					
					var count = 0;
					for(i = 0; i < sealNumGridStore.data.length; i++) {
						var v = sealNumGridStore.getAt(i).get('sealNo');
						if(v == value) {
							count ++;
						}
					}
					if(count > 1) {
						//不允许录入重复的封签号, 请修改.
						Ext.ux.Toast.msg(load.seal.i18n('foss.load.seal.prompt'), load.seal.i18n('foss.load.seal.wrong.sealNoCouldNotBeRepeat'));
						return;
					}
				}
			}
		}
	},{
	xtype:'actioncolumn',
		header: load.seal.i18n('foss.load.seal.operate'),
		flex : 1,
		items: [{
			iconCls: 'deppon_icons_delete',
			tooltip: load.seal.i18n('foss.load.seal.delete'),
			handler: function(grid, rowIndex, colIndex) {
				grid.getStore().removeAt(rowIndex);
			}
		}]
	}],
	buttons: [{
		text: load.seal.i18n('foss.load.seal.insertRow'),
		handler: function(){
			var grid = this.up('grid'),
				store = grid.getStore();
			
			var count = load.seal.bindSealSealPanel.getCenterPanel().getSealNumGrid().getStore().getCount();
			if(count >= 8) {
				//最多允许录入8个封签，请周知
				Ext.ux.Toast.msg(load.seal.i18n('foss.load.seal.prompt'), load.seal.i18n('foss.load.seal.wrong.onlyInputEeghtSeal'));
				return;
			}
			var nullNewRow = Ext.ModelManager.create({
						'sealNo' : '',
					},'Foss.seal.BindOrig.SealModel'),
				grid = this.up('grid'),
				edit = grid.getEditor();
			edit.cancelEdit();
			store.insert(0, nullNewRow); 
			edit.startEditByPosition({
				row: 0,
				column: 0
			});
		}
	}],
	editor: null,
	getEditor: function(){
		if(this.editor==null){
			this.editor = Ext.create('Ext.grid.plugin.CellEditing', {
				clicksToEdit: 1,
			});
		}
		return this.editor;
	},
	
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.seal.BindOrig.SealStore');
		me.plugins = [
			me.getEditor()
		];
		me.callParent([cfg]);
	}
});

Ext.define('Foss.Seal.BindOrig.CenterPanel', {
	extend:'Ext.container.Container',
	cls: 'autoHeight',
	bodyCls: 'autoHeight',
	layout: 'hbox',
	sealNumGrid: null,
	getSealNumGrid: function(){
		if(this.sealNumGrid==null){
			this.sealNumGrid = Ext.create('Foss.Seal.BindOrig.SealGrid',{
				flex : 1,
				margin: '5 10 5 5',
				title: load.seal.i18n('foss.load.seal.sealNo')
			});
		}
		return this.sealNumGrid;
	},
	billNoGridPanel: null,
	getBillNoGridPanel: function(){
		if(this.billNoGridPanel==null){
			this.billNoGridPanel = Ext.create('Foss.load.Seal.BindOrig.BillNoGridPanel',{
				flex : 1.5,
				margin: '5 5 5 10',
				title: load.seal.i18n('foss.load.seal.billNos')
			});
		}
		return this.billNoGridPanel;
	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.items = [me.getSealNumGrid(),me.getBillNoGridPanel()];
		me.callParent([cfg]);
	}
});

Ext.define('Foss.Seal.BindOrig.FooterPanel',{
	extend: 'Ext.form.Panel',
	layout:'column',
	defaultType: 'textfield',
	items:[{
		name: 'memo',
		labelAlign: 'left',
		fieldLabel: load.seal.i18n('foss.load.seal.sealMemo'),
		columnWidth: 1
	}, {
		xtype: 'button',
		plugins:Ext.create('Deppon.ux.ButtonLimitingPlugin',{
			seconds: 5
		}),
		name: 'save',
		text: load.seal.i18n('foss.load.seal.save'),
		disabled: !load.seal.isPermission('load/saveSealOrigButton'),
		hidden: !load.seal.isPermission('load/saveSealOrigButton'),
		columnWidth:.15,
		html: '&nbsp;',
		handler:function(){
			//点击保存后先要从界面上获取数据, 然后将数据封装
			var centerPanel = load.seal.bindSealSealPanel.getCenterPanel(),
				headerForm = load.seal.bindSealHeaderPanel.getForm(),
				footerForm = load.seal.bindSealSealPanel.getFooterPanel().getForm(),
				sealNewRecords = load.seal.bindSealSealPanel.getCenterPanel().getSealNumGrid().getStore().getNewRecords(),
				array = new Array(),
				submitValue = null,
				seal_header = headerForm.getValues(),
				seal_footer = footerForm.getValues();
			if(!headerForm.isValid()) {
				return;
			}
			if(sealNewRecords.length == 0) {
				Ext.ux.Toast.msg(load.seal.i18n('foss.load.seal.prompt'), load.seal.i18n('foss.load.seal.wrong.sealInfoCouldNotBeNull'));
				return;
			}
			for(var i = 0; i < sealNewRecords.length; i++){
				var record = sealNewRecords[i];
					sealNo = record.get('sealNo');
				if(sealNo.length < 7 || sealNo.length > 9) {
					//封签号只能为7到9位的数字
					Ext.ux.Toast.msg(load.seal.i18n('foss.load.seal.prompt'), load.seal.i18n('foss.load.seal.wrong.sealNoMustBeSevenOrEightDigit'));
					return;
				}
				if(isNaN(sealNo)) {
					//封签号只能为7到9位的数字
					Ext.ux.Toast.msg(load.seal.i18n('foss.load.seal.prompt'), load.seal.i18n('foss.load.seal.wrong.sealNoMustBeSevenOrEightDigit'));
					return;
				}
				array.push(record.data);
			}
			
			//封签是否重复校验
			for(i = 0; i < sealNewRecords.length; i++) {
				var count = 0;
				var value = sealNewRecords[i].get('sealNo');
				for(j = 0; j < sealNewRecords.length; j++) {
					var v = sealNewRecords[j].get('sealNo');
					if(v == value) {
						count ++;
					}
				}
				if(count > 1) {
					//不允许录入重复的封签号, 请修改.
					Ext.ux.Toast.msg(load.seal.i18n('foss.load.seal.prompt'), load.seal.i18n('foss.load.seal.wrong.sealNoCouldNotBeRepeat'));
					return;
				}
			}

			var seals = Ext.merge(seal_header,seal_footer);
			var newEntity = {sealVo:{seal:seals, sealOrigDetails:array}};
			//保存封签操作
			Ext.Ajax.request({
				url: load.realPath('saveSealOrig.action'),
				//params: submitValue,
				jsonData: newEntity,
				success: function(response){
					var json = Ext.decode(response.responseText);
					//封签保存成功!
					Ext.ux.Toast.msg(load.seal.i18n('foss.load.seal.prompt'), load.seal.i18n('foss.load.seal.saveSuccess'));
					var queryForm = Ext.getCmp('Foss_Seal_QueryForm_Id');
					load.seal.bindWindow.hide();
					queryForm.form.findField('vehicleNo').setValue(seals.vehicleNo);
					queryForm.form.findField('sealType').setValue(seals.sealType);
				},
				exception : function(response) {
					var json = Ext.decode(response.responseText);
					Ext.ux.Toast.msg(load.seal.i18n('foss.load.seal.wrong.saveFail'), json.message, 'error');
				}
			});
		}
	}]
});

Ext.define('Foss.Seal.BindOrig.SealPanel',{
	extend: 'Ext.panel.Panel',
	frame: true,
	centerPanel: null,
	getCenterPanel : function(){
		if(this.centerPanel == null) {
			this.centerPanel = Ext.create('Foss.Seal.BindOrig.CenterPanel');
		}
		return this.centerPanel;
	},
	footerPanel: null,
	getFooterPanel : function() {
		if(this.footerPanel == null) {
			this.footerPanel = Ext.create('Foss.Seal.BindOrig.FooterPanel');
		}
		return this.footerPanel;
	},
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.items = [
					me.getCenterPanel(),
					me.getFooterPanel()
					];
		me.callParent([cfg]);
	}
});

Ext.define('Foss.load.seal.BindOrig.BillNoGridModel',{
	extend:'Ext.data.Model',
	fields: [
		{name: 'vehicleNo' , type: 'string'},
		{name: 'lineName' , type: 'string'},
		{name: 'driverName' , type: 'string'},
		{name: 'status' , type: 'string'},
		{name: 'billNo' , type: 'string'},
	]
});

Ext.define('Foss.load.seal.BindOrig.BillNoGridStore',{
	extend: 'Ext.data.Store',
	model: 'Foss.load.seal.BindOrig.BillNoGridModel',
	proxy: {
		type: 'memory',
		reader: {
			type: 'json',
			root: 'items'
		}
	}
});

//表格panel
Ext.define('Foss.load.Seal.BindOrig.BillNoGridPanel', {
	extend:'Ext.grid.Panel',
	height:250,
	stripeRows: true,
	frame: true,
	animCollapse: true,
	autoScroll: true,
	store: null,
	columns: [{
			header: load.seal.i18n('foss.load.seal.vehicleNo'), 
			dataIndex: 'vehicleNo',
			flex : 1
		},{
			header: load.seal.i18n('foss.load.seal.lineName'), 
			dataIndex: 'lineName',
			flex : 1
		},{
			header: load.seal.i18n('foss.load.seal.driverName'), 
			dataIndex: 'driverName',
			flex: 1
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
			header: load.seal.i18n('foss.load.seal.billNo'),
			dataIndex: 'billNo',
			flex : 1
		}],
		constructor: function(config){
			var me = this,
				cfg = Ext.apply({}, config);
			me.store = Ext.create('Foss.load.seal.BindOrig.BillNoGridStore');
			me.callParent([cfg]);
		}
});

/****************2.绑定装车封签信息  end**************/