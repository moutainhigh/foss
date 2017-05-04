baseinfo.salesExpenseMapping.salesExpenseMappingConfirmAlert = function(message,yesFn,noFn){
	Ext.Msg.confirm('温馨提示',message,function(o){
		if(o=='yes'){
			yesFn();
		}else{
			noFn();
		}
	});
};
//查询已配置(营业部与外请车费用承担部门映射营业部映射关系)的营业部与外请车费用承担部门映射FORM查询方法: 
baseinfo.salesExpenseMapping.salesExpenseMappingQuery=function(me){
	//获取form及其参数值
	var form=me.getForm();
	var businessDepartmentCode = form.findField('businessDepartmentCode').getValue();
	var budgetDepartmentCode = form.findField('budgetDepartmentCode').getValue();
	if ((businessDepartmentCode == null
			|| businessDepartmentCode.replace(/(^s*)|(s*$)/g, "").length == 0)
			&& (budgetDepartmentCode == null
			|| budgetDepartmentCode.replace(/(^s*)|(s*$)/g, "").length == 0)) {
		Ext.MessageBox.show({
					title : '温馨提示',
					msg : '请输入查询条件',
					width : 300,
					buttons : Ext.Msg.OK,
					icon : Ext.MessageBox.WARNING
				});
		return false;
	}
	// 设置参数
	params = {};
	Ext.apply(params, {
		'salesExpenseMappingVo.salesExpenseMappingQueryDto.businessDepartmentCode' : businessDepartmentCode,
		'salesExpenseMappingVo.salesExpenseMappingQueryDto.budgetDepartmentCode' : budgetDepartmentCode
	});
	//获取grid及grid的store,并给store赋值
	var grid = Ext.getCmp('T_baseinfo-salesExpenseMapping_content').getSalesExpenseMappingGrid();
	grid.store.setSubmitParams(params);
	
	//查询
	grid.store.loadPage(1,{
	      callback: function(records, operation, success) {
	    	//抛出异常  
		    var rawData = grid.store.proxy.reader.rawData;
		    if(!success && ! rawData.isException){
		    	Ext.MessageBox.show({
					title : '温馨提示',
					msg : rawData.message,
					width : 300,
					buttons : Ext.Msg.OK,
					icon : Ext.MessageBox.WARNING
				});
				//baseinfo.showInfoMes(rawData.message);
				return false;
			}  
	    	
	    	//正常返回
	    	if(success){
	    		var result =   Ext.decode(operation.response.responseText);
	    		if(result.salesExpenseMappingVo.salesExpenseMappingEntityList == null){
	    		Ext.MessageBox.show({
							title : '温馨提示',
							msg : '没有数据信息，请维护',
							width : 300,
							buttons : Ext.Msg.OK,
							icon : Ext.MessageBox.WARNING
						});
						return false;
	    		}
	    		grid.show();
	    	}
	       }
	    }); 
}

//营业部与外请车费用承担部门映射Store的Model
Ext.define('Foss.baseinfo.salesExpenseMapping.salesExpenseMappingModel',{
	extend:'Ext.data.Model',
	fields:[{
		name:'id',
		type : 'string'
	},{
		name:'businessDepartmentCode',//营业部编码
		type : 'string'
	},{
		name:'businessDepartmentName',//营业部名称
		type : 'string'
	},{
		name:'budgetDepartmentCode',//预算承担部门编码
		type : 'string'
	},{
		name:'budgetDepartmentName',//预算承担部门名称
		type : 'string'
	}]
});
//营业部与外请车费用承担部门映射Store
Ext.define('Foss.baseinfo.salesExpenseMapping.salesExpenseMappingStore',{
	extend:'Ext.data.Store',
	model:'Foss.baseinfo.salesExpenseMapping.salesExpenseMappingModel',
	pageSize: 20,
	proxy:{
		type:'ajax',
		url:baseinfo.realPath('querySalesExpenseMapping.action'),//查询action 
		actionMethods:'post',
		reader:{
			type:'json',
			root:'salesExpenseMappingVo.salesExpenseMappingEntityList',
			totalProperty:'totalCount'
		}
	},
	submitParams:null,
	setSubmitParams: function(submitParams){
		this.submitParams = submitParams;
	},
	getSubmitParams: function(){
		return this.submitParams;
	},
	constructor:function(config){
		var me = this, 
			cfg = Ext.apply({}, config);
		me.listeners = {
	   		'beforeload': function(store, operation, eOpts){
	   			Ext.apply(me.submitParams, {
		          "limit":operation.limit,
		          "page":operation.page,
		          "start":operation.start
		          }); 
	   			Ext.apply(operation, {
	   				params : me.submitParams 
	   			});
	   		} 
		};
		me.callParent([ cfg ]);
	} 
});


//查询营业部与外请车费用承担部门映射FORM
Ext.define('Foss.baseinfo.salesExpenseMapping.salesExpenseMappingQueryForm',{
	extend:'Ext.form.Panel',
	title:'查询条件',
	frame:true,
	height:200,
	defaults:{
		margin :'20 0 0 10',
		colspan : 1 
	},
	defaultType:'textfield',
	layout:{
		type :'column',
		columns :4
	},
	items : [
				{
					xtype : 'commonsaledepartmentselector',
					fieldLabel : '营业部名称',
					name:'businessDepartmentCode',
					labelWidth : 90

				}, {
					xtype : 'dynamicorgcombselector',
					id : 'budgetDepartmentCode',
					fieldLabel : '预算承担部门',
					name:'owendSubsidiaryCode',
					transferCenter : 'Y',
					labelWidth : 90 
				},{
					xtype : 'container',
					columnWidth : 1,
					defaultType:'button',
					layout:'column',
					items : [{
						xtype : 'button', 
						text:baseinfo.salesExpenseMapping.i18n('foss.baseinfo.reset'),//重置
						columnWidth:.1,
						handler: function(){
							var form=this.up('form').getForm();
							form.reset();
						}
					},{
						xtype:'container',
						html:'&nbsp;',
						columnWidth:.8
					},{
						xtype : 'button', 
						width:70,
						columnWidth:.1,
						text : baseinfo.salesExpenseMapping.i18n('foss.baseinfo.query'),//查询
						disabled:!baseinfo.salesExpenseMapping.isPermission('baseinfo/querysalesExpenseMapping'),
						hidden:!baseinfo.salesExpenseMapping.isPermission('baseinfo/querysalesExpenseMapping'),
						cls:'yellow_button',
						handler : function() {
							var form=this.up('form');
						
							baseinfo.salesExpenseMapping.salesExpenseMappingQuery(form);
						}
					}]
				
				}]			
});

//修改营业部与外请车费用承担部门映射信息FORM
Ext.define('Foss.baseinfo.salesExpenseMapping.updatesalesExpenseMappingConfigForm',{
	extend:'Ext.form.Panel',
	title:'',
	frame:true,
	width : 800,
	height : 250,
	defaults:{
		margin :'20 0 0 20',
		labelWidth :100,
		colspan : 1 
	},
	defaultType:'textfield',
	layout:{
		type :'column',
		columns :2
	},
	items : [	{
					fieldLabel:'营业部名称',
					name:'businessDepartmentName',
					allowBlank:false,
					readOnly:true,
					columnWidth:.35
				},{
					xtype : 'dynamicorgcombselector',
					id : 'dynamicorgselector',
					fieldLabel : '预算承担部门',
					name:'budgetDepartmentName',
					transferCenter : 'Y',
					columnWidth:.35
				},{
					fieldLabel: '预算承担部门编码', 
					name: 'budgetDepartmentCode',
					columnWidth:.35,
					hidden:true
				}
				]			
});
//进入营业部与外请车费用承担部门映射修改界面
baseinfo.salesExpenseMapping.editDetialsalesExpenseMapping = function(grid, rowIndex, colIndex){
	// 获取选中的数据
	var selection = grid.getStore().getAt(rowIndex);	
	//获取显示弹出win
	var a_win = Ext.getCmp('Foss_baseinfo_salesExpenseMapping_updatesalesExpenseMappingWindow_Id');
	if (Ext.isEmpty(a_win)) {
		a_win = Ext.create('Foss.baseinfo.salesExpenseMapping.updatesalesExpenseMappingWindow');
	}
	//营业部与外请车费用承担部门映射FORM加载数据
	var salesExpenseMappingModel  = new Foss.baseinfo.salesExpenseMapping.salesExpenseMappingModel(selection.data);
	var updatesalesExpenseMappingConfigForm = a_win.items.items[0];
	updatesalesExpenseMappingConfigForm.loadRecord(salesExpenseMappingModel);
	a_win.salesExpenseMappingEntity = selection.data;
	

	a_win.show();

}

/**
 * 声明营业部与外请车费用承担部门映射windows
 */
Ext.define('Foss.baseinfo.salesExpenseMapping.updatesalesExpenseMappingWindow', {
	extend : 'Ext.window.Window',
	id : 'Foss_baseinfo_salesExpenseMapping_updatesalesExpenseMappingWindow_Id',
	title : '修改车队定区信息',
	width : 850,
	height : 400,
	columnWidth : 0.98,
	modal : true,
	closeAction : 'hide',
	layout : 'column',
	salesExpenseMappingEntity:null,
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.items = [
		            Ext.create('Foss.baseinfo.salesExpenseMapping.updatesalesExpenseMappingConfigForm')
		            ];
		me.dockedItems = [{
			xtype: 'toolbar',
		    dock: 'bottom',
		    layout:'column',		    	
		    defaults:{
				margin:'0 0 5 5'
			},	
			 items: [{
				 	xtype:'button',
					text:'取消',
					//itemId:'Foss_baseinfo_salesExpenseMapping_updatesalesExpenseMappingWindow_cancel_Id',
					columnWidth:.1,
					handler : function() {
						var a_win = Ext.getCmp('Foss_baseinfo_salesExpenseMapping_updatesalesExpenseMappingWindow_Id');
						var updatesalesExpenseMappingConfigForm = a_win.items.items[0];
						updatesalesExpenseMappingConfigForm.getForm().reset();
						a_win.hide();
					}
				},{
				 	xtype:'button',
					text:'重置',
					//itemId:'Foss_baseinfo_salesExpenseMapping_updatesalesExpenseMappingWindow_reset_Id',
					columnWidth:.1,
					handler : function() {
						
						var a_win = Ext.getCmp('Foss_baseinfo_salesExpenseMapping_updatesalesExpenseMappingWindow_Id');
						var salesExpenseMappingModel  = new Foss.baseinfo.salesExpenseMapping.salesExpenseMappingModel(a_win.salesExpenseMappingEntity);
						var updatesalesExpenseMappingConfigForm = a_win.items.items[0];
						updatesalesExpenseMappingConfigForm.loadRecord(salesExpenseMappingModel);
						
					}
				},{
					xtype:'container',
					border:false,
					html:'&nbsp;',
					columnWidth:.7
				},{
					xtype:'button',
					//cls : 'yellow_button',
					text : '提交',
					//itemId:'Foss_baseinfo_salesExpenseMapping_showOrUpdatesalesExpenseMappingWindow_submit_Id',
					columnWidth:.1,
					handler : function() {
							var a_win = Ext.getCmp('Foss_baseinfo_salesExpenseMapping_updatesalesExpenseMappingWindow_Id');
							var toUpdateId = a_win.salesExpenseMappingEntity.id;
							
							var updatesalesExpenseMappingConfigForm = a_win.items.items[0];
							var budgetDepartmentCode = updatesalesExpenseMappingConfigForm.getForm().findField('budgetDepartmentName').getValue();
							var budgetDepartmentName = updatesalesExpenseMappingConfigForm.getForm().findField('budgetDepartmentName').getRawValue();
							
							//调用提交后台修改
							Ext.Ajax.request({
								url:baseinfo.realPath('updateSalesExpenseMapping.action'),//修改action 参数  selectedIds
								params:{
									'salesExpenseMappingVo.salesExpenseMappingQueryDto.selectedIds':toUpdateId,
									'salesExpenseMappingVo.salesExpenseMappingQueryDto.budgetDepartmentCode':budgetDepartmentCode,
									'salesExpenseMappingVo.salesExpenseMappingQueryDto.budgetDepartmentName':budgetDepartmentName
								},
								method:'post',
								success:function(response){
									
									//重置form
									updatesalesExpenseMappingConfigForm.getForm().reset();
									//将win隐藏
									a_win.hide();
									
										// 设置参数
									var salesExpenseMappingGrid = Ext.getCmp('T_baseinfo-salesExpenseMapping_content').getSalesExpenseMappingGrid();
										var params = salesExpenseMappingGrid.store.getSubmitParams();
										if(params==null||params==''){
											params = {};
											Ext.apply(params, {
												'salesExpenseMappingVo.salesExpenseMappingQueryDto.businessDepartmentCode' : null,
												'salesExpenseMappingVo.salesExpenseMappingQueryDto.budgetDepartmentCode' : null
											});
											salesExpenseMappingGrid.store.setSubmitParams(params);
										}
									salesExpenseMappingGrid.store.loadPage(1,{
									      callback: function(records, operation, success) {
									    	  
									    	//抛出异常  
										    var rawData = salesExpenseMappingGrid.store.proxy.reader.rawData;
										    if(!success && ! rawData.isException){
										    	Ext.MessageBox.show({
													title : '温馨提示',
													msg : rawData.message,
													width : 300,
													buttons : Ext.Msg.OK,
													icon : Ext.MessageBox.WARNING
												});
												
												//baseinfo.showInfoMes(rawData.message);
												return false;
											}  
									    	//正常返回
									    	if(success){
									    		var result =   Ext.decode(operation.response.responseText);
									    		salesExpenseMappingGrid.show();
									    	}
									       }
									    }); 	
								},
								exception:function(response){
									var result = Ext.decode(response.responseText);
									Ext.MessageBox.show({
										title : '温馨提示',
										msg : result.message,
										width : 300,
										buttons : Ext.Msg.OK,
										icon : Ext.MessageBox.WARNING
									});
									return false;
								}			
							});
					}
				}]
		}];		
		me.callParent([cfg]);
	}
})
//未选营业部名称store
Ext.define('Foss.baseinfo.salesExpenseMapping.UnselectedBusinessDepartmentStore',{  
    extend:'Ext.data.Store',  
    model : 'Foss.baseinfo.salesExpenseMapping.salesExpenseMappingModel',
    data : []
  
}); 
//已选营业部名称store 
Ext.define('Foss.baseinfo.salesExpenseMapping.SelectedBusinessDepartmentStore',{  
    extend:'Ext.data.Store',  
    model : 'Foss.baseinfo.salesExpenseMapping.salesExpenseMappingModel',
    data : []
  
}); 
//营业部与外请车费用承担部门 界面form
Ext.define('Foss.baseinfo.salesExpenseMapping.salesExpenseMappingConfigForm', {
	extend : 'Ext.form.Panel',
	defaultType : 'textfield',
	width : 750,
	height : 550,
	autoScroll:true,
	layout:{
        type: 'table',
        columns: 4
    },
    formRecord:null,												//绑定的model 
    formStore:null,													//绑定的Store 
   // viewState:null,
    constructor : function(config) {							//构造器
		var me = this,cfg = Ext.apply({}, config);
		me.items = me.getItems(config);
		me.callParent([cfg]);
	},

	//获取营业部与外请车费用承担部门新增信息
	salesExpenseMappingAddList:null,
    getSalesExpenseMappingAddList:function(){
    	var me = this;
    	//var form = me.getForm();
    	var salesExpenseMappingAddModelList = me.getSelectedBusinessDepartmentStore().getNewRecords( );
    	var salesExpenseMappingAddList = new Array();
    	for(var i = 0;i<salesExpenseMappingAddModelList.length;i++){
    		salesExpenseMappingAddList.push(salesExpenseMappingAddModelList[i].data);
    	}
    	return salesExpenseMappingAddList;
    },
	getItems:function(config){
		var me = this;
		return [
		{
			colspan:2,
			labelWidth : 120,
			fieldLabel:'预算承担部门',	 				 
			xtype:'dynamicorgcombselector',
			//active : 'Y',
			allowBlank:false,
			//isFullFleetOrgFlag:'Y',
			name:'budgetDepartmentCode'
		},{
			colspan:2,
			labelWidth : 120,
			fieldLabel:'营业部名称',	 				 
			xtype:'commonsaledepartmentselector',
			//active : 'Y',
			allowBlank:false,
			//isFullFleetOrgFlag:'Y',
			name:'businessDepartmentCode',
			listeners:{
				select:function(comb,records,obj){
					var model = new Foss.baseinfo.salesExpenseMapping.salesExpenseMappingModel();
					var boolean = false;
					me.getUnselectedBusinessDepartmentStore().each(function(record){ //判断选择中的值是否存在纸槽中，
						if(record.get('businessDepartmentCode')==records[0].get('code')){
							boolean = true;
						}
					});
					if(boolean){
						return;
					}
					model.set('businessDepartmentCode',records[0].get('code'));
					model.set('businessDepartmentName',records[0].get('name'));
					me.getUnselectedBusinessDepartmentStore().add(model);
				}
			}
		},{
			colspan:4,
			xtype: 'container',
			layout : 'hbox',
			height:420,
			items:[
				me.getSourceGrid(config),
			       Ext.create('Foss.baseinfo.salesExpenseMapping.moveBtnContainer',{
					sourceGrid:me.getSourceGrid(config),
					targetGrid:me.getTargetGrid(config),
					//viewState:config.viewState,
					flex:0.3
			}),me.getTargetGrid(config)]
		}];
	},
	sourceGrid:null,										//未选营业部名称grid
	getSourceGrid:function(config){
		var me = this;
		if(me.sourceGrid === null){
			me.sourceGrid = Ext.create('Ext.grid.Panel',{
				frame:true,
				height:300,
				flex:1.2,
				store:me.getUnselectedBusinessDepartmentStore(config),
				columns:[
				{flex:1,
				text : '营业部名称',
				dataIndex : 'businessDepartmentName'
				}]
			});
		}
		return me.sourceGrid;
	},
	targetGrid:null,										//已选营业部名称grid 
	getTargetGrid:function(config){
		var me = this;
		if(me.targetGrid === null){
			me.targetGrid = 
			Ext.create('Ext.grid.Panel',{
				frame:true,
				height:300,
				flex:1.2,
				store:me.getSelectedBusinessDepartmentStore(config),
				columns:[{flex:1,
				text : '预算覆盖营业部名称',
				dataIndex : 'businessDepartmentName'
				}]
			});
		}
		return me.targetGrid;
	},
	unselectedBusinessDepartmentStore:null,										//未选营业部名称store
	getUnselectedBusinessDepartmentStore:function(config){
		var me = this,form = me.getForm();
		if(me.unselectedBusinessDepartmentStore === null){
			me.unselectedBusinessDepartmentStore = Ext.create('Foss.baseinfo.salesExpenseMapping.UnselectedBusinessDepartmentStore',{
				autoLoad : false
			});
		}
		return me.unselectedBusinessDepartmentStore;
	},
	selectedBusinessDepartmentStore:null,										//已选营业部名称store 
	getSelectedBusinessDepartmentStore:function(config){
		var me = this,form = me.getForm();
		if(me.selectedBusinessDepartmentStore === null){
			me.selectedBusinessDepartmentStore = Ext.create('Foss.baseinfo.salesExpenseMapping.SelectedBusinessDepartmentStore',{
				autoLoad : false
			});
		}
		return me.selectedBusinessDepartmentStore;
	}
});
//移动按钮 容器
Ext.define('Foss.baseinfo.salesExpenseMapping.moveBtnContainer', {
	extend : 'Ext.container.Container',
	buttonAlign : 'center',
	layout : 'column',
	sourceGrid:null,
	targetGrid:null,
	viewState:null,
	constructor : function(config){
		var me = this, cfg = Ext.apply({}, config);
		me.items = me.getItems(config);
		me.callParent([cfg]);
	},
	getItems:function(config){
		return [{
			columnWidth : 1,
			height : 0,
			xtype : 'container',
			style : 'padding-top:15px;border:none',
			hide:true
		},{
			columnWidth : 1,
		    xtype : 'button',
		    //disabled:baseinfo.viewState.view === config.viewState,
			text:'-->',
			handler : function(){
				//把选中的记录添加到目标列表中
				var models = config.sourceGrid.getSelectionModel().getSelection(),gisIds=[];
				if(!Ext.isEmpty(models) && models.length>0){
					for(var j = 0;j<models.length;j++){
						config.sourceGrid.store.remove(models[j]);
					}
					config.targetGrid.store.add(models);
				}
			}
		},{
			columnWidth : 1,
			height : 0,
			xtype : 'container',
			style : 'padding-top:10px;border:none',
			hide:true
		},{
			columnWidth : 1,
			xtype : 'button',
		    //disabled:baseinfo.viewState.view === config.viewState,
			text:'->>',
			handler : function(){
				//把选中的记录添加到目标列表中
				var models = config.sourceGrid.store.data.items;
				if(!Ext.isEmpty(models) && models.length>0){
					var tempArray = Ext.Array.clone(models);
					for(var j = 0;j<tempArray.length;j++){
						config.sourceGrid.store.remove(tempArray[j]);
					}
					config.targetGrid.store.add(tempArray);
				}
			}
		},{
			columnWidth : 1,
			height : 0,
			xtype : 'container',
			style : 'padding-top:10px;border:none',
			hide:true
		},{
			columnWidth : 1,
			xtype : 'button',
			text:'<--',
			handler : function(){
				//把选中的记录添加到源列表中
				var models = config.targetGrid.getSelectionModel().getSelection(),modelArray = [];
				if(!Ext.isEmpty(models) && models.length>0){
					for(var j = 0;j<models.length;j++){
						config.targetGrid.store.remove(models[j]);
						modelArray.push(models[j]);
					}
					config.sourceGrid.store.add(models);
				}}
		},{
			columnWidth : 1,
			height : 0,
			xtype : 'container',
			style : 'padding-top:10px;border:none',
			hide:true
		},{
			columnWidth : 1,
			xtype : 'button',
			text:'<<-',
			handler : function(){
				//把选中的记录添加到源列表中
				var models = config.targetGrid.store.data.items;
				if(!Ext.isEmpty(models) && models.length>0){
					var tempArray = Ext.Array.clone(models);
					var modelArray = [];
					for(var j = 0;j<tempArray.length;j++){
						config.targetGrid.store.remove(tempArray[j]);
						modelArray.push(tempArray[j]);
					}
					config.sourceGrid.store.add(tempArray);
				}}
		}];
	}
});
/**
 * 声明新增营业部与外请车费用承担部门映射windows
 */
Ext.define('Foss.baseinfo.salesExpenseMapping.addsalesExpenseMappingWindow', {
	extend : 'Ext.window.Window',
	id : 'Foss_baseinfo_salesExpenseMapping_addsalesExpenseMappingWindow_Id',
	title : '新增车队定区信息',
	width : 700,
	height :620,
	columnWidth : 0.98,
	modal : true,
	closeAction : 'hide',
	layout : 'column',
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.items = [
		            Ext.create('Foss.baseinfo.salesExpenseMapping.salesExpenseMappingConfigForm')
		            ];
		me.dockedItems = [{
			xtype: 'toolbar',
		    dock: 'bottom',
		    layout:'column',		    	
		    defaults:{
				margin:'0 0 5 3'
			},	
			 items: [{
					xtype:'container',
					border:false,
					html:'&nbsp;',
					columnWidth:.7
				},{
				 	xtype:'button',
					text:'取消',
					columnWidth:.1,
					handler : function() {
						//获取当前win
						var a_win = Ext.getCmp('Foss_baseinfo_salesExpenseMapping_addsalesExpenseMappingWindow_Id');
						if(!Ext.isEmpty(a_win)){
							
							//营业部与外请车费用承担部门映射FORM清空数据
							var salesExpenseMappingConfigForm = a_win.items.items[0];
							salesExpenseMappingConfigForm.getForm().reset();
							salesExpenseMappingConfigForm.getUnselectedBusinessDepartmentStore().removeAll();
							salesExpenseMappingConfigForm.getSelectedBusinessDepartmentStore().removeAll();
							
							//销毁当前win
							a_win.hide();
						}	
					}
				},{
				 	xtype:'button',
					text:'重置',
					columnWidth:.1,
					handler : function() {
						//获取当前win
						var a_win = Ext.getCmp('Foss_baseinfo_salesExpenseMapping_addsalesExpenseMappingWindow_Id');
						if(!Ext.isEmpty(a_win)){
							
							//营业部与外请车费用承担部门映射FORM清空数据
							var salesExpenseMappingConfigForm = a_win.items.items[0];
							salesExpenseMappingConfigForm.getForm().reset();
							salesExpenseMappingConfigForm.getUnselectedBusinessDepartmentStore().removeAll();
							salesExpenseMappingConfigForm.getSelectedBusinessDepartmentStore().removeAll();
							
						}	
					}
				},{
					xtype:'button',
					//cls : 'yellow_button',
					text : '提交',
					itemId:'addsalesExpenseMappingWindow_submit_ID',
					columnWidth:.1,
					handler : function() {
						
						//获取当前win
						var a_win = Ext.getCmp('Foss_baseinfo_salesExpenseMapping_addsalesExpenseMappingWindow_Id');
						if(!a_win.items.items[0].getForm().isValid()){
									Ext.MessageBox.show({
										title : '温馨提示',
										msg : '数据不能为空',
										width : 300,
										buttons : Ext.Msg.OK,
										icon : Ext.MessageBox.WARNING
									});
									return false;
						}
						if(!Ext.isEmpty(a_win)){
//							var model =new Foss.baseinfo.salesExpenseMapping.salesExpenseMappingModel();
							//营业部与外请车费用承担部门映射FORM数据
							var salesExpenseMappingConfigForm = a_win.items.items[0];
							var salesExpenseMappingAddList = salesExpenseMappingConfigForm.getSalesExpenseMappingAddList();
							var form = salesExpenseMappingConfigForm.getForm();
							var budgetDepartmentCode = form.findField('budgetDepartmentCode').getValue();
							var budgetDepartmentName = form.findField('budgetDepartmentCode').getRawValue();
							for(var i = 0;i<salesExpenseMappingAddList.length;i++){
									salesExpenseMappingAddList[i].budgetDepartmentCode=budgetDepartmentCode;
									salesExpenseMappingAddList[i].budgetDepartmentName=budgetDepartmentName;
								}
							
						// 设置参数
							var salesExpenseMappingGrid = Ext.getCmp('T_baseinfo-salesExpenseMapping_content').getSalesExpenseMappingGrid();
								var params = salesExpenseMappingGrid.store.getSubmitParams();
								if(params==null||params==''){
									params = {};
									Ext.apply(params, {
										'salesExpenseMappingVo.salesExpenseMappingQueryDto.businessDepartmentCode' : null,
										'salesExpenseMappingVo.salesExpenseMappingQueryDto.budgetDepartmentCode' : null
									});
									salesExpenseMappingGrid.store.setSubmitParams(params);
								}
								
								// 调用提交后台保存
								Ext.Ajax.request({
									url:baseinfo.realPath('addSalesExpenseMapping.action'),//新增action  参数salesExpenseMappingEntity
									jsonData:{'salesExpenseMappingVo':{'salesExpenseMappingEntityList':salesExpenseMappingAddList}},
									method:'post',
									success:function(response){
										
										//重置form
										salesExpenseMappingConfigForm.getForm().reset();
										salesExpenseMappingConfigForm.getUnselectedBusinessDepartmentStore().removeAll();
										salesExpenseMappingConfigForm.getSelectedBusinessDepartmentStore().removeAll();
										//将win隐藏
										a_win.hide();
										
										//重新查询数据
										salesExpenseMappingGrid.store.loadPage(1,{
										      callback: function(records, operation, success) {
										    	  
										    	//抛出异常  
											    var rawData = salesExpenseMappingGrid.store.proxy.reader.rawData;
											    if(!success && ! rawData.isException){
											    	Ext.MessageBox.show({
														title : '温馨提示',
														msg : rawData.message,
														width : 300,
														buttons : Ext.Msg.OK,
														icon : Ext.MessageBox.WARNING
													});
													//baseinfo.showInfoMes(rawData.message);
													return false;
												}  
										    	//正常返回
										    	if(success){
										    		var result = Ext.decode(operation.response.responseText);
										    		salesExpenseMappingGrid.show();
										    	}
										       }
										    }); 	
									},
									exception:function(response){
										var result = Ext.decode(response.responseText);
										Ext.MessageBox.show({
											title : '温馨提示',
											msg : result.message,
											width : 300,
											buttons : Ext.Msg.OK,
											icon : Ext.MessageBox.WARNING
										});
										//baseinfo.showInfoMes(result.message);
										return false;
									}			
								});
						
						}		
					}
				}]
		}];		
		me.callParent([cfg]);
	}
})

//营业部与外请车费用承担部门映射列表
Ext.define('Foss.baseinfo.salesExpenseMapping.SalesExpenseMappingGrid',{
	extend:'Ext.grid.Panel',
    title: '查询结果列表',
    frame:true,
	height:500,
    store: Ext.create('Foss.baseinfo.salesExpenseMapping.salesExpenseMappingStore'),
	columns:[{
    	xtype:'actioncolumn',
    	header:'操作',
    	width:120,
    	align: 'center',
    	items:[{
    		iconCls : 'deppon_icons_edit',  
			tooltip:'修改',
			disabled:!baseinfo.salesExpenseMapping.isPermission('baseinfo/updatesalesExpenseMapping'),
			hidden:!baseinfo.salesExpenseMapping.isPermission('baseinfo/updatesalesExpenseMapping'),
			handler:function(grid, rowIndex, colIndex){
				baseinfo.salesExpenseMapping.editDetialsalesExpenseMapping(grid, rowIndex, colIndex);
			}
    	},{
    		iconCls:'deppon_icons_delete',  
			tooltip:'删除',
			disabled:!baseinfo.salesExpenseMapping.isPermission('baseinfo/deletesalesExpenseMapping'),
			hidden:!baseinfo.salesExpenseMapping.isPermission('baseinfo/deletesalesExpenseMapping'),
			handler:function(grid, rowIndex, colIndex){
				var yesFn=function(){
					var record = grid.getStore().getAt(rowIndex)
						// 调用
				 		Ext.Ajax.request({
				 			url:baseinfo.realPath('deleteSalesExpenseMapping.action'),//作废 action 参数 selectedIds
				 			jsonData:{'salesExpenseMappingVo':{'salesExpenseMappingEntity':record.data}},
				 			success:function(response){
				 				//重新查询数据
				 				grid.store.loadPage(1,{
				 				      callback: function(records, operation, success) {
				 				    	  
				 				    	//抛出异常  
				 					    var rawData = grid.store.proxy.reader.rawData;
				 					    if(!success && ! rawData.isException){
				 					    	Ext.MessageBox.show({
				 								title : '温馨提示',
				 								msg : rawData.message,
				 								width : 300,
				 								buttons : Ext.Msg.OK,
				 								icon : Ext.MessageBox.WARNING
				 							});
				 							//baseinfo.showInfoMes(rawData.message);
				 							return false;
				 						}  
				 				    	
				 				    	//正常返回
				 				    	if(success){
				 				    		var result =   Ext.decode(operation.response.responseText);
				 				    		grid.show();
				 				    	}
				 				       }
				 				    }); 
				 			},
				 			exception:function(response){
				 				var result = Ext.decode(response.responseText);	
				 				Ext.MessageBox.show({
									title : '温馨提示',
									msg : result.message,
									width : 300,
									buttons : Ext.Msg.OK,
									icon : Ext.MessageBox.WARNING
								});
				 				//baseinfo.showInfoMes(result.message);
				 				//重新查询数据
				 				grid.store.loadPage(1,{
				 				      callback: function(records, operation, success) {
				 				    	  
				 				    	//抛出异常  
				 					    var rawData = grid.store.proxy.reader.rawData;
				 					    if(!success && ! rawData.isException){
				 					    	Ext.MessageBox.show({
				 								title : '温馨提示',
				 								msg : rawData.message,
				 								width : 300,
				 								buttons : Ext.Msg.OK,
				 								icon : Ext.MessageBox.WARNING
				 							});
				 							//baseinfo.showInfoMes(rawData.message);
				 							return false;
				 						}  
				 				    	
				 				    	//正常返回
				 				    	if(success){
				 				    		var result =   Ext.decode(operation.response.responseText);
//				 							if(result.expressPartSalesDeptVo.saleDepartmentResultDtoList.length>0){
//				 								grid.show();
//				 							}
				 				    		grid.show();
				 				    	}
				 				       }
				 				    }); 
				 			
				 			}
				 		});	
					};
				var noFn=function(){
					 	return false;
					};
				baseinfo.salesExpenseMapping.salesExpenseMappingConfirmAlert('确认是否要作废营业部与外请车费用承担部门映射',yesFn,noFn);
				
			}
    	
    	}]
	},{
		header: '营业部名称', 
		width:220,
		dataIndex: 'businessDepartmentName'
	},{
		
		header: '预算承担部门名称',
		width:220,
		dataIndex: 'budgetDepartmentName'
	},{
		
		header: '营业部编码',
		dataIndex: 'businessDepartmentCode',
		hidden:true
	},{
		header: '预算承担部门编码', 
		dataIndex: 'budgetDepartmentCode',
		hidden:true
	},{
		header: 'ID',
		dataIndex: 'id',
		hidden:true
	}],
	viewConfig: {
		enableTextSelection: true
	},
	constructor:function(config){
	
		var me = this;
		me.dockedItems =[{
			xtype :'toolbar',
			dock :'top',
			layout :'column',
			defaults :{
				margin :'0 8 0 0'
			},
			items :[{
				xtype :'button',
				text : '新增',
				columnWidth :.08,
				disabled:!baseinfo.salesExpenseMapping.isPermission('baseinfo/addsalesExpenseMapping'),
				hidden:!baseinfo.salesExpenseMapping.isPermission('baseinfo/addsalesExpenseMapping'),
				handler : function(grid, rowIndex, colIndex) {

					var a_win = Ext.getCmp('Foss_baseinfo_salesExpenseMapping_addsalesExpenseMappingWindow_Id');
					if (Ext.isEmpty(a_win)) {
						a_win = Ext.create('Foss.baseinfo.salesExpenseMapping.addsalesExpenseMappingWindow');
					}
					var salesExpenseMappingConfigForm = a_win.items.items[0];
					var budgetDepartmentCode = salesExpenseMappingConfigForm.getForm().findField('budgetDepartmentCode').getValue();
					var businessDepartmentCode = salesExpenseMappingConfigForm.getForm().findField('businessDepartmentCode').getValue();
					if(budgetDepartmentCode !=null || businessDepartmentCode!=null){
						salesExpenseMappingConfigForm.getForm().reset();
						salesExpenseMappingConfigForm.getUnselectedBusinessDepartmentStore().removeAll();
						salesExpenseMappingConfigForm.getSelectedBusinessDepartmentStore().removeAll();
					}
					
					//作废win窗体的按钮
					a_win.getDockedItems('toolbar[dock="bottom"]')[0].getComponent('addsalesExpenseMappingWindow_submit_ID').setVisible(true);
					a_win.show();
				}
			},{	
				xtype:'container',
				border:false,
				html:'&nbsp;',
				columnWidth:.9
			}]
		},{
	   		xtype: 'toolbar',
		    dock: 'bottom',
		    layout:'column',		    	
		    defaults:{
				margin:'0 0 5 3'
			},		
		    items: [{
				xtype:'container',
				border:false,
				html:'&nbsp;',
				columnWidth:.3
			},{
				xtype:'standardpaging',
				store:me.store,
				columnWidth:.7,
				plugins: Ext.create('Deppon.ux.PageSizePlugin',{
					maximumSize:500
				})
			}]
		}];	
		me.callParent();
	}
});
// 初始化界面
Ext.onReady(function() {
	Ext.QuickTips.init();
	if (Ext.getCmp('T_baseinfo-salesExpenseMapping_content')) {
		return;
	} 
	
	/*Ext.Loader.setConfig({enabled:true});*/
	//查询已配置(营业部与外请车费用承担部门映射)的营业部与外请车费用承担部门映射信息FORM
	var salesExpenseMappingQueryForm = Ext.create('Foss.baseinfo.salesExpenseMapping.salesExpenseMappingQueryForm');
	
	//已配置(营业部与外请车费用承担部门映射)的营业部与外请车费用承担部门映射信息列表grid
	var salesExpenseMappingGrid = Ext.create('Foss.baseinfo.salesExpenseMapping.SalesExpenseMappingGrid');
	
	Ext.create('Ext.panel.Panel', {
		id: 'T_baseinfo-salesExpenseMapping_content',
		cls : 'panelContentNToolbar',
		bodyCls : 'panelContentNToolbar-body',
		layout : 'auto',
		getSalesExpenseMappingQueryForm:function(){
			return salesExpenseMappingQueryForm;
		},
		getSalesExpenseMappingGrid:function(){
			return salesExpenseMappingGrid;
		},
		items: [salesExpenseMappingQueryForm,salesExpenseMappingGrid],
		renderTo : 'T_baseinfo-salesExpenseMapping-body'
	});
});