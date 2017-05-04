/**
 * 展会货统计货量查询
 * 
 */
//自定义工具类
var forecast={};
forecast.util={};
//转换日期
forecast.util.dateConvert = function(value) {
	if (value != null) {
		var date = new Date(value);
		return Ext.Date.format(date, 'Y-m-d H:i:s');
	} else {
		return null;
	}
};

// model
Ext.define('Foss.scheduling.forecastExhibit.Model', {
	extend : 'Ext.data.Model',
	fields : [ {
		/** 运单号 */
		name : 'waybillNo',
		type : 'string'
	}, {
		/** 展会货状态 */
		name : 'status',
		type : 'string'
	}, {
		/** 库存件数 */
		name : 'stockGoodsQty',
		type : 'string'
	}, {
		/** 开单件数 */
		name : 'goodsQtyTotal',
		type : 'string'
	}, {
		/** 开单时间 */
		name : 'billTime',
		type : 'string',
		convert : function(value){
			return forecast.util.dateConvert(value);
		}
	}, {
		/** 入库时间 */
		name : 'inStockTime',
		type : 'string',
		convert : function(value){
			return forecast.util.dateConvert(value);
		}
	}, {
		/** 计划到达时间 */
		name : 'planArriveTime',
		type : 'string',
		convert : function(value){
			return forecast.util.dateConvert(value);
		}
	}, {
		/**运输性质code */
		name : 'productCode',
		type : 'string'
	}, {
		/** 运输性质名称 */
		name : 'productName',
		type : 'string'
	}, {
		/** 提货方式 */
		name : 'receiveMethod',
		type : 'string'
	}, {
		/** 重量 */
		name : 'weight',
		type : 'string'
	}, {
		/** 体积 */
		name : 'volume',
		type : 'string'
	}, {
		/** 货物名称 */
		name : 'goodsName',
		type : 'string'
	}, {
		/** 最终到达部门 code */
		name : 'reachOrgCode',
		type : 'string'
	}, {
		/** 最终到达部门 Name */
		name : 'reachOrgName',
		type : 'string'
	}, {
		/** 包装 */
		name : 'packing',
		type : 'string'
	}, {
		/** 流水号 */
		name : 'goodsNos',
		type : 'string'
	}, {
		/** 创建时间*/
		name : 'createTime',
		type : 'string',
		convert : function(value){
			return forecast.util.dateConvert(value);
		}
	}, {
		/** 是否有效 */
		name : 'active',
		type : 'string'
	}]
});

//货物状态模型
Ext.define('Foss.partialline.model.goods', {
	extend: 'Ext.data.Model',
	//定义字段
	fields: [
		{name: 'id',type:'string'},
		{name: 'name',type:'string'},
		{name: 'value',type:'string'}
	]
});

// store
//货物状态数据源
Ext.define('Foss.scheduling.forecastExhibit.store.goodStatus',{
	extend: 'Ext.data.Store',
	model: 'Foss.partialline.model.goods',
	data : {
		'items':[		
		    /** 统计货量查询 开单未交接 */
			{id : '0', name : scheduling.forecastExhibit.i18n('foss.scheduling.forecastQuantity.forecastQuantity.billing.gui'),  value: 'NOTRANSFERBILLING'},//开单未交接
			/** 统计货量查询 在途 */
			{id : '1', name : scheduling.forecastExhibit.i18n('foss.scheduling.forecastQuantity.forecastQuantity.intransit.gui'),  value: 'INTRANSIT'},//在途
			/** 统计货量查询 在库 */
			{id : '2', name : scheduling.forecastExhibit.i18n('foss.scheduling.forecastQuantity.forecastQuantity.inventory.gui'),  value: 'INLIBRARY'},//在库
		]
	},
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


Ext.define('Foss.scheduling.forecastExhibit.store', {
	extend : 'Ext.data.Store',
	model : 'Foss.scheduling.forecastExhibit.Model',
	proxy : {
		// 以JSON的方式加载数据
		type : 'ajax',
		actionMethods : 'POST',
		url : scheduling.realPath('queryForecastExhibitList.action'),
		reader : {
			type : 'json',
			root : 'exhibitVo.exhibitForecastDto.exhibitList',
			totalProperty : 'totalCount',
			successProperty : 'success'
		}
	},
	listeners : {
		beforeload : function(store, operation, eOpts) {
			var queryForm = scheduling.forecastExhibit.queryForm;
			if (queryForm != null) {
				var queryParams = queryForm.getValues();
				queryParams['exhibitVo.exhibitForecastDto.transferCenterCode'] = FossUserContext.getCurrentDept().code;
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
			Ext.getCmp('Foss_scheduling_forecastExhibit_totalWaybillQty').setValue(exhibitVo.exhibitForecastDto.totalWaybillQty);
			Ext.getCmp('Foss_scheduling_forecastExhibit_totalWeight').setValue(exhibitVo.exhibitForecastDto.totalWeight);
			Ext.getCmp('Foss_scheduling_forecastExhibit_totalVolume').setValue(exhibitVo.exhibitForecastDto.totalVolume);
			Ext.getCmp('Foss_scheduling_forecastExhibit_stockRatio').setValue(exhibitVo.exhibitForecastDto.stockRatio + '%');
		}
	}
});

//查询条件
Ext.define('Foss.scheduling.forecastExhibit.search', {
	extend : 'Ext.form.Panel',
	title : scheduling.forecastExhibit.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyQueryForm.title'), // 查询条件
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
		name : 'exhibitVo.exhibitForecastDto.transferCenterCode',
		fieldLabel : scheduling.forecastExhibit
				.i18n('foss.scheduling.forecast.currentOrgCode'), // 当前部门
		columnWidth : .25,
		value : FossUserContext.getCurrentDept().name,
		allowBlank:true,
		readOnly:true
	}, {
		name : 'exhibitVo.exhibitForecastDto.status',
		fieldLabel : scheduling.forecastExhibit.i18n('foss.scheduling.forecastQuantity.forecastQuantity.goodStatus'), // 货物状态
		columnWidth : .25,
		xtype: 'combobox',
		mode:'local',
		queryMode: 'local',		
		forceSelection:true,
		editable:false,
		displayField:   'name',
		valueField:     'value',		
		store: Ext.create('Foss.scheduling.forecastExhibit.store.goodStatus'),
		value:'NOTRANSFERBILLING'
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
			text : scheduling.forecastExhibit
					.i18n('foss.scheduling.statisticalInquiries.changeAssembleWayWindow.detailExport'), // 导出
			cls : 'yellow_button',
			columnWidth : .08,
			handler : function() {
				var form = scheduling.forecastExhibit.queryForm
						.getForm();
				if (!form.isValid()) {
					Ext.Msg
							.alert(
									scheduling.forecastExhibit
											.i18n('Foss.scheduling.validation.alert.title'),
									scheduling.forecastExhibit
											.i18n('Foss.scheduling.validation.tip.search'));
					return false;
				}
				
				var queryParams = form.getValues();
				queryParams['exhibitVo.exhibitForecastDto.transferCenterCode'] = FossUserContext.getCurrentDept().code;
				
				if (!Ext.fly('downloadAttachFileForm')) {
					var frm = document.createElement('form');
					frm.id = 'downloadAttachFileForm';
					frm.style.display = 'none';
					document.body.appendChild(frm);
				}
				
				Ext.Ajax.request({
					url : scheduling
							.realPath('queryForecastExhibitExcelStream.action'),
					form : Ext.fly('downloadAttachFileForm'),
					method : 'POST',
					params : queryParams,
					isUpload : true,
					exception : function(response) {
						var result = Ext
								.decode(response.responseText);
						top.Ext.MessageBox.alert(scheduling.forecastExhibit.i18n('foss.scheduling.forecastQuantity.forecastQuantity.exportFail'),
								result.message);
					}
				});
			}
		}, {
			text : scheduling.forecastExhibit.i18n('Foss.scheduling.button.search'), // 查询
			cls : 'yellow_button',
			columnWidth : .08,
			handler : function() {
				var form = scheduling.forecastExhibit.queryForm
				.getForm();
				if (!form.isValid()) {
					Ext.Msg
					.alert(
							scheduling.forecastExhibit
							.i18n('Foss.scheduling.validation.alert.title'),
							scheduling.forecastExhibit
							.i18n('Foss.scheduling.validation.tip.search'));
					return false;
				}
				scheduling.forecastExhibit.pagingBar.moveFirst();
			}
	}]
	}]
});


// grid
Ext.define('Foss.scheduling.forecastExhibit.grid', {
	extend : 'Ext.grid.Panel',
	frame : true,
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	title : scheduling.forecastExhibit.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyQueryGrid.title'), // 查询结果
	columns : [{
		xtype : 'ellipsiscolumn',
		header : scheduling.forecastExhibit.i18n('foss.scheduling.adjustTransportationPath.adjustTransportationPath.waybillNo'), // 运单号
		dataIndex : 'waybillNo',
		flex : 0.8
	},{
		xtype : 'ellipsiscolumn',
		header : scheduling.forecastExhibit.i18n('Foss.PassInviteVehicleIndex.Grid.Tip.CarInfo.weight'), // 重量(KG)
		dataIndex : 'weight',
		flex : 0.8
	},{
		xtype : 'ellipsiscolumn',
		header : scheduling.forecastExhibit.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.customerAndGoodsInfo.volume'), // 体积(方)
		dataIndex : 'volume',
		flex : 0.8
	},{
		xtype : 'ellipsiscolumn',
		header : scheduling.forecastExhibit.i18n('foss.scheduling.reCreateTransportationPath.transportModel'), // 运输性质name
		dataIndex : 'productName',
		flex : 0.8
	},{
		xtype : 'ellipsiscolumn',
		header : scheduling.forecastExhibit.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.customerAndGoodsInfo.goodsName'), // 货物名称
		dataIndex : 'goodsName',
		flex : 0.8
	},{
		xtype : 'ellipsiscolumn',
		header : scheduling.forecastExhibit.i18n('foss.scheduling.temprentalmark.label.packing'), // 包装
		dataIndex : 'packing',
		flex : 0.8
	},{
		xtype : 'ellipsiscolumn',
		header : scheduling.forecastExhibit.i18n('foss.scheduling.forecastexhibit.grid.stockqty'), //库存件数
		dataIndex : 'stockGoodsQty',
		flex : 0.8
	},{
		xtype : 'ellipsiscolumn',
		header : scheduling.forecastExhibit.i18n('foss.scheduling.forecastexhibit.grid.billqty'), // 开单件数
		dataIndex : 'goodsQtyTotal',
		flex : 0.8
	},{
		xtype : 'ellipsiscolumn',
		header : scheduling.forecastExhibit.i18n('foss.scheduling.reCreateTransportationPath.billingTime'), // 开单时间
		dataIndex : 'billTime',
		flex : 0.8
	},{
		xtype : 'ellipsiscolumn',
		header : scheduling.forecastExhibit.i18n('foss.scheduling.reCreateTransportationPath.inStoreTime'), // 入库时间
		dataIndex : 'inStockTime',
		flex : 0.8
	},{
		xtype : 'ellipsiscolumn',
		header : scheduling.forecastExhibit.i18n('Foss.PassInviteVehicleIndex.Grid.Tip.CarInfo.perdictArriveTime.time'), // 预计到达时间
		dataIndex : 'planArriveTime',
		flex : 0.8
	},{
		xtype : 'ellipsiscolumn',
		header : scheduling.forecastExhibit.i18n('foss.scheduling.temprentalmark.label.pickUpWayName'), // 提货方式
		dataIndex : 'receiveMethod',
		flex : 0.8
	},{
		xtype : 'ellipsiscolumn',
		header : scheduling.forecastExhibit.i18n('foss.scheduling.forecastQuantity.forecastQuantity.goodStatus'), // 货物状态
		dataIndex : 'status',
		flex : 0.8
	},{
		xtype : 'ellipsiscolumn',
		header : scheduling.forecastExhibit.i18n('foss.shortDeparturePlan.form.planSearch.destOrgCode.lable'), // 到达部门
		dataIndex : 'reachOrgName',
		flex : 0.8
	}],dockedItems:[{
		   xtype:'toolbar',
		   id : 'Foss_scheduling_forecastExhibit_totalWaybillQty_toobar',
		   dock:'bottom',
		   layout:'column',
		   defaults:{
			 xtype:'textfield',
			 value:'0',
			 readOnly:true
		   },
		   items:[{
			   //fieldLabel:'总票数',
			   id:'Foss_scheduling_forecastExhibit_totalWaybillQty',
			   fieldLabel: scheduling.forecastExhibit.i18n('foss.scheduling.forecastQuantity.forecastQuantity.waybillQtyTotal'),
			   columnWidth:.20,
			   dataIndex: 'totalWaybillQty'
		   },{
			   //fieldLabel:'总重量(KG)',
			   id:'Foss_scheduling_forecastExhibit_totalWeight',
			   fieldLabel: scheduling.forecastExhibit.i18n('foss.scheduling.forecastexhibit.grid.totalWeight'),
			   columnWidth:.20,
			   dataIndex: 'totalWeight'
		   },{
			   //fieldLabel:'总体积(方)',
			   id:'Foss_scheduling_forecastExhibit_totalVolume',
			   fieldLabel: scheduling.forecastExhibit.i18n('foss.scheduling.forecastexhibit.grid.totalVolume'),
			   columnWidth:.20,
			   dataIndex: 'totalVolume'
		   },{
			   //fieldLabel:'库存占比',
			   id:'Foss_scheduling_forecastExhibit_stockRatio',
			   fieldLabel: scheduling.forecastExhibit.i18n('foss.scheduling.forecastexhibit.grid.stockRatio'),
			   columnWidth:.20,
			   dataIndex: 'stockRatio',
			   listeners : {
				   change : function(_this,newValue,oldValue,eOpts){
					   var temp = parseInt(newValue.split('%')[0]);
				  		if(temp >= 30){
				  			_this.setFieldStyle('color:red;');
				  		}else{
				  			_this.setFieldStyle('color:#333;');
				  		}
				   }
			   }
		   }]}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext
				.create('Foss.scheduling.forecastExhibit.store');
		me.bbar = Ext.create('Deppon.StandardPaging',{
			store : me.store,
			pageSize : 20,
			maximumSize : 50,
			plugins : Ext.create('Deppon.ux.PageSizePlugin', {
				sizeList : [['20', 20], ['30', 30], ['40', 40], ['50', 50]]
			})
		});
		scheduling.forecastExhibit.pagingBar = me.bbar;
		me.callParent([cfg]);
	},
	viewConfig : {
		enableTextSelection : true
	}
});



scheduling.forecastExhibit.queryGrid =Ext.create('Foss.scheduling.forecastExhibit.grid') ;
scheduling.forecastExhibit.queryForm = Ext.create('Foss.scheduling.forecastExhibit.search');

Ext.onReady(function() {
	Ext.QuickTips.init();
	Ext.create('Ext.panel.Panel', {
				id : 'T_scheduling-forecastExhibitindex_content',
				cls : "panelContentNToolbar",
				bodyCls : 'panelContentNToolbar-body',
				layout : 'auto',
				items : [scheduling.forecastExhibit.queryForm,
				         scheduling.forecastExhibit.queryGrid],
				renderTo : 'T_scheduling-forecastExhibitindex-body'
			});
});

