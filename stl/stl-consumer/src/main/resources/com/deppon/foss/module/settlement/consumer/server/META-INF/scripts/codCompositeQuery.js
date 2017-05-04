//由于平台正则BUG，为了加载message
consumer.billPaidCodIndex.i18n('foss.stl.consumer.cod.startTimeEndTimeAreNotMoreThanDays');

/**
 * 请求超时时间
 */
consumer.billPaidCodIndex.AJAX_TIMEOUT = 2*60*60; //默认2小时

// 日期对象转换
consumer.dateConvert = function(value, record) {
	return stl.longToDateConvert(value);
}

// 日期渲染
consumer.renderDate = function(value, format) {
	return stl.dateFormat(value, format);
}

consumer.convertProductCode = function(productCodes) {
	if (!Ext.isEmpty(productCodes)) {
		var productCodeList = [];
		for ( var i = 0; i < productCodes.length; i++) {
			// 如果产品类型中存在值为空，则表明为全部，那么默认全部查询
			if (Ext.isEmpty(productCodes[i])) {
				productCodeList = [];
				break;
			} else {
				productCodeList.push(productCodes[i]);
			}
		}
		return productCodeList;
	} else {
		return [];
	}
}

// 设置查询条件
consumer.setBillPaidCodQueryParam = function(from){
	var waybillQueryFrom = Ext.getCmp('Foss_consumer_BillPaidCodFormByNumber_ID');
	var dateQueryForm = Ext.getCmp('Foss_consumer_BillPaidCodFormByDate_ID');
	var mergeCodeQueryFrom = Ext.getCmp('Foss_consumer_BillPaidCodFormByMergeCode_ID');
	
	var params = {};
	// 获得当前页签
	var tabPanel = Ext.getCmp('Foss_consumer_BillPaidTablePanel_ID');
	var activePanel = tabPanel.getActiveTab();
	
	var queryType = null;
	// 判断查询类别
	if (activePanel.down('form').getId() == waybillQueryFrom.getId()) {
		queryType = 'queryByWaybillNo';
		Ext.apply(params, waybillQueryFrom.getForm().getValues());
	} else if(activePanel.down('form').getId() == mergeCodeQueryFrom.getId()){
		queryType = 'queryByMergeCode';
		Ext.apply(params, mergeCodeQueryFrom.getForm().getValues());	
	}else {
		queryType = 'queryByDate';
		var inceptCodAmount = dateQueryForm.getForm().findField(
				'vo.queryVo.inceptCodAmount').getValue();
		var endCodAmount = dateQueryForm.getForm().findField(
				'vo.queryVo.endCodAmount').getValue();
		if (inceptCodAmount == null) {
			dateQueryForm.getForm().findField(
					'vo.queryVo.inceptCodAmount').setValue(0);
		}
		if (endCodAmount == null) {
			dateQueryForm.getForm()
					.findField('vo.queryVo.endCodAmount').setValue(0);
		}
		
		// 验证时间
		// 时间 范围默认限制7天
		var max_diff_days = 7;
		var startTime = dateQueryForm.getForm().findField('vo.queryVo.inceptBizDateStr').getValue();
		var endTime = dateQueryForm.getForm().findField('vo.queryVo.endBizDateStr').getValue();
		if(!Ext.isEmpty(startTime)&&!Ext.isEmpty(endTime)){
			//开单 
			if(startTime > endTime){
				Ext.Msg.alert(consumer.billPaidCodIndex.i18n('foss.stl.consumer.common.warmTips'),"开单"+consumer.billPaidCodIndex.i18n('foss.stl.consumer.cod.startTime')+consumer.billPaidCodIndex.i18n('foss.stl.consumer.cod.cannotGreaterThan')+consumer.billPaidCodIndex.i18n('foss.stl.consumer.cod.endTime'));
				return false;
			}
			
			//如果如果存在收款人，则开单时间的范围可以扩大到15天
			var vPayeename = dateQueryForm.getForm().findField('vo.queryVo.payeeName').getValue();
			if(!Ext.isEmpty(vPayeename)){
				max_diff_days = 15;
			}
			if(stl.compareTwoDate(startTime,endTime) > max_diff_days){
				Ext.Msg.alert(consumer.billPaidCodIndex.i18n('foss.stl.consumer.common.warmTips'),"开单"+consumer.billPaidCodIndex.i18n('foss.stl.consumer.cod.startTimeEndTimeAreNotMoreThanDays',[max_diff_days]));
				return false;
			}
		}else{
			startTime = dateQueryForm.getForm().findField('vo.queryVo.inceptSignDateStr').getValue();
			endTime = dateQueryForm.getForm().findField('vo.queryVo.endSignDateStr').getValue();
			if(!Ext.isEmpty(startTime)&&!Ext.isEmpty(endTime)){
				//签收 
				if(startTime > endTime){
					Ext.Msg.alert(consumer.billPaidCodIndex.i18n('foss.stl.consumer.common.warmTips'),"签收"+consumer.billPaidCodIndex.i18n('foss.stl.consumer.cod.startTime')+consumer.billPaidCodIndex.i18n('foss.stl.consumer.cod.cannotGreaterThan')+consumer.billPaidCodIndex.i18n('foss.stl.consumer.cod.endTime'));
					return false;
				}
				
				if(stl.compareTwoDate(startTime,endTime) > max_diff_days){
					Ext.Msg.alert(consumer.billPaidCodIndex.i18n('foss.stl.consumer.common.warmTips'),"签收"+consumer.billPaidCodIndex.i18n('foss.stl.consumer.cod.startTimeEndTimeAreNotMoreThanDays',[max_diff_days]));
					return false;
				}
			}else{
				startTime = dateQueryForm.getForm().findField('vo.queryVo.inceptPaymentDateStr').getValue();
				endTime = dateQueryForm.getForm().findField('vo.queryVo.endPaymentDateStr').getValue();
				if(!Ext.isEmpty(startTime)&&!Ext.isEmpty(endTime)){
					//付款 
					if(startTime > endTime){
						Ext.Msg.alert(consumer.billPaidCodIndex.i18n('foss.stl.consumer.common.warmTips'),"付款"+consumer.billPaidCodIndex.i18n('foss.stl.consumer.cod.startTime')+consumer.billPaidCodIndex.i18n('foss.stl.consumer.cod.cannotGreaterThan')+consumer.billPaidCodIndex.i18n('foss.stl.consumer.cod.endTime'));
						return false;
					}
					
					if(stl.compareTwoDate(startTime,endTime) > max_diff_days){
						Ext.Msg.alert(consumer.billPaidCodIndex.i18n('foss.stl.consumer.common.warmTips'),"付款"+consumer.billPaidCodIndex.i18n('foss.stl.consumer.cod.startTimeEndTimeAreNotMoreThanDays',[max_diff_days]));
						return false;
					}
				}else{
				
					startTime = dateQueryForm.getForm().findField('vo.queryVo.inceptRefundSuccessDateStr').getValue();
					endTime = dateQueryForm.getForm().findField('vo.queryVo.endRefundSuccessDateStr').getValue();
					if(!Ext.isEmpty(startTime)&&!Ext.isEmpty(endTime)){
						//汇款成功 
						if(startTime > endTime){
							Ext.Msg.alert(consumer.billPaidCodIndex.i18n('foss.stl.consumer.common.warmTips'),"汇款成功"+consumer.billPaidCodIndex.i18n('foss.stl.consumer.cod.startTime')+consumer.billPaidCodIndex.i18n('foss.stl.consumer.cod.cannotGreaterThan')+consumer.billPaidCodIndex.i18n('foss.stl.consumer.cod.endTime'));
							return false;
						}
						
						if(stl.compareTwoDate(startTime,endTime) > max_diff_days){
							Ext.Msg.alert(consumer.billPaidCodIndex.i18n('foss.stl.consumer.common.warmTips'),"汇款成功"+consumer.billPaidCodIndex.i18n('foss.stl.consumer.cod.startTimeEndTimeAreNotMoreThanDays',[max_diff_days]));
							return false;
						}
					}
				}
			}
		}
		
		
		Ext.apply(params, dateQueryForm.getForm().getValues());
		
			Ext.apply(params, {
			'vo.queryVo.productCodeList' : consumer.convertProductCode(dateQueryForm.getForm().findField('vo.queryVo.productCodeList').getValue())
			});
	
	}
	
	var orgCodeQuery = dateQueryForm.getForm().findField('vo.queryVo.payableOrgCode').queryOrgCode;
	if (orgCodeQuery != ''){
	Ext.apply(params, {
		'vo.queryVo.payableOrgCode' :orgCodeQuery
	
	});
	}
	// 对查询类别进行赋值
	Ext.apply(params, {
	'vo.queryVo.queryType' : queryType
	});
	
	return params;
}

// 查询代收货款数据
consumer.billPaidCodQuery = function(form) {
	form = form.getForm();
	if (form.isValid()) {
		// 得到Store
		var grid = Ext.getCmp('FOSS_Consumer_BillPaidCODQueryGrid_ID');
		var store = grid.store;
		if (store) {
			params = consumer.setBillPaidCodQueryParam(form);
			if(params!=null && params ==false){
				return false;
			}
			// 设置查询参数
			grid.store.setSubmitParams(params);
			
			// 加载第一页数据
			store.loadPage(1,{
						callback : function(records, operation, success) {
							var rawData = store.proxy.reader.rawData;
							if (!success && !rawData.isException) {
								Ext.Msg.alert(consumer.billPaidCodIndex.i18n('foss.stl.consumer.common.warmTips'), rawData.message);
								return false;
							}
						}
					});
		}
		grid.show();
	}
	// 如果查询条件不合法
	else {
		Ext.Msg.alert(consumer.billPaidCodIndex.i18n('foss.stl.consumer.common.warmTips'), consumer.billPaidCodIndex.i18n('foss.stl.consumer.cod.pleaseCheckTheInputConditionIsLegal'));
	}
}

/**
 * 导出execl
 */
consumer.exportCod = function(){
	//获取主面板、查询GRID
	var mainPane = Ext.getCmp('T_consumer-billPaidCodIndex_content');
	var queryForm = mainPane.getQueryFormPanel();
	var queryGrid = mainPane.getBillPaidCodQueryGridId();
	
	//导出Excel的最大条数超过5万条
	if(queryGrid.store.totalCount>stl.EXPORT_EXCEL_MAX_COUNTS){
		Ext.Msg.alert(consumer.billPaidCodIndex.i18n('foss.stl.consumer.common.warmTips'),"导出Excel的最大条数超过5万条，请重新筛选条件！");
		return false;
	}
	
	//提示是否导出
	Ext.Msg.confirm(consumer.billPaidCodIndex.i18n('foss.stl.consumer.common.warmTips'),'确定导出代收货款综合报表吗?',function(btn,text){
		if('yes' == btn){
			
			var params = consumer.setBillPaidCodQueryParam(null);
			if(params!=null && params ==false){
				return false;
			}
			//创建一个form
			if(!Ext.fly('exportCodForm')){
				var frm = document.createElement('form');
				frm.id = 'exportCodForm';
				frm.style.display = 'none';
				document.body.appendChild(frm);
			}
			
			//导出Ajax请求
			Ext.Ajax.request({
				timeout: consumer.billPaidCodIndex.AJAX_TIMEOUT*1000,
				url:consumer.realPath('exprotBillPaidCod.action'), 
				form: Ext.fly('exportCodForm'),
				params:params,
				method:'post',
				isUpload: true,
				success:function(response){
					//获取响应的json字符串
					var jsonText = Ext.decode(response.responseText.trim());
                   	//导出失败
                   	if(jsonText.message!=null&&jsonText.message!=''){
                     	Ext.Msg.alert(consumer.billPaidCodIndex.i18n('foss.stl.consumer.common.warmTips'),jsonText.message);
                     }
				},
				failure:function(response){
					var json = Ext.decode(response.responseText);
					Ext.MessageBox.alert(consumer.billPaidCodIndex.i18n('foss.stl.consumer.common.warmTips'), jsonText.message);
				},
				exception : function(response) {
					var json = Ext.decode(response.responseText);
					Ext.MessageBox.alert(consumer.billPaidCodIndex.i18n('foss.stl.consumer.common.warmTips'), jsonText.message);
				}
		    });
			
		}
	});	
}

/**
 * 
 */
consumer.alertQueryLog = function(waybillNo) {
	if(!Ext.isEmpty(waybillNo)){
		Ext.Ajax.request({
			timeout: consumer.billPaidCodIndex.AJAX_TIMEOUT*1000,
			url:consumer.realPath('queryBillCodLog.action'),
			params:{
				'vo.queryVo.waybillNo':waybillNo
			},
			success:function(response){
				var result = Ext.decode(response.responseText);
				var list = result.vo.logVoSet;
				var grid = Ext.getCmp('T_consumer-billPaidCodIndex_content').getBillPaidCodQueryGridId();
				var win = grid.billPaidCodLog;
				win.items.items[0].store.loadData(list);
				win.show();
			},
			exception:function(response){
				var result = Ext.decode(response.responseText);
				Ext.Msg.alert(consumer.billPaidCodIndex.i18n('foss.stl.consumer.common.warmTips'),result.message);
			}			
		});
	}
	
}
// 代收货款综合查询列表定义
Ext.define('FOSS.Consumer.BillPaidCodDataModel', {
	extend : 'Ext.data.Model',
	idgen: 'uuid',//EXT在前台为每个模型额外以UUID方式生成主键
	idProperty : 'extid',//以上生成的主键用在名叫“extid”的列
	// 结算类别、应付部门、运单单号，合并编号、代收货款开单金额、有效状态
	// 核销部分金额、应退金额、代收货款手续费、发货客户、收款人、代收货款状态
	// 收款帐号、开户银行、开单时间、签收时间
	// 汇款导出时间、付款成功时间、退款失败原因、汇款失败原因
	fields : ['extid','id', 'codType', 'payableOrgName', 'waybillNo','mergeCode','productCode' , 'codAmount','active',
			'verifyAmount', 'payeeAmount','codFee', 'consignee', 'payeeName', 'status',
			'payeeAccount', 'bank','bankBranchName', {
				name : 'businessDate',
				type : 'date',
				convert : consumer.dateConvert
			},
			{
				name : 'codExportTime',
				type : 'date',
				convert : consumer.dateConvert
			},
			 {
				name : 'signDate',
				type : 'date',
				convert : consumer.dateConvert
			},
			 {
				name : 'cashConfirmTime',
				type : 'date',
				convert : consumer.dateConvert
			}, {
				name : 'tusyorgRfdApptime',
				type : 'date',
				convert : consumer.dateConvert
			}, {
				name : 'refundSuccessTime',
				type : 'date',
				convert : consumer.dateConvert
			}, 'remittanceFailNotes','batchNo','codBatchStatus','failNotes' ]
});

// 代收货款类别
/*Ext.define('Foss.consumer.codTypeStore', {
	extend : 'Ext.data.Store',
	fields : [ {
		name : 'code',
		type : 'string'
	}, {
		name : 'name',
		type : 'string'
	} ],
	data : {
		'items' : [ {
			code : '',
			name : '所有'
		}, {
			code : 'R1',
			name : '即日退'
		}, {
			code : 'R3',
			name : '三日退'
		}, {
			code : 'RA',
			name : '审核退'
		} ]
	},
	proxy : {
		type : 'memory',
		reader : {
			type : 'json',
			root : 'items'
		}
	}
});*/

// 退款路径
/*Ext.define('Foss.consumer.refundPathStore', {
	extend : 'Ext.data.Store',
	fields : [ {
		name : 'code',
		type : 'string'
	}, {
		name : 'name',
		type : 'string'
	} ],
	data : {
		'items' : [ {
			code : '',
			name : '所有'
		}, {
			code : 'OFFL',
			name : '线下付款'
		}, {
			code : 'ONL',
			name : '线上付款'
		} ]
	},
	proxy : {
		type : 'memory',
		reader : {
			type : 'json',
			root : 'items'
		}
	}
});*/

// 退款路径
/*Ext.define('Foss.consumer.codStatusStore', {
	extend : 'Ext.data.Store',
	fields : [ {
		name : 'code',
		type : 'string'
	}, {
		name : 'name',
		type : 'string'
	} ],
	data : {
		'items' : [ {
			code : '',
			name : '所有'
		}, {
			code : 'NR',
			name : '未退款'
		}, {
			code : 'AG',
			name : '待审核'
		}, {
			code : 'SF',
			name : '营业部冻结'
		}, {
			code : 'CA',
			name : '收银员审核'
		}, {
			code : 'FF',
			name : '资金部冻结'
		}, {
			code : 'RG',
			name : '退款中'
		}, {
			code : 'RFA',
			name : '退款失败申请'
		}, {
			code : 'NRS',
			name : '反汇款成功'
		}, {
			code : 'RF',
			name : '退款失败'
		}, {
			code : 'RD',
			name : '已退款'
		} ]
	},
	proxy : {
		type : 'memory',
		reader : {
			type : 'json',
			root : 'items'
		}
	}
});*/

// 定义Store
Ext.define('FOSS.Consumer.BillPaidCodStore', {
	extend : 'Ext.data.Store',
	model : 'FOSS.Consumer.BillPaidCodDataModel',
	pageSize : 100,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		timeout: consumer.billPaidCodIndex.AJAX_TIMEOUT*1000,
		url : consumer.realPath('queryBillPaidCod.action'),
		reader : {
			type : 'json',
			root : 'vo.gridVoSet',
			totalProperty : 'totalCount'
		}
	},
	submitParams: {},
	setSubmitParams: function(submitParams){
		this.submitParams = submitParams;
	},
	constructor:function(config){
		var me = this, 
			cfg = Ext.apply({}, config);
		me.listeners = {
	   		'beforeload': function(store, operation, eOpts){
	   			Ext.apply(me.submitParams, {
			          "limit":operation.limit,
			          "page":operation.page,
			          "start":operation.start
			          }); 
	   			Ext.apply(operation, {
	   				params : me.submitParams
	   			});
	   		} 
		};
		me.callParent([ cfg ]);
	} 
});

/**
 * 代收货款日志
 */
Ext.create('Ext.data.Store', {
	storeId : 'codLogStore',
	fields : [ 'waybillNo', 'operateContent','operateTime', 'operateActiontype','operatorCode','operatorName','operateOrgCode','operateOrgName','operatorIp'],
	proxy : {
		type : 'memory',
		reader : {
			type : 'json',
			root : 'items'
		}
	}
});

/**
 * 日志列表
 */
Ext.define('Foss.consumer.BillPaidCodLogGrid', {
	height:600,
	extend : 'Ext.grid.Panel',
	store : Ext.data.StoreManager.lookup('codLogStore'),
	columns : [ {
		header : consumer.billPaidCodIndex.i18n('foss.stl.consumer.cod.wayBillNo'),
		dataIndex : 'waybillNo'
	}, {
		header : consumer.billPaidCodIndex.i18n('foss.stl.consumer.cod.operateContent'),
		width : 200,
		dataIndex : 'operateContent'
	}, {
		header : consumer.billPaidCodIndex.i18n('foss.stl.consumer.cod.operateActiontype'),
		dataIndex : 'operateActiontype',
		renderer : function(value) {
			var displayField = FossDataDictionary.rendererSubmitToDisplay(value,'COD_LOG__OPERATE_TYPE');
			return displayField;
		}
	}, {
		header : consumer.billPaidCodIndex.i18n('foss.stl.consumer.cod.operateIp'),
		dataIndex : 'operatorIp'
	} ,{
		header : consumer.billPaidCodIndex.i18n('foss.stl.consumer.cod.operateTime'),
		dataIndex : 'operateTime',
		width : 160,
		renderer : function(value) {
			return consumer.renderDate(value, 'Y-m-d G:i');}
	}, {
		header : consumer.billPaidCodIndex.i18n('foss.stl.consumer.cod.operatorCode'),
		dataIndex : 'operatorCode'
	} ,
	 {
		header : consumer.billPaidCodIndex.i18n('foss.stl.consumer.cod.operatorName'),
		dataIndex : 'operatorName'
	} ,
	 {
		header : consumer.billPaidCodIndex.i18n('foss.stl.consumer.cod.operateOrgCode'),
		dataIndex : 'operateOrgCode'
	} ,
	 {
		header : consumer.billPaidCodIndex.i18n('foss.stl.consumer.cod.operateOrgName'),
		dataIndex : 'operateOrgName'
	}
	]
});

/**
 * 日志面板
 */
Ext.define('Foss.consumer.BillPaidCodLog', {
	extend : 'Ext.window.Window',
	title : consumer.billPaidCodIndex.i18n('foss.stl.consumer.cod.codLog'),
	width : 800,
	modal : true,
	constrainHeader : true,
	closeAction : 'destory',
	items : [ Ext.create('Foss.consumer.BillPaidCodLogGrid', {
		viewConfig : {   
			enableTextSelection: true,           
	        forceFit : true
    	}
	}) ]
});

// 定义表格
Ext.define('FOSS.Consumer.BillPaidCodQueryGrid', {
	extend : 'Ext.grid.Panel',
	title : consumer.billPaidCodIndex.i18n('foss.stl.consumer.cod.codComprehensiveQuery'),
	stripeRows : true,
	columnLines : true,
	collapsible : false,
	billPaidCodLog : null,
	bodyCls : 'autoHeight',
	bodyStyle : {
		padding : '0px'
	},
	store : null,
	margin : '10 5 5 5',
	frame : true,
	listeners : {
		'itemdblclick' : function(th, record) {
			var waybillNo = record.data.waybillNo;
			var me = this;
			// 打开注释
			Ext.Ajax.request({
				timeout: consumer.billPaidCodIndex.AJAX_TIMEOUT*1000,
				url:consumer.realPath('queryBillCodLog.action'),
				params:{
					'vo.queryVo.waybillNo':waybillNo
				},
				success:function(response){
					var result = Ext.decode(response.responseText);
					var list = result.vo.logVoSet;
					me.billPaidCodLog.items.items[0].store.loadData(list);
					me.billPaidCodLog.show();
				},
				exception:function(response){
					var result = Ext.decode(response.responseText);
					Ext.Msg.alert(consumer.billPaidCodIndex.i18n('foss.stl.consumer.common.warmTips'),result.message);
				}			
			});
		}
	},
	columns : {
		defaults : {
			sortable : false,
			draggable : false
		},
		items : [

		{
			header : consumer.billPaidCodIndex.i18n('foss.stl.consumer.cod.majorKey'),
			dataIndex : 'id',
			hidden : true
		},

		{
			header : consumer.billPaidCodIndex.i18n('foss.stl.consumer.cod.codType'),
			dataIndex : 'codType',
			renderer : function(value) {
				/*if (value == 'R1') {
					return '即日退';
				} else if (value == 'R3') {
					return '三日退';
				} else if (value == 'RA') {
					return '审核退';
				} else {
					return value;
				}*/
				var displayField = FossDataDictionary.rendererSubmitToDisplay(value,'COD__COD_TYPE');
				return displayField;
			}
		}, {
			header : consumer.billPaidCodIndex.i18n('foss.stl.consumer.cod.startDepartment'),
			dataIndex : 'payableOrgName'
		}, {
			header : consumer.billPaidCodIndex.i18n('foss.stl.consumer.cod.wayBillNo'),
			dataIndex : 'waybillNo'	 ,
			renderer: function (data, metadata, record, rowIndex, columnIndex, store) {  
			      var waybillNo = store.getAt(rowIndex).get('waybillNo');  
			      var href="javascript:consumer.alertQueryLog('"+waybillNo+"')";
			      return '<a href="'+href+'">'+waybillNo+'</a>';  
			  }
		},{
			header : consumer.billPaidCodIndex.i18n('foss.stl.consumer.cod.mergeCode'),
			dataIndex : 'mergeCode'
		},{
			header: consumer.billPaidCodIndex.i18n('foss.stl.consumer.queryFreightToCollectBill.productType'), 
			dataIndex: 'productCode',
			renderer:function(value){
				return Foss.pkp.ProductData.rendererSubmitToDisplay(value);
			}
		},{
			header : consumer.billPaidCodIndex.i18n('foss.stl.consumer.cod.openCodAmount'),
			dataIndex : 'codAmount'
		}, {
			header : consumer.billPaidCodIndex.i18n('foss.stl.consumer.cod.verReceivableAmount'),
			dataIndex : 'verifyAmount'
		}, {
			header : consumer.billPaidCodIndex.i18n('foss.stl.consumer.cod.returnAmount'),
			dataIndex : 'payeeAmount',
			renderer : function(value,metadata, record, rowIndex, columnIndex, store,view) {
				if(value == null || value == 0){
					var verifyAmount = store.getAt(rowIndex).get('verifyAmount'); 
					if(verifyAmount != null && verifyAmount>0 ){
						return store.getAt(rowIndex).get('codAmount') -verifyAmount; // 减去 应收冲代收货款应付的金额
					}else{
						return store.getAt(rowIndex).get('codAmount') ;
					}
				}else{
					return value;
				}
			}
		}, {
			header : '代收手续费',
			dataIndex : 'codFee'
		}, {
			header : consumer.billPaidCodIndex.i18n('foss.stl.consumer.common.active'),
			dataIndex : 'active',
			renderer : function(value) {
				var displayField = FossDataDictionary.rendererSubmitToDisplay(value,settlementDict.FOSS_ACTIVE);
				return displayField;				
			},
			hidden:true
		},{
			header : consumer.billPaidCodIndex.i18n('foss.stl.consumer.cod.payStatus'),
			dataIndex : 'status',
			renderer : function(value) {
				/*if (value == 'NR') {
					return '未退款';
				} else if (value == 'AG') {
					return '待审核';
				} else if (value == 'CA') {
					return '营业部冻结';
				} else if (value == 'SF') {
					return '收银员审核';
				} else if (value == 'FF') {
					return '资金部冻结';
				} else if (value == 'RG') {
					return '退款中';
				} else if (value == 'RFA') {
					return '退款失败申请';
				} else if (value == 'NRS') {
					return '反汇款成功';
				} else if (value == 'RF') {
					return '退款失败';
				} else if (value == 'RD') {
					return '已退款';
				} else {
					return value;
				}*/
				var displayField = FossDataDictionary.rendererSubmitToDisplay(value,'COD__STATUS');
				return displayField;				
			}
		}, {
			header : consumer.billPaidCodIndex.i18n('foss.stl.consumer.cod.consignee'),
			dataIndex : 'consignee'
		}, {
			header : consumer.billPaidCodIndex.i18n('foss.stl.consumer.cod.payeeName'),
			dataIndex : 'payeeName'
		}, {
			header : consumer.billPaidCodIndex.i18n('foss.stl.consumer.cod.payeeAccount'),
			dataIndex : 'payeeAccount'
		}, {
			header : consumer.billPaidCodIndex.i18n('foss.stl.consumer.cod.bank'),
			dataIndex : 'bank'
		}, {
			header : consumer.billPaidCodIndex.i18n('foss.stl.consumer.cod.subbranch'),
			dataIndex : 'bankBranchName'
		},
		{
			header : consumer.billPaidCodIndex.i18n('foss.stl.consumer.cod.openBusinessDate'),
			dataIndex : 'businessDate',
			renderer : function(value) {
				return consumer.renderDate(value, 'Y-m-d');
			}
		}, {
			header : consumer.billPaidCodIndex.i18n('foss.stl.consumer.cod.signDate'),
			dataIndex : 'signDate', //签收时间
			width : 160,
			renderer : function(value) {
				return consumer.renderDate(value, 'Y-m-d G:i');
			}
		},{
			header : consumer.billPaidCodIndex.i18n('foss.stl.consumer.cod.cashConfirmTime'),
			dataIndex : 'cashConfirmTime', //收银确认时间
			width : 160,
			renderer : function(value) {
				return consumer.renderDate(value, 'Y-m-d G:i');
			}
		}, {
			header : consumer.billPaidCodIndex.i18n('foss.stl.consumer.cod.codExportTime'),
			dataIndex : 'codExportTime', //代收货款表中的导出时间
			width : 160,
			renderer : function(value) {
				return consumer.renderDate(value, 'Y-m-d G:i');
			}
		}, {
			header : consumer.billPaidCodIndex.i18n('foss.stl.consumer.cod.codExportSuccessTime'),
			dataIndex : 'refundSuccessTime',
			width : 160,
			renderer : function(value) {
				return consumer.renderDate(value, 'Y-m-d G:i');
			}
		},{
			header:consumer.billPaidCodIndex.i18n('foss.stl.consumer.cod.tusyorgRfdApptime'),
			dataIndex:'tusyorgRfdApptime',
			width:160,
			renderer:function(value){
				return consumer.renderDate(value, 'Y-m-d G:i');
			}
		},{
			header : consumer.billPaidCodIndex.i18n('foss.stl.consumer.cod.codExportFailureCause'),
			dataIndex : 'remittanceFailNotes'
		},{
			header : consumer.billPaidCodIndex.i18n('foss.stl.consumer.cod.batchNumber'),
			dataIndex : 'batchNo'
		},{
			header : consumer.billPaidCodIndex.i18n('foss.stl.consumer.cod.codBatchStatus'),
			dataIndex : 'codBatchStatus',
			renderer:function(value){
				/*displayText = null;
				switch(value){
					case 'SG':
						displayText = '发送中';
						break;
					case 'SS':
						displayText = '发送成功';
						break;
					case 'SF':
						displayText = '发送失败';
						break;
					case 'AP':
						displayText = '银企审核通过';
						break;
					case 'AF':
						displayText = '银企审核不通过';
						break;
					default:
						displayText = null;
				}
				return displayText;*/
				var displayField = FossDataDictionary.rendererSubmitToDisplay(value,'COD_BATCH__STATUS');
				return displayField;
			}
		},{
			header : consumer.billPaidCodIndex.i18n('foss.stl.consumer.cod.qyreturnerrormsg'),
			dataIndex : 'failNotes'
		}]
	},
	getGridStore : function() {
		var me = this;

		return me.store;
	},
	dockedItems: [{
		xtype: 'toolbar',
		dock: 'top',
		layout:'column',
		defaults :{
			margin :'0 0 5 3'
		},
		items: [{
			xtype:'button',
			text:consumer.billPaidCodIndex.i18n('foss.stl.consumer.common.export'),
			columnWidth:.1,
			handler: consumer.exportCod
		},{
			xtype:'container',
			border:false,
			html:'&nbsp;',
			columnWidth:.6
		}]
	}],
	bbar : null,
	getGridBBar : function() {
		var me = this;
		if (Ext.isEmpty(me.bbar)) {
			me.bbar = Ext.create('Deppon.StandardPaging', {
						store: me.store,
						pageSize: 100,
			    		plugins: Ext.create('Deppon.ux.PageSizePlugin', {
							//设置分页记录最大值，防止输入过大的数值
							maximumSize: 500
						})
				});
		}
		return me.bbar;
	},
	constructor : function(config) {
		var me = this;
		cfg = Ext.apply({}, config);
		me.store = Ext.create('FOSS.Consumer.BillPaidCodStore');
		me.billPaidCodLog = Ext.create('Foss.consumer.BillPaidCodLog');
		me.bbar = me.getGridBBar();
		me.callParent([ cfg ]);
	}
});

// 定义按运单查询form
Ext.define('Foss.consumer.BillPaidCodByNumberForm', {
	extend : 'Ext.form.Panel',
	frame:false,
	layout : {
		type : 'column'
	},
	defaults : {
		msgTarget : 'qtip',
		allowBlank : true
	},
	items : [{
			xtype : 'textarea',
			name : 'vo.queryVo.waybillNo',
			allowBlank:false,
			columnWidth :.7,
			height : 115,
			emptyText:consumer.billPaidCodIndex.i18n('foss.stl.consumer.cod.singleNumberMustSevenToTen'),
			//354658-校验至14位运单号
			regex : /^([0-9]{7,14}[,])*([0-9]{7,14}[,]?)$/i,
			regexText : consumer.billPaidCodIndex.i18n('foss.stl.consumer.cod.singleNumberMustSevenToTen')
	},{
		border: 1,
		xtype:'container',
		columnWidth:1,
		defaultType:'button',
		layout:'column',
		items:[{
			text:consumer.billPaidCodIndex.i18n('foss.stl.consumer.common.reset'),
			columnWidth:.08,
			handler:function() {
				// 重置
				this.up('form').getForm().reset();
			}
		},{
			xtype:'container',
			border:false,
			html:'&nbsp;',
			columnWidth:.54
		},{
			text:consumer.billPaidCodIndex.i18n('foss.stl.consumer.common.query'),
			cls:'yellow_button',
			columnWidth:.08,
			handler : function() {
				var form = this.up('form');
				consumer.billPaidCodQuery(form);
			}
		}]
	}]

});

//定义按合并编号查询form
Ext.define('Foss.consumer.BillPaidCodByMergeCodeForm', {
	extend : 'Ext.form.Panel',
	frame:false,
	layout : {
		type : 'column'
	},
	defaults : {
		msgTarget : 'qtip',
		allowBlank : true
	},
	items : [{
			xtype : 'textfield',
			name : 'vo.queryVo.mergeCode',
			allowBlank:false,
			columnWidth :.3,
			height : 30,
			emptyText:consumer.billPaidCodIndex.i18n('foss.stl.consumer.cod.mergeCodeNotNull'),
			regex : /^h([0-9]{9})$/i,
			regexText : consumer.billPaidCodIndex.i18n('foss.stl.consumer.cod.mergeCodeSingleOneLengthTwenty')
	},{
		xtype:'container',
		border:false,
		html:'&nbsp;',
		height : 85,
		columnWidth:.54
	},{
		border: 1,
		xtype:'container',
		columnWidth:1,
		defaultType:'button',
		layout:'column',
		items:[{
			text:consumer.billPaidCodIndex.i18n('foss.stl.consumer.common.reset'),
			columnWidth:.08,
			handler:function() {
				// 重置
				this.up('form').getForm().reset();
			}
		},{
			xtype:'container',
			border:false,
			html:'&nbsp;',
			columnWidth:.54
		},{
			text:consumer.billPaidCodIndex.i18n('foss.stl.consumer.common.query'),
			cls:'yellow_button',
			columnWidth:.08,
			handler : function() {
				var form = this.up('form');
				consumer.billPaidCodQuery(form);
			}
		}]
	}]

});

// 定义按日期查询form
Ext.define('Foss.consumer.BillPaidCodByDateForm', {
	extend : 'Ext.form.Panel',
	frame:false,
	layout : {
		type : 'column'
	},
	height:172,
	autoScroll:true,
	defaults : {
		msgTarget : 'qtip',
		margin :'0 0 1 0',
		allowBlank : true,
		columnWidth:.25
	},
	items : [
			// 业务日期
			{
				xtype : 'datefield',
				name : 'vo.queryVo.inceptBizDateStr',
				fieldLabel : consumer.billPaidCodIndex.i18n('foss.stl.consumer.cod.openDateStart'),
				format : 'Y-m-d',
				value : stl.dateFormat(stl.getTargetDate(new Date(), -3),
						stl.FORMAT_DATE)
			},
			{
				xtype : 'datefield',
				name : 'vo.queryVo.endBizDateStr',
				fieldLabel : consumer.billPaidCodIndex.i18n('foss.stl.consumer.cod.to'),
				format : 'Y-m-d',
				value : stl.dateFormat(new Date(), stl.FORMAT_DATE)
			},
			// 签收日期
			{
				xtype : 'datefield',
				name : 'vo.queryVo.inceptSignDateStr',
				fieldLabel : consumer.billPaidCodIndex.i18n('foss.stl.consumer.cod.signDayStart'),
				format : 'Y-m-d',
				value : stl.dateFormat(stl.getTargetDate(new Date(), -3),
						stl.FORMAT_DATE)
			},
			{
				xtype : 'datefield',
				name : 'vo.queryVo.endSignDateStr',
				fieldLabel : consumer.billPaidCodIndex.i18n('foss.stl.consumer.cod.to'),
				format : 'Y-m-d',
				value : stl.dateFormat(new Date(), stl.FORMAT_DATE)
			},
			// 付款日期
			{
				xtype : 'datefield',
				name : 'vo.queryVo.inceptPaymentDateStr',
				fieldLabel : consumer.billPaidCodIndex.i18n('foss.stl.consumer.cod.payDateStart'),
				format : 'Y-m-d',
				maxText : consumer.billPaidCodIndex.i18n('foss.stl.consumer.cod.notGreaterThanTheCurrentDate'),
				value : stl.dateFormat(stl.getTargetDate(new Date(), -3),
						stl.FORMAT_DATE)
			}, {
				xtype : 'datefield',
				name : 'vo.queryVo.endPaymentDateStr',
				fieldLabel : consumer.billPaidCodIndex.i18n('foss.stl.consumer.cod.to'),
				format : 'Y-m-d',
				maxText : consumer.billPaidCodIndex.i18n('foss.stl.consumer.cod.notGreaterThanTheCurrentDate'),
				value : stl.dateFormat(new Date(), stl.FORMAT_DATE)
			},
			// 付款成功日期
			{
				xtype : 'datefield',
				name : 'vo.queryVo.inceptRefundSuccessDateStr',
				fieldLabel : '汇款成功日期-起',
				format : 'Y-m-d',
				maxText : consumer.billPaidCodIndex.i18n('foss.stl.consumer.cod.notGreaterThanTheCurrentDate'),
				value : stl.dateFormat(stl.getTargetDate(new Date(), -6),
						stl.FORMAT_DATE)
			}, {
				xtype : 'datefield',
				name : 'vo.queryVo.endRefundSuccessDateStr',
				fieldLabel : consumer.billPaidCodIndex.i18n('foss.stl.consumer.cod.to'),
				format : 'Y-m-d',
				maxText : consumer.billPaidCodIndex.i18n('foss.stl.consumer.cod.notGreaterThanTheCurrentDate'),
				value : stl.dateFormat(new Date(), stl.FORMAT_DATE)
			},
			// 代收货款业务类别
			{
				xtype : 'combo',
				name : 'vo.queryVo.codType',
				fieldLabel : consumer.billPaidCodIndex.i18n('foss.stl.consumer.cod.codType'),
				value : '',
				displayField : 'valueName',
				valueField : 'valueCode',
				queryMode : 'local',
				editable : false,
				store:FossDataDictionary.getDataDictionaryStore('COD__COD_TYPE',null,{
				 'valueCode': '',
           		 'valueName': consumer.billPaidCodIndex.i18n('foss.stl.consumer.common.all')
				},null)
				/*store : Ext.create('Foss.consumer.codTypeStore')*/
			},

			// 代收货款付款状态
			{
				xtype : 'combo',
				name : 'vo.queryVo.status',
				fieldLabel : consumer.billPaidCodIndex.i18n('foss.stl.consumer.cod.payStatus'),
				value : '',
				displayField : 'valueName',
				valueField : 'valueCode',
				queryMode : 'local',
				editable : false,
				store:FossDataDictionary.getDataDictionaryStore('COD__STATUS',null,{
				 'valueCode': '',
           		 'valueName': consumer.billPaidCodIndex.i18n('foss.stl.consumer.common.all')
				},null)
				/*store : Ext.create('Foss.consumer.codStatusStore')*/
			},
			// 应付部门
			{
				//xtype : 'textfield',
				xtype:'dynamicorgcombselector',
				name : 'vo.queryVo.payableOrgCode',
				fieldLabel : consumer.billPaidCodIndex.i18n('foss.stl.consumer.cod.payableOrgName'),
				queryOrgCode:stl.currentDept.code,
				value : stl.currentDept.name,
				allowBlank : false,
				listWidth:300,//设置下拉框宽度
				isPaging:true, //分页
				listeners : {
					select:function(c,r,e){  
						c.queryOrgCode = '';
				    }
				}
			},

			// 应付客户
			{
				//xtype : 'textfield',
				xtype:'commoncustomerselector',
				listWidth:300,
				all:'true',
				singlePeopleFlag : 'Y',
				name : 'vo.queryVo.consignee',
				fieldLabel : consumer.billPaidCodIndex.i18n('foss.stl.consumer.cod.payableConsignee'),
				isPaging:true // 分页
			},

			// 退款金额大小
			{
				xtype : 'numberfield',
				name : 'vo.queryVo.inceptCodAmount',
				fieldLabel : consumer.billPaidCodIndex.i18n('foss.stl.consumer.cod.codReturnAmountStart'),
				value : 0,
				minValue : 0,
				maxValue : 99999999
			}, {
				xtype : 'numberfield',
				name : 'vo.queryVo.endCodAmount',
				fieldLabel : consumer.billPaidCodIndex.i18n('foss.stl.consumer.cod.codReturnAmountEnd'),
				value : 99999999,
				minValue : 0,
				maxValue : 99999999
			},
			// 收款人
			{
				xtype : 'textfield',
				name : 'vo.queryVo.payeeName',
				fieldLabel : consumer.billPaidCodIndex.i18n('foss.stl.consumer.cod.payeeName')
			},

			// 批次号
			{
				xtype : 'textfield',
				name : 'vo.queryVo.batchNumber',
				fieldLabel : consumer.billPaidCodIndex.i18n('foss.stl.consumer.cod.payBatchNumber')
			},

			// 付款路径
			{
				xtype : 'combo',
				name : 'vo.queryVo.refundPath',
				fieldLabel : consumer.billPaidCodIndex.i18n('foss.stl.consumer.cod.refundPath'),
				value : '',
				displayField : 'valueName',
				valueField : 'valueCode',
				queryMode : 'local',
				editable : false,
				store:FossDataDictionary.getDataDictionaryStore('COD__REFUND_PATH',null,{
				 'valueCode': '',
           		 'valueName': consumer.billPaidCodIndex.i18n('foss.stl.consumer.common.all')
				},null)
				/*store : Ext.create('Foss.consumer.refundPathStore')*/
			} ,
			{
		    	xtype: 'combobox',
				name:'vo.queryVo.productCodeList',
		        fieldLabel: consumer.billPaidCodIndex.i18n('foss.stl.consumer.queryCashCollectionBill.productCode'),
				store:Ext.create('Foss.pkp.ProductStore'),
				queryMode:'local',
				multiSelect:true,
		    	editable:false,
				displayField:'name',
				valueField:'code',
				height:24,
				columnWidth:.25,
				value:''
		    },
			// 银行
			{
				//xtype : 'textfield',
				xtype:'commonbankmultiselector',
				name : 'vo.queryVo.bank',
				fieldLabel : consumer.billPaidCodIndex.i18n('foss.stl.consumer.cod.depositBank'),
				valueField:this.displayField,
				headOffice:'Y', // 只查询总行
				//height:23,
		    	showContent : '{name}'// 显示表格列
			},
			{
				border: 1,
				xtype:'container',
				columnWidth:1,
				defaultType:'button',
				layout:'column',
				items:[{
					text:consumer.billPaidCodIndex.i18n('foss.stl.consumer.common.reset'),
					columnWidth:.08,
					handler:function() {
						// 重置
						this.up('form').getForm().reset();
						this.up('form').getForm().findField('vo.queryVo.payableOrgCode').queryOrgCode = stl.currentDept.code;
					}
				},{
					xtype:'container',
					border:false,
					html:'&nbsp;',
					columnWidth:.84
				},{
					text:consumer.billPaidCodIndex.i18n('foss.stl.consumer.common.query'),
					cls:'yellow_button',
					columnWidth:.08,
					handler : function() {
						consumer.billPaidCodQuery(this.up('form'));
					}
				}]
			}]

});

// 显示
Ext.onReady(function() {
	Ext.QuickTips.init();

	// 创建面板
	var billPaidCodQueryByNumberForm = Ext.create(
			'Foss.consumer.BillPaidCodByNumberForm', {
				id : 'Foss_consumer_BillPaidCodFormByNumber_ID'
			});

	var billPaidCodQueryByDateForm = Ext.create(
			'Foss.consumer.BillPaidCodByDateForm', {
				id : 'Foss_consumer_BillPaidCodFormByDate_ID'
			});
	
	var billPaidCodQueryByMergeCodeForm = Ext.create(
			'Foss.consumer.BillPaidCodByMergeCodeForm', {
				id : 'Foss_consumer_BillPaidCodFormByMergeCode_ID'
			});
	

	// 创建TAB控件
	var tabPanel = Ext.create('Ext.tab.Panel', {
		frame : false,
		bodyCls : 'autoHeight',
		cls : 'innerTabPanel',
		height:220,
		activeTab : 0,
		id : 'Foss_consumer_BillPaidTablePanel_ID',
		items : [ {
			title : consumer.billPaidCodIndex.i18n('foss.stl.consumer.common.queryByNo'),
			tabConfig : {
				width : 120
			},
			defaults:{
        		margin:'5,5,5,5'
        	},
			items : [ billPaidCodQueryByNumberForm ]
		}, {
			title : consumer.billPaidCodIndex.i18n('foss.stl.consumer.common.queryByDate'),
			defaults:{
        		margin:'5,5,5,5'
        	},
			tabConfig : {
				width : 120
			},
			items : [ billPaidCodQueryByDateForm ]
		}, {
			title : consumer.billPaidCodIndex.i18n('foss.stl.consumer.common.queryByMergeCode'),
			defaults:{
        		margin:'5,5,5,5'
        	},
			tabConfig : {
				width : 120
			},
			items : [ billPaidCodQueryByMergeCodeForm ]
		} ]
	});

	var queryFormPanel = Ext.create('Ext.panel.Panel', {
		border : 0,
		items : [tabPanel]
	})

	// 创建GRID
	var billPaidCodQueryGridId = Ext
			.getCmp('FOSS_Consumer_BillPaidCodQueryGrid_ID');
	if (Ext.isEmpty(billPaidCodQueryGridId)) {
		// 创建显示表格
		billPaidCodQueryGridId = Ext.create(
				'FOSS.Consumer.BillPaidCodQueryGrid', {
					id : 'FOSS_Consumer_BillPaidCODQueryGrid_ID',
					hidden : true,
					height : 480,
					viewConfig : {   
						enableTextSelection: true,           
				        forceFit : true,
				        stripeRows: true,//显示重复样式，不用隔行显示
				        emptyText : consumer.billPaidCodIndex.i18n('foss.stl.consumer.common.emptyText')
			    	}
				});
	}

	// 显示到JSP页面
	Ext.create('Ext.panel.Panel', {
		id : 'T_consumer-billPaidCodIndex_content',
		cls : "panelContentNToolbar",
		bodyCls : 'panelContentNToolbar-body',
		layout : 'auto',
		getBillPaidCodQueryGridId:function(){
			return billPaidCodQueryGridId;
		},
		getQueryFormPanel:function(){
			return queryFormPanel;
		},
		items : [ queryFormPanel, billPaidCodQueryGridId ],
		renderTo : 'T_consumer-billPaidCodIndex-body'
	});
});