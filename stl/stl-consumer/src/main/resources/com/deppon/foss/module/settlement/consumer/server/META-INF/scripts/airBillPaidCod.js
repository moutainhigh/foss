/**
 * 请求超时时间
 */
consumer.airBillPaidCodIndex.AJAX_TIMEOUT = 2*60*60; //默认2小时
// 日期对象转换
consumer.dateConvert = function(value, record) {
	return stl.longToDateConvert(value);
}

// 日期渲染
consumer.renderDate = function(value, format) {
	return stl.dateFormat(value, format);
}

// 查询审核空运代收货款
consumer.airBillPaidCodQuery = function(form) {
	form = form.getForm();
	if (form.isValid()) {
		// 得到Store
		var grid = Ext.getCmp('FOSS_consumer_airBillPaidCodQueryGrid_ID');
		if(grid){
			var store = grid.store;
			if (store) {
				
				params = consumer.setAirBillPaidCodQuerySubmitParams(form);
				if (params == false){
					return false;
				}
				
				if(grid.isHidden()){
					grid.show();
				}
				
				// 设置查询参数
				grid.store.setSubmitParams(params);
				
				store.loadPage(1,{
					callback: function(records, operation, success) {
						var rawData = store.proxy.reader.rawData;
						//当success:false ,isException:false  --业务异常
					    if(!success && ! rawData.isException){
					    	var result = Ext.decode(operation.response.responseText);
							Ext.Msg.alert(consumer.airBillPaidCodIndex.i18n('foss.stl.consumer.common.warmTips'),result.message);
							return false;
						}
						if(success){
							var result = Ext.decode(operation.response.responseText);  
							var tbars = grid.dockedItems.items[2].query('textfield');
							if(!Ext.isEmpty(result.vo.grid)&& result.vo.grid.length>0){
								tbars[1].setValue(result.totalCount);
								tbars[2].setValue(result.vo.totalAmount);
					      	}else{
					      		tbars[1].setValue(0);
								tbars[2].setValue(0);
					      	}
							
						}
					}
				});
			}
		}
		
	}
	// 如果查询条件不合法
	else {
		Ext.Msg.alert(consumer.airBillPaidCodIndex.i18n('foss.stl.consumer.common.warmTips'), consumer.airBillPaidCodIndex.i18n('foss.stl.consumer.cod.pleaseCheckTheInputConditionIsLegal'));
	}
}

/**
 * 查询参数设置
 */
consumer.setAirBillPaidCodQuerySubmitParams = function(form){
	var waybillQueryFrom = Ext.getCmp('Foss_consumer_airBillPaidCodFormByNumber_ID');
	var dateQueryForm = Ext.getCmp('Foss_consumer_airBillPaidCodFormByDate_ID');

	var params = {};
	// 获得当前页签
	var tabPanel = Ext.getCmp('Foss_consumer_airBillPaidTablePanel_ID');
	var activePanel = tabPanel.getActiveTab();

	var queryType = null;
	// 判断查询类别
	if (activePanel.down('form').getId() == waybillQueryFrom.getId()) {
		queryType = 'queryByWaybillNo';
		Ext.apply(params, waybillQueryFrom.getForm().getValues());
	} else {
		queryType = 'queryByDate';
		form = activePanel.down('form').getForm();
		var inceptSignDateStr = form.findField('vo.queryVo.inceptSignDateStr')
				.getValue();
		var endSignDateStr = form.findField('vo.queryVo.endSignDateStr')
				.getValue();
		var inceptAuditDateStr = form
				.findField('vo.queryVo.inceptAuditDateStr').getValue();
		var endAuditDateStr = form.findField('vo.queryVo.endAuditDateStr')
				.getValue();

		if ((inceptSignDateStr == null || inceptSignDateStr == "")
				&& (endSignDateStr == null || endSignDateStr == "")
				&& (inceptAuditDateStr == null || inceptAuditDateStr == "")
				&& (endAuditDateStr == null || endAuditDateStr == "")) {
			Ext.Msg.alert(consumer.airBillPaidCodIndex.i18n('foss.stl.consumer.common.warmTips'),
							consumer.airBillPaidCodIndex.i18n('foss.stl.consumer.cod.helloInquiresTheTimeEmptyPleaseInputTime'));
			return false;
		}

		if ((inceptSignDateStr != null && inceptSignDateStr != "")
				&& (endSignDateStr == null || endSignDateStr == "")) {
			Ext.Msg
					.alert(
							consumer.airBillPaidCodIndex
									.i18n('foss.stl.consumer.common.warmTips'),
							consumer.airBillPaidCodIndex
									.i18n('foss.stl.consumer.cod.helloYourQuerySignForEndDateEmptyPleaseInput'));
			return false;
		}

		if ((inceptSignDateStr == null || inceptSignDateStr == "")
				&& (endSignDateStr != null && endSignDateStr != "")) {
			Ext.Msg
					.alert(
							consumer.airBillPaidCodIndex
									.i18n('foss.stl.consumer.common.warmTips'),
							consumer.airBillPaidCodIndex
									.i18n('foss.stl.consumer.cod.helloYourQuerySignForStartDateEmptyPleaseInput'));
			return false;
		}

		if (inceptSignDateStr != null && inceptSignDateStr != ""
				&& endSignDateStr != null && endSignDateStr != "") {
			var compareTwoDate = stl.compareTwoDate(inceptSignDateStr,
					endSignDateStr);
			if (compareTwoDate > 7) {
				Ext.Msg
						.alert(
								consumer.airBillPaidCodIndex
										.i18n('foss.stl.consumer.common.warmTips'),
								consumer.airBillPaidCodIndex
										.i18n('foss.stl.consumer.cod.helloYouInquiresTheDateSectionHasMoreThan7DaysPleaseReadjustTheDate'));
				return false;
			} else if (compareTwoDate < 1) {
				Ext.Msg
						.alert(
								consumer.airBillPaidCodIndex
										.i18n('foss.stl.consumer.common.warmTips'),
								consumer.airBillPaidCodIndex
										.i18n('foss.stl.consumer.cod.helloSignInEndDateNotLessThanStartDatePleaseReadjustInput'));
				return false;
			}
		}
		if ((inceptAuditDateStr != null && inceptAuditDateStr != "")
				&& (endAuditDateStr == null || endAuditDateStr == "")) {
			Ext.Msg
					.alert(
							consumer.airBillPaidCodIndex
									.i18n('foss.stl.consumer.common.warmTips'),
							consumer.airBillPaidCodIndex
									.i18n('foss.stl.consumer.cod.helloYouInquiresTheAuditEndDateEmptyPleaseInput'));
			return false;
		}

		if ((inceptAuditDateStr == null || inceptAuditDateStr == "")
				&& (endAuditDateStr != null && endAuditDateStr != "")) {
			Ext.Msg
					.alert(
							consumer.airBillPaidCodIndex
									.i18n('foss.stl.consumer.common.warmTips'),
							consumer.airBillPaidCodIndex
									.i18n('foss.stl.consumer.cod.helloYouInquiresTheAuditStartDateEmptyPleaseInput'));
			return false;
		}
		if (inceptAuditDateStr != null && inceptAuditDateStr != ""
				&& endAuditDateStr != null && endAuditDateStr != "") {
			var compareTwoDate = stl.compareTwoDate(inceptAuditDateStr,
					endAuditDateStr);
			if (compareTwoDate > 7) {
				Ext.Msg
						.alert(
								consumer.airBillPaidCodIndex
										.i18n('foss.stl.consumer.common.warmTips'),
								consumer.airBillPaidCodIndex
										.i18n('foss.stl.consumer.cod.helloYouInquiresTheDateSectionHasMoreThan7DaysPleaseReadjustTheDate'));
				return false;
			} else if (compareTwoDate < 1) {
				Ext.Msg
						.alert(
								consumer.airBillPaidCodIndex
										.i18n('foss.stl.consumer.common.warmTips'),
								consumer.airBillPaidCodIndex
										.i18n('foss.stl.consumer.cod.helloAuditInEndDateNotLessThanStartDatePleaseReadjustInput'));
				return false;
			}
		}
		Ext.apply(params, dateQueryForm.getForm().getValues());
	}
	// 对查询类别进行赋值
	Ext.apply(params, {
		'vo.queryVo.queryType' : queryType
	});
	return params;
}

// 空运代收货款审核
consumer.airBillPaidCodAudit = function(ids) {
	var grid = Ext.getCmp('FOSS_consumer_airBillPaidCodQueryGrid_ID');
	Ext.Ajax.request({
		timeout: consumer.airBillPaidCodIndex.AJAX_TIMEOUT*1000,
		url : consumer.realPath('auditAirCOD.action'),
		method : 'post',
		params : {
			'vo.entityIds' : ids
		},
		success : function(r, o) {
			Ext.Msg.alert(consumer.airBillPaidCodIndex.i18n('foss.stl.consumer.common.warmTips'), consumer.airBillPaidCodIndex.i18n('foss.stl.consumer.cod.auditAirCodFinish'), function() {
				var store = grid.store;
				if (store) {
					store.loadPage(1,{
    					callback: function(records, operation, success) {
    					    var result =   Ext.decode(operation.response.responseText);  
    					    if(result.isException){
    							Ext.Msg.alert(consumer.airBillPaidCodIndex.i18n('foss.stl.consumer.common.warmTips'),result.message);
    							return false;
    						}
    					}
					});
				}
			});
		},
		exception : function(response) {
			var message = Ext.decode(response.responseText).message;
			Ext.Msg.alert(consumer.airBillPaidCodIndex.i18n('foss.stl.consumer.common.warmTips'), consumer.airBillPaidCodIndex.i18n('foss.stl.consumer.cod.auditAirCodError') + message);
		}
	});
}

// 空运代收货款反审核
consumer.airBillPaidCodAntiAudit = function(ids) {
	var grid = Ext.getCmp('FOSS_consumer_airBillPaidCodQueryGrid_ID');
	Ext.Ajax.request({
		timeout: consumer.airBillPaidCodIndex.AJAX_TIMEOUT*1000,
		url : consumer.realPath('reverseAuditAirCOD.action'),
		method : 'post',
		params : {
			'vo.entityIds' : ids
		},
		success : function(r, o) {
			Ext.Msg.alert(consumer.airBillPaidCodIndex.i18n('foss.stl.consumer.common.warmTips'), consumer.airBillPaidCodIndex.i18n('foss.stl.consumer.cod.unAuditAirCodFinish'), function() {
				var store = grid.store;
				if (store) {
					store.loadPage(1,{
    					callback: function(records, operation, success) {
    						var rawData = store.proxy.reader.rawData;
							//当success:false ,isException:false  --业务异常
    					    if(!success && ! rawData.isException){
    					    	var result = Ext.decode(operation.response.responseText);  
    							Ext.Msg.alert(consumer.airBillPaidCodIndex.i18n('foss.stl.consumer.common.warmTips'),result.message);
    							return false;
    						}
    						if(success){
    							var result = Ext.decode(operation.response.responseText);  
    							var tbars = grid.dockedItems.items[2].query('textfield');
    							if(!Ext.isEmpty(result.vo.grid)&& result.vo.grid.length>0){
    								tbars[1].setValue(result.totalCount);
    								tbars[2].setValue(result.vo.totalAmount);
    					      	}else{
    					      		tbars[1].setValue(0);
    								tbars[2].setValue(0);
    					      	}
    						}
    					}
    				});
				}
			});
		},
		exception : function(response) {
			var message = Ext.decode(response.responseText).message;
			Ext.Msg.alert(consumer.airBillPaidCodIndex.i18n('foss.stl.consumer.common.warmTips'), consumer.airBillPaidCodIndex.i18n('foss.stl.consumer.cod.unAuditAirCodError') + message);
		}
	});
}

// 空运代收货款查询
Ext.define('FOSS.consumer.airBillPaidCodDataModel',
		{
			extend : 'Ext.data.Model',
			// 结算类别、空运审核状态、运单单号、出发部门(应付部门)、发货客户
			// 签收人、签收代理、目的站、代收货款金额
			// 开单时间、产品类型、到达部门名称、到达客户、到达付款方式
			fields : [ 'id', 'waybillNo', 'codType', 'airStatus',
					'airOrgAuditUserCode', 'airOrgAuditUserName', {
						name : 'airAuditTime',
						type : 'date',
						convert : consumer.dateConvert
					},

					'payableOrgCode', 'payableOrgName', 'customerCode',
					'customerName', 'signer', 'agencyCode', 'agencyName',
					'destination', 'codAmount', {
						name : 'signDate',
						type : 'date',
						convert : consumer.dateConvert
					}, 'productType', 'destDeptCode', 'destDeptName',
					'arriveCustomer' ]
		});

// 定义Store
Ext
		.define(
				'FOSS.consumer.airBillPaidCodStore',
				{
					extend : 'Ext.data.Store',
					model : 'FOSS.consumer.airBillPaidCodDataModel',
					pageSize : 10,
					proxy : {
						type : 'ajax',
						actionMethods : 'post',
						timeout: consumer.airBillPaidCodIndex.AJAX_TIMEOUT*1000,
						url : consumer.realPath('queryAirBillPaidCod.action'),
						reader : {
							type : 'json',
							root : 'vo.grid',
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

// 定义表格
Ext
		.define(
				'FOSS.consumer.airBillPaidCodQueryGrid',
				{
					extend : 'Ext.grid.Panel',
					selModel : Ext.create('Ext.selection.CheckboxModel'),
					title : consumer.airBillPaidCodIndex.i18n('foss.stl.consumer.cod.airCodAudit'),
					stripeRows : true,
					columnLines : true,
					collapsible : false,
					bodyCls : 'autoHeight',
					bodyStyle : {
						padding : '0px'
					},
					store : null,
					margin : '10 5 20 5',
					frame : true,
					columns : {
						defaults : {
							sortable : false,
							draggable : false
						},
						items : [
								{
									header : consumer.airBillPaidCodIndex.i18n('foss.stl.consumer.common.actionColumn'),
									align : 'center',
									dataIndex : 'id',
									renderer : function(id) {
										var array = [];
										var selectedModel = this.store
												.getById(id)
										var audited = selectedModel
												.get('airStatus');
										if ('NA' == audited) {
											array
													.push('<a href="javascript:consumer.airBillPaidCodAudit([\''
															+ id
															+ '\']);">'+consumer.airBillPaidCodIndex.i18n('foss.stl.consumer.common.audit')+'</a>');
										} else if ('AA' == audited) {
											array
													.push('<a href="javascript:consumer.airBillPaidCodAntiAudit([\''
															+ id
															+ '\']);">'+consumer.airBillPaidCodIndex.i18n('foss.stl.consumer.common.unAudit')+'</a>');
										}
										return array.join('');
									}
								},
								{
									header : consumer.airBillPaidCodIndex.i18n('foss.stl.consumer.cod.wayBillNo'),
									dataIndex : 'waybillNo'
								},
								// 注意完善
								{
									header : consumer.airBillPaidCodIndex.i18n('foss.stl.consumer.cod.codType'),
									dataIndex : 'codType',
									renderer:function(value){
        					    		var displayField = FossDataDictionary.rendererSubmitToDisplay(value,'COD__COD_TYPE');
        					    		return displayField;
        					    	}
								},
								{
									header : consumer.airBillPaidCodIndex.i18n('foss.stl.consumer.cod.airStatus'),
									dataIndex : 'airStatus',
									renderer:function(value){
        					    		var displayField = FossDataDictionary.rendererSubmitToDisplay(value,'COD__AIR_STATUS');
        					    		return displayField;
        					    	}
								},
								{
									header : consumer.airBillPaidCodIndex.i18n('foss.stl.consumer.cod.airOrgAuditUserCode'),
									dataIndex : 'airOrgAuditUserCode'
								},
								{
									header : consumer.airBillPaidCodIndex.i18n('foss.stl.consumer.cod.airOrgAuditUserName'),
									dataIndex : 'airOrgAuditUserName'
								},
								{
									header : consumer.airBillPaidCodIndex.i18n('foss.stl.consumer.cod.airAuditTime'),
									dataIndex : 'airAuditTime',
									renderer : function(value) {
    									return consumer.renderDate(value,
    											'Y-m-d');
									}
								},
								{
									header : consumer.airBillPaidCodIndex.i18n('foss.stl.consumer.cod.payableOrgCode'),
									dataIndex : 'payableOrgCode'
								},
								{
									header : consumer.airBillPaidCodIndex.i18n('foss.stl.consumer.cod.payableOrgName'),
									dataIndex : 'payableOrgName'
								},
								{
									header : consumer.airBillPaidCodIndex.i18n('foss.stl.consumer.cod.customerCode'),
									dataIndex : 'customerCode'
								},
								{
									header : consumer.airBillPaidCodIndex.i18n('foss.stl.consumer.cod.customerName'),
									dataIndex : 'customerName'
								},
								{
									header : consumer.airBillPaidCodIndex.i18n('foss.stl.consumer.cod.signer'),
									dataIndex : 'signer'
								},
								{
									header : consumer.airBillPaidCodIndex.i18n('foss.stl.consumer.cod.agencyCode'),
									dataIndex : 'agencyCode'
								},
								{
									header : consumer.airBillPaidCodIndex.i18n('foss.stl.consumer.cod.agencyName'),
									dataIndex : 'agencyName'
								},
								{
									header : consumer.airBillPaidCodIndex.i18n('foss.stl.consumer.cod.destination'),
									dataIndex : 'destination'
								},
								{
									header : consumer.airBillPaidCodIndex.i18n('foss.stl.consumer.cod.codAmount'),
									dataIndex : 'codAmount'
								},
								{
									header : consumer.airBillPaidCodIndex.i18n('foss.stl.consumer.cod.signDay'),
									dataIndex : 'signDate',
									renderer : function(value) {
    									return consumer.renderDate(value,
    											'Y-m-d');
									}
								},
								{
									header : consumer.airBillPaidCodIndex.i18n('foss.stl.consumer.cod.productType'),
									dataIndex : 'productType',
									renderer:function(value){
        					    		var displayField = Foss.pkp.ProductData.rendererSubmitToDisplay(value);
        					    		return displayField;
        					    	}
								}, {
									header : consumer.airBillPaidCodIndex.i18n('foss.stl.consumer.cod.destDeptCode'),
									dataIndex : 'destDeptCode'
								}, {
									header : consumer.airBillPaidCodIndex.i18n('foss.stl.consumer.cod.destDeptName'),
									dataIndex : 'destDeptName'
								}, {
									header : consumer.airBillPaidCodIndex.i18n('foss.stl.consumer.cod.arriveCustomer'),
									dataIndex : 'arriveCustomer'
								}]
					},
					getGridStore : function() {
						var me = this;

						return me.store;
					},
					initComponent:function(){
						var me = this;
						me.dockedItems =[{
					   		xtype: 'toolbar',
						    dock: 'bottom',
						    layout:'column',		    	
						    defaults:{
								margin:'0 0 5 3'
							},		
						    items: [{
								xtype:'textfield',
								readOnly:true,
								name:'total',
								columnWidth:.05,
								labelSeparator: '',
								labelWidth:40,
								fieldLabel:consumer.airBillPaidCodIndex.i18n('foss.stl.consumer.cod.total')
							},{
								xtype:'textfield',
								readOnly:true,	
								name:'codTotalNumber',
								columnWidth:.1,
								labelWidth:60,
								fieldLabel:consumer.airBillPaidCodIndex.i18n('foss.stl.consumer.cod.allNum')
							},{
								xtype:'textfield',
								readOnly:true,	
								name:'codTotalAmount',
								columnWidth:.2,
								labelWidth:100,
								fieldLabel:consumer.airBillPaidCodIndex.i18n('foss.stl.consumer.cod.codAmount')
							},{
								xtype:'standardpaging',
								store:me.store,
								columnWidth:1,
								plugins: Ext.create('Deppon.ux.PageSizePlugin', {
									//设置分页记录最大值，防止输入过大的数值
									maximumSize: 200
								})
							 },{
								 xtype: 'component',
								 border:true,
							     autoEl: {
							       tag: 'hr'
							     },	
						 		columnWidth:1
							 },{
								xtype: 'container', 
								border:false,
								html:'&nbsp;', 
								columnWidth:.72
							},{
								xtype : 'button',
								text : consumer.airBillPaidCodIndex.i18n('foss.stl.consumer.common.audit'),
								disabled:!consumer.airBillPaidCodIndex.isPermission('/stl-web/consumer/auditAirCOD.action'),
								hidden:!consumer.airBillPaidCodIndex.isPermission('/stl-web/consumer/auditAirCOD.action'),
								cls:'specialbtn',
								columnWidth : 0.1,
								handler : function() {
										var grid = this.up('grid');
										var store = grid.getStore();
										var recordArray = grid
												.getSelectionModel()
												.getSelection();
			
										if (!recordArray
												|| recordArray.length == 0) {
											Ext.Msg.alert(consumer.airBillPaidCodIndex.i18n('foss.stl.consumer.common.warmTips'),
															consumer.airBillPaidCodIndex.i18n('foss.stl.consumer.cod.pleaseSelectAtLeastOneDataConductOperation'));
											return;
										}
			
										// 得到代收货款ID
										var codEntityIds = [];
										for ( var i = 0; i < recordArray.length; i++) {
											var record = recordArray[i];
											var airStatus = record
													.get('airStatus');
											var waybillNo = record
													.get('waybillNo');
			
											if (airStatus != 'NA') {
												Ext.Msg.alert(consumer.airBillPaidCodIndex.i18n('foss.stl.consumer.common.warmTips'),
														consumer.airBillPaidCodIndex.i18n('foss.stl.consumer.cod.waybillNoCodAuditStateNotForNotAuditCannotAuditOperation',[waybillNo]));
												return;
											}
			
											codEntityIds.push(record.get('id'));
										}
										// 审核ajax请求
										consumer.airBillPaidCodAudit(codEntityIds);
									}
								}, {
									xtype : 'container',
									border : false,
									html : '&nbsp;',
									columnWidth : 0.05
								},{
									xtype : 'button',
									text : consumer.airBillPaidCodIndex.i18n('foss.stl.consumer.common.unAudit'),
									disabled:!consumer.airBillPaidCodIndex.isPermission('/stl-web/consumer/reverseAuditAirCOD.action'),
									hidden:!consumer.airBillPaidCodIndex.isPermission('/stl-web/consumer/reverseAuditAirCOD.action'),
									cls:'specialbtn',
									columnWidth : 0.1,
									handler : function() {
											var grid = this.up('grid');
											var store = grid.getStore();
											var recordArray = grid
													.getSelectionModel()
													.getSelection();
	
											if (!recordArray
													|| recordArray.length == 0) {
												Ext.Msg.alert(
														consumer.airBillPaidCodIndex.i18n('foss.stl.consumer.common.warmTips'),
														consumer.airBillPaidCodIndex.i18n('foss.stl.consumer.cod.pleaseSelectAtLeastOneDataConductOperation'));
												return;
											}
	
											// 得到代收货款ID
											var codEntityIds = [];
											for ( var i = 0; i < recordArray.length; i++) {
												var record = recordArray[i];
												var airStatus = record.get('airStatus');
												var waybillNo = record.get('waybillNo');
	
												if (airStatus != 'AA') {
													Ext.Msg.alert(
															consumer.airBillPaidCodIndex.i18n('foss.stl.consumer.common.warmTips'),
															consumer.airBillPaidCodIndex.i18n('foss.stl.consumer.cod.waybillNoCodAuditStateNotForAuditCannotUnAuditOperation',[waybillNo]));
													return;
												}
												codEntityIds.push(record.get('id'));
											}
	
											// 反审核ajax请求
											consumer.airBillPaidCodAntiAudit(codEntityIds);
										}
								}]
				   		 }];
				   		 me.callParent();
					},
					constructor : function(config) {
						var me = this;
						cfg = Ext.apply({}, config);
						me.store = Ext
								.create('FOSS.consumer.airBillPaidCodStore');
						me.callParent([ cfg ]);
					}
				});

// 定义按运单查询form
Ext.define('Foss.consumer.airBillPaidCodByNumberForm', {
	extend : 'Ext.form.Panel',
	frame : false,
	layout : {
		type : 'column'
	},
	defaults : {
		margin :'10 0 10 0',
		msgTarget : 'qtip'
	},
	items : [ {
		xtype : 'textarea',
		name : 'vo.queryVo.waybillNo',
		columnWidth : .7,
		height : 60,                                                          
		emptyText: consumer.airBillPaidCodIndex.i18n('foss.stl.consumer.cod.singleNumberMustSevenToTen'),
		allowBlank:false,
		//354658-校验至14位运单号
		regex :/^([0-9]{8,14})(,[0-9]{8,14}){0,9},?$/i,
		regexText : consumer.airBillPaidCodIndex.i18n('foss.stl.consumer.cod.singleNumberMustSevenToTen')
	}, {
		border : 1,
		xtype : 'container',
		columnWidth : 1,
		defaultType : 'button',
		layout : 'column',
		items : [ {
			text : consumer.airBillPaidCodIndex.i18n('foss.stl.consumer.common.reset'),
			columnWidth : .08,
			handler : function() {
				// 重置
				this.up('form').getForm().reset();
			}
		}, {
			xtype : 'container',
			border : false,
			html : '&nbsp;',
			columnWidth : .54
		}, {
			text : consumer.airBillPaidCodIndex.i18n('foss.stl.consumer.common.query'),
			cls : 'yellow_button',
			columnWidth : .08,
			handler : function() {
				var form = this.up('form');
				consumer.airBillPaidCodQuery(form);
			}
		} ]
	} ]

});

// 定义按日期查询form
Ext.define('Foss.consumer.airBillPaidCodByDateForm', {
	extend : 'Ext.form.Panel',
	frame : false, 
	layout : {
		type : 'column'
	},
	defaults : {
		msgTarget : 'qtip',
		allowBlank : true,
		margin :'10 0 10 0',
		columnWidth : .25
	},
	items : [
			// 业务日期
			{
				xtype : 'datefield',
				name : 'vo.queryVo.inceptSignDateStr',
				fieldLabel : consumer.airBillPaidCodIndex.i18n('foss.stl.consumer.cod.signDayStart'),
				format : 'Y-m-d',
				value : stl.getTargetDate(new Date(), -3)
			},
			{
				xtype : 'datefield',
				name : 'vo.queryVo.endSignDateStr',
				fieldLabel : consumer.airBillPaidCodIndex.i18n('foss.stl.consumer.cod.to'),
				format : 'Y-m-d',
				value : new Date()
			},
			// 审核日期
			{
				xtype : 'datefield',
				name : 'vo.queryVo.inceptAuditDateStr',
				fieldLabel : consumer.airBillPaidCodIndex.i18n('foss.stl.consumer.cod.auditDayStrat'),
				format : 'Y-m-d'
			},
			{
				xtype : 'datefield',
				name : 'vo.queryVo.endAuditDateStr',
				fieldLabel : consumer.airBillPaidCodIndex.i18n('foss.stl.consumer.cod.to'),
				format : 'Y-m-d'
			},
			// 代收货款业务类别
			{
				xtype : 'combo',
				name : 'vo.queryVo.codType',
				fieldLabel : consumer.airBillPaidCodIndex.i18n('foss.stl.consumer.cod.codType'),
				triggerAction : 'all',
				editable : false,
				store : FossDataDictionary.getDataDictionaryStore(
						'COD__COD_TYPE', null, {
							'valueCode' : '',
							'valueName' : consumer.airBillPaidCodIndex.i18n('foss.stl.consumer.common.all')
						}),
				queryModel : 'local',
				displayField : 'valueName',
				valueField : 'valueCode',
				value : ''
			},

			// 代收货款审核状态
			{
				xtype : 'combo',
				name : 'vo.queryVo.airStatus',
				fieldLabel : consumer.airBillPaidCodIndex.i18n('foss.stl.consumer.cod.airStatus'),
				triggerAction : 'all',
				editable : false,
				editable : false,
				store : FossDataDictionary.getDataDictionaryStore(
						'COD__AIR_STATUS', null, {
							'valueCode' : '',
							'valueName' : consumer.airBillPaidCodIndex.i18n('foss.stl.consumer.common.all')
						}),
				queryModel : 'local',
				displayField : 'valueName',
				valueField : 'valueCode',
				value : ''
			}, {
				border : 1,
				xtype : 'container',
				columnWidth : 1,
				defaultType : 'button',
				layout : 'column',
				items : [ {
					text : consumer.airBillPaidCodIndex.i18n('foss.stl.consumer.common.reset'),
					columnWidth : .08,
					handler : function() {
						// 重置
						this.up('form').getForm().reset();
					}
				}, {
					xtype : 'container',
					border : false,
					html : '&nbsp;',
					columnWidth : .84
				}, {
					text : consumer.airBillPaidCodIndex.i18n('foss.stl.consumer.common.query'),
					cls : 'yellow_button',
					columnWidth : .08,
					handler : function() {
						var form = this.up('form');
						consumer.airBillPaidCodQuery(form);
					}
				} ]
			} ]

});

// 显示
Ext.onReady(function() {
	Ext.QuickTips.init();

	// 创建面板
	var airBillPaidCodQueryByNumberForm = Ext.create(
			'Foss.consumer.airBillPaidCodByNumberForm', {
				id : 'Foss_consumer_airBillPaidCodFormByNumber_ID'
			});

	var airBillPaidCodQueryByDateForm = Ext.create(
			'Foss.consumer.airBillPaidCodByDateForm', {
				id : 'Foss_consumer_airBillPaidCodFormByDate_ID'
			});

	// 创建TAB控件
	var tabPanel = Ext.create('Ext.tab.Panel', {
		frame : false,
		bodyCls : 'autoHeight',
		cls : 'innerTabPanel',
		activeTab : 0,
		id : 'Foss_consumer_airBillPaidTablePanel_ID',
		items : [ {
			title : consumer.airBillPaidCodIndex.i18n('foss.stl.consumer.common.queryByDate'),
			tabConfig : {
				width : 120
			},
			items : [ airBillPaidCodQueryByDateForm ]
		}, {

			title : consumer.airBillPaidCodIndex.i18n('foss.stl.consumer.common.queryByNo'),
			tabConfig : {
				width : 120
			},
			items : [ airBillPaidCodQueryByNumberForm ]
		} ]
	});

	// 创建GRID
	var billPaidCodQueryGridId = Ext
			.getCmp('FOSS_consumer_airBillPaidCodQueryGrid_ID');
	if (Ext.isEmpty(billPaidCodQueryGridId)) {
		// 创建显示表格
		billPaidCodQueryGridId = Ext.create(
				'FOSS.consumer.airBillPaidCodQueryGrid', {
					id : 'FOSS_consumer_airBillPaidCodQueryGrid_ID',
					hidden : true,
					height : 480,
					viewConfig : {   
						enableTextSelection: true,           
				        forceFit : true,
				        stripeRows: true,//显示重复样式，不用隔行显示
				        emptyText : consumer.airBillPaidCodIndex.i18n('foss.stl.consumer.common.emptyText')
			    	}
				});
	}

	// 显示到JSP页面
	Ext.create('Ext.panel.Panel', {
		id : 'T_consumer-airBillPaidCodIndex_content',
		cls : "panelContentNToolbar",
		bodyCls : 'panelContentNToolbar-body',
		layout : 'auto',
		items : [ tabPanel, billPaidCodQueryGridId ],
		renderTo : 'T_consumer-airBillPaidCodIndex-body'
	});
});