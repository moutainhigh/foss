/**
 * 统计货量查询
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
Ext.define('Foss.scheduling.statisticalInquiries.DtoModel', {
	extend : 'Ext.data.Model',
	fields : [ {
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
		/** 体积(方) */
		name : 'volume',
		type : 'string'
	}, {
		/** 重量(KG) */
		name : 'weight',
		type : 'string'
	}, {
		/** 件数 */
		name : 'goodQty',
		type : 'string'
	}, {
		/** 出发部门 code */
		name : 'destOrgCode',
		type : 'string'
	}, {
		/** 出发部门 Name */
		name : 'destOrgName',
		type : 'string'
	}, {
		/** 到达部门 code */
		name : 'arriveOrgCode',
		type : 'string'
	}, {
		/** 到达部门 Name */
		name : 'arriveOrgName',
		type : 'string'
	}, {
		/** 开单时间 */
		name : 'billingTime',
		type : 'string',
		convert : function(value){
			return forecast.util.dateConvert(value);
		}
	}, {
		/** 到达时间 */
		name : 'arriveTime',
		type : 'string',
		convert : function(value){
			return forecast.util.dateConvert(value);
		}
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
Ext.define('Foss.scheduling.statisticalInquiries.store.goodStatus',{
	extend: 'Ext.data.Store',
	model: 'Foss.partialline.model.goods',
	data : {
		'items':[		
		    /** 统计货量查询 开单未交接 */
			{id : '0', name : scheduling.statisticalInquiries.i18n('foss.scheduling.forecastQuantity.forecastQuantity.billing.gui'),  value: 'NOTRANSFERBILLING'},//开单未交接
			/** 统计货量查询 在途 */
			{id : '1', name : scheduling.statisticalInquiries.i18n('foss.scheduling.forecastQuantity.forecastQuantity.intransit.gui'),  value: 'INTRANSIT'},//在途
			/** 统计货量查询 到达未卸车 */
			{id : '2', name : scheduling.statisticalInquiries.i18n('foss.scheduling.forecastQuantity.forecastQuantity.unloadingnotreached.gui'),  value: 'UNLOADINGNOTREACHED'},//到达未卸车
			/** 统计货量查询 在库 */
			{id : '3', name : scheduling.statisticalInquiries.i18n('foss.scheduling.forecastQuantity.forecastQuantity.inventory.gui'),  value: 'INLIBRARY'},//在库
			/** 统计货量查询 交接未出发 */
			{id : '4', name : scheduling.statisticalInquiries.i18n('foss.scheduling.forecastQuantity.forecastQuantity.nodeparturetransfer.gui'),  value: 'NODEPARTURETRANSFER'}//交接未出发
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
Ext.define('Foss.scheduling.statisticalInquiries.store.transportModel',{
	extend: 'Ext.data.Store',
	model: 'Foss.partialline.model.goods',
	data : {
		'items':[
				//全部
		        {id : '0', name : scheduling.statisticalInquiries.i18n('Foss.scheduling.common.all'),  value: 'ALL'},
		     	/** 统计货量查询 运输性质  汽运偏线 */
		       // {id : '1', name : scheduling.statisticalInquiries.i18n('Foss.scheduling.common.plf'),  value: 'PLF'},
		    	/** 统计货量查询 运输性质 精准卡航  */
		        {id : '2', name : scheduling.statisticalInquiries.i18n('Foss.scheduling.common.flf'),  value: 'FLF'},
		    	/** 统计货量查询 运输性质 精准城运 */
		        {id : '3', name : scheduling.statisticalInquiries.i18n('Foss.scheduling.common.fsf'),  value: 'FSF'},
		    	/** 统计货量查询 运输性质 精准汽运(长途) */
		        {id : '4', name : scheduling.statisticalInquiries.i18n('Foss.scheduling.common.lrf'),  value: 'LRF'},
		    	/** 统计货量查询 运输性质 精准汽运(短途) */
		        {id : '5', name : scheduling.statisticalInquiries.i18n('Foss.scheduling.common.srf'),  value: 'SRF'}
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


Ext.define('Foss.scheduling.statisticalInquiries.store', {
	extend : 'Ext.data.Store',
	model : 'Foss.scheduling.statisticalInquiries.DtoModel',
	proxy : {
		// 以JSON的方式加载数据
		type : 'ajax',
		actionMethods : 'POST',
		url : scheduling.realPath('queryStatisticalInquiriesList.action'),
		reader : {
			type : 'json',
			root : 'forecastVO.statisticalInquiriesEntityList',
			totalProperty : 'forecastVO.totalCount',
			successProperty : 'success'
		}
	},
	listeners : {
		beforeload : function(store, operation, eOpts) {
			var queryForm = scheduling.statisticalInquiries.queryForm;
			if (queryForm != null) {
				var queryParams = queryForm.getValues();
				queryParams['forecastVO.statisticalInquiriesDto.currentOrgCode'] = FossUserContext.getCurrentDept().code;
				//获取查询方案里的配载部门
				var arriveDeptList = new Array();
				if(scheduling.statisticalInquiries.AddAssembleDeptWindow != null){
					var store = scheduling.statisticalInquiries.addAssembleDeptGrid.store;
				store.each(function(record){
						arriveDeptList.push(record.get('assembleDeptCode'));
					});
				}
				queryParams['forecastVO.statisticalInquiriesDto.arriveDeptList'] = arriveDeptList;
				
				Ext.apply(operation, {
					params : queryParams
				});	
			}
		},
		load : function( store, records, successful, eOpts){
			var forecastVO = store.proxy.reader.rawData.forecastVO;
			Ext.getCmp('Foss_scheduling_statisticalInquiries_grid_Toolbar_weightSum_ID').setValue(forecastVO.weightSum);
			Ext.getCmp('Foss_scheduling_statisticalInquiries_grid_Toolbar_volumeSum_ID').setValue(forecastVO.volumeSum);
		}
	}
});

//查询条件
Ext.define('Foss.scheduling.statisticalInquiries.search', {
	extend : 'Ext.form.Panel',
	title : scheduling.statisticalInquiries.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyQueryForm.title'), // 查询条件
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
		fieldLabel : scheduling.statisticalInquiries
				.i18n('foss.scheduling.forecast.currentOrgCode'), // 当前部门
		columnWidth : .25,
		value : FossUserContext.getCurrentDept().name,
		allowBlank:true,
		readOnly:true
	}, {
		name : 'forecastVO.statisticalInquiriesDto.arriveOrgCode',
		fieldLabel : scheduling.statisticalInquiries.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.inviteVehicleApplyInfo.arrivedDeptCode'), // 到达部门
		columnWidth : .25,
		xtype : 'dynamicorgcombselector',
		type : 'ORG',
		salesDepartment : 'Y',
		transferCenter : 'Y',
		airDispatch : 'Y',
		doAirDispatch : 'Y',
		allowBlank:false
	}, {
		name : 'forecastVO.statisticalInquiriesDto.transportModelCode',
		fieldLabel : scheduling.statisticalInquiries.i18n('foss.scheduling.reCreateTransportationPath.transportModel'), // 运输性质
		columnWidth : .25,
		xtype: 'combobox',
		mode:'local',
		queryMode: 'local',		
		forceSelection:true,
		editable:false,
		displayField:   'name',
		valueField:     'value',		
		store: Ext.create('Foss.scheduling.statisticalInquiries.store.transportModel'),
		value:'ALL'
	}, {
		name : 'forecastVO.statisticalInquiriesDto.goodStatus',
		fieldLabel : scheduling.statisticalInquiries.i18n('foss.scheduling.forecastQuantity.forecastQuantity.goodStatus'), // 货物状态
		columnWidth : .25,
		xtype: 'combobox',
		mode:'local',
		queryMode: 'local',		
		forceSelection:true,
		editable:false,
		displayField:   'name',
		valueField:     'value',		
		store: Ext.create('Foss.scheduling.statisticalInquiries.store.goodStatus'),
		value:'NOTRANSFERBILLING'
	}, {
		xtype : 'rangeDateField',
		dateType : 'datetimefield_date97',
		fieldLabel : scheduling.statisticalInquiries.i18n('foss.scheduling.forecastQuantity.forecastQuantity.timeScope.colon'), // 时间范围
		fieldId : 'Foss_invitetruck_InviteVehicleApplyQueryForm_PredictUseTime_ID',
		fromName : 'forecastVO.statisticalInquiriesDto.startTime',
		toName : 'forecastVO.statisticalInquiriesDto.endTime',
		fromValue: Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()-1
				,'00','00','00'), 'Y-m-d H:i:s'),
		toValue: Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()
		,'23','59','59'), 'Y-m-d H:i:s'),
		columnWidth : .5
	}, {
		xtype : 'container',
		columnWidth : .5,
		html : '&nbsp;',
		height : 25
	}, {
		columnWidth : .15,
		xtype : 'button',
		text : scheduling.statisticalInquiries.i18n('foss.scheduling.statisticalInquiries.queryResultTab.changeQueryWayButton')/*'更改查询方案'*/,
		handler : function(){
			if(scheduling.statisticalInquiries.AddAssembleDeptWindow == null){
				scheduling.statisticalInquiries.AddAssembleDeptWindow = Ext.create('Foss.scheduling.statisticalInquiries.AddAssembleDeptWindow');
			}
			scheduling.statisticalInquiries.AddAssembleDeptWindow.show();
		}
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
			text : scheduling.statisticalInquiries
					.i18n('Foss.scheduling.button.reset'), // 重置
			columnWidth : .08,
			handler : function() {
				scheduling.statisticalInquiries.queryForm.getForm()
						.reset();
				
				scheduling.statisticalInquiries.queryForm.getForm().findField('forecastVO.statisticalInquiriesDto.startTime').setValue(Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()-1
						,'00','00','00'), 'Y-m-d H:i:s'));
				scheduling.statisticalInquiries.queryForm.getForm().findField('forecastVO.statisticalInquiriesDto.endTime').setValue(Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()
						,'23','59','59'), 'Y-m-d H:i:s'));
			}
		}, {
			xtype : 'container',
			columnWidth : .76,
			html : '&nbsp;'
		}, {
			text : scheduling.statisticalInquiries
					.i18n('foss.scheduling.statisticalInquiries.changeAssembleWayWindow.detailExport'), // 导出
			cls : 'yellow_button',
			columnWidth : .08,
			handler : function() {
				var form = scheduling.statisticalInquiries.queryForm
						.getForm();
				if (!form.isValid()) {
					Ext.Msg
							.alert(
									scheduling.statisticalInquiries
											.i18n('Foss.scheduling.validation.alert.title'),
									scheduling.statisticalInquiries
											.i18n('Foss.scheduling.validation.tip.search'));
					return false;
				}
				
					var queryParams = form.getValues();
					queryParams['forecastVO.statisticalInquiriesDto.currentOrgCode'] = FossUserContext.getCurrentDept().code;
				
					//获取查询方案里的配载部门
					var arriveDeptList = new Array();
					if(scheduling.statisticalInquiries.AddAssembleDeptWindow != null){
						var store = scheduling.statisticalInquiries.addAssembleDeptGrid.store;
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
					url : scheduling
							.realPath('queryStatisticalInquiriesExcelStream.action'),
					form : Ext.fly('downloadAttachFileForm'),
					method : 'POST',
					params : queryParams,
					isUpload : true,
					exception : function(response) {
						var result = Ext
								.decode(response.responseText);
						top.Ext.MessageBox.alert(scheduling.statisticalInquiries.i18n('foss.scheduling.forecastQuantity.forecastQuantity.exportFail'),
								result.message);
					}
				});
			}
		}, {
		text : scheduling.statisticalInquiries
		.i18n('Foss.scheduling.button.search'), // 查询
		cls : 'yellow_button',
		columnWidth : .08,
		handler : function() {
			var form = scheduling.statisticalInquiries.queryForm
			.getForm();
			if (!form.isValid()) {
				Ext.Msg
				.alert(
						scheduling.statisticalInquiries
						.i18n('Foss.scheduling.validation.alert.title'),
						scheduling.statisticalInquiries
						.i18n('Foss.scheduling.validation.tip.search'));
				return false;
			}
			scheduling.statisticalInquiries.pagingBar.moveFirst();
		}
	}]
	}]
});



// grid
Ext.define('Foss.scheduling.statisticalInquiries.grid', {
	extend : 'Ext.grid.Panel',
	frame : true,
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	title : scheduling.statisticalInquiries.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyQueryGrid.title'), // 查询结果
	columns : [{
		xtype : 'ellipsiscolumn',
		header : scheduling.statisticalInquiries.i18n('foss.scheduling.adjustTransportationPath.adjustTransportationPath.waybillNo'), // 运单号
		dataIndex : 'wayBillNo',
		flex : 0.8
	},{
		xtype : 'ellipsiscolumn',
		header : scheduling.statisticalInquiries.i18n('foss.scheduling.reCreateTransportationPath.transportModel'), // 运输性质name
		dataIndex : 'transportModelName',
		flex : 0.8
	},{
		xtype : 'ellipsiscolumn',
		header : scheduling.statisticalInquiries.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.customerAndGoodsInfo.goodsName'), // 货物名称
		dataIndex : 'goodName',
		flex : 0.8
	},{
		xtype : 'ellipsiscolumn',
		header : scheduling.statisticalInquiries.i18n('foss.scheduling.forecastQuantity.forecastQuantity.goodStatus'), // 货物状态
		dataIndex : 'goodStatus',
		flex : 0.8
	},{
		xtype : 'ellipsiscolumn',
		header : scheduling.statisticalInquiries.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.customerAndGoodsInfo.volume'), // 体积(方)
		dataIndex : 'volume',
		flex : 0.8
	},{
		xtype : 'ellipsiscolumn',
		header : scheduling.statisticalInquiries.i18n('foss.scheduling.adjustTransportationPath.joinCar.areaWeightTotal.kg'), // 货物重量(KG)
		dataIndex : 'weight',
		flex : 0.8
	},{
		xtype : 'ellipsiscolumn',
		header : scheduling.statisticalInquiries.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.customerAndGoodsInfo.goodsQty'), // 件数
		dataIndex : 'goodQty',
		flex : 0.8
	},{
		xtype : 'ellipsiscolumn',
		header : scheduling.statisticalInquiries.i18n('foss.scheduling.reCreateTransportationPath.origOrgName'), // 出发部门 Name 
		dataIndex : 'destOrgName',
		flex : 0.8
	},{
		xtype : 'ellipsiscolumn',
		header : scheduling.statisticalInquiries.i18n('foss.scheduling.reCreateTransportationPath.objectiveOrgName'), // 到达部门 Name
		dataIndex : 'arriveOrgName',
		flex : 0.8
	},{
		xtype : 'ellipsiscolumn',
		header : scheduling.statisticalInquiries.i18n('foss.scheduling.reCreateTransportationPath.billingTime'), // 开单时间
		dataIndex : 'billingTime',
		flex : 0.8
	},{
		xtype : 'ellipsiscolumn',
		header : scheduling.statisticalInquiries.i18n('foss.scheduling.adjustTransportationPath.adjustPath.arriveTime'), // 到达时间
		dataIndex : 'arriveTime',
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
			   //fieldLabel:'总体积',
			   fieldLabel: scheduling.statisticalInquiries.i18n('foss.scheduling.statisticalInquiries.weightSum'),
			   id:'Foss_scheduling_statisticalInquiries_grid_Toolbar_weightSum_ID',
			   columnWidth:.30,
			   dataIndex: 'weightSum'
		   },{
			   //fieldLabel:'总重量(T)',
			   fieldLabel: scheduling.statisticalInquiries.i18n('foss.scheduling.statisticalInquiries.volumeSum'),
			   id:'Foss_scheduling_statisticalInquiries_grid_Toolbar_volumeSum_ID',
			   columnWidth:.30,
			   dataIndex: 'volumeSum'
		   }]}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext
				.create('Foss.scheduling.statisticalInquiries.store');
		me.bbar = Ext.create('Deppon.StandardPaging', {
					store : me.store
				});
		scheduling.statisticalInquiries.pagingBar = me.bbar;
		me.callParent([cfg]);
	},
	viewConfig : {
		enableTextSelection : true
	}
});

/**
 * 更改配载查询方案
 */
//增加配载部门Model
Ext.define('Foss.scheduling.statisticalInquiries.AddAssembleDeptModel', {
	extend : 'Ext.data.Model',
	// 定义字段
	fields : [{
		name : 'assembleDeptName',
		type : 'string'
	},{
		name : 'assembleDeptCode',
		type : 'string'
	}]
});

//定义增加配载部门store
Ext.define('Foss.scheduling.statisticalInquiries.AddAssembleDeptStore', {
	extend : 'Ext.data.Store',
	// 绑定一个模型
	model : 'Foss.scheduling.statisticalInquiries.AddAssembleDeptModel',
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
});

//定义增加配载部门grid
Ext.define('Foss.scheduling.statisticalInquiries.AddAssembleDeptGrid', {
	extend : 'Ext.grid.Panel',
	columnLines: true,
	title : scheduling.statisticalInquiries.i18n('foss.scheduling.statisticalInquiries.changeAssembleWayWindow.gridTitle')/*'配载部门列表'*/,
	frame: true,
	height : 270,
	autoScroll : true,
	store : Ext.create('Foss.scheduling.statisticalInquiries.AddAssembleDeptStore'),
	columns : [ {
		xtype : 'actioncolumn',
		width : 80,
		text : scheduling.statisticalInquiries.i18n('foss.shortdepartureplan.grid.plansearchresult.actioncolumn.lable')/*'操作'*/,
		align : 'center',
		items : [ {
			tooltip : scheduling.statisticalInquiries.i18n('foss.scheduling.statisticalInquiries.waybillGrid.deleteButtonColumn')/*'删除'*/,
			iconCls : 'deppon_icons_remove',
			handler : function(grid, rowIndex, colIndex) {
				grid.store.removeAt(rowIndex);
			}
		} ]
	}, {
		dataIndex : 'assembleDeptName',
		align : 'center',
		flex : 1,
		xtype : 'ellipsiscolumn',
		text : scheduling.statisticalInquiries.i18n('foss.scheduling.statisticalInquiries.changeAssembleWayWindow.assembleDeptColumn')/*'配载部门'*/
	} ]
});

//定义增加配载部门form
Ext.define('Foss.scheduling.statisticalInquiries.AddAssembleDeptForm', {
	extend : 'Ext.form.Panel',
	title : scheduling.statisticalInquiries.i18n('foss.scheduling.statisticalInquiries.changeAssembleWayWindow.addAssembleDept')/*'增加配载部门'*/,
	frame : true,
	collapsible : true,
	animCollapse : true,
	defaults : {
		margin : '5 10 5 10',
		labelWidth : 85,
		xtype : 'textfield'
	},
	layout : 'column',
	items : [{
		fieldLabel : scheduling.statisticalInquiries.i18n('foss.scheduling.statisticalInquiries.changeAssembleWayWindow.assembleDeptColumn')/*'配载部门'*/,
		name : 'assembleDeptCode',
		xtype : 'dynamicorgcombselector',
		type : 'ORG',
		transferCenter : 'Y',
		columnWidth : 3/4,
		allowBlank : false
	},{
		xtype : 'button',
		text : scheduling.statisticalInquiries.i18n('foss.scheduling.adjustTransportationPath.add')/*'增加'*/,
		cls : 'yellow_button',
		columnWidth : 1/4,
		handler : function(){
			if(this.up('form').getForm().isValid()){
				var cmp = this.up('form').getForm().findField('assembleDeptCode'),
				store = cmp.store,
				deptCode = cmp.getValue(),
				deptRecord = store.findRecord('code',deptCode,0,false,true,true),
				deptName = deptRecord.get('name');

				//如果该部门已经被添加，则提示
				var store = scheduling.statisticalInquiries.addAssembleDeptGrid.store;
				
				if(store.getCount()>=scheduling.statisticalInquiries.stowagePlanDefault){
					Ext.ux.Toast.msg(scheduling.statisticalInquiries.i18n('foss.scheduling.ShortSchedule.tip.title')/*'提示'*/, '添加的部门数不能大于'+scheduling.statisticalInquiries.stowagePlanDefault+'个！', 'error', 1000);
				return ;
				}
				
				if(store.findRecord('assembleDeptCode',deptCode,0,false,true,true) != null){
					Ext.ux.Toast.msg(scheduling.statisticalInquiries.i18n('foss.scheduling.ShortSchedule.tip.title')/*'提示'*/, '该部门已经被添加！', 'error', 1000);
				}else{
					var deptInfo = {
						'assembleDeptCode' : deptCode,
						'assembleDeptName' : deptName
					};
					var deptInfoRecord = Ext.ModelManager.create(deptInfo,'Foss.scheduling.statisticalInquiries.AddAssembleDeptModel');
					store.insert(store.getCount(),deptInfoRecord);
					cmp.setValue(null);
					cmp.focus();
				}	
			}
		}
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
});


//定义增加配载部门窗口
Ext.define('Foss.scheduling.statisticalInquiries.AddAssembleDeptWindow', {
	extend : 'Ext.window.Window',
	closeAction : 'hide',
	modal : true,
	width : 400,
	height : 500,
	title : scheduling.statisticalInquiries.i18n('foss.scheduling.statisticalInquiries.queryResultTab.changeQueryWayButton')/*'更改查询方案'*/,
	addAssembleDeptForm : null,
	getAddAssembleDeptForm : function(){
		if(this.addAssembleDeptForm==null){
			this.addAssembleDeptForm = Ext.create('Foss.scheduling.statisticalInquiries.AddAssembleDeptForm');
			scheduling.statisticalInquiries.addAssembleDeptForm = this.addAssembleDeptForm;
		}
		return this.addAssembleDeptForm;
	},
	addAssembleDeptGrid : null,
	getAddAssembleDeptGrid : function(){
		if(this.addAssembleDeptGrid==null){
			this.addAssembleDeptGrid = Ext.create('Foss.scheduling.statisticalInquiries.AddAssembleDeptGrid');
			scheduling.statisticalInquiries.addAssembleDeptGrid = this.addAssembleDeptGrid;
		}
		return this.addAssembleDeptGrid;
	},
	constructor : function(config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.items = [me.getAddAssembleDeptForm(),me.getAddAssembleDeptGrid()];
		me.callParent([cfg]);
	},
	buttons : [{
		text : scheduling.statisticalInquiries.i18n('foss.scheduling.forecastQuantity.check')/*'确定'*/,
		cls : 'yellow_button',
		handler : function(){
			this.ownerCt.ownerCt.close();
		}
	}]
});




Ext.onReady(function() {
	Ext.QuickTips.init();
	var queryForm = Ext
			.create('Foss.scheduling.statisticalInquiries.search');
	var queryGrid = Ext
			.create('Foss.scheduling.statisticalInquiries.grid');
	scheduling.statisticalInquiries.queryGrid = queryGrid;
	scheduling.statisticalInquiries.queryForm = queryForm;
	Ext.create('Ext.panel.Panel', {
				id : 'T_scheduling-queryStatisticalInquiriesIndex_content',
				cls : "panelContentNToolbar",
				bodyCls : 'panelContentNToolbar-body',
				layout : 'auto',
				items : [queryForm,
				         queryGrid],
				renderTo : 'T_scheduling-queryStatisticalInquiriesIndex-body'
			});
});

