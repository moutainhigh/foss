///////////////////////////////////////////////////首续重价格方案/////////////////////////////////////////////////////////
// 公共字段--激活标志
pricing.ecGoodsPricePlanYes = 'Y';

// 公共字段--价格方案Id
pricing.ecGoodsPricePlanId = null;

// 公共方法--long类型转换为日期类型
pricing.changeLongToDate = function(value) {
	if (value != null) {
		var date = new Date(value);
		return date;
	} else {
		return null;
	}
};

// 公共方法--创建STORE
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

// 公共方法--Ajax请求-用于一般方法
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
			Ext.Msg.alert(pricing.ecGoodsPricePlanIndex.i18n('foss.pricing.promptMessage'),result.message);
		}
	});
};

// 公共方法--Ajax请求-用于立即激活、立即中止【设置较长的响应时间】
pricing.requestJsonAjaxMax = function(url,params,successFn,failFn)
{
	Ext.Ajax.request({
		url:url,
		jsonData:params,
		timeout:14400000,
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
			Ext.Msg.alert(pricing.ecGoodsPricePlanIndex.i18n('foss.pricing.promptMessage'),result.message);
		}
	});
};

//二级产品条目数组
pricing.queryEcProductItemEntityList = [];

// 精准电商-全部
pricing.EC_PRODUCTCODE='RECISION_EC_PRODUCTS'
	
// 货物类型-通用
pricing.EC_GOODS_TYPE_ALL = 'H00001'

// 查询二级产品条目-查询中的使用
pricing.searchEcProductItemEntityList = function(){
	Ext.Ajax.request({
		url:pricing.realPath('queryProductItemLevel2.action'),
		async:false,
		success:function(response){
			var result = Ext.decode(response.responseText);
			pricing.queryEcProductItemEntityList = result.productItemVo.productItemEntityList;
			var countries = pricing.queryEcProductItemEntityList;
			var arrays = new Array(); 
			var nullproduct = {
				name:pricing.ecGoodsPricePlanIndex.i18n('i18n.pricingRegion.all'),//全部
				id:"",
				code:""						
			};
			pricing.queryEcProductItemEntityList.push(nullproduct);
			Ext.Array.each(countries, function(countrie, index, countriesItSelf) {
				if(countrie.productCode == pricing.EC_PRODUCTCODE && countrie.goodstypeCode == pricing.EC_GOODS_TYPE_ALL){
					arrays.push(countrie);
				}
			});
			pricing.queryEcProductItemEntityList = arrays;
		},
		failure:function(response){
			var result = Ext.decode(response.responseText);
			if(Ext.isEmpty(result)){
				pricing.showErrorMes(pricing.ecGoodsPricePlanIndex.i18n('i18n.pricingRegion.requestTimeOut'));
			}else{
				pricing.showErrorMes(result.message);
			}
		}
	});
};

// 价格方案规则明细数据模型
Ext.define('Foss.pricing.ecGoodsPricePlan.PricePlanDetailDtoModel', {
    extend: 'Ext.data.Model',
    fields : [{
    	name : 'valuationId', //计费规则ID
        type : 'string'
    },{
        name : 'arrvRegionId', //目的区域ID
        type : 'string'
    },{
    	name : 'arrvRegionCode', //目的区域CODE
        type : 'string'
    },{
        name : 'arrvRegionName',//目的区域NAME
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
        name : 'caculateType',//计费类别
        type : 'string'
    },{
        name : 'leftRange'//下限 
    },{
        name : 'rightRange'//上限
    },{
        name : 'fixedCosts'//固定费用
    },{
        name : 'prices'//费率    
    },{
     	name : 'centralizePickup'//是否包含接货
    },{
     	name : 'centralizeDelivery'//是否包含送货
    },{
        name : 'remark',//备注
        type : 'string'
    },{
        name : 'pricePlanId',//方案ID
        type : 'string'
    }]
});

// 价格方案数据模型
Ext.define('Foss.pricing.ecGoodsPricePlan.PricePlanModel', {
    extend: 'Ext.data.Model',
    fields : [{
        name : 'id',//方案ID
        type : 'string'
    },{
        name : 'modifyDate',//修改时间
        type : 'date',
        defaultValue : null,
        convert : pricing.changeLongToDate
    },{
        name : 'modifyUser',//修改人
        type : 'string'
    },{
        name : 'modifyUserName',//修改人姓名
        type : 'string'
    },{
        name : 'priceRegionId',//出发区ID
        type : 'string'
    },{
    	name : 'priceRegionName',//出发区名称
        type : 'string'
    },{
        name : 'priceRegionCode',//出发区编码
        type : 'string'
    },{
        name : 'name',//方案名称
        type : 'string'
    },{
        name : 'beginTime',//生效日期
        type : 'date',
        defaultValue : null,
        convert : pricing.changeLongToDate
    },{
        name : 'endTime',//截止日期
        type : 'date',
        defaultValue : null,
        convert : pricing.changeLongToDate
    },{
        name : 'active',//激活状态
        type : 'string'
    },{
        name : 'description',//描述信息
        type : 'string'
    },{
        name : 'descNote',//描述信息
        type : 'string'
    },{
        name : 'versionInfo',//版本信息
        type : 'string'
    },{
        name : 'refId',//原方案ID
        type : 'string'
    },{
        name : 'currencyCode',//货币代号
        type : 'string'
    },{
    	name : 'currentUsedVersion',//是否当前版本
    	type : 'string'
    },{
    	name:'showTime',
    	type : 'string'
    },{
    	name:'isPromptly'
    }]
});

// 价格方案数据
Ext.define('Foss.pricing.ecGoodsPricePlan.PricePlanStore',{
	extend: 'Ext.data.Store',
	model: 'Foss.pricing.ecGoodsPricePlan.PricePlanModel',
	pageSize:20,
	proxy: {
		type : 'ajax',
		actionMethods:'POST',
		url: pricing.realPath('queryEcGoodsPricePlanBatchInfo.action'),
		reader : {
			type : 'json',
			root : 'ecGoodspriceManageMentVo.pricePlanEntityList',
			totalProperty : 'totalCount',
			successProperty: 'success'
		}
	},
	listeners: {
		beforeload: function(store, operation, eOpts){
			var n = pricing.ecGoodsQueryform.getValues();
			Ext.apply(operation,{
				params : {
					'ecGoodspriceManageMentVo.pricePlanEntity.active' : n.active,
					'ecGoodspriceManageMentVo.pricePlanEntity.name' : n.name,
					'ecGoodspriceManageMentVo.pricePlanEntity.priceRegionCode' : n.priceRegionCode,
					'ecGoodspriceManageMentVo.pricePlanEntity.beginTime' : n.beginTime,
					'ecGoodspriceManageMentVo.pricePlanEntity.endTime' : n.endTime
				}
			});			
		}
	}
});

// 价格方案明细规则数据
Ext.define('Foss.pricing.ecGoodsPricePlan.PricePlanDeatilStore',{
	extend: 'Ext.data.Store',
	model: 'Foss.pricing.ecGoodsPricePlan.PricePlanDetailDtoModel',
	pageSize:10,
	proxy: {
		type : 'ajax',
		actionMethods:'POST',
		url: pricing.realPath('queryEcGoodsPricePlanDetailInfo.action'),
		reader : {
			type : 'json',
			root : 'ecGoodspriceManageMentVo.pricePlanDetailDtoList',
			totalProperty : 'totalCount',
			successProperty: 'success'
		}
	}
});

// 查询条件面板--导出弹窗
Ext.define('Foss.pricing.ecGoodsPricePlan.PricePlanFormPanel', {
	extend : 'Ext.form.Panel',
	title: pricing.ecGoodsPricePlanIndex.i18n('i18n.pricingRegion.searchCondition'),
	frame: true,
	collapsible: true,
    defaults : {
    	margin : '8 10 5 10',
    	labelWidth:80
    },
   	layout: 'auto',
	defaultType : 'textfield',
	layout:'column',
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.items = [{
        xtype : 'textfield',
		fieldLabel:pricing.ecGoodsPricePlanIndex.i18n('foss.pricing.scenarioName'),//方案名称
		name: 'name',
        maxLength : 60,
        maxLengthText:pricing.ecGoodsPricePlanIndex.i18n('foss.pricing.theProgramNameLengthExceedsTheMaximumLimit'),//方案名称长度已超过最大限制!
	    allowBlank:true,
	    columnWidth:.3
	},{
		xtype: 'container',
		columnWidth:.2,
		html: '&nbsp;'
	},{
		name: 'priceRegionCode',
	    fieldLabel:pricing.ecGoodsPricePlanIndex.i18n('foss.pricing.originatingArea'),//始发区域
	    xtype:'commonpriceregionecgoodsselector',
	    columnWidth:.3
	},{
		xtype:'datetimefield_date97',
		fieldLabel:pricing.ecGoodsPricePlanIndex.i18n('foss.pricing.availabilityDate1'),//开始时间
		name:'beginTime',
		editable:false,
		time : true,
		id : 'Foss_ecGoodsPricePlan_activetionSearchStartTime_ID',
		dateConfig: {
			el : 'Foss_ecGoodsPricePlan_activetionSearchStartTime_ID-inputEl'
		},
		columnWidth:.3
	},{
		xtype: 'container',
		columnWidth:.2,
		html: '&nbsp;'
	},{
		xtype:'datetimefield_date97',
		fieldLabel:pricing.ecGoodsPricePlanIndex.i18n('foss.pricing.deadline1'),//结束时间
		name:'endTime',
		editable:false,
		time : true,
		id : 'Foss_ecGoodsPricePlan_activetionSearchEndTime_ID',
		dateConfig: {
			el : 'Foss_ecGoodsPricePlan_activetionSearchEndTime_ID-inputEl'
		},
		columnWidth:.3
	},{
		columnWidth : .3,
		name: 'active',
		queryMode: 'local',
	    displayField: 'value',
	    value:'ALL',
	    valueField: 'key',
	    editable:false,
	    store:pricing.getStore('Foss.pricing.region.AreaStatusStore',null,['key','value']
	    ,[{'key':'N','value':pricing.ecGoodsPricePlanIndex.i18n('i18n.pricingRegion.unActive')},
	      {'key':'Y','value':pricing.ecGoodsPricePlanIndex.i18n('i18n.pricingRegion.active')}
	    ,{'key':'ALL','value':pricing.ecGoodsPricePlanIndex.i18n('i18n.pricingRegion.all')}]),
	    fieldLabel: pricing.ecGoodsPricePlanIndex.i18n('foss.pricing.status'),//状态
	    xtype : 'combo'
	}];
	me.fbar = [{
			xtype : 'button', 
			width:70,
			left : 1,
			text : pricing.ecGoodsPricePlanIndex.i18n('foss.pricing.reset'),//重置
			margin:'0 635 0 0',
			handler : function() {
				me.getForm().reset();
			}
		},{
			xtype : 'container',
			margin : '0 0 0 0',
			items : [{
				xtype : 'button', 
				width:70,
				cls:'yellow_button',
				text : pricing.ecGoodsPricePlanIndex.i18n('i18n.pricingRegion.search'),//【查询】按钮
				disabled: !pricing.ecGoodsPricePlanIndex.isPermission('ecGoodsPricingPlan/pricePlanQueryButton'),
				hidden: !pricing.ecGoodsPricePlanIndex.isPermission('ecGoodsPricingPlan/pricePlanQueryButton'),
				handler : function() {
					var beginTime = me.getForm().findField('beginTime').getValue();
					var endTime = me.getForm().findField('endTime').getValue();
					if(!Ext.isEmpty(beginTime) && !Ext.isEmpty(endTime)){
						if(beginTime>endTime){
							// 输入的结束时间要大于开始时间！
							pricing.showWoringMessage(pricing.ecGoodsPricePlanIndex.i18n('foss.pricing.deadlineForInputGreaterThanEfectiveDate'));
			    			return;
						}
					}
					var grid = Ext.getCmp('T_pricing-ecGoodsPricePlanIndex_content').getPricePlanGridPanel();
					grid.getPagingToolbar().moveFirst();
				}
			}]
			},{
				xtype: 'displayfield',
				columnWidth : .15,
				value:pricing.ecGoodsPricePlanIndex.i18n('foss.pricing.specialP1')//可按照>=开始时间<=结束时间查詢
		}];
		me.callParent([cfg]);
	}
});

// 查询结果面板
Ext.define('Foss.pricing.ecGoodsPricePlan.PricePlanGridPanel',{
	extend: 'Ext.grid.Panel',
	title : pricing.ecGoodsPricePlanIndex.i18n('i18n.pricingRegion.searchResults'),//查询结果
	emptyText: pricing.ecGoodsPricePlanIndex.i18n('foss.pricing.theQueryResultIsEmpty'),//查询结果为空
	frame: true,
    enableColumnHide:false,
	selType : "rowmodel", 
	enableColumnMove:false,
	stripeRows:true, 
	border: true,
	defaults: {
		width: 150
	},
	//修改价格方案弹窗
	updatePricePlanWindow : null,
	getUpdatePricePlanWindow : function(){
		if(Ext.isEmpty(this.updateStandardWindow)){
			this.updatePricePlanWindow = Ext.create('Foss.pricing.ecGoodsPricePlan.PricePlanUpdateWindow');
			this.updatePricePlanWindow.parent = this;//设置父元素
		}
		return this.updatePricePlanWindow;
	},
	//中止价格方案弹窗
	stopPricePlanWindow:null,
	getStopPricePlanWindow: function(pricePlanId){
		if(Ext.isEmpty(this.stopPricePlanWindow)){
			this.stopPricePlanWindow = Ext.create('Foss.pricing.ecGoodsPricePlan.PricePlanStopEndTimeWindow');
			this.stopPricePlanWindow.parent = this;//设置父元素
		}
		this.stopPricePlanWindow.pricePlanId = pricePlanId;
		return this.stopPricePlanWindow;
	},
    //计划激活弹窗
    immediatelyActivePricePlanWindow:null,
	getimmediatelyActivePricePlanWindow: function(idList){
		if(Ext.isEmpty(this.immediatelyActivePricePlanWindow)){
			this.immediatelyActivePricePlanWindow = Ext.create('Foss.pricing.ecGoodsPricePlan.PricePlanImmediatelyActiveTimeWindow');
			this.immediatelyActivePricePlanWindow.parent = this;//设置父元素
		}
		this.immediatelyActivePricePlanWindow.idList = idList;
		return this.immediatelyActivePricePlanWindow;
	},
	// 计划激活功能
    immediatelyActivePricePlan:function(){
    	var me = this;
	 	var selections = me.getSelectionModel().getSelection();
	 	if(selections.length < 1){
	 		pricing.showWoringMessage('请选择一条记录进行操作！');
			return;
	 	}
		for(var i=0;i<selections.length;i++){
	 		if(selections[i].get('active')==pricing.ecGoodsPricePlanYes){
		 		pricing.showWoringMessage('请选择未激活数据进行操作！');
		 		return;
		 	}
	 	}
		var idList = new Array();
		for(var i = 0 ; i<selections.length ; i++){
			idList.push(selections[i].get('id'));
		}
 		var window = me.getimmediatelyActivePricePlanWindow(idList);
 		window.show();
	},
    //计划中止弹窗
    immediatelyStopPricePlanWindow:null,
	getImmediatelyStopPricePlanWindow: function(idList){
		if(Ext.isEmpty(this.immediatelyStopPricePlanWindow)){
			this.immediatelyStopPricePlanWindow = Ext.create('Foss.pricing.ecGoodsPricePlan.PricePlanImmediatelyStopEndTimeWindow');
			this.immediatelyStopPricePlanWindow.parent = this;//设置父元素
		}
		this.immediatelyStopPricePlanWindow.idList = idList;
		return this.immediatelyStopPricePlanWindow;
	},
	// 计划中止功能
    immediatelyStopPricePlan:function(){
    	var me = this;
	 	var selections = me.getSelectionModel().getSelection();
	 	if(selections.length < 1){
	 		pricing.showWoringMessage('请选择一条记录进行操作！');
			return;
	 	}
	 	for(var i=0;i<selections.length;i++){
	 		if(selections[i].get('active')!=pricing.ecGoodsPricePlanYes){
		 		pricing.showWoringMessage('请选择已激活数据进行操作！');
		 		return;
		 	}
	 	}
	 	var idList = new Array();
	 	for(var i=0;i<selections.length;i++){
	 		idList.push(selections[i].get('id'));
	 	}
 		var window = me.getImmediatelyStopPricePlanWindow(idList);
 		window.show();
	},
	//查询结果面板--方案数据信息
	pricePlanStore:null,
	getPricePlanStore: function(){
		if(Ext.isEmpty(this.pricePlanStore)){
			this.pricePlanStore = Ext.create('Foss.pricing.ecGoodsPricePlan.PricePlanStore');
		}
		return this.pricePlanStore;
	},
	//查询结果面板--复选框
	checkboxModel:null,
	getCheckboxModel:function(){
		if(Ext.isEmpty(this.checkboxModel)){
			this.checkboxModel = Ext.create('Ext.selection.CheckboxModel');	
		}
		return this.checkboxModel;
	},
	//查询结果面板--分页工具栏
	pagingToolbar : null,
	getPagingToolbar : function() {
		if (Ext.isEmpty(this.pagingToolbar)) {
			this.pagingToolbar = Ext.create('Deppon.StandardPaging', {
				store : this.store,
				plugins: 'pagesizeplugin',
				pageSize : 20
			});
		}
		return this.pagingToolbar;
	},
	//价格方案明细查询功能
	pricePlanDetailShowWindow:null,
	getPricePlanDetailShowWindow:function(){
		if(Ext.isEmpty(this.pricePlanDetailShowWindow)){
			this.pricePlanDetailShowWindow = Ext.create('Ext.pricing.ecGoodsPricePlan.PricePlanDetailShowWindow');	
			this.pricePlanDetailShowWindow.parent = this;
		}
		return this.pricePlanDetailShowWindow;
	},
	//删除方案功能
	deletePricePlan:function(){
	 	var me = this;
	 	var selections = me.getSelectionModel().getSelection();
	 	if(selections.length < 1){
	 		//请选择一条进行删除操作！
	 		pricing.showWoringMessage(pricing.ecGoodsPricePlanIndex.i18n('foss.pricing.pleaseSelectOneDeleteOperation'));
			return;
	 	}
	 	for(var i = 0;i<selections.length;i++){
			if(selections[i].get('active')==pricing.ecGoodsPricePlanYes){
				//请选择未激活数据进行删除！
				pricing.showWoringMessage(pricing.ecGoodsPricePlanIndex.i18n('foss.pricing.pleaseChooseToNotActivateTheDataToBeBeleted'));
				return;
			}
		}
		//是否要删除这些首续重价格方案？
		pricing.showQuestionMes(pricing.ecGoodsPricePlanIndex.i18n('foss.pricing.theseTheEcGoodsPriceProgramYouWantToRemove'),function(e){
			if(e=='yes'){
				var idList = new Array();
				for(var i = 0 ; i<selections.length ; i++){
					idList.push(selections[i].get('id'));
				}
				var params = {'ecGoodspriceManageMentVo':{'pricePlanIds':idList}};
				var successFun = function(json){
					pricing.showInfoMes(json.message);
					me.getPagingToolbar().moveFirst();
				};
				var failureFun = function(json){
					if(Ext.isEmpty(json)){
						pricing.showErrorMes(pricing.ecGoodsPricePlanIndex.i18n('foss.pricing.requestTimedOut'));
					}else{
						pricing.showErrorMes(json.message);
					}
				};
				var url = pricing.realPath('deleteEcGoodsPricePlan.action');
				pricing.requestJsonAjax(url,params,successFun,failureFun);
			}
		});
	},
    //导入功能
    uploadPriceform : null,
    getUploadPriceform: function(){
    	if(Ext.isEmpty(this.uploadPriceform)){
			this.uploadPriceform = Ext.create('Foss.pricing.ecGoodsPricePlan.UploadPriceform');	
		}
		return this.uploadPriceform;
    },
    //导出功能
    queryPricePlanForm: null,
    getQueryPricePlanForm:function(){
		if(Ext.isEmpty(this.queryPricePlanForm)){
			this.queryPricePlanForm  = Ext.create('Foss.pricing.ecGoodsPricePlan.PricePlanFormPanel')
		}
		return this.queryPricePlanForm;
    },
	constructor: function(config){
		var me = this,cfg = Ext.apply({}, config);
		me.store = me.getPricePlanStore();//加载方案数据
		me.selModel = me.getCheckboxModel();//加载复选框
		me.bbar = me.getPagingToolbar();//加载分页工具栏
		me.columns = [{xtype: 'rownumberer',
			width:60,
			text : pricing.ecGoodsPricePlanIndex.i18n('i18n.pricingRegion.num')//序号 
		},{
			text : pricing.ecGoodsPricePlanIndex.i18n('i18n.pricingRegion.opra'),//操作
			align : 'center',
			xtype : 'actioncolumn',
			items: [{
				iconCls: 'deppon_icons_showdetail',
                tooltip: pricing.ecGoodsPricePlanIndex.i18n('foss.pricing.details'), //【查看详情】按钮
                disabled: !pricing.ecGoodsPricePlanIndex.isPermission('ecGoodsPricingPlan/pricePlanQueryDetailButton'),
				width:42,
                handler: function(grid, rowIndex, colIndex) {
    				var record=grid.getStore().getAt(rowIndex);
                	me.getPricePlanDetailShowWindow().show();
                	me.getPricePlanDetailShowWindow().pricePlanId = record.get('id');	
                }
			},{
				iconCls:'deppon_icons_edit',
				tooltip: pricing.ecGoodsPricePlanIndex.i18n('foss.pricing.toAmendTheProposal'), //【修改方案】按钮
				disabled: !pricing.ecGoodsPricePlanIndex.isPermission('ecGoodsPricingPlan/pricePlanUpdateButton'),
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
                	var record = grid.up().getStore().getAt(rowIndex);
                	var updateWindow =  grid.up().getUpdatePricePlanWindow();
                	var params = {'ecGoodspriceManageMentVo':{'pricePlanId':record.get('id')}};
    				var successFun = function(json){
						updateWindow.pricePlanEntity = json.ecGoodspriceManageMentVo.pricePlanEntity;
						updateWindow.pricePlanDetailDtoList = json.ecGoodspriceManageMentVo.pricePlanDetailDtoList;
    					pricing.ecGoodsPricePlanId = json.ecGoodspriceManageMentVo.pricePlanEntity.id;
    					updateWindow.isUpdate = true;
    					updateWindow.show();
    					pricing.pagingBar.moveFirst();
    				};
    				var failureFun = function(json){
    					if(Ext.isEmpty(json)){
    						pricing.showErrorMes(pricing.ecGoodsPricePlanIndex.i18n('foss.pricing.requestTimedOut'));//请求超时
    					}else{
    						pricing.showErrorMes(json.message);
    					}
    				};
    				//修改之前根据id查出方案和详情
    				var url = pricing.realPath('queryEcGoodsPricePlanAndDetailInfoById.action');
    				pricing.requestJsonAjax(url,params,successFun,failureFun);
				}
			},{
				iconCls:'deppon_icons_end',
				tooltip: pricing.ecGoodsPricePlanIndex.i18n('foss.pricing.stop'), //【中止】按钮
				disabled: !pricing.ecGoodsPricePlanIndex.isPermission('ecGoodsPricingPlan/pricePlanStopButton'),
				width:42,
				handler: function(grid, rowIndex, colIndex){
					var record = grid.getStore().getAt(rowIndex);
					var pricePlanId = record.get('id');
					me.getStopPricePlanWindow(pricePlanId).show();
				}
			}]
		},{
			width: 140,
			text: pricing.ecGoodsPricePlanIndex.i18n('foss.pricing.scenarioName'), //方案名称
	        dataIndex: 'name'
		},{
			width: 140,
			text: pricing.ecGoodsPricePlanIndex.i18n('foss.pricing.theOriginatingRegion'),//始发区域
	        dataIndex: 'priceRegionName'
		},{
			text: pricing.ecGoodsPricePlanIndex.i18n('foss.pricing.updateTime'),//修改时间
			width: 140,
	        dataIndex: 'modifyDate',
	        renderer:function(value){
				return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
			}
		},{
			text: pricing.ecGoodsPricePlanIndex.i18n('foss.pricing.modifyUser'),//修改人
			width: 140,
	        dataIndex: 'modifyUserName'
		},{
			text: pricing.ecGoodsPricePlanIndex.i18n('foss.pricing.availabilityDate'),//生效日期
	        width: 80,
	        dataIndex: 'beginTime',
	        renderer:function(value){
				return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
			}
		},{
			text: pricing.ecGoodsPricePlanIndex.i18n('foss.pricing.endTimeTwo'),//截止日期
	        width: 80,
	        dataIndex: 'endTime',
	        renderer:function(value){
				return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
			}
		},{
			width: 80,
			text: pricing.ecGoodsPricePlanIndex.i18n('foss.pricing.dataType'),//数据状态
	        sortable: true,
	        dataIndex: 'active',
	        renderer:function(value){
				if(value=='Y'){//'Y'表示激活
					return pricing.ecGoodsPricePlanIndex.i18n('i18n.pricingRegion.active')//已激活
				}else if(value=='N'){//'N'表示未激活
					return  pricing.ecGoodsPricePlanIndex.i18n('i18n.pricingRegion.unActive')//未激活
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
			text : pricing.ecGoodsPricePlanIndex.i18n('foss.pricing.deleteProgram'),//【删除方案】按钮
			disabled: !pricing.ecGoodsPricePlanIndex.isPermission('ecGoodsPricingPlan/pricePlanDeleteButton'),
			hidden: !pricing.ecGoodsPricePlanIndex.isPermission('ecGoodsPricingPlan/pricePlanDeleteButton'),
			handler :function(){
				me.deletePricePlan();
			} 
		},'-', {
			text : pricing.ecGoodsPricePlanIndex.i18n('foss.pricing.planActivationProgram'),//【计划激活】按钮
			disabled:!pricing.ecGoodsPricePlanIndex.isPermission('ecGoodsPricingPlan/pricePlanImmediatelyActiveButton'),
			hidden:!pricing.ecGoodsPricePlanIndex.isPermission('ecGoodsPricingPlan/pricePlanImmediatelyActiveButton'),
			handler :function(){
				me.immediatelyActivePricePlan();
			} 
		},'-', {
			text : pricing.ecGoodsPricePlanIndex.i18n('foss.pricing.planStopProgram'),//【计划中止】按钮
			disabled:!pricing.ecGoodsPricePlanIndex.isPermission('ecGoodsPricingPlan/pricePlanImmediatelyStopButton'),
			hidden:!pricing.ecGoodsPricePlanIndex.isPermission('ecGoodsPricingPlan/pricePlanImmediatelyStopButton'),
			handler :function(){
				me.immediatelyStopPricePlan();
			} 
		},'-',{
			text : pricing.ecGoodsPricePlanIndex.i18n('foss.pricing.export'),//【导出】按钮
			disabled:!pricing.ecGoodsPricePlanIndex.isPermission('ecGoodsPricingPlan/pricePlanExportButton'),
			hidden:!pricing.ecGoodsPricePlanIndex.isPermission('ecGoodsPricingPlan/pricePlanExportButton'),
			plugins: {
				ptype: 'buttondisabledplugin',
				seconds: 8
			},
			handler :function(){
				var queryForm = pricing.ecGoodsQueryform;
				var pricePlanExport = '';
				var name = queryForm.getForm().findField('name').getValue(); // 方案名称
				var deptRegionCode = queryForm.getForm().findField('priceRegionCode').getValue(); //始发区域编码
				var active = queryForm.getForm().findField('active').getValue(); //是否有效
				var beginTime =Ext.Date.format(queryForm.getForm().findField('beginTime').getValue(),'Y-m-d');//开始时间
				var endTime = Ext.Date.format(queryForm.getForm().findField('endTime').getValue(),'Y-m-d');//结束时间
				pricePlanExport ='ecGoodspriceManageMentVo.pricePlanEntity.active='+active;
				if(!Ext.isEmpty(name)){
					pricePlanExport = pricePlanExport+'&'+'ecGoodspriceManageMentVo.pricePlanEntity.name='+name;
				}
				if(!Ext.isEmpty(beginTime)){
					pricePlanExport = pricePlanExport+'&'+'ecGoodspriceManageMentVo.pricePlanEntity.beginTime='+beginTime;
				}
				if(!Ext.isEmpty(endTime)){
					pricePlanExport = pricePlanExport+'&'+'ecGoodspriceManageMentVo.pricePlanEntity.endTime='+endTime;
				}
				if(!Ext.isEmpty(deptRegionCode)){
					if(!Ext.isEmpty(pricePlanExport)){
						pricePlanExport = pricePlanExport+'&'+'ecGoodspriceManageMentVo.pricePlanEntity.priceRegionCode='+deptRegionCode;
					}else{
						pricePlanExport = 'ecGoodspriceManageMentVo.pricePlanEntity.priceRegionCode='+deptRegionCode;
					}
				}
				var url = pricing.realPath('exportEcGoodsPricePlan.action');
				if(!Ext.isEmpty(pricePlanExport)){
					url = url+'?'+pricePlanExport;
				}
				window.location=encodeURI(encodeURI(url))
				pricePlanExport = '';
			} 
		},'-', {
			text : pricing.ecGoodsPricePlanIndex.i18n('foss.pricing.import'),//【导入】按钮
			disabled:!pricing.ecGoodsPricePlanIndex.isPermission('ecGoodsPricingPlan/pricePlanImportButton'),
			hidden:!pricing.ecGoodsPricePlanIndex.isPermission('ecGoodsPricingPlan/pricePlanImportButton'),
			handler :function(){
				me.getUploadPriceform().show();
			} 
		}];
		pricing.pagingBar = me.bbar;
		me.callParent([cfg]);
	}
});

// 查询结果面板--查看单条明细信息弹窗================================================================================================
Ext.define('Ext.pricing.ecGoodsPricePlan.PricePlanDetailShowWindow',{
	extend : 'Ext.window.Window',
	title: pricing.ecGoodsPricePlanIndex.i18n('foss.pricing.thePriceOfTheProgramDetailQuery'),//价格方案明细查询
	closable : true,
	modal : true,
	resizable:false,
	closeAction : 'hide',
	parent:null,
	pricePlanId:null,
	width :700,
	height :650,
	listeners:{
		beforehide:function(me){
			me.getQueryPricePlanDetailForm().getForm().reset();
			me.getPricePlanDetailShowGridPanel().getStore().removeAll();
			me.pricePlanId = null;
		},
		beforeshow:function(me){
			me.getPricePlanDetailShowGridPanel().getStore().load();
		}
	},
    //查询单条明细信息--查询条件
	queryPricePlanDetailForm:null,
    getQueryPricePlanDetailForm:function(){
    	if(Ext.isEmpty(this.queryPricePlanDetailForm)){
    		this.queryPricePlanDetailForm = Ext.create('Foss.pricing.ecGoodsPricePlan.QueryPricePlanDetailForm');
    	}
    	return this.queryPricePlanDetailForm;
    },
    //查询单条明细信息--查询结果
    pricePlanDetailShowGridPanel:null,
    getPricePlanDetailShowGridPanel:function(){
    	if(Ext.isEmpty(this.pricePlanDetailShowGridPanel)){
    		this.pricePlanDetailShowGridPanel = Ext.create('Foss.pricing.ecGoodsPricePlan.PricePlanDetailShowGridPanel');
    	}
    	return this.pricePlanDetailShowGridPanel;
    },
    constructor : function(config) {
		var me = this,cfg = Ext.apply({}, config);
		me.items = [me.getQueryPricePlanDetailForm(),me.getPricePlanDetailShowGridPanel()];
		me.callParent([cfg]);
	}
});
//查询单条明细信息--查询条件
Ext.define('Foss.pricing.ecGoodsPricePlan.QueryPricePlanDetailForm', {
	extend : 'Ext.form.Panel',
	title: pricing.ecGoodsPricePlanIndex.i18n('i18n.pricingRegion.searchCondition'),//查询条件
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
			name: 'productItemCode',
			queryMode: 'local',
		    displayField: 'name',
		    valueField: 'code',
		    editable:false,
		    store:pricing.getStore(null,null,['id','code','name'],pricing.queryEcProductItemEntityList),
	        fieldLabel:pricing.ecGoodsPricePlanIndex.i18n('foss.pricing.productEntry'),//产品条目
	        xtype : 'combo'
		},{
			name: 'arrvRegionId',
			valueField: 'id',
	        fieldLabel:pricing.ecGoodsPricePlanIndex.i18n('foss.pricing.destinationArea'),//目的区域
	        priceRegionFlag:'ARRIVE_REGION',
	        xtype : 'commonallpriceregionEcGoodsselector'//调公共选择器
		},{
			xtype : 'container',
			columnWidth : .2,
			margin : '0 0 0 0',
			items : [{
				xtype : 'button', 
				width:70,
				text : pricing.ecGoodsPricePlanIndex.i18n('i18n.pricingRegion.search'),//【查询】按钮
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
//查询单条明细信息--查询结果
Ext.define('Foss.pricing.ecGoodsPricePlan.PricePlanDetailShowGridPanel', {
	extend: 'Ext.grid.Panel',
	title :pricing.ecGoodsPricePlanIndex.i18n('i18n.pricingRegion.searchResults'),//查询结果
	frame: true,
	height :450,
    sortableColumns:false,
    enableColumnHide:false,
    enableColumnMove:false,
	stripeRows : true, // 交替行效果【灰/白】
	selType : "rowmodel", // 选择类型设置为：行选择
	emptyText: pricing.ecGoodsPricePlanIndex.i18n('foss.pricing.theQueryResultIsEmpty'),//查询结果为空
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
			width:60,
			text : pricing.ecGoodsPricePlanIndex.i18n('i18n.pricingRegion.num')//序号
		},{
			text :pricing.ecGoodsPricePlanIndex.i18n('foss.pricing.destinationArea'),//目的区域
			dataIndex : 'arrvRegionName',
			flex:2
		},{
			text :pricing.ecGoodsPricePlanIndex.i18n('foss.pricing.productEntry'),//产品条目
			dataIndex : 'productItemName',
			flex:2
		},{
			text: pricing.ecGoodsPricePlanIndex.i18n('foss.pricing.caculateEcGoodsType'),//计费类型
			width: 55,
	        dataIndex: 'caculateType',
	        renderer:function(value){
				if(value=='VOLUME'){
					return pricing.ecGoodsPricePlanIndex.i18n('foss.pricing.volume')//体积
				}else if(value=='WEIGHT'){
					return pricing.ecGoodsPricePlanIndex.i18n('foss.pricing.weight')//重量
				}else{
					return '';
				}
			}
		},{
			text: pricing.ecGoodsPricePlanIndex.i18n('foss.pricing.leftRange'),//下限
	    	width: 60,
	        dataIndex: 'leftRange'
		},{
			text: pricing.ecGoodsPricePlanIndex.i18n('foss.pricing.rightRange'),//上限      
	    	width: 60,
	        dataIndex: 'rightRange'
		},{
			text: pricing.ecGoodsPricePlanIndex.i18n('foss.pricing.rates'),//费率
			width: 40,
	        dataIndex: 'prices'
		},{
			text: pricing.ecGoodsPricePlanIndex.i18n('foss.pricing.fixedCosts'),//固定费用
			width: 55,
	        dataIndex: 'fixedCosts'
		},{
			width: 100,
			text: "是否包含接货",
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
			width: 100,
			text: "是否包含送货",
	        dataIndex: 'centralizeDelivery',
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
			text : pricing.ecGoodsPricePlanIndex.i18n('foss.pricing.remark'),//备注
			dataIndex : 'remark',
			flex:2
		}];
		me.store = Ext.create('Foss.pricing.ecGoodsPricePlan.PricePlanDeatilStore',{
			autoLoad : false,
			pageSize : 10,
			listeners: {
				beforeload: function(store, operation, eOpts){
					var queryForm =me.up('window').getQueryPricePlanDetailForm();
					var pricePlanId = me.up('window').pricePlanId;
					if(queryForm!=null){
						Ext.apply(operation,{
							params : {
								'ecGoodspriceManageMentVo.queryPricePlanDetailBean.productItemCode' : queryForm.getForm().findField('productItemCode').getValue(),//产品条目编码
								'ecGoodspriceManageMentVo.queryPricePlanDetailBean.arrvRegionId' : queryForm.getForm().findField('arrvRegionId').getValue(),//目的区域编码
								'ecGoodspriceManageMentVo.queryPricePlanDetailBean.pricePlanId' : pricePlanId//价格方案ID
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
		me.selModel = Ext.create('Ext.selection.CheckboxModel',{
					mode:'MULTI',
					checkOnly:true
				});
		me.tbar = [
		{
			text : pricing.ecGoodsPricePlanIndex.i18n('foss.pricing.export'),//【导出】按钮
			handler :function(){
				var queryForm = me.up('window').getQueryPricePlanDetailForm();
				var pricePlanId = me.up('window').pricePlanId;
				var productItemCode = queryForm.getForm().findField('productItemCode').getValue();
				var arrvRegionId = queryForm.getForm().findField('arrvRegionId').getValue();
				var pricePlanExport = '';
				if(!Ext.isEmpty(pricePlanId)){
					pricePlanExport ='ecGoodspriceManageMentVo.queryPricePlanDetailBean.pricePlanId='+pricePlanId;
				}
				if(!Ext.isEmpty(arrvRegionId)){
					if(!Ext.isEmpty(pricePlanExport)){
						pricePlanExport = pricePlanExport+'&'+'ecGoodspriceManageMentVo.queryPricePlanDetailBean.arrvRegionId='+arrvRegionId;
					}else{
						pricePlanExport = 'ecGoodspriceManageMentVo.queryPricePlanDetailBean.arrvRegionId='+arrvRegionId;
					}
				}
				if(!Ext.isEmpty(productItemCode)){
					if(!Ext.isEmpty(productItemCode)){
						pricePlanExport = pricePlanExport+'&'+'ecGoodspriceManageMentVo.queryPricePlanDetailBean.productItemCode='+productItemCode;
					}else{
						pricePlanExport = 'ecGoodspriceManageMentVo.queryPricePlanDetailBean.productItemCode='+productItemCode;
					}
				}
				var url = pricing.realPath('exportEcGoodsPricePlanDetail.action');
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

// 修改方案--明细的弹窗表格
Ext.define('Foss.pricing.ecGoodsPricePlan.PricePlanDetailForm', {
	extend : 'Ext.form.Panel',
	frame: true,
	height:440,
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
	        fieldLabel:pricing.ecGoodsPricePlanIndex.i18n('foss.pricing.destinationArea'),//目的区域
	        allowBlank:false,
	        priceRegionFlag:'ARRIVE_REGION',
	        xtype : 'commonallpriceregionEcGoodsselector',
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
			name: 'productItemCode',
			queryMode: 'local',
		    displayField: 'name',
		    valueField: 'code',
		    productItemId:null,
		    productItemName:null,
		    allowBlank:false,
		    store:pricing.getStore(null,null,['id','code','name'],pricing.queryEcProductItemEntityList),
	        fieldLabel:pricing.ecGoodsPricePlanIndex.i18n('foss.pricing.productEntry'),//产品条目
	        xtype : 'combo',
	        listeners:{
	        	select:function(comb,records){
	        		comb.productItemId = records[0].get('id');
	        	}
	        }
		},{	
			name: 'caculateType',
			queryMode: 'local',
			allowBlank:false,
		    displayField: 'value',
		    value:'WEIGHT',
		    valueField: 'key',
		    editable:false,
		    store:pricing.getStore('Foss.pricing.region.caculateType',null,['key','value']
		    ,[{'key':'WEIGHT','value':pricing.ecGoodsPricePlanIndex.i18n('foss.pricing.heavyGoodsPrices')},
		      {'key':'VOLUME','value':pricing.ecGoodsPricePlanIndex.i18n('foss.pricing.lightCargoPrices')}]),
		    fieldLabel: pricing.ecGoodsPricePlanIndex.i18n('foss.pricing.caculateEcGoodsType'),//计费规则
		    xtype : 'combo'
		},{
			name: 'leftRange',
			allowBlank:false,
			decimalPrecision:2,
			step:0.01,
		    maxValue: 99999999.99,
		    minValue:0,
	        fieldLabel: pricing.ecGoodsPricePlanIndex.i18n('foss.pricing.leftRange'),//下限
	        xtype : 'numberfield'
		},{
			name: 'rightRange',
			allowBlank:false,
			decimalPrecision:2,
			step:0.01,
		    maxValue: 99999999.99,
		    minValue:0,
	        fieldLabel: pricing.ecGoodsPricePlanIndex.i18n('foss.pricing.rightRange'),//上限
	        xtype : 'numberfield'
		},{
			name: 'prices',
			allowBlank:false,
			decimalPrecision:2,
			step:0.01,
		    maxValue: 999999.99,
		    minValue:0,
	        fieldLabel: pricing.ecGoodsPricePlanIndex.i18n('foss.pricing.rates'),//费率
	        xtype : 'numberfield'
		},{
			name: 'fixedCosts',
			allowBlank:false,
			decimalPrecision:2,
			step:0.01,
		    maxValue: 999999.99,
		    minValue:0,
	        fieldLabel: pricing.ecGoodsPricePlanIndex.i18n('foss.pricing.fixedCosts'),//固定费用
	        xtype : 'numberfield'
		},{
			 xtype:'radiogroup',
			 vertical:true,
			 allowBlank:false,
			 name:'centralizePickup',
			 fieldLabel:pricing.ecGoodsPricePlanIndex.i18n('foss.pricing.whetherAcceptEcGoods'),//是否接货
			 items:[{
			 	 xtype:'radio',
			     boxLabel:pricing.ecGoodsPricePlanIndex.i18n('i18n.pricingRegion.ye'),//是
			     name:'centralizePickup',		 
			     inputValue:'Y'
		     },{
		    	 xtype:'radio',
			     boxLabel:pricing.ecGoodsPricePlanIndex.i18n('i18n.pricingRegion.no'),//否
			     name:'centralizePickup',
			     inputValue:'N'
			     }]
		},{
			 xtype:'radiogroup',
			 vertical:true,
			 allowBlank:false,
			 name:'centralizeDelivery',
			 fieldLabel:pricing.ecGoodsPricePlanIndex.i18n('foss.pricing.whetherSendEcGoods'),//是否送货
			 items:[{
			 	 xtype:'radio',
			     boxLabel:pricing.ecGoodsPricePlanIndex.i18n('i18n.pricingRegion.ye'),//是
			     name:'centralizeDelivery',		 
			     inputValue:'Y'
		     },{
		    	 xtype:'radio',
			     boxLabel:pricing.ecGoodsPricePlanIndex.i18n('i18n.pricingRegion.no'),//否
			     name:'centralizeDelivery',
			     inputValue:'N'
			     }]
		},{
			xtype: 'textareafield',
			width:300,
		    fieldLabel: pricing.ecGoodsPricePlanIndex.i18n('foss.pricing.remark'),//备注
		    maxLength:200,
	        name:'remark'
		}];
		me.callParent([cfg]);
	}
});

// 修改方案--出发地信息面板
Ext.define('Foss.pricing.ecGoodsPricePlan.PricePlanAddFormPanel',{
	extend : 'Ext.form.Panel',
	title: pricing.ecGoodsPricePlanIndex.i18n('foss.pricing.departureinformation'),//出发地信息
	parent:null,
	frame: true,
	operaterCode:null,
	pricePlanEntity: null,
	priceRegionId: null,
	layout:{
		  type: 'table',
	      columns: 2
	},
	height :251,
    defaults : {
    	columnWidth : 1,
    	margin : '5 5 5 5',
    	width:320,
		labelSeparator:'',
		labelWidth:80,
		xtype : 'textfield'
    },
	//修改保存
	commitUpdatePricePlan:function(){
    	var me = this;
    	var baseForm = me.getForm();
    	if(baseForm.isValid()){
    		var pricePlanEntity = me.up('window').pricePlanEntity;
    		var priceRegionId = baseForm.findField('priceRegionCode').priceRegionId;
    		var priceRegionName = baseForm.findField('priceRegionCode').priceRegionName;
    		var priceRegionCode = baseForm.findField('priceRegionCode').value;
    		var pricePlanModel = new Foss.pricing.ecGoodsPricePlan.PricePlanModel(pricePlanEntity);
    		if(!Ext.isEmpty(priceRegionId)){
    			pricePlanModel.set('priceRegionId',priceRegionId);	
    		}
    		if(!Ext.isEmpty(priceRegionName)){
    			pricePlanModel.set('priceRegionName',priceRegionName);
    		}
    		if(!Ext.isEmpty(priceRegionCode)){
    			pricePlanModel.set('priceRegionCode',priceRegionCode);	
    		}
    		//设置修改人
    		pricePlanModel.set('modifyUser',FossUserContext.getCurrentUserEmp().empCode);
    		//将FORM中数据设置到MODEL里面
    		baseForm.updateRecord(pricePlanModel);
    		var params = {'ecGoodspriceManageMentVo':{'pricePlanEntity':pricePlanModel.data}};
    		var successFun = function(json){
				pricing.showInfoMes(json.message);
			};
			var failureFun = function(json){
				if(Ext.isEmpty(json)){
					pricing.showErrorMes(pricing.ecGoodsPricePlanIndex.i18n('foss.pricing.requestTimedOut'));//请求超时
				}else{
					pricing.showErrorMes(json.message);
				}
			};
			var url = pricing.realPath('updateEcGoodsPricePlan.action');
			pricing.requestJsonAjax(url,params,successFun,failureFun);
    	}
	 },
	//目的区域查询功能
	arrvRegionSearch:function(){
		var me = this;
    	var baseForm = me.getForm();
    	if(baseForm.isValid()){
    		pricePlanEntity = me.up('window').pricePlanEntity;
    		priceRegionId = baseForm.findField('arrvRegionCode').priceRegionId;
    		var grid = me.up('window').getPricePlanDetailGridPanel();
    		grid.arrvRegionId = priceRegionId;
    		grid.pricePlanId = pricePlanEntity.id;
			grid.getStore().load();
    	}
	 },
	//价格方案明细信息 --目的地信息列表
	pricePlanDetailGridPanel:null,
    getPricePlanDetailGridPanel: function(){
    	if(Ext.isEmpty(this.pricePlanDetailGridPanel)){
    			this.pricePlanDetailGridPanel = Ext.create('Foss.pricing.ecGoodsPricePlan.PricePlanDetailGridPanel');
    			pricing.ecGoodsPricePlanDetailGridPanel  = this.pricePlanDetailGridPanel;
    	}
    	return this.pricePlanDetailGridPanel;
    },	
	//构造函数
	constructor : function(config) {
		var me = this, 
		cfg = Ext.apply({}, config);
		me.items = [
		{
			name: 'id',
			hidden : true
		},{
			name: 'name',
			allowBlank:false,
			maxLength : 150,
	        fieldLabel:pricing.ecGoodsPricePlanIndex.i18n('foss.pricing.scenarioName')//方案名称
		},{
			name: 'priceRegionCode',
			allowBlank:false,
	        fieldLabel:pricing.ecGoodsPricePlanIndex.i18n('foss.pricing.originatingArea'),//始发区域
	        xtype:'commonpriceregionecgoodsselector',
	        priceRegionId: null,
	        priceRegionName:null,
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
			fieldLabel:pricing.ecGoodsPricePlanIndex.i18n('foss.pricing.availabilityDate'),//生效日期
			format:'Y-m-d',
			name:'beginTime'
		},{
			name: 'descNote',
			colspan : 2,
	        fieldLabel:pricing.ecGoodsPricePlanIndex.i18n('foss.pricing.programDescription'),//方案描述
	        xtype : 'textareafield',
	        maxLength : 200
		},{
			name: 'arrvRegionCode',
	        fieldLabel:pricing.ecGoodsPricePlanIndex.i18n('foss.pricing.destinationAreaNew'),//目的区域
	        xtype : 'commonallpriceregionEcGoodsselector',
	        priceRegionFlag:'ARRIVE_REGION',
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
		}],
		me.fbar = [{
			text : pricing.ecGoodsPricePlanIndex.i18n('foss.pricing.destinationAreaSearch'),//目的区域查询
			cls:'yellow_button',
			handler :function(){
				me.arrvRegionSearch();
			}  
		},{
			text :pricing.ecGoodsPricePlanIndex.i18n('i18n.pricingRegion.cancel'),//【取消】按钮
			handler :function(){
				me.up('window').isUpdate = null;
				me.up().close();
			}
		},{
			text : pricing.ecGoodsPricePlanIndex.i18n('foss.pricing.reset'),//【重置】按钮
			handler :function(){
					me.getForm().reset();
			} 
		},{
			text : pricing.ecGoodsPricePlanIndex.i18n('foss.pricing.save'),//【保存】按钮
			cls:'yellow_button',
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
// 修改价格方案明细中-目的地信息面板============================================================================================
Ext.define('Foss.pricing.ecGoodsPricePlan.PricePlanDetailGridPanel',{
	extend: 'Ext.grid.Panel',
	title : pricing.ecGoodsPricePlanIndex.i18n('foss.pricing.destinationInformation'),//目的地信息
	frame: true,
	sortableColumns:false,
    enableColumnHide:false,
	selType : "rowmodel", 
	enableColumnMove:false,
	stripeRows:true, 
	border: true,
	height :450,
	pricePlanId: null,
	arrvRegionId: null,
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
				pageSize:10
			});
		}
		return this.pagingToolbar;
	},
    //目的地信息--修改方案按钮-弹窗
    modifyPricePlanDetailWindow:null,
    getModifyPriceDetailWindow:function(){
    	if(Ext.isEmpty(this.modifyPricePlanDetailWindow)){  
    		this.modifyPricePlanDetailWindow = Ext.create('Foss.pricing.ecGoodsPricePlan.ModifyPriceDetailWindow',{
    			grid:this
    		});
	    	this.modifyPricePlanDetailWindow.parent = this;
    	}
    	return this.modifyPricePlanDetailWindow;
    },
	//初始化构造器
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.pricing.ecGoodsPricePlan.PricePlanDeatilStore',{
			autoLoad : false,
			pageSize : 10,
			listeners: {
				beforeload: function(store, operation, eOpts){
					var queryForm =me.up('window').getPricePlanAddFormPanel();
					if(queryForm!=null){
						Ext.apply(operation,{
							params : {
								'ecGoodspriceManageMentVo.queryPricePlanDetailBean.arrvRegionId' : me.arrvRegionId,//目的区域编码
								'ecGoodspriceManageMentVo.queryPricePlanDetailBean.pricePlanId' : me.pricePlanId//价格方案ID
							}
						});	
					}
				}
		    }
		});
		me.bbar = me.getPagingToolbar();
		me.selModel = me.getCheckboxModel();
	    //设置滚动条不失效
		me.listeners = {
	    	scrollershow: function(scroller) {
	    		if (scroller && scroller.scrollEl) {
	    				scroller.clearManagedListeners(); 
	    				scroller.mon(scroller.scrollEl, 'scroll', scroller.onElScroll, scroller); 
	    		}
	    	}
	    },
	    me.columns = [{
	    xtype: 'rownumberer',
		width:60,
		text: pricing.ecGoodsPricePlanIndex.i18n('i18n.pricingRegion.num')//序号
		},{
			text: pricing.ecGoodsPricePlanIndex.i18n('foss.pricing.destinationArea'),//目的区域
			width: 120,
	        dataIndex: 'arrvRegionName'
		},{
			text: pricing.ecGoodsPricePlanIndex.i18n('foss.pricing.productEntry'),//产品条目
			width: 90,
	        dataIndex: 'productItemName'
		},{
			text: pricing.ecGoodsPricePlanIndex.i18n('foss.pricing.caculateEcGoodsType'),//计费类型
			width: 55,
	        dataIndex: 'caculateType',
	        renderer:function(value){
				if(value=='VOLUME'){
					return pricing.ecGoodsPricePlanIndex.i18n('foss.pricing.volume')//体积
				}else if(value=='WEIGHT'){
					return pricing.ecGoodsPricePlanIndex.i18n('foss.pricing.weight')//重量
				}else{
					return '';
				}
			}
		},{
			text: pricing.ecGoodsPricePlanIndex.i18n('foss.pricing.leftRange'),//下限
	    	width: 60,
	        dataIndex: 'leftRange'
		},{
			text: pricing.ecGoodsPricePlanIndex.i18n('foss.pricing.rightRange'),//上限      
	    	width: 60,
	        dataIndex: 'rightRange'
		},{
			text: pricing.ecGoodsPricePlanIndex.i18n('foss.pricing.rates'),//费率
			width: 40,
	        dataIndex: 'prices'
		},{
			text: pricing.ecGoodsPricePlanIndex.i18n('foss.pricing.fixedCosts'),//固定费用
			width: 55,
	        dataIndex: 'fixedCosts'
		},{
			width: 100,
			text: pricing.ecGoodsPricePlanIndex.i18n('foss.pricing.whetherAcceptEcGoods'),//是否包含接货
	        dataIndex: 'centralizePickup',
	        renderer:function(value){
				if(value=='Y'){
					return pricing.ecGoodsPricePlanIndex.i18n('i18n.pricingRegion.ye')//"是";
				}else if(value=='N'){
					return pricing.ecGoodsPricePlanIndex.i18n('i18n.pricingRegion.no')//"否";
				}else{
					return '';
				}
			}
		},{
			width: 100,
			text: pricing.ecGoodsPricePlanIndex.i18n('foss.pricing.whetherSendEcGoods'),//是否包含送货
	        dataIndex: 'centralizeDelivery',
	        renderer:function(value){
				if(value=='Y'){
					return pricing.ecGoodsPricePlanIndex.i18n('i18n.pricingRegion.ye')//"是";
				}else if(value=='N'){
					return pricing.ecGoodsPricePlanIndex.i18n('i18n.pricingRegion.no')//"否";
				}else{
					return '';
				}
			}
		},{
			text: pricing.ecGoodsPricePlanIndex.i18n('foss.pricing.remark'),//备注
			width: 120,
	        dataIndex: 'remark'
		}],
		pricing.pagingBar = me.bbar;
		me.callParent([cfg]);
	}
});

// 查询结果面板-修改价格方案弹窗===================================================================================================
Ext.define('Foss.pricing.ecGoodsPricePlan.PricePlanUpdateWindow',{
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
				//重新加载父容器中的store
				me.parent.getStore().load();
				me.isUpdate = null;
			},
			beforeshow:function(me){
				me.getPricePlanAddFormPanel().getForm().loadRecord(new Foss.pricing.ecGoodsPricePlan.PricePlanModel(me.pricePlanEntity));
				me.getPricePlanAddFormPanel().getForm().findField('priceRegionCode').setCombValue(me.pricePlanEntity.priceRegionName,me.pricePlanEntity.priceRegionCode);
				me.getPricePlanDetailGridPanel().store.removeAll(true);
			}
		},
	    //出发地信息面板
		pricePlanAddFormPanel:null,
	    getPricePlanAddFormPanel : function(){
	    	var me = this;
	    	if(Ext.isEmpty(me.pricePlanAddFormPanel)){
		    		me.pricePlanAddFormPanel = Ext.create('Foss.pricing.ecGoodsPricePlan.PricePlanAddFormPanel');
 	    	} 
	    	return this.pricePlanAddFormPanel;
	    },
	     //目的地信息面板
		pricePlanDetailGridPanel:null,
	    getPricePlanDetailGridPanel: function(){
	    	if(Ext.isEmpty(this.pricePlanDetailGridPanel)){
	    			this.pricePlanDetailGridPanel = Ext.create('Foss.pricing.ecGoodsPricePlan.PricePlanDetailGridPanel');
	    			pricing.ecGoodsPricePlanDetailGridPanel  = this.pricePlanDetailGridPanel;
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

// 查询结果面板--中止价格方案弹窗【单条】=============================================================================================
Ext.define('Foss.pricing.ecGoodsPricePlan.PricePlanStopEndTimeWindow',{
		extend: 'Ext.window.Window',
		title: pricing.ecGoodsPricePlanIndex.i18n('foss.pricing.suspendPriceScheme'),//中止价格方案
		width:380,
		height:120,
		pricePlanId:null,
		closeAction: 'hide' ,
		beforehide:function(me){
				var form = me.down('form');
				form.getForm().reset();
		},
	    //中止
		pricePlanStopFormPanel:null,
		getPricePlanStopFormPanel : function(){
	    	if(Ext.isEmpty(this.pricePlanAddFormPanel)){
	    		this.pricePlanStopFormPanel = Ext.create('Foss.pricing.ecGoodsPricePlan.PricePlanStopFormPanel');
	    	}
	    	return this.pricePlanStopFormPanel;
	    },
	   	initComponent : function() {
			var me = this;
			me.items = [me.getPricePlanStopFormPanel()];
			me.callParent();
		}
});
Ext.define('Foss.pricing.ecGoodsPricePlan.PricePlanStopFormPanel',{
	extend : 'Ext.form.Panel',
	layout:'column',
	width:375,
	height:100,
	//中止方案
	stopPricePlan:function(pricePlanId){
		var me = this;
    	//获取表单
    	var form = me.getForm();
    	if(form.isValid()){
			var pricePlanModel = new Foss.pricing.ecGoodsPricePlan.PricePlanModel();
			form.updateRecord(pricePlanModel);
    		pricePlanModel.set('id',pricePlanId);
    		var params = {'ecGoodspriceManageMentVo':{'pricePlanEntity':pricePlanModel.data}};
    		//ajax请求
    		var url = pricing.realPath('stopEcGoodsPricePlan.action');
    		//成功提示
    		var successFun = function(json){
    			pricing.showInfoMes(json.message);
    			me.up('window').hide();
    			//成功后查询列表
				pricing.pagingBar.moveFirst();   			
    	    };
    	    //失败提示
    	    var failureFun = function(json){
    	    	if(Ext.isEmpty(json)){
    				pricing.showErrorMes(pricing.ecGoodsPricePlanIndex.i18n('i18n.pricingRegion.requestTimeOut'));
    			}else{
    				pricing.showErrorMes(json.message);
    			}
    	    };
    		pricing.requestJsonAjax(url,params,successFun,failureFun);
    	}
	},
	initComponent : function() {
		var me = this;
		me.items = [
			{
				xtype:'datetimefield_date97',
				fieldLabel:pricing.ecGoodsPricePlanIndex.i18n('foss.pricing.deadline'),//截止日期
				editable:false,
				time : true,
				name:'endTime',
				allowBlank:false,
				id : 'Foss_ecGoodsPricePlan_suspendEndTime_ID',
				dateConfig: {
					el : 'Foss_ecGoodsPricePlan_suspendEndTime_ID-inputEl'
				}
			},{
				xtype : 'container',
				margin : '0 0 2 0',
				items : [{
					xtype : 'button', 
					width:70,
					text : pricing.ecGoodsPricePlanIndex.i18n('foss.pricing.stop'),//【中止】按钮
					handler : function() {
						var pricePlanId = me.up('window').pricePlanId;
						me.stopPricePlan(pricePlanId);
					}
				}]
			}
		];
		me.callParent();
	}
});

// 查询结果面板--计划中止按钮点击后的弹窗======================================================================================================
Ext.define('Foss.pricing.ecGoodsPricePlan.PricePlanImmediatelyStopEndTimeWindow',{
		extend: 'Ext.window.Window',
		title: pricing.ecGoodsPricePlanIndex.i18n('foss.pricing.immediatelySupendPricePriceScheme'),//立即中止方案
		width:380,
		height:152,
		pricePlanEntity:null,
		idList:null,
		closeAction: 'hide' ,
		listeners:{
			beforehide:function(me){
				var form = me.down('form');
				form.getForm().reset();
			},
			beforeshow:function(me){
				me.getPricePlanStopFormPanel().getForm().findField('showTime').setValue('中止日期必须大于当前营业日期，且不能大于原方案截止日期！');
				me.getPricePlanStopFormPanel().getForm().findField('endTime').setValue(Ext.Date.format(new Date(),'Y-m-d H:i:s'));
			}
		},
		pricePlanStopFormPanel:null,
		getPricePlanStopFormPanel : function(idList){
	    	if(Ext.isEmpty(this.pricePlanStopFormPanel)){
	    		this.pricePlanStopFormPanel = Ext.create('Foss.pricing.ecGoodsPricePlan.PricePlanImmediatelyStopFormPanel');
	    	}
	    	this.pricePlanStopFormPanel.idList = idList;
	    	return this.pricePlanStopFormPanel;
	    },
	   	constructor : function(config) {
			var me = this, cfg = Ext.apply({}, config);
			me.items = [me.getPricePlanStopFormPanel(me.idList)];
			me.callParent(cfg);
		}
});
Ext.define('Foss.pricing.ecGoodsPricePlan.PricePlanImmediatelyStopFormPanel',{
	extend : 'Ext.form.Panel',
	layout:'column',
	pricePlanEntity:null,
	idList:null,
	stopPricePlan:function(idList){
		var me = this;
    	var form = me.getForm();
    	if(form.isValid()){
			var pricePlanModel = new Foss.pricing.ecGoodsPricePlan.PricePlanModel();
			form.updateRecord(pricePlanModel);
    		var params = {'ecGoodspriceManageMentVo':{'pricePlanEntity':pricePlanModel.data,'pricePlanIds':idList}};
    		pricePlanModel.set('endTime',Ext.Date.parse(form.findField('endTime').getValue(), 'Y-m-d H:i:s'));
    		var url = pricing.realPath('stopEcGoodsPricePlans.action');
    		var successFun = function(json){
    			pricing.showInfoMes(json.message);
    			me.up('window').hide();
    			me.up('window').parent.getStore().load();
				pricing.pagingBar.moveFirst();   			
    	    };
    	    var failureFun = function(json){
    	    	if(Ext.isEmpty(json)){
    				pricing.showErrorMes(pricing.ecGoodsPricePlanIndex.i18n('i18n.pricingRegion.requestTimeOut'));
    			}else{
    				pricing.showErrorMes(json.message);
    			}
    	    };
    		pricing.requestJsonAjaxMax(url,params,successFun,failureFun);
    	}
	},
	constructor: function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.items = [{
				width:280,
				name:'showTime',
				xtype: 'displayfield',
				columnWidth:.9
			},{ 
				fieldLabel :pricing.ecGoodsPricePlanIndex.i18n('foss.pricing.suspendTime'),//中止日期
				name : 'endTime',
				xtype : 'datetimefield_date97',
				editable:false,
				time : true,
				id : 'Foss_pricePlan_stopEndTime_ID',
				allowBlank:false,
				dateConfig: {
					el : 'Foss_pricePlan_stopEndTime_ID-inputEl'
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
				text : pricing.ecGoodsPricePlanIndex.i18n('i18n.pricingRegion.determine'),//【确认】按钮
				handler : function() {
					var idList = me.up('window').idList;
					me.stopPricePlan(idList);
				}
			},{
				xtype : 'button', 
				width:70,
				columnWidth:.15,
				text : pricing.ecGoodsPricePlanIndex.i18n('i18n.pricingRegion.cancel'),//【取消】按钮
				handler : function() {
					me.up('window').hide();
				}
			}];
	        me.callParent(cfg);
    }
});

// 查询结果面板--计划激活按钮点击后的弹窗======================================================================================================
Ext.define('Foss.pricing.ecGoodsPricePlan.PricePlanImmediatelyActiveTimeWindow',{
		extend: 'Ext.window.Window',
		title: pricing.ecGoodsPricePlanIndex.i18n('foss.pricing.immediatelyActiveationPriceScheme'),//立即激活方案
		width:380,
		height:152,
		pricePlanEntity:null,
		idList:null,
		closeAction: 'hide' ,
		listeners:{
			beforehide:function(me){
				var form = me.down('form');
				form.getForm().reset();
			},
			beforeshow:function(me){
				me.getPricePlanImmediatelyActiveFormPanel().getForm().findField('showTime').setValue('生效时间默认显示当前时间，激活时间不能小于当前时间！');
				me.getPricePlanImmediatelyActiveFormPanel().getForm().findField('beginTime').setValue(Ext.Date.format(new Date(),'Y-m-d H:i:s'));
			}
		},
		//创建表格面板
		pricePlanImmediatelyActiveFormPanel:null,
		getPricePlanImmediatelyActiveFormPanel : function(idList){
	    	if(Ext.isEmpty(this.pricePlanImmediatelyActiveFormPanel)){
	    		this.pricePlanImmediatelyActiveFormPanel = Ext.create('Foss.pricing.ecGoodsPricePlan.PricePlanImmediatelyActiveFormPanel');
	    	}
	    	this.pricePlanImmediatelyActiveFormPanel.idList = idList;
	    	return this.pricePlanImmediatelyActiveFormPanel;
	    },
	   	constructor : function(config) {
			var me = this, cfg = Ext.apply({}, config);
			me.items = [me.getPricePlanImmediatelyActiveFormPanel(me.idList)];
			me.callParent(cfg);
		}
});
// 立即激活功能表格面板
Ext.define('Foss.pricing.ecGoodsPricePlan.PricePlanImmediatelyActiveFormPanel',{
	extend : 'Ext.form.Panel',
	layout:'column',
	pricePlanEntity:null,
	idList:null,
	activetionPricePlan:function(idList){
		var me = this;
    	var form = me.getForm();
    	if(form.isValid()){
			var pricePlanModel = new Foss.pricing.ecGoodsPricePlan.PricePlanModel();
			form.updateRecord(pricePlanModel);
			pricePlanModel.set('beginTime',Ext.Date.parse(form.findField('beginTime').getValue(), 'Y-m-d H:i:s'));
    		var params = {
    				'ecGoodspriceManageMentVo':{'pricePlanEntity':pricePlanModel.data,'pricePlanIds':idList}
    		};
    		var url = pricing.realPath('immediatelyActiveEcGoodsPricePlan.action');
    		var successFun = function(json){
    			pricing.showInfoMes(json.message);
    			me.up('window').hide();
    			//重新加载价格方案界面的数据 更新界面显示
    			me.up('window').parent.getStore().load();
				pricing.pagingBar.moveFirst();   			
    	    };
    	    var failureFun = function(json){
    	    	if(Ext.isEmpty(json)){
    				pricing.showErrorMes(pricing.ecGoodsPricePlanIndex.i18n('i18n.pricingRegion.requestTimeOut'));
    			}else{
    				pricing.showErrorMes(json.message);
    			}
    	    };
    		pricing.requestJsonAjaxMax(url,params,successFun,failureFun);//发送AJAX请求
    	}
	},
	constructor: function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.items = [{
				width:280,
				name: 'showTime',
				xtype: 'displayfield',
				columnWidth:.9
			},{
				fieldLabel:pricing.ecGoodsPricePlanIndex.i18n('foss.pricing.availabilityDate'),//'生效日期',
				name : 'beginTime',
				xtype : 'datetimefield_date97',
				editable:false,
				time : true,
				id : 'Foss_pricePlan_activetionEndTime_ID',
				allowBlank:false,
				dateConfig: {
					el : 'Foss_pricePlan_activetionEndTime_ID-inputEl'
				},
				columnWidth:.9
			},{
				xtype: 'container',
				columnWidth:.6,
				html: '&nbsp;'
			},{
				xtype : 'button', 
				id : 'Foss_pricePlan_PricePlanImmediatelyActiveFormPanel_ID',
				width:70,
				columnWidth:.15,
				plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin', {
					  //设定间隔秒数,如果不设置，默认为2秒
					  seconds: 20
				}),
				text : pricing.ecGoodsPricePlanIndex.i18n('i18n.pricingRegion.determine'),//确定
				handler : function() {
					var form = this.up('form').getForm();
					var activeBeginTime = form.getValues().beginTime;
					var result = Ext.Date.parse(activeBeginTime,'Y-m-d H:i:s') - Ext.Date.parse(Ext.Date.format(new Date(),'Y-m-d H:i:s'),'Y-m-d H:i:s');
					if(result<0)
					{
						//提示消息
						Ext.ux.Toast.msg(pricing.ecGoodsPricePlanIndex.i18n('foss.pricing.promptMessage'),'激活时间不能小于当前时间','error', 3000);
						Ext.getCmp('Foss_pricePlan_PricePlanImmediatelyActiveFormPanel_ID').setDisabled(false);
						return;
					}
					var idList = me.up('window').idList;
					me.activetionPricePlan(idList);
				}
			},{
				xtype : 'button', 
				width:70,
				columnWidth:.15,
				text : pricing.ecGoodsPricePlanIndex.i18n('i18n.pricingRegion.cancel'),//【取消】按钮
				handler : function() {
					me.up('window').hide();
				}
			}];
        this.callParent(cfg);
    }
});

// 查询结果面板--导入弹窗=======================================================================================================
Ext.define('Foss.pricing.ecGoodsPricePlan.UploadPriceform',{
	extend:'Ext.window.Window',
	title:pricing.ecGoodsPricePlanIndex.i18n('foss.pricing.importPriceScheme'),
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
	parent:null,
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
				fieldLabel:pricing.ecGoodsPricePlanIndex.i18n('foss.pricing.pleaseSelectAttachments'),//请选择附件
				labelWidth:100,
				buttonText:pricing.ecGoodsPricePlanIndex.i18n('foss.pricing.browse'),//【浏览】按钮
				flex:3
			}]
		}];
		me.fbar = me.getFbar();
		me.callParent([cfg]);
	},
	getFbar:function(){
		var me = this;
		return [{
			text:pricing.ecGoodsPricePlanIndex.i18n('foss.pricing.import'),//【导入】按钮
			xtype:'button',
			scope:me,
			handler:me.uploadFile
		},{
			text:pricing.ecGoodsPricePlanIndex.i18n('i18n.pricingRegion.cancel'),//【取消】按钮
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
			if(Ext.isEmpty(json.ecGoodspriceManageMentVo.numList)){
				pricing.showInfoMes(pricing.ecGoodsPricePlanIndex.i18n('foss.pricing.allDataImportSuccess'));//全部数据导入成功！
				me.close();
			}else{
				var message = pricing.ecGoodsPricePlanIndex.i18n('foss.pricing.first');//第
				for(var i = 0;i<json.platformVo.numList;i++){
					message = message+json.platformVo.numList[i]+','
				}
				message = message+pricing.ecGoodsPricePlanIndex.i18n('foss.pricing.lineImportSuccess');//行，没有导入成功！
				pricing.showWoringMessage(message);
			}
		};
		var failureFn = function(json){
			if(Ext.isEmpty(json)){
				pricing.showErrorMes(pricing.ecGoodsPricePlanIndex.i18n('i18n.pricingRegion.requestTimeOut'));//请求超时
			}else{
				pricing.showErrorMes(json.message);
			}
		};
		var form = me.down('form').getForm();
		var url = pricing.realPath('importEcGoodsPrice.action');
		form.submit({
            url: url,
			timeout:60000,   
            waitMsg: pricing.ecGoodsPricePlanIndex.i18n('foss.pricing.uploadYourAttachment'),//上传您的附件...
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

// 开始加载界面=============================================================================================================
Ext.onReady(function(){
	Ext.QuickTips.init();
	pricing.searchEcProductItemEntityList();//查询中的条目元素
	var queryform = Ext.create('Foss.pricing.ecGoodsPricePlan.PricePlanFormPanel');
	var gridPanel =	Ext.create('Foss.pricing.ecGoodsPricePlan.PricePlanGridPanel');
	pricing.ecGoodsQueryform = queryform;
	pricing.gridPanel = gridPanel;
	Ext.getCmp('T_pricing-ecGoodsPricePlanIndex').add(Ext.create('Ext.panel.Panel', {
	  	id:'T_pricing-ecGoodsPricePlanIndex_content',
		cls:"panelContentNToolbar",
		bodyCls:'panelContent-body',
		//获得查询FORM
		getQueryPricePlanForm : function() {
			return queryform;
		},
		//获得查询结果GRID
		getPricePlanGridPanel : function() {
			if(Ext.isEmpty(this.gridPanel)){
				this.gridPanel = Ext.create('Foss.pricing.ecGoodsPricePlan.PricePlanGridPanel');
			}
			return gridPanel;
		},
		items : [queryform,gridPanel]
	}));
});

