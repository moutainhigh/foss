// 定义运单model
Ext.define('Foss.load.deliverloadtaskconfirm.waybillDetailModel', {
	extend : 'Ext.data.Model',
	//定义字段
	fields : [{
		name : 'isBindData',
		type : 'string',
		defaultValue : 'N'
	},{
		name : 'isSelectAll',
		type : 'string',
		defaultValue : 'N'
	},{
		name : 'deliverBillNo',
		type : 'string'
	},{
		name : 'waybillNo',
		type : 'string'
	},{
		name : 'goodsName',
		type : 'string'
	},{
		name : 'consignee',
		type : 'string'
	}, {
		name : 'loadQty',
		type : 'number'
	}, {
		name : 'deliverBillQty',
		type : 'number'
	},{
		name : 'stockQty',
		type : 'number'
	}, {
		name : 'waybillGoodsQty',
		type : 'number'
	}, {
		name : 'notes',
		type : 'string'
	}, {
		name : 'notes',
		type : 'string'
	}, {
		name : 'transportType',
		type : 'string'
	}, {
		name : 'pack',
		type : 'string'
	}, {
		name : 'receiveOrgName',
		type : 'string'
	}, {
		name : 'reachOrgName',
		type : 'string'
	}, {
		name : 'origOrgCode',
		type : 'string'
	}, {
		name : 'notes',
		type : 'string'
	}, {
		name : 'beJoinCar',
		type : 'string'
	}, {
		name : 'loadWeightTotal',
		type : 'number'
	}, {
		name : 'loadVolumeTotal',
		type : 'number'
	},{
		name : 'weightTotal',
		type : 'number'
	},{
		name : 'volumeTotal',
		type : 'number'
	}]
});

// 定义查询运单store
Ext.define('Foss.load.deliverloadtaskconfirm.waybillDetailStore', {
	extend : 'Ext.data.Store',
	// 绑定一个模型
	model : 'Foss.load.deliverloadtaskconfirm.waybillDetailModel',
	listeners : {
		update : function(store, record, operation,modifiedFieldNames,eOpts){
			//如果装车件数被更新
			for(var i in modifiedFieldNames){
				if(modifiedFieldNames[i] === 'loadQty'){
					//如果已装车件数为0，则将运单行反选
					var loadQty = record.get('loadQty'),
						superGrid = load.deliverloadtaskconfirm.waybillGrid,
						deliverBillQty = record.get('deliverBillQty'),
						stockQty = record.get('stockQty'),
						sm = superGrid.getSelectionModel();
					if(loadQty === 0){
						sm.deselect(record,true);
						var plugin = superGrid.getPlugin('Foss_deliverloadtaskconfirm_serialNoDetailGrid_ID'),
							eleMap = plugin.elementIdMap,
							id = record.internalId+'-rowbody-element',
							subGrid = eleMap.get(id);
						if(!Ext.isEmpty(plugin.getExpendRow())) {
							subGrid.getSelectionModel().deselectAll(true);
						}
					}
					//如果装车件数为1，则说明是第一次勾选流水号，则将运单选中
					if(loadQty === 1){
						sm.select(record,true,true);
					}
					//如果装车件数等于排单件数，则将isSelectAll设置为Y
					if(loadQty === deliverBillQty){
						record.set('isSelectAll','Y');
						//重置“装车少货备注”
						record.set('notes',null);
					}
					//如果装车件数小于排单件数，则将isSelectAll设置为N
					if(loadQty < deliverBillQty){
						record.set('isSelectAll','N');
					}
					//重新计算装车重量、体积，四舍五入两位小数
					if(stockQty !== 0){
						record.set('loadWeightTotal',((loadQty/stockQty)*record.get('weightTotal')).toFixed(2));
						record.set('loadVolumeTotal',((loadQty/stockQty)*record.get('volumeTotal')).toFixed(2));
					}
					break;
				}
			}
		}
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
});

// 定义运单明细grid
Ext.define('Foss.load.deliverloadtaskconfirm.waybillDetailGrid', {
	extend : 'Ext.grid.Panel',
	frame : true,
	title : '派送单明细     <font size="1px" style="font-weight : lighter; color: red;">（注：请勾选已装车的运单号、流水号。）</font>',
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	sortableColumns : false,//禁用排序功能，若不禁用则有bug
	enableColumnHide : false,//配置该属性可取消grid自定义显示列功能
	columnLines: true,
	autoScroll : true,
	collapsible : true,
	animCollapse : true,
	constructor : function(config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.load.deliverloadtaskconfirm.waybillDetailStore'),
		me.selModel = Ext.create('Ext.selection.CheckboxModel',{
			showHeaderCheckbox : true,
			mode : 'SIMPLE',
			checkOnly : true,
			listeners : {
				beforeselect : function(rowModel, rec, index, eOpts){
					//如果库存件数等于0，则无法勾选，如果库存件数大于排单件数，则需手动勾选流水号
					var stockQty = rec.get('stockQty'),
						deliverBillQty = rec.get('deliverBillQty');
					if(stockQty === 0){
						Ext.ux.Toast.msg('提示', '库存件数为0，无法装车！', 'error', 1500);
						return false;
					}
					if(stockQty > deliverBillQty){
						Ext.ux.Toast.msg('提示', '“库存件数”大于“排单件数”，请手动勾选装车的流水号！', 'error', 1500);
						return false;
					}
				}
			}
		});
		me.columns = [{
			dataIndex : 'waybillNo',
			align : 'center',
			width : 80,
			xtype : 'ellipsiscolumn',
			text : '运单号'
		}, {
			dataIndex : 'goodsName',
			align : 'center',
			width : 80,
			text : '货物名称'
		}, {
			dataIndex : 'consignee',
			align : 'center',
			width : 80,
			text : '收货人'
		}, {
			dataIndex : 'loadQty',
			align : 'center',
			width : 100,
			text : '已装车件数'
		}, {
			dataIndex : 'deliverBillQty',
			align : 'center',
			width : 80,
			text : '排单件数'
		}, {
			dataIndex : 'stockQty',
			align : 'center',
			width : 80,
			text : '库存件数'
		}, {
			dataIndex : 'waybillGoodsQty',
			align : 'center',
			width : 80,
			text : '开单件数'
		}, {
			dataIndex : 'notes',
			align : 'center',
			xtype : 'ellipsiscolumn',
			flex : 1,
			text : '装车少货备注',
			editor : {
				xtype : 'textfield',
				maxLength : 300
			}
		}],
		me.callParent([cfg]);
	},
	// 定义行展开
	plugins : [ {
		header: true,
		ptype : 'rowexpander',
		rowsExpander : false,
		// 行体内容
		rowBodyElement : 'Foss.load.deliverloadtaskconfirm.SerialNoDetailGrid',
		pluginId : 'Foss_deliverloadtaskconfirm_serialNoDetailGrid_ID'
	},Ext.create('Ext.grid.plugin.CellEditing', {
            clicksToEdit : 1,
            listeners : {
				'beforeedit' : function(editor,e,eOpts){
					var record = e.record,
						loadQty = record.get('loadQty'),
						deliverBillQty = record.get('deliverBillQty');
					//如果没有少货，则不允许输入备注
					if(loadQty >= deliverBillQty){
						return false;
					}
				}
			}
        })
     ],
	listeners : {
		select : function(rowModel,record, index, eOpts){
			var grid = this,
				plugin = grid.getPlugin('Foss_deliverloadtaskconfirm_serialNoDetailGrid_ID'),
				waybillNo = record.get('waybillNo'),
				stockQty = record.get('stockQty');
			if(!Ext.isEmpty(plugin.getExpendRow())) {
				var item = plugin.getExpendRowBody();
				var store = item.getStore();
				var subWaybillNo = store.getAt(0).get('waybillNo');
				if(subWaybillNo === waybillNo){
					var sm = item.getSelectionModel();
					sm.deselectAll(true);
					//勾选流水号，个数为排单件数
					for(var i = 0;i < store.getCount();i++){
						var rec = store.getAt(i);
						sm.select(rec,true,true);
						if(i +1 === stockQty){
							break;
						}
					}
				}
			}
			record.set('loadQty',stockQty);
		},
		deselect : function(rowModel, record, index, eOpts){
			record.set('loadQty',0);
			var grid = this,
				plugin = grid.getPlugin('Foss_deliverloadtaskconfirm_serialNoDetailGrid_ID'),
				waybillNo = record.get('waybillNo');
			if(!Ext.isEmpty(plugin.getExpendRow())) {
				var item = plugin.getExpendRowBody();
				var store = item.getStore();
				var subWaybillNo = store.getAt(0).get('waybillNo');
				if(subWaybillNo === waybillNo){
					var sm = item.getSelectionModel();
					sm.deselectAll(true);
				}
			}
		}
	}
});

//定义流水号列表Model
Ext.define('Foss.load.deliverloadtaskconfirm.serialNoDetailModel', {
	extend : 'Ext.data.Model',
	// 定义字段
	fields : [{
		name : 'waybillNo',
		type : 'string'
	},{
		name : 'serialNo',
		type : 'string'
	}]
});

// 定义流水号明细store
Ext.define('Foss.load.deliverloadtaskconfirm.serialNoDetailStore', {
	extend : 'Ext.data.Store',
	// 绑定一个模型
	model : 'Foss.load.deliverloadtaskconfirm.serialNoDetailModel',
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
});

// 定义查询库存运单中流水号列表grid
Ext.define('Foss.load.deliverloadtaskconfirm.SerialNoDetailGrid', {
	extend : 'Ext.grid.Panel',
	columnLines: true,
	width : 150,
	emptyText : '暂无库存',
	animCollapse : true,
	hideHeaders : true,
	baseCls : 'deliver_load_confirm_serialNoGap',
	columns : [ {
		dataIndex : 'serialNo',
		align : 'left',
		width : 100,
		xtype : 'ellipsiscolumn',
		renderer : function(value){
			if(!Ext.isEmpty(value)){
				return '流水号：' + value;
			}
			return value;
		}
	}],
	bindData : function(record){
		var grid = this,
			superGrid = this.superGrid,
		    waybillNo = record.get('waybillNo'),
		    isBindData = record.get('isBindData'),
		    stockQty = record.get('stockQty'),
		    deliverBillQty = record.get('deliverBillQty'),
		    isSelectAll = record.get('isSelectAll');
		if(isBindData == 'N'){
			//构造查询条件json串
			var data = {
				'deliverLoadTaskVo' : {
					'waybillNo' : waybillNo 
				}
			};
			Ext.Ajax.request({
				url : load.realPath('querySerialNoListForDeliverBill.action'),
				jsonData : data,
				success:function(response){
					var result = Ext.decode(response.responseText),
						store = grid.store,
						serialNoList = result.deliverLoadTaskVo.serialNoList;
					store.loadData(serialNoList);
					record.set('isBindData','Y');
					//如果当前行被选择，则将展开后的运单选择
					if(isSelectAll === 'Y'){
						var sm = grid.getSelectionModel();
						//勾选流水号，个数为排单件数
						for(var i = 0;i < store.getCount();i++){
							var rec = store.getAt(i);
							sm.select(rec,true,true);
							if(i +1 === deliverBillQty){
								break;
							}
						}
					}
					//如果加载出的库存流水号跟一级表中的运单库存件数不相等，则根据此处结果重置库存件数
					if(stockQty != serialNoList.length){
						record.set('stockQty',serialNoList.length);
					}
				}
			});
		}else{
			var sm = grid.getSelectionModel(),
				isSelect = superGrid.getSelectionModel().isSelected(record),
				store = grid.store;
			//如果没有勾选，则将所有流水号反选
			if(!isSelect){
				sm.deselectAll(true);
			}
//			//如果全部勾选，则展开后将流水号全部勾选
			if(isSelectAll === 'Y'){
				var plugin = superGrid.getPlugin('Foss_deliverloadtaskconfirm_serialNoDetailGrid_ID'),
					eleMap = plugin.elementIdMap,
					id = record.internalId+'-rowbody-element';
					subGrid = eleMap.get(id),
					selectedSerialNoList = subGrid.getSelectionModel().getSelection();
				//如果勾选的件数等于排单件数，不做处理
				if(selectedSerialNoList.length === deliverBillQty){
					return;
				//如果未被勾选，或者已勾选件数少于排单件数，则从最小的流水号开始勾选，勾选的件数等于排单件数
				}else{
					//勾选流水号，个数为排单件数
					sm.deselectAll(true);
					for(var i = 0;i < store.getCount();i++){
						var rec = store.getAt(i);
						sm.select(rec,true,true);
						if(i +1 === deliverBillQty){
							break;
						}
					}
				}
			}
		}
	},
	listeners : {
		'select' : function(rowModel,record,index,eOpts){
			var superGrid = this.superGrid,
				waybillNo = record.get('waybillNo'),
				superRecord = superGrid.store.findRecord('waybillNo',waybillNo,0,false,true,true);
			    loadQty = superRecord.get('loadQty');
		    var item = superGrid.getPlugin('Foss_deliverloadtaskconfirm_serialNoDetailGrid_ID').getExpendRowBody();
		    var store = item.getStore();
		    var sm = item.getSelectionModel();
		    //所有选中的数量
			superRecord.set('loadQty',sm.getCount());
		},
		'deselect' : function(rowModel,record,index,eOpts){
			var superGrid = this.superGrid,
				waybillNo = record.get('waybillNo'),
				superRecord = superGrid.store.findRecord('waybillNo',waybillNo,0,false,true,true);
			    loadQty = superRecord.get('loadQty');
		    var item = superGrid.getPlugin('Foss_deliverloadtaskconfirm_serialNoDetailGrid_ID').getExpendRowBody();
		    var store = item.getStore();
		    var sm = item.getSelectionModel();
		    //所有选中的数量
			superRecord.set('loadQty', sm.getCount());
		}
	},
	constructor : function(config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.load.deliverloadtaskconfirm.serialNoDetailStore');
		me.selModel = Ext.create('Ext.selection.CheckboxModel',{
			showHeaderCheckbox : false,
			mode : 'SIMPLE',
			checkOnly : true,
			listeners : {
				beforeselect : function(rowModel, rec, index, eOpts){
					//选择的件数总和不能大于排单件数
					var superGrid = me.superGrid,
						waybillNo = rec.get('waybillNo'),
						superRec = superGrid.store.findRecord('waybillNo',waybillNo,0,false,true,true);
					var deliverBillQty = superRec.get('deliverBillQty'),
						loadQty = superRec.get('loadQty');
					if(loadQty >= deliverBillQty){
						Ext.ux.Toast.msg('提示', '“已装车件数”不能大于“排单件数”！', 'error', 1500);
						return false;
					}
				}
			}
		});
		me.callParent([cfg]);
	}
});

load.deliverloadtaskconfirm.loadData = function(deliverBillNo){
	//加载派送单数据
	var myMask = new Ext.LoadMask(Ext.getCmp('T_load-deliverloadtaskconfirmindex_content'), {
		msg:"加载中，请稍候..."
	});
	myMask.show();
	//请求server端，获取数据
	Ext.Ajax.request({
		url : load.realPath('loadDeliverBillAndDetailListByNo.action'),
		params : {'deliverLoadTaskVo.billNo' : deliverBillNo},
		success : function(response) {
			var result = Ext.decode(response.responseText);
			//基本信息
			var baseEntity = result.deliverLoadTaskVo.billInfo;
			//派送单最后操作时间（用作时间戳）
			load.deliverloadtaskconfirm.operateTime = baseEntity.operateTime;
			Ext.getCmp('Foss_load_deliverloadtaskconfirm_deliverBillNo').setValue(baseEntity.billNo);
			Ext.getCmp('Foss_load_deliverloadtaskconfirm_vehicleNo').setValue(baseEntity.vehicleNo);
			//运单列表
			load.deliverloadtaskconfirm.waybillGrid.store.loadData(result.deliverLoadTaskVo.billDetailList);
			load.deliverloadtaskconfirm.waybillGrid.store.each(function(rec){
				rec.set('weightTotal',rec.get('loadWeightTotal'));
				rec.set('volumeTotal',rec.get('loadVolumeTotal'));
				rec.set('loadVolumeTotal',0);
				rec.set('loadWeightTotal',0);
			});
			myMask.hide();
		},
		exception : function(response){
			var message = Ext.decode(response.responseText).message;
			Ext.MessageBox.show({
		        title: '提示',
		        msg: '加载派送单数据失败，' + message,
		        buttons: Ext.MessageBox.OK,
		        icon: Ext.MessageBox.WARNING
		    });
		    myMask.hide();
		}
	});
}

load.deliverloadtaskconfirm.waybillGrid = Ext.create('Foss.load.deliverloadtaskconfirm.waybillDetailGrid');

Ext.onReady(function() {
	Ext.create('Ext.panel.Panel', {
		id : 'T_load-deliverloadtaskconfirmindex_content',
		cls : "panelContentNToolbar",
		bodyCls : 'panelContent-body',
		layout : 'auto',//
		items : [{
			layout : 'column',
			columnWidth : 1,
			defaults : {
				margin : '5 5 10 5'	
			},
			items : [{
					xtype : 'textfield',
					readOnly : true,
					columnWidth : .27,
					name : 'deliverBillNo',
					fieldLabel : '派送单号',
					id : 'Foss_load_deliverloadtaskconfirm_deliverBillNo'
				},{
					xtype : 'textfield',
					readOnly : true,
					columnWidth : .2,
					name : 'vehicleNo',
					fieldLabel : '车牌号',
					id : 'Foss_load_deliverloadtaskconfirm_vehicleNo'
				}]
		},load.deliverloadtaskconfirm.waybillGrid,{
			xtype : 'container',
			columnWidth : 1,
			layout : 'column',
			items : [ { 
					xtype : 'container',
					columnWidth : .9,
					html : '&nbsp'
			}, {
				columnWidth : .1,
				xtype : 'button',
				cls : 'yellow_button',
				name : 'saveButton',
				id : 'Foss_load_deliverloadtaskconfirm__confirmButton_ID',
				text : '确认装车结束',
				handler : function() {
					var waybillGrid = load.deliverloadtaskconfirm.waybillGrid,
						plugin = waybillGrid.getPlugin('Foss_deliverloadtaskconfirm_serialNoDetailGrid_ID'),
						eleMap = plugin.elementIdMap,
						waybillStore = waybillGrid.store;
						selectedWaybillNoList = waybillGrid.getSelectionModel().getSelection(),
						alertInfo = null,
						waybillList = new Array(),
						serialNoList = new Array(),
						isException = 'N';
					if(selectedWaybillNoList.length == 0){
						alertInfo = '本次装车没有装载任何货物，';
					}
					//封装运单list
					waybillStore.each(function(waybillRec){
						if(waybillRec.get('isSelectAll') === 'N'){
							isException = 'Y';
						}
						waybillList.push(waybillRec.data);
						//获取勾选的流水号
						var id = waybillRec.internalId+'-rowbody-element';
							subGrid = eleMap.get(id);
						if(subGrid !== null && subGrid !== undefined){
							var selectedSerialNoList = subGrid.getSelectionModel().getSelection();
							for(var i in selectedSerialNoList){
								serialNoList.push(selectedSerialNoList[i].data);
							}
						}
					});
					if(isException === 'N'){
						alertInfo = '本次装车没有任何异常，'
					}else{
						alertInfo = '本次装车存在少货异常，'
					}
					Ext.MessageBox.confirm('提示', alertInfo + '确定要结束装车任务吗？',function(btn){
						/*
						 * 装车完成后，将界面勾选的运单号、流水号提交至后台，后台校验、对比生成装车任务信息
						 * */
						if(btn === 'yes'){
							//ajax请求，loadMask
							var myMask = new Ext.LoadMask(Ext.getCmp('T_load-deliverloadtaskconfirmindex_content'), {
								msg:"数据提交中，请稍候..."
							}),
							jsonData = {
								'deliverLoadTaskVo' : {
									'billNo' : load.deliverloadtaskconfirm.deliverBillNo,
									'billDetailList' : waybillList,
									'serialNoList' : serialNoList,
									'operateTime' : load.deliverloadtaskconfirm.operateTime
								}
							};
							myMask.show();
							Ext.Ajax.request({
								url : load.realPath('confirmDeliverLoadTask.action'),
								jsonData : jsonData,
								success : function(response) {
									Ext.MessageBox.show({
								        title: '提示',
								        msg: '操作成功，装车已完成！',
								        buttons: Ext.MessageBox.OK,
								        icon: Ext.MessageBox.INFO
								    });
								    Ext.getCmp('Foss_load_deliverloadtaskconfirm__confirmButton_ID').setVisible(false);
									myMask.hide();
								},
								exception : function(response){
									var result = Ext.decode(response.responseText),
										message = result.message;
									//如果派送单被修改过
									if(message === 'N'){
										Ext.MessageBox.confirm('提示', '该派送单已经被修改过，是否重新加载派送单数据？',function(btn){
											if(btn === 'yes'){
												load.deliverloadtaskconfirm.loadData(load.deliverloadtaskconfirm.deliverBillNo);
											}else{
												Ext.MessageBox.show({
											        title: '提示',
											        msg: '提交装车任务失败！',
											        buttons: Ext.MessageBox.OK,
											        icon: Ext.MessageBox.WARNING
											    });
											}
										});
									}else{
										Ext.MessageBox.show({
									        title: '提示',
									        msg: '处理失败，' + message,
									        buttons: Ext.MessageBox.OK,
									        icon: Ext.MessageBox.WARNING
									    });
									}
									myMask.hide();
								}
							});
						}
					});
				}
			} ]
		}],
		renderTo : 'T_load-deliverloadtaskconfirmindex-body'
	});
	//加载派送单数据
	load.deliverloadtaskconfirm.loadData(load.deliverloadtaskconfirm.deliverBillNo);
});