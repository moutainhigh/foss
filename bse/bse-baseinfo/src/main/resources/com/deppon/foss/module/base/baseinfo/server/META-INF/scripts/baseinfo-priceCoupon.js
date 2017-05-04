/**
 * @author dujunhui-187862
 * @date 2014-9-17 10:13:32
*/
//转换long类型为日期(在model中会用到)
baseinfo.changeLongToDate = function(value) {
	if (value != null) {
		var date = new Date(value);
		return date;
	} else {
		return null;
	}
};
//Ajax请求--json
baseinfo.requestJsonAjax = function(url,params,successFn,failFn){
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
//通过storeId和model创建STORE
baseinfo.getStore = function(storeId,model,fields,data) {
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
baseinfo.priceCoupon.productEntityList = [];//基础产品列表
baseinfo.priceCoupon.goodTypeList = [];//货物类型列表
baseinfo.priceCoupon.caculateType = [{'valueCode':'WEIGHT','valueName':'重量'}//重量baseinfo.priceCoupon.i18n('foss.priceCoupon.weight')
,{'valueCode':'VOLUME','valueName':'体积'}//体积baseinfo.priceCoupon.i18n('foss.priceCoupon.volume')
,{'valueCode':'ORIGINALCOST','valueName':'直接费用'}];//直接费用baseinfo.priceCoupon.i18n('foss.priceCoupon.straight')

baseinfo.priceCoupon.channel = 'PKP_PRICE_CHANNEL';//渠道
baseinfo.priceCoupon.priceCouponDetailTypeDept = 'Coupon_DEPT';//降价发券明细类型——部门
baseinfo.priceCoupon.priceCouponDetailTypeRegion = 'Coupon_REGION';//降价发券明细类型——区域
baseinfo.priceCoupon.priceCouponDetailTypeCity = 'Coupon_CITY';//降价发券明细类型——城市
baseinfo.priceCoupon.priceCouponDetailTypeAllNet = 'ALLNET';//全网
baseinfo.priceCoupon.priceCouponArrive = 'ARRIVE';//到达
baseinfo.priceCoupon.priceCouponStart = 'START';//出发
baseinfo.priceCoupon.tomorrowTime = null;
//价格区域
baseinfo.priceCoupon.PRICING_REGION = 'PRICING_REGION';
//城市
baseinfo.priceCoupon.Pricing_City = 'CITY';
baseinfo.priceCoupon.allNet = 'ALLNET';//全网常量
//--------------------------------------baseinfo----------------------------------------
//查询货物类型列表
baseinfo.searchGoodTypeList = function(){
	Ext.Ajax.request({
		url:baseinfo.realPath('findGoodsTypeByCondiction.action'),//查询基础产品
		async:false,
		success:function(response){
			var result = Ext.decode(response.responseText);
			baseinfo.priceCoupon.goodTypeList = result.pricingValuationVo.goodsTypeEntityList;
		},
		failure:function(response){
			var result = Ext.decode(response.responseText);
			if(Ext.isEmpty(result)){
				baseinfo.showErrorMes('请求超时！');//请求超时！baseinfo.priceCoupon.i18n('foss.baseinfo.requestOvertime')
			}else{
				baseinfo.showErrorMes(result.message);
			}
		},
		exception:function(response){
			var result = Ext.decode(response.responseText);
			if(Ext.isEmpty(result)){
				baseinfo.showErrorMes('请求超时！');//请求超时！baseinfo.priceCoupon.i18n('foss.baseinfo.requestOvertime')
			}else{
				baseinfo.showErrorMes(result.message);
			}
		}
	});
};
//查询基础产品列表
baseinfo.searchProductEntityList = function(){
	Ext.Ajax.request({
		url:baseinfo.realPath('findProductByCondition.action'),//查询基础产品
		async:false,
		success:function(response){
			var result = Ext.decode(response.responseText);
			baseinfo.priceCoupon.productEntityList = result.pricingValuationVo.productEntityList;
		},
		failure:function(response){
			var result = Ext.decode(response.responseText);
			if(Ext.isEmpty(result)){
				baseinfo.showErrorMes('请求超时！');//请求超时！baseinfo.priceCoupon.i18n('foss.baseinfo.requestOvertime')
			}else{
				baseinfo.showErrorMes(result.message);
			}
		},
		exception:function(response){
			var result = Ext.decode(response.responseText);
			if(Ext.isEmpty(result)){
				baseinfo.showErrorMes('请求超时！');//请求超时！baseinfo.priceCoupon.i18n('foss.baseinfo.requestOvertime')
			}else{
				baseinfo.showErrorMes(result.message);
			}
		}
	});
};
//获取服务当前时间
baseinfo.haveServerNowTime = function(){
	Ext.Ajax.request({
		url:baseinfo.realPath('haveServerNowTime.action'),
		async:false,
		success:function(response){
			var result = Ext.decode(response.responseText);
			var today = new Date(result.priceCouponVo.nowTime);//获取服务当前时间
			baseinfo.priceCoupon.tomorrowTime = today.setDate(today.getDate()+1);
		},
		failure:function(response){
			var result = Ext.decode(response.responseText);
			if(Ext.isEmpty(result)){
				baseinfo.showErrorMes('请求超时！');//请求超时！baseinfo.priceCoupon.i18n('foss.baseinfo.requestOvertime')
			}else{
				baseinfo.showErrorMes(result.message);
			}
		},
		exception:function(response){
			var result = Ext.decode(response.responseText);
			failFn(result);
		}
	});
};
//价格降价发券明细dto
Ext.define('Foss.baseinfo.priceCoupon.PriceCouponDto', {
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
        name : 'beginDate',// 降价发券方案开始时间
        type : 'date',
        defaultValue : null,
        convert : baseinfo.changeLongToDate
    },{
        name : 'endDate',// 降价发券方案结束时间
        type : 'date',
        defaultValue : null,
        convert : baseinfo.changeLongToDate
    },{
        name : 'businessDate',// 业务日期
        type : 'date',
        defaultValue : null,
        convert : baseinfo.changeLongToDate
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
        convert : baseinfo.changeLongToDate
    },{
        name : 'createUserName',//创建人姓名
        type : 'string'
    },{
        name : 'modifyTime',//修改时间
        type : 'date',
        defaultValue : null,
        convert : baseinfo.changeLongToDate
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
        name : 'CouponOrgId',//降价发券适用起始目的组织网点ID
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
        name : 'couponRate'//降价发券率
    },{
        name : 'caculateType',//降价发券规则
        type : 'string'
    },{
    	defaultValue : null,// 最低一票
        name : 'minFee'
    },{
    	defaultValue : 0,// 重量范围左空间
        name : 'weightLeftRange'
    },{
    	defaultValue : 0,// 重量范围右空间
        name : 'weightRightRange'
    },{
    	defaultValue : 0,// 体积范围左空间
        name : 'volumeLeftRange'
    },{
    	defaultValue : 0,// 体积范围右空间
        name : 'volumeRightRange'
//    },{
//    	defaultValue : null,//返券时间
//        name : 'couponTimeToSend'
    },{
        name : 'isPickUp',//是否接货
        type : 'string'
    }]
});
//基础产品明细MODEL
Ext.define('Foss.baseinfo.priceCoupon.ProductEntity', {
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
Ext.define('Foss.baseinfo.priceCoupon.GoodsTypeEntity', {
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
//降价发券方案主信息
Ext.define('Foss.baseinfo.priceCoupon.MarketingEventEntity', {
    extend: 'Ext.data.Model',
    fields : [{
        name : 'id',
        type : 'string'
    },{
        name : 'createDate',//创建时间
        type : 'date',
        defaultValue : null,
        convert : baseinfo.changeLongToDate
    },{
        name : 'createUser',//创建人工号
        type : 'string'
    },{
        name : 'createUserName',//创建人名称
        type : 'string'
    },{
        name : 'modifyDate',//修改时间
        type : 'date',
        defaultValue : null,
        convert : baseinfo.changeLongToDate
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
        convert : baseinfo.changeLongToDate
    },{
        name : 'endTime',//截止日期
        type : 'date',
        defaultValue : null,
        convert : baseinfo.changeLongToDate
    },{
//        name : 'description',//备注
    	name : 'remark',//备注
        type : 'string'
    },{
        name : 'createTime',//创建时间
        type : 'date',
        defaultValue : null,
        convert : baseinfo.changeLongToDate
    },{
        name : 'createOrgCode',//创建人所在部门
        type : 'string'
    },{
        name : 'modifyTime',//修改时间
        type : 'date',
        defaultValue : null,
        convert : baseinfo.changeLongToDate
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
    },{
        name : 'productItem',//产品类型
        type : 'string'
    },{
        name : 'orderSource',//订单来源
        type : 'string'
    },{
        name : 'customerDegree',//客户等级
        type : 'string'
    },{
        name : 'customerProfession',//客户行业
        type : 'string'
    },{
        name : 'lineRegion',//线路区域要求
        type : 'string'
    },{
        name : 'availablePeriod',//优惠券有效期
        type : 'string'
    },{
        name : 'couponTimeToSend',//返券时间
        defaultValue : null//返券时间
    }]
});
/**
 * 区域MODEL
 */
Ext.define('Foss.baseinfo.priceCoupon.AreaModel', {
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
		 convert:baseinfo.changeLongToDate
	}, {
		name : 'endTime',// 结束时间
		type: 'Date',
		defaultValue : null,
		 convert:baseinfo.changeLongToDate
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
		 convert:baseinfo.changeLongToDate
	}, {
		name : 'modifyDate',//修改时间
		type: 'Date',
		defaultValue : null,
		 convert:baseinfo.changeLongToDate
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
 * 降价发券方案Store
 */
Ext.define('Foss.baseinfo.priceCoupon.priceCouponDetailStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.baseinfo.priceCoupon.PriceCouponDto',//降价发券方案明细MODEL
	pageSize:5,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url : baseinfo.realPath('selectPriceCouponByCodition.action'),
		reader : {
			type : 'json',
			root : 'priceCouponVo.priceCouponDtoList',
			totalProperty : 'totalCount'
		}
	}
});
/**
 * 降价发券方案Store
 */
Ext.define('Foss.baseinfo.priceCoupon.priceCouponStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.baseinfo.priceCoupon.MarketingEventEntity',//降价发券方案MODEL
	pageSize:20,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url : baseinfo.realPath('selectPriceProgramByCodition.action'),
		reader : {
			type : 'json',
			root : 'priceCouponVo.marketingSchemeEntityList',
			totalProperty : 'totalCount'
		}
	}
});
//----------------------------------------store---------------------------------
//通过storeId和model创建STORE<br/>
baseinfo.getStore = function(storeId,model,fields,data) {
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
 * 降价发券方案查询表单
 */
Ext.define('Foss.baseinfo.priceCoupon.QueryPriceCouponForm', {
	extend : 'Ext.form.Panel',
	title:'查询条件',//baseinfo.priceCoupon.i18n('foss.baseinfo.queryCondition'),//查询条件
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
//			columnWidth:0.3,
		    fieldLabel:'方案名称',//baseinfo.priceCoupon.i18n('foss.priceCoupon.priceCouponScheme'),//降价发券方案
	        xtype : 'textfield'
		},{
			name: 'active',
//			columnWidth:0.33,
			queryMode: 'local',
		    displayField: 'valueName',
		    valueField: 'valueCode',
		    editable:false,
		    value:'ALL',
		    store:baseinfo.getStore(null,null,['valueCode','valueName']
		    ,[
		      {'valueCode':'Y','valueName':'已激活'},//已激活baseinfo.priceCoupon.i18n('foss.priceCoupon.active')
		      {'valueCode':'N','valueName':'未激活'},//未激活baseinfo.priceCoupon.i18n('foss.priceCoupon.inActive')
		      {'valueCode':'ALL','valueName':'全部'}//全部baseinfo.priceCoupon.i18n('foss.baseinfo.all')
		      ]),
	        fieldLabel: '方案状态',//baseinfo.priceCoupon.i18n('foss.priceCoupon.priceCouponSchemeState'),//降价发券方案状态
	        xtype : 'combo'
		},{
			name: 'businessDate',
//			columnWidth:0.3,
			format:'Y-m-d H:i:s',
		    fieldLabel:'业务日期',//baseinfo.priceCoupon.i18n('foss.priceCoupon.businessDate'),//业务日期
	        xtype : 'datefield'
		}];
		me.fbar = [{
			xtype : 'button', 
			width:70,
			text : '重置',//baseinfo.priceCoupon.i18n('foss.baseinfo.reset'),//重置
			disabled: !baseinfo.priceCoupon.isPermission('priceCoupon/priceCouponQuerybutton'),
			hidden: !baseinfo.priceCoupon.isPermission('priceCoupon/priceCouponQuerybutton'),
			handler : function() {
				me.getForm().reset();
			}
		},{
			xtype : 'button', 
			width:70,
			cls:'yellow_button',
			margin:'0 0 0 825',
			text : '查询',//baseinfo.priceCoupon.i18n('foss.baseinfo.query'),//查询
			disabled:!baseinfo.priceCoupon.isPermission('priceCoupon/priceCouponQuerybutton'),
			hidden:!baseinfo.priceCoupon.isPermission('priceCoupon/priceCouponQuerybutton'),
			handler : function() {
				if(me.getForm().isValid()){
					var grid = Ext.getCmp('T_baseinfo-priceCoupon_content').getAreaGrid();//获取大查询GRID
					grid.getStore().load();
				}
			}
		}]
		me.callParent([cfg]);
	}
});
/**
 * 降价发券方案列表
 */
Ext.define('Foss.baseinfo.priceCoupon.PriceCouponGridPanel', {
	extend: 'Ext.grid.Panel',
	title :'查询结果',//baseinfo.priceCoupon.i18n('foss.baseinfo.queryResults'),//查询结果
	frame: true,
	cls: 'autoHeight',
	bodyCls: 'autoHeight', 
    sortableColumns:false,
    enableColumnHide:false,
    enableColumnMove:false,
	stripeRows : true, // 交替行效果
	selType : "rowmodel", // 选择类型设置为：行选择
	emptyText:'查询结果为空',//baseinfo.priceCoupon.i18n('foss.baseinfo.queryResultIsNull'),//查询结果为空
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
	//激活降价发券方案
	activePricingCoupon:function(){
		var me = this;
		var priceCouponIds = new Array();
		//获取选中的数据
		var selections = me.getSelectionModel().getSelection();
		if(selections.length<1){
			baseinfo.showErrorMes('请选择要激活的降价发券方案！');//请选择要激活的降价发券方案！baseinfo.priceCoupon.i18n('foss.priceCoupon.selectActiveWantedScheme')
			return;
		}
		for(var i = 0 ; i<selections.length ; i++){
			if(selections[i].get('active')=='Y'){//只有未激活的降价发券方案才可以删除
				baseinfo.showErrorMes('请选择要删除的未激活降价发券方案！');//请选择要删除的未激活降价发券方案！baseinfo.priceCoupon.i18n('foss.priceCoupon.selectInActiveDeleteWantedScheme')
				return;
			}else if(selections[i].get('active')=='N'){
				priceCouponIds.push(selections[i].get('id'));
			}else{
				priceCouponIds.push(selections[i].get('id'));
			}
		}
		if(priceCouponIds.length<1){
			baseinfo.showErrorMes('请至少选择一条未激活的降价发券方案！');//请至少选择一条未激活的降价发券方案！baseinfo.priceCoupon.i18n('foss.priceCoupon.selectAtLeastInActiveScheme')
			return;
		}
		baseinfo.showQuestionMes('是否要激活这些未激活的降价发券方案？',function(e){//是否要激活这些未激活的降价发券方案？
			if(e=='yes'){//询问是否激活，是则发送请求
				var params = {'priceCouponVo':{'priceCouponIds':priceCouponIds}};
				var successFun = function(json){
					baseinfo.showInfoMes(json.message);
					me.getPagingToolbar().moveFirst();
				};
				var failureFun = function(json){
					if(Ext.isEmpty(json)){
						baseinfo.showErrorMes('请求超时！');//请求超时baseinfo.priceCoupon.i18n('foss.baseinfo.requestOvertime')
					}else{
						baseinfo.showErrorMes(json.message);
					}
				};
				var url = baseinfo.realPath('activePricingCoupon.action');
				baseinfo.requestJsonAjax(url,params,successFun,failureFun);
			}
		});
	},
	//作废降价发券方案
	deletePricingCoupon:function(){
		var me = this;
		var priceCouponIds = new Array();
		//获取选中的数据
		var selections = me.getSelectionModel().getSelection();
		if(selections.length<1){
			baseinfo.showErrorMes('请选择要作废的未激活降价发券方案！');//请选择要删除的未激活降价发券方案！baseinfo.priceCoupon.i18n('foss.priceCoupon.selectInActiveDeleteWantedScheme')
			return;
		}
		for(var i = 0 ; i<selections.length ; i++){
			if(selections[i].get('active')=='Y'){//只有未激活的降价发券方案才可以删除
				baseinfo.showErrorMes('请选择要作废的未激活降价发券方案！');//请选择要删除的未激活降价发券方案！baseinfo.priceCoupon.i18n('foss.priceCoupon.selectInActiveDeleteWantedScheme')
				return;
			}else if(selections[i].get('active')=='N'){
				priceCouponIds.push(selections[i].get('id'));
			}else{
				priceCouponIds.push(selections[i].get('id'));
			}
		}
		if(priceCouponIds.length<1){
			baseinfo.showErrorMes('请至少选择一条未激活的降价发券方案！');//请至少选择一条未激活的降价发券方案！baseinfo.priceCoupon.i18n('foss.priceCoupon.selectAtLeastInActiveScheme')
			return;
		}
		baseinfo.showQuestionMes('是否要作废这些未激活的降价发券方案？',function(e){//是否要删除这些未激活的降价发券方案？
			if(e=='yes'){//询问是否删除，是则发送请求
				var params = {'priceCouponVo':{'priceCouponIds':priceCouponIds}};
				var successFun = function(json){
					baseinfo.showInfoMes(json.message);
					me.getPagingToolbar().moveFirst();
				};
				var failureFun = function(json){
					if(Ext.isEmpty(json)){
						baseinfo.showErrorMes('请求超时！');//请求超时baseinfo.priceCoupon.i18n('foss.baseinfo.requestOvertime')
					}else{
						baseinfo.showErrorMes(json.message);
					}
				};
				var url = baseinfo.realPath('deletePricingCoupon.action');
				baseinfo.requestJsonAjax(url,params,successFun,failureFun);
			}
		});
		
	},
	//降价发券主信息新增Window
	priceCouponAddWindow : null,
	getPriceCouponAddWindow : function() {
		if (Ext.isEmpty(this.priceCouponAddWindow)) {
			this.priceCouponAddWindow = Ext.create('Foss.baseinfo.priceCoupon.PriceCouponAddWindow');
			this.priceCouponAddWindow.parent = this;
		}
		return this.priceCouponAddWindow;
	},
	//降价发券主信息修改Window
	priceCouponUpdateWindow : null,
	getPriceCouponUpdateWindow : function() {
		if (Ext.isEmpty(this.priceCouponUpdateWindow)) {
			this.priceCouponUpdateWindow = Ext.create('Foss.baseinfo.priceCoupon.PriceCouponUpdateWindow');
			this.priceCouponUpdateWindow.parent = this;
		}
		return this.priceCouponUpdateWindow;
	},
	//降价发券主明细新增Window
	priceCouponDeatilAddWindow : null,
	getPriceCouponDeatilAddWindow : function() {
		if (Ext.isEmpty(this.priceCouponDeatilAddWindow)) {
			this.priceCouponDeatilAddWindow = Ext.create('Foss.baseinfo.priceCoupon.PriceCouponDeatilAddWindow');
			this.priceCouponDeatilAddWindow.parent = this;
		}
		return this.priceCouponDeatilAddWindow;
	},
	//降价发券主明细修改Window
	priceCouponDeatilUpdateWindow : null,
	getPriceCouponDeatilUpdateWindow : function() {
		if (Ext.isEmpty(this.priceCouponDeatilUpdateWindow)) {
			this.priceCouponDeatilUpdateWindow = Ext.create('Foss.baseinfo.priceCoupon.PriceCouponDeatilAddWindow');
			this.priceCouponDeatilUpdateWindow.parent = this;
		}
		return this.priceCouponDeatilUpdateWindow;
	},
	//终止，设置截止日期
	pricingCouponEndTimeWindow:null,
	getPricingCouponEndTimeWindow : function(selection) {
		if (Ext.isEmpty(this.pricingCouponEndTimeWindow)) {
			this.pricingCouponEndTimeWindow = Ext.create('Foss.baseinfo.priceCoupon.PricingCouponEndTimeWindow',{
				selection:selection
			});
			this.pricingCouponEndTimeWindow.parent = this;
		}
		return this.pricingCouponEndTimeWindow;
	},
	//查看详情window
	priceCouponDeatilShowWindow:null,
	getPriceCouponDeatilShowWindow : function() {
		if (Ext.isEmpty(this.priceCouponDeatilShowWindow)) {
			this.priceCouponDeatilShowWindow = Ext.create('Foss.baseinfo.priceCoupon.PriceCouponDeatilShowWindow');
			this.priceCouponDeatilShowWindow.parent = this;
		}
		return this.priceCouponDeatilShowWindow;
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
			this.immediatelyActiveWindow = Ext.create('Foss.baseinfo.priceCoupon.ImmediatelyActiveTimeWindow');
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
			this.immediatelyStopWindow = Ext.create('Foss.baseinfo.priceCoupon.ImmediatelyStopEndTimeWindow');
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
	 		baseinfo.showWoringMessage('请选择一条记录进行操作！');//请选择一条记录进行操作！baseinfo.priceCoupon.i18n('foss.priceCoupon.selectOneToOperate')
			return;
	 	}
	 	if(selections.length > 1){
	 		baseinfo.showWoringMessage('请选择一条记录进行操作！');//请选择一条记录进行操作！baseinfo.priceCoupon.i18n('foss.priceCoupon.selectOneToOperate')
			return;
	 	}
	 	if(selections[0].get('active')!='Y'){
	 		baseinfo.showWoringMessage('请选择已激活数据进行操作！');//请选择已激活数据进行操作！baseinfo.priceCoupon.i18n('foss.priceCoupon.selectActiveToOperate')
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
	 		baseinfo.showWoringMessage('请选择一条记录进行操作！');//请选择一条记录进行操作！baseinfo.priceCoupon.i18n('foss.priceCoupon.selectOneToOperate')
			return;
	 	}
	 	if(selections.length > 1){
	 		baseinfo.showWoringMessage('请选择一条记录进行操作！');//请选择一条记录进行操作！baseinfo.priceCoupon.i18n('foss.priceCoupon.selectOneToOperate')
			return;
	 	}
	 	if(selections[0].get('active')=='Y'){
	 		baseinfo.showWoringMessage('请选择未激活数据进行操作！');//请选择未激活数据进行操作！baseinfo.priceCoupon.i18n('foss.priceCoupon.selectInActiveToOperate')
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
			text : '序号',//baseinfo.priceCoupon.i18n('foss.baseinfo.sequence')//序号
		},{
			align : 'center',
			xtype : 'actioncolumn',
			text : '操作',//baseinfo.priceCoupon.i18n('foss.priceCoupon.operate'),//操作
			items: [{
				iconCls: 'deppon_icons_edit',
                tooltip: '修改',//baseinfo.priceCoupon.i18n('foss.baseinfo.update'),//修改
                disabled: !baseinfo.priceCoupon.isPermission('priceCoupon/priceCouponUpdatebutton'),
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
                	var params = {'priceCouponVo':{'marketingSchemeEntity':{'id':record.get('id')}}};
    				var successFun = function(json){
    					var marketingSchemeEntity = json.priceCouponVo.marketingSchemeEntity;
//    					var channelEntityList = json.priceCouponVo.channelEntityList;
                        grid.up().getPriceCouponUpdateWindow().marketingEventEntity = marketingSchemeEntity;//设置降价发券方案主信息
//                      grid.up().getPriceCouponUpdateWindow().channelEntityList = channelEntityList;//渠道信息
                        grid.up().getPriceCouponUpdateWindow().show();//显示window
    				};
    				var failureFun = function(json){
    					if(Ext.isEmpty(json)){
    						baseinfo.showErrorMes('请求超时！');//请求超时baseinfo.priceCoupon.i18n('foss.baseinfo.requestOvertime')
    					}else{
    						baseinfo.showErrorMes(json.message);
    					}
    				};
    				var url = baseinfo.realPath('selectMarketingByPrimaryKey.action');
    				baseinfo.requestJsonAjax(url,params,successFun,failureFun);
                }
			},{
				iconCls: 'deppon_icons_softwareUpgrade',
                tooltip: '升级版本',//baseinfo.priceCoupon.i18n('foss.priceCoupon.updateVersion'),//升级版本
                disabled: !baseinfo.priceCoupon.isPermission('priceCoupon/priceCouponUpgradebutton'),
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
                	var params = {'priceCouponVo':{'marketingSchemeEntity':{'id':record.get('id')}}};
    				var successFun = function(json){
    					baseinfo.showInfoMes(json.message);
    					grid.up().getPagingToolbar().moveFirst();
    				};
    				var failureFun = function(json){
    					if(Ext.isEmpty(json)){
    						baseinfo.showErrorMes('请求超时！');//请求超时baseinfo.priceCoupon.i18n('foss.baseinfo.requestOvertime')
    					}else{
    						baseinfo.showErrorMes(json.message);
    					}
    				};
    				var url = baseinfo.realPath('copyCouponProgram.action');
    				baseinfo.requestJsonAjax(url,params,successFun,failureFun);
                }
			},{
				iconCls: 'deppon_icons_showdetail',
                tooltip: '查看详情',//baseinfo.priceCoupon.i18n('foss.baseinfo.details'),//查看详情
                disabled: !baseinfo.priceCoupon.isPermission('priceCoupon/priceCouponDetailbutton'),
				width:42,
                handler: function(grid, rowIndex, colIndex) {
                	var record = grid.getStore().getAt(rowIndex);
                	var params = {'priceCouponVo':{'marketingSchemeEntity':{'id':record.get('id')}}};
    				var successFun = function(json){
    					var marketingEventEntity = json.priceCouponVo.marketingSchemeEntity;
//    					var channelEntityList = json.priceCouponVo.channelEntityList;
                        grid.up().getPriceCouponDeatilShowWindow().marketingEventEntity = marketingEventEntity;//设置降价发券方案主信息
//                        grid.up().getPriceCouponDeatilShowWindow().channelEntityList = channelEntityList;//渠道信息
                        grid.up().getPriceCouponDeatilShowWindow().show();//显示window
    				};
    				var failureFun = function(json){
    					if(Ext.isEmpty(json)){
    						baseinfo.showErrorMes('请求超时！');//请求超时baseinfo.priceCoupon.i18n('foss.baseinfo.requestOvertime')
    					}else{
    						baseinfo.showErrorMes(json.message);
    					}
    				};
    				var url = baseinfo.realPath('selectMarketingByPrimaryKey.action');
    				baseinfo.requestJsonAjax(url,params,successFun,failureFun);
                }
			}]
		},{
			text : '编码',//baseinfo.priceCoupon.i18n('foss.priceCoupon.code'),//编码
			width:140,
			dataIndex : 'code'
		},{
			text : '方案名称',//baseinfo.priceCoupon.i18n('foss.priceCoupon.schemeName'),//方案名称
			width:180,
			dataIndex : 'name'
		},{
			text :'状态',//baseinfo.priceCoupon.i18n('foss.baseinfo.status'),//状态
			dataIndex : 'active',
			width:50,
			renderer:function(value){
				if(value=='Y'){//'Y'表示激活
					return '已激活';//baseinfo.priceCoupon.i18n('foss.priceCoupon.active');
				}else if(value=='N'){//'N'表示未激活
					return '未激活';//baseinfo.priceCoupon.i18n('foss.priceCoupon.inActive');
				}else{
					return '';
				}
			}
		},{
			text : '创建人',//baseinfo.priceCoupon.i18n('foss.priceCoupon.creator'),//创建人
			width:80,
			dataIndex : 'createUserName'
		},{
			text :'修改时间',//baseinfo.priceCoupon.i18n('foss.priceCoupon.modifyDate'),//修改时间
			width:150,
			dataIndex : 'modifyDate',
			renderer:function(value){
				return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
			}
		},{
			text :'起始时间',//baseinfo.priceCoupon.i18n('foss.priceCoupon.beginDate'),//起始时间
			width:100,
			dataIndex : 'beginTime',
			renderer:function(value){
				return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
			}
		},{
			text :'截止时间',//baseinfo.priceCoupon.i18n('foss.priceCoupon.endDate'),//截止时间
			width:100,
			dataIndex : 'endTime',
			renderer:function(value){
				return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
			}
		}];
		me.store = Ext.create('Foss.baseinfo.priceCoupon.priceCouponStore',{
			autoLoad : false,
			pageSize : 20,
			listeners: {
				beforeload: function(store, operation, eOpts){
					var queryForm = me.up().getQueryPriceCouponForm();
					if(queryForm!=null){
						Ext.apply(operation,{
							params : {
								'priceCouponVo.marketingSchemeEntity.active':queryForm.getForm().findField('active').getValue(),//状态
								'priceCouponVo.marketingSchemeEntity.name':queryForm.getForm().findField('name').getValue(),//方案名称
								'priceCouponVo.marketingSchemeEntity.businessDate':queryForm.getForm().findField('businessDate').getValue()//业务日期
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
			text : '新增',//baseinfo.priceCoupon.i18n('foss.baseinfo.add'),//新增
			disabled: !baseinfo.priceCoupon.isPermission('priceCoupon/priceCouponAddbutton'),
			hidden: !baseinfo.priceCoupon.isPermission('priceCoupon/priceCouponAddbutton'),
			handler :function(){
				me.getPriceCouponAddWindow().show();
			} 
		},'-', {
			//删除
			text : '作废',//baseinfo.priceCoupon.i18n('foss.baseinfo.void'),//作废
			disabled: !baseinfo.priceCoupon.isPermission('priceCoupon/priceCouponDeletebutton'),
			hidden: !baseinfo.priceCoupon.isPermission('priceCoupon/priceCouponDeletebutton'),
			handler :function(){
				me.deletePricingCoupon();
			} 
		},
		'-', {
			text : '立即激活',//baseinfo.priceCoupon.i18n('foss.baseinfo.activatedImmediately'),//'立即激活',
			disabled:!baseinfo.priceCoupon.isPermission('priceCoupon/priceCouponImmediatelyActivebutton'),
			hidden:!baseinfo.priceCoupon.isPermission('priceCoupon/priceCouponImmediatelyActivebutton'),
			handler :function(){
				me.immediatelyActive();
			} 
		},'-', {
			text : '立即中止',//baseinfo.priceCoupon.i18n('foss.baseinfo.terminationImmediately'),//'立即中止',
			disabled:!baseinfo.priceCoupon.isPermission('priceCoupon/priceCouponImmediatelyStopbutton'),
			hidden:!baseinfo.priceCoupon.isPermission('priceCoupon/priceCouponImmediatelyStopbutton'),
			handler :function(){
				me.immediatelyStop();
			} 
		},'-', {
			text : '模板下载',//baseinfo.priceCoupon.i18n('foss.baseinfo.terminationImmediately'),//'立即中止',
			disabled:!baseinfo.priceCoupon.isPermission('priceCoupon/priceCouponTemplateDownloadbutton'),
			hidden:!baseinfo.priceCoupon.isPermission('priceCoupon/priceCouponTemplateDownloadbutton'),
			plugins: {
				ptype: 'buttondisabledplugin',
				seconds: 5
			},
			handler :function(){
				templateDownload();
			} 
		}];
		me.bbar = me.getPagingToolbar();
		me.callParent([cfg]);
	}
});

/**
 * @description 降价发券方案主页
 */
Ext.onReady(function() {
	Ext.QuickTips.init();
	if (Ext.getCmp('T_baseinfo-priceCoupon_content')) {
		return;
	}
	baseinfo.haveServerNowTime();
	var queryPriceCouponForm = Ext.create('Foss.baseinfo.priceCoupon.QueryPriceCouponForm');//查询条件
	var priceCouponGridPanel = Ext.create('Foss.baseinfo.priceCoupon.PriceCouponGridPanel');//查询结果
	Ext.getCmp('T_baseinfo-priceCoupon').add(Ext.create('Ext.panel.Panel', {
	  	id : 'T_baseinfo-priceCoupon_content',
		cls : 'panelContentNToolbar',
		bodyCls : 'panelContentNToolbar-body',
		//获得查询FORM
		getQueryPriceCouponForm : function() {
			return queryPriceCouponForm;
		},
		//获得查询结果GRID
		getAreaGrid : function() {
			return priceCouponGridPanel;
		},
		items : [ queryPriceCouponForm, priceCouponGridPanel]
	}));
});
/**
 * 降价发券方案-新增
 */
Ext.define('Foss.baseinfo.priceCoupon.PriceCouponAddWindow',{
	extend : 'Ext.window.Window',
	title : '价格降价发券方案定义',//价格降价发券方案定义baseinfo.priceCoupon.i18n('foss.priceCoupon.priceCouponSchemeDefine')
	closable : true,
	modal : true,
	resizable:false,
	autoScroll:true,
	parent:null,//(Foss.baseinfo.priceCoupon.PriceCouponGridPanel)
	closeAction : 'hide',
	width :680,
	height :600,	
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	listeners:{
		beforehide:function(me){
			me.getPriceCouponEditFormPanel().getForm().reset();
		},
		beforeshow:function(me){
			
		}
	},
	//降价发券方案主信息FORM
	priceCouponEditFormPanel:null,
	getPriceCouponEditFormPanel:function(){
		if(Ext.isEmpty(this.priceCouponEditFormPanel)){
    		this.priceCouponEditFormPanel = Ext.create('Foss.baseinfo.priceCoupon.PriceCouponEditFormPanel',{
    			isShow:false
    		});
    	}
    	return this.priceCouponEditFormPanel;
	},
	//提交降价发券方案主信息
	commintPriceCoupon:function(){
    	var me = this;
    	var form = me.getPriceCouponEditFormPanel();
    	if(form.getForm().isValid()){//校验form是否通过校验
    		var marketingEventModel = new Foss.baseinfo.priceCoupon.MarketingEventEntity();// 降价发券方案主信息
//    		var channelEntityList = new Array();//渠道信息
    		form.getForm().updateRecord(marketingEventModel);//将FORM中数据设置到MODEL里面
    		if(marketingEventModel.get('beginTime').getTime()>=marketingEventModel.get('endTime')){
    			baseinfo.showWoringMessage('终止日期需大于起始日期！');//终止日期需大于起始日期！baseinfo.priceCoupon.i18n('foss.priceCoupon.endDateBehindBeginDate')
    			return;
    		}
    		if(marketingEventModel.get('productItem').length>100){
    			baseinfo.showWoringMessage('产品类型输入过多！');
    			return;
    		}
    		if(marketingEventModel.get('customerDegree').length>100){
    			baseinfo.showWoringMessage('客户等级输入过多！');
    			return;
    		}
    		if(marketingEventModel.get('orderSource').length>2000){
    			baseinfo.showWoringMessage('订单来源输入过多！');
    			return;
    		}
    		if(marketingEventModel.get('customerProfession').length>2000){
    			baseinfo.showWoringMessage('客户行业输入过多！');
    			return;
    		}
    		var params = {'priceCouponVo':{'marketingSchemeEntity':marketingEventModel.data
    			}};//降价发券新增数据
    		var successFun = function(json){
				baseinfo.showInfoMes(json.message);//提示新增成功
				me.close();
				me.parent.getPagingToolbar().moveFirst();//成功之后重新查询刷新结果集
				me.parent.getPriceCouponDeatilAddWindow().marketingEventEntity = json.priceCouponVo.marketingSchemeEntity;
				me.parent.getPriceCouponDeatilAddWindow().show();
			};
			var failureFun = function(json){
				if(Ext.isEmpty(json)){
					baseinfo.showErrorMes('请求超时');//请求超时baseinfo.priceCoupon.i18n('foss.baseinfo.requestOvertime')
				}else{
					baseinfo.showErrorMes(json.message);//提示失败原因
				}
			};
			var url = baseinfo.realPath('addPricingCoupon.action');//请求降价发券新增
			baseinfo.requestJsonAjax(url,params,successFun,failureFun);//发送AJAX请求
   	    }
	},
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [me.getPriceCouponEditFormPanel()];
		me.fbar = [{
			text : '返回列表',//baseinfo.priceCoupon.i18n('foss.priceCoupon.returnList')
			handler :function(){
				me.close();
			} 
		},{
			text : '提交',//baseinfo.priceCoupon.i18n('foss.priceCoupon.commit')
			cls:'yellow_button',
			margin:'0 0 0 360',
			handler :function(){
				me.commintPriceCoupon();
			} 
		}];
		me.callParent([cfg]);
	}
});
/**
 * 降价发券方案-修改
 */
Ext.define('Foss.baseinfo.priceCoupon.PriceCouponUpdateWindow',{
	extend : 'Ext.window.Window',
	title : '降价发券方案定义',//降价发券方案定义baseinfo.priceCoupon.i18n('foss.priceCoupon.priceCouponSchemeDefine')
	closable : true,
	modal : true,
	resizable:false,
	autoScroll:true,
	marketingEventEntity:null,//降价发券方案主信息
	parent:null,//(Foss.baseinfo.priceCoupon.PriceCouponGridPanel)
	closeAction : 'hide',
	width :680,
	height :600,	
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	listeners:{
		beforehide:function(me){
			me.getPriceCouponEditFormPanel().getForm().reset();
		},
		beforeshow:function(me){
			var form = me.getPriceCouponEditFormPanel();
			if(!Ext.isEmpty(me.marketingEventEntity)){//方案修改
				if(me.marketingEventEntity.lineRegion=='出发城市'){
					me.marketingEventEntity.lineRegion='IS_DEPARTURE_CITY';
				}else if(me.marketingEventEntity.lineRegion=='线路'){
					me.marketingEventEntity.lineRegion='IS_LINE';
				}
				//四个多选公共选择器初始化
				me.marketingEventEntity.customerProfession=null;
				me.marketingEventEntity.productItem=null;
				me.marketingEventEntity.orderSource=null;
				me.marketingEventEntity.customerDegree=null;
				//加载数据
				form.getForm().loadRecord(new Foss.baseinfo.priceCoupon.MarketingEventEntity(me.marketingEventEntity));
				//加载四个多选公共选择器数据
				var combo1=me.getPriceCouponEditFormPanel().getForm().findField('customerProfession');
				combo1.getStore().loadData(me.marketingEventEntity.cusProEntityList);
				combo1.setValue(me.marketingEventEntity.cusProValue);
				var combo2=me.getPriceCouponEditFormPanel().getForm().findField('productItem');
				combo2.getStore().loadData(me.marketingEventEntity.proItemEntityList);
				combo2.setValue(me.marketingEventEntity.proItemValue);
				var combo3=me.getPriceCouponEditFormPanel().getForm().findField('orderSource');
				combo3.getStore().loadData(me.marketingEventEntity.ordSouEntityList);
				combo3.setValue(me.marketingEventEntity.ordSouValue);
				var combo4=me.getPriceCouponEditFormPanel().getForm().findField('customerDegree');
				combo4.getStore().loadData(me.marketingEventEntity.cusDgrEntityList);
				combo4.setValue(me.marketingEventEntity.cusDgrValue);
			}else{
				baseinfo.showErrorMes('没有降价发券方案主信息！');//没有降价发券方案主信息！baseinfo.priceCoupon.i18n('foss.priceCoupon.noPriceCouponSchemeMainInfo')
				return;
			}
		}
	},
	//降价发券方案主信息FORM
	priceCouponEditFormPanel:null,
	getPriceCouponEditFormPanel:function(){
		if(Ext.isEmpty(this.priceCouponEditFormPanel)){
    		this.priceCouponEditFormPanel = Ext.create('Foss.baseinfo.priceCoupon.PriceCouponEditFormPanel',{
    			isShow:false
    		});
    	}
    	return this.priceCouponEditFormPanel;
	},
	//提交降价发券方案主信息
	commintPriceCoupon:function(){
    	var me = this;
    	var form = me.getPriceCouponEditFormPanel();
    	if(form.getForm().isValid()){//校验form是否通过校验
    		var marketingEventModel = new Foss.baseinfo.priceCoupon.MarketingEventEntity(me.marketingEventEntity);// 降价发券方案主信息
    		form.getForm().updateRecord(marketingEventModel);//将FORM中数据设置到MODEL里面
    		if(marketingEventModel.get('beginTime').getTime()>=marketingEventModel.get('endTime')){
    			baseinfo.showWoringMessage('终止日期需大于起始日期！');//终止日期需大于起始日期！baseinfo.priceCoupon.i18n('foss.priceCoupon.endDateBehindBeginDate')
    			return;
    		}
    		if(marketingEventModel.get('productItem').length>100){
    			baseinfo.showWoringMessage('产品类型输入过多！');
    			return;
    		}
    		if(marketingEventModel.get('customerDegree').length>100){
    			baseinfo.showWoringMessage('客户等级输入过多！');
    			return;
    		}
    		if(marketingEventModel.get('orderSource').length>2000){
    			baseinfo.showWoringMessage('订单来源输入过多！');
    			return;
    		}
    		if(marketingEventModel.get('customerProfession').length>2000){
    			baseinfo.showWoringMessage('客户行业输入过多！');
    			return;
    		}
    		var params = {'priceCouponVo':{'marketingSchemeEntity':marketingEventModel.data}};//降价发券新增数据
    		var successFun = function(json){
				baseinfo.showInfoMes(json.message);//提示修改成功
				me.close();
				me.parent.getPagingToolbar().moveFirst();//成功之后重新查询刷新结果集
				me.parent.getPriceCouponDeatilUpdateWindow().marketingEventEntity = json.priceCouponVo.marketingSchemeEntity;
				me.parent.getPriceCouponDeatilUpdateWindow().show();
			};
			var failureFun = function(json){
				if(Ext.isEmpty(json)){
					baseinfo.showErrorMes('请求超时！');//请求超时baseinfo.priceCoupon.i18n('foss.baseinfo.requestOvertime')
				}else{
					baseinfo.showErrorMes(json.message);//提示失败原因
				}
			};
			var url = baseinfo.realPath('updatePricingCoupon.action');//请求降价发券修改
			baseinfo.requestJsonAjax(url,params,successFun,failureFun);//发送AJAX请求
   	    }
	},
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [me.getPriceCouponEditFormPanel()];
		me.fbar = [{
			text : '返回列表',//baseinfo.priceCoupon.i18n('foss.priceCoupon.returnList')
			handler :function(){
				me.close();
			} 
		},{
			text : '提交',//baseinfo.priceCoupon.i18n('foss.priceCoupon.commit')
			cls:'yellow_button',
			margin:'0 0 0 285',
			handler :function(){
				me.commintPriceCoupon();
			} 
		},{
			text : '修改明细',//修改明细baseinfo.priceCoupon.i18n('foss.priceCoupon.editDetail')
			cls:'yellow_button',
			handler :function(){
				var id = me.marketingEventEntity.id;
            	var params = {'priceCouponVo':{'marketingSchemeEntity':{'id':id}}};
				var successFun = function(json){
					me.parent.getPriceCouponDeatilUpdateWindow().marketingEventEntity = json.priceCouponVo.marketingSchemeEntity;
					me.parent.getPriceCouponDeatilUpdateWindow().show();
				};
				var failureFun = function(json){
					if(Ext.isEmpty(json)){
						baseinfo.showErrorMes('请求超时！');//请求超时baseinfo.priceCoupon.i18n('foss.baseinfo.requestOvertime')
					}else{
						baseinfo.showErrorMes(json.message);
					}
				};
				var url = baseinfo.realPath('selectMarketingByPrimaryKey.action');
				baseinfo.requestJsonAjax(url,params,successFun,failureFun);
			} 
		}];
		me.callParent([cfg]);
	}
});
/**
 * 降价发券方案明细-查看
 */
Ext.define('Foss.baseinfo.priceCoupon.PriceCouponDeatilShowWindow',{
	extend : 'Ext.window.Window',
	title : '查看降价发券方案',//查看降价发券方案baseinfo.priceCoupon.i18n('foss.priceCoupon.showSchemeDetail')
	resizable:false,
	marketingEventEntity:null,//降价发券方案主信息
	parent:null,//(Foss.baseinfo.priceCoupon.PriceCouponGridPanel)
	closeAction : 'hide',
	width :650,
	height :720,	
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	listeners:{
		beforehide:function(me){
			me.getPriceCouponShowTab().getPriceCouponEditFormPanel().getForm().reset();
			me.getPriceCouponShowTab().getPricingCouponDetailShowPanel().getQueryPriceCouponDetailForm().getForm().reset();
			me.getPriceCouponShowTab().getPricingCouponDetailShowPanel().getPriceCouponDetailGridPanel().getStore().removeAll();
		},
		beforeshow:function(me){
			var form = me.getPriceCouponShowTab().getPriceCouponEditFormPanel();
			if(!Ext.isEmpty(me.marketingEventEntity)){
				form.getForm().loadRecord(new Foss.baseinfo.priceCoupon.MarketingEventEntity(me.marketingEventEntity));//加载数据
			}else{
				baseinfo.showErrorMes('没有降价发券方案主信息！');//没有降价发券方案主信息！baseinfo.priceCoupon.i18n('foss.priceCoupon.noPriceCouponSchemeMainInfo')
				return;
			}
		}
	},
	//降价发券方案明细新增
	priceCouponShowTab:null,
	getPriceCouponShowTab:function(){
		if(Ext.isEmpty(this.priceCouponAddTab)){
    		this.priceCouponAddTab = Ext.create('Foss.baseinfo.priceCoupon.PriceCouponShowTab');
    	}
    	return this.priceCouponAddTab;
	},
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [me.getPriceCouponShowTab()];
		me.fbar = [{
			text : '返回列表',//返回列表baseinfo.priceCoupon.i18n('foss.priceCoupon.returnList')
			handler :function(){
				me.close();
			} 
		}];
		me.callParent([cfg]);
	}
});
/**
 * 降价发券方案明细-新增
 */
Ext.define('Foss.baseinfo.priceCoupon.PriceCouponDeatilAddWindow',{
	extend : 'Ext.window.Window',
	title : '降价发券方案定义',//价格降价发券定义baseinfo.priceCoupon.i18n('foss.priceCoupon.priceCouponSchemeDefine')
	closable : true,
	modal : true,
	startAllNet:null,//全网
	arrvAllNet:null,//全网
	resizable:false,
	marketingEventEntity:null,//显示的降价发券主信息
	channelEntityList:null,//渠道信息
	parent:null,//(Foss.baseinfo.priceCoupon.PriceCouponGridPanel)
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
			me.getPriceCouponAddTab().getPriceCouponEditFormPanel().getForm().reset();//清除降价发券方案主信息
			me.getPriceCouponAddTab().getPriceCouponDetailEditFormPanel().getForm().reset();//清除降价发券方案明细信息
			me.marketingEventEntity = null;//清掉降价发券主信息
			me.getPriceCouponAddTab().getStartArraiveGridPanel().getStartGrid().getStore().removeAll();//清除出发表数据
			if(me.getPriceCouponAddTab().getStartArraiveGridPanel().getStartGrid().dockedItems.items[2] != undefined) {
//				me.getPriceCouponAddTab().getStartArraiveGridPanel().getStartGrid().dockedItems.items[2].items.items[8].reset();//全网不勾选
			}
			me.getPriceCouponAddTab().getStartArraiveGridPanel().getArriveGrid().getStore().removeAll();//清除到达表数据
			if(me.getPriceCouponAddTab().getStartArraiveGridPanel().getArriveGrid().dockedItems.items[2] != undefined) {
//				me.getPriceCouponAddTab().getStartArraiveGridPanel().getArriveGrid().dockedItems.items[2].items.items[8].reset();//全网不勾选
			}
		},
		beforeshow:function(me){
			var form = me.getPriceCouponAddTab().getPriceCouponEditFormPanel();
			if(!Ext.isEmpty(me.marketingEventEntity)){
				form.getForm().loadRecord(new Foss.baseinfo.priceCoupon.MarketingEventEntity(me.marketingEventEntity));//加载数据
			}else{
				baseinfo.showErrorMes('没有降价发券方案主信息！');//没有降价发券方案主信息！baseinfo.priceCoupon.i18n('foss.priceCoupon.noPriceCouponSchemeMainInfo')
				return;
			}
			me.getPriceCouponAddTab().getPricingCouponDetailShowPanel().getPriceCouponDetailGridPanel().getStore().removeAll();
		}
	},
	//降价发券方案明细新增
	priceCouponAddTab:null,
	getPriceCouponAddTab:function(){
		if(Ext.isEmpty(this.priceCouponAddTab)){
    		this.priceCouponAddTab = Ext.create('Foss.baseinfo.priceCoupon.PriceCouponAddTab');
    	}
    	return this.priceCouponAddTab;
	},
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [me.getPriceCouponAddTab()];
		me.fbar = [{
			text : '返回列表',//返回列表baseinfo.priceCoupon.i18n('foss.priceCoupon.returnList')
			handler :function(){
				me.close();
			} 
		}];
		me.callParent([cfg]);
	}
});
/**
 * 降价发券方案明细-查看
 */
Ext.define('Foss.baseinfo.priceCoupon.PriceCouponDeatilShowWindow',{
	extend : 'Ext.window.Window',
	title : '查看降价发券方案',//查看降价发券方案baseinfo.priceCoupon.i18n('foss.priceCoupon.showSchemeDetail')
	resizable:false,
	marketingEventEntity:null,//降价发券方案主信息
	channelEntityList:null,//渠道信息
	parent:null,//(Foss.baseinfo.priceCoupon.PriceCouponGridPanel)
	closeAction : 'hide',
	width :650,
	height :720,	
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	listeners:{
		beforehide:function(me){
			me.getPriceCouponShowTab().getPriceCouponEditFormPanel().getForm().reset();
			me.getPriceCouponShowTab().getPricingCouponDetailShowPanel().getQueryPriceCouponDetailForm().getForm().reset();
			me.getPriceCouponShowTab().getPricingCouponDetailShowPanel().getPriceCouponDetailGridPanel().getStore().removeAll();
		},
		beforeshow:function(me){
			var form = me.getPriceCouponShowTab().getPriceCouponEditFormPanel();
			if(!Ext.isEmpty(me.marketingEventEntity)){
				form.getForm().loadRecord(new Foss.baseinfo.priceCoupon.MarketingEventEntity(me.marketingEventEntity));//加载数据
			}else{
				baseinfo.showErrorMes('没有降价发券方案主信息！');//没有降价发券方案主信息！baseinfo.priceCoupon.i18n('foss.priceCoupon.noPriceCouponSchemeMainInfo')
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
	//降价发券方案明细新增
	priceCouponShowTab:null,
	getPriceCouponShowTab:function(){
		if(Ext.isEmpty(this.priceCouponAddTab)){
    		this.priceCouponAddTab = Ext.create('Foss.baseinfo.priceCoupon.PriceCouponShowTab');
    	}
    	return this.priceCouponAddTab;
	},
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [me.getPriceCouponShowTab()];
		me.fbar = [{
			text : '返回列表',//返回列表baseinfo.priceCoupon.i18n('foss.priceCoupon.returnList')
			handler :function(){
				me.close();
			} 
		}];
		me.callParent([cfg]);
	}
});

/**
 * 查看降价发券方案-TAB
 */
Ext.define('Foss.baseinfo.priceCoupon.PriceCouponShowTab', {
	extend : 'Ext.tab.Panel',
	cls : 'innerTabPanel',
    flex:1,
    layout : {
		type : 'vbox',
		align : 'stretch'
	},
    //降价发券方案主信息FORM的展示Panel
    priceCouponEditFormPanel:null,
	getPriceCouponEditFormPanel:function(){
		if(Ext.isEmpty(this.priceCouponEditFormPanel)){
    		this.priceCouponEditFormPanel = Ext.create('Foss.baseinfo.priceCoupon.PriceCouponEditFormPanelShow',{
    			isShow:true
    		});
    		this.priceCouponEditFormPanel.getForm().getFields().each(function(item){
    			item.setReadOnly(true);
    		});
    	}
    	return this.priceCouponEditFormPanel;
	},
	//已选降价发券信息结果集
	pricingCouponDetailShowPanel:null,
	getPricingCouponDetailShowPanel:function(){
		if(Ext.isEmpty(this.pricingCouponDetailShowPanel)){
    		this.pricingCouponDetailShowPanel = Ext.create('Foss.baseinfo.priceCoupon.PricingCouponDetailShowPanel',{
    			isShow:true//表明只是查看
    		});
    	}
    	return this.pricingCouponDetailShowPanel;
	},
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [me.getPriceCouponEditFormPanel(),me.getPricingCouponDetailShowPanel()];
		me.callParent([cfg]);
	}
});
/**
 * 新增降价发券方案-TAB
 */
Ext.define('Foss.baseinfo.priceCoupon.PriceCouponAddTab', {
	extend : 'Ext.tab.Panel',
	cls : 'innerTabPanel',
    flex:1,
    layout : {
		type : 'vbox',
		align : 'stretch'
	},
    //降价发券方案主信息FORM展示Panel
    priceCouponEditFormPanel:null,
	getPriceCouponEditFormPanel:function(){
		if(Ext.isEmpty(this.priceCouponEditFormPanel)){
    		this.priceCouponEditFormPanel = Ext.create('Foss.baseinfo.priceCoupon.PriceCouponEditFormPanelShow',{
    			isShow:true
    		});
    		this.priceCouponEditFormPanel.getForm().getFields().each(function(item){
    			item.setReadOnly(true);
    		});
    	}
    	return this.priceCouponEditFormPanel;
	},
	 //维护降价发券信息FORM
	priceCouponDetailEditFormPanel:null,
	getPriceCouponDetailEditFormPanel:function(){
		if(Ext.isEmpty(this.priceCouponDetailEditFormPanel)){
    		this.priceCouponDetailEditFormPanel = Ext.create('Foss.baseinfo.priceCoupon.PriceCouponDetailEditFormPanel');
    	}
    	return this.priceCouponDetailEditFormPanel;
	},
	//出发到达grid panel
	startArraiveGridPanel:null,
	getStartArraiveGridPanel:function(){
		if(Ext.isEmpty(this.startArraiveGridPanel)){
    		this.startArraiveGridPanel = Ext.create('Foss.baseinfo.priceCoupon.StartArraiveGridPanel');
    	}
    	return this.startArraiveGridPanel;
	},
	//已选降价发券信息结果集
	pricingCouponDetailShowPanel:null,
	getPricingCouponDetailShowPanel:function(){
		if(Ext.isEmpty(this.pricingCouponDetailShowPanel)){
    		this.pricingCouponDetailShowPanel = Ext.create('Foss.baseinfo.priceCoupon.PricingCouponDetailShowPanel',{
    			'isShow':false
    		});
    	}
    	return this.pricingCouponDetailShowPanel;
	},
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [me.getPriceCouponEditFormPanel(),me.getStartArraiveGridPanel(),me.getPriceCouponDetailEditFormPanel(),me.getPricingCouponDetailShowPanel()];
		me.callParent([cfg]);
	}
});
/**
 * 维护降价发券信息
 */
Ext.define('Foss.baseinfo.priceCoupon.PriceCouponDetailEditFormPanel', {
	extend : 'Ext.form.Panel',
	//frame: true,
	title:'维护降价发券信息',//维护降价发券信息baseinfo.priceCoupon.i18n('foss.priceCoupon.manageSchemeDetail')
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
	//新增降价发券方案明细
	commintPriceCouponDetail:function(){
        var me = this;
        var startCouponOrgEntityList = new Array();//出发部门
        var endCouponOrgEntityList = new Array();//到达部门
        me.up('window').getPriceCouponAddTab().getStartArraiveGridPanel().getStartGrid().getStore().each(function(record){
        	startCouponOrgEntityList.push(record.data);
        });
        me.up('window').getPriceCouponAddTab().getStartArraiveGridPanel().getArriveGrid().getStore().each(function(record){
        	endCouponOrgEntityList.push(record.data);
        });
        if(startCouponOrgEntityList.length<1&&Ext.isEmpty(me.up('window').startAllNet)){
        	baseinfo.showWoringMessage('请选择出发地！');//请选择出发地！baseinfo.priceCoupon.i18n('foss.priceCoupon.selectDeparture')
        	return;
        }
        if(endCouponOrgEntityList.length<1&&Ext.isEmpty(me.up('window').arrvAllNet)){
        	baseinfo.showWoringMessage('请选择到达地！');//请选择到达地！baseinfo.priceCoupon.i18n('foss.priceCoupon.selectArrival')
        	return;
        }
        if(me.getForm().isValid()){
        	var priceCouponDtoModel = new Foss.baseinfo.priceCoupon.PriceCouponDto();
            me.getForm().updateRecord(priceCouponDtoModel);//更新数据
            //重量范围与体积范围规则
            if(priceCouponDtoModel.get('weightLeftRange')>priceCouponDtoModel.get('weightRightRange')){
            	baseinfo.showWoringMessage('重量起点不能大于重量终点！');//开始范围不能大于结束范围！baseinfo.priceCoupon.i18n('foss.priceCoupon.beginRangeNotBiggerEndRange')
            	return;
            }
            if(priceCouponDtoModel.get('volumeLeftRange')>priceCouponDtoModel.get('volumeRightRange')){
            	baseinfo.showWoringMessage('体积起点不能大于体积终点！');//开始范围不能大于结束范围！baseinfo.priceCoupon.i18n('foss.priceCoupon.beginRangeNotBiggerEndRange')
            	return;
            }
            var beginTime = me.up('window').marketingEventEntity.beginTime;//开始日期
            priceCouponDtoModel.set('beginDate',beginTime);
            var endTime = me.up('window').marketingEventEntity.endTime;//截止日期
            priceCouponDtoModel.set('endDate',endTime);
            var marketId = me.up('window').marketingEventEntity.id;//降价发券方案主信息ID
            priceCouponDtoModel.set('marketId',marketId);
            var marketCode = me.up('window').marketingEventEntity.code;//降价发券方案主信息CODE
            priceCouponDtoModel.set('marketCode',marketCode);
            var productCode = me.getForm().findField('productItem').getValue();
            priceCouponDtoModel.set('productCode',productCode);
            var startAllNet = me.up('window').startAllNet;
            var arrvAllNet = me.up('window').arrvAllNet;
            var param = {'priceCouponVo':{'startCouponOrgEntityList':startCouponOrgEntityList
            	,'endCouponOrgEntityList':endCouponOrgEntityList
            	,'priceCouponDto':priceCouponDtoModel.data
            	,'startAllNet':startAllNet
            	,'arrvAllNet':arrvAllNet}};
            var successFun = function(json){
            	baseinfo.showInfoMes(json.message);
				me.up('window').getPriceCouponAddTab().getPricingCouponDetailShowPanel().getPriceCouponDetailGridPanel().getPagingToolbar().moveFirst();
            };
            var failureFun = function(json){
            	if(Ext.isEmpty(json)){
					baseinfo.showErrorMes('请求超时！');//请求超时！baseinfo.priceCoupon.i18n('foss.baseinfo.requestOvertime')
				}else{
					baseinfo.showErrorMes(json.message);
				}
            };
        	var url = baseinfo.realPath('addCouponProgramItem.action');
			baseinfo.requestJsonAjax(url,param,successFun,failureFun);
        }  
	},
	constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [{
			name:'productItem',
	        xtype: 'commonthirdlevelproductmultiselector',
	        active: 'Y',//已激活的产品条目
	        fieldLabel:'产品类型',//产品类型baseinfo.priceCoupon.i18n('foss.priceCoupon.productPattern')
	        colspan: 2,
	        labelWidth: 120,  
	    	width:285
		},{
			xtype:'numberfield',
	        decimalPrecision:2,
	        labelWidth: 120,  
	        allowBlank:false,
	        name:'weightLeftRange',
	        fieldLabel:'重量范围',//开始范围baseinfo.priceCoupon.i18n('foss.priceCoupon.beginRange')
	        step:0.01,
	        maxValue: 99999999.99,
	        minValue: 0 
		},{
			xtype:'numberfield',
	        decimalPrecision:2,
	        labelWidth: 30,  
	        allowBlank:false,
	        name:'weightRightRange',
	        fieldLabel:'至',//开始范围baseinfo.priceCoupon.i18n('foss.priceCoupon.beginRange')
	        step:0.01,
	        maxValue: 99999999.99,
	        minValue: 0 
		},{
			xtype:'numberfield',
	        decimalPrecision:2,
	        labelWidth: 120,  
	        allowBlank:false,
	        name:'volumeLeftRange',
	        fieldLabel:'体积范围',//结束范围baseinfo.priceCoupon.i18n('foss.priceCoupon.endRange')
	        step:0.01,
	        maxValue: 99999999.99,
	        minValue: 0 
		},{
			xtype:'numberfield',
	        decimalPrecision:2,
	        labelWidth: 30,  
	        allowBlank:false,
	        name:'volumeRightRange',
	        fieldLabel:'至',//结束范围baseinfo.priceCoupon.i18n('foss.priceCoupon.endRange')
	        step:0.01,
	        maxValue: 99999999.99,
	        minValue: 0 
		},{
			xtype:'numberfield',
	        decimalPrecision:2,
	        labelWidth: 120,  
	        colspan: 2,
	        allowBlank:false,
	        name:'couponRate',
	        value:0,
	        fieldLabel:'返券系数',//降价发券系数baseinfo.priceCoupon.i18n('foss.priceCoupon.priceCouponParameter')
	        maxValue: 1.00,
	        step:0.01,
	        minValue: 0
		},{
			colspan: 2,
			xtype:'radiogroup',
			labelWidth: 120,  
			layout:'column',
			fieldLabel :'是否接货',
			items: [{ boxLabel: '是', name: 'isPickUp',inputValue: 'Y'},
			         {	xtype:'container',
				        border:false,
				        html:'&nbsp;',
				        height:1,
				        columnWidth:.22 },
			        { boxLabel: '否', name: 'isPickUp',inputValue: 'N',checked:true}]
		}];
		me.fbar = [{
			text : '提交',//提交baseinfo.priceCoupon.i18n('foss.priceCoupon.commit')
			cls:'yellow_button',
			margin:'0 0 0 360',
			handler :function(){
				me.commintPriceCouponDetail();
			} 
		}];
		me.callParent([cfg]);
	}
});
/**
 * 降价发券方案主信息
 */
Ext.define('Foss.baseinfo.priceCoupon.PriceCouponEditFormPanel', {
	extend : 'Ext.form.Panel',
	frame: true,
	autoScroll: true,
	title:'降价发券方案定义',//降价发券方案定义baseinfo.priceCoupon.i18n('foss.priceCoupon.priceCouponSchemeDefine')
	flex:1,
	collapsible: true,
    defaults : {
    	colspan: 2,
    	labelWidth:110,
    	allowBlank:true,
    	margin : '5 5 5 5'
    },
	layout:{
		type:'table',
		columns: 2
	},
	constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		var minValueDate = null;
		if(!config.isShow){
			minValueDate = new Date(baseinfo.priceCoupon.tomorrowTime);
		}
		me.items = [{
			name: 'code',
		    fieldLabel:'编码',//编码baseinfo.priceCoupon.i18n('foss.priceCoupon.schemeCode')
		    readOnly:true,
		    emptyText:'自动生成编码',//自动生成编码baseinfo.priceCoupon.i18n('foss.priceCoupon.autoGenerateCode')
	        xtype : 'textfield'
		},{
			name: 'name',
			maxLength:20,
			allowBlank:false,
		    fieldLabel:'方案名称',//方案名称baseinfo.priceCoupon.i18n('foss.priceCoupon.schemeName')
	        xtype : 'textfield'
		},{
	        xtype: 'displayfield',
	        value:'<span style="font-size:10;font-weight:bold;color:blue">'
	        	+'优惠券使用条件'//优惠券使用条件baseinfo.priceCoupon.i18n('foss.priceCoupon.conditionToUseCoupon')
	        	+'</span>',//优惠券使用条件
	        margins: '0 0 0 10'
		},{
			name:'productItem',
	        xtype: 'commonthirdlevelproductmultiselector',
	        allowBlank:false,
	        active: 'Y',//已激活的产品条目
	        fieldLabel:'产品类型',//产品类型baseinfo.priceCoupon.i18n('foss.priceCoupon.productPattern')
	        colspan: 1,
	    	width:285
		},{
			name:'orderSource',
	        xtype: 'commonordersourcemultiselector',
	        fieldLabel:'订单来源',//订单来源baseinfo.priceCoupon.i18n('foss.priceCoupon.orderChannel')
	        width:285,
	        colspan: 1
		},{
			name:'customerDegree',
	        xtype: 'commoncustomerdegreemultiselector',
	        fieldLabel:'客户等级',//客户等级
	        colspan: 1,
	    	width:285
		},{
			name:'customerProfession',
	        xtype: 'commoncustomerprofessionmultiselector',
	        fieldLabel:'客户行业',//客户行业
	        width:285,
	        colspan: 1
	    },{
			name: 'beginTime',
			colspan: 1,
			width:285,
			format:'Y-m-d H:i:s',
			allowBlank:false,
			minValue:minValueDate,
		    fieldLabel:'方案期限',//降价发券期限baseinfo.priceCoupon.i18n('foss.priceCoupon.couponExpiration')
	        xtype:'datefield'
		},{
			name: 'endTime',
			colspan: 1,
			width:285,
			minValue:minValueDate,
			format:'Y-m-d H:i:s',
			allowBlank:false,
		    fieldLabel:'至',//至baseinfo.priceCoupon.i18n('foss.priceCoupon.to')
		    xtype : 'datefield'
		},{
			width:285,
			colspan: 1,
			xtype:'radiogroup',
			layout:'column',
			fieldLabel :'线路区域要求',
			items: [{ boxLabel: '出发城市', name: 'lineRegion',inputValue: 'IS_DEPARTURE_CITY'},
			         {	xtype:'container',
				        border:false,
				        html:'&nbsp;',
				        height:1,
				        columnWidth:.22 },
			        { boxLabel: '线路', name: 'lineRegion',inputValue: 'IS_LINE'}]
		},{
			name: 'availablePeriod',
	        allowBlank:false,
	        colspan: 1,
			fieldLabel:'优惠券有效期(天)',
			minValue: 0,
			maxValue: 9999,
			allowDecimals: false,
			xtype:'numberfield'
		},{
			name: 'couponTimeToSend',
			allowDecimals: false,
	        allowBlank:false,
	        colspan: 2,
	        step:1,
			fieldLabel:'返券时间(小时)',
			minValue: 0,
			maxValue: 99999999,
			xtype:'numberfield'
		},{
			name: 'remark',
			colspan: 2,
			width:570,
			maxLength:40,
			allowBlank:false,
		    fieldLabel:'优惠券短信内容',
		    afterSubTpl:'**元优惠券，编码：***，有效期至**年**月**日，该优惠券仅在****使用。用券详情咨询当地营业部！【德邦物流】',
	        xtype : 'textareafield'
		}];
		me.callParent([cfg]);
	}
});
/**
 * 降价发券方案主信息展示Panel
 */
Ext.define('Foss.baseinfo.priceCoupon.PriceCouponEditFormPanelShow', {
	extend : 'Ext.form.Panel',
	frame: true,
	autoScroll: true,
	title:'降价发券方案定义',//降价发券方案定义baseinfo.priceCoupon.i18n('foss.priceCoupon.priceCouponSchemeDefine')
	flex:1,
	collapsible: true,
    defaults : {
    	colspan: 2,
    	labelWidth:110,
    	allowBlank:true,
    	margin : '5 5 5 5'
    },
	layout:{
		type:'table',
		columns: 2
	},
	constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		var minValueDate = null;
		if(!config.isShow){
			minValueDate = new Date(baseinfo.priceCoupon.tomorrowTime);
		}
		me.items = [{
			name: 'code',
		    fieldLabel:'编码',//编码baseinfo.priceCoupon.i18n('foss.priceCoupon.schemeCode')
		    readOnly:true,
		    allowBlank:true,
		    emptyText:'自动生成编码',//自动生成编码baseinfo.priceCoupon.i18n('foss.priceCoupon.autoGenerateCode')
	        xtype : 'textfield'
		},{
			name: 'name',
			maxLength:20,
		    fieldLabel:'方案名称',//方案名称baseinfo.priceCoupon.i18n('foss.priceCoupon.schemeName')
	        xtype : 'textfield'
		},{
	        xtype: 'displayfield',
	        value:'<span style="font-size:10;font-weight:bold;color:blue">'
	        	+'优惠券使用条件'//优惠券使用条件baseinfo.priceCoupon.i18n('foss.priceCoupon.conditionToUseCoupon')
	        	+'</span>',//优惠券使用条件
	        margins: '0 0 0 10'
		},{
			name:'productItem',
	        xtype: 'textfield',
	        fieldLabel:'产品类型',//产品类型baseinfo.priceCoupon.i18n('foss.priceCoupon.productPattern')
	        colspan: 1,
	        allowBlank:false, 
	    	width:285
		},{
			name:'orderSource',
	        xtype: 'textfield',
	        fieldLabel:'订单来源',//订单来源baseinfo.priceCoupon.i18n('foss.priceCoupon.orderChannel')
	        width:285,
	        colspan: 1
		},{
			name:'customerDegree',
	        xtype: 'textfield',
	        fieldLabel:'客户等级',//客户等级
	        colspan: 1,
	    	width:285
		},{
			name:'customerProfession',
	        xtype: 'textfield',
	        fieldLabel:'客户行业',//客户行业
	        width:285,
	        colspan: 1
	    },{
			name: 'beginTime',
			colspan: 1,
			width:285,
			format:'Y-m-d H:i:s',
			minValue:minValueDate,
		    fieldLabel:'方案期限',//降价发券期限baseinfo.priceCoupon.i18n('foss.priceCoupon.couponExpiration')
	        xtype:'datefield'
		},{
			name: 'endTime',
			colspan: 1,
			width:285,
			minValue:minValueDate,
			format:'Y-m-d H:i:s',
		    fieldLabel:'至',//至baseinfo.priceCoupon.i18n('foss.priceCoupon.to')
	        xtype:'datefield'
		},{
			width:285,
			colspan: 1,
			xtype:'textfield',
			layout:'column',
			fieldLabel :'线路区域要求',
			name:'lineRegion'
		},{
			name: 'availablePeriod',
	        allowBlank:false,
	        colspan: 1,
			fieldLabel:'优惠券有效期(天)',
			minValue: 0,
			maxValue: 9999,
			allowDecimals: false,
			xtype:'numberfield'
		},{
			name: 'couponTimeToSend',
	        allowBlank:false,
	        colspan: 2,
			fieldLabel:'返券时间(小时)',
			minValue: 0,
			maxValue: 99999999,
			allowDecimals: false,
			xtype:'numberfield'
		},{
			name: 'remark',
			colspan: 2,
			width:570,
			maxLength:40,
			allowBlank:false,
		    fieldLabel:'优惠券短信内容',
	        xtype : 'textareafield'
		}];
		me.callParent([cfg]);
	}
});
//出发和到达PANEL
Ext.define('Foss.baseinfo.priceCoupon.StartArraiveGridPanel',{
	extend : 'Ext.panel.Panel',
	//frame: true,
	title : '出发和到达',//出发和到达baseinfo.priceCoupon.i18n('foss.priceCoupon.departureAndArrival')
	flex:1,
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	//出发GRID
	startGrid:null,
	getStartGrid:function(){
		if(Ext.isEmpty(this.startGrid)){
    		this.startGrid = Ext.create('Foss.baseinfo.priceCoupon.StartGrid');
    	}
    	return this.startGrid;
	},
	//到达GRID
	arriveGrid:null,
	getArriveGrid:function(){
		if(Ext.isEmpty(this.arriveGrid)){
    		this.arriveGrid = Ext.create('Foss.baseinfo.priceCoupon.ArriveGrid');
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
//降价发券详情新增修改
Ext.define('Foss.baseinfo.priceCoupon.PricingCouponDetailShowPanel',{
	extend : 'Ext.panel.Panel',
	title : '已选降价发券信息结果',//已选降价发券信息结果baseinfo.priceCoupon.i18n('foss.priceCoupon.selectedCouponInfoResult')
	//frame: true,
	flex:1,
	autoScroll:true,
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	//已选降价发券信息结果-查询条件
	queryPriceCouponDetailForm:null,
	getQueryPriceCouponDetailForm:function(isShow){
		if(Ext.isEmpty(this.queryPriceCouponDetailForm)){
    		this.queryPriceCouponDetailForm = Ext.create('Foss.baseinfo.priceCoupon.QueryPriceCouponDetailForm',{
    			'isShow':isShow
    		});
    	}
    	return this.queryPriceCouponDetailForm;
	},
	//已选降价发券信息结果GRID
	priceCouponDetailGridPanel:null,
	getPriceCouponDetailGridPanel:function(isShow){
		if(Ext.isEmpty(this.priceCouponDetailGridPanel)){
    		this.priceCouponDetailGridPanel = Ext.create('Foss.baseinfo.priceCoupon.PriceCouponDetailGridPanel',{
    			'isShow':isShow
    		});
    	}
    	return this.priceCouponDetailGridPanel;
	},
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [me.getQueryPriceCouponDetailForm(config.isShow),me.getPriceCouponDetailGridPanel(config.isShow)];
		me.callParent([cfg]);
	}
});
//出发GRID
Ext.define('Foss.baseinfo.priceCoupon.StartGrid', {
   extend:'Ext.grid.Panel', 
   title:'出发地信息',//出发地信息baseinfo.priceCoupon.i18n('foss.priceCoupon.departureRegionInfo')
   frame: true,
   autoScroll:true,
   sortableColumns:false,
   enableColumnHide:false,
   enableColumnMove:false,
   flex:1,
	//添加区域
   priceCouponRegionGridShowWindow:null,
   getPriceCouponRegionGridShowWindow:function(){
		if(Ext.isEmpty(this.priceCouponRegionGridShowWindow)){
   		     this.priceCouponRegionGridShowWindow = Ext.create('Foss.baseinfo.priceCoupon.PriceCouponRegionGridShowWindow');
   		     this.priceCouponRegionGridShowWindow.parent = this;
   		     this.priceCouponRegionGridShowWindow.type = baseinfo.priceCoupon.priceCouponStart;
	   	}
	   	return this.priceCouponRegionGridShowWindow;
    },
     //添加城市
    priceCouponCityGridShowWindow:null,
    getPriceCouponCityGridShowWindow:function(){
 		if(Ext.isEmpty(this.priceCouponCityGridShowWindow)){
    		     this.priceCouponCityGridShowWindow = Ext.create('Foss.baseinfo.priceCoupon.PriceCouponCityGridShowWindow');
    		     this.priceCouponCityGridShowWindow.parent = this;
    		     this.priceCouponCityGridShowWindow.type = baseinfo.priceCoupon.priceCouponStart;
 	   	}
 	   	return this.priceCouponCityGridShowWindow;
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
    					dict.showErrorMes('请至少选择一条数据！');//请至少选择一条数据！baseinfo.priceCoupon.i18n('foss.priceCoupon.selectAtLeastOne')
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
							var data = {'deptOrgId':selections[i].get('id'),'name':selections[i].get('name'),'deptOrgCode':selections[i].get('code'),'deptOrgTypeCode':baseinfo.priceCoupon.priceCouponDetailTypeDept};
							datas.push(data);
						}
					};
					var oldData = me.getStore().getAt(0);//清掉不是一类的数据
 					if(!Ext.isEmpty(oldData)){
 						if(oldData.get('deptOrgTypeCode')!=baseinfo.priceCoupon.priceCouponDetailTypeDept){
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
				name : '内部组织'//内部组织baseinfo.priceCoupon.i18n('foss.priceCoupon.innerOrganization')
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
				text : '操作',//操作baseinfo.priceCoupon.i18n('foss.priceCoupon.operate')
				xtype:'actioncolumn',
				align: 'center',
				width:80,
				items: [{
						iconCls: 'deppon_icons_delete',
		                tooltip: '作废',//作废baseinfo.priceCoupon.i18n('foss.priceCoupon.void')
						width:42,
		                handler: function(grid, rowIndex, colIndex) {
		            		//获取选中的数据
		    				var record=grid.getStore().getAt(rowIndex);
		            		baseinfo.showQuestionMes('是否要作废这个数据？',function(e){//是否要作废这个数据？baseinfo.priceCoupon.i18n('foss.priceCoupon.voidOrNotTheRecord')
		            			if(e=='yes'){//询问是否删除，是则发送请求
		            				grid.getStore().remove(record);
		            			}
		            		});
		                }
					}]
			  },{ 
		        	  header:'可选产品',//可选产品baseinfo.priceCoupon.i18n('foss.baseinfo.optionalProducts')
		        	  dataIndex: 'name'
	          },{ 
	        	  header:'类型'//类型baseinfo.priceCoupon.i18n('foss.priceCoupon.pattern')
	        	  ,dataIndex: 'deptOrgTypeCode'
	              ,renderer:function(value){
	            	  if(baseinfo.priceCoupon.priceCouponDetailTypeDept==value){
	            		  return '部门';//部门baseinfo.priceCoupon.i18n('foss.priceCoupon.department')
	            	  }else if(baseinfo.priceCoupon.priceCouponDetailTypeRegion==value){
	            		  return '区域';//区域baseinfo.priceCoupon.i18n('foss.priceCoupon.region')
	            	  }else if(baseinfo.priceCoupon.priceCouponDetailTypeCity==value){
	            		  return '城市';//城市baseinfo.priceCoupon.i18n('foss.priceCoupon.city')
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
		me.store = baseinfo.getStore(null,null,['deptOrgId','name','deptOrgCode','deptOrgTypeCode'],[]),
		me.tbar = [{
			//作废
			text : '作废',//作废baseinfo.priceCoupon.i18n('foss.baseinfo.void')
			handler :function(){
				//获取选中的数据
				var selections = me.getSelectionModel().getSelection();
				if(selections.length<1){
					baseinfo.showErrorMes('请选择要作废的数据！');//请选择要作废的数据！baseinfo.priceCoupon.i18n('foss.priceCoupon.selectVoidWantedRecord')
					return;
				}
				baseinfo.showQuestionMes('是否要作废这些数据？',function(e){//是否要作废这些数据？baseinfo.priceCoupon.i18n('foss.priceCoupon.voidOrNotTheRecord')
					if(e=='yes'){//询问是否作废，是则发送请求
						me.getStore().remove(selections);
					}
				});
			} 
		},'-', {
			//添加区域
			text : '添加区域',//添加区域baseinfo.priceCoupon.i18n('foss.priceCoupon.addRegion')
			handler :function(){
				me.getPriceCouponRegionGridShowWindow().show();
			} 
		}];
		me.callParent([cfg]);
	}
});
//到达GRID
Ext.define('Foss.baseinfo.priceCoupon.ArriveGrid', {
   extend:'Ext.grid.Panel', 
   frame: true,
   title:'到达地信息',//到达地信息baseinfo.priceCoupon.i18n('foss.priceCoupon.arrivalRegionInfo')
   sortableColumns:false,
   enableColumnHide:false,
   enableColumnMove:false,
   autoScroll:true,
   flex:1,
   //添加区域
   priceCouponRegionGridShowWindow:null,
   getPriceCouponRegionGridShowWindow:function(){
		if(Ext.isEmpty(this.priceCouponRegionGridShowWindow)){
   		     this.priceCouponRegionGridShowWindow = Ext.create('Foss.baseinfo.priceCoupon.PriceCouponRegionGridShowWindow');
   		     this.priceCouponRegionGridShowWindow.parent = this;
   		     this.priceCouponRegionGridShowWindow.type = baseinfo.priceCoupon.priceCouponArrive;
	   	}
	   	return this.priceCouponRegionGridShowWindow;
    },
    //添加城市
    priceCouponCityGridShowWindow:null,
    getPriceCouponCityGridShowWindow:function(){
 		if(Ext.isEmpty(this.priceCouponCityGridShowWindow)){
    		     this.priceCouponCityGridShowWindow = Ext.create('Foss.baseinfo.priceCoupon.PriceCouponCityGridShowWindow');
    		     this.priceCouponCityGridShowWindow.parent = this;
    		     this.priceCouponCityGridShowWindow.type = baseinfo.priceCoupon.priceCouponArrive;
 	   	}
 	   	return this.priceCouponCityGridShowWindow;
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
     					dict.showErrorMes('请至少选择一条数据！');//请至少选择一条数据！baseinfo.priceCoupon.i18n('foss.priceCoupon.selectAtLeastOne')
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
 							var data = {'arrvOrgId':selections[i].get('id'),'name':selections[i].get('name'),'arrvOrgCode':selections[i].get('code'),'arrvOrgTypeCode':baseinfo.priceCoupon.priceCouponDetailTypeDept};
 							datas.push(data);
 						}
 					};
 					var oldData = me.getStore().getAt(0)//清掉不是一类的数据
 					if(!Ext.isEmpty(oldData)){
 						if(oldData.get('arrvOrgTypeCode')!=baseinfo.priceCoupon.priceCouponDetailTypeDept){
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
				text : '操作',//操作baseinfo.priceCoupon.i18n('foss.priceCoupon.operate')
				xtype:'actioncolumn',
				align: 'center',
				width:80,
				items: [{
						iconCls: 'deppon_icons_delete',
		                tooltip: '作废',//作废baseinfo.priceCoupon.i18n('foss.baseinfo.void')
						width:42,
		                handler: function(grid, rowIndex, colIndex) {
		            		//获取选中的数据
		    				var record=grid.getStore().getAt(rowIndex);
		    				baseinfo.showQuestionMes('是否要作废这个数据？',function(e){//是否要作废这个数据？baseinfo.priceCoupon.i18n('foss.priceCoupon.voidOrNotTheRecord')
		            			if(e=='yes'){//询问是否作废，是则发送请求
		            				grid.getStore().remove(record);
		            			}
		            		})
		                }
					}]
			  },{ 
	        	  header:'可选产品'//可选产品baseinfo.priceCoupon.i18n('foss.baseinfo.optionalProducts')
	        	  ,dataIndex: 'name'
	          },{ 
	        	  header:'类型'//类型baseinfo.priceCoupon.i18n('foss.priceCoupon.pattern')
	        	  ,dataIndex: 'arrvOrgTypeCode'
	              ,renderer:function(value){
	            	  if(baseinfo.priceCoupon.priceCouponDetailTypeDept==value){
	            		  return '部门';//部门baseinfo.priceCoupon.i18n('foss.priceCoupon.department')
	            	  }else if(baseinfo.priceCoupon.priceCouponDetailTypeRegion==value){
	            		  return '区域';//区域baseinfo.priceCoupon.i18n('foss.priceCoupon.region')
	            	  }else if(baseinfo.priceCoupon.priceCouponDetailTypeCity==value){
	            		  return '城市';//城市baseinfo.priceCoupon.i18n('foss.priceCoupon.city')
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
		me.store = baseinfo.getStore(null,null,['arrvOrgId','name','arrvOrgCode','arrvOrgTypeCode'],[]),
		me.tbar = [{
			//删除
			text : '作废',//作废baseinfo.priceCoupon.i18n('foss.baseinfo.void')
			handler :function(){
				//获取选中的数据
				var selections = me.getSelectionModel().getSelection();
				if(selections.length<1){
					baseinfo.showErrorMes('请选择要作废的数据！');//请选择要作废的数据！baseinfo.priceCoupon.i18n('foss.priceCoupon.selectVoidWantedRecord')
					return;
				}
				baseinfo.showQuestionMes('是否要作废这些数据？',function(e){//是否要作废这些数据？baseinfo.priceCoupon.i18n('foss.priceCoupon.voidOrNotTheRecord')
					if(e=='yes'){//询问是否作废，是则发送请求
						me.getStore().remove(selections);
					}
				});
			} 
		},'-', {
			//添加区域
			text : '添加区域',//添加区域baseinfo.priceCoupon.i18n('foss.priceCoupon.addRegion')
			handler :function(){
				me.getPriceCouponRegionGridShowWindow().show();
			} 
		}];
		me.callParent([cfg]);
	}
});
/**
 * 已选降价发券信息结果-查询FORM
 */
Ext.define('Foss.baseinfo.priceCoupon.QueryPriceCouponDetailForm', {
	extend : 'Ext.form.Panel',
	title: '查询条件',//查询条件baseinfo.priceCoupon.i18n('foss.baseinfo.queryCondition')
	frame: true,
	collapsible: true,
    defaults : {
    	columnWidth : .5,
    	margin : '8 10 5 10',
    	anchor : '100%'
    },
    height :220,
    isShow:false,//是否查看(若是则导入按钮不可见，否则可见)
	defaultType : 'textfield',
	layout:'column',
	//导入窗口
	importWin:null,
	getImportWin:function(){
		if(this.importWin==null){
			this.importWin =Ext.create('Foss.baseinfo.priceCoupon.ImportWin');
		}
		return this.importWin;
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		var all = {'id':'','name':'全部'};//全部baseinfo.priceCoupon.i18n('foss.baseinfo.all')
		me.items  = [{
			name: 'deptOrgName',
			columnWidth: .5,
		    fieldLabel:'出发',//出发baseinfo.priceCoupon.i18n('foss.priceCoupon.departure')
	        xtype : 'textfield'
		},{
			name: 'arrvOrgName',
			columnWidth: .5,
		    fieldLabel:'到达',//到达baseinfo.priceCoupon.i18n('foss.priceCoupon.arrival')
	        xtype : 'textfield'
		},{
			name: 'active',
			queryMode: 'local',
		    displayField: 'valueName',
		    valueField: 'valueCode',
		    editable:false,
		    columnWidth: .5,
		    value:'',
		    store:baseinfo.getStore(null,null,['valueCode','valueName']
		    ,[{'valueCode':'Y','valueName':'已激活'}//已激活baseinfo.priceCoupon.i18n('foss.priceCoupon.active')
		    ,{'valueCode':'N','valueName':'未激活'}//未激活baseinfo.priceCoupon.i18n('foss.priceCoupon.inActive')
		    ,{'valueCode':'','valueName':'全部'}]),//全部baseinfo.priceCoupon.i18n('foss.baseinfo.all')
	        fieldLabel: '状态',//状态baseinfo.priceCoupon.i18n('foss.priceCoupon.status')
	        xtype : 'combo'
		},{
			name:'productCode',
	        xtype: 'commonthirdlevelproductmultiselector',
	        active: 'Y',//已激活的产品条目
	        fieldLabel:'产品类型',//产品类型baseinfo.priceCoupon.i18n('foss.priceCoupon.productPattern')
	        columnWidth: .5,
	    	width:285
		}];
		me.fbar = [{
			xtype : 'button', 
			width:70,
			text : '重置',//重置baseinfo.priceCoupon.i18n('foss.baseinfo.reset')
			handler : function() {
				me.getForm().reset();
			}
		},{
			xtype : 'button', 
			width:70,
			text : '导入',//查询baseinfo.priceCoupon.i18n('foss.baseinfo.query')
			hidden:config.isShow,
			margin:'0 20 0 260',
			cls:'yellow_button',
			handler : function() {
				me.getImportWin().show();
			}
		},{
			xtype : 'button', 
			width:70,
			text : '查询',//查询baseinfo.priceCoupon.i18n('foss.baseinfo.query')
			margin:'0 0 0 20',
			cls:'yellow_button',
			handler : function() {
				if(me.getForm().isValid()){
					var grid = me.up('panel').getPriceCouponDetailGridPanel();
					grid.getStore().load();
				}
			}
		}]
		me.callParent([cfg]);
	}
});
/**
 * 降价发券方案明细列表
 */
Ext.define('Foss.baseinfo.priceCoupon.PriceCouponDetailGridPanel', {
	extend: 'Ext.grid.Panel',
	title : '查询结果',//查询结果baseinfo.priceCoupon.i18n('foss.baseinfo.queryResults')
	frame: true,
	flex:1,
	autoScroll:true,
	isShow:false,//是否是查看
    sortableColumns:false,
    enableColumnHide:false,
    enableColumnMove:false,
	stripeRows : true, // 交替行效果
	selType : "rowmodel", // 选择类型设置为：行选择
	emptyText: '查询结果为空',//查询结果为空baseinfo.priceCoupon.i18n('foss.baseinfo.queryResultIsNull')
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
	priceCouponDeatilUpdateWindow : null,
	getPriceCouponDeatilUpdateWindow : function() {
		if (Ext.isEmpty(this.priceCouponDeatilUpdateWindow)) {
			this.priceCouponDeatilUpdateWindow = Ext.create('Foss.baseinfo.priceCoupon.PriceCouponDeatilUpdateWindow');
			this.priceCouponDeatilUpdateWindow.parent = this;
		}
		return this.priceCouponDeatilUpdateWindow;
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.columns = [{xtype: 'rownumberer',
			width:40,
			text : '序号'//序号baseinfo.priceCoupon.i18n('foss.baseinfo.sequence')
		},{
			text : '操作',//操作baseinfo.priceCoupon.i18n('foss.priceCoupon.operate')
			//dataIndex : 'id',
			hidden:config.isShow,
			xtype:'actioncolumn',
			align: 'center',
			width:80,
			items: [{
				iconCls: 'deppon_icons_edit',
                tooltip: '修改',//修改baseinfo.priceCoupon.i18n('foss.baseinfo.update')
				width:42,
                handler: function(grid,rowIndex,colIndex) {
                	//获取选中的数据
    				var record=grid.getStore().getAt(rowIndex);
                	var priceValuationId = record.get('priceValuationId');//计费规则ID
    				var params = {'priceCouponVo':{'priceCouponDto':{'priceValuationId':priceValuationId}}};
    				var successFun = function(json){
    					var updateWindow = me.getPriceCouponDeatilUpdateWindow();//获得修改窗口
    					updateWindow.priceCouponDto = json.priceCouponVo.priceCouponDto;//降价发券方案明细
    					updateWindow.show();//显示修改窗口
    				};
    				var failureFun = function(json){
    					if(Ext.isEmpty(json)){
    						baseinfo.showErrorMes('请求超时！');//请求超时baseinfo.priceCoupon.i18n('foss.baseinfo.requestOvertime')
    					}else{
    						baseinfo.showErrorMes(json.message);
    					}
    				};
    				var url = baseinfo.realPath('selectPriceValuation.action');
    				baseinfo.requestJsonAjax(url,params,successFun,failureFun);
                }
			},{
				iconCls: 'deppon_icons_delete',
                tooltip: '作废',//作废baseinfo.priceCoupon.i18n('foss.baseinfo.void')
				width:42,
                handler: function(grid, rowIndex, colIndex) {
            		//获取选中的数据
    				var record=grid.getStore().getAt(rowIndex);
            		baseinfo.showQuestionMes('是否要作废这个降价发券明细？',function(e){//是否要作废这个降价发券明细？baseinfo.priceCoupon.i18n('foss.priceCoupon.voidOrNotTheCouponDetail')
            			if(e=='yes'){//询问是否删除，是则发送请求
            				var priceValuationId = record.get('priceValuationId');//计费规则ID
            				var params = {'priceCouponVo':{'priceCouponDto':{'priceValuationId':priceValuationId}}};
            				var successFun = function(json){
            					baseinfo.showInfoMes(json.message);
            					me.getPagingToolbar().moveFirst();
            				};
            				var failureFun = function(json){
            					if(Ext.isEmpty(json)){
            						baseinfo.showErrorMes('请求超时！');//请求超时！baseinfo.priceCoupon.i18n('foss.baseinfo.requestOvertime')
            					}else{
            						baseinfo.showErrorMes(json.message);
            					}
            				};
            				var url = baseinfo.realPath('deleteCouponProgramItem.action');
            				baseinfo.requestJsonAjax(url,params,successFun,failureFun);
            			}
            		})
                }
			}]
		},{
			text : '出发',//出发baseinfo.priceCoupon.i18n('foss.priceCoupon.departure')
			dataIndex : 'deptOrgName'
		},{
			text : '到达',//到达baseinfo.priceCoupon.i18n('foss.priceCoupon.arrival')
			dataIndex : 'arrvOrgName'
		},{
			text : '产品类型',//产品类型baseinfo.priceCoupon.i18n('foss.priceCoupon.arrivalPattern')
			dataIndex : 'productName'
		},{
			text : '重量起点',//开始范围baseinfo.priceCoupon.i18n('foss.priceCoupon.beginRange')
			dataIndex : 'weightLeftRange'
		},{
			text : '重量终点',//结束范围baseinfo.priceCoupon.i18n('foss.priceCoupon.endRange')
			dataIndex : 'weightRightRange'
		},{
			text : '体积起点',//降价发券规则baseinfo.priceCoupon.i18n('foss.priceCoupon.priceCouponSchemeRegulation')
			dataIndex : 'volumeLeftRange',
		},{
			text : '体积终点',//产品baseinfo.priceCoupon.i18n('foss.priceCoupon.product')
			dataIndex : 'volumeRightRange'
		},{
			text : '返券系数',//降价发券系数baseinfo.priceCoupon.i18n('foss.priceCoupon.priceCouponParameter')
			dataIndex : 'couponRate'
		},{
			text : '是否接货',//最低减免baseinfo.priceCoupon.i18n('foss.priceCoupon.minReduction')
			dataIndex : 'isPickUp',
			renderer:function(value){
				if(value=='Y'){//'Y'表示激活
					return '是';//已激活baseinfo.priceCoupon.i18n('foss.priceCoupon.active')
				}else if(value=='N'){//'N'表示未激活
					return  '否';//未激活baseinfo.priceCoupon.i18n('foss.priceCoupon.inActive')
				}else{
					return '';
				}
			}
		},{
			text :'状态',//状态baseinfo.priceCoupon.i18n('foss.priceCoupon.status')
			dataIndex : 'active',
			width:50,
			renderer:function(value){
				if(value=='Y'){//'Y'表示激活
					return '已激活';//已激活baseinfo.priceCoupon.i18n('foss.priceCoupon.active')
				}else if(value=='N'){//'N'表示未激活
					return  '未激活';//未激活baseinfo.priceCoupon.i18n('foss.priceCoupon.inActive')
				}else{
					return '';
				}
			}
		}];
		me.store = Ext.create('Foss.baseinfo.priceCoupon.priceCouponDetailStore',{
			autoLoad : false,
			pageSize : 5,
			listeners: {
				beforeload: function(store, operation, eOpts){
					var queryForm = me.up().getQueryPriceCouponDetailForm();
					var marketId = me.up('window').marketingEventEntity.id;
					if(queryForm!=null){
						Ext.apply(operation,{
							params : {
								'priceCouponVo.priceCouponDto.deptOrgName':queryForm.getForm().findField('deptOrgName').getValue(),//出发
								'priceCouponVo.priceCouponDto.arrvOrgName':queryForm.getForm().findField('arrvOrgName').getValue(),//到达
								'priceCouponVo.priceCouponDto.active':queryForm.getForm().findField('active').getValue(),//状态
								'priceCouponVo.priceCouponDto.productCode':queryForm.getForm().findField('productCode').getValue(),//产品Code
								'priceCouponVo.priceCouponDto.marketId':marketId//降价发券方案主信息ID
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
 * 降价发券方案明细-修改(单独修改)
 */
Ext.define('Foss.baseinfo.priceCoupon.PriceCouponDeatilUpdateWindow',{
	extend : 'Ext.window.Window',
	title : '降价发券方案定义',//baseinfo.priceCoupon.i18n('foss.priceCoupon.priceCouponSchemeDefine')
	closable : true,
	modal : true,
	resizable:false,
	priceCouponDto:null,//明细
	parent:null,//(Foss.baseinfo.priceCoupon.PriceCouponDetailGridPanel)
	closeAction : 'hide',
	width :600,
	height :450,	
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	listeners:{
		beforehide:function(me){
			me.getPriceCouponDetailEditFormPanel().getForm().reset();
		},
		beforeshow:function(me){
			//产品类型多选公共选择器初始化
			me.priceCouponDto.productCode=null;
			
			me.getPriceCouponDetailEditFormPanel().getForm().loadRecord(new Foss.baseinfo.priceCoupon.PriceCouponDto(me.priceCouponDto));//设置值
			//封装产品类型多选公共选择器
			var combo2=me.getPriceCouponDetailEditFormPanel().getForm().findField('productItem');
			combo2.getStore().loadData(me.priceCouponDto.productEntityList);
			combo2.setValue(me.priceCouponDto.productValue);
		}
	},
	//维护降价发券信息FORM
	priceCouponDetailEditFormPanel:null,
	getPriceCouponDetailEditFormPanel:function(){
		if(Ext.isEmpty(this.priceCouponDetailEditFormPanel)){
    		this.priceCouponDetailEditFormPanel = Ext.create('Foss.baseinfo.priceCoupon.PriceCouponDetailEditFormPanel');
    		this.priceCouponDetailEditFormPanel.getDockedItems()[0].items.items[0].hide();
    	}
    	return this.priceCouponDetailEditFormPanel;
	},
	//修改降价发券明细
	commitPriceCouponDetailEdit:function(){
		var me = this;
		//获取修改的原始数据
		var priceCouponDto = new Foss.baseinfo.priceCoupon.PriceCouponDto(me.priceCouponDto);
		var form = me.getPriceCouponDetailEditFormPanel().getForm();
        if(form.isValid()){
        	form.updateRecord(priceCouponDto);
            var productCode = form.findField('productItem').getValue();
            priceCouponDto.set('productCode',productCode);
            //重量范围与体积范围规则
            if(priceCouponDto.get('weightLeftRange')>priceCouponDto.get('weightRightRange')){
            	baseinfo.showWoringMessage('重量起点不能大于重量终点！');//开始范围不能大于结束范围！baseinfo.priceCoupon.i18n('foss.priceCoupon.beginRangeNotBiggerEndRange')
            	return;
            }
            if(priceCouponDto.get('volumeLeftRange')>priceCouponDto.get('volumeRightRange')){
            	baseinfo.showWoringMessage('体积起点不能大于体积终点！');//开始范围不能大于结束范围！baseinfo.priceCoupon.i18n('foss.priceCoupon.beginRangeNotBiggerEndRange')
            	return;
            }
			var params = {'priceCouponVo':{'priceCouponDto':priceCouponDto.data}};
			var successFun = function(json){
				baseinfo.showInfoMes(json.message);
				me.close();
				me.parent.getPagingToolbar().moveFirst();
			};
			var failureFun = function(json){
				if(Ext.isEmpty(json)){
					baseinfo.showErrorMes('请求超时！');//baseinfo.priceCoupon.i18n('foss.baseinfo.requestOvertime')
				}else{
					baseinfo.showErrorMes(json.message);
				}
			};
			var url = baseinfo.realPath('updateCouponProgramItem.action');
			baseinfo.requestJsonAjax(url,params,successFun,failureFun);
        }		
	},
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [me.getPriceCouponDetailEditFormPanel()];
		me.fbar = [{
			text : '返回列表',//返回列表baseinfo.priceCoupon.i18n('foss.priceCoupon.returnList')
			handler :function(){
				me.close();
			} 
		},{
			text : '重置',//重置baseinfo.priceCoupon.i18n('foss.baseinfo.reset')
			handler :function(){
				me.getPriceCouponDetailEditFormPanel().getForm().loadRecord(new Foss.baseinfo.priceCoupon.PriceCouponDto(me.priceCouponDto));//设置值
			} 
		},{
			text : '保存',//保存baseinfo.priceCoupon.i18n('foss.baseinfo.save')
			margin:'0 0 0 185',
			cls:'yellow_button',
			handler :function(){
				me.commitPriceCouponDetailEdit();
			} 
		}];
		me.callParent([cfg]);
	}
});

/**
 * 修改弹窗-设置中止时间
 */
Ext.define('Foss.baseinfo.priceCoupon.PricingCouponEndTimeWindow',{
	extend : 'Ext.window.Window',
	title : '设置失效时间',//设置失效时间baseinfo.priceCoupon.i18n('foss.priceCoupon.setExpirationTime')
	closable : true,
	modal : true,
	resizable:false,
	closeAction : 'hide',
	parent:null,//(Foss.baseinfo.priceCoupon.PricingBasicValuationGridPanel)
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
			var minValue = me.selection.get('beginTime')>new Date(baseinfo.priceCoupon.tomorrowTime)?me.selection.get('beginTime'):new Date(baseinfo.priceCoupon.tomorrowTime);
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
    			fieldLabel: '失效日期',//失效日期baseinfo.priceCoupon.i18n('foss.priceCoupon.expirationTime')
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
        		baseinfo.showWoringMessage('终止日期要大于起始日期！');//终止日期要大于起始日期！baseinfo.priceCoupon.i18n('foss.priceCoupon.endDateBehindBeginDate')
    			return;
        	}
        	var params = {'priceCouponVo':{'marketingEventEntity':{'id':me.selection.get('id'),'endTime':new Date(time)}}};
        	var successFun = function(json){
    			baseinfo.showInfoMes(json.message);
    			me.parent.getPagingToolbar().moveFirst();
    			me.close();
    		};
    		var failureFun = function(json){
    			if(Ext.isEmpty(json)){
    				baseinfo.showErrorMes('请求超时！');//请求超时！baseinfo.priceCoupon.i18n('foss.baseinfo.requestOvertime')
    			}else{
    				baseinfo.showErrorMes(json.message);
    			}
    		};
    		var url = baseinfo.realPath('stopCouponProgram.action');
    		baseinfo.requestJsonAjax(url,params,successFun,failureFun);
    	}
    },
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [me.getEndTimeField()];
		me.fbar = [{
			text : '取消',//取消baseinfo.priceCoupon.i18n('foss.baseinfo.cancel')
			handler :function(){
				me.close();
			} 
		},{
			text : '确认',//确认baseinfo.priceCoupon.i18n('foss.baseinfo.sure')
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
Ext.define('Foss.baseinfo.priceCoupon.PriceCouponAreaStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.baseinfo.priceCoupon.AreaModel',
	autoLoad:false,//不需要自动加载
	pageSize:5,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url : baseinfo.realPath('searchRegionByCondition.action'),
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
Ext.define('Foss.baseinfo.priceCoupon.PriceCouponAreaGridPanel', {
	extend: 'Ext.grid.Panel',
	title : '区域',//区域baseinfo.priceCoupon.i18n('foss.priceCoupon.region')
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
			text : '序号'//序号baseinfo.priceCoupon.i18n('foss.baseinfo.sequence')
		},{
			text : '区域编码',//区域编码baseinfo.priceCoupon.i18n('foss.priceCoupon.regionCode')
			dataIndex : 'regionCode'
		},{
			text : '区域名称',//区域名称baseinfo.priceCoupon.i18n('foss.priceCoupon.regionName')
			dataIndex : 'regionName'
		}];
		me.store = Ext.create('Foss.baseinfo.priceCoupon.PriceCouponAreaStore',{
			autoLoad:false,//不需要自动加载
			listeners: {
				beforeload: function(store, operation, eOpts){
					var queryForm = me.up('window').getPriceCouponSearchRegionForm();
					if(queryForm!=null){
						Ext.apply(operation,{
							params : {
								'regionVo.regionEntity.regionName' : "%"+queryForm.getForm().findField('regionName').getValue()+"%",//区域名称
								'regionVo.regionEntity.regionCode' : queryForm.getForm().findField('regionCode').getValue(),//区域编号
								'regionVo.regionEntity.active' : 'Y',//只查询激活的
								//查询的只是价格区域
								'regionVo.regionEntity.regionNature':baseinfo.priceCoupon.PRICING_REGION
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
Ext.define('Foss.baseinfo.priceCoupon.PriceCouponRegionGridShowWindow',{
	extend : 'Ext.window.Window',
	title : '查询区域',//查询区域baseinfo.priceCoupon.i18n('foss.priceCoupon.queryRegion')
	closable : true,
	parent:null,//(Foss.baseinfo.priceCoupon.StartGrid,Foss.baseinfo.priceCoupon.ArriveGrid)
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
			me.getPriceCouponSearchRegionForm().getForm().reset();
			me.textField = null;
			
		},
		beforeshow:function(me){
			me.getPriceCouponRegionGridPanel().getPagingToolbar().moveFirst();//确保查询第一页的数据
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
		var selections = me.getPriceCouponRegionGridPanel().getSelectionModel().getSelection();//获取选择的数据
		if(selections.length==0){
			baseinfo.showWoringMessage('请选择一个区域！');//请选择一个区域！baseinfo.priceCoupon.i18n('foss.priceCoupon.selectOneRegion')
			return;
		}
		var datas = new Array();
		if(me.type==baseinfo.priceCoupon.priceCouponStart){
			for(var i = 0;i<selections.length;i++){
				var isHave = false;//是否已经存在
				me.parent.getStore().each(function(record){
					if(record.get('deptOrgId')==selections[i].get('id')){
						isHave = true;
					}
				});
				if(!isHave){//不存在则新增
					var data = {'deptOrgId':selections[i].get('id'),'name':selections[i].get('regionName'),'deptOrgCode':selections[i].get('regionCode'),'deptOrgTypeCode':baseinfo.priceCoupon.priceCouponDetailTypeRegion};
					datas.push(data);
				}
			}
			var oldData = me.parent.getStore().getAt(0)//清掉不是一类的数据
			if(!Ext.isEmpty(oldData)){
				if(oldData.get('deptOrgTypeCode')!=baseinfo.priceCoupon.priceCouponDetailTypeRegion){
					me.parent.getStore().removeAll();
				}
			}
		}else if(me.type==baseinfo.priceCoupon.priceCouponArrive){
			for(var i = 0;i<selections.length;i++){
				var isHave = false;//是否已经存在
				me.parent.getStore().each(function(record){
					if(record.get('arrvOrgId')==selections[i].get('id')){
						isHave = true;
					}
				});
				if(!isHave){//不存在则新增
					var data = {'arrvOrgId':selections[i].get('id'),'name':selections[i].get('regionName'),'arrvOrgCode':selections[i].get('regionCode'),'arrvOrgTypeCode':baseinfo.priceCoupon.priceCouponDetailTypeRegion};
					datas.push(data);
				}
			}
			var oldData = me.parent.getStore().getAt(0)//清掉不是一类的数据
			if(!Ext.isEmpty(oldData)){
				if(oldData.get('arrvOrgTypeCode')!=baseinfo.priceCoupon.priceCouponDetailTypeRegion){
					me.parent.getStore().removeAll();
				}
			}
		}
		me.parent.getStore().add(datas);
		me.close();
	},
	//查询的FORM
	priceCouponSearchRegionForm:null,
	getPriceCouponSearchRegionForm:function(){
		if(Ext.isEmpty(this.priceCouponSearchRegionForm)){
    		this.priceCouponSearchRegionForm = Ext.create('Foss.baseinfo.priceCoupon.PriceCouponSearchRegionForm');
    	}
		return this.priceCouponSearchRegionForm;
	},
	//区域的GRID
	priceCouponRegionGridPanel:null,
	getPriceCouponRegionGridPanel:function(){
		if(Ext.isEmpty(this.priceCouponRegionGridPanel)){
    		this.priceCouponRegionGridPanel = Ext.create('Foss.baseinfo.priceCoupon.PriceCouponAreaGridPanel');
    	}
		return this.priceCouponRegionGridPanel;
	}, 
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [me.getPriceCouponSearchRegionForm(), me.getPriceCouponRegionGridPanel()];
		me.fbar = [{
			text : '返回列表',//返回列表baseinfo.priceCoupon.i18n('foss.priceCoupon.returnList')
			handler :function(){
				me.close();
			} 
		},{
			text : '确定',//确定baseinfo.priceCoupon.i18n('foss.baseinfo.sure')
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
Ext.define('Foss.baseinfo.priceCoupon.PriceCouponSearchRegionForm', {
	extend : 'Ext.form.Panel',
	title: '查询条件',//查询条件baseinfo.priceCoupon.i18n('foss.baseinfo.queryCondition')
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
			fieldLabel:'区域名称',//区域名称baseinfo.priceCoupon.i18n('foss.priceCoupon.regionName')
			name:'regionName'
		},{
			xtype:'textfield',
			fieldLabel:'区域编码',//区域编码baseinfo.priceCoupon.i18n('foss.priceCoupon.queryRegion')
			name:'regionCode'
		},{
			xtype : 'container',
			margin : '0 0 0 0',
			columnWidth : .2,
			items : [{
				xtype : 'button', 
				width:70,
				cls:'yellow_button',
				text : '查询按钮',//查询按钮baseinfo.priceCoupon.i18n('foss.baseinfo.query')
				handler : function() {
					if(me.getForm().isValid()){
						var grid  = me.up('window').getPriceCouponRegionGridPanel();
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
Ext.define('Foss.baseinfo.priceCoupon.PriceCouponCityStore', {
	extend : 'Ext.data.Store',
	fields : ['id','name','code'],
	autoLoad:false,//不需要自动加载
	pageSize:5,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url : baseinfo.realPath('searchCityByName.action'),
		reader : {
			type : 'json',
			root : 'priceCouponVo.administrativeRegionsEntityList',
			totalProperty : 'totalCount'
		}
	}
});
/**
 * 城市
 */
Ext.define('Foss.baseinfo.priceCoupon.PriceCouponCityGridPanel', {
	extend: 'Ext.grid.Panel',
	title : '城市',//城市baseinfo.priceCoupon.i18n('foss.priceCoupon.city')
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
			text : '序号'//序号baseinfo.priceCoupon.i18n('foss.baseinfo.noSequence')
		},{
			text : '城市名称',//城市名称baseinfo.priceCoupon.i18n('foss.priceCoupon.cityName')
			dataIndex : 'name'
		},{
			text : '城市编码',//城市编码baseinfo.priceCoupon.i18n('foss.priceCoupon.cityCode')
			dataIndex : 'code'
		}];
		me.store = Ext.create('Foss.baseinfo.priceCoupon.PriceCouponCityStore',{
			autoLoad:false,//不需要自动加载
			listeners: {
				beforeload: function(store, operation, eOpts){
					var queryForm = me.up('window').getPriceCouponSearchCityForm();
					if(queryForm!=null){
						Ext.apply(operation,{
							params : {
								'priceCouponVo.administrativeRegionsEntity.name' : queryForm.getForm().findField('name').getValue(),//城市名称
								 //查询的只是城市
								'priceCouponVo.administrativeRegionsEntity.degree':baseinfo.priceCoupon.Pricing_City
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
Ext.define('Foss.baseinfo.priceCoupon.PriceCouponCityGridShowWindow',{
	extend : 'Ext.window.Window',
	parent:null,//(Foss.baseinfo.priceCoupon.StartGrid,Foss.baseinfo.priceCoupon.ArriveGrid)
	title : '查询出发地/目的地',//查询出发地/目的地baseinfo.priceCoupon.i18n('foss.priceCoupon.queryDepartureArrival')
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
			me.getPriceCouponSearchCityForm().getForm().reset();
			me.textField = null;
			
		},
		beforeshow:function(me){
			me.getPriceCouponCityGridPanel().getPagingToolbar().moveFirst();//确保查询第一页的数据
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
		var selections = me.getPriceCouponCityGridPanel().getSelectionModel().getSelection();//获取选择的数据
		if(selections.length==0){
			baseinfo.showWoringMessage('请选择一个城市！');//请选择一个城市！baseinfo.priceCoupon.i18n('foss.priceCoupon.selectOneCity')
			return;
		}
		var datas = new Array();
		if(me.type==baseinfo.priceCoupon.priceCouponStart){
			for(var i = 0;i<selections.length;i++){
				var isHave = false;//是否已经存在
				me.parent.getStore().each(function(record){
					if(record.get('deptOrgId')==selections[i].get('id')){
						isHave = true;
					}
				});
				if(!isHave){//不存在则新增
					var data = {'deptOrgId':selections[i].get('id'),'name':selections[i].get('name'),'deptOrgCode':selections[i].get('code'),'deptOrgTypeCode':baseinfo.priceCoupon.priceCouponDetailTypeCity};
					datas.push(data);
				}
			}
			var oldData = me.parent.getStore().getAt(0)//清掉不是一类的数据
			if(!Ext.isEmpty(oldData)){
				if(oldData.get('deptOrgTypeCode')!=baseinfo.priceCoupon.priceCouponDetailTypeCity){
					me.parent.getStore().removeAll();
				}
			}
		}else if(me.type==baseinfo.priceCoupon.priceCouponArrive){
			for(var i = 0;i<selections.length;i++){
				var isHave = false;//是否已经存在
				me.parent.getStore().each(function(record){
					if(record.get('arrvOrgId')==selections[i].get('id')){
						isHave = true;
					}
				});
				if(!isHave){//不存在则新增
					var data = {'arrvOrgId':selections[i].get('id'),'name':selections[i].get('name'),'arrvOrgCode':selections[i].get('code'),'arrvOrgTypeCode':baseinfo.priceCoupon.priceCouponDetailTypeCity};
					datas.push(data);
				}
			}
			var oldData = me.parent.getStore().getAt(0)//清掉不是一类的数据
			if(!Ext.isEmpty(oldData)){
				if(oldData.get('arrvOrgTypeCode')!=baseinfo.priceCoupon.priceCouponDetailTypeCity){
					me.parent.getStore().removeAll();
				}
			}
		}
		me.parent.getStore().add(datas);
		me.close();
	},
	//查询的FORM
	priceCouponSearchCityForm:null,
	getPriceCouponSearchCityForm:function(){
		if(Ext.isEmpty(this.priceCouponSearchCityForm)){
    		this.priceCouponSearchCityForm = Ext.create('Foss.baseinfo.priceCoupon.PriceCouponSearchCityForm');
    	}
		return this.priceCouponSearchCityForm;
	},
	//区域的GRID
	priceCouponCityGridPanel:null,
	getPriceCouponCityGridPanel:function(){
		if(Ext.isEmpty(this.priceCouponCityGridPanel)){
    		this.priceCouponCityGridPanel = Ext.create('Foss.baseinfo.priceCoupon.PriceCouponCityGridPanel');
    	}
		return this.priceCouponCityGridPanel;
	}, 
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [me.getPriceCouponSearchCityForm(), me.getPriceCouponCityGridPanel()];
		me.fbar = [{
			text : '返回列表',//返回列表baseinfo.priceCoupon.i18n('foss.priceCoupon.returnList')
			handler :function(){
				me.close();
			} 
		},{
			text : '确定',//确定baseinfo.priceCoupon.i18n('foss.baseinfo.sure')
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
Ext.define('Foss.baseinfo.priceCoupon.PriceCouponSearchCityForm', {
	extend : 'Ext.form.Panel',
	title: '城市',//城市baseinfo.priceCoupon.i18n('foss.priceCoupon.city')
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
			fieldLabel:'城市名称',//城市名称baseinfo.priceCoupon.i18n('foss.priceCoupon.cityName')
			name:'name'
		},{
			xtype : 'container',
			margin : '0 0 0 0',
			columnWidth : .2,
			items : [{
				xtype : 'button', 
				width:70,
				cls:'yellow_button',
				text : '查询',//查询baseinfo.priceCoupon.i18n('foss.baseinfo.query')
				handler : function() {
					if(me.getForm().isValid()){
						var grid  = me.up('window').getPriceCouponCityGridPanel();
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
Ext.define('Foss.baseinfo.priceCoupon.ImmediatelyStopEndTimeWindow',{
		extend: 'Ext.window.Window',
		title: '立即中止方案',//"立即中止方案",baseinfo.priceCoupon.i18n('foss.priceCoupon.stopSchemeQuick')
		width:380,
		height:152,
		closeAction: 'hide' ,
		stopFormPanel:null,
		marketingEventEntity:null,
		parent:null,
		getStopFormPanel : function(){
	    	if(Ext.isEmpty(this.stopFormPanel)){
	    		this.stopFormPanel = Ext.create('Foss.baseinfo.priceCoupon.ImmediatelyStopFormPanel');
	    	}
	    	return this.stopFormPanel;
	    },
	    listeners:{
	    	beforeshow:function(me){
	    		var showbeginTime = Ext.Date.format(new Date(me.marketingEventEntity.beginTime), 'Y-m-d H:i:s');
	    		var showendTime = 	Ext.Date.format(new Date(me.marketingEventEntity.endTime), 'Y-m-d H:i:s');
	    		var value = '<p style="color:red">原方案生效日期为【'
				  +showbeginTime+'】截止日期为【'
				  +showendTime+'】，您是否立即中止该方案？</p>';
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
Ext.define('Foss.baseinfo.priceCoupon.ImmediatelyStopFormPanel',{
	extend : 'Ext.form.Panel',
	layout:'column',
	stop:function(){
		var me = this;
    	var form = me.getForm();
    	if(form.isValid()){
    		var marketingEventEntity = new Foss.baseinfo.priceCoupon.MarketingEventEntity();
			form.updateRecord(marketingEventEntity);
			marketingEventEntity.set('endTime',Ext.Date.parse(form.findField('endTime').getValue(), 'Y-m-d H:i:s'));
			var id = me.up('window').marketingEventEntity.id;
			marketingEventEntity.set('id',id);
    		var params = {'priceCouponVo':{'marketingSchemeEntity':marketingEventEntity.data}};
    		var url = baseinfo.realPath('terminateImmediatelyCouponProgramPrice.action');
    		var successFun = function(json){
    			baseinfo.showInfoMes(json.message);
    			me.up('window').hide();
    			me.up('window').parent.getPagingToolbar().moveFirst();
    	    };
    	    var failureFun = function(json){
    	    	if(Ext.isEmpty(json)){
    				baseinfo.showErrorMes('请求超时！');//请求超时！baseinfo.pricePlan.i18n('foss.baseinfo.requestOvertime')
    			}else{
    				baseinfo.showErrorMes(json.message);
    			}
    	    };
    		baseinfo.requestJsonAjax(url,params,successFun,failureFun);//发送AJAX请求
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
				fieldLabel : '中止日期',//'中止日期'baseinfo.priceCoupon.i18n('foss.priceCoupon.stopDate')
				name : 'endTime',
				xtype : 'datetimefield_date97',
				editable:false,
				time : true,
				id : 'Foss_priceCoupon_stopEndTime_ID',
				dateConfig: {
					el : 'Foss_priceCoupon_stopEndTime_ID-inputEl'
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
				text : '确认',//"确认"baseinfo.priceCoupon.i18n('foss.baseinfo.sure')
				handler : function() {
					me.stop();
				}
			},{
				xtype : 'button', 
				width:70,
				columnWidth:.15,
				text : '取消',//"取消"baseinfo.priceCoupon.i18n('foss.baseinfo.cancel')
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
Ext.define('Foss.baseinfo.priceCoupon.ImmediatelyActiveTimeWindow',{
		extend: 'Ext.window.Window',
		title: '立即激活方案',//"立即激活方案"baseinfo.priceCoupon.i18n('foss.priceCoupon.activeSchemeQuick')
		width:380,
		height:152,
		marketingEventEntity:null,
		closeAction: 'hide' ,
		immediatelyActiveFormPanel:null,
		getImmediatelyActiveFormPanel : function(){
	    	if(Ext.isEmpty(this.immediatelyActiveFormPanel)){
	    		this.immediatelyActiveFormPanel = Ext.create('Foss.baseinfo.priceCoupon.ImmediatelyActiveFormPanel');
	    	}
	    	return this.immediatelyActiveFormPanel;
	    },
	    listeners:{
	    	beforeshow:function(me){
	    		var showbeginTime = Ext.Date.format(new Date(me.marketingEventEntity.beginTime), 'Y-m-d H:i:s');
	    		var showendTime = 	Ext.Date.format(new Date(me.marketingEventEntity.endTime), 'Y-m-d H:i:s');
	    		var value = '<p style="color:red">原方案生效日期为【'
				  +showbeginTime+'】截止日期为【'
				  +showendTime+'】，您是否立即激活该方案？</p>';
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
Ext.define('Foss.baseinfo.priceCoupon.ImmediatelyActiveFormPanel',{
	extend : 'Ext.form.Panel',
	layout:'column',
	activetion:function(){
		var me = this;
    	var form = me.getForm();
    	if(form.isValid()){
			var marketingEventEntity = new Foss.baseinfo.priceCoupon.MarketingEventEntity();
			form.updateRecord(marketingEventEntity);
			marketingEventEntity.set('beginTime',Ext.Date.parse(form.findField('beginTime').getValue(), 'Y-m-d H:i:s'));
			var id = me.up('window').marketingEventEntity.id;
			marketingEventEntity.set('id',id);
    		var params = {'priceCouponVo':{'marketingSchemeEntity':marketingEventEntity.data}};
    		var url = baseinfo.realPath('activateImmediatelyCouponProgramPrice.action');
    		var successFun = function(json){
    			baseinfo.showInfoMes(json.message);
    			me.up('window').hide();
				me.up('window').parent.getPagingToolbar().moveFirst();  			
    	    };
    	    var failureFun = function(json){
    	    	if(Ext.isEmpty(json)){
    				baseinfo.showErrorMes('请求超时！');//请求超时！baseinfo.priceCoupon.i18n('foss.baseinfo.requestOvertime')
    			}else{
    				baseinfo.showErrorMes(json.message);
    			}
    	    };
    		baseinfo.requestJsonAjax(url,params,successFun,failureFun);//发送AJAX请求
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
				fieldLabel:'生效日期',//'生效日期'baseinfo.priceCoupon.i18n('foss.priceCoupon.effectiveDate')
				name : 'beginTime',
				xtype : 'datetimefield_date97',
				editable:false,
				time : true,
				allowBlank:false,
				id : 'Foss_priceCoupon_activeEndTime_ID',
				dateConfig: {
					el : 'Foss_priceCoupon_activeEndTime_ID-inputEl'
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
				text : '确认',//,"确认"baseinfo.priceCoupon.i18n('foss.baseinfo.sure')
				handler : function() {
					me.activetion();
				}
			},{
				xtype : 'button', 
				width:70,
				columnWidth:.15,
				text : '取消',//"取消"baseinfo.priceCoupon.i18n('foss.baseinfo.cancel')
				handler : function() {
					me.up('window').hide();
				}
			}];
        this.callParent(cfg);
    }
});

/**
 * 导入窗口
 */
Ext.define('Foss.baseinfo.priceCoupon.ImportWin',{
	extend:'Ext.window.Window',
	title : '导入明细',
	closable : true,
	modal : true,
	resizable:false,
	marketingEventEntity:null,
	parent:null,//父元素
	closeAction : 'hide',//关闭动作为hide，默认为destroy
	layout : 'auto',
	bodyCls: 'autoHeight',
	width :550,
	listeners:{
		'beforehide':function(me){
			me.getImprotForm().getForm().reset();
		}
	},
	improtForm:null,
	getImprotForm:function(){
		if(this.improtForm ==null){
			this.improtForm= Ext.create('Foss.baseinfo.priceCoupon.ImportForm');
		}
		return this.improtForm;
	},
	constructor:function(config){
		var me=this,cfg=Ext.apply({},config);
		me.items=[me.getImprotForm()];
		me.callParent([cfg]);
	}
});
//导入Form
Ext.define('Foss.baseinfo.priceCoupon.ImportForm',{
	extend:'Ext.form.Panel',
	layout:'column',	
	frame:true,
	title : '导入明细信息',
	defaultType: 'textfield',	
	defaults: {
		margin:'0 5 5 5',
		anchor: '99%',
		labelWidth:100
	},
	bodyCls: 'autoHeight',
	standardSubmit:true,
	constructor:function(config){
		var me =this,cfg =Ext.apply({},config);
		me.items=[{
			 xtype: 'filefield',
		     name: 'uploadFile',
		     readOnly:false,
		     buttonOnly:false,
		     fieldLabel:'导入文件',//'导入文件',
		     msgTarget: 'side',
		     cls:'uploadFile',
		     allowBlank: false,			        
		     buttonText: '浏览',//'浏览',
		     columnWidth:.85
		},{
			xtype : 'button',
			columnWidth:.15,
			height:25,
			margin:3,
			cls:'uploadFile',
			text: '清除',//'清除',
			handler: function() {
				this.up('form').getForm().findField('uploadFile').reset();						
			}
		}];
		me.fbar=[{
			text:'取消',//取消
			handler:function(){
				me.up('window').close();
			}
		},{
			text:'导入',//导入
			handler:function(){
				if(me.getForm().isValid()){
					//加上罩
					var myMask = new Ext.LoadMask(me,  {msg:'正在导入，请稍候...'});//"正在导入，请稍候..."
		 			myMask.show();
		 			me.getForm().submit({
		 				url:baseinfo.realPath('importDetail.action'),
		 				success:function(form, action){
		 					myMask.hide();
					        var json =action.result;
					        baseinfo.showInfoMes('导入成功'+json.priceCouponVo.importTotal+'条！');
					        if(!Ext.isEmpty(json.message)){
//					        	baseinfo.showInfoMes(json.message);
					        	return;
					        }
//					    	me.up().close();
		 				},
		 				failure:function(form, action){
		 					myMash.hide();
		 					var json =action.result;
		 					baseinfo.showInfoMes(json.message);
		 				},
		 				exception:function(form, action){
		 					myMash.hide();
		 					var json =action.result;
		 					baseinfo.showInfoMes(json.message);
		 				}
		 			});
				}
			}
		}];
		me.callParent([cfg]);
	}
});