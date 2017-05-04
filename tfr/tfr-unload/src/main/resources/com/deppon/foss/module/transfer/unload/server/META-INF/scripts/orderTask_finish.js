// 定义上方form
Ext.define('Foss.unload.ordertaskmodify.taskAddForm', {
			extend : 'Ext.form.Panel',
			layout : 'column',
			height : 80,
			defaults : {
				margin : '5 10 5 10',
				labelWidth : 85,
				columnWidth : 1 / 4
			},
			items : [{
				    xtype : 'textfield',
				    readOnly : true,
				    name : 'orderTaskNo',
				    fieldLabel : '点单任务编号'
				}, {
					fieldLabel : '车牌号',
					readOnly : true,
					name : 'vehicleNo',// ！车牌号
					xtype : 'textfield'
				}, {
					xtype : 'textfield',
					readOnly : true,
					name : 'departName',
					fieldLabel : '出发部门'
				}, {
					xtype : 'textfield',
					readOnly : true,
					name : 'arriveName',
					fieldLabel : '到达部门'
				}, {
					xtype : 'textfield',
					readOnly : true,
					name : 'totWaybillQty',
					fieldLabel : '总票数'
				}, {
					xtype : 'textfield',
					readOnly : true,
					name : 'totGoodsQty',
					fieldLabel : '总件数'
				}, {
					xtype : 'textfield',
					readOnly : true,
					name : 'totWeight',
					fieldLabel : '总重量'
				}, {
					xtype : 'textfield',
					readOnly : true,
					name : 'totVolume',
					fieldLabel : '总体积'
				}],
			constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				me.callParent([cfg]);
			}
		});
		

// 定义交接单明细列表之Model
Ext.define('Foss.unload.ordertaskmodify.taskInfoModel', {
			extend : 'Ext.data.Model',
			// 定义字段
			fields : [
					{
						name : 'id',
						type : 'string'
					},{
				        name : 'handoverNo',// 交接单号
				        type : 'string'
			        }, {
						name : 'waybillNo',// 运单号
						type : 'string'
					}, {
						name : 'transportType',// 运输性质
						type : 'string'
					}, {
						name : 'createBillQty',// 开单件数
						type : 'number'
					}, {
						name : 'alAssembleQty',// 已配件数
						type : 'string'
					}, {
						name : 'alAssembleWeight',// 已配重量
						type : 'string'
					}, {
						name : 'alAssembleVolume',// 已配体积
						type : 'string'
					}, {
						name : 'orderGoodsQty',// 点单件数
						type : 'number'
					}]
		});


//状态
Ext.define('Foss.Queryunloadtask.QueryForm.State.Store', {
	extend: 'Ext.data.Store',
	fields: [
		{name: 'code',  type: 'string'},
		{name: 'name',  type: 'string'}
	],
	proxy: {
		type: 'memory',
		reader: {
			type: 'json',
			root: 'items'
		}
	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

// 定义交接单明细列表之store
Ext.define('Foss.unload.ordertaskmodify.taskInfoStore', {
			extend : 'Ext.data.Store',
			// 绑定一个模型
			model : 'Foss.unload.ordertaskmodify.taskInfoModel',
			constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				me.callParent([cfg]);
			}
		});


//定义运单明细Model
Ext.define('Foss.unload.ordertaskmodify.WaybillDetailModel', {
	extend : 'Ext.data.Model',
	//定义字段
	fields: [{
		name : 'id',
		type : 'string'
	},{
		name : 'waybillNo',
		type : 'string'
	},{
		name : 'handoverNo',
		type : 'string'
	},{
		name : 'serialNo',
		type : 'string'
	},{
		name : 'orderReportType',
		type : 'string'
	}]
});


//定义运单明细store
Ext.define('Foss.unload.ordertaskmodify.WaybillDetailStore', {
	extend : 'Ext.data.Store',
	// 绑定一个模型
	model : 'Foss.unload.ordertaskmodify.WaybillDetailModel',
	proxy : {
		type : 'ajax',
		actionMethods:'POST',
		// 请求的url
		url : unload.realPath('queryOrderTaskSerialNoListByBillNo.action'),
		// 定义一个读取器
		reader : {
			// 以JSON的方式读取
			type : 'json',
			// 定义读取JSON数据的根对象
			root : 'orderTaskVo.serialNoDetailList',
			successProperty: 'success'
		}
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
});

// 定义下方列表之grid
Ext.define('Foss.unload.ordertaskmodify.taskInfoGrid', {
	extend : 'Ext.grid.Panel',
	autoScroll : true,
	height : 550,
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext
				.create('Foss.unload.ordertaskmodify.taskInfoStore'), me
				.callParent([cfg]);
	},
	// 定义行展开
	plugins : [ {
		header: true,
		ptype : 'rowexpander',
		rowsExpander : false,
		// 行体内容
		rowBodyElement : 'Foss.unload.ordertaskmodify.serialNoGrid'
	}],
	columns : [{
		dataIndex : 'handoverNo',
		align : 'center',
		flex : 1,
		xtype : 'ellipsiscolumn',
		text : unload
				.i18n('foss.unload.ordertaskaddnew.taskInfoGrid.handoverNoColumn')
	}, {
		dataIndex : 'waybillNo',
		align : 'center',
		flex : 1,
		xtype : 'ellipsiscolumn',
		text : unload
				.i18n('foss.unload.ordertaskaddnew.taskInfoGrid.waybillNoColumn')
	}, {
		dataIndex : 'transportType',
		align : 'center',
		flex : 1,
		text : unload
				.i18n('foss.unload.ordertaskaddnew.taskInfoGrid.transportTypeColumn')
	}, {
		dataIndex : 'createBillQty',
		align : 'center',
		flex : 1,
		text : unload
				.i18n('foss.unload.ordertaskaddnew.taskInfoGrid.createBillQty')
	}, {
		dataIndex : 'alAssembleQty',
		align : 'center',
		flex : 1,
		text : unload
				.i18n('foss.unload.ordertaskaddnew.taskInfoGrid.alAssembleQty')
	}, {
		dataIndex : 'alAssembleWeight',
		align : 'center',
		flex : 1,
		text : unload
				.i18n('foss.unload.ordertaskaddnew.taskInfoGrid.alAssembleWeight')
	}, {
		dataIndex : 'alAssembleVolume',
		align : 'center',
		flex : 1,
		text : unload
				.i18n('foss.unload.ordertaskaddnew.taskInfoGrid.alAssembleVolume')
	}, {
		dataIndex : 'orderGoodsQty',//点单件数
		align : 'center',
		flex : 1,
		text : '点单件数'
	}, {
		dataIndex : 'isunload',//判断是否加载流水号
		align : 'center',
		width : 120,
		xtype : 'ellipsiscolumn',
		hidden:true
	}]
	
});

//定义流水之grid
Ext.define('Foss.unload.ordertaskmodify.serialNoGrid', {
	extend : 'Ext.grid.Panel',
	enableColumnHide : false,// 配置该属性可取消grid自定义显示列功能
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext
				.create('Foss.unload.ordertaskmodify.WaybillDetailStore'), me
				.callParent([cfg]);
	},
		bindData : function(record){
			var id = record.get('id');
			var recordsMap = this.store.load({
				params : {
					'orderTaskVo.id' : id
				}
			});
	},
	columns : [{
		dataIndex : 'serialNo',
		align : 'center',
		width : 120,
		text : unload
				.i18n('foss.unload.ordertaskaddnew.taskInfoGrid.serialNoColumn') //流水号
	}, {
		dataIndex : 'orderReportType',
		align : 'center',
		width : 120,
		text : '点单差异类型' ,//点单差异类型
		renderer : function(value) {
			switch(value) {
				case 'LOSE':
					return unload.i18n('foss.unload.ordertaskmodify.ordeReportType.lose');
				case 'NORMAL':
					return unload.i18n('foss.unload.ordertaskmodify.ordeReportType.normal');
				case 'MORE':
					return unload.i18n('foss.unload.ordertaskmodify.ordeReportType.more');
				default: return value;
			}
		}
	}
	
	]
	
});


// 上方form
unload.taskAddForm = Ext
		.create('Foss.unload.ordertaskmodify.taskAddForm');
// 下方grid
unload.taskInfoGrid = Ext
		.create('Foss.unload.ordertaskmodify.taskInfoGrid');
//流水Grid	
unload.serialNoGrid = Ext
        .create('Foss.unload.ordertaskmodify.serialNoGrid');
     
        
		
// 定义上方控件Panel
Ext.define('Foss.unload.ordertaskmodify.taskInfoPanel', {
			extend : 'Ext.panel.Panel',
			title : unload
					.i18n('Foss.unload.ordertaskaddnew.taskInfoForm.title'),
			layout : 'auto',
			frame : true,
			collapsible : true,
			animCollapse : true,
			items : [
					unload.taskAddForm, 
					unload.taskInfoGrid
			]
		});
		

/*Maps*/
//存储修改前的单据
unload.oldBillMap = new Ext.util.HashMap();
//存储修改后的明细单据
unload.addedBillMap = new Ext.util.HashMap();
//存储修改后的流水信息
unload.addedSerialNoMap = new Ext.util.HashMap();
//存储添加的多货单据
unload.addedMoreBillMap = new Ext.util.HashMap();

Ext.onReady(function() {
	Ext.create('Ext.panel.Panel', {
		id : 'T_unload-orderTaskfinishindex_content',
		cls : "panelContentNToolbar",
		bodyCls : 'panelContent-body',
		layout : 'column',
		items : [{
			columnWidth : 1,
			items : [Ext
					.create('Foss.unload.ordertaskmodify.taskInfoPanel')]
		}],
		renderTo : 'T_unload-orderTaskfinishindex-body'
	});
	/**加载两部分数据，
	 * 1、基本信息，
	 * 2、单据list，
	 */
	//弹出数据加载，禁止操作
	var myMask = new Ext.LoadMask(Ext.getCmp('T_unload-orderTaskfinishindex_content'),{
		msg:"加载中，请稍后..."
			});
	myMask.show();
	Ext.Ajax.request({
		url : unload.realPath('loadOrderTaskInfo.action'),
		params : {'orderTaskVo.orderTaskNo' : unload.orderTaskNo},
		success : function(response){
			var orderTaskVo = Ext.decode(response.responseText).orderTaskVo;
			//获取基本信息
			var baseEntity = orderTaskVo.baseEntity;
			//获取列表
			var billDetailList = orderTaskVo.billDetailList;
			
			var form = unload.taskAddForm.getForm();
			//给点单任务编号，总重量，总体积，总件数，总票数赋值
			form.findField('orderTaskNo').setValue(baseEntity.orderTaskNo);
			form.findField('totWaybillQty').setValue(baseEntity.totWaybillQty);
			form.findField('totGoodsQty').setValue(baseEntity.totGoodsQty);
			form.findField('totWeight').setValue(baseEntity.totWeight);
			form.findField('totVolume').setValue(baseEntity.totVolume);
			form.findField('vehicleNo').setValue(baseEntity.vehicleNo);
			form.findField('departName').setValue(baseEntity.departName);
			form.findField('arriveName').setValue(baseEntity.arriveName);
			//单据列表赋值
			unload.taskInfoGrid.store.loadData(billDetailList);
			//将列表中的数据置于Map中
			for(var i in orderTaskVo.billDetailList){
				unload.oldBillMap.add(billDetailList[i].handoverNo+"@"+billDetailList[i].waybillNo,billDetailList[i]);
			}
			myMask.hide();
		}
	});
});