//package Foss.longScheduleDesign
//发车计划明细模型
Ext.define('Foss.longScheduleDesign.model.PlanDetail', {
	extend: 'Ext.data.Model',
	fields: [
		{name: 'id',type:'string'},//ID
		{name: 'truckDepartPlanId',type:'string'},//发车计划ID
		{name: 'planType',type:'string'},//计划类型
		{name: 'longCarGroup',type:'string'},//长途车队编码
		{name: 'carGroupName',type:'string'},//长途车队编码
		{name: 'longCarGroupName',type:'string'},//长途车队名		
		{name: 'vehicleNo',type:'string'},//车牌号
		{name: 'lineNo',type:'string'},//线路虚拟code
		{name: 'departDate',type:'date',
			convert: function(value) {
				if (value != null) {
					var date = new Date(value);
					return Ext.Date.format(date,'Y-m-d H:i:s');
				} else {
					return null;
				}
			}},//发车日期
		{name: 'planDepartTime',type:'date',
			convert: function(value) {
				if (value != null) {
					var date = new Date(value);
					return Ext.Date.format(date,'Y-m-d H:i:s');
				} else {
					return null;
				}
			}},//计划发车时间
		{name: 'frequencyNo',type:'string'},//班次号		
		{name: 'origOrgCode',type:'string'},//出发部门code
		{name: 'origOrgName',type:'string'},//出发部门
		{name: 'destOrgCode',type:'string'},//到达部门code
		{name: 'destOrgName',type:'string'},//到达部门
		{name: 'platformDistributeId',type:'string'},//月台ID
		{name: 'platformNo',type:'string'},//月台号编码
		{name: 'platformNoCode',type:'string'},//月台号
		{name: 'isOnScheduling',type:'string'},//是否正班车
		{name: 'frequencyType',type:'string'},//班次类型
		{name: 'truckModel',type:'string'},//车型编码
		{name: 'truckModelValue',type:'string'},//车型
		{name: 'truckType',type:'string'},//车辆归属类型 
		{name: 'truckArriveTime',type:'date'},//车辆报道时间
		{name: 'containerNo',type:'string'},//货柜号
		{name: 'trailerVehicleNo',type:'string'},//挂牌号
		{name: 'maxLoadWeight',type:'float'},//最大载重
		{name: 'actualMaxLoadWeight',type:'float'},//实际最大载重
		{name: 'truckVolume',type:'float'},//车容积
		{name: 'planLoadWeight',type:'float'},//预计装载重量
		{name: 'planLoadVolume',type:'float'},//预计装载体积 
		{name: 'truckInfoNotes',type:'string'},//车辆信息备注
		{name: 'driverCode1',type:'string'},//司机code1
		{name: 'driverName1',type:'string'},//司机姓名1
		{name: 'driverPhone1',type:'string'},//联系方式1
		{name: 'driverCode2',type:'string'},//司机code2
		{name: 'driverName2',type:'string'},//司机姓名2
		{name: 'driverPhone2',type:'string'},//联系方式2
		{name: 'planStatus',type:'string'},//状态
		{name: 'createUserCode', type: 'string'},//创建人code
		{name: 'createUserName', type: 'string'},//创建人姓名
		{name: 'createOrgCode', type: 'string'},//创建人组织code
		{name: 'lineName', type: 'string'},//线路名称
		{name: 'lineNoExpress', type: 'string'},//是否快递线路
		{name: 'planArriveTime', type: 'date'},//计划到达时间
		{name: 'carOwnerName', type: 'string'},//车辆所属车队
		{name: 'initFlag', type: 'string'},//导入标记
		{name: 'hasLeft', type: 'string'},//是否已出发
		{name: 'platformTimeStart', type: 'date',
			convert: function(value) {
				if (value != null) {
					var date = new Date(value);
					return Ext.Date.format(date,'Y-m-d H:i:s');
				} else {
					return null;
				}
			}},//月台计划使用开始时间
		{name: 'platformTimeEnd', type: 'date',
			convert: function(value) {
				if (value != null) {
					var date = new Date(value);
					return Ext.Date.format(date,'Y-m-d H:i:s');
				} else {
					return null;
				}
			}}//月台计划使用结束时间
	]
});


//货量预测模型
Ext.define('Foss.longScheduleDesign.model.ForecastForPlan', {
	extend: 'Ext.data.Model',
	fields: [
	         {name: 'origOrgName',type:'string'},//	   出发部门名称      
        	 {name: 'destOrgName', type: 'string'},//到达部门名称
        	 {name: 'origOrgCode', type: 'string'},//出发部门编码
        	 {name: 'destOrgCode', type: 'string'},//到达部门编码
        	 {name: 'departDate', type: 'date',//发车日期
     			convert: function(value) {
    				if (value != null) {
    					var date = new Date(value);
    					return Ext.Date.format(date,'Y-m-d');
    				} else {
    					return null;
    				}
    			}},
        	 {name: 'truckInfo', type: 'string'},//卡货重量/体积/票数
        	 {name: 'intercityInfo', type: 'string'},//城货重量/体积/票数
        	 {name: 'noneTransferOfBilling', type: 'string'},//开单未交接重量/体积/票数
        	 {name: 'lastGoodsInfo', type: 'string'},//余货重量/体积/票数
        	 {name: 'exceptArrivalInfo', type: 'string'},//预计到达重量/体积/票数
        	 {name: 'totalInfo', type: 'string'},//总重量/体积/票数
        	 {name: 'mergerInfo', type: 'string'},//(小计)合发重量/体积/票数
        	 {name: 'allTotalGoodsInfo', type: 'string'},//(汇总)重量/体积/票数
        	 {name: 'deviationVolume', type: 'float'},//合发体积
        	 {name: 'volumeTotal', type: 'float'},//总体积
        	 {name: 'weightTotal', type: 'float'},//总重量
        	 {name: 'forecastTime', type: 'date',//货量预测时间
     			convert: function(value) {
    				if (value != null) {
    					var date = new Date(value);
    					return Ext.Date.format(date,'Y-m-d H:i:s');
    				} else {
    					return null;
    				}
    			}}
	        ]
});

//线路模型
Ext.define('Foss.longScheduleDesign.model.LineNo', {
	extend: 'Ext.data.Model',
	fields: [
	         {name: 'lineNo',type:'string'},//线路虚拟code	         
        	 {name: 'lineName', type: 'string'},//线路名称
	         {name: 'lineNoExpress', type: 'string'}//是否快递线路
	        ]
});

//线路模型
Ext.define('Foss.longScheduleDesign.model.CarGroup', {
	extend: 'Ext.data.Model',
	fields: [
	         {name: 'code',type:'string'},//长途车队CODE
	         {name: 'name',type:'string'}//长途车队名		
	         ]
});

//发车计划日志
Ext.define('Foss.longScheduleDesign.model.MergeLog', {
	extend: 'Ext.data.Model',
	idgen: 'uuid',
	idProperty : 'uuid',
	fields: [
		{name: 'id',type:'string'},//ID
		{name: 'uuid',type:'string'},//UUID
		{name: 'operationDate',type:'date',
			convert: function(value) {
				if (value != null) {
					var date = new Date(value);
					return Ext.Date.format(date,'Y-m-d H:i:s');
				} else {
					return null;
				}
			}},//时间
		{name: 'operatorName',type:'string'},//操作人
		{name: 'operationDesc', type: 'string'}//操作描述
	]
});

//公司车辆模型
Ext.define('Foss.longScheduleDesign.model.InnerCar', {
	extend: 'Ext.data.Model',
	idgen: 'uuid',
	idProperty : 'uuid',
	fields: [
		{name: 'id',type:'string'},//ID
		{name: 'uuid',type:'string'},//UUID
		{name: 'carOwnerName',type:'string'},//所属车队
		{name: 'carOwnerCode',type:'string'},//所属车队CODE
		{name: 'carOwnerGroupName',type:'string'},//所属车队小组
		{name: 'vehicleNo',type:'string'},//车牌号
		{name: 'carStatus', type: 'string'},//车辆状态
		{name: 'carStatusDesc', type: 'string'},//车辆状态描述
		{name: 'truckModel', type: 'string'},//车型
		{name: 'truckModelValue', type: 'string'},//车型编码
		{name: 'maxLoadWeight', type: 'string'},//车辆载重(吨)
		{name: 'truckVolume', type: 'string'},//车辆净空(方)
		{name: 'driverOrgName', type: 'string'},//车队小组
		{name: 'driverOrgCode', type: 'string'},//车队小组code
		{name: 'driverName', type: 'string'},//司机
		{name: 'driverCode', type: 'string'}//司机code
	]
});

//发车计划明细数据源
Ext.define('Foss.longScheduleDesign.store.PlanDetail',{
	extend: 'Ext.data.Store',
	//绑定一个模型
	model: 'Foss.longScheduleDesign.model.PlanDetail',
	pageSize:15,
	proxy: {
		type: 'ajax',
		url:scheduling.realPath('queryTruckDepartPlanDetail.action'),
		actionMethods:'post',
		reader: {
			type: 'json',
			root: 'vo.detailList',
			totalProperty : 'totalCount'
		}
	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	},
	listeners: {
		beforeload : function(store, operation, eOpts) {
			var queryParams = {'vo.planDto.id':scheduling.longScheduleDesign.planId};		
			Ext.apply(operation, {
				params : queryParams
			});
		},load:function( store, records,successful,operation,eOpts ){
			if(Ext.isEmpty(records))
				Ext.ux.Toast.msg(scheduling.longScheduleDesign.i18n('foss.shortDeparturePlan.toast.msg.title.lable'),scheduling.longScheduleDesign.i18n('foss.shortDeparturePlan.toast.msg.empty.lable'));//('提示信息', '查询结果为空!');
		},datachanged:function(store,eOpts ){
			//车辆合计
			var cnt=store.getCount( ) ;
			var carTotal=0;
			//载重合计	actualMaxLoadWeight 实际最大载重求和
			var weightTotal=0.00;
			//载重缺口
			var gapWeightTotal=0.00;
			//体积合计 truckVolume 车容积
			var volumeTotal=0.00;
			//体积缺口
			var gapVolumeTotal=0.00;
			//合发总载重
			var mergeWeightTotal=0.00;
			//合发总体积
			var mergeVolumeTotal=0.00;
			//循环计算重量、体积求和
			if(!Ext.isEmpty(store)){
				for(var i=0;i<cnt;i++){
					//记录
					var record = store.getAt(i);					
					//非停发车辆数
					if(!Ext.isEmpty(record.data.vehicleNo)&&!Ext.isEmpty(record.data.frequencyType)&&record.data.frequencyType!='SUSPEND'){
						carTotal=carTotal+1;
						//实际最大载重求和
						if(record.data.actualMaxLoadWeight)
							weightTotal = weightTotal+record.data.actualMaxLoadWeight;
						//车容积求和
						if(record.data.truckVolume)
							volumeTotal = volumeTotal+record.data.truckVolume;	
					}
				}
			}
			var from = Ext.getCmp('Foss_longScheduleDesign_form_shipmentsForecast_ID');
			var fromRecord = from.getRecord( );			
			var forcastWeightTotal =0.00;
			var forcastVolumeTotal =0.00;
			if(!Ext.isEmpty(fromRecord)){
				//获取总重量
				 forcastWeightTotal = fromRecord.data.weightTotal;
				//计算重量缺口
				gapWeightTotal = forcastWeightTotal-weightTotal;
				//获取总体积
				 forcastVolumeTotal = fromRecord.data.volumeTotal;
				//计算体积缺口
				gapVolumeTotal = forcastVolumeTotal-volumeTotal;
				//获取合发体积
				if(fromRecord.data.deviationVolume)
				mergeVolumeTotal = fromRecord.data.deviationVolume;
			}
			
			
			if(scheduling.longScheduleDesign.planDetail){
				var textArray = scheduling.longScheduleDesign.planDetail.getDockedItems('toolbar')[1].items.items;
				//车辆合计
				textArray[0].setValue(carTotal);
				textArray[1].setValue(weightTotal);
				textArray[2].setValue(gapWeightTotal.toFixed(2));
				textArray[3].setValue(volumeTotal);
				textArray[4].setValue(gapVolumeTotal.toFixed(2));
				textArray[5].setValue(mergeWeightTotal);
				
			}
			
			
		}
	}
});

//合发数据源
Ext.define('Foss.longScheduleDesign.store.MergeLog',{
	extend: 'Ext.data.Store',
	id:'Foss_longScheduleDesign_store_mergeLog_ID',
	//绑定一个模型
	model: 'Foss.longScheduleDesign.model.MergeLog',
	proxy: {
		type: 'ajax',
		url:scheduling.realPath('queryMergeLogs.action'),
		actionMethods:'post',
		reader: {
			type: 'json',
			root: 'vo.mergeLogs'
		}
	},
	listeners: {
		beforeload : function(store, operation, eOpts) {
			//计划ID
			var queryParams = {'vo.detailDto.truckDepartPlanId':scheduling.longScheduleDesign.planId,
								'vo.detailDto.origOrgCode':scheduling.longScheduleDesign.origOrgCode,// 出发部门
								'vo.detailDto.destOrgCode':scheduling.longScheduleDesign.destOrgCode,//到达部门
								'vo.detailDto.departDate':scheduling.longScheduleDesign.planDate//计划日期
								};				
			Ext.apply(operation, {
				params : queryParams
			});	
		}
	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

//查询合发记录
scheduling.longScheduleDesign.loadMergeLog = function(){	
	if(scheduling.longScheduleDesign.mergeLog){
		scheduling.longScheduleDesign.mergeLog.getStore().load();
	}
}

//公司车辆数据源
Ext.define('Foss.longScheduleDesign.store.InnerCar',{
	extend: 'Ext.data.Store',
	//绑定一个模型
	model: 'Foss.longScheduleDesign.model.InnerCar',
	pageSize:5,
	proxy: {
		type: 'ajax',
		url:scheduling.realPath('queryCarList.action'),
		actionMethods:'post',
		reader: {
			type: 'json',
			root: 'vo.carList',
			totalProperty : 'totalCount'
		}
	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	},
	listeners: {
		beforeload : function(store, operation, eOpts) {
			var searchForm = Ext.getCmp('Foss_longScheduleDesign_form_innerCarSearch_ID');
			if(searchForm && searchForm.getForm().isValid( )){
				var queryParams = searchForm.getValues();				
				Ext.apply(operation, {
					params : queryParams
				});	
			}			
		},load:function( store, records,successful,operation,eOpts ){
			if(Ext.isEmpty(records))
				Ext.ux.Toast.msg(scheduling.longScheduleDesign.i18n('foss.shortDeparturePlan.toast.msg.title.lable'),scheduling.longScheduleDesign.i18n('foss.shortDeparturePlan.toast.msg.empty.lable'));//('提示信息', '查询结果为空!');
			}
	}
});


//线路列表store	
Ext.define('Foss.longScheduleDesign.store.LineNoCombox',{
	extend: 'Ext.data.Store',
	model: 'Foss.longScheduleDesign.model.LineNo',
	proxy: {
		type: 'ajax',
		url:scheduling.realPath('queryLineListByOrigDestCode.action'),
		actionMethods:'post',
		reader: {
			type: 'json',
			root: 'vo.detailList'
		}
	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	},
	listeners: {
		beforeload : function(store, operation, eOpts) {			
			if(!Ext.isEmpty(scheduling.longScheduleDesign.origOrgCode)&&!Ext.isEmpty(scheduling.longScheduleDesign.destOrgCode)){					
				var queryParams={
								'vo.detailDto.origOrgCode':scheduling.longScheduleDesign.origOrgCode,
								'vo.detailDto.destOrgCode':scheduling.longScheduleDesign.destOrgCode
								}
				Ext.apply(operation, {
					params : queryParams
				});
			}			
	}
}
});

//线路列表store	
Ext.define('Foss.longScheduleDesign.store.CarGroupCombox',{
	extend: 'Ext.data.Store',
	model: 'Foss.longScheduleDesign.model.CarGroup',
	proxy: {
		type: 'ajax',
		url:scheduling.realPath('queryCarGroup.action'),
		actionMethods:'post',
		reader: {
			type: 'json',
			root: 'vo.orgList'
		}
	},
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

//月台store
Ext.define('Foss.longScheduleDesign.store.PlatformCombox',{
	extend: 'Ext.data.Store',
	fields: [
	         {name: 'valueName', type: 'string'},
	         {name: 'valueCode',  type: 'string'}
	     ],
   data : {
		'items':[
			{valueCode:'1',valueName:'1'},
			{valueCode:'2',valueName:'2'},
			{valueCode:'3',valueName:'3'}
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

//实时货量信息表单
Ext.define('Foss.longScheduleDesign.form.ShipmentsForecast',{
	extend: 'Ext.form.Panel',
	id:'Foss_longScheduleDesign_form_shipmentsForecast_ID',
	layout:'column',	
	bodyStyle:'padding:5px 5px 0 5px',
	title:scheduling.longScheduleDesign.i18n('foss.longscheduledesign.form.shipmentsforecast.title.lable'),//'实时货量信息',
	frame: true,
	defaultType: 'textfield',	
	defaults: {
		margin:'5 10 5 10',
		anchor: '99%',
		labelWidth:155
	},
	items: [{
		fieldLabel: scheduling.longScheduleDesign.i18n('foss.longscheduledesign.form.shipmentsforecast.origOrgName.lable'),//'出发部门',
		name: 'origOrgName',
		readOnly :true,
		labelWidth:70,
		columnWidth:.3		
	},{
		fieldLabel: scheduling.longScheduleDesign.i18n('foss.longscheduledesign.form.shipmentsforecast.destOrgName.lable'),//'到达部门',
		name: 'destOrgName',
		labelWidth:70,
		readOnly :true,
		columnWidth:.3	
	},{
		xtype: 'datefield',
		fieldLabel: scheduling.longScheduleDesign.i18n('foss.longscheduledesign.form.shipmentsforecast.departDate.lable'),//'发车日期',
		name: 'departDate',
		labelWidth:70,
		format:'Y-m-d',		
		readOnly :true,
		columnWidth:.3		
	},{
		fieldLabel: scheduling.longScheduleDesign.i18n('foss.longscheduledesign.form.shipmentsforecast.truckInfo.lable'),//'卡货重量/体积/票数',
		name: 'truckInfo',
		readOnly :true,
		columnWidth:.3	
	},{
		fieldLabel: scheduling.longScheduleDesign.i18n('foss.longscheduledesign.form.shipmentsforecast.intercityInfo.lable'),//'城货重量/体积/票数',
		name: 'intercityInfo',
		readOnly :true,
		columnWidth:.3	
	},{
		fieldLabel: scheduling.longScheduleDesign.i18n('foss.longscheduledesign.form.shipmentsforecast.noneTransferOfBilling.lable'),//'开单未交接重量/体积/票数',
		name: 'noneTransferOfBilling',
		readOnly :true,
		columnWidth:.3	
	},{
		fieldLabel: scheduling.longScheduleDesign.i18n('foss.longscheduledesign.form.shipmentsforecast.lastGoodsInfo.lable'),//'余货重量/体积/票数',
		name: 'lastGoodsInfo',
		readOnly :true,
		columnWidth:.3	
	},{
		fieldLabel: scheduling.longScheduleDesign.i18n('foss.longscheduledesign.form.shipmentsforecast.exceptArrivalInfo.lable'),//'预计到达重量/体积/票数',
		name: 'exceptArrivalInfo',
		readOnly :true,
		columnWidth:.3	
	},{
		fieldLabel: scheduling.longScheduleDesign.i18n('foss.longscheduledesign.form.shipmentsforecast.totalInfo.lable'),//'总重量/体积/票数',
		name: 'totalInfo',
		readOnly :true,
		columnWidth:.3	
	},{
		fieldLabel: scheduling.longScheduleDesign.i18n('foss.longscheduledesign.form.shipmentsforecast.mergerInfo.lable'),//'(小计)合发重量/体积/票数',
		name: 'mergerInfo',
		readOnly :true,
		columnWidth:.3	
	},{
		fieldLabel: scheduling.longScheduleDesign.i18n('foss.longscheduledesign.form.shipmentsforecast.allTotalGoodsInfo.lable'),//'(汇总)重量/体积/票数',
		name: 'allTotalGoodsInfo',
		readOnly :true,
		columnWidth:.3	
	},{
		fieldLabel: scheduling.longScheduleDesign.i18n('foss.longscheduledesign.form.shipmentsforecast.forecastTime.lable'),//'货量预测时间',
		name: 'forecastTime',
		format:'Y-m-d',		
		readOnly :true,
		columnWidth:.3,
		minWidth:320
	}]	   
});


//短信息表单
Ext.define('Foss.longScheduleDesign.form.LongMessageDuplicate',{
	extend: 'Ext.form.Panel',
	layout:'column',	
	bodyStyle:'padding:5px 5px 0 5px',	
	cls:'autoHeight',
	defaultType: 'textarea',
	defaults: {
		margin:'5 10 5 10',
		anchor: '100%',
		labelWidth:80
	},
	items: [{
		xtype:'textarea',
		fieldLabel: scheduling.longScheduleDesign.i18n('foss.longscheduledesign.form.longmessageduplicate.remark.lable'),//'短信息内容',
		name: 'remark',
		height:100,
		columnWidth:.99
	},{
		border : false,
		xtype : 'container',
		columnWidth:.99,
		layout:'column',
		defaults: {
			margin:'5 0 5 10'
		},
		items : [{
			border : false,
			columnWidth:.78,
			html: '&nbsp;'
		},{
			columnWidth:.2,
			xtype : 'button',
			text: scheduling.longScheduleDesign.i18n('foss.longscheduledesign.form.longmessageduplicate.button.duplicate.lable'),//'复制',
			plugins: Ext.create('Deppon.ux.ZeroClipboardPlugin', {
				targetCmpName: 'remark'
			})

		}]
	}]	   
});

//短信复制窗口
Ext.define('Foss.longScheduleDesign.Window.LongMessageDuplicate', {
	extend:'Ext.window.Window',
	id:'Foss_longScheduleDesign_window_longMessageDuplicate_ID',
	title: scheduling.longScheduleDesign.i18n('foss.longscheduledesign.form.longmessageduplicate.remark.lable'),//'短信息内容',
	modal:true,
	closeAction:'hide',
	width: 400,	
	bodyCls: 'autoHeight',
	layout: 'auto',	
	items:[
		Ext.create('Foss.longScheduleDesign.form.LongMessageDuplicate')
	]
});



//班车列表信息表格
Ext.define('Foss.longScheduleDesign.gird.PlanDetail', {
	extend: 'Ext.grid.Panel',
	animCollapse: false,
	title:scheduling.longScheduleDesign.i18n('foss.longscheduledesign.gird.plandetail.title.lable'),//'长途发车计划列表',
	frame:true,
	cls:'autoHeight',
	bodyCls:'autoHeight',	
	store: null,
	selModel: null,	
	bbar: null,
	autoScroll:true,	
	viewConfig: {
		stripeRows: true
	},
	columns: [{
		xtype:'actioncolumn',
		text: scheduling.longScheduleDesign.i18n('foss.longscheduledesign.gird.plandetail.actioncolumn.lable'),//'操作',
		align: 'center',
		width:100,
		menuDisabled:true,
		selModel:null,
		renderer:function(value,metaData,record){
			//自动导入的不能删除
			if(record.data.initFlag=='Y'){
				this.items[2].iconCls='';
			}else{
				//已出发的不能删除
				if(record.data.hasLeft=='Y'){
					this.items[2].iconCls='';
				}else{
					this.items[2].iconCls='deppon_icons_delete';
				}
			}
			//已出发和下发的不能切换
			if(record.data.hasLeft=='Y'||record.data.planStatus=='RELEASE'){
				this.items[3].iconCls='';
			}else{
				this.items[3].iconCls='foss_icons_tfr_changeCarStyle';
			}
		},
		items: [{
                tooltip: scheduling.longScheduleDesign.i18n('foss.longscheduledesign.gird.plandetail.tooltip.duplicate.lable'),//'拷贝短信文本',
				iconCls:'deppon_icons_notice',
				width:20,
                handler: function(grid, rowIndex, colIndex) {	
					var record=grid.getStore().getAt(rowIndex).data;	
					var messageWin = Ext.getCmp('Foss_longScheduleDesign_window_longMessageDuplicate_ID');
					if(messageWin==null)
						messageWin=Ext.create('Foss.longScheduleDesign.Window.LongMessageDuplicate');
						var content,remark='线路:'+record.lineName+',班次：'+record.frequencyNo+',车辆：'+record.vehicleNo+',司机：'+record.driverName1+',计划发车时间:'+record.planDepartTime;
						content={remark:remark};
						messageWin.items.items[0].getForm().setValues(content);
						messageWin.show();
                }
            },{
                tooltip: scheduling.longScheduleDesign.i18n('foss.longscheduledesign.gird.plandetail.tooltip.edit.lable'),//'编辑',
				iconCls:'deppon_icons_edit',
				width:20,
                handler: function(grid, rowIndex, colIndex) {		
					var record=grid.getStore().getAt(rowIndex);
					if(record.data.truckType == 'OWN'){
						//判断是公司还是外请车，弹出相应的编辑框
						var innerCarWin;
						if(Ext.isEmpty(scheduling.longScheduleDesign.innerCarWin)){
							innerCarWin=Ext.create('Foss.longScheduleDesign.Window.LongInnerCar');
							scheduling.longScheduleDesign.innerCarWin=innerCarWin;
						}else{
							innerCarWin=scheduling.longScheduleDesign.innerCarWin;
						}		
						
							var form=innerCarWin.items.items[2].getForm();
							//如果已出发，则不允许修改
							if(record.data.hasLeft=='Y'){
								scheduling.longScheduleDesign.readOnlyForm(form,true);
								innerCarWin.items.items[2].query('button')[1].disable();
							}else{
								scheduling.longScheduleDesign.readOnlyForm(form,false);
								innerCarWin.items.items[2].query('button')[1].enable();
							}
							//线路列表
							//form.findField('lineNo').getStore().load();
							//是否快递线路
							scheduling.longScheduleDesign.lineNoStore.load(
									function(records, operation, success) {
										form.findField('lineNo').rawValue=records[0].data.lineName;
									    form.findField('lineNo').lastValue=records[0].data.lineNo;
									    form.findField('lineNo').setValue(records[0].data.lineNo);
									    form.findField('lineNoExpress').setValue(records[0].data.lineNoExpress);
									}		
							);
							//加载
							form.loadRecord(record);
							
							//2016-01-04 200968
							var depart = FossUserContext.getCurrentDept();
							if(record.data.planStatus=='RELEASE' && depart.transDepartment=='N'){
								//200968 2015-12-21 下发发车计划之后,除了车队外,公司车的车牌号和货柜号不可修改
								var vehicleNo = form.findField('vehicleNo');
								if(!Ext.isEmpty(vehicleNo.getValue())) {
									vehicleNo.setReadOnly(true);
								}
								var containerNo = form.findField('containerNo');
								if(!Ext.isEmpty(containerNo.getValue())) {
									containerNo.setReadOnly(true);
								}
							}
							
							
							//车队
							if(record.data.longCarGroup){
								form.findField('longCarGroup').setCombValue(record.data.carGroupName,record.data.longCarGroup);
							}	
							//月台号
							if(record.data.platformNo){
								form.findField('platformNo').setCombValue(record.data.platformNoCode,record.data.platformNo);
							}
							//车型
							if(record.data.truckModel)
							form.findField('truckModel').setCombValue(record.data.truckModelValue,record.data.truckModel);
							//司机1
							if(record.data.driverCode1)
							form.findField('driverCode1').setCombValue(record.data.driverName1,record.data.driverCode1);
							//司机2
							if(record.data.driverCode2)
							form.findField('driverCode2').setCombValue(record.data.driverName2,record.data.driverCode2);
							//出发部门
							if(record.data.origOrgCode)
							form.findField('origOrgCode').setCombValue(scheduling.longScheduleDesign.origOrgName,record.data.origOrgCode);
							//到达部门
							if(record.data.destOrgCode)
							form.findField('destOrgCode').setCombValue(scheduling.longScheduleDesign.destOrgName,record.data.destOrgCode);
							//线路列表
							if(record.data.lineNo){
								form.findField('lineNo').setValue(record.data.lineNo);
							}
							//下发不能修改备注
							if(record.data.planStatus=='RELEASE'){
								form.findField('truckInfoNotes').setReadOnly(true);								
							}else{
								form.findField('truckInfoNotes').setReadOnly(false);
							}
							innerCarWin.items.items[2].query('button')[2].disable();
							innerCarWin.setTitle(scheduling.longScheduleDesign.i18n('foss.shortScheduleDesign.gird.PlanDetail.action.editInner.lable'));//('编辑公司车');
							innerCarWin.show();
					}else{
						//判断是公司还是外请车，弹出相应的编辑框
							var outerCarWin ;
							if(Ext.isEmpty(scheduling.longScheduleDesign.outerCarWin)){
								outerCarWin=Ext.create('Foss.longScheduleDesign.Window.LongOuterCar');
								scheduling.longScheduleDesign.outerCarWin=outerCarWin;
							}else{
								outerCarWin=scheduling.longScheduleDesign.outerCarWin
							}
							outerCarWin.show();
							scheduling.longScheduleDesign.outerCarWin=outerCarWin;
							var form=outerCarWin.items.items[0].getForm();
							//如果已出发，则不允许修改
							if(record.data.hasLeft=='Y'){
								scheduling.longScheduleDesign.readOnlyForm(form,true);
								outerCarWin.items.items[0].query('button')[1].disable();
							}else{
								scheduling.longScheduleDesign.readOnlyForm(form,false);
								outerCarWin.items.items[0].query('button')[1].enable();
							}
							//线路列表
							//form.findField('lineNo').getStore().load();
							//是否快递线路
							scheduling.longScheduleDesign.lineNoStore.load(
									function(records, operation, success) {
									    form.findField('lineNo').rawValue=records[0].data.lineName;
									    form.findField('lineNo').lastValue=records[0].data.lineNo;
									    form.findField('lineNo').setValue(records[0].data.lineNo);
									    form.findField('lineNoExpress').setValue(records[0].data.lineNoExpress);
									}		
							);
							//加载
							form.loadRecord(record);							
							//车队
							if(record.data.longCarGroup){
								form.findField('longCarGroup').setCombValue(record.data.carGroupName,record.data.longCarGroup);
							}
							//月台号
							if(record.data.platformNo){
								form.findField('platformNo').setCombValue(record.data.platformNoCode,record.data.platformNo);
							}
							//车型
							if(record.data.truckModel)
							outerCarWin.items.items[0].getForm().findField('truckModel').setCombValue(record.data.truckModelValue,record.data.truckModel);
							//司机1
							if(record.data.driverCode1)
							outerCarWin.items.items[0].getForm().findField('driverCode1').setCombValue(record.data.driverName1,record.data.driverCode1);
							//司机2
							if(record.data.driverCode2)
							outerCarWin.items.items[0].getForm().findField('driverCode2').setCombValue(record.data.driverName2,record.data.driverCode2);
							//出发部门
							if(record.data.origOrgCode)
							form.findField('origOrgCode').setCombValue(scheduling.longScheduleDesign.origOrgName,record.data.origOrgCode);
							//到达部门
							if(record.data.destOrgCode)
							form.findField('destOrgCode').setCombValue(scheduling.longScheduleDesign.destOrgName,record.data.destOrgCode);
							//线路列表
							if(record.data.lineNo){
								form.findField('lineNo').setValue(record.data.lineNo);
							}
							
							//2016-01-17 200968  长途发车发车计划”界面，点击进入“加发外请车”界面:外请车的车辆净空一框为不可编辑状态
							form.findField('truckVolume').setReadOnly(true);
							
							//下发不能修改备注
							if(record.data.planStatus=='RELEASE'){
								form.findField('truckInfoNotes').setReadOnly(true);
								
							}else{
								form.findField('truckInfoNotes').setReadOnly(false);
							}
							outerCarWin.items.items[0].query('button')[2].disable();
							outerCarWin.setTitle(scheduling.longScheduleDesign.i18n('foss.shortScheduleDesign.gird.PlanDetail.action.editOuter.lable'));//('编辑外请车');
							
					}				
                }
            },{
                tooltip: scheduling.longScheduleDesign.i18n('foss.longscheduledesign.gird.plandetail.tooltip.delete.lable'),//'删除',
				iconCls:'deppon_icons_delete',
				width:20,
                handler: function(grid, rowIndex, colIndex) {	
					//grid.up('gridpanel');			
					var id=grid.getStore().getAt(rowIndex).data.id;
					var ids=new Array();
					ids.push(id);					
					if(!Ext.isEmpty(ids)){
						Ext.Msg.confirm(scheduling.longScheduleDesign.i18n('foss.shortDeparturePlan.confirm.title.lable'), 
								scheduling.longScheduleDesign.i18n('foss.shortDeparturePlan.confirm.confirmDelete.tip'), function(btn){//'确认删除选中的发车计划?'
						    if (btn == 'yes'){
						    	scheduling.longScheduleDesign.deleteTruckDepartPlanDetail(grid,ids);
						    }
						});	
					}else{
						Ext.MessageBox.alert(scheduling.longScheduleDesign.i18n('foss.shortDeparturePlan.confirm.title.lable'),
								scheduling.longScheduleDesign.i18n('foss.shortDeparturePlan.confirm.chooseDelete.tip'));//'请选择需要删除的发车计划'
					}
                }
            },{
                tooltip: scheduling.longScheduleDesign.i18n('foss.longscheduledesign.gird.plandetail.tooltip.changeCarStyle.lable'),//'切换归属类型',
				iconCls:'foss_icons_tfr_changeCarStyle',
				width:20,
                handler: function(grid, rowIndex, colIndex) {
                	var row = grid.getStore().getAt(rowIndex).data;
                	var tipMsg;
                	var truckTypeTo;
                	if(row.truckType=='OWN'){
                		tipMsg=scheduling.longScheduleDesign.i18n('foss.shortDeparturePlan.confirm.changeToOutter.tip');//'确认切换为外请车？';
                		truckTypeTo='OUTER';
                	}else{
                		tipMsg=scheduling.longScheduleDesign.i18n('foss.shortDeparturePlan.confirm.changeToInner.tip');//'确认切换为公司车？';
                		truckTypeTo='OWN';
                	}
                	Ext.Msg.confirm(scheduling.longScheduleDesign.i18n('foss.shortDeparturePlan.confirm.title.lable'), tipMsg, function(btn){
					    if (btn == 'yes'){
					    	scheduling.longScheduleDesign.changeTruckType(row.id,truckTypeTo);
					    }
					});
                }
            }]
	},{
		text: scheduling.longScheduleDesign.i18n('foss.longscheduledesign.gird.plandetail.lineName.lable'),//'线路名称',
		width:180, 
		menuDisabled:true,
		sortable: true, 
		dataIndex: 'lineName',
		renderer: function(value, metadata, record, rowIndex, columnIndex, store) {
			var me = this,
			deValue = value + "" ;
			metadata.tdAttr = 'data-qtip="' + deValue + '"';
			return value;
	    }
	},{
		text: scheduling.longScheduleDesign.i18n('foss.longscheduledesign.gird.plandetail.frequencyNo.lable'),//'班次',
		minWidth:50,
		flex: 1, 
		menuDisabled:true,
		sortable: true, 
		dataIndex: 'frequencyNo'
	},{
		text: scheduling.longScheduleDesign.i18n('foss.longscheduledesign.gird.plandetail.planDepartTime.lable'),//'计划发车时间',
		menuDisabled:true,
		minWidth:140,
		flex: 1, 
		sortable: true, 
		dataIndex: 'planDepartTime'
	},{
		text: scheduling.longScheduleDesign.i18n('foss.longscheduledesign.gird.plandetail.truckType.lable'),//'归属类型',
		menuDisabled:true,
		flex: 1.2, 
		sortable: true, 
		dataIndex: 'truckType',
		minWidth:80,
		renderer:function(val){
			if(val=='OWN'){
				return scheduling.longScheduleDesign.i18n('foss.longscheduledesign.gird.plandetail.truckType.OWN.lable');//'公司车';
			}
			else if(val=='OUTER'){
				return scheduling.longScheduleDesign.i18n('foss.longscheduledesign.gird.plandetail.truckType.OUTER.lable');//'外请车';
			}			
		}		
	},{
		text: scheduling.longScheduleDesign.i18n('foss.longscheduledesign.gird.plandetail.isOnScheduling.lable'),//'正班车',
		menuDisabled:true,
		flex: 1, 
		minWidth:60,
		sortable: true, 
		dataIndex: 'isOnScheduling',
		renderer:function(val){
			if(val=='Y'){
				return scheduling.longScheduleDesign.i18n('foss.longscheduledesign.gird.plandetail.isOnScheduling.yes.lable');//'是';
			}
			else if(val=='N'){
				return scheduling.longScheduleDesign.i18n('foss.longscheduledesign.gird.plandetail.isOnScheduling.no.lable');//'否';
			}			
		}
	},{
		text: scheduling.longScheduleDesign.i18n('foss.longscheduledesign.gird.plandetail.planStatus.lable'),//'状态',
		menuDisabled:true,
		flex: 1, 
		sortable: true, 
		dataIndex: 'planStatus',
		renderer:function(val){
			if(val=='NEW'){
				return scheduling.longScheduleDesign.i18n('foss.longscheduledesign.gird.plandetail.planStatus.NEW.lable');//'新建';
			}
			else if(val=='RELEASE'){
				return scheduling.longScheduleDesign.i18n('foss.longscheduledesign.gird.plandetail.planStatus.RELEASE.lable');//'下发';
			}		
		}
	},{
		text: scheduling.longScheduleDesign.i18n('foss.longscheduledesign.gird.plandetail.driverName1.lable'),//'司机姓名1',
		menuDisabled:true,
		minWidth:100,
		flex: 1, 
		sortable: true, 
		dataIndex: 'driverName1'
	},{
		text: scheduling.longScheduleDesign.i18n('foss.longscheduledesign.gird.plandetail.driverPhone1.lable'),//'联系方式1',
		menuDisabled:true,
		minWidth:100,
		flex: 1, 
		sortable: true, 
		dataIndex: 'driverPhone1'
	},{
		text: scheduling.longScheduleDesign.i18n('foss.longscheduledesign.gird.plandetail.vehicleNo.lable'),//'车牌号',
		menuDisabled:true,
		minWidth:100,
		flex: 1, 
		sortable: true, 
		dataIndex: 'vehicleNo'
	},{
		text: scheduling.longScheduleDesign.i18n('foss.longscheduledesign.gird.plandetail.truckModelValue.lable'),//'车型',
		menuDisabled:true,
		minWidth:100,
		flex: 1, 
		sortable: true, 
		dataIndex: 'truckModelValue'
		
	},{
		text: scheduling.longScheduleDesign.i18n('foss.longscheduledesign.gird.plandetail.driverName2.lable'),//'司机姓名2',
		menuDisabled:true,
		flex: 1, 
		minWidth:100,
		sortable: true, 
		dataIndex: 'driverName2'
	},{
		text: scheduling.longScheduleDesign.i18n('foss.longscheduledesign.gird.plandetail.driverPhone2.lable'),//'联系方式2',
		menuDisabled:true,
		minWidth:100,
		flex: 1, 
		sortable: true, 
		dataIndex: 'driverPhone2'
	},{
		text: scheduling.longScheduleDesign.i18n('foss.longscheduledesign.gird.plandetail.carOwnerName.lable'),//'车辆所属车队',
		menuDisabled:true,
		minWidth:80,
		sortable: true, 
		dataIndex: 'carOwnerName',
		renderer: function(value, metadata, record, rowIndex, columnIndex, store) {
			var me = this,
			deValue = value + "" ;
			metadata.tdAttr = 'data-qtip="' + deValue + '"';
			return value;
	    }
	},{
		text: scheduling.longScheduleDesign.i18n('foss.longscheduledesign.gird.plandetail.frequencyType.lable'),//'班次类型',
		menuDisabled:true,
		flex: 1.2, 
		minWidth:80,
		sortable: true, 
		dataIndex: 'frequencyType',
		renderer:function(val){
			if(val=='NORMAL'){
				return scheduling.longScheduleDesign.i18n('foss.longscheduledesign.gird.plandetail.frequencyType.NORMAL.lable');//'正常';
			}
			else if(val=='SUSPEND'){
				return scheduling.longScheduleDesign.i18n('foss.longscheduledesign.gird.plandetail.frequencyType.SUSPEND.lable');//'停发';
			}			
			else if(val=='ADD'){
				return scheduling.longScheduleDesign.i18n('foss.longscheduledesign.gird.plandetail.frequencyType.ADD.lable');//'加发';
			}			
		}
	},{
		text: scheduling.longScheduleDesign.i18n('foss.longscheduledesign.gird.plandetail.platformNoCode.lable'),//'月台号',
		menuDisabled:true,
		minWidth:60,
		flex: 1, 
		sortable: true, 
		dataIndex: 'platformNoCode'
	},{
		text: scheduling.longScheduleDesign.i18n('foss.longscheduledesign.gird.plandetail.maxLoadWeight.lable'),//'最大载重',
		menuDisabled:true,
		minWidth:100,
		flex: 1, 
		sortable: true, 
		dataIndex: 'maxLoadWeight'
	},{
		text: scheduling.longScheduleDesign.i18n('foss.longscheduledesign.gird.plandetail.planLoadWeight.lable'),//'预计装载载重',
		menuDisabled:true,
		minWidth:100,
		flex: 1, 
		sortable: true, 
		dataIndex: 'planLoadWeight'
	},{
		text: scheduling.longScheduleDesign.i18n('foss.longscheduledesign.gird.plandetail.truckVolume.lable'),//'车辆净空',
		menuDisabled:true,
		minWidth:100,
		flex: 1, 
		sortable: true, 
		dataIndex: 'truckVolume'
	},{
		text: scheduling.longScheduleDesign.i18n('foss.longscheduledesign.gird.plandetail.planLoadVolume.lable'),//'预计装载体积',
		menuDisabled:true,
		minWidth:100,
		flex: 1, 
		sortable: true, 
		dataIndex: 'planLoadVolume'
	},{
		text: scheduling.longScheduleDesign.i18n('foss.longscheduledesign.gird.plandetail.containerNo.lable'),//'货柜号',
		menuDisabled:true,
		minWidth:60,
		flex: 1.2, 
		sortable: true, 
		dataIndex: 'containerNo',
		renderer: function(value, metadata, record, rowIndex, columnIndex, store) {
			var me = this,
			deValue = value + "" ;
			metadata.tdAttr = 'data-qtip="' + deValue + '"';
			return value;
	    }
			
	},{
		text:'挂牌号',
		menuDisabled:true,
		minWidth:100,
		flex: 1,
		sortable: true, 
		dataIndex:'trailerVehicleNo'
		
	},{
		text: scheduling.longScheduleDesign.i18n('foss.longscheduledesign.gird.plandetail.createUserName.lable'),//'创建人',
		menuDisabled:true,
		minWidth:100,
		flex: 1, 
		sortable: true, 
		dataIndex: 'createUserName'
	},{
		text: scheduling.longScheduleDesign.i18n('foss.longscheduledesign.gird.plandetail.truckInfoNotes.lable'),//'备注',
		menuDisabled:true,
		columnWidth:200,
		sortable: true, 
		dataIndex: 'truckInfoNotes',
		renderer: function(value, metadata, record, rowIndex, columnIndex, store) {
			var me = this,
			deValue = value + "" ;
			metadata.tdAttr = 'data-qtip="' + deValue + '"';
			return value;
	    }
	}],
	tbar:[{
		xtype:'hiddenfield',
		flex:10
		},{
			xtype: 'button', 
			text: scheduling.longScheduleDesign.i18n('foss.longscheduledesign.gird.plandetail.button.addOwn.lable'),//'加发公司车',
			handler: function() {
				var innerCarWin;
				if(Ext.isEmpty(scheduling.longScheduleDesign.innerCarWin)){
					innerCarWin=Ext.create('Foss.longScheduleDesign.Window.LongInnerCar');
					scheduling.longScheduleDesign.innerCarWin=innerCarWin;
				}else{
					innerCarWin=scheduling.longScheduleDesign.innerCarWin;
				}
				var form =innerCarWin.items.items[2].getForm();
				//发车日期
				var departDate =scheduling.longScheduleDesign.planDate+" 00:00:00";
				var departDate2=new Date(departDate.replace(/-/g,"/")); 
				//重新加载数据
				var record = Ext.create('Foss.longScheduleDesign.model.PlanDetail',
						{origOrgCode:scheduling.longScheduleDesign.origOrgCode,//出发部门
						 destOrgCode:scheduling.longScheduleDesign.destOrgCode,//到达部门
						 planDepartTime:null,
						 platformTimeEnd:null,
						 platformTimeStart:null,
						 departDate:departDate2,
						 planType:'LONG',//长途
						 truckDepartPlanId:scheduling.longScheduleDesign.planId,
						 truckType:'OWN',//公司车
						 isOnScheduling:'N',//默认非正班车
						 frequencyType:'ADD'//默认加发班次						 
						});				    
						 
					//重置
					scheduling.longScheduleDesign.resetField(form);
					form.loadRecord(record);
					scheduling.longScheduleDesign.readOnlyForm(form,false);
					//出发部门
					form.findField('origOrgCode').setCombValue(scheduling.longScheduleDesign.origOrgName,record.data.origOrgCode);
					//到达部门
					form.findField('destOrgCode').setCombValue(scheduling.longScheduleDesign.destOrgName,record.data.destOrgCode);
					//备注信息
					form.findField('truckInfoNotes').setReadOnly(false);
					//线路列表
					scheduling.longScheduleDesign.lineNoStore.load(
							function(records, operation, success) {
							    if(!Ext.isEmpty(records)){
							    	form.findField('lineNo').rawValue=records[0].data.lineName;
							    	form.findField('lineNo').lastValue=records[0].data.lineNo;
							    	form.findField('lineNo').setValue(records[0].data.lineNo);
							    	form.findField('lineNoExpress').setValue(records[0].data.lineNoExpress);
							    }
							}		
					);
					//车队
					var depart = FossUserContext.getCurrentDept();
					if(depart.transDepartment=='Y'){
						form.findField('longCarGroup').setCombValue(depart.name,depart.code);
						var searchForm=Ext.getCmp('Foss_longScheduleDesign_form_innerCarSearch_ID');
						if(searchForm){
							searchForm.getForm().findField('vo.carDto.carOwnerCode').setCombValue(depart.name,depart.code);
						}
					}
					innerCarWin.items.items[2].query('button')[1].enable(true);
					innerCarWin.items.items[2].query('button')[2].enable(true);
					innerCarWin.setTitle(scheduling.longScheduleDesign.i18n('foss.shortScheduleDesign.gird.PlanDetail.button.addInner.lable'));//('加发公司车');
					innerCarWin.show();
					scheduling.longScheduleDesign.queryPlatformNo(form);
					//定时任务
					var task = new Ext.util.DelayedTask(function(){
								    scheduling.longScheduleDesign.queryMaxfrequencyNo(form);
								});
						task.delay(500);
					
			}
		},{
			xtype: 'button', 
			text: scheduling.longScheduleDesign.i18n('foss.longscheduledesign.gird.plandetail.button.addOuter.lable'),//'加发外请车',
			handler: function() {
				var outerCarWin ;
				if(Ext.isEmpty(scheduling.longScheduleDesign.outerCarWin)){
					outerCarWin=Ext.create('Foss.longScheduleDesign.Window.LongOuterCar');
					scheduling.longScheduleDesign.outerCarWin=outerCarWin;
				}else{
					outerCarWin=scheduling.longScheduleDesign.outerCarWin
				}	
				var departDate=scheduling.longScheduleDesign.planDate
				var departDate2=new Date(departDate.replace(/-/g,"/")); 
				//重新加载数据
				var record = Ext.create('Foss.longScheduleDesign.model.PlanDetail',
						{origOrgCode:scheduling.longScheduleDesign.origOrgCode,
						 destOrgCode:scheduling.longScheduleDesign.destOrgCode,
						 planDepartTime:null,
						 platformTimeEnd:null,
						 platformTimeStart:null,
						 departDate:departDate2,
						 planType:'LONG',
						 truckDepartPlanId:scheduling.longScheduleDesign.planId,
						 truckType:'OUTER',//外请
						 isOnScheduling:'N',//默认非正班车
						 frequencyType:'ADD'//默认加发班次
							 });
					var form=outerCarWin.items.items[0].getForm();
						scheduling.longScheduleDesign.resetField(form);
						form.loadRecord(record);
						scheduling.longScheduleDesign.readOnlyForm(form,false);
						//出发部门
						form.findField('origOrgCode').setCombValue(scheduling.longScheduleDesign.origOrgName,record.data.origOrgCode);
						//到达部门
						form.findField('destOrgCode').setCombValue(scheduling.longScheduleDesign.destOrgName,record.data.destOrgCode);
						//备注信息
						form.findField('truckInfoNotes').setReadOnly(false);

						//2016-01-17 200968  长途发车发车计划”界面，点击进入“加发外请车”界面:外请车的车辆净空一框为不可编辑状态
						form.findField('truckVolume').setReadOnly(true);
						
						
						//线路列表
						scheduling.longScheduleDesign.lineNoStore.load(
								function(records, operation, success) {
								    if(!Ext.isEmpty(records)){
								    	form.findField('lineNo').rawValue=records[0].data.lineName;
								    	form.findField('lineNo').lastValue=records[0].data.lineNo;
								    	form.findField('lineNo').setValue(records[0].data.lineNo);
								    	form.findField('lineNoExpress').setValue(records[0].data.lineNoExpress);
								    }
								}
						);
						//车队
						var depart = FossUserContext.getCurrentDept();
						if(depart.transDepartment=='Y'){
							form.findField('longCarGroup').setCombValue(depart.name,depart.code);
						}
						//按钮可用
						outerCarWin.items.items[0].query('button')[1].enable(true);
						outerCarWin.items.items[0].query('button')[2].enable(true);
						outerCarWin.setTitle(scheduling.longScheduleDesign.i18n('foss.shortScheduleDesign.gird.PlanDetail.button.addOuter.lable'));//('加发外请车');
						outerCarWin.show();
						scheduling.longScheduleDesign.queryPlatformNo(form);
						//定时任务
						var task = new Ext.util.DelayedTask(function(){
									    scheduling.longScheduleDesign.queryMaxfrequencyNo(form);
									});
							task.delay(500);
			}
		},{
			xtype: 'button', 
			text: scheduling.longScheduleDesign.i18n('foss.longscheduledesign.gird.plandetail.button.release.lable'),//'下发',
			handler: function() {
				var grid=this.up('grid');
				var records =grid.getSelectionModel( ).getSelection( ); 
				var ids=new Array();
				for(var i in records){
					var record=records[i].data;					
					if(record.planStatus=='NEW'){
						ids.push(records[i].data.id);
					}else{
						Ext.MessageBox.alert(scheduling.longScheduleDesign.i18n('foss.shortDeparturePlan.confirm.title.lable'),
											 scheduling.longScheduleDesign.i18n('foss.longscheduledesign.gird.plandetail.hasreleased.tip'));//'您选择的存在已下发的发车计划，请选择新建状态的发车计划进行下发'
						ids=null;
						return ;
					}					
				}
				if(!Ext.isEmpty(ids)){
					Ext.Msg.confirm(scheduling.longScheduleDesign.i18n('foss.shortDeparturePlan.confirm.title.lable'), 
							scheduling.longScheduleDesign.i18n('foss.longscheduledesign.gird.plandetail.confirmReleased.tip'), function(btn){//'确认下发选中的发车计划?'
					    if (btn == 'yes'){
					    	scheduling.longScheduleDesign.releaseTruckDepartPlanDetail(grid,ids);
					    }
					});	
				}else{
					Ext.MessageBox.alert(scheduling.longScheduleDesign.i18n('foss.shortDeparturePlan.confirm.title.lable'),
							scheduling.longScheduleDesign.i18n('foss.longscheduledesign.gird.plandetail.chooseReleased.tip'));//'请选择需要下发的发车计划'
				}				
			}
		},{
			xtype: 'button', 
			text: scheduling.longScheduleDesign.i18n('foss.longscheduledesign.gird.plandetail.button.delete.lable'),//'删除',
			handler: function() {
				var grid=this.up('grid');
				var records =grid.getSelectionModel( ).getSelection( ); 
				var ids=new Array();
				for(var i in records){					
					var record=records[i].data;
					if(record.hasLeft=='Y'){
						Ext.MessageBox.alert(scheduling.longScheduleDesign.i18n('foss.shortDeparturePlan.confirm.title.lable'),
								  scheduling.longScheduleDesign.i18n('foss.longscheduledesign.gird.plandetail.cannotDelete.tip')+',班次'+record.frequencyNo+'的车辆已经出发');
						return ;
					}						//非导入且未出发
					  if(record.initFlag=='N'){
						  ids.push(records[i].data.id);
					  }else{
						  Ext.MessageBox.alert(scheduling.longScheduleDesign.i18n('foss.shortDeparturePlan.confirm.title.lable'),
								  scheduling.longScheduleDesign.i18n('foss.longscheduledesign.gird.plandetail.cannotDelete.tip')+',班次'+record.frequencyNo+'的计划为系统初始化');//'存在不可删除的发车计划'
						  return;
					  }
											
				}
				
				if(!Ext.isEmpty(ids)){
					Ext.Msg.confirm(scheduling.longScheduleDesign.i18n('foss.shortDeparturePlan.confirm.title.lable'),
							scheduling.longScheduleDesign.i18n('foss.shortDeparturePlan.confirm.confirmDelete.tip'), function(btn){//'确认删除选中的发车计划?'
					    if (btn == 'yes'){
					    	scheduling.longScheduleDesign.deleteTruckDepartPlanDetail(grid,ids);
					    }
					});	
				}else{
					Ext.MessageBox.alert(scheduling.longScheduleDesign.i18n('foss.shortDeparturePlan.confirm.title.lable'),
							scheduling.longScheduleDesign.i18n('foss.shortDeparturePlan.confirm.chooseDelete.tip'));//'请选择需要删除的发车计划'
				}				
			
			}
		}
	],
	listeners:{
		beforeitemclick:function( view, record){
			if(record.data.id){
				scheduling.longScheduleDesign.planDetailId=	record.data.id;
				//查询本条计划明细的修改日志
				scheduling.longScheduleDesign.loadUpdateLog();
			}			
		}
	},
	dockedItems:[{
		   xtype:'toolbar',
		   dock:'bottom',
		   layout:'column',
		   defaults:{
			 xtype:'textfield',
			 value:'0',
			 readOnly:true,
			 labelWidth:50,
			 width:30
		   },
		   items:[{
			   fieldLabel:scheduling.longScheduleDesign.i18n('foss.longscheduledesign.gird.plandetail.toolbar.carTotal.lable'),//'车辆合计',
			   labelWidth:80,
			   columnWidth:.10,
			   dataIndex: 'carTotal'
		   },{
			   fieldLabel:scheduling.longScheduleDesign.i18n('foss.longscheduledesign.gird.plandetail.toolbar.weightTotal.lable'),//'载重合计',
			   columnWidth:.12,
			   labelWidth:80,
			   minWidth:140,
			   dataIndex: 'weightTotal'
		   },{
			   fieldLabel:scheduling.longScheduleDesign.i18n('foss.longscheduledesign.gird.plandetail.toolbar.gapWeightTotal.lable'),//'载重缺口',
			   labelWidth:80,
			   minWidth:140,
			   columnWidth:.12,
			   dataIndex: 'gapWeightTotal'
		   },{
			   fieldLabel:scheduling.longScheduleDesign.i18n('foss.longscheduledesign.gird.plandetail.toolbar.volumeTotal.lable'),//'体积合计',
			   columnWidth:.12,	
			   minWidth:140,
			   labelWidth:80,
			   dataIndex: 'volumeTotal'
		   },{
			   fieldLabel:scheduling.longScheduleDesign.i18n('foss.longscheduledesign.gird.plandetail.toolbar.gapVolumeTotal.lable'),//'体积缺口',
			   labelWidth:80,
			   minWidth:140,
			   columnWidth:.12,
			   dataIndex: 'gapVolumeTotal'
		   },{
			   fieldLabel:scheduling.longScheduleDesign.i18n('foss.longscheduledesign.gird.plandetail.toolbar.mergeWeightTotal.lable'),//'合发总载重',
			   labelWidth:90,
			   columnWidth:.1,
			   dataIndex: 'mergeWeightTotal'
		   },{
			   fieldLabel:scheduling.longScheduleDesign.i18n('foss.longscheduledesign.gird.plandetail.toolbar.mergeVolumeTotal.lable'),//'合发总体积',
			   labelWidth:90,
			   minWidth:140,
			   columnWidth:.12,
			   dataIndex: 'mergeVolumeTotal'
		   }]
	}],
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);		
		me.store = Ext.create('Foss.longScheduleDesign.store.PlanDetail');
		me.bbar = Ext.create('Deppon.StandardPaging',{
					store:me.store,
					plugins: 'pagesizeplugin'
				});
		me.selModel = Ext.create('Ext.selection.CheckboxModel');
		scheduling.longScheduleDesign.datailPagebar=me.bbar;
		me.callParent([cfg]);	 
	}
});	

//发车计划日志表格
Ext.define('Foss.longScheduleDesign.gird.MergeLog',{
		extend: 'Ext.grid.Panel',
		id:'Foss_longScheduleDesign_gird_mergeLog_ID',
		title:scheduling.longScheduleDesign.i18n('foss.longscheduledesign.gird.mergelog.title.lable'),//'合发记录信息',
		frame:true,
		animCollapse: false,
		hideHeaders:true,
		cls:'autoHeight',
		bodyCls:'autoHeight',
		store: null,
		selModel: null,	
		autoScroll:true,
		columns: [{
				text: scheduling.longScheduleDesign.i18n('foss.longscheduledesign.gird.mergelog.operationDate.lable'),//'操作时间',
				minWidth:150,
				sortable: true, 
				dataIndex: 'operationDate'
			},{
				text: scheduling.longScheduleDesign.i18n('foss.longscheduledesign.gird.mergelog.operatorName.lable'),//'操作人',
				minWidth:120,
				sortable: true, 
				dataIndex: 'operatorName'
			},{
				text: scheduling.longScheduleDesign.i18n('foss.longscheduledesign.gird.mergelog.operationDesc.lable'),//'合发描述',
				flex: 1, 
				sortable: true, 
				dataIndex: 'operationDesc'
			}],
		constructor: function(config){
			var me = this,
			cfg = Ext.apply({}, config);
			me.store = Ext.getCmp('Foss_longScheduleDesign_store_mergeLog_ID')==null?Ext.create('Foss.longScheduleDesign.store.MergeLog'):Ext.getCmp('Foss_longScheduleDesign_store_mergeLog_ID');
			me.callParent([cfg]);	 
		}
});



//线路store
scheduling.longScheduleDesign.lineNoStore=Ext.create('Foss.longScheduleDesign.store.LineNoCombox');


//加发外请车表单
Ext.define('Foss.longScheduleDesign.form.OuterCar',{
		extend: 'Ext.form.Panel',		
		id:'Foss_longScheduleDesign_form_outerCar_ID',
		bodyStyle:'padding:5px 5px 0',
		fieldDefaults: {
			msgTarget: 'side',
			labelWidth: 90,
			margin:'5 5 5 0'
		},
		defaults: {
			anchor: '97%'
		},
		items : [{				
			xtype:'fieldset',
			title: scheduling.longScheduleDesign.i18n('foss.longScheduleDesign.form.OuterCar.fieldset.lineInfo.lable'),//'线路班次信息',
			defaultType: 'textfield',
			layout: 'column',
			defaults: {
				anchor: '100%'
			},
			items: [{
				xtype : 'dynamicorgcombselector',
				transferCenter:'Y',//外场
				fieldLabel: scheduling.longScheduleDesign.i18n('foss.longScheduleDesign.form.OuterCar.origOrgCode.lable'),//'出发部门',
				name: 'origOrgCode',
				readOnly:true,
				allowBlank:false,
				columnWidth:.25
			},{
				xtype : 'dynamicorgcombselector',
				transferCenter:'Y',//外场
				fieldLabel: scheduling.longScheduleDesign.i18n('foss.longScheduleDesign.form.OuterCar.destOrgCode.lable'),//'到达部门',
				name: 'destOrgCode',
				readOnly:true,
				allowBlank:false,
				columnWidth:.25
			},{
				name: 'lineNo',
				fieldLabel: scheduling.longScheduleDesign.i18n('foss.longScheduleDesign.form.OuterCar.lineNo.lable'),//'线路名称',
				xtype:'combobox',
			    store:scheduling.longScheduleDesign.lineNoStore,
			    queryMode: 'local',
			    displayField: 'lineName',
			    valueField: 'lineNo',
			    editable:false,
			    forceSelection:true,
			    triggerAction:'all',
			    allowBlank:false,
			    columnWidth:.25		
			},{
				name: 'lineNoExpress',//是否快递线路
				readOnly:true,
				hidden:true
			},{
				xtype:'numberfield',
				fieldLabel: scheduling.longScheduleDesign.i18n('foss.longScheduleDesign.form.OuterCar.frequencyNo.lable'),//'班次',
				name: 'frequencyNo',
				allowBlank:false,
				hideTrigger :true,
				columnWidth:.25,
				maxLength:20,
				minValue:1,
				listeners:{
					blur:function(txtField,eOpts ){							
						var form =this.up('form').getForm();
						if(!Ext.isEmpty(txtField.getValue())&& !Ext.isEmpty(form.findField('lineNo').getValue())){								
							scheduling.longScheduleDesign.queryDepartTimeByLineNoAndFrequenceNo(this.up('form'));
						}
										
					}
				},
				validator:function(val){
					if(!Ext.isEmpty(val)){
						if(Ext.isNumber(val)&&val<=0){
							return scheduling.longScheduleDesign.i18n('foss.shortScheduleDesign.form.OuterCar.frequencyNo.validator.tip');//'班次班次必须是正整数';
						}else{
							return true;
						}
					}else{
						return true;
					}
				}
			},{
				xtype:'datetimefield_date97',
				fieldLabel: scheduling.longScheduleDesign.i18n('foss.longScheduleDesign.form.OuterCar.planDepartTime.lable'),//'计划发车时间',				
				name: 'planDepartTime',
				id:'outerplanDepartTime',
				allowBlank:false,
				columnWidth:.25,
				dateConfig: {
					el: 'outerplanDepartTime-inputEl',
					dateFmt: 'yyyy-MM-dd HH:mm',
					startDate:'%y-%M-01 00:00:00'
				},
				listeners:{
					blur:function(field,epts){
						if(!Ext.isEmpty(field.getValue())){
							this.up('form').getForm().findField('platformTimeEnd').setRawValue(field.getValue());
						}							
					}
				}

			},{
				name: 'frequencyType',
				fieldLabel: scheduling.longScheduleDesign.i18n('foss.longScheduleDesign.form.OuterCar.frequencyType.lable'),//'班次类型',
				xtype:'combobox',
			    store:FossDataDictionary.getDataDictionaryStore('TFR_FREQUENCY_TYPE'),
			    queryMode: 'local',
			    displayField: 'valueName',
			    valueField: 'valueCode',
			    editable:false,
			    value:'ADD',
			    forceSelection:true,
			    triggerAction:'all',
			    allowBlank:false,
				columnWidth:.25				   
			},
			{
				name: 'isOnScheduling',
				fieldLabel: scheduling.longScheduleDesign.i18n('foss.longScheduleDesign.form.OuterCar.isOnScheduling.lable'),//'是否正班车',
				xtype:'combobox',
			    store:FossDataDictionary.getDataDictionaryStore('TFR_IS_ON_SCHEDULING'),
			    queryMode: 'local',
			    displayField: 'valueName',
			    valueField: 'valueCode',
			    editable:false,
			    readOnly:true,
			    value:'N',
			    forceSelection:true,
			    triggerAction:'all',
			    allowBlank:false,
				columnWidth:.25				   
			}			
			,{
				name: 'platformNo',
				fieldLabel: scheduling.longScheduleDesign.i18n('foss.longScheduleDesign.form.OuterCar.platformNo.lable'),//'月台号',
				xtype:'commonplatformselector',	
				valueField: 'virtualCode',
				orgCode:scheduling.longScheduleDesign.origOrgCode,
			    allowBlank:true,
			    columnWidth:.25
			},
			{
				xtype: 'rangeDateField',
				fieldLabel: scheduling.longScheduleDesign.i18n('foss.longScheduleDesign.form.OuterCar.platformTime.lable'),//'使用时间',
				fieldId: 'platformTimeStart-date97-2',
				dateType: 'datetimefield_date97',
				fromName: 'platformTimeStart',
				toName: 'platformTimeEnd',
				columnWidth:.5
			},
			{
				xtype : 'commonmotorcadeselector',
				name: 'longCarGroup',
				fieldLabel: scheduling.longScheduleDesign.i18n('foss.longScheduleDesign.form.OuterCar.longCarGroup.lable'),//'车队',
				active : 'Y',
			    allowBlank:true,
			    forceSelection:true,
			    columnWidth:.25,
			    validator:function(val){
			    	var detailDtoInfo=this.up('form').getValues();
			    	//车队字段：当班次为正常、加发时，必填
			    	if((detailDtoInfo.frequencyType=='NORMAL'||detailDtoInfo.frequencyType=='ADD')&&Ext.isEmpty(val)){
			    		return scheduling.longScheduleDesign.i18n('foss.shortScheduleDesign.form.ShortInnerCar.longCarGroup.validator.tip');//'当班次类型为正常、加发时，车队必填';
			    	}else{
			    		return true;
			    	}
			    }
			}
			]
		},{				
			xtype:'fieldset',
			title: scheduling.longScheduleDesign.i18n('foss.longScheduleDesign.form.OuterCar.fieldset.carInfo.lable'),//'车辆信息',
			defaultType: 'textfield',
			layout: 'column',
			defaults: {
				anchor: '100%'
			},
			items: [
					{
						name: 'truckType',
						fieldLabel: scheduling.longScheduleDesign.i18n('foss.longScheduleDesign.form.OuterCar.truckType.lable'),//'归属类型',
						xtype:'combobox',
					    store:FossDataDictionary.getDataDictionaryStore('TFR_TRUCK_TYPE'),
					    queryMode: 'local',
					    displayField: 'valueName',
					    valueField: 'valueCode',
					    editable:false,
					    forceSelection:true,
					    triggerAction:'all',
					    allowBlank:false,
					    value:'OUTER',
					    columnWidth:.25,
					    readOnly:true					
					}
			        ,{
			        	xtype : 'commonvehicletypeselector',
						fieldLabel: scheduling.longScheduleDesign.i18n('foss.longScheduleDesign.form.OuterCar.truckModel.lable'),//'车型',
						name: 'truckModel',
						vehicleSort : 'ownership_leased',
						columnWidth:.25,
						allowBlank:true,
						listeners:{
							blur:function(txtfield,eopts){
								if(!Ext.isEmpty(txtfield.value)){
									scheduling.longScheduleDesign.queryCarInfoByVechileNo(this.up('form'));
								}
							}
						},
					    validator:function(val){
					    	var detailDtoInfo=this.up('form').getValues();
					    	//录入班次、选择班次类型为停发时，车型字段可以不填； 
					    	if((detailDtoInfo.frequencyType=='NORMAL'||detailDtoInfo.frequencyType=='ADD')&&Ext.isEmpty(val)){
					    		return scheduling.longScheduleDesign.i18n('foss.shortScheduleDesign.form.OuterCar.truckModel.validator.tip');//'当班次类型为正常、加发时，车型必填';
					    	}else{
					    		return true;
					    	}
					    }
					},{
						xtype : 'commonleasedvehicleselector',
						fieldLabel: scheduling.longScheduleDesign.i18n('foss.longScheduleDesign.form.OuterCar.vehicleNo.lable'),//'车牌号码',
						name: 'vehicleNo',
						columnWidth:.25,
						allowBlank:true,
						maxLength:8,
						listeners:{
							blur:function(txtfield,eopts){
								if(!Ext.isEmpty(txtfield.value)){
									//查询车辆信息
									scheduling.longScheduleDesign.queryCarInfoByVechileNo(this.up('form'));
									//车牌号查询司机信息
									scheduling.longScheduleDesign.queryDriverInfoByVechileNo(this.up('form'));
								}
							}
						}
					},
					{
						xtype: 'numberfield',
						fieldLabel: scheduling.longScheduleDesign.i18n('foss.longScheduleDesign.form.OuterCar.maxLoadWeight.lable'),//'车辆载重',
						columnWidth:.25,
						value:0,
						minValue:0,
						maxLength:8,
						listeners:{
							blur:function(val,epts){
								if(Ext.isEmpty(val.getValue())){
									val.setValue(0);
								}
							}
						},
						name: 'maxLoadWeight',
						allowBlank:true
					},{
						xtype: 'numberfield',
						fieldLabel: scheduling.longScheduleDesign.i18n('foss.longScheduleDesign.form.OuterCar.actualMaxLoadWeight.lable'),//'实际最大载重',
						name: 'actualMaxLoadWeight',
						value:0,
						minValue:0,
						maxLength:8,
						listeners:{
							blur:function(val,epts){
								if(Ext.isEmpty(val.getValue())){
									val.setValue(0);
								}
							}
						},
						validator:function(val){
							var maxLoadWeight = this.up('form').getForm().findField('maxLoadWeight').getValue();
							var maxLoadWeightVal = Ext.Number.from(maxLoadWeight, 0);
							var value=Ext.Number.from(val, 0);
							if(value>maxLoadWeightVal){
								Ext.Msg.alert(scheduling.longScheduleDesign.i18n('foss.shortDeparturePlan.confirm.title.lable'),
										scheduling.longScheduleDesign.i18n('foss.longScheduleDesign.form.OuterCar.actualMaxLoadWeight.validator.tip'));//'实际最大载重不能大于车辆载重'
								return scheduling.longScheduleDesign.i18n('foss.longScheduleDesign.form.OuterCar.actualMaxLoadWeight.validator.tip');//'实际最大载重不能大于车辆载重'
							}else{
								return true;
							}
						},
						columnWidth:.25,
						allowBlank:true
					},{
						xtype: 'numberfield',
						fieldLabel: scheduling.longScheduleDesign.i18n('foss.longScheduleDesign.form.OuterCar.planLoadWeight.lable'),//'预计装载载重',
						columnWidth:.25,
						name: 'planLoadWeight',
						value:0,
						minValue:0,
						maxLength:8,
						listeners:{
							blur:function(val,epts){
								if(Ext.isEmpty(val.getValue())){
									val.setValue(0);
								}
							}
						},
						validator:function(val){
							var actualMaxLoadWeight = this.up('form').getForm().findField('actualMaxLoadWeight').getValue();
							var actualMaxLoadWeightVal = Ext.Number.from(actualMaxLoadWeight, 0);
							var value=Ext.Number.from(val, 0);
							if(value>actualMaxLoadWeightVal){
								Ext.Msg.alert(scheduling.longScheduleDesign.i18n('foss.shortDeparturePlan.confirm.title.lable'),
										scheduling.longScheduleDesign.i18n('foss.longScheduleDesign.form.OuterCar.planLoadWeight.validator.tip'));//'预计装载载重不能大于实际最大载重'
								return  scheduling.longScheduleDesign.i18n('foss.longScheduleDesign.form.OuterCar.planLoadWeight.validator.tip');//'预计装载载重不能大于实际最大载重';
							}else{
								return true;
							}
						},
						allowBlank:true
					},{
						xtype: 'numberfield',
						fieldLabel: scheduling.longScheduleDesign.i18n('foss.longScheduleDesign.form.OuterCar.truckVolume.lable'),//'车辆净空',
						name: 'truckVolume',
						value:0,
						minValue:0,
						maxLength:8,
						listeners:{
							blur:function(val,epts){
								if(Ext.isEmpty(val.getValue())){
									val.setValue(0);
								}
							}
						},
						columnWidth:.25,
						allowBlank:true
				  },{
						xtype: 'numberfield',
						fieldLabel: scheduling.longScheduleDesign.i18n('foss.longScheduleDesign.form.OuterCar.planLoadVolume.lable'),//'预计装载体积',
						columnWidth:.25,
						value:0,
						minValue:0,
						maxLength:8,
						name: 'planLoadVolume',
						listeners:{
							blur:function(val,epts){
								if(Ext.isEmpty(val.getValue())){
									val.setValue(0);
								}
							}
						},
						validator:function(val){
							var truckVolume = this.up('form').getForm().findField('truckVolume').getValue();
							var truckVolumeVal = Ext.Number.from(truckVolume, 0);
							var value=Ext.Number.from(val, 0);
							if(value>truckVolumeVal){
								Ext.Msg.alert(scheduling.longScheduleDesign.i18n('foss.shortDeparturePlan.confirm.title.lable'),
										scheduling.longScheduleDesign.i18n('foss.shortScheduleDesign.form.OuterCar.planLoadVolume.validator.tip'));//'预计装载体积不能大于车辆净空'
								return scheduling.longScheduleDesign.i18n('foss.shortScheduleDesign.form.OuterCar.planLoadVolume.validator.tip');//'预计装载体积不能大于车辆净空';
							}else{
								return true;
							}
						},
						allowBlank:true
					},{
						xtype:'textarea',
						fieldLabel: scheduling.longScheduleDesign.i18n('foss.longScheduleDesign.form.OuterCar.truckInfoNotes.lable'),//'备注',
						name: 'truckInfoNotes',
						columnWidth:1,
						height:60,
						maxLength:1000,
						allowBlank:true
			      },{
						xtype:'hidden',
						name: 'id'
			      }
					,{
						xtype:'hidden',
						name: 'truckDepartPlanId'
					}
					,{
						xtype:'hidden',
						name: 'platformDistributeId'
					}
					,{
						xtype:'hidden',
						name: 'departDate'
					}
					,{
						xtype:'hidden',
						name: 'planType',
						value:'LONG'//长途
					}]
		},{				
			xtype:'fieldset',
			title: scheduling.longScheduleDesign.i18n('foss.longScheduleDesign.form.OuterCar.fieldset.driverInfo.lable'),//'司机信息',
			defaultType: 'textfield',
			layout: 'column',
			defaults: {
				anchor: '100%'
			},
			items: [{
				xtype : 'commonleaseddriverselector',
				fieldLabel: scheduling.longScheduleDesign.i18n('foss.longScheduleDesign.form.OuterCar.driverCode1.lable'),//'司机姓名1',
				name: 'driverCode1',
				columnWidth:.25,
				allowBlank:true,
				validator:function(val){
					var driverCode2 = this.up('form').getForm().findField('driverCode2').getValue();
					var driverCode1 = this.up('form').getForm().findField('driverCode1').getValue();
					if(!Ext.isEmpty(driverCode1)&&!Ext.isEmpty(driverCode2)){
						if(driverCode2==driverCode1)
							return scheduling.longScheduleDesign.i18n('foss.longScheduleDesign.form.OuterCar.driverCode1.validator.tip');//'司机姓名1与司机姓名2重复';
						else
							return true;
					}else{
						return true;
					}							
				},
				listeners:{
					blur:function(filed,  eOpts){
						var val=filed.getValue();
						var truckType='OUTER';
						//司机查询联系方式
						scheduling.longScheduleDesign.queryDriverInfoByDriverCode(this.up('form'),'driverCode1',truckType);
						//司机查询车牌信息
						//scheduling.longScheduleDesign.queryLeasedDriverVechile(this.up('form'));						
					}
				}
			},{
				fieldLabel: scheduling.longScheduleDesign.i18n('foss.longScheduleDesign.form.OuterCar.driverPhone1.lable'),//'联系方式1',
				name: 'driverPhone1',
				columnWidth:.25,
				maxLength:200,
				allowBlank:true
			},{
				xtype : 'commonleaseddriverselector',
				fieldLabel: scheduling.longScheduleDesign.i18n('foss.longScheduleDesign.form.OuterCar.driverCode2.lable'),//'司机姓名2',
				columnWidth:.25,
				name: 'driverCode2',
				allowBlank:true,
				validator:function(val){
					var driverCode2 = this.up('form').getForm().findField('driverCode2').getValue();
					var driverCode1 = this.up('form').getForm().findField('driverCode1').getValue();
					if(!Ext.isEmpty(driverCode1)&&!Ext.isEmpty(driverCode2)){
						if(driverCode2==driverCode1)
							return scheduling.longScheduleDesign.i18n('foss.longScheduleDesign.form.OuterCar.driverCode2.validator.tip');//'司机姓名2与司机姓名1重复';
						else
							return true;	
					}else{
						return true;
					}							
				},
				listeners:{
					blur:function(filed,  eOpts){
						var val=filed.getValue();
						var truckType='OUTER';
						scheduling.longScheduleDesign.queryDriverInfoByDriverCode(this.up('form'),'driverCode2',truckType);
					}
				}					
			},{
				fieldLabel: scheduling.longScheduleDesign.i18n('foss.longScheduleDesign.form.OuterCar.driverPhone2.lable'),//'联系方式2',
				columnWidth:.25,
				maxLength:200,
				name: 'driverPhone2',
				allowBlank:true
			}]
		},{
			border : false,
			xtype : 'container',
			columnWidth:0.9,
			layout:'column',
			defaults: {
				margin:'5 0 5 10'
			},
			items : [{
						xtype : 'button',
						columnWidth:.08,
						text: scheduling.longScheduleDesign.i18n('foss.longScheduleDesign.form.OuterCar.button.cancel.lable'),//'取消',
						handler: function() {
							this.up('window').close();
						}
					},{
						border : false,
						columnWidth:.76,
						html: '&nbsp;'
					},{
						columnWidth:.08,
						xtype : 'button',
						text: scheduling.longScheduleDesign.i18n('foss.longScheduleDesign.form.OuterCar.button.save.lable'),//'保存',
						handler: function() {
							if(this.up('form').getForm().isValid()){								
								var form = this.up('form');
								//判断月台的使用时间
								var vals =form.getValues();
								//月台号不为空,则使用时间也不能为空
								if(!Ext.isEmpty(vals.platformNo)){
									if(Ext.isEmpty(vals.platformTimeStart)||Ext.isEmpty(vals.platformTimeEnd)){
										Ext.MessageBox.alert(scheduling.longScheduleDesign.i18n('foss.shortDeparturePlan.error.title.lable'),
												scheduling.longScheduleDesign.i18n('foss.shortDeparturePlan.error.platformTimeEnd.lable'));//'请填写月台使用时间'
										return;
									}
								}
								var planDepartTime = form.getForm().findField('planDepartTime').getValue();
								var platformTimeEnd = form.getForm().findField('platformTimeEnd').getRawValue();
								var platformTimeStart = form.getForm().findField('platformTimeStart').getRawValue();
								if(!Ext.isEmpty(platformTimeEnd)){
										if(Date.parse(planDepartTime)<Date.parse(platformTimeEnd)){
											Ext.MessageBox.alert(scheduling.longScheduleDesign.i18n('foss.shortDeparturePlan.error.title.lable'),
													scheduling.longScheduleDesign.i18n('foss.shortDeparturePlan.error.platformTimeEnd.tip'));//"不能保存,月台使用结束时间大于发车时间"
											return ;
									}
								}
								if(!Ext.isEmpty(platformTimeStart)){
									if(Date.parse(planDepartTime)<Date.parse(platformTimeStart)){
										Ext.MessageBox.alert(scheduling.longScheduleDesign.i18n('foss.shortDeparturePlan.error.title.lable'),
												scheduling.longScheduleDesign.i18n('foss.shortDeparturePlan.error.platformTimeStart.tip'));//"不能保存,月台使用开始时间大于发车时间"	
										return ;
									}
								}
								var planStatus=this.up('form').getForm().getRecord().data.planStatus;
								//下发
								/*if(planStatus=='RELEASE'){
									Ext.Msg.prompt('确定修改？', '下发后每一次的修改备注（选填）', function(btn, text){
									    if (btn == 'ok'){
									    	//执行保存或更新
											scheduling.longScheduleDesign.saveOrUpdateInnerSchedule(form,text);
									    }
									});
								}else{
									//执行保存或更新
									scheduling.longScheduleDesign.saveOrUpdateInnerSchedule(form,'');
								}*/
								scheduling.longScheduleDesign.saveOrUpdateInnerSchedule(form,planStatus);
							}else{
								Ext.MessageBox.alert(scheduling.longScheduleDesign.i18n('foss.shortDeparturePlan.confirm.title.lable'),
										scheduling.longScheduleDesign.i18n('foss.shortDeparturePlan.confirm.connotsave.lable'));	//"不能执行保存,请查看页面提示信息"
							}
						}
					},{
						columnWidth:.08,
						xtype : 'button',
						text: scheduling.longScheduleDesign.i18n('foss.longScheduleDesign.form.OuterCar.button.reset.lable'),//'重置',
						handler: function() {
						  var form = this.up('form').getForm();
						  scheduling.longScheduleDesign.resetField(form);
						}
					}]
}]
});

//加发外请车弹出窗
Ext.define('Foss.longScheduleDesign.Window.LongOuterCar', {
	extend:'Ext.window.Window',
	title: scheduling.longScheduleDesign.i18n('foss.longScheduleDesign.Window.LongOuterCar.title.lable'),//'加发外请车',
	modal:true,
	closeAction:'hide',
	width: 1024,	
	bodyCls: 'autoHeight',
	layout: 'auto',	
	items:[
	       scheduling.longScheduleDesign.outerCarForm=Ext.create('Foss.longScheduleDesign.form.OuterCar')
	]
});

//查询司机信息
scheduling.longScheduleDesign.queryDriverInfoByDriverCode=function(form,fieldName,truckType){
	var actionUrl=scheduling.realPath('queryDriverInfoByDriverCode.action');
	//司机编号
	var driverCode = form.getForm().findField(fieldName).getValue();	
	if(!Ext.isEmpty(driverCode)){
		var params = {
				"vo.detailDto.driverCode1":driverCode,
				"vo.detailDto.truckType":truckType
				};	
		//请求查询出车的相关排班任务信息
		Ext.Ajax.request({
			url : actionUrl,
			params:params,
			//动态创建表单，显示任务信息
			success : function(response) {
				var json = Ext.decode(response.responseText);
	        	var driver=json.vo.driver;
	        	if(!Ext.isEmpty(driver)&&!Ext.isEmpty(driver.driverPhone)){
	        		if(fieldName=='driverCode1'){
	        			form.getForm().findField('driverPhone1').setValue(driver.driverPhone);
	        		}else if (fieldName=='driverCode2'){
	        			form.getForm().findField('driverPhone2').setValue(driver.driverPhone);
	        		}
	        	}        		
			},
			exception : function(response) {
				var json = Ext.decode(response.responseText);
				Ext.MessageBox.alert(scheduling.longScheduleDesign.i18n('foss.shortDeparturePlan.error.title.lable'),json.message);
			}		
			});	
	}	
}


scheduling.longScheduleDesign.queryDriverInfoByVechileNo=function(form){
	var vals = form.getValues();
	if(!Ext.isEmpty(vals.vehicleNo)){
		var actionUrl=scheduling.realPath('queryDriverInfoByVechileNo.action');
		var params = {
					"vo.detailDto.vehicleNo":vals.vehicleNo,
					"vo.detailDto.truckType":vals.truckType				
					};	
		//请求查询出车的相关排班任务信息
		Ext.Ajax.request({
			url : actionUrl,
			params:params,
			//动态创建表单，显示任务信息
			success : function(response) {
				var json = Ext.decode(response.responseText);
				var driver=json.vo.driver;
				if(!Ext.isEmpty(driver)){
					var bform =form.getForm();
					bform.findField('driverCode1').setCombValue(driver.driverName,driver.driverCode);	
					bform.findField('driverPhone1').setValue(driver.driverPhone);	
				}
						
			},
			exception : function(response) {				
			}		
			});	
			
	}
}

//保存或更新公司内发车计划
scheduling.longScheduleDesign.saveOrUpdateInnerSchedule=function(form,planStatus){
	var actionUrl=scheduling.realPath('saveOrUpdateInnerPlanDetail.action');
	var record = form.getForm().getRecord();	
	//计划ID
	form.getForm().findField('truckDepartPlanId').setValue(scheduling.longScheduleDesign.planId);
	//计划日期
	form.getForm().findField('departDate').setValue(record.data.departDate);
	var detailDtoInfo =form.getForm().getValues();	
	//构建参数	
	var params = {vo:{detailDto:detailDtoInfo}};	
	if(form.getForm().isValid()){
		form.getEl().mask(scheduling.longScheduleDesign.i18n('foss.shortDeparturePlan.mask.waiting.tip'));	
	//请求查询出车的相关排班任务信息
	Ext.Ajax.request({
		url : actionUrl,
		jsonData:params,
		//动态创建表单，显示任务信息
		success : function(response) {
			var json = Ext.decode(response.responseText);
			var detailDto = json.vo.detailDto;
			
			//当车牌号被其他部门占用是，给出提示
			var origName = json.vo.origName;
			if(!Ext.isEmpty(origName)){
				Ext.MessageBox.alert(scheduling.longScheduleDesign.i18n('foss.longDeparturePlan.confirm.title.systemPrompt'),scheduling.longScheduleDesign.i18n('foss.longDeparturePlan.first.vehicleNo.occupy')+origName+scheduling.longScheduleDesign.i18n('foss.longDeparturePlan.last.vehicleNo.occupy'));
			}
			
			if(!Ext.isEmpty(detailDto)){
				if(!Ext.isEmpty(detailDto.platformDistributeId))
					form.getForm().findField('platformDistributeId').setValue(detailDto.platformDistributeId);
				if(!Ext.isEmpty(detailDto.id))
					form.getForm().findField('id').setValue(detailDto.id);
			}
			form.getEl().unmask( );
			//刷新列表
			//scheduling.longScheduleDesign.datailPagebar.moveFirst();
			//查询修改记录
			scheduling.longScheduleDesign.planDetailId=detailDto.id;
			//刷新日志
			scheduling.longScheduleDesign.loadUpdateLog();
			//查询发车计划
			scheduling.longScheduleDesign.datailPagebar.moveFirst();
			//信息提示
			Ext.ux.Toast.msg(scheduling.longScheduleDesign.i18n('foss.shortDeparturePlan.confirm.title.lable'), 
					scheduling.longScheduleDesign.i18n('foss.shortDeparturePlan.confirm.save.success'));//'计划保存成功!'
			//如果已经下发
			if(!Ext.isEmpty(planStatus) && planStatus=='RELEASE'){
				Ext.Msg.prompt(scheduling.longScheduleDesign.i18n('foss.shortDeparturePlan.prompt.title.lable'), 
						       scheduling.longScheduleDesign.i18n('foss.shortDeparturePlan.prompt.modify.tip'), function(btn, text){//'下发后每一次的修改备注（选填，如不需要备注，直接点否）'
				    if (btn == 'ok'){
				    	//执行保存或更新
						scheduling.longScheduleDesign.saveRemarkAfterSaveSuccess(form,text);
				    }
				});
			}
			
		},
		exception : function(response) {
			var json = Ext.decode(response.responseText);
			form.getEl().unmask( );
			Ext.MessageBox.alert(scheduling.longScheduleDesign.i18n('foss.shortDeparturePlan.error.title.lable'),json.message);
		}		
		});	
	}
}

//保存下发后的备注
scheduling.longScheduleDesign.saveRemarkAfterSaveSuccess=function(form,remark){
	var actionUrl=scheduling.realPath('saveRemarkAfterSaveSuccess.action');
	//计划明细ID
	var detailId=form.getForm().findField('id').getValue();
	//参数
	var params = {
			      "vo.detailDto.id":detailId,
				  "vo.detailDto.remark":remark
				 };	
	//执行查询
	Ext.Ajax.request({
		url : actionUrl,
		params:params,
		//动态创建表单，显示任务信息
		success : function(response) {
			var json = Ext.decode(response.responseText);
			//信息提示
			Ext.ux.Toast.msg(scheduling.longScheduleDesign.i18n('foss.shortDeparturePlan.confirm.title.lable'),
					         scheduling.longScheduleDesign.i18n('foss.shortDeparturePlan.confirm.remarkSaveSuccess.tip'));//'备注信息保存成功!'
		},
		exception : function(response) {
			var json = Ext.decode(response.responseText);
			Ext.MessageBox.alert(scheduling.longScheduleDesign.i18n('foss.shortDeparturePlan.error.title.lable'),json.message);
		}		
	});	
}

//通过"线路"、"班次"查询"发车时间"
scheduling.longScheduleDesign.queryDepartTimeByLineNoAndFrequenceNo=function(formEl){
	var form=formEl.getForm();					
	//线路
	var lineNo=form.getValues().lineNo;
	//班次
	var frequencyNo=form.getValues().frequencyNo;	
	if(lineNo != null && frequencyNo != null){
		var actionUrl=scheduling.realPath('queryDepartTimeByLineNoAndFrequenceNo.action');
		var queryParams={"vo.simDto.lineNo":lineNo,"vo.simDto.frequencyNo":frequencyNo};
		//遮罩
		formEl.getEl().mask(scheduling.longScheduleDesign.i18n('foss.shortDeparturePlan.mask.querywaiting.tip'));//("正在查询，请稍等..."); 
		Ext.Ajax.request({
			url : actionUrl,
			params:queryParams,
			success : function(response) {
				//填充车型、车辆所属车队
				var json = Ext.decode(response.responseText);
	        	var simDto=json.vo.simDto;	        	
	        	if(scheduling.longScheduleDesign.planDate){	        		        		
		        	if(form.findField('planDepartTime') != null){
		        		//计划日期
		        		var planDate = scheduling.longScheduleDesign.planDate.substring(0,10);	
		        		var departTimeShort = simDto.departTimeShort;
		        		if(!Ext.isEmpty(planDate)&&!Ext.isEmpty(departTimeShort)){		        			
		        			//组装时间
		        			var planDepartTime =planDate+' '+departTimeShort;
		        			form.findField('planDepartTime').setValue(planDepartTime);
		        			form.findField('platformTimeEnd').setRawValue(planDepartTime);
		        			//如果存在，则是正常
		        			form.findField('frequencyType').setValue('NORMAL');
		        			form.findField('isOnScheduling').setValue('Y');
		        			
		        		}
		        		
		        	}		        	
	        	}
	        	
	        	formEl.getEl().unmask( );
			},
			exception : function(response) {
				var json = Ext.decode(response.responseText);
				//如果不存在，则是加发
    			form.findField('frequencyType').setValue('ADD');
    			form.findField('isOnScheduling').setValue('N');
    			form.findField('planDepartTime').setValue('');
    			form.findField('platformTimeEnd').setRawValue('');
				Ext.MessageBox.alert(scheduling.longScheduleDesign.i18n('foss.shortDeparturePlan.confirm.title.lable'),json.message);
				formEl.getEl().unmask( );
			}		
			});
	}	
}

//外请司机查询外请车
scheduling.longScheduleDesign.queryLeasedDriverVechile=function(form){
	var vals = form.getValues();
	if(!Ext.isEmpty(vals.driverCode1)){
		var actionUrl=scheduling.realPath('queryLeasedDriverVechile.action');
		var params = {
					"vo.detailDto.driverCode1":vals.driverCode1
					};	
		//请求查询出车的相关排班任务信息
		Ext.Ajax.request({
			url : actionUrl,
			params:params,
			//动态创建表单，显示任务信息
			success : function(response) {
				var json = Ext.decode(response.responseText);
				var detailDto=json.vo.detailDto;
				if(!Ext.isEmpty(detailDto)){
					//最大载重
					var bform = form.getForm();
					//最大载重
					if(!Ext.isEmpty(detailDto.vehicleNo)){
						bform.findField('vehicleNo').setValue(detailDto.vehicleNo);	
						bform.findField('vehicleNo').fireEvent('blur',bform.findField('vehicleNo'));
					}					
				}				
			},
			exception : function(response) {
				var json = Ext.decode(response.responseText);
				Ext.MessageBox.alert(scheduling.longScheduleDesign.i18n('foss.shortDeparturePlan.error.title.lable'),json.message);
			}		
			});	
			
	}
}


//通过车牌查询车辆信息
scheduling.longScheduleDesign.queryCarInfoByVechileNo=function(form){
	var vals = form.getValues();
	if(!Ext.isEmpty(vals.vehicleNo)||!Ext.isEmpty(vals.truckModel)||!Ext.isEmpty(vals.containerNo)){
		var actionUrl=scheduling.realPath('queryVehicleByVechileNo.action');
		var params = {
					"vo.detailDto.vehicleNo":vals.vehicleNo,
					"vo.detailDto.containerNo":vals.containerNo,
					"vo.detailDto.truckType":vals.truckType,
					"vo.detailDto.origOrgCode":vals.origOrgCode,
					"vo.detailDto.truckModel":vals.truckModel,
					"vo.detailDto.planDepartTime":vals.planDepartTime
					};	
		//请求查询出车的相关排班任务信息
		Ext.Ajax.request({
			url : actionUrl,
			params:params,
			//动态创建表单，显示任务信息
			success : function(response) {
				var json = Ext.decode(response.responseText);
				var vehicle=json.vo.vehicle;
				if(!Ext.isEmpty(vehicle)){
					//最大载重
					var bform = form.getForm();
					//最大载重
					if(!Ext.isEmpty(vehicle.maxLoadWeight))
					bform.findField('maxLoadWeight').setValue(vehicle.maxLoadWeight);
					//实际最大载重
					if(!Ext.isEmpty(vehicle.actualMaxLoadWeight))
					bform.findField('actualMaxLoadWeight').setValue(vehicle.actualMaxLoadWeight);
					//车容积
					if(!Ext.isEmpty(vehicle.truckVolume))
					bform.findField('truckVolume').setValue(vehicle.truckVolume);
					//预计装载重量
					if(!Ext.isEmpty(vehicle.planLoadWeight))
					bform.findField('planLoadWeight').setValue(vehicle.planLoadWeight);
					//预计装载体积
					if(!Ext.isEmpty(vehicle.planLoadVolume))
					bform.findField('planLoadVolume').setValue(vehicle.planLoadVolume);
					//车型
					if(!Ext.isEmpty(vehicle.vehicleType)&&vehicle.vehicleType=='vehicletype_tractors'){
						if(!Ext.isEmpty(vals.containerNo)){
							bform.findField('truckModel').setCombValue(vehicle.truckModelValue,vehicle.truckModel);
						}
					}else{
						if(!Ext.isEmpty(vehicle.truckModel))
							bform.findField('truckModel').setCombValue(vehicle.truckModelValue,vehicle.truckModel);
					}					
					//月台使用时间
					if(vehicle.platformTimeStart)
						bform.findField('platformTimeStart').setRawValue(Ext.Date.format(new Date(vehicle.platformTimeStart),'Y-m-d H:i:s'));
					if(vehicle.platformTimeEnd)
						bform.findField('platformTimeEnd').setRawValue(Ext.Date.format(new Date(vehicle.platformTimeEnd),'Y-m-d H:i:s'));
					
				}				
			},
			exception : function(response) {
				var json = Ext.decode(response.responseText);
				Ext.MessageBox.alert(scheduling.longScheduleDesign.i18n('foss.shortDeparturePlan.error.title.lable'),json.message);
			}		
			});	
			
	}
}

//删除发车计划
scheduling.longScheduleDesign.deleteTruckDepartPlanDetail=function(grid,ids){
	if(!Ext.isEmpty(ids)){
		var actionUrl=scheduling.realPath('deleteTruckDepartPlanDetail.action');
		var params = {"vo.detailDto.ids":ids};	
		//请求查询出车的相关排班任务信息
		Ext.Ajax.request({
			url : actionUrl,
			params:params,
			//动态创建表单，显示任务信息
			success : function(response) {
				scheduling.longScheduleDesign.datailPagebar.moveFirst();
				Ext.MessageBox.alert(scheduling.longScheduleDesign.i18n('foss.shortDeparturePlan.confirm.title.lable'),'成功删除');			
			},
			exception : function(response) {
				var json = Ext.decode(response.responseText);
				Ext.MessageBox.alert(scheduling.longScheduleDesign.i18n('foss.shortDeparturePlan.error.title.lable'),json.message);
			}		
		});			
	}
}

//下发发车计划
scheduling.longScheduleDesign.releaseTruckDepartPlanDetail=function(grid,ids){
	if(!Ext.isEmpty(ids)){
		var actionUrl=scheduling.realPath('releaseTruckDepartPlanDetail.action');
		var params = {"vo.detailDto.ids":ids};	
		//请求查询出车的相关排班任务信息
		Ext.Ajax.request({
			url : actionUrl,
			params:params,
			//动态创建表单，显示任务信息
			success : function(response) {
				scheduling.longScheduleDesign.datailPagebar.moveFirst();
				Ext.MessageBox.alert(scheduling.longScheduleDesign.i18n('foss.shortDeparturePlan.confirm.title.lable'),'下发成功');						
			},
			exception : function(response) {
				var json = Ext.decode(response.responseText);
				Ext.MessageBox.alert(scheduling.longScheduleDesign.i18n('foss.shortDeparturePlan.error.title.lable'),json.message);
			}		
		});			
	}
}

//查询修改记录
scheduling.longScheduleDesign.loadUpdateLog=function(){
	if(scheduling.longScheduleDesign.planLog){
		scheduling.longScheduleDesign.planLogPagebar.moveFirst();
	}
}

//提示
Ext.define('Foss.longScheduleDesign.form.Tip',{
	extend: 'Ext.form.Panel',
	layout:'column',
	frame: false,
	 defaults:{
			xtype: 'textfield',
			margin:'0 5 5 5',
			anchor: '90%'					
	},	
	items:[{
		name: 'tip',							
		fieldLabel: '',
		labelSeparator: '',
		xtype: 'textareafield',
		labelWidth:70,		
		readOnly:true,
		columnWidth:.9
	}],	
	bindData : function(record){
		me=this;
		me.items.items[0].setValue(record);
	},
	constructor: function(config){		
		var me = this,
			cfg = Ext.apply({},config);
		me.callParent([cfg]);
		
	}
});

//加发公司车表单
Ext.define('Foss.longScheduleDesign.form.LongInnerCar',{
		extend: 'Ext.form.Panel',	
		id:'Foss_longScheduleDesign_form_longInnerCar_ID',
		bodyStyle:'padding:5px 5px 0',
		frame:true,
		fieldDefaults: {
			msgTarget: 'side',
			labelWidth: 90,
			margin:'5 5 5 0'
		},
		defaults: {
			anchor: '97%'
		},
		items : [{				
				xtype:'fieldset',
				title: scheduling.longScheduleDesign.i18n('foss.longScheduleDesign.form.LongInnerCar.fieldset.lineInfo.lable'),//'线路班次信息',
				defaultType: 'textfield',
				layout: 'column',
				defaults: {
					anchor: '100%'
				},
				items: [{
					xtype : 'dynamicorgcombselector',
					transferCenter:'Y',//外场
					fieldLabel: scheduling.longScheduleDesign.i18n('foss.longScheduleDesign.form.LongInnerCar.origOrgCode.lable'),//'出发部门',
					name: 'origOrgCode',
					readOnly:true,
					allowBlank:false,
					columnWidth:.25
				},{
					xtype : 'dynamicorgcombselector',
					transferCenter:'Y',//外场
					fieldLabel: scheduling.longScheduleDesign.i18n('foss.longScheduleDesign.form.LongInnerCar.destOrgCode.lable'),//'到达部门',
					name: 'destOrgCode',
					readOnly:true,
					allowBlank:false,					
					columnWidth:.25
				},{
					name: 'lineNo',
					fieldLabel: scheduling.longScheduleDesign.i18n('foss.longScheduleDesign.form.LongInnerCar.lineNo.lable'),//'线路名称',
					xtype:'combobox',
				    store:scheduling.longScheduleDesign.lineNoStore,
				    queryMode: 'local',
				    displayField: 'lineName',
				    valueField: 'lineNo',
				    editable:false,
				    forceSelection:true,
				    triggerAction:'all',
				    allowBlank:false,
				    columnWidth:.25		
				},{
					name: 'lineNoExpress',//是否快递线路
					readOnly:true,
					hidden:true
				}
				,{
					xtype:'numberfield',
					fieldLabel: scheduling.longScheduleDesign.i18n('foss.longScheduleDesign.form.LongInnerCar.frequencyNo.lable'),//'班次',
					name: 'frequencyNo',
					allowBlank:false,
					hideTrigger :true,
					maxLength:20,
					columnWidth:.25,
					minValue:1,
					listeners:{
						blur:function(txtField,eOpts ){							
							var form =this.up('form').getForm();
							if(!Ext.isEmpty(txtField.getValue())&& !Ext.isEmpty(form.findField('lineNo').getValue())){								
								scheduling.longScheduleDesign.queryDepartTimeByLineNoAndFrequenceNo(this.up('form'));
							}
											
						}
					},
					validator:function(val){
						if(!Ext.isEmpty(val)){
							if(Ext.isNumber(val)&&val<=0){
								return scheduling.longScheduleDesign.i18n('foss.shortScheduleDesign.form.OuterCar.frequencyNo.validator.tip');//'班次班次必须是正整数';
							}else{
								return true;
							}
						}else{
							return true;
						}
					}
				},{
					xtype:'datetimefield_date97',
					fieldLabel: scheduling.longScheduleDesign.i18n('foss.longScheduleDesign.form.LongInnerCar.planDepartTime.lable'),//'计划发车时间',				
					name: 'planDepartTime',
					id:'innerplanDepartTime',
					allowBlank:false,
					columnWidth:.25,
					dateConfig: {
						el: 'innerplanDepartTime-inputEl',
						dateFmt: 'yyyy-MM-dd HH:mm',
						startDate:'%y-%M-01 00:00:00'
					},
					listeners:{
						blur:function(field,epts){
							if(!Ext.isEmpty(field.getValue())){
								this.up('form').getForm().findField('platformTimeEnd').setRawValue(field.getValue());
							}							
						}
					}
				},{
					name: 'frequencyType',
					fieldLabel: scheduling.longScheduleDesign.i18n('foss.longScheduleDesign.form.LongInnerCar.frequencyType.lable'),//'班次类型',
					xtype:'combobox',
				    store:FossDataDictionary.getDataDictionaryStore('TFR_FREQUENCY_TYPE'),
				    queryMode: 'local',
				    displayField: 'valueName',
				    valueField: 'valueCode',
				    editable:false,
				    value:'ADD',
				    forceSelection:true,
				    triggerAction:'all',
				    allowBlank:false,
					columnWidth:.25				   
				}
				,{
					name: 'isOnScheduling',
					fieldLabel: scheduling.longScheduleDesign.i18n('foss.longScheduleDesign.form.LongInnerCar.isOnScheduling.lable'),//'是否正班车',
					xtype:'combobox',
				    store:FossDataDictionary.getDataDictionaryStore('TFR_IS_ON_SCHEDULING'),
				    queryMode: 'local',
				    displayField: 'valueName',
				    valueField: 'valueCode',
				    editable:false,
				    readOnly:true,
				    value:'N',
				    forceSelection:true,
				    triggerAction:'all',
				    allowBlank:false,
					columnWidth:.25				   
				},{
					xtype:'commonplatformselector',	
					name: 'platformNo',
					fieldLabel: scheduling.longScheduleDesign.i18n('foss.longScheduleDesign.form.LongInnerCar.platformNo.lable'),//'月台号',
					valueField: 'virtualCode',
					orgCode:scheduling.longScheduleDesign.origOrgCode,
				    allowBlank:true,
				    columnWidth:.25
				},
				{
					xtype: 'rangeDateField',
					fieldLabel: scheduling.longScheduleDesign.i18n('foss.longScheduleDesign.form.LongInnerCar.platformTime.lable'),//'使用时间',
					fieldId: 'platformTimeStart-date97-1',
					dateType: 'datetimefield_date97',				
					fromName: 'platformTimeStart',
					toName: 'platformTimeEnd',
					columnWidth:.5
				},
				{
					xtype : 'commonmotorcadeselector',
					forceSelection:true,
					name: 'longCarGroup',
					fieldLabel: scheduling.longScheduleDesign.i18n('foss.longScheduleDesign.form.LongInnerCar.longCarGroup.lable'),//'车队',
					active : 'Y',
				    allowBlank:true,
				    columnWidth:.25,
				    validator:function(val){
				    	var detailDtoInfo=this.up('form').getValues();
				    	//车队字段：当班次为正常、加发时，必填
				    	if((detailDtoInfo.frequencyType=='NORMAL'||detailDtoInfo.frequencyType=='ADD')&&Ext.isEmpty(val)){
				    		return scheduling.longScheduleDesign.i18n('foss.shortScheduleDesign.form.ShortInnerCar.longCarGroup.validator.tip');//'当班次类型为正常、加发时，车队必填';
				    	}else{
				    		return true;
				    	}
				    }			
				}
				]
			},{				
				xtype:'fieldset',
				title: scheduling.longScheduleDesign.i18n('foss.longScheduleDesign.form.LongInnerCar.fieldset.driverInfo.lable'),//'司机信息',
				defaultType: 'textfield',
				layout: 'column',
				defaults: {
					anchor: '100%'
				},
				items: [{
							xtype : 'commonowndriverselector',
							fieldLabel: scheduling.longScheduleDesign.i18n('foss.longScheduleDesign.form.LongInnerCar.driverCode1.lable'),//'司机姓名1',
							name: 'driverCode1',
							allowBlank:true,
							columnWidth:.25,
							validator:function(val){
								var driverCode2 = this.up('form').getForm().findField('driverCode2').getValue();
								var driverCode1 = this.up('form').getForm().findField('driverCode1').getValue();
								if(!Ext.isEmpty(driverCode1)&&!Ext.isEmpty(driverCode2)){
									if(driverCode2==driverCode1)
										return scheduling.longScheduleDesign.i18n('foss.longScheduleDesign.form.OuterCar.driverCode1.validator.tip');//'司机姓名1与司机姓名2重复';
									else
										return true;
								}else{
									return true;
								}							
							},
							listeners:{
								blur:function(filed,  eOpts){
									var val=filed.getValue();
									var truckType='OWN';
									scheduling.longScheduleDesign.queryDriverInfoByDriverCode(this.up('form'),'driverCode1',truckType);
								}
							}
						},{
							fieldLabel: scheduling.longScheduleDesign.i18n('foss.longScheduleDesign.form.LongInnerCar.driverPhone1.lable'),//'联系方式1',
							name: 'driverPhone1',
							columnWidth:.25,
							maxLength:200,
							allowBlank:true
						},{
							xtype : 'commonowndriverselector',
							fieldLabel: scheduling.longScheduleDesign.i18n('foss.longScheduleDesign.form.LongInnerCar.driverCode2.lable'),//'司机姓名2',
							name: 'driverCode2',
							columnWidth:.25,
							allowBlank:true,
							validator:function(val){
								var driverCode2 = this.up('form').getForm().findField('driverCode2').getValue();
								var driverCode1 = this.up('form').getForm().findField('driverCode1').getValue();
								if(!Ext.isEmpty(driverCode1)&&!Ext.isEmpty(driverCode2)){
									if(driverCode2==driverCode1)
										return scheduling.longScheduleDesign.i18n('foss.longScheduleDesign.form.OuterCar.driverCode2.validator.tip');//'司机姓名2与司机姓名1重复';
									else
										return true;
								}else{
									return true;
								}							
							},
							listeners:{
								blur:function(filed,  eOpts){
									var val=filed.getValue();
									var truckType='OWN';
									scheduling.longScheduleDesign.queryDriverInfoByDriverCode(this.up('form'),'driverCode2',truckType);
								}
							}					
						},{
							fieldLabel: scheduling.longScheduleDesign.i18n('foss.longScheduleDesign.form.LongInnerCar.driverPhone2.lable'),//'联系方式2',
							name: 'driverPhone2',
							maxLength:200,
							columnWidth:.25,
							allowBlank:true
						}]
			},{				
				xtype:'fieldset',
				title: scheduling.longScheduleDesign.i18n('foss.longScheduleDesign.form.LongInnerCar.fieldset.carInfo.lable'),//'车辆信息',
				defaultType: 'textfield',
				layout: 'column',
				defaults: {
					anchor: '100%'
				},
				items: [{
							name: 'truckType',
							fieldLabel: scheduling.longScheduleDesign.i18n('foss.longScheduleDesign.form.LongInnerCar.truckType.lable'),//'归属类型',
							xtype:'combobox',
						    store:FossDataDictionary.getDataDictionaryStore('TFR_TRUCK_TYPE'),
						    queryMode: 'local',
						    displayField: 'valueName',
						    valueField: 'valueCode',
						    editable:false,
						    forceSelection:true,
						    triggerAction:'all',
						    allowBlank:false,
						    value:'OUTER',
						    columnWidth:.25,
						    readOnly:true					
						},{
							xtype : 'commonvehicletypeselector',
							fieldLabel: scheduling.longScheduleDesign.i18n('foss.longScheduleDesign.form.LongInnerCar.truckModel.lable'),//'车型',
							name: 'truckModel',
							vehicleSort : 'ownership_company',
							columnWidth:.25,
							allowBlank:true,
							listeners:{
								blur:function(txtfield,eopts){
									if(!Ext.isEmpty(txtfield.value)){
										scheduling.longScheduleDesign.queryCarInfoByVechileNo(this.up('form'));
									}
								}
							},
						    validator:function(val){
						    	var detailDtoInfo=this.up('form').getValues();
						    	//录入班次、选择班次类型为停发时，车型字段可以不填； 
						    	if((detailDtoInfo.frequencyType=='NORMAL'||detailDtoInfo.frequencyType=='ADD')&&Ext.isEmpty(val)){
						    		return scheduling.longScheduleDesign.i18n('foss.shortScheduleDesign.form.OuterCar.truckModel.validator.tip');//'当班次类型为正常、加发时，车型必填';
						    	}else{
						    		return true;
						    	}
						    }
						},{
							xtype : 'commonowntruckselector',
							fieldLabel: scheduling.longScheduleDesign.i18n('foss.longScheduleDesign.form.LongInnerCar.vehicleNo.lable'),//'车牌号码',
							name: 'vehicleNo',
							allowBlank:true,
							columnWidth:.25,
							listeners:{
								blur:function(txtfield,eopts){
									if(!Ext.isEmpty(txtfield.value)){
										//获取车辆所属类型（挂车、托头...）
										scheduling.longScheduleDesign.vehicleType=txtfield.store.findRecord('vehicleNo',txtfield.value,0,false,true,true).raw.vehicleType
										scheduling.longScheduleDesign.queryCarInfoByVechileNo(this.up('form'));
									}
								}
							}
						},{
							fieldLabel: scheduling.longScheduleDesign.i18n('foss.longScheduleDesign.form.LongInnerCar.containerNo.lable'),//'货柜号',
							xtype : 'commonowntruckselector',							
							displayField : 'containerCode',// 显示名称
							valueField : 'containerCode',
							myQueryParam : 'containerCode',
							showContent : '{containerCode}&nbsp;&nbsp;{vehicleNo}',  
							name: 'containerNo',
							vehicleTypes : 'vehicletype_trailer',//车辆类型 该配置只查挂车 多个类型以,分隔
							columnWidth:.25,
							listeners:{
								blur:function(txtfield,eopts){
									var form=this.up('form').getForm();
									if(!Ext.isEmpty(txtfield.value)){
										//获取挂牌号
										var vehicleNo=txtfield.store.findRecord('containerCode',txtfield.value,0,false,true,true).data.vehicleNo;
										//设置挂牌号
										form.findField('trailerVehicleNo').setValue(vehicleNo);
										scheduling.longScheduleDesign.queryCarInfoByVechileNo(this.up('form'));
									}
								}
							}
						},{
							xtype:'textfield',
							fieldLabel:'挂牌号',//挂牌号
							name:'trailerVehicleNo',
							readOnly:true,
//							hidden:true
							columnWidth:.25
						},{
							xtype: 'numberfield',
							fieldLabel: scheduling.longScheduleDesign.i18n('foss.longScheduleDesign.form.LongInnerCar.maxLoadWeight.lable'),//'车辆载重',
							columnWidth:.25,
							value:0,
							minValue:0,
							maxLength:8,
							listeners:{
								blur:function(val,epts){
									if(Ext.isEmpty(val.getValue())){
										val.setValue(0);
									}
								}
							},
							name: 'maxLoadWeight',
							allowBlank:true
						},{
							xtype: 'numberfield',
							fieldLabel: scheduling.longScheduleDesign.i18n('foss.longScheduleDesign.form.LongInnerCar.actualMaxLoadWeight.lable'),//'实际最大载重',
							name: 'actualMaxLoadWeight',
							value:0,
							minValue:0,
							maxLength:8,
							listeners:{
								blur:function(val,epts){
									if(Ext.isEmpty(val.getValue())){
										val.setValue(0);
									}
								}
							},
							validator:function(val){
								var maxLoadWeight = this.up('form').getForm().findField('maxLoadWeight').getValue();
								var maxLoadWeightVal = Ext.Number.from(maxLoadWeight, 0);
								var value=Ext.Number.from(val, 0);
								if(value>maxLoadWeightVal){
									Ext.Msg.alert(scheduling.longScheduleDesign.i18n('foss.shortDeparturePlan.confirm.title.lable'),
											      scheduling.longScheduleDesign.i18n('foss.longScheduleDesign.form.OuterCar.actualMaxLoadWeight.validator.tip'));//'实际最大载重不能大于车辆载重'
									return scheduling.longScheduleDesign.i18n('foss.longScheduleDesign.form.OuterCar.actualMaxLoadWeight.validator.tip');//'实际最大载重不能大于车辆载重';
								}else{
									return true;
								}
							},
							columnWidth:.25,
							allowBlank:true
						},{
							xtype: 'numberfield',
							fieldLabel: scheduling.longScheduleDesign.i18n('foss.longScheduleDesign.form.LongInnerCar.planLoadWeight.lable'),//'预计装载载重',
							columnWidth:.25,
							name: 'planLoadWeight',
							value:0,
							minValue:0,
							maxLength:8,
							listeners:{
								blur:function(val,epts){
									if(Ext.isEmpty(val.getValue())){
										val.setValue(0);
									}
								}
							},
							validator:function(val){
								var actualMaxLoadWeight = this.up('form').getForm().findField('actualMaxLoadWeight').getValue();
								var actualMaxLoadWeightVal = Ext.Number.from(actualMaxLoadWeight, 0);
								var value=Ext.Number.from(val, 0);
								if(value>actualMaxLoadWeightVal){
									Ext.Msg.alert(scheduling.longScheduleDesign.i18n('foss.shortDeparturePlan.confirm.title.lable'),
											scheduling.longScheduleDesign.i18n('foss.longScheduleDesign.form.OuterCar.planLoadWeight.validator.tip'));//'预计装载载重不能大于实际最大载重'
									return scheduling.longScheduleDesign.i18n('foss.longScheduleDesign.form.OuterCar.planLoadWeight.validator.tip');//'预计装载载重不能大于实际最大载重'
								}else{
									return true;
								}
							},
							allowBlank:true
						},{
							xtype: 'numberfield',
							fieldLabel: scheduling.longScheduleDesign.i18n('foss.longScheduleDesign.form.LongInnerCar.planLoadVolume.lable'),//'预计装载体积',
							columnWidth:.25,
							value:0,
							minValue:0,
							maxLength:8,
							name: 'planLoadVolume',
							listeners:{
								blur:function(val,epts){
									if(Ext.isEmpty(val.getValue())){
										val.setValue(0);
									}
								}
							},
							validator:function(val){
								var truckVolume = this.up('form').getForm().findField('truckVolume').getValue();
								var truckVolumeVal = Ext.Number.from(truckVolume, 0);
								var value=Ext.Number.from(val, 0);
								if(value>truckVolumeVal){
									Ext.Msg.alert(scheduling.longScheduleDesign.i18n('foss.shortDeparturePlan.confirm.title.lable'),
											scheduling.longScheduleDesign.i18n('foss.shortScheduleDesign.form.OuterCar.planLoadVolume.validator.tip'));//'预计装载体积不能大于车辆净空'
									return scheduling.longScheduleDesign.i18n('foss.shortScheduleDesign.form.OuterCar.planLoadVolume.validator.tip');//'预计装载体积不能大于车辆净空'
								}else{
									return true;
								}
							},
							allowBlank:true
						},{
							xtype: 'numberfield',
							fieldLabel: scheduling.longScheduleDesign.i18n('foss.longScheduleDesign.form.LongInnerCar.truckVolume.lable'),//'车辆净空',
							name: 'truckVolume',
							value:0,
							minValue:0,
							maxLength:8,
							listeners:{
								blur:function(val,epts){
									if(Ext.isEmpty(val.getValue())){
										val.setValue(0);
									}
								}
							},
							columnWidth:.25,
							allowBlank:true
					  },{
							xtype:'textarea',
							fieldLabel: scheduling.longScheduleDesign.i18n('foss.longScheduleDesign.form.LongInnerCar.truckInfoNotes.lable'),//'备注',
							columnWidth:.25,
							name: 'truckInfoNotes',
							columnWidth:.99,
							height:60,
							maxLength:1000,
							allowBlank:true
				      }	,{
							xtype:'hidden',
							name: 'id'
				      }
						,{
							xtype:'hidden',
							name: 'truckDepartPlanId'
						}
						,{
							xtype:'hidden',
							name: 'platformDistributeId'
						}
						,{
							xtype:'hidden',
							name: 'departDate'
						}
						,{
							xtype:'hidden',
							name: 'planType',
							value:'LONG'//长途
						}
				      ]
			},{
				border : false,
				xtype : 'container',
				columnWidth:0.9,
				layout:'column',
				defaults: {
					margin:'5 0 5 10'
				},
				items : [{
							xtype : 'button',
							columnWidth:.08,
							text: scheduling.longScheduleDesign.i18n('foss.longScheduleDesign.form.LongInnerCar.button.cancel.lable'),//'取消',
							handler: function() {
								this.up('window').close();
							}
						},{
							border : false,
							columnWidth:.76,
							html: '&nbsp;'
						},{
							columnWidth:.08,
							xtype : 'button',
							text: scheduling.longScheduleDesign.i18n('foss.longScheduleDesign.form.LongInnerCar.button.save.lable'),//'保存',
							handler: function() {
								if(this.up('form').getForm().isValid( )){
									var form = this.up('form');
									//判断月台的使用时间
									var vals =form.getValues();
									//月台号不为空,则使用时间也不能为空
									if(!Ext.isEmpty(vals.platformNo)){
										if(Ext.isEmpty(vals.platformTimeStart)||Ext.isEmpty(vals.platformTimeEnd)){
											Ext.MessageBox.alert(scheduling.longScheduleDesign.i18n('foss.shortDeparturePlan.error.title.lable'),
													scheduling.longScheduleDesign.i18n('foss.shortDeparturePlan.error.platformTimeEnd.lable'));//'请填写月台使用时间'
											return;
										}
									}
									//车牌号为必填-352203-wx
									if(Ext.isEmpty(vals.vehicleNo)){
										Ext.MessageBox.alert(scheduling.longScheduleDesign.i18n('foss.shortDeparturePlan.confirm.title.lable'),
											scheduling.longScheduleDesign.i18n('foss.longDeparturePlan.vehicleNo.message'));
											return;
									}
									
									//如果输入的是托头则货柜号必须填写
									if(scheduling.longScheduleDesign.vehicleType=='vehicletype_tractors'&&Ext.isEmpty(vals.containerNo)){
										Ext.MessageBox.alert(scheduling.longScheduleDesign.i18n('foss.shortDeparturePlan.error.title.lable'),
												'请输入货柜号！');//'请输入货柜号'
										return;
									}
									var planDepartTime = form.getForm().findField('planDepartTime').getValue();
									var platformTimeEnd = form.getForm().findField('platformTimeEnd').getRawValue();
									var platformTimeStart = form.getForm().findField('platformTimeStart').getRawValue();
									if(!Ext.isEmpty(platformTimeEnd)){
											if(Date.parse(planDepartTime)<Date.parse(platformTimeEnd)){
												Ext.MessageBox.alert(scheduling.longScheduleDesign.i18n('foss.shortDeparturePlan.error.title.lable'),
														scheduling.longScheduleDesign.i18n('foss.shortDeparturePlan.error.platformTimeEnd.tip'));//"不能保存,月台使用结束时间大于发车时间"
												return ;
										}
									}
									if(!Ext.isEmpty(platformTimeStart)){
										if(Date.parse(planDepartTime)<Date.parse(platformTimeStart)){
											Ext.MessageBox.alert(scheduling.longScheduleDesign.i18n('foss.shortDeparturePlan.error.title.lable'),
													scheduling.longScheduleDesign.i18n('foss.shortDeparturePlan.error.platformTimeStart.tip'));//"不能保存,月台使用开始时间大于发车时间"	
											return ;
										}
									}
									var planStatus=this.up('form').getForm().getRecord().data.planStatus;
									//下发
									/*if(planStatus=='RELEASE'){
										Ext.Msg.prompt('确定修改？', '下发后每一次的修改备注（选填）', function(btn, text){
										    if (btn == 'ok'){
										    	//执行保存或更新
												scheduling.longScheduleDesign.saveOrUpdateInnerSchedule(form,text);
										    }
										});
									}else{
										//执行保存或更新
										scheduling.longScheduleDesign.saveOrUpdateInnerSchedule(form,'');
									}*/
									scheduling.longScheduleDesign.saveOrUpdateInnerSchedule(form,planStatus);
								}else{
									Ext.MessageBox.alert(scheduling.longScheduleDesign.i18n('foss.shortDeparturePlan.confirm.title.lable'),
											scheduling.longScheduleDesign.i18n('foss.shortDeparturePlan.confirm.connotsave.lable'))//("提示","不能执行保存,请查看页面提示信息");	
								}
							}
						},{
							columnWidth:.08,
							xtype : 'button',
							text: scheduling.longScheduleDesign.i18n('foss.longScheduleDesign.form.LongInnerCar.button.reset.lable'),//'重置',
							handler: function() {
								 var form = this.up('form').getForm();
								  scheduling.longScheduleDesign.resetField(form);
							}
						}]
	}]
});



//公司车查询可用车辆表单
Ext.define('Foss.longScheduleDesign.form.InnerCarSearch',{
	extend: 'Ext.form.Panel',
	layout:'column',	
	id:'Foss_longScheduleDesign_form_innerCarSearch_ID',
	bodyStyle:'padding:5px 5px 0 5px',	
	title:scheduling.longScheduleDesign.i18n('foss.shortScheduleDesign.form.InnerCarSearch.title.lable'),//'查询车辆',
	frame:true,
	cls:'autoHeight',
	defaultType: 'textfield',	
	defaults: {
		margin:'5 10 5 10',
		anchor: '90%',
		labelWidth:80
	},
	items: [{
		active : 'Y',
		xtype : 'commonmotorcadeselector',
		forceSelection:true,
		fieldLabel: '车队',//'车队小组',
		name: 'vo.carDto.carOwnerCode',
		//allowBlank:false,
		columnWidth:.25		
	},{
		xtype : 'commonvehicletypeselector',
		//forceSelection:true,
		emptyText:scheduling.longScheduleDesign.i18n('foss.shortScheduleDesign.form.InnerCarSearch.carStatus.all'),//'全部',
		fieldLabel: scheduling.longScheduleDesign.i18n('foss.longScheduleDesign.form.InnerCarSearch.truckModel.lable'),//'车型',
		vehicleSort : 'ownership_company',
		name: 'vo.carDto.truckModel',
		allowBlank:true,
		columnWidth:.25,
		listeners:{
			blur:function( bfield,eOpts ){
				if(Ext.isEmpty(bfield.rawValue)){
					this.up('form').getForm().findField('vo.carDto.truckModel').reset();					
				}
			}
		}		
	},
	{
		name: 'vo.carDto.carStatus',
		forceSelection:true,
		fieldLabel: scheduling.longScheduleDesign.i18n('foss.longScheduleDesign.form.InnerCarSearch.carStatus.lable'),//'车辆状态',
		emptyText: scheduling.longScheduleDesign.i18n('foss.shortScheduleDesign.form.InnerCarSearch.carStatus.all'),//'全部',
		xtype:'combobox',
	    store:FossDataDictionary.getDataDictionaryStore('TFR_VECHILE_STATUS'),
	    queryMode: 'local',
	    displayField: 'valueName',
	    valueField: 'valueCode',
	    value:'Y',
	    triggerAction:'all',
	    allowBlank:true,
		columnWidth:.25,
		listeners:{
			blur:function( bfield,eOpts ){
				if(Ext.isEmpty(bfield.rawValue)){
					this.up('form').getForm().findField('vo.carDto.carStatus').reset();					
				}
			}
		}
	}
	,{
		xtype : 'commonowntruckselector',
		fieldLabel: scheduling.longScheduleDesign.i18n('foss.longScheduleDesign.form.InnerCarSearch.vehicleNo.lable'),//'车牌号',
		name: 'vo.carDto.vehicleNo',
		allowBlank:true,
		columnWidth:.25		
	},{
		border : false,
		xtype : 'container',
		columnWidth:0.99,
		layout:'column',
		defaults: {
			margin:'5 0 5 10'
		},
		items : [{
			xtype : 'button',
			columnWidth:.08,
			text: scheduling.longScheduleDesign.i18n('foss.longScheduleDesign.form.InnerCarSearch.button.reset.lable'),//'重置',
			handler: function() {
				this.up('form').getForm().reset();
				//车队
				var depart = FossUserContext.getCurrentDept();
				if(depart.transDepartment=='Y'){
					this.up('form').getForm().findField('vo.carDto.carOwnerCode').setCombValue(depart.name,depart.code);
				}
			}
		},{
			border : false,
			columnWidth:.84,
			html: '&nbsp;'
		},{
			columnWidth:.08,
			xtype : 'button',
			text: scheduling.longScheduleDesign.i18n('foss.longScheduleDesign.form.InnerCarSearch.button.query.lable'),//'查询',
			cls:'yellow_button',
			handler: function() {
				var bform=this.up('form').getForm();
				if(bform.isValid()){
					var carOwnerCode=bform.findField('vo.carDto.carOwnerCode').getValue();
					var vehicleNo=bform.findField('vo.carDto.vehicleNo').getValue();
					if(!Ext.isEmpty(carOwnerCode)||!Ext.isEmpty(vehicleNo)){
						scheduling.longScheduleDesign.innerCarPagebar.moveFirst();
					}
					else{
						Ext.Msg.alert('提示','车队或车牌号必须填入一个！');
					}
					
				}else{
					Ext.Msg.alert('提示','请填写完必填项再查询');
				}
				
			}
		}]
	}
	]
});

//公司车查询结果列表
Ext.define('Foss.longScheduleDesign.grid.InnerCarSearchResult', {
	extend: 'Ext.grid.Panel',
	id:'Foss_longScheduleDesign_grid_innerCarSearchResult_ID',
	frame: true,
	collapsible: true,
	animCollapse: false,
	title:scheduling.longScheduleDesign.i18n('foss.longScheduleDesign.grid.InnerCarSearchResult.title.lable'),//'可用车辆列表',
	cls:'autoHeight',
	//bodyCls:'autoHeight',
	store: null,
	selModel: null,	
	bbar:null,
	autoScroll:true,
	bodyStyle:{height:60},
	columns: [{
				text: scheduling.longScheduleDesign.i18n('foss.longScheduleDesign.grid.InnerCarSearchResult.carOwnerName.lable'),//'所属车队',
				flex: 1, 
				sortable: true, 
				dataIndex: 'carOwnerName'
			},{
				text: scheduling.longScheduleDesign.i18n('foss.longScheduleDesign.grid.InnerCarSearchResult.vehicleNo.lable'),//'车牌号',
				flex: 1, 
				sortable: true, 
				dataIndex: 'vehicleNo'
			},{
				text: scheduling.longScheduleDesign.i18n('foss.longScheduleDesign.grid.InnerCarSearchResult.carStatusDesc.lable'),//'车辆状态',
				flex: 1, 
				sortable: true, 
				dataIndex: 'carStatusDesc'
			},{
				text: scheduling.longScheduleDesign.i18n('foss.longScheduleDesign.grid.InnerCarSearchResult.truckModelValue.lable'),//'车型',
				flex: 1, 
				sortable: true, 
				dataIndex: 'truckModelValue'
			},{
				text: scheduling.longScheduleDesign.i18n('foss.longScheduleDesign.grid.InnerCarSearchResult.maxLoadWeight.lable'),//'车辆载重(吨)',
				flex: 1, 
				sortable: true, 
				dataIndex: 'maxLoadWeight'
			},{
				text: scheduling.longScheduleDesign.i18n('foss.longScheduleDesign.grid.InnerCarSearchResult.truckVolume.lable'),//'车辆净空(方)',
				flex: 1, 
				sortable: true, 
				dataIndex: 'truckVolume'
			},{
				text: scheduling.longScheduleDesign.i18n('foss.longScheduleDesign.grid.InnerCarSearchResult.carOwnerGroupName.lable'),//'车队小组',
				flex: 1, 
				sortable: true, 
				dataIndex: 'carOwnerGroupName'
			}],
			constructor: function(config){
				var me = this,
				cfg = Ext.apply({}, config);				
				me.store = Ext.create('Foss.longScheduleDesign.store.InnerCar');
				me.bbar = Ext.create('Deppon.StandardPaging',{
					store:me.store,
					pageSize:5
				});
				scheduling.longScheduleDesign.innerCarPagebar=me.bbar ;
				me.selModel=Ext.create('Ext.selection.RadioModel',{
					mode:'SINGLE'});
				me.callParent([cfg]);	 
			},
			listeners:{
				select:function( rowModel,record,index,eOpts ){
					//车牌号
					var vehicleNo = record.data.vehicleNo;
					//车型
					var truckModelCode = record.data.truckModel;
					var truckModelValue = record.data.truckModelValue;
					var form = Ext.getCmp('Foss_longScheduleDesign_form_longInnerCar_ID');
					if(!Ext.isEmpty(form)){
						var record=form.getRecord();
						if(!Ext.isEmpty(record)){
							if(Ext.isEmpty(record.data.hasLeft)||record.data.hasLeft=='N'){
								form.getForm().findField('truckModel').setCombValue(truckModelValue,truckModelCode);
								form.getForm().findField('vehicleNo').setValue(vehicleNo);	
								form.getForm().findField('vehicleNo').fireEvent('blur',form.getForm().findField('vehicleNo'));
							}							
						}								
					}
				}
			}
});

//加发公司车弹出窗
Ext.define('Foss.longScheduleDesign.Window.LongInnerCar', {
	extend:'Ext.window.Window',
	title: scheduling.longScheduleDesign.i18n('foss.longScheduleDesign.Window.LongInnerCar.title.lable'),//'加发公司车',
	modal:true,
	closeAction:'hide',
	width: 1024,	
	bodyCls: 'autoHeight',
	layout: 'auto',	
	items:[
		scheduling.longScheduleDesign.innerCarSearchForm=Ext.create('Foss.longScheduleDesign.form.InnerCarSearch'),//Ext.getCmp('Foss_longScheduleDesign_form_innerCarSearch_ID')==null?Ext.create('Foss.longScheduleDesign.form.InnerCarSearch'):Ext.getCmp('Foss_longScheduleDesign_form_innerCarSearch_ID'),
		scheduling.longScheduleDesign.innerCarSearchResultGrid=Ext.create('Foss.longScheduleDesign.grid.InnerCarSearchResult'),//Ext.getCmp('Foss_longScheduleDesign_grid_innerCarSearchResult_ID')==null?Ext.create('Foss.longScheduleDesign.grid.InnerCarSearchResult'):Ext.getCmp('Foss_longScheduleDesign_grid_innerCarSearchResult_ID'),
		scheduling.longScheduleDesign.longInnerCarForm=Ext.create('Foss.longScheduleDesign.form.LongInnerCar')//Ext.getCmp('Foss_longScheduleDesign_form_longInnerCar_ID')==null?Ext.create('Foss.longScheduleDesign.form.LongInnerCar'):Ext.getCmp('Foss_longScheduleDesign_form_longInnerCar_ID'),
	],
	listeners:{
		hide:function( component, eOpts ){
			var grid=Ext.getCmp('Foss_longScheduleDesign_grid_innerCarSearchResult_ID');
			if(grid)
			grid.getStore().removeAll();
		}		
	}
});

//重置
scheduling.longScheduleDesign.resetField=function(form){
	//form.findField('lineNo').reset();
	form.findField('longCarGroup').reset();
	form.findField('isOnScheduling').reset();
	form.findField('frequencyType').reset();
	form.findField('frequencyNo').reset();
	form.findField('planDepartTime').reset();
	form.findField('platformNo').reset();
	form.findField('platformTimeStart').reset();
	form.findField('platformTimeEnd').reset();
	form.findField('driverCode1').reset();
	form.findField('driverPhone1').reset();
	form.findField('driverCode2').reset();
	form.findField('driverPhone2').reset();
	form.findField('truckModel').reset();
	form.findField('vehicleNo').reset();
	if(form.findField('containerNo'))
	form.findField('containerNo').reset();
	form.findField('maxLoadWeight').reset();
	form.findField('actualMaxLoadWeight').reset();
	form.findField('planLoadWeight').reset();
	form.findField('planLoadVolume').reset();
	form.findField('truckVolume').reset();
	form.findField('truckInfoNotes').reset();
}

//设置只读
scheduling.longScheduleDesign.readOnlyForm=function(form,isreadOnly){
	form.findField('lineNo').setReadOnly(isreadOnly);
	form.findField('longCarGroup').setReadOnly(isreadOnly);
	//form.findField('isOnScheduling').setReadOnly(isreadOnly);
	form.findField('frequencyType').setReadOnly(isreadOnly);
	form.findField('frequencyNo').setReadOnly(isreadOnly);
	form.findField('planDepartTime').setReadOnly(isreadOnly);
	form.findField('platformNo').setReadOnly(isreadOnly);
	form.findField('platformTimeStart').setReadOnly(isreadOnly);
	form.findField('platformTimeEnd').setReadOnly(isreadOnly);
	form.findField('driverCode1').setReadOnly(isreadOnly);
	form.findField('driverPhone1').setReadOnly(isreadOnly);
	form.findField('driverCode2').setReadOnly(isreadOnly);
	form.findField('driverPhone2').setReadOnly(isreadOnly);
	form.findField('truckModel').setReadOnly(isreadOnly);
	form.findField('vehicleNo').setReadOnly(isreadOnly);
	if(form.findField('containerNo'))
	form.findField('containerNo').setReadOnly(isreadOnly);
	form.findField('maxLoadWeight').setReadOnly(isreadOnly);
	form.findField('actualMaxLoadWeight').setReadOnly(isreadOnly);
	form.findField('planLoadWeight').setReadOnly(isreadOnly);
	form.findField('planLoadVolume').setReadOnly(isreadOnly);
	form.findField('truckVolume').setReadOnly(isreadOnly);
	form.findField('truckInfoNotes').setReadOnly(isreadOnly);
}
//发车计划日志
Ext.define('Foss.longScheduleDesign.model.PlanLog', {
	extend: 'Ext.data.Model',
	idgen: 'uuid',
	idProperty : 'uuid',
	fields: [
		{name: 'id',type:'string'},//ID
		{name: 'uuid',type:'string'},//UUID
		{name: 'createTime',type:'date',
			convert: function(value) {
				if (value != null) {
					var date = new Date(value);
					return Ext.Date.format(date,'Y-m-d H:i:s');
				} else {
					return null;
				}
			}},//创建时间
		{name: 'createUserName',type:'string'},//操作人
		{name: 'logType',type:'string'},//日志类型
		{name: 'modifyContent', type: 'string'}//修改内容
	]
});

//发车计划日志数据源
Ext.define('Foss.longScheduleDesign.store.PlanLog',{
	extend: 'Ext.data.Store',
	//绑定一个模型
	model: 'Foss.longScheduleDesign.model.PlanLog',
	pageSize:8,
	proxy: {
		type: 'ajax',
		url:scheduling.realPath('queryUpdateLogs.action'),
		actionMethods:'post',
		reader: {
			type: 'json',
			root: 'vo.logList',
			totalProperty : 'totalCount'
		}
	},
	listeners: {
		beforeload : function(store, operation, eOpts) {
			//计划ID
			var queryParams = {
					'vo.detailDto.truckDepartPlanId':scheduling.longScheduleDesign.planId,
					'vo.detailDto.id':scheduling.longScheduleDesign.planDetailId					
					};
			
			Ext.apply(operation, {
				params : queryParams
			});	
		},load:function(){
			//清除planDetailId
			//scheduling.longScheduleDesign.planDetailId=null;
		}
	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});



//发车计划日志列表
Ext.define('Foss.longScheduleDesign.gird.PlanLog',{
		extend: 'Ext.grid.Panel',
		title:scheduling.longScheduleDesign.i18n('foss.longScheduleDesign.gird.PlanLog.title.lable'),//'发车计划修改记录',
		frame:true,
		animCollapse: false,		
		cls:'autoHeight',
		bodyCls:'autoHeight',
		store: null,
		selModel: null,	
		autoScroll:true,
		columns: [{
				text: scheduling.longScheduleDesign.i18n('foss.longScheduleDesign.gird.PlanLog.createTime.lable'),//'修改时间',
				minWidth:130,
				sortable: true, 
				dataIndex: 'createTime'
			},{
				text: scheduling.longScheduleDesign.i18n('foss.longScheduleDesign.gird.PlanLog.createUserName.lable'),//'操作人',
				minWidth:100,
				sortable: true, 
				dataIndex: 'createUserName'
			},{
				text: scheduling.longScheduleDesign.i18n('foss.longScheduleDesign.gird.PlanLog.logType.lable'),//'日志类型',
				minWidth:100,
				sortable: true, 
				dataIndex: 'logType',
				renderer:function(val){
					if(val=='AUTO'){
						return scheduling.longScheduleDesign.i18n('foss.longScheduleDesign.gird.PlanLog.logType.AUTO.lable');//'系统'
					}else{
						return scheduling.longScheduleDesign.i18n('foss.longScheduleDesign.gird.PlanLog.logType.USER.lable');//'人工备注'
					}
				}
			},{
				xtype: 'linebreakcolumn',
				text: scheduling.longScheduleDesign.i18n('foss.longScheduleDesign.gird.PlanLog.modifyContent.lable'),//'操作描述',
				flex: 1, 
				sortable: true, 
				dataIndex: 'modifyContent'
			}],
		constructor: function(config){
			var me = this,
			cfg = Ext.apply({}, config);
			me.store = Ext.create('Foss.longScheduleDesign.store.PlanLog');
			me.bbar = Ext.create('Deppon.StandardPaging',{
				store:me.store,
				pageSize:5
			});
			scheduling.longScheduleDesign.planLogPagebar=me.bbar;
			me.callParent([cfg]);	 
		}
		

});


//查询货量预测信息
scheduling.longScheduleDesign.queryForecastInfo=function(){
	var actionUrl=scheduling.realPath('queryForecastInfo.action');
	var queryParams={
						'vo.planDto.origOrgCode':scheduling.longScheduleDesign.origOrgCode,// 出发部门
						'vo.planDto.destOrgCode':scheduling.longScheduleDesign.destOrgCode,//到达部门
						'vo.planDto.planDate':scheduling.longScheduleDesign.planDate,//计划日期
						'vo.planDto.planType':'LONG'//长途发车计划
					};
	//验证并初始化数据
	Ext.Ajax.request({
		url : actionUrl,
		params:queryParams,
		//动态创建表单，显示任务信息
		success : function(response) {
			var json = Ext.decode(response.responseText);
			//设置表单值
			if(json.vo.forecastDto){
				var record=Ext.create('Foss.longScheduleDesign.model.ForecastForPlan',json.vo.forecastDto)
				scheduling.longScheduleDesign.forecastInfo.loadRecord(record);
				//出发部门名称
				scheduling.longScheduleDesign.origOrgName=json.vo.forecastDto.origOrgName;
				//到达部门名称
				scheduling.longScheduleDesign.destOrgName=json.vo.forecastDto.destOrgName;
			}
			
		},
		exception : function(response) {
			var json = Ext.decode(response.responseText);
			Ext.MessageBox.alert(scheduling.longScheduleDesign.i18n('foss.shortDeparturePlan.error.title.lable'),json.message);
		}		
	});	
}

//转换归属类型
scheduling.longScheduleDesign.changeTruckType=function(detailId,truckTypeTo){
	var actionUrl=scheduling.realPath('changeTruckType.action');
	var queryParams={
						'vo.detailDto.id':detailId,// 明细ID
						'vo.detailDto.truckType':truckTypeTo//需要变更为的归属类型						
					};
	//验证并初始化数据
	Ext.Ajax.request({
		url : actionUrl,
		params:queryParams,
		//动态创建表单，显示任务信息
		success : function(response) {
			var json = Ext.decode(response.responseText);
			//变成成功
			scheduling.longScheduleDesign.datailPagebar.moveFirst();
		},
		exception : function(response) {
			var json = Ext.decode(response.responseText);
			Ext.MessageBox.alert(scheduling.longScheduleDesign.i18n('foss.shortDeparturePlan.error.title.lable'),json.message);
		}		
	});	
}

//查询本线路 当前日期 班次的可用的最大值
scheduling.longScheduleDesign.queryMaxfrequencyNo=function(form){
	var actionUrl=scheduling.realPath('queryMaxfrequencyNo.action');
	var lineNo=form.findField('lineNo').getValue();
	//线路不为空
	if(!Ext.isEmpty(lineNo)){
		var queryParams={
							'vo.detailDto.origOrgCode':scheduling.longScheduleDesign.origOrgCode,// 出发部门
							'vo.detailDto.destOrgCode':scheduling.longScheduleDesign.destOrgCode,//到达部门
							'vo.detailDto.departDate':scheduling.longScheduleDesign.planDate,//计划日期
							'vo.detailDto.lineNo':lineNo,//线路编码
							'vo.detailDto.planType':'LONG'//长途发车计划
						};
		//验证并初始化数据
		Ext.Ajax.request({
			url : actionUrl,
			params:queryParams,
			//动态创建表单，显示任务信息
			success : function(response) {
				var json = Ext.decode(response.responseText);
				//设置表单值
				if(json.vo.detailDto){
					var detailDto=json.vo.detailDto;
					//班次号
					var frequencyNo = detailDto.frequencyNo;
					form.findField('frequencyNo').setValue(frequencyNo);
					//触发移除时间 
					form.findField('frequencyNo').fireEvent('blur',form.findField('frequencyNo'));
				}				
			},
			exception : function(response) {
				var json = Ext.decode(response.responseText);			
			}		
		});	
	}	
}

//出发部门到达部门查询月台号
scheduling.longScheduleDesign.queryPlatformNo=function(form){
	var actionUrl=scheduling.realPath('queryPlatformNo.action');
	var queryParams={
						'vo.detailDto.origOrgCode':form.findField('origOrgCode').getValue(),
						'vo.detailDto.destOrgCode':form.findField('destOrgCode').getValue()
					}
		//验证并初始化数据
		Ext.Ajax.request({
			url : actionUrl,
			params:queryParams,
			//动态创建表单，显示任务信息
			success : function(response) {
				var json = Ext.decode(response.responseText);
				//设置表单值
				if(json.vo.detailDto){
					var detailDto=json.vo.detailDto;
					if(!Ext.isEmpty(detailDto.platformNoCode)&&!Ext.isEmpty(detailDto.platformNo))
					form.findField('platformNo').setCombValue(detailDto.platformNoCode,detailDto.platformNo);
				}
				
			},
			exception : function(response) {
				var json = Ext.decode(response.responseText);			
			}		
		});	
	
}

//显示于标签页
Ext.onReady(function() {
		//货量预测信息
		var forecastInfo=Ext.getCmp('Foss_longScheduleDesign_form_shipmentsForecast_ID')==null?Ext.create('Foss.longScheduleDesign.form.ShipmentsForecast'):Ext.getCmp('Foss_longScheduleDesign_form_shipmentsForecast_ID');
		scheduling.longScheduleDesign.forecastInfo=forecastInfo;
		//发车计划明细
		var planDetail=Ext.create('Foss.longScheduleDesign.gird.PlanDetail');
		scheduling.longScheduleDesign.planDetail=planDetail;
		//合法记录日志
		var mergeLog=Ext.getCmp('Foss_longScheduleDesign_gird_mergeLog_ID')==null?Ext.create('Foss.longScheduleDesign.gird.MergeLog'):Ext.getCmp('Foss_longScheduleDesign_gird_mergeLog_ID');
		scheduling.longScheduleDesign.mergeLog =	mergeLog;
		//发车计划日志表格
		var planLog = Ext.create('Foss.longScheduleDesign.gird.PlanLog');
		scheduling.longScheduleDesign.planLog = planLog;
		//显示查询发车计划界面
		Ext.create('Ext.panel.Panel',{
		id:'T_scheduling-longScheduleDesignIndex_content',
		cls:"panelContentNToolbar",
		bodyCls:'panelContent-body',
		layout:'auto',
		margin:'0 0 0 0',
		items : [forecastInfo,mergeLog,planDetail,planLog],
		renderTo: 'T_scheduling-longScheduleDesignIndex-body'
	});	
	//查询发车计划
	scheduling.longScheduleDesign.datailPagebar.moveFirst();
	//查询合发记录
	scheduling.longScheduleDesign.loadMergeLog();
	//查询修改记录
	scheduling.longScheduleDesign.loadUpdateLog();
	//查询货量预测信息
	scheduling.longScheduleDesign.queryForecastInfo();
});