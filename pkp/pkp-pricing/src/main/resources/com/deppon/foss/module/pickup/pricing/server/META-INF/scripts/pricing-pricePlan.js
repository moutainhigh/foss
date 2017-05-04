/**
 * 该模块维护FOSS汽运价格信息,通过与区域、产品条目、
 * 来设置不同维度的价格信息（上海-北京 精准-通用 重货2.8元/斤 轻货250/立方 最低一票）
 * 
 */

/**
 * 
 * @type String 激活
 */
pricing.pricePlan.yes = 'Y';
/**
 * 
 * @type  价格方案ID
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

/**
 * <p>
 * 设置分段标签关闭属性<br/>
 * <p>
 * @param  tab 选项卡
 * @param  index 对应标签角标
 * @param  closable 设置是否可关闭
 * @returns tab 选项卡
 * @author 148246-孙建博
 */
pricing.setTabClosable = function(tab,index,closable){
	//缓存当前form的值
	var oldFormValues = tab.items.get(index).getValues();
	//缓存分段数
	var section = index+1;
	//重新渲染
	tab.remove(index);
	var newTabForm = Ext.create('Foss.pricing.pricePlan.PricePlanDetialSectionForm',{
		title : '第'+section+'段',
		//取消前一标签页关闭功能
		closable : closable,
		sectionID : section
	});
	newTabForm.getForm().setValues(oldFormValues);
	tab.add(newTabForm);
	return tab;
}

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
		timeout:1800000,
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
			Ext.Msg.alert(pricing.pricePlan.i18n('foss.pricing.promptMessage'),result.message);
		}
	});
};
/**
 * Ajax请求--json
 * @param {} url  请求服务器url
 * @param {} params 请求参数
 * @param {} successFn 成功回调服务
 * @param {} failFn    失败回调服务
 * 用于批量激活或者批量中止，设置较长的相应时间
 */
pricing.requestJsonAjaxMax = function(url,params,successFn,failFn)
{
	Ext.Ajax.request({
		url:url,
		jsonData:params,
		timeout:1800000,
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
			Ext.Msg.alert(pricing.pricePlan.i18n('foss.pricing.promptMessage'),result.message);
		}
	});
};

/**
 * 价格方案明细模型
 */
Ext.define('Foss.pricing.pricePlan.PricePlanDetailDtoModel', {
    extend: 'Ext.data.Model',
    fields : [{
    	name : 'valuationId', //计费规则ID
        type : 'string'
    },{
        name : 'arrvRegionId', //目的区域ID
        type : 'string'
    },{
    	name : 'arrvRegionCode', //目的区域code
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
    },{//[POP]modify by 148246 start
        name : 'heavyPrice'//重货价格Ⅰ
    },{
        name : 'lightPrice'//轻货价格 Ⅰ
    },{
        name : 'heavyPrice2'//重货价格Ⅱ
    },{
        name : 'lightPrice2'//轻货价格Ⅱ
    },{
        name : 'heavyCriticalVal2'//重量临界值Ⅱ
    },{
        name : 'lightCriticalVal2'//体积临界值Ⅱ
    },{
        name : 'heavyPrice3'//重货价格Ⅲ
    },{
        name : 'lightPrice3'//轻货价格Ⅲ
    },{
        name : 'heavyCriticalVal3'//重量临界值Ⅲ
    },{
        name : 'lightCriticalVal3'//体积临界值Ⅲ
    },{
        name : 'heavyPrice4'//重货价格Ⅳ
    },{
        name : 'lightPrice4'//轻货价格Ⅳ
    },{
        name : 'heavyCriticalVal4'//重量临界值Ⅳ
    },{
        name : 'lightCriticalVal4'//体积临界值Ⅳ
    },{
        name : 'heavyPrice5'//重货价格Ⅴ
    },{
        name : 'lightPrice5'//轻货价格Ⅴ
    },{
        name : 'heavyCriticalVal5'//重量临界值Ⅴ
    },{
        name : 'lightCriticalVal5'//体积临界值Ⅴ
    },{
        name : 'heavyPrice6'//重货价格Ⅵ
    },{
        name : 'lightPrice6'//轻货价格Ⅵ
    },{
        name : 'heavyCriticalVal6'//重量临界值Ⅵ
    },{
        name : 'lightCriticalVal6'//体积临界值Ⅵ
    },{
        name : 'createTime', //创建时间
        type : 'date',
        defaultValue : null,
        convert : pricing.changeLongToDate
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
    }]//[POP]modify by 148246 end
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
/**
 * productCode
 * 精准大票产品类型
 */
//精准大票-通用
pricing.Product_Code_Rectsion = 'RECISION_BG_PRODUCTS';
//普车大票-通用
pricing.Product_Code_Common = 'COMMON_BG_PRODUCTS';
pricing.EXPRESS_RCP='EXPRESS_RCP';
pricing.EXPRESS='EXPRESS';


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
				name:pricing.pricePlan.i18n('i18n.pricingRegion.all'),//"全部",
				id:"",
				code:""						
			};
			pricing.queryProductItemEntityList.push(nullproduct);
			Ext.Array.each(countries, function(countrie, index, countriesItSelf) {
				//过滤空运产品、和筛选汽运的货物类型, 原因是汽运目前没有按照货物类型来分类。 固目前只会按照货物类型为通用来处理
				if(countrie.productCode != pricing.AIR_FREIGHT && countrie.goodstypeCode == pricing.GOODS_TYPE_ALL 
						&& countrie.productCode != pricing.Product_Code_Rectsion && countrie.productCode != pricing.Product_Code_Common ){
					arrays.push(countrie);
				}
			});
			pricing.queryProductItemEntityList = arrays;
		},
		failure:function(response){
			var result = Ext.decode(response.responseText);
			if(Ext.isEmpty(result)){
				pricing.showErrorMes(pricing.pricePlan.i18n('i18n.pricingRegion.requestTimeOut'));
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
				//同时过滤掉精准大票价格方案中的产品条目
				if(countrie.productCode != pricing.AIR_FREIGHT && countrie.goodstypeCode == pricing.GOODS_TYPE_ALL
						&& countrie.productCode != pricing.Product_Code_Rectsion 
						&& countrie.productCode != pricing.Product_Code_Common 
						&& countrie.productCode != pricing.EXPRESS_RCP
						&& countrie.productCode != pricing.EXPRESS){
					arrays.push(countrie);
				}
			});
			pricing.queryProductItemEntityAddList = arrays;
		},
		failure:function(response){
			var result = Ext.decode(response.responseText);
			if(Ext.isEmpty(result)){
				pricing.showErrorMes(pricing.pricePlan.i18n('i18n.pricingRegion.requestTimeOut'));
			}else{
				pricing.showErrorMes(result.message);
			}
		}
	});
};

/**
 * 价格方案批次model
 */
Ext.define('Foss.pricing.pricePlan.PricePlanModel', {
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
        name : 'modifyUserName',
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
    	name:'showTime',
    	type : 'string'
    },{
    	name:'isPromptly'
    }]
});

/**
 * 价格方案批次store
 */
Ext.define('Foss.pricing.pricePlan.PricePlanStore',{
	extend: 'Ext.data.Store',
	model: 'Foss.pricing.pricePlan.PricePlanModel',
	pageSize:20,
	proxy: {
		type : 'ajax',
		actionMethods:'POST',
		url: pricing.realPath('queryPricePlanBatchInfo.action'),
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
					'priceManageMentVo.pricePlanEntity.active' : 	  n.active,
					'priceManageMentVo.pricePlanEntity.name' : 	  n.name,
					'priceManageMentVo.pricePlanEntity.priceRegionCode'	: n.priceRegionCode,
					'priceManageMentVo.pricePlanEntity.beginTime'	: n.beginTime,
					'priceManageMentVo.pricePlanEntity.endTime'	: n.endTime
				}
			});			
		}
	}
});
/**
 * 价格方案明细store
 */
Ext.define('Foss.pricing.pricePlan.PricePlanDeatilStore',{
	extend: 'Ext.data.Store',
	model: 'Foss.pricing.pricePlan.PricePlanDetailDtoModel',
	pageSize:10,
	proxy: {
		type : 'ajax',
		actionMethods:'POST',
		url: pricing.realPath('queryPricePlanDetailInfo.action'),
		reader : {
			type : 'json',
			root : 'priceManageMentVo.pricePlanDetailDtoList',
			totalProperty : 'totalCount',
			successProperty: 'success'
		}
	}
});

/**
 * 价格方案比对查询store
 */
Ext.define('Foss.pricing.pricePlan.ComparePricePlanStore',{
	extend: 'Ext.data.Store',
	model: 'Foss.pricing.pricePlan.PricePlanModel',
	proxy: {
		type : 'ajax',
		actionMethods:'POST',
		url: pricing.realPath('queryComparePricePlanInfo.action'),
		reader : {
			type : 'json',
			root : 'priceManageMentVo.pricePlanEntityList',
			successProperty: 'success'
		}
	}
});

/**
 * 价格方案比对结果信息store
 */
Ext.define('Foss.pricing.pricePlan.ComparePricePlanResultStore',{
	extend: 'Ext.data.Store',
	model: 'Foss.pricing.pricePlan.PricePlanDetailDtoModel',
	proxy: {
		type : 'ajax',
		actionMethods:'POST',
		timeout:1800000,
		url: pricing.realPath('queryPricePlanCompareResult.action'),
		reader : {
			type : 'json',
			root : 'priceManageMentVo.pricePlanDetailDtoList',
			successProperty: 'success'
		}
	}
});
/**
 * 价格方案批次查询form表单
 */
Ext.define('Foss.pricing.pricePlan.PricePlanFormPanel', {
	extend : 'Ext.form.Panel',
	title: pricing.pricePlan.i18n('i18n.pricingRegion.searchCondition'),
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
		fieldLabel:pricing.pricePlan.i18n('foss.pricing.scenarioName'),//方案名称
		name: 'name',
        maxLength : 60,
        maxLengthText:pricing.pricePlan.i18n('foss.pricing.theProgramNameLengthExceedsTheMaximumLimit'),
	    allowBlank:true,
	    columnWidth:.3
	},{
		xtype: 'container',
		columnWidth:.2,
		html: '&nbsp;'
	},{
		name: 'priceRegionCode',
	    fieldLabel:pricing.pricePlan.i18n('foss.pricing.originatingArea'),//始发区域
	    xtype : 'commonpriceregionselector',
	    columnWidth:.3
	},{
		xtype:'datefield',
		fieldLabel:pricing.pricePlan.i18n('foss.pricing.startDate'),//开始日期
		format:'Y-m-d',
		name:'beginTime',
		columnWidth:.3
	},{
		xtype: 'container',
		columnWidth:.2,
		html: '&nbsp;'
	},{
		xtype:'datefield',
		fieldLabel:pricing.pricePlan.i18n('foss.pricing.endDate'),//结束日期
		format:'Y-m-d',
		name:'endTime',
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
	    ,[{'key':'N','value':pricing.pricePlan.i18n('i18n.pricingRegion.unActive')},{'key':'Y','value':pricing.pricePlan.i18n('i18n.pricingRegion.active')}
	    ,{'key':'ALL','value':pricing.pricePlan.i18n('i18n.pricingRegion.all')}]),
	    fieldLabel: pricing.pricePlan.i18n('foss.pricing.status'),//状态
	    xtype : 'combo'
	}];
	me.fbar = [{
			xtype : 'button', 
			width:70,
			left : 1,
			text : pricing.pricePlan.i18n('foss.pricing.reset'),//重置
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
				text : pricing.pricePlan.i18n('i18n.pricingRegion.search'),//"查询",
				disabled: !pricing.pricePlan.isPermission('pricePlan/pricePlanQueryButton'),
				hidden: !pricing.pricePlan.isPermission('pricePlan/pricePlanQueryButton'),
				handler : function() {
					var beginTime = me.getForm().findField('beginTime').getValue();
					var endTime = me.getForm().findField('endTime').getValue();
					//时间控件结束时间加一天，变相达到查询结束时间当天23:59:59效果
					endTime = Ext.Date.add(new Date(endTime), Ext.Date.DAY, 1);
					if(!Ext.isEmpty(beginTime) && !Ext.isEmpty(endTime)){
						if(beginTime>endTime){
							pricing.showWoringMessage(pricing.pricePlan.i18n('foss.pricing.deadlineForInputGreaterThanEfectiveDate'));//截止日期要大于起始日期！
			    			return;
						}
					}
					var grid = Ext.getCmp('T_pricing-pricePlan_content').getPricePlanGridPanel();
					grid.getPagingToolbar().moveFirst();
				}
			}]
			},{
				xtype: 'displayfield',
				columnWidth : .15,
				value:pricing.pricePlan.i18n('foss.pricing.specialP')//根据业务日期查询介于生效日期和截止日期之间的增值服务
		}];
		me.callParent([cfg]);
	}
});

/**
 * 价格方案批次列表gird
 */
Ext.define('Foss.pricing.pricePlan.PricePlanGridPanel',{
	extend: 'Ext.grid.Panel',
	title : pricing.pricePlan.i18n('i18n.pricingRegion.searchResults'),//查询结果
	emptyText: pricing.pricePlan.i18n('foss.pricing.theQueryResultIsEmpty'),//查询结果为空
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
			this.addPricePlanWindow = Ext.create('Foss.pricing.pricePlan.PricePlanAddWindow');
			this.addPricePlanWindow.parent = this;
		}
		return this.addPricePlanWindow;
	},
	
	updatePricePlanWindow : null,
	//获取修改价格方案窗口
	getUpdatePricePlanWindow : function(){
		if(Ext.isEmpty(this.updateStandardWindow)){
			this.updatePricePlanWindow = Ext.create('Foss.pricing.pricePlan.PricePlanUpdateWindow');
			//设置器父元素
			this.updatePricePlanWindow.parent = this;
		}
		return this.updatePricePlanWindow;
	},
	
	//获取价格方案比对窗口
	comparePricePlanWindow:null,
	getComparepricePlanWindow : function(pricePlanEntity){
		if(Ext.isEmpty(this.comparePricePlanWindow)){
			this.comparePricePlanWindow = Ext.create('Ext.pricing.pricePlan.PricePlanCompareShowWindow');
			this.comparePricePlanWindow.parent = this;
		}
		this.comparePricePlanWindow.pricePlanEntity = pricePlanEntity;
		return this.comparePricePlanWindow;
	},
	
	//中止方案弹出日期选择
	stopPricePlanWindow:null,
	getStopPricePlanWindow: function(pricePlanId){
		if(Ext.isEmpty(this.stopPricePlanWindow)){
			this.stopPricePlanWindow = Ext.create('Foss.pricing.pricePlan.PricePlanStopEndTimeWindow');
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
	getimmediatelyActivePricePlanWindow: function(idList){
		if(Ext.isEmpty(this.immediatelyActivePricePlanWindow)){
			this.immediatelyActivePricePlanWindow = Ext.create('Foss.pricing.pricePlan.PricePlanImmediatelyActiveTimeWindow');
			this.immediatelyActivePricePlanWindow.parent = this;
		}
		this.immediatelyActivePricePlanWindow.idList = idList;
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
	getImmediatelyStopPricePlanWindow: function(idList){
		if(Ext.isEmpty(this.immediatelyStopPricePlanWindow)){
			this.immediatelyStopPricePlanWindow = Ext.create('Foss.pricing.pricePlan.PricePlanImmediatelyStopEndTimeWindow');
			this.immediatelyStopPricePlanWindow.parent = this;
		}
		this.immediatelyStopPricePlanWindow.idList = idList;
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

	 	for(var i=0;i<selections.length;i++){
	 		if(selections[i].get('active')!=pricing.pricePlan.yes){
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
	
	/**
	 * 立即激活
	 */
    immediatelyActivePricePlan:function(){
    	var me = this;
	 	var selections = me.getSelectionModel().getSelection();
	 	if(selections.length < 1){
	 		pricing.showWoringMessage('请选择一条记录进行操作！');
			return;
	 	}

		for(var i=0;i<selections.length;i++){
	 		if(selections[i].get('active')==pricing.pricePlan.yes){
		 		pricing.showWoringMessage('请选择未激活数据进行操作！');
		 		return;
		 	}
	 	}
		//批量激活汽运价格方案
		var idList = new Array();
		for(var i = 0 ; i<selections.length ; i++){
			idList.push(selections[i].get('id'));
		}
		
 		var window = me.getimmediatelyActivePricePlanWindow(idList);
 		window.show();
 		
	},
	
	//返回批次store
	pricePlanStore:null,
	getPricePlanStore: function(){
		if(Ext.isEmpty(this.pricePlanStore)){
			this.pricePlanStore = Ext.create('Foss.pricing.pricePlan.PricePlanStore');
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
			this.pricePlanDetailShowWindow = Ext.create('Ext.pricing.pricePlan.PricePlanDetailShowWindow');	
			this.pricePlanDetailShowWindow.parent = this;
		}
		return this.pricePlanDetailShowWindow;
	},
	
	//删除价格方案
	deletePricePlan:function(){
	 	var me = this;
	 	var selections = me.getSelectionModel().getSelection();
	 	if(selections.length < 1){
	 		pricing.showWoringMessage(pricing.pricePlan.i18n('foss.pricing.pleaseSelectOneDeleteOperation'));
			return;
	 	}
	 	//过滤草稿状态数据进行删除
	 	for(var i = 0;i<selections.length;i++){
			if(selections[i].get('active')==pricing.pricePlan.yes){
				pricing.showWoringMessage(pricing.pricePlan.i18n('foss.pricing.pleaseChooseToNotActivateTheDataToBeBeleted'));
				return;
			}
		}
		//是否要删除这些汽运价格方案？
		pricing.showQuestionMes(pricing.pricePlan.i18n('foss.pricing.theseTheQiyunPriceProgramYouWantToRemove'),function(e){
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
						pricing.showErrorMes(pricing.pricePlan.i18n('foss.pricing.requestTimedOut'));//请求超时
					}else{
						pricing.showErrorMes(json.message);
					}
				};
				var url = pricing.realPath('deletePricePlan.action');
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
			pricing.showErrorMes(pricing.pricePlan.i18n('foss.pricing.pleaseChooseThePriceYouWantToActivateTheProgram'));//请选择要激活的价格方案！
			return;
		}
		//过滤草稿状态数据进行激活
	 	for(var i = 0;i<selections.length;i++){
			if(selections[i].get('active')==pricing.pricePlan.yes){
				pricing.showWoringMessage(pricing.pricePlan.i18n('foss.pricing.pleaseSelectNotActivateProgramDataForActivation'));
				return;
			}
			pricePlans.push(selections[i].get('id'));
		}
		if(pricePlans.length<1){
			pricing.showErrorMes(pricing.pricePlan.i18n('foss.pricing.pleaseSelectAtLeastOneInactivePriceProgram'));//请至少选择一条未激活的价格方案！
			return;
		}
		//是否要激活这些汽运价格方案？
		pricing.showQuestionMes(pricing.pricePlan.i18n('foss.pricing.theseQiyunPriceProgramWhetherYouWantActivate'),function(e){
			if(e=='yes'){
				var params = {'priceManageMentVo':{'pricePlanIds':pricePlans}};
				var successFun = function(json){
					pricing.showInfoMes(json.message);
					me.getPagingToolbar().moveFirst();
				};
				var failureFun = function(json){
					if(Ext.isEmpty(json)){
						pricing.showErrorMes(pricing.pricePlan.i18n('i18n.pricingRegion.requestTimeOut'));//请求超时
					}else{
						pricing.showErrorMes(json.message);
					}
				};
				var url = pricing.realPath('activePricePlan.action');
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
			this.uploadPriceform = Ext.create('Foss.pricing.pricePlan.UploadPriceform');	
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
			this.queryPricePlanForm  = Ext.create('Foss.pricing.pricePlan.PricePlanFormPanel')
		}
		return this.queryPricePlanForm;
    },
	//价格方案比对
	comparePricePlan:function(){
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
	 	var pricePlanEntity = selections[0].data;	
 		var window = me.getComparepricePlanWindow(pricePlanEntity);
 		window.show();
	},
	
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.store = me.getPricePlanStore();
		me.selModel = me.getCheckboxModel();
		me.bbar = me.getPagingToolbar();
		me.columns = [{xtype: 'rownumberer',
			width:40,
			text : pricing.pricePlan.i18n('i18n.pricingRegion.num')//序号 
		},{
			text : pricing.pricePlan.i18n('i18n.pricingRegion.opra'),
			align : 'center',
			xtype : 'actioncolumn',
			items: [{
				iconCls: 'deppon_icons_showdetail',
                tooltip: pricing.pricePlan.i18n('foss.pricing.details'), 
                disabled: !pricing.pricePlan.isPermission('pricePlan/pricePlanQueryDetailButton'),
				width:42,
                handler: function(grid, rowIndex, colIndex) {
    				var record=grid.getStore().getAt(rowIndex);
                	me.getPricePlanDetailShowWindow().show();
                	me.getPricePlanDetailShowWindow().pricePlanId = record.get('id');
                }
			},{
				iconCls:'deppon_icons_edit',
				tooltip: pricing.pricePlan.i18n('foss.pricing.toAmendTheProposal'), 
				disabled: !pricing.pricePlan.isPermission('pricePlan/pricePlanUpdateButton'),
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
    						pricing.showErrorMes(pricing.pricePlan.i18n('foss.pricing.requestTimedOut'));//请求超时
    					}else{
    						pricing.showErrorMes(json.message);
    					}
    				};
    				var url = pricing.realPath('queryPricePlanAndDetailInfoById.action');
    				pricing.requestJsonAjax(url,params,successFun,failureFun);
				}
			},{
				iconCls:'deppon_icons_softwareUpgrade',
				tooltip: pricing.pricePlan.i18n('foss.pricing.replicationScheme'), 
				disabled: !pricing.pricePlan.isPermission('pricePlan/pricePlanCopyButton'),
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
						pricing.showErrorMes(pricing.pricePlan.i18n('foss.pricing.pleaseSelectTheProgramHaBbeenActivatedCopy'));
					}else{
						pricing.showQuestionMes(pricing.pricePlan.i18n('foss.pricing.toDeterminePriceProgramCopy'),function(e){
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
										pricing.showErrorMes(pricing.pricePlan.i18n('foss.pricing.requestTimedOut'));//请求超时
									}else{
										pricing.showErrorMes(json.message);
									}
								};
								var url = pricing.realPath('copyPricePlan.action');
								pricing.requestJsonAjax(url,params,successFun,failureFun);
							}
						});
					}
				}
			},{
				iconCls:'deppon_icons_end',
				tooltip: pricing.pricePlan.i18n('foss.pricing.stop'), 
				disabled: !pricing.pricePlan.isPermission('pricePlan/pricePlanStopButton'),
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
			text: pricing.pricePlan.i18n('foss.pricing.scenarioName'), //"方案名称", 
	        dataIndex: 'name'
		},{
			width: 140,
			text: pricing.pricePlan.i18n('foss.pricing.theOriginatingRegion'),//"始发区域",
	        dataIndex: 'priceRegionName'
		},{
			text: pricing.pricePlan.i18n('foss.pricing.updateTime'),//"修改时间",
			width: 140,
	        dataIndex: 'modifyDate',
	        renderer:function(value){
				return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
			}
		},{
			text: pricing.pricePlan.i18n('foss.pricing.modifyUser'),//"修改人",
			width: 140,
	        dataIndex: 'modifyUserName'
		},{
			text: pricing.pricePlan.i18n('foss.pricing.availabilityDate'),//"生效日期",
	        width: 80,
	        dataIndex: 'beginTime',
	        renderer:function(value){
				return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
			}
		},{
			text: pricing.pricePlan.i18n('foss.pricing.endTimeTwo'),//"截止日期",
	        width: 80,
	        dataIndex: 'endTime',
	        renderer:function(value){
				return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
			}
		},{
			width: 80,
			text: pricing.pricePlan.i18n('foss.pricing.dataType'),//"数据状态",
	        sortable: true,
	        dataIndex: 'active',
	        renderer:function(value){
				if(value=='Y'){//'Y'表示激活
					return pricing.pricePlan.i18n('i18n.pricingRegion.active')//"已激活";
				}else if(value=='N'){//'N'表示未激活
					return  pricing.pricePlan.i18n('i18n.pricingRegion.unActive')//"未激活";
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
			text : pricing.pricePlan.i18n('foss.pricing.theNewScheme'),//"新建方案",
			disabled: !pricing.pricePlan.isPermission('pricePlan/pricePlanAddButton'),
			hidden: !pricing.pricePlan.isPermission('pricePlan/pricePlanAddButton'),
			handler :function(){
				var addWindow = me.getAddpricePlanWindow();
				addWindow.isUpdate = false;
				addWindow.show();
				
			} 
		/**,'-', {
			text : pricing.pricePlan.i18n('foss.pricing.activationProgram'),
			hidden: !pricing.pricePlan.isPermission('pricePlan/pricePlanActiveButton'),
			handler :function(){
				me.activePricePlan();
			} 
			**/
		},'-', {
			text : pricing.pricePlan.i18n('foss.pricing.deleteProgram'),
			disabled: !pricing.pricePlan.isPermission('pricePlan/pricePlanDeleteButton'),
			hidden: !pricing.pricePlan.isPermission('pricePlan/pricePlanDeleteButton'),
			handler :function(){
				me.deletePricePlan();
			} 
		},'-', {
			text : pricing.pricePlan.i18n('foss.pricing.planActivationProgram'),//'立即激活',
			disabled:!pricing.pricePlan.isPermission('pricePlan/pricePlanImmediatelyActiveButton'),
			hidden:!pricing.pricePlan.isPermission('pricePlan/pricePlanImmediatelyActiveButton'),
			handler :function(){
				me.immediatelyActivePricePlan();
			} 
		},'-', {
			text : pricing.pricePlan.i18n('foss.pricing.planStopProgram'),//'立即中止',
			disabled:!pricing.pricePlan.isPermission('pricePlan/pricePlanImmediatelyStopButton'),
			hidden:!pricing.pricePlan.isPermission('pricePlan/pricePlanImmediatelyStopButton'),
			handler :function(){
				me.immediatelyStopPricePlan();
			} 
		},'-',{
			text : pricing.pricePlan.i18n('foss.pricing.export'),
			disabled:!pricing.pricePlan.isPermission('pricePlan/pricePlanExportButton'),
			hidden:!pricing.pricePlan.isPermission('pricePlan/pricePlanExportButton'),
		    //插件配置代码
		    plugins: {
		        ptype: 'buttondisabledplugin',
		        seconds: 8
		    },
			handler :function(){
				var queryForm = me.getQueryPricePlanForm();
				var pricePlanExport = '';
				var name = queryForm.getForm().findField('name').getValue(); // 方案名称
				var deptRegionCode = queryForm.getForm().findField('priceRegionCode').getValue(); //始发区域编码
				var active = queryForm.getForm().findField('active').getValue(); //始发区域编码
				
				pricePlanExport ='priceManageMentVo.pricePlanEntity.active='+active;
				
				if(!Ext.isEmpty(name)){
					pricePlanExport = pricePlanExport+'&'+'priceManageMentVo.pricePlanEntity.name='+name;
				}
				if(!Ext.isEmpty(deptRegionCode)){
					if(!Ext.isEmpty(pricePlanExport)){
						pricePlanExport = pricePlanExport+'&'+'priceManageMentVo.pricePlanEntity.deptRegionCode='+deptRegionCode;
					}else{
						pricePlanExport = 'priceManageMentVo.pricePlanEntity.deptRegionCode='+deptRegionCode;
					}
				}
				var url = pricing.realPath('exportPricePlan.action');
				if(!Ext.isEmpty(pricePlanExport)){
					url = url+'?'+pricePlanExport;
				}
				window.location=url;
				pricePlanExport = '';
 			} 
		},'-', {
			text : pricing.pricePlan.i18n('foss.pricing.import'),
			disabled:!pricing.pricePlan.isPermission('pricePlan/pricePlanImportButton'),
			hidden:!pricing.pricePlan.isPermission('pricePlan/pricePlanImportButton'),
			handler :function(){
			 	me.getUploadPriceform().show();
			} 
		},'-', {
			text : '价格比对',//"价格比对",
			disabled: !pricing.pricePlan.isPermission('pricePlan/pricePlanCompareButton'),
			hidden: !pricing.pricePlan.isPermission('pricePlan/pricePlanCompareButton'),
			handler :function(){
				me.comparePricePlan();
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
Ext.define('Ext.pricing.pricePlan.PricePlanDetailShowWindow',{
	extend : 'Ext.window.Window',
	title: pricing.pricePlan.i18n('foss.pricing.thePriceOfTheProgramDetailQuery'),//价格方案明细查询
	closable : true,
	modal : true,
	resizable:false,
	closeAction : 'hide',
	parent:null,//(Foss.pricing.pricePlan.PricePlanGridPanel)
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
			me.getPricePlanDetailShowGridPanel().getStore().load();
		}
	},
    //明细信息查询-FORM
	queryPricePlanDetailForm:null,
    getQueryPricePlanDetailForm:function(){
    	if(Ext.isEmpty(this.queryPricePlanDetailForm)){
    		this.queryPricePlanDetailForm = Ext.create('Foss.pricing.pricePlan.QueryPricePlanDetailForm');
    	}
    	return this.queryPricePlanDetailForm;
    },
    //明细信息结果集
    pricePlanDetailShowGridPanel:null,
    getPricePlanDetailShowGridPanel:function(){
    	if(Ext.isEmpty(this.pricePlanDetailShowGridPanel)){
    		this.pricePlanDetailShowGridPanel = Ext.create('Foss.pricing.pricePlan.PricePlanDetailShowGridPanel');
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
Ext.define('Foss.pricing.pricePlan.QueryPricePlanDetailForm', {
	extend : 'Ext.form.Panel',
	title: pricing.pricePlan.i18n('i18n.pricingRegion.searchCondition'),
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
		    store:pricing.getStore(null,null,['id','code','name'],pricing.queryProductItemEntityList),
	        fieldLabel:pricing.pricePlan.i18n('foss.pricing.productEntry'),//条目
	        xtype : 'combo'
		},{
			name: 'arrvRegionId',
			valueField: 'id',
	        fieldLabel:pricing.pricePlan.i18n('foss.pricing.destinationArea'),//目的区域
	        priceRegionFlag:'ARRIVE_REGION',
	        xtype : 'commonallpriceregionselector'
		},{
			xtype : 'container',
			columnWidth : .2,
			margin : '0 0 0 0',
			items : [{
				xtype : 'button', 
				width:70,
				text : pricing.pricePlan.i18n('i18n.pricingRegion.search'),
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
Ext.define('Foss.pricing.pricePlan.PricePlanDetailShowGridPanel', {
	extend: 'Ext.grid.Panel',
	title :pricing.pricePlan.i18n('i18n.pricingRegion.searchResults'),//查询结果
	frame: true,
	height :450,
    sortableColumns:false,
    enableColumnHide:false,
    enableColumnMove:false,
	stripeRows : true, // 交替行效果
	selType : "rowmodel", // 选择类型设置为：行选择
	emptyText: pricing.pricePlan.i18n('foss.pricing.theQueryResultIsEmpty'),//查询结果为空
	
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
			text : pricing.pricePlan.i18n('i18n.pricingRegion.num')//序号
		},{
			text :pricing.pricePlan.i18n('foss.pricing.destinationStation'),//目的站
			dataIndex : 'arrvRegionName',
			flex:2
		},{
			text :pricing.pricePlan.i18n('foss.pricing.productEntry'),//产品条目
			dataIndex : 'productItemName',
			flex:2
		}/*,{//[POP]modify by 148246 start
			text :pricing.pricePlan.i18n('foss.pricing.heavyGoodsPrices'),//重货价格
			dataIndex : 'heavyPrice',
			flex:2
		},{
			text :pricing.pricePlan.i18n('foss.pricing.lightCargoPrices'),//轻货价格
			dataIndex : 'lightPrice',
			flex:2
		}*/,{//[POP]modify by 148246 end
			text : pricing.pricePlan.i18n('foss.pricing.theLowestVotes'),//最低一票
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
		},{//[POP]modify by 148246 start
			text: pricing.pricePlan.i18n('foss.pricing.heavyPrice1'),//重货价格Ⅰ
	    	flex:2,
	        dataIndex: 'heavyPrice'
		},{
			text: pricing.pricePlan.i18n('foss.pricing.lightPrice1'),//轻货价格Ⅰ
	    	flex:2,
	        dataIndex: 'lightPrice'
	    },{
	    	text: pricing.pricePlan.i18n('foss.pricing.heavyCriticalVal2'),//重量临界值Ⅱ
	    	flex:2,
	        dataIndex: 'heavyCriticalVal2'
	    },{
	    	text: pricing.pricePlan.i18n('foss.pricing.lightCriticalVal2'),//体积临界值Ⅱ
	    	flex:2,
	        dataIndex: 'lightCriticalVal2'
		},{
	    	text: pricing.pricePlan.i18n('foss.pricing.heavyPrice2'),//重货价格Ⅱ
	    	flex:2,
	        dataIndex: 'heavyPrice2'
	    },{
	    	text: pricing.pricePlan.i18n('foss.pricing.lightPrice2'),//轻货价格Ⅱ
	    	flex:2,
	        dataIndex: 'lightPrice2'
	    },{
	    	text: pricing.pricePlan.i18n('foss.pricing.heavyCriticalVal3'),//重量临界值Ⅲ
	    	flex:2,
	        dataIndex: 'heavyCriticalVal3'
	    },{
	    	text: pricing.pricePlan.i18n('foss.pricing.lightCriticalVal3'),//体积临界值Ⅲ
	    	flex:2,
	        dataIndex: 'lightCriticalVal3'
	    },{
	    	text: pricing.pricePlan.i18n('foss.pricing.heavyPrice3'),//重货价格Ⅲ
	    	flex:2,
	        dataIndex: 'heavyPrice3'
	    },{
	    	text: pricing.pricePlan.i18n('foss.pricing.lightPrice3'),//轻货价格Ⅲ
	    	flex:2,
	    	dataIndex: 'lightPrice3'
	    },{
	    	text: pricing.pricePlan.i18n('foss.pricing.heavyCriticalVal4'),//重量临界值Ⅳ
	    	flex:2,
	        dataIndex: 'heavyCriticalVal4'
	    },{
	    	text: pricing.pricePlan.i18n('foss.pricing.lightCriticalVal4'),//体积临界值Ⅳ
	    	flex:2,
	        dataIndex: 'lightCriticalVal4'
	    },{
	    	text: pricing.pricePlan.i18n('foss.pricing.heavyPrice4'),//重货价格Ⅳ
	    	flex:2,
	        dataIndex: 'heavyPrice4'
	    },{
	    	text: pricing.pricePlan.i18n('foss.pricing.lightPrice4'),//轻货价格Ⅳ
	    	flex:2,
	    	dataIndex: 'lightPrice4'
	    },{
	    	text: pricing.pricePlan.i18n('foss.pricing.heavyCriticalVal5'),//重量临界值Ⅴ
	    	flex:2,
	        dataIndex: 'heavyCriticalVal5'
	    },{
	    	text: pricing.pricePlan.i18n('foss.pricing.lightCriticalVal5'),//体积临界值Ⅴ
	    	flex:2,
	        dataIndex: 'lightCriticalVal5'
	    },{
	    	text: pricing.pricePlan.i18n('foss.pricing.heavyPrice5'),//重货价格Ⅴ
	    	flex:2,
	        dataIndex: 'heavyPrice5'
	    },{
	    	text: pricing.pricePlan.i18n('foss.pricing.lightPrice5'),//轻货价格Ⅴ
	    	flex:2,
	    	dataIndex: 'lightPrice5'
	    },{
	    	text: pricing.pricePlan.i18n('foss.pricing.heavyCriticalVal6'),//重量临界值Ⅵ
	    	flex:2,
	    	dataIndex: 'heavyCriticalVal6'
	    },{
	    	text: pricing.pricePlan.i18n('foss.pricing.lightCriticalVal6'),//体积临界值Ⅵ
	    	flex:2,
	        dataIndex: 'lightCriticalVal6'
	    },{
	    	text: pricing.pricePlan.i18n('foss.pricing.heavyPrice6'),//重货价格Ⅵ
	    	flex:2,
	        dataIndex: 'heavyPrice6'
	    },{
	    	text: pricing.pricePlan.i18n('foss.pricing.lightPrice6'),//轻货价格Ⅵ
	    	flex:2,
	        dataIndex: 'lightPrice6'
	    
		},{//[POP]modify by 148246 end
			text : pricing.pricePlan.i18n('foss.pricing.remark'),//备注
			dataIndex : 'remark',
			flex:2
		}];
		me.store = Ext.create('Foss.pricing.pricePlan.PricePlanDeatilStore',{
			autoLoad : false,
			pageSize : 10,
			listeners: {
				beforeload: function(store, operation, eOpts){
					var queryForm =me.up('window').getQueryPricePlanDetailForm();
					var pricePlanId = me.up('window').pricePlanId;
					if(queryForm!=null){
						Ext.apply(operation,{
							params : {
								'priceManageMentVo.queryPricePlanDetailBean.productItemCode' : queryForm.getForm().findField('productItemCode').getValue(),//产品条目编码
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
			text : pricing.pricePlan.i18n('foss.pricing.export'),
			handler :function(){
				
			/**
			 * 	beforeload: function(store, operation, eOpts){
					var queryForm = me.up('window').getQueryPricePlanDetailForm();
					var pricePlanId = me.up('window').pricePlanId;
					if(queryForm!=null){
						Ext.apply(operation,{
							params : {
								'priceManageMentVo.queryPricePlanDetailBean.productItemCode' : queryForm.getForm().findField('productItemCode').getValue(),//产品条目编码
								'priceManageMentVo.queryPricePlanDetailBean.arrvRegionId' : queryForm.getForm().findField('arrvRegionId').getValue(),//目的区域编码
								'priceManageMentVo.queryPricePlanDetailBean.pricePlanId' : pricePlanId//价格方案ID
							}
						});	
					}
				}
			 */
				var queryForm = me.up('window').getQueryPricePlanDetailForm();
				var pricePlanId = me.up('window').pricePlanId;
				var productItemCode = queryForm.getForm().findField('productItemCode').getValue();//产品条目编码
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
				if(!Ext.isEmpty(productItemCode)){
					if(!Ext.isEmpty(productItemCode)){
						pricePlanExport = pricePlanExport+'&'+'priceManageMentVo.queryPricePlanDetailBean.productItemCode='+productItemCode;
					}else{
						pricePlanExport = 'priceManageMentVo.queryPricePlanDetailBean.productItemCode='+productItemCode;
					}
				}
				var url = pricing.realPath('exportPricePlanDetail.action');
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
Ext.define('Foss.pricing.pricePlan.PricePlanDetailForm', {
	extend : 'Ext.form.Panel',
	//frame: true,
	//height:200,
	collapsible: true,
	isSearchComb:true,
    defaults : {
    	colspan : 1,
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
	        fieldLabel:pricing.pricePlan.i18n('foss.pricing.destinationArea'),//目的区域
	        allowBlank:false,
	        priceRegionFlag:'ARRIVE_REGION',
	        xtype : 'commonallpriceregionselector',
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
		    store:pricing.getStore(null,null,['id','code','name'],pricing.queryProductItemEntityAddList),
	        fieldLabel:pricing.pricePlan.i18n('foss.pricing.productEntry'),//产品条目
	        xtype : 'combo',
	        listeners:{
	        	select:function(comb,records){
	        		comb.productItemId = records[0].get('id');
	        		me.getForm().findField('minimumOneTicket').setValue(records[0].get('feeBottom'));
	        	}
	        }
		}/*,{//[POP] modify 148246-孙建博 2014-10-22 start
			name: 'heavyPrice',
			allowBlank:false,
			decimalPrecision:2,
			step:0.01,
		    maxValue: 999999.99,
		    minValue:0,
	        fieldLabel: pricing.pricePlan.i18n('foss.pricing.heavyGoodsPrices'),//重货价格
	        xtype : 'numberfield'
		
		},{
			name: 'lightPrice',
			allowBlank:false,
			decimalPrecision:2,
			step:0.01,
		    maxValue: 999999.99,
		    minValue:0,
	        fieldLabel: pricing.pricePlan.i18n('foss.pricing.lightCargoPrices'),//轻货价格
	        xtype : 'numberfield'
		}*/,{//[POP] modify 148246-孙建博 2014-10-22 end
			name: 'minimumOneTicket',
			allowBlank:false,
			decimalPrecision:0,
			step:1,
		    maxValue: 99999999,
		    minValue:0,
	        fieldLabel: pricing.pricePlan.i18n('foss.pricing.theLowestVotes'),//最低一票
	        xtype : 'numberfield'
		},{
			 xtype:'radiogroup',
			 vertical:true,
			 allowBlank:false,
			 name:'centralizePickup',
			 fieldLabel:pricing.pricePlan.i18n('foss.pricing.whetherorNot'),
			 items:[{
			 	 xtype:'radio',
			     boxLabel:pricing.pricePlan.i18n('i18n.pricingRegion.ye'),//是
			     name:'centralizePickup',		 
			     inputValue:'Y'
		     },{
		    	 xtype:'radio',
			     boxLabel:pricing.pricePlan.i18n('i18n.pricingRegion.no'),//否
			     name:'centralizePickup',
			     inputValue:'N'
			     }]
		},{
			xtype: 'textareafield',
			colspan : 2,
			width:500,
		    fieldLabel: pricing.pricePlan.i18n('foss.pricing.remark'),//备注
		    maxLength:200,
	        name:'remark'
		}];
		me.callParent([cfg]);
	}
});

/**
 * [POP] author 148246-孙建博 2014-10-22
 * 价格方案明细信息窗口下的分段标签页
 */

Ext.define('Foss.pricing.pricePlan.PricePlanDetailTabPanelView',{
	extend:'Ext.tab.Panel'
	,minTabWidth:90
	,initComponent:function(){
		var me = this;
		me.items = [me.getPricePlanDetailSectionForm()];
		me.callParent();
	}
	//第1段
	,pricePlanDetailSectionForm:null
	,getPricePlanDetailSectionForm:function(){
		if(Ext.isEmpty(this.pricePlanDetailSectionForm)){
			var firstForm = Ext.create('Foss.pricing.pricePlan.PricePlanDetialSectionForm');
			firstForm.getForm().findField('heavyCriticalVal').hide();
			firstForm.getForm().findField('lightCriticalVal').hide();
			firstForm.getForm().findField('heavyCriticalVal').allowBlank=true;
			firstForm.getForm().findField('lightCriticalVal').allowBlank=true;
			firstForm.getForm().findField('heavyPrice').colspan = 2;
			firstForm.getForm().findField('lightPrice').colspan = 2;
    		this.pricePlanDetailSectionForm = firstForm;
    		
    	}
		return this.pricePlanDetailSectionForm;
	}
});
/**
 * [POP] author 148246-孙建博 2014-10-22
 * 价格方案明细分段标签页form
 */
Ext.define('Foss.pricing.pricePlan.PricePlanDetialSectionForm', {
	extend : 'Ext.form.Panel',
	title : '第1段',
	collapsible: true,
	frame: true,
	isSearchComb:true,//?
    defaults : {
    	margin : '5 5 5 5',
    	labelWidth:90,
    	anchor : '100%'
    },
	defaultType : 'textfield',
	layout: {
        type: 'table',
        columns: 2
    },
    sectionID : 1,
    listeners:{
		beforeclose:function(me){
			var tab = me.up('panel');
			//激活前一标签
			tab.setActiveTab(tab.items.length-2);
			if(tab.items.length > 2){
    			//取消前一标签页关闭功能
    			pricing.setTabClosable(tab,tab.items.length-2,true);
    		}
		}
	},
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [{
			name: 'heavyPrice',
			allowBlank:false,
			decimalPrecision:2,
			step:0.01,
		    maxValue: 999999.99,
		    minValue:0,
	        fieldLabel: pricing.pricePlan.i18n('foss.pricing.heavyGoodsPrices'),//重货价格
	        xtype : 'numberfield'
		},{
			name: 'heavyCriticalVal',
			allowBlank:false,
			decimalPrecision:2,
			step:0.01,
		    maxValue: 999999.99,
		    minValue:0,
	        fieldLabel: pricing.pricePlan.i18n('foss.pricing.heavyCriticalVal'),//重量临界值
	        xtype : 'numberfield'
		},{
			name: 'lightPrice',
			allowBlank:false,
			decimalPrecision:2,
			step:0.01,
		    maxValue: 999999.99,
		    minValue:0,
	        fieldLabel: pricing.pricePlan.i18n('foss.pricing.lightCargoPrices'),//轻货价格
	        xtype : 'numberfield'
		},{
			name: 'lightCriticalVal',
			allowBlank:false,
			decimalPrecision:2,
			step:0.01,
		    maxValue: 999999.99,
		    minValue:0,
	        fieldLabel: pricing.pricePlan.i18n('foss.pricing.lightCriticalVal'),//体积临界值
	        xtype : 'numberfield'
		}];
		me.callParent([cfg]);
	}
});

/**
 * 价格方案明细信息新增弹出窗口window
 */
Ext.define('Foss.pricing.pricePlan.PricePlanDetailWindow',{
	extend : 'Ext.window.Window',
	title: pricing.pricePlan.i18n('foss.pricing.aBreakdownOfNew'),//明细信息新增
	closable : true,
	modal : true,
	resizable:false,
	closeAction : 'hide',
	parent:null,
	width :600,
	//height :600,
	pricePlanDetailDto:null,
	grid:null,																	//父 grid
	listeners:{
		beforehide:function(me){
			me.getPricePlanDetailForm().getForm().reset();
			var tab = me.getPricePlanDetailTabPanelView();
			//清空第一段tab的form
			tab.getPricePlanDetailSectionForm().getForm().reset();
			//删除第一段之外的其他tab
			var length = tab.items.length;
			for(;length > 1;length--){
				tab.remove(length-1);
			}
		},
		beforeshow:function(me){
		}
	},
    //明细信息新增-FORM
    pricePlanDetailForm:null,
    getPricePlanDetailForm:function(){
    	if(Ext.isEmpty(this.pricePlanDetailForm)){
    		this.pricePlanDetailForm = Ext.create('Foss.pricing.pricePlan.PricePlanDetailForm');
    	}
    	return this.pricePlanDetailForm;
    },
    //明细信息新增-tabPanel [POP] author 148246-孙建博 2014-10-22
    pricePlanDetailTabPanelView:null,
    getPricePlanDetailTabPanelView:function(){
    	if(Ext.isEmpty(this.pricePlanDetailTabPanelView)){
    		this.pricePlanDetailTabPanelView = Ext.create('Foss.pricing.pricePlan.PricePlanDetailTabPanelView');
    	}
    	return this.pricePlanDetailTabPanelView;
    },
    
    //新增分段标签页 [POP] author 148246-孙建博 2014-10-22
    addTab:function(){
    	var me = this;
    	var tab = me.getPricePlanDetailTabPanelView();
    	if(tab.items.length < 6){
    		//新增标签页序号
    		var newTabSerial = tab.items.length + 1;
    		//创建新form
    		var pricePlanDetialSectionForm = Ext.create('Foss.pricing.pricePlan.PricePlanDetialSectionForm',{
    			title : '第'+newTabSerial+'段',
    			closable : true,
    			sectionID : newTabSerial
    		});
    		if(tab.items.length > 1){
    			//取消前一标签页关闭功能
    			pricing.setTabClosable(tab,tab.items.length-1,false);
    		}
    		//将新form渲染到tab中
    		tab.add(pricePlanDetialSectionForm);
    		//激活新增标签
    		tab.setActiveTab(tab.items.length-1);
    	}else{
    		Ext.ux.Toast.msg(pricing.pricePlan.i18n('foss.pricing.promptMessage'),pricing.pricePlan.i18n('foss.pricing.tooManyTabs'),'error', 3000);
    	}
    },
    
    
    //想GRID中设置数据
    commitPricePlanDetail:function(grid){
    	var me = this;
    	//得到明细form
    	var form = me.getPricePlanDetailForm().getForm();
    	var tab = me.getPricePlanDetailTabPanelView();
    	//定义用于存储tab中所有form数据的数组
    	var arr = new Array();
    	var tabCheckPass = true;//定义flag用于检测输入数据有效性
        tab.items.each(function(item){
        	if(!item.getForm().isValid()){
        		tabCheckPass = false;
        	}
        	var values = item.getValues();
        	//分段号
        	values.sectionID = item.sectionID;
            arr.push(values);
        });
        if(form.isValid() && tabCheckPass){
        	//验证临界值，后一段的不能小于前一段的临界值，第一段没有临界值
			for(var i=1;i<arr.length-1;i++){
				if(arr[i].heavyCriticalVal*1 >= arr[i+1].heavyCriticalVal*1){
					pricing.showErrorMes(pricing.pricePlan.i18n('foss.pricing.heavyCriticalValOutOfTurn').replace('n',i+2).replace('m',i+1));
					return ;
				}
				if(arr[i].lightCriticalVal*1 >= arr[i+1].lightCriticalVal*1){
					pricing.showErrorMes(pricing.pricePlan.i18n('foss.pricing.lightCriticalValOutOfTurn').replace('n',i+2).replace('m',i+1));
					return ;
				}
    		}
			//验证价格不能小於等於0
    		for(var i in arr){
    			if(arr[i].heavyPrice<=0){
    				pricing.showErrorMes(pricing.pricePlan.i18n('foss.pricing.thePriceOfHeavyCargoMustBeGreaterThan0'));
    				return ;
    			}
    			if(arr[i].lightPrice<=0){
    				pricing.showErrorMes(pricing.pricePlan.i18n('foss.pricing.lightGoodsPriceMustbegreaterthan0'));
    				return ;
    			}
    		}
    		/*var heavyPrice = pricePlanDetailDto.data.heavyPrice;//重货价格
    		var lightPrice = pricePlanDetailDto.data.lightPrice;//轻货价格 
    		if(heavyPrice<=0){
    			pricing.showErrorMes(pricing.pricePlan.i18n('foss.pricing.thePriceOfHeavyCargoMustBeGreaterThan0'));
    			return ;
    		}
    		
    		if(lightPrice<=0){
    			pricing.showErrorMes(pricing.pricePlan.i18n('foss.pricing.lightGoodsPriceMustbegreaterthan0'));
    			return;
    		}*/
			//获取明细信息
    		var pricePlanDetailDto = new Foss.pricing.pricePlan.PricePlanDetailDtoModel();
    		form.updateRecord(pricePlanDetailDto);
    		
	    	var productItemId = form.findField('productItemCode').productItemId;
	    	var productItemName = form.findField('productItemCode').getRawValue();
	    	var arrvRegionCode = form.findField('arrvRegionCode').getValue();
	    	var arrvRegionName = form.findField('arrvRegionCode').arrvRegionName;
	    	var arrvRegionId = form.findField('arrvRegionCode').arrvRegionId;
	    	var remark = form.findField('remark').getValue();
	    	
	    	//设置明细信息
	    	pricePlanDetailDto.set('productItemId',productItemId);
	    	pricePlanDetailDto.set('productItemName',productItemName);
	    	pricePlanDetailDto.set('arrvRegionCode',arrvRegionCode);
	    	pricePlanDetailDto.set('arrvRegionId',arrvRegionId);
	    	pricePlanDetailDto.set('arrvRegionName',arrvRegionName);
	    	pricePlanDetailDto.set('remark',remark);
	    	pricePlanDetailDto.set('pricePlanId',pricing.pricePlanId);
	    	
    		//如果sectionID=1的元素不为数组中第一个元素，找到sectionID=1的元素和一位互换
	    	if(arr[0].sectionID != 1){
	    		Ext.Array.each(arr,function(item,index,arr){
					if(item.sectionID == 1){
						var tmp = arr[0];
						arr[0] = item;
						arr[index] = tmp;
					}
	    		});
	    	}
	    	//把dto数据封装到arr数组第一个元素中
	    	var dtoData = pricePlanDetailDto.data;
    		//var dtoData = Ext.clone(pricePlanDetailDto.data);
			dtoData.sectionID = arr[0].sectionID;
			dtoData.heavyPrice = arr[0].heavyPrice;
			dtoData.lightPrice = arr[0].lightPrice;
			arr[0] = dtoData;
	    	
			//制定json请求参数
			var params = {'priceManageMentVo':{'pricePlanDetailDtoList':arr}};
			//ajax请求
			var url = pricing.realPath('addPricePlanDetail.action');
			
			//成功提示
			var successFun = function(json){
				pricing.showInfoMes(json.message);
				me.close();
				var arrayDate = json.priceManageMentVo.pricePlanDetailDtoList;
				if(!Ext.isEmpty(arrayDate)){
					grid.store.loadData(arrayDate);//显示第一页	
				}
		    };
		    //失败提示
		    var failureFun = function(json){
		    	if(Ext.isEmpty(json)){
					pricing.showErrorMes(pricing.pricePlan.i18n('i18n.pricingRegion.requestTimeOut'));
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
		me.items = [//设置window的元素 [POP] modify 148246-孙建博 2014-10-22
			me.getPricePlanDetailForm()
			,me.getPricePlanDetailTabPanelView()
			];
		me.fbar = [{
			text : pricing.pricePlan.i18n('i18n.pricingRegion.cancel'),//取消
			handler :function(){
				me.close();
			} 
		},{
			text : pricing.pricePlan.i18n('foss.pricing.reset'),//重置
			//margin:'0 185 0 0',
			margin:'0 50 0 0',
			handler :function(){
				if(Ext.isEmpty(me.pricePlanDetailDto)){
					me.getPricePlanDetailForm().getForm().loadRecord(new Foss.pricing.pricePlan.PricePlanDetailDtoModel());
				}else{
					me.getPricePlanDetailForm().getForm().loadRecord(new Foss.pricing.pricePlan.PricePlanDetailDtoModel(me.effectivePlanEntity));
				}
			} 
		},{
			text : pricing.pricePlan.i18n('foss.pricing.addTab'),//增加
			handler :function(){
				me.addTab();
			} 
		},
		'->',{
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
Ext.define('Foss.pricing.pricePlan.PricePlanAddFormPanel',{
	extend : 'Ext.form.Panel',
	title: pricing.pricePlan.i18n('foss.pricing.departureinformation'),//"出发地信息",
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
	/**修改价格方案**/
	commitUpdatePricePlan:function(){
    	var me = this;
    	var baseForm = me.getForm();
    	if(baseForm.isValid()){//校验form是否通过校验
    		var pricePlanEntity = me.up('window').pricePlanEntity;
    		var priceRegionId = baseForm.findField('priceRegionCode').priceRegionId;
    		var priceRegionName = baseForm.findField('priceRegionCode').priceRegionName;
    		var priceRegionCode = baseForm.findField('priceRegionCode').value;
    		var pricePlanModel = new Foss.pricing.pricePlan.PricePlanModel(pricePlanEntity);
    		if(!Ext.isEmpty(priceRegionId)){
    			pricePlanModel.set('priceRegionId',priceRegionId);	
    		}
    		if(!Ext.isEmpty(priceRegionName)){
    			pricePlanModel.set('priceRegionName',priceRegionName);
    		}
    		if(!Ext.isEmpty(priceRegionCode)){
    			pricePlanModel.set('priceRegionCode',priceRegionCode);	
    		}
    		
    		baseForm.updateRecord(pricePlanModel);//将FORM中数据设置到MODEL里面
    		var params = {'priceManageMentVo':{'pricePlanEntity':pricePlanModel.data}};//组织需要修改的数据
    		var successFun = function(json){
				pricing.showInfoMes(json.message);//提示新增成功
			};
			var failureFun = function(json){
				if(Ext.isEmpty(json)){
					pricing.showErrorMes(pricing.pricePlan.i18n('foss.pricing.requestTimedOut'));//请求超时
				}else{
					pricing.showErrorMes(json.message);//提示失败原因
				}
			};
			var url = pricing.realPath('updatePricePlan.action');//请求价格方案修改
			pricing.requestJsonAjax(url,params,successFun,failureFun);//发送AJAX请求
    	}
	 },
	
	/***批次保存***/
	commitPricePlan:function(){
		var me = this;
    	//获取表单
    	var form = me.getForm();
    	var pricePlanModel = new Foss.pricing.pricePlan.PricePlanModel();
    	if(form.isValid()){
    		form.updateRecord(pricePlanModel);
    		var priceRegionId = form.findField('priceRegionCode').priceRegionId;
    		var priceRegionName = form.findField('priceRegionCode').priceRegionName;
    		
    		//处理特殊字段
    		pricePlanModel.set('priceRegionId',priceRegionId);
    		pricePlanModel.set('priceRegionName',priceRegionName);
    		var params = {'priceManageMentVo':{'pricePlanEntity':pricePlanModel.data}};
    		var url = pricing.realPath('addPricePlan.action');
    		
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
    				pricing.showErrorMes(pricing.pricePlan.i18n('i18n.pricingRegion.requestTimeOut'));
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
    			this.pricePlanDetailGridPanel = Ext.create('Foss.pricing.pricePlan.PricePlanDetailGridPanel');
    			pricing.pricePlanDetailGridPanel  = this.pricePlanDetailGridPanel;
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
	        fieldLabel:pricing.pricePlan.i18n('foss.pricing.scenarioName')//方案名称
		},{
			name: 'priceRegionCode',
			allowBlank:false,
	        fieldLabel:pricing.pricePlan.i18n('foss.pricing.originatingArea'),//始发区域
	        xtype : 'commonpriceregionselector',
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
			fieldLabel:pricing.pricePlan.i18n('foss.pricing.availabilityDate'),//生效日期
			format:'Y-m-d',
			name:'beginTime'
		},{
			name: 'description',
			colspan : 2,
	        fieldLabel:pricing.pricePlan.i18n('foss.pricing.programDescription'),//方案描述
	        xtype : 'textareafield',
	        maxLength : 200
		},{
			name: 'arrvRegionCode',
	        fieldLabel:pricing.pricePlan.i18n('foss.pricing.destinationAreaNew'),//目的区域
	        xtype : 'commonallpriceregionselector',
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
			text : pricing.pricePlan.i18n('foss.pricing.destinationAreaSearch'),//目的区域查询
			cls:'yellow_button',
			handler :function(){
				me.arrvRegionSearch();
			}  
		},{
			text :pricing.pricePlan.i18n('i18n.pricingRegion.cancel'),//取消
			handler :function(){
				me.up('window').isUpdate = null;
				me.up().close();
			}
		},{
			text : pricing.pricePlan.i18n('foss.pricing.reset'),//重置
			handler :function(){
					me.getForm().reset();//表格重置
			} 
		},{
			text : pricing.pricePlan.i18n('foss.pricing.save'),//保存
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
Ext.define('Foss.pricing.pricePlan.PricePlanDetailGridPanel',{
	extend: 'Ext.grid.Panel',
	title : pricing.pricePlan.i18n('foss.pricing.destinationInformation'),//"目的地信息",
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
    		this.pricePlanDetailWindow = Ext.create('Foss.pricing.pricePlan.PricePlanDetailWindow',{
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
    		this.modifyPricePlanDetailWindow = Ext.create('Foss.pricing.pricePlan.ModifyPriceDetailWindow',{
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
			pricing.showWoringMessage(pricing.pricePlan.i18n('foss.pricing.pleaseSelectOneDeleteOperation'));//请选择一条进行删除操作！
			return;//没有则提示并返回
		}
		for(var i = 0;i<selections.length;i++){
			if(selections[i].get('active')==pricing.pricePlan.yes){
				pricing.showWoringMessage(pricing.pricePlan.i18n('foss.pricing.pleaseChooseToNotActivateTheDataToBeBeleted'));//请选择未激活数据进行删除！
				return;//没有则提示并返回
			}
		}
		pricing.showQuestionMes(pricing.pricePlan.i18n('foss.pricing.youWantDeletePricProgramDetails'),function(e){//是否要删除这些价格方案明细？
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
						pricing.showErrorMes(pricing.pricePlan.i18n('foss.pricing.requestTimedOut'));//请求超时
					}else{
						pricing.showErrorMes(json.message);
					}
				};
				var url = pricing.realPath('deletePricePlanDetail.action');
				pricing.requestJsonAjax(url,params,successFun,failureFun);
			}
		})	
    },
	//初始化构造器
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.pricing.pricePlan.PricePlanDeatilStore',{
			autoLoad : false,
			pageSize : 10,
			listeners: {
				beforeload: function(store, operation, eOpts){
					var pricePlanId = pricing.pricePlanId
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
	            text: pricing.pricePlan.i18n('i18n.pricingRegion.add'),
	            handler:function(){ 
	            	me.getPricePlanDetailWindow().show();	 
	            }
	        },'-',{
	            text: pricing.pricePlan.i18n('foss.pricing.delete'),
	            handler:function(){
	            	me.deletePricePlanDetail(me); 
	            }
	    }];
		me.store = Ext.create('Foss.pricing.pricePlan.PricePlanDeatilStore',{
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
		text: pricing.pricePlan.i18n('i18n.pricingRegion.num')//"序号"//序号
		
		},{
			text : pricing.pricePlan.i18n('i18n.pricingRegion.opra'),
			align : 'center',
			xtype : 'actioncolumn',
			items: [{
					iconCls:'deppon_icons_edit',
					tooltip: pricing.pricePlan.i18n('foss.pricing.toAmendTheProposal'), 
					width:42,
					handler: function(grid, rowIndex, colIndex){
						var me = this;
	                	var record = grid.up().getStore().getAt(rowIndex);//选中数据
	                	var pricePlanDetaiModel = new Foss.pricing.pricePlan.PricePlanDetailDtoModel();
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
	    						pricing.showErrorMes(pricing.pricePlan.i18n('foss.pricing.requestTimedOut'));//请求超时
	    					}else{
	    						pricing.showErrorMes(json.message);
	    					}
	    				};
	    				var url = pricing.realPath('queryBeforeModifyPricePlanDetail.action');
	    				pricing.requestJsonAjax(url,params,successFun,failureFun);
					}
			}]
		},{
			text: pricing.pricePlan.i18n('foss.pricing.destinationArea'),//目的区域
			width: 120,
	        dataIndex: 'arrvRegionName'
		},{
			text: pricing.pricePlan.i18n('foss.pricing.productEntryName'),// "产品条目名称",
			width: 90,
	        dataIndex: 'productItemName'
		}/*,{
			text: pricing.pricePlan.i18n('foss.pricing.heavyGoodsPrices'),//,重货价格
	    	width: 60,
	        dataIndex: 'heavyPrice'
		},{
			text: pricing.pricePlan.i18n('foss.pricing.lightCargoPrices'),//轻货价格
	    	width: 60,
	        dataIndex: 'lightPrice'
		}*/,{
			text: pricing.pricePlan.i18n('foss.pricing.theLowestVotes'),//最低一票
			width: 65,
	        dataIndex: 'minimumOneTicket'
	    },{
			width: 100,
			text: pricing.pricePlan.i18n('foss.pricing.whetherAcceptGoods'),//"是否接货",
	        dataIndex: 'centralizePickup',
	        renderer:function(value){
				if(value=='Y'){
					return pricing.pricePlan.i18n('i18n.pricingRegion.ye')//"是";
				}else if(value=='N'){
					return pricing.pricePlan.i18n('i18n.pricingRegion.no')//"否";
				}else{
					return '';
				}
			}
		},{//[POP]modify by 148246 start
			text: pricing.pricePlan.i18n('foss.pricing.heavyPrice1'),//重货价格Ⅰ
	    	width: 60,
	        dataIndex: 'heavyPrice'
		},{
			text: pricing.pricePlan.i18n('foss.pricing.lightPrice1'),//轻货价格Ⅰ
	    	width: 60,
	        dataIndex: 'lightPrice'
	    },{
	    	text: pricing.pricePlan.i18n('foss.pricing.heavyCriticalVal2'),//重量临界值Ⅱ
	    	width: 60,
	    	dataIndex: 'heavyCriticalVal2'
	    },{
	    	text: pricing.pricePlan.i18n('foss.pricing.lightCriticalVal2'),//体积临界值Ⅱ
	    	width: 60,
	        dataIndex: 'lightCriticalVal2'
	    },{
	    	text: pricing.pricePlan.i18n('foss.pricing.heavyPrice2'),//重货价格Ⅱ
	    	width: 60,
	        dataIndex: 'heavyPrice2'
	    },{
	    	text: pricing.pricePlan.i18n('foss.pricing.lightPrice2'),//轻货价格Ⅱ
	    	width: 60,
	        dataIndex: 'lightPrice2'
	    },{
	    	text: pricing.pricePlan.i18n('foss.pricing.heavyCriticalVal3'),//重量临界值Ⅲ
	    	width: 60,
	    	dataIndex: 'heavyCriticalVal3'
	    },{
	    	text: pricing.pricePlan.i18n('foss.pricing.lightCriticalVal3'),//体积临界值Ⅲ
	    	width: 60,
	        dataIndex: 'lightCriticalVal3'
	    },{
	    	text: pricing.pricePlan.i18n('foss.pricing.heavyPrice3'),//重货价格Ⅲ
	    	width: 60,
	        dataIndex: 'heavyPrice3'
	    },{
	    	text: pricing.pricePlan.i18n('foss.pricing.lightPrice3'),//轻货价格Ⅲ
	    	width: 60,
	        dataIndex: 'lightPrice3'
	    },{
	    	text: pricing.pricePlan.i18n('foss.pricing.heavyCriticalVal4'),//重量临界值Ⅳ
	    	width: 60,
	    	dataIndex: 'heavyCriticalVal4'
	    },{
	    	text: pricing.pricePlan.i18n('foss.pricing.lightCriticalVal4'),//体积临界值Ⅳ
	    	width: 60,
	        dataIndex: 'lightCriticalVal4'
	    },{
	    	text: pricing.pricePlan.i18n('foss.pricing.heavyPrice4'),//重货价格Ⅳ
	    	width: 60,
	        dataIndex: 'heavyPrice4'
	    },{
	    	text: pricing.pricePlan.i18n('foss.pricing.lightPrice4'),//轻货价格Ⅳ
	    	width: 60,
	        dataIndex: 'lightPrice4'
	    },{
	    	text: pricing.pricePlan.i18n('foss.pricing.heavyCriticalVal5'),//重量临界值Ⅴ
	    	width: 60,
	    	dataIndex: 'heavyCriticalVal5'
	    },{
	    	text: pricing.pricePlan.i18n('foss.pricing.lightCriticalVal5'),//体积临界值Ⅴ
	    	width: 60,
	        dataIndex: 'lightCriticalVal5'
	    },{
	    	text: pricing.pricePlan.i18n('foss.pricing.heavyPrice5'),//重货价格Ⅴ
	    	width: 60,
	        dataIndex: 'heavyPrice5'
	    },{
	    	text: pricing.pricePlan.i18n('foss.pricing.lightPrice5'),//轻货价格Ⅴ
	    	width: 60,
	        dataIndex: 'lightPrice5'
	    },{
	    	text: pricing.pricePlan.i18n('foss.pricing.heavyCriticalVal6'),//重量临界值Ⅵ
	    	width: 60,
	    	dataIndex: 'heavyCriticalVal6'
	    },{
	    	text: pricing.pricePlan.i18n('foss.pricing.lightCriticalVal6'),//体积临界值Ⅵ
	    	width: 60,
	        dataIndex: 'lightCriticalVal6'
	    },{
	    	text: pricing.pricePlan.i18n('foss.pricing.heavyPrice6'),//重货价格Ⅵ
	    	width: 60,
	        dataIndex: 'heavyPrice6'
	    },{
	    	text: pricing.pricePlan.i18n('foss.pricing.lightPrice6'),//轻货价格Ⅵ
	    	width: 60,
	        dataIndex: 'lightPrice6'
		},{//[POP]modify by 148246 end
			text: pricing.pricePlan.i18n('foss.pricing.remark'),//"备注",//备注
			width: 120,
	        dataIndex: 'remark'
		}],
		pricing.pagingBar = me.bbar;
		me.callParent([cfg]);
	}
});

/**
 * 价格方案弹出框
 */
Ext.define('Foss.pricing.pricePlan.PricePlanAddWindow',{
		extend: 'Ext.window.Window',
		title: "新增价格方案",
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
				//新增操作时候  刷新父容器中的数据 
				me.parent.getStore().load();
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
	    		this.pricePlanAddFormPanel = Ext.create('Foss.pricing.pricePlan.PricePlanAddFormPanel');
	    	} 
	    	return this.pricePlanAddFormPanel;
	    },
	     //价格方案明细信息 （目的明细列表）
		pricePlanDetailGridPanel:null,
	    getPricePlanDetailGridPanel: function(){
	    	if(Ext.isEmpty(this.pricePlanDetailGridPanel)){
	    		this.pricePlanDetailGridPanel = Ext.create('Foss.pricing.pricePlan.PricePlanDetailGridPanel');
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
Ext.define('Foss.pricing.pricePlan.PricePlanUpdateWindow',{
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
				me.getPricePlanAddFormPanel().getForm().loadRecord(new Foss.pricing.pricePlan.PricePlanModel(me.pricePlanEntity));
				me.getPricePlanAddFormPanel().getForm().findField('priceRegionCode').setCombValue(me.pricePlanEntity.priceRegionName,me.pricePlanEntity.priceRegionCode);
				me.getPricePlanDetailGridPanel().store.removeAll(true);
			}
		},
	    //价格方案批次信息 （出发地批次信息录入）
		pricePlanAddFormPanel:null,
	    getPricePlanAddFormPanel : function(){
	    	var me = this;
	    	if(Ext.isEmpty(me.pricePlanAddFormPanel)){
		    		me.pricePlanAddFormPanel = Ext.create('Foss.pricing.pricePlan.PricePlanAddFormPanel');
		    		//设置器父元素
 	    	} 
	    	return this.pricePlanAddFormPanel;
	    },
	     //价格方案明细信息 （目的明细列表）
		pricePlanDetailGridPanel:null,
	    getPricePlanDetailGridPanel: function(){
	    	if(Ext.isEmpty(this.pricePlanDetailGridPanel)){
	    			this.pricePlanDetailGridPanel = Ext.create('Foss.pricing.pricePlan.PricePlanDetailGridPanel');
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
 * 价格中止方案弹出框
 */
Ext.define('Foss.pricing.pricePlan.PricePlanStopEndTimeWindow',{
		extend: 'Ext.window.Window',
		title: pricing.pricePlan.i18n('foss.pricing.suspendPriceScheme'),//"中止价格方案",
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
	    		this.pricePlanStopFormPanel = Ext.create('Foss.pricing.pricePlan.PricePlanStopFormPanel');
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
Ext.define('Foss.pricing.pricePlan.PricePlanStopFormPanel',{
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
			var pricePlanModel = new Foss.pricing.pricePlan.PricePlanModel();
			form.updateRecord(pricePlanModel);
    		pricePlanModel.set('id',pricePlanId);
    		var params = {'priceManageMentVo':{'pricePlanEntity':pricePlanModel.data}};
    		
    		//ajax请求
    		var url = pricing.realPath('stopPricePlan.action');
    		
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
    				pricing.showErrorMes(pricing.pricePlan.i18n('i18n.pricingRegion.requestTimeOut'));
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
				fieldLabel:pricing.pricePlan.i18n('foss.pricing.deadline'),//截止日期
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
					text : pricing.pricePlan.i18n('foss.pricing.stop'),//"中止",
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
Ext.define('Foss.pricing.pricePlan.PricePlanImmediatelyStopEndTimeWindow',{
		extend: 'Ext.window.Window',
		title: pricing.pricePlan.i18n('foss.pricing.immediatelySupendPricePriceScheme'),//"立即中止方案",
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
				/**
				var beginTime = Ext.Date.format(new Date(me.pricePlanEntity.beginTime), 'Y-m-d H:i:s');
				var endTime = Ext.Date.format(new Date(me.pricePlanEntity.endTime), 'Y-m-d H:i:s');
				var value = pricing.pricePlan.i18n('foss.pricing.showleftTimeInfo')
					  +beginTime+pricing.pricePlan.i18n('foss.pricing.showmiddleTimeInfo')
					  +endTime+pricing.pricePlan.i18n('foss.pricing.showstopRightEndTimeInfo');
				me.pricePlanEntity.showTime = value;
				me.getPricePlanStopFormPanel().getForm().loadRecord(new Foss.pricing.pricePlan.PricePlanModel(me.pricePlanEntity));**/
				me.getPricePlanStopFormPanel().getForm().findField('showTime').setValue('中止日期必须大于当前营业日期，且不能大于原方案截止日期！');
				me.getPricePlanStopFormPanel().getForm().findField('endTime').setValue(Ext.Date.format(new Date(),'Y-m-d H:i:s'));
			}
		},
		pricePlanStopFormPanel:null,
		getPricePlanStopFormPanel : function(idList){
	    	if(Ext.isEmpty(this.pricePlanStopFormPanel)){
	    		this.pricePlanStopFormPanel = Ext.create('Foss.pricing.pricePlan.PricePlanImmediatelyStopFormPanel');
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

/**
 * 立即中止功能FormPanel
 */
Ext.define('Foss.pricing.pricePlan.PricePlanImmediatelyStopFormPanel',{
	extend : 'Ext.form.Panel',
	layout:'column',
	pricePlanEntity:null,
	idList:null,
	stopPricePlan:function(idList){
		var me = this;
    	var form = me.getForm();
    	if(form.isValid()){
			var pricePlanModel = new Foss.pricing.pricePlan.PricePlanModel();
			form.updateRecord(pricePlanModel);
//    		pricePlanModel.set('id',pricePlanId);
//    		pricePlanModel.set('isPromptly',true);
    		var params = {'priceManageMentVo':{'pricePlanEntity':pricePlanModel.data,'pricePlanIds':idList}};
    		pricePlanModel.set('endTime',Ext.Date.parse(form.findField('endTime').getValue(), 'Y-m-d H:i:s'));
    		var url = pricing.realPath('stopPricePlans.action');
    		var successFun = function(json){
    			pricing.showInfoMes(json.message);
    			me.up('window').hide();
    			//重新加载汽运价格方案界面的数据  刷新数据
    			me.up('window').parent.getStore().load();
				pricing.pagingBar.moveFirst();   			
    	    };
    	    var failureFun = function(json){
    	    	if(Ext.isEmpty(json)){
    				pricing.showErrorMes(pricing.pricePlan.i18n('i18n.pricingRegion.requestTimeOut'));
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
				name:'showTime',
				xtype: 'displayfield',
				columnWidth:.9
			},{ 
				fieldLabel :pricing.pricePlan.i18n('foss.pricing.suspendTime') ,//'中止日期',
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
				text : pricing.pricePlan.i18n('i18n.pricingRegion.determine'),//"确认",
				handler : function() {
					var idList = me.up('window').idList;
					me.stopPricePlan(idList);
				}
			},{
				xtype : 'button', 
				width:70,
				columnWidth:.15,
				text : pricing.pricePlan.i18n('i18n.pricingRegion.cancel'),//"取消",
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
Ext.define('Foss.pricing.pricePlan.PricePlanImmediatelyActiveTimeWindow',{
		extend: 'Ext.window.Window',
		title: pricing.pricePlan.i18n('foss.pricing.immediatelyActiveationPriceScheme'),//"立即激活方案",
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
				/**
				var beginTime = Ext.Date.format(new Date(me.pricePlanEntity.beginTime), 'Y-m-d H:i:s');
				var endTime = Ext.Date.format(new Date(me.pricePlanEntity.endTime), 'Y-m-d H:i:s');
				var value = pricing.pricePlan.i18n('foss.pricing.showleftTimeInfo')
					  +beginTime+pricing.pricePlan.i18n('foss.pricing.showmiddleTimeInfo')
					  +endTime+pricing.pricePlan.i18n('foss.pricing.showrightEndTimeInfo');
				me.pricePlanEntity.showTime = value;
				me.getPricePlanImmediatelyActiveFormPanel().getForm().loadRecord(new Foss.pricing.pricePlan.PricePlanModel(me.pricePlanEntity));
				me.getPricePlanImmediatelyActiveFormPanel().getForm().findField('beginTime').setValue(Ext.Date.format(me.pricePlanEntity.beginTime,'Y-m-d H:i:s'));**/
				me.getPricePlanImmediatelyActiveFormPanel().getForm().findField('showTime').setValue('生效时间默认显示当前时间，激活时间不能小于当前时间！');
				me.getPricePlanImmediatelyActiveFormPanel().getForm().findField('beginTime').setValue(Ext.Date.format(new Date(),'Y-m-d H:i:s'));
			}
		},
		//创建FormPanel 不管FormPanel是否存在都覆盖最新的信息值 idList
		pricePlanImmediatelyActiveFormPanel:null,
		getPricePlanImmediatelyActiveFormPanel : function(idList){
	    	if(Ext.isEmpty(this.pricePlanImmediatelyActiveFormPanel)){
	    		this.pricePlanImmediatelyActiveFormPanel = Ext.create('Foss.pricing.pricePlan.PricePlanImmediatelyActiveFormPanel');
	    	}
//	    	this.pricePlanImmediatelyActiveFormPanel.pricePlanEntity = pricePlanEntity;
	    	this.pricePlanImmediatelyActiveFormPanel.idList = idList;
	    	return this.pricePlanImmediatelyActiveFormPanel;
	    },
	   	constructor : function(config) {
			var me = this, cfg = Ext.apply({}, config);
			me.items = [me.getPricePlanImmediatelyActiveFormPanel(me.idList)];
			me.callParent(cfg);
		}
});


/**
 * 立即激活功能Form
 */
Ext.define('Foss.pricing.pricePlan.PricePlanImmediatelyActiveFormPanel',{
	extend : 'Ext.form.Panel',
	layout:'column',
	pricePlanEntity:null,
	idList:null,
	activetionPricePlan:function(idList){
		var me = this;
    	var form = me.getForm();
    	if(form.isValid()){
			var pricePlanModel = new Foss.pricing.pricePlan.PricePlanModel();
			form.updateRecord(pricePlanModel);
			pricePlanModel.set('beginTime',Ext.Date.parse(form.findField('beginTime').getValue(), 'Y-m-d H:i:s'));
    		var params = {
    				'priceManageMentVo':{'pricePlanEntity':pricePlanModel.data,'pricePlanIds':idList}
    		};
    		var url = pricing.realPath('immediatelyActivePricePlan.action');
    		var successFun = function(json){
    			Ext.getCmp('Foss_pricePlan_PricePlanImmediatelyActiveFormPanel_ID').setDisabled(false);
    			pricing.showInfoMes(json.message);
    			me.up('window').hide();
    			//重新加载汽运价格方案界面的数据 更新界面显示
    			me.up('window').parent.getStore().load();
				pricing.pagingBar.moveFirst();   			
    	    };
    	    var failureFun = function(json){
    	    	if(Ext.isEmpty(json)){
    				pricing.showErrorMes(pricing.pricePlan.i18n('i18n.pricingRegion.requestTimeOut'));
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
				fieldLabel:pricing.pricePlan.i18n('foss.pricing.availabilityDate'),//'生效日期',
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
//				plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin', {
//					  //设定间隔秒数,如果不设置，默认为2秒
//					  seconds: 20
//				}),
				text : pricing.pricePlan.i18n('i18n.pricingRegion.determine'),//,"确认",
				handler : function() {
					var form = this.up('form').getForm();
					Ext.getCmp('Foss_pricePlan_PricePlanImmediatelyActiveFormPanel_ID').setDisabled(true);
					var activeBeginTime = form.getValues().beginTime;
					var result = Ext.Date.parse(activeBeginTime,'Y-m-d H:i:s') - Ext.Date.parse(Ext.Date.format(new Date(),'Y-m-d H:i:s'),'Y-m-d H:i:s');
					if(result<0)
					{
						Ext.ux.Toast.msg(pricing.pricePlan.i18n('foss.pricing.promptMessage'),'激活时间不能小于当前时间','error', 3000);
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
				text : pricing.pricePlan.i18n('i18n.pricingRegion.cancel'),//"取消",
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
Ext.define('Foss.pricing.pricePlan.ModifyPriceDetailWindow',{
	extend : 'Ext.window.Window',
	title: '修改',//明细信息新增
	closable : true,
	modal : true,
	resizable:false,
	closeAction : 'hide',
	parent:null,
	width :600,
	//height :450,
	pricePlanDetailDto:null,
	grid:null,																	//父 grid
	listeners:{
		beforehide:function(me){
			me.getPricePlanDetailForm().getForm().reset();
			var tab = me.getPricePlanDetailTabPanelView();
			//清空第一段tab的form
			tab.getPricePlanDetailSectionForm().getForm().reset();
			//删除第一段之外的其他tab
			var length = tab.items.length;
			for(;length > 1;length--){
				tab.remove(length-1);
			}
		},
		beforeshow:function(me){
			var pricePlanDetailDtoModel = new Foss.pricing.pricePlan.PricePlanDetailDtoModel(me.pricePlanDetailDto);
			me.getPricePlanDetailForm().getForm().loadRecord(pricePlanDetailDtoModel);
			me.getPricePlanDetailForm().getForm().findField('arrvRegionCode').setCombValue(me.pricePlanDetailDto.arrvRegionName,me.pricePlanDetailDto.arrvRegionName);
			me.getPricePlanDetailForm().getForm().findField('arrvRegionCode').arrvRegionId = me.pricePlanDetailDto.arrvRegionId;
			me.getPricePlanDetailForm().getForm().findField('arrvRegionCode').arrvRegionName = me.pricePlanDetailDto.arrvRegionName;
			me.getPricePlanDetailForm().getForm().findField('productItemCode').setValue(me.pricePlanDetailDto.productItemName);
			me.getPricePlanDetailForm().getForm().findField('productItemCode').productItemId = me.pricePlanDetailDto.productItemId;
			me.getPricePlanDetailForm().getForm().findField('productItemCode').productItemName = me.pricePlanDetailDto.productItemName;
			me.getPricePlanDetailForm().getForm().findField('minimumOneTicket').setValue(me.pricePlanDetailDto.minimumOneTicket);
			//[pop] add 148246-孙建博 start
			//加载第一段的值
			me.getPricePlanDetailTabPanelView().getPricePlanDetailSectionForm().getForm().setValues(me.pricePlanDetailDto);
			//加载2-6段的值
			var tab = me.getPricePlanDetailTabPanelView();
			var heavyPrice,heavyCriticalVal,lightPrice,lightCriticalVal;
			for(var i=2;i<=6;i++){
				heavyPrice = pricePlanDetailDtoModel.get('heavyPrice'+i);
				heavyCriticalVal = pricePlanDetailDtoModel.get('heavyCriticalVal'+i);
				lightPrice = pricePlanDetailDtoModel.get('lightPrice'+i);
				lightCriticalVal = pricePlanDetailDtoModel.get('lightCriticalVal'+i);
				//当某一段数无数据时则结束循环
				if(Ext.isEmpty(heavyPrice)&&Ext.isEmpty(heavyCriticalVal)
				&&Ext.isEmpty(lightPrice)&&Ext.isEmpty(lightCriticalVal)){
					break;
				}else{
					//新增一个标签页
					me.addTab();
					//赋值到新增的标签页
					tab.items.get(i-1).getForm().findField('heavyPrice').setValue(heavyPrice);
					tab.items.get(i-1).getForm().findField('heavyCriticalVal').setValue(heavyCriticalVal);
					tab.items.get(i-1).getForm().findField('lightPrice').setValue(lightPrice);
					tab.items.get(i-1).getForm().findField('lightCriticalVal').setValue(lightCriticalVal);
				}
			}
			//[pop] add 148246-孙建博 end
		}
	},
    //明细信息新增-FORM
    pricePlanDetailForm:null,
    getPricePlanDetailForm:function(){
    	if(Ext.isEmpty(this.pricePlanDetailForm)){
    		this.pricePlanDetailForm = Ext.create('Foss.pricing.pricePlan.PricePlanDetailForm');
    	}
    	return this.pricePlanDetailForm;
    },
    //明细信息新增-tabPanel [POP] author 148246-孙建博 2014-10-22
    pricePlanDetailTabPanelView:null,
    getPricePlanDetailTabPanelView:function(){
    	if(Ext.isEmpty(this.pricePlanDetailTabPanelView)){
    		this.pricePlanDetailTabPanelView = Ext.create('Foss.pricing.pricePlan.PricePlanDetailTabPanelView');
    	}
    	return this.pricePlanDetailTabPanelView;
    },
    
    //新增分段标签页 [POP] author 148246-孙建博 2014-10-22
    addTab:function(){
    	var me = this;
    	var tab = me.getPricePlanDetailTabPanelView();
    	if(tab.items.length < 6){
    		//新增标签页序号
    		var newTabSerial = tab.items.length + 1;
    		//创建新form
    		var pricePlanDetialSectionForm = Ext.create('Foss.pricing.pricePlan.PricePlanDetialSectionForm',{
    			title : '第'+newTabSerial+'段',
    			closable : true,
    			sectionID : newTabSerial
    		});
    		if(tab.items.length > 1){
    			//取消前一标签页关闭功能
    			pricing.setTabClosable(tab,tab.items.length-1,false);
    		}
    		//将新form渲染到tab中
    		tab.add(pricePlanDetialSectionForm);
    		//激活新增标签
    		tab.setActiveTab(tab.items.length-1);
    	}else{
    		Ext.ux.Toast.msg(pricing.pricePlan.i18n('foss.pricing.promptMessage'),pricing.pricePlan.i18n('foss.pricing.tooManyTabs'),'error', 3000);
    	}
    },
    updatePriceDetailPlan:function(grid){
    	var me = this;
    	//得到明细form
    	var form = me.getPricePlanDetailForm().getForm();
    	var tab = me.getPricePlanDetailTabPanelView();
    	//定义用于存储tab中所有form数据的数组
    	var arr = new Array();
    	var tabCheckPass = true;//定义flag用于检测输入数据有效性
        tab.items.each(function(item){
        	if(!item.getForm().isValid()){
        		tabCheckPass = false;
        	}
        	var values = item.getValues();
        	//分段号
        	values.sectionID = item.sectionID;
            arr.push(values);
        });
        if(form.isValid() && tabCheckPass){
        	//验证临界值，后一段的不能小于前一段的临界值，第一段没有临界值
			for(var i=1;i<arr.length-1;i++){
				if(arr[i].heavyCriticalVal*1 >= arr[i+1].heavyCriticalVal*1){
					pricing.showErrorMes(pricing.pricePlan.i18n('foss.pricing.heavyCriticalValOutOfTurn').replace('n',i+2).replace('m',i+1));
					return ;
				}
				if(arr[i].lightCriticalVal*1 >= arr[i+1].lightCriticalVal*1){
					pricing.showErrorMes(pricing.pricePlan.i18n('foss.pricing.lightCriticalValOutOfTurn').replace('n',i+2).replace('m',i+1));
					return ;
				}
    		}
			//验证价格不能小於等於0
        	for(var i in arr){
    			if(arr[i].heavyPrice<=0){
    				pricing.showErrorMes(pricing.pricePlan.i18n('foss.pricing.thePriceOfHeavyCargoMustBeGreaterThan0'));
    				return ;
    			}
    			if(arr[i].lightPrice<=0){
    				pricing.showErrorMes(pricing.pricePlan.i18n('foss.pricing.lightGoodsPriceMustbegreaterthan0'));
    				return ;
    			}
    		}
    		
    		var pricePlanDetailDto = new Foss.pricing.pricePlan.PricePlanDetailDtoModel(me.pricePlanDetailDto);
    		form.updateRecord(pricePlanDetailDto);
    		var heavyPrice = pricePlanDetailDto.data.heavyPrice;//重货价格
    		var lightPrice = pricePlanDetailDto.data.lightPrice;//轻货价格 
    		var minimumOneTicket = pricePlanDetailDto.data.minimumOneTicket//最低一票
    		/*if(heavyPrice<=0){
    			pricing.showErrorMes(pricing.pricePlan.i18n('foss.pricing.thePriceOfHeavyCargoMustBeGreaterThan0'));
    			return ;
    		}
    		if(lightPrice<=0){
    			pricing.showErrorMes(pricing.pricePlan.i18n('foss.pricing.lightGoodsPriceMustbegreaterthan0'));
    			return;
    		}*/
			//获取明细信息
    	 
	    	var productItemId = form.findField('productItemCode').productItemId;
	    	var productItemName = form.findField('productItemCode').getRawValue();
	    	var arrvRegionCode = form.findField('arrvRegionCode').getValue();
	    	var arrvRegionName = form.findField('arrvRegionCode').arrvRegionName;
	    	var arrvRegionId = form.findField('arrvRegionCode').arrvRegionId;
	    	var remark = form.findField('remark').getValue();
	    	
	    	//设置明细信息
	    	pricePlanDetailDto.set('productItemId',productItemId);
	    	pricePlanDetailDto.set('productItemName',productItemName);
	    	pricePlanDetailDto.set('arrvRegionCode',arrvRegionCode);
	    	pricePlanDetailDto.set('arrvRegionId',arrvRegionId);
	    	pricePlanDetailDto.set('arrvRegionName',arrvRegionName);
	    	//以免丢失精度值问题，如重货与轻货以及最低一票化成分存入数据库
	    	pricePlanDetailDto.set('heavyPrice',heavyPrice*100);
	    	pricePlanDetailDto.set('lightPrice',lightPrice*100);
	    	pricePlanDetailDto.set('minimumOneTicket',minimumOneTicket);
	    	pricePlanDetailDto.set('remark',remark);
	    	pricePlanDetailDto.set('pricePlanId',pricePlanDetailDto.data.pricePlanId);
	    	
	    	//如果sectionID=1的元素不为数组中第一个元素，找到sectionID=1的元素和一位互换
	    	if(arr[0].sectionID != 1){
	    		Ext.Array.each(arr,function(item,index,arr){
					if(item.sectionID == 1){
						var tmp = arr[0];
						arr[0] = item;
						arr[index] = tmp;
					}
	    		});
	    	}
	    	//把dto数据封装到arr数组第一个元素中
	    	var dtoData = pricePlanDetailDto.data;
    		//var dtoData = Ext.clone(pricePlanDetailDto.data);
			dtoData.sectionID = arr[0].sectionID;
			dtoData.heavyPrice = arr[0].heavyPrice;
			dtoData.lightPrice = arr[0].lightPrice;
			arr[0] = dtoData;
			
			//制定json请求参数
			var params = {'priceManageMentVo':{'pricePlanDetailDtoList':arr}};
			//ajax请求
			var url = pricing.realPath('updatePriceDetailPlan.action');
			
			//成功提示
			var successFun = function(json){
				pricing.showInfoMes(json.message);
				pricing.pricePlanId = pricePlanDetailDto.data.pricePlanId;
				pricing.pagingBar.moveFirst();
				//[pop] add 148246-孙建博 start
				var arrayDate = json.priceManageMentVo.pricePlanDetailDtoList;
				if(!Ext.isEmpty(arrayDate)){
					grid.store.loadData(arrayDate);//显示第一页	
				}
				//[pop] add 148246-孙建博 end
		    };
		    //失败提示
		    var failureFun = function(json){
		    	if(Ext.isEmpty(json)){
					pricing.showErrorMes(pricing.pricePlan.i18n('i18n.pricingRegion.requestTimeOut'));
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
//		me.pricePlanDetailDto = cfg.pricePlanDetailDto;
		//设置window的元素
		me.items = [me.getPricePlanDetailForm()
			,me.getPricePlanDetailTabPanelView()];
		me.fbar = [{
			text : pricing.pricePlan.i18n('i18n.pricingRegion.cancel'),//取消
			handler :function(){
				me.close();
			} 
		},{
			text : pricing.pricePlan.i18n('foss.pricing.reset'),//重置
			margin:'0 50 0 0',
			handler :function(){
				me.getPricePlanDetailForm().getForm().reset();
//				if(Ext.isEmpty(me.pricePlanDetailDto)){
//					me.getPricePlanDetailForm().getForm().loadRecord(new Foss.pricing.pricePlan.PricePlanDetailDtoModel());
//				}else{
//					me.getPricePlanDetailForm().getForm().loadRecord(new Foss.pricing.pricePlan.PricePlanDetailDtoModel(me.pricePlanDetailDto));
//				}
			} 
		},{
			text : pricing.pricePlan.i18n('foss.pricing.addTab'),//增加
			handler :function(){
				me.addTab();
			} 
		},
		'->',{
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
Ext.define('Foss.pricing.pricePlan.UploadPriceform',{
	extend:'Ext.window.Window',
	title:pricing.pricePlan.i18n('foss.pricing.importPriceScheme'),
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
				fieldLabel:pricing.pricePlan.i18n('foss.pricing.pleaseSelectAttachments'),
				labelWidth:100,
				buttonText:pricing.pricePlan.i18n('foss.pricing.browse'),
				flex:3
			}]
		}];
		me.fbar = me.getFbar();
		me.callParent([cfg]);
	},
	getFbar:function(){
		var me = this;
		return [{
			text:pricing.pricePlan.i18n('foss.pricing.import'),
			xtype:'button',
			scope:me,
			handler:me.uploadFile
		},{
			text:pricing.pricePlan.i18n('i18n.pricingRegion.cancel'),
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
				pricing.showInfoMes(pricing.pricePlan.i18n('foss.pricing.allDataImportSuccess'));//全部数据导入成功！
				me.close();
			}else{
				var message = pricing.pricePlan.i18n('foss.pricing.first');//第
				for(var i = 0;i<json.platformVo.numList;i++){
					message = message+json.platformVo.numList[i]+','
				}
				message = message+pricing.pricePlan.i18n('foss.pricing.lineImportSuccess');
				pricing.showWoringMessage(message);
			}
		};
		var failureFn = function(json){
			if(Ext.isEmpty(json)){
				pricing.showErrorMes(pricing.pricePlan.i18n('i18n.pricingRegion.requestTimeOut'));//pricing.pricePlan.i18n('i18n.pricingRegion.requestTimeOut')
			}else{
				pricing.showErrorMes(json.message);
			}
		};
		var form = me.down('form').getForm();
		var url = pricing.realPath('importPrice.action');
		form.submit({
            url: url,
			timeout:1800000,   
            waitMsg: pricing.pricePlan.i18n('foss.pricing.uploadYourAttachment'),
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
 * 价格方案比对WINDOW
 */
Ext.define('Ext.pricing.pricePlan.PricePlanCompareShowWindow',{
	extend : 'Ext.window.Window',
	title: '价格比对',
	closable : true,
	modal : true,
	resizable:false,
	closeAction : 'hide',
	parent:null,
	pricePlanEntity:null,//选择的价格方案实体
	width :900,
	height :650,
	listeners:{
		beforehide:function(me){
			//清楚上次使用的数据
			me.getQueryPricePlanCompareForm().getForm().reset();
			me.getPricePlanCompareShowGridPanel().getStore().removeAll();
			me.pricePlanEntity = null;
		},
		beforeshow:function(me){
			//在window下一次show的时候刷新数据源
			me.getPricePlanCompareShowGridPanel().getStore().load();
		}
	},
    //价格比对方案信息查询-FORM
	queryPricePlanCompareForm:null,
    getQueryPricePlanCompareForm:function(){
    	if(Ext.isEmpty(this.queryPricePlanCompareForm)){
    		this.queryPricePlanCompareForm = Ext.create('Foss.pricing.pricePlan.QueryPricePlanCompareForm');
    	}
    	return this.queryPricePlanCompareForm;
    },
    //价格比对方案信息结果集
    pricePlanCompareShowGridPanel:null,
    getPricePlanCompareShowGridPanel:function(){
      if(Ext.isEmpty(this.pricePlanCompareShowGridPanel)){
        this.pricePlanCompareShowGridPanel = Ext.create('Foss.pricing.pricePlan.PricePlanCompareShowGridPanel');
      }
      return this.pricePlanCompareShowGridPanel;
    },
    constructor : function(config) {
    var me = this, 
      cfg = Ext.apply({}, config);
    me.items = [me.getQueryPricePlanCompareForm(),me.getPricePlanCompareShowGridPanel()];//设置window的元素
    me.callParent([cfg]);
  }
});
/**
 * 价格方案比对查询表单
 */
Ext.define('Foss.pricing.pricePlan.QueryPricePlanCompareForm', {
  extend : 'Ext.form.Panel',
  title: pricing.pricePlan.i18n('i18n.pricingRegion.searchCondition'),
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
		xtype:'datefield',
		fieldLabel:pricing.pricePlan.i18n('foss.pricing.availabilityDate'),//生效日期
		format:'Y-m-d',
		name:'beginTime'
	},{
		xtype:'datefield',
		fieldLabel:pricing.pricePlan.i18n('foss.pricing.deadline'),//截止日期
		format:'Y-m-d',
		name:'endTime'
	},{
      xtype : 'container',
      columnWidth : .2,
      margin : '0 0 0 0',
      items : [{
        xtype : 'button', 
        width:70,
        text : pricing.pricePlan.i18n('i18n.pricingRegion.search'),
        cls:'yellow_button',
        handler : function() {
          if(me.getForm().isValid()){
            var grid = me.up('window').getPricePlanCompareShowGridPanel();
            grid.getStore().load();
          }
          
        }
      }]
    }];
    me.callParent([cfg]);
  }
});
/**
 * 价格方案比对查询结果信息列表
 */
Ext.define('Foss.pricing.pricePlan.PricePlanCompareShowGridPanel', {
  extend: 'Ext.grid.Panel',
  title :pricing.pricePlan.i18n('i18n.pricingRegion.searchResults'),//查询结果
  frame: true,
  height :450,
  sortableColumns:false,
  enableColumnHide:false,
  enableColumnMove:false,
  stripeRows : true, // 交替行效果
  selType : "rowmodel", // 选择类型设置为：行选择
  emptyText: pricing.pricePlan.i18n('foss.pricing.theQueryResultIsEmpty'),//查询结果为空
  
  //查看详情界面
	pricePlanDetailShowWindow:null,
	getPricePlanDetailShowWindow:function(){
		if(Ext.isEmpty(this.pricePlanDetailShowWindow)){
			this.pricePlanDetailShowWindow = Ext.create('Ext.pricing.pricePlan.PricePlanDetailShowWindow');	
			this.pricePlanDetailShowWindow.parent = this;
		}
		return this.pricePlanDetailShowWindow;
	},
	//获取价格方案比对结果窗口
	comparePricePlanResultWindow:null,
	getComparepricePlanResultWindow : function(pricePlanEntityOld,pricePlanEntity){
		if(Ext.isEmpty(this.comparePricePlanResultWindow)){
			this.comparePricePlanResultWindow = Ext.create('Ext.pricing.pricePlan.PricePlanCompareResultShowWindow');
			this.comparePricePlanResultWindow.parent = this;
		}
		this.comparePricePlanResultWindow.pricePlanEntityOld = pricePlanEntityOld;
		this.comparePricePlanResultWindow.pricePlanEntity = pricePlanEntity;
		return this.comparePricePlanResultWindow;
	},
	//价格方案比对
	comparePricePlan:function(){
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
	 	var pricePlanEntityOld = me.up('window').pricePlanEntity;
	 	var pricePlanEntity = selections[0].data;	
 		var window = me.getComparepricePlanResultWindow(pricePlanEntityOld,pricePlanEntity);
 		window.show();
	},
  constructor : function(config) { 
    var me = this, cfg = Ext.apply({}, config);
    me.columns = [{xtype: 'rownumberer',
			width:40,
			text : pricing.pricePlan.i18n('i18n.pricingRegion.num')//序号 
		},{
			text : pricing.pricePlan.i18n('i18n.pricingRegion.opra'),
			align : 'center',
			xtype : 'actioncolumn',
			items: [{
				iconCls: 'deppon_icons_showdetail',
                tooltip: pricing.pricePlan.i18n('foss.pricing.details'), 
                disabled: !pricing.pricePlan.isPermission('pricePlan/pricePlanQueryDetailButton'),
				width:42,
                handler: function(grid, rowIndex, colIndex) {
    				      var record=grid.getStore().getAt(rowIndex);
                	me.getPricePlanDetailShowWindow().show();
                	me.getPricePlanDetailShowWindow().pricePlanId = record.get('id');
                }
			}]
		},{
			width: 140,
			text: pricing.pricePlan.i18n('foss.pricing.scenarioName'), //"方案名称", 
	        dataIndex: 'name'
		},{
			width: 140,
			text: pricing.pricePlan.i18n('foss.pricing.theOriginatingRegion'),//"始发区域",
	        dataIndex: 'priceRegionName'
		},{
			text: pricing.pricePlan.i18n('foss.pricing.updateTime'),//"修改时间",
			width: 140,
	        dataIndex: 'modifyDate',
	        renderer:function(value){
				return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
			}
		},{
			text: pricing.pricePlan.i18n('foss.pricing.modifyUser'),//"修改人",
			width: 140,
	        dataIndex: 'modifyUserName'
		},{
			text: pricing.pricePlan.i18n('foss.pricing.availabilityDate'),//"生效日期",
	        width: 80,
	        dataIndex: 'beginTime',
	        renderer:function(value){
				return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
			}
		},{
			text: pricing.pricePlan.i18n('foss.pricing.endTimeTwo'),//"截止日期",
	        width: 80,
	        dataIndex: 'endTime',
	        renderer:function(value){
				return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
			}
		},{
			width: 80,
			text: pricing.pricePlan.i18n('foss.pricing.dataType'),//"数据状态",
	        sortable: true,
	        dataIndex: 'active',
	        renderer:function(value){
				if(value=='Y'){//'Y'表示激活
					return pricing.pricePlan.i18n('i18n.pricingRegion.active')//"已激活";
				}else if(value=='N'){//'N'表示未激活
					return  pricing.pricePlan.i18n('i18n.pricingRegion.unActive')//"未激活";
				}else{
					return '';
				}
			}
		},{
			text : '是否当前版本',
			dataIndex : 'currentUsedVersion'
		}];
    me.store = Ext.create('Foss.pricing.pricePlan.ComparePricePlanStore',{
      autoLoad : true,
      listeners: {
        beforeload: function(store, operation, eOpts){
          var queryForm =me.up('window').getQueryPricePlanCompareForm();
          var pricePlanEntity = me.up('window').pricePlanEntity;
          if(queryForm!=null){
            Ext.apply(operation,{
              params : {
            	'priceManageMentVo.pricePlanId' :pricePlanEntity.id,//原价格方案id
                'priceManageMentVo.pricePlanEntity.name' :pricePlanEntity.name,//方案名称
                'priceManageMentVo.pricePlanEntity.priceRegionCode'	:pricePlanEntity.priceRegionCode,//始发区域编码
                'priceManageMentVo.pricePlanEntity.beginTime'	:queryForm.getForm().findField('beginTime').getValue(),//生效日期
                'priceManageMentVo.pricePlanEntity.endTime'	:queryForm.getForm().findField('endTime').getValue()//截止日期
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
		text : '比对',//"价格比对按钮",
		disabled: !pricing.pricePlan.isPermission('pricePlan/pricePlanCompareDetailButton'),
		hidden: !pricing.pricePlan.isPermission('pricePlan/pricePlanCompareDetailButton'),
		handler :function(){
			me.comparePricePlan();
		} 
	}];
    me.callParent([cfg]);
  }
});
/**
 * 价格方案比对结果明细window
 */
Ext.define('Ext.pricing.pricePlan.PricePlanCompareResultShowWindow',{
	extend : 'Ext.window.Window',
	title: '价格比对结果',
	closable : true,
	modal : true,
	resizable:false,
	closeAction : 'hide',
	parent:null,
	pricePlanEntityOld:null,//原价格方案
	pricePlanEntity:null,//选择比对的价格方案实体
	width :700,
	height :450,
	listeners:{
		beforehide:function(me){
			//清楚上次使用的数据
			me.getPricePlanCompareResultGridPanel().getStore().removeAll();
			me.pricePlanEntity = null;
		},
		beforeshow:function(me){
			//在window下一次show的时候刷新数据源
			me.getPricePlanCompareResultGridPanel().getStore().load();
		}
	},
    //价格比对结果信息集
	PricePlanCompareResultGridPanel:null,
    getPricePlanCompareResultGridPanel:function(){
      if(Ext.isEmpty(this.PricePlanCompareResultGridPanel)){
        this.PricePlanCompareResultGridPanel = Ext.create('Foss.pricing.pricePlan.PricePlanCompareResultGridPanel');
      }
      return this.PricePlanCompareResultGridPanel;
    },
    constructor : function(config) {
    var me = this, 
      cfg = Ext.apply({}, config);
    me.items = [me.getPricePlanCompareResultGridPanel()];//设置window的元素
    me.callParent([cfg]);
  }
});
/**
 * 价格比对结果明细列表
 */
Ext.define('Foss.pricing.pricePlan.PricePlanCompareResultGridPanel', {
	extend: 'Ext.grid.Panel',
	frame: true,
	height :400,
    sortableColumns:false,
    enableColumnHide:false,
    enableColumnMove:false,
	stripeRows : true, // 交替行效果
	selType : "rowmodel", // 选择类型设置为：行选择
	emptyText: pricing.pricePlan.i18n('foss.pricing.theQueryResultIsEmpty'),//查询结果为空
	

	
	constructor : function(config) { 
		var me = this, cfg = Ext.apply({}, config);
		me.columns = [{xtype: 'rownumberer',
			width:40,
			text : pricing.pricePlan.i18n('i18n.pricingRegion.num')//序号
		},{
			text :pricing.pricePlan.i18n('foss.pricing.destinationStation'),//目的站
			dataIndex : 'arrvRegionName',
			flex:2
		},{
			text :pricing.pricePlan.i18n('foss.pricing.productEntry'),//产品条目
			dataIndex : 'productItemName',
			flex:2
		},{
			text :pricing.pricePlan.i18n('foss.pricing.heavyGoodsPrices'),//重货价格
			dataIndex : 'heavyPrice',
			flex:2
		},{
			text :pricing.pricePlan.i18n('foss.pricing.lightCargoPrices'),//轻货价格
			dataIndex : 'lightPrice',
			flex:2
		},{
			text : pricing.pricePlan.i18n('foss.pricing.theLowestVotes'),//最低一票
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
		}];
		me.store = Ext.create('Foss.pricing.pricePlan.ComparePricePlanResultStore',{
			autoLoad : false,
			//pageSize : 10,
			listeners: {
				beforeload: function(store, operation, eOpts){

					var pricePlanEntityOld = me.up('window').pricePlanEntityOld;
					var pricePlanEntity = me.up('window').pricePlanEntity;
					
					if(pricePlanEntityOld!=null&& pricePlanEntity!=null){
						Ext.apply(operation,{
							params : {
								'priceManageMentVo.pricePlanEntityOld.id' : pricePlanEntityOld.id,//原价格方案ID
								'priceManageMentVo.pricePlanEntity.id' : pricePlanEntity.id//需要比对价格方案ID
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
		me.tbar = [
		{
			text : pricing.pricePlan.i18n('foss.pricing.export'),
			handler :function(){
				
				var pricePlanEntityOld = me.up('window').pricePlanEntityOld;
				var pricePlanEntity = me.up('window').pricePlanEntity;
				var pricePlanCompareResultExport = '';
				
				if(pricePlanEntityOld!=null&& pricePlanEntity!=null){
					pricePlanCompareResultExport = 'priceManageMentVo.pricePlanEntityOld.id='+pricePlanEntityOld.id+'&'+'priceManageMentVo.pricePlanEntity.id='+pricePlanEntity.id;
				}
			    
				var url = pricing.realPath('exportPricePlanCompareResult.action');
				
				if(!Ext.isEmpty(pricePlanCompareResultExport)){
					url = url+'?'+pricePlanCompareResultExport;
				}
				window.location=url;
				pricePlanCompareResultExport = '';
 			} 
		}];
//		me.bbar = me.getPagingToolbar();
		me.callParent([cfg]);
	}
});
/**
 * 开始加载界面
 */
Ext.onReady(function(){
	Ext.QuickTips.init();
	pricing.searchProductItemEntityAddList();//新增中的条目元素
	pricing.searchProductItemEntityList();//查询中的条目元素
	var queryform = Ext.create('Foss.pricing.pricePlan.PricePlanFormPanel');
	var gridPanel =	Ext.create('Foss.pricing.pricePlan.PricePlanGridPanel');
	pricing.queryform = queryform;
	pricing.gridPanel = gridPanel;
	Ext.getCmp('T_pricing-pricePlan').add(Ext.create('Ext.panel.Panel', {
	  	id:'T_pricing-pricePlan_content',
		cls:"panelContentNToolbar",
		bodyCls:'panelContent-body',
		//获得查询FORM
		getQueryPricePlanForm : function() {
			return queryform;
		},
		//获得查询结果GRID
		getPricePlanGridPanel : function() {
			if(Ext.isEmpty(this.gridPanel)){
				this.gridPanel = Ext.create('Foss.pricing.pricePlan.PricePlanGridPanel');//查询结果GRID
			}
			return gridPanel;
		},
		items : [queryform,gridPanel]
	}));
});

