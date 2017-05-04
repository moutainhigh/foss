//定义查询包结果列表Model,四个grid共用该Model
Ext.define('Foss.load.expresspackageaddnew.ExpressPackageModel', {
	extend : 'Ext.data.Model',
	//定义字段
	fields : [{
		name : 'id',
		type : 'string'
	}, {
		name : 'packageNo',
		type : 'string'
	}, {
		name : 'status',
		type : 'string'
	}, {
		name : 'departOrgCode',
		type : 'string'
	}, {
		name : 'departOrgName',
		type : 'string'
	}, {
		name : 'arriveOrgCode',
		type : 'string'
	}, {
		name : 'arriveOrgName',
		type : 'string'
	}, {
		name : 'createUserName',
		type : 'string'
	},{
		name : 'createUserCode',
		type : 'string'
	}, {
		name : 'createTime',
		type : 'date',
		convert: dateConvert
	}, {
		name : 'weight',
		type : 'number'
	}, {
		name : 'volume',
		type : 'number'
	}, {
		name : 'waybillQty',
		type : 'number'
	}, {
		name : 'goodsQty',
		type : 'number'
	},{
		name : 'endTime',
		type : 'date',
		convert: dateConvert
	}]
});

//定义查询运单结果Model
Ext.define('Foss.load.expresspackageaddnew.waybillStockModel', {
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
Ext.define('Foss.load.expresspackageaddnew.serialNoModel', {
	extend : 'Ext.data.Model',
	// 定义字段
	fields : [{
		name : 'superId',
		type : 'string'
	},{
		name : 'packageNo',
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
		name : 'isCreatedPackage',
		type : 'string'
	}]
});

// 定义包明细store
Ext.define('Foss.load.expresspackageaddnew.packageDetailStore', {
	extend : 'Ext.data.Store',
	// 绑定一个模型
	model : 'Foss.load.expresspackageaddnew.waybillStockModel',
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	},
	listeners : {
		'datachanged' : function(store){
			load.expresspackageaddnew.updateMainPageStaInfo(store);
		},
		'update' : function(store){
			load.expresspackageaddnew.updateMainPageStaInfo(store);
		},
		'clear' : function(store){
			load.expresspackageaddnew.updateMainPageStaInfo(store);
		}
	}
});

//方法用于各处调用，更新主页面grid下统计条数据
load.expresspackageaddnew.updateMainPageStaInfo = function (store){
	//更新主页总票数
	var totalCountCmp = Ext.getCmp('Foss_load_expresspackageaddnew_MainPageTotalCount');// 通过id查找现有的Component
	totalCountCmp.setValue(load.expresspackageaddnew.packageDetailGrid.store.getCount());
	//遍历主页store，获得总件数、总体积、总重量
	var totalPieces = 0,totalVolume = 0,totalWeight = 0;
	store.each(function(record){
		totalPieces += record.get('pieces');
		totalVolume += record.get('cubageAc');
		totalWeight += record.get('weightAc');
	});
	if(totalWeight != 0){
		totalWeight = totalWeight.toFixed(2);//如果不为0，则四舍五入，保留两位小数
	}
	if(totalVolume != 0){
		totalVolume = totalVolume.toFixed(2);
	}
	//更新主页总件数、总体积、总重量
	Ext.getCmp('Foss_load_expresspackageaddnew_MainPageTotalPieces').setValue(totalPieces);
	Ext.getCmp('Foss_load_expresspackageaddnew_MainPageTotalVolume').setValue(totalVolume);
	Ext.getCmp('Foss_load_expresspackageaddnew_MainPageTotalWeight').setValue(totalWeight);
};

//方法用于各处调用，更新查询库存运单界面下统计条数据
load.expresspackageaddnew.updateQueryPageStaInfo = function(){
	//由于一层map勾选导致的二层流水号增加个数不好确定，故遍历二层map，结合一层map，得到各统计信息
	var totalPieces = 0,totalWeight = 0,totalCubage = 0,totalMoney = 0;
	load.expresspackageaddnew.selectedWaybillMap.each(function(waybillNo,waybill,length){
		var serialNoMap = waybill.get('serialNoMap');
		totalPieces += serialNoMap.getCount();//得到总件数(总流水号数)
		totalMoney += waybill.get('waybillFee');//取得运单总价钱
		var waybillWeight = (serialNoMap.getCount()/waybill.get('pieces'))*(waybill.get('weight'));//按件数均分，得到该票运单的重量
		totalWeight += waybillWeight;//得到总重量
		var waybillCubage = (serialNoMap.getCount()/waybill.get('pieces'))*(waybill.get('cubage'));//按件数均分，得到该票运单的体积
		totalCubage += waybillCubage;//得到总体积
	});
	if(totalWeight != 0){
		totalWeight = totalWeight.toFixed(2);//如果不为0，则四舍五入，保留两位小数
	}
	if(totalCubage != 0){
		totalCubage = totalCubage.toFixed(2);
	}
	//设置已勾选总件数
	Ext.getCmp('Foss_load_expresspackageaddnew_QueryPageTotalPieces').setValue(totalPieces);
	Ext.getCmp('Foss_load_expresspackageaddnew_QueryPageTotalWeight').setValue(totalWeight);
	Ext.getCmp('Foss_load_expresspackageaddnew_QueryPageTotalCubage').setValue(totalCubage);
	Ext.getCmp('Foss_load_expresspackageaddnew_QueryPageTotalMoney').setValue(totalMoney);
}

//方法用于各处调用，更新已选运单统计条数据
load.expresspackageaddnew.updateUnsubmitedWaybillStaInfo = function(){
	var totalPieces = 0,totalWeight = 0,totalCubage = 0,totalMoney = 0;
	load.expresspackageaddnew.unsubmitedWaybillGrid.store.each(function(waybill){
		var serialNoMap = waybill.get('serialNoMap');
		totalPieces += serialNoMap.getCount();//得到总件数
		totalMoney += waybill.get('waybillFee');
		totalWeight += waybill.get('weight');//得到总重量
		totalCubage += waybill.get('cubage');//得到总体积
	});
	if(totalWeight != 0){
		totalWeight = totalWeight.toFixed(2);//如果不为0，则四舍五入，保留两位小数
	}
	if(totalCubage != 0){
		totalCubage = totalCubage.toFixed(2);
	}		
	//设置已勾选总件数
	Ext.getCmp('Foss_load_expresspackageaddnew_UnsubmitedWaybillTotalPieces').setValue(totalPieces);
	Ext.getCmp('Foss_load_expresspackageaddnew_UnsubmitedWaybillTotalWeight').setValue(totalWeight);
	Ext.getCmp('Foss_load_expresspackageaddnew_UnsubmitedWaybillTotalCubage').setValue(totalCubage);
	Ext.getCmp('Foss_load_expresspackageaddnew_UnsubmitedWaybilleTotalMoney').setValue(totalMoney);
}

//定义方法，判断流水号数组中是否存在某流水号
load.expresspackageaddnew.inArray = function(serialNoList,serialNo){
	for(var i in serialNoList){
		var serialNoRec = serialNoList[i];
		if(serialNoRec.serialNo === serialNo){
			return true;
		}
	}
	return false;
}

// 定义运单明细store
Ext.define('Foss.load.expresspackageaddnew.WaybillDetailStore', {
	extend : 'Ext.data.Store',
	// 绑定一个模型
	model : 'Foss.load.expresspackageaddnew.serialNoModel',
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
});

// 定义运单明细grid（二级grid）
Ext.define('Foss.load.expresspackageaddnew.WaybillDetailGrid', {
	extend : 'Ext.grid.Panel',
	columnLines: true,
	frame: true,
	enableColumnHide : false,//配置该属性可取消grid自定义显示列功能
	baseCls : 'package_addNew_serialNoGap',
	autoScroll : true,
	width : 180,
	store : Ext.create('Foss.load.expresspackageaddnew.WaybillDetailStore'),
	columns : [ {
		xtype : 'actioncolumn',
		width : 60,
		text : load.expresspackageaddnew.i18n('foss.load.expresspackageaddnew.waybillGrid.actionColumn')/*'操作'*/,
		align : 'center',
		items : [ {
			tooltip : load.expresspackageaddnew.i18n('foss.load.expresspackageaddnew.waybillGrid.deleteButtonColumn')/*'删除'*/,
			iconCls : 'deppon_icons_remove',
			handler : function(grid, rowIndex, colIndex) {
				if(grid.store.getCount() == 1){
					Ext.ux.Toast.msg(load.expresspackageaddnew.i18n('foss.load.expresspackageaddnew.waybillGrid.alertInfoTitle')/*'提示'*/, load.expresspackageaddnew.i18n('foss.load.expresspackageaddnew.waybillGrid.deleteWaybillAlert')/*'请直接删除运单'*/, 'error', 1500);
					return;
				}
				var record = grid.getStore().getAt(rowIndex),
					waybillNo = record.get('waybillNo'),
					serialNo = record.get('serialNo');
				//更新一级表格内的信息
				waybillStore = load.expresspackageaddnew.packageDetailGrid.store;
				var waybillRecord = waybillStore.findRecord('waybillNo', waybillNo, 0, false,true,true);
				//删掉map里的该流水号
				waybillRecord.get('serialNoMap').removeAtKey(serialNo);
				waybillRecord.set('weight',(waybillRecord.get('weight')-(waybillRecord.get('weight')/waybillRecord.get('pieces'))).toFixed(2));
				waybillRecord.set('cubage',(waybillRecord.get('cubage')-(waybillRecord.get('cubage')/waybillRecord.get('pieces'))).toFixed(2));
				waybillRecord.set('weightAc',(waybillRecord.get('weightAc')-(waybillRecord.get('weightAc')/waybillRecord.get('pieces'))).toFixed(2));
				waybillRecord.set('cubageAc',(waybillRecord.get('cubageAc')-(waybillRecord.get('cubageAc')/waybillRecord.get('pieces'))).toFixed(2));
				waybillRecord.set('pieces',waybillRecord.get('pieces') - 1);
				grid.store.removeAt(rowIndex);
			}
		} ]
	}, {
		dataIndex : 'serialNo',
		align : 'center',
		flex : 1,
		xtype : 'ellipsiscolumn',
		text : load.expresspackageaddnew.i18n('foss.load.expresspackageaddnew.waybillGrid.serialNoColumn')/*'流水号'*/
	} ],
	bindData : function(record){
		var superGrid = this.superGrid,
			serialNoMap = record.get('serialNoMap');
		if(load.expresspackageaddnew.isSaved == 'Y'){
			this.columns[0].setVisible(false);
			//this.columns[0].destroy();
		}
		this.store.loadData(serialNoMap.getValues());
	}
});

/*
 * 判断待添加的运单和已添加的运单是否都为快递货或都为零担货
 * tbAddWaybills : 待添加的运单集合； 目前tbAddWaybills的数据类型为Ext.util.MixedCollection或Ext.data.Store
 * existWaybill ： 已经存在的运单; 目前existsWaybill的数据类型为Ext.data.Model
 */
load.expresspackageaddnew.validatepackageDetailsTransProperty = function(tbAddWaybills, existWaybill){
	
	if(Ext.isEmpty(tbAddWaybills)){
		return true;
	}
	
	//用于存放循环中的运单
	var waybill = null;
	//循环待添加运单时，用于存放当前运单的运输性质
	var transPropertyCode = null;
	
	if(Ext.isEmpty(existWaybill)){
		//待添加运单运输性质标识位
		var tbAddFlag = null;
		
		for(var i = 0; i < tbAddWaybills.getCount(); i++){
			waybill = tbAddWaybills.getAt(i);
			//非快递都为零担//note......
			transPropertyCode = ((waybill.transPropertyCode || waybill.get("transPropertyCode")) === "PACKAGE" 
				|| (waybill.transPropertyCode || waybill.get("transPropertyCode")) === "RCP" 
				|| (waybill.transPropertyCode || waybill.get("transPropertyCode")) === "EPEP") ? "EXPRESS" : "LTL"; 
			
			if(tbAddFlag === null){
				//将标识位设置为第一条待添加运单的运输性质
				tbAddFlag = transPropertyCode;
			}else if(tbAddFlag != transPropertyCode){
				Ext.ux.Toast.msg('提示', '添加失败，商务专递货不能与其他货混装在同一个包中！', 'error');
				return false;
			}
		}
	}else{
		for(var i = 0; i < tbAddWaybills.getCount(); i++){
			waybill = tbAddWaybills.getAt(i);
			//非快递都为零担
			transPropertyCode = ((waybill.transPropertyCode || waybill.get("transPropertyCode")) === "PACKAGE" 
				|| (waybill.transPropertyCode || waybill.get("transPropertyCode")) === "RCP"
				|| (waybill.transPropertyCode || waybill.get("transPropertyCode")) === "EPEP")? "EXPRESS" : "LTL"; 
			
			var existFlag = (existWaybill.get("transPropertyCode") === "PACKAGE"
				||existWaybill.get("transPropertyCode") === "RCP" 
				||existWaybill.get("transPropertyCode") === "EPEP") ? "EXPRESS" : "LTL";
			if(transPropertyCode != existFlag){
				Ext.ux.Toast.msg('提示', '添加失败，商务专递货不能与其他货混装在同一个包中！', 'error');
				return false;
			}
		}
	}
	
	return true;
};

// 定义包明细列表
Ext.define('Foss.load.expresspackageaddnew.packageDetailGrid', {
	extend : 'Ext.grid.Panel',
	frame : true,
	title : load.expresspackageaddnew.i18n('foss.load.expresspackageaddnew.waybillGrid.gridTitle')/*'包明细'*/,
//	bodyCls : 'autoHeight',
	height : 500,
	cls : 'autoHeight',
	enableColumnHide : false,//配置该属性可取消grid自定义显示列功能
	columnLines: true,
	autoScroll : true,
	collapsible : true,
	animCollapse : true,
	store : Ext.create('Foss.load.expresspackageaddnew.packageDetailStore'),
	// 定义行展开
	plugins : [ {
		header: true,
		ptype : 'rowexpander',
		// 定义行展开模式（单行与多行），默认是多行展开(值true)
		rowsExpander : false,
		// 行体内容
		rowBodyElement : 'Foss.load.expresspackageaddnew.WaybillDetailGrid',
		pluginId : 'Foss_expresspackageaddnew_mainPage_serialNoGrid_ID'
	},Ext.create('Ext.grid.plugin.CellEditing', {
            clicksToEdit : 1,
            listeners : {
				'beforeedit' : function(editor,e,eOpts){
					//如果已经保存，则禁止编辑
					if(load.expresspackageaddnew.isSaved == 'Y'){
						return false;
					}
				}
			}
        })],
	tbar : [{
			xtype : 'button',
			text : load.expresspackageaddnew.i18n('foss.load.expresspackageaddnew.waybillGrid.addWaybillButtonText')/*'添加运单'*/,
			tooltip : load.expresspackageaddnew.i18n('foss.load.expresspackageaddnew.waybillGrid.addWaybillButtonToolTipText')/*'点击可批量添加运单'*/,
			id : 'Foss_load_expresspackageaddnew_mainPage_queryButton_ID',
			name : 'queryWaybillButton',
			handler : function() {//必须填写交接类型、到达部门/外发代理
				var basicForm = load.expresspackageaddnew.addNewForm.getForm();
				var arriveOrgCode = basicForm.findField('arriveOrgCode');
				var departOrgCode = basicForm.findField('departOrgCode');
				//如果到达部门为空，不让添加
				if(arriveOrgCode.value == null){
					load.expresspackageaddnew.packageAlert(arriveOrgCode,Ext.MessageBox.WARNING);
					return;
				}
				//如果出发部门为空，不让添加
			/*	if(departOrgCode.value == null){
					load.expresspackageaddnew.packageAlert(departOrgCode,Ext.MessageBox.WARNING);
					return;
				}*/
				load.expresspackageaddnew.queryWayBillWindow.showAt(0,0);//显示添加运单窗口
			}
		},'->',{
			xtype : 'textfield',
			fieldLabel : load.expresspackageaddnew.i18n('foss.load.expresspackageaddnew.waybillGrid.waybillNoColumn')/*'运单号'*/,
			emptyText : load.expresspackageaddnew.i18n('foss.load.expresspackageaddnew.waybillGrid.quickAddTextfieldToolTipText')/*'输入运单号敲击回车'*/,
			labelWidth : 60,
			vtype : 'waybill',
			id : 'Foss_load_expresspackageaddnew_quickAddWaybillNo_ID',
			enableKeyEvents : true,
			listeners : {
				'keypress' : function(text,e,eOpts){
					//如果敲击回车键，则触发添加按钮事件
					if(e.getKey() == e.ENTER){
						var addButton = Ext.getCmp('Foss_load_expresspackageaddnew_quickAddButton_ID');
						addButton.handler();
					}
				}
			}
		},{
			xtype : 'container',
			html : '&nbsp'
		},{
			xtype : 'button',
			id : 'Foss_load_expresspackageaddnew_quickAddButton_ID',
			text : load.expresspackageaddnew.i18n('foss.load.expresspackageaddnew.waybillGrid.quickAddButtonText')/*'快速添加'*/,
			handler : function(){
				//基本信息form
				var basicForm = load.expresspackageaddnew.addNewForm.getForm();
				
				var arriveOrgCode = basicForm.findField('arriveOrgCode');
			//	var departOrgCode = basicForm.findField('departOrgCode');
				if(arriveOrgCode.value == null){
					load.expresspackageaddnew.packageAlert(arriveOrgCode,Ext.MessageBox.WARNING);
					return;
				}
				//如果出发部门为空，不让添加
			/*	if(departOrgCode.value == null){
					load.expresspackageaddnew.packageAlert(departOrgCode,Ext.MessageBox.WARNING);
					return;
				}*/
				var waybillNoCmp = Ext.getCmp('Foss_load_expresspackageaddnew_quickAddWaybillNo_ID'),
					waybillNo = waybillNoCmp.getValue();
				if(!Ext.isEmpty(waybillNo) && waybillNoCmp.isValid()){
					//获取参数
					var arriveDeptCode;
						arriveDeptCode = basicForm.findField('arriveOrgCode').getValue();
					var arriveDeptList = new Array();
					arriveDeptList.push(arriveDeptCode);
					var jsonData = {
						'packageVo' : {
							'queryWaybillForExpressPackageDto' : {
								'arriveDeptList' : arriveDeptList,
								'waybillNo' : waybillNo
							}
						}
					},
					mainGrid = load.expresspackageaddnew.packageDetailGrid,
					//获取主页面的扩展组件
					plugin = mainGrid.getPlugin('Foss_expresspackageaddnew_mainPage_serialNoGrid_ID');
					var loadMask = new Ext.LoadMask(mainGrid, {
						msg:"加载中，请稍候..."
					});
					//加载运单库存及流水号
					loadMask.show();
					Ext.Ajax.request({
						url : load.realPath('queryWaybillStockByWaybillNoForPackage.action'),
						jsonData : jsonData,
						success : function(response) {
							var result = Ext.decode(response.responseText),
								waybillStock = result.packageVo.waybillStock,
								serialNoList = result.packageVo.serialNoStockList;
							//检查是否存在 商务专递（快递空运），如果存在 则需要所有的运单运输性质需相同
							var transPropertyCode=waybillStock.transPropertyCode;
							//默认为非空运
							var isAirExpress=0;
							if(transPropertyCode=='DEAP'){
								isAirExpress=1;
							}
							//已经存在的运单运输性质集合
							var transPropertyCodeList = new Array();
				            var items = mainGrid.store.data.items;
				            if (items.length > 0) {
				                //遍历选定区
				                for (var j = 0; j < items.length; j++) {
				                	if(isAirExpress==1&&items[j].data.transPropertyCode!=transPropertyCode){
				                		 Ext.Msg.alert("提示：",'亲 [快递空运]包不能添加非商务专递的产品!');
				                		 return;
				                	}else{
				                		continue;
				                	}
				                	
				                }
				            }

							
							//从主页获取该运单号记录
							var record = mainGrid.store.findRecord('waybillNo',waybillNo,0,false,true,true);
							var unsavedSerialNoMap = new Ext.util.HashMap();
							for(var i in serialNoList){
								var serialNo = serialNoList[i],
									serialNoRecord = Ext.ModelManager.create(serialNo, 'Foss.load.expresspackageaddnew.serialNoModel');
								unsavedSerialNoMap.add(serialNo.serialNo,serialNoRecord);
							}
							if(record != null){//若该运单曾经被提交过，则将该运单的流水号累加
								record.set('serialNoMap',unsavedSerialNoMap);
								//更新主页列表中的件数等信息
								record.set('pieces',unsavedSerialNoMap.getCount());
								record.set('cubage',waybillStock.cubage);
								record.set('weight',waybillStock.weight);
								record.set('cubageAc',record.get('cubage'));
								record.set('weightAc',record.get('weight'));
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
								
								var tbAddWaybills = new Ext.util.MixedCollection();
								tbAddWaybills.add(waybillStock);
								
								if(!load.expresspackageaddnew.validatepackageDetailsTransProperty(tbAddWaybills, mainGrid.store.getAt(0))){
									loadMask.hide();
									waybillNoCmp.focus(true,true);
									return;
								}
								
								var newRecord =  Ext.ModelManager.create(waybillStock, 'Foss.load.expresspackageaddnew.waybillStockModel');
								newRecord.set('pieces',unsavedSerialNoMap.getCount());
								newRecord.set('serialNoMap',unsavedSerialNoMap);
								newRecord.set('cubageAc',newRecord.get('cubage'));
								newRecord.set('weightAc',newRecord.get('weight'));
								//将该运单插入主页面
								mainGrid.store.insert(mainGrid.store.getCount(),newRecord);
							}
							Ext.ux.Toast.msg(load.expresspackageaddnew.i18n('foss.load.expresspackageaddnew.waybillGrid.alertInfoTitle')/*'提示'*/, 
								'添加成功！', 
								'info');
							waybillNoCmp.focus(true,true);
							loadMask.hide();
						},
						exception : function(response){
							var result = Ext.decode(response.responseText);
							Ext.ux.Toast.msg(load.expresspackageaddnew.i18n('foss.load.expresspackageaddnew.waybillGrid.alertInfoTitle')/*'提示'*/, 
								load.expresspackageaddnew.i18n('foss.load.expresspackageaddnew.waybillGrid.addFailureAlert')/*'添加失败，'*/ + result.message, 
								'error');
							loadMask.hide();
						}
					});
				}else{
					Ext.ux.Toast.msg(load.expresspackageaddnew.i18n('foss.load.expresspackageaddnew.waybillGrid.alertInfoTitle')/*'提示'*/, 
								'请输入正确的运单号！', 
								'error',
								1500);
				}
			}
		},{
			xtype : 'container',
			html : '&nbsp'
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
			fieldLabel : load.expresspackageaddnew.i18n('foss.load.expresspackageaddnew.waybillGrid.totalWaybillLabel')/*'总票数'*/,
			xtype : 'textfield',
			readOnly : true,
			columnWidth : 1/5,
			value : 0,
			id : 'Foss_load_expresspackageaddnew_MainPageTotalCount'
			},{
				fieldLabel : load.expresspackageaddnew.i18n('foss.load.expresspackageaddnew.waybillGrid.totalPiecesLabel')/*'总件数'*/,
				xtype : 'textfield',
				readOnly : true,
				columnWidth : 1/5,
				value : 0,
				id : 'Foss_load_expresspackageaddnew_MainPageTotalPieces'
			},{
				fieldLabel : '总重量(千克)',
				xtype : 'textfield',
				readOnly : true,
				columnWidth : 1/4,
				value : 0,
				id : 'Foss_load_expresspackageaddnew_MainPageTotalWeight'
			},{
				fieldLabel : '总体积(方)',
				xtype : 'textfield',
				readOnly : true,
				columnWidth : 1/4,
				value : 0,
				id : 'Foss_load_expresspackageaddnew_MainPageTotalVolume'
			}]
	  }],
	columns : [ {
		xtype : 'actioncolumn',
		width : 60,
		text : load.expresspackageaddnew.i18n('foss.load.expresspackageaddnew.waybillGrid.actionColumn')/*'操作'*/,
		align : 'center',
		items : [ {
			iconCls : 'deppon_icons_remove',
			tooltip : load.expresspackageaddnew.i18n('foss.load.expresspackageaddnew.waybillGrid.deleteButtonColumn')/*'删除'*/,
			handler : function(grid, rowIndex, colIndex) {
				grid.getStore().removeAt(rowIndex);
			}
		} ]
	}, {
		dataIndex : 'waybillNo',
		align : 'center',
		width : 80,
		xtype : 'ellipsiscolumn',
		text : load.expresspackageaddnew.i18n('foss.load.expresspackageaddnew.waybillGrid.waybillNoColumn')/*'运单号'*/
	}, {
		dataIndex : 'transProperty',
		align : 'center',
		width : 95,
		text : load.expresspackageaddnew.i18n('foss.load.expresspackageaddnew.waybillGrid.transPropertyColumn')/*'运输性质'*/
	}, {
		dataIndex : 'pieces',
		align : 'center',
		flex : 1,
		text : load.expresspackageaddnew.i18n('foss.load.expresspackageaddnew.waybillGrid.piecesColumn')/*'已配件数'*/
	}, {
		dataIndex : 'weight',
		align : 'center',
		flex : 1,
		text : load.expresspackageaddnew.i18n('foss.load.expresspackageaddnew.waybillGrid.weightColumn')/*'已配重量'*/
	}, {
		dataIndex : 'weightAc',
		align : 'center',
		flex : 1,
		text : load.expresspackageaddnew.i18n('foss.load.expresspackageaddnew.waybillGrid.weightAcColumn')/*'实际重量'*/
	}, {
		dataIndex : 'cubage',
		align : 'center',
		flex : 1,
		text : load.expresspackageaddnew.i18n('foss.load.expresspackageaddnew.waybillGrid.volumeColumn')/*'已配体积'*/
	}, {
		dataIndex : 'cubageAc',
		align : 'center',
		flex : 1,
		text : load.expresspackageaddnew.i18n('foss.load.expresspackageaddnew.waybillGrid.volumeAcColumn')/*'实际体积'*/
	}, {
		dataIndex : 'note',
		align : 'center',
		xtype : 'ellipsiscolumn',
		flex : 1,
		text : load.expresspackageaddnew.i18n('foss.load.expresspackageaddnew.waybillGrid.noteColumn')/*'备注'*/,
		editor : {
			xtype : 'textarea',
			maxLength : 300
		}
	}, {
		dataIndex : 'goodsName',
		align : 'center',
		flex : 1,
		text : load.expresspackageaddnew.i18n('foss.load.expresspackageaddnew.waybillGrid.goodsNameColumn')/*'货物名称'*/
	}, {
		dataIndex : 'packing',
		align : 'center',
		flex : 1,
		text : load.expresspackageaddnew.i18n('foss.load.expresspackageaddnew.waybillGrid.packingColumn')/*'包装'*/
	}, {
		dataIndex : 'waybillNote',
		align : 'center',
		flex : 1,
		text : load.expresspackageaddnew.i18n('foss.load.expresspackageaddnew.waybillGrid.waybillNoteColumn')/*'运单备注'*/
	} ]
});

//生成当前日期
load.expresspackageaddnew.packageGetDateTime = function(){
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
load.expresspackageaddnew.arriveOrgCode = null;

// 包基本信息form
Ext.define('Foss.load.expresspackageaddnew.AddNewForm', {
	extend : 'Ext.form.Panel',
	title : load.expresspackageaddnew.i18n('foss.load.expresspackageaddnew.basicInfoForm.formTitle'),
	frame : true,
	collapsible : true,
	height : 150,
	animCollapse : true,
	defaults : {
		margin : '5 10 5 10',
		labelWidth : 85,
		columnWidth : 1 / 3,
		xtype : 'textfield'
	},
	layout : 'column',
	items : [{
		fieldLabel : load.expresspackageaddnew.i18n('foss.load.expresspackageaddnew.basicInfoForm.packageNoLabel')/*'包号'*/,
		name : 'packageNo',
		allowBlank : false,
		listeners : {
			'blur' : function(cmp, eO, eOpts) {
						var packageNo = cmp.getValue();
						//如果包号长度不能超过45
						if(packageNo.length !=10 && packageNo.length !=12){
							Ext.MessageBox.alert(load.expresspackageaddnew.i18n('foss.load.expresspackageaddnew.waybillGrid.alertInfoTitle'),"包号只能是B后面加9位或者11位的数字，请重新输入！");
						}
						//是否已经存在包号
						if (cmp.getValue() != null && cmp.getValue().trim() != ''){
							Ext.Ajax.request({
								url : load.realPath('queryIsExistPackageNo.action'),
								jsonData : {
									'packageVo' : {'expressPackageSaveConditionDto': {
										'packageEntity' : {
											'packageNo' : packageNo
											}
										}
									}
								},
								success : function(response){
									var result = Ext.decode(response.responseText);
									//如果不存在
									if(result.packageVo.expressPackageSaveConditionDto.isExistPackageNo){
										Ext.MessageBox.alert(load.expresspackageaddnew.i18n('foss.load.expresspackageaddnew.waybillGrid.alertInfoTitle'),"包号已经存在，请重新输入！");
									}
								},
								exception : function(response){
									var result = Ext.decode(response.responseText);
					    			top.Ext.MessageBox.alert(load.expresspackageaddnew.i18n('foss.load.expresspackageaddnew.waybillGrid.alertInfoTitle')/*'提示'*/,result.message);
								}
							});
						}
					}
				}
	}, 
	/*{
		name : 'createTime',
		value : load.expresspackageaddnew.packageGetDateTime(),
	//	xtype:'hiddenfield'
		hidden : true
	},*/
	{
		fieldLabel : load.expresspackageaddnew.i18n('foss.load.expresspackageaddnew.basicInfoForm.departDeptLabel')/*'出发部门'*/,
		name : 'departOrgCode',
		readOnly : true,
		value : FossUserContext.getCurrentDept().name
	},{
		
		fieldLabel : load.expresspackageaddnew.i18n('foss.load.expresspackageaddnew.basicInfoForm.arriveDeptLabel')/*'到达部门'*/,
		allowBlank : false,
		name : 'arriveOrgCode',
		xtype : 'dynamicorgcombselector',
		type : 'ORG',
	//	salesDepartment : 'Y',
		transferCenter : 'Y',
	//	airDispatch : 'Y',
	//	doAirDispatch : 'Y',
		listeners : {
			'blur' : function(cmp, eO, eOpts) {
						// 如果包列表中存在有数据，说明已经添加过运单，一旦到达部门(cmp.getValue()应该拿到的是选择的部门code)
						if (cmp.getValue() != null
							&& cmp.getValue().trim() != ''
							&& cmp.getValue().trim() != load.expresspackageaddnew.arriveOrgCode) {
							var rec = cmp.store.findRecord('code',cmp.getValue(),0,false,true,true);
							if (load.expresspackageaddnew.packageDetailGrid.store
									.getCount() > 0) {
								Ext.MessageBox.confirm(load.expresspackageaddnew.i18n('foss.load.expresspackageaddnew.waybillGrid.alertInfoTitle')/*'提示'*/,
								'确定要更改到达部门吗？</br>这将导致已添加的运单被清空。',function(btn) {
									if (btn == 'yes') {
										load.expresspackageaddnew.packageDetailGrid.store.removeAll();
										load.expresspackageaddnew.arriveOrgCode = cmp.getValue();
										load.expresspackageaddnew.arriveDeptName = rec.get('name');//定义全局变量
										return;
									} else {
										cmp.setCombValue(load.expresspackageaddnew.arriveDeptName,load.expresspackageaddnew.arriveOrgCode);
										return;
									}
								});
							}else{
								load.expresspackageaddnew.arriveOrgCode = cmp.getValue();
								load.expresspackageaddnew.arriveDeptName = rec.get('name');
							}
						}
						//到达部门
						var arriveDeptList = new Array();
						arriveDeptList.push(cmp.getValue());
						
						//获取外场，判断是否存在走货路径
						if (cmp.getValue() != null && cmp.getValue().trim() != ''){
							Ext.Ajax.request({
								url : load.realPath('queryExpressLineIsExist.action'),
								jsonData : {
									'packageVo' : {
										'queryWaybillForExpressPackageDto' : {
											'arriveDeptList' : arriveDeptList
										}
									}
								},
								success : function(response){
									var result = Ext.decode(response.responseText);
									var basicInfoForm = load.expresspackageaddnew.addNewForm.getForm();
									//如果不存在该走货路径
									if(!result.packageVo.queryWaybillForExpressPackageDto.isExistPackageLine){
										Ext.MessageBox.alert(load.expresspackageaddnew.i18n('foss.load.expresspackageaddnew.waybillGrid.alertInfoTitle'),"不存在该条始发线路，请重新选择到达部门！");
									}
								},
								exception : function(response){
									var result = Ext.decode(response.responseText);
					    			top.Ext.MessageBox.alert(load.expresspackageaddnew.i18n('foss.load.expresspackageaddnew.waybillGrid.alertInfoTitle')/*'提示'*/,result.message);
								}
							});
						}
					}
				}
	
	},{
		fieldLabel : load.expresspackageaddnew.i18n('foss.load.expresspackageaddnew.basicInfoForm.userCodeLabel')/*'建包员工号'*/,
		name : 'createUserCode',
		readOnly : true,
		value : FossUserContext.getCurrentUserEmp().empCode
	},  {
		fieldLabel : load.expresspackageaddnew.i18n('foss.load.expresspackageaddnew.basicInfoForm.userNameLabel')/*'建包员姓名'*/,
		name : 'createUserName',
		readOnly : true,
		value : FossUserContext.getCurrentUserEmp().empName
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
});

// 定义查询包交接运单界面-查询条件form
Ext.define('Foss.load.expresspackageaddnew.QueryWaybillForm', {
	extend : 'Ext.form.Panel',
	title : load.expresspackageaddnew.i18n('foss.load.expresspackageaddnew.queryWaybillForm.formTitle')/*'查询条件'*/,
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
		fieldLabel : load.expresspackageaddnew.i18n('foss.load.expresspackageaddnew.queryWaybillForm.instorageTime')/*'入库时间'*/,
		columnWidth : 2/5,
		// 类型为datetimefield_date97需要配置fieldId,可以赋予此属性任何唯一标 //识的String值
		fieldId : 'Foss_expresspackageaddnew_QueryForm_InstorageTime_fieldID',
		id : 'Foss_expresspackageaddnew_QueryForm_InstorageTime_ID',
		// dateType: 'datetimefield_date97',
		dateType: 'datefield',
		fromName : 'beginInStorageTime',
		toName : 'endInStorageTime',
		toValue : new Date(),
		fromValue : new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate() - 20),
		allowBlank : false,
		disallowBlank: true
	},/*{
		fieldLabel : load.expresspackageaddnew.i18n('foss.load.expresspackageaddnew.queryWaybillForm.transType'),//'运输类型'
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
	},*/ {
		fieldLabel : load.expresspackageaddnew.i18n('foss.load.expresspackageaddnew.waybillGrid.transPropertyColumn')/*'运输性质'*/,
		name : 'transProperty',
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
		            {"key":"PACKAGE", "value":"标准快递"},
		            {"key":"RCP", "value":"3.60特惠件"},
		            {"key":"EPEP", "value":"电商尊享"},
		            {"key":"DEAP", "value":"商务专递"}

		    ]
		})
	}, {
		fieldLabel : load.expresspackageaddnew.i18n('foss.load.expresspackageaddnew.waybillGrid.waybillNoColumn')/*'运单号'*/,
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
			text : load.expresspackageaddnew.i18n('foss.load.expresspackageaddnew.queryWaybillForm.resetButton')/*'重置'*/,
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
			id : 'Foss_expresspackageaddnew_QueryForm_queryButton_ID',
			text : load.expresspackageaddnew.i18n('foss.load.expresspackageaddnew.queryWaybillForm.queryButton')/*'查询'*/,
			handler : function(){
					load.expresspackageaddnew.pagingBar.moveFirst();
					var sm = load.expresspackageaddnew.queryWaybillGrid.getSelectionModel();
					sm.deselectAll(true);//note...............
					//清空统计信息
					Ext.getCmp('Foss_load_expresspackageaddnew_QueryPageTotalPieces').setValue(0);
					Ext.getCmp('Foss_load_expresspackageaddnew_QueryPageTotalWeight').setValue(0);
					Ext.getCmp('Foss_load_expresspackageaddnew_QueryPageTotalCubage').setValue(0);
					Ext.getCmp('Foss_load_expresspackageaddnew_QueryPageTotalMoney').setValue(0);
					//每次查询前清空map
					load.expresspackageaddnew.selectedWaybillMap.clear();
			}
		} ]
	} ],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
});

// 定义查询库存运单store
Ext.define('Foss.load.expresspackageaddnew.QueryWaybillStore', {
	extend : 'Ext.data.Store',
	// 绑定一个模型
	model : 'Foss.load.expresspackageaddnew.waybillStockModel',//note.............
	buffered : false,
	// 定义一个代理对象
	proxy : {
		type : 'ajax',
		actionMethods:'POST',
		// 请求的url
		url : load.realPath('queryWaybillStockListForPackage.action'),
		// 定义一个读取器
		reader : {
			// 以JSON的方式读取
			type : 'json',
			// 定义读取JSON数据的根对象
			root : 'packageVo.waybillStockList',
			totalProperty : 'totalCount',
			successProperty: 'success'
		}
	},
	buttonLoadMask : null,
	getButtonLoadMask : function(){
		if(this.buttonLoadMask == null){
			this.buttonLoadMask = new Ext.LoadMask(Ext.getCmp('Foss_expresspackageaddnew_QueryForm_queryButton_ID'),{
				msg : '.....'
			});
		}
		return this.buttonLoadMask;
	},
	gridLoadMask : null,
	getGridLoadMask : function(){
		if(this.gridLoadMask == null){
			this.gridLoadMask = new Ext.LoadMask(load.expresspackageaddnew.queryWaybillGrid, {
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
			var sm = load.expresspackageaddnew.queryWaybillGrid.getSelectionModel(),
				record,
				waybillNo;
			for(var i in records){
				record = records[i];
				waybillNo = record.get('waybillNo');
				//tempWaybillMap.add(waybillNo,record);
				//数据加载后，检查之前是否被勾选，若缓存map中记录的有该运单号，则重新勾选
				if(load.expresspackageaddnew.selectedWaybillMap.get(waybillNo) != null){
					sm.select(record,true,true);//勾选第一层表格中的行：第二个参数为true，保留其他已勾选的行，第三个参数为true，表示勾选后不触发select事件
				}
			};
			this.getButtonLoadMask().hide();
			this.getGridLoadMask().hide();
		},
		'beforeload' : function(store, operation, eOpts){
			this.getGridLoadMask().show();
			this.getButtonLoadMask().show();
			var form =  load.expresspackageaddnew.queryWaybillForm.getForm();
			var arriveDeptCode = load.expresspackageaddnew.addNewForm.getForm().findField('arriveOrgCode').getValue();
			if(!form.findField('beginInStorageTime').isValid()){
				Ext.ux.Toast.msg(load.expresspackageaddnew.i18n('foss.load.expresspackageaddnew.waybillGrid.alertInfoTitle')/*'提示'*/, load.expresspackageaddnew.i18n('foss.load.expresspackageaddnew.queryResultTab.input1stDate')/*'请输入起始日期'*/, 'error', 2000);
				return false;
			}
			//因为入库时间只有年月日，故天数加一天
			var endInStorageTime = form.findField('endInStorageTime').getValue();
			if(!form.findField('endInStorageTime').isValid()){
				Ext.ux.Toast.msg(load.expresspackageaddnew.i18n('foss.load.expresspackageaddnew.waybillGrid.alertInfoTitle')/*'提示'*/, load.expresspackageaddnew.i18n('foss.load.expresspackageaddnew.queryResultTab.input2ndDate')/*'请输入截止日期'*/, 'error', 2000);
				return false;
			}
			endInStorageTime = new Date(endInStorageTime.getFullYear(),endInStorageTime.getMonth(),endInStorageTime.getDate() + 1)
			//到达部门
			var arriveDeptList = new Array();
			arriveDeptList.push(arriveDeptCode);
			//运输性质
			var transPropertyList = new Array();
			transPropertyList.push( form.findField('transProperty').getValue());
			Ext.apply(operation, {
				params : {
					'packageVo.queryWaybillForExpressPackageDto.beginInStorageTime' : form.findField('beginInStorageTime').getValue(),
					'packageVo.queryWaybillForExpressPackageDto.endInStorageTime' : endInStorageTime,
					'packageVo.queryWaybillForExpressPackageDto.waybillNo' : form.findField('waybillNo').getValue(),
					'packageVo.queryWaybillForExpressPackageDto.transPropertyList' : transPropertyList,//运输性质
					'packageVo.queryWaybillForExpressPackageDto.arriveDeptList' : arriveDeptList//到达部门
				}
			});
		}
	}
});

/**
 * 定义map，selectedWaybillMap：key，运单号，value，选中的某行运单库存列
 * 						  用于记录被勾选的运单库存
 */
load.expresspackageaddnew.selectedWaybillMap = new Ext.util.HashMap();

// 定义库存运单查询结果列表
Ext.define('Foss.load.expresspackageaddnew.QueryWaybillGrid', {
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
	emptyText : load.expresspackageaddnew.i18n('foss.load.expresspackageaddnew.queryResultTab.emptyText')/*'查询结果为空'*/,
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.load.expresspackageaddnew.QueryWaybillStore');
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
		load.expresspackageaddnew.pagingBar = me.bbar;
		me.callParent([cfg]);
	},
	dockedItems: [{
	    xtype: 'toolbar',
	    id : 'Foss_load_expresspackageaddnew_QueryPageTotalPieces_toobar',
	    dock: 'bottom',
	    layout : 'column',
	    defaults: {
			xtype : 'textfield',
			readOnly : true,
			labelWidth : 120
		},
		items: [{
				id : 'Foss_load_expresspackageaddnew_QueryPageTotalPieces',
				fieldLabel: load.expresspackageaddnew.i18n('foss.load.expresspackageaddnew.queryResultTab.totalPieces')/*'已选总件数'*/,
				columnWidth : 1/2,
				value : 0
			},{
				id : 'Foss_load_expresspackageaddnew_QueryPageTotalWeight',
				fieldLabel: '已选总重量(千克)',
				columnWidth : 1/2,
				value : 0
			},{
				id : 'Foss_load_expresspackageaddnew_QueryPageTotalCubage',
				fieldLabel: '已选总体积(方)',
				columnWidth : 1/2,
				value : 0
			},{
				id : 'Foss_load_expresspackageaddnew_QueryPageTotalMoney',
				fieldLabel: load.expresspackageaddnew.i18n('foss.load.expresspackageaddnew.queryResultTab.totalMoney')/*'已选总金额'*/,
				columnWidth : 1/2,
				value : 0
			}]
    }],
	// 定义行展开
	plugins : [ {
		header: true,
		ptype : 'rowexpander',
		// 定义行展开模式（单行与多行），默认是多行展开(值true)
		pluginId : 'expresspackageaddnew_queryWaybillGrid_serialNoGrid',
		rowsExpander : false,
		// 行体内容
		rowBodyElement : 'Foss.load.expresspackageaddnew.QueryWaybillSerialNoGrid'
	} ],
	rightMove : function(grid,record,rowIndex,type){
		var waybillNo = record.get('waybillNo');
		unsubmitedGrid = load.expresspackageaddnew.unsubmitedWaybillGrid;
		unStore = unsubmitedGrid.store;
		unRec = unStore.findRecord('waybillNo',waybillNo,0,false,true,true);
		//获取主页面该运单号的record
		unsavedWaybill = load.expresspackageaddnew.packageDetailGrid.store.findRecord('waybillNo',waybillNo,0,false,true,true);
		serialNoList = record.get('serialNoStockList');
		
		//检查是否存在 商务专递（快递空运），如果存在 则需要所有的运单运输性质需相同
		var productCode=record.data.transPropertyCode;
		//默认为非空运
		var isAirExpress=0;
		if(productCode=='DEAP'){
			isAirExpress=1;
		}
		//待选grid中已经存在的运单运输性质集合
		
        var items = unsubmitedGrid.store.data.items;
        if (items.length > 0) {
            //遍历选定区
            for (var j = 0; j < items.length; j++) {
            	if(isAirExpress==1&&items[j].data.transPropertyCode!=productCode){
				     Ext.Msg.alert("提示：",'亲 [快递空运]包不能添加非商务专递的产品!');
            		 return;
            	}else{
            		continue;
            	}
            	
            }
        }
		
		flag = false;//判断该运单是否已勾选
		//如果已勾选运单map中有此运单，则移除(如果在map中有这条运单就应该移除掉)
		if(load.expresspackageaddnew.selectedWaybillMap.get(waybillNo) !== undefined){
			load.expresspackageaddnew.selectedWaybillMap.removeAtKey(waybillNo);
			flag = true;
		}
		//右侧grid中是否有该运单
		if(unRec === null){//如果右侧没有运单
			var serialNoMap = new Ext.util.HashMap();
			for(var i in serialNoList){
				var serialNo = serialNoList[i],
				isCreatedPackage = serialNo.isCreatedPackage;
				//如果主页面没有的流水号，并且是未预配状态，则可右移
				if((unsavedWaybill === null || unsavedWaybill.get('serialNoMap').get(serialNo.serialNo) === undefined)
					&& isCreatedPackage !== 'Y'){
					var serialNoRecord = Ext.ModelManager.create(serialNo, 'Foss.load.expresspackageaddnew.serialNoModel');
					serialNoMap.add(serialNo.serialNo,serialNoRecord);
				}
			}
			//如果serialNoMap中有数据,就把map中的数据insert到store中
			if(serialNoMap.getCount() !== 0){
				record.set('serialNoMap',serialNoMap);
				//插入右侧grid
				var recCopy = record.copy(),
					pieces = serialNoMap.getCount();
				recCopy.set('weight',((recCopy.get('weight')/recCopy.get('pieces'))*pieces).toFixed(2));
				recCopy.set('cubage',((recCopy.get('cubage')/recCopy.get('pieces'))*pieces).toFixed(2));
				recCopy.set('pieces',pieces);
				unStore.insert(unStore.getCount(),recCopy);
			}
		}else{//如果右侧有运单
			var serialNoMap = unRec.get('serialNoMap');
			for(var i in serialNoList){
				var serialNo = serialNoList[i],
				isCreatedPackage = serialNo.isCreatedPackage;
				//如果主页面没有的流水号，并且是未预配状态，则可右移
				if((unsavedWaybill === null || unsavedWaybill.get('serialNoMap').get(serialNo.serialNo) === undefined)
					&& isCreatedPackage !== 'Y'){
					var serialNoRecord = Ext.ModelManager.create(serialNo, 'Foss.load.expresspackageaddnew.serialNoModel');
					serialNoMap.replace(serialNo.serialNo,serialNoRecord);//replace方法，有数据会被替换，没有数据会新增
				}
			}
			//更新件数、重量、体积
			var pieces = serialNoMap.getCount();
			unRec.set('weight',((unRec.get('weight')/unRec.get('pieces'))*pieces).toFixed(2));
			unRec.set('cubage',((unRec.get('cubage')/unRec.get('pieces'))*pieces).toFixed(2));
			unRec.set('pieces',serialNoMap.getCount());
		}
		var plugin = unsubmitedGrid.getPlugin('expresspackageaddnew_unsubmitedWaybillGrid_serialNoGrid');
		if(!Ext.isEmpty(plugin.getExpendRow())) {
			var item = plugin.getExpendRowBody();
			var innerStore = item.getStore();
			var subWaybillNo = innerStore.getAt(0).get('waybillNo');
			if(waybillNo == subWaybillNo){
				innerStore.loadData(serialNoMap.getValues());//重新加载map中的数据，此时map中包含了，之前没有的数据
			}
		}
		//如果是单条移除，则逐条移除
		if(type === 'ONE'){
			//移除该运单
			grid.store.removeAt(rowIndex);
			//如果勾选map有变动，则重新统计信息
			if(flag){
				load.expresspackageaddnew.updateQueryPageStaInfo();
			}
		}
		//重新统计已选运单
		load.expresspackageaddnew.updateUnsubmitedWaybillStaInfo();
		return flag;
	},
	columns : [ {
		xtype : 'actioncolumn',
		width : 40,
		text : load.expresspackageaddnew.i18n('foss.load.expresspackageaddnew.waybillGrid.actionColumn')/*'操作'*/,
		align : 'center',
		items : [ {
			tooltip : load.expresspackageaddnew.i18n('foss.load.expresspackageaddnew.queryResultTab.moveRight')/*'右移'*/,
			iconCls : 'foss_icons_stl_sendmes',
			handler : function(grid, rowIndex, colIndex) {
				var record = grid.store.getAt(rowIndex);
				load.expresspackageaddnew.queryWaybillGrid.rightMove(grid,record,rowIndex,'ONE');
			}
		} ]
	}, {
		dataIndex : 'waybillNo',
		align : 'center',
		width : 80,
		xtype : 'ellipsiscolumn',
		text : load.expresspackageaddnew.i18n('foss.load.expresspackageaddnew.waybillGrid.waybillNoColumn')/*'运单号'*/
	}, {
		dataIndex : 'transProperty',
		align : 'center',
		width : 95,
		text : load.expresspackageaddnew.i18n('foss.load.expresspackageaddnew.waybillGrid.transPropertyColumn')/*'运输性质'*/
	}, {
		dataIndex : 'goodsName',
		align : 'center',
		width : 60,
		xtype : 'ellipsiscolumn',
		text : load.expresspackageaddnew.i18n('foss.load.expresspackageaddnew.waybillGrid.goodsNameColumn')/*'货物名称'*/
	}, {
		dataIndex : 'packing',
		align : 'center',
		width : 60,
		xtype : 'ellipsiscolumn',
		text : load.expresspackageaddnew.i18n('foss.load.expresspackageaddnew.waybillGrid.packingColumn')/*'包装'*/
	}, {
		dataIndex : 'cubage',
		align : 'center',
		width : 60,
		xtype : 'ellipsiscolumn',
		text : load.expresspackageaddnew.i18n('foss.load.expresspackageaddnew.waybillGrid.volume')/*'体积'*/
	}, {
		dataIndex : 'weight',
		align : 'center',
		width : 60,
		xtype : 'ellipsiscolumn',
		text : load.expresspackageaddnew.i18n('foss.load.expresspackageaddnew.waybillGrid.weight')/*'重量'*/
	}, {
		dataIndex : 'pieces',
		align : 'center',
		flex : 1,
		text : load.expresspackageaddnew.i18n('foss.load.expresspackageaddnew.waybillGrid.piecees')/*'件数'*/
	}, {
		dataIndex : 'instorageDate',
		align : 'center',
		width : 90,
		text : load.expresspackageaddnew.i18n('foss.load.expresspackageaddnew.waybillGrid.waybillDate')/*'入库日期'*/,
		renderer : function(value) {//renderer方法:在加载前执行
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
		text : load.expresspackageaddnew.i18n('foss.load.expresspackageaddnew.basicInfoForm.arriveDeptLabel')/*'到达部门'*/
	}, {
		dataIndex : 'insuranceValue',
		align : 'center',
		width : 60,
		xtype : 'ellipsiscolumn',
		text : load.expresspackageaddnew.i18n('foss.load.expresspackageaddnew.waybillGrid.insuranceValue')/*'保险价值'*/
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
		text : load.expresspackageaddnew.i18n('foss.load.expresspackageaddnew.waybillGrid.waybillPieces')/*'开单件数'*/
	}, {
		dataIndex : 'isPreciousGoods',
		align : 'center',
		width : 80,
		text : load.expresspackageaddnew.i18n('foss.load.expresspackageaddnew.waybillGrid.isPreciousGoods')/*'是否贵重物品'*/,
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
		text : load.expresspackageaddnew.i18n('foss.load.expresspackageaddnew.waybillGrid.waybillNoteColumn')/*'运单备注'*/
	} ],
	getRightMenu : function(record,rowIndex){
		var grid = this;
		function rightMoveOne(){
			grid.rightMove(grid,record,rowIndex,'ONE');
		}
		var	rightMenu =	new Ext.menu.Menu({
	        items : [{
                text : load.expresspackageaddnew.i18n('foss.load.expresspackageaddnew.queryResultTab.moveRight')/*'右移'*/,
                handler : rightMoveOne
	        }]
	     });
		 return rightMenu;
	},
	listeners : {
		'select' : function(rowModel, record, index, eOpts ){
			var grid = this,
				plugin = grid.getPlugin('expresspackageaddnew_queryWaybillGrid_serialNoGrid'),
				waybillNo = record.get('waybillNo'),
				unsavedWaybill = load.expresspackageaddnew.packageDetailGrid.store.findRecord('waybillNo',waybillNo,0,false,true,true),
				unsubmitedWaybill = load.expresspackageaddnew.unsubmitedWaybillGrid.store.findRecord('waybillNo',waybillNo,0,false,true,true);
			//取出record中的流水号list，做成map，放到第一层map的record中
			var serialNoStockList = record.get('serialNoStockList'),
				serialNoStockMap = new Ext.util.HashMap();
			for(var i in serialNoStockList){
				var serialNo = serialNoStockList[i];
				isCreatedPackage = serialNo.isCreatedPackage;
				if((unsavedWaybill != null && unsavedWaybill.get('serialNoMap') !== '' && unsavedWaybill.get('serialNoMap').get(serialNo.serialNo) !== undefined)
					||(unsubmitedWaybill != null && unsubmitedWaybill.get('serialNoMap') !== '' && unsubmitedWaybill.get('serialNoMap').get(serialNo.serialNo) !== undefined)
						|| isCreatedPackage == 'Y'){
					continue;
				}
				var serialNoRecord = Ext.ModelManager.create(serialNoStockList[i], 'Foss.load.expresspackageaddnew.serialNoModel');
				serialNoStockMap.replace(serialNo.serialNo,serialNoRecord);
			}
			if(serialNoStockMap.getCount() !== 0){
				//将运单置于已勾选运单map中
				var recCopy = record.copy();
				load.expresspackageaddnew.selectedWaybillMap.add(waybillNo,recCopy);
				recCopy.set('serialNoMap',serialNoStockMap);
				load.expresspackageaddnew.updateQueryPageStaInfo();
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
		var plugin = grid.getPlugin('expresspackageaddnew_queryWaybillGrid_serialNoGrid');
		if(!Ext.isEmpty(plugin.getExpendRow())) {
			var item = plugin.getExpendRowBody();
			var store = item.getStore();
			var waybillNo = store.getAt(0).get('waybillNo');
			if(waybillNo == record.get('waybillNo')){
				item.getSelectionModel().deselectAll(true);
			}
		}
		var waybillNo = record.get('waybillNo');
		if(load.expresspackageaddnew.selectedWaybillMap.get(waybillNo) !== undefined){
			// 从map中移除和此运单对应的库存信息
			load.expresspackageaddnew.selectedWaybillMap.removeAtKey(waybillNo);
		}
		record.set('serialNoMap','');
		load.expresspackageaddnew.updateQueryPageStaInfo();
	},
	'itemcontextmenu' : function(view,record,item,index,e, eOpts ){
		var menu = this.getRightMenu(record,index);
		e.preventDefault();
       	menu.showAt(e.getXY());
	}
}
});

// 定义查询库存运单中流水号列表store
Ext.define('Foss.load.expresspackageaddnew.QueryWaybillSerialNoStore', {
	extend : 'Ext.data.Store',
	// 绑定一个模型
	model : 'Foss.load.expresspackageaddnew.serialNoModel',
	listeners : {
		'remove' : function(store, record, index, eOpts){
			var waybillNo = record.get('waybillNo'),
				serialNo = record.get('serialNo');
			//将移除的流水号从一级record的stockList中删除
			var superRec = load.expresspackageaddnew.queryWaybillGrid.store.findRecord('waybillNo',waybillNo,0,false,true,true),
				serialNoStockList = superRec.get('serialNoStockList');
			for(var i = 0;i < serialNoStockList.length;i++){
				var serialNoRec = serialNoStockList[i];
				if(serialNoRec.serialNo === serialNo){
					serialNoStockList.splice(i,1);//将第i个元素用1替换
					break;
				}
			}
		}
	}
});

// 定义查询库存运单中流水号列表grid
Ext.define('Foss.load.expresspackageaddnew.QueryWaybillSerialNoGrid', {
	extend : 'Ext.grid.Panel',
	columnLines: true,
	frame : true,
	width : 362,
	baseCls : 'package_queryWayBill_serialNoGap',
	// collapsible : true,
	animCollapse : true,
	viewConfig : {
		getRowClass: function(record, rowIndex, rowParams, store) {
			var waybillNo = record.get('waybillNo'),
				serialNo = record.get('serialNo'),
				isCreatedPackage = record.get('isCreatedPackage'),
				unsubmitedWaybill = load.expresspackageaddnew.unsubmitedWaybillGrid.store.findRecord('waybillNo',waybillNo,0,false,true,true),
				unsavedWaybill = load.expresspackageaddnew.packageDetailGrid.store.findRecord('waybillNo',waybillNo,0,false,true,true);
			if(!(isCreatedPackage !== 'Y'
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
		text : load.expresspackageaddnew.i18n('foss.load.expresspackageaddnew.waybillGrid.actionColumn')/*'操作'*/,
		align : 'center',
		items : [ {
			tooltip : load.expresspackageaddnew.i18n('foss.load.expresspackageaddnew.queryResultTab.moveRight')/*'右移'*/,
			iconCls : 'foss_icons_stl_sendmes',
			handler : function(grid, rowIndex, colIndex) {
				var store = grid.store;
				if(store.getCount() === 1){
					Ext.ux.Toast.msg(load.expresspackageaddnew.i18n('foss.load.expresspackageaddnew.waybillGrid.alertInfoTitle')/*'提示'*/, '请直接移动整个运单！', 'error', 1500);
					return;
				}
				if(this.iconCls === null){
					return;
				}
				var record = grid.store.getAt(rowIndex);
					waybillNo = record.get('waybillNo'),
					serialNo = record.get('serialNo'),
					isSelected = grid.getSelectionModel().isSelected(record),//检查grid中record这条记录是否被选择
					unsubmitedWaybill = load.expresspackageaddnew.unsubmitedWaybillGrid.store.findRecord('waybillNo',waybillNo,0,false,true,true),
					leftGrid = load.expresspackageaddnew.queryWaybillGrid,
					waybill = leftGrid.store.findRecord('waybillNo',waybillNo,0,false,true,true),
					pieces = waybill.get('pieces'),
					weight = waybill.get('weight'),
					cubage = waybill.get('cubage');
				//如果右侧有该运单
				if(unsubmitedWaybill !== null){
					unsubmitedWaybill.set('pieces',unsubmitedWaybill.get('pieces') + 1);
					unsubmitedWaybill.set('weight',(unsubmitedWaybill.get('weight') + weight/pieces).toFixed(2));
					unsubmitedWaybill.set('cubage',(unsubmitedWaybill.get('cubage') + cubage/pieces).toFixed(2));
					var tempMap = unsubmitedWaybill.get('serialNoMap');
					tempMap.replace(serialNo,record);
					var plugin = load.expresspackageaddnew.unsubmitedWaybillGrid.getPlugin('expresspackageaddnew_unsubmitedWaybillGrid_serialNoGrid');
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
					var tempMap = new Ext.util.HashMap()
					tempMap.replace(serialNo,record);
					rightWaybill.set('serialNoMap',tempMap);
					//插入右边的表格
					var rightGrid = load.expresspackageaddnew.unsubmitedWaybillGrid;
						rightStore = rightGrid.store;
					rightStore.insert(rightStore.getCount(),rightWaybill);
				}
				//移除该行
				store.removeAt(rowIndex);
				//如果当前行被选中
				if(isSelected){
					//从map中移除
					var serialNoMap = load.expresspackageaddnew.selectedWaybillMap.get(waybillNo).get('serialNoMap');
					serialNoMap.removeAtKey(serialNo);
					//如果map中没有任何元素
					if(serialNoMap.getCount() ===0){
						load.expresspackageaddnew.selectedWaybillMap.removeAtKey(waybillNo);
						//如果此时一级表中的行被选择，则反选
						var sm = leftGrid.getSelectionModel()
							isSelectedWaybill = sm.isSelected(waybill)
						//反选
						if(isSelectedWaybill){
							sm.deselect(waybill,true)
						}
					}
					load.expresspackageaddnew.updateQueryPageStaInfo();
				}
				//修改左边表格的数据
				waybill.set('pieces',pieces - 1);
				waybill.set('weight',((weight/pieces)*waybill.get('pieces')).toFixed(2));
				waybill.set('cubage',((cubage/pieces)*waybill.get('pieces')).toFixed(2));
				//重新统计已选运单
				load.expresspackageaddnew.updateUnsubmitedWaybillStaInfo();
			}
		} ],
		renderer : function(value,metaData,record,rowIndex,colIndex,store,view){
			var waybillNo = record.get('waybillNo'),
				serialNo = record.get('serialNo'),
				isCreatedPackage = record.get('isCreatedPackage'),
				unsubmitedWaybill = load.expresspackageaddnew.unsubmitedWaybillGrid.store.findRecord('waybillNo',waybillNo,0,false,true,true),
				unsavedWaybill = load.expresspackageaddnew.packageDetailGrid.store.findRecord('waybillNo',waybillNo,0,false,true,true);
			if(isCreatedPackage !== 'Y'
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
		text : load.expresspackageaddnew.i18n('foss.load.expresspackageaddnew.waybillGrid.serialNoColumn')/*'流水号'*/
	}, {
		dataIndex : 'instorageDate',
		align : 'center',
		width : 145,
		text : load.expresspackageaddnew.i18n('foss.load.expresspackageaddnew.queryWaybillForm.instorageTime')/*'入库时间'*/,
		renderer : function(value) {
			if(value!=null){
					var date = new Date(value);
					return Ext.Date.format(date, 'Y-m-d H:i:s');									
			}else{
					return null;
	}}
	},{
		dataIndex : 'isCreatedPackage',
		align : 'center',
		text : load.expresspackageaddnew.i18n('foss.load.expresspackageaddnew.queryResultTab.isCreatedPackage')/*'是否已建包'*/,
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
		var rec = load.expresspackageaddnew.selectedWaybillMap.get(waybillNo);
		if(rec !== undefined){
			var selectedSerialNoMap = rec.get('serialNoMap'),
				selectedSerialNo = [];
			store.each(function(rec){
				var serialNo = rec.get('serialNo');
				if(selectedSerialNoMap.get(serialNo) !== undefined){
					selectedSerialNo.push(rec);//向数组中添加一条数据
				}
			});
			var sm = grid.getSelectionModel();
			sm.select(selectedSerialNo,true,true);//选择一个记录实例通过记录实例或者索引。
		}
	},
		/*
			grid.getSelectionModel()
			记录在一个数据绑定组件内部被选择的记录
			这个一个抽象类，不是直接使用的。数据绑定UI小部件例如 Grid和Tree应该子类Ext.selection.Model 并且提供一个绑定到组件的方法。
			那些子类应该实现抽象方法onSelectChange 和 onLastFocusChanged 用来更新UI小部件。
		*/
	listeners : {
		'select' : function(rowModel,record,index,eOpts){
			var superGrid = this.superGrid;
			var serialNo = record.get('serialNo'),
				waybillNo = record.get('waybillNo'),
				isCreatedPackage = record.get('isCreatedPackage');
			var superRecord = superGrid.store.findRecord('waybillNo',waybillNo,0,false,true,true);//获取一级表格中该运单号对应的行记录
			var sm = superGrid.getSelectionModel();
			sm.select(superRecord,true,true);//勾选第一层表格中的行：第二个参数为true，为保留其他已勾选的行，第三个参数为true，表示勾选后不触发select事件
			//勾选后，将运单放入map中
			if(load.expresspackageaddnew.selectedWaybillMap.get(waybillNo) !== undefined){
				superRecord = load.expresspackageaddnew.selectedWaybillMap.get(waybillNo);
			}else{
				load.expresspackageaddnew.selectedWaybillMap.add(waybillNo,superRecord);
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
			load.expresspackageaddnew.updateQueryPageStaInfo();
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
				load.expresspackageaddnew.selectedWaybillMap.removeAtKey(waybillNo);
			}else{//如果第二层表格记录未全部反选，则从map中的选中行中删除该流水号对应的记录
				var selectedWaybill = load.expresspackageaddnew.selectedWaybillMap.get(waybillNo);
				serialNoMap = selectedWaybill.get('serialNoMap');
				serialNoMap.removeAtKey(serialNo);
			}
			load.expresspackageaddnew.updateQueryPageStaInfo();
		}
	},
	constructor : function(config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.load.expresspackageaddnew.QueryWaybillSerialNoStore');
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

//按钮panel
Ext.define('Foss.load.expresspackageaddnew.moveButtonPanel', {
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
			 tooltip : load.expresspackageaddnew.i18n('foss.load.expresspackageaddnew.moveButtonPanel.moveRightAllToolTip')/*'点击可将左侧全部运单移动到右侧'*/,
		     handler : function() {
		     	var myMask = load.expresspackageaddnew.queryWayBillWindow.getLoadMask('运单右移中，请稍后...');
		     	myMask.show();
		     	var myMask2 = new Ext.LoadMask(load.expresspackageaddnew.queryWayBillWindow, {msg : '运单右移中，请稍后...'});
		    	myMask2.show();
		    	me.rightMoveAll();
		    	myMask.hide();
		    	myMask2.hide();
		     }
		},{
			iconCls : 'deppon_icons_turnright',
			tooltip : load.expresspackageaddnew.i18n('foss.load.expresspackageaddnew.moveButtonPanel.moveRightToolTip')/*'点击可将左侧已勾选运单及流水号移动到右侧'*/,
		     handler: function() {
		     	var myMask = load.expresspackageaddnew.queryWayBillWindow.getLoadMask('运单右移中，请稍后...');
		     	myMask.show();
		        me.rightMove();
		        myMask.hide();
		     }
		},{
			iconCls : 'deppon_icons_turnleft',
			tooltip : load.expresspackageaddnew.i18n('foss.load.expresspackageaddnew.moveButtonPanel.moveLeftToolTip')/*'点击可将右侧选中运单移动到左侧'*/,
		     handler: function() {
		     	var myMask = load.expresspackageaddnew.queryWayBillWindow.getLoadMask('运单左移中，请稍后...');
		     	myMask.show();
		    	me.leftMove();
		    	myMask.hide();
		     }
		},{
			iconCls : 'deppon_icons_turnleftall',
			tooltip : load.expresspackageaddnew.i18n('foss.load.expresspackageaddnew.moveButtonPanel.moveLeftAllToolTip')/*'点击可将右侧全部运单移动到左侧'*/,
		    handler: function() {
		    	var myMask = load.expresspackageaddnew.queryWayBillWindow.getLoadMask('运单左移中，请稍后...');
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
			unsubmitedGrid = load.expresspackageaddnew.unsubmitedWaybillGrid,
			unStore = unsubmitedGrid.store;
			waybillGrid = load.expresspackageaddnew.queryWaybillGrid,
				waybillMap = load.expresspackageaddnew.selectedWaybillMap;
			if(waybillMap.getCount() === 0){
				Ext.ux.Toast.msg(load.expresspackageaddnew.i18n('foss.load.expresspackageaddnew.waybillGrid.alertInfoTitle')/*'提示'*/, '左侧表格没有勾选任何库存运单！', 'error', 2000);
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
						leftWaybillRec.set('weight',((leftWaybillRec.get('weight')/pieces)*(pieces - movePieces)).toFixed(2));
						leftWaybillRec.set('cubage',((leftWaybillRec.get('cubage')/pieces)*(pieces - movePieces)).toFixed(2));
						leftWaybillRec.set('pieces',pieces - movePieces);
						//如果左侧流水号已展开，则重新加载流水号
						var plugin = waybillGrid.getPlugin('expresspackageaddnew_queryWaybillGrid_serialNoGrid');
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
						recCopy.set('weight',((recCopy.get('weight')/recCopy.get('pieces'))*pieces).toFixed(2));
						recCopy.set('cubage',((recCopy.get('cubage')/recCopy.get('pieces'))*pieces).toFixed(2));
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
						rightWaybillRec.set('weight',((rightWaybillRec.get('weight')/rightWaybillRec.get('pieces'))*pieces).toFixed(2));
						rightWaybillRec.set('cubage',((rightWaybillRec.get('cubage')/rightWaybillRec.get('pieces'))*pieces).toFixed(2));
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
						recCopy.set('weight',((recCopy.get('weight')/recCopy.get('pieces'))*pieces).toFixed(2));
						recCopy.set('cubage',((recCopy.get('cubage')/recCopy.get('pieces'))*pieces).toFixed(2));
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
						rightWaybillRec.set('weight',((rightWaybillRec.get('weight')/rightWaybillRec.get('pieces'))*pieces).toFixed(2));
						rightWaybillRec.set('cubage',((rightWaybillRec.get('cubage')/rightWaybillRec.get('pieces'))*pieces).toFixed(2));
						rightWaybillRec.set('pieces',rightSerialNoMap.getCount());
					}
				}
			});
			//清空map
			load.expresspackageaddnew.selectedWaybillMap.clear();
			load.expresspackageaddnew.updateQueryPageStaInfo();
			load.expresspackageaddnew.updateUnsubmitedWaybillStaInfo();
	},
	rightMoveAll : function(){
		var me = this,
			waybillGrid,
			unsubmitedGrid = load.expresspackageaddnew.unsubmitedWaybillGrid,
			unStore = unsubmitedGrid.store;
			var waybillGrid = load.expresspackageaddnew.queryWaybillGrid,
				store = waybillGrid.store,
				flag = false;
			if(store.getCount() === 0){
				Ext.ux.Toast.msg(load.expresspackageaddnew.i18n('foss.load.expresspackageaddnew.waybillGrid.alertInfoTitle')/*'提示'*/, '库存运单不可为空！', 'error', 1000);
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
				load.expresspackageaddnew.updateQueryPageStaInfo();
			}
	},
	leftMove : function(){
		var me = this,
			unsubmitedGrid = load.expresspackageaddnew.unsubmitedWaybillGrid,
			unStore = unsubmitedGrid.store,
			selectedWaybillList = unsubmitedGrid.getSelectionModel().getSelection();
		if(selectedWaybillList.length === 0){
			Ext.ux.Toast.msg(load.expresspackageaddnew.i18n('foss.load.expresspackageaddnew.waybillGrid.alertInfoTitle')/*'提示'*/, '未勾选右侧任何运单！', 'error', 1500);
			return;
		}
		for(var i in selectedWaybillList){
			var waybill = selectedWaybillList[i];
			unsubmitedGrid.leftMove(waybill);
		}
	},
	leftMoveAll : function(){
		var me = this,
			unsubmitedGrid = load.expresspackageaddnew.unsubmitedWaybillGrid,
			unStore = unsubmitedGrid.store;
		if(unStore.getCount() === 0){
			Ext.ux.Toast.msg(load.expresspackageaddnew.i18n('foss.load.expresspackageaddnew.waybillGrid.alertInfoTitle')/*'提示'*/, '右侧没有任何运单！', 'error', 1500);
			return;
		}
		unStore.each(function(record){
			unsubmitedGrid.leftMove(record,'MANY');
		});
		unStore.removeAll();
		load.expresspackageaddnew.updateUnsubmitedWaybillStaInfo();
	}
});

//定义查询库存运单的tabPanel
Ext.define('Foss.load.expresspackageaddnew.QueryWayBillTabPanel', {
	extend : 'Ext.tab.Panel',
	bodyCls : 'autoHeight',
	flex : 1,
    cls: 'innerTabPanel',
    queryWaybillGrid: null,
	getQueryWaybillGrid: function(){
		if(this.queryWaybillGrid==null){
			this.queryWaybillGrid = Ext.create('Foss.load.expresspackageaddnew.QueryWaybillGrid',{
				title: load.expresspackageaddnew.i18n('foss.load.expresspackageaddnew.queryResultTab.tab1Title')/*'库存货物'*/,
		        tabConfig: {
		            tooltip: load.expresspackageaddnew.i18n('foss.load.expresspackageaddnew.queryResultTab.tab1ToolTip')/*'查询部门库存货物'*/,
		            width:100
		        }
			});
			load.expresspackageaddnew.queryWaybillGrid = this.queryWaybillGrid;
		}
		return this.queryWaybillGrid;
	},
    constructor : function(config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.items = [me.getQueryWaybillGrid()];
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
			if(newCard.title == load.expresspackageaddnew.i18n('foss.load.expresspackageaddnew.queryResultTab.tab1Title')/*'库存货物'*/){
				Ext.getCmp('Foss_expresspackageaddnew_QueryForm_InstorageTime_ID').setVisible(true);
			}else{
				Ext.getCmp('Foss_expresspackageaddnew_QueryForm_InstorageTime_ID').setVisible(false);
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
Ext.define('Foss.load.expresspackageaddnew.UnSubmitedWaybillStore', {
	extend : 'Ext.data.Store',
	// 绑定一个模型
	model : 'Foss.load.expresspackageaddnew.waybillStockModel',
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
});

//定义待提交流水号的store
Ext.define('Foss.load.expresspackageaddnew.UnSubmitedSerialNoStore', {
	extend : 'Ext.data.Store',
	// 绑定一个模型
	model : 'Foss.load.expresspackageaddnew.serialNoModel',
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
});

//定义待提交运单中流水号列表grid
Ext.define('Foss.load.expresspackageaddnew.unsubmitedWaybillSerialNoGrid', {
	extend : 'Ext.grid.Panel',
	columnLines: true,
	frame : true,
	width : 185,
	bodyCls: 'autoHeight',
	baseCls : 'package_queryWayBill_serialNoGap',
	// collapsible : true,
	animCollapse : true,
	columns : [{
		xtype : 'actioncolumn',
		width : 40,
		text : load.expresspackageaddnew.i18n('foss.load.expresspackageaddnew.waybillGrid.actionColumn')/*'操作'*/,
		align : 'center',
		items : [ {
			tooltip : load.expresspackageaddnew.i18n('foss.load.expresspackageaddnew.moveButtonPanel.moveLeft')/*'左移'*/,
			iconCls : 'deppon_icons_remove',
			handler : function(grid, rowIndex, colIndex) {
				var store= grid.store,
					record = store.getAt(rowIndex),
					waybillNo = record.get('waybillNo'),
					superRec = load.expresspackageaddnew.unsubmitedWaybillGrid.store.findRecord('waybillNo',waybillNo,0,false,true,true),
					serialNo = record.get('serialNo'),
					waybillId = record.get('superId'),
					packageNo = record.get('packageNo'),
					oneWeight = superRec.get('weight')/superRec.get('pieces'),
					oneCubage = superRec.get('cubage')/superRec.get('pieces'),
					isInStorage = record.get('isInStorage');
				if(store.getCount() === 1){
					Ext.ux.Toast.msg(load.expresspackageaddnew.i18n('foss.load.expresspackageaddnew.waybillGrid.alertInfoTitle')/*'提示'*/, '请左移整条运单！', 'error', 1500);
					return;
				}
				store.removeAt(rowIndex);
				//从左侧表格的流水号map中删除该流水号
				superRec.get('serialNoMap').removeAtKey(serialNo);
				//如果移除的是库存流水号
					//左侧是否有该运单
					var leftGrid = load.expresspackageaddnew.queryWaybillGrid,
						leftWaybill = leftGrid.store.findRecord('waybillNo',waybillNo,0,false,true,true);
					//如果没有，则新建一条，插入
					if(leftWaybill === null){
						var recCopy = superRec.copy();
						recCopy.set('pieces',1);
						recCopy.set('weight',oneWeight.toFixed(2));
						recCopy.set('cubage',oneCubage.toFixed(2));
						var serialNoList = [];
						serialNoList.push(record.data);
						recCopy.set('serialNoStockList',serialNoList);
						leftGrid.store.insert(leftGrid.store.getCount(),recCopy);
					}else{
						//获取左侧运单的流水号list
						var leftSerialNoList = leftWaybill.get('serialNoStockList');
						if(!load.expresspackageaddnew.inArray(leftSerialNoList,serialNo)){
							leftSerialNoList.push(record.data);
							leftWaybill.set('pieces',leftWaybill.get('pieces') + 1);
							leftWaybill.set('weight',(leftWaybill.get('weight') + oneWeight).toFixed(2));
							leftWaybill.set('cubage',(leftWaybill.get('cubage') + oneCubage).toFixed(2));
						}
						//如果左侧流水号已展开，则重新加载流水号
						var plugin = leftGrid.getPlugin('expresspackageaddnew_queryWaybillGrid_serialNoGrid');
						if(!Ext.isEmpty(plugin.getExpendRow())) {
							var item = plugin.getExpendRowBody();
							var innerStore = item.getStore();
							var subWaybillNo = innerStore.getAt(0).get('waybillNo');
							if(waybillNo == subWaybillNo){
								innerStore.loadData(leftSerialNoList);
							}
						}
					}
				superRec.set('pieces',superRec.get('pieces') - 1);
				superRec.set('weight',(superRec.get('weight') - oneWeight).toFixed(2));
				superRec.set('cubage',(superRec.get('cubage') - oneCubage).toFixed(2));
				//重新统计已选运单
				load.expresspackageaddnew.updateUnsubmitedWaybillStaInfo();
			}
		} ]
	}, {
		dataIndex : 'serialNo',
		align : 'center',
		width : 120,
		xtype : 'ellipsiscolumn',
		text : load.expresspackageaddnew.i18n('foss.load.expresspackageaddnew.waybillGrid.serialNoColumn')/*'流水号'*/
	}],
	bindData : function(record){
		var grid = this;
		grid.store.loadData(record.get('serialNoMap').getValues());
		console.log(grid.store);
	},
	constructor : function(config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.load.expresspackageaddnew.UnSubmitedSerialNoStore');
		me.callParent([cfg]);
	}
});

//定义待提交运单的grid
Ext.define('Foss.load.expresspackageaddnew.UnSubmitedWaybillGrid', {
	extend : 'Ext.grid.Panel',
	title : load.expresspackageaddnew.i18n('foss.load.expresspackageaddnew.leftGrid.gridTitle')/*'已选运单列表'*/,
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
		pluginId : 'expresspackageaddnew_unsubmitedWaybillGrid_serialNoGrid',
		rowsExpander : false,
		// 行体内容
		rowBodyElement : 'Foss.load.expresspackageaddnew.unsubmitedWaybillSerialNoGrid'
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
		me.store = Ext.create('Foss.load.expresspackageaddnew.UnSubmitedWaybillStore');
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
			id : 'Foss_load_expresspackageaddnew_UnsubmitedWaybillTotalPieces',
			fieldLabel: load.expresspackageaddnew.i18n('foss.load.expresspackageaddnew.waybillGrid.totalPiecesLabel')/*'总件数'*/,
			columnWidth : 1/2,
			value : 0
		},{
			id : 'Foss_load_expresspackageaddnew_UnsubmitedWaybillTotalWeight',
			fieldLabel: '总重量(千克)',
			labelWidth : 120,
			columnWidth : 1/2,
			value : 0
		},{
			id : 'Foss_load_expresspackageaddnew_UnsubmitedWaybillTotalCubage',
			fieldLabel: '总体积(方)',
			columnWidth : 1/2,
			value : 0
		},{
			id : 'Foss_load_expresspackageaddnew_UnsubmitedWaybilleTotalMoney',
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
			load.expresspackageaddnew.updateUnsubmitedWaybillStaInfo();
		}
		//此时左移的record中，流水号可能有库存的，也可能有在途的，而且在途的可能分别隶属于不同的包，所以此处要分别进行区分，分别生成不同的record
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
			var leftGrid = load.expresspackageaddnew.queryWaybillGrid,
				leftWaybill = leftGrid.store.findRecord('waybillNo',waybillNo,0,false,true,true);
			//如果没有，则新建一条，插入
			if(leftWaybill === null){
				var recCopy = record.copy();
				recCopy.set('pieces',stockSerialNoMap.getCount());
				recCopy.set('weight',(oneWeight*record.get('pieces')).toFixed(2));
				recCopy.set('cubage',(oneCubage*record.get('pieces')).toFixed(2));
				recCopy.set('serialNoStockList',stockSerialNoMap.getValues());
				leftGrid.store.insert(leftGrid.store.getCount(),recCopy);
			}else{
				//获取左侧运单的流水号list
				var leftSerialNoList = leftWaybill.get('serialNoStockList');
				stockSerialNoMap.each(function(serialNo,serialNoData,length){
					if(!load.expresspackageaddnew.inArray(leftSerialNoList,serialNo)){
						leftSerialNoList.push(serialNoData);
						leftWaybill.set('pieces',leftWaybill.get('pieces') + 1);
						leftWaybill.set('weight',(leftWaybill.get('weight') + oneWeight).toFixed(2));
						leftWaybill.set('cubage',(leftWaybill.get('cubage') + oneCubage).toFixed(2));
					}
				});
				//如果左侧流水号已展开，则重新加载流水号
				var plugin = leftGrid.getPlugin('expresspackageaddnew_queryWaybillGrid_serialNoGrid');
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
	},
	columns : [ {
		xtype : 'actioncolumn',
		width : 40,
		text : load.expresspackageaddnew.i18n('foss.load.expresspackageaddnew.waybillGrid.actionColumn')/*'操作'*/,
		align : 'center',
		items : [ {
			tooltip : load.expresspackageaddnew.i18n('foss.load.expresspackageaddnew.moveButtonPanel.moveLeft')/*'左移'*/,
			iconCls : 'deppon_icons_remove',
			handler : function(grid, rowIndex, colIndex) {
				var record = grid.store.getAt(rowIndex);
				load.expresspackageaddnew.unsubmitedWaybillGrid.leftMove(record);
			}
		} ]
	}, {
		dataIndex : 'waybillNo',
		align : 'center',
		width : 80,
		xtype : 'ellipsiscolumn',
		text : load.expresspackageaddnew.i18n('foss.load.expresspackageaddnew.waybillGrid.waybillNoColumn')/*'运单号'*/
	}, {
		dataIndex : 'transProperty',
		align : 'center',
		width : 95,
		text : load.expresspackageaddnew.i18n('foss.load.expresspackageaddnew.waybillGrid.transPropertyColumn')/*'运输性质'*/
	}, {
		dataIndex : 'goodsName',
		align : 'center',
		width : 60,
		xtype : 'ellipsiscolumn',
		text : load.expresspackageaddnew.i18n('foss.load.expresspackageaddnew.waybillGrid.goodsNameColumn')/*'货物名称'*/
	}, {
		dataIndex : 'packing',
		align : 'center',
		width : 60,
		xtype : 'ellipsiscolumn',
		text : load.expresspackageaddnew.i18n('foss.load.expresspackageaddnew.waybillGrid.packingColumn')/*'包装'*/
	}, {
		dataIndex : 'cubage',
		align : 'center',
		width : 60,
		xtype : 'ellipsiscolumn',
		text : load.expresspackageaddnew.i18n('foss.load.expresspackageaddnew.waybillGrid.volume')/*'体积'*/
	}, {
		dataIndex : 'weight',
		align : 'center',
		width : 60,
		xtype : 'ellipsiscolumn',
		text : load.expresspackageaddnew.i18n('foss.load.expresspackageaddnew.waybillGrid.weight')/*'重量'*/
	}, {
		dataIndex : 'pieces',
		align : 'center',
		flex : 1,
		text : load.expresspackageaddnew.i18n('foss.load.expresspackageaddnew.waybillGrid.piecees')/*'件数'*/
	}, {
		dataIndex : 'arriveDept',
		align : 'center',
		width : 150,
		xtype : 'ellipsiscolumn',
		text : load.expresspackageaddnew.i18n('foss.load.expresspackageaddnew.basicInfoForm.arriveDeptLabel')/*'到达部门'*/
	}, {
		dataIndex : 'insuranceValue',
		align : 'center',
		width : 60,
		xtype : 'ellipsiscolumn',
		text : load.expresspackageaddnew.i18n('foss.load.expresspackageaddnew.waybillGrid.insuranceValue')/*'保险价值'*/
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
		text : load.expresspackageaddnew.i18n('foss.load.expresspackageaddnew.waybillGrid.waybillPieces')/*'开单件数'*/
	}, {
		dataIndex : 'isPreciousGoods',
		align : 'center',
		width : 80,
		text : load.expresspackageaddnew.i18n('foss.load.expresspackageaddnew.waybillGrid.isPreciousGoods')/*'是否贵重物品'*/,
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
		text : load.expresspackageaddnew.i18n('foss.load.expresspackageaddnew.waybillGrid.waybillNoteColumn')/*'运单备注'*/
	} ],
	getRightMenu : function(record){
		var grid = this;
		function leftMoveOne(){
			grid.leftMove(record);
		}
		var	rightMenu =	new Ext.menu.Menu({
	        items : [{
                text : load.expresspackageaddnew.i18n('foss.load.expresspackageaddnew.moveButtonPanel.moveLeft')/*'左移'*/,
                handler : leftMoveOne
	        }]
	     });
		 return rightMenu;
	}
});

load.expresspackageaddnew.unsubmitedWaybillGrid = Ext.create('Foss.load.expresspackageaddnew.UnSubmitedWaybillGrid');

//定义查询窗口中间部分的panel
Ext.define('Foss.load.expresspackageaddnew.QueryWindowCenterPanel', {
	extend : 'Ext.panel.Panel',
	gridTabPanel : null,
	getGridTabPanel : function(){
		if(this.gridTabPanel==null){
			this.gridTabPanel = Ext.create('Foss.load.expresspackageaddnew.QueryWayBillTabPanel');
			load.expresspackageaddnew.queryWaybillTabPanel = this.gridTabPanel;
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
					tooltip : load.expresspackageaddnew.i18n('foss.load.expresspackageaddnew.moveButtonPanel.closeAllExpandLeftGridAlert')/*'点击可收起或展开待选运单表格'*/,
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
			Ext.create('Foss.load.expresspackageaddnew.moveButtonPanel'),
			Ext.create('Ext.panel.Panel',{
				width : 10,
				items : [{
					xtype : 'button',
					border : false,
					cls : 'flexleft',
					tooltip : load.expresspackageaddnew.i18n('foss.load.expresspackageaddnew.moveButtonPanel.closeAllExpandRightGridAlert')/*'点击可收起或展开已选运单表格'*/,
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
			load.expresspackageaddnew.unsubmitedWaybillGrid],
		me.callParent([cfg]);
	}
});
// 定义查询包交接运单窗口
Ext.define('Foss.load.expresspackageaddnew.QueryWayBillWindow', {
	extend : 'Ext.window.Window',
	closeAction : 'hide',
	modal : true,
//	maximized : true,
//	width : document.body.clientWidth,
	width : 1260,
	height : 930,
	title : load.expresspackageaddnew.i18n('foss.load.expresspackageaddnew.queryWaybillForm.windowTitle')/*'查询包交接运单'*/,
	queryWaybillForm: null,
	getQueryWaybillForm: function(){
		if(this.queryWaybillForm==null){
			this.queryWaybillForm = Ext.create('Foss.load.expresspackageaddnew.QueryWaybillForm');
			load.expresspackageaddnew.queryWaybillForm = this.queryWaybillForm;
		}
		return this.queryWaybillForm;
	},
	centerPanel : null,
	getCenterPanel : function(){
		if(this.centerPanel==null){
			this.centerPanel = Ext.create('Foss.load.expresspackageaddnew.QueryWindowCenterPanel');
			load.expresspackageaddnew.queryWindowCenterPanel = this.centerPanel;
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
			load.expresspackageaddnew.selectedWaybillMap.clear();
			if(load.expresspackageaddnew.queryWaybillGrid.store.getCount() != 0){
				load.expresspackageaddnew.queryWaybillGrid.store.removeAll();//清空store
				load.expresspackageaddnew.pagingBar.onLoad();
			}
			//清空已选运单列表
			load.expresspackageaddnew.unsubmitedWaybillGrid.store.removeAll();
			load.expresspackageaddnew.updateUnsubmitedWaybillStaInfo();
			//清空统计信息
			Ext.getCmp('Foss_load_expresspackageaddnew_QueryPageTotalPieces').setValue(0);
			Ext.getCmp('Foss_load_expresspackageaddnew_QueryPageTotalWeight').setValue(0);
			Ext.getCmp('Foss_load_expresspackageaddnew_QueryPageTotalCubage').setValue(0);
			Ext.getCmp('Foss_load_expresspackageaddnew_QueryPageTotalMoney').setValue(0);
		}
	},
	buttons : [{
		text : load.expresspackageaddnew.i18n('foss.load.expresspackageaddnew.changeAssembleWayWindow.confirmButton')/*'确定'*/,
		cls : 'yellow_button',
		handler : function(){
			//若未勾选任何运单，则提示
			var unGrid = load.expresspackageaddnew.unsubmitedWaybillGrid,
				unStore = unGrid.store;
			if(unStore.getCount() == 0){
				Ext.ux.Toast.msg(load.expresspackageaddnew.i18n('foss.load.expresspackageaddnew.waybillGrid.alertInfoTitle')/*'提示'*/, '右侧列表没有任何运单！', 'error', 1000);
				return;
			}
			
			var count=0;
			//待选grid中已经存在的运单运输性质集合
	        var items = unGrid.store.data.items;
	        if (items.length > 0) {
	            //遍历选定区
	            for (var j = 0; j < items.length; j++) {
	            	if(items[j].data.transPropertyCode=='DEAP'){
	            		count++;
	            	}else{
	            		continue;
	            	}
	            	
	            }
	        }
	        if(count>0&&count<items.length){
			   Ext.Msg.alert("提示：",'亲 [快递空运]包不能添加非商务专递的产品!');
	        	return;
	        }
			
			
			//获取主页面的表格
			var mainGrid = load.expresspackageaddnew.packageDetailGrid;
			//获取主页面的扩展组件
			var plugin = mainGrid.getPlugin('Foss_expresspackageaddnew_mainPage_serialNoGrid_ID'),
				myMask = load.expresspackageaddnew.queryWayBillWindow.getLoadMask('运单添加中，请稍后...');
		     myMask.show();
		     
		     if(!load.expresspackageaddnew.validatepackageDetailsTransProperty(unStore, mainGrid.store.getAt(0))){
				myMask.hide();
				return;
		     }	
		    if( Ext.getCmp('Foss_load_expresspackageaddnew_MainPageTotalWeight')>30){
		    	Ext.Msg.alert('提示','建包总重量大于30KG，无法扫描该件！' );
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
					var oldSerialNoMap = mainRecord.get('serialNoMap');
					serialNoMap.each(function(key,value,length){
						oldSerialNoMap.replace(key,value);
					});
					//更新主页列表中的件数等信息
					mainRecord.set('pieces',oldSerialNoMap.getCount());
					mainRecord.set('cubage',((record.get('cubage')/record.get('pieces'))*mainRecord.get('pieces')).toFixed(2));
					mainRecord.set('weight',((record.get('weight')/record.get('pieces'))*mainRecord.get('pieces')).toFixed(2));
					mainRecord.set('cubageAc',mainRecord.get('cubage'));
					mainRecord.set('weightAc',mainRecord.get('weight'));
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
					newRecord.set('cubageAc',newRecord.get('cubage'));
					newRecord.set('weightAc',newRecord.get('weight'));
					//将该运单插入主页面
					mainGrid.store.insert(mainGrid.store.getCount(),newRecord);
				}
			});
			myMask.hide();
			this.ownerCt.ownerCt.close();
		}
	}, {
		text : load.expresspackageaddnew.i18n('foss.load.expresspackageaddnew.leftGrid.concelButton')/*'取消'*/,
		handler : function(){
			this.ownerCt.ownerCt.close();
		}
	} ]
});

//定义控件的弹出框方法
load.expresspackageaddnew.packageAlert = function(cmp,icon){
	Ext.MessageBox.show({
        title: load.expresspackageaddnew.i18n('foss.load.expresspackageaddnew.waybillGrid.alertInfoTitle')/*'提示'*/,
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
load.expresspackageaddnew.packageDetailGrid = Ext.create('Foss.load.expresspackageaddnew.packageDetailGrid');

Ext.onReady(function() {
	// Ext.QuickTips.init();
	//定义变量，记录包是否已被保存
	load.expresspackageaddnew.isSaved = 'N';
	// 定义基本信息表单
	var addNewForm = load.expresspackageaddnew.addNewForm = Ext.create('Foss.load.expresspackageaddnew.AddNewForm');

	//更新时间  addNewForm表单中createTime，影藏参数
	function updatePackageTime(){
	//	addNewForm.getForm().findField('createTime').setValue(load.expresspackageaddnew.packageGetDateTime());
	}
//	var intervalControl = window.setInterval(updatePackageTime,1000);//每隔1秒执行一次
	//定义查询运单窗口
	load.expresspackageaddnew.queryWayBillWindow = Ext.create('Foss.load.expresspackageaddnew.QueryWayBillWindow');
	
	Ext.create('Ext.panel.Panel', {
		id : 'T_load-expresspackageaddnewindex_content',
		cls : "panelContentNToolbar",
		bodyCls : 'panelContent-body',
		layout : 'auto',//
		items : [ addNewForm, load.expresspackageaddnew.packageDetailGrid,{
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
				id : 'Foss_load_expresspackageaddnew_mainPage_saveButton_ID',
				text : load.expresspackageaddnew.i18n('foss.load.expresspackageaddnew.waybillGrid.saveButtonText')/*'保存'*/,
				handler : function() {
					var form = addNewForm.getForm();//拿到addNewForm
					if(form.isValid()){
						//若未添加任何运单，则无法保存
						if(load.expresspackageaddnew.packageDetailGrid.store.getCount() == 0){
							Ext.Msg.show({
							     title : load.expresspackageaddnew.i18n('foss.load.expresspackageaddnew.waybillGrid.alertInfoTitle')/*'提示'*/,
							     msg : '未添加任何运单',
							     buttons : Ext.Msg.OK,
							     icon: Ext.Msg.WARNING
							});
							return;
						}
						//如果包号为空
						var packageNoTemp = form.findField('packageNo').getValue();
						if(packageNoTemp == null && packageNoTemp.trim() == ''){
							Ext.MessageBox.alert(load.expresspackageaddnew.i18n('foss.load.expresspackageaddnew.waybillGrid.alertInfoTitle'),"包号不能为空！");
							return;
						}
						//通过基本信息form.getValues()获取一级实体
						var packageEntity = form.getValues(),
						//遍历表格store，封装二级实体列表
						waybillStore = load.expresspackageaddnew.packageDetailGrid.store;
						//由于装车完成时间和交接时间getValue返回的是字符串，故此处转换为date类型，重新设置属性
					/*	var reg=new RegExp('-','g');
						if(!Ext.isEmpty(packageEntity.createTime)){
							packageEntity.createTime = new Date(packageEntity.createTime.replace(reg,'/'));
						}*/
						
						//在对象中加入到达部门
						Ext.Object.merge(packageEntity,{
							'arriveOrgName' : load.expresspackageaddnew.arriveDeptName,
							'departOrgName' : FossUserContext.getCurrentDept().name
						});
						
						//到达部门
						packageEntity.arriveOrgCode = load.expresspackageaddnew.arriveOrgCode;
						packageEntity.departOrgCode = FossUserContext.getCurrentDeptCode();
						
					//	var waybillList = new Array();
					//	waybillList = waybillStore.data;
						
						//运单list//流水号list
						var waybillList = [],	
							serialNoList = [];
						for(var i = 0;i < waybillStore.getCount();i++){
							var record = waybillStore.getAt(i),
								waybill = record.data;
							//获取流水号
							var serialNoMap = waybill.serialNoMap;
							//循环流水号
							serialNoMap.each(function(key,value,length){
								serialNoList.push(value.data);
							});
							//运单list 删掉无用的三个属性
							waybillCopy = Ext.clone(waybill);//复制一条运单
							delete waybillCopy.serialNoStockList;
							delete waybillCopy.serialNoHandOveredList;
							delete waybillCopy.serialNoMap;
							waybillList.push(waybillCopy);
						}
						
						//构造传到后台的json数据
						var data = {
								'packageVo' : {'expressPackageSaveConditionDto': {
									'packageEntity' : packageEntity,
									'waybillStockList' : waybillList,
									'serialNoStockList' : serialNoList
								}
							}
						};
						//mask
						var mainPanel = Ext.getCmp('T_load-expresspackageaddnewindex_content');//取得新增的界面panel
						//模态的,悬浮的组件
						var myMask = new Ext.LoadMask(mainPanel, {msg : "数据提交中，请稍等..."});
		 				myMask.show();
						//保存包数据
						Ext.Ajax.request({
							url : load.realPath('saveExpressPackage.action'),
							jsonData : data,
							timeout : 300000,
							success : function(response){
								//获取后台重新生成的包号
								var result = Ext.decode(response.responseText);
								var packageNo = result.packageVo.packageNo;
								//提示保存成功，展示包号
								load.expresspackageaddnew.prtpackageNo = packageNo;
								Ext.Msg.show({
								     title : load.expresspackageaddnew.i18n('foss.load.expresspackageaddnew.waybillGrid.alertInfoTitle')/*'提示'*/,
								     msg : '保存成功，包号为：' + packageNo,
								     buttons : Ext.Msg.OK,
								     icon: Ext.Msg.INFO
								});
								load.expresspackageaddnew.isSaved = 'Y';
								myMask.hide();
								
								//重新设置包号
								form.findField('packageNo').setValue(packageNo);
								//设置form所有控件为只读
								var formCmps = form.getFields().getRange(0,form.getFields().getCount());
								for(var i in formCmps){
									formCmps[i].setReadOnly(true);
								}
								//隐藏“查询包交接运单”、“保存”按钮
								Ext.getCmp('Foss_load_expresspackageaddnew_mainPage_saveButton_ID').setVisible(false);
								Ext.getCmp('Foss_load_expresspackageaddnew_mainPage_queryButton_ID').setVisible(false);
								//禁用快速添加里的输入框、按钮
								Ext.getCmp('Foss_load_expresspackageaddnew_quickAddWaybillNo_ID').setVisible(false);
								Ext.getCmp('Foss_load_expresspackageaddnew_quickAddButton_ID').setVisible(false);
								
								//隐藏运单grid和流水号grid前的操作列并设置为不可用
								load.expresspackageaddnew.packageDetailGrid.columns[1].setVisible(false);
								//获取展开的流水号grid
								var plugin = load.expresspackageaddnew.packageDetailGrid.getPlugin('Foss_expresspackageaddnew_mainPage_serialNoGrid_ID');
								var pluginGrid = plugin.getExpendRowBody();
								if(pluginGrid != null){
									pluginGrid.columns[0].setVisible(false);
								}
							},
							exception : function(response) {
			    				var result = Ext.decode(response.responseText);
			    				top.Ext.MessageBox.alert(load.expresspackageaddnew.i18n('foss.load.expresspackageaddnew.waybillGrid.alertInfoTitle')/*'提示'*/,'保存失败，' + result.message);
			    				myMask.hide();
			    			},
			    			failure : function(){
			    				myMask.hide();
			    				console.log('保存包时服务端异常！');
			    			}
						});
					}
				}
			} ]
		} ],
		renderTo : 'T_load-expresspackageaddnewindex-body'
	});
});



