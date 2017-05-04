/**
 * 航空代理运价维护,配载部门、航班号,目的站分别对
 * 价格分段记录基础运费费率信息其中包括最低一票设置
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
		url:pricing.realPath('haveServerNowTime.action'),
		async:false,
		success:function(response){
			var result = Ext.decode(response.responseText);
			var today = new Date(result.pricingValuationVo.nowTime);//获取服务当前时间
			pricing.tomorrowTime = today.setDate(today.getDate()+1);
		},
		failure:function(response){
			var result = Ext.decode(response.responseText);
			if(Ext.isEmpty(result)){
				pricing.showErrorMes(pricing.flightPrice.i18n('i18n.pricingRegion.requestTimeOut'));
			}else{
				pricing.showErrorMes(result.message);
			}
		},
		exception:function(response){
			var result = Ext.decode(response.responseText);
			if(Ext.isEmpty(result)){
				pricing.showErrorMes(pricing.flightPrice.i18n('i18n.pricingRegion.requestTimeOut'));
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
				pricing.showErrorMes(pricing.flightPrice.i18n('i18n.pricingRegion.requestTimeOut'));
			}else{
				pricing.showErrorMes(result.message);
			}
		},
		exception:function(response){
			var result = Ext.decode(response.responseText);
			if(Ext.isEmpty(result)){
				pricing.showErrorMes(pricing.flightPrice.i18n('i18n.pricingRegion.requestTimeOut'));
			}else{
				pricing.showErrorMes(result.message);
			}
		}
	});
};
//--------------------------------------pricing----------------------------------------
//航空公司运价信息
Ext.define('Foss.pricing.FlightPricePlanEntity', {
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
        name : 'airlinesCode',/* 航空公司编码 */
        type : 'string'
    },{
        name : 'airlinesName',/* 航空公司名称 */
        type : 'string'
    },{
        name : 'airport',/* 出发机场 */
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
        name : 'active',/* 是否激活 */
        type : 'string'
    },{
        name : 'description',/* 描述 */
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
    	name : 'currentUsedVersion',/*是否最新版本*/
    	type : 'string'
    },{
    	name : 'showTime',
    	type : 'string'
    }]
});
//代理航空公司运价方案明细信息
Ext.define('Foss.pricing.FlightPricePlanDetailEntity', {
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
        name : 'flightPricePlanId',/* 运价方案ID */
        type : 'string'
    },{
        name : 'destDistrictCode',/* 目的站 */
        type : 'string'
    },{
        name : 'destDistrictName',/* 目的站名称 */
        type : 'string'
    },{
        name : 'goodsTypeCode', /* 货物类型编码 */
        type : 'string'
    },{
        name : 'flightNo', /* 航班号*/
        type : 'string'
    },{
    	defaultValue : null,
        name : 'minFee'/* 最低运费 */
    },{
        name : 'active',/* 是否激活 */
        type : 'string'
    },{
        name : 'createOrgCode',/* 创建机构编码 */
        type : 'string'
    },{
        name : 'modifyOrgCode',/* 修改组织机构编码 */
        type : 'string'
    },{
        name : 'currencyCode', /* 币种 */
        type : 'string'
    },{
    	defaultValue : null,
        name : 'down45Kg' /* 45公斤以下 */
    },{
    	defaultValue : null,
        name : 'up45Kg' /* 45公斤以上 */
    },{
        name : 'up100Kg'/* 100公斤以上 */
    },{
    	defaultValue : null,
        name : 'up300Kg'/* 300公斤以上 */
    },{
    	defaultValue : null,
        name : 'up500Kg'/* 500公斤以上 */
    },{
    	defaultValue : null,
        name : 'up1000Kg'/* 1000公斤以上 */
    },{
    	defaultValue : null,
        name : 'up2000Kg'/* 2000公斤以上 */
    },{
    	defaultValue : null,
        name : 'up3000Kg'/* 3000公斤以上 */
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
 * 航空公司运价Store（Foss.pricing.FlightPricePlanEntity）
 */
Ext.define('Foss.pricing.FlightPriceStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.pricing.FlightPricePlanEntity',//航空公司运价的MODEL
	pageSize:20,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url : '../pricing/searchFlightPriceByCondition.action',//请求地址
		reader : {
			type : 'json',
			root : 'flightPriceVo.flightPricePlanEntityList',//获取的数据
			totalProperty : 'totalCount'//总个数
		}
	}
});

/**
 * 航空公司运价明细Store（Foss.pricing.FlightPriceDetailEntity）
 */
Ext.define('Foss.pricing.FlightPricePlanDetailStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.pricing.FlightPricePlanDetailEntity',//航空公司运价明细的MODEL
	pageSize:20,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url : '../pricing/searchFlightPriceDetailByCondition.action',//请求地址
		reader : {
			type : 'json',
			root : 'flightPriceVo.flightPricePlanDetailEntityList',//获取的数据
			totalProperty : 'totalCount'//总个数
		}
	}
});

//----------------------------------------store---------------------------------

/**
 * 航空公司运价表单
 */
Ext.define('Foss.pricing.QueryFlightPriceForm', {
	extend : 'Ext.form.Panel',
	title: pricing.flightPrice.i18n('i18n.pricingRegion.searchCondition'),//查询条件
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
			xtype : 'commonairlinesselector',
			forceSelection : true,
			name: 'airlinesCode',//航空公司
			fieldLabel : pricing.flightPrice.i18n('foss.pricing.airline')
		},{
			name: 'active',
			queryMode: 'local',
		    displayField: 'valueName',
		    valueField: 'valueCode',
		    editable:false,
		    value:'',
		    store:pricing.getStore(null,null,['valueName','valueCode']
		    ,[ {'valueName':pricing.flightPrice.i18n('i18n.pricingRegion.all'),'valueCode':''}//全部
		    , {'valueName':pricing.flightPrice.i18n('i18n.pricingRegion.active'),'valueCode':pricing.yes}//激活
		    , {'valueName':pricing.flightPrice.i18n('i18n.pricingRegion.unActive'),'valueCode':pricing.no}]),//未激活
	        fieldLabel: pricing.flightPrice.i18n('foss.pricing.status'),//可停靠车型
	        xtype : 'combo'
		},
		{//是否当前版本 是，否，全部
			
			name: 'currentUsedVersion',
			queryMode: 'local',
		    displayField: 'valueName',
		    valueField: 'valueCode',
		    editable:false,
		    value:'',
		    store:pricing.getStore(null,null,['valueName','valueCode']
		    ,[{'valueName':pricing.flightPrice.i18n('i18n.pricing.currentVersion_yes'),'valueCode':pricing.yes}//是当前版本
		    , {'valueName':pricing.flightPrice.i18n('i18n.pricing.currentVersion_no'),'valueCode':pricing.no}]),//不是当前
	        fieldLabel: pricing.flightPrice.i18n('foss.pricing.currentUsedVersion'),//是否当前版本
	        xtype : 'combo'
		},
		{
			name:'billDate',
			editabled:false,
	        fieldLabel: pricing.flightPrice.i18n('foss.pricing.businessTime'),//业务时间
	        xtype : 'datefield'
		}],
		me.fbar = [{
			xtype : 'button', 
			width:70,
			left : 1,
			text : pricing.flightPrice.i18n('foss.pricing.reset'),//重置
			margin:'0 825 0 0',
			handler : function() {
				me.getForm().reset();
			}
		},{
			xtype : 'button', 
			width:70,
			cls:'yellow_button',
			text : pricing.flightPrice.i18n('i18n.pricingRegion.search'),//查询
			disabled: !pricing.flightPrice.isPermission('flightPrice/flightPriceQuerybutton'),
			hidden: !pricing.flightPrice.isPermission('flightPrice/flightPriceQuerybutton'),
			handler : function() {
				if(me.getForm().isValid()){
					var grid = Ext.getCmp('T_pricing-flightPrice_content').getFlightPriceGrid();
					grid.getPagingToolbar().moveFirst();
				}
				
			}
		}];
		me.callParent([cfg]);
	}
});
/**
 * 航空公司运价列表
 */
Ext.define('Foss.pricing.FlightPriceGrid', {
	extend: 'Ext.grid.Panel',
	title : pricing.flightPrice.i18n('foss.pricing.airlineTransportationInformationManagement'),//航空公司运价信息
	frame: true,
	cls: 'autoHeight',
	bodyCls: 'autoHeight', 
    sortableColumns:false,
    enableColumnHide:false,
    enableColumnMove:false,
	stripeRows : true, // 交替行效果
	selType : "rowmodel", // 选择类型设置为：行选择
	emptyText: pricing.flightPrice.i18n('foss.pricing.theQueryResultIsEmpty'),//查询结果为空
	//得到bbar
	pagingToolbar : null,
	getPagingToolbar : function() {
		if (this.pagingToolbar == null) {
			this.pagingToolbar = Ext.create('Deppon.StandardPaging', {
				store : this.store,
				pageSize : 20
			});
		}
		return this.pagingToolbar;
	},
	//航空公司运价新增WINDOW
	flightPriceAddWindow:null,
	getFlightPriceAddWindow:function(){
		if (this.flightPriceAddWindow == null) {
			this.flightPriceAddWindow = Ext.create('Foss.pricing.FlightPriceAddWindow');
			this.flightPriceAddWindow.parent = this;//父元素
		}
		return this.flightPriceAddWindow;
	},
	//修改航空公司运价WINDOW
	flightPriceUpdateWindow:null,
	getFlightPriceUpdateWindow:function(){
		if (this.flightPriceUpdateWindow == null) {
			this.flightPriceUpdateWindow = Ext.create('Foss.pricing.FlightPriceUpdateWindow');
			this.flightPriceUpdateWindow.parent = this;//父元素
		}
		return this.flightPriceUpdateWindow;
	},
	//查看航空公司运价
	flightPriceShowWindow:null,
	getFlightPriceShowWindow:function(){
		if (this.flightPriceShowWindow == null) {
			this.flightPriceShowWindow = Ext.create('Foss.pricing.FlightPriceShowWindow');
			this.flightPriceShowWindow.parent = this;//父元素
		}
		return this.flightPriceShowWindow;
	},
	//作废航空公司运价
	deleteFlightPrice: function(btn){
		var me = this;
		var selections = me.getSelectionModel().getSelection();//获取选中的数据
		if(selections.length<1){//判断是否至少选中了一条
			pricing.showWoringMessage(pricing.flightPrice.i18n('foss.pricing.pleaseSelectVoidOperation'));//请选择一条进行作废操作！
			return;//没有则提示并返回
		}
		for(var i = 0;i<selections.length;i++){
			if(selections[i].get('active')==pricing.yes){
				pricing.showWoringMessage(pricing.flightPrice.i18n('foss.pricing.pleaseChooseToNotActivateTheDataToBeBeleted'));//请选择未激活数据进行删除！
				return;//没有则提示并返回
			}
		}
		pricing.showQuestionMes(pricing.flightPrice.i18n('foss.pricing.wantDeleteTheseAviationFreight'),function(e){//是否要删除这些航空运价？
			if(e=='yes'){//询问是否删除，是则发送请求
				var idList = new Array();//航空公司运价
				for(var i = 0 ; i<selections.length ; i++){
					idList.push(selections[i].get('id'));
				}
				var params = {'flightPriceVo':{'idList':idList}};
				var successFun = function(json){
					pricing.showInfoMes(json.message);
					me.getPagingToolbar().moveFirst();
				};
				var failureFun = function(json){
					if(Ext.isEmpty(json)){
						pricing.showErrorMes(pricing.flightPrice.i18n('foss.pricing.requestTimedOut'));//请求超时
					}else{
						pricing.showErrorMes(json.message);
					}
				};
				var url = '../pricing/deleteFlightPrice.action';
				pricing.requestJsonAjax(url,params,successFun,failureFun);
			}
		})
	},
	//激活航空公司运价
	activeFlightPrice: function(){
		var me = this;
		var selections = me.getSelectionModel().getSelection();//获取选中的数据
		if(selections.length<1){//判断是否至少选中了一条
			pricing.showWoringMessage(pricing.flightPrice.i18n('foss.pricing.pleaseSelectAnActivateOperation'));//请选择一条进行激活操作！
			return;//没有则提示并返回
		}
		for(var i = 0;i<selections.length;i++){
			if(selections[i].get('active')==pricing.yes){
				pricing.showWoringMessage(pricing.flightPrice.i18n('foss.pricing.pleaseSelectTheActivationDataForActivation'));//请选择未激活数据进行激活！
				return;//没有则提示并返回
			}
		}
		pricing.showQuestionMes('是否要激活这些航空运价？',function(e){//是否要激活这些航空运价？
			if(e=='yes'){//询问是否删除，是则发送请求
				var idList = new Array();//航空公司运价
				for(var i = 0 ; i<selections.length ; i++){
					idList.push(selections[i].get('id'));
				}
				var params = {'flightPriceVo':{'idList':idList}};
				var successFun = function(json){
					pricing.showInfoMes(json.message);
					me.getPagingToolbar().moveFirst();
				};
				var failureFun = function(json){
					if(Ext.isEmpty(json)){
						pricing.showErrorMes(pricing.flightPrice.i18n('foss.pricing.requestTimedOut'));//请求超时
					}else{
						pricing.showErrorMes(json.message);
					}
				};
				var url = '../pricing/activeFlightPrice.action';
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
     * 上传时效方案信息
     * @return {}
     */
    getUploadFlightPriceform: function(){
    	if(Ext.isEmpty(this.uploadFlightPriceform)){
			this.uploadFlightPriceform = Ext.create('Foss.pricing.flightPrice.UploadFlightPriceform');	
		}
		return this.uploadFlightPriceform;
    },
    
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.columns = [{xtype: 'rownumberer',
			width:40,
			text : pricing.flightPrice.i18n('i18n.pricingRegion.num')//序号
		},{
			text : pricing.flightPrice.i18n('i18n.pricingRegion.opra'),//操作
			//dataIndex : 'id',
			xtype:'actioncolumn',
			align: 'center',
			width:80,
			items: [{
				iconCls: 'deppon_icons_edit',
                tooltip: pricing.flightPrice.i18n('foss.pricing.update'),//修改
                disabled: !pricing.flightPrice.isPermission('flightPrice/flightPriceUpdatebutton'),
				width:42,
				getClass : function (v, m, r, rowIndex) {
					if (r.get('active') === 'N') {
					    return 'deppon_icons_edit';
					} else {
					    return 'statementBill_hide';
					}
				},
                handler: function(grid, rowIndex, colIndex) {
                	var id = grid.getStore().getAt(rowIndex).get('id');
    				var params = {'flightPriceVo':{'flightPricePlanEntity':{'id':id}}};
    				var successFun = function(json){
    					var updateWindow = me.getFlightPriceUpdateWindow();
    					updateWindow.flightPricePlanEntity = json.flightPriceVo.flightPricePlanEntity;
    					updateWindow.show();
    					updateWindow.getFlightPriceDetailGrid().getStore().load();//加载明细数据
    				};
    				var failureFun = function(json){
    					if(Ext.isEmpty(json)){
    						pricing.showErrorMes(pricing.flightPrice.i18n('foss.pricing.requestTimedOut'));//请求超时
    					}else{
    						pricing.showErrorMes(json.message);
    					}
    				};
    				var url = pricing.realPath('queryFlightPriceById.action');
    				pricing.requestJsonAjax(url,params,successFun,failureFun);
                }
			},{
				iconCls: 'deppon_icons_softwareUpgrade',
                tooltip: pricing.flightPrice.i18n('foss.pricing.upgradedVersion'),//升级版本
                disabled: !pricing.flightPrice.isPermission('flightPrice/flightPriceUpgradebutton'),
				width:42,
				getClass : function (v, m, r, rowIndex) {
					if (r.get('active') === 'Y') {
					    return 'deppon_icons_softwareUpgrade';
					} else {
					    return 'statementBill_hide';
					}
				},
                handler: function(grid, rowIndex, colIndex) {
                	var id = grid.getStore().getAt(rowIndex).get('id');
    				var params = {'flightPriceVo':{'flightPricePlanEntity':{'id':id}}};
    				var successFun = function(json){
    					grid.up().getPagingToolbar().moveFirst();
    					var updateWindow = me.getFlightPriceUpdateWindow();
    					updateWindow.flightPricePlanEntity = json.flightPriceVo.flightPricePlanEntity;
    					updateWindow.show();
    					updateWindow.getFlightPriceDetailGrid().getStore().load();//加载明细数据
    				};
    				var failureFun = function(json){
    					if(Ext.isEmpty(json)){
    						pricing.showErrorMes(pricing.flightPrice.i18n('foss.pricing.requestTimedOut'));//请求超时
    					}else{
    						pricing.showErrorMes(json.message);
    					}
    				};
    				var url = pricing.realPath('copyFlightPricePlanEntity.action');
    				pricing.requestJsonAjax(url,params,successFun,failureFun);
                }
			},{
				iconCls: 'deppon_icons_showdetail',
                tooltip: pricing.flightPrice.i18n('foss.pricing.details'),//查看详情
                disabled: !pricing.flightPrice.isPermission('flightPrice/flightPriceDetailbutton'),
				width:42,
                handler: function(grid, rowIndex, colIndex) {
                	var id = grid.getStore().getAt(rowIndex).get('id');
    				var params = {'flightPriceVo':{'flightPricePlanEntity':{'id':id}}};
    				var successFun = function(json){
    					var showWindow = me.getFlightPriceShowWindow();
    					showWindow.flightPricePlanEntity = json.flightPriceVo.flightPricePlanEntity;
    					showWindow.show();
    					showWindow.getQueryFlightPriceDetailGrid().getStore().load();//加载明细数据
    				};
    				var failureFun = function(json){
    					if(Ext.isEmpty(json)){
    						pricing.showErrorMes(pricing.flightPrice.i18n('foss.pricing.requestTimedOut'));//请求超时
    					}else{
    						pricing.showErrorMes(json.message);
    					}
    				};
    				var url = pricing.realPath('queryFlightPriceById.action');
    				pricing.requestJsonAjax(url,params,successFun,failureFun);
                }
			}]
		},{
			text : pricing.flightPrice.i18n('foss.pricing.airline'),//航空公司
			dataIndex : 'airlinesName'
		},{
			text : pricing.flightPrice.i18n('foss.pricing.status'),//状态
			dataIndex : 'active',
			width:50,
			renderer:function(value){
				if(value==pricing.yes){//'Y'表示激活
					return pricing.flightPrice.i18n('i18n.pricingRegion.active');
				}else if(value==pricing.no){//'N'表示未激活
					return  pricing.flightPrice.i18n('i18n.pricingRegion.unActive');
				}else{
					return '';
				}
			}
		},{
			text : pricing.flightPrice.i18n('foss.pricing.stowageDepartment'),//配载部门
			dataIndex : 'loadOrgName'
		},{
			text : pricing.flightPrice.i18n('foss.pricing.createUser'),//创建人
			dataIndex : 'createUserName'
		},{
			text : pricing.flightPrice.i18n('foss.pricing.modifyUser'),//外场名称
			dataIndex : 'modifyUserName'
		},{
			text : pricing.flightPrice.i18n('foss.pricing.createTime'),//创建时间
			dataIndex : 'createDate',
			renderer:function(value){
				return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
			}
		},{
			text : pricing.flightPrice.i18n('foss.pricing.startTime'),//开始时间
			dataIndex : 'beginTime',
			renderer:function(value){
				return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
			}
		},{
			text : pricing.flightPrice.i18n('foss.pricing.endTime'),//外场名称
			dataIndex : 'endTime',
			renderer:function(value){
				return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
			}
		},{
			text : pricing.flightPrice.i18n('foss.pricing.remark'),//备注
			dataIndex : 'description'
		},{
			text : '是否当前版本',
			dataIndex : 'currentUsedVersion'
		}];
		me.store = Ext.create('Foss.pricing.FlightPriceStore',{
			autoLoad : false,//不自动加载
			pageSize : 20,
			listeners: {
				beforeload: function(store, operation, eOpts){
					var queryForm = me.up().getQueryFlightPriceForm();
					if(queryForm!=null){
						Ext.apply(operation,{
							params : {//航空公司运价大查询，查询条件组织
								'flightPriceVo.flightPricePlanEntity.airlinesCode':queryForm.getForm().findField('airlinesCode').getValue(),//航空公司CODE
								'flightPriceVo.flightPricePlanEntity.active':queryForm.getForm().findField('active').getValue(),//状态
								'flightPriceVo.flightPricePlanEntity.billDate':queryForm.getForm().findField('billDate').getValue(),//结束时间
								'flightPriceVo.flightPricePlanEntity.currentUsedVersion':queryForm.getForm().findField('currentUsedVersion').getValue()//是否当前版本
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
			text : pricing.flightPrice.i18n('i18n.pricingRegion.add'),//新增
			disabled: !pricing.flightPrice.isPermission('flightPrice/flightPriceAddbutton'),
			hidden: !pricing.flightPrice.isPermission('flightPrice/flightPriceAddbutton'),
			handler :function(){
				me.getFlightPriceAddWindow().show();
			} 
		},'-',{
			text : pricing.flightPrice.i18n('i18n.pricingRegion.active'),//激活
			disabled: !pricing.flightPrice.isPermission('flightPrice/flightPriceActivebutton'),
			hidden: !pricing.flightPrice.isPermission('flightPrice/flightPriceActivebutton'),
			handler :function(){
				me.activeFlightPrice();
			} 			
		},'-',{
			text : pricing.flightPrice.i18n('foss.pricing.delete'),//删除
			disabled: !pricing.flightPrice.isPermission('flightPrice/flightPriceDeletebutton'),
			hidden: !pricing.flightPrice.isPermission('flightPrice/flightPriceDeletebutton'),
			handler :function(){
				me.deleteFlightPrice();
			} 
		},'-',{
			text : pricing.flightPrice.i18n('foss.pricing.immediatelyActivationProgram'),//'立即激活',
			disabled:!pricing.flightPrice.isPermission('flightPrice/flightPriceImmediatelyActivebutton'),
			hidden:!pricing.flightPrice.isPermission('flightPrice/flightPriceImmediatelyActivebutton'),
			handler :function(){
				me.immediatelyActiveFlightPrice();
			} 
		},'-', {
			text : pricing.flightPrice.i18n('foss.pricing.immediatelyStopProgram'),//'立即中止',
			disabled:!pricing.flightPrice.isPermission('flightPrice/flightPriceImmediatelyStopbutton'),
			hidden:!pricing.flightPrice.isPermission('flightPrice/flightPriceImmediatelyStopbutton'),
			handler :function(){
				me.immediatelyStopFlightPrice();
			} 
		},'-', {
			text : pricing.flightPrice.i18n('foss.pricing.import'), //'导入',
			disabled:!pricing.flightPrice.isPermission('flightPrice/flightPriceImportbutton'),
			hidden:!pricing.flightPrice.isPermission('flightPrice/flightPriceImportbutton'),
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
 * @description 航空公司运价主页
 */
Ext.onReady(function() {
	Ext.QuickTips.init();
	if (Ext.getCmp('T_pricing-flightPrice_content')) {
		return;
	};
	pricing.queryAllGoodsType();
	var queryFlightPriceForm = Ext.create('Foss.pricing.QueryFlightPriceForm');//查询FORM
	var flightPriceGrid = Ext.create('Foss.pricing.FlightPriceGrid');//查询结果GRID
	Ext.getCmp('T_pricing-flightPrice').add(Ext.create('Ext.panel.Panel', {
	  	id : 'T_pricing-flightPrice_content',
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
 * 航空公司运价明细列表
 */
Ext.define('Foss.pricing.FlightPriceDetailGrid', {
	extend: 'Ext.grid.Panel',
	title : pricing.flightPrice.i18n('foss.pricing.airFreightDetailedInformationResults'),//航空运价信息
	frame: true,
	height :300,	
    sortableColumns:false,
    enableColumnHide:false,
    enableColumnMove:false,
	stripeRows : true, // 交替行效果
	selType : "rowmodel", // 选择类型设置为：行选择
	emptyText: pricing.flightPrice.i18n('foss.pricing.theQueryResultIsEmpty'),//查询结果为空
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
	//航空公司运价明细新增WINDOW
	flightPriceDetailAddWindow:null,
	getFlightPriceDetailAddWindow:function(){
		if (this.flightPriceDetailAddWindow == null) {
			this.flightPriceDetailAddWindow = Ext.create('Foss.pricing.FlightPriceDetailAddWindow');
			this.flightPriceDetailAddWindow.parent = this;//父元素
		}
		return this.flightPriceDetailAddWindow;
	},
	//修改航空公司运价WINDOW
	flightPriceDetailUpdateWindow:null,
	getFlightPriceDetailUpdateWindow:function(){
		if (this.flightPriceDetailUpdateWindow == null) {
			this.flightPriceDetailUpdateWindow = Ext.create('Foss.pricing.FlightPriceDetailUpdateWindow');
			this.flightPriceDetailUpdateWindow.parent = this;//父元素
		}
		return this.flightPriceDetailUpdateWindow;
	},
	//删除航空公司运价明细
	deleteFlightPriceDetail: function(){
		var me = this;
		var selections = me.getSelectionModel().getSelection();//获取选中的数据
		if(selections.length<1){//判断是否至少选中了一条
			pricing.showWoringMessage(pricing.flightPrice.i18n('foss.pricing.pleaseSelectOneDeleteOperation'));//请选择一条进行删除操作！
			return;//没有则提示并返回
		}
		pricing.showQuestionMes(pricing.flightPrice.i18n('foss.pricing.wantDeleteTheseAviationTariffDetails'),function(e){//是否要删除这些航空运价明细？
			if(e=='yes'){//询问是否删除，是则发送请求
				var idList = new Array();//航空公司运价
				for(var i = 0 ; i<selections.length ; i++){
					idList.push(selections[i].get('id'));
				}
				var params = {'flightPriceVo':{'idList':idList}};
				var successFun = function(json){
					pricing.showInfoMes(json.message);
					me.getPagingToolbar().moveFirst();
				};
				var failureFun = function(json){
					if(Ext.isEmpty(json)){
						pricing.showErrorMes(pricing.flightPrice.i18n('foss.pricing.requestTimedOut'));//请求超时
					}else{
						pricing.showErrorMes(json.message);
					}
				};
				var url = pricing.realPath('deleteFlightPriceDetail.action');
				pricing.requestJsonAjax(url,params,successFun,failureFun);
			}
		})
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.columns = [{xtype: 'rownumberer',
			width:40,
			text : pricing.flightPrice.i18n('i18n.pricingRegion.num')//序号
		},{
			text : pricing.flightPrice.i18n('i18n.pricingRegion.opra'),//操作
			//dataIndex : 'id',
			xtype:'actioncolumn',
			align: 'center',
			hidden:config.isShow,
			width:42,
			items: [{
				iconCls: 'deppon_icons_edit',
                tooltip: pricing.flightPrice.i18n('foss.pricing.update'),//修改
				width:42,
                handler: function(grid, rowIndex, colIndex) {
                	var id = grid.getStore().getAt(rowIndex).get('id');
    				var params = {'flightPriceVo':{'flightPricePlanDetailEntity':{'id':id}}};
    				var successFun = function(json){
    					var updateWindow = me.getFlightPriceDetailUpdateWindow();
    					updateWindow.flightPricePlanDetailEntity = json.flightPriceVo.flightPricePlanDetailEntity;
    					updateWindow.show();
    				};
    				var failureFun = function(json){
    					if(Ext.isEmpty(json)){
    						pricing.showErrorMes(pricing.flightPrice.i18n('foss.pricing.requestTimedOut'));//请求超时
    					}else{
    						pricing.showErrorMes(json.message);
    					}
    				};
    				var url = pricing.realPath('queryFlightPriceDetailById.action');
    				pricing.requestJsonAjax(url,params,successFun,failureFun);
                }
			}]
		},{
			text : pricing.flightPrice.i18n('foss.pricing.destinationStation'),//目的站
			dataIndex : 'destDistrictName'
		},{
			text : pricing.flightPrice.i18n('foss.pricing.theTypeGoods'),//货物类别
			dataIndex : 'goodsTypeCode',
			renderer:function(value){
				for(var i = 0;i<pricing.goodsTypeFlightPlan.length;i++){
					if(pricing.goodsTypeFlightPlan[i].code==value){
						return pricing.goodsTypeFlightPlan[i].name;
					}
				}
			}
		},{
			text : pricing.flightPrice.i18n('foss.pricing.flightNumber'),//航班号
			dataIndex : 'flightNo'
		},{
			text : pricing.flightPrice.i18n('foss.pricing.theLowestVotes'),//最低一票
			dataIndex : 'minFee'
		},{
			text : pricing.flightPrice.i18n('foss.pricing.45kgdown'),//45kg以下
			dataIndex : 'down45Kg'
		},{
			text : pricing.flightPrice.i18n('foss.pricing.45kgup'),//45kg以上
			dataIndex : 'up45Kg'
		},{
			text : pricing.flightPrice.i18n('foss.pricing.100kgup'),//100kg以上
			dataIndex : 'up100Kg'
		},{
			text : pricing.flightPrice.i18n('foss.pricing.300kgup'),//300kg以上
			dataIndex : 'up300Kg'
		},{
			text : pricing.flightPrice.i18n('foss.pricing.500kgup'),//500kg以上
			dataIndex : 'up500Kg'
		},{
			text : pricing.flightPrice.i18n('foss.pricing.1000kgup'),//1000kg以上
			dataIndex : 'up1000Kg'
		},{
			text : pricing.flightPrice.i18n('foss.pricing.2000kgup'),//2000kg以上
			dataIndex : 'up2000Kg'
		},{
			text : pricing.flightPrice.i18n('foss.pricing.3000kgup'),//3000kg以上
			dataIndex : 'up3000Kg'
		}];
		me.store = Ext.create('Foss.pricing.FlightPricePlanDetailStore',{
			autoLoad : false,//不自动加载
			pageSize : 10,
			listeners: {
				beforeload: function(store, operation, eOpts){
					var flightPriceId = me.up('window').flightPricePlanEntity.id;
					Ext.apply(operation,{
						params : { 
							'flightPriceVo.flightPricePlanDetailEntity.flightPricePlanId':flightPriceId//航空运价ID
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
			text : pricing.flightPrice.i18n('i18n.pricingRegion.add'),//新增
			handler :function(){
				me.getFlightPriceDetailAddWindow().show();
			} 
		},'-',{
			text : pricing.flightPrice.i18n('foss.pricing.delete'),//删除
			handler :function(){
				me.deleteFlightPriceDetail();
			} 
		}];
		me.bbar = me.getPagingToolbar();
		me.callParent([cfg]);
	}
});


/**
 * 航空公司运价明细列表
 */
Ext.define('Foss.pricing.QueryFlightPriceDetailGrid', {
	extend: 'Ext.grid.Panel',
	title : pricing.flightPrice.i18n('foss.pricing.theAviationFreightDetailsQuery'),//航空运价信息
	frame: true,
	height :300,
    sortableColumns:false,
    enableColumnHide:false,
    enableColumnMove:false,
	stripeRows : true, // 交替行效果
	selType : "rowmodel", // 选择类型设置为：行选择
	emptyText: pricing.flightPrice.i18n('foss.pricing.theQueryResultIsEmpty'),//查询结果为空
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
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.columns = [{xtype: 'rownumberer',
			width:40,
			text : pricing.flightPrice.i18n('i18n.pricingRegion.num')//序号
		},{
			text : pricing.flightPrice.i18n('foss.pricing.destinationStation'),//目的站
			dataIndex : 'destDistrictName'
		},{
			text : pricing.flightPrice.i18n('foss.pricing.theTypeGoods'),//货物类别
			dataIndex : 'goodsTypeCode',
			renderer:function(value){
				for(var i = 0;i<pricing.goodsTypeFlightPlan.length;i++){
					if(pricing.goodsTypeFlightPlan[i].code==value){
						return pricing.goodsTypeFlightPlan[i].name;
					}
				}
			}
		},{
			text : pricing.flightPrice.i18n('foss.pricing.flightNumber'),//航班号
			dataIndex : 'flightNo'
		},{
			text : pricing.flightPrice.i18n('foss.pricing.theLowestVotes'),//最低一票
			dataIndex : 'minFee'
		},{
			text : pricing.flightPrice.i18n('foss.pricing.45kgdown'),//45kg以下
			dataIndex : 'down45Kg'
		},{
			text : pricing.flightPrice.i18n('foss.pricing.45kgup'),//45kg以上
			dataIndex : 'up45Kg'
		},{
			text : pricing.flightPrice.i18n('foss.pricing.100kgup'),//100kg以上
			dataIndex : 'up100Kg'
		},{
			text : pricing.flightPrice.i18n('foss.pricing.300kgup'),//300kg以上
			dataIndex : 'up300Kg'
		},{
			text : pricing.flightPrice.i18n('foss.pricing.500kgup'),//500kg以上
			dataIndex : 'up500Kg'
		},{
			text : pricing.flightPrice.i18n('foss.pricing.1000kgup'),//1000kg以上
			dataIndex : 'up1000Kg'
		},{
			text : pricing.flightPrice.i18n('foss.pricing.2000kgup'),//2000kg以上
			dataIndex : 'up2000Kg'
		},{
			text : pricing.flightPrice.i18n('foss.pricing.3000kgup'),//3000kg以上
			dataIndex : 'up3000Kg'
		}];
		me.store = Ext.create('Foss.pricing.FlightPricePlanDetailStore',{
			autoLoad : false,//不自动加载
			pageSize : 10,
			listeners: {
				beforeload: function(store, operation, eOpts){
					var flightPriceId = me.up('window').flightPricePlanEntity.id;
					var queryForm = me.up('window').getQueryFlightPriceDetailForm();
					var destDistrictCode  = queryForm.getForm().findField('destDistrictCode').getValue();
					var flightNo  = queryForm.getForm().findField('flightNo').getValue();
					var goodsTypeCode  = queryForm.getForm().findField('goodsTypeCode').getValue();
					Ext.apply(operation,{
						params : { 
							'flightPriceVo.flightPricePlanDetailEntity.flightPricePlanId':flightPriceId,//航空运价ID
							'flightPriceVo.flightPricePlanDetailEntity.destDistrictCode': destDistrictCode,//目的站
							'flightPriceVo.flightPricePlanDetailEntity.flightNo':flightNo,//航班号
							'flightPriceVo.flightPricePlanDetailEntity.goodsTypeCode':goodsTypeCode//货物类别
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
 * 新增航空公司运价信息
 */
Ext.define('Foss.pricing.FlightPriceAddWindow',{
	extend : 'Ext.window.Window',
	title : pricing.flightPrice.i18n('foss.pricing.newAirlineTariffs'),//新增航空公司运价
	closable : true,
    parent:null,//父元素（弹出这个window的gird——Foss.pricing.FlightPriceGrid）
	modal : true,
	flightPricePlanEntity:null,
	resizable:true,//可以调整窗口的大小
	closeAction : 'hide',//点击关闭是隐藏窗口
	width :580,
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
			
		}
	},
	//新增航空公司运价FORM
	flightPriceForm:null,
	getFlightPriceForm : function(){
    	if(Ext.isEmpty(this.flightPriceForm)){
    		this.flightPriceForm = Ext.create('Foss.pricing.FlightPriceForm',{
    			'isUpdate':false
    		});
    	}
    	return this.flightPriceForm;
    },
     //新增航空运价明细GRID
    flightPriceDetailGrid:null,
	getFlightPriceDetailGrid : function(){
    	if(Ext.isEmpty(this.flightPriceDetailGrid)){
    		this.flightPriceDetailGrid = Ext.create('Foss.pricing.FlightPriceDetailGrid',{
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
 * 航空公司运价信息
 */
Ext.define('Foss.pricing.FlightPriceShowWindow',{
	extend : 'Ext.window.Window',
	title : pricing.flightPrice.i18n('foss.pricing.airlineTariffs'),//航空公司运价
	closable : true,
    parent:null,//父元素（弹出这个window的gird——Foss.pricing.FlightPriceGrid）
	modal : true,
	flightPricePlanEntity:null,
	resizable:true,//可以调整窗口的大小
	closeAction : 'hide',//点击关闭是隐藏窗口
	width :900,
	height :800,
	listeners:{
		beforehide:function(me){//隐藏WINDOW的时候清除数据
			me.getQueryFlightPriceDetailForm().getForm().reset();//表格重置
			me.getQueryFlightPriceDetailGrid().getStore().removeAll();
		},
		beforeshow:function(me){//显示WINDOW的时候清除数据
			me.getFlightPriceForm().getForm().loadRecord(new Foss.pricing.FlightPricePlanEntity(me.flightPricePlanEntity));
			me.getFlightPriceForm().getForm().findField('airlinesCode').setCombValue(me.flightPricePlanEntity.airlinesName,me.flightPricePlanEntity.airlinesCode);
			me.getFlightPriceForm().getForm().findField('loadOrgCode').setCombValue(me.flightPricePlanEntity.loadOrgName,me.flightPricePlanEntity.loadOrgCode);
		}
	},
	//新增航空公司运价FORM
	flightPriceForm:null,
	getFlightPriceForm : function(){
    	if(Ext.isEmpty(this.flightPriceForm)){
    		this.flightPriceForm = Ext.create('Foss.pricing.FlightPriceForm');
    		this.flightPriceForm.getForm().getFields().each(function(item){
    			item.setReadOnly(true);
    		});//全部设置为只读
    		this.flightPriceForm.getDockedItems()[0].hide();//隐藏按钮
    	}
    	return this.flightPriceForm;
    },
     //新增航空运价明细GRID
    queryFlightPriceDetailGrid:null,
	getQueryFlightPriceDetailGrid : function(){
    	if(Ext.isEmpty(this.queryFlightPriceDetailGrid)){
    		this.queryFlightPriceDetailGrid = Ext.create('Foss.pricing.QueryFlightPriceDetailGrid');
    	}
    	return this.queryFlightPriceDetailGrid;
    },
     //查询航空条件
    queryFlightPriceDetailForm:null,
	getQueryFlightPriceDetailForm : function(){
    	if(Ext.isEmpty(this.queryFlightPriceDetailForm)){
    		this.queryFlightPriceDetailForm = Ext.create('Foss.pricing.flightPrice.QueryFlightPriceDetailForm');
    	}
    	return this.queryFlightPriceDetailForm;
    },
    
    //组件构造器
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [me.getFlightPriceForm(),me.getQueryFlightPriceDetailForm(),me.getQueryFlightPriceDetailGrid()];  
		me.callParent([cfg]);
	}
});

/**
 * 修改航空公司运价
 */
Ext.define('Foss.pricing.FlightPriceUpdateWindow',{
	extend : 'Ext.window.Window',
	title : pricing.flightPrice.i18n('foss.pricing.modifyAirlineTariffs'),//修改航空公司运价
	closable : true,
	modal : true,
	resizable:false,
	flightPricePlanEntity:null,//修改航空公司运价数据
	parent:null,//父元素（弹出这个window的gird——Foss.pricing.SiteGroupGrid）
	closeAction : 'hide',
	width :580,
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
			me.getFlightPriceForm().getForm().loadRecord(new Foss.pricing.FlightPricePlanEntity(me.flightPricePlanEntity));
			me.getFlightPriceForm().getForm().findField('airlinesCode').setCombValue(me.flightPricePlanEntity.airlinesName,me.flightPricePlanEntity.airlinesCode);
			me.getFlightPriceForm().getForm().findField('loadOrgCode').setCombValue(me.flightPricePlanEntity.loadOrgName,me.flightPricePlanEntity.loadOrgCode);
		}
	},
	//修改航空公司运价FORM
	flightPriceForm:null,
	getFlightPriceForm : function(){
    	if(Ext.isEmpty(this.flightPriceForm)){
    		this.flightPriceForm = Ext.create('Foss.pricing.FlightPriceForm',{
    			'isUpdate':true
    		});
    	}
    	return this.flightPriceForm;
    },
    //新增航空运价明细GRID
    flightPriceDetailGrid:null,
	getFlightPriceDetailGrid : function(){
    	if(Ext.isEmpty(this.flightPriceDetailGrid)){
    		this.flightPriceDetailGrid = Ext.create('Foss.pricing.FlightPriceDetailGrid',{
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
/**
 * 航空公司运价组-FORM
 */
Ext.define('Foss.pricing.FlightPriceForm', {
	extend : 'Ext.form.Panel',
	title : pricing.flightPrice.i18n('foss.pricing.airlineTransportationInformationManagement'),//航空公司运价信息
	frame: true,
	height:290,
	collapsible: true,
    defaults : {
    	margin : '5 5 5 5',
    	labelWidth:80,
    	allowBlank:false,
    	colspan : 1
    },
    layout:{
		type:'table',
		columns: 2
	},
    //提交航空运价数据
    commitFlightPrice:function(){
    	var me = this;
    	if(me.getForm().isValid()){//校验form是否通过校验
    		var flightPriceModel = null;
    		if(me.isUpdate){
    			flightPriceModel = new Foss.pricing.FlightPricePlanEntity(me.up('window').flightPricePlanEntity);
    		}else{
    			flightPriceModel = new Foss.pricing.FlightPricePlanEntity();
    		}
    		
    		me.getForm().updateRecord(flightPriceModel);//将FORM中数据设置到MODEL里面
    		var params = {'flightPriceVo':{'flightPricePlanEntity':flightPriceModel.data}};//组织新增数据
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
				me.up('window').flightPricePlanEntity = json.flightPriceVo.flightPricePlanEntity;//返回数据
				//me.up('window').getFlightPriceDetailGrid().getStore().load();(保存航空运价主信息之后不需要查询的)
				me.up('window').parent.getPagingToolbar().moveFirst();//成功之后重新查询刷新结果集
			};
			var failureFun = function(json){
				if(Ext.isEmpty(json)){
					pricing.showErrorMes(pricing.flightPrice.i18n('foss.pricing.requestTimedOut'));//请求超时
				}else{
					pricing.showErrorMes(json.message);//提示失败原因
				}
			};
			var url = null;
			if(me.isUpdate){
				url = pricing.realPath('updateFlightPrice.action');//请求航空公司运价修改
    		}else{
    			url = pricing.realPath('addFlightPrice.action');//请求航空公司运价新增
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
	        fieldLabel: pricing.flightPrice.i18n('foss.pricing.tariffNo'),
	        xtype : 'textfield'
		},{
			name:'loadOrgCode',
			fieldLabel: pricing.flightPrice.i18n('foss.pricing.stowageDepartment'),//车队负责行政区域
			forceSelection : true,
			types:'ORG',
			doAirDispatch:'Y',//空运配载
			xtype:'dynamicorgcombselector'
		},{
			name: 'beginTime',//生效时间
	        fieldLabel: pricing.flightPrice.i18n('foss.pricing.effectiveTime'),
	        editable:false,
	        minValue:new Date(pricing.tomorrowTime),
	        format:'Y-m-d',
	        xtype : 'datefield'
		},{
			name: 'endTime',//截止日期
	        fieldLabel: pricing.flightPrice.i18n('foss.pricing.deadline'),
	        editable:false,
	        format:'Y-m-d',
	        minValue:new Date(pricing.tomorrowTime),
	        xtype : 'datefield'
		},{
			name: 'airlinesCode',//航空公司
	        fieldLabel: pricing.flightPrice.i18n('foss.pricing.airline'),
	        xtype : 'commonairlinesselector'
		},{
			xtype:'label'
		},{
			name: 'description',//备注描述
	        fieldLabel: pricing.flightPrice.i18n('foss.pricing.remarksDescription'),
	        colspan : 2,
	        allowBlank:true,
	        width:400,
	        xtype : 'textareafield'
		}];
		me.fbar = [{
			text :pricing.flightPrice.i18n('i18n.pricingRegion.cancel'),//取消
			handler :function(){
				me.up('window').close();
			}
		},{
			text : pricing.flightPrice.i18n('foss.pricing.reset'),//重置
			handler :function(){
				var flightPricePlanEntity =  me.up('window').flightPricePlanEntity;
				if(Ext.isEmpty(flightPricePlanEntity)){
					me.getForm().loadRecord(new Foss.pricing.FlightPricePlanEntity());
				}else{
					me.getForm().loadRecord(new Foss.pricing.FlightPricePlanEntity(me.up('window').flightPricePlanEntity));
					me.getForm().findField('airlinesCode').setCombValue(me.up('window').flightPricePlanEntity.airlinesName,me.up('window').flightPricePlanEntity.airlinesCode);
				}
					
			} 
		},{
			text : pricing.flightPrice.i18n('foss.pricing.save'),//保存
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
	title: pricing.flightPrice.i18n('i18n.pricingRegion.searchCondition'),
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
			fieldLabel : pricing.flightPrice.i18n('foss.pricing.destinationStation')//目的站
		},{
			name: 'goodsTypeCode',
			queryMode: 'local',
		    displayField: 'name',
		    valueField: 'code',
		    editable:false,
		    forceSelection:true,		    
		    store:pricing.getStore(null,'Foss.pricing.GoodEntity',null
		    ,pricing.goodsTypeFlightPlan),
	        fieldLabel: pricing.flightPrice.i18n('foss.pricing.theTypeGoods'),//货物类别
	        xtype : 'combo'
		},{

			queryMode: 'local',
		    displayField: 'flightNo',
		    valueField: 'flightNo',
		    forceSelection :true,
		    store:pricing.getStore(null,null,['flightNo','departureAirport','departureAirportName','destinationAirport','planLeaveTime','planArriveTime'],[]),
	        xtype : 'combo',
			name: 'flightNo',//航班号
	        fieldLabel: pricing.flightPrice.i18n('foss.pricing.flightNumber')
		}];
		
		/* 重置,查询,按钮 */
		me.fbar = [{
			xtype : 'button', 
			width:70,
			text : pricing.flightPrice.i18n('foss.pricing.reset'),//重置
			margin:'0 825 0 0',
			handler : function() {
				me.getForm().reset();
			}
		},{
			xtype : 'button', 
			width:70,
			cls:'yellow_button',
			text : pricing.flightPrice.i18n('i18n.pricingRegion.search'),//查询
			handler : function() {
					me.up().getQueryFlightPriceDetailGrid().getPagingToolbar().moveFirst();
			}
		}];
		me.callParent([cfg]);
	}
});

/**
 * 新增航空公司运价明细信息
 */
Ext.define('Foss.pricing.FlightPriceDetailAddWindow',{
	extend : 'Ext.window.Window',
	title : pricing.flightPrice.i18n('foss.pricing.newAirReservePriceProgramDetail'),//新增空运底价方案明细
	closable : true,
    parent:null,//父元素（弹出这个window的gird——Foss.pricing.FlightPriceGrid）
	modal : true,
	flightPricePlanDetailEntity:null,//航空公司运价明细实体
	resizable:true,//可以调整窗口的大小
	closeAction : 'hide',//点击关闭是隐藏窗口
	width :570,
	height :480,
	listeners:{
		beforehide:function(me){//隐藏WINDOW的时候清除数据
			me.getFlightPriceDetailForm().getForm().reset();//表格重置
		},
		beforeshow:function(me){//显示WINDOW的时候清除数据
			var airlines = me.parent.up('window').flightPricePlanEntity.airlinesCode;
			var loadOrgCode = me.parent.up('window').flightPricePlanEntity.loadOrgCode;
			var params = {'flightPriceVo':{'flightEntity':{'airlines':airlines,'origCode':loadOrgCode}}};
			var successFun = function(json){
				var flightDtoList = json.flightPriceVo.flightDtoList;
				me.getFlightPriceDetailForm().getForm().findField('flightNo').getStore().loadData(flightDtoList);
			};
			var failureFun = function(json){
				if(Ext.isEmpty(json)){
					pricing.showErrorMes(pricing.flightPrice.i18n('foss.pricing.requestTimedOut'));//请求超时
				}else{
					pricing.showErrorMes(json.message);
				}
			};
			var url = pricing.realPath('searchFlightInfo.action');
			pricing.requestJsonAjax(url,params,successFun,failureFun);
		}
	},
	//新增航空公司运价明细FORM
	flightPriceDetailForm:null,
	getFlightPriceDetailForm : function(){
    	if(Ext.isEmpty(this.flightPriceDetailForm)){
    		this.flightPriceDetailForm = Ext.create('Foss.pricing.FlightPriceDetailForm');
    	}
    	return this.flightPriceDetailForm;
    },
    //提交航空公司运价明细数据
    commitFlightPriceDetail:function(){
    	var me = this;
    	if(me.getFlightPriceDetailForm().getForm().isValid()){//校验form是否通过校验
    		var flightPriceDetailModel = new Foss.pricing.FlightPricePlanDetailEntity();
    		me.getFlightPriceDetailForm().getForm().updateRecord(flightPriceDetailModel);//将FORM中数据设置到MODEL里面
    		var flightPricePlanId = me.parent.up('window').flightPricePlanEntity.id;
    		flightPriceDetailModel.set('flightPricePlanId',flightPricePlanId);

    		/**
    		 * 数据库以分的单位来存入相关价格信息,以免丢失精度,前台新增时有关价格相关属性都应该划分存入
    		 */
			var minFee = flightPriceDetailModel.get('minFee')*100//最低一票
    		var down45Kg = flightPriceDetailModel.get('down45Kg')*100//45公斤以下
        	var up45Kg = flightPriceDetailModel.get('up45Kg')*100//45公斤以上
        	var up100Kg = flightPriceDetailModel.get('up100Kg')*100;//100公斤以上
    		var up300Kg = flightPriceDetailModel.get('up300Kg')*100;//300公斤以上
    		var up500Kg = flightPriceDetailModel.get('up500Kg')*100;//500公斤以上
    		var up1000Kg = flightPriceDetailModel.get('up1000Kg')*100;//1000公斤以上
    		var up2000Kg = flightPriceDetailModel.get('up2000Kg')*100;//2000公斤以上
    		var up3000Kg = flightPriceDetailModel.get('up3000Kg')*100;//3000公斤以上
    		
    		/**
    		 * 设置转换后的数据
    		 */
    		flightPriceDetailModel.set('minFee',minFee);//最低一票
    		flightPriceDetailModel.set('down45Kg',down45Kg);//45公斤以下
    		flightPriceDetailModel.set('up45Kg',up45Kg);//45公斤以上
    		flightPriceDetailModel.set('up100Kg',up100Kg);//100公斤以上
    		flightPriceDetailModel.set('up300Kg',up300Kg);//300公斤以上
    		flightPriceDetailModel.set('up500Kg',up500Kg);//500公斤以上
    		flightPriceDetailModel.set('up1000Kg',up1000Kg);//1000公斤以上
    		flightPriceDetailModel.set('up2000Kg',up2000Kg);//2000公斤以上
    		flightPriceDetailModel.set('up3000Kg',up3000Kg);//3000公斤以上
    		
    		var params = {'flightPriceVo':{'flightPricePlanDetailEntity':flightPriceDetailModel.data}};//组织新增数据
    		var successFun = function(json){
				pricing.showInfoMes(json.message);//提示新增成功
				me.close();
				me.parent.getPagingToolbar().moveFirst();//成功之后重新查询刷新结果集
			};
			var failureFun = function(json){
				if(Ext.isEmpty(json)){
					pricing.showErrorMes(pricing.flightPrice.i18n('foss.pricing.requestTimedOut'));//请求超时
				}else{
					pricing.showErrorMes(json.message);//提示失败原因
				}
			};
			var url = pricing.realPath('addFlightPriceDetail.action');//请求航空公司运价明细新增
			pricing.requestJsonAjax(url,params,successFun,failureFun);//发送AJAX请求
    	}
    },
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.fbar = [{
			text :pricing.flightPrice.i18n('i18n.pricingRegion.cancel'),//取消,
			handler :function(){
				me.close();
			}
		},{
			text : pricing.flightPrice.i18n('foss.pricing.reset'),//重置
			handler :function(){
					me.getFlightPriceDetailForm().getForm().loadRecord(new Foss.pricing.FlightPricePlanDetailEntity());
			} 
		},{
			text : pricing.flightPrice.i18n('foss.pricing.save'),//保存
			cls:'yellow_button',
			margin:'0 0 0 305',
			handler :function(){
				me.commitFlightPriceDetail();
			} 
		}];
		me.items = [me.getFlightPriceDetailForm()];
		me.callParent([cfg]);
	}
});

/**
 * 修改航空公司运价明细信息
 */
Ext.define('Foss.pricing.FlightPriceDetailUpdateWindow',{
	extend : 'Ext.window.Window',
	title : pricing.flightPrice.i18n('foss.pricing.modifyAirReservePriceProgramDetail'),//修改空运底价方案明细
	closable : true,
    parent:null,//父元素（弹出这个window的gird——Foss.pricing.FlightPriceGrid）
	modal : true,
	flightPricePlanDetailEntity:null,//航空运价明细实体
	resizable:true,//可以调整窗口的大小
	closeAction : 'hide',//点击关闭是隐藏窗口
	width :570,
	height :480,
	listeners:{
		beforehide:function(me){//隐藏WINDOW的时候清除数据
			me.getFlightPriceDetailForm().getForm().reset();//表格重置
		},
		beforeshow:function(me){//显示WINDOW的时候清除数据
			
			//航班号
			var airlines = me.parent.up('window').flightPricePlanEntity.airlinesCode;
			//配载部门
			var origCode = me.parent.up('window').flightPricePlanEntity.loadOrgCode;
			var params = {'flightPriceVo':{'flightEntity':{'airlines':airlines,'origCode':origCode}}};
			var successFun = function(json){
				var flightDtoList = json.flightPriceVo.flightDtoList;
				me.getFlightPriceDetailForm().getForm().findField('flightNo').getStore().loadData(flightDtoList);
				me.getFlightPriceDetailForm().getForm().loadRecord(new Foss.pricing.FlightPricePlanDetailEntity(me.flightPricePlanDetailEntity));
				me.getFlightPriceDetailForm().getForm().findField('destDistrictCode').setCombValue(me.flightPricePlanDetailEntity.destDistrictName,me.flightPricePlanDetailEntity.destDistrictCode);
			};
			var failureFun = function(json){
				if(Ext.isEmpty(json)){
					pricing.showErrorMes(pricing.flightPrice.i18n('foss.pricing.requestTimedOut'));//请求超时
				}else{
					pricing.showErrorMes(json.message);
				}
			};
			var url = pricing.realPath('searchFlightInfo.action');
			pricing.requestJsonAjax(url,params,successFun,failureFun);
		}
	},
	//新增航空公司运价明细FORM
	flightPriceDetailForm:null,
	getFlightPriceDetailForm : function(){
    	if(Ext.isEmpty(this.flightPriceDetailForm)){
    		this.flightPriceDetailForm = Ext.create('Foss.pricing.FlightPriceDetailForm');
    	}
    	return this.flightPriceDetailForm;
    },
    //提交航空公司运价明细数据
    commitFlightPriceDetail:function(){
    	var me = this;
    	if(me.getFlightPriceDetailForm().getForm().isValid()){//校验form是否通过校验
    		var flightPriceDetailModel = new Foss.pricing.FlightPricePlanDetailEntity(me.flightPricePlanDetailEntity);
    		me.getFlightPriceDetailForm().getForm().updateRecord(flightPriceDetailModel);//将FORM中数据设置到MODEL里面
    		var flightPricePlanId = me.parent.up('window').flightPricePlanEntity.id;
    		flightPriceDetailModel.set('flightPricePlanId',flightPricePlanId);
    		/**
    		 * 数据库以分的单位来存入相关价格信息,以免丢失精度,前台新增时有关价格相关属性都应该划分存入
    		 */
			var minFee = flightPriceDetailModel.get('minFee')*100//最低一票
    		var down45Kg = flightPriceDetailModel.get('down45Kg')*100//45公斤以下
        	var up45Kg = flightPriceDetailModel.get('up45Kg')*100//45公斤以上
        	var up100Kg = flightPriceDetailModel.get('up100Kg')*100;//100公斤以上
    		var up300Kg = flightPriceDetailModel.get('up300Kg')*100;//300公斤以上
    		var up500Kg = flightPriceDetailModel.get('up500Kg')*100;//500公斤以上
    		var up1000Kg = flightPriceDetailModel.get('up1000Kg')*100;//1000公斤以上
    		var up2000Kg = flightPriceDetailModel.get('up2000Kg')*100;//2000公斤以上
    		var up3000Kg = flightPriceDetailModel.get('up3000Kg')*100;//3000公斤以上
    		
    		/**
    		 * 设置转换后的数据
    		 */
    		flightPriceDetailModel.set('minFee',minFee);//最低一票
    		flightPriceDetailModel.set('down45Kg',down45Kg);//45公斤以下
    		flightPriceDetailModel.set('up45Kg',up45Kg);//45公斤以上
    		flightPriceDetailModel.set('up100Kg',up100Kg);//100公斤以上
    		flightPriceDetailModel.set('up300Kg',up300Kg);//300公斤以上
    		flightPriceDetailModel.set('up500Kg',up500Kg);//500公斤以上
    		flightPriceDetailModel.set('up1000Kg',up1000Kg);//1000公斤以上
    		flightPriceDetailModel.set('up2000Kg',up2000Kg);//2000公斤以上
    		flightPriceDetailModel.set('up3000Kg',up3000Kg);//3000公斤以上
    		
    		var params = {'flightPriceVo':{'flightPricePlanDetailEntity':flightPriceDetailModel.data}};//组织新增数据
    		var successFun = function(json){
				pricing.showInfoMes(json.message);//提示新增成功
				me.close();
				me.parent.getPagingToolbar().moveFirst();//成功之后重新查询刷新结果集
			};
			var failureFun = function(json){
				if(Ext.isEmpty(json)){
					pricing.showErrorMes(pricing.flightPrice.i18n('foss.pricing.requestTimedOut'));//请求超时
				}else{
					pricing.showErrorMes(json.message);//提示失败原因
				}
			};
			var url = pricing.realPath('updateFlightPriceDetail.action');//请求航空公司运价新增
			pricing.requestJsonAjax(url,params,successFun,failureFun);//发送AJAX请求
    	}
    },
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.fbar = [{
			text :pricing.flightPrice.i18n('i18n.pricingRegion.cancel'),//取消,
			handler :function(){
				me.close();
			}
		},{
			text : pricing.flightPrice.i18n('foss.pricing.reset'),//重置
			handler :function(){
					me.getFlightPriceDetailForm().getForm().loadRecord(new Foss.pricing.FlightPricePlanDetailEntity(me.flightPricePlanDetailEntity));
					me.getFlightPriceDetailForm().getForm().findField('destDistrictCode').setCombValue(me.flightPricePlanDetailEntity.destDistrictName,me.flightPricePlanDetailEntity.destDistrictCode);
			} 
		},{
			text : pricing.flightPrice.i18n('foss.pricing.save'),//保存
			cls:'yellow_button',
			margin:'0 0 0 305',
			handler :function(){
				me.commitFlightPriceDetail();
			} 
		}];
		me.items = [me.getFlightPriceDetailForm()];
		me.callParent([cfg]);
	}
});
/**
* 空运低价方案明细-FORM
*/
Ext.define('Foss.pricing.FlightPriceDetailForm', {
	extend : 'Ext.form.Panel',
	title : pricing.flightPrice.i18n('foss.pricing.airTransportOfLowCostProgramDetails'),//空运低价方案明细
	frame: true,
	flex:1,
	collapsible: true,
   defaults : {
   	margin : '5 5 5 5',
   	labelWidth:80,
    allowBlank:false,
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
			forceSelection : true,
			xtype : 'commoncityselector',
			name: 'destDistrictCode',//目的站
			fieldLabel : pricing.flightPrice.i18n('foss.pricing.destinationStation')//目的站
		},{
			name: 'goodsTypeCode',
			queryMode: 'local',
		    displayField: 'name',
		    valueField: 'code',
		    editable:false,
		    forceSelection:true,		    
		    store:pricing.getStore(null,'Foss.pricing.GoodEntity',null
		    ,pricing.goodsTypeFlightPlan),
	        fieldLabel: pricing.flightPrice.i18n('foss.pricing.theTypeGoods'),//货物类别
	        xtype : 'combo'
		},{

			queryMode: 'local',
		    displayField: 'flightNo',
		    valueField: 'flightNo',
		    forceSelection :true,
		    store:pricing.getStore(null,null,['flightNo','departureAirport','departureAirportName','destinationAirport','planLeaveTime','planArriveTime'],[]),
	        xtype : 'combo',
			name: 'flightNo',//航班号
			allowBlank:false,
	        fieldLabel: pricing.flightPrice.i18n('foss.pricing.flightNumber'),
	        listeners:{
	        	change:function(item,newvalue){
	        		if(!Ext.isEmpty(newvalue)){
	        			item.getStore().each(function(record){
	        				if(record.get('flightNo')==newvalue){
	        					item.up('form').getForm().loadRecord(record);
	        					item.up('form').getForm().findField('planLeaveTime').setValue(new Date(record.get('planLeaveTime')));
	        					item.up('form').getForm().findField('planArriveTime').setValue(new Date(record.get('planArriveTime')));
	        					return;
	        				}
	        			});
	        		}
	        	}
	        }
	        
		},{
			name: 'planLeaveTime',//起飞时间
	        fieldLabel: pricing.flightPrice.i18n('foss.pricing.timeOfDeparture'),
	        readOnly:true,
	        format:'H:i',
	        editable:false,
	        xtype : 'timefield'
		},{
			name: 'planArriveTime',//到达时间
	        fieldLabel: pricing.flightPrice.i18n('foss.pricing.arrivalTime'),
	        readOnly:true,
	        format:'H:i',
	        editable:false,
	        xtype : 'timefield'
		},{
			name: 'departureAirportName',//始发城市
            readOnly:true,
            allowBlank:true,
	        xtype: 'textfield',
			width:300,
	    	fieldLabel: pricing.flightPrice.i18n('foss.pricing.originatingCity'),
        	name:'description'
        
		},{
			name: 'minFee',//最低一票
	        fieldLabel: pricing.flightPrice.i18n('foss.pricing.theLowestVotes'),
	        decimalPrecision:0,
	        step:1,
	        allowBlank:false,
	        maxValue: 999999999,
	        minValue: 0,
	        xtype : 'numberfield'
		},{
			name: 'down45Kg',//45kg以下
	        fieldLabel: pricing.flightPrice.i18n('foss.pricing.45kgdown'),
	        decimalPrecision:2,
	        step:0.01,
	        allowBlank:false,
	        maxValue: 9999999.99,
	        minValue: 0,
	        xtype : 'numberfield'
		},{
			name: 'up45Kg',//45kg以上
	        fieldLabel: pricing.flightPrice.i18n('foss.pricing.45kgup'),
	        decimalPrecision:2,
	        step:0.01,
	        allowBlank:false,
	        maxValue: 9999999.99,
	        minValue: 0,
	        xtype : 'numberfield'
		},{
			name: 'up100Kg',//100kg以上
	        fieldLabel: pricing.flightPrice.i18n('foss.pricing.100kgup'),
	        decimalPrecision:2,
	        step:0.01,
	        allowBlank:false,
	        maxValue: 9999999.99,
	        minValue: 0,
	        xtype : 'numberfield'
		},{
			name: 'up300Kg',//300kg以上
	        fieldLabel: pricing.flightPrice.i18n('foss.pricing.300kgup'),
	        decimalPrecision:2,
	        step:0.01,
	        allowBlank:false,
	        maxValue: 9999999.99,
	        minValue: 0,
	        xtype : 'numberfield'
		},{
			name: 'up500Kg',//500kg以上
	        fieldLabel: pricing.flightPrice.i18n('foss.pricing.500kgup'),
	        decimalPrecision:2,
	        step:0.01,
	        allowBlank:false,
	        maxValue: 9999999.99,
	        minValue: 0,
	        xtype : 'numberfield'
		},{
			name: 'up1000Kg',//1000kg以上
	        fieldLabel: pricing.flightPrice.i18n('foss.pricing.1000kgup'),
	        decimalPrecision:2,
	        step:0.01,
	        allowBlank:false,
	        maxValue: 9999999.99,
	        minValue: 0,
	        xtype : 'numberfield'
		},{
			name: 'up2000Kg',//2000kg以上
	        fieldLabel: pricing.flightPrice.i18n('foss.pricing.2000kgup'),
	        decimalPrecision:2,
	        step:0.01,
	        allowBlank:false,
	        maxValue: 9999999.99,
	        minValue: 0,
	        xtype : 'numberfield'
		},{
			name: 'up3000Kg',//3000kg以上
	        fieldLabel: pricing.flightPrice.i18n('foss.pricing.3000kgup'),
	        decimalPrecision:2,
	        step:0.01,
	        allowBlank:false,
	        maxValue: 9999999.99,
	        minValue: 0,
	        xtype : 'numberfield'
		}];
		me.callParent([cfg]);
	}
});



/**
 * 立即中止价格方案 Window
 */
Ext.define('Foss.pricing.flightPrice.FlightPriceImmediatelyStopEndTimeWindow',{
		extend: 'Ext.window.Window',
		title: pricing.flightPrice.i18n('foss.pricing.immediatelySupendPricePriceScheme'),//"立即中止方案",
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
				var endTime = Ext.Date.format(new Date(me.flightPriceEntity.beginTime), 'Y-m-d H:i:s');
				var value = pricing.flightPrice.i18n('foss.pricing.showleftTimeInfo')
					  +beginTime+pricing.flightPrice.i18n('foss.pricing.showmiddleTimeInfo')
					  +endTime+pricing.flightPrice.i18n('foss.pricing.showrightEndTimeInfo');
				me.flightPriceEntity.showTime = value;
				me.flightPriceEntity.endTime = null;
				me.getFlightPriceStopFormPanel().getForm().loadRecord(new Foss.pricing.FlightPricePlanEntity(me.flightPriceEntity));
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
    		var flightPriceModel = new Foss.pricing.FlightPricePlanEntity(flightPriceEntity);
    		form.updateRecord(flightPriceModel);//将FORM中数据设置到MODEL里面
    		flightPriceModel.set('endTime',Ext.Date.parse(form.findField('endTime').getValue(), 'Y-m-d H:i:s'))
    		var params = {'flightPriceVo':{'flightPricePlanEntity':flightPriceModel.data}};
    		var url = pricing.realPath('immediatelyStopFlightPrice.action');
    		var successFun = function(json){
    			pricing.showInfoMes(json.message);
    			me.up('window').hide();
				pricing.pagingBar.moveFirst();   			
    	    };
    	    var failureFun = function(json){
    	    	if(Ext.isEmpty(json)){
    				pricing.showErrorMes(pricing.flightPrice.i18n('i18n.pricingRegion.requestTimeOut'));
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
				fieldLabel :pricing.flightPrice.i18n('foss.pricing.suspendTime') ,//'中止日期',
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
				text : pricing.flightPrice.i18n('i18n.pricingRegion.determine'),//"确认",
				handler : function() {
					var flightPriceEntity = me.up('window').flightPriceEntity;
					me.stopPricePlan(flightPriceEntity);
				}
			},{
				xtype : 'button', 
				width:70,
				columnWidth:.15,
				text : pricing.flightPrice.i18n('i18n.pricingRegion.cancel'),//"取消",
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
		title: pricing.flightPrice.i18n('foss.pricing.immediatelyActiveationPriceScheme'),//"立即激活方案",
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
				var endTime = Ext.Date.format(new Date(me.flightPriceEntity.beginTime), 'Y-m-d H:i:s');
				var value = pricing.flightPrice.i18n('foss.pricing.showleftTimeInfo')
					  +beginTime+pricing.flightPrice.i18n('foss.pricing.showmiddleTimeInfo')
					  +endTime+pricing.flightPrice.i18n('foss.pricing.showrightEndTimeInfo');
				me.flightPriceEntity.showTime = value;
				me.flightPriceEntity.beginTime = null;
				me.getFlightPriceImmediatelyActiveFormPanel().getForm().loadRecord(new Foss.pricing.FlightPricePlanEntity(me.flightPriceEntity));
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
    		var flightPriceModel = new Foss.pricing.FlightPricePlanEntity(flightPriceEntity);
    		form.updateRecord(flightPriceModel);//将FORM中数据设置到MODEL里面
    		flightPriceModel.set('id',flightPriceModel.data.id);
    		flightPriceModel.set('beginTime',Ext.Date.parse(form.findField('beginTime').getValue(), 'Y-m-d H:i:s'));
    		var params = {'flightPriceVo':{'flightPricePlanEntity':flightPriceModel.data}};
    		var url = pricing.realPath('immediatelyActiveFlightPrice.action');
    		var successFun = function(json){
    			pricing.showInfoMes(json.message);
    			me.up('window').hide();
				pricing.pagingBar.moveFirst();   			
    	    };
    	    var failureFun = function(json){
    	    	if(Ext.isEmpty(json)){
    				pricing.showErrorMes(pricing.flightPrice.i18n('i18n.pricingRegion.requestTimeOut'));
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
				fieldLabel:pricing.flightPrice.i18n('foss.pricing.availabilityDate'),//'生效日期',
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
				text : pricing.flightPrice.i18n('i18n.pricingRegion.determine'),//,"确认",
				handler : function() {
					var flightPriceEntity = me.up('window').flightPriceEntity;
					me.activetionPricePlan(flightPriceEntity);
				}
			},{
				xtype : 'button', 
				width:70,
				columnWidth:.15,
				text : pricing.flightPrice.i18n('i18n.pricingRegion.cancel'),//"取消",
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
Ext.define('Foss.pricing.flightPrice.UploadFlightPriceform',{
	extend:'Ext.window.Window',
	title:pricing.flightPrice.i18n('foss.pricing.importPriceScheme'),
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
				fieldLabel:pricing.flightPrice.i18n('foss.pricing.pleaseSelectAttachments'),
				labelWidth:100,
				buttonText:pricing.flightPrice.i18n('foss.pricing.browse'),
				flex:3
			}]
		}];
		me.fbar = me.getFbar();
		me.callParent([cfg]);
	},
	getFbar:function(){
		var me = this;
		return [{
			text:pricing.flightPrice.i18n('foss.pricing.import'),
			xtype:'button',
			scope:me,
			handler:me.uploadFile
		},{
			text:pricing.flightPrice.i18n('i18n.pricingRegion.cancel'),
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
			if(Ext.isEmpty(json.flightPriceVo.numList)){
				pricing.showInfoMes(pricing.flightPrice.i18n('foss.pricing.allDataImportSuccess'));//全部数据导入成功！
				me.close();
			}else{
				var message = pricing.flightPrice.i18n('foss.pricing.first');
				for(var i = 0;i<json.flightPriceVo.numList;i++){
					message = message+json.flightPriceVo.numList[i]+','
				}
				message = message+pricing.flightPrice.i18n('foss.pricing.lineImportSuccess');
				pricing.showWoringMessage(message);
			}
		};
		var failureFn = function(json){
			if(Ext.isEmpty(json)){
				pricing.showErrorMes(pricing.flightPrice.i18n('i18n.pricingRegion.requestTimeOut'));
			}else{
				pricing.showErrorMes(json.message);
			}
		};
		var form = me.down('form').getForm();
		var url = pricing.realPath('importFlightPrice.action');
		form.submit({
            url: url,
            waitMsg: pricing.flightPrice.i18n('foss.pricing.uploadYourAttachment'),
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





