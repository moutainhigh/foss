/**
 * 线路货量查询
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
Ext.define('Foss.platform.statisticalInquiries.DtoModel', {
	extend : 'Ext.data.Model',
	fields : [ {
		/** 下一线路 */
		name : 'nextLine',
		type : 'string'
	}, {
		/** 运单号 */
		name : 'wayBillNo',
		type : 'string'
	}, {
		/** 运输性质code */
		name : 'transportModelCode',
		type : 'string'
	}, {
		/** 运输性质name */
		name : 'transportModelName',
		type : 'string'
	}, {
		/** 货物名称 */
		name : 'goodName',
		type : 'string'
	}, {
		/** 货物状态 */
		name : 'goodStatus',
		type : 'string'
	}, {
		/** 件数 */
		name : 'goodQty',
		type : 'string'
	}, {
		/** 当前件数 */
		name : 'currentQty',
		type : 'double'
	}, {
		/** 重量(KG) */
		name : 'currentWeight',
		type : 'double'
	}, {
		/** 体积(方) */
		name : 'currentVolume',
		type : 'double'
	}, {
		/** 车次号 */
		name : 'vehicleassembleNo',
		type : 'string'
	}, {
		/** 车牌号 */
		name : 'vehicleNo',
		type : 'string'
	},{
		name : 'billingTime',
		type : 'date',
		convert : dateConvert
	}]
});

//货物(运输性质)状态模型
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
Ext.define('Foss.platform.statisticalInquiries.store.goodStatus',{
	extend: 'Ext.data.Store',
	model: 'Foss.partialline.model.goods',
	data : {
		'items':[		
		    /** 统计货量查询 开单未交接 */
			{id : '0', name : platform.statisticalInquiries.i18n('foss.platform.forecastQuantity.forecastQuantity.billing.gui'),  value: 'NOTRANSFERBILLING'},//开单未交接
			/** 统计货量查询 在途 */
			{id : '1', name : platform.statisticalInquiries.i18n('foss.platform.forecastQuantity.forecastQuantity.intransit.gui'),  value: 'INTRANSIT'},//在途
			/** 统计货量查询 到达未卸车 */
			{id : '2', name : platform.statisticalInquiries.i18n('foss.platform.forecastQuantity.forecastQuantity.unloadingnotreached.gui'),  value: 'UNLOADINGNOTREACHED'},//到达未卸车
			/** 统计货量查询 在库 */
			{id : '3', name : platform.statisticalInquiries.i18n('foss.platform.forecastQuantity.forecastQuantity.inventory.gui'),  value: 'INLIBRARY'},//在库
			/** 统计货量查询 交接未出发 */
			{id : '4', name : platform.statisticalInquiries.i18n('foss.platform.forecastQuantity.forecastQuantity.nodeparturetransfer.gui'),  value: 'NODEPARTURETRANSFER'}//交接未出发
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

//运输性质数据源
Ext.define('Foss.platform.statisticalInquiries.store.transportModel',{
	extend: 'Ext.data.Store',
	model: 'Foss.partialline.model.goods',
	data : {
		'items':[
				//全部
		        {id : '0', name : platform.statisticalInquiries.i18n('Foss.platform.common.all'),  value: 'ALL'},
		     	/** 统计货量查询 运输性质  汽运偏线 */
		       // {id : '1', name : platform.statisticalInquiries.i18n('Foss.platform.common.plf'),  value: 'PLF'},
		    	/** 统计货量查询 运输性质 精准卡航  */
		        {id : '2', name : platform.statisticalInquiries.i18n('Foss.platform.common.flf'),  value: 'FLF'},
		    	/** 统计货量查询 运输性质 精准城运 */
		        {id : '3', name : platform.statisticalInquiries.i18n('Foss.platform.common.fsf'),  value: 'FSF'},
		    	/** 统计货量查询 运输性质 精准汽运(长途) */
		        {id : '4', name : platform.statisticalInquiries.i18n('Foss.platform.common.lrf'),  value: 'LRF'},
		    	/** 统计货量查询 运输性质 精准汽运(短途) */
		        {id : '5', name : platform.statisticalInquiries.i18n('Foss.platform.common.srf'),  value: 'SRF'}，
		        /** 统计货量查询 运输性质 精准包裹 */
		        {id : '6', name : platform.statisticalInquiries.i18n('Foss.platform.common.pcp'),  value: 'PCP'}
		        
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


Ext.define('Foss.platform.statisticalInquiries.store', {
	extend : 'Ext.data.Store',
	model : 'Foss.platform.statisticalInquiries.DtoModel',
	proxy : {
		// 以JSON的方式加载数据
		type : 'ajax',
		actionMethods : 'POST',
		url : platform.realPath('queryStatisticalInquiriesList.action'),
		reader : {
			type : 'json',
			root : 'forecastVO.statisticalInquiriesEntityList',
			totalProperty : 'forecastVO.totalCount',
			successProperty : 'success'
		},
		listeners : {
			exception : function(dataProxy, response, action, options) {
				var result = Ext.decode(response.responseText);
				Ext.ux.Toast.msg('提示', result.message);
			}
		}
	},
	listeners : {
		beforeload : function(store, operation, eOpts) {
			var queryForm = platform.statisticalInquiries.queryForm;
			if (queryForm != null) {
				var queryParams = queryForm.getValues();
				queryParams['forecastVO.statisticalInquiriesDto.currentOrgCode'] = FossUserContext.getCurrentDept().code;
				Ext.apply(operation, {
					params : queryParams
				});	
			}
		},
		load : function( store, records, successful, eOpts){
			var forecastVO = platform.statisticalInquiries.forecastVO = store.proxy.reader.rawData.forecastVO;
			Ext.getCmp('Foss_platform_statisticalInquiries_grid_Toolbar_goodsQtySum_ID').setValue(forecastVO.totalCount);
			Ext.getCmp('Foss_platform_statisticalInquiries_grid_Toolbar_weightSum_ID').setValue(forecastVO.currentWeightSum);
			Ext.getCmp('Foss_platform_statisticalInquiries_grid_Toolbar_volumeSum_ID').setValue(forecastVO.currentVolumeSum);
		}
	}
});

//查询条件
Ext.define('Foss.platform.statisticalInquiries.search', {
	extend : 'Ext.form.Panel',
	title : platform.statisticalInquiries.i18n('Foss.platform.inviteVehicle.InviteVehicleApplyQueryForm.title'), // 查询条件
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
		name : 'forecastVO.statisticalInquiriesDto.currentOrgCode',
		fieldLabel : platform.statisticalInquiries
				.i18n('foss.platform.forecast.currentOrgCode'), // 当前部门
		columnWidth : .25,
		value : FossUserContext.getCurrentDept().name,
		allowBlank:true,
		readOnly:true
	}, {
		name : 'forecastVO.statisticalInquiriesDto.nextLine',
		xtype : 'commonlineselector',
		allowBlank: false,
		orginalOrganizationCode : platform.statisticalInquiries.parentTfrCtrCode,
		valueField : 'destinationOrganizationCode',
		fieldLabel : platform.statisticalInquiries.i18n('Foss.platform.inviteVehicle.InviteVehicleApplyWindow.inviteVehicleApplyInfo.nextLine'), // 下一线路
		columnWidth : .25
	}, {
		name : 'forecastVO.statisticalInquiriesDto.transportModelCode',
		fieldLabel : platform.statisticalInquiries.i18n('foss.platform.reCreateTransportationPath.transportModel'), // 运输性质
		columnWidth : .25,
		xtype: 'combobox',
		mode:'local',
		queryMode: 'local',		
		forceSelection:true,
		editable:false,
		displayField:   'name',
		valueField:     'value',		
		store: Ext.create('Foss.platform.statisticalInquiries.store.transportModel'),
		value:'ALL'
	}, {
		name : 'forecastVO.statisticalInquiriesDto.goodStatus',
		fieldLabel : platform.statisticalInquiries.i18n('foss.platform.forecastQuantity.forecastQuantity.goodStatus'), // 货物状态
		columnWidth : .25,
		xtype: 'combobox',
		mode:'local',
		queryMode: 'local',		
		forceSelection:true,
		editable:false,
		displayField:   'name',
		valueField:     'value',		
		store: Ext.create('Foss.platform.statisticalInquiries.store.goodStatus'),
		value:'NOTRANSFERBILLING'
	}, {
		xtype : 'rangeDateField',
		dateType : 'datetimefield_date97',
		dateRange : 7,
		fieldLabel : platform.statisticalInquiries.i18n('foss.platform.forecastQuantity.forecastQuantity.timeScope.colon'), // 时间范围
		fieldId : 'Foss_invitetruck_InviteVehicleApplyQueryForm_PredictUseTime_ID',
		fromName : 'forecastVO.statisticalInquiriesDto.startTime',
		toName : 'forecastVO.statisticalInquiriesDto.endTime',
		fromValue: Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()-1
				,'00','00','00'), 'Y-m-d H:i:s'),
		toValue: Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()
		,'23','59','59'), 'Y-m-d H:i:s'),
		columnWidth : .5,
		allowBlank : false,
		disallowBlank : true
	}, {
		name : 'forecastVO.statisticalInquiriesDto.wayBillNo',
		fieldLabel : '运单号', // 运单号
		columnWidth : .25
	}, {
		xtype : 'container',
		columnWidth : .25,
		html : '&nbsp;',
		height : 25
	}, {
		border : 1,
		xtype : 'container',
		columnWidth : 1,
		defaultType : 'button',
		layout : 'column',
		items : [{
			text : platform.statisticalInquiries
					.i18n('Foss.platform.button.reset'), // 重置
			columnWidth : .08,
			handler : function() {
				platform.statisticalInquiries.queryForm.getForm()
						.reset();
				
				platform.statisticalInquiries.queryForm.getForm().findField('forecastVO.statisticalInquiriesDto.startTime').setValue(Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()-1
						,'00','00','00'), 'Y-m-d H:i:s'));
				platform.statisticalInquiries.queryForm.getForm().findField('forecastVO.statisticalInquiriesDto.endTime').setValue(Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()
						,'23','59','59'), 'Y-m-d H:i:s'));
			}
		}, {
			xtype : 'container',
			columnWidth : .76,
			html : '&nbsp;'
		}, {
			text : platform.statisticalInquiries
					.i18n('foss.platform.statisticalInquiries.changeAssembleWayWindow.detailExport'), // 导出
			cls : 'yellow_button',
			columnWidth : .08,
			handler : function() {
				var form = platform.statisticalInquiries.queryForm
						.getForm();
				if (!form.isValid()) {
					Ext.Msg
							.alert(
									platform.statisticalInquiries
											.i18n('Foss.platform.validation.alert.title'),
									platform.statisticalInquiries
											.i18n('Foss.platform.validation.tip.search'));
					return false;
				}
				
					var queryParams = form.getValues();
					queryParams['forecastVO.statisticalInquiriesDto.currentOrgCode'] = FossUserContext.getCurrentDept().code;
				
					//获取查询方案里的配载部门
					var arriveDeptList = new Array();
					if(platform.statisticalInquiries.AddAssembleDeptWindow != null){
						var store = platform.statisticalInquiries.addAssembleDeptGrid.store;
					store.each(function(record){
							arriveDeptList.push(record.get('assembleDeptCode'));
						});
					}
					queryParams['forecastVO.statisticalInquiriesDto.arriveDeptList'] = arriveDeptList;
					
				if (!Ext.fly('downloadAttachFileForm')) {
					var frm = document.createElement('form');
					frm.id = 'downloadAttachFileForm';
					frm.style.display = 'none';
					document.body.appendChild(frm);
				}
				
				Ext.Ajax.request({
					url : platform
							.realPath('queryStatisticalInquiriesExcelStream.action'),
					form : Ext.fly('downloadAttachFileForm'),
					method : 'POST',
					params : queryParams,
					isUpload : true,
					exception : function(response) {
						var result = Ext
								.decode(response.responseText);
						top.Ext.MessageBox.alert(platform.statisticalInquiries.i18n('foss.platform.forecastQuantity.forecastQuantity.exportFail'),
								result.message);
					}
				});
			}
		}, {
		text : platform.statisticalInquiries
		.i18n('Foss.platform.button.search'), // 查询
		cls : 'yellow_button',
		columnWidth : .08,
		handler : function() {
			var form = platform.statisticalInquiries.queryForm
			.getForm();
			if (!form.isValid()) {
				Ext.Msg
				.alert(
						platform.statisticalInquiries
						.i18n('Foss.platform.validation.alert.title'),
						platform.statisticalInquiries
						.i18n('Foss.platform.validation.tip.search'));
				return false;
			}
			platform.statisticalInquiries.pagingBar.moveFirst();
		}
	}]
	}]
});

//运单流水Mode
Ext.define('Foss.platform.statisticalInquiries.SerialnoModel',{
	extend:'Ext.data.Model',
	fields: [
		{name: 'wayBillNo' , type: 'string'},
		{name: 'serialno' , type: 'string'}
	]
});

//运单流水store
Ext.define('Foss.platform.statisticalInquiries.serialno.grid.store', {
	extend : 'Ext.data.Store',
	model : 'Foss.platform.statisticalInquiries.SerialnoModel'
});

//运单流水grid
Ext.define('Foss.platform.statisticalInquiries.serialno.grid',{
	extend: 'Ext.grid.Panel',
	title: '运单流水',
	frame: true,
	animCollapse: true,
	autoScroll: true,
	height:300,
	columns: [{
			header: '运单', 
			dataIndex: 'wayBillNo',
			flex : 1
		},{
			header: '流水号', 
			dataIndex: 'serialno',
			flex : 1
		}],
	constructor: function(config){
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.platform.statisticalInquiries.serialno.grid.store');
		me.callParent([cfg]);
	}
});

//点击“运单号列”打开流水明细窗口
javascript: platform.statisticalInquiries.showSerialno = function(vehicleassembleNo, wayBillNo, nextLine){
	var grid = Ext.create('Foss.platform.statisticalInquiries.serialno.grid'),
		window = Ext.create('Ext.window.Window', {
			title: "运单流水列表",
			height:400,
			width:600,
			closable:true,
			closeAction:'hide',
			modal: true,
			items: [
					grid
			]
		});
	var goodStatus = platform.statisticalInquiries.forecastVO.statisticalInquiriesDto.goodStatus
	var params = {
			'forecastVO.statisticalInquiriesDto.vehicleassembleNo' : vehicleassembleNo,
			'forecastVO.statisticalInquiriesDto.wayBillNo' : wayBillNo,
			'forecastVO.statisticalInquiriesDto.nextLine' : nextLine,
			'forecastVO.statisticalInquiriesDto.goodStatus' : goodStatus
	};

	Ext.Ajax.request({
		url : platform.realPath('querySerialnoByVehicleassembleNo.action'),
		params : params,
		success : function(response) {
			var result = Ext.decode(response.responseText),
			store = grid.store;
			store.loadData(result.forecastVO.serialnos);
			window.show();
		}
	});
}

// grid
Ext.define('Foss.platform.statisticalInquiries.grid', {
	extend : 'Ext.grid.Panel',
	frame : true,
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	title : platform.statisticalInquiries.i18n('Foss.platform.inviteVehicle.InviteVehicleApplyQueryGrid.title'), // 查询结果
	columns : [{
		xtype : 'ellipsiscolumn',
		header : platform.statisticalInquiries.i18n('Foss.platform.inviteVehicle.InviteVehicleApplyWindow.inviteVehicleApplyInfo.nextLine'), //下一线路
		dataIndex : 'nextLine',
		flex : 0.8
	},{
		xtype : 'ellipsiscolumn',
		header : platform.statisticalInquiries.i18n('foss.platform.adjustTransportationPath.adjustTransportationPath.waybillNo'), // 运单号
		dataIndex : 'wayBillNo',
		renderer : function(value, cellmeta, record, rowIndex, columnIndex, store){
			var vehicleassembleNo = record.get('vehicleassembleNo');
			var nextLine = record.get('nextLine');
			if(vehicleassembleNo == null) {
				vehicleassembleNo = '';
			}
			if(nextLine == null) {
				nextLine = '';
			}
			return '<a href="javascript:platform.statisticalInquiries.showSerialno('+"'" + vehicleassembleNo + "','" + value + "','" + nextLine +"'" +')">' + value + '</a>';
		},
		flex : 0.8
		
	},{
		xtype : 'ellipsiscolumn',
		header : platform.statisticalInquiries.i18n('foss.platform.forecastQuantity.forecastQuantity.goodStatus'), // 货物状态
		dataIndex : 'goodStatus',
		flex : 0.8
	},{
		xtype : 'ellipsiscolumn',
		header : platform.statisticalInquiries.i18n('Foss.platform.inviteVehicle.InviteVehicleApplyWindow.customerAndGoodsInfo.goodsQty'), // 开单件数
		dataIndex : 'goodQty',
		flex : 0.8
	},{
		xtype : 'ellipsiscolumn',
		header : '当前件数',
		dataIndex : 'currentQty',
		flex : 0.8
	},{
		xtype : 'ellipsiscolumn',
		header : '重量',
		dataIndex : 'currentWeight',
		flex : 0.8
	},{
		xtype : 'ellipsiscolumn',
		header : '体积',
		dataIndex : 'currentVolume',
		flex : 0.8
	},{
		xtype : 'ellipsiscolumn',
		header : '运输性质',
		dataIndex : 'transportModelName',
		flex : 0.8
	},{
		header : '开单时间',
		dataIndex : 'billingTime',
		xtype : 'datecolumn',
		format : 'Y-m-d H:i:s',
		flex : 0.8
	},{
		xtype : 'ellipsiscolumn',
		header : '车次号', 
		dataIndex : 'vehicleassembleNo',
		flex : 0.8
	},{
		xtype : 'ellipsiscolumn',
		header : '车牌号',
		dataIndex : 'vehicleNo',
		flex : 0.8
	}],dockedItems:[{
		   xtype:'toolbar',
		   dock:'bottom',
		   layout:'column',
		   defaults:{
			 xtype:'textfield',
			 value:'0',
			 readOnly:true
		   },
		   items:[{
			   fieldLabel: '总票数(票)',
			   id:'Foss_platform_statisticalInquiries_grid_Toolbar_goodsQtySum_ID',
			   columnWidth:.30,
			   dataIndex: 'waybillNoCount'
		   },{
			   //fieldLabel:'总体积',
			   fieldLabel: platform.statisticalInquiries.i18n('foss.platform.statisticalInquiries.volumeSum'),
			   id:'Foss_platform_statisticalInquiries_grid_Toolbar_volumeSum_ID',
			   columnWidth:.30,
			   dataIndex: 'currentVolumeSum'
		   },{
			   //fieldLabel:'总重量(T)',
			   fieldLabel: platform.statisticalInquiries.i18n('foss.platform.statisticalInquiries.weightSum'),
			   id:'Foss_platform_statisticalInquiries_grid_Toolbar_weightSum_ID',
			   columnWidth:.30,
			   dataIndex: 'currentWeightSum'
		   }]}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext
				.create('Foss.platform.statisticalInquiries.store');
		me.bbar = Ext.create('Deppon.StandardPaging',{
			store:me.store,
			plugins: 'pagesizeplugin'
		});
		platform.statisticalInquiries.pagingBar = me.bbar;
		me.callParent([cfg]);
	},
	viewConfig : {
		enableTextSelection : true
	}
});


Ext.onReady(function() {
	Ext.QuickTips.init();
	var queryForm = Ext
			.create('Foss.platform.statisticalInquiries.search');
	var queryGrid = Ext
			.create('Foss.platform.statisticalInquiries.grid');
	platform.statisticalInquiries.queryGrid = queryGrid;
	platform.statisticalInquiries.queryForm = queryForm;
	Ext.create('Ext.panel.Panel', {
				id : 'T_platform-queryStatisticalInquiriesIndex_content',
				cls : "panelContentNToolbar",
				bodyCls : 'panelContentNToolbar-body',
				layout : 'auto',
				items : [queryForm,
				         queryGrid],
				renderTo : 'T_platform-queryStatisticalInquiriesIndex-body'
			});
});

