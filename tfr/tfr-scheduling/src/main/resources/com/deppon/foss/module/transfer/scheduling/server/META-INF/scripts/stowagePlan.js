//定义
Ext.define('Foss.scheduling.stowagePlan.listModel', {
	extend : 'Ext.data.Model',
	//定义字段
	fields : [
	          {name : 'id',type : 'string'},
	          {name : 'origOrgName',type : 'string'},
	          {name : 'origOrgCode',type : 'string'},
	          {name : 'destOrgName',type : 'string'},
	          {name : 'destOrgCode',type : 'string'},
	          {name : 'createUserName',type : 'string'},
	          {name : 'createUserCode',type : 'string'},
	          {name : 'modifyUserName',type : 'string'},
	          {name : 'modifyUserCode',type : 'string'},
	          {name : 'createTime',type : 'string',
		  			convert: function(value) {
						if (value != null) {
							var date = new Date(value);						
							return Ext.Date.format(date,'Y-m-d H:i:s');
						} else {
							return value;
						}
					}},
	          {name : 'modifyTime',type : 'string',
		  			convert: function(value) {
						if (value != null) {
							var date = new Date(value);						
							return Ext.Date.format(date,'Y-m-d H:i:s');
						} else {
							return value;
						}
					}}
	         ]
});


Ext.define('Foss.scheduling.stowagePlan.selectStowagePlansListStore', {
	extend : 'Ext.data.Store',
	// 绑定一个模型
	model : 'Foss.scheduling.stowagePlan.listModel',
	pageSize:10,
	// 定义一个代理对象
	proxy : {
		type : 'ajax',
		actionMethods : 'POST',
		// 请求的url
		url : scheduling.realPath('selectStowagePlansList.action'),
		// 定义一个读取器
		reader : {
			// 以JSON的方式读取
			type : 'json',
			// 定义读取JSON数据的根对象
			root : 'vo.stowagePlansList',
			totalProperty : 'totalCount',
			successProperty : 'success'
		}
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	},
	listeners: {
		beforeload : function(store, operation, eOpts) {
			var queryPanel = scheduling.stowagePlan.queryform;
			if (queryPanel != null) {
				var queryParams = queryPanel.getValues();
				Ext.apply(operation, {
					params : {
						'vo.stowagePlansDto.origOrgName' : queryParams.origOrgName,
						'vo.stowagePlansDto.destOrgCode' : queryParams.destOrgCode
					}
				});	
			}
		}
	}
});

//查询条件-页面
Ext.define('Foss.scheduling.stowagePlan.QueryForm', {
	extend:'Ext.form.Panel',
	title: scheduling.stowagePlan.i18n('foss.queryShortSchedule.form.TruckSchedulingSearch.title.lable'),
	frame: true,
	animCollapse: true,
	defaultType: 'textfield',
	layout:'column',
	//id: 'Foss_Queryloadtask_QueryForm_Id', 
	defaults: {
		margin: '5 5 5 5',
		labelWidth: 60
	},
	items:[{
		name : 'origOrgName',
		fieldLabel : scheduling.stowagePlan
				.i18n('foss.scheduling.forecast.currentOrgCode'), // 当前部门
		columnWidth : .25,
		value : FossUserContext.getCurrentDept().name,
		allowBlank:true,
		readOnly:true
	},{
		name : 'destOrgCode',
		fieldLabel : scheduling.stowagePlan.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.inviteVehicleApplyInfo.arrivedDeptCode'), // 到达部门
		columnWidth : .25,
		xtype : 'dynamicorgcombselector',
		type : 'ORG',
		transferCenter : 'Y',
		allowBlank:true
	},{
		xtype: 'container',
		columnWidth:.50,
		html: '&nbsp;',
		height:30,
	},{
		text: scheduling.stowagePlan.i18n('foss.scheduling.forecastQuantity.reset'), //重置 按钮
		xtype:"button",
		columnWidth:.10,
		height:30,
		handler:function(){
			this.up('form').getForm().reset();
		}
	},{
		xtype: 'container',
		columnWidth:.80,
		html: '&nbsp;'
	},{
		text: scheduling.stowagePlan.i18n('foss.scheduling.forecastQuantity.query'), //查询 按钮
		xtype:"button",
		cls:'yellow_button',
		columnWidth:.10,
		height:30,
		handler:function(){
			var form = this.up('form').getForm();
			if(!form.isValid()){
				return;
			}
			scheduling.stowagePlan.pagingBar.moveFirst();
		}
	}]
});

//展示查询的结果
Ext.define('Foss.scheduling.stowagePlan.QueryResult',{
	extend:'Ext.grid.Panel',
    title:scheduling.stowagePlan.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyQueryGrid.title'),   //查询结果
    frame: true,
    bodyCls: 'autoHeight',
	cls: 'autoHeight',
	autoScroll: true,
	collapsible: true,
    animCollapse: true,
    dockedItems : [{
		xtype : 'toolbar',
		dock : 'top',
		layout : 'column',
		defaultType : 'button',
		items : [{
					text : scheduling.stowagePlan
							.i18n('foss.shortdepartureplan.form.addnewplan.button.new.lable'), // 新增
					columnWidth : .08,
					handler : function() {
						var grid = this.ownerCt.ownerCt;
						scheduling.stowagePlan.id = null;
						
						if(scheduling.stowagePlan.AddAssembleDeptWindow == null){
							scheduling.stowagePlan.AddAssembleDeptWindow = Ext.create('Foss.scheduling.stowagePlan.AddAssembleDeptWindow');
						}
						
						//清空窗口里面的数据
						scheduling.stowagePlan.addAssembleDeptGrid.getStore().removeAll();
						scheduling.stowagePlan.addAssembleDeptGrid.columns[0].show();
		             	scheduling.stowagePlan.addAssembleDeptForm.show();

		             	scheduling.stowagePlan.arrivedDeptCodeFrom.getForm().findField('arrivedDeptCodeHidden').hide();
		             	scheduling.stowagePlan.arrivedDeptCodeFrom.getForm().findField('arrivedDeptCode').show();
		             	scheduling.stowagePlan.arrivedDeptCodeFrom.getForm().findField('arrivedDeptCode').setValue(null);
		             	scheduling.stowagePlan.arrivedDeptCodeFrom.doLayout();
		             	
		             	if(scheduling.stowagePlan.AddAssembleDeptWindow.dockedItems.items.length == 2){
		             		scheduling.stowagePlan.AddAssembleDeptWindow.dockedItems.items[1].items.items[0].show();
		             		scheduling.stowagePlan.AddAssembleDeptWindow.dockedItems.items[1].items.items[1].show();
		             	}
		             	
						scheduling.stowagePlan.AddAssembleDeptWindow.show();
					}
				},{
					xtype : 'container',
					html : '&nbsp;',
					columnWidth : .5
				}]
    }],
    columns: [{
    	xtype:'actioncolumn',
		header: scheduling.stowagePlan.i18n('foss.shortdepartureplan.grid.plansearchresult.actioncolumn.lable'),   //操作
        flex : 0.3,
        items: [{
        	iconCls: 'deppon_icons_showdetail',
            tooltip: scheduling.stowagePlan.i18n('foss.shortdepartureplan.grid.plansearchresult.tooltip.lable'),   //查看
            handler: function(grid, rowIndex, colIndex) {
            	var record = grid.getStore().getAt(rowIndex);
            	scheduling.stowagePlan.id = null;
            	
            	if(scheduling.stowagePlan.AddAssembleDeptWindow == null){
					scheduling.stowagePlan.AddAssembleDeptWindow = Ext.create('Foss.scheduling.stowagePlan.AddAssembleDeptWindow');
				}
            	
            	scheduling.stowagePlan.addAssembleDeptForm.hide();
            	scheduling.stowagePlan.addAssembleDeptGrid.columns[0].hide();
            	
            	scheduling.stowagePlan.arrivedDeptCodeFrom.getForm().findField('arrivedDeptCode').hide();
            	
            	var arrivedDeptCode = scheduling.stowagePlan.arrivedDeptCodeFrom.getForm().findField('arrivedDeptCodeHidden');
            	arrivedDeptCode.setValue(record.data.destOrgName);
            	arrivedDeptCode.getStore().load({params:{'commonOrgVo.name' : record.data.destOrgName}});
            	arrivedDeptCode.show();
            	//doLayout
            	scheduling.stowagePlan.arrivedDeptCodeFrom.doLayout();

				//清空窗口里面的数据
				scheduling.stowagePlan.arrivedDeptCodeFrom.getForm().reset();
				scheduling.stowagePlan.addAssembleDeptGrid.getStore().removeAll();
             	
            	//
				var array = {vo: {stowagePlansDto :{stowagePlansId:record.data.id}}};

        		//执行查询
     			Ext.Ajax.request({
     				url : scheduling.realPath('selectStowagePlansDetail.action'),
     				jsonData :array,
     				//动态创建表单，显示任务信息
     				success : function(response) {
     					var result = Ext.decode(response.responseText);
     					
     					var store = scheduling.stowagePlan.addAssembleDeptGrid.store;
     					
     					for(var i=0;i<result.vo.stowagePlansDetailList.length;i++){
     					    
     					    var record = result.vo.stowagePlansDetailList[i];
     					    
     						var deptInfo = {
     	    						'assembleDeptCode' : record.orgCode,
     	    						'assembleDeptName' : record.orgName
     	    					};
     						
 	    					var deptInfoRecord = Ext.ModelManager.create(deptInfo,'Foss.scheduling.stowagePlan.AddAssembleDeptModel');
 	    					store.insert(store.getCount(),deptInfoRecord);
     					}
     					
     					scheduling.stowagePlan.AddAssembleDeptWindow.show();
     					
     	             	if(scheduling.stowagePlan.AddAssembleDeptWindow.dockedItems.items.length == 2){
     	             		scheduling.stowagePlan.AddAssembleDeptWindow.dockedItems.items[1].items.items[0].hide();
     	             		scheduling.stowagePlan.AddAssembleDeptWindow.dockedItems.items[1].items.items[1].hide();
     	             	}

     				},
     				exception : function(response) {
     					var json = Ext.decode(response.responseText);
     					Ext.ux.Toast.msg(scheduling.stowagePlan.i18n('foss.scheduling.ShortSchedule.tip.title'), json.message, 'error', 1000);
     				}		
     			});
            }
        },{
        	iconCls: 'deppon_icons_edit',
        	tooltip: scheduling.stowagePlan.i18n('foss.shortScheduleDesign.gird.PlanDetail.tooltip.edit.lable'),   //修改
        	 handler: function(grid, rowIndex, colIndex) {
             	var record = grid.getStore().getAt(rowIndex);
             	
        		if(scheduling.stowagePlan.AddAssembleDeptWindow == null){
 					scheduling.stowagePlan.AddAssembleDeptWindow = Ext.create('Foss.scheduling.stowagePlan.AddAssembleDeptWindow');
 				}
        		
				//清空窗口里面的数据
				scheduling.stowagePlan.addAssembleDeptGrid.getStore().removeAll();
        		
        		scheduling.stowagePlan.addAssembleDeptGrid.columns[0].show();
             	
             	scheduling.stowagePlan.addAssembleDeptForm.show();
             	
             	scheduling.stowagePlan.arrivedDeptCodeFrom.getForm().findField('arrivedDeptCode').hide();
            	var arrivedDeptCode = scheduling.stowagePlan.arrivedDeptCodeFrom.getForm().findField('arrivedDeptCodeHidden');
            	arrivedDeptCode.setValue(record.data.destOrgCode);
            	arrivedDeptCode.getStore().load({params:{'commonOrgVo.code' : record.data.destOrgCode}});
            	arrivedDeptCode.show();
             	scheduling.stowagePlan.arrivedDeptCodeFrom.doLayout();
             	//doLayout				
              	//
				var array = {vo: {stowagePlansDto :{stowagePlansId:record.data.id}}};
				scheduling.stowagePlan.modifyTime=record.data.modifyTime;
				scheduling.stowagePlan.id=record.data.id;

        		//执行查询
     			Ext.Ajax.request({
     				url : scheduling.realPath('selectStowagePlansDetail.action'),
     				jsonData :array,
     				//动态创建表单，显示任务信息
     				success : function(response) {
     					var result = Ext.decode(response.responseText);
     					
     					var store = scheduling.stowagePlan.addAssembleDeptGrid.store;
     					
     					for(var i=0;i<result.vo.stowagePlansDetailList.length;i++){
     					    
     					    var record = result.vo.stowagePlansDetailList[i];
     					    
     						var deptInfo = {
     	    						'assembleDeptCode' : record.orgCode,
     	    						'assembleDeptName' : record.orgName
     	    					};
     						
 	    					var deptInfoRecord = Ext.ModelManager.create(deptInfo,'Foss.scheduling.stowagePlan.AddAssembleDeptModel');
 	    					store.insert(store.getCount(),deptInfoRecord);
     					}
     					
     					scheduling.stowagePlan.AddAssembleDeptWindow.show();

     					if(scheduling.stowagePlan.AddAssembleDeptWindow.dockedItems.items.length == 2){
     						scheduling.stowagePlan.AddAssembleDeptWindow.dockedItems.items[1].items.items[0].show();
     						scheduling.stowagePlan.AddAssembleDeptWindow.dockedItems.items[1].items.items[1].show();
     					}
     				},
     				exception : function(response) {
     					var json = Ext.decode(response.responseText);
     					Ext.ux.Toast.msg(scheduling.stowagePlan.i18n('foss.scheduling.ShortSchedule.tip.title'), json.message, 'error', 1000);
     				}		
     			});
             	
 				
 				scheduling.stowagePlan.AddAssembleDeptWindow.show();
             }
        },{
        	iconCls: 'deppon_icons_delete',
        	tooltip: scheduling.stowagePlan.i18n('foss.shortScheduleDesign.gird.PlanDetail.tooltip.delete.lable'),   //删除
        	 handler: function(grid, rowIndex, colIndex) {
        		 Ext.Msg.show({
             		title:scheduling.stowagePlan.i18n('foss.shortScheduleDesign.gird.PlanDetail.tooltip.delete.lable'),   //删除
 					msg:scheduling.stowagePlan.i18n('foss.scheduling.ShortSchedule.tip.deleteConfirm'),   //确认删除?
 					buttons:Ext.Msg.YESNO,
 					icon: Ext.Msg.QUESTION, 
 					fn : function(btn){
 						if(btn == 'yes'){
 							var id = grid.getStore().getAt(rowIndex).data.id;
 							var array = {vo: {stowagePlansDto :{id:id}}};
 							Ext.Ajax.request({
 								url:scheduling.realPath('deleteStowagePlans.action'),
 			        			jsonData:array,
 			        			success : function(response) {
 			        				var json = Ext.decode(response.responseText);
 			        				grid.store.load();
 			        				Ext.ux.Toast.msg(scheduling.stowagePlan.i18n('foss.scheduling.ShortSchedule.tip.title'),scheduling.stowagePlan.i18n('foss.shortScheduleModify.deleteSuccess'),'ok',1000);
 			        			},
 			        			exception : function(response) {
 			        				var json = Ext.decode(response.responseText);
 			        				Ext.Msg.alert(scheduling.stowagePlan.i18n('foss.scheduling.ShortSchedule.tip.title'),json.message);
 			        			}
 			        		});
 						}
 					}
             	});
             }
        }]
    },{
		dataIndex: 'id' ,
		hidden:true
	},{
		header: scheduling.stowagePlan.i18n('Foss.scheduling.stowagePlan.label.origOrgName'),   //当前外场
		dataIndex: 'origOrgName' ,
		flex: 1 
	},{
		header: scheduling.stowagePlan.i18n('Foss.scheduling.stowagePlan.label.destOrgName'),   //到达部门
		dataIndex: 'destOrgName' ,
		flex: 1 
	},{
		header: scheduling.stowagePlan.i18n('Foss.scheduling.stowagePlan.label.modifyUserName'),   //修改人
		dataIndex: 'modifyUserName' ,
		flex: 1 
	},{
		header: scheduling.stowagePlan.i18n('Foss.scheduling.stowagePlan.label.modifyTime'),   //修改时间
		dataIndex: 'modifyTime' ,
		flex: 1
	}],
    constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.scheduling.stowagePlan.selectStowagePlansListStore');
		me.bbar = Ext.create('Deppon.StandardPaging', {
			store: me.store,
			plugins: 'pagesizeplugin',
			displayInfo: true
		});
		scheduling.stowagePlan.pagingBar = me.bbar;
		me.callParent([cfg]);
	}
});

/**
 * 更改配载查询方案
 */
//增加配载部门Model
Ext.define('Foss.scheduling.stowagePlan.AddAssembleDeptModel', {
	extend : 'Ext.data.Model',
	// 定义字段
	fields : [{
		name : 'assembleDeptName',
		type : 'string'
	},{
		name : 'assembleDeptCode',
		type : 'string'
	}]
});

//定义增加配载部门store
Ext.define('Foss.scheduling.stowagePlan.AddAssembleDeptStore', {
	extend : 'Ext.data.Store',
	// 绑定一个模型
	model : 'Foss.scheduling.stowagePlan.AddAssembleDeptModel',
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
});

//定义增加配载部门grid
Ext.define('Foss.scheduling.stowagePlan.AddAssembleDeptGrid', {
	extend : 'Ext.grid.Panel',
	columnLines: true,
	title : scheduling.stowagePlan.i18n('foss.scheduling.statisticalInquiries.changeAssembleWayWindow.gridTitle')/*'配载部门列表'*/,
	frame: true,
	height : 270,
	autoScroll : true,
	store : Ext.create('Foss.scheduling.stowagePlan.AddAssembleDeptStore'),
	columns : [ {
		fieldId : ' scheduling_stowagePlan_deleteButtonColumn',
		xtype : 'actioncolumn',
		width : 80,
		text : scheduling.stowagePlan.i18n('foss.shortdepartureplan.grid.plansearchresult.actioncolumn.lable')/*'操作'*/,
		align : 'center',
		items : [ {
			tooltip : scheduling.stowagePlan.i18n('foss.scheduling.statisticalInquiries.waybillGrid.deleteButtonColumn')/*'删除'*/,
			iconCls : 'deppon_icons_remove',
			handler : function(grid, rowIndex, colIndex) {
				grid.store.removeAt(rowIndex);
			}
		} ]
	}, {
		dataIndex : 'assembleDeptName',
		align : 'center',
		flex : 1,
		xtype : 'ellipsiscolumn',
		text : scheduling.stowagePlan.i18n('foss.scheduling.statisticalInquiries.changeAssembleWayWindow.assembleDeptColumn')/*'配载部门'*/
	} ]
});

//到达部门(新增OR修改)
Ext.define('Foss.scheduling.stowagePlan.arrivedDeptCodeFrom', {
	extend : 'Ext.form.Panel',
	title : scheduling.stowagePlan.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.inviteVehicleApplyInfo.arrivedDeptCode')/*'到达部门'*/,
	frame : true,
	collapsible : true,
	animCollapse : true,
	defaults : {
		margin : '5 10 5 10',
		labelWidth : 85,
		xtype : 'textfield'
	},
	layout : 'column',
	items : [{
		fieldLabel : scheduling.stowagePlan.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.inviteVehicleApplyInfo.arrivedDeptCode')/*'到达部门'*/,
		name : 'arrivedDeptCode',
		xtype : 'dynamicorgcombselector',
		type : 'ORG',
		transferCenter : 'Y',
		columnWidth : 3/4,
		allowBlank : false
	},{
		fieldLabel : scheduling.stowagePlan.i18n('Foss.scheduling.inviteVehicle.InviteVehicleApplyWindow.inviteVehicleApplyInfo.arrivedDeptCode')/*'到达部门'*/,
		name : 'arrivedDeptCodeHidden',
		xtype : 'dynamicorgcombselector',
		type : 'ORG',
		transferCenter : 'Y',
		columnWidth : 3/4,
		allowBlank : false,
		readOnly : true,
		hidden : true
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
});

//定义增加配载部门form
Ext.define('Foss.scheduling.stowagePlan.AddAssembleDeptForm', {
	extend : 'Ext.form.Panel',
	title : scheduling.stowagePlan.i18n('foss.scheduling.statisticalInquiries.changeAssembleWayWindow.addAssembleDept')/*'增加配载部门'*/,
	frame : true,
	collapsible : true,
	animCollapse : true,
	defaults : {
		margin : '5 10 5 10',
		labelWidth : 85,
		xtype : 'textfield'
	},
	layout : 'column',
	items : [{
		fieldLabel : scheduling.stowagePlan.i18n('foss.scheduling.statisticalInquiries.changeAssembleWayWindow.assembleDeptColumn')/*'配载部门'*/,
		name : 'assembleDeptCode',
		xtype : 'dynamicorgcombselector',
		type : 'ORG',
		transferCenter : 'Y',
		columnWidth : 3/4
	},{
		xtype : 'button',
		text : scheduling.stowagePlan.i18n('foss.scheduling.adjustTransportationPath.add')/*'增加'*/,
		cls : 'yellow_button',
		columnWidth : 1/4,
		handler : function(){
			if(this.up('form').getForm().isValid()){
				var cmp = this.up('form').getForm().findField('assembleDeptCode'),
				store = cmp.store,
				deptCode = cmp.getValue(),
				deptRecord = store.findRecord('code',deptCode,0,false,true,true);
				if(deptRecord != null){
					var deptName = deptRecord.get('name');

					//如果该部门已经被添加，则提示
					var store = scheduling.stowagePlan.addAssembleDeptGrid.store;
					
					if(store.getCount()>=scheduling.stowagePlan.stowagePlanDefault){
						Ext.ux.Toast.msg(scheduling.stowagePlan.i18n('foss.scheduling.ShortSchedule.tip.title')/*'提示'*/, '添加的部门数不能大于' + scheduling.stowagePlan.stowagePlanDefault + '个！', 'error', 1000);
					return ;
					}
					
					if(store.findRecord('assembleDeptCode',deptCode,0,false,true,true) != null){
						Ext.ux.Toast.msg(scheduling.stowagePlan.i18n('foss.scheduling.ShortSchedule.tip.title')/*'提示'*/, '该部门已经被添加！', 'error', 1000);
					}else if(scheduling.stowagePlan.arrivedDeptCodeFrom.getForm().findField('arrivedDeptCodeHidden').value === deptCode || scheduling.stowagePlan.arrivedDeptCodeFrom.getForm().findField('arrivedDeptCode').value === deptCode){
						Ext.ux.Toast.msg(scheduling.stowagePlan.i18n('foss.scheduling.ShortSchedule.tip.title')/*'提示'*/, '配载部门不能与到达部门相等！', 'error', 1000);
					}else{
						var deptInfo = {
							'assembleDeptCode' : deptCode,
							'assembleDeptName' : deptName
						};
						var deptInfoRecord = Ext.ModelManager.create(deptInfo,'Foss.scheduling.stowagePlan.AddAssembleDeptModel');
						store.insert(store.getCount(),deptInfoRecord);
						cmp.setValue(null);
						cmp.focus();
					}
				}
			}
		}
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
});


//定义增加配载部门窗口
Ext.define('Foss.scheduling.stowagePlan.AddAssembleDeptWindow', {
	extend : 'Ext.window.Window',
	closeAction : 'hide',
	modal : true,
	width : 600,
	title : scheduling.stowagePlan.i18n('foss.scheduling.stowagePlan.queryResultTab')/*'更改查询方案'*/,
	addAssembleDeptForm : null,
	getAddAssembleDeptForm : function(){
		if(this.addAssembleDeptForm==null){
			this.addAssembleDeptForm = Ext.create('Foss.scheduling.stowagePlan.AddAssembleDeptForm');
			scheduling.stowagePlan.addAssembleDeptForm = this.addAssembleDeptForm;
		}
		return this.addAssembleDeptForm;
	},
	arrivedDeptCodeFrom : null,
	getArrivedDeptCodeFrom : function(){
		if(this.arrivedDeptCodeFrom==null){
			this.arrivedDeptCodeFrom = Ext.create('Foss.scheduling.stowagePlan.arrivedDeptCodeFrom');
			scheduling.stowagePlan.arrivedDeptCodeFrom = this.arrivedDeptCodeFrom;
			//doLayout
		}
		return this.arrivedDeptCodeFrom;
	},
	addAssembleDeptGrid : null,
	getAddAssembleDeptGrid : function(){
		if(this.addAssembleDeptGrid==null){
			this.addAssembleDeptGrid = Ext.create('Foss.scheduling.stowagePlan.AddAssembleDeptGrid');
			scheduling.stowagePlan.addAssembleDeptGrid = this.addAssembleDeptGrid;
		}
		return this.addAssembleDeptGrid;
	},
	constructor : function(config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.items = [me.getArrivedDeptCodeFrom(),me.getAddAssembleDeptForm(),me.getAddAssembleDeptGrid()];
		me.callParent([cfg]);
	},
	buttons : [{
		text : scheduling.stowagePlan.i18n('foss.scheduling.forecastQuantity.cancle')/*'取消'*/,
		handler : function(){
			this.ownerCt.ownerCt.close();
		}
	},{
		text : scheduling.stowagePlan.i18n('foss.scheduling.forecastQuantity.save')/*'保存'*/,
		cls : 'yellow_button',
		handler : function(){
			var self = this.ownerCt.ownerCt;
			var store = scheduling.stowagePlan.addAssembleDeptGrid.store;
			
			var arrivedDeptCode = null;
			if(scheduling.stowagePlan.id != null){
				arrivedDeptCode = scheduling.stowagePlan.arrivedDeptCodeFrom.getForm().findField('arrivedDeptCodeHidden').value;
			}else{
				arrivedDeptCode = scheduling.stowagePlan.arrivedDeptCodeFrom.getForm().findField('arrivedDeptCode').value;
			}
			
			//chack
			if(arrivedDeptCode == null){
				Ext.ux.Toast.msg(scheduling.stowagePlan.i18n('foss.scheduling.ShortSchedule.tip.title'),'到达部门不能为空！');
				return ;
			}
			if(store.getCount() == 0){
				Ext.ux.Toast.msg(scheduling.stowagePlan.i18n('foss.scheduling.ShortSchedule.tip.title'),'配载部门不能为空！');
				return ;
			}
			
		    
			//获取查询方案里的配载部门
			var assembleDeptCodeList = new Array();
				
			store.each(function(record){
			    var assembleDeptObj={};
			    assembleDeptObj.orgName= record.get('assembleDeptName');
				assembleDeptObj.orgCode= record.get('assembleDeptCode');
				assembleDeptCodeList.push(assembleDeptObj);
				});

		    var vo = {};
		    vo.stowagePlansDetailList = assembleDeptCodeList;
		    vo.stowagePlansDto={};

		    var url='';
		    if(scheduling.stowagePlan.id){
		    	vo.stowagePlansDto.id = scheduling.stowagePlan.id;
		    	vo.stowagePlansDto.modifyTime = scheduling.stowagePlan.modifyTime;
		    	url='updateStowagePlans.action';
		    	
		    	vo.stowagePlansDto.destOrgName = scheduling.stowagePlan.arrivedDeptCodeFrom.getForm().findField('arrivedDeptCodeHidden').rawValue;
		    	vo.stowagePlansDto.destOrgCode = scheduling.stowagePlan.arrivedDeptCodeFrom.getForm().findField('arrivedDeptCodeHidden').value;
		    }else{
		    	url='insertStowagePlans.action';
		    	vo.stowagePlansDto.destOrgName = scheduling.stowagePlan.arrivedDeptCodeFrom.getForm().findField('arrivedDeptCode').rawValue;
		    	vo.stowagePlansDto.destOrgCode = scheduling.stowagePlan.arrivedDeptCodeFrom.getForm().findField('arrivedDeptCode').value;
		    }
		    
			//执行查询
			Ext.Ajax.request({
				url : scheduling.realPath(url),
				jsonData :{vo:vo},
				//动态创建表单，显示任务信息
				success : function(response) {
					var result = Ext.decode(response.responseText);
					Ext.ux.Toast.msg(scheduling.stowagePlan.i18n('foss.scheduling.ShortSchedule.tip.title'),'保存成功！');
					
					//清空窗口里面的数据
					scheduling.stowagePlan.arrivedDeptCodeFrom.getForm().reset();
					scheduling.stowagePlan.addAssembleDeptGrid.getStore().removeAll();
					scheduling.stowagePlan.queryResult.store.load();
					
					//保存成功则关闭当前窗口
					self.close();
				},
				exception : function(response) {
					var json = Ext.decode(response.responseText);
					Ext.ux.Toast.msg(scheduling.stowagePlan.i18n('foss.scheduling.ShortSchedule.tip.title'), json.message, 'error', 1000);
				}		
			});	
			
		}
	}]
});


Ext.onReady(function() {
	Ext.QuickTips.init();
	
	var queryForm = Ext.create('Foss.scheduling.stowagePlan.QueryForm');
	scheduling.stowagePlan.queryform=queryForm;
	var queryResult = Ext.create('Foss.scheduling.stowagePlan.QueryResult');
	scheduling.stowagePlan.queryResult=queryResult;

	Ext.create('Ext.panel.Panel',{
		id: 'T_scheduling-stowagePlanIndex_content',
		cls: "panelContentNToolbar",
		bodyCls: 'panelContentNToolbar-body',
		layout: 'auto',
		items: [queryForm,queryResult],
		renderTo: 'T_scheduling-stowagePlanIndex-body'
	});	
});
