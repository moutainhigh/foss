/**
 * 该模块维护FOSS汽运价格信息,通过与区域、产品条目、
 * 来设置不同维度的价格信息（上海-北京 精准-通用 重货2.8元/斤 轻货250/立方 最低一票）
 * 
 */

/**
 * 
 * @type String 激活
 */
pricing.carloadPricingFloat.yes = 'Y';
/**
 * 
 * @type  价格方案ID
 */
pricing.carloadPricingFloatId = null;
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
			Ext.Msg.alert(pricing.carloadPricingFloat.i18n('foss.pricing.promptMessage'),result.message);
		}
	});
};

/**
 * 价格方案明细模型
 */
Ext.define('Foss.pricing.carloadPricingFloat.PricePlanDetailDtoModel', {
    extend: 'Ext.data.Model',
    fields : [{
        name : 'id',//整车价格参数明细ID
        type : 'string'
    },{
        name : 'carloadPriceId',//整车价格参数方案ID
        type : 'string'
    },{
        name : 'invoiceType',//发票标记
        type : 'string'
    },{
        name : 'createTime', //创建时间
        type : 'date',
        defaultValue : null,
        convert : pricing.changeLongToDate
    },{
        name : 'floatUp'//整车价格
    },{
        name : 'floatDown'//轻货价格 
    },{
        name : 'otherCostParameter',//其他成本参数
    },{
        name : 'packageFeeParameter'//包装费参数
    },{
        name : 'weightParameter'//重量参数 
    },{
        name : 'carCostParameter'//车价参数 
    },{
        name : 'humanCostParameter'//人力成本参数
    },{
        name : 'remark',//备注
        type : 'string'
    }]
});

/**
 * 产品条目数组 - 用于方案明细信息中添加产品条目信息填充数据使用
 */
pricing.queryProductItemEntityList = [];
pricing.queryProductItemEntityAddList = [];

/**
 * 空运产品
 */
pricing.AIR_FREIGHT='AIR_FREIGHT'
/**
 * 货物类型定义-通用
 */
pricing.GOODS_TYPE_ALL = 'H00001'

 
pricing.searchProductItemEntityList = function(){
	Ext.Ajax.request({
		url:pricing.realPath('queryProductItemLevel2.action'),//查询产品条目
		async:false,
		success:function(response){
			var result = Ext.decode(response.responseText);
			pricing.queryProductItemEntityList = result.productItemVo.productItemEntityList;
			var countries = pricing.queryProductItemEntityList;
			var arrays = new Array(); 
			var nullproduct = {
				name:pricing.carloadPricingFloat.i18n('i18n.pricingRegion.all'),//"全部",
				id:"",
				code:""						
			};
			pricing.queryProductItemEntityList.push(nullproduct);
			Ext.Array.each(countries, function(countrie, index, countriesItSelf) {
				//过滤空运产品、和筛选汽运的货物类型, 原因是汽运目前没有按照货物类型来分类。 固目前只会按照货物类型为通用来处理
				if(countrie.productCode != pricing.AIR_FREIGHT && countrie.goodstypeCode == pricing.GOODS_TYPE_ALL){
					arrays.push(countrie);
				}
			});
			pricing.queryProductItemEntityList = arrays;
		},
		failure:function(response){
			var result = Ext.decode(response.responseText);
			if(Ext.isEmpty(result)){
				pricing.showErrorMes(pricing.carloadPricingFloat.i18n('i18n.pricingRegion.requestTimeOut'));
			}else{
				pricing.showErrorMes(result.message);
			}
		}
	});
};


pricing.searchProductItemEntityAddList = function(){
	Ext.Ajax.request({
		url:pricing.realPath('queryProductItemLevel2.action'),//查询产品条目
		async:false,
		success:function(response){
			var result = Ext.decode(response.responseText);
			pricing.queryProductItemEntityAddList = result.productItemVo.productItemEntityList;
			var countries = pricing.queryProductItemEntityAddList;
			var arrays = new Array(); 
			Ext.Array.each(countries, function(countrie, index, countriesItSelf) {
				//过滤空运产品、和筛选汽运的货物类型, 原因是汽运目前没有按照货物类型来分类。 固目前只会按照货物类型为通用来处理
				if(countrie.productCode != pricing.AIR_FREIGHT && countrie.goodstypeCode == pricing.GOODS_TYPE_ALL){
					arrays.push(countrie);
				}
			});
			pricing.queryProductItemEntityAddList = arrays;
		},
		failure:function(response){
			var result = Ext.decode(response.responseText);
			if(Ext.isEmpty(result)){
				pricing.showErrorMes(pricing.carloadPricingFloat.i18n('i18n.pricingRegion.requestTimeOut'));
			}else{
				pricing.showErrorMes(result.message);
			}
		}
	});
};

/**
 * 价格方案批次model
 */
Ext.define('Foss.pricing.carloadPricingFloat.PricePlanModel', {
    extend: 'Ext.data.Model',
    fields : [{
        name : 'id',
        type : 'string'
    },{
        name : 'modifyTime',
        type : 'date',
        defaultValue : null,
        convert : pricing.changeLongToDate
    },{
        name : 'modifyUser',
        type : 'string'
    },{
    	name : 'scenarioName',
        type : 'string'
    },{
    	name : 'organizationID',
        type : 'string'
    },{
    	name : 'organizationCode',
        type : 'string'
    },{
    	name : 'organizationName',
        type : 'string'
    },{
        name : 'active',
        type : 'string'
    },{
        name : 'currentUsedVersion',
        type : 'string'
    },{
        name : 'serialNo',
        type : 'string'
    },{
        name : 'createTime',
        type : 'date',
        defaultValue : null,
        convert : pricing.changeLongToDate
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
        name : 'createUser',
        type : 'string'
    },{
        name : 'invoiceType',
        type : 'string'
    },{
        name : 'floatUp',
        type : 'string'
    },{
        name : 'floatDown',
        type : 'string'
    },{
        name : 'remark',
        type : 'string'
    }]
});

/**
 * 价格方案批次store
 */
Ext.define('Foss.pricing.carloadPricingFloat.PricePlanStore',{
	extend: 'Ext.data.Store',
	model: 'Foss.pricing.carloadPricingFloat.PricePlanModel',
	pageSize:20,
	proxy: {
		type : 'ajax',
		actionMethods:'POST',
		url: pricing.realPath('queryCarloadPriceAction.action'),
		reader : {
			type : 'json',
			root : 'carloadManageMeantVo.carloadPricePlanDtos',
			totalProperty : 'totalCount',
			successProperty: 'success'
		}
	},
	listeners: {
		beforeload: function(store, operation, eOpts){
			var n = pricing.queryform.getValues();
			Ext.apply(operation,{
				params : {
					'carloadManageMeantVo.carloadPricePlanDto.scenarioName': n.scenarioName,
					'carloadManageMeantVo.carloadPricePlanDto.organizationCode': n.organizationCode,
					'carloadManageMeantVo.carloadPricePlanDto.active': n.active,
					'carloadManageMeantVo.carloadPricePlanDto.currentUsedVersion': n.currentUsedVersion
				}
			});			
		}
	}
});
/**
 * 价格方案明细store
 */
Ext.define('Foss.pricing.carloadPricingFloat.PricePlanDeatilStore',{
	extend: 'Ext.data.Store',
	model: 'Foss.pricing.carloadPricingFloat.PricePlanDetailDtoModel',
	pageSize:10,
	proxy: {
		type : 'ajax',
		actionMethods:'POST',
		url: pricing.realPath('queryCarloadPricePlanDetailInfo.action'),
		reader : {
			type : 'json',
			root : 'carloadManageMeantVo.carloadPriceDetailEntityList',
			totalProperty : 'totalCount',
			successProperty: 'success'
		}
	}
});


/**
 * 价格方案批次查询form表单
 */
Ext.define('Foss.pricing.carloadPricingFloat.PricePlanFormPanel', {
	extend : 'Ext.form.Panel',
	title: pricing.carloadPricingFloat.i18n('i18n.pricingRegion.searchCondition'),
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
		fieldLabel:pricing.carloadPricingFloat.i18n('foss.pricing.scenarioName'),//方案名称
		name: 'scenarioName',
        maxLength : 60,
        maxLengthText:pricing.carloadPricingFloat.i18n('foss.pricing.theProgramNameLengthExceedsTheMaximumLimit'),
	    allowBlank:true,
	    columnWidth:.3
	},{
		xtype: 'container',
		columnWidth:.2,
		html: '&nbsp;'
	},{
		name: 'organizationCode',
	    fieldLabel:pricing.carloadPricingFloat.i18n('foss.pricing.sectorOrganizations'),//组织
	    xtype : 'dynamicorgcombselector',
	    columnWidth:.3
	},{
		columnWidth : .3,
		name: 'active',
		queryMode: 'local',
	    displayField: 'value',
	    value:'ALL',
	    valueField: 'key',
	    editable:false,
	    store:pricing.getStore('Foss.pricing.region.AreaStatusStore',null,['key','value'],
	     [{'key':'N','value':pricing.carloadPricingFloat.i18n('i18n.pricingRegion.unActive')},
	      {'key':'Y','value':pricing.carloadPricingFloat.i18n('i18n.pricingRegion.active')},
	      {'key':'ALL','value':pricing.carloadPricingFloat.i18n('i18n.pricingRegion.all')}]),
	    fieldLabel: pricing.carloadPricingFloat.i18n('foss.pricing.status'),//状态
	    xtype : 'combo'
	},{
		xtype: 'container',
		columnWidth:.2,
		html: '&nbsp;'
	},{
		columnWidth : .3,
		name: 'currentUsedVersion',
		queryMode: 'local',
	    displayField: 'value',
	    value:'ALL',
	    valueField: 'key',
	    editable:false,
	    store:pricing.getStore('Foss.pricing.region.VersionStatusStore',null,['key','value']
	    ,[{'key':'N','value':pricing.carloadPricingFloat.i18n('i18n.pricingRegion.no')},{'key':'Y','value':pricing.carloadPricingFloat.i18n('i18n.pricingRegion.ye')}
	    ,{'key':'ALL','value':pricing.carloadPricingFloat.i18n('i18n.pricingRegion.all')}]),
	    fieldLabel:pricing.carloadPricingFloat.i18n('foss.pricing.currentUsedVersion'),//当前版本
	    xtype : 'combo'
	}];
	me.fbar = [{
			xtype : 'button', 
			width:70,
			left : 1,
			text : pricing.carloadPricingFloat.i18n('foss.pricing.reset'),//重置
			margin:'0 10 0 0',
			handler : function() {
				me.getForm().reset();
			}
		},{
			xtype : 'container',
			margin : '0 208 0 0',
			items : [{
				xtype : 'button', 
				width:70,
				cls:'yellow_button',
				text : pricing.carloadPricingFloat.i18n('i18n.pricingRegion.search'),//"查询",
				disabled: !pricing.carloadPricingFloat.isPermission('carloadPricingFloat/pricePlanQueryButton'),
				hidden: !pricing.carloadPricingFloat.isPermission('carloadPricingFloat/pricePlanQueryButton'),
				handler : function() {
					//var beginTime = me.getForm().findField('beginTime').getValue();
					//var endTime = me.getForm().findField('endTime').getValue();
					//if(!Ext.isEmpty(beginTime) && !Ext.isEmpty(endTime)){
						//if(beginTime>endTime){
							//pricing.showWoringMessage(pricing.carloadPricingFloat.i18n('foss.pricing.deadlineForInputGreaterThanEfectiveDate'));//截止日期要大于起始日期！
			    			//return;
						//}
					//}
					var grid = Ext.getCmp('T_pricing-carloadPricingFloatIndex_content').getPricePlanGridPanel();
					grid.getPagingToolbar().moveFirst();
				}
			}]
			}];
		me.callParent([cfg]);
	}
});

/**
 * 价格方案批次列表gird
 */
Ext.define('Foss.pricing.carloadPricingFloat.PricePlanGridPanel',{
	extend: 'Ext.grid.Panel',
	title : pricing.carloadPricingFloat.i18n('i18n.pricingRegion.searchResults'),//查询结果
	emptyText: pricing.carloadPricingFloat.i18n('foss.pricing.theQueryResultIsEmpty'),//查询结果为空
	frame: true,
	//sortableColumns:false,
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
			this.addPricePlanWindow = Ext.create('Foss.pricing.carloadPricingFloat.PricePlanAddWindow');
			this.addPricePlanWindow.parent = this;
		}else{
			this.addPricePlanWindow.getPricePlanAddFormPanel().getForm().reset();
			this.addPricePlanWindow.getPricePlanAddFormPanel().getForm().findField('orgCodes').orgCodes=null;
		}
		return this.addPricePlanWindow;
	},
	
	updatePricePlanWindow : null,
	//获取修改价格方案窗口
	getUpdatePricePlanWindow : function(){
		if(Ext.isEmpty(this.updateStandardWindow)){
			this.updatePricePlanWindow = Ext.create('Foss.pricing.carloadPricingFloat.PricePlanUpdateWindow');
			//设置器父元素
			this.updatePricePlanWindow.parent = this;
		}
		return this.updatePricePlanWindow;
	},
	
	
	//中止方案弹出日期选择
	stopPricePlanWindow:null,
	getStopPricePlanWindow: function(pricePlanId){
		if(Ext.isEmpty(this.stopPricePlanWindow)){
			this.stopPricePlanWindow = Ext.create('Foss.pricing.carloadPricingFloat.PricePlanStopEndTimeWindow');
			this.stopPricePlanWindow.parent = this;
		}
		this.stopPricePlanWindow.pricePlanId = pricePlanId;
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
	getimmediatelyActivePricePlanWindow: function(pricePlanEntity){
		if(Ext.isEmpty(this.immediatelyActivePricePlanWindow)){
			this.immediatelyActivePricePlanWindow = Ext.create('Foss.pricing.carloadPricingFloat.PricePlanImmediatelyActiveTimeWindow');
			this.immediatelyActivePricePlanWindow.parent = this;
		}
		this.immediatelyActivePricePlanWindow.pricePlanEntity = pricePlanEntity;
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
	getImmediatelyStopPricePlanWindow: function(pricePlanEntity){
		if(Ext.isEmpty(this.immediatelyStopPricePlanWindow)){
			this.immediatelyStopPricePlanWindow = Ext.create('Foss.pricing.carloadPricingFloat.PricePlanImmediatelyStopEndTimeWindow');
			this.immediatelyStopPricePlanWindow.parent = this;
		}
		this.immediatelyStopPricePlanWindow.pricePlanEntity = pricePlanEntity;
		return this.immediatelyStopPricePlanWindow;
	},
	
	
	/**
	 * 立即中止
	 */
    immediatelyStopPricePlan:function(){
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
	 	if(selections[0].get('active')!=pricing.carloadPricingFloat.yes){
	 		pricing.showWoringMessage('请选择已激活数据进行操作！');
	 		return;
	 	}else{
	 		var pricePlanEntity = selections[0].data;
	 		var window = me.getImmediatelyStopPricePlanWindow(pricePlanEntity);
	 		window.show();
	 	}
	},
	
	/**
	 * 立即激活
	 */
    immediatelyActiveCarloadPricePlan:function(){
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
	 	if(selections[0].get('active')==pricing.carloadPricingFloat.yes){
	 		pricing.showWoringMessage('请选择未激活数据进行操作！');
	 		return;
	 	}else{
	 		var pricePlanEntity = selections[0].data;
	 		var window = me.getimmediatelyActivePricePlanWindow(pricePlanEntity);
	 		window.show();
	 	}
	},
	
	
	//返回批次store
	pricePlanStore:null,
	getPricePlanStore: function(){
		if(Ext.isEmpty(this.pricePlanStore)){
			this.pricePlanStore = Ext.create('Foss.pricing.carloadPricingFloat.PricePlanStore');
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
				plugins: 'pagesizeplugin',
				pageSize : 20
			});
		}
		return this.pagingToolbar;
	},
	//查看详情界面
	pricePlanDetailShowWindow:null,
	getPricePlanDetailShowWindow:function(){
		if(Ext.isEmpty(this.pricePlanDetailShowWindow)){
			this.pricePlanDetailShowWindow = Ext.create('Ext.pricing.carloadPricingFloat.PricePlanDetailShowWindow');	
			this.pricePlanDetailShowWindow.parent = this;
		}
		return this.pricePlanDetailShowWindow;
	},
	
	//删除价格方案
	deleteCarloadPricePlan:function(){
	 	var me = this;
	 	var selections = me.getSelectionModel().getSelection();
	 	if(selections.length < 1){
	 		pricing.showWoringMessage(pricing.carloadPricingFloat.i18n('foss.pricing.pleaseSelectOneDeleteOperation'));
			return;
	 	}
	 	//过滤草稿状态数据进行删除
	 	for(var i = 0;i<selections.length;i++){
			if(selections[i].get('active')==pricing.carloadPricingFloat.yes){
				pricing.showWoringMessage(pricing.carloadPricingFloat.i18n('foss.pricing.pleaseChooseToNotActivateTheDataToBeBeleted'));
				return;
			}
		}
		//是否要删除这些汽运价格方案？
		pricing.showQuestionMes(pricing.carloadPricingFloat.i18n('foss.pricing.theseTheQiyunPriceProgramYouWantToRemove'),function(e){
			if(e=='yes'){
				//汽运价格方案
				var idList = new Array();
				for(var i = 0 ; i<selections.length ; i++){
					idList.push(selections[i].get('id'));
				}
				var params = {'carloadManageMeantVo':{'carPlanIds':idList}};
				var successFun = function(json){
					pricing.showInfoMes(json.message);
					me.getPagingToolbar().moveFirst();
				};
				var failureFun = function(json){
					if(Ext.isEmpty(json)){
						pricing.showErrorMes(pricing.carloadPricingFloat.i18n('foss.pricing.requestTimedOut'));//请求超时
					}else{
						pricing.showErrorMes(json.message);
					}
				};
				var url = pricing.realPath('deleteCarloadPricePlan.action');
				pricing.requestJsonAjax(url,params,successFun,failureFun);
			}
		});
	},
	
	//激活整车价格参数方案
	activeCarloadPricePlan:function(){
    	var me = this;
		var pricePlans = new Array();
		//获取选中的数据
		var selections = me.getSelectionModel().getSelection();
		if(selections.length<1){
			pricing.showErrorMes(pricing.carloadPricingFloat.i18n('foss.pricing.pleaseChooseThePriceYouWantToActivateTheProgram'));//请选择要激活的价格方案！
			return;
		}
		//过滤草稿状态数据进行激活
	 	for(var i = 0;i<selections.length;i++){
			if(selections[i].get('active')==pricing.carloadPricingFloat.yes){
				pricing.showWoringMessage(pricing.carloadPricingFloat.i18n('foss.pricing.pleaseSelectNotActivateProgramDataForActivation'));
				return;
			}
			pricePlans.push(selections[i].get('id'));
		}
		if(pricePlans.length<1){
			pricing.showErrorMes(pricing.carloadPricingFloat.i18n('foss.pricing.pleaseSelectAtLeastOneInactivePriceProgram'));//请至少选择一条未激活的价格方案！
			return;
		}
		//是否要激活这些汽运价格方案？
		pricing.showQuestionMes(pricing.carloadPricingFloat.i18n('foss.pricing.theseQiyunPriceProgramWhetherYouWantActivate'),function(e){
			if(e=='yes'){
				var params = {'carloadManageMeantVo':{'carPlanIds':pricePlans}};
				var successFun = function(json){
					pricing.showInfoMes(json.message);
					me.getPagingToolbar().moveFirst();
				};
				var failureFun = function(json){
					if(Ext.isEmpty(json)){
						pricing.showErrorMes(pricing.carloadPricingFloat.i18n('i18n.pricingRegion.requestTimeOut'));//请求超时
					}else{
						pricing.showErrorMes(json.message);
					}
				};
				var url = pricing.realPath('activeCarloadPricePlan.action');
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
			this.uploadPriceform = Ext.create('Foss.pricing.carloadPricingFloat.UploadPriceform');	
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
			this.queryPricePlanForm  = Ext.create('Foss.pricing.carloadPricingFloat.PricePlanFormPanel')
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
			text : pricing.carloadPricingFloat.i18n('i18n.pricingRegion.num')//序号 
		},{
			text : pricing.carloadPricingFloat.i18n('i18n.pricingRegion.opra'),
			align : 'center',
			xtype : 'actioncolumn',
			items: [{
				iconCls: 'deppon_icons_showdetail',
                tooltip: pricing.carloadPricingFloat.i18n('foss.pricing.details'), 
                disabled: !pricing.carloadPricingFloat.isPermission('carloadPricingFloat/pricePlanQueryDetailButton'),
				width:42,
                handler: function(grid, rowIndex, colIndex) {
    				var record=grid.getStore().getAt(rowIndex);
                	me.getPricePlanDetailShowWindow().show();
                	me.getPricePlanDetailShowWindow().pricePlanId = record.get('id');
                }
			},{
				iconCls:'deppon_icons_edit',
				tooltip: pricing.carloadPricingFloat.i18n('foss.pricing.toAmendTheProposal'), 
				disabled: !pricing.carloadPricingFloat.isPermission('carloadPricingFloat/pricePlanUpdateButton'),
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
                	var params = {'carloadManageMeantVo':{'carloadPricePlanDto':record.data}};
    				var successFun = function(json){
						updateWindow.pricePlanEntity = json.carloadManageMeantVo.carloadPriceEntity;
						updateWindow.carloadPricePlanDtoList = json.carloadManageMeantVo.carloadPricePlanDtos;
						updateWindow.carloadPriceDetailEntityList = json.carloadManageMeantVo.carloadPriceDetailEntityList;
    					pricing.carloadPricingFloatId = json.carloadManageMeantVo.carloadPriceEntity.id;
    					updateWindow.isUpdate = true;
    					updateWindow.show();
    					pricing.pagingBar.moveFirst();
    				};
    				var failureFun = function(json){
    					if(Ext.isEmpty(json)){
    						pricing.showErrorMes(pricing.carloadPricingFloat.i18n('foss.pricing.requestTimedOut'));//请求超时
    					}else{
    						pricing.showErrorMes(json.message);
    					}
    				};
    				var url = pricing.realPath('queryCarloadPricePlanAndOrgInfo.action');
    				pricing.requestJsonAjax(url,params,successFun,failureFun);
				}
			},{
				iconCls:'deppon_icons_softwareUpgrade',
				tooltip: pricing.carloadPricingFloat.i18n('foss.pricing.replicationScheme'), 
				disabled: !pricing.carloadPricingFloat.isPermission('carloadPricingFloat/pricePlanCopyButton'),
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
						pricing.showErrorMes(pricing.carloadPricingFloat.i18n('foss.pricing.pleaseSelectTheProgramHaBbeenActivatedCopy'));
					}else{
						pricing.showQuestionMes(pricing.carloadPricingFloat.i18n('foss.pricing.toDeterminePriceProgramCopy'),function(e){
							if(e=='yes'){
								var me = this;
								//处理复制功能
								var updateWindow =  grid.up().getUpdatePricePlanWindow();
								var params = {'carloadManageMeantVo':{'carloadPricePlanDto':record.data}};
								var successFun = function(json){
									pricing.showInfoMes(json.message);
									updateWindow.pricePlanEntity = json.carloadManageMeantVo.carloadPriceEntity;
									updateWindow.carloadPricePlanDtoList = json.carloadManageMeantVo.carloadPricePlanDtos;
									updateWindow.carloadPriceDetailEntityList = json.carloadManageMeantVo.carloadPriceDetailEntityList;
									pricing.carloadPricingFloatId = json.carloadManageMeantVo.carloadPriceEntity.id;
			    					updateWindow.isUpdate = true;
			    					updateWindow.show();
			    					pricing.pagingBar.moveFirst();
								};
								var failureFun = function(json){
									if(Ext.isEmpty(json)){
										pricing.showErrorMes(pricing.carloadPricingFloat.i18n('foss.pricing.requestTimedOut'));//请求超时
									}else{
										pricing.showErrorMes(json.message);
									}
								};
								var url = pricing.realPath('copyCarloadPricePlan.action');
								pricing.requestJsonAjax(url,params,successFun,failureFun);
							}
						});
					}
				}
			},{
				iconCls:'deppon_icons_end',
				tooltip: pricing.carloadPricingFloat.i18n('foss.pricing.stop'), 
				disabled: !pricing.carloadPricingFloat.isPermission('carloadPricingFloat/pricePlanStopButton'),
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
			text: pricing.carloadPricingFloat.i18n('foss.pricing.scenarioName'), //"方案名称", 
	        dataIndex: 'scenarioName'
		},{
			width: 140,
			text: pricing.carloadPricingFloat.i18n('foss.pricing.organizationCode'),//"组织编码",
	        dataIndex: 'organizationCode'
		},{
			width: 140,
			text: pricing.carloadPricingFloat.i18n('foss.pricing.organizationName'),//"组织名称",
	        dataIndex: 'organizationName'
		},{
			width: 80,
			text: pricing.carloadPricingFloat.i18n('foss.pricing.dataType'),//"数据状态",
	        sortable: true,
	        dataIndex: 'active',
	        renderer:function(value){
				if(value=='Y'){//'Y'表示激活
					return pricing.carloadPricingFloat.i18n('i18n.pricingRegion.active')//"已激活";
				}else if(value=='N'){//'N'表示未激活
					return  pricing.carloadPricingFloat.i18n('i18n.pricingRegion.unActive')//"未激活";
				}else{
					return '';
				}
			}
		},{
			text: pricing.carloadPricingFloat.i18n('foss.pricing.availabilityDate'),//"生效日期",
	        width: 80,
	        dataIndex: 'beginTime',
	        renderer:function(value){
				return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
			}
		},{
			text: pricing.carloadPricingFloat.i18n('foss.pricing.endTimeTwo'),//"截止日期",
	        width: 80,
	        dataIndex: 'endTime',
	        renderer:function(value){
				return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
			}
		},{
			text : '是否当前版本',
			dataIndex : 'currentUsedVersion',
			renderer:function(value){
				if(value=='Y'){//'Y'表示是
					return pricing.carloadPricingFloat.i18n('i18n.pricingRegion.ye')//"是";
				}else if(value=='N'){//'N'表示未激活
					return  pricing.carloadPricingFloat.i18n('i18n.pricingRegion.no')//"否";
				}else{
					return '';
				}
			}
		},{
			text: pricing.carloadPricingFloat.i18n('foss.pricing.updateTime'),//"修改时间",
			width: 140,
	        dataIndex: 'modifyTime',
	        renderer:function(value){
				return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
			}
		},{
			text: pricing.carloadPricingFloat.i18n('foss.pricing.modifyUser'),//"修改人",
			width: 140,
	        dataIndex: 'modifyUser'
		}];
		me.tbar = [
		{
			text : pricing.carloadPricingFloat.i18n('foss.pricing.theNewScheme'),//"新建方案",
			disabled: !pricing.carloadPricingFloat.isPermission('carloadPricingFloat/pricePlanAddButton'),
			hidden: !pricing.carloadPricingFloat.isPermission('carloadPricingFloat/pricePlanAddButton'),
			handler :function(){
				var addWindow = me.getAddpricePlanWindow();
				addWindow.isUpdate = false;
				addWindow.show();
				
			} 
		},'-', {
			text : pricing.carloadPricingFloat.i18n('foss.pricing.activationProgram'),//"激活方案"
			disabled:!pricing.carloadPricingFloat.isPermission('carloadPricingFloat/pricePlanImmediatelyActiveButton'),
			hidden:!pricing.carloadPricingFloat.isPermission('carloadPricingFloat/pricePlanImmediatelyActiveButton'),
			handler :function(){
				me.activeCarloadPricePlan();
			} 
		},'-', {
			text : pricing.carloadPricingFloat.i18n('foss.pricing.deleteProgram'),  //"删除方案"
			disabled: !pricing.carloadPricingFloat.isPermission('carloadPricingFloat/pricePlanDeleteButton'),
			hidden: !pricing.carloadPricingFloat.isPermission('carloadPricingFloat/pricePlanDeleteButton'),
			handler :function(){
				me.deleteCarloadPricePlan();
			} 
		},'-', {
			text : pricing.carloadPricingFloat.i18n('foss.pricing.immediatelyActivationProgram'),//'立即激活',
			disabled:!pricing.carloadPricingFloat.isPermission('carloadPricingFloat/pricePlanImmediatelyActiveButton'),
			hidden:!pricing.carloadPricingFloat.isPermission('carloadPricingFloat/pricePlanImmediatelyActiveButton'),
			handler :function(){
				me.immediatelyActiveCarloadPricePlan();
			} 
		},'-', {
			text : pricing.carloadPricingFloat.i18n('foss.pricing.immediatelyStopProgram'),//'立即中止',
			disabled:!pricing.carloadPricingFloat.isPermission('carloadPricingFloat/pricePlanImmediatelyStopButton'),
			hidden:!pricing.carloadPricingFloat.isPermission('carloadPricingFloat/pricePlanImmediatelyStopButton'),
			handler :function(){
				me.immediatelyStopPricePlan();
			} 
		},'-',{
			text : pricing.carloadPricingFloat.i18n('foss.pricing.export'),
			disabled:!pricing.carloadPricingFloat.isPermission('carloadPricingFloat/pricePlanExportButton'),
			hidden:!pricing.carloadPricingFloat.isPermission('carloadPricingFloat/pricePlanExportButton'),
		    //插件配置代码
		    plugins: {
		        ptype: 'buttondisabledplugin',
		        seconds: 8
		    },
			handler :function(){
				//var queryForm = me.getQueryPricePlanForm();
				var queryForm = Ext.getCmp('T_pricing-carloadPricingFloatIndex_content').getQueryPricePlanForm();
				var pricePlanExport = '';
				var scenarioName = queryForm.getForm().findField('scenarioName').getValue(); // 方案名称
				var organizationCode = queryForm.getForm().findField('organizationCode').getValue(); //组织编码
				var active = queryForm.getForm().findField('active').getValue(); //数据状态  
				var currentUsedVersion = queryForm.getForm().findField('currentUsedVersion').getValue(); //是否当前版本
				
				pricePlanExport ='carloadManageMeantVo.carloadPricePlanDto.active='+active;
				
				pricePlanExport =pricePlanExport+'&'+'carloadManageMeantVo.carloadPricePlanDto.currentUsedVersion='+currentUsedVersion;
				
				if(!Ext.isEmpty(scenarioName)){
					pricePlanExport = pricePlanExport+'&'+'carloadManageMeantVo.carloadPricePlanDto.scenarioName='+scenarioName;
				}
				if(!Ext.isEmpty(organizationCode)){
					if(!Ext.isEmpty(pricePlanExport)){
						pricePlanExport = pricePlanExport+'&'+'carloadManageMeantVo.carloadPricePlanDto.organizationCode='+organizationCode;
					}else{
						pricePlanExport = 'carloadManageMeantVo.carloadPricePlanDto.organizationCode='+organizationCode;
					}
				}
				var url = pricing.realPath('exportCarloadPricePlan.action');
				if(!Ext.isEmpty(pricePlanExport)){
					url = url+'?'+pricePlanExport;
				}
				//window.location=url;
				//解决乱码问题
				window.location=encodeURI(encodeURI(url))
				pricePlanExport = '';
 			} 
		}];
		pricing.pagingBar = me.bbar;
		me.callParent([cfg]);
	}
 	
});
//-------------------查询详情------------------
/**
 * 明细信息查看WINDOW
 */
Ext.define('Ext.pricing.carloadPricingFloat.PricePlanDetailShowWindow',{
	extend : 'Ext.window.Window',
	title: '整车价格波动参数明细查询',//
	closable : true,
	modal : true,
	resizable:false,
	closeAction : 'hide',
	parent:null,//(Foss.pricing.carloadPricingFloat.PricePlanGridPanel)
	pricePlanId:null,//价格方案ID
	width :900,
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
    		this.queryPricePlanDetailForm = Ext.create('Foss.pricing.carloadPricingFloat.QueryPricePlanDetailForm');
    	}
    	return this.queryPricePlanDetailForm;
    },
    //明细信息结果集
    pricePlanDetailShowGridPanel:null,
    getPricePlanDetailShowGridPanel:function(){
    	if(Ext.isEmpty(this.pricePlanDetailShowGridPanel)){
    		this.pricePlanDetailShowGridPanel = Ext.create('Foss.pricing.carloadPricingFloat.PricePlanDetailShowGridPanel');
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
 * 价格方案明细查询表单
 */
Ext.define('Foss.pricing.carloadPricingFloat.QueryPricePlanDetailForm', {
	extend : 'Ext.form.Panel',
	title: pricing.carloadPricingFloat.i18n('i18n.pricingRegion.searchCondition'),
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
        	name: 'invoiceType',
			queryMode: 'local',
			allowBlank:false,
		    displayField: 'value',
		    value:'ALL',
		    valueField: 'key',
		    editable:false,
		    store:pricing.getStore('Foss.pricing.region.invoiceasType',null,['key','value'],
		     [{'key':'INVOICE_TYPE_01','value':pricing.carloadPricingFloat.i18n('foss.pricing.INVOICE_TYPE_01')},
		      {'key':'INVOICE_TYPE_02','value':pricing.carloadPricingFloat.i18n('foss.pricing.INVOICE_TYPE_02')},
		      {'key':'ALL','value':pricing.carloadPricingFloat.i18n('i18n.pricingRegion.all')}]),
		    fieldLabel: pricing.carloadPricingFloat.i18n('foss.pricing.invoiceType'),//发票标记
		    xtype : 'combo'
		},{
			name: 'organizationCode',
			valueField: 'code',
			//editable:false,
			fieldLabel:pricing.carloadPricingFloat.i18n('foss.pricing.sectorOrganizations'),//组织
	        priceRegionFlag:'ARRIVE_REGION',
	        xtype : 'dynamicorgcombselector'   
		},{
			xtype : 'container',
			columnWidth : .2,
			margin : '0 0 0 0',
			items : [{
				xtype : 'button', 
				width:70,
				text : pricing.carloadPricingFloat.i18n('i18n.pricingRegion.search'),
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
 * 价格方案明细列表
 */
Ext.define('Foss.pricing.carloadPricingFloat.PricePlanDetailShowGridPanel', {
	extend: 'Ext.grid.Panel',
	title :pricing.carloadPricingFloat.i18n('i18n.pricingRegion.searchResults'),//查询结果
	frame: true,
	height :450,
    sortableColumns:false,
    enableColumnHide:false,
    enableColumnMove:false,
	stripeRows : true, // 交替行效果
	selType : "rowmodel", // 选择类型设置为：行选择
	emptyText: pricing.carloadPricingFloat.i18n('foss.pricing.theQueryResultIsEmpty'),//查询结果为空
	
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
			text : pricing.carloadPricingFloat.i18n('i18n.pricingRegion.num')//序号
		},{
			text: pricing.carloadPricingFloat.i18n('foss.pricing.invoiceType'),//发票标记
			dataIndex : 'invoiceType',
			flex:2,
			renderer:function(value){
				if(value=='INVOICE_TYPE_01'){//01—运输专票11%
					return pricing.carloadPricingFloat.i18n('foss.pricing.INVOICE_TYPE_01')
				}else if(value=='INVOICE_TYPE_02'){//02—非运输专票
					return  pricing.carloadPricingFloat.i18n('foss.pricing.INVOICE_TYPE_02')
				}else{
					return '';
				}
			}
		},{
			text: pricing.carloadPricingFloat.i18n('foss.pricing.floatUp'),// 整车价格波动向上浮动值
			dataIndex : 'floatUp',
			flex:2
		},{
			text: pricing.carloadPricingFloat.i18n('foss.pricing.floatDown'),//整车价格波动向下浮动值
			dataIndex : 'floatDown',
			flex:2
		},{
			text: pricing.carloadPricingFloat.i18n('foss.pricing.otherCostParameter'),//其他成本参数
			dataIndex : 'otherCostParameter',
			flex:2
		},{
			text: pricing.carloadPricingFloat.i18n('foss.pricing.packageFeeParameter'),//包装费参数
			dataIndex : 'packageFeeParameter',
			flex:2
		},{
			text: pricing.carloadPricingFloat.i18n('foss.pricing.weightParameter'),//重量参数
			dataIndex : 'weightParameter',
			flex:2
		},{
			text: pricing.carloadPricingFloat.i18n('foss.pricing.carCostParameter'),//车价参数
			dataIndex : 'carCostParameter',
			flex:2
		},{
			text: pricing.carloadPricingFloat.i18n('foss.pricing.humanCostParameter'),//人力成本参数
			dataIndex : 'humanCostParameter',
			flex:2
		},{
			text : pricing.carloadPricingFloat.i18n('foss.pricing.remark'),//备注
			dataIndex : 'remark',
			flex:2
		}];
		me.store = Ext.create('Foss.pricing.carloadPricingFloat.PricePlanDeatilStore',{
			autoLoad : false,
			pageSize : 10,
			listeners: {
				beforeload: function(store, operation, eOpts){
					var queryForm =me.up('window').getQueryPricePlanDetailForm();
					var pricePlanId = me.up('window').pricePlanId;
					if(queryForm!=null){
						Ext.apply(operation,{
							params : {
								'carloadManageMeantVo.carloadPriceDetailEntity.invoiceType' : queryForm.getForm().findField('invoiceType').getValue(),//发票标记
								'carloadManageMeantVo.carloadPricePlanDto.organizationCode' : queryForm.getForm().findField('organizationCode').getValue(),//组织
								'carloadManageMeantVo.carloadPricePlanDto.id' : pricePlanId//价格方案ID
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
			text : pricing.carloadPricingFloat.i18n('foss.pricing.export'),
			handler :function(){
				
				var queryForm = me.up('window').getQueryPricePlanDetailForm();
				var pricePlanId = me.up('window').pricePlanId;
				var invoiceType = queryForm.getForm().findField('invoiceType').getValue();//发票标记
				var organizationCode = queryForm.getForm().findField('organizationCode').getValue();//组织
				var pricePlanExport = '';
				if(!Ext.isEmpty(pricePlanId)){
					pricePlanExport ='carloadManageMeantVo.carloadPricePlanDto.id='+pricePlanId;
				}
				if(!Ext.isEmpty(invoiceType)){
					pricePlanExport = pricePlanExport+'&'+'carloadManageMeantVo.carloadPricePlanDto.invoiceType='+invoiceType;
				}
				if(!Ext.isEmpty(organizationCode)){
					if(!Ext.isEmpty(pricePlanExport)){
						pricePlanExport = pricePlanExport+'&'+'carloadManageMeantVo.carloadPricePlanDto.organizationCode='+organizationCode;
					}
				}
				var url = pricing.realPath('exportCarloadPricePlanDetail.action');
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


/**
 * 价格方案明细form
 */
Ext.define('Foss.pricing.carloadPricingFloat.PricePlanDetailForm', {
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
			name: 'invoiceType',
			queryMode: 'local',
			allowBlank:false,
		    displayField: 'value',
		    value:'INVOICE_TYPE_02',
		    valueField: 'key',
		    editable:false,
		    store:pricing.getStore('Foss.pricing.region.invoiceType',null,['key','value']
		    ,[{'key':'INVOICE_TYPE_01','value':pricing.carloadPricingFloat.i18n('foss.pricing.INVOICE_TYPE_01')},{'key':'INVOICE_TYPE_02','value':pricing.carloadPricingFloat.i18n('foss.pricing.INVOICE_TYPE_02')}]),
		    fieldLabel: pricing.carloadPricingFloat.i18n('foss.pricing.invoiceType'),//发票标记
		    xtype : 'combo'
		},{
			name: 'floatUp',
			allowBlank:false,
			decimalPrecision:2,
			step:0.01,
		    maxValue: 999999.99,
		    minValue:0,
	        fieldLabel: pricing.carloadPricingFloat.i18n('foss.pricing.floatUp'),//整车价格波动向上浮动值
	        xtype : 'numberfield'
		
		},{
			name: 'floatDown',
			allowBlank:false,
			decimalPrecision:2,
			step:0.01,
		    maxValue: 999999.99,
		    minValue:0,
	        fieldLabel: pricing.carloadPricingFloat.i18n('foss.pricing.floatDown'),//整车价格波动向下
	        xtype : 'numberfield'
		},{
			name: 'otherCostParameter',
			allowBlank:false,
			decimalPrecision:3,
			step:0.001,
		    maxValue: 999999.999,
		    minValue:0,
	        fieldLabel: pricing.carloadPricingFloat.i18n('foss.pricing.otherCostParameter'),//其他成本参数
	        xtype : 'numberfield'
		},{
			name: 'packageFeeParameter',
			allowBlank:false,
			decimalPrecision:3,
			step:0.001,
		    maxValue: 999999.999,
		    minValue:0,
	        fieldLabel: pricing.carloadPricingFloat.i18n('foss.pricing.packageFeeParameter'),//包装费参数
	        xtype : 'numberfield'
		},{
			name: 'weightParameter',
			allowBlank:false,
			decimalPrecision:3,
			step:0.001,
		    maxValue: 999999.999,
		    minValue:0,
	        fieldLabel: pricing.carloadPricingFloat.i18n('foss.pricing.weightParameter'),//重量参数
	        xtype : 'numberfield'
		},{
			name: 'carCostParameter',
			allowBlank:false,
			decimalPrecision:3,
			step:0.001,
		    maxValue: 999999.999,
		    minValue:0,
	        fieldLabel: pricing.carloadPricingFloat.i18n('foss.pricing.carCostParameter'),//车价参数
	        xtype : 'numberfield'
		},{
			name: 'humanCostParameter',
			allowBlank:false,
			decimalPrecision:2,
			step:0.01,
		    maxValue: 999999.99,
		    minValue:0,
	        fieldLabel: pricing.carloadPricingFloat.i18n('foss.pricing.humanCostParameter'),//人力成本参数
	        xtype : 'numberfield'
		},{
			xtype: 'textareafield',
			width:300,
		    fieldLabel: pricing.carloadPricingFloat.i18n('foss.pricing.remark'),//备注
		    maxLength:160, //DEFECT-5854:整车介个浮动方案界面，备注框功能不完善,字符超长会报错，需要优化
	        name:'remark'
		}];
		me.callParent([cfg]);
	}
});

/**
 * 价格方案明细信息新增弹出窗口window
 */
Ext.define('Foss.pricing.carloadPricingFloat.PricePlanDetailWindow',{
	extend : 'Ext.window.Window',
	title: pricing.carloadPricingFloat.i18n('foss.pricing.aBreakdownOfNew'),//明细信息新增
	closable : true,
	modal : true,
	resizable:false,
	closeAction : 'hide',
	parent:null,
	width :450,
	height :600,
	pricePlanDetailDto:null,
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
    		this.pricePlanDetailForm = Ext.create('Foss.pricing.carloadPricingFloat.PricePlanDetailForm');
    	}
    	return this.pricePlanDetailForm;
    },
    
    //想GRID中设置数据
    commitPricePlanDetail:function(grid){
    	var me = this;
    	//得到明细form
    	var form = me.getPricePlanDetailForm().getForm();
    	if(form.isValid()){
    		var pricePlanDetailDto = new Foss.pricing.carloadPricingFloat.PricePlanDetailDtoModel();
    		form.updateRecord(pricePlanDetailDto);

    		var floatUpValue = pricePlanDetailDto.data.floatUp;//整车价格波动向上浮动值
    		var floatDownValue = pricePlanDetailDto.data.floatDown;//整车价格波动向下浮动值
    		if(floatUpValue<=0){
    			pricing.showErrorMes(pricing.carloadPricingFloat.i18n('foss.pricing.thePriceOfFloatUpMustBeGreaterThan0'));
    			return ;	
    		}
    		
    		if(floatDownValue<=0){
    			pricing.showErrorMes(pricing.carloadPricingFloat.i18n('foss.pricing.thePriceOfFloatDownMustBeGreaterThan0'));
    			return;
    		}
    		if(floatUpValue<floatDownValue){
    			pricing.showErrorMes(pricing.carloadPricingFloat.i18n('foss.pricing.floatDownValueBigFloatUpValue'));
    			return;
    		}
			//整车参数方案id
	    	pricePlanDetailDto.set('carloadPriceId',pricing.carloadPricingFloatId);
			//制定json请求参数
			var params = {'carloadManageMeantVo':{'carloadPriceDetailEntity':pricePlanDetailDto.data}};
			//ajax请求
			var url = pricing.realPath('addCarloadPriceDetail.action');
			
			//成功提示
			var successFun = function(json){
				pricing.showInfoMes(json.message);
				me.close();
				var arrayDate = json.carloadManageMeantVo.carloadPriceDetailEntityList;
				if(!Ext.isEmpty(arrayDate)){
					grid.store.loadData(arrayDate);//显示第一页	
				}
		    };
		    //失败提示
		    var failureFun = function(json){
		    	if(Ext.isEmpty(json)){
					pricing.showErrorMes(pricing.carloadPricingFloat.i18n('i18n.pricingRegion.requestTimeOut'));
				}else{
					pricing.showErrorMes(json.message);
				}
		    };
		    //调用ajax请求
			pricing.requestJsonAjax(url,params,successFun,failureFun);//发送AJAX请求
    	}
    },
    constructor : function(config) {
		var me = this, 
		cfg = Ext.apply({}, config);
		me.items = [me.getPricePlanDetailForm()];//设置window的元素
		me.fbar = [{
			text : pricing.carloadPricingFloat.i18n('i18n.pricingRegion.cancel'),//取消
			handler :function(){
				me.close();
			} 
		},{
			text : pricing.carloadPricingFloat.i18n('foss.pricing.reset'),//重置
			margin:'0 185 0 0',
			handler :function(){
				if(Ext.isEmpty(me.pricePlanDetailDto)){
					me.getPricePlanDetailForm().getForm().loadRecord(new Foss.pricing.carloadPricingFloat.PricePlanDetailDtoModel());
				}else{
					me.getPricePlanDetailForm().getForm().loadRecord(new Foss.pricing.carloadPricingFloat.PricePlanDetailDtoModel(me.effectivePlanEntity));
				}
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
 * 价格方案批次信息录入form
 */
Ext.define('Foss.pricing.carloadPricingFloat.PricePlanAddFormPanel',{
	extend : 'Ext.form.Panel',
	title: pricing.carloadPricingFloat.i18n('foss.pricing.organizationInfo'),//"出发地信息",
	parent:null,
	frame: true,
	operaterCode:null,
	pricePlanEntity: null,
	priceRegionId: null,
	/*layout:{
		  type: 'table',
	      columns: 2
	},*/
	layout: 'column',
	height :251,
	//colspan: 2,
    defaults : {
    	//columnWidth : 1,
    	margin : '5 5 5 5',
    	//width:320,
		labelSeparator:'',
		labelWidth:80,
		xtype : 'textfield'
    },
	/**修改价格方案**/
	commitUpdatePricePlan:function(){
    	var me = this;
    	var baseForm = me.getForm();
    	if(baseForm.isValid()){//校验form是否通过校验
    		var pricePlanEntity = me.up('window').pricePlanEntity;
    		var orgCodes = baseForm.findField('orgCodes').orgCodes;
    		var pricePlanModel = new Foss.pricing.carloadPricingFloat.PricePlanModel(pricePlanEntity);
    		for(var i = 0;i<orgCodes.length;i++){
        		orgCodes[i].scenarioName=pricePlanModel.get('scenarioName');
        		orgCodes[i].beginTime=pricePlanModel.get('beginTime');
        		
        	}
    		baseForm.updateRecord(pricePlanModel);//将FORM中数据设置到MODEL里面
    		var params = {'carloadManageMeantVo':{'carloadPriceEntity':pricePlanModel.data,'carloadPriceOrgEntityList':orgCodes}};
    		var successFun = function(json){
				pricing.showInfoMes(json.message);//提示新增成功
				
				baseForm.getFields().each(function(item){
	    			   item.setReadOnly(true);
	    		});
				me.getDockedItems()[1].items.items[0].setDisabled(true);//重置按钮不可用
				me.getDockedItems()[1].items.items[1].setDisabled(true);//保存按钮不可用
			};
			var failureFun = function(json){
				if(Ext.isEmpty(json)){
					pricing.showErrorMes(pricing.carloadPricingFloat.i18n('foss.pricing.requestTimedOut'));//请求超时
				}else{
					pricing.showErrorMes(json.message);//提示失败原因
				}
			};
			var url = pricing.realPath('updateCarloadPricePlan.action');//请求价格方案修改
			pricing.requestJsonAjax(url,params,successFun,failureFun);//发送AJAX请求
    	}
	 },
	
	/***批次保存***/
	commitPricePlan:function(){
		var me = this;
    	//获取表单
    	var form = me.getForm();
    	var pricePlanModel = new Foss.pricing.carloadPricingFloat.PricePlanModel();
    	if(form.isValid()){
    		form.updateRecord(pricePlanModel);  		
    		var orgCodes = form.findField('orgCodes').orgCodes;
    		if(orgCodes==null){
    			pricing.showWoringMessage('组织信息为空，请重新选择');
    			return;
    		}
        	for(var i = 0;i<orgCodes.length;i++){
        		orgCodes[i].scenarioName=pricePlanModel.get('scenarioName');
        		orgCodes[i].beginTime=pricePlanModel.get('beginTime');
        		
        	}
        	
    		var params = {'carloadManageMeantVo':{'carloadPriceEntity':pricePlanModel.data,'carloadPriceOrgEntityList':orgCodes}};
    		var url = pricing.realPath('addCarloadPricePlan.action');
    		
    		//成功提示
    		var successFun = function(json){
    			pricing.showInfoMes(json.message);
    			//成功后获取价格方案主ID
				pricing.carloadPricingFloatId = json.carloadManageMeantVo.carloadPriceEntity.id;  
				me.getPricePlanDetailGridPanel().store.add();
					    		
	    		//var itemArrays = me.items.items;
	    		//Ext.Array.each(itemArrays,function(value,index,arrays){
	    			//itemArrays[index].setReadOnly(true);
	    		//});
			   //获取formPanel所有属性,全部设置为只读
	    	   form.getFields().each(function(item){
    			   item.setReadOnly(true);
    		   });
	    		me.getDockedItems()[1].items.items[0].setDisabled(true);//重置按钮不可用
				me.getDockedItems()[1].items.items[1].setDisabled(true);//保存按钮不可用
				var dockedItems = me.up('window').getPricePlanDetailGridPanel().getDockedItems();
				dockedItems[1].items.items[0].setDisabled(false);//新增明明细按钮可用
				dockedItems[1].items.items[2].setDisabled(false);//删除明细按钮可用
				//dockedItems[1].items.items[4].setDisabled(false);//导入明细按钮可用
				
    	    };
    	    
    	    //失败提示
    	    var failureFun = function(json){
    	    	if(Ext.isEmpty(json)){
    				pricing.showErrorMes(pricing.carloadPricingFloat.i18n('i18n.pricingRegion.requestTimeOut'));
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
    			this.pricePlanDetailGridPanel = Ext.create('Foss.pricing.carloadPricingFloat.PricePlanDetailGridPanel');
    			pricing.carloadPricingFloatDetailGridPanel  = this.pricePlanDetailGridPanel;
    	}
    	return this.pricePlanDetailGridPanel;
    },
    orgSelectWindow:null,
    //组织选择
    getOrgWindow: function() {
        var me = this;
        if (Ext.isEmpty(this.orgSelectWindow)) {
            this.orgSelectWindow = Ext.create('Foss.baseinfo.commonSelector.orgSelectorWindow', 
            	{type: 'ORG',active: 'Y',modal: true,
            	commitFun: function() {
                    var selections = this.getGridRecord();
                    if (selections.length < 1) {
                        dict.showErrorMes(pricing.carloadPricingFloat.i18n('foss.dict.pleaseSelectOrg'));
                        return;
                    }
                    var orgCodes = new Array();
                    //var names = new Array();
                    //var ids =new Array();
                    var value = '';
                    for (var i = 0; i < selections.length; i++) {
                    	 var carloadPriceEntity =new Object();
                    	value = value + selections[i].get('name') + ',';
                    	carloadPriceEntity.organizationID =selections[i].get('id');
                    	carloadPriceEntity.organizationCode =selections[i].get('code');
                    	carloadPriceEntity.organizationName =selections[i].get('name');
                    	carloadPriceEntity.scenarioName='';
                    	carloadPriceEntity.beginTime=new Date();
                    	orgCodes.push(carloadPriceEntity);
                    }
                    me.getForm().findField('orgCodes').setValue(value);
                    me.getForm().findField('orgCodes').orgCodes = orgCodes;
                    
                    this.close();
                }});
            me.orgSelectWindow.items.items[0].getForm().getFields().each(function(item){
            	if(item.name =='transferCenter'||item.name =='doAirDispatch'||item.name=='transDepartment'
            		||item.name=='dispatchTeam'||item.name=='billingGroup'
            		||item.name =='transTeam'||item.name=='isDeliverSchedule'
            		||item.name =='isArrangeGoods' || item.name=='airDispatch' || item.name=='isEntityFinance'){
            		item.setVisible(false);
            	}
            	
            });
            me.orgSelectWindow.items.items[0].getForm().findField('type').getStore().removeAll();
            me.orgSelectWindow.items.items[0].getForm().findField('type').getStore().
            add({code: 'ORG',name: pricing.carloadPricingFloat.i18n('foss.dict.interiorTissue')});
            this.orgSelectWindow.parent = this;
        }
        return this.orgSelectWindow;
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
			colspan: 1,
			name: 'scenarioName',
			allowBlank:false,
			maxLength : 150,
			columnWidth: .45,
	        fieldLabel:pricing.carloadPricingFloat.i18n('foss.pricing.scenarioName')//方案名称
		},{
        	name: 'orgCodes',
        	colspan: 1,
        	width: 250,
        	orgCodes: [],
        	ids:[],
        	//readOnly: true,
        	columnWidth: .42,
        	allowBlank:false,
        	margin : '5 5 5 15',
        	labelSeparator:':',
    		labelWidth:70,
        	readOnly: true,
        	//fieldLabel: '<label style="color:red">*</label>'+pricing.carloadPricingFloat.i18n('foss.pricing.sectorOrganizations'),  //组织
        	fieldLabel:pricing.carloadPricingFloat.i18n('foss.pricing.sectorOrganizations'),  //组织
        	xtype: 'textareafield'
        },{
        	//colspan: 1,
        	name:'select',
			width:50,
			columnWidth: .08,
			margin : '40 5 0 4',
			//margin-top:40,
        	text: pricing.carloadPricingFloat.i18n('foss.pricing.select'), //选择
        	xtype: 'button',
        	handler: function() {
        			me.getOrgWindow().show();
        		}
        },{
			xtype:'datefield',
			allowBlank:false,
			columnWidth: .45,
			fieldLabel:pricing.carloadPricingFloat.i18n('foss.pricing.availabilityDate'),//生效日期
			format:'Y-m-d',
			name:'beginTime'
		}],
		me.fbar = [{
			text : pricing.carloadPricingFloat.i18n('foss.pricing.reset'),//重置
			handler :function(){
					me.getForm().reset();//表格重置
			} 
		},{
			text : pricing.carloadPricingFloat.i18n('foss.pricing.save'),//保存
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

/**
 * 价格方案明细目的地grid
 */
Ext.define('Foss.pricing.carloadPricingFloat.PricePlanDetailGridPanel',{
	extend: 'Ext.grid.Panel',
	title : pricing.carloadPricingFloat.i18n('foss.pricing.parameterInfo'),//"参数信息",
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
    
	//弹出明细新增Window信息
    pricePlanDetailWindow:null,
    getPricePlanDetailWindow:function(){
    	var me = this;
    	if(Ext.isEmpty(this.pricePlanDetailWindow)){
    		this.pricePlanDetailWindow = Ext.create('Foss.pricing.carloadPricingFloat.PricePlanDetailWindow',{
    			grid:me
    		});
	    	this.pricePlanDetailWindow.parent = this;
    	}
    	return this.pricePlanDetailWindow;
    },
    
    //弹出修改明细列表信息
    modifyPricePlanDetailWindow:null,
    getModifyPriceDetailWindow:function(){
    	if(Ext.isEmpty(this.modifyPricePlanDetailWindow)){  
    		this.modifyPricePlanDetailWindow = Ext.create('Foss.pricing.carloadPricingFloat.ModifyPriceDetailWindow',{
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
			pricing.showWoringMessage(pricing.carloadPricingFloat.i18n('foss.pricing.pleaseSelectOneDeleteOperation'));//请选择一条进行删除操作！
			return;//没有则提示并返回
		}
		for(var i = 0;i<selections.length;i++){
			if(selections[i].get('active')==pricing.carloadPricingFloat.yes){
				pricing.showWoringMessage(pricing.carloadPricingFloat.i18n('foss.pricing.pleaseChooseToNotActivateTheDataToBeBeleted'));//请选择未激活数据进行删除！
				return;//没有则提示并返回
			}
		}
		pricing.showQuestionMes(pricing.carloadPricingFloat.i18n('foss.pricing.youWantDeletePricProgramDetails'),function(e){//是否要删除这些价格方案明细？
		if(e=='yes'){//询问是否删除，是则发送请求
				var valuationIds = new Array();//计费规则ID
				for(var i = 0 ; i<selections.length ; i++){
					valuationIds.push(selections[i].get('id'));
				}
				var params = {'carloadManageMeantVo':{'carPlanDetailIds':valuationIds}};
				var successFun = function(json){
					pricing.showInfoMes(json.message);
					var arrayDate = json.carloadManageMeantVo.carloadPriceDetailEntityList;
							grid.store.loadData(arrayDate);//显示第一页	
					};
				var failureFun = function(json){
					if(Ext.isEmpty(json)){
						pricing.showErrorMes(pricing.carloadPricingFloat.i18n('foss.pricing.requestTimedOut'));//请求超时
					}else{
						pricing.showErrorMes(json.message);
					}
				};
				var url = pricing.realPath('deleteCarloadPricePlanDetail.action');
				pricing.requestJsonAjax(url,params,successFun,failureFun);
			}
		})	
    },
	//初始化构造器
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.pricing.carloadPricingFloat.PricePlanDeatilStore',{
			autoLoad : false,
			pageSize : 10,
			listeners: {
				beforeload: function(store, operation, eOpts){
					var pricePlanId = pricing.carloadPricingFloatId
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
		me.bbar = me.getPagingToolbar();
		me.selModel = me.getCheckboxModel();
		//加入tbar菜单
		me.tbar = [{
	            text: pricing.carloadPricingFloat.i18n('i18n.pricingRegion.add'),
	            handler:function(){ 
	            	me.getPricePlanDetailWindow().show();	 
	            }
	        },'-',{
	            text: pricing.carloadPricingFloat.i18n('foss.pricing.delete'),
	            handler:function(){
	            	me.deletePricePlanDetail(me); 
	            }
	    }];
		me.store = Ext.create('Foss.pricing.carloadPricingFloat.PricePlanDeatilStore',{
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
		width:40,
		text: pricing.carloadPricingFloat.i18n('i18n.pricingRegion.num')//"序号"//序号
		
		},{
			text : pricing.carloadPricingFloat.i18n('i18n.pricingRegion.opra'),
			align : 'center',
			xtype : 'actioncolumn',
			items: [{
					iconCls:'deppon_icons_edit',
					tooltip: pricing.carloadPricingFloat.i18n('foss.pricing.toAmendTheProposal'), 
					width:42,
					handler: function(grid, rowIndex, colIndex){
	                	var record = grid.up().getStore().getAt(rowIndex);//选中数据
	                	var pricePlanDetaiModel = new Foss.pricing.carloadPricingFloat.PricePlanDetailDtoModel(record.data);
 	                		if(!Ext.isEmpty(pricePlanDetaiModel)){
 	                			var window =me.getModifyPriceDetailWindow();
 	                			window.pricePlanDetaiModel =pricePlanDetaiModel;
 	                			window.show();
 	                		}
	    				}
			}]
		},{
			text: pricing.carloadPricingFloat.i18n('foss.pricing.invoiceType'),//发票标记
			width: 100,
			align : 'left',
	        dataIndex: 'invoiceType',
	        renderer:function(value){
				if(value=='INVOICE_TYPE_01'){//01—运输专票11%
					return pricing.carloadPricingFloat.i18n('foss.pricing.INVOICE_TYPE_01')
				}else if(value=='INVOICE_TYPE_02'){//02—非运输专票
					return  pricing.carloadPricingFloat.i18n('foss.pricing.INVOICE_TYPE_02')
				}else{
					return '';
				}
			}
		},{
			text: pricing.carloadPricingFloat.i18n('foss.pricing.floatUp'),// 整车价格波动向上浮动值
			width: 100,
	        dataIndex: 'floatUp'
		},{
			text: pricing.carloadPricingFloat.i18n('foss.pricing.floatDown'),//整车价格波动向下浮动值
	    	width: 100,
	        dataIndex: 'floatDown'
		},
		{
			text: pricing.carloadPricingFloat.i18n('foss.pricing.otherCostParameter'),//其他成本参数
	    	width: 80,
	        dataIndex: 'otherCostParameter'
		},
		{
			text: pricing.carloadPricingFloat.i18n('foss.pricing.packageFeeParameter'),//包装费参数
	    	width: 70,
	        dataIndex: 'packageFeeParameter'
		},
		{
			text: pricing.carloadPricingFloat.i18n('foss.pricing.weightParameter'),//重量参数
	    	width: 60,
	        dataIndex: 'weightParameter'
		},
		{
			text: pricing.carloadPricingFloat.i18n('foss.pricing.carCostParameter'),//车价参数
	    	width: 60,
	        dataIndex: 'carCostParameter'
		},
		{
			text: pricing.carloadPricingFloat.i18n('foss.pricing.humanCostParameter'),//人力成本参数
	    	width: 80,
	        dataIndex: 'humanCostParameter'
		},{
			text: pricing.carloadPricingFloat.i18n('foss.pricing.remark'),//"备注",//备注
			width: 50,
	        dataIndex: 'remark'
		}],
		pricing.pagingBar = me.bbar;
		me.callParent([cfg]);
	}
});

/**
 * 价格方案弹出框
 */
Ext.define('Foss.pricing.carloadPricingFloat.PricePlanAddWindow',{
		extend: 'Ext.window.Window',
		title: "新增整车价格波动参数方案",
		width:900,
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
//				//属性设置只读属性为false
//				me.getPricePlanAddFormPanel().getForm().getFields().each(function(item){
//					item.setReadOnly(false);
//				});
				//设置价格方案form操作按钮可用
				me.getPricePlanAddFormPanel().getDockedItems()[1].items.items[0].setDisabled(false);//重置按钮可用
				me.getPricePlanAddFormPanel().getDockedItems()[1].items.items[1].setDisabled(false);//保存按钮可用
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
	    		this.pricePlanAddFormPanel = Ext.create('Foss.pricing.carloadPricingFloat.PricePlanAddFormPanel');
	    	} 
	    	return this.pricePlanAddFormPanel;
	    },
	     //价格方案明细信息 （目的明细列表）
		pricePlanDetailGridPanel:null,
	    getPricePlanDetailGridPanel: function(){
	    	if(Ext.isEmpty(this.pricePlanDetailGridPanel)){
	    		this.pricePlanDetailGridPanel = Ext.create('Foss.pricing.carloadPricingFloat.PricePlanDetailGridPanel');
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
 * 价格方案弹出修改框
 */
Ext.define('Foss.pricing.carloadPricingFloat.PricePlanUpdateWindow',{
		extend: 'Ext.window.Window',
		title: "修改整车价格参数方案",
		width:900,
		height:770,
		modal:true,
		isUpdate:null,
		parent:null,
 		pricePlanEntity:null,
 		carloadPricePlanDtoList:null,
 		carloadPriceDetailEntityList:null,
		closeAction:'hide',
	    //监听器
	    listeners:{
			beforehide:function(me){
				me.getPricePlanAddFormPanel().getForm().reset();
				me.isUpdate = null;
			},
			beforeshow:function(me){
				me.getPricePlanAddFormPanel().getForm().loadRecord(new Foss.pricing.carloadPricingFloat.PricePlanModel(me.pricePlanEntity));
				var orgCodes = new Array();
                var value = '';
                for (var i = 0; i < me.carloadPricePlanDtoList.length; i++) {
                	 var carloadPriceEntity =new Object();
                	value = value + me.carloadPricePlanDtoList[i].organizationName + ',';
                	carloadPriceEntity.organizationID = me.carloadPricePlanDtoList[i].organizationID;
                	carloadPriceEntity.organizationCode = me.carloadPricePlanDtoList[i].organizationCode;
                	carloadPriceEntity.organizationName = me.carloadPricePlanDtoList[i].organizationName;
                	orgCodes.push(carloadPriceEntity);
                }
                me.getPricePlanAddFormPanel().getForm().findField('orgCodes').setValue(value);
                me.getPricePlanAddFormPanel().getForm().findField('orgCodes').orgCodes = orgCodes;
                
                var arrayDate = me.carloadPriceDetailEntityList;
				if(!Ext.isEmpty(arrayDate)){
					me.getPricePlanDetailGridPanel().store.loadData(arrayDate);//显示第一页	
				}
				//me.getPricePlanDetailGridPanel().store.removeAll(true);
			}
		},
	    //价格方案批次信息 （出发地批次信息录入）
		pricePlanAddFormPanel:null,
	    getPricePlanAddFormPanel : function(){
	    	var me = this;
	    	if(Ext.isEmpty(me.pricePlanAddFormPanel)){
		    		me.pricePlanAddFormPanel = Ext.create('Foss.pricing.carloadPricingFloat.PricePlanAddFormPanel');
		    		//设置器父元素
 	    	} 
	    	return this.pricePlanAddFormPanel;
	    },
	     //价格方案明细信息 （目的明细列表）
		pricePlanDetailGridPanel:null,
	    getPricePlanDetailGridPanel: function(){
	    	if(Ext.isEmpty(this.pricePlanDetailGridPanel)){
	    			this.pricePlanDetailGridPanel = Ext.create('Foss.pricing.carloadPricingFloat.PricePlanDetailGridPanel');
	    			pricing.carloadPricingFloatDetailGridPanel  = this.pricePlanDetailGridPanel;
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
 * 价格中止方案弹出框
 */
Ext.define('Foss.pricing.carloadPricingFloat.PricePlanStopEndTimeWindow',{
		extend: 'Ext.window.Window',
		title: pricing.carloadPricingFloat.i18n('foss.pricing.suspendPriceScheme'),//"中止价格方案",
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
	    		this.pricePlanStopFormPanel = Ext.create('Foss.pricing.carloadPricingFloat.PricePlanStopFormPanel');
	    	}
	    	return this.pricePlanStopFormPanel;
	    },
	    
	   	initComponent : function() {
			var me = this;
			me.items = [me.getPricePlanStopFormPanel()];//设置window的元素
			me.callParent();
		}
});

/**
 * 中止功能window
 */
Ext.define('Foss.pricing.carloadPricingFloat.PricePlanStopFormPanel',{
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
			var pricePlanModel = new Foss.pricing.carloadPricingFloat.PricePlanModel();
			form.updateRecord(pricePlanModel);
    		pricePlanModel.set('id',pricePlanId);
    		var params = {'carloadManageMeantVo':{'carloadPriceEntity':pricePlanModel.data}};
    		
    		//ajax请求
    		var url = pricing.realPath('stopCarloadPricePlan.action');
    		
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
    				pricing.showErrorMes(pricing.carloadPricingFloat.i18n('i18n.pricingRegion.requestTimeOut'));
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
				xtype:'datetimefield_date97',
				fieldLabel:pricing.carloadPricingFloat.i18n('foss.pricing.deadline'),//截止日期
				editable:false,
				time : true,
				name:'endTime',
				allowBlank:false,
				id : 'Foss_pricePlan_suspendEndTime_ID',
				dateConfig: {
					el : 'Foss_pricePlan_suspendEndTime_ID-inputEl'
				}
			},{
				xtype : 'container',
				margin : '0 0 2 0',
				items : [{
					xtype : 'button', 
					width:70,
					text : pricing.carloadPricingFloat.i18n('foss.pricing.stop'),//"中止",
					handler : function() {
						var pricePlanId = me.up('window').pricePlanId;
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
Ext.define('Foss.pricing.carloadPricingFloat.PricePlanImmediatelyStopEndTimeWindow',{
		extend: 'Ext.window.Window',
		title: pricing.carloadPricingFloat.i18n('foss.pricing.immediatelySupendPricePriceScheme'),//"立即中止方案",
		width:380,
		height:152,
		pricePlanEntity:null,
		closeAction: 'hide' ,
		listeners:{
			beforehide:function(me){
				var form = me.down('form');
				form.getForm().reset();
			},
			beforeshow:function(me){
				var beginTime = Ext.Date.format(new Date(me.pricePlanEntity.beginTime), 'Y-m-d H:i:s');
				var endTime = Ext.Date.format(new Date(me.pricePlanEntity.endTime), 'Y-m-d H:i:s');
				var value = pricing.carloadPricingFloat.i18n('foss.pricing.showleftTimeInfo')
					  +beginTime+pricing.carloadPricingFloat.i18n('foss.pricing.showmiddleTimeInfo')
					  +endTime+pricing.carloadPricingFloat.i18n('foss.pricing.showstopRightEndTimeInfo');
				me.pricePlanEntity.showTime = value;
				me.getPricePlanStopFormPanel().getForm().loadRecord(new Foss.pricing.carloadPricingFloat.PricePlanModel(me.pricePlanEntity));
				me.getPricePlanStopFormPanel().getForm().findField('endTime').setValue(Ext.Date.format(me.pricePlanEntity.endTime,'Y-m-d H:i:s'));
			}
		},
		pricePlanStopFormPanel:null,
		getPricePlanStopFormPanel : function(pricePlanEntity){
	    	if(Ext.isEmpty(this.pricePlanStopFormPanel)){
	    		this.pricePlanStopFormPanel = Ext.create('Foss.pricing.carloadPricingFloat.PricePlanImmediatelyStopFormPanel');
	    	}
	    	this.pricePlanStopFormPanel.pricePlanEntity = pricePlanEntity;
	    	return this.pricePlanStopFormPanel;
	    },
	   	constructor : function(config) {
			var me = this, cfg = Ext.apply({}, config);
			me.items = [me.getPricePlanStopFormPanel(me.pricePlanEntity)];
			me.callParent(cfg);
		}
});

/**
 * 立即中止功能FormPanel
 */
Ext.define('Foss.pricing.carloadPricingFloat.PricePlanImmediatelyStopFormPanel',{
	extend : 'Ext.form.Panel',
	layout:'column',
	pricePlanEntity:null,
	stopPricePlan:function(pricePlanId){
		var me = this;
    	var form = me.getForm();
    	if(form.isValid()){
			var pricePlanModel = new Foss.pricing.carloadPricingFloat.PricePlanModel();
			form.updateRecord(pricePlanModel);
    		pricePlanModel.set('id',pricePlanId);
    		//pricePlanModel.set('isPromptly',true);
    		var params = {'carloadManageMeantVo':{'carloadPriceEntity':pricePlanModel.data}};
    		pricePlanModel.set('endTime',Ext.Date.parse(form.findField('endTime').getValue(), 'Y-m-d H:i:s'));
    		var url = pricing.realPath('immediatelystopCarloadPricePlan.action');
    		var successFun = function(json){
    			pricing.showInfoMes(json.message);
    			me.up('window').hide();
				pricing.pagingBar.moveFirst();   			
    	    };
    	    var failureFun = function(json){
    	    	if(Ext.isEmpty(json)){
    				pricing.showErrorMes(pricing.carloadPricingFloat.i18n('i18n.pricingRegion.requestTimeOut'));
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
				fieldLabel :pricing.carloadPricingFloat.i18n('foss.pricing.suspendTime') ,//'中止日期',
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
				text : pricing.carloadPricingFloat.i18n('i18n.pricingRegion.determine'),//"确认",
				handler : function() {
					var pricePlanId = me.up('window').pricePlanEntity.id;
					me.stopPricePlan(pricePlanId);
				}
			},{
				xtype : 'button', 
				width:70,
				columnWidth:.15,
				text : pricing.carloadPricingFloat.i18n('i18n.pricingRegion.cancel'),//"取消",
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
Ext.define('Foss.pricing.carloadPricingFloat.PricePlanImmediatelyActiveTimeWindow',{
		extend: 'Ext.window.Window',
		title: pricing.carloadPricingFloat.i18n('foss.pricing.immediatelyActiveationPriceScheme'),//"立即激活方案",
		width:380,
		height:152,
		pricePlanEntity:null,
		closeAction: 'hide' ,
		
		listeners:{
			beforehide:function(me){
				var form = me.down('form');
				form.getForm().reset();
			},
			beforeshow:function(me){
				var beginTime = Ext.Date.format(new Date(me.pricePlanEntity.beginTime), 'Y-m-d H:i:s');
				var endTime = Ext.Date.format(new Date(me.pricePlanEntity.endTime), 'Y-m-d H:i:s');
				var value = pricing.carloadPricingFloat.i18n('foss.pricing.showleftTimeInfo')
					  +beginTime+pricing.carloadPricingFloat.i18n('foss.pricing.showmiddleTimeInfo')
					  +endTime+pricing.carloadPricingFloat.i18n('foss.pricing.showrightEndTimeInfo');
				me.pricePlanEntity.showTime = value;
				me.getPricePlanImmediatelyActiveFormPanel().getForm().loadRecord(new Foss.pricing.carloadPricingFloat.PricePlanModel(me.pricePlanEntity));
				me.getPricePlanImmediatelyActiveFormPanel().getForm().findField('beginTime').setValue(Ext.Date.format(me.pricePlanEntity.beginTime,'Y-m-d H:i:s'));
			}
		},
		//创建FormPanel 不管FormPanel是否存在都覆盖最新的信息值 pricePlanEntity
		pricePlanImmediatelyActiveFormPanel:null,
		getPricePlanImmediatelyActiveFormPanel : function(pricePlanEntity){
	    	if(Ext.isEmpty(this.pricePlanImmediatelyActiveFormPanel)){
	    		this.pricePlanImmediatelyActiveFormPanel = Ext.create('Foss.pricing.carloadPricingFloat.PricePlanImmediatelyActiveFormPanel');
	    	}
	    	this.pricePlanImmediatelyActiveFormPanel.pricePlanEntity = pricePlanEntity;
	    	return this.pricePlanImmediatelyActiveFormPanel;
	    },
	   	constructor : function(config) {
			var me = this, cfg = Ext.apply({}, config);
			me.items = [me.getPricePlanImmediatelyActiveFormPanel(me.pricePlanEntity)];
			me.callParent(cfg);
		}
});


/**
 * 立即激活功能Form
 */
Ext.define('Foss.pricing.carloadPricingFloat.PricePlanImmediatelyActiveFormPanel',{
	extend : 'Ext.form.Panel',
	layout:'column',
	pricePlanEntity:null,
	activetionPricePlan:function(pricePlanId){
		var me = this;
    	var form = me.getForm();
    	if(form.isValid()){
			var pricePlanModel = new Foss.pricing.carloadPricingFloat.PricePlanModel();
			form.updateRecord(pricePlanModel);
			pricePlanModel.set('beginTime',Ext.Date.parse(form.findField('beginTime').getValue(), 'Y-m-d H:i:s'));
    		pricePlanModel.set('id',pricePlanId);
    		var params = {'carloadManageMeantVo':{'carloadPriceEntity':pricePlanModel.data}};
    		var url = pricing.realPath('immediatelyActiveCarloadPricePlan.action');
    		var successFun = function(json){
    			pricing.showInfoMes(json.message);
    			me.up('window').hide();
				pricing.pagingBar.moveFirst();
    	    };
    	    var failureFun = function(json){
    	    	if(Ext.isEmpty(json)){
    				pricing.showErrorMes(pricing.carloadPricingFloat.i18n('i18n.pricingRegion.requestTimeOut'));
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
				name: 'showTime',
				xtype: 'displayfield',
				columnWidth:.9
			},{
				fieldLabel:pricing.carloadPricingFloat.i18n('foss.pricing.availabilityDate'),//'生效日期',
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
				text : pricing.carloadPricingFloat.i18n('i18n.pricingRegion.determine'),//,"确认",
				handler : function() {
					var form = this.up('form').getForm();
					var activeBeginTime = form.getValues().beginTime;
					var result = Ext.Date.parse(activeBeginTime,'Y-m-d H:i:s') - Ext.Date.parse(Ext.Date.format(new Date(),'Y-m-d H:i:s'),'Y-m-d H:i:s');
					if(result<0)
					{
						Ext.ux.Toast.msg(pricing.carloadPricingFloat.i18n('foss.pricing.promptMessage'),'激活时间不能小于当前时间','error', 3000);
						Ext.getCmp('Foss_pricePlan_PricePlanImmediatelyActiveFormPanel_ID').setDisabled(false);
						return;
					}
					
					var pricePlanId = me.up('window').pricePlanEntity.id;
					me.activetionPricePlan(pricePlanId);
				}
			},{
				xtype : 'button', 
				width:70,
				columnWidth:.15,
				text : pricing.carloadPricingFloat.i18n('i18n.pricingRegion.cancel'),//"取消",
				handler : function() {
					me.up('window').hide();
				}
			}];
        this.callParent(cfg);
    }
});




/**
 * 修改价格明细信息Window
 */
Ext.define('Foss.pricing.carloadPricingFloat.ModifyPriceDetailWindow',{
	extend : 'Ext.window.Window',
	title: '修改',//明细信息新增
	closable : true,
	modal : true,
	resizable:false,
	closeAction : 'hide',
	parent:null,
	width :450,
	height :450,
	pricePlanDetaiModel:null,
	grid:null,																	//父 grid
	listeners:{
		beforehide:function(me){
			me.getPricePlanDetailForm().getForm().reset();
		},
		beforeshow:function(me){
			if(!Ext.isEmpty(me.pricePlanDetaiModel)){
				me.getPricePlanDetailForm().getForm().loadRecord(me.pricePlanDetaiModel);
			}
			
		}
	},
    //明细信息新增-FORM
    pricePlanDetailForm:null,
    getPricePlanDetailForm:function(){
    	if(Ext.isEmpty(this.pricePlanDetailForm)){
    		this.pricePlanDetailForm = Ext.create('Foss.pricing.carloadPricingFloat.PricePlanDetailForm');
    	}
    	return this.pricePlanDetailForm;
    },
    updatePriceDetailPlan:function(grid){
    	var me = this;
    	//得到明细form
    	var form = me.getPricePlanDetailForm().getForm();
    	if(form.isValid()){
    		//var pricePlanDetailDto = new Foss.pricing.carloadPricingFloat.PricePlanDetailDtoModel(me.pricePlanDetailDto);
    		form.updateRecord(me.pricePlanDetaiModel);
    		var floatUpValue = me.pricePlanDetaiModel.data.floatUp;//整车价格参数波动向上值
    		var floatDownValue = me.pricePlanDetaiModel.data.floatDown;//整车价格参数波动向下值
    		if(floatUpValue<=0){
    			pricing.showErrorMes(pricing.carloadPricingFloat.i18n('foss.pricing.thePriceOfFloatUpMustBeGreaterThan0'));
    			return ;
    		}
    		if(floatDownValue<=0){
    			pricing.showErrorMes(pricing.carloadPricingFloat.i18n('foss.pricing.thePriceOfFloatDownMustBeGreaterThan0'));
    			return;
    		}
			
    		if(floatUpValue<floatDownValue){
    			pricing.showErrorMes(pricing.carloadPricingFloat.i18n('foss.pricing.floatDownValueBigFloatUpValue'));
    			return;
    		}
	    	//设置明细信息
	    	//pricePlanDetailDto.set('carloadPriceId',pricing.carloadPricingFloatId);
	    	//pricePlanDetailDto。set('')
	    	
	    	
			//制定json请求参数
			var params = {'carloadManageMeantVo':{'carloadPriceDetailEntity':me.pricePlanDetaiModel.data}};
			//ajax请求
			var url = pricing.realPath('updateCarloadPriceDetailPlan.action');
			
			//成功提示
			var successFun = function(json){
				pricing.showInfoMes(json.message);
				//pricing.carloadPricingFloatId = me.pricePlanDetaiModel.data.carloadPriceId;
				//pricing.pagingBar.moveFirst();
				var arrayDate = json.carloadManageMeantVo.carloadPriceDetailEntityList;
				if(!Ext.isEmpty(arrayDate)){
					grid.store.loadData(arrayDate);//显示第一页	
				}
		    };
		    //失败提示
		    var failureFun = function(json){
		    	if(Ext.isEmpty(json)){
					pricing.showErrorMes(pricing.carloadPricingFloat.i18n('i18n.pricingRegion.requestTimeOut'));
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
        //me.pricePlanDetailDto = cfg.pricePlanDetailDto;
		me.items = [me.getPricePlanDetailForm()];//设置window的元素
		me.fbar = [{
			text : pricing.carloadPricingFloat.i18n('i18n.pricingRegion.cancel'),//取消
			handler :function(){
				me.close();
			} 
		},{
			text : pricing.carloadPricingFloat.i18n('foss.pricing.reset'),//重置
			margin:'0 185 0 0',
			handler :function(){
				me.getPricePlanDetailForm().getForm().reset();
//				if(Ext.isEmpty(me.pricePlanDetailDto)){
//					me.getPricePlanDetailForm().getForm().loadRecord(new Foss.pricing.carloadPricingFloat.PricePlanDetailDtoModel());
//				}else{
//					me.getPricePlanDetailForm().getForm().loadRecord(new Foss.pricing.carloadPricingFloat.PricePlanDetailDtoModel(me.pricePlanDetailDto));
//				}
			} 
		},{
			text : "修改保存",//提交
			cls:'yellow_button',
			handler :function(){
				me.updatePriceDetailPlan(config.grid);
			} 
		}];
		me.callParent([cfg]);
	}
});


/**
 * 上传附件弹出框
 */
Ext.define('Foss.pricing.carloadPricingFloat.UploadPriceform',{
	extend:'Ext.window.Window',
	title:pricing.carloadPricingFloat.i18n('foss.pricing.importPriceScheme'),
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
	parent:null,//（Foss.pricing.carloadPricingFloat.pricePlanformGrid）
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
				fieldLabel:pricing.carloadPricingFloat.i18n('foss.pricing.pleaseSelectAttachments'),
				labelWidth:100,
				buttonText:pricing.carloadPricingFloat.i18n('foss.pricing.browse'),
				flex:3
			}]
		}];
		me.fbar = me.getFbar();
		me.callParent([cfg]);
	},
	getFbar:function(){
		var me = this;
		return [{
			text:pricing.carloadPricingFloat.i18n('i18n.pricingRegion.cancel'),
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
				pricing.showInfoMes(pricing.carloadPricingFloat.i18n('foss.pricing.allDataImportSuccess'));//全部数据导入成功！
				me.close();
			}else{
				var message = pricing.carloadPricingFloat.i18n('foss.pricing.first');//第
				for(var i = 0;i<json.platformVo.numList;i++){
					message = message+json.platformVo.numList[i]+','
				}
				message = message+pricing.carloadPricingFloat.i18n('foss.pricing.lineImportSuccess');
				pricing.showWoringMessage(message);
			}
		};
		var failureFn = function(json){
			if(Ext.isEmpty(json)){
				pricing.showErrorMes(pricing.carloadPricingFloat.i18n('i18n.pricingRegion.requestTimeOut'));//pricing.carloadPricingFloat.i18n('i18n.pricingRegion.requestTimeOut')
			}else{
				pricing.showErrorMes(json.message);
			}
		};
		var form = me.down('form').getForm();
		var url = pricing.realPath('importPrice.action');
		form.submit({
            url: url,
			timeout:60000,   
            waitMsg: pricing.carloadPricingFloat.i18n('foss.pricing.uploadYourAttachment'),
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
	pricing.searchProductItemEntityAddList();//新增中的条目元素
	pricing.searchProductItemEntityList();//查询中的条目元素
	var queryform = Ext.create('Foss.pricing.carloadPricingFloat.PricePlanFormPanel');
	var gridPanel =	Ext.create('Foss.pricing.carloadPricingFloat.PricePlanGridPanel');
	pricing.queryform = queryform;
	pricing.gridPanel = gridPanel;
	Ext.getCmp('T_pricing-carloadPricingFloatIndex').add(Ext.create('Ext.panel.Panel', {
	  	id:'T_pricing-carloadPricingFloatIndex_content',
		cls:"panelContentNToolbar",
		bodyCls:'panelContent-body',
		//获得查询FORM
		getQueryPricePlanForm : function() {
			return queryform;
		},
		//获得查询结果GRID
		getPricePlanGridPanel : function() {
			if(Ext.isEmpty(this.gridPanel)){
				this.gridPanel = Ext.create('Foss.pricing.carloadPricingFloat.PricePlanGridPanel');//查询结果GRID
			}
			return gridPanel;
		},
		items : [queryform,gridPanel]
	}));
});


