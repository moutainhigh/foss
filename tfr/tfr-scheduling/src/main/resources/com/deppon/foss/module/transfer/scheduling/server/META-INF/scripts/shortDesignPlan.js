//package Foss.shortScheduleDesign
//发车计划明细模型
Ext.define('Foss.shortScheduleDesign.model.PlanDetail', {
	extend: 'Ext.data.Model',
	fields: [
		{name: 'id',type:'string'},//ID
		{name: 'truckDepartPlanId',type:'string'},//发车计划ID
		{name: 'planType',type:'string'},//计划类型
		{name: 'longCarGroup',type:'string'},//车队编码
		{name: 'carGroupName',type:'string'},//车队名称
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
		{name: 'platformDistributeId',type:'string'},//月台虚拟code
		{name: 'platformNo',type:'string'},//月台号编码
		{name: 'platformNoCode',type:'string'},//月台号
		{name: 'isOnScheduling',type:'string'},//是否正班车
		{name: 'frequencyType',type:'string'},//班次类型
		{name: 'truckModel',type:'string'},//车型编码
		{name: 'truckModelValue',type:'string'},//车型
		{name: 'truckType',type:'string'},//车辆归属类型 
		{name: 'truckArriveTime',type:'date'},//车辆报道时间
		{name: 'containerNo',type:'string'},//货柜号
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
Ext.define('Foss.shortScheduleDesign.model.ForecastForPlan', {
	extend: 'Ext.data.Model',
	fields: [
	         {name: 'origOrgName',type:'string'},//	   出发部门名称      
        	 {name: 'destOrgName', type: 'string'},//到达部门名称
        	 {name: 'origOrgCode', type: 'string'},//出发部门编码
        	 {name: 'destOrgCode', type: 'string'},//到达部门编码
        	 {name: 'isIssue', type: 'string'},//是否异常
        	 {name: 'notes', type: 'string'},//备注
        	 {name: 'departDate', type: 'date',//发车日期
     			convert: function(value) {
    				if (value != null) {
    					var date = new Date(value);
    					return Ext.Date.format(date,'Y-m-d');
    				} else {
    					return null;
    				}
    			}},
			{name: 'forecastVolumeTotal', type: 'float'},//预测货物体积
			{name: 'forecastWeightTotal', type: 'float'},//预测货物重量
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
Ext.define('Foss.shortScheduleDesign.model.LineNo', {
	extend: 'Ext.data.Model',
	fields: [
	         {name: 'lineNo',type:'string'},//线路虚拟code	         
        	 {name: 'lineName', type: 'string'},//线路名称
	         {name: 'lineNoExpress',type: 'string'}//是否快递线路
	        ]
});

//线路模型
Ext.define('Foss.shortScheduleDesign.model.CarGroup', {
	extend: 'Ext.data.Model',
	fields: [
	         {name: 'code',type:'string'},//长途车队CODE
	         {name: 'name',type:'string'}//长途车队名		
	         ]
});


//公司车辆模型
Ext.define('Foss.shortScheduleDesign.model.InnerCar', {
	extend: 'Ext.data.Model',
	idgen: 'uuid',
	idProperty : 'uuid',
	fields: [
		{name: 'id',type:'string'},//ID
		{name: 'uuid',type:'string'},//UUID
		{name: 'carOwnerName',type:'string'},//所属车队
		{name: 'carOwnerCode',type:'string'},//所属车队CODE
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
Ext.define('Foss.shortScheduleDesign.store.PlanDetail',{
	extend: 'Ext.data.Store',
	//绑定一个模型
	model: 'Foss.shortScheduleDesign.model.PlanDetail',
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
			var queryParams = {'vo.planDto.id':scheduling.shortScheduleDesign.planId};		
			Ext.apply(operation, {
				params : queryParams
			});
		},load:function( store, records,successful,operation,eOpts ){
			if(Ext.isEmpty(records))
			Ext.ux.Toast.msg(scheduling.shortScheduleDesign.i18n('foss.shortDeparturePlan.toast.msg.title.lable'),scheduling.shortScheduleDesign.i18n('foss.shortDeparturePlan.toast.msg.empty.lable'));//('提示信息', '查询结果为空!');
		},datachanged:function(store,eOpts ){
			//车辆合计
			var cnt=store.getCount( ) ;
			//载重合计	 实际最大载重求和
			var weightTotal=0.00;
			//载重缺口
			var gapWeightTotal=0.00;
			//体积合计 truckVolume 车容积
			var volumeTotal=0.00;
			//体积缺口
			var gapVolumeTotal=0.00;
			var carTotal=0;
			//循环计算重量、体积求和
			if(!Ext.isEmpty(store)){
				for(var i=0;i<cnt;i++){
					//记录
					var record = store.getAt(i);
					//停发情况不统计进入
					//非停发车辆数
					if(!Ext.isEmpty(record.data.vehicleNo)&&!Ext.isEmpty(record.data.frequencyType)&&record.data.frequencyType!='SUSPEND'){
						//实际最大载重求和
						if(!Ext.isEmpty(record.data.planLoadWeight))
							weightTotal = weightTotal+record.data.planLoadWeight;
						//车容积求和
						if(!Ext.isEmpty(record.data.planLoadVolume))
							volumeTotal = volumeTotal+record.data.planLoadVolume;
						carTotal=carTotal+1;
					}
									
				}
			}
			var from = Ext.getCmp('Foss_shortScheduleDesign_form_shipmentsForecast_ID');
			var fromRecord = from.getRecord( );
			//预测重量
			var forcastWeightTotal =0.00;
			//预测体积
			var forcastVolumeTotal =0.00;
			if(!Ext.isEmpty(fromRecord)){
				//获取总重量
				if(!Ext.isEmpty(fromRecord.data.forecastWeightTotal))
				 forcastWeightTotal = fromRecord.data.forecastWeightTotal;
				//计算重量缺口
				gapWeightTotal = forcastWeightTotal-weightTotal;
				//获取总体积
				if(!Ext.isEmpty(fromRecord.data.forecastVolumeTotal))
				 forcastVolumeTotal = fromRecord.data.forecastVolumeTotal;
				//计算体积缺口
				gapVolumeTotal = forcastVolumeTotal-volumeTotal;
			}
			
			if(scheduling.shortScheduleDesign.planDetail){
				var textArray = scheduling.shortScheduleDesign.planDetail.getDockedItems('toolbar')[1].items.items;
				//车辆合计
				textArray[0].setValue(carTotal);
				textArray[1].setValue(volumeTotal);
				textArray[2].setValue(gapVolumeTotal);
				textArray[3].setValue(weightTotal);
				textArray[4].setValue(gapWeightTotal);
				
			}
			
			
		}
	}
});



//查询合发记录
scheduling.shortScheduleDesign.loadMergeLog = function(){	
	if(scheduling.shortScheduleDesign.mergeLog){
		scheduling.shortScheduleDesign.mergeLog.getStore().load();
	}
}

//公司车辆数据源
Ext.define('Foss.shortScheduleDesign.store.InnerCar',{
	extend: 'Ext.data.Store',
	//绑定一个模型
	model: 'Foss.shortScheduleDesign.model.InnerCar',
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
			var searchForm = Ext.getCmp('Foss_shortScheduleDesign_form_innerCarSearch_ID');
			if(searchForm && searchForm.getForm().isValid( )){
				var queryParams = searchForm.getValues();				
				Ext.apply(operation, {
					params : queryParams
				});	
			}			
		},load:function( store, records,successful,operation,eOpts ){
			if(Ext.isEmpty(records))
				Ext.ux.Toast.msg(scheduling.shortScheduleDesign.i18n('foss.shortDeparturePlan.toast.msg.title.lable'),scheduling.shortScheduleDesign.i18n('foss.shortDeparturePlan.toast.msg.empty.lable'));//('提示信息', '查询结果为空!');
			}
	}
});


//线路列表store	
Ext.define('Foss.shortScheduleDesign.store.LineNoCombox',{
	extend: 'Ext.data.Store',
	model: 'Foss.shortScheduleDesign.model.LineNo',
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
			if(!Ext.isEmpty(scheduling.shortScheduleDesign.origOrgCode)&&!Ext.isEmpty(scheduling.shortScheduleDesign.destOrgCode)){					
				var queryParams={
								'vo.detailDto.origOrgCode':scheduling.shortScheduleDesign.origOrgCode,
								'vo.detailDto.destOrgCode':scheduling.shortScheduleDesign.destOrgCode
								}
				Ext.apply(operation, {
					params : queryParams
				});
			}			
	}
}
});

//线路列表store	
Ext.define('Foss.shortScheduleDesign.store.CarGroupCombox',{
	extend: 'Ext.data.Store',
	model: 'Foss.shortScheduleDesign.model.CarGroup',
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
Ext.define('Foss.shortScheduleDesign.store.PlatformCombox',{
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
Ext.define('Foss.shortScheduleDesign.form.ShipmentsForecast',{
	extend: 'Ext.form.Panel',
	id:'Foss_shortScheduleDesign_form_shipmentsForecast_ID',
	layout:'column',	
	bodyStyle:'padding:5px 5px 0 5px',
	title:scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.form.ShipmentsForecast.title.lable'),//'短途发车计划及货量信息',
	frame: true,
	fieldDefaults: {
		msgTarget: 'side',
		labelWidth: 90,
		margin:'5 5 5 0'
	},
	defaults: {
		anchor: '99%'
	},
	items: [{				
			xtype:'fieldset',
			title: scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.form.ShipmentsForecast.fieldset.forecastInfo.lable'),//'货量预测信息',
			defaultType: 'textfield',
			layout: 'column',
			defaults: {
				anchor: '100%'
			},
			items: [{
						fieldLabel: scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.form.ShipmentsForecast.origOrgName.lable'),//'出发部门',
						name: 'origOrgName',
						readOnly :true,
						columnWidth:.3		
					},{
						fieldLabel: scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.form.ShipmentsForecast.destOrgName.lable'),//'到达部门',
						name: 'destOrgName',
						
						readOnly :true,
						columnWidth:.3	
					},{
						fieldLabel: scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.form.ShipmentsForecast.forecastTime.lable'),//'货量预测时间',
						name: 'forecastTime',
						readOnly :true,
						columnWidth:.3	
				    },{
						fieldLabel: scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.form.ShipmentsForecast.forecastVolumeTotal.lable'),//'总货物体积',
						name: 'forecastVolumeTotal',
						readOnly :true,
						columnWidth:.3	
					},{
						fieldLabel: scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.form.ShipmentsForecast.forecastWeightTotal.lable'),//'预测货物总重量',
						name: 'forecastWeightTotal',
						labelWidth:110,
						readOnly :true,
						columnWidth:.3	
					}]
		  },
		  {				
			xtype:'fieldset',
			title: scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.form.ShipmentsForecast.fieldset.elseInfo.lable'),//'其他信息',
			defaultType: 'textfield',
			layout: 'column',
			defaults: {
				anchor: '100%'
			},
			items: [{
						xtype: 'datefield',
						fieldLabel: scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.form.ShipmentsForecast.departDate.lable'),//'发车日期',
						name: 'departDate',
						format:'Y-m-d',		
						readOnly :true,
						columnWidth:.3		
					},{
						fieldLabel: scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.form.ShipmentsForecast.isIssue.lable'),//'是否异常',
						columnWidth:.3,
						xtype: 'radiogroup',
						layout:'column',
						style:{padding:'0 0 0 0'},
						readOnly:true,
						items: [
							{ boxLabel: scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.form.ShipmentsForecast.isIssue.YES.lable'), name: 'isIssue', inputValue: 'Y' ,columnWidth:.5},
							{ boxLabel: scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.form.ShipmentsForecast.isIssue.NO.lable'), name: 'isIssue', inputValue: 'N',columnWidth:.5,checked: true}
						]
					},{
						xtype:'textarea',
						fieldLabel: scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.form.ShipmentsForecast.notes.lable'),//'备注信息',
						name: 'notes',
						height:60,
						columnWidth:.9,
						validator:function(val){
							if(!Ext.isEmpty(val)&&val.length>1000){
								return scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.form.ShipmentsForecast.notes.validator.tip');//"备注信息长度不能大于1000";
							}else{
								return true;
							}
						}
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
							text: scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.form.ShipmentsForecast.button.back.lable'),//'返回',
							handler: function() {
								//激活标签
								var Panel=Ext.getCmp('mainAreaPanel');
								var newTab=Panel.child('#T_scheduling-shortDeparturePlanIndex');
								Panel.setActiveTab(newTab);	
								//执行查询
								if(scheduling.shortDeparturePlan.pagingBar){
									scheduling.shortDeparturePlan.pagingBar.moveFirst();
								}								
							}
						},{
							border : false,
							columnWidth:.82,
							html: '&nbsp;'
						},{
							columnWidth:.1,
							xtype : 'button',
							text: scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.form.ShipmentsForecast.button.save.lable'),//'保存计划',
							handler: function() {
								//保存计划
								scheduling.shortScheduleDesign.updatePlanRemark(this.up('form'));
							}
						}]
				}]
		}
			]	   
});


//短信息表单
Ext.define('Foss.shortScheduleDesign.form.ShortMessageDuplicate',{
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
		fieldLabel:  scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.form.ShortMessageDuplicate.remark.lable'),//'短信息内容',
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
			text: scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.form.ShortMessageDuplicate.button.duplicate.lable'),//'复制',
			plugins: Ext.create('Deppon.ux.ZeroClipboardPlugin', {
				targetCmpName: 'remark'
			})

		}]
	}]	   
});

//短信复制窗口
Ext.define('Foss.shortScheduleDesign.Window.ShortMessageDuplicate', {
	extend:'Ext.window.Window',
	id:'Foss_shortScheduleDesign_window_shortMessageDuplicate_ID',
	title: scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.form.ShortMessageDuplicate.remark.lable'),//'短信息内容',
	modal:true,
	closeAction:'hide',
	width: 400,	
	bodyCls: 'autoHeight',
	layout: 'auto',	
	items:[
		Ext.create('Foss.shortScheduleDesign.form.ShortMessageDuplicate')
	]
});



//班车列表信息表格
Ext.define('Foss.shortScheduleDesign.gird.PlanDetail', {
	extend: 'Ext.grid.Panel',
	animCollapse: false,
	title:scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.gird.PlanDetail.title.lable'),//'班车列表',
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
		text: scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.gird.PlanDetail.actioncolumn.lable'),//'操作',
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
			if(record.data.hasLeft=='Y'){
				this.items[3].iconCls='';
			}else{
				this.items[3].iconCls='foss_icons_tfr_changeCarStyle';
			}
		},
		items: [{
                tooltip: scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.gird.PlanDetail.tooltip.copy.lable'),//'拷贝短信文本',
				iconCls:'deppon_icons_notice',
				width:20,
                handler: function(grid, rowIndex, colIndex) {	
					var record=grid.getStore().getAt(rowIndex).data;	
					var messageWin = Ext.getCmp('Foss_shortScheduleDesign_window_shortMessageDuplicate_ID');
					if(messageWin==null)
						messageWin=Ext.create('Foss.shortScheduleDesign.Window.ShortMessageDuplicate');
						var content,remark='线路:'+record.lineName+',班次：'+record.frequencyNo+',车辆：'+record.vehicleNo+',司机：'+record.driverName1+',计划发车时间:'+record.planDepartTime;
						content={remark:remark};
						messageWin.items.items[0].getForm().setValues(content);
						messageWin.show();
                }
            },{
                tooltip: scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.gird.PlanDetail.tooltip.edit.lable'),//'编辑',
				iconCls:'deppon_icons_edit',
				width:20,
                handler: function(grid, rowIndex, colIndex) {		
					var record=grid.getStore().getAt(rowIndex);
					if(record.data.truckType == 'OWN'){
						//判断是公司还是外请车，弹出相应的编辑框
						var innerCarWin; 
						if(Ext.isEmpty(scheduling.shortScheduleDesign.innerCarWin)){
							innerCarWin=Ext.create('Foss.shortScheduleDesign.Window.ShortInnerCar');
							scheduling.shortScheduleDesign.innerCarWin=innerCarWin;
						}else{
							innerCarWin=scheduling.shortScheduleDesign.innerCarWin;
						}		
							var form=innerCarWin.items.items[2].getForm();
							//加载数据
							form.loadRecord(record);
							//如果已出发，则不允许修改
							if(record.data.hasLeft=='Y'){
								scheduling.shortScheduleDesign.readOnlyForm(form,true,'EDIT');
								innerCarWin.items.items[2].query('button')[1].disable();
							}else{
								scheduling.shortScheduleDesign.readOnlyForm(form,false,'EDIT');
								innerCarWin.items.items[2].query('button')[1].enable(true);
							}
							//线路列表
							//form.findField('lineNo').getStore().load();
							//是否快递线路
							scheduling.shortScheduleDesign.lineNoStore.load(
									function(records, operation, success) {
									    form.findField('lineNo').rawValue=records[0].data.lineName;
									    form.findField('lineNo').lastValue=records[0].data.lineNo;
									    form.findField('lineNo').setValue(records[0].data.lineNo);
									    form.findField('lineNoExpress').setValue(records[0].data.lineNoExpress);
									}		
							);
							//设置标题
							innerCarWin.setTitle(scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.gird.PlanDetail.action.editInner.lable'));//('编辑公司车');	
							//显示
							innerCarWin.show();					
														
							//出发部门名称
							if(record.data.origOrgCode)
							form.findField('origOrgCode').setCombValue(scheduling.shortScheduleDesign.origOrgName,record.data.origOrgCode);
							//到达部门名称
							if(record.data.destOrgCode)
							form.findField('destOrgCode').setCombValue(scheduling.shortScheduleDesign.destOrgName,record.data.destOrgCode);
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
							//司机
							if(record.data.driverCode1)
							form.findField('driverCode1').setCombValue(record.data.driverName1,record.data.driverCode1);
							//司机2
							if(record.data.driverCode2)
							form.findField('driverCode2').setCombValue(record.data.driverName2,record.data.driverCode2);
							
							//线路列表
							if(record.data.lineNo){
								form.findField('lineNo').setValue(record.data.lineNo);
							}
							//重置按钮							
							innerCarWin.items.items[2].query('button')[2].disable();
							
					}else{
						//判断是公司还是外请车，弹出相应的编辑框
							var outerCarWin;
							if(Ext.isEmpty(scheduling.shortScheduleDesign.outerCarWin)){
								outerCarWin=Ext.create('Foss.shortScheduleDesign.Window.ShortOuterCar');
								scheduling.shortScheduleDesign.outerCarWin=outerCarWin;
							}else{
								outerCarWin=scheduling.shortScheduleDesign.outerCarWin;
							}		
							var form=outerCarWin.items.items[0].getForm();
							//加载数据
							form.loadRecord(record);
							//如果已出发，则不允许修改
							if(record.data.hasLeft=='Y'){
								scheduling.shortScheduleDesign.readOnlyForm(form,true,'EDIT');
								outerCarWin.items.items[0].query('button')[1].disable(true);
							}else{
								scheduling.shortScheduleDesign.readOnlyForm(form,false,'EDIT');
								outerCarWin.items.items[0].query('button')[1].enable();
							}
							//线路列表
							//form.findField('lineNo').getStore().load();
							//是否快递线路
							scheduling.shortScheduleDesign.lineNoStore.load(
									function(records, operation, success) {
									    form.findField('lineNo').rawValue=records[0].data.lineName;
									    form.findField('lineNo').lastValue=records[0].data.lineNo;
									    form.findField('lineNo').setValue(records[0].data.lineNo);
									    form.findField('lineNoExpress').setValue(records[0].data.lineNoExpress);
									}		
							);
							//设置标题
							outerCarWin.setTitle(scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.gird.PlanDetail.action.editOuter.lable'));//('编辑外请车');
							//显示
							outerCarWin.show();
							//出发部门名称
							if(record.data.origOrgCode)
							form.findField('origOrgCode').setCombValue(scheduling.shortScheduleDesign.origOrgName,record.data.origOrgCode);
							//到达部门名称
							if(record.data.destOrgCode)
							form.findField('destOrgCode').setCombValue(scheduling.shortScheduleDesign.destOrgName,record.data.destOrgCode);
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
							//司机
							if(record.data.driverCode1)
							form.findField('driverCode1').setCombValue(record.data.driverName1,record.data.driverCode1);
							//司机2
							if(record.data.driverCode2)
							form.findField('driverCode2').setCombValue(record.data.driverName2,record.data.driverCode2);
							//线路列表
							if(record.data.lineNo){
								form.findField('lineNo').setValue(record.data.lineNo);
							}
							//重置按钮										
							outerCarWin.items.items[0].query('button')[2].disable();			
							
					}				
                }
            },{
                tooltip: scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.gird.PlanDetail.tooltip.delete.lable'),//'删除',
				iconCls:'deppon_icons_delete',
				width:20,
                handler: function(grid, rowIndex, colIndex) {	
					//grid.up('gridpanel');			
					var id=grid.getStore().getAt(rowIndex).data.id;
					var ids=new Array();
					ids.push(id);					
					if(!Ext.isEmpty(ids)){
						Ext.Msg.confirm(scheduling.shortScheduleDesign.i18n('foss.shortDeparturePlan.confirm.title.lable'), 
						scheduling.shortScheduleDesign.i18n('foss.shortDeparturePlan.confirm.confirmDelete.tip'), function(btn){
						    if (btn == 'yes'){
						    	scheduling.shortScheduleDesign.deleteTruckDepartPlanDetail(grid,ids);
						    }
						});	
					}else{
						Ext.MessageBox.alert(scheduling.shortScheduleDesign.i18n('foss.shortDeparturePlan.confirm.title.lable'),
						scheduling.shortScheduleDesign.i18n('foss.shortDeparturePlan.confirm.chooseDelete.tip'));
					}
                }
            },{
                tooltip: scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.gird.PlanDetail.tooltip.changeCarStyle.lable'),//'切换归属类型',
				iconCls:'foss_icons_tfr_changeCarStyle',
				width:20,
                handler: function(grid, rowIndex, colIndex) {
                	var row = grid.getStore().getAt(rowIndex).data;
                	var tipMsg;
                	var truckTypeTo;
                	if(row.truckType=='OWN'){
                		tipMsg=scheduling.shortScheduleDesign.i18n('foss.shortDeparturePlan.confirm.changeToOutter.tip');//'确认切换为外请车？';
                		truckTypeTo='OUTER';
                	}else{
                		tipMsg=scheduling.shortScheduleDesign.i18n('foss.shortDeparturePlan.confirm.changeToInner.tip');//'确认切换为公司车？';
                		truckTypeTo='OWN';
                	}
                	Ext.Msg.confirm(scheduling.shortScheduleDesign.i18n('foss.shortDeparturePlan.confirm.title.lable'), tipMsg, function(btn){
					    if (btn == 'yes'){
					    	scheduling.shortScheduleDesign.changeTruckType(row.id,truckTypeTo);
					    }
					});
                }
            }]
	},{
		text: scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.gird.PlanDetail.driverName1.lable'),//'司机',
		menuDisabled:true,
		minWidth:100,
		flex: 1, 
		sortable: true, 
		dataIndex: 'driverName1'
	},{
		text: scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.gird.PlanDetail.driverphone1.lable'),//'司机电话',
		menuDisabled:true,
		minWidth:100,
		flex: 1, 
		sortable: true, 
		dataIndex: 'driverPhone1'
	},{
		text: scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.gird.PlanDetail.vehicleNo.lable'),//'车牌号',
		menuDisabled:true,
		minWidth:100,
		flex: 1, 
		sortable: true, 
		dataIndex: 'vehicleNo'
	},{
		text: scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.gird.PlanDetail.truckModelValue.lable'),//'车型',
		menuDisabled:true,
		minWidth:100,
		flex: 1, 
		sortable: true, 
		dataIndex: 'truckModelValue'
		
	},{
		text: scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.gird.PlanDetail.maxLoadWeight.lable'),//'车辆载重<br>吨',
		menuDisabled:true,
		minWidth:100,
		flex: 1, 
		sortable: true, 
		dataIndex: 'maxLoadWeight'
	},{
		text: scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.gird.PlanDetail.planLoadWeight.lable'),//'预计装载载重<br>吨',
		menuDisabled:true,
		minWidth:100,
		flex: 1, 
		sortable: true, 
		dataIndex: 'planLoadWeight'
	},{
		text: scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.gird.PlanDetail.truckVolume.lable'),//'车辆净空<br>方',
		menuDisabled:true,
		minWidth:100,
		flex: 1, 
		sortable: true, 
		dataIndex: 'truckVolume'
	},{
		text: scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.gird.PlanDetail.planLoadVolume.lable'),//'预计装载体积<br>方',
		menuDisabled:true,
		minWidth:100,
		flex: 1, 
		sortable: true, 
		dataIndex: 'planLoadVolume'
	},{
		text: scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.gird.PlanDetail.frequencyType.lable'),//'发车类型',
		menuDisabled:true,
		flex: 1.2, 
		minWidth:80,
		sortable: true, 
		dataIndex: 'frequencyType',
		renderer:function(val){
			if(val=='NORMAL'){
				return scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.gird.PlanDetail.frequencyType.NORMAL.lable');//'正常';
			}
			else if(val=='SUSPEND'){
				return scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.gird.PlanDetail.frequencyType.SUSPEND.lable');//'停发';
			}			
			else if(val=='ADD'){
				return scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.gird.PlanDetail.frequencyType.ADD.lable');//'加发';
			}			
		}
	},{
		text: scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.gird.PlanDetail.lineName.lable'),//'线路名称',
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
		text: scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.gird.PlanDetail.truckType.lable'),//'归属类型',
		menuDisabled:true,
		flex: 1.2, 
		sortable: true, 
		dataIndex: 'truckType',
		minWidth:80,
		renderer:function(val){
			if(val=='OWN'){
				return scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.gird.PlanDetail.truckType.OWN.lable');//'公司车';
			}
			else if(val=='OUTER'){
				return scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.gird.PlanDetail.truckType.OUTER.lable');//'外请车';
			}			
		}		
	},{
		text: scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.gird.PlanDetail.carOwnerName.lable'),//'车辆所属车队',
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
		text: scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.gird.PlanDetail.platformNoCode.lable'),//'月台号',
		menuDisabled:true,
		minWidth:60,
		flex: 1, 
		sortable: true, 
		dataIndex: 'platformNoCode'
	},{
		text: scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.gird.PlanDetail.frequencyNo.lable'),//'班次',
		minWidth:50,
		flex: 1, 
		menuDisabled:true,
		sortable: true, 
		dataIndex: 'frequencyNo'
	},{
		text: scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.gird.PlanDetail.planDepartTime.lable'),//'计划发车时间',
		menuDisabled:true,
		minWidth:140,
		flex: 1, 
		sortable: true, 
		dataIndex: 'planDepartTime'
	},{
		text: scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.gird.PlanDetail.driverName2.lable'),//'司机2',
		menuDisabled:true,
		minWidth:100,
		flex: 1, 
		sortable: true, 
		dataIndex: 'driverName2'
	},{
		text: scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.gird.PlanDetail.driverphone2.lable'),//'司机电话2',
		menuDisabled:true,
		minWidth:100,
		flex: 1, 
		sortable: true, 
		dataIndex: 'driverPhone2'
	}],
	tbar:[{
		xtype:'hiddenfield',
		flex:10
		},{
			xtype: 'button', 
			text: scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.gird.PlanDetail.button.addInner.lable'),//'加发公司车',
			handler: function() {			
				var innerCarWin; 
				if(Ext.isEmpty(scheduling.shortScheduleDesign.innerCarWin)){
					innerCarWin=Ext.create('Foss.shortScheduleDesign.Window.ShortInnerCar');
					scheduling.shortScheduleDesign.innerCarWin=innerCarWin;
				}else{
					innerCarWin=scheduling.shortScheduleDesign.innerCarWin;
				}
				var form =innerCarWin.items.items[2].getForm();
				//线路列表
				scheduling.shortScheduleDesign.lineNoStore.load(
						function(records, operation, success) {
						    if(!Ext.isEmpty(records)){
						    	form.findField('lineNo').rawValue=records[0].data.lineName;
						    	form.findField('lineNo').lastValue=records[0].data.lineNo;
						    	form.findField('lineNo').setValue(records[0].data.lineNo);
						    	form.findField('lineNoExpress').setValue(records[0].data.lineNoExpress);
						    }
						}
				);
				//发车日期
				var departDate =scheduling.shortScheduleDesign.planDate+" 00:00:00";
				var departDate2=new Date(departDate.replace(/-/g,"/")); 
				//重新加载数据
				var record = Ext.create('Foss.shortScheduleDesign.model.PlanDetail',
						{origOrgCode:scheduling.shortScheduleDesign.origOrgCode,//出发部门
						 destOrgCode:scheduling.shortScheduleDesign.destOrgCode,//到达部门
						 planDepartTime:null,
						 platformTimeEnd:null,
						 platformTimeStart:null,
						 departDate:departDate2,
						 planType:'SHORT',//短途
						 truckDepartPlanId:scheduling.shortScheduleDesign.planId,
						 truckType:'OWN',//公司车
						 isOnScheduling:'Y',//默认正班车
						 frequencyType:'ADD'//默认加发班次						 
						});				    
						 
					
						scheduling.shortScheduleDesign.resetField(form);
						form.loadRecord(record);
						scheduling.shortScheduleDesign.readOnlyForm(form,false);
					//出发部门、到达部门
					form.findField('origOrgCode').setCombValue(scheduling.shortScheduleDesign.origOrgName,record.data.origOrgCode);
					form.findField('destOrgCode').setCombValue(scheduling.shortScheduleDesign.destOrgName,record.data.destOrgCode);
					//加发时，直接是操作人员所在部门,作为车队
					//车队
					var depart = FossUserContext.getCurrentDept();
					if(depart.transDepartment=='Y'){
						form.findField('longCarGroup').setCombValue(depart.name,depart.code);
						var searchForm=Ext.getCmp('Foss_shortScheduleDesign_form_innerCarSearch_ID');
						if(searchForm){
							searchForm.getForm().findField('vo.carDto.carOwnerCode').setCombValue(depart.name,depart.code);
						}
					}
										
					//重置按钮
					innerCarWin.items.items[2].query('button')[1].enable(true);
					innerCarWin.items.items[2].query('button')[2].enable(true);
					innerCarWin.setTitle(scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.gird.PlanDetail.button.addInner.lable'));//('加发公司车');
					innerCarWin.show();
					scheduling.shortScheduleDesign.queryPlatformNo(form);
					//定时任务
					var task = new Ext.util.DelayedTask(function(){
								    scheduling.shortScheduleDesign.queryMaxfrequencyNo(form);
								});
						task.delay(1000);
			}
		},{
			xtype: 'button', 
			text: scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.gird.PlanDetail.button.addOuter.lable'),//'加发外请车',
			handler: function() {
				var outerCarWin;
				if(Ext.isEmpty(scheduling.shortScheduleDesign.outerCarWin)){
					outerCarWin=Ext.create('Foss.shortScheduleDesign.Window.ShortOuterCar');
					scheduling.shortScheduleDesign.outerCarWin=outerCarWin;
				}else{
					outerCarWin=scheduling.shortScheduleDesign.outerCarWin;
				}	
				var departDate=scheduling.shortScheduleDesign.planDate;
			    var departDate2=new Date(departDate.replace(/-/g,"/")); 
				//重新加载数据
				var record = Ext.create('Foss.shortScheduleDesign.model.PlanDetail',
						{origOrgCode:scheduling.shortScheduleDesign.origOrgCode,
						 destOrgCode:scheduling.shortScheduleDesign.destOrgCode,
						 planDepartTime:null,
						 platformTimeEnd:null,
						 platformTimeStart:null,
						 departDate:departDate2,
						 planType:'SHORT',//短途
						 truckDepartPlanId:scheduling.shortScheduleDesign.planId,
						 truckType:'OUTER',//外请车
						 isOnScheduling:'Y',//默认正班车
						 frequencyType:'ADD'//默认加发班次	
						});			
						
						var form=outerCarWin.items.items[0].getForm();
						scheduling.shortScheduleDesign.resetField(form);
						form.loadRecord(record);
						scheduling.shortScheduleDesign.readOnlyForm(form,false);
						//出发部门、到达部门
						form.findField('origOrgCode').setCombValue(scheduling.shortScheduleDesign.origOrgName,record.data.origOrgCode);
						form.findField('destOrgCode').setCombValue(scheduling.shortScheduleDesign.destOrgName,record.data.destOrgCode);
						//线路列表
						scheduling.shortScheduleDesign.lineNoStore.load(
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
					//重置按钮
					outerCarWin.items.items[0].query('button')[1].enable(true);
					outerCarWin.items.items[0].query('button')[2].enable(true);
					outerCarWin.setTitle(scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.gird.PlanDetail.button.addOuter.lable'));//('加发外请车');
					outerCarWin.show();
					scheduling.shortScheduleDesign.queryPlatformNo(form);
					//定时任务
					var task = new Ext.util.DelayedTask(function(){
								    scheduling.shortScheduleDesign.queryMaxfrequencyNo(form);
								});
						task.delay(1000);
			}
		}
	],
	listeners:{
		beforeitemclick:function( view, record){
			if(record.data.id){
				scheduling.shortScheduleDesign.planDetailId=	record.data.id;
				//查询本条计划明细的修改日志
				scheduling.shortScheduleDesign.loadUpdateLog();
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
			   fieldLabel:scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.gird.PlanDetail.carTotal.lable'),//'车辆数',
			   labelWidth:80,
			   columnWidth:.10,
			   dataIndex: 'carTotal'
		   },{
			   fieldLabel:scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.gird.PlanDetail.volumeTotal.lable'),//'体积合计',
			   columnWidth:.12,	
			   minWidth:140,
			   labelWidth:80,
			   dataIndex: 'volumeTotal'
		   },{
			   fieldLabel:scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.gird.PlanDetail.gapVolumeTotal.lable'),//'体积缺口',
			   labelWidth:80,
			   minWidth:140,
			   columnWidth:.12,
			   dataIndex: 'gapVolumeTotal'
		   },{
			   fieldLabel:scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.gird.PlanDetail.weightTotal.lable'),//'载重合计',
			   columnWidth:.12,
			   labelWidth:80,
			   minWidth:140,
			   dataIndex: 'weightTotal'
		   },{
			   fieldLabel:scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.gird.PlanDetail.gapWeightTotal.lable'),//'载重缺口',
			   labelWidth:80,
			   minWidth:140,
			   columnWidth:.12,
			   dataIndex: 'gapWeightTotal'
		   }]
	}],
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);		
		me.store = Ext.create('Foss.shortScheduleDesign.store.PlanDetail');
		me.bbar = Ext.create('Deppon.StandardPaging',{
					store:me.store,
					plugins: 'pagesizeplugin'
				});
		me.selModel = Ext.create('Ext.selection.CheckboxModel');
		scheduling.shortScheduleDesign.datailPagebar=me.bbar;
		me.callParent([cfg]);	 
	}
});	


//线路store
scheduling.shortScheduleDesign.lineNoStore=Ext.create('Foss.shortScheduleDesign.store.LineNoCombox');



//加发外请车表单
Ext.define('Foss.shortScheduleDesign.form.OuterCar',{
		extend: 'Ext.form.Panel',
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
				title: scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.form.OuterCar.fieldset.lineInfo.lable'),//'线路信息',
				defaultType: 'textfield',
				layout: 'column',
				defaults: {
					anchor: '100%'
				},
				items: [{
					xtype : 'dynamicorgcombselector',
					transferCenter:'Y',//外场
					fieldLabel: scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.form.OuterCar.origOrgCode.lable'),//'出发部门',
					name: 'origOrgCode',
					readOnly:true,
					allowBlank:false,
					columnWidth:.25
				},{
					xtype : 'dynamicorgcombselector',
					salesDepartment:'Y',//营业部
					fieldLabel: scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.form.OuterCar.destOrgCode.lable'),//'到达部门',
					name: 'destOrgCode',
					readOnly:true,
					allowBlank:false,
					columnWidth:.25
				},{
					name: 'lineNo',
					fieldLabel: scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.form.OuterCar.lineName.lable'),//'线路名称',
					xtype:'combobox',
				    store:scheduling.shortScheduleDesign.lineNoStore,
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
					fieldLabel: scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.form.OuterCar.frequencyNo.lable'),//'班次',
					name: 'frequencyNo',
					hideTrigger :true,
					minValue:1,
					allowBlank:false,
					columnWidth:.25,
					maxLength:20,
					listeners:{
						blur:function(txtField,eOpts ){							
							var form =this.up('form').getForm();
							if(!Ext.isEmpty(txtField.getValue())&& !Ext.isEmpty(form.findField('lineNo').getValue())){								
								scheduling.shortScheduleDesign.queryDepartTimeByLineNoAndFrequenceNo(this.up('form'));
							}											
						}
					},
					validator:function(val){
						if(!Ext.isEmpty(val)){
							if(Ext.isNumeric(val)&&val<=0){
								return scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.form.OuterCar.frequencyNo.validator.tip');//'班次班次必须是正整数';
							}else{
								return true;
							}
						}else{
							return true;
						}
					}
				},{
					xtype:'datetimefield_date97',
					fieldLabel: scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.form.OuterCar.planDepartTime.lable'),//'发车时间',				
					name: 'planDepartTime',
					id:'short-outerplanDepartTime',
					allowBlank:false,
					columnWidth:.25,
					dateConfig: {
						el: 'short-outerplanDepartTime-inputEl',
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
				},				
				{
					name: 'frequencyType',
					fieldLabel: scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.form.OuterCar.frequencyType.lable'),//'发车类型',
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
				},{
					name: 'isOnScheduling',
					fieldLabel: scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.form.OuterCar.isOnScheduling.lable'),//'是否正班车',
					xtype:'combobox',
				    store:FossDataDictionary.getDataDictionaryStore('TFR_IS_ON_SCHEDULING'),
				    queryMode: 'local',
				    displayField: 'valueName',
				    valueField: 'valueCode',
				    editable:false,
				    readOnly :true,
				    value:'Y',
				    forceSelection:true,
				    triggerAction:'all',
				    allowBlank:false,
					columnWidth:.25				   
				},
				{
					name: 'longCarGroup',
					fieldLabel: scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.form.OuterCar.longCarGroup.lable'),//'车队',
					active : 'Y',
					xtype : 'commonmotorcadeselector',
				    allowBlank:true,
				    columnWidth:.25,
				    validator:function(val){
				    	var detailDtoInfo=this.up('form').getValues();
				    	//车队字段：当班次为正常、加发时，必填
				    	if((detailDtoInfo.frequencyType=='NORMAL'||detailDtoInfo.frequencyType=='ADD')&&Ext.isEmpty(val)){
				    		return scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.form.ShortInnerCar.longCarGroup.validator.tip');//'当班次类型为正常、加发时，车队必填';
				    	}else{
				    		return true;
				    	}
				    }		
				}
				,
				{
					name: 'platformNo',
					fieldLabel: scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.form.OuterCar.platformNo.lable'),//'月台号',
					xtype:'commonplatformselector',	
					valueField: 'virtualCode',
					orgCode:scheduling.shortScheduleDesign.origOrgCode,
				    allowBlank:true,
				    columnWidth:.25
				}
				,
				{
					xtype: 'rangeDateField',
					fieldLabel: scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.form.OuterCar.platformTime.lable'),//'使用时间',
					fieldId: 'short-outerplatformTimeStart-date97-1',
					dateType: 'datetimefield_date97',				
					fromName: 'platformTimeStart',
					toName: 'platformTimeEnd',
					columnWidth:.5
				}
				]
			},{				
				xtype:'fieldset',
				title: scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.form.OuterCar.fieldset.carinfo.lable'),//'车辆信息',
				defaultType: 'textfield',
				layout: 'column',
				defaults: {
					anchor: '100%'
				},
				items: [{
							name: 'truckType',
							fieldLabel: scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.form.OuterCar.truckType.lable'),//'归属类型',
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
							fieldLabel: scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.form.OuterCar.truckModel.lable'),//'车型',
							name: 'truckModel',
							vehicleSort : 'ownership_leased',
							columnWidth:.25,
							allowBlank:true,
							listeners:{
								blur:function(txtfield,eopts){
									if(!Ext.isEmpty(txtfield.value)){
										scheduling.shortScheduleDesign.queryCarInfoByVechileNo(this.up('form'));
									}
								}
							},
						    validator:function(val){
						    	var detailDtoInfo=this.up('form').getValues();
						    	//车队字段：当班次为正常、加发时，必填
						    	if((detailDtoInfo.frequencyType=='NORMAL'||detailDtoInfo.frequencyType=='ADD')&&Ext.isEmpty(val)){
						    		return scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.form.OuterCar.truckModel.validator.tip');//'当班次类型为正常、加发时，车型必填';
						    	}else{
						    		return true;
						    	}
						    }
						},{
							xtype : 'commonleasedvehicleselector',
							fieldLabel: scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.form.OuterCar.vehicleNo.lable'),//'车牌号',
							name: 'vehicleNo',
							allowBlank:true,
							columnWidth:.25,
							listeners:{
								blur:function(txtfield,eopts){
									if(!Ext.isEmpty(txtfield.value)){
										//查询车辆信息
										scheduling.shortScheduleDesign.queryCarInfoByVechileNo(this.up('form'));
										//车辆查询司机信息
										scheduling.shortScheduleDesign.queryDriverInfoByVechileNo(this.up('form'));
									}
								}
							}
						},{
							xtype: 'numberfield',
							fieldLabel: scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.form.OuterCar.maxLoadWeight.lable'),//'车辆载重',
							columnWidth:.25,
							value:0,
							minValue:0,
							maxLength:8,
							listeners:{
								blur:function(val,epts){
									if(Ext.isEmpty(val.getValue())){
										val.setValue(0);
									}
									var field = this.up('form').getForm().findField('planLoadWeight');
									field.fireEvent('blur',field);
								}
							},
							name: 'maxLoadWeight',
							allowBlank:true
						},{
							xtype: 'numberfield',
							fieldLabel: scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.form.OuterCar.truckVolume.lable'),//'车辆净空',
							name: 'truckVolume',
							value:0,
							minValue:0,
							maxLength:8,
							listeners:{
								blur:function(val,epts){
									console.log('blur');
									if(Ext.isEmpty(val.getValue())){
										val.setValue(0);
									}
									var field = this.up('form').getForm().findField('planLoadVolume');
									field.fireEvent('blur',field);
								}
							},
							columnWidth:.25,
							allowBlank:true
					  },{
							xtype: 'numberfield',
							fieldLabel: scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.form.OuterCar.planLoadWeight.lable'),//'预计装载载重',
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
								var maxLoadWeight = this.up('form').getForm().findField('maxLoadWeight').getValue();
								var maxLoadWeightVal = Ext.Number.from(maxLoadWeight, 0);
								var value=Ext.Number.from(val, 0);
								if(value>maxLoadWeightVal){
									Ext.Msg.alert(scheduling.shortScheduleDesign.i18n('foss.shortDeparturePlan.confirm.title.lable'),
												  scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.form.OuterCar.planLoadWeight.validator.tip'));
									return scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.form.OuterCar.planLoadWeight.validator.tip');//'预计装载载重不能大于车辆载重';
								}else{
									return true;
								}
							},
							allowBlank:true
						},{
							xtype: 'numberfield',
							fieldLabel: scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.form.OuterCar.planLoadVolume.lable'),//'预计装载体积',
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
									Ext.Msg.alert(scheduling.shortScheduleDesign.i18n('foss.shortDeparturePlan.confirm.title.lable'),
												  scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.form.OuterCar.planLoadVolume.validator.tip'));
									return scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.form.OuterCar.planLoadVolume.validator.tip');//'预计装载体积不能大于车辆净空';
								}else{
									return true;
								}
							},
							allowBlank:true
						},{
							xtype:'textarea',
							fieldLabel: scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.form.OuterCar.truckInfoNotes.lable'),//'备注信息',
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
							value:'SHORT'//长途
						}
				      ]
			},{				
				xtype:'fieldset',
				title: scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.form.OuterCar.fieldset.driverInfo.lable'),//'司机信息',
				defaultType: 'textfield',
				layout: 'column',
				defaults: {
					anchor: '100%'
				},
				items: [{
							xtype : 'commonleaseddriverselector',
							fieldLabel: scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.form.OuterCar.driverCode1.lable'),//'司机姓名',
							name: 'driverCode1',
							allowBlank:true,
							columnWidth:.25,
							listeners:{
								blur:function(filed,  eOpts){
									var val=filed.getValue();
									var truckType='OUTER';
									//司机查询司机联系方式
									scheduling.shortScheduleDesign.queryDriverInfoByDriverCode(this.up('form'),'driverCode1',truckType);
									//司机查询车牌号
									//scheduling.shortScheduleDesign.queryLeasedDriverVechile(this.up('form'));
								}	
							},
						    validator:function(val){
						    	var detailDtoInfo=this.up('form').getValues();
						    	//车队字段：当班次为正常、加发时，必填
						    	if((detailDtoInfo.frequencyType=='NORMAL'||detailDtoInfo.frequencyType=='ADD')&&Ext.isEmpty(val)){
						    		return scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.form.OuterCar.driverCode1.validator.tip');//'当班次类型为正常、加发时，司机姓名必填';
						    	}else{
						    		return true;
						    	}
						    }
						},{
							fieldLabel: scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.form.OuterCar.driverPhone1.lable'),//'司机电话',
							name: 'driverPhone1',
							maxLength:200,
							columnWidth:.25,
							allowBlank:true
						},{
							xtype : 'commonleaseddriverselector',
							fieldLabel: scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.form.OuterCar.driverCode2.lable'),//'司机姓名2',
							name: 'driverCode2',
							allowBlank:true,
							columnWidth:.25,
							listeners:{
								blur:function(filed,  eOpts){
									var val=filed.getValue();
									var truckType='OUTER';
									//司机查询司机联系方式
									scheduling.shortScheduleDesign.queryDriverInfoByDriverCode(this.up('form'),'driverCode2',truckType);
								}	
							}
						},{
							fieldLabel: scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.form.OuterCar.driverPhone2.lable'),//'司机电话2',
							name: 'driverPhone2',
							maxLength:200,
							columnWidth:.25,
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
							text: scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.form.OuterCar.button.cancel.lable'),//'取消',
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
							text: scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.form.OuterCar.button.save.lable'),//'保存',
							handler: function() {
								if(this.up('form').getForm().isValid( )){
									var form = this.up('form');
									//判断月台的使用时间
									var vals =form.getValues();
									//月台号不为空,则使用时间也不能为空
									if(!Ext.isEmpty(vals.platformNo)){
										if(Ext.isEmpty(vals.platformTimeStart)||Ext.isEmpty(vals.platformTimeEnd)){
											Ext.MessageBox.alert(scheduling.shortScheduleDesign.i18n('foss.shortDeparturePlan.error.title.lable'),
											scheduling.shortScheduleDesign.i18n('foss.shortDeparturePlan.error.platformTimeEnd.lable'));//'请填写月台使用时间'
											return;
										}
									}
									var planDepartTime = form.getForm().findField('planDepartTime').getValue();
									var platformTimeEnd = form.getForm().findField('platformTimeEnd').getRawValue();
									var platformTimeStart = form.getForm().findField('platformTimeStart').getRawValue();
									if(!Ext.isEmpty(platformTimeEnd)){
											if(Date.parse(planDepartTime)<Date.parse(platformTimeEnd)){
												Ext.MessageBox.alert(scheduling.shortScheduleDesign.i18n('foss.shortDeparturePlan.error.title.lable'),
																	 scheduling.shortScheduleDesign.i18n('foss.shortDeparturePlan.error.platformTimeEnd.tip'));//"不能保存,月台使用结束时间大于发车时间"	
												return ;
										}
									}
									if(!Ext.isEmpty(platformTimeStart)){
										if(Date.parse(planDepartTime)<Date.parse(platformTimeStart)){
											Ext.MessageBox.alert(scheduling.shortScheduleDesign.i18n('foss.shortDeparturePlan.error.title.lable'),
																 scheduling.shortScheduleDesign.i18n('foss.shortDeparturePlan.error.platformTimeStart.tip'));//"不能保存,月台使用开始时间大于发车时间"	
											return ;
										}
									}
									//执行保存或更新
									scheduling.shortScheduleDesign.saveOrUpdateInnerSchedule(form);
								}else{
									Ext.MessageBox.alert(scheduling.shortScheduleDesign.i18n('foss.shortDeparturePlan.confirm.title.lable'),
														 scheduling.shortScheduleDesign.i18n('foss.shortDeparturePlan.confirm.connotsave.lable'));//"不能执行保存,请查看页面提示信息"	
								}
							}
						},{
							columnWidth:.08,
							xtype : 'button',
							text: scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.form.OuterCar.button.reset.lable'),//'重置',
							handler: function() {
								var form = this.up('form').getForm();
								scheduling.shortScheduleDesign.resetField(form);
							}
						}]
	}]
});

//加发外请车弹出窗
Ext.define('Foss.shortScheduleDesign.Window.ShortOuterCar', {
	extend:'Ext.window.Window',
	title: scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.Window.ShortOuterCar.title.lable'),//'加发外请车',
	modal:true,
	closeAction:'hide',
	width: 1024,	
	bodyCls: 'autoHeight',
	layout: 'auto',	
	items:[
		scheduling.shortScheduleDesign.outerCarForm=Ext.create('Foss.shortScheduleDesign.form.OuterCar')
	]
});


//保存发车计划备注
scheduling.shortScheduleDesign.updatePlanRemark=function(form){
	var actionUrl=scheduling.realPath('updatePlanRemark.action');
	var vals=form.getForm().getValues();
	//备注信息
	var notes =vals.notes;	
	//是否异常
	var isIssue = vals.isIssue;
	if(!Ext.isEmpty(scheduling.shortScheduleDesign.planId)&&form.getForm().findField('notes').isValid( )){
		var params = {
				"vo.planDto.notes":notes,
				"vo.planDto.id":scheduling.shortScheduleDesign.planId,
				"vo.planDto.isIssue":isIssue
				};	
		//请求查询出车的相关排班任务信息
		Ext.Ajax.request({
			url : actionUrl,
			params:params,
			//动态创建表单，显示任务信息
			success : function(response) {
				var json = Ext.decode(response.responseText);
				Ext.MessageBox.alert(scheduling.shortScheduleDesign.i18n('foss.shortDeparturePlan.confirm.title.lable'),
									 scheduling.shortScheduleDesign.i18n('foss.shortDeparturePlan.confirm.save.success'));//'保存成功'     		
			},
			exception : function(response) {
				var json = Ext.decode(response.responseText);
				Ext.MessageBox.alert(scheduling.shortScheduleDesign.i18n('foss.shortDeparturePlan.error.title.lable'),json.message);
			}		
			});	
	}	
}

//查询司机信息
scheduling.shortScheduleDesign.queryDriverInfoByDriverCode=function(form,fieldName,truckType){
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
				Ext.MessageBox.alert(scheduling.shortScheduleDesign.i18n('foss.shortDeparturePlan.error.title.lable'),json.message);
			}		
			});	
	}	
}

//保存或更新公司内发车计划
scheduling.shortScheduleDesign.saveOrUpdateInnerSchedule=function(form){
	var actionUrl=scheduling.realPath('saveOrUpdateInnerPlanDetail.action');
	var record = form.getForm().getRecord();	
	//计划ID
	form.getForm().findField('truckDepartPlanId').setValue(scheduling.shortScheduleDesign.planId);
	//计划日期
	form.getForm().findField('departDate').setValue(record.data.departDate);
	var detailDtoInfo =form.getForm().getValues();
	//构建参数	
	var params = {vo:{detailDto:detailDtoInfo}};
	if(form.getForm().isValid()){
		form.getEl().mask(scheduling.shortScheduleDesign.i18n('foss.shortDeparturePlan.mask.waiting.tip'));//('正在保存，请稍等 ...');	
	//请求查询出车的相关排班任务信息
	Ext.Ajax.request({
		url : actionUrl,
		jsonData:params,
		//动态创建表单，显示任务信息
		success : function(response) {
			form.getEl().unmask();
			var json = Ext.decode(response.responseText);
			var detailDto = json.vo.detailDto;
			if(!Ext.isEmpty(detailDto)){
				if(!Ext.isEmpty(detailDto.platformDistributeId))
					form.getForm().findField('platformDistributeId').setValue(detailDto.platformDistributeId);
				if(!Ext.isEmpty(detailDto.id))
					form.getForm().findField('id').setValue(detailDto.id);
			}
			//刷新列表
			scheduling.shortScheduleDesign.datailPagebar.moveFirst();
			//查询修改记录
			scheduling.shortScheduleDesign.planDetailId=detailDto.id;
			scheduling.shortScheduleDesign.loadUpdateLog();
			//信息提示
			Ext.Msg.confirm(scheduling.shortScheduleDesign.i18n('foss.shortDeparturePlan.confirm.title.lable'), scheduling.shortScheduleDesign.i18n('foss.shortDeparturePlan.confirm.whetherClose.tip'), function(btn){//'保存成功,是否需要关闭窗口?'
			    if (btn == 'yes'){
			    	form.up('window').close();
			    }
			});
		},
		exception : function(response) {
			form.getEl().unmask();
			var json = Ext.decode(response.responseText);
			Ext.MessageBox.alert(scheduling.shortScheduleDesign.i18n('foss.shortDeparturePlan.error.title.lable'),json.message);
		}		
		});	
	}
}

//通过"线路"、"班次"查询"发车时间"
scheduling.shortScheduleDesign.queryDepartTimeByLineNoAndFrequenceNo=function(formEl){
	var form=formEl.getForm();					
	//线路
	var lineNo=form.getValues().lineNo;
	//班次
	var frequencyNo=form.getValues().frequencyNo;	
	if(lineNo != null && frequencyNo != null){
		var actionUrl=scheduling.realPath('queryDepartTimeByLineNoAndFrequenceNo.action');
		var queryParams={"vo.simDto.lineNo":lineNo,"vo.simDto.frequencyNo":frequencyNo};
		//遮罩
		formEl.getEl().mask(scheduling.shortScheduleDesign.i18n('foss.shortDeparturePlan.mask.querywaiting.tip'));//("正在查询，请稍等..."); 
		Ext.Ajax.request({
			url : actionUrl,
			params:queryParams,
			success : function(response) {
				//填充车型、车辆所属车队
				var json = Ext.decode(response.responseText);
	        	var simDto=json.vo.simDto;	        	
	        	if(scheduling.shortScheduleDesign.planDate){	        		        		
		        	if(form.findField('planDepartTime') != null){
		        		//计划日期
		        		var planDate = scheduling.shortScheduleDesign.planDate.substring(0,10);	
		        		var departTimeShort = simDto.departTimeShort;
		        		if(!Ext.isEmpty(planDate)&&!Ext.isEmpty(departTimeShort)){		        			
		        			//组装时间
		        			var planDepartTime =planDate+' '+departTimeShort;
		        			form.findField('planDepartTime').setValue(planDepartTime);
		        			form.findField('platformTimeEnd').maxDate=planDepartTime;
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
				Ext.MessageBox.alert(scheduling.shortScheduleDesign.i18n('foss.shortDeparturePlan.confirm.title.lable'),json.message);
				formEl.getEl().unmask( );
			}		
			});
	}	
}

//外请司机查询外请车
scheduling.shortScheduleDesign.queryLeasedDriverVechile=function(form){
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
				Ext.MessageBox.alert(scheduling.shortScheduleDesign.i18n('foss.shortDeparturePlan.error.title.lable'),json.message);
			}		
			});	
			
	}
}

//转换归属类型
scheduling.shortScheduleDesign.changeTruckType=function(detailId,truckTypeTo){
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
			scheduling.shortScheduleDesign.datailPagebar.moveFirst();
		},
		exception : function(response) {
			var json = Ext.decode(response.responseText);
			Ext.MessageBox.alert(scheduling.shortScheduleDesign.i18n('foss.shortDeparturePlan.error.title.lable'),json.message);
		}		
	});	
}

//通过车牌查询车辆信息
scheduling.shortScheduleDesign.queryCarInfoByVechileNo=function(form){
	var vals = form.getValues();
	if(!Ext.isEmpty(vals.vehicleNo)||!Ext.isEmpty(vals.truckModel)){
		var actionUrl=scheduling.realPath('queryVehicleByVechileNo.action');
		var params = {
					"vo.detailDto.vehicleNo":vals.vehicleNo,
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
					//车容积
					if(!Ext.isEmpty(vehicle.truckVolume))
					bform.findField('truckVolume').setValue(vehicle.truckVolume);
					//预计装载重量
					if(!Ext.isEmpty(vehicle.planLoadWeight))
					bform.findField('planLoadWeight').setValue(vehicle.planLoadWeight);
					//预计装载体积
					if(!Ext.isEmpty(vehicle.planLoadVolume))
					bform.findField('planLoadVolume').setValue(vehicle.planLoadVolume);
					//车型,且不为拖头
					if(!Ext.isEmpty(vehicle.vehicleType)&&vehicle.vehicleType=='vehicletype_tractors'){						
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
				Ext.MessageBox.alert(scheduling.shortScheduleDesign.i18n('foss.shortDeparturePlan.error.title.lable'),json.message);
			}		
			});	
			
	}
}


scheduling.shortScheduleDesign.queryDriverInfoByVechileNo=function(form){
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

//删除发车计划
scheduling.shortScheduleDesign.deleteTruckDepartPlanDetail=function(grid,ids){
	if(!Ext.isEmpty(ids)){
		var actionUrl=scheduling.realPath('deleteTruckDepartPlanDetail.action');
		var params = {"vo.detailDto.ids":ids};	
		//请求查询出车的相关排班任务信息
		Ext.Ajax.request({
			url : actionUrl,
			params:params,
			//动态创建表单，显示任务信息
			success : function(response) {
				scheduling.shortScheduleDesign.datailPagebar.moveFirst();
				Ext.MessageBox.alert(scheduling.shortScheduleDesign.i18n('foss.shortDeparturePlan.confirm.title.lable'),'成功删除');			
			},
			exception : function(response) {
				var json = Ext.decode(response.responseText);
				Ext.MessageBox.alert(scheduling.shortScheduleDesign.i18n('foss.shortDeparturePlan.error.title.lable'),json.message);
			}		
		});			
	}
}

//下发发车计划
scheduling.shortScheduleDesign.releaseTruckDepartPlanDetail=function(grid,ids){
	if(!Ext.isEmpty(ids)){
		var actionUrl=scheduling.realPath('releaseTruckDepartPlanDetail.action');
		var params = {"vo.detailDto.ids":ids};	
		//请求查询出车的相关排班任务信息
		Ext.Ajax.request({
			url : actionUrl,
			params:params,
			//动态创建表单，显示任务信息
			success : function(response) {
				scheduling.shortScheduleDesign.datailPagebar.moveFirst();
				Ext.MessageBox.alert("提示",'下发成功');						
			},
			exception : function(response) {
				var json = Ext.decode(response.responseText);
				Ext.MessageBox.alert(scheduling.shortScheduleDesign.i18n('foss.shortDeparturePlan.error.title.lable'),json.message);
			}		
		});			
	}
}

//查询修改记录
scheduling.shortScheduleDesign.loadUpdateLog=function(){
	if(scheduling.shortScheduleDesign.planLog){
		scheduling.shortScheduleDesign.planLogPagebar.moveFirst();
	}
}

//加发公司车表单
Ext.define('Foss.shortScheduleDesign.form.ShortInnerCar',{
		extend: 'Ext.form.Panel',	
		id:'Foss_shortScheduleDesign_form_shortInnerCar_ID',
		bodyStyle:'padding:5px 5px 0',
		frame:true,
		title:scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.form.ShortInnerCar.title.lable'),//'公司车发车计划信息',
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
				title: scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.form.ShortInnerCar.fieldset.lineInfo.lable'),//'线路信息',
				defaultType: 'textfield',
				layout: 'column',
				defaults: {
					anchor: '100%'
				},
				items: [{
					xtype : 'dynamicorgcombselector',
					transferCenter:'Y',//外场
					fieldLabel: scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.form.ShortInnerCar.origOrgCode.lable'),//'出发部门',
					name: 'origOrgCode',
					readOnly:true,
					allowBlank:false,
					columnWidth:.25
				},{
					xtype : 'dynamicorgcombselector',
					salesDepartment:'Y',//营业部
					fieldLabel:scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.form.ShortInnerCar.destOrgCode.lable'),//'到达部门',
					name: 'destOrgCode',
					readOnly:true,
					allowBlank:false,
					columnWidth:.25
				},{
					name: 'lineNo',
					fieldLabel: scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.form.ShortInnerCar.lineName.lable'),//'线路名称',
					xtype:'combobox',
				    store:scheduling.shortScheduleDesign.lineNoStore,
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
					fieldLabel: scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.form.ShortInnerCar.frequencyNo.lable'),//'班次',
					name: 'frequencyNo',
					allowBlank:false,
					hideTrigger :true,
					minValue:1,
					columnWidth:.25,
					maxLength:20,
					listeners:{
						blur:function(txtField,eOpts ){							
							var form =this.up('form').getForm();
							if(!Ext.isEmpty(txtField.getValue())&& !Ext.isEmpty(form.findField('lineNo').getValue())){								
								scheduling.shortScheduleDesign.queryDepartTimeByLineNoAndFrequenceNo(this.up('form'));
							}											
						}
					},
					validator:function(val){
						if(!Ext.isEmpty(val)){
							if(Ext.isNumeric(val)&&val<=0){
								return scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.form.OuterCar.frequencyNo.validator.tip');//'班次班次必须是正整数';
							}else{
								return true;
							}
						}else{
							return true;
						}
					}
				},{
					xtype:'datetimefield_date97',
					fieldLabel: scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.form.ShortInnerCar.planDepartTime.lable'),//'发车时间',				
					name: 'planDepartTime',
					id:'short-innerplanDepartTime',
					allowBlank:false,
					columnWidth:.25,
					dateConfig: {
						el: 'short-innerplanDepartTime-inputEl',
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
				},				
				{
					name: 'frequencyType',
					fieldLabel: scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.form.ShortInnerCar.frequencyType.lable'),//'发车类型',
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
				},{
					name: 'isOnScheduling',
					fieldLabel: scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.form.ShortInnerCar.isOnScheduling.lable'),//'是否正班车',
					xtype:'combobox',
				    store:FossDataDictionary.getDataDictionaryStore('TFR_IS_ON_SCHEDULING'),
				    queryMode: 'local',
				    displayField: 'valueName',
				    valueField: 'valueCode',
				    editable:false,
				    readOnly :true,
				    value:'Y',
				    forceSelection:true,
				    triggerAction:'all',
				    allowBlank:false,
					columnWidth:.25				   
				},
				{
					xtype : 'commonmotorcadeselector',
					name: 'longCarGroup',
					fieldLabel: scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.form.ShortInnerCar.longCarGroup.lable'),//'车队',
					active : 'Y',
				    allowBlank:true,
				    columnWidth:.25,
				    validator:function(val){
				    	var detailDtoInfo=this.up('form').getValues();
				    	//车队字段：当班次为正常、加发时，必填
				    	if((detailDtoInfo.frequencyType=='NORMAL'||detailDtoInfo.frequencyType=='ADD')&&Ext.isEmpty(val)){
				    		return scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.form.ShortInnerCar.longCarGroup.validator.tip');//'当班次类型为正常、加发时，车队必填';
				    	}else{
				    		return true;
				    	}
				    }		
				},
				{
					xtype:'commonplatformselector',	
					name: 'platformNo',
					fieldLabel: scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.form.ShortInnerCar.platformNo.lable'),//'月台号',
					valueField: 'virtualCode',
					orgCode:scheduling.shortScheduleDesign.origOrgCode,
				    allowBlank:true,
				    columnWidth:.25
				},
				{
					xtype: 'rangeDateField',
					fieldLabel: scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.form.ShortInnerCar.platformTime.lable'),//'使用时间',
					fieldId: 'short-platformTimeStart-date97-1',
					dateType: 'datetimefield_date97',				
					fromName: 'platformTimeStart',
					toName: 'platformTimeEnd',
					columnWidth:.5,
					listeners:{
						blur:function(field,epts){
							console.log(field);
							console.log(this);
						}
					},validator:function(val){
						var planDepartTime = this.up('form').getForm().findField('planDepartTime').getValue();
						var platformTimeEnd = this.up('form').getForm().findField('platformTimeEnd').getRawValue();
						if(!Ext.isEmpty(platformTimeEnd)){
							if(platformTimeEnd){
								if(Date.parse(planDepartTime)>Date.parse(platformTimeEnd)){
									return scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.form.ShortInnerCar.platformTime.startLtEnd.tip');//"月台结束时间大于发车时间";
								}
							}
						}
						return true;						
					}
				}
				]
			},{				
				xtype:'fieldset',
				title: scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.form.ShortInnerCar.driverInfo.lable'),//'司机信息',
				defaultType: 'textfield',
				layout: 'column',
				defaults: {
					anchor: '100%'
				},
				items: [{
							xtype : 'commonowndriverselector',
							fieldLabel: scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.form.ShortInnerCar.driverCode1.lable'),//'司机姓名',
							name: 'driverCode1',
							allowBlank:true,
							columnWidth:.25,
							listeners:{
								blur:function(filed,  eOpts){
									var val=filed.getValue();
									var truckType='OWN';
									scheduling.shortScheduleDesign.queryDriverInfoByDriverCode(this.up('form'),'driverCode1',truckType);
								}				
							},
						    validator:function(val){
						    	var detailDtoInfo=this.up('form').getValues();
						    	//车队字段：当班次为正常、加发时，必填
						    	if((detailDtoInfo.frequencyType=='NORMAL'||detailDtoInfo.frequencyType=='ADD')&&Ext.isEmpty(val)){
						    		return scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.form.OuterCar.driverCode1.validator.tip');//'当班次类型为正常、加发时，司机姓名必填';
						    	}else{
						    		return true;
						    	}
						    }
						},{
							fieldLabel: scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.form.ShortInnerCar.driverPhone1.lable'),//'司机电话',
							name: 'driverPhone1',
							maxLength:200,
							columnWidth:.25,
							allowBlank:true
						},{
							xtype : 'commonowndriverselector',
							fieldLabel: scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.form.ShortInnerCar.driverCode2.lable'),//'司机姓名2',
							name: 'driverCode2',
							allowBlank:true,
							columnWidth:.25,
							listeners:{
								blur:function(filed,  eOpts){
									var val=filed.getValue();
									var truckType='OWN';
									scheduling.shortScheduleDesign.queryDriverInfoByDriverCode(this.up('form'),'driverCode2',truckType);
								}	
							}
						},{
							fieldLabel: scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.form.ShortInnerCar.driverPhone2.lable'),//'司机电话2',
							name: 'driverPhone2',
							maxLength:200,
							columnWidth:.25,
							allowBlank:true
						}]
			},{				
				xtype:'fieldset',
				title: scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.form.ShortInnerCar.fieldset.carinfo.lable'),//'车辆信息',
				defaultType: 'textfield',
				layout: 'column',
				defaults: {
					anchor: '100%'
				},
				items: [{
							name: 'truckType',
							fieldLabel: scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.form.ShortInnerCar.truckType.lable'),//'归属类型',
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
							fieldLabel: scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.form.ShortInnerCar.truckModel.lable'),//'车型',
							name: 'truckModel',
							vehicleSort : 'ownership_company',
							columnWidth:.25,
							allowBlank:true,
							listeners:{
								blur:function(txtfield,eopts){
									if(!Ext.isEmpty(txtfield.value)){
										scheduling.shortScheduleDesign.queryCarInfoByVechileNo(this.up('form'));
									}
								}
							},
						    validator:function(val){
						    	var detailDtoInfo=this.up('form').getValues();
						    	//车队字段：当班次为正常、加发时，必填
						    	if((detailDtoInfo.frequencyType=='NORMAL'||detailDtoInfo.frequencyType=='ADD')&&Ext.isEmpty(val)){
						    		return scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.form.OuterCar.truckModel.validator.tip');//'当班次类型为正常、加发时，车型必填';
						    	}else{
						    		return true;
						    	}
						    }
						},{
							xtype : 'commonowntruckselector',
							fieldLabel: scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.form.ShortInnerCar.vehicleNo.lable'),//'车牌号',
							name: 'vehicleNo',
							allowBlank:true,
							columnWidth:.25,
							maxLength:50,
							listeners:{
								blur:function(txtfield,eopts){
									if(!Ext.isEmpty(txtfield.value)){
										scheduling.shortScheduleDesign.queryCarInfoByVechileNo(this.up('form'));
									}
								}
							}
						},{
							xtype: 'numberfield',
							fieldLabel: scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.form.ShortInnerCar.maxLoadWeight.lable'),//'车辆载重',
							columnWidth:.25,
							readOnly:true,
							value:0,
							minValue:0,
							maxLength:8,
							listeners:{
								blur:function(val,epts){
									if(Ext.isEmpty(val.getValue())){
										val.setValue(0);
									}
									var field = this.up('form').getForm().findField('planLoadWeight');
									field.isValid();
								}
							},
							name: 'maxLoadWeight',
							allowBlank:true
						},{
							xtype: 'numberfield',
							fieldLabel: scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.form.ShortInnerCar.truckVolume.lable'),//'车辆净空',
							name: 'truckVolume',
							readOnly:true,
							maxLength:8,
							value:0,
							minValue:0,
							listeners:{
								blur:function(val,epts){
									if(Ext.isEmpty(val.getValue())){
										val.setValue(0);
									}
									var field = this.up('form').getForm().findField('planLoadVolume');
									field.isValid();
								}
							},
							columnWidth:.25,
							allowBlank:true
					  },{
							xtype: 'numberfield',
							fieldLabel: scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.form.ShortInnerCar.planLoadWeight.lable'),//'预计装载载重',
							columnWidth:.25,
							name: 'planLoadWeight',
							maxLength:8,
							value:0,
							minValue:0,
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
									Ext.Msg.alert(scheduling.shortScheduleDesign.i18n('foss.shortDeparturePlan.confirm.title.lable'),
									scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.form.OuterCar.planLoadWeight.validator.tip'));
									return scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.form.OuterCar.planLoadWeight.validator.tip');//'预计装载载重不能大于车辆载重';
								}else{
									return true;
								}
							},
							allowBlank:true
						},{
							xtype: 'numberfield',
							fieldLabel: scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.form.ShortInnerCar.planLoadVolume.lable'),//'预计装载体积',
							columnWidth:.25,
							value:0,
							maxLength:8, 
							minValue:0,
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
									Ext.Msg.alert(scheduling.shortScheduleDesign.i18n('foss.shortDeparturePlan.confirm.title.lable'),
									scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.form.OuterCar.planLoadVolume.validator.tip'));
									return scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.form.OuterCar.planLoadVolume.validator.tip');//'预计装载体积不能大于车辆净空';
								}else{
									return true;
								}
							},
							allowBlank:true
						},{
							xtype:'textarea',
							fieldLabel: scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.form.ShortInnerCar.truckInfoNotes.lable'),//'备注信息',
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
							value:'SHORT'//长途
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
							text: scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.form.ShortInnerCar.button.cancel.lable'),//'取消',
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
							text: scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.form.ShortInnerCar.button.save.lable'),//'保存',
							handler: function() {
								if(this.up('form').getForm().isValid( )){
									var form = this.up('form');
									//判断月台的使用时间
									var vals =form.getValues();
									//月台号不为空,则使用时间也不能为空
									if(!Ext.isEmpty(vals.platformNo)){
										if(Ext.isEmpty(vals.platformTimeStart)||Ext.isEmpty(vals.platformTimeEnd)){
											Ext.MessageBox.alert(scheduling.shortScheduleDesign.i18n('foss.shortDeparturePlan.error.title.lable'),
											scheduling.shortScheduleDesign.i18n('foss.shortDeparturePlan.error.platformTimeEnd.lable'));
											return;
										}
									}
									var planDepartTime = form.getForm().findField('planDepartTime').getValue();
									var platformTimeEnd = form.getForm().findField('platformTimeEnd').getRawValue();
									var platformTimeStart = form.getForm().findField('platformTimeStart').getRawValue();
									if(!Ext.isEmpty(platformTimeEnd)){
											if(Date.parse(planDepartTime)<Date.parse(platformTimeEnd)){
												Ext.MessageBox.alert(scheduling.shortScheduleDesign.i18n('foss.shortDeparturePlan.error.title.lable'),
												scheduling.shortScheduleDesign.i18n('foss.shortDeparturePlan.error.platformTimeEnd.tip'));//"不能保存,月台使用结束时间大于发车时间"	
												return ;
										}
									}
									if(!Ext.isEmpty(platformTimeStart)){
										if(Date.parse(planDepartTime)<Date.parse(platformTimeStart)){
											Ext.MessageBox.alert(scheduling.shortScheduleDesign.i18n('foss.shortDeparturePlan.error.title.lable'),
											scheduling.shortScheduleDesign.i18n('foss.shortDeparturePlan.error.platformTimeStart.tip'));//"不能保存,月台使用开始时间大于发车时间"	
											return ;
										}
									}
									//执行保存或更新
									scheduling.shortScheduleDesign.saveOrUpdateInnerSchedule(form);
								}else{
									Ext.MessageBox.alert(scheduling.shortScheduleDesign.i18n('foss.shortDeparturePlan.confirm.title.lable')
									,scheduling.shortScheduleDesign.i18n('foss.shortDeparturePlan.confirm.connotsave.lable'));//"不能执行保存,请查看页面提示信息"	
								}
							}
						},{
							columnWidth:.08,
							xtype : 'button',
							text: scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.form.ShortInnerCar.button.reset.lable'),//'重置',
							handler: function() {
								var from=this.up('form').getForm();								
								scheduling.shortScheduleDesign.resetField(from);
							}
						}]
	}]
});

//重置
scheduling.shortScheduleDesign.resetField=function(form){
//	if(!Ext.isEmpty(form.findField('lineNo')))
//	form.findField('lineNo').reset();
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
	
	form.findField('truckModel').reset();
	form.findField('vehicleNo').reset();
	form.findField('maxLoadWeight').reset();
	
	form.findField('planLoadWeight').reset();
	form.findField('planLoadVolume').reset();
	form.findField('truckVolume').reset();
	form.findField('truckInfoNotes').reset();
}

//重置
scheduling.shortScheduleDesign.readOnlyForm=function(form,isReadOnly,actionType){
	if(!Ext.isEmpty(form.findField('lineNo'))){
		if(!Ext.isEmpty(actionType)&&actionType=='EDIT'){
			form.findField('lineNo').setReadOnly(true);
		}else{
			form.findField('lineNo').setReadOnly(isReadOnly);
		}		
	}
	form.findField('longCarGroup').setReadOnly(isReadOnly);
	//form.findField('isOnScheduling').setReadOnly(isReadOnly);
	form.findField('frequencyType').setReadOnly(isReadOnly);
	form.findField('frequencyNo').setReadOnly(isReadOnly);
	form.findField('planDepartTime').setReadOnly(isReadOnly);
	form.findField('platformNo').setReadOnly(isReadOnly);
	form.findField('platformTimeStart').setReadOnly(isReadOnly);
	form.findField('platformTimeEnd').setReadOnly(isReadOnly);
	form.findField('driverCode1').setReadOnly(isReadOnly);
	form.findField('driverPhone1').setReadOnly(isReadOnly);
	form.findField('driverCode2').setReadOnly(isReadOnly);
	form.findField('driverPhone2').setReadOnly(isReadOnly);
	form.findField('truckModel').setReadOnly(isReadOnly);
	form.findField('vehicleNo').setReadOnly(isReadOnly);
	if(form.getRecord().data.truckType=='OUTER'){
		form.findField('maxLoadWeight').setReadOnly(isReadOnly);		
	}	
	form.findField('planLoadWeight').setReadOnly(isReadOnly);
	form.findField('planLoadVolume').setReadOnly(isReadOnly);
	if(form.getRecord().data.truckType=='OUTER'){
		form.findField('truckVolume').setReadOnly(isReadOnly);
	}	
	form.findField('truckInfoNotes').setReadOnly(isReadOnly);
}

//公司车查询可用车辆表单
Ext.define('Foss.shortScheduleDesign.form.InnerCarSearch',{
	extend: 'Ext.form.Panel',
	layout:'column',	
	id:'Foss_shortScheduleDesign_form_innerCarSearch_ID',
	bodyStyle:'padding:5px 5px 0 5px',	
	title:scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.form.InnerCarSearch.title.lable'),//'查询车辆',
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
		fieldLabel: '车队',//'车队小组',
		name: 'vo.carDto.carOwnerCode',
		forceSelection:true,
		//allowBlank:false,
		columnWidth:.25		
	},{
		xtype : 'commonvehicletypeselector',
		fieldLabel: scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.form.InnerCarSearch.truckModel.lable'),//'车型',
		//forceSelection:true,
		emptyText:scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.form.InnerCarSearch.carStatus.all'),//'全部',
		name: 'vo.carDto.truckModel',
		vehicleSort : 'ownership_company',
		allowBlank:true,
		columnWidth:.25,
		listeners:{
			blur:function( bfield,eOpts ){
				if(Ext.isEmpty(bfield.rawValue)){
					this.up('form').getForm().findField('vo.carDto.truckModel').reset();					
				}
			}
		}
	},{
		name: 'vo.carDto.carStatus',
		fieldLabel: scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.form.InnerCarSearch.carStatus.lable'),//'车辆状态',
		forceSelection:true,
		xtype:'combobox',
	    store:FossDataDictionary.getDataDictionaryStore('TFR_VECHILE_STATUS'),
	    queryMode: 'local',
	    displayField: 'valueName',
	    valueField: 'valueCode',
	    emptyText:scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.form.InnerCarSearch.carStatus.all'),//'全部',
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
	},{
		xtype : 'commonowntruckselector',
		fieldLabel: scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.form.InnerCarSearch.vehicleNo.lable'),//'车牌号',
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
			text: scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.form.InnerCarSearch.button.reset.lable'),//'重置',
			handler: function() {
				var form = this.up('form').getForm();
				form.findField('vo.carDto.carOwnerCode').reset();
				form.findField('vo.carDto.truckModel').reset();
				form.findField('vo.carDto.carStatus').reset();
				form.findField('vo.carDto.vehicleNo').reset();	
				//车队
				var depart = FossUserContext.getCurrentDept();
				if(depart.transDepartment=='Y'){
					form.findField('vo.carDto.carOwnerCode').setCombValue(depart.name,depart.code);
				}
			}
		},{
			border : false,
			columnWidth:.84,
			html: '&nbsp;'
		},{
			columnWidth:.08,
			xtype : 'button',
			text: scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.form.InnerCarSearch.button.query.lable'),//'查询',
			cls:'yellow_button',
			handler: function() {
				var bform=this.up('form').getForm();
				if(bform.isValid()){
					var carOwnerCode=bform.findField('vo.carDto.carOwnerCode').getValue();
					var vehicleNo=bform.findField('vo.carDto.vehicleNo').getValue();
					if(!Ext.isEmpty(carOwnerCode)||!Ext.isEmpty(vehicleNo)){
						scheduling.shortScheduleDesign.innerCarPagebar.moveFirst();
					}else{
						Ext.Msg.alert(scheduling.shortScheduleDesign.i18n('foss.shortDeparturePlan.confirm.title.lable'),'车队或车牌号必须填入一个！');
					}
					
				}else{
					Ext.Msg.alert(scheduling.shortScheduleDesign.i18n('foss.shortDeparturePlan.confirm.title.lable'),'请填写完必填项再查询');
				}
				
			}
		}]
	}
	]
});

//公司车查询结果列表
Ext.define('Foss.shortScheduleDesign.grid.InnerCarSearchResult', {
	extend: 'Ext.grid.Panel',
	id:'Foss_shortScheduleDesign_grid_innerCarSearchResult_ID',
	frame: true,
	collapsible: true,
	animCollapse: false,
	title:scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.grid.InnerCarSearchResult.title.lable'),//'查询车辆列表',
	cls:'autoHeight',
	//bodyCls:'autoHeight',
	store: null,
	selModel: null,	
	bbar:null,
	autoScroll:true,
	bodyStyle:{height:60},
	columns: [{
				text: scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.grid.InnerCarSearchResult.carOwnerName.lable'),//'所属车队',
				flex: 1, 
				sortable: true, 
				dataIndex: 'carOwnerName'
			},{
				text: scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.grid.InnerCarSearchResult.vehicleNo.lable'),//'车牌号',
				flex: 1, 
				sortable: true, 
				dataIndex: 'vehicleNo'
			},{
				text: scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.grid.InnerCarSearchResult.carStatusDesc.lable'),//'车辆状态',
				flex: 1, 
				sortable: true, 
				dataIndex: 'carStatusDesc'
			},{
				text: scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.grid.InnerCarSearchResult.truckModelValue.lable'),//'车型',
				flex: 1, 
				sortable: true, 
				dataIndex: 'truckModelValue'
			},{
				text: scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.grid.InnerCarSearchResult.maxLoadWeight.lable'),//'车辆载重(吨)',
				flex: 1, 
				sortable: true, 
				dataIndex: 'maxLoadWeight'
			},{
				text: scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.grid.InnerCarSearchResult.truckVolume.lable'),//'车辆净空(方)',
				flex: 1, 
				sortable: true, 
				dataIndex: 'truckVolume'
			},{
				text: scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.grid.InnerCarSearchResult.driverOrgName.lable'),//'车队小组',
				flex: 1, 
				sortable: true, 
				dataIndex: 'driverOrgName'
			},{
				text: scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.grid.InnerCarSearchResult.driverName.lable'),//'司机',
				flex: 1, 
				sortable: true, 
				dataIndex: 'driverName'
			}],
			constructor: function(config){
				var me = this,
				cfg = Ext.apply({}, config);				
				me.store = Ext.create('Foss.shortScheduleDesign.store.InnerCar');
				me.bbar = Ext.create('Deppon.StandardPaging',{
					store:me.store,
					pageSize:5
				});
				scheduling.shortScheduleDesign.innerCarPagebar=me.bbar ;
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
					var form = Ext.getCmp('Foss_shortScheduleDesign_form_shortInnerCar_ID');
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
Ext.define('Foss.shortScheduleDesign.Window.ShortInnerCar', {
	extend:'Ext.window.Window',
	title: scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.Window.ShortInnerCar.title.lable'),//'加发公司车',
	modal:true,
	closeAction:'hide',
	width: 1024,	
	bodyCls: 'autoHeight',
	layout: 'auto',	
	items:[
		scheduling.shortScheduleDesign.innerCarSearchForm=Ext.create('Foss.shortScheduleDesign.form.InnerCarSearch'),,//Ext.getCmp('Foss_shortScheduleDesign_form_innerCarSearch_ID')==null?Ext.create('Foss.shortScheduleDesign.form.InnerCarSearch'):Ext.getCmp('Foss_shortScheduleDesign_form_innerCarSearch_ID'),
		scheduling.shortScheduleDesign.innerCarSearchResultGrid=Ext.create('Foss.shortScheduleDesign.grid.InnerCarSearchResult'),//Ext.getCmp('Foss_shortScheduleDesign_grid_innerCarSearchResult_ID')==null?Ext.create('Foss.shortScheduleDesign.grid.InnerCarSearchResult'):Ext.getCmp('Foss_shortScheduleDesign_grid_innerCarSearchResult_ID'),
		scheduling.shortScheduleDesign.shortInnerCarForm=Ext.create('Foss.shortScheduleDesign.form.ShortInnerCar')//Ext.getCmp('Foss_shortScheduleDesign_form_shortInnerCar_ID')==null?Ext.create('Foss.shortScheduleDesign.form.ShortInnerCar'):Ext.getCmp('Foss_shortScheduleDesign_form_shortInnerCar_ID'),
	],
	listeners:{
		hide:function( component, eOpts ){
			var grid=Ext.getCmp('Foss_shortScheduleDesign_grid_innerCarSearchResult_ID');
			if(grid)
			grid.getStore().removeAll();
		}		
	}
});

//发车计划日志
Ext.define('Foss.shortScheduleDesign.model.PlanLog', {
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
		{name: 'modifyContent', type: 'string'}//修改内容
	]
});

//发车计划日志数据源
Ext.define('Foss.shortScheduleDesign.store.PlanLog',{
	extend: 'Ext.data.Store',
	//绑定一个模型
	model: 'Foss.shortScheduleDesign.model.PlanLog',
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
					'vo.detailDto.truckDepartPlanId':scheduling.shortScheduleDesign.planId,
					'vo.detailDto.id':scheduling.shortScheduleDesign.planDetailId					
					};
			
			Ext.apply(operation, {
				params : queryParams
			});	
		},load:function(){
			//清除planDetailId
			//scheduling.shortScheduleDesign.planDetailId=null;
		}
	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});



//发车计划日志列表
Ext.define('Foss.shortScheduleDesign.gird.PlanLog',{
		extend: 'Ext.grid.Panel',
		title:scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.gird.PlanLog.title.lable'),//'发车计划修改记录',
		frame:true,
		animCollapse: false,
		//hideHeaders:true,
		cls:'autoHeight',
		bodyCls:'autoHeight',
		store: null,
		selModel: null,	
		autoScroll:true,
		columns: [{
				text: scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.gird.PlanLog.createTime.lable'),//'修改时间',
				minWidth:130,
				sortable: true, 
				dataIndex: 'createTime'
			},{
				text: scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.gird.PlanLog.createUserName.lable'),//'操作人',
				minWidth:100,
				sortable: true, 
				dataIndex: 'createUserName'
			},{
				xtype: 'linebreakcolumn',
				text: scheduling.shortScheduleDesign.i18n('foss.shortScheduleDesign.gird.PlanLog.modifyContent.lable'),//'操作描述',
				flex: 1, 
				sortable: true, 
				dataIndex: 'modifyContent'
			}],
		constructor: function(config){
			var me = this,
			cfg = Ext.apply({}, config);
			me.store = Ext.create('Foss.shortScheduleDesign.store.PlanLog');
			me.bbar = Ext.create('Deppon.StandardPaging',{
				store:me.store,
				pageSize:5
			});
			scheduling.shortScheduleDesign.planLogPagebar=me.bbar;
			me.callParent([cfg]);	 
		}
});

//查询货量预测信息
scheduling.shortScheduleDesign.queryForecastInfo=function(){
	var actionUrl=scheduling.realPath('queryForecastInfo.action');
	var queryParams={
						'vo.planDto.origOrgCode':scheduling.shortScheduleDesign.origOrgCode,// 出发部门
						'vo.planDto.destOrgCode':scheduling.shortScheduleDesign.destOrgCode,//到达部门
						'vo.planDto.planDate':scheduling.shortScheduleDesign.planDate,//计划日期
						'vo.planDto.id':scheduling.shortScheduleDesign.planId,//计划ID
						'vo.planDto.planType':'SHORT'//长途发车计划
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
				var record=Ext.create('Foss.shortScheduleDesign.model.ForecastForPlan',json.vo.forecastDto)
				scheduling.shortScheduleDesign.forecastInfo.loadRecord(record);
				//出发部门名称、到达部门名称
				scheduling.shortScheduleDesign.origOrgName=json.vo.forecastDto.origOrgName;
				scheduling.shortScheduleDesign.destOrgName=json.vo.forecastDto.destOrgName;
			}
			
		},
		exception : function(response) {
			var json = Ext.decode(response.responseText);
			Ext.MessageBox.alert(scheduling.shortScheduleDesign.i18n('foss.shortDeparturePlan.error.title.lable'),json.message);
		}		
	});	
}


//查询本线路 当前日期 班次的可用的最大值
scheduling.shortScheduleDesign.queryMaxfrequencyNo=function(form){
	var actionUrl=scheduling.realPath('queryMaxfrequencyNo.action');
	var lineNo=form.findField('lineNo').getValue();
	//线路不为空
	if(!Ext.isEmpty(lineNo)){
		var queryParams={
							'vo.detailDto.origOrgCode':scheduling.shortScheduleDesign.origOrgCode,// 出发部门
							'vo.detailDto.destOrgCode':scheduling.shortScheduleDesign.destOrgCode,//到达部门
							'vo.detailDto.departDate':scheduling.shortScheduleDesign.planDate,//计划日期
							'vo.detailDto.lineNo':lineNo,//线路编码
							'vo.detailDto.planType':'SHORT'//长途发车计划
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
scheduling.shortScheduleDesign.queryPlatformNo=function(form){
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
		//设置当前部门编码
		//FossUserContext. getCurrentDeptCode();
		//货量预测信息
		var forecastInfo=Ext.getCmp('Foss_shortScheduleDesign_form_shipmentsForecast_ID')==null?Ext.create('Foss.shortScheduleDesign.form.ShipmentsForecast'):Ext.getCmp('Foss_shortScheduleDesign_form_shipmentsForecast_ID');
		scheduling.shortScheduleDesign.forecastInfo=forecastInfo;
		//发车计划明细
		var planDetail=Ext.create('Foss.shortScheduleDesign.gird.PlanDetail');
		scheduling.shortScheduleDesign.planDetail=planDetail;
		//发车计划日志表格
		var planLog = Ext.create('Foss.shortScheduleDesign.gird.PlanLog');
		scheduling.shortScheduleDesign.planLog = planLog;
		//显示查询发车计划界面
		Ext.create('Ext.panel.Panel',{
		id:'T_scheduling-shortScheduleDesignIndex_content',
		cls:"panelContentNToolbar",
		bodyCls:'panelContent-body',
		layout:'auto',
		margin:'0 0 0 0',
		items : [forecastInfo,planDetail,planLog],
		renderTo: 'T_scheduling-shortScheduleDesignIndex-body'
	});	
		scheduling.shortScheduleDesign.datailPagebar.moveFirst();
		//查询合发记录
		scheduling.shortScheduleDesign.loadMergeLog();
		//查询修改记录
		scheduling.shortScheduleDesign.loadUpdateLog();
		//查询货量预测信息
		scheduling.shortScheduleDesign.queryForecastInfo();
});