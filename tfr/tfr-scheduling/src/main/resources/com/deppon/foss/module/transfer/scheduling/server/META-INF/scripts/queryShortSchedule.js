//package Foss.queryShortSchedule
// 短途排班任务列表
Ext.define('Foss.queryShortSchedule.model.TruckSchedulingTask', {
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
		{name: 'truckModel', type: 'string'},// 车型
		{name: 'truckModelValue', type: 'string'},// 车型名
		{name: 'carOwner', type: 'string'},// 车辆所属车队code
		{name: 'carOwnerName', type: 'string'},// 车辆所属车队名称
		{name: 'lineNo', type: 'string'},// 线路
		{name: 'lineName', type: 'string'},// 线路名称
		{name: 'driverPhone', type: 'string'},// 司机电话
		{name: 'departTime', type: 'date'},// 发车时间
		{name: 'frequencyNo', type: 'string'},// 班次
		{name: 'driverCode',type:'string'},// 司机代码		
		{name: 'planType',type:'string'},// 工作类别
		{name: 'truckFlag',type:'string'},// 是否本车队小组的车（Y是N否）
		{name: 'departTimeShort',type:'string'}// 发车时间(字符串如:0800)
		
	]
});

//排班查询store
Ext.define('Foss.queryShortSchedule.store.TruckSchedulingTask',{
	extend: 'Ext.data.Store',
	model: 'Foss.queryShortSchedule.model.TruckSchedulingTask',
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
				var queryParams = scheduling.queryShortSchedule.searchForm.getValues();		
				Ext.apply(operation, {
					params : queryParams
				});
		},
		datachanged:function(store,eOpts ){
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
			//统计
			var textArray = scheduling.queryShortSchedule.rsGrid.getDockedItems('toolbar')[1].items.items;
			textArray[0].setValue(workTotal);
			textArray[1].setValue(dutyTotal);
			textArray[2].setValue(trainingTotal);
			textArray[3].setValue(restTotal);
		}
	}
});

//查询时间空间
Ext.define('Foss.queryShortSchedule.form.truckSchedulingSearch.RangeDateField',{
			extend: 'Deppon.form.RangeDateField',
			fieldLabel: scheduling.queryShortSchedule.i18n('foss.queryShortSchedule.form.truckSchedulingSearch.RangeDateField.fieldLabel'),//'排班日期',
			id:'Foss_queryShortSchedule_form_truckSchedulingSearch_RangeDateField_ID',
			fieldId:'Foss_queryShortSchedule_form_truckSchedulingSearch_scheduleDateFrom_ID',
			dateType: 'datefield',
			fromName: 'vo.simDto.scheduleDateFrom',
			fromValue:Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()-62), 'Y-m-d'),
			toName: 'vo.simDto.scheduleDateTo',
			toValue:Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()), 'Y-m-d'),
			editable: false,
			dateRange:62
			});
			
//查询表单
Ext.define('Foss.queryShortSchedule.form.TruckSchedulingSearch',{
	extend: 'Ext.form.Panel',
	id:'Foss_queryShortSchedule_form_truckSchedulingSearch_ID',
	layout:'column',	
	frame:true,
	title:scheduling.queryShortSchedule.i18n('foss.queryShortSchedule.form.TruckSchedulingSearch.title.lable'),//'查询条件',
	bodyStyle:'padding:5px 5px 0 5px',	
	cls:'autoHeight',
	defaultType: 'textfield',	
	defaults: {
		margin:'5 10 5 10',
		anchor: '90%',
		labelWidth:90
	},
	items: [{
		fieldLabel: scheduling.queryShortSchedule.i18n('foss.queryShortSchedule.form.TruckSchedulingSearch.schedulingDepartCode.lable'),//'车队小组',
		name: 'vo.simDto.schedulingDepartCode',
		xtype : 'commonDynamicTransTeamSelector',
		allowBlank:false,
		columnWidth:.3,
		readOnly: scheduling.queryShortSchedule.isPermission('scheduling/schedulingDepartCode')?false:true,//TODO
		orgCode: scheduling.queryShortSchedule.topFleetOrgCode//FossUserContext.getCurrentDeptCode()
	},{
		xtype : 'commonowntruckselector',
		fieldLabel: scheduling.queryShortSchedule.i18n('foss.queryShortSchedule.form.TruckSchedulingSearch.vehicleNo.lable'),//'车牌号',
		name: 'vo.simDto.vehicleNo',
		allowBlank:true,
		columnWidth:.3	
	},{
		xtype : 'commonowndriverselector',
		fieldLabel: scheduling.queryShortSchedule.i18n('foss.queryShortSchedule.form.TruckSchedulingSearch.driverCode.lable'),//'司机',
		name: 'vo.simDto.driverCode',
		allowBlank:true,
		columnWidth:.3		
	},
	Ext.getCmp('Foss_queryShortSchedule_form_truckSchedulingSearch_RangeDateField_ID')==null?Ext.create('Foss.queryShortSchedule.form.truckSchedulingSearch.RangeDateField',{labelWidth: 90,columnWidth: .6}):Ext.getCmp('Foss_queryShortSchedule_form_truckSchedulingSearch_RangeDateField_ID')
		,{
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
			text: scheduling.queryShortSchedule.i18n('foss.queryShortSchedule.form.TruckSchedulingSearch.button.reset.lable'),//'重置',
			handler: function() {
				scheduling.queryShortSchedule.searchForm.getForm().reset();
				var currentDeptCode = FossUserContext. getCurrentDeptCode();
				var currentDeptName = FossUserContext. getCurrentDept().name; 
				scheduling.queryShortSchedule.searchForm.getForm().findField('vo.simDto.schedulingDepartCode').setCombValue(currentDeptName,currentDeptCode);
				scheduling.queryShortSchedule.searchForm.getForm().findField('vo.simDto.scheduleDateFrom').setValue('');
				scheduling.queryShortSchedule.searchForm.getForm().findField('vo.simDto.scheduleDateTo').setValue('');
			}
		},{
			border : false,
			columnWidth:.84,
			html: '&nbsp;'
		},{
			columnWidth:.08,
			xtype : 'button',
			cls:'yellow_button',
			text: scheduling.queryShortSchedule.i18n('foss.queryShortSchedule.form.TruckSchedulingSearch.button.query.lable'),//'查询',
			handler: function() {
				if(scheduling.queryShortSchedule.searchForm.getForm().isValid()){
					if(scheduling.queryShortSchedule.searchForm.getForm().findField('vo.simDto.scheduleDateFrom').getValue()==''||
							scheduling.queryShortSchedule.searchForm.getForm().findField('vo.simDto.scheduleDateFrom').getValue()==null||
							scheduling.queryShortSchedule.searchForm.getForm().findField('vo.simDto.scheduleDateTo').getValue()==''||
							scheduling.queryShortSchedule.searchForm.getForm().findField('vo.simDto.scheduleDateTo').getValue()==null){
						Ext.ux.Toast.msg('提示', "排班日期不能为空！");
						return;
					}
					scheduling.queryShortSchedule.pagingBar.moveFirst();
				}				
			}
		}]
	},{
		xtype:'hidden',
		name: 'vo.simDto.schedulingtype',
		value:'TFR',//短途排班
		allowBlank:true,
		columnWidth:.3		
	}]
});



//查询结果
Ext.define('Foss.queryShortSchedule.grid.TruckSchedulingTask', {
	extend: 'Ext.grid.Panel',	
	frame: true,
	collapsible: true,
	animCollapse: false,
	title:scheduling.queryShortSchedule.i18n('foss.queryShortSchedule.grid.TruckSchedulingTask.title.lable'),//'排班查询结果列表',
	cls:'autoHeight',
	bodyCls:'autoHeight',
	store: null,
	selModel :null,
	autoScroll:true,
	columns: [{
				text: scheduling.queryShortSchedule.i18n('foss.queryShortSchedule.grid.TruckSchedulingTask.driverName.lable'),//'司机',
				flex: 1, 
				dataIndex: 'driverName'
//				menuDisabled:true,
//				sortable:true
			},{
				text: scheduling.queryShortSchedule.i18n('foss.queryShortSchedule.grid.TruckSchedulingTask.schedulingDate.lable'),//'日期',
				flex: 1, 
				dataIndex: 'schedulingDate'
//				menuDisabled:true,
//				sortable:true
			},{
				text: scheduling.queryShortSchedule.i18n('foss.queryShortSchedule.grid.TruckSchedulingTask.planType.lable'),//'工作类别',
//				menuDisabled:true,
				flex: 1, 
				dataIndex: 'planType',
				renderer:function(val){
					if(val=='REST') return scheduling.queryShortSchedule.i18n('foss.queryShortSchedule.grid.TruckSchedulingTask.planType.REST');//'休息';
					else if(val=='WORK') return scheduling.queryShortSchedule.i18n('foss.queryShortSchedule.grid.TruckSchedulingTask.planType.WORK');//'出车';
					else if(val=='DUTY') return scheduling.queryShortSchedule.i18n('foss.queryShortSchedule.grid.TruckSchedulingTask.planType.DUTY');//'值班'; 
					else if(val=='TRAINING') return scheduling.queryShortSchedule.i18n('foss.queryShortSchedule.grid.TruckSchedulingTask.planType.TRAINING');//'培训'; 
					else if(val=='LEAVE') return scheduling.queryShortSchedule.i18n('foss.queryShortSchedule.grid.TruckSchedulingTask.planType.LEAVE');//'离岗';
					else if(val=='UNKNOWN') return scheduling.queryShortSchedule.i18n('foss.queryShortSchedule.grid.TruckSchedulingTask.planType.UNKNOWN');//'未知';
					else return scheduling.queryShortSchedule.i18n('foss.queryShortSchedule.grid.TruckSchedulingTask.planType.ELSE');//'其他';
				}
//				sortable:true
			},{
				text: scheduling.queryShortSchedule.i18n('foss.queryShortSchedule.grid.TruckSchedulingTask.vehicleNo.lable'),//'车牌号',
//				menuDisabled:true,
				flex: 1, 
				dataIndex: 'vehicleNo'
//				sortable:true
			},{
				text: scheduling.queryShortSchedule.i18n('foss.queryShortSchedule.grid.TruckSchedulingTask.truckModelValue.lable'),//'车型',
				flex: 1, 
				dataIndex: 'truckModelValue'
//				menuDisabled:true,
//				sortable:true
			},{				
//				menuDisabled:true,
				text: scheduling.queryShortSchedule.i18n('foss.queryShortSchedule.grid.TruckSchedulingTask.carOwnerName.lable'),//'车辆所属车队',
				flex: 1.5,  
				dataIndex: 'carOwnerName',
//				sortable:true,
				renderer: function(value, metadata, record, rowIndex, columnIndex, store) {
					var me = this,
					deValue = value + "" ;
					metadata.tdAttr = 'data-qtip="' + deValue + '"';
					return value;
			    }				
			},{
//				menuDisabled:true,
				text: scheduling.queryShortSchedule.i18n('foss.queryShortSchedule.grid.TruckSchedulingTask.lineName.lable'),//'线路',
				flex: 1, 
				dataIndex: 'lineName',
//				sortable:true,
				renderer: function(value, metadata, record, rowIndex, columnIndex, store) {
					var me = this,
					deValue = value + "" ;
					metadata.tdAttr = 'data-qtip="' + deValue + '"';
					return value;
			    }
			},{
				xtype: 'linebreakcolumn',
//				menuDisabled:true,
				text: scheduling.queryShortSchedule.i18n('foss.queryShortSchedule.grid.TruckSchedulingTask.driverPhone.lable'),//'司机电话',
				flex: 1,  
				dataIndex: 'driverPhone'
//				sortable:true
			},{
				text: scheduling.queryShortSchedule.i18n('foss.queryShortSchedule.grid.TruckSchedulingTask.departTimeShort.lable'),//'发车时间',
				flex: 0.7,  
				dataIndex: 'departTimeShort'
//				menuDisabled:true,
//				sortable:true
			},{
				text: scheduling.queryShortSchedule.i18n('foss.queryShortSchedule.grid.TruckSchedulingTask.frequencyNo.lable'),//'班次',
				flex: 0.4, 
				dataIndex: 'frequencyNo'
//				menuDisabled:true,
//				sortable:true
			},{
				text: scheduling.queryShortSchedule.i18n('foss.queryShortSchedule.grid.TruckSchedulingTask.driverOrgName.lable'),//'司机所属车队',
				flex: 1.5, 
				dataIndex: 'driverOrgName',
//				menuDisabled:true,
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
			   fieldLabel:scheduling.queryShortSchedule.i18n('foss.queryShortSchedule.grid.TruckSchedulingTask.workTotal.lable'),//'出车人次',
			   labelWidth:80,
			   minWidth:140,
			   columnWidth:.12,
			   dataIndex: 'workTotal'
		   },{
			   fieldLabel:scheduling.queryShortSchedule.i18n('foss.queryShortSchedule.grid.TruckSchedulingTask.dutyTotal.lable'),//'值班人次',
			   columnWidth:.12,
			   labelWidth:80,
			   minWidth:140,
			   dataIndex: 'dutyTotal'
		   },{
			   fieldLabel:scheduling.queryShortSchedule.i18n('foss.queryShortSchedule.grid.TruckSchedulingTask.trainingTotal.lable'),//'培训人次',
			   labelWidth:80,
			   minWidth:140,
			   columnWidth:.12,
			   dataIndex: 'trainingTotal'
		   },{
			   fieldLabel:scheduling.queryShortSchedule.i18n('foss.queryShortSchedule.grid.TruckSchedulingTask.restTotal.lable'),//'休息人次',
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
		me.store = Ext.create('Foss.queryShortSchedule.store.TruckSchedulingTask');
		me.bbar =Ext.create('Deppon.StandardPaging',{
			store:me.store,
			plugins: 'pagesizeplugin'
		});
		me.selModel = Ext.create('Ext.selection.CheckboxModel');	
		scheduling.queryShortSchedule.pagingBar = me.bbar;
		me.callParent([cfg]);
	}
});




//导入表单
Ext.define('Foss.queryShortSchedule.form.ImportShortSchedule',{
		extend: 'Ext.form.Panel',	
		cls:'autoHeight',
		defaultType: 'textfield',
		layout:'column',
		defaults: {
			margin:'5 5 5 5',
			anchor: '98%',
			labelWidth:90
		},
		standardSubmit: true,
		items : [{
					fieldLabel: scheduling.queryShortSchedule.i18n('foss.queryShortSchedule.form.ImportShortSchedule.schedulingDepartCode.lable'),//'车队小组',
					name: 'vo.tsEntity.schedulingDepartCode',
					xtype : 'commonDynamicTransTeamSelector',
					allowBlank:false,
					readOnly: scheduling.queryShortSchedule.isPermission('scheduling/schedulingDepartCode')?false:true,//TODO
					columnWidth:.5,
					orgCode: scheduling.queryShortSchedule.topFleetOrgCode
				},{
					xtype: 'datefield',
					format:'Y-m',
					fieldLabel: scheduling.queryShortSchedule.i18n('foss.queryShortSchedule.form.ImportShortSchedule.yearMonth.lable'),//'排班年月',
					name: 'vo.tsEntity.yearMonth',
					allowBlank:false,
					columnWidth:.5
				},{
			        xtype: 'filefield',
			        name: 'uploadFile',
			        readOnly:false,
			        buttonOnly:false,
			        fieldLabel: scheduling.queryShortSchedule.i18n('foss.queryShortSchedule.form.ImportShortSchedule.uploadFile.lable'),//'导入文件',
			        msgTarget: 'side',
			        cls:'uploadFile',
			        allowBlank: false,			        
			        buttonText: scheduling.queryShortSchedule.i18n('foss.queryShortSchedule.form.ImportShortSchedule.uploadFile.buttonText.lable'),//'浏览',
			      	columnWidth:.85
			    },{
					xtype : 'button',
					columnWidth:.15,
					cls:'cleanBtn',
					text: scheduling.queryShortSchedule.i18n('foss.queryShortSchedule.form.ImportShortSchedule.button.clear.lable'),//'清除',
					handler: function() {
						this.up('form').getForm().findField('uploadFile').reset();						
					}
				},{
					xtype:'hidden',
					name: 'vo.tsEntity.schedulingType',
					value:'TFR'//短途排班导入
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
						text: scheduling.queryShortSchedule.i18n('foss.queryShortSchedule.form.ImportShortSchedule.button.cancel.lable'),//'取消',
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
						text: scheduling.queryShortSchedule.i18n('foss.queryShortSchedule.form.ImportShortSchedule.button.import.lable'),//'导入',
						handler: function() {
							var form = this.up('form').getForm();
					            if(form.isValid()){
					            	var myMask = new Ext.LoadMask(this.up('form'),  {msg:scheduling.queryShortSchedule.i18n('foss.scheduling.ShortSchedule.tip.importing')});//"正在导入，请稍等..."
		 							    myMask.show();
					                form.submit({
					                    url: scheduling.realPath('importShortSchedule.action'),
					                    success: function(form, action) {
					                    	myMask.hide();
					                    	var json =action.result;
					                        Ext.MessageBox.alert(scheduling.queryShortSchedule.i18n('foss.scheduling.ShortSchedule.tip.title'),
					                        '导入成功'+json.vo.importTotal+'条');
					                    },
										exception : function(form, action) {
											myMask.hide();
					        				json=action.result;
					        				var msg=json.message;
					        				while(msg.indexOf(';')>-1){
					        					msg=msg.replace(';', "\r\n");
					        				}			        				
					        				Ext.create('Ext.window.Window', {
					        				    title: scheduling.queryShortSchedule.i18n('foss.scheduling.ShortSchedule.tip.title'),
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
					        				var msg=json.message;
					        				while(msg.indexOf(';')>-1){
					        					msg=msg.replace(';', "\r\n");
					        				}		
					        				Ext.create('Ext.window.Window', {
					        				    title: scheduling.queryShortSchedule.i18n('foss.scheduling.ShortSchedule.tip.title'),
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
Ext.define('Foss.queryShortSchedule.form.ModifyShortSchedule',{
		extend: 'Ext.form.Panel',	
		id:'Foss_queryShortSchedule_form_modifyShortSchedule_ID',
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
					fieldLabel: scheduling.queryShortSchedule.i18n('foss.queryShortSchedule.form.ImportShortSchedule.schedulingDepartCode.lable'),//'车队小组',
					name: 'schedulingDepartCode',
					allowBlank:false,
					readOnly: scheduling.queryShortSchedule.isPermission('scheduling/schedulingDepartCode')?false:true,//TODO
					columnWidth:.5,
					orgCode: scheduling.queryShortSchedule.topFleetOrgCode
				},{
					xtype: 'datefield',
					fieldLabel: scheduling.queryShortSchedule.i18n('foss.queryShortSchedule.form.ImportShortSchedule.yearMonth.lable'),//'排班年月',
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
						text: scheduling.queryShortSchedule.i18n('foss.queryShortSchedule.form.ImportShortSchedule.button.cancel.lable'),//'取消',
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
						text: scheduling.queryShortSchedule.i18n('foss.queryShortSchedule.form.ModifyShortSchedule.button.ok.lable'),//'确认',
						handler: function() {
							var form = this.up('form').getForm();
				            if(form.isValid()){
				                //弹出修改界面计划日
				            	var vals = this.up('form').getValues();
				            	//计划日期
				            	scheduling.queryShortSchedule.ymd = vals.yearMonth;
				            	//车队小组
				            	scheduling.queryShortSchedule.schedulingDepartCode=vals.schedulingDepartCode;
				            	scheduling.queryShortSchedule.driverOrgName=form.findField('schedulingDepartCode').rawValue;
				            	var tempTitle;
				            	if(this.up('window').actionType=='REPORT_AND_MODIFY'){
				            		tempTitle =  scheduling.queryShortSchedule.i18n('foss.queryShortSchedule.window.ModifyShortSchedule.title.lable');//'修改短途排班';
				            	}else if (this.up('window').actionType=='REPORT_AND_READ'){
				            		tempTitle =  scheduling.queryShortSchedule.i18n('foss.queryShortSchedule.window.ModifyShortSchedule.reportLook.lable');//'报表式查看短途排班';
				            	}
				            	//窗口类型
				            	scheduling.queryShortSchedule.actionType=this.up('window').actionType;
				            	//先判断，如有则先关闭
				            	if(Ext.getCmp('T_scheduling-shortScheduleModifyIndex')!=null){
				            		removeTab('T_scheduling-shortScheduleModifyIndex');
				            	}	
				            	if(this.up('window').actionType=='REPORT_AND_MODIFY'){
				            		Ext.Ajax.request({
										url : scheduling.realPath('querybeExsitScheduling.action'),
										params : {
												'vo.tsEntity.yearMonth':scheduling.queryShortSchedule.ymd,
												'vo.tsEntity.schedulingType':'TFR',
												'vo.tsEntity.schedulingDepartCode':scheduling.queryShortSchedule.schedulingDepartCode},
										success : function(response) {
											var result = Ext.decode(response.responseText),
											tatolScheduling = result.vo.tatolScheduling;
											var modifyWindow = Ext.getCmp('Foss_queryShortSchedule_window_modifyShortSchedule_ID');
											if(tatolScheduling==null||tatolScheduling<=0){
												Ext.Msg.confirm("提示"/*'提示'*/, "当前车队小组，不存在司机排班，请确认是否要拷贝一份司机排班！", function(optional){
				    								if(optional == 'yes'){
				    									//var modifyWindow = Ext.getCmp('Foss_pkpQueryShortSchedule_window_modifyShortSchedule_ID');
				    									var myMask = new Ext.LoadMask(modifyWindow, {msg : "数据提交中，请稍等..."});
				    					 				myMask.show();
				    									Ext.Ajax.request({
				    										url : scheduling.realPath('copyTruckScheduling.action'),
				    										params : {
				    												'vo.tsEntity.copyYearMonth':scheduling.queryShortSchedule.copyYearMonth,
				    												'vo.tsEntity.yearMonth':scheduling.queryShortSchedule.ymd,
				    												'vo.tsEntity.schedulingType':'TFR',
				    												'vo.tsEntity.schedulingDepartCode':scheduling.queryShortSchedule.schedulingDepartCode
				    												},
				    										success : function(response) {
				    											myMask.hide();
				    											var result = Ext.decode(response.responseText);
				    											//打开新的标签进行操作
				    						            		addTab('T_scheduling-shortScheduleModifyIndex',//对应打开的目标页面js的onReady里定义的renderTo
				    						            				scheduling.queryShortSchedule.i18n('foss.queryShortSchedule.grid.TruckSchedulingTask.designe.lable'),//'制定短途排班',//打开的Tab页的标题
				    						            				scheduling.realPath('shortScheduleModifyIndex.action')
				    						            				+ '?ymd="' + scheduling.queryShortSchedule.ymd + '"'
				    						            				+ '&schedulingDepartCode="' + scheduling.queryShortSchedule.schedulingDepartCode + '"'
				    						            				+ '&actionType="' + scheduling.queryShortSchedule.actionType + '"'
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
				    								}else if(optional=='no'){
				    									//打开新的标签进行操作
				    				            		addTab('T_scheduling-shortScheduleModifyIndex',//对应打开的目标页面js的onReady里定义的renderTo
				    				            				scheduling.queryShortSchedule.i18n('foss.queryShortSchedule.grid.TruckSchedulingTask.designe.lable'),//'制定短途排班',//打开的Tab页的标题
				    				            				scheduling.realPath('shortScheduleModifyIndex.action')
				    				            				+ '?ymd="' + scheduling.queryShortSchedule.ymd + '"'
				    				            				+ '&schedulingDepartCode="' + scheduling.queryShortSchedule.schedulingDepartCode + '"'
				    				            				+ '&actionType="' + scheduling.queryShortSchedule.actionType + '"'
				    				            		);
				    									 	//关闭本窗口
				    									modifyWindow.close();
				    								}else if(optional =='cancel'){
				    									//关闭本窗口
				    									//modifyWindow.close();
				    								}
													});
											}else{
												//打开新的标签进行操作
							            		addTab('T_scheduling-shortScheduleModifyIndex',//对应打开的目标页面js的onReady里定义的renderTo
							            				scheduling.queryShortSchedule.i18n('foss.queryShortSchedule.grid.TruckSchedulingTask.designe.lable'),//'制定短途排班',//打开的Tab页的标题
							            				scheduling.realPath('shortScheduleModifyIndex.action')
							            				+ '?ymd="' + scheduling.queryShortSchedule.ymd + '"'
							            				+ '&schedulingDepartCode="' + scheduling.queryShortSchedule.schedulingDepartCode + '"'
							            				+ '&actionType="' + scheduling.queryShortSchedule.actionType + '"'
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
				            		
				            		//打开新的标签进行操作
				            		addTab('T_scheduling-shortScheduleModifyIndex',//对应打开的目标页面js的onReady里定义的renderTo
				            				scheduling.queryShortSchedule.i18n('foss.queryShortSchedule.grid.TruckSchedulingTask.designe.lable'),//'制定短途排班',//打开的Tab页的标题
				            				scheduling.realPath('shortScheduleModifyIndex.action')
				            				+ '?ymd="' + scheduling.queryShortSchedule.ymd + '"'
				            				+ '&schedulingDepartCode="' + scheduling.queryShortSchedule.schedulingDepartCode + '"'
				            				+ '&actionType="' + scheduling.queryShortSchedule.actionType + '"'
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

//初始化排班表单
Ext.define('Foss.queryShortSchedule.form.InitShortSchedule',{
		extend: 'Ext.form.Panel',	
		id:'Foss_queryShortSchedule_form_InitShortSchedule_ID',
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
					fieldLabel: scheduling.queryShortSchedule.i18n('foss.queryShortSchedule.form.ImportShortSchedule.schedulingDepartCode.lable'),//'车队小组',
					name: 'schedulingDepartCode',
					allowBlank:false,
					readOnly: scheduling.queryShortSchedule.isPermission('scheduling/schedulingDepartCode')?false:true,//TODO
					columnWidth:.5,
					orgCode: scheduling.queryShortSchedule.topFleetOrgCode
				},{
					xtype: 'datefield',
					fieldLabel: scheduling.queryShortSchedule.i18n('foss.queryShortSchedule.form.ImportShortSchedule.yearMonth.lable'),//'排班年月',
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
						text: scheduling.queryShortSchedule.i18n('foss.queryShortSchedule.form.ImportShortSchedule.button.cancel.lable'),//'取消',
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
						text: scheduling.queryShortSchedule.i18n('foss.queryShortSchedule.form.ModifyShortSchedule.button.ok.lable'),//'确认',
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
							scheduling.queryShortSchedule.driverOrgName=bform.findField('schedulingDepartCode').rawValue;
				            if(bform.isValid()){
				                var actionUrl=scheduling.realPath('initDeaprtDriverScheduling.action');
								var queryParams={
													'vo.tsEntity.schedulingDepartCode':schedulingDepartCode,// 排班部门
													'vo.tsEntity.yearMonth':yearMonth,//排班年月
													'vo.tsEntity.schedulingType':'TFR'//排班类型
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
										var searchForm =scheduling.queryShortSchedule.searchForm;
										//将新增的条件给查询条件
										if(!Ext.isEmpty(searchForm)){
											searchForm.getForm().findField('vo.simDto.schedulingDepartCode').setCombValue(bform.findField('schedulingDepartCode').rawValue,schedulingDepartCode);														
											form.up('window').close();
										}
										
										//准备跳转到修改界面，进行调整
										//先判断，如有则先关闭
						            	if(Ext.getCmp('T_scheduling-shortScheduleModifyIndex')!=null){
						            		removeTab('T_scheduling-shortScheduleModifyIndex');
						            	}					            	
						            	//打开新的标签进行操作
						            	addTab('T_scheduling-shortScheduleModifyIndex',//对应打开的目标页面js的onReady里定义的renderTo
											scheduling.queryShortSchedule.i18n('foss.queryShortSchedule.grid.TruckSchedulingTask.designe.lable'),//'制定短途排班',//打开的Tab页的标题
											scheduling.realPath('shortScheduleModifyIndex.action')
											+ '?ymd="' + yearMonth + '"'
											+ '&schedulingDepartCode="' + schedulingDepartCode + '"'
											+ '&actionType="REPORT_AND_MODIFY"'
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


//导入窗口
Ext.define('Foss.queryShortSchedule.window.ImportShortSchedule', {
	extend:'Ext.window.Window',
	id:'Foss_queryShortSchedule_window_importShortSchedule_ID',
	title: scheduling.queryShortSchedule.i18n('foss.queryShortSchedule.window.ImportShortSchedule.title.lable'),//'短途排班导入',
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
	items:[Ext.create('Foss.queryShortSchedule.form.ImportShortSchedule')]
});


//弹出修改、报表式查看窗口
Ext.define('Foss.queryShortSchedule.window.ModifyShortSchedule', {
	extend:'Ext.window.Window',
	id:'Foss_queryShortSchedule_window_modifyShortSchedule_ID',
	title: scheduling.queryShortSchedule.i18n('foss.queryShortSchedule.window.ModifyShortSchedule.title.lable'),//'修改短途排班',
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
	items:[Ext.getCmp('Foss_queryShortSchedule_form_modifyShortSchedule_ID')==null?Ext.create('Foss.queryShortSchedule.form.ModifyShortSchedule'):Ext.getCmp('Foss_queryShortSchedule_form_modifyShortSchedule_ID')]
});

//初始化排班窗口
Ext.define('Foss.queryShortSchedule.window.InitShortSchedule', {
	extend:'Ext.window.Window',
	id:'Foss_queryShortSchedule_window_InitShortSchedule_ID',
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
	items:[Ext.getCmp('Foss_queryShortSchedule_form_InitShortSchedule_ID')==null?Ext.create('Foss.queryShortSchedule.form.InitShortSchedule'):Ext.getCmp('Foss_queryShortSchedule_form_InitShortSchedule_ID')]
});

//入口
Ext.onReady(function() {	
	//查询表单
	scheduling.queryShortSchedule.searchForm = Ext.create('Foss.queryShortSchedule.form.TruckSchedulingSearch');
	var searchForm = scheduling.queryShortSchedule.searchForm;
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
	
	//查询 结果列表
	var rsGrid = Ext.create('Foss.queryShortSchedule.grid.TruckSchedulingTask',{
		tbar:[{
					xtype:'hiddenfield',
					flex:10
					},{
						xtype: 'button', 
						text: '初始化排班',
						tooltip:'初始化车队所属司机的排班信息（如果已经有数据,则跳过而不会覆盖原有数据）',
						handler: function() {
							//弹出窗口，供用户选择年月
							var initWindow = Ext.getCmp('Foss_queryShortSchedule_window_InitShortSchedule_ID');
							if(initWindow == null){
								initWindow=Ext.create('Foss.queryShortSchedule.window.InitShortSchedule');
							}
							//车队小组赋值
							var tempForm = Ext.getCmp('Foss_queryShortSchedule_form_InitShortSchedule_ID');
							var field =searchForm.getForm().findField('vo.simDto.schedulingDepartCode');
							tempForm.getForm().findField('schedulingDepartCode').setCombValue(field.rawValue,field.lastValue);
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
						text: scheduling.queryShortSchedule.i18n('foss.queryShortSchedule.window.ModifyShortSchedule.title.lable'),//'修改短途排班',
						handler: function() {
							//弹出窗口，供用户选择年月
							var modifyWindow = Ext.getCmp('Foss_queryShortSchedule_window_modifyShortSchedule_ID');
							if(modifyWindow == null){
								modifyWindow=Ext.create('Foss.queryShortSchedule.window.ModifyShortSchedule');
							}
							//车队小组赋值
							var tempForm = Ext.getCmp('Foss_queryShortSchedule_form_modifyShortSchedule_ID');
							var records = scheduling.queryShortSchedule.rsGrid.getSelectionModel().getSelection( );
							if(records.length>=1){
								var rowRecord=records[0].data;
								tempForm.getForm().findField('schedulingDepartCode').setCombValue(rowRecord.driverOrgName,rowRecord.driverOrgCode);
								if(Ext.Array.indexOf(currentUserDepts,'1')>-1){
									tempForm.getForm().findField('schedulingDepartCode').setReadOnly(false);
								}
								var myDate= new Date(Date.parse(rowRecord.schedulingDate.replace(/-/g, "/")));
								//排班年月
								scheduling.queryShortSchedule.copyYearMonth=Ext.Date.format(myDate, 'Y-m');
								tempForm.getForm().findField('yearMonth').setRawValue(Ext.Date.format(myDate, 'Y-m'));
								modifyWindow.setActionType('REPORT_AND_MODIFY');
								modifyWindow.setTitle(scheduling.queryShortSchedule.i18n('foss.queryShortSchedule.window.ModifyShortSchedule.title.lable'));
								modifyWindow.center().show();							
								
							}else{
								
								var field =searchForm.getForm().findField('vo.simDto.schedulingDepartCode');
								tempForm.getForm().findField('schedulingDepartCode').setCombValue(field.rawValue,field.lastValue);
								if(Ext.Array.indexOf(currentUserDepts,'1')>-1){
									tempForm.getForm().findField('schedulingDepartCode').setReadOnly(false);
								}
								//排班年月
								tempForm.getForm().findField('yearMonth').setRawValue(Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()), 'Y-m'));
								modifyWindow.setActionType('REPORT_AND_MODIFY');
								modifyWindow.setTitle(scheduling.queryShortSchedule.i18n('foss.queryShortSchedule.window.ModifyShortSchedule.title.lable'));
								modifyWindow.center().show();							
							}
							
						}
					},{
						xtype: 'button', 
						text: scheduling.queryShortSchedule.i18n('foss.queryShortSchedule.grid.TruckSchedulingTask.button.import.lable'),//'导入排班',
						handler: function() {
							//弹出窗口，供用户选择年月
							var importWindow = Ext.getCmp('Foss_queryShortSchedule_window_importShortSchedule_ID');
							if(importWindow == null){
								importWindow=Ext.create('Foss.queryShortSchedule.window.ImportShortSchedule');								
							}
							var field =searchForm.getForm().findField('vo.simDto.schedulingDepartCode')
							var key=field.lastValue;
							var value=field.rawValue;
							importWindow.down('form').getForm().findField('vo.tsEntity.schedulingDepartCode').setCombValue(value,key);
							if(Ext.Array.indexOf(currentUserDepts,'1')>-1){
								importWindow.down('form').getForm().findField('vo.tsEntity.schedulingDepartCode').setReadOnly(false);
							}
							importWindow.center().show();
						}
					}
					,{
						xtype: 'button', 
						text: scheduling.queryShortSchedule.i18n('foss.queryShortSchedule.grid.TruckSchedulingTask.button.report.lable'),//'报表式查看',
						handler: function() {
							//弹出窗口，供用户选择年月
							var modifyWindow = Ext.getCmp('Foss_queryShortSchedule_window_modifyShortSchedule_ID');
							if(modifyWindow == null){
								modifyWindow=Ext.create('Foss.queryShortSchedule.window.ModifyShortSchedule');
							}
							//车队小组赋值
							var tempForm = Ext.getCmp('Foss_queryShortSchedule_form_modifyShortSchedule_ID');
							//部门
							var field =searchForm.getForm().findField('vo.simDto.schedulingDepartCode');
							tempForm.getForm().findField('schedulingDepartCode').setCombValue(field.rawValue,field.lastValue);
							if(Ext.Array.indexOf(currentUserDepts,'1')>-1){
								tempForm.getForm().findField('schedulingDepartCode').setReadOnly(false);
							}
							//排班年月
							tempForm.getForm().findField('yearMonth').setRawValue(Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()), 'Y-m'));
							modifyWindow.setTitle(scheduling.queryShortSchedule.i18n('foss.queryShortSchedule.grid.TruckSchedulingTask.button.report.lable'));
							modifyWindow.setActionType('REPORT_AND_READ');
						    modifyWindow.center().show();
						}
					}
					,{
						xtype: 'button', 
						text: scheduling.queryShortSchedule.i18n('foss.queryShortSchedule.grid.TruckSchedulingTask.button.delete.lable'),//'删除',
						handler: function() {
							if(scheduling.queryShortSchedule.rsGrid != null){
								var records = scheduling.queryShortSchedule.rsGrid.getSelectionModel().getSelection( );
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
									Ext.Msg.confirm(scheduling.queryShortSchedule.i18n('foss.scheduling.ShortSchedule.tip.title'),
												    scheduling.queryShortSchedule.i18n('foss.scheduling.ShortSchedule.tip.deleteConfirm'), function(btn){
									    if (btn == 'yes'){
									       var myMask = new Ext.LoadMask(Ext.getBody(),  {msg:scheduling.queryShortSchedule.i18n('foss.scheduling.ShortSchedule.tip.deleting')});
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
														scheduling.queryShortSchedule.pagingBar.moveFirst();
														myMask.hide();
													},
													exception : function(response) {
														var json = Ext.decode(response.responseText);
														Ext.MessageBox.alert(scheduling.queryShortSchedule.i18n('foss.scheduling.ShortSchedule.tip.title'),json.message);
														myMask.hide();
													}		
													});
									    }
									});									
								}else{
									Ext.MessageBox.alert(scheduling.queryShortSchedule.i18n('foss.scheduling.ShortSchedule.tip.title'),
									scheduling.queryShortSchedule.i18n('foss.scheduling.ShortSchedule.tip.chooseDelete'));//'请选择需要删除的排班任务'
								}
							}
						}
					},{
						xtype: 'button', 
						text: scheduling.queryShortSchedule.i18n('foss.queryShortSchedule.grid.TruckSchedulingTask.button.download.lable'),//'模板下载',
						handler: function() {
							downloadShortExcelTemplatPath();					
						}
					}
				]
	});
	scheduling.queryShortSchedule.rsGrid=rsGrid;
	
	 //显示于主页面
	 Ext.create('Ext.panel.Panel',{
	 	id: 'T_scheduling-shortScheduleIndex_content',
		cls:"panelContentNToolbar",
		bodyCls:'panelContent-body',
		layout:'auto',
		margin:'0 0 0 0',
		items : [searchForm,rsGrid],
		renderTo: 'T_scheduling-shortScheduleIndex-body'
	});
});