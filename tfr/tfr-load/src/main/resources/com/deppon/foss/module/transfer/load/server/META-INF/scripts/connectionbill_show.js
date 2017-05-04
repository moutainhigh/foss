//定义交接单基本信息Model
Ext.define('Foss.load.connectionbillshow.ConnectionBillBaseInfoModel', {
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
		name : 'arriveDeptName',
		type : 'string'
	}, {
		name : 'vehicleNo',
		type : 'string'
	}, {
		name : 'driverName',
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


//定义交接单明细Model
Ext.define('Foss.load.conectionbillshow.ConnectionBillDetailModel', {
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
	},  {
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
Ext.define('Foss.load.conectionbillshow.ConnectionBillDetailStore', {
	extend : 'Ext.data.Store',
	// 绑定一个模型
	model : 'Foss.load.conectionbillshow.ConnectionBillDetailModel',
	autoLoad : true,
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
					'connectionBillVo.connectionBillNo' : load.connectionbillshow.connectionBillNo
				}
			});	
		}
	}
});

//定义运单明细Model
Ext.define('Foss.load.connectionbillshow.WaybillDetailModel', {
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
Ext.define('Foss.load.connectionbillshow.WaybillDetailStore', {
	extend : 'Ext.data.Store',
	// 绑定一个模型
	model : 'Foss.load.connectionbillshow.WaybillDetailModel',
	proxy : {
		type : 'ajax',
		actionMethods:'POST',
		// 请求的url
		url : load.realPath('queryConnectionWaybillDetailByNos.action'),
		// 定义一个读取器
		reader : {
			// 以JSON的方式读取
			type : 'json',
			// 定义读取JSON数据的根对象
			root : 'connectionBillVo.serialNoList',
			successProperty: 'success'
		}
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
});

//定义运单明细grid
Ext.define('Foss.load.connectionbillshow.WaybillDetailGrid', {
	extend : 'Ext.grid.Panel',
	columnLines: true,
	frame: true,
	baseCls : 'handOverBill_addNew_serialNoGap',
	autoScroll : true,
	width : 121,
	store : Ext.create('Foss.load.connectionbillshow.WaybillDetailStore'),
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
				'connectionBillVo.connectionBillNo' : load.connectionbillshow.connectionBillNo,
				'connectionBillVo.waybillNo' : waybillNo
			}
		});
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
});


//定义交接单明细列表
Ext.define('Foss.load.conectionbillshow.ConnectionBillDetailGrid', {
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
	store : Ext.create('Foss.load.conectionbillshow.ConnectionBillDetailStore'),
	// 定义行展开
	plugins : [ {
		header : true,
		ptype : 'rowexpander',
		// 定义行展开模式（单行与多行），默认是多行展开(值true)
		rowsExpander : false,
		// 行体内容
		rowBodyElement : 'Foss.load.connectionbillshow.WaybillDetailGrid'
	}],
	tbar : [{
		xtype : 'button',
		text : '导出运单',
		handler : function(){
			

			
			if(!Ext.fly('downloadAttachFileForm')){
				    var frm = document.createElement('form');
				    frm.id = 'downloadAttachFileForm';
				    frm.style.display = 'none';
				    document.body.appendChild(frm);
			}
			//获取查询参数
			var queryParams = load.connectionbillquery.ConnectionBillQueryForm.getForm().getValues();
			Ext.Ajax.request({
				url : load.realPath('exportConnectionDetailExcel.action'),
				form: Ext.fly('downloadAttachFileForm'),
				method : 'POST',
				params : {
					'connectionBillVo.connectionBillNo' : load.connectionbillshow.connectionBillNo
				},
				isUpload: true,
				success:function(response){
					
				},
				exception : function(response) {
					top.Ext.MessageBox.alert('导出失败',result.message);
				}
			});
		
			
		 
			
		}
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
		dataIndex : 'note',
		align : 'center',
		xtype : 'ellipsiscolumn',
		width : 120,
		text : '备注'
	}, {
		dataIndex : 'goodsName',
		align : 'center',
		width : 80,
		text : '货物名称'
	}, {
		dataIndex : 'packing',
		align : 'center',
		width : 60,
		text : '包装'
	}, {
		dataIndex : 'waybillNote',
		align : 'center',
		width : 120,
		text : '运单备注'
	},{
		dataIndex : 'isPreciousGoods',
		align : 'center',
		width : 100,
		text : '是否为贵重物品',
		renderer : function(value){
			if(value){
				return '是';
			}
			return '否';
		}
	},{
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
		dataIndex : 'consignee',
		align : 'center',
		width : 80,
		text : '收货人'
	},{
		dataIndex : 'destination',
		align : 'center',
		width : 80,
		text : '目的站'
	},{
		dataIndex : 'waybillPieces',
		align : 'center',
		width : 80,
		text : '运单件数'
	},{
		dataIndex : 'insuranceValue',
		align : 'center',
		width : 80,
		text : '保险价值'
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
				id : 'Foss_load_connectionBillShow_DetailPageTotalCount'
			},{
				fieldLabel : '总件数',
				xtype : 'textfield',
				readOnly : true,
				columnWidth : 1/5,
				value : 0,
				id : 'Foss_load_connectionBillShow_DetailPageTotalPieces'
			},{
				fieldLabel :'总重量(千克)',
				xtype : 'textfield',
				readOnly : true,
				columnWidth : 1/5,
				value : 0,
				id : 'Foss_load_connectionBillShow_DetailPageTotalWeight'
			},{
				fieldLabel :'总体积(方)',
				xtype : 'textfield',
				readOnly : true,
				columnWidth : 1/5,
				value : 0,
				id : 'Foss_load_connectionBillShow_DetailPageTotalVolume'
			}]
		}]
});



// 交接单基本信息form
Ext.define('Foss.load.connectionbillshow.BasicInfoForm', {
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
		name : 'handOverType'
	}, {
		fieldLabel : '交接单编号',
		name : 'connectionBillNo'
	}, {
		fieldLabel : '交接时间',
		name : 'handOverTime'
	}, {
		fieldLabel : '出发部门',
		name : 'departDeptName'
	}, {
		fieldLabel : '到达接驳点',
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
	}, {
		fieldLabel : '装车完成时间',
		name : 'loadEndTime'
	}, {
		fieldLabel : '制单人',
		name : 'createUserName'
	}, {
		fieldLabel : '修改人',
		name : 'modifyUserName'
	}, {
		fieldLabel : '备注',
		name : 'notes',
		columnWidth : .5
	}, {
		boxLabel : '是否PDA生成',
		name : 'isPda',
		xtype : 'checkbox',
		columnWidth : .14
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
});


// 定义运单列表
load.connectionbillshow.connectionBillDetailGrid = Ext.create('Foss.load.conectionbillshow.ConnectionBillDetailGrid');

Ext.onReady(function() {
	//接受前一页面传入的交接单号
	var connectionBillNo = load.connectionbillshow.connectionBillNo;
	//加载运单列表
	//load.handoverbillshow.handOverBillDetailGrid.store.load();
	
	//根据交接单号，从后台获取交接单基本信息
	Ext.Ajax.request({
		url : load.realPath('queryConnectionBillByNo.action'),
		params : {'connectionBillVo.connectionBillNo': connectionBillNo},
		success : function(response){
			var result = Ext.decode(response.responseText);
			//定义基本信息实体
			var basicInfo = result.connectionBillVo.connectionBillEntity;
			//绑定model
			var basicInfoRecord =  Ext.ModelManager.create(basicInfo, 'Foss.load.connectionbillshow.ConnectionBillBaseInfoModel');
//			//定义基本信息form
			var basicInfoForm = Ext.create('Foss.load.connectionbillshow.BasicInfoForm');
//			//该交接单是否由PDA生成
			load.connectionbillshow.isPda = basicInfoRecord.get('isPda');
//			//给基本信息form加载值
			var form = basicInfoForm.getForm();
			form.loadRecord(basicInfoRecord);


			if(load.connectionbillshow.isPda == 'Y'){
				form.findField('isPda').setValue(true);
			}
			
			if(basicInfoRecord.get('handOverType')=='EXPRESS_CONNECTION_HANDOVER'){
				form.findField('handOverType').setValue('接驳交接单');
			}
			Ext.create('Ext.panel.Panel', {
				id : 'T_load-connectionbillshowindex_content',
				cls : "panelContentNToolbar",
				bodyCls : 'panelContent-body',
				layout : 'auto',//
				items : [ basicInfoForm,load.connectionbillshow.connectionBillDetailGrid],
				renderTo : 'T_load-connectionbillshowindex-body'
			});
			//从基本信息record中获取值，给统计信息赋值
			Ext.getCmp('Foss_load_connectionBillShow_DetailPageTotalCount').setValue(basicInfoRecord.get('waybillQtyTotal'));
			Ext.getCmp('Foss_load_connectionBillShow_DetailPageTotalPieces').setValue(basicInfoRecord.get('goodsQtyTotal'));
			Ext.getCmp('Foss_load_connectionBillShow_DetailPageTotalWeight').setValue(basicInfoRecord.get('weightTotal'));
			Ext.getCmp('Foss_load_connectionBillShow_DetailPageTotalVolume').setValue(basicInfoRecord.get('volumeTotal'));
		}
	});
});