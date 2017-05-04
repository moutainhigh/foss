// 定义查询运单结果Model，四个grid共用该Model
Ext.define('Foss.load.handoverbilladdnew.waybillStockModel', {
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
	},{
		name : 'serialNoHandOveredList',
		type : 'object'
	},{
		name : 'serialNoMap',
		type : 'object'
	}]
});

// 定义流水号列表Model
Ext.define('Foss.load.handoverbilladdnew.serialNoModel', {
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
	},{
		name :　'weight',
		type :　'number'
	},{
		name :　'volumn',
		type :　'number'
	}]
});

//运输性质store
Ext.define('Foss.load.handoverbilladdnew.ProductStore',{
	extend :'Ext.data.Store',
	autoLoad : true,
	fields:[
		{name: 'valueName',  type: 'string'},
		{name: 'valueCode',  type: 'string'}
	],
	proxy: {
        type : 'ajax',
        actionMethods : 'post',
        url : load.realPath('queryProductList.action'),
		reader : {
			type : 'json',
			root : 'handOverBillVo.productList'
		}
    }
});

/**
 * 定义Map，用来存储在途运单列表中被勾选的运单号和流水号
 * load.handoverbilladdnew.selectedEnRouteWaybillMap key，交接单中运单分录ID，value，交接单中运单对象
 */
load.handoverbilladdnew.selectedEnRouteWaybillMap = new Ext.util.HashMap();

// 定义交接单明细store
Ext.define('Foss.load.handoverbilladdnew.HandOverBillDetailStore', {
	extend : 'Ext.data.Store',
	// 绑定一个模型
	model : 'Foss.load.handoverbilladdnew.waybillStockModel',
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	},
	listeners : {
		'datachanged' : function(store){
			load.handoverbilladdnew.updateMainPageStaInfo(store);
		},
		'update' : function(store){
			load.handoverbilladdnew.updateMainPageStaInfo(store);
		},
		'clear' : function(store){
			load.handoverbilladdnew.updateMainPageStaInfo(store);
		}
	}
});
//转换快递体积转换率
load.handoverbilladdnew.rate=load.handoverbilladdnew.rate/1000;
//方法用于各处调用，更新主页面grid下统计条数据
load.handoverbilladdnew.updateMainPageStaInfo = function (store){
	//更新主页总票数
	var totalCountCmp = Ext.getCmp('Foss_load_handOverBillAddnew_MainPageTotalCount');
	// 定义运单列表 (新增界面 交接单明细)
	totalCountCmp.setValue(load.handoverbilladdnew.handOverBillDetailGrid.store.getCount());
	//遍历主页store，获得总件数、总体积、总重量、非快递货总体积、快递货总重量、未转换总体积
	var totalPieces = 0,totalVolume = 0,totalWeight = 0, normalTotalVolume=0,fastTotalWeight=0,nuConvertTotalVolume=0;
	store.each(function(record){
		totalPieces += record.get('pieces');
		nuConvertTotalVolume += record.get('cubage');
		totalWeight += record.get('weightAc');
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
	//totalVolume=normalTotalVolume+fastTotalWeight*load.handoverbilladdnew.rate
	if(totalVolume!=0){
		totalVolume = totalVolume.toFixed(2);
	}
	if(totalWeight != 0){
		totalWeight = totalWeight.toFixed(2);//如果不为0，则四舍五入，保留两位小数
	}
	if(nuConvertTotalVolume != 0){
		nuConvertTotalVolume = nuConvertTotalVolume.toFixed(2);
	}
	//更新主页总件数、总体积、总重量、未转换总体积
	Ext.getCmp('Foss_load_handOverBillAddnew_MainPageTotalPieces').setValue(totalPieces);
	Ext.getCmp('Foss_load_handOverBillAddnew_MainPageTotalVolume').setValue(totalVolume);
	Ext.getCmp('Foss_load_handOverBillAddnew_MainPageTotalWeight').setValue(totalWeight);
	Ext.getCmp('Foss_load_handOverBillAddnew_MainPageUnconvertTotalVolume').setValue(nuConvertTotalVolume);
};

//方法用于各处调用，更新查询库存运单界面下统计条数据
load.handoverbilladdnew.updateQueryPageStaInfo = function(){ //
	//由于一层map勾选导致的二层流水号增加个数不好确定，故遍历二层map，结合一层map，得到各统计信息
	var totalPieces = 0,totalWeight = 0,totalCubage = 0,totalMoney = 0, normalTotalCubage=0,fastTotalWeight=0,nuConvertTotalCubage=0;
	load.handoverbilladdnew.selectedWaybillMap.each(function(waybillNo,waybill,length){
		var serialNoMap = waybill.get('serialNoMap');
		totalPieces += serialNoMap.getCount();//得到总件数
		totalMoney += waybill.get('waybillFee');
//		var waybillWeight = (serialNoMap.getCount()/waybill.get('pieces'))*(waybill.get('weight'));//按件数均分，得到该票运单的重量
//		totalWeight += waybillWeight;//得到总重量
		serialNoMap.each(function(serialNO,serial,length){
			totalWeight += serial.get('weight');//得到总重量
			totalCubage += serial.get('volumn');//得到中重量
			
		});
		var waybillCubage = (serialNoMap.getCount()/waybill.get('pieces'))*(waybill.get('cubage'));//按件数均分，得到该票运单的体积
		nuConvertTotalCubage += waybillCubage;//得到未转换总体积
//		//统计快递货总重量，非快递货总体积
//		if(waybill.get('transPropertyCode')=='PACKAGE'||waybill.get('transPropertyCode')=='RCP'){
//			var fastwaybillWeight = (serialNoMap.getCount()/waybill.get('pieces'))*(waybill.get('weight'));//按件数均分，得到该票运单的重量
//			fastTotalWeight += fastwaybillWeight;//得到总重量
//		}else{
//			var normalwaybillCubage = (serialNoMap.getCount()/waybill.get('pieces'))*(waybill.get('cubage'));//按件数均分，得到该票运单的体积
//			normalTotalCubage += normalwaybillCubage;//得到未转换总体积
//		}
	});
	//计算转换后总体积
	//totalCubage=fastTotalWeight*load.handoverbilladdnew.rate +normalTotalCubage;
	if(totalCubage!=0){
		totalCubage = totalCubage.toFixed(2);
	}
	if(totalWeight != 0){
		totalWeight = totalWeight.toFixed(2);//如果不为0，则四舍五入，保留两位小数
	}
	if(nuConvertTotalCubage != 0){
		nuConvertTotalCubage = nuConvertTotalCubage.toFixed(2);
	}
	//设置已勾选总件数
	Ext.getCmp('Foss_load_handOverBillAddnew_QueryPageTotalPieces').setValue(totalPieces);
	Ext.getCmp('Foss_load_handOverBillAddnew_QueryPageTotalWeight').setValue(totalWeight);
	Ext.getCmp('Foss_load_handOverBillAddnew_QueryPageTotalCubage').setValue(totalCubage);
	Ext.getCmp('Foss_load_handOverBillAddnew_QueryPageTotalMoney').setValue(totalMoney);
	Ext.getCmp('Foss_load_handOverBillAddnew_QueryPageUnconvertTotalCubage').setValue(nuConvertTotalCubage);
}

//方法用于各处调用，更新查询在途运单界面下统计条数据
load.handoverbilladdnew.updateQueryEnRoutePageStaInfo = function(){
	//由于一层map勾选导致的二层流水号增加个数不好确定，故遍历二层map，结合一层map，得到各统计信息
	var totalPieces = 0,totalWeight = 0,totalCubage = 0,totalMoney = 0 ,normalTotalCubage=0,fastTotalWeight=0,nuConvertTotalCubage=0;
	load.handoverbilladdnew.selectedEnRouteWaybillMap.each(function(waybillId,waybill,length){
		var serialNoMap = waybill.get('serialNoMap');
		totalPieces += serialNoMap.getCount();//得到总件数
		totalMoney += waybill.get('waybillFee');
		serialNoMap.each(function(serialId,serial,length){
			totalWeight += serial.get('weight');//得到总重量
			totalCubage += serial.get('volumn');//得到总重量
		});
//		var waybillWeight = (serialNoMap.getCount()/waybill.get('pieces'))*(waybill.get('weight'));//按件数均分，得到该票运单的重量
//		totalWeight += waybillWeight;//得到总重量
		
		var waybillCubage = (serialNoMap.getCount()/waybill.get('pieces'))*(waybill.get('cubage'));//按件数均分，得到该票运单的体积
		nuConvertTotalCubage += waybillCubage;//得到总体积
		
//		//统计快递货总重量，非快递货总体积
//		if(waybill.get('transPropertyCode')=='PACKAGE'||waybill.get('transPropertyCode')=='RCP'){
//			var fastwaybillWeight = (serialNoMap.getCount()/waybill.get('pieces'))*(waybill.get('weight'));//按件数均分，得到该票运单的重量
//			fastTotalWeight += fastwaybillWeight;//得到总重量
//		}else{
//			var normalwaybillCubage = (serialNoMap.getCount()/waybill.get('pieces'))*(waybill.get('cubage'));//按件数均分，得到该票运单的体积
//			normalTotalCubage += normalwaybillCubage;//得到未转换总体积
//		}
	});
	//计算转换后总体积
//	totalCubage=fastTotalWeight*load.handoverbilladdnew.rate +normalTotalCubage;
	if(totalCubage != 0){
		totalCubage = totalCubage.toFixed(2);
	}
	if(totalWeight != 0){
		totalWeight = totalWeight.toFixed(2);//如果不为0，则四舍五入，保留两位小数
	}
	if(nuConvertTotalCubage != 0){
		nuConvertTotalCubage = nuConvertTotalCubage.toFixed(2);//如果不为0，则四舍五入，保留两位小数
	}
	
	//设置已勾选总件数
	Ext.getCmp('Foss_load_handOverBillAddnew_EnRouteQueryPageTotalPieces').setValue(totalPieces);
	Ext.getCmp('Foss_load_handOverBillAddnew_EnRouteQueryPageTotalWeight').setValue(totalWeight);
	Ext.getCmp('Foss_load_handOverBillAddnew_EnRouteQueryPageTotalCubage').setValue(totalCubage);
	Ext.getCmp('Foss_load_handOverBillAddnew_EnRouteQueryPageTotalMoney').setValue(totalMoney);
	Ext.getCmp('Foss_load_handOverBillAddnew_EnRouteQueryPageUnconvertTotalCubage').setValue(nuConvertTotalCubage);
}

//方法用于各处调用，更新已选运单统计条数据
load.handoverbilladdnew.updateUnsubmitedWaybillStaInfo = function(){
	var totalPieces = 0,totalWeight = 0,totalCubage = 0,totalMoney = 0, normalTotalVolume=0,fastTotalWeight=0,nuConvertTotalVolume=0;
	// 右边 待提交运单的grid（已选运单列表 查询交接单界面--新增时候）
	load.handoverbilladdnew.unsubmitedWaybillGrid.store.each(function(waybill){
		var serialNoMap = waybill.get('serialNoMap');
		totalPieces += serialNoMap.getCount();//得到总件数
		totalMoney += waybill.get('waybillFee');
		totalWeight += waybill.get('weightAc');//得到总重量
		nuConvertTotalVolume += waybill.get('cubage');//得到总体积
		totalCubage += waybill.get('cubageAc')
//		if(waybill.get('transPropertyCode')=='PACKAGE'||waybill.get('transPropertyCode')=='RCP'){
//			//获取快递的总重量
//			fastTotalWeight+=waybill.get('weight');
//		}else{
//			//获取除快递货的总体积
//			normalTotalVolume += waybill.get('cubage');
//		}
	});
	//计算转换后的体积
	//totalCubage=normalTotalVolume+fastTotalWeight*load.handoverbilladdnew.rate;
	
	if(totalWeight != 0){
		totalWeight = totalWeight.toFixed(2);//如果不为0，则四舍五入，保留两位小数
	}
	if(totalCubage != 0){
		totalCubage = totalCubage.toFixed(2);
	}
	if(nuConvertTotalVolume != 0){
		nuConvertTotalVolume = nuConvertTotalVolume.toFixed(2);
	}
	//设置已勾选总件数
	Ext.getCmp('Foss_load_handOverBillAddnew_UnsubmitedWaybillTotalPieces').setValue(totalPieces);
	Ext.getCmp('Foss_load_handOverBillAddnew_UnsubmitedWaybillTotalWeight').setValue(totalWeight);
	Ext.getCmp('Foss_load_handOverBillAddnew_UnsubmitedWaybillTotalCubage').setValue(totalCubage);
	Ext.getCmp('Foss_load_handOverBillAddnew_UnsubmitedWaybilleTotalMoney').setValue(totalMoney);
	Ext.getCmp('Foss_load_handOverBillAddnew_UnsubmitedWaybillUnconvertTotalCubage').setValue(nuConvertTotalVolume);
}

//定义方法，判断流水号数组中是否存在某流水号
load.handoverbilladdnew.inArray = function(serialNoList,serialNo){
	for(var i in serialNoList){
		var serialNoRec = serialNoList[i];
		if(serialNoRec.serialNo === serialNo){
			return true;
		}
	}
	return false;
}

// 定义运单明细store
Ext.define('Foss.load.handoverbilladdnew.WaybillDetailStore', {
	extend : 'Ext.data.Store',
	// 绑定一个模型
	model : 'Foss.load.handoverbilladdnew.serialNoModel',
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
});

// 定义运单明细grid
Ext.define('Foss.load.handoverbilladdnew.WaybillDetailGrid', {
	extend : 'Ext.grid.Panel',
	columnLines: true,
	frame: true,
	enableColumnHide : false,//配置该属性可取消grid自定义显示列功能
	baseCls : 'handOverBill_addNew_serialNoGap',
	autoScroll : true,
	width : 180,
	store : Ext.create('Foss.load.handoverbilladdnew.WaybillDetailStore'),
	columns : [ {
		xtype : 'actioncolumn',
		width : 60,
		text : load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.actionColumn')/*'操作'*/,
		align : 'center',
		items : [ {
			tooltip : load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.deleteButtonColumn')/*'删除'*/,
			iconCls : 'deppon_icons_remove',
			handler : function(grid, rowIndex, colIndex) {
				if(grid.store.getCount() == 1){
					Ext.ux.Toast.msg(load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.alertInfoTitle')/*'提示'*/, load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.deleteWaybillAlert')/*'请直接删除运单'*/, 'error', 1500);
					return;
				}
				//从unsavedSerialNoMap中删除该流水号
				var record = grid.getStore().getAt(rowIndex),
					waybillNo = record.get('waybillNo'),
					serialNo = record.get('serialNo'),
					weight = record.get('weight'),
					volumn = record.get('volumn');
				//更新一级表格内的信息
					// 定义运单列表 (新增界面 交接单明细)
				waybillStore = load.handoverbilladdnew.handOverBillDetailGrid.store;
				var waybillRecord = waybillStore.findRecord('waybillNo', waybillNo, 0, false,true,true);
				//删掉map里的该流水号     
				waybillRecord.get('serialNoMap').removeAtKey(serialNo);
				waybillRecord.set('weight',(waybillRecord.get('weight')-(waybillRecord.get('weight')/waybillRecord.get('pieces'))).toFixed(3));
				waybillRecord.set('cubage',(waybillRecord.get('cubage')-(waybillRecord.get('cubage')/waybillRecord.get('pieces'))).toFixed(3));
				waybillRecord.set('weightAc',(waybillRecord.get('weightAc')-weight).toFixed(3));
				waybillRecord.set('cubageAc',(waybillRecord.get('cubageAc')-volumn).toFixed(3));
				waybillRecord.set('pieces',waybillRecord.get('pieces') - 1);
				grid.store.removeAt(rowIndex);
			}
		} ]
	}, {
		dataIndex : 'serialNo',
		align : 'center',
		flex : 1,
		xtype : 'ellipsiscolumn',
		text : load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.serialNoColumn')/*'流水号'*/
	} ],
	bindData : function(record){
		var superGrid = this.superGrid,
			serialNoMap = record.get('serialNoMap');
		if(load.handoverbilladdnew.isSaved == 'Y'){
			this.columns[0].setVisible(false);
			//this.columns[0].destroy();
		}
		this.store.loadData(serialNoMap.getValues());
	}
});

//定义打印模版window
Ext.define('Foss.load.handoverbilladdnew.PrintWindow', {
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
	            {"key":"快递代理外发清单", "value":"快递代理地配外发清单打印"},
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
		text : load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.printButtonText')/*'打印'*/,
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
					params : {'handOverBillVo.handOverBillNo' : handOverBillNos[0]},
					success : function(response) {
						var result = Ext.decode(response.responseText),
							vehicleAssembleNo = result.handOverBillVo.vehicleassembleNo;
						if(vehicleAssembleNo == null || vehicleAssembleNo == '') {
							Ext.ux.Toast.msg(load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.alertInfoTitle')/*'提示'*/, 
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
				} else if (printTemplate == '快递代理外发清单') {
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

/*
 * 判断待添加的运单和已添加的运单是否都为快递货或都为零担货
 * tbAddWaybills : 待添加的运单集合； 目前tbAddWaybills的数据类型为Ext.util.MixedCollection或Ext.data.Store
 * existWaybill ： 已经存在的运单; 目前existsWaybill的数据类型为Ext.data.Model
 */
load.handoverbilladdnew.validateHandoverbillDetailsTransProperty = function(tbAddWaybills, existWaybill){
	
	if(Ext.isEmpty(tbAddWaybills)){
		return true;
	}
	
	//用于存放循环中的运单
	var waybill = null;
	//循环待添加运单时，用于存放当前运单的运输性质
	var transPropertyCode = null;
	var transProperty = null;
	if(Ext.isEmpty(existWaybill)){
		//待添加运单运输性质标识位
		var tbAddFlag = null;
		var addFlag=null;
		for(var i = 0; i < tbAddWaybills.getCount(); i++){
			waybill = tbAddWaybills.getAt(i);
			//非商务专递都为false
			transProperty = ( (waybill.transPropertyCode || waybill.get("transPropertyCode")) == "DEAP") ? "DEAP" : "false"; 
			//获取到商务专递(DEPA)数据开始进入判断
			if(transProperty == "DEAP"){
				//如果第一条是商务专递数据,则赋值给变量
				if(i==0){
					addFlag=transProperty;	
				//如果不是第一条,则与第一条比较,相等则说明第一条也是商务专递
				}else if(transProperty!=addFlag){
					Ext.ux.Toast.msg('提示', '添加失败，商务专递和其它性质的运单不能在同一个交接单中！', 'error');
					return false;
				}	
			}else{
				if(addFlag!=null){
					Ext.ux.Toast.msg('提示', '添加失败，商务专递和其它性质的运单不能在同一个交接单中！', 'error');
					return false;
				}
			}
			//非快递都为零担
			transPropertyCode = (
					( (waybill.transPropertyCode || waybill.get("transPropertyCode")) === "RCP")
					||( (waybill.transPropertyCode || waybill.get("transPropertyCode")) === "PACKAGE" )
					||( (waybill.transPropertyCode || waybill.get("transPropertyCode")) === "EPEP" )
					) ? "EXPRESS" : "LTL"; 
			
			if(tbAddFlag === null){
				//将标识位设置为第一条待添加运单的运输性质
				tbAddFlag = transPropertyCode;
			}else if(tbAddFlag != transPropertyCode){
				Ext.ux.Toast.msg('提示', '添加失败，零担货和快递货不能混装在同一个交接单中！', 'error');
				return false;
			}
		}
	}else{
		for(var i = 0; i < tbAddWaybills.getCount(); i++){
			waybill = tbAddWaybills.getAt(i);
			
			//非商务专递都为false
			transProperty = ( (waybill.transPropertyCode || waybill.get("transPropertyCode")) === "DEAP") ? "DEAP" : "false";
			var exist =(existWaybill.get("transPropertyCode") === "DEAP") ? "DEAP" : "false";
			if(transProperty=="DEAP"||exist=="DEAP"){
				if(transProperty!=exist){
					Ext.ux.Toast.msg('提示', '添加失败，商务专递和其它性质的运单不能在同一个交接单中！', 'error');
					return false;
				}	
			}
			//非快递都为零担
			transPropertyCode =(
					( (waybill.transPropertyCode || waybill.get("transPropertyCode")) === "RCP")
					||( (waybill.transPropertyCode || waybill.get("transPropertyCode")) === "PACKAGE" )
					||( (waybill.transPropertyCode || waybill.get("transPropertyCode")) === "EPEP")
					) ? "EXPRESS" : "LTL";  
			
			var existFlag = (
								(existWaybill.get("transPropertyCode") === "PACKAGE")
								||(existWaybill.get("transPropertyCode") === "RCP")
								||(existWaybill.get("transPropertyCode") === "EPEP")
							) ? "EXPRESS" : "LTL";
			if(transPropertyCode != existFlag){
				Ext.ux.Toast.msg('提示', '添加失败，零担货和快递货不能混装在同一个交接单中！', 'error');
				return false;
			}
		}
	}
	
	return true;
};

// 定义交接单明细列表
Ext.define('Foss.load.handoverbilladdnew.HandOverBillDetailGrid', {
	extend : 'Ext.grid.Panel',
	frame : true,
	title : load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.gridTitle')/*'交接单明细'*/,
//	bodyCls : 'autoHeight',
	height : 500,
	cls : 'autoHeight',
	enableColumnHide : false,//配置该属性可取消grid自定义显示列功能
	columnLines: true,
	autoScroll : true,
	collapsible : true,
	animCollapse : true,
	store : Ext.create('Foss.load.handoverbilladdnew.HandOverBillDetailStore'),
	// 定义行展开
	plugins : [ {
		header: true,
		ptype : 'rowexpander',
		// 定义行展开模式（单行与多行），默认是多行展开(值true)
		rowsExpander : false,
		// 行体内容
		rowBodyElement : 'Foss.load.handoverbilladdnew.WaybillDetailGrid',
		pluginId : 'Foss_handoverbilladdnew_mainPage_serialNoGrid_ID'
	},Ext.create('Ext.grid.plugin.CellEditing', {
            clicksToEdit : 1,
            listeners : {
				'beforeedit' : function(editor,e,eOpts){
					//如果已经保存，则禁止编辑
					if(load.handoverbilladdnew.isSaved == 'Y'){
						return false;
					}
				}
			}
        })],
	tbar : [{
			xtype : 'button',
			text : load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.addWaybillButtonText')/*'添加运单'*/,
			tooltip : load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.addWaybillButtonToolTipText')/*'点击可批量添加运单'*/,
			id : 'Foss_load_handoverbilladdnew_mainPage_queryButton_ID',
			name : 'queryWaybillButton',
			handler : function() {//必须填写交接类型、到达部门/外发代理
				//每次选择，初始化时间
				
				var basicForm = load.handoverbilladdnew.addNewForm.getForm();
				var handOverType = basicForm.findField('handOverType');
				if(handOverType.value == null){
					load.handoverbilladdnew.handOverBillAlert(handOverType,Ext.MessageBox.WARNING);
					return;
					                              //短配交接单                                                                                                  //集配交接单
				}else if((handOverType.value == 'SHORT_DISTANCE_HANDOVER' || handOverType.value == 'LONG_DISTANCE_HANDOVER'
					  ||handOverType.value =='DIVISION_HANDOVER')//分部交接单
						&& (basicForm.findField('arriveDept').value == null || basicForm.findField('arriveDept').value.trim() == '')){
					load.handoverbilladdnew.handOverBillAlert(basicForm.findField('arriveDept'),Ext.MessageBox.WARNING);
					return;//外发交接单
				}else if(handOverType.value == 'PARTIALLINE_HANDOVER' 
					&& (basicForm.findField('agency').value == null || basicForm.findField('agency').value.trim() == '')){
					load.handoverbilladdnew.handOverBillAlert(basicForm.findField('agency'),Ext.MessageBox.WARNING);
					return;//落地配交接单
				}else if(handOverType.value == 'LDP_HANDOVER'
					&& (basicForm.findField('ldpCompany').value == null || basicForm.findField('ldpCompany').value.trim() == '')){
					load.handoverbilladdnew.handOverBillAlert(basicForm.findField('ldpCompany'),Ext.MessageBox.WARNING);
					return;					
				}				
				load.handoverbilladdnew.queryWayBillWindow.showAt(0,0);
				//根据交接类型来控制弹出窗口查询条件的显示与隐藏
				var queryForm = load.handoverbilladdnew.queryWaybillForm.getForm();
				//****linhua.yan 2016年8月20日****
				
				queryForm.findField('arriveDept').setCombValue(load.handoverbilladdnew.arriveDeptName,load.handoverbilladdnew.arriveDept);//目的站
				queryForm.findField('beginInStorageTime').setValue(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate() - 20));	//初始化时间
				 //短配交接单
				if(handOverType.value == 'SHORT_DISTANCE_HANDOVER'||handOverType.value =='DIVISION_HANDOVER'){//短配交接单或者分部交接单
					//显示运输类型
					queryForm.findField('transType').setVisible(true);
					//隐藏运输性质
					queryForm.findField('transProperty').setVisible(true);
					//在途货物 左边Grid（查询交接单界面--新增时候）
					load.handoverbilladdnew.queryEnRouteWaybillGrid.dockedItems.items[2].setVisible(false);
				}else if(handOverType.value == 'LONG_DISTANCE_HANDOVER'){//集配交接单
					//隐藏运输类型
					queryForm.findField('transType').setVisible(true);
					//显示运输性质
					queryForm.findField('transProperty').setVisible(true);
					load.handoverbilladdnew.queryEnRouteWaybillGrid.dockedItems.items[2].setVisible(true);
				}else{
					//隐藏运输类型
					queryForm.findField('transType').setVisible(false);
					//隐藏运输性质
					queryForm.findField('transProperty').setVisible(false);
					load.handoverbilladdnew.queryEnRouteWaybillGrid.dockedItems.items[2].setVisible(false);
				}
			}
		},'->',{
			xtype : 'textfield',
			fieldLabel : load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.waybillNoColumn')/*'运单号'*/,
			emptyText : load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.quickAddTextfieldToolTipText')/*'输入运单号敲击回车'*/,
			labelWidth : 60,
			vtype : 'waybill',
			id : 'Foss_load_handOverBillAddnew_quickAddWaybillNo_ID',
			enableKeyEvents : true,
			listeners : {
				'keypress' : function(text,e,eOpts){
					//如果敲击回车键，则触发添加按钮事件
					if(e.getKey() == e.ENTER){
						var addButton = Ext.getCmp('Foss_load_handOverBillAddnew_quickAddButton_ID');
						addButton.handler();
					}
				}
			}
		},{
			xtype : 'container',
			html : '&nbsp'
		},{
			xtype : 'button',
			id : 'Foss_load_handOverBillAddnew_quickAddButton_ID',
			text : load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.quickAddButtonText')/*'快速添加'*/,
			handler : function(){
				//基本信息form
				var basicForm = load.handoverbilladdnew.addNewForm.getForm();
				var handOverType = basicForm.findField('handOverType');
				if(handOverType.value == null){
					load.handoverbilladdnew.handOverBillAlert(handOverType,Ext.MessageBox.WARNING);
					return;
				}else if((handOverType.value == 'SHORT_DISTANCE_HANDOVER' || handOverType.value == 'LONG_DISTANCE_HANDOVER'
					    ||handOverType.value =='DIVISION_HANDOVER')
						&& (basicForm.findField('arriveDept').value == null || basicForm.findField('arriveDept').value.trim() == '')){
					load.handoverbilladdnew.handOverBillAlert(basicForm.findField('arriveDept'),Ext.MessageBox.WARNING);
					return;
				}else if(handOverType.value == 'PARTIALLINE_HANDOVER' 
					&& (basicForm.findField('agency').value == null || basicForm.findField('agency').value.trim() == '')){
					load.handoverbilladdnew.handOverBillAlert(basicForm.findField('agency'),Ext.MessageBox.WARNING);
					return;
				}
				var waybillNoCmp = Ext.getCmp('Foss_load_handOverBillAddnew_quickAddWaybillNo_ID'),
					waybillNo = waybillNoCmp.getValue();
				if(!Ext.isEmpty(waybillNo) && waybillNoCmp.isValid()){
					//获取参数
					var arriveDeptCode;
					if(handOverType.value === 'PARTIALLINE_HANDOVER'){
						arriveDeptCode = basicForm.findField('agency').getValue();
					}else{
						arriveDeptCode = basicForm.findField('arriveDept').getValue();
					}
					var arriveDeptList = new Array();
					arriveDeptList.push(arriveDeptCode);
					var jsonData = {
						'handOverBillVo' : {
							'queryWaybillForHandOverBillDto' : {
								'arriveDeptList' : arriveDeptList,
								'waybillNo' : waybillNo,
								'handOverType' : handOverType.value
							}
						}
					},
					// 定义运单列表 (新增界面 交接单明细)
					mainGrid = load.handoverbilladdnew.handOverBillDetailGrid,
					//获取主页面的扩展组件
					plugin = mainGrid.getPlugin('Foss_handoverbilladdnew_mainPage_serialNoGrid_ID');
					var loadMask = new Ext.LoadMask(mainGrid, {
						msg:"加载中，请稍候..."
					});
					//加载运单库存及流水号
					loadMask.show();
					Ext.Ajax.request({
						url : load.realPath('queryWaybillStockByWaybillNo.action'),
						jsonData : jsonData,
						success : function(response) {
							var result = Ext.decode(response.responseText),
								waybillStock = result.handOverBillVo.waybillStock,
								serialNoList = result.handOverBillVo.serialNoStockList;
							//从主页获取该运单号记录
							var record = mainGrid.store.findRecord('waybillNo',waybillNo,0,false,true,true);
							var unsavedSerialNoMap = new Ext.util.HashMap();
							for(var i in serialNoList){
								var serialNo = serialNoList[i],
									serialNoRecord = Ext.ModelManager.create(serialNo, 'Foss.load.handoverbilladdnew.serialNoModel');
								unsavedSerialNoMap.add(serialNo.serialNo,serialNoRecord);
							}
							if(record != null){//若该运单曾经被提交过，则将该运单的流水号累加
								record.set('serialNoMap',unsavedSerialNoMap);
								//更新主页列表中的件数等信息
								record.set('pieces',unsavedSerialNoMap.getCount());
								record.set('cubage',waybillStock.cubage);
								record.set('weight',waybillStock.weight);
								record.set('cubageAc',waybillStock.cubageAc.toFixed(3));
								record.set('weightAc',waybillStock.weightAc.toFixed(3));
								//若主页列表该运单记录的二级表格打开，则刷新二级表格
								if(!Ext.isEmpty(plugin.getExpendRow())) {
									var pluginGrid = plugin.getExpendRowBody();
									var innerStore = pluginGrid.getStore();
									var subWaybillNo = innerStore.getAt(0).get('waybillNo');
									if(subWaybillNo == waybillNo){
										innerStore.loadData(unsavedSerialNoMap.getValues());
									}
								}
							}else{
								  if(mainGrid.store.getCount()>0){
								    var transPropertyCode=waybillStock.transPropertyCode;
								   //右侧 添加的待提交 运单的第一条数据的运输性质 
									var rightTransPropertyCode=mainGrid.store.getAt(0).get('transPropertyCode');
									//判断是否能添加 运单信息到右侧
								    if(!load.handoverbilladdnew.isAdd(transPropertyCode,rightTransPropertyCode,null,false)){
								    waybillNoCmp.focus(true,true);
							        loadMask.hide();
								    return;}
								  }
								var tbAddWaybills = new Ext.util.MixedCollection();
								tbAddWaybills.add(waybillStock);
								
								if(!load.handoverbilladdnew.validateHandoverbillDetailsTransProperty(tbAddWaybills, mainGrid.store.getAt(0))){
									loadMask.hide();
									waybillNoCmp.focus(true,true);
									return;
								}
								
								var newRecord =  Ext.ModelManager.create(waybillStock, 'Foss.load.handoverbilladdnew.waybillStockModel');
								newRecord.set('pieces',unsavedSerialNoMap.getCount());
								newRecord.set('serialNoMap',unsavedSerialNoMap);
								//TODO 不需要复制重量体积，应为后台已经填充
								//newRecord.set('cubageAc',newRecord.get('cubage'));
								//newRecord.set('weightAc',newRecord.get('weight'));
								//将该运单插入主页面
								mainGrid.store.insert(mainGrid.store.getCount(),newRecord);
							}
							Ext.ux.Toast.msg(load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.alertInfoTitle')/*'提示'*/, 
								'添加成功！', 
								'info');
							waybillNoCmp.focus(true,true);
							loadMask.hide();
						},
						exception : function(response){
							var result = Ext.decode(response.responseText);
							Ext.ux.Toast.msg(load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.alertInfoTitle')/*'提示'*/, 
								load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.addFailureAlert')/*'添加失败，'*/ + result.message, 
								'error');
							loadMask.hide();
						}
					});
				}else{
					Ext.ux.Toast.msg(load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.alertInfoTitle')/*'提示'*/, 
								'请输入正确的运单号！', 
								'error',
								1500);
				}
			}
		},{
			xtype : 'container',
			html : '&nbsp'
		},{
		xtype : 'button',
		disabled: !load.handoverbilladdnew.isPermission('load/printnewhandoverbillButton'),
		hidden: !load.handoverbilladdnew.isPermission('load/printnewhandoverbillButton'),
		text : load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.printButtonText')/*'打印'*/,
		handler : function(){
			if(load.handoverbilladdnew.prthandOverBillNo == undefined) {
				Ext.ux.Toast.msg(load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.alertInfoTitle')/*'提示'*/, 
						"请先保存交接单!", 
						'error');
				return;
			}
			var records = this.up('grid').getStore().getRange();
			if(records.length <= 0) {
				Ext.ux.Toast.msg(load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.alertInfoTitle')/*'提示'*/, 
						"没有运单数据, 请确认!", 
				'error');
				return;
			}
			var vehicleNo = load.handoverbilladdnew.addNewForm.getForm().findField('vehicleNo').getValue(),
				handOverBillNos = new Array(),
				handOverBillNo = load.handoverbilladdnew.addNewForm.getForm().findField('handOverBillNo').getValue();
			handOverBillNos.push(handOverBillNo);
			var grids = this.up('grid');
			//如果选择的交接单的车牌号下还有其他的交接单则提示还有其他交接单,请注意
			Ext.Ajax.request({
				url : load.realPath('checkPrintHandOverBill.action'),
				params : {'handOverBillVo.vehicleNo' : vehicleNo,
					'handOverBillVo.handOverBillNos' : handOverBillNos
				},
				success : function(response) {
					var result = Ext.decode(response.responseText),
						count = result.handOverBillVo.checkPrintHandOverBillRestlt;
					var handBillState = result.handOverBillVo.errorMessage;
					if(count > 0) {
						//大于0则说明该车牌号下还有其他交接单尚未选择
						Ext.ux.Toast.msg(load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.alertInfoTitle')/*'提示'*/, 
								"此车牌中还有" + count + "个交接单没有选择打印，请注意!", 
								'error');
					} 
					if(handBillState == 'CANCEL') {
						Ext.ux.Toast.msg('提示', "此交接单已作废，不能打印!", 'error');
					}else{
						Ext.create('Foss.load.handoverbilladdnew.PrintWindow', {
							vehicleNo : vehicleNo,
							handOverBillNos : handOverBillNos,
							grid : grids
						}).show();
					}
				}
			});
		}
	} ],
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
				fieldLabel : load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.totalWaybillLabel')/*'总票数'*/,
				xtype : 'textfield',
				readOnly : true,
				columnWidth : 1/5,
				value : 0,
				id : 'Foss_load_handOverBillAddnew_MainPageTotalCount'
			},{
				fieldLabel : load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.totalPiecesLabel')/*'总件数'*/,
				xtype : 'textfield',
				readOnly : true,
				columnWidth : 1/5,
				value : 0,
				id : 'Foss_load_handOverBillAddnew_MainPageTotalPieces'
			},{
				fieldLabel : '总重量(千克)',
				xtype : 'textfield',
				readOnly : true,
				columnWidth : 1/5,
				value : 0,
				id : 'Foss_load_handOverBillAddnew_MainPageTotalWeight'
			},{
				fieldLabel : '总体积(方)',
				xtype : 'textfield',
				readOnly : true,
				columnWidth : 1/5,
				value : 0,
				id : 'Foss_load_handOverBillAddnew_MainPageTotalVolume'
			},{
				fieldLabel : '未转换总体积(方)',
				xtype : 'textfield',
				readOnly : true,
				columnWidth : 1/5,
				value : 0,
				id : 'Foss_load_handOverBillAddnew_MainPageUnconvertTotalVolume'
			}]
	  }],
	columns : [ {
		xtype : 'actioncolumn',
		width : 60,
		text : load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.actionColumn')/*'操作'*/,
		align : 'center',
		items : [ {
			iconCls : 'deppon_icons_remove',
			tooltip : load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.deleteButtonColumn')/*'删除'*/,
			handler : function(grid, rowIndex, colIndex) {
				grid.getStore().removeAt(rowIndex);
			}
		} ]
	}, {
		dataIndex : 'waybillNo',
		align : 'center',
		width : 80,
		xtype : 'ellipsiscolumn',
		text : load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.waybillNoColumn')/*'运单号'*/
	}, {
		dataIndex : 'transProperty',
		align : 'center',
		width : 95,
		text : load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.transPropertyColumn')/*'运输性质'*/
	}, {
		dataIndex : 'transPropertyCode',
		align : 'center',
		hidden : true, 
		width : 95,
		text : '运输性质编码'/*'运输性质'*/
	},{
		dataIndex : 'pieces',
		align : 'center',
		flex : 1,
		text : load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.piecesColumn')/*'已配件数'*/
	}, {
		dataIndex : 'weight',
		align : 'center',
		flex : 1,
		text : load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.weightColumn')/*'已配重量'*/
	}, {
		dataIndex : 'weightAc',
		align : 'center',
		flex : 1,
		text : load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.weightAcColumn')/*'实际重量'*/
//		editor : {
//			xtype : 'numberfield',
//			allowBlank : false,
//			step : 10,
//			minValue : 0,
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
		text : load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.volumeColumn')/*'已配体积'*/
	}, {
		dataIndex : 'cubageAc',
		align : 'center',
		flex : 1,
		text : load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.volumeAcColumn')/*'实际体积'*/
//		editor : {
//			xtype : 'numberfield',
//			allowBlank : false,
//			step : 1,
//			minValue : 0,
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
		text : load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.noteColumn')/*'备注'*/,
		editor : {
			xtype : 'textarea',
			maxLength : 300
		}
	}, {
		dataIndex : 'goodsName',
		align : 'center',
		flex : 1,
		text : load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.goodsNameColumn')/*'货物名称'*/
	}, {
		dataIndex : 'packing',
		align : 'center',
		flex : 1,
		text : load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.packingColumn')/*'包装'*/
	}, {
		dataIndex : 'waybillNote',
		align : 'center',
		flex : 1,
		text : load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.waybillNoteColumn')/*'运单备注'*/
	} ]
});

//生成当前日期
load.handoverbilladdnew.handOverBillGetDateTime = function(){
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

//定义到达部门，存储每次修改前的值
load.handoverbilladdnew.arriveDept = null;

// 交接单基本信息form
Ext.define('Foss.load.handoverbilladdnew.AddNewForm', {
	extend : 'Ext.form.Panel',
	title : load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.basicInfoForm.formTitle'),
	frame : true,
	collapsible : true,
	height : 220,
	animCollapse : true,
	defaults : {
		margin : '5 10 5 10',
		labelWidth : 85,
		columnWidth : 1 / 4,
		xtype : 'textfield'
	},
	layout : 'column',
	items : [{
		fieldLabel : load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.basicInfoForm.handOverTypeLabel')/*'交接类型'*/,
		name : 'handOverType',
		xtype : 'combobox',
		queryMode: 'local',
		allowBlank : false,
	    displayField: 'value',
	    valueField: 'key',
	    editable : false,
	    value : 'ALL',
	    store : Ext.create('Ext.data.Store', {
	        fields: ['key', 'value'],
	        data : [
	            {"key":"ALL", "value":"全部"},
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
	    		if(newValue === 'PARTIALLINE_HANDOVER'){//如果选择外发交接单，则隐藏“到达部门”，显示“外发代理”
	    			form.findField('agency').setVisible(true);
	    			form.findField('agency').setReadOnly(false);
	    			form.findField('arriveDept').setVisible(false);
	    			form.findField('arriveDept').reset();
	    			form.findField('ldpCompany').setVisible(false);
	    			form.findField('ldpCompany').reset();
	    			//显示“是否代理上门接货”
	    			form.findField('isAgencyVisit').setVisible(true);
	    			//货物类型修改为“全部”且只读
	    			form.findField('goodsType').setValue('ALL');
	    			form.findField('goodsType').setReadOnly(true);
	    			
	    			form.findField('arriveDept').setDisabled(true);
	    			form.findField('ldpCompany').setDisabled(true);
	    			form.findField('agency').setDisabled(false);
	    			// 定义运单列表 (新增界面 交接单明细)
	    			if (load.handoverbilladdnew.handOverBillDetailGrid.store.getCount() > 0) {
						load.handoverbilladdnew.handOverBillDetailGrid.store.removeAll();
					}
	    		}else if(newValue === 'LDP_HANDOVER'){
	    			form.findField('ldpCompany').setVisible(true);
	    			form.findField('ldpCompany').setReadOnly(false);
	    			form.findField('arriveDept').setVisible(false);
	    			form.findField('arriveDept').reset();
	    			form.findField('agency').setVisible(false);
	    			form.findField('agency').reset();
	    			//显示“是否代理上门接货”
	    			form.findField('isAgencyVisit').setVisible(true);
	    			//货物类型修改为“全部”且只读
	    			form.findField('goodsType').setValue('ALL');
	    			form.findField('goodsType').setReadOnly(true);
	    			
	    			form.findField('arriveDept').setDisabled(true);
	    			form.findField('agency').setDisabled(true);
	    			form.findField('ldpCompany').setDisabled(false);
	    			if (load.handoverbilladdnew.handOverBillDetailGrid.store.getCount() > 0) {
						load.handoverbilladdnew.handOverBillDetailGrid.store.removeAll();
					}
			}else if(newValue === 'SALES_DEPARTMENT_HANDOVER'){
				form.findField('driver').setVisible(false);
				form.findField('driver').reset();
				form.findField('driverTel').setVisible(false);
				form.findField('driverTel').reset();
				form.findField('goodsType').setVisible(false);
				form.findField('goodsType').reset();
				form.findField('tranGoodsType').setVisible(false);
				form.findField('tranGoodsType').reset();
				form.findField('isPreHandOverBill').setVisible(false);
				form.findField('isPreHandOverBill').reset();
				form.findField('loadEndTime').setDisabled(false);
				form.findField('loadEndTime').setReadOnly(true);
				var oldHandOverBillNo = form.findField('handOverBillNo').getValue();   
				var fdStart = oldHandOverBillNo.indexOf("yy");
				if(fdStart == -1){
					var newHandOverBillNo = 'yy'+oldHandOverBillNo;
					form.findField('handOverBillNo').setValue(newHandOverBillNo);
				}
				if (load.handoverbilladdnew.handOverBillDetailGrid.store.getCount() > 0) {
					load.handoverbilladdnew.handOverBillDetailGrid.store.removeAll();
				}
	    		}else{
	    			form.findField('agency').setVisible(false);
	    			form.findField('agency').reset();
	    			form.findField('ldpCompany').setVisible(false);
	    			form.findField('ldpCompany').reset();
	    			form.findField('arriveDept').setVisible(true);
	    			form.findField('arriveDept').setReadOnly(false);
	    			//隐藏“是否代理上门接货”
	    			form.findField('isAgencyVisit').setVisible(false);
	    			form.findField('vehicleNo').setReadOnly(false);
					form.findField('driver').setReadOnly(false);
					
					form.findField('arriveDept').setDisabled(false);
					form.findField('agency').setDisabled(true);
					form.findField('ldpCompany').setDisabled(true);
					if(oldValue === 'PARTIALLINE_HANDOVER'
						|| oldValue === 'LDP_HANDOVER'){	
	    	    	}else if(oldValue == 'SALES_DEPARTMENT_HANDOVER'){
					
					}
			}
		    	if(newValue ==='SHORT_DISTANCE_HANDOVER'||newValue==='DIVISION_HANDOVER'){
		    		form.findField('tranGoodsType').bindStore(load.handoverbilladdnew.getTranGoodsStore(1));
		    	}else if(newValue !='SALES_DEPARTMENT_HANDOVER'){
		    		form.findField('tranGoodsType').setValue(load.handoverbilladdnew.getTranGoodsStore(2).findRecord(
								'key','TRANSITGOODS',0,false,false,false));
					form.findField('tranGoodsType').bindStore(load.handoverbilladdnew.getTranGoodsStore(2));
		    	}
		    	/**
		    	 *@auther zenghaibin 205109
		    	 *@date 2014/08/28 11:00
		    	 ***/
		    	if(newValue==='SHORT_DISTANCE_HANDOVER'&&load.handoverbilladdnew.beDivision=='Y'){//假如交接类型为分部交接
		    		form.findField('driver').setReadOnly(true);//只读
		    	
		    		form.findField('driver').reset();
		    		form.findField('driverTel').reset();//清空
		    	}else if(newValue !='SALES_DEPARTMENT_HANDOVER'){
		    		form.findField('driver').setReadOnly(false);
		    	}
	    	}
	    }
	}, {
		fieldLabel : load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.basicInfoForm.handOverBillNoLabel')/*'交接单编号'*/,
		name : 'handOverBillNo',
		allowBlank : false,
		readOnly : true,
		value : load.handoverbilladdnew.handOverBillNo
	}, {
		fieldLabel : load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.basicInfoForm.handOverTimeLabel')/*'交接时间'*/,
		name : 'handOverTime',
		value : load.handoverbilladdnew.handOverBillGetDateTime(),
		readOnly : true
	}, {
		fieldLabel : load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.basicInfoForm.departDeptLabel')/*'出发部门'*/,
		name : 'startDept',
		readOnly : true,
		value : load.handoverbilladdnew.departOrgName
	}, {
		fieldLabel : load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.basicInfoForm.arriveDeptLabel')/*'到达部门'*/,
		allowBlank : false,
		name : 'arriveDept',
		xtype : 'dynamicorgcombselector',
		type : 'ORG',
		salesDepartment : 'Y',
		transferCenter : 'Y',
		airDispatch : 'Y',
		doAirDispatch : 'Y',
		listeners : {
			'blur' : function(cmp, eO, eOpts) {
						// 如果交接单列表中存在有数据，说明已经添加过运单，一旦到达部门
						if (cmp.getValue() != null
							&& cmp.getValue().trim() != ''
							&& cmp.getValue().trim() != load.handoverbilladdnew.arriveDept) {
							var rec = cmp.store.findRecord('code',cmp.getValue(),0,false,true,true);
							// 定义运单列表 (新增界面 交接单明细)
							if (load.handoverbilladdnew.handOverBillDetailGrid.store
									.getCount() > 0) {
								Ext.MessageBox.confirm(load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.alertInfoTitle')/*'提示'*/,
								'确定要更改到达部门吗？</br>这将导致已添加的运单被清空。',function(btn) {
									if (btn == 'yes') {
										load.handoverbilladdnew.handOverBillDetailGrid.store.removeAll();
										load.handoverbilladdnew.arriveDept = cmp.getValue();
										load.handoverbilladdnew.arriveDeptName = rec.get('name');
										return;
									} else {
										cmp.setCombValue(load.handoverbilladdnew.arriveDeptName,load.handoverbilladdnew.arriveDept);
										return;
									}
								});
							}else{
								load.handoverbilladdnew.arriveDept = cmp.getValue();
								load.handoverbilladdnew.arriveDeptName = rec.get('name');
							}
						}
						//获取外场，判断是否支持自动分拣
						if (cmp.getValue() != null && cmp.getValue().trim() != ''){
							Ext.Ajax.request({
								url : load.realPath('queryHandOverBillOutfieldByCode.action'),
								jsonData : {
									'handOverBillVo' : {
										'arriveDeptCode' : cmp.getValue()
									}
								},
								success : function(response){
									var result = Ext.decode(response.responseText);
									var outfield = result.handOverBillVo.outfield;
									var basicInfoForm = load.handoverbilladdnew.addNewForm.getForm();
									//如果支持自动分拣
									if(outfield != null){
										if(outfield.sortingMachine == 'Y'){
											load.handoverbilladdnew.sortingMachine = 'Y';
											basicInfoForm.findField('goodsType').setReadOnly(false);
											
											//load.handoverbilladdnew.sortingMachineOldValue
											//用于保存之前的到达部门是否支持自动分拣属性
											//如果是由非自动分拣到自动分拣则货物类型默认设为A
											if(load.handoverbilladdnew.sortingMachineOldValue != 'Y'){
												load.handoverbilladdnew.sortingMachineOldValue = 'Y'
												basicInfoForm.findField('goodsType').setValue('A_TYPE');
											}
										}else{
											load.handoverbilladdnew.sortingMachine = 'N';
											basicInfoForm.findField('goodsType').setReadOnly(true);
											basicInfoForm.findField('goodsType').setValue('ALL');
										}
									}else{
										load.handoverbilladdnew.sortingMachineOldValue = 'N';
										
										load.handoverbilladdnew.sortingMachine = 'N';
										basicInfoForm.findField('goodsType').setReadOnly(true);
										basicInfoForm.findField('goodsType').setValue('ALL');
									}
								},
								exception : function(response){
									var result = Ext.decode(response.responseText);
					    			top.Ext.MessageBox.alert(load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.alertInfoTitle')/*'提示'*/,result.message);
								}
							});
						}
					}
				}
	},  {
		fieldLabel : load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.basicInfoForm.agencyLabel')/*'外发代理'*/,
		hidden : true,
		xtype : 'commonvehagencycompselector',
		allowBlank : false,
		name : 'agency',
		listeners : {
			'blur' : function(cmp, eO, eOpts) {
				// 如果交接单列表中存在有数据，说明已经添加过运单，一旦外发代理发生更改，则清空运单
				if (cmp.getValue() != null
					&& cmp.getValue().trim() != ''
					&& cmp.getValue().trim() != load.handoverbilladdnew.arriveDept) {
					var rec = cmp.store.findRecord('agentCompanyCode',cmp.getValue(),0,false,true,true);
					if (load.handoverbilladdnew.handOverBillDetailGrid.store
							.getCount() > 0) {
						Ext.MessageBox.confirm(load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.alertInfoTitle')/*'提示'*/,
						'确定要更改外发代理吗？</br>这将导致已添加的运单被清空。',function(btn) {
							if (btn == 'yes') {
								load.handoverbilladdnew.handOverBillDetailGrid.store.removeAll();
								load.handoverbilladdnew.arriveDept = cmp.getValue();
								load.handoverbilladdnew.arriveDeptName = rec.get('agentCompanyName');
								return;
							} else {
								cmp.setCombValue(load.handoverbilladdnew.arriveDeptName,load.handoverbilladdnew.arriveDept);
								return;
							}
						});
					}else{
						load.handoverbilladdnew.arriveDept = cmp.getValue();
						load.handoverbilladdnew.arriveDeptName = rec.get('agentCompanyName');
					}
				}
			}
		}
	}, {
		fieldLabel : '快递代理公司',
		allowBlank : false,
		hidden : true,
		name : 'ldpCompany',
		xtype : 'commonLdpAgencyCompanySelector',
		listeners : {
			'blur' : function(cmp, eO, eOpts) {
				// 如果交接单列表中存在有数据，说明已经添加过运单，一旦落地配公司发生更改，则清空运单
				if (cmp.getValue() != null
					&& cmp.getValue().trim() != ''
					&& cmp.getValue().trim() != load.handoverbilladdnew.arriveDept) {
					var rec = cmp.store.findRecord('agentCompanyCode',cmp.getValue(),0,false,true,true);
					if (load.handoverbilladdnew.handOverBillDetailGrid.store
							.getCount() > 0) {
						Ext.MessageBox.confirm(load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.alertInfoTitle')/*'提示'*/,
						'确定要更改快递代理公司吗？</br>这将导致已添加的运单被清空。',function(btn) {
							if (btn == 'yes') {
								load.handoverbilladdnew.handOverBillDetailGrid.store.removeAll();
								load.handoverbilladdnew.arriveDept = cmp.getValue();
								load.handoverbilladdnew.arriveDeptName = rec.get('agentCompanyName');
								return;
							} else {
								cmp.setCombValue(load.handoverbilladdnew.arriveDeptName,load.handoverbilladdnew.arriveDept);
								return;
							}
						});
					}else{
						load.handoverbilladdnew.arriveDept = cmp.getValue();
						load.handoverbilladdnew.arriveDeptName = rec.get('agentCompanyName');
					}
				}
			}
		}
	},{
		fieldLabel : load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.basicInfoForm.vehicleNoLabel')/*'车牌号'*/,
		allowBlank : false,
		xtype : 'commontruckselector',
		queryParam : 'truckVo.truck.vehicleNoNoLike',
		queryAllFlag : false,
		name : 'vehicleNo',
		listeners : {
			'blur' : function(cmp,eObject,eOpts){
				
				if(!Ext.isEmpty(cmp.getValue())){
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
					
					//200968 2015-12-22  集配交接单,公司车只能用拖车做,外请车不限制
					if(!Ext.isEmpty(vehicleEntity)){
						var truckType = vehicleEntity.data.truckType;
						if(truckType =='公司车'){
							if(handOverType=='LONG_DISTANCE_HANDOVER'){
								var vehicleType=vehicleEntity.raw.vehicleType;
								if(vehicleType=='vehicletype_tractors'){
									Ext.Msg.alert('提示', '请使用挂牌号建立任务！');	//"提示", "请使用挂牌号建立任务！"
									cmp.reset();
								}
							}
						}
					}
					
					if(load.handoverbilladdnew.beDivision=='Y'&&handOverType=='SHORT_DISTANCE_HANDOVER'){
						form.findField('driver').reset();
						form.findField('driverTel').reset();
					}
					//如果司机已经输入，则不读取后台绑定关系
					if(Ext.isEmpty(form.findField('driver').getValue())){
						//后台获取司机信息
						var myMask = new Ext.LoadMask(this.up('form'), {
							msg:"加载中，请稍候..."
						});
						var saveButton = Ext.getCmp('Foss_load_handoverbilladdnew_mainPage_saveButton_ID');
						var buttonMask = new Ext.LoadMask(saveButton, {
							msg:"请稍候..."
						});
						myMask.show();
						buttonMask.show();
						Ext.Ajax.request({
							url : load.realPath('queryDriverInfoByVehicleNoForHandOverBill.action'),
							params : {'handOverBillVo.vehicleNo' : cmp.getValue(),
								       'handOverBillVo.beDivision':load.handoverbilladdnew.beDivision,
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
			}
		}
	}, {
		fieldLabel:'是否挂牌号',
		xtype : 'textfield',
		hidden:true,
		name:'beTrailerVehicleNo'
	}, {
		fieldLabel : load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.basicInfoForm.driverLabel')/*'司机'*/,
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
		fieldLabel : load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.basicInfoForm.driverPhoneLabel')/*'司机电话'*/,
		name : 'driverTel',
		readOnly : true
	},// {
		//fieldLabel : load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.basicInfoForm.loadEndTimeLabel')/*'装车完成时间'*/,
		//name : 'loadEndTime',
		//xtype : 'datetimefield_date97',
		//time : true,
		//id : 'Foss_handOverBillAddNew_loadEndTime_ID',
		//allowBlank:false,
		//dateConfig: {
			//el : 'Foss_handOverBillAddNew_loadEndTime_ID-inputEl'
		//},
		//disabled : true
	//}, 
	{
		xtype: 'datefield',
		fieldLabel : load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.basicInfoForm.loadEndTimeLabel')/*'装车完成时间'*/,
		name : 'loadEndTime',
		format:'Y-m-d',
		value:Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()), 'Y-m-d'),
		xtype : 'datetimefield_date97',
		time : true,
		id : 'Foss_handOverBillAddNew_loadEndTime_ID',
		allowBlank:false,
		dateConfig: {
			el : 'Foss_handOverBillAddNew_loadEndTime_ID-inputEl'
		},
		disabled : true,
		columnWidth:.25
	}, FossDataDictionary.getDataDictionaryCombo('LOAD_GOODS_TYPE',{
			fieldLabel : load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.basicInfoForm.goodsTypeLabel')/*'货物类型'*/,
			labelWidth : 85,
			allowBlank : false,
			readOnly : true,
			name : 'goodsType',
			editable : false,
			listeners: {
				'select': function(field) {
					var me = this;
					if(load.handoverbilladdnew.sortingMachine == 'Y') {
						if(field.getValue() == 'ALL') {
							Ext.Msg.show({
								title : load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.alertInfoTitle')/*'提示'*/,
								msg : '自动分拣的部门必须选择货物类型',
								buttons : Ext.Msg.OK,
								icon: Ext.Msg.WARNING
							});
							field.setValue('A_TYPE');
						}
					}
				}
			}
	}), {
		fieldLabel : load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.basicInfoForm.creatorLabel')/*'制单人'*/,
		name : 'createUser',
		readOnly : true,
		value : FossUserContext.getCurrentUserEmp().empName
	}, {
		fieldLabel : load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.basicInfoForm.modifyManLabel')/*'修改人'*/,
		name : 'modifyUser',
		readOnly : true,
		value : FossUserContext.getCurrentUserEmp().empName
	}, {
		fieldLabel : load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.noteColumn')/*'备注'*/,
		name : 'note',
		maxLength : 300,
		columnWidth : .5
	}, {
		boxLabel : load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.basicInfoForm.isPreHandOverBillLabel')/*'是否预配交接单'*/,
		name : 'isPreHandOverBill',
		xtype : 'checkbox',
		checked : true,
		columnWidth : .15,
		listeners : {
			'change' : function(field,newValue,oldValue,eOpts){
				var form = this.up('form').getForm();
				var loadEndTimeCmp = form.findField('loadEndTime');
				if(newValue){
					loadEndTimeCmp.reset();
					loadEndTimeCmp.setDisabled(true);
				}else{
					loadEndTimeCmp.setDisabled(false);
				}
			}
		}
	},{
		fieldLabel : load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.basicInfoForm.tranGoodsType')/*'转货类型'*/,
		name : 'tranGoodsType',
		xtype : 'combobox',
		queryMode: 'local',
		allowBlank : false,
	    displayField: 'value',
	    valueField: 'key',
	    editable : false
	        
	}, {
		boxLabel : load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.basicInfoForm.isAgencyVisitLabel')/*'是否代理上门接货'*/,
		hidden : true,
		name : 'isAgencyVisit',
		xtype : 'checkbox',
		columnWidth : .15,
		listeners : {
			'change' : function(field,newValue,oldValue,eOpts){
				var form = this.up('form').getForm();
				if(newValue){
					form.findField('vehicleNo').setDisabled(true);
					form.findField('vehicleNo').reset();
					form.findField('driverTel').reset()
					form.findField('driver').setDisabled(true);
					form.findField('driver').reset();
				}else{
					form.findField('vehicleNo').setDisabled(false);
					form.findField('driver').setDisabled(false);
				}
			}
		}
	} ],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
});

//差异store
Ext.define('load.handoverbilladdnew.store',{
	extend: 'Ext.data.Store',
	fields: [
		{name: 'key',  type: 'string'},
		{name: 'value',  type: 'string'}
	],
	proxy: {
		type: 'memory',
		reader: {
			type: 'json',
			root: 'tranGoodType'
		}
	},
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});
load.handoverbilladdnew.getTranGoodsStore = function(value){
	if(value==1){
		return Ext.create('load.handoverbilladdnew.store', {
		 	data:{
				'tranGoodType':[
					{'key':'TRANSITGOODS','value':'转货'},
					{'key':'CARRYGOODS','value':'带货'}
				]
			}
		});
	} else {
		return Ext.create('load.handoverbilladdnew.store', {
		 	data:{
				'tranGoodType':[
					{'key':'TRANSITGOODS','value':'转货'}
				]
			}
		});
	}
}

// 定义查询交接运单界面-查询条件form
Ext.define('Foss.load.handoverbilladdnew.QueryWaybillForm', {
	extend : 'Ext.form.Panel',
	title : load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.queryWaybillForm.formTitle')/*'查询条件'*/,
	frame : true,
	collapsible : true,
	height : 135,
	animCollapse : true,
	defaults : {
		margin : '5 10 5 10',
		labelWidth : 80,
		columnWidth : 1/5,
		xtype : 'textfield'
	},
	layout : 'column',
	items : [{
		xtype : 'rangeDateField',
		fieldLabel : load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.queryWaybillForm.instorageTime')/*'入库时间'*/,
		columnWidth : 2/5,
		// 类型为datetimefield_date97需要配置fieldId,可以赋予此属性任何唯一标 //识的String值
		fieldId : 'Foss_handoverbilladdnew_QueryForm_InstorageTime_fieldID',
		id : 'Foss_handoverbilladdnew_QueryForm_InstorageTime_ID',
		// dateType: 'datetimefield_date97',
		dateType: 'datefield',
		fromName : 'beginInStorageTime',
		toName : 'endInStorageTime',
		toValue : new Date(),
		fromValue : new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate() - 20),
		allowBlank : false,
		disallowBlank: true
	},{
		fieldLabel : load.handoverbilladdnew.i18n('foss.load.handoverbillshow.basicInfoForm.destinationColumn')/*'目的站'*/,
		allowBlank : false,
		name : 'arriveDept',
		xtype : 'dynamicorgcombselector',
		type : 'ORG',
		salesDepartment : 'Y',
		transferCenter : 'Y',
		airDispatch : 'Y',
		doAirDispatch : 'Y',
		hidden : true
	},{
		fieldLabel : load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.queryWaybillForm.transType')/*'运输类型'*/,
		name : 'transType',
		xtype : 'combobox',
		queryMode: 'local',
	    displayField: 'value',
	    valueField: 'key',
	    value : 'ALL',
	    editable : false,
	    store : Ext.create('Ext.data.Store', {
	        fields: ['key', 'value'],
	        data : [
	            {"key":"ALL", "value":"全部"},
	            {"key":"汽运", "value":"汽运"},
	            {"key":"空运", "value":"空运"}
	        ]
	    })
	}, {
		fieldLabel : load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.transPropertyColumn')/*'运输性质'*/,
		name : 'transProperty',
		xtype : 'combobox',
		queryMode: 'local',
		triggerAction:'all',
	    displayField: 'valueName',
	    valueField: 'valueCode',
	    multiSelect : true,
	    editable : false,
	    store : Ext.create('Foss.load.handoverbilladdnew.ProductStore'),
	    listeners : {
	    	'beforeselect' : function(combo, record, index, eOpts){
	    		//如果选择了全部，则清空其他选择
	    		if(record.get('valueCode') === 'ALL'){
	    			combo.clearValue();
	    		}
	    		//如果已选择了全部，则不能再选择其他、
	    		var values = combo.getValue();
	    		for(var i in values){
	    			if(values[i] === 'ALL'){
	    				return false;
	    			}
	    		}
	    	}
	    }
	}, {
		fieldLabel : load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.waybillNoColumn')/*'运单号'*/,
		vtype : 'waybill',
		name : 'waybillNo'
	}, {
		border : false,
		xtype : 'container',
		columnWidth : 1,
		layout : 'column',
		defaults : {
			margin : '5 0 5 0'
		},
		items : [ {
			xtype : 'button',
			columnWidth : .08,
			text : load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.queryWaybillForm.resetButton')/*'重置'*/,
			handler : function() {
				var form = this.up('form').getForm();
				form.reset();
				form.findField('beginInStorageTime').setValue(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate() - 20));
				form.findField('endInStorageTime').setValue(new Date());
			}
		}, {
			border : false,
			columnWidth : .84,
			html : '&nbsp;'
		}, {
			columnWidth : .08,
			xtype : 'button',
			cls : 'yellow_button',
			id : 'Foss_handoverbilladdnew_QueryForm_queryButton_ID',
			text : load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.queryWaybillForm.queryButton')/*'查询'*/,
			handler : function(){
				if(load.handoverbilladdnew.queryWaybillTabPanel.getActiveTab().title == load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.queryResultTab.tab1Title')/*'库存货物'*/){
					load.handoverbilladdnew.pagingBar.moveFirst();
					//库存 左边Grid（查询交接单界面--新增时候）
					var sm = load.handoverbilladdnew.queryWaybillGrid.getSelectionModel();
					sm.deselectAll(true);
					//清空统计信息
					Ext.getCmp('Foss_load_handOverBillAddnew_QueryPageTotalPieces').setValue(0);
					Ext.getCmp('Foss_load_handOverBillAddnew_QueryPageTotalWeight').setValue(0);
					Ext.getCmp('Foss_load_handOverBillAddnew_QueryPageTotalCubage').setValue(0);
					Ext.getCmp('Foss_load_handOverBillAddnew_QueryPageTotalMoney').setValue(0);
					//每次查询前清空map
					load.handoverbilladdnew.selectedWaybillMap.clear();
				}else{
					load.handoverbilladdnew.enRoutePagingBar.moveFirst();
					//在途货物 左边Grid（查询交接单界面--新增时候）
					var sm = load.handoverbilladdnew.queryEnRouteWaybillGrid.getSelectionModel();
					sm.deselectAll(true);
					Ext.getCmp('Foss_load_handOverBillAddnew_EnRouteQueryPageTotalPieces').setValue(0);
					Ext.getCmp('Foss_load_handOverBillAddnew_EnRouteQueryPageTotalWeight').setValue(0);
					Ext.getCmp('Foss_load_handOverBillAddnew_EnRouteQueryPageTotalCubage').setValue(0);
					Ext.getCmp('Foss_load_handOverBillAddnew_EnRouteQueryPageTotalMoney').setValue(0);
					//每次查询前清空map
					load.handoverbilladdnew.selectedEnRouteWaybillMap.clear();
				}
			}
		} ]
	} ],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
});

// 定义查询库存运单store
Ext.define('Foss.load.handoverbilladdnew.QueryWaybillStore', {
	extend : 'Ext.data.Store',
	// 绑定一个模型
	model : 'Foss.load.handoverbilladdnew.waybillStockModel',
	buffered : false,
	// 定义一个代理对象
	proxy : {
		type : 'ajax',
		actionMethods:'POST',
		// 请求的url
		url : load.realPath('queryWaybillStockList.action'),
		// 定义一个读取器
		reader : {
			// 以JSON的方式读取
			type : 'json',
			// 定义读取JSON数据的根对象
			root : 'handOverBillVo.waybillStockList',
			totalProperty : 'totalCount',
			successProperty: 'success'
		}
	},
	buttonLoadMask : null,
	getButtonLoadMask : function(){
		if(this.buttonLoadMask == null){
			this.buttonLoadMask = new Ext.LoadMask(Ext.getCmp('Foss_handoverbilladdnew_QueryForm_queryButton_ID'),{
				msg : '.....'
			});
		}
		return this.buttonLoadMask;
	},
	gridLoadMask : null,
	getGridLoadMask : function(){
		if(this.gridLoadMask == null){
			this.gridLoadMask = new Ext.LoadMask(load.handoverbilladdnew.queryWaybillGrid, {
				msg:"数据查询中，请稍候..."
			});
		}
		return this.gridLoadMask;
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	},
	listeners : {
		'load' : function( store, records, successful, eOpts){
			//库存 左边Grid（查询交接单界面--新增时候）
			var sm = load.handoverbilladdnew.queryWaybillGrid.getSelectionModel(),
				record,
				waybillNo;
			for(var i in records){
				record = records[i];
				waybillNo = record.get('waybillNo');
				//tempWaybillMap.add(waybillNo,record);
				//数据加载后，检查之前是否被勾选，若缓存map中记录的有该运单号，则重新勾选
				if(load.handoverbilladdnew.selectedWaybillMap.get(waybillNo) != null){
					sm.select(record,true,true);//勾选第一层表格中的行：第二个参数为true，保留其他已勾选的行，第三个参数为true，表示勾选后不触发select事件
				}
			};
			this.getButtonLoadMask().hide();
			this.getGridLoadMask().hide();
		},
		'beforeload' : function(store, operation, eOpts){
			this.getGridLoadMask().show();
			this.getButtonLoadMask().show();
			var form =  load.handoverbilladdnew.queryWaybillForm.getForm();
			var handOverType = load.handoverbilladdnew.addNewForm.getForm().findField('handOverType').getValue();
			var arriveDeptCode = load.handoverbilladdnew.addNewForm.getForm().findField('arriveDept').getValue();
			var agencyCode = load.handoverbilladdnew.addNewForm.getForm().findField('agency').getValue();
			var ldpCode = load.handoverbilladdnew.addNewForm.getForm().findField('ldpCompany').getValue();
			if(!form.findField('beginInStorageTime').isValid()){
				Ext.ux.Toast.msg(load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.alertInfoTitle')/*'提示'*/, load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.queryResultTab.input1stDate')/*'请输入起始日期'*/, 'error', 2000);
				return false;
			}
			//因为入库时间只有年月日，故天数加一天
			var endInStorageTime = form.findField('endInStorageTime').getValue();
			if(!form.findField('endInStorageTime').isValid()){
				Ext.ux.Toast.msg(load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.alertInfoTitle')/*'提示'*/, load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.queryResultTab.input2ndDate')/*'请输入截止日期'*/, 'error', 2000);
				return false;
			}
			endInStorageTime = new Date(endInStorageTime.getFullYear(),endInStorageTime.getMonth(),endInStorageTime.getDate() + 1)
			//到达部门
			var arriveDeptList = new Array();
			arriveDeptList.push(arriveDeptCode);
			//外发代理
			var agencyList = new Array();
			agencyList.push(agencyCode);
			//落地配公司
			var ldpList = new Array();
			ldpList.push(ldpCode);
			//如果为短配交接单或营业部交接单
			if(handOverType == 'SHORT_DISTANCE_HANDOVER'||handOverType=='DIVISION_HANDOVER'||handOverType == 'SHORT_DISTANCE_HANDOVER' || handOverType == 'SALES_DEPARTMENT_HANDOVER'){
				Ext.apply(operation, {
				params : {
					'handOverBillVo.queryWaybillForHandOverBillDto.beginInStorageTime' : form.findField('beginInStorageTime').getValue(),
					'handOverBillVo.queryWaybillForHandOverBillDto.endInStorageTime' : endInStorageTime,
					'handOverBillVo.queryWaybillForHandOverBillDto.waybillNo' : form.findField('waybillNo').getValue(),
					'handOverBillVo.queryWaybillForHandOverBillDto.transType' : form.findField('transType').getValue(),
					'handOverBillVo.queryWaybillForHandOverBillDto.goodsType' : load.handoverbilladdnew.addNewForm.getForm().findField('goodsType').getValue(),
					'handOverBillVo.queryWaybillForHandOverBillDto.transPropertyList' : form.findField('transProperty').getValue(),
					'handOverBillVo.queryWaybillForHandOverBillDto.arriveDeptList' : arriveDeptList,
					'handOverBillVo.queryWaybillForHandOverBillDto.handOverType' : handOverType
				}
			});
			//如果为集配交接单
			}else if(handOverType == 'LONG_DISTANCE_HANDOVER'){
				Ext.apply(operation, {
					params : {
						'handOverBillVo.queryWaybillForHandOverBillDto.beginInStorageTime' : form.findField('beginInStorageTime').getValue(),
						'handOverBillVo.queryWaybillForHandOverBillDto.endInStorageTime' : endInStorageTime,
						'handOverBillVo.queryWaybillForHandOverBillDto.waybillNo' : form.findField('waybillNo').getValue(),
						'handOverBillVo.queryWaybillForHandOverBillDto.transType' : form.findField('transType').getValue(),
						'handOverBillVo.queryWaybillForHandOverBillDto.transPropertyList' : form.findField('transProperty').getValue(),
						'handOverBillVo.queryWaybillForHandOverBillDto.goodsType' : load.handoverbilladdnew.addNewForm.getForm().findField('goodsType').getValue(),
						'handOverBillVo.queryWaybillForHandOverBillDto.arriveDeptList' : arriveDeptList,
						'handOverBillVo.queryWaybillForHandOverBillDto.handOverType' : handOverType
					}
				});
			//如果为落地配交接单
			}else if(handOverType === 'LDP_HANDOVER'){
				Ext.apply(operation, {
					params : {
						'handOverBillVo.queryWaybillForHandOverBillDto.beginInStorageTime' : form.findField('beginInStorageTime').getValue(),
						'handOverBillVo.queryWaybillForHandOverBillDto.endInStorageTime' : endInStorageTime,
						'handOverBillVo.queryWaybillForHandOverBillDto.waybillNo' : form.findField('waybillNo').getValue(),
						'handOverBillVo.queryWaybillForHandOverBillDto.arriveDeptList' : ldpList,
						'handOverBillVo.queryWaybillForHandOverBillDto.handOverType' : handOverType
					}
				});
			//如果为外发交接单
			}else{
				Ext.apply(operation, {
				params : {
						'handOverBillVo.queryWaybillForHandOverBillDto.beginInStorageTime' : form.findField('beginInStorageTime').getValue(),
						'handOverBillVo.queryWaybillForHandOverBillDto.endInStorageTime' : endInStorageTime,
						'handOverBillVo.queryWaybillForHandOverBillDto.waybillNo' : form.findField('waybillNo').getValue(),
						'handOverBillVo.queryWaybillForHandOverBillDto.arriveDeptList' : agencyList,
						'handOverBillVo.queryWaybillForHandOverBillDto.handOverType' : handOverType
					}
				});
			}
		}
	}
});

//定义查询在途运单store
Ext.define('Foss.load.handoverbilladdnew.QueryEnRouteWaybillStore', {
	extend : 'Ext.data.Store',
	// 绑定一个模型
	model : 'Foss.load.handoverbilladdnew.waybillStockModel',
	buffered : false,
	// 定义一个代理对象
	proxy : {
		type : 'ajax',
		actionMethods:'POST',
		// 请求的url
		url : load.realPath('queryEnRouteWaybillList.action'),
		// 定义一个读取器
		reader : {
			// 以JSON的方式读取
			type : 'json',
			// 定义读取JSON数据的根对象
			root : 'handOverBillVo.waybillStockList',
			totalProperty : 'totalCount',
			successProperty: 'success'
		}
	},
	buttonLoadMask : null,
	getButtonLoadMask : function(){
		if(this.buttonLoadMask == null){
			this.buttonLoadMask = new Ext.LoadMask(Ext.getCmp('Foss_handoverbilladdnew_QueryForm_queryButton_ID'),{
				msg : '.....'
			});
		}
		return this.buttonLoadMask;
	},
	gridLoadMask : null,
	getGridLoadMask : function(){
		if(this.gridLoadMask == null){
			this.gridLoadMask = new Ext.LoadMask(load.handoverbilladdnew.queryEnRouteWaybillGrid, {
				msg:"数据查询中，请稍候..."
			});
		}
		return this.gridLoadMask;
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	},
	listeners : {
		'load' : function( store, records, successful, eOpts){
			//在途货物 左边Grid（查询交接单界面--新增时候）
			var sm = load.handoverbilladdnew.queryEnRouteWaybillGrid.getSelectionModel();
			var record;
			var waybillId;
			for(var i in records){
				record = records[i];
				waybillId = record.get('id');
				//数据加载后，检查之前是否被勾选，若缓存map中记录的有该运单号，则重新勾选
				if(load.handoverbilladdnew.selectedEnRouteWaybillMap.get(waybillId) != null){
					var selectedRecord = load.handoverbilladdnew.queryEnRouteWaybillGrid.store.findRecord('id', waybillId, 0, false, true, true);//
					sm.select(selectedRecord,true,true);//勾选第一层表格中的行：第二个参数为true，保留其他已勾选的行，第三个参数为true，表示勾选后不触发select事件
				}
			};
			this.getButtonLoadMask().hide();
			this.getGridLoadMask().hide();
		},
		'beforeload' : function(store, operation, eOpts){
			this.getButtonLoadMask().show();
			this.getGridLoadMask().show();
			var form =  load.handoverbilladdnew.queryWaybillForm.getForm();
			var handOverType = load.handoverbilladdnew.addNewForm.getForm().findField('handOverType').getValue();
			var arriveDeptCode = load.handoverbilladdnew.addNewForm.getForm().findField('arriveDept').getValue();
			var agencyCode = load.handoverbilladdnew.addNewForm.getForm().findField('agency').getValue();
			var ldpCode = load.handoverbilladdnew.addNewForm.getForm().findField('ldpCompany').getValue();
			//到达部门
			var arriveDeptList = new Array();
			arriveDeptList.push(arriveDeptCode);
			//获取查询方案里的配载部门
			if(load.handoverbilladdnew.AddAssembleDeptWindow != null){
				var store = load.handoverbilladdnew.addAssembleDeptGrid.store;
			store.each(function(record){
					arriveDeptList.push(record.get('assembleDeptCode'));
				});
			}
			//外发代理
			var agencyList = new Array();
			agencyList.push(agencyCode);
			//落地配公司
			var ldpList = new Array();
			ldpList.push(ldpCode);
			//如果为短配交接单或者分部交接单
			if(handOverType == 'SHORT_DISTANCE_HANDOVER'||handOverType=='DIVISION_HANDOVER'){
				Ext.apply(operation, {
				params : {
					'handOverBillVo.queryWaybillForHandOverBillDto.waybillNo' : form.findField('waybillNo').getValue(),
					'handOverBillVo.queryWaybillForHandOverBillDto.transType' : form.findField('transType').getValue(),
					'handOverBillVo.queryWaybillForHandOverBillDto.transPropertyList' : form.findField('transProperty').getValue(),
					'handOverBillVo.queryWaybillForHandOverBillDto.arriveDeptList' : [arriveDeptCode],
					'handOverBillVo.queryWaybillForHandOverBillDto.handOverType' : handOverType
				}
			});
			//如果为集配交接单
			}else if(handOverType == 'LONG_DISTANCE_HANDOVER'){
				Ext.apply(operation, {
				params : {
					'handOverBillVo.queryWaybillForHandOverBillDto.waybillNo' : form.findField('waybillNo').getValue(),
					'handOverBillVo.queryWaybillForHandOverBillDto.transType' : form.findField('transType').getValue(),
					'handOverBillVo.queryWaybillForHandOverBillDto.transPropertyList' : form.findField('transProperty').getValue(),
					'handOverBillVo.queryWaybillForHandOverBillDto.arriveDeptList' : arriveDeptList,
					'handOverBillVo.queryWaybillForHandOverBillDto.handOverType' : handOverType
				}
			});
			//如果为落地配外发
			}else if(handOverType == 'LDP_HANDOVER'){
				Ext.apply(operation, {
					params : {
						'handOverBillVo.queryWaybillForHandOverBillDto.waybillNo' : form.findField('waybillNo').getValue(),
						'handOverBillVo.queryWaybillForHandOverBillDto.arriveDeptList' : ldpList,
						'handOverBillVo.queryWaybillForHandOverBillDto.handOverType' : handOverType
					}
				});
			//如果为外发交接单
			}else{
				Ext.apply(operation, {
					params : {
						'handOverBillVo.queryWaybillForHandOverBillDto.waybillNo' : form.findField('waybillNo').getValue(),
						'handOverBillVo.queryWaybillForHandOverBillDto.arriveDeptList' : agencyList,
						'handOverBillVo.queryWaybillForHandOverBillDto.handOverType' : handOverType
					}
				});
			}
		}
	}
});
/**
 * 定义map，selectedWaybillMap：key，运单号，value，选中的某行运单库存列
 * 						  用于记录被勾选的运单库存
 */
load.handoverbilladdnew.selectedWaybillMap = new Ext.util.HashMap();

// 定义库存运单查询结果列表
Ext.define('Foss.load.handoverbilladdnew.QueryWaybillGrid', {
	extend : 'Ext.grid.Panel',
	height : 603,
	columnLines: true,
	bodyCls : 'autoHeight',
	cls : 'tworowbbargirdpanel',
	autoScroll : true,
	//collapsible : true,
	animCollapse : true,
	viewConfig : {
		loadMask : false
	},
	emptyText : load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.queryResultTab.emptyText')/*'查询结果为空'*/,
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.load.handoverbilladdnew.QueryWaybillStore');
		me.selModel = Ext.create('Ext.selection.CheckboxModel',{
			mode : 'SIMPLE',
			checkOnly : true//限制只有点击checkBox后才能选中行
		});
		me.bbar = Ext.create('Deppon.StandardPaging',{
			store : me.store,
			pageSize : 30,
			maximumSize : 50,
			plugins : Ext.create('Deppon.ux.PageSizePlugin', {
				sizeList : [['20', 20], ['30', 30], ['40', 40], ['50', 50]]
			})
		});
		load.handoverbilladdnew.pagingBar = me.bbar;
		me.callParent([cfg]);
	},
	dockedItems: [{
	    xtype: 'toolbar',
	    id : 'Foss_load_handOverBillAddnew_QueryPageTotalPieces_toobar',
	    dock: 'bottom',
	    layout : 'column',
	    defaults: {
			xtype : 'textfield',
			readOnly : true,
			labelWidth : 120
		},
		items: [{
				id : 'Foss_load_handOverBillAddnew_QueryPageTotalPieces',
				fieldLabel: load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.queryResultTab.totalPieces')/*'已选总件数'*/,
				columnWidth : 1/3,
				value : 0
			},{
				id : 'Foss_load_handOverBillAddnew_QueryPageTotalWeight',
				fieldLabel: '已选总重量(千克)',
				columnWidth : 1/3,
				value : 0
			},{
				id : 'Foss_load_handOverBillAddnew_QueryPageTotalCubage',
				fieldLabel: '已选总体积(方)',
				columnWidth : 1/3,
				value : 0
			},{
				id : 'Foss_load_handOverBillAddnew_QueryPageUnconvertTotalCubage',
				fieldLabel: '已选未转换总体积(方)',
				labelWidth : 140,
				columnWidth : 1/2,
				value : 0
			},{
				id : 'Foss_load_handOverBillAddnew_QueryPageTotalMoney',
				fieldLabel: load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.queryResultTab.totalMoney')/*'已选总金额'*/,
				columnWidth : 1/2,
				value : 0
			}]
    }],
	// 定义行展开
	plugins : [ {
		header: true,
		ptype : 'rowexpander',
		// 定义行展开模式（单行与多行），默认是多行展开(值true)
		pluginId : 'handoverbilladdnew_queryWaybillGrid_serialNoGrid',
		rowsExpander : false,
		// 行体内容
		rowBodyElement : 'Foss.load.handoverbilladdnew.QueryWaybillSerialNoGrid'
	} ],
	rightMove : function(grid,record,rowIndex,type){ 
		var waybillNo = record.get('waybillNo'),
			unsubmitedGrid = load.handoverbilladdnew.unsubmitedWaybillGrid,//代提交运单grid
			unStore = unsubmitedGrid.store,
			unRec = unStore.findRecord('waybillNo',waybillNo,0,false,true,true),//移动的运单的store
			//获取主页面该运单号的record
			unsavedWaybill = load.handoverbilladdnew.handOverBillDetailGrid.store.findRecord('waybillNo',waybillNo,0,false,true,true),
			serialNoList = record.get('serialNoStockList'),
			flag = false;
		//如果已勾选运单map中有此运单，则移除
		if(load.handoverbilladdnew.selectedWaybillMap.get(waybillNo) !== undefined){
			load.handoverbilladdnew.selectedWaybillMap.removeAtKey(waybillNo);
			flag = true;
		}
		//右侧grid中是否有该运单
		if(unRec === null){
			//右侧 已经有添加待提交运单  需要判断能不能添加
			if(unStore.getCount()>0){
				    var transPropertyCode=record.get('transPropertyCode');
					//右侧 添加的待提交 运单的第一条数据的运输性质 
					var rightTransPropertyCode=unStore.getAt(0).get('transPropertyCode');
					//判断是否能添加 运单信息到右侧
				    if(!load.handoverbilladdnew.isAdd(transPropertyCode,rightTransPropertyCode,null,false)){
				    return;}
			}
			//构造当前运单下所有流水号MAP
			var serialNoMap = new Ext.util.HashMap();
			//实际重量，体积
			var weightAc = 0,volumnAc = 0;
			//循环构造流水号map 和 累加重量 体积
			for(var i in serialNoList){
				var serialNo = serialNoList[i],
					isPreHandOver = serialNo.isPreHandOver;
				
				
				//如果主页面没有的流水号，并且是未预配状态，则可右移
				if((unsavedWaybill === null || unsavedWaybill.get('serialNoMap').get(serialNo.serialNo) === undefined)
					&& isPreHandOver !== 'Y'){
					//累加每个流水号的重量体积
					weightAc = weightAc+serialNo.weight;
					volumnAc = volumnAc+serialNo.volumn;
					//构造流水号store
					var serialNoRecord = Ext.ModelManager.create(serialNo, 'Foss.load.handoverbilladdnew.serialNoModel');
					serialNoMap.add(serialNo.serialNo,serialNoRecord);
				}
			}
			
			if(serialNoMap.getCount() !== 0){
				record.set('serialNoMap',serialNoMap);
				//插入右侧grid
				var recCopy = record.copy(),
					pieces = serialNoMap.getCount();
				//填充按平均分配方式的重量体积
				recCopy.set('weight',((recCopy.get('weight')/recCopy.get('pieces'))*pieces).toFixed(3));
				recCopy.set('cubage',((recCopy.get('cubage')/recCopy.get('pieces'))*pieces).toFixed(3));
				//填充按流水号即每件货累加的重量和体积
				recCopy.set('weightAc',weightAc.toFixed(3));
				recCopy.set('cubageAc',volumnAc.toFixed(3));
				recCopy.set('pieces',pieces);
				
				unStore.insert(unStore.getCount(),recCopy);
			}
		}else{
			var serialNoMap = unRec.get('serialNoMap');
			//实际重量，体积
			var weightAc = 0,volumnAc = 0;
			//循环更新流水号map 和 累加重量 体积
			for(var i in serialNoList){
				var serialNo = serialNoList[i],
					isPreHandOver = serialNo.isPreHandOver;
				//如果主页面没有的流水号，并且是未预配状态，则可右移
				if((unsavedWaybill === null || unsavedWaybill.get('serialNoMap').get(serialNo.serialNo) === undefined)
					&& isPreHandOver !== 'Y'){
					var serialNoRecord = Ext.ModelManager.create(serialNo, 'Foss.load.handoverbilladdnew.serialNoModel');
					serialNoMap.replace(serialNo.serialNo,serialNoRecord);
				}
			}
			//更新件数、重量、体积
			var pieces = serialNoMap.getCount();
			unRec.set('weight',((unRec.get('weight')/unRec.get('pieces'))*pieces).toFixed(3));
			unRec.set('cubage',((unRec.get('cubage')/unRec.get('pieces'))*pieces).toFixed(3));
			//更新按流水号即每件货累加的重量和体积
			serialNoMap.each(function(serial,serialNo,length){
				//累加每个流水号的重量体积
				weightAc = weightAc+serialNo.get('weight');
				volumnAc = volumnAc+serialNo.get('volumn');
			});
			unRec.set('weightAc',weightAc.toFixed(3));
			unRec.set('cubageAc',volumnAc.toFixed(3));
			unRec.set('pieces',pieces);
		}
		var plugin = unsubmitedGrid.getPlugin('handoverbilladdnew_unsubmitedWaybillGrid_serialNoGrid');
		if(!Ext.isEmpty(plugin.getExpendRow())) {
			var item = plugin.getExpendRowBody();
			var innerStore = item.getStore();
			var subWaybillNo = innerStore.getAt(0).get('waybillNo');
			if(waybillNo == subWaybillNo){
				innerStore.loadData(serialNoMap.getValues());
			}
		}
		//如果是单条移除，则逐条移除
		if(type === 'ONE'){
			//移除该运单
			grid.store.removeAt(rowIndex);
			//如果勾选map有变动，则重新统计信息
			if(flag){
				load.handoverbilladdnew.updateQueryPageStaInfo();
			}
		}
		//重新统计已选运单
		load.handoverbilladdnew.updateUnsubmitedWaybillStaInfo();
		return flag;
	},
	columns : [ {
		xtype : 'actioncolumn',
		width : 40,
		text : load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.actionColumn')/*'操作'*/,
		align : 'center',
		items : [ {
			tooltip : load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.queryResultTab.moveRight')/*'右移'*/,
			iconCls : 'foss_icons_stl_sendmes',
			handler : function(grid, rowIndex, colIndex) {
				var record = grid.store.getAt(rowIndex);
				//库存 左边Grid（查询交接单界面--新增时候）
				load.handoverbilladdnew.queryWaybillGrid.rightMove(grid,record,rowIndex,'ONE');
			}
		} ]
	}, {
		dataIndex : 'waybillNo',
		align : 'center',
		width : 80,
		xtype : 'ellipsiscolumn',
		text : load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.waybillNoColumn')/*'运单号'*/
	}, {
		dataIndex : 'transProperty',
		align : 'center',
		width : 95,
		text : load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.transPropertyColumn')/*'运输性质'*/
	}, {
		dataIndex : 'transPropertyCode',
		align : 'center',
		hidden: true,
		width : 95,
		text : '运输性质编码'/*'运输性质'*/
	}, {
		dataIndex : 'goodsName',
		align : 'center',
		width : 60,
		xtype : 'ellipsiscolumn',
		text : load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.goodsNameColumn')/*'货物名称'*/
	}, {
		dataIndex : 'packing',
		align : 'center',
		width : 60,
		xtype : 'ellipsiscolumn',
		text : load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.packingColumn')/*'包装'*/
	}, {
		dataIndex : 'cubage',
		align : 'center',
		width : 60,
		xtype : 'ellipsiscolumn',
		text : load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.volume')/*'体积'*/
	}, {
		dataIndex : 'weight',
		align : 'center',
		width : 60,
		xtype : 'ellipsiscolumn',
		text : load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.weight')/*'重量'*/
	}, {
		dataIndex : 'pieces',
		align : 'center',
		flex : 1,
		text : load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.piecees')/*'件数'*/
	}, {
		dataIndex : 'instorageDate',
		align : 'center',
		width : 90,
		text : load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.waybillDate')/*'入库日期'*/,
		renderer : function(value) {
			if(value!=null){
					var date = new Date(value);
					return Ext.Date.format(date, 'Y-m-d');									
			}else{
					return null;
	}}
	}, {
		dataIndex : 'arriveDept',
		align : 'center',
		width : 150,
		xtype : 'ellipsiscolumn',
		text : load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.basicInfoForm.arriveDeptLabel')/*'到达部门'*/
	}, {
		dataIndex : 'insuranceValue',
		align : 'center',
		width : 60,
		xtype : 'ellipsiscolumn',
		text : load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.insuranceValue')/*'保险价值'*/
	}, {
		dataIndex : 'waybillDate',
		align : 'center',
		width : 90,
		text : '开单日期',
		renderer : function(value) {
			if(value!=null){
					var date = new Date(value);
					return Ext.Date.format(date, 'Y-m-d');									
			}else{
					return null;
	}}
	}, {
		dataIndex : 'waybillPieces',
		align : 'center',
		width : 60,
		xtype : 'ellipsiscolumn',
		text : load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.waybillPieces')/*'开单件数'*/
	}, {
		dataIndex : 'isPreciousGoods',
		align : 'center',
		width : 80,
		text : load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.isPreciousGoods')/*'是否贵重物品'*/,
		renderer : function(value) {
			if (value == 'Y') {
				return '是';
			} else {
				return '否';
			}
		}
	}, {
		dataIndex : 'waybillNote',
		align : 'center',
		width : 200,
		xtype : 'ellipsiscolumn',
		text : load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.waybillNoteColumn')/*'运单备注'*/
	} ],
	getRightMenu : function(record,rowIndex){
		var grid = this;
		function rightMoveOne(){
			grid.rightMove(grid,record,rowIndex,'ONE');
		}
		var	rightMenu =	new Ext.menu.Menu({
	        items : [{
                text : load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.queryResultTab.moveRight')/*'右移'*/,
                handler : rightMoveOne
	        }]
	     });
		 return rightMenu;
	},
	listeners : {
		'select' : function(rowModel, record, index, eOpts ){
			var grid = this,
				plugin = grid.getPlugin('handoverbilladdnew_queryWaybillGrid_serialNoGrid'),
				waybillNo = record.get('waybillNo'),
				// 定义运单列表 (新增界面 交接单明细)
				unsavedWaybill = load.handoverbilladdnew.handOverBillDetailGrid.store.findRecord('waybillNo',waybillNo,0,false,true,true),
				// 右边 待提交运单的grid（已选运单列表 查询交接单界面--新增时候）
				unsubmitedWaybill = load.handoverbilladdnew.unsubmitedWaybillGrid.store.findRecord('waybillNo',waybillNo,0,false,true,true);
			//取出record中的流水号list，做成map，放到第一层map的record中
			var serialNoStockList = record.get('serialNoStockList'),
				serialNoStockMap = new Ext.util.HashMap();
			for(var i in serialNoStockList){
				var serialNo = serialNoStockList[i];
					isPreHandOver = serialNo.isPreHandOver;
					//待提交 和 待保存 的运单对应的流水号Map不为空则不处理，否则重新更新该运单的流水号集合
				if((unsavedWaybill != null && unsavedWaybill.get('serialNoMap') !== '' && unsavedWaybill.get('serialNoMap').get(serialNo.serialNo) !== undefined)
					||(unsubmitedWaybill != null && unsubmitedWaybill.get('serialNoMap') !== '' && unsubmitedWaybill.get('serialNoMap').get(serialNo.serialNo) !== undefined)
						|| isPreHandOver == 'Y'){
					continue;
				}
				var serialNoRecord = Ext.ModelManager.create(serialNoStockList[i], 'Foss.load.handoverbilladdnew.serialNoModel');
				serialNoStockMap.replace(serialNo.serialNo,serialNoRecord);
			}
			if(serialNoStockMap.getCount() !== 0){
				//将运单置于已勾选运单map中
				var recCopy = record.copy();
				load.handoverbilladdnew.selectedWaybillMap.add(waybillNo,recCopy);
				recCopy.set('serialNoMap',serialNoStockMap);
				load.handoverbilladdnew.updateQueryPageStaInfo();
				//如果二级表被展开，则勾选
				if(!Ext.isEmpty(plugin.getExpendRow())) {
					var item = plugin.getExpendRowBody();
					var innerStore = item.getStore();
					var subWaybillNo = innerStore.getAt(0).get('waybillNo');
					if(subWaybillNo === waybillNo){
						item.getSelectionModel().selectAll(true);
					}
				}
			}else{
				//反选之
				var sm = grid.getSelectionModel();
				sm.deselect(record,true);
			}
		},
	'deselect' : function( rowModel, record, index, eOpts){
		var grid = this;
		var plugin = grid.getPlugin('handoverbilladdnew_queryWaybillGrid_serialNoGrid');
		if(!Ext.isEmpty(plugin.getExpendRow())) {
			var item = plugin.getExpendRowBody();
			var store = item.getStore();
			var waybillNo = store.getAt(0).get('waybillNo');
			if(waybillNo == record.get('waybillNo')){
				item.getSelectionModel().deselectAll(true);
			}
		}
		var waybillNo = record.get('waybillNo');
		if(load.handoverbilladdnew.selectedWaybillMap.get(waybillNo) !== undefined){
			// 从map中移除和此运单对应的库存信息
			load.handoverbilladdnew.selectedWaybillMap.removeAtKey(waybillNo);
		}
		record.set('serialNoMap','');
		load.handoverbilladdnew.updateQueryPageStaInfo();
	},
	'itemcontextmenu' : function(view,record,item,index,e, eOpts ){
		var menu = this.getRightMenu(record,index);
		e.preventDefault();
       	menu.showAt(e.getXY());
	}
}
});

// 定义查询库存运单中流水号列表store
Ext.define('Foss.load.handoverbilladdnew.QueryWaybillSerialNoStore', {
	extend : 'Ext.data.Store',
	// 绑定一个模型
	model : 'Foss.load.handoverbilladdnew.serialNoModel',
	listeners : {
		'remove' : function(store, record, index, eOpts){
			var waybillNo = record.get('waybillNo'),
				serialNo = record.get('serialNo');
			//将移除的流水号从一级record的stockList中删除
				//库存 左边Grid（查询交接单界面--新增时候）
			var superRec = load.handoverbilladdnew.queryWaybillGrid.store.findRecord('waybillNo',waybillNo,0,false,true,true),
				serialNoStockList = superRec.get('serialNoStockList');
			for(var i = 0;i < serialNoStockList.length;i++){
				var serialNoRec = serialNoStockList[i];
				if(serialNoRec.serialNo === serialNo){
					serialNoStockList.splice(i,1);
					break;
				}
			}
		}
	}
});

//定义查询在途运单中流水号列表store
Ext.define('Foss.load.handoverbilladdnew.QueryEnRouteWaybillSerialNoStore', {
	extend : 'Ext.data.Store',
	// 绑定一个模型
	model : 'Foss.load.handoverbilladdnew.serialNoModel',
	listeners : {
		'remove' : function(store, record, index, eOpts){
			var waybillNo = record.get('waybillNo'),
				waybillId = record.get('superId'),
				serialNo = record.get('serialNo');
			//将移除的流水号从一级record的stockList中删除
			var superRec = load.handoverbilladdnew.queryEnRouteWaybillGrid.store.findRecord('id',waybillId,0,false,true,true),
				serialNoHandOveredList = superRec.get('serialNoHandOveredList');
			for(var i = 0;i < serialNoHandOveredList.length;i++){
				var serialNoRec = serialNoHandOveredList[i];
				if(serialNoRec.serialNo === serialNo){
					serialNoHandOveredList.splice(i,1);
					break;
				}
			}
		}
	}
});

// 定义查询库存运单中流水号列表grid
Ext.define('Foss.load.handoverbilladdnew.QueryWaybillSerialNoGrid', {
	extend : 'Ext.grid.Panel',
	columnLines: true,
	frame : true,
	width : 362,
	baseCls : 'handOverBill_queryWayBill_serialNoGap',
	// collapsible : true,
	animCollapse : true,
	viewConfig : {
		getRowClass: function(record, rowIndex, rowParams, store) {
			var waybillNo = record.get('waybillNo'),
				serialNo = record.get('serialNo'),
				isPreHandOver = record.get('isPreHandOver'),
				// 右边 待提交运单的grid（已选运单列表）
				unsubmitedWaybill = load.handoverbilladdnew.unsubmitedWaybillGrid.store.findRecord('waybillNo',waybillNo,0,false,true,true),
				// 定义运单列表 (新增界面 交接单明细)
				unsavedWaybill = load.handoverbilladdnew.handOverBillDetailGrid.store.findRecord('waybillNo',waybillNo,0,false,true,true);
			if(!(isPreHandOver !== 'Y'
					&& (unsubmitedWaybill === null 
						|| unsubmitedWaybill.get('serialNoMap').get(serialNo) === undefined)
					&& (unsavedWaybill === null
						|| unsavedWaybill.get('serialNoMap').get(serialNo) === undefined))){
				return 'disabledrow';
			}
		}
	},
	columns : [ {
		xtype : 'actioncolumn',
		width : 40,
		text : load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.actionColumn')/*'操作'*/,
		align : 'center',
		items : [ {
			tooltip : load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.queryResultTab.moveRight')/*'右移'*/,
			iconCls : 'foss_icons_stl_sendmes',
			handler : function(grid, rowIndex, colIndex) {
				var store = grid.store;
				if(store.getCount() === 1){
					Ext.ux.Toast.msg(load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.alertInfoTitle')/*'提示'*/, '请直接移动整个运单！', 'error', 1500);
					return;
				}
				if(this.iconCls === null){
					return;
				}
				var record = grid.store.getAt(rowIndex);
					waybillNo = record.get('waybillNo'),
					serialNo = record.get('serialNo'),
					sweight = record.get('weight'),
					scubage = record.get('volumn'),
					isSelected = grid.getSelectionModel().isSelected(record),
					// 右边 待提交运单的grid（已选运单列表 查询交接单界面--新增时候）
					unsubmitedWaybill = load.handoverbilladdnew.unsubmitedWaybillGrid.store.findRecord('waybillNo',waybillNo,0,false,true,true),
					//库存 左边Grid（查询交接单界面--新增时候）
					leftGrid = load.handoverbilladdnew.queryWaybillGrid,
					waybill = leftGrid.store.findRecord('waybillNo',waybillNo,0,false,true,true),
					pieces = waybill.get('pieces'),
					weight = waybill.get('weight'),
					cubage = waybill.get('cubage'),
					weightAc = waybill.get('weightAc'),
					cubageAc = waybill.get('cubageAc');
				//如果右侧有该运单
				if(unsubmitedWaybill !== null){
					unsubmitedWaybill.set('pieces',unsubmitedWaybill.get('pieces') + 1);
					unsubmitedWaybill.set('weight',(unsubmitedWaybill.get('weight') + weight/pieces).toFixed(3));
					unsubmitedWaybill.set('cubage',(unsubmitedWaybill.get('cubage') + cubage/pieces).toFixed(3));
					//设置实际重量体积 即 上分拣和快递转换后的体积
					unsubmitedWaybill.set('weightAc',(unsubmitedWaybill.get('weightAc')+sweight).toFixed(3));
					unsubmitedWaybill.set('cubageAc',(unsubmitedWaybill.get('cubageAc')+scubage).toFixed(3));
					
					var tempMap = unsubmitedWaybill.get('serialNoMap');
					tempMap.replace(serialNo,record);
					// 右边 待提交运单的grid（已选运单列表 查询交接单界面--新增时候）
					var plugin = load.handoverbilladdnew.unsubmitedWaybillGrid.getPlugin('handoverbilladdnew_unsubmitedWaybillGrid_serialNoGrid');
					if(!Ext.isEmpty(plugin.getExpendRow())) {
						var item = plugin.getExpendRowBody();
						var innerStore = item.getStore();
						var subWaybillNo = innerStore.getAt(0).get('waybillNo');
						if(waybillNo === subWaybillNo){
							innerStore.loadData(tempMap.getValues());
						}
					}
				}else{
					var rightWaybill = waybill.copy();
					rightWaybill.set('pieces',1);
					rightWaybill.set('weight',(weight/pieces).toFixed(3));
					rightWaybill.set('cubage',(cubage/pieces).toFixed(3));
					rightWaybill.set('weightAc',sweight.toFixed(3));
					rightWaybill.set('cubageAc',scubage.toFixed(3));
					var tempMap = new Ext.util.HashMap()
					tempMap.replace(serialNo,record);
					rightWaybill.set('serialNoMap',tempMap);
					//插入右边的表格
					var rightGrid = load.handoverbilladdnew.unsubmitedWaybillGrid;
						rightStore = rightGrid.store;
					rightStore.insert(rightStore.getCount(),rightWaybill);
				}
				//移除该行
				store.removeAt(rowIndex);
				//如果当前行被选中
				if(isSelected){
					//从map中移除
					var serialNoMap = load.handoverbilladdnew.selectedWaybillMap.get(waybillNo).get('serialNoMap');
					serialNoMap.removeAtKey(serialNo);
					//如果map中没有任何元素
					if(serialNoMap.getCount() ===0){
						load.handoverbilladdnew.selectedWaybillMap.removeAtKey(waybillNo);
						//如果此时一级表中的行被选择，则反选
						var sm = leftGrid.getSelectionModel()
							isSelectedWaybill = sm.isSelected(waybill)
						//反选
						if(isSelectedWaybill){
							sm.deselect(waybill,true)
						}
					}
					load.handoverbilladdnew.updateQueryPageStaInfo();
				}
				//修改左边表格的数据
				waybill.set('pieces',pieces - 1);
				waybill.set('weight',((weight/pieces)*waybill.get('pieces')).toFixed(3));
				waybill.set('cubage',((cubage/pieces)*waybill.get('pieces')).toFixed(3));
				waybill.set('weightAc',(weightAc-sweight).toFixed(3));
				waybill.set('cubageAc',(cubageAc-scubage).toFixed(3));
				//重新统计已选运单
				load.handoverbilladdnew.updateUnsubmitedWaybillStaInfo();
			}
		} ],
		renderer : function(value,metaData,record,rowIndex,colIndex,store,view){
			var waybillNo = record.get('waybillNo'),
				serialNo = record.get('serialNo'),
				isPreHandOver = record.get('isPreHandOver'),
				// 右边 待提交运单的grid（已选运单列表 查询交接单界面--新增时候）
				unsubmitedWaybill = load.handoverbilladdnew.unsubmitedWaybillGrid.store.findRecord('waybillNo',waybillNo,0,false,true,true),
				unsavedWaybill = load.handoverbilladdnew.handOverBillDetailGrid.store.findRecord('waybillNo',waybillNo,0,false,true,true);
			if(isPreHandOver !== 'Y'
					&& (unsubmitedWaybill === null 
						|| unsubmitedWaybill.get('serialNoMap').get(serialNo) === undefined)
					&& (unsavedWaybill === null
						|| unsavedWaybill.get('serialNoMap').get(serialNo) === undefined)){
				this.items[0].iconCls = 'foss_icons_stl_sendmes';
			}else{
				this.items[0].iconCls = null;
			}
		}
	}, {
		dataIndex : 'serialNo',
		align : 'center',
		width : 75,
		xtype : 'ellipsiscolumn',
		text : load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.serialNoColumn')/*'流水号'*/
	}, {
		dataIndex : 'instorageDate',
		align : 'center',
		width : 145,
		text : load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.queryWaybillForm.instorageTime')/*'入库时间'*/,
		renderer : function(value) {
			if(value!=null){
					var date = new Date(value);
					return Ext.Date.format(date, 'Y-m-d H:i:s');									
			}else{
					return null;
	}}
	},{
		dataIndex : 'isPreHandOver',
		align : 'center',
		text : load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.queryResultTab.isPreHandOvered')/*'是否已预配'*/,
		width : '80',
		renderer : function(value){
			if(value == 'Y'){
				return '<input type = "checkbox" disabled = "true" checked>';
			}else{	
				return '<input type = "checkbox" disabled = "true">';
			}
		}
	}],
	bindData : function(record){
		var waybillNo = record.get('waybillNo'),
			grid = this,
			store = grid.store;
		store.loadData(record.get('serialNoStockList'));
		//如果之前被勾选，则勾选上之前选中的流水号
		var rec = load.handoverbilladdnew.selectedWaybillMap.get(waybillNo);
		if(rec !== undefined){
			var selectedSerialNoMap = rec.get('serialNoMap'),
				selectedSerialNo = [];
			store.each(function(rec){
				var serialNo = rec.get('serialNo');
				if(selectedSerialNoMap.get(serialNo) !== undefined){
					selectedSerialNo.push(rec);
				}
			});
			var sm = grid.getSelectionModel();
			sm.select(selectedSerialNo,true,true);
		}
	},
	listeners : {
		'select' : function(rowModel,record,index,eOpts){
			var superGrid = this.superGrid;
			var serialNo = record.get('serialNo'),
				waybillNo = record.get('waybillNo'),
				isPreHandOver = record.get('isPreHandOver');
			var superRecord = superGrid.store.findRecord('waybillNo',waybillNo,0,false,true,true);//获取一级表格中该运单号对应的行记录
			var sm = superGrid.getSelectionModel();
			sm.select(superRecord,true,true);//勾选第一层表格中的行：第二个参数为true，为保留其他已勾选的行，第三个参数为true，表示勾选后不触发select事件
			//勾选后，将运单放入map中
			if(load.handoverbilladdnew.selectedWaybillMap.get(waybillNo) !== undefined){
				superRecord = load.handoverbilladdnew.selectedWaybillMap.get(waybillNo);
			}else{
				load.handoverbilladdnew.selectedWaybillMap.add(waybillNo,superRecord);
			}
			//获取运单record中的勾选的流水号map
			var selectedSerialNoMap = superRecord.get('serialNoMap');
			if(selectedSerialNoMap !== ''){
				selectedSerialNoMap.add(serialNo,record);
			}else{
				selectedSerialNoMap = new Ext.util.HashMap();
				selectedSerialNoMap.add(serialNo,record);
			}
			superRecord.set('serialNoMap',selectedSerialNoMap);
			load.handoverbilladdnew.updateQueryPageStaInfo();
		},
		'deselect' : function(rowModel,record,index,eOpts){
			var superGrid = this.superGrid,
				grid = this,
				selectedList =grid.getSelectionModel().selected,
				serialNo = record.get('serialNo'),
				waybillNo = record.get('waybillNo');
			if(selectedList.length == 0){//如果第二层表格记录全部被反选，则直接将第一层表格反选，并删除第三层map中的流水号记录
				var superGrid = this.superGrid;
				var superRecord = superGrid.store.findRecord('waybillNo',waybillNo,0,false,true,true);
				var sm = superGrid.getSelectionModel();
				sm.deselect(superRecord,true);//反选第一层表格中的行：第二个参数为true，表示反选后不触发deselect事件
				superRecord.set('serialNoMap','');
				load.handoverbilladdnew.selectedWaybillMap.removeAtKey(waybillNo);
			}else{//如果第二层表格记录未全部反选，则从map中的选中行中删除该流水号对应的记录
				var selectedWaybill = load.handoverbilladdnew.selectedWaybillMap.get(waybillNo);
				serialNoMap = selectedWaybill.get('serialNoMap');
				serialNoMap.removeAtKey(serialNo);
			}
			load.handoverbilladdnew.updateQueryPageStaInfo();
		}
	},
	constructor : function(config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.load.handoverbilladdnew.QueryWaybillSerialNoStore');
		me.selModel = Ext.create('Ext.selection.CheckboxModel',{
			showHeaderCheckbox : false,
			mode : 'SIMPLE',
			checkOnly : true,//限制只有点击checkBox后才能选中行
			listeners : {
				'beforeselect' : function(rowModel, record, index, eOpts){
					//如果不可选，则返回false
					if(me.viewConfig.getRowClass(record) === 'disabledrow'){
						return false;
					}
				}
			}
		});
		me.callParent([cfg]);
	}
});

//定义查询在途运单中流水号列表grid
Ext.define('Foss.load.handoverbilladdnew.QueryEnRouteWaybillSerialNoGrid', {
	extend : 'Ext.grid.Panel',
	columnLines: true,
	frame : true,
	width : 185,
	baseCls : 'handOverBill_queryWayBill_serialNoGap',
	// collapsible : true,
	animCollapse : true,
	viewConfig : {
		getRowClass: function(record, rowIndex, rowParams, store) {
			var waybillNo = record.get('waybillNo'),
				serialNo = record.get('serialNo'),
				isPreHandOver = record.get('isPreHandOver'),
				// 右边 待提交运单的grid（已选运单列表 查询交接单界面--新增时候）
				unsubmitedWaybill = load.handoverbilladdnew.unsubmitedWaybillGrid.store.findRecord('waybillNo',waybillNo,0,false,true,true),
				// 定义运单列表 (新增界面 交接单明细)
				unsavedWaybill = load.handoverbilladdnew.handOverBillDetailGrid.store.findRecord('waybillNo',waybillNo,0,false,true,true);
			if(!((unsubmitedWaybill === null 
						|| unsubmitedWaybill.get('serialNoMap').get(serialNo) === undefined)
					&& (unsavedWaybill === null
						|| unsavedWaybill.get('serialNoMap').get(serialNo) === undefined))){
				return 'disabledrow';
			}
		}
	},
	columns : [{
		xtype : 'actioncolumn',
		width : 40,
		text : load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.actionColumn')/*'操作'*/,
		align : 'center',
		items : [ {
			tooltip : load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.queryResultTab.moveRight')/*'右移'*/,
			iconCls : 'foss_icons_stl_sendmes',
			handler : function(grid, rowIndex, colIndex) {
				var store = grid.store;
				if(store.getCount() === 1){
					Ext.ux.Toast.msg(load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.alertInfoTitle')/*'提示'*/, '请直接移动整个运单！', 'error', 1500);
					return;
				}
				if(this.iconCls === null){
					return;
				}
				var record = grid.store.getAt(rowIndex);
					waybillNo = record.get('waybillNo'),
					waybillId = record.get('superId'),
					serialNo = record.get('serialNo'),
					sweight = record.get('weight'),
					scubage = record.get('cubage'),
					isSelected = grid.getSelectionModel().isSelected(record),
					// 右边 待提交运单的grid（已选运单列表 查询交接单界面--新增时候）
					unsubmitedWaybill = load.handoverbilladdnew.unsubmitedWaybillGrid.store.findRecord('waybillNo',waybillNo,0,false,true,true),
					leftGrid = load.handoverbilladdnew.queryEnRouteWaybillGrid,
					waybill = leftGrid.store.findRecord('id',waybillId,0,false,true,true),
					pieces = waybill.get('pieces'),
					weight = waybill.get('weight'),
					cubage = waybill.get('cubage'),
					weightAc = waybill.get('weightAc'),
					cubageAc = waybill.get('cubageAc');
				//如果左边有该运单
				if(unsubmitedWaybill !== null){
					unsubmitedWaybill.set('pieces',unsubmitedWaybill.get('pieces') + 1);
					unsubmitedWaybill.set('weight',(unsubmitedWaybill.get('weight') + weight/pieces).toFixed(3));
					unsubmitedWaybill.set('cubage',(unsubmitedWaybill.get('cubage') + cubage/pieces).toFixed(3));
					unsubmitedWaybill.set('weightAc',(unsubmitedWaybill.get('cubage') + sweight).toFixed(3));
					unsubmitedWaybill.set('cubageAc',(unsubmitedWaybill.get('cubage') + scubage).toFixed(3));
					var tempMap = unsubmitedWaybill.get('serialNoMap');
					tempMap.replace(serialNo,record);
					//如果右侧流水号已展开，则重新加载流水号
					var plugin = load.handoverbilladdnew.unsubmitedWaybillGrid.getPlugin('handoverbilladdnew_unsubmitedWaybillGrid_serialNoGrid');
					if(!Ext.isEmpty(plugin.getExpendRow())) {
						var item = plugin.getExpendRowBody();
						var innerStore = item.getStore();
						var subWaybillNo = innerStore.getAt(0).get('waybillNo');
						if(waybillNo == subWaybillNo){
							innerStore.loadData(tempMap.getValues());
						}
					}
				}else{
					var rightWaybill = waybill.copy();
					rightWaybill.set('pieces',1);
					rightWaybill.set('weight',(weight/pieces).toFixed(3));
					rightWaybill.set('cubage',(cubage/pieces).toFixed(3));
					rightWaybill.set('weightAc',sweight.toFixed(3));
					rightWaybill.set('cubageAc',scubage.toFixed(3));
					var tempMap = new Ext.util.HashMap()
					tempMap.replace(serialNo,record);
					rightWaybill.set('serialNoMap',tempMap);
					//插入右边的表格
					var rightGrid = load.handoverbilladdnew.unsubmitedWaybillGrid;
						rightStore = rightGrid.store;
					rightStore.insert(rightStore.getCount(),rightWaybill);
				}
				//移除该行
				store.removeAt(rowIndex);
				//如果当前行被选中
				if(isSelected){
					//从map中移除
					var serialNoMap = load.handoverbilladdnew.selectedEnRouteWaybillMap.get(waybillId).get('serialNoMap');
					serialNoMap.removeAtKey(serialNo);
					//如果map中没有任何元素
					if(serialNoMap.getCount() ===0){
						load.handoverbilladdnew.selectedWaybillMap.removeAtKey(waybillId);
						//如果此时一级表中的行被选择，则反选
						var sm = leftGrid.getSelectionModel()
							isSelectedWaybill = sm.isSelected(waybill)
						//反选
						if(isSelectedWaybill){
							sm.deselect(waybill,true)
						}
					}
					load.handoverbilladdnew.updateQueryEnRoutePageStaInfo();
				}
				//修改左边表格的数据
				waybill.set('pieces',pieces - 1);
				waybill.set('weight',((weight/pieces)*waybill.get('pieces')).toFixed(3));
				waybill.set('cubage',((cubage/pieces)*waybill.get('pieces')).toFixed(3));
				waybill.set('weightAc',(weightAc-sweight).toFixed(3));
				waybill.set('cubageAc',(cubageAc-scubage).toFixed(3));
			}
		} ],
		renderer : function(value,metaData,record,rowIndex,colIndex,store,view){
			var waybillNo = record.get('waybillNo'),
				serialNo = record.get('serialNo'),
				// 右边 待提交运单的grid（已选运单列表 查询交接单界面--新增时候）
				unsubmitedWaybill = load.handoverbilladdnew.unsubmitedWaybillGrid.store.findRecord('waybillNo',waybillNo,0,false,true,true),
				// 定义运单列表 (新增界面 交接单明细)
				unsavedWaybill = load.handoverbilladdnew.handOverBillDetailGrid.store.findRecord('waybillNo',waybillNo,0,false,true,true);
			if((unsubmitedWaybill === null 
						|| unsubmitedWaybill.get('serialNoMap').get(serialNo) === undefined)
					&& (unsavedWaybill === null
						|| unsavedWaybill.get('serialNoMap').get(serialNo) === undefined)){
				this.items[0].iconCls = 'foss_icons_stl_sendmes';
			}else{
				this.items[0].iconCls = null;
			}
		}
	}, {
		dataIndex : 'serialNo',
		align : 'center',
		width : 120,
		xtype : 'ellipsiscolumn',
		text : load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.serialNoColumn')/*'流水号'*/
	}],
	bindData : function(record){
		var waybillNo = record.get('waybillNo'),
			grid = this,
			waybillId = record.get('id'),
			store = grid.store;
		store.loadData(record.get('serialNoHandOveredList'));
		//如果之前被勾选，则勾选上之前选中的流水号
		var rec = load.handoverbilladdnew.selectedEnRouteWaybillMap.get(waybillId);
		if(rec !== undefined){
			var selectedSerialNoMap = rec.get('serialNoMap'),
				selectedSerialNo = [];
			store.each(function(rec){
				var serialNo = rec.get('serialNo');
				if(selectedSerialNoMap.get(serialNo) !== undefined){
					selectedSerialNo.push(rec);
				}
			});
			var sm = grid.getSelectionModel();
			sm.select(selectedSerialNo,true,true);
		}
	},
	listeners : {
		'select' : function(rowModel,record,index,eOpts){
			var superGrid = this.superGrid,
				superId = record.get('superId'),
				serialNo = record.get('serialNo'),
				waybillNo = record.get('waybillNo');
			var superRecord = superGrid.store.findRecord('id',superId,0,false,true,true);//获取一级表格中该运单号对应的行记录
			var sm = superGrid.getSelectionModel();
			sm.select(superRecord,true,true);//勾选第一层表格中的行：第二个参数为true，为保留其他已勾选的行，第三个参数为true，表示勾选后不触发select事件
			//勾选后，将运单放入map中
			if(load.handoverbilladdnew.selectedEnRouteWaybillMap.get(superId) !== undefined){
				superRecord = load.handoverbilladdnew.selectedEnRouteWaybillMap.get(superId);
			}else{
				load.handoverbilladdnew.selectedEnRouteWaybillMap.add(superId,superRecord);
			}
			//获取运单record中的勾选的流水号map
			var selectedSerialNoMap = superRecord.get('serialNoMap');
			if(selectedSerialNoMap !== ''){
				selectedSerialNoMap.add(serialNo,record);
			}else{
				selectedSerialNoMap = new Ext.util.HashMap();
				selectedSerialNoMap.add(serialNo,record);
			}
			superRecord.set('serialNoMap',selectedSerialNoMap);
			load.handoverbilladdnew.updateQueryEnRoutePageStaInfo();
		},
		'deselect' : function(rowModel,record,index,eOpts){
			var superGrid = this.superGrid;
			var grid = this;
			var superId = record.get('superId');
			var selectedList =grid.getSelectionModel().selected;
			var serialNo = record.get('serialNo');
			
			if(selectedList.length == 0){//如果第二层表格记录全部被反选，则直接将第一层表格反选，并删除第三层map中的流水号记录
				var superGrid = this.superGrid;
				var superRecord = superGrid.store.findRecord('id',superId,0,false,true,true);
				var sm = superGrid.getSelectionModel();
				sm.deselect(superRecord,true);
				superRecord.set('serialNoMap','');
				load.handoverbilladdnew.selectedEnRouteWaybillMap.removeAtKey(superId);
			}else{//如果第二层表格记录未全部反选，则从第二层map中的value中删除该流水号对应的map
				var selectedWaybill = load.handoverbilladdnew.selectedEnRouteWaybillMap.get(superId);
				serialNoMap = selectedWaybill.get('serialNoMap');
				serialNoMap.removeAtKey(serialNo);
			}
			load.handoverbilladdnew.updateQueryEnRoutePageStaInfo();
		}
	},
	constructor : function(config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.load.handoverbilladdnew.QueryEnRouteWaybillSerialNoStore');
		me.selModel = Ext.create('Ext.selection.CheckboxModel',{
			showHeaderCheckbox : false,
			mode : 'SIMPLE',
			checkOnly : true,//限制只有点击checkBox后才能选中行
			listeners : {
				'beforeselect' : function(rowModel, record, index, eOpts){
					//如果不可选，则返回false
					if(me.viewConfig.getRowClass(record) === 'disabledrow'){
						return false;
					}
				}
			}
		});
		me.callParent([cfg]);
	}
});

//定义在途运单查询结果列表
Ext.define('Foss.load.handoverbilladdnew.QueryEnRouteWaybillGrid', {
	extend : 'Ext.grid.Panel',
	height : 603,
	columnLines: true,
	bodyCls : 'autoHeight',
	cls : 'tworowbbargirdpanel',
	viewConfig : {
		loadMask : false
	},
	autoScroll : true,
	emptyText : load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.queryResultTab.emptyText')/*'查询结果为空'*/,
	//collapsible : true,
	animCollapse : true,
	dockedItems: [{
	    xtype : 'toolbar',
	    dock : 'bottom',
	    layout : 'column',
	    defaults: {
			xtype : 'textfield',
			readOnly :true,
			labelWidth : 120
		},
		items: [{
			id : 'Foss_load_handOverBillAddnew_EnRouteQueryPageTotalPieces',
			fieldLabel: load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.queryResultTab.totalPieces')/*'已选总件数'*/,
			columnWidth : 1/3,
			value : 0
			},{
			id : 'Foss_load_handOverBillAddnew_EnRouteQueryPageTotalWeight',
			fieldLabel: '已选总重量(千克)',
			columnWidth : 1/3,
			value : 0
			},{
			id : 'Foss_load_handOverBillAddnew_EnRouteQueryPageTotalCubage',
			fieldLabel: '已选总体积(方)',
			columnWidth : 1/3,
			value : 0
			},{
				id : 'Foss_load_handOverBillAddnew_EnRouteQueryPageUnconvertTotalCubage',
				fieldLabel: '已选未转换总体积(方)',
				labelWidth : 140,
				columnWidth : 1/2,
				value : 0
			},{
			id : 'Foss_load_handOverBillAddnew_EnRouteQueryPageTotalMoney',
			fieldLabel: load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.queryResultTab.totalMoney')/*'已选总金额'*/,
			columnWidth : 1/2,
			value : 0
			}]
	  }],
	  constructor: function(config){
			var me = this,
			cfg = Ext.apply({}, config);
			me.store = Ext.create('Foss.load.handoverbilladdnew.QueryEnRouteWaybillStore');
			me.selModel = Ext.create('Ext.selection.CheckboxModel',{
				mode : 'SIMPLE',
				checkOnly : true//限制只有点击checkBox后才能选中行
			});
			me.bbar = Ext.create('Deppon.StandardPaging',{
			store : me.store,
			pageSize : 30,
			maximumSize : 50,
			plugins : Ext.create('Deppon.ux.PageSizePlugin', {
				sizeList : [['20', 20], ['30', 30], ['40', 40], ['50', 50]]
			})
		});
		load.handoverbilladdnew.enRoutePagingBar = me.bbar;
		me.callParent([cfg]);
	},
	tbar : [{
		xtype : 'button',
		text : load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.queryResultTab.changeQueryWayButton')/*'更改查询方案'*/,
		handler : function(){
			if(load.handoverbilladdnew.AddAssembleDeptWindow == null){
				load.handoverbilladdnew.AddAssembleDeptWindow = Ext.create('Foss.load.handoverbilladdnew.AddAssembleDeptWindow');
			}
			load.handoverbilladdnew.AddAssembleDeptWindow.show();
		}
	}],
	// 定义行展开
	plugins : [ {
		header : true,
		ptype : 'rowexpander',
		// 定义行展开模式（单行与多行），默认是多行展开(值true)
		pluginId : 'handoverbilladdnew_queryEnRouteWaybillGrid_serialNoGrid',
		rowsExpander : false,
		// 行体内容
		rowBodyElement : 'Foss.load.handoverbilladdnew.QueryEnRouteWaybillSerialNoGrid'
	} ],
	rightMove : function(grid,record,rowIndex,type){
		var waybillNo = record.get('waybillNo'),
			waybillId = record.get('id'),
			weightAc = 0,cubageAc = 0,
			// 右边 待提交运单的grid（已选运单列表 查询交接单界面--新增时候）
			unsubmitedGrid = load.handoverbilladdnew.unsubmitedWaybillGrid,
			unStore = unsubmitedGrid.store,
			unRec = unStore.findRecord('waybillNo',waybillNo,0,false,true,true),
			//获取主页面该运单号的record
			unsavedWaybill = load.handoverbilladdnew.handOverBillDetailGrid.store.findRecord('waybillNo',waybillNo,0,false,true,true),
			serialNoList = record.get('serialNoHandOveredList'),
			flag = false;
		//如果已勾选运单map中有此运单，则移除
		if(load.handoverbilladdnew.selectedEnRouteWaybillMap.get(waybillId) !== undefined){
			load.handoverbilladdnew.selectedEnRouteWaybillMap.removeAtKey(waybillId);
			flag = true;
		}
		//右侧grid中是否有该运单
		if(unRec === null){
			//右侧 已经有添加待提交运单  需要判断能不能添加
			if(unStore.getCount()>0){
				    var transPropertyCode=record.get('transPropertyCode');
					//右侧 添加的待提交 运单的第一条数据的运输性质 
					var rightTransPropertyCode=unStore.getAt(0).get('transPropertyCode');
					//判断是否能添加 运单信息到右侧
				    if(!load.handoverbilladdnew.isAdd(transPropertyCode,rightTransPropertyCode,null,false)){
				    return;}
			}
			var serialNoMap = new Ext.util.HashMap();
			for(var i in serialNoList){
				var serialNo = serialNoList[i];
				
				//如果主页面没有的流水号，并且是未预配状态，则可右移
				if(unsavedWaybill === null || unsavedWaybill.get('serialNoMap').get(serialNo.serialNo) === undefined){
					var serialNoRecord = Ext.ModelManager.create(serialNo, 'Foss.load.handoverbilladdnew.serialNoModel');
					weightAc += serialNo.weight;
					cubageAc += serialNo.volumn;
					serialNoMap.replace(serialNo.serialNo,serialNoRecord);
				}
			}
			if(serialNoMap.getCount() !== 0){
				record.set('serialNoMap',serialNoMap);
				//插入右侧grid
				var recCopy = record.copy(),
					pieces = serialNoMap.getCount();
				recCopy.set('weight',((recCopy.get('weight')/recCopy.get('pieces'))*pieces).toFixed(3));
				recCopy.set('cubage',((recCopy.get('cubage')/recCopy.get('pieces'))*pieces).toFixed(3));
				recCopy.set('weightAc',weightAc.toFixed(3));
				recCopy.set('cubageAc',cubageAc.toFixed(3));
				recCopy.set('pieces',pieces);
				unStore.insert(unStore.getCount(),recCopy);
			}
		}else{
			var serialNoMap = unRec.get('serialNoMap');
			for(var i in serialNoList){
				var serialNo = serialNoList[i],
					isPreHandOver = serialNo.isPreHandOver;
				//如果主页面没有的流水号，并且是未预配状态，则可右移
				if(unsavedWaybill === null || unsavedWaybill.get('serialNoMap').get(serialNo.serialNo) === undefined){
					var serialNoRecord = Ext.ModelManager.create(serialNo, 'Foss.load.handoverbilladdnew.serialNoModel');
					weightAc += serialNo.weight;
					cubageAc += serialNo.volumn;
					serialNoMap.replace(serialNo.serialNo,serialNoRecord);
				}
			}
			//更新件数、重量、体积
			var pieces = serialNoMap.getCount();
			unRec.set('weight',((unRec.get('weight')/unRec.get('pieces'))*pieces).toFixed(3));
			unRec.set('cubage',((unRec.get('cubage')/unRec.get('pieces'))*pieces).toFixed(3));
			unRec.set('weightAc',weightAc.toFixed(3));
			unRec.set('cubageAc',cubageAc.toFixed(3));
			unRec.set('pieces',serialNoMap.getCount());
		}
		//如果是单条移除，则逐条移除
		if(type === 'ONE'){
			//移除该运单
			grid.store.removeAt(rowIndex);
			//如果勾选map有变动，则重新统计信息
			if(flag){
				load.handoverbilladdnew.updateQueryEnRoutePageStaInfo();
			}
		}
		//重新统计已选运单
		load.handoverbilladdnew.updateUnsubmitedWaybillStaInfo();
		return flag;
	},
	columns : [{
		xtype : 'actioncolumn',
		width : 40,
		text : load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.actionColumn')/*'操作'*/,
		align : 'center',
		items : [ {
			tooltip : load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.queryResultTab.moveRight')/*'右移'*/,
			iconCls : 'foss_icons_stl_sendmes',
			handler : function(grid, rowIndex, colIndex) {
				var record = grid.store.getAt(rowIndex);
				load.handoverbilladdnew.queryEnRouteWaybillGrid.rightMove(grid,record,rowIndex,'ONE');
			}
		} ]
	}, {
		dataIndex : 'waybillNo',
		align : 'center',
		width : 80,
		xtype : 'ellipsiscolumn',
		text : load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.waybillNoColumn')/*'运单号'*/
	},{
		dataIndex : 'transProperty',
		align : 'center',
		width : 95,
		text : load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.transPropertyColumn')/*'运输性质'*/
	}, {
		dataIndex : 'transPropertyCode',
		align : 'center',
		hidden: true,
		width : 95,
		text : '运输性质编码'/*'运输性质'*/
	},{
		dataIndex : 'handOverBillNo',
		align : 'center',
		width : 80,
		xtype : 'ellipsiscolumn',
		text : '交接单号'
	},{
		dataIndex : 'goodsName',
		align : 'center',
		width : 60,
		xtype : 'ellipsiscolumn',
		text : load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.goodsNameColumn')/*'货物名称'*/
	}, {
		dataIndex : 'packing',
		align : 'center',
		width : 60,
		xtype : 'ellipsiscolumn',
		text : load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.packingColumn')/*'包装'*/
	}, {
		dataIndex : 'cubage',
		align : 'center',
		width : 60,
		xtype : 'ellipsiscolumn',
		text : load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.volume')/*'体积'*/
	}, {
		dataIndex : 'weight',
		align : 'center',
		width : 60,
		xtype : 'ellipsiscolumn',
		text : load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.weight')/*'重量'*/
	}, {
		dataIndex : 'pieces',
		align : 'center',
		width : 60,
		xtype : 'ellipsiscolumn',
		text : load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.piecees')/*'件数'*/
	}, {
		dataIndex : 'planArriveTime',
		align : 'center',
		width : 150,
		xtype : 'ellipsiscolumn',
		text : '预计到达时间',
		renderer : function(value) {
			if(value!=null){
					var date = new Date(value);
					return Ext.Date.format(date, 'Y-m-d H:i:s');									
			}else{
					return null;
	}}
	}, {
		dataIndex : 'arriveDept',
		align : 'center',
		width : 150,
		xtype : 'ellipsiscolumn',
		text : load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.basicInfoForm.arriveDeptLabel')/*'到达部门'*/
	}, {
		dataIndex : 'insuranceValue',
		align : 'center',
		width : 60,
		xtype : 'ellipsiscolumn',
		text : load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.insuranceValue')/*'保险价值'*/
	}, {
		dataIndex : 'waybillDate',
		align : 'center',
		width : 90,
		text : '开单日期',
		renderer : function(value) {
			if(value!=null){
					var date = new Date(value);
					return Ext.Date.format(date, 'Y-m-d');									
			}else{
					return null;
	}}
	}, {
		dataIndex : 'waybillPieces',
		align : 'center',
		width : 60,
		xtype : 'ellipsiscolumn',
		text : load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.waybillPieces')/*'开单件数'*/
	}, {
		dataIndex : 'isPreciousGoods',
		align : 'center',
		width : 80,
		text : load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.isPreciousGoods')/*'是否贵重物品'*/,
		renderer : function(value) {
			if(value == 'Y'){
				return '是';
			}else{
				return '否';
			}
		}
	}, {
		dataIndex : 'waybillNote',
		align : 'center',
		width : 200,
		xtype : 'ellipsiscolumn',
		text : load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.waybillNoteColumn')/*'运单备注'*/
	} ],
	getRightMenu : function(record,rowIndex){
		var grid = this;
		function rightMoveOne(){
			grid.rightMove(grid,record,rowIndex,'ONE');
		}
		var	rightMenu =	new Ext.menu.Menu({
	        items : [{
                text : load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.queryResultTab.moveRight')/*'右移'*/,
                handler : rightMoveOne
	        }]
	     });
		 return rightMenu;
	},
	listeners : {
		'select' : function(rowModel, record, index, eOpts ){
			var grid = this,
				plugin = grid.getPlugin('handoverbilladdnew_queryEnRouteWaybillGrid_serialNoGrid'),
				waybillNo = record.get('waybillNo'),
				waybillId = record.get('id'),
				// 定义运单列表 (新增界面 交接单明细)
				unsavedWaybill = load.handoverbilladdnew.handOverBillDetailGrid.store.findRecord('waybillNo',waybillNo,0,false,true,true),
				// 右边 待提交运单的grid（已选运单列表 查询交接单界面--新增时候）
				unsubmitedWaybill = load.handoverbilladdnew.unsubmitedWaybillGrid.store.findRecord('waybillNo',waybillNo,0,false,true,true);
			//取出record中的流水号list，做成map，放到第一层map的record中
			var serialNoStockList = record.get('serialNoHandOveredList'),
				serialNoStockMap = new Ext.util.HashMap();
			for(var i in serialNoStockList){
				var serialNo = serialNoStockList[i];
				if((unsavedWaybill != null && unsavedWaybill.get('serialNoMap') !== '' && unsavedWaybill.get('serialNoMap').get(serialNo.serialNo) !== undefined)
					||(unsubmitedWaybill != null && unsubmitedWaybill.get('serialNoMap') !== '' && unsubmitedWaybill.get('serialNoMap').get(serialNo.serialNo) !== undefined)){
					continue;
				}
				var serialNoRecord = Ext.ModelManager.create(serialNoStockList[i], 'Foss.load.handoverbilladdnew.serialNoModel');
				serialNoStockMap.replace(serialNo.serialNo,serialNoRecord);
			}
			if(serialNoStockMap.getCount() !== 0){
				var recCopy = record.copy();
				recCopy.set('serialNoMap',serialNoStockMap);
				//将运单置于已勾选运单map中
				load.handoverbilladdnew.selectedEnRouteWaybillMap.add(waybillId,recCopy);
				load.handoverbilladdnew.updateQueryEnRoutePageStaInfo();
				//如果此时二级表展开，则全选
				if(!Ext.isEmpty(plugin.getExpendRow())) {
					var item = plugin.getExpendRowBody();
					var store = item.getStore();
					var subWaybillId = store.getAt(0).get('superId');
					if(subWaybillId === waybillId){
						item.getSelectionModel().selectAll(true);
					}
				}
			}else{
				//将之反选
				var sm = grid.getSelectionModel();
				sm.deselect(record,true);
			}
		},
	'deselect' : function( rowModel, record, index, eOpts){
		var grid = this,
			waybillId = record.get('id'),
			plugin = grid.getPlugin('handoverbilladdnew_queryEnRouteWaybillGrid_serialNoGrid');
		if(!Ext.isEmpty(plugin.getExpendRow())) {
			var item = plugin.getExpendRowBody();
			var store = item.getStore();
			var superId = store.getAt(0).get('superId');
			if(superId == waybillId){
				item.getSelectionModel().deselectAll(true);
			}
		}	
		if(load.handoverbilladdnew.selectedEnRouteWaybillMap.get(waybillId) != null){
			// 从map中移除和此运单对应的库存信息
			load.handoverbilladdnew.selectedEnRouteWaybillMap.removeAtKey(waybillId);
		}
		record.set('serialNoMap','');
		load.handoverbilladdnew.updateQueryEnRoutePageStaInfo();
	},
	'itemcontextmenu' : function(view,record,item,index,e, eOpts ){
		var menu = this.getRightMenu(record,index);
		e.preventDefault();
       	menu.showAt(e.getXY());
	}
}
});

//增加配载部门Model
Ext.define('Foss.load.handoverbilladdnew.AddAssembleDeptModel', {
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
/**
 * 判断时候 可以往右侧添加 运单
 * @param {} transPropertyCode 左侧当前需要添加数据的运输性质
 * @param {} rightTransPropertyCode 右侧已经添加的运单store 的第一条数据的 运输性质 如果没有为空 null
 * @param {} leftTransPropertyCode 左侧勾选的运单store 的第一条数据的 运输性质
 * @param {}  flage 是否批量添加
 */
load.handoverbilladdnew.isAdd=function(transPropertyCode,rightTransPropertyCode,leftTransPropertyCode,flage){
	if(!Ext.isEmpty(rightTransPropertyCode)){
					/**
					 * DEAP商务专递
					 * 1.如果右边表格第一列为商务专递，则不能再添加其他运输性质的运单
					 * 2.如果添加的运单第一列不为商务专递，则不允许在添加商务专递
					 * （即 商务专递 不能和其他运输性质的  混合添加）
					 */
					if("DEAP"===rightTransPropertyCode){
						if(transPropertyCode!=rightTransPropertyCode){
						 Ext.ux.Toast.msg(load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.alertInfoTitle')/*'提示'*/, '已经添加商务专递货！不可再添加，非商务专递货！', 'error', 1000);
				         return false ;
						}else{
						 return true;
						}
					}else{
					     if("DEAP"===transPropertyCode){
					     Ext.ux.Toast.msg(load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.alertInfoTitle')/*'提示'*/, '已经添加非商务专递货！不可再添加，商务专递货！', 'error', 1000);
				         return false;
					     }else{
					       return true;
					     }
					}
				}else{
					if(flage){
						if("DEAP"===leftTransPropertyCode){
							if(transPropertyCode!="DEAP"){
							 Ext.ux.Toast.msg(load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.alertInfoTitle')/*'提示'*/, '选择的第一条为商务专递货！不可添加，非商务专递货！', 'error', 1000);
					         return false;
							}else{
							 return true;
							}
						}else{
						     if("DEAP"===transPropertyCode){
						     Ext.ux.Toast.msg(load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.alertInfoTitle')/*'提示'*/, '第一条添加的非商务专递货！不可再添加，商务专递货！', 'error', 1000);
					         return false;
						     }else{
						      return true;
						     }
						}
					}
				}
}
//定义增加配载部门store
Ext.define('Foss.load.handoverbilladdnew.AddAssembleDeptStore', {
	extend : 'Ext.data.Store',
	// 绑定一个模型
	model : 'Foss.load.handoverbilladdnew.AddAssembleDeptModel',
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
});

//定义增加配载部门grid
Ext.define('Foss.load.handoverbilladdnew.AddAssembleDeptGrid', {
	extend : 'Ext.grid.Panel',
	columnLines: true,
	title : load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.changeAssembleWayWindow.gridTitle')/*'配载部门列表'*/,
	frame: true,
	height : 270,
	autoScroll : true,
	store : Ext.create('Foss.load.handoverbilladdnew.AddAssembleDeptStore'),
	columns : [ {
		xtype : 'actioncolumn',
		width : 80,
		text : load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.actionColumn')/*'操作'*/,
		align : 'center',
		items : [ {
			tooltip : load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.deleteButtonColumn')/*'删除'*/,
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
		text : load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.changeAssembleWayWindow.assembleDeptColumn')/*'配载部门'*/
	} ]
});

//定义增加配载部门form
Ext.define('Foss.load.handoverbilladdnew.AddAssembleDeptForm', {
	extend : 'Ext.form.Panel',
	title : load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.changeAssembleWayWindow.addAssembleDept')/*'增加配载部门'*/,
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
		fieldLabel : load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.changeAssembleWayWindow.assembleDeptColumn')/*'配载部门'*/,
		name : 'assembleDeptCode',
		xtype : 'dynamicorgcombselector',
		type : 'ORG',
		transferCenter : 'Y',
		columnWidth : 3/4,
		allowBlank : false
	},{
		xtype : 'button',
		text : load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.changeAssembleWayWindow.addButton')/*'添加'*/,
		cls : 'yellow_button',
		columnWidth : 1/4,
		handler : function(){
			if(this.up('form').getForm().isValid()){
				var cmp = this.up('form').getForm().findField('assembleDeptCode'),
				store = cmp.store,
				deptCode = cmp.getValue(),
				deptRecord = store.findRecord('code',deptCode,0,false,true,true),
				deptName = deptRecord.get('name');
				//如果该部门已经被添加，则提示
				var store = load.handoverbilladdnew.addAssembleDeptGrid.store;
				if(store.findRecord('assembleDeptCode',deptCode,0,false,true,true) != null){
					Ext.ux.Toast.msg(load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.alertInfoTitle')/*'提示'*/, '该部门已经被添加！', 'error', 1000);
				}else{
					var deptInfo = {
						'assembleDeptCode' : deptCode,
						'assembleDeptName' : deptName
					};
					var deptInfoRecord = Ext.ModelManager.create(deptInfo,'Foss.load.handoverbilladdnew.AddAssembleDeptModel');
					store.insert(store.getCount(),deptInfoRecord);
					cmp.setValue(null);
					cmp.focus();
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
Ext.define('Foss.load.handoverbilladdnew.AddAssembleDeptWindow', {
	extend : 'Ext.window.Window',
	closeAction : 'hide',
	modal : true,
	width : 400,
	height : 500,
	title : load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.queryResultTab.changeQueryWayButton')/*'更改查询方案'*/,
	addAssembleDeptForm : null,
	getAddAssembleDeptForm : function(){
		if(this.addAssembleDeptForm==null){
			this.addAssembleDeptForm = Ext.create('Foss.load.handoverbilladdnew.AddAssembleDeptForm');
			load.handoverbilladdnew.addAssembleDeptForm = this.addAssembleDeptForm;
		}
		return this.addAssembleDeptForm;
	},
	addAssembleDeptGrid : null,
	getAddAssembleDeptGrid : function(){
		if(this.addAssembleDeptGrid==null){
			this.addAssembleDeptGrid = Ext.create('Foss.load.handoverbilladdnew.AddAssembleDeptGrid');
			load.handoverbilladdnew.addAssembleDeptGrid = this.addAssembleDeptGrid;
		}
		return this.addAssembleDeptGrid;
	},
	constructor : function(config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.items = [me.getAddAssembleDeptForm(),me.getAddAssembleDeptGrid()];
		me.callParent([cfg]);
	},
	buttons : [{
		text : load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.changeAssembleWayWindow.confirmButton')/*'确定'*/,
		cls : 'yellow_button',
		handler : function(){
			this.ownerCt.ownerCt.close();
		}
	}]
});

//按钮panel
Ext.define('Foss.load.handoverbilladdnew.moveButtonPanel', {
    extend:'Ext.panel.Panel',
    height : 600,
    width : 80,
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.defaults ={
				xtype:'button',
				width:60,
				disabled:false,
				height:25,
				margin:'8 0 0 10'
		};
		me.items = [{
			 iconCls : 'deppon_icons_turnrightall',
			 margin : '265 0 0 10',
			 tooltip : load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.moveButtonPanel.moveRightAllToolTip')/*'点击可将左侧全部运单移动到右侧'*/,
		     handler : function() {
		     	var myMask = load.handoverbilladdnew.queryWayBillWindow.getLoadMask('运单右移中，请稍后...');
		     	myMask.show();
		     	var myMask2 = new Ext.LoadMask(load.handoverbilladdnew.queryWayBillWindow, {msg : '运单右移中，请稍后...'});
		    	myMask2.show();
		    	me.rightMoveAll();
		    	myMask.hide();
		    	myMask2.hide();
		     }
		},{
			iconCls : 'deppon_icons_turnright',
			tooltip : load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.moveButtonPanel.moveRightToolTip')/*'点击可将左侧已勾选运单及流水号移动到右侧'*/,
		     handler: function() {
		     	var myMask = load.handoverbilladdnew.queryWayBillWindow.getLoadMask('运单右移中，请稍后...');
		     	myMask.show();
		        me.rightMove();
		        myMask.hide();
		     }
		},{
			iconCls : 'deppon_icons_turnleft',
			tooltip : load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.moveButtonPanel.moveLeftToolTip')/*'点击可将右侧选中运单移动到左侧'*/,
		     handler: function() {
		     	var myMask = load.handoverbilladdnew.queryWayBillWindow.getLoadMask('运单左移中，请稍后...');
		     	myMask.show();
		    	me.leftMove();
		    	myMask.hide();
		     }
		},{
			iconCls : 'deppon_icons_turnleftall',
			tooltip : load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.moveButtonPanel.moveLeftAllToolTip')/*'点击可将右侧全部运单移动到左侧'*/,
		    handler: function() {
		    	var myMask = load.handoverbilladdnew.queryWayBillWindow.getLoadMask('运单左移中，请稍后...');
		     	myMask.show();
		    	me.leftMoveAll();
		    	myMask.hide();
		    }
		}]
		me.callParent([cfg]);
	},
	rightMove : function(){
		var me = this,
			waybillGrid,
			// 右边 待提交运单的grid（已选运单列表 查询交接单界面--新增时候）
			unsubmitedGrid = load.handoverbilladdnew.unsubmitedWaybillGrid,
			unStore = unsubmitedGrid.store;
		//判断当前激活的是库存tab还是在途tab
		if(load.handoverbilladdnew.queryWaybillTabPanel.getActiveTab().title == load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.queryResultTab.tab1Title')/*'库存货物'*/){
			//库存 左边Grid（查询交接单界面--新增时候）
			waybillGrid = load.handoverbilladdnew.queryWaybillGrid,
				waybillMap = load.handoverbilladdnew.selectedWaybillMap;
			if(waybillMap.getCount() === 0){
				Ext.ux.Toast.msg(load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.alertInfoTitle')/*'提示'*/, '左侧表格没有勾选任何库存运单！', 'error', 2000);
				return;
			}
			//遍历勾选的运单
			waybillMap.each(function(waybillNo,waybill,length){
				//获取记录的勾选的流水号
				var serialNoMap = waybill.get('serialNoMap'),
					serialNoStockList = waybill.get('serialNoStockList'),
					//获取左侧表格中的record
					leftWaybillRec = waybillGrid.store.findRecord('waybillNo',waybillNo,0,false,true,true),
					//获取右侧表格中的record
					rightWaybillRec = unsubmitedGrid.store.findRecord('waybillNo',waybillNo,0,false,true,true);
					//右侧 已经有添加待提交运单  需要判断能不能添加
					if(unStore.getCount()>0){
						//左侧 选择添加运单的 运输性质
					var transPropertyCode=leftWaybillRec.data.transPropertyCode;
					//右侧 添加的待提交 运单的第一条数据的运输性质 
					var rightTransPropertyCode=unStore.getAt(0).get('transPropertyCode');
					//判断是否能添加 运单信息到右侧
				    if(!load.handoverbilladdnew.isAdd(transPropertyCode,rightTransPropertyCode,null,false)){
				      return ;
				    }
					}
				//初始化参数
				var moveWeight = 0, moveVolumn = 0;
				//循环统计移动的总重量和总体积
				serialNoMap.each(function(innerSerialNo,serialNoRec){
					moveWeight += serialNoRec.get('weight');
					moveVolumn += serialNoRec.get('volumn');
				});
				//如果选择的运单在左侧store中存在
				if(leftWaybillRec !== null){
					//勾选的流水号是否为全部流水号
					var selectedSerialNoMap = waybill.get('serialNoMap'),
						pieces = leftWaybillRec.get('pieces'),
						movePieces = selectedSerialNoMap.getCount() ;
					if(movePieces === pieces){
						//执行grid的单条右移方法
						var rowIndex = waybillGrid.store.indexOf(leftWaybillRec);
						waybillGrid.rightMove(waybillGrid,leftWaybillRec,rowIndex,'ONE');
						return;
					}else{
						
						//拆分左侧的record
						leftWaybillRec.set('weight',((leftWaybillRec.get('weight')/pieces)*(pieces - movePieces)).toFixed(3));
						leftWaybillRec.set('cubage',((leftWaybillRec.get('cubage')/pieces)*(pieces - movePieces)).toFixed(3));
						leftWaybillRec.set('weightAc',(leftWaybillRec.get('weightAc')-moveWeight).toFixed(3));
						leftWaybillRec.set('cubageAc',(leftWaybillRec.get('cubageAc')-moveVolumn).toFixed(3));
						leftWaybillRec.set('pieces',pieces - movePieces);
						//如果左侧流水号已展开，则重新加载流水号
						var plugin = waybillGrid.getPlugin('handoverbilladdnew_queryWaybillGrid_serialNoGrid');
						if(!Ext.isEmpty(plugin.getExpendRow())) {
							var item = plugin.getExpendRowBody();
							var innerStore = item.getStore();
							var subWaybillNo = innerStore.getAt(0).get('waybillNo');
							if(waybillNo == subWaybillNo){
								//移除勾选的流水号
								var tempSerialNoList = [];
								innerStore.each(function(inRec){
									var inSerialNo = inRec.get('serialNo');
									if(selectedSerialNoMap.containsKey(inSerialNo)){
										tempSerialNoList.push(inRec);
									}
								});
								innerStore.remove(tempSerialNoList);
							}
						}else{
							//从左侧record的流水号list中移除选中的流水号
							selectedSerialNoMap.each(function(innerSerialNo,serialNoRec){
								for(var i = 0;i < serialNoStockList.length;i++){
									var temp = serialNoStockList[i];
									if(temp.serialNo === serialNoRec.get('serialNo')){
										serialNoStockList.splice(i,1);
										break;
									}
								}
							});
						}
					}
					//右侧grid中是否有该运单
					if(rightWaybillRec === null){
						//插入右侧grid
						var recCopy = waybill.copy(),
							pieces = serialNoMap.getCount();
						
						recCopy.set('weight',((recCopy.get('weight')/recCopy.get('pieces'))*pieces).toFixed(3));
						recCopy.set('cubage',((recCopy.get('cubage')/recCopy.get('pieces'))*pieces).toFixed(3));
						recCopy.set('weightAc',moveWeight.toFixed(3));
						recCopy.set('cubageAc',moveVolumn.toFixed(3));
						recCopy.set('pieces',pieces);
						unStore.insert(unStore.getCount(),recCopy);
					}else{
						var rightSerialNoMap = rightWaybillRec.get('serialNoMap');
						
						//将流水号累加
						serialNoMap.each(function(serialNo,serialNoRec,length){
							rightSerialNoMap.replace(serialNo,serialNoRec);
						});
						//更新件数、重量、体积
						var pieces = rightSerialNoMap.getCount();
						rightWaybillRec.set('weight',((rightWaybillRec.get('weight')/rightWaybillRec.get('pieces'))*pieces).toFixed(3));
						rightWaybillRec.set('cubage',((rightWaybillRec.get('cubage')/rightWaybillRec.get('pieces'))*pieces).toFixed(3));
						rightWaybillRec.set('weightAc',moveWeight.toFixed(3));
						rightWaybillRec.set('cubageAc',moveVolumn.toFixed(3));
						rightWaybillRec.set('pieces',rightSerialNoMap.getCount());
					}
					leftWaybillRec.set('serialNoMap','');
					waybillGrid.getSelectionModel().deselect(leftWaybillRec,true);
				}else{
					//右侧grid中是否有该运单
					if(rightWaybillRec === null){
						//插入右侧grid
						var recCopy = waybill.copy(),
							pieces = serialNoMap.getCount();
						recCopy.set('weight',((recCopy.get('weight')/recCopy.get('pieces'))*pieces).toFixed(3));
						recCopy.set('cubage',((recCopy.get('cubage')/recCopy.get('pieces'))*pieces).toFixed(3));
						recCopy.set('weightAc',moveWeight.toFixed(3));
						recCopy.set('cubageAc',moveVolumn.toFixed(3));
						recCopy.set('pieces',pieces);
						unStore.insert(unStore.getCount(),recCopy);
					}else{
						var rightSerialNoMap = rightWaybillRec.get('serialNoMap');
						//将流水号累加
						serialNoMap.each(function(serialNo,serialNoRec,length){
							rightSerialNoMap.replace(serialNo,serialNoRec);
						});
						//更新件数、重量、体积
						var pieces = rightSerialNoMap.getCount();
						rightWaybillRec.set('weight',((rightWaybillRec.get('weight')/rightWaybillRec.get('pieces'))*pieces).toFixed(3));
						rightWaybillRec.set('cubage',((rightWaybillRec.get('cubage')/rightWaybillRec.get('pieces'))*pieces).toFixed(3));
						//初始化
						moveWeight = 0; moveVolumn = 0;
						rightSerialNoMap.each(function(serial,serialNo,length){
							moveWeight += serialNo.get('weight');
							moveVolumn += serialNo.get('volumn');
						});
						rightWaybillRec.set('weightAc',moveWeight.toFixed(3));
						rightWaybillRec.set('cubageAc',moveVolumn.toFixed(3));
						rightWaybillRec.set('pieces',rightSerialNoMap.getCount());
					}
				}
			});
			//清空map
			load.handoverbilladdnew.selectedWaybillMap.clear();
			load.handoverbilladdnew.updateQueryPageStaInfo();
			load.handoverbilladdnew.updateUnsubmitedWaybillStaInfo();
		}else{
			//在途货物 左边Grid（查询交接单界面--新增时候）
			waybillGrid = load.handoverbilladdnew.queryEnRouteWaybillGrid,
				waybillMap = load.handoverbilladdnew.selectedEnRouteWaybillMap;
			if(waybillMap.getCount() === 0){
				Ext.ux.Toast.msg(load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.alertInfoTitle')/*'提示'*/, '左侧表格没有勾选任何在途运单！', 'error', 2000);
				return;
			}
			//遍历勾选的运单
			waybillMap.each(function(waybillId,waybill,length){
				//获取记录的勾选的流水号
				var serialNoMap = waybill.get('serialNoMap'),
					waybillNo = waybill.get('waybillNo'),
					serialNoHandOveredList = waybill.get('serialNoHandOveredList'),
					//获取左侧表格中的record
					leftWaybillRec = waybillGrid.store.findRecord('waybillNo',waybillNo,0,false,true,true),
					//获取右侧表格中的record
					rightWaybillRec = unsubmitedGrid.store.findRecord('id',waybillId,0,false,true,true);
					//右侧 已经有添加待提交运单  需要判断能不能添加
					if(unStore.getCount()>0){
						    var transPropertyCode=leftWaybillRec.data.transPropertyCode;
							//右侧 添加的待提交 运单的第一条数据的运输性质 
							var rightTransPropertyCode=unStore.getAt(0).get('transPropertyCode');
							//判断是否能添加 运单信息到右侧
						    if(load.handoverbilladdnew.isAdd(transPropertyCode,rightTransPropertyCode,null,false)){
						    return;}
					}
				//初始化参数
				var moveWeight = 0, moveVolumn = 0;
				//循环统计移动的总重量和总体积
				serialNoMap.each(function(innerSerialNo,serialNoRec){
					moveWeight += serialNoRec.get('weight');
					moveVolumn += serialNoRec.get('volumn');
				});
				//如果选择的运单在左侧store中存在
				if(leftWaybillRec !== null){
					//勾选的流水号是否为全部流水号
					var selectedSerialNoMap = waybill.get('serialNoMap'),
						pieces = leftWaybillRec.get('pieces'),
						movePieces = selectedSerialNoMap.getCount();
					if(movePieces === pieces){
						//执行grid的单条右移方法
						var rowIndex = waybillGrid.store.indexOf(leftWaybillRec);
						waybillGrid.rightMove(waybillGrid,leftWaybillRec,rowIndex,'ONE');
						return;
					}else{
						//拆分左侧的record
						leftWaybillRec.set('weight',((leftWaybillRec.get('weight')/pieces)*(pieces - movePieces)).toFixed(3));
						leftWaybillRec.set('cubage',((leftWaybillRec.get('cubage')/pieces)*(pieces - movePieces)).toFixed(3));
						leftWaybillRec.set('weightAc',moveWeight.toFixed(3));
						leftWaybillRec.set('cubageAc',moveVolumn.toFixed(3));
						leftWaybillRec.set('pieces',pieces - movePieces);
						//如果左侧流水号已展开，则重新加载流水号
						var plugin = waybillGrid.getPlugin('handoverbilladdnew_queryEnRouteWaybillGrid_serialNoGrid');
						if(!Ext.isEmpty(plugin.getExpendRow())) {
							var item = plugin.getExpendRowBody();
							var innerStore = item.getStore();
							var subWaybillId = innerStore.getAt(0).get('superId');
							if(waybillId == subWaybillId){
								//移除勾选的流水号
								var tempSerialNoList = [];
								innerStore.each(function(inRec){
									var inSerialNo = inRec.get('serialNo');
									if(selectedSerialNoMap.containsKey(inSerialNo)){
										tempSerialNoList.push(inRec);
									}
								});
								innerStore.remove(tempSerialNoList);
							}
						}else{
							//从左侧record的流水号list中移除选中的流水号
							selectedSerialNoMap.each(function(innerSerialNo,serialNoRec){
								for(var i = 0;i < serialNoHandOveredList.length;i++){
									var temp = serialNoHandOveredList[i];
									if(temp.serialNo === serialNoRec.get('serialNo')){
										serialNoHandOveredList.splice(i,1);
										break;
									}
								}
							});
						}
					}
					//右侧grid中是否有该运单
					if(rightWaybillRec === null){
						//插入右侧grid
						var recCopy = waybill.copy(),
							pieces = serialNoMap.getCount();
						recCopy.set('weight',((recCopy.get('weight')/recCopy.get('pieces'))*pieces).toFixed(3));
						recCopy.set('cubage',((recCopy.get('cubage')/recCopy.get('pieces'))*pieces).toFixed(3));
						recCopy.set('weightAc',moveWeight.toFixed(3));
						recCopy.set('cubageAc',moveVolumn.toFixed(3));
						recCopy.set('pieces',pieces);
						unStore.insert(unStore.getCount(),recCopy);
					}else{
						var rightSerialNoMap = rightWaybillRec.get('serialNoMap');
						//将流水号累加
						serialNoMap.each(function(serialNo,serialNoRec,length){
							rightSerialNoMap.replace(serialNo,serialNoRec);
						});
						//更新件数、重量、体积
						var pieces = rightSerialNoMap.getCount();
						rightWaybillRec.set('weight',((rightWaybillRec.get('weight')/rightWaybillRec.get('pieces'))*pieces).toFixed(3));
						rightWaybillRec.set('cubage',((rightWaybillRec.get('cubage')/rightWaybillRec.get('pieces'))*pieces).toFixed(3));
						rightWaybillRec.set('weightAc',moveWeight.toFixed(3));
						rightWaybillRec.set('cubageAc',moveVolumn.toFixed(3));
						rightWaybillRec.set('pieces',rightSerialNoMap.getCount());
					}
					leftWaybillRec.set('serialNoMap','');
					waybillGrid.getSelectionModel().deselect(leftWaybillRec,true);
				}else{
					//右侧grid中是否有该运单
					if(rightWaybillRec === null){
						//插入右侧grid
						var recCopy = waybill.copy(),
							pieces = serialNoMap.getCount();
						recCopy.set('weight',((recCopy.get('weight')/recCopy.get('pieces'))*pieces).toFixed(3));
						recCopy.set('cubage',((recCopy.get('cubage')/recCopy.get('pieces'))*pieces).toFixed(3));
						recCopy.set('weightAc',moveWeight.toFixed(3));
						recCopy.set('cubageAc',moveVolumn.toFixed(3));
						recCopy.set('pieces',pieces);
						unStore.insert(unStore.getCount(),recCopy);
					}else{
						var rightSerialNoMap = rightWaybillRec.get('serialNoMap');
						//将流水号累加
						serialNoMap.each(function(serialNo,serialNoRec,length){
							rightSerialNoMap.replace(serialNo,serialNoRec);
						});
						//更新件数、重量、体积
						var pieces = rightSerialNoMap.getCount();
						rightWaybillRec.set('weight',((rightWaybillRec.get('weight')/rightWaybillRec.get('pieces'))*pieces).toFixed(3));
						rightWaybillRec.set('cubage',((rightWaybillRec.get('cubage')/rightWaybillRec.get('pieces'))*pieces).toFixed(3));
						rightWaybillRec.set('weightAc',moveWeight.toFixed(3));
						rightWaybillRec.set('cubageAc',moveVolumn.toFixed(3));
						rightWaybillRec.set('pieces',rightSerialNoMap.getCount());
					}
				}
			});
			//清空map
			load.handoverbilladdnew.selectedEnRouteWaybillMap.clear();
			load.handoverbilladdnew.updateQueryEnRoutePageStaInfo();
			load.handoverbilladdnew.updateUnsubmitedWaybillStaInfo();
		}
	},
	rightMoveAll : function(){
		var me = this,
			waybillGrid,
			// 右边 待提交运单的grid（已选运单列表 查询交接单界面--新增时候）
			unsubmitedGrid = load.handoverbilladdnew.unsubmitedWaybillGrid,
			unStore = unsubmitedGrid.store;
		//判断当前激活的是库存tab还是在途tab
		if(load.handoverbilladdnew.queryWaybillTabPanel.getActiveTab().title == load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.queryResultTab.tab1Title')/*'库存货物'*/){
			//库存 左边Grid（查询交接单界面--新增时候）
			var waybillGrid = load.handoverbilladdnew.queryWaybillGrid,
				store = waybillGrid.store,
				flag = false;
			if(store.getCount() === 0){
				Ext.ux.Toast.msg(load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.alertInfoTitle')/*'提示'*/, '库存运单不可为空！', 'error', 1000);
				return;
			}
			//遍历库存运单当前页
			var flag = false;
			store.each(function(record){
				//获取运单号
				var waybillNo = record.get('waybillNo');
				//左侧 选择添加运单的 运输性质
				var  transPropertyCode=record.get('transPropertyCode');
				//右侧 已经有添加待提交运单  需要判断能不能添加
				if(unStore.getCount()>0){
					//右侧 添加的待提交 运单的第一条数据的运输性质 
					var rightTransPropertyCode=unStore.getAt(0).get('transPropertyCode');
					//判断是否能添加 运单信息到右侧
				    if(!load.handoverbilladdnew.isAdd(transPropertyCode,rightTransPropertyCode,null,false)){
				    return;}
				}else{//批量添加需要判断左侧数据
					 var leftTransPropertyCode=store.getAt(0).get('transPropertyCode');
				    if(!load.handoverbilladdnew.isAdd(transPropertyCode,null,leftTransPropertyCode,true)){
				    return;}
				}
				//获取行索引
				var rowIndex = store.indexOf(record);
				
				if(waybillGrid.rightMove(waybillGrid,record,rowIndex,'MANY')){
					flag = true;
				};
			});
			store.removeAll();
			//如果移除的运单中有勾选的运单，则重新计算统计信息
			if(flag){
				load.handoverbilladdnew.updateQueryPageStaInfo();
			}
		}else{
			//在途货物 左边Grid（查询交接单界面--新增时候）
			waybillGrid = load.handoverbilladdnew.queryEnRouteWaybillGrid;
			store = waybillGrid.store;
			if(store.getCount() === 0){
				Ext.ux.Toast.msg(load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.alertInfoTitle')/*'提示'*/, '在途运单不可为空！', 'error', 1000);
				return;
			}
			//遍历在途运单当前页
			var flag = false;
			store.each(function(record){
				//获取运单号
				var waybillNo = record.get('waybillNo');
				//左侧 选择添加运单的 运输性质
				var  transPropertyCode=record.get('transPropertyCode');
				//右侧 已经有添加待提交运单  需要判断能不能添加
				if(unStore.getCount()>0){
					//右侧 添加的待提交 运单的第一条数据的运输性质 
					var rightTransPropertyCode=unStore.getAt(0).get('transPropertyCode');
					//判断是否能添加 运单信息到右侧
				    if(!load.handoverbilladdnew.isAdd(transPropertyCode,rightTransPropertyCode,null,false)){
				    return;}
				}else{//批量添加需要判断左侧数据
					 var leftTransPropertyCode=store.getAt(0).get('transPropertyCode');
				    if(!load.handoverbilladdnew.isAdd(transPropertyCode,null,leftTransPropertyCode,true)){
				    return;}
				}
				//获取行索引
				var rowIndex = store.indexOf(record);
				if(waybillGrid.rightMove(waybillGrid,record,rowIndex,'MANY')){
					flag = true;
				};
			});
			store.removeAll();
			//如果移除的运单中有勾选的运单，则重新计算统计信息
			if(flag){
				load.handoverbilladdnew.updateQueryEnRoutePageStaInfo();
			}
		}
	},
	leftMove : function(){
		var me = this,
		   // 右边 待提交运单的grid（已选运单列表 查询交接单界面--新增时候）
			unsubmitedGrid = load.handoverbilladdnew.unsubmitedWaybillGrid,
			unStore = unsubmitedGrid.store,
			selectedWaybillList = unsubmitedGrid.getSelectionModel().getSelection();
		if(selectedWaybillList.length === 0){
			Ext.ux.Toast.msg(load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.alertInfoTitle')/*'提示'*/, '未勾选右侧任何运单！', 'error', 1500);
			return;
		}
		for(var i in selectedWaybillList){
			var waybill = selectedWaybillList[i];
			unsubmitedGrid.leftMove(waybill);
		}
	},
	leftMoveAll : function(){
		var me = this,
			unsubmitedGrid = load.handoverbilladdnew.unsubmitedWaybillGrid,
			unStore = unsubmitedGrid.store;
		if(unStore.getCount() === 0){
			Ext.ux.Toast.msg(load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.alertInfoTitle')/*'提示'*/, '右侧没有任何运单！', 'error', 1500);
			return;
		}
		unStore.each(function(record){
			unsubmitedGrid.leftMove(record,'MANY');
		});
		unStore.removeAll();
		load.handoverbilladdnew.updateUnsubmitedWaybillStaInfo();
	}
});

//定义查询库存运单和在途运单的tabPanel
Ext.define('Foss.load.handoverbilladdnew.QueryWayBillTabPanel', {
	extend : 'Ext.tab.Panel',
	bodyCls : 'autoHeight',
	flex : 1,
    cls: 'innerTabPanel',
    queryWaybillGrid: null,
    //库存 左边Grid（查询交接单界面--新增时候）
	getQueryWaybillGrid: function(){
		if(this.queryWaybillGrid==null){
			this.queryWaybillGrid = Ext.create('Foss.load.handoverbilladdnew.QueryWaybillGrid',{
				title: load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.queryResultTab.tab1Title')/*'库存货物'*/,
		        tabConfig: {
		            tooltip: load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.queryResultTab.tab1ToolTip')/*'查询部门库存货物'*/,
		            width:100
		        }
			});
			load.handoverbilladdnew.queryWaybillGrid = this.queryWaybillGrid;
		}
		return this.queryWaybillGrid;
	},
	//在途货物 左边Grid（查询交接单界面--新增时候）
	queryEnRouteWaybillGrid: null,
	getQueryEnRouteWaybillGrid: function(){
		if(this.queryEnRouteWaybillGrid==null){
			this.queryEnRouteWaybillGrid = Ext.create('Foss.load.handoverbilladdnew.QueryEnRouteWaybillGrid',{
				title: load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.queryResultTab.tab2Title')/*'在途货物'*/,
		        tabConfig: {
		            tooltip: load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.queryResultTab.tab2ToolTip')/*'查询将到达本部的在途货物'*/,
		            width:100
		        }
			});
			load.handoverbilladdnew.queryEnRouteWaybillGrid = this.queryEnRouteWaybillGrid;
		}
		return this.queryEnRouteWaybillGrid;
	},
    constructor : function(config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.items = [me.getQueryWaybillGrid(),me.getQueryEnRouteWaybillGrid()];
		me.callParent([cfg]);
	},
	beHidden : false,
	setBeHidden : function(value){
		this.beHidden = value;
	},
	getBeHidden : function(){
		return this.beHidden;
	},
	listeners : {
		'tabchange' : function(tabPanel,newCard,oldCard,eOpts ){
			if(newCard.title == load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.queryResultTab.tab1Title')/*'库存货物'*/){
				Ext.getCmp('Foss_handoverbilladdnew_QueryForm_InstorageTime_ID').setVisible(true);
			}else{
				Ext.getCmp('Foss_handoverbilladdnew_QueryForm_InstorageTime_ID').setVisible(false);
			}
		},
		'beforetabchange' : function(tabPanel,newCard,oldCard,eOpts){
			if(newCard.title == load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.queryResultTab.tab2Title')/*'在途货物'*/ && !load.handoverbilladdnew.addNewForm.getForm().findField('isPreHandOverBill').getValue()){
				return false;
			}
		},
		'hide' : function(){
			this.setBeHidden(true);
		},
		'show' : function(){
			this.setBeHidden(false);
		}
	}
});

//定义待提交运单的store
Ext.define('Foss.load.handoverbilladdnew.UnSubmitedWaybillStore', {
	extend : 'Ext.data.Store',
	// 绑定一个模型
	model : 'Foss.load.handoverbilladdnew.waybillStockModel',
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
});

//定义待提交流水号的store
Ext.define('Foss.load.handoverbilladdnew.UnSubmitedSerialNoStore', {
	extend : 'Ext.data.Store',
	// 绑定一个模型
	model : 'Foss.load.handoverbilladdnew.serialNoModel',
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
});

//定义待提交运单中流水号列表grid
Ext.define('Foss.load.handoverbilladdnew.unsubmitedWaybillSerialNoGrid', {
	extend : 'Ext.grid.Panel',
	columnLines: true,
	frame : true,
	width : 185,
	bodyCls: 'autoHeight',
	baseCls : 'handOverBill_queryWayBill_serialNoGap',
	// collapsible : true,
	animCollapse : true,
	columns : [{
		xtype : 'actioncolumn',
		width : 40,
		text : load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.actionColumn')/*'操作'*/,
		align : 'center',
		items : [ {
			tooltip : load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.moveButtonPanel.moveLeft')/*'左移'*/,
			iconCls : 'deppon_icons_remove',
			handler : function(grid, rowIndex, colIndex) {
				var store= grid.store,
					record = store.getAt(rowIndex),
					waybillNo = record.get('waybillNo'),
					// 右边 待提交运单的grid（已选运单列表 查询交接单界面--新增时候）
					superRec = load.handoverbilladdnew.unsubmitedWaybillGrid.store.findRecord('waybillNo',waybillNo,0,false,true,true),
					serialNo = record.get('serialNo'),
					waybillId = record.get('superId'),
					handOverBillNo = record.get('handOverBillNo'),
					oneWeight = superRec.get('weight')/superRec.get('pieces'),//
					oneCubage = superRec.get('cubage')/superRec.get('pieces'),
					moveWeight = record.get('weight'),
					moveVolumn = record.get('volumn'),
					isInStorage = record.get('isInStorage');
				if(store.getCount() === 1){
					Ext.ux.Toast.msg(load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.alertInfoTitle')/*'提示'*/, '请左移整条运单！', 'error', 1500);
					return;
				}
				store.removeAt(rowIndex);
				//从左侧表格的流水号map中删除该流水号
				superRec.get('serialNoMap').removeAtKey(serialNo);
				//如果移除的是库存流水号
				if(isInStorage === 1){
					//左侧是否有该运单
					var leftGrid = load.handoverbilladdnew.queryWaybillGrid,
						leftWaybill = leftGrid.store.findRecord('waybillNo',waybillNo,0,false,true,true);
					//如果没有，则新建一条，插入
					if(leftWaybill === null){
						var recCopy = superRec.copy();
						recCopy.set('pieces',1);
						recCopy.set('weight',oneWeight.toFixed(3));
						recCopy.set('cubage',oneCubage.toFixed(3));
						recCopy.set('weightAc',moveWeight.toFixed(3));
						recCopy.set('cubageAc',moveVolumn.toFixed(3));
						var serialNoList = [];
						serialNoList.push(record.data);
						recCopy.set('serialNoStockList',serialNoList);
						leftGrid.store.insert(leftGrid.store.getCount(),recCopy);
					}else{
						//获取左侧运单的流水号list
						var leftSerialNoList = leftWaybill.get('serialNoStockList');
						if(!load.handoverbilladdnew.inArray(leftSerialNoList,serialNo)){
							leftSerialNoList.push(record.data);
							leftWaybill.set('pieces',leftWaybill.get('pieces') + 1);
							leftWaybill.set('weight',(leftWaybill.get('weight') + oneWeight).toFixed(3));//
							leftWaybill.set('cubage',(leftWaybill.get('cubage') + oneCubage).toFixed(3));
							leftWaybill.set('weightAc',(leftWaybill.get('weightAc') + moveWeight).toFixed(3));
							leftWaybill.set('cubageAc',(leftWaybill.get('cubageAc') + moveVolumn).toFixed(3));
						}
						//如果左侧流水号已展开，则重新加载流水号
						var plugin = leftGrid.getPlugin('handoverbilladdnew_queryWaybillGrid_serialNoGrid');
						if(!Ext.isEmpty(plugin.getExpendRow())) {
							var item = plugin.getExpendRowBody();
							var innerStore = item.getStore();
							var subWaybillNo = innerStore.getAt(0).get('waybillNo');
							if(waybillNo == subWaybillNo){
								innerStore.loadData(leftSerialNoList);
							}
						}
					}
				}else{
					//左侧是否有该运单
					//在途货物 左边Grid（查询交接单界面--新增时候）
					var leftGrid = load.handoverbilladdnew.queryEnRouteWaybillGrid,
						leftWaybill = leftGrid.store.findRecord('id',waybillId,0,false,true,true);
					//如果没有，则新建一条，插入
					if(leftWaybill === null){
						var recCopy = superRec.copy();
						recCopy.set('pieces',1);
						recCopy.set('id',waybillId);
						recCopy.set('handOverBillNo',handOverBillNo);
						recCopy.set('weight',oneWeight.toFixed(3));
						recCopy.set('cubage',oneCubage.toFixed(3));
						recCopy.set('weightAc',moveWeight.toFixed(3));
						recCopy.set('cubageAc',moveVolumn.toFixed(3));
						var serialNoList = [];
						serialNoList.push(record.data);
						recCopy.set('serialNoHandOveredList',serialNoList);
						leftGrid.store.insert(leftGrid.store.getCount(),recCopy);
					}else{
						//获取左侧运单的流水号list
						var leftSerialNoList = leftWaybill.get('serialNoHandOveredList');
						if(!load.handoverbilladdnew.inArray(leftSerialNoList,serialNo)){
							leftSerialNoList.push(record.data);
							leftWaybill.set('pieces',leftWaybill.get('pieces') + 1);
							leftWaybill.set('weight',(leftWaybill.get('weight') + oneWeight).toFixed(3));
							leftWaybill.set('cubage',(leftWaybill.get('cubage') + oneCubage).toFixed(3));
							leftWaybill.set('weightAc',(leftWaybill.get('weightAc') + moveWeight).toFixed(3));
							leftWaybill.set('cubageAc',(leftWaybill.get('cubageAc') + moveVolumn).toFixed(3));
						}
						//如果左侧流水号已展开，则重新加载流水号
						var plugin = leftGrid.getPlugin('handoverbilladdnew_queryEnRouteWaybillGrid_serialNoGrid');
						if(!Ext.isEmpty(plugin.getExpendRow())) {
							var item = plugin.getExpendRowBody();
							var innerStore = item.getStore();
							var subWaybillId = innerStore.getAt(0).get('superId');
							if(waybillId == subWaybillId){
								innerStore.loadData(leftSerialNoList);
							}
						}
					}
				}
				superRec.set('pieces',superRec.get('pieces') - 1);
				superRec.set('weight',(superRec.get('weight') - oneWeight).toFixed(3));
				superRec.set('cubage',(superRec.get('cubage') - oneCubage).toFixed(3));
				superRec.set('weightAc',(superRec.get('weightAc') - moveWeight).toFixed(3));
				superRec.set('cubageAc',(superRec.get('cubageAc') - moveVolumn).toFixed(3));
				//重新统计已选运单
				load.handoverbilladdnew.updateUnsubmitedWaybillStaInfo();
			}
		} ]
	}, {
		dataIndex : 'serialNo',
		align : 'center',
		width : 120,
		xtype : 'ellipsiscolumn',
		text : load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.serialNoColumn')/*'流水号'*/
	}],//
	bindData : function(record){
		var grid = this;
		grid.store.loadData(record.get('serialNoMap').getValues());
		console.log(grid.store);
	},
	constructor : function(config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.load.handoverbilladdnew.UnSubmitedSerialNoStore');
		me.callParent([cfg]);
	}
});

//定义待提交运单的grid
Ext.define('Foss.load.handoverbilladdnew.UnSubmitedWaybillGrid', {
	extend : 'Ext.grid.Panel',
	title : load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.leftGrid.gridTitle')/*'已选运单列表'*/,
	cls : 'tworowbbargirdpanel',
	flex : 1,
	frame : true,
	height : 620,
	columnLines: true,
	beHidden : false,
	// 定义行展开
	plugins : [ {
		header: true,
		ptype : 'rowexpander',
		// 定义行展开模式（单行与多行），默认是多行展开(值true)
		pluginId : 'handoverbilladdnew_unsubmitedWaybillGrid_serialNoGrid',
		rowsExpander : false,
		// 行体内容
		rowBodyElement : 'Foss.load.handoverbilladdnew.unsubmitedWaybillSerialNoGrid'
	} ],
	setBeHidden : function(value){
		this.beHidden = value;
	},
	getBeHidden : function(){
		return this.beHidden;
	},
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.load.handoverbilladdnew.UnSubmitedWaybillStore');
		me.selModel = Ext.create('Ext.selection.CheckboxModel',{
			mode : 'SIMPLE',
			checkOnly : true//限制只有点击checkBox后才能选中行
		});
		me.callParent([cfg]);
	},
	dockedItems: [{
	    xtype: 'toolbar',
	    dock: 'bottom',
	    layout : 'column',
	    defaults: {
			xtype : 'textfield',
			readOnly  :true,
			labelWidth : 120
		},
		items: [{
			id : 'Foss_load_handOverBillAddnew_UnsubmitedWaybillTotalPieces',
			fieldLabel: load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.totalPiecesLabel')/*'总件数'*/,
			columnWidth : 1/3,
			value : 0
		},{
			id : 'Foss_load_handOverBillAddnew_UnsubmitedWaybillTotalWeight',
			fieldLabel: '总重量(千克)',
			labelWidth : 120,
			columnWidth : 1/3,
			value : 0
		},{
			id : 'Foss_load_handOverBillAddnew_UnsubmitedWaybillTotalCubage',
			fieldLabel: '总体积(方)',
			columnWidth : 1/3,
			value : 0
		},{
			id : 'Foss_load_handOverBillAddnew_UnsubmitedWaybillUnconvertTotalCubage',
			fieldLabel: '未转换总体积(方)',
			columnWidth : 1/2,
			labelWidth:140,
			value : 0
		},{
			id : 'Foss_load_handOverBillAddnew_UnsubmitedWaybilleTotalMoney',
			fieldLabel: '总金额',
			columnWidth : 1/2,
			value : 0
		}]
  	}],
	listeners : {
		'hide' : function(){
			this.setBeHidden(true);
		},
		'show' : function(){
			this.setBeHidden(false);
		},
		'itemcontextmenu' : function(view,record,item,index,e, eOpts ){
			var menu = this.getRightMenu(record);
			e.preventDefault();
	       	menu.showAt(e.getXY());
		}
	},
	leftMove : function(record,type){
		var grid = this,
			serialNoMap = record.get('serialNoMap'),
			oneWeight = record.get('weight')/record.get('pieces'),//
			oneCubage = record.get('cubage')/record.get('pieces'),
			waybillNo = record.get('waybillNo');
		
		if(type !== 'MANY'){
			grid.store.remove(record);
			//重新统计已选运单
			load.handoverbilladdnew.updateUnsubmitedWaybillStaInfo();//
		}
		//此时左移的record中，流水号可能有库存的，也可能有在途的，而且在途的可能分别隶属于不同的交接单，所以此处要分别进行区分，分别生成不同的record
		var stockSerialNoMap = new Ext.util.HashMap(),//存储库存的流水号，流水号为key，record.data为value
			handOveredSerialNoMap = new Ext.util.HashMap();//waybillId为key，流水号record.data的list为value
		//遍历此处移除的流水号map
		serialNoMap.each(function(serialNo,serialNoRec,length){
			var isInStorage = serialNoRec.get('isInStorage');
			//如果为库存流水号
			if(isInStorage === 1){
				stockSerialNoMap.replace(serialNo,serialNoRec.data);
			}else{
				var waybillId = serialNoRec.get('superId');
				if(handOveredSerialNoMap.containsKey(waybillId)){
					handOveredSerialNoMap.get(waybillId).push(serialNoRec.data);
				}else{
					serialNoList = [];
					serialNoList.push(serialNoRec.data);
					handOveredSerialNoMap.replace(waybillId,serialNoList);
				}
			}
		});
		//分组后对库存流水号和在途流水号分别进行左移
		if(stockSerialNoMap.getCount() !== 0){
			//左侧是否有该运单
			var leftGrid = load.handoverbilladdnew.queryWaybillGrid,
				leftWaybill = leftGrid.store.findRecord('waybillNo',waybillNo,0,false,true,true);
			//如果没有，则新建一条，插入
			if(leftWaybill === null){
				var recCopy = record.copy();
				recCopy.set('pieces',stockSerialNoMap.getCount());
				recCopy.set('weight',(oneWeight*record.get('pieces')).toFixed(3));
				recCopy.set('cubage',(oneCubage*record.get('pieces')).toFixed(3));//
				
				recCopy.set('weightAc',(record.get('weightAc')).toFixed(3));
				recCopy.set('cubageAc',(record.get('cubageAc')).toFixed(3));
				recCopy.set('serialNoStockList',stockSerialNoMap.getValues());
				leftGrid.store.insert(leftGrid.store.getCount(),recCopy);
			}else{
				//获取左侧运单的流水号list
				var leftSerialNoList = leftWaybill.get('serialNoStockList');
				stockSerialNoMap.each(function(serialNo,serialNoData,length){
					if(!load.handoverbilladdnew.inArray(leftSerialNoList,serialNo)){
						leftSerialNoList.push(serialNoData);
						leftWaybill.set('pieces',leftWaybill.get('pieces') + 1);
						leftWaybill.set('weight',(leftWaybill.get('weight') + oneWeight).toFixed(3));
						leftWaybill.set('cubage',(leftWaybill.get('cubage') + oneCubage).toFixed(3));
						leftWaybill.set('weightAc',(leftWaybill.get('weightAc') + serialNoData.weight).toFixed(3));
						leftWaybill.set('cubageAc',(leftWaybill.get('cubageAc') + serialNoData.volumn).toFixed(3));
					}
				});
				//如果左侧流水号已展开，则重新加载流水号
				var plugin = leftGrid.getPlugin('handoverbilladdnew_queryWaybillGrid_serialNoGrid');
				if(!Ext.isEmpty(plugin.getExpendRow())) {
					var item = plugin.getExpendRowBody();
					var innerStore = item.getStore();
					var subWaybillNo = innerStore.getAt(0).get('waybillNo');
					if(waybillNo == subWaybillNo){
						innerStore.loadData(leftSerialNoList);
					}
				}
			}
		}
		//左移在途的流水号
		if(handOveredSerialNoMap.getCount() !== 0){
			//在途货物 左边Grid（查询交接单界面--新增时候）
			var leftGrid = load.handoverbilladdnew.queryEnRouteWaybillGrid;
			handOveredSerialNoMap.each(function(waybillId,serialNoList,length){
				//左侧是否有该运单
				var leftWaybill = leftGrid.store.findRecord('id',waybillId,0,false,true,true),
					pieces = serialNoList.length;
					handOverBillNo = serialNoList[0].handOverBillNo;
				//如果没有，则新建一条，插入
				if(leftWaybill === null){
					var recCopy = record.copy();
					recCopy.set('pieces',pieces);
					recCopy.set('id',waybillId);
					recCopy.set('handOverBillNo',handOverBillNo);
					recCopy.set('weight',(oneWeight*pieces).toFixed(3));
					recCopy.set('cubage',(oneCubage*pieces).toFixed(3));//
					recCopy.set('weightAc',(record.get('weightAc')).toFixed(3));
					recCopy.set('cubageAc',(record.get('cubageAc')).toFixed(3));//
					recCopy.set('serialNoHandOveredList',serialNoList);
					leftGrid.store.insert(leftGrid.store.getCount(),recCopy);
				}else{
					//获取左侧运单的流水号list
					var leftSerialNoList = leftWaybill.get('serialNoHandOveredList');
					for(var i in serialNoList){
						var serialNo = serialNoList[i].serialNo;
						if(!load.handoverbilladdnew.inArray(leftSerialNoList,serialNo)){
							leftSerialNoList.push(serialNoList[i]);
							leftWaybill.set('pieces',leftWaybill.get('pieces') + 1);
							leftWaybill.set('weight',(leftWaybill.get('weight') + oneWeight).toFixed(3));
							leftWaybill.set('cubage',(leftWaybill.get('cubage') + oneCubage).toFixed(3));
							leftWaybill.set('weightAc',(leftWaybill.get('weightAc') + serialNoList[i].weight).toFixed(3));
							leftWaybill.set('cubageAc',(leftWaybill.get('cubageAc') + serialNoList[i].volumn).toFixed(3));
						}
						//如果左侧流水号已展开，则重新加载流水号
						var plugin = leftGrid.getPlugin('handoverbilladdnew_queryEnRouteWaybillGrid_serialNoGrid');
						if(!Ext.isEmpty(plugin.getExpendRow())) {
							var item = plugin.getExpendRowBody();
							var innerStore = item.getStore();
							var subWaybillId = innerStore.getAt(0).get('superId');
							if(waybillId == subWaybillId){
								innerStore.loadData(leftSerialNoList);
							}
						}
					}
				}
			});
		}
	},
	columns : [ {
		xtype : 'actioncolumn',
		width : 40,
		text : load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.actionColumn')/*'操作'*/,
		align : 'center',
		items : [ {
			tooltip : load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.moveButtonPanel.moveLeft')/*'左移'*/,
			iconCls : 'deppon_icons_remove',
			handler : function(grid, rowIndex, colIndex) {
				var record = grid.store.getAt(rowIndex);
				// 右边 待提交运单的grid（已选运单列表 查询交接单界面--新增时候）
				load.handoverbilladdnew.unsubmitedWaybillGrid.leftMove(record);
			}
		} ]
	}, {
		dataIndex : 'waybillNo',
		align : 'center',
		width : 80,
		xtype : 'ellipsiscolumn',
		text : load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.waybillNoColumn')/*'运单号'*/
	}, {
		dataIndex : 'transProperty',
		align : 'center',
		width : 95,
		text : load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.transPropertyColumn')/*'运输性质'*/
	}, {
		dataIndex : 'transPropertyCode',
		align : 'center',
		hidden : true, 
		width : 95,
		text : '运输性质编码'/*'运输性质'*/
	},{
		dataIndex : 'goodsName',
		align : 'center',
		width : 60,
		xtype : 'ellipsiscolumn',
		text : load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.goodsNameColumn')/*'货物名称'*/
	}, {
		dataIndex : 'packing',
		align : 'center',
		width : 60,
		xtype : 'ellipsiscolumn',
		text : load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.packingColumn')/*'包装'*/
	}, {
		dataIndex : 'cubage',
		align : 'center',
		width : 60,
		xtype : 'ellipsiscolumn',
		text : load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.volume')/*'体积'*/
	}, {
		dataIndex : 'weight',
		align : 'center',
		width : 60,
		xtype : 'ellipsiscolumn',
		text : load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.weight')/*'重量'*/
	}, {
		dataIndex : 'pieces',
		align : 'center',
		flex : 1,
		text : load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.piecees')/*'件数'*/
	}, {
		dataIndex : 'arriveDept',
		align : 'center',
		width : 150,
		xtype : 'ellipsiscolumn',
		text : load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.basicInfoForm.arriveDeptLabel')/*'到达部门'*/
	}, {
		dataIndex : 'insuranceValue',
		align : 'center',
		width : 60,
		xtype : 'ellipsiscolumn',
		text : load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.insuranceValue')/*'保险价值'*/
	}, {
		dataIndex : 'waybillDate',
		align : 'center',
		width : 90,
		text : '开单日期',
		renderer : function(value) {
			if(value!=null){
					var date = new Date(value);
					return Ext.Date.format(date, 'Y-m-d');									
			}else{
					return null;
	}}
	}, {
		dataIndex : 'waybillPieces',
		align : 'center',
		width : 60,
		xtype : 'ellipsiscolumn',
		text : load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.waybillPieces')/*'开单件数'*/
	}, {
		dataIndex : 'isPreciousGoods',
		align : 'center',
		width : 80,
		text : load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.isPreciousGoods')/*'是否贵重物品'*/,
		renderer : function(value) {
			if (value == 'Y') {
				return '是';
			} else {
				return '否';
			}
		}
	}, {
		dataIndex : 'waybillNote',
		align : 'center',
		width : 200,
		xtype : 'ellipsiscolumn',
		text : load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.waybillNoteColumn')/*'运单备注'*/
	} ],
	getRightMenu : function(record){
		var grid = this;
		function leftMoveOne(){
			grid.leftMove(record);
		}
		var	rightMenu =	new Ext.menu.Menu({
	        items : [{
                text : load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.moveButtonPanel.moveLeft')/*'左移'*/,
                handler : leftMoveOne
	        }]
	     });
		 return rightMenu;
	}
});
// 右边 待提交运单的grid（已选运单列表 查询交接单界面--新增时候）
load.handoverbilladdnew.unsubmitedWaybillGrid = Ext.create('Foss.load.handoverbilladdnew.UnSubmitedWaybillGrid');

//定义查询窗口中间部分的panel
Ext.define('Foss.load.handoverbilladdnew.QueryWindowCenterPanel', {
	extend : 'Ext.panel.Panel',
	gridTabPanel : null,
	getGridTabPanel : function(){
		if(this.gridTabPanel==null){
			this.gridTabPanel = Ext.create('Foss.load.handoverbilladdnew.QueryWayBillTabPanel');
			load.handoverbilladdnew.queryWaybillTabPanel = this.gridTabPanel;
		}
		return this.gridTabPanel;
	},
	layout : 'hbox',
	constructor : function(config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.items = [me.getGridTabPanel(),
			Ext.create('Ext.panel.Panel',{
				width : 10,
				items : [{
					xtype : 'button',
					border : false,
					margin : '310 0 0 0',
					tooltip : load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.moveButtonPanel.closeAllExpandLeftGridAlert')/*'点击可收起或展开待选运单表格'*/,
					cls : 'flexright',
					width : 10,
					handler : function(){
						var queryTab = me.items.items[0],
							changeButton1 = this,
							buttonPanel = me.items.items[2],
							changeButton2 = me.items.items[3],
							waybillGrid = me.items.items[4];
						//如果待提交运单grid被隐藏，则显示
						if(waybillGrid.beHidden === true){
							buttonPanel.show();
							changeButton2.show();
							waybillGrid.show();
							//切换箭头的css
							changeButton1.removeCls('flexleft');
							changeButton1.addCls('flexright');
							changeButton2.removeCls('flexright');
							changeButton2.addCls('flexleft');
						}else{
							buttonPanel.hide();
							changeButton2.hide();
							waybillGrid.hide();
							//切换箭头的css
							changeButton1.removeCls('flexright');
							changeButton1.addCls('flexleft');
						}
					}
				}]
			}),
			Ext.create('Foss.load.handoverbilladdnew.moveButtonPanel'),
			Ext.create('Ext.panel.Panel',{
				width : 10,
				items : [{
					xtype : 'button',
					border : false,
					cls : 'flexleft',
					tooltip : load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.moveButtonPanel.closeAllExpandRightGridAlert')/*'点击可收起或展开已选运单表格'*/,
					margin : '310 0 0 0',
					width : 10,
					handler : function(){
						var queryTab = me.items.items[0],
							changeButton1 = me.items.items[1],
							buttonPanel = me.items.items[2],
							changeButton2 = this,
							waybillGrid = me.items.items[4];
						//如果查询tab被隐藏，则显示
						if(queryTab.beHidden === true){
							queryTab.show();
							changeButton1.show();
							buttonPanel.show();
							//切换箭头的css
							changeButton1.removeCls('flexleft');
							changeButton1.addCls('flexright');
							changeButton2.removeCls('flexright');
							changeButton2.addCls('flexleft');
						}else{
							queryTab.hide();
							changeButton1.hide();
							buttonPanel.hide();
							//切换箭头的css
							changeButton2.removeCls('flexleft');
							changeButton2.addCls('flexright');
						}
					}
				}]
			}),
			// 右边 待提交运单的grid（已选运单列表 查询交接单界面--新增时候）
			load.handoverbilladdnew.unsubmitedWaybillGrid],
		me.callParent([cfg]);
	}
});
// 定义查询交接运单窗口
Ext.define('Foss.load.handoverbilladdnew.QueryWayBillWindow', {
	extend : 'Ext.window.Window',
	closeAction : 'hide',
	modal : true,
//	maximized : true,
//	width : document.body.clientWidth,
	width : 1260,
	height : 930,
	title : load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.queryWaybillForm.windowTitle')/*'查询交接运单'*/,
	queryWaybillForm: null,
	getQueryWaybillForm: function(){
		if(this.queryWaybillForm==null){
			this.queryWaybillForm = Ext.create('Foss.load.handoverbilladdnew.QueryWaybillForm');
			load.handoverbilladdnew.queryWaybillForm = this.queryWaybillForm;
		}
		return this.queryWaybillForm;
	},
	centerPanel : null,
	getCenterPanel : function(){
		if(this.centerPanel==null){
			this.centerPanel = Ext.create('Foss.load.handoverbilladdnew.QueryWindowCenterPanel');
			load.handoverbilladdnew.queryWindowCenterPanel = this.centerPanel;
		}
		return this.centerPanel;
	},
	getLoadMask : function(msg){
		var me = this,
			myMask = new Ext.LoadMask(me.getCenterPanel(), {msg : msg});
		return myMask;
	},
	constructor : function(config) {
		var me = this,
		cfg = Ext.apply({}, config);
		//me.items = [me.getQueryWaybillForm(),me.getQueryWaybillGrid()];
		me.items = [me.getQueryWaybillForm(),me.getCenterPanel()];
		me.callParent([cfg]);
	},
	listeners : {
		'beforehide' : function(){
			//窗口内的tab切换到第一个
			this.getCenterPanel().getGridTabPanel().setActiveTab(0);
			//处理查询库存运单grid数据
			load.handoverbilladdnew.selectedWaybillMap.clear();
			//库存 左边Grid（查询交接单界面--新增时候）
			if(load.handoverbilladdnew.queryWaybillGrid.store.getCount() != 0){
				load.handoverbilladdnew.queryWaybillGrid.store.removeAll();//清空store
				load.handoverbilladdnew.pagingBar.onLoad();
			}
			//处理查询在途运单grid数据
			load.handoverbilladdnew.selectedEnRouteWaybillMap.clear();
			//在途货物 左边Grid（查询交接单界面--新增时候）
			if(load.handoverbilladdnew.queryEnRouteWaybillGrid.store.getCount() != 0){
				load.handoverbilladdnew.queryEnRouteWaybillGrid.store.removeAll();//清空store
				load.handoverbilladdnew.enRoutePagingBar.onLoad();
			}
			//清空已选运单列表
			// 右边 待提交运单的grid（已选运单列表）
			load.handoverbilladdnew.unsubmitedWaybillGrid.store.removeAll();
			load.handoverbilladdnew.updateUnsubmitedWaybillStaInfo();
			//清空统计信息
			Ext.getCmp('Foss_load_handOverBillAddnew_QueryPageTotalPieces').setValue(0);
			Ext.getCmp('Foss_load_handOverBillAddnew_QueryPageTotalWeight').setValue(0);
			Ext.getCmp('Foss_load_handOverBillAddnew_QueryPageTotalCubage').setValue(0);
			Ext.getCmp('Foss_load_handOverBillAddnew_QueryPageTotalMoney').setValue(0);
			Ext.getCmp('Foss_load_handOverBillAddnew_QueryPageUnconvertTotalCubage').setValue(0);
			
		}
	},
	buttons : [{
		text : load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.changeAssembleWayWindow.confirmButton')/*'确定'*/,
		cls : 'yellow_button',
		handler : function(){
			//若未勾选任何运单，则提示
			var unGrid = load.handoverbilladdnew.unsubmitedWaybillGrid,
				unStore = unGrid.store;
			if(unStore.getCount() == 0){
				Ext.ux.Toast.msg(load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.alertInfoTitle')/*'提示'*/, '右侧列表没有任何运单！', 'error', 1000);
				return;
			}
			//获取主页面的表格
			var mainGrid = load.handoverbilladdnew.handOverBillDetailGrid;
			//获取主页面的扩展组件
			var plugin = mainGrid.getPlugin('Foss_handoverbilladdnew_mainPage_serialNoGrid_ID'),
				myMask = load.handoverbilladdnew.queryWayBillWindow.getLoadMask('运单添加中，请稍后...');
		     myMask.show();
		     
		     if(!load.handoverbilladdnew.validateHandoverbillDetailsTransProperty(unStore, mainGrid.store.getAt(0))){
				myMask.hide();
				return;
		     }	
		     
			//将提交来的运单record添加至主页
			unStore.each(function(record){
				  var transPropertyCode=record.get('transPropertyCode');
					//右侧 添加的待提交 运单的第一条数据的运输性质 
					var rightTransPropertyCode=unStore.getAt(0).get('transPropertyCode');
					if( //判断是否能添加 运单信息到右侧
				      !load.handoverbilladdnew.isAdd(transPropertyCode,rightTransPropertyCode,null,false)){
						myMask.hide();
					    return;
					}
					//判断能否添加到主页面
					if(mainGrid.store.getCount()>0){
						var leftTransPropertyCode=mainGrid.store.getAt(0).get('transPropertyCode');
						if(!load.handoverbilladdnew.isAdd(transPropertyCode,null,leftTransPropertyCode,true)){
						  myMask.hide();
					     return;
						}
					}
				var serialNoMap = record.get('serialNoMap'),
					waybillNo = record.get('waybillNo');
				//从主页获取该运单号记录
				var mainRecord = mainGrid.store.findRecord('waybillNo',waybillNo,0,false,true,true);
				if(mainRecord !== null){//若该运单曾经被提交过，则将该运单的流水号累加
					//获取原来的流水号map
					var oldSerialNoMap = mainRecord.get('serialNoMap');
					serialNoMap.each(function(key,value,length){
						oldSerialNoMap.replace(key,value);
					});
					//更新主页列表中的件数等信息
					mainRecord.set('pieces',oldSerialNoMap.getCount());
					mainRecord.set('cubage',((record.get('cubage')/record.get('pieces'))*mainRecord.get('pieces')).toFixed(3));
					mainRecord.set('weight',((record.get('weight')/record.get('pieces'))*mainRecord.get('pieces')).toFixed(3));
					var weightAc = 0, cubageAc = 0;
					
					oldSerialNoMap.each(function(serialNo,serial,length){
						weightAc += serial.get('weight');
						cubageAc += serial.get('volumn');
					});
					mainRecord.set('cubageAc',cubageAc.toFixed(3));
					mainRecord.set('weightAc',weightAc.toFixed(3));
					//若主页列表该运单记录的二级表格打开，则刷新二级表格
					if(!Ext.isEmpty(plugin.getExpendRow())) {
						var pluginGrid = plugin.getExpendRowBody();
						var innerStore = pluginGrid.getStore();
						var subWaybillNo = innerStore.getAt(0).get('waybillNo');
						if(subWaybillNo == waybillNo){
							innerStore.loadData(oldSerialNoMap.getValues());
						}
					}
				}else{
					var newRecord =  record.copy();
					newRecord.set('cubageAc',newRecord.get('cubageAc').toFixed(3));
					newRecord.set('weightAc',newRecord.get('weightAc').toFixed(3));
					//将该运单插入主页面
					mainGrid.store.insert(mainGrid.store.getCount(),newRecord);
				}
			});
			myMask.hide();
			this.ownerCt.ownerCt.close();
		}
	}, {
		text : load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.leftGrid.concelButton')/*'取消'*/,
		handler : function(){
			this.ownerCt.ownerCt.close();
		}
	} ]
});

//定义控件的弹出框方法
load.handoverbilladdnew.handOverBillAlert = function(cmp,icon){
	Ext.MessageBox.show({
        title: load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.alertInfoTitle')/*'提示'*/,
        msg: '请输入' + cmp.fieldLabel,
        buttons: Ext.MessageBox.OK,
        animateTarget: cmp,
        icon: icon,
        fn : function(){
 		   cmp.focus(false,100);
 	   }
    });
}

// 定义运单列表 (新增界面 交接单明细)
load.handoverbilladdnew.handOverBillDetailGrid = Ext.create('Foss.load.handoverbilladdnew.HandOverBillDetailGrid');

Ext.onReady(function() {
	// Ext.QuickTips.init();
	//定义变量，记录交接单是否已被保存
	load.handoverbilladdnew.isSaved = 'N';
	// 定义基本信息表单
	var addNewForm = load.handoverbilladdnew.addNewForm = Ext.create('Foss.load.handoverbilladdnew.AddNewForm');
	//设置转货类型
	addNewForm.getForm().findField('tranGoodsType').setValue(
						load.handoverbilladdnew.getTranGoodsStore(1).findRecord(
								'key','TRANSITGOODS',0,false,false,false));
	//如果当前登录部门为营业部，则交接类型只能为“短配交接单”BUG-5383
	var handOverTypeCmp = addNewForm.getForm().findField('handOverType'),
		vehicleNoCmp = addNewForm.getForm().findField('vehicleNo');
	
	if(load.handoverbilladdnew.beSalesDept == 'Y'){
		handOverTypeCmp.setReadOnly(true); 
		//默认为短配
		handOverTypeCmp.setValue('SHORT_DISTANCE_HANDOVER');	
		//只能使用营业部关联顶级车队的公司车辆和所有外请车，登录部门为外场则无此限制
//		vehicleNoCmp.store.removeAll();
//		vehicleNoCmp.store.addListener('beforeload', function(store, operation, eOpts) {
//			var searchParams = operation.params;
//			if (Ext.isEmpty(searchParams)) {
//				searchParams = {};
//				Ext.apply(operation, {
//					params : searchParams
//				});
//			}
//			searchParams['truckVo.truck.topFleetOrgCode'] = load.handoverbilladdnew.superOrgCode;
//		});
	}
	
	function updateHandOverTime(){
		addNewForm.getForm().findField('handOverTime').setValue(load.handoverbilladdnew.handOverBillGetDateTime());
	}
	var intervalControl = window.setInterval(updateHandOverTime,1000);
	load.handoverbilladdnew.queryWayBillWindow = Ext.create('Foss.load.handoverbilladdnew.QueryWayBillWindow');
	Ext.create('Ext.panel.Panel', {
		id : 'T_load-handoverbilladdnewindex_content',
		cls : "panelContentNToolbar",
		bodyCls : 'panelContent-body',
		layout : 'auto',//
		items : [ addNewForm, load.handoverbilladdnew.handOverBillDetailGrid,{// 定义运单列表 (新增界面 交接单明细)
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
				id : 'Foss_load_handoverbilladdnew_mainPage_saveButton_ID',
				text : load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.saveButtonText')/*'保存'*/,
				handler : function() {
					var form = addNewForm.getForm();
					var type = form.getValues().handOverType;
					if(type == 'SALES_DEPARTMENT_HANDOVER' || form.isValid()){
						//若未添加任何运单，则无法保存
						// 定义运单列表 (新增界面 交接单明细)
						if(load.handoverbilladdnew.handOverBillDetailGrid.store.getCount() == 0){
							Ext.Msg.show({
							     title : load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.alertInfoTitle')/*'提示'*/,
							     msg : '未添加任何运单',
							     buttons : Ext.Msg.OK,
							     icon: Ext.Msg.WARNING
							});
							return;
						}
						//通过基本信息form.getValues()获取一级实体
						var handOverBillEntity = form.getValues();
						//如果交接类型为分部交接，则车牌号必须为以德开头的车牌
						 if(handOverBillEntity.handOverType=='SHORT_DISTANCE_HANDOVER'&&load.handoverbilladdnew.beDivision=='Y'){
							 if((handOverBillEntity.vehicleNo).substr(0,1)!='德'){
								Ext.Msg.alert("提示","分部交接单，车牌号必须以“德”开头")
								return;
							 }
							 
						 }
							  //遍历表格store，封装二级实体列表
						     // 定义运单列表 (新增界面 交接单明细)
							  waybillStore = load.handoverbilladdnew.handOverBillDetailGrid.store;
						//由于装车完成时间和交接时间getValue返回的是字符串，故此处转换为date类型，重新设置属性
						var reg=new RegExp('-','g');
						if(!Ext.isEmpty(handOverBillEntity.loadEndTime)){
							handOverBillEntity.loadEndTime = new Date(handOverBillEntity.loadEndTime.replace(reg,'/'));
						}
						handOverBillEntity.handOverTime = new Date(handOverBillEntity.handOverTime.replace(reg,'/'));
						//转换是否预配交接单
						if(handOverBillEntity.isPreHandOverBill == 'on'){
							handOverBillEntity.isPreHandOverBill = 'Y';
						}else{
							handOverBillEntity.isPreHandOverBill = 'N';
						}
						//转换是否代理上门接货，
						if(handOverBillEntity.handOverType == 'PARTIALLINE_HANDOVER'
							|| handOverBillEntity.handOverType == 'LDP_HANDOVER'){
							if(handOverBillEntity.isAgencyVisit == 'on'){
								handOverBillEntity.isAgencyVisit = 'Y';
							}else{
								handOverBillEntity.isAgencyVisit = 'N';
							}
						}else{
							handOverBillEntity.isAgencyVisit = '0';
						}

						//在对象中加入到达部门/外发代理code
						Ext.Object.merge(handOverBillEntity,{
							'arriveDeptCode' : load.handoverbilladdnew.arriveDept
						});

						//到达部门/外发代理名称
						handOverBillEntity.arriveDept = load.handoverbilladdnew.arriveDeptName;
						
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
								var rightTransPropertyCode=waybillStore.getAt(0).data.transPropertyCode;
								//判断是否能添加 运单信息到右侧
						    if(!load.handoverbilladdnew.isAdd(waybill.transPropertyCode,rightTransPropertyCode,null,false)){
						        return;
						    }
							//一旦有整车运单，则只允许有一个运单存在
							if(waybill.isCarLoad == 'Y' && waybillStore.getCount() > 1){
								Ext.Msg.show({
								     title : load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.alertInfoTitle')/*'提示'*/,
								     msg : '保存失败，运单' + waybill.waybillNo + '为整车运单，添加整车运单时只能有一条运单',
								     buttons : Ext.Msg.OK,
								     icon: Ext.Msg.WARNING
								});
								return;
							}
							//如果为外发交接单，则交接件数不能小于开单件数
							if(handOverBillEntity.handOverType == 'PARTIALLINE_HANDOVER'
									&& waybill.pieces < waybill.waybillPieces){
								Ext.Msg.show({
								     title : load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.alertInfoTitle')/*'提示'*/,
								     msg : '保存失败，外发交接时必须全票交接，运单' + waybill.waybillNo + '的交接件数小于开单件数！',
								     buttons : Ext.Msg.OK,
								     icon: Ext.Msg.WARNING
								});
								return;
							}
							//获取流水号
							var serialNoMap = waybill.serialNoMap;
							serialNoMap.each(function(key,value,length){
								serialNoList.push(value.data);
							});
							//运单list 删掉无用的三个属性
							waybillCopy = Ext.clone(waybill);
							delete waybillCopy.serialNoStockList;
							delete waybillCopy.serialNoHandOveredList;
							delete waybillCopy.serialNoMap;
							waybillList.push(waybillCopy);
						}
						
						//构造传到后台的json数据
						var data = {
								'handOverBillVo' : {'newHandOverBillDto': {
									'handOverBillEntity' : handOverBillEntity,
									'waybillStockList' : waybillList,
									'serialNoStockList' : serialNoList
								}
							}
						};
						//mask
						var mainPanel = Ext.getCmp('T_load-handoverbilladdnewindex_content');
						var myMask = new Ext.LoadMask(mainPanel, {msg : "数据提交中，请稍等..."});
		 				myMask.show();
						//保存交接单数据
						Ext.Ajax.request({
							url : load.realPath('saveHandOverBill.action'),
							jsonData : data,
							timeout : 300000,
							success : function(response){
								//获取后台重新生成的交接单号
								var result = Ext.decode(response.responseText);
								var handOverBillNo = result.handOverBillVo.handOverBillNo;
								//提示保存成功，展示交接单号
								load.handoverbilladdnew.prthandOverBillNo = handOverBillNo;
								Ext.Msg.show({
								     title : load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.alertInfoTitle')/*'提示'*/,
								     msg : '保存成功，交接单号为：' + handOverBillNo,
								     buttons : Ext.Msg.OK,
								     icon: Ext.Msg.INFO
								});
								load.handoverbilladdnew.isSaved = 'Y';
								myMask.hide();
								
								//重新设置交接单号
								form.findField('handOverBillNo').setValue(handOverBillNo);
								//设置form所有控件为只读
								var formCmps = form.getFields().getRange(0,form.getFields().getCount());
								for(var i in formCmps){
									formCmps[i].setReadOnly(true);
								}
								//隐藏“查询交接运单”、“保存”按钮
								Ext.getCmp('Foss_load_handoverbilladdnew_mainPage_saveButton_ID').setVisible(false);
								Ext.getCmp('Foss_load_handoverbilladdnew_mainPage_queryButton_ID').setVisible(false);
								//禁用快速添加里的输入框、按钮
								Ext.getCmp('Foss_load_handOverBillAddnew_quickAddWaybillNo_ID').setVisible(false);
								Ext.getCmp('Foss_load_handOverBillAddnew_quickAddButton_ID').setVisible(false);
								
								//隐藏运单grid和流水号grid前的操作列并设置为不可用
								// 定义运单列表 (新增界面 交接单明细)
								load.handoverbilladdnew.handOverBillDetailGrid.columns[1].setVisible(false);
								//获取展开的流水号grid
								var plugin = load.handoverbilladdnew.handOverBillDetailGrid.getPlugin('Foss_handoverbilladdnew_mainPage_serialNoGrid_ID');
								var pluginGrid = plugin.getExpendRowBody();
								if(pluginGrid != null){
									pluginGrid.columns[0].setVisible(false);
								}
							},
							exception : function(response) {
			    				var result = Ext.decode(response.responseText);
			    				top.Ext.MessageBox.alert(load.handoverbilladdnew.i18n('foss.load.handoverbilladdnew.waybillGrid.alertInfoTitle')/*'提示'*/,'保存失败，' + result.message);
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
		renderTo : 'T_load-handoverbilladdnewindex-body'
	});
});