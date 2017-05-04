
//定义map，存储修改前的流水号map
load.connectionbillmodify.oldSerialNoMap = new Ext.util.HashMap();
load.connectionbillmodify.allSerialNoMap = new Ext.util.HashMap();
load.connectionbillmodify.oldWaybillMap=new Ext.util.HashMap();
load.connectionbillmodify.updatedWaybillMap=new Ext.util.HashMap();
load.connectionbillmodify.oldSerialNoMap=new Ext.util.HashMap();
load.connectionbillmodify.deletedSerialNoMap=new Ext.util.HashMap();
load.connectionbillmodify.deletedWaybillMap=new Ext.util.HashMap();



//自定义方法，将Ext.util.HashMap转化为json格式，以便传往后台  
load.connectionbillmodify.convertMap2Json = function(beforeMap){
	var map = {};
	beforeMap.each(function(key,value,length){
		var v = '{'+ '"' +key + '"' +':'+Ext.encode(value.data)+'}';
		var record = Ext.decode(v);
		map = Ext.merge(map,record);
	});
	return map;
}

load.connectionbillmodify.updateMainPageStaInfo = function (store){
	//更新主页总票数
	var totalCountCmp = Ext.getCmp('Foss_load_connectionBillModify_MainPageTotalCount');
	totalCountCmp.setValue(load.connectionbillmodify.connectionBillDetailGrid.store.getCount());
	//遍历主页store，获得总件数、总体积、总重量
	var totalPieces = 0,totalVolume = 0,totalWeight = 0,totalCodAmount = 0;
	store.each(function(record){
		totalPieces += record.get('pieces');
		totalWeight += record.get('weight');
		totalVolume += record.get('cubage');
	});
	//转换后体积
	if(totalWeight != 0){
		totalWeight = totalWeight.toFixed(2);//如果不为0，则四舍五入，保留两位小数
	}
	if(totalVolume != 0){
		totalVolume = totalVolume.toFixed(2);
	}
	//更新主页总件数、总体积、总重量、代收货款总额
	Ext.getCmp('Foss_load_connectionBillModify_MainPageTotalPieces').setValue(totalPieces);
	Ext.getCmp('Foss_load_connectionBillModify_MainPageTotalVolume').setValue(totalVolume);
	Ext.getCmp('Foss_load_connectionBillModify_MainPageTotalWeight').setValue(totalWeight);
};

//定义流水号列表Model
Ext.define('Foss.load.connectionbillmodify.serialNoModel', {
	extend : 'Ext.data.Model',
	// 定义字段
	fields : [{
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
		convert: function(value) {
			if(!value) return '';
			var date = new Date(value);						
			var formatStr = 'Y-m-d H:i:s';
			return Ext.Date.format(date, formatStr);
		},
		defaultValue : new Date()
	}]
});


//定义交接单基本信息Model
Ext.define('Foss.load.connectionbillmodify.ConnectionBillBaseInfoModel', {
	extend : 'Ext.data.Model',
	//定义字段
	fields : [{
		name : 'connectionBillNo',
		type : 'string'
	}, {
		name : 'handOverType',
		type : 'string'
	}, {
		name : 'handOverTime',
		type : 'date',
		convert: function(value) {
			if(!value) return '';
			var date = new Date(value);						
			var formatStr = 'Y-m-d H:i:s';
			return Ext.Date.format(date, formatStr);
		}
	}, {
		name : 'departDeptName',
		type : 'string'
	}, {
		name : 'departDeptCode',
		type : 'string'
	}, {
		name : 'arriveDeptName',
		type : 'string'
	}, {
		name : 'arriveDeptCode',
		type : 'string'
	}, {
		name : 'vehicleNo',
		type : 'string'
	}, {
		name : 'driverName',
		type : 'string'
	}, {
		name : 'driverCode',
		type : 'string'
	}, {
		name : 'driverTel',
		type : 'string'
	}, {
		name : 'loadEndTime',
		type : 'date',
		convert: function(value) {
			if(!value) return '';
			var date = new Date(value);						
			var formatStr = 'Y-m-d H:i:s';
			return Ext.Date.format(date, formatStr);
		}
	}, {
		name : 'createUserName',
		type : 'string'
	}, {
		name : 'modifyUserName',
		type : 'string'
	}, {
		name : 'notes',
		type : 'string'
	}, {
		name : 'isPda',
		type : 'string'
	}, {
		name : 'waybillQtyTotal',
		type : 'number'
	}, {
		name : 'goodsQtyTotal',
		type : 'number'
	}, {
		name : 'volumeTotal',
		type : 'number'
	}, {
		name : 'weightTotal',
		type : 'number'
	}]});


//交接单基本信息form
Ext.define('Foss.load.connectionbillmodify.BasicInfoForm', {
	extend : 'Ext.form.Panel',
	title : '交接单基本信息',
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
		fieldLabel : '交接类型',
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
	            {"key":"EXPRESS_CONNECTION_HANDOVER", "value":"接驳交接单"}
	        ]
	    })
	 },{
		fieldLabel : '交接单编号',
		name : 'connectionBillNo'
	}, {
		fieldLabel : '交接时间',
		name : 'handOverTime'
	}, {
		fieldLabel : '出发部门',
		name : 'departDeptName'
	},
	{fieldLabel : '出发部门',
	name : 'departDeptCode',
	hidden:true
	}
, {
		name : 'arriveDeptCode',
		hidden : true
	}, {
		name : 'arriveDeptName',
		fieldLabel : '到达接驳点'
	},{
		fieldLabel : '车牌号',
		readOnly : true,
		xtype : 'commontruckselector',
		name : 'vehicleNo',
		queryParam : 'truckVo.truck.vehicleNoNoLike',
		queryAllFlag : false,
		allowBlank : false
	},{
		fieldLabel : '司机',
		readOnly : true,
		name : 'driver',
		xtype : 'commondriverselector',
		allowBlank : true
	}, {
		fieldLabel : '司机电话',
		name : 'driverTel'
	}, {
		name : 'driverName',
		hidden : true
	},{
		fieldLabel : '装车完成时间',
		name : 'loadEndTime',
		xtype : 'datetimefield_date97',
		time : true,
		readOnly : true,
		
		id : 'Foss_connectionBillModify_loadEndTime_ID',
		allowBlank : false,
		dateConfig: {
			el : 'Foss_connectionBillModify_loadEndTime_ID-inputEl'
		}
	},{
		fieldLabel : '制单人',
		name : 'createUserName'
	}, {
		fieldLabel : '修改人',
		name : 'modifyUserName'
	},{
		boxLabel : '是否PDA扫描',
		name : 'isPda',
		xtype : 'checkbox'
	},{
		fieldLabel : '备注',
		readOnly : false,
		maxLength : 300,
		name : 'notes',
		columnWidth : .5
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
});

//定义交接单明细Model
Ext.define('Foss.load.connectionbillmodify.waybillStockModel', {
	extend : 'Ext.data.Model',
	// 定义字段
	fields : [{
		name : 'waybillNo',
		type : 'string'
	}, {
		name : 'transProperty',
		type : 'string'
	}, {
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
		convert: function(value) {
			if(!value) return '';
			var date = new Date(value);						
			var formatStr = 'Y-m-d H:i:s';
			return Ext.Date.format(date, formatStr);
		}
	},{
		name : 'instorageDate',
		type : 'date',
		convert: function(value) {
			if(!value) return '';
			var date = new Date(value);						
			var formatStr = 'Y-m-d H:i:s';
			return Ext.Date.format(date, formatStr);
		}
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
		name : 'goodsType',
		type : 'string'
	}]
});

//定义交接单明细store
Ext.define('Foss.load.connectionbillmodify.ConnectionBillDetailStore', {
	extend : 'Ext.data.Store',
	// 绑定一个模型
	model : 'Foss.load.connectionbillmodify.waybillStockModel',
	// 定义一个代理对象
	proxy : {
		type : 'ajax',
		actionMethods:'POST',
		// 请求的url
		url : load.realPath('queryConnectionBillDetailByNo.action'),
		// 定义一个读取器
		reader : {
			// 以JSON的方式读取
			type : 'json',
			// 定义读取JSON数据的根对象
			root : 'connectionBillVo.waybillStockList',
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
					'connectionBillVo.connectionBillNo': load.connectionbillmodify.connectionBillNo
				}
			});	
		},
		'load' : function(store, records, successful, eOpts){
			
			//交接单修改前的运单加载完毕后，存储于load.connectionbillmodify.load.connectionbillmodify.deletedWaybillMap
			for(var i in records){
				var record = records[i];
				var waybillNo = record.get('waybillNo');
				load.connectionbillmodify.oldWaybillMap.replace(waybillNo,record);
			}
		
		},
		'update' : function(store,record,operation,modifiedFieldNames,eOpts){
			//如果更新了“实际重量”和“实际体积”，则重新统计
			for(var i in modifiedFieldNames){
				if(
				 modifiedFieldNames[i] == 'weight' 
					||	modifiedFieldNames[i] == 'cubage' 
					||  modifiedFieldNames[i] == 'pieces'){
					load.connectionbillmodify.updateMainPageStaInfo(store);
				}
			}
			var waybillNo = record.get('waybillNo');
			if(load.connectionbillmodify.oldWaybillMap.get(waybillNo) != null){
				load.connectionbillmodify.updatedWaybillMap.replace(waybillNo,record);
			}
		},
		'datachanged' : function(store,record,operation,modifiedFieldNames,eOpts){
			load.connectionbillmodify.updateMainPageStaInfo(store);
		}
	}
});

//定义运单明细store
Ext.define('Foss.load.connectionbillmodify.WaybillDetailStore', {
	extend : 'Ext.data.Store',
	// 绑定一个模型
	model : 'Foss.load.connectionbillmodify.serialNoModel',
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
});

// 定义运单明细grid
Ext.define('Foss.load.connectionbillmodify.WaybillDetailGrid', {
	extend : 'Ext.grid.Panel',
	columnLines: true,
	frame: true,
	enableColumnHide : false,//配置该属性可取消grid自定义显示列功能
	baseCls : 'handOverBill_addNew_serialNoGap',
	autoScroll : true,
	width : 180,
	store : Ext.create('Foss.load.connectionbillmodify.WaybillDetailStore'),
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
				//从主页面的流水号map中删除该流水号
				var serialMap = load.connectionbillmodify.allSerialNoMap.get(waybillNo);
				serialMap.removeAtKey(serialNo);
				//如果修改前的流水号map中存在有该流水号，则将该流水号放于load.connectionbillmodify.deletedSerialNoMap
				if(load.connectionbillmodify.oldSerialNoMap.get(waybillNo) != null && load.connectionbillmodify.oldSerialNoMap.get(waybillNo).get(serialNo) != null){
					//若为第一次删除，则新建map，如果不是，则取出map，加入该流水号后重新放入
					if(load.connectionbillmodify.deletedSerialNoMap.get(waybillNo) == null){
						var tempMap = new Ext.util.HashMap();
						tempMap.replace(serialNo,record);
						load.connectionbillmodify.deletedSerialNoMap.replace(waybillNo,tempMap);
					}else{
						var tempMap = load.connectionbillmodify.deletedSerialNoMap.get(waybillNo);
						tempMap.replace(serialNo,record);
						load.connectionbillmodify.deletedSerialNoMap.replace(waybillNo,tempMap);
					}					
				}
				//更新一级表格内的信息
				waybillStore = load.connectionbillmodify.connectionBillDetailGrid.store;
				var waybillRecord = waybillStore.findRecord('waybillNo', waybillNo, 0, false,true,true);
				//删掉map里的该流水号
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
		//若已保存，则隐藏操作列
		if(load.connectionbillmodify.isSaved == 'Y'){
			this.columns[0].setVisible(false);
		}
		var waybillNo = record.get('waybillNo');
		var serialNoRecords = load.connectionbillmodify.allSerialNoMap.get(waybillNo).getValues();
		this.store.loadData(serialNoRecords);
	}
});

//定义交接单明细列表
Ext.define('Foss.load.connectionbillmodify.ConnectionBillDetailGrid', {
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
	store : Ext.create('Foss.load.connectionbillmodify.ConnectionBillDetailStore'),
	// 定义行展开
	plugins : [ {
		header: true,
		ptype : 'rowexpander',
		// 定义行展开模式（单行与多行），默认是多行展开(值true)
		rowsExpander : false,
		// 行体内容
		rowBodyElement : 'Foss.load.connectionbillmodify.WaybillDetailGrid',
		pluginId : 'Foss_connectionBillModify_mainPage_serialNoGrid_ID'
	},Ext.create('Ext.grid.plugin.CellEditing', {
            clicksToEdit : 1,
            listeners : {
				'beforeedit' : function(editor,e,eOpts){
					//如果已经保存，则禁止编辑
					if(load.connectionbillmodify.isSaved == 'Y'){
						return false;
					}
				}
			}
        })],
	tbar : ['->',{
			xtype : 'container',
			html : '&nbsp'
		},{
			xtype : 'container',
			html : '&nbsp'
		},{
		xtype : 'button',
		disabled: !load.connectionbillmodify.isPermission('load/printnewConnectionBilllButton'),
		hidden: !load.connectionbillmodify.isPermission('load/printnewConnectionBilllButton'),
		text : '打印',
		handler : function(){
			
			
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
				fieldLabel :'总票数',
				xtype : 'textfield',
				readOnly : true,
				columnWidth : 1/5,
				value : 0,
				id : 'Foss_load_connectionBillModify_MainPageTotalCount'
			},{
				fieldLabel : '总件数',
				xtype : 'textfield',
				readOnly : true,
				columnWidth : 1/5,
				value : 0,
				id : 'Foss_load_connectionBillModify_MainPageTotalPieces'
			},{
				fieldLabel : '总重量(千克)',
				xtype : 'textfield',
				readOnly : true,
				labelWidth : 120,
				columnWidth : 1/5,
				value : 0,
				id : 'Foss_load_connectionBillModify_MainPageTotalWeight'
			},{
				fieldLabel : '总体积(方)',
				xtype : 'textfield',
				readOnly : true,
				columnWidth : 1/5,
				value : 0,
				id : 'Foss_load_connectionBillModify_MainPageTotalVolume'
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
				var record = grid.store.getAt(rowIndex),
					waybillNo = record.get('waybillNo');
				load.connectionbillmodify.allSerialNoMap.removeAtKey(waybillNo);
				//若修改前的运单map中有此运单，则从load.connectionbillmodify.oldWaybillMap中取出该运单放于deletedWaybillMap,流水号置于load.connectionbillmodify.deletedSerialNoMap中
				if(load.connectionbillmodify.oldWaybillMap.get(waybillNo) !== undefined){
					load.connectionbillmodify.deletedWaybillMap.replace(waybillNo,load.connectionbillmodify.oldWaybillMap.get(waybillNo));
					//此处无需判断删除的流水号是新增的还是原来就有的， 因为直接取出原来的流水号Map覆盖之
					var serialNoMap = load.connectionbillmodify.oldSerialNoMap.get(waybillNo);
					load.connectionbillmodify.deletedSerialNoMap.replace(waybillNo,serialNoMap.clone());
				}
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
		text : '运输性质编码'
	}, {
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
	}, {
		dataIndex : 'note',
		align : 'center',
		xtype : 'ellipsiscolumn',
		flex : 1,
		text : '备注',
		editor : {
			xtype : 'textarea',
			maxLength : 300
		}
	}, {
		dataIndex : 'goodsName',
		align : 'center',
		flex : 1,
		text : '货物名称'
	}, {
		dataIndex : 'packing',
		align : 'center',
		flex : 1,
		text : '包装'
	}, {
		dataIndex : 'waybillNote',
		align : 'center',
		flex : 1,
		text :'运单备注'
	} ]
});
//加载交接单数据的方法
load.connectionbillmodify.loadData = function(connectionBillNo){
	//定义值，记录修改后是否已保存
	load.connectionbillmodify.isSaved = 'N';
	
	Ext.Ajax.request({
		url : load.realPath('queryConnectionBillByNo.action'),
		params : {'connectionBillVo.connectionBillNo': connectionBillNo},
		success : function(response){
			
			var result = Ext.decode(response.responseText);
			//定义基本信息实体
			var basicInfo = load.connectionbillmodify.basicInfo=result.connectionBillVo.connectionBillEntity;
			//绑定model
			var basicInfoRecord = load.connectionbillmodify.basicInfoRecord= Ext.ModelManager.create(basicInfo, 'Foss.load.connectionbillmodify.ConnectionBillBaseInfoModel');
//			//定义基本信息form
			var basicInfoForm =load.connectionbillmodify.basicInfoForm ;
			basicInfoForm.getForm().loadRecord(load.connectionbillmodify.basicInfoRecord);
//			//该交接单是否由PDA生成
			load.connectionbillmodify.isPda = basicInfoRecord.get('isPda');
//			//给基本信息form加载值
			var form = basicInfoForm.getForm();
			form.loadRecord(basicInfoRecord);
			//处理司机信息
			var driverCmp = basicInfoForm.getForm().findField('driver');
			driverCmp.setCombValue(basicInfo.driverName,basicInfo.driverCode);
			basicInfoForm.getForm().findField('driverName').setValue(basicInfo.driverName);
			if(load.connectionbillmodify.isPda == 'Y'){
				form.findField('isPda').setValue(true);
			}
			
			
			//弹出数据加载，禁止操作
			var myMask = new Ext.LoadMask(load.connectionbillmodify.connectionBillDetailGrid, {
				msg:"加载中，请稍后..."
			});
			myMask.show();
			//加载交接单中运单数据
			load.connectionbillmodify.connectionBillDetailGrid.store.load();
			//后台请求所有交接单下的流水号list，封装成Map
			Ext.Ajax.request({
				url : load.realPath('queryConnectionWaybillDetailByNos.action'),
				params : {'connectionBillVo.connectionBillNo': connectionBillNo},
				success : function(response){

					var result = Ext.decode(response.responseText);
					var serialNoList = result.connectionBillVo.serialNoList;
					//获取流水号list后，在前台封装为map
					for(var i in serialNoList){
						var serialNo = serialNoList[i];
						var serialNoRecord =  Ext.ModelManager.create(serialNo, 'Foss.load.connectionbillmodify.serialNoModel');
						var waybillNo = serialNoRecord.get('waybillNo');
						var serialNo = serialNoRecord.get('serialNo');
						if(load.connectionbillmodify.oldSerialNoMap.get(waybillNo) == null){
							var tempMap = new Ext.util.HashMap();
							tempMap.replace(serialNo,serialNoRecord);
							load.connectionbillmodify.oldSerialNoMap.replace(waybillNo,tempMap);//此处需使用clone new出一个新map，否则两map将是同一引用
							load.connectionbillmodify.allSerialNoMap.replace(waybillNo,tempMap.clone());
						}else{
							var tempMap = load.connectionbillmodify.oldSerialNoMap.get(waybillNo);
							tempMap.replace(serialNo,serialNoRecord);
							load.connectionbillmodify.oldSerialNoMap.replace(waybillNo,tempMap);
							load.connectionbillmodify.allSerialNoMap.replace(waybillNo,tempMap.clone());
						}
					}
					myMask.hide();
				
					
				}
			});
		
			//从基本信息record中获取值，给统计信息赋值
			Ext.getCmp('Foss_load_connectionBillModify_MainPageTotalCount').setValue(basicInfoRecord.get('waybillQtyTotal'));
			Ext.getCmp('Foss_load_connectionBillModify_MainPageTotalPieces').setValue(basicInfoRecord.get('goodsQtyTotal'));
			Ext.getCmp('Foss_load_connectionBillModify_MainPageTotalWeight').setValue(basicInfoRecord.get('weightTotal'));
			Ext.getCmp('Foss_load_connectionBillModify_MainPageTotalVolume').setValue(basicInfoRecord.get('volumeTotal'));
			
		}
	});
}


// 定义运单列表
load.connectionbillmodify.basicInfoForm = Ext.create('Foss.load.connectionbillmodify.BasicInfoForm');
load.connectionbillmodify.connectionBillDetailGrid = Ext.create('Foss.load.connectionbillmodify.ConnectionBillDetailGrid');


//加载主panel
Ext.onReady(function() {
	
	//加载数据
	var connectionBillNo = load.connectionbillmodify.connectionBillNo ;
	load.connectionbillmodify.loadData(connectionBillNo);
	
	Ext.create('Ext.panel.Panel', {
		id : 'T_load-connectionbillmodifyindex_content',
		cls : "panelContentNToolbar",
		bodyCls : 'panelContent-body',
		layout : 'auto',//
		//load.connectionbillmodify.connectionBillDetailGrid
		items : [ load.connectionbillmodify.basicInfoForm,load.connectionbillmodify.connectionBillDetailGrid,{
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
				id : 'Foss_load_connectionBillModify_mainPage_saveButton_ID',
				text : '保存',
				handler : function() {
					var basicInfoForm = load.connectionbillmodify.basicInfoForm;
				
					//基本信息未填写完整
					if(!basicInfoForm.getForm().isValid()){
						Ext.ux.Toast.msg('提示', '基本信息未填写完整!', 'error', 2000);
						return;
					}
					//获取基本信息实体
					var baseEntity = basicInfoForm.getForm().getValues();
					
					
					//“是否PDA生成”设置为N
					baseEntity.isPda = 'N';
					//未修改任何信息
					var oldBasicInfo = load.connectionbillmodify.basicInfo;
					if(
							oldBasicInfo.notes == baseEntity.notes
							&&load.connectionbillmodify.deletedSerialNoMap.getCount()==0
							&&load.connectionbillmodify.deletedWaybillMap.getCount()==0
					){
						Ext.ux.Toast.msg('提示', '未做任何修改!', 'error', 2000);
						return;
					}
					if(load.connectionbillmodify.connectionBillDetailGrid.store.getCount() == 0){
						Ext.ux.Toast.msg('提示', '交接单内无任何运单!', 'error', 2000);
						return;
					}
					var deletedWaybill = load.connectionbillmodify.convertMap2Json(load.connectionbillmodify.deletedWaybillMap);
					var updatedWaybill = load.connectionbillmodify.convertMap2Json(load.connectionbillmodify.updatedWaybillMap);
					//获取当前所有的流水号list
					var tempAllSerialNoList = load.connectionbillmodify.allSerialNoMap.getValues();
					var allSerialNoList = new Array();
					for(var i=0;i<tempAllSerialNoList.length;i++){
						var serialNoList = tempAllSerialNoList[i].getValues();
						for(var j=0;j<serialNoList.length;j++){
							allSerialNoList.push(serialNoList[j].data);
						}
					}
					//获取所有的删除的流水号list
					var tempDeletedSerialNoList = load.connectionbillmodify.deletedSerialNoMap.getValues();
					var deletedSerialNoList = new Array();
					for(var i=0;i<tempDeletedSerialNoList.length;i++){
						var serialNoList = tempDeletedSerialNoList[i].getValues();
						for(var j=0;j<serialNoList.length;j++){
							deletedSerialNoList.push(serialNoList[j].data);
						}
					}

					
					var data = {'connectionBillVo' : {
						'updateConnectionBillDto' : {
							'deletedWaybillMap' : deletedWaybill,
							'updatedWaybillMap' : updatedWaybill,
							'deletedSerialNoList' : deletedSerialNoList,
							'connectionBillEntity' : baseEntity,
							'totalCount' : Ext.getCmp('Foss_load_connectionBillModify_MainPageTotalCount').getValue(),
							'totalPieces' : Ext.getCmp('Foss_load_connectionBillModify_MainPageTotalPieces').getValue() ,
							'totalCubage' : Ext.getCmp('Foss_load_connectionBillModify_MainPageTotalVolume').getValue() ,
							'totalWeight' : Ext.getCmp('Foss_load_connectionBillModify_MainPageTotalWeight').getValue()
						}
					}};
					//mask
					var myMask = new Ext.LoadMask(Ext.getCmp('T_load-connectionbillmodifyindex_content'), {
						msg:"数据提交中，请稍候..."
					});
					myMask.show();
					Ext.Ajax.request({
						url : load.realPath('updateConnectionBill.action'),
						jsonData : data,
						timeout : 300000,
						success : function(response){
							Ext.ux.Toast.msg('提示', '修改成功!', 'info', 3000);
							load.connectionbillmodify.isSaved = 'Y';
							//隐藏“查询交接运单”、“保存”按钮
							Ext.getCmp('Foss_load_connectionBillModify_mainPage_saveButton_ID').setVisible(false);
							//设置form所有字段均为只读
							var form = basicInfoForm.getForm();
							var formCmps = form.getFields().getRange(0,form.getFields().getCount());
							for(var i in formCmps){
								formCmps[i].setReadOnly(true);
							}
							myMask.hide();
							//隐藏运单列表操作列
							var superGrid = load.connectionbillmodify.connectionBillDetailGrid;
							superGrid.columns[1].setVisible(false);
							//如果运单被展开，隐藏流水号前的操作列
							var plugin = superGrid.getPlugin('Foss_connectionModify_mainPage_serialNoGrid_ID');
							var pluginGrid = plugin.getExpendRowBody();
							if(pluginGrid != null){
								pluginGrid.columns[0].setVisible(false);
							}
						},
						exception : function(response) {
		    				var result = Ext.decode(response.responseText);
		    				top.Ext.MessageBox.alert('提示','保存失败，' + result.message);
		    				myMask.hide();
		    			}
					});
				}
			}]
		}],
		renderTo : 'T_load-connectionbillmodifyindex-body'
	});
});