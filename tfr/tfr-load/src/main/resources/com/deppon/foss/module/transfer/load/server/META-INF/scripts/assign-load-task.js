/**-----------------------------------------------BillForm------------------------------**/
Ext.define('Foss.Load.DeliverBillForm',{
	extend: 'Ext.form.Panel',
	layout: 'column',
	frame: false,
	border: false,
	title : load.assignloadtask.i18n('foss.load.assignLoadTask.queryDeliverBill'),
	defaults: {
		margin: '2 10 3 5',
		labelWidth: 90
	},
	items: [{
		xtype: 'textfield',
		labelWidth: 90,
		fieldLabel: load.assignloadtask.i18n('foss.load.assignLoadTask.deliverBillNo'),//派送单号
		name: 'billNo',
		columnWidth:.23
	},{
		xtype: 'commontruckselector',
		fieldLabel: load.assignloadtask.i18n('foss.load.assignLoadTask.vehicleNo'),//车牌号
		labelWidth: 60,
		name: 'vehicleNo',
		columnWidth:.20
	},{
		xtype: 'datetimefield_date97',
		editable : false,
		fieldLabel: load.assignloadtask.i18n('foss.load.assignLoadTask.createTime'),//提交时间
		value:Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()
			,00,00,00),'Y-m-d H:i:s'),
		labelWidth: 80,
		columnWidth: .25,
		name: 'createTimeBegin',
		id:'createTimeBegin',
		time : true,
		allowBlank:false,
		dateConfig: {
			el : 'createTimeBegin-inputEl'
		}
	},{
		xtype: 'datetimefield_date97',
		editable : false,
		labelWidth:30,
		fieldLabel: load.assignloadtask.i18n('foss.load.assignLoadTask.to'),//至
		allowBlank:false,
		value:Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()
				,23,59,59),'Y-m-d H:i:s'),
		columnWidth: .20,
		name: 'createTimeEnd',
		id:'createTimeEnd',
		time : true,
		dateConfig: {
			el : 'createTimeEnd-inputEl'
		}
	},{
		border: 1,
		xtype:'container',
		columnWidth:1,
		defaultType:'button',
		layout:'column',
		items:[{
			text:load.assignloadtask.i18n('foss.load.assignLoadTask.reset'),//重置
			columnWidth:.08,
			handler:function(){
				this.up('form').getForm().findField('billNo').reset();
				this.up('form').getForm().findField('vehicleNo').reset();
				this.up('form').getForm().findField('createTimeBegin').reset();
				this.up('form').getForm().findField('createTimeEnd').reset();
			}
		},{
			xtype: 'container',
			border : false,
			columnWidth:.7,
			html: '&nbsp;'
		},{
			text:load.assignloadtask.i18n('foss.load.assignLoadTask.query'),//查询
			cls:'yellow_button',
			disabled : !load.assignloadtask.isPermission('load/queryDeliverBillButton'),
			hidden : !load.assignloadtask.isPermission('load/queryDeliverBillButton'),
			columnWidth:.08,
			handler:function(){
				if(this.up('form').getForm().isValid()){
					/*Ext.getCmp('Foss_Load_DeliverBillGrid_Id').store.load({
						callback : function(records, operation, success) {
							if (success == false) {
								var errorMessage = operation.request.proxy.reader.jsonData.message;
								Ext.Msg.alert(load.assignloadtask.i18n('foss.load.assignLoadTask.prompt'),errorMessage);
								Ext.getCmp('Foss_Load_DeliverBillGrid_Id').store.removeAll();
							}
						}
					});*/
					load.assignloadtask.deliverPagingBar.moveFirst();
				}else{
					//Ext.Msg.alert(load.assignloadtask.i18n('foss.load.assignLoadTask.prompt'),load.assignloadtask.i18n('foss.load.assignLoadTask.conditionUncomplete'));
					Ext.ux.Toast.msg(load.assignloadtask.i18n('foss.load.assignLoadTask.prompt'), load.assignloadtask.i18n('foss.load.assignLoadTask.conditionUncomplete'), 'error', 3000);
				}	
			}
			}]
	}]
});
/**-----------------------------------------------BillModel------------------------------**/
Ext.define('Foss.Load.DeliverBillModel',{
	extend:'Ext.data.Model',
	fields:[
	       {name:'billNo',type:'string'},
	       {name:'vehicleNo',type:'string'},
	       {name:'driver',type:'string'},
	       {name:'wayBillQtyTotal',type:'int'},
	       {name:'fastWayBillQtyTotal',type:'int'},
	       {name:'goodsQtyTotal',type:'int'},
	       {name:'volumeTotal',type:'float'},
	       {name:'weightTotal',type:'float'},
	       {name:'arriveFeeTotal',type:'float'},
	       {name:'createTime',type:'string'}
	       ]
});
/**-----------------------------------------------BillStore------------------------------**/
Ext.define('Foss.Load.DeliverBillStore',{
	extend:'Ext.data.Store',
	model:'Foss.Load.DeliverBillModel',
	pageSize: 6,
	proxy : {
		type : 'ajax',
		url : load.realPath('queryDeliverBill.action'),//'../load/queryDeliverBill.action',
		actionMethods : 'post',
		reader : {
			type : 'json',
			root : 'vo.bills',
			totalProperty : 'totalCount',
			successProperty: 'success'
		}
	}
});
/**-----------------------------------------------BillGrid------------------------------**/
Ext.define('Foss.Load.DeliverBillGrid',{
	extend: 'Ext.grid.Panel',
	emptyText : '查询结果为空',
	selType:'rowmodel',
	autoScroll:true,
	stripeRows : true, 
	bodyCls: 'autoHeight',
	height:200,
	frame: false,
	columns:[
		{text: load.assignloadtask.i18n('foss.load.assignLoadTask.deliverBillNo'),dataIndex : 'billNo', flex: 1.4},//派送单号
		{text: load.assignloadtask.i18n('foss.load.assignLoadTask.vehicleNo'),dataIndex : 'vehicleNo', flex: 1.2},//车牌号
		{text: load.assignloadtask.i18n('foss.load.assignLoadTask.driver'),dataIndex : 'driver', flex: 1.0},//司机
		{text: load.assignloadtask.i18n('foss.load.assignLoadTask.wayBillQtyTotal'),dataIndex : 'wayBillQtyTotal', flex: 1.0},//总票数
		{text: load.assignloadtask.i18n('foss.load.assignLoadTask.fastWayBillQtyTotal'),dataIndex : 'fastWayBillQtyTotal', flex: 1.0},//卡货总票数
		{text: load.assignloadtask.i18n('foss.load.assignLoadTask.goodsQtyTotal'),dataIndex : 'goodsQtyTotal', flex: 1.0},//总件数
		{text: load.assignloadtask.i18n('foss.load.assignLoadTask.volumeTotal'),dataIndex : 'volumeTotal', flex: 1.2},//总体积(方)
		{text: load.assignloadtask.i18n('foss.load.assignLoadTask.weightTotal'),dataIndex : 'weightTotal', flex: 1.4},//总重量(公斤)
		{text: load.assignloadtask.i18n('foss.load.assignLoadTask.arriveFeeTotal'),dataIndex : 'arriveFeeTotal', flex: 1.2},//到付总金额
		{text: load.assignloadtask.i18n('foss.load.assignLoadTask.createTime'),dataIndex : 'createTime', flex: 1.8}//制单时间
	],
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.Load.DeliverBillStore',{
			listeners : {
				'beforeload' : function(store, operation, eOpts){
					var values = Ext.getCmp('Foss_Load_DeliverBillForm_Id').getForm().getValues();
					Ext.apply(operation, {
						params : {
							'vo.deliverBillQCVo.vehicleNo':values.vehicleNo,
							'vo.deliverBillQCVo.billNo':values.billNo,
							'vo.deliverBillQCVo.createTimeBegin':values.createTimeBegin,
							'vo.deliverBillQCVo.createTimeEnd':values.createTimeEnd
						}
					});	
				}
			}
		});
		me.selModel = Ext.create('Ext.selection.CheckboxModel', {
			mode: 'SINGLE',
			allowDeselect : true,
			showHeaderCheckbox : false 
		});
		me.bbar = Ext.create('Deppon.StandardPaging',{
			store:me.store
		});
		load.assignloadtask.deliverPagingBar = me.bbar;
		me.callParent([cfg]);	
	}
});
/**-----------------------------------------------BillPanel------------------------------**/
Ext.define('Foss.Load.DeliverBillMainPanel', {
	extend: 'Ext.panel.Panel',
	frame: true,
	layout: 'auto',
	//height : 260,
	items: [
		Ext.create('Foss.Load.DeliverBillForm',
				{id: 'Foss_Load_DeliverBillForm_Id'}),
		Ext.create('Foss.Load.DeliverBillGrid',
				{id: 'Foss_Load_DeliverBillGrid_Id'})
	]
});
/**-----------------------------------------------LoaderForm------------------------------**/
Ext.define('Foss.Load.LoaderForm',{
	extend: 'Ext.form.Panel',
	layout: 'column',
	frame: false,
	border: false,
	title : load.assignloadtask.i18n('foss.load.assignLoadTask.queryLoader'),//查询理货员
	defaults: {
		margin: '2 10 3 5',
		labelWidth: 90
	},
	items: [{
		xtype: 'commonemployeeselector',
		//xtype: 'textfield',
		labelWidth: 70,
		fieldLabel: load.assignloadtask.i18n('foss.load.assignLoadTask.loader'),//理货员
		name: 'loader',
		columnWidth:.20
	},{
		xtype: 'dynamicorgcombselector',
		//xtype: 'textfield',
		fieldLabel: load.assignloadtask.i18n('foss.load.assignLoadTask.org'),//部门
		labelWidth: 40,
		name: 'org',
		columnWidth:.18
	},{
		xtype: 'commonpositionselector',
		fieldLabel: load.assignloadtask.i18n('foss.load.assignLoadTask.title'),//职位
		labelWidth: 40,
		name: 'title',
		columnWidth:.18
	}
	/*{
		xtype: 'textfield',
		fieldLabel: load.assignloadtask.i18n('foss.load.assignLoadTask.title'),//职位
		labelWidth: 40,
		name: 'title',
		columnWidth:.18
	}*/,{
		xtype: 'datetimefield_date97',
		editable : false,
		fieldLabel: load.assignloadtask.i18n('foss.load.assignLoadTask.queryTime'),//查询时间
		allowBlank:false,
		value:Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),01
			,00,00,00),'Y-m-d H:i:s'),
		labelWidth: 75,
		columnWidth: .24,
		name: 'queryTimeBegin',
		id:'foss.load.assignLoadTask.queryTimeBegin',
		time : true,
		dateConfig: {
			el : 'foss.load.assignLoadTask.queryTimeBegin-inputEl'
		}
	},{
		xtype: 'datetimefield_date97',
		editable : false,
		labelWidth:30,
		fieldLabel: load.assignloadtask.i18n('foss.load.assignLoadTask.to'),//至
		allowBlank:false,
		value:Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()
				,23,59,59),'Y-m-d H:i:s'),
		columnWidth: .20,
		name: 'queryTimeEnd',
		id:'foss.load.assignLoadTask.queryTimeEnd',
		time : true,
		dateConfig: {
			el : 'foss.load.assignLoadTask.queryTimeEnd-inputEl'
		}
	},{
		border: 1,
		xtype:'container',
		columnWidth:1,
		defaultType:'button',
		layout:'column',
		items:[{
			text:load.assignloadtask.i18n('foss.load.assignLoadTask.reset'),//重置
			columnWidth:.08,
			handler:function(){
				this.up('form').getForm().findField('loader').reset();
				this.up('form').getForm().findField('org').reset();
				this.up('form').getForm().findField('title').reset();
				this.up('form').getForm().findField('queryTimeBegin').reset();
				this.up('form').getForm().findField('queryTimeEnd').reset();
			}
		},{
			xtype: 'container',
			border : false,
			columnWidth:.7,
			html: '&nbsp;'
		},{
			text:load.assignloadtask.i18n('foss.load.assignLoadTask.query'),//查询
			disabled : !load.assignloadtask.isPermission('load/queryLoaderButton'),
			hidden : !load.assignloadtask.isPermission('load/queryLoaderButton'),
			cls:'yellow_button',
			columnWidth:.08,
			handler:function(){
				if(this.up('form').getForm().isValid()){
					/*Ext.getCmp('Foss_Load_LoaderGrid_Id').store.load({
						callback : function(records, operation, success) {
							var jsonData = operation.request.proxy.reader.jsonData;
							if (success == false) {
								var errorMessage = jsonData.message;
								Ext.Msg.alert(load.assignloadtask.i18n('foss.load.assignLoadTask.prompt'),errorMessage);
								Ext.getCmp('Foss_Load_LoaderGrid_Id').store.removeAll();
							}
						}
					});*/
					load.assignloadtask.loaderPagingBar.moveFirst();
				}else{
					Ext.Msg.alert(load.assignloadtask.i18n('foss.load.assignLoadTask.prompt'),load.assignloadtask.i18n('foss.load.assignLoadTask.conditionUncomplete'));
					Ext.ux.Toast.msg(load.assignloadtask.i18n('foss.load.assignLoadTask.prompt'), load.assignloadtask.i18n('foss.load.assignLoadTask.conditionUncomplete'), 'error', 3000);
				}
			}
			}]
	}]
});
/**-----------------------------------------------LoaderModel------------------------------**/
Ext.define('Foss.Load.LoaderModel',{
	extend:'Ext.data.Model',
	fields:[
	    {name:'id',type:'string'},
	    {name: 'title',type:'string'},
		{name: 'loaderCode',type: 'string'},
		{name: 'loaderName',type: 'string'},
		{name: 'orgName',type: 'string'},
		{name: 'orgCode',type: 'string'},
		{name: 'unfinishedWeight',type: 'float'},
		{name: 'unfinishedTaskQty',type: 'int'},
		{name: 'assignedWeight',type: 'float'},
		{name: 'finishedWeight',type: 'float'},
	  ]
});
/**-----------------------------------------------LoaderStore------------------------------**/
Ext.define('Foss.Load.LoaderStore',{
	extend:'Ext.data.Store',
	model:'Foss.Load.LoaderModel',
	pageSize: 6,
	proxy : {
		type : 'ajax',
		url : load.realPath('queryLoader.action'),//'../load/queryLoader.action',
		actionMethods : 'post',
		reader : {
			type : 'json',
			root : 'vo.loaders',
			totalProperty : 'totalCount',
			successProperty: 'success'
		}
	}
});
/**-----------------------------------------------LoaderGrid------------------------------**/
Ext.define('Foss.Load.LoaderGrid',{
	extend: 'Ext.grid.Panel',
	emptyText : '查询结果为空',
	selType:'rowmodel',
	autoScroll:true,
	stripeRows : true, 
	bodyCls: 'autoHeight',
	height:200,
	frame: false,
	columns:[
		{text: load.assignloadtask.i18n('foss.load.assignLoadTask.loaderCode'),dataIndex : 'loaderCode', flex: 1.0},//理货员工号
		{text: load.assignloadtask.i18n('foss.load.assignLoadTask.loaderName'),dataIndex : 'loaderName', flex: 1.0},//姓名
		{text: load.assignloadtask.i18n('foss.load.assignLoadTask.title'),dataIndex : 'title', flex: 1.0},//职位
		{text: load.assignloadtask.i18n('foss.load.assignLoadTask.org'),dataIndex : 'orgName', flex: 1.2},//部门
		{
			text: load.assignloadtask.i18n('foss.load.assignLoadTask.unfinishedWeightTotal'),
			renderer: function(value){
				if(value){
					return value;
				}else{
					return 0;
				}
			},
			dataIndex : 'unfinishedWeight',
			flex: 1.6
		},//当前未完成货量(公斤)
		{
			text: load.assignloadtask.i18n('foss.load.assignLoadTask.unfinisherTaskQty'),
			renderer: function(value){
				if(value){
					return value;
				}else{
					return 0;
				}
			},
			dataIndex : 'unfinishedTaskQty', 
			flex: 1.6
		},//当前未完成任务数
		{
			text: load.assignloadtask.i18n('foss.load.assignLoadTask.assignedWeigtTotal'),
			renderer: function(value){
				if(value){
					return value;
				}else{
					return 0;
				}
			},
			dataIndex : 'assignedWeight', 
			flex: 1.4
		},//已分配货量(公斤)
		{
			text: load.assignloadtask.i18n('foss.load.assignLoadTask.finishedWeightTotal'),
			renderer: function(value){
				if(value){
					return value;
				}else{
					return 0;
				}
			},
			dataIndex : 'finishedWeightTotal', 
			flex: 1.4
		}//已完成货量(公斤)
	],
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.Load.LoaderStore',{
			listeners : {
				'beforeload' : function(store, operation, eOpts){
					var values = Ext.getCmp('Foss_Load_LoaderForm_Id').getForm().getValues();
					Ext.apply(operation, {
						params : {
							'vo.loaderQCVo.loader':values.loader,
							'vo.loaderQCVo.org':values.org,
							'vo.loaderQCVo.title':values.title,
							'vo.loaderQCVo.queryTimeBegin':values.queryTimeBegin,
							'vo.loaderQCVo.queryTimeEnd':values.queryTimeEnd
						}
					});	
				}
			}
		});
		me.selModel = Ext.create('Ext.selection.CheckboxModel', {
			mode: 'SINGLE',
			allowDeselect : true,
			showHeaderCheckbox : false 
		});
		me.bbar = Ext.create('Deppon.StandardPaging',{
			store:me.store
		});
		load.assignloadtask.loaderPagingBar = me.bbar;
		me.callParent([cfg]);	
	}
});
/**-----------------------------------------------LoaderPanel------------------------------**/
Ext.define('Foss.Load.LoaderMainPanel', {
	extend: 'Ext.panel.Panel',
	frame: true,
	layout: 'auto',
	//height : 260,
	items: [
		Ext.create('Foss.Load.LoaderForm',
				{id: 'Foss_Load_LoaderForm_Id'}),
		Ext.create('Foss.Load.LoaderGrid',
				{id: 'Foss_Load_LoaderGrid_Id'})
	]
});
/**-----------------------------------------------AssignPanel------------------------------**/
Ext.define('Foss.Load.AssignPanel',{
	extend: 'Ext.container.Container',
	frame: true,
	items: [Ext.create('Foss.Load.DeliverBillMainPanel'),
	        Ext.create('Foss.Load.LoaderMainPanel'),
	        {
	    		border: 1,
	    		xtype:'container',
	    		columnWidth:1,
	    		defaultType:'button',
	    		layout:'column',
	    		items:[{
	    			text:load.assignloadtask.i18n('foss.load.assignLoadTask.clear'),//清除
	    			columnWidth:.08,
	    			handler:function(){
	    				Ext.getCmp('Foss_Load_DeliverBillGrid_Id').getSelectionModel().deselectAll();
	    				Ext.getCmp('Foss_Load_LoaderGrid_Id').getSelectionModel().deselectAll();
	    			}
	    		},{
	    			xtype: 'container',
	    			border : false,
	    			columnWidth:.7,
	    			html: '&nbsp;'
	    		},{
	    			text:load.assignloadtask.i18n('foss.load.assignLoadTask.assign'),//分配
	    			disabled : !load.assignloadtask.isPermission('load/assignButton'),
	    			hidden : !load.assignloadtask.isPermission('load/assignButton'),
	    			cls:'yellow_button',
	    			columnWidth:.08,
	    			handler:function(){
	    				load.assignloadtask.assignLoadTaskWindow.restore();
	    				var loaderRecord = Ext.getCmp('Foss_Load_LoaderGrid_Id').getSelectionModel().getSelection();
	    				var billRecord = Ext.getCmp('Foss_Load_DeliverBillGrid_Id').getSelectionModel().getSelection();
	    				if(loaderRecord.length != 0&&billRecord.length != 0){
	    					var windowPanel = Ext.getCmp('Foss_Load_AssignWindowForm');
	    					var windowForm = windowPanel.getForm();
	    					windowForm.findField('billNo').setValue(billRecord[0].get('billNo'));
	    					windowForm.findField('vehicleNo').setValue(billRecord[0].get('vehicleNo'));
	    					windowForm.findField('driver').setValue(billRecord[0].get('driver'));
	    					windowForm.findField('wayBillQtyTotal').setValue(billRecord[0].get('wayBillQtyTotal'));
	    					windowForm.findField('fastWayBillQtyTotal').setValue(billRecord[0].get('fastWayBillQtyTotal'));
	    					windowForm.findField('goodsQtyTotal').setValue(billRecord[0].get('goodsQtyTotal'));
	    					windowForm.findField('volumeTotal').setValue(billRecord[0].get('volumeTotal'));
	    					windowForm.findField('weightTotal').setValue(billRecord[0].get('weightTotal'));
	    					windowForm.findField('arriveFeeTotal').setValue(billRecord[0].get('arriveFeeTotal'));
	    					windowForm.findField('createTime').setValue(billRecord[0].get('createTime'));
	    					
	    					windowForm.findField('loaderCode').setValue(loaderRecord[0].get('loaderCode'));
	    					windowForm.findField('loaderName').setValue(loaderRecord[0].get('loaderName'));
	    					windowForm.findField('title').setValue(loaderRecord[0].get('title'));
	    					windowForm.findField('orgName').setValue(loaderRecord[0].get('orgName'));
	    					windowForm.findField('unfinishedWeight').setValue(loaderRecord[0].get('unfinishedWeight'));
	    					windowForm.findField('unfinishedTaskQty').setValue(loaderRecord[0].get('unfinishedTaskQty'));
	    					windowForm.findField('orgCode').setValue(loaderRecord[0].get('orgCode'));
	    					windowForm.findField('assignTime').setVisible(false);
	    					windowForm.findField('unfinishedWeight').setVisible(true);
	    					windowForm.findField('unfinishedTaskQty').setVisible(true);
	    					windowPanel.items.items[19].setVisible(true);
	    					load.assignloadtask.assignLoadTaskWindow.show();
	    				}else{
	    					if(loaderRecord.length==0){
	    						//Ext.Msg.alert(load.assignloadtask.i18n('foss.load.assignLoadTask.prompt'),load.assignloadtask.i18n('foss.load.assignLoadTask.selectLoader'));//请选择理货员
	    						Ext.ux.Toast.msg(load.assignloadtask.i18n('foss.load.assignLoadTask.prompt'), load.assignloadtask.i18n('foss.load.assignLoadTask.selectLoader'), 'error', 3000);
	    					}else{
	    						//Ext.Msg.alert(load.assignloadtask.i18n('foss.load.assignLoadTask.prompt'),load.assignloadtask.i18n('foss.load.assignLoadTask.selectDeliverBill'));//请选择派送单
	    						Ext.ux.Toast.msg(load.assignloadtask.i18n('foss.load.assignLoadTask.prompt'), load.assignloadtask.i18n('foss.load.assignLoadTask.selectDeliverBill'), 'error', 3000);
	    					}
	    					
	    				}
	    				
	    			}
	    			}]
	    	}
	]
	
});
/**---------------------------------------------AssignWindow------------------------------**/
/**-----------------------------------------------WindowForm------------------------------**/
Ext.define('Foss.Load.AssignWindowForm',{
	extend: 'Ext.form.Panel',
	layout: 'column',
	frame: false,
	border: false,
	defaults: {
		margin:'5 10 10 5',
		labelWidth:85,
	},
	items: [{
		xtype: 'displayfield',
		name: 'billNo',
		allowBlank: true,
		columnWidth :.48,
		fieldLabel: load.assignloadtask.i18n('foss.load.assignLoadTask.deliverBillNo')//派送单号
	},{
		xtype: 'displayfield',
		name: 'vehicleNo',
		allowBlank: true,
		columnWidth :.48,
		fieldLabel: load.assignloadtask.i18n('foss.load.assignLoadTask.vehicleNo')//车牌号
	},{
		xtype: 'displayfield',
		name: 'driver',
		allowBlank: true,
		columnWidth :.48,
		fieldLabel: load.assignloadtask.i18n('foss.load.assignLoadTask.driver')//司机
	},{
		xtype: 'displayfield',
		name: 'wayBillQtyTotal',
		allowBlank: true,
		columnWidth :.48,
		fieldLabel: load.assignloadtask.i18n('foss.load.assignLoadTask.wayBillQtyTotal')//总票数
	},{
		xtype: 'displayfield',
		name: 'fastWayBillQtyTotal',
		allowBlank: true,
		columnWidth :.48,
		fieldLabel: load.assignloadtask.i18n('foss.load.assignLoadTask.fastWayBillQtyTotal')//卡货总票数
	},{
		xtype: 'displayfield',
		name: 'goodsQtyTotal',
		allowBlank: true,
		columnWidth :.48,
		fieldLabel: load.assignloadtask.i18n('foss.load.assignLoadTask.goodsQtyTotal')//总件数
	},{
		xtype: 'displayfield',
		name: 'volumeTotal',
		allowBlank: true,
		columnWidth :.48,
		fieldLabel: load.assignloadtask.i18n('foss.load.assignLoadTask.volumeTotal')//总体积
	},{
		xtype: 'displayfield',
		name: 'weightTotal',
		allowBlank: true,
		columnWidth :.48,
		fieldLabel: load.assignloadtask.i18n('foss.load.assignLoadTask.weightTotal')//总重量(公斤)
	},{
		xtype: 'displayfield',
		name: 'arriveFeeTotal',
		allowBlank: true,
		columnWidth :.48,
		fieldLabel: load.assignloadtask.i18n('foss.load.assignLoadTask.arriveFeeTotal')//到付总金额
	},{
		xtype: 'displayfield',
		name: 'createTime',
		allowBlank: true,
		columnWidth :.52,
		fieldLabel: load.assignloadtask.i18n('foss.load.assignLoadTask.createTime')//制单时间
	},{
		margin:'15 0 15 0'
	},{
		xtype: 'displayfield',
		name: 'loaderCode',
		allowBlank: true,
		columnWidth :.48,
		fieldLabel: load.assignloadtask.i18n('foss.load.assignLoadTask.loaderCode')//理货员工号
	},{
		xtype: 'displayfield',
		name: 'loaderName',
		allowBlank: true,
		columnWidth :.48,
		fieldLabel: load.assignloadtask.i18n('foss.load.assignLoadTask.loaderName')//姓名
	},{
		xtype: 'displayfield',
		name: 'title',
		allowBlank: true,
		columnWidth :.48,
		fieldLabel: load.assignloadtask.i18n('foss.load.assignLoadTask.title')//职位
	},{
		xtype: 'displayfield',
		name: 'orgName',
		allowBlank: true,
		columnWidth :.48,
		fieldLabel: load.assignloadtask.i18n('foss.load.assignLoadTask.org')//部门
	},{
		xtype: 'displayfield',
		name: 'unfinishedWeight',
		allowBlank: true,
		columnWidth :.48,
		labelWidth:130,
		fieldLabel: load.assignloadtask.i18n('foss.load.assignLoadTask.unfinishedWeightTotal')//当前未完成货量(公斤)
	},{
		xtype: 'displayfield',
		name: 'unfinishedTaskQty',
		allowBlank: true,
		columnWidth :.48,
		labelWidth:130,
		fieldLabel: load.assignloadtask.i18n('foss.load.assignLoadTask.unfinisherTaskQty')//当前未完成任务数
	},{
		xtype: 'displayfield',
		name: 'orgCode',
		hidden : true,
		//columnWidth :.5,
		fieldLabel: load.assignloadtask.i18n('foss.load.assignLoadTask.orgCode')//部门编码
	},{
		xtype: 'displayfield',
		name: 'assignTime',
		allowBlank: true,
		hidden : true,
		columnWidth :.52,
		fieldLabel: load.assignloadtask.i18n('foss.load.assignLoadTask.assignTime')//分配时间
	},{
		border: 1,
		xtype:'container',
		columnWidth:1,
		defaultType:'button',
		layout:'column',
		items:[{
			text:load.assignloadtask.i18n('foss.load.assignLoadTask.cancel'),//取消
			columnWidth:.2,
			handler:function(){
				load.assignloadtask.assignLoadTaskWindow.hide();
			}
		},{
			xtype: 'container',
			border : false,
			columnWidth:.55,
			html: '&nbsp;'
		},{
			text:load.assignloadtask.i18n('foss.load.assignLoadTask.comfirm'),//确认
			cls:'yellow_button',
			columnWidth:.2,
			handler:function(){
				load.assignloadtask.assignLoadTaskWindow.hide();
				Ext.Ajax.request({
					url : load.realPath('assign.action'),//'../load/assign.action',
					params : {
						'vo.assignLoadTask.bill.billNo' : this.up('form').getForm().findField('billNo').getValue(),
						'vo.assignLoadTask.bill.vehicleNo' : this.up('form').getForm().findField('vehicleNo').getValue(),
						'vo.assignLoadTask.bill.driver' : this.up('form').getForm().findField('driver').getValue(),
						'vo.assignLoadTask.bill.wayBillQtyTotal' : this.up('form').getForm().findField('wayBillQtyTotal').getValue(),
						'vo.assignLoadTask.bill.fastWayBillQtyTotal' : this.up('form').getForm().findField('fastWayBillQtyTotal').getValue(),
						'vo.assignLoadTask.bill.goodsQtyTotal' : this.up('form').getForm().findField('goodsQtyTotal').getValue(),
						'vo.assignLoadTask.bill.volumeTotal' : this.up('form').getForm().findField('volumeTotal').getValue(),
						'vo.assignLoadTask.bill.weightTotal' : this.up('form').getForm().findField('weightTotal').getValue(),
						'vo.assignLoadTask.bill.arriveFeeTotal' : this.up('form').getForm().findField('arriveFeeTotal').getValue(),
						'vo.assignLoadTask.bill.createTime' : this.up('form').getForm().findField('createTime').getValue(),
    					
						'vo.assignLoadTask.loader.loaderCode' : this.up('form').getForm().findField('loaderCode').getValue(),
						'vo.assignLoadTask.loader.loaderName' : this.up('form').getForm().findField('loaderName').getValue(),
						'vo.assignLoadTask.loader.title' : this.up('form').getForm().findField('title').getValue(),
						'vo.assignLoadTask.loader.orgName' : this.up('form').getForm().findField('orgName').getValue(),
						'vo.assignLoadTask.loader.orgCode' : this.up('form').getForm().findField('orgCode').getValue()
					},
					success:function(response){
						//Ext.Msg.alert(load.assignloadtask.i18n('foss.load.assignLoadTask.prompt'),load.assignloadtask.i18n('foss.load.assignLoadTask.assignSuccess'));//分配成功
						Ext.ux.Toast.msg(load.assignloadtask.i18n('foss.load.assignLoadTask.prompt'), load.assignloadtask.i18n('foss.load.assignLoadTask.assignSuccess'), 'info', 3000);
						var billStore = Ext.getCmp('Foss_Load_DeliverBillGrid_Id').store;
						var billItem = Ext.getCmp('Foss_Load_DeliverBillGrid_Id').getSelectionModel().getSelection();
						
						var loaderItem = Ext.getCmp('Foss_Load_LoaderGrid_Id').getSelectionModel().getSelection();
						var loaderStore = Ext.getCmp('Foss_Load_LoaderGrid_Id').store;
						var loaderRecords = [];
						loaderStore.each(function(record) {
							if(record.get('loaderCode')==loaderItem[0].get('loaderCode')){
								record.data['unfinishedTaskQty'] = record.data['unfinishedTaskQty']+1;
								record.data['unfinishedWeight'] = record.data['unfinishedWeight']+billItem[0].get('weightTotal');
								var time1 = Ext.getCmp('Foss_Load_LoaderForm_Id').getForm().findField('queryTimeBegin').getValue(),
								time2 = Ext.getCmp('Foss_Load_LoaderForm_Id').getForm().findField('queryTimeEnd').getValue(),
								quertTimeBegin = new Date(time1.substring(0,4),time1.substring(5,7)-1,time1.substring(8,10),time1.substring(11,13),time1.substring(14,16),time1.substring(17,19));
								queryTimeEnd = new Date(time2.substring(0,4),time2.substring(5,7)-1,time2.substring(8,10),time2.substring(11,13),time2.substring(14,16),time2.substring(17,19));
								currentTime = new Date();
								if(Ext.Date.between(currentTime,quertTimeBegin,queryTimeEnd)){
									record.data['assignedWeight'] = record.data['assignedWeight']+billItem[0].get('weightTotal');
								}
							}
							loaderRecords.push(record);
						});
						loaderStore.loadData(loaderRecords);
						Ext.getCmp('Foss_Load_LoaderGrid_Id').getSelectionModel().deselectAll();;
						billStore.remove(billItem);
					},
					exception:function(response){
						var result = Ext.decode(response.responseText);
						//Ext.Msg.alert(load.assignloadtask.i18n('foss.load.assignLoadTask.prompt'),result.message);
						Ext.ux.Toast.msg(load.assignloadtask.i18n('foss.load.assignLoadTask.prompt'), result.message, 'error', 3000);
					}
				});
			}
			}]
		}
	]
});
load.assignloadtask.assignLoadTaskWindow = Ext.create('Ext.window.Window',{
	autoDestroy : true,
	closable : true,
	closeAction : 'hide',
	draggable : true,
	width: 500,
	height : 420,
	modal:true,
    layout: 'column',
    //id: 'windForm',
	title: load.assignloadtask.i18n('foss.load.assignLoadTask.detail'),//详细信息
	defaults: {
		margin:'5 10 10 5',
		labelWidth:85,
	},
	items:[
	       Ext.create('Foss.Load.AssignWindowForm',{id:'Foss_Load_AssignWindowForm'})
	]
});
/**-----------------------------------------------cancelModel------------------------------**/
Ext.define('Foss.Load.CancelModel',{
	extend:'Ext.data.Model',
	fields:[
	       {name:'id',type:'string'},
	       {name:'assignTime',type:'string'},
	       {name:'bill',type:'object'},
	       {name:'loader',type: 'object'}
	    ]
});
/**-----------------------------------------------cancelStore------------------------------**/
Ext.define('Foss.Load.CancelStore',{
	extend:'Ext.data.Store',
	model:'Foss.Load.CancelModel',
	pageSize: 15,
	proxy : {
		type : 'ajax',
		url : load.realPath('queryUnstartAssignLoadTask.action'),//'../load/queryUnstartAssignLoadTask.action',
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
Ext.define('Foss.Load.CancelGrid',{
	extend: 'Ext.grid.Panel',
	selType:'rowmodel',
	emptyText : '查询结果为空',
	autoScroll:true,
	stripeRows : true, 
	bodyCls: 'autoHeight',
	frame: false,
	columns:[
		{
			xtype : 'actioncolumn',
			flex: 0.3,
			text : load.assignloadtask.i18n('foss.load.assignLoadTask.operate'),//操作
			align : 'center',
			items : [ {
				tooltip : load.assignloadtask.i18n('foss.load.assignLoadTask.watch'),//查看
				iconCls : 'deppon_icons_showdetail',
				handler : function(grid, rowIndex, colIndex) {
					load.assignloadtask.assignLoadTaskWindow.restore();
    					var record = grid.store.getAt(rowIndex);
    					var windowPanel = Ext.getCmp('Foss_Load_AssignWindowForm');
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
        					windowForm.findField('createTime').setValue(record.get('bill').createTime);
    					}
    					if(record.get('loader')!=null){
    						windowForm.findField('loaderCode').setValue(record.get('loader').loaderCode);
        					windowForm.findField('loaderName').setValue(record.get('loader').loaderName);
        					windowForm.findField('title').setValue(record.get('loader').title);
        					windowForm.findField('orgName').setValue(record.get('loader').orgName);
        					windowForm.findField('orgCode').setValue(record.get('loader').orgCode);
    					}
    					windowForm.findField('assignTime').setValue(record.get('assignTime'));
    					windowForm.findField('unfinishedTaskQty').setVisible(false);
    					windowForm.findField('unfinishedWeight').setVisible(false);
    					windowForm.findField('assignTime').setVisible(true);
    					windowPanel.items.items[19].setVisible(false);
    					load.assignloadtask.assignLoadTaskWindow.show();
					}
				} ]
		},
		{
			text: load.assignloadtask.i18n('foss.load.assignLoadTask.deliverBillNo'),
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
			text: load.assignloadtask.i18n('foss.load.assignLoadTask.vehicleNo'),
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
		{text: load.assignloadtask.i18n('foss.load.assignLoadTask.driver'),
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
		{text: load.assignloadtask.i18n('foss.load.assignLoadTask.wayBillQtyTotal'),
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
			text: load.assignloadtask.i18n('foss.load.assignLoadTask.fastWayBillQtyTotal'),
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
			text: load.assignloadtask.i18n('foss.load.assignLoadTask.goodsQtyTotal'),
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
			text: load.assignloadtask.i18n('foss.load.assignLoadTask.volumeTotal'),
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
			text: load.assignloadtask.i18n('foss.load.assignLoadTask.weightTotal'),
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
			text: load.assignloadtask.i18n('foss.load.assignLoadTask.arriveFeeTotal'),
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
			text: load.assignloadtask.i18n('foss.load.assignLoadTask.loaderCode'),
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
			text: load.assignloadtask.i18n('foss.load.assignLoadTask.loaderName'),
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
			text: load.assignloadtask.i18n('foss.load.assignLoadTask.title'),
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
			text: load.assignloadtask.i18n('foss.load.assignLoadTask.assignTime'),
			dataIndex : 'assignTime', 
			flex: 1.1
		}//分配时间
	],
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.Load.CancelStore');
		me.selModel = Ext.create('Ext.selection.CheckboxModel', {
			mode: 'SINGLE',
			allowDeselect : true,
			showHeaderCheckbox : false 
		});
		me.bbar = Ext.create('Deppon.StandardPaging',{
			store:me.store
		});
		load.assignloadtask.cancelPagingBar = me.bbar;
		me.callParent([cfg]);	
	}
});
/**---------------------------------------------CancelPanel------------------------------**/
Ext.define('Foss.Load.CancelPanel',{
	extend: 'Ext.container.Container',
	frame: true,
	items: [
			{
				border: 1,
				xtype:'container',
				columnWidth:1,
				defaultType:'button',
				layout:'column',
				items:[{
					text:load.assignloadtask.i18n('foss.load.assignLoadTask.refresh'),//刷新
					columnWidth:.08,
					handler:function(){
						/*Ext.getCmp('Foss_Load_CancelGrid_Id').store.load({
							callback : function(records, operation, success) {
								if (success == false) {
									var errorMessage = operation.request.proxy.reader.jsonData.message;
									Ext.Msg.alert(load.assignloadtask.i18n('foss.load.assignLoadTask.prompt'),errorMessage);
									Ext.getCmp('Foss_Load_CancelGrid_Id').store.removeAll();
								}
							}
						});*/
						load.assignloadtask.cancelPagingBar.moveFirst();
					}
				},{
					xtype: 'container',
					border : false,
					columnWidth:.7,
					html: '&nbsp;'
				},{
					text:load.assignloadtask.i18n('foss.load.assignLoadTask.cancelAssign'),//取消分配
					cls:'yellow_button',
					columnWidth:.08,
					handler:function(){
						var assignRecord = Ext.getCmp('Foss_Load_CancelGrid_Id').getSelectionModel().getSelection();
	    				if(assignRecord.length != 0){
	    					Ext.Msg.confirm(load.assignloadtask.i18n('foss.load.assignLoadTask.prompt'), load.assignloadtask.i18n('foss.load.assignLoadTask.doComfirm'), function(btn){//是否确认取消
	    						if (btn == 'yes'){
	    							Ext.Ajax.request({
			    						url : load.realPath('cancelAssign.action'),//'../load/cancelAssign.action',
			    						params : {
			    							'vo.assignTaskId' : assignRecord[0].get('id'),
			    							'vo.assignBillNo' : assignRecord[0].get('bill').billNo
			    						},
			    						success:function(response){
			    							//Ext.Msg.alert(load.assignloadtask.i18n('foss.load.assignLoadTask.prompt'),load.assignloadtask.i18n('foss.load.assignLoadTask.docancel'));//已取消分配
			    							Ext.ux.Toast.msg(load.assignloadtask.i18n('foss.load.assignLoadTask.prompt'), load.assignloadtask.i18n('foss.load.assignLoadTask.docancel'), 'info', 3000);
			    							var cancelGrid = Ext.getCmp('Foss_Load_CancelGrid_Id'); 
			    							var cancelStore = cancelGrid.store;
			    							var cancelItem = cancelGrid.getSelectionModel().getSelection();
			    							Ext.getCmp('Foss_Load_LoaderGrid_Id').getSelectionModel().deselectAll();;
			    							cancelStore.remove(cancelItem);
			    						},
			    						exception:function(response){
			    							var result = Ext.decode(response.responseText);
			    							//Ext.Msg.alert(load.assignloadtask.i18n('foss.load.assignLoadTask.prompt'),result.message);
			    							Ext.ux.Toast.msg(load.assignloadtask.i18n('foss.load.assignLoadTask.prompt'), result.message, 'error', 3000);
			    						}
			    					});
	    						}
	    					});
	    				}else{
	    					//Ext.Msg.alert(load.assignloadtask.i18n('foss.load.assignLoadTask.prompt'),load.assignloadtask.i18n('foss.load.assignLoadTask.noSelectedRecord'));//没有选择记录
	    					Ext.ux.Toast.msg(load.assignloadtask.i18n('foss.load.assignLoadTask.prompt'), load.assignloadtask.i18n('foss.load.assignLoadTask.noSelectedRecord'), 'error', 3000);
	    				}
					}
					}]
			},
	        Ext.create('Foss.Load.CancelGrid',{id:'Foss_Load_CancelGrid_Id'})
	]
	
});
/**-----------------------------------------------TabPanel------------------------------**/
Ext.define('Foss.Load.MainTabPanel',{
	extend: 'Ext.tab.Panel',
	frame: true,
	layout: 'auto',
	height: 800,
	items: [{
		itemId: 'temporary',
		title: load.assignloadtask.i18n('foss.load.assignLoadTask.assignLoadTask'),//分配派送装车任务
		items: [  
		    {margin: '20 5 20 5'},
			Ext.create('Foss.Load.AssignPanel'),
			{
				items: [{
					margin: '10 10 20 10'
				}]
			}
		]
	},{
		itemId: 'forever',
		title: load.assignloadtask.i18n('foss.load.assignLoadTask.assignedLoadTask'),//已分配派送装车任务
		items:[
		    {margin: '20 5 20 5'},
		    Ext.create('Foss.Load.CancelPanel')
		]
	}]
});
/**-----------------------------------------------viewpanel--------------------------------------------------**/
Ext.onReady(function(){
	Ext.create('Ext.panel.Panel',{
		id: 'T_load-assignloadtaskindex_content',
		frame:false,
		style:'padding-top:10px',
		items: [Ext.create('Foss.Load.MainTabPanel')],
		renderTo: 'T_load-assignloadtaskindex-body'
	});
});