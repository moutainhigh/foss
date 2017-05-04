//定义交接单基本信息Model
Ext.define('Foss.load.handoverbillmodifypda.HandOverBillBaseInfoModel', {
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
		name : 'arriveDept',
		type : 'string'
	}, {
		name : 'agency',
		type : 'string'
	}, {
		name : 'vehicleNo',
		type : 'string'
	}, {
		name : 'createUserName',
		type : 'string'
	}, {
		name : 'driver',
		type : 'string'
	} ,{
		name : 'volumeTotal',
		type : 'number'
	},{
		name : 'goodsQtyTotal',
		type : 'number'
	},{
		name : 'moneyTotal',	
		type : 'number'
	},{
		name : 'waybillQtyTotal',
		type : 'number'
	},{
		name : 'weightTotal',
		type : 'number'
	},{
		name : 'driverTel',
		type : 'string'
	},{
		name : 'loadEndTime',
		type : 'date',
		convert : function(value) {
			if(!value) return '';
			var date = new Date(value);						
			var formatStr = 'Y-m-d H:i:s';
			return Ext.Date.format(date, formatStr);
		}
	},{
		name : 'goodsType',
		type : 'string'
	},{
		name : 'modifyUserName',
		type : 'string'
	},{
		name : 'note',
		type : 'string'
	},{
		name : 'isAgencyVisit',
		type : 'string'
	},{
		name : 'handOverBillState',
		type : 'number'
	},{
		name : 'isCreatedByPDA',
		type : 'string'
	},{
		name : 'codAmountTotal',
		type : 'number'
	},{
		name : 'isCarLoad',
		type : 'string'
	},{
		name : 'tranGoodsType',
		type : 'string'
	},{
		name : 'beTrailerVehicleNo',
		tyep : 'string' 
	}]
});

//定义交接单明细Model
Ext.define('Foss.load.handoverbillmodifypda.HandOverBillDetailModel', {
	extend : 'Ext.data.Model',
	// 定义字段
	fields : [{
		name : 'handOverBillNo',
		type : 'string'
	}, {
		name : 'waybillNo',
		type : 'string'
	}, {
		name : 'transProperty',
		type : 'string'
	}, {
		name : 'transPropertyCode',
		type : 'string'
	}, {
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
		convert: dateConvert
	},{
		name : 'arriveDept',
		type : 'string'
	},{
		name : 'insuranceValue',
		type : 'number'
	},{
		name : 'receiveOrgName',
		type : 'string'
	},{
		name : 'arriveDept',
		type : 'string'
	},{
		name : 'consignee',
		type : 'string'
	},{
		name : 'destination',
		type : 'string'
	},{
		name : 'origOrgCode',
		type : 'string'
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
		name : 'codAmount',
		type : 'number'
	},{
		name : 'isCarLoad',
		type : 'string'
	},{
		name : 'isJoinCar',
		type : 'number'
	}]
});

// 定义交接单明细store
Ext.define('Foss.load.handoverbillmodifypda.HandOverBillDetailStore', {
	extend : 'Ext.data.Store',
	// 绑定一个模型
	model : 'Foss.load.handoverbillmodifypda.HandOverBillDetailModel',
	// 定义一个代理对象
	proxy : {
		type : 'ajax',
		actionMethods:'POST',
		// 请求的url
		url : load.realPath('queryHandOverBillDetailByNo.action'),
		// 定义一个读取器
		reader : {
			// 以JSON的方式读取
			type : 'json',
			// 定义读取JSON数据的根对象
			root : 'handOverBillVo.waybillStockList',
			successProperty: 'success'
		}
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	},
	listeners : {
		'beforeload' : function(store, operation, eOpts){
			Ext.apply(operation, {
				params : {
					'handOverBillVo.handOverBillNo' : load.handoverbillmodifypda.handOverBillNo
				}
			});	
		},
		'load' : function(store, records, successful, eOpts){
			//交接单修改前的运单加载完毕后，存储于load.handoverbillmodifypda.oldWaybillMap
			for(var i in records){
				var record = records[i];
				var waybillNo = record.get('waybillNo');
				load.handoverbillmodifypda.oldWaybillMap.add(waybillNo,record);
			}
		},
		'update' : function(store,record,operation,modifiedFieldNames,eOpts){			
			var waybillNo = record.get('waybillNo');
			load.handoverbillmodifypda.updatedWaybillMap.add(waybillNo,record);
			load.handoverbillmodifypda.updateMainPageStaInfo(store);
		},
		'datachanged' : function(store,record,operation,modifiedFieldNames,eOpts){
			load.handoverbillmodifypda.updateMainPageStaInfo(store);
		}
	}
});

// 定义运单明细Model
Ext.define('Foss.load.handoverbillmodifypda.WaybillDetailModel', {
	extend : 'Ext.data.Model',
	//定义字段
	fields: [{
		name : 'handOverBillNo',
		type : 'string'
	},{
		name : 'waybillNo',
		type : 'string'
	},{
		name : 'serialNo',
		type : 'string'
	},{
		name : 'loadExceptionType',
		type : 'string'
	},{
		name : 'weight',
		type : 'number'
	},{
		name : 'volumn',
		type : 'number'
	}]
});

// 定义运单明细store
Ext.define('Foss.load.handoverbillmodifypda.WaybillDetailStore', {
	extend : 'Ext.data.Store',
	// 绑定一个模型
	model : 'Foss.load.handoverbillmodifypda.WaybillDetailModel',
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
});

// 定义运单明细grid
Ext.define('Foss.load.handoverbillmodifypda.WaybillDetailGrid', {
	extend : 'Ext.grid.Panel',
	columnLines: true,
	frame: true,
	enableColumnHide : false,//配置该属性可取消grid自定义显示列功能
	baseCls : 'handOverBill_addNew_serialNoGap',
	autoScroll : true,
	width : 121,
	store : Ext.create('Foss.load.handoverbillmodifypda.WaybillDetailStore'),
	columns : [{
		xtype : 'actioncolumn',
		width : 60,
		text : load.handoverbillmodifypda.i18n('foss.load.handoverbilladdnew.waybillGrid.actionColumn')/*'操作'*/,
		align : 'center',
		items : [ {
			tooltip : load.handoverbillmodifypda.i18n('foss.load.handoverbilladdnew.waybillGrid.deleteButtonColumn')/*'删除'*/,
			iconCls : 'deppon_icons_remove',
			handler : function(grid, rowIndex, colIndex) {
				//整车运单，不可删除运单、流水号
				var isCarLoad = load.handoverbillmodifypda.basicInfoRecord.get('isCarLoad');
				if(isCarLoad == 'Y'){
					Ext.ux.Toast.msg(load.handoverbillmodifypda.i18n('foss.load.handoverbilladdnew.waybillGrid.alertInfoTitle')/*'提示'*/, '“整车”交接单无法删除货物，请直接作废交接单！', 'error', 3000);
					return;
				}
				if(grid.store.getCount() == 1){
					Ext.ux.Toast.msg(load.handoverbillmodifypda.i18n('foss.load.handoverbilladdnew.waybillGrid.alertInfoTitle')/*'提示'*/, load.handoverbillmodifypda.i18n('foss.load.handoverbilladdnew.waybillGrid.deleteWaybillAlert')/*'请直接删除运单'*/, 'error', 1500);
					return;
				}
				var record = grid.store.getAt(rowIndex);
				var waybillNo = record.get('waybillNo');
				var serialNo = record.get('serialNo');
				var sweight = record.get('weight');
				var svolumn = record.get('volumn');
				//从主页面的流水号map中删除该流水号
				var serialMap = load.handoverbillmodifypda.allSerialNoMap.get(waybillNo);
				serialMap.removeAtKey(serialNo);
				load.handoverbillmodifypda.allSerialNoMap.add(waybillNo,serialMap);
				//如果修改前的流水号map中存在有该流水号，则将该流水号放于load.handoverbillmodifypda.deletedSerialNoMap
				//若为第一次删除，则新建map，如果不是，则取出map，加入该流水号后重新放入
				if(load.handoverbillmodifypda.deletedSerialNoMap.get(waybillNo) == null){
					var tempMap = new Ext.util.HashMap();
					tempMap.add(serialNo,record);
					load.handoverbillmodifypda.deletedSerialNoMap.add(waybillNo,tempMap);
				}else{
					var tempMap = load.handoverbillmodifypda.deletedSerialNoMap.get(waybillNo);
					tempMap.add(serialNo,record);
					load.handoverbillmodifypda.deletedSerialNoMap.add(waybillNo,tempMap);
				}					
				//更新一级表格内容
				waybillStore = load.handoverbillmodifypda.handOverBillDetailGrid.store;
				var waybillRecord = waybillStore.findRecord('waybillNo', waybillNo, 0, false, false, true);
				waybillRecord.set('weight',(waybillRecord.get('weight')-(waybillRecord.get('weight')/waybillRecord.get('pieces'))).toFixed(2));
				waybillRecord.set('cubage',(waybillRecord.get('cubage')-(waybillRecord.get('cubage')/waybillRecord.get('pieces'))).toFixed(2));
				waybillRecord.set('weightAc',waybillRecord.get('weightAc')-sweight);
				waybillRecord.set('cubageAc',waybillRecord.get('cubageAc')-svolumn);
				waybillRecord.set('pieces',waybillRecord.get('pieces') - 1);
				grid.store.removeAt(rowIndex);
			}
		} ]
	}, {
		dataIndex : 'serialNo',
		align : 'center',
		flex : .45,
		xtype : 'ellipsiscolumn',
		text : load.handoverbillmodifypda.i18n('foss.load.handoverbilladdnew.waybillGrid.serialNoColumn')/*'流水号'*/
	}],
	bindData : function(record){
		var waybillNo = record.get('waybillNo');
		var serialNoRecords = load.handoverbillmodifypda.allSerialNoMap.get(waybillNo).getValues();
		this.store.loadData(serialNoRecords);
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
		if(load.handoverbillmodifypda.bePackage == 'N'){
			//若该交接单为PDA生成，则二级表格增加一列，同时表格加宽
			this.width = 271,
			this.columns.push(Ext.create('Ext.grid.column.Column',{
				dataIndex : 'loadExceptionType',
				align : 'center',
				width : 150,
				xtype : 'ellipsiscolumn',
				text : load.handoverbillmodifypda.i18n('foss.load.handoverbillshow.serialNoGrid.loadExceptionTypeColumn')/*'装车异常类型'*/,
				renderer : function(value){
					return FossDataDictionary.rendererSubmitToDisplay(value,'LOAD_EXCEPTION_TYPE');
				}
			}));
		}
	}
});

//定义打印模版window
Ext.define('Foss.load.handoverbillmodify.PrintWindow', {
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
		text : load.handoverbillmodifypda.i18n('foss.load.handoverbilladdnew.waybillGrid.printButtonText')/*'打印'*/,
		handler : function(){
			var upwindow = this.up('window'),
				printTemplate = upwindow.items.items[0].getValue(),
				records	= upwindow.grid.getSelectionModel().getSelection(),
				vehicleNo	= upwindow.vehicleNo,
				handOverBillNos = upwindow.handOverBillNos;
			
			var vehicleAssembleNo = '';
			var currentDeptCode = FossUserContext.getCurrentDept().code;
			var currentDeptName = FossUserContext.getCurrentDept().name;
			var currentUserCode = FossUserContext.getCurrentUser().employee.empCode;
			var currentUserName = FossUserContext.getCurrentUser().employee.empName;
			if(printTemplate == '配载单' || printTemplate == '配载单(流水)') {
				//如果选择为配载单则需要先从数据库中根据交接单号查出配载单号
				Ext.Ajax.request({
					url : load.realPath('getVehicleassembleNoByHandoverNo.action'),
					params : {'handOverBillVo.handOverBillNo' : load.handoverbillmodify.handOverBillNo},
					success : function(response) {
						var result = Ext.decode(response.responseText),
							vehicleAssembleNo = result.handOverBillVo.vehicleassembleNo;
						if(vehicleAssembleNo == null || vehicleAssembleNo == '') {
							Ext.ux.Toast.msg(load.handoverbillmodifypda.i18n('foss.load.handoverbilladdnew.waybillGrid.alertInfoTitle')/*'提示'*/, 
									"配载单尚未生成, 不能打印!", 
									'error');
						} else {
							if(printTemplate == '配载单') {
								do_printpreview('vehicleassemblebill',{
									"vehicleAssembleNos": vehicleAssembleNo, 
									"currentDeptName" : currentDeptName, "currentUserName" : currentUserName,
									"currentDeptCode" : currentDeptCode, "currentUserCode" : currentUserCode}, ContextPath.TFR_EXECUTION)
							} else if (printTemplate == '配载单(流水)') {
								do_printpreview('vehicleassemblebillsn',{
									"vehicleAssembleNos": vehicleAssembleNo, 
									"currentDeptName" : currentDeptName, "currentUserName" : currentUserName,
									"currentDeptCode" : currentDeptCode, "currentUserCode" : currentUserCode}, ContextPath.TFR_EXECUTION)
							}
						}
					}
				});
			} else {
				if (printTemplate == '交接单') {
					do_printpreview('load',{
						"handOverBillNos": load.handoverbillmodify.handOverBillNo, 
						"currentDeptName" : currentDeptName, "currentUserName" : currentUserName,
						"currentDeptCode" : currentDeptCode, "currentUserCode" : currentUserCode}, ContextPath.TFR_EXECUTION)
				} else if (printTemplate == '交接单(流水)') {
					do_printpreview('loadsn',{
						"handOverBillNos": load.handoverbillmodify.handOverBillNo, 
						"currentDeptName" : currentDeptName, "currentUserName" : currentUserName,
						"currentDeptCode" : currentDeptCode, "currentUserCode" : currentUserCode}, ContextPath.TFR_EXECUTION)
				} else if (printTemplate == '外发清单') {
					do_printpreview('partialline',{
						"handOverBillNos": load.handoverbillmodify.handOverBillNo,
						"currentDeptName" : currentDeptName, "currentUserName" : currentUserName,
						"currentDeptCode" : currentDeptCode, "currentUserCode" : currentUserCode}, ContextPath.TFR_EXECUTION)
				} else if (printTemplate == '外发清单(流水)') {
					do_printpreview('partiallinesn',{
						"handOverBillNos": load.handoverbillmodify.handOverBillNo, 
						"currentDeptName" : currentDeptName, "currentUserName" : currentUserName,
						"currentDeptCode" : currentDeptCode, "currentUserCode" : currentUserCode}, ContextPath.TFR_EXECUTION)
				} else if (printTemplate == '快递代理外发清单') {
					do_printpreview('ldppartialline',{
						"handOverBillNos": load.handoverbillmodify.handOverBillNo,
						"currentDeptName" : currentDeptName, "currentUserName" : currentUserName,
						"currentDeptCode" : currentDeptCode, "currentUserCode" : currentUserCode}, ContextPath.TFR_EXECUTION)
				} else if (printTemplate == '快递代理外发清单(流水)') {
					do_printpreview('ldppartiallinesn',{
						"handOverBillNos": load.handoverbillmodify.handOverBillNo, 
						"currentDeptName" : currentDeptName, "currentUserName" : currentUserName,
						"currentDeptCode" : currentDeptCode, "currentUserCode" : currentUserCode}, ContextPath.TFR_EXECUTION)
				}
			}
		}
	}]
});

// 定义交接单明细列表
Ext.define('Foss.load.handoverbillmodifypda.HandOverBillDetailGrid', {
	extend : 'Ext.grid.Panel',
	frame : true,
	title : load.handoverbillmodifypda.i18n('foss.load.handoverbilladdnew.waybillGrid.gridTitle')/*'交接单明细'*/,
//	bodyCls : 'autoHeight',
	enableColumnHide : false,//配置该属性可取消grid自定义显示列功能
	height : 500,
	cls : 'autoHeight',
	columnLines: true,
	autoScroll : true,
	collapsible : true,
	animCollapse : true,
	store : Ext.create('Foss.load.handoverbillmodifypda.HandOverBillDetailStore'),
	// 定义行展开
	plugins : [ {
		header : true,
		ptype : 'rowexpander',
		// 定义行展开模式（单行与多行），默认是多行展开(值true)
		rowsExpander : false,
		// 行体内容
		rowBodyElement : 'Foss.load.handoverbillmodifypda.WaybillDetailGrid',
		pluginId : 'Foss_handoverbillmodifypda_mainPage_serialNoGrid_ID'
	},Ext.create('Ext.grid.plugin.CellEditing', {
		id : 'Foss_load_handoverbillmodifypda_waybillEditor_ID',
        clicksToEdit : 1,
        listeners : {
				'beforeedit' : function(editor,e,eOpts){
					//如果已经保存，则禁止编辑
					if(load.handoverbillmodifypda.isSaved == 'Y'){
						return false;
					}
				}
			}
    })],
    tbar : [{
    	xtype : 'button',
    	text : load.handoverbillmodifypda.i18n('foss.load.handoverbilladdnew.waybillGrid.printButtonText')/*'打印'*/,
		handler : function(){
			var records = this.up('grid').getStore().getRange();
			var vehicleNo = load.handoverbillmodifypda.basicInfoRecord.get('vehicleNo'),
				isdiff = false,
				handOverBillNos = new Array();
			for(var i = 0; i < records.length; i++) {
				var handOverBillNo = records[i].get('handOverBillNo');
				handOverBillNos.push(handOverBillNo);
			}
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
						Ext.ux.Toast.msg(load.handoverbillmodifypda.i18n('foss.load.handoverbilladdnew.waybillGrid.alertInfoTitle')/*'提示'*/, 
								"此车牌中还有" + count + "个交接单没有选择打印，请注意!", 
								'error');
					} else {
						
					}
				}
			});
			Ext.create('Foss.load.handoverbillmodify.PrintWindow', {
				vehicleNo : vehicleNo,
				handOverBillNos : handOverBillNos,
				grid : this.up('grid')
			}).show();
		}
    }],
    dockedItems: [{
	    xtype: 'toolbar',
	    dock: 'bottom',
	    layout : 'column',
	    defaults: {
			xtype: 'textfield',
			readOnly:true,
			labelWidth:80
		},
		items: [{
			fieldLabel : load.handoverbillmodifypda.i18n('foss.load.handoverbilladdnew.waybillGrid.totalWaybillLabel')/*'总票数'*/,
			xtype : 'numberfield',
			readOnly : true,
			columnWidth : 1/5,
			value : 0,
			id : 'Foss_load_handoverbillmodifypda_MainPageTotalCount'
			},{
				fieldLabel : load.handoverbillmodifypda.i18n('foss.load.handoverbilladdnew.waybillGrid.totalPiecesLabel')/*'总件数'*/,
				xtype : 'numberfield',
				readOnly : true,
				columnWidth : 1/5,
				value : 0,
				id : 'Foss_load_handoverbillmodifypda_MainPageTotalPieces'
			},{
				fieldLabel : '总重量(千克)',
				xtype : 'numberfield',
				readOnly : true,
				labelWidth : 120,
				columnWidth : 1/5,
				value : 0,
				id : 'Foss_load_handoverbillmodifypda_MainPageTotalWeight'
			},{
				fieldLabel : '总体积(方)',
				xtype : 'numberfield',
				readOnly : true,
				columnWidth : 1/5,
				value : 0,
				id : 'Foss_load_handoverbillmodifypda_MainPageTotalVolume'
			},{
				fieldLabel : '未转换总体积(方)',
				xtype : 'numberfield',
				readOnly : true,
				labelWidth:120,
				columnWidth : 1/5,
				value : 0,
				id : 'Foss_load_handoverbillmodifypda_MainPageUnconvertTotalVolume'
			},{
				xtype : 'numberfield',
				readOnly : true,
				columnWidth : 0,
				hidden : true,
				id : 'Foss_load_handoverbillmodifypda_MainPageTotalCodAmount'
			}]
	  }],
	columns : [{
		xtype : 'actioncolumn',
		width : 60,
		text : load.handoverbillmodifypda.i18n('foss.load.handoverbilladdnew.waybillGrid.actionColumn')/*'操作'*/,
		align : 'center',
		items : [ {
			tooltip : load.handoverbillmodifypda.i18n('foss.load.handoverbilladdnew.waybillGrid.deleteButtonColumn')/*'删除'*/,
			iconCls : 'deppon_icons_remove',
			handler : function(grid, rowIndex, colIndex) {
				//整车运单，不可删除运单、流水号
				var isCarLoad = load.handoverbillmodifypda.basicInfoRecord.get('isCarLoad');
				if(isCarLoad == 'Y'){
					Ext.ux.Toast.msg(load.handoverbillmodifypda.i18n('foss.load.handoverbilladdnew.waybillGrid.alertInfoTitle')/*'提示'*/, '“整车”交接单无法删除货物，请直接作废交接单！', 'error', 3000);
					return;
				}
				var record = grid.store.getAt(rowIndex);
				var waybillNo = record.get('waybillNo');
				load.handoverbillmodifypda.allSerialNoMap.removeAtKey(waybillNo);
				//从load.handoverbillmodifypda.oldWaybillMap中取出该运单放于load.handoverbillmodifypda.deletedWaybillMap,流水号置于deletedSerialNoMap中
				load.handoverbillmodifypda.deletedWaybillMap.add(waybillNo,load.handoverbillmodifypda.oldWaybillMap.get(waybillNo));
				var serialNoMap = load.handoverbillmodifypda.oldSerialNoMap.get(waybillNo);
				load.handoverbillmodifypda.deletedSerialNoMap.add(waybillNo,serialNoMap);
				grid.store.removeAt(rowIndex);
			}
		} ]
	}, {
		dataIndex : 'waybillNo',
		align : 'center',
		width : 80,
		xtype : 'ellipsiscolumn',
		text : load.handoverbillmodifypda.i18n('foss.load.handoverbilladdnew.waybillGrid.waybillNoColumn')/*'运单号'*/
	}, {
		dataIndex : 'transProperty',
		align : 'center',
		flex : 1,
		text : load.handoverbillmodifypda.i18n('foss.load.handoverbilladdnew.waybillGrid.transPropertyColumn')/*'运输性质'*/
	}, {
		dataIndex : 'transPropertyCode',
		align : 'center',
		hidden : true, 
		width : 95,
		text : '运输性质编码'/*'运输性质'*/
	}, {
		dataIndex : 'pieces',
		align : 'center',
		flex : 1,
		text : load.handoverbillmodifypda.i18n('foss.load.handoverbilladdnew.waybillGrid.piecesColumn')/*'已配件数'*/
	}, {
		dataIndex : 'weight',
		align : 'center',
		flex : 1,
		text : load.handoverbillmodifypda.i18n('foss.load.handoverbilladdnew.waybillGrid.weightColumn')/*'已配重量'*/
	}, {
		dataIndex : 'weightAc',
		align : 'center',
		flex : 1,
		text : load.handoverbillmodifypda.i18n('foss.load.handoverbilladdnew.waybillGrid.weightAcColumn')/*'实际重量'*/
//		editor : {
//			xtype : 'numberfield',
//			allowBlank : false,
//			step : 10,
//			validator : function(value){
//				if(value <= 0){
//					return false;
//				}
//				return true;
//			}
//		}
	}, {
		dataIndex : 'cubage',
		align : 'center',
		flex : 1,
		text : load.handoverbillmodifypda.i18n('foss.load.handoverbilladdnew.waybillGrid.volumeColumn')/*'已配体积'*/
	}, {
		dataIndex : 'cubageAc',
		align : 'center',
		flex : 1,
		text : load.handoverbillmodifypda.i18n('foss.load.handoverbilladdnew.waybillGrid.volumeAcColumn')/*'实际体积'*/
//		editor : {
//			xtype : 'numberfield',
//			allowBlank : false,
//			step : 1,
//			validator : function(value){
//				if(value <= 0){
//					return false;
//				}
//				return true;
//			}
//		}
	}, {
		dataIndex : 'note',
		align : 'center',
		xtype : 'ellipsiscolumn',
		flex : 1,
		text : load.handoverbillmodifypda.i18n('foss.load.handoverbilladdnew.waybillGrid.noteColumn')/*'备注'*/,
		editor : {
			xtype : 'textfield',
			maxLength : 300
		}
	}, {
		dataIndex : 'goodsName',
		align : 'center',
		flex : 1,
		text : load.handoverbillmodifypda.i18n('foss.load.handoverbilladdnew.waybillGrid.goodsNameColumn')/*'货物名称'*/
	}, {
		dataIndex : 'packing',
		align : 'center',
		flex : 1,
		text : load.handoverbillmodifypda.i18n('foss.load.handoverbilladdnew.waybillGrid.packingColumn')/*'包装'*/
	}, {
		dataIndex : 'waybillNote',
		align : 'center',
		flex : 1,
		text : load.handoverbillmodifypda.i18n('foss.load.handoverbilladdnew.waybillGrid.waybillNoteColumn')/*'运单备注'*/
	}]
});

// 交接单基本信息form
Ext.define('Foss.load.handoverbillmodifypda.BasicInfoForm', {
	extend : 'Ext.form.Panel',
	title : load.handoverbillmodifypda.i18n('foss.load.handoverbilladdnew.basicInfoForm.formTitle')/*'交接单基本信息'*/,
	frame : true,
	collapsible : true,
	height : 230,
	animCollapse : true,
	defaults : {
		margin : '5 10 5 10',
		labelWidth : 85,
		columnWidth : 1 / 4,
		xtype : 'textfield',
		readOnly : true
	},
	layout : 'column',
	items : [
		{
		fieldLabel : load.handoverbillmodifypda.i18n('foss.load.handoverbilladdnew.basicInfoForm.handOverTypeLabel')/*'交接类型'*/,
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
	            {"key":"PARTIALLINE_HANDOVER", "value":"外发交接单"},
	            {"key":"LDP_HANDOVER", "value":"快递代理交接单"},
	            {"key":"DIVISION_HANDOVER", "value":"分部交接单"}
	        ]
	    }),
	    listeners : {
	    	'change' : function(field,newValue,oldValue,eOpts){
	    		var form = this.up('form').getForm();
	    		if(newValue ==='SHORT_DISTANCE_HANDOVER'||newValue==='DIVISION_HANDOVE'){
		    		
		    		form.findField('tranGoodsType').bindStore(load.handoverbillmodifypda.getTranGoodsStore(1));
		    	}else{
		    		form.findField('tranGoodsType').bindStore(load.handoverbillmodifypda.getTranGoodsStore(2));
		    	}
	    		if(newValue==='SHORT_DISTANCE_HANDOVER'&&load.handoverbillmodifypda.beDivision=='Y'){
		    		form.findField('driver').setReadOnly(true);//只读
		    	
		    		form.findField('driver').reset();
		    		form.findField('driverTel').reset();//清空
		    	}else{
		    		form.findField('driver').setReadOnly(false);
		    	}
	    	}
	    } 
	 },{
		fieldLabel : load.handoverbillmodifypda.i18n('foss.load.handoverbilladdnew.basicInfoForm.handOverBillNoLabel')/*'交接单编号'*/,
		name : 'handOverBillNo'
	}, {
		fieldLabel : load.handoverbillmodifypda.i18n('foss.load.handoverbilladdnew.basicInfoForm.handOverTimeLabel')/*'交接时间'*/,
		name : 'handOverTime'
	}, {
		fieldLabel : load.handoverbillmodifypda.i18n('foss.load.handoverbilladdnew.basicInfoForm.departDeptLabel')/*'出发部门'*/,
		name : 'departDept'
	}, {
		name : 'arriveDeptCode',
		hidden : true
	}, {
		name : 'arriveDept'
	}, {
		name : 'driverName',
		hidden : true
	},{
		fieldLabel : load.handoverbillmodifypda.i18n('foss.load.handoverbilladdnew.basicInfoForm.vehicleNoLabel')/*'车牌号'*/,
		readOnly : false,
		name : 'vehicleNo',
		xtype : 'commontruckselector',
		queryParam : 'truckVo.truck.vehicleNoNoLike',
		queryAllFlag : false,
		allowBlank : false,
		listeners : {
			'blur' : function(cmp,eObject,eOpts){
				if(Ext.isEmpty(cmp.getValue())){
					return;
				}
				var form = this.up('form').getForm();
				var handOverType=form.findField('handOverType').getValue();
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
				if(load.handoverbillmodifypda.beDivision=='Y'&&handOverType=='SHORT_DISTANCE_HANDOVER'){
					form.findField('driver').reset();
					form.findField('driverTel').reset();
				}
				//后台获取司机信息
				var myMask = new Ext.LoadMask(this.up('form'), {
					msg:"加载中，请稍候..."
				});
				var saveButton = Ext.getCmp('Foss_load_handoverbillmodifypda_mainPage_saveButton_ID');
				var buttonMask = new Ext.LoadMask(saveButton, {
					msg:"请稍候..."
				});
				myMask.show();
				buttonMask.show();
				Ext.Ajax.request({
					url : load.realPath('queryDriverInfoByVehicleNoForHandOverBill.action'),
					params : {'handOverBillVo.vehicleNo' : cmp.getValue(),
						       'handOverBillVo.beDivision':load.handoverbillmodifypda.beDivision,
						       'handOverBillVo.handOverType' : handOverType},
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
								form.findField('driverName').setValue(driverInfo.driverName);
							}
						}
						myMask.hide();
						buttonMask.hide();
					},
					exception : function(response){
						myMask.hide();
						buttonMask.hide();
					}
				});
			}
		}
	}, {
		fieldLabel:'是否挂牌号',
		xtype : 'textfield',
		hidden:true,
		name:'beTrailerVehicleNo'
	}, {
		fieldLabel : load.handoverbillmodifypda.i18n('foss.load.handoverbilladdnew.basicInfoForm.driverLabel')/*'司机'*/,
		readOnly : false,
		name : 'driver',
		xtype : 'commondriverselector',
		listeners : {
			'blur' : function(field, eo, eOpts){
				var form = this.up('form').getForm();
				var newValue = field.getValue();
				if(field.isValid()){
					//如果司机不为空，则更新司机电话
					if(!Ext.isEmpty(newValue)){
						var store = field.store;
						var driverRecord = store.findRecord('empCode',newValue,0,false,false,false);
						if(driverRecord == null){
							form.findField('driverTel').reset();
							form.findField('driverName').reset();
						}else{
							form.findField('driverTel').setValue(driverRecord.get('empPhone'));		
							form.findField('driverName').setValue(driverRecord.get('empName'));
						}
					}
				}else{
					form.findField('driverTel').reset();
				}
			}
		}
	}, {
		fieldLabel : load.handoverbillmodifypda.i18n('foss.load.handoverbilladdnew.basicInfoForm.driverPhoneLabel')/*'司机电话'*/,
		name : 'driverTel'
	}, {
		fieldLabel : load.handoverbillmodifypda.i18n('foss.load.handoverbilladdnew.basicInfoForm.loadEndTimeLabel')/*'装车完成时间'*/,
		name : 'loadEndTime'
	}, FossDataDictionary.getDataDictionaryCombo('LOAD_GOODS_TYPE',{
		fieldLabel : load.handoverbillmodifypda.i18n('foss.load.handoverbilladdnew.basicInfoForm.goodsTypeLabel')/*'货物类型'*/,
		labelWidth : 85,
		allowBlank : false,
		name : 'goodsType',
		editable : false
	}),{
		fieldLabel : load.handoverbillmodifypda.i18n('foss.load.handoverbilladdnew.basicInfoForm.creatorLabel')/*'制单人'*/,
		name : 'createUserName'
	}, {
		fieldLabel : load.handoverbillmodifypda.i18n('foss.load.handoverbilladdnew.basicInfoForm.modifyManLabel')/*'修改人'*/,
		name : 'modifyUserName'
	},{
		boxLabel : load.handoverbillmodifypda.i18n('foss.load.handoverbilladdnew.basicInfoForm.isAgencyVisitLabel')/*'是否代理上门接货'*/,
		name : 'isAgencyVisit',
		xtype : 'checkbox'
		
	},{
		boxLabel : '是否PDA扫描',
		name : 'isCreatedByPDA',
		xtype : 'checkbox'
		
	}, {
		fieldLabel : load.handoverbillmodifypda.i18n('foss.load.handoverbilladdnew.basicInfoForm.tranGoodsType')/*'转货类型'*/,
		name : 'tranGoodsType',
		xtype : 'combobox',
		queryMode: 'local',
		allowBlank : false,
	    displayField: 'value',
	    valueField: 'key',
	    editable : false,
	    readOnly :false
	        
	}, {
		fieldLabel : load.handoverbillmodifypda.i18n('foss.load.handoverbilladdnew.waybillGrid.noteColumn')/*'备注'*/,
		readOnly : false,
		maxLength : 300,
		name : 'note',
		columnWidth : .5
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
});
//差异store
Ext.define('load.handoverbillmodifypda.store',{
	extend: 'Ext.data.Store',
	fields: [
		{name: 'key',  type: 'string'},
		{name: 'value',  type: 'string'}
	],
	proxy: {
		type: 'memory',
		reader: {
			type: 'json',
			root: 'tranGoodsType'
		}
	},
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});
load.handoverbillmodifypda.getTranGoodsStore = function(value){
	if(value==1){
		return Ext.create('load.handoverbillmodifypda.store', {
		 	data:{
				'tranGoodsType':[
					{'key':'TRANSITGOODS','value':'转货'},
					{'key':'CARRYGOODS','value':'带货'}
				]
			}
		});
	} else {
		return Ext.create('load.handoverbillmodifypda.store', {
		 	data:{
				'tranGoodsType':[
					{'key':'TRANSITGOODS','value':'转货'}
				]
			}
		});
	}
}
load.handoverbillmodifypda.rate=load.handoverbillmodifypda.rate/1000;
//方法用于各处调用，更新主页面grid下统计条数据
load.handoverbillmodifypda.updateMainPageStaInfo = function (store){
	//更新主页总票数
	var totalCountCmp = Ext.getCmp('Foss_load_handoverbillmodifypda_MainPageTotalCount');
	totalCountCmp.setValue(load.handoverbillmodifypda.handOverBillDetailGrid.store.getCount());
	//遍历主页store，获得总件数、总体积、总重量
	var totalPieces = 0,totalVolume = 0,totalWeight = 0,totalCodAmount = 0, normalTotalVolume=0,fastTotalWeight=0,nuConvertTotalVolume=0;
	store.each(function(record){
		totalPieces += record.get('pieces');
		nuConvertTotalVolume += record.get('cubage');
		totalWeight += record.get('weightAc');
		totalCodAmount += record.get('codAmount');
		totalVolume += record.get('cubageAc');
//		if(record.get('transPropertyCode')=='PACKAGE'||record.get('transPropertyCode')=='RCP'){
//			//获取快递的总重量
//			fastTotalWeight+=record.get('weightAc');
//		}else{
//			//获取除快递货的总体积
//			normalTotalVolume += record.get('cubageAc');
//		}
	});
	//转换后体积
//	totalVolume=normalTotalVolume+fastTotalWeight*load.handoverbillmodifypda.rate;
	if(totalWeight != 0){
		totalWeight = totalWeight.toFixed(2);//如果不为0，则四舍五入，保留两位小数
	}
	if(totalVolume != 0){
		totalVolume = totalVolume.toFixed(2);
	}
	if(nuConvertTotalVolume != 0){
		nuConvertTotalVolume = nuConvertTotalVolume.toFixed(2);
	}
	//更新主页总件数、总体积、总重量
	Ext.getCmp('Foss_load_handoverbillmodifypda_MainPageTotalPieces').setValue(totalPieces);
	Ext.getCmp('Foss_load_handoverbillmodifypda_MainPageTotalVolume').setValue(totalVolume);
	Ext.getCmp('Foss_load_handoverbillmodifypda_MainPageTotalWeight').setValue(totalWeight);
	Ext.getCmp('Foss_load_handoverbillmodifypda_MainPageTotalCodAmount').setValue(totalCodAmount);
	Ext.getCmp('Foss_load_handoverbillmodifypda_MainPageUnconvertTotalVolume').setValue(nuConvertTotalVolume);
};

//自定义方法，将Ext.util.HashMap转化为json格式，以便传往后台
load.handoverbillmodifypda.convertMap2Json = function(beforeMap){
	var map = {};
	beforeMap.each(function(key,value,length){
		var v = '{'+ '"' +key + '"' +':'+Ext.encode(value.data)+'}';
		var record = Ext.decode(v);
		map = Ext.merge(map,record);
	});
	return map;
}

//定义交接单日志Model
Ext.define('Foss.load.handoverbillmodifypda.HandOverBillOptLogModel', {
	extend : 'Ext.data.Model',
	//定义字段
	fields: [{
		name : 'operatorName',
		type : 'string'
	},{
		name : 'optTime',
		type : 'date',
		convert: dateConvert
	},{
		name : 'optType',
		type : 'string'
	},{
		name : 'optContent',
		type : 'string'
	}]
});

//定义交接单日志store
Ext.define('Foss.load.handoverbillmodifypda.HandOverBillOptLogStore', {
	extend : 'Ext.data.Store',
	// 绑定一个模型
	model : 'Foss.load.handoverbillmodifypda.HandOverBillOptLogModel',
	pageSize : 20,
	proxy : {
		type : 'ajax',
		actionMethods:'POST',
		// 请求的url
		url : load.realPath('queryHandOverBillOptLogByNo.action'),
		// 定义一个读取器
		reader : {
			// 以JSON的方式读取
			type : 'json',
			// 定义读取JSON数据的根对象
			root : 'handOverBillVo.handOverBillOptLogList',
			totalProperty : 'totalCount',
			successProperty: 'success'
		}
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	},
	listeners : {
		'beforeload' : function(store, operation, eOpts){
			Ext.apply(operation, {
				params : {
					'handOverBillVo.handOverBillNo' : load.handoverbillmodifypda.handOverBillNo
				}
			});	
		}
	}
});

//定义交接单修改日志列表
Ext.define('Foss.load.handoverbillmodifypda.HandOverBillOptLogGrid', {
	extend : 'Ext.grid.Panel',
	frame : true,
	title : load.handoverbillmodifypda.i18n('foss.load.handoverbilladdnew.optLogGrid.gridTitle')/*'修改日志'*/,
//	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	columnLines: true,
	autoScroll : true,
	collapsible : true,
	collapsed : true,//页面初始化时不展开该grid，不加载数据
	animCollapse : true,
	store : Ext.create('Foss.load.handoverbillmodifypda.HandOverBillOptLogStore'),
	columns : [{
		dataIndex : 'operatorName',
		align : 'center',
		width : 70,
		xtype : 'ellipsiscolumn',
		text : load.handoverbillmodifypda.i18n('foss.load.handoverbilladdnew.optLogGrid.operator')/*'操作人'*/
	}, {
		dataIndex : 'optTime',
		align : 'center',
		width : 135,
		text : load.handoverbillmodifypda.i18n('foss.load.handoverbilladdnew.optLogGrid.operateTime')/*'操作时间'*/,
		renderer : function(value){
			if(value!=null){
				var date = new Date(value);
				return Ext.Date.format(date, 'Y-m-d H:i:s');									
		}else{
				return null;
			}
		}
	}, {
		dataIndex : 'optType',
		align : 'center',
		width : 100,
		text : load.handoverbillmodifypda.i18n('foss.load.handoverbilladdnew.optLogGrid.operateType')/*'操作类别'*/
	}, {
		dataIndex : 'optContent',
		flex : 1,
		text : load.handoverbillmodifypda.i18n('foss.load.handoverbilladdnew.optLogGrid.operateContent')/*'操作内容'*/,
		xtype : 'linebreakcolumn'
	}],
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.bbar = Ext.create('Deppon.StandardPaging',{
			store:me.store
	});
	load.handoverbillmodifypda.logPagingBar = me.bbar;
	me.callParent([cfg]);
},
	listeners : {
		'expand' : function(panel, eOpts){
			load.handoverbillmodifypda.logPagingBar.moveFirst();
		}
	}
});

//定义map，存储修改前的运单
load.handoverbillmodifypda.oldWaybillMap = new Ext.util.HashMap();
//定义map，记录删除的运单号map
load.handoverbillmodifypda.deletedWaybillMap = new Ext.util.HashMap();
//定义map，记录被修改的运单号map
load.handoverbillmodifypda.updatedWaybillMap = new Ext.util.HashMap();
//定义map，存储修改前的流水号map
load.handoverbillmodifypda.oldSerialNoMap = new Ext.util.HashMap();
//定义map，记录删除的流水号map
load.handoverbillmodifypda.deletedSerialNoMap = new Ext.util.HashMap();
//定义map，记录当前主页面的的流水号map
load.handoverbillmodifypda.allSerialNoMap = new Ext.util.HashMap();

// 定义运单列表
load.handoverbillmodifypda.handOverBillDetailGrid = Ext.create('Foss.load.handoverbillmodifypda.HandOverBillDetailGrid');

Ext.onReady(function() {
	//接受前一页面传入的交接单号
	var handOverBillNo = load.handoverbillmodifypda.handOverBillNo;
	Ext.Ajax.request({
		url : load.realPath('queryHandOverBillByNo.action'),
		params : {'handOverBillVo.handOverBillNo': handOverBillNo},
		success : function(response){
			var result = Ext.decode(response.responseText);
			//定义基本信息实体
			var basicInfo = result.handOverBillVo.baseEntity,
				departDeptCode = basicInfo.departDeptCode,
				beSalesDept = result.handOverBillVo.beSalesDept;
			var basicInfoRecord = load.handoverbillmodifypda.basicInfoRecord = Ext.ModelManager.create(basicInfo, 'Foss.load.handoverbillmodifypda.HandOverBillBaseInfoModel');
			//定义基本信息form
			var basicInfoForm = Ext.create('Foss.load.handoverbillmodifypda.BasicInfoForm');
//			if(beSalesDept === 'Y'){
//				var vehicleNoCmp = basicInfoForm.getForm().findField('vehicleNo');
//				//只能使用营业部关联顶级车队的公司车辆和所有外请车，登录部门为外场则不过滤
//				vehicleNoCmp.store.removeAll();
//				vehicleNoCmp.store.addListener('beforeload', function(store, operation, eOpts) {
//					var searchParams = operation.params;
//					if (Ext.isEmpty(searchParams)) {
//						searchParams = {};
//						Ext.apply(operation, {
//							params : searchParams
//						});
//					}
//					searchParams['truckVo.truck.topFleetOrgCode'] = departDeptCode;
//				});
//				vehicleNoCmp.store.loadPage(1);
//			}
			//给基本信息form加载值
			basicInfoForm.getForm().loadRecord(load.handoverbillmodifypda.basicInfoRecord);
			//记录修改后的数据有无被保存
			load.handoverbillmodifypda.isSaved = 'N';
			//是否为PDA生成
			var isCreatedByPDA = basicInfoRecord.get('isCreatedByPDA');
			if(isCreatedByPDA == 'Y'){
				basicInfoForm.getForm().findField('isCreatedByPDA').setValue(true);
			}else{
				basicInfoForm.getForm().findField('isCreatedByPDA').setValue(false);
			}
			
			//处理司机
			var driverCmp = basicInfoForm.getForm().findField('driver');
			driverCmp.setCombValue(basicInfo.driverName,basicInfo.driver);
			basicInfoForm.getForm().findField('driverName').setValue(basicInfo.driverName);
			
			//如果为外发交接单，则显示“外发代理”隐藏“到达部门”，反之，则反之
			var arriveDeptCmp = basicInfoForm.getForm().findField('arriveDept');
			if(basicInfoRecord.get('handOverType') == 'PARTIALLINE_HANDOVER'){
				arriveDeptCmp.fieldLabel = '外发代理';
				basicInfoForm.getForm().findField('goodsType').setReadOnly(true);
				if(basicInfoRecord.get('isAgencyVisit') == 'Y'){
					basicInfoForm.getForm().findField('isAgencyVisit').setValue(true);
					basicInfoForm.getForm().findField('vehicleNo').setDisabled(true);
					basicInfoForm.getForm().findField('driver').setDisabled(true);
				}
			}else if(basicInfoRecord.get('handOverType') == 'LDP_HANDOVER'){
				arriveDeptCmp.fieldLabel = '快递代理公司';
				basicInfoForm.getForm().findField('goodsType').setReadOnly(true);
				if(basicInfoRecord.get('isAgencyVisit') == 'Y'){
					basicInfoForm.getForm().findField('isAgencyVisit').setValue(true);
					basicInfoForm.getForm().findField('vehicleNo').setDisabled(true);
					basicInfoForm.getForm().findField('driver').setDisabled(true);
				}
			}else{
				//如果为集配, 短配交接单，且状态为“已配载”或以后的状态，则车牌号不能修改；
				var handOverType = basicInfoRecord.get('handOverType');
				if((handOverType == 'LONG_DISTANCE_HANDOVER' || handOverType == 'SHORT_DISTANCE_HANDOVER'||handOverType=='DIVISION_HANDOVE')
						&& (basicInfoRecord.get('handOverBillState') != 10
								&& basicInfoRecord.get('handOverBillState') != 20)){
					basicInfoForm.getForm().findField('vehicleNo').setReadOnly(true);
					driverCmp.setReadOnly(true);
				}
				arriveDeptCmp.fieldLabel = load.handoverbillmodifypda.i18n('foss.load.handoverbilladdnew.basicInfoForm.arriveDeptLabel')/*'到达部门'*/;
				//隐藏“是否代理上门接货”checkbox
				basicInfoForm.getForm().findField('isAgencyVisit').setVisible(false);
				//如果到达部门支持自动分拣，则“货物类型”可编辑
				Ext.Ajax.request({
					url : load.realPath('queryHandOverBillOutfieldByCode.action'),
					jsonData : {
						'handOverBillVo' : {
							'arriveDeptCode' : basicInfoForm.getForm().findField('arriveDeptCode').getValue()
						}
					},
					success : function(response){
						var result = Ext.decode(response.responseText);
						var outfield = result.handOverBillVo.outfield;
						//如果支持自动分拣
						if(outfield != null){
								if(outfield.sortingMachine == 'Y'){
								basicInfoForm.getForm().findField('goodsType').setReadOnly(false);
							}else{
								basicInfoForm.getForm().findField('goodsType').setReadOnly(true);
							}
						}else{
							basicInfoForm.getForm().findField('goodsType').setReadOnly(true);
						}
					},
					exception : function(response){
						var result = Ext.decode(response.responseText);
		    			top.Ext.MessageBox.alert(load.handoverbillmodifypda.i18n('foss.load.handoverbilladdnew.waybillGrid.alertInfoTitle')/*'提示'*/,result.message);
					}
				});
			}
			if(basicInfoRecord.get('handOverType')==='SHORT_DISTANCE_HANDOVER'||basicInfoRecord.get('handOverType')==='DIVISION_HANDOVER'){
				
				basicInfoForm.getForm().findField('tranGoodsType').setValue(
						load.handoverbillmodifypda.getTranGoodsStore(1).findRecord(
								'key',basicInfoRecord.get('tranGoodsType'),0,false,false,false));
			}else{
				
				basicInfoForm.getForm().findField('tranGoodsType').setValue(
						load.handoverbillmodifypda.getTranGoodsStore(2).findRecord(
								'key',basicInfoRecord.get('tranGoodsType'),0,false,true,true));
				
			}	
			//如果到达部门支持自动分拣，则“货物类型”可编辑
			Ext.Ajax.request({
				url : load.realPath('queryHandOverBillOutfieldByCode.action'),
				jsonData : {
					'handOverBillVo' : {
						'arriveDeptCode' : basicInfoForm.getForm().findField('arriveDept').getValue()
					}
				},
				success : function(response){
					var result = Ext.decode(response.responseText);
					var outfield = result.handOverBillVo.outfield;
					//如果支持自动分拣
					if(outfield != null){
							if(outfield.sortingMachine == 'Y'){
							basicInfoForm.getForm().findField('goodsType').setReadOnly(false);
						}else{
							basicInfoForm.getForm().findField('goodsType').setReadOnly(true);
						}
					}else{
						basicInfoForm.getForm().findField('goodsType').setReadOnly(true);
					}
				},
				exception : function(response){
					var result = Ext.decode(response.responseText);
					top.Ext.MessageBox.alert(load.handoverbillmodifypda.i18n('foss.load.handoverbilladdnew.waybillGrid.alertInfoTitle')/*'提示'*/,result.message);
				}
			});
			
		Ext.create('Ext.panel.Panel', {
				id : 'T_load-handoverbillmodifypdaindex_content',
				cls : "panelContentNToolbar",
				bodyCls : 'panelContent-body',
				layout : 'auto',//
				items : [ basicInfoForm, load.handoverbillmodifypda.handOverBillDetailGrid,{
					xtype : 'container',
					columnWidth : 1,
					layout : 'column',
					items : [ {
							xtype : 'container',
							columnWidth : .92,
							html : '&nbsp'
					},{
						columnWidth : .08,
						xtype : 'button',
						cls : 'yellow_button',
						name : 'saveButton',
						id : 'Foss_load_handoverbillmodifypda_mainPage_saveButton_ID',
						text : load.handoverbillmodifypda.i18n('foss.load.handoverbilladdnew.waybillGrid.saveButtonText')/*'保存'*/,
						handler : function() {
							var deletedWaybill = load.handoverbillmodifypda.convertMap2Json(load.handoverbillmodifypda.deletedWaybillMap);
							var updatedWaybill = load.handoverbillmodifypda.convertMap2Json(load.handoverbillmodifypda.updatedWaybillMap);
							//获取所有的删除的流水号list
							var tempDeletedSerialNoList = load.handoverbillmodifypda.deletedSerialNoMap.getValues();
							var deletedSerialNoList = new Array();
							for(var i=0;i<tempDeletedSerialNoList.length;i++){
								var serialNoList = tempDeletedSerialNoList[i].getValues();
								for(var j=0;j<serialNoList.length;j++){
									deletedSerialNoList.push(serialNoList[j].data);
								}
							}
							if(load.handoverbillmodifypda.handOverBillDetailGrid.store.getCount() == 0){
								Ext.ux.Toast.msg(load.handoverbillmodifypda.i18n('foss.load.handoverbilladdnew.waybillGrid.alertInfoTitle')/*'提示'*/, '交接单内无任何运单!', 'error', 2000);
								return;
							}
							//获取当前所有的流水号list
							var tempAllSerialNoList = load.handoverbillmodifypda.allSerialNoMap.getValues();
							var allSerialNoList = new Array();
							for(var i=0;i<tempAllSerialNoList.length;i++){
								var serialNoList = tempAllSerialNoList[i].getValues();
								for(var j=0;j<serialNoList.length;j++){
									allSerialNoList.push(serialNoList[j].data);
								}
							}
							//计算总金额，同时获取所有运单号List
							var totalMoney = 0;
							var allWaybillList = new Array();
							load.handoverbillmodifypda.handOverBillDetailGrid.store.each(function(record){
								totalMoney += record.get('waybillFee');
								allWaybillList.push(record.data);
							});
							//交接单基本信息实体
							var baseEntity = basicInfoForm.getForm().getValues();
							baseEntity.isCreatedByPDA = 'Y';
							
							 if(baseEntity.handOverType=='SHORT_DISTANCE_HANDOVER'&&load.handoverbillmodifypda.beDivision=='Y'){
								 if((baseEntity.vehicleNo).substr(0,1)!='德'){
									Ext.Msg.alert("提示","分部到点部做交接单，车牌号必须以“德”开头")
									return;
								 }
							 }
							
							var data = {'handOverBillVo' : {
								'updateHandOverBillDto' : {
									'deletedWaybillMap' : deletedWaybill,
									'updatedWaybillMap' : updatedWaybill,
									'allWaybillList' : allWaybillList,
									'deletedSerialNoList' : deletedSerialNoList,
									'allSerialNoList' : allSerialNoList,
									'handOverBillEntity' : baseEntity,
									'totalCount' : Ext.getCmp('Foss_load_handoverbillmodifypda_MainPageTotalCount').getValue(),
									'totalPieces' : Ext.getCmp('Foss_load_handoverbillmodifypda_MainPageTotalPieces').getValue() ,
									'totalCubage' : Ext.getCmp('Foss_load_handoverbillmodifypda_MainPageTotalVolume').getValue() ,
									'totalWeight' : Ext.getCmp('Foss_load_handoverbillmodifypda_MainPageTotalWeight').getValue(),
									'totalMoney' : totalMoney,
									'totalCodAmount' : Ext.getCmp('Foss_load_handoverbillmodifypda_MainPageTotalCodAmount').getValue()
								}
							}};
							//mask
							var myMask = new Ext.LoadMask(Ext.getCmp('T_load-handoverbillmodifypdaindex_content'), {
								msg:"数据提交中，请稍候..."
							});
							myMask.show();
							Ext.Ajax.request({
								url : load.realPath('updateHandOverBill.action'),
								jsonData : data,
								success : function(response){
									Ext.ux.Toast.msg(load.handoverbillmodifypda.i18n('foss.load.handoverbilladdnew.waybillGrid.alertInfoTitle')/*'提示'*/, '修改成功!', 'info', 3000);
									//隐藏保存按钮
									load.handoverbillmodifypda.isSaved = 'Y';
									//隐藏“查询交接运单”、“保存”按钮
									Ext.getCmp('Foss_load_handoverbillmodifypda_mainPage_saveButton_ID').setVisible(false);
									//设置form所有字段均为只读
									var form = basicInfoForm.getForm();
									var formCmps = form.getFields().getRange(0,form.getFields().getCount());
									for(var i in formCmps){
										formCmps[i].setReadOnly(true);
									}
									myMask.hide();
									//隐藏运单列表操作列
									var superGrid = load.handoverbillmodifypda.handOverBillDetailGrid;
									superGrid.columns[1].setVisible(false);
									//如果运单被展开，隐藏流水号前的操作列
									var plugin = superGrid.getPlugin('Foss_handoverbillmodifypda_mainPage_serialNoGrid_ID');
									var pluginGrid = plugin.getExpendRowBody();
									if(pluginGrid != null){
										pluginGrid.columns[0].setVisible(false);
									}
								},
								exception : function(response) {
				    				var result = Ext.decode(response.responseText);
				    				top.Ext.MessageBox.alert(load.handoverbillmodifypda.i18n('foss.load.handoverbilladdnew.waybillGrid.alertInfoTitle')/*'提示'*/,'保存失败，' + result.message);
				    				myMask.hide();
				    			}
							});
						}
					}]
				},Ext.create('Foss.load.handoverbillmodifypda.HandOverBillOptLogGrid') ],
				renderTo : 'T_load-handoverbillmodifypdaindex-body'
			});
			//弹出数据加载，禁止操作
			var myMask = new Ext.LoadMask(load.handoverbillmodifypda.handOverBillDetailGrid, {
				msg:"加载中，请稍后..."
					});
			myMask.show();
			//加载交接单中运单数据
			load.handoverbillmodifypda.handOverBillDetailGrid.store.load();
			//后台请求所有交接单下的流水号list，封装成Map
			Ext.Ajax.request({
				url : load.realPath('queryWaybillDetailByNos.action'),
				params : {'handOverBillVo.handOverBillNo': handOverBillNo},
				success : function(response){
					var result = Ext.decode(response.responseText);
					var serialNoList = result.handOverBillVo.pdaSerialNoList;
					//获取流水号list后，在前台封装为map
					for(var i in serialNoList){
						var serialNo = serialNoList[i];
						var serialNoRecord =  Ext.ModelManager.create(serialNo, 'Foss.load.handoverbillmodifypda.WaybillDetailModel');
						var waybillNo = serialNoRecord.get('waybillNo');
						var serialNo = serialNoRecord.get('serialNo');
						if(load.handoverbillmodifypda.oldSerialNoMap.get(waybillNo) == null){
							var tempMap = new Ext.util.HashMap();
							tempMap.add(serialNo,serialNoRecord);
							load.handoverbillmodifypda.oldSerialNoMap.add(waybillNo,tempMap);
							load.handoverbillmodifypda.allSerialNoMap.add(waybillNo,tempMap.clone());
						}else{
							var tempMap = load.handoverbillmodifypda.oldSerialNoMap.get(waybillNo);
							tempMap.add(serialNo,serialNoRecord);
							load.handoverbillmodifypda.oldSerialNoMap.add(waybillNo,tempMap);
							load.handoverbillmodifypda.allSerialNoMap.add(waybillNo,tempMap.clone());
						}
					}
					myMask.hide();
				}
			});
		
			//从基本信息record中获取值，给统计信息赋值
			Ext.getCmp('Foss_load_handoverbillmodifypda_MainPageTotalCount').setValue(basicInfoRecord.get('waybillQtyTotal'));
			Ext.getCmp('Foss_load_handoverbillmodifypda_MainPageTotalPieces').setValue(basicInfoRecord.get('goodsQtyTotal'));
			Ext.getCmp('Foss_load_handoverbillmodifypda_MainPageTotalWeight').setValue(basicInfoRecord.get('weightTotal'));
			Ext.getCmp('Foss_load_handoverbillmodifypda_MainPageTotalVolume').setValue(basicInfoRecord.get('volumeTotal'));
			Ext.getCmp('Foss_load_handoverbillmodifypda_MainPageTotalCodAmount').setValue(basicInfoRecord.get('codAmountTotal'));
		}
	});
});