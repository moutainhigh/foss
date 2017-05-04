// 定义上方form
Ext.define('Foss.unload.ordertaskmodify.taskAddForm', {
			extend : 'Ext.form.Panel',
			layout : 'column',
			defaults : {
				margin : '5 10 5 10',
				labelWidth : 85,
				columnWidth : 1 / 4
			},
			items : [{
				    xtype : 'textfield',
				    readOnly : true,
				    name : 'orderTaskNo',
				    fieldLabel : '点单任务编号'
				}, {
					fieldLabel : '车牌号',
					name : 'vehicleNo',// ！车牌号
					xtype : 'textfield'
				}, {
					xtype : 'container',
					html : '&nbsp;'
				}, {
					xtype : 'button',
					text : '添加运单',
					columnWidth :.10,
					id : 'Foss_ununload_ununloadtaskmodify_addButton_ID',
					cls : 'btnAfterTextfield',
					handler : function(){
					unload.moreSerialNoGrid.store.removeAll();
					//显示窗口
			        //if(unload.addMoreGoodsWindow == null){
				    unload.addMoreGoodsWindow = Ext.create('Foss.unload.ordertaskmodify.addMoreGoodsWindow');
			         // }
			          unload.addMoreGoodsWindow.show();
					}
				}, {
					xtype : 'textfield',
					readOnly : true,
					name : 'totWaybillQty',
					fieldLabel : '总票数'
				}, {
					xtype : 'textfield',
					readOnly : true,
					name : 'totGoodsQty',
					fieldLabel : '总件数'
				}, {
					xtype : 'textfield',
					readOnly : true,
					name : 'totWeight',
					fieldLabel : '总重量'
				}, {
					xtype : 'textfield',
					readOnly : true,
					name : 'totVolume',
					fieldLabel : '总体积'
				}, {
				  name:'isHandle',
				  xtype : 'textfield',
				  hidden:true
				}, {
				  name:'orderCode',
				  xtype : 'textfield',
				  hidden:true
					}],
			constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				me.callParent([cfg]);
			}
		});

		
//添加多货运单号表单
 Ext.define('Foss.unload.ordertaskmodify.addMoreGoodsForm', {
	extend : 'Ext.form.Panel',
	layout : 'column',
	defaults : {
				margin : '5 10 5 10',
				labelWidth : 50
			},
	items : [{
		xtype : 'textfield',
		fieldLabel: '运单号',
		name: 'waybillNo',
		allowBlank : false,
		columnWidth :.50
	}, {
		xtype : 'container',
		columnWidth : .05,
		html : '&nbsp;'
	}, {
		xtype : 'button',
		text : '查询',
		cls : 'btnAfterTextfield',
		columnWidth :.20,
		handler : function(){
			var form = this.up('form').getForm();
			//获取运单号
			var waybillNoCmp = form.findField('waybillNo');
			if(Ext.isEmpty(waybillNoCmp.getValue())){
							Ext.ux.Toast.msg(unload.i18n('foss.unload.ordertaskaddnew.alertInfo.alertTitle'), 
									'请输入运单号！', 
									'error', 2000);
							waybillNoCmp.reset();
							waybillNoCmp.focus()
						}else{
							var store = unload.moreSerialNoGrid.store;
							Ext.Ajax.request({
								url : unload.realPath('queryValidateWaybillNoAndSerialNo.action'),
								params : {'orderTaskVo.waybillNo': waybillNoCmp.getValue()},
								success : function(response){
									var result = Ext.decode(response.responseText);
									//获取流水号
									var serialNoInfoList = result.orderTaskVo.serialNoInfoList;
									if(serialNoInfoList == null){
										Ext.ux.Toast.msg('提示', '添加失败，运单号不存在！', 'error', 2000);
										waybillNoCmp.focus();
										return;
									}
									//单据列表赋值
			                      store.loadData(serialNoInfoList);
			                      waybillNoCmp.focus();
								}
							});
						}
					}
				}]
});
		
// 定义多货列表之Model
Ext.define('Foss.unload.ordertaskmodify.moreWaybillModel', {
           extend : 'Ext.data.Model',
           fields : [{
           	            name : 'serialNo',// 多货流水号
						type : 'string'
           }, {        
           	            name : 'waybillNo',// 运单号
						type : 'string'
           }, {
           	            name : 'transportType',// 运输性质
						type : 'string'
           },{
           	            name : 'createBillQty',// 开单件数
						type : 'number'
           },{
                        name : 'orderReportType',// 点单差异类型
						type : 'string'
           }]
});


// 定义多货列表之store
Ext.define('Foss.unload.ordertaskmodify.moreWaybillStore', {
			extend : 'Ext.data.Store',
			// 绑定一个模型
			model : 'Foss.unload.ordertaskmodify.moreWaybillModel',
			constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				me.callParent([cfg]);
			}
		});

// 定义交接单明细列表之Model
Ext.define('Foss.unload.ordertaskmodify.taskInfoModel', {
			extend : 'Ext.data.Model',
			// 定义字段
			fields : [
					{
						name : 'id',
						type : 'string'
					},{
				        name : 'handoverNo',// 交接单号
				        type : 'string'
			        }, {
						name : 'waybillNo',// 运单号
						type : 'string'
					}, {
						name : 'transportType',// 运输性质
						type : 'string'
					}, {
						name : 'createBillQty',// 开单件数
						type : 'number'
					}, {
						name : 'alAssembleQty',// 已配件数
						type : 'number'
					}, {
						name : 'alAssembleWeight',// 已配重量
						type : 'number'
					}, {
						name : 'alAssembleVolume',// 已配体积
						type : 'number'
					}, {
						name : 'orderGoodsQty',// 点单件数
						type : 'number'
					}, {
	                    name : 'isunload',//判断是否加载
	                    type : 'string', 
	                    defaultValue: '0'
	   }]
		});

Ext.define('Foss.Queryunloadtask.QueryForm.State.Model', {
	extend: 'Ext.data.Model',
	//定义字段
	fields: [
		{name: 'code',  type: 'string'},
		{name: 'name',  type: 'string'}
	]
});
		
		
//点单差异类型
Ext.define('Foss.Queryunloadtask.QueryForm.State.Store', {
	extend: 'Ext.data.Store',
	model: 'Foss.Queryunloadtask.QueryForm.State.Model',
	data: {
				'items':[
					{'code':'LOSE','name':unload.i18n('foss.unload.ordertaskmodify.ordeReportType.lose')},
					{'code':'NORMAL','name':unload.i18n('foss.unload.ordertaskmodify.ordeReportType.normal')}
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



//多货点单差异类型
Ext.define('Foss.Queryunloadtask.QueryForm.MoreState.Store', {
	extend: 'Ext.data.Store',
	model: 'Foss.Queryunloadtask.QueryForm.State.Model',
	data: {
				'items':[
					{'code':'MORE','name':unload.i18n('foss.unload.ordertaskmodify.ordeReportType.more')},
					{'code':'NORMAL','name':unload.i18n('foss.unload.ordertaskmodify.ordeReportType.normal')}
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

// 定义交接单明细列表之store
Ext.define('Foss.unload.ordertaskmodify.taskInfoStore', {
			extend : 'Ext.data.Store',
			// 绑定一个模型
			model : 'Foss.unload.ordertaskmodify.taskInfoModel',
			constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				me.callParent([cfg]);
			}
		});


//定义运单明细Model
Ext.define('Foss.unload.ordertaskmodify.WaybillDetailModel', {
	extend : 'Ext.data.Model',
	//定义字段
	fields: [{
		name : 'id',
		type : 'string'
	},{
		name : 'waybillNo',
		type : 'string'
	},{
		name : 'handoverNo',
		type : 'string'
	},{
		name : 'serialNo',
		type : 'string'
	},{
		name : 'orderReportType',
		type : 'string'
	}]
});



// 定义交接单明细列表之store
Ext.define('Foss.unload.ordertaskmodify.WaybillDetailStore', {
			extend : 'Ext.data.Store',
			// 绑定一个模型
			model : 'Foss.unload.ordertaskmodify.WaybillDetailModel',
			constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				me.callParent([cfg]);
			}
		});


var cellEditing = Ext.create('Ext.grid.plugin.CellEditing', {
    clicksToEdit: 1
});

var orderTypeStore = Ext.create('Foss.Queryunloadtask.QueryForm.MoreState.Store');

//定义多货流水之grid
Ext.define('Foss.unload.ordertaskmodify.moreSerialNoGrid', {
    extend : 'Ext.grid.Panel',
    //enableColumnHide : false,// 配置该属性可取消grid自定义显示列功能
    autoScroll : true,
    height: 300,
    plugins: [cellEditing],
	viewConfig: {
		//处理行勾选事件
		getRowClass: function(record, rowIndex, rowParams, store) {
			//console.log(unload.taskInfoGrid.store);
			var list  = unload.map.get(record.data.waybillNo+"@"+record.data.serialNo);
			if(list != null){
			      return 'disabledrow';		
			}
			var serialNoDetailList = null;
			Ext.Ajax.request({
				url: unload.realPath('queryOrderTaskMoreSerialNoListByNo.action'),
				method: "GET",
				async: false, 
				params : {
						'orderTaskVo.waybillNo' : record.data.waybillNo,
						'orderTaskVo.orderTaskNo' : orderTaskNoForMore
					},
				success:function(response){
					var orderTaskVo = Ext.decode(response.responseText).orderTaskVo;
					serialNoDetailList = orderTaskVo.serialNoDetailList;
					selectRowList = serialNoDetailList;
				}
			});
			
			for(var i =0;i<serialNoDetailList.length;i++){
				if(serialNoDetailList[i].serialNo == record.data.serialNo){
					return 'disabledrow';	
				}
			}

		}
	},
	columns :[{
		dataIndex : 'serialNo',
		align : 'center',
		width : 120,
		xtype : 'ellipsiscolumn',
		text : unload
				.i18n('foss.unload.ordertaskaddnew.taskInfoGrid.serialNoColumn') //流水号
	}, {
		dataIndex : 'orderReportType',
		align : 'center',
		width : 120,
		//xtype : 'ellipsiscolumn',
		text : '点单差异类型' ,//点单差异类型
	    renderer : function(value) {
					return unload.i18n('foss.unload.ordertaskmodify.ordeReportType.more');
		}
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
	  	me.store = Ext.create('Foss.unload.ordertaskmodify.moreWaybillStore');
		me.selModel = Ext.create('Ext.selection.CheckboxModel',{
			showHeaderCheckbox : false,
			mode : 'SIMPLE',
			checkOnly : true//限制只有点击checkBox后才能选中行
		});
		me.callParent([ cfg ]);
	}
});


var queryunloadTaskStore = Ext.create('Foss.Queryunloadtask.QueryForm.State.Store');
var serailNoGrid = null;
//定义流水之grid
Ext.define('Foss.unload.ordertaskmodify.serialNoGrid', {
	extend : 'Ext.grid.Panel',
	plugins: [cellEditing],
	//enableColumnHide : false,// 配置该属性可取消grid自定义显示列功能
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.unload.ordertaskmodify.WaybillDetailStore');
		me.callParent([cfg]);
	},
	bindData : function(record){
		var grid = this;
		serailNoGrid = grid;
		var id = record.get('id');
		var handOverBillNo = record.get('handoverNo');
		var waybillNo = record.get('waybillNo');
		var isunload = record.get('isunload');
		if(isunload == '0'){//第一次行展开
			Ext.Ajax.request({
				url: unload.realPath('queryOrderTaskSerialNoListByBillNo.action'),
				params : {
						'orderTaskVo.id' : id
					},
				success:function(response){
					var orderTaskVo = Ext.decode(response.responseText).orderTaskVo;
					var serialNoDetailList = orderTaskVo.serialNoDetailList;
					unload.serialNoGrid.store.loadData(serialNoDetailList);
					grid.store.loadData(serialNoDetailList);
					unload.handleMap.add(handOverBillNo+waybillNo,grid.getStore());
				    var oldSerialNoStore =grid.getStore();
							/**
							 * 点击过一键处理
							 */
					var isHandle=unload.taskAddForm.getForm().findField('isHandle').getValue();				
					  if(isHandle=='Y'){
					 	  for(var i=0;i<oldSerialNoStore.getCount();i++){
					 		if( oldSerialNoStore.data.items[i].data.orderReportType!='MORE'){
					 		 oldSerialNoStore.data.items[i].data.orderReportType = 'NORMAL';
					 		}
					 		oldSerialNoStore.sort('serialNo', 'ASC');
					 	}
					 }
					record.set('isunload','1');
				}
			});
		}else{//第二次行展开
			var bindStore = unload.handleMap.get(handOverBillNo+waybillNo);
			 grid.store.loadData(bindStore.data.items);
	 	}
		 
	},
	columns : [{
		dataIndex : 'serialNo',
		align : 'center',
		width : 120,
		text : unload
				.i18n('foss.unload.ordertaskaddnew.taskInfoGrid.serialNoColumn') //流水号
	}, {
		dataIndex : 'orderReportType',
		align : 'center',
		width : 120,
		text : '点单差异类型' ,//点单差异类型
		renderer : function(value) {
			switch(value) {
				case 'LOSE':
					return unload.i18n('foss.unload.ordertaskmodify.ordeReportType.lose');
				case 'NORMAL':
					return unload.i18n('foss.unload.ordertaskmodify.ordeReportType.normal');
				case 'MORE':
					return unload.i18n('foss.unload.ordertaskmodify.ordeReportType.more');
				default: return value;
			}
		}
		
	 }],
	listeners:{ 
		itemdblclick : function(view,record,item,index,e,eOpts){
			unload.isunload = record.data;
			storeBeforeUpdate=record.store;
		 if(unload.addWindow == null && record.data.orderReportType != 'MORE'){
				    unload.addWindow = Ext.create('Ext.window.Window',{
				    	modal : true,
				    	closeAction : 'hide',
				    	//closeAction :'destroy',
				    	width : 300,
				    	height : 100,
				    	layout : 'auto',	
				    	listeners: {
				    		'close':function(){
				    			serailNoGrid.getView().refresh(); 
				    		}
				    	},	
				    	items : [{
				    				xtype: 'combo',
				    				id :'combo_id',
				    				name:'orderReportType',
				    				fieldLabel:'点单差异类型',
				    				columnWidth:.25,
				    				displayField: 'name',
				    				valueField:'code', 
				    				queryMode:'local',
				    				triggerAction:'all',
				    				value:'',
				    				editable:false,
				    				store: queryunloadTaskStore,
				    				listeners : { 
				    						select : function(combo, record, index) {
				    							    var a = Ext.getCmp('combo_id');
				    							    a.clearValue();
				    								var handoverNo=unload.isunload.handoverNo;
				    								var waybillNo= unload.isunload.waybillNo;
				    								var handleMapStore = unload.handleMap.get(handoverNo+waybillNo);
				    								if(unload.isunload.orderReportType != record[0].data.code){
				    									if(record != null ){
				    							   			 unload.isunload.orderReportType = record[0].data.code;
				    									}
				    									
					    							//正常流水点单件数加1
						    							var serialInfoId = unload.isunload.id;
						    							var billInfoId = unload.isunload.handoverNo+"@"+unload.isunload.waybillNo;
						    							var billRecord = unload.taskInfoGrid.store.data;
						    							if(record[0].data.code == 'NORMAL'){
						    								//*** 处理流水表信息 ****//* 
						    								if(unload.addedSerialNoMap.get(serialInfoId)==null){
						    									unload.addedSerialNoMap.add(serialInfoId,unload.isunload);
						    									//**处理主表信息**//*
						    									//新增
						    									if(unload.addedBillMap.get(billInfoId)==null){
						    										var bean = unload.oldBillMap.get(billInfoId);
						    										bean.orderGoodsQty = bean.orderGoodsQty+1;
						    										unload.addedBillMap.add(billInfoId,bean);
						    										for(var i=0 ;i<billRecord.length;i++){
						    											if(billRecord.get(i).data.id == bean.id){
						    												billRecord.get(i).set('orderGoodsQty',bean.orderGoodsQty);
						    												break;
						    											}
						    										}
						    									}
						    									//更新
						    									else{
						    										var bean = unload.addedBillMap.get(billInfoId);
						    										bean.orderGoodsQty = bean.orderGoodsQty+1;
						    										for(var i=0 ;i<billRecord.length;i++){
						    											if(billRecord.get(i).data.id == bean.id){
						    												billRecord.get(i).set('orderGoodsQty',bean.orderGoodsQty);
						    												break;
						    											}
						    										}
						    									}
						    								}
						    							}
					    							else if(record[0].data.code == 'LOSE'){
					    								if(unload.addedSerialNoMap.get(serialInfoId)==null){
					    									unload.addedSerialNoMap.add(serialInfoId,unload.isunload);
					    									//**处理主表信息**//*
					    									//新增
					    									if(unload.addedBillMap.get(billInfoId)==null){
					    										var bean = unload.oldBillMap.get(billInfoId);
					    										bean.orderGoodsQty = bean.orderGoodsQty-1;
					    										unload.addedBillMap.add(billInfoId,bean);
					    										for(var i=0 ;i<billRecord.length;i++){
					    											if(billRecord.get(i).data.id == bean.id){
					    												billRecord.get(i).set('orderGoodsQty',bean.orderGoodsQty);
					    												break;
					    											}
					    										}
					    									}
					    									//更新
					    									else{
					    										var bean = unload.addedBillMap.get(billInfoId);
					    										bean.orderGoodsQty = bean.orderGoodsQty-1;
					    										for(var i=0 ;i<billRecord.length;i++){
					    											if(billRecord.get(i).data.id == bean.id){
					    												billRecord.get(i).set('orderGoodsQty',bean.orderGoodsQty);
					    												break;
					    											}
					    										}
					    									}
					    								}else{

					    									//删除流水表集合记录
					    									unload.addedSerialNoMap.removeAtKey(serialInfoId);
					    									//**处理主表信息**//*
					    									if(unload.addedBillMap.get(billInfoId)!=null){
					    										var bean = unload.addedBillMap.get(billInfoId);
					    										bean.orderGoodsQty = bean.orderGoodsQty-1;
					    										for(var i=0 ;i<billRecord.length;i++){
					    											if(billRecord.get(i).data.id == bean.id){
					    												billRecord.get(i).set('orderGoodsQty',bean.orderGoodsQty);
					    												break;
					    											}
					    										}
					    									}
					    								
					    								}
					    	
					    							}else{
					    								//*** 处理流水表信息 ****//* 
					    								if(unload.addedSerialNoMap.get(serialInfoId)!=null){
					    									//删除流水表集合记录
					    									unload.addedSerialNoMap.removeAtKey(serialInfoId);
					    									//**处理主表信息**//*
					    									if(unload.addedBillMap.get(billInfoId)!=null){
					    										var bean = unload.addedBillMap.get(billInfoId);
					    										//bean.orderGoodsQty = bean.orderGoodsQty-1;
					    										for(var i=0 ;i<billRecord.length;i++){
					    											if(billRecord.get(i).data.id == bean.id){
					    												billRecord.get(i).set('orderGoodsQty',bean.orderGoodsQty);
					    												break;
					    											}
					    										}
					    									}
					    								}
					    								
					    							}
					    						
				    						}
				    						
				    						storeBeforeUpdate.loadData(handleMapStore.data.items);				
				    						unload.addWindow.close();
				    						}
				    					}
				    	    }]

				    });
				    
			          }
			 if(record.data.orderReportType != 'MORE'){
			 unload.addWindow.show();
			 }
			          
		}
		   }
});

// 定义下方列表之grid
Ext.define('Foss.unload.ordertaskmodify.taskInfoGrid', {
	extend : 'Ext.grid.Panel',
	//id : 'Foss_Order_taskInfoGrid_Id',
	//enableColumnHide : false,// 配置该属性可取消grid自定义显示列功能
	autoScroll : true,
	height : 550,
	cls : 'autoHeight',
	deferRowRender:false,
	columnLines: true,
	autoScroll : true,
	collapsible : true,
	animCollapse : true,
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.unload.ordertaskmodify.taskInfoStore'),
		me.callParent([cfg]);
	},
	// 定义行展开
	plugins : [ {
		header: true,
		ptype : 'rowexpander',
		// 定义行展开模式（单行与多行），默认是多行展开(值true)
		//pluginId : 'handOverBillModify_unsubmitedWaybillGrid_serialNoGrid',
		rowsExpander : false,
		// 行体内容
		rowBodyElement : 'Foss.unload.ordertaskmodify.serialNoGrid'
	}],
	columns : [{
		dataIndex : 'handoverNo',
		align : 'center',
		flex : 1,
		xtype : 'ellipsiscolumn',
		text : unload
				.i18n('foss.unload.ordertaskaddnew.taskInfoGrid.handoverNoColumn')
	}, {
		dataIndex : 'waybillNo',
		align : 'center',
		flex : 1,
		xtype : 'ellipsiscolumn',
		text : unload
				.i18n('foss.unload.ordertaskaddnew.taskInfoGrid.waybillNoColumn')
	}, {
		dataIndex : 'transportType',
		align : 'center',
		flex : 1,
		text : unload
				.i18n('foss.unload.ordertaskaddnew.taskInfoGrid.transportTypeColumn')
	}, {
		dataIndex : 'createBillQty',
		align : 'center',
		flex : 1,
		text : unload
				.i18n('foss.unload.ordertaskaddnew.taskInfoGrid.createBillQty')
	}, {
		dataIndex : 'alAssembleQty',
		align : 'center',
		flex : 1,
		text : unload
				.i18n('foss.unload.ordertaskaddnew.taskInfoGrid.alAssembleQty')
	}, {
		dataIndex : 'alAssembleWeight',
		align : 'center',
		flex : 1,
		text : unload
				.i18n('foss.unload.ordertaskaddnew.taskInfoGrid.alAssembleWeight')
	}, {
		dataIndex : 'alAssembleVolume',
		align : 'center',
		flex : 1,
		text : unload
				.i18n('foss.unload.ordertaskaddnew.taskInfoGrid.alAssembleVolume')
	}, {
		dataIndex : 'orderGoodsQty',//点单件数
		align : 'center',
		flex : 1,
		text : '点单件数'
	}, {
		dataIndex : 'isunload',//判断是否加载流水号
		align : 'center',
		width : 120,
		xtype : 'ellipsiscolumn',
		hidden:true
	}]
});


// 上方form
unload.taskAddForm = Ext.create('Foss.unload.ordertaskmodify.taskAddForm');
// 下方grid
unload.taskInfoGrid = Ext.create('Foss.unload.ordertaskmodify.taskInfoGrid');
//流水Grid	
unload.serialNoGrid = Ext.create('Foss.unload.ordertaskmodify.serialNoGrid');
//多货流水Grid	
unload.moreSerialNoGrid = Ext.create('Foss.unload.ordertaskmodify.moreSerialNoGrid');       
        
		
// 定义上方控件Panel
Ext.define('Foss.unload.ordertaskmodify.taskInfoPanel', {
			extend : 'Ext.panel.Panel',
			title : unload.i18n('Foss.unload.ordertaskaddnew.taskInfoForm.title'),
			layout : 'auto',
			frame : true,
			collapsible : true,
			animCollapse : true,
			items : [
					unload.taskAddForm, 
					unload.taskInfoGrid
			]
		});
		
Ext.define('Foss.unload.ordertaskmodify.addMoreGoodsWindow', {
		extend : 'Ext.window.Window',
		title : '添加运单',
		modal : true,
		closeAction : 'hide',
		width : 400,
		height : 500,
		layout : 'auto',	
		items : [
		Ext.create('Foss.unload.ordertaskmodify.addMoreGoodsForm'),
		unload.moreSerialNoGrid
		],
		buttons : [{
			text : '添加',
			margin : '0 12 0 0',
			handler : function(but){
			   
			    but.setDisabled(true);//不可点击
	            setTimeout(function(){
	            	but.setDisabled(false);//恢复可点击
	            }, 3000);
                //勾选的流水
				moreSerialNoRecords = unload.moreSerialNoGrid.getSelectionModel().getSelection();
				if(moreSerialNoRecords.length!=0){ 
					var billRecord = Ext.ModelManager.create(moreSerialNoRecords[0].data, 'Foss.unload.ordertaskmodify.taskInfoModel');
					var moreSerialNoList = new Array();
		            for(var i=0; i<moreSerialNoRecords.length;i++){
		            	//将添加多货的已配重量 ，体积置为空值
			            moreSerialNoRecords[i].data.orderReportType = 'MORE';
			            billRecord.data.alAssembleWeight = '';
			            billRecord.data.alAssembleQty = '';
			            billRecord.data.alAssembleVolume = '';  
			            unload.addedMoreBillMap.add(billRecord.data.waybillNo,billRecord.data);
			            unload.map.add(billRecord.data.waybillNo+"@"+moreSerialNoRecords[i].data.serialNo,moreSerialNoRecords[i]);
			            //添加后可以多货行展开
			            billRecord.data.isunload = '0';
		            }
		            
		            //避免勾选的流水跟已经添加过的流水有重复
		            for(var i=0; i<moreSerialNoRecords.length;i++){ 
		            	var flag = false;
		            	for(var p=0;p<selectRowList.length;p++){//跟置灰流水进行对比 ,如果有流水号相等，则不push
		            		if(moreSerialNoRecords[i].data.serialNo == selectRowList[p].serialNo){
		            			flag = true;
		            			break;       
		            	   }	
		             }
		            	if(!flag){
		            		moreSerialNoList.push(moreSerialNoRecords[i].data);
		            	}   
		            }
		            
		            var order = false;
		            var orderGoodsQty;
		            for(var k=0 ;k<unload.taskInfoGrid.store.data.length;k++){
		            	//如果该运单已经添加过多货，则将多货的流水挂在已经添加过得运单下
		            	if((unload.taskInfoGrid.store.data.getAt(k).data.waybillNo == billRecord.data.waybillNo) && 
		            			(unload.taskInfoGrid.store.data.getAt(k).data.handoverNo == billRecord.data.handoverNo)){
		            		//已经添加过多货的运单的点单件数
		            		orderGoodsQty = unload.taskInfoGrid.store.data.getAt(k).data.orderGoodsQty
		            		//对应的添加过的运单的点单件数加上勾选的流水数  是添加后的点单件数
		            		unload.taskInfoGrid.store.data.getAt(k).data.orderGoodsQty += moreSerialNoList.length;
		            		//插入的多货运单信息第一次行展开
		            		unload.taskInfoGrid.store.data.getAt(k).data.isunload = '0';
		            		//已经添加过
		            		order=true;
		            	}
		            }
		            
		            if(order){//order是true 则该运单已经添加过 
		            	//点单件数等于已经添加过的多货运单的点单件数加上现在勾选的流水数
		            	billRecord.data.orderGoodsQty = orderGoodsQty +moreSerialNoList.length;
		            }else{
		            	//该运单没有添加过点单件数就等于勾选的流水数
		            	billRecord.data.orderGoodsQty += moreSerialNoList.length;
		            }
		            var taskForm = unload.taskAddForm.getForm();
		            //总票数加1
					var totWaybillQty = taskForm.findField('totWaybillQty').getValue();
					var totWaybillQty = Number(totWaybillQty)+1;
				    taskForm.findField('totWaybillQty').setValue(totWaybillQty);
		            
				    if(moreSerialNoList.length == 0){
		            	Ext.ux.Toast.msg('提示', '请勾选多货流水！', 'info', 1000);
		            }else{
		            var data = {
							'orderTaskVo' : {
								'orderTaskMoreDto' : {
									'totWaybillQty' : totWaybillQty,
								    'moreSerialNoList':moreSerialNoList,
								    'moreBillInfo' : billRecord.data,
								    'orderTaskNo' : unload.orderTaskNo
								}
							}
						};
						
		            Ext.Ajax.request({
									url : unload.realPath('insertMoreSerialNo.action'),
									jsonData : data,
									success : function(response){
											var orderTaskVo = Ext.decode(response.responseText).orderTaskVo;
											var id = orderTaskVo.id;
											var a = true;
											billRecord.data.id = id;
											for(var j = 0;j<unload.taskInfoGrid.store.data.length;j++){	
												if(unload.taskInfoGrid.store.data.getAt(j).data.id == id ){
													a = false;
												}
											}
											if(a){//没有添加过该运单
											unload.taskInfoGrid.store.insert(unload.taskInfoGrid.store.getCount(),billRecord); 
											}else{//添加过总票数不增加1
												var totWaybillQty = taskForm.findField('totWaybillQty').getValue();
												var totWaybillQty = Number(totWaybillQty)-1;
											    taskForm.findField('totWaybillQty').setValue(totWaybillQty);
											    unload.taskInfoGrid.getView().refresh(); 
											   
											 
											}
									        unload.addMoreGoodsWindow.close();
									}
								});
		            
		            }
	           
	            }else{
	             Ext.ux.Toast.msg('提示', '请勾选多货流水！', 'info', 1000);
	            }
	            
			}
		}],	
		listeners : {
			'beforehide' : function(cmp,eOpts){
				var form = cmp.items.items[0].getForm();
				form.reset();
			}
		}
});

/*Maps*/
//存储修改前的单据
unload.oldBillMap = new Ext.util.HashMap();
//存储修改后的明细单据
unload.addedBillMap = new Ext.util.HashMap();
//存储修改前的流水信息
unload.oldSerialNoMap = new Ext.util.HashMap();
//存储修改后的流水信息
unload.addedSerialNoMap = new Ext.util.HashMap();
//存储添加的多货单据
unload.addedMoreBillMap = new Ext.util.HashMap();
//一键处理map
unload.handleMap = new Ext.util.HashMap();
unload.map = new Ext.util.HashMap();
unload.addnewMap = new Ext.util.HashMap();
//用于查询多货时的点单任务号 全局变量
var orderTaskNoForMore = null;

//行勾选重复
 var selectRowList = new Array() ;

Ext.onReady(function() {
	unload.isunload=null;
    storeBeforeUpdate=null;
	Ext.create('Ext.panel.Panel', {
		id : 'T_unload-orderTaskmodifyindex_content',
		cls : "panelContentNToolbar",
		bodyCls : 'panelContent-body',
		layout : 'column',
		items : [{
			columnWidth : 1,
			items : [Ext.create('Foss.unload.ordertaskmodify.taskInfoPanel')]
		}, {
			xtype : 'container',
			columnWidth : 1,
			layout : 'column',
			items : [{
						columnWidth : .10,
						xtype : 'button',
						cls : 'yellow_button',// 处理点单任务
						name : 'saveButton',
						id : 'Foss_unload_ordertaskmodify_handleButton_ID',
						text : '一键处理',
						handler : function() {
							var form = unload.taskAddForm.getForm();
							form.findField('isHandle').setValue('Y');
							Ext.Ajax.request({
			                     url : unload.realPath('queryOrderTaskSerialNo.action'),
			                     params : {'orderTaskVo.orderTaskNo' : unload.orderTaskNo},
			                     success : function(response){
			                     	var orderTaskVo = Ext.decode(response.responseText).orderTaskVo;
			                     	var serialNoDetailList = orderTaskVo.serialDetailDtoList;
			                     	for(var i=0;i<serialNoDetailList.length;i++){
			                     		if(serialNoDetailList[i].orderReportType != 'MORE'){
			                     		serialNoDetailList[i].orderReportType = 'NORMAL';
			                     	
				                     	var serialInfoId = serialNoDetailList[i].id;
								        var billInfoId = serialNoDetailList[i].handoverNo+"@"+serialNoDetailList[i].waybillNo;
								        var store = unload.taskInfoGrid.store;	
								        var billRecord = store.data;
			                     		
								        var qty = serialNoDetailList[i].serialcounts;
										//更新流水明细信息
										unload.addedSerialNoMap.add(serialInfoId,serialNoDetailList[i]);
										//更新主表点单件数
										var bean = unload.oldBillMap.get(billInfoId);
										if(bean!=null){
											bean.orderGoodsQty = qty;
											unload.addedBillMap.add(billInfoId,bean);
										}
										//更新前台界面的值
										for(var j=0 ;j<billRecord.length;j++){
											if(billRecord.get(j).data.id == bean.id){
												billRecord.get(j).set('orderGoodsQty',qty);
												break;
											}
										}
			                     		}}
							       
								 }
							});
								
							var oldSerialNoInfo = unload.handleMap.getValues();
							var selecteGrid=unload.taskInfoGrid.getSelectionModel().selected;	
							var record = unload.taskInfoGrid.store.data;
							for(var k = 0;k<record.length;k++){
								var handoverNo = record.getAt(k).data.handoverNo;
								var waybillNo = record.getAt(k).data.waybillNo;
								var oldSerialNoStore = unload.handleMap.get(handoverNo+waybillNo);
								if(oldSerialNoStore!= null){
								for(var p=0;p<oldSerialNoStore.getCount();p++){
									if(oldSerialNoStore.data.items[p].data.orderReportType != 'MORE'){
							 		oldSerialNoStore.data.items[p].data.orderReportType = 'NORMAL';
									}
									oldSerialNoStore.sort('serialNo', 'ASC');
							 	}
								}
								
							}
							
					  }		
					}, {
						columnWidth : .80,
						xtype : 'container',
						html : '&nbsp'
					}, {
						columnWidth : .10,
						xtype : 'button',
						cls : 'yellow_button',// 处理点单任务
						name : 'saveButton',
						id : 'Foss_unload_ordertaskmodify_saveButton_ID',
						text : '点单完毕',
						handler : function() {
						var form = unload.taskAddForm.getForm();
						var vehicleNo = form.findField('vehicleNo').getValue();
						var orderTaskNo = form.findField('orderTaskNo').getValue();
						var totWaybillQty = form.findField('totWaybillQty').getValue();
						var orderCode = form.findField('orderCode').getValue();
						if(Ext.isEmpty(vehicleNo)){
						Ext.ux.Toast.msg(unload.i18n('foss.unload.ordertaskaddnew.alertInfo.alertTitle'), 
										unload.i18n('foss.unload.ordertaskaddnew.alertInfo.vehicleNoNotAllowBlankAlertInfo'), 
								'error', 2000);
								return;
						}
						if(orderCode != unload.empCode){
							Ext.ux.Toast.msg(unload.i18n('foss.unload.ordertaskaddnew.alertInfo.alertTitle'), 
										unload.i18n('foss.unload.ordertaskaddnew.alertInfo.orderManNotSame'), 
								'error', 2000);
								return;
								}
						
					//获取修改的流水号list
						var addedBillListParam = new Array();
						try{
							var addedBillList = unload.addedSerialNoMap.getValues();
							for(var i=0;i<addedBillList.length;i++){
								addedBillListParam.push(addedBillList[i]);
							}
						}catch(e){
							console.log(e);
						}
					//获得修改的单据明细list
						var addedBillDetailList = unload.addedBillMap.getValues();	

					var addedMoreBillList = unload.addedMoreBillMap.getValues();
					//构造jsondata
					var data = {
						'orderTaskVo' : {
							'orderTaskModifyDto' : {
								'vehicleNo' : vehicleNo,
								'orderTaskNo' : orderTaskNo,
								'addedBillList' : addedBillListParam,
								'addedBillDetailList' : addedBillDetailList
							}
						}
					};
						var myMask = new Ext.LoadMask(Ext.getCmp('T_unload-orderTaskmodifyindex_content'),{
		                    msg:"加载中，请稍后..."
			                    });
	                    myMask.show();
	                    Ext.Ajax.request({
	                    url : unload.realPath('updateOrderTask.action'),
	                    jsonData : data,
	                    success : function(response){
						Ext.Msg.show({
							     title : unload.i18n('foss.unload.ordertaskaddnew.alertInfo.alertTitle')/*'提示'*/,
							     msg : '点单成功！',
							     buttons : Ext.Msg.OK,
							     icon : Ext.Msg.INFO
							});
							Ext.getCmp('Foss_unload_ordertaskmodify_saveButton_ID').setDisabled(true);
							Ext.getCmp('Foss_unload_ordertaskmodify_handleButton_ID').setDisabled(true);
							Ext.getCmp('Foss_ununload_ununloadtaskmodify_addButton_ID').setDisabled(true);
							myMask.hide();
						},
						exception : function(response){
							var result = Ext.decode(response.responseText);
					    	top.Ext.MessageBox.alert(unload.i18n('foss.unload.ordertaskaddnew.alertInfo.alertTitle')/*'提示'*/,'操作失败'/*'操作失败，'*/ + result.message);
					    	myMask.hide();
						}
	                    });
						}
					}]
		}],
		renderTo : 'T_unload-orderTaskmodifyindex-body'
	});
	/**加载两部分数据，
	 * 1、基本信息，
	 * 2、单据list，
	 */
	//弹出数据加载，禁止操作
	var myMask = new Ext.LoadMask(Ext.getCmp('T_unload-orderTaskmodifyindex_content'),{
		msg:"加载中，请稍后..."
			});
	myMask.show();
	Ext.Ajax.request({
		url : unload.realPath('loadOrderTaskInfo.action'),
		params : {'orderTaskVo.orderTaskNo' : unload.orderTaskNo},
		success : function(response){
			var orderTaskVo = Ext.decode(response.responseText).orderTaskVo;
			//获取基本信息
			var baseEntity = orderTaskVo.baseEntity;
			orderTaskNoForMore = baseEntity.orderTaskNo;
			//获取列表
			var billDetailList = orderTaskVo.billDetailList;
			
			var form = unload.taskAddForm.getForm();
			//给点单任务编号，总重量，总体积，总件数，总票数赋值
			form.findField('orderTaskNo').setValue(baseEntity.orderTaskNo);
			form.findField('totWaybillQty').setValue(baseEntity.totWaybillQty);
			form.findField('totGoodsQty').setValue(baseEntity.totGoodsQty);
			form.findField('totWeight').setValue(baseEntity.totWeight);
			form.findField('totVolume').setValue(baseEntity.totVolume);
			form.findField('vehicleNo').setValue(baseEntity.vehicleNo);
			form.findField('orderCode').setValue(baseEntity.orderCode);
			form.findField('isHandle').setValue('N');
			//单据列表赋值
			unload.taskInfoGrid.store.loadData(billDetailList);
			unload.moreSerialNoGrid.store.removeAll();
			//将列表中的数据置于Map中
			for(var i in orderTaskVo.billDetailList){
				unload.oldBillMap.add(billDetailList[i].handoverNo+"@"+billDetailList[i].waybillNo,billDetailList[i]);
			}
			myMask.hide();
		}
	});
});