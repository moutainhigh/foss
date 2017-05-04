// 定义查询运单结果Model，四个grid共用该Model
Ext.define('Foss.load.connectionbilladdnew.waybillStockModel', {
	extend : 'Ext.data.Model',
	//定义字段
	fields : [{
		name : 'id',
		type : 'string'
	},{
		name : 'connectionBillNo',
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
		name : 'cubage',
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
		name : 'codAmount',
		type : 'number'
	},{
		name : 'isCarLoad',
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
Ext.define('Foss.load.connectionbilladdnew.serialNoModel', {
	extend : 'Ext.data.Model',
	// 定义字段
	fields : [{
		name : 'superId',
		type : 'string'
	},{
		name : 'connectionBillNo',
		type : 'string'
	},{
		name : 'waybillNo',
		type : 'string'
	},{
		name : 'serialNo',
		type : 'string'
	}]
});


/**
 * 定义Map，用来存储在途运单列表中被勾选的运单号和流水号
 * load.connectionbilladdnew.selectedEnRouteWaybillMap key，交接单中运单分录ID，value，交接单中运单对象
 */
load.connectionbilladdnew.selectedEnRouteWaybillMap = new Ext.util.HashMap();

// 定义交接单明细store
Ext.define('Foss.load.connectionbilladdnew.ConnectionBillDetailStore', {
	extend : 'Ext.data.Store',
	// 绑定一个模型
	model : 'Foss.load.connectionbilladdnew.waybillStockModel',
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	},
	listeners : {
		'datachanged' : function(store){
			load.connectionbilladdnew.updateMainPageStaInfo(store);
		},
		'update' : function(store){
			load.connectionbilladdnew.updateMainPageStaInfo(store);
		},
		'clear' : function(store){
			load.connectionbilladdnew.updateMainPageStaInfo(store);
		}
	}
});
//转换快递体积转换率
load.connectionbilladdnew.rate=load.connectionbilladdnew.rate/1000;
//方法用于各处调用，更新主页面grid下统计条数据
load.connectionbilladdnew.updateMainPageStaInfo = function (store){
	//更新主页总票数
	var totalCountCmp = Ext.getCmp('Foss_load_connectionBillAddnew_MainPageTotalCount');
	totalCountCmp.setValue(load.connectionbilladdnew.connectionBillDetailGrid.store.getCount());
	//遍历主页store，获得总件数、总体积、总重量、非快递货总体积、快递货总重量、未转换总体积
	var totalPieces = 0,totalVolume = 0,totalWeight = 0;
	store.each(function(record){
		totalPieces += record.get('pieces');
		totalWeight += record.get('weight');
		totalVolume+=record.get('cubage');
	});
	if(totalVolume!=0){
		totalVolume = totalVolume.toFixed(2);
	}
	if(totalWeight != 0){
		totalWeight = totalWeight.toFixed(2);//如果不为0，则四舍五入，保留两位小数
	}
	//更新主页总件数、总体积、总重量、未转换总体积
	Ext.getCmp('Foss_load_connectionBillAddnew_MainPageTotalPieces').setValue(totalPieces);
	Ext.getCmp('Foss_load_connectionBillAddnew_MainPageTotalVolume').setValue(totalVolume);
	Ext.getCmp('Foss_load_connectionBillAddnew_MainPageTotalWeight').setValue(totalWeight);
};

//方法用于各处调用，更新查询库存运单界面下统计条数据
load.connectionbilladdnew.updateQueryPageStaInfo = function(){
	//由于一层map勾选导致的二层流水号增加个数不好确定，故遍历二层map，结合一层map，得到各统计信息
	var totalPieces = 0,totalWeight = 0,totalCubage = 0, normalTotalCubage=0,fastTotalWeight=0;
	load.connectionbilladdnew.selectedWaybillMap.each(function(waybillNo,waybill,length){
		var serialNoMap = waybill.get('serialNoMap');
		totalPieces += serialNoMap.getCount();//得到总件数
		var waybillWeight = (serialNoMap.getCount()/waybill.get('pieces'))*(waybill.get('weight'));//按件数均分，得到该票运单的重量
		totalWeight += waybillWeight;//得到总重量
		var waybillCubage = (serialNoMap.getCount()/waybill.get('pieces'))*(waybill.get('cubage'));//按件数均分，得到该票运单的体积
		totalCubage+=waybillCubage;
	});
	if(totalCubage!=0){
		totalCubage = totalCubage.toFixed(2);
	}
	if(totalWeight != 0){
		totalWeight = totalWeight.toFixed(2);//如果不为0，则四舍五入，保留两位小数
	}
	//设置已勾选总件数
	Ext.getCmp('Foss_load_connectionBillAddnew_QueryPageTotalPieces').setValue(totalPieces);
	Ext.getCmp('Foss_load_connectionBillAddnew_QueryPageTotalWeight').setValue(totalWeight);
	Ext.getCmp('Foss_load_connectionBillAddnew_QueryPageTotalCubage').setValue(totalCubage);
}


//方法用于各处调用，更新已选运单统计条数据
load.connectionbilladdnew.updateUnsubmitedWaybillStaInfo = function(){
	var totalPieces = 0,totalWeight = 0,totalCubage = 0;
	load.connectionbilladdnew.unsubmitedWaybillGrid.store.each(function(waybill){
		var serialNoMap = waybill.get('serialNoMap');
		totalPieces += serialNoMap.getCount();//得到总件数
		totalWeight += waybill.get('weight');//得到总重量
		totalCubage+=waybill.get('cubage');
	});
	
	if(totalWeight != 0){
		totalWeight = totalWeight.toFixed(2);//如果不为0，则四舍五入，保留两位小数
	}
	if(totalCubage != 0){
		totalCubage = totalCubage.toFixed(2);
	}
	//设置已勾选总件数
	Ext.getCmp('Foss_load_connectionBillAddnew_UnsubmitedWaybillTotalPieces').setValue(totalPieces);
	Ext.getCmp('Foss_load_connectionBillAddnew_UnsubmitedWaybillTotalWeight').setValue(totalWeight);
	Ext.getCmp('Foss_load_connectionBillAddnew_UnsubmitedWaybillTotalCubage').setValue(totalCubage);
}

//定义方法，判断流水号数组中是否存在某流水号
load.connectionbilladdnew.inArray = function(serialNoList,serialNo){
	for(var i in serialNoList){
		var serialNoRec = serialNoList[i];
		if(serialNoRec.serialNo === serialNo){
			return true;
		}
	}
	return false;
}

// 定义运单明细store
Ext.define('Foss.load.connectionbilladdnew.WaybillDetailStore', {
	extend : 'Ext.data.Store',
	// 绑定一个模型
	model : 'Foss.load.connectionbilladdnew.serialNoModel',
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
});

// 定义运单明细grid
Ext.define('Foss.load.connectionbilladdnew.WaybillDetailGrid', {
	extend : 'Ext.grid.Panel',
	columnLines: true,
	frame: true,
	enableColumnHide : false,//配置该属性可取消grid自定义显示列功能
	baseCls : 'handOverBill_addNew_serialNoGap',
	autoScroll : true,
	width : 180,
	store : Ext.create('Foss.load.connectionbilladdnew.WaybillDetailStore'),
	columns : [ {
		xtype : 'actioncolumn',
		width : 60,
		text : '操作',
		align : 'center',
		items : [ {
			tooltip : '删除',
			iconCls : 'deppon_icons_remove',
			handler : function(grid, rowIndex, colIndex) {
				if(grid.store.getCount() == 1){
					Ext.ux.Toast.msg('提示', '请直接删除运单', 'error', 1500);
					return;
				}
				//从unsavedSerialNoMap中删除该流水号
				var record = grid.getStore().getAt(rowIndex),
					waybillNo = record.get('waybillNo'),
					serialNo = record.get('serialNo');
				//更新一级表格内的信息
				waybillStore = load.connectionbilladdnew.connectionBillDetailGrid.store;
				var waybillRecord = waybillStore.findRecord('waybillNo', waybillNo, 0, false,true,true);
				//删掉map里的该流水号
				waybillRecord.get('serialNoMap').removeAtKey(serialNo);
				waybillRecord.set('weight',(waybillRecord.get('weight')-(waybillRecord.get('weight')/waybillRecord.get('pieces'))).toFixed(2));
				waybillRecord.set('cubage',(waybillRecord.get('cubage')-(waybillRecord.get('cubage')/waybillRecord.get('pieces'))).toFixed(2));
				waybillRecord.set('pieces',waybillRecord.get('pieces') - 1);
				grid.store.removeAt(rowIndex);
			}
		} ]
	}, {
		dataIndex : 'serialNo',
		align : 'center',
		flex : 1,
		xtype : 'ellipsiscolumn',
		text : '流水号'
	} ],
	bindData : function(record){
		var superGrid = this.superGrid,
			serialNoMap = record.get('serialNoMap');
		if(load.connectionbilladdnew.isSaved == 'Y'){
			this.columns[0].setVisible(false);
			//this.columns[0].destroy();
		}
		this.store.loadData(serialNoMap.getValues());
	}
});

//定义打印模版window
Ext.define('Foss.load.connectionbilladdnew.PrintWindow', {
	extend: 'Ext.window.Window',
	title: '打印模板选择',
	layout:'column',
	height: 150,
	width: 300,
	closable:true,
	closeAction:'hide',
	modal: true,
	connectionBillNos : null,
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
		text : '打印',
		handler : function(){}
	}]
});

/*
 * 判断待添加的运单和已添加的运单是否都为快递货或都为零担货
 * tbAddWaybills : 待添加的运单集合； 目前tbAddWaybills的数据类型为Ext.util.MixedCollection或Ext.data.Store
 * existWaybill ： 已经存在的运单; 目前existsWaybill的数据类型为Ext.data.Model
 */
load.connectionbilladdnew.validateHandoverbillDetailsTransProperty = function(tbAddWaybills, existWaybill){
	
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
			//非快递都为零担
			transPropertyCode = (
					( (waybill.transPropertyCode || waybill.get("transPropertyCode")) === "RCP")
					||( (waybill.transPropertyCode || waybill.get("transPropertyCode")) === "PACKAGE" )
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
			//非快递都为零担
			transPropertyCode =(
					( (waybill.transPropertyCode || waybill.get("transPropertyCode")) === "RCP")
					||( (waybill.transPropertyCode || waybill.get("transPropertyCode")) === "PACKAGE" )
					) ? "EXPRESS" : "LTL";  
			
			var existFlag = (
								(existWaybill.get("transPropertyCode") === "PACKAGE")||(existWaybill.get("transPropertyCode") === "RCP")
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
Ext.define('Foss.load.connectionbilladdnew.ConnectionBillDetailGrid', {
	extend : 'Ext.grid.Panel',
	frame : true,
	title : '交接单明细',
//	bodyCls : 'autoHeight',
	height : 500,
	cls : 'autoHeight',
	enableColumnHide : false,//配置该属性可取消grid自定义显示列功能
	columnLines: true,
	autoScroll : true,
	collapsible : true,
	animCollapse : true,
	store : Ext.create('Foss.load.connectionbilladdnew.ConnectionBillDetailStore'),
	// 定义行展开
	plugins : [ {
		header: true,
		ptype : 'rowexpander',
		// 定义行展开模式（单行与多行），默认是多行展开(值true)
		rowsExpander : false,
		// 行体内容
		rowBodyElement : 'Foss.load.connectionbilladdnew.WaybillDetailGrid',
		pluginId : 'Foss_connectionbilladdnew_mainPage_serialNoGrid_ID'
	},Ext.create('Ext.grid.plugin.CellEditing', {
            clicksToEdit : 1,
            listeners : {
				'beforeedit' : function(editor,e,eOpts){
					//如果已经保存，则禁止编辑
					if(load.connectionbilladdnew.isSaved == 'Y'){
						return false;
					}
				}
			}
        })],
	tbar : [{
			xtype : 'button',
			text : '添加运单',
			tooltip : '点击可批量添加运单',
			id : 'Foss_load_connectionbilladdnew_mainPage_queryButton_ID',
			name : 'queryWaybillButton',
			handler : function() {
				var basicForm = load.connectionbilladdnew.addNewForm.getForm();
				var arriveDept=basicForm.findField('arriveDept');
				if(arriveDept.value==null||arriveDept.value.trim()==''){
					//如果到达接驳点为空则提示
					load.connectionbilladdnew.connectionBillAlert(arriveDept,Ext.MessageBox.WARNING);
					return;
				}
				load.connectionbilladdnew.queryWayBillWindow.showAt(0,0);
			}
		},'->',{
		xtype : 'button',
		disabled: !load.connectionbilladdnew.isPermission('load/printnewhandoverbillButton'),
		hidden: !load.connectionbilladdnew.isPermission('load/printnewhandoverbillButton'),
		text : '打印',
		handler : function(){
			if(load.connectionbilladdnew.prtconnectionBillNo == undefined) {
				Ext.ux.Toast.msg('提示', 
						"请先保存交接单!", 
						'error');
				return;
			}
			var records = this.up('grid').getStore().getRange();
			if(records.length <= 0) {
				Ext.ux.Toast.msg('提示', 
						"没有运单数据, 请确认!", 
				'error');
				return;
			}
			var vehicleNo = load.connectionbilladdnew.addNewForm.getForm().findField('vehicleNo').getValue(),
				connectionBillNos = new Array(),
				connectionBillNo = load.connectionbilladdnew.addNewForm.getForm().findField('connectionBillNo').getValue();
			connectionBillNos.push(connectionBillNo);
			var grids = this.up('grid');
			//如果选择的交接单的车牌号下还有其他的交接单则提示还有其他交接单,请注意
			Ext.Ajax.request({
				url : load.realPath('checkPrintConnectionBill.action'),
				params : {'connectionBillVo.vehicleNo' : vehicleNo,
					'connectionBillVo.connectionBillNos' : connectionBillNos
				},
				success : function(response) {
					var result = Ext.decode(response.responseText),
						count = result.connectionBillVo.checkPrintConnectionBillRestlt;
					var handBillState = result.connectionBillVo.errorMessage;
					if(count > 0) {
						//大于0则说明该车牌号下还有其他交接单尚未选择
						Ext.ux.Toast.msg('提示', 
								"此车牌中还有" + count + "个交接单没有选择打印，请注意!", 
								'error');
					} 
					if(handBillState == 'CANCEL') {
						Ext.ux.Toast.msg('提示', "此交接单已作废，不能打印!", 'error');
					}else{
						Ext.create('Foss.load.connectionbilladdnew.PrintWindow', {
							vehicleNo : vehicleNo,
							connectionBillNos : connectionBillNos,
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
				fieldLabel : '总票数',    
				xtype : 'textfield',    
				readOnly : true,
				columnWidth : 1/5,
				value : 0,
				id : 'Foss_load_connectionBillAddnew_MainPageTotalCount'
			},{
				fieldLabel : '总件数',
				xtype : 'textfield',
				readOnly : true,
				columnWidth : 1/5,
				value : 0,
				id : 'Foss_load_connectionBillAddnew_MainPageTotalPieces'
			},{
				fieldLabel : '总重量(千克)',
				xtype : 'textfield',
				readOnly : true,
				columnWidth : 1/5,
				value : 0,
				id : 'Foss_load_connectionBillAddnew_MainPageTotalWeight'
			},{
				fieldLabel : '总体积(方)',
				xtype : 'textfield',
				readOnly : true,
				columnWidth : 1/5,
				value : 0,
				id : 'Foss_load_connectionBillAddnew_MainPageTotalVolume'
			}]
	  }],
	columns : [ {
		xtype : 'actioncolumn',
		width : 60,
		text : '操作',
		align : 'center',
		items : [ {
			iconCls : 'deppon_icons_remove',
			tooltip : '删除',
			handler : function(grid, rowIndex, colIndex) {
				grid.getStore().removeAt(rowIndex);
			}
		} ]
	}, {
		dataIndex : 'waybillNo',
		align : 'center',
		width : 80,
		xtype : 'ellipsiscolumn',
		text : '运单号'
	}, {
		dataIndex : 'transProperty',
		align : 'center',
		width : 95,
		text : '运输性质'
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
		text : '已配件数'
	}, {
		dataIndex : 'weight',
		align : 'center',
		flex : 1,
		text : '已配重量'
	},  {
		dataIndex : 'cubage',
		align : 'center',
		flex : 1,
		text : '已配体积'
	},  {
		dataIndex : 'note',
		align : 'center',
		xtype : 'ellipsiscolumn',
		flex : 1,
		text :'备注',
		editor : {
			xtype : 'textarea',
			maxLength : 300
		}
	}, {
		dataIndex : 'goodsName',
		align : 'center',
		flex : 1,
		text :'货物名称'
	}, {
		dataIndex : 'packing',
		align : 'center',
		flex : 1,
		text :'包装'
	}, {
		dataIndex : 'waybillNote',
		align : 'center',
		flex : 1,
		text :'运单备注'
	} ]
});

//生成当前日期
load.connectionbilladdnew.connectionBillGetDateTime = function(){
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
load.connectionbilladdnew.arriveDept = null;

// 交接单基本信息form
Ext.define('Foss.load.connectionbilladdnew.AddNewForm', {
		extend : 'Ext.form.Panel',
		title : '交接单基本信息',
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
			fieldLabel : '交接类型',
			name : 'handOverType',
		    readOnly:true,
			xtype : 'combobox',
		    displayField: 'value',
		    valueField: 'key',
		    value : 'EXPRESS_CONNECTION_HANDOVER',
		    editable : false,
		    store : Ext.create('Ext.data.Store', {
		        fields: ['key', 'value'],
		        data : [
		            {"key":"EXPRESS_CONNECTION_HANDOVER", "value":"接驳交接单"}
		        ]
		    })
		}, {
			fieldLabel : '交接单编号',
			name : 'connectionBillNo',
			allowBlank : false,
			readOnly : true,
			value : load.connectionbilladdnew.connectionBillNo
		}, {
			fieldLabel :'交接时间',
			name : 'handOverTime',
			value : load.connectionbilladdnew.connectionBillGetDateTime(),
			readOnly : true
		}, {
			fieldLabel : '出发部门',
			name : 'departDept',
			xtype : 'dynamicorgcombselector',
			type : 'ORG',
			salesDepartment : 'Y',
			transferCenter : 'Y',
			airDispatch : 'Y',
			doAirDispatch : 'Y',
			readOnly:true,
			allowBlank:false
		}, {
			fieldLabel : '到达接驳点',
			allowBlank : false,
			name : 'arriveDept',
			xtype : 'accesspointselector',
			listeners:{
				'blur' : function(cmp, eO, eOpts) {
					if (cmp.getValue() != null
							&& cmp.getValue().trim() != ''
							&& cmp.getValue().trim() != load.connectionbilladdnew.arriveDept) {
							var rec = cmp.store.findRecord('code',cmp.getValue(),0,false,true,true);
							if (load.connectionbilladdnew.connectionBillDetailGrid.store
									.getCount() > 0) {
								Ext.MessageBox.confirm('提示',
								'确定要更改到达部门吗？</br>这将导致已添加的运单被清空。',function(btn) {
									if (btn == 'yes') {
										load.connectionbilladdnew.connectionBillDetailGrid.store.removeAll();
										load.connectionbilladdnew.arriveDept = cmp.getValue();
										load.connectionbilladdnew.arriveDeptName = rec.get('name');
										return;
									} else {
										cmp.setCombValue(load.connectionbilladdnew.arriveDeptName,load.connectionbilladdnew.arriveDept);
										return;
									}
								});
							}else{
								load.connectionbilladdnew.arriveDept = cmp.getValue();
								load.connectionbilladdnew.arriveDeptName = rec.get('name');
							}
						}
				}
				
			}
			
		}, {
			fieldLabel : '车牌号',
			allowBlank : false,
			xtype : 'commontruckselector',
			queryParam : 'truckVo.truck.vehicleNoNoLike',
			queryAllFlag : false,
			name : 'vehicleNo'
		}, {
			fieldLabel : '司机',
			allowBlank : false,
			name : 'driver',
			xtype : 'commondriverselector',
			listeners:{
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
			fieldLabel : '司机电话',
			name : 'driverTel',
			readOnly : true,
			
		}, {
			fieldLabel :'装车完成时间',
			name : 'loadEndTime',
			xtype : 'datetimefield_date97',
			time : true,
			id : 'Foss_connectionBillAddNew_loadEndTime_ID',
			allowBlank:false,
			dateConfig: {
				el : 'Foss_connectionBillAddNew_loadEndTime_ID-inputEl'
			},
			disabled : false
		}, {
			fieldLabel : '制单人',
			name : 'createUser',
			readOnly : true,
			value : FossUserContext.getCurrentUserEmp().empName
		}, {
			fieldLabel : '修改人',
			name : 'modifyUser',
			readOnly : true,
			value : FossUserContext.getCurrentUserEmp().empName
		}, {
			fieldLabel : '备注',
			name : 'note',
			maxLength : 300,
			columnWidth : .5
		} ],
		constructor : function(config) {
			var me = this, cfg = Ext.apply({}, config);
			me.callParent([ cfg ]);
		}
});

// 定义查询交接运单界面-查询条件form
Ext.define('Foss.load.connectionbilladdnew.QueryWaybillForm', {
	extend : 'Ext.form.Panel',
	title : '查询条件',
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
		fieldLabel : '入库时间',
		columnWidth : 2/5,
		// 类型为datetimefield_date97需要配置fieldId,可以赋予此属性任何唯一标 //识的String值
		fieldId : 'Foss_connectionbilladdnew_QueryForm_InstorageTime_fieldID',
		id : 'Foss_connectionbilladdnew_QueryForm_InstorageTime_ID',
		// dateType: 'datetimefield_date97',
		dateType: 'datefield',
		fromName : 'beginInStorageTime',
		toName : 'endInStorageTime',
		toValue : new Date(),
		fromValue : new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate() - 20),
		allowBlank : false,
		disallowBlank: true
	},{
		fieldLabel : '运输类型',
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
		fieldLabel : '运输性质',
		name : 'transProperty',
		xtype : 'combobox',
		queryMode: 'local',
		triggerAction:'all',
	    displayField: 'valueName',
	    valueField: 'valueCode',
	    multiSelect : true,
	    editable : true,
	    store : Ext.create('Ext.data.Store', {
	        fields: ['valueCode', 'valueName'],
	        data : [
	            {"valueCode":"ALL", "valueName":"全部"},
	            {"valueCode":"PACKAGE", "valueName":"标准快递"},
	            {"valueCode":"RCP", "valueName":"3.60特惠件"}
	        ]
	    })
	}, {
		fieldLabel : '运单号',
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
			text :'重置',
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
			id : 'Foss_connectionbilladdnew_QueryForm_queryButton_ID',
			text : '查询',
			handler : function(){
					load.connectionbilladdnew.pagingBar.moveFirst();
					var sm = load.connectionbilladdnew.queryWaybillGrid.getSelectionModel();
					sm.deselectAll(true);
					//清空统计信息
					Ext.getCmp('Foss_load_connectionBillAddnew_QueryPageTotalPieces').setValue(0);
					Ext.getCmp('Foss_load_connectionBillAddnew_QueryPageTotalWeight').setValue(0);
					Ext.getCmp('Foss_load_connectionBillAddnew_QueryPageTotalCubage').setValue(0);
					//每次查询前清空map
					load.connectionbilladdnew.selectedWaybillMap.clear();
			}
		} ]
	} ],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
});

// 定义查询库存运单store
Ext.define('Foss.load.connectionbilladdnew.QueryWaybillStore', {
	extend : 'Ext.data.Store',
	// 绑定一个模型
	model : 'Foss.load.connectionbilladdnew.waybillStockModel',
	buffered : false,
	// 定义一个代理对象
	proxy : {
		type : 'ajax',
		actionMethods:'POST',
		// 请求的url
		url : load.realPath('queryConnectionWaybillStockList.action'),
		// 定义一个读取器
		reader : {
			// 以JSON的方式读取
			type : 'json',
			// 定义读取JSON数据的根对象
			root : 'connectionBillVo.waybillStockList',
			totalProperty : 'totalCount',
			successProperty: 'success',
		}
	},
	buttonLoadMask : null,
	getButtonLoadMask : function(){
		if(this.buttonLoadMask == null){
			this.buttonLoadMask = new Ext.LoadMask(Ext.getCmp('Foss_connectionbilladdnew_QueryForm_queryButton_ID'),{
				msg : '.....'
			});
		}
		return this.buttonLoadMask;
	},
	gridLoadMask : null,
	getGridLoadMask : function(){
		if(this.gridLoadMask == null){
			this.gridLoadMask = new Ext.LoadMask(load.connectionbilladdnew.queryWaybillGrid, {
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
			var sm = load.connectionbilladdnew.queryWaybillGrid.getSelectionModel(),
				record,
				waybillNo;
			for(var i in records){
				record = records[i];
				waybillNo = record.get('waybillNo');
				//tempWaybillMap.add(waybillNo,record);
				//数据加载后，检查之前是否被勾选，若缓存map中记录的有该运单号，则重新勾选
				if(load.connectionbilladdnew.selectedWaybillMap.get(waybillNo) != null){
					sm.select(record,true,true);//勾选第一层表格中的行：第二个参数为true，保留其他已勾选的行，第三个参数为true，表示勾选后不触发select事件
				}
			};
			
			if(!successful){
				
			}
			this.getButtonLoadMask().hide();
			this.getGridLoadMask().hide();
		},
		'beforeload' : function(store, operation, eOpts){
			//获取运单查询条件参数
			var form=load.connectionbilladdnew.queryWaybillForm.getForm();
			var queryParams = form.getValues();
			var arriveDeptCode=load.connectionbilladdnew.addNewForm.getForm().findField('arriveDept').getValue();
			var departDeptCode=load.connectionbilladdnew.addNewForm.getForm().findField('departDept').getValue();
			var endInStorageTime=form.findField('endInStorageTime').getValue();
			 endInStorageTime=new Date(endInStorageTime.getFullYear(),endInStorageTime.getMonth(),endInStorageTime.getDate() + 1);
			Ext.apply(operation, {
				params : {
					'connectionBillVo.queryWayBillForConnectionBillDto.beginInStorageTime' : queryParams.beginInStorageTime,//入库开始时间
					'connectionBillVo.queryWayBillForConnectionBillDto.endInStorageTime' : endInStorageTime,//入库结束时间
					'connectionBillVo.queryWayBillForConnectionBillDto.transType' : queryParams.transType,//运输类型
					'connectionBillVo.queryWayBillForConnectionBillDto.transProperty' : queryParams.transProperty,//运输性质
					'connectionBillVo.queryWayBillForConnectionBillDto.waybillNo' : queryParams.waybillNo,//运单号
					'connectionBillVo.queryWayBillForConnectionBillDto.arriveDeptCode':	arriveDeptCode,
					'connectionBillVo.queryWayBillForConnectionBillDto.orgCode':departDeptCode//出发部门

				}
			});	
		},
		exception:function(dataProxy, type, action, options, response, arg) {
			
			alert("111");
		}
	}
});

/**
 * 定义map，selectedWaybillMap：key，运单号，value，选中的某行运单库存列
 * 						  用于记录被勾选的运单库存
 */
load.connectionbilladdnew.selectedWaybillMap = new Ext.util.HashMap();

// 定义库存运单查询结果列表
Ext.define('Foss.load.connectionbilladdnew.QueryWaybillGrid', {
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
	emptyText : '查询结果为空',
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.load.connectionbilladdnew.QueryWaybillStore');
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
		load.connectionbilladdnew.pagingBar = me.bbar;
		me.callParent([cfg]);
	},
	dockedItems: [{
	    xtype: 'toolbar',
	    id : 'Foss_load_connectionBillAddnew_QueryPageTotalPieces_toobar',
	    dock: 'bottom',
	    layout : 'column',
	    defaults: {
			xtype : 'textfield',
			readOnly : true,
			labelWidth : 120
		},
		items: [{
				id : 'Foss_load_connectionBillAddnew_QueryPageTotalPieces',
				fieldLabel: '已选总件数',
				columnWidth : 1/3,
				value : 0
			},{
				id : 'Foss_load_connectionBillAddnew_QueryPageTotalWeight',
				fieldLabel: '已选总重量(千克)',
				columnWidth : 1/3,
				value : 0
			},{
				id : 'Foss_load_connectionBillAddnew_QueryPageTotalCubage',
				fieldLabel: '已选总体积(方)',
				columnWidth : 1/3,
				value : 0
			}]
    }],
	// 定义行展开
	plugins : [ {
		header: true,
		ptype : 'rowexpander',
		// 定义行展开模式（单行与多行），默认是多行展开(值true)
		pluginId : 'connectionbilladdnew_queryWaybillGrid_serialNoGrid',
		rowsExpander : false,
		// 行体内容
		rowBodyElement : 'Foss.load.connectionbilladdnew.QueryWaybillSerialNoGrid'
	} ],
	rightMove : function(grid,record,rowIndex,type){
		var waybillNo = record.get('waybillNo'),
			unsubmitedGrid = load.connectionbilladdnew.unsubmitedWaybillGrid,
			unStore = unsubmitedGrid.store,
			unRec = unStore.findRecord('waybillNo',waybillNo,0,false,true,true),
			//获取主页面该运单号的record
			unsavedWaybill = load.connectionbilladdnew.connectionBillDetailGrid.store.findRecord('waybillNo',waybillNo,0,false,true,true),
			serialNoList = record.get('serialNoStockList'),
			flag = false;
		//如果已勾选运单map中有此运单，则移除
		if(load.connectionbilladdnew.selectedWaybillMap.get(waybillNo) !== undefined){
			load.connectionbilladdnew.selectedWaybillMap.removeAtKey(waybillNo);
			flag = true;
		}
		//右侧grid中是否有该运单
		if(unRec === null){
			var serialNoMap = new Ext.util.HashMap();
			for(var i in serialNoList){
				var serialNo = serialNoList[i];
				//如果主页面没有的流水号，并且是未预配状态，则可右移
				if((unsavedWaybill === null || unsavedWaybill.get('serialNoMap').get(serialNo.serialNo) === undefined)
					){
					var serialNoRecord = Ext.ModelManager.create(serialNo, 'Foss.load.connectionbilladdnew.serialNoModel');
					serialNoMap.add(serialNo.serialNo,serialNoRecord);
				}
			}
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
		}else{
			var serialNoMap = unRec.get('serialNoMap');
			for(var i in serialNoList){
				var serialNo = serialNoList[i];
				//如果主页面没有的流水号，并且是未预配状态，则可右移
				if((unsavedWaybill === null || unsavedWaybill.get('serialNoMap').get(serialNo.serialNo) === undefined)
					){
					var serialNoRecord = Ext.ModelManager.create(serialNo, 'Foss.load.connectionbilladdnew.serialNoModel');
					serialNoMap.replace(serialNo.serialNo,serialNoRecord);
				}
			}
			//更新件数、重量、体积
			var pieces = serialNoMap.getCount();
			unRec.set('weight',((unRec.get('weight')/unRec.get('pieces'))*pieces).toFixed(2));
			unRec.set('cubage',((unRec.get('cubage')/unRec.get('pieces'))*pieces).toFixed(2));
			unRec.set('pieces',serialNoMap.getCount());
		}
		var plugin = unsubmitedGrid.getPlugin('connectionbilladdnew_unsubmitedWaybillGrid_serialNoGrid');
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
				load.connectionbilladdnew.updateQueryPageStaInfo();
			}
		}
		//重新统计已选运单
		load.connectionbilladdnew.updateUnsubmitedWaybillStaInfo();
		return flag;
	},
	columns : [ {
		xtype : 'actioncolumn',
		width : 40,
		text : '操作',
		align : 'center',
		items : [ {
			tooltip : '右移',
			iconCls : 'foss_icons_stl_sendmes',
			handler : function(grid, rowIndex, colIndex) {
				var record = grid.store.getAt(rowIndex);
				load.connectionbilladdnew.queryWaybillGrid.rightMove(grid,record,rowIndex,'ONE');
			}
		} ]
	}, {
		dataIndex : 'waybillNo',
		align : 'center',
		width : 80,
		xtype : 'ellipsiscolumn',
		text : '运单号'
	}, {
		dataIndex : 'transProperty',
		align : 'center',
		width : 95,
		text : '运输性质'
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
		text : '货物名称'
	}, {
		dataIndex : 'packing',
		align : 'center',
		width : 60,
		xtype : 'ellipsiscolumn',
		text : '包装'
	}, {
		dataIndex : 'cubage',
		align : 'center',
		width : 60,
		xtype : 'ellipsiscolumn',
		text : '体积'
	}, {
		dataIndex : 'weight',
		align : 'center',
		width : 60,
		xtype : 'ellipsiscolumn',
		text :'重量'
	}, {
		dataIndex : 'pieces',
		align : 'center',
		flex : 1,
		text : '件数'
	}, {
		dataIndex : 'instorageDate',
		align : 'center',
		width : 90,
		text : '入库日期',
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
		text : '到达部门'
	}, {
		dataIndex : 'insuranceValue',
		align : 'center',
		width : 60,
		xtype : 'ellipsiscolumn',
		text : '保险价值'
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
		text : '开单件数'
	}, {
		dataIndex : 'isPreciousGoods',
		align : 'center',
		width : 80,
		text : '是否贵重物品',
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
		text : '运单备注'
	} ],
	getRightMenu : function(record,rowIndex){
		var grid = this;
		function rightMoveOne(){
			grid.rightMove(grid,record,rowIndex,'ONE');
		}
		var	rightMenu =	new Ext.menu.Menu({
	        items : [{
                text : '右移',
                handler : rightMoveOne
	        }]
	     });
		 return rightMenu;
	},
	listeners : {
		'select' : function(rowModel, record, index, eOpts ){
			var grid = this,
				plugin = grid.getPlugin('connectionbilladdnew_queryWaybillGrid_serialNoGrid'),
				waybillNo = record.get('waybillNo'),
				unsavedWaybill = load.connectionbilladdnew.connectionBillDetailGrid.store.findRecord('waybillNo',waybillNo,0,false,true,true),
				unsubmitedWaybill = load.connectionbilladdnew.unsubmitedWaybillGrid.store.findRecord('waybillNo',waybillNo,0,false,true,true);
			//取出record中的流水号list，做成map，放到第一层map的record中
			var serialNoStockList = record.get('serialNoStockList'),
				serialNoStockMap = new Ext.util.HashMap();
			for(var i in serialNoStockList){
				var serialNo = serialNoStockList[i];
				if((unsavedWaybill != null && unsavedWaybill.get('serialNoMap') !== '' && unsavedWaybill.get('serialNoMap').get(serialNo.serialNo) !== undefined)
					||(unsubmitedWaybill != null && unsubmitedWaybill.get('serialNoMap') !== '' && unsubmitedWaybill.get('serialNoMap').get(serialNo.serialNo) !== undefined)
						){
					continue;
				}
				var serialNoRecord = Ext.ModelManager.create(serialNoStockList[i], 'Foss.load.connectionbilladdnew.serialNoModel');
				serialNoStockMap.replace(serialNo.serialNo,serialNoRecord);
			}
			if(serialNoStockMap.getCount() !== 0){
				//将运单置于已勾选运单map中
				var recCopy = record.copy();
				load.connectionbilladdnew.selectedWaybillMap.add(waybillNo,recCopy);
				recCopy.set('serialNoMap',serialNoStockMap);
				load.connectionbilladdnew.updateQueryPageStaInfo();
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
		var plugin = grid.getPlugin('connectionbilladdnew_queryWaybillGrid_serialNoGrid');
		if(!Ext.isEmpty(plugin.getExpendRow())) {
			var item = plugin.getExpendRowBody();
			var store = item.getStore();
			var waybillNo = store.getAt(0).get('waybillNo');
			if(waybillNo == record.get('waybillNo')){
				item.getSelectionModel().deselectAll(true);
			}
		}
		var waybillNo = record.get('waybillNo');
		if(load.connectionbilladdnew.selectedWaybillMap.get(waybillNo) !== undefined){
			// 从map中移除和此运单对应的库存信息
			load.connectionbilladdnew.selectedWaybillMap.removeAtKey(waybillNo);
		}
		record.set('serialNoMap','');
		load.connectionbilladdnew.updateQueryPageStaInfo();
	},
	'itemcontextmenu' : function(view,record,item,index,e, eOpts ){
		var menu = this.getRightMenu(record,index);
		e.preventDefault();
       	menu.showAt(e.getXY());
	}
}
});

// 定义查询库存运单中流水号列表store
Ext.define('Foss.load.connectionbilladdnew.QueryWaybillSerialNoStore', {
	extend : 'Ext.data.Store',
	// 绑定一个模型
	model : 'Foss.load.connectionbilladdnew.serialNoModel',
	listeners : {
		'remove' : function(store, record, index, eOpts){
			var waybillNo = record.get('waybillNo'),
				serialNo = record.get('serialNo');
			//将移除的流水号从一级record的stockList中删除
			var superRec = load.connectionbilladdnew.queryWaybillGrid.store.findRecord('waybillNo',waybillNo,0,false,true,true),
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


// 定义查询库存运单中流水号列表grid
Ext.define('Foss.load.connectionbilladdnew.QueryWaybillSerialNoGrid', {
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
				serialNo = record.get('serialNo');
				unsubmitedWaybill = load.connectionbilladdnew.unsubmitedWaybillGrid.store.findRecord('waybillNo',waybillNo,0,false,true,true),
				unsavedWaybill = load.connectionbilladdnew.connectionBillDetailGrid.store.findRecord('waybillNo',waybillNo,0,false,true,true);
			if(!(
					(unsubmitedWaybill === null 
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
		text : '操作',
		align : 'center',
		items : [ {
			tooltip : '右移',
			iconCls : 'foss_icons_stl_sendmes',
			handler : function(grid, rowIndex, colIndex) {
				var store = grid.store;
				if(store.getCount() === 1){
					Ext.ux.Toast.msg('提示','请直接移动整个运单！', 'error', 1500);
					return;
				}
				if(this.iconCls === null){
					return;
				}
				var record = grid.store.getAt(rowIndex);
					waybillNo = record.get('waybillNo'),
					serialNo = record.get('serialNo'),
					isSelected = grid.getSelectionModel().isSelected(record),
					unsubmitedWaybill = load.connectionbilladdnew.unsubmitedWaybillGrid.store.findRecord('waybillNo',waybillNo,0,false,true,true),
					leftGrid = load.connectionbilladdnew.queryWaybillGrid,
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
					var plugin = load.connectionbilladdnew.unsubmitedWaybillGrid.getPlugin('connectionbilladdnew_unsubmitedWaybillGrid_serialNoGrid');
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
					var rightGrid = load.connectionbilladdnew.unsubmitedWaybillGrid;
						rightStore = rightGrid.store;
					rightStore.insert(rightStore.getCount(),rightWaybill);
				}
				//移除该行
				store.removeAt(rowIndex);
				//如果当前行被选中
				if(isSelected){
					//从map中移除
					var serialNoMap = load.connectionbilladdnew.selectedWaybillMap.get(waybillNo).get('serialNoMap');
					serialNoMap.removeAtKey(serialNo);
					//如果map中没有任何元素
					if(serialNoMap.getCount() ===0){
						load.connectionbilladdnew.selectedWaybillMap.removeAtKey(waybillNo);
						//如果此时一级表中的行被选择，则反选
						var sm = leftGrid.getSelectionModel()
							isSelectedWaybill = sm.isSelected(waybill)
						//反选
						if(isSelectedWaybill){
							sm.deselect(waybill,true)
						}
					}
					load.connectionbilladdnew.updateQueryPageStaInfo();
				}
				//修改左边表格的数据
				waybill.set('pieces',pieces - 1);
				waybill.set('weight',((weight/pieces)*waybill.get('pieces')).toFixed(2));
				waybill.set('cubage',((cubage/pieces)*waybill.get('pieces')).toFixed(2));
				//重新统计已选运单
				load.connectionbilladdnew.updateUnsubmitedWaybillStaInfo();
			}
		} ],
		renderer : function(value,metaData,record,rowIndex,colIndex,store,view){
			var waybillNo = record.get('waybillNo'),
				serialNo = record.get('serialNo');
				unsubmitedWaybill = load.connectionbilladdnew.unsubmitedWaybillGrid.store.findRecord('waybillNo',waybillNo,0,false,true,true),
				unsavedWaybill = load.connectionbilladdnew.connectionBillDetailGrid.store.findRecord('waybillNo',waybillNo,0,false,true,true);
			if( (unsubmitedWaybill === null 
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
		text : '流水号'
	}, {
		dataIndex : 'instorageDate',
		align : 'center',
		width : 145,
		text : '入库时间',
		renderer : function(value) {
			if(value!=null){
					var date = new Date(value);
					return Ext.Date.format(date, 'Y-m-d H:i:s');									
			}else{
					return null;
	}}
	}],
	bindData : function(record){
		var waybillNo = record.get('waybillNo'),
			grid = this,
			store = grid.store;
		store.loadData(record.get('serialNoStockList'));
		//如果之前被勾选，则勾选上之前选中的流水号
		var rec = load.connectionbilladdnew.selectedWaybillMap.get(waybillNo);
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
				waybillNo = record.get('waybillNo');
			var superRecord = superGrid.store.findRecord('waybillNo',waybillNo,0,false,true,true);//获取一级表格中该运单号对应的行记录
			var sm = superGrid.getSelectionModel();
			sm.select(superRecord,true,true);//勾选第一层表格中的行：第二个参数为true，为保留其他已勾选的行，第三个参数为true，表示勾选后不触发select事件
			//勾选后，将运单放入map中
			if(load.connectionbilladdnew.selectedWaybillMap.get(waybillNo) !== undefined){
				superRecord = load.connectionbilladdnew.selectedWaybillMap.get(waybillNo);
			}else{
				load.connectionbilladdnew.selectedWaybillMap.add(waybillNo,superRecord);
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
			load.connectionbilladdnew.updateQueryPageStaInfo();
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
				load.connectionbilladdnew.selectedWaybillMap.removeAtKey(waybillNo);
			}else{//如果第二层表格记录未全部反选，则从map中的选中行中删除该流水号对应的记录
				var selectedWaybill = load.connectionbilladdnew.selectedWaybillMap.get(waybillNo);
				serialNoMap = selectedWaybill.get('serialNoMap');
				serialNoMap.removeAtKey(serialNo);
			}
			load.connectionbilladdnew.updateQueryPageStaInfo();
		}
	},
	constructor : function(config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.load.connectionbilladdnew.QueryWaybillSerialNoStore');
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
Ext.define('Foss.load.connectionbilladdnew.moveButtonPanel', {
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
			 tooltip : '点击可将左侧全部运单移动到右侧',
		     handler : function() {
		     	var myMask = load.connectionbilladdnew.queryWayBillWindow.getLoadMask('运单右移中，请稍后...');
		     	myMask.show();
		     	var myMask2 = new Ext.LoadMask(load.connectionbilladdnew.queryWayBillWindow, {msg : '运单右移中，请稍后...'});
		    	myMask2.show();
		    	me.rightMoveAll();
		    	myMask.hide();
		    	myMask2.hide();
		     }
		},{
			iconCls : 'deppon_icons_turnright',
			tooltip : '点击可将左侧已勾选运单及流水号移动到右侧',
		     handler: function() {
		     	var myMask = load.connectionbilladdnew.queryWayBillWindow.getLoadMask('运单右移中，请稍后...');
		     	myMask.show();
		        me.rightMove();
		        myMask.hide();
		     }
		},{
			iconCls : 'deppon_icons_turnleft',
			tooltip : '点击可将右侧选中运单移动到左侧',
		     handler: function() {
		     	var myMask = load.connectionbilladdnew.queryWayBillWindow.getLoadMask('运单左移中，请稍后...');
		     	myMask.show();
		    	me.leftMove();
		    	myMask.hide();
		     }
		},{
			iconCls : 'deppon_icons_turnleftall',
			tooltip : '点击可将右侧全部运单移动到左侧',
		    handler: function() {
		    	var myMask = load.connectionbilladdnew.queryWayBillWindow.getLoadMask('运单左移中，请稍后...');
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
			unsubmitedGrid = load.connectionbilladdnew.unsubmitedWaybillGrid,
			unStore = unsubmitedGrid.store;
		//判断当前激活的是库存tab还是在途tab
			waybillGrid = load.connectionbilladdnew.queryWaybillGrid,
				waybillMap = load.connectionbilladdnew.selectedWaybillMap;
			if(waybillMap.getCount() === 0){
				Ext.ux.Toast.msg('提示', '左侧表格没有勾选任何库存运单！', 'error', 2000);
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
						var plugin = waybillGrid.getPlugin('connectionbilladdnew_queryWaybillGrid_serialNoGrid');
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
			load.connectionbilladdnew.selectedWaybillMap.clear();
			load.connectionbilladdnew.updateQueryPageStaInfo();
			load.connectionbilladdnew.updateUnsubmitedWaybillStaInfo();
	},
	rightMoveAll : function(){
		var me = this,
			waybillGrid,
			unsubmitedGrid = load.connectionbilladdnew.unsubmitedWaybillGrid,
			unStore = unsubmitedGrid.store;
		//判断当前激活的是库存tab还是在途tab
			var waybillGrid = load.connectionbilladdnew.queryWaybillGrid,
				store = waybillGrid.store,
				flag = false;
			if(store.getCount() === 0){
				Ext.ux.Toast.msg('提示', '库存运单不可为空！', 'error', 1000);
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
				load.connectionbilladdnew.updateQueryPageStaInfo();
			}
	},
	leftMove : function(){
		var me = this,
			unsubmitedGrid = load.connectionbilladdnew.unsubmitedWaybillGrid,
			unStore = unsubmitedGrid.store,
			selectedWaybillList = unsubmitedGrid.getSelectionModel().getSelection();
		if(selectedWaybillList.length === 0){
			Ext.ux.Toast.msg('提示', '未勾选右侧任何运单！', 'error', 1500);
			return;
		}
		for(var i in selectedWaybillList){
			var waybill = selectedWaybillList[i];
			unsubmitedGrid.leftMove(waybill);
		}
	},
	leftMoveAll : function(){
		var me = this,
			unsubmitedGrid = load.connectionbilladdnew.unsubmitedWaybillGrid,
			unStore = unsubmitedGrid.store;
		if(unStore.getCount() === 0){
			Ext.ux.Toast.msg('提示', '右侧没有任何运单！', 'error', 1500);
			return;
		}
		unStore.each(function(record){
			unsubmitedGrid.leftMove(record,'MANY');
		});
		unStore.removeAll();
		load.connectionbilladdnew.updateUnsubmitedWaybillStaInfo();
	}
});

//定义查询库存运单tabPanel
Ext.define('Foss.load.connectionbilladdnew.QueryWayBillTabPanel', {
	extend : 'Ext.tab.Panel',
	bodyCls : 'autoHeight',
	flex : 1,
    cls: 'innerTabPanel',
    queryWaybillGrid: null,
	getQueryWaybillGrid: function(){
		if(this.queryWaybillGrid==null){
			this.queryWaybillGrid = Ext.create('Foss.load.connectionbilladdnew.QueryWaybillGrid',{
				title: '库存货物',
		        tabConfig: {
		            tooltip: '查询部门库存货物',
		            width:100
		        }
			});
			load.connectionbilladdnew.queryWaybillGrid = this.queryWaybillGrid;
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
			Ext.getCmp('Foss_connectionbilladdnew_QueryForm_InstorageTime_ID').setVisible(true);
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
Ext.define('Foss.load.connectionbilladdnew.UnSubmitedWaybillStore', {
	extend : 'Ext.data.Store',
	// 绑定一个模型
	model : 'Foss.load.connectionbilladdnew.waybillStockModel',
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
});

//定义待提交流水号的store
Ext.define('Foss.load.connectionbilladdnew.UnSubmitedSerialNoStore', {
	extend : 'Ext.data.Store',
	// 绑定一个模型
	model : 'Foss.load.connectionbilladdnew.serialNoModel',
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
});

//定义待提交运单中流水号列表grid
Ext.define('Foss.load.connectionbilladdnew.unsubmitedWaybillSerialNoGrid', {
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
		text : '操作',
		align : 'center',
		items : [ {
			tooltip : '左移',
			iconCls : 'deppon_icons_remove',
			handler : function(grid, rowIndex, colIndex) {
				var store= grid.store,
					record = store.getAt(rowIndex),
					waybillNo = record.get('waybillNo'),
					superRec = load.connectionbilladdnew.unsubmitedWaybillGrid.store.findRecord('waybillNo',waybillNo,0,false,true,true),
					serialNo = record.get('serialNo'),
					waybillId = record.get('superId'),
					connectionBillNo = record.get('connectionBillNo'),
					oneWeight = superRec.get('weight')/superRec.get('pieces'),
					oneCubage = superRec.get('cubage')/superRec.get('pieces');
				if(store.getCount() === 1){
					Ext.ux.Toast.msg('提示', '请左移整条运单！', 'error', 1500);
					return;
				}
				store.removeAt(rowIndex);
				//从左侧表格的流水号map中删除该流水号
				superRec.get('serialNoMap').removeAtKey(serialNo);
				//如果移除的是库存流水号
					//左侧是否有该运单
					var leftGrid = load.connectionbilladdnew.queryWaybillGrid,
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
						if(!load.connectionbilladdnew.inArray(leftSerialNoList,serialNo)){
							leftSerialNoList.push(record.data);
							leftWaybill.set('pieces',leftWaybill.get('pieces') + 1);
							leftWaybill.set('weight',(leftWaybill.get('weight') + oneWeight).toFixed(2));
							leftWaybill.set('cubage',(leftWaybill.get('cubage') + oneCubage).toFixed(2));
						}
						//如果左侧流水号已展开，则重新加载流水号
						var plugin = leftGrid.getPlugin('connectionbilladdnew_queryWaybillGrid_serialNoGrid');
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
				load.connectionbilladdnew.updateUnsubmitedWaybillStaInfo();
			}
		} ]
	}, {
		dataIndex : 'serialNo',
		align : 'center',
		width : 120,
		xtype : 'ellipsiscolumn',
		text : '流水号'
	}],
	bindData : function(record){
		var grid = this;
		grid.store.loadData(record.get('serialNoMap').getValues());
		console.log(grid.store);
	},
	constructor : function(config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.load.connectionbilladdnew.UnSubmitedSerialNoStore');
		me.callParent([cfg]);
	}
});


//定义待提交运单的grid
Ext.define('Foss.load.connectionbilladdnew.UnSubmitedWaybillGrid', {
	extend : 'Ext.grid.Panel',
	title : '已选运单列表',
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
		pluginId : 'connectionbilladdnew_unsubmitedWaybillGrid_serialNoGrid',
		rowsExpander : false,
		// 行体内容
		rowBodyElement : 'Foss.load.connectionbilladdnew.unsubmitedWaybillSerialNoGrid'
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
		me.store = Ext.create('Foss.load.connectionbilladdnew.UnSubmitedWaybillStore');
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
			id : 'Foss_load_connectionBillAddnew_UnsubmitedWaybillTotalPieces',
			fieldLabel: '总件数',
			columnWidth : 1/3,
			value : 0
		},{
			id : 'Foss_load_connectionBillAddnew_UnsubmitedWaybillTotalWeight',
			fieldLabel: '总重量(千克)',
			labelWidth : 120,
			columnWidth : 1/3,
			value : 0
		},{
			id : 'Foss_load_connectionBillAddnew_UnsubmitedWaybillTotalCubage',
			fieldLabel: '总体积(方)',
			columnWidth : 1/3,
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
			load.connectionbilladdnew.updateUnsubmitedWaybillStaInfo();
		}
		var stockSerialNoMap = new Ext.util.HashMap();//存储库存的流水号，流水号为key，record.data为value
		//遍历此处移除的流水号map
		serialNoMap.each(function(serialNo,serialNoRec,length){
			//如果为库存流水号
				stockSerialNoMap.replace(serialNo,serialNoRec.data);
		});
		//分组后对库存流水号和在途流水号分别进行左移
		if(stockSerialNoMap.getCount() !== 0){
			//左侧是否有该运单
			var leftGrid = load.connectionbilladdnew.queryWaybillGrid,
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
					if(!load.connectionbilladdnew.inArray(leftSerialNoList,serialNo)){
						leftSerialNoList.push(serialNoData);
						leftWaybill.set('pieces',leftWaybill.get('pieces') + 1);
						leftWaybill.set('weight',(leftWaybill.get('weight') + oneWeight).toFixed(2));
						leftWaybill.set('cubage',(leftWaybill.get('cubage') + oneCubage).toFixed(2));
					}
				});
				//如果左侧流水号已展开，则重新加载流水号
				var plugin = leftGrid.getPlugin('connectionbilladdnew_queryWaybillGrid_serialNoGrid');
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
		text : '操作',
		align : 'center',
		items : [ {
			tooltip : '左移',
			iconCls : 'deppon_icons_remove',
			handler : function(grid, rowIndex, colIndex) {
				var record = grid.store.getAt(rowIndex);
				load.connectionbilladdnew.unsubmitedWaybillGrid.leftMove(record);
			}
		} ]
	}, {
		dataIndex : 'waybillNo',
		align : 'center',
		width : 80,
		xtype : 'ellipsiscolumn',
		text : '运单号'
	}, {
		dataIndex : 'transProperty',
		align : 'center',
		width : 95,
		text : '运输性质'
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
		text : '货物名称'
	}, {
		dataIndex : 'packing',
		align : 'center',
		width : 60,
		xtype : 'ellipsiscolumn',
		text : '包装'
	}, {
		dataIndex : 'cubage',
		align : 'center',
		width : 60,
		xtype : 'ellipsiscolumn',
		text : '体积'
	}, {
		dataIndex : 'weight',
		align : 'center',
		width : 60,
		xtype : 'ellipsiscolumn',
		text : '重量'
	}, {
		dataIndex : 'pieces',
		align : 'center',
		flex : 1,
		text : '件数'
	}, {
		dataIndex : 'arriveDept',
		align : 'center',
		width : 150,
		xtype : 'ellipsiscolumn',
		text : '到达部门'
	}, {
		dataIndex : 'insuranceValue',
		align : 'center',
		width : 60,
		xtype : 'ellipsiscolumn',
		text : '保险价值'
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
		text : '开单件数'
	}, {
		dataIndex : 'isPreciousGoods',
		align : 'center',
		width : 80,
		text : '是否贵重物品',
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
		text : '运单备注'
	} ],
	getRightMenu : function(record){
		var grid = this;
		function leftMoveOne(){
			grid.leftMove(record);
		}
		var	rightMenu =	new Ext.menu.Menu({
	        items : [{
                text : '左移',
                handler : leftMoveOne
	        }]
	     });
		 return rightMenu;
	}
});

load.connectionbilladdnew.unsubmitedWaybillGrid = Ext.create('Foss.load.connectionbilladdnew.UnSubmitedWaybillGrid');

//定义查询窗口中间部分的panel
Ext.define('Foss.load.connectionbilladdnew.QueryWindowCenterPanel', {
	extend : 'Ext.panel.Panel',
	gridTabPanel : null,
	getGridTabPanel : function(){
		if(this.gridTabPanel==null){
			this.gridTabPanel = Ext.create('Foss.load.connectionbilladdnew.QueryWayBillTabPanel');
			load.connectionbilladdnew.queryWaybillTabPanel = this.gridTabPanel;
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
					tooltip : '点击可收起或展开待选运单表格',
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
			Ext.create('Foss.load.connectionbilladdnew.moveButtonPanel'),
			Ext.create('Ext.panel.Panel',{
				width : 10,
				items : [{
					xtype : 'button',
					border : false,
					cls : 'flexleft',
					tooltip : '点击可收起或展开已选运单表格',
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
			load.connectionbilladdnew.unsubmitedWaybillGrid],
		me.callParent([cfg]);
	}
});
// 定义查询交接运单窗口
Ext.define('Foss.load.connectionbilladdnew.QueryWayBillWindow', {
	extend : 'Ext.window.Window',
	closeAction : 'hide',
	modal : true,
//	maximized : true,
//	width : document.body.clientWidth,
	width : 1260,
	height : 930,
	title : '查询交接运单',
	queryWaybillForm: null,
	getQueryWaybillForm: function(){
		if(this.queryWaybillForm==null){
			this.queryWaybillForm = Ext.create('Foss.load.connectionbilladdnew.QueryWaybillForm');
			load.connectionbilladdnew.queryWaybillForm = this.queryWaybillForm;
		}
		return this.queryWaybillForm;
	},
	centerPanel : null,
	getCenterPanel : function(){
		if(this.centerPanel==null){
			this.centerPanel = Ext.create('Foss.load.connectionbilladdnew.QueryWindowCenterPanel');
			load.connectionbilladdnew.queryWindowCenterPanel = this.centerPanel;
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
			load.connectionbilladdnew.selectedWaybillMap.clear();
			if(load.connectionbilladdnew.queryWaybillGrid.store.getCount() != 0){
				load.connectionbilladdnew.queryWaybillGrid.store.removeAll();//清空store
				load.connectionbilladdnew.pagingBar.onLoad();
			}
			//清空已选运单列表
			load.connectionbilladdnew.unsubmitedWaybillGrid.store.removeAll();
			load.connectionbilladdnew.updateUnsubmitedWaybillStaInfo();
			//清空统计信息
			Ext.getCmp('Foss_load_connectionBillAddnew_QueryPageTotalPieces').setValue(0);
			Ext.getCmp('Foss_load_connectionBillAddnew_QueryPageTotalWeight').setValue(0);
			Ext.getCmp('Foss_load_connectionBillAddnew_QueryPageTotalCubage').setValue(0);
		}
	},
	buttons : [{
		text : '确定',
		cls : 'yellow_button',
		handler : function(){
			//若未勾选任何运单，则提示
			var unGrid = load.connectionbilladdnew.unsubmitedWaybillGrid,
				unStore = unGrid.store;
			if(unStore.getCount() == 0){
				Ext.ux.Toast.msg('提示', '右侧列表没有任何运单！', 'error', 1000);
				return;
			}
			//获取主页面的表格
			var mainGrid = load.connectionbilladdnew.connectionBillDetailGrid;
			//获取主页面的扩展组件
			var plugin = mainGrid.getPlugin('Foss_connectionbilladdnew_mainPage_serialNoGrid_ID'),
				myMask = load.connectionbilladdnew.queryWayBillWindow.getLoadMask('运单添加中，请稍后...');
		     myMask.show();
		     
		     if(!load.connectionbilladdnew.validateHandoverbillDetailsTransProperty(unStore, mainGrid.store.getAt(0))){
				myMask.hide();
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
					//将该运单插入主页面
					mainGrid.store.insert(mainGrid.store.getCount(),newRecord);
				}
			});
			myMask.hide();
			this.ownerCt.ownerCt.close();
		}
	}, {
		text : '取消',
		handler : function(){
			this.ownerCt.ownerCt.close();
		}
	} ]
});

//定义控件的弹出框方法
load.connectionbilladdnew.connectionBillAlert = function(cmp,icon){
	Ext.MessageBox.show({
        title: '提示',
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
load.connectionbilladdnew.connectionBillDetailGrid = Ext.create('Foss.load.connectionbilladdnew.ConnectionBillDetailGrid');

Ext.onReady(function() {
	// Ext.QuickTips.init();
	//定义变量，记录交接单是否已被保存
	load.connectionbilladdnew.isSaved = 'N';
	// 定义基本信息表单
	var addNewForm = load.connectionbilladdnew.addNewForm = Ext.create('Foss.load.connectionbilladdnew.AddNewForm');
	
	function updateHandOverTime(){
		addNewForm.getForm().findField('handOverTime').setValue(load.connectionbilladdnew.connectionBillGetDateTime());
	}
	var intervalControl = window.setInterval(updateHandOverTime,1000);
	load.connectionbilladdnew.queryWayBillWindow = Ext.create('Foss.load.connectionbilladdnew.QueryWayBillWindow');
	Ext.create('Ext.panel.Panel', {
		id : 'T_load-connectionbilladdnewindex_content',
		cls : "panelContentNToolbar",
		bodyCls : 'panelContent-body',
		layout : 'auto',//
		items : [ addNewForm, load.connectionbilladdnew.connectionBillDetailGrid,{
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
				id : 'Foss_load_connectionbilladdnew_mainPage_saveButton_ID',
				text : '保存',
				handler : function() {
					var form = addNewForm.getForm();
					if(form.isValid()){
						//若未添加任何运单，则无法保存
						if(load.connectionbilladdnew.connectionBillDetailGrid.store.getCount() == 0){
							Ext.Msg.show({
							     title : '提示',
							     msg : '未添加任何运单',
							     buttons : Ext.Msg.OK,
							     icon: Ext.Msg.WARNING
							});
							return;
						}
						var reg=new RegExp('-','g');
						var  waybillStore = load.connectionbilladdnew.connectionBillDetailGrid.store;
						//构造交接单基本信息实体
						var connectionBillEntity=new Object();
						var params = form.getValues();
						var departDeptName=form.findField('departDept').getRawValue();//出发部门名称
						var departDeptCode=form.findField('departDept').getValue();//出发部门编码
						var arriveDeptName=form.findField('arriveDept').getRawValue();//到达接驳点名称
						var arriveDeptCode=form.findField('arriveDept').getValue();//到达接驳点编码
						var waybillQtyTotal=Ext.getCmp('Foss_load_connectionBillAddnew_MainPageTotalCount').getValue();//总票数
						var goodsQtyTotal=Ext.getCmp('Foss_load_connectionBillAddnew_MainPageTotalPieces').getValue();//总件数
						var volumeTotal=Ext.getCmp('Foss_load_connectionBillAddnew_MainPageTotalVolume').getValue();//总体积
						var weightTotal=Ext.getCmp('Foss_load_connectionBillAddnew_MainPageTotalWeight').getValue();//总重量
						var driverName=form.findField('driver').getRawValue();//司机姓名
						var driverCode=form.findField('driver').getValue();//司机工号
						var note=params.note;//备注
						var driverTel=params.driverTel;//司机电话
						var vehicleNo=params.vehicleNo;//车牌号
						var handOverType=params.handOverType;
						if(!Ext.isEmpty(params.loadEndTime)){
							params.loadEndTime = new Date(params.loadEndTime.replace(reg,'/'));					
						}
						var loadEndTime=params.loadEndTime ;//装车完成时间
						Ext.Object.merge(connectionBillEntity,{
						'departDeptName' : departDeptName,
						'departDeptCode' : departDeptCode,
						'arriveDeptName' : arriveDeptName,
						'arriveDeptCode' : arriveDeptCode,
						'waybillQtyTotal' : waybillQtyTotal,
						'goodsQtyTotal' : goodsQtyTotal,
						'volumeTotal' : volumeTotal,
						'weightTotal' : weightTotal,
						'driverName' : driverName,
						'driverCode' : driverCode,
						'notes' : note,
						'driverTel' : driverTel,
						'vehicleNo' : vehicleNo,
						'handOverType':handOverType,
						'loadEndTime' : loadEndTime
						});
						
						//运单list//流水号list
						var wayBillList = [],	
							serialNoList = [];
						for(var i = 0;i < waybillStore.getCount();i++){
							var record = waybillStore.getAt(i),
								waybill = record.data;
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
							wayBillList.push(waybillCopy);
						}
						
						//构造传到后台的json数据
						var data = {
								'connectionBillVo' : {'newConnectionBillDto': {
									'connectionBillEntity' : connectionBillEntity,
									'waybillStockList' : wayBillList,
									'serialNoStockList' : serialNoList
								}
							}
						};
						//mask
						var mainPanel = Ext.getCmp('T_load-connectionbilladdnewindex_content');
						var myMask = new Ext.LoadMask(mainPanel, {msg : "数据提交中，请稍等..."});
		 				myMask.show();
						//保存交接单数据
						Ext.Ajax.request({
							url : load.realPath('saveConnectionBill.action'),
							jsonData : data,
							timeout : 300000,
							success : function(response){
								//获取后台重新生成的交接单号
								var result = Ext.decode(response.responseText);
								var connectionBillNo = result.connectionBillVo.connectionBillNo;
								//提示保存成功，展示交接单号
								load.connectionbilladdnew.prtconnectionBillNo = connectionBillNo;
								Ext.Msg.show({
								     title : '提示',
								     msg : '保存成功，交接单号为：' + connectionBillNo,
								     buttons : Ext.Msg.OK,
								     icon: Ext.Msg.INFO
								});
								load.connectionbilladdnew.isSaved = 'Y';
								myMask.hide();
								
								//重新设置交接单号
								form.findField('connectionBillNo').setValue(connectionBillNo);
								//设置form所有控件为只读
								var formCmps = form.getFields().getRange(0,form.getFields().getCount());
								for(var i in formCmps){
									formCmps[i].setReadOnly(true);
								}
								//隐藏“查询交接运单”、“保存”按钮
								Ext.getCmp('Foss_load_connectionbilladdnew_mainPage_saveButton_ID').setVisible(false);
								Ext.getCmp('Foss_load_connectionbilladdnew_mainPage_queryButton_ID').setVisible(false);
								//隐藏运单grid和流水号grid前的操作列并设置为不可用
								load.connectionbilladdnew.connectionBillDetailGrid.columns[1].setVisible(false);
								//获取展开的流水号grid
								var plugin = load.connectionbilladdnew.connectionBillDetailGrid.getPlugin('Foss_connectionbilladdnew_mainPage_serialNoGrid_ID');
								var pluginGrid = plugin.getExpendRowBody();
								if(pluginGrid != null){
									pluginGrid.columns[0].setVisible(false);
								}
							},
							exception : function(response) {
								myMask.hide();
			    				var result = Ext.decode(response.responseText);
			    				top.Ext.MessageBox.alert('提示','保存失败，' + result.message);
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
		renderTo : 'T_load-connectionbilladdnewindex-body'
	});
	load.connectionbilladdnew.addNewForm.getForm().findField('departDept').setCombValue(load.connectionbilladdnew.orgName,load.connectionbilladdnew.orgCode);
	load.connectionbilladdnew.addNewForm.getForm().findField('connectionBillNo').setValue(load.connectionbilladdnew.connectionBillNo);
});