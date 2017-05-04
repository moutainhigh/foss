//package Foss.longDeparturePlan
//发车计划模型
Ext.define('Foss.longDeparturePlan.model.TruckDepartPlan', {
	extend: 'Ext.data.Model',
	fields: [
		{name: 'id',type:'string'},//ID
		{name: 'origOrgName',type:'string'},//出发部门
		{name: 'origOrgCode',type:'string'},//出发部门Code
		{name: 'destOrgName',type:'string'},//到达部门
		{name: 'destOrgCode',type:'string'},//到达部门Code
		{name: 'planDate', type: 'date',
			convert: function(value) {
				if (value != null) {
					var date = new Date(value);
					return Ext.Date.format(date,'Y-m-d');
				} else {
					return null;
				}
			}},//发车日期
		{name: 'carTotal', type: 'float'},//车辆数
		{name: 'ownCarTotal', type: 'float'},//自有车数
		{name: 'outerCarTotal', type: 'float'},//外请车数
		{name: 'frequencyTotal', type: 'float'},//班次数
		{name: 'preWeightTotal', type: 'float'},//载重合计
		{name: 'expectedLoad', type: 'float'},//预计载重
		{name: 'weightGapTotal', type: 'float'},//载重缺口
		{name: 'volumeTotal', type: 'float'},//体积合计
		{name: 'preVolumeTotal', type: 'float'},//预计体积
		{name: 'volumeGapTotal', type: 'float'},//体积缺口
		{name: 'isIssue', type: 'string'},//是否异常
		{name: 'createTime', type: 'date',
			convert: function(value) {
				if (value != null) {
					var date = new Date(value);
					return Ext.Date.format(date,'Y-m-d H:i:s');
				} else {
					return null;
				}
			}},//创建时间
		{name: 'createUserName', type: 'string'},//创建人
		{name: 'createUserCode', type: 'string'},//创建人code
		{name: 'ownCarTotal', type: 'string'},//自有车数
		{name: 'outerCarTotal', type: 'string'},//外请车数
		{name: 'weightVolTotal', type: 'string'},//(汇总)重量体积
		{name: 'preWeightVolTotal', type: 'string'},//已完成计划货量合计
		{name: 'gapTotal', type: 'string'}//缺口合计
	]
});

//发车计划数据源
Ext.define('Foss.longDeparturePlan.store.TruckDepartPlan',{
	extend: 'Ext.data.Store',
	model: 'Foss.longDeparturePlan.model.TruckDepartPlan',
	pageSize:10,
	proxy: {
		type: 'ajax',
		url:scheduling.realPath('queryTruckDepartPlan.action'),
		actionMethods:'post',
		reader: {
			type: 'json',
			root: 'vo.planList',
			totalProperty : 'totalCount'
		},
		listeners: {
			exception:function( proxy,response,operation,eOpts ){
				scheduling.longDeparturePlan.searchResult.getStore().removeAll();
			}
		}
	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	},
	listeners: {
		beforeload : function(store, operation, eOpts) {
				var queryParams = scheduling.longDeparturePlan.searchForm.getValues();		
				Ext.apply(operation, {
					params : queryParams
				});
		},
		load:function( store, records,successful,operation,eOpts ){			
			if(Ext.isEmpty(records))
			Ext.ux.Toast.msg('提示信息', '查询结果为空!');
		}
	}
});

//查询时间空间
Ext.define('Foss.longDeparturePlan.form.PlanSearch.RangeDateField',{
			extend: 'Deppon.form.RangeDateField',
			fieldLabel: '创建时间',
			id:'Foss_longDeparturePlan_form_PlanSearch_rangeDateField_ID',
			fieldId:'planSearch_rangeDateField_ID',
			dateType: 'datetimefield_date97',
			fromName: 'vo.planDto.createTimeFrom',
			fromValue:'',
			toName: 'vo.planDto.createTimeTo',
			toValue:'',
			dateRange:31,
			editable: false,
			labelWidth:80
		})

//查询表单
Ext.define('Foss.longDeparturePlan.form.PlanSearch',{
	extend: 'Ext.form.Panel',
	title:scheduling.longDeparturePlan.i18n('foss.longdepartureplan.form.plansearch.title.lable'),//'查询发车计划',
	frame:true,
	layout:'column',	
	bodyStyle:'padding:5px 5px 0 5px',	
	cls:'autoHeight',
	defaultType: 'textfield',	
	defaults: {
		margin:'5 10 5 10',
		anchor: '90%',
		labelWidth:80
	},
	items: [{
		xtype: 'datefield',
		fieldLabel: scheduling.longDeparturePlan.i18n('foss.longdepartureplan.form.plansearch.planDate.lable'),//'发车日期',
		name: 'vo.planDto.planDate',
		format:'Y-m-d',
		value:Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()), 'Y-m-d'),
		allowBlank:false,
		columnWidth:.25		
	},
	{
		xtype : 'dynamicorgcombselector',
		transferCenter:'Y',//外场
		type : 'ORG',// 部门类型 一种部门类型，传递此值
		forceSelection:true,
		fieldLabel : scheduling.longDeparturePlan.i18n('foss.longdepartureplan.form.plansearch.origOrgCode.lable'),//'出发部门',
		name: 'vo.planDto.origOrgCode',
		columnWidth:.25	
	}
	,
	{
		xtype : 'dynamicorgcombselector',
		transferCenter:'Y',//外场
		type : 'ORG',// 部门类型 一种部门类型，传递此值
		forceSelection:true,
		fieldLabel : scheduling.longDeparturePlan.i18n('foss.longdepartureplan.form.plansearch.destOrgCode.lable'),//'到达部门',
		name: 'vo.planDto.destOrgCode',
		columnWidth:.25	
	}
	,{
		xtype : 'commontruckselector',
		fieldLabel: scheduling.longDeparturePlan.i18n('foss.longdepartureplan.form.plansearch.vehicleNo.lable'),//'车牌号',
		name: 'vo.planDto.vehicleNo',
		allowBlank:true,
		columnWidth:.25		
	},
	Ext.getCmp('Foss_longDeparturePlan_form_PlanSearch_rangeDateField_ID')==null?Ext.create('Foss.longDeparturePlan.form.PlanSearch.RangeDateField',{columnWidth:.5}):Ext.getCmp('Foss_longDeparturePlan_form_PlanSearch_rangeDateField_ID')
	,{
		xtype : 'commonemployeeselector',
		fieldLabel: scheduling.longDeparturePlan.i18n('foss.longdepartureplan.form.plansearch.createUserCode.lable'),//'创建人',
		name: 'vo.planDto.createUserCode',
		allowBlank:true,
		columnWidth:.25		
	},{
		xtype:'hidden',
		fieldLabel: scheduling.longDeparturePlan.i18n('foss.longdepartureplan.form.plansearch.planType.lable'),//'计划类型',
		name: 'vo.planDto.planType',
		value:'LONG',//长途
		columnWidth:.25		
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
			text: scheduling.longDeparturePlan.i18n('foss.longdepartureplan.form.plansearch.button.reset.lable'),//'重置',
			handler: function() {
				var bform=this.up('form').getForm();
				bform.reset();
				/*bform.findField('vo.planDto.createTimeFrom').setRawValue(Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()
						,'00','00','00'), 'Y-m-d H:i:s'));
				bform.findField('vo.planDto.createTimeTo').setRawValue(Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()
						,'23','59','59'), 'Y-m-d H:i:s'));*/
				scheduling.longDeparturePlan.queryTransferCenter(FossUserContext. getCurrentDeptCode());
			}
		},{
			border : false,
			columnWidth:.74,
			html: '&nbsp;'
		},{
			columnWidth:.08,
			xtype : 'button',
			text: scheduling.longDeparturePlan.i18n('foss.longdepartureplan.form.plansearch.button.query.lable'),//'查询',
			cls:'yellow_button',
			handler: function() {
				if(this.up('form').getForm().isValid()){					
					scheduling.longDeparturePlan.pagingBar.moveFirst();
					
				}				 
			}
		},{
			xtype : 'button',
			columnWidth:.1,
			text: '导出明细',
			handler: function() {
				scheduling.longDeparturePlan.exportExcel();
			}
		}]
	}]
});

//新增发车计划表单
Ext.define('Foss.longDeparturePlan.form.AddNewPlan',{
		extend: 'Ext.form.Panel',	
		cls:'autoHeight',
		defaultType: 'textfield',
		layout:'column',
		defaults: {
			margin:'5 5 5 5',
			anchor: '98%',
			labelWidth:80
		},
		items : [{
					xtype: 'datefield',
					fieldLabel: scheduling.longDeparturePlan.i18n('foss.longdepartureplan.form.addnewplan.planDate.lable'),//'发车日期',
					name: 'vo.planDto.planDate',
					format:'Y-m-d',
					value:Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()+1), 'Y-m-d'),
					allowBlank:false,
					columnWidth:.99	
				},
				{
					xtype : 'dynamicorgcombselector',
					transferCenter:'Y',//外场
					type : 'ORG',// 部门类型 一种部门类型，传递此值
					forceSelection:true,
					allowBlank:false,
					fieldLabel : scheduling.longDeparturePlan.i18n('foss.longdepartureplan.form.addnewplan.origOrgCode.lable'),//'出发部门',
					name: 'vo.planDto.origOrgCode',					
					columnWidth:.99
				}
				,
				{
					xtype : 'dynamicorgcombselector',
					transferCenter:'Y',//外场
					type : 'ORG',// 部门类型 一种部门类型，传递此值
					forceSelection:true,
					allowBlank:false,
					fieldLabel : scheduling.longDeparturePlan.i18n('foss.longdepartureplan.form.addnewplan.destOrgCode.lable'),//'到达部门',
					name: 'vo.planDto.destOrgCode',
					columnWidth:.99	
				},{
			        xtype: 'container',
			        columnWidth:1,
					layout:'column',
					defaults: {
						margin:'5 0 5 0'
					},
			        items: [{
						xtype : 'button',
						columnWidth:.15,
						text: scheduling.longDeparturePlan.i18n('foss.longdepartureplan.form.addnewplan.button.cancel.lable'),//'取消',
						handler: function() {		
							this.up('form').getForm().findField('vo.planDto.destOrgCode').reset();
							this.up('window').close();
						}
					},{
						border : false,
						columnWidth:.7,
						html: '&nbsp;'
					},{
						columnWidth:.15,
						xtype : 'button',
						text: scheduling.longDeparturePlan.i18n('foss.longdepartureplan.form.addnewplan.button.new.lable'),//'新增',
						handler: function() {
							var form=this.up('form');
							if(form.getForm().isValid()){
								//校验是否为同一部门
								var origOrgCode=form.getForm().findField('vo.planDto.origOrgCode').getValue();
								var destOrgCode=form.getForm().findField('vo.planDto.destOrgCode').getValue();
								if(origOrgCode==destOrgCode){
									Ext.MessageBox.alert("提示",'出发部门与到达部门相同,请核实');
									return;
								}
								this.disable(true);								
								scheduling.longDeparturePlan.doOpenShortDesignPlan(this,form);
								
							}
						}
					}]
				    }
				],
		constructor: function(config){
			var me = this,
				cfg = Ext.apply({}, config);			
				me.callParent([cfg]);
		}
})


//新增发车计划表单
Ext.define('Foss.longDeparturePlan.form.BatchCreateDepartPlan',{
		extend: 'Ext.form.Panel',	
		cls:'autoHeight',
		defaultType: 'textfield',
		layout:'column',
		defaults: {
			margin:'5 5 5 5',
			anchor: '98%',
			labelWidth:80
		},
		items : [{
					xtype: 'datefield',
					fieldLabel: scheduling.longDeparturePlan.i18n('foss.longdepartureplan.form.addnewplan.planDate.lable'),//'发车日期',
					name: 'vo.planDto.planDate',
					format:'Y-m-d',
					value:Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()+1), 'Y-m-d'),
					allowBlank:false,
					columnWidth:.99	
				},
				{
					xtype : 'dynamicorgcombselector',
					transferCenter:'Y',//外场
					type : 'ORG',// 部门类型 一种部门类型，传递此值
					forceSelection:true,
					allowBlank:false,
					fieldLabel : scheduling.longDeparturePlan.i18n('foss.longdepartureplan.form.addnewplan.origOrgCode.lable'),//'出发部门',
					name: 'vo.planDto.origOrgCode',					
					columnWidth:.99
				},{
			        xtype: 'container',
			        columnWidth:1,
					layout:'column',
					defaults: {
						margin:'5 0 5 0'
					},
			        items: [{
						xtype : 'button',
						columnWidth:.15,
						text: scheduling.longDeparturePlan.i18n('foss.longdepartureplan.form.addnewplan.button.cancel.lable'),//'取消',
						handler: function() {
							this.up('window').close();
						}
					},{
						border : false,
						columnWidth:.7,
						html: '&nbsp;'
					},{
						columnWidth:.15,
						xtype : 'button',
						text: scheduling.longDeparturePlan.i18n('foss.longdepartureplan.form.addnewplan.button.new.lable'),//'新增',
						handler: function() {
							var form=this.up('form');
							if(form.getForm().isValid()){
								this.disable(true);								
								scheduling.longDeparturePlan.batchCreateDepartPlanFun(this,form);
								
							}
						}
					}]
				    }
				],
		constructor: function(config){
			var me = this,
				cfg = Ext.apply({}, config);			
				me.callParent([cfg]);
		}
})

//新增发车计划窗口
Ext.define('Foss.longDeparturePlan.window.AddNewPlan', {
	extend:'Ext.window.Window',
	title: scheduling.longDeparturePlan.i18n('foss.longdepartureplan.window.addnewplan.title.lable'),//'新增发车计划',
	modal:true,
	closeAction:'hide',
	width: 300,
	bodyCls: 'autoHeight',
	layout: 'auto',	
	items:[
	       scheduling.longDeparturePlan.addNewPlanForm=Ext.create('Foss.longDeparturePlan.form.AddNewPlan')
	       ]
});

//批量新增发车计划窗口
Ext.define('Foss.longDeparturePlan.window.BatchCreateDepartPlan', {
	extend:'Ext.window.Window',
	title: '批量新增发车计划',//'新增发车计划',
	modal:true,
	closeAction:'hide',
	width: 300,
	bodyCls: 'autoHeight',
	layout: 'auto',	
	items:[
	       scheduling.longDeparturePlan.batchDepartPlanForm=Ext.create('Foss.longDeparturePlan.form.BatchCreateDepartPlan')
	       ]
});

//新增发车计划窗口获取默认出发部门
scheduling.longDeparturePlan.queryTransferCenterForNewPlanWin=function(origOrgCode){
	var actionUrl=scheduling.realPath('queryTransferCenter.action');
	var queryParams={
						'vo.planDto.origOrgCode':origOrgCode// 出发部门
					};
	//验证并初始化数据
	Ext.Ajax.request({
		url : actionUrl,
		params:queryParams,
		//动态创建表单，显示任务信息
		success : function(response) {
			var json = Ext.decode(response.responseText);
			var org=json.vo.orgInfo;
			if(!Ext.isEmpty(org)){
				if(scheduling.longDeparturePlan.addNewPlanForm){
					scheduling.longDeparturePlan.addNewPlanForm.getForm().findField('vo.planDto.origOrgCode').setCombValue(org.name,org.code);
				}					
				if(scheduling.longDeparturePlan.batchDepartPlanForm){
					scheduling.longDeparturePlan.batchDepartPlanForm.getForm().findField('vo.planDto.origOrgCode').setCombValue(org.name,org.code);
				}
			}
		},
		exception : function(response) {
			var json = Ext.decode(response.responseText);
			Ext.MessageBox.alert("错误提示",json.message);
		}		
	});	

}


//根据当前部门查询外场信息
scheduling.longDeparturePlan.queryTransferCenter=function(origOrgCode){
		var actionUrl=scheduling.realPath('queryTransferCenter.action');
		var queryParams={
							'vo.planDto.origOrgCode':origOrgCode// 出发部门
						};
		//验证并初始化数据
		Ext.Ajax.request({
			url : actionUrl,
			params:queryParams,
			//动态创建表单，显示任务信息
			success : function(response) {
				var json = Ext.decode(response.responseText);
				var org=json.vo.orgInfo;
				if(!Ext.isEmpty(org)&&scheduling.longDeparturePlan.searchForm){
					scheduling.longDeparturePlan.searchForm.getForm().findField('vo.planDto.origOrgCode').setCombValue(org.name,org.code);
				}			
			},
			exception : function(response) {
				var json = Ext.decode(response.responseText);
				Ext.MessageBox.alert("错误提示",json.message);
			}		
		});	
	
}


//根据planId，查询货量预测信息、发车明细、合车记录、日志等
scheduling.longDeparturePlan.openShortDesignPlan=function(record){
	//ID
	scheduling.longDeparturePlan.planId=record.data.id;
	//出发部门
	scheduling.longDeparturePlan.origOrgCode=record.data.origOrgCode;
	//到达部门
	scheduling.longDeparturePlan.destOrgCode=record.data.destOrgCode;
	//计划日期
	scheduling.longDeparturePlan.planDate=record.data.planDate;		
	//关闭原有标签
	if(!Ext.isEmpty(Ext.getCmp('T_scheduling-longScheduleDesignIndex'))){
		removeTab('T_scheduling-longScheduleDesignIndex');
	}
	//打开新标签并查询数据
	addTab('T_scheduling-longScheduleDesignIndex',//对应打开的目标页面js的onReady里定义的renderTo
			'制定长途发车计划',//打开的Tab页的标题
			scheduling.realPath('longScheduleDesignIndex.action')
			+ '?planId="' + scheduling.longDeparturePlan.planId + '"'
			+ '&origOrgCode="' + scheduling.longDeparturePlan.origOrgCode + '"'
			+ '&destOrgCode="' + scheduling.longDeparturePlan.destOrgCode + '"'
			+ '&planDate="' + scheduling.longDeparturePlan.planDate + '"'
			);
}


//新增发车计划，并初始化发车计划
scheduling.longDeparturePlan.addAndInitTruckPlan=function(origOrgCode,destOrgCode,planDate,btn,addForm){
	var actionUrl=scheduling.realPath('addTruckDepartPlan.action');
	var queryParams={
						'vo.planDto.origOrgCode':origOrgCode,// 出发部门
						'vo.planDto.destOrgCode':destOrgCode,//到达部门
						'vo.planDto.planDate':planDate,//计划日期
						'vo.planDto.planType':'LONG'//长途发车计划
					};
	//验证并初始化数据
	Ext.Ajax.request({
		url : actionUrl,
		params:queryParams,
		//动态创建表单，显示任务信息
		success : function(response) {
			var json = Ext.decode(response.responseText);
			var exist=json.vo.planExsit;
			if(!Ext.isEmpty(exist)&&exist=='exist'){
				Ext.ux.Toast.msg('提示','发车计划已存在！');
			}else{
				Ext.ux.Toast.msg('提示','发车计划新增成功！');
			}
			//按钮可用
			if(btn){
				btn.enable(true);
			}
			//查询表单
			var form =scheduling.longDeparturePlan.searchForm;
			//将新增的条件给查询条件
			if(addForm&&!Ext.isEmpty(form)){
				var vals = addForm.getValues();
				var bform = addForm.getForm();
				form.getForm().findField('vo.planDto.origOrgCode').setCombValue(bform.findField('vo.planDto.origOrgCode').rawValue,origOrgCode);
				form.getForm().findField('vo.planDto.destOrgCode').setCombValue(bform.findField('vo.planDto.destOrgCode').rawValue,destOrgCode);
				form.getForm().findField('vo.planDto.planDate').setValue(planDate);
				bform.findField('vo.planDto.destOrgCode').reset();
				addForm.up('window').close();
			}
			//执行查询
			if(!Ext.isEmpty(form)&&form.getForm().isValid()){					
				scheduling.longDeparturePlan.pagingBar.moveFirst();				
			}
		},
		exception : function(response) {
			//按钮可用
			if(btn){
				btn.enable(true);
			}
			var json = Ext.decode(response.responseText);
			Ext.MessageBox.alert("错误提示",json.message);
		}		
	});	
}

scheduling.longDeparturePlan.batchCreateDepartPlanReq=function(origOrgCode,planDate,btn,addForm){
	var actionUrl=scheduling.realPath('batchCreateDepartPlan.action');
	var queryParams={
						'vo.planDto.origOrgCode':origOrgCode,// 出发部门
						'vo.planDto.planDate':planDate,//计划日期
						'vo.planDto.planType':'LONG'//长途发车计划
					};
	//验证并初始化数据
	Ext.Ajax.request({
		url : actionUrl,
		params:queryParams,
		//动态创建表单，显示任务信息
		success : function(response) {
			var json = Ext.decode(response.responseText);			
			//按钮可用
			if(btn){
				btn.enable(true);
			}
			//查询表单
			var form =scheduling.longDeparturePlan.searchForm;
			//将新增的条件给查询条件
			if(addForm&&!Ext.isEmpty(form)){
				var vals = addForm.getValues();
				var bform = addForm.getForm();
				form.getForm().findField('vo.planDto.origOrgCode').setCombValue(bform.findField('vo.planDto.origOrgCode').rawValue,origOrgCode);
				form.getForm().findField('vo.planDto.planDate').setValue(planDate);	
				form.getForm().findField('vo.planDto.destOrgCode').reset();
				addForm.up('window').close();
			}
			//执行查询
			if(!Ext.isEmpty(form)&&form.getForm().isValid()){					
				scheduling.longDeparturePlan.pagingBar.moveFirst();				
			}
		},
		exception : function(response) {
			//按钮可用
			if(btn){
				btn.enable(true);
			}
			var json = Ext.decode(response.responseText);
			Ext.MessageBox.alert("错误提示",json.message);
		}		
	});	
}

//打开指定发车计划
scheduling.longDeparturePlan.doOpenShortDesignPlan=function(btn,addForm){							
		//获取查询表单的数据
		if(addForm){
			var vals =addForm.getValues();
			var origOrgCode=vals['vo.planDto.origOrgCode'];
			var destOrgCode=vals['vo.planDto.destOrgCode'];
			var planDate=vals['vo.planDto.planDate'];					
			if(!Ext.isEmpty(origOrgCode)&&!Ext.isEmpty(destOrgCode)&&!Ext.isEmpty(planDate)){
				//增加发车计划，并初始化
				scheduling.longDeparturePlan.addAndInitTruckPlan(origOrgCode,destOrgCode,planDate,btn,addForm);
			}else{
				if(btn){
					btn.enable(true);
				}				
				Ext.MessageBox.alert("提示",'必须录入出发部门、到达部门，方可新增！');
			}
			
		}
			
}

scheduling.longDeparturePlan.batchCreateDepartPlanFun=function(btn,addForm){							
	//获取查询表单的数据
	if(addForm){
		var vals =addForm.getValues();
		var origOrgCode=vals['vo.planDto.origOrgCode'];
		var planDate=vals['vo.planDto.planDate'];					
		if(!Ext.isEmpty(origOrgCode)&&!Ext.isEmpty(planDate)){
			//增加发车计划，并初始化
			scheduling.longDeparturePlan.batchCreateDepartPlanReq(origOrgCode,planDate,btn,addForm);
		}else{
			if(btn){
				btn.enable(true);
			}				
			Ext.MessageBox.alert("提示",'必须录入出发部门、到达部门，方可新增！');
		}
	}		
}

//发车计划查询结果
Ext.define('Foss.longDeparturePlan.grid.PlanSearchResult', {
	extend: 'Ext.grid.Panel',
	frame: true,
	collapsible: true,
	animCollapse: false,
	title:scheduling.longDeparturePlan.i18n('foss.longdepartureplan.grid.plansearchresult.title.lable'),//'发车计划列表',
	cls:'autoHeight',
	bodyCls:'autoHeight',
	store: null,
	selModel: null,	
	autoScroll:true,
	tbar:[{
		xtype: 'button', 
		text: scheduling.longDeparturePlan.i18n('foss.longdepartureplan.grid.plansearchresult.button.add.lable'),//'新增发车计划',
		hidden:false,
		handler: function() {
			//弹出新增发车计划窗口
			if(scheduling.longDeparturePlan.addNewPlanWin==null){
				scheduling.longDeparturePlan.addNewPlanWin=Ext.create('Foss.longDeparturePlan.window.AddNewPlan');
			}
			scheduling.longDeparturePlan.addNewPlanWin.show();
			//出发部门
			scheduling.longDeparturePlan.queryTransferCenterForNewPlanWin(FossUserContext.getCurrentDeptCode());
		}
	},{
		xtype: 'button', 
		text: '批量新增发车计划',//'新增发车计划',
		hidden:false,
		handler: function() {
			//弹出新增发车计划窗口
			if(scheduling.longDeparturePlan.batchDepartPlanWin==null){
				scheduling.longDeparturePlan.batchDepartPlanWin=Ext.create('Foss.longDeparturePlan.window.BatchCreateDepartPlan');
			}
			scheduling.longDeparturePlan.batchDepartPlanWin.show();
			//出发部门
			scheduling.longDeparturePlan.queryTransferCenterForNewPlanWin(FossUserContext.getCurrentDeptCode());
		}
	}],
	columns: [{
		xtype:'actioncolumn',
		menuDisabled:true,
		text: scheduling.longDeparturePlan.i18n('foss.longdepartureplan.grid.plansearchresult.actioncolumn.lable'),//'操作',
		align: 'center',
		width:50,
		//locked   : true,
		items: [{
                tooltip: scheduling.longDeparturePlan.i18n('foss.longdepartureplan.grid.plansearchresult.tooltip.lable'),//'查看',
				iconCls:'deppon_icons_showdetail',				
                handler: function(grid, rowIndex, colIndex) {		
					var record=grid.getStore().getAt(rowIndex);
					scheduling.longDeparturePlan.openShortDesignPlan(record);
                }
            }]
	},{
		
		menuDisabled:true,
		//locked   : true,
		text: scheduling.longDeparturePlan.i18n('foss.longdepartureplan.grid.plansearchresult.origOrgName.lable'),//'出发部门',
		width:150, 
		dataIndex: 'origOrgName',
		renderer: function(value, metadata, record, rowIndex, columnIndex, store) {
			var me = this,
			deValue = value + "" ;
			metadata.tdAttr = 'data-qtip="' + deValue + '"';
			return value;
	    }
	},{
		
		menuDisabled:true,
		//locked   : true,
		text: scheduling.longDeparturePlan.i18n('foss.longdepartureplan.grid.plansearchresult.destOrgName.lable'),//'到达部门',
		width:150, 
		dataIndex: 'destOrgName',
		renderer: function(value, metadata, record, rowIndex, columnIndex, store) {
			var me = this,
			deValue = value + "" ;
			metadata.tdAttr = 'data-qtip="' + deValue + '"';
			return value;
	    }
	},{
		xtype: 'datecolumn',
		menuDisabled:true,
	    //locked   : true,
		text: scheduling.longDeparturePlan.i18n('foss.longdepartureplan.grid.plansearchresult.planDate.lable'),//'发车日期',
		width:100,
		format:'Y-m-d',
		dataIndex: 'planDate'
	},{
		text: scheduling.longDeparturePlan.i18n('foss.longdepartureplan.grid.plansearchresult.isIssue.lable'),//'是否异常',
		menuDisabled:true,
		width:80, 
		//locked : true,
		dataIndex: 'isIssue',
		renderer:function(value){
			if(value=='Y'){
				return scheduling.longDeparturePlan.i18n('foss.longdepartureplan.grid.plansearchresult.isIssue.YES.lable')//'是'
			}else if(value=='N'){
				return scheduling.longDeparturePlan.i18n('foss.longdepartureplan.grid.plansearchresult.isIssue.NO.lable')//'否'
			}
		}
	},{
		text: scheduling.longDeparturePlan.i18n('foss.longdepartureplan.grid.plansearchresult.frequencyTotal.lable'),//'班次数',
		menuDisabled:true,
		//locked   : true,
		align:'center',
		width:60, 
		dataIndex: 'frequencyTotal'
	},{
		text: scheduling.longDeparturePlan.i18n('foss.longdepartureplan.grid.plansearchresult.carTotal.lable'),//'车辆数',
		menuDisabled:true,
		//locked   : true,
		align:'center',
		width:60, 
		dataIndex: 'carTotal'
	},{
		text: scheduling.longDeparturePlan.i18n('foss.longdepartureplan.grid.plansearchresult.ownCarTotal.lable'),//'自有数',
		menuDisabled:true,
		//locked   : true,
		align:'center',
		width:60, 
		dataIndex: 'ownCarTotal'
	},{
		text: scheduling.longDeparturePlan.i18n('foss.longdepartureplan.grid.plansearchresult.outerCarTotal.lable'),//'外请车数',
		menuDisabled:true,
		//locked   : true,
		align:'center',
		width:60, 
		dataIndex: 'outerCarTotal'
	},{
		text: scheduling.longDeparturePlan.i18n('foss.longdepartureplan.grid.plansearchresult.weightVolTotal.lable'),//'(汇总)重量/体积',
		menuDisabled:true,
		align:'center',
		width:150,
		dataIndex: 'weightVolTotal'
	},{
		text: scheduling.longDeparturePlan.i18n('foss.longdepartureplan.grid.plansearchresult.preWeightVolTotal.lable'),//'已计划重量/体积',
		menuDisabled:true,
		align:'center',
		width:150,
		dataIndex: 'preWeightVolTotal'
	},{
		text: scheduling.longDeparturePlan.i18n('foss.longdepartureplan.grid.plansearchresult.gapTotal.lable'),//'缺口合计',
		menuDisabled:true,
		align:'center',
		width:150,
		dataIndex: 'gapTotal'
	},{
		//xtype: 'datecolumn',
		menuDisabled:true,
	    //format:'Y-m-d H:i:s',
		align:'center',
		text: scheduling.longDeparturePlan.i18n('foss.longdepartureplan.grid.plansearchresult.createTime.lable'),//'创建时间',
		width:140,
		dataIndex: 'createTime'
	},{
		xtype: 'linebreakcolumn',
		menuDisabled:true,
		align:'center',
		text: scheduling.longDeparturePlan.i18n('foss.longdepartureplan.grid.plansearchresult.createUserName.lable'),//'创建人',
		width:120,
		dataIndex: 'createUserName',
		renderer: function(value, metadata, record, rowIndex, columnIndex, store) {
			var me = this,
				deValue = value + "-工号：" + record.get('createUserCode');
			metadata.tdAttr = 'data-qtip="' + deValue + '"';
			return value;
	    }
	}],
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.longDeparturePlan.store.TruckDepartPlan');
		me.bbar =Ext.create('Deppon.StandardPaging',{
			store:me.store,
			plugins: 'pagesizeplugin'
		});
		scheduling.longDeparturePlan.pagingBar=me.bbar;		
		me.callParent([cfg]);	 
	},
	 viewConfig: {
         stripeRows: true
     }
	});	

//执行导出EXCEL
scheduling.longDeparturePlan.exportExcel=function(){	
	var actionUrl=scheduling.realPath('exportPlanExcel.action');	
	var queryParams = scheduling.longDeparturePlan.searchForm.getValues();		
	
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
		exception : function(response,opts) {
		},success : function(response,opts) {			
		}		
		});
}
//显示于标签页
Ext.onReady(function() {
		//发车计划查询表单
		var searchfrom=Ext.create('Foss.longDeparturePlan.form.PlanSearch');
		scheduling.longDeparturePlan.searchForm=searchfrom;
		//发车计划查询结果
		var searchResult=Ext.create('Foss.longDeparturePlan.grid.PlanSearchResult');
		scheduling.longDeparturePlan.searchResult=searchResult;
		//显示查询发车计划界面
		Ext.create('Ext.panel.Panel',{
		id:'T_scheduling-longDeparturePlanIndex_content',
		cls:"panelContentNToolbar",
		bodyCls:'panelContent-body',
		layout:'auto',
		margin:'0 0 0 0',
		items : [searchfrom,searchResult],
		renderTo: 'T_scheduling-longDeparturePlanIndex-body'
	});
	//出发部门
    scheduling.longDeparturePlan.queryTransferCenter(FossUserContext. getCurrentDeptCode());
});