//package Foss.pkpShortScheduleModify
//接货小区list 在删除、保存、切换的时候一定要置为空
scheduling.pkpShortScheduleModify.pkpAreas=[];
var flag;
Ext.define('Foss.pkpShortScheduleModify.model.TruckSchedulingTaskDetail', {
	extend: 'Ext.data.Model',
	// 定义字段
	fields: [
	    {name: 'carOwner',type:'string'},
	    {name: 'carOwnerName',type:'string'},
	    {name: 'departTime',type:'date'},// 发车时间
	    {name: 'departTimeShort',type:'string'},
	    {name: 'driverCode',type:'string'},
	    {name: 'driverName',type:'string'},
	    {name: 'driverOrgCode',type:'string'},
	    {name: 'driverOrgName',type:'string'},
	    {name: 'driverPhone',type:'string'},
	    //{name: 'frequencyNo',type:'string'},// 班次
	    {name: 'lineName',type:'string'},
	    {name: 'lineNo',type:'string'},// 线路	    
	    {name: 'planType',type:'string'},
	    {name: 'scheduleId',type:'string'},	    
	    {name: 'scheduleTaskId',type:'string'},
	    {name: 'schedulingDate',type:'date'},		
	    {name: 'taskNo',type:'number'},
	    {name: 'truckModel',type:'string'},// 车型
	    {name: 'truckModelValue',type:'string'},
	    {name: 'vehicleNo',type:'string'},		
	    {name: 'ymd',type:'string'},		
	    {name: 'zoneName',type:'string'},
	    {name: 'deliveryAreaName',type:'string'},//送货区域
		{name: 'tOptTruckSchedulingId',type:'string'},// 短途排班表ID
		{name: 'tsId',type:'string'},// 短途排班任务列表ID
		{name: 'taskStatus', type: 'string'},// 任务状态
		{name: 'createUserCode', type: 'string'},// 创建人代码
		{name: 'updateUserCode', type: 'string'},// 更新人代码
		{name: 'pkpRegionalName',type:'string'},//非节假日排班接货区域
		{name: 'deliveryRegionalName',type:'string'},//节假日排班送货区域
		{name: 'expectedBringVolume',type:'string'},
		//###########################################
		{name: 'dispatchVehicleTask',type:'string'},// 出车任务
		{name:'expectedDispatchVehicleTime',type:'string'},// 预计出车时间
		{name: 'isTheTwoTripTime',type:'string'},//预计二次出车时间
		{name: 'takeGoodsDepartment',type:'string'},//带货部门code
		{name: 'takeGoodsDepartmentName',type:'string'},//带货部门名称
		{name: 'transferGoodsDepartment',type:'string'}, //转货部门code
		{name: 'transferGoodsDepartmentName',type:'string'}, //转货部门名称
		//###########################################
		{name:'frequencyNo',type:'string',
			convert: function(value) {
				if(value == '1') {
					return '1'; 
				}else if(value == '2') {
					return '2'; 
				}else if(value == '3') {
					return '3'; 
				}
			}
		}
		//{name:'isBringExpress',type:'string'}
	]
});
// 短途排班任务列表模型
Ext.define('Foss.pkpShortScheduleModify.model.TruckSchedulingTask', {
	extend: 'Ext.data.Model',
	// 定义字段
	fields: [
			{name: 'driverName',type:'string'},//司机名称
			{name: 'driverCode',type:'string'},//司机代码
			{name: 'driverPhone',type:'string'},//司机电话
			{name: 'driverOrgCode',type:'string'},//司机所属部门
			{name: 'schedulingMonth',type:'date'},//排班日期
			{name: 'yearMonth',type:'string'},//排班年月
			{name: 'date1',type:'string'},//一月的排班数据
			{name: 'date2',type:'string'},
			{name: 'date3',type:'string'},
			{name: 'date4',type:'string'},
			{name: 'date5',type:'string'},
			{name: 'date6',type:'string'},
			{name: 'date7',type:'string'},
			{name: 'date8',type:'string'},
			{name: 'date9',type:'string'},
			{name: 'date10',type:'string'},
			{name: 'date11',type:'string'},
			{name: 'date12',type:'string'},
			{name: 'date13',type:'string'},
			{name: 'date14',type:'string'},
			{name: 'date15',type:'string'},
			{name: 'date16',type:'string'},
			{name: 'date17',type:'string'},
			{name: 'date18',type:'string'},
			{name: 'date19',type:'string'},
			{name: 'date20',type:'string'},
			{name: 'date21',type:'string'},
			{name: 'date22',type:'string'},
			{name: 'date23',type:'string'},
			{name: 'date24',type:'string'},
			{name: 'date25',type:'string'},
			{name: 'date26',type:'string'},
			{name: 'date27',type:'string'},
			{name: 'date28',type:'string'},
			{name: 'date29',type:'string'},
			{name: 'date30',type:'string'},
			{name: 'date31',type:'string'},
			{name: 'workTotal',type:'int'}//上班天数
	        ]
});

//查询排班统计表数据源
Ext.define('Foss.pkpShortScheduleModify.store.TruckSchedulingTask',{
	extend: 'Ext.data.Store',
	model: 'Foss.pkpShortScheduleModify.model.TruckSchedulingTask',
	pageSize:10,
	proxy: {
		type: 'ajax',
		url:scheduling.realPath('queryTruckSchedulingList.action'),
		actionMethods:'post',
		reader: {
			type: 'json',
			root: 'vo.tsDtos',
			totalProperty : 'totalCount'
		}
	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	},listeners: {
		beforeload : function(store, operation, eOpts) {
				//TODO 根据实际情况获取参数
				var queryParams ={'vo.tsEntity.yearMonth':scheduling.pkpShortScheduleModify.ymd,
						'vo.tsEntity.schedulingType':'PKP','vo.tsEntity.schedulingDepartCode':scheduling.pkpShortScheduleModify.schedulingDepartCode};				
				Ext.apply(operation, {
					params : queryParams
				});
		}
	}
});

//工作类型
Ext.define('Foss.pkpShortScheduleModify.model.PlanTypeCombox', {
	extend: 'Ext.data.Model',
	//定义字段
	fields: [
		{name: 'id',type:'String'},//ID
		{name: 'des',type:'string'}//描述
	]
});

//审核状态数据源
Ext.define('Foss.pkpShortScheduleModify.store.PlanTypeCombox',{
	extend: 'Ext.data.Store',
	model: 'Foss.pkpShortScheduleModify.model.PlanTypeCombox',
	data : {
		'items':[
			{id : 'REST', des : scheduling.pkpShortScheduleModify.i18n('foss.pkpQueryShortSchedule.grid.TruckSchedulingTask.planType.REST')},//'休息'
			{id : 'WORK', des : scheduling.pkpShortScheduleModify.i18n('foss.pkpQueryShortSchedule.grid.TruckSchedulingTask.planType.WORK')},//'出车'
			{id : 'DUTY', des : scheduling.pkpShortScheduleModify.i18n('foss.pkpQueryShortSchedule.grid.TruckSchedulingTask.planType.DUTY')},//'值班'
			{id : 'TRAINING', des : scheduling.pkpShortScheduleModify.i18n('foss.pkpQueryShortSchedule.grid.TruckSchedulingTask.planType.TRAINING')},//'培训'
			{id : 'LEAVE', des : scheduling.pkpShortScheduleModify.i18n('foss.pkpQueryShortSchedule.grid.TruckSchedulingTask.planType.LEAVE')},//'离岗'
			{id : 'UNKNOWN', des : scheduling.pkpShortScheduleModify.i18n('foss.pkpQueryShortSchedule.grid.TruckSchedulingTask.planType.UNKNOWN')}//'未知'
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



//排班表
Ext.define('Foss.pkpShortScheduleModify.grid.TruckSchedulingTaskGrid', {
	extend: 'Ext.grid.Panel',
	frame:true,
	title:'',
	cls:'autoHeight',
	bodyCls:'autoHeight',
	store: null,
	autoScroll:true,
	columns:null,	
	getColumn:function(){
		return this.columns;
	},
	viewConfig: {
	    stripeRows: false
	},	
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.columns=config.columns;
		me.store=Ext.create('Foss.pkpShortScheduleModify.store.TruckSchedulingTask');
		me.bbar = Ext.create('Deppon.StandardPaging',{
			store:me.store,
			pageSize:10,
			plugins: 'pagesizeplugin'
		});
		scheduling.pkpShortScheduleModify.pagebar=me.bbar ;
		me.callParent([cfg]);
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
		   items:[
				{
					xtype:'label',
					columnWidth:.05,
					style: 'background-color:#ffe2e2;',
					html :'&nbsp;',
					height:20
				},{
					type:'textfield',
					columnWidth:.05,
					value :'休息'
				},{
					xtype:'label',
					columnWidth:.05,
					style: 'background-color:#FFB0C4;',
					html :'&nbsp;',
					height:20
				},{
					type:'textfield',
					columnWidth:.05,
					value :'离岗'
				},
				{
					xtype:'label',
					columnWidth:.05,
					style: 'background-color:#e2ffe2;',
					html :'&nbsp;',
					height:20
						
				},{	
					type:'textfield',
					columnWidth:.15,
					value :'上班(值班、出车、培训)'
				}
    ]
	}]
});
//接送货小区model
Ext.define('Foss.pkpShortScheduleModify.model.AreaModel',{
	extend: 'Ext.data.Model',
	//定义字段
	fields: [
		{name: 'zoneName',type:'string'},//ID
		{name: 'zoneCode',type:'string'},//描述
		{name: 'regionType',type:'string'}//接送货类型
	]
});
//接送货小区store
Ext.define('Foss.pkpShortScheduleModify.store.AreaStore',{
	extend: 'Ext.data.Store',
	model: 'Foss.pkpShortScheduleModify.model.AreaModel',
	data : {
		'items':[]
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
Ext.define('Foss.pkpShortScheduleModify.grid.AreaGrid', {
	extend : 'Ext.grid.Panel',
	columnLines: true,
	title:'非工作日预设小区',
	frame: true,
	enableColumnHide : false,//配置该属性可取消grid自定义显示列功能
	//baseCls : 'handOverBill_addNew_serialNoGap',
	hight:400,
	width:487,
	cls:'autoHeight',
	bodyCls:'autoHeight',
	defaultType: 'textfield',	
	defaults: {
		margin:'5 10 5 10',
		anchor: '90%',
		labelWidth:95
	},
	store : Ext.create('Foss.pkpShortScheduleModify.store.AreaStore'),
	tbar:[{
			xtype : 'commonsmallzoneselector',
			regionType:'PK',
			name : 'pkpAreaZone',
			fieldLabel : '接货小区',
			management : scheduling.pkpQueryShortSchedule.topFleetOrgCode,
			//id:'Foss_pkpShortScheduleModify_quickAddArea_ID',
			//maxLength : 50,
			allowBlank : true,
			readOnly : false
		},{
			xtype : 'button',
			width : 75,
			text : '添加小区',
			margin:'0 0 0 40',
			handler : function(button, rowIndex, colIndex) {
				//获取button上一级tbar
				var ntbar = button.ownerCt;
				//获取接货小区控件
				var pkpAreaZonefield =ntbar.items.items[0];
				//获取接货小区名称
				var pkpAreaName=pkpAreaZonefield.getRawValue();
				//获取接货小区code
				var pkpAreaCode=pkpAreaZonefield.getValue();
				//设置接送货类型为‘PK’
				var regionType='PK';
				//获取tbar的上一级grid（也可以通过this.up('grid')获取）
				var grid = ntbar.ownerCt;
				//获取gride的store
				var store = grid.getStore();
			
				
				var areaEntity=new Object();
				
				 areaEntity.zoneName = pkpAreaName;
				 areaEntity.zoneCode = pkpAreaCode;
				 areaEntity.regionType = regionType;
				var records = new Foss.pkpShortScheduleModify.model.AreaModel(areaEntity);
				var exist = 'N';
				if(!Ext.isEmpty(pkpAreaCode) && pkpAreaCode!=''){
					
					store.each(function(record) {
								if (pkpAreaCode == record.get('zoneCode')) {
									exist = 'Y';
								}
							});
					if (exist == 'Y') {
						Ext.ux.Toast.msg('错误提示','已经存在，不能重复添加！');
						return;
					}
					store.add(records);
					//添加成功后控件置为空
					pkpAreaZonefield.setValue("");
				}
			}
		}
	],
	
	
	columns : [ {
		xtype : 'actioncolumn',
		width : 80,
		//flex : 0.1,
		text : "操作"/*'操作'*/,
		align : 'center',
		items : [ {
			tooltip :"删除" /*'删除'*/,
			iconCls : 'deppon_icons_remove',
			handler : function(grid, rowIndex, colIndex) {
				//if(grid.store.getCount() == 1){
					//Ext.ux.Toast.msg("提示")/*'提示'*/, ""/*''*/, 'error', 1500);
					//return;
				//}
				//从unsavedSerialNoMap中删除该流水号
				var record = grid.getStore().getAt(rowIndex);
				grid.store.removeAt(rowIndex);
			}
		} ]
	}, {
		dataIndex : 'zoneName',
		align : 'center',
		width : 350,
		//flex : 0.5,
		xtype : 'ellipsiscolumn',
		text : "接货区域名称"/*'接货区域名称'*/
	},{
		dataIndex : 'zoneCode',
		hidden:true,
		//flex : 0.2,
		//xtype : 'ellipsiscolumn',
		text : "接货区域编码"/*'接货区域编码'*/
	}],
	dockedItems: [{
		xtype: 'toolbar',
		dock: 'bottom',
		layout : 'column',
		defaults: {
			xtype : 'button',
			readOnly : true,
			labelWidth : 120
		},
		items: [{
			xtype : 'button',
			width : 75,
			text : '提交',
			handler : function() {
				//获取grid
				var grid = this.up('grid');
				//获取表单store
				var store =grid.getStore();
				var names='';
				var j = 0;
				//保存前先置空
				if(scheduling.pkpShortScheduleModify.pkpAreas!=null){
					length=scheduling.pkpShortScheduleModify.pkpAreas.length;
				}
				for(i=0;i<length;i++){
						var records = Ext.ModelManager.create(scheduling.pkpShortScheduleModify.pkpAreas[j],'Foss.pkpShortScheduleModify.model.AreaModel')
						if('PK' == records.data.regionType || null == records.data.regionType || '' == records.data.regionType) {
							scheduling.pkpShortScheduleModify.pkpAreas.splice(j,1);
						}else {
							j++;
						}
				}
				//scheduling.pkpShortScheduleModify.pkpAreas=[];
				for(i=0;i<store.getCount();i++){
					var record = store.getAt(i);
					record.data.regionType='PK';
					//拼接接货小区名称
					if(i==(store.getCount()-1)){
						names=names+record.data.zoneName;
					}else{
						names=names+record.data.zoneName+"，";
					}
					//保存接货小区code，name
					scheduling.pkpShortScheduleModify.pkpAreas.push(record.data);
					
				}
				//获取panel下的排班信息表单
				var mainPanel = grid.ownerCt;
				var form = mainPanel.items.items[0];
				form.getForm().findField('pkpRegionalName').setValue(names);
				
				
			}
		}]
	}]
});

Ext.define('Foss.pkpShortScheduleModify.grid.deAreaGrid', {
	extend : 'Ext.grid.Panel',
	columnLines: true,
	title:' ',
	frame: true,
	enableColumnHide : false,//配置该属性可取消grid自定义显示列功能
	//baseCls : 'handOverBill_addNew_serialNoGap',
	hight:400,
	width:487,
	cls:'autoHeight',
	bodyCls:'autoHeight',
	defaultType: 'textfield',	
	defaults: {
		margin:'5 10 5 10',
		anchor: '90%',
		labelWidth:95
	},
	store : Ext.create('Foss.pkpShortScheduleModify.store.AreaStore'),
	tbar:[{
			xtype : 'commonsmallzoneselector',
			regionType:'DE',
			name : 'pkpAreaZone',
			fieldLabel : '送货小区',
			management : scheduling.pkpQueryShortSchedule.topFleetOrgCode,
			//id:'Foss_pkpShortScheduleModify_quickAddArea_ID',
			//maxLength : 50,
			allowBlank : true,
			readOnly : false
		},{
			xtype : 'button',
			width : 75,
			text : '添加小区',
			margin:'0 0 0 40',
			handler : function(button, rowIndex, colIndex) {
				//获取button上一级tbar
				var ntbar = button.ownerCt;
				//获取送货小区控件
				var pkpAreaZonefield =ntbar.items.items[0];
				//获取送货小区名称
				var pkpAreaName=pkpAreaZonefield.getRawValue();
				//获取送货小区code
				var pkpAreaCode=pkpAreaZonefield.getValue();
				//设置接送货类型为‘DE’
				var regionType='DE';
				//获取tbar的上一级grid（也可以通过this.up('grid')获取）
				var grid = ntbar.ownerCt;
				//获取gride的store
				var store = grid.getStore();
			
				
				var areaEntity=new Object();
				
				 areaEntity.zoneName = pkpAreaName;
				 areaEntity.zoneCode = pkpAreaCode;
				 areaEntity.regionType = regionType;
				var records = new Foss.pkpShortScheduleModify.model.AreaModel(areaEntity);
				var exist = 'N';
				if(!Ext.isEmpty(pkpAreaCode) && pkpAreaCode!=''){
					
					store.each(function(record) {
								if (pkpAreaCode == record.get('zoneCode')) {
									exist = 'Y';
								}
							});
					if (exist == 'Y') {
						Ext.ux.Toast.msg('错误提示','已经存在，不能重复添加！');
						return;
					}
					store.add(records);
					//添加成功后控件置为空
					pkpAreaZonefield.setValue("");
				}
			}
		}
	],
	
	
	columns : [ {
		xtype : 'actioncolumn',
		width : 80,
		//flex : 0.1,
		text : "操作"/*'操作'*/,
		align : 'center',
		items : [ {
			tooltip :"删除" /*'删除'*/,
			iconCls : 'deppon_icons_remove',
			handler : function(grid, rowIndex, colIndex) {
				//if(grid.store.getCount() == 1){
					//Ext.ux.Toast.msg("提示")/*'提示'*/, ""/*''*/, 'error', 1500);
					//return;
				//}
				//从unsavedSerialNoMap中删除该流水号
				var record = grid.getStore().getAt(rowIndex);
				grid.store.removeAt(rowIndex);
			}
		} ]
	}, {
		dataIndex : 'zoneName',
		align : 'center',
		width : 350,
		//flex : 0.5,
		xtype : 'ellipsiscolumn',
		text : "送货区域名称"/*'送货区域名称'*/
	},{
		dataIndex : 'zoneCode',
		hidden:true,
		//flex : 0.2,
		//xtype : 'ellipsiscolumn',
		text : "送货区域编码"/*'送货区域编码'*/
	}],
	dockedItems: [{
		xtype: 'toolbar',
		dock: 'bottom',
		layout : 'column',
		defaults: {
			xtype : 'button',
			readOnly : true,
			labelWidth : 120
		},
		items: [{
			xtype : 'button',
			width : 75,
			text : '提交',
			handler : function() {
				//获取grid
				var grid = this.up('grid');
				//获取表单store
				var store =grid.getStore();
				var names='';
				var j = 0;
				//保存前先置空
				if(scheduling.pkpShortScheduleModify.pkpAreas!=null){
					length=scheduling.pkpShortScheduleModify.pkpAreas.length;
				}
				for(i=0;i<length;i++){
						var records = Ext.ModelManager.create(scheduling.pkpShortScheduleModify.pkpAreas[j],'Foss.pkpShortScheduleModify.model.AreaModel')
						if('DE' == records.data.regionType) {
							scheduling.pkpShortScheduleModify.pkpAreas.splice(j,1)
						}else {
							j++;
						}
				}
				//scheduling.pkpShortScheduleModify.pkpAreas=[];
				for(i=0;i<store.getCount();i++){
					var record = store.getAt(i);
					//拼接接货小区名称
					if(i==(store.getCount()-1)){
						names=names+record.data.zoneName;
					}else{
						names=names+record.data.zoneName+"，";
					}
					//保存接货小区code，name
					scheduling.pkpShortScheduleModify.pkpAreas.push(record.data);
					
				}
				//获取panel下的排班信息表单
				var mainPanel = grid.ownerCt;
				var form = mainPanel.items.items[0];
				form.getForm().findField('deliveryRegionalName').setValue(names);
				
				
			}
		}]
	}]
});

//排班表单
Ext.define('Foss.pkpShortScheduleModify.panle.ScheduleForms',{
	extend:'Ext.panel.Panel',
	id:'Foss_pkpShortScheduleModify_panle_scheduleForms_ID',
	margin:'0 5 0 0',
	layout:'column',
	frame:true,
	cls:'autoHeight',
	bodyCls:'autoHeight',
	items:[],
	tbar:[{
		xtype: 'button', 
		text: scheduling.pkpShortScheduleModify.i18n('foss.shortScheduleModify.panle.ScheduleForms.button.addNew.lable'),//'新增任务',
		hidden:true,
		id:'Foss_pkpShortScheduleModify_panle_scheduleForms_button_ID',
		handler: function() {
			//动态新增任务表单
			scheduling.pkpShortScheduleModify.addTaskNewTask();
		}
	}]
});


//接送货排班表单
Ext.define('Foss.pkpShortScheduleModify.form.ScheduleForm', {
	extend: 'Ext.form.Panel',	
	layout:'column',
	title:scheduling.pkpShortScheduleModify.i18n('foss.shortScheduleModify.form.ScheduleForm.title.lable'),//'排班信息',
	bodyStyle:'padding:5px 5px 0',	
	cls:'autoHeight',
	bodyCls:'autoHeight',
	width:984,
	frame:true,
	defaultType: 'textfield',	
	defaults: {
		margin:'5 10 5 10',
		anchor: '90%',
		labelWidth:95
	},
	items: [
	        {xtype : 'commonowntruckselector',fieldLabel: scheduling.pkpShortScheduleModify.i18n('foss.pkpQueryShortSchedule.form.PkpTruckSchedulingSearch.vehicleNo.lable'),name: 'vehicleNo',allowBlank:false,columnWidth:.25,//'车牌号',
	        	listeners:{
					blur:function(txtField,eOpts ){
						txtField.setValue(Ext.String.trim(txtField.getValue()));
						var vehicleNo=txtField.value;
						if(!Ext.isEmpty(vehicleNo)){
							scheduling.pkpShortScheduleModify.queryCarInfoByVehicleNo(this.up('form'),vehicleNo);
							scheduling.pkpShortScheduleModify.queryZoneCodeByVehicleNo(this.up('form'),vehicleNo);
						}						
					},
				   change:function(  txtField,  newValue,  oldValue,  eOpts ){
					   txtField.setValue(Ext.String.trim(txtField.getValue()));
						var vehicleNo=txtField.value;
					   //重置临时小区设置
					   scheduling.pkpShortScheduleModify.pkpAreas=[];
					   this.up('form').getForm().findField('pkpRegionalName').setValue('');
					   this.up('form').getForm().findField('deliveryRegionalName').setValue('');
					   //检查一个车同一天只能安排一个司机
					   scheduling.pkpShortScheduleModify.checkVehicle(this.up('form'),vehicleNo);
					   //换车牌号时，查询最新的预设接货小区，并填冲到预设小区grid中
					   scheduling.pkpShortScheduleModify.querynewPkpAreaByVehicleNo(this.up('form'),vehicleNo);
				   }
	        	
				}},
	        {xtype : 'commonvehicletypeselector',fieldLabel: scheduling.pkpShortScheduleModify.i18n('foss.pkpQueryShortSchedule.grid.TruckSchedulingTask.truckModelValue.lable'),name: 'truckModel',allowBlank:false,columnWidth:.25,readOnly:true},//'车型'
	        {fieldLabel: scheduling.pkpShortScheduleModify.i18n('foss.pkpQueryShortSchedule.grid.TruckSchedulingTask.carOwnerName.lable'),name: 'carOwnerName',allowBlank:false,columnWidth:.25,readOnly:true},//'车辆所属车队'	        
	        {fieldLabel: scheduling.pkpShortScheduleModify.i18n('foss.pkpQueryShortSchedule.grid.TruckSchedulingTask.driverPhone.lable'),name: 'driverPhone',allowBlank:false,columnWidth:.25,readOnly:true},//'司机电话'
	        {fieldLabel: scheduling.pkpShortScheduleModify.i18n('foss.pkpQueryShortSchedule.window.ImportShortSchedule.area.lable'),labelWidth:110,name: 'zoneName',allowBlank:true,columnWidth:.25,readOnly:true}, //'接送货区域'
	        {fieldLabel: scheduling.pkpShortScheduleModify.i18n('foss.pkpQueryShortSchedule.window.ImportShortSchedule.pkparea.lable'),labelWidth:110 ,name:'pkpRegionalName' ,allowBlank:true,columnWidth:.7,readOnly:true},//'节假日接货小区'
	        {fieldLabel: scheduling.pkpShortScheduleModify.i18n('foss.pkpQueryShortSchedule.window.ImportShortSchedule.deliveryArea.lable'),labelWidth:110,name: 'deliveryAreaName',allowBlank:true,columnWidth:.25,readOnly:true}, //'接送货区域'
	        {fieldLabel: scheduling.pkpShortScheduleModify.i18n('foss.pkpQueryShortSchedule.window.ImportShortSchedule.deliveryRegional.lable'),labelWidth:110 ,name:'deliveryRegionalName' ,allowBlank:true,columnWidth:.7,readOnly:true},//'节假日送货小区'
			
	        {
	        	xtype: 'combobox',
	    		name:'dispatchVehicleTask',
	    		editable:false,
//	    		displayField:'text',
//	    		valueField:'value',
	    		margin:'5 0 0 0',
	    		displayField:'valueName',
	    		valueField:'valueCode',
	    		store:FossDataDictionary.getDataDictionaryStore("DISPATCH_VEHICLE_TASK"),
	    		allowBlack:false,
	    		fieldLabel : '出车任务',  //品类
	    		maxLength:50,
	    		columnWidth:.35,
	    		value:'1',
	   	 listeners : {
			 afterrender:{
				 fn: function(){
//					 var isTheTwoTripTime=this.up('form').getForm().findField('isTheTwoTripTime');
//					 if(!isTheTwoTripTime.isHidden()){
//						this.up('form').getForm().findField('isTheTwoTripTime').allowBlank=false; 
//					 }else{
					 this.up('form').getForm().findField('transferGoodsDepartment').setValue("");
						 this.up('form').getForm().findField('isTheTwoTripTime').allowBlank=true; 
//					 }
						
				 } 
			 },
				change : {
					fn : function(src, newValue, oldValue, eOpts) {
						var value = (newValue && true == newValue);
//						选择“带+送+转”或者“送+转”
						if(newValue==4||newValue==2){
//							this.up('form').getForm().findField('transferGoodsDepartment').setValue("");
							this.up('form').getForm().findField('transferGoodsDepartment').setVisible(true);
							
						}else{
							this.up('form').getForm().findField('transferGoodsDepartment').setValue("");
							this.up('form').getForm().findField('transferGoodsDepartment').setVisible(false);
						}//选择“带+送+接”、“带+送+转”、“带+二次派送”这三种类型，则系统显示带货部门、预计带货方数（F），否则预计带货方数（F）字段隐藏
						if(newValue==3||newValue==4||newValue==6){
							this.up('form').getForm().findField('takeGoodsDepartment').setVisible(true);
							this.up('form').getForm().findField('expectedBringVolume').setVisible(true);
						}else{
							this.up('form').getForm().findField('takeGoodsDepartment').setValue("");
							this.up('form').getForm().findField('expectedBringVolume').setValue("");
							this.up('form').getForm().findField('takeGoodsDepartment').setVisible(false);
							this.up('form').getForm().findField('expectedBringVolume').setVisible(false);
							
						}//若选择“二次派送”或“带+二次派送”，则系统显示预计二次出车时间，否则，此字段隐藏
						if(newValue==5||newValue==6){
							this.up('form').getForm().findField('isTheTwoTripTime').setVisible(true);
							this.up('form').getForm().findField('isTheTwoTripTime').allowBlank=false;
						}else{
							this.up('form').getForm().findField('isTheTwoTripTime').setValue("");
							this.up('form').getForm().findField('isTheTwoTripTime').setVisible(false);
							this.up('form').getForm().findField('isTheTwoTripTime').allowBlank=true;
						}
						if(newValue==1){
							this.up('form').getForm().findField('expectedDispatchVehicleTime').allowBlank=false;
						}
					}
				   }
    	        }
             },

	        {fieldLabel: '班次',name: 'frequencyNo',columnWidth:.25,allowBlank:false,
	        	store : Ext.create('Ext.data.Store', {
	    			fields : ['valueName', 'valueCode'],
	    		data : [{
				'valueName' : '1',
				'valueCode' : '1'
	    		},		// 
	    		{
	    			'valueName' : '2',
	    			'valueCode' : '2'
	    		},
	    		{
	    			'valueName' : '3',
	    			'valueCode' : '3'
	    		}]
	    		}),
	    		xtype : 'combobox',
	    		queryMode : 'local',
				displayField : 'valueName',
				valueField : 'valueCode',
				//hidden :true,
				editable : false,
				forceSelection : true,
				triggerAction : 'all',
				listeners : {
					change : {
						fn : function(src, newValue, oldValue, eOpts) {
							var value = (newValue && true == newValue);
							//①若班次为1班车,则预计出车时间默认为8:30；
							//②若班次为2班车,则预计出车时间默认为10:30；
							//③若班次为3班车,则预计出车时间默认为12:00;
							//解决修改操作时班次不能带出出车时间判断
							if(newValue!=oldValue&&newValue!=null&&oldValue!=null){
							 	if(newValue=='1'){
									 this.up('form').getForm().findField('expectedDispatchVehicleTime').setValue('8:30');
								}else if(newValue=='2'){
									this.up('form').getForm().findField('expectedDispatchVehicleTime').setValue('10:30');
								}else if(newValue=='3'){
									this.up('form').getForm().findField('expectedDispatchVehicleTime').setValue('12:00');	
								}else{
									this.up('form').getForm().findField('expectedDispatchVehicleTime').setValue('');
								}
							}
							   //解决预计出车时间保存恢复默认值的操作
							    if(!flag){
							    	if(newValue=='1'){
										 this.up('form').getForm().findField('expectedDispatchVehicleTime').setValue('8:30');
									}else if(newValue=='2'){
										this.up('form').getForm().findField('expectedDispatchVehicleTime').setValue('10:30');
									}else if(newValue=='3'){
										this.up('form').getForm().findField('expectedDispatchVehicleTime').setValue('12:00');	
									}else{
										this.up('form').getForm().findField('expectedDispatchVehicleTime').setValue('');
									}
							    }else{
							    	var expectTime=this.up('form').getForm().findField('expectedDispatchVehicleTime').getValue();
							    	this.up('form').getForm().findField('expectedDispatchVehicleTime').setValue(expectTime);
							    }	
					}
	    	   }
			} 
	    },
	    	//预计出车时间
	    	{
	            xtype: 'timefield',
	            name: 'expectedDispatchVehicleTime',
	            fieldLabel: '预计出车时间',
	            editable:true,
	            increment: 5,
	            submitFormat : 'H:i',
	            format : 'H:i',
	            value : '',
	            allowBlank:false,
	            columnWidth:.35,
	            pickerMaxHeight :100
	         }
	       /* {
		    	   xtype : 'checkboxfield',
		    	   name : 'isBringExpress',
		    	   id: 'isBringExpress',
		    	   inputValue : 'Y',
		    	   //checked:true,
		    	   uncheckedValue: 'N',
		    	   columnWidth : 0.20,
		    	   boxLabel : '是否带货',
		    	   listeners : {
						change : {
							fn : function(src, newValue, oldValue, eOpts) {
								var value = (newValue && true == newValue);								
								this.up('form').getForm().findField('expectedBringVolume').setVisible(value);
								if(!value) {
									this.up('form').getForm().findField('expectedBringVolume').setValue('');
								}
								this.up('form').getForm().findField('expectedBringVolume').allowBlank=!value;
								}
						}
		    	   }
		       }*/
	    	,
	    	  {xtype : 'commonsaledepartmentselector',  salesDepartment:'Y',types : 'CPPX,LD,CPLD',fieldLabel: scheduling.pkpShortScheduleModify.i18n('带货部门'),name: 'takeGoodsDepartment',allowBlank:true,hidden:true,columnWidth:.35,//'带货部门',
	        	listeners:{
					blur:function(txtField,eOpts ){
						txtField.setValue(Ext.String.trim(txtField.getValue()));
						var vehicleNo=txtField.value;
					},
				   change:function(  txtField,  newValue,  oldValue,  eOpts ){
					   txtField.setValue(Ext.String.trim(txtField.getValue()));
				   }
	        	
				}},
	        {fieldLabel: '预计带货方数（方/F）',labelWidth:140,name: 'expectedBringVolume',allowBlank:true,columnWidth:.3,
		    	   hidden :true,
		    	   maxLength : 9,
					maxLengthText : '请输入小数点后最多两位且大于零的数字!',
					allowBlank:true,
					validator : function(value){
						if(Ext.isEmpty(value)){
							value=null;
						}
						if(null != value){
							value = value*1;
						}
						if(this.allowBlank && (null == value || 0 == value)){
							return true;
						}else if(!(/^(\d{1,6})(\.\d{1,2})?$/.test(value))){
							return  '请输入小数点后最多两位且大于零的数字!';
						}else if(value < 0){
							return  '请输入小数点后最多两位且大于零的数字!';
						}else if(0 == value){
							return  '请输入小数点后最多两位且大于零的数字!';
						}
						return true;
					}
		       }, 
		       //转货部门
		       {xtype : 'commonsaledepartmentselector',  salesDepartment:'Y',
			    types : 'CPPX,LD,CPLD',fieldLabel: scheduling.pkpShortScheduleModify.i18n('转货部门'),name: 'transferGoodsDepartment',hidden:true,allowBlank:true,columnWidth:.35,//'转货部门',
		        	listeners:{
						blur:function(txtField,eOpts ){
							txtField.setValue(Ext.String.trim(txtField.getValue()));
							var vehicleNo=txtField.value;
						},
					   change:function(  txtField,  newValue,  oldValue,  eOpts ){
						   txtField.setValue(Ext.String.trim(txtField.getValue()));
					   }
		        	
					}},
					//预计二次出车时间
			{
	            xtype: 'timefield',
	            name: 'isTheTwoTripTime',
	            fieldLabel: '预计二次出车时间',
	            labelWidth:140,
	            editable:true,
	            increment: 5,
	            submitFormat : 'H:i',
	            format : 'H:i',
	            value : '',
	            allowBlank:false,
	            columnWidth:.35,
	            hidden :true,
	            pickerMaxHeight :100
	         },
			{xtype: 'hiddenfield',fieldLabel: 'scheduleTaskId',name: 'scheduleTaskId',columnWidth:.25,readOnly:true}//scheduleTaskId
	        ],
	        dockedItems: [{
	        	xtype: 'toolbar',
	        	dock: 'bottom',
	        	buttonAlign:'end',		
	        	items: [{
	        		xtype: 'button',			
	        		text: '删除',
	        		margin:'0 0 0 100',
	        		handler: function() {	
	        			scheduling.pkpShortScheduleModify.deleteSchedulingTask(this.up('form'));
	        		}
	        	},{
		        		xtype: 'button',			
		        		text: '小区临时设置',
		        		margin:'0 0 0 100',
		        		handler: function() {	
		        			scheduling.pkpShortScheduleModify.addpkpAreaTask(this.up('form'));
		        		}
		        },
	        	{xtype:'tbspacer',flex:1},
	        	{
	        		xtype: 'button',			
	        		text: scheduling.pkpShortScheduleModify.i18n('foss.queryShortSchedule.form.ImportShortSchedule.button.save.lable'),//'保存'
	        		margin:'0 0 0 190',
	        		handler: function() {
	        			var form =this.up('form').getForm();
	    				//验证
	    				if(form.isValid( )){
	    					//alert(21213223);
	    					//执行保存
		        			scheduling.pkpShortScheduleModify.saveNewSchedulingTask(this.up('form'));
	    					//if(form.findField('zoneName') != null &&form.findField('zoneName').getValue() != ''){
	    						
	    					//}	        			
		        			//else {
							//	Ext.MessageBox.alert(scheduling.pkpShortScheduleModify.i18n('foss.shortDeparturePlan.confirm.title.lable'),
							//			scheduling.pkpShortScheduleModify.i18n('foss.scheduling.ShortSchedule.tip.vehicleNoUnuseable'));//'本车牌无效，请核实再保存'
							//}
	    				}
	        		}
	        	}]
	        }]
	//,
//	        constructor : function(config) {							//构造器
//	    		var me = this,cfg = Ext.apply({}, config);
//	    		me.items[0].store=FossDataDictionary.getDataDictionaryStore("DISPATCH_VEHICLE_TASK");
//	    		me.callParent([cfg]);
//	    	}
});

//查询状态为出车的任务数据
scheduling.pkpShortScheduleModify.obtainShortTask=function(driverCode,field){
	var actionUrl=scheduling.realPath('queryShortScheduleTaskByDriver.action');
	var day = Ext.String.leftPad(field.substring(4,field.length), 2, '0');
	//原始年月日
	var  ymd =scheduling.pkpShortScheduleModify.ymd;
	//车队小组
	var  schedulingDepartCode =scheduling.pkpShortScheduleModify.schedulingDepartCode;
	//根据原始的日期和选择的列动态解析当前编辑的计划日期
	var schedulingDate=ymd.substring(0,7)+'-'+day;
	// 构建查询参数
	var queryParams={'vo.simDto.ymd':schedulingDate,//年月日
					'vo.simDto.driverCode':driverCode,//司机编码
					'vo.simDto.schedulingDepartCode':schedulingDepartCode,//车队小组
					'vo.simDto.planType':'WORK',//工作类别
					'vo.simDto.schedulingtype':'PKP'};//排班类型（短途排班）
	//请求查询出车的相关排班任务信息
		var window = Ext.getCmp('Foss_pkpShortScheduleModify_panle_scheduleForms_ID');
		if(window){
			window.getEl().mask(scheduling.pkpShortScheduleModify.i18n('foss.shortDeparturePlan.mask.querywaiting.tip'));//"正在查询，请稍等..."
		}
	//增加出车时间判断标识
	flag=true;	
	//执行查询
		Ext.Ajax.request({
			url : actionUrl,
			params:queryParams,
			//动态创建表单，显示任务信息
			success : function(response) {
				var json = Ext.decode(response.responseText);
	        	var imDtos=json.vo.simDtos;
	        	var taskNum = 0;
	        	if(imDtos!=null){
	        		taskNum = imDtos.length;
	        	}
	        	//排班调单列表,用于根据数据动态构建表单,清空动态表单
	        	 var scheduleForms=Ext.getCmp('Foss_pkpShortScheduleModify_panle_scheduleForms_ID');
					 scheduling.pkpShortScheduleModify.clearDynamicForm(scheduleForms);
					 scheduleForms.doLayout() ;
				//根据后台数据动态创建表单
	        	for(var i=0;i<taskNum ;i++){						
					 var form = Ext.create('Foss.pkpShortScheduleModify.form.ScheduleForm');
					 var items=scheduleForms.items;
					 //如果不是修改，则隐藏保存按钮 
					 if(scheduling.pkpShortScheduleModify.actionType!='REPORT_AND_MODIFY'){
						 form.dockedItems.items[0].items.items[0].hide();
						 form.dockedItems.items[0].items.items[1].hide();
						 form.dockedItems.items[0].items.items[3].hide();
					 	
					 	var bform = form.getForm();
					 	var fields=bform.getFields( ) ;
					 	fields.each(function(item,index,length){
					 		item.setReadOnly(true);
					 	});
					 }
					 //插入
					 scheduleForms.insert(items.length,form);
					 //设置
					 var record = Ext.create('Foss.pkpShortScheduleModify.model.TruckSchedulingTaskDetail',imDtos[i]);
					 form.loadRecord(record);
					 form.getForm().findField('truckModel').setCombValue(record.data.truckModelValue,record.data.truckModel);
					 form.getForm().findField('takeGoodsDepartment').setCombValue(record.data.takeGoodsDepartmentName,record.data.takeGoodsDepartment);
					 form.getForm().findField('transferGoodsDepartment').setCombValue(record.data.transferGoodsDepartmentName,record.data.transferGoodsDepartment);
	        	}
	        	window.getEl().unmask();
			},
			exception : function(response) {
				var json = Ext.decode(response.responseText);
				Ext.MessageBox.alert(scheduling.pkpShortScheduleModify.i18n('foss.shortDeparturePlan.confirm.title.lable'),json.message);
				window.getEl().unmask();
			}		
			});	
}

//清空动态表单
scheduling.pkpShortScheduleModify.clearDynamicForm=function(scheduleForms){
	//排班调单列表,用于根据数据动态构建表单,清空动态表单	
	 if(scheduleForms==null){
	 	scheduleForms=Ext.create('Foss.pkpShortScheduleModify.panle.ScheduleForms')				
	 }else{
	 	 var len = scheduleForms.items.length;
	 	 for(var i=0 ;i<len;i++){
	 	 	if(scheduleForms.items.length>0)
		 	scheduleForms.remove(scheduleForms.items.items[0],true);
	      }
	 }	
}

//删除单个排班任务
scheduling.pkpShortScheduleModify.deleteSchedulingTask=function(form){
	var actionUrl=scheduling.realPath('deleteTask.action');
	var scheduleForms=Ext.getCmp('Foss_pkpShortScheduleModify_panle_scheduleForms_ID');
	var scheduleTaskId='';
	if(form.getRecord()!=null){
		scheduleTaskId=form.getRecord().data.scheduleTaskId;
	}
 	var queryParams={'vo.simDto.scheduleTaskId':scheduleTaskId};//任务ID
 	Ext.Ajax.request({
		url : actionUrl,
		params:queryParams,
		//动态创建表单，显示任务信息
		success : function(response) {
			//重置临时接货小区
			scheduling.pkpShortScheduleModify.pkpAreas=[];
			//移除该表单,删除临时小区表单
			 var len = scheduleForms.items.length;
		 	 for(var i=0 ;i<len;i++){
		 	 	if(scheduleForms.items.length>0)
			 	scheduleForms.remove(scheduleForms.items.items[0],true);
		      }
			
			var json = Ext.decode(response.responseText);
			Ext.ux.Toast.msg(scheduling.pkpShortScheduleModify.i18n('foss.scheduling.ShortSchedule.tip.title'), scheduling.pkpShortScheduleModify.i18n('foss.shortScheduleModify.deleteSuccess'));
		},
		exception : function(response) {
			var json = Ext.decode(response.responseText);
			Ext.ux.Toast.msg(scheduling.pkpShortScheduleModify.i18n('foss.scheduling.ShortSchedule.tip.title'), scheduling.pkpShortScheduleModify.i18n('foss.shortScheduleModify.deleteFail'));
		}		
	});
}

//后台保存新建任务
scheduling.pkpShortScheduleModify.saveNewSchedulingTask=function(form){
	var actionUrl=scheduling.realPath('saveOrUpdateTaskAndFetchTask.action');
	var fieldName = scheduling.pkpShortScheduleModify.fieldName;
	var day = Ext.String.leftPad(fieldName.substring(4,fieldName.length), 2, '0');
	//原始年月日（修改或查看时赋值的全局变量）
	var  ymd =scheduling.pkpShortScheduleModify.ymd;
	//车队小组（修改或查看时赋值的全局变量）
	var  schedulingDepartCode =scheduling.pkpShortScheduleModify.schedulingDepartCode;
	//选择时赋值的全局变量
	var driverCode = scheduling.pkpShortScheduleModify.driverCode;
	//根据原始的日期和选择的列动态解析当前编辑的计划日期
	var schedulingDate=ymd.substring(0,7)+'-'+day;
	//获取表单数据
	var formVals = form.getValues();
	var scheduleTaskId='';
	var scheduleId='';
	if(form.getRecord()!=null){
		scheduleTaskId=form.getRecord().data.scheduleTaskId;
		scheduleId=form.getRecord().data.scheduleId;
	}
	//预计出车时间标识
	flag=true;
	//TODO 校验数据
	// 构建查询参数
	var data={'vo':{'simDto':{
							'ymd':schedulingDate,//年月日
							'driverCode':driverCode,//司机编码
							'schedulingDepartCode':schedulingDepartCode,//车队小组
							'planType':'WORK',//工作类别
							'schedulingtype':'PKP',//排班类型（短途排班）
							'schedulingStatus':'Y',//可用
							'schedulingDate':schedulingDate,//计划日期
							'vehicleNo':formVals.vehicleNo,//车牌号
							'truckModel':formVals.truckModel,//车型
							'carOwner':formVals.carOwner,//车辆所属车队
							'scheduleTaskId':scheduleTaskId,//任务ID
							'scheduleId':scheduleId,//计划ID
							'pkpRegionalName':formVals.pkpRegionalName,//定人定区接货小区
							'deliveryRegionalName':formVals.deliveryRegionalName,//非工作日送货区域拼接名称
							'frequencyNo':formVals.frequencyNo,//班次
							//'isBringExpress':formVals.isBringExpress,//是否带快递货（Y是N否）
							'expectedBringVolume':formVals.expectedBringVolume,//预计带货方数（方/F）
							'schedulingZoneEntity':scheduling.pkpShortScheduleModify.pkpAreas,//非工作日接货区域
							'dispatchVehicleTask' :formVals.dispatchVehicleTask,// 出车任务
							'expectedDispatchVehicleTime':formVals.expectedDispatchVehicleTime,// 预计出车时间
							'isTheTwoTripTime' :formVals.isTheTwoTripTime,//预计二次出车时间
							'takeGoodsDepartment' :formVals.takeGoodsDepartment,//带货部门
							'transferGoodsDepartment' :formVals.transferGoodsDepartment //转货部门
							}		
						}
					};
	//请求新增并查询出一个空的任务
	var myMask = new Ext.LoadMask(Ext.getBody(),  {msg:scheduling.pkpShortScheduleModify.i18n('foss.shortDeparturePlan.mask.waiting.tip')});
	myMask.show();				
	Ext.Ajax.request({
		url : actionUrl,
		jsonData:data,
		//动态创建表单，显示任务信息
		success : function(response) {
			//重置临时小区设置
			scheduling.pkpShortScheduleModify.pkpAreas=[];
			
			var json = Ext.decode(response.responseText);
        	var imDtos=json.vo.simDtos;
        	var taskNum = 0;
        	if(imDtos!=null){
        		taskNum = imDtos.length;
        	}
        	//排班调单列表,用于根据数据动态构建表单,清空动态表单
        	 var scheduleForms=Ext.getCmp('Foss_pkpShortScheduleModify_panle_scheduleForms_ID');
				 scheduling.pkpShortScheduleModify.clearDynamicForm(scheduleForms);
				 scheduleForms.doLayout() ;
			//根据后台数据动态创建表单
        	for(var i=0;i<taskNum ;i++){						
				 var form = Ext.create('Foss.pkpShortScheduleModify.form.ScheduleForm');
				 var items=scheduleForms.items;	
				 //插入
				 scheduleForms.insert(items.length,form);
				 //设置
				 var record = Ext.create('Foss.pkpShortScheduleModify.model.TruckSchedulingTaskDetail',imDtos[i]);
				 form.loadRecord(record);
				 form.getForm().findField('truckModel').setCombValue(record.data.truckModelValue,record.data.truckModel);
				 form.getForm().findField('takeGoodsDepartment').setCombValue(record.data.takeGoodsDepartmentName,record.data.takeGoodsDepartment);
				 form.getForm().findField('transferGoodsDepartment').setCombValue(record.data.transferGoodsDepartmentName,record.data.transferGoodsDepartment);
        	}
        	myMask.hide();
        	//刷新数据
			if(scheduling.pkpShortScheduleModify.grid){
					scheduling.pkpShortScheduleModify.grid.getStore().load();
				}
		},
		exception : function(response) {
			var json = Ext.decode(response.responseText);
			Ext.MessageBox.alert(scheduling.pkpShortScheduleModify.i18n('foss.shortDeparturePlan.confirm.title.lable'),json.message);	
			myMask.hide();
		}		
		});
}


//前端新建任务
scheduling.pkpShortScheduleModify.addTaskNewTask=function(){
	//动态创建新表单						
	 var form = Ext.create('Foss.pkpShortScheduleModify.form.ScheduleForm');
	 var scheduleForms=Ext.getCmp('Foss_pkpShortScheduleModify_panle_scheduleForms_ID');
	 var items=scheduleForms.items;	
	 //var form1=items.get(items.length);
	 if(items.length<1){
		 //var entiyt= form1.getForm().getValues();
		 //插入
		 scheduleForms.insert(items.length,form);
		 //设置
		 //var record = Ext.create('Foss.pkpShortScheduleModify.model.TruckSchedulingTaskDetail');
		 //form.loadRecord(record);
		 //设置司机电话
		 if(scheduling.pkpShortScheduleModify.driverPhone!=null)	
			 form.getForm().findField('driverPhone').setValue(scheduling.pkpShortScheduleModify.driverPhone)
	 }
	 flag=false;
}

//前端新建接货小区信息面板
scheduling.pkpShortScheduleModify.addpkpAreaTask=function(form){
	var panel = Ext.create('Foss.pkpShortScheduleModify.grid.AreaGrid');
	var dePanel = Ext.create('Foss.pkpShortScheduleModify.grid.deAreaGrid');
	var scheduleForms=Ext.getCmp('Foss_pkpShortScheduleModify_panle_scheduleForms_ID');
	var items=scheduleForms.items;
	if(items.length==1){
		scheduleForms.insert(items.length,panel);
		scheduleForms.insert(items.length,dePanel);
		var vehicleNo=form.getForm().findField('vehicleNo').getValue();
		//如果车牌号不为空则查询当月该车牌号最近新增的接货区域填充到 非工作日小区gird中
		if(!Ext.isEmpty(vehicleNo)){
			var actionUrl=scheduling.realPath('queryPkpAreaInfoByVehicleNo.action');
			var queryParams={"vo.simDto.vehicleNo":vehicleNo};
				
			Ext.Ajax.request({
				url : actionUrl,
				params:queryParams,
				success : function(response) {
					//加载前清空store
					var records = Ext.create('Foss.pkpShortScheduleModify.model.AreaModel')
					//scheduling.pkpShortScheduleModify.pkpAreas=[];
		    		panel.getStore().loadData(records,false);
					dePanel.getStore().loadData(records,false);
					//填充车型、车辆所属车队
					var json = Ext.decode(response.responseText);
		        	var simDto=json.vo.simDto.schedulingZoneEntity;
		        	var length = 0
		        	if(simDto!=null){
		        		length=simDto.length;
		        	}
		        	for(i=0;i<length;i++){
		        		var records = Ext.ModelManager.create(simDto[i],'Foss.pkpShortScheduleModify.model.AreaModel')
		        		//panel.getStore().loadData(records,false);
		        		//scheduling.pkpShortScheduleModify.pkpAreas.push(records.data);
		        		if('PK' == records.data.regionType || null == records.data.regionType || '' == records.data.regionType) {
		        			panel.store.insert(panel.store.getCount(),records);
		        		} else {
		        			dePanel.store.insert(dePanel.store.getCount(),records);
		        		}
		        	}
		        
				},
				exception : function(response) {
					var records = Ext.create('Foss.pkpShortScheduleModify.model.AreaModel')
		    		panel.getStore().loadData(records,false);
					dePanel.getStore().loadData(records,false);	
				}		
				});
		}else{
			//如果车牌号为空，则 非工作日小区gird设置为空
			var records = Ext.create('Foss.pkpShortScheduleModify.model.AreaModel')
			panel.getStore().loadData(records,false);
			dePanel.getStore().loadData(records,false);
		}
	}else if(items.length>=2){
		//删除预设小区
		for(var i=1;i<items.items.length;i++){
			scheduleForms.remove(scheduleForms.items.items[i],true);
			scheduleForms.remove(scheduleForms.items.items[i],true);
		}
	}
	
}
//查询最新的预设小区，并填充到预设小区grid中
scheduling.pkpShortScheduleModify.querynewPkpAreaByVehicleNo=function(form,vehicleNo){
	var scheduleForms=Ext.getCmp('Foss_pkpShortScheduleModify_panle_scheduleForms_ID');
	var items=scheduleForms.items;	
	if(items.length>1){
		var panel =scheduleForms.items.items[1];
		var dePanel =scheduleForms.items.items[2];
		//如果车牌号不为空则查询当月该车牌号最近新增的接货区域填充到 非工作日小区gird中
		var actionUrl=scheduling.realPath('queryPkpAreaInfoByVehicleNo.action');
		var queryParams={"vo.simDto.vehicleNo":vehicleNo};
		
		Ext.Ajax.request({
			url : actionUrl,
			params:queryParams,
			success : function(response) {
				//加载前清空store
				var records = Ext.create('Foss.pkpShortScheduleModify.model.AreaModel')
				panel.getStore().loadData(records,false);
				dePanel.getStore().loadData(records,false);
				//填充车型、车辆所属车队
				var json = Ext.decode(response.responseText);
				var simDto=json.vo.simDto.schedulingZoneEntity;
				var length = 0
				if(simDto!=null){
					length=simDto.length;
				}
				for(i=0;i<length;i++){
						var records = Ext.ModelManager.create(simDto[i],'Foss.pkpShortScheduleModify.model.AreaModel')
						//panel.getStore().loadData(records,false);
						if('PK' == records.data.regionType || null == records.data.regionType || '' == records.data.regionType) {
							panel.store.insert(panel.store.getCount(),records);
						} else {
							dePanel.store.insert(dePanel.store.getCount(),records);
						}
				}
				
			},
			exception : function(response) {
				var records = Ext.create('Foss.pkpShortScheduleModify.model.AreaModel')
				panel.getStore().loadData(records,false);
				dePanel.getStore().loadData(records,false);
			}		
		});
	}
}
//根据车牌号查询车型、车辆所属车队
scheduling.pkpShortScheduleModify.queryCarInfoByVehicleNo=function(form,vehicleNo){
	var actionUrl=scheduling.realPath('queryCarInfoByVehicleNo.action');
	var fieldName = scheduling.pkpShortScheduleModify.fieldName;
	var day = Ext.String.leftPad(fieldName.substring(4,fieldName.length), 2, '0');
	//原始年月日（修改或查看时赋值的全局变量）
	var  ymd =scheduling.pkpShortScheduleModify.ymd;
	//根据原始的日期和选择的列动态解析当前编辑的计划日期
	var schedulingDate=ymd.substring(0,7)+'-'+day;
	//选择时赋值的全局变量
	var driverCode = scheduling.pkpShortScheduleModify.driverCode;
	var queryParams={
			"vo.simDto.vehicleNo":vehicleNo,
			'vo.simDto.schedulingtype':'PKP',
			'vo.simDto.driverCode':driverCode,//司机编码
			'vo.simDto.ymd':schedulingDate//年月日
			};

			
	form.getEl().mask(scheduling.pkpShortScheduleModify.i18n('foss.shortDeparturePlan.mask.querywaiting.tip'));	
	Ext.Ajax.request({
		url : actionUrl,
		params:queryParams,
		success : function(response) {
			//填充车型、车辆所属车队
			var json = Ext.decode(response.responseText);
        	var simDto=json.vo.simDto;
        	form.getForm().findField('truckModel').setCombValue(simDto.truckModelValue,simDto.truckModel);
        	if(form.getForm().findField('carOwnerName') != null)
        	form.getForm().findField('carOwnerName').setValue(simDto.carOwnerName);
        	form.getEl().unmask();
		},
		exception : function(response) {
			var json = Ext.decode(response.responseText);
			Ext.MessageBox.alert(scheduling.pkpShortScheduleModify.i18n('foss.shortDeparturePlan.confirm.title.lable'),json.message);
			form.getEl().unmask();
		}		
		});
}

//根据车牌号查询区域信息
scheduling.pkpShortScheduleModify.queryZoneCodeByVehicleNo=function(form,vehicleNo){
	var actionUrl=scheduling.realPath('queryZoneCodeByVehicleNo.action');
	var queryParams={"vo.simDto.vehicleNo":vehicleNo};
	form.getEl().mask(scheduling.pkpShortScheduleModify.i18n('foss.shortDeparturePlan.mask.querywaiting.tip'));
	Ext.Ajax.request({
		url : actionUrl,
		params:queryParams,
		success : function(response) {
			//填充车型、车辆所属车队
			var json = Ext.decode(response.responseText);
        	var simDto=json.vo.simDto;
        	if(form.getForm().findField('zoneName') != null)
        	form.getForm().findField('zoneName').setValue(simDto.zoneName);
        	if(form.getForm().findField('deliveryAreaName') != null) 
        	form.getForm().findField('deliveryAreaName').setValue(simDto.deliveryAreaName);
        	if(simDto.zoneName == null)
        	Ext.MessageBox.alert(scheduling.pkpShortScheduleModify.i18n('foss.shortDeparturePlan.confirm.title.lable'),"没有查询到本车辆的接货区域信息");
        	if(simDto.deliveryAreaName == null)
        	Ext.MessageBox.alert(scheduling.pkpShortScheduleModify.i18n('foss.shortDeparturePlan.confirm.title.lable'),"没有查询到本车辆的送货区域信息");
        	form.getEl().unmask();
		},
		exception : function(response) {
			var json = Ext.decode(response.responseText);
			Ext.MessageBox.alert(scheduling.pkpShortScheduleModify.i18n('foss.shortDeparturePlan.confirm.title.lable'),json.message);
			form.getEl().unmask();
		}		
		});
}
scheduling.pkpShortScheduleModify.checkVehicle=function(form,vehicleNo){
	var actionUrl=scheduling.realPath('checkSchedulingTaskByVehicleNoAndDate.action');
	var fieldName = scheduling.pkpShortScheduleModify.fieldName;
	var day = Ext.String.leftPad(fieldName.substring(4,fieldName.length), 2, '0');
	//原始年月日（修改或查看时赋值的全局变量）
	var  ymd =scheduling.pkpShortScheduleModify.ymd;
	//根据原始的日期和选择的列动态解析当前编辑的计划日期
	var schedulingDate=ymd.substring(0,7)+'-'+day;
	//选择时赋值的全局变量
	var driverCode = scheduling.pkpShortScheduleModify.driverCode;
	var queryParams={
			"vo.simDto.vehicleNo":vehicleNo,
			'vo.simDto.schedulingtype':'PKP',
			'vo.simDto.driverCode':driverCode,//司机编码
			'vo.simDto.ymd':schedulingDate//年月日
			};
	Ext.Ajax.request({
		url : actionUrl,
		params:queryParams,
		success : function(response) {
		},
		exception : function(response) {
			var json = Ext.decode(response.responseText);
			Ext.MessageBox.alert(scheduling.pkpShortScheduleModify.i18n('foss.shortDeparturePlan.confirm.title.lable'),json.message);
			form.getForm().findField('truckModel').setValue("");
			form.getForm().findField('carOwnerName').setValue("");
			form.getForm().findField('zoneName').setValue("");
			form.getForm().findField('deliveryAreaName').setValue("");
			form.getForm().findField('frequencyNo').setValue("");
			//form.getForm().findField('isBringExpress').setValue("");
			form.getForm().findField('expectedBringVolume').setValue("");
			form.getForm().findField('dispatchVehicleTask').setValue("");
			form.getForm().findField('expectedDispatchVehicleTime').setValue("");
			form.getForm().findField('isTheTwoTripTime').setValue("");
			form.getForm().findField('takeGoodsDepartment').setValue("");
			form.getForm().findField('transferGoodsDepartment').setValue("");
			form.getForm().findField('pkpRegionalName').setValue("");
			form.getForm().findField('vehicleNo').setCombValue(null,null);
		}		
		});
}
//提示用户确认，改为非“出车”状态，且删除任务列表
scheduling.pkpShortScheduleModify.delteTaskAndModifySchedule=function(newValue){
	if(newValue=='LEAVE'||newValue=='UNKNOWN'||newValue=='REST'||newValue=='DUTY'||newValue=='TRAINING'){
		var actionUrl=scheduling.realPath('delteTaskAndModifySchedule.action');
	var fieldName = scheduling.pkpShortScheduleModify.fieldName;
	var day = Ext.String.leftPad(fieldName.substring(4,fieldName.length), 2, '0');
	//原始年月日（修改或查看时赋值的全局变量）
	var  ymd =scheduling.pkpShortScheduleModify.ymd;
	//车队小组（修改或查看时赋值的全局变量）
	var  schedulingDepartCode =scheduling.pkpShortScheduleModify.schedulingDepartCode;
	//选择时赋值的全局变量
	var driverCode = scheduling.pkpShortScheduleModify.driverCode;
	//根据原始的日期和选择的列动态解析当前编辑的计划日期
	var schedulingDate=ymd.substring(0,7)+'-'+day;
	// 构建查询参数
	var queryParams={'vo.simDto.ymd':schedulingDate,//年月日
					'vo.simDto.driverCode':driverCode,//司机编码
					'vo.simDto.schedulingDepartCode':schedulingDepartCode,//车队小组
					'vo.simDto.planType':newValue,//工作类别（出车）
					'vo.simDto.schedulingtype':'PKP',//排班类型（短途排班）
					'vo.simDto.schedulingStatus':'Y'//可用
					};
	//执行删除并修改计划
	//var myMask = new Ext.LoadMask(Ext.getBody(),  {msg:scheduling.pkpShortScheduleModify.i18n('foss.shortDeparturePlan.mask.waiting.tip')});
	//myMask.show();	
	Ext.Ajax.request({
		url : actionUrl,
		params:queryParams,
		//动态创建表单，显示任务信息
		success : function(response) {
			//执行成功，清除表单
			var scheduleForms=Ext.getCmp('Foss_pkpShortScheduleModify_panle_scheduleForms_ID');
				 scheduling.pkpShortScheduleModify.clearDynamicForm(scheduleForms);
				 scheduleForms.doLayout() ;
			//隐藏新增任务按钮
			Ext.getCmp('Foss_pkpShortScheduleModify_panle_scheduleForms_button_ID').hide();
			//myMask.hide();
			//刷新数据
			if(scheduling.pkpShortScheduleModify.grid){
					scheduling.pkpShortScheduleModify.grid.getStore().load();
				}
		},
		exception : function(response) {
			Ext.MessageBox.alert(scheduling.pkpShortScheduleModify.i18n('foss.shortDeparturePlan.confirm.title.lable'),'操作出错');
			//myMask.hide();
		}		
		});
	}
	
}
//取值动态构建表单
scheduling.pkpShortScheduleModify.obtainValFieldWhenClicked=function(colVal,fieldName,driverCode,actionStep){	
		//如果选中，且工作类别是“出车”，则查询出本数据		
		if(actionStep=='select'){
			if(colVal=='WORK'){
				scheduling.pkpShortScheduleModify.obtainShortTask(driverCode,fieldName);
				//显示新建按钮,只有修改时才能显示
				if(scheduling.pkpShortScheduleModify.actionType=='REPORT_AND_MODIFY'){
					Ext.getCmp('Foss_pkpShortScheduleModify_panle_scheduleForms_button_ID').show();
				}
				
			}//清空表单
			else{
				var scheduleForms=Ext.getCmp('Foss_pkpShortScheduleModify_panle_scheduleForms_ID');
				scheduling.pkpShortScheduleModify.clearDynamicForm(scheduleForms);
				//隐藏新建按钮
				Ext.getCmp('Foss_pkpShortScheduleModify_panle_scheduleForms_button_ID').hide();
			}					
		}
		//如果是编辑
		else if(actionStep=='edited'){
			
			//如果原值与新值不等
			if(scheduling.pkpShortScheduleModify.oldValue!=colVal){
				//其他状态转换为“出车”
				if(colVal=='WORK'){
					//清空原值
					scheduling.pkpShortScheduleModify.oldValue='';
				}
				//从“出车”状态改为其他工作类别，
				else{
					//TODO 则删除当前的任务					
					scheduling.pkpShortScheduleModify.oldValue='';
				}
			}
		}else{
			scheduling.pkpShortScheduleModify.oldValue='';
		}
}


//导出排班数据为excel
scheduling.pkpShortScheduleModify.exportExcel=function(){
	//var actionUrl=scheduling.realPath('exportExcel.action');	
	var actionUrl=scheduling.realPath('exportTruckSchedulingList.action');	
	var queryParams ={
						'vo.simDto.yearMonth':scheduling.pkpShortScheduleModify.ymd,//年月
						'vo.simDto.schedulingtype':'PKP',//接送货
						'vo.simDto.schedulingDepartCode':scheduling.pkpShortScheduleModify.schedulingDepartCode,//车队小组编码
						'vo.simDto.schedulingStatus':'Y'//状态为可用的						
					 };
	
	if(!Ext.fly('downloadAttachFileForm')){
			    var frm = document.createElement('form');
			    frm.id = 'downloadAttachFileForm';
			    frm.style.display = 'none';
			    document.body.appendChild(frm);
		}
		Ext.Ajax.request({
		url:actionUrl,
		form: Ext.fly('downloadAttachFileForm'),
		method : 'POST',
		params : queryParams,
		isUpload: true,
		exception : function(response) {
			var result = Ext.decode(response.responseText);
			top.Ext.MessageBox.alert(scheduling.pkpShortScheduleModify.i18n('foss.scheduling.forecastQuantity.forecastQuantity.exportFail'),result.message);
		}
		});
}


//制定、修改、查看短途排班(动态的生成js对象)
scheduling.pkpShortScheduleModify.makeOrModify = function(){		
	
}

//入口
Ext.onReady(function() {
	//部门名称
	if(!Ext.isEmpty(scheduling.pkpQueryShortSchedule.driverOrgName)){
		scheduling.pkpShortScheduleModify.driverOrgName=scheduling.pkpQueryShortSchedule.driverOrgName;
	}
	//编辑插件
	var cellEditing = Ext.create('Ext.grid.plugin.CellEditing', {
	    clicksToEdit: 1,
		clicksToEdit: 1,
		listeners:{
				////当值改变时，则获取任务数据，动态新建表单
				edit:function( editor,  e,  eOpts ){
					//被选中的值
					var colVal=e.value;
					//司机代码
					var driverCode=e.record.data.driverCode;
					//字段名称
					var fieldName=e.field;
					//取值动态构建表单
					scheduling.pkpShortScheduleModify.obtainValFieldWhenClicked(colVal,fieldName,driverCode,'edited');
					console.log(2);
				}
			}
			
	});
	//编辑的combox
	var store = Ext.create('Foss.pkpShortScheduleModify.store.PlanTypeCombox');
	//排班表
	var grid;
	//本月第一天
	var schedulingDate = scheduling.pkpShortScheduleModify.ymd+'-01';
	//动态构建表头
	var actionUrl=scheduling.realPath('queryGridHeader.action');	
	var queryParams ={'vo.simDto.ymd':schedulingDate};
	//动态请求列
	Ext.Ajax.request({
		url : actionUrl,
		params:queryParams,
		success : function(response) {
			var json = Ext.decode(response.responseText);
			var fields=json.vo.gridHeaderFields;
			//动态构建列
			if(fields!=null){
				var gridHeaderFields="[";
				//姓名列
				gridHeaderFields+="{text : '姓名',locked :true,sortable:false,menuDisabled:true,columnWidth:100,dataIndex: 'driverName'},"
				for(var i = 0 ;i<fields.length; i++){
					var field=fields[i];
					var fieldItem="{text: '"+field.headerDate+"',menuDisabled:true,columns: [{text : '"+field.dayText+"',menuDisabled:true,width : 80,sortable : false," +
							"renderer : function(val, metadata, record){" +
							"if(val=='REST'){ metadata.tdCls = metadata.tdCls +'alertedCell'; return '休息';}" +
							" else if(val=='WORK'){metadata.tdCls = metadata.tdCls +'alertedCellGreen'; return '出车';}" +
							" else if(val=='DUTY'){metadata.tdCls = metadata.tdCls +'alertedCellGreen'; return '值班';}" +
							" else if(val=='TRAINING'){metadata.tdCls = metadata.tdCls +'alertedCellGreen'; return '培训'; }" +
							"else if(val=='LEAVE'){metadata.tdCls = metadata.tdCls +'alertedCellRed'; return '离岗';}" +
							"else if(val=='UNKNOWN'){ return '未知';}" +
							" else return '其他';" +
							"},dataIndex: '"+field.dataIndex+"'," +
							"editor: {" +
							"xtype: 'combobox'," +
							"fieldLabel: '',labelSeparator:''," +
							"displayField: 'des'," +
							"valueField: 'id'," +
							"width: 80," +
							"store: store," +
							"queryMode: 'local'," +
							"typeAhead: true," +
							"listeners:{" +
							//值改变时,根据值显示新增按钮
							"change:function( field, newValue,oldValue,eOpts )" +
							"{" +
								"if(newValue=='LEAVE'||newValue=='UNKNOWN'||newValue=='REST'||newValue=='DUTY'||newValue=='TRAINING'||newValue=='WORK'){}" +
								"else{" +
								"field.clearValue( );return;}" +
								"if(newValue!=oldValue){" +
									"console.log(3); " +
									"if(newValue=='WORK'){" +
										"console.log(5);" +
										"Ext.ux.Toast.msg(scheduling.pkpShortScheduleModify.i18n('foss.scheduling.ShortSchedule.tip.title'), scheduling.pkpShortScheduleModify.i18n('foss.shortScheduleModify.workPrompt'));" +
										"Ext.getCmp('Foss_pkpShortScheduleModify_panle_scheduleForms_button_ID').show();"+
									"}else{" +
									    	"Ext.getCmp('Foss_pkpShortScheduleModify_panle_scheduleForms_button_ID').hide();" +
											//提示用户确认，改为非“出车”状态，且删除任务列表
											"console.log(4);scheduling.pkpShortScheduleModify.delteTaskAndModifySchedule(newValue);" +				    
									
									"}" +
								"}" +
							"}" +
							"}," +
							"allowBlank: false" +
							"}" +
							"}]},"
					gridHeaderFields=gridHeaderFields+fieldItem;
				}
				//上班天数列
				gridHeaderFields+="{text : '上班天数',sortable : true,menuDisabled:true,columnWidth:100,dataIndex: 'workTotal'}"
				gridHeaderFields+="]";
			}
			//解析表格列
			var gcs=eval(gridHeaderFields);
			//表格标题
			var gridTitle = scheduling.pkpShortScheduleModify.driverOrgName+'车队'+scheduling.pkpShortScheduleModify.ymd+'排班信息';
			//传入表格列及编辑插件和grid对象一起实例化
			if(scheduling.pkpShortScheduleModify.actionType=='REPORT_AND_MODIFY'){
				grid = Ext.create('Foss.pkpShortScheduleModify.grid.TruckSchedulingTaskGrid',{
					 	columns:gcs,
					 	plugins: [cellEditing],
					 	selModel: Ext.create('Ext.selection.CellModel',{
					 		listeners: {
					 			//当点击单元格时，根据当前的值，控制表单显示
					 			'select': function(cellModel, record, row, column, eOpts){
					 				//列表
					 				var fieldName = 'date'+(column+1);//+(column+1);
					 				//司机编码
					 				var driverCode = record.data.driverCode;
					 				//当前的工作类别
					 				var colVal=record.data[fieldName];
					 				//取值动态构建表单
									scheduling.pkpShortScheduleModify.obtainValFieldWhenClicked(colVal,fieldName,driverCode,'select');
									scheduling.pkpShortScheduleModify.oldValue=colVal;
									scheduling.pkpShortScheduleModify.driverCode=driverCode;
									scheduling.pkpShortScheduleModify.driverPhone=record.data.driverPhone;	
									scheduling.pkpShortScheduleModify.fieldName=fieldName;						
									console.log(1);
					 			}
					 		}
					 	}),
						title:gridTitle,
						//导出按钮
						tbar:[
						     {xtype:'tbspacer',flex:1},
						     {
								xtype:'button',	
								text: scheduling.pkpShortScheduleModify.i18n('foss.scheduling.ShortSchedule.button.exportDate.lable'),//'导出排班数据',
								handler: function() {
									scheduling.pkpShortScheduleModify.exportExcel();
							}
						}]
					 });		
			}else{
				grid = Ext.create('Foss.pkpShortScheduleModify.grid.TruckSchedulingTaskGrid',{
					 	columns:gcs,
					 	selModel: Ext.create('Ext.selection.CellModel',{
					 		listeners: {
					 			//当点击单元格时，根据当前的值，控制表单显示
					 			'select': function(cellModel, record, row, column, eOpts){
					 				//列表
					 				var fieldName = 'date'+(column+1);//(column+1);
					 				//司机编码
					 				var driverCode = record.data.driverCode;
					 				//当前的工作类别
					 				var colVal=record.data[fieldName];
					 				//取值动态构建表单
									scheduling.pkpShortScheduleModify.obtainValFieldWhenClicked(colVal,fieldName,driverCode,'select');
									scheduling.pkpShortScheduleModify.oldValue=colVal;
									scheduling.pkpShortScheduleModify.driverCode=driverCode;
									scheduling.pkpShortScheduleModify.driverPhone=record.data.driverPhone;	
									scheduling.pkpShortScheduleModify.fieldName=fieldName;						
									console.log(1);
					 			}
					 		}
					 	}),
						title:gridTitle,
						//导出按钮
						tbar:[
						     {xtype:'tbspacer',flex:1},
						     {
								xtype:'button',	
								text: scheduling.pkpShortScheduleModify.i18n('foss.scheduling.ShortSchedule.button.exportDate.lable'),//'导出排班数据',
								handler: function() {
									scheduling.pkpShortScheduleModify.exportExcel();
							}
						}]
					 });		
			}
			 scheduling.pkpShortScheduleModify.grid=grid;
			 //创建面板容器
			var scheduleForms=Ext.create('Foss.pkpShortScheduleModify.panle.ScheduleForms');
			 //显示于主页面
			 Ext.create('Ext.panel.Panel',{
					id:'T_scheduling-pkpShortScheduleModify_content',
					cls:"panelContent",
					bodyCls:'panelContent-body',
					layout:'auto',
					margin:'0 0 0 0',
					items : [grid,scheduleForms],
					renderTo: 'T_scheduling-pkpShortScheduleModify-body'
				});
			 grid.getStore().load();
		},
		exception : function(response) {
		}
	});
});