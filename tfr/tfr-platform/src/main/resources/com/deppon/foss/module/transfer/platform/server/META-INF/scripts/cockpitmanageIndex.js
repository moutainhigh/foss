

// 驾驶舱管理查询条件form
Ext.define('Foss.platform.cockpitmanageIndex.QueryForm', {
	extend : 'Ext.form.Panel',
	title : '查询条件',
	frame : true,
	collapsible : true,
	animCollapse : true,
	defaults : {
		margin : '5 10 5 10',
		labelWidth : 85,
		columnWidth : 1 / 4,
		xtype : 'textfield'
	},
	layout : 'column',
	items : [{
		fieldLabel : '外场',
		name : 'tfrCtrName',
		value:deptName,
		columnWidth:0.4,
		readOnly:true
	}, {
		fieldLabel : '查询日期',
		name : 'currentTime',
		value:Ext.Date.format(new Date(),'Y-m-d H:i:s'),
		columnWidth:0.5,
		readOnly:true
	}, {
		fieldLabel : '当前部门编码',
		name : 'tfrCtrCode',
		value:deptCode,
		columnWidth:0.1,
		hidden:true,
		readOnly:true
	},{
		border : false,
		xtype : 'container',
		columnWidth : 1,
		layout : 'column',
		defaults : {
			margin : '5 0 5 0'
		},
		items : [ {
			xtype : 'container',
			columnWidth : .08,
		}, {
			border : false,
			columnWidth : .84,
			html : '&nbsp;'
		}, {
			columnWidth : .08,
			xtype : 'button',
			cls : 'yellow_button',
			text : '查询',
			handler : function(){
				var form = this.up('form').getForm();
				if(form.isValid()){
					pageBar.moveFirst();
					this.up('form').getForm().findField('currentTime').setValue(Ext.Date.format(new Date(),'Y-m-d H:i:s'));
				}
			}
		} ]
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
});

//定义查询包结果列表Model
Ext.define('Foss.platform.cockpitmanageIndex.QueryCockpitModel', {
	extend : 'Ext.data.Model',
	//定义字段
	fields : [{
		name : 'dutyCount',//上班人数
		type : 'string'
	}, {
		name : 'absenteeCount',//异常人数/
		type : 'string'
	}, {
		name : 'zxlhCount',//在线理货员人数/
		type : 'string'
	}, {
		name : 'zxdccCount', //在线电叉车司机人数/
		type : 'string'
	}, {
		name : 'stockWeight', //当前货台库存货量/
		type : 'string'
	}, {
		name : 'waitUnloadWeight', //当前待卸货量/
		type : 'string'
	}, {
		name : 'lngDisOnTheWayNums',  //当前长途在途车辆数/
		type : 'string'
	}, {
		name : 'shtDisOnTheWayNums',//当前短途在途车辆数/
		type : 'string'
	}, {
		name : 'lngDisArrivedNums',//到达未卸车长途车辆数/
		type : 'string'
	},{
		name : 'shtDisArrivedNums',//到达未卸车短途车辆数/
		type : 'string'
	}, {
		name : 'loadUnloadProgressAbnormalNums',//装卸车进度异常数/
		type : 'string'
	},{
		name : 'platformRate',//月台使用率/
		type : 'string'
	},{
		name : 'stockVolume',//零担派送当前库存方数/
		type : 'string'
	}, {
		name : 'returnRate',//派送退单率/
		type : 'string'
	}, {
		name : 'saturationDay',//仓库饱和率/
		type : 'string'
	}]
});

//定义包列表store
Ext.define('Foss.platform.cockpitmanageIndex.QueryCockpitStore', {
	extend : 'Ext.data.Store',
	pageSize : 20,
	// 绑定一个模型
	model : 'Foss.platform.cockpitmanageIndex.QueryCockpitModel',
	// 定义一个代理对象
	proxy : {
		type : 'ajax',
		actionMethods:'POST',
		// 请求的url
		url : platform.realPath('queryCockpitList.action'),
		// 定义一个读取器
		reader : {
			// 以JSON的方式读取
			type : 'json',
			// 定义读取JSON数据的根对象
			root : 'cockpitVo.cockpits',
			successProperty: 'success',
			totalProperty : 'totalCount'
		}
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	},
	listeners : {
		'beforeload' : function(store, operation, eOpts){
			var queryParams = platform.cockpitManageIndex.QueryForm.getForm().getValues();
			Ext.apply(operation, {
				params : {
					'cockpitVo.tfrCtrCode' : queryParams.tfrCtrCode,
					'cockpitVo.tfrCtrName' : queryParams.tfrCtrName,
					'cockpitVo.currentTime' : queryParams.currentTime
				}
			});	
		}
	}
});


var pageBar='';
//定义包查询结果列表
Ext.define('Foss.platform.cockpitmanageIndex.QueryCockpitGrid', {
	extend : 'Ext.grid.Panel',
	frame : true,
	columnLines: true,
	title : '转运场  管理驾驶舱',
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	emptyText : '查询结果为空',
	autoScroll : true,
	collapsible : true,
	animCollapse : true,
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config)
		me.store = Ext.create('Foss.platform.cockpitmanageIndex.QueryCockpitStore');
		me.bbar = Ext.create('Deppon.StandardPaging',{
			store : me.store,
			pageSize : 20,
			maximumSize : 50,
			plugins : Ext.create('Deppon.ux.PageSizePlugin', {
				sizeList : [['20', 20], ['30', 30], ['40', 40], ['50', 50]]
			})
		});
		pageBar = me.bbar;////////////////
		me.callParent([cfg]);
	},
	columns : [{
			dataIndex : 'dutyCount',
			align : 'center',
			width : 80,
			//xtype : 'ellipsiscolumn',
			text : '上班人数',
			renderer:function(value){
				if(Ext.isEmpty(value,false)){
					return "0";
				}else{
					return value;
				}
			}
	}, {
		dataIndex : 'absenteeCount',
		align : 'center',
		width : 80,
		text : '异常人数',
		renderer:function(value){
			if(Ext.isEmpty(value,false)){
				return "0";
			}else{
				return value;
			}
		}
	},{
		dataIndex : 'zxlhCount', //
		align : 'center',
		width : 110,
		text : '在线理货员人数',
		renderer:function(value){
			if(Ext.isEmpty(value,false)){
				return "0";
			}else{
				return value;
			}
		}
	}, {
		dataIndex : 'zxdccCount',
		align : 'center',
		width : 150,
		text : '在线电叉车司机人数',//
		renderer:function(value){
			if(Ext.isEmpty(value,false)){
				return "0";
			}else{
				return value;
			}
		}
	}, {
		dataIndex : 'stockWeight',
		align : 'center',
		width : 135,
		text : '当前货台库存量(吨)',//需精确到小数点
		renderer:function(value){
				if(Ext.isEmpty(value,false)){
					return "0";
				}else{
					return value;
				}
			}
	}, {
		dataIndex : 'waitUnloadWeight',
		align : 'center',
		width : 130,
		text : '当前待卸货量(吨)',//需精确到小数点
		renderer:function(value){
			if(Ext.isEmpty(value,false)){
				return "0";
			}else{
				return value;
			}
		}
	}, {
		dataIndex : 'lngDisOnTheWayNums',
		align : 'center',
		width : 155,
		text : '当前长途在途车辆数(台)',//
		renderer:function(value){
			if(Ext.isEmpty(value,false)){
				return "0";
			}else{
				return value;
			}
		}
	}, {
		dataIndex : 'shtDisOnTheWayNums',
		align : 'center',
		width : 155,
		text : '当前短途在途车辆数(台)',//
		renderer:function(value){
			if(Ext.isEmpty(value,false)){
				return "0";
			}else{
				return value;
			}
		}
	}, {
		dataIndex : 'lngDisArrivedNums',
		align : 'center',
		width : 165,
		text : '到达未卸车长途车辆数(台)',//
		renderer:function(value){
			if(Ext.isEmpty(value,false)){
				return "0";
			}else{
				return value;
			}
		}
	},{
		dataIndex : 'shtDisArrivedNums',
		align : 'center',
		width : 165,
		text : '到达未卸车短途车辆数(台)',//
		renderer:function(value){
			if(Ext.isEmpty(value,false)){
				return "0";
			}else{
				return value;
			}
		}
	},{
		dataIndex : 'loadUnloadProgressAbnormalNums',
		align : 'center',
		width : 135,
		text : '装卸车进度异常数(台)',//
		renderer:function(value){
			if(Ext.isEmpty(value,false)){
				return "0";
			}else{
				return value;
			}
		}
	},{
		dataIndex : 'platformRate',
		align : 'center',
		width : 95,
		text : '月台使用率',//需精确到小数点
		renderer:function(value){
			if(Ext.isEmpty(value,false)){
				return "0%";
			}else{
				return value;
			}
		}
	},{
		dataIndex : 'stockVolume',
		align : 'center',
		width : 155,
		text : '零担派送当前库存方数',//需精确到小数点
		renderer:function(value){
			if(Ext.isEmpty(value,false)){
				return "0";
			}else{
				return value;
			}
		}
	},{
		dataIndex : 'returnRate',
		align : 'center',
		width : 95,
		text : '派送退单率',//需精确到小数点
		renderer:function(value){
			if(Ext.isEmpty(value,false)){
				return "0%";
			}else{
				return value+"%";
			}
		}
	},{
		dataIndex : 'saturationDay',
		align : 'center',
		width : 95,
		text : '仓库饱和度',//需精确到小数点
		renderer:function(value){
			if(Ext.isEmpty(value,false)){
				return "0%";
			}else{
				return value+"%";
			}
		}
	}]
});
//定义查询结果列表和查询条件
//var queryCockpitGrid = Ext.create('Foss.platform.cockpitmanageIndex.QueryCockpitGrid');
//var queryForm = Ext.create('Foss.platform.cockpitmanageIndex.QueryForm');
platform.cockpitManageIndex.QueryForm=Ext.create('Foss.platform.cockpitmanageIndex.QueryForm');
platform.cockpitManageIndex.QueryCockpitGrid=Ext.create('Foss.platform.cockpitmanageIndex.QueryCockpitGrid');
Ext.onReady(function() {
	var user=FossUserContext.getCurrentDept()
	var orgDeptCode=user.code;
/*	Ext.Ajax.request({
		url:platform.realPath('queryTopDept.action'),
		params:{'cockpitVo.tfrCtrCode':orgDeptCode},
		success:function(response){
			var result=Ext.decode(response.responseText);
			alert(result.cockpitVo.tfrCtrName);
			platform.cockpitManageIndex.QueryForm.getForm().findField('tfrCtrName').setValue(result.cockpitVo.tfrCtrName);
			platform.cockpitManageIndex.QueryForm.getForm().findField('tfrCtrCode').setValue(result.cockpitVo.tfrCtrCode);
		}
	});*/
	Ext.create('Ext.panel.Panel', {
		id : 'T_platform-cockpitManageIndex_content',
		cls : "panelContentNToolbar",
		bodyCls : 'panelContent-body',
		layout : 'auto',
		items : [platform.cockpitManageIndex.QueryForm,platform.cockpitManageIndex.QueryCockpitGrid],
		renderTo : 'T_platform-cockpitManageIndex-body'
	});
});