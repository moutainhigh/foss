closing.mvrPtpAllDetails.MVR_AFR_DETAIL_QUERY_MAX = 31;  //时间间隔最大不超过31天

/**
 * 查询 所有的月报表明细
 */
closing.mvrPtpAllDetails.queryReport = function(form,me){
	//判断是否合法
	if(form.isValid()){
		var grid = Ext.getCmp('T_closing-mvrPtpAllDetails_content').getGrid();
		//设置查询条件
		closing.mvrPtpAllDetails.reportType=form.findField('reportType').getValue();//报表类型
		
		closing.mvrPtpAllDetails.startDate = form.findField('startDate').getValue();
		closing.mvrPtpAllDetails.endDate= form.findField('endDate').getValue();
		closing.mvrPtpAllDetails.productType= form.findField('productType').getValue();
		//合伙人
		closing.mvrPtpAllDetails.customerCode = form.findField('customerCode').getValue();
		closing.mvrPtpAllDetails.origOrgCode= form.findField('origOrgCode').getValue();
		closing.mvrPtpAllDetails.destOrgCode= form.findField('destOrgCode').getValue();
		closing.mvrPtpAllDetails.typeCode= form.findField('typeCode').getValue();
		closing.mvrPtpAllDetails.subTypeCode= form.findField('subTypeCode').getValue();
		
		
		if(Ext.isEmpty(closing.mvrPtpAllDetails.reportType))
		{
			Ext.Msg.alert('温馨提示','报表类型不能为空');
			return false;
		}		
		
		if(closing.mvrPtpAllDetails.startDate==null || closing.mvrPtpAllDetails.startDate==''){
			Ext.Msg.alert('温馨提示','开始日期不能为空');
			return false;
		}

		if(closing.mvrPtpAllDetails.endDate==null || closing.mvrPtpAllDetails.endDate==''){
			Ext.Msg.alert('温馨提示','结束日期不能为空');
			return false;
		}
		var compareTwoDate = stl.compareTwoDate(closing.mvrPtpAllDetails.startDate,closing.mvrPtpAllDetails.endDate);
		if(compareTwoDate>closing.mvrPtpAllDetails.MVR_AFR_DETAIL_QUERY_MAX){
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
		}, 5000);
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
closing.mvrPtpAllDetails.reset = function(){
	this.up('form').getForm().reset();
}

/**
 * 导出
 */
/*closing.mvrAllDetails.exportRfo = function(){

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
}*/

/**
 * 声明指标大类和小类model
 */
Ext.define('Foss.mvrPtpAllDetails.TypeModel',{
	extend:'Ext.data.Model',
	fields:['name','code']
});

/**
 * 声明指标大类store
 */
Ext.define('Foss.mvrPtpAllDetails.TypeStore',{
	extend:'Ext.data.Store',
	model:'Foss.mvrPtpAllDetails.TypeModel',
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
			var form = Ext.getCmp('T_closing-mvrPtpAllDetails_content').getForm().getForm();
			
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
Ext.define('Foss.mvrPtpAllDetails.Model',{
	extend:'Ext.data.Model',
	fields:[{
		name:'businessCase'
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
		name:'recOrgCode'
	},{
		name:'recOrgName'
	},{
		name:'payOrgCode'
	},{
		name:'payOrgName'
	},{
		name:'depOrgCode'
	},{
		name:'depOrgName'
	},{
		name:'generatingOrgCode'
	},{
		name:'generatingOrgName'
	},{
		name:'expenseBearCode'
	},{
		name:'expenseBearName'
	},{
		name:'waybillNo'
	},{
		name:'billNo'
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
		name:'amountBof',
		type:'double'
	},{
		name:'amountDfni',
		type:'double'
	},{
		name:'amountDf',
		type:'double'
	},{
		name:'amountDc',
		type:'double'
	},{
		name:'amountLuf',
		type:'double'
	},{
		name:'amountUldf',
		type:'double'
	},{
		name:'amountSr',
		type:'double'
	},{
		name:'amountOt',
		type:'double'
	},{
		name:'productCode'
	},{
		name:'typeCode'
	},{
		name:'subTypeCode'
	}]
});

/**
 * 声明报表store
 */
Ext.define('Foss.mvrPtpAllDetails.Store',{
	extend:'Ext.data.Store',
	model:'Foss.mvrPtpAllDetails.Model',
	pageSize:100,
	proxy:{
		type:'ajax',
		url:closing.realPath('queryMvrPtpAllDetails.action'),
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
			var form = Ext.getCmp('T_closing-mvrPtpAllDetails_content').getForm().getForm();
			//如果用户直接点击分页查询，即没点击查询按钮，则在此处获取查询条件
			closing.mvrPtpAllDetails.reportType=form.findField('reportType').getValue();
			closing.mvrPtpAllDetails.startDate = form.findField('startDate').getValue();
			closing.mvrPtpAllDetails.endDate= form.findField('endDate').getValue();
			closing.mvrPtpAllDetails.productType= form.findField('productType').getValue();
			closing.mvrPtpAllDetails.customerCode= form.findField('customerCode').getValue();
			closing.mvrPtpAllDetails.origOrgCode= form.findField('origOrgCode').getValue();
			closing.mvrPtpAllDetails.destOrgCode= form.findField('destOrgCode').getValue();
			closing.mvrPtpAllDetails.typeCode= form.findField('typeCode').getValue();
			closing.mvrPtpAllDetails.subTypeCode= form.findField('subTypeCode').getValue();
			var searchParams = {
					'vo.dto.reportType':closing.mvrPtpAllDetails.reportType,
					'vo.dto.startDate':closing.mvrPtpAllDetails.startDate,	
					'vo.dto.endDate':closing.mvrPtpAllDetails.endDate,	
					'vo.dto.productType':closing.mvrPtpAllDetails.productType,
					'vo.dto.customerCode':closing.mvrPtpAllDetails.customerCode,
					'vo.dto.origOrgCode':closing.mvrPtpAllDetails.origOrgCode,
					'vo.dto.destOrgCode':closing.mvrPtpAllDetails.destOrgCode,
					'vo.dto.typeCode':closing.mvrPtpAllDetails.typeCode,
					'vo.dto.subTypeCode':closing.mvrPtpAllDetails.subTypeCode
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

Ext.define('Foss.mvrPtpAllDetails.reportTypeStore',{
	extend:'Ext.data.Store',
	model:'Ext.data.reportTypeModel',
	autoLoad:true,
	data:{
		'items':[
				  {Code:'PTP_DQPA',detailsName:'合伙人德启代付月报表明细'},
				  {Code:'PTP_ST',detailsName:'合伙人股份中转月报表明细'},
				  {Code:'PTP_PSC',detailsName:'合伙人子公司月报表明细'},
				  {Code:'PTP_RP',detailsName:'合伙人奖罚月报表明细'},
				  {Code:'PTP_RPS',detailsName:'合伙人奖罚特殊月报表明细'}
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
var  mvrPtpAllDetailsStore=Ext.create('Foss.mvrPtpAllDetails.reportTypeStore');

/**
 * 基本查询信息
 */
Ext.define('Foss.mvrPtpAllDetails.QueryForm',{
	extend:'Ext.form.Panel',
	layout:'column',
	title:'查询条件',
	frame:true,
	layout:'column',
	defaults:{
		labelWidth:80,
		columnWidth:.20,
		margin:'5 5 5 10'
	},
	items:[{
		xtype: 'combobox',
		fieldLabel:'报表类型',
		name:'reportType',
    	editable:false,
    	allowBlank:false,
    	queryModel:'local',
		store:mvrPtpAllDetailsStore,	
		displayField:'detailsName',
		valueField:'Code',
		value:'',
		columnWidth:.3,
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
	},{
    	xtype:'datefield',
    	name:'startDate',
    	fieldLabel:'起始日期',
    	allowBlank:false,
    	columnWidth:.3,
    	value: stl.getLastMonthFristDay(new Date()),
    	format:'Y-m-d'
 	},{
    	xtype:'datefield',
    	name:'endDate',
    	fieldLabel:'结束日期',
    	allowBlank:false,
    	columnWidth:.3,
    	value:stl.getLastMonthLastDay(new Date()),
    	format:'Y-m-d'
 	},{
 		//合伙人名称
         xtype: 'commonsaledepartmentselector',
        name:'customerCode',
//        emptyText:'手机号可查',
//    	contcatFlag :'Y',//增加按手机号查询
    	singlePeopleFlag:'Y',
        fieldLabel: '合伙人名称',       //合伙人代理
//        allowBlank: false,
        listWidth:300,//设置下拉框宽度
        columnWidth:.3,
        isPaging:true//分页
 	},{
    	xtype:'dynamicorgcombselector',
    	name:'origOrgCode',
    	columnWidth:.3,
    	fieldLabel:'始发部门'
    },{
    	xtype:'dynamicorgcombselector',
    	name:'destOrgCode',
    	columnWidth:.3,
    	fieldLabel:'到达部门'
    },{
    	xtype:'combobox',
    	name:'typeCode',
    	fieldLabel:'指标大类',
		store:Ext.create('Foss.mvrPtpAllDetails.TypeStore'),
	    queryMode: 'remote', 	
		displayField:'name',
//		readOnly:true,
		editable:false,
		valueField:'code',
		columnWidth:.3,
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
    	store:Ext.create('Foss.mvrPtpAllDetails.TypeStore'),
		displayField:'name',
		columnWidth:.3,
		valueField:'code'
    },{
    	xtype:'combobox',
    	name:'productType',
    	fieldLabel:'运输性质',
		store:Ext.create('Foss.pkp.ProductStore'),
	    queryMode: 'local', 	
		displayField:'name',
		columnWidth:.3,
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
			handler:closing.mvrPtpAllDetails.reset
		},{
			xtype:'container',
			border:false,
			html:'&nbsp;',
			columnWidth:.74
		},{
			text:'查询',
			cls:'yellow_button',
			columnWidth:.08,
			handler:function(){
    			var form = this.up('form').getForm();
    			var me = this;
    			closing.mvrPtpAllDetails.queryReport(form,me)
    		}
		}]
  	}]
});

/**
 * 表格
 */
Ext.define('Foss.mvrPtpAllDetails.Grid', {
	extend : 'Ext.grid.Panel',
	title : '报表明细',
	frame : true,
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	height:600,
	store : Ext.create('Foss.mvrPtpAllDetails.Store'),
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
		dataIndex:'businessCase',
		sortable:false,
		hidden:true,
		text:'业务场景'
	},{
		dataIndex:'productCode',
		sortable:false,
		text:'运输性质',
		renderer:function(value){
			return Foss.pkp.ProductData.rendererSubmitToDisplay(value);
		}
	},{
		dataIndex:'customerCode',
		sortable:false,
		text:'合伙人编码'
	},{
		dataIndex:'customerName',
		sortable:false,
		text:'合伙人名称'
	},{
		dataIndex:'origOrgCode',
		sortable:false,
		text:'始发部门编码'
	},{
		dataIndex:'origOrgName',
		sortable:false,
		text:'始发部门名称'
	},{
		dataIndex:'destOrgCode',
		sortable:false,
		text:'到达部门编码'
	},{
		dataIndex:'destOrgName',
		sortable:false,
		text:'到达部门名称'
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
			if(value=='20.YF'){
				return '应付单';
			}else{
				return FossDataDictionary.rendererSubmitToDisplay(value,"BILL_PARENT_TYPE");
			}
  		}
	},{
		dataIndex:'billType',
		sortable:false,
		text:'单据子类型',
		renderer:function(v,m,record){
			if(v=='A'&&record.get('billParentType')=='UF')
			{
				return '空运';
			}else if(v=='PFCP'){
				return '合伙人到付运费应付';
			}else if(v=='PDFP'){
				return '合伙人到达提成应付';
			}else if(v=='PDDF'){
				return '合伙人委托派费应付';
			}else if(v=='PLE'){
				return '合伙人快递差错应付';
			}else if(v=='PB'){
				return '合伙人业务奖励应付';
			}else if(v=='POP'){
				return '合伙人其他应付';
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
		text:'公布价运费'
	},{
		dataIndex:'amountBof',
		sortable:false,
		text:'开单操作费'
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
		dataIndex:'amountDfni',
		sortable:false,
		text:'送货费（不含上楼）'
	},{
		dataIndex:'amountDel',
		sortable:false,
		text:'基础送货费'
	},{
		dataIndex:'amountOt',
		sortable:false,
		text:'其他费用'
	},{
		dataIndex:'amountPup',
		sortable:false,
		text:'接货费'
	},{
		dataIndex:'amountDf',
		sortable:false,
		text:'送货上楼费'
	},{
		dataIndex:'amountDc',
		sortable:false,
		text:'送货进仓费'
	},{
		dataIndex:'amountLuf',
		sortable:false,
		text:'大件上楼费 '
	},{
		dataIndex:'amountUldf',
		sortable:false,
		text:'超远派送费'
	},{
		dataIndex:'amountSr',
		sortable:false,
		text:'签收单返回'
	}],
	/*getExportButton:function(){
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
	},*/
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
				//items:[me.getExportButton()],
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
	var form = Ext.create('Foss.mvrPtpAllDetails.QueryForm');
	var grid = Ext.create('Foss.mvrPtpAllDetails.Grid');
	// 显示到JSP页面
	Ext.create('Ext.panel.Panel', {
		id : 'T_closing-mvrPtpAllDetails_content',
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
		renderTo : 'T_closing-mvrPtpAllDetails-body'
	});
});