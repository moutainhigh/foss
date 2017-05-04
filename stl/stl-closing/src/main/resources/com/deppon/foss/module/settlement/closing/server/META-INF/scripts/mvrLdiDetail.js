closing.mvrLdiDetail.MVR_AFI_DETAIL_QUERY_MAX = 31;  //时间间隔最大不超过31天

/**
 * 查询
 */
closing.mvrLdiDetail.queryReport = function(form,me){
	//判断是否合法
	if(form.isValid()){
		var grid = Ext.getCmp('T_closing-mvrLdiDetail_content').getGrid();
		//设置查询条件
		closing.mvrLdiDetail.startDate = form.findField('startDate').getValue();
		closing.mvrLdiDetail.endDate= form.findField('endDate').getValue();
		closing.mvrLdiDetail.productType= form.findField('productType').getValue();
//		closing.mvrLdiDetail.commoncustomerselector= form.findField('commoncustomerselector').getValue();
//		closing.mvrLdiDetail.commonvehagencycompselector= form.findField('commonvehagencycompselector').getValue();
//		closing.mvrLdiDetail.commonairlinesselector= form.findField('commonairlinesselector').getValue();
//		closing.mvrLdiDetail.commonairagencycompanyselector= form.findField('commonairagencycompanyselector').getValue();
		closing.mvrLdiDetail.landStowage= form.findField('landStowage').getValue();
		
		closing.mvrLdiDetail.origOrgCode= form.findField('origOrgCode').getValue();
		closing.mvrLdiDetail.destOrgCode= form.findField('destOrgCode').getValue();
		closing.mvrLdiDetail.typeCode= form.findField('typeCode').getValue();
		closing.mvrLdiDetail.subTypeCode= form.findField('subTypeCode').getValue();
		
		closing.mvrLdiDetail.customerCode = "";
//		if(closing.mvrLdiDetail.commoncustomerselector != null && closing.mvrLdiDetail.commoncustomerselector != ""){
//			closing.mvrLdiDetail.customerCode = closing.mvrLdiDetail.commoncustomerselector;
//		}else if(closing.mvrLdiDetail.commonvehagencycompselector != null && closing.mvrLdiDetail.commonvehagencycompselector != ""){
//			closing.mvrLdiDetail.customerCode = closing.mvrLdiDetail.commonvehagencycompselector;
//		}else if(closing.mvrLdiDetail.commonairlinesselector != null && closing.mvrLdiDetail.commonairlinesselector != ""){
//			closing.mvrLdiDetail.customerCode = closing.mvrLdiDetail.commonairlinesselector;
//		}else if(closing.mvrLdiDetail.commonairagencycompanyselector != null && closing.mvrLdiDetail.commonairagencycompanyselector != ""){
//			closing.mvrLdiDetail.customerCode = closing.mvrLdiDetail.commonairagencycompanyselector;
//		}else if(!Ext.isEmpty(closing.mvrLdiDetail.landStowage)){
//			closing.mvrLdiDetail.customerCode = closing.mvrLdiDetail.landStowage;
//		}else{
//			closing.mvrLdiDetail.customerCode = "";
//		}
		
		closing.mvrLdiDetail.customerCode = closing.mvrLdiDetail.landStowage;
		if(closing.mvrLdiDetail.startDate==null || closing.mvrLdiDetail.startDate==''){
			Ext.Msg.alert('温馨提示','开始日期不能为空');
			return false;
		}

		if(closing.mvrLdiDetail.endDate==null || closing.mvrLdiDetail.endDate==''){
			Ext.Msg.alert('温馨提示','结束日期不能为空');
			return false;
		}
		var compareTwoDate = stl.compareTwoDate(closing.mvrLdiDetail.startDate,closing.mvrLdiDetail.endDate);
		if(compareTwoDate>closing.mvrLdiDetail.MVR_AFI_DETAIL_QUERY_MAX){
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
closing.mvrLdiDetail.reset = function(){
	this.up('form').getForm().reset();
}

/**
 * 导出
 */
closing.mvrLdiDetail.exportRfo = function(){
	var me = this;
	//获取主面板、查询GRID
	var mainPane = Ext.getCmp('T_closing-mvrLdiDetail_content');
	var queryGrid = mainPane.getGrid();
	//判断是否有数据
	if(queryGrid.store.data.length==0){
		Ext.Msg.alert('温馨提示','表格没有数据，不能进行导出操作！');
		return false;
	}
	
	//提示是否导出
	Ext.Msg.confirm('温馨提示','确定要导出报表?',function(btn,text){
		if('yes' == btn){
//			if(closing.mvrLdiDetail.commoncustomerselector != null && closing.mvrLdiDetail.commoncustomerselector != ""){
//				closing.mvrLdiDetail.customerCode = closing.mvrLdiDetail.commoncustomerselector;
//			}else if(closing.mvrLdiDetail.commonvehagencycompselector != null && closing.mvrLdiDetail.commonvehagencycompselector != ""){
//				closing.mvrLdiDetail.customerCode = closing.mvrLdiDetail.commonvehagencycompselector;
//			}else if(closing.mvrLdiDetail.commonairlinesselector != null && closing.mvrLdiDetail.commonairlinesselector != ""){
//				closing.mvrLdiDetail.customerCode = closing.mvrLdiDetail.commonairlinesselector;
//			}else if(closing.mvrLdiDetail.commonairagencycompanyselector != null && closing.mvrLdiDetail.commonairagencycompanyselector != ""){
//				closing.mvrLdiDetail.customerCode = closing.mvrLdiDetail.commonairagencycompanyselector;
//			}else{
//				closing.mvrLdiDetail.customerCode = "";
//			}
			closing.mvrLdiDetail.customerCode = closing.mvrLdiDetail.landStowage;
			var params  = {
				'vo.dto.startDate':closing.mvrLdiDetail.startDate,	
				'vo.dto.endDate':closing.mvrLdiDetail.endDate,	
				'vo.dto.productType':closing.mvrLdiDetail.productType,
				'vo.dto.customerCode':closing.mvrLdiDetail.customerCode,
				'vo.dto.origOrgCode':closing.mvrLdiDetail.origOrgCode,
				'vo.dto.destOrgCode':closing.mvrLdiDetail.destOrgCode,
				'vo.dto.typeCode':closing.mvrLdiDetail.typeCode,
				'vo.dto.subTypeCode':closing.mvrLdiDetail.subTypeCode,
				'vo.dto.rptType':mvrDetail.rptType_LDI
			};
			
		    //导出Ajax请求
			Ext.Ajax.request({
				url:closing.realPath('mvrDetailExportSynchronized.action'), 
				params:params,
				method:'post',
				success:function(response){
					var result = Ext.JSON.decode(response.responseText);
					me.disable();
					if(result.syncExport){
						
						//创建一个form
						if(!Ext.fly('exportmvrLdiDetailForm')){
							var frm = document.createElement('form');
							frm.id = 'exportmvrLdiDetailForm';
							frm.style.display = 'none';
							document.body.appendChild(frm);
						}
						
						//导出Ajax请求
						Ext.Ajax.request({
							url:closing.realPath('syncExportMvrDetail.action'), 
							form: Ext.fly('exportmvrLdiDetailForm'),
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
									url:closing.realPath('asynExportMvrDetail.action'), 
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
Ext.define('Foss.mvrAfiDetail.TypeModel',{
	extend:'Ext.data.Model',
	fields:['name','code']
});

/**
 * 声明指标大类store
 */
Ext.define('Foss.mvrAfiDetail.TypeStore',{
	extend:'Ext.data.Store',
	model:'Foss.mvrAfiDetail.TypeModel',
	proxy:{
		type:'ajax',
		url:closing.realPath('queryType.action'),
		extraParams:{
			'vo.dto.rptType':mvrDetail.rptType_LDI
		},
		actionMethods:'post',
		reader:{
			type:'json',
			root:'vo.comboList'
		}
	}
});

/*
 * 声明报表模型
 */
Ext.define('Foss.mvrAfiDetail.Model',{
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
		name:'typeCode'
	},{
		name:'subTypeCode'
	},{
		name:'waybillNo'
	},{
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
Ext.define('Foss.mvrAfiDetail.Store',{
	extend:'Ext.data.Store',
	model:'Foss.mvrAfiDetail.Model',
	pageSize:100,
	proxy:{
		type:'ajax',
		url:closing.realPath('queryMvrDetail.action'),
		actionMethods:'post',
		timeout:10*60*1000,
		reader:{
			type:'json',
			root:'vo.resultList',
			totalProperty:'totalCount'
		}
	},
	listeners:{
		'beforeLoad':function(store, operation, eOpts){
			var form = Ext.getCmp('T_closing-mvrLdiDetail_content').getForm().getForm();
			//如果用户直接点击分页查询，即没点击查询按钮，则在此处获取查询条件
			if(Ext.isEmpty(closing.mvrLdiDetail.startDate)){
				closing.mvrLdiDetail.startDate = form.findField('startDate').getValue();
				closing.mvrLdiDetail.endDate= form.findField('endDate').getValue();
				closing.mvrLdiDetail.productType= form.findField('productType').getValue();
//				closing.mvrLdiDetail.commoncustomerselector= form.findField('commoncustomerselector').getValue();
//				closing.mvrLdiDetail.commonvehagencycompselector= form.findField('commonvehagencycompselector').getValue();
//				closing.mvrLdiDetail.commonairlinesselector= form.findField('commonairlinesselector').getValue();
//				closing.mvrLdiDetail.commonairagencycompanyselector= form.findField('commonairagencycompanyselector').getValue();
				closing.mvrLdiDetail.landStowage= form.findField('landStowage').getValue();
				
				closing.mvrLdiDetail.origOrgCode= form.findField('origOrgCode').getValue();
				closing.mvrLdiDetail.destOrgCode= form.findField('destOrgCode').getValue();
				closing.mvrLdiDetail.typeCode= form.findField('typeCode').getValue();
				closing.mvrLdiDetail.subTypeCode= form.findField('subTypeCode').getValue();
			} 
//			if(closing.mvrLdiDetail.commoncustomerselector != null && closing.mvrLdiDetail.commoncustomerselector != ""){
//				closing.mvrLdiDetail.customerCode = closing.mvrLdiDetail.commoncustomerselector;
//			}else if(closing.mvrLdiDetail.commonvehagencycompselector != null && closing.mvrLdiDetail.commonvehagencycompselector != ""){
//				closing.mvrLdiDetail.customerCode = closing.mvrLdiDetail.commonvehagencycompselector;
//			}else if(closing.mvrLdiDetail.commonairlinesselector != null && closing.mvrLdiDetail.commonairlinesselector != ""){
//				closing.mvrLdiDetail.customerCode = closing.mvrLdiDetail.commonairlinesselector;
//			}else if(closing.mvrLdiDetail.commonairagencycompanyselector != null && closing.mvrLdiDetail.commonairagencycompanyselector != ""){
//				closing.mvrLdiDetail.customerCode = closing.mvrLdiDetail.commonairagencycompanyselector;
//			}else if(!Ext.isEmpty(closing.mvrLdiDetail.landStowage)){
//				closing.mvrLdiDetail.customerCode = closing.mvrLdiDetail.landStowage;
//			}else{
//				closing.mvrLdiDetail.customerCode = "";
//			}
			closing.mvrLdiDetail.customerCode = closing.mvrLdiDetail.landStowage;
			var searchParams = {
					'vo.dto.startDate':closing.mvrLdiDetail.startDate,	
					'vo.dto.endDate':closing.mvrLdiDetail.endDate,	
					'vo.dto.productType':closing.mvrLdiDetail.productType,
					'vo.dto.customerCode':closing.mvrLdiDetail.customerCode,
					'vo.dto.origOrgCode':closing.mvrLdiDetail.origOrgCode,
					'vo.dto.destOrgCode':closing.mvrLdiDetail.destOrgCode,
					'vo.dto.typeCode':closing.mvrLdiDetail.typeCode,
					'vo.dto.subTypeCode':closing.mvrLdiDetail.subTypeCode,
					'vo.dto.rptType':mvrDetail.rptType_LDI
				};
			Ext.apply(operation,{
				params :searchParams
			}); 
		}
	}
});

/**
 * 基本查询信息
 */
Ext.define('Foss.mvrAfiDetail.QueryForm',{
	extend:'Ext.form.Panel',
	layout:'column',
	title:'查询条件',
	frame:true,
	layout:'column',
	defaults:{
		labelWidth:65,
		columnWidth:.25,
		margin:'5 5 5 10'
	},
	items:[{
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
 	},
 	/*{
    	xtype: 'combobox',
		fieldLabel:'客户类型',
		name:'customerType',
    	editable:false,
		store:FossDataDictionary.getDataDictionaryStore('SETTLEMENT__CUSTOMER_TYPE',null,null,
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
					commonvehagencycompselector,
					commonairlinesselector,
					commonairagencycompanyselector,landStowage;
				//获取表单	
				form= this.up('form').getForm();
				//获取下面组件,
				commoncustomerselector = form.findField('commoncustomerselector');
				commonvehagencycompselector = form.findField('commonvehagencycompselector');
				commonairlinesselector = form.findField('commonairlinesselector');
				commonairagencycompanyselector = form.findField('commonairagencycompanyselector');
				landStowage= form.findField('landStowage');
				if(newValue=='LC'){
					commoncustomerselector.show();
					commonvehagencycompselector.hide();
					commonvehagencycompselector.setValue("");
					commonairlinesselector.hide();
					commonairlinesselector.setValue("");
					commonairagencycompanyselector.hide();
					commonairagencycompanyselector.setValue("");
					landStowage.hide();
					landStowage.setValue("");
				}else if(newValue=='PA'){
					commoncustomerselector.hide();
					commoncustomerselector.setValue("");
					commonvehagencycompselector.show();
					commonairlinesselector.hide();
					commonairlinesselector.setValue("");
					commonairagencycompanyselector.hide();
					commonairagencycompanyselector.setValue("");
					landStowage.hide();
					landStowage.setValue("");
				}else if(newValue=='A'){
					commoncustomerselector.hide();
					commoncustomerselector.setValue("");
					commonvehagencycompselector.hide();
					commonvehagencycompselector.setValue("");
					commonairlinesselector.show();
					commonairagencycompanyselector.hide();
					commonairagencycompanyselector.setValue("");
					landStowage.hide();
					landStowage.setValue("");
				}else if(newValue=='AA'){
					commoncustomerselector.hide();
					commoncustomerselector.setValue("");
					commonvehagencycompselector.hide();
					commonvehagencycompselector.setValue("");
					commonairlinesselector.hide();
					commonairlinesselector.setValue("");
					commonairagencycompanyselector.show();
					landStowage.hide();
					landStowage.setValue("");
				}else if(newValue=='LS'){
					landStowage.show();
					commoncustomerselector.hide();
					commoncustomerselector.setValue("");
					commonvehagencycompselector.hide();
					commonvehagencycompselector.setValue("");
					commonairlinesselector.hide();
					commonairlinesselector.setValue("");
					commonairagencycompanyselector.hide();
					commonairagencycompanyselector.setValue("");
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
	},*/
	{
		xtype:'commonLdpAgencyCompanySelector',
    	fieldLabel :'快递代理',
    	labelWidth:75,
    	name : 'landStowage',
		isPaging:true 
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
		store:Ext.create('Foss.mvrAfiDetail.TypeStore'),
	    queryMode: 'remote', 	
		displayField:'name',
		valueField:'code',
		listeners:{
			'change':function(th,newValue,oldValue){
				var form = this.up('form');
				//如果不为空，则过滤出小类
				if(!Ext.isEmpty(newValue)){
					Ext.Ajax.request({
						url : closing.realPath('querySubType.action'),
						params : {
							'vo.dto.typeCode':newValue,
							'vo.dto.rptType':mvrDetail.rptType_LDI
						},
						method:'post',		
						success:function(response){
							// 返回冻结后发生更改单的运单号
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
							// 返回冻结后发生更改单的运单号
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
    	store:Ext.create('Foss.mvrAfiDetail.TypeStore'),
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
			handler:closing.mvrLdiDetail.reset
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
    			closing.mvrLdiDetail.queryReport(form,me)
    		}
		}]
  	}]
});

/**
 * 表格
 */
Ext.define('Foss.mvrAfiDetail.Grid', {
	extend : 'Ext.grid.Panel',
	title : '报表明细',
	frame : true,
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	height:600,
	store : Ext.create('Foss.mvrAfiDetail.Store'),
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
				handler:closing.mvrLdiDetail.exportRfo,
				disabled:!closing.mvrLdiDetail.isPermission('/stl-web/closing/exportMvrLdiDetail.action'),
				hidden:!closing.mvrLdiDetail.isPermission('/stl-web/closing/exportMvrLdiDetail.action')
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
	var form = Ext.create('Foss.mvrAfiDetail.QueryForm');
	var grid = Ext.create('Foss.mvrAfiDetail.Grid');
	// 显示到JSP页面
	Ext.create('Ext.panel.Panel', {
		id : 'T_closing-mvrLdiDetail_content',
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
		renderTo : 'T_closing-mvrLdiDetail-body'
	});
});