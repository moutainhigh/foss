//转换long类型为日期(在model中会用到)
pricing.valueAddDiscount.changeLongToDate = function(value) {
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
pricing.valueAddDiscount.productEntityList = [];//基础产品列表
pricing.valueAddDiscount.goodTypeList = [];//货物类型列表
pricing.valueAddDiscount.caculateType = [{'valueCode':'WEIGHT','valueName':pricing.valueAddDiscount.i18n('foss.pricing.weight')}//重量
,{'valueCode':'VOLUME','valueName':pricing.valueAddDiscount.i18n('foss.pricing.volume')}//体积
,{'valueCode':'ORIGINALCOST','valueName':pricing.valueAddDiscount.i18n('foss.pricing.directCosts')}]
pricing.valueAddDiscount.channel = 'PKP_PRICE_CHANNEL';//渠道
pricing.valueAddDiscount.valueAddDiscountDetailTypeDept = 'DISCOUNT_DEPT';//折扣明细类型——部门
pricing.valueAddDiscount.valueAddDiscountDetailTypeRegion = 'DISCOUNT_REGION';//折扣明细类型——区域
pricing.valueAddDiscount.valueAddDiscountDetailTypeCity = 'DISCOUNT_CITY';//折扣明细类型——城市
pricing.valueAddDiscount.valueAddDiscountDetailTypeAllNet = 'ALLNET';//全网
pricing.valueAddDiscount.industry = 'PKP_PRICE_INDUSTRY';//所属行业
pricing.valueAddDiscount.priceDiscountArrive = 'ARRIVE';//到达
pricing.valueAddDiscount.priceDiscountStart = 'START';//出发
pricing.valueAddDiscount.tomorrowTime = null;
//价格区域
pricing.valueAddDiscount.PRICING_REGION = 'PRICING_REGION';
//城市
pricing.valueAddDiscount.Pricing_City = 'CITY';
pricing.valueAddDiscount.allNet = 'ALLNET';//全网常量
//--------------------------------------pricing----------------------------------------
//查询货物类型列表
pricing.searchGoodTypeList = function(){
	Ext.Ajax.request({
		url:pricing.realPath('findGoodsTypeByCondiction.action'),//查询基础产品
		async:false,
		success:function(response){
			var result = Ext.decode(response.responseText);
			pricing.valueAddDiscount.goodTypeList = result.pricingValuationVo.goodsTypeEntityList;
		},
		failure:function(response){
			var result = Ext.decode(response.responseText);
			if(Ext.isEmpty(result)){
				pricing.showErrorMes(pricing.valueAddDiscount.i18n('i18n.pricingRegion.requestTimeOut'));
			}else{
				pricing.showErrorMes(result.message);
			}
		},
		exception:function(response){
			var result = Ext.decode(response.responseText);
			if(Ext.isEmpty(result)){
				pricing.showErrorMes(pricing.valueAddDiscount.i18n('i18n.pricingRegion.requestTimeOut'));
			}else{
				pricing.showErrorMes(result.message);
			}
		}
	});
};
//查询基础产品列表
pricing.searchProductEntityList = function(){
	Ext.Ajax.request({
		url:pricing.realPath('findProductByCondition.action'),//查询基础产品
		async:false,
		success:function(response){
			var result = Ext.decode(response.responseText);
			pricing.valueAddDiscount.productEntityList = result.pricingValuationVo.productEntityList;
		},
		failure:function(response){
			var result = Ext.decode(response.responseText);
			if(Ext.isEmpty(result)){
				pricing.showErrorMes(pricing.valueAddDiscount.i18n('i18n.pricingRegion.requestTimeOut'));
			}else{
				pricing.showErrorMes(result.message);
			}
		},
		exception:function(response){
			var result = Ext.decode(response.responseText);
			if(Ext.isEmpty(result)){
				pricing.showErrorMes(pricing.valueAddDiscount.i18n('i18n.pricingRegion.requestTimeOut'));
			}else{
				pricing.showErrorMes(result.message);
			}
		}
	});
};
//获取服务当前时间
pricing.haveServerNowTime = function(){
	Ext.Ajax.request({
		url:pricing.realPath('haveServerNowTime.action'),
		async:false,
		success:function(response){
			var result = Ext.decode(response.responseText);
			var today = new Date(result.pricingValuationVo.nowTime);//获取服务当前时间
			pricing.valueAddDiscount.tomorrowTime = today.setDate(today.getDate()+1);
		},
		failure:function(response){
			var result = Ext.decode(response.responseText);
			if(Ext.isEmpty(result)){
				pricing.showErrorMes(pricing.valueAddDiscount.i18n('i18n.pricingRegion.requestTimeOut'));
			}else{
				pricing.showErrorMes(result.message);
			}
		},
		exception:function(response){
			var result = Ext.decode(response.responseText);
			failFn(result);
		}
	});
};

//价格折扣明细dto
Ext.define('Foss.pricing.valueAddDiscount.ValueAddDiscountDto', {
    extend: 'Ext.data.Model',
    fields : [{
        name : 'marketId',// 市场ID
        type : 'string'
    },{
        name : 'marketCode',// 市场CODE
        type : 'string'
    },{
        name : 'marketName',// 市场NAME
        type : 'string'
    },{
        name : 'marketType',//市场活动类型
        type : 'string'
    },{
        name : 'beginDate',// 增值服务折扣方案开始时间
        type : 'date',
        defaultValue : null,
        convert : pricing.valueAddDiscount.changeLongToDate
    },{
        name : 'endDate',// 增值服务折扣方案结束时间
        type : 'date',
        defaultValue : null,
        convert : pricing.valueAddDiscount.changeLongToDate
    },{
        name : 'businessDate',// 业务日期
        type : 'date',
        defaultValue : null,
        convert : pricing.valueAddDiscount.changeLongToDate
    },{
        name : 'programDesc',// 方案描述
        type : 'string'
    },{
        name : 'active',// 数据状态
        type : 'string'
    },{
        name : 'createUserCode',//创建人CODE
        type : 'string'
    },{
        name : 'createOrgCode',//创建组织CODE
        type : 'string'
    },{
        name : 'createTime',//创建时间
        type : 'date',
        defaultValue : null,
        convert : pricing.valueAddDiscount.changeLongToDate
    },{
        name : 'createUserName',//创建人姓名
        type : 'string'
    },{
        name : 'modifyTime',//修改时间
        type : 'date',
        defaultValue : null,
        convert : pricing.valueAddDiscount.changeLongToDate
    },{
        name : 'marketChannelId',//市场活动适用渠道ID
        type : 'string'
    },{
        name : 'saleChannelId',// 方案渠道ID
        type : 'string'
    },{
        name : 'saleChannelCode',// 方案渠道CODE
        type : 'string'
    },{
        name : 'saleChannelName',// 方案渠道NAME
        type : 'string'
    },{
        name : 'discountOrgId',//折扣适用起始目的组织网点ID
        type : 'string'
    },{
        name : 'deptOrgId',// 出发区域ID
        type : 'string'
    },{
        name : 'deptOrgCode',// 出发区域CODE
        type : 'string'
    },{
        name : 'deptOrgName',// 出发区域NAME
        type : 'string'
    },{
        name : 'arrvOrgId',// 目的区域ID
        type : 'string'
    },{
        name : 'arrvOrgCode',// 目的区域CODE
        type : 'string'
    },{
        name : 'arrvOrgName',// 目的区域NAME
        type : 'string'
    },{
        name : 'deptOrgTypeCode',// 出发组织类型CODE
        type : 'string'
    },{
        name : 'deptOrgTypeName',// 出发组织类型NAME
        type : 'string'
    },{
        name : 'arrvOrgTypeCode',// 到达组织类型CODE
        type : 'string'
    },{
        name : 'arrvOrgTypeName',//到达组织类型NAME
        type : 'string'
    },{
        name : 'priceValuationId',//计费规则ID
        type : 'string'
    },{
        name : 'productId',// 产品ID
        type : 'string'
    },{
        name : 'productCode',// 产品CODE
        type : 'string'
    },{
        name : 'productName',// 产品条目名称
        type : 'string'
    },{
        name : 'goodsTypeId',// 货物类型ID
        type : 'string'
    },{
        name : 'goodsTypeCode',// 货物类型CODE
        type : 'string'
    },{
        name : 'googsTypeName',// 货物类型NAME
        type : 'string'
    },{
        name : 'priceCriteriaId',//计价方式ID
        type : 'string'
    },{
    	defaultValue : null,
        name : 'leftRange'//价格左区间
    },{
    	defaultValue : null,
        name : 'rightRange'//价格右区间
    },{
    	defaultValue : null,
        name : 'discountRate'//折扣率
    },{
        name : 'caculateType',//折扣规则
        type : 'string'
    },{
    	defaultValue : null,// 最低一票
        name : 'minFee'
    },{
    	defaultValue : null,// 最高一票
        name : 'maxFee'
    },{
    	type : 'string',// 所属行业ID
        name : 'pricingIndustryId'
    },{
    	type : 'string',// 所属行业CODE
        name : 'pricingIndustryCode'
    },{
    	type : 'string',// 计价条目ID
        name : 'priceEntryId'
    },{
    	type : 'string',// 计价条目CODE
        name : 'priceEntryCode'
    }]
});
//基础产品明细MODEL
Ext.define('Foss.pricing.valueAddDiscount.ProductEntity', {
    extend: 'Ext.data.Model',
    fields : [{
        name : 'id',//id
        type : 'string'
    },{
        name : 'code',//code
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
    },{
        name : 'transportType',//运输类型
        type : 'string'
    },{
        name : 'levels'//产品等级
    },{
        name : 'parentCode',//父产品CODE
        type : 'string'
    },{
        name : 'refId',
        type : 'string'
    },{
        name : 'shortName',//简称
        type : 'string'
    },{
        name : 'priority',
        type : 'string'
    }]
});
//货物类型MODEL
Ext.define('Foss.pricing.valueAddDiscount.GoodsTypeEntity', {
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
//增值服务折扣方案主信息
Ext.define('Foss.pricing.valueAddDiscount.MarketingEventEntity', {
    extend: 'Ext.data.Model',
    fields : [{
        name : 'id',
        type : 'string'
    },{
        name : 'createDate',//创建时间
        type : 'date',
        defaultValue : null,
        convert : pricing.valueAddDiscount.changeLongToDate
    },{
        name : 'createUser',//创建人工号
        type : 'string'
    },{
        name : 'modifyDate',//修改时间
        type : 'date',
        defaultValue : null,
        convert : pricing.valueAddDiscount.changeLongToDate
    },{
        name : 'modifyUser',//修改人工号
        type : 'string'
    },{
        name : 'code',//code
        type : 'string'
    },{
        name : 'name',//名称
        type : 'string'
    },{
        name : 'beginTime',//开始日期
        type : 'date',
        defaultValue : null,
        convert : pricing.valueAddDiscount.changeLongToDate
    },{
        name : 'endTime',//截止日期
        type : 'date',
        defaultValue : null,
        convert : pricing.valueAddDiscount.changeLongToDate
    },{
//        name : 'description',//备注
    	name : 'remark',//备注
        type : 'string'
    },{
        name : 'createTime',//创建时间
        type : 'date',
        defaultValue : null,
        convert : pricing.valueAddDiscount.changeLongToDate
    },{
        name : 'createOrgCode',//创建
        type : 'string'
    },{
        name : 'modifyTime',//修改时间
        type : 'date',
        defaultValue : null,
        convert : pricing.valueAddDiscount.changeLongToDate
    },{
        name : 'modifyOrgCode',//修改人所在部门
        type : 'string'
    },{
        name : 'priceRegionCode',//价格区域CODE
        type : 'string'
    },{
        name : 'priceRegionId',//价格区域ID
        type : 'string'
    },{
        name : 'active',//状态
        type : 'string'
    },{
        name : 'type',//类型
        type : 'string'
    },{
        name : 'pricingEntryName',//计价条目NAME
        type : 'string'
    },{
        name : 'pricingEntryCode',//计价条目CODE
        type : 'string'
    },{
        name : 'pricingEntryId',//计价条目ID 
        type : 'string'
    },{
        name : 'createUserName',//创建人姓名
        type : 'string'
    }]
});
//计价条目MODEL-----
Ext.define('Foss.pricing.valueAddDiscount.PriceEntityModel', {
    extend: 'Ext.data.Model',
    fields : [{
        name : 'id',
        type : 'string'
    },{
        name : 'createDate',//创建时间
        type : 'date',
        defaultValue : null,
        convert : pricing.valueAddDiscount.changeLongToDate
    },{
        name : 'createUser',//创建人
        type : 'string'
    },{
        name : 'modifyDate',//修改时间
        type : 'date',
        defaultValue : null,
        convert :pricing.valueAddDiscount.changeLongToDate
    },{
        name : 'modifyUser',//修改人
        type : 'string'
    },{
        name : 'name',//name
        type : 'string'
    },{
        name : 'code',//编码
        type : 'string'
    },{
        name : 'active',//是否激活
        type : 'string'
    },{
        name : 'refId',//父节点ID
        type : 'string'
    },{
        name : 'refCode',//父节点CODE
        type : 'string'
    },{
        name : 'description',//描述
        type : 'string'
    },{
        name : 'beginTime',//有效起始时间
        type : 'date',
        defaultValue : null,
        convert : pricing.valueAddDiscount.changeLongToDate
    },{
        name : 'endTime',//有效截止时间
        type : 'date',
        defaultValue : null,
        convert : pricing.valueAddDiscount.changeLongToDate
    },{
        name : 'createOrgCode',//创建人部门CODE
        type : 'string'
    },{
        name : 'modifyOrgCode',//修改人部门CODE
        type : 'string'
    },{
        name : 'blongPricingId',//归集类型ID
        type : 'string'
    },{
        name : 'blongPricingCode',//归集类型CODE
        type : 'string'
    },{
        name : 'blongPricingName',//归集类型名称
        type : 'string'
    }]
});
/**
 * 区域MODEL
 */
Ext.define('Foss.pricing.valueAddDiscount.AreaModel', {
	extend : 'Ext.data.Model',
	fields : [ {
		name : 'id',
		type : 'string'
	},{
		name : 'regionCode',// 区域编码
		type : 'string'
	},  {
		name : 'regionName',// 区域名称
		type : 'string'
	}, {
		name : 'regionType',// 区域类型
		type : 'string'
	}, {
		name : 'regionNature',// 区域性质
		type : 'string'
	}, {
		name : 'active',// 区域状态
		type : 'string'
	}, {
		name : 'beginTime',// 开始时间
		type: 'Date',
		defaultValue : null,
		 convert:pricing.valueAddDiscount.changeLongToDate
	}, {
		name : 'endTime',// 结束时间
		type: 'Date',
		defaultValue : null,
		 convert:pricing.valueAddDiscount.changeLongToDate
	}, {
		name : 'description',// 描述
		type : 'string'
	}, {
		name : 'nationCode',// 国家编号
		type : 'string'
	},{
		name : 'nationName',// 国家名称
		type : 'string'
	}, {
		name : 'proCode',// 省份编号
		type : 'string'
	},{
		name : 'proName',// 省份编号
		type : 'string'
	}, {
		name : 'cityCode',// 市编号
		type : 'string'
	},{
		name : 'cityName',// 市名称
		type : 'string'
	}, {
		name : 'countyCode',// 区县编号
		type : 'string'
	},{
		name : 'countyName',// 区县名称
		type : 'string'
	}, {
		name : 'modifyUserName',//修改人姓名
		type : 'string'
	}, {
		name : 'createUserName',//创建人姓名
		type : 'string'
	}, {
		name : 'createDate',//创建时间
		type: 'Date',
		defaultValue : null,
		 convert:pricing.valueAddDiscount.changeLongToDate
	}, {
		name : 'modifyDate',//修改时间
		type: 'Date',
		defaultValue : null,
		 convert:pricing.valueAddDiscount.changeLongToDate
	}, {
		name : 'createUser',//创建人
		type : 'string'
	}, {
		name : 'modifyUser',//修改人
		type : 'string'
	}]
});
//------------------------------------model---------------------------------------------------
/**
 * 增值服务折扣方案Store
 */
Ext.define('Foss.pricing.valueAddDiscount.valueAddDiscountDetailStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.pricing.valueAddDiscount.ValueAddDiscountDto',//增值服务折扣方案明细MODEL
	pageSize:5,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url : pricing.realPath('selectValueAddDiscountByCodition.action'),
		reader : {
			type : 'json',
			root : 'priceDiscountVo.priceDiscountDtoList',
			totalProperty : 'totalCount'
		}
	}
});
/**
 * 增值服务折扣方案Store
 */
Ext.define('Foss.pricing.valueAddDiscount.valueAddDiscountStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.pricing.valueAddDiscount.MarketingEventEntity',//增值服务折扣方案MODEL
	pageSize:20,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url : pricing.realPath('selectValueAddProgramByCodition.action'),
		reader : {
			type : 'json',
			root : 'priceDiscountVo.marketingEventEntityList',
			totalProperty : 'totalCount'
		}
	}
});
//----------------------------------------store---------------------------------
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
 * 增值服务折扣方案查询表单
 */
Ext.define('Foss.pricing.valueAddDiscount.QueryValueAddDiscountForm', {
	extend : 'Ext.form.Panel',
	title: pricing.valueAddDiscount.i18n('i18n.pricingRegion.searchCondition'),//查询条件
	frame: true,
	collapsible: true,
    defaults : {
    	columnWidth : .3,
    	margin : '8 10 5 10',
    	anchor : '100%'
    },
    height :140,
	defaultType : 'textfield',
	layout:'column',
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.items  = [{
			name: 'name',
			labelWidth:120,
		    fieldLabel:pricing.valueAddDiscount.i18n('foss.pricing.valueVddedOffers'),//增值服务折扣方案
	        xtype : 'textfield'
		},{
			name: 'active',
			queryMode: 'local',
		    displayField: 'valueName',
		    valueField: 'valueCode',
		    editable:false,
		    value:'ALL',
		    store:pricing.getStore(null,null,['valueCode','valueName']
		    ,[{'valueCode':'Y','valueName':pricing.valueAddDiscount.i18n('i18n.pricingRegion.active')},{'valueCode':'N','valueName':pricing.valueAddDiscount.i18n('i18n.pricingRegion.unActive')},{'valueCode':'ALL','valueName':pricing.valueAddDiscount.i18n('i18n.pricingRegion.all')}]),
	        fieldLabel: pricing.valueAddDiscount.i18n('foss.pricing.preferentialStatus'),//折扣状态
	        xtype : 'combo'
		},{
			name: 'businessDate',
			format:'Y-m-d',
		    fieldLabel:pricing.valueAddDiscount.i18n('foss.pricing.businessDate'),//目的站
	        xtype : 'datefield'
		}];
		me.fbar = [{
			xtype : 'button', 
			width:70,
			text : pricing.valueAddDiscount.i18n('foss.pricing.reset'),//重置
			handler : function() {
				me.getForm().reset();
			}
		},{
				xtype : 'button', 
				width:70,
				cls:'yellow_button',
				margin:'0 0 0 820',
				text : pricing.valueAddDiscount.i18n('i18n.pricingRegion.search'),
				disabled: !pricing.valueAddDiscount.isPermission('valueAddDiscount/valueAddDiscountQuerybutton'),
				hidden: !pricing.valueAddDiscount.isPermission('valueAddDiscount/valueAddDiscountQuerybutton'),
				handler : function() {
					if(me.getForm().isValid()){
						var grid = Ext.getCmp('T_pricing-valueAddDiscount_content').getAreaGrid();//获取大查询GRID
						grid.getStore().load();
					}
				}
			}]
		me.callParent([cfg]);
	}
});
/**
 * 增值服务折扣方案列表
 */
Ext.define('Foss.pricing.valueAddDiscount.ValueAddDiscountGridPanel', {
	extend: 'Ext.grid.Panel',
	title : pricing.valueAddDiscount.i18n('i18n.pricingRegion.searchResults'),//查询结果
	frame: true,
	cls: 'autoHeight',
	bodyCls: 'autoHeight', 
    sortableColumns:false,
    enableColumnHide:false,
    enableColumnMove:false,
	stripeRows : true, // 交替行效果
	selType : "rowmodel", // 选择类型设置为：行选择
	emptyText: pricing.valueAddDiscount.i18n('foss.pricing.theQueryResultIsEmpty'),//查询结果为空
	//得到bbar（分页）
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
	//激活增值服务折扣方案
	activePricingDiscount:function(){
		var me = this;
		var priceDiscountIds = new Array();
		//获取选中的数据
		var selections = me.getSelectionModel().getSelection();
		if(selections.length<1){
			pricing.showErrorMes(pricing.valueAddDiscount.i18n('foss.pricing.toActivateSelectValueAddedPromotions'));//请选择要激活的增值优惠！
			return;
		}
		for(var i = 0 ; i<selections.length ; i++){
			if(selections[i].get('active')=='Y'){//只有未激活的增值优惠才可以删除
				pricing.showErrorMes(pricing.valueAddDiscount.i18n('foss.pricing.pleaseChooseNotToActivateTheValueAddedPromotions'));//请选择未激活增值优惠！
				return;
			}else if(selections[i].get('active')=='N'){
				priceDiscountIds.push(selections[i].get('id'));
			}else{
				priceDiscountIds.push(selections[i].get('id'));
			}
		}
		if(priceDiscountIds.length<1){
			pricing.showErrorMes(pricing.valueAddDiscount.i18n('foss.pricing.pleaseSelectAtLeastOneInactiveValueAddedPromotions'));//请至少选择一条未激活的增值优惠！
			return;
		}
		pricing.showQuestionMes(pricing.valueAddDiscount.i18n('foss.pricing.doYouWantToActivateTheInactiveValueAddedPromotions'),function(e){//是否要激活这些未激活的增值优惠？
			if(e=='yes'){//询问是否激活，是则发送请求
				var params = {'priceDiscountVo':{'priceDiscountIds':priceDiscountIds}};
				var successFun = function(json){
					pricing.showInfoMes(json.message);
					me.getPagingToolbar().moveFirst();
				};
				var failureFun = function(json){
					if(Ext.isEmpty(json)){
						pricing.showErrorMes(pricing.valueAddDiscount.i18n('i18n.pricingRegion.requestTimeOut'));
					}else{
						pricing.showErrorMes(json.message);
					}
				};
				var url = pricing.realPath('activeValueAddDiscount.action');
				pricing.requestJsonAjax(url,params,successFun,failureFun);
			}
		});
	},
	//删除增值优惠
	deletePricingDiscount:function(){
		var me = this;
		var priceDiscountIds = new Array();
		//获取选中的数据
		var selections = me.getSelectionModel().getSelection();
		if(selections.length<1){
			pricing.showErrorMes(pricing.valueAddDiscount.i18n('foss.pricing.pleaseChooseToDeleteIsNotActiveValueAddedOffers'));//请选择要删除的未激活增值优惠！
			return;
		}
		for(var i = 0 ; i<selections.length ; i++){
			if(selections[i].get('active')=='Y'){//只有未激活的增值优惠才可以删除
				pricing.showErrorMes(pricing.valueAddDiscount.i18n('foss.pricing.pleaseChooseNotToActivateTheValueAddedPromotions'));//请选择要删除的未激活增值优惠！
				return;
			}else if(selections[i].get('active')=='N'){
				priceDiscountIds.push(selections[i].get('id'));
			}else{
				priceDiscountIds.push(selections[i].get('id'));
			}
		}
		if(priceDiscountIds.length<1){
			pricing.showErrorMes(pricing.valueAddDiscount.i18n('foss.pricing.pleaseSelectAtLeastOneInactiveValueAddedPromotions'));//请至少选择一条未激活的增值优惠！
			return;
		}
		pricing.showQuestionMes(pricing.valueAddDiscount.i18n('foss.pricing.doYouWantDeleteInactiveValueAddedPromotions'),function(e){//是否要删除这些未激活的增值优惠？
			if(e=='yes'){//询问是否删除，是则发送请求
				var params = {'priceDiscountVo':{'priceDiscountIds':priceDiscountIds}};
				var successFun = function(json){
					pricing.showInfoMes(json.message);
					me.getPagingToolbar().moveFirst();
				};
				var failureFun = function(json){
					if(Ext.isEmpty(json)){
						pricing.showErrorMes(pricing.valueAddDiscount.i18n('i18n.pricingRegion.requestTimeOut'));
					}else{
						pricing.showErrorMes(json.message);
					}
				};
				var url = pricing.realPath('deleteValueAddDiscount.action');
				pricing.requestJsonAjax(url,params,successFun,failureFun);
			}
		});
		
	},
	//折扣主信息新增Window
	valueAddDiscountAddWindow : null,
	getValueAddDiscountAddWindow : function() {
		if (Ext.isEmpty(this.valueAddDiscountAddWindow)) {
			this.valueAddDiscountAddWindow = Ext.create('Foss.pricing.valueAddDiscount.ValueAddDiscountAddWindow');
			this.valueAddDiscountAddWindow.parent = this;
		}
		return this.valueAddDiscountAddWindow;
	},
	//折扣主信息修改Window
	valueAddDiscountUpdateWindow : null,
	getValueAddDiscountUpdateWindow : function() {
		if (Ext.isEmpty(this.valueAddDiscountUpdateWindow)) {
			this.valueAddDiscountUpdateWindow = Ext.create('Foss.pricing.valueAddDiscount.ValueAddDiscountUpdateWindow');
			this.valueAddDiscountUpdateWindow.parent = this;
		}
		return this.valueAddDiscountUpdateWindow;
	},
	//折扣主明细新增Window
	valueAddDiscountDeatilAddWindow : null,
	getValueAddDiscountDeatilAddWindow : function() {
		if (Ext.isEmpty(this.valueAddDiscountDeatilAddWindow)) {
			this.valueAddDiscountDeatilAddWindow = Ext.create('Foss.pricing.valueAddDiscount.ValueAddDiscountDeatilAddWindow');
			this.valueAddDiscountDeatilAddWindow.parent = this;
		}
		return this.valueAddDiscountDeatilAddWindow;
	},
	//折扣主明细修改Window
	valueAddDiscountDeatilUpdateWindow : null,
	getValueAddDiscountDeatilUpdateWindow : function() {
		if (Ext.isEmpty(this.valueAddDiscountDeatilUpdateWindow)) {
			this.valueAddDiscountDeatilUpdateWindow = Ext.create('Foss.pricing.valueAddDiscount.ValueAddDiscountDeatilAddWindow');
			this.valueAddDiscountDeatilUpdateWindow.parent = this;
		}
		return this.valueAddDiscountDeatilUpdateWindow;
	},
	//终止，设置截止日期
	valueAddDiscountEndTimeWindow:null,
	getValueAddDiscountEndTimeWindow : function(selection) {
		if (Ext.isEmpty(this.valueAddDiscountEndTimeWindow)) {
			this.valueAddDiscountEndTimeWindow = Ext.create('Foss.pricing.valueAddDiscount.ValueAddDiscountEndTimeWindow',{
				selection:selection
			});
			this.valueAddDiscountEndTimeWindow.parent = this;
		}
		return this.valueAddDiscountEndTimeWindow;
	},
	//查看详情window
	valueAddDiscountDeatilShowWindow:null,
	getValueAddDiscountDeatilShowWindow : function() {
		if (Ext.isEmpty(this.valueAddDiscountDeatilShowWindow)) {
			this.valueAddDiscountDeatilShowWindow = Ext.create('Foss.pricing.valueAddDiscount.ValueAddDiscountDeatilShowWindow');
			this.valueAddDiscountDeatilShowWindow.parent = this;
		}
		this.valueAddDiscountDeatilShowWindow.valueAddDiscountAddTab.items.items[0].items.items[4].setDisabled(true);
		return this.valueAddDiscountDeatilShowWindow;
	},
	/**
	 * 
     * 立即生效
     * 
     * 对于实际业务可能在当天发生两次以上的调整，故给予立即激中止功能用于支持该业务，
     * 
     * 1、立即中止功能给予特定角色来操作。根据所登陆的用户所属某某角色来决定是否给予立即中止功能。防止一般用户进行当天频繁调价操作
     * 
     * 2、立即中止功能中止日期可以等于今天但是不能小于今天。
     * 
     */
    immediatelyActiveWindow:null,
	getImmediatelyActiveWindow: function(){
		if(Ext.isEmpty(this.immediatelyActiveWindow)){
			this.immediatelyActiveWindow = Ext.create('Foss.pricing.valueAddDiscount.ImmediatelyActiveTimeWindow');
			this.immediatelyActiveWindow.parent = this;
		}
		return this.immediatelyActiveWindow;
	},
	/**
     * 立即中止
     * 对于实际业务可能在当天发生两次以上的调整，故给予立即激中止功能用于支持该业务，
     * 1、立即中止功能给予特定角色来操作。根据所登陆的用户所属某某角色来决定是否给予立即中止功能。防止一般用户进行当天频繁调价操作
     * 2、立即中止功能中止日期可以等于今天但是不能小于今天。
     * 
     */
    immediatelyStopWindow:null,
	getImmediatelyStopWindow: function(){
		if(Ext.isEmpty(this.immediatelyStopWindow)){
			this.immediatelyStopWindow = Ext.create('Foss.pricing.valueAddDiscount.ImmediatelyStopEndTimeWindow');
			this.immediatelyStopWindow.parent = this;
		}
		return this.immediatelyStopWindow;
	},
	/**
	 * 立即中止
	 */
    immediatelyStop:function(){
    	var me = this;
	 	var selections = me.getSelectionModel().getSelection();
	 	if(selections.length < 1){
	 		pricing.showWoringMessage(pricing.valueAddDiscount.i18n('foss.pricing.selectOneRecordToOp'));
			return;
	 	}
	 	if(selections.length > 1){
	 		pricing.showWoringMessage(pricing.valueAddDiscount.i18n('foss.pricing.selectOneRecordToOp'));
			return;
	 	}
	 	if(selections[0].get('active')!='Y'){
	 		pricing.showWoringMessage(pricing.valueAddDiscount.i18n('foss.pricing.selectOneActiveRecordToOp'));
	 		return;
	 	}else{
	 		var marketingEventEntity = selections[0].data;
	 		me.getImmediatelyStopWindow().marketingEventEntity = marketingEventEntity;
	 		me.getImmediatelyStopWindow().show();
	 	}
	},
	/**
	 * 立即激活
	 */
    immediatelyActive:function(){
    	var me = this;
	 	var selections = me.getSelectionModel().getSelection();
	 	if(selections.length < 1){
	 		pricing.showWoringMessage(pricing.valueAddDiscount.i18n('foss.pricing.selectOneRecordToOp'));
			return;
	 	}
	 	if(selections.length > 1){
	 		pricing.showWoringMessage(pricing.valueAddDiscount.i18n('foss.pricing.selectOneRecordToOp'));
			return;
	 	}
	 	if(selections[0].get('active')=='Y'){
	 		pricing.showWoringMessage(pricing.valueAddDiscount.i18n('foss.pricing.selectOneUnActiveRecordToOp'));
	 		return;
	 	}else{
	 		var marketingEventEntity = selections[0].data;
	 		me.getImmediatelyActiveWindow().marketingEventEntity = marketingEventEntity;
	 		me.getImmediatelyActiveWindow().show();
	 	}
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.columns = [{xtype: 'rownumberer',
			width:40,
			text : pricing.valueAddDiscount.i18n('i18n.pricingRegion.num')//序号
		},{
			align : 'center',
			xtype : 'actioncolumn',
			text : pricing.valueAddDiscount.i18n('i18n.pricingRegion.opra'),//操作
			items: [{
				iconCls: 'deppon_icons_edit',
                tooltip: pricing.valueAddDiscount.i18n('foss.pricing.update'),//修改
                disabled: !pricing.valueAddDiscount.isPermission('valueAddDiscount/valueAddDiscountUpdatebutton'),
				width:42,
				getClass : function (v, m, r, rowIndex) {
					if (r.get('active') === 'N') {
					    return 'deppon_icons_edit';
					} else {
					    return 'statementBill_hide';
					}
				},
                handler: function(grid, rowIndex, colIndex) {
                	var record = grid.getStore().getAt(rowIndex);
                	var params = {'priceDiscountVo':{'marketingEventEntity':{'id':record.get('id')}}};
    				var successFun = function(json){
    					var marketingEventEntity = json.priceDiscountVo.marketingEventEntity;
    					var channelEntityList = json.priceDiscountVo.channelEntityList;
                        grid.up().getValueAddDiscountUpdateWindow().marketingEventEntity = marketingEventEntity;//设置增值优惠主信息
                        grid.up().getValueAddDiscountUpdateWindow().channelEntityList = channelEntityList;//渠道信息
                        grid.up().getValueAddDiscountUpdateWindow().show();//显示window
    				};
    				var failureFun = function(json){
    					if(Ext.isEmpty(json)){
    						pricing.showErrorMes(pricing.valueAddDiscount.i18n('i18n.pricingRegion.requestTimeOut'));
    					}else{
    						pricing.showErrorMes(json.message);
    					}
    				};
    				var url = pricing.realPath('selectValueAddMarketingByPrimaryKey.action');
    				pricing.requestJsonAjax(url,params,successFun,failureFun);
                }
			},{
				iconCls: 'deppon_icons_softwareUpgrade',
                tooltip: pricing.valueAddDiscount.i18n('foss.pricing.upgradedVersion'),//升级版本
                disabled: !pricing.valueAddDiscount.isPermission('valueAddDiscount/valueAddDiscountUpgradebutton'),
				width:42,
				getClass : function (v, m, r, rowIndex) {
					if (r.get('active') === 'Y') {
					    return 'deppon_icons_softwareUpgrade';
					} else {
					    return 'statementBill_hide';
					}
				},
                handler: function(grid, rowIndex, colIndex) {
                	var record = grid.getStore().getAt(rowIndex);
                	var params = {'priceDiscountVo':{'marketingEventEntity':{'id':record.get('id')}}};
    				var successFun = function(json){
    					pricing.showInfoMes(json.message);
    					grid.up().getPagingToolbar().moveFirst();
//    					var marketingEventEntity = json.priceDiscountVo.marketingEventEntity;
//    					var channelEntityList = json.priceDiscountVo.channelEntityList;
//                        grid.up().getValueAddDiscountUpdateWindow().marketingEventEntity = marketingEventEntity;//设置增值优惠主信息
//                        grid.up().getValueAddDiscountUpdateWindow().channelEntityList = channelEntityList;//渠道信息
//                        grid.up().getValueAddDiscountUpdateWindow().show();//显示window
    				};
    				var failureFun = function(json){
    					if(Ext.isEmpty(json)){
    						pricing.showErrorMes(pricing.valueAddDiscount.i18n('i18n.pricingRegion.requestTimeOut'));
    					}else{
    						pricing.showErrorMes(json.message);
    					}
    				};
    				var url = pricing.realPath('copyValueAddDiscountProgram.action');
    				pricing.requestJsonAjax(url,params,successFun,failureFun);
                }
			},{
				iconCls: 'deppon_icons_showdetail',
                tooltip: pricing.valueAddDiscount.i18n('foss.pricing.details'),//查看详情
                disabled: !pricing.valueAddDiscount.isPermission('valueAddDiscount/valueAddDiscountDetailbutton'),
				width:42,
                handler: function(grid, rowIndex, colIndex) {
                	var record = grid.getStore().getAt(rowIndex);
                	var params = {'priceDiscountVo':{'marketingEventEntity':{'id':record.get('id')}}};
    				var successFun = function(json){
    					var marketingEventEntity = json.priceDiscountVo.marketingEventEntity;
    					var channelEntityList = json.priceDiscountVo.channelEntityList;
                        grid.up().getValueAddDiscountDeatilShowWindow().marketingEventEntity = marketingEventEntity;//设置增值优惠主信息
                        grid.up().getValueAddDiscountDeatilShowWindow().channelEntityList = channelEntityList;//渠道信息
                        grid.up().getValueAddDiscountDeatilShowWindow().show();//显示window
    				};                                                                                                                    
    				var failureFun = function(json){
    					if(Ext.isEmpty(json)){
    						pricing.showErrorMes(pricing.valueAddDiscount.i18n('i18n.pricingRegion.requestTimeOut'));
    					}else{
    						pricing.showErrorMes(json.message);
    					}
    				};
    				var url = pricing.realPath('selectValueAddMarketingByPrimaryKey.action');
    				pricing.requestJsonAjax(url,params,successFun,failureFun);
                }
			}]
		},{
			text : pricing.valueAddDiscount.i18n('foss.pricing.coding'),//编码
			dataIndex : 'code'
		},{
			text : pricing.valueAddDiscount.i18n('foss.pricing.scenarioName'),//方案名称
			dataIndex : 'name'
		},{
			text :pricing.valueAddDiscount.i18n('foss.pricing.status'),//状态
			dataIndex : 'active',
			width:50,
			renderer:function(value){
				if(value=='Y'){//'Y'表示激活
					return pricing.valueAddDiscount.i18n('i18n.pricingRegion.active');
				}else if(value=='N'){//'N'表示未激活
					return  pricing.valueAddDiscount.i18n('i18n.pricingRegion.unActive');
				}else{
					return '';
				}
			}
		},{
			text : pricing.valueAddDiscount.i18n('foss.pricing.createUser'),//创建人
			dataIndex : 'createUserName'
		},{
			text :pricing.valueAddDiscount.i18n('foss.pricing.updateTime'),//修改时间
			dataIndex : 'modifyDate',
			renderer:function(value){
				return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
			}
		},{
			text :pricing.valueAddDiscount.i18n('foss.pricing.qishiTime'),//起始时间
			dataIndex : 'beginTime',
			renderer:function(value){
				return Ext.Date.format(new Date(value), 'Y-m-d');
			}
		},{
			text :pricing.valueAddDiscount.i18n('foss.pricing.theCutOffTime'),//截止时间
			dataIndex : 'endTime',
			renderer:function(value){
				return Ext.Date.format(new Date(value), 'Y-m-d');
			}
		}];
		me.store = Ext.create('Foss.pricing.valueAddDiscount.valueAddDiscountStore',{
			autoLoad : false,
			pageSize : 20,
			listeners: {
				beforeload: function(store, operation, eOpts){
					var queryForm = me.up().getQueryValueAddDiscountForm();
					if(queryForm!=null){
						Ext.apply(operation,{
							params : {
								'priceDiscountVo.marketingEventEntity.active':queryForm.getForm().findField('active').getValue(),//状态
								'priceDiscountVo.marketingEventEntity.name':queryForm.getForm().findField('name').getValue(),//方案名称
								'priceDiscountVo.marketingEventEntity.businessDate':queryForm.getForm().findField('businessDate').getValue()//业务日期
							}
						});	
					}
				}
		    }
		});
		me.listeners = {//消除出现滚动条之后，却不能用的BUG
	    	scrollershow: function(scroller) {
	    		if (scroller && scroller.scrollEl) {
	    				scroller.clearManagedListeners(); 
	    				scroller.mon(scroller.scrollEl, 'scroll', scroller.onElScroll, scroller); 
	    		}
	    	}
	    },
		me.selModel = Ext.create('Ext.selection.CheckboxModel',{//带选择框
					mode:'MULTI',
					checkOnly:true
				});
		//添加头部按钮
		me.tbar = [{
			//新增
			text : pricing.valueAddDiscount.i18n('i18n.pricingRegion.add'),
			disabled: !pricing.valueAddDiscount.isPermission('valueAddDiscount/valueAddDiscountAddbutton'),
			hidden: !pricing.valueAddDiscount.isPermission('valueAddDiscount/valueAddDiscountAddbutton'),
			handler :function(){
				me.getValueAddDiscountAddWindow().show();
			} 
		},'-', {
			//删除
			text : pricing.valueAddDiscount.i18n('foss.pricing.delete'),
			disabled: !pricing.valueAddDiscount.isPermission('valueAddDiscount/valueAddDiscountDeletebutton'),
			hidden: !pricing.valueAddDiscount.isPermission('valueAddDiscount/valueAddDiscountDeletebutton'),
			handler :function(){
				me.deletePricingDiscount();
			} 
		},
		
//		'-', {
//			//激活
//			text : pricing.valueAddDiscount.i18n('i18n.pricingRegion.active'),
//			handler :function(){
//				me.activePricingDiscount();
//			} 
//		},'-', {
//			//中止
//			text : pricing.valueAddDiscount.i18n('foss.pricing.stop'),
//			handler :function(){
//				var selections = me.getSelectionModel().getSelection();
//				if(selections.length!=1){
//					pricing.showWoringMessage(pricing.valueAddDiscount.i18n('foss.pricing.pleaseSelectActivationDataAbortTheOperation'));//请选择一条激活数据进行中止操作！
//					return;
//				}
//				if(selections[0].get('active')!='Y'){
//					pricing.showWoringMessage(pricing.valueAddDiscount.i18n('foss.pricing.pleaseSelectActivationDataAbortTheOperation'));//请选择一条激活数据进行中止操作！
//					return;
//				}
//				if(selections[0].get('beginTime')>=selections[0].get('endTime')){
//					pricing.showWoringMessage(pricing.valueAddDiscount.i18n('foss.pricing.pleaseSelectEffectiveDataAbortOperation'));//请选择一条有效的数据进行中止操作！
//					return;
//				}
//				var model = new Foss.pricing.valueAddDiscount.MarketingEventEntity(selections[0].data);
//				me.getValueAddDiscountEndTimeWindow(model);
//				me.getValueAddDiscountEndTimeWindow().show();
//				me.getValueAddDiscountEndTimeWindow().selection = model;
//			} 
//		},
		
		'-', {
			text : pricing.valueAddDiscount.i18n('foss.pricing.immediatelyActivationProgram'),//'立即激活',
			disabled:!pricing.valueAddDiscount.isPermission('valueAddDiscount/valueAddDiscountImmediatelyActivebutton'),
			hidden:!pricing.valueAddDiscount.isPermission('valueAddDiscount/valueAddDiscountImmediatelyActivebutton'),
			handler :function(){
				me.immediatelyActive();
			} 
		},'-', {
			text : pricing.valueAddDiscount.i18n('foss.pricing.immediatelyStopProgram'),//'立即中止',
			disabled:!pricing.valueAddDiscount.isPermission('valueAddDiscount/valueAddDiscountImmediatelyStopbutton'),
			hidden:!pricing.valueAddDiscount.isPermission('valueAddDiscount/valueAddDiscountImmediatelyStopbutton'),
			handler :function(){
				me.immediatelyStop();
			} 
		}];
		me.bbar = me.getPagingToolbar();
		me.callParent([cfg]);
	}
});

/**
 * @description 增值服务折扣方案主页
 */
Ext.onReady(function() {
	Ext.QuickTips.init();
	if (Ext.getCmp('T_pricing-valueAddDiscount_content')) {
		return;
	}
	pricing.searchGoodTypeList();//货物类型
	pricing.searchProductEntityList();//产品类型
	pricing.haveServerNowTime();
	var queryValueAddDiscountForm = Ext.create('Foss.pricing.valueAddDiscount.QueryValueAddDiscountForm');//查询条件
	var valueAddDiscountGridPanel = Ext.create('Foss.pricing.valueAddDiscount.ValueAddDiscountGridPanel');//查询结果
	Ext.getCmp('T_pricing-valueAddDiscount').add(Ext.create('Ext.panel.Panel', {
	  id : 'T_pricing-valueAddDiscount_content',
		cls : 'panelContentNToolbar',
		bodyCls : 'panelContentNToolbar-body',
		//获得查询FORM
		getQueryValueAddDiscountForm : function() {
			return queryValueAddDiscountForm;
		},
		//获得查询结果GRID
		getAreaGrid : function() {
			return valueAddDiscountGridPanel;
		},
		items : [ queryValueAddDiscountForm, valueAddDiscountGridPanel]
	}));
});
/**
 * 增值服务折扣方案-新增
 */
Ext.define('Foss.pricing.valueAddDiscount.ValueAddDiscountAddWindow',{
	extend : 'Ext.window.Window',
	title : pricing.valueAddDiscount.i18n('foss.pricing.definitionOfPriceAppreciationDiscountProgram'),//价格增值服务折扣方案定义
	closable : true,
	modal : true,
	resizable:false,
	parent:null,//(Foss.pricing.valueAddDiscount.ValueAddDiscountGridPanel)
	closeAction : 'hide',
	width :590,
	height :480,	
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	listeners:{
		beforehide:function(me){
			me.getValueAddDiscountEditFormPanel().getForm().reset();
		},
		beforeshow:function(me){
			
		}
	},
	//增值服务折扣方案主信息FORM
	valueAddDiscountEditFormPanel:null,
	getValueAddDiscountEditFormPanel:function(){
		if(Ext.isEmpty(this.valueAddDiscountEditFormPanel)){
    		this.valueAddDiscountEditFormPanel = Ext.create('Foss.pricing.valueAddDiscount.ValueAddDiscountEditFormPanel',{
    			isShow:false
    		});
    	}
    	return this.valueAddDiscountEditFormPanel;
	},
	//提交增值服务折扣方案主信息
	commintValueAddDiscount:function(){
    	var me = this;
    	var form = me.getValueAddDiscountEditFormPanel();
    	if(form.getForm().isValid()){//校验form是否通过校验
    		var marketingEventModel = new Foss.pricing.valueAddDiscount.MarketingEventEntity();// 增值服务折扣方案主信息
    		var channelEntityList = new Array();//渠道信息
    		form.getForm().updateRecord(marketingEventModel);//将FORM中数据设置到MODEL里面
    		if(marketingEventModel.get('beginTime').getTime()>=marketingEventModel.get('endTime')){
    			pricing.showWoringMessage(pricing.valueAddDiscount.i18n('foss.pricing.dateTerminationMustGreaterThanStartDate'));//终止日期需大于起始日期！
    			return;
    		}
    		var pricingEntryId = form.getForm().findField('pricingEntryName').pricingEntryId;//计价条目ID 
    		var pricingEntryCode = form.getForm().findField('pricingEntryName').pricingEntryCode;//计价条目CODE
    		marketingEventModel.set('pricingEntryCode',pricingEntryCode);
    		marketingEventModel.set('pricingEntryId',pricingEntryId);
    		var channel = FossDataDictionary.getDataDictionaryStore(pricing.valueAddDiscount.channel);//渠道store
    		channel.each(function(record){
    			var item = form.getForm().findField(record.get('valueCode'));
    			if(item.getValue()){
    				var channelEntity = {'salesChannelCode':record.get('valueCode'),'salesChannelId':record.get('id')};
        			channelEntityList.push(channelEntity);
    			}
    		});
    		var params = {'priceDiscountVo':{'marketingEventEntity':marketingEventModel.data,'channelEntityList':channelEntityList}};//折扣新增数据
    		var successFun = function(json){
				pricing.showInfoMes(json.message);//提示新增成功
				me.close();
				me.parent.getPagingToolbar().moveFirst();//成功之后重新查询刷新结果集
				me.parent.getValueAddDiscountDeatilAddWindow().marketingEventEntity = json.priceDiscountVo.marketingEventEntity;
				me.parent.getValueAddDiscountDeatilAddWindow().channelEntityList = json.priceDiscountVo.channelEntityList;
				me.parent.getValueAddDiscountDeatilAddWindow().show();
			};
			var failureFun = function(json){
				if(Ext.isEmpty(json)){
					pricing.showErrorMes(pricing.valueAddDiscount.i18n('foss.pricing.requestTimedOut'));//请求超时
				}else{
					pricing.showErrorMes(json.message);//提示失败原因
				}
			};
			var url = pricing.realPath('addValueAddDiscount.action');//请求折扣新增
			pricing.requestJsonAjax(url,params,successFun,failureFun);//发送AJAX请求
   	    }
	},
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [me.getValueAddDiscountEditFormPanel()];
		me.fbar = [{
			text : pricing.valueAddDiscount.i18n('i18n.pricingRegion.returnGrid'),
			handler :function(){
				me.close();
			} 
		},{
			text : pricing.valueAddDiscount.i18n('i18n.pricingRegion.commit'),
			cls:'yellow_button',
			margin:'0 0 0 360',
			handler :function(){
				me.commintValueAddDiscount();
			} 
		}];
		me.callParent([cfg]);
	}
});
/**
 * 增值服务折扣方案-修改
 */
Ext.define('Foss.pricing.valueAddDiscount.ValueAddDiscountUpdateWindow',{
	extend : 'Ext.window.Window',
	title : pricing.valueAddDiscount.i18n('foss.pricing.definitionOfPriceAppreciationDiscountProgram'),//价格增值服务折扣方案定义
	closable : true,
	modal : true,
	resizable:false,
	marketingEventEntity:null,//增值服务折扣方案主信息
	channelEntityList:null,//渠道信息
	parent:null,//(Foss.pricing.valueAddDiscount.ValueAddDiscountGridPanel)
	closeAction : 'hide',
	width :590,
	height :480,	
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	listeners:{
		beforehide:function(me){
			me.getValueAddDiscountEditFormPanel().getForm().reset();
		},
		beforeshow:function(me){
			var form = me.getValueAddDiscountEditFormPanel();
			if(!Ext.isEmpty(me.marketingEventEntity)){
				var marketingEventModel = new Foss.pricing.valueAddDiscount.MarketingEventEntity(me.marketingEventEntity);
				form.getForm().loadRecord(marketingEventModel);//加载数据
	    		var pricingEntryId = marketingEventModel.get('pricingEntryId');//计价条目ID 
	    		var pricingEntryCode = marketingEventModel.get('pricingEntryCode');//计价条目CODE
	    		var pricingEntryName = marketingEventModel.get('pricingEntryName');//计价条目名称
	    		form.getForm().findField('pricingEntryName').pricingEntryCode = pricingEntryCode;
	    		form.getForm().findField('pricingEntryName').pricingEntryId = pricingEntryId;
	    		form.getForm().findField('pricingEntryName').setValue(pricingEntryName);
			}else{
				pricing.showErrorMes(pricing.valueAddDiscount.i18n('foss.pricing.mainValueAddedServicesDiscountPrograms'));//没有增值服务折扣方案主信息！
				return;
			}
			if(!Ext.isEmpty(me.channelEntityList)){//设置渠道
				for(var i = 0;i<me.channelEntityList.length;i++){
					if(!Ext.isEmpty(form.getForm().findField(me.channelEntityList[i].salesChannelCode))){
						form.getForm().findField(me.channelEntityList[i].salesChannelCode).setValue(true);//渠道元素的name就是渠道的code
					}
				}
			}
		}
	},
	//增值服务折扣方案主信息FORM
	valueAddDiscountEditFormPanel:null,
	getValueAddDiscountEditFormPanel:function(){
		if(Ext.isEmpty(this.valueAddDiscountEditFormPanel)){
    		this.valueAddDiscountEditFormPanel = Ext.create('Foss.pricing.valueAddDiscount.ValueAddDiscountEditFormPanel',{
    			isShow:false
    		});
    	}
    	return this.valueAddDiscountEditFormPanel;
	},
	//提交增值服务折扣方案主信息
	commintValueAddDiscount:function(){
    	var me = this;
    	var form = me.getValueAddDiscountEditFormPanel();
    	if(form.getForm().isValid()){//校验form是否通过校验
    		var marketingEventModel = new Foss.pricing.valueAddDiscount.MarketingEventEntity(me.marketingEventEntity);// 增值服务折扣方案主信息
    		var channelEntityList = new Array();//渠道信息
    		form.getForm().updateRecord(marketingEventModel);//将FORM中数据设置到MODEL里面
    		if(marketingEventModel.get('beginTime').getTime()>=marketingEventModel.get('endTime')){
    			pricing.showWoringMessage(pricing.valueAddDiscount.i18n('foss.pricing.dateTerminationMustGreaterThanStartDate'));//终止日期需大于起始日期！
    			return;
    		}
    		var pricingEntryId = form.getForm().findField('pricingEntryName').pricingEntryId;//计价条目ID 
    		var pricingEntryCode = form.getForm().findField('pricingEntryName').pricingEntryCode;//计价条目CODE
    		marketingEventModel.set('pricingEntryCode',pricingEntryCode);
    		marketingEventModel.set('pricingEntryId',pricingEntryId);
    		var channel = FossDataDictionary.getDataDictionaryStore(pricing.valueAddDiscount.channel);//渠道store
    		channel.each(function(record){
    			var item = form.getForm().findField(record.get('valueCode'));
    			if(item.getValue()){
    				var channelEntity = {'salesChannelCode':record.get('valueCode'),'salesChannelId':record.get('id')};
        			channelEntityList.push(channelEntity);
    			}
    		});
    		var params = {'priceDiscountVo':{'marketingEventEntity':marketingEventModel.data,'channelEntityList':channelEntityList}};//折扣新增数据
    		var successFun = function(json){
				pricing.showInfoMes(json.message);//提示新增成功
				me.close();
				me.parent.getPagingToolbar().moveFirst();//成功之后重新查询刷新结果集
				me.parent.getValueAddDiscountDeatilUpdateWindow().marketingEventEntity = json.priceDiscountVo.marketingEventEntity;
				me.parent.getValueAddDiscountDeatilUpdateWindow().channelEntityList = json.priceDiscountVo.channelEntityList;
				me.parent.getValueAddDiscountDeatilUpdateWindow().show();
			};
			var failureFun = function(json){
				if(Ext.isEmpty(json)){
					pricing.showErrorMes(pricing.valueAddDiscount.i18n('foss.pricing.requestTimedOut'));//请求超时
				}else{
					pricing.showErrorMes(json.message);//提示失败原因
				}
			};
			var url = pricing.realPath('updateValueAddDiscount.action');//请求折扣修改
			pricing.requestJsonAjax(url,params,successFun,failureFun);//发送AJAX请求
   	    }
	},
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [me.getValueAddDiscountEditFormPanel()];
		me.fbar = [{
			text : pricing.valueAddDiscount.i18n('i18n.pricingRegion.returnGrid'),
			handler :function(){
				me.close();
			} 
		},{
			text : pricing.valueAddDiscount.i18n('i18n.pricingRegion.commit'),
			cls:'yellow_button',
			margin:'0 0 0 285',
			handler :function(){
				me.commintValueAddDiscount();
			} 
		},{
			text : pricing.valueAddDiscount.i18n('foss.pricing.modifyDetails'),
			cls:'yellow_button',
			handler :function(){
				var id = me.marketingEventEntity.id;
            	var params = {'priceDiscountVo':{'marketingEventEntity':{'id':id}}};
				var successFun = function(json){
					me.parent.getValueAddDiscountDeatilUpdateWindow().marketingEventEntity = json.priceDiscountVo.marketingEventEntity;
					me.parent.getValueAddDiscountDeatilUpdateWindow().channelEntityList = json.priceDiscountVo.channelEntityList;
					me.parent.getValueAddDiscountDeatilUpdateWindow().show();
				};
				var failureFun = function(json){
					if(Ext.isEmpty(json)){
						pricing.showErrorMes(pricing.valueAddDiscount.i18n('i18n.pricingRegion.requestTimeOut'));
					}else{
						pricing.showErrorMes(json.message);
					}
				};
				var url = pricing.realPath('selectValueAddMarketingByPrimaryKey.action');
				pricing.requestJsonAjax(url,params,successFun,failureFun);
			} 
		}];
		me.callParent([cfg]);
	}
});
/**
 * 增值服务折扣方案明细-查看
 */
Ext.define('Foss.pricing.valueAddDiscount.ValueAddDiscountDeatilShowWindow',{
	extend : 'Ext.window.Window',
	title : pricing.valueAddDiscount.i18n('foss.pricing.valueAddedServicesDiscountProgram'),//查看增值服务折扣方案
	resizable:false,
	marketingEventEntity:null,//增值服务折扣方案主信息
	channelEntityList:null,//渠道信息
	parent:null,//(Foss.pricing.valueAddDiscount.ValueAddDiscountGridPanel)
	closeAction : 'hide',
	width :650,
	height :720,	
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	listeners:{
		beforehide:function(me){
			me.getValueAddDiscountShowTab().getValueAddDiscountEditFormPanel().getForm().reset();
			me.getValueAddDiscountShowTab().getValueAddDiscountDetailShowPanel().getQueryValueAddDiscountDetailForm().getForm().reset();
			me.getValueAddDiscountShowTab().getValueAddDiscountDetailShowPanel().getValueAddDiscountDetailGridPanel().getStore().removeAll();
		},
		beforeshow:function(me){
			var form = me.getValueAddDiscountShowTab().getValueAddDiscountEditFormPanel();
			if(!Ext.isEmpty(me.marketingEventEntity)){
				form.getForm().loadRecord(new Foss.pricing.valueAddDiscount.MarketingEventEntity(me.marketingEventEntity));//加载数据
			}else{
				pricing.showErrorMes(pricing.valueAddDiscount.i18n('foss.pricing.mainValueAddedServicesDiscountPrograms'));//没有增值服务折扣方案主信息！
				return;
			}
			if(!Ext.isEmpty(me.channelEntityList)){//设置渠道
				for(var i = 0;i<me.channelEntityList.length;i++){
					form.getForm().findField(me.channelEntityList[i].salesChannelCode).setValue(true);//渠道元素的name就是渠道的code
				}
			}
		}
	},
	//增值服务折扣方案明细新增
	valueAddDiscountShowTab:null,
	getValueAddDiscountShowTab:function(){
		if(Ext.isEmpty(this.valueAddDiscountAddTab)){
    		this.valueAddDiscountAddTab = Ext.create('Foss.pricing.valueAddDiscount.ValueAddDiscountShowTab');
    	}
    	return this.valueAddDiscountAddTab;
	},
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [me.getValueAddDiscountShowTab()];
		me.fbar = [{
			text : pricing.valueAddDiscount.i18n('i18n.pricingRegion.returnGrid'),
			handler :function(){
				me.close();
			} 
		}];
		me.callParent([cfg]);
	}
});
/**
 * 增值服务折扣方案明细-新增
 */
Ext.define('Foss.pricing.valueAddDiscount.ValueAddDiscountDeatilAddWindow',{
	extend : 'Ext.window.Window',
	title : pricing.valueAddDiscount.i18n('foss.pricing.definitionOfPriceDiscounts'),//价格折扣定义
	closable : true,
	modal : true,
	startAllNet:null,//全网
	arrvAllNet:null,
	resizable:false,
	marketingEventEntity:null,//显示的折扣主信息
	channelEntityList:null,//渠道信息
	parent:null,//(Foss.pricing.valueAddDiscount.ValueAddDiscountGridPanel)
	closeAction : 'hide',
	width :650,
	height :750,	
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	listeners:{
		beforehide:function(me){
			me.startAllNet = null;
			me.arrvAllNet = null;
			me.getValueAddDiscountAddTab().getValueAddDiscountEditFormPanel().getForm().reset();//清除增值服务折扣方案主信息
			me.getValueAddDiscountAddTab().getValueAddDiscountDetailEditFormPanel().getForm().reset();//清除增值服务折扣方案明细信息
			me.marketingEventEntity = null;//清掉折扣主信息
			me.channelEntityList = [];
			me.getValueAddDiscountAddTab().getStartArraiveGridPanel().getStartGrid().getStore().removeAll();//清除出发表数据
			if(me.getValueAddDiscountAddTab().getStartArraiveGridPanel().getStartGrid().dockedItems.items[2] != undefined) {
				me.getValueAddDiscountAddTab().getStartArraiveGridPanel().getStartGrid().dockedItems.items[2].items.items[8].reset();//全网不勾选
			}
			me.getValueAddDiscountAddTab().getStartArraiveGridPanel().getArriveGrid().getStore().removeAll();//清除到达表数据
			if(me.getValueAddDiscountAddTab().getStartArraiveGridPanel().getArriveGrid().dockedItems.items[2] != undefined) {
				me.getValueAddDiscountAddTab().getStartArraiveGridPanel().getArriveGrid().dockedItems.items[2].items.items[8].reset();//全网不勾选
			}			
		},
		beforeshow:function(me){
			var form = me.getValueAddDiscountAddTab().getValueAddDiscountEditFormPanel();
			if(!Ext.isEmpty(me.marketingEventEntity)){
				form.getForm().loadRecord(new Foss.pricing.valueAddDiscount.MarketingEventEntity(me.marketingEventEntity));//加载数据
			}else{
				pricing.showErrorMes(pricing.valueAddDiscount.i18n('foss.pricing.mainValueAddedServicesDiscountPrograms'));//没有增值服务折扣方案主信息！
				return;
			}
			if(!Ext.isEmpty(me.channelEntityList)){//设置渠道
				for(var i = 0;i<me.channelEntityList.length;i++){
					if(!Ext.isEmpty(form.getForm().findField(me.channelEntityList[i].salesChannelCode))){
						form.getForm().findField(me.channelEntityList[i].salesChannelCode).setValue(true);//渠道元素的name就是渠道的code
					}
				}
			}
			
		}
	},
	//增值服务折扣方案明细新增
	valueAddDiscountAddTab:null,
	getValueAddDiscountAddTab:function(){
		if(Ext.isEmpty(this.valueAddDiscountAddTab)){
    		this.valueAddDiscountAddTab = Ext.create('Foss.pricing.valueAddDiscount.ValueAddDiscountAddTab');
    	}
    	return this.valueAddDiscountAddTab;
	},
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [me.getValueAddDiscountAddTab()];
		me.fbar = [{
			text : pricing.valueAddDiscount.i18n('i18n.pricingRegion.returnGrid'),
			handler :function(){
				me.close();
			} 
		}];
		me.callParent([cfg]);
	}
});
/**
 * 增值服务折扣方案明细-查看
 */
Ext.define('Foss.pricing.valueAddDiscount.ValueAddDiscountDeatilShowWindow',{
	extend : 'Ext.window.Window',
	title : pricing.valueAddDiscount.i18n('foss.pricing.valueAddedServicesDiscountProgram'),//查看增值服务折扣方案
	resizable:false,
	marketingEventEntity:null,//增值服务折扣方案主信息
	channelEntityList:null,//渠道信息
	parent:null,//(Foss.pricing.valueAddDiscount.ValueAddDiscountGridPanel)
	closeAction : 'hide',
	width :650,
	height :720,	
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	listeners:{
		beforehide:function(me){
			me.getValueAddDiscountShowTab().getValueAddDiscountEditFormPanel().getForm().reset();
			me.getValueAddDiscountShowTab().getValueAddDiscountDetailShowPanel().getQueryValueAddDiscountDetailForm().getForm().reset();
			me.getValueAddDiscountShowTab().getValueAddDiscountDetailShowPanel().getValueAddDiscountDetailGridPanel().getStore().removeAll();
		},
		beforeshow:function(me){
			var form = me.getValueAddDiscountShowTab().getValueAddDiscountEditFormPanel();
			if(!Ext.isEmpty(me.marketingEventEntity)){
				form.getForm().loadRecord(new Foss.pricing.valueAddDiscount.MarketingEventEntity(me.marketingEventEntity));//加载数据
			}else{
				pricing.showErrorMes(pricing.valueAddDiscount.i18n('foss.pricing.mainValueAddedServicesDiscountPrograms'));//没有增值服务折扣方案主信息！
				return;
			}
			if(!Ext.isEmpty(me.channelEntityList)){//设置渠道
				for(var i = 0;i<me.channelEntityList.length;i++){
					if(!Ext.isEmpty(form.getForm().findField(me.channelEntityList[i].salesChannelCode))){
						form.getForm().findField(me.channelEntityList[i].salesChannelCode).setValue(true);//渠道元素的name就是渠道的code
					}
				}
			}
		}
	},
	//增值服务折扣方案明细新增
	valueAddDiscountShowTab:null,
	getValueAddDiscountShowTab:function(){
		if(Ext.isEmpty(this.valueAddDiscountAddTab)){
    		this.valueAddDiscountAddTab = Ext.create('Foss.pricing.valueAddDiscount.ValueAddDiscountShowTab');
    	}
    	return this.valueAddDiscountAddTab;
	},
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [me.getValueAddDiscountShowTab()];
		me.fbar = [{
			text : pricing.valueAddDiscount.i18n('i18n.pricingRegion.returnGrid'),
			handler :function(){
				me.close();
			} 
		}];
		me.callParent([cfg]);
	}
});

/**
 * 查看增值服务折扣方案-TAB
 */
Ext.define('Foss.pricing.valueAddDiscount.ValueAddDiscountShowTab', {
	extend : 'Ext.tab.Panel',
	cls : 'innerTabPanel',
    flex:1,
    layout : {
		type : 'vbox',
		align : 'stretch'
	},
    //增值服务折扣方案主信息FORM
    valueAddDiscountEditFormPanel:null,
	getValueAddDiscountEditFormPanel:function(){
		if(Ext.isEmpty(this.valueAddDiscountEditFormPanel)){
    		this.valueAddDiscountEditFormPanel = Ext.create('Foss.pricing.valueAddDiscount.ValueAddDiscountEditFormPanel',{
    			isShow:true
    		});
    		this.valueAddDiscountEditFormPanel.getForm().getFields().each(function(item){
    			item.setReadOnly(true);
    		});
    	}
    	return this.valueAddDiscountEditFormPanel;
	},
	//已选折扣信息结果集
	valueAddDiscountDetailShowPanel:null,
	getValueAddDiscountDetailShowPanel:function(){
		if(Ext.isEmpty(this.valueAddDiscountDetailShowPanel)){
    		this.valueAddDiscountDetailShowPanel = Ext.create('Foss.pricing.valueAddDiscount.ValueAddDiscountDetailShowPanel',{
    			isShow:true//表明只是查看
    		});
    	}
    	return this.valueAddDiscountDetailShowPanel;
	},
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [me.getValueAddDiscountEditFormPanel(),me.getValueAddDiscountDetailShowPanel()];
		me.callParent([cfg]);
	}
});
/**
 * 新增增值服务折扣方案-TAB
 */
Ext.define('Foss.pricing.valueAddDiscount.ValueAddDiscountAddTab', {
	extend : 'Ext.tab.Panel',
	cls : 'innerTabPanel',
    flex:1,
    layout : {
		type : 'vbox',
		align : 'stretch'
	},
    //增值服务折扣方案主信息FORM
    valueAddDiscountEditFormPanel:null,
	getValueAddDiscountEditFormPanel:function(){
		if(Ext.isEmpty(this.valueAddDiscountEditFormPanel)){
    		this.valueAddDiscountEditFormPanel = Ext.create('Foss.pricing.valueAddDiscount.ValueAddDiscountEditFormPanel',{
    			isShow:true
    		});
    		this.valueAddDiscountEditFormPanel.getForm().getFields().each(function(item){
    			item.setReadOnly(true);
    		});
    	}
    	return this.valueAddDiscountEditFormPanel;
	},
	 //维护折扣信息FORM
	valueAddDiscountDetailEditFormPanel:null,
	getValueAddDiscountDetailEditFormPanel:function(){
		if(Ext.isEmpty(this.valueAddDiscountDetailEditFormPanel)){
    		this.valueAddDiscountDetailEditFormPanel = Ext.create('Foss.pricing.valueAddDiscount.ValueAddDiscountDetailEditFormPanel');
    	}
    	return this.valueAddDiscountDetailEditFormPanel;
	},
	//出发到达grid panel
	startArraiveGridPanel:null,
	getStartArraiveGridPanel:function(){
		if(Ext.isEmpty(this.startArraiveGridPanel)){
    		this.startArraiveGridPanel = Ext.create('Foss.pricing.valueAddDiscount.StartArraiveGridPanel');
    	}
    	return this.startArraiveGridPanel;
	},
	//已选折扣信息结果集
	valueAddDiscountDetailShowPanel:null,
	getValueAddDiscountDetailShowPanel:function(){
		if(Ext.isEmpty(this.valueAddDiscountDetailShowPanel)){
    		this.valueAddDiscountDetailShowPanel = Ext.create('Foss.pricing.valueAddDiscount.ValueAddDiscountDetailShowPanel',{
    			'isShow':false
    		});
    	}
    	return this.valueAddDiscountDetailShowPanel;
	},
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [me.getValueAddDiscountEditFormPanel(),me.getStartArraiveGridPanel(),me.getValueAddDiscountDetailEditFormPanel(),me.getValueAddDiscountDetailShowPanel()];
		me.callParent([cfg]);
	}
});
/**
 * 维护折扣信息
 */
Ext.define('Foss.pricing.valueAddDiscount.ValueAddDiscountDetailEditFormPanel', {
	extend : 'Ext.form.Panel',
	//frame: true,
	title:' 维护折扣信息',
	flex:1,
	collapsible: true,
    defaults : {
    	colspan: 1,
    	margin : '8 10 5 10',
        allowBlank:false
    },
	defaultType : 'textfield',
	layout:{
		type:'table',
		columns: 2
	},
	//新增增值服务折扣方案明细
	commintValueAddDiscountDetail:function(){
        var me = this;
        var startDiscountOrgEntityList = new Array();//出发部门
        var endDiscountOrgEntityList = new Array();//到达部门
        me.up('window').getValueAddDiscountAddTab().getStartArraiveGridPanel().getStartGrid().getStore().each(function(record){
        	startDiscountOrgEntityList.push(record.data);
        });
        me.up('window').getValueAddDiscountAddTab().getStartArraiveGridPanel().getArriveGrid().getStore().each(function(record){
        	endDiscountOrgEntityList.push(record.data);
        });
        if(startDiscountOrgEntityList.length<1&&Ext.isEmpty(me.up('window').startAllNet)){
        	pricing.showWoringMessage(pricing.valueAddDiscount.i18n('foss.pricing.pleaseElectDeparture'));//请选者出发地！
        	return;
        }
        if(endDiscountOrgEntityList.length<1&&Ext.isEmpty(me.up('window').arrvAllNet)){
        	pricing.showWoringMessage(pricing.valueAddDiscount.i18n('foss.pricing.pleaseChooseArr'));//请选者到达地！
        	return;
        }
        if(me.getForm().isValid()){
        	var priceDiscountDtoModel = new Foss.pricing.valueAddDiscount.ValueAddDiscountDto();
            me.getForm().updateRecord(priceDiscountDtoModel);//更新数据
            //如果开始范围大于结束范围！
            if(priceDiscountDtoModel.get('leftRange')>priceDiscountDtoModel.get('rightRange')){
            	pricing.showWoringMessage(pricing.valueAddDiscount.i18n('foss.pricing.leftRangeMoreRanRightRange'));//开始范围不能大于结束范围！
            	return;
            }
           //如果直接减免大于最高减免！
            if(priceDiscountDtoModel.get('minFee')>priceDiscountDtoModel.get('maxFee')){
            	pricing.showWoringMessage(pricing.valueAddDiscount.i18n('foss.pricing.minFeeMoreRanMaxFee'));//直接减免不能大于最高减免！
            	return;
            }
            if(priceDiscountDtoModel.get('discountRate')<100&&priceDiscountDtoModel.get('minFee')>0){
            	pricing.showWoringMessage(pricing.valueAddDiscount.i18n('foss.pricing.minFeeAnddiscountRateEqcelZero'));//直接减免和折扣率不能同时大于0！
            	return;
            }
            if(priceDiscountDtoModel.get('discountRate')==100&&priceDiscountDtoModel.get('minFee')==0){
            	pricing.showWoringMessage(pricing.valueAddDiscount.i18n('foss.pricing.minFeeAnddiscountRateDengZero'));//直接减免和折扣率不能同时等于0！
            	return;
            }
            var priceEntryId =  me.up('window').marketingEventEntity.pricingEntryId;//计价条目ID
            var priceEntryCode = me.up('window').marketingEventEntity.pricingEntryCode;//计价条目CODE
            priceDiscountDtoModel.set('priceEntryId',priceEntryId);
            priceDiscountDtoModel.set('priceEntryCode',priceEntryCode);
            var beginTime = me.up('window').marketingEventEntity.beginTime;//开始日期
            priceDiscountDtoModel.set('beginDate',beginTime);
            var endTime = me.up('window').marketingEventEntity.endTime;//截止日期
            priceDiscountDtoModel.set('endDate',endTime);
            var marketId = me.up('window').marketingEventEntity.id;//增值服务折扣方案主信息ID
            priceDiscountDtoModel.set('marketId',marketId);
            var marketCode = me.up('window').marketingEventEntity.code;//增值服务折扣方案主信息CODE
            priceDiscountDtoModel.set('marketCode',marketCode);
            var goodsTypeCode = me.getForm().findField('goodsTypeId').goodsTypeRecord.get('code');
            priceDiscountDtoModel.set('goodsTypeCode',goodsTypeCode);
            var productCode = me.getForm().findField('productId').productRecord.get('code');
            priceDiscountDtoModel.set('productCode',productCode);
            var startAllNet = me.up('window').startAllNet;
            var arrvAllNet = me.up('window').arrvAllNet;
            var param = {'priceDiscountVo':{'startDiscountOrgEntityList':startDiscountOrgEntityList
            	,'endDiscountOrgEntityList':endDiscountOrgEntityList
            	,'priceDiscountDto':priceDiscountDtoModel.data
            	,'startAllNet':startAllNet
            	,'arrvAllNet':arrvAllNet}};
            var successFun = function(json){
            	pricing.showInfoMes(json.message);
				me.up('window').getValueAddDiscountAddTab().getValueAddDiscountDetailShowPanel().getValueAddDiscountDetailGridPanel().getPagingToolbar().moveFirst();
            };
            var failureFun = function(json){
            	if(Ext.isEmpty(json)){
					pricing.showErrorMes(pricing.valueAddDiscount.i18n('i18n.pricingRegion.requestTimeOut'));//请求超时！
				}else{
					pricing.showErrorMes(json.message);
				}
            };
        	var url = pricing.realPath('addValueAddDiscountProgramItem.action');
			pricing.requestJsonAjax(url,param,successFun,failureFun);
        }  
	},
	constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		var allProduct = {'id':'ALL','code':'ALL','name':pricing.valueAddDiscount.i18n('foss.pricing.allProducts')};
//		var allDoodsType = {'id':'ALL','code':'ALL','name':pricing.valueAddDiscount.i18n('foss.pricing.allCargoTypes')};
		var productEntityList =  pricing.addAll(pricing.valueAddDiscount.productEntityList,allProduct);
//		var goodTypeList =  pricing.addAll(pricing.valueAddDiscount.goodTypeList,allDoodsType);
		var goodTypeList = pricing.valueAddDiscount.goodTypeList;
		me.items = [{
			name: 'caculateType',
			queryMode: 'local',
		    displayField: 'valueName',
		    valueField: 'valueCode',
		    editable:false,
		    productRecord:null,
		    store:pricing.getStore(null,null,['valueCode','valueName'],
		    pricing.valueAddDiscount.caculateType),
	        fieldLabel: pricing.valueAddDiscount.i18n('foss.pricing.discountRules'),//折扣规则
	        xtype : 'combo'
		},{
			xtype: 'displayfield',
	        value: '<span style="color:red">'
	        	+pricing.valueAddDiscount.i18n('foss.pricing.canWeightVolumeDirectCosts')
	        	+'</span>',//可以按重量、体积或者直接费用
	        margins: '0 0 0 10'
		},{
			name: 'productId',
			queryMode: 'local',
		    displayField: 'name',
		    valueField: 'id',
		    editable:false,
		    productRecord:null,//基础产品实体
		    store:pricing.getStore(null,'Foss.pricing.valueAddDiscount.ProductEntity',null
		    ,productEntityList),
	        fieldLabel: pricing.valueAddDiscount.i18n('foss.pricing.basicProducts'),//基础产品
	        xtype : 'combo',
	        listeners:{
	        	change:function(comb,newValue,oldvalue){
	        		if(!Ext.isEmpty(newValue)){
	        			comb.productRecord = comb.getStore().getById(newValue);
	        		}else{
	        			comb.productRecord = null;
	        		}
	        	}
	        }
		},{
			xtype: 'displayfield',
	        value: '<span style="color:red">'
	        	+pricing.valueAddDiscount.i18n('foss.pricing.productsPrecisionQatarAirways')
	        	+'</span>',//产品：精准卡航
	        margins: '0 0 0 10'
		},{
			name: 'pricingIndustryCode',
			queryMode: 'local',
		    displayField: 'valueName',
		    valueField: 'valueCode',
		    editable:false,
		    pricingIndustryId:null,
		    store:FossDataDictionary.getDataDictionaryStore(pricing.valueAddDiscount.industry),
	        fieldLabel: pricing.valueAddDiscount.i18n('foss.pricing.trade'),//所属行业
	        xtype : 'combo',
	        listeners:{
	        	change:function(comb,newValue){
	        		comb.getStore().each(function(record){
	        			if(record.get('valueCode')==newValue){
	        				comb.pricingIndustryId = record.get('id');
	        			}
	        		});
	        	}
	        }
		},{
			xtype: 'displayfield',
	        value: '',//产品：精准卡航
	        margins: '0 0 0 10'
		},{
			name: 'goodsTypeId',
			queryMode: 'local',
		    displayField: 'name',
		    valueField: 'id',
		    editable:false,
		    goodsTypeRecord:null,
		    store:pricing.getStore(null,'Foss.pricing.valueAddDiscount.GoodsTypeEntity',null
		    ,goodTypeList),
	        fieldLabel: pricing.valueAddDiscount.i18n('foss.pricing.cargoType'),//货物类型
	        xtype : 'combo',
	        listeners:{
	        	change:function(comb,newValue,oldvalue){
	        		if(!Ext.isEmpty(newValue)){
	        			comb.goodsTypeRecord = comb.getStore().getById(newValue);
	        		}else{
	        			comb.goodsTypeRecord = null;
	        		}
	        		
	        	}
	        }
		},{
			xtype: 'displayfield',
			value: '<span style="color:red">'
	        	+pricing.valueAddDiscount.i18n('foss.pricing.seafoodEquipmentAndTheLike')
	        	+'</span>',//海鲜、器材之类
	        margins: '0 0 0 10'
		},{
			xtype:'numberfield',
	        decimalPrecision:2,
	        allowBlank:false,
	        name:'leftRange',
	        fieldLabel:pricing.valueAddDiscount.i18n('foss.pricing.beginningOfTheRange'),//开始范围
	        step:0.01,
	        maxValue: 99999999.99,
	        minValue: 0 
		},{
			xtype: 'displayfield',
			value: '<span style="color:red">'
	        	+pricing.valueAddDiscount.i18n('foss.pricing.rangeKgCubicMetersPerDollar')
	        	+'</span>',//范围（公斤立方米每元）
	        margins: '0 0 0 10'
		},{
			xtype:'numberfield',
	        decimalPrecision:2,
	        name:'rightRange',
	        fieldLabel:pricing.valueAddDiscount.i18n('foss.pricing.endOfTheRange'),//结束范围
	        step:0.01,
	        maxValue: 99999999.99,
	        minValue: 0 
		},{
			xtype: 'displayfield',
			value: '<span style="color:red">'
	        	+pricing.valueAddDiscount.i18n('foss.pricing.rangeKgCubicMetersPerDollar')
	        	+'</span>',//范围（公斤立方米每元）
	        margins: '0 0 0 10'
		},{
			xtype:'numberfield',
	        decimalPrecision:2,
	        allowBlank:false,
	        name:'discountRate',
	        value:100,
	        fieldLabel:pricing.valueAddDiscount.i18n('foss.pricing.theDiscountRate'),//折扣率
	        maxValue: 100,
	        step:0.01,
	        minValue: 0
		},{
			xtype: 'displayfield',
			value: '<span style="color:red">'
	        	+'%'
	        	+'</span>',//范围（公斤立方米每元）
	        margins: '0 0 0 10'
		},{
			xtype:'numberfield',
	        decimalPrecision:2,
	        name:'minFee',
	        allowBlank:false,
	        step:1,
	        fieldLabel:pricing.valueAddDiscount.i18n('foss.pricing.directReliefYuan'),
	        maxValue: 99999999.99,
	        minValue: 0
		},{
			xtype: 'displayfield',
			value: '',
	        margins: '0 0 0 10'
		},{
			xtype:'numberfield',
	        decimalPrecision:2,
	        name:'maxFee',
	        step:1,
	        allowBlank:false,
	        fieldLabel:pricing.valueAddDiscount.i18n('foss.pricing.theUpLestVotesdirectRelief'),
	        maxValue: 99999999.99,
	        minValue: 0.01
		},{
			xtype: 'displayfield',
			value: '<span style="color:red">'
	        	+pricing.valueAddDiscount.i18n('foss.pricing.directReliefYuan')
	        	+'</span>',//直接减免（元）
	        margins: '0 0 0 10'
		}];
		me.fbar = [{
			text : pricing.valueAddDiscount.i18n('i18n.pricingRegion.commit'),
			cls:'yellow_button',
			margin:'0 0 0 360',
			handler :function(){
				me.commintValueAddDiscountDetail();
			} 
		}];
		me.callParent([cfg]);
	}
});
/**
 * 增值服务折扣方案主信息
 */
Ext.define('Foss.pricing.valueAddDiscount.ValueAddDiscountEditFormPanel', {
	extend : 'Ext.form.Panel',
	frame: true,
	title:' 价格增值服务折扣方案定义',//价格增值服务折扣方案定义
	flex:1,
	collapsible: true,
    defaults : {
    	colspan: 2,
    	labelWidth:110,
    	allowBlank:false,
    	margin : '5 5 5 5'
    },
	layout:{
		type:'table',
		columns: 2
	},
	 //查询增值服务
	discountPricingWindow:null,
	getDiscountPricingWindow:function(){
		if(Ext.isEmpty(this.discountPricingWindow)){
    		this.discountPricingWindow = Ext.create('Foss.pricing.valueAddDiscount.DiscountPricingWindow');
    		this.discountPricingWindow.parent = this;
    	}
    	return this.discountPricingWindow;
	},
	constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		var channel = FossDataDictionary.getDataDictionaryStore(pricing.valueAddDiscount.channel);
		var channelItems = new Array();
		channel.each(function(record){
			var item = { boxLabel:record.get('valueName'), name:record.get('valueCode'),channelEntity:record.data };
			channelItems.push(item);
		});
		var minValueDate = null;
		if(!config.isShow){
			minValueDate = new Date(pricing.valueAddDiscount.tomorrowTime);
		}
		me.items = [{
			name: 'code',
		    fieldLabel:'<span style="color:red">*</span>编码',//编码
		    readOnly:true,
		    allowBlank:true,
		    emptyText:pricing.valueAddDiscount.i18n('foss.pricing.automaticallyGeneratedCoding'),//自动生成编码
	        xtype : 'textfield'
		},{
			name: 'name',
			maxLength:20,
		    fieldLabel:pricing.valueAddDiscount.i18n('foss.pricing.scenarioName'),//方案名称
	        xtype : 'textfield'
		},{
	        xtype: 'checkboxgroup',
	        fieldLabel:pricing.valueAddDiscount.i18n('foss.pricing.orderChannels'),//订单渠道
	        width:500,
	        allowBlank:true,
	        vertical: true,
	        items:channelItems
	    },{
	        xtype: 'textfield',
	        colspan: 1,
	        name:'pricingEntryName',
	        pricingEntryId:null,
	        pricingEntryCode:null,
	        fieldLabel:'<span style="color:red">*</span>'+pricing.valueAddDiscount.i18n('foss.pricing.valueAddedServices'),//增值服务
	        readOnly:true
	    },{
	        xtype: 'button',
	        colspan: 1,
	        text:pricing.valueAddDiscount.i18n('foss.pricing.select'),//选择
	        handler: function() {
	           me.getDiscountPricingWindow().show();
	        }
	    },{
			name: 'beginTime',
			colspan: 1,
			minValue:minValueDate,
			format:'Y-m-d',
		    fieldLabel:pricing.valueAddDiscount.i18n('foss.pricing.preferentialPeriod'),//折扣期限
	        xtype : 'datefield'
		},{
			name: 'endTime',
			colspan: 1,
			labelWidth:20,
			minValue:minValueDate,
			format:'Y-m-d',
		    fieldLabel:pricing.valueAddDiscount.i18n('foss.pricing.to'),//至
	        xtype : 'datefield'
		},{
//			name: 'description',
			name: 'remark',
			width:400,
			maxLength:200,
			allowBlank:true,
		    fieldLabel:pricing.valueAddDiscount.i18n('foss.pricing.priceValueAddedServicesDiscountProgramDescription'),//价格增值服务折扣方案说明
	        xtype : 'textareafield'
		}];
		me.callParent([cfg]);
	}
});
//出发和到达PANEL
Ext.define('Foss.pricing.valueAddDiscount.StartArraiveGridPanel',{
	extend : 'Ext.panel.Panel',
	//frame: true,
	title : pricing.valueAddDiscount.i18n('foss.pricing.departureAndArrival'),//出发和到达
	flex:1,
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	//出发GRID
	startGrid:null,
	getStartGrid:function(){
		if(Ext.isEmpty(this.startGrid)){
    		this.startGrid = Ext.create('Foss.pricing.valueAddDiscount.StartGrid');
    	}
    	return this.startGrid;
	},
	//到达GRID
	arriveGrid:null,
	getArriveGrid:function(){
		if(Ext.isEmpty(this.arriveGrid)){
    		this.arriveGrid = Ext.create('Foss.pricing.valueAddDiscount.ArriveGrid');
    	}
    	return this.arriveGrid;
	},
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [me.getStartGrid(),me.getArriveGrid()];
		me.callParent([cfg]);
	}
});
//折扣详情新增修改
Ext.define('Foss.pricing.valueAddDiscount.ValueAddDiscountDetailShowPanel',{
	extend : 'Ext.panel.Panel',
	title : pricing.valueAddDiscount.i18n('foss.pricing.theDiscountSelectedInformationResults'),//已选折扣信息结果
	//frame: true,
	flex:1,
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	//查询条件
	queryValueAddDiscountDetailForm:null,
	getQueryValueAddDiscountDetailForm:function(){
		if(Ext.isEmpty(this.queryValueAddDiscountDetailForm)){
    		this.queryValueAddDiscountDetailForm = Ext.create('Foss.pricing.valueAddDiscount.QueryValueAddDiscountDetailForm');
    	}
    	return this.queryValueAddDiscountDetailForm;
	},
	//增值服务折扣方案明细GRID
	valueAddDiscountDetailGridPanel:null,
	getValueAddDiscountDetailGridPanel:function(isShow){
		if(Ext.isEmpty(this.valueAddDiscountDetailGridPanel)){
    		this.valueAddDiscountDetailGridPanel = Ext.create('Foss.pricing.valueAddDiscount.ValueAddDiscountDetailGridPanel',{
    			'isShow':isShow
    		});
    	}
    	return this.valueAddDiscountDetailGridPanel;
	},
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [me.getQueryValueAddDiscountDetailForm(),me.getValueAddDiscountDetailGridPanel(config.isShow)];
		me.callParent([cfg]);
	}
});
//出发GRID
Ext.define('Foss.pricing.valueAddDiscount.StartGrid', {
   extend:'Ext.grid.Panel', 
   title:pricing.valueAddDiscount.i18n('foss.pricing.departure'),
   frame: true,
   autoScroll:true,
   sortableColumns:false,
   enableColumnHide:false,
   enableColumnMove:false,
   flex:1,
 //添加区域
   valueAddDiscountRegionGridShowWindow:null,
   getValueAddDiscountRegionGridShowWindow:function(){
		if(Ext.isEmpty(this.valueAddDiscountRegionGridShowWindow)){
   		     this.valueAddDiscountRegionGridShowWindow = Ext.create('Foss.pricing.valueAddDiscount.ValueAddDiscountRegionGridShowWindow');
   		     this.valueAddDiscountRegionGridShowWindow.parent = this;
   		     this.valueAddDiscountRegionGridShowWindow.type = pricing.valueAddDiscount.priceDiscountStart;
	   	}
	   	return this.valueAddDiscountRegionGridShowWindow;
    },
     //添加城市
    valueAddDiscountCityGridShowWindow:null,
    getValueAddDiscountCityGridShowWindow:function(){
 		if(Ext.isEmpty(this.valueAddDiscountCityGridShowWindow)){
    		     this.valueAddDiscountCityGridShowWindow = Ext.create('Foss.pricing.valueAddDiscount.ValueAddDiscountCityGridShowWindow');
    		     this.valueAddDiscountCityGridShowWindow.parent = this;
    		     this.valueAddDiscountCityGridShowWindow.type = pricing.valueAddDiscount.priceDiscountStart;
 	   	}
 	   	return this.valueAddDiscountCityGridShowWindow;
     },
    orgWindow:null,
    getOrgWindow:function(){
    	var me = this;
    	if(Ext.isEmpty(this.orgWindow)){
    		this.orgWindow = Ext.create('Foss.baseinfo.commonSelector.orgSelectorWindow',{
    			types:'ORG',
    			salesDepartment:'Y',
    			active:'Y',
    			modal:true,
    			commitFun:function(){
    				var selections = this.getGridRecord();
    				if(selections.length==0){
    					dict.showErrorMes(pricing.valueAddDiscount.i18n('foss.pricing.pleaseSelectLeastOneData'));//请至少选择一条数据！
    					return;
    				}
    				var datas = new Array();
					for(var i = 0;i<selections.length;i++){
						var isHave = false;//是否已经存在
						me.getStore().each(function(record){
							if(record.get('deptOrgId')==selections[i].get('id')){
								isHave = true;
							}
						});
						if(!isHave){//不存在则新增
							var data = {'deptOrgId':selections[i].get('id'),'name':selections[i].get('name'),'deptOrgCode':selections[i].get('code'),'deptOrgTypeCode':pricing.valueAddDiscount.valueAddDiscountDetailTypeDept};
							datas.push(data);
						}
					};
					var oldData = me.getStore().getAt(0)//清掉不是一类的数据
 					if(!Ext.isEmpty(oldData)){
 						if(oldData.get('deptOrgTypeCode')!=pricing.valueAddDiscount.valueAddDiscountDetailTypeDept){
 							me.getStore().removeAll();
 						}
 					}
    				me.getStore().add(datas);
    				this.close();
    			}
    		});
    		me.orgWindow.items.items[0].getForm().findField('type').getStore().removeAll();
    		me.orgWindow.items.items[0].getForm().findField('type').getStore().add({
				code : 'ORG',
				name : pricing.valueAddDiscount.i18n('foss.pricing.internalOrganization')//内部组织
			});
    		me.orgWindow.items.items[0].items.items[8].items.items[3].setReadOnly(true);
    		this.orgWindow.parent = this;
    	}
    	return this.orgWindow;
    },
   constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.columns = [{
				text : pricing.valueAddDiscount.i18n('i18n.pricingRegion.opra'),//操作
				xtype:'actioncolumn',
				align: 'center',
				width:80,
				items: [{
						iconCls: 'deppon_icons_delete',
		                tooltip: pricing.valueAddDiscount.i18n('foss.pricing.delete'),//删除
						width:42,
		                handler: function(grid, rowIndex, colIndex) {
		            		//获取选中的数据
		    				var record=grid.getStore().getAt(rowIndex);
		            		pricing.showQuestionMes(pricing.valueAddDiscount.i18n('foss.pricing.wantDeleteThisData'),function(e){//是否要删除这个数据？
		            			if(e=='yes'){//询问是否删除，是则发送请求
		            				grid.getStore().remove(record);
		            			}
		            		});
		                }
					}]
			  },{ 
		        	  header:pricing.valueAddDiscount.i18n('foss.pricing.optionalProducts')//可选产品
		        	  ,dataIndex: 'name'
	          },{ 
	        	  header:pricing.valueAddDiscount.i18n('foss.pricing.type')
	        	  ,dataIndex: 'deptOrgTypeCode'
	              ,renderer:function(value){
	            	  if(pricing.valueAddDiscount.valueAddDiscountDetailTypeDept==value){
	            		  return pricing.valueAddDiscount.i18n('foss.pricing.dept');
	            	  }else if(pricing.valueAddDiscount.valueAddDiscountDetailTypeRegion==value){
	            		  return pricing.valueAddDiscount.i18n('foss.pricing.region');
	            	  }else if(pricing.valueAddDiscount.valueAddDiscountDetailTypeCity==value){
	            		  return pricing.valueAddDiscount.i18n('i18n.pricingRegion.city');
	            	  }else{
	            		  return '';
	            	  }
	              }
	         }
		      ];
		me.listeners = {
		    	scrollershow: function(scroller) {
		    		if (scroller && scroller.scrollEl) {
		    				scroller.clearManagedListeners(); 
		    				scroller.mon(scroller.scrollEl, 'scroll', scroller.onElScroll, scroller); 
		    		}
		    	}
		  };
		me.selModel = Ext.create('Ext.selection.CheckboxModel',{//单选框
			mode:'MULTI',
			checkOnly:true
		});
		me.store = pricing.getStore(null,null,['deptOrgId','name','deptOrgCode','deptOrgTypeCode'],[]),
		me.tbar = [{
			//删除
			text : pricing.valueAddDiscount.i18n('foss.pricing.delete'),
			handler :function(){
				//获取选中的数据
				var selections = me.getSelectionModel().getSelection();
				if(selections.length<1){
					pricing.showErrorMes(pricing.valueAddDiscount.i18n('foss.pricing.pleaseSelectDataYouWantDelete'));//请选择要删除的数据！
					return;
				}
				pricing.showQuestionMes(pricing.valueAddDiscount.i18n('foss.pricing.wwantDeleteTheseData'),function(e){//是否要删除这些数据？
					if(e=='yes'){//询问是否删除，是则发送请求
						me.getStore().remove(selections);
					}
				});
			} 
		},'-', {
			//添加区域
			text : pricing.valueAddDiscount.i18n('foss.pricing.addARegion'),
			handler :function(){
				me.getValueAddDiscountRegionGridShowWindow().show();
			} 
		},'-', {
			//添加城市
			text : pricing.valueAddDiscount.i18n('foss.pricing.addACity'),
			handler :function(){
				me.getValueAddDiscountCityGridShowWindow().show();
			} 
		},'-', {
			//添加营业部
			text : pricing.valueAddDiscount.i18n('foss.pricing.addBusinessDepartment'),
			handler :function(){
				me.getOrgWindow().show();
			} 
		},'-', {
			xtype:'checkbox',
			boxLabel : pricing.valueAddDiscount.i18n('foss.pricing.allNet'),
			listeners:{
				change:function(check,newValue,oldValue){
					if(newValue){
						check.up('window').startAllNet = pricing.valueAddDiscount.allNet;
						check.up('grid').getStore().removeAll();
						check.up('grid').dockedItems.items[2].items.items[0].setDisabled(true);
						check.up('grid').dockedItems.items[2].items.items[2].setDisabled(true);
						check.up('grid').dockedItems.items[2].items.items[4].setDisabled(true);
						check.up('grid').dockedItems.items[2].items.items[6].setDisabled(true);
					}else{
						check.up('window').startAllNet = null;
						check.up('grid').dockedItems.items[2].items.items[0].setDisabled(false);
						check.up('grid').dockedItems.items[2].items.items[2].setDisabled(false);
						check.up('grid').dockedItems.items[2].items.items[4].setDisabled(false);
						check.up('grid').dockedItems.items[2].items.items[6].setDisabled(false);
					}
					
				}
			}
		}];
		me.callParent([cfg]);
	}
});
//到达GRID
Ext.define('Foss.pricing.valueAddDiscount.ArriveGrid', {
   extend:'Ext.grid.Panel', 
   frame: true,
   title:pricing.valueAddDiscount.i18n('foss.pricing.reach'),
   sortableColumns:false,
   enableColumnHide:false,
   enableColumnMove:false,
   autoScroll:true,
   flex:1,
   //添加区域
   valueAddDiscountRegionGridShowWindow:null,
   getValueAddDiscountRegionGridShowWindow:function(){
		if(Ext.isEmpty(this.valueAddDiscountRegionGridShowWindow)){
   		     this.valueAddDiscountRegionGridShowWindow = Ext.create('Foss.pricing.valueAddDiscount.ValueAddDiscountRegionGridShowWindow');
   		     this.valueAddDiscountRegionGridShowWindow.parent = this;
   		     this.valueAddDiscountRegionGridShowWindow.type = pricing.valueAddDiscount.priceDiscountArrive;
	   	}
	   	return this.valueAddDiscountRegionGridShowWindow;
    },
     //添加城市
    valueAddDiscountCityGridShowWindow:null,
    getValueAddDiscountCityGridShowWindow:function(){
 		if(Ext.isEmpty(this.valueAddDiscountCityGridShowWindow)){
    		     this.valueAddDiscountCityGridShowWindow = Ext.create('Foss.pricing.valueAddDiscount.ValueAddDiscountCityGridShowWindow');
    		     this.valueAddDiscountCityGridShowWindow.parent = this;
    		     this.valueAddDiscountCityGridShowWindow.type = pricing.valueAddDiscount.priceDiscountArrive;
 	   	}
 	   	return this.valueAddDiscountCityGridShowWindow;
     },
    orgWindow:null,
    getOrgWindow:function(){
    	var me = this;
    	if(Ext.isEmpty(this.orgWindow)){
    		this.orgWindow = Ext.create('Foss.baseinfo.commonSelector.orgSelectorWindow',{
    			types:'ORG',
    			salesDepartment:'Y',
    			commitFun:function(){
    				var selections = this.getGridRecord();
    				if(selections.length==0){
    					dict.showErrorMes(pricing.valueAddDiscount.i18n('foss.pricing.pleaseSelectLeastOneData'));//请至少选择一条数据！
    					return;
    				}
    				var datas = new Array();
					for(var i = 0;i<selections.length;i++){
						var isHave = false;//是否已经存在
						me.getStore().each(function(record){
							if(record.get('arrvOrgId')==selections[i].get('id')){
								isHave = true;
							}
						});
						if(!isHave){//不存在则新增
							var data = {'arrvOrgId':selections[i].get('id'),'name':selections[i].get('name'),'arrvOrgCode':selections[i].get('code'),'arrvOrgTypeCode':pricing.valueAddDiscount.valueAddDiscountDetailTypeDept};
							datas.push(data);
						}
					};
					var oldData = me.getStore().getAt(0)//清掉不是一类的数据
 					if(!Ext.isEmpty(oldData)){
 						if(oldData.get('arrvOrgTypeCode')!=pricing.valueAddDiscount.valueAddDiscountDetailTypeDept){
 							me.getStore().removeAll();
 						}
 					}
    				me.getStore().add(datas);
    				this.close();
    			}
    		});
    		this.orgWindow.parent = this;
    	}
    	return this.orgWindow;
    },
   constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.columns = [{
				text : pricing.valueAddDiscount.i18n('i18n.pricingRegion.opra'),//操作
				xtype:'actioncolumn',
				align: 'center',
				width:80,
				items: [{
						iconCls: 'deppon_icons_delete',
		                tooltip: pricing.valueAddDiscount.i18n('foss.pricing.delete'),//删除
						width:42,
		                handler: function(grid, rowIndex, colIndex) {
		            		//获取选中的数据
		    				var record=grid.getStore().getAt(rowIndex);
		    				pricing.showQuestionMes(pricing.valueAddDiscount.i18n('foss.pricing.wantDeleteThisData'),function(e){//是否要删除这个数据？
		            			if(e=='yes'){//询问是否删除，是则发送请求
		            				grid.getStore().remove(record);
		            			}
		            		})
		                }
					}]
			  },{ 
	        	  header:pricing.valueAddDiscount.i18n('foss.pricing.optionalProducts')//可选产品
	        	  ,dataIndex: 'name'
	          },{ 
	        	  header:pricing.valueAddDiscount.i18n('foss.pricing.type')
	        	  ,dataIndex: 'arrvOrgTypeCode'
	              ,renderer:function(value){
	            	  if(pricing.valueAddDiscount.valueAddDiscountDetailTypeDept==value){
	            		  return pricing.valueAddDiscount.i18n('foss.pricing.dept');//部门
	            	  }else if(pricing.valueAddDiscount.valueAddDiscountDetailTypeRegion==value){
	            		  return pricing.valueAddDiscount.i18n('foss.pricing.region');//区域
	            	  }else if(pricing.valueAddDiscount.valueAddDiscountDetailTypeCity==value){
	            		  return pricing.valueAddDiscount.i18n('i18n.pricingRegion.city');//城市
	            	  }else{
	            		  return '';
	            	  }
	              }
		         }
		      ];
		me.selModel = Ext.create('Ext.selection.CheckboxModel',{//单选框
			mode:'MULTI',
			checkOnly:true
		});
		me.listeners = {
		    	scrollershow: function(scroller) {
		    		if (scroller && scroller.scrollEl) {
		    				scroller.clearManagedListeners(); 
		    				scroller.mon(scroller.scrollEl, 'scroll', scroller.onElScroll, scroller); 
		    		}
		    	}
		  };
		me.store = pricing.getStore(null,null,['arrvOrgId','name','arrvOrgCode','arrvOrgTypeCode'],[]),
		me.tbar = [{
			//删除
			text : pricing.valueAddDiscount.i18n('foss.pricing.delete'),
			handler :function(){
				var me = this;
				//获取选中的数据
				var selections = me.getSelectionModel().getSelection();
				if(selections.length<1){
					pricing.showErrorMes(pricing.valueAddDiscount.i18n('foss.pricing.pleaseSelectDataYouWantDelete'));//请选择要删除的数据！
					return;
				}
				pricing.showQuestionMes(pricing.valueAddDiscount.i18n('foss.pricing.wwantDeleteTheseData'),function(e){//是否要删除这些数据？
					if(e=='yes'){//询问是否删除，是则发送请求
						me.getStore().remove(selections);
					}
				});
			} 
		},'-', {
			//添加区域
			text : pricing.valueAddDiscount.i18n('foss.pricing.addARegion'),
			handler :function(){
				me.getValueAddDiscountRegionGridShowWindow().show();
			} 
		},'-', {
			//添加城市
			text : pricing.valueAddDiscount.i18n('foss.pricing.addACity'),
			handler :function(){
				me.getValueAddDiscountCityGridShowWindow().show();
			} 
		},'-', {
			//添加营业部
			text : pricing.valueAddDiscount.i18n('foss.pricing.addBusinessDepartment'),
			handler :function(){
				me.getOrgWindow().show();
			} 
		},'-', {
			xtype:'checkbox',
			boxLabel : pricing.valueAddDiscount.i18n('foss.pricing.allNet'),
			listeners:{
				change:function(check,newValue,oldValue){
					if(newValue){
						check.up('window').arrvAllNet = pricing.valueAddDiscount.allNet;
						check.up('grid').getStore().removeAll();
						check.up('grid').dockedItems.items[2].items.items[0].setDisabled(true);
						check.up('grid').dockedItems.items[2].items.items[2].setDisabled(true);
						check.up('grid').dockedItems.items[2].items.items[4].setDisabled(true);
						check.up('grid').dockedItems.items[2].items.items[6].setDisabled(true);
					}else{
						check.up('window').arrvAllNet = null;
						check.up('grid').dockedItems.items[2].items.items[0].setDisabled(false);
						check.up('grid').dockedItems.items[2].items.items[2].setDisabled(false);
						check.up('grid').dockedItems.items[2].items.items[4].setDisabled(false);
						check.up('grid').dockedItems.items[2].items.items[6].setDisabled(false);
					}
					
				}
			}
		}];
		me.callParent([cfg]);
	}
});
/**
 * 增值服务折扣方案明细查询FORM
 */
Ext.define('Foss.pricing.valueAddDiscount.QueryValueAddDiscountDetailForm', {
	extend : 'Ext.form.Panel',
	title: pricing.valueAddDiscount.i18n('i18n.pricingRegion.searchCondition'),//查询条件
	frame: true,
	collapsible: true,
    defaults : {
    	columnWidth : .5,
    	margin : '8 10 5 10',
    	anchor : '100%'
    },
    height :220,
	defaultType : 'textfield',
	layout:'column',
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		var all = {'id':'','name':pricing.valueAddDiscount.i18n('i18n.pricingRegion.all')};
		var allProduct = {'id':'ALL','code':'ALL','name':pricing.valueAddDiscount.i18n('foss.pricing.allProducts')};
//		var allDoodsType = {'id':'ALL','code':'ALL','name':pricing.valueAddDiscount.i18n('foss.pricing.allCargoTypes')};
		var productEntityList =  pricing.addAll(pricing.valueAddDiscount.productEntityList,all);
		var productEntityList =  pricing.addAll(pricing.valueAddDiscount.productEntityList,allProduct);
//		var goodTypeList =  pricing.addAll(pricing.valueAddDiscount.goodTypeList,all);
//		var goodTypeList =  pricing.addAll(pricing.valueAddDiscount.goodTypeList,allDoodsType);
		var goodTypeList = pricing.valueAddDiscount.goodTypeList;
		me.items  = [{
			name: 'deptOrgName',
		    fieldLabel:pricing.valueAddDiscount.i18n('foss.pricing.departure'),//出发
	        xtype : 'textfield'
		},{
			name: 'arrvOrgName',
		    fieldLabel:pricing.valueAddDiscount.i18n('foss.pricing.reach'),//到达
	        xtype : 'textfield'
		},{
			name: 'active',
			queryMode: 'local',
		    displayField: 'valueName',
		    valueField: 'valueCode',
		    editable:false,
		    value:'',
		    store:pricing.getStore(null,null,['valueCode','valueName']
		    ,[{'valueCode':'Y','valueName':pricing.valueAddDiscount.i18n('i18n.pricingRegion.active')}//激活
		    ,{'valueCode':'N','valueName':pricing.valueAddDiscount.i18n('i18n.pricingRegion.unActive')}//未激活
		    ,{'valueCode':'','valueName':pricing.valueAddDiscount.i18n('i18n.pricingRegion.all')}]),//全部
	        fieldLabel: pricing.valueAddDiscount.i18n('foss.pricing.status'),//折扣状态
	        xtype : 'combo'
		},{
			name: 'productId',
			queryMode: 'local',
		    displayField: 'name',
		    valueField: 'id',
		    value:'',
		    editable:false,
		    productRecord:null,//基础产品实体
		    store:pricing.getStore(null,'Foss.pricing.valueAddDiscount.ProductEntity',null
		    ,productEntityList),
	        fieldLabel: pricing.valueAddDiscount.i18n('foss.pricing.basicProducts'),//基础产品
	        xtype : 'combo'
		},{
			name: 'goodsTypeId',
			queryMode: 'local',
		    displayField: 'name',
		    valueField: 'id',
		    editable:false,
		    goodsTypeRecord:null,
		    value:'',
		    store:pricing.getStore(null,'Foss.pricing.valueAddDiscount.GoodsTypeEntity',null
		    ,goodTypeList),
	        fieldLabel: pricing.valueAddDiscount.i18n('foss.pricing.cargoType'),//货物类型
	        xtype : 'combo'
		}];
		me.fbar = [{
			xtype : 'button', 
			width:70,
			text : pricing.valueAddDiscount.i18n('foss.pricing.reset'),//
			handler : function() {
				me.getForm().reset();
			}
		},{
				xtype : 'button', 
				width:70,
				text : pricing.valueAddDiscount.i18n('i18n.pricingRegion.search'),
				margin:'0 0 0 375',
				cls:'yellow_button',
				handler : function() {
					if(me.getForm().isValid()){
						var grid = me.up('panel').getValueAddDiscountDetailGridPanel();
						grid.getStore().load();
					}
				}
			}]
		me.callParent([cfg]);
	}
});
/**
 * 增值服务折扣方案明细列表
 */
Ext.define('Foss.pricing.valueAddDiscount.ValueAddDiscountDetailGridPanel', {
	extend: 'Ext.grid.Panel',
	title : pricing.valueAddDiscount.i18n('i18n.pricingRegion.searchResults'),//查询结果
	frame: true,
	flex:1,
	autoScroll:true,
	isShow:false,//是否是查看
    sortableColumns:false,
    enableColumnHide:false,
    enableColumnMove:false,
	stripeRows : true, // 交替行效果
	selType : "rowmodel", // 选择类型设置为：行选择
	emptyText: pricing.valueAddDiscount.i18n('foss.pricing.theQueryResultIsEmpty'),//查询结果为空
	//得到bbar（分页）
	pagingToolbar : null,
	getPagingToolbar : function() {
		if (Ext.isEmpty(this.pagingToolbar)) {
			this.pagingToolbar = Ext.create('Deppon.StandardPaging', {
				store : this.store,
				pageSize : 5
			});
		}
		return this.pagingToolbar;
	},
	//修改明细信息
	valueAddDiscountDeatilUpdateWindow : null,
	getValueAddDiscountDeatilUpdateWindow : function() {
		if (Ext.isEmpty(this.valueAddDiscountDeatilUpdateWindow)) {
			this.valueAddDiscountDeatilUpdateWindow = Ext.create('Foss.pricing.valueAddDiscount.ValueAddDiscountDeatilUpdateWindow');
			this.valueAddDiscountDeatilUpdateWindow.parent = this;
		}
		return this.valueAddDiscountDeatilUpdateWindow;
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.columns = [{xtype: 'rownumberer',
			width:40,
			text : pricing.valueAddDiscount.i18n('i18n.pricingRegion.num')//序号
		},{
			text : pricing.valueAddDiscount.i18n('i18n.pricingRegion.opra'),//操作
			//dataIndex : 'id',
			hidden:config.isShow,
			xtype:'actioncolumn',
			align: 'center',
			width:80,
			items: [{
				iconCls: 'deppon_icons_edit',
                tooltip: pricing.valueAddDiscount.i18n('foss.pricing.update'),//修改
				width:42,
                handler: function(grid,rowIndex,colIndex) {
                	//获取选中的数据
    				var record=grid.getStore().getAt(rowIndex);
                	var priceValuationId = record.get('priceValuationId');//计费规则ID
    				var params = {'priceDiscountVo':{'priceDiscountDto':{'priceValuationId':priceValuationId}}};
    				var successFun = function(json){
    					var updateWindow = me.getValueAddDiscountDeatilUpdateWindow();//获得修改窗口
    					updateWindow.priceDiscountDto = json.priceDiscountVo.priceDiscountDto;//增值服务折扣方案明细
    					updateWindow.show();//显示修改窗口
    				};
    				var failureFun = function(json){
    					if(Ext.isEmpty(json)){
    						pricing.showErrorMes(pricing.valueAddDiscount.i18n('foss.pricing.requestTimedOut'));//请求超时
    					}else{
    						pricing.showErrorMes(json.message);
    					}
    				};
    				var url = pricing.realPath('selectValueAddPriceValuation.action');
    				pricing.requestJsonAjax(url,params,successFun,failureFun);
                }
			},{
				iconCls: 'deppon_icons_delete',
                tooltip: pricing.valueAddDiscount.i18n('foss.pricing.delete'),//删除
				width:42,
                handler: function(grid, rowIndex, colIndex) {
            		//获取选中的数据
    				var record=grid.getStore().getAt(rowIndex);
            		pricing.showQuestionMes(pricing.valueAddDiscount.i18n('foss.pricing.wantDeleteThisDiscountDetails'),function(e){//是否要删除这个折扣明细？
            			if(e=='yes'){//询问是否删除，是则发送请求
            				var priceValuationId = record.get('priceValuationId');//计费规则ID
            				var params = {'priceDiscountVo':{'priceDiscountDto':{'priceValuationId':priceValuationId}}};
            				var successFun = function(json){
            					pricing.showInfoMes(json.message);
            					me.getPagingToolbar().moveFirst();
            				};
            				var failureFun = function(json){
            					if(Ext.isEmpty(json)){
            						pricing.showErrorMes(pricing.valueAddDiscount.i18n('i18n.pricingRegion.requestTimeOut'));//请求超时！
            					}else{
            						pricing.showErrorMes(json.message);
            					}
            				};
            				var url = pricing.realPath('deleteValueAddDiscountProgramItem.action');
            				pricing.requestJsonAjax(url,params,successFun,failureFun);
            			}
            		})
                }
			}]
		},{
			text : pricing.valueAddDiscount.i18n('foss.pricing.departure'),//出发
			dataIndex : 'deptOrgName'
		},{
			text : pricing.valueAddDiscount.i18n('foss.pricing.reach'),//到达
			dataIndex : 'arrvOrgName'
		},{
			text : pricing.valueAddDiscount.i18n('foss.pricing.startingType'),//出发类型
			dataIndex : 'deptOrgTypeCode',
			renderer:function(value){
				if(value == pricing.valueAddDiscount.valueAddDiscountDetailTypeDept){
					return pricing.valueAddDiscount.i18n('foss.pricing.dept');//部门
				}else if(value == pricing.valueAddDiscount.valueAddDiscountDetailTypeRegion){
					return pricing.valueAddDiscount.i18n('foss.pricing.region');//区域
				}else if(value == pricing.valueAddDiscount.valueAddDiscountDetailTypeCity){
					return pricing.valueAddDiscount.i18n('i18n.pricingRegion.city');//城市
				}else if(value == pricing.valueAddDiscount.valueAddDiscountDetailTypeAllNet){
					return pricing.valueAddDiscount.i18n('foss.pricing.allNet');//全网
				}else{
					return '';
				}
			}
		},{
			text : pricing.valueAddDiscount.i18n('foss.pricing.reachTheType'),//到达类型
			dataIndex : 'arrvOrgTypeCode',
			renderer:function(value){
				if(value == pricing.valueAddDiscount.valueAddDiscountDetailTypeDept){
					return pricing.valueAddDiscount.i18n('foss.pricing.dept');//部门
				}else if(value == pricing.valueAddDiscount.valueAddDiscountDetailTypeRegion){
					return pricing.valueAddDiscount.i18n('foss.pricing.region');//区域
				}else if(value == pricing.valueAddDiscount.valueAddDiscountDetailTypeCity){
					return pricing.valueAddDiscount.i18n('i18n.pricingRegion.city');//城市
				}else if(value == pricing.valueAddDiscount.valueAddDiscountDetailTypeAllNet){
					return pricing.valueAddDiscount.i18n('foss.pricing.allNet');//全网
				}else{
					return '';
				}
			}
		},{
			text : pricing.valueAddDiscount.i18n('foss.pricing.beginningOfTheRange'),//开始范围
			dataIndex : 'leftRange'
		},{
			text : pricing.valueAddDiscount.i18n('foss.pricing.endOfTheRange'),//结束范围
			dataIndex : 'rightRange'
		},{
			text : pricing.valueAddDiscount.i18n('foss.pricing.discountRules'),//折扣规则
			dataIndex : 'caculateType',
			renderer:function(value){
				return pricing.changeCodeToName(pricing.valueAddDiscount.caculateType,value);
			}
		},{
			text : pricing.valueAddDiscount.i18n('foss.pricing.product'),//产品
			dataIndex : 'productName'
		},{
			text : pricing.valueAddDiscount.i18n('foss.pricing.theDiscountRate'),//折扣率
			dataIndex : 'discountRate'
		},{
			text : pricing.valueAddDiscount.i18n('foss.pricing.theLowestVotesdirectRelief'),//最低减免
			dataIndex : 'minFee'
		},{
			text : pricing.valueAddDiscount.i18n('foss.pricing.theUpLestVotesdirectRelief'),//最高减免
			dataIndex : 'maxFee'
		},{
			text :pricing.valueAddDiscount.i18n('foss.pricing.status'),//状态
			dataIndex : 'active',
			width:50,
			renderer:function(value){
				if(value=='Y'){//'Y'表示激活
					return pricing.valueAddDiscount.i18n('i18n.pricingRegion.active');
				}else if(value=='N'){//'N'表示未激活
					return  pricing.valueAddDiscount.i18n('i18n.pricingRegion.unActive');
				}else{
					return '';
				}
			}
		}];
		me.store = Ext.create('Foss.pricing.valueAddDiscount.valueAddDiscountDetailStore',{
			autoLoad : false,
			pageSize : 5,
			listeners: {
				beforeload: function(store, operation, eOpts){
					var queryForm = me.up().getQueryValueAddDiscountDetailForm();
					var marketId = me.up('window').marketingEventEntity.id;
					if(queryForm!=null){
						Ext.apply(operation,{
							params : {
								'priceDiscountVo.priceDiscountDto.deptOrgName':queryForm.getForm().findField('deptOrgName').getValue(),//出发
								'priceDiscountVo.priceDiscountDto.arrvOrgName':queryForm.getForm().findField('arrvOrgName').getValue(),//到达
								'priceDiscountVo.priceDiscountDto.active':queryForm.getForm().findField('active').getValue(),//状态
								'priceDiscountVo.priceDiscountDto.productId':queryForm.getForm().findField('productId').getValue(),//产品ID
								'priceDiscountVo.priceDiscountDto.goodsTypeId':queryForm.getForm().findField('goodsTypeId').getValue(),//货物类型ID
								'priceDiscountVo.priceDiscountDto.marketId':marketId//增值服务折扣方案主信息ID
							}
						});	
					}
				}
		    }
		});
		me.listeners = {//消除出现滚动条之后，却不能用的BUG
	    	scrollershow: function(scroller) {
	    		if (scroller && scroller.scrollEl) {
	    				scroller.clearManagedListeners(); 
	    				scroller.mon(scroller.scrollEl, 'scroll', scroller.onElScroll, scroller); 
	    		}
	    	}
	    },
		me.selModel = Ext.create('Ext.selection.CheckboxModel',{//带选择框
					mode:'MULTI',
					checkOnly:true
				});
		me.bbar = me.getPagingToolbar();
		me.callParent([cfg]);
	}
});
/**
 * 增值服务折扣方案明细-修改(单独修改)
 */
Ext.define('Foss.pricing.valueAddDiscount.ValueAddDiscountDeatilUpdateWindow',{
	extend : 'Ext.window.Window',
	title : pricing.valueAddDiscount.i18n('foss.pricing.definitionOfPriceDiscounts'),//价格折扣定义
	closable : true,
	modal : true,
	resizable:false,
	priceDiscountDto:null,//明细
	parent:null,//(Foss.pricing.valueAddDiscount.ValueAddDiscountDetailGridPanel)
	closeAction : 'hide',
	width :450,
	height :500,	
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	listeners:{
		beforehide:function(me){
			me.getValueAddDiscountDetailEditFormPanel().getForm().reset();
		},
		beforeshow:function(me){
			me.getValueAddDiscountDetailEditFormPanel().getForm().loadRecord(new Foss.pricing.valueAddDiscount.ValueAddDiscountDto(me.priceDiscountDto));//设置值
		}
	},
	//维护折扣信息FORM
	valueAddDiscountDetailEditFormPanel:null,
	getValueAddDiscountDetailEditFormPanel:function(){
		if(Ext.isEmpty(this.valueAddDiscountDetailEditFormPanel)){
    		this.valueAddDiscountDetailEditFormPanel = Ext.create('Foss.pricing.valueAddDiscount.ValueAddDiscountDetailEditFormPanel');
    		this.valueAddDiscountDetailEditFormPanel.getDockedItems()[0].items.items[0].hide();
    	}
    	return this.valueAddDiscountDetailEditFormPanel;
	},
	//修改折扣明细
	commitValueAddDiscountDetailEdit:function(){
		var me = this;
		//获取修改的原始数据
		var priceDiscountDto = new Foss.pricing.valueAddDiscount.ValueAddDiscountDto(me.priceDiscountDto);
		var form = me.getValueAddDiscountDetailEditFormPanel().getForm();
        if(form.isValid()){
        	form.updateRecord(priceDiscountDto);
        	var goodsTypeCode = form.findField('goodsTypeId').goodsTypeRecord.get('code');
        	priceDiscountDto.set('goodsTypeCode',goodsTypeCode);
            var productCode = form.findField('productId').productRecord.get('code');
            priceDiscountDto.set('productCode',productCode);
           //如果开始范围大于结束范围！
            if(priceDiscountDto.get('leftRange')>priceDiscountDto.get('rightRange')){
            	pricing.showWoringMessage(pricing.valueAddDiscount.i18n('foss.pricing.leftRangeMoreRanRightRange'));//开始范围不能大于结束范围！
            	return;
            }
            //如果直接减免大于最高减免！
            if(priceDiscountDto.get('minFee')>priceDiscountDto.get('maxFee')){
            	pricing.showWoringMessage(pricing.valueAddDiscount.i18n('foss.pricing.minFeeMoreRanMaxFee'));//直接减免不能大于最高减免！
            	return;
            }
            if(priceDiscountDto.get('discountRate')<100&&priceDiscountDto.get('minFee')>0){
            	pricing.showWoringMessage(pricing.valueAddDiscount.i18n('foss.pricing.minFeeAnddiscountRateEqcelZero'));//直接减免和折扣率不能同时大于0！
            	return;
            }
            if(priceDiscountDto.get('discountRate')==100&&priceDiscountDto.get('minFee')==0){
            	pricing.showWoringMessage(pricing.valueAddDiscount.i18n('foss.pricing.minFeeAnddiscountRateDengZero'));//直接减免和折扣率不能同时等于0！
            	return;
            }
			var params = {'priceDiscountVo':{'priceDiscountDto':priceDiscountDto.data}};
			var successFun = function(json){
				pricing.showInfoMes(json.message);
				me.close();
				me.parent.getPagingToolbar().moveFirst();
			};
			var failureFun = function(json){
				if(Ext.isEmpty(json)){
					pricing.showErrorMes(pricing.valueAddDiscount.i18n('i18n.pricingRegion.requestTimeOut'));//请求超时！
				}else{
					pricing.showErrorMes(json.message);
				}
			};
			var url = pricing.realPath('updateValueAddDiscountProgramItem.action');
			pricing.requestJsonAjax(url,params,successFun,failureFun);
        }		
	},
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [me.getValueAddDiscountDetailEditFormPanel()];
		me.fbar = [{
			text : pricing.valueAddDiscount.i18n('i18n.pricingRegion.returnGrid'),
			handler :function(){
				me.close();
			} 
		},{
			text : pricing.valueAddDiscount.i18n('foss.pricing.reset'),
			handler :function(){
				me.getValueAddDiscountEditFormPanel().getForm().loadRecord(new Foss.pricing.valueAddDiscount.ValueAddDiscountDto(me.priceDiscountDto));//设置值
			} 
		},{
			text : pricing.valueAddDiscount.i18n('foss.pricing.save'),//保存
			margin:'0 0 0 185',
			cls:'yellow_button',
			handler :function(){
				me.commitValueAddDiscountDetailEdit();
			} 
		}];
		me.callParent([cfg]);
	}
});




/**
 * 修改弹窗-设置中止时间
 */
Ext.define('Foss.pricing.valueAddDiscount.ValueAddDiscountEndTimeWindow',{
	extend : 'Ext.window.Window',
	title : pricing.valueAddDiscount.i18n('i18n.pricingRegion.setEndTime'),
	closable : true,
	modal : true,
	resizable:false,
	closeAction : 'hide',
	parent:null,//(Foss.pricing.valueAddDiscount.PricingBasicValuationGridPanel)
	width :250,
	height :150,
	selection:null,//选择的当行的数据
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	listeners:{
		beforehide:function(me){
			me.getEndTimeField().reset();//清除上次的数据
		},
		beforeshow:function(me){
			var minValue = me.selection.get('beginTime')>new Date(pricing.valueAddDiscount.tomorrowTime)?me.selection.get('beginTime'):new Date(pricing.valueAddDiscount.tomorrowTime);
			var maxValue = new Date(me.selection.get('endTime'));
			me.getEndTimeField().setMinValue(minValue);
			me.getEndTimeField().setMaxValue(maxValue);
		}
	},
	endTimeField:null,//设置失效时间的datefield
     //得到失效日期元素
	getEndTimeField:function(selection){
		if(Ext.isEmpty(this.endTimeField)){
    		this.endTimeField = Ext.create('Ext.form.field.Date',{
    			//minValue: minValue,
    			//maxValue: maxValue,
    	    	labelWidth:60,
    			fieldLabel: pricing.valueAddDiscount.i18n('i18n.pricingRegion.endTime'),//失效日期
    			name:'endTime',
    			editable:false
    		});
    	}
    	return this.endTimeField;
	},
    //设置失效日期
	commintEndTime:function(){
    	var me = this;
    	if(me.getEndTimeField().isValid()){
    		var time = me.getEndTimeField().getValue().getTime();
        	time = time+(24*60*60*1000-1);//设置时间为当天的最后一毫秒
        	if(time<=me.selection.get('beginTime').getTime()){
        		pricing.showWoringMessage(pricing.valueAddDiscount.i18n('foss.pricing.theClosingDateIsGreaterThanStartDate'));//截止日期要大于起始日期！
    			return;
        	}
        	var params = {'priceDiscountVo':{'marketingEventEntity':{'id':me.selection.get('id'),'endTime':new Date(time)}}};
        	var successFun = function(json){
    			pricing.showInfoMes(json.message);
    			me.parent.getPagingToolbar().moveFirst();
    			me.close();
    		};
    		var failureFun = function(json){
    			if(Ext.isEmpty(json)){
    				pricing.showErrorMes(pricing.valueAddDiscount.i18n('i18n.pricingRegion.requestTimeOut'));
    			}else{
    				pricing.showErrorMes(json.message);
    			}
    		};
    		var url = pricing.realPath('stopValueAddDiscountProgram.action');
    		pricing.requestJsonAjax(url,params,successFun,failureFun);
    	}
    },
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [me.getEndTimeField(config.selection)];
		me.fbar = [{
			text : pricing.valueAddDiscount.i18n('i18n.pricingRegion.cancel'),//取消
			handler :function(){
				me.close();
			} 
		},{
			text : pricing.valueAddDiscount.i18n('i18n.pricingRegion.determine'),//确认
			margin:'0 0 0 55',
			cls:'yellow_button',
			handler :function(){
				me.commintEndTime();
			} 
		}];
		me.callParent([cfg]);
	}
});


/**
 * 查询增值服务
 */
Ext.define('Foss.pricing.valueAddDiscount.DiscountPricingWindow',{
	extend : 'Ext.window.Window',
	title : pricing.valueAddDiscount.i18n('foss.pricing.valueAddedServiceOptions'),//增值服务选择
	closable : true,
	modal : true,
	resizable:false,
	closeAction : 'hide',
	parent:null,//(Foss.pricing.valueAddDiscount.ValueAddDiscountEditFormPanel)
	width :350,
	height :350,
	selection:null,//选择的当行的数据
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	listeners:{
		beforehide:function(me){
			me.getDiscountPricingGridPanel().getStore().removeAll();//清除上次的数据
		},
		beforeshow:function(me){
			var me = this;
	    	var params = {};
	    	var successFun = function(json){
				var priceEntityList = json.priceDiscountVo.priceEntityList;
				me.getDiscountPricingGridPanel().getStore().add(priceEntityList);
			};
			var failureFun = function(json){
				if(Ext.isEmpty(json)){
					pricing.showErrorMes(pricing.valueAddDiscount.i18n('i18n.pricingRegion.requestTimeOut'));
				}else{
					pricing.showErrorMes(json.message);
				}
			};
			var url = pricing.realPath('queryAllPricingEntries.action');
			pricing.requestJsonAjax(url,params,successFun,failureFun);
		}
	},
	discountPricingGridPanel:null,//查询增值服务类型
     //查询增值服务类型
	getDiscountPricingGridPanel:function(){
		if(Ext.isEmpty(this.discountPricingGridPanel)){
			this.discountPricingGridPanel = Ext.create('Foss.pricing.valueAddDiscount.DiscountPricingGridPanel');
		}
    	return this.discountPricingGridPanel;
	},
    //设置增值服务
	commintSelectPriceEntity:function(){
		var me = this;
    	var selections = me.getDiscountPricingGridPanel().getSelectionModel().getSelection();
    	if(selections.length!=1){
    		pricing.showWoringMessage(pricing.valueAddDiscount.i18n('foss.pricing.pleaseSelectOneDatea'));
    	}
    	me.parent.getForm().findField('pricingEntryName').setValue(selections[0].get('name'));
    	me.parent.getForm().findField('pricingEntryName').pricingEntryId = selections[0].get('id');
    	me.parent.getForm().findField('pricingEntryName').pricingEntryCode = selections[0].get('code');
    	me.close();
    },
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [me.getDiscountPricingGridPanel()];
		me.fbar = [{
			text : pricing.valueAddDiscount.i18n('i18n.pricingRegion.cancel'),//取消
			handler :function(){
				me.close();
			} 
		},{
			text : pricing.valueAddDiscount.i18n('i18n.pricingRegion.determine'),//确认
			margin:'0 0 0 155',
			cls:'yellow_button',
			handler :function(){
				me.commintSelectPriceEntity();
			} 
		}];
		me.callParent([cfg]);
	}
});
/**
 * 增值服务选择
 */
Ext.define('Foss.pricing.valueAddDiscount.DiscountPricingGridPanel', {
	extend: 'Ext.grid.Panel',
	title : pricing.valueAddDiscount.i18n('foss.pricing.valueAddedServiceOptions'),//增值服务选择
	frame: true,
	flex:1,
	aotoScroll:true,
    sortableColumns:false,
    enableColumnHide:false,
    enableColumnMove:false,
	stripeRows : true, // 交替行效果
	selType : "rowmodel", // 选择类型设置为：行选择
	emptyText: pricing.valueAddDiscount.i18n('foss.pricing.theQueryResultIsEmpty'),//查询结果为空
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.columns = [{xtype: 'rownumberer',
			width:40,
			text : pricing.valueAddDiscount.i18n('i18n.pricingRegion.num')//序号
		},{
			text : pricing.valueAddDiscount.i18n('foss.pricing.valueAddedServiceNumber'),//增值服务编号
			dataIndex : 'code'
		},{
			text : pricing.valueAddDiscount.i18n('foss.pricing.nameValueAddedServices'),//增值服务名称
			dataIndex : 'name'
		}];
		me.store = pricing.getStore(null,'Foss.pricing.valueAddDiscount.PriceEntityModel',null,[]);
		me.listeners = {//消除出现滚动条之后，却不能用的BUG
	    	scrollershow: function(scroller) {
	    		if (scroller && scroller.scrollEl) {
	    				scroller.clearManagedListeners(); 
	    				scroller.mon(scroller.scrollEl, 'scroll', scroller.onElScroll, scroller); 
	    		}
	    	}
	    },
		me.selModel = Ext.create('Ext.selection.CheckboxModel',{//带选择框
					mode:'SINGLE',
					checkOnly:true
				});
		me.callParent([cfg]);
	}
});
/**
 * 区域Store
 */
Ext.define('Foss.pricing.valueAddDiscount.ValueAddDiscountAreaStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.pricing.valueAddDiscount.AreaModel',
	autoLoad:false,//不需要自动加载
	pageSize:5,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url : pricing.realPath('searchRegionByCondition.action'),
		reader : {
			type : 'json',
			root : 'regionVo.regionEntityList',
			totalProperty : 'totalCount'
		}
	}
});
/**
 * 区域列表
 */
Ext.define('Foss.pricing.valueAddDiscount.ValueAddDiscountAreaGridPanel', {
	extend: 'Ext.grid.Panel',
	title : pricing.valueAddDiscount.i18n('foss.pricing.region'),//区域
	frame: true,
	flex:1,
    sortableColumns:false,
    enableColumnHide:false,
    enableColumnMove:false,
	stripeRows : true, // 交替行效果
	selType : "rowmodel", // 选择类型设置为：行选择
	getPagingToolbar : function() {
		if (Ext.isEmpty(this.pagingToolbar)) {
			this.pagingToolbar = Ext.create('Deppon.StandardPaging', {
				store : this.store,
				pageSize : 5
			});
		}
		return this.pagingToolbar;
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.columns = [{xtype: 'rownumberer',
			width:40,
			text : pricing.valueAddDiscount.i18n('i18n.pricingRegion.num')//序号
		},{
			text : pricing.valueAddDiscount.i18n('i18n.pricingRegion.regionNum'),//区域编号
			dataIndex : 'regionCode'
		},{
			text : pricing.valueAddDiscount.i18n('i18n.pricingRegion.regionName'),//区域名称
			dataIndex : 'regionName'
		}];
		me.store = Ext.create('Foss.pricing.valueAddDiscount.ValueAddDiscountAreaStore',{
			autoLoad:false,//不需要自动加载
			listeners: {
				beforeload: function(store, operation, eOpts){
					var queryForm = me.up('window').getValueAddDiscountSearchRegionForm();
					if(queryForm!=null){
						Ext.apply(operation,{
							params : {
								'regionVo.regionEntity.regionName' : "%"+queryForm.getForm().findField('regionName').getValue()+"%",//区域名称
								'regionVo.regionEntity.regionCode' : queryForm.getForm().findField('regionCode').getValue(),//区域编号
								'regionVo.regionEntity.active' : 'Y',//之查询激活的
								//查询的只是价格区域
								'regionVo.regionEntity.regionNature':pricing.valueAddDiscount.PRICING_REGION
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
		me.selModel = Ext.create('Ext.selection.CheckboxModel',{//单选框
					mode:'MULTI',
					checkOnly:true
				});
		me.bbar = me.getPagingToolbar();
		me.callParent([cfg]);
	}
});

/**
 * 区域查询WINDOW
 */
Ext.define('Foss.pricing.valueAddDiscount.ValueAddDiscountRegionGridShowWindow',{
	extend : 'Ext.window.Window',
	title : pricing.valueAddDiscount.i18n('foss.pricing.queryRegion'),//查询区域
	closable : true,
	parent:null,//(Foss.pricing.valueAddDiscount.StartGrid,Foss.pricing.valueAddDiscount.ArriveGrid)
	modal : true,
	type:null,//是出发还是到达
	resizable:false,
	closeAction : 'hide',
	width :590,
	height :520,
	textField:null,//对应的是哪个textField区点击选择
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	listeners:{
		beforehide:function(me){//在隐藏之前清除掉所有加载的数据
			me.getValueAddDiscountSearchRegionForm().getForm().reset();
			me.textField = null;
			
		},
		beforeshow:function(me){
			me.getValueAddDiscountRegionGridPanel().getPagingToolbar().moveFirst();//确保查询第一页的数据
		}
	},
	//返回列表
	returnGrid:function(){
		var me = this;
		me.close();
	},
	//选择区域
	selectRegion:function(){
		var me = this;
		var selections = me.getValueAddDiscountRegionGridPanel().getSelectionModel().getSelection();//获取选择的数据
		if(selections.length==0){
			pricing.showWoringMessage(pricing.valueAddDiscount.i18n('foss.pricing.pleaseSelectRegion'));//请选择一个区域！
			return;
		}
		var datas = new Array();
		if(me.type==pricing.valueAddDiscount.priceDiscountStart){
			for(var i = 0;i<selections.length;i++){
				var isHave = false;//是否已经存在
				me.parent.getStore().each(function(record){
					if(record.get('deptOrgId')==selections[i].get('id')){
						isHave = true;
					}
				});
				if(!isHave){//不存在则新增
					var data = {'deptOrgId':selections[i].get('id'),'name':selections[i].get('regionName'),'deptOrgCode':selections[i].get('regionCode'),'deptOrgTypeCode':pricing.valueAddDiscount.valueAddDiscountDetailTypeRegion};
					datas.push(data);
				}
			}
			var oldData = me.parent.getStore().getAt(0)//清掉不是一类的数据
			if(!Ext.isEmpty(oldData)){
				if(oldData.get('deptOrgTypeCode')!=pricing.valueAddDiscount.valueAddDiscountDetailTypeRegion){
					me.parent.getStore().removeAll();
				}
			}
		}else if(me.type==pricing.valueAddDiscount.priceDiscountArrive){
			for(var i = 0;i<selections.length;i++){
				var isHave = false;//是否已经存在
				me.parent.getStore().each(function(record){
					if(record.get('arrvOrgId')==selections[i].get('id')){
						isHave = true;
					}
				});
				if(!isHave){//不存在则新增
					var data = {'arrvOrgId':selections[i].get('id'),'name':selections[i].get('regionName'),'arrvOrgCode':selections[i].get('regionCode'),'arrvOrgTypeCode':pricing.valueAddDiscount.valueAddDiscountDetailTypeRegion};
					datas.push(data);
				}
			}
			var oldData = me.parent.getStore().getAt(0)//清掉不是一类的数据
			if(!Ext.isEmpty(oldData)){
				if(oldData.get('arrvOrgTypeCode')!=pricing.valueAddDiscount.valueAddDiscountDetailTypeRegion){
					me.parent.getStore().removeAll();
				}
			}
		}
		me.parent.getStore().add(datas);
		me.close();
	},
	//查询的FORM
	priceDiscountSearchRegionForm:null,
	getValueAddDiscountSearchRegionForm:function(){
		if(Ext.isEmpty(this.priceDiscountSearchRegionForm)){
    		this.priceDiscountSearchRegionForm = Ext.create('Foss.pricing.valueAddDiscount.ValueAddDiscountSearchRegionForm');
    	}
		return this.priceDiscountSearchRegionForm;
	},
	//区域的GRID
	priceDiscountRegionGridPanel:null,
	getValueAddDiscountRegionGridPanel:function(){
		if(Ext.isEmpty(this.priceDiscountRegionGridPanel)){
    		this.priceDiscountRegionGridPanel = Ext.create('Foss.pricing.valueAddDiscount.ValueAddDiscountAreaGridPanel');
    	}
		return this.priceDiscountRegionGridPanel;
	}, 
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [me.getValueAddDiscountSearchRegionForm(), me.getValueAddDiscountRegionGridPanel()];
		me.fbar = [{
			text : pricing.valueAddDiscount.i18n('i18n.pricingRegion.returnGrid'),//返回列表
			handler :function(){
				me.close();
			} 
		},{
			text : pricing.valueAddDiscount.i18n('i18n.pricingRegion.determine'),//确定
			cls:'yellow_button',
			margin:'0 0 0 365',
			handler :function(){
				me.selectRegion();
			} 
		}];
		me.callParent([cfg]);
	}
});
/**
 * 查询区域
 */
Ext.define('Foss.pricing.valueAddDiscount.ValueAddDiscountSearchRegionForm', {
	extend : 'Ext.form.Panel',
	title: pricing.valueAddDiscount.i18n('i18n.pricingRegion.searchCondition'),
	frame: true,
	collapsible: true,
    defaults : {
    	columnWidth : .4,
    	margin : '8 10 5 10',
    	labelSeparator:'',
    	labelWidth:60,
    	anchor : '100%'
    },
    height :90,
	defaultType : 'textfield',
	layout:'column',
	constructor : function(config) {//构造器
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [{
			xtype:'textfield',
			fieldLabel:pricing.valueAddDiscount.i18n('i18n.pricingRegion.regionName'),//区域名称
			name:'regionName'
		},{
			xtype:'textfield',
			fieldLabel:pricing.valueAddDiscount.i18n('i18n.pricingRegion.regionCode'),//区域编码
			name:'regionCode'
		},{
			xtype : 'container',
			margin : '0 0 0 0',
			columnWidth : .2,
			items : [{
				xtype : 'button', 
				width:70,
				cls:'yellow_button',
				text : pricing.valueAddDiscount.i18n('i18n.pricingRegion.search'),//查询按钮
				handler : function() {
					if(me.getForm().isValid()){
						var grid  = me.up('window').getValueAddDiscountRegionGridPanel();
						grid.getPagingToolbar().moveFirst();//用分页的moveFirst()方法
					}
				}
			}]
		}];
		me.callParent([cfg]);
	}
});



/**
 * 城市Store
 */
Ext.define('Foss.pricing.valueAddDiscount.ValueAddDiscountCityStore', {
	extend : 'Ext.data.Store',
	fields : ['id','name','code'],
	autoLoad:false,//不需要自动加载
	pageSize:5,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url : pricing.realPath('searchCityByName.action'),
		reader : {
			type : 'json',
			root : 'priceDiscountVo.administrativeRegionsEntityList',
			totalProperty : 'totalCount'
		}
	}
});
/**
 * 城市
 */
Ext.define('Foss.pricing.valueAddDiscount.ValueAddDiscountCityGridPanel', {
	extend: 'Ext.grid.Panel',
	title : pricing.valueAddDiscount.i18n('i18n.pricingRegion.city'),//城市
	frame: true,
	flex:1,
    sortableColumns:false,
    enableColumnHide:false,
    enableColumnMove:false,
	stripeRows : true, // 交替行效果
	selType : "rowmodel", // 选择类型设置为：行选择
	//得到bbar
	//得到bbar（分页）
	pagingToolbar : null,
	getPagingToolbar : function() {
		if (Ext.isEmpty(this.pagingToolbar)) {
			this.pagingToolbar = Ext.create('Deppon.StandardPaging', {
				store : this.store,
				pageSize : 5
			});
		}
		return this.pagingToolbar;
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.columns = [{xtype: 'rownumberer',
			width:40,
			text : pricing.valueAddDiscount.i18n('i18n.pricingRegion.num')//序号
		},{
			text : pricing.valueAddDiscount.i18n('foss.pricing.cityName'),//城市名称
			dataIndex : 'name'
		},{
			text : pricing.valueAddDiscount.i18n('foss.pricing.cityCode'),//城市编号
			dataIndex : 'code'
		}];
		me.store = Ext.create('Foss.pricing.valueAddDiscount.ValueAddDiscountCityStore',{
			autoLoad:false,//不需要自动加载
			listeners: {
				beforeload: function(store, operation, eOpts){
					var queryForm = me.up('window').getValueAddDiscountSearchCityForm();
					if(queryForm!=null){
						Ext.apply(operation,{
							params : {
								'priceDiscountVo.administrativeRegionsEntity.name' : queryForm.getForm().findField('name').getValue(),//城市名称
								 //查询的只是城市
								'priceDiscountVo.administrativeRegionsEntity.degree':pricing.valueAddDiscount.Pricing_City
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
		me.selModel = Ext.create('Ext.selection.CheckboxModel',{//单选框
					mode:'MULTI',
					checkOnly:true
				});
		me.bbar = me.getPagingToolbar();
		me.callParent([cfg]);
	}
});

/**
 * 城市查询WINDOW
 */
Ext.define('Foss.pricing.valueAddDiscount.ValueAddDiscountCityGridShowWindow',{
	extend : 'Ext.window.Window',
	parent:null,//(Foss.pricing.valueAddDiscount.StartGrid,Foss.pricing.valueAddDiscount.ArriveGrid)
	title : pricing.valueAddDiscount.i18n('i18n.pricingRegion.city'),//查询出发地/目的地
	closable : true,
	type:null,//是出发还是到达
	modal : true,
	resizable:false,
	closeAction : 'hide',
	width :590,
	height :520,
	textField:null,//对应的是哪个textField区点击选择
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	listeners:{
		beforehide:function(me){//在隐藏之前清除掉所有加载的数据
			me.getValueAddDiscountSearchCityForm().getForm().reset();
			me.textField = null;
			
		},
		beforeshow:function(me){
			me.getValueAddDiscountCityGridPanel().getPagingToolbar().moveFirst();//确保查询第一页的数据
		}
	},
	//返回列表
	returnGrid:function(){
		var me = this;
		me.close();
	},
	//选择城市
	selectCity:function(){
		var me = this;
		var selections = me.getValueAddDiscountCityGridPanel().getSelectionModel().getSelection();//获取选择的数据
		if(selections.length==0){
			pricing.showWoringMessage(pricing.valueAddDiscount.i18n('foss.pricing.pleaseSelectCity'));//请选择一个城市！
			return;
		}
		var datas = new Array();
		if(me.type==pricing.valueAddDiscount.priceDiscountStart){
			for(var i = 0;i<selections.length;i++){
				var isHave = false;//是否已经存在
				me.parent.getStore().each(function(record){
					if(record.get('deptOrgId')==selections[i].get('id')){
						isHave = true;
					}
				});
				if(!isHave){//不存在则新增
					var data = {'deptOrgId':selections[i].get('id'),'name':selections[i].get('name'),'deptOrgCode':selections[i].get('code'),'deptOrgTypeCode':pricing.valueAddDiscount.valueAddDiscountDetailTypeCity};
					datas.push(data);
				}
			}
			var oldData = me.parent.getStore().getAt(0)//清掉不是一类的数据
			if(!Ext.isEmpty(oldData)){
				if(oldData.get('deptOrgTypeCode')!=pricing.valueAddDiscount.valueAddDiscountDetailTypeCity){
					me.parent.getStore().removeAll();
				}
			}
		}else if(me.type==pricing.valueAddDiscount.priceDiscountArrive){
			for(var i = 0;i<selections.length;i++){
				var isHave = false;//是否已经存在
				me.parent.getStore().each(function(record){
					if(record.get('arrvOrgId')==selections[i].get('id')){
						isHave = true;
					}
				});
				if(!isHave){//不存在则新增
					var data = {'arrvOrgId':selections[i].get('id'),'name':selections[i].get('name'),'arrvOrgCode':selections[i].get('code'),'arrvOrgTypeCode':pricing.valueAddDiscount.valueAddDiscountDetailTypeCity};
					datas.push(data);
				}
			}
			var oldData = me.parent.getStore().getAt(0)//清掉不是一类的数据
			if(!Ext.isEmpty(oldData)){
				if(oldData.get('arrvOrgTypeCode')!=pricing.valueAddDiscount.valueAddDiscountDetailTypeCity){
					me.parent.getStore().removeAll();
				}
			}
		}
		me.parent.getStore().add(datas);
		me.close();
	},
	//查询的FORM
	priceDiscountSearchCityForm:null,
	getValueAddDiscountSearchCityForm:function(){
		if(Ext.isEmpty(this.priceDiscountSearchCityForm)){
    		this.priceDiscountSearchCityForm = Ext.create('Foss.pricing.valueAddDiscount.ValueAddDiscountSearchCityForm');
    	}
		return this.priceDiscountSearchCityForm;
	},
	//区域的GRID
	priceDiscountCityGridPanel:null,
	getValueAddDiscountCityGridPanel:function(){
		if(Ext.isEmpty(this.priceDiscountCityGridPanel)){
    		this.priceDiscountCityGridPanel = Ext.create('Foss.pricing.valueAddDiscount.ValueAddDiscountCityGridPanel');
    	}
		return this.priceDiscountCityGridPanel;
	}, 
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [me.getValueAddDiscountSearchCityForm(), me.getValueAddDiscountCityGridPanel()];
		me.fbar = [{
			text : pricing.valueAddDiscount.i18n('i18n.pricingRegion.returnGrid'),//返回列表
			handler :function(){
				me.close();
			} 
		},{
			text : pricing.valueAddDiscount.i18n('i18n.pricingRegion.determine'),//确定
			cls:'yellow_button',
			margin:'0 0 0 365',
			handler :function(){
				me.selectCity();
			} 
		}];
		me.callParent([cfg]);
	}
});

/**
 * 查询城市
 */
Ext.define('Foss.pricing.valueAddDiscount.ValueAddDiscountSearchCityForm', {
	extend : 'Ext.form.Panel',
	title: pricing.valueAddDiscount.i18n('i18n.pricingRegion.city'),
	frame: true,
	collapsible: true,
    defaults : {
    	columnWidth : .4,
    	margin : '8 10 5 10',
    	labelSeparator:'',
    	labelWidth:60,
    	anchor : '100%'
    },
    height :90,
	defaultType : 'textfield',
	layout:'column',
	constructor : function(config) {//构造器
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [{
			xtype:'textfield',
			fieldLabel:pricing.valueAddDiscount.i18n('foss.pricing.cityName'),//城市名称
			name:'name'
		},{
			xtype : 'container',
			margin : '0 0 0 0',
			columnWidth : .2,
			items : [{
				xtype : 'button', 
				width:70,
				cls:'yellow_button',
				text : pricing.valueAddDiscount.i18n('i18n.pricingRegion.search'),//查询
				handler : function() {
					if(me.getForm().isValid()){
						var grid  = me.up('window').getValueAddDiscountCityGridPanel();
						grid.getPagingToolbar().moveFirst();//用分页的moveFirst()方法
					}
				}
			}]
		}];
		me.callParent([cfg]);
	}
});

/**
 * 立即中止价格方案 Window
 */
Ext.define('Foss.pricing.valueAddDiscount.ImmediatelyStopEndTimeWindow',{
		extend: 'Ext.window.Window',
		title: pricing.valueAddDiscount.i18n('foss.pricing.immediatelySupendPricePriceScheme'),//"立即中止方案",
		width:380,
		height:152,
		closeAction: 'hide' ,
		marketingEventEntity:null,
		stopFormPanel:null,
		parent:null,
		getStopFormPanel : function(){
	    	if(Ext.isEmpty(this.stopFormPanel)){
	    		this.stopFormPanel = Ext.create('Foss.pricing.valueAddDiscount.ImmediatelyStopFormPanel');
	    	}
	    	return this.stopFormPanel;
	    },
	    listeners:{
	    	beforeshow:function(me){
	    		var showbeginTime = Ext.Date.format(new Date(me.marketingEventEntity.beginTime), 'Y-m-d');
	    		var showendTime = 	Ext.Date.format(new Date(me.marketingEventEntity.endTime), 'Y-m-d');
	    		var value = pricing.valueAddDiscount.i18n('foss.pricing.showleftTimeInfo')
				  +showbeginTime+pricing.valueAddDiscount.i18n('foss.pricing.showmiddleTimeInfo')
				  +showendTime+pricing.valueAddDiscount.i18n('foss.pricing.showrightEndTimeInfo');
	    		me.getStopFormPanel().down('displayfield').setValue(value);
	    	},
	    	beforehide:function(me){
	    		me.getStopFormPanel().getForm().reset();
	    	}
	    },
	   	constructor : function(config) {
			var me = this, cfg = Ext.apply({}, config);
			me.items = [me.getStopFormPanel()];
			me.callParent(cfg);
		}
});

/**
 * 立即中止功能FormPanel
 */
Ext.define('Foss.pricing.valueAddDiscount.ImmediatelyStopFormPanel',{
	extend : 'Ext.form.Panel',
	layout:'column',
	stop:function(){
		var me = this;
    	var form = me.getForm();
    	if(form.isValid()){
    		var marketingEventEntity = new Foss.pricing.valueAddDiscount.MarketingEventEntity();
			form.updateRecord(marketingEventEntity);
			marketingEventEntity.set('endTime',Ext.Date.parse(form.findField('endTime').getValue(), 'Y-m-d H:i:s'));
			var id = me.up('window').marketingEventEntity.id;
			marketingEventEntity.set('id',id);
    		var params = {'priceDiscountVo':{'marketingEventEntity':marketingEventEntity.data}};
    		var url = pricing.realPath('terminateImmediatelyDiscountProgram.action');
    		var successFun = function(json){
    			pricing.showInfoMes(json.message);
    			me.up('window').hide();
    			me.up('window').parent.getPagingToolbar().moveFirst();
    	    };
    	    var failureFun = function(json){
    	    	if(Ext.isEmpty(json)){
    				pricing.showErrorMes(pricing.valueAddDiscount.i18n('i18n.pricingRegion.requestTimeOut'));
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
				xtype: 'displayfield',
				columnWidth:.9,
				value:''
			},{ 
				fieldLabel :pricing.valueAddDiscount.i18n('foss.pricing.suspendTime') ,//'中止日期',
				name : 'endTime',
				xtype : 'datetimefield_date97',
				editable:false,
				time : true,
				id : 'Foss_valueAddDiscount_stopEndTime_ID',
				dateConfig: {
					el : 'Foss_valueAddDiscount_stopEndTime_ID-inputEl'
				},
				allowBlank:false,
				columnWidth:.9
	    	},{
				xtype: 'container',
				columnWidth:.6,
				html: '&nbsp;'
			},{
				xtype : 'button', 
				width:70,
				columnWidth:.15,
				text : pricing.valueAddDiscount.i18n('i18n.pricingRegion.determine'),//"确认",
				handler : function() {
					me.stop();
				}
			},{
				xtype : 'button', 
				width:70,
				columnWidth:.15,
				text : pricing.valueAddDiscount.i18n('i18n.pricingRegion.cancel'),//"取消",
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
Ext.define('Foss.pricing.valueAddDiscount.ImmediatelyActiveTimeWindow',{
		extend: 'Ext.window.Window',
		title: pricing.valueAddDiscount.i18n('foss.pricing.immediatelyActiveationPriceScheme'),//"立即激活方案",
		width:380,
		height:152,
		marketingEventEntity:null,
		closeAction: 'hide' ,
		immediatelyActiveFormPanel:null,
		getImmediatelyActiveFormPanel : function(){
	    	if(Ext.isEmpty(this.immediatelyActiveFormPanel)){
	    		this.immediatelyActiveFormPanel = Ext.create('Foss.pricing.valueAddDiscount.ImmediatelyActiveFormPanel');
	    	}
	    	return this.immediatelyActiveFormPanel;
	    },
	    listeners:{
	    	beforeshow:function(me){
	    		var showbeginTime = Ext.Date.format(new Date(me.marketingEventEntity.beginTime), 'Y-m-d');
	    		var showendTime = 	Ext.Date.format(new Date(me.marketingEventEntity.endTime), 'Y-m-d');
	    		var value = pricing.valueAddDiscount.i18n('foss.pricing.showleftTimeInfo')
				  +showbeginTime+pricing.valueAddDiscount.i18n('foss.pricing.showmiddleTimeInfo')
				  +showendTime+pricing.valueAddDiscount.i18n('foss.pricing.showrightEndTimeInfo');
	    		me.getImmediatelyActiveFormPanel().down('displayfield').setValue(value);
	    	},
	    	beforehide:function(me){
	    		me.getImmediatelyActiveFormPanel().getForm().reset();
	    	}
	    },
	   	constructor : function(config) {
			var me = this, cfg = Ext.apply({}, config);
			me.items = [me.getImmediatelyActiveFormPanel()];
			me.callParent(cfg);
		}
});


/**
 * 立即激活功能Form
 */
Ext.define('Foss.pricing.valueAddDiscount.ImmediatelyActiveFormPanel',{
	extend : 'Ext.form.Panel',
	layout:'column',
	activetion:function(){
		var me = this;
    	var form = me.getForm();
    	if(form.isValid()){
			var marketingEventEntity = new Foss.pricing.valueAddDiscount.MarketingEventEntity();
			form.updateRecord(marketingEventEntity);
			var id = me.up('window').marketingEventEntity.id;
			marketingEventEntity.set('id',id);
			marketingEventEntity.set('beginTime',Ext.Date.parse(form.findField('beginTime').getValue(), 'Y-m-d H:i:s'));
    		var params = {'priceDiscountVo':{'marketingEventEntity':marketingEventEntity.data}};
    		var url = pricing.realPath('activateImmediatelyDiscountProgram.action');
    		var successFun = function(json){
    			pricing.showInfoMes(json.message);
    			me.up('window').hide();
				me.up('window').parent.getPagingToolbar().moveFirst();  			
    	    };
    	    var failureFun = function(json){
    	    	if(Ext.isEmpty(json)){
    				pricing.showErrorMes(pricing.valueAddDiscount.i18n('i18n.pricingRegion.requestTimeOut'));
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
				xtype: 'displayfield',
				columnWidth:.9,
				value:''
			},{
				fieldLabel:pricing.valueAddDiscount.i18n('foss.pricing.availabilityDate'),//'生效日期',
				name : 'beginTime',
				xtype : 'datetimefield_date97',
				editable:false,
				time : true,
				allowBlank:false,
				id : 'Foss_valueAddDiscount_activeEndTime_ID',
				dateConfig: {
					el : 'Foss_valueAddDiscount_activeEndTime_ID-inputEl'
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
				text : pricing.valueAddDiscount.i18n('i18n.pricingRegion.determine'),//,"确认",
				handler : function() {
					me.activetion();
				}
			},{
				xtype : 'button', 
				width:70,
				columnWidth:.15,
				text : pricing.valueAddDiscount.i18n('i18n.pricingRegion.cancel'),//"取消",
				handler : function() {
					me.up('window').hide();
				}
			}];
        this.callParent(cfg);
    }
});

