//定义交接单基本信息Model
Ext.define('Foss.load.arrivalconnectionbillshow.ArrivalConnectionBillBaseInfoModel', {
	extend : 'Ext.data.Model',
	//定义字段
	fields : [{
		name : 'connectionBillNo',
		type : 'string'
	}, {
		name : 'handOverType',
		type : 'string'
	}, {
		name : 'departTime',//出发时间
		type : 'date',
		convert: function(value) {
			if(!value) return '';
			var date = new Date(value);						
			var formatStr = 'Y-m-d H:i:s';
			return Ext.Date.format(date, formatStr);
		}
	},  {
		name : 'arriveDeptName',//到达部门（外场）
		type : 'string'
	}, {
		name : 'vehicleNo',//车牌号
		type : 'string'
	}, {
		name : 'driverName',//司机
		type : 'string'
	}, {
		name : 'driverTel',//司机电话
		type : 'string'
	},{
		name : 'waybillQtyTotal',//总票数
		type : 'number'
	}, {
		name : 'goodsQtyTotal',//总件数
		type : 'number'
	}, {
		name : 'volumeTotal',//总体积
		type : 'number'
	}, {
		name : 'weightTotal',//总重量
		type : 'number'
	}]});


//定义交接单明细Model
Ext.define('Foss.load.arrivalconectionbillshow.ArrivalConnectionBillDetailModel', {
	extend : 'Ext.data.Model',
	// 定义字段
	fields : [{
		name : 'waybillNo',//运单号
		type : 'string'
	}, {
		name : 'transProperty',//运输性质
		type : 'string'
	}, {
		name : 'pieces',//已配件数
		type : 'number'
	}, {
		name : 'weight',//已配重量
		type : 'number'
	}, {
		name : 'cubage',//已配体积
		type : 'number'
	}, {
		name : 'receiveOrgName',//收货部门
		type : 'string'
	},{
		name : 'arriveDept',//到达部门
		type : 'string'
	},{
		name : 'waybillPieces',//运单件数
		type : 'string'
	},]
});

//定义交接单明细store
Ext.define('Foss.load.arrivalconectionbillshow.ArrivalConnectionBillDetailStore', {
	extend : 'Ext.data.Store',
	// 绑定一个模型
	model : 'Foss.load.arrivalconectionbillshow.ArrivalConnectionBillDetailModel',
	autoLoad : true,
	// 定义一个代理对象
	proxy : {
		type : 'ajax',
		actionMethods:'POST',
		// 请求的url
		url : load.realPath('queryArrivalConnectionBillDetailByNo.action'),
		// 定义一个读取器
		reader : {
			// 以JSON的方式读取
			type : 'json',
			// 定义读取JSON数据的根对象
			root : 'arrivalConnectionBillVo.waybillStockList',
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
					'arrivalConnectionBillVo.arrivalConnectionBillNo' : load.arrivalconnectionbillshow.connectionBillNo
				}
			});	
		}
	}
});

//定义运单明细Model
Ext.define('Foss.load.arrivalconnectionbillshow.WaybillDetailModel', {
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

// 定义运单明细store
Ext.define('Foss.load.arrivalconnectionbillshow.WaybillDetailStore', {
	extend : 'Ext.data.Store',
	// 绑定一个模型
	model : 'Foss.load.arrivalconnectionbillshow.WaybillDetailModel',
	proxy : {
		type : 'ajax',
		actionMethods:'POST',
		// 请求的url
		url : load.realPath('queryArrivalConnectionBillDetailByNos.action'),
		// 定义一个读取器
		reader : {
			// 以JSON的方式读取
			type : 'json',
			// 定义读取JSON数据的根对象
			root : 'arrivalConnectionBillVo.serialNoList',
			successProperty: 'success'
		}
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
});

//定义运单明细grid
Ext.define('Foss.load.arrivalconnectionbillshow.WaybillDetailGrid', {
	extend : 'Ext.grid.Panel',
	columnLines: true,
	frame: true,
	baseCls : 'handOverBill_addNew_serialNoGap',
	autoScroll : true,
	width : 121,
	store : Ext.create('Foss.load.arrivalconnectionbillshow.WaybillDetailStore'),
	columns : [{
		dataIndex : 'serialNo',
		align : 'center',
		width : 120,
		xtype : 'ellipsiscolumn',
		text : '流水号'
	}],
	bindData : function(record){
		var waybillNo = record.get('waybillNo');
		var recordsMap = this.store.load({
			params : {
				'arrivalConnectionBillVo.arrivalConnectionBillNo' : load.arrivalconnectionbillshow.connectionBillNo,
				'arrivalConnectionBillVo.waybillNo' : waybillNo
			}
		});
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
});


//定义交接单明细列表
Ext.define('Foss.load.arrivalconectionbillshow.ConnectionBillDetailGrid', {
	extend : 'Ext.grid.Panel',
	frame : true,
	title : '交接单明细',
//	bodyCls : 'autoHeight',
	height : 500,
	cls : 'autoHeight',
	columnLines: true,
	autoScroll : true,
	collapsible : true,
	animCollapse : true,
	store : Ext.create('Foss.load.arrivalconectionbillshow.ArrivalConnectionBillDetailStore'),
	// 定义行展开
	plugins : [ {
		header : true,
		ptype : 'rowexpander',
		// 定义行展开模式（单行与多行），默认是多行展开(值true)
		rowsExpander : false,
		// 行体内容
		rowBodyElement : 'Foss.load.arrivalconnectionbillshow.WaybillDetailGrid'
	}],
	columns : [{
		dataIndex : 'waybillNo',
		align : 'center',
		width : 80,
		xtype : 'ellipsiscolumn',
		text : '运单号'
	}, {
		dataIndex : 'transProperty',
		align : 'center',
		width : 100,
		text : '运输性质'
	}, {
		dataIndex : 'pieces',
		align : 'center',
		width : 80,
		text : '已配件数'
	}, {
		dataIndex : 'weight',
		align : 'center',
		width : 80,
		text : '已配重量'
	}, {
		dataIndex : 'cubage',
		align : 'center',
		width : 80,
		text : '已配体积'
	}, {
		dataIndex : 'receiveOrgName',
		align : 'center',
		width : 120,
		text : '收货部门'
	},{
		dataIndex : 'arriveDept',
		align : 'center',
		width : 120,
		text : '到达部门'
	},{
		dataIndex : 'waybillPieces',
		align : 'center',
		width : 80,
		text : '运单件数'
	} ],
	dockedItems: [{
	    xtype: 'toolbar',
	    dock: 'bottom',
	    layout : 'column',
	    defaults: {
			xtype : 'textfield',
			readOnly : true,
			columnWidth : 0.8
		},
		items : [{
				fieldLabel : '总票数',
				xtype : 'textfield',
				readOnly : true,
				columnWidth : 1/5,
				value : 0,
				id : 'Foss_PageTotalCount'
			},{
				fieldLabel : '总件数',
				xtype : 'textfield',
				readOnly : true,
				columnWidth : 1/5,
				value : 0,
				id : 'Foss_PageTotalPieces'
			},{
				fieldLabel :'总重量(千克)',
				xtype : 'textfield',
				readOnly : true,
				columnWidth : 1/5,
				value : 0,
				id : 'Foss_PageTotalWeight'
			},{
				fieldLabel :'总体积(方)',
				xtype : 'textfield',
				readOnly : true,
				columnWidth : 1/5,
				value : 0,
				id : 'Foss_PageTotalVolume'
			}]
		}]
});



// 交接单基本信息form
Ext.define('Foss.load.arrivalconnectionbillshow.BasicInfoForm', {
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
		xtype : 'textfield',
		readOnly : true
	},
	layout : 'column',
	items : [{
		fieldLabel : '交接类型',
		name : 'handOverType',
		readOnly:true,
		xtype : 'combobox',
	    displayField: 'value',
	    valueField: 'key',
	    value : 'EXPRESS_DRIVER',
	    editable : false,
	    store : Ext.create('Ext.data.Store', {
	        fields: ['key', 'value'],
	        data : [
	            {"key":"EXPRESS_DRIVER", "value":"接驳交接单"}
	        ]
	    })
	}, {
		fieldLabel : '交接单编号',
		name : 'connectionBillNo'
	}, {
		fieldLabel : '出发时间',
		name : 'departTime'
	}, {
		fieldLabel : '到达外场',//到达外场
		name : 'arriveDeptName'
	}, {
		fieldLabel : '车牌号',
		name : 'vehicleNo'
	},{
		fieldLabel : '司机',
		name : 'driverName'
	},{
		fieldLabel : '司机电话',
		name : 'driverTel'
	},],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
});


// 定义运单列表
load.arrivalconnectionbillshow.connectionBillDetailGrid = Ext.create('Foss.load.arrivalconectionbillshow.ConnectionBillDetailGrid');

Ext.onReady(function() {
	//接受前一页面传入的交接单号
	var connectionBillNo = load.arrivalconnectionbillshow.connectionBillNo;
	//加载运单列表
	//load.handoverbillshow.handOverBillDetailGrid.store.load();
	
	//根据交接单号，从后台获取交接单基本信息
	Ext.Ajax.request({
		url : load.realPath('queryArrivalConnectionBillByNo.action'),
		params : {'arrivalConnectionBillVo.arrivalConnectionBillNo': connectionBillNo},
		success : function(response){
			var result = Ext.decode(response.responseText);
			//定义基本信息实体
			var basicInfo = result.arrivalConnectionBillVo.connectionBillEntity;
			//绑定model
		    var basicInfoRecord =  Ext.ModelManager.create(basicInfo, 'Foss.load.arrivalconnectionbillshow.ArrivalConnectionBillBaseInfoModel');
			//定义基本信息form
			var basicInfoForm = Ext.create('Foss.load.arrivalconnectionbillshow.BasicInfoForm');
			//给基本信息form加载值
			var form = basicInfoForm.getForm();
			form.loadRecord(basicInfoRecord);
			
			if(basicInfoRecord.get('handOverType')=='EXPRESS_DRIVER'){
				form.findField('handOverType').setValue('接驳交接单');
			}
			Ext.create('Ext.panel.Panel', {
				id : 'T_load-arrivalconnectionbillshowindex_content',
				cls : "panelContentNToolbar",
				bodyCls : 'panelContent-body',
				layout : 'auto',
				items : [ basicInfoForm,load.arrivalconnectionbillshow.connectionBillDetailGrid],
				renderTo : 'T_load-arrivalconnectionbillshowindex-body'
			});
			//从基本信息record中获取值，给统计信息赋值
			Ext.getCmp('Foss_PageTotalCount').setValue(basicInfoRecord.get('waybillQtyTotal'));
			Ext.getCmp('Foss_PageTotalPieces').setValue(basicInfoRecord.get('goodsQtyTotal'));
			Ext.getCmp('Foss_PageTotalWeight').setValue(basicInfoRecord.get('weightTotal'));
			Ext.getCmp('Foss_PageTotalVolume').setValue(basicInfoRecord.get('volumeTotal'));
		}
	});
});