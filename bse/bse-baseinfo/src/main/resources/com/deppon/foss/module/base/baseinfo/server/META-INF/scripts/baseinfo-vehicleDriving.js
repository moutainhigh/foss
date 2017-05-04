baseinfo.vehicledriving.vehicleDrivingConfirmAlert = function(message,yesFn,noFn){
	Ext.Msg.confirm('温馨提示',message,function(o){
		if(o=='yes'){
			yesFn();
		}else{
			noFn();
		}
	});
};


//查询长途车队信息FORM查询方法: 
baseinfo.vehicledriving.vehicleDrivingQuery=function(me){
	//获取form及其参数值
	var form=me.getForm();
	var longHaulFleetCode = form.findField('longHaulFleetCode').getValue();
	// 设置参数
	params = {};
	Ext.apply(params, {
		'vehicleDrivingVo.vehicleDrivingQueryDto.longHaulFleetCode' : longHaulFleetCode,
	});
	//获取grid及grid的store,并给store赋值
	var grid = Ext.getCmp('T_baseinfo-vehicleDriving_content').getVehicleDrivingGrid();
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
				return false;
			}  
	    	
	    	//正常返回
	    	if(success){
	    		var result =   Ext.decode(operation.response.responseText);
	    		grid.show();
	    	}
	       }
	    }); 
}

//长途车队信息Store的Model
Ext.define('Foss.baseinfo.vehicledriving.VehicleDrivingModel',{
	extend:'Ext.data.Model',
	fields:[{
		name:'id',
		type : 'string'
	},{
		name:'longHaulFleetName',//长途车队名称
		type : 'string'
	},{
		name:'longHaulFleetCode',//长途车队编码
		type : 'string'
	},{
		name:'departmentName',//下辖部门名称
		type : 'string'
	},{
		name:'departmentCode',//下辖部门编码
		type : 'string'
	},{
		name:'trafficCode',//行车编码简称
		type : 'string'
	}]
});
//长途车队信息Store
Ext.define('Foss.baseinfo.vehicledriving.VehicleDrivingStore',{
	extend:'Ext.data.Store',
	model:'Foss.baseinfo.vehicledriving.VehicleDrivingModel',
	pageSize: 20,
	sorters: [{
	     property: 'modifyTime',
	     direction: 'DESC'
	 }],
	proxy:{
		type:'ajax',
		url:baseinfo.realPath('queryVehicleDriving.action'),//查询action 
		actionMethods:'post',
		reader:{
			type:'json',
			root:'vehicleDrivingVo.vehicleDrivingEntityList',
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


//查询长途车队信息FORM
Ext.define('Foss.baseinfo.vehicledriving.VehicleDrivingQueryForm',{
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
	items : [{
					xtype:'commonvehicledrivingselector',
					fieldLabel:'长途车队名称',
					name:'longHaulFleetCode',
					labelWidth :90,
					columnWidth:.27
				},{
					xtype : 'container',
					columnWidth : 1,
					defaultType:'button',
					layout:'column',
					items : [{
						xtype : 'button', 
						text:baseinfo.vehicledriving.i18n('foss.baseinfo.reset'),//重置
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
						text : baseinfo.vehicledriving.i18n('foss.baseinfo.query'),//查询
						cls:'yellow_button',
						handler : function() {
							var form=this.up('form');
						
							baseinfo.vehicledriving.vehicleDrivingQuery(form);
						}
					}]
				
				}]			
});

//配置新增重分类基础信息FORM
Ext.define('Foss.baseinfo.vehicledriving.VehicleDrivingConfigForm',{
	extend:'Ext.form.Panel',
	title:'长途车队信息',
	frame:true,
	height : 300,
	collapsible: true,
	defaults : {
		margin : '5 15 5 25',
		labelWidth : 145
	},
	defaultType : 'textfield',
	layout: {
        type: 'table',
        columns: 1
    },
	constructor : function(config) { // 构造器
					var me = this, cfg = Ext.apply({}, config);
				    me.items = [{
								xtype:'dynamicorgcombselector',
								fieldLabel:'长途车队',
								name:'longHaulFleetCode',
								allowBlank: false,
								columnWidth : 0.6
							},{ 
								xtype:'dynamicorgcombselector',
								fieldLabel:'下辖部门',
								name:'departmentCode',
								allowBlank: false,
								columnWidth:0.6
							
							},{
								name : 'trafficCode',
								fieldLabel : '行车编码简称',
								allowBlank: false,
								emptyText : '自定义行车编码缩写。',
								columnWidth:0.6
							
							}];
						
						me.callParent([cfg]);
					}
		
});
/**
 * 声明新增长途车队信息windows
 */
Ext.define('Foss.baseinfo.vehicledriving.addVehicleDrivingWindow', {
	extend : 'Ext.window.Window',
	id : 'Foss_baseinfo_vehicledriving_addVehicleDrivingWindow_Id',
	title : '新增长途车队信息',
	width : 700,
	height : 400,
	modal : true,
	closeAction : 'hidden',
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.items = [
		            Ext.create('Foss.baseinfo.vehicledriving.VehicleDrivingConfigForm')
		            ];
		me.dockedItems = [{
			xtype: 'toolbar',
		    dock: 'bottom',
		    layout:'column',		    	
		    defaults:{
				margin:'0 0 5 3'
			},	
			 items: [{
				 	xtype:'button',
					text:'返回列表',
					columnWidth:.1,
					handler : function() {
						//获取当前win
						var a_win = Ext.getCmp('Foss_baseinfo_vehicledriving_addVehicleDrivingWindow_Id');
						if(!Ext.isEmpty(a_win)){
							//长途车队信息FORM清空数据
							var vehicleDrivingConfigForm = a_win.items.items[0];
							vehicleDrivingConfigForm.getForm().reset();
							//销毁当前win
							a_win.hide();
						}	
					}
				},{
					xtype:'container',
					border:false,
					html:'&nbsp;',
					columnWidth:.7
				},{
				 	xtype:'button',
					text:'重置',
					columnWidth:.1,
					handler : function() {
						//获取当前win
						var a_win = Ext.getCmp('Foss_baseinfo_vehicledriving_addVehicleDrivingWindow_Id');
						if(!Ext.isEmpty(a_win)){
							//长途车队信息FORM清空数据
							var vehicleDrivingConfigForm = a_win.items.items[0];
							vehicleDrivingConfigForm.getForm().reset();
							
						}	
					}
				},{
					xtype:'button',
					//cls : 'yellow_button',
					text : '提交',
					itemId:'addVehicleDrivingWindow_submit_ID',
					columnWidth:.1,
					handler : function() {
						
						//获取当前win
						var a_win = Ext.getCmp('Foss_baseinfo_vehicledriving_addVehicleDrivingWindow_Id');
						if(!Ext.isEmpty(a_win)){
							var model =new Foss.baseinfo.vehicledriving.VehicleDrivingModel();
							//长途车队信息FORM数据
							var vehicleDrivingConfigForm = a_win.items.items[0];
							if(vehicleDrivingConfigForm.getForm().isValid()){
								vehicleDrivingConfigForm.getForm().updateRecord(model);
								var longHaulFleetCode = vehicleDrivingConfigForm.getForm().findField('longHaulFleetCode').getValue();
								var departmentCode = vehicleDrivingConfigForm.getForm().findField('departmentCode').getValue();
								var trafficCode = vehicleDrivingConfigForm.getForm().findField('trafficCode').getValue();
								
								
								//把数据传到后台
								model.set('longHaulFleetCode',longHaulFleetCode);
								model.set('departmentCode',departmentCode);
								model.set('trafficCode',trafficCode);
								
							// 设置参数
								var vehicleDrivingGrid = Ext.getCmp('T_baseinfo-vehicleDriving_content').getVehicleDrivingGrid();
									var params = vehicleDrivingGrid.store.getSubmitParams();
									if(params==null||params==''){
										params = {};
										Ext.apply(params, {
											'vehicleDrivingVo.vehicleDrivingQueryDto.longHaulFleetCode' : null,
										});
										vehicleDrivingGrid.store.setSubmitParams(params);
									}
									
									// 调用提交后台保存
									Ext.Ajax.request({
										url:baseinfo.realPath('addVehicleDriving.action'),//新增action  参数vehicleDrivingEntity
										jsonData:{'vehicleDrivingVo':{'vehicleDrivingEntity':model.data}},
										method:'post',
										success:function(response){
											
											//重置form
											vehicleDrivingConfigForm.getForm().reset();
											//将win隐藏
											a_win.hide();
											
											//重新查询数据
											vehicleDrivingGrid.store.loadPage(1,{
											      callback: function(records, operation, success) {
											    	  
											    	//抛出异常  
												    var rawData = vehicleDrivingGrid.store.proxy.reader.rawData;
												    if(!success && ! rawData.isException){
												    	Ext.MessageBox.show({
															title : '温馨提示',
															msg : rawData.message,
															width : 300,
															buttons : Ext.Msg.OK,
															icon : Ext.MessageBox.WARNING
														});
														return false;
													}  
											    	//正常返回
											    	if(success){
											    		var result = Ext.decode(operation.response.responseText);
											    		vehicleDrivingGrid.show();
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
						}		
					}
				}]
		}];		
		me.callParent([cfg]);
	}
})

//长途车队信息列表
Ext.define('Foss.baseinfo.vehicledriving.VehicleDrivingGrid',{
	extend:'Ext.grid.Panel',
	id:'Foss_baseinfo_vehicledriving_ExportExcelWin_Id',
    title: '长途车队明细',
    frame:true,
	height:500,
	selModel:Ext.create('Ext.selection.CheckboxModel'),
    store: Ext.create('Foss.baseinfo.vehicledriving.VehicleDrivingStore'),
	columns:[{
		header: '长途车队名称', 
		width : 180,
		dataIndex: 'longHaulFleetName'
	},{
		
		header: '下辖部门名称',
		width : 180,
		dataIndex: 'departmentName'
	},{
		header: '行车编码简称',
		width : 180,
		dataIndex: 'trafficCode'
	},{
		header: '修改人编码', 
		dataIndex: 'modifyUser',
		hidden:true
	},{
		
		header: '长途车队编码',
		dataIndex: 'longHaulFleetCode',
		hidden:true
	},{
		header: '下辖部门编码', 
		dataIndex: 'departmentCode',
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
				disabled:!baseinfo.vehicledriving.isPermission('baseinfo/addvehicleDriving'),
				hidden:!baseinfo.vehicledriving.isPermission('baseinfo/addvehicleDriving'),
				handler : function(grid, rowIndex, colIndex) {

					var a_win = Ext.getCmp('Foss_baseinfo_vehicledriving_addVehicleDrivingWindow_Id');
					if (Ext.isEmpty(a_win)) {
						a_win = Ext.create('Foss.baseinfo.vehicledriving.addVehicleDrivingWindow');
					}
					var vehicleDrivingConfigForm = a_win.items.items[0];
					//重置form
					vehicleDrivingConfigForm.getForm().reset();
					//作废win窗体的按钮
					a_win.getDockedItems('toolbar[dock="bottom"]')[0].getComponent('addVehicleDrivingWindow_submit_ID').setVisible(true);
					a_win.show();
				}
			},{
				xtype :'button',
				text : '作废',
				columnWidth :.08,
				disabled:!baseinfo.vehicledriving.isPermission('baseinfo/deleteVehicleDriving'),
				hidden:!baseinfo.vehicledriving.isPermission('baseinfo/deleteVehicleDriving'),
				handler : function(grid, rowIndex, colIndex) {

					// grid
					var grid = Ext.getCmp('T_baseinfo-vehicleDriving_content').getVehicleDrivingGrid();
					
					// 获取选中的数据
					var selections = grid.getSelectionModel().getSelection();
					
					// 如果未选中数据，提示至少选择一条记录进行操作
					if(selections.length==0){
						Ext.MessageBox.show({
							title : '温馨提示',
							msg : '请至少选择一条数据进行操作',
							width : 300,
							buttons : Ext.Msg.OK,
							icon : Ext.MessageBox.WARNING
						});
						
						return false;
					}
					
					var selectDeleteIds = [];
					for(var i=0;i<selections.length;i++){
						selectDeleteIds.push(selections[i].get('id'));
					}
					
					var yesFn=function(){
						// 调用
				 		Ext.Ajax.request({
				 			url:baseinfo.realPath('deleteVehicleDriving.action'),//作废 action 参数 selectedIds
				 			params:{
				 				'vehicleDrivingVo.vehicleDrivingQueryDto.selectedIds':selectDeleteIds
				 			},
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
				 							return false;
				 						}  
				 				    	
				 				    	//正常返回
				 				    	if(success){
				 				    		var result =   Ext.decode(operation.response.responseText);
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
					baseinfo.vehicledriving.vehicleDrivingConfirmAlert('确认是否要作废长途车队信息',yesFn,noFn);

				}
				},{	
				xtype:'container',
				border:false,
				html:'&nbsp;',
				columnWidth:.6
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
	//长途车队FORM
	var vehicleDrivingQueryForm = Ext.create('Foss.baseinfo.vehicledriving.VehicleDrivingQueryForm');
	
	//长途车队grid
	var vehicleDrivingGrid = Ext.create('Foss.baseinfo.vehicledriving.VehicleDrivingGrid');
	
	Ext.create('Ext.panel.Panel', {
		id: 'T_baseinfo-vehicleDriving_content',
		cls : 'panelContentNToolbar',
		bodyCls : 'panelContentNToolbar-body',
		layout : 'auto',
		getVehicleDrivingQueryForm:function(){
			return cvehicleDrivingQueryForm;
		},
		getVehicleDrivingGrid:function(){
			return vehicleDrivingGrid;
		},
		items: [vehicleDrivingQueryForm,vehicleDrivingGrid],
		renderTo : 'T_baseinfo-vehicleDriving-body'
	});
});