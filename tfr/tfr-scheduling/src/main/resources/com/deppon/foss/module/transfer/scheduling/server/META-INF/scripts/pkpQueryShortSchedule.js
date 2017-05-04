//package Foss.pkpQueryShortSchedule
// 短途排班任务列表
Ext.define('Foss.pkpQueryShortSchedule.model.TruckSchedulingTask', {
	extend: 'Ext.data.Model',
	// 定义字段
	fields: [
	    {name: 'scheduleId',type:'string'},// 计划ID
	    {name: 'scheduleTaskId',type:'string'},// 计划任务ID
	    {name: 'driverName',type:'string'},// 司机姓名
	    {name: 'driverOrgName',type:'string'},//司机所属部门
	    {name: 'driverOrgCode',type:'string'},//司机所属部门编码
	    {name: 'schedulingDate',type:'date',// 日期
			convert: function(value) {
				if (value != null) {
					var date = new Date(value);
					return Ext.Date.format(date,'Y-m-d');
				} else {
					return null;
				}
		}},
		{name: 'vehicleNo',type:'string'},// 车牌号
		{name: 'frequencyNo',type:'string'},// 班次
		{name: 'truckModelValue', type: 'string'},// 车型
		{name: 'carOwner', type: 'string'},// 车辆所属车队
		{name: 'carOwnerName', type: 'string'},// 车辆所属车队名
		{name: 'driverPhone', type: 'string'},// 司机电话
		{name: 'driverCode',type:'string'},// 司机代码		
		{name: 'planType',type:'string'},// 工作类别		
		{name: 'zoneCode',type:'string'},// 接货区域	
		{name: 'zoneName',type:'string'},// 接货区域	名
		{name: 'deliveryAreaCode',type:'string'},// 送货区域	
		{name: 'deliveryAreaName',type:'string'},// 送货区域	名
		{name: 'pkpRegionalName',type:'string'},//节假日接货区域
		{name: 'deliveryRegionalName',type:'string'},//节假日送货区域
		{name: 'expectedBringVolume',type:'string'},//快递货方数
		//###########################################
		{name: 'dispatchVehicleTask',type:'string'},// 出车任务
		{name:'expectedDispatchVehicleTime',type:'string'},// 预计出车时间
		{name: 'isTheTwoTripTime',type:'string'},//预计二次出车时间
		{name: 'takeGoodsDepartmentName',type:'string'},//带货部门
		{name: 'transferGoodsDepartmentName',type:'string'}, //转货部门
		//###########################################
		{name: 'truckFlag',type:'string'},// 是否本车队小组的车（Y是N否）
		{name: 'truckModel',type:'string'}// 车型	
	]
});

//接送货排班store
Ext.define('Foss.pkpQueryShortSchedule.store.TruckSchedulingTask',{
	extend: 'Ext.data.Store',
	model: 'Foss.pkpQueryShortSchedule.model.TruckSchedulingTask',
	pageSize:10,
	proxy: {
		type: 'ajax',
		url:scheduling.realPath('querySimpleTruckSchedulingList.action'),
		actionMethods:'post',
		reader: {
			type: 'json',
			root: 'vo.simDtos',
			totalProperty : 'totalCount'
		}
	},
	
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	},listeners: {
		beforeload : function(store, operation, eOpts) {
				var queryParams = scheduling.pkpQueryShortSchedule.searchForm.getValues();		
				Ext.apply(operation, {
					params : queryParams
				});
		},datachanged:function(store,eOpts ){
			var workTotal=0;
			var dutyTotal=0;
			var trainingTotal=0;
			var restTotal=0;
			if(!Ext.isEmpty(store)){
				var cnt= store.getCount();
				for(var i=0;i<cnt;i++){
					var record = store.getAt(i);
					if(record.data.planType=='WORK'){
						workTotal=workTotal+1;
					}
					else if(record.data.planType=='TRAINING'){
						trainingTotal=trainingTotal+1;
					}
					else if(record.data.planType=='DUTY'){
						dutyTotal=dutyTotal+1;
					}
					else if(record.data.planType=='REST'){
						restTotal=restTotal+1;
					}
				}
			}
			
			var textArray = scheduling.pkpQueryShortSchedule.rsGrid.getDockedItems('toolbar')[1].items.items;
			textArray[0].setValue(workTotal);
			textArray[1].setValue(dutyTotal);
			textArray[2].setValue(trainingTotal);
			textArray[3].setValue(restTotal);
		}
	}
});

//查询时间空间
Ext.define('Foss.pkpQueryShortSchedule.form.pkpTruckSchedulingSearch.RangeDateField',{
			extend: 'Deppon.form.RangeDateField',
			fieldLabel: scheduling.pkpQueryShortSchedule.i18n('foss.pkpQueryShortSchedule.form.pkpTruckSchedulingSearch.RangeDateField.scheduleDateFrom.lable'),//'排班日期',
			id:'Foss_pkpQueryShortSchedule_form_pkpTruckSchedulingSearch_RangeDateField_ID',
			fieldId:'Foss_pkpQueryShortSchedule_RangeDateField_ID',
			dateType: 'datefield',
			fromName: 'vo.simDto.scheduleDateFrom',
			fromValue:Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()-62), 'Y-m-d'),
			toName: 'vo.simDto.scheduleDateTo',
			toValue:Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()), 'Y-m-d'),
			editable: false,
			dateRange:62
			});
			
//查询表单
Ext.define('Foss.pkpQueryShortSchedule.form.PkpTruckSchedulingSearch',{
	extend: 'Ext.form.Panel',
	id:'Foss_pkpQueryShortSchedule_form_pkpTruckSchedulingSearch_ID',
	layout:'column',	
	frame:true,
	title:scheduling.pkpQueryShortSchedule.i18n('foss.pkpQueryShortSchedule.form.PkpTruckSchedulingSearch.title.lable'),//'查询条件',
	bodyStyle:'padding:5px 5px 0 5px',	
	cls:'autoHeight',
	defaultType: 'textfield',	
	defaults: {
		margin:'5 10 5 10',
		anchor: '90%',
		labelWidth:90
	},
	items: [{
		fieldLabel: scheduling.pkpQueryShortSchedule.i18n('foss.pkpQueryShortSchedule.form.PkpTruckSchedulingSearch.schedulingDepartCode.lable'),//'车队小组',
		name: 'vo.simDto.schedulingDepartCode',
		xtype : 'commonDynamicTransTeamSelector',
		allowBlank:false,
		columnWidth:.3,
		orgCode: scheduling.pkpQueryShortSchedule.topFleetOrgCode,
		readOnly:scheduling.pkpQueryShortSchedule.isPermission('pkpScheduling/schedulingDepartCode')?false:true//TODO	
	},{
		fieldLabel: scheduling.pkpQueryShortSchedule.i18n('foss.pkpQueryShortSchedule.form.PkpTruckSchedulingSearch.vehicleNo.lable'),//'车牌号',
		xtype : 'commonowntruckselector',
		name: 'vo.simDto.vehicleNo',
		allowBlank:true,
		columnWidth:.3	
	},{
		fieldLabel: scheduling.pkpQueryShortSchedule.i18n('foss.pkpQueryShortSchedule.form.PkpTruckSchedulingSearch.driverCode.lable'),//'司机',
		xtype : 'commonowndriverselector',
		name: 'vo.simDto.driverCode',
		allowBlank:true,
		columnWidth:.3
	},
//	{
//		xtype : 'rangeDateField',
//		fieldLabel: scheduling.pkpQueryShortSchedule.i18n('foss.pkpQueryShortSchedule.form.pkpTruckSchedulingSearch.RangeDateField.scheduleDateFrom.lable'),//'排班日期',
//		dateType: 'datefield',
//		id:'Foss_pkpQueryShortSchedule_form_pkpTruckSchedulingSearch_RangeDateField_ID',
//		fieldId:'Foss_stock_QueryForm_HandOverTime_ID',
//		fromName: 'vo.simDto.scheduleDateFrom',
//		fromValue:Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()-62), 'Y-m-d'),
//		toName: 'vo.simDto.scheduleDateTo',
//		toValue:Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()), 'Y-m-d'),
//		editable: false,
//		dateRange:62,
//		labelWidth: 90,
//		columnWidth : 1/2
//		//columnWidth: .6
//	},
	Ext.getCmp('Foss_pkpQueryShortSchedule_form_pkpTruckSchedulingSearch_RangeDateField_ID')==null?Ext.create('Foss.pkpQueryShortSchedule.form.pkpTruckSchedulingSearch.RangeDateField',{labelWidth: 90,columnWidth: .6}):Ext.getCmp('Foss_pkpQueryShortSchedule_form_pkpTruckSchedulingSearch_RangeDateField_ID')
,
	{
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
			text: scheduling.pkpQueryShortSchedule.i18n('foss.pkpQueryShortSchedule.form.PkpTruckSchedulingSearch.button.reset.lable'),//'重置',
			handler: function() {
				scheduling.pkpQueryShortSchedule.searchForm.getForm().reset();
				var currentDeptCode = FossUserContext. getCurrentDeptCode();
				var currentDeptName = FossUserContext. getCurrentDept().name; 
				scheduling.pkpQueryShortSchedule.searchForm.getForm().findField('vo.simDto.schedulingDepartCode').setCombValue(currentDeptName,currentDeptCode);
				scheduling.pkpQueryShortSchedule.searchForm.getForm().findField('vo.simDto.scheduleDateFrom').setValue('');
				scheduling.pkpQueryShortSchedule.searchForm.getForm().findField('vo.simDto.scheduleDateTo').setValue('');
			}
		},{
			border : false,
			columnWidth:.84,
			html: '&nbsp;'
		},{
			columnWidth:.08,
			xtype : 'button',
			cls:'yellow_button',
			text: scheduling.pkpQueryShortSchedule.i18n('foss.pkpQueryShortSchedule.form.PkpTruckSchedulingSearch.button.query.lable'),//'查询',
			handler: function() {
				if(scheduling.pkpQueryShortSchedule.searchForm.getForm().isValid()){
					if(scheduling.pkpQueryShortSchedule.searchForm.getForm().findField('vo.simDto.scheduleDateFrom').getValue()==''||
					   scheduling.pkpQueryShortSchedule.searchForm.getForm().findField('vo.simDto.scheduleDateFrom').getValue()==null||	
					   scheduling.pkpQueryShortSchedule.searchForm.getForm().findField('vo.simDto.scheduleDateTo').getValue()==''||
					   scheduling.pkpQueryShortSchedule.searchForm.getForm().findField('vo.simDto.scheduleDateTo').getValue()==null){
						Ext.ux.Toast.msg(scheduling.pkpQueryShortSchedule.i18n('foss.scheduling.ShortSchedule.tip.title'), "排班日期不能为空！");
						return;
					}
					scheduling.pkpQueryShortSchedule.pagingBar.moveFirst();
				}				
			}
		}]
	},{
		xtype:'hidden',
		name: 'vo.simDto.schedulingtype',
		value:'PKP',//接送货
		allowBlank:true,
		columnWidth:.3		
	}]
});



//查询结果
Ext.define('Foss.pkpQueryShortSchedule.grid.TruckSchedulingTask', {
	extend: 'Ext.grid.Panel',	
	frame: true,
	collapsible: true,
	animCollapse: true,
	title:scheduling.pkpQueryShortSchedule.i18n('foss.pkpQueryShortSchedule.grid.TruckSchedulingTask.title.lable'),//'排班查询结果列表',
	cls:'autoHeight',
	bodyCls:'autoHeight',
	store: null,
	autoScroll:true,
	columns: [{text: scheduling.pkpQueryShortSchedule.i18n('foss.pkpQueryShortSchedule.grid.TruckSchedulingTask.driverName.lable'),//'司机',
				width : 80, 
				dataIndex: 'driverName'
//				menuDisabled:true,
//				sortable:true
			},{
				text: scheduling.pkpQueryShortSchedule.i18n('foss.pkpQueryShortSchedule.grid.TruckSchedulingTask.schedulingDate.lable'),//'日期',
				width : 80, 
				dataIndex: 'schedulingDate'
//				menuDisabled:true,
//				sortable:true
			},{
				text: scheduling.pkpQueryShortSchedule.i18n('foss.pkpQueryShortSchedule.grid.TruckSchedulingTask.planType.lable'),//'工作类别',
//				menuDisabled:true,
				width : 80, 
				dataIndex: 'planType',
				renderer:function(val){
					if(val=='REST') return scheduling.pkpQueryShortSchedule.i18n('foss.pkpQueryShortSchedule.grid.TruckSchedulingTask.planType.REST');//'休息';
					else if(val=='WORK') return scheduling.pkpQueryShortSchedule.i18n('foss.pkpQueryShortSchedule.grid.TruckSchedulingTask.planType.WORK');//'出车';
					else if(val=='DUTY') return scheduling.pkpQueryShortSchedule.i18n('foss.pkpQueryShortSchedule.grid.TruckSchedulingTask.planType.DUTY');//'值班'; 
					else if(val=='TRAINING') return scheduling.pkpQueryShortSchedule.i18n('foss.pkpQueryShortSchedule.grid.TruckSchedulingTask.planType.TRAINING');//'培训'; 
					else if(val=='LEAVE') return scheduling.pkpQueryShortSchedule.i18n('foss.pkpQueryShortSchedule.grid.TruckSchedulingTask.planType.LEAVE');//'离岗';
					else if(val=='UNKNOWN') return scheduling.pkpQueryShortSchedule.i18n('foss.pkpQueryShortSchedule.grid.TruckSchedulingTask.planType.UNKNOWN');//'未知';
					else return scheduling.pkpQueryShortSchedule.i18n('foss.pkpQueryShortSchedule.grid.TruckSchedulingTask.planType.ELSE');//'其他';
				}
//				sortable:true
			},{
				text: scheduling.pkpQueryShortSchedule.i18n('foss.pkpQueryShortSchedule.grid.TruckSchedulingTask.vehicleNo.lable'),//'车牌号',
//				menuDisabled:true,
				width : 80, 
				dataIndex: 'vehicleNo'
//				sortable:true
			},{
				text: '班次',
//				menuDisabled:true,
				width : 80, 
				dataIndex: 'frequencyNo'
//				sortable:true
			},{
				text: '预计出车时间',
				width : 130, 
				dataIndex: 'expectedDispatchVehicleTime'
			},{
				text: '预计二次出车时间',
				width : 130, 
				dataIndex: 'isTheTwoTripTime'
			},{
				text: '出车任务',
				width : 80, 
				dataIndex: 'dispatchVehicleTask',
				renderer: function(value, metadata, record, rowIndex, columnIndex, store) {
					if(value==1){
						return "送+接";
					}else if(value==2){
						return "送+转";
					}else if(value==3){
						return "带+送+接";
					}else if(value==4){
						return "带+送+转";
					}else if(value==5){
						return "二次派送";
					}else if(value==6){
						return "带+二次派送";
					}else{
						return "";
					}
			    }
			},{
				text: '带货部门',
				width : 80, 
				dataIndex: 'takeGoodsDepartmentName'
			}
			,{
				text: '带货方数',
//				menuDisabled:true,
				width : 80, 
				dataIndex: 'expectedBringVolume'
//				sortable:true
			},{
				text: '转货部门',
				width : 80, 
				dataIndex: 'transferGoodsDepartmentName'
			},{
				text: scheduling.pkpQueryShortSchedule.i18n('foss.pkpQueryShortSchedule.grid.TruckSchedulingTask.truckModelValue.lable'),//'车型',
//				menuDisabled:true,
				width : 80, 
				dataIndex: 'truckModelValue'
//				sortable:true
			},{
//				menuDisabled:true,
				text: scheduling.pkpQueryShortSchedule.i18n('foss.pkpQueryShortSchedule.grid.TruckSchedulingTask.carOwnerName.lable'),//'车辆所属车队',
				width : 200, 
				dataIndex: 'carOwnerName',
//				sortable:true,
				renderer: function(value, metadata, record, rowIndex, columnIndex, store) {
					var me = this,
					deValue = value + "" ;
					metadata.tdAttr = 'data-qtip="' + deValue + '"';
					return value;
			    }
			},{
				text: scheduling.pkpQueryShortSchedule.i18n('foss.pkpQueryShortSchedule.grid.TruckSchedulingTask.driverPhone.lable'),//'司机电话',
//				menuDisabled:true,
				width : 80,  
				dataIndex: 'driverPhone'
//				sortable:true
			},{
//				menuDisabled:true,
				text: scheduling.pkpQueryShortSchedule.i18n('foss.pkpQueryShortSchedule.grid.TruckSchedulingTask.driverOrgName.lable'),//'司机所属车队',
				width : 200, 
				dataIndex: 'driverOrgName',
//				sortable:true,
				renderer: function(value, metadata, record, rowIndex, columnIndex, store) {
					var me = this,
					deValue = value + "" ;
					metadata.tdAttr = 'data-qtip="' + deValue + '"';
					return value;
			    }
			},{
				text:'司机所属部门编码',
				hidden:true,
				dataIndex:'driverOrgCode'
			},{
				text: '接货区域',//'区域', 
//				menuDisabled:true,
				width : 150,  
				dataIndex: 'zoneName'
//				sortable:true
			},{
				text:'节假日接货区域',
				width : 350,
				dataIndex:'pkpRegionalName'
			},{
				text:'送货区域',
				width : 150,
				dataIndex:'deliveryAreaName'
			}
			,{
				text:'节假日送货区域',
				width : 350,
				dataIndex:'deliveryRegionalName'
			}
	],
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
			   fieldLabel:scheduling.pkpQueryShortSchedule.i18n('foss.pkpQueryShortSchedule.grid.TruckSchedulingTask.workTotal.lable'),//'出车人次',
			   labelWidth:80,
			   minWidth:140,
			   columnWidth:.12,
			   dataIndex: 'workTotal'
		   },{
			   fieldLabel:scheduling.pkpQueryShortSchedule.i18n('foss.pkpQueryShortSchedule.grid.TruckSchedulingTask.dutyTotal.lable'),//'值班人次',
			   columnWidth:.12,
			   labelWidth:80,
			   minWidth:140,
			   dataIndex: 'dutyTotal'
		   },{
			   fieldLabel:scheduling.pkpQueryShortSchedule.i18n('foss.pkpQueryShortSchedule.grid.TruckSchedulingTask.trainingTotal.lable'),//'培训人次',
			   labelWidth:80,
			   minWidth:140,
			   columnWidth:.12,
			   dataIndex: 'trainingTotal'
		   },{
			   fieldLabel:scheduling.pkpQueryShortSchedule.i18n('foss.pkpQueryShortSchedule.grid.TruckSchedulingTask.restTotal.lable'),//'休息人次',
			   columnWidth:.12,	
			   minWidth:140,
			   labelWidth:80,
			   dataIndex: 'restTotal'
		   }]
	}],
	viewConfig: {
        stripeRows: false,
		getRowClass: function(record, rowParams, rp, store) {
			var vehicleNo = record.get('vehicleNo');
			var truckFlag = record.get('truckFlag');
			var planType = record.get('planType');
			if(!Ext.isEmpty(planType)){
				if(planType=='WORK'||planType=='DUTY'||planType=='TRAINING') return 'truckSchedulingTask-row-green';//'出车';
				else if(planType=='LEAVE') return 'truckSchedulingTask-row-red';//'离岗';
				else if(planType=='REST') return 'truckSchedulingTask-row-rest';//'离岗';
			}
			//if(!Ext.isEmpty(vehicleNo)&&!Ext.isEmpty(truckFlag)&&truckFlag=='N'){
			//	return 'truckSchedulingTask-row-yellow';
			//}
			
		}
	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.pkpQueryShortSchedule.store.TruckSchedulingTask');
		me.bbar =Ext.create('Deppon.StandardPaging',{
			store:me.store,
			plugins: 'pagesizeplugin'
		});
		me.selModel = Ext.create('Ext.selection.CheckboxModel');	
		scheduling.pkpQueryShortSchedule.pagingBar = me.bbar;
		me.callParent([cfg]);
	}
});









//导入表单
Ext.define('Foss.pkpQueryShortSchedule.form.ImportShortSchedule',{
		extend: 'Ext.form.Panel',	
		cls:'autoHeight',
		defaultType: 'textfield',
		layout:'column',
		defaults: {
			margin:'5 5 5 5',
			anchor: '100%',
			labelWidth:90
		},
		items : [{
					xtype : 'commonDynamicTransTeamSelector',
					fieldLabel: scheduling.pkpQueryShortSchedule.i18n('foss.pkpQueryShortSchedule.form.ImportShortSchedule.schedulingDepartCode.lable'),//'车队小组',
					name: 'vo.tsEntity.schedulingDepartCode',
					allowBlank:false,
					readOnly:scheduling.pkpQueryShortSchedule.isPermission('pkpScheduling/schedulingDepartCode')?false:true,//TODO
					columnWidth:.5,
					orgCode: scheduling.pkpQueryShortSchedule.topFleetOrgCode
				},{
					xtype: 'datefield',
					format:'Y-m',
					fieldLabel: scheduling.pkpQueryShortSchedule.i18n('foss.pkpQueryShortSchedule.form.ImportShortSchedule.yearMonth.lable'),//'排班年月',
					name: 'vo.tsEntity.yearMonth',
					allowBlank:false,
					columnWidth:.5
				},{
			        xtype: 'filefield',
			        name: 'uploadFile',
			        readOnly:false,
			        buttonOnly:false,
			        fieldLabel: scheduling.pkpQueryShortSchedule.i18n('foss.pkpQueryShortSchedule.form.ImportShortSchedule.uploadFile.lable'),//'导入文件',
			        msgTarget: 'side',
			        cls:'uploadFile',
			        allowBlank: false,			        
			        buttonText: scheduling.pkpQueryShortSchedule.i18n('foss.pkpQueryShortSchedule.form.ImportShortSchedule.uploadFile.buttonText.lable'),//'浏览',
			      	columnWidth:.85
			    },{
					xtype : 'button',
					columnWidth:.15,
					cls:'cleanBtn',
					text: scheduling.pkpQueryShortSchedule.i18n('foss.pkpQueryShortSchedule.form.ImportShortSchedule.cleanBtn.lable'),//'清除',
					handler: function() {
						this.up('form').getForm().findField('uploadFile').reset();
					}
				},{
					xtype:'hidden',
					name: 'vo.tsEntity.schedulingType',
					value:'PKP'//接送货排班导入
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
						text: scheduling.pkpQueryShortSchedule.i18n('foss.pkpQueryShortSchedule.form.ImportShortSchedule.button.cancel.lable'),//'取消',
						handler: function() {
							this.up('form').getForm().findField('uploadFile').reset();
							this.up('window').close();
						}
					},{
						border : false,
						columnWidth:.7,
						html: '&nbsp;'
					},{
						columnWidth:.15,
						xtype : 'button',
						text: scheduling.pkpQueryShortSchedule.i18n('foss.pkpQueryShortSchedule.form.ImportShortSchedule.import.lable'),//'导入',
						handler: function() {
							var form = this.up('form').getForm();
					            if(form.isValid()){
					            	var myMask = new Ext.LoadMask(this.up('form'), {msg:scheduling.pkpQueryShortSchedule.i18n('foss.scheduling.ShortSchedule.tip.importing')});//"正在导入，请稍等..."
		 							    myMask.show();
					                 form.submit({
					                    url: scheduling.realPath('importShortSchedule.action'),
					                    success: function(form, action) {
					                    	myMask.hide();
					                    	var json =action.result;
					                        Ext.MessageBox.alert(scheduling.pkpQueryShortSchedule.i18n('foss.shortDeparturePlan.confirm.title.lable'),
					                        scheduling.pkpQueryShortSchedule.i18n('foss.scheduling.ShortSchedule.tip.importSuccess')+json.vo.importTotal+'条');
					                    },
										exception : function(form, action) {
											myMask.hide();
					        				json=action.result;
					        				//Ext.MessageBox.alert(scheduling.pkpQueryShortSchedule.i18n('foss.shortDeparturePlan.confirm.title.lable'),json.message);
					        				var msg=json.message;
					        				while(msg.indexOf(';')>-1){
					        					msg=msg.replace(';', "\r\n");
					        				}					        				
					        				//Ext.MessageBox.alert(scheduling.queryShortSchedule.i18n('foss.scheduling.ShortSchedule.tip.title'),json.message);
					        					Ext.create('Ext.window.Window', {
					        				    title: '错误信息提示',
					        				    height: 200,
					        				    width: 400,
					        				    layout: 'fit',
					        				    items: {  
					        				        xtype: 'form',
					        				        border: false,
					        				        items:[
														{
															xtype : 'textarea',
															fieldLabel: '',
															height: 130,
								        				    width: 380,
								        				    autoScroll:true,
								        				    readOnly:true,
															name: 'message',
															value:msg
														}
					        				              ]
					        				    }
					        				}).show();
										},
					        			failure: function(form, action){
					        				myMask.hide();
					        				json=action.result;
					        				//Ext.MessageBox.alert(scheduling.pkpQueryShortSchedule.i18n('foss.shortDeparturePlan.confirm.title.lable'),json.message);
					        				var msg=json.message;
					        				while(msg.indexOf(';')>-1){
					        					msg=msg.replace(';', "\r\n");
					        				}					        				
					        				//Ext.MessageBox.alert(scheduling.queryShortSchedule.i18n('foss.scheduling.ShortSchedule.tip.title'),json.message);
					        					Ext.create('Ext.window.Window', {
					        				    title: '错误信息提示',
					        				    height: 200,
					        				    width: 400,
					        				    layout: 'fit',
					        				    items: {  
					        				        xtype: 'form',
					        				        border: false,
					        				        items:[
														{
															xtype : 'textarea',
															fieldLabel: '',
															height: 130,
								        				    width: 380,
								        				    autoScroll:true,
								        				    readOnly:true,
															name: 'message',
															value:msg
														}
					        				              ]
					        				    }
					        				}).show();
					        			}
					                });
					            }
						}
					}]
				    }
				],
		dockedItems: [],
			
		constructor: function(config){
			var me = this,
				cfg = Ext.apply({}, config);			
				me.callParent([cfg]);
		}
})


//修改排班表单
Ext.define('Foss.pkpQueryShortSchedule.form.ModifyShortSchedule',{
		extend: 'Ext.form.Panel',	
		id:'Foss_pkpQueryShortSchedule_form_modifyShortSchedule_ID',
		cls:'autoHeight',
		defaultType: 'textfield',
		layout:'column',
		defaults: {
			margin:'5 5 5 5',
			anchor: '100%',
			labelWidth:90
		},
		items : [{
					xtype : 'commonDynamicTransTeamSelector',
					fieldLabel: scheduling.pkpQueryShortSchedule.i18n('foss.pkpQueryShortSchedule.form.ImportShortSchedule.schedulingDepartCode.lable'),//'车队小组',
					name: 'schedulingDepartCode',
					allowBlank:false,
					readOnly:scheduling.pkpQueryShortSchedule.isPermission('pkpScheduling/schedulingDepartCode')?false:true,//TODO
					columnWidth:.5,
					orgCode: scheduling.pkpQueryShortSchedule.topFleetOrgCode
				},{
					xtype: 'datefield',
					fieldLabel: scheduling.pkpQueryShortSchedule.i18n('foss.pkpQueryShortSchedule.form.ImportShortSchedule.yearMonth.lable'),//'排班年月',
					format:'Y-m',
					name: 'yearMonth',
					allowBlank:false,
					columnWidth:.5
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
						text: scheduling.pkpQueryShortSchedule.i18n('foss.pkpQueryShortSchedule.form.ImportShortSchedule.button.cancel.lable'),//'取消',
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
						text: scheduling.pkpQueryShortSchedule.i18n('foss.pkpQueryShortSchedule.form.ImportShortSchedule.button.ok.lable'),//'确认',
						handler: function() {
							var form = this.up('form').getForm();
					            if(form.isValid()){
					                //弹出修改界面计划日
					            	var vals = this.up('form').getValues();
					            	//计划日期
					            	scheduling.pkpQueryShortSchedule.ymd = vals.yearMonth;
					            	//车队小组
					            	scheduling.pkpQueryShortSchedule.schedulingDepartCode=vals.schedulingDepartCode;
					            	scheduling.pkpQueryShortSchedule.driverOrgName=form.findField('schedulingDepartCode').rawValue;
					            	var tempTitle;
					            	if(this.up('window').actionType=='REPORT_AND_MODIFY'){
					            		tempTitle =  scheduling.pkpQueryShortSchedule.i18n('foss.pkpQueryShortSchedule.window.ImportShortSchedule.modifydesc.lable');//'修改接送货排班';
					            	}else if (this.up('window').actionType=='REPORT_AND_READ'){
					            		tempTitle =  scheduling.pkpQueryShortSchedule.i18n('foss.pkpQueryShortSchedule.window.ImportShortSchedule.lookdesc.lable');//'报表式接送货排班';
					            	}
					            	//窗口类型
					            	scheduling.pkpQueryShortSchedule.actionType=this.up('window').actionType;
					            	//先判断，如有则先关闭
					            	if(Ext.getCmp('T_scheduling-pkpShortScheduleModify')!=null){
					            		removeTab('T_scheduling-pkpShortScheduleModify');
					            	}
					            	if(this.up('window').actionType=='REPORT_AND_MODIFY'){
						            	Ext.Ajax.request({
											url : scheduling.realPath('querybeExsitScheduling.action'),
											params : {
													'vo.tsEntity.yearMonth':scheduling.pkpQueryShortSchedule.ymd,
													'vo.tsEntity.schedulingType':'PKP',
													'vo.tsEntity.schedulingDepartCode':scheduling.pkpQueryShortSchedule.schedulingDepartCode},
											success : function(response) {
												var result = Ext.decode(response.responseText),
												tatolScheduling = result.vo.tatolScheduling;
												var modifyWindow = Ext.getCmp('Foss_pkpQueryShortSchedule_window_modifyShortSchedule_ID');
												if(tatolScheduling==null||tatolScheduling<=0){
													Ext.Msg.confirm("提示"/*'提示'*/, "当前车队小组，不存在司机排班，请确认是否要拷贝一份司机排班！", function(optional){
						    								if(optional == 'yes'){
						    									//var modifyWindow = Ext.getCmp('Foss_pkpQueryShortSchedule_window_modifyShortSchedule_ID');
						    									var myMask = new Ext.LoadMask(modifyWindow, {msg : "数据提交中，请稍等..."});
						    					 				myMask.show();
						    									Ext.Ajax.request({
						    										url : scheduling.realPath('copyTruckScheduling.action'),
						    										params : {
						    												'vo.tsEntity.copyYearMonth':scheduling.pkpQueryShortSchedule.copyYearMonth,
						    												'vo.tsEntity.yearMonth':scheduling.pkpQueryShortSchedule.ymd,
						    												'vo.tsEntity.schedulingType':'PKP',
						    												'vo.tsEntity.schedulingDepartCode':scheduling.pkpQueryShortSchedule.schedulingDepartCode
						    												},
						    										success : function(response) {
						    											myMask.hide();
						    											var result = Ext.decode(response.responseText);
						    											addTab('T_scheduling-pkpShortScheduleModify',//对应打开的目标页面js的onReady里定义的renderTo
								    											scheduling.pkpQueryShortSchedule.i18n('foss.pkpQueryShortSchedule.window.ImportShortSchedule.design.title'),//制定接送货排班,打开的Tab页的标题
								    											scheduling.realPath('pkpShortScheduleModifyIndex.action')
								    											+ '?ymd="' + scheduling.pkpQueryShortSchedule.ymd + '"'
								    											+ '&schedulingDepartCode="' + scheduling.pkpQueryShortSchedule.schedulingDepartCode + '"'
								    											+ '&actionType="' + scheduling.pkpQueryShortSchedule.actionType + '"'
								    											+ '&driverOrgName="' + scheduling.pkpQueryShortSchedule.driverOrgName + '"'
								    											+ '&topFleetOrgCode="' + scheduling.pkpQueryShortSchedule.topFleetOrgCode + '"'
								    											);
								    									 	//关闭本窗口
						    											modifyWindow.close();
								    									 
						    											},
					    											exception : function(response) {
					    												var json = Ext.decode(response.responseText);
					    												Ext.MessageBox.alert("",json.message);
					    												myMask.hide();
					    											}
						    									})
						    								}else if(optional == 'no'){
						    									addTab('T_scheduling-pkpShortScheduleModify',//对应打开的目标页面js的onReady里定义的renderTo
						    											scheduling.pkpQueryShortSchedule.i18n('foss.pkpQueryShortSchedule.window.ImportShortSchedule.design.title'),//制定接送货排班,打开的Tab页的标题
						    											scheduling.realPath('pkpShortScheduleModifyIndex.action')
						    											+ '?ymd="' + scheduling.pkpQueryShortSchedule.ymd + '"'
						    											+ '&schedulingDepartCode="' + scheduling.pkpQueryShortSchedule.schedulingDepartCode + '"'
						    											+ '&actionType="' + scheduling.pkpQueryShortSchedule.actionType + '"'
						    											+ '&driverOrgName="' + scheduling.pkpQueryShortSchedule.driverOrgName + '"'
						    											+ '&topFleetOrgCode="' + scheduling.pkpQueryShortSchedule.topFleetOrgCode + '"'
						    											);
						    									 	//关闭本窗口
						    									modifyWindow.close();
						    								}else if(optional== 'cancel'){
						    								}
														});
												}else{
													addTab('T_scheduling-pkpShortScheduleModify',//对应打开的目标页面js的onReady里定义的renderTo
															scheduling.pkpQueryShortSchedule.i18n('foss.pkpQueryShortSchedule.window.ImportShortSchedule.design.title'),//制定接送货排班,打开的Tab页的标题
															scheduling.realPath('pkpShortScheduleModifyIndex.action')
															+ '?ymd="' + scheduling.pkpQueryShortSchedule.ymd + '"'
															+ '&schedulingDepartCode="' + scheduling.pkpQueryShortSchedule.schedulingDepartCode + '"'
															+ '&actionType="' + scheduling.pkpQueryShortSchedule.actionType + '"'
															+ '&driverOrgName="' + scheduling.pkpQueryShortSchedule.driverOrgName + '"'
															+ '&topFleetOrgCode="' + scheduling.pkpQueryShortSchedule.topFleetOrgCode + '"'
															);
													 	//关闭本窗口
													modifyWindow.close();
												}
											},
											exception : function(response) {
												var json = Ext.decode(response.responseText);
												Ext.MessageBox.alert("",json.message);
												
											}		
											});
					            	}else{
						            	addTab('T_scheduling-pkpShortScheduleModify',//对应打开的目标页面js的onReady里定义的renderTo
											scheduling.pkpQueryShortSchedule.i18n('foss.pkpQueryShortSchedule.window.ImportShortSchedule.design.title'),//制定接送货排班,打开的Tab页的标题
											scheduling.realPath('pkpShortScheduleModifyIndex.action')
											+ '?ymd="' + scheduling.pkpQueryShortSchedule.ymd + '"'
											+ '&schedulingDepartCode="' + scheduling.pkpQueryShortSchedule.schedulingDepartCode + '"'
											+ '&actionType="' + scheduling.pkpQueryShortSchedule.actionType + '"'
											+ '&driverOrgName="' + scheduling.pkpQueryShortSchedule.driverOrgName + '"'
											+ '&topFleetOrgCode="' + scheduling.pkpQueryShortSchedule.topFleetOrgCode + '"'
											);
									 	//关闭本窗口
									 	this.up('window').close();
					            		
					            	}
					            	
					            }
						}
					}]
				    }
				],
		dockedItems: [],
			
		constructor: function(config){
			var me = this,
				cfg = Ext.apply({}, config);			
				me.callParent([cfg]);
		}
})


//导入窗口
Ext.define('Foss.pkpQueryShortSchedule.window.ImportShortSchedule', {
	extend:'Ext.window.Window',
	id:'Foss_pkpQueryShortSchedule_window_importShortSchedule_ID',
	title: scheduling.pkpQueryShortSchedule.i18n('foss.pkpQueryShortSchedule.window.ImportShortSchedule.title.lable'),//'接送货排班导入',
	modal:true,
	closeAction:'hide',
	width: 550,
	bodyCls: 'autoHeight',
	layout: 'auto',		
	listeners:{
		hide:function(comp, eOpts){
			this.down('form').getForm().findField('uploadFile').reset();
			this.down('form').getForm().findField('vo.tsEntity.yearMonth').reset();
		}
	},
	items:[Ext.create('Foss.pkpQueryShortSchedule.form.ImportShortSchedule')]
});


//弹出修改、报表式查看窗口
Ext.define('Foss.pkpQueryShortSchedule.window.ModifyShortSchedule', {
	extend:'Ext.window.Window',
	id:'Foss_pkpQueryShortSchedule_window_modifyShortSchedule_ID',
	title: scheduling.pkpQueryShortSchedule.i18n('foss.pkpQueryShortSchedule.window.ImportShortSchedule.modify.lable'),//'修改排班',
	modal:true,
	closeAction:'hide',
	width: 550,
	bodyCls: 'autoHeight',
	layout: 'auto',	
	actionType:null,
	setActionType:function(actionType){
		this.actionType=actionType;
	},
	getActionType:function(){
		return this.actionType;
	},
	listeners:{
			hide:function(comp, eOpts){
				this.down('form').getForm().findField('yearMonth').reset( );
			}
	},
	items:[Ext.getCmp('Foss_pkpQueryShortSchedule_form_modifyShortSchedule_ID')==null?Ext.create('Foss.pkpQueryShortSchedule.form.ModifyShortSchedule'):Ext.getCmp('Foss_pkpQueryShortSchedule_form_modifyShortSchedule_ID')]
});



//初始化排班表单
Ext.define('Foss.pkpQueryShortSchedule.form.InitShortSchedule',{
		extend: 'Ext.form.Panel',	
		id:'Foss_pkpQueryShortSchedule_form_InitShortSchedule_ID',
		cls:'autoHeight',
		defaultType: 'textfield',
		layout:'column',
		defaults: {
			margin:'5 5 5 5',
			anchor: '100%',
			labelWidth:90
		},
		items : [{
					xtype : 'commonDynamicTransTeamSelector',
					fieldLabel: scheduling.pkpQueryShortSchedule.i18n('foss.pkpQueryShortSchedule.form.ImportShortSchedule.schedulingDepartCode.lable'),//'车队小组',
					name: 'schedulingDepartCode',
					allowBlank:false,
					readOnly:scheduling.pkpQueryShortSchedule.isPermission('pkpScheduling/schedulingDepartCode')?false:true,//TODO
					columnWidth:.5,
					orgCode: scheduling.pkpQueryShortSchedule.topFleetOrgCode
				},{
					xtype: 'datefield',
					fieldLabel: scheduling.pkpQueryShortSchedule.i18n('foss.pkpQueryShortSchedule.form.ImportShortSchedule.yearMonth.lable'),//'排班年月',
					format:'Y-m',
					name: 'yearMonth',
					allowBlank:false,
					columnWidth:.5
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
						text: scheduling.pkpQueryShortSchedule.i18n('foss.pkpQueryShortSchedule.form.ImportShortSchedule.button.cancel.lable'),//'取消',
						handler: function() {
							this.up('form').getForm().findField('yearMonth').reset( );
							this.up('window').close();
						}
					},{
						border : false,
						columnWidth:.7,
						html: '&nbsp;'
					},{
						columnWidth:.15,
						xtype : 'button',
						text: scheduling.pkpQueryShortSchedule.i18n('foss.pkpQueryShortSchedule.form.ImportShortSchedule.button.ok.lable'),//'确认',
						handler: function() {
							//不可用
							var btn=this;
							btn.disable(true);
							//获取值
							var vals = this.up('form').getValues();
							var schedulingDepartCode=vals.schedulingDepartCode;
							var yearMonth=vals.yearMonth;
							//表单校验
							var form = this.up('form');
							var bform =form.getForm();
				            if(bform.isValid()){
				                var actionUrl=scheduling.realPath('initDeaprtDriverScheduling.action');
								var queryParams={
													'vo.tsEntity.schedulingDepartCode':schedulingDepartCode,// 排班部门
													'vo.tsEntity.yearMonth':yearMonth,//排班年月
													'vo.tsEntity.schedulingType':'PKP'//排班类型
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
										var searchForm =scheduling.pkpQueryShortSchedule.searchForm;
										//将新增的条件给查询条件
										if(!Ext.isEmpty(searchForm)){
											searchForm.getForm().findField('vo.simDto.schedulingDepartCode').setCombValue(bform.findField('schedulingDepartCode').rawValue,schedulingDepartCode);														
											form.up('window').close();
										}
										
										//准备跳转到修改界面，进行调整
										//先判断，如有则先关闭
						            	if(Ext.getCmp('T_scheduling-pkpShortScheduleModify')!=null){
						            		removeTab('T_scheduling-pkpShortScheduleModify');
						            	}
						            	scheduling.pkpQueryShortSchedule.driverOrgName=bform.findField('schedulingDepartCode').rawValue;
						            	//打开新的标签进行操作  	
											
											addTab('T_scheduling-pkpShortScheduleModify',//对应打开的目标页面js的onReady里定义的renderTo
											scheduling.pkpQueryShortSchedule.i18n('foss.pkpQueryShortSchedule.window.ImportShortSchedule.design.title'),//制定接送货排班,打开的Tab页的标题
											scheduling.realPath('pkpShortScheduleModifyIndex.action')
											+ '?ymd="' + yearMonth + '"'
											+ '&schedulingDepartCode="' + schedulingDepartCode + '"'
											+ '&actionType="REPORT_AND_MODIFY"'
											+ '&driverOrgName="' + scheduling.pkpQueryShortSchedule.driverOrgName + '"'
											+ '&topFleetOrgCode="' + scheduling.pkpQueryShortSchedule.topFleetOrgCode + '"'
											);
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
						}
					}]
				    }
				],
		dockedItems: [],			
		constructor: function(config){
			var me = this,
				cfg = Ext.apply({}, config);			
				me.callParent([cfg]);
		}
})

//初始化排班窗口
Ext.define('Foss.pkpQueryShortSchedule.window.InitShortSchedule', {
	extend:'Ext.window.Window',
	id:'Foss_pkpQueryShortSchedule_window_InitShortSchedule_ID',
	title: '初始化排班',
	modal:true,
	closeAction:'hide',
	width: 550,
	bodyCls: 'autoHeight',
	layout: 'auto',	
	actionType:null,
	setActionType:function(actionType){
		this.actionType=actionType;
	},
	getActionType:function(){
		return this.actionType;
	},
	listeners:{
			hide:function(comp, eOpts){
				this.down('form').getForm().findField('yearMonth').reset( );
			}
	},
	items:[Ext.getCmp('Foss_pkpQueryShortSchedule_form_InitShortSchedule_ID')==null?Ext.create('Foss.pkpQueryShortSchedule.form.InitShortSchedule'):Ext.getCmp('Foss_pkpQueryShortSchedule_form_InitShortSchedule_ID')]
});

//入口
Ext.onReady(function() {	
	//查询表单
	var searchForm = Ext.getCmp('Foss_pkpQueryShortSchedule_form_pkpTruckSchedulingSearch_ID');
	if(searchForm == null){
		 searchForm = Ext.create('Foss.pkpQueryShortSchedule.form.PkpTruckSchedulingSearch');		
	}else{
		searchForm = Ext.getCmp('Foss_pkpQueryShortSchedule_form_pkpTruckSchedulingSearch_ID');
	}
	//角色
	var currentUserDepts = FossUserContext.getCurrentUserRoleCodes();
	console.log(currentUserDepts);
	//设置当前部门编码
	var currentDeptCode = FossUserContext. getCurrentDeptCode();
	var currentDeptName = FossUserContext. getCurrentDept().name;
	searchForm.getForm().findField('vo.simDto.schedulingDepartCode').setCombValue(currentDeptName,currentDeptCode);
	//判断角色,如果是超级权限
	if(Ext.Array.indexOf(currentUserDepts,'1')>-1){
		searchForm.getForm().findField('vo.simDto.schedulingDepartCode').setReadOnly(false);
	}
	scheduling.pkpQueryShortSchedule.searchForm=searchForm;
	//查询 结果列表
	var rsGrid = Ext.create('Foss.pkpQueryShortSchedule.grid.TruckSchedulingTask',{
		tbar:[{
					xtype:'hiddenfield',
					flex:10
					},{
						xtype: 'button', 
						text: '初始化排班',
						tooltip:'初始化车队所属司机的排班信息（如果已经有数据,则跳过而不会覆盖原有数据）',
						handler: function() {
							//弹出窗口，供用户选择年月
							var initWindow = Ext.getCmp('Foss_pkpQueryShortSchedule_window_InitShortSchedule_ID');
							if(initWindow == null){
								initWindow=Ext.create('Foss.pkpQueryShortSchedule.window.InitShortSchedule');
							}
							//车队小组赋值
							var tempForm = Ext.getCmp('Foss_pkpQueryShortSchedule_form_InitShortSchedule_ID');
							var field =searchForm.getForm().findField('vo.simDto.schedulingDepartCode');
							tempForm.getForm().findField('schedulingDepartCode').setCombValue(field.rawValue,field.lastValue);
							//判断角色,如果是超级权限
							if(Ext.Array.indexOf(currentUserDepts,'1')>-1){
								tempForm.getForm().findField('schedulingDepartCode').setReadOnly(false);
							}
							//排班年月
							tempForm.getForm().findField('yearMonth').setRawValue(Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()), 'Y-m'));							
							initWindow.setTitle('初始化排班');
						    initWindow.center().show();							
						}
					},{
						xtype: 'button', 
						text: scheduling.pkpQueryShortSchedule.i18n('foss.pkpQueryShortSchedule.window.ImportShortSchedule.modify.lable'),//'修改排班',
						handler: function() {
							//弹出窗口，供用户选择年月
							var modifyWindow = Ext.getCmp('Foss_pkpQueryShortSchedule_window_modifyShortSchedule_ID');
							if(modifyWindow == null){
								modifyWindow=Ext.create('Foss.pkpQueryShortSchedule.window.ModifyShortSchedule');
							}
							//车队小组赋值
							var tempForm = Ext.getCmp('Foss_pkpQueryShortSchedule_form_modifyShortSchedule_ID');
							
							
							var records = scheduling.pkpQueryShortSchedule.rsGrid.getSelectionModel().getSelection( );
							if(records.length>=1){
								
								var rowRecord=records[0].data;
								tempForm.getForm().findField('schedulingDepartCode').setCombValue(rowRecord.driverOrgName,rowRecord.driverOrgCode);
								//判断角色,如果是超级权限
								if(Ext.Array.indexOf(currentUserDepts,'1')>-1){
									tempForm.getForm().findField('schedulingDepartCode').setReadOnly(false);
								}
								//排
								//排班年月
								var myDate= new Date(Date.parse(rowRecord.schedulingDate.replace(/-/g, "/")));
								//复制排班年月
								scheduling.pkpQueryShortSchedule.copyYearMonth=Ext.Date.format(myDate, 'Y-m');
								//赋值时间
								tempForm.getForm().findField('yearMonth').setRawValue(Ext.Date.format(myDate, 'Y-m'));
								modifyWindow.setActionType('REPORT_AND_MODIFY');
								modifyWindow.setTitle(scheduling.pkpQueryShortSchedule.i18n('foss.pkpQueryShortSchedule.window.ImportShortSchedule.modify.lable'));
							    modifyWindow.center().show();
							}else{
								var field =searchForm.getForm().findField('vo.simDto.schedulingDepartCode');
								tempForm.getForm().findField('schedulingDepartCode').setCombValue(field.rawValue,field.lastValue);
								//判断角色,如果是超级权限
								if(Ext.Array.indexOf(currentUserDepts,'1')>-1){
									tempForm.getForm().findField('schedulingDepartCode').setReadOnly(false);
								}
								//复制排班年月
								scheduling.pkpQueryShortSchedule.copyYearMonth=Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()), 'Y-m');
								//排班年月
								tempForm.getForm().findField('yearMonth').setRawValue(Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()), 'Y-m'));
								modifyWindow.setActionType('REPORT_AND_MODIFY');
								modifyWindow.setTitle(scheduling.pkpQueryShortSchedule.i18n('foss.pkpQueryShortSchedule.window.ImportShortSchedule.modify.lable'));
							    modifyWindow.center().show();
							}
							
						}
					},{
						xtype: 'button', 
						text: scheduling.pkpQueryShortSchedule.i18n('foss.pkpQueryShortSchedule.window.ImportShortSchedule.import.lable'),//'导入排班',
						handler: function() {
							//弹出窗口，供用户选择年月
							var importWindow = Ext.getCmp('Foss_pkpQueryShortSchedule_window_importShortSchedule_ID');
							if(importWindow == null){
								importWindow=Ext.create('Foss.pkpQueryShortSchedule.window.ImportShortSchedule');								
							}
							var field =searchForm.getForm().findField('vo.simDto.schedulingDepartCode')
							var key=field.lastValue;
							var value=field.rawValue;
							importWindow.down('form').getForm().findField('vo.tsEntity.schedulingDepartCode').setCombValue(value,key);
							//判断角色,如果是超级权限
							if(Ext.Array.indexOf(currentUserDepts,'1')>-1){
								importWindow.down('form').getForm().findField('vo.tsEntity.schedulingDepartCode').setReadOnly(false);
							}
							importWindow.center().show();
						}
					}
					,{
						xtype: 'button', 
						text: scheduling.pkpQueryShortSchedule.i18n('foss.queryShortSchedule.grid.TruckSchedulingTask.button.report.lable'),//'报表式查看',
						handler: function() {
							//弹出窗口，供用户选择年月
							var modifyWindow = Ext.getCmp('Foss_pkpQueryShortSchedule_window_modifyShortSchedule_ID');
							if(modifyWindow == null){
								modifyWindow=Ext.create('Foss.pkpQueryShortSchedule.window.ModifyShortSchedule');
							}
							//车队小组赋值
							var tempForm = Ext.getCmp('Foss_pkpQueryShortSchedule_form_modifyShortSchedule_ID');
							var records = scheduling.pkpQueryShortSchedule.rsGrid.getSelectionModel().getSelection( );
							if(records.length>=1){
								
								var rowRecord=records[0].data;
								tempForm.getForm().findField('schedulingDepartCode').setCombValue(rowRecord.driverOrgName,rowRecord.driverOrgCode);
								//排班年月
								var myDate= new Date(Date.parse(rowRecord.schedulingDate.replace(/-/g, "/")));
								tempForm.getForm().findField('yearMonth').setRawValue(Ext.Date.format(myDate, 'Y-m'));
								modifyWindow.setActionType('REPORT_AND_READ');
								modifyWindow.setTitle(scheduling.pkpQueryShortSchedule.i18n('foss.pkpQueryShortSchedule.window.ImportShortSchedule.modify.lable'));
							    modifyWindow.center().show();
							}else{
								var field =searchForm.getForm().findField('vo.simDto.schedulingDepartCode');
								tempForm.getForm().findField('schedulingDepartCode').setCombValue(field.rawValue,field.lastValue);
								//判断角色,如果是超级权限
								if(Ext.Array.indexOf(currentUserDepts,'1')>-1){
									tempForm.getForm().findField('schedulingDepartCode').setReadOnly(false);
								}
								//排班年月
								tempForm.getForm().findField('yearMonth').setRawValue(Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()), 'Y-m'));
								modifyWindow.setTitle(scheduling.pkpQueryShortSchedule.i18n('foss.queryShortSchedule.grid.TruckSchedulingTask.button.report.lable'));
								modifyWindow.setActionType('REPORT_AND_READ');
							    modifyWindow.center().show();
							}
						}
					},{
						xtype: 'button', 
						text: scheduling.pkpQueryShortSchedule.i18n('foss.queryShortSchedule.grid.TruckSchedulingTask.button.delete.lable'),//'删除',
						handler: function() {
							if(scheduling.pkpQueryShortSchedule.rsGrid != null){
								var records = scheduling.pkpQueryShortSchedule.rsGrid.getSelectionModel().getSelection( );
								var scheduleTaskIds=new Array();
								var scheduleIds=new Array();
								for(var record in records){
									var rowRecord=records[record].data;
									if(Ext.isEmpty(rowRecord.scheduleTaskId)){
										scheduleIds.push(rowRecord.scheduleId)
									}else{
										scheduleTaskIds.push(rowRecord.scheduleTaskId);
									}
									
								}
								//如果选择不为空，则请求删除操作
								if(!Ext.isEmpty(scheduleTaskIds)||!Ext.isEmpty(scheduleIds)){
									Ext.Msg.confirm(scheduling.pkpQueryShortSchedule.i18n('foss.shortDeparturePlan.confirm.title.lable'),
									'确认删除?', function(btn){
									    if (btn == 'yes'){									        
											var myMask = new Ext.LoadMask(Ext.getBody(),  {msg:"正在删除，请稍等..."});
											myMask.show();
											var queryParams={
															'vo.simDto.list':scheduleTaskIds,
															'vo.simDto.scheduleIds':scheduleIds
															};
											var actionUrl=scheduling.realPath('deleteTasksByScheduleTaskIds.action');
											Ext.Ajax.request({
												url : actionUrl,
												params:queryParams,
												//动态创建表单，显示任务信息
												success : function(response) {
													//执行成功
													scheduling.pkpQueryShortSchedule.pagingBar.moveFirst();
													myMask.hide();
												},
												exception : function(response) {
													var json = Ext.decode(response.responseText);
													Ext.MessageBox.alert(scheduling.pkpQueryShortSchedule.i18n('foss.shortDeparturePlan.confirm.title.lable'),json.message);
													myMask.hide();
												}		
												});
									    }
									});
								}else{
									Ext.MessageBox.alert(scheduling.pkpQueryShortSchedule.i18n('foss.shortDeparturePlan.confirm.title.lable'),
									scheduling.pkpQueryShortSchedule.i18n('foss.scheduling.ShortSchedule.tip.chooseDelete'));//'请选择需要删除的排班任务'
								}
							}
						}
					},{
						xtype: 'button', 
						text: scheduling.pkpQueryShortSchedule.i18n('foss.queryShortSchedule.grid.TruckSchedulingTask.button.download.lable'),//'模板下载',
						handler: function() {
							downloadPkpExcelTemplatPath();					
						}
					}
				]
	});
	scheduling.pkpQueryShortSchedule.rsGrid=rsGrid;	
	 //显示于主页面
	 Ext.create('Ext.panel.Panel',{
	 		id: 'T_scheduling-pkpScheduleIndex_content',
			cls:"panelContentNToolbar",
			bodyCls:'panelContent-body',
			layout:'auto',
			margin:'0 0 0 0',
			items : [searchForm,rsGrid],
			renderTo: 'T_scheduling-pkpScheduleIndex-body'
		});
});