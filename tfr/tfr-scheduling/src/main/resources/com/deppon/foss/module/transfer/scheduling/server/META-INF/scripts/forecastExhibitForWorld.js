/**
 * 全国展会货统计货量查询
 * 
 */

//自定义工具类
var forecast={};
forecast.util={};
//转换日期
forecast.util.ratioConvert = function(value) {
	if (value != null) {
		return value + '%';
	} else {
		return null;
	}
};

// model
Ext.define('Foss.scheduling.forecastExhibitForWorld.Model', {
	extend : 'Ext.data.Model',
	fields : [ {
		/** 驻地外场编码 */
		name : 'transferCenterCode',
		type : 'string'
	}, {
		/** 驻地外场名称 */
		name : 'transferCenterName',
		type : 'string'
	}, {
		/** 总票数 */
		name : 'totalWaybillQty',
		type : 'string'
	}, {
		/** 总重量 */
		name : 'totalWeight',
		type : 'string'
	}, {
		/** 总体积 */
		name : 'totalVolume',
		type : 'string',
	}, {
		/** 开单未交接总重量 */
		name : 'noBillWeight',
		type : 'string',
	}, {
		/** 开单未交接总体积 */
		name : 'noBillVolume',
		type : 'string',
	}, {
		/**开单未交接总票数 */
		name : 'noBillWaybillQty',
		type : 'string'
	}, {
		/** 库存总重量 */
		name : 'stockWeight',
		type : 'string'
	}, {
		/** 库存总体积 */
		name : 'stockVolume',
		type : 'string'
	}, {
		/** 库存总票数 */
		name : 'stockWaybillQty',
		type : 'string'
	}, {
		/** 在途总重量 */
		name : 'runningWeight',
		type : 'string'
	}, {
		/** 在途总体积 */
		name : 'runningVolume',
		type : 'string'
	}, {
		/** 在途总票数 */
		name : 'runningWaybillQty',
		type : 'string'
	}, {
		/** 库存占比 */
		name : 'stockRatio',
		type : 'string',
		convert : function(value){
			return forecast.util.ratioConvert(value);
		}
	}]
});

Ext.define('Foss.scheduling.forecastExhibitForWorld.store', {
	extend : 'Ext.data.Store',
	model : 'Foss.scheduling.forecastExhibitForWorld.Model',
	proxy : {
		// 以JSON的方式加载数据
		type : 'ajax',
		actionMethods : 'POST',
		url : scheduling.realPath('queryForecastExhibitForWorldList.action'),
		reader : {
			type : 'json',
			root : 'exhibitVo.exhibitForecastForWorldList',
			totalProperty : 'totalCount',
			successProperty : 'success'
		}
	},
	listeners : {
		beforeload : function(store, operation, eOpts) {
			var queryForm = scheduling.forecastExhibitForWorld.queryForm;
			if (queryForm != null) {
				var queryParams = queryForm.getValues();
				Ext.apply(operation, {
					params : queryParams
				});	
			}
		},
		load : function( store, records, successful, eOpts){
			var exhibitVo = store.proxy.reader.rawData.exhibitVo;
			if(!store.proxy.reader.rawData.success){
				Ext.ux.Toast.msg('提示', '查询失败，' + store.proxy.reader.rawData.message, 'error');
			}
			Ext.getCmp('Foss_scheduling_forecastExhibitForWorld_totalWaybillQty').setValue(exhibitVo.totalWaybillQty);
			Ext.getCmp('Foss_scheduling_forecastExhibitForWorld_totalWeight').setValue(exhibitVo.totalWeight + '公斤');
			Ext.getCmp('Foss_scheduling_forecastExhibitForWorld_totalVolume').setValue(exhibitVo.totalVolume + '方');
		}
	}
});

//查询条件
Ext.define('Foss.scheduling.forecastExhibitForWorld.search', {
	extend : 'Ext.form.Panel',
	title : scheduling.forecastExhibitForWorld.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyQueryForm.title'), // 查询条件
	frame : true,
	collapsible : true,
	animCollapse : true,
	defaultType : 'textfield',
	layout : 'column',
	defaults : {
		labelWidth : 85,
		margin : '5 10 5 10'
	},
	items : [{
		name : 'exhibitVo.transferCenterCode',
		fieldLabel : scheduling.forecastExhibitForWorld
				.i18n('foss.scheduling.adjustTransportationPath.transferCenter'), // 外场
		columnWidth : .25,
		allowBlank : true,
		xtype : 'dynamicorgcombselector',
		type : 'ORG',
		transferCenter : 'Y',
	}, {
		xtype : 'container',
		columnWidth : .85,
		html : '&nbsp;'
	}, {
		border : 1,
		xtype : 'container',
		columnWidth : 1,
		defaultType : 'button',
		layout : 'column',
		items : [{
			xtype : 'container',
			columnWidth : .84,
			html : '&nbsp;'
		}, {
			text : scheduling.forecastExhibitForWorld
					.i18n('foss.scheduling.statisticalInquiries.changeAssembleWayWindow.detailExport'), // 导出
			cls : 'yellow_button',
			columnWidth : .08,
			handler : function() {
				var form = scheduling.forecastExhibitForWorld.queryForm
						.getForm();
				if (!form.isValid()) {
					Ext.Msg
							.alert(
									scheduling.forecastExhibitForWorld
											.i18n('Foss.scheduling.validation.alert.title'),
									scheduling.forecastExhibitForWorld
											.i18n('Foss.scheduling.validation.tip.search'));
					return false;
				}
				
				var queryParams = form.getValues();
				
				if (!Ext.fly('downloadAttachFileForm')) {
					var frm = document.createElement('form');
					frm.id = 'downloadAttachFileForm';
					frm.style.display = 'none';
					document.body.appendChild(frm);
				}
				
				Ext.Ajax.request({
					url : scheduling
							.realPath('queryForecastExhibitForWorldExcelStream.action'),
					form : Ext.fly('downloadAttachFileForm'),
					method : 'POST',
					params : queryParams,
					isUpload : true,
					exception : function(response) {
						var result = Ext
								.decode(response.responseText);
						top.Ext.MessageBox.alert(scheduling.forecastExhibitForWorld.i18n('foss.scheduling.forecastQuantity.forecastQuantity.exportFail'),
								result.message);
					}
				});
			}
		}, {
			text : scheduling.forecastExhibitForWorld.i18n('Foss.scheduling.button.search'), // 查询
			cls : 'yellow_button',
			columnWidth : .08,
			handler : function() {
				var form = scheduling.forecastExhibitForWorld.queryForm
				.getForm();
				if (!form.isValid()) {
					Ext.Msg
					.alert(
							scheduling.forecastExhibitForWorld
							.i18n('Foss.scheduling.validation.alert.title'),
							scheduling.forecastExhibitForWorld
							.i18n('Foss.scheduling.validation.tip.search'));
					return false;
				}
				scheduling.forecastExhibitForWorld.pagingBar.moveFirst();
			}
	}]
	}]
});


// grid
Ext.define('Foss.scheduling.forecastExhibitForWorld.grid', {
	extend : 'Ext.grid.Panel',
	frame : true,
	bodyCls : 'autoHeight',
	height : 600,
	cls : 'autoHeight',
	autoScroll : true,
	defaults : {
		align : 'center'
	},
	title : scheduling.forecastExhibitForWorld.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyQueryGrid.title'), // 查询结果
	columns : [{
		xtype : 'ellipsiscolumn',
		header : scheduling.forecastExhibitForWorld.i18n('foss.scheduling.adjustTransportationPath.transferCenter'), // 外场
		dataIndex : 'transferCenterName',
		flex : 0.4
	},{
		xtype : 'ellipsiscolumn',
		header : scheduling.forecastExhibitForWorld.i18n('foss.scheduling.forecastQuantity.forecastQuantity.waybillQtyTotal'), // 总票数
		dataIndex : 'totalWaybillQty',
		flex : 0.4
	},{
		xtype : 'ellipsiscolumn',
		header : scheduling.forecastExhibitForWorld.i18n('foss.scheduling.forecastexhibit.grid.totalWeight'), // 总重量
		dataIndex : 'totalWeight',
		flex : 0.4
	},{
		xtype : 'ellipsiscolumn',
		header : scheduling.forecastExhibitForWorld.i18n('foss.scheduling.forecastexhibit.grid.totalVolume'), // 总体积
		dataIndex : 'totalVolume',
		flex : 0.4
	},{
		text : '开单未交接',
		columns : [{
			xtype : 'ellipsiscolumn',
			header : scheduling.forecastExhibitForWorld.i18n('foss.scheduling.adjustTransportationPath.joinCar.areaWeightTotal'), // 重量
			dataIndex : 'noBillWeight',
			flex : 0.4
		},{
			xtype : 'ellipsiscolumn',
			header : scheduling.forecastExhibitForWorld.i18n('foss.scheduling.passOrderVehicle.volume'), // 体积
			dataIndex : 'noBillVolume',
			flex : 0.4
		},{
			xtype : 'ellipsiscolumn',
			header : scheduling.forecastExhibitForWorld.i18n('foss.scheduling.forecastexhibit.grid.waybillqty'), // 票数
			dataIndex : 'noBillWaybillQty',
			flex : 0.4
		}]
	},{
		text : '库存余货',
		columns : [{
			xtype : 'ellipsiscolumn',
			header : scheduling.forecastExhibitForWorld.i18n('foss.scheduling.adjustTransportationPath.joinCar.areaWeightTotal'), // 重量
			dataIndex : 'stockWeight',
			flex : 0.4
		},{
			xtype : 'ellipsiscolumn',
			header : scheduling.forecastExhibitForWorld.i18n('foss.scheduling.passOrderVehicle.volume'), // 体积
			dataIndex : 'stockVolume',
			flex : 0.4
		},{
			xtype : 'ellipsiscolumn',
			header : scheduling.forecastExhibitForWorld.i18n('foss.scheduling.forecastexhibit.grid.waybillqty'), // 票数
			dataIndex : 'stockWaybillQty',
			flex : 0.4
		}]
	},{
		text : '在途',
		columns : [{
			xtype : 'ellipsiscolumn',
			header : scheduling.forecastExhibitForWorld.i18n('foss.scheduling.adjustTransportationPath.joinCar.areaWeightTotal'), // 重量
			dataIndex : 'runningWeight',
			flex : 0.4
		},{
			xtype : 'ellipsiscolumn',
			header : scheduling.forecastExhibitForWorld.i18n('foss.scheduling.passOrderVehicle.volume'), // 体积
			dataIndex : 'runningVolume',
			flex : 0.4
		},{
			xtype : 'ellipsiscolumn',
			header : scheduling.forecastExhibitForWorld.i18n('foss.scheduling.forecastexhibit.grid.waybillqty'), // 票数
			dataIndex : 'runningWaybillQty',
			flex : 0.4
		}]
	},{
		xtype : 'ellipsiscolumn',
		header : scheduling.forecastExhibitForWorld.i18n('foss.scheduling.forecastexhibit.grid.stockRatio'), // 库存占比
		dataIndex : 'stockRatio',
		flex : 0.4,
			renderer: function(value){
				 var temp = parseInt(value.split('%')[0]);
			  		if(temp >= 30){
			  			return '<span style="color:red;">'+value+'</span>';
			  		}else{
			  			return '<span style="color:#333;">'+value+'</span>'
			  		}
		    }
	}],dockedItems:[{
		   xtype:'toolbar',
		   id : 'Foss_scheduling_forecastExhibitForWorld_totalWaybillQty_toobar',
		   dock:'bottom',
		   layout:'column',
		   defaults:{
			 xtype:'textfield',
			 value:'0',
			 readOnly:true
		   },
		   items:[{
			   //fieldLabel:'总票数',
			   fieldLabel: scheduling.forecastExhibitForWorld.i18n('foss.scheduling.forecastQuantity.forecastQuantity.waybillQtyTotal'),
			   id:'Foss_scheduling_forecastExhibitForWorld_totalWaybillQty',
			   columnWidth:.20,
			   dataIndex: 'totalWaybillQty'
		   },{
			   //fieldLabel:'总重量(公斤)',
			   fieldLabel: scheduling.forecastExhibitForWorld.i18n('foss.scheduling.forecastexhibit.grid.totalWeight'),
			   id:'Foss_scheduling_forecastExhibitForWorld_totalWeight',
			   columnWidth:.20,
			   dataIndex: 'totalWeight'
		   },{
			   //fieldLabel:'总体积(方)',
			   fieldLabel: scheduling.forecastExhibitForWorld.i18n('foss.scheduling.forecastexhibit.grid.totalVolume'),
			   id:'Foss_scheduling_forecastExhibitForWorld_totalVolume',
			   columnWidth:.20,
			   dataIndex: 'totalVolume'
		   }]}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext
				.create('Foss.scheduling.forecastExhibitForWorld.store');
		me.bbar = Ext.create('Deppon.StandardPaging', {
					store : me.store
				});
		scheduling.forecastExhibitForWorld.pagingBar = me.bbar;
		me.callParent([cfg]);
	},
	viewConfig : {
		enableTextSelection : true
	}
});


scheduling.forecastExhibitForWorld.queryForm = Ext.create('Foss.scheduling.forecastExhibitForWorld.search');
scheduling.forecastExhibitForWorld.queryGrid = Ext.create('Foss.scheduling.forecastExhibitForWorld.grid');

Ext.onReady(function() {
	Ext.QuickTips.init();
	Ext.create('Ext.panel.Panel', {
				id : 'T_scheduling-forecastExhibitForWorldindex_content',
				cls : "panelContentNToolbar",
				bodyCls : 'panelContentNToolbar-body',
				layout : 'auto',
				items : [scheduling.forecastExhibitForWorld.queryForm,
				         scheduling.forecastExhibitForWorld.queryGrid],
				renderTo : 'T_scheduling-forecastExhibitForWorldindex-body'
			});
});

