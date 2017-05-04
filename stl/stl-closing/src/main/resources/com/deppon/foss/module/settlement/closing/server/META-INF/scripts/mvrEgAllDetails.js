closing.mvrEgAllDetails.MVR_RFI_DETAIL_QUERY_MAX = 31; // 时间间隔最大不超过31天

/**
 * 始发往来月报表明细------>所有的往来月报表明细
 */

/**
 * 查询
 */
closing.mvrEgAllDetails.queryReport = function(form, me) {
	// 判断是否合法
	if (form.isValid()) {
		var grid = Ext.getCmp('T_closing-querymvrEgAllDetails_content').getGrid();
		// 设置查询条件
		
		closing.mvrEgAllDetails.reportType=form.findField('reportType').getValue();//报表类型
		//closing.mvrEgAllDetails.voucherMark=form.findField('voucherMark').getValue();//发票标记
		
		closing.mvrEgAllDetails.startDate = form.findField('startDate').getValue();
		closing.mvrEgAllDetails.endDate = form.findField('endDate').getValue();
//		closing.mvrEgAllDetails.productTypeList = form
//				.findField('productTypeList').getValue();
		
		closing.mvrEgAllDetails.commoncustomerselector = form.findField(
				'commoncustomerselector').getValue();
		closing.mvrEgAllDetails.commonvehagencycompselector = form.findField(
				'commonvehagencycompselector').getValue();
		closing.mvrEgAllDetails.commonairlinesselector = form.findField(
				'commonairlinesselector').getValue();
		closing.mvrEgAllDetails.commonairagencycompanyselector = form.findField(
				'commonairagencycompanyselector').getValue();
		closing.mvrEgAllDetails.commonLdpAgencyCompanySelector=form.findField(
		'commonLdpAgencyCompanySelector').getValue();
		
		closing.mvrEgAllDetails.creditOrgCode = form.findField('creditOrgCode')
				.getValue();
		closing.mvrEgAllDetails.debitOrgCode = form.findField('debitOrgCode')
				.getValue();
		closing.mvrEgAllDetails.typeCode = form.findField('typeCode').getValue();
		closing.mvrEgAllDetails.subTypeCode= form.findField('subTypeCode').getValue();
		
		//closing.mvrEgAllDetails.businessCaseList = form.findField('subTypeCode').getValue();

		closing.mvrEgAllDetails.customerCode = "";
		if (closing.mvrEgAllDetails.commoncustomerselector != null
				&& closing.mvrEgAllDetails.commoncustomerselector != "") {
			closing.mvrEgAllDetails.customerCode = closing.mvrEgAllDetails.commoncustomerselector;
		} else if (closing.mvrEgAllDetails.commonvehagencycompselector != null
				&& closing.mvrEgAllDetails.commonvehagencycompselector != "") {
			closing.mvrEgAllDetails.customerCode = closing.mvrEgAllDetails.commonvehagencycompselector;
		} else if (closing.mvrEgAllDetails.commonairlinesselector != null
				&& closing.mvrEgAllDetails.commonairlinesselector != "") {
			closing.mvrEgAllDetails.customerCode = closing.mvrEgAllDetails.commonairlinesselector;
		} else if (closing.mvrEgAllDetails.commonairagencycompanyselector != null
				&& closing.mvrEgAllDetails.commonairagencycompanyselector != "") {
			closing.mvrEgAllDetails.customerCode = closing.mvrEgAllDetails.commonairagencycompanyselector;
		}else if(closing.mvrEgAllDetails.commonLdpAgencyCompanySelector!=null&&closing.mvrEgAllDetails.commonLdpAgencyCompanySelector!=""){
			closing.mvrEgAllDetails.customerCode = closing.mvrEgAllDetails.commonLdpAgencyCompanySelector;
		} else {
			closing.mvrEgAllDetails.customerCode = "";
		}
		
		if(Ext.isEmpty(closing.mvrEgAllDetails.reportType))
		{
			Ext.Msg.alert('温馨提示', '报表类型不能为空');
			return false;
		}
		
//		if(Ext.isEmpty(closing.mvrEgAllDetails.voucherMark))
//		{
//			Ext.Msg.alert('温馨提示', '发票标记不能为空');
//			return false;
//		}
		
		if (closing.mvrEgAllDetails.startDate == null
				|| closing.mvrEgAllDetails.startDate == '') {
			Ext.Msg.alert('温馨提示', '开始日期不能为空');
			return false;
		}

		if (closing.mvrEgAllDetails.endDate == null
				|| closing.mvrEgAllDetails.endDate == '') {
			Ext.Msg.alert('温馨提示', '结束日期不能为空');
			return false;
		}
		var compareTwoDate = stl.compareTwoDate(closing.mvrEgAllDetails.startDate,
				closing.mvrEgAllDetails.endDate);
		if (compareTwoDate > closing.mvrEgAllDetails.MVR_RFI_DETAIL_QUERY_MAX) {
			Ext.Msg.alert('温馨提示', '开始日期和结束日期间隔不能超过31天');
			return false;
		} else if (compareTwoDate < 1) {
			Ext.Msg.alert('温馨提示', '开始日期不能晚于结束日期');
			return false;
		}

		grid.store.removeAll();
		// 设置该按钮灰掉
		me.disable(false);
		// 30秒后自动解除灰掉效果
		setTimeout(function() {
			me.enable(true);
		}, 30000);
		grid.store.loadPage(1, {
			callback : function(records, operation, success) {
				var rawData = grid.store.proxy.reader.rawData;
				// 当success:false ,isException:false --业务异常
				if (!success && !rawData.isException) {
					Ext.Msg.alert('温馨提示', rawData.message);
					me.enable(true);
					return false;
				}
				me.enable(true);
			}
		});
	} else {
		Ext.Msg.alert('温馨提示', '请检查输入条件合法性');
		return false;
	}
}

/**
 * 重置
 */
closing.mvrEgAllDetails.reset = function() {
	this.up('form').getForm().reset();
}

/**
 * 导出
 */
closing.mvrEgAllDetails.exportRfo = function() {

	var me = this;

	// 获取主面板、查询GRID
	var mainPane = Ext.getCmp('T_closing-querymvrEgAllDetails_content');
	var queryGrid = mainPane.getGrid();
	// 判断是否有数据
	if (queryGrid.store.data.length == 0) {
		Ext.Msg.alert('温馨提示', '表格没有数据，不能进行导出操作！');
		return false;
	}

	// 提示是否导出
	Ext.Msg
			.confirm(
					'温馨提示',
					'确定要导出报表?',
					function(btn, text) {
						if ('yes' == btn) {
							if (closing.mvrEgAllDetails.commoncustomerselector != null
									&& closing.mvrEgAllDetails.commoncustomerselector != "") {
								closing.mvrEgAllDetails.customerCode = closing.mvrEgAllDetails.commoncustomerselector;
							} else if (closing.mvrEgAllDetails.commonvehagencycompselector != null
									&& closing.mvrEgAllDetails.commonvehagencycompselector != "") {
								closing.mvrEgAllDetails.customerCode = closing.mvrEgAllDetails.commonvehagencycompselector;
							} else if (closing.mvrEgAllDetails.commonairlinesselector != null
									&& closing.mvrEgAllDetails.commonairlinesselector != "") {
								closing.mvrEgAllDetails.customerCode = closing.mvrEgAllDetails.commonairlinesselector;
							} else if (closing.mvrEgAllDetails.commonairagencycompanyselector != null
									&& closing.mvrEgAllDetails.commonairagencycompanyselector != "") {
								closing.mvrEgAllDetails.customerCode = closing.mvrEgAllDetails.commonairagencycompanyselector;
							}else if(closing.mvrEgAllDetails.commonLdpAgencyCompanySelector!=null&&closing.mvrEgAllDetails.commonLdpAgencyCompanySelector!=""){
								closing.mvrEgAllDetails.customerCode = closing.mvrEgAllDetails.commonLdpAgencyCompanySelector;
							} else {
								closing.mvrEgAllDetails.customerCode = "";
							}
							var params = {
								//'vo.dto.voucherMark':closing.mvrEgAllDetails.voucherMark,
								'vo.dto.startDate' : closing.mvrEgAllDetails.startDate,
								'vo.dto.endDate' : closing.mvrEgAllDetails.endDate,
								// 'vo.dto.productType':closing.mvrEgAllDetails.productType,			
								'vo.dto.creditOrgCode' : closing.mvrEgAllDetails.creditOrgCode,
								'vo.dto.debitOrgCode' : closing.mvrEgAllDetails.debitOrgCode,
								'vo.dto.typeCode':closing.mvrEgAllDetails.typeCode,
								'vo.dto.subTypeCode':closing.mvrEgAllDetails.subTypeCode,
								'vo.dto.reportType' : closing.mvrEgAllDetails.reportType
							};

							Ext.apply(
											params,
											{
//												'vo.dto.productTypeList' : stl
//														.convertProductCode(closing.mvrEgAllDetails.productTypeList),
												'vo.dto.businessCaseList' : stl
														.convertProductCode(closing.mvrEgAllDetails.subTypeCode)		
											});

							// 导出Ajax请求
							Ext.Ajax
									.request({
										url : closing
												.realPath('mvrAllDetailExportSynchronized.action'),
										params : params,
										method : 'post',
										success : function(response) {
											var result = Ext.JSON
													.decode(response.responseText);
											me.disable();
											if (result.syncExport) {

												// 创建一个form
												if (!Ext
														.fly('exportmvrEgAllDetailsForm')) {
													var frm = document
															.createElement('form');
													frm.id = 'exportmvrEgAllDetailsForm';
													frm.style.display = 'none';
													document.body
															.appendChild(frm);
												}

												// 导出Ajax请求
												Ext.Ajax
														.request({
															url : closing
																	.realPath('syncAllExportMvrDetail.action'),
															form : Ext
																	.fly('exportmvrEgAllDetailsForm'),
															params : params,
															method : 'post',
															isUpload : true,
															timeout : 3 * 60 * 1000,
															success : function(
																	response) {
																var result = Ext
																		.decode(response.responseText);
																// 如果异常信息有值，则弹出提示
																if (!Ext
																		.isEmpty(result.errorMessage)) {
																	Ext.Msg
																			.alert(
																					'温馨提示',
																					result.errorMessage);
																	return false;
																}
																Ext.ux.Toast
																		.msg(
																				'温馨提示',
																				'导出成功！',
																				'success',
																				1000);
															},
															failure : function(
																	response) {
																Ext.ux.Toast
																		.msg(
																				'温馨提示',
																				'导出失败！',
																				'error',
																				5000);
															}
														});
												me.enable();
											} else {

												Ext.Msg
														.confirm(
																'温馨提示',
																'导出数据量比较大，系统将自动转为后台导出',
																function(btn) {
																	if (btn == 'yes') {
																		// 导出Ajax请求
																		Ext.Ajax
																				.request({
																					url : closing
																							.realPath('asynAllExportMvrDetail.action'),
																					params : params,
																					method : 'post',
																					timeout : 10 * 60 * 1000,
																					success : function(
																							response) {
																						var result = Ext
																								.decode(response.responseText);
																						// 如果异常信息有值，则弹出提示
																						if (!Ext
																								.isEmpty(result.errorMessage)) {
																							Ext.Msg
																									.alert(
																											'温馨提示',
																											result.errorMessage);
																							return false;
																						}
																						me
																								.enable();
																						Ext.ux.Toast
																								.msg(
																										'温馨提示',
																										'异步导出任务提交成功，稍后请到【运作基础数据->Excel文件下载】功能界面中下载',
																										'success',
																										10000);
																					},
																					failure : function(
																							response) {
																						me
																								.enable();
																						Ext.ux.Toast
																								.msg(
																										'温馨提示',
																										'导出失败！',
																										'error',
																										5000);
																					}
																				});
																	} else {
																		me
																				.enable();
																	}
																});
											}
										},
										failure : function(response) {
											Ext.ux.Toast.msg('温馨提示', '导出失败！',
													'error', 5000);
										}
									});

						}
					});
}

/**
 * 声明指标大类和小类model
 */
Ext.define('Foss.mvrEgAllDetails.TypeModel', {
	extend : 'Ext.data.Model',
	fields : [ 'name', 'code' ]
});

/**
 * 声明指标大类store
 */
Ext.define('Foss.mvrEgAllDetails.TypeStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.mvrEgAllDetails.TypeModel',
	proxy : {
		type : 'ajax',
		url : closing.realPath('queryBigType.action'),
//		,extraParams : {
//			'vo.dto.reportType' : mvrEgAllDetails.rptType_RFI
//		},
		actionMethods : 'post',
		reader : {
			type : 'json',
			root : 'vo.comboList'
		}
	},
	listeners:{
		'beforeLoad':function(store, operation, eOpts){
			var form = Ext.getCmp('T_closing-querymvrEgAllDetails_content').getForm().getForm();
			
			var report=form.findField('reportType').getValue();
			
			var bigType=form.findField('typeCode').getValue();
			
			if(Ext.isEmpty(report))
			{
				//Ext.Msg.alert("报表类型为空，请选择！");
				return false;
			}
			var searchParams={};
			if(!Ext.isEmpty(bigType))
			{
				 searchParams = {	
				'vo.bigSubTypeEntity.tableName':report,
				'vo.bigSubTypeEntity.bigTypeCode':bigType
				}
			}		
			else
			{
				searchParams = {				
				'vo.bigSubTypeEntity.tableName':report
					};
			}
			
			Ext.apply(operation,{
				params :searchParams
			}); 
			}
		}
});

/*
 * 声明报表模型
 */
Ext.define('Foss.mvrEgAllDetails.Model', {
	extend : 'Ext.data.Model',
	fields : [ {
		name:'reportType'
	},{
		name : 'businessCase'
	}, {
		name : 'productCode'
	},{
		name : 'customerType'
	}, {
		name : 'unifiedSettlementType'
	}, {
		name : 'creditOrgCode'
	}, {
		name : 'creditOrgName'
	},{
		name:'creditOrgType'
	}, {
		name:'creditInvoiceMark'
	},{
		name : 'debitOrgCode'
	}, {
		name : 'debitOrgName'
	},{
		name:'debitOrgType'
	},{
		name:'debitInvoiceMark'
	}, {
		name : 'typeCode'
	}, {
		name : 'subTypeCode'
	}, {
		name : 'waybillNo'
	}, {
		name:'billNo'
	},{
		name : 'accountDate',
		type : 'date',
		convert : stl.longToDateConvert
	}, {
		name : 'businessDate',
		type : 'date',
		convert : stl.longToDateConvert
	}, {
		name : 'billParentType'
	}, {
		name : 'billType'
	}, {
		name : 'amount',
		type : 'double'
	}]
});

/**
 * 声明报表store
 */
Ext.define(
				'Foss.mvrEgAllDetails.Store',
				{
					extend : 'Ext.data.Store',
					model : 'Foss.mvrEgAllDetails.Model',
					pageSize : 100,
					proxy : {
						type : 'ajax',
						url : closing.realPath('queryEgDetailsByConditions.action'),
						actionMethods : 'post',
						timeout : 10 * 60 * 1000,
						reader : {
							type : 'json',
							root : 'vo.resultList',
							totalProperty : 'totalCount'
						}
					},
					listeners : {
						'beforeLoad' : function(store, operation, eOpts) {
							var form = Ext.getCmp(
									'T_closing-querymvrEgAllDetails_content').getForm()
									.getForm();
							// 如果用户直接点击分页查询，即没点击查询按钮，则在此处获取查询条件
							if (Ext.isEmpty(closing.mvrEgAllDetails.startDate)) {
								closing.mvrEgAllDetails.reportType=form
								.findField('reportType').getValue();
								
//								closing.mvrEgAllDetails.voucherMark=form
//								.findField('voucherMark').getValue();
								
								closing.mvrEgAllDetails.startDate = form
										.findField('startDate').getValue();
								closing.mvrEgAllDetails.endDate = form.findField(
										'endDate').getValue();
//								closing.mvrEgAllDetails.productTypeList = form
//										.findField('productTypeList')
//										.getValue();
								closing.mvrEgAllDetails.commoncustomerselector = form
										.findField('commoncustomerselector')
										.getValue();
								closing.mvrEgAllDetails.commonvehagencycompselector = form
										.findField(
												'commonvehagencycompselector')
										.getValue();
								closing.mvrEgAllDetails.commonairlinesselector = form
										.findField('commonairlinesselector')
										.getValue();
								closing.mvrEgAllDetails.commonairagencycompanyselector = form
										.findField(
												'commonairagencycompanyselector')
										.getValue();
								closing.mvrEgAllDetails.creditOrgCode = form
										.findField('creditOrgCode').getValue();
								closing.mvrEgAllDetails.debitOrgCode = form
										.findField('debitOrgCode').getValue();
								closing.mvrEgAllDetails.typeCode = form.findField(
										'typeCode').getValue();
								closing.mvrEgAllDetails.subTypeCode = form
										.findField('subTypeCode').getValue();
							}
							if (closing.mvrEgAllDetails.commoncustomerselector != null
									&& closing.mvrEgAllDetails.commoncustomerselector != "") {
								closing.mvrEgAllDetails.customerCode = closing.mvrEgAllDetails.commoncustomerselector;
							} else if (closing.mvrEgAllDetails.commonvehagencycompselector != null
									&& closing.mvrEgAllDetails.commonvehagencycompselector != "") {
								closing.mvrEgAllDetails.customerCode = closing.mvrEgAllDetails.commonvehagencycompselector;
							} else if (closing.mvrEgAllDetails.commonairlinesselector != null
									&& closing.mvrEgAllDetails.commonairlinesselector != "") {
								closing.mvrEgAllDetails.customerCode = closing.mvrEgAllDetails.commonairlinesselector;
							} else if (closing.mvrEgAllDetails.commonairagencycompanyselector != null
									&& closing.mvrEgAllDetails.commonairagencycompanyselector != "") {
								closing.mvrEgAllDetails.customerCode = closing.mvrEgAllDetails.commonairagencycompanyselector;
							} else if(closing.mvrEgAllDetails.commonLdpAgencyCompanySelector!=null&&closing.mvrEgAllDetails.commonLdpAgencyCompanySelector!=""){
								closing.mvrEgAllDetails.customerCode = closing.mvrEgAllDetails.commonLdpAgencyCompanySelector;
							}else {
								closing.mvrEgAllDetails.customerCode = "";
							}
							var searchParams = {
								'vo.dto.reportType':closing.mvrEgAllDetails.reportType,
								//'vo.dto.voucherMark':closing.mvrEgAllDetails.voucherMark,
								
								'vo.dto.startDate' : closing.mvrEgAllDetails.startDate,
								'vo.dto.endDate' : closing.mvrEgAllDetails.endDate,
								// 'vo.dto.productType':closing.mvrEgAllDetails.productType,
								'vo.dto.customerCode' : closing.mvrEgAllDetails.customerCode,
								'vo.dto.creditOrgCode' : closing.mvrEgAllDetails.creditOrgCode,
								'vo.dto.debitOrgCode' : closing.mvrEgAllDetails.debitOrgCode,
								'vo.dto.typeCode' : closing.mvrEgAllDetails.typeCode,
								'vo.dto.subTypeCode' : closing.mvrEgAllDetails.subTypeCode
							};

//							Ext.apply(
//											searchParams,
//											{
//												'vo.dto.productTypeList' : stl
//														.convertProductCode(closing.mvrEgAllDetails.productTypeList)
//											});

							Ext.apply(operation, {
								params : searchParams
							});
						}
					}
				});

Ext.define('Foss.mvrAllExchangeDetails.typeModel',{
	extend:'Ext.data.Model',
	fields:[{
		name:'Code'
	},{
		name:'detailsName'
	}]
});

Ext.define('Foss.mvrAllExchangeDetails.typeStore',{
	extend:'Ext.data.Store',
	model:'Foss.mvrAllExchangeDetails.typeModel',
	autoLoad:true,
	data:{
		'items':[
		          {Code:'NUSI',detailsName:'统一结算往来月报表明细'},
		          {Code:'SNRFI',detailsName:'始发专线到达往来月报表(特殊)明细'},
				  {Code:'NRFI',detailsName:'始发专线到达往来月报表明细'},
				  {Code:'NPLI',detailsName:'始发偏线往来月报表明细'},
				  {Code:'NAFI',detailsName:'始发空运往来月报表明细'},
				  {Code:'DCI',detailsName:'折扣调整往来月报表明细'}
			      ]
	},
	proxy:{
	  		type:'memory',
	  		reader:{
	  			type:'json',
	  			root:'items'
	  		}
	  	}
});
var  mvrAllExchangeDetails=Ext.create('Foss.mvrAllExchangeDetails.typeStore');


Ext.define('Foss.voucherMark.Store',{
extend:'Ext.data.Store',
model:'Foss.mvrAllExchangeDetails.typeModel',
autoLoad:true,
data:{
	'items':[{Code:'INVOICE_TYPE_01',detailsName:'01-运输专票11%'},
	         {Code:'INVOICE_TYPE_02',detailsName:'02-非运输专票'}
	         ]
},
proxy:{
  	type:'memory',
  	reader:{
  		type:'json',
  		root:'items'
  		}
  	}
});
var voucherMarkStore=Ext.create('Foss.voucherMark.Store');

/**
 * 基本查询信息
 */
Ext.define('Foss.mvrEgAllDetails.QueryForm',
				{
					extend : 'Ext.form.Panel',
					layout : 'column',
					title : '查询条件',
					frame : true,
					layout : 'column',
					defaults : {
						labelWidth : 80,
						columnWidth : .25,
						margin : '5 5 5 10'
					},
					items : [
							{
								xtype: 'combobox',
								fieldLabel:'报表类型',
								name:'reportType',
						    	editable:false,
						    	allowBlank:false,
						    	queryModel:'local',
								store:mvrAllExchangeDetails,	
								displayField:'detailsName',
								valueField:'Code',
								value:'',
								listeners:{
									'change':function(th,newValue,oldValue){
										var form = this.up('form');
										
										
										//如果不为空，则过滤出大类
										if(!Ext.isEmpty(newValue)){
											Ext.Ajax.request({
												url : closing.realPath('queryBigType.action'),  
												params : {						
													'vo.bigSubTypeEntity.tableName':newValue
												},
												method:'post',		
												success:function(response){
													// 返回冻结后发生更改单的运单号
													var result = Ext.decode(response.responseText);
													var orgType = form.getForm().findField('typeCode');  //指标大类
													
													var subType = form.getForm().findField('subTypeCode');  //指标小类
													
													//清空大类内容
													orgType.setValue(null);
													
													subType.setValue(null);
													if(!Ext.isEmpty(result.vo.comboList)){
														//重新加载数据
														orgType.store.loadData(result.vo.comboList);
														orgType.expand();
													}
												},
												exception : function(response) {
													// 返回冻结后发生更改单的运单号
													var result = Ext.decode(response.responseText);
													Ext.Msg.alert('温馨提示',result.message);
												}					
											});
										}
									}
								}
							},
//							{
//								xtype : 'combobox',
//								fieldLabel : '发票标记',
//								allowBlank : false,
//								name : 'voucherMark',
//								queryModel : 'local',
//								editable : false,
//								store : voucherMarkStore,
//								displayField : 'detailsName',
//								valueField : 'Code',
//								value : ''
//							},
							{
								xtype : 'datefield',
								name : 'startDate',
								fieldLabel : '起始日期',
								allowBlank : false,
								value : stl.getLastMonthFristDay(new Date()),
								format : 'Y-m-d'
							},
							{
								xtype : 'datefield',
								name : 'endDate',
								fieldLabel : '结束日期',
								allowBlank : false,
								value : stl.getLastMonthLastDay(new Date()),
								format : 'Y-m-d'
							},
							{
								xtype : 'combobox',
								fieldLabel : '客户类型',
								name : 'customerType',
								editable : false,
								store : FossDataDictionary
										.getDataDictionaryStore(
												'SETTLEMENT__CUSTOMER_TYPE',
												null,
												null,
												[
														mvrDetail.SETTLEMENT__CUSTOMER_TYPE__LINE_CUSTOMER,
														mvrDetail.SETTLEMENT__CUSTOMER_TYPE__AIR,
														mvrDetail.SETTLEMENT__CUSTOMER_TYPE__AIR_AGENCY,
														mvrDetail.SETTLEMENT__CUSTOMER_TYPE__PARTIAL_AGENCY,
														mvrDetail.SETTLEMENT__CUSTOMER_TYPE__LANDSTOWAGE]),
								queryModel : 'local',
								displayField : 'valueName',
								valueField : 'valueCode',
								value : mvrDetail.SETTLEMENT__CUSTOMER_TYPE__LINE_CUSTOMER,
								listeners : {
									'change' : function(th, newValue, oldValue) {
										// 获取表单等控件
										var form, // 表单
										commoncustomerselector, 
										commonvehagencycompselector, 
										commonairlinesselector, 
										commonairagencycompanyselector,
										commonLdpAgencyCompanySelector;;
										// 获取表单
										form = this.up('form').getForm();
										// 获取下面组件,
										commoncustomerselector = form
												.findField('commoncustomerselector');
										commonvehagencycompselector = form
												.findField('commonvehagencycompselector');
										commonairlinesselector = form
												.findField('commonairlinesselector');
										commonairagencycompanyselector = form
												.findField('commonairagencycompanyselector');
										commonLdpAgencyCompanySelector=form
										.findField('commonLdpAgencyCompanySelector');
										if (newValue == 'LC') {
											commoncustomerselector.show();
											commonvehagencycompselector.hide();
											commonvehagencycompselector
													.setValue("");
											commonairlinesselector.hide();
											commonairlinesselector.setValue("");
											commonairagencycompanyselector
													.hide();
											commonairagencycompanyselector
													.setValue("");
											commonLdpAgencyCompanySelector.hide();
											commonLdpAgencyCompanySelector.setValue("");
										} else if (newValue == 'PA') {
											commoncustomerselector.hide();
											commoncustomerselector.setValue("");
											commonvehagencycompselector.show();
											commonairlinesselector.hide();
											commonairlinesselector.setValue("");
											commonairagencycompanyselector
													.hide();
											commonairagencycompanyselector
													.setValue("");
											commonLdpAgencyCompanySelector.hide();
											commonLdpAgencyCompanySelector.setValue("");
										} else if (newValue == 'A') {
											commoncustomerselector.hide();
											commoncustomerselector.setValue("");
											commonvehagencycompselector.hide();
											commonvehagencycompselector
													.setValue("");
											commonairlinesselector.show();
											commonairagencycompanyselector
													.hide();
											commonairagencycompanyselector
													.setValue("");
											commonLdpAgencyCompanySelector.hide();
											commonLdpAgencyCompanySelector.setValue("");
										} else if (newValue == 'AA') {
											commoncustomerselector.hide();
											commoncustomerselector.setValue("");
											commonvehagencycompselector.hide();
											commonvehagencycompselector
													.setValue("");
											commonairlinesselector.hide();
											commonairlinesselector.setValue("");
											commonLdpAgencyCompanySelector.hide();
											commonLdpAgencyCompanySelector.setValue("");
											commonairagencycompanyselector
													.show();
										}else if(newValue == 'LS'){
											commoncustomerselector.hide();
											commoncustomerselector.setValue("");
											commonvehagencycompselector.hide();
											commonvehagencycompselector.setValue("");
											commonairlinesselector.hide();
											commonairlinesselector.setValue("");
											commonairagencycompanyselector.hide();
											commonairagencycompanyselector.setValue("");
											commonLdpAgencyCompanySelector.show();
										}
									}
								}
							},
							{
								xtype : 'commoncustomerselector',
								name : 'commoncustomerselector',
								fieldLabel : '客户信息',
								singlePeopleFlag : 'Y',
								listWidth : 300,// 设置下拉框宽度
								isPaging : true
							},
							{
								xtype : 'commonvehagencycompselector',
								fieldLabel : '偏线代理',
								name : 'commonvehagencycompselector',
								hidden : true,
								listWidth : 300,// 设置下拉框宽度
								isPaging : true
							// 分页
							},
							{
								xtype : 'commonairlinesselector',
								fieldLabel : '航空公司',
								name : 'commonairlinesselector',
								hidden : true,
								listWidth : 300,// 设置下拉框宽度
								isPaging : true
							// 分页
							},
							{
								xtype : 'commonallagentselector',
								fieldLabel : '空运代理',
								name : 'commonairagencycompanyselector',
								hidden : true,
								listWidth : 300,// 设置下拉框宽度
								isPaging : true
							// 分页
							},{
								xtype : 'commonLdpAgencyCompanySelector',
								fieldLabel : '快递代理',
								name : 'commonLdpAgencyCompanySelector',
								hidden:true,
								listWidth:300,//设置下拉框宽度
								isPaging:true //分页
							},{
								xtype : 'dynamicorgcombselector',
								name : 'creditOrgCode',
								fieldLabel : '借方部门'
							},
							{
								xtype : 'dynamicorgcombselector',
								name : 'debitOrgCode',
								fieldLabel : '贷方部门'
							},
							{
								xtype : 'combobox',
								name : 'typeCode',
								fieldLabel : '指标大类',
								store : Ext
										.create('Foss.mvrEgAllDetails.TypeStore'),
								queryMode : 'remote',
								displayField : 'name',
								valueField : 'code',
//								readOnly:true,
								editable:false,
								listeners : {
									'change' : function(th, newValue, oldValue) {
										var form = this.up('form');
										
										var report = form.getForm().findField('reportType').getValue();
										
										if(Ext.isEmpty(report))
										{
											//Ext.Msg.alert("报表类型为空，请选择！");
											return false;
										}
										// 如果不为空，则过滤出小类
										if (!Ext.isEmpty(newValue)) {										
											if(newValue=="all")
											{
												return false;
											}
											Ext.Ajax
													.request({
														url : closing
																.realPath('queryNewSubType.action'),
														params : {
															'vo.bigSubTypeEntity.bigTypeCode' : newValue,
															'vo.bigSubTypeEntity.tableName' : report
														},
														method : 'post',
														success : function(
																response) {
															// 返回冻结后发生更改单的运单号
															var result = Ext
																	.decode(response.responseText);
															var orgType = form
																	.getForm()
																	.findField(
																			'subTypeCode');
															// 清空小类内容
															orgType
																	.setValue(null);
															if (!Ext
																	.isEmpty(result.vo.comboList)) {
																// 重新加载数据
																orgType.store
																		.loadData(result.vo.comboList);
																orgType
																		.expand();
															}
														},
														exception : function(
																response) {
															// 返回冻结后发生更改单的运单号
															var result = Ext
																	.decode(response.responseText);
															Ext.Msg
																	.alert(
																			'温馨提示',
																			result.message);
														}
													});
										}
									}
								}
							},
							{
								xtype : 'combobox',
								name : 'subTypeCode',
								fieldLabel : '指标小类',
								//multiSelect : true,
								editable:false,
								queryMode : 'local',
								store : Ext
										.create('Foss.mvrEgAllDetails.TypeStore'),
								displayField : 'name',
								valueField : 'code'
							},
//							{
//								xtype : 'combobox',
//								name : 'productTypeList',
//								fieldLabel : '运输性质',
//								store : Ext.create('Foss.pkp.ProductStore'),
//								multiSelect : true,
//								queryMode : 'local',
//								displayField : 'name',
//								valueField : 'code'
//							},
							{
								border : 1,
								xtype : 'container',
								columnWidth : 1,
								defaultType : 'button',
								layout : 'column',
								items : [
										{
											text : '重置',
											columnWidth : .08,
											handler : closing.mvrEgAllDetails.reset
										},
										{
											xtype : 'container',
											border : false,
											html : '&nbsp;',
											columnWidth : .84
										},
										{
											text : '查询',
											cls : 'yellow_button',
											columnWidth : .08,
											handler : function() {
												var form = this.up('form')
														.getForm();
												var me = this;
												closing.mvrEgAllDetails.queryReport(form, me)
											}
										} ]
							} ]
				});

/**
 * 表格
 */
Ext
		.define(
				'Foss.mvrEgAllDetails.Grid',
				{
					extend : 'Ext.grid.Panel',
					title : '报表明细',
					frame : true,
					bodyCls : 'autoHeight',
					cls : 'autoHeight',
					height : 600,
					store : Ext.create('Foss.mvrEgAllDetails.Store'),
					defaults : {
						align : 'center',
						margin : '5 0 5 0'
					},
					viewConfig : {
						enableTextSelection : true,
						stripeRows : false,// 显示重复样式，不用隔行显示
						getRowClass : function(record, rowIndex, p, store) {
							var count = store.data.length;
							if (count > 0 && rowIndex == store.data.length - 1) {
								return 'closing-totalBgColor';
							}
						}
					},
					columns : [
							{
								dataIndex : 'unifiedSettlementType',
								sortable : false,
								text : '统一结算类型',
                                renderer: function (value, metaData, record, rowIndex, colIndex, store) {
                                        if (rowIndex != store.data.length - 1) {
                                            return value == 'ORIG' ? "始发统一结算" : value == 'DEST' ? "到达统一结算" : "非统一结算";
                                        }
                                    }
							},{
								dataIndex : 'creditOrgName',
								sortable : false,
								text : '借方部门名称'
							},
							{
								dataIndex : 'creditOrgCode',
								sortable : false,
								text : '借方部门编码'
							},{
								dataIndex : 'creditOrgType',
								sortable : false,
								text : '借方部门类型',
								renderer:function(value){
									var display = null;
					    			if(!Ext.isEmpty(value) &&value=='D'){
					    				display = '到达';
					    			}else if(!Ext.isEmpty(value) &&value=='O'){
					    				display = '始发';
					    			} else if (!Ext.isEmpty(value) &&value=='C') {
                                        display = '合同';
                                    }
					    			return display;
								}
							},
							{
								dataIndex : 'creditInvoiceMark',
								sortable : false,
								text : '借方部门发票标记',
								renderer:function(value){
									if(value=='INVOICE_TYPE_01')
										{
											return '01-运输专票11%';
										}
										else if(value=='INVOICE_TYPE_02')
										{
											return '02-非运输专票';
										}
								}
							},
							{
								dataIndex : 'debitOrgName',
								sortable : false,
								text : '贷方部门名称'
							},
							{
								dataIndex : 'debitOrgCode',
								sortable : false,
								text : '贷方部门编码'
							},{
								dataIndex : 'debitOrgType',
								sortable : false,
								text : '贷方部门类型',
                                renderer:function(value){
                                    var display = null;
                                    if(!Ext.isEmpty(value) &&value=='D'){
                                        display = '到达';
                                    }else if(!Ext.isEmpty(value) &&value=='O'){
                                        display = '始发';
                                    } else if (!Ext.isEmpty(value) &&value=='C') {
                                        display = '合同';
                                    }
                                    return display;
                                }
							},
							{
								dataIndex : 'debitInvoiceMark',
								sortable : false,
								text : '贷方部门发票标记',
								renderer:function(value){
									if(value=='INVOICE_TYPE_01')
										{
											return '01-运输专票11%';
										}
										else if(value=='INVOICE_TYPE_02')
										{
											return '02-非运输专票';
										}
								}
							},
							{
								dataIndex : 'typeCode',
								sortable : false,
								text : '指标大类'
							},
							{
								dataIndex : 'subTypeCode',
								sortable : false,
								text : '指标小类'
							},
							{
								dataIndex : 'waybillNo',
								sortable : false,
								text : '单号'
							},{
								dataIndex : 'billNo',
								sortable : false,
								text : '单据编号'
							},{
								dataIndex : 'accountDate',
								sortable : false,
								text : '会计日期',
								renderer : function(value) {
									return stl.dateFormat(value, 'Y-m-d');
								}
							},
							{
								dataIndex : 'businessDate',
								sortable : false,
								text : '业务日期',
								renderer : function(value) {
									return stl.dateFormat(value, 'Y-m-d');
								}
							},
							{
								dataIndex : 'billParentType',
								sortable : false,
								text : '单据类型',
								renderer : function(value) {
									return FossDataDictionary
											.rendererSubmitToDisplay(value,
													"BILL_PARENT_TYPE");
								}
							},
							{
								dataIndex : 'billType',
								sortable : false,
								text : '单据子类型',
								renderer : function(v, m, record) {
									return mvrDetail.billTypeToConvert(v,
											record);
								}
							}, {
								dataIndex : 'amount',
								sortable : false,
								text : '金额'
							}],
					getExportButton : function() {
						var me = this;
						if (Ext.isEmpty(me.exportButton)) {
							me.exportButton = Ext
									.create(
											'Ext.Button',
											{
												text : '导出',
												height : 20,
												handler : closing.mvrEgAllDetails.exportRfo,
												disabled : !closing.mvrEgAllDetails
														.isPermission('/stl-web/closing/mvrAllDetailExportSynchronized.action'),
												hidden : !closing.mvrEgAllDetails
														.isPermission('/stl-web/closing/mvrAllDetailExportSynchronized.action')
											});
						}
						return me.exportButton;
					},
					initComponent : function() {
						var me = this;
						me.dockedItems = [ {
							xtype : 'toolbar',
							dock : 'bottom',
							layout : 'column',
							items : [ {
								xtype : 'standardpaging',
								store : me.store,
								pageSize : 100,
								columnWidth : 1,
								items : [ me.getExportButton() ],
								plugins : Ext.create(
										'Deppon.ux.PageSizePlugin', {
											// 设置分页记录最大值，防止输入过大的数值
											maximumSize : 100,
											sizeList : [ [ '10', 10 ],
													[ '30', 30 ], [ '50', 50 ],
													[ '100', 100 ] ]
										})
							} ]
						} ];
						me.callParent();
					}
				});
Ext.onReady(function() {
	var form = Ext.create('Foss.mvrEgAllDetails.QueryForm');
	var grid = Ext.create('Foss.mvrEgAllDetails.Grid');
	// 显示到JSP页面
	Ext.create('Ext.panel.Panel', {
		id : 'T_closing-querymvrEgAllDetails_content',
		cls : "panelContentNToolbar",
		bodyCls : 'panelContentNToolbar-body',
		getGrid : function() {
			return grid;
		},
		getForm : function() {
			return form;
		},
		layout : 'auto',
		items : [ form, grid ],
		renderTo : 'T_closing-querymvrEgAllDetails-body'
	});
});