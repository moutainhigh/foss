/**
 * 查询方式类型
 * @param queryType
 */
consumer.cashIncomeStatements.QUERY_BY_DATE='TD';
consumer.cashIncomeStatements.QUERY_BY_BILL='TDZ';
consumer.cashIncomeStatements.QUERY_BY_COLLECT='DERE';
consumer.cashIncomeStatements.QUERY_BY_WAYBILL='WB';

consumer.cashIncomeStatements.CHOICE_BY_ACCOUNT='AC';
consumer.cashIncomeStatements.CHOICE_BY_CONFIRM='CO';

//请输入1个或10内的多个单号,单号之间用半角逗号隔开
consumer.cashIncomeStatements.PROMPT=consumer.cashIncomeStatements.i18n('foss.stl.consumer.cashIncome.emptyPrompt');

consumer.cashIncomeStatements.resetSelectValue=function(){
	var collectionOrg_Id = Ext.getCmp('foss.consumer.cashIncomeStatements_collectionOrgCode');
	var generatingOrg_Id=Ext.getCmp('foss.consumer.cashIncomeStatements_generatingOrgCode');
	Ext.apply(generatingOrg_Id,{allowBlank:true});
	Ext.apply(collectionOrg_Id,{allowBlank:true});
}

consumer.cashIncomeStatements.setParams=function(form,queryType){
	//定义查询参数
	var params={};
	//按日期查询
	if(consumer.cashIncomeStatements.QUERY_BY_DATE==queryType){
		var startDate = form.findField('vo.dto.startDate').getValue();
		var endDate = form.findField('vo.dto.endDate').getValue();
		
		
		/**
		 * 单独提取收入部门和收款名称和CODE
		 */
		var generatingOrg=form.findField('generatingOrgCode');
		//收入部门编码
		var generatingOrgCode=generatingOrg.getValue();
		//收入部门名称
		var generatingOrgName=generatingOrg.getRawValue();
		
		var collectionOrg = form.findField('collectionOrgCode');
		var collectionOrgCodeLast = collectionOrg.lastValue;
		//收款部门编码
		var collectionOrgCode = "";
		//收款部门名称
		var collectionOrgName = "";
		if(collectionOrgCodeLast == stl.currentDept.name){
			collectionOrgCode = stl.currentDept.code;
			collectionOrgName = stl.currentDept.name;
		}else{
			collectionOrgCode = collectionOrgCodeLast;
			collectionOrgName = collectionOrg.getRawValue();
		}
		
		var paymentType=form.findField('repayWay').getValue();

		if(Ext.isEmpty(generatingOrgCode)&&Ext.isEmpty(collectionOrgCode)){
			var collectionOrg_Id = Ext.getCmp('foss.consumer.cashIncomeStatements_collectionOrgCode');
			var generatingOrg_Id=Ext.getCmp('foss.consumer.cashIncomeStatements_generatingOrgCode');
			Ext.apply(generatingOrg_Id,{allowBlank:true});
			Ext.apply(collectionOrg_Id,{allowBlank:true});
			if(Ext.isEmpty(generatingOrgCode)){
				Ext.apply(generatingOrg_Id,{allowBlank:false});
				generatingOrg_Id.doComponentLayout();
				return ;
			}
			
			if(Ext.isEmpty(collectionOrgCode)){
				Ext.apply(collectionOrg_Id,{allowBlank:false});
				collectionOrg_Id.doComponentLayout();
				return ;
			}
		}
		
		
		
		if(!startDate){
			
			//开始日期不能为空
			Ext.Msg.alert(consumer.cashIncomeStatements.i18n('foss.stl.consumer.common.warmTips'), consumer.cashIncomeStatements.i18n('foss.stl.consumer.cashIncome.startDateCannotBeNull'));
			return null;
		}
		if(!endDate){
			
			//结束日期不能为空
			Ext.Msg.alert(consumer.cashIncomeStatements.i18n('foss.stl.consumer.common.warmTips'), consumer.cashIncomeStatements.i18n('foss.stl.consumer.cashIncome.endDateCannotBeNull'));
			return null;
		}
			
		var diffDays = stl.compareTwoDate(startDate, endDate);
		if (diffDays > stl.DATELIMITDAYS_MONTH) {
			
			//起始日期和结束日期间隔不能超过{0}天
			//stl.DATELIMITDAYS_MONTH
			Ext.Msg.alert(consumer.cashIncomeStatements.i18n('foss.stl.consumer.common.warmTips'), consumer.cashIncomeStatements.i18n('foss.stl.consumer.cashIncome.startAndEndDateDiffCannotExceedSomeDays',[stl.DATELIMITDAYS_MONTH]));
			return null;
		} else if (diffDays < 1) {
			
			//开始日期不能晚于结束日期
			Ext.Msg.alert(consumer.cashIncomeStatements.i18n('foss.stl.consumer.common.warmTips'), consumer.cashIncomeStatements.i18n('foss.stl.consumer.cashIncome.startDateCannotGtEndDate'));
			return null;
		}

		//获取FORM所有值
		params = form.getValues();

		//设置账单号参数到DTO中
		Ext.apply(params, {
							'vo.dto.generatingOrgCode' : generatingOrgCode,
							'vo.dto.generatingOrgName' : generatingOrgName,
							'vo.dto.collectionOrgCode' : collectionOrgCode,
							'vo.dto.collectionOrgName' : collectionOrgName,
							'vo.dto.paymentType':paymentType
					});
	//按账单号查询
	}else if(consumer.cashIncomeStatements.QUERY_BY_BILL==queryType){ 
		//获取账单号输入值
		var statementBillNos = form.findField('statementBillNos').getValue();
		//临时保存账单号输值
		//var statementBillNosArray_tmp = stl.splitToArray(statementBillNos);
		//保存传入ACTION的账单号
		var statementBillNosArray=new Array();
		
		statementBillNosArray=statementBillNos.match(/DZ\d{7,}/g);
		
		
		//去掉为空的值
		/*for(var i=0;i<statementBillNosArray_tmp.length;i++){
			if(statementBillNosArray_tmp[i].trim()!=''){
				if(statementBillNosArray_tmp[i].length>11)
				{
					Ext.Msg.alert("第"+(i+1)+"个对账单"+"长度超过11位，不合法！");
					return null;
				}
				statementBillNosArray.push(statementBillNosArray_tmp[i]);
			} 
		}*/
		if(statementBillNosArray.length==0){
			Ext.Msg.alert(consumer.cashIncomeStatements.i18n('foss.stl.consumer.common.warmTips'),consumer.cashIncomeStatements.PROMPT);
		 	return null;
		}
		if(statementBillNosArray.length>10){
			
			//最多输入10个对账单号
			Ext.Msg.alert(consumer.cashIncomeStatements.i18n('foss.stl.consumer.common.warmTips'),consumer.cashIncomeStatements.i18n('foss.stl.consumer.cashIncome.10statementBillNosMost'));
		 	return null;
		}
		//设置账单号参数到DTO中
		Ext.apply(params,{ 
							'vo.dto.statementBillNos' : statementBillNosArray
							});
	//按预收单查询
	}else if(consumer.cashIncomeStatements.QUERY_BY_COLLECT==queryType){
		var depositReceivedNos = form.findField('depositReceivedNos').getValue();
		var depositReceivedNosArray_tmp = stl.splitToArray(depositReceivedNos);
		var depositReceivedNosArray=new Array();
		
		for(var i=0;i<depositReceivedNosArray_tmp.length;i++){
			if(depositReceivedNosArray_tmp[i].trim()!=''){
				depositReceivedNosArray.push(depositReceivedNosArray_tmp[i]);
			} 
		}
		 
		if(depositReceivedNosArray.length==0){
			Ext.Msg.alert(consumer.cashIncomeStatements.i18n('foss.stl.consumer.common.warmTips'),consumer.cashIncomeStatements.PROMPT);
		 	return null;
		}
		if(depositReceivedNosArray.length>10){
			
			//最多输入10个预收单号
			Ext.Msg.alert(consumer.cashIncomeStatements.i18n('foss.stl.consumer.common.warmTips'),consumer.cashIncomeStatements.i18n('foss.stl.consumer.cashIncome.10depositReceivedNosMost'));
		 	return null;
		}
		
		Ext.apply(params,{ 
							 'vo.dto.depositReceivedNos' : depositReceivedNosArray
							});
	}else if(consumer.cashIncomeStatements.QUERY_BY_WAYBILL==queryType){//按运单号查询
		var waybillNos= form.findField('waybillNos').getValue();
		var waybillNosArray_tmp = stl.splitToArray(waybillNos);
		var waybillNosArray=new Array();
		
		for(var i=0;i<waybillNosArray_tmp.length;i++){
			if(waybillNosArray_tmp[i].trim()!=''){
				waybillNosArray.push(waybillNosArray_tmp[i]);
			} 
		}
	 
		if(waybillNosArray.length==0){
			Ext.Msg.alert(consumer.cashIncomeStatements.i18n('foss.stl.consumer.common.warmTips'),consumer.cashIncomeStatements.PROMPT);
		 	return null;
		}
		if(waybillNosArray.length>10){
			
			//最多输入10个运单号
			Ext.Msg.alert(consumer.cashIncomeStatements.i18n('foss.stl.consumer.common.warmTips'),consumer.cashIncomeStatements.i18n('foss.stl.consumer.cashIncome.10waybillNosMost'));
		 	return null;
		}
		Ext.apply(params,{ 
							'vo.dto.waybillNos' : waybillNosArray
							});
	}
	//设置查询类型
	Ext.apply(params,{
					'vo.queryType':queryType
			});
	return params;
}


/**
 * 获取Grid组件信息
 * @returns
 */
consumer.cashIncomeStatements.getPageObj=function(){
	var me=Ext.getCmp('T_consumer-queryCashIncomeStatements_content');
	if(me){
		var grid = me.getQueryGrid();
		var store=grid.getStore();
		if(store.data.length==0){
			
			//查询结果集为空不能进行该操作
			Ext.Msg.alert(consumer.cashIncomeStatements.i18n('foss.stl.consumer.common.warmTips'), consumer.cashIncomeStatements.i18n('foss.stl.consumer.cashIncome.queryResultNull.cannotOperate'));
			return null;
		}
		return grid;	
	}
	return null;
};
/**
 * Form查询方法
 */
consumer.cashIncomeStatements.query=function(f,me,queryType){
	var form=f.getForm();
	
	var grid = Ext.getCmp('T_consumer-queryCashIncomeStatements_content').getQueryGrid();

	if(form.isValid()){
		var params=consumer.cashIncomeStatements.setParams(form,queryType);
		if(null==params){
			return;
		}
		//清除上一次查询的结果
		grid.store.removeAll();
		//设置查询参数
		grid.store.setSubmitParams(params);
		
		//设置该按钮灰掉
		me.disable(false);
		//30秒后自动解除灰掉效果
		setTimeout(function() {
			me.enable(true);
		}, 30000);
		//设置统计值
		grid.store.loadPage(1,{
	      callback: function(records, operation, success) {
	      	if(success == false){
	      		Ext.Msg.alert(consumer.cashIncomeStatements.i18n('foss.stl.consumer.common.warmTips'),grid.store.proxy.reader.jsonData.message);
	      		me.enable(true);
	      		return;
	      	}
			var result =   Ext.decode(operation.response.responseText),
			
			toolBar = grid.getDockedItems('toolbar[dock="bottom"]')[0];
			toolBar.getComponent('totalCounts').setValue(result.totalCount);
			toolBar.getComponent('totalAmount').setValue(result.vo.totalAmount);
			toolBar.getComponent('chTotalAmount').setValue(result.vo.chTotalAmount);
			toolBar.getComponent('ttTotalAmount').setValue(result.vo.ttTotalAmount);
			toolBar.getComponent('ntTotalAmount').setValue(result.vo.ntTotalAmount);
			toolBar.getComponent('cdTotalAmount').setValue(result.vo.cdTotalAmount);
			toolBar.getComponent('olTotalAmount').setValue(result.vo.olTotalAmount);
			me.enable(true);
	       }
	    });
	}else {
		
		//请检查输入条件是否合法
		Ext.Msg.alert(consumer.cashIncomeStatements.i18n('foss.stl.consumer.common.warmTips'), consumer.cashIncomeStatements.i18n('foss.stl.consumer.cashIncome.pleaseCheckInputConditionisLegal'));
	}
}

/**
 * 导出方法
 */
consumer.cashIncomeStatements.cashIncomeStatementsExport = function(){
	var grid = consumer.cashIncomeStatements.getPageObj();
	if(grid==null){
		return;
	}
	Ext.MessageBox.buttonText.yes = consumer.cashIncomeStatements.i18n('foss.stl.consumer.common.OK');  
	Ext.MessageBox.buttonText.no = consumer.cashIncomeStatements.i18n('foss.stl.consumer.common.cancel'); 
	
	//确定要导出查询结果吗?
	Ext.Msg.confirm( consumer.cashIncomeStatements.i18n('foss.stl.consumer.common.warmTips'), consumer.cashIncomeStatements.i18n('foss.stl.consumer.cashIncome.sureExportQueryResult'), function(btn,text){
		if(btn == 'yes'){
			var params=grid.store.submitParams;
			if(!Ext.fly('downloadCashIncomeForm')){
				var frm = document.createElement('form');
				frm.id = 'downloadCashIncomeForm';
				frm.style.display = 'none';
				document.body.appendChild(frm);
			}
	
			Ext.Ajax.request({
				url:consumer.realPath('cashIncomeStatementsExport.action'),
				form: Ext.fly('downloadCashIncomeForm'),
				params:params,
				method:'post',
				isUpload: true,
				success:function(response){
					var result = Ext.decode(response.responseText);
				},
				failure:function(response){
					var result = Ext.decode(response.responseText);
				}
			});
		}
	});
}

/**
 * 设置打印公共参数
 * @returns
 */
consumer.cashIncomeStatements.getPrintCashIncomeParams=function(grid){  
	//获取Form信息
	var params =grid.store.submitParams;
	
	var depositReceivedNos=params["vo.dto.depositReceivedNos"];
	if(depositReceivedNos){
		depositReceivedNos=depositReceivedNos.toString();
	}
	
	var statementBillNos=params["vo.dto.statementBillNos"];
	if(statementBillNos){
		statementBillNos=statementBillNos.toString();
	}
	
	var waybillNos=params["vo.dto.waybillNos"]; 
	if(waybillNos){
		waybillNos=waybillNos.toString();
	}
	
	var formParams={
		'dateType' : params["vo.dto.dateType"],
		'startDate' : params["vo.dto.startDate"],
		'endDate' : params["vo.dto.endDate"],
		'orgCode' : params["vo.dto.orgCode"],
		'billType' : params["vo.dto.billType"],
		'largeAreaCode' : params["vo.dto.largeAreaCode"],
		'smallAreaCode' : params["vo.dto.smallAreaCode"],
		'status' : params["vo.dto.status"],
		'generatingOrgCode' : params["vo.dto.generatingOrgCode"],
		'generatingOrgName' : params["vo.dto.generatingOrgName"],
		'collectionOrgCode' : params["vo.dto.collectionOrgCode"],
		'collectionOrgName' : params["vo.dto.collectionOrgName"],
		'cashConfirmUserCode':params["vo.dto.cashConfirmUserCode"],
		'queryType' : params["vo.queryType"],
		'depositReceivedNumbers' :depositReceivedNos,
		'statementBillNumbers' : statementBillNos,
		'waybillNumbers' : waybillNos,
		'createUserName' : stl.emp.empName,
		'createUserCode' : stl.emp.empCode,
		'paymentType' : params["vo.dto.paymentType"]
	}; 
	
	//转化列头和列明
	var columns = grid.columns;
	var arrayColumns = [];
	var index=0;//列标
	//传入后台打印，与index拼接为后台列明
	var headerStr = "{";
	for(var i=1;i<columns.length;i++){
		if(columns[i].isHidden()==false){
			var hederName = columns[i].text;
			if(i!=columns.length-1){
				if(columns[i].text!=consumer.cashIncomeStatements.i18n('foss.stl.consumer.common.actionColumn')){
					index = index+1;
					headerStr = headerStr+'\''+'columnName'+index+'\':\''+hederName+'\',';
					arrayColumns.push(columns[i].dataIndex);
				}
			}else{
				index = index+1;
				arrayColumns.push(columns[i].dataIndex);
				headerStr = headerStr+'\''+'columnName'+index+'\':\''+hederName+'\'';
			}
		}
	}
	//控制打印列数
	if(arrayColumns.length>15){
		
		//最多不能超过15列
		Ext.Msg.alert(consumer.cashIncomeStatements.i18n('foss.stl.consumer.common.warmTips'),consumer.cashIncomeStatements.i18n('foss.stl.consumer.cashIncome.15columnsMost'));
		return null;
	}
	headerStr = headerStr+'}';
	var headerObject = Ext.decode(headerStr);
    targetObject = Ext.merge(headerObject,formParams);
	targetObject.arrayColumns = arrayColumns; 
	
return targetObject;
}

/**
 * 打印汇总
 */
consumer.cashIncomeStatements.printCashIncomeTotal=function(){
	var grid=consumer.cashIncomeStatements.getPageObj();
	if(grid==null){
		return;
	}
	Ext.MessageBox.buttonText.yes = consumer.cashIncomeStatements.i18n('foss.stl.consumer.common.OK');  
	Ext.MessageBox.buttonText.no = consumer.cashIncomeStatements.i18n('foss.stl.consumer.common.cancel');
	
	//确定要打印吗?
	Ext.Msg.confirm(consumer.cashIncomeStatements.i18n('foss.stl.consumer.common.warmTips'), consumer.cashIncomeStatements.i18n('foss.stl.consumer.cashIncome.surePrint'), function(btn,text){
		if(btn == 'yes'){
			//获取打印参数信息
			targetObject = consumer.cashIncomeStatements.getPrintCashIncomeParams(grid);
			do_printpreview('poolcashincomestatements',targetObject,ContextPath.STL_WEB);
		}
	});
}

/**
 * 打印明细
 */
consumer.cashIncomeStatements.printCashIncomeDetail=function(){
	var grid=consumer.cashIncomeStatements.getPageObj();
	if(grid==null){
		return;
	}
	Ext.MessageBox.buttonText.yes = consumer.cashIncomeStatements.i18n('foss.stl.consumer.common.OK');  
	Ext.MessageBox.buttonText.no = consumer.cashIncomeStatements.i18n('foss.stl.consumer.common.cancel');
	
	//确定要打印吗?
	Ext.Msg.confirm(consumer.cashIncomeStatements.i18n('foss.stl.consumer.common.warmTips'), consumer.cashIncomeStatements.i18n('foss.stl.consumer.cashIncome.surePrint'), function(btn,text){
		if(btn == 'yes'){
			//获取打印参数信息
			targetObject = consumer.cashIncomeStatements.getPrintCashIncomeParams(grid);
			do_printpreview('cashincomestatements',targetObject,ContextPath.STL_WEB);
		}
	});
}

/**
 * Form重置方法
 */
consumer.cashIncomeStatements.reset=function(){
	this.up('form').getForm().reset();
}
/**
 *现金收入 model 
 */
Ext.define('Foss.cashIncomeStatements.CashIncomeStatementsModel',{
	extend:'Ext.data.Model',
	fields :[  
			{	
				name :'id',  //申请部门ID
				type :'string'
			},{	
				name :'accountDate',  //记账日期
				type :'date',
				convert:function(value) {
					if (value != null) {
						var date = new Date(value);
						return date;
					} else {
						return null;
					}
				}
			},{	
				name :'billNo',  //单据编号
				type :'string'
			},{
				name :'productCode', // 运输性质
				type : 'string'
			}, {
				name :'collectionOrgCode',   //收款部门编码
				type :'string'
			}, {
				name :'collectionOrgName',   //收款部门名称
				type :'string'
				
			}, {
				name :'generatingOrgCode', //收入部门编码
				type :'string'
			}, {
				name :'generatingOrgName', //收入部门名称
				type :'string'
			}, {
				name :'waybillNo', //运单号
				type :'string'
			}, { 
				name :'billType', //单据类别
				type :'string' 
			}, {
				name :'status', //单据状态
				type :'string'
			}, {
				name :'paymentType', //付款方式
				type :'string'
			}, {
				name :'amount', //金额
				type :'long'
			}, { 
				name :'cashConfirmUserCode', //收银确认人工号（收银员）
				type :'string' 
			}, {
				name :'cashConfirmUserName', //收银确认人名称
				type :'string'
			}, {
				name :'cashConfirmTime', //收银确认时间
				type :'date',
				convert:function(value) {
					if (value != null) {
						var date = new Date(value);
						return date;
					} else {
						return null;
					}
				}
			}
		]
});

//单据类别
Ext.define('Foss.cashIncomeStatements.BillTypeStore',{
	extend:'Ext.data.Store',
	fields:[
		{name:'code',  type:'string'},
		{name:'name',  type:'string'}
	],
	data:{
		'items':[
			    {code:'',name:consumer.cashIncomeStatements.i18n('foss.stl.consumer.cashIncome.all')},//全部
				{code:'XS',name:consumer.cashIncomeStatements.i18n('foss.stl.consumer.cashIncome.billType.XS')},//现金收款单
				{code:'US',name:consumer.cashIncomeStatements.i18n('foss.stl.consumer.cashIncome.billType.US')},//预收单
				{code:'HK',name:consumer.cashIncomeStatements.i18n('foss.stl.consumer.cashIncome.billType.HK')}//还款单
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
//单据状态
Ext.define('Foss.cashIncomeStatements.StatusStore',{
	extend:'Ext.data.Store',
	fields:[
		{name:'code',  type:'string'},
		{name:'name',  type:'string'}
	],
	data:{
		'items':[
			    {code:'',name:consumer.cashIncomeStatements.i18n('foss.stl.consumer.cashIncome.all')},//全部
				{code:'S',name:consumer.cashIncomeStatements.i18n('foss.stl.consumer.cashIncome.status.S')},//提交
				{code:'C',name:consumer.cashIncomeStatements.i18n('foss.stl.consumer.cashIncome.status.C')}//收银确认
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
//付款方式
Ext.define('Foss.cashIncomeStatements.PaymentTypeStore',{
	extend:'Ext.data.Store',
	fields:[
		{name:'code',  type:'string'},
		{name:'name',  type:'string'}
	],
	data:{
		'items':[
		    {code:'',name:consumer.cashIncomeStatements.i18n('foss.stl.consumer.cashIncome.all')},//全部
			{code:'CH',name:consumer.cashIncomeStatements.i18n('foss.stl.consumer.cashIncome.paymentType.CH')},//现金
			{code:'CD',name:consumer.cashIncomeStatements.i18n('foss.stl.consumer.cashIncome.paymentType.CD')},//银行卡
			{code:'NT',name:consumer.cashIncomeStatements.i18n('foss.stl.consumer.cashIncome.paymentType.NT')},//支票
			{code:'TT',name:consumer.cashIncomeStatements.i18n('foss.stl.consumer.cashIncome.paymentType.TT')},//电汇
			{code:'OL',name:consumer.cashIncomeStatements.i18n('foss.stl.consumer.cashIncome.paymentType.OL')}//网上支付
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

/**
 * 现金收入  store
 */
Ext.define('Foss.cashIncomeStatements.CashIncomeStatementsStore',{
	extend:'Ext.data.Store',
	model:'Foss.cashIncomeStatements.CashIncomeStatementsModel',  
	pageSize:100,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url : consumer.realPath('queryCashIncomeStatementsByCondition.action'),
		reader : {
			type : 'json',
			root : 'vo.list',
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
 * FORM 定义
 */

//按日期查询 Form
Ext.define('Foss.cashIncomeStatements.QueryDateForm',{
	extend:'Ext.form.Panel',
	frame:false,
	columnWidth:1,
	height:230,
	defaults:{
		margin :'5 5 5 0',
		labelWidth :85,
		colspan : 1 
	},
	defaultType:'textfield',
	layout:{
		type :'table',
		columns :3
	},
	items:[
	    {
			xtype:'radiogroup',
			fieldLabel:consumer.cashIncomeStatements.i18n('foss.stl.consumer.cashIncome.dateType'),//日期类型
			name:'vo.dto.dateType',
			margin :'10 5 5 5',
			colspan:3,
			allowBlank:false,
			defaultType:'radio',
			layout:'table',
			isFormField: true, 
			items:[{
				boxLabel:consumer.cashIncomeStatements.i18n('foss.stl.consumer.cashIncome.acctDate'),//记账日期
				margin :'5 5 5 5',
				name:'vo.dto.dateType',
				checked:true,
				inputValue:'AC'
			},{
				boxLabel:consumer.cashIncomeStatements.i18n('foss.stl.consumer.cashIncome.cashDate'),//收银日期
				margin :'5 5 5 5',
				name:'vo.dto.dateType',
				inputValue:'CO'
			}]
	    },
	    { 
			xtype:'datefield',
			name:'vo.dto.startDate', 
			fieldLabel:consumer.cashIncomeStatements.i18n('foss.stl.consumer.cashIncome.startDate'),//开始日期
			format:'Y-m-d',
	    	editable:false,
			allowBlank:false,
			value:stl.dateFormat(new Date(),stl.FORMAT_DATE)
	    },
	    {
			xtype:'datefield',
			name:'vo.dto.endDate',
			fieldLabel:consumer.cashIncomeStatements.i18n('foss.stl.consumer.cashIncome.endDate'),//结束日期
			format:'Y-m-d',
	    	editable:false,
			allowBlank:false,
			value:stl.dateFormat(new Date(),stl.FORMAT_DATE)
	    },
	    {
			xtype:'combo',
			name:'vo.dto.billType',
			fieldLabel:consumer.cashIncomeStatements.i18n('foss.stl.consumer.cashIncome.billType'),//单据类别
			value:'',
			displayField:'name',
			valueField:'code',
			queryMode:'local',
			triggerAction:'all',
			allowBlank:true,
			editable:false,
			store:Ext.create('Foss.cashIncomeStatements.BillTypeStore')
		},
		 
//		{
//			xtype : 'commonbigzoneselector',
//			fieldLabel : consumer.cashIncomeStatements.i18n('foss.stl.consumer.cashIncome.largeArea'),//大&nbsp;&nbsp;区
//			name : 'vo.dto.largeAreaCode',
//			allowBlank: true
//		},
		{
			xtype : 'linkagecomboselector',
			eventType : ['focus'],// 一般callparent包含focus事件，如果有callparent,则focus事件可不用传递
			itemId : 'Foss_baseinfo_BigRegion_ID',
			store : Ext.create('Foss.baseinfo.commonSelector.BigRegionStore'),// 从外面传入
			fieldLabel : consumer.cashIncomeStatements.i18n('foss.stl.consumer.cashIncome.largeArea'),//大&nbsp;&nbsp;区
			value:'',
			minChars : 0,
			displayField : 'name',// 显示名称
			valueField : 'code',
			minChars : 0,
			queryParam : 'commonOrgVo.name',
			//queryParam : 'commonOrgVo.name'
			name : 'vo.dto.largeAreaCode',
			allowBlank : true,
			listWidth : 300,// 设置下拉框宽度
			isPaging : true
			// 分页
		},
//		{
//	    	xtype: 'commonsmallzoneselector',
//	        name: 'vo.dto.smallAreaCode',
//	        fieldLabel: consumer.cashIncomeStatements.i18n('foss.stl.consumer.cashIncome.smallArea'),//小&nbsp;&nbsp;区
//	        allowBlank: true
//	    },
		{
			xtype : 'linkagecomboselector',
			itemId : 'Foss_baseinfo_SmallRegion_ID',
			eventType : ['callparent'],// 一般callparent包含focus事件，如果有callparent,则focus事件可不用传递
			store : Ext.create('Foss.baseinfo.commonSelector.SmallRegionStore'),// 从外面传入
			name : 'vo.dto.smallAreaCode',
			fieldLabel : consumer.cashIncomeStatements.i18n('foss.stl.consumer.cashIncome.smallArea'),//小&nbsp;&nbsp;区
			parentParamsAndItemIds : {
				'commonOrgVo.code' : 'Foss_baseinfo_BigRegion_ID'
			},// 此处城市不需要传入
			minChars : 0,
			displayField : 'name',// 显示名称
			valueField : 'code',
			minChars : 0,
			//queryParam : 'commonOrgVo.name'
			allowBlank : true,
			listWidth : 300,// 设置下拉框宽度
			isPaging : true,
			queryParam : 'commonOrgVo.name'
			// 分页
		},
	    {
			xtype:'combo',
			name:'vo.dto.status',
			fieldLabel:consumer.cashIncomeStatements.i18n('foss.stl.consumer.cashIncome.status'),//单据状态
			value:'',
			displayField:'name',
			valueField:'code',
			queryMode:'local',
			triggerAction:'all',
			allowBlank:true,
			editable:false,
			store:Ext.create('Foss.cashIncomeStatements.StatusStore')
		},
	    {
			id:'foss.consumer.cashIncomeStatements_collectionOrgCode',
			xtype:'dynamicorgcombselector',
			name:'collectionOrgCode',
			blankText:'收款和收入部门不能同时为空！',
			value:stl.currentDept.name,
			fieldLabel:consumer.cashIncomeStatements.i18n('foss.stl.consumer.cashIncome.collectionOrgName'),//收款部门
			listeners:{
		         scope: this,
		         'select': consumer.cashIncomeStatements.resetSelectValue
		    }
		},
	    {
			id:'foss.consumer.cashIncomeStatements_generatingOrgCode',
			xtype:'dynamicorgcombselector',
			name:'generatingOrgCode',
			fieldLabel:consumer.cashIncomeStatements.i18n('foss.stl.consumer.cashIncome.generatingOrgName'),//收入部门
			blankText:'收款和收入部门不能同时为空！',
			listeners:{
		         scope: this,
		         'select': consumer.cashIncomeStatements.resetSelectValue
		    }
		},
		/* BUG-30317  去掉收银员查询条件
		 * 
		 * Yang Shushuo
		 * 2013-06-24
		 */
//	    {
//	    	xtype: 'commonemployeeselector',
//	        name: 'vo.dto.cashConfirmUserCode',
//	        fieldLabel: consumer.cashIncomeStatements.i18n('foss.stl.consumer.cashIncome.cashConfirmUser'),//收银员
//	        allowBlank: true
//	    },
		{
	    	xtype: 'combobox',
			name:'repayWay',
	        fieldLabel:consumer.cashIncomeStatements.i18n('foss.stl.consumer.cashIncome.paymentType'),
	        columnWidth:.33,
	        editable:false,
	    	store:FossDataDictionary.getDataDictionaryStore(settlementDict.SETTLEMENT__PAYMENT_TYPE,null,{
				 'valueCode': '',
         		 'valueName': consumer.cashIncomeStatements.i18n('foss.stl.consumer.common.all')
			},[
				consumer.cashIncomeStatements.SETTLEMENT__PAYMENT_TYPE__CASH , // 现金
				consumer.cashIncomeStatements.SETTLEMENT__PAYMENT_TYPE__CARD,// 银行卡
				consumer.cashIncomeStatements.SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER,// 电汇
				consumer.cashIncomeStatements.SETTLEMENT__PAYMENT_TYPE__NOTE,// 支票
				consumer.cashIncomeStatements.SETTLEMENT__PAYMENT_TYPE__ONLINE
			]),
			queryModel:'local',
			displayField:'valueName',
			valueField:'valueCode',
			value:''
	    },
	    {
			border: 1,
			xtype:'container',
			columnWidth:1,
			colspan:3,
			defaultType:'button',
			layout:'column',
			items:[{
				  text:consumer.cashIncomeStatements.i18n('foss.stl.consumer.common.reset'),   
				  columnWidth:.1,
				  handler:consumer.cashIncomeStatements.reset
			  	},{
					xtype:'container',
					border:false,
					html:'&nbsp;<span style="color:red;">温馨提示：收款部门和收入部门，必须选择填一项！</span>',
					columnWidth:.8,
					id:''
				},
			  	{
				  text:consumer.cashIncomeStatements.i18n('foss.stl.consumer.common.query'),
				  columnWidth:.1,
				  cls:'yellow_button',  
				  handler:function(){
					  var form=this.up('form');
					  var me = this;
					  consumer.cashIncomeStatements.query(form,me,consumer.cashIncomeStatements.QUERY_BY_DATE)
				  }
			  	}]
			}
	  ]
});

//按对账单查询 Form
Ext.define('Foss.cashIncomeStatements.QueryBillForm',{
	extend:'Ext.form.Panel',
	frame:false,
	defaults:{
		margin :'15 5 5 0',
		labelWidth :85,
		colspan : 1 
	},
	defaultType:'textfield',
	layout:{
		type :'table',
		columns :3
	},
	items:[
	       {
			xtype: 'textarea',
			autoScroll:true,
			emptyText: consumer.cashIncomeStatements.PROMPT,
			fieldLabel: consumer.cashIncomeStatements.i18n('foss.stl.consumer.cashIncome.statementBillNos'),//对账单号
			name: 'statementBillNos',
			regex     :/^DZ\d{9}(.DZ\d{9}){0,9}$/,
			regexText:'请输入正确的对账单号！',
			height : 80,
			width : 600,
			allowBlank:false,
			colspan:3
 		   },{
 				border: 1,
 				xtype:'container',
 				columnWidth:1,
 				colspan:3,
 				defaultType:'button',
 				layout:'column',
 				items:[{
 					  text:consumer.cashIncomeStatements.i18n('foss.stl.consumer.common.reset'),   
 					  columnWidth:.1,
 					 handler:consumer.cashIncomeStatements.reset
 				  	},{
 						xtype:'container',
 						border:false,
 						html:'&nbsp;',
 						columnWidth:.8
 					},
 				  	{
 					  text:consumer.cashIncomeStatements.i18n('foss.stl.consumer.common.query'),
 					  columnWidth:.1,
 					  cls:'yellow_button',  
 					  handler:function(){
 						  var form=this.up('form');
 						  var me = this;
 						 consumer.cashIncomeStatements.query(form,me,consumer.cashIncomeStatements.QUERY_BY_BILL); 
 					  }
 				  	}]
 				}
	      ]  
});

//按预收单查询 Form 
Ext.define('Foss.cashIncomeStatements.QueryCollectForm',{
	extend:'Ext.form.Panel',
	frame:false,
	defaults:{
		margin :'15 5 5 0',
		labelWidth :85,
		colspan : 1 
	} ,
	defaultType:'textfield',
	layout:{
		type :'table',
		columns :3
	},
	items:[
	       {
			xtype: 'textarea',
			autoScroll:true,
			emptyText: consumer.cashIncomeStatements.PROMPT, 
			fieldLabel: consumer.cashIncomeStatements.i18n('foss.stl.consumer.cashIncome.depositReceivedNos'),//预收单号
			name: 'depositReceivedNos',
			height : 80,
			width : 600,
			allowBlank:false,
			colspan:3
		   },
		   {
			border: 1,
			xtype:'container',
			columnWidth:1,
			colspan:3,
			defaultType:'button',
			layout:'column',
			items:[{
				  text:consumer.cashIncomeStatements.i18n('foss.stl.consumer.common.reset'),   
				  columnWidth:.1,
				 handler:consumer.cashIncomeStatements.reset
			  	},{
					xtype:'container',
					border:false,
					html:'&nbsp;',
					columnWidth:.8
				},
			  	{
				  text:consumer.cashIncomeStatements.i18n('foss.stl.consumer.common.query'),
				  columnWidth:.1,
				  cls:'yellow_button',  
				  handler:function(){
					 var form=this.up('form');
					 var me = this;
					 consumer.cashIncomeStatements.query(form,me,consumer.cashIncomeStatements.QUERY_BY_COLLECT);
				  }
			  	}]
 			}
	      ] 
});
//按运单号查询 Form 
Ext.define('Foss.cashIncomeStatements.QueryWaybillForm',{
	extend:'Ext.form.Panel',
	frame:false,
	defaults:{
		margin :'15 5 5 0',
		labelWidth :85,
		colspan : 1 
	},
	defaultType:'textfield',
	layout:{
		type :'table',
		columns :3
	},
	items:[
	       {
			xtype: 'textarea',
			autoScroll:true,
			emptyText: consumer.cashIncomeStatements.PROMPT,
			fieldLabel: consumer.cashIncomeStatements.i18n('foss.stl.consumer.cashIncome.waybillNos'),//运单单号
			name: 'waybillNos',
			height : 80,
			width : 600,
			allowBlank:false,
			colspan:3,
			columnWidth:.4,
			listeners:{
				 listeners :{
					'keyup':{fn:function(){
                        
                      }},
                    'blur':{fn:function(){
                          
                      }}
		       }
			}
	       },
		   {
			border: 1,
			xtype:'container',
			columnWidth:1,
			colspan:3,
			defaultType:'button',
			layout:'column',
			items:[{
				  text:consumer.cashIncomeStatements.i18n('foss.stl.consumer.common.reset'),   
				  columnWidth:.1,
				 handler:consumer.cashIncomeStatements.reset
			  	},{
					xtype:'container',
					border:false,
					html:'&nbsp;',
					columnWidth:.8
				},
			  	{
				  text:consumer.cashIncomeStatements.i18n('foss.stl.consumer.common.query'),
				  columnWidth:.1,
				  cls:'yellow_button',  
				  handler:function(){
					 var form=this.up('form');
					 var me = this;
					 consumer.cashIncomeStatements.query(form,me,consumer.cashIncomeStatements.QUERY_BY_WAYBILL);
				  }
			  	}]
	 		}
	  ]
});



//现金收入明细 Grid
Ext.define('Foss.cashIncomeStatements.CashIncomeGrid',{
	extend: 'Ext.grid.Panel',
	title: consumer.cashIncomeStatements.i18n('foss.stl.consumer.cashIncome.caseIncomeDetail'),//现金收入明细
	columnWidth: 1,
	stripeRows: true,
	columnLines: true,
	collapsible: false,
	sortableColumns: false,
	viewConfig: {         
		enableTextSelection: true       
	}, 
	bodyCls: 'autoHeight',
	frame: true,
	cls: 'autoHeight', 
	store : null,
	autoScroll : true,
	height: 450,
	emptyText: consumer.cashIncomeStatements.i18n('foss.stl.consumer.common.emptyText'),
	pagingToolbar:null,
	getPagingToolbar:function(){
		var me = this;
		if(Ext.isEmpty(me.pagingToolbar)){
			me.pagingToolbar = Ext.create('Deppon.StandardPaging',{
				store:me.store,
				pageSize:100,
				maximumSize:1000,
				plugins:'pagesizeplugin'
			});
		}
     return me.pagingToolbar;
	},
	hidMeParams:null,
	setHidMeParams:function(params){
		this.hidMeParams=params;
	},
	getHidMeParams:function(){
		return this.hidMeParams;
	},
	totalCounts:null,//总行数
	getTotalCounts:function(){
		var me=this;
		if(Ext.isEmpty(me.totalCounts)){
			me.totalCounts=0;
		}
		return me.totalCounts;
	},
	totalAmount:null,//总金额
	getTotalAmount:function(){
		var me=this;
		if(Ext.isEmpty(me.totalAmount)){
			me.totalAmount=0;
		}
		return me.totalAmount;
	},
	chTotalAmount:null,//现金总金额
	getChTotalAmount:function(){
		var me=this;
		if(Ext.isEmpty(me.chTotalAmount)){
			me.chTotalAmount=0;
		}
		return me.chTotalAmount;
	},
	ttTotalAmount:null,//电汇总金额
	getTtTotalAmount:function(){
		var me=this;
		if(Ext.isEmpty(me.ttTotalAmount)){
			me.ttTotalAmount=0;
		}
		return me.ttTotalAmount;
	},
	ntTotalAmount:null,//支票总金额
	getNtTotalAmount:function(){
		var me=this;
		if(Ext.isEmpty(me.ntTotalAmount)){
			me.ntTotalAmount=0;
		}
		return me.ntTotalAmount;
	},
	cdTotalAmount:null,//支票总金额
	getCdTotalAmount:function(){
		var me=this;
		if(Ext.isEmpty(me.cdTotalAmount)){
			me.cdTotalAmount=0;
		}
		return me.cdTotalAmount;
	},
	olTotalAmount:null,//支票总金额
	getOlTotalAmount:function(){
		var me=this;
		if(Ext.isEmpty(me.olTotalAmount)){
			me.olTotalAmount=0;
		}
		return me.olTotalAmount;
	},
	columns : [ 
			{ text:'id',  dataIndex :'id' ,hidden:true},
			{ text:consumer.cashIncomeStatements.i18n('foss.stl.consumer.cashIncome.acctDate'),  dataIndex :'accountDate' ,width:100,
				renderer:function(value){
					if(value!=null){
						return Ext.Date.format(new Date(value), 'Y-m-d');
					}else{
						return null;
					}
				}
			},
			{ text:consumer.cashIncomeStatements.i18n('foss.stl.consumer.cashIncome.billNo'),  dataIndex :'billNo' ,width:120},//单据编号
			{ text:consumer.cashIncomeStatements.i18n('foss.stl.consumer.queryCashCollectionBill.productCode'),  dataIndex :'productCode' ,
				renderer:function(value){
					return Foss.pkp.ProductData.rendererSubmitToDisplay(value);
				}
			},//运输性质
			{ text:consumer.cashIncomeStatements.i18n('foss.stl.consumer.cashIncome.collectionOrgCode'),  dataIndex :'collectionOrgCode',hidden:true},//收款部门编码
			{ text:consumer.cashIncomeStatements.i18n('foss.stl.consumer.cashIncome.collectionOrgName'),  dataIndex :'collectionOrgName',width:100 },//收款部门
			{ text:consumer.cashIncomeStatements.i18n('foss.stl.consumer.cashIncome.generatingOrgCode'),  dataIndex:'generatingOrgCode' ,hidden:true},//收入部门编码
			{ text:consumer.cashIncomeStatements.i18n('foss.stl.consumer.cashIncome.generatingOrgName'),  dataIndex:'generatingOrgName',width:100},//收入部门
			{ text:consumer.cashIncomeStatements.i18n('foss.stl.consumer.cashIncome.waybillNos'),  dataIndex:'waybillNo' ,width:120},//运单单号
			{ text:consumer.cashIncomeStatements.i18n('foss.stl.consumer.cashIncome.billType'),  dataIndex:'billType' ,width:100,//单据类别
				renderer: function(value) {
					if (value == 'XS') {					
						return consumer.cashIncomeStatements.i18n('foss.stl.consumer.cashIncome.billType.XS');//现金收款单
					} else if (value == 'US') {					
						return consumer.cashIncomeStatements.i18n('foss.stl.consumer.cashIncome.billType.US');//预收单
					} else if (value == 'HK') {					
						return consumer.cashIncomeStatements.i18n('foss.stl.consumer.cashIncome.billType.HK');//还款单
					}    
				}},
			{ text:consumer.cashIncomeStatements.i18n('foss.stl.consumer.cashIncome.status'),   dataIndex:'status' ,width:100,//单据状态
				renderer: function(value) {
					if (value == 'S') {					
						return consumer.cashIncomeStatements.i18n('foss.stl.consumer.cashIncome.status.S');//提交
					} else if (value == 'C') {					
						return consumer.cashIncomeStatements.i18n('foss.stl.consumer.cashIncome.status.C');//收银确认
					}  
			}},
			{ text:consumer.cashIncomeStatements.i18n('foss.stl.consumer.cashIncome.paymentType'),   dataIndex:'paymentType' ,width:100,//付款方式
				renderer: function(value) {
					return  FossDataDictionary. rendererSubmitToDisplay (value,'SETTLEMENT__PAYMENT_TYPE');
				}}, 
			{ text:consumer.cashIncomeStatements.i18n('foss.stl.consumer.cashIncome.amount'), align:'right', dataIndex :'amount' ,width:120},//金额
			{ text:consumer.cashIncomeStatements.i18n('foss.stl.consumer.cashIncome.cashConfirmUserCode'),  dataIndex :'cashConfirmUserCode' ,width:100,hidden:true},//收银员编码
			{ text:consumer.cashIncomeStatements.i18n('foss.stl.consumer.cashIncome.cashConfirmUser'),  dataIndex :'cashConfirmUserName' ,width:110},//收银员
			{ text:consumer.cashIncomeStatements.i18n('foss.stl.consumer.cashIncome.cashConfirmTime'),  dataIndex:'cashConfirmTime' ,width:120,//收银时间
				renderer:function(value){
				if(value!=null){
					return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
				}else{
					return null;
				}
			}}
			],   
	constructor:function(config){
		var me = this, 
		cfg = Ext.apply({}, config);
		me.dockedItems =[{
			xtype :'toolbar',
			dock :'top',
			layout :'column',
			defaults :{
				margin :'0 0 5 3'
			},
			items :[{
				xtype :'button',
				text :consumer.cashIncomeStatements.i18n('foss.stl.consumer.common.export'),
				columnWidth :.1,
				handler :consumer.cashIncomeStatements.cashIncomeStatementsExport,
				disabled:!consumer.cashIncomeStatements.isPermission('/stl-web/consumer/cashIncomeStatementsExport.action'),
				hidden:!consumer.cashIncomeStatements.isPermission('/stl-web/consumer/cashIncomeStatementsExport.action')
			},
			{
				xtype :'button',
				text :consumer.cashIncomeStatements.i18n('foss.stl.consumer.cashIncome.printGather'),//打印汇总
				columnWidth :.1,
				handler :consumer.cashIncomeStatements.printCashIncomeTotal
			},
			{
				xtype :'button',
				text :consumer.cashIncomeStatements.i18n('foss.stl.consumer.cashIncome.printDetail'),//打印明细
				columnWidth :.1,
				handler : consumer.cashIncomeStatements.printCashIncomeDetail
			}
		  ]
		},{
	   		xtype: 'toolbar',
		    dock: 'bottom',
		    layout:'column',		    	
		     defaults:{
				margin:'1 2 1 1'
			},
			/* BUG-30317  添加总条数统计项，修改统计项顺序为：合计总金额、总条数、现金、银行卡、电汇、支票、网上支付
			 * BUG-36770  合计总金额显示不清楚
			 * Yang Shushuo
			 * 2013-06-24
			 */
		    items: [{
				xtype:'displayfield',
				itemId: 'totalAmount',
				name:'totalAmount',
				allowBlank:true,
				labelWidth:85,
				columnWidth:.2,
				fieldLabel:consumer.cashIncomeStatements.i18n('foss.stl.consumer.cashIncome.sumAmount'),//合计&nbsp;总金额
				value:me.getTotalAmount()
			},{
				xtype:'container',
				border:false,
				html:'&nbsp;',
				columnWidth:.05
			},{
				xtype:'displayfield',
				itemId: 'totalCounts',
				name:'totalCounts',
				allowBlank:true,
				labelWidth:65,
				columnWidth:.2,
				fieldLabel:consumer.cashIncomeStatements.i18n('foss.stl.consumer.cashIncome.allNum'),//总条数
				value:me.getTotalCounts()
			},{
				xtype:'container',
				border:false,
				html:'&nbsp;',
				columnWidth:.05
			},{
				xtype:'displayfield',
				name:'chTotalAmount',
				itemId: 'chTotalAmount',
				allowBlank:true,
				labelWidth:65,
				columnWidth:.2,
				fieldLabel:consumer.cashIncomeStatements.i18n('foss.stl.consumer.cashIncome.chAmount'),//现金金额
				value:me.getChTotalAmount()
			},{
				xtype:'container',
				border:false,
				html:'&nbsp;',
				columnWidth:.05
			},{
				xtype:'displayfield',
				name:'cdTotalAmount',
				itemId: 'cdTotalAmount',
				labelWidth:85,
				allowBlank:true,
				columnWidth:.2,
				flex:1,
				fieldLabel:consumer.cashIncomeStatements.i18n('foss.stl.consumer.cashIncome.cdAmount'),//银行卡金额
				value:me.getCdTotalAmount()
			},{
				xtype:'container',
				border:false,
				html:'&nbsp;',
				columnWidth:.04
			},{
				xtype:'container',
				border:false,
				html:'&nbsp;',
				columnWidth:.01
			},{
				xtype:'displayfield',
				itemId: 'ttTotalAmount',
				name:'ttTotalAmount',
				allowBlank:true,
				labelWidth:85,
				columnWidth:.2,
				fieldLabel:consumer.cashIncomeStatements.i18n('foss.stl.consumer.cashIncome.ttAmount'),//电汇金额
				value:me.getTtTotalAmount()
			},{
				xtype:'container',
				border:false,
				html:'&nbsp;',
				columnWidth:.05
			},{
				xtype:'displayfield',
				name:'ntTotalAmount',
				itemId: 'ntTotalAmount',
				allowBlank:true,
				labelWidth:65,
				columnWidth:.2,
				fieldLabel:consumer.cashIncomeStatements.i18n('foss.stl.consumer.cashIncome.ntAmount'),//支票金额
				value:me.getNtTotalAmount()
			},{
				xtype:'container',
				border:false,
				html:'&nbsp;',
				columnWidth:.05
			},{
				xtype:'displayfield',
				name:'olTotalAmount',
				itemId: 'olTotalAmount',
				labelWidth:100,
				allowBlank:true,
				columnWidth:.2,
				flex:1,
				fieldLabel:consumer.cashIncomeStatements.i18n('foss.stl.consumer.cashIncome.olAmount'),//网上支付金额
				value:me.getOlTotalAmount()
			}]
		 }];
		
		me.store=Ext.create('Foss.cashIncomeStatements.CashIncomeStatementsStore');
		
		me.bbar = me.getPagingToolbar();
		me.getPagingToolbar().store = me.store; 
		me.callParent([ cfg ]);
	} 
});

/**
 * 现金收入查询tab
 */
Ext.define('Foss.cashIncomeStatements.QueryCashIncomeTab',{
	extend:'Ext.tab.Panel',
	frame : false,
	bodyCls : 'autoHeight',
	cls : 'innerTabPanel',
	activeTab : 0,
	columnWidth: 1,
	//columnHeight: 'autoHeight',
	height:230, 
	items : [
	 {
		title : consumer.cashIncomeStatements.i18n('foss.stl.consumer.cashIncome.queryByDate'),//按日期查询
		tabConfig : {
			width : 120
			},
		items : [
				 Ext.create('Foss.cashIncomeStatements.QueryDateForm')
		       ]
	},
	{
		title : consumer.cashIncomeStatements.i18n('foss.stl.consumer.cashIncome.queryByStatementBill'),//按对账单查询
		tabConfig : {
			width : 120
			},
		items : [
		         Ext.create('Foss.cashIncomeStatements.QueryBillForm')
			   	]
	},
	{
		title : consumer.cashIncomeStatements.i18n('foss.stl.consumer.cashIncome.queryByDepositeReceivedBill'),//按预收单查询
		tabConfig : {
			width : 120
			},
		items : [ 
		         Ext.create('Foss.cashIncomeStatements.QueryCollectForm')
		        ]
	},
	{
		title : consumer.cashIncomeStatements.i18n('foss.stl.consumer.cashIncome.queryByWaybillNo'),//按运单号查询
		tabConfig : {
			width : 120
			},
		items : [ 
		         Ext.create('Foss.cashIncomeStatements.QueryWaybillForm')
			    ]
	} 
   ] 
});
 
//初始化界面
Ext.onReady(function() {
	Ext.QuickTips.init();
	
	if (Ext.getCmp('T_consumer-queryCashIncomeStatements_content')) {
		return;
	} 
	var queryTab = Ext.create('Foss.cashIncomeStatements.QueryCashIncomeTab');//查询TAB
	var queryGrid = Ext.create('Foss.cashIncomeStatements.CashIncomeGrid');//查询结果GRID
	
	Ext.create('Ext.panel.Panel',{
		id: 'T_consumer-queryCashIncomeStatements_content',
		cls: "panelContentNToolbar",
		bodyCls: 'panelContentNToolbar-body',
		layout: 'auto',
		//获得查询FORM
		getQueryTab : function() {
			return queryTab;
		},
		//获得查询结果GRID
		getQueryGrid : function() {
			return queryGrid;
		},
		items: [queryTab,queryGrid],
		renderTo: 'T_consumer-queryCashIncomeStatements-body'
	});
});


