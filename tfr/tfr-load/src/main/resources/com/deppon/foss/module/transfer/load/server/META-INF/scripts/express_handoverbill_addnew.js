//定义交接单基本信息Model
Ext.define('Foss.load.expresshandoverbilladdnew.HandOverBillModel', {
	extend : 'Ext.data.Model',
	//定义字段
	fields : [{
		name : 'handOverBillNo',
		type : 'string'
	}, {
		name : 'handOverType',
		type : 'string'
	}, {
		name : 'handOverTime',
		type : 'date',
		convert : function(value) {
			if(!value) return '';
			var date = new Date(value);						
			var formatStr = 'Y-m-d H:i:s';
			return Ext.Date.format(date, formatStr);
		}
	}, {
		name : 'departDept',
		type : 'string'
	}, {
		name : 'arriveDeptCode',
		type : 'string'
	}, {
		name : 'arriveDept',
		type : 'string'
	}, {
		name : 'loadEndTime',
		type : 'date',
		convert : function(value) {
			if(!value) return '';
			var date = new Date(value);						
			var formatStr = 'Y-m-d H:i:s';
			return Ext.Date.format(date, formatStr);
		}
	}, {
		name : 'modifyUserName',
		type : 'string'
	}]
});

// 定义交接单明细Model
Ext.define('foss.load.expresshandoverbilladdnew.billDetailModel', {
	extend : 'Ext.data.Model',
	//定义字段
	fields : [{
		name : 'id',
		type : 'string'
	},{
		name : 'handOverBillNo',
		type : 'string'
	},{
		name : 'waybillNo',
		type : 'string'
	}, {
		name : 'transProperty',
		type : 'string'
	}, {
		name : 'transPropertyCode',
		type : 'string'
	},{
		name : 'pieces',
		type : 'number'
	}, {
		name : 'weight',
		type : 'number'
	}, {
		name : 'weightAc',
		type : 'number'
	}, {
		name : 'cubage',
		type : 'number'
	}, {
		name : 'cubageAc',
		type : 'number'
	}, {
		name : 'note',
		type : 'string'
	}, {
		name : 'goodsName',
		type : 'string'
	}, {
		name : 'packing',
		type : 'string'
	}, {
		name : 'waybillNote',
		type : 'string'
	} ,{
		name : 'waybillPieces',
		type : 'number'
	},{
		name : 'isPreciousGoods',
		type : 'string'
	},{
		name : 'waybillDate',	
		type : 'date',
		convert: dateConvert
	},{
		name : 'instorageDate',
		type : 'date',
		convert : dateConvert
	},{
		name : 'arriveDept',
		type : 'string'
	},{
		name : 'insuranceValue',
		type : 'number'
	},{
		name : 'consignee',
		type : 'string'
	},{
		name : 'destination',
		type : 'string'
	},{
		name : 'waybillFee',
		type : 'number'
	},{
		name : 'origOrgCode',
		type : 'string'
	},{
		name : 'receiveOrgName',
		type : 'string'
	},{
		name : 'planArriveTime',
		type : 'date',
		convert : dateConvert
	},{
		name : 'codAmount',
		type : 'number'
	},{
		name : 'isCarLoad',
		type : 'string'
	},{
		name : 'isJoinCar',
		type : 'string'
	},{
		name : 'isFastGoods',
		type : 'string'
	},{
		name : 'goodsAreaCode',
		type : 'string'
	},{
		name : 'goodsAreaType',
		type : 'string'
	},{
		name : 'serialNoStockList',
		type : 'object'
	}]
});

// 定义流水号列表Model
Ext.define('foss.load.expresshandoverbilladdnew.serialNoModel', {
	extend : 'Ext.data.Model',
	// 定义字段
	fields : [{
		name : 'superId',
		type : 'string'
	},{
		name : 'handOverBillNo',
		type : 'string'
	},{
		name : 'waybillNo',
		type : 'string'
	},{
		name : 'serialNo',
		type : 'string'
	}, {
		name : 'instorageDate',
		type : 'date',
		convert: dateConvert,
		defaultValue : new Date()
	},{
		name : 'isInStorage',
		type : 'number'
	},{
		name : 'isJoinCar',
		type : 'string'
	},{
		name : 'isPreHandOver',
		type : 'string'
	}]
});

// 定义交接单明细store
Ext.define('foss.load.expresshandoverbilladdnew.HandOverBillDetailStore', {
	extend : 'Ext.data.Store',
	// 绑定一个模型
	model : 'foss.load.expresshandoverbilladdnew.billDetailModel',
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	},
	listeners : {
		'datachanged' : function(store){
			load.expresshandoverbilladdnew.updateMainPageStaInfo(store);
		},
		'update' : function(store){
			load.expresshandoverbilladdnew.updateMainPageStaInfo(store);
		},
		'clear' : function(store){
			load.expresshandoverbilladdnew.updateMainPageStaInfo(store);
		}
	}
});

//方法用于各处调用，更新主页面grid下统计条数据
load.expresshandoverbilladdnew.updateMainPageStaInfo = function (store){
	//更新主页总票数
	var totalCountCmp = Ext.getCmp('Foss_load_expresshandoverbilladdnew_MainPageTotalCount');
	totalCountCmp.setValue(load.expresshandoverbilladdnew.handOverBillDetailGrid.store.getCount());
	//遍历主页store，获得总件数、总体积、总重量
	var totalPieces = 0,totalVolume = 0,totalWeight = 0, normalTotalVolume=0,fastTotalWeight=0,nuConvertTotalVolume=0;
	store.each(function(record){
		totalPieces += record.get('pieces');
		nuConvertTotalVolume += record.get('cubageAc');
		totalWeight += record.get('weightAc');
		if(record.get('transPropertyCode')=='PACKAGE'||record.get('transPropertyCode')=='RCP'||record.get('transPropertyCode')=='EPEP'){
			//获取快递的总重量
			fastTotalWeight+=record.get('weightAc');
		}else{
			//获取除快递货的总体积
			normalTotalVolume += record.get('cubageAc');
		}
	});
	//转换后体积
	totalVolume=normalTotalVolume+fastTotalWeight*load.expresshandoverbilladdnew.rate
	if(totalWeight != 0){
		totalWeight = totalWeight.toFixed(3);//如果不为0，则四舍五入，保留3位小数
	}
	if(totalVolume != 0){
		totalVolume = totalVolume.toFixed(3);
	}
	if(nuConvertTotalVolume != 0){
		nuConvertTotalVolume = nuConvertTotalVolume.toFixed(2);
	}
	//更新主页总件数、总体积、总重量
	Ext.getCmp('Foss_load_expresshandoverbilladdnew_MainPageTotalPieces').setValue(totalPieces);
	Ext.getCmp('Foss_load_expresshandoverbilladdnew_MainPageTotalVolume').setValue(totalVolume);
	Ext.getCmp('Foss_load_expresshandoverbilladdnew_MainPageTotalWeight').setValue(totalWeight);
	Ext.getCmp('Foss_load_expresshandoverbilladdnew_MainPageUnConvertTotalVolume').setValue(nuConvertTotalVolume);
};

// 定义运单明细store
Ext.define('foss.load.expresshandoverbilladdnew.WaybillDetailStore', {
	extend : 'Ext.data.Store',
	// 绑定一个模型
	model : 'foss.load.expresshandoverbilladdnew.serialNoModel',
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
});

// 定义运单明细grid
Ext.define('foss.load.expresshandoverbilladdnew.WaybillDetailGrid', {
	extend : 'Ext.grid.Panel',
	columnLines: true,
	frame: true,
	enableColumnHide : false,//配置该属性可取消grid自定义显示列功能
	baseCls : 'handOverBill_addNew_serialNoGap',
	autoScroll : true,
	width : 180,
	store : Ext.create('foss.load.expresshandoverbilladdnew.WaybillDetailStore'),
	columns : [ {
		xtype : 'actioncolumn',
		width : 60,
		text : load.expresshandoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.actionColumn')/*'操作'*/,
		align : 'center',
		items : [ {
			tooltip : load.expresshandoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.deleteButtonColumn')/*'删除'*/,
			iconCls : 'deppon_icons_remove',
			handler : function(grid, rowIndex, colIndex) {
				if(grid.store.getCount() == 1){
					Ext.ux.Toast.msg(load.expresshandoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.alertInfoTitle')/*'提示'*/, load.expresshandoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.deleteWaybillAlert')/*'请直接删除运单'*/, 'error', 1500);
					return;
				}
				var serialNoRec = grid.store.getAt(rowIndex),
					waybillNo = serialNoRec.data.waybillNo;
				
				//更新一级表格内的信息
				var waybillStore = load.expresshandoverbilladdnew.handOverBillDetailGrid.store,
					waybillRecord = waybillStore.findRecord('waybillNo', waybillNo, 0, false,true,true),
					serialNoStockList = waybillRecord.data.serialNoStockList;
				for(var i = 0;i < serialNoStockList.length;i++){
					var temp = serialNoStockList[i];
					if(temp.serialNo === serialNoRec.get('serialNo')){
						serialNoStockList.splice(i,1);
						break;
					}
				}
				
				//更新运单
				waybillRecord.set('weight',(waybillRecord.get('weight')-(waybillRecord.get('weight')/waybillRecord.get('pieces'))).toFixed(3));
				waybillRecord.set('cubage',(waybillRecord.get('cubage')-(waybillRecord.get('cubage')/waybillRecord.get('pieces'))).toFixed(3));
				waybillRecord.set('weightAc',(waybillRecord.get('weightAc')-(waybillRecord.get('weightAc')/waybillRecord.get('pieces'))).toFixed(3));
				waybillRecord.set('cubageAc',(waybillRecord.get('cubageAc')-(waybillRecord.get('cubageAc')/waybillRecord.get('pieces'))).toFixed(3));
				waybillRecord.set('pieces',waybillRecord.get('pieces') - 1);
				grid.store.removeAt(rowIndex);
			}
		} ]
	}, {
		dataIndex : 'serialNo',
		align : 'center',
		flex : 1,
		xtype : 'ellipsiscolumn',
		text : load.expresshandoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.serialNoColumn')/*'流水号'*/
	} ],
	bindData : function(record){
		if(load.expresshandoverbilladdnew.isSaved == 'Y'){
			this.columns[0].setVisible(false);
			//this.columns[0].destroy();
		}
		this.store.loadData(record.data.serialNoStockList);
	}
});

//定义打印模版window
Ext.define('foss.load.expresshandoverbilladdnew.PrintWindow', {
	extend: 'Ext.window.Window',
	title: '打印模板选择',
	layout:'column',
	height: 150,
	width: 300,
	closable:true,
	closeAction:'hide',
	modal: true,
	handOverBillNos : null,
	vehicleNo : null,
	grid : null,
	items : [{
		fieldLabel : '打印模版',
		name : 'printTemplate',
		columnWidth: 1,
		xtype : 'combobox',
		queryMode: 'local',
	    displayField: 'value',
	    valueField: 'key',
	    editable : false,
	    defaults: {
			margin: '10 5 10 5'
		},
	    store : Ext.create('Ext.data.Store', {
	        fields: ['key', 'value'],
	        data : [
	            {"key":"配载单", "value":"配载单打印"},
	            {"key":"配载单(流水)", "value":"配载单(流水号)打印"},
	            {"key":"交接单", "value":"交接单打印"},
	            {"key":"交接单(流水)", "value":"交接单(流水号)打印"},
	            {"key":"外发清单", "value":"外发清单打印"},
	            {"key":"外发清单(流水)", "value":"外发清单(流水号)打印"},
	            {"key":"快递代理外发清单", "value":"快递代理外发清单打印"},
	            {"key":"快递代理外发清单(流水)", "value":"快递代理外发清单(流水号)打印"}
	        ]
	    })
	},{
		xtype: 'container',
		columnWidth: .6,
		html: '&nbsp;'
	},{
		columnWidth : .39,
		xtype : 'button',
		text : load.expresshandoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.printButtonText')/*'打印'*/,
		handler : function(){
			var upwindow = this.up('window'),
				printTemplate = upwindow.items.items[0].getValue(),
				records	= upwindow.grid.getSelectionModel().getSelection(),
				vehicleNo	= upwindow.vehicleNo,
				handOverBillNos = upwindow.handOverBillNos;
			
			var vehicleAssembleNo = '';
			var currentDeptCode = FossUserContext.getCurrentUserDept().code;
			var currentDeptName = FossUserContext.getCurrentUserDept().name;
			var currentUserCode = FossUserContext.getCurrentUser().employee.empCode;
			var currentUserName = FossUserContext.getCurrentUser().employee.empName;
			if(printTemplate == '配载单' || printTemplate == '配载单(流水)') {
				//如果选择为配载单则需要先从数据库中根据交接单号查出配载单号
				Ext.Ajax.request({
					url : load.realPath('getVehicleassembleNoByHandoverNo.action'),
					params : {'handOverBillVo.handOverBillNo' : handOverBillNos[0]},
					success : function(response) {
						var result = Ext.decode(response.responseText),
							vehicleAssembleNo = result.handOverBillVo.vehicleassembleNo;
						if(vehicleAssembleNo == null || vehicleAssembleNo == '') {
							Ext.ux.Toast.msg(load.expresshandoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.alertInfoTitle')/*'提示'*/, 
									"配载单尚未生成, 不能打印!", 
									'error');
						} else {
							if(printTemplate == '配载单') {
								do_printpreview('load',{"vehicleassemblebill": handOverBillNos, 
									"currentDeptName" : currentDeptName, "currentUserName" : currentUserName,
									"currentDeptCode" : currentDeptCode, "currentUserCode" : currentUserCode}, ContextPath.TFR_EXECUTION)
							} else if (printTemplate == '配载单(流水)') {
								do_printpreview('load',{"vehicleassemblebillsn": handOverBillNos, 
									"currentDeptName" : currentDeptName, "currentUserName" : currentUserName,
									"currentDeptCode" : currentDeptCode, "currentUserCode" : currentUserCode}, ContextPath.TFR_EXECUTION)
							}
						}
					}
				});
			} else {
				if (printTemplate == '交接单') {
					do_printpreview('load',{"handOverBillNos": handOverBillNos, 
						"currentDeptName" : currentDeptName, "currentUserName" : currentUserName,
						"currentDeptCode" : currentDeptCode, "currentUserCode" : currentUserCode}, ContextPath.TFR_EXECUTION)
				} else if (printTemplate == '交接单(流水)') {
					do_printpreview('loadsn',{"handOverBillNos": handOverBillNos, 
						"currentDeptName" : currentDeptName, "currentUserName" : currentUserName,
						"currentDeptCode" : currentDeptCode, "currentUserCode" : currentUserCode}, ContextPath.TFR_EXECUTION)
				} else if (printTemplate == '外发清单') {
					do_printpreview('partialline',{"handOverBillNos": handOverBillNos,
						"currentDeptName" : currentDeptName, "currentUserName" : currentUserName,
						"currentDeptCode" : currentDeptCode, "currentUserCode" : currentUserCode}, ContextPath.TFR_EXECUTION)
				} else if (printTemplate == '外发清单(流水)') {
					do_printpreview('partiallinesn',{"handOverBillNos": handOverBillNos, 
						"currentDeptName" : currentDeptName, "currentUserName" : currentUserName,
						"currentDeptCode" : currentDeptCode, "currentUserCode" : currentUserCode}, ContextPath.TFR_EXECUTION)
				}else if (printTemplate == '快递代理外发清单') {
					do_printpreview('ldppartialline',{
						"handOverBillNos": handOverBillNos,
						"currentDeptName" : currentDeptName, "currentUserName" : currentUserName,
						"currentDeptCode" : currentDeptCode, "currentUserCode" : currentUserCode}, ContextPath.TFR_EXECUTION)
				} else if (printTemplate == '快递代理外发清单(流水)') {
					do_printpreview('ldppartiallinesn',{
						"handOverBillNos": handOverBillNos, 
						"currentDeptName" : currentDeptName, "currentUserName" : currentUserName,
						"currentDeptCode" : currentDeptCode, "currentUserCode" : currentUserCode}, ContextPath.TFR_EXECUTION)
				}
			}
		}
	}]
});

// 定义交接单明细列表
Ext.define('foss.load.expresshandoverbilladdnew.HandOverBillDetailGrid', {
	extend : 'Ext.grid.Panel',
	frame : true,
	title : load.expresshandoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.gridTitle')/*'交接单明细'*/,
//	bodyCls : 'autoHeight',
	height : 500,
	cls : 'autoHeight',
	enableColumnHide : false,//配置该属性可取消grid自定义显示列功能
	columnLines: true,
	autoScroll : true,
	collapsible : true,
	animCollapse : true,
	store : Ext.create('foss.load.expresshandoverbilladdnew.HandOverBillDetailStore'),
	// 定义行展开
	plugins : [ {
		header: true,
		ptype : 'rowexpander',
		// 定义行展开模式（单行与多行），默认是多行展开(值true)
		rowsExpander : false,
		// 行体内容
		rowBodyElement : 'foss.load.expresshandoverbilladdnew.WaybillDetailGrid',
		pluginId : 'Foss_expresshandoverbilladdnew_mainPage_serialNoGrid_ID'
	},Ext.create('Ext.grid.plugin.CellEditing', {
            clicksToEdit : 1,
            listeners : {
				'beforeedit' : function(editor,e,eOpts){
					//如果已经保存，则禁止编辑
					if(load.expresshandoverbilladdnew.isSaved == 'Y'){
						return false;
					}
				}
			}
        })],
	dockedItems: [{
	    xtype: 'toolbar',
	    dock: 'bottom',
	    layout : 'column',
	    defaults: {
			xtype : 'textfield',
			readOnly : true,
			labelWidth : 120
		},
		items: [{
			fieldLabel : load.expresshandoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.totalWaybillLabel')/*'总票数'*/,
			xtype : 'textfield',
			readOnly : true,
			columnWidth : 1/5,
			value : 0,
			id : 'Foss_load_expresshandoverbilladdnew_MainPageTotalCount'
			},{
				fieldLabel : load.expresshandoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.totalPiecesLabel')/*'总件数'*/,
				xtype : 'textfield',
				readOnly : true,
				columnWidth : 1/5,
				value : 0,
				id : 'Foss_load_expresshandoverbilladdnew_MainPageTotalPieces'
			},{
				fieldLabel : '总重量(千克)',
				xtype : 'textfield',
				readOnly : true,
				columnWidth : 1/5,
				value : 0,
				id : 'Foss_load_expresshandoverbilladdnew_MainPageTotalWeight'
			},{
				fieldLabel : '总体积(方)',
				xtype : 'textfield',
				readOnly : true,
				columnWidth : 1/5,
				value : 0,
				id : 'Foss_load_expresshandoverbilladdnew_MainPageTotalVolume'
			},{
				fieldLabel : '未转换总体积(方)',
				xtype : 'textfield',
				readOnly : true,
				columnWidth : 1/5,
				value : 0,
				id : 'Foss_load_expresshandoverbilladdnew_MainPageUnConvertTotalVolume'
			}]
	  }],
	tbar : ['->',{
		xtype : 'button',
		disabled: !load.expresshandoverbilladdnew.isPermission('load/printnewhandoverbillButton'),
		hidden: !load.expresshandoverbilladdnew.isPermission('load/printnewhandoverbillButton'),
		text : load.expresshandoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.printButtonText')/*'打印'*/,
		handler : function(){
			if(load.expresshandoverbilladdnew.prthandOverBillNo == undefined) {
				Ext.ux.Toast.msg(load.expresshandoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.alertInfoTitle')/*'提示'*/, 
						"请先保存交接单!", 
						'error');
				return;
			}
			var records = this.up('grid').getStore().getRange();
			if(records.length <= 0) {
				Ext.ux.Toast.msg(load.expresshandoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.alertInfoTitle')/*'提示'*/, 
						"没有运单数据, 请确认!", 
				'error');
				return;
			}
			var vehicleNo = load.expresshandoverbilladdnew.addNewForm.getForm().findField('vehicleNo').getValue(),
				handOverBillNos = new Array(),
				handOverBillNo = load.expresshandoverbilladdnew.addNewForm.getForm().findField('handOverBillNo').getValue();
			handOverBillNos.push(handOverBillNo);
			//如果选择的交接单的车牌号下还有其他的交接单则提示还有其他交接单,请注意
			Ext.Ajax.request({
				url : load.realPath('checkPrintHandOverBill.action'),
				params : {'handOverBillVo.vehicleNo' : vehicleNo,
					'handOverBillVo.handOverBillNos' : handOverBillNos
				},
				success : function(response) {
					var result = Ext.decode(response.responseText),
						count = result.handOverBillVo.checkPrintHandOverBillRestlt;
					if(count > 0) {
						//大于0则说明该车牌号下还有其他交接单尚未选择
						Ext.ux.Toast.msg(load.expresshandoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.alertInfoTitle')/*'提示'*/, 
								"此车牌中还有" + count + "个交接单没有选择打印，请注意!", 
								'error');
					} else {
						
					}
				}
			});
			Ext.create('Foss.load.expresshandoverbilladdnew.PrintWindow', {
				vehicleNo : vehicleNo,
				handOverBillNos : handOverBillNos,
				grid : this.up('grid')
			}).show();
		}
	}],
	columns : [ {
		xtype : 'actioncolumn',
		width : 60,
		text : load.expresshandoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.actionColumn')/*'操作'*/,
		align : 'center',
		items : [ {
			iconCls : 'deppon_icons_remove',
			tooltip : load.expresshandoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.deleteButtonColumn')/*'删除'*/,
			handler : function(grid, rowIndex, colIndex) {
				grid.getStore().removeAt(rowIndex);
			}
		} ]
	}, {
		dataIndex : 'waybillNo',
		align : 'center',
		width : 100,
		xtype : 'ellipsiscolumn',
		text : load.expresshandoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.waybillNoColumn')/*'运单号'*/
	}, {
		dataIndex : 'transProperty',
		align : 'center',
		width : 95,
		text : load.expresshandoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.transPropertyColumn')/*'运输性质'*/
	},{
		dataIndex:'transPropertyCode',
		align : 'center',
		hidden: true,
		width : 95,
		text :'运输性质'
	}, {
		dataIndex : 'pieces',
		align : 'center',
		flex : 1,
		text : load.expresshandoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.piecesColumn')/*'已配件数'*/
	}, {
		dataIndex : 'weight',
		align : 'center',
		flex : 1,
		text : load.expresshandoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.weightColumn')/*'已配重量'*/
	}, {
		dataIndex : 'weightAc',
		align : 'center',
		flex : 1,
		text : load.expresshandoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.weightAcColumn')/*'实际重量'*/,
		editor : {
			xtype : 'numberfield',
			allowBlank : false,
			step : 10,
			minValue : 0,
			validator : function(value){
				if(value <= 0){
					return false;
				}
				return true;
			}
		}
	}, {
		dataIndex : 'cubage',
		align : 'center',
		flex : 1,
		text : load.expresshandoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.volumeColumn')/*'已配体积'*/
	}, {
		dataIndex : 'cubageAc',
		align : 'center',
		flex : 1,
		text : load.expresshandoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.volumeAcColumn')/*'实际体积'*/,
		editor : {
			xtype : 'numberfield',
			allowBlank : false,
			step : 1,
			minValue : 0,
			validator : function(value){
				if(value <= 0){
					return false;
				}
				return true;
			}
		}
	}, {
		dataIndex : 'note',
		align : 'center',
		xtype : 'ellipsiscolumn',
		flex : 1,
		text : load.expresshandoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.noteColumn')/*'备注'*/,
		editor : {
			xtype : 'textarea',
			maxLength : 300
		}
	}, {
		dataIndex : 'goodsName',
		align : 'center',
		flex : 1,
		text : load.expresshandoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.goodsNameColumn')/*'货物名称'*/
	}, {
		dataIndex : 'packing',
		align : 'center',
		flex : 1,
		text : load.expresshandoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.packingColumn')/*'包装'*/
	}, {
		dataIndex : 'waybillNote',
		align : 'center',
		flex : 1,
		text : load.expresshandoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.waybillNoteColumn')/*'运单备注'*/
	} ]
});

//生成当前日期
load.expresshandoverbilladdnew.handOverBillGetDateTime = function(){
	var today = new Date();
	var dd = today.getDate();
	var mm = today.getMonth()+1; //January is 0!
	var yyyy = today.getFullYear();
	var hh = today.getHours();
	var minutes = today.getMinutes();
	var ss=today.getSeconds();
	if(dd<10){dd='0'+dd;} 
	if(mm<10){mm='0'+mm;} 
	if(hh<10){hh='0'+hh;}
	if(minutes<10){minutes='0'+minutes;}
	if(ss<10){ss='0'+ss;}
	var today = yyyy+'-'+mm+'-'+dd+' '+hh+':'+minutes+':'+ss;
	return today;
}

// 交接单基本信息form
Ext.define('foss.load.expresshandoverbilladdnew.AddNewForm', {
	extend : 'Ext.form.Panel',
	title : load.expresshandoverbilladdnew.i18n('foss.load.handoverbilladdnew.basicInfoForm.formTitle'),
	frame : true,
	collapsible : true,
	height : 190,
	animCollapse : true,
	defaults : {
		margin : '5 10 5 10',
		labelWidth : 85,
		columnWidth : 1 / 4,
		xtype : 'textfield'
	},
	layout : 'column',
	items : [{
		fieldLabel : load.expresshandoverbilladdnew.i18n('foss.load.handoverbilladdnew.basicInfoForm.handOverTypeLabel')/*'交接类型'*/,
		name : 'handOverType',
		xtype : 'combobox',
		queryMode: 'local',
		allowBlank : false,
	    displayField: 'value',
	    valueField: 'key',
	    editable : false,
	    store : Ext.create('Ext.data.Store', {
	        fields: ['key', 'value'],
	        data : [
	            {"key":"SHORT_DISTANCE_HANDOVER", "value":"短配交接单"},
	            {"key":"LONG_DISTANCE_HANDOVER", "value":"集配交接单"},
	            {"key":"DIVISION_HANDOVER", "value":"分部交接单"}
	        ]
	    })
	}, {
		fieldLabel : load.expresshandoverbilladdnew.i18n('foss.load.handoverbilladdnew.basicInfoForm.handOverBillNoLabel')/*'交接单编号'*/,
		name : 'handOverBillNo',
		allowBlank : false,
		readOnly : true,
		value : load.expresshandoverbilladdnew.handOverBillNo
	}, {
		fieldLabel : load.expresshandoverbilladdnew.i18n('foss.load.handoverbilladdnew.basicInfoForm.handOverTimeLabel')/*'交接时间'*/,
		name : 'handOverTime',
		value : load.expresshandoverbilladdnew.handOverBillGetDateTime(),
		readOnly : true
	}, {
		fieldLabel : load.expresshandoverbilladdnew.i18n('foss.load.handoverbilladdnew.basicInfoForm.departDeptLabel')/*'出发部门'*/,
		name : 'departDept',
		readOnly : true,
		value : load.expresshandoverbilladdnew.departOrgName
	}, {
		xtype : 'hidden',
		name : 'departDeptCode'
	}, {
		fieldLabel : load.expresshandoverbilladdnew.i18n('foss.load.handoverbilladdnew.basicInfoForm.arriveDeptLabel')/*'到达部门'*/,
		readOnly : true,
		name : 'arriveDept'
	}, {
		xtype : 'hidden',
		name : 'arriveDeptCode'
	}, {
		fieldLabel : load.expresshandoverbilladdnew.i18n('foss.load.handoverbilladdnew.basicInfoForm.vehicleNoLabel')/*'车牌号'*/,
		allowBlank : false,
		xtype : 'commontruckselector',
		queryParam : 'truckVo.truck.vehicleNoNoLike',
		queryAllFlag : false,
		name : 'vehicleNo',
		listeners : {
			'blur' : function(cmp,eObject,eOpts){
				if(!Ext.isEmpty(cmp.getValue())){
					var form = this.up('form').getForm();
					//获取车辆类型（托头 、挂车...）
					var vehicleEntity = cmp.store.findRecord('vehicleNo',cmp.getValue(),0,false,true,true);
					if(!Ext.isEmpty(vehicleEntity)){
						var vehicleType=vehicleEntity.raw.vehicleType;
						if(vehicleType=='vehicletype_trailer'){
							form.findField('beTrailerVehicleNo').setValue('Y');
						}else{
							form.findField('beTrailerVehicleNo').setValue(null);
						}
						
					}
					//如果司机已经输入，则不读取后台绑定关系
					if(Ext.isEmpty(form.findField('driver').getValue())){
						//后台获取司机信息
						var myMask = new Ext.LoadMask(this.up('form'), {
							msg:"加载中，请稍候..."
						});
						myMask.show();
						Ext.Ajax.request({
							url : load.realPath('queryDriverInfoByVehicleNoForHandOverBill.action'),
							params : {'handOverBillVo.vehicleNo' : cmp.getValue()},
							success : function(response){
								var result = Ext.decode(response.responseText);
								var driverInfo = result.handOverBillVo.driverInfo;
								//如果“司机”为空，则不覆盖原来选择的值
								var driverCmp = form.findField('driver');
								var driverTelCmp = form.findField('driverTel');
								if(Ext.isEmpty(driverCmp.getValue())){
									if(driverInfo != null){
										var driverCode = driverInfo.driverCode;
										driverCmp.setCombValue(driverInfo.driverName,driverCode);
										driverTelCmp.setValue(driverInfo.driverPhone);
									}
								}
								myMask.hide();
							},
							exception : function(response){
								myMask.hide();
							}
						});
					}
				}
			}
		}
	}, {
		fieldLabel:'是否挂牌号',
		xtype : 'textfield',
		hidden:true,
		name:'beTrailerVehicleNo'
	}, {
		fieldLabel : load.expresshandoverbilladdnew.i18n('foss.load.handoverbilladdnew.basicInfoForm.driverLabel')/*'司机'*/,
		allowBlank : false,
		name : 'driver',
		xtype : 'commondriverselector',
		listeners : {
			'change' : function(field, newValue,oldValue,eOpts){
				var form = this.up('form').getForm();
				if(field.isValid()){
					//如果司机不为空，则更新司机电话
					if(!Ext.isEmpty(newValue)){
						var store = field.store;
						var driverRecord = store.findRecord('empCode',newValue,0,false,true,true);
						if(driverRecord == null){
							form.findField('driverTel').reset();
						}else{
							form.findField('driverTel').setValue(driverRecord.get('empPhone'));						
						}
					}
				}else{
					form.findField('driverTel').reset();
				}
			}
		}
	}, {
		fieldLabel : load.expresshandoverbilladdnew.i18n('foss.load.handoverbilladdnew.basicInfoForm.driverPhoneLabel')/*'司机电话'*/,
		name : 'driverTel',
		readOnly : true
	}, {
		fieldLabel : load.expresshandoverbilladdnew.i18n('foss.load.handoverbilladdnew.basicInfoForm.loadEndTimeLabel')/*'装车完成时间'*/,
		name : 'loadEndTime',
		xtype : 'datetimefield_date97',
		time : true,
		id : 'Foss_expresshandoverbilladdnew_loadEndTime_ID',
		allowBlank:false,
		dateConfig: {
			el : 'Foss_expresshandoverbilladdnew_loadEndTime_ID-inputEl'
		}
	}, FossDataDictionary.getDataDictionaryCombo('LOAD_GOODS_TYPE',{
		fieldLabel : load.expresshandoverbilladdnew.i18n('foss.load.handoverbilladdnew.basicInfoForm.goodsTypeLabel')/*'货物类型'*/,
		labelWidth : 85,
		allowBlank : false,
		readOnly : true,
		value : 'ALL',
		name : 'goodsType',
		editable : false
	}),{
		fieldLabel : load.expresshandoverbilladdnew.i18n('foss.load.handoverbilladdnew.basicInfoForm.creatorLabel')/*'制单人'*/,
		name : 'createUser',
		readOnly : true,
		value : FossUserContext.getCurrentUserEmp().empName
	}, {
		fieldLabel : load.expresshandoverbilladdnew.i18n('foss.load.handoverbilladdnew.basicInfoForm.modifyManLabel')/*'修改人'*/,
		name : 'modifyUser',
		readOnly : true,
		value : FossUserContext.getCurrentUserEmp().empName
	}, {
		fieldLabel : load.expresshandoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.noteColumn')/*'备注'*/,
		name : 'note',
		columnWidth : .5,
		maxLength : 300
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
});

//定义控件的弹出框方法
load.expresshandoverbilladdnew.handOverBillAlert = function(cmp,icon){
	Ext.MessageBox.show({
        title: load.expresshandoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.alertInfoTitle')/*'提示'*/,
        msg: '请输入' + cmp.fieldLabel,
        buttons: Ext.MessageBox.OK,
        animateTarget: cmp,
        icon: icon,
        fn : function(){
 		   cmp.focus(false,100);
 	   }
    });
}

// 定义运单列表
load.expresshandoverbilladdnew.handOverBillDetailGrid = Ext.create('foss.load.expresshandoverbilladdnew.HandOverBillDetailGrid');

//加载包数据方法
load.expresshandoverbilladdnew.loadExpressPackageInfo = function(){
	var packageNo = load.expresshandoverbilladdnew.packageNo;
	var data = {
		'expressHandOverBillVo' : {
			'packageNo' : packageNo
		}
	};
	//加载数据
	var myMask = new Ext.LoadMask(Ext.getCmp('T_load-expresshandoverbilladdnewindex_content'), {
		msg:"包数据加载中，请稍候..."
	});
	myMask.show();
	Ext.Ajax.request({
		url : load.realPath('loadExpressPackageInfo.action'),
		jsonData : data,
		success : function(response){
			var result = Ext.decode(response.responseText);
			var vo = result.expressHandOverBillVo,
				waybillList = vo.waybillList,
				entity = vo.handOverBillEntity;
			load.expresshandoverbilladdnew.rate=vo.expressConverParameter/1000;
			//加载运单明细信息
			load.expresshandoverbilladdnew.handOverBillDetailGrid.store.loadData(waybillList);
			//加载基本信息
			var form = load.expresshandoverbilladdnew.addNewForm.getForm();
			var entityRec = Ext.ModelManager.create(entity,'Foss.load.expresshandoverbilladdnew.HandOverBillModel');
			form.loadRecord(entityRec);
			myMask.hide();
		},
		exception : function(response) {
			var result = Ext.decode(response.responseText);
			top.Ext.MessageBox.alert('提示',result.message);
			myMask.hide();
			Ext.getCmp('mainAreaPanel').remove('T_load-expresshandoverbilladdnewindex',true);
		}
	});
}

Ext.onReady(function() {
	// Ext.QuickTips.init();
	//定义变量，记录交接单是否已被保存
	load.expresshandoverbilladdnew.isSaved = 'N';
	// 定义基本信息表单
	var addNewForm = load.expresshandoverbilladdnew.addNewForm = Ext.create('foss.load.expresshandoverbilladdnew.AddNewForm');
	//如果当前登录部门为营业部，则交接类型只能为“短配交接单”BUG-5383
	var handOverTypeCmp = addNewForm.getForm().findField('handOverType'),
		vehicleNoCmp = addNewForm.getForm().findField('vehicleNo');
	
	if(load.expresshandoverbilladdnew.beSalesDept == 'Y'){
		handOverTypeCmp.setReadOnly(true);
		handOverTypeCmp.setValue('SHORT_DISTANCE_HANDOVER');
	}
	
	function updateHandOverTime(){
		addNewForm.getForm().findField('handOverTime').setValue(load.expresshandoverbilladdnew.handOverBillGetDateTime());
	}
	var intervalControl = window.setInterval(updateHandOverTime,1000);
	Ext.create('Ext.panel.Panel', {
		id : 'T_load-expresshandoverbilladdnewindex_content',
		cls : "panelContentNToolbar",
		bodyCls : 'panelContent-body',
		layout : 'auto',//
		items : [ addNewForm, load.expresshandoverbilladdnew.handOverBillDetailGrid,{
			xtype : 'container',
			columnWidth : 1,
			layout : 'column',
			items : [ {
					xtype : 'container',
					columnWidth : .92,
					html : '&nbsp'
			}, {
				columnWidth : .08,
				xtype : 'button',
				cls : 'yellow_button',
				name : 'saveButton',
				id : 'Foss_load_expresshandoverbilladdnew_mainPage_saveButton_ID',
				text : load.expresshandoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.saveButtonText')/*'保存'*/,
				handler : function() {
					var form = addNewForm.getForm();
					if(form.isValid()){
						//若未添加任何运单，则无法保存
						if(load.expresshandoverbilladdnew.handOverBillDetailGrid.store.getCount() == 0){
							Ext.Msg.show({
							     title : load.expresshandoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.alertInfoTitle')/*'提示'*/,
							     msg : '未添加任何运单',
							     buttons : Ext.Msg.OK,
							     icon: Ext.Msg.WARNING
							});
							return;
						}
						//通过基本信息form.getValues()获取一级实体
						var handOverBillEntity = form.getValues(),
							  //遍历表格store，封装二级实体列表
							  waybillStore = load.expresshandoverbilladdnew.handOverBillDetailGrid.store;
						//由于装车完成时间和交接时间getValue返回的是字符串，故此处转换为date类型，重新设置属性
						var reg=new RegExp('-','g');
						if(!Ext.isEmpty(handOverBillEntity.loadEndTime)){
							handOverBillEntity.loadEndTime = new Date(handOverBillEntity.loadEndTime.replace(reg,'/'));
						}
						handOverBillEntity.handOverTime = new Date(handOverBillEntity.handOverTime.replace(reg,'/'));
						
						//如果司机不为空，则获取司机名称
						var driverCmp = form.findField('driver');
						var driverName;
						if(!Ext.isEmpty(driverCmp.getValue())){
							driverName = driverCmp.store.findRecord('empCode',driverCmp.getValue(),0,false,true,true).get('empName');
							//在对象中加入司机姓名
							Ext.Object.merge(handOverBillEntity,{
								'driverName' : driverName
							});
						}
						
						//运单list//流水号list
						var waybillList = [],	
							serialNoList = [];
						for(var i = 0;i < waybillStore.getCount();i++){
							var record = waybillStore.getAt(i),
								waybill = record.data;
							//获取流水号
							var tempSerialNoList = waybill.serialNoStockList;
							for(var j in tempSerialNoList){
								serialNoList.push(tempSerialNoList[j]);
							}
							
							waybillCopy = Ext.clone(waybill);
							delete waybillCopy.serialNoStockList;
							waybillList.push(waybillCopy);
						}
						
						//构造传到后台的json数据
						var data = {
								'expressHandOverBillVo' : {'newHandOverBillDto': {
									'handOverBillEntity' : handOverBillEntity,
									'waybillStockList' : waybillList,
									'serialNoStockList' : serialNoList
								}
							}
						};
						//mask
						var mainPanel = Ext.getCmp('T_load-expresshandoverbilladdnewindex_content');
						var myMask = new Ext.LoadMask(mainPanel, {msg : "数据提交中，请稍等..."});
		 				myMask.show();
						//保存交接单数据
						Ext.Ajax.request({
							url : load.realPath('saveExpressHandOverBill.action'),
							jsonData : data,
							timeout : 300000,
							success : function(response){
								//获取后台重新生成的交接单号
								var result = Ext.decode(response.responseText);
								//提示保存成功，展示交接单号
								load.expresshandoverbilladdnew.prthandOverBillNo = handOverBillEntity.handOverBillNo;
								Ext.Msg.show({
								     title : load.expresshandoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.alertInfoTitle')/*'提示'*/,
								     msg : '保存成功，交接单号为：' + handOverBillEntity.handOverBillNo,
								     buttons : Ext.Msg.OK,
								     icon: Ext.Msg.INFO
								});
								load.expresshandoverbilladdnew.isSaved = 'Y';
								myMask.hide();
								
								//设置form所有控件为只读
								var formCmps = form.getFields().getRange(0,form.getFields().getCount());
								for(var i in formCmps){
									formCmps[i].setReadOnly(true);
								}
								//隐藏“保存”按钮
								Ext.getCmp('Foss_load_expresshandoverbilladdnew_mainPage_saveButton_ID').setVisible(false);
								
								//隐藏运单grid和流水号grid前的操作列并设置为不可用
								load.expresshandoverbilladdnew.handOverBillDetailGrid.columns[1].setVisible(false);
								//获取展开的流水号grid
								var plugin = load.expresshandoverbilladdnew.handOverBillDetailGrid.getPlugin('Foss_expresshandoverbilladdnew_mainPage_serialNoGrid_ID');
								var pluginGrid = plugin.getExpendRowBody();
								if(pluginGrid != null){
									pluginGrid.columns[0].setVisible(false);
								}
							},
							exception : function(response) {
			    				var result = Ext.decode(response.responseText);
			    				top.Ext.MessageBox.alert(load.expresshandoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.alertInfoTitle')/*'提示'*/,'保存失败，' + result.message);
			    				myMask.hide();
			    			},
			    			failure : function(){
			    				myMask.hide();
			    				console.log('保存交接单时服务端异常！');
			    			}
						});
					}
				}
			} ]
		} ],
		renderTo : 'T_load-expresshandoverbilladdnewindex-body'
	});
	//加载数据
	load.expresshandoverbilladdnew.loadExpressPackageInfo();
});