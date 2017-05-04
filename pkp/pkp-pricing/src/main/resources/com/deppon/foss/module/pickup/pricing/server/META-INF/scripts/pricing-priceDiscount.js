//转换long类型为日期(在model中会用到)
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
pricing.priceDiscount.productEntityList = [];//基础产品列表
pricing.priceDiscount.goodTypeList = [];//货物类型列表
pricing.priceDiscount.caculateType = [{'valueCode':'WEIGHT','valueName':pricing.priceDiscount.i18n('foss.pricing.weight')}//重量
,{'valueCode':'VOLUME','valueName':pricing.priceDiscount.i18n('foss.pricing.volume')}//体积
,{'valueCode':'ORIGINALCOST','valueName':pricing.priceDiscount.i18n('foss.pricing.directCosts')}]
pricing.priceDiscount.channel = 'PKP_PRICE_CHANNEL';//渠道
pricing.priceDiscount.priceDiscountDetailTypeDept = 'DISCOUNT_DEPT';//折扣明细类型——部门
pricing.priceDiscount.priceDiscountDetailTypeRegion = 'DISCOUNT_REGION';//折扣明细类型——区域
pricing.priceDiscount.priceDiscountDetailTypeCity = 'DISCOUNT_CITY';//折扣明细类型——城市
pricing.priceDiscount.priceDiscountDetailTypeAllNet = 'ALLNET';//全网
pricing.priceDiscount.priceDiscountArrive = 'ARRIVE';//到达
pricing.priceDiscount.priceDiscountStart = 'START';//出发
pricing.priceDiscount.tomorrowTime = null;
//价格区域
pricing.priceDiscount.PRICING_REGION = 'PRICING_REGION';
//城市
pricing.priceDiscount.Pricing_City = 'CITY';
pricing.priceDiscount.allNet = 'ALLNET';//全网常量
//--------------------------------------pricing----------------------------------------
//查询货物类型列表
pricing.searchGoodTypeList = function(){
	Ext.Ajax.request({
		url:pricing.realPath('findGoodsTypeByCondiction.action'),//查询基础产品
		async:false,
		success:function(response){
			var result = Ext.decode(response.responseText);
			pricing.priceDiscount.goodTypeList = result.pricingValuationVo.goodsTypeEntityList;
		},
		failure:function(response){
			var result = Ext.decode(response.responseText);
			if(Ext.isEmpty(result)){
				pricing.showErrorMes(pricing.priceDiscount.i18n('i18n.pricingRegion.requestTimeOut'));
			}else{
				pricing.showErrorMes(result.message);
			}
		},
		exception:function(response){
			var result = Ext.decode(response.responseText);
			if(Ext.isEmpty(result)){
				pricing.showErrorMes(pricing.priceDiscount.i18n('i18n.pricingRegion.requestTimeOut'));
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
			pricing.priceDiscount.productEntityList = result.pricingValuationVo.productEntityList;
		},
		failure:function(response){
			var result = Ext.decode(response.responseText);
			if(Ext.isEmpty(result)){
				pricing.showErrorMes(pricing.priceDiscount.i18n('i18n.pricingRegion.requestTimeOut'));
			}else{
				pricing.showErrorMes(result.message);
			}
		},
		exception:function(response){
			var result = Ext.decode(response.responseText);
			if(Ext.isEmpty(result)){
				pricing.showErrorMes(pricing.priceDiscount.i18n('i18n.pricingRegion.requestTimeOut'));
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
			pricing.priceDiscount.tomorrowTime = today.setDate(today.getDate()+1);
		},
		failure:function(response){
			var result = Ext.decode(response.responseText);
			if(Ext.isEmpty(result)){
				pricing.showErrorMes(pricing.priceDiscount.i18n('i18n.pricingRegion.requestTimeOut'));
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
Ext.define('Foss.pricing.priceDiscount.PriceDiscountDto', {
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
        name : 'beginDate',// 折扣方案开始时间
        type : 'date',
        defaultValue : null,
        convert : pricing.changeLongToDate
    },{
        name : 'endDate',// 折扣方案结束时间
        type : 'date',
        defaultValue : null,
        convert : pricing.changeLongToDate
    },{
        name : 'businessDate',// 业务日期
        type : 'date',
        defaultValue : null,
        convert : pricing.changeLongToDate
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
        convert : pricing.changeLongToDate
    },{
        name : 'createUserName',//创建人姓名
        type : 'string'
    },{
        name : 'modifyTime',//修改时间
        type : 'date',
        defaultValue : null,
        convert : pricing.changeLongToDate
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
    }]
});
//基础产品明细MODEL
Ext.define('Foss.pricing.priceDiscount.ProductEntity', {
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
Ext.define('Foss.pricing.priceDiscount.GoodsTypeEntity', {
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
//折扣方案主信息
Ext.define('Foss.pricing.priceDiscount.MarketingEventEntity', {
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
        name : 'createUser',//创建人工号
        type : 'string'
    },{
        name : 'modifyDate',//修改时间
        type : 'date',
        defaultValue : null,
        convert : pricing.changeLongToDate
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
        convert : pricing.changeLongToDate
    },{
        name : 'endTime',//截止日期
        type : 'date',
        defaultValue : null,
        convert : pricing.changeLongToDate
    },{
//        name : 'description',//备注
    	name : 'remark',//备注
        type : 'string'
    },{
        name : 'createTime',//创建时间
        type : 'date',
        defaultValue : null,
        convert : pricing.changeLongToDate
    },{
        name : 'createOrgCode',//创建
        type : 'string'
    },{
        name : 'modifyTime',//修改时间
        type : 'date',
        defaultValue : null,
        convert : pricing.changeLongToDate
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
        name : 'pricingEntryName',//计价条目NAME
        type : 'string'
    },{
        name : 'pricingEntryCode',//计价条目CODE
        type : 'string'
    },{
        name : 'pricingEntryId',//计价条目ID 
        type : 'string'
    },{
        name : 'type',//类型
        type : 'string'
    },{
        name : 'createUserName',//创建人姓名
        type : 'string'
    }]
});
/**
 * 区域MODEL
 */
Ext.define('Foss.pricing.priceDiscount.AreaModel', {
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
		 convert:pricing.changeLongToDate
	}, {
		name : 'endTime',// 结束时间
		type: 'Date',
		defaultValue : null,
		 convert:pricing.changeLongToDate
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
		 convert:pricing.changeLongToDate
	}, {
		name : 'modifyDate',//修改时间
		type: 'Date',
		defaultValue : null,
		 convert:pricing.changeLongToDate
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
 * 折扣方案Store
 */
Ext.define('Foss.pricing.priceDiscount.priceDiscountDetailStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.pricing.priceDiscount.PriceDiscountDto',//折扣方案明细MODEL
	pageSize:5,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url : pricing.realPath('selectPriceDiscountByCodition.action'),
		reader : {
			type : 'json',
			root : 'priceDiscountVo.priceDiscountDtoList',
			totalProperty : 'totalCount'
		}
	}
});
/**
 * 折扣方案Store
 */
Ext.define('Foss.pricing.priceDiscount.priceDiscountStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.pricing.priceDiscount.MarketingEventEntity',//折扣方案MODEL
	pageSize:20,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url : pricing.realPath('selectPriceProgramByCodition.action'),
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
 * 折扣方案查询表单
 */
Ext.define('Foss.pricing.priceDiscount.QueryPriceDiscountForm', {
	extend : 'Ext.form.Panel',
	title: pricing.priceDiscount.i18n('i18n.pricingRegion.searchCondition'),//查询条件
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
		    fieldLabel:pricing.priceDiscount.i18n('foss.pricing.discountProgram'),//折扣方案
	        xtype : 'textfield'
		},{
			name: 'active',
			queryMode: 'local',
		    displayField: 'valueName',
		    valueField: 'valueCode',
		    editable:false,
		    value:'ALL',
		    store:pricing.getStore(null,null,['valueCode','valueName']
		    ,[{'valueCode':'Y','valueName':pricing.priceDiscount.i18n('i18n.pricingRegion.active')},{'valueCode':'N','valueName':pricing.priceDiscount.i18n('i18n.pricingRegion.unActive')},{'valueCode':'ALL','valueName':pricing.priceDiscount.i18n('i18n.pricingRegion.all')}]),
	        fieldLabel: pricing.priceDiscount.i18n('foss.pricing.discountState'),//折扣状态
	        xtype : 'combo'
		},{
			name: 'businessDate',
			format:'Y-m-d H:i:s',
		    fieldLabel:pricing.priceDiscount.i18n('foss.pricing.businessDate'),//业务日期
	        xtype : 'datefield'
		}];
		me.fbar = [{
			xtype : 'button', 
			width:70,
			text : pricing.priceDiscount.i18n('foss.pricing.reset'),//重置
			handler : function() {
				me.getForm().reset();
			}
		},{
				xtype : 'button', 
				width:70,
				cls:'yellow_button',
				margin:'0 0 0 825',
				text : pricing.priceDiscount.i18n('i18n.pricingRegion.search'),
				disabled: !pricing.priceDiscount.isPermission('priceDiscount/priceDiscountQuerybutton'),
				hidden: !pricing.priceDiscount.isPermission('priceDiscount/priceDiscountQuerybutton'),
				handler : function() {
					if(me.getForm().isValid()){
						var grid = Ext.getCmp('T_pricing-priceDiscount_content').getAreaGrid();//获取大查询GRID
						grid.getStore().load();
					}
				}
			}]
		me.callParent([cfg]);
	}
});
/**
 * 折扣方案列表
 */
Ext.define('Foss.pricing.priceDiscount.PriceDiscountGridPanel', {
	extend: 'Ext.grid.Panel',
	title : pricing.priceDiscount.i18n('i18n.pricingRegion.searchResults'),//查询结果
	frame: true,
	cls: 'autoHeight',
	bodyCls: 'autoHeight', 
    sortableColumns:false,
    enableColumnHide:false,
    enableColumnMove:false,
	stripeRows : true, // 交替行效果
	selType : "rowmodel", // 选择类型设置为：行选择
	emptyText: pricing.priceDiscount.i18n('foss.pricing.theQueryResultIsEmpty'),//查询结果为空
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
	//激活折扣方案
	activePricingDiscount:function(){
		var me = this;
		var priceDiscountIds = new Array();
		//获取选中的数据
		var selections = me.getSelectionModel().getSelection();
		if(selections.length<1){
			pricing.showErrorMes(pricing.priceDiscount.i18n('foss.pricing.pleaseSelectToActivateDiscountProgram'));//请选择要激活的折扣方案！
			return;
		}
		for(var i = 0 ; i<selections.length ; i++){
			if(selections[i].get('active')=='Y'){//只有未激活的折扣方案才可以删除
				pricing.showErrorMes(pricing.priceDiscount.i18n('foss.pricing.pleaseChooseNotActivateTheDiscountProgram'));//请选择要删除的未激活折扣方案！
				return;
			}else if(selections[i].get('active')=='N'){
				priceDiscountIds.push(selections[i].get('id'));
			}else{
				priceDiscountIds.push(selections[i].get('id'));
			}
		}
		if(priceDiscountIds.length<1){
			pricing.showErrorMes(pricing.priceDiscount.i18n('foss.pricing.pleaseSelectLeastInactiveDiscountProgram'));//请至少选择一条未激活的折扣方案！
			return;
		}
		pricing.showQuestionMes(pricing.priceDiscount.i18n('foss.pricing.wantActivateInactiveDiscountProgram'),function(e){//是否要激活这些未激活的折扣方案？
			if(e=='yes'){//询问是否激活，是则发送请求
				var params = {'priceDiscountVo':{'priceDiscountIds':priceDiscountIds}};
				var successFun = function(json){
					pricing.showInfoMes(json.message);
					me.getPagingToolbar().moveFirst();
				};
				var failureFun = function(json){
					if(Ext.isEmpty(json)){
						pricing.showErrorMes(pricing.priceDiscount.i18n('i18n.pricingRegion.requestTimeOut'));
					}else{
						pricing.showErrorMes(json.message);
					}
				};
				var url = pricing.realPath('activePricingDiscount.action');
				pricing.requestJsonAjax(url,params,successFun,failureFun);
			}
		});
	},
	//删除折扣方案
	deletePricingDiscount:function(){
		var me = this;
		var priceDiscountIds = new Array();
		//获取选中的数据
		var selections = me.getSelectionModel().getSelection();
		if(selections.length<1){
			pricing.showErrorMes(pricing.priceDiscount.i18n('foss.pricing.pleaseSelectDiscountProgramWantDeleteNotActivated'));//请选择要删除的未激活折扣方案！
			return;
		}
		for(var i = 0 ; i<selections.length ; i++){
			if(selections[i].get('active')=='Y'){//只有未激活的折扣方案才可以删除
				pricing.showErrorMes(pricing.priceDiscount.i18n('foss.pricing.pleaseChooseNotActivateTheDiscountProgram'));//请选择要删除的未激活折扣方案！
				return;
			}else if(selections[i].get('active')=='N'){
				priceDiscountIds.push(selections[i].get('id'));
			}else{
				priceDiscountIds.push(selections[i].get('id'));
			}
		}
		if(priceDiscountIds.length<1){
			pricing.showErrorMes(pricing.priceDiscount.i18n('foss.pricing.pleaseSelectLeastInactiveDiscountProgram'));//请至少选择一条未激活的折扣方案！
			return;
		}
		pricing.showQuestionMes(pricing.priceDiscount.i18n('foss.pricing.wantDeleteTheseDiscountProgramNotActivated'),function(e){//是否要删除这些未激活的折扣方案？
			if(e=='yes'){//询问是否删除，是则发送请求
				var params = {'priceDiscountVo':{'priceDiscountIds':priceDiscountIds}};
				var successFun = function(json){
					pricing.showInfoMes(json.message);
					me.getPagingToolbar().moveFirst();
				};
				var failureFun = function(json){
					if(Ext.isEmpty(json)){
						pricing.showErrorMes(pricing.priceDiscount.i18n('i18n.pricingRegion.requestTimeOut'));
					}else{
						pricing.showErrorMes(json.message);
					}
				};
				var url = pricing.realPath('deletePricingDiscount.action');
				pricing.requestJsonAjax(url,params,successFun,failureFun);
			}
		});
		
	},
	//折扣主信息新增Window
	priceDiscountAddWindow : null,
	getPriceDiscountAddWindow : function() {
		if (Ext.isEmpty(this.priceDiscountAddWindow)) {
			this.priceDiscountAddWindow = Ext.create('Foss.pricing.priceDiscount.PriceDiscountAddWindow');
			this.priceDiscountAddWindow.parent = this;
		}
		return this.priceDiscountAddWindow;
	},
	//折扣主信息修改Window
	priceDiscountUpdateWindow : null,
	getPriceDiscountUpdateWindow : function() {
		if (Ext.isEmpty(this.priceDiscountUpdateWindow)) {
			this.priceDiscountUpdateWindow = Ext.create('Foss.pricing.priceDiscount.PriceDiscountUpdateWindow');
			this.priceDiscountUpdateWindow.parent = this;
		}
		return this.priceDiscountUpdateWindow;
	},
	//折扣主明细新增Window
	priceDiscountDeatilAddWindow : null,
	getPriceDiscountDeatilAddWindow : function() {
		if (Ext.isEmpty(this.priceDiscountDeatilAddWindow)) {
			this.priceDiscountDeatilAddWindow = Ext.create('Foss.pricing.priceDiscount.PriceDiscountDeatilAddWindow');
			this.priceDiscountDeatilAddWindow.parent = this;
		}
		return this.priceDiscountDeatilAddWindow;
	},
	//折扣主明细修改Window
	priceDiscountDeatilUpdateWindow : null,
	getPriceDiscountDeatilUpdateWindow : function() {
		if (Ext.isEmpty(this.priceDiscountDeatilUpdateWindow)) {
			this.priceDiscountDeatilUpdateWindow = Ext.create('Foss.pricing.priceDiscount.PriceDiscountDeatilAddWindow');
			this.priceDiscountDeatilUpdateWindow.parent = this;
		}
		return this.priceDiscountDeatilUpdateWindow;
	},
	//终止，设置截止日期
	pricingDiscountEndTimeWindow:null,
	getPricingDiscountEndTimeWindow : function(selection) {
		if (Ext.isEmpty(this.pricingDiscountEndTimeWindow)) {
			this.pricingDiscountEndTimeWindow = Ext.create('Foss.pricing.priceDiscount.PricingDiscountEndTimeWindow',{
				selection:selection
			});
			this.pricingDiscountEndTimeWindow.parent = this;
		}
		return this.pricingDiscountEndTimeWindow;
	},
	//查看详情window
	priceDiscountDeatilShowWindow:null,
	getPriceDiscountDeatilShowWindow : function() {
		if (Ext.isEmpty(this.priceDiscountDeatilShowWindow)) {
			this.priceDiscountDeatilShowWindow = Ext.create('Foss.pricing.priceDiscount.PriceDiscountDeatilShowWindow');
			this.priceDiscountDeatilShowWindow.parent = this;
		}
		return this.priceDiscountDeatilShowWindow;
	},
	   /**
     * 立即生效
     * 对于实际业务可能在当天发生两次以上的调整，故给予立即激中止功能用于支持该业务，
     * 1、立即中止功能给予特定角色来操作。根据所登陆的用户所属某某角色来决定是否给予立即中止功能。防止一般用户进行当天频繁调价操作
     * 2、立即中止功能中止日期可以等于今天但是不能小于今天。
     * 
     */
    immediatelyActiveWindow:null,
	getImmediatelyActiveWindow: function(){
		if(Ext.isEmpty(this.immediatelyActiveWindow)){
			this.immediatelyActiveWindow = Ext.create('Foss.pricing.priceDiscount.ImmediatelyActiveTimeWindow');
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
			this.immediatelyStopWindow = Ext.create('Foss.pricing.priceDiscount.ImmediatelyStopEndTimeWindow');
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
	 		pricing.showWoringMessage(pricing.priceDiscount.i18n('foss.pricing.selectOneRecordToOp'));
			return;
	 	}
	 	if(selections.length > 1){
	 		pricing.showWoringMessage(pricing.priceDiscount.i18n('foss.pricing.selectOneRecordToOp'));
			return;
	 	}
	 	if(selections[0].get('active')!='Y'){
	 		pricing.showWoringMessage(pricing.priceDiscount.i18n('foss.pricing.selectOneActiveRecordToOp'));
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
	 		pricing.showWoringMessage(pricing.priceDiscount.i18n('foss.pricing.selectOneRecordToOp'));
			return;
	 	}
	 	if(selections.length > 1){
	 		pricing.showWoringMessage(pricing.priceDiscount.i18n('foss.pricing.selectOneRecordToOp'));
			return;
	 	}
	 	if(selections[0].get('active')=='Y'){
	 		pricing.showWoringMessage(pricing.priceDiscount.i18n('foss.pricing.selectOneUnActiveRecordToOp'));
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
			text : pricing.priceDiscount.i18n('i18n.pricingRegion.num')//序号
		},{
			align : 'center',
			xtype : 'actioncolumn',
			text : pricing.priceDiscount.i18n('i18n.pricingRegion.opra'),//操作
			items: [{
				iconCls: 'deppon_icons_edit',
                tooltip: pricing.priceDiscount.i18n('foss.pricing.update'),//修改
                disabled: !pricing.priceDiscount.isPermission('priceDiscount/priceDiscountUpdatebutton'),
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
                        grid.up().getPriceDiscountUpdateWindow().marketingEventEntity = marketingEventEntity;//设置折扣方案主信息
                        grid.up().getPriceDiscountUpdateWindow().channelEntityList = channelEntityList;//渠道信息
                        grid.up().getPriceDiscountUpdateWindow().show();//显示window
    				};
    				var failureFun = function(json){
    					if(Ext.isEmpty(json)){
    						pricing.showErrorMes(pricing.priceDiscount.i18n('i18n.pricingRegion.requestTimeOut'));
    					}else{
    						pricing.showErrorMes(json.message);
    					}
    				};
    				var url = pricing.realPath('selectMarketingByPrimaryKey.action');
    				pricing.requestJsonAjax(url,params,successFun,failureFun);
                }
			},{
				iconCls: 'deppon_icons_softwareUpgrade',
                tooltip: pricing.priceDiscount.i18n('foss.pricing.upgradedVersion'),//升级版本
                disabled: !pricing.priceDiscount.isPermission('priceDiscount/priceDiscountUpgradebutton'),
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
//                        grid.up().getPriceDiscountUpdateWindow().marketingEventEntity = marketingEventEntity;//设置折扣方案主信息
//                        grid.up().getPriceDiscountUpdateWindow().channelEntityList = channelEntityList;//渠道信息
//                        grid.up().getPriceDiscountUpdateWindow().show();//显示window
    				};
    				var failureFun = function(json){
    					if(Ext.isEmpty(json)){
    						pricing.showErrorMes(pricing.priceDiscount.i18n('i18n.pricingRegion.requestTimeOut'));
    					}else{
    						pricing.showErrorMes(json.message);
    					}
    				};
    				var url = pricing.realPath('copyDiscountProgram.action');
    				pricing.requestJsonAjax(url,params,successFun,failureFun);
                }
			},{
				iconCls: 'deppon_icons_showdetail',
                tooltip: pricing.priceDiscount.i18n('foss.pricing.details'),//查看详情
                disabled: !pricing.priceDiscount.isPermission('priceDiscount/priceDiscountDetailbutton'),
				width:42,
                handler: function(grid, rowIndex, colIndex) {
                	var record = grid.getStore().getAt(rowIndex);
                	var params = {'priceDiscountVo':{'marketingEventEntity':{'id':record.get('id')}}};
    				var successFun = function(json){
    					var marketingEventEntity = json.priceDiscountVo.marketingEventEntity;
    					var channelEntityList = json.priceDiscountVo.channelEntityList;
                        grid.up().getPriceDiscountDeatilShowWindow().marketingEventEntity = marketingEventEntity;//设置折扣方案主信息
                        grid.up().getPriceDiscountDeatilShowWindow().channelEntityList = channelEntityList;//渠道信息
                        grid.up().getPriceDiscountDeatilShowWindow().show();//显示window
    				};
    				var failureFun = function(json){
    					if(Ext.isEmpty(json)){
    						pricing.showErrorMes(pricing.priceDiscount.i18n('i18n.pricingRegion.requestTimeOut'));
    					}else{
    						pricing.showErrorMes(json.message);
    					}
    				};
    				var url = pricing.realPath('selectMarketingByPrimaryKey.action');
    				pricing.requestJsonAjax(url,params,successFun,failureFun);
                }
			}]
		},{
			text : pricing.priceDiscount.i18n('foss.pricing.coding'),//编码
			dataIndex : 'code'
		},{
			text : pricing.priceDiscount.i18n('foss.pricing.scenarioName'),//方案名称
			dataIndex : 'name'
		},{
			text :pricing.priceDiscount.i18n('foss.pricing.status'),//状态
			dataIndex : 'active',
			width:50,
			renderer:function(value){
				if(value=='Y'){//'Y'表示激活
					return pricing.priceDiscount.i18n('i18n.pricingRegion.active');
				}else if(value=='N'){//'N'表示未激活
					return  pricing.priceDiscount.i18n('i18n.pricingRegion.unActive');
				}else{
					return '';
				}
			}
		},{
			text : pricing.priceDiscount.i18n('foss.pricing.createUser'),//创建人
			dataIndex : 'createUserName'
		},{
			text :pricing.priceDiscount.i18n('foss.pricing.updateTime'),//修改时间
			dataIndex : 'modifyDate',
			renderer:function(value){
				return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
			}
		},{
			text :pricing.priceDiscount.i18n('foss.pricing.qishiTime'),//起始时间
			dataIndex : 'beginTime',
			renderer:function(value){
				return Ext.Date.format(new Date(value), 'Y-m-d');
			}
		},{
			text :pricing.priceDiscount.i18n('foss.pricing.theCutOffTime'),//截止时间
			dataIndex : 'endTime',
			renderer:function(value){
				return Ext.Date.format(new Date(value), 'Y-m-d');
			}
		}];
		me.store = Ext.create('Foss.pricing.priceDiscount.priceDiscountStore',{
			autoLoad : false,
			pageSize : 20,
			listeners: {
				beforeload: function(store, operation, eOpts){
					var queryForm = me.up().getQueryPriceDiscountForm();
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
			text : pricing.priceDiscount.i18n('i18n.pricingRegion.add'),
			disabled: !pricing.priceDiscount.isPermission('priceDiscount/priceDiscountAddbutton'),
			hidden: !pricing.priceDiscount.isPermission('priceDiscount/priceDiscountAddbutton'),
			handler :function(){
				me.getPriceDiscountAddWindow().show();
			} 
		},'-', {
			//删除
			text : pricing.priceDiscount.i18n('foss.pricing.delete'),
			disabled: !pricing.priceDiscount.isPermission('priceDiscount/priceDiscountDeletebutton'),
			hidden: !pricing.priceDiscount.isPermission('priceDiscount/priceDiscountDeletebutton'),
			handler :function(){
				me.deletePricingDiscount();
			} 
		},
//		'-', {
//			//激活
//			text : pricing.priceDiscount.i18n('i18n.pricingRegion.active'),
//			handler :function(){
//				me.activePricingDiscount();
//			} 
//		},'-', {
//			//中止
//			text : pricing.priceDiscount.i18n('foss.pricing.stop'),
//			handler :function(){
//				var selections = me.getSelectionModel().getSelection();
//				if(selections.length!=1){
//					pricing.showWoringMessage(pricing.priceDiscount.i18n('foss.pricing.pleaseSelectActivationDataAbortTheOperation'));
//					return;
//				}
//				if(selections[0].get('active')!='Y'){
//					pricing.showWoringMessage(pricing.priceDiscount.i18n('foss.pricing.pleaseSelectActivationDataAbortTheOperation'));
//					return;
//				}
//				if(selections[0].get('beginTime').getTime()>=selections[0].get('endTime').getTime()){
//					pricing.showWoringMessage(pricing.priceDiscount.i18n('foss.pricing.pleaseSelectEffectiveDataAbortOperation'));
//					return;
//				}
//				var model = new Foss.pricing.priceDiscount.MarketingEventEntity(selections[0].data);
//				me.getPricingDiscountEndTimeWindow(model);
//				me.getPricingDiscountEndTimeWindow().selection = model;
//				me.getPricingDiscountEndTimeWindow().show();
//			} 
//		},
		'-', {
			text : pricing.priceDiscount.i18n('foss.pricing.immediatelyActivationProgram'),//'立即激活',
			disabled:!pricing.priceDiscount.isPermission('priceDiscount/priceDiscountImmediatelyActivebutton'),
			hidden:!pricing.priceDiscount.isPermission('priceDiscount/priceDiscountImmediatelyActivebutton'),
			handler :function(){
				me.immediatelyActive();
			} 
		},'-', {
			text : pricing.priceDiscount.i18n('foss.pricing.immediatelyStopProgram'),//'立即中止',
			disabled:!pricing.priceDiscount.isPermission('priceDiscount/priceDiscountImmediatelyStopbutton'),
			hidden:!pricing.priceDiscount.isPermission('priceDiscount/priceDiscountImmediatelyStopbutton'),
			handler :function(){
				me.immediatelyStop();
			} 
		}];
		me.bbar = me.getPagingToolbar();
		me.callParent([cfg]);
	}
});

/**
 * @description 折扣方案主页
 */
Ext.onReady(function() {
	Ext.QuickTips.init();
	if (Ext.getCmp('T_pricing-priceDiscount_content')) {
		return;
	}
	pricing.searchGoodTypeList();//货物类型
	pricing.searchProductEntityList();//产品类型
	pricing.haveServerNowTime();
	var queryPriceDiscountForm = Ext.create('Foss.pricing.priceDiscount.QueryPriceDiscountForm');//查询条件
	var priceDiscountGridPanel = Ext.create('Foss.pricing.priceDiscount.PriceDiscountGridPanel');//查询结果
	Ext.getCmp('T_pricing-priceDiscount').add(Ext.create('Ext.panel.Panel', {
	  	id : 'T_pricing-priceDiscount_content',
		cls : 'panelContentNToolbar',
		bodyCls : 'panelContentNToolbar-body',
		//获得查询FORM
		getQueryPriceDiscountForm : function() {
			return queryPriceDiscountForm;
		},
		//获得查询结果GRID
		getAreaGrid : function() {
			return priceDiscountGridPanel;
		},
		items : [ queryPriceDiscountForm, priceDiscountGridPanel]
	}));
});
/**
 * 折扣方案-新增
 */
Ext.define('Foss.pricing.priceDiscount.PriceDiscountAddWindow',{
	extend : 'Ext.window.Window',
	title : pricing.priceDiscount.i18n('foss.pricing.priceDiscountProgramDefinition'),//价格折扣方案定义
	closable : true,
	modal : true,
	resizable:false,
	parent:null,//(Foss.pricing.priceDiscount.PriceDiscountGridPanel)
	closeAction : 'hide',
	width :590,
	height :480,	
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	listeners:{
		beforehide:function(me){
			me.getPriceDiscountEditFormPanel().getForm().reset();
		},
		beforeshow:function(me){
			
		}
	},
	//折扣方案主信息FORM
	priceDiscountEditFormPanel:null,
	getPriceDiscountEditFormPanel:function(){
		if(Ext.isEmpty(this.priceDiscountEditFormPanel)){
    		this.priceDiscountEditFormPanel = Ext.create('Foss.pricing.priceDiscount.PriceDiscountEditFormPanel',{
    			isShow:false
    		});
    	}
    	return this.priceDiscountEditFormPanel;
	},
	//提交折扣方案主信息
	commintPriceDiscount:function(){
    	var me = this;
    	var form = me.getPriceDiscountEditFormPanel();
    	if(form.getForm().isValid()){//校验form是否通过校验
    		var marketingEventModel = new Foss.pricing.priceDiscount.MarketingEventEntity();// 折扣方案主信息
    		var channelEntityList = new Array();//渠道信息
    		form.getForm().updateRecord(marketingEventModel);//将FORM中数据设置到MODEL里面
    		if(marketingEventModel.get('beginTime').getTime()>=marketingEventModel.get('endTime')){
    			pricing.showWoringMessage(pricing.priceDiscount.i18n('foss.pricing.dateTerminationMustGreaterThanStartDate'));//终止日期需大于起始日期！
    			return;
    		}
    		var channel = FossDataDictionary.getDataDictionaryStore(pricing.priceDiscount.channel);//渠道store
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
				me.parent.getPriceDiscountDeatilAddWindow().marketingEventEntity = json.priceDiscountVo.marketingEventEntity;
				me.parent.getPriceDiscountDeatilAddWindow().channelEntityList = json.priceDiscountVo.channelEntityList;
				me.parent.getPriceDiscountDeatilAddWindow().show();
			};
			var failureFun = function(json){
				if(Ext.isEmpty(json)){
					pricing.showErrorMes(pricing.priceDiscount.i18n('foss.pricing.requestTimedOut'));//请求超时
				}else{
					pricing.showErrorMes(json.message);//提示失败原因
				}
			};
			var url = pricing.realPath('addPricingDiscount.action');//请求折扣新增
			pricing.requestJsonAjax(url,params,successFun,failureFun);//发送AJAX请求
   	    }
	},
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [me.getPriceDiscountEditFormPanel()];
		me.fbar = [{
			text : pricing.priceDiscount.i18n('i18n.pricingRegion.returnGrid'),
			handler :function(){
				me.close();
			} 
		},{
			text : pricing.priceDiscount.i18n('i18n.pricingRegion.commit'),
			cls:'yellow_button',
			margin:'0 0 0 360',
			handler :function(){
				me.commintPriceDiscount();
			} 
		}];
		me.callParent([cfg]);
	}
});
/**
 * 折扣方案-修改
 */
Ext.define('Foss.pricing.priceDiscount.PriceDiscountUpdateWindow',{
	extend : 'Ext.window.Window',
	title : pricing.priceDiscount.i18n('foss.pricing.priceDiscountProgramDefinition'),//价格折扣方案定义
	closable : true,
	modal : true,
	resizable:false,
	marketingEventEntity:null,//折扣方案主信息
	channelEntityList:null,//渠道信息
	parent:null,//(Foss.pricing.priceDiscount.PriceDiscountGridPanel)
	closeAction : 'hide',
	width :590,
	height :480,	
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	listeners:{
		beforehide:function(me){
			me.getPriceDiscountEditFormPanel().getForm().reset();
		},
		beforeshow:function(me){
			var form = me.getPriceDiscountEditFormPanel();
			if(!Ext.isEmpty(me.marketingEventEntity)){
				form.getForm().loadRecord(new Foss.pricing.priceDiscount.MarketingEventEntity(me.marketingEventEntity));//加载数据
			}else{
				pricing.showErrorMes(pricing.priceDiscount.i18n('foss.pricing.noMainDiscountProgram'));//没有折扣方案主信息！
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
	//折扣方案主信息FORM
	priceDiscountEditFormPanel:null,
	getPriceDiscountEditFormPanel:function(){
		if(Ext.isEmpty(this.priceDiscountEditFormPanel)){
    		this.priceDiscountEditFormPanel = Ext.create('Foss.pricing.priceDiscount.PriceDiscountEditFormPanel',{
    			isShow:false
    		});
    	}
    	return this.priceDiscountEditFormPanel;
	},
	//提交折扣方案主信息
	commintPriceDiscount:function(){
    	var me = this;
    	var form = me.getPriceDiscountEditFormPanel();
    	if(form.getForm().isValid()){//校验form是否通过校验
    		var marketingEventModel = new Foss.pricing.priceDiscount.MarketingEventEntity(me.marketingEventEntity);// 折扣方案主信息
    		var channelEntityList = new Array();//渠道信息
    		form.getForm().updateRecord(marketingEventModel);//将FORM中数据设置到MODEL里面
    		if(marketingEventModel.get('beginTime').getTime()>=marketingEventModel.get('endTime')){
    			pricing.showWoringMessage(pricing.priceDiscount.i18n('foss.pricing.dateTerminationMustGreaterThanStartDate'));//终止日期需大于起始日期！
    			return;
    		}
    		var channel = FossDataDictionary.getDataDictionaryStore(pricing.priceDiscount.channel);//渠道store
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
				me.parent.getPriceDiscountDeatilUpdateWindow().marketingEventEntity = json.priceDiscountVo.marketingEventEntity;
				me.parent.getPriceDiscountDeatilUpdateWindow().channelEntityList = json.priceDiscountVo.channelEntityList;
				me.parent.getPriceDiscountDeatilUpdateWindow().show();
			};
			var failureFun = function(json){
				if(Ext.isEmpty(json)){
					pricing.showErrorMes(pricing.priceDiscount.i18n('foss.pricing.requestTimedOut'));//请求超时
				}else{
					pricing.showErrorMes(json.message);//提示失败原因
				}
			};
			var url = pricing.realPath('updatePricingDiscount.action');//请求折扣修改
			pricing.requestJsonAjax(url,params,successFun,failureFun);//发送AJAX请求
   	    }
	},
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [me.getPriceDiscountEditFormPanel()];
		me.fbar = [{
			text : pricing.priceDiscount.i18n('i18n.pricingRegion.returnGrid'),
			handler :function(){
				me.close();
			} 
		},{
			text : pricing.priceDiscount.i18n('i18n.pricingRegion.commit'),
			cls:'yellow_button',
			margin:'0 0 0 285',
			handler :function(){
				me.commintPriceDiscount();
			} 
		},{
			text : pricing.priceDiscount.i18n('foss.pricing.modifyDetails'),
			cls:'yellow_button',
			handler :function(){
				var id = me.marketingEventEntity.id;
            	var params = {'priceDiscountVo':{'marketingEventEntity':{'id':id}}};
				var successFun = function(json){
					me.parent.getPriceDiscountDeatilUpdateWindow().marketingEventEntity = json.priceDiscountVo.marketingEventEntity;
					me.parent.getPriceDiscountDeatilUpdateWindow().channelEntityList = json.priceDiscountVo.channelEntityList;
					me.parent.getPriceDiscountDeatilUpdateWindow().show();
				};
				var failureFun = function(json){
					if(Ext.isEmpty(json)){
						pricing.showErrorMes(pricing.priceDiscount.i18n('i18n.pricingRegion.requestTimeOut'));
					}else{
						pricing.showErrorMes(json.message);
					}
				};
				var url = pricing.realPath('selectMarketingByPrimaryKey.action');
				pricing.requestJsonAjax(url,params,successFun,failureFun);
			} 
		}];
		me.callParent([cfg]);
	}
});
/**
 * 折扣方案明细-查看
 */
Ext.define('Foss.pricing.priceDiscount.PriceDiscountDeatilShowWindow',{
	extend : 'Ext.window.Window',
	title : pricing.priceDiscount.i18n('foss.pricing.viewDiscountProgram'),//查看折扣方案
	resizable:false,
	marketingEventEntity:null,//折扣方案主信息
	channelEntityList:null,//渠道信息
	parent:null,//(Foss.pricing.priceDiscount.PriceDiscountGridPanel)
	closeAction : 'hide',
	width :650,
	height :720,	
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	listeners:{
		beforehide:function(me){
			me.getPriceDiscountShowTab().getPriceDiscountEditFormPanel().getForm().reset();
			me.getPriceDiscountShowTab().getPricingDiscountDetailShowPanel().getQueryPriceDiscountDetailForm().getForm().reset();
			me.getPriceDiscountShowTab().getPricingDiscountDetailShowPanel().getPriceDiscountDetailGridPanel().getStore().removeAll();
		},
		beforeshow:function(me){
			var form = me.getPriceDiscountShowTab().getPriceDiscountEditFormPanel();
			if(!Ext.isEmpty(me.marketingEventEntity)){
				form.getForm().loadRecord(new Foss.pricing.priceDiscount.MarketingEventEntity(me.marketingEventEntity));//加载数据
			}else{
				pricing.showErrorMes(pricing.priceDiscount.i18n('foss.pricing.noMainDiscountProgram'));//没有折扣方案主信息！
				return;
			}
			if(!Ext.isEmpty(me.channelEntityList)){//设置渠道（TODO）
				for(var i = 0;i<me.channelEntityList.length;i++){
					form.getForm().findField(me.channelEntityList[i].salesChannelCode).setValue(true);//渠道元素的name就是渠道的code
				}
			}
		}
	},
	//折扣方案明细新增
	priceDiscountShowTab:null,
	getPriceDiscountShowTab:function(){
		if(Ext.isEmpty(this.priceDiscountAddTab)){
    		this.priceDiscountAddTab = Ext.create('Foss.pricing.priceDiscount.PriceDiscountShowTab');
    	}
    	return this.priceDiscountAddTab;
	},
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [me.getPriceDiscountShowTab()];
		me.fbar = [{
			text : pricing.priceDiscount.i18n('i18n.pricingRegion.returnGrid'),
			handler :function(){
				me.close();
			} 
		}];
		me.callParent([cfg]);
	}
});
/**
 * 折扣方案明细-新增
 */
Ext.define('Foss.pricing.priceDiscount.PriceDiscountDeatilAddWindow',{
	extend : 'Ext.window.Window',
	title : pricing.priceDiscount.i18n('foss.pricing.definitionOfPriceDiscounts'),//价格折扣定义
	closable : true,
	modal : true,
	startAllNet:null,//全网
	arrvAllNet:null,//全网
	resizable:false,
	marketingEventEntity:null,//显示的折扣主信息
	channelEntityList:null,//渠道信息
	parent:null,//(Foss.pricing.priceDiscount.PriceDiscountGridPanel)
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
			me.getPriceDiscountAddTab().getPriceDiscountEditFormPanel().getForm().reset();//清除折扣方案主信息
			me.getPriceDiscountAddTab().getPriceDiscountDetailEditFormPanel().getForm().reset();//清除折扣方案明细信息
			me.marketingEventEntity = null;//清掉折扣主信息
			me.channelEntityList = [];
			me.getPriceDiscountAddTab().getStartArraiveGridPanel().getStartGrid().getStore().removeAll();//清除出发表数据
			if(me.getPriceDiscountAddTab().getStartArraiveGridPanel().getStartGrid().dockedItems.items[2] != undefined) {
				me.getPriceDiscountAddTab().getStartArraiveGridPanel().getStartGrid().dockedItems.items[2].items.items[8].reset();//全网不勾选
			}
			me.getPriceDiscountAddTab().getStartArraiveGridPanel().getArriveGrid().getStore().removeAll();//清除到达表数据
			if(me.getPriceDiscountAddTab().getStartArraiveGridPanel().getArriveGrid().dockedItems.items[2] != undefined) {
				me.getPriceDiscountAddTab().getStartArraiveGridPanel().getArriveGrid().dockedItems.items[2].items.items[8].reset();//全网不勾选
			}
		},
		beforeshow:function(me){
			var form = me.getPriceDiscountAddTab().getPriceDiscountEditFormPanel();
			if(!Ext.isEmpty(me.marketingEventEntity)){
				form.getForm().loadRecord(new Foss.pricing.priceDiscount.MarketingEventEntity(me.marketingEventEntity));//加载数据
			}else{
				pricing.showErrorMes(pricing.priceDiscount.i18n('foss.pricing.noMainDiscountProgram'));//没有折扣方案主信息！
				return;
			}
			if(!Ext.isEmpty(me.channelEntityList)){//设置渠道
				for(var i = 0;i<me.channelEntityList.length;i++){
					if(!Ext.isEmpty(form.getForm().findField(me.channelEntityList[i].salesChannelCode))){
						form.getForm().findField(me.channelEntityList[i].salesChannelCode).setValue(true);//渠道元素的name就是渠道的code
					}
				}
			}
			me.getPriceDiscountAddTab().getPricingDiscountDetailShowPanel().getPriceDiscountDetailGridPanel().getStore().removeAll();
		}
	},
	//折扣方案明细新增
	priceDiscountAddTab:null,
	getPriceDiscountAddTab:function(){
		if(Ext.isEmpty(this.priceDiscountAddTab)){
    		this.priceDiscountAddTab = Ext.create('Foss.pricing.priceDiscount.PriceDiscountAddTab');
    	}
    	return this.priceDiscountAddTab;
	},
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [me.getPriceDiscountAddTab()];
		me.fbar = [{
			text : pricing.priceDiscount.i18n('i18n.pricingRegion.returnGrid'),
			handler :function(){
				me.close();
			} 
		}];
		me.callParent([cfg]);
	}
});
/**
 * 折扣方案明细-查看
 */
Ext.define('Foss.pricing.priceDiscount.PriceDiscountDeatilShowWindow',{
	extend : 'Ext.window.Window',
	title : pricing.priceDiscount.i18n('foss.pricing.viewDiscountProgram'),//查看折扣方案
	resizable:false,
	marketingEventEntity:null,//折扣方案主信息
	channelEntityList:null,//渠道信息
	parent:null,//(Foss.pricing.priceDiscount.PriceDiscountGridPanel)
	closeAction : 'hide',
	width :650,
	height :720,	
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	listeners:{
		beforehide:function(me){
			me.getPriceDiscountShowTab().getPriceDiscountEditFormPanel().getForm().reset();
			me.getPriceDiscountShowTab().getPricingDiscountDetailShowPanel().getQueryPriceDiscountDetailForm().getForm().reset();
			me.getPriceDiscountShowTab().getPricingDiscountDetailShowPanel().getPriceDiscountDetailGridPanel().getStore().removeAll();
		},
		beforeshow:function(me){
			var form = me.getPriceDiscountShowTab().getPriceDiscountEditFormPanel();
			if(!Ext.isEmpty(me.marketingEventEntity)){
				form.getForm().loadRecord(new Foss.pricing.priceDiscount.MarketingEventEntity(me.marketingEventEntity));//加载数据
			}else{
				pricing.showErrorMes(pricing.priceDiscount.i18n('foss.pricing.noMainDiscountProgram'));//没有折扣方案主信息！
				return;
			}
			if(!Ext.isEmpty(me.channelEntityList)){//设置渠道
				for(var i = 0;i<me.channelEntityList.length;i++){
					var item = form.getForm().findField(me.channelEntityList[i].salesChannelCode);
					if(!Ext.isEmpty(item)){
						form.getForm().findField(me.channelEntityList[i].salesChannelCode).setValue(true);//渠道元素的name就是渠道的code
					}
				}
			}
		}
	},
	//折扣方案明细新增
	priceDiscountShowTab:null,
	getPriceDiscountShowTab:function(){
		if(Ext.isEmpty(this.priceDiscountAddTab)){
    		this.priceDiscountAddTab = Ext.create('Foss.pricing.priceDiscount.PriceDiscountShowTab');
    	}
    	return this.priceDiscountAddTab;
	},
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [me.getPriceDiscountShowTab()];
		me.fbar = [{
			text : pricing.priceDiscount.i18n('i18n.pricingRegion.returnGrid'),
			handler :function(){
				me.close();
			} 
		}];
		me.callParent([cfg]);
	}
});

/**
 * 查看折扣方案-TAB
 */
Ext.define('Foss.pricing.priceDiscount.PriceDiscountShowTab', {
	extend : 'Ext.tab.Panel',
	cls : 'innerTabPanel',
    flex:1,
    layout : {
		type : 'vbox',
		align : 'stretch'
	},
    //折扣方案主信息FORM
    priceDiscountEditFormPanel:null,
	getPriceDiscountEditFormPanel:function(){
		if(Ext.isEmpty(this.priceDiscountEditFormPanel)){
    		this.priceDiscountEditFormPanel = Ext.create('Foss.pricing.priceDiscount.PriceDiscountEditFormPanel',{
    			isShow:true
    		});
    		this.priceDiscountEditFormPanel.getForm().getFields().each(function(item){
    			item.setReadOnly(true);
    		});
    	}
    	return this.priceDiscountEditFormPanel;
	},
	//已选折扣信息结果集
	pricingDiscountDetailShowPanel:null,
	getPricingDiscountDetailShowPanel:function(){
		if(Ext.isEmpty(this.pricingDiscountDetailShowPanel)){
    		this.pricingDiscountDetailShowPanel = Ext.create('Foss.pricing.priceDiscount.PricingDiscountDetailShowPanel',{
    			isShow:true//表明只是查看
    		});
    	}
    	return this.pricingDiscountDetailShowPanel;
	},
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [me.getPriceDiscountEditFormPanel(),me.getPricingDiscountDetailShowPanel()];
		me.callParent([cfg]);
	}
});
/**
 * 新增折扣方案-TAB
 */
Ext.define('Foss.pricing.priceDiscount.PriceDiscountAddTab', {
	extend : 'Ext.tab.Panel',
	cls : 'innerTabPanel',
    flex:1,
    layout : {
		type : 'vbox',
		align : 'stretch'
	},
    //折扣方案主信息FORM
    priceDiscountEditFormPanel:null,
	getPriceDiscountEditFormPanel:function(){
		if(Ext.isEmpty(this.priceDiscountEditFormPanel)){
    		this.priceDiscountEditFormPanel = Ext.create('Foss.pricing.priceDiscount.PriceDiscountEditFormPanel',{
    			isShow:true
    		});
    		this.priceDiscountEditFormPanel.getForm().getFields().each(function(item){
    			item.setReadOnly(true);
    		});
    	}
    	return this.priceDiscountEditFormPanel;
	},
	 //维护折扣信息FORM
	priceDiscountDetailEditFormPanel:null,
	getPriceDiscountDetailEditFormPanel:function(){
		if(Ext.isEmpty(this.priceDiscountDetailEditFormPanel)){
    		this.priceDiscountDetailEditFormPanel = Ext.create('Foss.pricing.priceDiscount.PriceDiscountDetailEditFormPanel');
    	}
    	return this.priceDiscountDetailEditFormPanel;
	},
	//出发到达grid panel
	startArraiveGridPanel:null,
	getStartArraiveGridPanel:function(){
		if(Ext.isEmpty(this.startArraiveGridPanel)){
    		this.startArraiveGridPanel = Ext.create('Foss.pricing.priceDiscount.StartArraiveGridPanel');
    	}
    	return this.startArraiveGridPanel;
	},
	//已选折扣信息结果集
	pricingDiscountDetailShowPanel:null,
	getPricingDiscountDetailShowPanel:function(){
		if(Ext.isEmpty(this.pricingDiscountDetailShowPanel)){
    		this.pricingDiscountDetailShowPanel = Ext.create('Foss.pricing.priceDiscount.PricingDiscountDetailShowPanel',{
    			'isShow':false
    		});
    	}
    	return this.pricingDiscountDetailShowPanel;
	},
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [me.getPriceDiscountEditFormPanel(),me.getStartArraiveGridPanel(),me.getPriceDiscountDetailEditFormPanel(),me.getPricingDiscountDetailShowPanel()];
		me.callParent([cfg]);
	}
});
/**
 * 维护折扣信息
 */
Ext.define('Foss.pricing.priceDiscount.PriceDiscountDetailEditFormPanel', {
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
	//新增折扣方案明细
	commintPriceDiscountDetail:function(){
        var me = this;
        var startDiscountOrgEntityList = new Array();//出发部门
        var endDiscountOrgEntityList = new Array();//到达部门
        me.up('window').getPriceDiscountAddTab().getStartArraiveGridPanel().getStartGrid().getStore().each(function(record){
        	startDiscountOrgEntityList.push(record.data);
        });
        me.up('window').getPriceDiscountAddTab().getStartArraiveGridPanel().getArriveGrid().getStore().each(function(record){
        	endDiscountOrgEntityList.push(record.data);
        });
        if(startDiscountOrgEntityList.length<1&&Ext.isEmpty(me.up('window').startAllNet)){
        	pricing.showWoringMessage(pricing.priceDiscount.i18n('foss.pricing.pleaseElectDeparture'));//请选者出发地！
        	return;
        }
        if(endDiscountOrgEntityList.length<1&&Ext.isEmpty(me.up('window').arrvAllNet)){
        	pricing.showWoringMessage(pricing.priceDiscount.i18n('foss.pricing.pleaseChooseArr'));//请选者到达地！
        	return;
        }
        if(me.getForm().isValid()){
        	var priceDiscountDtoModel = new Foss.pricing.priceDiscount.PriceDiscountDto();
            me.getForm().updateRecord(priceDiscountDtoModel);//更新数据
          //如果开始范围大于结束范围！
            if(priceDiscountDtoModel.get('leftRange')>priceDiscountDtoModel.get('rightRange')){
            	pricing.showWoringMessage(pricing.priceDiscount.i18n('foss.pricing.leftRangeMoreRanRightRange'));//开始范围不能大于结束范围！
            	return;
            }
            if(priceDiscountDtoModel.get('discountRate')<100&&priceDiscountDtoModel.get('minFee')>0){
            	pricing.showWoringMessage(pricing.priceDiscount.i18n('foss.pricing.minFeeAnddiscountRateEqcelZero'));//直接减免和折扣率不能同时享受！
            	return;
            }
            if(priceDiscountDtoModel.get('discountRate')==100&&priceDiscountDtoModel.get('minFee')==0){
            	pricing.showWoringMessage(pricing.priceDiscount.i18n('foss.pricing.minFeeAnddiscountRateDengZero'));//直接减免和折扣率不能同时等于0！
            	return;
            }
            var beginTime = me.up('window').marketingEventEntity.beginTime;//开始日期
            priceDiscountDtoModel.set('beginDate',beginTime);
            var endTime = me.up('window').marketingEventEntity.endTime;//截止日期
            priceDiscountDtoModel.set('endDate',endTime);
            var marketId = me.up('window').marketingEventEntity.id;//折扣方案主信息ID
            priceDiscountDtoModel.set('marketId',marketId);
            var marketCode = me.up('window').marketingEventEntity.code;//折扣方案主信息CODE
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
				me.up('window').getPriceDiscountAddTab().getPricingDiscountDetailShowPanel().getPriceDiscountDetailGridPanel().getPagingToolbar().moveFirst();
            };
            var failureFun = function(json){
            	if(Ext.isEmpty(json)){
					pricing.showErrorMes(pricing.priceDiscount.i18n('i18n.pricingRegion.requestTimeOut'));//请求超时！
				}else{
					pricing.showErrorMes(json.message);
				}
            };
        	var url = pricing.realPath('addDiscountProgramItem.action');
			pricing.requestJsonAjax(url,param,successFun,failureFun);
        }  
	},
	constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		var allProduct = {'id':'ALL','code':'ALL','name':pricing.priceDiscount.i18n('foss.pricing.allProducts')};
//		var allDoodsType = {'id':'ALL','code':'ALL','name':pricing.priceDiscount.i18n('foss.pricing.allCargoTypes')};
		var productEntityList =  pricing.addAll(pricing.priceDiscount.productEntityList,allProduct);
//		var goodTypeList =  pricing.addAll(pricing.priceDiscount.goodTypeList,allDoodsType);
		var goodTypeList = pricing.priceDiscount.goodTypeList;
		me.items = [{
			name: 'caculateType',
			queryMode: 'local',
		    displayField: 'valueName',
		    valueField: 'valueCode',
		    editable:false,
		    productRecord:null,
		    store:pricing.getStore(null,null,['valueCode','valueName'],
		    pricing.priceDiscount.caculateType),
	        fieldLabel: pricing.priceDiscount.i18n('foss.pricing.discountRules'),//折扣规则
	        xtype : 'combo'
		},{
			xtype: 'displayfield',
	        value: '<span style="color:red">'
	        	+pricing.priceDiscount.i18n('foss.pricing.canWeightVolumeDirectCosts')
	        	+'</span>',//可以按重量、体积或者直接费用
	        margins: '0 0 0 10'
		},{
			name: 'productId',
			queryMode: 'local',
		    displayField: 'name',
		    valueField: 'id',
		    editable:false,
		    productRecord:null,//基础产品实体
		    store:pricing.getStore(null,'Foss.pricing.priceDiscount.ProductEntity',null
		    ,productEntityList),
	        fieldLabel: pricing.priceDiscount.i18n('foss.pricing.basicProducts'),//基础产品
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
	        	+pricing.priceDiscount.i18n('foss.pricing.productsPrecisionQatarAirways')
	        	+'</span>',//产品：精准卡航
	        margins: '0 0 0 10'
		},{
			name: 'goodsTypeId',
			queryMode: 'local',
		    displayField: 'name',
		    valueField: 'id',
		    editable:false,
		    goodsTypeRecord:null,
		    store:pricing.getStore(null,'Foss.pricing.priceDiscount.GoodsTypeEntity',null
		    ,goodTypeList),
	        fieldLabel: pricing.priceDiscount.i18n('foss.pricing.cargoType'),//货物类型
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
	        	+pricing.priceDiscount.i18n('foss.pricing.seafoodEquipmentAndTheLike')
	        	+'</span>',//海鲜、器材之类
	        margins: '0 0 0 10'
		},{
			xtype:'numberfield',
	        decimalPrecision:2,
	        allowBlank:false,
	        name:'leftRange',
	        fieldLabel:pricing.priceDiscount.i18n('foss.pricing.beginningOfTheRange'),//开始范围
	        step:0.01,
	        maxValue: 99999999.99,
	        minValue: 0 
		},{
			xtype: 'displayfield',
			value: '<span style="color:red">'
	        	+pricing.priceDiscount.i18n('foss.pricing.rangeKgCubicMetersPerDollar')
	        	+'</span>',//范围（公斤立方米每元）
	        margins: '0 0 0 10'
		},{
			xtype:'numberfield',
	        decimalPrecision:2,
	        name:'rightRange',
	        fieldLabel:pricing.priceDiscount.i18n('foss.pricing.endOfTheRange'),//结束范围
	        step:0.01,
	        maxValue: 99999999.99,
	        minValue: 0 
		},{
			xtype: 'displayfield',
			value: '<span style="color:red">'
	        	+pricing.priceDiscount.i18n('foss.pricing.rangeKgCubicMetersPerDollar')
	        	+'</span>',//范围（公斤立方米每元）
	        margins: '0 0 0 10'
		},{
			xtype:'numberfield',
	        decimalPrecision:2,
	        allowBlank:false,
	        name:'discountRate',
	        value:100,
	        fieldLabel:pricing.priceDiscount.i18n('foss.pricing.theDiscountRate'),//折扣率
	        maxValue: 100,
	        step:0.01,
	        minValue: 0
		},{
			xtype: 'displayfield',
			value: '<span style="color:red">'
	        	+'%'
	        	+'</span>',//%
	        margins: '0 0 0 10'
		},{
			xtype:'numberfield',
	        decimalPrecision:2,
	        name:'minFee',
	        allowBlank:false,
	        fieldLabel:pricing.priceDiscount.i18n('foss.pricing.directReliefYuan'),
	        maxValue: 99999999.99,
	        minValue: 0 
		},{
			xtype: 'displayfield',
			value: '',
	        margins: '0 0 0 10'
		}];
		me.fbar = [{
			text : pricing.priceDiscount.i18n('i18n.pricingRegion.commit'),
			cls:'yellow_button',
			margin:'0 0 0 360',
			handler :function(){
				me.commintPriceDiscountDetail();
			} 
		}];
		me.callParent([cfg]);
	}
});
/**
 * 折扣方案主信息
 */
Ext.define('Foss.pricing.priceDiscount.PriceDiscountEditFormPanel', {
	extend : 'Ext.form.Panel',
	frame: true,
	title:' 价格折扣方案定义',//价格折扣方案定义
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
	constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		var channel = FossDataDictionary.getDataDictionaryStore(pricing.priceDiscount.channel);
		var channelItems = new Array();
		channel.each(function(record){
			var item = { boxLabel:record.get('valueName'), name:record.get('valueCode'),channelEntity:record.data };
			channelItems.push(item);
		});
		var minValueDate = null;
		if(!config.isShow){
			minValueDate = new Date(pricing.priceDiscount.tomorrowTime);
		}
		me.items = [{
			name: 'code',
		    fieldLabel:'<span style="color:red">*</span>编码',//编码
		    readOnly:true,
		    allowBlank:true,
		    emptyText:pricing.priceDiscount.i18n('foss.pricing.automaticallyGeneratedCoding'),//自动生成编码
	        xtype : 'textfield'
		},{
			name: 'name',
			maxLength:20,
		    fieldLabel:pricing.priceDiscount.i18n('foss.pricing.scenarioName'),//方案名称
	        xtype : 'textfield'
		},{
	        xtype: 'checkboxgroup',
	        fieldLabel:pricing.priceDiscount.i18n('foss.pricing.orderChannels'),//订单渠道
	        width:500,
	        allowBlank:true,
	        vertical: true,
	        items:channelItems
	    },{
			name: 'beginTime',
			colspan: 1,
			format:'Y-m-d',
			minValue:minValueDate,
		    fieldLabel:pricing.priceDiscount.i18n('foss.pricing.discountPeriod'),//折扣期限
	        xtype : 'datefield'
		},{
			name: 'endTime',
			colspan: 1,
			labelWidth:20,
			minValue:minValueDate,
			format:'Y-m-d',
		    fieldLabel:pricing.priceDiscount.i18n('foss.pricing.to'),//至
	        xtype : 'datefield'
		},{
			name: 'remark',
			width:400,
			maxLength:200,
			allowBlank:true,
		    fieldLabel:pricing.priceDiscount.i18n('foss.pricing.priceDiscountProgramDescription'),//价格折扣方案说明
	        xtype : 'textareafield'
		}];
		me.callParent([cfg]);
	}
});
//出发和到达PANEL
Ext.define('Foss.pricing.priceDiscount.StartArraiveGridPanel',{
	extend : 'Ext.panel.Panel',
	//frame: true,
	title : pricing.priceDiscount.i18n('foss.pricing.departureAndArrival'),//出发和到达
	flex:1,
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	//出发GRID
	startGrid:null,
	getStartGrid:function(){
		if(Ext.isEmpty(this.startGrid)){
    		this.startGrid = Ext.create('Foss.pricing.priceDiscount.StartGrid');
    	}
    	return this.startGrid;
	},
	//到达GRID
	arriveGrid:null,
	getArriveGrid:function(){
		if(Ext.isEmpty(this.arriveGrid)){
    		this.arriveGrid = Ext.create('Foss.pricing.priceDiscount.ArriveGrid');
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
Ext.define('Foss.pricing.priceDiscount.PricingDiscountDetailShowPanel',{
	extend : 'Ext.panel.Panel',
	title : pricing.priceDiscount.i18n('foss.pricing.theDiscountSelectedInformationResults'),//已选折扣信息结果
	//frame: true,
	flex:1,
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	//已选折扣信息结果-查询条件
	queryPriceDiscountDetailForm:null,
	getQueryPriceDiscountDetailForm:function(){
		if(Ext.isEmpty(this.queryPriceDiscountDetailForm)){
    		this.queryPriceDiscountDetailForm = Ext.create('Foss.pricing.priceDiscount.QueryPriceDiscountDetailForm');
    	}
    	return this.queryPriceDiscountDetailForm;
	},
	//已选折扣信息结果GRID
	priceDiscountDetailGridPanel:null,
	getPriceDiscountDetailGridPanel:function(isShow){
		if(Ext.isEmpty(this.priceDiscountDetailGridPanel)){
    		this.priceDiscountDetailGridPanel = Ext.create('Foss.pricing.priceDiscount.PriceDiscountDetailGridPanel',{
    			'isShow':isShow
    		});
    	}
    	return this.priceDiscountDetailGridPanel;
	},
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [me.getQueryPriceDiscountDetailForm(),me.getPriceDiscountDetailGridPanel(config.isShow)];
		me.callParent([cfg]);
	}
});
//出发GRID
Ext.define('Foss.pricing.priceDiscount.StartGrid', {
   extend:'Ext.grid.Panel', 
   title:pricing.priceDiscount.i18n('foss.pricing.departure'),
   frame: true,
   autoScroll:true,
   sortableColumns:false,
   enableColumnHide:false,
   enableColumnMove:false,
   flex:1,
	//添加区域
   priceDiscountRegionGridShowWindow:null,
   getPriceDiscountRegionGridShowWindow:function(){
		if(Ext.isEmpty(this.priceDiscountRegionGridShowWindow)){
   		     this.priceDiscountRegionGridShowWindow = Ext.create('Foss.pricing.priceDiscount.PriceDiscountRegionGridShowWindow');
   		     this.priceDiscountRegionGridShowWindow.parent = this;
   		     this.priceDiscountRegionGridShowWindow.type = pricing.priceDiscount.priceDiscountStart;
	   	}
	   	return this.priceDiscountRegionGridShowWindow;
    },
     //添加城市
    priceDiscountCityGridShowWindow:null,
    getPriceDiscountCityGridShowWindow:function(){
 		if(Ext.isEmpty(this.priceDiscountCityGridShowWindow)){
    		     this.priceDiscountCityGridShowWindow = Ext.create('Foss.pricing.priceDiscount.PriceDiscountCityGridShowWindow');
    		     this.priceDiscountCityGridShowWindow.parent = this;
    		     this.priceDiscountCityGridShowWindow.type = pricing.priceDiscount.priceDiscountStart;
 	   	}
 	   	return this.priceDiscountCityGridShowWindow;
     },
    orgWindow:null,
    getOrgWindow:function(){
    	var me = this;
    	if(Ext.isEmpty(this.orgWindow)){
    		this.orgWindow = Ext.create('Foss.baseinfo.commonSelector.orgSelectorWindow',{
    			type:'ORG',
    			salesDepartment:'Y',
    			active:'Y',
    			modal:true,
    			commitFun:function(){
    				var selections = this.getGridRecord();
    				if(selections.length==0){
    					dict.showErrorMes(pricing.priceDiscount.i18n('foss.pricing.pleaseSelectLeastOneData'));//请至少选择一条数据！
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
							var data = {'deptOrgId':selections[i].get('id'),'name':selections[i].get('name'),'deptOrgCode':selections[i].get('code'),'deptOrgTypeCode':pricing.priceDiscount.priceDiscountDetailTypeDept};
							datas.push(data);
						}
					};
					var oldData = me.getStore().getAt(0)//清掉不是一类的数据
 					if(!Ext.isEmpty(oldData)){
 						if(oldData.get('deptOrgTypeCode')!=pricing.priceDiscount.priceDiscountDetailTypeDept){
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
				name : pricing.priceDiscount.i18n('foss.pricing.internalOrganization')//内部组织
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
				text : pricing.priceDiscount.i18n('i18n.pricingRegion.opra'),//操作
				xtype:'actioncolumn',
				align: 'center',
				width:80,
				items: [{
						iconCls: 'deppon_icons_delete',
		                tooltip: pricing.priceDiscount.i18n('foss.pricing.delete'),//删除
						width:42,
		                handler: function(grid, rowIndex, colIndex) {
		            		//获取选中的数据
		    				var record=grid.getStore().getAt(rowIndex);
		            		pricing.showQuestionMes(pricing.priceDiscount.i18n('foss.pricing.wantDeleteThisData'),function(e){//是否要删除这个数据？
		            			if(e=='yes'){//询问是否删除，是则发送请求
		            				grid.getStore().remove(record);
		            			}
		            		});
		                }
					}]
			  },{ 
		        	  header:pricing.priceDiscount.i18n('foss.pricing.optionalProducts')//可选产品
		        	  ,dataIndex: 'name'
	          },{ 
	        	  header:pricing.priceDiscount.i18n('foss.pricing.type')
	        	  ,dataIndex: 'deptOrgTypeCode'
	              ,renderer:function(value){
	            	  if(pricing.priceDiscount.priceDiscountDetailTypeDept==value){
	            		  return pricing.priceDiscount.i18n('foss.pricing.dept');
	            	  }else if(pricing.priceDiscount.priceDiscountDetailTypeRegion==value){
	            		  return pricing.priceDiscount.i18n('foss.pricing.region');
	            	  }else if(pricing.priceDiscount.priceDiscountDetailTypeCity==value){
	            		  return pricing.priceDiscount.i18n('i18n.pricingRegion.city');
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
			text : pricing.priceDiscount.i18n('foss.pricing.delete'),
			handler :function(){
				//获取选中的数据
				var selections = me.getSelectionModel().getSelection();
				if(selections.length<1){
					pricing.showErrorMes(pricing.priceDiscount.i18n('foss.pricing.pleaseSelectDataYouWantDelete'));//请选择要删除的数据！
					return;
				}
				pricing.showQuestionMes(pricing.priceDiscount.i18n('foss.pricing.wwantDeleteTheseData'),function(e){//是否要删除这些数据？
					if(e=='yes'){//询问是否删除，是则发送请求
						me.getStore().remove(selections);
					}
				});
			} 
		},'-', {
			//添加区域
			text : pricing.priceDiscount.i18n('foss.pricing.addARegion'),
			handler :function(){
				me.getPriceDiscountRegionGridShowWindow().show();
			} 
		},'-', {
			//添加城市
			text : pricing.priceDiscount.i18n('foss.pricing.addACity'),
			handler :function(){
				me.getPriceDiscountCityGridShowWindow().show();
			} 
		},'-', {
			//添加营业部
			text : pricing.priceDiscount.i18n('foss.pricing.addBusinessDepartment'),
			handler :function(){
				me.getOrgWindow().show();
			} 
		},'-', {
			xtype:'checkbox',
			boxLabel : pricing.priceDiscount.i18n('foss.pricing.allNet'),
			listeners:{
				change:function(check,newValue,oldValue){
					if(newValue){
						check.up('window').startAllNet = pricing.priceDiscount.allNet;
						check.up('grid').getStore().removeAll();
						check.up('grid').dockedItems.items[2].items.items[0].setDisabled(true);
						check.up('grid').dockedItems.items[2].items.items[2].setDisabled(true);
						check.up('grid').dockedItems.items[2].items.items[4].setDisabled(true);
						check.up('grid').dockedItems.items[2].items.items[6].setDisabled(true);
					}else{
						check.up('window').startAllNet = null;
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
//到达GRID
Ext.define('Foss.pricing.priceDiscount.ArriveGrid', {
   extend:'Ext.grid.Panel', 
   frame: true,
   title:pricing.priceDiscount.i18n('foss.pricing.reach'),
   sortableColumns:false,
   enableColumnHide:false,
   enableColumnMove:false,
   autoScroll:true,
   flex:1,
   //添加区域
   priceDiscountRegionGridShowWindow:null,
   getPriceDiscountRegionGridShowWindow:function(){
		if(Ext.isEmpty(this.priceDiscountRegionGridShowWindow)){
   		     this.priceDiscountRegionGridShowWindow = Ext.create('Foss.pricing.priceDiscount.PriceDiscountRegionGridShowWindow');
   		     this.priceDiscountRegionGridShowWindow.parent = this;
   		     this.priceDiscountRegionGridShowWindow.type = pricing.priceDiscount.priceDiscountArrive;
	   	}
	   	return this.priceDiscountRegionGridShowWindow;
    },
    //添加城市
    priceDiscountCityGridShowWindow:null,
    getPriceDiscountCityGridShowWindow:function(){
 		if(Ext.isEmpty(this.priceDiscountCityGridShowWindow)){
    		     this.priceDiscountCityGridShowWindow = Ext.create('Foss.pricing.priceDiscount.PriceDiscountCityGridShowWindow');
    		     this.priceDiscountCityGridShowWindow.parent = this;
    		     this.priceDiscountCityGridShowWindow.type = pricing.priceDiscount.priceDiscountArrive;
 	   	}
 	   	return this.priceDiscountCityGridShowWindow;
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
     					dict.showErrorMes(pricing.priceDiscount.i18n('foss.pricing.pleaseSelectLeastOneData'));//请至少选择一条数据！
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
 							var data = {'arrvOrgId':selections[i].get('id'),'name':selections[i].get('name'),'arrvOrgCode':selections[i].get('code'),'arrvOrgTypeCode':pricing.priceDiscount.priceDiscountDetailTypeDept};
 							datas.push(data);
 						}
 					};
 					var oldData = me.getStore().getAt(0)//清掉不是一类的数据
 					if(!Ext.isEmpty(oldData)){
 						if(oldData.get('arrvOrgTypeCode')!=pricing.priceDiscount.priceDiscountDetailTypeDept){
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
				text : pricing.priceDiscount.i18n('i18n.pricingRegion.opra'),//操作
				xtype:'actioncolumn',
				align: 'center',
				width:80,
				items: [{
						iconCls: 'deppon_icons_delete',
		                tooltip: pricing.priceDiscount.i18n('foss.pricing.delete'),//删除
						width:42,
		                handler: function(grid, rowIndex, colIndex) {
		            		//获取选中的数据
		    				var record=grid.getStore().getAt(rowIndex);
		    				pricing.showQuestionMes(pricing.priceDiscount.i18n('foss.pricing.wantDeleteThisData'),function(e){//是否要删除这个数据？
		            			if(e=='yes'){//询问是否删除，是则发送请求
		            				grid.getStore().remove(record);
		            			}
		            		})
		                }
					}]
			  },{ 
	        	  header:pricing.priceDiscount.i18n('foss.pricing.optionalProducts')//可选产品
	        	  ,dataIndex: 'name'
	          },{ 
	        	  header:pricing.priceDiscount.i18n('foss.pricing.type')
	        	  ,dataIndex: 'arrvOrgTypeCode'
	              ,renderer:function(value){
	            	  if(pricing.priceDiscount.priceDiscountDetailTypeDept==value){
	            		  return pricing.priceDiscount.i18n('foss.pricing.dept');//部门
	            	  }else if(pricing.priceDiscount.priceDiscountDetailTypeRegion==value){
	            		  return pricing.priceDiscount.i18n('foss.pricing.region');//区域
	            	  }else if(pricing.priceDiscount.priceDiscountDetailTypeCity==value){
	            		  return pricing.priceDiscount.i18n('i18n.pricingRegion.city');//城市
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
		me.store = pricing.getStore(null,null,['arrvOrgId','name','arrvOrgCode','arrvOrgTypeCode'],[]),
		me.tbar = [{
			//删除
			text : pricing.priceDiscount.i18n('foss.pricing.delete'),
			handler :function(){
				//获取选中的数据
				var selections = me.getSelectionModel().getSelection();
				if(selections.length<1){
					pricing.showErrorMes(pricing.priceDiscount.i18n('foss.pricing.pleaseSelectDataYouWantDelete'));//请选择要删除的数据！
					return;
				}
				pricing.showQuestionMes(pricing.priceDiscount.i18n('foss.pricing.wwantDeleteTheseData'),function(e){//是否要删除这些数据？
					if(e=='yes'){//询问是否删除，是则发送请求
						me.getStore().remove(selections);
					}
				});
			} 
		},'-', {
			//添加区域
			text : pricing.priceDiscount.i18n('foss.pricing.addARegion'),
			handler :function(){
				me.getPriceDiscountRegionGridShowWindow().show();
			} 
		},'-', {
			//添加城市
			text : pricing.priceDiscount.i18n('foss.pricing.addACity'),
			handler :function(){
				me.getPriceDiscountCityGridShowWindow().show();
			} 
		},'-', {
			//添加营业部
			text : pricing.priceDiscount.i18n('foss.pricing.addBusinessDepartment'),
			handler :function(){
				me.getOrgWindow().show();
			} 
		},'-', {
			xtype:'checkbox',
			boxLabel : pricing.priceDiscount.i18n('foss.pricing.allNet'),
			listeners:{
				change:function(check,newValue,oldValue){
					if(newValue){
						check.up('window').arrvAllNet = pricing.priceDiscount.allNet;
						check.up('grid').getStore().removeAll();
						check.up('grid').dockedItems.items[2].items.items[0].setDisabled(true);
						check.up('grid').dockedItems.items[2].items.items[2].setDisabled(true);
						check.up('grid').dockedItems.items[2].items.items[4].setDisabled(true);
						check.up('grid').dockedItems.items[2].items.items[6].setDisabled(true);
					}else{
						check.up('window').arrvAllNet = null;
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
 * 已选折扣信息结果-查询FORM
 */
Ext.define('Foss.pricing.priceDiscount.QueryPriceDiscountDetailForm', {
	extend : 'Ext.form.Panel',
	title: pricing.priceDiscount.i18n('i18n.pricingRegion.searchCondition'),//查询条件
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
		var all = {'id':'','name':pricing.priceDiscount.i18n('i18n.pricingRegion.all')};
		var allProduct = {'id':'ALL','code':'ALL','name':pricing.priceDiscount.i18n('foss.pricing.allProducts')};
//		var allDoodsType = {'id':'ALL','code':'ALL','name':pricing.priceDiscount.i18n('foss.pricing.allCargoTypes')};
		var productEntityList =  pricing.addAll(pricing.priceDiscount.productEntityList,all);
		var productEntityList =  pricing.addAll(pricing.priceDiscount.productEntityList,allProduct);
//		var goodTypeList =  pricing.addAll(pricing.priceDiscount.goodTypeList,all);
//		var goodTypeList =  pricing.addAll(pricing.priceDiscount.goodTypeList,allDoodsType);
		var goodTypeList = pricing.priceDiscount.goodTypeList;
		me.items  = [{
			name: 'deptOrgName',
		    fieldLabel:pricing.priceDiscount.i18n('foss.pricing.departure'),//出发
	        xtype : 'textfield'
		},{
			name: 'arrvOrgName',
		    fieldLabel:pricing.priceDiscount.i18n('foss.pricing.reach'),//到达
	        xtype : 'textfield'
		},{
			name: 'active',
			queryMode: 'local',
		    displayField: 'valueName',
		    valueField: 'valueCode',
		    editable:false,
		    value:'',
		    store:pricing.getStore(null,null,['valueCode','valueName']
		    ,[{'valueCode':'Y','valueName':pricing.priceDiscount.i18n('i18n.pricingRegion.active')}//激活
		    ,{'valueCode':'N','valueName':pricing.priceDiscount.i18n('i18n.pricingRegion.unActive')}//未激活
		    ,{'valueCode':'','valueName':pricing.priceDiscount.i18n('i18n.pricingRegion.all')}]),//全部
	        fieldLabel: pricing.priceDiscount.i18n('foss.pricing.status'),//状态
	        xtype : 'combo'
		},{
			name: 'productId',
			queryMode: 'local',
		    displayField: 'name',
		    valueField: 'id',
		    value:'',
		    editable:false,
		    productRecord:null,//基础产品实体
		    store:pricing.getStore(null,'Foss.pricing.priceDiscount.ProductEntity',null
		    ,productEntityList),
	        fieldLabel: pricing.priceDiscount.i18n('foss.pricing.basicProducts'),//基础产品
	        xtype : 'combo'
		},{
			name: 'goodsTypeId',
			queryMode: 'local',
		    displayField: 'name',
		    valueField: 'id',
		    editable:false,
		    goodsTypeRecord:null,
		    value:'',
		    store:pricing.getStore(null,'Foss.pricing.priceDiscount.GoodsTypeEntity',null
		    ,goodTypeList),
	        fieldLabel: pricing.priceDiscount.i18n('foss.pricing.cargoType'),//货物类型
	        xtype : 'combo'
		}];
		me.fbar = [{
			xtype : 'button', 
			width:70,
			text : pricing.priceDiscount.i18n('foss.pricing.reset'),
			handler : function() {
				me.getForm().reset();
			}
		},{
			xtype : 'button', 
			width:70,
			text : pricing.priceDiscount.i18n('i18n.pricingRegion.search'),
			margin:'0 0 0 375',
			cls:'yellow_button',
			handler : function() {
				if(me.getForm().isValid()){
					var grid = me.up('panel').getPriceDiscountDetailGridPanel();
					grid.getStore().load();
				}
			}
		}]
		me.callParent([cfg]);
	}
});
/**
 * 折扣方案明细列表
 */
Ext.define('Foss.pricing.priceDiscount.PriceDiscountDetailGridPanel', {
	extend: 'Ext.grid.Panel',
	title : pricing.priceDiscount.i18n('i18n.pricingRegion.searchResults'),//查询结果
	frame: true,
	flex:1,
	autoScroll:true,
	isShow:false,//是否是查看
    sortableColumns:false,
    enableColumnHide:false,
    enableColumnMove:false,
	stripeRows : true, // 交替行效果
	selType : "rowmodel", // 选择类型设置为：行选择
	emptyText: pricing.priceDiscount.i18n('foss.pricing.theQueryResultIsEmpty'),//查询结果为空
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
	priceDiscountDeatilUpdateWindow : null,
	getPriceDiscountDeatilUpdateWindow : function() {
		if (Ext.isEmpty(this.priceDiscountDeatilUpdateWindow)) {
			this.priceDiscountDeatilUpdateWindow = Ext.create('Foss.pricing.priceDiscount.PriceDiscountDeatilUpdateWindow');
			this.priceDiscountDeatilUpdateWindow.parent = this;
		}
		return this.priceDiscountDeatilUpdateWindow;
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.columns = [{xtype: 'rownumberer',
			width:40,
			text : pricing.priceDiscount.i18n('i18n.pricingRegion.num')//序号
		},{
			text : pricing.priceDiscount.i18n('i18n.pricingRegion.opra'),//操作
			//dataIndex : 'id',
			hidden:config.isShow,
			xtype:'actioncolumn',
			align: 'center',
			width:80,
			items: [{
				iconCls: 'deppon_icons_edit',
                tooltip: pricing.priceDiscount.i18n('foss.pricing.update'),//修改
				width:42,
                handler: function(grid,rowIndex,colIndex) {
                	//获取选中的数据
    				var record=grid.getStore().getAt(rowIndex);
                	var priceValuationId = record.get('priceValuationId');//计费规则ID
    				var params = {'priceDiscountVo':{'priceDiscountDto':{'priceValuationId':priceValuationId}}};
    				var successFun = function(json){
    					var updateWindow = me.getPriceDiscountDeatilUpdateWindow();//获得修改窗口
    					updateWindow.priceDiscountDto = json.priceDiscountVo.priceDiscountDto;//折扣方案明细
    					updateWindow.show();//显示修改窗口
    				};
    				var failureFun = function(json){
    					if(Ext.isEmpty(json)){
    						pricing.showErrorMes(pricing.priceDiscount.i18n('foss.pricing.requestTimedOut'));//请求超时
    					}else{
    						pricing.showErrorMes(json.message);
    					}
    				};
    				var url = pricing.realPath('selectPriceValuation.action');
    				pricing.requestJsonAjax(url,params,successFun,failureFun);
                }
			},{
				iconCls: 'deppon_icons_delete',
                tooltip: pricing.priceDiscount.i18n('foss.pricing.delete'),//删除
				width:42,
                handler: function(grid, rowIndex, colIndex) {
            		//获取选中的数据
    				var record=grid.getStore().getAt(rowIndex);
            		pricing.showQuestionMes(pricing.priceDiscount.i18n('foss.pricing.wantDeleteThisDiscountDetails'),function(e){//是否要删除这个折扣明细？
            			if(e=='yes'){//询问是否删除，是则发送请求
            				var priceValuationId = record.get('priceValuationId');//计费规则ID
            				var params = {'priceDiscountVo':{'priceDiscountDto':{'priceValuationId':priceValuationId}}};
            				var successFun = function(json){
            					pricing.showInfoMes(json.message);
            					me.getPagingToolbar().moveFirst();
            				};
            				var failureFun = function(json){
            					if(Ext.isEmpty(json)){
            						pricing.showErrorMes(pricing.priceDiscount.i18n('i18n.pricingRegion.requestTimeOut'));//请求超时！
            					}else{
            						pricing.showErrorMes(json.message);
            					}
            				};
            				var url = pricing.realPath('deleteDiscountProgramItem.action');
            				pricing.requestJsonAjax(url,params,successFun,failureFun);
            			}
            		})
                }
			}]
		},{
			text : pricing.priceDiscount.i18n('foss.pricing.departure'),//出发
			dataIndex : 'deptOrgName'
		},{
			text : pricing.priceDiscount.i18n('foss.pricing.reach'),//到达
			dataIndex : 'arrvOrgName'
		},{
			text : pricing.priceDiscount.i18n('foss.pricing.startingType'),//出发类型
			dataIndex : 'deptOrgTypeCode',
			renderer:function(value){
				if(value == pricing.priceDiscount.priceDiscountDetailTypeDept){
					return pricing.priceDiscount.i18n('foss.pricing.dept');//部门
				}else if(value == pricing.priceDiscount.priceDiscountDetailTypeRegion){
					return pricing.priceDiscount.i18n('foss.pricing.region');//区域
				}else if(value == pricing.priceDiscount.priceDiscountDetailTypeCity){
					return pricing.priceDiscount.i18n('i18n.pricingRegion.city');//城市
				}else if(value == pricing.priceDiscount.priceDiscountDetailTypeAllNet){
					return pricing.priceDiscount.i18n('foss.pricing.allNet');//全网
				}else{
					return '';
				}
			}
		},{
			text : pricing.priceDiscount.i18n('foss.pricing.reachTheType'),//到达类型
			dataIndex : 'arrvOrgTypeCode',
			renderer:function(value){
				if(value == pricing.priceDiscount.priceDiscountDetailTypeDept){
					return pricing.priceDiscount.i18n('foss.pricing.dept');//部门
				}else if(value == pricing.priceDiscount.priceDiscountDetailTypeRegion){
					return pricing.priceDiscount.i18n('foss.pricing.region');//区域
				}else if(value == pricing.priceDiscount.priceDiscountDetailTypeCity){
					return pricing.priceDiscount.i18n('i18n.pricingRegion.city');//城市
				}else if(value == pricing.priceDiscount.priceDiscountDetailTypeAllNet){
					return pricing.priceDiscount.i18n('foss.pricing.allNet');//全网
				}else{
					return '';
				}
			}
		},{
			text : pricing.priceDiscount.i18n('foss.pricing.beginningOfTheRange'),//开始范围
			dataIndex : 'leftRange'
		},{
			text : pricing.priceDiscount.i18n('foss.pricing.endOfTheRange'),//结束范围
			dataIndex : 'rightRange'
		},{
			text : pricing.priceDiscount.i18n('foss.pricing.discountRules'),//折扣规则
			dataIndex : 'caculateType',
			renderer:function(value){
				return pricing.changeCodeToName(pricing.priceDiscount.caculateType,value);
			}
		},{
			text : pricing.priceDiscount.i18n('foss.pricing.product'),//产品
			dataIndex : 'productName'
		},{
			text : pricing.priceDiscount.i18n('foss.pricing.theDiscountRate'),//折扣率
			dataIndex : 'discountRate'
		},{
			text : pricing.priceDiscount.i18n('foss.pricing.theLowestVotesdirectRelief'),//最低减免
			dataIndex : 'minFee'
		},{
			text :pricing.priceDiscount.i18n('foss.pricing.status'),//状态
			dataIndex : 'active',
			width:50,
			renderer:function(value){
				if(value=='Y'){//'Y'表示激活
					return pricing.priceDiscount.i18n('i18n.pricingRegion.active');
				}else if(value=='N'){//'N'表示未激活
					return  pricing.priceDiscount.i18n('i18n.pricingRegion.unActive');
				}else{
					return '';
				}
			}
		}];
		me.store = Ext.create('Foss.pricing.priceDiscount.priceDiscountDetailStore',{
			autoLoad : false,
			pageSize : 5,
			listeners: {
				beforeload: function(store, operation, eOpts){
					var queryForm = me.up().getQueryPriceDiscountDetailForm();
					var marketId = me.up('window').marketingEventEntity.id;
					if(queryForm!=null){
						Ext.apply(operation,{
							params : {
								'priceDiscountVo.priceDiscountDto.deptOrgName':queryForm.getForm().findField('deptOrgName').getValue(),//出发
								'priceDiscountVo.priceDiscountDto.arrvOrgName':queryForm.getForm().findField('arrvOrgName').getValue(),//到达
								'priceDiscountVo.priceDiscountDto.active':queryForm.getForm().findField('active').getValue(),//状态
								'priceDiscountVo.priceDiscountDto.productId':queryForm.getForm().findField('productId').getValue(),//产品ID
								'priceDiscountVo.priceDiscountDto.goodsTypeId':queryForm.getForm().findField('goodsTypeId').getValue(),//货物类型ID
								'priceDiscountVo.priceDiscountDto.marketId':marketId//折扣方案主信息ID
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
 * 折扣方案明细-修改(单独修改)
 */
Ext.define('Foss.pricing.priceDiscount.PriceDiscountDeatilUpdateWindow',{
	extend : 'Ext.window.Window',
	title : pricing.priceDiscount.i18n('foss.pricing.definitionOfPriceDiscounts'),//价格折扣定义
	closable : true,
	modal : true,
	resizable:false,
	priceDiscountDto:null,//明细
	parent:null,//(Foss.pricing.priceDiscount.PriceDiscountDetailGridPanel)
	closeAction : 'hide',
	width :450,
	height :450,	
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	listeners:{
		beforehide:function(me){
			me.getPriceDiscountDetailEditFormPanel().getForm().reset();
		},
		beforeshow:function(me){
			me.getPriceDiscountDetailEditFormPanel().getForm().loadRecord(new Foss.pricing.priceDiscount.PriceDiscountDto(me.priceDiscountDto));//设置值
		}
	},
	//维护折扣信息FORM
	priceDiscountDetailEditFormPanel:null,
	getPriceDiscountDetailEditFormPanel:function(){
		if(Ext.isEmpty(this.priceDiscountDetailEditFormPanel)){
    		this.priceDiscountDetailEditFormPanel = Ext.create('Foss.pricing.priceDiscount.PriceDiscountDetailEditFormPanel');
    		this.priceDiscountDetailEditFormPanel.getDockedItems()[0].items.items[0].hide();
    	}
    	return this.priceDiscountDetailEditFormPanel;
	},
	//修改折扣明细
	commitPriceDiscountDetailEdit:function(){
		var me = this;
		//获取修改的原始数据
		var priceDiscountDto = new Foss.pricing.priceDiscount.PriceDiscountDto(me.priceDiscountDto);
		var form = me.getPriceDiscountDetailEditFormPanel().getForm();
        if(form.isValid()){
        	form.updateRecord(priceDiscountDto);
        	var goodsTypeCode = form.findField('goodsTypeId').goodsTypeRecord.get('code');
        	priceDiscountDto.set('goodsTypeCode',goodsTypeCode);
            var productCode = form.findField('productId').productRecord.get('code');
            priceDiscountDto.set('productCode',productCode);
            //如果开始范围大于结束范围！
            if(priceDiscountDto.get('leftRange')>priceDiscountDto.get('rightRange')){
            	pricing.showWoringMessage(pricing.priceDiscount.i18n('foss.pricing.leftRangeMoreRanRightRange'));//开始范围不能大于结束范围！
            	return;
            }
        	if(priceDiscountDto.get('discountRate')<100&&priceDiscountDto.get('minFee')>0){
            	pricing.showWoringMessage(pricing.priceDiscount.i18n('foss.pricing.minFeeAnddiscountRateEqcelZero'));//直接减免和折扣率不能同时享受！
            	return;
            }
            if(priceDiscountDto.get('discountRate')==100&&priceDiscountDto.get('minFee')==0){
            	pricing.showWoringMessage(pricing.priceDiscount.i18n('foss.pricing.minFeeAnddiscountRateDengZero'));//直接减免和折扣率不能同时等于0！
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
					pricing.showErrorMes(pricing.priceDiscount.i18n('i18n.pricingRegion.requestTimeOut'));//请求超时！
				}else{
					pricing.showErrorMes(json.message);
				}
			};
			var url = pricing.realPath('updateDiscountProgramItem.action');
			pricing.requestJsonAjax(url,params,successFun,failureFun);
        }		
	},
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [me.getPriceDiscountDetailEditFormPanel()];
		me.fbar = [{
			text : pricing.priceDiscount.i18n('i18n.pricingRegion.returnGrid'),
			handler :function(){
				me.close();
			} 
		},{
			text : pricing.priceDiscount.i18n('foss.pricing.reset'),
			handler :function(){
				me.getPriceDiscountEditFormPanel().getForm().loadRecord(new Foss.pricing.priceDiscount.PriceDiscountDto(me.priceDiscountDto));//设置值
			} 
		},{
			text : pricing.priceDiscount.i18n('foss.pricing.save'),//保存
			margin:'0 0 0 185',
			cls:'yellow_button',
			handler :function(){
				me.commitPriceDiscountDetailEdit();
			} 
		}];
		me.callParent([cfg]);
	}
});




/**
 * 修改弹窗-设置中止时间
 */
Ext.define('Foss.pricing.priceDiscount.PricingDiscountEndTimeWindow',{
	extend : 'Ext.window.Window',
	title : pricing.priceDiscount.i18n('i18n.pricingRegion.setEndTime'),
	closable : true,
	modal : true,
	resizable:false,
	closeAction : 'hide',
	parent:null,//(Foss.pricing.priceDiscount.PricingBasicValuationGridPanel)
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
			var minValue = me.selection.get('beginTime')>new Date(pricing.priceDiscount.tomorrowTime)?me.selection.get('beginTime'):new Date(pricing.priceDiscount.tomorrowTime);
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
    			fieldLabel: pricing.priceDiscount.i18n('i18n.pricingRegion.endTime'),//失效日期
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
        		pricing.showWoringMessage(pricing.priceDiscount.i18n('foss.pricing.theClosingDateIsGreaterThanStartDate'));//截止日期要大于起始日期！
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
    				pricing.showErrorMes(pricing.priceDiscount.i18n('i18n.pricingRegion.requestTimeOut'));
    			}else{
    				pricing.showErrorMes(json.message);
    			}
    		};
    		var url = pricing.realPath('stopDiscountProgram.action');
    		pricing.requestJsonAjax(url,params,successFun,failureFun);
    	}
    },
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [me.getEndTimeField()];
		me.fbar = [{
			text : pricing.priceDiscount.i18n('i18n.pricingRegion.cancel'),//取消
			handler :function(){
				me.close();
			} 
		},{
			text : pricing.priceDiscount.i18n('i18n.pricingRegion.determine'),//确认
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
 * 区域Store
 */
Ext.define('Foss.pricing.priceDiscount.PriceDiscountAreaStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.pricing.priceDiscount.AreaModel',
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
Ext.define('Foss.pricing.priceDiscount.PriceDiscountAreaGridPanel', {
	extend: 'Ext.grid.Panel',
	title : pricing.priceDiscount.i18n('foss.pricing.region'),//区域
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
			text : pricing.priceDiscount.i18n('i18n.pricingRegion.num')//序号
		},{
			text : pricing.priceDiscount.i18n('i18n.pricingRegion.regionNum'),//区域编号
			dataIndex : 'regionCode'
		},{
			text : pricing.priceDiscount.i18n('i18n.pricingRegion.regionName'),//区域名称
			dataIndex : 'regionName'
		}];
		me.store = Ext.create('Foss.pricing.priceDiscount.PriceDiscountAreaStore',{
			autoLoad:false,//不需要自动加载
			listeners: {
				beforeload: function(store, operation, eOpts){
					var queryForm = me.up('window').getPriceDiscountSearchRegionForm();
					if(queryForm!=null){
						Ext.apply(operation,{
							params : {
								'regionVo.regionEntity.regionName' : "%"+queryForm.getForm().findField('regionName').getValue()+"%",//区域名称
								'regionVo.regionEntity.regionCode' : queryForm.getForm().findField('regionCode').getValue(),//区域编号
								'regionVo.regionEntity.active' : 'Y',//之查询激活的
								//查询的只是价格区域
								'regionVo.regionEntity.regionNature':pricing.priceDiscount.PRICING_REGION
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
Ext.define('Foss.pricing.priceDiscount.PriceDiscountRegionGridShowWindow',{
	extend : 'Ext.window.Window',
	title : pricing.priceDiscount.i18n('foss.pricing.queryRegion'),//查询区域
	closable : true,
	parent:null,//(Foss.pricing.priceDiscount.StartGrid,Foss.pricing.priceDiscount.ArriveGrid)
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
			me.getPriceDiscountSearchRegionForm().getForm().reset();
			me.textField = null;
			
		},
		beforeshow:function(me){
			me.getPriceDiscountRegionGridPanel().getPagingToolbar().moveFirst();//确保查询第一页的数据
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
		var selections = me.getPriceDiscountRegionGridPanel().getSelectionModel().getSelection();//获取选择的数据
		if(selections.length==0){
			pricing.showWoringMessage(pricing.priceDiscount.i18n('foss.pricing.pleaseSelectRegion'));//请选择一个区域！
			return;
		}
		var datas = new Array();
		if(me.type==pricing.priceDiscount.priceDiscountStart){
			for(var i = 0;i<selections.length;i++){
				var isHave = false;//是否已经存在
				me.parent.getStore().each(function(record){
					if(record.get('deptOrgId')==selections[i].get('id')){
						isHave = true;
					}
				});
				if(!isHave){//不存在则新增
					var data = {'deptOrgId':selections[i].get('id'),'name':selections[i].get('regionName'),'deptOrgCode':selections[i].get('regionCode'),'deptOrgTypeCode':pricing.priceDiscount.priceDiscountDetailTypeRegion};
					datas.push(data);
				}
			}
			var oldData = me.parent.getStore().getAt(0)//清掉不是一类的数据
			if(!Ext.isEmpty(oldData)){
				if(oldData.get('deptOrgTypeCode')!=pricing.priceDiscount.priceDiscountDetailTypeRegion){
					me.parent.getStore().removeAll();
				}
			}
		}else if(me.type==pricing.priceDiscount.priceDiscountArrive){
			for(var i = 0;i<selections.length;i++){
				var isHave = false;//是否已经存在
				me.parent.getStore().each(function(record){
					if(record.get('arrvOrgId')==selections[i].get('id')){
						isHave = true;
					}
				});
				if(!isHave){//不存在则新增
					var data = {'arrvOrgId':selections[i].get('id'),'name':selections[i].get('regionName'),'arrvOrgCode':selections[i].get('regionCode'),'arrvOrgTypeCode':pricing.priceDiscount.priceDiscountDetailTypeRegion};
					datas.push(data);
				}
			}
			var oldData = me.parent.getStore().getAt(0)//清掉不是一类的数据
			if(!Ext.isEmpty(oldData)){
				if(oldData.get('arrvOrgTypeCode')!=pricing.priceDiscount.priceDiscountDetailTypeRegion){
					me.parent.getStore().removeAll();
				}
			}
		}
		me.parent.getStore().add(datas);
		me.close();
	},
	//查询的FORM
	priceDiscountSearchRegionForm:null,
	getPriceDiscountSearchRegionForm:function(){
		if(Ext.isEmpty(this.priceDiscountSearchRegionForm)){
    		this.priceDiscountSearchRegionForm = Ext.create('Foss.pricing.priceDiscount.PriceDiscountSearchRegionForm');
    	}
		return this.priceDiscountSearchRegionForm;
	},
	//区域的GRID
	priceDiscountRegionGridPanel:null,
	getPriceDiscountRegionGridPanel:function(){
		if(Ext.isEmpty(this.priceDiscountRegionGridPanel)){
    		this.priceDiscountRegionGridPanel = Ext.create('Foss.pricing.priceDiscount.PriceDiscountAreaGridPanel');
    	}
		return this.priceDiscountRegionGridPanel;
	}, 
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [me.getPriceDiscountSearchRegionForm(), me.getPriceDiscountRegionGridPanel()];
		me.fbar = [{
			text : pricing.priceDiscount.i18n('i18n.pricingRegion.returnGrid'),//返回列表
			handler :function(){
				me.close();
			} 
		},{
			text : pricing.priceDiscount.i18n('i18n.pricingRegion.determine'),//确定
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
Ext.define('Foss.pricing.priceDiscount.PriceDiscountSearchRegionForm', {
	extend : 'Ext.form.Panel',
	title: pricing.priceDiscount.i18n('i18n.pricingRegion.searchCondition'),
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
			fieldLabel:pricing.priceDiscount.i18n('i18n.pricingRegion.regionName'),//区域名称
			name:'regionName'
		},{
			xtype:'textfield',
			fieldLabel:pricing.priceDiscount.i18n('i18n.pricingRegion.regionCode'),//区域编码
			name:'regionCode'
		},{
			xtype : 'container',
			margin : '0 0 0 0',
			columnWidth : .2,
			items : [{
				xtype : 'button', 
				width:70,
				cls:'yellow_button',
				text : pricing.priceDiscount.i18n('i18n.pricingRegion.search'),//查询按钮
				handler : function() {
					if(me.getForm().isValid()){
						var grid  = me.up('window').getPriceDiscountRegionGridPanel();
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
Ext.define('Foss.pricing.priceDiscount.PriceDiscountCityStore', {
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
Ext.define('Foss.pricing.priceDiscount.PriceDiscountCityGridPanel', {
	extend: 'Ext.grid.Panel',
	title : pricing.priceDiscount.i18n('i18n.pricingRegion.city'),//城市
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
			text : pricing.priceDiscount.i18n('i18n.pricingRegion.num')//序号
		},{
			text : pricing.priceDiscount.i18n('foss.pricing.cityName'),//城市名称
			dataIndex : 'name'
		},{
			text : pricing.priceDiscount.i18n('foss.pricing.cityCode'),//城市编号
			dataIndex : 'code'
		}];
		me.store = Ext.create('Foss.pricing.priceDiscount.PriceDiscountCityStore',{
			autoLoad:false,//不需要自动加载
			listeners: {
				beforeload: function(store, operation, eOpts){
					var queryForm = me.up('window').getPriceDiscountSearchCityForm();
					if(queryForm!=null){
						Ext.apply(operation,{
							params : {
								'priceDiscountVo.administrativeRegionsEntity.name' : queryForm.getForm().findField('name').getValue(),//城市名称
								 //查询的只是城市
								'priceDiscountVo.administrativeRegionsEntity.degree':pricing.priceDiscount.Pricing_City
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
Ext.define('Foss.pricing.priceDiscount.PriceDiscountCityGridShowWindow',{
	extend : 'Ext.window.Window',
	parent:null,//(Foss.pricing.priceDiscount.StartGrid,Foss.pricing.priceDiscount.ArriveGrid)
	title : pricing.priceDiscount.i18n('i18n.pricingRegion.city'),//查询出发地/目的地
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
			me.getPriceDiscountSearchCityForm().getForm().reset();
			me.textField = null;
			
		},
		beforeshow:function(me){
			me.getPriceDiscountCityGridPanel().getPagingToolbar().moveFirst();//确保查询第一页的数据
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
		var selections = me.getPriceDiscountCityGridPanel().getSelectionModel().getSelection();//获取选择的数据
		if(selections.length==0){
			pricing.showWoringMessage(pricing.priceDiscount.i18n('foss.pricing.pleaseSelectCity'));//请选择一个城市！
			return;
		}
		var datas = new Array();
		if(me.type==pricing.priceDiscount.priceDiscountStart){
			for(var i = 0;i<selections.length;i++){
				var isHave = false;//是否已经存在
				me.parent.getStore().each(function(record){
					if(record.get('deptOrgId')==selections[i].get('id')){
						isHave = true;
					}
				});
				if(!isHave){//不存在则新增
					var data = {'deptOrgId':selections[i].get('id'),'name':selections[i].get('name'),'deptOrgCode':selections[i].get('code'),'deptOrgTypeCode':pricing.priceDiscount.priceDiscountDetailTypeCity};
					datas.push(data);
				}
			}
			var oldData = me.parent.getStore().getAt(0)//清掉不是一类的数据
			if(!Ext.isEmpty(oldData)){
				if(oldData.get('deptOrgTypeCode')!=pricing.priceDiscount.priceDiscountDetailTypeCity){
					me.parent.getStore().removeAll();
				}
			}
		}else if(me.type==pricing.priceDiscount.priceDiscountArrive){
			for(var i = 0;i<selections.length;i++){
				var isHave = false;//是否已经存在
				me.parent.getStore().each(function(record){
					if(record.get('arrvOrgId')==selections[i].get('id')){
						isHave = true;
					}
				});
				if(!isHave){//不存在则新增
					var data = {'arrvOrgId':selections[i].get('id'),'name':selections[i].get('name'),'arrvOrgCode':selections[i].get('code'),'arrvOrgTypeCode':pricing.priceDiscount.priceDiscountDetailTypeCity};
					datas.push(data);
				}
			}
			var oldData = me.parent.getStore().getAt(0)//清掉不是一类的数据
			if(!Ext.isEmpty(oldData)){
				if(oldData.get('arrvOrgTypeCode')!=pricing.priceDiscount.priceDiscountDetailTypeCity){
					me.parent.getStore().removeAll();
				}
			}
		}
		me.parent.getStore().add(datas);
		me.close();
	},
	//查询的FORM
	priceDiscountSearchCityForm:null,
	getPriceDiscountSearchCityForm:function(){
		if(Ext.isEmpty(this.priceDiscountSearchCityForm)){
    		this.priceDiscountSearchCityForm = Ext.create('Foss.pricing.priceDiscount.PriceDiscountSearchCityForm');
    	}
		return this.priceDiscountSearchCityForm;
	},
	//区域的GRID
	priceDiscountCityGridPanel:null,
	getPriceDiscountCityGridPanel:function(){
		if(Ext.isEmpty(this.priceDiscountCityGridPanel)){
    		this.priceDiscountCityGridPanel = Ext.create('Foss.pricing.priceDiscount.PriceDiscountCityGridPanel');
    	}
		return this.priceDiscountCityGridPanel;
	}, 
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [me.getPriceDiscountSearchCityForm(), me.getPriceDiscountCityGridPanel()];
		me.fbar = [{
			text : pricing.priceDiscount.i18n('i18n.pricingRegion.returnGrid'),//返回列表
			handler :function(){
				me.close();
			} 
		},{
			text : pricing.priceDiscount.i18n('i18n.pricingRegion.determine'),//确定
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
Ext.define('Foss.pricing.priceDiscount.PriceDiscountSearchCityForm', {
	extend : 'Ext.form.Panel',
	title: pricing.priceDiscount.i18n('i18n.pricingRegion.city'),
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
			fieldLabel:pricing.priceDiscount.i18n('foss.pricing.cityName'),//城市名称
			name:'name'
		},{
			xtype : 'container',
			margin : '0 0 0 0',
			columnWidth : .2,
			items : [{
				xtype : 'button', 
				width:70,
				cls:'yellow_button',
				text : pricing.priceDiscount.i18n('i18n.pricingRegion.search'),//查询
				handler : function() {
					if(me.getForm().isValid()){
						var grid  = me.up('window').getPriceDiscountCityGridPanel();
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
Ext.define('Foss.pricing.priceDiscount.ImmediatelyStopEndTimeWindow',{
		extend: 'Ext.window.Window',
		title: pricing.priceDiscount.i18n('foss.pricing.immediatelySupendPricePriceScheme'),//"立即中止方案",
		width:380,
		height:152,
		closeAction: 'hide' ,
		stopFormPanel:null,
		marketingEventEntity:null,
		parent:null,
		getStopFormPanel : function(){
	    	if(Ext.isEmpty(this.stopFormPanel)){
	    		this.stopFormPanel = Ext.create('Foss.pricing.priceDiscount.ImmediatelyStopFormPanel');
	    	}
	    	return this.stopFormPanel;
	    },
	    listeners:{
	    	beforeshow:function(me){
	    		var showbeginTime = Ext.Date.format(new Date(me.marketingEventEntity.beginTime), 'Y-m-d');
	    		var showendTime = 	Ext.Date.format(new Date(me.marketingEventEntity.endTime), 'Y-m-d');
	    		var value = pricing.priceDiscount.i18n('foss.pricing.showleftTimeInfo')
				  +showbeginTime+pricing.priceDiscount.i18n('foss.pricing.showmiddleTimeInfo')
				  +showendTime+pricing.priceDiscount.i18n('foss.pricing.showstopRightEndTimeInfo');
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
Ext.define('Foss.pricing.priceDiscount.ImmediatelyStopFormPanel',{
	extend : 'Ext.form.Panel',
	layout:'column',
	stop:function(){
		var me = this;
    	var form = me.getForm();
    	if(form.isValid()){
    		var marketingEventEntity = new Foss.pricing.priceDiscount.MarketingEventEntity();
			form.updateRecord(marketingEventEntity);
			marketingEventEntity.set('endTime',Ext.Date.parse(form.findField('endTime').getValue(), 'Y-m-d H:i:s'));
			var id = me.up('window').marketingEventEntity.id;
			marketingEventEntity.set('id',id);
    		var params = {'priceDiscountVo':{'marketingEventEntity':marketingEventEntity.data}};
    		var url = pricing.realPath('terminateImmediatelyDiscountProgramPrice.action');
    		var successFun = function(json){
    			pricing.showInfoMes(json.message);
    			me.up('window').hide();
    			me.up('window').parent.getPagingToolbar().moveFirst();
    	    };
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
	constructor: function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.items = [{
				width:280,
				xtype: 'displayfield',
				columnWidth:.9,
				value:''
			},{ 
				fieldLabel :pricing.priceDiscount.i18n('foss.pricing.suspendTime') ,//'中止日期',
				name : 'endTime',
				xtype : 'datetimefield_date97',
				editable:false,
				time : true,
				id : 'Foss_priceDiscount_stopEndTime_ID',
				dateConfig: {
					el : 'Foss_priceDiscount_stopEndTime_ID-inputEl'
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
				text : pricing.priceDiscount.i18n('i18n.pricingRegion.determine'),//"确认",
				handler : function() {
					me.stop();
				}
			},{
				xtype : 'button', 
				width:70,
				columnWidth:.15,
				text : pricing.priceDiscount.i18n('i18n.pricingRegion.cancel'),//"取消",
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
Ext.define('Foss.pricing.priceDiscount.ImmediatelyActiveTimeWindow',{
		extend: 'Ext.window.Window',
		title: pricing.priceDiscount.i18n('foss.pricing.immediatelyActiveationPriceScheme'),//"立即激活方案",
		width:380,
		height:152,
		marketingEventEntity:null,
		closeAction: 'hide' ,
		immediatelyActiveFormPanel:null,
		getImmediatelyActiveFormPanel : function(){
	    	if(Ext.isEmpty(this.immediatelyActiveFormPanel)){
	    		this.immediatelyActiveFormPanel = Ext.create('Foss.pricing.priceDiscount.ImmediatelyActiveFormPanel');
	    	}
	    	return this.immediatelyActiveFormPanel;
	    },
	    listeners:{
	    	beforeshow:function(me){
	    		var showbeginTime = Ext.Date.format(new Date(me.marketingEventEntity.beginTime), 'Y-m-d');
	    		var showendTime = 	Ext.Date.format(new Date(me.marketingEventEntity.endTime), 'Y-m-d');
	    		var value = pricing.priceDiscount.i18n('foss.pricing.showleftTimeInfo')
				  +showbeginTime+pricing.priceDiscount.i18n('foss.pricing.showmiddleTimeInfo')
				  +showendTime+pricing.priceDiscount.i18n('foss.pricing.showrightEndTimeInfo');
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
Ext.define('Foss.pricing.priceDiscount.ImmediatelyActiveFormPanel',{
	extend : 'Ext.form.Panel',
	layout:'column',
	activetion:function(){
		var me = this;
    	var form = me.getForm();
    	if(form.isValid()){
			var marketingEventEntity = new Foss.pricing.priceDiscount.MarketingEventEntity();
			form.updateRecord(marketingEventEntity);
			marketingEventEntity.set('beginTime',Ext.Date.parse(form.findField('beginTime').getValue(), 'Y-m-d H:i:s'));
			var id = me.up('window').marketingEventEntity.id;
			marketingEventEntity.set('id',id);
    		var params = {'priceDiscountVo':{'marketingEventEntity':marketingEventEntity.data}};
    		var url = pricing.realPath('activateImmediatelyDiscountProgramPrice.action');
    		var successFun = function(json){
    			pricing.showInfoMes(json.message);
    			me.up('window').hide();
				me.up('window').parent.getPagingToolbar().moveFirst();  			
    	    };
    	    var failureFun = function(json){
    	    	if(Ext.isEmpty(json)){
    				pricing.showErrorMes(pricing.priceDiscount.i18n('i18n.pricingRegion.requestTimeOut'));
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
				fieldLabel:pricing.priceDiscount.i18n('foss.pricing.availabilityDate'),//'生效日期',
				name : 'beginTime',
				xtype : 'datetimefield_date97',
				editable:false,
				time : true,
				allowBlank:false,
				id : 'Foss_priceDiscount_activeEndTime_ID',
				dateConfig: {
					el : 'Foss_priceDiscount_activeEndTime_ID-inputEl'
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
				text : pricing.priceDiscount.i18n('i18n.pricingRegion.determine'),//,"确认",
				handler : function() {
					me.activetion();
				}
			},{
				xtype : 'button', 
				width:70,
				columnWidth:.15,
				text : pricing.priceDiscount.i18n('i18n.pricingRegion.cancel'),//"取消",
				handler : function() {
					me.up('window').hide();
				}
			}];
        this.callParent(cfg);
    }
});


