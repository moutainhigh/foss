/**
 * 快递代理运价方案管理
 * @author 094463-foss-xieyantao
 * @date 2013-7-26 下午3:08:57
 */

//转换long类型为日期
pricing.changeLongToDate = function(value) {
	if (value != null) {
		var date = new Date(value);
		return date;
	} else {
		return null;
	}
};

//消息提醒框
pricing.agencyPricePlan.showWarningMsg = function(title,message,fun){
		Ext.Msg.show({
		    title:title,
		    msg:message,
		    width:120,
		    buttons: Ext.Msg.OK,
		    icon: Ext.MessageBox.WARNING,
		    callback:function(e){
		    	if(!Ext.isEmpty(fun)){
		    		if(e=='ok'){
			    		fun();
			    	}
		    	}
		    }
		});
		//3秒后提醒框隐藏
		setTimeout(function(){
	        Ext.Msg.hide();
	    }, 3000);
	};
	
//Ajax请求--json
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
			failFn(result);
		}
	});
};
/**.
 * <p>
 * 公共方法，通过storeId和model创建STORE<br/>
 * <p>
 * @param  storeId  
 * @param  model   store所用到的model名
 * @param  fields   store所用到的fields
 * @returns store  返回创建的store
 * @author 张斌
 * @时间 2012-8-31
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
pricing.yes = 'Y';//是
pricing.no = 'N';//否
pricing.ALL = 'ALL';//全部
pricing.tomorrowTime = null;
pricing.goodsTypeFlightPlan = [];//货物类型
//获取服务当前时间
pricing.haveServerNowTime = function(){
	Ext.Ajax.request({
		url:pricing.realPath('haveServerNowTimeA.action'),
		async:false,
		success:function(response){
			var result = Ext.decode(response.responseText);
			var today = new Date(result.pricingValuationVo.nowTime);//获取服务当前时间
			pricing.tomorrowTime = today.setDate(today.getDate()+1);
		},
		failure:function(response){
			var result = Ext.decode(response.responseText);
			if(Ext.isEmpty(result)){
				pricing.showErrorMes(pricing.agencyPricePlan.i18n('i18n.pricingRegion.requestTimeOut'));
			}else{
				pricing.showErrorMes(result.message);
			}
		},
		exception:function(response){
			var result = Ext.decode(response.responseText);
			if(Ext.isEmpty(result)){
				pricing.showErrorMes(pricing.agencyPricePlan.i18n('i18n.pricingRegion.requestTimeOut'));
			}else{
				pricing.showErrorMes(result.message);
			}
		}
	});
};

//获取货物类型
pricing.queryAllGoodsType = function(){
	Ext.Ajax.request({
		url:pricing.realPath('queryAllGoodsType.action'),
		async:false,
		success:function(response){
			var result = Ext.decode(response.responseText);
			pricing.goodsTypeFlightPlan = result.goodVo.goodsTypeEntityList;
		},
		failure:function(response){
			var result = Ext.decode(response.responseText);
			if(Ext.isEmpty(result)){
				pricing.showErrorMes(pricing.agencyPricePlan.i18n('i18n.pricingRegion.requestTimeOut'));
			}else{
				pricing.showErrorMes(result.message);
			}
		},
		exception:function(response){
			var result = Ext.decode(response.responseText);
			if(Ext.isEmpty(result)){
				pricing.showErrorMes(pricing.agencyPricePlan.i18n('i18n.pricingRegion.requestTimeOut'));
			}else{
				pricing.showErrorMes(result.message);
			}
		}
	});
};
//--------------------------------------pricing----------------------------------------
//快递代理公司运价信息
Ext.define('Foss.pricing.PartbussPlanEntity', {
    extend: 'Ext.data.Model',
    fields : [{
        name : 'id',
        type : 'string'
    },{
        name : 'createDate',//创建时间
        type : 'date',
        defaultValue : null,
        convert : pricing.changeLongToDate
    },{
        name : 'createUser',//创建人
        type : 'string'
    },{
        name : 'createUserName',//创建人姓名
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
        name : 'expressPartbussCode',/* 快递代理公司编码 */
        type : 'string'
    },{
        name : 'expressPartbussName',/* 快递代理公司名称 */
        type : 'string'
    },{
        name : 'priceNo', /* 运价号 */
        type : 'string'
    },{
        name : 'loadOrgCode',/* 配载部门编码 */
        type : 'string'
    },{
        name : 'loadOrgName',/* 配载部门名称 */
        type : 'string'
    },{
        name : 'beginTime',/* 开始时间 */
        type : 'date',
        defaultValue : null,
        convert : pricing.changeLongToDate
    },{
        name : 'endTime',/* 结束时间 */
        type : 'date',
        defaultValue : null,
        convert : pricing.changeLongToDate
    },{
        name : 'billDate',/* 业务时间 */
        type : 'date',
        defaultValue : null,
        convert : pricing.changeLongToDate
    },{
        name : 'active',/* 是否激活 */
        type : 'string'
    },{
        name : 'descNote',/* 描述 */
        type : 'string'
    },{
        name : 'createOrgCode',/* 创建部门编号 */
        type : 'string'
    },{
        name : 'modifyOrgCode', /* 修改部门编号 */
        type : 'string'
    },{
        name : 'currencyCode',/* 币种 */
        type : 'string'
    },{
    	name : 'versionNo',/*是否最新版本*/
    	type : 'number'
    },{
    	name : 'showTime',
    	type : 'string'
    },{
    	name : 'currentUsedVersion',//是否当前版本
    	type : 'string'
    },{
    	name : 'weightRule',//重量取数规则
    	type : 'string'
    }]
});

//快递代理网点运价方案信息
Ext.define('Foss.pricing.OutbranchPlanEntity', {
    extend: 'Ext.data.Model',
    fields : [{
        name : 'id',
        type : 'string'
    },{
        name : 'createDate',//创建时间
        type : 'date',
        defaultValue : null,
        convert : pricing.changeLongToDate
    },{
        name : 'createUser',//创建人
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
        name : 'expressPartbussPlanId',/* 快递代理运价方案ID */
        type : 'string'
    },{
        name : 'outerBranchCode',/* 快递代理公司网点CODE */
        type : 'string'
    },{
        name : 'outerBranchName', /* 快递代理公司网点名称（扩展） */
        type : 'string'
    },{
        name : 'billType',/* 计费方式分为：FX：按照区间划分，固定收费 AW：步进价格规则 */
        type : 'string'
    },{
        name : 'active',/* 是否激活 */
        type : 'string'
    },{
        name : 'createOrgCode',/* 创建部门编号 */
        type : 'string'
    },{
        name : 'modifyOrgCode', /* 修改部门编号 */
        type : 'string'
    },{
        name : 'currencyCode',/* 币种 */
        type : 'string'
    },{
        name : 'provName',/* 省份 */
        type : 'string'
    },{
        name : 'cityName',/* 城市 */
        type : 'string'
    },{
        name : 'cityCode',/*城市编码*/
        type : 'string'
    },{
        name : 'districtName',/*区县名称*/
        type : 'string'
    },{
        name : 'districtCode',/*区县编码*/
        type : 'string'
    },{
    	name : 'versionNo',/*是否最新版本*/
    	type : 'string'
    }]
});

//快递代理网点运价方案明细信息
Ext.define('Foss.pricing.OubrPlanDetailEntity', {
    extend: 'Ext.data.Model',
    fields : [{
        name : 'id',
        type : 'string'
    },{
        name : 'createDate',//创建时间
        type : 'date',
        defaultValue : null,
        convert : pricing.changeLongToDate
    },{
        name : 'createUser',//创建人
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
        name : 'expressOutbranchPlanId',/* 快递代理网点运价方案ID */
        type : 'string'
    },{
        name : 'caculateType',/* 计费类别WEIGHT ：重量计费VOLUME：体积计费 */
        type : 'string'
    },{
        name : 'leftrange', /* 计价左区间 */
        type : 'number'
    },{
        name : 'rightrange', /* 计价右区间 */
        type : 'number'
    },{
        name : 'fee', /* 固定费用 */
        type : 'number'
    },{
        name : 'feeRate', /* 费率或者单价 */
        type : 'number'
    },{
        name : 'dimension', /* 量纲 */
        type : 'number'
    },{
        name : 'minFee',/* 最低一票*/
        type : 'int'
    },{
        name : 'active',/* 是否激活 */
        type : 'string'
    },{
        name : 'createOrgCode',/* 创建部门编号 */
        type : 'string'
    },{
        name : 'modifyOrgCode', /* 修改部门编号 */
        type : 'string'
    },{
        name : 'currencyCode',/* 币种 */
        type : 'string'
    },{
    	name : 'versionNo',/*是否最新版本*/
    	type : 'string'
    }]
});

//货物类型信息
Ext.define('Foss.pricing.GoodEntity', {
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
        name : 'createDate',
        type : 'date',
        defaultValue : null,
        convert : pricing.changeLongToDate
    },{
        name : 'code',// 编码
        type : 'string'
    },{
        name : 'name',// 名称
        type : 'string'
    },{
        name : 'active',// 是否启用
        type : 'string'
    },{
        name : 'description',//描述
        type : 'string'
    },{
        name : 'modifyUserName',//修改人姓名
        type : 'string'
    },{
        name : 'createUserName',//创建人姓名
        type : 'string'
    }]
});
//------------------------------------model---------------------------------------------------



/**
 * 快递代理公司运价Store（Foss.pricing.PartbussPlanEntity）
 */
Ext.define('Foss.pricing.PartbussPlanStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.pricing.PartbussPlanEntity',//快递代理公司运价的MODEL
	pageSize:20,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url : '../pricing/queryPartbussPlans.action',//请求地址
		reader : {
			type : 'json',
			root : 'pricePlanVo.partbussPlanList',//获取的数据
			totalProperty : 'totalCount'//总个数
		}
	}
});

/**
 * 快递代理网点运价方案Store
 */
Ext.define('Foss.pricing.OutbranchPlanStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.pricing.OutbranchPlanEntity',//快递代理网点运价方案的MODEL
	pageSize:20,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url : '../pricing/queryOutbranchPlans.action',//请求地址
		reader : {
			type : 'json',
			root : 'pricePlanVo.outbranchPlanList',//获取的数据
			totalProperty : 'totalCount'//总个数
		}
	}
});

/**
 * 快递代理网点运价明细Store
 */
Ext.define('Foss.pricing.OubrPlanDetailGridStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.pricing.OubrPlanDetailEntity',//快递代理公司运价明细的MODEL
	pageSize:20,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url : '../pricing/queryOubrPlanDetails.action',//请求地址
		reader : {
			type : 'json',
			root : 'pricePlanVo.oubrPlanDetailList',//获取的数据
			totalProperty : 'totalCount'//总个数
		}
	}
});

//创建一个本地的类型store
Ext.define('Foss.pricing.agencyPricePlan.TypeStore',{
	extend: 'Ext.data.Store',
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
	},
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

//----------------------------------------store---------------------------------

/**
 * 快递代理公司查询条件FORM   
 */
Ext.define('Foss.pricing.QueryPartbussPlanForm', {
	extend : 'Ext.form.Panel',
	title: pricing.agencyPricePlan.i18n('i18n.pricingRegion.searchCondition'),//查询条件
	frame: true,
	collapsible: true,
	layout:{
		type:'table',
		columns: 3
	},
    defaults : {
    	colspan: 1,
		margin : '8 10 8 10',
		anchor : '100%'
    },
    height :180,
	defaultType : 'textfield',
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.items  = [{
			xtype : 'commonLdpAgencyCompanySelector',
			active:'Y',
			name: 'expressPartbussCode',//快递代理公司
			fieldLabel :pricing.agencyPricePlan.i18n('i18n.pricingRegion.expressDeliveryAgencyCompany')
		},{
			name: 'active',
			queryMode: 'local',
		    displayField: 'valueName',
		    valueField: 'valueCode',
		    editable:false,
		    value:'',
		    store:pricing.getStore(null,null,['valueName','valueCode']
		    ,[ {'valueName':pricing.agencyPricePlan.i18n('i18n.pricingRegion.all'),'valueCode':''}//全部
		    , {'valueName':pricing.agencyPricePlan.i18n('i18n.pricingRegion.active'),'valueCode':pricing.yes}//激活
		    , {'valueName':pricing.agencyPricePlan.i18n('i18n.pricingRegion.unActive'),'valueCode':pricing.no}]),//未激活
	        fieldLabel: pricing.agencyPricePlan.i18n('foss.pricing.status'),//状态
	        xtype : 'combo'
		},{
			name:'billDate',
			editabled:false,
	        fieldLabel: pricing.agencyPricePlan.i18n('foss.pricing.businessTime'),//业务时间
	        xtype : 'datetimefield_date97',
			editable:false,
			time : true,
			id : 'Foss_flightPrice_billDate_ID',
//			allowBlank:false,
			dateConfig: {
				el : 'Foss_flightPrice_billDate_ID-inputEl'
			}
		}],
		me.fbar = [{
			xtype : 'button', 
			width:70,
			left : 1,
			text : pricing.agencyPricePlan.i18n('foss.pricing.reset'),//重置
			margin:'0 825 0 0',
			handler : function() {
				me.getForm().reset();
			}
		},{
			xtype : 'button', 
			width:70,
			cls:'yellow_button',
			text : pricing.agencyPricePlan.i18n('i18n.pricingRegion.search'),//查询
//			hidden: !pricing.flightPrice.isPermission('flightPrice/flightPriceQuerybutton'),
			handler : function() {
				if(me.getForm().isValid()){
					var grid = Ext.getCmp('T_pricing-agencyPricePlanIndex_content').getFlightPriceGrid();
					grid.getPagingToolbar().moveFirst();
				}			
			}
		}];
		me.callParent([cfg]);
	}
});

/**
 * 快递代理公司网点运价查询条件表单
 */
Ext.define('Foss.pricing.QueryOutBranchPlanForm', {
	extend : 'Ext.form.Panel',
	title: pricing.agencyPricePlan.i18n('i18n.pricingRegion.searchCondition'),//查询条件
	frame: true,
	collapsible: true,
	layout:{
		type:'table',
		columns: 3
	},
    defaults : {
    	colspan: 1,
		margin : '8 10 8 10',
		anchor : '100%'
    },
    height :150,
	defaultType : 'textfield',
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.items  = [{
			xtype : 'commonairlinesselector',
			forceSelection : true,
			//更改相匹配长度
			labelWidth:120,
			name: 'outerBranchCode',//快递代理公司网点
			fieldLabel : pricing.agencyPricePlan.i18n('i18n.pricingRegion.expressDeliveryAgencyCompanyWebSite'),
			active:'Y',
			xtype:'commonldpagencydeptselector'
		},{
			xtype : 'linkregincombselector',
			type : 'C-C',
//			labelWidth:300,
//			cityWidth : 150,// 城市长度
			cityLabel : '城市',// 城市label
			cityLabelWidth:80,
			cityName : 'cityCode',// 城市name
			cityIsBlank : true,
//			areaWidth : 150,// 县长度
			areaLabel : '区县',// 县label
			areaLabelWidth:80,
			areaName : 'districtCode',// 县name
			areaIsBlank : true
		}],
		me.fbar = [{
			xtype : 'button', 
			width:70,
			left : 1,
			text : pricing.agencyPricePlan.i18n('foss.pricing.reset'),//重置
			margin:'0 825 0 0',
			handler : function() {
				me.getForm().reset();
			}
		},{
			xtype : 'button', 
			width:70,
			cls:'yellow_button',
			text : pricing.agencyPricePlan.i18n('i18n.pricingRegion.search'),//查询
//			hidden: !pricing.flightPrice.isPermission('flightPrice/flightPriceQuerybutton'),
			handler : function() {
				me.up().getQueryFlightPriceDetailGrid().getPagingToolbar().moveFirst();
			}
		}];
		me.callParent([cfg]);
	}
});

/**
 * 快递代理公司运价方案列表   
 */
Ext.define('Foss.pricing.PartbussPlanGrid', {
	extend: 'Ext.grid.Panel',
	//快递代理公司运价方案列表
	title : pricing.agencyPricePlan.i18n('i18n.pricingRegion.expressDeliveryAgencyCompanyFreightPlanList'),//快递代理公司运价方案列表
	frame: true,
    sortableColumns:false,
    enableColumnHide:false,
    enableColumnMove:false,
	stripeRows : true, // 交替行效果
	selType : "rowmodel", // 选择类型设置为：行选择
	emptyText: pricing.agencyPricePlan.i18n('foss.pricing.theQueryResultIsEmpty'),//查询结果为空
	//得到bbar
	pagingToolbar : null,
	getPagingToolbar : function() {
		if (this.pagingToolbar == null) {
			this.pagingToolbar = Ext.create('Deppon.StandardPaging', {
				store : this.store,
				pageSize : 20,
				prependButtons: true,
				defaults : {
					margin : '0 0 15 3'
				}
			});
		}
		return this.pagingToolbar;
	},
	//快递代理公司运价新增WINDOW
	flightPriceAddWindow:null,
	getFlightPriceAddWindow:function(){
		if (this.flightPriceAddWindow == null) {
			this.flightPriceAddWindow = Ext.create('Foss.pricing.PartbussPlanAddWindow');
			this.flightPriceAddWindow.parent = this;//父元素
		}
		return this.flightPriceAddWindow;
	},
	//修改快递代理公司运价WINDOW
	flightPriceUpdateWindow:null,
	getFlightPriceUpdateWindow:function(){
		if (this.flightPriceUpdateWindow == null) {
			this.flightPriceUpdateWindow = Ext.create('Foss.pricing.PartbussPlanUpdateWindow');
			this.flightPriceUpdateWindow.parent = this;//父元素
		}
		return this.flightPriceUpdateWindow;
	},
	//查看快递代理公司运价
	flightPriceShowWindow:null,
	getFlightPriceShowWindow:function(){
		if (this.flightPriceShowWindow == null) {
			this.flightPriceShowWindow = Ext.create('Foss.pricing.PartbussPlanShowWindow');
			this.flightPriceShowWindow.parent = this;//父元素
		}
		return this.flightPriceShowWindow;
	},
	//作废快递代理公司运价
	deleteFlightPrice: function(btn){
		var me = this;
		var selections = me.getSelectionModel().getSelection();//获取选中的数据
		if(selections.length<1){//判断是否至少选中了一条
			pricing.showWoringMessage(pricing.agencyPricePlan.i18n('foss.pricing.pleaseSelectVoidOperation'));//请选择一条进行作废操作！
			return;//没有则提示并返回
		}
		for(var i = 0;i<selections.length;i++){
			if(selections[i].get('active')==pricing.yes){
				pricing.showWoringMessage(pricing.agencyPricePlan.i18n('foss.pricing.pleaseChooseToNotActivateTheDataToBeBeleted'));//请选择未激活数据进行删除！
				return;//没有则提示并返回
			}
		}
		pricing.showQuestionMes(pricing.agencyPricePlan.i18n('i18n.pricingRegion.isDeleteTheseExpressDeliveryAgencyFreight'),function(e){//是否要删除这些快递代理运价？
			if(e=='yes'){//询问是否删除，是则发送请求
				var idList = new Array();//快递代理公司运价
				for(var i = 0 ; i<selections.length ; i++){
					idList.push(selections[i].get('id'));
				}
				var params = {'pricePlanVo':{'idList':idList}};
				var successFun = function(json){
					pricing.showInfoMes(json.message);
					me.getPagingToolbar().moveFirst();
				};
				var failureFun = function(json){
					if(Ext.isEmpty(json)){
						pricing.showErrorMes(pricing.agencyPricePlan.i18n('foss.pricing.requestTimedOut'));//请求超时
					}else{
						pricing.showErrorMes(json.message);
					}
				};
				var url = '../pricing/deleteFlightPriceA.action';
				pricing.requestJsonAjax(url,params,successFun,failureFun);
			}
		})
	},
	//激活快递代理公司运价
	activeFlightPrice: function(){
		var me = this;
		var selections = me.getSelectionModel().getSelection();//获取选中的数据
		if(selections.length<1){//判断是否至少选中了一条
			pricing.showWoringMessage(pricing.agencyPricePlan.i18n('foss.pricing.pleaseSelectAnActivateOperation'));//请选择一条进行激活操作！
			return;//没有则提示并返回
		}
		for(var i = 0;i<selections.length;i++){
			if(selections[i].get('active')==pricing.yes){
				pricing.showWoringMessage(pricing.agencyPricePlan.i18n('foss.pricing.pleaseSelectTheActivationDataForActivation'));//请选择未激活数据进行激活！
				return;//没有则提示并返回
			}
		}
		pricing.showQuestionMes(pricing.agencyPricePlan.i18n('i18n.pricingRegion.doYouWantActivateTheseExpressDeliveryAgencyFreight'),function(e){//是否要激活这些快递代理运价？
			if(e=='yes'){//询问是否删除，是则发送请求
				var idList = new Array();//快递代理公司运价
				for(var i = 0 ; i<selections.length ; i++){
					idList.push(selections[i].get('id'));
				}
				var params = {'pricePlanVo':{'idList':idList}};
				var successFun = function(json){
					pricing.showInfoMes(json.message);
					me.getPagingToolbar().moveFirst();
				};
				var failureFun = function(json){
					if(Ext.isEmpty(json)){
						pricing.showErrorMes(pricing.agencyPricePlan.i18n('foss.pricing.requestTimedOut'));//请求超时
					}else{
						pricing.showErrorMes(json.message);
					}
				};
				var url = '../pricing/activePartbussPlan.action';
				pricing.requestJsonAjax(url,params,successFun,failureFun);
			}
		})
	},
	
	
	  /**
     * 立即生效
     * 对于实际业务可能在当天发生两次以上的调整，故给予立即激中止功能用于支持该业务，
     * 1、立即中止功能给予特定角色来操作。根据所登陆的用户所属某某角色来决定是否给予立即中止功能。防止一般用户进行当天频繁调价操作
     * 2、立即中止功能中止日期可以等于今天但是不能小于今天。
     * 
     */
    immediatelyActiveFlightPriceWindow:null,
	getImmediatelyActiveFlightPriceWindow: function(flightPriceEntity){
		if(Ext.isEmpty(this.immediatelyActiveFlightPriceWindow)){
			this.immediatelyActiveFlightPriceWindow = Ext.create('Foss.pricing.flightPrice.FlightPriceImmediatelyActiveTimeWindow');
			this.immediatelyActiveFlightPriceWindow.parent = this;
		}
		this.immediatelyActiveFlightPriceWindow.flightPriceEntity = flightPriceEntity;
		return this.immediatelyActiveFlightPriceWindow;
	},
	
	
	/**
     * 立即中止
     * 对于实际业务可能在当天发生两次以上的调整，故给予立即激中止功能用于支持该业务，
     * 1、立即中止功能给予特定角色来操作。根据所登陆的用户所属某某角色来决定是否给予立即中止功能。防止一般用户进行当天频繁调价操作
     * 2、立即中止功能中止日期可以等于今天但是不能小于今天。
     * 
     */
    immediatelyStopFlightPriceWindow:null,
	getImmediatelyStopFlightPriceWindow: function(flightPriceEntity){
		if(Ext.isEmpty(this.immediatelyStopFlightPriceWindow)){
			this.immediatelyStopFlightPriceWindow = Ext.create('Foss.pricing.flightPrice.FlightPriceImmediatelyStopEndTimeWindow');
			this.immediatelyStopFlightPriceWindow.parent = this;
		}
		this.immediatelyStopFlightPriceWindow.flightPriceEntity = flightPriceEntity;
		return this.immediatelyStopFlightPriceWindow;
	},
	
	
	/**
	 * 立即中止
	 */
    immediatelyStopFlightPrice:function(){
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
	 	}else if(selections[0].get('endTime').getTime() < new Date().getTime()){
	 		pricing.agencyPricePlan.showWarningMsg('温馨提醒','该方案结束时间已经过期，不允许再立即中止！');
	 		return;
	 	}else{
	 		var flightPriceEntity = selections[0].data;
	 		var window = me.getImmediatelyStopFlightPriceWindow(flightPriceEntity);
	 		window.show()
	 	}
	},
	
	/**
	 * 立即激活
	 */
    immediatelyActiveFlightPrice:function(){
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
	 		var flightPriceEntity = selections[0].data;
	 		me.getImmediatelyActiveFlightPriceWindow(flightPriceEntity).show();
	 	}
	},
	
	uploadFlightPriceform : null,
    /**
     * 上传快递代理运价方案
     * @return {}
     */
    getUploadFlightPriceform: function(){
    	if(Ext.isEmpty(this.uploadFlightPriceform)){
			this.uploadFlightPriceform = Ext.create('Foss.pricing.agencyPricePlan.UploadFlightPriceform');	
		}
		return this.uploadFlightPriceform;
    },
    
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.columns = [{xtype: 'rownumberer',
			width:40,
			text : pricing.agencyPricePlan.i18n('i18n.pricingRegion.num')//序号
		},{
			text : pricing.agencyPricePlan.i18n('i18n.pricingRegion.opra'),//操作
			//dataIndex : 'id',
			xtype:'actioncolumn',
			align: 'center',
			width:80,
			items: [{
				iconCls: 'deppon_icons_edit',
                tooltip: pricing.agencyPricePlan.i18n('foss.pricing.update'),//修改
                disabled: !pricing.agencyPricePlan.isPermission('agencyPricePlan/updatebutton'),
				width:39,
				getClass : function (v, m, r, rowIndex) {
					if (r.get('active') === 'N') {
					    return 'deppon_icons_edit';
					} else {
					    return 'statementBill_hide';
					}
				},
                handler: function(grid, rowIndex, colIndex) {
                	var id = grid.getStore().getAt(rowIndex).get('id');
    				var params = {'pricePlanVo':{'partbussPlan':{'id':id}}};
    				var successFun = function(json){
    					var updateWindow = me.getFlightPriceUpdateWindow();
    					updateWindow.partbussPlan = json.pricePlanVo.partbussPlan;
    					updateWindow.show();
    					updateWindow.getFlightPriceDetailGrid().getStore().load();//加载明细数据
    				};
    				var failureFun = function(json){
    					if(Ext.isEmpty(json)){
    						pricing.showErrorMes(pricing.agencyPricePlan.i18n('foss.pricing.requestTimedOut'));//请求超时
    					}else{
    						pricing.showErrorMes(json.message);
    					}
    				};
    				var url = pricing.realPath('queryPartbussPlanById.action');
    				pricing.requestJsonAjax(url,params,successFun,failureFun);
                }
			},{
				iconCls: 'deppon_icons_softwareUpgrade',
                tooltip: pricing.agencyPricePlan.i18n('foss.pricing.upgradedVersion'),//升级版本
                disabled: !pricing.agencyPricePlan.isPermission('agencyPricePlan/upgradebutton'),
				width:39,
				getClass : function (v, m, r, rowIndex) {
					if (r.get('active') === 'Y') {
					    return 'deppon_icons_softwareUpgrade';
					} else {
					    return 'statementBill_hide';
					}
				},
                handler: function(grid, rowIndex, colIndex) {
                	var id = grid.getStore().getAt(rowIndex).get('id');
    				var params = {'pricePlanVo':{'partbussPlan':{'id':id}}};
    				var successFun = function(json){
    					grid.up().getPagingToolbar().moveFirst();
    					var updateWindow = me.getFlightPriceUpdateWindow();
    					updateWindow.partbussPlan = json.pricePlanVo.partbussPlan;
    					updateWindow.show();
    					updateWindow.getFlightPriceDetailGrid().getStore().load();//加载明细数据
    				};
    				var failureFun = function(json){
    					if(Ext.isEmpty(json)){
    						pricing.showErrorMes(pricing.agencyPricePlan.i18n('foss.pricing.requestTimedOut'));//请求超时
    					}else{
    						pricing.showErrorMes(json.message);
    					}
    				};
    				var url = pricing.realPath('copyPartbussPlan.action');
    				pricing.requestJsonAjax(url,params,successFun,failureFun);
                }
			},{
				iconCls: 'deppon_icons_showdetail',
                tooltip: pricing.agencyPricePlan.i18n('foss.pricing.details'),//查看详情
//                disabled: !pricing.flightPrice.isPermission('flightPrice/flightPriceDetailbutton'),
				width:39,
                handler: function(grid, rowIndex, colIndex) {
                	var id = grid.getStore().getAt(rowIndex).get('id');
    				var params = {'pricePlanVo':{'partbussPlan':{'id':id}}};
    				var successFun = function(json){
    					var showWindow = me.getFlightPriceShowWindow();
    					showWindow.partbussPlan = json.pricePlanVo.partbussPlan;
    					showWindow.show();
    					showWindow.getQueryFlightPriceDetailGrid().getStore().load();//加载明细数据
    				};
    				var failureFun = function(json){
    					if(Ext.isEmpty(json)){
    						pricing.showErrorMes(pricing.agencyPricePlan.i18n('foss.pricing.requestTimedOut'));//请求超时
    					}else{
    						pricing.showErrorMes(json.message);
    					}
    				};
    				var url = pricing.realPath('queryPartbussPlanById.action');
    				pricing.requestJsonAjax(url,params,successFun,failureFun);
                }
			}]
		},{
		    text : pricing.agencyPricePlan.i18n('i18n.pricingRegion.expressDeliveryAgencyCompany'),//快递代理公司
			dataIndex : 'expressPartbussName'
		},{
			text : pricing.agencyPricePlan.i18n('foss.pricing.status'),//状态
			dataIndex : 'active',
			width:50,
			renderer:function(value){
				if(value==pricing.yes){//'Y'表示激活
					return pricing.agencyPricePlan.i18n('i18n.pricingRegion.active');
				}else if(value==pricing.no){//'N'表示未激活
					return  pricing.agencyPricePlan.i18n('i18n.pricingRegion.unActive');
				}else{
					return '';
				}
			}
		},{
			text : pricing.agencyPricePlan.i18n('foss.pricing.stowageDepartment'),//配载部门
			dataIndex : 'loadOrgName'
		},{
			text : pricing.agencyPricePlan.i18n('foss.pricing.createUser'),//创建人
			dataIndex : 'createUserName',
			width:60
		},{
			text : pricing.agencyPricePlan.i18n('foss.pricing.modifyUser'),//修改人
			dataIndex : 'modifyUserName',
			width:60
		},{
			text : pricing.agencyPricePlan.i18n('foss.pricing.createTime'),//创建时间
			dataIndex : 'createDate',
			renderer:function(value){
				return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
			}
		},{
			text : pricing.agencyPricePlan.i18n('foss.pricing.startTime'),//开始时间
			dataIndex : 'beginTime',
			renderer:function(value){
				return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
			}
		},{
			text : pricing.agencyPricePlan.i18n('foss.pricing.endTime'),//结束时间
			dataIndex : 'endTime',
			renderer:function(value){
				return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
			}
		},{
			text : '是否当前版本',
			dataIndex : 'currentUsedVersion',
			flex : 1
		}];
		me.store = Ext.create('Foss.pricing.PartbussPlanStore',{
			autoLoad : false,//不自动加载
			pageSize : 20,
			listeners: {
				beforeload: function(store, operation, eOpts){
					var queryForm = me.up().getQueryFlightPriceForm();
					if(queryForm!=null){
						Ext.apply(operation,{
							params : {//快递代理公司运价大查询，查询条件组织
								'pricePlanVo.partbussPlan.expressPartbussCode':queryForm.getForm().findField('expressPartbussCode').getValue(),//快递代理公司CODE
								'pricePlanVo.partbussPlan.active':queryForm.getForm().findField('active').getValue(),//状态
								'pricePlanVo.partbussPlan.billDate':queryForm.getForm().findField('billDate').getValue()//结束时间
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
		me.tbar = [{
			text : pricing.agencyPricePlan.i18n('i18n.pricingRegion.add'),//新增
			disabled: !pricing.agencyPricePlan.isPermission('agencyPricePlan/addbutton'),
			hidden: !pricing.agencyPricePlan.isPermission('agencyPricePlan/addbutton'),
			handler :function(){
				me.getFlightPriceAddWindow().show();
			} 
		},'-',{
			text : pricing.agencyPricePlan.i18n('i18n.pricingRegion.active'),//激活
			disabled: !pricing.agencyPricePlan.isPermission('agencyPricePlan/activebutton'),
			hidden: !pricing.agencyPricePlan.isPermission('agencyPricePlan/activebutton'),
			handler :function(){
				me.activeFlightPrice();
			} 			
		},'-',{
			text : pricing.agencyPricePlan.i18n('foss.pricing.delete'),//删除
			disabled: !pricing.agencyPricePlan.isPermission('agencyPricePlan/deletebutton'),
			hidden: !pricing.agencyPricePlan.isPermission('agencyPricePlan/deletebutton'),
			handler :function(){
				me.deleteFlightPrice();
			} 
		},'-',{
			text : pricing.agencyPricePlan.i18n('foss.pricing.immediatelyActivationProgram'),//'立即激活',
			disabled:!pricing.agencyPricePlan.isPermission('agencyPricePlan/immediatelyActivebutton'),
			hidden:!pricing.agencyPricePlan.isPermission('agencyPricePlan/immediatelyActivebutton'),
			handler :function(){
				me.immediatelyActiveFlightPrice();
			} 
		},'-', {
			text : pricing.agencyPricePlan.i18n('foss.pricing.immediatelyStopProgram'),//'立即中止',
			disabled:!pricing.agencyPricePlan.isPermission('agencyPricePlan/immediatelyStopbutton'),
			hidden:!pricing.agencyPricePlan.isPermission('agencyPricePlan/immediatelyStopbutton'),
			handler :function(){
				me.immediatelyStopFlightPrice();
			} 
		},'-', {
			text : pricing.agencyPricePlan.i18n('foss.pricing.import'), //'导入',
//			hidden:!pricing.flightPrice.isPermission('flightPrice/flightPriceImportbutton'),
			handler :function(){
			 	me.getUploadFlightPriceform().show();
			} 
		}];
		me.bbar = me.getPagingToolbar();
		pricing.pagingBar = me.bbar;
		me.callParent([cfg]);
	}
});
/**
 * @description 快递代理运价方案管理主页
 */
Ext.onReady(function() {
	Ext.QuickTips.init();
	if (Ext.getCmp('T_pricing-agencyPricePlanIndex_content')) {
		return;
	};
	pricing.queryAllGoodsType();
	var queryFlightPriceForm = Ext.create('Foss.pricing.QueryPartbussPlanForm');//查询FORM
	var flightPriceGrid = Ext.create('Foss.pricing.PartbussPlanGrid');//查询结果GRID
	Ext.getCmp('T_pricing-agencyPricePlanIndex').add(Ext.create('Ext.panel.Panel', {
	  	id : 'T_pricing-agencyPricePlanIndex_content',
		cls : 'panelContentNToolbar',
		bodyCls : 'panelContentNToolbar-body',
		//获得查询FORM
		getQueryFlightPriceForm : function() {
			return queryFlightPriceForm;
		},
		//获得查询结果GRID
		getFlightPriceGrid : function() {
			return flightPriceGrid;
		},
		items : [queryFlightPriceForm, flightPriceGrid]
	}));
});


//----------------------------------------------上面是整体布局，下面是弹出窗口----------------------------------
/**
 * 快递代理网点运价信息列表
 */
Ext.define('Foss.pricing.OutbranchPlanGrid', {
	extend: 'Ext.grid.Panel',
	title : pricing.agencyPricePlan.i18n('i18n.pricingRegion.expressDeliveryAgencyWebSiteFreightInfoList'),//快递代理网点运价信息列表
	frame: true,
	height :300,	
    sortableColumns:false,
    enableColumnHide:false,
    enableColumnMove:false,
	stripeRows : true, // 交替行效果
	selType : "rowmodel", // 选择类型设置为：行选择
	emptyText: pricing.agencyPricePlan.i18n('foss.pricing.theQueryResultIsEmpty'),//查询结果为空
	//得到bbar
	pagingToolbar : null,
	getPagingToolbar : function() {
		if (this.pagingToolbar == null) {
			this.pagingToolbar = Ext.create('Deppon.StandardPaging', {
				store : this.store,
				pageSize : 10
			});
		}
		return this.pagingToolbar;
	},
	//快递代理公司运价明细新增WINDOW
	flightPriceDetailAddWindow:null,
	getFlightPriceDetailAddWindow:function(){
		if (this.flightPriceDetailAddWindow == null) {
			this.flightPriceDetailAddWindow = Ext.create('Foss.pricing.OutbranchPlanAddWindow');
			this.flightPriceDetailAddWindow.parent = this;//父元素
		}
		return this.flightPriceDetailAddWindow;
	},
	//修改快递代理公司运价WINDOW
	flightPriceDetailUpdateWindow:null,
	getFlightPriceDetailUpdateWindow:function(){
		if (this.flightPriceDetailUpdateWindow == null) {
			this.flightPriceDetailUpdateWindow = Ext.create('Foss.pricing.OutbranchPlanUpdateWindow');
			this.flightPriceDetailUpdateWindow.parent = this;//父元素
		}
		return this.flightPriceDetailUpdateWindow;
	},
	//删除快递代理公司运价明细
	deleteFlightPriceDetail: function(){
		var me = this;
		var selections = me.getSelectionModel().getSelection();//获取选中的数据
		if(selections.length<1){//判断是否至少选中了一条
			pricing.showWoringMessage(pricing.agencyPricePlan.i18n('foss.pricing.pleaseSelectOneDeleteOperation'));//请选择一条进行删除操作！
			return;//没有则提示并返回
		}
		pricing.showQuestionMes(pricing.agencyPricePlan.i18n('i18n.pricingRegion.isDeleteTheseExpressDeliveryAgencyWebSiteFreightPlan'),function(e){//是否要删除这些快递代理网点运价方案？
			if(e=='yes'){//询问是否删除，是则发送请求
				var idList = new Array();//快递代理公司运价
				for(var i = 0 ; i<selections.length ; i++){
					idList.push(selections[i].get('id'));
				}
				var params = {'pricePlanVo':{'idList':idList}};
				var successFun = function(json){
					pricing.showInfoMes(json.message);
					me.getPagingToolbar().moveFirst();
				};
				var failureFun = function(json){
					if(Ext.isEmpty(json)){
						pricing.showErrorMes(pricing.agencyPricePlan.i18n('foss.pricing.requestTimedOut'));//请求超时
					}else{
						pricing.showErrorMes(json.message);
					}
				};
				var url = pricing.realPath('deleteoutbranchPlan.action');
				pricing.requestJsonAjax(url,params,successFun,failureFun);
			}
		})
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.columns = [{xtype: 'rownumberer',
			width:40,
			text : pricing.agencyPricePlan.i18n('i18n.pricingRegion.num')//序号
		},{
			text : pricing.agencyPricePlan.i18n('i18n.pricingRegion.opra'),//操作
			//dataIndex : 'id',
			xtype:'actioncolumn',
			align: 'center',
			hidden:config.isShow,
			width:42,
			items: [{
				iconCls: 'deppon_icons_edit',
                tooltip: pricing.agencyPricePlan.i18n('foss.pricing.update'),//修改
				width:42,
                handler: function(grid, rowIndex, colIndex) {
                	var id = grid.getStore().getAt(rowIndex).get('id');
    				var params = {'pricePlanVo':{'outbranchPlan':{'id':id}}};
    				var successFun = function(json){
    					var updateWindow = me.getFlightPriceDetailUpdateWindow();
    					updateWindow.outbranchPlan = json.pricePlanVo.outbranchPlan;
    					updateWindow.show();
    					updateWindow.getOubrPlanDetailGrid().getStore().load();//加载明细数据
    				};
    				var failureFun = function(json){
    					if(Ext.isEmpty(json)){
    						pricing.showErrorMes(pricing.agencyPricePlan.i18n('foss.pricing.requestTimedOut'));//请求超时
    					}else{
    						pricing.showErrorMes(json.message);
    					}
    				};
    				var url = pricing.realPath('queryOutbranchPlanById.action');
    				pricing.requestJsonAjax(url,params,successFun,failureFun);
                }
			}]
		},{
			text : pricing.agencyPricePlan.i18n('i18n.pricingRegion.expressDeliveryAgencyCompanyWebSite'),//快递代理公司网点
			dataIndex : 'outerBranchName'
		},{
			text : '方案类型',//方案类型
			dataIndex : 'billType',
			renderer:function(value){
			    if('FX' == value){
			    	return '固定价格';
			    }else if('AW' == value){
			        return '步进价格';
			    }
			}
		},{
			text : '省份',//省份
			dataIndex : 'provName'
		},{
			text : '城市',//城市
			dataIndex : 'cityName'
		},{
			text : '区县',//区县
			dataIndex : 'districtName'
		}];
		me.store = Ext.create('Foss.pricing.OutbranchPlanStore',{
			autoLoad : false,//不自动加载
			pageSize : 10,
			listeners: {
				beforeload: function(store, operation, eOpts){
					var flightPriceId = me.up('window').partbussPlan.id;
					Ext.apply(operation,{
						params : { 
							'pricePlanVo.outbranchPlan.expressPartbussPlanId':flightPriceId//快递代理运价ID
						}
					});	
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
		me.tbar = [{
			text : pricing.agencyPricePlan.i18n('i18n.pricingRegion.add'),//新增
			handler :function(){
				me.getFlightPriceDetailAddWindow().show();
			} 
		},'-',{
			text : pricing.agencyPricePlan.i18n('foss.pricing.delete'),//删除
			handler :function(){
				me.deleteFlightPriceDetail();
			} 
		}];
		me.bbar = me.getPagingToolbar();
		me.callParent([cfg]);
	}
});


/**
 * 快递代理网点运价明细列表
 */
Ext.define('Foss.pricing.OubrPlanDetailGrid', {
	extend: 'Ext.grid.Panel',
	title : pricing.agencyPricePlan.i18n('i18n.pricingRegion.expressDeliveryAgencyWebSiteFreightDetailList'),//快递代理网点运价明细列表
	frame: true,
	height :300,	
    sortableColumns:false,
    enableColumnHide:false,
    enableColumnMove:false,
	stripeRows : true, // 交替行效果
	selType : "rowmodel", // 选择类型设置为：行选择
	emptyText: pricing.agencyPricePlan.i18n('foss.pricing.theQueryResultIsEmpty'),//查询结果为空
	//得到bbar
	pagingToolbar : null,
	getPagingToolbar : function() {
		if (this.pagingToolbar == null) {
			this.pagingToolbar = Ext.create('Deppon.StandardPaging', {
				store : this.store,
				pageSize : 10
			});
		}
		return this.pagingToolbar;
	},
	//新增快递代理网点运价明细新增WINDOW
	oubrPlanDetailAddWindow:null,
	getOubrPlanDetailAddWindow:function(){
		if (this.oubrPlanDetailAddWindow == null) {
			this.oubrPlanDetailAddWindow = Ext.create('Foss.pricing.OubrPlanDetailAddWindow');
			this.oubrPlanDetailAddWindow.parent = this;//父元素
		}
		return this.oubrPlanDetailAddWindow;
	},
	//修改快递代理网点运价明细WINDOW
	oubrPlanDetailUpdateWindow:null,
	getOubrPlanDetailUpdateWindow:function(){
		if (this.oubrPlanDetailUpdateWindow == null) {
			this.oubrPlanDetailUpdateWindow = Ext.create('Foss.pricing.OubrPlanDetailUpdateWindow');
			this.oubrPlanDetailUpdateWindow.parent = this;//父元素
		}
		return this.oubrPlanDetailUpdateWindow;
	},
	//删除快递代理网点运价明细
	deleteFlightPriceDetail: function(){
		var me = this;
		var selections = me.getSelectionModel().getSelection();//获取选中的数据
		if(selections.length<1){//判断是否至少选中了一条
			pricing.showWoringMessage(pricing.agencyPricePlan.i18n('foss.pricing.pleaseSelectOneDeleteOperation'));//请选择一条进行删除操作！
			return;//没有则提示并返回
		}
		pricing.showQuestionMes(pricing.agencyPricePlan.i18n('i18n.pricingRegion.isDeleteTheseExpressDeliveryAgencyWebSiteFreightDetail'),function(e){//是否要删除这些快递代理网点运价明细？
			if(e=='yes'){//询问是否删除，是则发送请求
				var idList = new Array();//快递代理公司运价
				for(var i = 0 ; i<selections.length ; i++){
					idList.push(selections[i].get('id'));
				}
				var params = {'pricePlanVo':{'idList':idList}};
				var successFun = function(json){
					pricing.showInfoMes(json.message);
					me.getPagingToolbar().moveFirst();
				};
				var failureFun = function(json){
					if(Ext.isEmpty(json)){
						pricing.showErrorMes(pricing.agencyPricePlan.i18n('foss.pricing.requestTimedOut'));//请求超时
					}else{
						pricing.showErrorMes(json.message);
					}
				};
				var url = pricing.realPath('deleteOubrPlanDetail.action');
				pricing.requestJsonAjax(url,params,successFun,failureFun);
			}
		})
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.columns = [{
			text : pricing.agencyPricePlan.i18n('i18n.pricingRegion.opra'),//操作
			//dataIndex : 'id',
			xtype:'actioncolumn',
			align: 'center',
			hidden:config.isShow,
			width:42,
			items: [{
				iconCls: 'deppon_icons_edit',
                tooltip: pricing.agencyPricePlan.i18n('foss.pricing.update'),//修改
				width:42,
                handler: function(grid, rowIndex, colIndex) {
                	var id = grid.getStore().getAt(rowIndex).get('id');
    				var params = {'pricePlanVo':{'oubrPlanDetail':{'id':id}}};
    				var successFun = function(json){
    					var updateWindow = me.getOubrPlanDetailUpdateWindow();
    					updateWindow.oubrPlanDetail = json.pricePlanVo.oubrPlanDetail;
    					updateWindow.show();
    				};
    				var failureFun = function(json){
    					if(Ext.isEmpty(json)){
    						pricing.showErrorMes(pricing.agencyPricePlan.i18n('foss.pricing.requestTimedOut'));//请求超时
    					}else{
    						pricing.showErrorMes(json.message);
    					}
    				};
    				var url = pricing.realPath('queryOubrPlanDetailById.action');
    				pricing.requestJsonAjax(url,params,successFun,failureFun);
                }
			}]
		},{
			xtype: 'rownumberer',
			width:60,
			text : '重量等级'//重量等级
		},{
			text : '重量下线（kg）',//重量下线（kg）
			dataIndex : 'leftrange'
		},{
			text : '重量上线（kg）',//重量上线（kg）
			dataIndex : 'rightrange'
		},{
			text : '量纲（kg）',//重量上线（kg）
			dataIndex : 'dimension'
		},{
			text : '价格（元/票）',//价格（元/票）
			dataIndex : 'fee'
		},{
			text : '价格（元/量纲）',//价格（元/量纲）
			dataIndex : 'fee'
		}];
		me.store = Ext.create('Foss.pricing.OubrPlanDetailGridStore',{
			autoLoad : false,//不自动加载
			pageSize : 10,
			listeners: {
				beforeload: function(store, operation, eOpts){
					var flightPriceId = me.up('window').outbranchPlan.id;
					Ext.apply(operation,{
						params : { 
							'pricePlanVo.oubrPlanDetail.expressOutbranchPlanId':flightPriceId//快递代理运价ID
						}
					});	
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
		me.tbar = [{
			text : pricing.agencyPricePlan.i18n('i18n.pricingRegion.add'),//新增
			handler :function(){
				me.getOubrPlanDetailAddWindow().show();
			} 
		},'-',{
			text : pricing.agencyPricePlan.i18n('foss.pricing.delete'),//删除
			handler :function(){
				me.deleteFlightPriceDetail();
			} 
		}];
		me.dockedItems =[{
	   		xtype: 'toolbar',
		    dock: 'bottom',
		    layout:'column',
		    style:{
		    	backgroundColor:'#FFFFFF'
		    },
		    items: [{
				xtype:'label',
				forId: 'myFieldId',
		        text: '* 温馨提醒：重量区段新增时必须从低到高，删除时必须从高到低删除!',
		        margin: '0 0 0 10',
		        style: {
		            color: '#FF0000'
		        }
			}]
		}];	
		me.bbar = me.getPagingToolbar();
		me.callParent([cfg]);
	}
});

/**
 * 快递代理网点运价明细列表
 */
Ext.define('Foss.pricing.QueryOubrPlanDetailGrid', {
	extend: 'Ext.grid.Panel',
	title : pricing.agencyPricePlan.i18n('i18n.pricingRegion.expressDeliveryAgencyWebSiteFreightDetailList'),//快递代理网点运价明细列表
	frame: true,
	height :300,	
    sortableColumns:false,
    enableColumnHide:false,
    enableColumnMove:false,
	stripeRows : true, // 交替行效果
	selType : "rowmodel", // 选择类型设置为：行选择
	emptyText: pricing.agencyPricePlan.i18n('foss.pricing.theQueryResultIsEmpty'),//查询结果为空
	//得到bbar
	pagingToolbar : null,
	getPagingToolbar : function() {
		if (this.pagingToolbar == null) {
			this.pagingToolbar = Ext.create('Deppon.StandardPaging', {
				store : this.store,
				pageSize : 10
			});
		}
		return this.pagingToolbar;
	},
	//新增快递代理网点运价明细新增WINDOW
	oubrPlanDetailAddWindow:null,
	getOubrPlanDetailAddWindow:function(){
		if (this.oubrPlanDetailAddWindow == null) {
			this.oubrPlanDetailAddWindow = Ext.create('Foss.pricing.OubrPlanDetailAddWindow');
			this.oubrPlanDetailAddWindow.parent = this;//父元素
		}
		return this.oubrPlanDetailAddWindow;
	},
	//修改快递代理网点运价明细WINDOW
	oubrPlanDetailUpdateWindow:null,
	getOubrPlanDetailUpdateWindow:function(){
		if (this.oubrPlanDetailUpdateWindow == null) {
			this.oubrPlanDetailUpdateWindow = Ext.create('Foss.pricing.OubrPlanDetailUpdateWindow');
			this.oubrPlanDetailUpdateWindow.parent = this;//父元素
		}
		return this.oubrPlanDetailUpdateWindow;
	},
	//删除快递代理网点运价明细
	deleteFlightPriceDetail: function(){
		var me = this;
		var selections = me.getSelectionModel().getSelection();//获取选中的数据
		if(selections.length<1){//判断是否至少选中了一条
			pricing.showWoringMessage(pricing.agencyPricePlan.i18n('foss.pricing.pleaseSelectOneDeleteOperation'));//请选择一条进行删除操作！
			return;//没有则提示并返回
		}
		pricing.showQuestionMes(pricing.agencyPricePlan.i18n('i18n.pricingRegion.isDeleteTheseExpressDeliveryAgencyWebSiteFreightDetail'),function(e){//是否要删除这些快递代理网点运价明细？
			if(e=='yes'){//询问是否删除，是则发送请求
				var idList = new Array();//快递代理公司运价
				for(var i = 0 ; i<selections.length ; i++){
					idList.push(selections[i].get('id'));
				}
				var params = {'pricePlanVo':{'idList':idList}};
				var successFun = function(json){
					pricing.showInfoMes(json.message);
					me.getPagingToolbar().moveFirst();
				};
				var failureFun = function(json){
					if(Ext.isEmpty(json)){
						pricing.showErrorMes(pricing.agencyPricePlan.i18n('foss.pricing.requestTimedOut'));//请求超时
					}else{
						pricing.showErrorMes(json.message);
					}
				};
				var url = pricing.realPath('deleteOubrPlanDetail.action');
				pricing.requestJsonAjax(url,params,successFun,failureFun);
			}
		})
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.columns = [{
			xtype: 'rownumberer',
			width:60,
			text : '重量等级'//重量等级
		},{
			text : '重量下线（kg）',//重量下线（kg）
			dataIndex : 'leftrange'
		},{
			text : '重量上线（kg）',//重量上线（kg）
			dataIndex : 'rightrange'
		},{
			text : '量纲（kg）',//重量上线（kg）
			dataIndex : 'dimension'
		},{
			text : '价格（元/票）',//价格（元/票）
			dataIndex : 'fee'
		},{
			text : '价格（元/量纲）',//价格（元/量纲）
			dataIndex : 'fee'
		}];
		me.store = Ext.create('Foss.pricing.OubrPlanDetailGridStore',{
			autoLoad : false,//不自动加载
			pageSize : 10,
			listeners: {
				beforeload: function(store, operation, eOpts){
					var flightPriceId = me.up('window').outbranchPlan.id;
					Ext.apply(operation,{
						params : { 
							'pricePlanVo.oubrPlanDetail.expressOutbranchPlanId':flightPriceId//快递代理运价ID
						}
					});	
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
		me.bbar = me.getPagingToolbar();
		me.callParent([cfg]);
	}
});



/**
 * 快递代理公司运价明细列表
 */
Ext.define('Foss.pricing.QueryFlightPriceDetailGrid', {
	extend: 'Ext.grid.Panel',
	title : pricing.agencyPricePlan.i18n('i18n.pricingRegion.expressDeliveryAgencyWebSiteFreightPlanInfoList'),//快递代理网点运价方案信息列表
	frame: true,
	height :300,
    sortableColumns:false,
    enableColumnHide:false,
    enableColumnMove:false,
	stripeRows : true, // 交替行效果
	selType : "rowmodel", // 选择类型设置为：行选择
	emptyText: pricing.agencyPricePlan.i18n('foss.pricing.theQueryResultIsEmpty'),//查询结果为空
	//得到bbar
	pagingToolbar : null,
	getPagingToolbar : function() {
		if (this.pagingToolbar == null) {
			this.pagingToolbar = Ext.create('Deppon.StandardPaging', {
				store : this.store,
				pageSize : 10
			});
		}
		return this.pagingToolbar;
	},
	//查看快递代理公司运价WINDOW
	flightPriceDetailUpdateWindow:null,
	getFlightPriceDetailUpdateWindow:function(){
		if (this.flightPriceDetailUpdateWindow == null) {
			this.flightPriceDetailUpdateWindow = Ext.create('Foss.pricing.OutbranchPlanQueryWindow');
			this.flightPriceDetailUpdateWindow.parent = this;//父元素
		}
		return this.flightPriceDetailUpdateWindow;
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.columns = [{xtype: 'rownumberer',
			width:40,
			text : pricing.agencyPricePlan.i18n('i18n.pricingRegion.num')//序号
		},{
			text : pricing.agencyPricePlan.i18n('i18n.pricingRegion.opra'),//操作
			xtype:'actioncolumn',
			align: 'center',
//			hidden:config.isShow,
			width:42,
			items: [{
				iconCls: 'deppon_icons_showdetail',
                tooltip: '查看明细',//修改
				width:42,
                handler: function(grid, rowIndex, colIndex) {
                	var id = grid.getStore().getAt(rowIndex).get('id');
    				var params = {'pricePlanVo':{'outbranchPlan':{'id':id}}};
    				var successFun = function(json){
    					var updateWindow = me.getFlightPriceDetailUpdateWindow();
    					updateWindow.outbranchPlan = json.pricePlanVo.outbranchPlan;
    					updateWindow.show();
    					updateWindow.getOubrPlanDetailGrid().getStore().load();//加载明细数据
    				};
    				var failureFun = function(json){
    					if(Ext.isEmpty(json)){
    						pricing.showErrorMes(pricing.agencyPricePlan.i18n('foss.pricing.requestTimedOut'));//请求超时
    					}else{
    						pricing.showErrorMes(json.message);
    					}
    				};
    				var url = pricing.realPath('queryOutbranchPlanById.action');
    				pricing.requestJsonAjax(url,params,successFun,failureFun);
                }
			}]
		},{
			text : pricing.agencyPricePlan.i18n('i18n.pricingRegion.expressDeliveryAgencyCompanyWebSite'),//快递代理公司网点
			dataIndex : 'outerBranchName'
		},{
			text : '方案类型',//方案类型
			dataIndex : 'billType',
			renderer:function(value){
			    if('FX' == value){
			    	return '固定价格';
			    }else if('AW' == value){
			        return '步进价格';
			    }
			}
		},{
			text : '省份',//省份
			dataIndex : 'provName'
		},{
			text : '城市',//城市
			dataIndex : 'cityName'
		},{
			text : '区县',//区县
			dataIndex : 'districtName'
		}];
		me.store = Ext.create('Foss.pricing.OutbranchPlanStore',{
			autoLoad : false,//不自动加载
			pageSize : 10,
			listeners: {
				beforeload: function(store, operation, eOpts){
					var flightPriceId = me.up('window').partbussPlan.id;
					var form1 = me.up('window').getQueryOutBranchPlanForm().getForm();
					Ext.apply(operation,{
						params : { 
							'pricePlanVo.outbranchPlan.expressPartbussPlanId':flightPriceId,//快递代理运价ID
							'pricePlanVo.outbranchPlan.outerBranchCode':form1.findField('outerBranchCode').getValue(),//快递代理网点CODE
							'pricePlanVo.outbranchPlan.cityCode':form1.findField('cityCode').getValue(),//快递代理网点CODE
							'pricePlanVo.outbranchPlan.districtCode':form1.findField('districtCode').getValue()//快递代理网点CODE
						}
					});	
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
		me.bbar = me.getPagingToolbar();
		me.callParent([cfg]);
	}
});

/**
 * 新增快递代理公司运价信息
 */
Ext.define('Foss.pricing.PartbussPlanAddWindow',{
	extend : 'Ext.window.Window',
	title :pricing.agencyPricePlan.i18n('i18n.pricingRegion.addExpressDeliveryAgencyCompanyFreight'),//新增快递代理公司运价
	closable : true,
    parent:null,//父元素（弹出这个window的gird——Foss.pricing.PartbussPlanGrid）
	modal : true,
	partbussPlan:null,
	resizable:true,//可以调整窗口的大小
	closeAction : 'hide',//点击关闭是隐藏窗口
	width :750,
	height :700,
	listeners:{
		beforehide:function(me){//隐藏WINDOW的时候清除数据
			me.getFlightPriceForm().getForm().reset();//表格重置
			me.getFlightPriceDetailGrid().getStore().removeAll();
    		me.getFlightPriceDetailGrid().getDockedItems()[1].items.items[0].setDisabled(true);//grid的新增按钮不可用
    		me.getFlightPriceDetailGrid().getDockedItems()[1].items.items[2].setDisabled(true);//grid的删除按钮不可用
    		me.getFlightPriceForm().getDockedItems()[1].items.items[1].setDisabled(false);//form的重置按钮可用
    		me.getFlightPriceForm().getDockedItems()[1].items.items[2].setDisabled(false);//form的保存按钮可用
    		me.getFlightPriceForm().getForm().getFields().each(function(item){
    			item.setReadOnly(false);
    		});
		},
		beforeshow:function(me){//显示WINDOW的时候清除数据
			var datet = new Date(2999, 11, 31, 23, 59, 59);
			me.getFlightPriceForm().getForm().findField('endTime').setValue(me.dateFormat(datet,'Y-m-d H:i:s'));
		}
	},
	//新增快递代理公司运价FORM
	flightPriceForm:null,
	getFlightPriceForm : function(){
    	if(Ext.isEmpty(this.flightPriceForm)){
    		this.flightPriceForm = Ext.create('Foss.pricing.PartbussPlanAddForm',{
    			'isUpdate':false
    		});
    	}
    	return this.flightPriceForm;
    },
    dateFormat:function(value,format){
		if(!Ext.isEmpty(value)){
			return Ext.Date.format(new Date(value), format);
		}else{
			return null;
		}
	},
    //快递代理公司网点运价查询条件FORM
    queryOutBranchPlanForm:null,
	getQueryOutBranchPlanForm : function(){
    	if(Ext.isEmpty(this.queryOutBranchPlanForm)){
    		this.queryOutBranchPlanForm = Ext.create('Foss.pricing.QueryOutBranchPlanForm');
    	}
    	return this.queryOutBranchPlanForm;
    },
     //新增快递代理运价明细GRID
    flightPriceDetailGrid:null,
	getFlightPriceDetailGrid : function(){
    	if(Ext.isEmpty(this.flightPriceDetailGrid)){
    		this.flightPriceDetailGrid = Ext.create('Foss.pricing.OutbranchPlanGrid',{
    			'isShow':false
    		});
    		this.flightPriceDetailGrid.getDockedItems()[0].items.items[0].setDisabled(true);//grid的新增按钮不可用
    		this.flightPriceDetailGrid.getDockedItems()[0].items.items[2].setDisabled(true);//grid的删除按钮不可用
    	}
    	return this.flightPriceDetailGrid;
    },
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [me.getFlightPriceForm(),me.getFlightPriceDetailGrid()];
		me.callParent([cfg]);
	}
});

/**
 * 快递代理公司运价详细信息
 */
Ext.define('Foss.pricing.PartbussPlanShowWindow',{
	extend : 'Ext.window.Window',
	title : pricing.agencyPricePlan.i18n('i18n.pricingRegion.expressDeliveryAgencyCompanyFreightPlanInfo'),//快递代理公司运价方案信息
	closable : true,
    parent:null,//父元素（弹出这个window的gird——Foss.pricing.PartbussPlanGrid）
	modal : true,
	partbussPlan:null,
	resizable:true,//可以调整窗口的大小
	closeAction : 'hide',//点击关闭是隐藏窗口
	width :900,
	height :850,
	listeners:{
		beforehide:function(me){//隐藏WINDOW的时候清除数据
			me.getQueryOutBranchPlanForm().getForm().reset();//表格重置
			me.getQueryFlightPriceDetailGrid().getStore().removeAll();
		},
		beforeshow:function(me){//显示WINDOW的时候清除数据
			var record = new Foss.pricing.PartbussPlanEntity(me.partbussPlan);
			me.getFlightPriceForm().getForm().loadRecord(record);
			me.getFlightPriceForm().getForm().findField('expressPartbussCode').setCombValue(me.partbussPlan.expressPartbussName,me.partbussPlan.expressPartbussCode);
			me.getFlightPriceForm().getForm().findField('loadOrgCode').setCombValue(me.partbussPlan.loadOrgName,me.partbussPlan.loadOrgCode);
			me.getFlightPriceForm().getForm().findField('beginTime').setValue(me.dateFormat(record.get('beginTime'),'Y-m-d H:i:s'));
			me.getFlightPriceForm().getForm().findField('endTime').setValue(me.dateFormat(record.get('endTime'),'Y-m-d H:i:s'));
			if (!Ext.isEmpty(record.get('weightRule')))
				me.getFlightPriceForm().getForm().findField('weightRule').setValue(pricing.agencyPricePlan.i18n('i18n.pricingRegion.' + (record.get('weightRule')).toLowerCase()));
		}
	},
	//新增快递代理公司运价FORM
	flightPriceForm:null,
	getFlightPriceForm : function(){
    	if(Ext.isEmpty(this.flightPriceForm)){
    		this.flightPriceForm = Ext.create('Foss.pricing.QueryPartbussPlanForm');
    		this.flightPriceForm.getForm().getFields().each(function(item){
    			item.setReadOnly(true);
    		});//全部设置为只读
    		this.flightPriceForm.getDockedItems()[0].hide();//隐藏按钮
    	}
    	return this.flightPriceForm;
    },
    dateFormat:function(value,format){
		if(!Ext.isEmpty(value)){
			return Ext.Date.format(new Date(value), format);
		}else{
			return null;
		}
	},
     //新增快递代理运价明细GRID
    queryFlightPriceDetailGrid:null,
	getQueryFlightPriceDetailGrid : function(){
    	if(Ext.isEmpty(this.queryFlightPriceDetailGrid)){
    		this.queryFlightPriceDetailGrid = Ext.create('Foss.pricing.QueryFlightPriceDetailGrid');
    	}
    	return this.queryFlightPriceDetailGrid;
    },
     //查询快递代理网点条件
    queryOutBranchPlanForm:null,
	getQueryOutBranchPlanForm : function(){
    	if(Ext.isEmpty(this.queryOutBranchPlanForm)){
    		this.queryOutBranchPlanForm = Ext.create('Foss.pricing.QueryOutBranchPlanForm');
    	}
    	return this.queryOutBranchPlanForm;
    },
    
    //组件构造器
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [me.getFlightPriceForm(),me.getQueryOutBranchPlanForm(),me.getQueryFlightPriceDetailGrid()];  
		me.callParent([cfg]);
	}
});

/**
 * 修改快递代理公司运价
 */
Ext.define('Foss.pricing.PartbussPlanUpdateWindow',{
	extend : 'Ext.window.Window',
	title : pricing.agencyPricePlan.i18n('i18n.pricingRegion.updateExpressDeliveryAgencyCompanyFreight'),//修改快递代理公司运价
	closable : true,
	modal : true,
	resizable:false,
	partbussPlan:null,//修改快递代理公司运价数据
	parent:null,//父元素（弹出这个window的gird——Foss.pricing.SiteGroupGrid）
	closeAction : 'hide',
	width :750,
	height :700,
	listeners:{
		beforehide:function(me){
			me.getFlightPriceForm().getForm().reset();//表格重置
			me.getFlightPriceForm().getForm().getFields().each(function(item){
				item.setReadOnly(false);
			});
			me.getFlightPriceDetailGrid().getStore().removeAll();
		},
		beforeshow:function(me){
			var record = new Foss.pricing.PartbussPlanEntity(me.partbussPlan);
			me.getFlightPriceForm().getForm().loadRecord(record);
			me.getFlightPriceForm().getForm().findField('expressPartbussCode').setCombValue(me.partbussPlan.expressPartbussName,me.partbussPlan.expressPartbussCode);
			me.getFlightPriceForm().getForm().findField('loadOrgCode').setCombValue(me.partbussPlan.loadOrgName,me.partbussPlan.loadOrgCode);
			me.getFlightPriceForm().getForm().findField('beginTime').setValue(me.dateFormat(record.get('beginTime'),'Y-m-d H:i:s'));
			me.getFlightPriceForm().getForm().findField('endTime').setValue(me.dateFormat(record.get('endTime'),'Y-m-d H:i:s'));
		}
	},
	//修改快递代理公司运价FORM
	flightPriceForm:null,
	getFlightPriceForm : function(){
    	if(Ext.isEmpty(this.flightPriceForm)){
    		this.flightPriceForm = Ext.create('Foss.pricing.PartbussPlanForm',{
    			'isUpdate':true
    		});
    	}
    	return this.flightPriceForm;
    },
    dateFormat:function(value,format){
		if(!Ext.isEmpty(value)){
			return Ext.Date.format(new Date(value), format);
		}else{
			return null;
		}
	},
    //快递代理公司网点运价查询条件FORM
    queryOutBranchPlanForm:null,
	getQueryOutBranchPlanForm : function(){
    	if(Ext.isEmpty(this.queryOutBranchPlanForm)){
    		this.queryOutBranchPlanForm = Ext.create('Foss.pricing.QueryOutBranchPlanForm');
    	}
    	return this.queryOutBranchPlanForm;
    },
    //新增快递代理运价明细GRID
    flightPriceDetailGrid:null,
	getFlightPriceDetailGrid : function(){
    	if(Ext.isEmpty(this.flightPriceDetailGrid)){
    		this.flightPriceDetailGrid = Ext.create('Foss.pricing.OutbranchPlanGrid',{
    			'isShow':false
    		});
    	}
    	return this.flightPriceDetailGrid;
    },
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [ me.getFlightPriceForm(),me.getFlightPriceDetailGrid()];
		me.callParent([cfg]);
	}
});

//重量取数规则
pricing.roundup = 'ROUNDUP';//向上取整
pricing.round = 'ROUND';//四舍五入
pricing.remain = 'REMAIN';//保持不变
/**
 * 快递代理公司运价组-FORM
 */
Ext.define('Foss.pricing.PartbussPlanForm', {
	extend : 'Ext.form.Panel',
	title :pricing.agencyPricePlan.i18n('i18n.pricingRegion.expressDeliveryAgencyCompanyFreightInfo'),//快递代理公司运价信息
	frame: true,
	height:290,
	collapsible: true,
    defaults : {
    	margin : '5 5 5 5',
    	labelWidth:120,
    	allowBlank:false,
    	colspan : 1
    },
    layout:{
		type:'table',
		columns: 2
	},
    //提交快递代理运价数据
    commitFlightPrice:function(){
    	var me = this;
    	if(me.getForm().isValid()){//校验form是否通过校验
    		var flightPriceModel = null;
    		if(me.isUpdate){
    			flightPriceModel = new Foss.pricing.PartbussPlanEntity(me.up('window').partbussPlan);
    		}else{
    			flightPriceModel = new Foss.pricing.PartbussPlanEntity();
    		}
    		
    		me.getForm().updateRecord(flightPriceModel);//将FORM中数据设置到MODEL里面
    		flightPriceModel.set('beginTime',Ext.Date.parse(me.getForm().findField('beginTime').getValue(), 'Y-m-d H:i:s'));
    		flightPriceModel.set('endTime',Ext.Date.parse(me.getForm().findField('endTime').getValue(), 'Y-m-d H:i:s'));
    		var params = {'pricePlanVo':{'partbussPlan':flightPriceModel.data}};//新增数据
    		var successFun = function(json){
				pricing.showInfoMes(json.message);//提示新增成功
				if(!me.isUpdate){//修改了之后还要继续修改，新增之后不可以修改了
		    		me.up('window').getFlightPriceDetailGrid().getDockedItems()[1].items.items[0].setDisabled(false);//grid的新增按钮可用
		    		me.up('window').getFlightPriceDetailGrid().getDockedItems()[1].items.items[2].setDisabled(false);//grid的删除按钮可用
		    		me.getDockedItems()[1].items.items[1].setDisabled(true);//form的重置按钮不可用
		    		me.getDockedItems()[1].items.items[2].setDisabled(true);//form的保存按钮不可用
		    		me.getForm().getFields().each(function(item){
		    			item.setReadOnly(true);
		    		});
				}
				me.up('window').partbussPlan = json.pricePlanVo.partbussPlan;//返回数据
				//me.up('window').getFlightPriceDetailGrid().getStore().load();(保存快递代理运价主信息之后不需要查询的)
				me.up('window').parent.getPagingToolbar().moveFirst();//成功之后重新查询刷新结果集
			};
			var failureFun = function(json){
				if(Ext.isEmpty(json)){
					pricing.showErrorMes(pricing.agencyPricePlan.i18n('foss.pricing.requestTimedOut'));//请求超时
				}else{
					pricing.showErrorMes(json.message);//提示失败原因
				}
			};
			var url = null;
			if(me.isUpdate){
				url = pricing.realPath('updatePartbussPlan.action');//请求快递代理公司运价修改
    		}else{
    			url = pricing.realPath('addPartbussPlan.action');//请求快递代理公司运价新增
    		}
			
			pricing.requestJsonAjax(url,params,successFun,failureFun);//发送AJAX请求
    	}
    },
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [{
			name: 'priceNo',//运价编号
			maxLength:'20',
	        fieldLabel: pricing.agencyPricePlan.i18n('foss.pricing.tariffNo'),
	        xtype : 'textfield'
		},{
			name:'loadOrgCode',
			fieldLabel: pricing.agencyPricePlan.i18n('foss.pricing.stowageDepartment'),//配置部门
			forceSelection : true,
			allowBlank:true,
			types:'ORG',
			transferCenter:'Y',//查询外场
			xtype:'dynamicorgcombselector'
		},{
			name: 'beginTime',//生效时间
	        fieldLabel: pricing.agencyPricePlan.i18n('foss.pricing.effectiveTime'),
	        xtype : 'datetimefield_date97',
			editable:false,
			format:'Y-m-d H:i:s',
			time : true,
			id : 'Foss_flightPrice_beginTime_ID',
			allowBlank:false,
			dateConfig: {
				el : 'Foss_flightPrice_beginTime_ID-inputEl',
				dateFmt: 'yyyy-MM-dd HH:mi:ss'
			}
		},{
			name: 'endTime',//截止日期
	        fieldLabel: pricing.agencyPricePlan.i18n('foss.pricing.deadline'),
	        xtype : 'datetimefield_date97',
			editable:false,
			format:'Y-m-d H:i:s',
			time : true,
			id : 'Foss_flightPrice_endTime_ID',
			allowBlank:false,
			dateConfig: {
				el : 'Foss_flightPrice_endTime_ID-inputEl',
				dateFmt: 'yyyy-MM-dd HH:mi:ss'
			}
		},{
			name: 'expressPartbussCode',//快递代理公司
	        fieldLabel: pricing.agencyPricePlan.i18n('i18n.pricingRegion.expressDeliveryAgencyCompany'),
	        active:'Y',
	        xtype : 'commonLdpAgencyCompanySelector'
		},{
			xtype:'label'
		},{
			name: 'weightRule',
			queryMode: 'local',
		    displayField: 'valueName',
		    valueField: 'valueCode',
		    editable:false,
		    value:'',
		    store:pricing.getStore(null,null,['valueName','valueCode']
		    ,[ {'valueName':pricing.agencyPricePlan.i18n('i18n.pricingRegion.roundup'),'valueCode':pricing.roundup}//向上取整
		    , {'valueName':pricing.agencyPricePlan.i18n('i18n.pricingRegion.round'),'valueCode':pricing.round}//四舍五入
		    , {'valueName':pricing.agencyPricePlan.i18n('i18n.pricingRegion.remain'),'valueCode':pricing.remain}]),//保持不变
	        fieldLabel: pricing.agencyPricePlan.i18n('foss.pricing.weightrule'),//重量取数规则
	        xtype : 'combo'
		},{
			xtype:'label'
		},{
			name: 'descNote',//备注描述
	        fieldLabel: pricing.agencyPricePlan.i18n('foss.pricing.remarksDescription'),
	        colspan : 2,
	        allowBlank:true,
	        width:400,
	        xtype : 'textareafield'
		}];
		me.fbar = [{
			text :pricing.agencyPricePlan.i18n('i18n.pricingRegion.cancel'),//取消
			handler :function(){
				me.up('window').close();
			}
		},{
			text : pricing.agencyPricePlan.i18n('foss.pricing.reset'),//重置
			handler :function(){
				var partbussPlan =  me.up('window').partbussPlan;
				if(Ext.isEmpty(partbussPlan)){
					me.getForm().loadRecord(new Foss.pricing.PartbussPlanEntity());
				}else{
					me.getForm().loadRecord(new Foss.pricing.PartbussPlanEntity(me.up('window').partbussPlan));
					me.getForm().findField('expressPartbussCode').setCombValue(me.up('window').partbussPlan.expressPartbussName,me.up('window').partbussPlan.expressPartbussCode);
				}
					
			} 
		},{
			text : pricing.agencyPricePlan.i18n('foss.pricing.save'),//保存
			cls:'yellow_button',
			margin:'0 0 0 275',
			handler :function(){
				me.commitFlightPrice();
			} 
		}];
		me.callParent([cfg]);
	}
});


/**
 * 快递代理公司运价组-FORM
 */
Ext.define('Foss.pricing.PartbussPlanAddForm', {
	extend : 'Ext.form.Panel',
	title : pricing.agencyPricePlan.i18n('i18n.pricingRegion.expressDeliveryAgencyCompanyFreightInfo'),//快递代理公司运价信息
	frame: true,
	height:310,
	collapsible: true,
    defaults : {
    	margin : '5 5 5 5',
    	labelWidth:120,
    	allowBlank:false,
    	colspan : 1
    },
    layout:{
		type:'table',
		columns: 2
	},
    //提交快递代理运价数据
    commitFlightPrice:function(){
    	var me = this;
    	if(me.getForm().isValid()){//校验form是否通过校验
    		var flightPriceModel = null;
    		if(me.isUpdate){
    			flightPriceModel = new Foss.pricing.PartbussPlanEntity(me.up('window').partbussPlan);
    		}else{
    			flightPriceModel = new Foss.pricing.PartbussPlanEntity();
    		}
    		
    		me.getForm().updateRecord(flightPriceModel);//将FORM中数据设置到MODEL里面
    		flightPriceModel.set('beginTime',Ext.Date.parse(me.getForm().findField('beginTime').getValue(), 'Y-m-d H:i:s'));
    		flightPriceModel.set('endTime',Ext.Date.parse(me.getForm().findField('endTime').getValue(), 'Y-m-d H:i:s'));
    		var params = {'pricePlanVo':{'partbussPlan':flightPriceModel.data}};//新增数据
    		var successFun = function(json){
				pricing.showInfoMes(json.message);//提示新增成功
				if(!me.isUpdate){//修改了之后还要继续修改，新增之后不可以修改了
		    		me.up('window').getFlightPriceDetailGrid().getDockedItems()[1].items.items[0].setDisabled(false);//grid的新增按钮可用
		    		me.up('window').getFlightPriceDetailGrid().getDockedItems()[1].items.items[2].setDisabled(false);//grid的删除按钮可用
		    		me.getDockedItems()[1].items.items[1].setDisabled(true);//form的重置按钮不可用
		    		me.getDockedItems()[1].items.items[2].setDisabled(true);//form的保存按钮不可用
		    		me.getForm().getFields().each(function(item){
		    			item.setReadOnly(true);
		    		});
				}
				me.up('window').partbussPlan = json.pricePlanVo.partbussPlan;//返回数据
				//me.up('window').getFlightPriceDetailGrid().getStore().load();(保存快递代理运价主信息之后不需要查询的)
				me.up('window').parent.getPagingToolbar().moveFirst();//成功之后重新查询刷新结果集
			};
			var failureFun = function(json){
				if(Ext.isEmpty(json)){
					pricing.showErrorMes(pricing.agencyPricePlan.i18n('foss.pricing.requestTimedOut'));//请求超时
				}else{
					pricing.showErrorMes(json.message);//提示失败原因
				}
			};
			var url = null;
			if(me.isUpdate){
				url = pricing.realPath('updatePartbussPlan.action');//请求快递代理公司运价修改
    		}else{
    			url = pricing.realPath('addPartbussPlan.action');//请求快递代理公司运价新增
    		}
			
			pricing.requestJsonAjax(url,params,successFun,failureFun);//发送AJAX请求
    	}
    },
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [{
			name: 'priceNo',//运价编号
			maxLength:'20',
	        fieldLabel: pricing.agencyPricePlan.i18n('foss.pricing.tariffNo'),
	        xtype : 'textfield'
		},{
			name:'loadOrgCode',
			fieldLabel: pricing.agencyPricePlan.i18n('foss.pricing.stowageDepartment'),//配置部门
			forceSelection : true,
			allowBlank:true,
			types:'ORG',
			transferCenter:'Y',//查询外场
			xtype:'dynamicorgcombselector'
		},{
			name: 'beginTime',//生效时间
	        fieldLabel: pricing.agencyPricePlan.i18n('foss.pricing.effectiveTime'),
	        xtype : 'datetimefield_date97',
			editable:false,
			time : true,
			id : 'Foss_flightPrice_beginTime2_ID',
			allowBlank:false,
			dateConfig: {
				el : 'Foss_flightPrice_beginTime2_ID-inputEl'
//				dateFmt: 'yyyy-MM-dd HH:mi:ss'
			}
		},{
			name: 'endTime',//截止日期
	        fieldLabel: pricing.agencyPricePlan.i18n('foss.pricing.deadline'),
	        xtype : 'datetimefield_date97',
			editable:false,
			time : true,
			id : 'Foss_flightPrice_endTime2_ID',
			allowBlank:false,
			dateConfig: {
				el : 'Foss_flightPrice_endTime2_ID-inputEl'
//				dateFmt: 'yyyy-MM-dd HH:mi:ss'
			}
		},{
			name: 'expressPartbussCode',//快递代理公司
	        fieldLabel: pricing.agencyPricePlan.i18n('i18n.pricingRegion.expressDeliveryAgencyCompany'),
	        active:'Y',
	        xtype : 'commonLdpAgencyCompanySelector'
		},{
			xtype:'label'
		},{
			name: 'weightRule',
			queryMode: 'local',
		    displayField: 'valueName',
		    valueField: 'valueCode',
		    editable:false,
		    value:'',
		    store:pricing.getStore(null,null,['valueName','valueCode']
		    ,[ {'valueName':pricing.agencyPricePlan.i18n('i18n.pricingRegion.roundup'),'valueCode':pricing.roundup}//向上取整
		    , {'valueName':pricing.agencyPricePlan.i18n('i18n.pricingRegion.round'),'valueCode':pricing.round}//四舍五入
		    , {'valueName':pricing.agencyPricePlan.i18n('i18n.pricingRegion.remain'),'valueCode':pricing.remain}]),//保持不变
	        fieldLabel: pricing.agencyPricePlan.i18n('foss.pricing.weightrule'),//重量取数规则
	        xtype : 'combo'
		},{
			xtype:'label'
		},{
			name: 'descNote',//备注描述
	        fieldLabel: pricing.agencyPricePlan.i18n('foss.pricing.remarksDescription'),
	        colspan : 2,
	        allowBlank:true,
	        width:400,
	        xtype : 'textareafield'
		}];
		me.fbar = [{
			text :pricing.agencyPricePlan.i18n('i18n.pricingRegion.cancel'),//取消
			handler :function(){
				me.up('window').close();
			}
		},{
			text : pricing.agencyPricePlan.i18n('foss.pricing.reset'),//重置
			handler :function(){
				var partbussPlan =  me.up('window').partbussPlan;
				if(Ext.isEmpty(partbussPlan)){
					me.getForm().loadRecord(new Foss.pricing.PartbussPlanEntity());
				}else{
					me.getForm().loadRecord(new Foss.pricing.PartbussPlanEntity(me.up('window').partbussPlan));
					me.getForm().findField('expressPartbussCode').setCombValue(me.up('window').partbussPlan.expressPartbussName,me.up('window').partbussPlan.expressPartbussCode);
				}
					
			} 
		},{
			text : pricing.agencyPricePlan.i18n('foss.pricing.save'),//保存
			cls:'yellow_button',
			margin:'0 0 0 275',
			handler :function(){
				me.commitFlightPrice();
			} 
		}];
		me.callParent([cfg]);
	}
});

/**
 * 快递代理公司运价组-FORM
 */
Ext.define('Foss.pricing.QueryPartbussPlanForm', {
	extend : 'Ext.form.Panel',
	title : pricing.agencyPricePlan.i18n('i18n.pricingRegion.expressDeliveryAgencyCompanyFreightInfo'),//快递代理公司运价信息
	frame: true,
	height:300,
	collapsible: true,
    defaults : {
    	margin : '5 5 5 5',
    	labelWidth:120,
    	allowBlank:false,
    	colspan : 1
    },
    layout:{
		type:'table',
		columns: 2
	},
    //提交快递代理运价数据
    commitFlightPrice:function(){
    	var me = this;
    	if(me.getForm().isValid()){//校验form是否通过校验
    		var flightPriceModel = null;
    		if(me.isUpdate){
    			flightPriceModel = new Foss.pricing.PartbussPlanEntity(me.up('window').partbussPlan);
    		}else{
    			flightPriceModel = new Foss.pricing.PartbussPlanEntity();
    		}
    		
    		me.getForm().updateRecord(flightPriceModel);//将FORM中数据设置到MODEL里面
    		var params = {'pricePlanVo':{'partbussPlan':flightPriceModel.data}};//新增数据
    		var successFun = function(json){
				pricing.showInfoMes(json.message);//提示新增成功
				if(!me.isUpdate){//修改了之后还要继续修改，新增之后不可以修改了
		    		me.up('window').getFlightPriceDetailGrid().getDockedItems()[1].items.items[0].setDisabled(false);//grid的新增按钮可用
		    		me.up('window').getFlightPriceDetailGrid().getDockedItems()[1].items.items[2].setDisabled(false);//grid的删除按钮可用
		    		me.getDockedItems()[1].items.items[1].setDisabled(true);//form的重置按钮不可用
		    		me.getDockedItems()[1].items.items[2].setDisabled(true);//form的保存按钮不可用
		    		me.getForm().getFields().each(function(item){
		    			item.setReadOnly(true);
		    		});
				}
				me.up('window').partbussPlan = json.pricePlanVo.partbussPlan;//返回数据
				//me.up('window').getFlightPriceDetailGrid().getStore().load();(保存快递代理运价主信息之后不需要查询的)
				me.up('window').parent.getPagingToolbar().moveFirst();//成功之后重新查询刷新结果集
			};
			var failureFun = function(json){
				if(Ext.isEmpty(json)){
					pricing.showErrorMes(pricing.agencyPricePlan.i18n('foss.pricing.requestTimedOut'));//请求超时
				}else{
					pricing.showErrorMes(json.message);//提示失败原因
				}
			};
			var url = null;
			if(me.isUpdate){
				url = pricing.realPath('updatePartbussPlan.action');//请求快递代理公司运价修改
    		}else{
    			url = pricing.realPath('addPartbussPlan.action');//请求快递代理公司运价新增
    		}
			
			pricing.requestJsonAjax(url,params,successFun,failureFun);//发送AJAX请求
    	}
    },
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [{
			name: 'priceNo',//运价编号
			maxLength:'20',
	        fieldLabel: pricing.agencyPricePlan.i18n('foss.pricing.tariffNo'),
	        xtype : 'textfield'
		},{
			name:'loadOrgCode',
			fieldLabel: pricing.agencyPricePlan.i18n('foss.pricing.stowageDepartment'),//配置部门
			forceSelection : true,
			allowBlank:true,
			types:'ORG',
			transferCenter:'Y',//查询外场
			xtype:'dynamicorgcombselector'
		},{
			name: 'beginTime',//生效时间
	        fieldLabel: pricing.agencyPricePlan.i18n('foss.pricing.effectiveTime'),
	        xtype : 'datetimefield_date97',
			editable:false,
			format:'Y-m-d H:i:s',
			time : true,
			id : 'Foss_flightPrice_beginTime1_ID',
			allowBlank:false,
			dateConfig: {
				el : 'Foss_flightPrice_beginTime1_ID-inputEl',
				dateFmt: 'yyyy-MM-dd HH:mi:ss'
			}
		},{
			name: 'endTime',//截止日期
	        fieldLabel: pricing.agencyPricePlan.i18n('foss.pricing.deadline'),
	        xtype : 'datetimefield_date97',
			editable:false,
			format:'Y-m-d H:i:s',
			time : true,
			id : 'Foss_flightPrice_endTime1_ID',
			allowBlank:false,
			dateConfig: {
				el : 'Foss_flightPrice_endTime1_ID-inputEl',
				dateFmt: 'yyyy-MM-dd HH:mi:ss'
			}
		},{
			name: 'expressPartbussCode',//快递代理公司
	        fieldLabel:pricing.agencyPricePlan.i18n('i18n.pricingRegion.expressDeliveryAgencyCompany'),
	        active:'Y',
	        xtype : 'commonLdpAgencyCompanySelector'
		},{
			xtype:'label'
		},{
			name: 'weightRule',
			active:'Y',
			allowBlank:true,
	        fieldLabel: pricing.agencyPricePlan.i18n('foss.pricing.weightrule'),//重量取数规则
	        xtype : 'combo'
		}
		,{
			xtype:'label'
		},{
			name: 'descNote',//备注描述
	        fieldLabel: pricing.agencyPricePlan.i18n('foss.pricing.remarksDescription'),
	        colspan : 2,
	        allowBlank:true,
	        width:400,
	        xtype : 'textareafield'
		}];
		me.fbar = [{
			text :pricing.agencyPricePlan.i18n('i18n.pricingRegion.cancel'),//取消
			handler :function(){
				me.up('window').close();
			}
		},{
			text : pricing.agencyPricePlan.i18n('foss.pricing.reset'),//重置
			handler :function(){
				var partbussPlan =  me.up('window').partbussPlan;
				if(Ext.isEmpty(partbussPlan)){
					me.getForm().loadRecord(new Foss.pricing.PartbussPlanEntity());
				}else{
					me.getForm().loadRecord(new Foss.pricing.PartbussPlanEntity(me.up('window').partbussPlan));
					me.getForm().findField('expressPartbussCode').setCombValue(me.up('window').partbussPlan.expressPartbussName,me.up('window').partbussPlan.expressPartbussCode);
				}
					
			} 
		},{
			text : pricing.agencyPricePlan.i18n('foss.pricing.save'),//保存
			cls:'yellow_button',
			margin:'0 0 0 275',
			handler :function(){
				me.commitFlightPrice();
			} 
		}];
		me.callParent([cfg]);
	}
});



/**
 * 明细查询
 */
Ext.define('Foss.pricing.flightPrice.QueryFlightPriceDetailForm',{
	extend: 'Ext.form.Panel',
	title: pricing.agencyPricePlan.i18n('i18n.pricingRegion.searchCondition'),
	frame: true,
	collapsible: true,
	layout:{
		type:'table',
		columns: 5
	},
	
	constructor: function(config){
		var me = this, 
		cfg = Ext.apply({}, config);
		me.items =[{
			forceSelection : true,
			xtype : 'commoncityselector',
			name: 'destDistrictCode',//目的站
			fieldLabel : pricing.agencyPricePlan.i18n('foss.pricing.destinationStation')//目的站
		},{
			name: 'goodsTypeCode',
			queryMode: 'local',
		    displayField: 'name',
		    valueField: 'code',
		    editable:false,
		    forceSelection:true,		    
		    store:pricing.getStore(null,'Foss.pricing.GoodEntity',null
		    ,pricing.goodsTypeFlightPlan),
	        fieldLabel: pricing.agencyPricePlan.i18n('foss.pricing.theTypeGoods'),//货物类别
	        xtype : 'combo'
		},{

			queryMode: 'local',
		    displayField: 'flightNo',
		    valueField: 'flightNo',
		    forceSelection :true,
		    store:pricing.getStore(null,null,['flightNo','departureAirport','departureAirportName','destinationAirport','planLeaveTime','planArriveTime'],[]),
	        xtype : 'combo',
			name: 'flightNo',//航班号
	        fieldLabel: pricing.agencyPricePlan.i18n('foss.pricing.flightNumber')
		}];
		
		/* 重置,查询,按钮 */
		me.fbar = [{
			xtype : 'button', 
			width:70,
			text : pricing.agencyPricePlan.i18n('foss.pricing.reset'),//重置
			margin:'0 825 0 0',
			handler : function() {
				me.getForm().reset();
			}
		},{
			xtype : 'button', 
			width:70,
			cls:'yellow_button',
			text : pricing.agencyPricePlan.i18n('i18n.pricingRegion.search'),//查询
			handler : function() {
					me.up().getQueryFlightPriceDetailGrid().getPagingToolbar().moveFirst();
			}
		}];
		me.callParent([cfg]);
	}
});

/**
 * 新增快递代理公司网点运价信息
 */
Ext.define('Foss.pricing.OutbranchPlanAddWindow',{
	extend : 'Ext.window.Window',
	title : pricing.agencyPricePlan.i18n('i18n.pricingRegion.addExpressDeliveryAgencyCompanyWebSiteFreightInfo'),//新增快递代理公司网点运价信息
	closable : true,
    parent:null,//父元素（弹出这个window的gird——Foss.pricing.PartbussPlanGrid）
	modal : true,
	outbranchPlan:null,//快递代理网点运价方案实体
	resizable:true,//可以调整窗口的大小
	closeAction : 'hide',//点击关闭是隐藏窗口
	width :700,
	height :700,
	listeners:{
		beforehide:function(me){//隐藏WINDOW的时候清除数据
			me.getOutbranchPlanForm().getForm().reset();//表格重置
			me.getOubrPlanDetailGrid().getStore().removeAll();
    		me.getOubrPlanDetailGrid().getDockedItems()[1].items.items[0].setDisabled(true);//grid的新增按钮不可用
    		me.getOubrPlanDetailGrid().getDockedItems()[1].items.items[2].setDisabled(true);//grid的删除按钮不可用
    		me.getOutbranchPlanForm().getDockedItems()[1].items.items[1].setDisabled(false);//form的重置按钮可用
    		me.getOutbranchPlanForm().getDockedItems()[1].items.items[2].setDisabled(false);//form的保存按钮可用
    		me.getOutbranchPlanForm().getForm().getFields().each(function(item){
    			if(item.getName( )=='provName'||item.getName( )=='cityName'||item.getName( )=='districtName'){
					//只读的
					item.setReadOnly(true);
				}else{
					item.setReadOnly(false);
				}
    		});
		},
		beforeshow:function(me){//显示WINDOW的时候清除数据
		  	var partbussPlan = me.parent.up('window').partbussPlan;
		  	var bussCode = partbussPlan.expressPartbussCode;
		  	//获得落地网点对象
		  	var outerBranchCode = me.getOutbranchPlanForm().getForm().findField('outerBranchCode');
		  	outerBranchCode.store.removeAll();
			outerBranchCode.store.removeListener('beforeload');
			outerBranchCode.store.addListener('beforeload', function(store, operation, eOpts) {
			var searchParams = operation.params;
			if (Ext.isEmpty(searchParams)) {
				searchParams = {};
				Ext.apply(operation, {
							params : searchParams
						});
			}
			searchParams['expressDeptVo.outerBranchExpressEntity.agentCompany'] = bussCode;
			
			});
			outerBranchCode.store.loadPage(1);
				//隐藏量纲
//				me.getOubrPlanDetailGrid().columns[4].hide();
				//隐藏价格（元/量纲）
//				me.getOubrPlanDetailGrid().columns[6].hide();
				//显示价格（元/kg）
//				me.getOubrPlanDetailGrid().columns[5].show();
		}
	},
	//新增快递代理公司网点运价信息FORM
	outbranchPlanForm:null,
	getOutbranchPlanForm : function(){
    	if(Ext.isEmpty(this.outbranchPlanForm)){
    		this.outbranchPlanForm = Ext.create('Foss.pricing.OutbranchPlanForm',{'isUpdate':false});
    	}
    	return this.outbranchPlanForm;
    },
    //快递代理网点运价明细GRID
    oubrPlanDetailGrid:null,
	getOubrPlanDetailGrid : function(){
    	if(Ext.isEmpty(this.oubrPlanDetailGrid)){
    		this.oubrPlanDetailGrid = Ext.create('Foss.pricing.OubrPlanDetailGrid',{
    			'isShow':false
    		});
    		this.oubrPlanDetailGrid.getDockedItems()[0].items.items[0].setDisabled(true);//grid的新增按钮不可用
    		this.oubrPlanDetailGrid.getDockedItems()[0].items.items[2].setDisabled(true);//grid的删除按钮不可用
    	}
    	return this.oubrPlanDetailGrid;
    },
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [me.getOutbranchPlanForm(),me.getOubrPlanDetailGrid()];
		me.callParent([cfg]);
	}
});

/**
 * 修改快递代理网点运价方案
 */
Ext.define('Foss.pricing.OutbranchPlanUpdateWindow',{
	extend : 'Ext.window.Window',
	title : pricing.agencyPricePlan.i18n('i18n.pricingRegion.updateExpressDeliveryAgencyWebSiteFreightPlan'),//修改快递代理网点运价方案
	closable : true,
    parent:null,//父元素（弹出这个window的gird——Foss.pricing.PartbussPlanGrid）
	modal : true,
	outbranchPlan:null,//快递代理网点运价方案实体
	resizable:true,//可以调整窗口的大小
	closeAction : 'hide',//点击关闭是隐藏窗口
	width :700,
	height :700,
	listeners:{
		beforehide:function(me){//隐藏WINDOW的时候清除数据
			me.getOutbranchPlanForm().getForm().reset();//表格重置
			me.getOutbranchPlanForm().getForm().getFields().each(function(item){
				if(item.getName( )=='provName'||item.getName( )=='cityName'||item.getName( )=='districtName'){
					//只读的
					item.setReadOnly(true);
				}else{
					item.setReadOnly(false);
				}
			});
			me.getOubrPlanDetailGrid().getStore().removeAll();
		},
		beforeshow:function(me){//显示WINDOW的时候清除数据
			var partbussPlan = me.parent.up('window').partbussPlan;
		  	var bussCode = partbussPlan.expressPartbussCode;
		  	//获得落地网点对象
		  	var outerBranchCode = me.getOutbranchPlanForm().getForm().findField('outerBranchCode');
		  	outerBranchCode.store.removeAll();
			outerBranchCode.store.removeListener('beforeload');
			outerBranchCode.store.addListener('beforeload', function(store, operation, eOpts) {
			var searchParams = operation.params;
			if (Ext.isEmpty(searchParams)) {
				searchParams = {};
				Ext.apply(operation, {
							params : searchParams
						});
			}
			searchParams['expressDeptVo.outerBranchExpressEntity.agentCompany'] = bussCode;
			
			});
			outerBranchCode.store.loadPage(1);
			
			var outPlan = new Foss.pricing.OutbranchPlanEntity(me.outbranchPlan);
			me.getOutbranchPlanForm().getForm().loadRecord(outPlan);
			me.getOutbranchPlanForm().getForm().findField('outerBranchCode').setCombValue(me.outbranchPlan.outerBranchName,me.outbranchPlan.outerBranchCode);
			
			//获取方案类型
			var typeCode = outPlan.get('billType');
			if(typeCode == 'FX'){//判断方案类型是固定价格
				if(me.getOubrPlanDetailGrid().columns[0].text == '&#160;'){
					//隐藏量纲
					me.getOubrPlanDetailGrid().columns[5].hide();
					//隐藏价格（元/量纲）
					me.getOubrPlanDetailGrid().columns[7].hide();
					//显示价格（元/票）
					me.getOubrPlanDetailGrid().columns[6].show();
				}else{
					//隐藏量纲
					me.getOubrPlanDetailGrid().columns[4].hide();
					//隐藏价格（元/量纲）
					me.getOubrPlanDetailGrid().columns[6].hide();
					//显示价格（元/票）
					me.getOubrPlanDetailGrid().columns[5].show();
				}
				
			}else if(typeCode == 'AW'){
				if(me.getOubrPlanDetailGrid().columns[0].text == '&#160;'){
					//显示量纲
					me.getOubrPlanDetailGrid().columns[5].show();
					//显示价格（元/量纲）
					me.getOubrPlanDetailGrid().columns[7].show();
					//隐藏价格（元/票）
					me.getOubrPlanDetailGrid().columns[6].hide();
				}else{
					//显示量纲
					me.getOubrPlanDetailGrid().columns[4].show();
					//显示价格（元/量纲）
					me.getOubrPlanDetailGrid().columns[6].show();
					//隐藏价格（元/票）
					me.getOubrPlanDetailGrid().columns[5].hide();
				}
				
			}else{
				
			}
		}
	},
	//新增快递代理网点运价信息FORM
	//新增快递代理公司网点运价信息FORM
	outbranchPlanForm:null,
	getOutbranchPlanForm : function(){
    	if(Ext.isEmpty(this.outbranchPlanForm)){
    		this.outbranchPlanForm = Ext.create('Foss.pricing.OutbranchPlanForm',{'isUpdate':true});
    	}
    	return this.outbranchPlanForm;
    },
    //快递代理网点运价明细GRID
    oubrPlanDetailGrid:null,
	getOubrPlanDetailGrid : function(){
    	if(Ext.isEmpty(this.oubrPlanDetailGrid)){
    		this.oubrPlanDetailGrid = Ext.create('Foss.pricing.OubrPlanDetailGrid',{
    			'isShow':false
    		});
    	}
    	return this.oubrPlanDetailGrid;
    },
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [me.getOutbranchPlanForm(),me.getOubrPlanDetailGrid()];
		me.callParent([cfg]);
	}
});


/**
 * 新增快递代理公司网点运价明细Window
 */
Ext.define('Foss.pricing.OubrPlanDetailAddWindow',{
	extend : 'Ext.window.Window',
	title : pricing.agencyPricePlan.i18n('i18n.pricingRegion.addExpressDeliveryAgencyCompanyWebSiteFreightDetail'),//新增快递代理公司网点运价明细
	closable : true,
    parent:null,//父元素（弹出这个window的gird——Foss.pricing.PartbussPlanGrid）
	modal : true,
	oubrPlanDetail:null,//快递代理网点运价方案明细实体
	resizable:true,//可以调整窗口的大小
	closeAction : 'hide',//点击关闭是隐藏窗口
	width :700,
	height :350,
	listeners:{
		beforehide:function(me){//隐藏WINDOW的时候清除数据
			me.getOubrPlanDetailForm().getForm().reset();//表格重置
			me.getOubrPlanDetailForm().getForm().findField('dimension').allowBlank = false;
			//生效按钮可用
//			me.getOubrPlanDetailForm().getDockedItems()[1].items.items[2].setDisabled(false);
		},
		beforeshow:function(me){//显示WINDOW的时候清除数据
			//获得快递代理网点运价信息
			var billType = me.parent.up('window').getOutbranchPlanForm().getForm().findField('billType').getValue();
			if(billType == 'FX'){//判断方案类型是固定价格
				//隐藏量纲
				me.getOubrPlanDetailForm().getForm().findField('dimension').hide();
				me.getOubrPlanDetailForm().getForm().findField('dimension').allowBlank = true;
				me.getOubrPlanDetailForm().doLayout();//让其重新调整，没有这句话，时效显示不出来
				
			}else{
				//隐藏量纲
				me.getOubrPlanDetailForm().getForm().findField('dimension').show();
				me.getOubrPlanDetailForm().getForm().findField('dimension').allowBlank = false;
				me.getOubrPlanDetailForm().doLayout();//让其重新调整，没有这句话，时效显示不出来
				
			}
		}
	},
	//新增快递代理公司网点运价信息FORM
	oubrPlanDetailForm:null,
	getOubrPlanDetailForm : function(){
    	if(Ext.isEmpty(this.oubrPlanDetailForm)){
    		this.oubrPlanDetailForm = Ext.create('Foss.pricing.OubrPlanDetailForm',{'isUpdate':false});
    	}
    	return this.oubrPlanDetailForm;
    },
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [me.getOubrPlanDetailForm()];
		me.callParent([cfg]);
	}
});

/**
 * 查询快递代理网点运价方案
 */
Ext.define('Foss.pricing.OutbranchPlanQueryWindow',{
	extend : 'Ext.window.Window',
	title :pricing.agencyPricePlan.i18n('i18n.pricingRegion.viewExpressDeliveryAgencyWebSiteFreightPlan'),//查看快递代理网点运价方案
	closable : true,
    parent:null,//父元素（弹出这个window的gird——Foss.pricing.PartbussPlanGrid）
	modal : true,
	outbranchPlan:null,//快递代理网点运价方案实体
	resizable:true,//可以调整窗口的大小
	closeAction : 'hide',//点击关闭是隐藏窗口
	width :700,
	height :700,
	listeners:{
		beforehide:function(me){//隐藏WINDOW的时候清除数据
			me.getOutbranchPlanForm().getForm().reset();//表格重置
			me.getOutbranchPlanForm().getForm().getFields().each(function(item){
				item.setReadOnly(true);
			});
			me.getOubrPlanDetailGrid().getStore().removeAll();
		},
		beforeshow:function(me){//显示WINDOW的时候清除数据
			var outPlan = new Foss.pricing.OutbranchPlanEntity(me.outbranchPlan);
			me.getOutbranchPlanForm().getForm().loadRecord(outPlan);
			
			//获取方案类型
			var typeCode = outPlan.get('billType');
			if(typeCode == 'FX'){//判断方案类型是固定价格
				if(me.getOubrPlanDetailGrid().columns[0].text == '&#160;'){
					//隐藏量纲
					me.getOubrPlanDetailGrid().columns[4].hide();
					//隐藏价格（元/量纲）
					me.getOubrPlanDetailGrid().columns[6].hide();
					//显示价格（元/票）
					me.getOubrPlanDetailGrid().columns[5].show();
				}else{
					//隐藏量纲
					me.getOubrPlanDetailGrid().columns[3].hide();
					//隐藏价格（元/量纲）
					me.getOubrPlanDetailGrid().columns[5].hide();
					//显示价格（元/票）
					me.getOubrPlanDetailGrid().columns[4].show();
				}
				
			}else if(typeCode == 'AW'){
				if(me.getOubrPlanDetailGrid().columns[0].text == '&#160;'){
					//显示量纲
					me.getOubrPlanDetailGrid().columns[4].show();
					//显示价格（元/量纲）
					me.getOubrPlanDetailGrid().columns[6].show();
					//隐藏价格（元/票）
					me.getOubrPlanDetailGrid().columns[5].hide();
				}else{
					//显示量纲
					me.getOubrPlanDetailGrid().columns[3].show();
					//显示价格（元/量纲）
					me.getOubrPlanDetailGrid().columns[5].show();
					//隐藏价格（元/票）
					me.getOubrPlanDetailGrid().columns[4].hide();
				}
				
			}else{
				
			}
//			me.getOutbranchPlanForm().getForm().findField('outerBranchCode').setCombValue(me.outbranchPlan.outerBranchName,me.outbranchPlan.outerBranchCode);
		}
	},
	//新增快递代理网点运价信息FORM
	//新增快递代理公司网点运价信息FORM
	outbranchPlanForm:null,
	getOutbranchPlanForm : function(){
    	if(Ext.isEmpty(this.outbranchPlanForm)){
    		this.outbranchPlanForm = Ext.create('Foss.pricing.OutbranchPlanQueryForm',{'isUpdate':true});
    	}
    	return this.outbranchPlanForm;
    },
    //快递代理网点运价明细GRID
    oubrPlanDetailGrid:null,
	getOubrPlanDetailGrid : function(){
    	if(Ext.isEmpty(this.oubrPlanDetailGrid)){
    		this.oubrPlanDetailGrid = Ext.create('Foss.pricing.QueryOubrPlanDetailGrid',{
    			'isShow':false
    		});
    	}
    	return this.oubrPlanDetailGrid;
    },
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [me.getOutbranchPlanForm(),me.getOubrPlanDetailGrid()];
		me.callParent([cfg]);
	}
});

/**
 * 修改快递代理公司网点运价明细Window
 */
Ext.define('Foss.pricing.OubrPlanDetailUpdateWindow',{
	extend : 'Ext.window.Window',
	title : pricing.agencyPricePlan.i18n('i18n.pricingRegion.updateExpressDeliveryAgencyCompanyWebSiteFreightDetail'),//修改快递代理公司网点运价明细
	closable : true,
    parent:null,//父元素（弹出这个window的）
	modal : true,
	oubrPlanDetail:null,//快递代理网点运价方案明细实体
	resizable:true,//可以调整窗口的大小
	closeAction : 'hide',//点击关闭是隐藏窗口
	width :700,
	height :350,
	listeners:{
		beforehide:function(me){//隐藏WINDOW的时候清除数据
			me.getOubrPlanDetailForm().getForm().reset();//表格重置
			me.getOubrPlanDetailForm().getForm().findField('dimension').allowBlank = false;
			//生效按钮可用
//			me.getOubrPlanDetailForm().getDockedItems()[1].items.items[2].setDisabled(false);
		},
		beforeshow:function(me){//显示WINDOW的时候清除数据
			me.getOubrPlanDetailForm().getForm().loadRecord(new Foss.pricing.OubrPlanDetailEntity(me.oubrPlanDetail));
			//获得快递代理网点运价信息
			var outPlan = new Foss.pricing.OutbranchPlanEntity(me.parent.up('window').outbranchPlan);
			if(outPlan.get('billType') == 'FX'){//判断方案类型是固定价格
				//隐藏量纲
				me.getOubrPlanDetailForm().getForm().findField('dimension').hide();
				me.getOubrPlanDetailForm().getForm().findField('dimension').allowBlank = true;
				me.getOubrPlanDetailForm().doLayout();//让其重新调整，没有这句话，时效显示不出来
				
			}else{
				//隐藏量纲
				me.getOubrPlanDetailForm().getForm().findField('dimension').show();
				me.getOubrPlanDetailForm().getForm().findField('dimension').allowBlank = false;
				me.getOubrPlanDetailForm().doLayout();//让其重新调整，没有这句话，时效显示不出来
				
			}
		}
	},
	//新增快递代理公司网点运价信息FORM
	oubrPlanDetailForm:null,
	getOubrPlanDetailForm : function(){
    	if(Ext.isEmpty(this.oubrPlanDetailForm)){
    		this.oubrPlanDetailForm = Ext.create('Foss.pricing.OubrPlanDetailForm',{'isUpdate':true});
    	}
    	return this.oubrPlanDetailForm;
    },
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [me.getOubrPlanDetailForm()];
		me.callParent([cfg]);
	}
});


/**
 * 快递代理公司网点运价方案信息-FORM
 */
Ext.define('Foss.pricing.OutbranchPlanForm', {
	extend : 'Ext.form.Panel',
	title : pricing.agencyPricePlan.i18n('i18n.pricingRegion.expressDeliveryAgencyWebSiteFreightPlan'),//快递代理网点运价方案-FORM
	frame: true,
	height:290,
	collapsible: true,
    defaults : {
    	margin : '5 5 5 5',
    	labelWidth:120,
//    	allowBlank:false,
    	colspan : 1
    },
    layout:{
		type:'table',
		columns: 2
	},
    //提交快递代理运价数据
    commitFlightPrice:function(){
    	var me = this;
    	if(me.getForm().isValid()){//校验form是否通过校验
    		var outbranchPlanModel = null;
    		if(me.isUpdate){
    			outbranchPlanModel = new Foss.pricing.OutbranchPlanEntity(me.up('window').outbranchPlan);
    		}else{
    			outbranchPlanModel = new Foss.pricing.OutbranchPlanEntity();
    		}
    		var partbussPlan = me.up('window').parent.up('window').partbussPlan
    		me.getForm().updateRecord(outbranchPlanModel);//将FORM中数据设置到MODEL里面
    		outbranchPlanModel.set('expressPartbussPlanId',partbussPlan.id)
    		var params = {'pricePlanVo':{'outbranchPlan':outbranchPlanModel.data}};//快递代理网点数据
    		var successFun = function(json){
				pricing.showInfoMes(json.message);//提示新增成功
				if(!me.isUpdate){//修改了之后还要继续修改，新增之后不可以修改了
		    		me.up('window').getOubrPlanDetailGrid().getDockedItems()[1].items.items[0].setDisabled(false);//grid的新增按钮可用
		    		me.up('window').getOubrPlanDetailGrid().getDockedItems()[1].items.items[2].setDisabled(false);//grid的删除按钮可用
		    		me.getDockedItems()[1].items.items[1].setDisabled(true);//form的重置按钮不可用
		    		me.getDockedItems()[1].items.items[2].setDisabled(true);//form的保存按钮不可用
		    		me.getForm().getFields().each(function(item){
		    			item.setReadOnly(true);
		    		});
				}
				me.up('window').outbranchPlan = json.pricePlanVo.outbranchPlan;//返回数据
				//me.up('window').getFlightPriceDetailGrid().getStore().load();(保存快递代理运价主信息之后不需要查询的)
				me.up('window').parent.getPagingToolbar().moveFirst();//成功之后重新查询刷新结果集
			};
			var failureFun = function(json){
				if(Ext.isEmpty(json)){
					pricing.showErrorMes(pricing.agencyPricePlan.i18n('foss.pricing.requestTimedOut'));//请求超时
				}else{
					pricing.showErrorMes(json.message);//提示失败原因
				}
			};
			var url = null;
			if(me.isUpdate){
				url = pricing.realPath('updateOutbranchPlan.action');//请求快递代理公司运价修改
    		}else{
    			url = pricing.realPath('addOutbranchPlan.action');//请求快递代理公司运价新增
    		}
			
			pricing.requestJsonAjax(url,params,successFun,failureFun);//发送AJAX请求
    	}
    },
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [{
			name: 'outerBranchCode',//快递代理公司网点
			maxLength:'20',
	        fieldLabel: pricing.agencyPricePlan.i18n('i18n.pricingRegion.expressDeliveryAgencyCompanyWebSite'),
	        allowBlank:false,
	        active:'Y',
			xtype:'commonldpagencydeptselector',
			listeners : {
	        	select:function(combo,records,eopts){
	        		var provName = records[0].get('provName');
	        		var cityName = records[0].get('cityName');
	        		var districtName = records[0].get('countyName');
	        		
	        		combo.up('form').getForm().findField('provName').setValue(provName);
	        		combo.up('form').getForm().findField('cityName').setValue(cityName);
	        		combo.up('form').getForm().findField('districtName').setValue(districtName);
	        	}
	        		
	        }
		},{
			name:'provName',
			fieldLabel:'省份',//省份
			xtype : 'textfield',
			readOnly : true
		},{
			name:'cityName',
			fieldLabel:'城市',//城市
			readOnly : true,
			xtype : 'textfield'
		},{
			name:'districtName',
			fieldLabel:'区县',//区县
			readOnly : true,
			xtype : 'textfield'
		},{
		    xtype:'combobox',
			name: 'billType',//方案类型
			fieldLabel: '方案类型',
			columnWidth: .28,
			displayField: 'name',
			allowBlank:false,
			//value值字段
			valueField:'code', 
			queryMode:'local',
//			triggerAction:'FX',
			editable:false,
			value:'',
			store: Ext.create('Foss.pricing.agencyPricePlan.TypeStore',{
				data:{'items':[
		       		{'code':'FX','name':'固定价格'},
					{'code':'AW','name':'步进价格'}
	   			]}
			}),
			listeners:{
				select:function(combo,records,eopts){
					var window = combo.up('form').up('window');
					//方案类型Code
	        		var typeCode = combo.getValue();
	        		if(typeCode == 'FX'){//判断方案类型是固定价格
						//隐藏量纲
						window.getOubrPlanDetailGrid().columns[5].hide();
						//隐藏价格（元/量纲）
						window.getOubrPlanDetailGrid().columns[7].hide();
						//显示价格（元/票）
						window.getOubrPlanDetailGrid().columns[6].show();
					}else if(typeCode == 'AW'){
						//显示量纲
						window.getOubrPlanDetailGrid().columns[5].show();
						//显示价格（元/量纲）
						window.getOubrPlanDetailGrid().columns[7].show();
						//隐藏价格（元/票）
						window.getOubrPlanDetailGrid().columns[6].hide();
					}else{
						
					}
				}
			}
		},{
			xtype:'label'
		}];
		me.fbar = [{
			text :pricing.agencyPricePlan.i18n('i18n.pricingRegion.cancel'),//取消
			handler :function(){
				me.up('window').close();
			}
		},{
			text : pricing.agencyPricePlan.i18n('foss.pricing.reset'),//重置
			handler :function(){
				var partbussPlan =  me.up('window').partbussPlan;
				if(Ext.isEmpty(partbussPlan)){
					me.getForm().loadRecord(new Foss.pricing.OutbranchPlanEntity());
				}else{
					me.getForm().loadRecord(new Foss.pricing.OutbranchPlanEntity(me.up('window').outbranchPlan));
					me.getForm().findField('expressPartbussCode').setCombValue(me.up('window').outbranchPlan.outerBranchName,me.up('window').outbranchPlan.outerBranchCode);
				}
					
			} 
		},{
			text : pricing.agencyPricePlan.i18n('foss.pricing.save'),//保存
			cls:'yellow_button',
			margin:'0 0 0 275',
			handler :function(){
				me.commitFlightPrice();
			} 
		}];
		me.callParent([cfg]);
	}
});

/**
 * 查询快递代理公司网点运价方案信息-FORM
 */
Ext.define('Foss.pricing.OutbranchPlanQueryForm', {
	extend : 'Ext.form.Panel',
	title : pricing.agencyPricePlan.i18n('i18n.pricingRegion.expressDeliveryAgencyWebSiteFreightPlan'),//快递代理网点运价方案-FORM
	frame: true,
	height:290,
	collapsible: true,
    defaults : {
    	margin : '5 5 5 5',
    	labelWidth:120,
//    	allowBlank:false,
    	colspan : 1
    },
    layout:{
		type:'table',
		columns: 2
	},
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [{
			name:'outerBranchName',
			fieldLabel:pricing.agencyPricePlan.i18n('i18n.pricingRegion.expressDeliveryAgencyCompanyWebSite'),//省份
			xtype : 'textfield',
			readOnly : true
		},{
			name:'provName',
			fieldLabel:'省份',//省份
			xtype : 'textfield',
			readOnly : true
		},{
			name:'cityName',
			fieldLabel:'城市',//城市
			readOnly : true,
			xtype : 'textfield'
		},{
			name:'districtName',
			fieldLabel:'区县',//区县
			readOnly : true,
			xtype : 'textfield'
		},{
		    xtype:'combobox',
			name: 'billType',//方案类型
			fieldLabel: '方案类型',
			columnWidth: .28,
			displayField: 'name',
			allowBlank:true,
			readOnly : true,
			//value值字段
			valueField:'code', 
			queryMode:'local',
//			triggerAction:'FX',
			editable:false,
			value:'',
			store: Ext.create('Foss.pricing.agencyPricePlan.TypeStore',{
				data:{'items':[
		       		{'code':'FX','name':'固定价格'},
					{'code':'AW','name':'步进价格'}
	   			]}
			}),
			listeners:{
				select:function(combo,records,eopts){
					var window = combo.up('form').up('window');
					//方案类型Code
	        		var typeCode = combo.getValue();
	        		if(typeCode == 'FX'){//判断方案类型是固定价格
						//隐藏量纲
						window.getOubrPlanDetailGrid().columns[3].hide();
						//隐藏价格（元/量纲）
						window.getOubrPlanDetailGrid().columns[5].hide();
						//显示价格（元/票）
						window.getOubrPlanDetailGrid().columns[4].show();
					}else if(typeCode == 'AW'){
						//显示量纲
						window.getOubrPlanDetailGrid().columns[3].show();
						//显示价格（元/量纲）
						window.getOubrPlanDetailGrid().columns[5].show();
						//隐藏价格（元/票）
						window.getOubrPlanDetailGrid().columns[4].hide();
					}else{
						
					}
				}
			}
		},{
			xtype:'label'
		}];
		me.callParent([cfg]);
	}
});


/**
 * 快递代理公司网点运价方案明细信息-FORM
 */
Ext.define('Foss.pricing.OubrPlanDetailForm', {
	extend : 'Ext.form.Panel',
	title :pricing.agencyPricePlan.i18n('i18n.pricingRegion.expressDeliveryAgencyWebSiteFreightPlanDetail'),//快递代理网点运价方案明细-FORM
	frame: true,
	height:290,
	collapsible: true,
    defaults : {
    	margin : '5 5 5 5',
    	labelWidth:120,
//    	allowBlank:false,
    	colspan : 1
    },
    layout:{
		type:'table',
		columns: 2
	},
    //提交快递代理运价数据
    commitFlightPrice:function(){
    	var me = this;
    	var valids = me.getForm().isValid();
    	if(valids){//校验form是否通过校验
    		var oubrPlanDetailModel = null;
    		if(me.isUpdate){
    			oubrPlanDetailModel = new Foss.pricing.OubrPlanDetailEntity(me.up('window').oubrPlanDetail);
    		}else{
    			oubrPlanDetailModel = new Foss.pricing.OubrPlanDetailEntity();
    		}
    		var outbranchPlan = me.up('window').parent.up('window').outbranchPlan
    		me.getForm().updateRecord(oubrPlanDetailModel);//将FORM中数据设置到MODEL里面
    		oubrPlanDetailModel.set('expressOutbranchPlanId',outbranchPlan.id)
    		var params = {'pricePlanVo':{'oubrPlanDetail':oubrPlanDetailModel.data}};//组织新增数据
    		var successFun = function(json){
				pricing.showInfoMes(json.message);//提示新增成功
				/*if(!me.isUpdate){//修改了之后还要继续修改，新增之后不可以修改了
		    		me.getDockedItems()[1].items.items[1].setDisabled(true);//form的重置按钮不可用
		    		me.getDockedItems()[1].items.items[2].setDisabled(true);//form的保存按钮不可用
		    		me.getForm().getFields().each(function(item){
		    			item.setReadOnly(true);
		    		});
				}*/
				me.getDockedItems()[1].items.items[2].setDisabled(false);//form的保存按钮不可用
				me.up('window').oubrPlanDetail = json.pricePlanVo.oubrPlanDetail;//返回数据
				me.up('window').parent.getPagingToolbar().moveFirst();//成功之后重新查询刷新结果集
				me.up('window').close();
			};
			var failureFun = function(json){
				me.getDockedItems()[1].items.items[2].setDisabled(false);//form的保存按钮不可用
				if(Ext.isEmpty(json)){
					pricing.showErrorMes(pricing.agencyPricePlan.i18n('foss.pricing.requestTimedOut'));//请求超时
				}else{
					pricing.showErrorMes(json.message);//提示失败原因
				}
			};
			var url = null;
			if(me.isUpdate){
				url = pricing.realPath('updateOubrPlanDetail.action');//请求快递代理网点运价方案明细
    		}else{
    			url = pricing.realPath('addOubrPlanDetail.action');//请求快递代理网点运价方案明细
    		}
			
			pricing.requestJsonAjax(url,params,successFun,failureFun);//发送AJAX请求
    	}else{
    		pricing.agencyPricePlan.showWarningMsg('温馨提醒','验证不通过！');
    	}
    },
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [{
			name: 'leftrange',//重量下限
	        fieldLabel: '重量下限',
	        allowBlank:false,
	        decimalPrecision:2,
	        step:0.01,
	        allowBlank:false,
	        maxValue:999999.99,
	        minValue:0,
	        xtype : 'numberfield'
		},{
			name: 'rightrange',//重量上限
	        fieldLabel: '重量上限',
	        allowBlank:false,
	        decimalPrecision:2,
	        step:0.01,
	        allowBlank:false,
	        maxValue:999999.99,
	        minValue:0,
	        xtype : 'numberfield'
		},{
			name: 'dimension',//量纲.
	        fieldLabel: '量纲',
	        decimalPrecision:2,
	        step:0.01,
	        hidden : true,
	        allowBlank:true,
	        maxValue:999999.99,
	        minValue:0,
	        xtype : 'numberfield'
		},{
			name: 'fee',//固定费用
	        fieldLabel: '价格',
	        allowBlank:false,
	        decimalPrecision:2,
	        step:0.01,
	        allowBlank:false,
	        maxValue:999999.99,
	        minValue:0,
	        xtype : 'numberfield'
		}];
		me.fbar = [{
			text :pricing.agencyPricePlan.i18n('i18n.pricingRegion.cancel'),//取消
			handler :function(){
				me.up('window').close();
			}
		},{
			text : pricing.agencyPricePlan.i18n('foss.pricing.reset'),//重置
			handler :function(){
				var oubrPlanDetail =  me.up('window').oubrPlanDetail;
				if(Ext.isEmpty(oubrPlanDetail)){
					var model = new Foss.pricing.OubrPlanDetailEntity();
					me.getForm().loadRecord(model);
				}else{
					me.getForm().loadRecord(new Foss.pricing.OubrPlanDetailEntity(me.up('window').oubrPlanDetail));
				}
					
			} 
		},{
			text : pricing.agencyPricePlan.i18n('foss.pricing.save'),//保存
			cls:'yellow_button',
			margin:'0 0 0 275',
			handler :function(){
				this.disable();
				me.commitFlightPrice();
			} 
		}];
		me.callParent([cfg]);
	}
});


/**
 * 立即中止价格方案 Window
 */
Ext.define('Foss.pricing.flightPrice.FlightPriceImmediatelyStopEndTimeWindow',{
		extend: 'Ext.window.Window',
		title: pricing.agencyPricePlan.i18n('foss.pricing.immediatelySupendPricePriceScheme'),//"立即中止方案",
		width:380,
		height:152,
		flightPriceEntity:null,
		closeAction: 'hide' ,
		listeners:{
			beforehide:function(me){
				var form = me.down('form');
				form.getForm().reset();
			},
			beforeshow:function(me){
				var beginTime = Ext.Date.format(new Date(me.flightPriceEntity.beginTime), 'Y-m-d H:i:s');
				var endTime = Ext.Date.format(new Date(me.flightPriceEntity.endTime), 'Y-m-d H:i:s');
				var value = pricing.agencyPricePlan.i18n('foss.pricing.showleftTimeInfo')
					  +beginTime+pricing.agencyPricePlan.i18n('foss.pricing.showmiddleTimeInfo')
					  +endTime+'】，您是否立即终止该方案！';
				me.flightPriceEntity.showTime = value;
				me.flightPriceEntity.endTime = null;
				me.getFlightPriceStopFormPanel().getForm().loadRecord(new Foss.pricing.PartbussPlanEntity(me.flightPriceEntity));
			}
		},
		flightPriceImmediatelyStopFormPanel:null,
		getFlightPriceStopFormPanel : function(){
	    	if(Ext.isEmpty(this.flightPriceImmediatelyStopFormPanel)){
	    		this.flightPriceImmediatelyStopFormPanel = Ext.create('Foss.pricing.flightPrice.FlightPriceImmediatelyStopFormPanel');
	    	}
	    	return this.flightPriceImmediatelyStopFormPanel;
	    },
	   	constructor : function(config) {
			var me = this, cfg = Ext.apply({}, config);
			me.items = [me.getFlightPriceStopFormPanel()];
			me.callParent(cfg);
		}
});

/**
 * 立即中止功能FormPanel
 */
Ext.define('Foss.pricing.flightPrice.FlightPriceImmediatelyStopFormPanel',{
	extend : 'Ext.form.Panel',
	layout:'column',
	stopPricePlan:function(flightPriceEntity){
		var me = this;
    	var form = me.getForm();
    	if(form.isValid()){
    		var flightPriceModel = new Foss.pricing.PartbussPlanEntity(flightPriceEntity);
    		form.updateRecord(flightPriceModel);//将FORM中数据设置到MODEL里面
    		flightPriceModel.set('endTime',Ext.Date.parse(form.findField('endTime').getValue(), 'Y-m-d H:i:s'))
    		var params = {'pricePlanVo':{'partbussPlan':flightPriceModel.data}};
    		var url = pricing.realPath('immediatelyStop.action');
    		var successFun = function(json){
    			pricing.showInfoMes(json.message);
    			me.up('window').hide();
				pricing.pagingBar.moveFirst();   			
    	    };
    	    var failureFun = function(json){
    	    	if(Ext.isEmpty(json)){
    				pricing.showErrorMes(pricing.agencyPricePlan.i18n('i18n.pricingRegion.requestTimeOut'));
    			}else{
    				pricing.showErrorMes(json.message);
    			}
    	    };
    		pricing.requestJsonAjax(url,params,successFun,failureFun);//发送AJAX请求
    	}
	},
	constructor: function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.items = [{
				name:'showTime',
				width:280,
				xtype: 'displayfield',
				columnWidth:.9
			},{ 
				fieldLabel :pricing.agencyPricePlan.i18n('foss.pricing.suspendTime') ,//'中止日期',
				name : 'endTime',
				xtype : 'datetimefield_date97',
				editable:false,
				time : true,
				id : 'Foss_flightPrice_stopEndTime_ID',
				allowBlank:false,
				dateConfig: {
					el : 'Foss_flightPrice_stopEndTime_ID-inputEl'
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
				text : pricing.agencyPricePlan.i18n('i18n.pricingRegion.determine'),//"确认",
				handler : function() {
					var flightPriceEntity = me.up('window').flightPriceEntity;
					me.stopPricePlan(flightPriceEntity);
				}
			},{
				xtype : 'button', 
				width:70,
				columnWidth:.15,
				text : pricing.agencyPricePlan.i18n('i18n.pricingRegion.cancel'),//"取消",
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
Ext.define('Foss.pricing.flightPrice.FlightPriceImmediatelyActiveTimeWindow',{
		extend: 'Ext.window.Window',
		title: pricing.agencyPricePlan.i18n('foss.pricing.immediatelyActiveationPriceScheme'),//"立即激活方案",
		width:380,
		height:152,
		flightPriceEntity:null,
		closeAction: 'hide' ,
		listeners:{
			beforehide:function(me){
				var form = me.down('form');
				form.getForm().reset();
			},
			beforeshow:function(me){
				var beginTime = Ext.Date.format(new Date(me.flightPriceEntity.beginTime), 'Y-m-d H:i:s');
				var endTime = Ext.Date.format(new Date(me.flightPriceEntity.endTime), 'Y-m-d H:i:s');
				var value = pricing.agencyPricePlan.i18n('foss.pricing.showleftTimeInfo')
					  +beginTime+pricing.agencyPricePlan.i18n('foss.pricing.showmiddleTimeInfo')
					  +endTime+pricing.agencyPricePlan.i18n('foss.pricing.showrightEndTimeInfo');
				me.flightPriceEntity.showTime = value;
				me.flightPriceEntity.beginTime = null;
				me.getFlightPriceImmediatelyActiveFormPanel().getForm().loadRecord(new Foss.pricing.PartbussPlanEntity(me.flightPriceEntity));
			}
		},
		flightPriceImmediatelyActiveFormPanel:null,
		getFlightPriceImmediatelyActiveFormPanel : function(flightPriceEntity){
	    	if(Ext.isEmpty(this.flightPriceImmediatelyActiveFormPanel)){
	    		this.flightPriceImmediatelyActiveFormPanel = Ext.create('Foss.pricing.flightPrice.FlightPriceImmediatelyActiveFormPanel');
	    	}
	    	return this.flightPriceImmediatelyActiveFormPanel;
	    },
	   	constructor : function(config) {
			var me = this, cfg = Ext.apply({}, config);
			me.items = [me.getFlightPriceImmediatelyActiveFormPanel()];
			me.callParent(cfg);
		}
});


/**
 * 立即激活功能Form
 */
Ext.define('Foss.pricing.flightPrice.FlightPriceImmediatelyActiveFormPanel',{
	extend : 'Ext.form.Panel',
	layout:'column',
	activetionPricePlan:function(flightPriceEntity){
		var me = this;
    	var form = me.getForm();
    	if(form.isValid()){
    		var flightPriceModel = new Foss.pricing.PartbussPlanEntity(flightPriceEntity);
    		form.updateRecord(flightPriceModel);//将FORM中数据设置到MODEL里面
    		flightPriceModel.set('id',flightPriceModel.data.id);
    		flightPriceModel.set('beginTime',Ext.Date.parse(form.findField('beginTime').getValue(), 'Y-m-d H:i:s'));
    		var params = {'pricePlanVo':{'partbussPlan':flightPriceModel.data}};
    		var url = pricing.realPath('immediatelyActivate.action');
    		var successFun = function(json){
    			pricing.showInfoMes(json.message);
    			me.up('window').hide();
				pricing.pagingBar.moveFirst();   			
    	    };
    	    var failureFun = function(json){
    	    	if(Ext.isEmpty(json)){
    				pricing.showErrorMes(pricing.agencyPricePlan.i18n('i18n.pricingRegion.requestTimeOut'));
    			}else{
    				pricing.showErrorMes(json.message);
    			}
    	    };
    		pricing.requestJsonAjax(url,params,successFun,failureFun);//发送AJAX请求
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
				fieldLabel:pricing.agencyPricePlan.i18n('foss.pricing.availabilityDate'),//'生效日期',
				name : 'beginTime',
				xtype : 'datetimefield_date97',
				editable:false,
				time : true,
				id : 'Foss_flightPrice_activetionEndTime_ID',
				allowBlank:false,
				dateConfig: {
					el : 'Foss_flightPrice_activetionEndTime_ID-inputEl'
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
				text : pricing.agencyPricePlan.i18n('i18n.pricingRegion.determine'),//,"确认",
				handler : function() {
					var flightPriceEntity = me.up('window').flightPriceEntity;
					me.activetionPricePlan(flightPriceEntity);
				}
			},{
				xtype : 'button', 
				width:70,
				columnWidth:.15,
				text : pricing.agencyPricePlan.i18n('i18n.pricingRegion.cancel'),//"取消",
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
Ext.define('Foss.pricing.agencyPricePlan.UploadFlightPriceform',{
	extend:'Ext.window.Window',
	title:pricing.agencyPricePlan.i18n('foss.pricing.importPriceScheme'),
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
				fieldLabel:pricing.agencyPricePlan.i18n('foss.pricing.pleaseSelectAttachments'),
				labelWidth:100,
				buttonText:pricing.agencyPricePlan.i18n('foss.pricing.browse'),
				flex:3
			}]
		}];
		me.fbar = me.getFbar();
		me.callParent([cfg]);
	},
	getFbar:function(){
		var me = this;
		return [{
			text:pricing.agencyPricePlan.i18n('foss.pricing.import'),
			xtype:'button',
			scope:me,
			handler:me.uploadFile
		},{
			text:pricing.agencyPricePlan.i18n('i18n.pricingRegion.cancel'),
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
			if(Ext.isEmpty(json.pricePlanVo.numList)){
				pricing.showInfoMes(pricing.agencyPricePlan.i18n('foss.pricing.allDataImportSuccess'));//全部数据导入成功！
				me.close();
			}else{
				var message = pricing.agencyPricePlan.i18n('foss.pricing.first');
				for(var i = 0;i<json.pricePlanVo.numList;i++){
					message = message+json.pricePlanVo.numList[i]+','
				}
				message = message+pricing.agencyPricePlan.i18n('foss.pricing.lineImportSuccess');
				pricing.showWoringMessage(message);
			}
		};
		var failureFn = function(json){
			if(Ext.isEmpty(json)){
				pricing.showErrorMes(pricing.agencyPricePlan.i18n('i18n.pricingRegion.requestTimeOut'));
			}else{
				pricing.showErrorMes(json.message);
			}
		};
		var form = me.down('form').getForm();
		var url = pricing.realPath('importFlightPriceA.action');
		form.submit({
            url: url,
            waitMsg: pricing.agencyPricePlan.i18n('foss.pricing.uploadYourAttachment'),
            timeout:60000,    
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





