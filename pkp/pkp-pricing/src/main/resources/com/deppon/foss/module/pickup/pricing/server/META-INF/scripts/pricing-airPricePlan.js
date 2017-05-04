/*****
 * 空运价格方案,维护空运价格运费的基础信息,界面与汽运大致相同,区别在于
 * 空运的运费明细界面中不用设置产品以及多加了航班类型(早，中，晚班)
 * 空运没有时效概念
 * 
 */


/**
 * 
 * @type String 激活
 */
pricing.yes = 'Y';
pricing.no = 'N';
/**
 * 
 * @type  航空运价费用
 */
pricing.pricePlanId = null;
/**
 * 转换long类型为日期
 * @param {} value 转换前的value
 * @return {} 返回转换后的value
 */
pricing.changeLongToDate = function(value) {
	if (value != null) {
		var date = new Date(value);
		return date;
	} else {
		return null;
	}
};
/**.
 * <p>
 * 公共方法，通过storeId和model创建STORE<br/>
 * <p>
 * @param  storeId  
 * @param  model   store所用到的model名
 * @param  fields   store所用到的fields
 * @returns store  返回创建的store
 * @author Foss-YueHongJie
 */
pricing.getStore = function(storeId,model,fields,data) {
	var store = null;
	if(!Ext.isEmpty(storeId)){
		store = Ext.data.StoreManager.lookup(storeId);
	}
	if(Ext.isEmpty(data)){
		data = [];
	}
	if(!Ext.isEmpty(model)){
		if(Ext.isEmpty(store)){
			store = Ext.create('Ext.data.Store', {
				storeId:storeId,
			    model:model,
			    data:data
		     });
		}
	}
	if(!Ext.isEmpty(fields)){
		if(Ext.isEmpty(store)){
			store = Ext.create('Ext.data.Store', {
				storeId:storeId,
			    fields:fields,
			    data:data
		     });
		}
	}
	return store;
};
/**
 * Ajax请求--json
 * @param {} url  请求服务器url
 * @param {} params 请求参数
 * @param {} successFn 成功回调服务
 * @param {} failFn    失败回调服务
 */
pricing.requestJsonAjax = function(url,params,successFn,failFn)
{
	Ext.Ajax.request({
		url:url,
		jsonData:params,
		success:function(response){
			var result = Ext.decode(response.responseText);
			if(result.success){
				successFn(result);
			}else{
				failFn(result);
			}
		},
		failure:function(response){
			var result = Ext.decode(response.responseText);
			failFn(result);
		},
		exception:function(response){
			var result = Ext.decode(response.responseText);
			Ext.Msg.alert(pricing.airPricePlan.i18n('foss.pricing.promptMessage'),result.message);
		}
	});
};

/**
 * 航空运价费用方案明细模型
 */
Ext.define('Foss.pricing.airPricePlan.AirPricePlanDetailDto', {
    extend: 'Ext.data.Model',
    fields : [{
    	name : 'valuationId', //计费规则ID
        type : 'string'
    },{
        name : 'arrvRegionId', //目的区域ID
        type : 'string'
    },{
        name : 'arrvRegionName',//目的区域NAME
        type : 'string'
    },{
        name : 'productItemId',//产品条目ID
        type : 'string'
    },{
    	name : 'productItemId',//产品条目ID
        type : 'string'
    },{
        name : 'productItemCode',//产品条目CODE
        type : 'string'
    },{
        name : 'productItemName',//产品条目名称
        type : 'string'
    },{
        name : 'createTime', //创建时间
        type : 'date',
        defaultValue : null,
        convert : pricing.changeLongToDate
    },{
        name : 'heavyPrice'//重货价格
    },{
        name : 'lightPrice'//轻货价格 
    },{
        name : 'minimumOneTicket'//最低一票
    },{
     	name : 'centralizePickup'//是否接货
    },{
        name : 'remark',//备注
        type : 'string'
    },{
        name : 'pricePlanId',//
        type : 'string'
    },{
     	name : 'flightTypeCode',//1
        type : 'string'
    },{
     	name : 'flightTypeName',//1
        type : 'string'
    },{
    	name : 'combBillTypeCode',	//合票类型code
    	type : 'string'
    },{
    	name : 'combBillTypeName',	//合票类型名称
    	type : 'string'
    },{
     	name : 'goodsTypeCode',//1
        type : 'string'
    },{
     	name : 'goodsTypeName',//1
        type : 'string'
    }]
});

/**
 * 货物类别下拉列表查询
 */
pricing.goodTypeList = [];
//--------------------------------------pricing----------------------------------------
//查询货物类型列表
pricing.searchGoodTypeList = function(){
	Ext.Ajax.request({
		url:pricing.realPath('findGoodsTypeByCondiction.action'),//查询基础产品
		async:false,
		success:function(response){
			var result = Ext.decode(response.responseText);
			pricing.goodTypeList = result.pricingValuationVo.goodsTypeEntityList;
		},
		failure:function(response){
			var result = Ext.decode(response.responseText);
			if(Ext.isEmpty(result)){
				pricing.showErrorMes(pricing.airPricePlan.i18n('i18n.pricingRegion.requestTimeOut'));
			}else{
				pricing.showErrorMes(result.message);
			}
		},
		exception:function(response){
			var result = Ext.decode(response.responseText);
			if(Ext.isEmpty(result)){
				pricing.showErrorMes(pricing.airPricePlan.i18n('i18n.pricingRegion.requestTimeOut'));
			}else{
				pricing.showErrorMes(result.message);
			}
		}
	});
};


/**
 * 航空运价费用方案批次model
 */
Ext.define('Foss.pricing.airPricePlan.AirPricePlanModel', {
    extend: 'Ext.data.Model',
    fields : [{
        name : 'id',
        type : 'string'
    },{
        name : 'modifyDate',
        type : 'date',
        defaultValue : null,
        convert : pricing.changeLongToDate
    },{
        name : 'modifyUser',
        type : 'string'
    },{
        name : 'priceRegionId',
        type : 'string'
    },{
    	name : 'priceRegionName',
        type : 'string'
    },{
        name : 'priceRegionCode',
        type : 'string'
    },{
        name : 'name',
        type : 'string'
    },{
        name : 'beginTime',
        type : 'date',
        defaultValue : null,
        convert : pricing.changeLongToDate
    },{
        name : 'endTime',
        type : 'date',
        defaultValue : null,
        convert : pricing.changeLongToDate
    },{
        name : 'active',
        type : 'string'
    },{
        name : 'description',
        type : 'string'
    },{
        name : 'versionInfo',
        type : 'string'
    },{
        name : 'refId',
        type : 'string'
    },{
        name : 'currencyCode',
        type : 'string'
    },{
    	name : 'currentUsedVersion',
    	type : 'string'
    },{
    	name:'isPromptly'
    }]
});

/**
 *航空运价费用方案批次store
 */
Ext.define('Foss.pricing.airPricePlan.AirPricePlanStore',{
	extend: 'Ext.data.Store',
	model: 'Foss.pricing.airPricePlan.AirPricePlanModel',
	pageSize:20,
	proxy: {
		type : 'ajax',
		actionMethods:'POST',
		url: pricing.realPath('queryAirPricePlanBatchInfo.action'),
		reader : {
			type : 'json',
			root : 'priceManageMentVo.pricePlanEntityList',
			totalProperty : 'totalCount',
			successProperty: 'success'
		}
	},
	listeners: {
		beforeload: function(store, operation, eOpts){
			var n = pricing.queryform.getValues();
			Ext.apply(operation,{
				params : {
					'priceManageMentVo.pricePlanEntity.name' : 	  n.name,
					'priceManageMentVo.pricePlanEntity.priceRegionCode'	: n.priceRegionCode,
					'priceManageMentVo.pricePlanEntity.beginTime'	: n.beginTime,
					'priceManageMentVo.pricePlanEntity.endTime'	: n.endTime,
					'priceManageMentVo.pricePlanEntity.currentUsedVersion'	: n.currentUsedVersion
					//'flightPriceVo.flightPricePlanEntity.currentUsedVersion':queryForm.getForm().findField('currentUsedVersion').getValue()//是否当前版本
				}
			});			
		}
	}
});
/**
 * 航空运价费用方案明细store
 */
Ext.define('Foss.pricing.airPricePlan.AirPricePlanDeatilStore',{
	extend: 'Ext.data.Store',
	model: 'Foss.pricing.airPricePlan.AirPricePlanDetailDto',
	pageSize:10,
	proxy: {
		type : 'ajax',
		actionMethods:'POST',
		url: pricing.realPath('queryAirPricePlanDetailInfo.action'),
		reader : {
			type : 'json',
			root : 'priceManageMentVo.pricePlanDetailDtoList',
			totalProperty : 'totalCount',
			successProperty: 'success'
		}
	}
});


/**
 * 价格方案批次查询form表单
 */
Ext.define('Foss.pricing.airPricePlan.AirPricePlanFormPanel', {
	extend : 'Ext.form.Panel',
	title: pricing.airPricePlan.i18n('i18n.pricingRegion.searchCondition'),
	frame: true,
	collapsible: true,
    defaults : {
    	margin : '8 10 8 10',
    	labelWidth:80
    },
   	layout: 'auto',
	defaultType : 'textfield',
	layout:'column',
	items :[{
        xtype : 'textfield',
		fieldLabel:pricing.airPricePlan.i18n('foss.pricing.scenarioName'),//方案名称
		name: 'name',
        maxLength : 60,
        maxLengthText:pricing.airPricePlan.i18n('foss.pricing.theProgramNameLengthExceedsTheMaximumLimit'),
	    allowBlank:true,
	    columnWidth:.3
	},{
		xtype: 'container',
		columnWidth:.1,
		html: '&nbsp;'
	},{
		name: 'priceRegionCode',
	    fieldLabel:pricing.airPricePlan.i18n('foss.pricing.originatingArea'),//始发区域
	    xtype : 'commonpriceregionselector',
	    airPriceFlag:'Y',
	    columnWidth:.3
	},{//是否当前版本 是，否，全部
		
		name: 'currentUsedVersion',
		queryMode: 'local',
	    displayField: 'valueName',
	    valueField: 'valueCode',
	    editable:false,
	    value:'',
	    store:pricing.getStore(null,null,['valueName','valueCode']
	    ,[{'valueName':pricing.airPricePlan.i18n('i18n.pricing.currentVersion_yes'),'valueCode':pricing.yes}//是当前版本
	    , {'valueName':pricing.airPricePlan.i18n('i18n.pricing.currentVersion_no'),'valueCode':pricing.no}]),//不是当前
	    fieldLabel: pricing.airPricePlan.i18n('foss.pricing.currentUsedVersion'),//是否当前版本
	    xtype : 'combo'
	},{
		xtype:'datefield',
		fieldLabel:pricing.airPricePlan.i18n('foss.pricing.availabilityDate'),//生效日期
		format:'20y-m-d',
		name:'beginTime',
		columnWidth:.3
	},{
		xtype: 'container',
		columnWidth:.1,
		html: '&nbsp;'
	},{
		xtype:'datefield',
		fieldLabel:pricing.airPricePlan.i18n('foss.pricing.deadline'),//截止日期
		format:'20y-m-d',
		name:'endTime',
		columnWidth:.3
	},{
		xtype: 'container',
		columnWidth:.2,
		html: '&nbsp;'
	},{
		xtype : 'container',
		margin : '0 0 0 0',
		items : [{
			xtype : 'button', 
			width:70,
			cls:'yellow_button',
			text : "查询",
			disabled: !pricing.airPricePlan.isPermission('airPricePlan/airPricePlanQueryButton'),
			hidden: !pricing.airPricePlan.isPermission('airPricePlan/airPricePlanQueryButton'),
			handler : function() {
				var grid = Ext.getCmp('T_pricing-airPricePlan_content').getAirPricePlanGridPanel();
				grid.getPagingToolbar().moveFirst();
			}
		}]
	},{
		xtype: 'displayfield',
		columnWidth : .15,
		value:pricing.airPricePlan.i18n('foss.pricing.specialP')//根据业务日期查询介于生效日期和截止日期之间的增值服务
	}]
});

//-------------------查询详情------------------
/**
 * 航空运价费用方案明细信息查看WINDOW
 */
Ext.define('Ext.pricing.airPricePlan.AirPricePlanDetailShowWindow',{
	extend : 'Ext.window.Window',
	title: pricing.airPricePlan.i18n('foss.pricing.thePriceOfTheProgramDetailQuery'),//始发区域与目的区域时效方案明细查询
	closable : true,
	modal : true,
	resizable:false,
	closeAction : 'hide',
	parent:null,
	pricePlanId:null,//价格方案ID
	width :700,
	height :650,
	listeners:{
		beforehide:function(me){
			//清楚上次使用的数据
			me.getQueryPricePlanDetailForm().getForm().reset();
			me.getPricePlanDetailShowGridPanel().getStore().removeAll();
			me.pricePlanId = null;
		},
		beforeshow:function(me){
			//me.getEditForm().isSearchComb = true;
		}
	},
    //明细信息查询-FORM
	queryPricePlanDetailForm:null,
    getQueryPricePlanDetailForm:function(){
    	if(Ext.isEmpty(this.queryPricePlanDetailForm)){
    		this.queryPricePlanDetailForm = Ext.create('Foss.pricing.airPricePlan.QueryAirPricePlanDetailForm');
    	}
    	return this.queryPricePlanDetailForm;
    },
    //明细信息结果集
    pricePlanDetailShowGridPanel:null,
    getPricePlanDetailShowGridPanel:function(){
    	if(Ext.isEmpty(this.pricePlanDetailShowGridPanel)){
    		this.pricePlanDetailShowGridPanel = Ext.create('Foss.pricing.airPricePlan.AirPricePlanDetailShowGridPanel');
    	}
    	return this.pricePlanDetailShowGridPanel;
    },
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [me.getQueryPricePlanDetailForm(),me.getPricePlanDetailShowGridPanel()];//设置window的元素
		me.callParent([cfg]);
	}
});



/**
 * 航空运价费用方案批次列表gird
 */
Ext.define('Foss.pricing.airPricePlan.AirPricePlanGridPanel',{
	extend: 'Ext.grid.Panel',
	title : pricing.airPricePlan.i18n('i18n.pricingRegion.searchResults'),//查询结果
	emptyText: pricing.airPricePlan.i18n('foss.pricing.theQueryResultIsEmpty'),//查询结果为空
	frame: true,
	cls: 'autoHeight',
	bodyCls: 'autoHeight', 
	sortableColumns:false,
    enableColumnHide:false,
	selType : "rowmodel", 
	enableColumnMove:false,
	stripeRows:true, 
	border: true,
	defaults: {
		width: 150
	},
	
	//返回批次新增弹出框
	addPricePlanWindow:null,
	getAddpricePlanWindow : function(){
		if(Ext.isEmpty(this.addPricePlanWindow)){
			this.addPricePlanWindow = Ext.create('Foss.pricing.airPricePlan.AirPricePlanAddWindow');
			this.addPricePlanWindow.parent = this;
		}
		return this.addPricePlanWindow;
	},
	
	updatePricePlanWindow : null,
	//获取修改价格方案窗口
	getUpdatePricePlanWindow : function(){
		if(Ext.isEmpty(this.updateStandardWindow)){
			this.updatePricePlanWindow = Ext.create('Foss.pricing.airPricePlan.AirPricePlanUpdateWindow');
			//设置器父元素
			this.updatePricePlanWindow.parent = this;
		}
		return this.updatePricePlanWindow;
	},
	
	
	//中止方案弹出日期选择
	stopPricePlanWindow:null,
	getStopPricePlanWindow: function(pricePlanId){
		if(Ext.isEmpty(this.stopPricePlanWindow)){
			this.stopPricePlanWindow = Ext.create('Foss.pricing.airPricePlan.AirPricePlanStopEndTimeWindow',{
				'pricePlanId':pricePlanId
			});
			this.stopPricePlanWindow.parent = this;
		}
		return this.stopPricePlanWindow;
	},
	
	
	   /**
     * 立即生效
     * 对于实际业务可能在当天发生两次以上的调整，故给予立即激中止功能用于支持该业务，
     * 1、立即中止功能给予特定角色来操作。根据所登陆的用户所属某某角色来决定是否给予立即中止功能。防止一般用户进行当天频繁调价操作
     * 2、立即中止功能中止日期可以等于今天但是不能小于今天。
     * 
     */
    immediatelyActivePricePlanWindow:null,
	getimmediatelyActiveAirPricePlanWindow: function(){
		if(Ext.isEmpty(this.immediatelyActivePricePlanWindow)){
//			this.immediatelyActivePricePlanWindow = Ext.create('Foss.pricing.airPricePlan.AirPricePlanImmediatelyActiveTimeWindow',{
//				pricePlanEntity:pricePlanEntity
//			});
			this.immediatelyActivePricePlanWindow = Ext.create('Foss.pricing.airPricePlan.AirPricePlanImmediatelyActiveTimeWindow');
			this.immediatelyActivePricePlanWindow.parent = this;
		}
		return this.immediatelyActivePricePlanWindow;
	},
	
	
	/**
     * 立即中止
     * 对于实际业务可能在当天发生两次以上的调整，故给予立即激中止功能用于支持该业务，
     * 1、立即中止功能给予特定角色来操作。根据所登陆的用户所属某某角色来决定是否给予立即中止功能。防止一般用户进行当天频繁调价操作
     * 2、立即中止功能中止日期可以等于今天但是不能小于今天。
     * 
     */
    immediatelyStopPricePlanWindow:null,
	getImmediatelyStopAirPricePlanWindow: function(pricePlanEntity){
		if(Ext.isEmpty(this.immediatelyStopPricePlanWindow)){
			this.immediatelyStopPricePlanWindow = Ext.create('Foss.pricing.airPricePlan.AirPricePlanImmediatelyStopEndTimeWindow',{
				pricePlanEntity:pricePlanEntity
			});
			this.immediatelyStopPricePlanWindow.parent = this;
		}
		return this.immediatelyStopPricePlanWindow;
	},
	
	
	/**
	 * 立即中止
	 */
    immediatelyStopAirPricePlan:function(){
    	var me = this;
	 	var selections = me.getSelectionModel().getSelection();
	 	if(selections.length < 1){
	 		pricing.showWoringMessage('请选择一条记录进行操作！');
			return;
	 	}
	 	if(selections.length > 1){
	 		pricing.showWoringMessage('请选择一条记录进行操作！');
			return;
	 	}
	 	if(selections[0].get('active')!=pricing.yes){
	 		pricing.showWoringMessage('请选择已激活数据进行操作！');
	 		return;
	 	}else{
	 		var pricePlanEntity = selections[0].data;
	 		me.getImmediatelyStopAirPricePlanWindow(pricePlanEntity).show();
	 	}
	},
	
	/**
	 * 立即激活
	 */
    immediatelyActiveAirPricePlan:function(){
    	var me = this;
	 	var selections = me.getSelectionModel().getSelection();
	 	if(selections.length < 1){
	 		pricing.showWoringMessage('请选择一条记录进行操作！');
			return;
	 	}
	 	if(selections.length > 1){
	 		pricing.showWoringMessage('请选择一条记录进行操作！');
			return;
	 	}
	 	if(selections[0].get('active')==pricing.yes){
	 		pricing.showWoringMessage('请选择未激活数据进行操作！');
	 		return;
	 	}else{
	 		var pricePlanEntity = selections[0].data;
//	 		me.getimmediatelyActiveAirPricePlanWindow(pricePlanEntity).show();
	 		me.getimmediatelyActiveAirPricePlanWindow().pricePlanEntity=pricePlanEntity;
	 		me.getimmediatelyActiveAirPricePlanWindow().show();
	 	}
	},
	
	//返回批次store
	pricePlanStore:null,
	getPricePlanStore: function(){
		if(Ext.isEmpty(this.pricePlanStore)){
			this.pricePlanStore = Ext.create('Foss.pricing.airPricePlan.AirPricePlanStore');
		}
		return this.pricePlanStore;
	},
	
	checkboxModel:null,
	getCheckboxModel:function(){
		if(Ext.isEmpty(this.checkboxModel)){
			this.checkboxModel = Ext.create('Ext.selection.CheckboxModel');	
		}
		return this.checkboxModel;
	},
	
	//返回分页toolbbar
	pagingToolbar : null,
	getPagingToolbar : function() {
		if (Ext.isEmpty(this.pagingToolbar)) {
			this.pagingToolbar = Ext.create('Deppon.StandardPaging', {
				store : this.store,
				pageSize : 20
			});
		}
		return this.pagingToolbar;
	},
	//查看详情界面
	pricePlanDetailShowWindow:null,
	getPricePlanDetailShowWindow:function(){
		if(Ext.isEmpty(this.pricePlanDetailShowWindow)){
			this.pricePlanDetailShowWindow = Ext.create('Ext.pricing.airPricePlan.AirPricePlanDetailShowWindow');	
			this.pricePlanDetailShowWindow.parent = this;
		}
		return this.pricePlanDetailShowWindow;
	},
	
	//删除价格方案
	deletePricePlan:function(){
	 	var me = this;
	 	var selections = me.getSelectionModel().getSelection();
	 	if(selections.length < 1){
	 		pricing.showWoringMessage(pricing.airPricePlan.i18n('foss.pricing.pleaseSelectOneDeleteOperation'));
			return;
	 	}
	 	//过滤草稿状态数据进行删除
	 	for(var i = 0;i<selections.length;i++){
			if(selections[i].get('active')==pricing.yes){
				pricing.showWoringMessage(pricing.airPricePlan.i18n('foss.pricing.pleaseChooseToNotActivateTheDataToBeBeleted'));
				return;
			}
		}
		//是否要删除这些汽运价格方案？
		pricing.showQuestionMes(pricing.airPricePlan.i18n('foss.pricing.theseTheAirPriceProgramYouWantToRemove'),function(e){
			if(e=='yes'){
				//汽运价格方案
				var idList = new Array();
				for(var i = 0 ; i<selections.length ; i++){
					idList.push(selections[i].get('id'));
				}
				var params = {'priceManageMentVo':{'pricePlanIds':idList}};
				var successFun = function(json){
					pricing.showInfoMes(json.message);
					me.getPagingToolbar().moveFirst();
				};
				var failureFun = function(json){
					if(Ext.isEmpty(json)){
						pricing.showErrorMes(pricing.airPricePlan.i18n('foss.pricing.requestTimedOut'));//请求超时
					}else{
						pricing.showErrorMes(json.message);
					}
				};
				var url = pricing.realPath('deleteAirPricePlan.action');
				pricing.requestJsonAjax(url,params,successFun,failureFun);
			}
		});
	},
	
	//激活时效方案
    activePricePlan:function(){
    	var me = this;
		var pricePlans = new Array();
		//获取选中的数据
		var selections = me.getSelectionModel().getSelection();
		if(selections.length<1){
			pricing.showErrorMes(pricing.airPricePlan.i18n('foss.pricing.pleaseChooseThePriceYouWantToActivateTheProgram'));//请选择要激活的价格方案！
			return;
		}
		
		//过滤草稿状态数据进行删除
	 	for(var i = 0;i<selections.length;i++){
			if(selections[i].get('active')==pricing.yes){
				pricing.showWoringMessage(pricing.airPricePlan.i18n('foss.pricing.pleaseSelectNotActivateProgramDataForActivation'));
				return;
			}
			pricePlans.push(selections[i].get('id'));
		}
		
		if(pricePlans.length<1){
			pricing.showErrorMes(pricing.airPricePlan.i18n('foss.pricing.pleaseSelectAtLeastOneInactivePriceProgram'));//请至少选择一条未激活的价格方案！
			return;
		}
		
		//是否要激活这些汽运价格方案？
		pricing.showQuestionMes(pricing.airPricePlan.i18n('foss.pricing.doYouWantActivateTheseAirCargoPriceScheme'),function(e){
			if(e=='yes'){
				var params = {'priceManageMentVo':{'pricePlanIds':pricePlans}};
				var successFun = function(json){
					pricing.showInfoMes(json.message);
					me.getPagingToolbar().moveFirst();
				};
				var failureFun = function(json){
					if(Ext.isEmpty(json)){
						pricing.showErrorMes(pricing.airPricePlan.i18n('i18n.pricingRegion.requestTimeOut'));//请求超时
					}else{
						pricing.showErrorMes(json.message);
					}
				};
				var url = pricing.realPath('activeAirPricePlan.action');
				pricing.requestJsonAjax(url,params,successFun,failureFun);
			}
		});
    },
    
    
    uploadPriceform : null,
    /**
     * 上传时效方案信息
     * @return {}
     */
    getUploadPriceform: function(){
    	if(Ext.isEmpty(this.uploadPriceform)){
			this.uploadPriceform = Ext.create('Foss.pricing.airPricePlan.UploadAirPriceform');	
		}
		return this.uploadPriceform;
    },
    
    
    /**
     * 
     * 导出
     */
    queryPricePlanForm: null,
    getQueryPricePlanForm:function(){
		if(Ext.isEmpty(this.queryPricePlanForm)){
			this.queryPricePlanForm  = Ext.create('Foss.pricing.airPricePlan.AirPricePlanFormPanel')
		}
		return this.queryPricePlanForm;
    },
    
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.store = me.getPricePlanStore();
		me.selModel = me.getCheckboxModel();
		me.bbar = me.getPagingToolbar();
		me.columns = [{xtype: 'rownumberer',
			width:40,
			text : "序号"//序号
		},{
			text : pricing.airPricePlan.i18n('i18n.pricingRegion.opra'),
			align : 'center',
			xtype : 'actioncolumn',
			items: [{
				iconCls: 'deppon_icons_showdetail',
                tooltip: pricing.airPricePlan.i18n('foss.pricing.details'), 
                disabled: !pricing.airPricePlan.isPermission('airPricePlan/airPricePlanQueryDetailButton'),
				width:42,
                handler: function(grid, rowIndex, colIndex) {
    				var record=grid.getStore().getAt(rowIndex);
                	me.getPricePlanDetailShowWindow().show();
                	me.getPricePlanDetailShowWindow().pricePlanId = record.get('id');
                }
			},{
				iconCls:'deppon_icons_edit',
				tooltip: pricing.airPricePlan.i18n('foss.pricing.toAmendTheProposal'), 
				disabled: !pricing.airPricePlan.isPermission('airPricePlan/airPricePlanUpdateButton'),
				width:42,
				getClass : function (v, m, r, rowIndex) {
					if (r.get('active') === 'N') {
					    return 'deppon_icons_edit';
					} else {
					    return 'statementBill_hide';
					}
				},
				handler: function(grid, rowIndex, colIndex){
					var me = this;
                	var record = grid.up().getStore().getAt(rowIndex);//选中数据
                	var updateWindow =  grid.up().getUpdatePricePlanWindow();
                	var params = {'priceManageMentVo':{'pricePlanId':record.get('id')}};
    				var successFun = function(json){
						updateWindow.pricePlanEntity = json.priceManageMentVo.pricePlanEntity;
    					pricing.pricePlanId = json.priceManageMentVo.pricePlanEntity.id;
    					updateWindow.isUpdate = true;
    					updateWindow.show();
    					pricing.pagingBar.moveFirst();
    				};
    				var failureFun = function(json){
    					if(Ext.isEmpty(json)){
    						pricing.showErrorMes(pricing.airPricePlan.i18n('foss.pricing.requestTimedOut'));//请求超时
    					}else{
    						pricing.showErrorMes(json.message);
    					}
    				};
    				var url = pricing.realPath('queryAirPricePlanAndDetailInfoById.action');
    				pricing.requestJsonAjax(url,params,successFun,failureFun);
				}
			},{
				iconCls:'deppon_icons_softwareUpgrade',
				tooltip: pricing.airPricePlan.i18n('foss.pricing.replicationScheme'), 
				disabled: !pricing.airPricePlan.isPermission('airPricePlan/airPricePlanCopyButton'),
				width:42,
					getClass : function (v, m, r, rowIndex) {
					if (r.get('active') === 'Y') {
					    return 'deppon_icons_softwareUpgrade';
					} else {
					    return 'statementBill_hide';
					}
				},
				handler: function(grid, rowIndex, colIndex){
					var record = grid.getStore().getAt(rowIndex);
					var pricePlanId = record.get('id');
					var active = record.get('active');
					if(active == 'N'){
						pricing.showErrorMes(pricing.airPricePlan.i18n('foss.pricing.pleaseSelectTheProgramHaBbeenActivatedCopy'));
					}else{
						pricing.showQuestionMes(pricing.airPricePlan.i18n('foss.pricing.toDeterminePriceProgramCopy'),function(e){
							if(e=='yes'){
								var me = this;
								//处理复制功能
								var updateWindow =  grid.up().getUpdatePricePlanWindow();
								var params = {'priceManageMentVo':{'pricePlanId':pricePlanId}};
								var successFun = function(json){
									pricing.showInfoMes(json.message);
									updateWindow.pricePlanEntity = json.priceManageMentVo.pricePlanEntity;
									pricing.pricePlanId = json.priceManageMentVo.pricePlanEntity.id;
			    					updateWindow.isUpdate = true;
			    					updateWindow.show();
			    					pricing.pagingBar.moveFirst();
								};
								var failureFun = function(json){
									if(Ext.isEmpty(json)){
										pricing.showErrorMes(pricing.airPricePlan.i18n('foss.pricing.requestTimedOut'));//请求超时
									}else{
										pricing.showErrorMes(json.message);
									}
								};
								var url = pricing.realPath('copyAirPricePlan.action');
								pricing.requestJsonAjax(url,params,successFun,failureFun);
							}
						});
					}
				}
			},{
				iconCls:'deppon_icons_end',
				tooltip: pricing.airPricePlan.i18n('foss.pricing.stop'), 
				disabled: !pricing.airPricePlan.isPermission('airPricePlan/airPricePlanStopButton'),
				width:42,
				handler: function(grid, rowIndex, colIndex){
					//得到当前行
					var record = grid.getStore().getAt(rowIndex);
					//得到当前行中的具体字段
					var pricePlanId = record.get('id');
					me.getStopPricePlanWindow(pricePlanId).show();
				}
			}]
		},{
			width: 140,
			text: "方案名称",
	        dataIndex: 'name'
		},{
			width: 140,
			text: "始发区域",
	        dataIndex: 'priceRegionName'
		},{
			text: "修改时间",
			width: 140,
	        dataIndex: 'modifyDate',
	        renderer:function(value){
				return Ext.Date.format(new Date(value), 'Y-m-d H:m:s');
			}
		},{
			text: "修改人",
			width: 140,
	        dataIndex: 'modifyUser'
		},{
			text: "生效日期",
	        width: 80,
	        dataIndex: 'beginTime',
	        renderer:function(value){
				return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
			}
		},{
			text: "截止日期",
	        width: 80,
	        dataIndex: 'endTime',
	        renderer:function(value){
				return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
			}
		},{
			width: 80,
			text: "数据状态",
	        sortable: true,
	        dataIndex: 'active',
	        renderer:function(value){
				if(value=='Y'){//'Y'表示激活
					return "已激活";
				}else if(value=='N'){//'N'表示未激活
					return  "未激活";
				}else{
					return '';
				}
			}
		},{
			text : '是否当前版本',
			dataIndex : 'currentUsedVersion'
		}];
		me.tbar = [
		{
			text : "新建方案",
			disabled: !pricing.airPricePlan.isPermission('airPricePlan/airPricePlanAddButton'),
			hidden: !pricing.airPricePlan.isPermission('airPricePlan/airPricePlanAddButton'),
			handler :function(){
				var addWindow = me.getAddpricePlanWindow();
				addWindow.isUpdate = false;
				addWindow.show();
				
			} 
		},'-', {
			text : pricing.airPricePlan.i18n('foss.pricing.activationProgram'),
			disabled: !pricing.airPricePlan.isPermission('airPricePlan/airPricePlanActiveButton'),
			hidden: !pricing.airPricePlan.isPermission('airPricePlan/airPricePlanActiveButton'),
			handler :function(){
				me.activePricePlan();
			} 
		},'-', {
			text : pricing.airPricePlan.i18n('foss.pricing.deleteProgram'),
			disabled: !pricing.airPricePlan.isPermission('airPricePlan/airPricePlanDeleteButton'),
			hidden: !pricing.airPricePlan.isPermission('airPricePlan/airPricePlanDeleteButton'),
			handler :function(){
				me.deletePricePlan();
			} 
		},'-',{
			text : pricing.airPricePlan.i18n('foss.pricing.immediatelyActivationProgram'),//'立即激活',
			disabled:!pricing.airPricePlan.isPermission('airPricePlan/airPricePlanImmediatelyActiveButton'),
			hidden:!pricing.airPricePlan.isPermission('airPricePlan/airPricePlanImmediatelyActiveButton'),
			handler :function(){
				me.immediatelyActiveAirPricePlan();
			} 
		},'-', {
			text : pricing.airPricePlan.i18n('foss.pricing.immediatelyStopProgram'),//'立即中止',
			disabled:!pricing.airPricePlan.isPermission('airPricePlan/airPricePlanImmediatelyStopButton'),
			hidden:!pricing.airPricePlan.isPermission('airPricePlan/airPricePlanImmediatelyStopButton'),
			handler :function(){
				me.immediatelyStopAirPricePlan();
			} 
		},'-', {
			text : pricing.airPricePlan.i18n('foss.pricing.export'),
			disabled: !pricing.airPricePlan.isPermission('airPricePlan/airPricePlanExportButton'),
			hidden: !pricing.airPricePlan.isPermission('airPricePlan/airPricePlanExportButton'),
			handler :function(){
				var queryForm = me.getQueryPricePlanForm();
				var pricePlanExport = '';
				var name = queryForm.getForm().findField('name').getValue(); // 方案名称
				var deptRegionCode = queryForm.getForm().findField('priceRegionCode').getValue(); //始发区域编码
				if(!Ext.isEmpty(name)){
					pricePlanExport ='priceManageMentVo.pricePlanEntity.name='+name;
				}
				if(!Ext.isEmpty(deptRegionCode)){
					if(!Ext.isEmpty(pricePlanExport)){
						pricePlanExport = pricePlanExport+'&'+'priceManageMentVo.pricePlanEntity.deptRegionCode='+deptRegionCode;
					}else{
						pricePlanExport = 'priceManageMentVo.pricePlanEntity.deptRegionCode='+deptRegionCode;
					}
				}
				var url = pricing.realPath('exportAirPricePlan.action');
				if(!Ext.isEmpty(pricePlanExport)){
					url = url+'?'+pricePlanExport;
				}
				window.location=url;
				pricePlanExport = '';
 			} 
		},'-', {
			text : pricing.airPricePlan.i18n('foss.pricing.import'),
			disabled: !pricing.airPricePlan.isPermission('airPricePlan/airPricePlanImportButton'),
			hidden: !pricing.airPricePlan.isPermission('airPricePlan/airPricePlanImportButton'),
			handler :function(){
			 	me.getUploadPriceform().show();
			} 
		}];
		pricing.pagingBar = me.bbar;
		me.callParent([cfg]);
	}
 	
});
/**
 * 航空运价费用方案明细查询表单
 */
Ext.define('Foss.pricing.airPricePlan.QueryAirPricePlanDetailForm', {
	extend : 'Ext.form.Panel',
	title: pricing.airPricePlan.i18n('i18n.pricingRegion.searchCondition'),
	frame: true,
	collapsible: true,
    defaults : {
    	columnWidth : .4,
    	margin : '8 10 5 10',
    	anchor : '100%'
    },
    height :100,
	defaultType : 'textfield',
	layout:'column',
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.items = [{
			xtype : 'combobox',
			name : 'flightSort',
			allowBlank:false,
			fieldLabel : pricing.airPricePlan.i18n('foss.pricing.flightCategory'),
			displayField : 'valueName',
			valueField : 'valueCode',
			queryMode : 'local',
			triggerAction : 'all',
			editable : false,
			flightRecord: null,
			store : FossDataDictionary.getDataDictionaryStore('AIR_FLIGHT_TYPE', 'Foss_baseinfo_flight_FlightSortStore_Id', {
				'valueCode' : '',
				'valueName' : pricing.airPricePlan.i18n('i18n.pricingRegion.all')
			}),
			value : ''
		},{
			name: 'arrvRegionId',
			valueField: 'id',
	        fieldLabel:pricing.airPricePlan.i18n('foss.pricing.destinationArea'),//目的区域
	        xtype : 'commonpriceregionselector',
	        airPriceFlag :'Y'
		},{
			xtype : 'container',
			columnWidth : .2,
			margin : '0 0 0 0',
			items : [{
				xtype : 'button', 
				width:70,
				text : pricing.airPricePlan.i18n('i18n.pricingRegion.search'),
				cls:'yellow_button',
				handler : function() {
					if(me.getForm().isValid()){
						var grid = me.up('window').getPricePlanDetailShowGridPanel();
						grid.getStore().load();
					}
					
				}
			}]
		}];
		me.callParent([cfg]);
	}
});
/**
 * 航空运价费用方案明细列表
 */
Ext.define('Foss.pricing.airPricePlan.AirPricePlanDetailShowGridPanel', {
	extend: 'Ext.grid.Panel',
	title :pricing.airPricePlan.i18n('i18n.pricingRegion.searchResults'),//查询结果
	frame: true,
	height :450,
    sortableColumns:false,
    enableColumnHide:false,
    enableColumnMove:false,
	stripeRows : true, // 交替行效果
	selType : "rowmodel", // 选择类型设置为：行选择
	emptyText: pricing.airPricePlan.i18n('foss.pricing.theQueryResultIsEmpty'),//查询结果为空
	
	//得到bbar
	pagingToolbar : null,
	getPagingToolbar : function() {
		if (Ext.isEmpty(this.pagingToolbar)) {
			this.pagingToolbar = Ext.create('Deppon.StandardPaging', {
				store : this.store,
				pageSize : 10
			});
		}
		return this.pagingToolbar;
	}, 
	
	
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.columns = [{xtype: 'rownumberer',
			width:40,
			text : pricing.airPricePlan.i18n('i18n.pricingRegion.num')//序号
		},{
			text :pricing.airPricePlan.i18n('foss.pricing.destinationStation'),//目的站
			dataIndex : 'arrvRegionName',
			flex:2
		},{
			text :pricing.airPricePlan.i18n('foss.pricing.flightType'),//航班类型
			dataIndex : 'flightTypeName',
			flex:2
		},{
			text :pricing.airPricePlan.i18n('foss.pricing.combBillType'),//合票类型
			dataIndex : 'combBillTypeName',
			width:100,
			flex:2
		},{
			text :pricing.airPricePlan.i18n('foss.pricing.cargoType'),//货物类型
			dataIndex : 'goodsTypeName',
			flex:2
		},{
			text :pricing.airPricePlan.i18n('foss.pricing.heavyGoodsPrices'),//重货价格
			dataIndex : 'heavyPrice',
			flex:2
		},{
			text : pricing.airPricePlan.i18n('foss.pricing.theLowestVotes'),//最低一票
			dataIndex : 'minimumOneTicket',
			flex:2
		},{
			width: 100,
			text: "是否接货",
	        dataIndex: 'centralizePickup',
	        renderer:function(value){
				if(value=='Y'){//'Y'表示激活
					return "是";
				}else if(value=='N'){//'N'表示未激活
					return  "否";
				}else{
					return '';
				}
			}
		},{
			text : pricing.airPricePlan.i18n('foss.pricing.remark'),//备注
			dataIndex : 'remark',
			flex:2
		}];
		me.store = Ext.create('Foss.pricing.airPricePlan.AirPricePlanDeatilStore',{
			autoLoad : false,
			pageSize : 10,
			listeners: {
				beforeload: function(store, operation, eOpts){
					var queryForm =me.up('window').getQueryPricePlanDetailForm();
					var pricePlanId = me.up('window').pricePlanId;
					if(queryForm!=null){
						Ext.apply(operation,{
							params : {
								'priceManageMentVo.queryPricePlanDetailBean.flightTypeCode' : queryForm.getForm().findField('flightSort').getValue(),//航班类型编码
								'priceManageMentVo.queryPricePlanDetailBean.arrvRegionId' : queryForm.getForm().findField('arrvRegionId').getValue(),//目的区域编码
								'priceManageMentVo.queryPricePlanDetailBean.pricePlanId' : pricePlanId//价格方案ID
							}
						});	
					}
				}
		    }
		});
		me.listeners = {
	    	scrollershow: function(scroller) {
	    		if (scroller && scroller.scrollEl) {
	    				scroller.clearManagedListeners(); 
	    				scroller.mon(scroller.scrollEl, 'scroll', scroller.onElScroll, scroller); 
	    		}
	    	}
	    },
		me.selModel = Ext.create('Ext.selection.CheckboxModel',{//多选框
					mode:'MULTI',
					checkOnly:true
				});
		me.tbar = [
		   		{
		   			text : pricing.airPricePlan.i18n('foss.pricing.export'),
		   			handler :function(){
		   				var queryForm = me.up('window').getQueryPricePlanDetailForm();
		   				var pricePlanId = me.up('window').pricePlanId;
		   				var flightTypeCode = queryForm.getForm().findField('flightSort').getValue()
		   				var arrvRegionId = queryForm.getForm().findField('arrvRegionId').getValue();//目的区域ID
		   				var pricePlanExport = '';
		   				if(!Ext.isEmpty(pricePlanId)){
		   					pricePlanExport ='priceManageMentVo.queryPricePlanDetailBean.pricePlanId='+pricePlanId;
		   				}
		   				if(!Ext.isEmpty(arrvRegionId)){
		   					if(!Ext.isEmpty(pricePlanExport)){
		   						pricePlanExport = pricePlanExport+'&'+'priceManageMentVo.queryPricePlanDetailBean.arrvRegionId='+arrvRegionId;
		   					}else{
		   						pricePlanExport = 'priceManageMentVo.queryPricePlanDetailBean.arrvRegionId='+arrvRegionId;
		   					}
		   				}
		   				if(!Ext.isEmpty(flightTypeCode)){
		   					if(!Ext.isEmpty(flightTypeCode)){
		   						pricePlanExport = pricePlanExport+'&'+'priceManageMentVo.queryPricePlanDetailBean.flightTypeCode='+flightTypeCode;
		   					}else{
		   						pricePlanExport = 'priceManageMentVo.queryPricePlanDetailBean.flightTypeCode='+flightTypeCode;
		   					}
		   				}
		   				var url = pricing.realPath('exportAirPricePlanDetail.action');
		   				if(!Ext.isEmpty(pricePlanExport)){
		   					url = url+'?'+pricePlanExport;
		   				}
		   				window.location=url;
		   				pricePlanExport = '';
		    			} 
		}];
		me.bbar = me.getPagingToolbar();
		me.callParent([cfg]);
	}
});

//货物类型MODEL
Ext.define('Foss.pricing.GoodsTypeEntity', {
    extend: 'Ext.data.Model',
    fields : [{
        name : 'id',
        type : 'string'//id
    },{
        name : 'code',//编号
        type : 'string'
    },{
        name : 'name',//名称
        type : 'string'
    },{
        name : 'active',//是否激活
        type : 'string'
    },{
        name : 'description',//描述
        type : 'string'
    }]
});

/**
 * 航空运价费用方案明细form
 */
Ext.define('Foss.pricing.airPricePlan.AirPricePlanDetailForm', {
	extend : 'Ext.form.Panel',
	frame: true,
	height:430,
	collapsible: true,
	isSearchComb:true,
    defaults : {
    	colspan : 2,
    	margin : '5 5 5 5',
    	labelWidth:90,
    	anchor : '100%'
    },
	defaultType : 'textfield',
	layout: {
        type: 'table',
        columns: 2
    },
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [{
			name: 'arrvRegionCode',
	        fieldLabel:pricing.airPricePlan.i18n('foss.pricing.destinationArea'),//目的区域
	        allowBlank:false,
	        xtype : 'commonpriceregionselector',
	        airPriceFlag:'Y',
	        arrvRegionName:null,
	        arrvRegionId:null,
	        listeners:{
	        	select:function(comb,records,eOpts){
	        		var record = records[0];
	        		comb.arrvRegionId = record.get('id');
	        		comb.arrvRegionName = record.get('regionName'); 
	        	}
	        }
		},{	
			xtype : 'combobox',
			name : 'flightSort',
			allowBlank:false,
			fieldLabel : pricing.airPricePlan.i18n('foss.pricing.flightCategory'),
			displayField : 'valueName',
			valueField : 'valueCode',
			valueName:null,
			valueCode:null,
			queryMode : 'local',
			triggerAction : 'all',
			editable : false,
			store : FossDataDictionary.getDataDictionaryStore('AIR_FLIGHT_TYPE', 'Foss_baseinfo_flight_FlightSortStore_Id'),
			value : '',
			listeners:{
	        	select:function(comb,records,eOpts){
	        		var record = records[0];
	        		comb.valueName = record.get('valueName');
	        		comb.valueCode = record.get('valueCode'); 
	        	}
	        }
		},{
			xtype : 'combo',
			name: 'combBillType',
			queryMode: 'local',
			displayField: 'valueName',
		    valueField: 'valueCode',
		    triggerAction : 'all',
		    allowBlank:false,
		    editable:false,
		    store:FossDataDictionary.getDataDictionaryStore('MAKE_WAYBILL_WAY','a123'),
	        fieldLabel: pricing.airPricePlan.i18n('foss.pricing.combBillType'),//合票类型
	        goodsTypeName:null,
	        goodsTypeCode:null,
	        listeners:{
	        	select:function(comb,records,eOpts){
	        		var record = records[0];
	        		comb.goodsTypeCode = record.get('code');
	        		comb.goodsTypeName = record.get('name'); 
	        	}
	        }
		},{
			xtype : 'combo',
			name: 'goodsTypeId',
			queryMode: 'local',
			displayField: 'name',
		    valueField: 'code',
		    allowBlank:false,
		    editable:false,
		    store:pricing.getStore(null,'Foss.pricing.GoodsTypeEntity',null,pricing.goodTypeList),
	        fieldLabel: pricing.airPricePlan.i18n('foss.pricing.cargoType'),//货物类型
	        goodsTypeName:null,
	        goodsTypeCode:null,
	        listeners:{
	        	select:function(comb,records,eOpts){
	        		var record = records[0];
	        		comb.goodsTypeCode = record.get('code');
	        		comb.goodsTypeName = record.get('name'); 
	        	}
	        }
		},{
			name: 'heavyPrice',
			allowBlank:false,
			decimalPrecision:2,
			step:0.01,
		    maxValue: 999999.99,
		    minValue:0,
	        fieldLabel: pricing.airPricePlan.i18n('foss.pricing.heavyGoodsPrices'),//重货价格
	        xtype : 'numberfield'
		
		},{
			name: 'minimumOneTicket',
			allowBlank:false,
			decimalPrecision:0,
			step:1,
		    maxValue: 99999999,
		    minValue:0,
	        fieldLabel: pricing.airPricePlan.i18n('foss.pricing.theLowestVotes'),//最低一票
	        xtype : 'numberfield'
		},{
			 xtype:'radiogroup',
			 vertical:true,
			 allowBlank:false,
			 name:'centralizePickup',
			 fieldLabel:pricing.airPricePlan.i18n('foss.pricing.whetherorNot'),
			 items:[{
			 	 xtype:'radio',
			     boxLabel:pricing.airPricePlan.i18n('i18n.pricingRegion.ye'),//是
			     name:'centralizePickup',		 
			     inputValue:'Y'
		     },{
		    	 xtype:'radio',
			     boxLabel:pricing.airPricePlan.i18n('i18n.pricingRegion.no'),//否
			     name:'centralizePickup',
			     inputValue:'N'
			     }]
		},{
			xtype: 'textareafield',
			width:300,
		    fieldLabel: pricing.airPricePlan.i18n('foss.pricing.remark'),//备注
		    maxLength:200,
	        name:'remark'
		}];
		me.callParent([cfg]);
	}
});

/**
 * 航空运价费用方案明细信息新增弹出窗口window
 */
Ext.define('Foss.pricing.airPricePlan.AirPricePlanDetailWindow',{
	extend : 'Ext.window.Window',
	title: pricing.airPricePlan.i18n('foss.pricing.aBreakdownOfNew'),//明细信息新增
	closable : true,
	modal : true,
	resizable:false,
	closeAction : 'hide',
	parent:null,
	width :450,
	height :600,
	grid:null,																	//父 grid
	listeners:{
		beforehide:function(me){
			me.getPricePlanDetailForm().getForm().reset();
		},
		beforeshow:function(me){
		}
	},
    //明细信息新增-FORM
    pricePlanDetailForm:null,
    getPricePlanDetailForm:function(){
    	if(Ext.isEmpty(this.pricePlanDetailForm)){
    		this.pricePlanDetailForm = Ext.create('Foss.pricing.airPricePlan.AirPricePlanDetailForm');
    	}
    	return this.pricePlanDetailForm;
    },
    
    //想GRID中设置数据
    commitPricePlanDetail:function(grid){
    	var me = this;
    	//得到明细form
    	var form = me.getPricePlanDetailForm().getForm();
    	if(form.isValid()){
    		var pricePlanDetailDto = new Foss.pricing.airPricePlan.AirPricePlanDetailDto();
    		form.updateRecord(pricePlanDetailDto);
			//获取明细信息
	    	var goodsTypeCode = form.findField('goodsTypeId').getValue();
	    	var flightTypeCode = form.findField('flightSort').getValue();
	    	var arrvRegionCode = form.findField('arrvRegionCode').getValue();
	    	var arrvRegionName = form.findField('arrvRegionCode').arrvRegionName;
	    	var arrvRegionId = form.findField('arrvRegionCode').arrvRegionId;
	    	var remark = form.findField('remark').getValue();
	    	var combBillTypeCode = form.findField('combBillType').getValue();
	    	//设置明细信息
	    	pricePlanDetailDto.set('arrvRegionCode',arrvRegionCode);
	    	pricePlanDetailDto.set('arrvRegionId',arrvRegionId);
	    	pricePlanDetailDto.set('arrvRegionName',arrvRegionName);
	    	pricePlanDetailDto.set('remark',remark);
	    	pricePlanDetailDto.set('pricePlanId',pricing.pricePlanId);
	    	pricePlanDetailDto.set('flightTypeCode',flightTypeCode);
	    	pricePlanDetailDto.set('goodsTypeCode',goodsTypeCode);
	    	pricePlanDetailDto.set('combBillTypeCode',combBillTypeCode);
	    	
			//制定json请求参数
			var params = {'priceManageMentVo':{'pricePlanDetailDto':pricePlanDetailDto.data}};
			//ajax请求
			var url = pricing.realPath('addAirPricePlanDetail.action');
			
			//成功提示
			var successFun = function(json){
				pricing.showInfoMes(json.message);
				var arrayDate = json.priceManageMentVo.pricePlanDetailDtoList;
				if(!Ext.isEmpty(arrayDate)){
					grid.store.loadData(arrayDate);//显示第一页	
				}
		    };
		    //失败提示
		    var failureFun = function(json){
		    	if(Ext.isEmpty(json)){
					pricing.showErrorMes(pricing.airPricePlan.i18n('i18n.pricingRegion.requestTimeOut'));
				}else{
					pricing.showErrorMes(json.message);
				}
		    };
		    //调用ajax请求
			pricing.requestJsonAjax(url,params,successFun,failureFun);//发送AJAX请求
	    	me.close();
    	}
    },
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [me.getPricePlanDetailForm()];//设置window的元素
		me.fbar = [{
			text : pricing.airPricePlan.i18n('i18n.pricingRegion.cancel'),//取消
			handler :function(){
				me.close();
			} 
		},{
			text : pricing.airPricePlan.i18n('foss.pricing.reset'),//重置
			margin:'0 185 0 0',
			handler :function(){
				me.getPricePlanDetailForm().getForm().reset();
			} 
		},{
			text : "保存",//提交
			cls:'yellow_button',
			handler :function(){
				me.commitPricePlanDetail(config.grid);
			} 
		}];
		me.callParent([cfg]);
	}
});




/**
 * 修改价格明细信息Window
 */
Ext.define('Foss.pricing.airPricePlan.ModifyAirPricePlanDetailWindow',{
	extend : 'Ext.window.Window',
	title: '修改',//明细信息新增
	closable : true,
	modal : true,
	resizable:false,
	closeAction : 'hide',
	parent:null,
	width :450,
	height :450,
	pricePlanDetailDto:null,
	grid:null,																	//父 grid
	listeners:{
		beforehide:function(me){
			me.getPricePlanDetailForm().getForm().reset();
		},
		beforeshow:function(me){
			//赋值目的地区域相关信息
			me.getPricePlanDetailForm().getForm().loadRecord(new Foss.pricing.airPricePlan.AirPricePlanDetailDto(me.pricePlanDetailDto));
			me.getPricePlanDetailForm().getForm().findField('arrvRegionCode').setCombValue(me.pricePlanDetailDto.arrvRegionName,me.pricePlanDetailDto.arrvRegionName);
			me.getPricePlanDetailForm().getForm().findField('arrvRegionCode').arrvRegionId = me.pricePlanDetailDto.arrvRegionId;
			me.getPricePlanDetailForm().getForm().findField('arrvRegionCode').arrvRegionName = me.pricePlanDetailDto.arrvRegionName;
			
			//赋值航班类型相关信息
			me.getPricePlanDetailForm().getForm().findField('flightSort').setValue(me.pricePlanDetailDto.flightTypeName); 
			me.getPricePlanDetailForm().getForm().findField('flightSort').valueCode = me.pricePlanDetailDto.flightTypeCode;
			me.getPricePlanDetailForm().getForm().findField('flightSort').valueName = me.pricePlanDetailDto.flightTypeName;
			
			//赋值货物类型相关信息
			me.getPricePlanDetailForm().getForm().findField('goodsTypeId').goodsTypeCode = me.pricePlanDetailDto.goodsTypeCode;
			me.getPricePlanDetailForm().getForm().findField('goodsTypeId').setValue(me.pricePlanDetailDto.goodsTypeName)
			
			//赋值最低一票
			me.getPricePlanDetailForm().getForm().findField('minimumOneTicket').setValue(me.pricePlanDetailDto.minimumOneTicket);
			
			//合票类型
			me.getPricePlanDetailForm().getForm().findField('combBillType').setValue(me.pricePlanDetailDto.combBillTypeCode); 
			me.getPricePlanDetailForm().getForm().findField('combBillType').valueCode = me.pricePlanDetailDto.combBillTypeCode;
			me.getPricePlanDetailForm().getForm().findField('combBillType').valueName = me.pricePlanDetailDto.combBillTypeName;
		}
	},
    //明细信息新增-FORM
    pricePlanDetailForm:null,
    getPricePlanDetailForm:function(){
    	if(Ext.isEmpty(this.pricePlanDetailForm)){
    		this.pricePlanDetailForm = Ext.create('Foss.pricing.airPricePlan.AirPricePlanDetailForm');
    	}
    	return this.pricePlanDetailForm;
    },
     	
    updatePriceDetailPlan:function(grid){
    	var me = this;
    	//得到明细form
    	var form = me.getPricePlanDetailForm().getForm();
    	if(form.isValid()){
    		 	var pricePlanDetailDto = new Foss.pricing.airPricePlan.AirPricePlanDetailDto(me.pricePlanDetailDto);
    		form.updateRecord(pricePlanDetailDto);
			//获取明细信息
	    	var goodsTypeCode = form.findField('goodsTypeId').goodsTypeCode;
	    	var flightTypeCode = form.findField('flightSort').valueCode;
	    	var flightTypeName = form.findField('flightSort').valueName;
	    	var arrvRegionName = form.findField('arrvRegionCode').arrvRegionName;
	    	var arrvRegionId = form.findField('arrvRegionCode').arrvRegionId;
	    	var remark = form.findField('remark').getValue();
	    	var combBillTypeCode = form.findField('combBillType').getValue(); //zxy 20140504 MANA-1253 合票类型
	    	
	    	//设置明细信息
	    	pricePlanDetailDto.set('arrvRegionId',arrvRegionId);
	    	pricePlanDetailDto.set('arrvRegionName',arrvRegionName);
	    	pricePlanDetailDto.set('remark',remark);
	    	pricePlanDetailDto.set('pricePlanId',pricing.pricePlanId);
	    	pricePlanDetailDto.set('flightTypeCode',flightTypeCode);
	    	pricePlanDetailDto.set('goodsTypeCode',goodsTypeCode);
	    	pricePlanDetailDto.set('combBillTypeCode',combBillTypeCode);
	    	
			//制定json请求参数
			var params = {'priceManageMentVo':{'pricePlanDetailDto':pricePlanDetailDto.data}};
			//ajax请求
			var url = pricing.realPath('updateAirPriceDetailPlan.action');
			
			//成功提示
			var successFun = function(json){
				pricing.showInfoMes(json.message);
				var arrayDate = json.priceManageMentVo.pricePlanDetailDtoList;
				if(!Ext.isEmpty(arrayDate)){
					grid.store.loadData(arrayDate);//显示第一页	
				}
		    };
		    //失败提示
		    var failureFun = function(json){
		    	if(Ext.isEmpty(json)){
					pricing.showErrorMes(pricing.airPricePlan.i18n('i18n.pricingRegion.requestTimeOut'));
				}else{
					pricing.showErrorMes(json.message);
				}
		    };
		    //调用ajax请求
			pricing.requestJsonAjax(url,params,successFun,failureFun);//发送AJAX请求
	    	me.close();
    	}
    },
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [me.getPricePlanDetailForm()];//设置window的元素
		me.fbar = [{
			text : pricing.airPricePlan.i18n('i18n.pricingRegion.cancel'),//取消
			handler :function(){
				me.close();
			} 
		},{
			text : pricing.airPricePlan.i18n('foss.pricing.clean'),//清空
			margin:'0 185 0 0',
			handler :function(){
				me.getPricePlanDetailForm().getForm().reset();
			} 
		},{
			text : "修改",//提交
			cls:'yellow_button',
			handler :function(){
				me.updatePriceDetailPlan(config.grid);
			} 
		}];
		me.callParent([cfg]);
	}
});

/**
 * 航空运价费用方案批次信息录入form
 */
Ext.define('Foss.pricing.airPricePlan.AirPricePlanAddFormPanel',{
	extend : 'Ext.form.Panel',
	title: "出发地信息",
	parent:null,
	frame: true,
	operaterCode:null,
	pricePlanEntity: null,
	priceRegionId: null,
	layout:{
		  type: 'table',
	      columns: 2
	},
	height:251,
    defaults : {
    	columnWidth : 1,
    	margin : '3 10 5 10',
    	width:320,
		labelSeparator:'',
		labelWidth:80,
		xtype : 'textfield'
    },
	items: [
		{
			name: 'id',
			hidden : true
		},
		{
			name: 'name',
			allowBlank:false,
			maxLength : 150,
	        fieldLabel:pricing.airPricePlan.i18n('foss.pricing.scenarioName')//方案名称
		},{
			name: 'priceRegionCode',
			allowBlank:false,
	        fieldLabel:pricing.airPricePlan.i18n('foss.pricing.originatingArea'),//始发区域
	        xtype : 'commonpriceregionselector',
	        airPriceFlag:'Y',
	        priceRegionId: null, //定义始发区域ID
	        priceRegionName:null,//定义始发区域名称
	        listeners:{
	        	select:function(comb,records,objs){
	        		var record = records[0];
	        		var id = record.get('id');
	        		var name = record.get('regionName');
	        		comb.priceRegionId = id;
	        		comb.priceRegionName = name;
	        	}
        	}
		},{
			xtype:'datefield',
			allowBlank:false,
			fieldLabel:pricing.airPricePlan.i18n('foss.pricing.availabilityDate'),//生效日期
			format:'20y-m-d',
			name:'beginTime'
		},{
			name: 'description',
			colspan : 3,
	        fieldLabel:pricing.airPricePlan.i18n('foss.pricing.programDescription'),//方案描述
	        xtype : 'textareafield',
	        maxLength : 200
		},{
			name: 'arrvRegionCode',
	        fieldLabel:pricing.airPricePlan.i18n('foss.pricing.destinationAreaNew'),//目的区域
	        xtype : 'commonpriceregionselector',
	        airPriceFlag:'Y',
	        priceRegionId: null, //定义始发区域ID
	        priceRegionName:null,//定义始发区域名称
	        listeners:{
	        	select:function(comb,records,objs){
	        		var record = records[0];
	        		var id = record.get('id');
	        		var name = record.get('regionName');
	        		comb.priceRegionId = id;
	        		comb.priceRegionName = name;
	        	}
        	}
		}
	],
	
	/**修改价格方案**/
	commitUpdatePricePlan:function(){
    	var me = this;
    	var baseForm = me.getForm();
    	if(baseForm.isValid()){//校验form是否通过校验
    		var pricePlanEntity = me.up('window').pricePlanEntity;
    		var pricePlanModel = new Foss.pricing.airPricePlan.AirPricePlanModel(pricePlanEntity);
    		baseForm.updateRecord(pricePlanModel);//将FORM中数据设置到MODEL里面
    		
    		
    		//处理特殊字段
    		var priceRegionId = baseForm.findField('priceRegionCode').priceRegionId;
    		var priceRegionName = baseForm.findField('priceRegionCode').priceRegionName;
    		var priceRegionCode = baseForm.findField('priceRegionCode').value;
    		if(null != priceRegionId){
    			pricePlanModel.set('priceRegionId',priceRegionId);
    		
    		}
    		if(null != priceRegionName){
    			pricePlanModel.set('priceRegionName',priceRegionName);
    		}
    		if(null != priceRegionCode){
    			pricePlanModel.set('priceRegionCode',priceRegionCode);
    		}
    		var params = {'priceManageMentVo':{'pricePlanEntity':pricePlanModel.data}};//组织需要修改的数据
    		var successFun = function(json){
				pricing.showInfoMes(json.message);//提示新增成功
			};
			var failureFun = function(json){
				if(Ext.isEmpty(json)){
					pricing.showErrorMes(pricing.airPricePlan.i18n('foss.pricing.requestTimedOut'));//请求超时
				}else{
					pricing.showErrorMes(json.message);//提示失败原因
				}
			};
			var url = pricing.realPath('updateAirPricePlan.action');//请求价格方案修改
			pricing.requestJsonAjax(url,params,successFun,failureFun);//发送AJAX请求
    	}
	 },
	
	/***批次保存***/
	commitPricePlan:function(){
		var me = this;
    	//获取表单
    	var form = me.getForm();
    	var pricePlanModel = new Foss.pricing.airPricePlan.AirPricePlanModel();
    	if(form.isValid()){
    		form.updateRecord(pricePlanModel);
    		var priceRegionId = form.findField('priceRegionCode').priceRegionId;
    		var priceRegionName = form.findField('priceRegionCode').priceRegionName;
    		
    		//处理特殊字段
    		pricePlanModel.set('priceRegionId',priceRegionId);
    		pricePlanModel.set('priceRegionName',priceRegionName);
    		var params = {'priceManageMentVo':{'pricePlanEntity':pricePlanModel.data}};
    		var url = pricing.realPath('addAirPricePlan.action');
    		
    		//成功提示
    		var successFun = function(json){
    			pricing.showInfoMes(json.message);
    			//成功后获取价格方案主ID
				pricing.pricePlanId = json.priceManageMentVo.pricePlanEntity.id;  
				me.getPricePlanDetailGridPanel().store.add();
				//获取formPanel所有属性,全部设置为只读	    		
	    		var itemArrays = me.items.items;
	    		Ext.Array.each(itemArrays,function(value,index,arrays){
	    			itemArrays[index].setReadOnly(true);
	    		});
	    		me.getDockedItems()[1].items.items[1].setDisabled(true);//重置按钮不可用
				me.getDockedItems()[1].items.items[2].setDisabled(true);//保存按钮不可用
				var dockedItems = me.up('window').getPricePlanDetailGridPanel().getDockedItems();
				dockedItems[1].items.items[0].setDisabled(false);//新增明明细按钮可用
				dockedItems[1].items.items[2].setDisabled(false);//删除明细按钮可用
				dockedItems[1].items.items[4].setDisabled(false);//导入明细按钮可用
	    	};
    	    
    	    //失败提示
    	    var failureFun = function(json){
    	    	if(Ext.isEmpty(json)){
    				pricing.showErrorMes(pricing.airPricePlan.i18n('i18n.pricingRegion.requestTimeOut'));
    			}else{
    				pricing.showErrorMes(json.message);
    			}
    	    };
    		pricing.requestJsonAjax(url,params,successFun,failureFun);//发送AJAX请求
    	}
	},
	/**目的区域查询**/
	arrvRegionSearch:function(){
		var me = this;
    	var baseForm = me.getForm();
    	if(baseForm.isValid()){//校验form是否通过校验
    		pricePlanEntity = me.up('window').pricePlanEntity;
    		priceRegionId = baseForm.findField('arrvRegionCode').priceRegionId;
    		
    		var grid = me.up('window').getPricePlanDetailGridPanel();
    		grid.arrvRegionId = priceRegionId;
    		grid.pricePlanId = pricePlanEntity.id;
			grid.getStore().load();
    	}
	 },
	 
	//价格方案明细信息 （目的明细列表）
	pricePlanDetailGridPanel:null,
    getPricePlanDetailGridPanel: function(){
    	if(Ext.isEmpty(this.pricePlanDetailGridPanel)){
    			this.pricePlanDetailGridPanel = Ext.create('Foss.pricing.airPricePlan.AirPricePlanDetailGridPanel');
    			pricing.pricePlanDetailGridPanel  = this.pricePlanDetailGridPanel;
    	}
    	return this.pricePlanDetailGridPanel;
    },	
	//构造函数
	constructor : function(config) {
		var me = this, 
		cfg = Ext.apply({}, config);
		me.fbar = [{
			text : pricing.airPricePlan.i18n('foss.pricing.destinationAreaSearch'),//目的区域查询
			cls:'yellow_button',
			handler :function(){
				me.arrvRegionSearch();
			}  
		},{
			text :pricing.airPricePlan.i18n('i18n.pricingRegion.cancel'),//取消
			handler :function(){
				me.up('window').isUpdate = null;
				me.up().close();
			}
		},{
			text : pricing.airPricePlan.i18n('foss.pricing.reset'),//重置
			handler :function(){
					me.getForm().reset();//表格重置
			} 
		},{
			text : pricing.airPricePlan.i18n('foss.pricing.save'),//保存
			handler :function(){
				var isUpdate = me.up('window').isUpdate;
				if(isUpdate){
					me.commitUpdatePricePlan();	
				}else{
					me.commitPricePlan();	
				}
			} 
		}];
		me.callParent([cfg]);
	}
});

/**
 * 航空运价费用方案明细目的地grid
 */
Ext.define('Foss.pricing.airPricePlan.AirPricePlanDetailGridPanel',{
	extend: 'Ext.grid.Panel',
	title : "目的地信息",
	frame: true,
	sortableColumns:false,
    enableColumnHide:false,
	selType : "rowmodel", 
	enableColumnMove:false,
	stripeRows:true, 
	border: true,
	height :440,
	pricePlanId: null,
	arrvRegionId: null,
	columns: [{
	    xtype: 'rownumberer',
		width:40,
		text : "序号"//序号
		
	},{
		text : pricing.airPricePlan.i18n('i18n.pricingRegion.opra'),
		align : 'center',
		xtype : 'actioncolumn',
		items: [{
				iconCls:'deppon_icons_edit',
				tooltip: pricing.airPricePlan.i18n('foss.pricing.toAmendTheProposal'), 
				width:42,
				handler: function(grid, rowIndex, colIndex){
					var me = this;
                	var record = grid.up().getStore().getAt(rowIndex);//选中数据
                	
                	var pricePlanDetaiModel = new Foss.pricing.airPricePlan.AirPricePlanDetailDto();
                	//处理特殊字段
					pricePlanDetaiModel.set('pricePlanId',record.get('pricePlanId'));
					pricePlanDetaiModel.set('valuationId',record.get('valuationId'));
					var params = {'priceManageMentVo':{'queryPricePlanDetailBean':pricePlanDetaiModel.data}};
    				var successFun = function(json){
    					//获取明细window
    					var updateWindow =  grid.up().getModifyPriceDetailWindow();
    					//获取根据价格方案ID和计费规则ID所查询出来的计价规则以及费率信息包括重轻货
    					var arrayDate = json.priceManageMentVo.pricePlanDetailDtoList;
    					//如果数据非空才赋值给明细FormPanel作为显示数据,否则提示没有找到对应的数据
						if(!Ext.isEmpty(arrayDate)){
							var pricePlanDetailDto = arrayDate[0];
							updateWindow.pricePlanDetailDto = pricePlanDetailDto;
						} 
						updateWindow.show();
    				};
    				var failureFun = function(json){
    					if(Ext.isEmpty(json)){
    						pricing.showErrorMes(pricing.airPricePlan.i18n('foss.pricing.requestTimedOut'));//请求超时
    					}else{
    						pricing.showErrorMes(json.message);
    					}
    				};
    				var url = pricing.realPath('queryBeforeModifyAirPricePlanDetail.action');
    				pricing.requestJsonAjax(url,params,successFun,failureFun);
				}
		}]
	},{
		text: "目的区域",//目的区域
		width: 120,
        dataIndex: 'arrvRegionName'
	},{
		text: "航班类型",//航班
		width: 70,
        dataIndex: 'flightTypeName'
	},{
		text :pricing.airPricePlan.i18n('foss.pricing.combBillType'),//合票类型
		dataIndex : 'combBillTypeName',
		flex:2
	},{
		text: "货物类型",//货物类型
		width: 70,
        dataIndex: 'goodsTypeName'
	},{
		text: "重货价格",//重货价格
    	width: 60,
        dataIndex: 'heavyPrice'
	},{
		text: "最低一票",//最低一票
		width: 65,
        dataIndex: 'minimumOneTicket'
	},{
		width: 80,
		text: "是否接货",
        dataIndex: 'centralizePickup',
        renderer:function(value){
			if(value=='Y'){
				return "是";
			}else if(value=='N'){
				return  "否";
			}else{
				return '';
			}
		}
	},{
		text: "备注",//备注
		width: 80,
        dataIndex: 'remark'
	}],
	
	//返回chekbox
	checkboxModel:null,
	getCheckboxModel:function(){
		if(Ext.isEmpty(this.checkboxModel)){
			this.checkboxModel = Ext.create('Ext.selection.CheckboxModel');	
		}
		return this.checkboxModel;
	},
	
    //返回分页toolbbar
	pagingToolbar : null,
	getPagingToolbar : function() {
		if (Ext.isEmpty(this.pagingToolbar)) {
			this.pagingToolbar = Ext.create('Deppon.StandardPaging', {
				store : this.store,
				pageSize : 10
			});
		}
		return this.pagingToolbar;
	},
    
	//弹出明细列表信息
    pricePlanDetailWindow:null,
    getPricePlanDetailWindow:function(){
    	var me = this;
    	if(Ext.isEmpty(this.pricePlanDetailWindow)){
    		this.pricePlanDetailWindow = Ext.create('Foss.pricing.airPricePlan.AirPricePlanDetailWindow',{
    			grid:me
    		});
	    	this.pricePlanDetailWindow.parent = this;
    	}
    	return this.pricePlanDetailWindow;
    },
    //修改明细信息window
    modifyPricePlanDetailWindow:null,
    getModifyPriceDetailWindow:function(){
    	if(Ext.isEmpty(this.modifyPricePlanDetailWindow)){
    		this.modifyPricePlanDetailWindow = Ext.create('Foss.pricing.airPricePlan.ModifyAirPricePlanDetailWindow',{
    			grid:this
    		});
    		this.modifyPricePlanDetailWindow.parent = this;
    	}
    	return this.modifyPricePlanDetailWindow;
    },
    
    //删除价格方案明细信息
    deletePricePlanDetail: function(grid){
    	var me = this;
		var selections = me.getSelectionModel().getSelection();//获取选中的数据
		if(selections.length<1){//判断是否至少选中了一条
			pricing.showWoringMessage(pricing.airPricePlan.i18n('foss.pricing.pleaseSelectOneDeleteOperation'));//请选择一条进行删除操作！
			return;//没有则提示并返回
		}
		for(var i = 0;i<selections.length;i++){
			if(selections[i].get('active')==pricing.yes){
				pricing.showWoringMessage(pricing.airPricePlan.i18n('foss.pricing.pleaseChooseToNotActivateTheDataToBeBeleted'));//请选择未激活数据进行删除！
				return;//没有则提示并返回
			}
		}
		pricing.showQuestionMes(pricing.airPricePlan.i18n('foss.pricing.youWantDeletePricProgramDetails'),function(e){//是否要删除这些价格方案明细？
		if(e=='yes'){//询问是否删除，是则发送请求
				var valuationIds = new Array();//计费规则ID
				for(var i = 0 ; i<selections.length ; i++){
					valuationIds.push(selections[i].get('valuationId'));
				}
				var params = {'priceManageMentVo':{'pricePlanDetailIds':valuationIds}};
				var successFun = function(json){
					pricing.showInfoMes(json.message);
					var arrayDate = json.priceManageMentVo.pricePlanDetailDtoList;
							grid.store.loadData(arrayDate);//显示第一页	
					};
				var failureFun = function(json){
					if(Ext.isEmpty(json)){
						pricing.showErrorMes(pricing.airPricePlan.i18n('foss.pricing.requestTimedOut'));//请求超时
					}else{
						pricing.showErrorMes(json.message);
					}
				};
				var url = pricing.realPath('deleteAirPricePlanDetail.action');
				pricing.requestJsonAjax(url,params,successFun,failureFun);
			}
		})	
    },
	//初始化构造器
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
			me.store = Ext.create('Foss.pricing.airPricePlan.AirPricePlanDeatilStore',{
			autoLoad : false,
			pageSize : 10,
			listeners: {
				beforeload: function(store, operation, eOpts){
					var pricePlanId = pricing.pricePlanId;
					if(pricePlanId!=null){
						Ext.apply(operation,{
							params : {
								'priceManageMentVo.queryPricePlanDetailBean.pricePlanId' : pricePlanId//价格方案ID
							}
						});	
					}
				}
		    }
		});
		me.selModel = me.getCheckboxModel();
		me.store = Ext.create('Foss.pricing.airPricePlan.AirPricePlanDeatilStore',{
			autoLoad : false,
			pageSize : 10,
			listeners: {
				beforeload: function(store, operation, eOpts){
					var queryForm =me.up('window').getPricePlanAddFormPanel();
					if(queryForm!=null){
						Ext.apply(operation,{
							params : {
								'priceManageMentVo.queryPricePlanDetailBean.arrvRegionId' : me.arrvRegionId,//目的区域编码
								'priceManageMentVo.queryPricePlanDetailBean.pricePlanId' : me.pricePlanId//价格方案ID
							}
						});	
					}
				}
		    }
		});
		//加入tbar菜单
		me.tbar = [{
	            text: pricing.airPricePlan.i18n('i18n.pricingRegion.add'),
	            handler:function(){ 
	            	me.getPricePlanDetailWindow().show();	 
	            }
	        },'-',{
	            text: pricing.airPricePlan.i18n('foss.pricing.delete'),
	            handler:function(){
	            	me.deletePricePlanDetail(me); 
	            }
	        }];
	    //设置滚动条不失效
		me.listeners = {
	    	scrollershow: function(scroller) {
	    		if (scroller && scroller.scrollEl) {
	    				scroller.clearManagedListeners(); 
	    				scroller.mon(scroller.scrollEl, 'scroll', scroller.onElScroll, scroller); 
	    		}
	    	}
	    },
	    me.bbar = me.getPagingToolbar();
	    pricing.pagingBar = me.bbar;
		me.callParent([cfg]);
	}
});

/**
 * 航空运价费用方案弹出框
 */
Ext.define('Foss.pricing.airPricePlan.AirPricePlanAddWindow',{
		extend: 'Ext.window.Window',
		title: "新增价格方案",
		x:400,
		y:50,
		width:750,
		height:770,
		modal:true,
		isUpdate:null,
		parent:null,
		closeAction:'hide',
	    //监听器
	    listeners:{
			beforehide:function(me){
				//页面清空
				me.getPricePlanAddFormPanel().getForm().reset();
				//属性设置只读属性为false
				me.getPricePlanAddFormPanel().getForm().getFields().each(function(item){
					item.setReadOnly(false);
				});
				//设置价格方案form操作按钮可用
				me.getPricePlanAddFormPanel().getDockedItems()[1].items.items[1].setDisabled(false);//重置按钮可用
				me.getPricePlanAddFormPanel().getDockedItems()[1].items.items[2].setDisabled(false);//保存按钮可用
				//移除store中的数据
				me.getPricePlanDetailGridPanel().getStore().removeAll();
			},
			//窗口显示之前事件
			beforeshow:function(me){
				//新增价格时，价格明细操作按钮设置禁用
				var wid =me.getPricePlanDetailGridPanel();
				wid.getDockedItems('toolbar[dock="top"]')[0].items.items[0].setDisabled(true);
				wid.getDockedItems('toolbar[dock="top"]')[0].items.items[2].setDisabled(true);
			}
		},
	    //价格方案批次信息 （出发地批次信息录入）
		pricePlanAddFormPanel:null,
	    getPricePlanAddFormPanel : function(){
	    	var me = this;
	    	if(Ext.isEmpty(this.pricePlanAddFormPanel)){
	    		this.pricePlanAddFormPanel = Ext.create('Foss.pricing.airPricePlan.AirPricePlanAddFormPanel');
	    	} 
	    	return this.pricePlanAddFormPanel;
	    },
	     //价格方案明细信息 （目的明细列表）
		pricePlanDetailGridPanel:null,
	    getPricePlanDetailGridPanel: function(){
	    	if(Ext.isEmpty(this.pricePlanDetailGridPanel)){
	    		this.pricePlanDetailGridPanel = Ext.create('Foss.pricing.airPricePlan.AirPricePlanDetailGridPanel');
	    	}
	    	return this.pricePlanDetailGridPanel;
	    },
	    
	    //构造器
		constructor: function(config){
			var me = this,
			cfg = Ext.apply({}, config);
			me.isUpdate,
			me.items = [me.getPricePlanAddFormPanel(),me.getPricePlanDetailGridPanel()],
			
			me.callParent([cfg]);
		}	
});


/**
 * 航空运价费用方案弹出修改框
 */
Ext.define('Foss.pricing.airPricePlan.AirPricePlanUpdateWindow',{
		extend: 'Ext.window.Window',
		title: "修改价格方案",
		width:750,
		height:770,
		modal:true,
		isUpdate:null,
		parent:null,
		pricePlanEntity:null,
 		pricePlanDetailDtoList:null,
		closeAction:'hide',
	    //监听器
	    listeners:{
			beforehide:function(me){
				me.getPricePlanAddFormPanel().getForm().reset();
				me.isUpdate = null;
			},
			beforeshow:function(me){
				me.getPricePlanAddFormPanel().getForm().loadRecord(new Foss.pricing.airPricePlan.AirPricePlanModel(me.pricePlanEntity));
				me.getPricePlanAddFormPanel().getForm().findField('priceRegionCode').setCombValue(me.pricePlanEntity.priceRegionName,me.pricePlanEntity.priceRegionCode);
				me.getPricePlanDetailGridPanel().store.removeAll(true);
			}
		},
	    //价格方案批次信息 （出发地批次信息录入）
		pricePlanAddFormPanel:null,
	    getPricePlanAddFormPanel : function(){
	    	var me = this;
	    	if(Ext.isEmpty(me.pricePlanAddFormPanel)){
		    		me.pricePlanAddFormPanel = Ext.create('Foss.pricing.airPricePlan.AirPricePlanAddFormPanel');
		    		//设置器父元素
 	    	} 
	    	return this.pricePlanAddFormPanel;
	    },
	     //价格方案明细信息 （目的明细列表）
		pricePlanDetailGridPanel:null,
	    getPricePlanDetailGridPanel: function(){
	    	if(Ext.isEmpty(this.pricePlanDetailGridPanel)){
	    			this.pricePlanDetailGridPanel = Ext.create('Foss.pricing.airPricePlan.AirPricePlanDetailGridPanel');
	    			pricing.pricePlanDetailGridPanel  = this.pricePlanDetailGridPanel;
	    	}
	    	return this.pricePlanDetailGridPanel;
	    },
	    
	    //构造器
		constructor: function(config){
			var me = this,
			cfg = Ext.apply({}, config);
			var formPanel = me.getPricePlanAddFormPanel();
			var gridPanel = me.getPricePlanDetailGridPanel()
			me.items = [formPanel,gridPanel],
			me.callParent([cfg]);
		}	
});


/**
 * 航空运价费用方案中止方案弹出框
 */
Ext.define('Foss.pricing.airPricePlan.AirPricePlanStopEndTimeWindow',{
		extend: 'Ext.window.Window',
		title: "中止价格方案",
		width:380,
		height:120,
		pricePlanId:null,
		
	    //中止
		pricePlanStopFormPanel:null,
		getPricePlanStopFormPanel : function(pricePlanId){
	    	if(Ext.isEmpty(this.pricePlanAddFormPanel)){
	    		this.pricePlanStopFormPanel = Ext.create('Foss.pricing.airPricePlan.AirPricePlanStopFormPanel',{
	    			'pricePlanId':pricePlanId
	    		});
	    	}
	    	return this.pricePlanStopFormPanel;
	    },
	    
	   	initComponent : function() {
			var me = this;
			me.items = [me.getPricePlanStopFormPanel(me.pricePlanId)];//设置window的元素
			me.callParent();
		}
});

/**
 * 中止功能window
 */
Ext.define('Foss.pricing.airPricePlan.AirPricePlanStopFormPanel',{
	extend : 'Ext.form.Panel',
	layout:'column',
	width:375,
	height:100,
	pricePlanId:null,
	
	//中止方案
	stopPricePlan:function(pricePlanId){
		var me = this;
    	//获取表单
    	var form = me.getForm();
    	if(form.isValid()){
			var pricePlanModel = new Foss.pricing.airPricePlan.AirPricePlanModel();
			form.updateRecord(pricePlanModel);
    		pricePlanModel.set('id',pricePlanId);
    		var params = {'priceManageMentVo':{'pricePlanEntity':pricePlanModel.data}};
    		
    		//ajax请求
    		var url = pricing.realPath('stopAirPricePlan.action');
    		
    		//成功提示
    		var successFun = function(json){
    			pricing.showInfoMes(json.message);
    			me.up('window').close();
    			//成功后查询列表
				pricing.pagingBar.moveFirst();   			
    	    };
    	    
    	    //失败提示
    	    var failureFun = function(json){
    	    	if(Ext.isEmpty(json)){
    				pricing.showErrorMes(pricing.airPricePlan.i18n('i18n.pricingRegion.requestTimeOut'));
    			}else{
    				pricing.showErrorMes(json.message);
    			}
    	    };
    		
    	    //调用ajax请求
    		pricing.requestJsonAjax(url,params,successFun,failureFun);//发送AJAX请求
    	}
	},
	
	initComponent : function() {
		var me = this;
		me.items = [
			{
			xtype:'datefield',
			fieldLabel:pricing.airPricePlan.i18n('foss.pricing.deadline'),//截止日期
			format:'20y-m-d',
			name:'endTime',
			allowBlank:false
			},{
				xtype : 'container',
				margin : '0 0 2 0',
				items : [{
					xtype : 'button', 
					width:70,
					text : "中止",
					handler : function() {
						var pricePlanId = me.pricePlanId;
						me.stopPricePlan(pricePlanId);
					}
				}]
			}
		];//设置window的元素
		me.callParent();
	}
});













/**
 * 立即中止价格方案 Window
 */
Ext.define('Foss.pricing.airPricePlan.AirPricePlanImmediatelyStopEndTimeWindow',{
		extend: 'Ext.window.Window',
		title: pricing.airPricePlan.i18n('foss.pricing.immediatelySupendPricePriceScheme'),//"立即中止方案",
		width:380,
		height:152,
		pricePlanEntity:null,
		closeAction: 'hide' ,
		pricePlanStopFormPanel:null,
		getAirPricePlanStopFormPanel : function(pricePlanEntity){
	    	if(Ext.isEmpty(this.pricePlanAddFormPanel)){
	    		this.pricePlanStopFormPanel = Ext.create('Foss.pricing.airPricePlan.AirPricePlanImmediatelyStopFormPanel',{
	    			pricePlanEntity:pricePlanEntity
	    		});
	    	}
	    	return this.pricePlanStopFormPanel;
	    },
	   	constructor : function(config) {
			var me = this, cfg = Ext.apply({}, config);
			me.pricePlanEntity = config.pricePlanEntity;
			me.items = [me.getAirPricePlanStopFormPanel(me.pricePlanEntity)];
			me.callParent(cfg);
		}
});

/**
 * 立即中止功能FormPanel
 */
Ext.define('Foss.pricing.airPricePlan.AirPricePlanImmediatelyStopFormPanel',{
	extend : 'Ext.form.Panel',
	layout:'column',
	pricePlanEntity:null,
	stopPricePlan:function(pricePlanId){
		var me = this;
    	var form = me.getForm();
    	if(form.isValid()){
			var pricePlanModel = new Foss.pricing.airPricePlan.AirPricePlanModel();
			form.updateRecord(pricePlanModel);
			pricePlanModel.set('endTime',Ext.Date.parse(form.findField('endTime').getValue(), 'Y-m-d H:i:s'));
    		pricePlanModel.set('id',pricePlanId);
    		pricePlanModel.set('isPromptly',true);
    		var params = {'priceManageMentVo':{'pricePlanEntity':pricePlanModel.data}};
    		var url = pricing.realPath('stopAirPricePlan.action');
    		var successFun = function(json){
    			pricing.showInfoMes(json.message);
    			me.up('window').hide();
				pricing.pagingBar.moveFirst();   			
    	    };
    	    var failureFun = function(json){
    	    	if(Ext.isEmpty(json)){
    				pricing.showErrorMes(pricing.airPricePlan.i18n('i18n.pricingRegion.requestTimeOut'));
    			}else{
    				pricing.showErrorMes(json.message);
    			}
    	    };
    		pricing.requestJsonAjax(url,params,successFun,failureFun);//发送AJAX请求
    	}
	},
	constructor: function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.pricePlanEntity = config.pricePlanEntity;
		var showbeginTime = Ext.Date.format(new Date(me.pricePlanEntity.beginTime), 'Y-m-d');
		var showendTime = 	Ext.Date.format(new Date(me.pricePlanEntity.endTime), 'Y-m-d');
		me.items = [{
				width:280,
				xtype: 'displayfield',
				columnWidth:.9,
				value:pricing.airPricePlan.i18n('foss.pricing.showleftTimeInfo')
					  +showbeginTime+ pricing.airPricePlan.i18n('foss.pricing.showmiddleTimeInfo')
					  +showendTime  + pricing.airPricePlan.i18n('foss.pricing.showstopRightEndTimeInfo')
				//'<p style="color:red">原方案生效日期为【2013-02-31】截止日期为【2013-09-11】,您是否立即中止该方案?</p>'
			},{ 
				fieldLabel :pricing.airPricePlan.i18n('foss.pricing.suspendTime') ,//'中止日期',
				name : 'endTime',
				xtype : 'datetimefield_date97',
				editable:false,
				time : true,
				id : 'Foss_airPricePlan_stopEndTime_ID',
				allowBlank:false,
				dateConfig: {
					el : 'Foss_airPricePlan_stopEndTime_ID-inputEl'
				},
				columnWidth:.9
	    	},{
				xtype: 'container',
				columnWidth:.6,
				html: '&nbsp;'
			},{
				xtype : 'button', 
				width:70,
				columnWidth:.15,
				text : pricing.airPricePlan.i18n('i18n.pricingRegion.determine'),//"确认",
				handler : function() {
					var pricePlanId = me.up('window').pricePlanEntity.id;
					me.stopPricePlan(pricePlanId);
				}
			},{
				xtype : 'button', 
				width:70,
				columnWidth:.15,
				text : pricing.airPricePlan.i18n('i18n.pricingRegion.cancel'),//"取消",
				handler : function() {
					me.up('window').hide();
				}
			}];
	        me.callParent(cfg);
    }
});


/**
 * 立即激活价格方案Window
 */
Ext.define('Foss.pricing.airPricePlan.AirPricePlanImmediatelyActiveTimeWindow',{
		extend: 'Ext.window.Window',
		title: pricing.airPricePlan.i18n('foss.pricing.immediatelyActiveationPriceScheme'),//"立即激活方案",
		width:380,
		height:152,
		pricePlanEntity:null,
		closeAction: 'hide' ,
		airPricePlanImmediatelyActiveFormPanel:null,
		getAirPricePlanImmediatelyActiveFormPanel : function(){
	    	if(Ext.isEmpty(this.airPricePlanImmediatelyActiveFormPanel)){
//	    		this.airPricePlanImmediatelyActiveFormPanel = Ext.create('Foss.pricing.airPricePlan.AirPricePlanImmediatelyActiveFormPanel',{
//	    			pricePlanEntity:pricePlanEntity
//	    		});
	    		this.airPricePlanImmediatelyActiveFormPanel = Ext.create('Foss.pricing.airPricePlan.AirPricePlanImmediatelyActiveFormPanel');
	    	}
	    	return this.airPricePlanImmediatelyActiveFormPanel;
	    },
	    listeners:{
	    	beforeshow:function(me){
	    		var showbeginTime = Ext.Date.format(new Date(me.pricePlanEntity.beginTime), 'Y-m-d');
	    		var showendTime = 	Ext.Date.format(new Date(me.pricePlanEntity.endTime), 'Y-m-d');
	    		var value = pricing.airPricePlan.i18n('foss.pricing.showleftTimeInfo')
				  +showbeginTime+pricing.airPricePlan.i18n('foss.pricing.showmiddleTimeInfo')
				  +showendTime+pricing.airPricePlan.i18n('foss.pricing.showrightEndTimeInfo');
	    		me.getAirPricePlanImmediatelyActiveFormPanel().down('displayfield').setValue(value);
	    	},
	    	beforehide:function(me){
	    		me.getAirPricePlanImmediatelyActiveFormPanel().getForm().reset();
	    	}
	    },
	   	constructor : function(config) {
			var me = this, cfg = Ext.apply({}, config);
//			me.pricePlanEntity = config.pricePlanEntity;
//			me.items = [me.getAirPricePlanImmediatelyActiveFormPanel(me.pricePlanEntity)];
			me.items = [me.getAirPricePlanImmediatelyActiveFormPanel()];
			me.callParent(cfg);
		}
});


/**
 * 立即激活功能Form
 */
Ext.define('Foss.pricing.airPricePlan.AirPricePlanImmediatelyActiveFormPanel',{
	extend : 'Ext.form.Panel',
	layout:'column',
	pricePlanEntity:null,
	activetionPricePlan:function(){
		var me = this;
    	var form = me.getForm();
    	if(form.isValid()){
			var pricePlanModel = new Foss.pricing.airPricePlan.AirPricePlanModel();
			form.updateRecord(pricePlanModel);
			var pricePlanId = me.up('window').pricePlanEntity.id;
			var priceRegionId = me.up('window').pricePlanEntity.priceRegionId;
			pricePlanModel.set('beginTime',Ext.Date.parse(form.findField('beginTime').getValue(), 'Y-m-d H:i:s'));
    		pricePlanModel.set('id',pricePlanId);
    		pricePlanModel.set('priceRegionId',priceRegionId);
    		var params = {'priceManageMentVo':{'pricePlanEntity':pricePlanModel.data}};
    		var url = pricing.realPath('immediatelyActiveAirPricePlan.action');
    		var successFun = function(json){
    			pricing.showInfoMes(json.message);
    			me.up('window').hide();
				pricing.pagingBar.moveFirst();   			
    	    };
    	    var failureFun = function(json){
    	    	if(Ext.isEmpty(json)){
    				pricing.showErrorMes(pricing.airPricePlan.i18n('i18n.pricingRegion.requestTimeOut'));
    			}else{
    				pricing.showErrorMes(json.message);
    			}
    	    };
    		pricing.requestJsonAjax(url,params,successFun,failureFun);//发送AJAX请求
    	}
	},
	constructor: function(config) {
		var me = this, cfg = Ext.apply({}, config);
//		me.pricePlanEntity  = config.pricePlanEntity;
//		var showbeginTime = Ext.Date.format(new Date(me.up('window').pricePlanEntity.beginTime), 'Y-m-d');
//		var showendTime = 	Ext.Date.format(new Date(me.up('window').pricePlanEntity.endTime), 'Y-m-d');
		me.items = [{
				
				width:280,
				xtype: 'displayfield',
				columnWidth:.9,
				value:''
			
//				width:280,
//				xtype: 'displayfield',
//				columnWidth:.9,
//				value:pricing.airPricePlan.i18n('foss.pricing.showleftTimeInfo')
//					  +showbeginTime+pricing.airPricePlan.i18n('foss.pricing.showmiddleTimeInfo')
//					  +showendTime+pricing.airPricePlan.i18n('foss.pricing.showrightEndTimeInfo')
				//'<p style="color:red">原方案生效日期为【'+showbeginTime+'】截止日期为【'+showendTime+'】,您是否立即生效该方案?</p>'
			},{
				fieldLabel:pricing.airPricePlan.i18n('foss.pricing.availabilityDate'),//'生效日期',
				name : 'beginTime',
				xtype : 'datetimefield_date97',
				editable:false,
				time : true,
				id : 'Foss_airPricePlan_activetionEndTime_ID',
				allowBlank:false,
				dateConfig: {
					el : 'Foss_airPricePlan_activetionEndTime_ID-inputEl'
				},
				columnWidth:.9
			},{
				xtype: 'container',
				columnWidth:.6,
				html: '&nbsp;'
			},{
				xtype : 'button', 
				width:70,
				columnWidth:.15,
				text : pricing.airPricePlan.i18n('i18n.pricingRegion.determine'),//,"确认",
				handler : function() {
					me.activetionPricePlan();  
				}
			},{
				xtype : 'button', 
				width:70,
				columnWidth:.15,
				text : pricing.airPricePlan.i18n('i18n.pricingRegion.cancel'),//"取消",
				handler : function() {
					me.up('window').hide();
				}
			}];
        this.callParent(cfg);
    }
});









/**
 * 上传附件弹出框
 */
Ext.define('Foss.pricing.airPricePlan.UploadAirPriceform',{
	extend:'Ext.window.Window',
	title:pricing.airPricePlan.i18n('foss.pricing.importPriceScheme'),
	layout:{
		type:'vbox',
		align:'stretch'
	},
	width:400,
	height:150,
	closeAction:'hide',
	listeners:{
		beforehide:function(me){
			var form = me.down('form');
			form.getForm().findField('uploadFile').reset();
		}
	},
	parent:null,//（Foss.pricing.pricePlan.pricePlanformGrid）
	constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [
		{
			xtype:'form',
			flex:1,
			layout:{
				type : 'hbox'
			},
			defaults : {
				margins : '0 5 0 0'
			},
			items:[{
				xtype:'filefield',
				name:'uploadFile',
				fieldLabel:pricing.airPricePlan.i18n('foss.pricing.pleaseSelectAttachments'),
				labelWidth:100,
				buttonText:pricing.airPricePlan.i18n('foss.pricing.browse'),
				flex:3
			}]
		}];
		me.fbar = me.getFbar();
		me.callParent([cfg]);
	},
	getFbar:function(){
		var me = this;
		return [{
			text:pricing.airPricePlan.i18n('foss.pricing.import'),
			xtype:'button',
			scope:me,
			handler:me.uploadFile
		},{
			text:pricing.airPricePlan.i18n('i18n.pricingRegion.cancel'),
			xtype:'button',
			handler:function(){
				me.close();
			}
		}];
	},
	//文件上传
    uploadFile:function(){
		var me = this;
		var successFn = function(json){
			if(Ext.isEmpty(json.priceManageMentVo.numList)){
				pricing.showInfoMes(pricing.airPricePlan.i18n('foss.pricing.allDataImportSuccess'));//全部数据导入成功！
				me.close();
			}else{
				var message = pricing.airPricePlan.i18n('foss.pricing.first');//第
				for(var i = 0;i<json.platformVo.numList;i++){
					message = message+json.platformVo.numList[i]+','
				}
				message = message+pricing.airPricePlan.i18n('foss.pricing.lineImportSuccess');
				pricing.showWoringMessage(message);
			}
		};
		var failureFn = function(json){
			if(Ext.isEmpty(json)){
				pricing.showErrorMes(pricing.airPricePlan.i18n('i18n.pricingRegion.requestTimeOut'));//pricing.airPricePlan.i18n('i18n.pricingRegion.requestTimeOut')
			}else{
				pricing.showErrorMes(json.message);
			}
		};
		var form = me.down('form').getForm();
		var url = pricing.realPath('importAirPrice.action');
		form.submit({
            url: url,
            waitMsg: pricing.airPricePlan.i18n('foss.pricing.uploadYourAttachment'),
            success:function(form, action){
    			var result = action.result;
    			if(result.success){
    				successFn(result);
    			}else{
    				failureFn(result);
    			}
    		},
    		failure:function(form, action){
    			var result = action.result;
    			failureFn(result);
    		},
    		exception : function(form, action) {
				var result = action.result;
				failureFn(result);
			}
        });
	}
});

/**
 * 开始加载界面
 */
Ext.onReady(function(){
	Ext.QuickTips.init();
	pricing.searchGoodTypeList();
	var queryform = Ext.create('Foss.pricing.airPricePlan.AirPricePlanFormPanel');
	var gridPanel =	Ext.create('Foss.pricing.airPricePlan.AirPricePlanGridPanel');
	pricing.queryform = queryform;
	pricing.gridPanel = gridPanel;
	Ext.getCmp('T_pricing-airPricePlan').add(Ext.create('Ext.panel.Panel', {
	 	id:'T_pricing-airPricePlan_content',
		cls:"panelContentNToolbar",
		bodyCls:'panelContent-body',
		
		//获得查询FORM
		getQueryAirPricePlanForm : function() {
			return queryform;
		},
		//获得查询结果GRID
		getAirPricePlanGridPanel : function() {
			if(Ext.isEmpty(this.gridPanel)){
				this.gridPanel = Ext.create('Foss.pricing.airPricePlan.AirPricePlanGridPanel');//查询结果GRID
			}
			return gridPanel;
		},
		
		items : [queryform,gridPanel]
	}));
});

