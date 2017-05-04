/***
 * @author ibm-wangfei
 * page:客户通知管理
 */
 predeliver.NOTIFY_TYPE = 'PKP_NOTIFY_CUSTOMER_TYPE'; // 通知方式词条
 predeliver.NOTIFY_STATUS = 'PKP_NOTIFY_CUSTOMER_STATUS'; // 通知结果词条
 predeliver.PICKUPGOODSHIGHWAYS = 'PICKUPGOODSHIGHWAYS'; // 派送方式词条
 predeliver.PAYMENTMODE = 'PAYMENTMODE'; // 付款方式词条
 predeliver.FAILURE = 'FAILURE'; // 通知状态-失败
 predeliver.SUCCESS = 'SUCCESS'; // 通知状态-成功
 predeliver.ARRIVED = 'ARRIVED'; // 车辆到达
 predeliver.FC = 'FC'; // 付款方式-到付
 predeliver.CLEARING_TYPE_MONTH = 'MONTH'; // 客户付款方式-月结
 predeliver.CLEARING_TYPE_HALF_MONTH = 'HALF_MONTH'; // 付款方式-半月结
 predeliver.SELECT_TYPE = ''; // 查询方式
 
// 根据免费库存天数，获取在库天数的store
predeliver.notifyCustomer.getStorageDayStore = function() {
	var data = new Array();
	for (var len = predeliver.notifyCustomer.warehouseFreeSafeDataNum; len > 0; len --) {
		data.unshift({'valueCode': len, 'valueName': len + '天'});
	}
	data.unshift({'valueCode': '', 'valueName': '全部'});
	var json={
		fields:['valueCode','valueName'],
	    data : data
	};
	return Ext.create('Ext.data.Store',json);
}

Ext.define('Foss.predeliver.model.NoticeCustomer', {
	extend : 'Ext.data.Model',
	fields : [
		{name : 'noticeResult' , type: 'string'}, /** 通知状态 */
		{name : 'waybillNo'}, /** 运单号 */
		{name : 'receiveCustomerContact'}, /** 收货人姓名 */
		{name : 'receiveCustomerPhone'}, /** 收货人电话 */
		{name : 'receiveCustomerMobilephone'}, /** 收货人手机 */
		{name : 'receiveMethod',
			convert:function(value) {
				return FossDataDictionary.rendererSubmitToDisplay(value, predeliver.PICKUPGOODSHIGHWAYS);
			}
		}, /** 派送方式 */
		{name : 'receiveCustomerAddress'}, /** 送货地址 */
		{name : 'inStockTime', convert:dateConvert}, /** 入库时间 */
		{name : 'arriveTime', convert:dateConvert}, /** 到达时间 */
		{name : 'planArriveTime', convert:dateConvert}, /** 预计到达时间 */
		{name : 'goodsQtyTotal', type : 'int'}, /** 开单件数 */
		{name : 'arriveGoodsQty', type : 'int'}, /** 到达件数 */
		{name : 'handoverGoodsQty', type : 'int'}, /** 交接件数 */
		{name : 'storageDay', type : 'int'}, /** 在库天数 */
		{name : 'handoverNo'}, /** 交接单号 */
		{name : 'deliveryCustomerName'}, /** 发货人 */
		{name : 'goodsName'}, /** 货物名称 */
		{name : 'insuranceAmount', type : 'float'}, /** 货物价值 */
		{name : 'paidMethod',
			convert:function(value) {
				return FossDataDictionary.rendererSubmitToDisplay(value, predeliver.PAYMENTMODE);
			}
		}, /** 付款方式 */
		{name : 'receiveCustomerContact'}, /** 始发部门 */
		{name : 'productCode',
			convert:function(value) {
				return Foss.pkp.ProductData.rendererSubmitToDisplay(value);
			}
		}, /** 运输性质 */
		{name : 'stockStatus', type : 'string'}, /** 库存状态 */
		{name : 'stockQty', type : 'int'}, /** 库存件数 */
		{name : 'goodsWeightTotal', type : 'float'}, /** 总量 */
		{name : 'goodsVolumeTotal', type : 'float'}, /** 体积 */
		{name : 'goodsPackage'}, /** 包装 */
		{name : 'goodsSize'}, /** 尺寸 */
		{name : 'transportFee', type : 'float'}, /** 运费 */
		{name : 'deliveryGoodsFee', type : 'float'}, /** 送货费 */
		{name : 'otherFee', type : 'float'}, /** 其他费用 */
		{name : 'insuranceFee', type : 'float'}, /** 保价费 */
		{name : 'codAmount', type : 'float'}, /** 代收费 */
		{name : 'toPayAmount', type : 'float'}, /** 到付费 */
		{name : 'storageCharge', type : 'float'}, /** 存储费 */
		{name : 'billTime', type : 'date'}, /** 出发日期 */
		{name : 'notificationTime', type : 'date', convert:dateConvert}, /** 上次通知日期 */
		{name : 'creditLimit', type : 'float'}, /** 可用信用额度 */
		{name : 'receivingHabits'}, /** 收货习惯 */
		{name : 'selectType'}, /** 查询方式 */
		{name : 'notificationTimeSpace'}, /** 最后通知日期与当前日期的间隔天数 */
		{name : 'receiveOrgName'}, /** 始发部门*/
		{name : 'taskStatus'}, /** 车辆状态*/
		{name : 'customerQulification'}, /** 客户资质*/
		{name : 'creditAmount'}, /** 信用额度*/
		{name : 'receiveCustomerCode'}, /** 发送部门code*/
		{name : 'isBackFlg'}, /** 客户是否有银行账户列表*/
		{name : 'createOrgCode'} /** 创建部门code*/
	]
});

Ext.define('Foss.predeliver.model.NoticeInfo', {
	extend : 'Ext.data.Model',
	fields : [
		{name: 'waybillNo',type:'string'},
		{name: 'receiveCustomerContact',type:'string'},
		{name: 'deliverDate',type:'date',
			convert: function(value) {
				if (value != null) {
					var date = new Date(value);
					return Ext.Date.format(date,'Y-m-d');
				} else {
					return null;
				}
			}},
		{name: 'customerQulification'},// 客户资质
		{name: 'deliverRequire',type:'string'},
		{name: 'creditLimit',type:'string'},
		{name: 'smallTicketFee',type:'number'},
		{name: 'contactType',type:'string'},
		{name: 'container',type:'string'},
		{name: 'toPayAmount',type:'number'},
		{name: 'isStorageCharge',type:'string'},
		{name: 'arriveGoodsQty',type:'string'},
		{name: 'estimatedPickupTime',type:'string'},
		{name: 'noticeContent',type:'string'},
		{name: 'noticeResult',type:'string'},
		{name: 'paymentType',type:'string'},
		{name: 'isNeedInvoice',type:'string'},
		{name: 'isSentRequired',type:'string'},
		{name: 'taxNo',type:'string'},
		{name: 'companyName',type:'string'},
		{name: 'tel',type:'string'},
		{name: 'account',type:'string'},
		{name: 'bank',type:'string'},
		{name: 'address',type:'string'}
	]
});

Ext.define('Foss.predeliver.model.WarehouseCharge', {
	extend : 'Ext.data.Model',
	fields : [
		{name : 'waybillNo'}, 
		{name : 'storageDay',type : 'number'}, 
		{name : 'overdueDay',type : 'number'}, 
		{name : 'storageCharge',type : 'number'}, 
		{name : 'exceptionType'}, 
		{name : 'exceptionNotes'}]
});

//创建一个查询枚举store
Ext.define('Foss.predeliver.model.QueryNoticeInfo',{
	extend: 'Ext.data.Model',
	fields: [
		{name: 'code',  type: 'string'},
		{name: 'name',  type: 'string'}
	],
	//定义一个代理对象
	proxy: {
		//代理的类型为内存代理
		type: 'memory',
		//定义一个读取器
		reader: {
			//以JSON的方式读取
			type: 'json',
			//定义读取JSON数据的根对象
			root: 'items'
		}
	}
});
// 运单通知历史信息model
Ext.define('Foss.predeliver.model.HistoryPanel', {
	extend : 'Ext.data.Model',
	fields : [
	  {name : 'operateTime', type: 'date',
		  convert: function(value) {
			if (value != null) {
				var date = new Date(value);
				return Ext.Date.format(date,'Y-m-d H:i:s');
			} else {
				return null;
			}
		}},
	  {name : 'consignee'}, 
	  {name : 'mobile'}, 
	  {name : 'deliverType'},
	  {name : 'noticeType', type: 'string'}, /** 通知状态 */
	  {name : 'noticeResult', type: 'string'}, /** 通知状态 */
	  {name : 'operateOrgName'},
	  {name : 'noticeContent'},
	  {name : 'exceptionNotes'}
]
});


Ext.define('Foss.predeliver.store.NoticeCustomer', {
	extend : 'Ext.data.Store',
	model : 'Foss.predeliver.model.NoticeCustomer',
	pageSize : 10,
	proxy : {
		type : 'ajax',
		actionMethods : 'POST',
		// url : '../predeliver/queryStayNotificationBill.action',
		url:predeliver.realPath('queryStayNotificationBill.action'),
		reader : {
			type : 'json',
			root : 'vo.dtoList',
			totalProperty : 'totalCount',
			successProperty : 'success'
		}
	},
	listeners : {
		beforeload : function(store, operation, eOpts) {
			var queryParams = predeliver.notifyCustomer.queryform.getValues();
			Ext.apply(operation, {
				params : queryParams
			});
		},
		load : function(store, records, successful,eOpts) {
			// 根据后台传入的值动态显示列
			var columns = Ext.getCmp('Foss_predeliver_grid_NoticeCustomerGrid_Id').columns;
			if (records.length == 0) {
				return;	
			}
			if (records[0].data.selectType == '0') {
				// 按库存查询
				columns[9].setVisible(true);  // 入库时间
				columns[10].setVisible(false);// 到达时间
				columns[11].setVisible(false);// 预计到达时间
				columns[14].setVisible(true);// 库存件数
				columns[15].setVisible(true);// 在库天数
				columns[16].setVisible(false);// 交接单件数
				columns[17].setVisible(false);// 交接单号
			} else {
				// 按交接单查询
				columns[9].setVisible(false);  // 入库时间
				columns[10].setVisible(true);// 到达时间
				columns[11].setVisible(true);// 预计到达时间
				columns[14].setVisible(false);// 库存件数
				columns[15].setVisible(false);// 在库天数
				columns[16].setVisible(true);// 交接单件数
				columns[17].setVisible(true);// 交接单号
			}
			// 隐藏列
			columns[19].setVisible(false);
			columns[20].setVisible(false);
			columns[21].setVisible(false);
			predeliver.SELECT_TYPE = records[0].data.selectType;
		}
	}
});

Ext.define('Foss.predeliver.form.NoticeCustomerSearch', {
	extend : 'Ext.form.Panel',
	title : '查询条件',
	collapsible : true,
	animCollapse : true,
	defaults : {
		margin : '5 10 5 10',
		labelWidth : 60
	},
	cls : 'autoHeight',
	bodyCls : 'autoHeight',
	defaultType : 'textfield',
	layout : 'column',
	frame : true,
	id : 'Foss_predeliver_form_noticeCustomerSearch_Id',
	items : [{
		name : 'vo.conditionDto.waybillNo',
		fieldLabel : '运单号',
		columnWidth : .25,
		vtype: 'waybill'
	}, {
		name : 'vo.conditionDto.handoverNo',
		fieldLabel : '交接单号',
		columnWidth : .25
	}, {
		name : 'vo.conditionDto.vehicleAssembleNo',
		fieldLabel : '车次号',
		columnWidth : .25
	}, {
		xtype:'combo',
		displayField:'valueName',
		valueField:'valueCode',
		queryMode:'local',
		triggerAction:'all',
		value : '',
		editable:false,
		name : 'vo.conditionDto.receiveMethod',
		fieldLabel : '派送方式',
		columnWidth : .25,
		store : FossDataDictionary.getDataDictionaryStore(predeliver.PICKUPGOODSHIGHWAYS, null, {
			'valueCode': '',
            'valueName': '全部'
		})
	}, {
		xtype:'combo',
		displayField:'name',
		valueField:'code',
		queryMode:'local',
		triggerAction:'all',
		value : '',
		editable:false,
		name : 'vo.conditionDto.productCode',
		fieldLabel : '运输性质',
		columnWidth : .25,
		store : Ext.create('Foss.pkp.ProductStore')
	}, {
		xtype:'combo',
		displayField:'valueName',
		valueField:'valueCode',
		queryMode:'local',
		triggerAction:'all',
		value : '',
		editable:false,
		name : 'vo.conditionDto.noticeResult',
		fieldLabel : '通知情况',
		columnWidth : .25,
		store : FossDataDictionary.getDataDictionaryStore(predeliver.NOTIFY_STATUS, null, {
			'valueCode': '',
            'valueName': '全部'
		})
	}, {
		xtype:'combo',
		displayField:'valueName',
		valueField:'valueCode',
		queryMode:'local',
		triggerAction:'all',
		value : '',
		editable:false,
		name : 'vo.conditionDto.storageDay',
		fieldLabel : '在库天数',
		columnWidth : .25,
		store : predeliver.notifyCustomer.getStorageDayStore()
	}, {
		xtype : 'rangeDateField',
		fieldLabel : '入库时间',
		fieldId : 'Foss_predeliver_inStockTime_Id',
		dateType : 'datetimefield_date97',
		fromName : 'vo.conditionDto.inStockTimeFrom',
		toName : 'vo.conditionDto.inStockTimeTo',
		editable:false,
		labelWidth : 60,
		columnWidth : .5
	}, {
		xtype : 'rangeDateField',
		fieldLabel : '预计到达时间',
		fieldId : 'Foss_predeliver_planArriveTime_Id',
		dateType : 'datetimefield_date97',
		fromName : 'vo.conditionDto.planArriveTimeFrom',
		toName : 'vo.conditionDto.planArriveTimeTo',
		labelWidth : 90,
		editable:false,
		columnWidth : .5
	},{
		border: 1,
		xtype:'container',
		columnWidth:1,
		defaultType:'button',
		layout:'column',
		items:[{
		text : '重置',
		columnWidth:.08,
		handler : function() {
			this.up('form').getForm().reset();
		}
		}, {
			xtype: 'container',
			border : false,
			columnWidth:.84,
			html: '&nbsp;'
		}, {
			text : '查询',
			columnWidth:.08,
			cls : 'yellow_button',
			handler : function() {
				var myForm = this.up('form').getForm();
				if(myForm.isValid()){
					Ext.getCmp('Foss_predeliver_grid_NoticeCustomerGrid_Id').getPagingToolbar().moveFirst();
				}
			}
		}]
	}]
});
Ext.define('Foss.predeliver.grid.NoticeCustomerGrid', {
	extend : 'Ext.grid.Panel',
	title : '搜索结果',
	cls : 'autoHeight',
	bodyCls : 'autoHeight',
	frame : true,
	emptyText: "查询结果为空",
	id : 'Foss_predeliver_grid_NoticeCustomerGrid_Id',
	// 增加滚动条
	autoScroll : false,
	collapsible : true,
	// animCollapse: true,
	selModel : Ext.create('Ext.selection.CheckboxModel'),

	viewConfig : {
		// 单元格可复制
		enableTextSelection: true,
		//显示重复样式，不用隔行显示
		stripeRows : false,
		getRowClass : function(record, rowIndex, rp, ds) {
			var notificationStatus = record.get('noticeResult');
			if (notificationStatus == '') {
				//未通知-白色
				return 'predeliver_notice_customer_row_white';
			}
			var selectType = record.get('selectType'),goodsQtyTotal = record.get('goodsQtyTotal'),
				stockQty = record.get('stockQty');
			if (selectType == '0' && goodsQtyTotal != stockQty) {
				// 开单库存件数不一致 - 粉色
				return 'predeliver_notice_customer_row_pink';
			}
			var notificationTimeSpace = record.get('notificationTimeSpace');
			if (notificationStatus ==  predeliver.SUCCESS && stockQty > 0) {
				// 通知成功并且库存数量大于0
				if (notificationTimeSpace != null && notificationTimeSpace > predeliver.notifyCustomer.warehouseFreeSafeDataNum) {
					// 通知时间后X天且库存中仍有该票运单，则表明是通知X天未提货 - 紫色
					return 'predeliver_notice_customer_row_purole';
				}
			}
		}
	},
	// 表格行可展开的插件
	plugins : [{
		header : true,
		ptype : 'rowexpander',
		// 定义行展开模式（单行与多行），默认是多行展开(值true)
		rowsExpander : false,
		layout : 'hbox',
		// 行体内容
		rowBodyElement : 'Foss.predeliver.panel.NoticeCustomerInfoPanel'
	}],
	tbar : [{
		xtype : 'button',
		text : '批量通知',
		plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin', {
			seconds: 3
		}),
		handler : function() {
			var waybillNos = '', array = new Array(), notificationEntity = '',taskStatus = '',selectType = '',
				records = Ext.getCmp('Foss_predeliver_grid_NoticeCustomerGrid_Id').getSelectionModel().getSelection();
			
			for (var i = 0; i < records.length; i++) {
				waybillNos += records[i].get("waybillNo") + ",";
				selectType = records[i].get('selectType');
				if (selectType == '0') {
					// 按库存方式查询时，通知的数量为库存数量
					notificationEntity = {'waybillNo' : records[i].data.waybillNo, 'arriveGoodsQty' : records[i].data.stockQty};
				} else {
					// 按交接单、车次、预计到达时间查询时，通知的数量为交接单数量
					notificationEntity = {'waybillNo' : records[i].data.waybillNo, 'arriveGoodsQty' : records[i].data.handoverGoodsQty};
				}
				array.push(notificationEntity);
				if (selectType == '0') {
					// 对按照库存查询不执行车辆状态验证
					continue;
				}
				
				taskStatus = records[i].get("taskStatus");
				if (taskStatus !=  predeliver.ARRIVED) {
					// 如果选择的运单车辆状态不是已到达的，不能进行批量通知
					Ext.ux.Toast.msg('提示', '您选择的运单，车辆还没有到达，不能进行语音短信通知，请重新选择。', 'error', 1000);
					return;
				}
			}
			if (waybillNos == "") {
				Ext.ux.Toast.msg('提示', '请选择运单。', 'error', 1000);
				return;
			}
			var newVo = {
				'vo': {
					'conditionDto' : {
						'waybillNos': waybillNos,
						'notificationEntityList' : array
					}
				}
			}
			Ext.Ajax.request({
				// url: '../predeliver/batchNotifyList.action',
				url:predeliver.realPath('batchNotifyList.action'),
				jsonData: newVo,
				success: function(response){
					var result = Ext.decode(response.responseText);
					predeliver.notifyCustomer.initBatchNoticeWinForm(result);
				},
				exception: function(response) {
	                var json = Ext.decode(response.responseText);
	                Ext.ux.Toast.msg('批量通知失败', json.message, 'error', 2000);
	            }
			});
		}
	}, {
		xtype : 'button',
		text : '打印到达联',
		handler : function() {
			var records = Ext.getCmp('Foss_predeliver_grid_NoticeCustomerGrid_Id').getSelectionModel().getSelection();
			//var waybillNos = [];
			var mygrid = this.up('gridpanel');
			if(records.length==0){
    			Ext.ux.Toast.msg('提示', '请选择运单。', 'error', 1000);
    			return;
    		}
    		mygrid.getPrintWindow().show();
		}
	}, {
		xtype : 'label',
		text : '语音通知中:'
	}, {
		xtype : 'image',
		imgCls : 'foss_icons_pkp_telnoticing'
	},{
		xtype : 'label',
		text : '短信通知中:'
	}, {
		xtype : 'image',
		imgCls : 'foss_icons_pkp_mailnoticing'
	}, {
		xtype : 'label',
		text : '通知成功:'
	}, {
		xtype : 'image',
		imgCls : 'foss_icons_pkp_noticeOk'
	}, {
		xtype : 'label',
		text : '通知失败:'
	}, {
		xtype : 'image',
		imgCls : 'foss_icons_pkp_noticeError'
	}, {
		xtype : 'label',
		text : '通知未果:'
	}, {
		xtype : 'image',
		imgCls : 'foss_icons_pkp_noticeNoResult'
	}, {
		xtype : 'label',
		text : '未通知:'
	}, {
		xtype : 'image',
		imgCls : 'foss_icons_pkp_noNotice'
	}, {
		xtype : 'label',
		text : '通知' + predeliver.notifyCustomer.warehouseFreeSafeDataNum + '天未提货:'
	}, {
		xtype : 'image',
		imgCls : 'foss_icons_pkp_3daysNoPicking'
	}, {
		xtype : 'label',
		text : '开单库存件数不一致:'
	}, {
		xtype : 'image',
		imgCls : 'foss_icons_pkp_goodsNumDisac'
	}, {
		xtype : 'tbspacer',
		flex : 1
	}, {
		xtype : 'button',
		text : '保管费报表',
		handler : function() {
			var records = Ext.getCmp('Foss_predeliver_grid_NoticeCustomerGrid_Id').getSelectionModel().getSelection();
			var waybillNos = '';
			for (var i = 0; i < records.length; i++) {
				waybillNos += records[i].get("waybillNo") + ",";
			}
			if (waybillNos == "") {
				Ext.ux.Toast.msg('提示', '请选择运单。', 'error', 1000);
				return;
			}
			Ext.Ajax.request({
				// url: '../predeliver/queryStorageList.action',
				url:predeliver.realPath('queryStorageList.action'),
				params: {
					'vo.conditionDto.waybillNos': waybillNos
				},
				success: function(response){
					var result = Ext.decode(response.responseText);
					predeliver.notifyCustomer.initWarehouseChargeWinForm(result);
				}
			});
		}
	}],
	columns : [
		{text : '状态',flex : .2,dataIndex : 'noticeResult', 
			renderer : function(value, cellmeta, record, rowIndex, columnIndex, store){
				var result = '';
				if(value == 'VOICE_NOTICING') {
					//语音通知中
					result = '<div class="foss_icons_pkp_telnoticing"></div>';
				} else if(value == 'SMS_NOTICING') {
					//短信通知中
					result = '<div class="foss_icons_pkp_mailnoticing"></div>';
				} else if(value == 'SUCCESS') {
					//通知成功 
					result = '<div class="foss_icons_pkp_noticeOk"></div>';
				} else if(value == 'FAILURE') {
					//通知失败
					result = '<div class="foss_icons_pkp_noticeError"></div>';
				} else if(value == 'NOTICING_UNSUCCESSFUL') {
					//通知未果
					result = '<div class="foss_icons_pkp_noticeNoResult"></div>';
				}
				return result;
			}
		},
		{text : '运单号',flex : .5,dataIndex : 'waybillNo', xtype : 'ellipsiscolumn'},
		{text : '收货人姓名',flex : .5,dataIndex : 'receiveCustomerContact', xtype : 'ellipsiscolumn'},
		{text : '收货人电话',flex : .5,dataIndex : 'receiveCustomerPhone', xtype : 'ellipsiscolumn'},
		{text : '收货人手机',flex : .5,dataIndex : 'receiveCustomerMobilephone', xtype : 'ellipsiscolumn'},
		{text : '派送方式',flex : .5,dataIndex : 'receiveMethod', xtype : 'ellipsiscolumn'},
		{text : '送货地址',flex : 1,xtype : 'ellipsiscolumn',dataIndex : 'receiveCustomerAddress'},
		{text : '入库时间',width : 140, dataIndex : 'inStockTime',
			renderer : function(value) {
				if (value != null) {
					var date = Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
					return date;
				} else {
					return null;
				}
			} 
		},
		{text : '到达时间',width : 140, dataIndex : 'arriveTime',hidden : true,
			renderer : function(value) {
				if (value != null) {
					var date = Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
					return date;
				} else {
					return null;
				}
			} 		
		},
		{text : '预计到达时间',width : 140, dataIndex : 'planArriveTime',hidden : true,
			renderer : function(value) {
				if (value != null) {
					var date = Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
					return date;
				} else {
					return null;
				}
			} 		
		},
		{text : '开单<br>件数',flex : .2,xtype : 'numbercolumn',dataIndex : 'goodsQtyTotal',format:'0'},
		{text : '到达<br>件数',flex : .2,xtype : 'numbercolumn',dataIndex : 'arriveGoodsQty',format:'0'},
		{text : '库存<br>件数',flex : .2,xtype : 'numbercolumn',dataIndex : 'stockQty',format:'0'},
		{text : '在库<br>天数',flex : .2,xtype : 'numbercolumn',dataIndex : 'storageDay',format:'0'},
		{text : '交接单<br>&nbsp;件数',flex : .2,dataIndex : 'handoverGoodsQty',hidden : true},
		{text : '交接单号',flex : .5,dataIndex : 'handoverNo',hidden : true, xtype : 'ellipsiscolumn'},
		{text : '收货习惯',width : 140,dataIndex : 'receivingHabits', xtype : 'ellipsiscolumn'},
		{dataIndex : 'selectType',hidden : true},
		{dataIndex : 'notificationTimeSpace',hidden : true},
		{dataIndex : 'taskStatus',hidden : true}
	],
	pagingToolbar : null,
  	getPagingToolbar : function() {
  		if (this.pagingToolbar == null) {
  			this.pagingToolbar = Ext.create('Deppon.StandardPaging', {
  				store : this.store
  			});
  		}
  		return this.pagingToolbar;
  	},
  	printWindow: null,
    	getPrintWindow: function(){
    		var me = this;
    		if(this.printWindow==null){
    			me.printWindow = Ext.create('Foss.printArriveSheet.printWindow',me);
    		}
    		return me.printWindow;
    	},
  	constructor : function(config) {
  		var me = this,
			cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.predeliver.store.NoticeCustomer');
		me.bbar = me.getPagingToolbar();
		me.callParent(cfg);
	}
});

// ====================batch notice window and grid start================
predeliver.notifyCustomer.initBatchNoticeWinForm = function(data) {
	if (Ext.getCmp('Foss_predeliver_window_batchNotice_Id')) {
		Ext.getCmp('Foss_predeliver_window_batchNotice_Id').show();
		return;
	}
	Ext.create('Ext.window.Window', {
		id : 'Foss_predeliver_window_batchNotice_Id',
		width : 1000,
		title : '批量通知',
		modal:true,
		// 动态进行通知内容赋值
		changeNotice : function(_this, newValue, oldValue, eOpts) {
			// 预计提货时间
			if (_this.name == 'estimatedPickupTime') {
				var estimatedPickupTimeH = Ext.getCmp('Foss_predeliver_estimatedPickupTimeH_Id');
				// 判断输入小时，如果大于99，不处理
				if (_this.getValue() > 99) {
					return;
				}
				// 判断现在输入的值和未变更前的值是否相等
				if (estimatedPickupTimeH.getValue() == _this.getValue()) {
					return;
				}
				estimatedPickupTimeH.setValue(_this.getValue());
			}
			var noticeType = Ext.getCmp('Foss_predeliver_noticeType_Id').getValue().noticeType,
				isStorageChargeCheck = Ext.getCmp('Foss_predeliver_isStorageCharge_Id').getSubmitData(),isStorageCharge,
				estimatedPickupTime = Ext.getCmp('Foss_predeliver_estimatedPickupTime_Id').getValue(), notificationEntity = '', 
				array = new Array(), notificationEntityT = '';
			// 是否征收保管费
			if (isStorageChargeCheck != null) {
				isStorageCharge = isStorageChargeCheck.isStorageCharge;
			} else {
				isStorageCharge = '';
			}
			var store = Ext.data.StoreManager.lookup('Foss_predeliver_model_BatchNoticeID'),
				batchNoticeStoreItems = store.data.items, waybillNos = '';
			for (var i = 0; i < batchNoticeStoreItems.length; i++) {
				waybillNos +=  batchNoticeStoreItems[i].data.waybillNo  + ",";
				notificationEntity = {'waybillNo' : batchNoticeStoreItems[i].data.waybillNo, 'arriveGoodsQty' : batchNoticeStoreItems[i].data.arriveGoodsQty};
				array.push(notificationEntity);
			}
			notificationEntity = { 'noticeType' : noticeType,'isStorageCharge' : isStorageCharge,'estimatedPickupTime' : estimatedPickupTime};
			var newVo = {
						'vo': {
								'conditionDto' : {
								'notificationEntity' :notificationEntity, 
								'waybillNos' : waybillNos,
								'notificationEntityList' : array
							}
						}
					};
			Ext.Ajax.request({
				// url: '../predeliver/batchNotifyList.action',
				url:predeliver.realPath('batchNotifyList.action'),
				jsonData : newVo,
				success: function(response){
					var result = Ext.decode(response.responseText);
					Ext.getCmp('Foss_noticeCustomer_batchNotice_ID').getStore().loadData(result.vo.dtoList);
				}
			});
		},
		items : [predeliver.notifyCustomer.initBatchNoticeGrid(data.vo.dtoList)],
			buttons : [{
				xtype : 'label',
				text : '说明:只能在' + data.vo.informationReceiveTimeRange + '发送语音与短信通知',
				width : 250,
				margin: '4 640 0 0'
			}, {
				cls : 'yellow_button',
				text : '确定通知',
				plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin', {
					seconds: 3
				}),
				style : {
					float : 'right'
				},
				handler : function() {
					var records = Ext.getCmp('Foss_noticeCustomer_batchNotice_ID').getSelectionModel().getSelection();
					if (records.length == 0) {
						Ext.ux.Toast.msg('提示', '至少选择一条记录。', 'error', 1000);
						return;
					}
					var array = new Array(), notificationEntity = '',noticeType = Ext.getCmp('Foss_predeliver_noticeType_Id').getValue().noticeType,
						isStorageChargeCheck = Ext.getCmp('Foss_predeliver_isStorageCharge_Id').getSubmitData(),isStorageCharge,
						estimatedPickupTime = Ext.getCmp('Foss_predeliver_estimatedPickupTime_Id').getValue();
					if (isStorageChargeCheck != null) {
						isStorageCharge = isStorageChargeCheck.isStorageCharge;
					} else {
						isStorageCharge = '';
					}
					
					for (var i = 0; i < records.length; i++) {
						// 判断选择的记录有没有手机号码
						if(Ext.isEmpty(records[i].data.receiveCustomerMobilephone)) {
							Ext.ux.Toast.msg('错误', "选择的运单必须有手机号码。", 'error', 2000);
							return;
						}
						notificationEntity = {'waybillNo' : records[i].data.waybillNo,
							'receiveCustomerContact' : records[i].data.receiveCustomerContact,
							'receiveCustomerMobilephone' : records[i].data.receiveCustomerMobilephone,
							'receiveMethod' : records[i].data.receiveMethod,
							'noticeType' : noticeType, 
							'isStorageCharge' : isStorageCharge,
							'estimatedPickupTime' : estimatedPickupTime,
							'noticeContent' : records[i].data.noticeContent
							};
						array.push(notificationEntity);
					}
					var newVo = {
						'vo': {
							'conditionDto' : {'notificationEntityList' :array}
						}
					};
					Ext.Ajax.request({
						// url: '../predeliver/batchNotify.action',
						url:predeliver.realPath('batchNotify.action'),
						jsonData : newVo,
						success: function(response){
							Ext.ux.Toast.msg('提示', '批量通知成功。', 'ok', 1000);
							Ext.getCmp('Foss_predeliver_window_batchNotice_Id').close();
						},
						 exception: function(response) {
			                var json = Ext.decode(response.responseText);
			                Ext.ux.Toast.msg('批量通知失败', json.message, 'error', 2000);
		                }
					});
					
				}
			}],
		constructor: function(config){
			var me = this,
				cfg = Ext.apply({}, config);
			me.callParent([cfg]);
		}
	}).show();
}

predeliver.notifyCustomer.initWarehouseChargeWinForm = function(data) {
	if (Ext.getCmp('Foss_predeliver_window_warehouseCharge_Id')) {
		Ext.getCmp('Foss_predeliver_window_warehouseCharge_Id').show();
		return;
	}
	Ext.create('Ext.window.Window', {
		id : 'Foss_predeliver_window_warehouseCharge_Id',
		width : 800,
		title : '保管费报表',
		modal:true,
		items : [predeliver.notifyCustomer.initWarehouseChargeGrid(data.vo.dtoList)]
	}).show();
}
	
predeliver.notifyCustomer.initBatchNoticeGrid = function(data) {
	Ext.define('Foss.predeliver.model.BatchNotice', {
		extend : 'Ext.data.Model',
		fields : [
		  {name : 'waybillNo'},
		  {name : 'receiveCustomerContact'},
		  {name : 'receiveCustomerMobilephone'},
		  {name : 'receiveMethod',
			convert:function(value) {
				return FossDataDictionary.rendererSubmitToDisplay(value, predeliver.PICKUPGOODSHIGHWAYS);
			}
		  }, /** 派送方式 */
		  {name : 'noticeContent'},
		  {name : 'arriveGoodsQty', type:'number'}]
		});
	var batchNoticeStore = Ext.create('Ext.data.Store', {
			model : 'Foss.predeliver.model.BatchNotice',
			storeId : 'Foss_predeliver_model_BatchNoticeID',
			data : data
		});
	return Ext.create('Ext.grid.Panel', {
		id : 'Foss_noticeCustomer_batchNotice_ID',
		store : batchNoticeStore,
		bodyCls : 'autoHeight',
		autoScroll : false,
		selModel : Ext.create('Ext.selection.CheckboxModel'),
		columns : [{
			text : '单号',
			width : 100,
			dataIndex : 'waybillNo'
		}, {
			text : '收货人',
			flex : .8,
			dataIndex : 'receiveCustomerContact'
		}, {
			text : '手机号',
			width : 120,
			dataIndex : 'receiveCustomerMobilephone'
		}, {
			text : '派送方式',
			flex : 1,
			dataIndex : 'receiveMethod',
			xtype : 'ellipsiscolumn'
		}, {
			text : '信息内容',
			flex : 3.6,
			dataIndex : 'noticeContent',
			xtype : 'ellipsiscolumn'
		} , {
			dataIndex : 'arriveGoodsQty',
			hidden : true
		}],
		dockedItems: {
			xtype : 'toolbar',
			dock : 'bottom',
			layout : 'column',
			items : [
				{xtype : 'radiogroup' , vertical : true , columnWidth : .4,id : 'Foss_predeliver_noticeType_Id',
				items : [
						{boxLabel : '语音通知' , name : 'noticeType' , inputValue : 'VOICE_NOTICE'} , 
						{boxLabel : '短信通知' , name : 'noticeType' , inputValue : 'SMS_NOTICE', checked : true}
					],
				listeners : { 
						change:function(_this, newValue, oldValue, eOpts){
							this.up('window').changeNotice(_this, newValue, oldValue, eOpts);
						}
					}
				}, 
				{xtype : 'checkbox' , name : 'isStorageCharge' , boxLabel : '是否征收保管费',columnWidth : .3, id : 'Foss_predeliver_isStorageCharge_Id', inputValue : 'Y',
					listeners : { 
						change:function(_this, newValue, oldValue, eOpts){
							this.up('window').changeNotice(_this, newValue, oldValue, eOpts);
						}
					}}, 
				{xtype : 'container' ,  layout: 'column',columnWidth : .3,
					items : [
						{xtype : 'label' , text : '预计', margin: '4 0 0 9'} , 
						{xtype : 'numberfield' , name : 'estimatedPickupTime', width : 30,id : 'Foss_predeliver_estimatedPickupTime_Id',
						hideTrigger : true, maxValue: 99, minValue: 1, maxLength :2, allowDecimals : false, msgTarget: 'qtip',
						listeners : { 
							blur : function (_this, The, eOpts) {
								this.up('window').changeNotice(_this, null, null, eOpts);
							}
						}} , 
						{xtype : 'label',  text : '小时后提货', margin: '4 0 0 0'}
					]
				},
				{name : 'estimatedPickupTimeH' , xtype : 'hiddenfield', id : 'Foss_predeliver_estimatedPickupTimeH_Id'}]
		}
	});
}
// ====================batch notice window and grid end================

// ====================warehosue charge report window and grid
// start================
predeliver.notifyCustomer.initWarehouseChargeGrid = function(data) {
	var store = Ext.create('Ext.data.Store', {
		model : 'Foss.predeliver.model.WarehouseCharge',
		data : data
	});
	return Ext.create('Ext.grid.Panel', {
		id : 'Foss.noticeCustomer.panel.WarehouseChargeGrid',
		autoScroll : false,
		stripeRows : true,
		collapsible : true,
		selType : 'rowmodel',
		store : store,
		bodyCls : 'autoHeight',
		columns : [{
			dataIndex : 'waybillNo',
			text : '运单号',
			flex : 0.8
		}, {
			dataIndex : 'storageDay',
			text : '库存天数',
			flex : 0.8
		}, {
			dataIndex : 'overdueDay',
			text : '逾期天数',
			flex : 0.8
		}, {
			dataIndex : 'storageCharge',
			text : '保管费金额',
			flex : 0.8
		}, {
			dataIndex : 'exceptionType',
			text : '货物异常类型',
			flex : 1
		}, {
			dataIndex : 'exceptionNotes',
			text : '异常备注',
			flex : 2,
			xtype : 'ellipsiscolumn'
		}]
	})
}
Ext.define('Foss.predeliver.noticeCustomer.TransportInfoFormPanel', {
	extend : 'Ext.form.Panel',
	title : '运单信息',
	defaultType : 'textfield',
	height : 453,
	defaults:{
    	labelWidth:85
    },
	flex : 1,
	layout : 'column',
	items : [
		{name : 'waybillNo' ,  fieldLabel : '单号' ,  columnWidth : .33} ,  
		{name : 'receiveOrgName' ,  fieldLabel : '始发部门' ,  columnWidth : .33 } ,  
		{name : 'transportFee' , fieldLabel : '运费' , columnWidth : .33} ,  
		{name : 'receiveCustomerContact' , fieldLabel : '收货人姓名' , columnWidth : .33} ,   
		{name : 'productCode' , fieldLabel : '运输性质' , columnWidth : .33} ,
		{name : 'deliveryGoodsFee' , fieldLabel : '送货费' , columnWidth : .33} ,
		{name : 'receiveCustomerPhone' , fieldLabel : '收货人电话' , columnWidth : .33} ,  
		{name : 'stockStatus' , fieldLabel : '库存状态' , columnWidth : .33} ,  
		{name : 'otherFee' , fieldLabel : '其他费用' , columnWidth : .33} ,  
		{name : 'receiveCustomerMobilephone' , fieldLabel : '收货人手机' , columnWidth : .33} ,  
		{name : 'goodsQtyTotal' , fieldLabel : '开单件数' , columnWidth : .33} ,  
		{name : 'insuranceFee' , fieldLabel : '保价费' , columnWidth : .33} ,  
		{name : 'receiveCustomerAddress' , fieldLabel : '收货人地址' , columnWidth : .33 , xtype : 'textarea'} ,  
		{name : 'arriveGoodsQty' , fieldLabel : '到达件数' , columnWidth : .33} ,  
		{name : 'codAmount' , fieldLabel : '代收货款' , columnWidth : .33} ,  
		{name : 'tmp' , columnWidth : .33 ,  hidden : true} ,  
		{name : 'stockQty' , fieldLabel : '库存件数' , columnWidth : .33} ,  
		{name : 'toPayAmount' , fieldLabel : '到付金额' , columnWidth : .33} ,  
		{name : 'tmp' , columnWidth : .33 ,  hidden : true} ,  
		{name : 'storageDay' , fieldLabel : '在库天数' , columnWidth : .33} ,  
		{name : 'storageCharge' , fieldLabel : '保管费' , columnWidth : .33} ,  
		{xtype: 'container' , border : false , columnWidth : 1 ,  html : '&nbsp;'} ,  
		{name : 'deliveryCustomerName' , fieldLabel : '发货人' , columnWidth : 1} ,  
		{name : 'goodsName' , fieldLabel : '货物名称' , columnWidth : .33} ,  
		{name : 'goodsWeightTotal' , fieldLabel : '重量(千克)' , columnWidth : .66} ,  
		{name : 'insuranceAmount' , fieldLabel : '货物价值' , columnWidth : .33} ,  
		{name : 'goodsVolumeTotal' , fieldLabel : '体积(立方米)' , columnWidth : .66} ,  
		{name : 'paidMethod' , fieldLabel : '付款方式' , columnWidth : .33},  
		{name : 'goodsPackage' , fieldLabel : '包装' , columnWidth : .66} ,  
		{name : 'receiveMethod' , fieldLabel : '派送方式' , columnWidth : .33} ,  
		{name : 'goodsSize' , fieldLabel : '尺寸(厘米)' , columnWidth : .33} ,  
		{name : 'notificationTime' , xtype: 'datefield', format : 'Y-m-d', fieldLabel : '上次通知日期' , columnWidth : .33}
	]
});
Ext.define('Foss.predeliver.noticeCustomer.HistoryPanelGrid', {
	extend : 'Ext.grid.Panel',
	title : '历史通知',
	height : 453,
	store : null,
	//frame: true,
	stripeRows : true,
	collapsible : true,
	viewConfig: {
		// 单元格可复制
        enableTextSelection: true
    },
	selType : 'rowmodel',
	columns : [{
		text : '时间',
		flex : 0.8,
		dataIndex : 'operateTime',
		xtype : 'datecolumn',
		format : 'Y-m-d H:i:s',
		xtype : 'ellipsiscolumn'
	}, {
		text : '收货人',
		flex : 1,
		dataIndex : 'consignee',
		xtype : 'ellipsiscolumn'
	}, {
		text : '手机号',
		flex : 0.8,
		dataIndex : 'mobile',
		xtype : 'ellipsiscolumn'
	}, {
		text : '派送方式',
		flex : 0.8,
		dataIndex : 'deliverType',
		xtype : 'ellipsiscolumn'
	}, {
		text : '通知方式',
		flex : 1,
		dataIndex : 'noticeType',
		xtype : 'ellipsiscolumn',
		renderer:function(value){
    		return FossDataDictionary.rendererSubmitToDisplay(value, predeliver.NOTIFY_TYPE);
		}
	}, {
		text : '通知结果',
		flex : 1,
		dataIndex : 'noticeResult',
		xtype : 'ellipsiscolumn',
		renderer:function(value){
    		return FossDataDictionary.rendererSubmitToDisplay(value, predeliver.NOTIFY_STATUS);
		}
	}, {
		text : '联系人',
		flex : 1,
		dataIndex : 'operateOrgName',
		xtype : 'ellipsiscolumn'
	}, {
		text : '短信内容',
		flex : 2,
		dataIndex : 'noticeContent',
		xtype : 'ellipsiscolumn'
	}, {
		text : '异常备注',
		flex : 2,
		dataIndex : 'exceptionNotes',
		xtype : 'ellipsiscolumn'
	}],
	constructor : function(config) {
		var me = this;
		Ext.apply(this, config);
		me.store = Ext.create('Ext.data.Store', {
			model: 'Foss.predeliver.model.HistoryPanel',
			data: []
		});
		this.callParent(arguments);
	}
});
/**
 * 设置发票相关信息的显示方式
 * 
 */
predeliver.notifyCustomer.setInvoice = function(form, isVisible) {
	var taxNo = form.findField('taxNo'), companyName = form.findField('companyName'), tel = form.findField('tel'),
		account = form.findField('account'), bank = form.findField('bank'), address = form.findField('address');
	// 税号
	taxNo.setVisible(isVisible);
	taxNo.allowBlank = !isVisible;
	// 公司名称
	companyName.setVisible(isVisible);
	companyName.allowBlank = !isVisible;
	// 电话
	tel.setVisible(isVisible);
	tel.allowBlank = !isVisible;
	// 帐号
	account.setVisible(isVisible);
	account.allowBlank = !isVisible;
	// 开户行
	bank.setVisible(isVisible);
	bank.allowBlank = !isVisible;
	// 地址
	address.setVisible(isVisible);
	address.allowBlank = !isVisible;
    
}

Ext.define('Foss.predeliver.noticeCustomer.NoticeInfoFormPanel', {
	extend : 'Ext.form.Panel',
	title : '通知信息',
	defaultType : 'textfield',
	defaults : {
		labelWidth : 100
	},
	flex : 1,
	frame: true,
	height : 475,
	layout : {type : 'table', columns: 2},
	viewNeedInvoice : function(form) {
		// 是否需要发票radio变更时
		var isNeedInvoice = form.findField('isNeedInvoice').getGroupValue(), isVisible = false, 
			receiveCustomerCode = form.findField('receiveCustomerCode').getValue(), 
			isBackFlg = form.findField('isBackFlg').getValue(), isReadOnly = true;
		
		if (isNeedInvoice == "Y") {
			isVisible = true;
			if (isBackFlg == 'N') {
				// 客户编码为空 或客户没有银行账户信息时
				isReadOnly = false;
			} 
		} else if (isNeedInvoice == "N") {
			isVisible = false;
			isReadOnly = false;
		}
		Ext.suspendLayouts();
		predeliver.notifyCustomer.setInvoice(form, isVisible);
		Ext.resumeLayouts(true);
		
		if (isReadOnly) {
			// 税号
			form.findField('taxNo').setReadOnly(isReadOnly);
			// 公司名称
			form.findField('companyName').setReadOnly(isReadOnly);
			// 电话
			form.findField('tel').setReadOnly(isReadOnly);
			// 帐号
			form.findField('account').setReadOnly(isReadOnly);
			// 开户行
			form.findField('bank').setReadOnly(isReadOnly);
			// 地址
			form.findField('address').setReadOnly(isReadOnly);
			// 存在客户编码
			var win = Ext.getCmp('Foss_baseinfo_commonSelector_customerWindow');
			if (Ext.isEmpty(win)) {
				win = Ext.create('Foss.baseinfo.commonSelector.CustomerWindow', {
					'customerCode' : receiveCustomerCode,
					 modal:true,
					 commitFun : function() {
					 	var record = win.getGridRecord();
					 	if (record.length == 1) {
					 		// 设置税号
					 		form.findField('taxNo').setValue(record[0].data.license);
					 		// 公司名称
					 		form.findField('companyName').setValue(record[0].data.name);
					 		// 电话
					 		form.findField('tel').setValue(record[0].data.mobilePhone);
					 		// 账号
					 		form.findField('account').setValue(record[0].data.accountNo);
					 		// 开户行
					 		form.findField('bank').setValue(record[0].data.openingBankName);
					 		// 地址
					 		form.findField('address').setValue(record[0].data.address);
					 	}
					}
				});
			}
			win.down('grid').store.removeAll();
			win.down('grid').setTitle('客户银行信息列表');
			win.show();
		}
		
	},
	viewNoticeTypeInfo : function(form) {
		// 通知方式radio变更时
		var noticeType = form.findField('noticeType').getGroupValue(), isVisible = false, 
			paidMethod = FossDataDictionary.rendererDisplayToSubmit(form.findField('paidMethod').getValue(), 'CLEARING_TYPE');
		if (noticeType == "TEL_NOTICE") {
			isVisible = true;
		} else {
			isVisible = false;
		}
		Ext.suspendLayouts();
		// 选择语音、短信通知方式时，通知状态、付款方式、是否需要发票、是否必须送货、送货日期、送货要求、小票费用、税号、公司名称、电话、账号、开户行、地址控件不可见
		form.findField('noticeResultName').setVisible(isVisible);
		form.findField('paymentTypeName').setVisible(isVisible);
		form.findField('isNeedInvoiceName').setVisible(isVisible);
		form.findField('isSentRequiredName').setVisible(isVisible);
		form.findField('deliverDate').setVisible(isVisible);
		form.findField('deliverRequire').setVisible(isVisible);
		form.findField('smallTicketFee').setVisible(isVisible);
		form.findField('isNeedInvoiceName').setValue({'isNeedInvoice' : 'N'});
		predeliver.notifyCustomer.setInvoice(form, false);
		//if (paidMethod != predeliver.FC) {
		// 付款方式不等于到付时，隐藏付款方式
		form.findField('paymentTypeName').setVisible(isVisible);
		//}
		Ext.resumeLayouts(true);
	},
	viewNoticeContent : function(form, controlName) {
		// 通知方式radio变更时，如果是短信、语音，自动获取模版对应内容，并不可编辑
		var noticeType = form.findField('noticeType').getGroupValue(), notificationEntity = '',
			isStorageChargeCheck = form.findField('isStorageCharge').getSubmitData(), notificationEntity = '',
			estimatedPickupTime = form.findField('estimatedPickupTime').getValue(),
			estimatedPickupTimeH = form.findField('estimatedPickupTimeH'), isStorageCharge = '', newVo = '';
			
		if (controlName == 'estimatedPickupTime') {
			// 判断输入小时，如果大于99，不处理
			if (estimatedPickupTime > 99) {
				return;
			}
			// 判断现在输入的值和未变更前的值是否相等
			if (estimatedPickupTimeH.getValue() == estimatedPickupTime) {
				return;
			}
			estimatedPickupTimeH.setValue(estimatedPickupTime);
		}
		if (noticeType == "TEL_NOTICE") {
			form.findField('noticeResultName').setValue({'noticeResult' : 'SUCCESS'});
			form.findField('noticeContent').setValue('通知成功！'); // 设置通知内容为默认值：“通知成功！”
			form.findField('noticeContent').setReadOnly(false);// 设置只读属性为false
			form.findField('noticeContent').setHeight(64);// 恢复通知内容多行文本框高度设置
			return;
		}
		// 是否征收保管费
		if (isStorageChargeCheck != null) {
			isStorageCharge = isStorageChargeCheck.isStorageCharge;
		} else {
			isStorageCharge = '';
		}
		notificationEntity = { 
			'waybillNo' : form.findField('waybillNo').getValue(),
			'noticeType' : noticeType,
			'isStorageCharge' : isStorageCharge,
			'estimatedPickupTime' : estimatedPickupTime,
			'arriveGoodsQty' : form.findField('arriveGoodsQty').getValue()
		};
		newVo = {
			'vo': {
				'conditionDto' : {'notificationEntity' :notificationEntity}
			}
		};
		Ext.Ajax.request({
		    // url: '../predeliver/queryNoticeContent.action',
		    url:predeliver.realPath('queryNoticeContent.action'),
		    jsonData: newVo,
		    success: function(response){
		    	var result = Ext.decode(response.responseText);
		    	// 设置通知内容的值、高度和只读属性
		    	form.findField('noticeContent').setValue(result.vo.conditionDto.notificationEntity.noticeContent);
		    	form.findField('noticeContent').setHeight(200);
		    	form.findField('noticeContent').setReadOnly(true);
		    },
		    exception: function(response) {
                var json = Ext.decode(response.responseText);
                Ext.ux.Toast.msg('变更通知方式失败', json.message, 'error', 2000);
            }
		});
	},
	viewNoticeResult : function(form) {
		// 通知结果radio变更时
		var noticeResult = form.findField('noticeResult').getGroupValue();
		Ext.suspendLayouts();
		if (noticeResult == predeliver.FAILURE) {
			form.findField('noticeContent').setValue('');
		} else {
			form.findField('noticeContent').setValue('通知成功！');
		}
		Ext.resumeLayouts(true);
	},
	dockedItems : [{
		xtype : 'toolbar',
		dock : 'bottom',
		items : [{
			xtype : 'tbspacer',
			flex : 1
		}, {
			xtype : 'button',
			text : '保存',
			plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin', {
				seconds: 3
			}),
			handler : function() {
				var form = this.up('form').getForm(), values = form.getValues();
				if (values.isNeedInvoice == 'Y') {
					// 添加合同客户的发票必须选择的验证
					if (Ext.isEmpty(values.taxNo) && Ext.isEmpty(values.companyName) &&  Ext.isEmpty(values.tel) &&
						Ext.isEmpty(values.account) && Ext.isEmpty(values.bank) &&  Ext.isEmpty(values.address)) {
						Ext.ux.Toast.msg('提示', '合同客户必须选择银行信息', 'error', 2000);
						return;
					}
				}
				if (form.isValid()) {
					
					var array = {'vo':{'conditionDto':{'notificationEntity':values,'invoiceInfomationEntity':values}}};
					Ext.Ajax.request({
					    // url: '../predeliver/addNotifyCustomer.action',
					    url:predeliver.realPath('addNotifyCustomer.action'),
					    jsonData: array,
					    success: function(response){
					    	Ext.ux.Toast.msg('提示', '提交成功！', 'ok', 1000);
					    },
					    exception: function(response) {
			                var json = Ext.decode(response.responseText);
			                Ext.ux.Toast.msg('保存失败', json.message, 'error', 2000);
		                }
					});
				}
			}
		}]
	}],
	items : [
		{name : 'receiveCustomerContact' , fieldLabel : '联系人' , readOnly : true} , 
		{name : 'deliverDate' , xtype : 'datefield' , minValue:new Date() , format: 'Y-m-d' , editable:false, invalidText:'输入的日期格式不对' , altFormats: 'Y , m , d|Y.m.d' , fieldLabel : '送货日期'} , 
		{name : 'customerQulification' , fieldLabel : '客户资质', readOnly : true} , 
		{name : 'deliverRequire' , fieldLabel : '送货要求', maxLength : 500} , 
		{name : 'creditAmount' , fieldLabel : '可用信用额度', readOnly : true} , 
		{name : 'smallTicketFee' , fieldLabel : '小票费用' , xtype: 'numberfield',hideTrigger : true,
		maxValue: 99999999, minValue: 1, allowDecimals : false } , 
		{name : 'receiveCustomerMobilephone' , fieldLabel : '联系方式', readOnly : true} , 
		{xtype : 'container' , layout : 'fit' , 
			items : [{
				xtype : 'radiogroup' , vertical : true , fieldLabel : '通知方式' , name : 'noticeTypeName',
				listeners : { 
					change:function(_this, newValue, oldValue, eOpts){
						this.up('panel').viewNoticeContent(this.up('form').getForm(), '');
						this.up('panel').viewNoticeTypeInfo(this.up('form').getForm());
					}
				},
				items : [
					{boxLabel : '语音' , name : 'noticeType' , inputValue : 'VOICE_NOTICE' , itemId:'VOICE_NOTICE'} , 
					{boxLabel : '短信' , name : 'noticeType' , inputValue : 'SMS_NOTICE' , itemId:'SMS_NOTICE'} ,  
					{boxLabel : '电话' , name : 'noticeType' , inputValue : 'TEL_NOTICE', itemId:'TEL_NOTICE' , checked : true}
				]}
		]} , 
		{name : 'toPayAmount' , fieldLabel : '到付总额', readOnly : true} , 
		{xtype : 'checkbox' , name : 'isStorageCharge' , margin: '0 0 0 9', boxLabel : '是否征收保管费', inputValue : 'Y'}, 
		{name : 'arriveGoodsQty' , fieldLabel : '件数' , readOnly : true} , 
		{xtype : 'container' ,  layout: 'column',
			items : [
				{xtype : 'label' , text : '预计', margin: '4 0 0 9'} , 
				{xtype : 'numberfield' , name : 'estimatedPickupTime', width : 30, hideTrigger : true, maxValue: 999, 
					minValue: 1, maxLength :2, allowDecimals : false, msgTarget: 'qtip',
					listeners : { 
							blur : function (_this, The, eOpts) {
								this.up('panel').viewNoticeContent(this.up('form').getForm(), 'estimatedPickupTime');
							}
						}
				} , 
				{xtype : 'label',  text : '小时后提货', margin: '4 0 0 0'}
			]
		} , 
		{name : 'goodsWeightTotal' , fieldLabel : '重量' ,readOnly : true} , 
		{name : 'noticeContent' , fieldLabel : '通知内容<br>(失败原因)' ,allowBlank : false, 
			rowspan : 3, xtype : 'textarea', value : '通知成功！', maxLength : 500,
			blankText : '通知内容(失败原因)不能为空',
			listeners : { 
				focus:function(_this, The, eOpts){
					var noticeResult = this.up('form').getForm().findField('noticeResult').getGroupValue();
					if (noticeResult == predeliver.SUCCESS && this.getValue() == '通知成功！') {
						this.setValue('');
					}
				}
			}
		}, 
		
		{xtype : 'container',
			items : 
			[{xtype : 'radiogroup',vertical : true,name : 'noticeResultName', defaultType:'radio',fieldLabel : '通知状态',labelStype : {width : '70px'},
				listeners : { 
					change:function(_this, newValue, oldValue, eOpts){
						this.up('panel').viewNoticeResult(this.up('form').getForm());
					}
				},
				items : [
					{boxLabel : '通知成功',name : 'noticeResult',inputValue : 'SUCCESS', checked : true},
					{boxLabel : '通知失败',name : 'noticeResult',inputValue : 'FAILURE'}
				]}
			]},
		{xtype : 'container',
			items : [
				{xtype : 'radiogroup',vertical : true,name : 'paymentTypeName',fieldLabel : '付款方式',labelStype : {width : '70px'},
//					listeners : { 
//						change:function(_this, newValue, oldValue, eOpts){
//							this.up('panel').viewNoticeTypeInfo(this.up('form').getForm());
//						}
//					},
					items : [
						{boxLabel : '到付',name : 'paymentType',inputValue : 'CH', itemId:'CH', checked : true},
						{boxLabel : '月结',name : 'paymentType',inputValue : 'CT', itemId:'CT',  disabled : true},
						{boxLabel : '临欠',name : 'paymentType',inputValue : 'DT', itemId:'DT', disabled : true}
					]}
				]},
		{xtype : 'container',layout : 'fit',
			items : [
				{
					xtype : 'radiogroup',
					vertical : true, 
					name : 'isNeedInvoiceName', 
					defaultType:'radio',
					fieldLabel : '是否需要发票',
					items : [
						{boxLabel : '是',name : 'isNeedInvoice',itemId:'yes',inputValue : 'Y'},
						{boxLabel : '否',name : 'isNeedInvoice',itemId:'no',inputValue : 'N',checked : true}
					]}
			]},
		{xtype : 'container',layout : 'fit',
			items : [
				{xtype : 'radiogroup',vertical : true,name : 'isSentRequiredName', defaultType:'radio',fieldLabel : '是否必送货',
				items : [
					{boxLabel : '是',name : 'isSentRequired',inputValue : 'Y'},
					{boxLabel : '否',name : 'isSentRequired',inputValue : 'N',checked : true}
				]}
			]},
		{name : 'taxNo' , fieldLabel : '税号', allowBlank : false, maxLength : 65} , 
		{name : 'companyName' , fieldLabel : '公司名称', allowBlank : false, maxLength : 65} , 
		{name : 'tel' , fieldLabel : '电话', allowBlank : false, maxLength : 15} , 
		{name : 'account' , fieldLabel : '帐号', allowBlank : false, maxLength : 65} , 
		{name : 'bank' , fieldLabel : '开户行',colspan : 2, allowBlank : false, maxLength : 65} , 
		{name : 'address' , fieldLabel : '地址',colspan : 2,width : 430, allowBlank : false, maxLength : 65} , 
		{name : 'waybillNo' , xtype : 'hiddenfield'} , 
		{name : 'receiveMethod' , xtype : 'hiddenfield'},
		{name : 'receiveCustomerCode' , xtype : 'hiddenfield'},
		{name : 'createOrgCode' , xtype : 'hiddenfield'},
		{name : 'estimatedPickupTimeH' , xtype : 'hiddenfield'},
		{name : 'paidMethod' , xtype : 'hiddenfield'},
		{name : 'isBackFlg' , xtype : 'hiddenfield'}
	],
	listeners: {
		'boxready': function( component, width, height, eOpts ){
			// 添加是否需要发票--选择”是"的时候click事件
			component.getForm().findField('isNeedInvoiceName').getComponent('yes').getEl().on('click',function(event, element, eOpts){
				component.viewNeedInvoice(component.getForm());
			});
			// 添加是否需要发票--选择"否"的时候click事件
			component.getForm().findField('isNeedInvoiceName').getComponent('no').getEl().on('click',function(event, element, eOpts){
				component.viewNeedInvoice(component.getForm());
			});
		}
	}
});
Ext.define('Foss.predeliver.panel.NoticeCustomerInfoPanel', {
	extend : 'Ext.container.Container',
	layout : {
		type : 'hbox'
	},
	transportInfoForm : null,
	historyPanel : null,
	noticeTabPanel : null,
	constructor : function(config) {
		var me = this;
		Ext.apply(this, config);
		this._initCompoment();
		this.items = [this.noticeTabPanel, this.noticeInfoForm];
		me.callParent(arguments);
	},
	_initCompoment : function() {
		this.getTransportInfoForm();
		this.getNoticeInfoForm();
		this.getHistoryPanel();
		this._initNoticeTabPanel();
	},
	getTransportInfoForm : function() {
		if (!this.transportInfoForm) {
			this.transportInfoForm = Ext.create('Foss.predeliver.noticeCustomer.TransportInfoFormPanel',{
				tabConfig : {
					width : 100
				}
			});
		}

		return this.transportInfoForm;
	},
	getNoticeInfoForm : function() {
		if (!this.noticeInfoForm) {
			this.noticeInfoForm = Ext.create('Foss.predeliver.noticeCustomer.NoticeInfoFormPanel',{
				tabConfig : {
					width : 100
				}
			});
		}
		return this.noticeInfoForm;
	},
	getHistoryPanel : function() {
		if (!this.historyPanel) {
			this.historyPanel = Ext.create('Foss.predeliver.noticeCustomer.HistoryPanelGrid',{
				tabConfig : {
					width : 100
				}
			});
		}

		return this.historyPanel;
	},
	_initNoticeTabPanel : function() {
		if (!this.noticeTabPanel) {
			this.noticeTabPanel = Ext.create('Ext.tab.Panel', {
				bodyCls: 'autoHeight',
				cls: 'innerTabPanel',
				flex : 1,
				items : [this.transportInfoForm, this.historyPanel]
			});
		}
	},
	bindData : function(record, grid, rowBodyElement) {
		var transportInfoForm = this.transportInfoForm;
		var noticeInfoForm = this.noticeInfoForm;
		setFormEditAble(transportInfoForm, false);
		// 绑定表格数据到表单上
		Ext.Ajax.request({
			// url: '../predeliver/queryWaybillInfo.action',
			url:predeliver.realPath('queryWaybillInfo.action'),
			params: {
				'vo.conditionDto.waybillNo': record.get('waybillNo'),
				'vo.conditionDto.notifyCustomerDto.selectType': record.get('selectType'),
				'vo.conditionDto.notifyCustomerDto.arriveGoodsQty': record.get('arriveGoodsQty'),
				'vo.conditionDto.notifyCustomerDto.stockQty': record.get('stockQty'),
				'vo.conditionDto.notifyCustomerDto.handoverGoodsQty': record.get('handoverGoodsQty'),
				'vo.conditionDto.notifyCustomerDto.taskStatus': record.get('taskStatus')
			},
			success: function(response){
				var result = Ext.decode(response.responseText),
					 model = Ext.ModelManager.create(result.vo.conditionDto.notifyCustomerDto,'Foss.predeliver.model.NoticeCustomer'),
					 transportInfoFormT = transportInfoForm.getForm(),
					 noticeInfoFormT = noticeInfoForm.getForm(),
					 paymentTypeGroup = noticeInfoFormT.findField('paymentTypeName');
				transportInfoFormT.loadRecord(model);
				noticeInfoFormT.loadRecord(model);
				rowBodyElement.getHistoryPanel().getStore().loadData(result.vo.conditionDto.notificationEntityList);
				Ext.suspendLayouts();
				// 动态显示运行信息页面元素
				var selectType = model.data.selectType, // 查询类型
					paidMethod = FossDataDictionary.rendererDisplayToSubmit(model.data.paidMethod, predeliver.PAYMENTMODE), // 运单付款方式
					customerQulification = model.data.customerQulification;// 客户付款方式
				if (selectType == '0') {
					transportInfoFormT.findField('stockStatus').setValue("库存中");
				} else {
					// 根据交接单号、车次号、预计到货时间显示的结果内容
					transportInfoFormT.findField('stockStatus').setFieldLabel("&nbsp;&nbsp;开单件数");
					transportInfoFormT.findField('stockStatus').setValue(model.data.goodsQtyTotal);
					transportInfoFormT.findField('goodsQtyTotal').setFieldLabel("&nbsp;&nbsp;到达件数");
					transportInfoFormT.findField('goodsQtyTotal').setValue(model.data.arriveGoodsQty);
					transportInfoFormT.findField('arriveGoodsQty').setFieldLabel("&nbsp;&nbsp;交接单件数");
					transportInfoFormT.findField('arriveGoodsQty').setValue(model.data.handoverGoodsQty);
					transportInfoFormT.findField('stockQty').setFieldLabel("");
					transportInfoFormT.findField('stockQty').setValue("");
					transportInfoFormT.findField('storageDay').setFieldLabel("");
					transportInfoFormT.findField('storageDay').setValue("");
					transportInfoFormT.findField('storageCharge').setFieldLabel("");
					transportInfoFormT.findField('storageCharge').setValue("");
				}
				// 初始化通知信息页面元素
				if (selectType == "1") {
					// 按照车次、交接单、预计到达时间查询，如果车辆状态不是已到达，营业员可以通过电话提前通知,不能通过语音或者短信通知客户。
					var taskStatus = model.data.taskStatus;
					if (taskStatus != predeliver.ARRIVED) {
						var noticeTypeName = noticeInfoFormT.findField('noticeTypeName');
						noticeTypeName.getComponent('VOICE_NOTICE').setDisabled(true);
						noticeTypeName.getComponent('SMS_NOTICE').setDisabled(true);
					}
					// 设置通知信息上的件数为交接单件数
					noticeInfoFormT.findField('arriveGoodsQty').setValue(model.data.handoverGoodsQty);
				} else {
					// 设置通知信息上的件数为库存件数
					noticeInfoFormT.findField('arriveGoodsQty').setValue(model.data.stockQty);
				}
				// 取消付款方式的验证
				//if (paidMethod != predeliver.FC) {
					// 运单的付款方式不等于到付，隐藏付款方式
				//	paymentTypeGroup.setVisible(false);
				//} else {
				if (customerQulification != null && (customerQulification == predeliver.CLEARING_TYPE_MONTH || customerQulification == predeliver.CLEARING_TYPE_HALF_MONTH)) {
					//客户资质为半月结、月结时，显示到付、月结，默认显示月结
					paymentTypeGroup.setVisible(true);
					paymentTypeGroup.getComponent('CT').setDisabled(false);
					paymentTypeGroup.setValue({'paymentType' : 'CT'});
				} else {
					// 客户资质为空或非月结时，只显示到付
				}
				//}
				// 设置页面客户资质显示
				noticeInfoFormT.findField('customerQulification').setValue(FossDataDictionary.rendererSubmitToDisplay(model.data.customerQulification, 'CLEARING_TYPE'));
				// 初始化时，通知信息发票内容自动隐藏
				predeliver.notifyCustomer.setInvoice(noticeInfoFormT, false);
				Ext.resumeLayouts(true);
			}
		});
	}
});

Ext.onReady(function() {
	Ext.QuickTips.init();
	if (Ext.getCmp('T_predeliver-notifyCustomer_content')) {
		return;
	}
	var queryForm = Ext.create('Foss.predeliver.form.NoticeCustomerSearch'), noticeCustomerGridPanel = Ext
			.create('Foss.predeliver.grid.NoticeCustomerGrid');
	predeliver.notifyCustomer.queryform = queryForm;
	Ext.create('Ext.panel.Panel', {
		id : 'T_predeliver-notifyCustomerIndex_content',
		cls : 'panelContentNToolbar',
		bodyCls : 'panelContentNToolbar-body',
		layout : 'auto',
		items : [queryForm, noticeCustomerGridPanel],
		renderTo : 'T_predeliver-notifyCustomerIndex-body'
	});
});
