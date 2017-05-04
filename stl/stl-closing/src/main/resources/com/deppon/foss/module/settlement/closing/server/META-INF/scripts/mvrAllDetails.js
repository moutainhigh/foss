closing.mvrAllDetails.MVR_AFR_DETAIL_QUERY_MAX = 31;  //时间间隔最大不超过31天

/**
 * 查询 所有的月报表明细
 */
closing.mvrAllDetails.queryReport = function(form,me){
	//判断是否合法
	if(form.isValid()){
		var grid = Ext.getCmp('T_closing-querymvrAllDetails_content').getGrid();
		//设置查询条件
		closing.mvrAllDetails.reportType=form.findField('reportType').getValue();//报表类型
		//closing.mvrAllDetails.voucherMark=form.findField('voucherMark').getValue();//发票标记
		
		closing.mvrAllDetails.startDate = form.findField('startDate').getValue();
		closing.mvrAllDetails.endDate= form.findField('endDate').getValue();
		closing.mvrAllDetails.productType= form.findField('productType').getValue();
		closing.mvrAllDetails.commoncustomerselector= form.findField('commoncustomerselector').getValue();
		closing.mvrAllDetails.commondriverselector= form.findField('commondriverselector').getValue();
		closing.mvrAllDetails.commonvehagencycompselector= form.findField('commonvehagencycompselector').getValue();
		closing.mvrAllDetails.commonairlinesselector= form.findField('commonairlinesselector').getValue();
		closing.mvrAllDetails.commonairagencycompanyselector= form.findField('commonairagencycompanyselector').getValue();
		closing.mvrAllDetails.commonLdpAgencyCompanySelector=form.findField('commonLdpAgencyCompanySelector').getValue();
		closing.mvrAllDetails.packagingSupplierSelector = form.findField('packagingSupplierSelector').getValue();
		closing.mvrAllDetails.origOrgCode= form.findField('origOrgCode').getValue();
		closing.mvrAllDetails.destOrgCode= form.findField('destOrgCode').getValue();
		closing.mvrAllDetails.typeCode= form.findField('typeCode').getValue();
		closing.mvrAllDetails.subTypeCode= form.findField('subTypeCode').getValue();
		
		closing.mvrAllDetails.customerCode = "";
		if(closing.mvrAllDetails.commoncustomerselector != null && closing.mvrAllDetails.commoncustomerselector != ""){
			closing.mvrAllDetails.customerCode = closing.mvrAllDetails.commoncustomerselector;
		}else if(closing.mvrAllDetails.commondriverselector != null && closing.mvrAllDetails.commondriverselector != ""){
			closing.mvrAllDetails.customerCode = closing.mvrAllDetails.commondriverselector;
		}else if(closing.mvrAllDetails.commonvehagencycompselector != null && closing.mvrAllDetails.commonvehagencycompselector != ""){
			closing.mvrAllDetails.customerCode = closing.mvrAllDetails.commonvehagencycompselector;
		}else if(closing.mvrAllDetails.commonairlinesselector != null && closing.mvrAllDetails.commonairlinesselector != ""){
			closing.mvrAllDetails.customerCode = closing.mvrAllDetails.commonairlinesselector;
		}else if(closing.mvrAllDetails.commonairagencycompanyselector != null && closing.mvrAllDetails.commonairagencycompanyselector != ""){
			closing.mvrAllDetails.customerCode = closing.mvrAllDetails.commonairagencycompanyselector;
		}else if(closing.mvrAllDetails.commonLdpAgencyCompanySelector!=null&&closing.mvrAllDetails.commonLdpAgencyCompanySelector!=""){
			closing.mvrAllDetails.customerCode = closing.mvrAllDetails.commonLdpAgencyCompanySelector;
		}else if(closing.mvrAllDetails.packagingSupplierSelector!=null&&closing.mvrAllDetails.packagingSupplierSelector!=""){
			closing.mvrAllDetails.customerCode = closing.mvrAllDetails.packagingSupplierSelector;
		}else{
			closing.mvrAllDetails.customerCode = "";
		}
		
		if(Ext.isEmpty(closing.mvrAllDetails.reportType))
		{
			Ext.Msg.alert('温馨提示','报表类型不能为空');
			return false;
		}		
		
		if(closing.mvrAllDetails.startDate==null || closing.mvrAllDetails.startDate==''){
			Ext.Msg.alert('温馨提示','开始日期不能为空');
			return false;
		}

		if(closing.mvrAllDetails.endDate==null || closing.mvrAllDetails.endDate==''){
			Ext.Msg.alert('温馨提示','结束日期不能为空');
			return false;
		}
		var compareTwoDate = stl.compareTwoDate(closing.mvrAllDetails.startDate,closing.mvrAllDetails.endDate);
		if(compareTwoDate>closing.mvrAllDetails.MVR_AFR_DETAIL_QUERY_MAX){
			Ext.Msg.alert('温馨提示','开始日期和结束日期间隔不能超过31天');
			return false;
		}else if(compareTwoDate<1){
			Ext.Msg.alert('温馨提示','开始日期不能晚于结束日期');
			return false;
		}
		
		grid.store.removeAll();
		
		//设置该按钮灰掉
		me.disable(false);
		//30秒后自动解除灰掉效果
		setTimeout(function() {
			me.enable(true);
		}, 30000);
		grid.store.loadPage(1,{
			callback: function(records, operation, success) {
				var rawData = grid.store.proxy.reader.rawData;
				//当success:false ,isException:false  --业务异常
				if(!success && ! rawData.isException){
					Ext.Msg.alert('温馨提示',rawData.message);
					me.enable(true);
					return false;
				}
				me.enable(true);
		    }
		});
	}else{
		Ext.Msg.alert('温馨提示','请检查输入条件合法性');
		return false;
	}
}

/**
 * 重置
 */
closing.mvrAllDetails.reset = function(){
	this.up('form').getForm().reset();
}

/**
 * 导出
 */
closing.mvrAllDetails.exportRfo = function(){

	var me = this;
	//获取主面板、查询GRID
	var mainPane = Ext.getCmp('T_closing-querymvrAllDetails_content');
	var queryGrid = mainPane.getGrid();
	//判断是否有数据
	if(queryGrid.store.data.length==0){
		Ext.Msg.alert('温馨提示','表格没有数据，不能进行导出操作！');
		return false;
	}
	
	//提示是否导出
	Ext.Msg.confirm('温馨提示','确定要导出报表?',function(btn,text){
		if('yes' == btn){
			if(closing.mvrAllDetails.commoncustomerselector != null && closing.mvrAllDetails.commoncustomerselector != ""){
				closing.mvrAllDetails.customerCode = closing.mvrAllDetails.commoncustomerselector;
			}else if(closing.mvrAllDetails.commondriverselector != null && closing.mvrAllDetails.commondriverselector != ""){
				closing.mvrAllDetails.customerCode = closing.mvrAllDetails.commondriverselector;
			}else if(closing.mvrAllDetails.commonvehagencycompselector != null && closing.mvrAllDetails.commonvehagencycompselector != ""){
				closing.mvrAllDetails.customerCode = closing.mvrAllDetails.commonvehagencycompselector;
			}else if(closing.mvrAllDetails.commonairlinesselector != null && closing.mvrAllDetails.commonairlinesselector != ""){
				closing.mvrAllDetails.customerCode = closing.mvrAllDetails.commonairlinesselector;
			}else if(closing.mvrAllDetails.commonairagencycompanyselector != null && closing.mvrAllDetails.commonairagencycompanyselector != ""){
				closing.mvrAllDetails.customerCode = closing.mvrAllDetails.commonairagencycompanyselector;
			}else if(closing.mvrAllDetails.commonLdpAgencyCompanySelector!=null&&closing.mvrAllDetails.commonLdpAgencyCompanySelector!=""){
				closing.mvrAllDetails.customerCode = closing.mvrAllDetails.commonLdpAgencyCompanySelector;
			}else if(closing.mvrAllDetails.packagingSupplierSelector!=null&&closing.mvrAllDetails.packagingSupplierSelector!=""){
				closing.mvrAllDetails.customerCode = closing.mvrAllDetails.packagingSupplierSelector;
			}else{
				closing.mvrAllDetails.customerCode = "";
			}
			var params  = {
				'vo.dto.reportType':closing.mvrAllDetails.reportType,	
				'vo.dto.startDate':closing.mvrAllDetails.startDate,	
				'vo.dto.endDate':closing.mvrAllDetails.endDate,	
				'vo.dto.productType':closing.mvrAllDetails.productType,
				'vo.dto.customerCode':closing.mvrAllDetails.customerCode,
				'vo.dto.origOrgCode':closing.mvrAllDetails.origOrgCode,
				'vo.dto.destOrgCode':closing.mvrAllDetails.destOrgCode,
				'vo.dto.typeCode':closing.mvrAllDetails.typeCode,
				'vo.dto.subTypeCode':closing.mvrAllDetails.subTypeCode				
			};
			
			//导出Ajax请求
			Ext.Ajax.request({
				url:closing.realPath('mvrAllDetailExportSynchronized.action'), 
				params:params,
				method:'post',
				success:function(response){
					var result = Ext.JSON.decode(response.responseText);
					me.disable();
					if(result.syncExport){
						
						//创建一个form
						if(!Ext.fly('exportmvrAllDetailForm')){
							var frm = document.createElement('form');
							frm.id = 'exportmvrAllDetailForm';
							frm.style.display = 'none';
							document.body.appendChild(frm);
						}
						
						//导出Ajax请求
						Ext.Ajax.request({
							url:closing.realPath('syncAllExportMvrDetail.action'), 
							form: Ext.fly('exportmvrAllDetailForm'),
							params:params,
							method:'post',
							isUpload: true,
							timeout:3*60*1000,
							success:function(response){
								var result = Ext.decode(response.responseText);
						    	//如果异常信息有值，则弹出提示
						    	if(!Ext.isEmpty(result.errorMessage)){
						    		Ext.Msg.alert('温馨提示',result.errorMessage);
						    		return false;
						    	}
								Ext.ux.Toast.msg('温馨提示','导出成功！', 'success', 1000);
							},
						    failure : function(response){
								Ext.ux.Toast.msg('温馨提示','导出失败！', 'error', 5000);
						    }
						});
						me.enable();
					}else{
						
						Ext.Msg.confirm('温馨提示','导出数据量比较大，系统将自动转为后台导出',function(btn){
							if(btn == 'yes'){
								//导出Ajax请求
								Ext.Ajax.request({
									url:closing.realPath('asynAllExportMvrDetail.action'), 
									params:params,
									method:'post',
									timeout:10*60*1000,
									success:function(response){
										var result = Ext.decode(response.responseText);
								    	//如果异常信息有值，则弹出提示
								    	if(!Ext.isEmpty(result.errorMessage)){
								    		Ext.Msg.alert('温馨提示',result.errorMessage);
								    		return false;
								    	}
								    	me.enable();
										Ext.ux.Toast.msg('温馨提示','异步导出任务提交成功，稍后请到【运作基础数据->Excel文件下载】功能界面中下载', 'success', 10000);
									},
								    failure : function(response){
								    	me.enable();
										Ext.ux.Toast.msg('温馨提示','导出失败！', 'error', 5000);
								    }
								});
							}else{
								me.enable();
							}
						});
					}
				},
			    failure : function(response){
					Ext.ux.Toast.msg('温馨提示','导出失败！', 'error', 5000);
			    }
		    });
			
		}
	});	
}

/**
 * 声明指标大类和小类model
 */
Ext.define('Foss.mvrAllDetails.TypeModel',{
	extend:'Ext.data.Model',
	fields:['name','code']
});

/**
 * 声明指标大类store
 */
Ext.define('Foss.mvrAllDetails.TypeStore',{
	extend:'Ext.data.Store',
	model:'Foss.mvrAllDetails.TypeModel',
	proxy:{
		type:'ajax',
		url:closing.realPath('queryBigType.action'),
//		extraParams:{
//			'vo.dto.rptType':mvrAllDetails.rptType_AFR
//		},		
		actionMethods:'post',
		reader:{
			type:'json',
			root:'vo.comboList'
		}
	},
	listeners:{
		'beforeload':function(store, operation, eOpts){
			var form = Ext.getCmp('T_closing-querymvrAllDetails_content').getForm().getForm();
			
			var report=form.findField('reportType').getValue();
			
			var bigType=form.findField('typeCode').getValue();
			
			if(Ext.isEmpty(report))
			{
				
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
Ext.define('Foss.mvrAllDetails.Model',{
	extend:'Ext.data.Model',
	fields:[{
		name:'businessCase'
	},{
		name:'productCode'
	},{
		name:'customerCode'
	},{
		name:'customerName'
	},{
		name:'customerType'
	},{
		name:'origOrgCode'
	},{
		name:'origOrgName'
	},{
		name:'destOrgCode'
	},{
		name:'destOrgName'
	},{
		name:'unifiedSettlementType'
	},{
		name:'contractOrgCode'
	},{
		name:'contractOrgName'
	},{
		name:'typeCode'
	},{
		name:'subTypeCode'
	},{
		name:'waybillNo'
	},{
		name:'billNo'
	},
	{
		name:'accountDate',
		type:'date',
		convert:stl.longToDateConvert
	},{
		name:'businessDate',
		type:'date',
		convert:stl.longToDateConvert
	},{
		name:'billParentType'
	},{
		name:'billType'
	},{
		name:'amount',
		type:'double'
	},{
		name:'amountFrt',
		type:'double'
	},{
		name:'amountPup',
		type:'double'
	},{
		name:'amountDel',
		type:'double'
	},{
		name:'amountPkg',
		type:'double'
	},{
		name:'amountDv',
		type:'double'
	},{
		name:'amountCod',
		type:'double'
	},{
		name:'amountOt',
		type:'double'
	}]
});

/**
 * 声明报表store
 */
Ext.define('Foss.mvrAllDetails.Store',{
	extend:'Ext.data.Store',
	model:'Foss.mvrAllDetails.Model',
	pageSize:100,
	proxy:{
		type:'ajax',
		url:closing.realPath('queryAllDetailsByConditions.action'),
		actionMethods:'post',
		timeout:10*60*1000,
		reader:{
			type:'json',
			root:'vo.resultList',
			totalProperty:'totalCount'
		}
	},
	listeners:{
		'beforeload':function(store, operation, eOpts){
			var form = Ext.getCmp('T_closing-querymvrAllDetails_content').getForm().getForm();
			//如果用户直接点击分页查询，即没点击查询按钮，则在此处获取查询条件
			if(Ext.isEmpty(closing.mvrAllDetails.startDate)||
				Ext.isEmpty(closing.mvrAllDetails.reportType)){
				closing.mvrAllDetails.reportType=form.findField('reportType').getValue();
				closing.mvrAllDetails.startDate = form.findField('startDate').getValue();
				closing.mvrAllDetails.endDate= form.findField('endDate').getValue();
				closing.mvrAllDetails.productType= form.findField('productType').getValue();
				closing.mvrAllDetails.commoncustomerselector= form.findField('commoncustomerselector').getValue();
				closing.mvrAllDetails.commondriverselector= form.findField('commondriverselector').getValue();
				closing.mvrAllDetails.commonvehagencycompselector= form.findField('commonvehagencycompselector').getValue();
				closing.mvrAllDetails.commonairlinesselector= form.findField('commonairlinesselector').getValue();
				closing.mvrAllDetails.commonairagencycompanyselector= form.findField('commonairagencycompanyselector').getValue();
				closing.mvrAllDetails.origOrgCode= form.findField('origOrgCode').getValue();
				closing.mvrAllDetails.destOrgCode= form.findField('destOrgCode').getValue();
				closing.mvrAllDetails.typeCode= form.findField('typeCode').getValue();
				closing.mvrAllDetails.subTypeCode= form.findField('subTypeCode').getValue();
			} 
			if(closing.mvrAllDetails.commoncustomerselector != null && closing.mvrAllDetails.commoncustomerselector != ""){
				closing.mvrAllDetails.customerCode = closing.mvrAllDetails.commoncustomerselector;
			}else if(closing.mvrAllDetails.commondriverselector != null && closing.mvrAllDetails.commondriverselector != ""){
				closing.mvrAllDetails.customerCode = closing.mvrAllDetails.commondriverselector;
			}else if(closing.mvrAllDetails.commonvehagencycompselector != null && closing.mvrAllDetails.commonvehagencycompselector != ""){
				closing.mvrAllDetails.customerCode = closing.mvrAllDetails.commonvehagencycompselector;
			}else if(closing.mvrAllDetails.commonairlinesselector != null && closing.mvrAllDetails.commonairlinesselector != ""){
				closing.mvrAllDetails.customerCode = closing.mvrAllDetails.commonairlinesselector;
			}else if(closing.mvrAllDetails.commonairagencycompanyselector != null && closing.mvrAllDetails.commonairagencycompanyselector != ""){
				closing.mvrAllDetails.customerCode = closing.mvrAllDetails.commonairagencycompanyselector;
			}else if(closing.mvrAllDetails.commonLdpAgencyCompanySelector!=null&&closing.mvrAllDetails.commonLdpAgencyCompanySelector!=""){
				closing.mvrAllDetails.customerCode = closing.mvrAllDetails.commonLdpAgencyCompanySelector;
			}else{
				closing.mvrAllDetails.customerCode = "";
			}
			var searchParams = {
					'vo.dto.reportType':closing.mvrAllDetails.reportType,
					'vo.dto.startDate':closing.mvrAllDetails.startDate,	
					'vo.dto.endDate':closing.mvrAllDetails.endDate,	
					'vo.dto.productType':closing.mvrAllDetails.productType,
					'vo.dto.customerCode':closing.mvrAllDetails.customerCode,
					'vo.dto.origOrgCode':closing.mvrAllDetails.origOrgCode,
					'vo.dto.destOrgCode':closing.mvrAllDetails.destOrgCode,
					'vo.dto.typeCode':closing.mvrAllDetails.typeCode,
					'vo.dto.subTypeCode':closing.mvrAllDetails.subTypeCode
					//'vo.dto.businessCaseList' : stl.convertProductCode(closing.mvrAllDetails.subTypeCode)	
				};
			Ext.apply(operation,{
				params :searchParams
			}); 
		}
	}
});

Ext.define('Ext.data.reportTypeModel',{
	extend:'Ext.data.Model',
	fields:[{
		name:'Code'
	},{
		name:'detailsName'
	}]
});

Ext.define('Foss.mvrAllDetails.reportTypeStore',{
	extend:'Ext.data.Store',
	model:'Ext.data.reportTypeModel',
	autoLoad:true,
	data:{
		'items':[
				  {Code:'NRFONO',detailsName:'01普通业务始发月报表明细'},
				  {Code:'NRFOSO',detailsName:'01特殊业务始发月报表明细'},
				  {Code:'NRFONT',detailsName:'02普通业务始发月报表明细'},
				  {Code:'NRFOST',detailsName:'02特殊业务始发月报表明细'},
				  {Code:'NRFDO',detailsName:'01到达月报表明细'},
				  {Code:'NRFDT',detailsName:'02到达月报表明细'},
				  {Code:'SNRFDT',detailsName:'02特殊到达月报表明细'},
				  {Code:'NPLR',detailsName:'偏线月报表明细'},
				  {Code:'NAFR',detailsName:'空运月报表明细'},
				  {Code:'ORCC',detailsName:'外请车月报表明细'},
				  {Code:'WOODEN',detailsName:'代打木架'},
				  {Code:'DCO',detailsName:'折扣调整始发月报表明细'},
				  {Code:'DCD',detailsName:'折扣调整到达月报表明细'},
                  {Code:'HI',detailsName:'家装业务月报表明细'}
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
var  mvrAllDetailsStore=Ext.create('Foss.mvrAllDetails.reportTypeStore');

//Ext.define('Foss.voucherMark.Store',{
//	extend:'Ext.data.Store',
//	model:'Foss.mvrAllDetails.Model',
//	autoLoad:true,
//	data:{
//		'items':[{Code:'01',detailsName:'运输专票11%'},
//		         {Code:'02',detailsName:'非运输专票'}
//		         ]
//	},
//	proxy:{
//	  	type:'memory',
//	  	reader:{
//	  		type:'json',
//	  		root:'items'
//	  		}
//	  	}
//	});
//var voucherMarkStore=Ext.create('Foss.voucherMark.Store');

/**
 * 基本查询信息
 */
Ext.define('Foss.mvrAllDetails.QueryForm',{
	extend:'Ext.form.Panel',
	layout:'column',
	title:'查询条件',
	frame:true,
	layout:'column',
	defaults:{
		labelWidth:80,
		columnWidth:.25,
		margin:'5 5 5 10'
	},
	items:[{
		xtype: 'combobox',
		fieldLabel:'报表类型',
		name:'reportType',
    	editable:false,
    	allowBlank:false,
    	queryModel:'local',
		store:mvrAllDetailsStore,	
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
								
								var comboList = result.vo.comboList;
								
								orgType.store.loadData(comboList);
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
//	{
//		xtype: 'combobox',
//		fieldLabel:'发票标记',
//		allowBlank:false,
//		name:'voucherMark',
//		queryModel:'local',
//    	editable:false,
//		store:voucherMarkStore,	
//		displayField:'detailsName',
//		valueField:'Code',
//		value:''
//	},
	{
    	xtype:'datefield',
    	name:'startDate',
    	fieldLabel:'起始日期',
    	allowBlank:false,
    	value: stl.getLastMonthFristDay(new Date()),
    	format:'Y-m-d'
 	},{
    	xtype:'datefield',
    	name:'endDate',
    	fieldLabel:'结束日期',
    	allowBlank:false,
    	value:stl.getLastMonthLastDay(new Date()),
    	format:'Y-m-d'
 	},{
    	xtype: 'combobox',
		fieldLabel:'客户类型',
		name:'customerType',
    	editable:false,
		store:FossDataDictionary.getDataDictionaryStore('SETTLEMENT__CUSTOMER_TYPE',null,[{
			 'valueCode': mvrDetail.SETTLEMENT__CUSTOMER_TYPE__DRIVER,
       		 'valueName': '司机'
		},{
			'valueCode': mvrDetail.SETTLEMENT__CUSTOMER_TYPE__PACKAGENCE,
       		 'valueName': '包装供应商'
		}],
				[mvrDetail.SETTLEMENT__CUSTOMER_TYPE__LINE_CUSTOMER,
				 mvrDetail.SETTLEMENT__CUSTOMER_TYPE__AIR,
				 mvrDetail.SETTLEMENT__CUSTOMER_TYPE__AIR_AGENCY,
				 mvrDetail.SETTLEMENT__CUSTOMER_TYPE__PARTIAL_AGENCY,
				 mvrDetail.SETTLEMENT__CUSTOMER_TYPE__LANDSTOWAGE]),
		queryModel:'local',
		displayField:'valueName',
		valueField:'valueCode',
		value:mvrDetail.SETTLEMENT__CUSTOMER_TYPE__LINE_CUSTOMER,
    	listeners:{
		    	'change':function(th,newValue,oldValue){
				//获取表单等控件
				var form,//表单
					commoncustomerselector,
					commondriverselector,
					commonvehagencycompselector,
					commonairlinesselector,
					commonairagencycompanyselector,
					commonLdpAgencyCompanySelector,
					packagingSupplierSelector;
				//获取表单	
				form= this.up('form').getForm();
				//获取下面组件,
				commoncustomerselector = form.findField('commoncustomerselector');
				commondriverselector = form.findField('commondriverselector');
				commonvehagencycompselector = form.findField('commonvehagencycompselector');
				commonairlinesselector = form.findField('commonairlinesselector');
				commonairagencycompanyselector = form.findField('commonairagencycompanyselector');
				commonLdpAgencyCompanySelector=form.findField('commonLdpAgencyCompanySelector');
				packagingSupplierSelector = form.findField('packagingSupplierSelector');
				if(newValue=='LC'){
					commoncustomerselector.show();
					commonvehagencycompselector.hide();
					commonvehagencycompselector.setValue("");
					commonairlinesselector.hide();
					commonairlinesselector.setValue("");
					commonairagencycompanyselector.hide();
					commonairagencycompanyselector.setValue("");
					commonLdpAgencyCompanySelector.hide();
					commonLdpAgencyCompanySelector.setValue("");
					commondriverselector.hide();
					commondriverselector.setValue("");
					packagingSupplierSelector.hide();
					packagingSupplierSelector.setValue("");
				}else if(newValue=='D'){
					commondriverselector.show();
					commoncustomerselector.hide();
					commoncustomerselector.setValue("");
					commonairlinesselector.hide();
					commonairlinesselector.setValue("");
					commonairagencycompanyselector.hide();
					commonairagencycompanyselector.setValue("");
					commonLdpAgencyCompanySelector.hide();
					commonLdpAgencyCompanySelector.setValue("");	
					commonvehagencycompselector.hide();
					commonvehagencycompselector.setValue("");
					packagingSupplierSelector.hide();
					packagingSupplierSelector.setValue("");
				}else if(newValue=='PA'){
					commoncustomerselector.hide();
					commoncustomerselector.setValue("");
					commonvehagencycompselector.show();
					commonairlinesselector.hide();
					commonairlinesselector.setValue("");
					commonairagencycompanyselector.hide();
					commonairagencycompanyselector.setValue("");
					commonLdpAgencyCompanySelector.hide();
					commonLdpAgencyCompanySelector.setValue("");
					commondriverselector.hide();
					commondriverselector.setValue("");
					packagingSupplierSelector.hide();
					packagingSupplierSelector.setValue("");
				}else if(newValue=='A'){
					commoncustomerselector.hide();
					commoncustomerselector.setValue("");
					commonvehagencycompselector.hide();
					commonvehagencycompselector.setValue("");
					commonairlinesselector.show();
					commonairagencycompanyselector.hide();
					commonairagencycompanyselector.setValue("");
					commonLdpAgencyCompanySelector.hide();
					commonLdpAgencyCompanySelector.setValue("");
					commondriverselector.hide();
					commondriverselector.setValue("");
					packagingSupplierSelector.hide();
					packagingSupplierSelector.setValue("");
				}else if(newValue=='AA'){
					commoncustomerselector.hide();
					commoncustomerselector.setValue("");
					commonvehagencycompselector.hide();
					commonvehagencycompselector.setValue("");
					commonairlinesselector.hide();
					commonairlinesselector.setValue("");
					commonLdpAgencyCompanySelector.hide();
					commonLdpAgencyCompanySelector.setValue("");
					commonairagencycompanyselector.show();
					commondriverselector.hide();
					commondriverselector.setValue("");
					packagingSupplierSelector.hide();
					packagingSupplierSelector.setValue("");
				}else if(newValue='LS'){
					commoncustomerselector.hide();
					commoncustomerselector.setValue("");
					commonvehagencycompselector.hide();
					commonvehagencycompselector.setValue("");
					commonairlinesselector.hide();
					commonairlinesselector.setValue("");
					commonairagencycompanyselector.hide();
					commonairagencycompanyselector.setValue("");
					commonLdpAgencyCompanySelector.show();
					commondriverselector.hide();
					commondriverselector.setValue("");
					packagingSupplierSelector.hide();
					packagingSupplierSelector.setValue("");
					
				}else if(newValue='PKA'){
					commoncustomerselector.hide();
					commoncustomerselector.setValue("");
					commonvehagencycompselector.hide();
					commonvehagencycompselector.setValue("");
					commonairlinesselector.hide();
					commonairlinesselector.setValue("");
					commonairagencycompanyselector.hide();
					commonairagencycompanyselector.setValue("");
					commonLdpAgencyCompanySelector.hide();
					commonLdpAgencyCompanySelector.setValue("");
					commondriverselector.hide();
					commondriverselector.setValue("");
					packagingSupplierSelector.show();
				
				}
			}
		}
    },{
    	xtype:'commoncustomerselector',
    	name:'commoncustomerselector',
    	fieldLabel:'客户信息',
    	singlePeopleFlag : 'Y',
		listWidth:300,//设置下拉框宽度
		isPaging:true
 	},{
    	xtype:'commondriverselector',
    	name:'commondriverselector',
    	fieldLabel:'司机信息',
    	hidden:true,
		listWidth:300,//设置下拉框宽度
		isPaging:true
 	},{
		xtype : 'commonvehagencycompselector',
		fieldLabel : '偏线代理',
		name : 'commonvehagencycompselector',
		hidden:true,
		listWidth:300,//设置下拉框宽度
		isPaging:true //分页
	},{
		xtype : 'commonairlinesselector',
		fieldLabel : '航空公司',
		name : 'commonairlinesselector',
		hidden:true,
		listWidth:300,//设置下拉框宽度
		isPaging:true //分页
	},{
		xtype : 'commonallagentselector',
		fieldLabel : '空运代理',
		name : 'commonairagencycompanyselector',
		hidden:true,
		listWidth:300,//设置下拉框宽度
		isPaging:true //分页
	},{
		xtype : 'commonLdpAgencyCompanySelector',
		fieldLabel : '快递代理',
		name : 'commonLdpAgencyCompanySelector',
		hidden:true,
		listWidth:300,//设置下拉框宽度
		isPaging:true //分页
	},{
    	xtype:'dynamicPackagingSupplierSelector',
	    listWidth:300,
	    name:'packagingSupplierSelector',
	    fieldLabel : '包装代理',
	    active:'Y',
	    hidden:true
 	},{
    	xtype:'dynamicorgcombselector',
    	name:'origOrgCode',
    	fieldLabel:'始发部门'
    },{
    	xtype:'dynamicorgcombselector',
    	name:'destOrgCode',
    	fieldLabel:'到达部门'
    },{
    	xtype:'combobox',
    	name:'typeCode',
    	fieldLabel:'指标大类',
		store:Ext.create('Foss.mvrAllDetails.TypeStore'),
	    queryMode: 'remote', 	
		displayField:'name',
//		readOnly:true,
		editable:false,
		valueField:'code',
		listeners:{
			'change':function(th,newValue,oldValue){
				var form = this.up('form');
				
				var report = form.getForm().findField('reportType').getValue();
				
				if(Ext.isEmpty(report))
				{					
					return false;
				}
								
				//如果不为空，则过滤出小类
				if(!Ext.isEmpty(newValue)){
					
					if(newValue=="all")
					{
						return false;
					}
					Ext.Ajax.request({
						url : closing.realPath('queryNewSubType.action'),
						params : {
							'vo.bigSubTypeEntity.bigTypeCode':newValue,
							'vo.bigSubTypeEntity.tableName':report
						},
						method:'post',		
						success:function(response){
							
							var result = Ext.decode(response.responseText);
							var orgType = form.getForm().findField('subTypeCode');
							//清空小类内容
							orgType.setValue(null);
							if(!Ext.isEmpty(result.vo.comboList)){
								//重新加载数据
								orgType.store.loadData(result.vo.comboList);
								orgType.expand();
							}
						},
						exception : function(response) {
							
							var result = Ext.decode(response.responseText);
							Ext.Msg.alert('温馨提示',result.message);
						}					
					});
				}
			}
		}
    },{
    	xtype:'combobox',
    	name:'subTypeCode',
    	fieldLabel:'指标小类',
    	queryMode: 'local', 
    	//multiSelect : true,
    	editable:false,
    	store:Ext.create('Foss.mvrAllDetails.TypeStore'),
		displayField:'name',
		valueField:'code'
    },{
    	xtype:'combobox',
    	name:'productType',
    	fieldLabel:'运输性质',
		store:Ext.create('Foss.pkp.ProductStore'),
	    queryMode: 'local', 	
		displayField:'name',
		valueField:'code'
    },{
		border: 1,
		xtype:'container',
		columnWidth:1,
		defaultType:'button',
		layout:'column',
		items:[{
			text:'重置',
			columnWidth:.08,
			handler:closing.mvrAllDetails.reset
		},{
			xtype:'container',
			border:false,
			html:'&nbsp;',
			columnWidth:.84
		},{
			text:'查询',
			cls:'yellow_button',
			columnWidth:.08,
			handler:function(){
    			var form = this.up('form').getForm();
    			var me = this;
    			closing.mvrAllDetails.queryReport(form,me)
    		}
		}]
  	}]
});

/**
 * 表格
 */
Ext.define('Foss.mvrAllDetails.Grid', {
	extend : 'Ext.grid.Panel',
	title : '报表明细',
	frame : true,
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	height:600,
	store : Ext.create('Foss.mvrAllDetails.Store'),
	defaults : {
		align : 'center',
		margin : '5 0 5 0'
	},
	viewConfig : {
		enableTextSelection : true,
		stripeRows: false,//显示重复样式，不用隔行显示
  	 	getRowClass:function(record, rowIndex, p, store){
  	 		var count = store.data.length;
  	 		if(count>0 && rowIndex==store.data.length-1) {
  	 			return 'closing-totalBgColor';
  	 		} 
  	 	}
	},
	columns : [{
		dataIndex:'productCode',
		sortable:false,
		text:'运输性质',
		renderer:function(value){
			return Foss.pkp.ProductData.rendererSubmitToDisplay(value);
		}
	},{
		dataIndex:'customerName',
		sortable:false,
		text:'客户名称'
	},{
		dataIndex:'customerCode',
		sortable:false,
		text:'客户编码'
	},{
		dataIndex:'origOrgName',
		sortable:false,
		text:'始发部门名称'
	},{
		dataIndex:'origOrgCode',
		sortable:false,
		text:'始发部门编码'
	},{
		dataIndex:'destOrgName',
		sortable:false,
		text:'到达部门名称'
	},{
		dataIndex:'destOrgCode',
		sortable:false,
		text:'到达部门编码'
	},{
		dataIndex:'unifiedSettlementType',
		sortable:false,
		text:'统一结算类型',
        renderer: function (value, metaData, record, rowIndex, colIndex, store) {
            if (rowIndex != store.data.length - 1) {
                return value == 'ORIG' ? "始发统一结算" : value == 'DEST' ? "到达统一结算" : "非统一结算";
            }
        }
	},{
		dataIndex:'contractOrgName',
		sortable:false,
		text:'合同部门名称'
	},{
		dataIndex:'contractOrgCode',
		sortable:false,
		text:'合同部门编码'
	},{
		dataIndex:'typeCode',
		sortable:false,
		text:'指标大类'
	},{
		dataIndex:'subTypeCode',
		sortable:false,
		text:'指标小类'
	},{
		dataIndex:'waybillNo',
		sortable:false,
		text:'单号'
	},{
		dataIndex:'billNo',
		sortable:false,
		text:'单据编号'
	},{
		dataIndex:'accountDate',
		sortable:false,
		text:'会计日期',
		renderer:function(value){
			return stl.dateFormat(value,'Y-m-d');
		}
	},{
		dataIndex:'businessDate',
		sortable:false,
		text:'业务日期',
		renderer:function(value){
			return stl.dateFormat(value,'Y-m-d');
		}
	},{
		dataIndex:'billParentType',
		sortable:false,
		text:'单据类型',
		renderer:function(value){
			return FossDataDictionary.rendererSubmitToDisplay(value,"BILL_PARENT_TYPE");
  		}
	},{
		dataIndex:'billType',
		sortable:false,
		text:'单据子类型',
		renderer:function(v,m,record){
			if(v=='A'&&record.get('billParentType')=='UF')
			{
				return '空运';
			}
			else
			return mvrDetail.billTypeToConvert(v,record);
		}
	},{
		dataIndex:'amount',
		sortable:false,
		text:'金额'
	},{
		dataIndex:'amountFrt',
		sortable:false,
		text:'运费'
	},{
		dataIndex:'amountPup',
		sortable:false,
		text:'接货费'
	},{
		dataIndex:'amountDel',
		sortable:false,
		text:'送货费'
	},{
		dataIndex:'amountPkg',
		sortable:false,
		text:'包装费'
	},{
		dataIndex:'amountDv',
		sortable:false,
		text:'保价费'
	},{
		dataIndex:'amountCod',
		sortable:false,
		text:'代收货款手续费'
	},{
		dataIndex:'amountOt',
		sortable:false,
		text:'其他费用'
	}],
	getExportButton:function(){
		var me = this;
		if(Ext.isEmpty(me.exportButton)){
			me.exportButton = Ext.create('Ext.Button',{
				text:'导出',
				height:20,
				handler:closing.mvrAllDetails.exportRfo,
				disabled:!closing.mvrAllDetails.isPermission('/stl-web/closing/mvrAllDetailExportSynchronized.action'),
				hidden:!closing.mvrAllDetails.isPermission('/stl-web/closing/mvrAllDetailExportSynchronized.action')
			});
		}
		return me.exportButton;
	},
    initComponent:function(){
		var me = this;
		me.dockedItems =[{
	   		xtype: 'toolbar',
		    dock: 'bottom',
		    layout:'column',		    		
		    items: [{
				xtype:'standardpaging',
				store:me.store,
				pageSize:100,
				columnWidth:1,
				items:[me.getExportButton()],
				plugins: Ext.create('Deppon.ux.PageSizePlugin', {
					//设置分页记录最大值，防止输入过大的数值
					maximumSize: 100,
					sizeList: [['10', 10], ['30', 30], ['50', 50], ['100', 100]]
				})
			 }]
   		 }];
   		 me.callParent();
    }
});
Ext.onReady(function() {
	var form = Ext.create('Foss.mvrAllDetails.QueryForm');
	var grid = Ext.create('Foss.mvrAllDetails.Grid');
	// 显示到JSP页面
	Ext.create('Ext.panel.Panel', {
		id : 'T_closing-querymvrAllDetails_content',
		cls : "panelContentNToolbar",
		bodyCls : 'panelContentNToolbar-body',
		getGrid:function(){
			return grid;
		},
		getForm:function(){
			return form;
		},
		layout : 'auto',
		items : [form,grid],
		renderTo : 'T_closing-querymvrAllDetails-body'
	});
});