//定义交接单基本信息Model
Ext.define('Foss.load.handoverbillsaleshow.HandOverBillBaseInfoModel', {
	extend : 'Ext.data.Model',
	//定义字段
	fields : [{
		name : 'handOverBillNo',
		type : 'string'
	}, {
		name : 'handOverType',
		type : 'string',
		convert : function(value){
			if(value == 'SALES_DEPARTMENT_HANDOVER'){//营业部交接单
				return '营业部交接单';
			}
		}
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
		name : 'driverName',
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
		type : 'string',
		convert : function(value){
			if(value == 'A_TYPE'){
				return 'A类';
			}else if(value == 'B_TYPE'){
				return 'B类';
			}else{
				return '全部';
			}
		}
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
		name : 'tranGoodsType',
		type : 'string',
		convert : function(value){
			if(value == 'TRANSITGOODS'){
				return '转货';
			}else if(value == 'CARRYGOODS'){
				return '带货';
			}
		}
	}]
});

//定义交接单明细Model
Ext.define('Foss.load.handoverbillsaleshow.HandOverBillDetailModel', {
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
		type : 'boolean'
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
		name : 'goodsType',
		type : 'string'
	}]
});

// 定义交接单明细store
Ext.define('Foss.load.handoverbillsaleshow.HandOverBillDetailStore', {
	extend : 'Ext.data.Store',
	// 绑定一个模型
	model : 'Foss.load.handoverbillsaleshow.HandOverBillDetailModel',
	autoLoad : true,
	// 定义一个代理对象
	proxy : {
		type : 'ajax',
		actionMethods:'POST',
		// 请求的url
		url : load.realPath('queryHandOverBillSaleDetailByNo.action'),
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
					'handOverBillVo.handOverBillNo' : load.handoverbillsaleshow.handOverBillNo,
					'handOverBillVo.handOverType':load.handoverbillsaleshow.handOverType
				}
			});	
		},
		'datachanged' : function(store){
			load.handoverbillsaleshow.updateMainPageStaInfo(store);
		}
	}
});
//方法用于各处调用，更新主页面grid下统计条数据
load.handoverbillsaleshow.updateMainPageStaInfo = function (store){
	
	//遍历主页store，获得总件数、总体积、总重量、非快递货总体积、快递货总重量、未转换总体积
	var nuConvertTotalVolume=0;
	store.each(function(record){
		
		nuConvertTotalVolume += record.get('cubage');
		
	});
	
	if(nuConvertTotalVolume != 0){
		nuConvertTotalVolume = nuConvertTotalVolume.toFixed(2);
	}
	load.handoverbillsaleshow.nuConvertTotalVolume=nuConvertTotalVolume;
	//更新主页未转换总体积
	Ext.getCmp('Foss_load_handOverBillSaleShow_DetailPageUnconvertTotalVolume').setValue(load.handoverbillsaleshow.nuConvertTotalVolume);
};

// 定义运单明细Model
Ext.define('Foss.load.handoverbillsaleshow.WaybillDetailModel', {
	extend : 'Ext.data.Model',
	//定义字段
	fields: [{
		name : 'waybillNo',
		type : 'string'
	},{
		name : 'serialNo',
		type : 'string'
	},{
		name : 'loadExceptionType',
		type : 'string'
	}]
});

// 定义运单明细store
Ext.define('Foss.load.handoverbillsaleshow.WaybillDetailStore', {
	extend : 'Ext.data.Store',
	// 绑定一个模型
	model : 'Foss.load.handoverbillsaleshow.WaybillDetailModel',
	proxy : {
		type : 'ajax',
		actionMethods:'POST',
		// 请求的url
		url : load.realPath('queryWaybillDetailByNos.action'),
		// 定义一个读取器
		reader : {
			// 以JSON的方式读取
			type : 'json',
			// 定义读取JSON数据的根对象
			root : 'handOverBillVo.pdaSerialNoList',
			successProperty: 'success'
		}
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
		//若本交接单不是PDA生成，则更改加载流水号的url和reader的root
		if(load.handoverbillsaleshow.isCreatedByPDA == 'N'||load.handoverbillsaleshow.handOverType=='PACKAGE_HANDOVER'){
			this.proxy.url = load.realPath('querySerialNoListByHandOverBillNo.action');
			this.proxy.reader.root = 'handOverBillVo.serialNoList';
		}
	}
});

// 定义运单明细grid
Ext.define('Foss.load.handoverbillsaleshow.WaybillDetailGrid', {
	extend : 'Ext.grid.Panel',
	columnLines: true,
	frame: true,
	baseCls : 'handOverBill_addNew_serialNoGap',
	autoScroll : true,
	width : 121,
	store : Ext.create('Foss.load.handoverbillsaleshow.WaybillDetailStore'),
	columns : [{
		dataIndex : 'serialNo',
		align : 'center',
		width : 120,
		xtype : 'ellipsiscolumn',
		text : load.handoverbillsaleshow.i18n('foss.load.handoverbillshow.serialNoGrid.serialNoColumn')/*'流水号'*/
	}],
	bindData : function(record){
		var waybillNo = record.get('waybillNo');
		var recordsMap = this.store.load({
			params : {
				'handOverBillVo.handOverBillNo' : load.handoverbillsaleshow.handOverBillNo,
				'handOverBillVo.handOverType' : load.handoverbillsaleshow.handOverType,
				'handOverBillVo.waybillNo' : waybillNo
			}
		});
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
		if(load.handoverbillsaleshow.isCreatedByPDA == 'Y'){
			//若该交接单为PDA生成，则二级表格增加一列，同时表格加宽
			this.width = 271,
			this.columns.push(Ext.create('Ext.grid.column.Column',{
				dataIndex : 'loadExceptionType',
				align : 'center',
				width : 150,
				xtype : 'ellipsiscolumn',
				text : load.handoverbillsaleshow.i18n('foss.load.handoverbillshow.serialNoGrid.loadExceptionTypeColumn')/*'装车异常类型'*/,
				renderer : function(value){
					return FossDataDictionary.rendererSubmitToDisplay(value,'LOAD_EXCEPTION_TYPE');
				}
			}));
		}
	}
});

// 定义交接单明细列表
Ext.define('Foss.load.handoverbillsaleshow.HandOverBillDetailGrid', {
	extend : 'Ext.grid.Panel',
	frame : true,
	title : load.handoverbillsaleshow.i18n('foss.load.handoverbillshow.waybillGrid.expertWaybillButtonText')/*'交接单明细'*/,
//	bodyCls : 'autoHeight',
	height : 500,
	cls : 'autoHeight',
	columnLines: true,
	autoScroll : true,
	collapsible : true,
	animCollapse : true,
	store : Ext.create('Foss.load.handoverbillsaleshow.HandOverBillDetailStore'),
	// 定义行展开
	plugins : [ {
		header : true,
		ptype : 'rowexpander',
		// 定义行展开模式（单行与多行），默认是多行展开(值true)
		rowsExpander : false,
		// 行体内容
		rowBodyElement : 'Foss.load.handoverbillsaleshow.WaybillDetailGrid'
	}],
	tbar : [{
		xtype : 'button',
		text : load.handoverbillsaleshow.i18n('foss.load.handoverbillshow.waybillGrid.expertWaybillButtonText'),//'导出运单'
		handler : function(){
			if(!Ext.fly('downloadAttachFileForm')){
				    var frm = document.createElement('form');
				    frm.id = 'downloadAttachFileForm';
				    frm.style.display = 'none';
				    document.body.appendChild(frm);
			}			
			Ext.Ajax.request({
				url : load.realPath('exportWayBillExcel.action'),
				form: Ext.fly('downloadAttachFileForm'),
				method : 'POST',
				params : {
					'handOverBillVo.handOverBillNo' : load.handoverbillsaleshow.handOverBillNo,
					'handOverBillVo.handOverType':load.handoverbillsaleshow.handOverType
					},
				isUpload: true,
				success:function(response){
					
				},
				exception : function(response) {
					top.Ext.MessageBox.alert(load.handoverbillsaleshow.i18n('foss.load.handoverbillshow.waybillGrid.expertFailureAlertInfo')/*'导出失败'*/,result.message);
				}
			});
		}
	}],
	columns : [{
		dataIndex : 'waybillNo',
		align : 'center',
		width : 80,
		xtype : 'ellipsiscolumn',
		text : load.handoverbillsaleshow.i18n('foss.load.handoverbillshow.waybillGrid.waybillColumn')/*'运单号'*/
	}, {
		dataIndex : 'transProperty',
		align : 'center',
		width : 100,
		text : load.handoverbillsaleshow.i18n('foss.load.handoverbillshow.waybillGrid.transPropertyColumn')/*'运输性质'*/
	}, {
		dataIndex : 'pieces',
		align : 'center',
		width : 80,
		text : load.handoverbillsaleshow.i18n('foss.load.handoverbillshow.waybillGrid.piecesColumn')/*'已配件数'*/
	}, {
		dataIndex : 'weight',
		align : 'center',
		width : 80,
		text : load.handoverbillsaleshow.i18n('foss.load.handoverbillshow.waybillGrid.weightColumn')/*'已配重量'*/
	}, {
		dataIndex : 'weightAc',
		align : 'center',
		width : 80,
		text : load.handoverbillsaleshow.i18n('foss.load.handoverbillshow.waybillGrid.weightAcColumn')/*'实际重量'*/
	}, {
		dataIndex : 'cubage',
		align : 'center',
		width : 80,
		text : load.handoverbillsaleshow.i18n('foss.load.handoverbillshow.waybillGrid.volumeColumn')/*'已配体积'*/
	}, {
		dataIndex : 'cubageAc',
		align : 'center',
		width : 80,
		text : load.handoverbillsaleshow.i18n('foss.load.handoverbillshow.waybillGrid.volumeAcColumn')/*'实际体积'*/
	}, {
		dataIndex : 'note',
		align : 'center',
		xtype : 'ellipsiscolumn',
		width : 120,
		text : load.handoverbillsaleshow.i18n('foss.load.handoverbillshow.waybillGrid.noteColumn')/*'备注'*/
	}, {
		dataIndex : 'goodsName',
		align : 'center',
		width : 80,
		text : load.handoverbillsaleshow.i18n('foss.load.handoverbillshow.waybillGrid.goodsNameColumn')/*'货物名称'*/
	}, {
		dataIndex : 'packing',
		align : 'center',
		width : 60,
		text : load.handoverbillsaleshow.i18n('foss.load.handoverbillshow.waybillGrid.packingColumn')/*'包装'*/
	}, {
		dataIndex : 'waybillNote',
		align : 'center',
		width : 120,
		text : load.handoverbillsaleshow.i18n('foss.load.handoverbillshow.waybillGrid.waybillNoteColumn')/*'运单备注'*/
	},{
		dataIndex : 'isPreciousGoods',
		align : 'center',
		width : 100,
		text : load.handoverbillsaleshow.i18n('foss.load.handoverbillshow.waybillGrid.isPreciousGoodsColumn')/*'是否为贵重物品'*/,
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
		text : load.handoverbillsaleshow.i18n('foss.load.handoverbillshow.waybillGrid.receiveOrgColumn')/*'收货部门'*/
	},{
		dataIndex : 'arriveDept',
		align : 'center',
		width : 120,
		text : load.handoverbillsaleshow.i18n('foss.load.handoverbillshow.basicInfoForm.arriveDeptLabel')/*'到达部门'*/
	},{
		dataIndex : 'consignee',
		align : 'center',
		width : 80,
		text : load.handoverbillsaleshow.i18n('foss.load.handoverbillshow.waybillGrid.consigneeColumn')/*'收货人'*/
	},{
		dataIndex : 'destination',
		align : 'center',
		width : 80,
		text : load.handoverbillsaleshow.i18n('foss.load.handoverbillshow.waybillGrid.destinationColumn')/*'目的站'*/
	},{
		dataIndex : 'waybillPieces',
		align : 'center',
		width : 80,
		text : load.handoverbillsaleshow.i18n('foss.load.handoverbillshow.waybillGrid.waybillPiecesColumn')/*'运单件数'*/
	},{
		dataIndex : 'insuranceValue',
		align : 'center',
		width : 80,
		text : load.handoverbillsaleshow.i18n('foss.load.handoverbillshow.waybillGrid.insuranceValueColumn')/*'保险价值'*/
	},{
		dataIndex : 'goodsType',
		align : 'center',
		width : 80,
		text : load.handoverbillsaleshow.i18n('foss.load.handoverbillshow.waybillGrid.goodsTypeColumn')/*'货物类型(A,B货)'*/,
		renderer : function(value){
			switch(value) {
				case 'A':
					return 'A类';
				case 'B':
					return 'B类';
				default: return '全部';
			}
		}
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
				fieldLabel : load.handoverbillsaleshow.i18n('foss.load.handoverbillshow.waybillGrid.totalWaybillLabel')/*'总票数'*/,
				xtype : 'textfield',
				readOnly : true,
				columnWidth : 1/5,
				value : 0,
				id : 'Foss_load_handOverBillSaleShow_DetailPageTotalCount'
			},{
				fieldLabel : load.handoverbillsaleshow.i18n('foss.load.handoverbillshow.waybillGrid.totalPiecesLabel')/*'总件数'*/,
				xtype : 'textfield',
				readOnly : true,
				columnWidth : 1/5,
				value : 0,
				id : 'Foss_load_handOverBillSaleShow_DetailPageTotalPieces'
			},{
				fieldLabel : load.handoverbillsaleshow.i18n('foss.load.handoverbillshow.waybillGrid.totalWeightLabel')/*'总重量(千克)'*/,
				xtype : 'textfield',
				readOnly : true,
				columnWidth : 1/5,
				value : 0,
				id : 'Foss_load_handOverBillSaleShow_DetailPageTotalWeight'
			},{
				fieldLabel : load.handoverbillsaleshow.i18n('foss.load.handoverbillshow.waybillGrid.totalVolumeLabel')/*'总体积(方)'*/,
				xtype : 'textfield',
				readOnly : true,
				columnWidth : 1/5,
				value : 0,
				id : 'Foss_load_handOverBillSaleShow_DetailPageTotalVolume'
			},{
				fieldLabel : '未转换总体积'/*'未转换总体积(方)'*/,
				xtype : 'textfield',
				readOnly : true,
				columnWidth : 1/5,
				value : 0,
				id : 'Foss_load_handOverBillSaleShow_DetailPageUnconvertTotalVolume'
			}]
		}]
});

// 交接单基本信息form
Ext.define('Foss.load.handoverbillsaleshow.BasicInfoForm', {
	extend : 'Ext.form.Panel',
	title : load.handoverbillsaleshow.i18n('foss.load.handoverbillshow.basicInfoForm.title')/*'交接单基本信息'*/,
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
		fieldLabel : load.handoverbillsaleshow.i18n('foss.load.handoverbillshow.basicInfoForm.handOverTypeLabel')/*'交接类型'*/,
		name : 'handOverType'
	}, {
		fieldLabel : load.handoverbillsaleshow.i18n('foss.load.handoverbillshow.basicInfoForm.handOverBillNoLabel')/*'交接单编号'*/,
		name : 'handOverBillNo'
	}, {
		fieldLabel : load.handoverbillsaleshow.i18n('foss.load.handoverbillshow.basicInfoForm.handOverTimeLabel')/*'交接时间'*/,
		name : 'handOverTime'
	}, {
		fieldLabel : load.handoverbillsaleshow.i18n('foss.load.handoverbillshow.basicInfoForm.departDeptLabel')/*'出发部门'*/,
		name : 'departDept'
	}, {
		fieldLabel : load.handoverbillsaleshow.i18n('foss.load.handoverbillshow.basicInfoForm.arriveDeptLabel')/*'到达部门'*/,
		name : 'arriveDept'
	}, {
		fieldLabel : load.handoverbillsaleshow.i18n('foss.load.handoverbillshow.basicInfoForm.vehicleNoLabel')/*'车牌号'*/,
		name : 'vehicleNo'
	},{
		fieldLabel : load.handoverbillsaleshow.i18n('foss.load.handoverbillshow.basicInfoForm.driverLabel')/*'司机'*/,
		name : 'driverName'
	},{
		fieldLabel : load.handoverbillsaleshow.i18n('foss.load.handoverbillshow.basicInfoForm.driverPhoneLabel')/*'司机电话'*/,
		name : 'driverTel'
	}, {
		fieldLabel : load.handoverbillsaleshow.i18n('foss.load.handoverbillshow.basicInfoForm.loadEndTimeLabel')/*'装车完成时间'*/,
		name : 'loadEndTime'
	}, {
		fieldLabel : load.handoverbillsaleshow.i18n('foss.load.handoverbillshow.basicInfoForm.goodsTypeLabel')/*'货物类型'*/,
		name : 'goodsType'
	}, {
		fieldLabel : load.handoverbillsaleshow.i18n('foss.load.handoverbillshow.basicInfoForm.creatorLabel')/*'制单人'*/,
		name : 'createUserName'
	}, {
		fieldLabel : load.handoverbillsaleshow.i18n('foss.load.handoverbillshow.basicInfoForm.modifyManLabel')/*'修改人'*/,
		name : 'modifyUserName'
	}, {
		fieldLabel : load.handoverbillsaleshow.i18n('foss.load.handoverbillshow.waybillGrid.noteColumn')/*'备注'*/,
		name : 'note',
		columnWidth : .5
	}, {
		boxLabel : load.handoverbillsaleshow.i18n('foss.load.handoverbillshow.basicInfoForm.isPreHandOverBillLabel')/*'是否预配交接单'*/,
		name : 'isPreHandOverBill',
		xtype : 'checkbox',
		columnWidth : .14
	}, {
		boxLabel : load.handoverbillsaleshow.i18n('foss.load.handoverbillshow.basicInfoForm.isMadeByPDALabel')/*'是否PDA生成'*/,
		name : 'isCreatedByPDA',
		xtype : 'checkbox',
		columnWidth : .14
	}, {
		fieldLabel : load.handoverbillsaleshow.i18n('foss.load.handoverbillshow.basicInfoForm.tranGoodsType')/*'转货类型'*/,
		name : 'tranGoodsType',
		columnWidth : .16
	}, {
		boxLabel : load.handoverbillsaleshow.i18n('foss.load.handoverbillshow.basicInfoForm.isAgencyVisitLabel')/*'是否代理上门接货'*/,
		name : 'isAgencyVisit',
		xtype : 'checkbox'
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
});

//定义交接单日志Model
Ext.define('Foss.load.handoverbillsaleshow.HandOverBillOptLogModel', {
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
Ext.define('Foss.load.handoverbillsaleshow.HandOverBillOptLogStore', {
	extend : 'Ext.data.Store',
	// 绑定一个模型
	model : 'Foss.load.handoverbillsaleshow.HandOverBillOptLogModel',
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
					'handOverBillVo.handOverBillNo' : load.handoverbillsaleshow.handOverBillNo
				}
			});	
		}
	}
});

//定义交接单修改日志列表
Ext.define('Foss.load.handoverbillsaleshow.HandOverBillOptLogGrid', {
	extend : 'Ext.grid.Panel',
	frame : true,
	title : load.handoverbillsaleshow.i18n('foss.load.handoverbillshow.optLogGrid.title')/*'修改日志'*/,
//	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	columnLines: true,
	autoScroll : true,
	collapsible : true,
	collapsed : true,//页面初始化时不展开该grid，不加载数据
	animCollapse : true,
	store : Ext.create('Foss.load.handoverbillsaleshow.HandOverBillOptLogStore'),
	columns : [{
		dataIndex : 'operatorName',
		align : 'center',
		width : 70,
		xtype : 'ellipsiscolumn',
		text : load.handoverbillsaleshow.i18n('foss.load.handoverbillshow.optLogGrid.operatorNameColumn')/*'操作人'*/
	}, {
		dataIndex : 'optTime',
		align : 'center',
		width : 135,
		text : load.handoverbillsaleshow.i18n('foss.load.handoverbillshow.optLogGrid.optTimeColumn')/*'操作时间'*/,
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
		text : load.handoverbillsaleshow.i18n('foss.load.handoverbillshow.optLogGrid.optTypeColumn')/*'操作类别'*/
	}, {
		dataIndex : 'optContent',
		flex : 1,
		text : load.handoverbillsaleshow.i18n('foss.load.handoverbillshow.optLogGrid.optContentColumn')/*'操作内容'*/,
		xtype : 'linebreakcolumn'
	}],
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.bbar = Ext.create('Deppon.StandardPaging',{
			store:me.store
	});
	load.handoverbillsaleshow.pagingBar = me.bbar;
	me.callParent([cfg]);
},
	listeners : {
		'expand' : function(panel, eOpts){
			this.store.load();	//展开表格后加载日志数据
		}
	}
});

// 定义运单列表
load.handoverbillsaleshow.handOverBillDetailGrid = Ext.create('Foss.load.handoverbillsaleshow.HandOverBillDetailGrid');

Ext.onReady(function() {
	//接受前一页面传入的交接单号
	var handOverBillNo = load.handoverbillsaleshow.handOverBillNo;
	//交单类型
	var handOverType  = load.handoverbillsaleshow.handOverType;
	//加载运单列表
	//load.handoverbillsaleshow.handOverBillDetailGrid.store.load();
	
	//根据交接单号，从后台获取交接单基本信息
	Ext.Ajax.request({
		url : load.realPath('queryHandOverBillSaleByNo.action'),
		params : {'handOverBillVo.handOverBillNo': handOverBillNo,
		          'handOverBillVo.handOverType': handOverType
		   },
		success : function(response){
			var result = Ext.decode(response.responseText);
			//定义基本信息实体
			var basicInfo = result.handOverBillVo.baseEntity;
			//绑定model
			var basicInfoRecord =  Ext.ModelManager.create(basicInfo, 'Foss.load.handoverbillsaleshow.HandOverBillBaseInfoModel');
			//定义基本信息form
			var basicInfoForm = Ext.create('Foss.load.handoverbillsaleshow.BasicInfoForm');
			//给基本信息form加载值
			var form = basicInfoForm.getForm();
			form.loadRecord(basicInfoRecord);
			//根据交接类型来显示或者隐藏到达部门、外发代理
			if(basicInfoRecord.get('handOverType') == '外发交接单'){
				form.findField('arriveDept').fieldLabel = load.handoverbillsaleshow.i18n('foss.load.handoverbillshow.basicInfoForm.agencyLabel')/*'外发代理'*/;
				if(basicInfoRecord.get('isAgencyVisit') == 'Y'){
					form.findField('isAgencyVisit').setValue(true);
				}
			}else if(basicInfoRecord.get('handOverType') == '快递代理交接单'){
				form.findField('arriveDept').fieldLabel = '快递代理公司';
				if(basicInfoRecord.get('isAgencyVisit') == 'Y'){
					form.findField('isAgencyVisit').setValue(true);
				}
			}else if(basicInfoRecord.get('handOverType') == '营业部交接单'){
				form.findField('driverName').setVisible(false);
				form.findField('driverTel').setVisible(false);
				form.findField('goodsType').setVisible(false);
				form.findField('isPreHandOverBill').setVisible(false);
				form.findField('isCreatedByPDA').setVisible(false);
				form.findField('tranGoodsType').setVisible(false);
				form.findField('isAgencyVisit').setVisible(false);
			}else{
				form.findField('arriveDept').fieldLabel = load.handoverbillsaleshow.i18n('foss.load.handoverbillshow.basicInfoForm.arriveDeptLabel')/*'到达部门'*/;
				//隐藏“是否代理上门接货”checkbox
				form.findField('isAgencyVisit').setVisible(false);
			}
			//判断“是否预配交接单”
			if(basicInfoRecord.get('handOverBillState') == 10){
				form.findField('isPreHandOverBill').setValue(true);
			}
			//是否PDA生成
			if(load.handoverbillsaleshow.isCreatedByPDA == 'Y'){
				form.findField('isCreatedByPDA').setValue(true);
			}
			Ext.create('Ext.panel.Panel', {
				id : 'T_load-handoverbillsaleshowindex_content',
				cls : "panelContentNToolbar",
				bodyCls : 'panelContent-body',
				layout : 'auto',//
				items : [ basicInfoForm, load.handoverbillsaleshow.handOverBillDetailGrid,Ext.create('Foss.load.handoverbillsaleshow.HandOverBillOptLogGrid') ],
				renderTo : 'T_load-handoverbillsaleshowindex-body'
			});
			//从基本信息record中获取值，给统计信息赋值
			Ext.getCmp('Foss_load_handOverBillSaleShow_DetailPageTotalCount').setValue(basicInfoRecord.get('waybillQtyTotal'));
			Ext.getCmp('Foss_load_handOverBillSaleShow_DetailPageTotalPieces').setValue(basicInfoRecord.get('goodsQtyTotal'));
			Ext.getCmp('Foss_load_handOverBillSaleShow_DetailPageTotalWeight').setValue(basicInfoRecord.get('weightTotal').toFixed(2));
			Ext.getCmp('Foss_load_handOverBillSaleShow_DetailPageTotalVolume').setValue(basicInfoRecord.get('volumeTotal').toFixed(2));
			//更新主页未转换总体积
			Ext.getCmp('Foss_load_handOverBillSaleShow_DetailPageUnconvertTotalVolume').setValue(load.handoverbillsaleshow.nuConvertTotalVolume);
		}
	});
});