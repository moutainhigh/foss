// 线路一
Ext.define('Foss.forecastQuantity.JoinCarAdjust.LineInfoOnePanel', {
	extend : 'Ext.form.Panel',
	frame : true,
	height : 150,
	layout : 'column',
	style : 'text-align:center;',
	defaults : {
		xtype : 'label'
	},
	bindData: function(fromRecord){
		var me = this,
			form = me.getForm();
		me.queryById('one_path').setText(fromRecord.get('belongOrgCodeName')+ '-'	+ fromRecord.get('relevantOrgCodeName'));
		if(null!=fromRecord.get('deviationVolume')&&""!=fromRecord.get('deviationVolume')){
			var deviationVolume = parseFloat(fromRecord.get('deviationVolume'));
		}else{
			var deviationVolume = 0;
		}
		form.findField('oneOrigVolume').setValue(parseFloat(fromRecord.get('volumeTotal'))+deviationVolume);
	},
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	},
	items : [{
		padding : '10 0 10 0',
		columnWidth: 1,
		text : platform.forecastQuantity.i18n('foss.platform.forecastQuantity.joinCarAdjust.lineOne'),
		style : 'font-weight:bold;'
	}, {
		columnWidth: 1,
		itemId: 'one_path'
	}, {
		columnWidth: .9,
		padding : '10 0 10 0',
		xtype : 'displayfield',
		fieldLabel : platform.forecastQuantity.i18n('foss.platform.forecastQuantity.joinCarAdjust.OrigVolume'),
		labelWidth: 70,
		allowBlank : true,
		name : 'oneOrigVolume'
	}, {
		padding : '13 0 10 0',
		xtype : 'label',
		columnWidth: .1,
		text : platform.forecastQuantity.i18n('foss.platform.forecastQuantity.volumeUnit')
	}, {
		columnWidth: .9,
		padding : '0 0 10 0',
		xtype : 'displayfield',
		fieldLabel : platform.forecastQuantity.i18n('foss.platform.forecastQuantity.joinCarAdjust.adjustVolume'),
		labelWidth: 70,
		allowBlank : true,
		name : 'one_path_adjust_volume'
	}, {
		padding : '10 0 10 0',
		xtype : 'label',
		columnWidth: .1,
		text : platform.forecastQuantity.i18n('foss.platform.forecastQuantity.volumeUnit')
	}]
});

// 线路二
Ext.define('Foss.forecastQuantity.JoinCarAdjust.LineInfoTwoPanel', {
	extend : 'Ext.form.Panel',
	frame : true,
	height : 150,
	layout : 'column',
	style : 'text-align:center;',
	defaults : {
		xtype : 'label'
	},
	bindData: function(toRecord){
		var me = this,
		form = me.getForm();
		me.queryById('two_path').setText(toRecord.get('belongOrgCodeName')+ '-'	+ toRecord.get('relevantOrgCodeName'));
		if(null!=toRecord.get('deviationVolume') && ""!=toRecord.get('deviationVolume')){
			var deviationVolume = parseFloat(toRecord.get('deviationVolume'));
		}else{
			var deviationVolume = 0;
		}
		form.findField('twoOrigVolume').setValue(parseFloat(toRecord.get('volumeTotal'))+deviationVolume);
	},
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	},
	items : [{
		padding : '10 0 10 0',
		columnWidth: 1,
		text : platform.forecastQuantity.i18n('foss.platform.forecastQuantity.joinCarAdjust.lineTwo'),
		style : 'font-weight:bold;'
	}, {
		columnWidth: 1,
		itemId: 'two_path'
	}, {
		columnWidth: .9,
		padding : '10 0 10 0',
		xtype : 'displayfield',
		fieldLabel : platform.forecastQuantity.i18n('foss.platform.forecastQuantity.joinCarAdjust.OrigVolume'),
		labelWidth: 70,
		allowBlank : true,
		name : 'twoOrigVolume'
	}, {
		padding : '13 0 10 0',
		xtype : 'label',
		columnWidth: .1,
		text : platform.forecastQuantity.i18n('foss.platform.forecastQuantity.volumeUnit')
	}, {
		columnWidth: .9,
		padding : '0 0 10 0',
		xtype : 'displayfield',
		fieldLabel : platform.forecastQuantity.i18n('foss.platform.forecastQuantity.joinCarAdjust.adjustVolume'),
		labelWidth: 70,
		allowBlank : true,
		name : 'two_path_adjust_volume'
	}, {
		padding : '10 0 10 0',
		xtype : 'label',
		columnWidth: .1,
		text : platform.forecastQuantity.i18n('foss.platform.forecastQuantity.volumeUnit')
	}]
});

// 中间位置
Ext.define('Foss.forecastQuantity.JoinCarAdjust.AdjustInputPanel', {
	extend : 'Ext.form.Panel',
	frame : false,
	height : 150,
	layout : 'column',
	padding : '5 0 0 0',
	style : 'text-align:center;',
	bindData: function(value){
		var me = this;
		me.getForm().findField('adjustVolume').setValue(value);
	},
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	},
	items : [{
			xtype : 'container',
			columnWidth : 1,
			height : 15,
			html : '&nbsp;'
		}, {
			xtype : 'button',
			columnWidth : 1,
			text : '&nbsp;>>>&nbsp;',
			handler : function() {
				this.up('window').adjust(-1);
			}
		}, {
			xtype : 'container',
			columnWidth : 1,
			height : 15,
			html : '&nbsp;'
		}, {
			xtype : 'container',
			layout : 'column',
			columnWidth : 1,
			items : [{
						xtype : 'label',
						columnWidth : .4,
						text : platform.forecastQuantity.i18n('foss.platform.forecastQuantity.joinCarAdjust.adjustVolume')
					}, {
						xtype : 'numberfield',
						name : 'adjustVolume',
						columnWidth : .45
					}, {
						xtype : 'label',
						columnWidth : .15,
						text : platform.forecastQuantity.i18n('foss.platform.forecastQuantity.volumeUnit')
					}]
		}, {
			xtype : 'container',
			columnWidth : 1,
			height : 15,
			html : '&nbsp;'
		}, {
			xtype : 'button',
			columnWidth : 1,
			text : '&nbsp;<<<&nbsp;',
			handler : function() {
				this.up('window').adjust(1);
			}
		}]
});

// 按钮区域
Ext.define('Foss.forecastQuantity.JoinCarAdjust.OperatePanel', {
	extend : 'Ext.panel.Panel',
	frame : false,
	height : 120,
	layout : 'column',
	items : [{
				xtype : 'container',
				html : '&nbsp;',
				columnWidth : .6
			}, {
				xtype : 'button',
				disabled : !platform.forecastQuantity.isPermission('platform/changeQuantityButton'),
				hidden : !platform.forecastQuantity.isPermission('platform/changeQuantityButton'),
				text : platform.forecastQuantity.i18n('foss.platform.forecastQuantity.check'),
				cls : 'yellow_button',
				columnWidth : .2,
				handler : function() {
					var window = this.up('window');
					var adjustVolume = window.adjustVolume;
					if (adjustVolume == 0) {
						Ext.MessageBox.alert(platform.forecastQuantity.i18n('foss.platform.forecastQuantity.hint'), platform.forecastQuantity.i18n('foss.platform.forecastQuantity.noChange'));
						return false;
					}
					var forecastQuantityList = new Array();
					var fromRecord = window.fromRecord;
					var toRecord = window.toRecord;
					// 更新左边的调整合车体积
					if(null!=fromRecord.get('deviationVolume')&&""!=fromRecord.get('deviationVolume')){
						var deviationVolume = parseFloat(fromRecord.get('deviationVolume'));
					}else{
						var deviationVolume = 0;
					}
					fromRecord.set('deviationVolume', deviationVolume + adjustVolume);
					// 更新右边的调整合车体积
					if(null!=toRecord.get('deviationVolume')&&""!=toRecord.get('deviationVolume')){
						var deviationVolume = parseFloat(toRecord.get('deviationVolume'));
					}else{
						var deviationVolume = 0;
					}
					toRecord.set('deviationVolume', deviationVolume - adjustVolume);
					forecastQuantityList.push(fromRecord.data);
					forecastQuantityList.push(toRecord.data);
					var changeQuantityEntity = {
						"modifyWeight" : adjustVolume,
						"belongTransferCenter" : fromRecord.get('belongOrgCode')
					};
					// 如果小于零则表示是从左边向右边合车
					if (adjustVolume < 0) {
						changeQuantityEntity.origDestOrg = fromRecord.get('relevantOrgCode');
						changeQuantityEntity.newDestOrg = toRecord.get('relevantOrgCode');
					} else if (adjustVolume > 0) {// 否则是右边向左边合车
						changeQuantityEntity.origDestOrg = toRecord.get('relevantOrgCode');
						changeQuantityEntity.newDestOrg = fromRecord.get('relevantOrgCode');
					}
					var params = {
						forecastVO : {
							forecastQuantityList : forecastQuantityList,
							changeQuantityEntity : changeQuantityEntity
						}
					}
					this.setDisabled(true);
					Ext.Ajax.request({
								url : platform.realPath('changeQuantity.action'),
								jsonData : params,
								success : function(response) {
									Ext.ux.Toast.msg(platform.forecastQuantity.i18n('foss.platform.forecastQuantity.hint'), platform.forecastQuantity.i18n('foss.platform.forecastQuantity.success'));
									platform.forecastQuantity.queryResult.store.load();
									window.close();
								},
								exception : function(response) {
									var result = Ext
											.decode(response.responseText);
									Ext.ux.Toast.msg(platform.forecastQuantity.i18n('foss.platform.forecastQuantity.hint'), result.message,'error');
								}
							});
				}
			}, {
				xtype : 'button',
				text : platform.forecastQuantity.i18n('foss.platform.forecastQuantity.cancle'),
				cls : 'yellow_button',
				columnWidth : .2,
				handler : function() {
					this.up('window').close();
				}
			}]
});