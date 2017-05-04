/**-----------------------------------------------装车状态store------------------------------**/
/*Ext.define('Foss.Load.TaskStateStore', {
	extend:'Ext.data.Store',
    fields: ['value', 'name'],
    data : [
        {"value":"UNSTART", "name":load.assignedloadtaskquery.i18n('foss.load.assignLoadTask.UNSTART')},
        {"value":"PROCESSING", "name":load.assignedloadtaskquery.i18n('foss.load.assignLoadTask.PROCESSING')},
        {"value":"FINISHED", "name":load.assignedloadtaskquery.i18n('foss.load.assignLoadTask.FINISHED')},
        {"value":"ALL", "name":load.assignedloadtaskquery.i18n('foss.load.assignLoadTask.ALL')}
    ]
});*/
/**-----------------------------------------------QuryForm------------------------------**/
Ext.define('Foss.Load.AssignForm',{
	extend: 'Ext.form.Panel',
	layout: 'column',
	frame: true,
	border: false,
	title : load.assignedloadtaskquery.i18n('foss.load.assignLoadTask.queryCondition'),//查询条件
	defaults: {
		margin: '2 10 3 5',
		labelWidth: 150
	},
	items: [{
		xtype: 'textfield',
		//labelWidth: 90,
		fieldLabel: load.assignedloadtaskquery.i18n('foss.load.assignLoadTask.deliverBillNo'),//派送单号
		name: 'billNo',
		columnWidth:.33
	},{
		xtype: 'commontruckselector',
		fieldLabel: load.assignedloadtaskquery.i18n('foss.load.assignLoadTask.vehicleNo'),//车牌号
		//labelWidth: 60,
		name: 'vehicleNo',
		columnWidth:.33
	},{
		xtype: 'commonemployeeselector',
		//xtype: 'textfield',
		fieldLabel: load.assignedloadtaskquery.i18n('foss.load.assignLoadTask.loader'),//理货员
		//labelWidth: 60,
		name: 'loader',
		columnWidth:.33
	},{
		xtype: 'datetimefield_date97',
		editable : false,
		fieldLabel: load.assignedloadtaskquery.i18n('foss.load.assignLoadTask.assignTime'),//分配时间
		value:Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()
			,00,00,00),'Y-m-d H:i:s'),
		//labelWidth: 80,
		columnWidth: .33,
		name: 'assignTimeBegin',
		id:'assignTimeBegin',
		time : true,
		allowBlank:false,
		dateConfig: {
			el : 'assignTimeBegin-inputEl'
		}
	},{
		xtype: 'datetimefield_date97',
		//labelWidth:33,
		fieldLabel: load.assignedloadtaskquery.i18n('foss.load.assignLoadTask.to'),//至
		allowBlank:false,
		editable : false,
		value:Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()
				,23,59,59),'Y-m-d H:i:s'),
		columnWidth: .33,
		name: 'assignTimeEnd',
		id:'assignTimeEnd',
		time : true,
		dateConfig: {
			el : 'assignTimeEnd-inputEl'
		}
	},FossDataDictionary.getDataDictionaryCombo('ASSIGNUNLOADTASK_TASKSTATE',{
		fieldLabel : load.assignedloadtaskquery.i18n('foss.load.assignLoadTask.taskState'),//装车状态
		allowBlank : false,
		name : 'taskState',
		value : 'ALL',
		editable : false,
		labelWidth: 150,
		columnWidth:.33
	})/*{
		xtype: 'combo',
		name: 'taskState',
		editable :false,
		fieldLabel: load.assignedloadtaskquery.i18n('foss.load.assignLoadTask.taskState'),//装车状态
		store: Ext.create('Foss.Load.TaskStateStore'),
		queryMode: 'local',
		displayField: 'name',
		valueField: 'value',
		value : 'ALL',
		columnWidth:.33
	}*/,{
		border: 1,
		xtype:'container',
		columnWidth:1,
		defaultType:'button',
		layout:'column',
		items:[{
			text:load.assignedloadtaskquery.i18n('foss.load.assignLoadTask.reset'),//重置
			columnWidth:.08,
			handler:function(){
				this.up('form').getForm().reset();
			}
		},{
			xtype: 'container',
			border : false,
			columnWidth:.84,
			html: '&nbsp;'
		},{
			text:load.assignedloadtaskquery.i18n('foss.load.assignLoadTask.query'),//查询
			disabled : !load.assignedloadtaskquery.isPermission('load/queryAssignLoadTaskByConditionButton'),
			hidden : !load.assignedloadtaskquery.isPermission('load/queryAssignLoadTaskByConditionButton'),
			cls:'yellow_button',
			columnWidth:.08,
			handler:function(){
				if(this.up('form').getForm().isValid()){
					/*//var params = this.up('form').getForm().getValues();
					Ext.getCmp('Foss_Load_AssignGrid_Id').store.load({
						params : {
							'vo.assignLoadTaskQcVo.vehicleNo':params.vehicleNo,
							'vo.assignLoadTaskQcVo.billNo':params.billNo,
							'vo.assignLoadTaskQcVo.loaderCode':params.loader,
							'vo.assignLoadTaskQcVo.taskState':params.taskState,
							'vo.assignLoadTaskQcVo.assignTimeBegin':params.assignTimeBegin,
							'vo.assignLoadTaskQcVo.assignTimeEnd':params.assignTimeEnd
						},
						callback : function(records, operation, success) {
							if (success == false) {
								var errorMessage = operation.request.proxy.reader.jsonData.message;
								Ext.Msg.alert(load.assignedloadtaskquery.i18n('foss.load.assignLoadTask.prompt'),errorMessage);
								Ext.getCmp('Foss_Load_AssignGrid_Id').store.removeAll();
							}
						}
					});*/
					load.assignedloadtaskquery.pagingBar.moveFirst();
				}else{
					Ext.ux.Toast.msg(load.assignedloadtaskquery.i18n('foss.load.assignLoadTask.prompt'), load.assignedloadtaskquery.i18n('foss.load.assignLoadTask.conditionUncomplete'), 'error', 3000);
					//Ext.Msg.alert(load.assignedloadtaskquery.i18n('foss.load.assignLoadTask.prompt'),load.assignedloadtaskquery.i18n('foss.load.assignLoadTask.conditionUncomplete'));
				}	
			}
			}]
	}]
});
/**-----------------------------------------------cancelModel------------------------------**/
Ext.define('Foss.Load.AssignModel',{
	extend:'Ext.data.Model',
	fields:[
	       {name:'id',type:'string'},
	       {name:'assignTime',type:'string'},
	       {name:'taskState',type:'string',
	    	   convert: function(value) {
					if (value == 'UNSTART') {					
						return load.assignedloadtaskquery.i18n('foss.load.assignLoadTask.UNSTART');
					} else if (value == 'PROCESSING') {					
						return load.assignedloadtaskquery.i18n('foss.load.assignLoadTask.PROCESSING');
					}else if (value == 'FINISHED') {					
						return load.assignedloadtaskquery.i18n('foss.load.assignLoadTask.FINISHED');
					}
				}
	       },
	       {name:'bill',type:'object'},
	       {name:'loader',type: 'object'}
	    ]
});
/**-----------------------------------------------cancelStore------------------------------**/
Ext.define('Foss.Load.AssignStore',{
	extend:'Ext.data.Store',
	model:'Foss.Load.AssignModel',
	pageSize: 13,
	proxy : {
		type : 'ajax',
		url : load.realPath('queryAssignLoadTaskByCondition.action'),//'../load/queryAssignLoadTaskByCondition.action',
		actionMethods : 'post',
		reader : {
			type : 'json',
			root : 'vo.assignedTasks',
			totalProperty : 'totalCount',
			successProperty: 'success'
		}
	}
});
/**-----------------------------------------------cancelGrid------------------------------**/
Ext.define('Foss.Load.AssignGrid',{
	extend: 'Ext.grid.Panel',
	emptyText : '查询结果为空',
	selType:'rowmodel',
	autoScroll:true,
	stripeRows : true, 
	bodyCls: 'autoHeight',
	frame: true,
	title:load.assignedloadtaskquery.i18n('foss.load.assignLoadTask.queryList'),//查询列表
	columns:[
		{
			xtype : 'actioncolumn',
			width : 60,
			text : load.assignedloadtaskquery.i18n('foss.load.assignLoadTask.operate'),//操作
			align : 'center',
			items : [ {
				tooltip : load.assignedloadtaskquery.i18n('foss.load.assignLoadTask.watch'),//查看
				iconCls : 'deppon_icons_showdetail',
				handler : function(grid, rowIndex, colIndex) {
					load.assignedloadtaskquery.assignLoadTaskWindow.restore();
					var record = grid.store.getAt(rowIndex);
					var windowPanel = Ext.getCmp('Foss_Load_QueryAssignWindowForm');
					var windowForm = windowPanel.getForm();
					if(record.get('bill')!=null){
						windowForm.findField('billNo').setValue(record.get('bill').billNo);
    					windowForm.findField('vehicleNo').setValue(record.get('bill').vehicleNo);
    					windowForm.findField('driver').setValue(record.get('bill').driver);
    					windowForm.findField('wayBillQtyTotal').setValue(record.get('bill').wayBillQtyTotal);
    					windowForm.findField('fastWayBillQtyTotal').setValue(record.get('bill').fastWayBillQtyTotal);
    					windowForm.findField('goodsQtyTotal').setValue(record.get('bill').goodsQtyTotal);
    					windowForm.findField('volumeTotal').setValue(record.get('bill').volumeTotal);
    					windowForm.findField('weightTotal').setValue(record.get('bill').weightTotal);
    					windowForm.findField('arriveFeeTotal').setValue(record.get('bill').arriveFeeTotal);
					}
					if(record.get('loader')!=null){
						windowForm.findField('loaderCode').setValue(record.get('loader').loaderCode);
    					windowForm.findField('loaderName').setValue(record.get('loader').loaderName);
    					windowForm.findField('title').setValue(record.get('loader').title);
    					windowForm.findField('orgName').setValue(record.get('loader').orgName);
    					windowForm.findField('orgCode').setValue(record.get('loader').orgCode);
    					var loaderCode = record.get('loader').loaderCode;
    					if(loaderCode!=null&&loaderCode!=''){
    						Ext.Ajax.request({
    							url : load.realPath('queryUnfinishedWordLoadByLoader.action'),//'../load/queryUnfinishedWordLoadByLoader.action',
    							params : {
    								'vo.loaderCode' : record.get('loader').loaderCode
    							},
    							success:function(response){
    								var result = Ext.decode(response.responseText),
    								unfinishWorkLoad = result.vo.unfinishWorkLoad;
    								if(unfinishWorkLoad!=null){
    									windowForm.findField('unfinishedWeight').setValue(unfinishWorkLoad.unfinishedWeight);
        		    					windowForm.findField('unfinishedTaskQty').setValue(unfinishWorkLoad.unfinishedTaskQty);
    								}
    							},
    							exception:function(response){
    							}
    						});
    					}
					}
					windowForm.findField('assignTime').setValue(record.get('assignTime'));
					windowForm.findField('taskState').setValue(record.get('taskState'));
					load.assignedloadtaskquery.assignLoadTaskWindow.show();
					}
				},{
					tooltip : '手工装车完成(无PDA)',//查看
					iconCls : 'deppon_icons_affirm',
					handler : function(grid, rowIndex, colIndex) {
						var deliverBillNo = grid.store.getAt(rowIndex).get('bill').billNo,
							confirmPage = Ext.getCmp('T_load-deliverloadtaskconfirmindex_content'),
							mainPanel = Ext.getCmp('mainAreaPanel');
						if(confirmPage == null){
							addTab('T_load-deliverloadtaskconfirmindex',
							'手工确认派送装车任务',
							load.realPath('deliverloadtaskconfirmindex.action?deliverBillNo="' + deliverBillNo + '"'));
						}else{
							mainPanel.setActiveTab(mainPanel.child('#'+'T_load-deliverloadtaskconfirmindex'));
							load.deliverloadtaskconfirm.loadData(deliverBillNo);
						}
					}
				} ],
				renderer : function(value,metaData,record,rowIndex,colIndex,store,view){
					//只能手动完成未开始的派送装车任务
					var taskState = record.get('taskState');
					if(taskState === '未开始'){
						this.items[1].iconCls = 'deppon_icons_affirm';
					}else{
						this.items[1].iconCls = null;
					}
				}
			},
		{
			text: load.assignedloadtaskquery.i18n('foss.load.assignLoadTask.deliverBillNo'),
			dataIndex : 'bill',
			renderer: function(value){
				if(value){
					return value.billNo;
				}else{
					return 0;
				}
			},
			flex: 0.6
		},//派送单号
		{
			text: load.assignedloadtaskquery.i18n('foss.load.assignLoadTask.vehicleNo'),
			dataIndex : 'bill',
			renderer: function(value){
				if(value){
					return value.vehicleNo;
				}else{
					return 0;
				}
			},
			flex: 0.4
		},
		{text: load.assignedloadtaskquery.i18n('foss.load.assignLoadTask.driver'),
			dataIndex : 'bill',
			renderer: function(value){
				if(value){
					return value.driver;
				}else{
					return 0;
				}
			},
			flex: 0.4
		},//司机
		{text: load.assignedloadtaskquery.i18n('foss.load.assignLoadTask.taskState'),
			dataIndex : 'taskState',
			flex: 0.4
		},//装车状态
		{text: load.assignedloadtaskquery.i18n('foss.load.assignLoadTask.wayBillQtyTotal'),
			dataIndex : 'bill',
			renderer: function(value){
				if(value){
					return value.wayBillQtyTotal;
				}else{
					return 0;
				}
			},
			flex: 0.4
		},//总票数
		{
			text: load.assignedloadtaskquery.i18n('foss.load.assignLoadTask.fastWayBillQtyTotal'),
			dataIndex : 'bill',
			renderer: function(value){
				if(value){
					return value.fastWayBillQtyTotal;
				}else{
					return 0;
				}
			}, 
			flex: 0.5
		},//卡货总票数
		{
			text: load.assignedloadtaskquery.i18n('foss.load.assignLoadTask.goodsQtyTotal'),
			dataIndex : 'bill',
			renderer: function(value){
				if(value){
					return value.goodsQtyTotal;
				}else{
					return 0;
				}
			}, 
			flex: 0.4
		},//总件数
		{
			text: load.assignedloadtaskquery.i18n('foss.load.assignLoadTask.volumeTotal'),
			dataIndex : 'bill',
			renderer: function(value){
				if(value){
					return value.volumeTotal;
				}else{
					return 0;
				}
			}, 
			flex: 0.6
		},//总体积(方)
		{
			text: load.assignedloadtaskquery.i18n('foss.load.assignLoadTask.weightTotal'),
			dataIndex : 'bill',
			renderer: function(value){
				if(value){
					return value.weightTotal;
				}else{
					return 0;
				}
			},
			flex: 0.7
		},//总重量(公斤)
		{
			text: load.assignedloadtaskquery.i18n('foss.load.assignLoadTask.arriveFeeTotal'),
			dataIndex : 'bill',
			renderer: function(value){
				if(value){
					return value.arriveFeeTotal;
				}else{
					return 0;
				}
			}, 
			flex: 0.6
		},//到付总金额
		{
			text: load.assignedloadtaskquery.i18n('foss.load.assignLoadTask.loaderCode'),
			dataIndex : 'loader',
			renderer: function(value){
				if(value){
					return value.loaderCode;
				}else{
					return 0;
				}
			}, 
			flex: 0.6
		},//理货员工号
		{
			text: load.assignedloadtaskquery.i18n('foss.load.assignLoadTask.loaderName'),
			dataIndex : 'loader',
			renderer: function(value){
				if(value){
					return value.loaderName;
				}else{
					return 0;
				}
			}, 
			flex: 0.4
		},//姓名
		{
			text: load.assignedloadtaskquery.i18n('foss.load.assignLoadTask.title'),
			dataIndex : 'loader',
			renderer: function(value){
				if(value){
					return value.title;
				}else{
					return 0;
				}
			}, 
			flex: 0.4
		},//职位
		{
			text: load.assignedloadtaskquery.i18n('foss.load.assignLoadTask.assignTime'),
			dataIndex : 'assignTime', 
			flex: 1.1
		}//分配时间
	],
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.Load.AssignStore',{
			listeners : {
				'beforeload' : function(store, operation, eOpts){
					var values = Ext.getCmp('Foss_Load_AssignForm_Id').getForm().getValues();
					Ext.apply(operation, {
						params : {
							'vo.assignLoadTaskQcVo.vehicleNo':values.vehicleNo,
							'vo.assignLoadTaskQcVo.billNo':values.billNo,
							'vo.assignLoadTaskQcVo.loaderCode':values.loader,
							'vo.assignLoadTaskQcVo.taskState':values.taskState,
							'vo.assignLoadTaskQcVo.assignTimeBegin':values.assignTimeBegin,
							'vo.assignLoadTaskQcVo.assignTimeEnd':values.assignTimeEnd
						}
					});	
				}
			}
		}),
		me.bbar = Ext.create('Deppon.StandardPaging',{
			store:me.store
		}),
		load.assignedloadtaskquery.pagingBar = me.bbar,
		me.callParent([cfg]);	
	}
});
/**-----------------------------------------------WindowForm------------------------------**/
Ext.define('Foss.Load.AssignWindowForm',{
	extend: 'Ext.form.Panel',
	layout: 'column',
	frame: false,
	border: false,
	defaults: {
		margin:'5 10 10 5',
		labelWidth:85
	},
	items: [{
		xtype: 'displayfield',
		name: 'billNo',
		allowBlank: true,
		columnWidth :.48,
		fieldLabel: load.assignedloadtaskquery.i18n('foss.load.assignLoadTask.deliverBillNo')//派送单号
	},{
		xtype: 'displayfield',
		name: 'vehicleNo',
		allowBlank: true,
		columnWidth :.48,
		fieldLabel: load.assignedloadtaskquery.i18n('foss.load.assignLoadTask.vehicleNo')//车牌号
	},{
		xtype: 'displayfield',
		name: 'driver',
		allowBlank: true,
		columnWidth :.48,
		fieldLabel: load.assignedloadtaskquery.i18n('foss.load.assignLoadTask.driver')//司机
	},{
		xtype: 'displayfield',
		name: 'wayBillQtyTotal',
		allowBlank: true,
		columnWidth :.48,
		fieldLabel: load.assignedloadtaskquery.i18n('foss.load.assignLoadTask.wayBillQtyTotal')//总票数
	},{
		xtype: 'displayfield',
		name: 'fastWayBillQtyTotal',
		allowBlank: true,
		columnWidth :.48,
		fieldLabel: load.assignedloadtaskquery.i18n('foss.load.assignLoadTask.fastWayBillQtyTotal')//卡货总票数
	},{
		xtype: 'displayfield',
		name: 'goodsQtyTotal',
		allowBlank: true,
		columnWidth :.48,
		fieldLabel: load.assignedloadtaskquery.i18n('foss.load.assignLoadTask.goodsQtyTotal')//总件数
	},{
		xtype: 'displayfield',
		name: 'volumeTotal',
		allowBlank: true,
		columnWidth :.48,
		fieldLabel: load.assignedloadtaskquery.i18n('foss.load.assignLoadTask.volumeTotal')//总体积
	},{
		xtype: 'displayfield',
		name: 'weightTotal',
		allowBlank: true,
		columnWidth :.48,
		fieldLabel: load.assignedloadtaskquery.i18n('foss.load.assignLoadTask.weightTotal')//总重量(公斤)
	},{
		xtype: 'displayfield',
		name: 'arriveFeeTotal',
		allowBlank: true,
		columnWidth :.48,
		fieldLabel: load.assignedloadtaskquery.i18n('foss.load.assignLoadTask.arriveFeeTotal')//到付总金额
	},{
		xtype: 'displayfield',
		name: 'assignTime',
		allowBlank: true,
		columnWidth :.52,
		fieldLabel: load.assignedloadtaskquery.i18n('foss.load.assignLoadTask.assignTime')//分配时间
	},{
		margin:'15 0 15 0'
	},{
		xtype: 'displayfield',
		name: 'loaderCode',
		allowBlank: true,
		columnWidth :.48,
		fieldLabel: load.assignedloadtaskquery.i18n('foss.load.assignLoadTask.loaderCode')//理货员工号
	},{
		xtype: 'displayfield',
		name: 'loaderName',
		allowBlank: true,
		columnWidth :.48,
		fieldLabel: load.assignedloadtaskquery.i18n('foss.load.assignLoadTask.loaderName')//姓名
	},{
		xtype: 'displayfield',
		name: 'title',
		allowBlank: true,
		columnWidth :.48,
		fieldLabel: load.assignedloadtaskquery.i18n('foss.load.assignLoadTask.title')//职位
	},{
		xtype: 'displayfield',
		name: 'orgName',
		allowBlank: true,
		columnWidth :.48,
		fieldLabel: load.assignedloadtaskquery.i18n('foss.load.assignLoadTask.org')//部门
	},{
		xtype: 'displayfield',
		name: 'taskState',
		allowBlank: true,
		columnWidth :.48,
		fieldLabel: load.assignedloadtaskquery.i18n('foss.load.assignLoadTask.taskState')//装车状态
	},{
		xtype: 'displayfield',
		name: 'unfinishedWeight',
		allowBlank: true,
		columnWidth :.48,
		labelWidth:130,
		fieldLabel: load.assignedloadtaskquery.i18n('foss.load.assignLoadTask.unfinishedWeightTotal')//当前未完成货量(公斤)
	},{
		xtype: 'displayfield',
		name: 'unfinishedTaskQty',
		allowBlank: true,
		columnWidth :.48,
		labelWidth:130,
		fieldLabel: load.assignedloadtaskquery.i18n('foss.load.assignLoadTask.unfinisherTaskQty')//当前未完成任务数
	},{
		xtype: 'displayfield',
		name: 'orgCode',
		hidden : true,
		//columnWidth :.5,
		fieldLabel: load.assignedloadtaskquery.i18n('foss.load.assignLoadTask.orgCode')//部门编码
	}
	]
});
load.assignedloadtaskquery.assignLoadTaskWindow = Ext.create('Ext.window.Window',{
	autoDestroy : true,
	closable : true,
	closeAction : 'hide',
	draggable : true,
	width: 500,
	height : 390,
	modal:true,
    layout: 'column',
    //id: 'windForm',
	title: load.assignedloadtaskquery.i18n('foss.load.assignLoadTask.detail'),//详细信息
	defaults: {
		margin:'5 10 10 5',
		labelWidth:85
	},
	items:[
	       Ext.create('Foss.Load.AssignWindowForm',{id:'Foss_Load_QueryAssignWindowForm'})
	]
});
/**-----------------------------------------------viewpanel--------------------------------------------------**/
Ext.onReady(function(){
	Ext.create('Ext.panel.Panel',{
		id: 'T_load-assignedloadtaskqueryindex_content',
		frame:false,
		height:800,
		style:'padding-top:10px',
		items: [Ext.create('Foss.Load.AssignForm',{id:'Foss_Load_AssignForm_Id'}),Ext.create('Foss.Load.AssignGrid',{id:'Foss_Load_AssignGrid_Id'})],
		renderTo: 'T_load-assignedloadtaskqueryindex-body'
	});
});