//定义交接单基本信息Model
Ext.define('Foss.load.handoverbillmodify.HandOverBillBaseInfoModel', {
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
		name : 'vehicleNo',
		type : 'string'
	}, {
		name : 'createUserName',
		type : 'string'
	}, {
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

// 定义查询运单结果Model，四个grid共用该Model
Ext.define('Foss.load.handoverbillmodify.waybillStockModel', {
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
Ext.define('Foss.load.handoverbillmodify.serialNoModel', {
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
		name : 'weight',
		type : 'number'
	},{
		name : 'volumn',
		type : 'number'
	}]
});

//运输性质store
Ext.define('Foss.load.handoverbillmodify.ProductStore',{
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

//添加的时候置空三个属性
load.handoverbillmodify.beforeMapAddedRecord = function(map){
	map.addListener('add',function(map, key, value, eOpts){
		delete value.data.serialNoStockList;
		delete value.data.serialNoHandOveredList;
		delete value.data.serialNoMap;
	});
	map.addListener('replace',function(map, key, value, eOpts){
		delete value.data.serialNoStockList;
		delete value.data.serialNoHandOveredList;
		delete value.data.serialNoMap;
	});
}

//定义map，存储修改前的运单
load.handoverbillmodify.oldWaybillMap = new Ext.util.HashMap();
//定义map，记录删除的运单号map
load.handoverbillmodify.deletedWaybillMap = new Ext.util.HashMap();
load.handoverbillmodify.beforeMapAddedRecord(load.handoverbillmodify.deletedWaybillMap);
//定义map，记录增加的运单号map
load.handoverbillmodify.addedWaybillMap = new Ext.util.HashMap();
load.handoverbillmodify.beforeMapAddedRecord(load.handoverbillmodify.addedWaybillMap);
//记录被修改的运单号map
load.handoverbillmodify.updatedWaybillMap = new Ext.util.HashMap();
load.handoverbillmodify.beforeMapAddedRecord(load.handoverbillmodify.updatedWaybillMap);
//定义map，存储修改前的流水号map
load.handoverbillmodify.oldSerialNoMap = new Ext.util.HashMap();
//定义map，记录删除的流水号map
load.handoverbillmodify.deletedSerialNoMap = new Ext.util.HashMap();
//定义map，记录增加的流水号map
load.handoverbillmodify.addedSerialNoMap = new Ext.util.HashMap();
//定义map，记录当前主页面的的流水号map
load.handoverbillmodify.allSerialNoMap = new Ext.util.HashMap();
//定义map，记录弹出窗口中勾选的库存运单map
load.handoverbillmodify.selectedWaybillMap = new Ext.util.HashMap();
//定义map，记录弹出窗口中勾选的在途运单map（key，交接单明细表中该运单ID，value，交接单中该运单记录）
load.handoverbillmodify.selectedEnRouteWaybillMap = new Ext.util.HashMap();

/**
 * 定义Map，用来存储在途运单列表中被勾选的运单号和流水号
 * load.handoverbillmodify.selectedEnRouteWaybillMap key，交接单中运单分录ID，value，交接单中运单对象
 */
load.handoverbillmodify.selectedEnRouteWaybillMap = new Ext.util.HashMap();

//定义交接单明细store
Ext.define('Foss.load.handoverbillmodify.HandOverBillDetailStore', {
	extend : 'Ext.data.Store',
	// 绑定一个模型
	model : 'Foss.load.handoverbillmodify.waybillStockModel',
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
					'handOverBillVo.handOverBillNo' : load.handoverbillmodify.handOverBillNo
				}
			});	
		},
		'load' : function(store, records, successful, eOpts){
			//交接单修改前的运单加载完毕后，存储于load.handoverbillmodify.load.handoverbillmodify.deletedWaybillMap
			for(var i in records){
				var record = records[i];
				var waybillNo = record.get('waybillNo');
				load.handoverbillmodify.oldWaybillMap.replace(waybillNo,record);
			}
		},
		'update' : function(store,record,operation,modifiedFieldNames,eOpts){
			//如果更新了“实际重量”和“实际体积”，则重新统计
			for(var i in modifiedFieldNames){
				if(modifiedFieldNames[i] == 'weightAc' 
					|| modifiedFieldNames[i] == 'cubageAc'
					|| modifiedFieldNames[i] == 'weight' 
					||	modifiedFieldNames[i] == 'cubage' 
					||  modifiedFieldNames[i] == 'pieces'){
					load.handoverbillmodify.updateMainPageStaInfo(store);
				}
			}
			var waybillNo = record.get('waybillNo');
			if(load.handoverbillmodify.oldWaybillMap.get(waybillNo) != null){
				load.handoverbillmodify.updatedWaybillMap.replace(waybillNo,record);
			}
			if(load.handoverbillmodify.addedWaybillMap.get(waybillNo) != null){
				load.handoverbillmodify.addedWaybillMap.replace(waybillNo,record);
			}
		},
		'datachanged' : function(store,record,operation,modifiedFieldNames,eOpts){
			load.handoverbillmodify.updateMainPageStaInfo(store);
		}
	}
});
//转换快递体积转换率
load.handoverbillmodify.rate=load.handoverbillmodify.rate/1000;
//方法用于各处调用，更新主页面grid下统计条数据
load.handoverbillmodify.updateMainPageStaInfo = function (store){
	//更新主页总票数
	var totalCountCmp = Ext.getCmp('Foss_load_handOverBillModify_MainPageTotalCount');
	totalCountCmp.setValue(load.handoverbillmodify.handOverBillDetailGrid.store.getCount());
	//遍历主页store，获得总件数、总体积、总重量、非快递货总体积、快递货总重量、未转换总体积
	var totalPieces = 0,totalVolume = 0,totalWeight = 0,totalCodAmount = 0,normalTotalVolume=0,fastTotalWeight=0,nuConvertTotalVolume=0;
	store.each(function(record){
		totalPieces += record.get('pieces');
		nuConvertTotalVolume += record.get('cubage');
		totalWeight += record.get('weightAc');
		totalCodAmount += record.get('codAmount');
		totalVolume +=  record.get('cubageAc');
//		if(record.get('transPropertyCode')=='PACKAGE'||record.get('transPropertyCode')=='RCP'){
//			//获取快递的总重量
//			fastTotalWeight+=record.get('weightAc');
//		}else{
//			//获取除快递货的总体积
//			normalTotalVolume += record.get('cubageAc');
//		}
	});
	//转换后体积
//	totalVolume=normalTotalVolume+fastTotalWeight*load.handoverbillmodify.rate
	if(totalWeight != 0){
		totalWeight = totalWeight.toFixed(2);//如果不为0，则四舍五入，保留两位小数
	}
	if(totalVolume != 0){
		totalVolume = totalVolume.toFixed(2);
	}
	if(nuConvertTotalVolume != 0){
		nuConvertTotalVolume = nuConvertTotalVolume.toFixed(2);
	}
	//更新主页总件数、总体积、总重量、代收货款总额
	Ext.getCmp('Foss_load_handOverBillModify_MainPageTotalPieces').setValue(totalPieces);
	Ext.getCmp('Foss_load_handOverBillModify_MainPageTotalVolume').setValue(totalVolume);
	Ext.getCmp('Foss_load_handOverBillModify_MainPageTotalWeight').setValue(totalWeight);
	Ext.getCmp('Foss_load_handOverBillModify_MainPageUnconvertTotalVolume').setValue(nuConvertTotalVolume);
	Ext.getCmp('Foss_load_handOverBillModify_MainPageTotalCodAmount').setValue(totalCodAmount);
};

//方法用于各处调用，更新查询库存运单界面下统计条数据
load.handoverbillmodify.updateQueryPageStaInfo = function(){
	//由于一层map勾选导致的二层流水号增加个数不好确定，故遍历二层map，结合一层map，得到各统计信息
	var totalPieces = 0,totalWeight = 0,totalCubage = 0,totalMoney = 0,normalTotalCubage=0,fastTotalWeight=0,nuConvertTotalCubage=0;
	load.handoverbillmodify.selectedWaybillMap.each(function(waybillNo,waybill,length){
		var serialNoMap = waybill.get('serialNoMap');
		totalPieces += serialNoMap.getCount();//得到总件数
		totalMoney += waybill.get('waybillFee');
//		var waybillWeight = (serialNoMap.getCount()/waybill.get('pieces'))*(waybill.get('weight'));//按件数均分，得到该票运单的重量
//		totalWeight += waybillWeight;//得到总重量
		serialNoMap.each(function(serialId,serial,length){
			totalWeight += serial.get('weight');//得到总重量
			totalCubage += serial.get('volumn');//得到总重量
		});
		var waybillCubage = (serialNoMap.getCount()/waybill.get('pieces'))*(waybill.get('cubage'));//按件数均分，得到该票运单的体积
		nuConvertTotalCubage += waybillCubage;//得到总体积
		
		//统计快递货总重量，非快递货总体积
//		if(waybill.get('transPropertyCode')=='PACKAGE'||waybill.get('transPropertyCode')=='RCP'){
//			var fastwaybillWeight = (serialNoMap.getCount()/waybill.get('pieces'))*(waybill.get('weight'));//按件数均分，得到该票运单的重量
//			fastTotalWeight += fastwaybillWeight;//得到总重量
//		}else{
//			var normalwaybillCubage = (serialNoMap.getCount()/waybill.get('pieces'))*(waybill.get('cubage'));//按件数均分，得到该票运单的体积
//			normalTotalCubage += normalwaybillCubage;//得到未转换总体积
//		}
	});
	//计算转换后总体积
//	totalCubage=fastTotalWeight*load.handoverbillmodify.rate +normalTotalCubage;
	if(totalWeight != 0){
		totalWeight = totalWeight.toFixed(2);//如果不为0，则四舍五入，保留两位小数
	}
	if(totalCubage != 0){
		totalCubage = totalCubage.toFixed(2);
	}
	if(nuConvertTotalCubage != 0){
		nuConvertTotalCubage = nuConvertTotalCubage.toFixed(2);
	}
	//设置已勾选总件数
	Ext.getCmp('Foss_load_handOverBillModify_QueryPageTotalPieces').setValue(totalPieces);
	Ext.getCmp('Foss_load_handOverBillModify_QueryPageTotalWeight').setValue(totalWeight);
	Ext.getCmp('Foss_load_handOverBillModify_QueryPageTotalCubage').setValue(totalCubage);
	Ext.getCmp('Foss_load_handOverBillModify_QueryPageTotalMoney').setValue(totalMoney);
	Ext.getCmp('Foss_load_handOverBillModify_QueryPageUnconvertTotalCubage').setValue(nuConvertTotalCubage);
}

//方法用于各处调用，更新查询在途运单界面下统计条数据
load.handoverbillmodify.updateQueryEnRoutePageStaInfo = function(){
	//由于一层map勾选导致的二层流水号增加个数不好确定，故遍历二层map，结合一层map，得到各统计信息
	var totalPieces = 0,totalWeight = 0,totalCubage = 0,totalMoney = 0,normalTotalCubage=0,fastTotalWeight=0,nuConvertTotalCubage=0;
	load.handoverbillmodify.selectedEnRouteWaybillMap.each(function(waybillId,waybill,length){
		var serialNoMap = waybill.get('serialNoMap');
		totalPieces += serialNoMap.getCount();//得到总件数
		totalMoney += waybill.get('waybillFee');
//		var waybillWeight = (serialNoMap.getCount()/waybill.get('pieces'))*(waybill.get('weight'));//按件数均分，得到该票运单的重量
//		totalWeight += waybillWeight;//得到总重量
		serialNoMap.each(function(serialId,serial,length){
			totalWeight += serial.get('weight');//得到总重量
			totalCubage += serial.get('volumn');//得到总重量
		});
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
//	totalCubage=fastTotalWeight*load.handoverbillmodify.rate +normalTotalCubage;
	if(totalWeight != 0){
		totalWeight = totalWeight.toFixed(2);//如果不为0，则四舍五入，保留两位小数
	}
	if(totalCubage != 0){
		totalCubage = totalCubage.toFixed(2);
	}
	if(nuConvertTotalCubage != 0){
		nuConvertTotalCubage = nuConvertTotalCubage.toFixed(2);//如果不为0，则四舍五入，保留两位小数
	}
	
	//设置已勾选总件数
	Ext.getCmp('Foss_load_handOverBillModify_EnRouteQueryPageTotalPieces').setValue(totalPieces);
	Ext.getCmp('Foss_load_handOverBillModify_EnRouteQueryPageTotalWeight').setValue(totalWeight);
	Ext.getCmp('Foss_load_handOverBillModify_EnRouteQueryPageTotalCubage').setValue(totalCubage);
	Ext.getCmp('Foss_load_handOverBillModify_EnRouteQueryPageTotalMoney').setValue(totalMoney);
	Ext.getCmp('Foss_load_handOverBillModify_EnRouteQueryPageUnconvertTotalCubage').setValue(nuConvertTotalCubage);
}

//方法用于各处调用，更新已选运单统计条数据
load.handoverbillmodify.updateUnsubmitedWaybillStaInfo = function(){
	var totalPieces = 0,totalWeight = 0,totalCubage = 0,totalMoney = 0, normalTotalVolume=0,fastTotalWeight=0,nuConvertTotalVolume=0;
	load.handoverbillmodify.unsubmitedWaybillGrid.store.each(function(waybill){
		var serialNoMap = waybill.get('serialNoMap');
		totalPieces += serialNoMap.getCount();//得到总件数
		totalMoney += waybill.get('waybillFee');
		totalWeight += waybill.get('weightAc');//得到总重量
		nuConvertTotalVolume += waybill.get('cubage');//得到总体积
		totalCubage += waybill.get('cubageAc');//得到总体积
//		if(waybill.get('transPropertyCode')=='PACKAGE'||waybill.get('transPropertyCode')=='RCP'){
//			//获取快递的总重量
//			fastTotalWeight+=waybill.get('weight');
//		}else{
//			//获取除快递货的总体积
//			normalTotalVolume += waybill.get('cubage');
//		}
	});
	//计算转换后的体积
//	totalCubage=normalTotalVolume+fastTotalWeight*load.handoverbillmodify.rate;
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
	Ext.getCmp('Foss_load_handOverBillModify_UnsubmitedWaybillTotalPieces').setValue(totalPieces);
	Ext.getCmp('Foss_load_handOverBillModify_UnsubmitedWaybillTotalWeight').setValue(totalWeight);
	Ext.getCmp('Foss_load_handOverBillModify_UnsubmitedWaybillTotalCubage').setValue(totalCubage);
	Ext.getCmp('Foss_load_handOverBillModify_UnsubmitedWaybilleTotalMoney').setValue(totalMoney);
	Ext.getCmp('Foss_load_handOverBillModify_UnsubmitedWaybillUnconvertTotalCubage').setValue(nuConvertTotalVolume);
}

//定义方法，判断流水号数组中是否存在某流水号
load.handoverbillmodify.inArray = function(serialNoList,serialNo){
	for(var i in serialNoList){
		var serialNoRec = serialNoList[i];
		if(serialNoRec.serialNo === serialNo){
			return true;
		}
	}
	return false;
}

// 定义运单明细store
Ext.define('Foss.load.handoverbillmodify.WaybillDetailStore', {
	extend : 'Ext.data.Store',
	// 绑定一个模型
	model : 'Foss.load.handoverbillmodify.serialNoModel',
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
});

// 定义运单明细grid
Ext.define('Foss.load.handoverbillmodify.WaybillDetailGrid', {
	extend : 'Ext.grid.Panel',
	columnLines: true,
	frame: true,
	enableColumnHide : false,//配置该属性可取消grid自定义显示列功能
	baseCls : 'handOverBill_addNew_serialNoGap',
	autoScroll : true,
	width : 180,
	store : Ext.create('Foss.load.handoverbillmodify.WaybillDetailStore'),
	columns : [ {
		xtype : 'actioncolumn',
		width : 60,
		text : load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.waybillGrid.actionColumn')/*'操作'*/,
		align : 'center',
		items : [ {
			tooltip : load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.waybillGrid.deleteButtonColumn')/*'删除'*/,
			iconCls : 'deppon_icons_remove',
			handler : function(grid, rowIndex, colIndex) {
				//整车运单，不可删除运单、流水号
				var isCarLoad = load.handoverbillmodify.basicInfoRecord.get('isCarLoad');
				if(isCarLoad == 'Y'){
					Ext.ux.Toast.msg(load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.waybillGrid.alertInfoTitle')/*'提示'*/, '“整车”交接单无法删除货物，请直接作废交接单！', 'error', 3000);
					return;
				}
				if(grid.store.getCount() == 1){
					Ext.ux.Toast.msg(load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.waybillGrid.alertInfoTitle')/*'提示'*/, load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.waybillGrid.deleteWaybillAlert')/*'请直接删除运单'*/, 'error', 1500);
					return;
				}
				//从unsavedSerialNoMap中删除该流水号
				var record = grid.getStore().getAt(rowIndex),
					waybillNo = record.get('waybillNo'),
					serialNo = record.get('serialNo'),
					weight = record.get('weight'),
					volumn = record.get('volumn');
				//从主页面的流水号map中删除该流水号
				var serialMap = load.handoverbillmodify.allSerialNoMap.get(waybillNo);
				serialMap.removeAtKey(serialNo);
				//如果修改前的流水号map中存在有该流水号，则将该流水号放于load.handoverbillmodify.deletedSerialNoMap
				if(load.handoverbillmodify.oldSerialNoMap.get(waybillNo) != null && load.handoverbillmodify.oldSerialNoMap.get(waybillNo).get(serialNo) != null){
					//若为第一次删除，则新建map，如果不是，则取出map，加入该流水号后重新放入
					if(load.handoverbillmodify.deletedSerialNoMap.get(waybillNo) == null){
						var tempMap = new Ext.util.HashMap();
						tempMap.replace(serialNo,record);
						load.handoverbillmodify.deletedSerialNoMap.replace(waybillNo,tempMap);
					}else{
						var tempMap = load.handoverbillmodify.deletedSerialNoMap.get(waybillNo);
						tempMap.replace(serialNo,record);
						load.handoverbillmodify.deletedSerialNoMap.replace(waybillNo,tempMap);
					}					
				}
				//更新一级表格内的信息
				waybillStore = load.handoverbillmodify.handOverBillDetailGrid.store;
				var waybillRecord = waybillStore.findRecord('waybillNo', waybillNo, 0, false,true,true);
				//删掉map里的该流水号
				waybillRecord.set('weight',(waybillRecord.get('weight')-(waybillRecord.get('weight')/waybillRecord.get('pieces'))).toFixed(3));
				waybillRecord.set('cubage',(waybillRecord.get('cubage')-(waybillRecord.get('cubage')/waybillRecord.get('pieces'))).toFixed(3));
				waybillRecord.set('weightAc',(waybillRecord.get('weightAc')-weight).toFixed(3));//TODO
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
		text : load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.waybillGrid.serialNoColumn')/*'流水号'*/
	} ],
	bindData : function(record){
		//若已保存，则隐藏操作列
		if(load.handoverbillmodify.isSaved == 'Y'){
			this.columns[0].setVisible(false);
		}
		var waybillNo = record.get('waybillNo');
		var serialNoRecords = load.handoverbillmodify.allSerialNoMap.get(waybillNo).getValues();
		this.store.loadData(serialNoRecords);
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
		text : load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.waybillGrid.printButtonText')/*'打印'*/,
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
							Ext.ux.Toast.msg(load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.waybillGrid.alertInfoTitle')/*'提示'*/, 
									"配载单尚未生成, 不能打印!", 
									'error');
						} else {
							if(printTemplate == '配载单') {
								do_printpreview('vehicleassemblebill',{"vehicleAssembleNos": vehicleAssembleNo, 
									"currentDeptName" : currentDeptName, "currentUserName" : currentUserName,
									"currentDeptCode" : currentDeptCode, "currentUserCode" : currentUserCode}, ContextPath.TFR_EXECUTION)
							} else if (printTemplate == '配载单(流水)') {
								do_printpreview('vehicleassemblebillsn',{"vehicleAssembleNos": vehicleAssembleNo, 
									"currentDeptName" : currentDeptName, "currentUserName" : currentUserName,
									"currentDeptCode" : currentDeptCode, "currentUserCode" : currentUserCode}, ContextPath.TFR_EXECUTION)
							}
						}
					}
				});
			} else {
				if (printTemplate == '交接单') {
					do_printpreview('load',{"handOverBillNos": load.handoverbillmodify.handOverBillNo, 
						"currentDeptName" : currentDeptName, "currentUserName" : currentUserName,
						"currentDeptCode" : currentDeptCode, "currentUserCode" : currentUserCode}, ContextPath.TFR_EXECUTION)
				} else if (printTemplate == '交接单(流水)') {
					do_printpreview('loadsn',{"handOverBillNos": load.handoverbillmodify.handOverBillNo, 
						"currentDeptName" : currentDeptName, "currentUserName" : currentUserName,
						"currentDeptCode" : currentDeptCode, "currentUserCode" : currentUserCode}, ContextPath.TFR_EXECUTION)
				} else if (printTemplate == '外发清单') {
					do_printpreview('partialline',{"handOverBillNos": load.handoverbillmodify.handOverBillNo,
						"currentDeptName" : currentDeptName, "currentUserName" : currentUserName,
						"currentDeptCode" : currentDeptCode, "currentUserCode" : currentUserCode}, ContextPath.TFR_EXECUTION)
				} else if (printTemplate == '外发清单(流水)') {
					do_printpreview('partiallinesn',{"handOverBillNos": load.handoverbillmodify.handOverBillNo, 
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

load.handoverbillmodify.validateHandoverbillDetailsTransProperty = function(tbAddWaybills){
	if(Ext.isEmpty(tbAddWaybills)){
		return true;
	}
	
	var handOverBillNo = load.handoverbillmodify.handOverBillNo;
	if(!Ext.isEmpty(handOverBillNo)){
		//用于存放循环中的运单
		var waybill = null;
		//循环待添加运单时，用于存放当前运单的运输性质
		var transPropertyCode = null;
		if("k" === handOverBillNo.charAt(0)){
			for(var i = 0; i < tbAddWaybills.getCount(); i++){
				waybill = tbAddWaybills.getAt(i);
				//非快递都为零担
				transPropertyCode = 
				        (
							( (waybill.transPropertyCode || waybill.get("transPropertyCode")) === "RCP")
							||( (waybill.transPropertyCode || waybill.get("transPropertyCode")) === "PACKAGE" )
						    ||( (waybill.transPropertyCode || waybill.get("transPropertyCode")) === "EPEP") 
				        ) ? "EXPRESS" : "LTL"; 
				if(transPropertyCode === "LTL"){
					Ext.ux.Toast.msg('提示', '交接单为快递交接单，不允许将交接单明细的运单修改为零担货！', 'error');
					return false;
				}
			}
		}else{
			for(var i = 0; i < tbAddWaybills.getCount(); i++){
				waybill = tbAddWaybills.getAt(i);
				//非快递都为零担
				transPropertyCode =  (
						( (waybill.transPropertyCode || waybill.get("transPropertyCode")) === "RCP")
						||( (waybill.transPropertyCode || waybill.get("transPropertyCode")) === "PACKAGE" )
					    ||( (waybill.transPropertyCode || waybill.get("transPropertyCode")) === "EPEP")
				) ? "EXPRESS" : "LTL";  
				if(transPropertyCode === "EXPRESS"){
					Ext.ux.Toast.msg('提示', '交接单为零担交接单，不允许将交接单明细的运单修改为快递货！', 'error');
					return false;
				}
			}
		}
	}
	
	return true;
}

// 定义交接单明细列表
Ext.define('Foss.load.handoverbillmodify.HandOverBillDetailGrid', {
	extend : 'Ext.grid.Panel',
	frame : true,
	title : load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.waybillGrid.gridTitle')/*'交接单明细'*/,
//	bodyCls : 'autoHeight',
	height : 500,
	cls : 'autoHeight',
	enableColumnHide : false,//配置该属性可取消grid自定义显示列功能
	columnLines: true,
	autoScroll : true,
	collapsible : true,
	animCollapse : true,
	store : Ext.create('Foss.load.handoverbillmodify.HandOverBillDetailStore'),
	// 定义行展开
	plugins : [ {
		header: true,
		ptype : 'rowexpander',
		// 定义行展开模式（单行与多行），默认是多行展开(值true)
		rowsExpander : false,
		// 行体内容
		rowBodyElement : 'Foss.load.handoverbillmodify.WaybillDetailGrid',
		pluginId : 'Foss_handOverBillModify_mainPage_serialNoGrid_ID'
	},Ext.create('Ext.grid.plugin.CellEditing', {
            clicksToEdit : 1,
            listeners : {
				'beforeedit' : function(editor,e,eOpts){
					//如果已经保存，则禁止编辑
					if(load.handoverbillmodify.isSaved == 'Y'){
						return false;
					}
				}
			}
        })],
	tbar : [{
			xtype : 'button',
			text : load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.waybillGrid.addWaybillButtonText')/*'添加运单'*/,
			id : 'Foss_load_handOverBillModify_mainPage_queryButton_ID',
			name : 'queryWaybillButton',
			handler : function() {//必须填写交接类型、到达部门/外发代理
				var basicForm = load.handoverbillmodify.basicInfoForm.getForm();
				var handOverType = basicForm.findField('handOverType');
				load.handoverbillmodify.queryWaybillWindow.showAt(0,0);
				//根据交接类型来控制弹出窗口查询条件的显示与隐藏
				var queryForm = load.handoverbillmodify.queryWaybillForm.getForm();
				if(handOverType.value == 'SHORT_DISTANCE_HANDOVER'||handOverType.value == 'DIVISION_HANDOVER'){//短配交接单
					//显示运输类型
					queryForm.findField('transType').setVisible(true);
					//隐藏运输性质
					queryForm.findField('transProperty').setVisible(true);
					load.handoverbillmodify.queryEnRouteWaybillGrid.dockedItems.items[2].setVisible(false);
				}else if(handOverType.value == 'LONG_DISTANCE_HANDOVER'){//集配交接单
					//隐藏运输类型
					queryForm.findField('transType').setVisible(true);
					//显示运输性质
					queryForm.findField('transProperty').setVisible(true);
					load.handoverbillmodify.queryEnRouteWaybillGrid.dockedItems.items[2].setVisible(true);
				}else{//外发交接单
					//隐藏运输类型
					queryForm.findField('transType').setVisible(false);
					//隐藏运输性质
					queryForm.findField('transProperty').setVisible(false);
					load.handoverbillmodify.queryEnRouteWaybillGrid.dockedItems.items[2].setVisible(false);
				}
			}
		},'->',{
			xtype : 'textfield',
			fieldLabel : load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.waybillGrid.waybillNoColumn')/*'运单号'*/,
			emptyText : load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.waybillGrid.quickAddTextfieldToolTipText')/*'输入运单号敲击回车'*/,
			labelWidth : 60,
			vtype : 'waybill',
			id : 'Foss_load_handOverBillModify_quickAddWaybillNo_ID',
			enableKeyEvents : true,
			listeners : {
				'keypress' : function(text,e,eOpts){
					//如果敲击回车键，则触发添加按钮事件
					if(e.getKey() == e.ENTER){
						var addButton = Ext.getCmp('Foss_load_handOverBillModify_quickAddButton_ID');
						addButton.handler();
					}
				}
			}
		},{
			xtype : 'container',
			html : '&nbsp'
		},{
			xtype : 'button',
			id : 'Foss_load_handOverBillModify_quickAddButton_ID',
			text : load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.waybillGrid.quickAddButtonText')/*'快速添加'*/,
			handler : function(){
				/**
			 * 此处快速添加，从后台读取运单库存的流水号添加之，并且从被删除掉的流水号中重新添加
			 */
			//基本信息form
			var basicForm = load.handoverbillmodify.basicInfoForm.getForm();
			var waybillNoCmp = Ext.getCmp('Foss_load_handOverBillModify_quickAddWaybillNo_ID'),
				waybillNo = waybillNoCmp.getValue(),
				handOverType = basicForm.findField('handOverType').getValue();
			if(!Ext.isEmpty(waybillNo) && waybillNoCmp.isValid()){
				//获取参数
				var arriveDeptCode = basicForm.findField('arriveDeptCode').getValue(),
					arriveDeptList = new Array();
				arriveDeptList.push(arriveDeptCode);
				var jsonData = {
					'handOverBillVo' : {
						'queryWaybillForHandOverBillDto' : {
							'arriveDeptList' : arriveDeptList,
							'waybillNo' : waybillNo,
							'isModifyHandOverBill' : 'Y',
							'handOverType' : handOverType
						}
					}
				},
					mainGrid = load.handoverbillmodify.handOverBillDetailGrid,
				//获取主页面的扩展组件
				plugin = mainGrid.getPlugin('Foss_handOverBillModify_mainPage_serialNoGrid_ID');
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
							serialNoList = result.handOverBillVo.serialNoStockList,
							waybillStockRecord =  Ext.ModelManager.create(waybillStock, 'Foss.load.handoverbillmodify.waybillStockModel');
						//若运单库存、流水号库存均为空，且未有该运单流水号删除之记录，则提示
						if(waybillStock == null && serialNoList == null && load.handoverbillmodify.deletedSerialNoMap.get(waybillNo) == null){
							Ext.ux.Toast.msg('提示', 
									'该运单没有可添加的流水号！', 
									'error');
							loadMask.hide();
							return;
						}
						//从主页获取该运单号记录
						var record = mainGrid.store.findRecord('waybillNo',waybillNo,0,false,true,true);
						var isbillStorck=0;
						var unsavedSerialNoMap = new Ext.util.HashMap();
						for(var i in serialNoList){
							var serialNo = serialNoList[i],
								serialNoRecord = Ext.ModelManager.create(serialNo, 'Foss.load.handoverbillmodify.serialNoModel');
								/**      bug:修复交接单中的运单单票入库后再修改交接单添加该运单把保存报错     */
								if(load.handoverbillmodify.oldSerialNoMap.get(waybillNo)!=undefined 
										&& load.handoverbillmodify.oldSerialNoMap.get(waybillNo).get(serialNo.serialNo) != null){
									isbillStorck=isbillStorck+1;
								}
								
								
							unsavedSerialNoMap.add(serialNo.serialNo,serialNoRecord);
						}
						if(serialNoList!=null && isbillStorck!=0 && isbillStorck==serialNoList.length){
							loadMask.hide();
							return;
						}
						//定义待插入的运单record
						var addedWaybill;
						//如果修改前的运单map中无此运单，则视为新增
						if(Ext.isEmpty(load.handoverbillmodify.oldWaybillMap.get(waybillNo))){
							if(Ext.isEmpty(load.handoverbillmodify.addedWaybillMap.get(waybillNo))){
								
								var tbAddWaybills = new Ext.util.MixedCollection();
								tbAddWaybills.add(waybillStock);
								if(!load.handoverbillmodify.validateHandoverbillDetailsTransProperty(tbAddWaybills)){
									loadMask.hide();
									waybillNoCmp.focus(true,true);
									return;
								}
								
								load.handoverbillmodify.addedWaybillMap.add(waybillNo,waybillStockRecord);
								load.handoverbillmodify.addedSerialNoMap.add(waybillNo,unsavedSerialNoMap);
								load.handoverbillmodify.allSerialNoMap.add(waybillNo,unsavedSerialNoMap);
								addedWaybill = waybillStockRecord;
								addedWaybill.set('cubageAc',addedWaybill.get('cubageAc'));//TODO
								addedWaybill.set('weightAc',addedWaybill.get('weightAc'));
							}else{
								var tempSerialNoMap = load.handoverbillmodify.addedSerialNoMap.get(waybillNo);
								
								//把原有流水号加入 现有流水号中
								tempSerialNoMap.each(function(key,value,length){
									
									unsavedSerialNoMap.add(key,value);
								});
								load.handoverbillmodify.addedSerialNoMap.add(waybillNo,unsavedSerialNoMap);
								load.handoverbillmodify.allSerialNoMap.add(waybillNo,unsavedSerialNoMap);
								//更新新增的map中运单的件数、重量体积等；
								addedWaybill = load.handoverbillmodify.addedWaybillMap.get(waybillNo),
									pieces = unsavedSerialNoMap.getCount();
								addedWaybill.set('pieces',pieces);
								addedWaybill.set('cubage',((waybillStock.cubage/waybillStock.pieces)*pieces).toFixed(2));
								addedWaybill.set('weight',((waybillStock.weight/waybillStock.pieces)*pieces).toFixed(2));
								
								//主要是 会在流水号明细表中新增字段，上线前的数据 流水号重量体积为0 不好计算，
								//解决方案：在修改页面初始话查询流水号的sql ，如果查询出来的流水号的重量体积为空（上线前的交接单）则，平均取数，否则取保存的数据
								//这样减少js代码难度 author:hyd
								//计算实际总重量，实体体积.每个流水号总和
								var weight = 0 , volumn = 0;
								unsavedSerialNoMap.each(function(serial,serialNo,length){
									weight += serialNo.get('weight');
									volumn += serialNo.get('volumn');
								});
								addedWaybill.set('cubageAc',volumn);
								addedWaybill.set('weightAc',weight);
							
								
								//重新放入更新后的运单record
								load.handoverbillmodify.addedWaybillMap.add(waybillNo,addedWaybill);
							}
						//如果修改前的map中有此运单，则视为新增流水号
						}else{
							//如果该运单有删除的流水号或者运单，则将已删除的流水号取回
							if(load.handoverbillmodify.deletedSerialNoMap.get(waybillNo) != null){
								//获取被删除的流水号
								var deletedSerialNoMap = load.handoverbillmodify.deletedSerialNoMap.get(waybillNo);
								//将被删除的流水号找回
								deletedSerialNoMap.each(function(key,value,length){
									unsavedSerialNoMap.add(key,value);
								});
								//如果被删除的运单不为空，则将删除的运单移除
								if(load.handoverbillmodify.deletedWaybillMap.get(waybillNo) != undefined){
									addedWaybill = load.handoverbillmodify.deletedWaybillMap.get(waybillNo);
									load.handoverbillmodify.deletedWaybillMap.removeAtKey(waybillNo);
								}else{
									addedWaybill = record;
									var nowSerialNoMap = load.handoverbillmodify.allSerialNoMap.get(waybillNo);
									nowSerialNoMap.each(function(key,value,length){
										unsavedSerialNoMap.add(key,value);
									});
								}
								//重新记录找回的流水号，并将之从删除的流水号map中删除
								load.handoverbillmodify.allSerialNoMap.add(waybillNo,unsavedSerialNoMap);
								load.handoverbillmodify.deletedSerialNoMap.removeAtKey(waybillNo);
								//构造待插入的运单
								var pieces = load.handoverbillmodify.allSerialNoMap.get(waybillNo).getCount();
								addedWaybill.set('cubage',((addedWaybill.get('cubage')/addedWaybill.get('pieces'))*pieces).toFixed(2));
								addedWaybill.set('weight',((addedWaybill.get('weight')/addedWaybill.get('pieces'))*pieces).toFixed(2));
								//计算实际总重量，实体体积.每个流水号总和
								var weight = 0 , volumn = 0;
								unsavedSerialNoMap.each(function(serial,serialNo,length){
									weight += serialNo.get('weight');
									volumn += serialNo.get('volumn');
								});
								addedWaybill.set('cubageAc',volumn);
								addedWaybill.set('weightAc',weight);
							
								addedWaybill.set('pieces',pieces);
								//因为该运单中流水号曾被删除，所以在update运单map中必然有记录，此时还原，所以将该运单号相关更新记录删除
								load.handoverbillmodify.updatedWaybillMap.removeAtKey(waybillNo);
							//如果运单未有删除之记录，则为添加流水号
							}else{
								var oldWaybillRecord = load.handoverbillmodify.oldWaybillMap.get(waybillNo);
								load.handoverbillmodify.addedSerialNoMap.add(waybillNo,unsavedSerialNoMap);
								//获取已经存在的流水号
								var oldSerialNoMap = load.handoverbillmodify.allSerialNoMap.get(waybillNo);
								unsavedSerialNoMap.each(function(key,value,length){
									oldSerialNoMap.add(key,value);
								});
								//重新放入当前所有流水号
								load.handoverbillmodify.allSerialNoMap.add(waybillNo,oldSerialNoMap);
								//构造待插入的运单
								addedWaybill = oldWaybillRecord;
								var pieces = oldSerialNoMap.getCount();
								addedWaybill.set('pieces',pieces);
								addedWaybill.set('cubage',((waybillStock.cubage/waybillStock.pieces)*pieces).toFixed(2));
								addedWaybill.set('weight',((waybillStock.weight/waybillStock.pieces)*pieces).toFixed(2));
								//计算实际总重量，实体体积.每个流水号总和 author：hyd
								var weight = 0 , volumn = 0;
								oldSerialNoMap.each(function(serial,serialNo,length){
									weight += serialNo.get('weight');
									volumn += serialNo.get('volumn');
								});
								addedWaybill.set('cubageAc',volumn);
								addedWaybill.set('weightAc',weight);
								
							}
						}
						//将运单插入表格中
						if(record != null){//若主界面表格中有该运单
							//更新主页列表中的件数等信息
							record.set('pieces',addedWaybill.get('pieces'));
							record.set('cubage',addedWaybill.get('cubage'));
							record.set('weight',addedWaybill.get('weight'));
							record.set('cubageAc',addedWaybill.get('cubageAc'));
							record.set('weightAc',addedWaybill.get('weightAc'));
							//若主页列表该运单记录的二级表格打开，则刷新二级表格
							if(!Ext.isEmpty(plugin.getExpendRow())) {
								var pluginGrid = plugin.getExpendRowBody();
								var store = pluginGrid.getStore();
								var subWaybillNo = store.getAt(0).get('waybillNo');
								if(subWaybillNo == waybillNo){
									store.loadData(load.handoverbillmodify.allSerialNoMap.get(waybillNo).getValues());
								}
							}
						}else{
							//若主界面表格没有该运单
							//将该运单插入主页面
							mainGrid.store.insert(mainGrid.store.getCount(),addedWaybill);
						}
						Ext.ux.Toast.msg('提示', 
							'添加成功！', 
							'info',
							1500);
						waybillNoCmp.focus(true,true);
						loadMask.hide();
					},
					exception : function(response){
						var result = Ext.decode(response.responseText);
						Ext.ux.Toast.msg('提示', 
							'添加失败，' + result.message, 
							'error');
						loadMask.hide();
					}
				});
			}else{
				Ext.ux.Toast.msg('提示', 
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
		disabled: !load.handoverbillmodify.isPermission('load/printnewhandoverbillButton'),
		hidden: !load.handoverbillmodify.isPermission('load/printnewhandoverbillButton'),
		text : load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.waybillGrid.printButtonText')/*'打印'*/,
		handler : function(){
			var records = this.up('grid').getStore().getRange();
			if(load.handoverbillmodify.isSaved !== 'Y'){
				Ext.ux.Toast.msg('提示', 
						"请先保存交接单！", 
						'error');
				return;
			}
			if(records.length <= 0) {
				Ext.ux.Toast.msg('提示', 
						"没有运单数据, 请确认!", 
						'error');
				return;
			}
			var vehicleNo = load.handoverbillmodify.basicInfoForm.getForm().findField('vehicleNo').getValue(),
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
						Ext.ux.Toast.msg('提示', 
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
	} ],
	dockedItems: [{
	    xtype: 'toolbar',
	    dock: 'bottom',
	    layout : 'column',
	    defaults: {
			xtype : 'textfield',
			readOnly : true,
			labelWidth : 80
		},
		items: [{
			fieldLabel : load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.waybillGrid.totalWaybillLabel')/*'总票数'*/,
			xtype : 'textfield',
			readOnly : true,
			columnWidth : 1/5,
			value : 0,
			id : 'Foss_load_handOverBillModify_MainPageTotalCount'
			},{
				fieldLabel : load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.waybillGrid.totalPiecesLabel')/*'总件数'*/,
				xtype : 'textfield',
				readOnly : true,
				columnWidth : 1/5,
				value : 0,
				id : 'Foss_load_handOverBillModify_MainPageTotalPieces'
			},{
				fieldLabel : '总重量(千克)',
				xtype : 'textfield',
				readOnly : true,
				labelWidth : 120,
				columnWidth : 1/5,
				value : 0,
				id : 'Foss_load_handOverBillModify_MainPageTotalWeight'
			},{
				fieldLabel : '总体积(方)',
				xtype : 'textfield',
				readOnly : true,
				columnWidth : 1/5,
				value : 0,
				id : 'Foss_load_handOverBillModify_MainPageTotalVolume'
			},{
				fieldLabel : '未转换总体积(方)',
				xtype : 'textfield',
				readOnly : true,
				labelWidth : 120,
				columnWidth : 1/5,
				value : 0,
				id : 'Foss_load_handOverBillModify_MainPageUnconvertTotalVolume'
			},{
				xtype : 'hidden',
				id : 'Foss_load_handOverBillModify_MainPageTotalCodAmount',
				value : 0
			}]
	  }],
	columns : [ {
		xtype : 'actioncolumn',
		width : 60,
		text : load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.waybillGrid.actionColumn')/*'操作'*/,
		align : 'center',
		items : [ {
			iconCls : 'deppon_icons_remove',
			tooltip : load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.waybillGrid.deleteButtonColumn')/*'删除'*/,
			handler : function(grid, rowIndex, colIndex) {
				var record = grid.store.getAt(rowIndex),
					waybillNo = record.get('waybillNo');
				load.handoverbillmodify.allSerialNoMap.removeAtKey(waybillNo);
				//若修改前的运单map中有此运单，则从load.handoverbillmodify.oldWaybillMap中取出该运单放于deletedWaybillMap,流水号置于load.handoverbillmodify.deletedSerialNoMap中
				if(load.handoverbillmodify.oldWaybillMap.get(waybillNo) !== undefined){
					load.handoverbillmodify.deletedWaybillMap.replace(waybillNo,load.handoverbillmodify.oldWaybillMap.get(waybillNo));
					//此处无需判断删除的流水号是新增的还是原来就有的， 因为直接取出原来的流水号Map覆盖之
					var serialNoMap = load.handoverbillmodify.oldSerialNoMap.get(waybillNo);
					load.handoverbillmodify.deletedSerialNoMap.replace(waybillNo,serialNoMap.clone());
				}else{
					//若修改前的运单map中没有此运单，则将该运单和其下的流水号分别从load.handoverbillmodify.addedWaybillMap和load.handoverbillmodify.addedSerialNoMap中删除
					load.handoverbillmodify.addedWaybillMap.removeAtKey(waybillNo);
					load.handoverbillmodify.addedSerialNoMap.removeAtKey(waybillNo);
				}
				grid.getStore().removeAt(rowIndex);
			}
		} ]
	}, {
		dataIndex : 'waybillNo',
		align : 'center',
		width : 80,
		xtype : 'ellipsiscolumn',
		text : load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.waybillGrid.waybillNoColumn')/*'运单号'*/
	}, {
		dataIndex : 'transProperty',
		align : 'center',
		width : 95,
		text : load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.waybillGrid.transPropertyColumn')/*'运输性质'*/
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
		text : load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.waybillGrid.piecesColumn')/*'已配件数'*/
	}, {
		dataIndex : 'weight',
		align : 'center',
		flex : 1,
		text : load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.waybillGrid.weightColumn')/*'已配重量'*/
	}, {
		dataIndex : 'weightAc',
		align : 'center',
		flex : 1,
		text : load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.waybillGrid.weightAcColumn')/*'实际重量'*/
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
		text : load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.waybillGrid.volumeColumn')/*'已配体积'*/
	}, {
		dataIndex : 'cubageAc',
		align : 'center',
		flex : 1,
		text : load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.waybillGrid.volumeAcColumn')/*'实际体积'*/
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
		text : load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.waybillGrid.noteColumn')/*'备注'*/,
		editor : {
			xtype : 'textarea',
			maxLength : 300
		}
	}, {
		dataIndex : 'goodsName',
		align : 'center',
		flex : 1,
		text : load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.waybillGrid.goodsNameColumn')/*'货物名称'*/
	}, {
		dataIndex : 'packing',
		align : 'center',
		flex : 1,
		text : load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.waybillGrid.packingColumn')/*'包装'*/
	}, {
		dataIndex : 'waybillNote',
		align : 'center',
		flex : 1,
		text : load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.waybillGrid.waybillNoteColumn')/*'运单备注'*/
	} ]
});

//生成当前日期
load.handoverbillmodify.handOverBillGetDateTime = function(){
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
Ext.define('Foss.load.handoverbillmodify.BasicInfoForm', {
	extend : 'Ext.form.Panel',
	title : load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.basicInfoForm.formTitle')/*'交接单基本信息'*/,
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
	items : [{
		fieldLabel : load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.basicInfoForm.handOverTypeLabel')/*'交接类型'*/,
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
	    		if(newValue ==='SHORT_DISTANCE_HANDOVER'||newValue ==='DIVISION_HANDOVER'){
		    		
		    		form.findField('tranGoodsType').bindStore(load.handoverbillmodify.getTranGoodsStore(1));
		    	}else{
		    		form.findField('tranGoodsType').bindStore(load.handoverbillmodify.getTranGoodsStore(2));
		    	}
	    		if(newValue==='SHORT_DISTANCE_HANDOVER'&&load.handoverbillmodify.beDivision=='Y'){//假如交接类型为分部交接
		    		form.findField('driver').setReadOnly(true);//只读
		    	
		    		form.findField('driver').reset();
		    		form.findField('driverTel').reset();//清空
		    	}else{
		    		form.findField('driver').setReadOnly(false);
		    	}
	    		
	    	}
	    } 
	 },{
		fieldLabel : load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.basicInfoForm.handOverBillNoLabel')/*'交接单编号'*/,
		name : 'handOverBillNo'
	}, {
		fieldLabel : load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.basicInfoForm.handOverTimeLabel')/*'交接时间'*/,
		name : 'handOverTime'
	}, {
		fieldLabel : load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.basicInfoForm.departDeptLabel')/*'出发部门'*/,
		name : 'departDept'
	}, {
		name : 'arriveDeptCode',
		hidden : true
	}, {
		name : 'arriveDept',
		fieldLabel : '到达部门'
	},{
		fieldLabel : load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.basicInfoForm.vehicleNoLabel')/*'车牌号'*/,
		readOnly : false,
		xtype : 'commontruckselector',
		name : 'vehicleNo',
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
				if(load.handoverbillmodify.beDivision=='Y'&&handOverType=='SHORT_DISTANCE_HANDOVER'){
					form.findField('driver').reset();
					form.findField('driverTel').reset();
				}
				
				//后台获取司机信息
				var myMask = new Ext.LoadMask(this.up('form'), {
					msg:"加载中，请稍候..."
				});
				var saveButton = Ext.getCmp('Foss_load_handOverBillModify_mainPage_saveButton_ID');
				var buttonMask = new Ext.LoadMask(saveButton, {
					msg:"请稍候..."
				});
				myMask.show();
				buttonMask.show();
				Ext.Ajax.request({
					url : load.realPath('queryDriverInfoByVehicleNoForHandOverBill.action'),
					params : {'handOverBillVo.vehicleNo' : cmp.getValue(),
						       'handOverBillVo.beDivision':load.handoverbillmodify.beDivision,
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
		fieldLabel : load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.basicInfoForm.driverLabel')/*'司机'*/,
		readOnly : false,
		name : 'driver',
		xtype : 'commondriverselector',
		allowBlank : false,
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
		fieldLabel : load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.basicInfoForm.driverPhoneLabel')/*'司机电话'*/,
		name : 'driverTel'
	}, {
		name : 'driverName',
		hidden : true
	},{
		fieldLabel : load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.basicInfoForm.loadEndTimeLabel')/*'装车完成时间'*/,
		name : 'loadEndTime',
		xtype : 'datetimefield_date97',
		time : true,
		readOnly : false,
		id : 'Foss_handOverBillModify_loadEndTime_ID',
		allowBlank : false,
		dateConfig: {
			el : 'Foss_handOverBillModify_loadEndTime_ID-inputEl'
		}
	}, FossDataDictionary.getDataDictionaryCombo('LOAD_GOODS_TYPE',{
		fieldLabel : load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.basicInfoForm.goodsTypeLabel')/*'货物类型'*/,
		labelWidth : 85,
		allowBlank : false,
		name : 'goodsType',
		editable : false
	}),{
		fieldLabel : load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.basicInfoForm.creatorLabel')/*'制单人'*/,
		name : 'createUserName'
	}, {
		fieldLabel : load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.basicInfoForm.modifyManLabel')/*'修改人'*/,
		name : 'modifyUserName'
	}, {
		boxLabel : load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.basicInfoForm.isPreHandOverBillLabel')/*'是否预配交接单'*/,
		name : 'isPreHandOverBill',
		xtype : 'checkbox',
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
	}, {
		boxLabel : load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.basicInfoForm.isAgencyVisitLabel')/*'是否代理上门接货'*/,
		name : 'isAgencyVisit',
		xtype : 'checkbox'
	},{
		boxLabel : '是否PDA扫描',
		name : 'isCreatedByPDA',
		xtype : 'checkbox'
	},{
		fieldLabel : load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.basicInfoForm.tranGoodsType')/*'转货类型'*/,
		name : 'tranGoodsType',
		xtype : 'combobox',
		queryMode: 'local',
		allowBlank : false,
	    displayField: 'value',
	    valueField: 'key',
	    editable : false
      
	}, {
		fieldLabel : load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.waybillGrid.noteColumn')/*'备注'*/,
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
Ext.define('load.handoverbillmodify.store',{
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
load.handoverbillmodify.getTranGoodsStore = function(value){
	if(value==1){
		return Ext.create('load.handoverbillmodify.store', {
		 	data:{
				'tranGoodsType':[
					{'key':'TRANSITGOODS','value':'转货'},
					{'key':'CARRYGOODS','value':'带货'}
				]
			}
		});
	} else {
		return Ext.create('load.handoverbillmodify.store', {
		 	data:{
				'tranGoodsType':[
					{'key':'TRANSITGOODS','value':'转货'}
				]
			}
		});
	}
}
// 定义查询交接运单界面-查询条件form
Ext.define('Foss.load.handoverbillmodify.QueryWaybillForm', {
	extend : 'Ext.form.Panel',
	title : load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.queryWaybillForm.formTitle')/*'查询条件'*/,
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
		fieldLabel : load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.queryWaybillForm.instorageTime')/*'入库时间'*/,
		columnWidth : 2/5,
		// 类型为datetimefield_date97需要配置fieldId,可以赋予此属性任何唯一标 //识的String值
		fieldId : 'Foss_handOverBillModify_QueryForm_InstorageTime_fieldID',
		id : 'Foss_handOverBillModify_QueryForm_InstorageTime_ID',
		// dateType: 'datetimefield_date97',
		dateType: 'datefield',
		fromName : 'beginInStorageTime',
		toName : 'endInStorageTime',
		toValue : new Date(),
		fromValue : new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate() - 20),
		allowBlank : false,
		disallowBlank: true
	},{
		fieldLabel : load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.queryWaybillForm.transType')/*'运输类型'*/,
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
		fieldLabel : load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.waybillGrid.transPropertyColumn')/*'运输性质'*/,
		name : 'transProperty',
		xtype : 'combobox',
		queryMode: 'local',
		triggerAction:'all',
	    displayField: 'valueName',
	    valueField: 'valueCode',
	    editable : false,
	    multiSelect : true,
	    store : Ext.create('Foss.load.handoverbillmodify.ProductStore'),
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
		fieldLabel : load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.waybillGrid.waybillNoColumn')/*'运单号'*/,
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
			text : load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.queryWaybillForm.resetButton')/*'重置'*/,
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
			id : 'Foss_handOverBillModify_QueryForm_queryButton_ID',
			text : load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.queryWaybillForm.queryButton')/*'查询'*/,
			handler : function(){
				if(load.handoverbillmodify.queryWaybillTabPanel.getActiveTab().title == load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.queryResultTab.tab1Title')/*'库存货物'*/){
					load.handoverbillmodify.pagingBar.moveFirst();
					var sm = load.handoverbillmodify.queryWaybillGrid.getSelectionModel();
					sm.deselectAll(true);
					//清空统计信息
					Ext.getCmp('Foss_load_handOverBillModify_QueryPageTotalPieces').setValue(0);
					Ext.getCmp('Foss_load_handOverBillModify_QueryPageTotalWeight').setValue(0);
					Ext.getCmp('Foss_load_handOverBillModify_QueryPageTotalCubage').setValue(0);
					Ext.getCmp('Foss_load_handOverBillModify_QueryPageTotalMoney').setValue(0);
					//每次查询前清空map
					load.handoverbillmodify.selectedWaybillMap.clear();
				}else{
					load.handoverbillmodify.enRoutePagingBar.moveFirst();
					var sm = load.handoverbillmodify.queryEnRouteWaybillGrid.getSelectionModel();
					sm.deselectAll(true);
					//每次查询前清空map
					load.handoverbillmodify.selectedEnRouteWaybillMap.clear();
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
Ext.define('Foss.load.handoverbillmodify.QueryWaybillStore', {
	extend : 'Ext.data.Store',
	// 绑定一个模型
	model : 'Foss.load.handoverbillmodify.waybillStockModel',
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
			this.buttonLoadMask = new Ext.LoadMask(Ext.getCmp('Foss_handOverBillModify_QueryForm_queryButton_ID'),{
				msg : '.....'
			});
		}
		return this.buttonLoadMask;
	},
	gridLoadMask : null,
	getGridLoadMask : function(){
		if(this.gridLoadMask == null){
			this.gridLoadMask = new Ext.LoadMask(load.handoverbillmodify.queryWaybillGrid, {
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
			var sm = load.handoverbillmodify.queryWaybillGrid.getSelectionModel(),
				record,
				waybillNo;
			for(var i in records){
				record = records[i];
				waybillNo = record.get('waybillNo');
				//tempWaybillMap.replace(waybillNo,record);
				//数据加载后，检查之前是否被勾选，若缓存map中记录的有该运单号，则重新勾选
				if(load.handoverbillmodify.selectedWaybillMap.get(waybillNo) != null){
					sm.select(record,true,true);//勾选第一层表格中的行：第二个参数为true，保留其他已勾选的行，第三个参数为true，表示勾选后不触发select事件
				}
			};
			this.getButtonLoadMask().hide();
			this.getGridLoadMask().hide();
		},
		'beforeload' : function(store, operation, eOpts){
			this.getGridLoadMask().show();
			this.getButtonLoadMask().show();
			var form =  load.handoverbillmodify.queryWaybillForm.getForm();
			var handOverType = load.handoverbillmodify.basicInfoForm.getForm().findField('handOverType').getValue();
			var arriveDeptCode = load.handoverbillmodify.basicInfoForm.getForm().findField('arriveDeptCode').getValue();
			if(!form.findField('beginInStorageTime').isValid()){
				Ext.ux.Toast.msg(load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.waybillGrid.alertInfoTitle')/*'提示'*/, load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.queryResultTab.input1stDate')/*'请输入起始日期'*/, 'error', 2000);
				return false;
			}
			//因为入库时间只有年月日，故天数加一天
			var endInStorageTime = form.findField('endInStorageTime').getValue();
			if(!form.findField('endInStorageTime').isValid()){
				Ext.ux.Toast.msg(load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.waybillGrid.alertInfoTitle')/*'提示'*/, load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.queryResultTab.input2ndDate')/*'请输入截止日期'*/, 'error', 2000);
				return false;
			}
			endInStorageTime = new Date(endInStorageTime.getFullYear(),endInStorageTime.getMonth(),endInStorageTime.getDate() + 1)
			//到达部门
			var arriveDeptList = new Array();
			arriveDeptList.push(arriveDeptCode);

			//如果为短配交接单或营业部交接单
			if(handOverType == 'SHORT_DISTANCE_HANDOVER'||handOverType == 'DIVISION_HANDOVER'||handOverType == 'SALES_DEPARTMENT_HANDOVER'){
				Ext.apply(operation, {
				params : {
					'handOverBillVo.queryWaybillForHandOverBillDto.beginInStorageTime' : form.findField('beginInStorageTime').getValue(),
					'handOverBillVo.queryWaybillForHandOverBillDto.endInStorageTime' : endInStorageTime,
					'handOverBillVo.queryWaybillForHandOverBillDto.waybillNo' : form.findField('waybillNo').getValue(),
					'handOverBillVo.queryWaybillForHandOverBillDto.transType' : form.findField('transType').getValue(),
					'handOverBillVo.queryWaybillForHandOverBillDto.goodsType' : load.handoverbillmodify.basicInfoForm.getForm().findField('goodsType').getValue(),
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
					'handOverBillVo.queryWaybillForHandOverBillDto.goodsType' : load.handoverbillmodify.basicInfoForm.getForm().findField('goodsType').getValue(),
					'handOverBillVo.queryWaybillForHandOverBillDto.transPropertyList' : form.findField('transProperty').getValue(),
					'handOverBillVo.queryWaybillForHandOverBillDto.arriveDeptList' : arriveDeptList,
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
						'handOverBillVo.queryWaybillForHandOverBillDto.arriveDeptList' : arriveDeptList,
						'handOverBillVo.queryWaybillForHandOverBillDto.handOverType' : handOverType
					}
				});
			}
		}
	}
});

//定义查询在途运单store
Ext.define('Foss.load.handoverbillmodify.QueryEnRouteWaybillStore', {
	extend : 'Ext.data.Store',
	// 绑定一个模型
	model : 'Foss.load.handoverbillmodify.waybillStockModel',
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
			this.buttonLoadMask = new Ext.LoadMask(Ext.getCmp('Foss_handOverBillModify_QueryForm_queryButton_ID'),{
				msg : '.....'
			});
		}
		return this.buttonLoadMask;
	},
	gridLoadMask : null,
	getGridLoadMask : function(){
		if(this.gridLoadMask == null){
			this.gridLoadMask = new Ext.LoadMask(load.handoverbillmodify.queryEnRouteWaybillGrid, {
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
			var sm = load.handoverbillmodify.queryEnRouteWaybillGrid.getSelectionModel();
			var record;
			var waybillId;
			for(var i in records){
				record = records[i];
				waybillId = record.get('id');
				//数据加载后，检查之前是否被勾选，若缓存map中记录的有该运单号，则重新勾选
				if(load.handoverbillmodify.selectedEnRouteWaybillMap.get(waybillId) != null){
					var selectedRecord = load.handoverbillmodify.queryEnRouteWaybillGrid.store.findRecord('id', waybillId, 0, false, true, true);//
					sm.select(selectedRecord,true,true);//勾选第一层表格中的行：第二个参数为true，保留其他已勾选的行，第三个参数为true，表示勾选后不触发select事件
				}
			};
			this.getButtonLoadMask().hide();
			this.getGridLoadMask().hide();
		},
		'beforeload' : function(store, operation, eOpts){
			this.getButtonLoadMask().show();
			this.getGridLoadMask().show();
			var form =  load.handoverbillmodify.queryWaybillForm.getForm(),
				basicInfoForm = load.handoverbillmodify.basicInfoForm.getForm();
			var handOverType = basicInfoForm.findField('handOverType').getValue();
			var arriveDeptCode = basicInfoForm.findField('arriveDeptCode').getValue();
			//到达部门
			var arriveDeptList = new Array();
			arriveDeptList.push(arriveDeptCode);
			//获取查询方案里的配载部门
			if(load.handoverbillmodify.AddAssembleDeptWindow != null){
				var store = load.handoverbillmodify.addAssembleDeptGrid.store;
			store.each(function(record){
					arriveDeptList.push(record.get('assembleDeptCode'));
				});
			}
			//如果为短配交接单
			if(handOverType == 'SHORT_DISTANCE_HANDOVER'||handOverType == 'DIVISION_HANDOVER'){
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
			//如果为外发交接单
			}else{
				Ext.apply(operation, {
					params : {
						'handOverBillVo.queryWaybillForHandOverBillDto.waybillNo' : form.findField('waybillNo').getValue(),
						'handOverBillVo.queryWaybillForHandOverBillDto.arriveDeptList' : arriveDeptList,
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
load.handoverbillmodify.selectedWaybillMap = new Ext.util.HashMap();

// 定义库存运单查询结果列表
Ext.define('Foss.load.handoverbillmodify.QueryWaybillGrid', {
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
	emptyText : load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.queryResultTab.emptyText')/*'查询结果为空'*/,
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.load.handoverbillmodify.QueryWaybillStore');
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
		load.handoverbillmodify.pagingBar = me.bbar;
		me.callParent([cfg]);
	},
	dockedItems: [{
	    xtype: 'toolbar',
	    id : 'Foss_load_handOverBillModify_QueryPageTotalPieces_toobar',
	    dock: 'bottom',
	    layout : 'column',
	    defaults: {
			xtype : 'textfield',
			readOnly : true,
			labelWidth : 120
		},
		items: [{
				id : 'Foss_load_handOverBillModify_QueryPageTotalPieces',
				fieldLabel: load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.queryResultTab.totalPieces')/*'已选总件数'*/,
				columnWidth : 1/3,
				value : 0
			},{
				id : 'Foss_load_handOverBillModify_QueryPageTotalWeight',
				fieldLabel: '已选总重量(千克)',
				columnWidth : 1/3,
				value : 0
			},{
				id : 'Foss_load_handOverBillModify_QueryPageTotalCubage',
				fieldLabel: '已选总体积(方)',
				columnWidth : 1/3,
				value : 0
			},{
				id : 'Foss_load_handOverBillModify_QueryPageUnconvertTotalCubage',
				fieldLabel: '已选未转换总体积(方)',
				columnWidth : 1/2,
				labelWidth : 140,
				value : 0
			},{
				id : 'Foss_load_handOverBillModify_QueryPageTotalMoney',
				fieldLabel: load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.queryResultTab.totalMoney')/*'已选总金额'*/,
				columnWidth : 1/2,
				value : 0
			}]
    }],
	// 定义行展开
	plugins : [ {
		header: true,
		ptype : 'rowexpander',
		// 定义行展开模式（单行与多行），默认是多行展开(值true)
		pluginId : 'handOverBillModify_queryWaybillGrid_serialNoGrid',
		rowsExpander : false,
		// 行体内容
		rowBodyElement : 'Foss.load.handoverbillmodify.QueryWaybillSerialNoGrid'
	} ],
	rightMove : function(grid,record,rowIndex,type){
		var waybillNo = record.get('waybillNo'),
			unsubmitedGrid = load.handoverbillmodify.unsubmitedWaybillGrid,
			unStore = unsubmitedGrid.store,
			unRec = unStore.findRecord('waybillNo',waybillNo,0,false,true,true),
			//获取主页面该运单号的record
			unsavedWaybill = load.handoverbillmodify.allSerialNoMap.get(waybillNo);
			serialNoList = record.get('serialNoStockList'),
			flag = false;
		//如果已勾选运单map中有此运单，则移除
		if(load.handoverbillmodify.selectedWaybillMap.get(waybillNo) !== undefined){
			load.handoverbillmodify.selectedWaybillMap.removeAtKey(waybillNo);
			flag = true;
		}
		//右侧grid中是否有该运单
		if(unRec === null){
			var serialNoMap = new Ext.util.HashMap();
			//实际重量，体积 author:hyd
			var weightAc = 0,volumnAc = 0;
			for(var i in serialNoList){
				var serialNo = serialNoList[i],
					isPreHandOver = serialNo.isPreHandOver;
				//如果主页面没有的流水号，并且是未预配状态，则可右移
				if((unsavedWaybill === undefined || unsavedWaybill.get(serialNo.serialNo) === undefined)
					&& isPreHandOver !== 'Y'){
					weightAc += serialNo.weight;
					volumnAc += serialNo.volumn;
					var serialNoRecord = Ext.ModelManager.create(serialNo, 'Foss.load.handoverbillmodify.serialNoModel');
					serialNoMap.replace(serialNo.serialNo,serialNoRecord);
				}
			}
			if(serialNoMap.getCount() !== 0){
				record.set('serialNoMap',serialNoMap);
				//插入右侧grid
				var recCopy = record.copy(),
					pieces = serialNoMap.getCount();
				recCopy.set('weight',((recCopy.get('weight')/recCopy.get('pieces'))*pieces).toFixed(2));
				recCopy.set('cubage',((recCopy.get('cubage')/recCopy.get('pieces'))*pieces).toFixed(2));
				recCopy.set('weightAc',weightAc);
				recCopy.set('cubageAc',volumnAc);
				recCopy.set('pieces',pieces);
				unStore.insert(unStore.getCount(),recCopy);
			}
		}else{
			var weightAc = 0,volumnAc = 0;
			var serialNoMap = unRec.get('serialNoMap');
			for(var i in serialNoList){
				var serialNo = serialNoList[i],
					isPreHandOver = serialNo.isPreHandOver;
				//如果主页面没有的流水号，并且是未预配状态，则可右移
				if((unsavedWaybill === undefined || unsavedWaybill.get(serialNo.serialNo) === undefined)
					&& isPreHandOver !== 'Y'){
					var serialNoRecord = Ext.ModelManager.create(serialNo, 'Foss.load.handoverbillmodify.serialNoModel');
					serialNoMap.replace(serialNo.serialNo,serialNoRecord);
				}
			}
			//更新件数、重量、体积
			var pieces = serialNoMap.getCount();
			unRec.set('weight',((unRec.get('weight')/unRec.get('pieces'))*pieces).toFixed(2));
			unRec.set('cubage',((unRec.get('cubage')/unRec.get('pieces'))*pieces).toFixed(2));
			unRec.set('pieces',serialNoMap.getCount());
			serialNoMap.each(function(serial,serialNo,length){
				//累加每个流水号的重量体积
				weightAc = weightAc+serialNo.get('weight');
				volumnAc = volumnAc+serialNo.get('volumn');
			});
			unRec.set('weightAc',weightAc);
			unRec.set('cubageAc',volumnAc);
		}
		var plugin = unsubmitedGrid.getPlugin('handOverBillModify_unsubmitedWaybillGrid_serialNoGrid');
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
				load.handoverbillmodify.updateQueryPageStaInfo();
			}
		}
		//重新统计已选运单
		load.handoverbillmodify.updateUnsubmitedWaybillStaInfo();
		return flag;
	},
	columns : [ {
		xtype : 'actioncolumn',
		width : 40,
		text : load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.waybillGrid.actionColumn')/*'操作'*/,
		align : 'center',
		items : [ {
			tooltip : load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.queryResultTab.moveRight')/*'右移'*/,
			iconCls : 'foss_icons_stl_sendmes',
			handler : function(grid, rowIndex, colIndex) {
				var record = grid.store.getAt(rowIndex);
				load.handoverbillmodify.queryWaybillGrid.rightMove(grid,record,rowIndex,'ONE');
			}
		} ]
	}, {
		dataIndex : 'waybillNo',
		align : 'center',
		width : 80,
		xtype : 'ellipsiscolumn',
		text : load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.waybillGrid.waybillNoColumn')/*'运单号'*/
	}, {
		dataIndex : 'transProperty',
		align : 'center',
		width : 95,
		text : load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.waybillGrid.transPropertyColumn')/*'运输性质'*/
	}, {
		dataIndex : 'transPropertyCode',
		align : 'center',
		hidden : true, 
		width : 95,
		text : '运输性质编码'/*'运输性质'*/
	}, {
		dataIndex : 'goodsName',
		align : 'center',
		width : 60,
		xtype : 'ellipsiscolumn',
		text : load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.waybillGrid.goodsNameColumn')/*'货物名称'*/
	}, {
		dataIndex : 'packing',
		align : 'center',
		width : 60,
		xtype : 'ellipsiscolumn',
		text : load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.waybillGrid.packingColumn')/*'包装'*/
	}, {
		dataIndex : 'pieces',
		align : 'center',
		flex : 1,
		text : load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.waybillGrid.piecees')/*'件数'*/
	}, {
		dataIndex : 'weight',
		align : 'center',
		width : 60,
		xtype : 'ellipsiscolumn',
		text : load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.waybillGrid.weight')/*'重量'*/
	}, {
		dataIndex : 'cubage',
		align : 'center',
		width : 60,
		xtype : 'ellipsiscolumn',
		text : load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.waybillGrid.volume')/*'体积'*/
	}, {
		dataIndex : 'instorageDate',
		align : 'center',
		width : 90,
		text : load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.waybillGrid.waybillDate')/*'入库日期'*/,
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
		text : load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.basicInfoForm.arriveDeptLabel')/*'到达部门'*/
	}, {
		dataIndex : 'insuranceValue',
		align : 'center',
		width : 60,
		xtype : 'ellipsiscolumn',
		text : load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.waybillGrid.insuranceValue')/*'保险价值'*/
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
		text : load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.waybillGrid.waybillPieces')/*'开单件数'*/
	}, {
		dataIndex : 'isPreciousGoods',
		align : 'center',
		width : 80,
		text : load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.waybillGrid.isPreciousGoods')/*'是否贵重物品'*/,
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
		text : load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.waybillGrid.waybillNoteColumn')/*'运单备注'*/
	} ],
	getRightMenu : function(record,rowIndex){
		var grid = this;
		function rightMoveOne(){
			grid.rightMove(grid,record,rowIndex,'ONE');
		}
		var	rightMenu =	new Ext.menu.Menu({
	        items : [{
                text : load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.queryResultTab.moveRight')/*'右移'*/,
                handler : rightMoveOne
	        }]
	     });
		 return rightMenu;
	},
	listeners : {
		'select' : function(rowModel, record, index, eOpts ){
			var grid = this,
				plugin = grid.getPlugin('handOverBillModify_queryWaybillGrid_serialNoGrid'),
				waybillNo = record.get('waybillNo'),
				unsavedWaybill = load.handoverbillmodify.allSerialNoMap.get(waybillNo);
				unsubmitedWaybill = load.handoverbillmodify.unsubmitedWaybillGrid.store.findRecord('waybillNo',waybillNo,0,false,true,true);
			//取出record中的流水号list，做成map，放到第一层map的record中
			var serialNoStockList = record.get('serialNoStockList'),
				serialNoStockMap = new Ext.util.HashMap();
			for(var i in serialNoStockList){
				var serialNo = serialNoStockList[i];
					isPreHandOver = serialNo.isPreHandOver;
				if((unsavedWaybill !== undefined &&  unsavedWaybill.get(serialNo.serialNo) !== undefined)
					||(unsubmitedWaybill != null && unsubmitedWaybill.get('serialNoMap') !== '' && unsubmitedWaybill.get('serialNoMap').get(serialNo.serialNo) !== undefined)
						|| isPreHandOver == 'Y'){
					continue;
				}
				var serialNoRecord = Ext.ModelManager.create(serialNoStockList[i], 'Foss.load.handoverbillmodify.serialNoModel');
				serialNoStockMap.replace(serialNo.serialNo,serialNoRecord);
			}
			if(serialNoStockMap.getCount() !== 0){
				//将运单置于已勾选运单map中
				var recCopy = record.copy();
				load.handoverbillmodify.selectedWaybillMap.replace(waybillNo,recCopy);
				recCopy.set('serialNoMap',serialNoStockMap);
				load.handoverbillmodify.updateQueryPageStaInfo();
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
		var plugin = grid.getPlugin('handOverBillModify_queryWaybillGrid_serialNoGrid');
		if(!Ext.isEmpty(plugin.getExpendRow())) {
			var item = plugin.getExpendRowBody();
			var store = item.getStore();
			var waybillNo = store.getAt(0).get('waybillNo');
			if(waybillNo == record.get('waybillNo')){
				item.getSelectionModel().deselectAll(true);
			}
		}
		var waybillNo = record.get('waybillNo');
		if(load.handoverbillmodify.selectedWaybillMap.get(waybillNo) !== undefined){
			// 从map中移除和此运单对应的库存信息
			load.handoverbillmodify.selectedWaybillMap.removeAtKey(waybillNo);
		}
		record.set('serialNoMap','');
		load.handoverbillmodify.updateQueryPageStaInfo();
	},
	'itemcontextmenu' : function(view,record,item,index,e, eOpts ){
		var menu = this.getRightMenu(record,index);
		e.preventDefault();
       	menu.showAt(e.getXY());
	}
}
});

// 定义查询库存运单中流水号列表store
Ext.define('Foss.load.handoverbillmodify.QueryWaybillSerialNoStore', {
	extend : 'Ext.data.Store',
	// 绑定一个模型
	model : 'Foss.load.handoverbillmodify.serialNoModel',
	listeners : {
		'remove' : function(store, record, index, eOpts){
			var waybillNo = record.get('waybillNo'),
				serialNo = record.get('serialNo');
			//将移除的流水号从一级record的stockList中删除
			var superRec = load.handoverbillmodify.queryWaybillGrid.store.findRecord('waybillNo',waybillNo,0,false,true,true),
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
Ext.define('Foss.load.handoverbillmodify.QueryEnRouteWaybillSerialNoStore', {
	extend : 'Ext.data.Store',
	// 绑定一个模型
	model : 'Foss.load.handoverbillmodify.serialNoModel',
	listeners : {
		'remove' : function(store, record, index, eOpts){
			var waybillNo = record.get('waybillNo'),
				waybillId = record.get('superId'),
				serialNo = record.get('serialNo');
			//将移除的流水号从一级record的stockList中删除
			var superRec = load.handoverbillmodify.queryEnRouteWaybillGrid.store.findRecord('id',waybillId,0,false,true,true),
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
Ext.define('Foss.load.handoverbillmodify.QueryWaybillSerialNoGrid', {
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
				unsubmitedWaybill = load.handoverbillmodify.unsubmitedWaybillGrid.store.findRecord('waybillNo',waybillNo,0,false,true,true),
				unsavedWaybill = load.handoverbillmodify.allSerialNoMap.get(waybillNo);
			if(!(isPreHandOver !== 'Y'
				&& (unsubmitedWaybill === null 
						|| unsubmitedWaybill.get('serialNoMap').get(serialNo) === undefined)
					&& (unsavedWaybill === undefined
						|| unsavedWaybill.get(serialNo) === undefined))){
				return 'disabledrow';
			}
		}
	},
	columns : [ {
		xtype : 'actioncolumn',
		width : 40,
		text : load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.waybillGrid.actionColumn')/*'操作'*/,
		align : 'center',
		items : [ {
			tooltip : load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.queryResultTab.moveRight')/*'右移'*/,
			iconCls : 'foss_icons_stl_sendmes',
			handler : function(grid, rowIndex, colIndex) {
				var store = grid.store;
				if(store.getCount() === 1){
					Ext.ux.Toast.msg(load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.waybillGrid.alertInfoTitle')/*'提示'*/, '请直接移动整个运单！', 'error', 1500);
					return;
				}
				if(this.iconCls === null){
					return;
				}
				var record = grid.store.getAt(rowIndex);
					waybillNo = record.get('waybillNo'),
					serialNo = record.get('serialNo'),
					moveWeight = record.get('weight'),//每个流水号的重量体积 author:hyd 
					moveVolumn = record.get('volumn'),
					isSelected = grid.getSelectionModel().isSelected(record),
					unsubmitedWaybill = load.handoverbillmodify.unsubmitedWaybillGrid.store.findRecord('waybillNo',waybillNo,0,false,true,true),
					leftGrid = load.handoverbillmodify.queryWaybillGrid,
					waybill = leftGrid.store.findRecord('waybillNo',waybillNo,0,false,true,true),
					pieces = waybill.get('pieces'),
					weight = waybill.get('weight'),
					cubage = waybill.get('cubage'),
					weightAc = waybill.get('weightAc'),
					cubageAc = waybill.get('cubageAc');
				//如果右侧有该运单
				if(unsubmitedWaybill !== null){
					unsubmitedWaybill.set('pieces',unsubmitedWaybill.get('pieces') + 1);
					unsubmitedWaybill.set('weight',(unsubmitedWaybill.get('weight') + weight/pieces).toFixed(2));
					unsubmitedWaybill.set('cubage',(unsubmitedWaybill.get('cubage') + cubage/pieces).toFixed(2));
					unsubmitedWaybill.set('cubageAc',(unsubmitedWaybill.get('cubageAc') + moveVolumn));
					unsubmitedWaybill.set('weightAc',(unsubmitedWaybill.get('weightAc') + moveWeight));
					var tempMap = unsubmitedWaybill.get('serialNoMap');
					tempMap.replace(serialNo,record);
					var plugin = load.handoverbillmodify.unsubmitedWaybillGrid.getPlugin('handOverBillModify_unsubmitedWaybillGrid_serialNoGrid');
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
					rightWaybill.set('weight',weight/pieces.toFixed(2));
					rightWaybill.set('cubage',cubage/pieces.toFixed(2));
					rightWaybill.set('cubageAc',moveVolumn);
					rightWaybill.set('weightAc',moveWeight);
					var tempMap = new Ext.util.HashMap()
					tempMap.replace(serialNo,record);
					rightWaybill.set('serialNoMap',tempMap);
					//插入右边的表格
					var rightGrid = load.handoverbillmodify.unsubmitedWaybillGrid;
						rightStore = rightGrid.store;
					rightStore.insert(rightStore.getCount(),rightWaybill);
				}
				//移除该行
				store.removeAt(rowIndex);
				//如果当前行被选中
				if(isSelected){
					//从map中移除
					var serialNoMap = load.handoverbillmodify.selectedWaybillMap.get(waybillNo).get('serialNoMap');
					serialNoMap.removeAtKey(serialNo);
					//如果map中没有任何元素
					if(serialNoMap.getCount() ===0){
						load.handoverbillmodify.selectedWaybillMap.removeAtKey(waybillNo);
						//如果此时一级表中的行被选择，则反选
						var sm = leftGrid.getSelectionModel()
							isSelectedWaybill = sm.isSelected(waybill)
						//反选
						if(isSelectedWaybill){
							sm.deselect(waybill,true)
						}
					}
					load.handoverbillmodify.updateQueryPageStaInfo();
				}
				//修改左边表格的数据
				waybill.set('pieces',pieces - 1);
				waybill.set('weight',((weight/pieces)*waybill.get('pieces')).toFixed(2));
				waybill.set('cubage',((cubage/pieces)*waybill.get('pieces')).toFixed(2));
				waybill.set('cubageAc',cubageAc-moveVolumn);
				waybill.set('weightAc',weightAc-moveWeight);
				//重新统计已选运单
				load.handoverbillmodify.updateUnsubmitedWaybillStaInfo();
			}
		} ],
		renderer : function(value,metaData,record,rowIndex,colIndex,store,view){
			var waybillNo = record.get('waybillNo'),
				serialNo = record.get('serialNo'),
				isPreHandOver = record.get('isPreHandOver'),
				unsubmitedWaybill = load.handoverbillmodify.unsubmitedWaybillGrid.store.findRecord('waybillNo',waybillNo,0,false,true,true),
				unsavedWaybill = load.handoverbillmodify.allSerialNoMap.get(waybillNo);
			if(isPreHandOver !== 'Y'
					&& (unsubmitedWaybill === null 
						|| unsubmitedWaybill.get('serialNoMap').get(serialNo) === undefined)
					&& (unsavedWaybill === undefined
						|| unsavedWaybill.get(serialNo) === undefined)){
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
		text : load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.waybillGrid.serialNoColumn')/*'流水号'*/
	}, {
		dataIndex : 'instorageDate',
		align : 'center',
		width : 145,
		text : load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.queryWaybillForm.instorageTime')/*'入库时间'*/,
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
		text : load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.queryResultTab.isPreHandOvered')/*'是否已预配'*/,
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
		var rec = load.handoverbillmodify.selectedWaybillMap.get(waybillNo);
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
			if(load.handoverbillmodify.selectedWaybillMap.get(waybillNo) !== undefined){
				superRecord = load.handoverbillmodify.selectedWaybillMap.get(waybillNo);
			}else{
				load.handoverbillmodify.selectedWaybillMap.replace(waybillNo,superRecord);
			}
			//获取运单record中的勾选的流水号map
			var selectedSerialNoMap = superRecord.get('serialNoMap');
			if(selectedSerialNoMap !== ''){
				selectedSerialNoMap.replace(serialNo,record);
			}else{
				selectedSerialNoMap = new Ext.util.HashMap();
				selectedSerialNoMap.replace(serialNo,record);
			}
			superRecord.set('serialNoMap',selectedSerialNoMap);
			load.handoverbillmodify.updateQueryPageStaInfo();
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
				load.handoverbillmodify.selectedWaybillMap.removeAtKey(waybillNo);
			}else{//如果第二层表格记录未全部反选，则从map中的选中行中删除该流水号对应的记录
				var selectedWaybill = load.handoverbillmodify.selectedWaybillMap.get(waybillNo);
				serialNoMap = selectedWaybill.get('serialNoMap');
				serialNoMap.removeAtKey(serialNo);
			}
			load.handoverbillmodify.updateQueryPageStaInfo();
		}
	},
	constructor : function(config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.load.handoverbillmodify.QueryWaybillSerialNoStore');
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
Ext.define('Foss.load.handoverbillmodify.QueryEnRouteWaybillSerialNoGrid', {
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
				unsubmitedWaybill = load.handoverbillmodify.unsubmitedWaybillGrid.store.findRecord('waybillNo',waybillNo,0,false,true,true),
				unsavedWaybill = load.handoverbillmodify.allSerialNoMap.get(waybillNo);
			if(!((unsubmitedWaybill === null 
						|| unsubmitedWaybill.get('serialNoMap').get(serialNo) === undefined)
					&& (unsavedWaybill === undefined
						|| unsavedWaybill.get(serialNo) === undefined))){
				return 'disabledrow';
			}
		}
	},
	columns : [{
		xtype : 'actioncolumn',
		width : 40,
		text : load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.waybillGrid.actionColumn')/*'操作'*/,
		align : 'center',
		items : [ {
			tooltip : load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.queryResultTab.moveRight')/*'右移'*/,
			iconCls : 'foss_icons_stl_sendmes',
			handler : function(grid, rowIndex, colIndex) {
				var store = grid.store;
				if(store.getCount() === 1){
					Ext.ux.Toast.msg(load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.waybillGrid.alertInfoTitle')/*'提示'*/, '请直接移动整个运单！', 'error', 1500);
					return;
				}
				if(this.iconCls === null){
					return;
				}
				var record = grid.store.getAt(rowIndex);
					waybillNo = record.get('waybillNo'),
					waybillId = record.get('superId'),
					serialNo = record.get('serialNo'),
					moveWeight = record.get('weight'),// 获取流水号的重量体积 author：hyd
					moveVolumn = record.get('volumn'),
					isSelected = grid.getSelectionModel().isSelected(record),
					unsubmitedWaybill = load.handoverbillmodify.unsubmitedWaybillGrid.store.findRecord('waybillNo',waybillNo,0,false,true,true),
					leftGrid = load.handoverbillmodify.queryEnRouteWaybillGrid,
					waybill = leftGrid.store.findRecord('id',waybillId,0,false,true,true),
					pieces = waybill.get('pieces'),
					weight = waybill.get('weight'),
					cubage = waybill.get('cubage'),
					weightAc = waybill.get('weightAC'),
					cubageAc = waybill.get('cubageAc');
				//如果左边有该运单
				if(unsubmitedWaybill !== null){
					unsubmitedWaybill.set('pieces',unsubmitedWaybill.get('pieces') + 1);
					unsubmitedWaybill.set('weight',(unsubmitedWaybill.get('weight') + weight/pieces).toFixed(2));
					unsubmitedWaybill.set('cubage',(unsubmitedWaybill.get('cubage') + cubage/pieces).toFixed(2));
					var tempMap = unsubmitedWaybill.get('serialNoMap');
					tempMap.replace(serialNo,record);
					//如果右侧流水号已展开，则重新加载流水号
					var plugin = load.handoverbillmodify.unsubmitedWaybillGrid.getPlugin('handOverBillModify_unsubmitedWaybillGrid_serialNoGrid');
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
					rightWaybill.set('weightAC',(moveWeight).toFixed(3));
					rightWaybill.set('cubageAc',(moveVolumn).toFixed(3));
					var tempMap = new Ext.util.HashMap()
					tempMap.replace(serialNo,record);
					rightWaybill.set('serialNoMap',tempMap);
					//插入右边的表格
					var rightGrid = load.handoverbillmodify.unsubmitedWaybillGrid;
						rightStore = rightGrid.store;
					rightStore.insert(rightStore.getCount(),rightWaybill);
				}
				//移除该行
				store.removeAt(rowIndex);
				//如果当前行被选中
				if(isSelected){
					//从map中移除
					var serialNoMap = load.handoverbillmodify.selectedEnRouteWaybillMap.get(waybillId).get('serialNoMap');
					serialNoMap.removeAtKey(serialNo);
					//如果map中没有任何元素
					if(serialNoMap.getCount() ===0){
						load.handoverbillmodify.selectedWaybillMap.removeAtKey(waybillId);
						//如果此时一级表中的行被选择，则反选
						var sm = leftGrid.getSelectionModel()
							isSelectedWaybill = sm.isSelected(waybill)
						//反选
						if(isSelectedWaybill){
							sm.deselect(waybill,true)
						}
					}
					load.handoverbillmodify.updateQueryEnRoutePageStaInfo();
				}
				//修改左边表格的数据
				waybill.set('pieces',pieces - 1);
				waybill.set('weight',((weight/pieces)*waybill.get('pieces')).toFixed(3));
				waybill.set('cubage',((cubage/pieces)*waybill.get('pieces')).toFixed(3));
				waybill.set('weightAC',(weightAc-moveWeight).toFixed(3));
				waybill.set('cubageAc',(cubageAc-moveVolumn).toFixed(3));
			}
		} ],
		renderer : function(value,metaData,record,rowIndex,colIndex,store,view){
			var waybillNo = record.get('waybillNo'),
				serialNo = record.get('serialNo'),
				unsubmitedWaybill = load.handoverbillmodify.unsubmitedWaybillGrid.store.findRecord('waybillNo',waybillNo,0,false,true,true),
				unsavedWaybill = load.handoverbillmodify.allSerialNoMap.get(waybillNo);
			if((unsubmitedWaybill === null 
						|| unsubmitedWaybill.get('serialNoMap').get(serialNo) === undefined)
					&& (unsavedWaybill === undefined
						|| unsavedWaybill.get(serialNo) === undefined)){
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
		text : load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.waybillGrid.serialNoColumn')/*'流水号'*/
	}],
	bindData : function(record){
		var waybillNo = record.get('waybillNo'),
			grid = this,
			waybillId = record.get('id'),
			store = grid.store;
		store.loadData(record.get('serialNoHandOveredList'));
		//如果之前被勾选，则勾选上之前选中的流水号
		var rec = load.handoverbillmodify.selectedEnRouteWaybillMap.get(waybillId);
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
			if(load.handoverbillmodify.selectedEnRouteWaybillMap.get(superId) !== undefined){
				superRecord = load.handoverbillmodify.selectedEnRouteWaybillMap.get(superId);
			}else{
				load.handoverbillmodify.selectedEnRouteWaybillMap.replace(superId,superRecord);
			}
			//获取运单record中的勾选的流水号map
			var selectedSerialNoMap = superRecord.get('serialNoMap');
			if(selectedSerialNoMap !== ''){
				selectedSerialNoMap.replace(serialNo,record);
			}else{
				selectedSerialNoMap = new Ext.util.HashMap();
				selectedSerialNoMap.replace(serialNo,record);
			}
			superRecord.set('serialNoMap',selectedSerialNoMap);
			load.handoverbillmodify.updateQueryEnRoutePageStaInfo();
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
				load.handoverbillmodify.selectedEnRouteWaybillMap.removeAtKey(superId);
			}else{//如果第二层表格记录未全部反选，则从第二层map中的value中删除该流水号对应的map
				var selectedWaybill = load.handoverbillmodify.selectedEnRouteWaybillMap.get(superId);
				serialNoMap = selectedWaybill.get('serialNoMap');
				serialNoMap.removeAtKey(serialNo);
			}
			load.handoverbillmodify.updateQueryEnRoutePageStaInfo();
		}
	},
	constructor : function(config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.load.handoverbillmodify.QueryEnRouteWaybillSerialNoStore');
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
Ext.define('Foss.load.handoverbillmodify.QueryEnRouteWaybillGrid', {
	extend : 'Ext.grid.Panel',
	height : 603,
	columnLines: true,
	bodyCls : 'autoHeight',
	viewConfig : {
		loadMask : false
	},
	cls : 'tworowbbargirdpanel',
	autoScroll : true,
	emptyText : load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.queryResultTab.emptyText')/*'查询结果为空'*/,
	//collapsible : true,
	animCollapse : true,
	dockedItems: [{
	    xtype : 'toolbar',
	    dock : 'bottom',
	    layout : 'column',
	    defaults : {
			xtype : 'textfield',
			readOnly : true,
			labelWidth : 120
		},
		items: [{
				id : 'Foss_load_handOverBillModify_EnRouteQueryPageTotalPieces',
				fieldLabel : load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.queryResultTab.totalPieces')/*'已选总件数'*/,
				columnWidth : 1/3,
				value : 0
			},{
				id : 'Foss_load_handOverBillModify_EnRouteQueryPageTotalWeight',
				fieldLabel : '已选总重量(千克)',
				columnWidth : 1/3,
				value : 0
			},{
				id : 'Foss_load_handOverBillModify_EnRouteQueryPageTotalCubage',
				fieldLabel : '已选总体积(方)',
				columnWidth : 1/3,
				value : 0
			},{
				id : 'Foss_load_handOverBillModify_EnRouteQueryPageUnconvertTotalCubage',
				fieldLabel : '已选未转换总体积(方)',
				columnWidth : 1/2,
				labelWidth : 140,
				value : 0
			},{
				id : 'Foss_load_handOverBillModify_EnRouteQueryPageTotalMoney',
				fieldLabel: load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.queryResultTab.totalMoney')/*'已选总金额'*/,
				columnWidth : 1/2,
				value : 0
			}]
	  }],
	  constructor: function(config){
			var me = this,
			cfg = Ext.apply({}, config);
			me.store = Ext.create('Foss.load.handoverbillmodify.QueryEnRouteWaybillStore');
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
		load.handoverbillmodify.enRoutePagingBar = me.bbar;
		me.callParent([cfg]);
	},
	tbar : [{
		xtype : 'button',
		text : load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.queryResultTab.changeQueryWayButton')/*'更改查询方案'*/,
		handler : function(){
			if(load.handoverbillmodify.AddAssembleDeptWindow == null){
				load.handoverbillmodify.AddAssembleDeptWindow = Ext.create('Foss.load.handoverbillmodify.AddAssembleDeptWindow');
			}
			load.handoverbillmodify.AddAssembleDeptWindow.show();
		}
	}],
	// 定义行展开
	plugins : [ {
		header : true,
		ptype : 'rowexpander',
		// 定义行展开模式（单行与多行），默认是多行展开(值true)
		pluginId : 'handOverBillModify_queryEnRouteWaybillGrid_serialNoGrid',
		rowsExpander : false,
		// 行体内容
		rowBodyElement : 'Foss.load.handoverbillmodify.QueryEnRouteWaybillSerialNoGrid'
	} ],
	rightMove : function(grid,record,rowIndex,type){
		var waybillNo = record.get('waybillNo'),
			waybillId = record.get('id'),
			unsubmitedGrid = load.handoverbillmodify.unsubmitedWaybillGrid,
			unStore = unsubmitedGrid.store,
			unRec = unStore.findRecord('waybillNo',waybillNo,0,false,true,true),
			//获取主页面流水号map
			unsavedWaybill = load.handoverbillmodify.allSerialNoMap.get(waybillNo);
			serialNoList = record.get('serialNoHandOveredList'),
			flag = false;
		//如果已勾选运单map中有此运单，则移除
		if(load.handoverbillmodify.selectedEnRouteWaybillMap.get(waybillId) !== undefined){
			load.handoverbillmodify.selectedEnRouteWaybillMap.removeAtKey(waybillId);
			flag = true;
		}
		//右侧grid中是否有该运单
		if(unRec === null){
			var serialNoMap = new Ext.util.HashMap();
			for(var i in serialNoList){
				var serialNo = serialNoList[i];
				//如果主页面没有的流水号，并且是未预配状态，则可右移
				if(unsavedWaybill === undefined || unsavedWaybill.get(serialNo.serialNo) === undefined){
					var serialNoRecord = Ext.ModelManager.create(serialNo, 'Foss.load.handoverbillmodify.serialNoModel');
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
				//实际重量，体积
				var weightAc = 0, cubageAc = 0; 
				serialNoMap.each(function(serial,serialNo,length){
					weightAc += serialNo.get('weight');
					cubageAc += serialNo.get('volumn');
				});
				recCopy.set('weightAc',weightAc);
				recCopy.set('cubageAc',cubageAc);
				//累加 总重量 和总体积 
				recCopy.set('pieces',pieces);
				unStore.insert(unStore.getCount(),recCopy);
			}
		}else{
			var serialNoMap = unRec.get('serialNoMap');
			for(var i in serialNoList){
				var serialNo = serialNoList[i],
					isPreHandOver = serialNo.isPreHandOver;
				//如果主页面没有的流水号，并且是未预配状态，则可右移
				if(unsavedWaybill === undefined || unsavedWaybill.get(serialNo.serialNo) === undefined){
					var serialNoRecord = Ext.ModelManager.create(serialNo, 'Foss.load.handoverbillmodify.serialNoModel');
					serialNoMap.replace(serialNo.serialNo,serialNoRecord);
				}
			}
			//更新件数、重量、体积
			var pieces = serialNoMap.getCount();
			unRec.set('weight',((unRec.get('weight')/unRec.get('pieces'))*pieces).toFixed(3));
			unRec.set('cubage',((unRec.get('cubage')/unRec.get('pieces'))*pieces).toFixed(3));
			unRec.set('pieces',serialNoMap.getCount());
			//实际重量，体积
			var weightAc = 0, cubageAc = 0; 
			serialNoMap.each(function(serial,serialNo,length){
				weightAc += serialNo.get('weight');
				cubageAc += serialNo.get('volumn');
			});
			unRec.set('weightAc',weightAc);
			unRec.set('cubageAc',cubageAc);
		}
		//如果是单条移除，则逐条移除
		if(type === 'ONE'){
			//移除该运单
			grid.store.removeAt(rowIndex);
			//如果勾选map有变动，则重新统计信息
			if(flag){
				load.handoverbillmodify.updateQueryEnRoutePageStaInfo();
			}
		}
		//重新统计已选运单
		load.handoverbillmodify.updateUnsubmitedWaybillStaInfo();
		return flag;
	},
	columns : [{
		xtype : 'actioncolumn',
		width : 40,
		text : load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.waybillGrid.actionColumn')/*'操作'*/,
		align : 'center',
		items : [ {
			tooltip : load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.queryResultTab.moveRight')/*'右移'*/,
			iconCls : 'foss_icons_stl_sendmes',
			handler : function(grid, rowIndex, colIndex) {
				var record = grid.store.getAt(rowIndex);
				load.handoverbillmodify.queryEnRouteWaybillGrid.rightMove(grid,record,rowIndex,'ONE');
			}
		} ]
	}, {
		dataIndex : 'waybillNo',
		align : 'center',
		width : 80,
		xtype : 'ellipsiscolumn',
		text : load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.waybillGrid.waybillNoColumn')/*'运单号'*/
	}, {
		dataIndex : 'transProperty',
		align : 'center',
		width : 95,
		text : load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.waybillGrid.transPropertyColumn')/*'运输性质'*/
	}, {
		dataIndex : 'transPropertyCode',
		align : 'center',
		hidden : true, 
		width : 95,
		text : '运输性质编码'/*'运输性质'*/
	}, {
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
		text : load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.waybillGrid.goodsNameColumn')/*'货物名称'*/
	}, {
		dataIndex : 'packing',
		align : 'center',
		width : 60,
		xtype : 'ellipsiscolumn',
		text : load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.waybillGrid.packingColumn')/*'包装'*/
	}, {
		dataIndex : 'pieces',
		align : 'center',
		width : 60,
		xtype : 'ellipsiscolumn',
		text : load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.waybillGrid.piecees')/*'件数'*/
	}, {
		dataIndex : 'weight',
		align : 'center',
		width : 60,
		xtype : 'ellipsiscolumn',
		text : load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.waybillGrid.weight')/*'重量'*/
	}, {
		dataIndex : 'cubage',
		align : 'center',
		width : 60,
		xtype : 'ellipsiscolumn',
		text : load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.waybillGrid.volume')/*'体积'*/
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
		text : load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.basicInfoForm.arriveDeptLabel')/*'到达部门'*/
	}, {
		dataIndex : 'insuranceValue',
		align : 'center',
		width : 60,
		xtype : 'ellipsiscolumn',
		text : load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.waybillGrid.insuranceValue')/*'保险价值'*/
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
		text : load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.waybillGrid.waybillPieces')/*'开单件数'*/
	}, {
		dataIndex : 'isPreciousGoods',
		align : 'center',
		width : 80,
		text : load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.waybillGrid.isPreciousGoods')/*'是否贵重物品'*/,
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
		text : load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.waybillGrid.waybillNoteColumn')/*'运单备注'*/
	} ],
	getRightMenu : function(record,rowIndex){
		var grid = this;
		function rightMoveOne(){
			grid.rightMove(grid,record,rowIndex,'ONE');
		}
		var	rightMenu =	new Ext.menu.Menu({
	        items : [{
                text : load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.queryResultTab.moveRight')/*'右移'*/,
                handler : rightMoveOne
	        }]
	     });
		 return rightMenu;
	},
	listeners : {
		'select' : function(rowModel, record, index, eOpts ){
			var grid = this,
				plugin = grid.getPlugin('handOverBillModify_queryEnRouteWaybillGrid_serialNoGrid'),
				waybillNo = record.get('waybillNo'),
				waybillId = record.get('id'),
				unsavedWaybill = load.handoverbillmodify.allSerialNoMap.get(waybillNo);
				unsubmitedWaybill = load.handoverbillmodify.unsubmitedWaybillGrid.store.findRecord('waybillNo',waybillNo,0,false,true,true);
			//取出record中的流水号list，做成map，放到第一层map的record中
			var serialNoStockList = record.get('serialNoHandOveredList'),
				serialNoStockMap = new Ext.util.HashMap();
			for(var i in serialNoStockList){
				var serialNo = serialNoStockList[i];
				if((unsavedWaybill !== undefined && unsavedWaybill.get(serialNo.serialNo) !== undefined)
					||(unsubmitedWaybill != null && unsubmitedWaybill.get('serialNoMap') !== '' && unsubmitedWaybill.get('serialNoMap').get(serialNo.serialNo) !== undefined)){
					continue;
				}
				var serialNoRecord = Ext.ModelManager.create(serialNoStockList[i], 'Foss.load.handoverbillmodify.serialNoModel');
				serialNoStockMap.replace(serialNo.serialNo,serialNoRecord);
			}
			if(serialNoStockMap.getCount() !== 0){
				var recCopy = record.copy();
				recCopy.set('serialNoMap',serialNoStockMap);
				//将运单置于已勾选运单map中
				load.handoverbillmodify.selectedEnRouteWaybillMap.replace(waybillId,recCopy);
				load.handoverbillmodify.updateQueryEnRoutePageStaInfo();
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
			plugin = grid.getPlugin('handOverBillModify_queryEnRouteWaybillGrid_serialNoGrid');
		if(!Ext.isEmpty(plugin.getExpendRow())) {
			var item = plugin.getExpendRowBody();
			var store = item.getStore();
			var superId = store.getAt(0).get('superId');
			if(superId == waybillId){
				item.getSelectionModel().deselectAll(true);
			}
		}	
		if(load.handoverbillmodify.selectedEnRouteWaybillMap.get(waybillId) != null){
			// 从map中移除和此运单对应的库存信息
			load.handoverbillmodify.selectedEnRouteWaybillMap.removeAtKey(waybillId);
		}
		record.set('serialNoMap','');
		load.handoverbillmodify.updateQueryEnRoutePageStaInfo();
	},
	'itemcontextmenu' : function(view,record,item,index,e, eOpts ){
		var menu = this.getRightMenu(record,index);
		e.preventDefault();
       	menu.showAt(e.getXY());
	}
}
});

//增加配载部门Model
Ext.define('Foss.load.handoverbillmodify.AddAssembleDeptModel', {
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
Ext.define('Foss.load.handoverbillmodify.AddAssembleDeptStore', {
	extend : 'Ext.data.Store',
	// 绑定一个模型
	model : 'Foss.load.handoverbillmodify.AddAssembleDeptModel',
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
});

//定义增加配载部门grid
Ext.define('Foss.load.handoverbillmodify.AddAssembleDeptGrid', {
	extend : 'Ext.grid.Panel',
	columnLines: true,
	title : load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.changeAssembleWayWindow.gridTitle')/*'配载部门列表'*/,
	frame: true,
	height : 270,
	autoScroll : true,
	store : Ext.create('Foss.load.handoverbillmodify.AddAssembleDeptStore'),
	columns : [ {
		xtype : 'actioncolumn',
		width : 80,
		text : load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.waybillGrid.actionColumn')/*'操作'*/,
		align : 'center',
		items : [ {
			tooltip : load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.waybillGrid.deleteButtonColumn')/*'删除'*/,
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
		text : load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.changeAssembleWayWindow.assembleDeptColumn')/*'配载部门'*/
	} ]
});

//定义增加配载部门form
Ext.define('Foss.load.handoverbillmodify.AddAssembleDeptForm', {
	extend : 'Ext.form.Panel',
	title : load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.changeAssembleWayWindow.addAssembleDept')/*'增加配载部门'*/,
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
		fieldLabel : load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.changeAssembleWayWindow.assembleDeptColumn')/*'配载部门'*/,
		name : 'assembleDeptCode',
		xtype : 'dynamicorgcombselector',
		type : 'ORG',
		transferCenter : 'Y',
		columnWidth : 3/4,
		allowBlank : false
	},{
		xtype : 'button',
		text : load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.changeAssembleWayWindow.addButton')/*'添加'*/,
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
				var store = load.handoverbillmodify.addAssembleDeptGrid.store;
				if(store.findRecord('assembleDeptCode',deptCode,0,false,true,true) != null){
					Ext.ux.Toast.msg(load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.waybillGrid.alertInfoTitle')/*'提示'*/, '该部门已经被添加！', 'error', 1000);
				}else{
					var deptInfo = {
						'assembleDeptCode' : deptCode,
						'assembleDeptName' : deptName
					};
					var deptInfoRecord = Ext.ModelManager.create(deptInfo,'Foss.load.handoverbillmodify.AddAssembleDeptModel');
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
Ext.define('Foss.load.handoverbillmodify.AddAssembleDeptWindow', {
	extend : 'Ext.window.Window',
	closeAction : 'hide',
	modal : true,
	width : 400,
	height : 500,
	title : load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.queryResultTab.changeQueryWayButton')/*'更改查询方案'*/,
	addAssembleDeptForm : null,
	getAddAssembleDeptForm : function(){
		if(this.addAssembleDeptForm==null){
			this.addAssembleDeptForm = Ext.create('Foss.load.handoverbillmodify.AddAssembleDeptForm');
			load.handoverbillmodify.addAssembleDeptForm = this.addAssembleDeptForm;
		}
		return this.addAssembleDeptForm;
	},
	addAssembleDeptGrid : null,
	getAddAssembleDeptGrid : function(){
		if(this.addAssembleDeptGrid==null){
			this.addAssembleDeptGrid = Ext.create('Foss.load.handoverbillmodify.AddAssembleDeptGrid');
			load.handoverbillmodify.addAssembleDeptGrid = this.addAssembleDeptGrid;
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
		text : load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.changeAssembleWayWindow.confirmButton')/*'确定'*/,
		cls : 'yellow_button',
		handler : function(){
			this.ownerCt.ownerCt.close();
		}
	}]
});

//按钮panel
Ext.define('Foss.load.handoverbillmodify.moveButtonPanel', {
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
			 margin:'265 0 0 10',
			 tooltip : load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.moveButtonPanel.moveRightAllToolTip')/*'点击可将左侧全部运单移动到右侧'*/,
		     handler: function() {
		    	 me.rightMoveAll();
		     }
		},{
			iconCls : 'deppon_icons_turnright',
			tooltip : '点击可将左侧选中的运单、流水号移动到右侧',
		    handler: function() {
		        me.rightMove();
		    }
		},{
			iconCls : 'deppon_icons_turnleft',
			tooltip : '点击可将右侧选中的运单移动到左侧',
		    handler: function() {
		    	me.leftMove();
		    }
		},{
			iconCls : 'deppon_icons_turnleftall',
			tooltip : load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.moveButtonPanel.moveLeftAllToolTip')/*'点击可将右侧全部运单移动到左侧'*/,
		    handler: function() {
		    	me.leftMoveAll();
		    }
		}]
		me.callParent([cfg]);
	},
	rightMove : function(){
		var me = this,
			waybillGrid,
			unsubmitedGrid = load.handoverbillmodify.unsubmitedWaybillGrid,
			unStore = unsubmitedGrid.store;
		//判断当前激活的是库存tab还是在途tab
		if(load.handoverbillmodify.queryWaybillTabPanel.getActiveTab().title == load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.queryResultTab.tab1Title')/*'库存货物'*/){
			waybillGrid = load.handoverbillmodify.queryWaybillGrid,
				waybillMap = load.handoverbillmodify.selectedWaybillMap;
			if(waybillMap.getCount() === 0){
				Ext.ux.Toast.msg(load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.waybillGrid.alertInfoTitle')/*'提示'*/, '左侧表格没有勾选任何库存运单！', 'error', 2000);
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
						leftWaybillRec.set('pieces',pieces - movePieces);
						leftWaybillRec.set('weightAc',(leftWaybillRec.get('weightAc')-moveWeight));
						leftWaybillRec.set('cubageAc',(leftWaybillRec.get('cubageAc')-moveVolumn));
						//如果左侧流水号已展开，则重新加载流水号
						var plugin = waybillGrid.getPlugin('handOverBillModify_queryWaybillGrid_serialNoGrid');
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
						recCopy.set('pieces',pieces);
						recCopy.set('weightAc',moveWeight);
						recCopy.set('cubageAc',moveVolumn);
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
						//再次累加 重量和体积
						moveWeight = 0 ; moveVolumn = 0 ;
						rightSerialNoMap.each(function(serial,serialNo,length){
							moveWeight += serialNo.get('weight');
							moveVolumn += serialNo.get('volumn');
						});
						rightWaybillRec.set('weightAc',moveWeight);
						rightWaybillRec.set('cubageAc',moveVolumn);
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
						recCopy.set('pieces',pieces);
						recCopy.set('weightAc',moveWeight.toFixed(3));
						recCopy.set('cubageAc',moveVolumn.toFixed(3));
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
						//再次累加 重量和体积
						moveWeight = 0 ; moveVolumn = 0 ;
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
			load.handoverbillmodify.selectedWaybillMap.clear();
			load.handoverbillmodify.updateQueryPageStaInfo();
			load.handoverbillmodify.updateUnsubmitedWaybillStaInfo();
		}else{
			waybillGrid = load.handoverbillmodify.queryEnRouteWaybillGrid,
				waybillMap = load.handoverbillmodify.selectedEnRouteWaybillMap;
			if(waybillMap.getCount() === 0){
				Ext.ux.Toast.msg(load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.waybillGrid.alertInfoTitle')/*'提示'*/, '左侧表格没有勾选任何在途运单！', 'error', 2000);
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
						leftWaybillRec.set('pieces',pieces - movePieces);
						leftWaybillRec.set('weightAc',(leftWaybillRec.get('weightAc')-moveWeight).toFixed(3));
						leftWaybillRec.set('cubageAc',(leftWaybillRec.get('cubageAc')-moveVolumn).toFixed(3));
						//如果左侧流水号已展开，则重新加载流水号
						var plugin = waybillGrid.getPlugin('handOverBillModify_queryEnRouteWaybillGrid_serialNoGrid');
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
						recCopy.set('pieces',pieces);
						recCopy.set('weightAc',moveWeight);
						recCopy.set('cubageAc',moveVolumn);
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
						rightWaybillRec.set('pieces',rightSerialNoMap.getCount());
						//再次累加 重量和体积
						moveWeight = 0 ; moveVolumn = 0 ;
						rightSerialNoMap.each(function(serial,serialNo,length){
							moveWeight += serialNo.get('weight');
							moveVolumn += serialNo.get('volumn');
						});
						rightWaybillRec.set('weightAc',moveWeight.toFixed(3));
						rightWaybillRec.set('cubageAc',moveVolumn.toFixed(3));
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
						recCopy.set('pieces',pieces);
						recCopy.set('weightAc',moveWeight);
						recCopy.set('cubageAc',moveVolumn);
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
						rightWaybillRec.set('pieces',rightSerialNoMap.getCount());
						//再次累加 重量和体积
						moveWeight = 0 ; moveVolumn = 0 ;
						rightSerialNoMap.each(function(serial,serialNo,length){
							moveWeight += serialNo.get('weight');
							moveVolumn += serialNo.get('volumn');
						});
						rightWaybillRec.set('weightAc',moveWeight.toFixed(3));
						rightWaybillRec.set('cubageAc',moveVolumn.toFixed(3));
					}
				}
			});
			//清空map
			load.handoverbillmodify.selectedEnRouteWaybillMap.clear();
			load.handoverbillmodify.updateQueryEnRoutePageStaInfo();
			load.handoverbillmodify.updateUnsubmitedWaybillStaInfo();
		}
	},
	rightMoveAll : function(){
		var me = this,
			waybillGrid,
			unsubmitedGrid = load.handoverbillmodify.unsubmitedWaybillGrid,
			unStore = unsubmitedGrid.store;
		//判断当前激活的是库存tab还是在途tab
		if(load.handoverbillmodify.queryWaybillTabPanel.getActiveTab().title == load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.queryResultTab.tab1Title')/*'库存货物'*/){
			var waybillGrid = load.handoverbillmodify.queryWaybillGrid,
				store = waybillGrid.store,
				flag = false;
			if(store.getCount() === 0){
				Ext.ux.Toast.msg(load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.waybillGrid.alertInfoTitle')/*'提示'*/, '库存运单不可为空！', 'error', 1000);
				return;
			}
			//遍历库存运单当前页
			var flag = false;
			store.each(function(record){
				//获取运单号
				var waybillNo = record.get('waybillNo');
				//获取行索引
				var rowIndex = store.indexOf(record);
				if(waybillGrid.rightMove(waybillGrid,record,rowIndex,'MANY')){
					flag = true;
				};
			});
			store.removeAll();
			//如果移除的运单中有勾选的运单，则重新计算统计信息
			if(flag){
				load.handoverbillmodify.updateQueryPageStaInfo();
			}
		}else{
			waybillGrid = load.handoverbillmodify.queryEnRouteWaybillGrid;
			store = waybillGrid.store;
			if(store.getCount() === 0){
				Ext.ux.Toast.msg(load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.waybillGrid.alertInfoTitle')/*'提示'*/, '在途运单不可为空！', 'error', 1000);
				return;
			}
			//遍历在途运单当前页
			var flag = false;
			store.each(function(record){
				//获取运单号
				var waybillNo = record.get('waybillNo');
				//获取行索引
				var rowIndex = store.indexOf(record);
				if(waybillGrid.rightMove(waybillGrid,record,rowIndex,'MANY')){
					flag = true;
				};
			});
			store.removeAll();
			//如果移除的运单中有勾选的运单，则重新计算统计信息
			if(flag){
				load.handoverbillmodify.updateQueryEnRoutePageStaInfo();
			}
		}
	},
	leftMove : function(){
		var me = this,
			unsubmitedGrid = load.handoverbillmodify.unsubmitedWaybillGrid,
			unStore = unsubmitedGrid.store,
			selectedWaybillList = unsubmitedGrid.getSelectionModel().getSelection();
		if(selectedWaybillList.length === 0){
			Ext.ux.Toast.msg(load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.waybillGrid.alertInfoTitle')/*'提示'*/, '未勾选右侧任何运单！', 'error', 1500);
			return;
		}
		for(var i in selectedWaybillList){
			var waybill = selectedWaybillList[i];
			unsubmitedGrid.leftMove(waybill);
		}
	},
	leftMoveAll : function(){
		var me = this,
			unsubmitedGrid = load.handoverbillmodify.unsubmitedWaybillGrid,
			unStore = unsubmitedGrid.store;
		if(unStore.getCount() === 0){
			Ext.ux.Toast.msg(load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.waybillGrid.alertInfoTitle')/*'提示'*/, '右侧没有任何运单！', 'error', 1500);
			return;
		}
		unStore.each(function(record){
			unsubmitedGrid.leftMove(record,'MANY');
		});
		unStore.removeAll();
		load.handoverbillmodify.updateUnsubmitedWaybillStaInfo();
	}
});

//定义查询库存运单和在途运单的tabPanel
Ext.define('Foss.load.handoverbillmodify.QueryWayBillTabPanel', {
	extend : 'Ext.tab.Panel',
	bodyCls : 'autoHeight',
	flex : 1,
    cls: 'innerTabPanel',
    queryWaybillGrid: null,
	getQueryWaybillGrid: function(){
		if(this.queryWaybillGrid==null){
			this.queryWaybillGrid = Ext.create('Foss.load.handoverbillmodify.QueryWaybillGrid',{
				title: load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.queryResultTab.tab1Title')/*'库存货物'*/,
		        tabConfig: {
		            tooltip: load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.queryResultTab.tab1ToolTip')/*'查询部门库存货物'*/,
		            width:100
		        }
			});
			load.handoverbillmodify.queryWaybillGrid = this.queryWaybillGrid;
		}
		return this.queryWaybillGrid;
	},
	queryEnRouteWaybillGrid: null,
	getQueryEnRouteWaybillGrid: function(){
		if(this.queryEnRouteWaybillGrid==null){
			this.queryEnRouteWaybillGrid = Ext.create('Foss.load.handoverbillmodify.QueryEnRouteWaybillGrid',{
				title: load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.queryResultTab.tab2Title')/*'在途货物'*/,
		        tabConfig: {
		            tooltip: load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.queryResultTab.tab2ToolTip')/*'查询将到达本部的在途货物'*/,
		            width:100
		        }
			});
			load.handoverbillmodify.queryEnRouteWaybillGrid = this.queryEnRouteWaybillGrid;
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
			if(newCard.title == load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.queryResultTab.tab1Title')/*'库存货物'*/){
				Ext.getCmp('Foss_handOverBillModify_QueryForm_InstorageTime_ID').setVisible(true);
			}else{
				Ext.getCmp('Foss_handOverBillModify_QueryForm_InstorageTime_ID').setVisible(false);
			}
		},
		'beforetabchange' : function(tabPanel,newCard,oldCard,eOpts){
			if(newCard.title == load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.queryResultTab.tab2Title')/*'在途货物'*/ && !load.handoverbillmodify.basicInfoForm.getForm().findField('isPreHandOverBill').getValue()){
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
Ext.define('Foss.load.handoverbillmodify.UnSubmitedWaybillStore', {
	extend : 'Ext.data.Store',
	// 绑定一个模型
	model : 'Foss.load.handoverbillmodify.waybillStockModel',
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
});

//定义待提交流水号的store
Ext.define('Foss.load.handoverbillmodify.UnSubmitedSerialNoStore', {
	extend : 'Ext.data.Store',
	// 绑定一个模型
	model : 'Foss.load.handoverbillmodify.serialNoModel',
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
});

//定义待提交运单中流水号列表grid
Ext.define('Foss.load.handoverbillmodify.unsubmitedWaybillSerialNoGrid', {
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
		text : load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.waybillGrid.actionColumn')/*'操作'*/,
		align : 'center',
		items : [ {
			tooltip : load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.moveButtonPanel.moveLeft')/*'左移'*/,
			iconCls : 'deppon_icons_remove',
			handler : function(grid, rowIndex, colIndex) {
				var store= grid.store,
					record = store.getAt(rowIndex),
					waybillNo = record.get('waybillNo'),
					superRec = load.handoverbillmodify.unsubmitedWaybillGrid.store.findRecord('waybillNo',waybillNo,0,false,true,true),
					serialNo = record.get('serialNo'),
					waybillId = record.get('superId'),
					sweight = record.get('weight'),
					svolumn = record.get('volumn'),
					handOverBillNo = record.get('handOverBillNo'),
					oneWeight = superRec.get('weight')/superRec.get('pieces'),
					oneCubage = superRec.get('cubage')/superRec.get('pieces'),
					isInStorage = record.get('isInStorage');
				if(store.getCount() === 1){
					Ext.ux.Toast.msg(load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.waybillGrid.alertInfoTitle')/*'提示'*/, '请左移整条运单！!', 'error', 1500);
					return;
				}
				store.removeAt(rowIndex);
				//从左侧表格的流水号map中删除该流水号
				superRec.get('serialNoMap').removeAtKey(serialNo);
				//如果移除的是库存流水号
				if(isInStorage === 1){
					//左侧是否有该运单
					var leftGrid = load.handoverbillmodify.queryWaybillGrid,
						leftWaybill = leftGrid.store.findRecord('waybillNo',waybillNo,0,false,true,true);
					//如果没有，则新建一条，插入
					if(leftWaybill === null){
						var recCopy = superRec.copy();
						recCopy.set('pieces',1);
						recCopy.set('weight',oneWeight.toFixed(2));
						recCopy.set('cubage',oneCubage.toFixed(2));
						recCopy.set('weightAc',sweight.toFixed(3));
						recCopy.set('cubageAc',svolumn.toFixed(3));
						var serialNoList = [];
						serialNoList.push(record.data);
						recCopy.set('serialNoStockList',serialNoList);
						leftGrid.store.insert(leftGrid.store.getCount(),recCopy);
					}else{
						//获取左侧运单的流水号list
						var leftSerialNoList = leftWaybill.get('serialNoStockList');
						if(!load.handoverbillmodify.inArray(leftSerialNoList,serialNo)){
							leftSerialNoList.push(record.data);
							leftWaybill.set('pieces',leftWaybill.get('pieces') + 1);
							leftWaybill.set('weight',(leftWaybill.get('weight') + oneWeight).toFixed(3));
							leftWaybill.set('cubage',(leftWaybill.get('cubage') + oneCubage).toFixed(3));
							leftWaybill.set('weightAc',(leftWaybill.get('weightAc') + sweight).toFixed(3));
							leftWaybill.set('cubageAc',(leftWaybill.get('cubageAc') + svolumn).toFixed(3));
						}
						//如果左侧流水号已展开，则重新加载流水号
						var plugin = leftGrid.getPlugin('handOverBillModify_queryWaybillGrid_serialNoGrid');
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
					var leftGrid = load.handoverbillmodify.queryEnRouteWaybillGrid,
						leftWaybill = leftGrid.store.findRecord('id',waybillId,0,false,true,true);
					//如果没有，则新建一条，插入
					if(leftWaybill === null){
						var recCopy = superRec.copy();
						recCopy.set('pieces',1);
						recCopy.set('id',waybillId);
						recCopy.set('handOverBillNo',handOverBillNo);
						recCopy.set('weight',oneWeight.toFixed(2));
						recCopy.set('cubage',oneCubage.toFixed(2));
						recCopy.set('weightAc',sweight.toFixed(3));
						recCopy.set('cubageAc',svolumn.toFixed(3));
						var serialNoList = [];
						serialNoList.push(record.data);
						recCopy.set('serialNoHandOveredList',serialNoList);
						leftGrid.store.insert(leftGrid.store.getCount(),recCopy);
					}else{
						//获取左侧运单的流水号list
						var leftSerialNoList = leftWaybill.get('serialNoHandOveredList');
						if(!load.handoverbillmodify.inArray(leftSerialNoList,serialNo)){
							leftSerialNoList.push(record.data);
							leftWaybill.set('pieces',leftWaybill.get('pieces') + 1);
							leftWaybill.set('weight',(leftWaybill.get('weight') + oneWeight).toFixed(3));
							leftWaybill.set('cubage',(leftWaybill.get('cubage') + oneCubage).toFixed(3));
							leftWaybill.set('weightAc',(leftWaybill.get('weightAc') + sweight).toFixed(3));
							leftWaybill.set('cubageAc',(leftWaybill.get('cubageAc') + svolumn).toFixed(3));
						}
						//如果左侧流水号已展开，则重新加载流水号
						var plugin = leftGrid.getPlugin('handOverBillModify_queryEnRouteWaybillGrid_serialNoGrid');
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
				superRec.set('weightAc',(superRec.get('weightAc') - sweight).toFixed(3));
				superRec.set('cubageAc',(superRec.get('cubageAc') - svolumn).toFixed(3));
				//重新统计已选运单
				load.handoverbillmodify.updateUnsubmitedWaybillStaInfo();
			}
		} ]
	}, {
		dataIndex : 'serialNo',
		align : 'center',
		width : 120,
		xtype : 'ellipsiscolumn',
		text : load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.waybillGrid.serialNoColumn')/*'流水号'*/
	}],
	bindData : function(record){
		var grid = this;
		grid.store.loadData(record.get('serialNoMap').getValues());
		console.log(grid.store);
	},
	constructor : function(config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.load.handoverbillmodify.UnSubmitedSerialNoStore');
		me.callParent([cfg]);
	}
});

//定义待提交运单的grid
Ext.define('Foss.load.handoverbillmodify.UnSubmitedWaybillGrid', {
	extend : 'Ext.grid.Panel',
	title : load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.leftGrid.gridTitle')/*'已选运单列表'*/,
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
		pluginId : 'handOverBillModify_unsubmitedWaybillGrid_serialNoGrid',
		rowsExpander : false,
		// 行体内容
		rowBodyElement : 'Foss.load.handoverbillmodify.unsubmitedWaybillSerialNoGrid'
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
		me.store = Ext.create('Foss.load.handoverbillmodify.UnSubmitedWaybillStore');
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
			readOnly : true,
			labelWidth : 120
		},
		items: [{
			id : 'Foss_load_handOverBillModify_UnsubmitedWaybillTotalPieces',
			fieldLabel : load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.waybillGrid.totalPiecesLabel')/*'总件数'*/,
			columnWidth : 1/3,
			value : 0
		},{
			id : 'Foss_load_handOverBillModify_UnsubmitedWaybillTotalWeight',
			fieldLabel: '总重量(千克)',
			columnWidth : 1/3,
			value : 0
		},{
			id : 'Foss_load_handOverBillModify_UnsubmitedWaybillTotalCubage',
			fieldLabel: '总体积(方)',
			columnWidth : 1/3,
			value : 0
		},{
			id : 'Foss_load_handOverBillModify_UnsubmitedWaybillUnconvertTotalCubage',
			fieldLabel: '未转换总体积(方)',
			columnWidth : 1/2,
			labelWidth : 140,
			value : 0
		},{
			id : 'Foss_load_handOverBillModify_UnsubmitedWaybilleTotalMoney',
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
			oneWeight = record.get('weight')/record.get('pieces'),
			oneCubage = record.get('cubage')/record.get('pieces'),
			waybillNo = record.get('waybillNo');
		
		if(type !== 'MANY'){
			grid.store.remove(record);
			//重新统计已选运单
			load.handoverbillmodify.updateUnsubmitedWaybillStaInfo();
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
		if(stockSerialNoMap.getCount() !== 0){//TODO
			//左侧是否有该运单
			var leftGrid = load.handoverbillmodify.queryWaybillGrid,
				leftWaybill = leftGrid.store.findRecord('waybillNo',waybillNo,0,false,true,true);
			//实际重量，实际体积
			var weightAc = 0, cubageAc = 0;
			//循环累加每个流水号的重量体积
			stockSerialNoMap.each(function(serial,serialNoData,length){
				weightAc += serialNoData.weight;
				cubageAc += serialNoData.volumn;
			});
			//如果没有，则新建一条，插入
			if(leftWaybill === null){
				var recCopy = record.copy();
				recCopy.set('pieces',stockSerialNoMap.getCount());
				recCopy.set('weight',(oneWeight*record.get('pieces')).toFixed(3));
				recCopy.set('cubage',(oneCubage*record.get('pieces')).toFixed(3));
				recCopy.set('serialNoStockList',stockSerialNoMap.getValues());
				
				recCopy.set('weightAc',weightAc);
				recCopy.set('weightAc',cubageAc);
				
				leftGrid.store.insert(leftGrid.store.getCount(),recCopy);
			}else{
				//获取左侧运单的流水号list
				var leftSerialNoList = leftWaybill.get('serialNoStockList');
				stockSerialNoMap.each(function(serialNo,serialNoData,length){
					if(!load.handoverbillmodify.inArray(leftSerialNoList,serialNo)){
						leftSerialNoList.push(serialNoData);
						leftWaybill.set('pieces',leftWaybill.get('pieces') + 1);
						leftWaybill.set('weight',(leftWaybill.get('weight') + oneWeight).toFixed(3));
						leftWaybill.set('cubage',(leftWaybill.get('cubage') + oneCubage).toFixed(3));
						leftWaybill.set('weightAc',(leftWaybill.get('weightAc') + serialNoData.weight).toFixed(3));
						leftWaybill.set('cubageAc',(leftWaybill.get('cubageAc') + serialNoData.volumn).toFixed(3));
					}
				});
				//如果左侧流水号已展开，则重新加载流水号
				var plugin = leftGrid.getPlugin('handOverBillModify_queryWaybillGrid_serialNoGrid');
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
			var leftGrid = load.handoverbillmodify.queryEnRouteWaybillGrid;
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
					recCopy.set('cubage',(oneCubage*pieces).toFixed(3));
					recCopy.set('serialNoHandOveredList',serialNoList);
					recCopy.set('weightAc',(record.get('weightAc')).toFixed(3));
					recCopy.set('cubageAc',(record.get('cubageAc')).toFixed(3));//TODO
					leftGrid.store.insert(leftGrid.store.getCount(),recCopy);
				}else{
					//获取左侧运单的流水号list
					var leftSerialNoList = leftWaybill.get('serialNoHandOveredList');
					for(var i in serialNoList){
						var serialNo = serialNoList[i].serialNo;
						if(!load.handoverbillmodify.inArray(leftSerialNoList,serialNo)){
							leftSerialNoList.push(serialNoList[i]);
							leftWaybill.set('pieces',leftWaybill.get('pieces') + 1);
							leftWaybill.set('weight',(leftWaybill.get('weight') + oneWeight).toFixed(3));
							leftWaybill.set('cubage',(leftWaybill.get('cubage') + oneCubage).toFixed(3));
							leftWaybill.set('weightAc',(leftWaybill.get('weightAc') + serialNoList[i].weight).toFixed(3));
							leftWaybill.set('cubageAc',(leftWaybill.get('cubageAc') + serialNoList[i].volumn).toFixed(3));
						}
						//如果左侧流水号已展开，则重新加载流水号
						var plugin = leftGrid.getPlugin('handOverBillModify_queryEnRouteWaybillGrid_serialNoGrid');
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
		text : load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.waybillGrid.actionColumn')/*'操作'*/,
		align : 'center',
		items : [ {
			tooltip : load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.moveButtonPanel.moveLeft')/*'左移'*/,
			iconCls : 'deppon_icons_remove',
			handler : function(grid, rowIndex, colIndex) {
				var record = grid.store.getAt(rowIndex);
				load.handoverbillmodify.unsubmitedWaybillGrid.leftMove(record);
			}
		} ]
	}, {
		dataIndex : 'waybillNo',
		align : 'center',
		width : 80,
		xtype : 'ellipsiscolumn',
		text : load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.waybillGrid.waybillNoColumn')/*'运单号'*/
	}, {
		dataIndex : 'transProperty',
		align : 'center',
		width : 95,
		text : load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.waybillGrid.transPropertyColumn')/*'运输性质'*/
	}, {
		dataIndex : 'transPropertyCode',
		align : 'center',
		hidden : true, 
		width : 95,
		text : '运输性质编码'/*'运输性质'*/
	}, {
		dataIndex : 'goodsName',
		align : 'center',
		width : 60,
		xtype : 'ellipsiscolumn',
		text : load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.waybillGrid.goodsNameColumn')/*'货物名称'*/
	}, {
		dataIndex : 'packing',
		align : 'center',
		width : 60,
		xtype : 'ellipsiscolumn',
		text : load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.waybillGrid.packingColumn')/*'包装'*/
	}, {
		dataIndex : 'pieces',
		align : 'center',
		flex : 1,
		text : load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.waybillGrid.piecees')/*'件数'*/
	}, {
		dataIndex : 'weight',
		align : 'center',
		width : 60,
		xtype : 'ellipsiscolumn',
		text : load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.waybillGrid.weight')/*'重量'*/
	}, {
		dataIndex : 'cubage',
		align : 'center',
		width : 60,
		xtype : 'ellipsiscolumn',
		text : load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.waybillGrid.volume')/*'体积'*/
	}, {
		dataIndex : 'arriveDept',
		align : 'center',
		width : 150,
		xtype : 'ellipsiscolumn',
		text : load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.basicInfoForm.arriveDeptLabel')/*'到达部门'*/
	}, {
		dataIndex : 'insuranceValue',
		align : 'center',
		width : 60,
		xtype : 'ellipsiscolumn',
		text : load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.waybillGrid.insuranceValue')/*'保险价值'*/
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
		text : load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.waybillGrid.waybillPieces')/*'开单件数'*/
	}, {
		dataIndex : 'isPreciousGoods',
		align : 'center',
		width : 80,
		text : load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.waybillGrid.isPreciousGoods')/*'是否贵重物品'*/,
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
		text : load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.waybillGrid.waybillNoteColumn')/*'运单备注'*/
	} ],
	getRightMenu : function(record){
		var grid = this;
		function leftMoveOne(){
			grid.leftMove(record);
		}
		var	rightMenu =	new Ext.menu.Menu({
	        items : [{
                text : load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.moveButtonPanel.moveLeft')/*'左移'*/,
                handler : leftMoveOne
	        }]
	     });
		 return rightMenu;
	}
});

load.handoverbillmodify.unsubmitedWaybillGrid = Ext.create('Foss.load.handoverbillmodify.UnSubmitedWaybillGrid');

//定义查询窗口中间部分的panel
Ext.define('Foss.load.handoverbillmodify.QueryWindowCenterPanel', {
	extend : 'Ext.panel.Panel',
	gridTabPanel : null,
	getGridTabPanel : function(){
		if(this.gridTabPanel==null){
			this.gridTabPanel = Ext.create('Foss.load.handoverbillmodify.QueryWayBillTabPanel');
			load.handoverbillmodify.queryWaybillTabPanel = this.gridTabPanel;
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
			Ext.create('Foss.load.handoverbillmodify.moveButtonPanel'),
			Ext.create('Ext.panel.Panel',{
				width : 10,
				items : [{
					xtype : 'button',
					border : false,
					cls : 'flexleft',
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
			load.handoverbillmodify.unsubmitedWaybillGrid],
		me.callParent([cfg]);
	}
});
// 定义查询交接运单窗口
Ext.define('Foss.load.handoverbillmodify.QueryWayBillWindow', {
	extend : 'Ext.window.Window',
	closeAction : 'hide',
	modal : true,
//	maximized : true,
//	width : document.body.clientWidth,
	width : 1260,
	height : 930,
	title : load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.queryWaybillForm.windowTitle')/*'查询交接运单'*/,
	queryWaybillForm: null,
	getQueryWaybillForm: function(){
		if(this.queryWaybillForm==null){
			this.queryWaybillForm = Ext.create('Foss.load.handoverbillmodify.QueryWaybillForm');
			load.handoverbillmodify.queryWaybillForm = this.queryWaybillForm;
		}
		return this.queryWaybillForm;
	},
	centerPanel : null,
	getCenterPanel : function(){
		if(this.centerPanel==null){
			this.centerPanel = Ext.create('Foss.load.handoverbillmodify.QueryWindowCenterPanel');
			load.handoverbillmodify.queryWindowCenterPanel = this.centerPanel;
		}
		return this.centerPanel;
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
			load.handoverbillmodify.selectedWaybillMap.clear();
			if(load.handoverbillmodify.queryWaybillGrid.store.getCount() !== 0){
				load.handoverbillmodify.queryWaybillGrid.store.removeAll();//清空store
				load.handoverbillmodify.pagingBar.onLoad();
			}
			//处理查询在途运单grid数据
			load.handoverbillmodify.selectedEnRouteWaybillMap.clear();
			if(load.handoverbillmodify.queryEnRouteWaybillGrid.store.getCount() !== 0){
				load.handoverbillmodify.queryEnRouteWaybillGrid.store.removeAll();//清空store
				load.handoverbillmodify.enRoutePagingBar.onLoad();
			}
			//清空已选运单列表
			load.handoverbillmodify.unsubmitedWaybillGrid.store.removeAll();
			load.handoverbillmodify.updateUnsubmitedWaybillStaInfo();
			//清空统计信息
			Ext.getCmp('Foss_load_handOverBillModify_QueryPageTotalPieces').setValue(0);
			Ext.getCmp('Foss_load_handOverBillModify_QueryPageTotalWeight').setValue(0);
			Ext.getCmp('Foss_load_handOverBillModify_QueryPageTotalCubage').setValue(0);
			Ext.getCmp('Foss_load_handOverBillModify_QueryPageTotalMoney').setValue(0);
		}
	},
	buttons : [{
		text : load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.changeAssembleWayWindow.confirmButton')/*'确定'*/,
		cls : 'yellow_button',
		handler : function(){
			//若未勾选任何运单，则提示
			var unGrid = load.handoverbillmodify.unsubmitedWaybillGrid,
				unStore = unGrid.store;
			if(unStore.getCount() == 0){
				Ext.ux.Toast.msg(load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.waybillGrid.alertInfoTitle')/*'提示'*/, '右侧列表没有任何运单！', 'error', 1000);
				return;
			}
			//获取主页面的表格
			var mainGrid = load.handoverbillmodify.handOverBillDetailGrid;
			//获取主页面的扩展组件
			var plugin = mainGrid.getPlugin('Foss_handOverBillModify_mainPage_serialNoGrid_ID');
			
			if(!load.handoverbillmodify.validateHandoverbillDetailsTransProperty(unStore)){
				return;
			}
			
			//将提交来的运单record添加至主页
			unStore.each(function(record){
				var serialNoMap = record.get('serialNoMap'),
					waybillNo = record.get('waybillNo');
				//从主页获取该运单号记录
				var mainRecord = mainGrid.store.findRecord('waybillNo',waybillNo,0,false,true,true);
				if(mainRecord !== null){//若该运单曾经被提交过，则将该运单的流水号累加
					//获取原来的流水号map
					var tempSerialNoMap = load.handoverbillmodify.allSerialNoMap.get(waybillNo);
					serialNoMap.each(function(key,value,length){
						value.set('instorageDate',null);
						tempSerialNoMap.replace(key,value);
						//如果修改前的交接单中没有该流水号
						if(!(load.handoverbillmodify.oldSerialNoMap.get(waybillNo) != null
								&& load.handoverbillmodify.oldSerialNoMap.get(waybillNo).get(key) != null)){
							if(load.handoverbillmodify.addedSerialNoMap.get(waybillNo) != null){
								var tempMap = load.handoverbillmodify.addedSerialNoMap.get(waybillNo);
								tempMap.replace(key,value);
								load.handoverbillmodify.addedSerialNoMap.replace(waybillNo,tempMap);
							}else{
								var tempMap = new Ext.util.HashMap();
								tempMap.replace(key,value);
								load.handoverbillmodify.addedSerialNoMap.replace(waybillNo,tempMap);
							}
						}
					});
					//更新主页列表中的件数等信息
					mainRecord.set('pieces',tempSerialNoMap.getCount());
					mainRecord.set('cubage',((record.get('cubage')/record.get('pieces'))*mainRecord.get('pieces')).toFixed(3));
					mainRecord.set('weight',((record.get('weight')/record.get('pieces'))*mainRecord.get('pieces')).toFixed(3));
					//实际重量，实际体积
					var weightAc = 0, cubageAc=0;
					//循环 遍历所有流水号 计算 体积 重量
					tempSerialNoMap.each(function(serial,serialNo,length){
						weightAc += serialNo.get('weight');
						cubageAc += serialNo.get('volumn');
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
					//若在修改前的load.handoverbillmodify.oldWaybillMap中无此运单，则将该运单放于load.handoverbillmodify.addedWaybillMap中
					if(load.handoverbillmodify.oldWaybillMap.get(waybillNo) === undefined){
						load.handoverbillmodify.addedWaybillMap.replace(waybillNo,mainRecord.copy());
					}
				}else{
					var tempSerialNoMap = new Ext.util.HashMap();
					var addedSerialNoMap;
					if(load.handoverbillmodify.addedSerialNoMap.get(waybillNo) !== undefined){
						addedSerialNoMap = load.handoverbillmodify.addedSerialNoMap.get(waybillNo);
					}else{
						addedSerialNoMap = new Ext.util.HashMap();
					}
					serialNoMap.each(function(key,value,length){
						value.set('instorageDate',null);
						tempSerialNoMap.replace(key,value);
						if(load.handoverbillmodify.oldSerialNoMap.get(waybillNo) === undefined 
								|| (load.handoverbillmodify.oldSerialNoMap.get(waybillNo) !== undefined
								&& load.handoverbillmodify.oldSerialNoMap.get(waybillNo).get(key) === undefined)){
							addedSerialNoMap.replace(key,value);
						}
					});
					load.handoverbillmodify.allSerialNoMap.replace(waybillNo,tempSerialNoMap);
					if(addedSerialNoMap.length!=0){
						load.handoverbillmodify.addedSerialNoMap.replace(waybillNo,addedSerialNoMap);
					}
					var newRecord =  record.copy();
					//实际重量，实际体积
					var weightAc = 0, cubageAc=0;
					//循环 遍历所有流水号 计算 体积 重量
					tempSerialNoMap.each(function(serial,serialNo,length){
						weightAc += serialNo.get('weight');
						cubageAc += serialNo.get('volumn');
					});
					newRecord.set('cubageAc',cubageAc.toFixed(3));
					newRecord.set('weightAc',weightAc.toFixed(3));
					
					
					//若在修改前的load.handoverbillmodify.oldWaybillMap中无此运单，则将该运单放于load.handoverbillmodify.addedWaybillMap中
					if(load.handoverbillmodify.oldWaybillMap.get(waybillNo) === undefined){
						load.handoverbillmodify.addedWaybillMap.replace(waybillNo,newRecord.copy());
					}
					//将该运单插入主页面
					mainGrid.store.insert(mainGrid.store.getCount(),newRecord);
				}
				//若新添加的运单号之前被删除，此次重复添加，则从deletedWaybillMap中移除该运单
				if(load.handoverbillmodify.deletedWaybillMap.get(waybillNo) !== undefined){
					load.handoverbillmodify.deletedWaybillMap.removeAtKey(waybillNo);
				}
				if(load.handoverbillmodify.deletedSerialNoMap.get(waybillNo)!==undefined){
					load.handoverbillmodify.deletedSerialNoMap.removeAtKey(waybillNo);
				}
			});
			this.ownerCt.ownerCt.close();
		}
	}, {
		text : load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.leftGrid.concelButton')/*'取消'*/,
		handler : function(){
			this.ownerCt.ownerCt.close();
		}
	} ]
});

//定义交接单日志Model
Ext.define('Foss.load.handoverbillmodify.HandOverBillOptLogModel', {
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
Ext.define('Foss.load.handoverbillmodify.HandOverBillOptLogStore', {
	extend : 'Ext.data.Store',
	// 绑定一个模型
	model : 'Foss.load.handoverbillmodify.HandOverBillOptLogModel',
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
					'handOverBillVo.handOverBillNo' : load.handoverbillmodify.handOverBillNo
				}
			});	
		}
	}
});

//定义交接单修改日志列表
Ext.define('Foss.load.handoverbillmodify.HandOverBillOptLogGrid', {
	extend : 'Ext.grid.Panel',
	frame : true,
	title : load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.optLogGrid.gridTitle')/*'修改日志'*/,
//	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	columnLines: true,
	autoScroll : true,
	collapsible : true,
	collapsed : true,//页面初始化时不展开该grid，不加载数据
	animCollapse : true,
	store : Ext.create('Foss.load.handoverbillmodify.HandOverBillOptLogStore'),
	columns : [{
		dataIndex : 'operatorName',
		align : 'center',
		width : 70,
		xtype : 'ellipsiscolumn',
		text : load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.optLogGrid.operator')/*'操作人'*/
	}, {
		dataIndex : 'optTime',
		align : 'center',
		width : 135,
		text : load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.optLogGrid.operateTime')/*'操作时间'*/,
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
		text : load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.optLogGrid.operateType')/*'操作类别'*/
	}, {
		dataIndex : 'optContent',
		flex : 1,
		text : load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.optLogGrid.operateContent')/*'操作内容'*/,
		xtype : 'linebreakcolumn'
	}],
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.bbar = Ext.create('Deppon.StandardPaging',{
			store:me.store
	});
	load.handoverbillmodify.logPagingBar = me.bbar;
	me.callParent([cfg]);
},
	listeners : {
		'expand' : function(panel, eOpts){
			load.handoverbillmodify.logPagingBar.moveFirst();
		}
	}
});

//自定义方法，将Ext.util.HashMap转化为json格式，以便传往后台
load.handoverbillmodify.convertMap2Json = function(beforeMap){
	var map = {};
	beforeMap.each(function(key,value,length){
		var v = '{'+ '"' +key + '"' +':'+Ext.encode(value.data)+'}';
		var record = Ext.decode(v);
		map = Ext.merge(map,record);
	});
	return map;
}

// 定义运单列表
load.handoverbillmodify.basicInfoForm = Ext.create('Foss.load.handoverbillmodify.BasicInfoForm');
load.handoverbillmodify.handOverBillDetailGrid = Ext.create('Foss.load.handoverbillmodify.HandOverBillDetailGrid');

//加载交接单数据的方法
load.handoverbillmodify.loadData = function(handOverBillNo){
	//定义值，记录修改后是否已保存
	load.handoverbillmodify.isSaved = 'N';
	
	Ext.Ajax.request({
		url : load.realPath('queryHandOverBillByNo.action'),
		params : {'handOverBillVo.handOverBillNo': handOverBillNo},
		success : function(response){
			var result = Ext.decode(response.responseText);
			//定义基本信息实体
			var basicInfo = load.handoverbillmodify.basicInfo = result.handOverBillVo.baseEntity;
			if(load.handoverbillmodify.basicInfo.note == null){
				load.handoverbillmodify.basicInfo.note = '';
			}
			var departDeptCode = basicInfo.departDeptCode;
			var beSalesDept = result.handOverBillVo.beSalesDept;
			var basicInfoRecord = load.handoverbillmodify.basicInfoRecord = Ext.ModelManager.create(basicInfo, 'Foss.load.handoverbillmodify.HandOverBillBaseInfoModel');
			//获取该交接单是否为PDA生成
			var isCreatedByPDA = load.handoverbillmodify.basicInfoRecord.get('isCreatedByPDA');
			//定义基本信息form
			var basicInfoForm = load.handoverbillmodify.basicInfoForm;
			//给基本信息form加载值
			basicInfoForm.getForm().loadRecord(load.handoverbillmodify.basicInfoRecord);
			if(isCreatedByPDA == 'Y'){
				basicInfoForm.getForm().findField('isCreatedByPDA').setValue(true);
			}else{
				basicInfoForm.getForm().findField('isCreatedByPDA').setValue(false);
			}
			
			//只能使用营业部关联顶级车队的公司车辆和所有外请车，登录部门为外场则不过滤
//			if(beSalesDept === 'Y'){
//				var vehicleNoCmp = basicInfoForm.getForm().findField('vehicleNo');
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
			
			//处理司机
			var driverCmp = basicInfoForm.getForm().findField('driver');
			driverCmp.setCombValue(basicInfo.driverName,basicInfo.driver);
			basicInfoForm.getForm().findField('driverName').setValue(basicInfo.driverName);
			
			//根据交接单状态来判断是否预配交接单，如果是预配交接单，则可修改为正式交接单，反之不可
			if(load.handoverbillmodify.basicInfoRecord.get('handOverBillState') == 10){
				basicInfoForm.getForm().findField('isPreHandOverBill').setValue(true);
				basicInfoForm.getForm().findField('loadEndTime').setDisabled(true);
				basicInfoForm.getForm().findField('isPreHandOverBill').setReadOnly(false);//若为预配交接单，则可反选该checkbox改为正式交接单
			}else{
				basicInfoForm.getForm().findField('loadEndTime').setReadOnly(true);
			}
			//如果为外发交接单，则显示“外发代理”隐藏“到达部门”，反之，则反之
			var arriveDeptCmp = basicInfoForm.getForm().findField('arriveDept');
			if(basicInfoRecord.get('handOverType') == 'PARTIALLINE_HANDOVER'){
				arriveDeptCmp.fieldLabel = '外发代理';
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
				if((handOverType == 'LONG_DISTANCE_HANDOVER' || handOverType == 'SHORT_DISTANCE_HANDOVER'
					||handOverType == 'DIVISION_HANDOVER')
						&& (basicInfoRecord.get('handOverBillState') != 10
								&& basicInfoRecord.get('handOverBillState') != 20)){
					basicInfoForm.getForm().findField('vehicleNo').setReadOnly(true);
					driverCmp.setReadOnly(true);
				}
				arriveDeptCmp.fieldLabel = load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.basicInfoForm.arriveDeptLabel')/*'到达部门'*/;
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
		    			top.Ext.MessageBox.alert(load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.waybillGrid.alertInfoTitle')/*'提示'*/,result.message);
					}
				});
			}
			//设置转货类型的值
			if(basicInfoRecord.get('handOverType')==='SHORT_DISTANCE_HANDOVER'||basicInfoRecord.get('handOverType')==='DIVISION_HANDOVER'){
				
				basicInfoForm.getForm().findField('tranGoodsType').setValue(
						load.handoverbillmodify.getTranGoodsStore(1).findRecord(
								'key',basicInfoRecord.get('tranGoodsType'),0,false,false,false));
			}else{
				
				basicInfoForm.getForm().findField('tranGoodsType').setValue(
						load.handoverbillmodify.getTranGoodsStore(2).findRecord(
								'key',basicInfoRecord.get('tranGoodsType'),0,false,true,true));
				
			}
			//当交接单状态为预配或者交接状态时可以修改转货类型否则不可以修改
			if( (basicInfoRecord.get('handOverBillState') != 10 && basicInfoRecord.get('handOverBillState') != 20)){
				basicInfoForm.getForm().findField('tranGoodsType').setReadOnly(true);
			}else{
				basicInfoForm.getForm().findField('tranGoodsType').setReadOnly(false);
			}
			load.handoverbillmodify.queryWaybillWindow = Ext.create('Foss.load.handoverbillmodify.QueryWayBillWindow');
			
			//弹出数据加载，禁止操作
			var myMask = new Ext.LoadMask(load.handoverbillmodify.handOverBillDetailGrid, {
				msg:"加载中，请稍后..."
			});
			myMask.show();
			//加载交接单中运单数据
			load.handoverbillmodify.handOverBillDetailGrid.store.load();
			//后台请求所有交接单下的流水号list，封装成Map
			Ext.Ajax.request({
				url : load.realPath('querySerialNoListByHandOverBillNo.action'),
				params : {'handOverBillVo.handOverBillNo': handOverBillNo},
				success : function(response){
					var result = Ext.decode(response.responseText);
					var serialNoList = result.handOverBillVo.serialNoList;
					//获取流水号list后，在前台封装为map
					for(var i in serialNoList){
						var serialNo = serialNoList[i];
						var serialNoRecord =  Ext.ModelManager.create(serialNo, 'Foss.load.handoverbillmodify.serialNoModel');
						var waybillNo = serialNoRecord.get('waybillNo');
						var serialNo = serialNoRecord.get('serialNo');
						if(load.handoverbillmodify.oldSerialNoMap.get(waybillNo) == null){
							var tempMap = new Ext.util.HashMap();
							tempMap.replace(serialNo,serialNoRecord);
							load.handoverbillmodify.oldSerialNoMap.replace(waybillNo,tempMap);//此处需使用clone new出一个新map，否则两map将是同一引用
							load.handoverbillmodify.allSerialNoMap.replace(waybillNo,tempMap.clone());
						}else{
							var tempMap = load.handoverbillmodify.oldSerialNoMap.get(waybillNo);
							tempMap.replace(serialNo,serialNoRecord);
							load.handoverbillmodify.oldSerialNoMap.replace(waybillNo,tempMap);
							load.handoverbillmodify.allSerialNoMap.replace(waybillNo,tempMap.clone());
						}
					}
					myMask.hide();
				}
			});
		
			//从基本信息record中获取值，给统计信息赋值
			Ext.getCmp('Foss_load_handOverBillModify_MainPageTotalCount').setValue(basicInfoRecord.get('waybillQtyTotal'));
			Ext.getCmp('Foss_load_handOverBillModify_MainPageTotalPieces').setValue(basicInfoRecord.get('goodsQtyTotal'));
			Ext.getCmp('Foss_load_handOverBillModify_MainPageTotalWeight').setValue(basicInfoRecord.get('weightTotal'));
			Ext.getCmp('Foss_load_handOverBillModify_MainPageTotalVolume').setValue(basicInfoRecord.get('volumeTotal'));
			Ext.getCmp('Foss_load_handOverBillModify_MainPageTotalCodAmount').setValue(basicInfoRecord.get('codAmountTotal'));
		}
	});
}

//加载主panel
Ext.onReady(function() {
	
	//加载数据
	var handOverBillNo = load.handoverbillmodify.handOverBillNo;
	load.handoverbillmodify.loadData(handOverBillNo);
	
	Ext.create('Ext.panel.Panel', {
		id : 'T_load-handoverbillmodifyindex_content',
		cls : "panelContentNToolbar",
		bodyCls : 'panelContent-body',
		layout : 'auto',//
		items : [ load.handoverbillmodify.basicInfoForm, load.handoverbillmodify.handOverBillDetailGrid,{
			xtype : 'container',
			columnWidth : 1,
			layout : 'column',
			items : [ {
				xtype : 'container',
				html : '&nbsp',
				columnWidth : .92
			},{
				columnWidth : .08,
				xtype : 'button',
				cls : 'yellow_button',
				name : 'saveButton',
				id : 'Foss_load_handOverBillModify_mainPage_saveButton_ID',
				text : load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.waybillGrid.saveButtonText')/*'保存'*/,
				handler : function() {
					var basicInfoForm = load.handoverbillmodify.basicInfoForm;
				
					//基本信息未填写完整
					if(!basicInfoForm.getForm().isValid()){
						Ext.ux.Toast.msg(load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.waybillGrid.alertInfoTitle')/*'提示'*/, '基本信息未填写完整!', 'error', 2000);
						return;
					}
					//获取基本信息实体
					var baseEntity = basicInfoForm.getForm().getValues();
					
					
					 if(baseEntity.handOverType=='SHORT_DISTANCE_HANDOVER'&&load.handoverbillmodify.beDivision=='Y'){
						 if((baseEntity.vehicleNo).substr(0,1)!='德'){
							Ext.Msg.alert("提示","分部到点部交接单，车牌号必须以“德”开头")
							return;
						 }
					 }
					//“是否PDA生成”设置为N
					baseEntity.isCreatedByPDA = 'N';
					//未修改任何信息
					var oldBasicInfo = load.handoverbillmodify.basicInfo;
					//修改前是否为预配交接单
					if(oldBasicInfo.handOverBillState == 10){
						oldBasicInfo.isPreHandOverBill = 'Y';
					}else{
						oldBasicInfo.isPreHandOverBill = 'N';
					}
					//修改后是否为预配交接单
					if(baseEntity.isPreHandOverBill == 'on'){
						baseEntity.isPreHandOverBill = 'Y';
					}else{
						baseEntity.isPreHandOverBill = 'N';
					}
					//判断是否做过修改
					if(oldBasicInfo.vehicleNo == baseEntity.vehicleNo
							&& oldBasicInfo.driver == baseEntity.driver
							&& oldBasicInfo.note == baseEntity.note.trim() 
							&& oldBasicInfo.goodsType == baseEntity.goodsType 
							&& oldBasicInfo.tranGoodsType == baseEntity.tranGoodsType
							&& load.handoverbillmodify.addedSerialNoMap.getCount() == 0
							&& load.handoverbillmodify.deletedSerialNoMap.getCount() == 0
							&& load.handoverbillmodify.updatedWaybillMap.getCount() == 0
							&& oldBasicInfo.isPreHandOverBill == baseEntity.isPreHandOverBill
							){
						Ext.ux.Toast.msg(load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.waybillGrid.alertInfoTitle')/*'提示'*/, '未做任何修改!', 'error', 2000);
						return;
					}
					if(load.handoverbillmodify.handOverBillDetailGrid.store.getCount() == 0){
						Ext.ux.Toast.msg(load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.waybillGrid.alertInfoTitle')/*'提示'*/, '交接单内无任何运单!', 'error', 2000);
						return;
					}
					var deletedWaybill = load.handoverbillmodify.convertMap2Json(load.handoverbillmodify.deletedWaybillMap);
					var addedWaybill = load.handoverbillmodify.convertMap2Json(load.handoverbillmodify.addedWaybillMap);
					var updatedWaybill = load.handoverbillmodify.convertMap2Json(load.handoverbillmodify.updatedWaybillMap);
					var addedSerialNo = load.handoverbillmodify.convertMap2Json(load.handoverbillmodify.addedSerialNoMap);
					//获取所有的新增的流水号list
					var tempAddedSerialNoList = load.handoverbillmodify.addedSerialNoMap.getValues();
					var addedSerialNoList = new Array();
					for(var i=0;i<tempAddedSerialNoList.length;i++){
						var serialNoList = tempAddedSerialNoList[i].getValues();
						for(var j=0;j<serialNoList.length;j++){
							addedSerialNoList.push(serialNoList[j].data);
						}
					}
					//获取当前所有的流水号list
					var tempAllSerialNoList = load.handoverbillmodify.allSerialNoMap.getValues();
					var allSerialNoList = new Array();
					for(var i=0;i<tempAllSerialNoList.length;i++){
						var serialNoList = tempAllSerialNoList[i].getValues();
						for(var j=0;j<serialNoList.length;j++){
							allSerialNoList.push(serialNoList[j].data);
						}
					}
					//获取所有的删除的流水号list
					var tempDeletedSerialNoList = load.handoverbillmodify.deletedSerialNoMap.getValues();
					var deletedSerialNoList = new Array();
					for(var i=0;i<tempDeletedSerialNoList.length;i++){
						var serialNoList = tempDeletedSerialNoList[i].getValues();
						for(var j=0;j<serialNoList.length;j++){
							deletedSerialNoList.push(serialNoList[j].data);
						}
					}
					//计算总金额，并获取当前所有的运单list
					var totalMoney = 0,
						allWaybillList = new Array(),
						mainStore = load.handoverbillmodify.handOverBillDetailGrid.store;
					for(var i = 0;i < mainStore.getCount();i++){
						var record = mainStore.getAt(i);
						totalMoney += record.get('waybillFee');
						var waybill = record.data;
						if(waybill.isCarLoad == 'Y' && mainStore.getCount() > 1){
							Ext.Msg.show({
							     title : load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.waybillGrid.alertInfoTitle')/*'提示'*/,
							     msg : '保存失败，运单' + waybill.waybillNo + '为整车运单，添加整车运单时只能有一条运单',
							     buttons : Ext.Msg.OK,
							     icon: Ext.Msg.WARNING
							});
							return;
						}
						//如果为外发交接单，则交接件数不能小于开单件数
						if(baseEntity.handOverType == 'PARTIALLINE_HANDOVER'
								&& waybill.pieces < waybill.waybillPieces){
							Ext.Msg.show({
							     title : load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.waybillGrid.alertInfoTitle')/*'提示'*/,
							     msg : '保存失败，外发交接时必须全票交接，运单' + waybill.waybillNo + '的交接件数小于开单件数！',
							     buttons : Ext.Msg.OK,
							     icon: Ext.Msg.WARNING
							});
							return;
						}
						var obj = Ext.clone(waybill);
						delete obj.serialNoStockList;
						delete obj.serialNoHandOveredList;
						delete obj.serialNoMap;
						allWaybillList.push(obj);
					}
					
					var data = {'handOverBillVo' : {
						'updateHandOverBillDto' : {
							'deletedWaybillMap' : deletedWaybill,
							'addedWaybillMap' : addedWaybill,
							'allWaybillList' : allWaybillList,
							'updatedWaybillMap' : updatedWaybill,
							'deletedSerialNoList' : deletedSerialNoList,
							'addedSerialNoList' : addedSerialNoList,
							'allSerialNoList' : allSerialNoList,
							'handOverBillEntity' : baseEntity,
							'totalCount' : Ext.getCmp('Foss_load_handOverBillModify_MainPageTotalCount').getValue(),
							'totalPieces' : Ext.getCmp('Foss_load_handOverBillModify_MainPageTotalPieces').getValue() ,
							'totalCubage' : Ext.getCmp('Foss_load_handOverBillModify_MainPageTotalVolume').getValue() ,
							'totalWeight' : Ext.getCmp('Foss_load_handOverBillModify_MainPageTotalWeight').getValue(),
							'totalMoney' : totalMoney,
							'totalCodAmount' : Ext.getCmp('Foss_load_handOverBillModify_MainPageTotalCodAmount').getValue()
						}
					}};
					//mask
					var myMask = new Ext.LoadMask(Ext.getCmp('T_load-handoverbillmodifyindex_content'), {
						msg:"数据提交中，请稍候..."
					});
					myMask.show();
					Ext.Ajax.request({
						url : load.realPath('updateHandOverBill.action'),
						jsonData : data,
						timeout : 300000,
						success : function(response){
							Ext.ux.Toast.msg(load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.waybillGrid.alertInfoTitle')/*'提示'*/, '修改成功!', 'info', 3000);
							load.handoverbillmodify.isSaved = 'Y';
							//隐藏“查询交接运单”、“保存”按钮
							Ext.getCmp('Foss_load_handOverBillModify_mainPage_saveButton_ID').setVisible(false);
							Ext.getCmp('Foss_load_handOverBillModify_mainPage_queryButton_ID').setVisible(false);
							//禁用快速添加里的输入框、按钮
							Ext.getCmp('Foss_load_handOverBillModify_quickAddWaybillNo_ID').setVisible(false);
							Ext.getCmp('Foss_load_handOverBillModify_quickAddButton_ID').setVisible(false);
							//设置form所有字段均为只读
							var form = basicInfoForm.getForm();
							var formCmps = form.getFields().getRange(0,form.getFields().getCount());
							for(var i in formCmps){
								formCmps[i].setReadOnly(true);
							}
							myMask.hide();
							//隐藏运单列表操作列
							var superGrid = load.handoverbillmodify.handOverBillDetailGrid;
							superGrid.columns[1].setVisible(false);
							//如果运单被展开，隐藏流水号前的操作列
							var plugin = superGrid.getPlugin('Foss_handOverBillModify_mainPage_serialNoGrid_ID');
							var pluginGrid = plugin.getExpendRowBody();
							if(pluginGrid != null){
								pluginGrid.columns[0].setVisible(false);
							}
						},
						exception : function(response) {
		    				var result = Ext.decode(response.responseText);
		    				top.Ext.MessageBox.alert(load.handoverbillmodify.i18n('foss.load.handoverbilladdnew.waybillGrid.alertInfoTitle')/*'提示'*/,'保存失败，' + result.message);
		    				myMask.hide();
		    			}
					});
				}
			}]
		},Ext.create('Foss.load.handoverbillmodify.HandOverBillOptLogGrid') ],
		renderTo : 'T_load-handoverbillmodifyindex-body'
	});
});