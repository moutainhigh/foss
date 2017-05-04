//package Foss.shortScheduleModify

// 短途排班任务列表
Ext.define('Foss.shortScheduleModify.model.TruckSchedulingTaskDetail', {
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
	    {name: 'frequencyNo',type:'string'},// 班次
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
		{name: 'tOptTruckSchedulingId',type:'string'},// 短途排班表ID
		{name: 'tsId',type:'string'},// 短途排班任务列表ID
		{name: 'taskStatus', type: 'string'},// 任务状态
		{name: 'createUserCode', type: 'string'},// 创建人代码
		{name: 'updateUserCode', type: 'string'}// 更新人代码
	]
});
// 短途排班任务列表模型
Ext.define('Foss.shortScheduleModify.model.TruckSchedulingTask', {
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
Ext.define('Foss.shortScheduleModify.store.TruckSchedulingTask',{
	extend: 'Ext.data.Store',
	model: 'Foss.shortScheduleModify.model.TruckSchedulingTask',
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
				var queryParams ={'vo.tsEntity.yearMonth':scheduling.shortScheduleModify.ymd,'vo.tsEntity.schedulingType':'TFR','vo.tsEntity.schedulingDepartCode':scheduling.shortScheduleModify.schedulingDepartCode};				
				Ext.apply(operation, {
					params : queryParams
				});
		}
	}
});

//工作类型
Ext.define('Foss.shortScheduleModify.model.PlanTypeCombox', {
	extend: 'Ext.data.Model',
	//定义字段
	fields: [
		{name: 'id',type:'String'},//ID
		{name: 'des',type:'string'}//描述
	]
});

//审核状态数据源
Ext.define('Foss.shortScheduleModify.store.PlanTypeCombox',{
	extend: 'Ext.data.Store',
	model: 'Foss.shortScheduleModify.model.PlanTypeCombox',
	data : {
		'items':[
			{id : 'REST', des : scheduling.shortScheduleModify.i18n('foss.queryShortSchedule.grid.TruckSchedulingTask.planType.REST')},//'休息'
			{id : 'WORK', des : scheduling.shortScheduleModify.i18n('foss.queryShortSchedule.grid.TruckSchedulingTask.planType.WORK')},//'出车'
			{id : 'DUTY', des : scheduling.shortScheduleModify.i18n('foss.queryShortSchedule.grid.TruckSchedulingTask.planType.DUTY')},//'值班'
			{id : 'TRAINING', des : scheduling.shortScheduleModify.i18n('foss.queryShortSchedule.grid.TruckSchedulingTask.planType.TRAINING')},//'培训'
			{id : 'LEAVE', des : scheduling.shortScheduleModify.i18n('foss.queryShortSchedule.grid.TruckSchedulingTask.planType.LEAVE')},//'离岗'
			{id : 'UNKNOWN', des : scheduling.shortScheduleModify.i18n('foss.queryShortSchedule.grid.TruckSchedulingTask.planType.UNKNOWN')}//'未知'
			
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
Ext.define('Foss.shortScheduleModify.grid.TruckSchedulingTaskGrid', {
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
		me.store=Ext.create('Foss.shortScheduleModify.store.TruckSchedulingTask');
		me.bbar = Ext.create('Deppon.StandardPaging',{
			store:me.store,
			pageSize:10,
			plugins: 'pagesizeplugin'
		});
		scheduling.shortScheduleModify.pagebar=me.bbar ;
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

//排班表单
Ext.define('Foss.shortScheduleModify.panle.ScheduleForms',{
	extend:'Ext.panel.Panel',
	id:'Foss_shortScheduleModify_panle_scheduleForms_ID',
	margin:'0 5 0 0',
	frame:true,
	cls:'autoHeight',
	bodyCls:'autoHeight',
	items:[],
	tbar:[{
		xtype: 'button', 
		text:  scheduling.shortScheduleModify.i18n('foss.shortScheduleModify.panle.ScheduleForms.button.addNew.lable'),//'新增任务',
		hidden:true,
		id:'Foss_shortScheduleModify_panle_scheduleForms_button_ID',
		handler: function() {
			//动态新增任务表单
			scheduling.shortScheduleModify.addTaskNewTask();
		}
	}]
});

//短途排班表单
Ext.define('Foss.shortScheduleModify.form.ScheduleForm', {
	extend: 'Ext.form.Panel',	
	layout:'column',
	title:scheduling.shortScheduleModify.i18n('foss.shortScheduleModify.form.ScheduleForm.title.lable'),//'排班信息',
	bodyStyle:'padding:5px 5px 0',	
	cls:'autoHeight',
	bodyCls:'autoHeight',
	frame:true,
	defaultType: 'textfield',	
	defaults: {
		margin:'5 10 5 10',
		anchor: '90%',
		labelWidth:95
	},
	items: [
	        {xtype : 'commonowntruckselector',fieldLabel: scheduling.shortScheduleModify.i18n('foss.queryShortSchedule.form.TruckSchedulingSearch.vehicleNo.lable'),name: 'vehicleNo',allowBlank:false,columnWidth:.25,//'车牌号'
	        	listeners:{
					blur:function(txtField,eOpts ){
						txtField.setValue(Ext.String.trim(txtField.getValue()));
						var vehicleNo=txtField.value;
						if(!Ext.isEmpty(vehicleNo)){
							scheduling.shortScheduleModify.queryCarInfoByVehicleNo(this.up('form'),vehicleNo);
						}											
					}
				}},
	        {xtype : 'commonvehicletypeselector',fieldLabel: scheduling.shortScheduleModify.i18n('foss.queryShortSchedule.grid.TruckSchedulingTask.truckModelValue.lable'),name: 'truckModel',allowBlank:false,columnWidth:.25,readOnly:true},//'车型'
	        {fieldLabel: scheduling.shortScheduleModify.i18n('foss.queryShortSchedule.grid.TruckSchedulingTask.carOwnerName.lable'),name: 'carOwnerName',allowBlank:false,columnWidth:.25,readOnly:true},//'车辆所属车队'	        
	        {fieldLabel: scheduling.shortScheduleModify.i18n('foss.queryShortSchedule.grid.TruckSchedulingTask.driverPhone.lable'),name: 'driverPhone',allowBlank:false,columnWidth:.25,readOnly:true},//'司机电话'
	        {xtype : 'commonlineselector',valueField: 'virtualCode',displayField: 'lineName',fieldLabel: scheduling.shortScheduleModify.i18n('foss.queryShortSchedule.grid.TruckSchedulingTask.lineName.lable'),name: 'lineNo',allowBlank:false,columnWidth:.25,regex:/(^\s*)|(\s*$)/g,//'线路'
	        	listeners:{
					blur:function(txtField,eOpts ){
						txtField.setValue(Ext.String.trim(txtField.getValue()));
						var form =this.up('form').getForm();						
						if(!Ext.isEmpty(txtField.getValue()) && !Ext.isEmpty(form.findField('frequencyNo').getValue())){
							
							scheduling.shortScheduleModify.queryDepartTimeByLineNoAndFrequenceNo(this.up('form'));	
						}
											
					}
				}},
	        {fieldLabel: scheduling.shortScheduleModify.i18n('foss.queryShortSchedule.grid.TruckSchedulingTask.frequencyNo.lable'),name: 'frequencyNo',xtype:'numberfield',allowBlank:false,columnWidth:.25,maxLength:5,//'班次'
		        	listeners:{
						blur:function(txtField,eOpts ){							
							var form =this.up('form').getForm();
							if(!Ext.isEmpty(txtField.getValue())&& !Ext.isEmpty(form.findField('lineNo').getValue())){								
								scheduling.shortScheduleModify.queryDepartTimeByLineNoAndFrequenceNo(this.up('form'));
							}
											
						}
					}},
	        {fieldLabel: scheduling.shortScheduleModify.i18n('foss.queryShortSchedule.grid.TruckSchedulingTask.departTimeShort.lable'),name: 'departTimeShort',allowBlank:false,columnWidth:.25,readOnly:true},//'发车时间'	        
			{xtype: 'hiddenfield',fieldLabel: 'scheduleTaskId',name: 'scheduleTaskId',columnWidth:.25,readOnly:true}//scheduleTaskId
	],
	dockedItems: [{
		xtype: 'toolbar',
		dock: 'bottom',
		buttonAlign:'end',		
		items: [
		    {
			xtype: 'button',			
			text: '删除',
			margin:'0 0 0 100',
			handler: function() {
			 	scheduling.shortScheduleModify.deleteSchedulingTask(this.up('form'));
			}
		},
		{xtype:'tbspacer',flex:1},
		{
			xtype: 'button',			
			text: scheduling.shortScheduleModify.i18n('foss.queryShortSchedule.form.ImportShortSchedule.button.save.lable'),//'保存',
			margin:'0 0 0 190',
			handler: function() {
				var form =this.up('form').getForm();
				//验证
				if(form.isValid( )){
					var departTimeShort = form.findField('lineNo').getValue();
					var truckModel = form.findField('truckModel').getValue();
					//验证是否有发车时间
					if(!Ext.isEmpty(departTimeShort) && !Ext.isEmpty(truckModel)){
						//执行保存
						scheduling.shortScheduleModify.saveNewSchedulingTask(this.up('form'));													
					}else if(Ext.isEmpty(departTimeShort)){
						Ext.MessageBox.alert(scheduling.shortScheduleModify.i18n('foss.scheduling.ShortSchedule.tip.title'),
						scheduling.shortScheduleModify.i18n('foss.scheduling.ShortSchedule.tip.lineFreUnuseable'));//'本线路、班次无效，请核实再保存'
					}else if(Ext.isEmpty(truckModel)){
						Ext.MessageBox.alert(scheduling.shortScheduleModify.i18n('foss.scheduling.ShortSchedule.tip.title'),
						scheduling.shortScheduleModify.i18n('foss.scheduling.ShortSchedule.tip.vehicleNoUnuseable'));//'本车牌无效，请核实再保存'
					}else{
						Ext.MessageBox.alert(scheduling.shortScheduleModify.i18n('foss.scheduling.ShortSchedule.tip.title'),
						scheduling.shortScheduleModify.i18n('foss.scheduling.ShortSchedule.tip.dataUnuseable'));//'数据无效，请核实再保存'
					}
				}
								
			}
		}]
	}]
});

//查询状态为出车的任务数据
scheduling.shortScheduleModify.obtainShortTask=function(driverCode,field){
	var actionUrl=scheduling.realPath('queryShortScheduleTaskByDriver.action');
	var day = Ext.String.leftPad(field.substring(4,field.length), 2, '0');
	//原始年月日
	var  ymd =scheduling.shortScheduleModify.ymd;
	//车队小组
	var  schedulingDepartCode =scheduling.shortScheduleModify.schedulingDepartCode;
	//根据原始的日期和选择的列动态解析当前编辑的计划日期
	var schedulingDate=ymd.substring(0,7)+'-'+day;
	// 构建查询参数
	var queryParams={'vo.simDto.ymd':schedulingDate,//年月日
					'vo.simDto.driverCode':driverCode,//司机编码
					'vo.simDto.schedulingDepartCode':schedulingDepartCode,//车队小组
					'vo.simDto.planType':'WORK',//工作类别
					'vo.simDto.schedulingtype':'TFR'};//排班类型（短途排班）
	//请求查询出车的相关排班任务信息
	var window = Ext.getCmp('Foss_shortScheduleModify_panle_scheduleForms_ID');
	if(window){
		window.getEl().mask(scheduling.shortScheduleModify.i18n('foss.shortDeparturePlan.mask.querywaiting.tip'));//"正在查询，请稍等..."
	}
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
        	 var scheduleForms=Ext.getCmp('Foss_shortScheduleModify_panle_scheduleForms_ID');
				 scheduling.shortScheduleModify.clearDynamicForm(scheduleForms);
				 scheduleForms.doLayout() ;
			//根据后台数据动态创建表单
        	for(var i=0;i<taskNum ;i++){						
				 var form = Ext.create('Foss.shortScheduleModify.form.ScheduleForm');
				 var items=scheduleForms.items;
				 //如果不是修改，则隐藏保存按钮 
				 if(scheduling.shortScheduleModify.actionType!='REPORT_AND_MODIFY'){
				 	form.dockedItems.items[0].items.items[1].hide();
				 	var bform = form.getForm();
				 	var fields=bform.getFields( ) ;
				 	fields.each(function(item,index,length){
				 		item.setReadOnly(true);
				 	});
				 }
				 //插入
				 scheduleForms.insert(items.length,form);
				 //设置
				 var record = Ext.create('Foss.shortScheduleModify.model.TruckSchedulingTaskDetail',imDtos[i]);
				 form.loadRecord(record);
				 form.getForm().findField('truckModel').setCombValue(record.data.truckModelValue,record.data.truckModel);
				 form.getForm().findField('lineNo').setCombValue(record.data.lineName,record.data.lineNo);
        	}
        	window.getEl().unmask();
		},
		exception : function(response) {
			var json = Ext.decode(response.responseText);
			Ext.MessageBox.alert(scheduling.shortScheduleModify.i18n('foss.scheduling.ShortSchedule.tip.title'),json.message);
			window.getEl().unmask();
		}		
		});	
}

//清空动态表单
scheduling.shortScheduleModify.clearDynamicForm=function(scheduleForms){
	//排班调单列表,用于根据数据动态构建表单,清空动态表单	
	 if(scheduleForms==null){
	 	scheduleForms=Ext.create('Foss.shortScheduleModify.panle.ScheduleForms')				
	 }else{
	 	 var len = scheduleForms.items.length;
	 	 for(var i=0 ;i<len;i++){
	 	 	if(scheduleForms.items.length>0)
		 	scheduleForms.remove(scheduleForms.items.items[0],true);
	      }
	 }	
}

//删除单个任务
scheduling.shortScheduleModify.deleteSchedulingTask=function(form){
	var actionUrl=scheduling.realPath('deleteTask.action');
	var scheduleForms=Ext.getCmp('Foss_shortScheduleModify_panle_scheduleForms_ID');
 	var queryParams={'vo.simDto.scheduleTaskId':form.getRecord().data.scheduleTaskId};//任务ID
 	Ext.Ajax.request({
		url : actionUrl,
		params:queryParams,
		//动态创建表单，显示任务信息
		success : function(response) {
			//移除该表单
			scheduleForms.remove(form,true);
			var json = Ext.decode(response.responseText);
			Ext.ux.Toast.msg(scheduling.shortScheduleModify.i18n('foss.scheduling.ShortSchedule.tip.title'), scheduling.shortScheduleModify.i18n('foss.shortScheduleModify.deleteSuccess'));
		},
		exception : function(response) {
			var json = Ext.decode(response.responseText);
			Ext.ux.Toast.msg(scheduling.shortScheduleModify.i18n('foss.scheduling.ShortSchedule.tip.title'), scheduling.shortScheduleModify.i18n('foss.shortScheduleModify.deleteFail'));
		}		
	});
}
//后台保存新建任务
scheduling.shortScheduleModify.saveNewSchedulingTask=function(form){
	var actionUrl=scheduling.realPath('saveOrUpdateTaskAndFetchTask.action');
	var fieldName = scheduling.shortScheduleModify.fieldName;
	var day = Ext.String.leftPad(fieldName.substring(4,fieldName.length), 2, '0');
	//原始年月日（修改或查看时赋值的全局变量）
	var  ymd =scheduling.shortScheduleModify.ymd;
	//车队小组（修改或查看时赋值的全局变量）
	var  schedulingDepartCode =scheduling.shortScheduleModify.schedulingDepartCode;
	//选择时赋值的全局变量
	var driverCode = scheduling.shortScheduleModify.driverCode;
	//根据原始的日期和选择的列动态解析当前编辑的计划日期
	var schedulingDate=ymd.substring(0,7)+'-'+day;
	//获取表单数据
	var formVals = form.getValues();
	// 构建查询参数
	var queryParams={'vo.simDto.ymd':schedulingDate,//年月日
					'vo.simDto.driverCode':driverCode,//司机编码
					'vo.simDto.schedulingDepartCode':schedulingDepartCode,//车队小组
					'vo.simDto.planType':'WORK',//工作类别
					'vo.simDto.schedulingtype':'TFR',//排班类型（短途排班）
					'vo.simDto.schedulingStatus':'Y',//可用
					'vo.simDto.schedulingDate':schedulingDate,//计划日期
					'vo.simDto.vehicleNo':formVals.vehicleNo,//车牌号
					'vo.simDto.truckModel':formVals.truckModel,//车型
					'vo.simDto.carOwner':formVals.carOwner,//车辆所属车队
					'vo.simDto.lineNo':formVals.lineNo,//线路
					'vo.simDto.frequencyNo':formVals.frequencyNo,//班次
					'vo.simDto.scheduleTaskId':form.getRecord().data.scheduleTaskId,//任务ID
					'vo.simDto.scheduleId':form.getRecord().data.scheduleId//计划ID
					};
	//请求新增并查询出一个空的任务
	var myMask = new Ext.LoadMask(Ext.getBody(),  {msg:scheduling.shortScheduleModify.i18n('foss.shortDeparturePlan.mask.waiting.tip')});//"正在保存，请稍等..."
		myMask.show();					
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
        	 var scheduleForms=Ext.getCmp('Foss_shortScheduleModify_panle_scheduleForms_ID');
				 scheduling.shortScheduleModify.clearDynamicForm(scheduleForms);
				 scheduleForms.doLayout() ;
			//根据后台数据动态创建表单
        	for(var i=0;i<taskNum ;i++){						
				 var form = Ext.create('Foss.shortScheduleModify.form.ScheduleForm');
				 var items=scheduleForms.items;	
				 //插入
				 scheduleForms.insert(items.length,form);
				 //设置
				 var record = Ext.create('Foss.shortScheduleModify.model.TruckSchedulingTaskDetail',imDtos[i]);
				 form.loadRecord(record);
				 form.getForm().findField('truckModel').setCombValue(record.data.truckModelValue,record.data.truckModel);
				 form.getForm().findField('lineNo').setCombValue(record.data.lineName,record.data.lineNo);
        	}
        	Ext.MessageBox.alert(scheduling.shortScheduleModify.i18n('foss.scheduling.ShortSchedule.tip.title'),
        						scheduling.shortScheduleModify.i18n('foss.shortDeparturePlan.confirm.save.success'));
        	myMask.hide();
        	if(scheduling.shortScheduleModify.grid){
				scheduling.shortScheduleModify.grid.getStore().load();
			}
		},
		exception : function(response) {
			var json = Ext.decode(response.responseText);
			Ext.MessageBox.alert(scheduling.shortScheduleModify.i18n('foss.scheduling.ShortSchedule.tip.title'),json.message);
			//如果存在ID ，则重新查询，否则删除本新增的表单
			var scheduleId =form.getRecord().data.scheduleId;
			if(scheduleId){
				var queryParams={
								'vo.simDto.scheduleId':scheduleId//司机编码							
								};
				var actionUrl=scheduling.realPath('queryShortScheduleTaskByDriver.action');
				//请求查询出车的相关排班任务信息
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
			        	 var scheduleForms=Ext.getCmp('Foss_shortScheduleModify_scheduleForms_ID');
							 scheduling.shortScheduleModify.clearDynamicForm(scheduleForms);
							 scheduleForms.doLayout() ;
						//根据后台数据动态创建表单
			        	for(var i=0;i<taskNum ;i++){						
							 var form = Ext.create('Foss.shortScheduleModify.form.ScheduleForm');
							 var items=scheduleForms.items;	
							 //插入
							 scheduleForms.insert(items.length,form);
							 //设置
							 var record = Ext.create('Foss.shortScheduleModify.model.TruckSchedulingTaskDetail',imDtos[i]);
							 form.loadRecord(record);
							 form.getForm().findField('truckModel').setCombValue(record.data.truckModelValue,record.data.truckModel);
				 			 form.getForm().findField('lineNo').setCombValue(record.data.lineName,record.data.lineNo);
			        	}
			        	
					},
					exception : function(response) {
						
					}		
					});	
			}else{
				var scheduleForms=Ext.getCmp('Foss_shortScheduleModify_panle_scheduleForms_ID');
					scheduleForms.remove(form,true);
			}
			myMask.hide();
		}		
		});
}


//前端新建任务
scheduling.shortScheduleModify.addTaskNewTask=function(){
	//动态创建新表单						
	 var form = Ext.create('Foss.shortScheduleModify.form.ScheduleForm');
	 var scheduleForms=Ext.getCmp('Foss_shortScheduleModify_panle_scheduleForms_ID');
	 var items=scheduleForms.items;	
	 //插入
	 scheduleForms.insert(items.length,form);
	 //设置
	 var record = Ext.create('Foss.shortScheduleModify.model.TruckSchedulingTask');
	 form.loadRecord(record);
	  //设置司机电话
	 if(scheduling.shortScheduleModify.driverPhone!=null)	
	 form.getForm().findField('driverPhone').setValue(scheduling.shortScheduleModify.driverPhone)
}

//提示用户确认，改为非“出车”状态，且删除任务列表
scheduling.shortScheduleModify.delteTaskAndModifySchedule=function(newValue){
	if(newValue=='LEAVE'||newValue=='UNKNOWN'||newValue=='REST'||newValue=='DUTY'||newValue=='TRAINING'){
		var actionUrl=scheduling.realPath('delteTaskAndModifySchedule.action');
	var fieldName = scheduling.shortScheduleModify.fieldName;
	var day = Ext.String.leftPad(fieldName.substring(4,fieldName.length), 2, '0');
	//原始年月日（修改或查看时赋值的全局变量）
	var  ymd =scheduling.shortScheduleModify.ymd;
	//车队小组（修改或查看时赋值的全局变量）
	var  schedulingDepartCode =scheduling.shortScheduleModify.schedulingDepartCode;
	//选择时赋值的全局变量
	var driverCode = scheduling.shortScheduleModify.driverCode;
	//根据原始的日期和选择的列动态解析当前编辑的计划日期
	var schedulingDate=ymd.substring(0,7)+'-'+day;
	// 构建查询参数
	var queryParams={'vo.simDto.ymd':schedulingDate,//年月日
					'vo.simDto.driverCode':driverCode,//司机编码
					'vo.simDto.schedulingDepartCode':schedulingDepartCode,//车队小组
					'vo.simDto.planType':newValue,//工作类别（出车）
					'vo.simDto.schedulingtype':'TFR',//排班类型（短途排班）
					'vo.simDto.schedulingStatus':'Y'//可用
					};
	//执行删除并修改计划
		var myMask = new Ext.LoadMask(Ext.getBody(),  {msg:scheduling.shortScheduleModify.i18n('foss.scheduling.ShortSchedule.tip.deleting')});
		myMask.show();				
		Ext.Ajax.request({
			url : actionUrl,
			params:queryParams,
			//动态创建表单，显示任务信息
			success : function(response) {
				//执行成功，清除表单
				var scheduleForms=Ext.getCmp('Foss_shortScheduleModify_panle_scheduleForms_ID');
					 scheduling.shortScheduleModify.clearDynamicForm(scheduleForms);
					 scheduleForms.doLayout() ;
				//隐藏新增任务按钮
				Ext.getCmp('Foss_shortScheduleModify_panle_scheduleForms_button_ID').hide();	
				myMask.hide();
				if(scheduling.shortScheduleModify.grid){
					scheduling.shortScheduleModify.grid.getStore().load();
					scheduling.shortScheduleModify.grid.doLayout() ;
				}
			},
			exception : function(response) {
				var json = Ext.decode(response.responseText);
				Ext.MessageBox.alert(scheduling.shortScheduleModify.i18n('foss.scheduling.ShortSchedule.tip.title'),json.message);
				myMask.hide();
			}		
			});
	}
	
}
//取值动态构建表单
scheduling.shortScheduleModify.obtainValFieldWhenClicked=function(colVal,fieldName,driverCode,actionStep){	
		//如果选中，且工作类别是“出车”，则查询出本数据		
		if(actionStep=='select'){
			if(colVal=='WORK'){
				scheduling.shortScheduleModify.obtainShortTask(driverCode,fieldName);
				//显示新建按钮
				if(scheduling.shortScheduleModify.actionType=='REPORT_AND_MODIFY'){
					Ext.getCmp('Foss_shortScheduleModify_panle_scheduleForms_button_ID').show();
				}
			}//清空表单
			else{
				var scheduleForms=Ext.getCmp('Foss_shortScheduleModify_panle_scheduleForms_ID');
				scheduling.shortScheduleModify.clearDynamicForm(scheduleForms);
				//隐藏新建按钮
				Ext.getCmp('Foss_shortScheduleModify_panle_scheduleForms_button_ID').hide();
			}					
		}
		//如果是编辑
		else if(actionStep=='edited'){
			
			//如果原值与新值不等
			if(scheduling.shortScheduleModify.oldValue!=colVal){
				//其他状态转换为“出车”
				if(colVal=='WORK'){
					//清空原值
					scheduling.shortScheduleModify.oldValue='';
				}
				//从“出车”状态改为其他工作类别，
				else{					
					scheduling.shortScheduleModify.oldValue='';
				}
			}
		}else{
			scheduling.shortScheduleModify.oldValue='';
		}
}

//根据车牌号查询车型、车辆所属车队
scheduling.shortScheduleModify.queryCarInfoByVehicleNo=function(formEl,vehicleNo){
	var form =formEl.getForm();
	
	var actionUrl=scheduling.realPath('queryCarInfoByVehicleNo.action');
	var queryParams={"vo.simDto.vehicleNo":vehicleNo};
	//遮罩
	if(formEl){
			formEl.getEl().mask(scheduling.shortScheduleModify.i18n('foss.shortDeparturePlan.mask.querywaiting.tip'));
		}
	//执行查询
	Ext.Ajax.request({
		url : actionUrl,
		params:queryParams,
		success : function(response) {
			//填充车型、车辆所属车队
			var json = Ext.decode(response.responseText);
        	var simDto=json.vo.simDto;
        	if(!Ext.isEmpty(simDto.truckModel))
        	form.findField('truckModel').setCombValue(simDto.truckModelValue,simDto.truckModel);
        	if(form.findField('carOwnerName') != null)
        	form.findField('carOwnerName').setValue(simDto.carOwnerName);
        	formEl.getEl().unmask();
		},
		exception : function(response) {
			var json = Ext.decode(response.responseText);
			Ext.MessageBox.alert(scheduling.shortScheduleModify.i18n('foss.scheduling.ShortSchedule.tip.title'),json.message);
			formEl.getEl().unmask();
		}		
		});
}

// 通过"线路"、"班次"查询"发车时间"
scheduling.shortScheduleModify.queryDepartTimeByLineNoAndFrequenceNo=function(formEl){
	var form=formEl.getForm();					
	//线路
	var lineNo=form.getValues().lineNo;
	//班次
	var frequencyNo=form.getValues().frequencyNo;	
	if(lineNo != null && frequencyNo != null){
		var actionUrl=scheduling.realPath('queryDepartTimeByLineNoAndFrequenceNo.action');
		var queryParams={"vo.simDto.lineNo":lineNo,"vo.simDto.frequencyNo":frequencyNo};
		//遮罩
		formEl.getEl().mask(scheduling.shortScheduleModify.i18n('foss.shortDeparturePlan.mask.querywaiting.tip')); 
		Ext.Ajax.request({
			url : actionUrl,
			params:queryParams,
			success : function(response) {
				//填充车型、车辆所属车队
				var json = Ext.decode(response.responseText);
	        	var simDto=json.vo.simDto;
	        	if(form.findField('departTimeShort') != null)
	        	form.findField('departTimeShort').setValue(simDto.departTimeShort);
	        	formEl.getEl().unmask( );
			},
			exception : function(response) {
				var json = Ext.decode(response.responseText);
				Ext.MessageBox.alert(scheduling.shortScheduleModify.i18n('foss.scheduling.ShortSchedule.tip.title'),json.message);
				formEl.getEl().unmask( );
			}		
			});
	}	
}


//执行导出简单EXCEL
scheduling.shortScheduleModify.exportExcel=function(){	
	var actionUrl=scheduling.realPath('exportExcel.action');	
	var queryParams ={
						'vo.simDto.yearMonth':scheduling.shortScheduleModify.ymd,//年月
						'vo.simDto.schedulingtype':'TFR',//短途排班
						'vo.simDto.schedulingDepartCode':scheduling.shortScheduleModify.schedulingDepartCode,//车队小组编码
						'vo.simDto.schedulingStatus':'Y'//状态为可用的						
					 };
	
	if(!Ext.fly('downloadAttachFileForm')){
			    var frm = document.createElement('form');
			    frm.id = 'downloadAttachFileForm';
			    frm.style.display = 'none';
			    document.body.appendChild(frm);
		}
		var myMask = new Ext.LoadMask(Ext.getBody(),  {msg:scheduling.shortScheduleModify.i18n('foss.scheduling.ShortSchedule.tip.exporting')});
			//myMask.show();
		Ext.Ajax.request({
		url:actionUrl,
		form: Ext.fly('downloadAttachFileForm'),
		method : 'POST',
		params : queryParams,
		isUpload: true,
		exception : function(response,opts) {
			var result = Ext.decode(response.responseText);
			Ext.MessageBox.alert(scheduling.shortScheduleModify.i18n('foss.scheduling.ShortSchedule.tip.title'),result.message);
			myMask.hide();
		},success : function(response,opts) {
			Ext.MessageBox.alert(scheduling.shortScheduleModify.i18n('foss.scheduling.ShortSchedule.tip.title'),'导出成功');
			myMask.hide();
		}		
		});
}
//执行导出EXCEL
scheduling.shortScheduleModify.exportComplexExcel=function(){	
	var actionUrl=scheduling.realPath('exportTruckSchedulingList.action');	
	var queryParams ={
						'vo.simDto.yearMonth':scheduling.shortScheduleModify.ymd,//年月
						'vo.simDto.schedulingtype':'TFR',//短途排班
						'vo.simDto.schedulingDepartCode':scheduling.shortScheduleModify.schedulingDepartCode,//车队小组编码
						'vo.simDto.schedulingStatus':'Y'//状态为可用的						
					 };
	
	if(!Ext.fly('downloadAttachFileForm')){
			    var frm = document.createElement('form');
			    frm.id = 'downloadAttachFileForm';
			    frm.style.display = 'none';
			    document.body.appendChild(frm);
		}
		var myMask = new Ext.LoadMask(Ext.getBody(),  {msg:scheduling.shortScheduleModify.i18n('foss.scheduling.ShortSchedule.tip.exporting')});//"正在导出，请稍等..."
			//myMask.show();
		Ext.Ajax.request({
		url:actionUrl,
		form: Ext.fly('downloadAttachFileForm'),
		method : 'POST',
		params : queryParams,
		isUpload: true,
		exception : function(response,opts) {
			var result = Ext.decode(response.responseText);
			Ext.MessageBox.alert(scheduling.shortScheduleModify.i18n('foss.scheduling.ShortSchedule.tip.title'),result.message);
			myMask.hide();
		},success : function(response,opts) {
			Ext.MessageBox.alert(scheduling.shortScheduleModify.i18n('foss.scheduling.ShortSchedule.tip.title'),
			scheduling.shortScheduleModify.i18n('foss.scheduling.ShortSchedule.button.exportDate.success'));//'导出成功'
			myMask.hide();
		}		
		});
}

//制定、修改、查看短途排班(动态的生成js对象)
scheduling.shortScheduleModify.makeOrModify = function(){		
	
}
//入口
Ext.onReady(function() {
	//部门名称
	if(!Ext.isEmpty(scheduling.queryShortSchedule.driverOrgName)){
		scheduling.shortScheduleModify.driverOrgName=scheduling.queryShortSchedule.driverOrgName;
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
					scheduling.shortScheduleModify.obtainValFieldWhenClicked(colVal,fieldName,driverCode,'edited');
					console.log(2);
				}
			}
			
	});
	//编辑的combox
	var store = Ext.create('Foss.shortScheduleModify.store.PlanTypeCombox');
	//排班表
	var grid;
	//本月第一天
	var schedulingDate = scheduling.shortScheduleModify.ymd+'-01';
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
							"if(val=='REST'){  metadata.tdCls = metadata.tdCls +'alertedCell'; return '休息';} " +
							"else if(val=='WORK'){ metadata.tdCls = metadata.tdCls +'alertedCellGreen'; return '出车';} " +
							"else if(val=='DUTY'){ metadata.tdCls = metadata.tdCls +'alertedCellGreen';  return '值班'; }" +
							"else if(val=='TRAINING') {  metadata.tdCls = metadata.tdCls +'alertedCellGreen';  return '培训';} " +
							"else if(val=='LEAVE') {  metadata.tdCls = metadata.tdCls +'alertedCellRed';return '离岗'; }" +
							"else if(val=='UNKNOWN'){  return '未知';}" +
							"else {return '未知';}" +
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
									"if(newValue!=oldValue)" +
									"{" +
										"console.log(3); " +
										"if(newValue=='WORK')" +
										"{" +
											"console.log(5);" +
											"Ext.ux.Toast.msg(scheduling.shortScheduleModify.i18n('foss.scheduling.ShortSchedule.tip.title'), scheduling.shortScheduleModify.i18n('foss.shortScheduleModify.workPrompt'));" +
										    "Ext.getCmp('Foss_shortScheduleModify_panle_scheduleForms_button_ID').show();" +											
										"}" +
										"else{" +
												"Ext.getCmp('Foss_shortScheduleModify_panle_scheduleForms_button_ID').hide();" +
												//提示用户确认，改为非“出车”状态，且删除任务列表
												"console.log(4);scheduling.shortScheduleModify.delteTaskAndModifySchedule(newValue);" +
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
			var gridTitle = scheduling.shortScheduleModify.driverOrgName+'车队'+scheduling.shortScheduleModify.ymd+'排班信息';
			
			//传入表格列及编辑插件和grid对象一起实例化
			 
			//如果为修改排班，则可以编辑下拉
			if(scheduling.shortScheduleModify.actionType=='REPORT_AND_MODIFY'){
				grid = Ext.create('Foss.shortScheduleModify.grid.TruckSchedulingTaskGrid',{
				 	columns:gcs,				 	
				 	title:gridTitle,
				 	plugins:[cellEditing],
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
								scheduling.shortScheduleModify.obtainValFieldWhenClicked(colVal,fieldName,driverCode,'select');
								scheduling.shortScheduleModify.oldValue=colVal;
								scheduling.shortScheduleModify.driverCode=driverCode;
								scheduling.shortScheduleModify.driverPhone=record.data.driverPhone;	
								scheduling.shortScheduleModify.fieldName=fieldName;	
								console.log(1);
								console.log(fieldName);
								
				 			}
				 		}
				 	}) ,					
					//导出按钮
					tbar:[
					     {xtype:'tbspacer',flex:1},
					    /* {
							xtype:'button',	
							text: '导出排班数据',
							handler: function() {
								scheduling.shortScheduleModify.exportExcel();
							}
						},*/
						{
							xtype:'button',	
							text: scheduling.shortScheduleModify.i18n('foss.scheduling.ShortSchedule.button.exportDate.lable'),//'导出排班数据',
							handler: function() {
								scheduling.shortScheduleModify.exportComplexExcel();
							}
						}]
				 });
			}else{
				grid = Ext.create('Foss.shortScheduleModify.grid.TruckSchedulingTaskGrid',{
					 	columns:gcs,				 	
					 	title:gridTitle,
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
									scheduling.shortScheduleModify.obtainValFieldWhenClicked(colVal,fieldName,driverCode,'select');
									scheduling.shortScheduleModify.oldValue=colVal;
									scheduling.shortScheduleModify.driverCode=driverCode;
									scheduling.shortScheduleModify.driverPhone=record.data.driverPhone;	
									scheduling.shortScheduleModify.fieldName=fieldName;	
									console.log(1);
					 			}
					 		}
					 	}) ,					
						//导出按钮
						tbar:[
						     {xtype:'tbspacer',flex:1},
						     /*{
								xtype:'button',	
								text: '导出排班数据',
								handler: function() {
									scheduling.shortScheduleModify.exportExcel();
								}
							}*/
						     {
								xtype:'button',	
								text: scheduling.shortScheduleModify.i18n('foss.scheduling.ShortSchedule.button.exportDate.lable'),//'导出排班数据',
								handler: function() {
									scheduling.shortScheduleModify.exportComplexExcel();
								}
							}
						]
				 });
			}
			scheduling.shortScheduleModify.grid=grid;
			 //创建面板容器
			var scheduleForms=Ext.create('Foss.shortScheduleModify.panle.ScheduleForms');
			 //显示于主页面
			 Ext.create('Ext.panel.Panel',{
					id:'T_scheduling-shortScheduleModifyIndex_content',
					cls:"panelContent",
					bodyCls:'panelContent-body',
					layout:'auto',
					margin:'0 0 0 0',
					items : [grid,scheduleForms],
					renderTo: 'T_scheduling-shortScheduleModifyIndex-body'
				});
			 scheduling.shortScheduleModify.grid.getStore().load();
		},
		exception : function(response) {
		}
	});
})

