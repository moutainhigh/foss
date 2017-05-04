// 定义上方form
Ext.define('Foss.unload.ordertaskaddnew.taskAddForm', {
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
					xtype : 'textfield',
					readOnly : true,
					name : 'departName',
					fieldLabel : '出发部门'
				}, {
					xtype : 'textfield',
					readOnly : true,
					name : 'arriveName',
					fieldLabel : '到达部门'
				}, {
					name : 'orderCode',// ！点单员
					fieldLabel : '点单员',
					xtype : 'commonemployeeselector',
					deptCode : FossUserContext.getCurrentDeptCode(),
					displayField : 'empName',// 显示名称
					valueField : 'empCode'// 值
				}, {
					fieldLabel : '交接单编号',
					name : 'handoverNo',
					xtype : 'textfield'
				}, {
					xtype : 'button',
					text : unload
							.i18n('foss.unload.ordertaskaddnew.assignOrderTaskForm.addassignOrderTaskButtonText'),// 添加
					columnWidth : .10,
					cls : 'btnAfterTextfield',
					handler : function() {
						var form = this.up('form').getForm();
						//获取交接单编号控件
						var handoverNoCmp = form.findField('handoverNo');
						if(Ext.isEmpty(handoverNoCmp.getValue())){
							Ext.ux.Toast.msg(unload.i18n('foss.unload.ordertaskaddnew.alertInfo.alertTitle'), 
									unload.i18n('foss.unload.ordertaskaddnew.alertInfo.inputBillNoAlertInfo'), 
									'error', 2000);
							handoverNoCmp.reset();
							handoverNoCmp.focus();
						}else{
							var store = unload.taskInfoGrid.store;
							if(store.findRecord('handoverNo',handoverNoCmp.getValue(),0,false,true,true) != null){
								Ext.ux.Toast.msg(unload.i18n('foss.unload.ordertaskaddnew.alertInfo.alertTitle'), 
										unload.i18n('foss.unload.ordertaskaddnew.alertInfo.billAlreadyAddedAlertInfo'), 
								'error', 2000);
								handoverNoCmp.focus();
								return;
							}
							//获取交接单信息
							Ext.Ajax.request({
								url : unload.realPath('queryBillInfoByHandoverNo.action'),
								params : {'orderTaskVo.handoverNo': handoverNoCmp.getValue()},
								success : function(response){
									var result = Ext.decode(response.responseText);
									//获取交接单信息
									var billList = result.orderTaskVo.billList;
									var billInfo = result.orderTaskVo.billInfo;
									if(billList == null){
										Ext.ux.Toast.msg(unload.i18n('foss.unload.ordertaskaddnew.alertInfo.alertTitle'), 
												unload.i18n('foss.unload.ordertaskaddnew.alertInfo.billNotFoundAlertInfo'), 
										'error', 2500);
										handoverNoCmp.focus();
										//myMask.hide();
										return;
									}
									if(store.getCount() != 0){
										var record = store.getAt(0);
										if((record.get('departName') != billInfo.departName) && (record.get('arriveName') != billInfo.arriveName)){
											Ext.ux.Toast.msg(unload.i18n('foss.unload.ordertaskaddnew.alertInfo.alertTitle'), 
													unload.i18n('foss.unload.ordertaskaddnew.alertInfo.billDepartNameNotSameAlertInfo'), 
													'error', 2500);
											        handoverNoCmp.focus();
													//myMask.hide();
													return;
										}
										
									}
								
									for(var i=0; i<billList.length;i++){
									//create一条model
									var billRecord = Ext.ModelManager.create(billList[i], 'Foss.unload.ordertaskaddnew.taskInfoModel');
									//插入列表
									store.insert(store.getCount(),billRecord);
									//填充出发部门
									form.findField('departName').setValue(billList[i].departName);
									//填充到达部门
									form.findField('arriveName').setValue(billList[i].arriveName);
									
									}
									//回到单据编号
									handoverNoCmp.focus();
									handoverNoCmp.reset();
									
									var waybillQtyTotal = 0,
								    goodsQtyTotal = 0,
								    weightTotal = 0,
								    volumeTotal = 0;
									var record = store.data;
								for(var i=0;i<record.length;i++ ){
									waybillQtyTotal += 1;//总票数
									goodsQtyTotal += record.get(i).get('alAssembleQty');//总件数
									weightTotal += record.get(i).get('alAssembleWeight');//总重量
									volumeTotal += record.get(i).get('alAssembleVolume');//总体积
								}
									
									if(volumeTotal!=0){
										volumeTotal = volumeTotal.toFixed(2);
									}
									if(weightTotal != 0){
										weightTotal = weightTotal.toFixed(2);//如果不为0，则四舍五入，保留两位小数
									}
									form.findField('totWaybillQty').setValue(waybillQtyTotal);
									form.findField('totGoodsQty').setValue(goodsQtyTotal);
									form.findField('totVolume').setValue(volumeTotal);
									form.findField('totWeight').setValue(weightTotal);
								},
								exception : function(response){
									var result = Ext.decode(response.responseText);
							    	top.Ext.MessageBox.alert(unload.i18n('foss.unload.ordertaskaddnew.alertInfo.alertTitle'),
							    	result.message);
							}
							});
						}
						
					}
				}, {
					xtype : 'button',
					text : unload
							.i18n('foss.unload.ordertaskaddnew.assignOrderTaskForm.delassignOrderTaskButtonText'),// 删除
					columnWidth : .10,
					cls : 'btnAfterTextfield',
					handler : function() {
						var form = this.up('form').getForm();
						var handoverNoCmp = form.findField('handoverNo');
						if(Ext.isEmpty(handoverNoCmp.getValue())){
							Ext.ux.Toast.msg(unload.i18n('foss.unload.ordertaskaddnew.alertInfo.alertTitle'), 
									unload.i18n('foss.unload.ordertaskaddnew.alertInfo.inputBillNoAlertInfo'), 
									'error', 2000);
							handoverNoCmp.reset();
							handoverNoCmp.focus()
						}else{
							var store = unload.taskInfoGrid.store;
							Ext.Ajax.request({
								url : unload.realPath('queryBillInfoByHandoverNo.action'),
								params : {'orderTaskVo.handoverNo': handoverNoCmp.getValue()},
								success : function(response){
									var result = Ext.decode(response.responseText);
									//获取交接单信息
									var billList = result.orderTaskVo.billList;
									if(billList == null){
										Ext.ux.Toast.msg(unload.i18n('foss.unload.ordertaskaddnew.alertInfo.alertTitle'), 
												unload.i18n('foss.unload.ordertaskaddnew.alertInfo.billNotFoundAlertInfo'), 
										'error', 2500);
										handoverNoCmp.focus();
										return;
									}
									var record = store.data;
									var k = record.length;
									var waybillQtyTotal = form.findField('totWaybillQty').getValue();
									var goodsQtyTotal = form.findField('totGoodsQty').getValue();
									var weightTotal = form.findField('totWeight').getValue();
									var volumeTotal = form.findField('totVolume').getValue();
									for(var j=0;j<record.length;j++){
										for(var i=0;i<billList.length;i++){
										if(record.get(j).get('handoverNo') == billList[i].handoverNo){
											store.remove(record.get(j));
											waybillQtyTotal -= 1;
											goodsQtyTotal -= billList[i].alAssembleQty;
											weightTotal -= billList[i].alAssembleWeight;
											volumeTotal -= billList[i].alAssembleVolume;
										}}
									};
									if(k == record.length){
										Ext.ux.Toast.msg(unload.i18n('foss.unload.ordertaskaddnew.alertInfo.alertTitle'), 
												unload.i18n('foss.unload.ordertaskaddnew.alertInfo.billNotAlertInfo'), 
										'error', 2500);
										handoverNoCmp.focus();
										return;
									};
									
									if(volumeTotal!=0){
										volumeTotal = volumeTotal.toFixed(2);
									}
									if(weightTotal != 0){
										weightTotal = weightTotal.toFixed(2);//如果不为0，则四舍五入，保留两位小数
									}
									form.findField('totWaybillQty').setValue(waybillQtyTotal);
									form.findField('totGoodsQty').setValue(goodsQtyTotal);
									form.findField('totWeight').setValue(weightTotal);
									form.findField('totVolume').setValue(volumeTotal);
									//回到单据编号
									handoverNoCmp.focus();
									handoverNoCmp.reset();
								},
								exception : function(response){
									var result = Ext.decode(response.responseText);
							    	top.Ext.MessageBox.alert(unload.i18n('foss.unload.ordertaskaddnew.alertInfo.alertTitle'),
							    	result.message);
							}
							});
					    }
					}	
				}, {
					xtype : 'container',
					columnWidth : .08,
					html : '&nbsp;'
				}, {
					fieldLabel : '车牌号',
					name : 'vehicleNo',// ！车牌号
					xtype : 'textfield'
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
				}],
			constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				me.callParent([cfg]);
			}
		});

// 定义交接单明细列表之Model
Ext.define('Foss.unload.ordertaskaddnew.taskInfoModel', {
			extend : 'Ext.data.Model',
			// 定义字段
			fields : [{
				        name : 'handoverNo',// 交接单号
				        type : 'string'
			        }, {
				        name : 'departCode',// 出发部门
				        type : 'string'
			        }, {
				        name : 'departName',// 出发部门
				        type : 'string'
			        }, {
				        name : 'arriveCode',// 到达部门
				        type : 'string'
			        }, {
				        name : 'arriveName',// 到达部门
				        type : 'string'
			        }, {
						name : 'waybillNo',// 运单号
						type : 'string'
					}, {
						name : 'transportType',// 运输性质
						type : 'string'
					}, {
						name : 'packing',// 包装
						type : 'string'
					}, {
						name : 'goodsName',// 货物名称
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
						name : 'totWaybillQty',// 总票数
						type : 'number'
					}, {
						name : 'totGoodsQty',// 总件数
						type : 'number'
					}, {
						name : 'totWeight',// 总重量
						type : 'number'
					}, {
						name : 'totVolume',// 总体积
						type : 'number'
					}]
		});


// 定义交接单明细列表之store
Ext.define('Foss.unload.ordertaskaddnew.taskInfoStore', {
			extend : 'Ext.data.Store',
			// 绑定一个模型
			model : 'Foss.unload.ordertaskaddnew.taskInfoModel',
			constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				me.callParent([cfg]);
			}
		});


//定义运单明细Model
Ext.define('Foss.unload.ordertaskaddnew.WaybillDetailModel', {
	extend : 'Ext.data.Model',
	//定义字段
	fields: [{
		name : 'waybillNo',
		type : 'string'
	},{
		name : 'serialNo',
		type : 'string'
	}]
});

//定义运单明细store
Ext.define('Foss.unload.ordertaskaddnew.WaybillDetailStore', {
	extend : 'Ext.data.Store',
	// 绑定一个模型
	model : 'Foss.unload.ordertaskaddnew.WaybillDetailModel',
	proxy : {
		type : 'ajax',
		actionMethods:'POST',
		// 请求的url
		url : unload.realPath('queryOrderSerialNoListByNo.action'),
		// 定义一个读取器
		reader : {
			// 以JSON的方式读取
			type : 'json',
			// 定义读取JSON数据的根对象
			root : 'orderTaskVo.serialNoList',
			successProperty: 'success'
		}
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
});

// 定义下方列表之grid
Ext.define('Foss.unload.ordertaskaddnew.taskInfoGrid', {
	extend : 'Ext.grid.Panel',
	//enableColumnHide : false,// 配置该属性可取消grid自定义显示列功能
	autoScroll : true,
	height : 550,
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext
				.create('Foss.unload.ordertaskaddnew.taskInfoStore'), me
				.callParent([cfg]);
	},
	// 定义行展开
	plugins : [ {
		header: true,
		ptype : 'rowexpander',
		// 定义行展开模式（单行与多行），默认是多行展开(值true)
		rowsExpander : false,
		// 行体内容
		rowBodyElement : 'Foss.unload.ordertaskaddnew.serialNoGrid'
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
	}]
});

//定义流水之grid
Ext.define('Foss.unload.ordertaskaddnew.serialNoGrid', {
	extend : 'Ext.grid.Panel',
	enableColumnHide : false,// 配置该属性可取消grid自定义显示列功能
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext
				.create('Foss.unload.ordertaskaddnew.WaybillDetailStore'), me
				.callParent([cfg]);
	},
	columns : [{
		dataIndex : 'serialNo',
		align : 'center',
		width : 120,
		xtype : 'ellipsiscolumn',
		text : unload
				.i18n('foss.unload.ordertaskaddnew.taskInfoGrid.serialNoColumn') //流水号
	}],
	bindData : function(record){
		var waybillNo = record.get('waybillNo');
		var handOverBillNo = record.get('handoverNo');
		
		var recordsMap = this.store.load({
			params : {
				'orderTaskVo.handoverNo' : handOverBillNo,
				'orderTaskVo.waybillNo' : waybillNo
			}
		});
		}
});



// 上方form
unload.taskAddForm = Ext
		.create('Foss.unload.ordertaskaddnew.taskAddForm');
// 下方grid
unload.taskInfoGrid = Ext
		.create('Foss.unload.ordertaskaddnew.taskInfoGrid');

// 定义上方控件Panel
Ext.define('Foss.unload.ordertaskaddnew.taskInfoPanel', {
			extend : 'Ext.panel.Panel',
			title : unload
					.i18n('Foss.unload.ordertaskaddnew.taskInfoForm.title'),
			layout : 'auto',
			frame : true,
			collapsible : true,
			animCollapse : true,
			items : [
					unload.taskAddForm, 
					unload.taskInfoGrid
			]
		});


Ext.onReady(function() {
	Ext.create('Ext.panel.Panel', {
		id : 'T_unload-orderTaskaddnewindex_content',
		cls : "panelContentNToolbar",
		bodyCls : 'panelContent-body',
		layout : 'column',
		items : [{
			columnWidth : 1,
			items : [Ext
					.create('Foss.unload.ordertaskaddnew.taskInfoPanel')]
		}, {
			xtype : 'container',
			columnWidth : 1,
			layout : 'column',
			items : [{
						columnWidth : .90,
						xtype : 'container',
						html : '&nbsp'
					}, {
						columnWidth : .10,
						xtype : 'button',
						cls : 'yellow_button',// ！分配任务
						name : 'saveButton',
						id : 'Foss_unload_ordertaskaddnew_saveButton_ID',
						text : unload
								.i18n('foss.unload.ordertaskaddnew.orderManGrid.assignOrderTask'),
						handler : function() {
						 var form = unload.taskAddForm.getForm();
                         var vehicleNo = form.findField('vehicleNo').getValue();
						 var orderCode = form.findField('orderCode').getValue();
						 var totWaybillQty = form.findField('totWaybillQty').getValue();
						 var totGoodsQty = form.findField('totGoodsQty').getValue();
						 var totWeight = form.findField('totWeight').getValue();
						 var totVolume = form.findField('totVolume').getValue();
						 if(Ext.isEmpty(vehicleNo)){
								Ext.ux.Toast.msg(unload.i18n('foss.unload.ordertaskaddnew.alertInfo.alertTitle'), 
										unload.i18n('foss.unload.ordertaskaddnew.alertInfo.vehicleNoNotAllowBlankAlertInfo'), 
								'error', 2000);
								return;
							}
						 if(Ext.isEmpty(orderCode)){
								Ext.ux.Toast.msg(unload.i18n('foss.unload.ordertaskaddnew.alertInfo.alertTitle'), 
										unload.i18n('foss.unload.ordertaskaddnew.alertInfo.orderManNotAllowBlankAlertInfo'), 
								'error', 2000);
								return;
							}else{
								
								var orderStore = form.findField('orderCode').store;
								orderName = orderStore.findRecord('empCode',orderCode,0,false,true,true).get('empName');
							}
						 var billStore = unload.taskInfoGrid.getStore();
						 if(billStore.getCount() == 0){
							 Ext.ux.Toast.msg(unload.i18n('foss.unload.ordertaskaddnew.alertInfo.alertTitle'), 
										unload.i18n('foss.unload.ordertaskaddnew.alertInfo.atLeastOneBillAlertInfo'), 
								'error', 2000);
								return;
							}
						  //构造jsondata
							//单据list
							var billList = new Array();
							billStore.each(function(record){
								billList.push(record.data);
							});
							var data = {
									'orderTaskVo' : {
										'addnewDto' : {
											'vehicleNo' : vehicleNo,
											'orderCode' : orderCode,
											'orderName' : orderName,
											'totWaybillQty' : totWaybillQty,
											'totGoodsQty' : totGoodsQty,
											'totWeight' : totWeight,
											'totVolume' : totVolume,
											'createOrgCode' : FossUserContext.getCurrentDeptCode(),
											'createOrgName' : FossUserContext.getCurrentDept().name,
											'billList' : billList
									}
								}
							}
							var myMask = new Ext.LoadMask(Ext.getCmp('T_unload-orderTaskaddnewindex_content'), {
								msg : unload.i18n('foss.unload.ordertaskaddnew.alertInfo.dataCommittingAlertInfo')
							});
							myMask.show();
							//保存数据
							Ext.Ajax.request({
								url : unload.realPath('addOrderTask.action'),
								jsonData : data,
								success : function(response){
									var result = Ext.decode(response.responseText);
									//获取生成的任务编号
									var orderTaskNo = result.orderTaskVo.orderTaskNo;
									Ext.Msg.show({
									     title : unload.i18n('foss.unload.ordertaskaddnew.alertInfo.alertTitle'),
									     msg : unload.i18n('foss.unload.ordertaskaddnew.alertInfo.saveSuccessAlertInfo') +orderTaskNo,
									     buttons : Ext.Msg.OK,
									     icon : Ext.Msg.INFO
										});
									   form.findField('orderTaskNo').setValue(orderTaskNo);
										//分配按钮不可用
									   Ext.getCmp('Foss_unload_ordertaskaddnew_saveButton_ID').setVisible(false);
										myMask.hide();
									},
									exception : function(response){
										var result = Ext.decode(response.responseText);
								    	top.Ext.MessageBox.alert(unload.i18n('foss.unload.ordertaskaddnew.alertInfo.alertTitle'),
								    			unload.i18n('foss.unload.ordertaskaddnew.alertInfo.saveFailureAlertInfo') + result.message);
								    	myMask.hide();
									}
								});
						}
					}]
		}],
		renderTo : 'T_unload-orderTaskaddnewindex-body'
	});
});