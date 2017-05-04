/****************3.校验卸车封签信息界面  begin**************/
//操作类型store
Ext.define('Foss.Seal.Checkout.SealType.Store',{
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

Ext.define('Foss.Seal.Checkout.HeaderPanel',{
	extend: 'Ext.form.Panel',
	layout:'column',
	frame: false,
	defaultType: 'textfield',
	defaults: {
		margin: '5 20 5 20'
	},
	items:[{
		name: 'vehicleNo',
		fieldLabel: load.seal.i18n('foss.load.seal.vehicleNo'),
		columnWidth: .25,
		allowBlank: false,
		blankText: load.seal.i18n('foss.load.seal.wrong.vehicleNoCouldNotBeNull')
	},{
		name: 'driverName',
		xtype : 'commondriverselector',
		fieldLabel: load.seal.i18n('foss.load.seal.driverName'),
		columnWidth:.25,
		blankText: load.seal.i18n('foss.load.seal.wrong.driverNameCouldNotBeNull')
	},{
		xtype: 'combobox',
		name:'sealType',
		fieldLabel: load.seal.i18n('foss.load.seal.operateType'),
		columnWidth:.25,
		displayField: 'name',
		editable: false,
		readOnly: true,
		valueField:'code', 
		queryMode:'local',
		triggerAction:'all',
		allowBlank: false,
		blankText: load.seal.i18n('foss.load.seal.wrong.mustSeleteOperateType'),
		value:'CHECK',
		store: Ext.create('Foss.Seal.Checkout.SealType.Store',{
			data: [
				 {'code':'CHECK','name':load.seal.i18n('foss.load.seal.vehicleSealCheck')}
			]
		})
	},{
		name: 'sealerName',
		fieldLabel: load.seal.i18n('foss.load.seal.checkerUser'),
		columnWidth:.25,
		readOnly : true,
		value : FossUserContext.getCurrentUser().employee.empName
	},{
		name: 'sealState',
		xtype: 'hidden'
	}, {
		name: 'id',//封签ID
		xtype: 'hidden'
	}]
});

Ext.define('Foss.seal.Checkout.SealModel',{
	extend:'Ext.data.Model',
	fields: [
		{name: 'id', type: 'int'},
		{name: 'sealType', type: 'string'},
		{name: 'sealNo' , type: 'string'}
	]
});

//Store
Ext.define('Foss.seal.Checkout.SealStore',{
	extend: 'Ext.data.Store',
	model: 'Foss.seal.Checkout.SealModel',
	proxy: {
		type: 'memory',
		reader: {
			type: 'json',
			root: 'items'
		}
	}
});

//grid
Ext.define('Foss.Seal.Checkout.SealGrid', {
	extend:'Ext.grid.Panel',
	height : 250,
	autoScroll : true,
	columnLines: true,
	sealType : null,
	columns: [{
		header: load.seal.i18n('foss.load.seal.sealNo'), 
		dataIndex: 'sealNo',
		flex : 3,
		field: {
			xtype: 'textfield',
			allowBlank:false,
			listeners: {
				'blur': function(field) {
					value = field.getValue();
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
					
					var sealNewRecords = load.seal.checkSealCenterPanel.getSealNumGrid().getStore().getNewRecords();
					
					var count = 0;
					for(i = 0; i < sealNewRecords.length; i++) {
						var v = sealNewRecords[i].get('sealNo');
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
				store = grid.getStore(),
				sealType = grid.sealType;
			var count = load.seal.checkSealCenterPanel.getSealNumGrid().getStore().getCount();
			if(count >= 8) {
				//最多允许录入8个封签，请周知
				Ext.ux.Toast.msg(load.seal.i18n('foss.load.seal.prompt'), load.seal.i18n('foss.load.seal.wrong.onlyInputEeghtSeal'));
				return;
			}
			var nullNewRow = Ext.ModelManager.create({
						'sealNo' : ''
					},'Foss.seal.Checkout.SealModel'),
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
				clicksToEdit: 1
			})
		}
		return this.editor;
	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.seal.Checkout.SealStore');
		me.plugins = [
			me.getEditor()
		];
		me.callParent([cfg]);
	}
});

Ext.define('Foss.Seal.Checkout.CenterPanel', {
	extend:'Ext.container.Container',
	cls: 'autoHeight',
	bodyCls: 'autoHeight',
	layout: 'hbox',
	sealNumGrid: null,
	getSealNumGrid: function(){
		if(this.sealNumGrid==null){
			this.sealNumGrid = Ext.create('Foss.Seal.Checkout.SealGrid',{
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
			this.billNoGridPanel = Ext.create('Foss.load.Seal.Checkout.BillNoGridPanel',{
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
		me.items = [me.getSealNumGrid(), me.getBillNoGridPanel()];
		me.callParent([cfg]);
	}
});

Ext.define('Foss.Seal.Checkout.FooterPanel',{
	extend: 'Ext.form.Panel',
	layout:'column',
	defaultType: 'textfield',
	items:[{
		name: 'memo',
		columnWidth: 1,
		labelAlign: 'left',
		fieldLabel: load.seal.i18n('foss.load.seal.sealMemo'),
		columnWidth: 1
	},{
		xtype: 'label',
		columnWidth:.85,
		style: {
			width: '95%',
			marginTop: '15px',
			color: 'red'
		},
		text: '提示:封签破损不输入任何封签号.'
	},{
		xtype: 'button',
		marginTop: '15px',
		plugins:Ext.create('Deppon.ux.ButtonLimitingPlugin',{
			seconds: 5
		}),
		name: 'save',
		text: load.seal.i18n('foss.load.seal.save'),
		disabled: !load.seal.isPermission('load/checkSealButton'),
		hidden: !load.seal.isPermission('load/checkSealButton'),
		columnWidth:.15,
		html: '&nbsp;',
		handler:function(){
			var headerForm = load.seal.checkSealHeaderPanel.getForm(),
				footerForm = load.seal.checkSealFooterPanel.getForm(),
				sealNewRecords = load.seal.checkSealCenterPanel.getSealNumGrid().getStore().getNewRecords(),
				array = new Array(),
				submitValue = null;
				seal_header = headerForm.getValues(),
				seal_footer = footerForm.getValues();
			if(!headerForm.isValid()) {
				return;
			}
//			if(backSealNewRecords.length == 0 && sideSealNewRecords.length == 0) {
//				Ext.ux.Toast.msg('提示', '请录入封签明细!');
//				return;
//			}
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
			var newEntity = {sealVo:{seal:seals, sealDestDetails:array}};
			//这里是否需要还需商榷, 如果不需要则直接执行else里的内容   mark
			if(array.length == 0) {
				//封签明细为空则默认为破损, 是否确认?
				var msg = load.seal.i18n('foss.load.seal.wrong.sealInfoBeNullDefaultBeDamaged');
				Ext.Msg.confirm(load.seal.i18n('foss.load.seal.prompt'), msg, function(optional){
					if(optional == 'yes'){
						newEntity.sealVo.seal.sealState = 'DAMAGED';
						load.seal.saveSealDest(newEntity);
					}
				})
			} else {
				//请求action做差异校验
				Ext.Ajax.request({
					url: load.realPath('checkoutSeal.action'),
					jsonData: newEntity,
					success: function(response){
						var json = Ext.decode(response.responseText);
						var opt = 1;
						//如果有差异则给予提示, 无差异则直接保存.
						if(json.sealVo.isdiff) {
							//卸车校验封签号码与绑定封签号码不一致，确认或者取消!
							var msg = load.seal.i18n('foss.load.seal.wrong.checkSealNotEqualsBindSeal');
							Ext.Msg.confirm(load.seal.i18n('foss.load.seal.prompt'), msg, function(optional){
								if(optional == 'yes'){
									newEntity.sealVo.seal.sealState = 'EXCEPTION';
									//校验完毕后保存卸车封签
									load.seal.saveSealDest(newEntity);
								}
							})
						} else {
							newEntity.sealVo.seal.sealState = 'NORMAL';
							//校验完毕后保存卸车封签
							load.seal.saveSealDest(newEntity);
						}
					},
					exception : function(response) {
						var json = Ext.decode(response.responseText);
						Ext.ux.Toast.msg(load.seal.i18n('foss.load.seal.wrong.checkFail'), json.message, 'error');
					}
				});
			}
		}
	}]
});
//校验完毕后保存卸车封签
load.seal.saveSealDest = function (newEntity){
		Ext.Ajax.request({
			url: load.realPath('saveSealDest.action'),
			jsonData: newEntity,
			success: function(response){
				var json = Ext.decode(response.responseText);
				Ext.ux.Toast.msg(load.seal.i18n('foss.load.seal.prompt'), load.seal.i18n('foss.load.seal.saveSuccess'));
				var queryForm = Ext.getCmp('Foss_Seal_QueryForm_Id');
				load.seal.checkoutWindow.hide();
				queryForm.form.findField('vehicleNo').setValue(newEntity.sealVo.seal.vehicleNo);
				queryForm.form.findField('sealType').setValue(newEntity.sealVo.seal.sealType);
			},
			exception : function(response) {
				var json = Ext.decode(response.responseText);
				Ext.ux.Toast.msg(load.seal.i18n('foss.load.seal.wrong.saveFail'), json.message, 'error');
			}
		});

}

Ext.define('Foss.Seal.Checkout.SealPanel',{
	extend: 'Ext.panel.Panel',
	frame: true,
	centerPanel: null,
	getCenterPanel : function(){
		if(this.centerPanel == null) {
			this.centerPanel = Ext.create('Foss.Seal.Checkout.CenterPanel');
			load.seal.checkSealCenterPanel = this.centerPanel;
		}
		return this.centerPanel;
	},
	footerPanel: null,
	getFooterPanel : function() {
		if(this.footerPanel == null) {
			this.footerPanel = Ext.create('Foss.Seal.Checkout.FooterPanel');
			load.seal.checkSealFooterPanel = this.footerPanel;
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

Ext.define('Foss.load.seal.Checkout.BillNoGridModel',{
	extend:'Ext.data.Model',
	fields: [
		{name: 'vehicleNo' , type: 'string'},
		{name: 'lineName' , type: 'string'},
		{name: 'driverName' , type: 'string'},
		{name: 'status' , type: 'string'},
		{name: 'billNo' , type: 'string'},
	]
});

Ext.define('Foss.load.seal.Checkout.BillNoGridStore',{
	extend: 'Ext.data.Store',
	model: 'Foss.load.seal.Checkout.BillNoGridModel',
	proxy: {
		type: 'memory',
		reader: {
			type: 'json',
			root: 'items'
		}
	}
});

//表格panel
Ext.define('Foss.load.Seal.Checkout.BillNoGridPanel', {
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
			me.store = Ext.create('Foss.load.seal.Checkout.BillNoGridStore');
			me.callParent([cfg]);
		}
});

/****************2.校验卸车封签信息  end**************/