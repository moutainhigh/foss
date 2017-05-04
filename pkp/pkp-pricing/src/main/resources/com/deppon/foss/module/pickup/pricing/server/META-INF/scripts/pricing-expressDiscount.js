/**
 * 快递客户折扣方案明细
 * 
 */

//转换long类型为日期(在model中会用到)
pricing.changeLongToDate = function(value){
	if(value!=null){
		var date = new Date(value);
		return date;
	}else{
		return null;
	}
};

pricing.expressDiscount.showWoringMessage = function(message,fun) {
	var len = message.length;
	Ext.Msg.show({
	    title:'FOSS提醒您:',//pricing.util.i18n('i18n.pricing-util.fossAlert'), 平台会对具体的大模块.小模块去映射i18n资源文件，util.js文件属于公共文件故不存在模块概念不被解析到，暂时先用中文
	    msg:message,
	    //cls:'mesbox',
	    width:110+len*15,
	    msg:'<div id="message">'+message+'</div>',
	    buttons: Ext.Msg.OK,
	    icon: Ext.MessageBox.WARNING,
	    callback:function(e){
	     if(!Ext.isEmpty(fun)){
	     if(e=='ok' || e=='cancel'){
	     fun();
	     }
	     }
	    }
	});
	};
//JSON格式的Ajax请求服务
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
pricing.getStore = function(storeId, model, fields, data) {
	var store = null;
	if (!Ext.isEmpty(storeId)) {
		store = Ext.data.StoreManager.lookup(storeId);
	}
	if (Ext.isEmpty(data)) {
		data = [];
	}
	if (!Ext.isEmpty(model)) {
		if (Ext.isEmpty(store)) {
			store = Ext.create('Ext.data.Store', {
				storeId : storeId,
				model : model,
				data : data
			});
		}
	}
	if (!Ext.isEmpty(fields)) {
		if (Ext.isEmpty(store)) {
			store = Ext.create('Ext.data.Store', {
				storeId : storeId,
				fields : fields,
				data : data
			});
		}
	}
	return store;
};

//基础产品列表
pricing.expressDiscount.productEntityList;
//货物类型列表
pricing.expressDiscount.goodTypeList ;
//获取服务当前时间
pricing.expressDiscount.tomorrowTime ;
//渠道
pricing.expressDiscount.channel = 'PKP_PRICE_CHANNEL';
//快递
pricing.PACKAGE = 'PACKAGE';
//3.60
pricing.PACKAGE_RCP = 'RCP';
//电商尊享
pricing.PACKAGE_EPEP = 'EPEP';
//查询基础产品列表
pricing.searchProductEntityList = function(){
	Ext.Ajax.request({
		url:pricing.realPath('getAllLevels3ExpressProductCode.action'),//查询基础产品
		async:false,
		success:function(response){
			var result = Ext.decode(response.responseText);
			pricing.expressDiscount.productEntityList = result.productVo.productEntityList;
//			var productEntitys = pricing.expressDiscount.productEntityList;
//			var arrays = new Array();
//			Ext.Array.each(productEntitys, function(productEntity, index, countriesItSelf) {
//				//过滤出标准快递和3.60特惠件(三级产品)
//				if(productEntity.code == pricing.PACKAGE || productEntity.code == pricing.PACKAGE_RCP){
//					arrays.push(productEntity);
//				}
//			});
//			pricing.expressDiscount.productEntityList = arrays;			
		},
		failure:function(response){
			var result = Ext.decode(response.responseText);
			if(Ext.isEmpty(result)){
				pricing.showErrorMes(pricing.expressDiscount.i18n('i18n.pricingRegion.requestTimeOut'));
			}else{
				pricing.showErrorMes(result.message);
			}
		},
		exception:function(response){
			var result = Ext.decode(response.responseText);
			if(Ext.isEmpty(result)){
				pricing.showErrorMes(pricing.expressDiscount.i18n('i18n.pricingRegion.requestTimeOut'));
			}else{
				pricing.showErrorMes(result.message);
			}
		}
	});
};
//查询货物类型列表
pricing.searchGoodTypeList = function(){
	Ext.Ajax.request({
		url:pricing.realPath('findGoodsTypeByCondiction.action'),//查询基础产品
		async:false,
		success:function(response){
			var result = Ext.decode(response.responseText);
			pricing.expressDiscount.goodTypeList = result.pricingValuationVo.goodsTypeEntityList;
		},
		failure:function(response){
			var result = Ext.decode(response.responseText);
			if(Ext.isEmpty(result)){
				pricing.showErrorMes(pricing.expressDiscount.i18n('i18n.pricingRegion.requestTimeOut'));
			}else{
				pricing.showErrorMes(result.message);
			}
		},
		exception:function(response){
			var result = Ext.decode(response.responseText);
			if(Ext.isEmpty(result)){
				pricing.showErrorMes(pricing.expressDiscount.i18n('i18n.pricingRegion.requestTimeOut'));
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
			pricing.expressDiscount.tomorrowTime = today.setDate(today.getDate()+1);
		},
		failure:function(response){
			var result = Ext.decode(response.responseText);
			if(Ext.isEmpty(result)){
				pricing.showErrorMes('请求超时！');
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
//基础信息定义结束  ====   end

//---------------------------定义快递折扣方案中所需要的MODEL-----------------------------------------------------------------------
//1.快递折扣主方案信息MODEL
Ext.define('Foss.pricing.expressDiscount.expressDiscountEntity', {
	extend : 'Ext.data.Model',
	fields : [ 
	          {
		name : 'name', // 方案名称
		type : 'string'
	},{
		name : 'planType',// 方案类型
		type : 'string'
	},{
		name : 'channelSource', // 渠道来源
		type : 'string'
	},{
		name : 'beginTime', //开始时间
		type : 'date',
		dafaultValue:null,
		convert:pricing.changeLongToDate
	},{
		name : 'endTime', // 结束时间
		type : 'date',
		dafaultValue:null,
		convert:pricing.changeLongToDate
	},{
		name : 'active',  //状态
		type : 'string'
	},{
		name : 'remark', //描述
		type : 'string'
	},{
		name : 'versionNo', //版本
		type : 'long'
	},{
		name : 'createTime',  //创建方案时间
		type : 'date',
		dafaultValue:null,
		convert:pricing.changeLongToDate
	},{
		name : 'modifyTime', //修改方案时间
		type : 'date',
		dafaultValue:null,
		convert:pricing.changeLongToDate
	},{
		name : 'createUserCode',//创建人编码
		type : 'string'
	},{
		name : 'modifyUserCode',//修改人编码
		type : 'string'
	},{
		name : 'modifyUserName',//修改人编码
		type : 'string'
	},{
		name : 'createOrgCode',//创建部门编码
		type : 'string'
	},{
		name : 'modifyOrgCode', //修改部门编码
		type : 'string'
	},{
		name : 'customerName', //客户名称
		type : 'string'
	},{
		name : 'customerCode', //客户编码
		type : 'string'
	},{
		name:'billTime', 	//业务日期
		type : 'date',
		dafaultValue:null,
		convert:pricing.changeLongToDate
	},{
		name:'currentUsedVersion',
		type:'string'
	},{
		name:'weekNames',
		type:'string'
	}]
});

//2.快递折扣方案明细MODEL
Ext.define('Foss.pricing.expressDiscount.ExpressDiscountPlanDetailEntity', {
	extend : 'Ext.data.Model',
	fields : [ 
	          {
		name : 'expressDiscountPlanId',//折扣主方案ID
		type : 'string'
	},{
		name : 'goodsTypeCode',//货物类型CODE
		type : 'string'
	},{
		name : 'goodsTypeId',//货物类型ID
		type : 'string'
	},{
		name : 'productCode',//产品类型
		type : 'string'
	},{
		name : 'startRegionCode',//出发区域CODE
		type : 'string'
	},{
		name : 'arriveRegionCode', //到达区域CODE
		type : 'string'
	},{
		name : 'pricingEntryCode', //折扣费用类型
		type : 'string'
	},{
		name : 'versionNo',//版本
		type : 'long'
	},{
		name : 'active',//状态
		type : 'string'
	},
	{
		name : 'discountRule',//折扣规则
		type : 'string'
	},{
		name : 'currencyCode',//币种
		type : 'string'
	},{
		name : 'createTime',//创建时间
		type : 'date',
		dafaultValue:null,
		convert:pricing.changeLongToDate
	},{
		name : 'modifyTime',//修改时间
		type : 'date',
		dafaultValue:null,
		convert:pricing.changeLongToDate
	},{
		name : 'firstDiscountRate',//首重折扣率
		defaultValue : null
	},{
		name : 'renewalDiscountRate',//续重折扣率
		defaultValue : null
	},{
		name : 'leftRange',//开始
		defaultValue : null
	},{
		name : 'rightRange',//结束
		defaultValue : null
	},{
		name : 'createUserCode',//修改部门
		type : 'string'
	},{
		name : 'modifyUserCode',//修改部门
		type : 'string'
	},{
		name : 'createOrgCode',//修改部门
		type : 'string'
	},{
		name : 'modifyOrgCode',//修改部门
		type : 'string'
	},{
		name : 'continueHeavyLowestRate',
		defaultValue : null
	}]
});


/***
 * 
 * 快递折扣明细查询实体
 * Foss.pricing.expressDiscount.ExpressDiscountDto
 * Foss.pricing.expressDiscount.ExpressDiscountDto
 ***/
Ext.define('Foss.pricing.expressDiscount.ExpressDiscountDto',{
	extend:'Ext.data.Model',
	fields:[{
		name : 'expressDiscountPlanId',//快递折扣方案ID
        type : 'string'
	},{
		name : 'id',//明细信息ID
        type : 'string'
	},{
		name : 'versionNo',//versionNo
        type : 'long'
	},{
		name : 'createTime',// 创建时间
		type : 'date',
		dafaultValue:null,
		convert:pricing.changeLongToDate
	},{
		name : 'modifyTime',// 修改时间
		type : 'date',
		dafaultValue:null,
		convert:pricing.changeLongToDate
	},{
		name : 'createUserCode',//创建人
        type : 'string'
	},{
		name : 'modifyUserCode',// 修改人
        type : 'string'
	},{
		name : 'createOrgCode',// 创建部门
        type : 'string'
	},{
		name : 'modifyOrgCode',// 修改部门
        type : 'string'
	},{
		name : 'goodsTypeCode',// 货物类型CODE
        type : 'string'
	},{
		name : 'goodsTypeId',//货物类型ID
        type : 'string'
	},{
		name : 'goodsTypeName',//货物类型名字
        type : 'string'
	},{
		name : 'productCode',//产品类型
        type : 'string'
	},{
		name : 'productName',//产品名称
        type : 'string'
	},{
		name : 'startRegionCode',//出发区域CODE
        type : 'string'
	},{
		name : 'startRegionName',	//出发区域名称
        type : 'string'
	},{
		name : 'arriveRegionCode',	//到达区域CODE
        type : 'string'
	},{
		name : 'arriveRegionName',	//到达区域名称
        type : 'string'
	},{
		name : 'pricingEntryCode',	//折扣费用类型
        type : 'string'
	},{
		name : 'discountRule',	//折扣规则
        type : 'string'
	},{
		name : 'discountRuleName',	//折扣规则名称
        type : 'string'
	},{
		name : 'currencyCode',	//币种
        type : 'string'
	},{
		name : 'firstDiscountRate',	//首重折扣
		defaultValue : null
	},{
		name : 'renewalDiscountRate',	//续重折扣
		defaultValue : null
	},{
		name : 'leftRange',			//开始范围
		defaultValue : null
	},{
		name : 'rightRange',	//结束范围
		defaultValue : null
	},{
		name : 'continueHeavyLowestRate',
		defaultValue : null
	}]
});


//3.基础产品明细MODEL
Ext.define('Foss.pricing.expressDiscount.ProductEntity', {
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
//4.货物类型MODEL
Ext.define('Foss.pricing.expressDiscount.GoodsTypeEntity', {
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

//-----------------------定义Store------------------------

//1.快递折扣方案明细Store ||
Ext.define('Foss.pricing.expressDiscount.expressDiscountDetailStore',{
	extend:'Ext.data.Store',
	model:'Foss.pricing.expressDiscount.ExpressDiscountDto',//快递折扣主方案Model
	pageSize:5,
	proxy:{
		type:'ajax',
		actionMethods:'POST',
		url:pricing.realPath('queryExpressDiscountPlanDetailList.action'),
		reader:{
			type:'json',
			root:'expressDiscountPlanVo.expressDiscountDtoList', //快递折扣方案明细
			totalProperty:'totalCount'
		}
	}
});
//2.快递折扣主方案明细Store
Ext.define('Foss.pricing.expressDiscount.expressDiscountPlanStore',{
	extend:'Ext.data.Store',
	model:'Foss.pricing.expressDiscount.expressDiscountEntity',//快递折扣主方案Model
	pageSize:20,
	proxy:{
		type:'ajax',
		actionMethods:'POST',
		url:pricing.realPath('queryExpressDiscountPlanList.action'),
		reader:{
			type:'json',
			root:'expressDiscountPlanVo.expressDiscountEntityList',//快递主方案
			totalProperty:'totalCount'
		}
	}
});
//3
Ext.define('Foss.pricing.expressPricePlan.ExpressPricePlanDetailFormProductStore', {
	extend : 'Ext.data.Store',
	fields : [{
			name : 'id',
			type : 'string'
		}, {
			name : 'code',
			type : 'string'
		}, {
			name : 'name',
			type : 'string'
		}
	],
	proxy : {
		type : 'memory',
		reader : {
			type : 'json',
			root : 'items' //定义读取JSON数据的根对象
		}
	},
	constructor : function (config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});
//-------------------基础信息定义完成---------------------------------------------------------
/***
 * 快递折扣主方案 查询表单
 */
Ext.define('Foss.pricing.expressDiscount.QueryExpressDiscountForm',{
	extend:'Ext.form.Panel',
	title: pricing.expressDiscount.i18n('foss.pricing.expressDiscount.expressDiscountQueryCondition'),//'查询条件'查询条件
	frame:true,
	collapsible:true,
	defaults:{
		columnWidth : .3,
    	margin : '8 10 5 10',
    	anchor : '100%'
	},
	height :200,
	defaultType : 'textfield',
	layout:'column',
	constructor : function(config){
		var me = this, cfg = Ext.apply({}, config);
		me.items=[{
			xtype:'textfield',
			name:'name',
			fieldLabel:pricing.expressDiscount.i18n('foss.pricing.expressDiscount.planName')//方案名称
		},{
			name: 'planType',
			queryMode: 'local',
		    displayField: 'planTypeName',
		    valueField: 'planTypeCode',
		    editable:false,
		    value:'ALL',
		    store:pricing.getStore(null,null,['planTypeCode','planTypeName']
		    ,[{'planTypeCode':'setOffDiscount','planTypeName':pricing.expressDiscount.i18n('foss.pricing.expressDiscount.setOffDiscount')},{'planTypeCode':'ALL','planTypeName':pricing.expressDiscount.i18n('foss.pricing.expressDiscount.all')}]),
		    fieldLabel:pricing.expressDiscount.i18n('foss.pricing.expressDiscount.planType'),//'方案类型',
	        xtype : 'combo'
		},{
			name: 'active',
			queryMode: 'local',
		    displayField: 'valueName',
		    valueField: 'valueCode',
		    editable:false,
		    value:'ALL',
		    store:pricing.getStore(null,null,['valueCode','valueName']
		    ,[{'valueCode':'Y','valueName':pricing.expressDiscount.i18n('foss.pricing.expressDiscount.active')},{'valueCode':'N','valueName':pricing.expressDiscount.i18n('foss.pricing.expressDiscount.inActive')},{'valueCode':'ALL','valueName':pricing.expressDiscount.i18n('foss.pricing.expressDiscount.all')}]),
	        fieldLabel: pricing.expressDiscount.i18n('foss.pricing.expressDiscount.state'),//'状态',
	        xtype : 'combo'
		},{
			xtype:'commoncustomerselector',
			name:'customerCode',
			fieldLabel:pricing.expressDiscount.i18n('foss.pricing.expressDiscount.customerName'),//'客户名称',
			customerCode:null,
			listeners:{
			'select':function(comb,records,objs){
        		var form = this.up('form').getForm(); 
        		var record = records[0];
        		var cusCode = record.get('cusCode');
        		comb.customerCode = cusCode;
        	}
		}
		},{
			name: 'billTime',
			format:'Y-m-d H:i:s',
		    fieldLabel:pricing.expressDiscount.i18n('foss.pricing.expressDiscount.billTime'),//'业务日期',
	        xtype : 'datefield'
		}
	    ];
		me.fbar=[{
			xtype:'button',
			width:70,
			text:pricing.expressDiscount.i18n('foss.pricing.expressDiscount.reset'),//'重置',
			handler:function(){
				me.getForm().reset();
			}
		},{
			xtype : 'button', 
			width:70,
			cls:'yellow_button',
			margin:'0 0 0 825',
			text:pricing.expressDiscount.i18n('foss.pricing.expressDiscount.query'),//'查询',
			disabled: !pricing.expressDiscount.isPermission('expressDiscount/expressDiscountQuerybutton'),
			hidden: !pricing.expressDiscount.isPermission('expressDiscount/expressDiscountQuerybutton'),
			handler:function(){
				if(me.getForm().isValid()){
					var grid = Ext.getCmp('T_pricing-expressDiscount_content').getAreaGrid();
					grid.getStore().load();
				}
			}
		}];
		me.callParent([cfg]);
	}
});
/***
 * 快递折扣方案列表，（查询出来的数据放的位置）
 */
Ext.define('Foss.pricing.expressDiscount.ExpressDiscountGridPanel',{
	extend: 'Ext.grid.Panel',
	title : pricing.expressDiscount.i18n('foss.pricing.expressDiscount.queryResult'),//'查询结果',//查询结果
	frame: true,
	cls: 'autoHeight',
	bodyCls: 'autoHeight', 
    sortableColumns:false,
    enableColumnHide:false,
    enableColumnMove:false,
	stripeRows : true, // 交替行效果
	selType : "rowmodel", // 选择类型设置为：行选择
	emptyText:pricing.expressDiscount.i18n('foss.pricing.expressDiscount.queryResultEmpty'),//'查询结果为null',
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
	//分页结束
	
	//删除快递折扣主方案信息
	deleteExpressDiscount:function(){
		var me = this;
		var expressDiscountPlanIds = new Array();
		//获取选中的数据
		var selections = me.getSelectionModel().getSelection();
		if(selections.length<1){
			pricing.showErrorMes(pricing.expressDiscount.i18n('foss.pricing.expressDiscount.pleaseDeleteInActiveExpressDiscountPlan'));//请选择要删除的未激活折扣方案！
			return;
		}
		for(var i = 0 ; i<selections.length ; i++){
			if(selections[i].get('active')=='Y'){//只有未激活的折扣方案才可以删除
				pricing.showErrorMes(pricing.expressDiscount.i18n('foss.pricing.expressDiscount.pleaseDeleteInActiveExpressDiscountPlan'));//请选择要删除的未激活折扣方案！
				return;
			}else if(selections[i].get('active')=='N'){
				expressDiscountPlanIds.push(selections[i].get('id'));
			}else{
				expressDiscountPlanIds.push(selections[i].get('id'));
			}
		}
		if(expressDiscountPlanIds.length<1){
			pricing.showErrorMes(pricing.expressDiscount.i18n('foss.pricing.expressDiscount.pleaseChooseLeastOneDiscountPlan'));//请至少选择一条未激活的折扣方案！
			return;
		}
		pricing.showQuestionMes(pricing.expressDiscount.i18n('foss.pricing.expressDiscount.ifDeleteTheseInAcitveDiscountPlan'),function(e){//是否要删除这些未激活的折扣方案？
			if(e=='yes'){//询问是否删除，是则发送请求
				var params = {'expressDiscountPlanVo':{'expressDiscountPlanIds':expressDiscountPlanIds}};
				var successFun = function(json){
					pricing.showInfoMes(json.message);
					me.getPagingToolbar().moveFirst();
				};
				var failureFun = function(json){
					if(Ext.isEmpty(json)){
						pricing.showErrorMes(pricing.expressDiscount.i18n('i18n.pricingRegion.requestTimeOut'));
					}else{
						pricing.showErrorMes(json.message);
					}
				};
				var url = pricing.realPath('deleteExpressDsicountPlan.action'); //删除记录ACTION
				pricing.requestJsonAjax(url,params,successFun,failureFun);
			}
		});
	},
	//快递折扣方案新增Window
	expressDiscountAddWindow:null,
	getExpressDiscountAddWindow:function(){
		if(Ext.isEmpty(this.expressDiscountAddWindow)){
			this.expressDiscountAddWindow = Ext.create('Foss.pricing.expressDiscount.ExpressDiscountAddWindow');//定义的新增价格方案的面板
			this.expressDiscountAddWindow.parent = this;
		}
		return this.expressDiscountAddWindow;
	},
	//快递折扣方案修改Window
	expressDiscountUpdateWindow : null,
	getExpressDiscountUpdateWindow : function() {
		if (Ext.isEmpty(this.expressDiscountUpdateWindow)) {
			this.expressDiscountUpdateWindow = Ext.create('Foss.pricing.expressDiscount.ExpressDiscountDeatilEditorWindow');//快递折扣信息主方案修改面板定义
			this.expressDiscountUpdateWindow.parent = this;
		}
		return this.expressDiscountUpdateWindow;
	},
	
	//快递折扣方案明细新增Window
	expressDiscountDetailAddTabWindow : null,
	getExpressDiscountDetailAddTabWindow : function() {
		if (Ext.isEmpty(this.expressDiscountDetailAddTabWindow)) {
			this.expressDiscountDetailAddTabWindow = Ext.create('Foss.pricing.expressDiscount.ExpressDiscountDeatilEditorWindow');//快递折扣明细新增面板
			this.expressDiscountDetailAddTabWindow.parent = this;
		}
		return this.expressDiscountDetailAddTabWindow;
	},
	//查看详情明细
	expressDiscountDeatilShowWindow:null,
	getExpressDiscountDeatilShowWindow : function() {
		if (Ext.isEmpty(this.expressDiscountDeatilShowWindow)) {
			this.expressDiscountDeatilShowWindow = Ext.create('Foss.pricing.expressDiscount.ExpressDiscountDeatilShowWindow');
			this.expressDiscountDeatilShowWindow.parent = this;
		}
		return this.expressDiscountDeatilShowWindow;
	},
	
	 /**
     * 立即激活
     * 对于实际业务可能在当天发生两次以上的调整，故给予立即激中止功能用于支持该业务，
     * 1、立即中止功能给予特定角色来操作。根据所登陆的用户所属某某角色来决定是否给予立即中止功能。防止一般用户进行当天频繁调价操作
     * 2、立即中止功能中止日期可以等于今天但是不能小于今天。
     * 
     */
	 immediatelyExpressDiscountActiveWindow:null,
	 getImmediatelyExpressDiscountActiveWindow: function(){
			if(Ext.isEmpty(this.immediatelyExpressDiscountActiveWindow)){
				this.immediatelyExpressDiscountActiveWindow = Ext.create('Foss.pricing.expressDiscount.ImmediatelyActiveTimeWindow');
				this.immediatelyExpressDiscountActiveWindow.parent = this;
			}
			return this.immediatelyExpressDiscountActiveWindow;
		},

	/**
     * 立即中止
     * 对于实际业务可能在当天发生两次以上的调整，故给予立即激中止功能用于支持该业务，
     * 1、立即中止功能给予特定角色来操作。根据所登陆的用户所属某某角色来决定是否给予立即中止功能。防止一般用户进行当天频繁调价操作
     * 2、立即中止功能中止日期可以等于今天但是不能小于今天。
     * 
     */
    immediatelyStopWindow:null,
	getImmediatelyStopWindow : function(){
		if(Ext.isEmpty(this.immediatelyStopWindow)){
			this.immediatelyStopWindow = Ext.create('Foss.pricing.expressDiscount.ImmediatelyStopEndTimeWindow');
			this.immediatelyStopWindow.parent = this;
		}
		return this.immediatelyStopWindow;
	},
	
	
	/**
	 * 立即激活
	 */
	immediatelyExpressDiscountActive:function(){
    	var me = this;
	 	var selections = me.getSelectionModel().getSelection();
	 	if(selections.length < 1){
	 		pricing.showWoringMessage(pricing.expressDiscount.i18n('foss.pricing.expressDiscount.pleaseChooseOneDiscountPlan'));
			return;
	 	}
	 	if(selections.length > 1){
	 		pricing.showWoringMessage(pricing.expressDiscount.i18n('foss.pricing.expressDiscount.pleaseChooseOneDiscountPlan'));
			return;
	 	}
	 	if(selections[0].get('active')=='Y'){
	 		pricing.showWoringMessage(pricing.expressDiscount.i18n('foss.pricing.expressDiscount.pleaseChooseOneInActiveDiscountPlan'));
	 		return;
	 	}else{
	 		var expressDiscountEntity = selections[0].data;
	 		me.getImmediatelyExpressDiscountActiveWindow().expressDiscountEntity = expressDiscountEntity;
	 		me.getImmediatelyExpressDiscountActiveWindow().show();
	 	}
	},
	/**
	 * 立即中止
	 */
    immediatelyStop:function(){
    	var me = this;
	 	var selections = me.getSelectionModel().getSelection();
	 	if(selections.length < 1){
	 		pricing.showWoringMessage(pricing.expressDiscount.i18n('foss.pricing.selectOneRecordToOp'));
			return;
	 	}
	 	if(selections.length > 1){
	 		pricing.showWoringMessage(pricing.expressDiscount.i18n('foss.pricing.selectOneRecordToOp'));
			return;
	 	}
	 	if(selections[0].get('active')!='Y'){
	 		pricing.showWoringMessage(pricing.expressDiscount.i18n('foss.pricing.selectOneActiveRecordToOp'));
	 		return;
	 	}else{
	 		var expressDiscountEntity = selections[0].data;
	 		me.getImmediatelyStopWindow().expressDiscountEntity = expressDiscountEntity;
	 		me.getImmediatelyStopWindow().show();
	 	}
	},
	uploadDiscountform : null,
	    /**
	     * 上传时效方案信息
	     * @return {}
	     */
	    getUploadDiscountform: function(){
	    	if(Ext.isEmpty(this.uploadDiscountform)){
				this.uploadDiscountform = Ext.create('Foss.pricing.expressDiscount.uploadDiscountform');	
			}
			return this.uploadDiscountform;
	    },
	//GIRD的构造函数
	constructor : function(config){
		var me = this, cfg = Ext.apply({}, config);
		me.columns = [{
			xtype: 'rownumberer',
			width:40,
			text : pricing.expressDiscount.i18n('i18n.pricing.rowNumber'),//'序号'
		},{
			align : 'center',
			xtype : 'actioncolumn',
			text : pricing.expressDiscount.i18n('i18n.pricingRegion.opra'),//'操作',
			items:[{
				//修改操作
				iconCls: 'deppon_icons_edit',
                tooltip: pricing.expressDiscount.i18n('foss.pricing.update'),//'修改',
                disabled: !pricing.expressDiscount.isPermission('expressDiscount/expressDiscountUpdatebutton'),
				width:42,
				getClass : function (v, m, r, rowIndex) {
					if (r.get('active') === 'N') {
					    return 'deppon_icons_edit';
					} else {
					    return 'statementBill_hide';
					}
				},
				handler:function(grid,rowIndex,colIndex){
					//获取选中的操作的行的数据信息
					var record = grid.getStore().getAt(rowIndex);
					//封装JSON格式的数据用于传输 expressDiscountPlanVo.expressDiscountEntity
					var params = {'expressDiscountPlanVo':{'expressDiscountEntity':{'id':record.get('id')}}};
					var successFun = function(json){
						//获取要修改的行的数据信息
						var expressDiscountEntity = json.expressDiscountPlanVo.expressDiscountEntity;
						//方案渠道的信息集合
						var channelCodes = json.expressDiscountPlanVo.channelCodes;
						//操作
						grid.up().getExpressDiscountUpdateWindow().expressDiscountEntity = expressDiscountEntity;//设置折扣方案主信息
                        grid.up().getExpressDiscountUpdateWindow().channelCodes = channelCodes;//渠道信息
                        grid.up().getExpressDiscountUpdateWindow().show();//显示window
    	
					};
					var failureFun = function(json){
    					if(Ext.isEmpty(json)){
    						pricing.showErrorMes(pricing.expressDiscount.i18n('i18n.pricingRegion.requestTimeOut'));
    					}else{
    						pricing.showErrorMes(json.message);
    					}
    				};
    				var url = pricing.realPath('queryExpressDiscountPlanById.action'); //根据选中的ID查询
    				pricing.requestJsonAjax(url,params,successFun,failureFun);
                } 
			},{ //升级版本的操作 
				iconCls: 'deppon_icons_softwareUpgrade',
                tooltip: pricing.expressDiscount.i18n('foss.pricing.upgradedVersion'),//'升级版本',
                disabled: !pricing.expressDiscount.isPermission('expressDiscount/expressDiscountUpgradebutton'),
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
	                	var expressDiscountEntity = record.data;
	                	var params = {'expressDiscountPlanVo':{'expressDiscountEntity':expressDiscountEntity}};
	    				var successFun = function(json){
	    					pricing.showInfoMes(json.message);
	    					grid.up().getPagingToolbar().moveFirst();
	    					};
	    				var failureFun = function(json){
	    					if(Ext.isEmpty(json)){
	    						pricing.showErrorMes(pricing.expressDiscount.i18n('i18n.pricingRegion.requestTimeOut'));
	    					}else{
	    						pricing.showErrorMes(json.message);
	    					}
	    				};
	    				var url = pricing.realPath('upgradeExpressDiscountPlan.action'); //升级方案操作
	    				pricing.requestJsonAjax(url,params,successFun,failureFun);
	                }
			},{
				//查询详情的操作
				iconCls: 'deppon_icons_showdetail',
                tooltip: pricing.expressDiscount.i18n('foss.pricing.details'),//'查看详情',
                disabled: !pricing.expressDiscount.isPermission('expressDiscount/expressDiscountDetailbutton'),
				width:42,
                handler: function(grid, rowIndex, colIndex) {
                	var record = grid.getStore().getAt(rowIndex);
                	//封装JSON格式
                	var params = {'expressDiscountPlanVo':{'expressDiscountEntity':{'id':record.get('id')}}};
    				var successFun = function(json){
    					//如果操作成功；获取返回的数据
    					var expressDiscountEntity = json.expressDiscountPlanVo.expressDiscountEntity;
    					var channelCodes = json.expressDiscountPlanVo.channelCodes;
    					
                        grid.up().getExpressDiscountDeatilShowWindow().expressDiscountEntity = expressDiscountEntity;//设置折扣方案主信息
                        grid.up().getExpressDiscountDeatilShowWindow().channelCodes = channelCodes;//渠道信息
                        grid.up().getExpressDiscountDeatilShowWindow().show();//显示window
    				};
    				var failureFun = function(json){
    					if(Ext.isEmpty(json)){
    						pricing.showErrorMes(pricing.expressDiscount.i18n('i18n.pricingRegion.requestTimeOut'));
    					}else{
    						pricing.showErrorMes(json.message);
    					}
    				};
    				var url = pricing.realPath('queryExpressDiscountPlanById.action'); //选中数据后的查询语句
    				pricing.requestJsonAjax(url,params,successFun,failureFun);
                }
			}]
		},{//客户编码、客户名称、方案类型、方案名称、状态、创建人。修改时间，起始时间、截止时间
			text:pricing.expressDiscount.i18n('foss.pricing.expressDiscount.customerCode'),//'客户编码',
			dataIndex:'customerCode'
		},{
			text:pricing.expressDiscount.i18n('foss.pricing.expressDiscount.customerName'),//'客户名称',
			dataIndex:'customerName'
		},{
			text:pricing.expressDiscount.i18n('foss.pricing.expressDiscount.planType'),//'方案类型',
			dataIndex:'planType',
			renderer:function(value){
				if(value=='setOffDiscount'){
					return pricing.expressDiscount.i18n('foss.pricing.expressDiscount.setOffDiscount');//出发折扣
				}
			}
		},{
			text:pricing.expressDiscount.i18n('foss.pricing.expressDiscount.planName'),//'方案名称',
			dataIndex:'name'
		},{
			text:pricing.expressDiscount.i18n('foss.pricing.expressDiscount.modifyUser'),//'修改人',
			dataIndex:'modifyUserName'
		},{
			text:pricing.expressDiscount.i18n('foss.pricing.expressDiscount.modifyTime'),//'修改时间',
			dataIndex:'modifyTime',
			renderer:function(value){
				return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
			}
		},{
			text:pricing.expressDiscount.i18n('foss.pricing.expressDiscount.beginTime'),//'开始时间',
			dataIndex:'beginTime',
			renderer:function(value){
				return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
			}
		},{
			text:pricing.expressDiscount.i18n('foss.pricing.expressDiscount.endTime'),//'截止时间',
			dataIndex:'endTime',
			renderer:function(value){
				return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
			}
		},{
			text:'周特惠',//'周特惠',
			dataIndex:'weekNames'						
		},{
			text:pricing.expressDiscount.i18n('foss.pricing.expressDiscount.state'),//'状态',
			dataIndex:'active',
			renderer:function(value){
				if(value=='Y'){//'Y'表示激活
					return pricing.expressDiscount.i18n('i18n.pricingRegion.active');
				}else if(value=='N'){//'N'表示未激活
					return  pricing.expressDiscount.i18n('i18n.pricingRegion.unActive');
				}else{
					return '';
				}
			}
		},{
			text:"是否当前版本",
			dataIndex:'currentUsedVersion',
			renderer:function(value){
				if(value=='Y'){//'Y'表示是
					return "是";
				}else if(value=='N'){//'N'表示否
					return  "否";
				}else{
					return '';
				}
			}
		}];
		//通过定义的Store查询数据
		me.store = Ext.create('Foss.pricing.expressDiscount.expressDiscountPlanStore',{
			autoLoad : false,
			pageSize : 20,
			listeners:{
				beforeload:function(store,operation,eOpts){
					//在主界面中定义的查询的表单，在OnReady的时候加载，并赋值
					var queryForm = me.up().getQueryExpressDiscountForm();
					if(queryForm!=null){
						Ext.apply(operation,{
							params:{
								'expressDiscountPlanVo.expressDiscountEntity.name':queryForm.getForm().findField('name').getValue(),//方案名称 
								'expressDiscountPlanVo.expressDiscountEntity.planType':queryForm.getForm().findField('planType').getValue(),//方案类型、
								'expressDiscountPlanVo.expressDiscountEntity.active':queryForm.getForm().findField('active').getValue(),//方案状态、
								'expressDiscountPlanVo.expressDiscountEntity.customerCode':queryForm.getForm().findField('customerCode').getValue(),//客户名称、
								'expressDiscountPlanVo.expressDiscountEntity.billTime':queryForm.getForm().findField('billTime').getValue(),//业务日期查询
	
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
			text : pricing.expressDiscount.i18n('foss.pricing.expressDiscount.addDiscountPlan'),//'新增',
			disabled: !pricing.expressDiscount.isPermission('expressDiscount/expressDiscountAddbutton'),
			hidden: !pricing.expressDiscount.isPermission('expressDiscount/expressDiscountAddbutton'),
			handler :function(){
				me.getExpressDiscountAddWindow().show();
			} 
		},'-', {
			//删除
			text : pricing.expressDiscount.i18n('foss.pricing.expressDiscount.deleteDiscountPlan'),//'删除',
			disabled: !pricing.expressDiscount.isPermission('expressDiscount/expressDiscountDeletebutton'),
			hidden: !pricing.expressDiscount.isPermission('expressDiscount/expressDiscountDeletebutton'),
			handler :function(){
				me.deleteExpressDiscount();
			} 
		},'-', {
			text : pricing.expressDiscount.i18n('foss.pricing.expressDiscount.immediatelyActiveDiscountPlan'),//'立即激活',
			//hidden:!pricing.expressDiscount.isPermission('expressDiscount/expressDiscountImmediatelyActivebutton'),
			handler :function(){
				me.immediatelyExpressDiscountActive();
			} 
		},'-', {
			text : pricing.expressDiscount.i18n('foss.pricing.expressDiscount.immediatelyStopDiscountPlan'),//'立即中止',
			disabled:!pricing.expressDiscount.isPermission('expressDiscount/expressDiscountImmediatelyStopbutton'),
			hidden:!pricing.expressDiscount.isPermission('expressDiscount/expressDiscountImmediatelyStopbutton'),
			handler :function(){
				me.immediatelyStop();
			} 
		},'-', {
			text : pricing.expressDiscount.i18n('foss.pricing.import'),
			disabled:!pricing.expressDiscount.isPermission('expressDiscount/expressDiscountImportButton'),
			hidden:!pricing.expressDiscount.isPermission('expressDiscount/expressDiscountImportButton'),
			handler :function(){
				pricing.expressDiscount.showWoringMessage(pricing.expressDiscount.i18n('foss.pricing.expressDiscount.importWoring'),function(e) {
					me.getUploadDiscountform().show();
				});
				}
		}];
		me.bbar = me.getPagingToolbar();
		me.callParent([cfg]);
	}
	
});

/****
 * 
 * 定义新增主快递折扣主方案界面
 * 
 ****/
Ext.define('Foss.pricing.expressDiscount.ExpressDiscountAddWindow',{
	extend : 'Ext.window.Window',
	title : pricing.expressDiscount.i18n('foss.pricing.expressDiscount.addExpressDiscountPlan'),//'新增快递折扣方案',
	closable : true,
	modal : true,
	resizable:false,
	parent:null,
	closeAction : 'hide',
	width :590,
	height :540,
	//布局方式
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	listeners:{
		beforehide:function(me){
			//获取到快递折扣方案的FORM表单
			me.getExpressDiscountEditFormPanel().getForm().reset();
		},
		beforeshow:function(me){
			
		}
	},
	//定义主快递折扣价格方案的Form表单
	expressDiscountEditFormPanel:null,
	getExpressDiscountEditFormPanel:function(){
		if(Ext.isEmpty(this.expressDiscountEditFormPanel)){
			this.expressDiscountEditFormPanel =Ext.create('Foss.pricing.expressDiscount.ExpressDiscountEditFormShowPanel');
		}
		return this.expressDiscountEditFormPanel;
	},
	//提交快递折扣方案的主信息
	commintExpressDiscount:function(){
		var me = this;
		//获取表单的信息
		var form = me.getExpressDiscountEditFormPanel();
		//校验form是否通过校验
		if(form.getForm().isValid()){
			var expressDiscountEntityModel = new Foss.pricing.expressDiscount.expressDiscountEntity();// 折扣方案主信息
			//渠道信息
			var channelCodes = new Array();
			//将FORM中数据设置到MODEL里面
    		form.getForm().updateRecord(expressDiscountEntityModel);
    		//验证起始时间必须大于开始时间
    		if(expressDiscountEntityModel.get('beginTime').getTime()>=expressDiscountEntityModel.get('endTime')){
    			pricing.showWoringMessage(pricing.expressDiscount.i18n('foss.pricing.expressDiscount.endTimeGTbeginTime'));//终止日期需大于起始日期！
    			return;
    		}
    		var customerName = form.getForm().findField('customerName').customerName;
    		var customerCode = form.getForm().findField('customerName').customerCode;
    		expressDiscountEntityModel.set('customerName',customerName);
    		expressDiscountEntityModel.set('customerCode',customerCode);
 
    		//createTime":NaN,"modifyTime":NaN,"billTime":NaN
    		expressDiscountEntityModel.set('createTime',null);
    		expressDiscountEntityModel.set('modifyTime',null);
    		expressDiscountEntityModel.set('billTime',null);
    		
    		//渠道store
    		var channel = FossDataDictionary.getDataDictionaryStore(pricing.expressDiscount.channel);
    		
    		//循环将渠道信息添加到一个集合中，传递到后台数据进行插入操
    		var weeks = new Array();
    		var channelCodes = form.getForm().findField('channelType').getValue();	
    		var checkBoxs = form.down('checkboxgroup').getChecked();
    		Ext.Array.each(checkBoxs,function(item){
    				weeks.push(item.inputValue);			
    			});
    		//将需要添加的参数信息存在一个JSON格式的对象中
    		var params = {'expressDiscountPlanVo':{'expressDiscountEntity':expressDiscountEntityModel.data,'channelCodes':channelCodes,'weeks':weeks}};//折扣新增数据
    		var successFun = function(json){
				pricing.showInfoMes(json.message);//提示新增成功
				me.close();
				//成功之后重新查询刷新结果集
				me.parent.getPagingToolbar().moveFirst();
				//成功之后弹出新增一些明细的信息
				
				me.parent.getExpressDiscountDetailAddTabWindow().expressDiscountEntity = json.expressDiscountPlanVo.expressDiscountEntity;
				me.parent.getExpressDiscountDetailAddTabWindow().channelCodes = json.expressDiscountPlanVo.channelCodes;
				//显示添加折扣明细信息
				me.parent.getExpressDiscountDetailAddTabWindow().show();
			};
			var failureFun = function(json){
				if(Ext.isEmpty(json)){
					pricing.showErrorMes(pricing.expressDiscount.i18n('foss.pricing.requestTimedOut'));//请求超时
				}else{
					//提示失败原因
					pricing.showErrorMes(json.message);
				}
			};
			//请求折扣新增
			var url = pricing.realPath('addExpressDiscountPlan.action');
			//发送AJAX请求
			pricing.requestJsonAjax(url,params,successFun,failureFun);
		}
	},
	constructor : function(config){
		var me = this, 
		cfg = Ext.apply({}, config);
	me.items = [me.getExpressDiscountEditFormPanel()];
	me.fbar = [{
		text : pricing.expressDiscount.i18n('foss.pricing.expressDiscount.closeWindow'),//关闭
		handler :function(){
			me.close();
		} 
	},{
		text : pricing.expressDiscount.i18n('foss.pricing.expressDiscount.commitExpressDiscountPlan'),//'提交',
		cls:'yellow_button',
		margin:'0 0 0 360',
		handler :function(){
			me.commintExpressDiscount();
		} 
	}];
	me.callParent([cfg]);
 }
});

/***
 * 
 * 快递折扣方案的FORM表单，用于显示和新增的表单
 * 
 ***/
Ext.define('Foss.pricing.expressDiscount.ExpressDiscountEditFormShowPanel',{
	extend : 'Ext.form.Panel',
	frame: true,
	title:pricing.expressDiscount.i18n('foss.pricing.expressDiscount.discountPlanDefine'),//'折扣方案定义',//价格折扣方案定义
	flex:1,
	collapsible: true,
	height:500,
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
	constructor:function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		var channel = FossDataDictionary.getDataDictionaryStore(pricing.expressDiscount.channel);
		var minValueDate = new Date(pricing.expressDiscount.tomorrowTime);
		me.items = [{
			name: 'name',
			maxLength:50,
		    fieldLabel:pricing.expressDiscount.i18n('foss.pricing.expressDiscount.planName'),//方案名称
	        xtype : 'textfield'
		},{
			name: 'planType',
			queryMode: 'local',
		    displayField: 'planTypeName',
		    valueField: 'planTypeCode',
		    editable:false,
		    value:'',
		    store:pricing.getStore(null,null,['planTypeCode','planTypeName']
		    ,[{'planTypeCode':'setOffDiscount','planTypeName':pricing.expressDiscount.i18n('foss.pricing.expressDiscount.setOffDiscount')}]),
		    fieldLabel: pricing.expressDiscount.i18n('foss.pricing.expressDiscount.planType'),//'方案类型',
	        xtype : 'combo'
		},{
			xtype:'commoncustomerselector',
			name:'customerName',
			fieldLabel:pricing.expressDiscount.i18n('foss.pricing.expressDiscount.customerName'),//'客户名称',
			customerName:null,
		    customerCode:null,
		    listeners:{
	        	'select':function(comb,records,objs){
	        		var form = this.up('form').getForm(); 
	        		var record = records[0];
	        		var name = record.get('name');	        		
	        		var cusCode = record.get('cusCode');	
	        		comb.customerName = name;
	        		comb.customerCode = cusCode;
	        		form.findField('customerCode').setValue(cusCode);
	        	}
		  }
		},{
			xtype:'textfield',
			name:'customerCode',
			readOnly:true,
			fieldLabel:pricing.expressDiscount.i18n('foss.pricing.expressDiscount.customerCode')//'客户编码'
		},{ //下拉复选框	
			xtype: 'combo',
			fieldLabel:pricing.expressDiscount.i18n('foss.pricing.expressDiscount.channel'),//'渠道',
            name: 'channelType',
            valueField:'value', //显示值       
            editable: false,           
            store: channel,
            multiSelect:true,
            valueField: 'valueCode',
            displayField: 'valueName', 
            triggerAction: 'all',
            allowBlank:true,
            emptyText:pricing.expressDiscount.i18n('foss.pricing.expressDiscount.pleaseChoose')//'请选择' 
	    },{
			name: 'beginTime',
			colspan: 1,
			format:'Y-m-d',
			minValue:minValueDate,  
		    fieldLabel:pricing.expressDiscount.i18n('foss.pricing.expressDiscount.beginTime'),//开始时间
	        xtype : 'datefield'
		},{
			name: 'endTime',//截止时间,
			colspan: 1,
			labelWidth:60,
			minValue:minValueDate,
			format:'Y-m-d',
		    fieldLabel:pricing.expressDiscount.i18n('foss.pricing.expressDiscount.endTime'),//'截止时间',
	        xtype : 'datefield'
		},{
			xtype: 'checkboxgroup',
	        fieldLabel: '周特惠',
	        // Arrange checkboxes into two columns, distributed vertically
	        columns: 8,
	        columnWidth:50,
	        vertical: true,
	        items: [
                { boxLabel: '全周', name: 'preferentialWeek', inputValue: 'ALL' },
	            { boxLabel: '周一', name: 'preferentialWeek', inputValue: 'MON' },
	            { boxLabel: '周二', name: 'preferentialWeek', inputValue: 'TUE'},
	            { boxLabel: '周三', name: 'preferentialWeek', inputValue: 'WED' },
	            { boxLabel: '周四', name: 'preferentialWeek', inputValue: 'THU' },
	            { boxLabel: '周五', name: 'preferentialWeek', inputValue: 'FRI' },
	            { boxLabel: '周六', name: 'preferentialWeek', inputValue: 'SAT' },
	            { boxLabel: '周日', name: 'preferentialWeek', inputValue: 'SUN' }
	        ]	    
		},{
			name: 'remark',
			width:400,
			maxLength:200,
			allowBlank:true,
		    fieldLabel:pricing.expressDiscount.i18n('foss.pricing.expressDiscount.planDescription'),//'方案描述',
	        xtype : 'textareafield'
		}];
		me.callParent([cfg]);
	}
});


/***
 * 
 * 快递折扣方案的FORM表单，用于修改折扣方案
 * 
 ***/
Ext.define('Foss.pricing.expressDiscount.ExpressDiscountEditFormPanel',{
	extend : 'Ext.form.Panel',
	frame: true,
	title:pricing.expressDiscount.i18n('foss.pricing.expressDiscount.discountPlanUpdate'),//价格折扣方案定义
	flex:1,
	expressDiscountEntity:null,
	oldChannelCodes:null,
	collapsible: true,
	height:500,
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
	
	//用于修改 fancy
	updateExpressDiscountDetail:function(){
		var me = this;
		//获取表单的信息
		var form = me;
		//校验form是否通过校验
		if(form.getForm().isValid()){
			var expressDiscountEntityModel = new Foss.pricing.expressDiscount.expressDiscountEntity(me.expressDiscountEntity);// 折扣方案主信息
			//渠道信息
			var channelCodes = new Array();
			
			//用于存放老的折扣信息方案
			var oldChannelCodes = new Array();
			
			//赋值老的数据
			oldChannelCodes = me.getForm().oldChannelCodes;	
			
			//将FORM中数据设置到MODEL里面
    		form.getForm().updateRecord(expressDiscountEntityModel);
    		
    		//验证起始时间必须大于开始时间
    		if(expressDiscountEntityModel.get('beginTime').getTime()>=expressDiscountEntityModel.get('endTime')){
    			pricing.showWoringMessage(pricing.expressDiscount.i18n('foss.pricing.expressDiscount.endTimeGTbeginTime'));//终止日期需大于起始日期！
    			return;
    		}
    		var customerName = form.getForm().findField('customerName').getRawValue();
    		var expressDiscountEntityId = me.getForm().expressDiscountEntity.id;
    		var remark = form.getForm().findField('remark').getValue();
  		
    		expressDiscountEntityModel.set('customerName',customerName);    	
    		expressDiscountEntityModel.set('remark',remark);
    		expressDiscountEntityModel.set('createTime',null);
    		expressDiscountEntityModel.set('modifyTime',null);
    		expressDiscountEntityModel.set('billTime',null);
    		expressDiscountEntityModel.set('id',expressDiscountEntityId);
    		
    		//渠道store
    		var channel = FossDataDictionary.getDataDictionaryStore(pricing.expressDiscount.channel);
    		
    		//循环将渠道信息添加到一个集合中，传递到后台数据进行插入操
    		var channelCodes = form.getForm().findField('channelType').getValue();	
    		var weeks = new Array();
    		var checkBoxs = form.down('checkboxgroup').getChecked();
    		Ext.Array.each(checkBoxs,function(item){
    				weeks.push(item.inputValue);
    			});
    		//将需要添加的参数信息存在一个JSON格式的对象中
    		var params = {'expressDiscountPlanVo':{'expressDiscountEntity':expressDiscountEntityModel.data,'channelCodes':channelCodes,'oldChannelCodes':oldChannelCodes,'weeks':weeks}};//折扣新增数据
    		var successFun = function(json){
    			//提示新增成功
				pricing.showInfoMes(json.message);
				//当修改成功了，需要更新下表单中的数据信息
				Ext.getCmp('T_pricing-expressDiscount_content').getAreaGrid().getPagingToolbar().moveFirst();
			};
			var failureFun = function(json){
				if(Ext.isEmpty(json)){
					pricing.showErrorMes(pricing.expressDiscount.i18n('foss.pricing.requestTimedOut'));//请求超时
				}else{
					//提示失败原因
					pricing.showErrorMes(json.message);
				}
			};
			//更新主方案折扣信息
			var url = pricing.realPath('updateByIdSelective.action');
			//发送AJAX请求
			pricing.requestJsonAjax(url,params,successFun,failureFun);
		}
		
	},
	
	constructor:function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		var channel = FossDataDictionary.getDataDictionaryStore(pricing.expressDiscount.channel);
		var channelItems = new Array();
	
		var minValueDate = new Date(pricing.expressDiscount.tomorrowTime);
		me.items = [{
			name: 'name',
			maxLength:50,
		    fieldLabel:pricing.expressDiscount.i18n('foss.pricing.expressDiscount.planName'),//方案名称
	        xtype : 'textfield'
		},{
			name: 'planType',
			queryMode: 'local',
		    displayField: 'planTypeName',
		    valueField: 'planTypeCode',
		    editable:false,
		    value:'',
		    store:pricing.getStore(null,null,['planTypeCode','planTypeName']
		    ,[{'planTypeCode':'setOffDiscount','planTypeName':pricing.expressDiscount.i18n('foss.pricing.expressDiscount.setOffDiscount')}]),
		    fieldLabel:pricing.expressDiscount.i18n('foss.pricing.expressDiscount.planType'),//'方案类型',
	        xtype : 'combo'
		},{
			xtype:'commoncustomerselector',
			name:'customerName',
			fieldLabel:pricing.expressDiscount.i18n('foss.pricing.expressDiscount.customerName'),//'客户名称',
			customerName:null,
		    customerCode:null,
		    listeners:{
	        	'select':function(comb,records,objs){
	        		var form = this.up('form').getForm(); 
	        		var record = records[0];
	        		var name = record.get('name');	        		
	        		var cusCode = record.get('cusCode');	
	        		comb.customerName = name;
	        		comb.customerCode = cusCode;
	        		form.findField('customerCode').setValue(cusCode);
	        	}
		  }
		},{
			xtype:'textfield',
			name:'customerCode',
			readOnly:true,
			fieldLabel:pricing.expressDiscount.i18n('foss.pricing.expressDiscount.customerCode')//'客户编码'
		},{ //下拉复选框	
			xtype: 'combo',
			fieldLabel:pricing.expressDiscount.i18n('foss.pricing.expressDiscount.channel'),//'渠道',
            name: 'channelType',
            valueField:'value', //显示值       
            editable: false,           
            store: channel,
            multiSelect:true,
            valueField: 'valueCode',
            displayField: 'valueName', 
            triggerAction: 'all',
            allowBlank:true,
            emptyText:pricing.expressDiscount.i18n('foss.pricing.expressDiscount.pleaseChoose')//'请选择' 
	    },{
			name: 'beginTime',
			colspan: 1,
			format:'Y-m-d',
			minValue:minValueDate,
		    fieldLabel:pricing.expressDiscount.i18n('foss.pricing.expressDiscount.beginTime'),//开始时间
	        xtype : 'datefield'
		},{
			name: 'endTime',
			colspan: 1,
			labelWidth:60,
			minValue:minValueDate,
			format:'Y-m-d',
		    fieldLabel:pricing.expressDiscount.i18n('foss.pricing.expressDiscount.endTime'),//'截止时间',
	        xtype : 'datefield'
		},{
			xtype: 'checkboxgroup',
	        fieldLabel: '周特惠',
	        itemId:'preferentialWeek',
	        // Arrange checkboxes into two columns, distributed vertically
	        columns: 8,
	        columnWidth:50,
	        vertical: true,
	        items: [
                { boxLabel: '全周', name: 'preferentialWeek', inputValue: 'ALL' },
	            { boxLabel: '周一', name: 'preferentialWeek', inputValue: 'MON' },
	            { boxLabel: '周二', name: 'preferentialWeek', inputValue: 'TUE'},
	            { boxLabel: '周三', name: 'preferentialWeek', inputValue: 'WED' },
	            { boxLabel: '周四', name: 'preferentialWeek', inputValue: 'THU' },
	            { boxLabel: '周五', name: 'preferentialWeek', inputValue: 'FRI' },
	            { boxLabel: '周六', name: 'preferentialWeek', inputValue: 'SAT' },
	            { boxLabel: '周日', name: 'preferentialWeek', inputValue: 'SUN' }
	        ]	    
		},{
			name: 'remark',
			width:400,
			maxLength:200,
			allowBlank:true,
		    fieldLabel:pricing.expressDiscount.i18n('foss.pricing.expressDiscount.planDescription'),//'方案描述',
	        xtype : 'textareafield'
		}];
		me.fbar = [{
			text : pricing.expressDiscount.i18n('foss.pricing.expressDiscount.reset'),//'重置',
			margin:'0 0 0 360',
			handler :function(){
				me.getForm().reset();
			} 
		},{
			text :  pricing.expressDiscount.i18n('foss.pricing.expressDiscount.commitExpressDiscountPlan'),//'提交',
			cls:'yellow_button',
			margin:'0 0 0 360',
			handler :function(){
				me.updateExpressDiscountDetail();
			} 
		}];
		me.callParent([cfg]);
	}
});
/***
 * @author 200945 wutao
 * 用于修改新快递主折扣方案的信息
 * 
 ***/
Ext.define('Foss.pricing.expressDiscount.ExpressDiscountEditFormUpdatePanel',{
	extend : 'Ext.form.Panel',
	frame: true,
	title:pricing.expressDiscount.i18n('foss.pricing.expressDiscount.discountPlanUpdate'),//'修改价格折扣方案定义',
	flex:1,
	collapsible: true,
	height:500,
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
	constructor:function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		var channel = FossDataDictionary.getDataDictionaryStore(pricing.expressDiscount.channel);
		var channelItems = new Array();
		channel.each(function(record){
			var item = { text:record.get('valueName'), name:record.get('valueCode'),channelEntity:record.data };
			channelItems.push(item);
		});
		var minValueDate = null;
		me.items = [{
			name: 'name',
			maxLength:50,
		    fieldLabel:pricing.expressDiscount.i18n('foss.pricing.expressDiscount.planName'),//'方案名称',
	        xtype : 'textfield'
		},{
			name: 'planType',
			queryMode: 'local',
		    displayField: 'planTypeName',
		    valueField: 'planTypeCode',
		    editable:false,
		    value:'ALL',
		    store:pricing.getStore(null,null,['planTypeCode','planTypeName']
		    ,[{'planTypeCode':'setOffDiscount','planTypeName':pricing.expressDiscount.i18n('foss.pricing.expressDiscount.setOffDiscount')}]),
		    fieldLabel:pricing.expressDiscount.i18n('foss.pricing.expressDiscount.planType'),//'方案类型',
	        xtype : 'combo'
		},{
			xtype:'commoncustomerselector',
			name:'customerName',
			fieldLabel:pricing.expressDiscount.i18n('foss.pricing.expressDiscount.customerName'),//'客户名称',
			customerName:null,
		    customerCode:null,
		    listeners:{
	        	'select':function(comb,records,objs){
	        		var form = this.up('form').getForm(); 
	        		var record = records[0];
	        		var name = record.get('name');	        		
	        		var cusCode = record.get('cusCode');	
	        		comb.customerName = name;
	        		comb.customerCode = cusCode;
	        		form.findField('customerCode').setValue(cusCode);
	        	}
		  }
		},{
			xtype:'textfield',
			name:'customerCode',
			readOnly:true,
			fieldLabel:pricing.expressDiscount.i18n('foss.pricing.expressDiscount.customerCode')//'客户编码'
		},{ //下拉复选框	
			xtype: 'combo',
			fieldLabel:pricing.expressDiscount.i18n('foss.pricing.expressDiscount.channel'),//'渠道',
            name: 'channelType',
            valueField:'value', //显示值       
            editable: false,           
            store: channel,
            multiSelect:true,
            valueField: 'valueCode',
            displayField: 'valueName', 
            triggerAction: 'all',
            emptyText:pricing.expressDiscount.i18n('foss.pricing.expressDiscount.pleaseChoose')//'请选择' 
	    },{
			name: 'beginTime',
			colspan: 1,
			format:'Y-m-d',
			minValue:minValueDate,
		    fieldLabel:pricing.expressDiscount.i18n('foss.pricing.expressDiscount.beginTime'),//'开始时间',
	        xtype : 'datefield'
		},{
			name: 'endTime',
			colspan: 1,
			labelWidth:60,
			minValue:minValueDate,
			format:'Y-m-d',
		    fieldLabel:pricing.expressDiscount.i18n('foss.pricing.expressDiscount.endTime'),//'截止时间',
	        xtype : 'datefield'
		},{
			xtype: 'checkboxgroup',
	        fieldLabel: '周特惠',
	        itemId:'preferentialWeek',
	        // Arrange checkboxes into two columns, distributed vertically
	        columns: 8,
	        columnWidth:50,
	        vertical: true,
	        items: [
	            { boxLabel: '周一', name: 'preferentialWeek', inputValue: 'MON' },
	            { boxLabel: '周二', name: 'preferentialWeek', inputValue: 'TUE'},
	            { boxLabel: '周三', name: 'preferentialWeek', inputValue: 'WED' },
	            { boxLabel: '周四', name: 'preferentialWeek', inputValue: 'THU' },
	            { boxLabel: '周五', name: 'preferentialWeek', inputValue: 'FRI' },
	            { boxLabel: '周六', name: 'preferentialWeek', inputValue: 'SAT' },
	            { boxLabel: '周日', name: 'preferentialWeek', inputValue: 'SUN' },
	            { boxLabel: '全周', name: 'preferentialWeek', inputValue: 'ALL' }
	        ]	    
		},{
			name: 'remark',
			width:400,
			maxLength:200,
			allowBlank:true,
		    fieldLabel:pricing.expressDiscount.i18n('foss.pricing.expressDiscount.planDescription'),//'方案描述',
	        xtype : 'textareafield'
		}];
		me.callParent([cfg]);
	},
	//
});
/****
 * 
 * TAB，用于显示折扣详细信息和主方案信息
 * 
 ***/
Ext.define('Foss.pricing.expressDiscount.ExpressDiscountShowTab',{
	extend:'Ext.tab.Panel',
	clsL:'innerTabPanel',
	flex:1,
	layout:{
		type:'vbox',
		align:'stretch'
	},
	//快递折扣主方案信息FORM
	//定义主快递折扣价格方案的Form表单
	expressDiscountEditFormPanel:null,
	getExpressDiscountEditFormPanel:function(){
		if(Ext.isEmpty(this.expressDiscountEditFormPanel)){
			this.expressDiscountEditFormPanel =Ext.create('Foss.pricing.expressDiscount.ExpressDiscountEditFormShowPanel');
			//设置为不可编辑
			this.expressDiscountEditFormPanel.getForm().getFields().each(function(item){
    			item.setReadOnly(true);
    		});
			//fancy
			this.expressDiscountEditFormPanel.getForm().findField('beginTime').allowBlank=true;
			this.expressDiscountEditFormPanel.getForm().findField('endTime').allowBlank=true;
		}
		return this.expressDiscountEditFormPanel;
	},
	
	
	//快递显示折扣详细信息FORM
	expressDiscountDetailShowPanel:null,
	getExpressDiscountDetailShowPanel:function(){
		if(Ext.isEmpty(this.ExpressDiscountDetailShowPanel)){
			this.ExpressDiscountDetailShowPanel =Ext.create('Foss.pricing.expressDiscount.ExpressDiscountDetailShowPanel');
		}
		return this.ExpressDiscountDetailShowPanel;
	},
	
	constructor:function(config){
		var me = this,cfg= Ext.apply({},config);
		me.items = [me.getExpressDiscountEditFormPanel(),me.getExpressDiscountDetailShowPanel()];
		me.callParent([cfg]);
	}
});

/****
 * 
 * TAB，用于修改  折扣详细信息  和  主方案信息
 * 
 ***/
Ext.define('Foss.pricing.expressDiscount.ExpressDiscountEditorTab',{
	extend:'Ext.tab.Panel',
	clsL:'innerTabPanel',
	flex:1,
	layout:{
		type:'vbox',
		align:'stretch'
	},
	//快递折扣主方案信息FORM
	//定义主快递折扣价格方案的Form表单
	expressDiscountEditFormPanel:null,
	getExpressDiscountEditFormPanel:function(){
		if(Ext.isEmpty(this.expressDiscountEditFormPanel)){
			this.expressDiscountEditFormPanel =Ext.create('Foss.pricing.expressDiscount.ExpressDiscountEditFormPanel');
		}
		return this.expressDiscountEditFormPanel;
	},
	
	
	//快递折扣详细信息FORM
	expressDiscountDetailEditorPanel:null,
	getExpressDiscountDetailEditorPanel:function(){
		if(Ext.isEmpty(this.expressDiscountDetailEditorPanel)){
			this.expressDiscountDetailEditorPanel =Ext.create('Foss.pricing.expressDiscount.ExpressDiscountDetailEditorPanel');
		}
		return this.expressDiscountDetailEditorPanel;
	},
	
	constructor:function(config){
		var me = this,cfg= Ext.apply({},config);           
		me.items = [me.getExpressDiscountEditFormPanel(),me.getExpressDiscountDetailEditorPanel()];
		me.callParent([cfg]);
	}
});


//------------------------------------------------------折扣方案明细的查询和新增的PANEL布局开始-------------------------------------------------------------
/****
 * 
 * @author 200945 wutao
 * 快递折扣明细信息显示页面
 * FORM(上)
 * GIRD(下)
 * 
 */
Ext.define('Foss.pricing.expressDiscount.ExpressDiscountDetailShowPanel',{
	extend : 'Ext.panel.Panel',
	title : pricing.expressDiscount.i18n('foss.pricing.expressDiscount.discountDetailResult'),//'折扣明细信息结果',
	//frame: true,
	flex:1,
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	//已选折扣信息结果-查询条件
	queryExpressDiscountDetailForm:null,
	getQueryExpressDiscountDetailForm:function(){
		if(Ext.isEmpty(this.queryExpressDiscountDetailForm)){
    		this.queryExpressDiscountDetailForm = Ext.create('Foss.pricing.expressDiscount.QueryExpressDiscountDetailShowForm'); //查询的FORM表单
    	}
    	return this.queryExpressDiscountDetailForm;
	},
	
	//已选折扣信息结果GRID
	expressDiscountDetailGridPanel:null,
	getExpressDiscountDetailGridPanel:function(){
		if(Ext.isEmpty(this.expressDiscountDetailGridPanel)){
    		this.expressDiscountDetailGridPanel = Ext.create('Foss.pricing.expressDiscount.ExpressDiscountDetailGridPanel'); //用于显示GRID
    	}
    	return this.expressDiscountDetailGridPanel;
	},
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		//加载到对应的Panel上
		me.items = [me.getQueryExpressDiscountDetailForm(),me.getExpressDiscountDetailGridPanel()];
		me.callParent([cfg]);
	}
});



/****
 * 
 * 用于编辑明细信息显示页面
 * FORM(上)
 * GIRD(下)
 * 
 */
Ext.define('Foss.pricing.expressDiscount.ExpressDiscountDetailEditorPanel',{
	extend : 'Ext.panel.Panel',
	title : pricing.expressDiscount.i18n('foss.pricing.expressDiscount.discountDetailResult'),//'折扣明细信息结果',
	//frame: true,
	flex:1,
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	//已选折扣信息结果-查询条件
	queryExpressDiscountDetailForm:null,
	getQueryExpressDiscountDetailForm:function(){
		if(Ext.isEmpty(this.queryExpressDiscountDetailForm)){
    		this.queryExpressDiscountDetailForm = Ext.create('Foss.pricing.expressDiscount.QueryExpressDiscountDetailForm');
    	}
    	return this.queryExpressDiscountDetailForm;
	},
	
	//扣信息结果GRID
	expressDiscountDetailGridEditorPanel:null,
	getExpressDiscountDetailGridEditorPanel:function(){
		if(Ext.isEmpty(this.expressDiscountDetailGridEditorPanel)){ 
    		this.expressDiscountDetailGridEditorPanel = Ext.create('Foss.pricing.expressDiscount.ExpressDiscountDetailGridEditorPanel');
    	}
    	return this.expressDiscountDetailGridEditorPanel;
	},
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		//加载到对应的Panel上
		me.items = [me.getQueryExpressDiscountDetailForm(),me.getExpressDiscountDetailGridEditorPanel()];
		me.callParent([cfg]);
	}
});
/***
 * 
 *快递折扣详细价格方案查询FORM 
 * 
 ***/
Ext.define('Foss.pricing.expressDiscount.QueryExpressDiscountDetailForm',{
	extend:'Ext.form.Panel',
	title:  pricing.expressDiscount.i18n('foss.pricing.expressDiscount.discountDetailQueryCondition'),//查询条件
	frame:true,
	collapsible:true,
	defaults:{
		columnWidth : .4,
    	margin : '8 10 5 10',
    	anchor : '100%'
	},
	height :200,
	defaultType : 'textfield',
	layout:'column',
	constructor : function(config){
		var me = this, cfg = Ext.apply({}, config);
		var all = {'id':'','name':pricing.expressDiscount.i18n('i18n.pricingRegion.all')};
		var allProduct = {'id':'ALL','code':'ALL','name':pricing.expressDiscount.i18n('foss.pricing.allProducts')};
		var productEntityList =  pricing.addAll(pricing.expressDiscount.productEntityList,all);
		var productEntityList =  pricing.addAll(pricing.expressDiscount.productEntityList,allProduct);
		var goodTypeList = pricing.expressDiscount.goodTypeList;
		me.items=[{
			xtype:'commonexpresspriceregionselector',
			name:'startRegionCode',
			fieldLabel:pricing.expressDiscount.i18n('foss.pricing.expressDiscount.startRegion'),//'始发区域',
			valueField:'id',
			startRegionName:null,
		    startRegionId:null,
		    listeners:{
		    	select:function(comb,records,eOpts){
		    		var record = records[0];
		    		comb.startRegionId = record.get('id');
		    		comb.startRegionName = record.get('regionName'); 
		    		}
		       }
		},{
			xtype:'commonexpresspriceregionselector',
			name:'arriveRegionCode',
			fieldLabel:pricing.expressDiscount.i18n('foss.pricing.expressDiscount.arriveRegion'),//'目的区域',
			valueField:'id',
			arriveRegionName:null,
		    arriveRegionId:null,
		    listeners:{
		    	select:function(comb,records,eOpts){
		    		var record = records[0];
		    		comb.arriveRegionId = record.get('id');
		    		comb.arriveRegionName = record.get('regionName'); 
		    		}
		       }
		},{
			name: 'productCode',
			queryMode: 'local',
		    displayField: 'name',
		    valueField: 'id',
		    value:'',
		    editable:false,
		    productRecord:null,//基础产品实体
		    store:pricing.getStore(null,'Foss.pricing.expressDiscount.ProductEntity',null,productEntityList),
	        fieldLabel: pricing.expressDiscount.i18n('foss.pricing.basicProducts'),//'基础产品',
	        xtype : 'combo'
		},{
			name: 'goodsTypeCode',
			queryMode: 'local',
		    displayField: 'name',
		    valueField: 'code',
		    editable:false,
		    goodsTypeRecord:null,
		    value:'',
		    store:pricing.getStore(null,'Foss.pricing.expressDiscount.GoodsTypeEntity',null,goodTypeList),
	        fieldLabel:  pricing.expressDiscount.i18n('foss.pricing.cargoType'),//'货物类型',
	        goodsTypeCodes:null,
	        goodsTypeId:null,
	        goodsTypeName:null,
	        xtype : 'combo',
	        listeners:{
	        	select:function(comb,records){
	        		comb.goodsTypeCodes = records[0].get('code');
	        		comb.goodsTypeId = records[0].get('id');
	        		comb.goodsTypeName = records[0].get('name');
	        		
	        	}
	        }
		}];
		me.fbar = [{
			xtype : 'button', 
			width:70,
			text : pricing.expressDiscount.i18n('foss.pricing.reset'),
			handler : function() {
				me.getForm().reset();
			}
		},{
			xtype : 'button', 
			width:70,
			text : pricing.expressDiscount.i18n('i18n.pricingRegion.search'),
			margin:'0 0 0 375',
			cls:'yellow_button',
			handler : function() {
				if(me.getForm().isValid()){
					var grid = me.up('panel').getExpressDiscountDetailGridEditorPanel();
					grid.getStore().load();
				}
			}
		}]
		me.callParent([cfg]);
	}
});

/***
 * 
 *快递折扣详细价格方案查询FORM 
 * 仅仅只用于查询
 * 
 ***/
Ext.define('Foss.pricing.expressDiscount.QueryExpressDiscountDetailShowForm',{
	extend:'Ext.form.Panel',
	title: pricing.expressDiscount.i18n('foss.pricing.expressDiscount.discountDetailQueryCondition'),//查询条件
	frame:true,
	collapsible:true,
	defaults:{
		columnWidth : .4,
    	margin : '8 10 5 10',
    	anchor : '100%'
	},
	height :200,
	defaultType : 'textfield',
	layout:'column',
	constructor : function(config){
		var me = this, cfg = Ext.apply({}, config);
		var all = {'id':'','name':pricing.expressDiscount.i18n('i18n.pricingRegion.all')};
		var allProduct = {'id':'ALL','code':'ALL','name':pricing.expressDiscount.i18n('foss.pricing.allProducts')};
		var productEntityList =  pricing.addAll(pricing.expressDiscount.productEntityList,all);
		var productEntityList =  pricing.addAll(pricing.expressDiscount.productEntityList,allProduct);
		var goodTypeList = pricing.expressDiscount.goodTypeList;
		me.items=[{
			xtype:'commonexpresspriceregionselector',
			name:'startRegionCode',
			fieldLabel:pricing.expressDiscount.i18n('foss.pricing.expressDiscount.startRegion'),//'始发区域',
			valueField:'id',
			startRegionName:null,
		    startRegionId:null,
		    listeners:{
		    	select:function(comb,records,eOpts){
		    		var record = records[0];
		    		comb.startRegionId = record.get('id');
		    		comb.startRegionName = record.get('regionName'); 
		    		}
		       }
		},{
			xtype:'commonexpresspriceregionselector',
			name:'arriveRegionCode',
			fieldLabel:pricing.expressDiscount.i18n('foss.pricing.expressDiscount.arriveRegion'),//'目的区域',
			valueField:'id',
			arriveRegionName:null,
		    arriveRegionId:null,
		    listeners:{
		    	select:function(comb,records,eOpts){
		    		var record = records[0];
		    		comb.arriveRegionId = record.get('id');
		    		comb.arriveRegionName = record.get('regionName'); 
		    		}
		       }
		},{
			name: 'productCode',
			queryMode: 'local',
		    displayField: 'name',
		    valueField: 'id',
		    value:'',
		    editable:false,
		    productRecord:null,//基础产品实体
		    store:pricing.getStore(null,'Foss.pricing.expressDiscount.ProductEntity',null,productEntityList),
	        fieldLabel:pricing.expressDiscount.i18n('foss.pricing.basicProducts') ,//'基础产品',
	        xtype : 'combo'
		},{
			name: 'goodsTypeCode',
			queryMode: 'local',
		    displayField: 'name',
		    valueField: 'code',
		    editable:false,
		    goodsTypeRecord:null,
		    value:'',
		    store:pricing.getStore(null,'Foss.pricing.expressDiscount.GoodsTypeEntity',null,goodTypeList),
	        fieldLabel:pricing.expressDiscount.i18n('foss.pricing.cargoType'),//'货物类型',
	        goodsTypeCodes:null,
	        goodsTypeId:null,
	        goodsTypeName:null,
	        xtype : 'combo',
	        listeners:{
	        	select:function(comb,records){
	        		comb.goodsTypeCodes = records[0].get('code');
	        		comb.goodsTypeId = records[0].get('id');
	        		comb.goodsTypeName = records[0].get('name');
	        	}
	        }
		}];
		me.fbar=[{
			xtype : 'button', 
			width:70,
			text : pricing.expressDiscount.i18n('foss.pricing.reset'),
			handler : function() {
				me.getForm().reset();
			}
		},{
			xtype : 'button', 
			width:70,
			cls:'yellow_button',
			margin:'0 0 0 375',
			text:pricing.expressDiscount.i18n('foss.pricing.expressDiscount.query'),
			disabled: !pricing.expressDiscount.isPermission('expressDiscount/expressDiscountQuerybutton'),
			hidden: !pricing.expressDiscount.isPermission('expressDiscount/expressDiscountQuerybutton'),
			handler : function() {
				if(me.getForm().isValid()){
					var grid = me.up('panel').getExpressDiscountDetailGridPanel();
					grid.getStore().load();
				}
			}
		}];
		me.callParent([cfg]);
	}
});
/***
 * 用于显示快递折扣详细信息的GRID(可以编辑)
 ***/
Ext.define('Foss.pricing.expressDiscount.ExpressDiscountDetailGridEditorPanel',{
	extend: 'Ext.grid.Panel',
	title :pricing.expressDiscount.i18n('foss.pricing.expressDiscount.discountDetailsQueryResult'),//'查询结果', 
	frame: true,
	flex:1,
	expressDiscountEntity:null,
	autoScroll:true,
    sortableColumns:false,
    enableColumnHide:false,
    enableColumnMove:false,
	stripeRows : true, // 交替行效果
	selType : "rowmodel", // 选择类型设置为：行选择
	emptyText: pricing.expressDiscount.i18n('foss.pricing.theQueryResultIsEmpty'),//查询结果为空
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
	//删除快递折扣明细方案
	deleteExpressDiscountDeatil:function(){
		var me = this;
		var expressDiscountDetailIds = new Array();
		//获取选中的数据
		var selections = me.getSelectionModel().getSelection();
		if(selections.length<1){
			pricing.showErrorMes(pricing.expressDiscount.i18n('foss.pricing.expressDiscount.pleaseChooseDeleteDiscountDetial'));//请选择要删除的未激活折扣方案！
			return;
		}
		for(var i = 0 ; i<selections.length ; i++){			
			expressDiscountDetailIds.push(selections[i].get('id'));
		}
		if(expressDiscountDetailIds.length<1){
			pricing.showErrorMes(pricing.expressDiscount.i18n('foss.pricing.expressDiscount.pleaseChooseAtLeastOneDiscountDetial'));//请至少选择一条未激活的折扣方案！
			return;
		}
		//传递数据  wutao
		//需要修改
		pricing.showQuestionMes(pricing.expressDiscount.i18n('foss.pricing.expressDiscount.ifDeleteTheseDiscoutDetailsInfo'),function(e){//是否要删除这些未激活的折扣方案？
			if(e=='yes'){//询问是否删除，是则发送请求
				var params = {'expressDiscountPlanVo':{'expressDiscountDetailIds':expressDiscountDetailIds,'expressDiscountEntity':me.expressDiscountEntity}};
				var successFun = function(json){
					pricing.showInfoMes(json.message);
					me.getPagingToolbar().moveFirst();
				};
				var failureFun = function(json){
					if(Ext.isEmpty(json)){
						pricing.showErrorMes(pricing.expressDiscount.i18n('i18n.pricingRegion.requestTimeOut'));
					}else{
						pricing.showErrorMes(json.message);
					}
				};
				var url = pricing.realPath('deleteExpressDiscountDetail.action'); //删除记录ACTION
				pricing.requestJsonAjax(url,params,successFun,failureFun);
			}
		});
	},
	//信息快递折扣明细信息（用于新增快递折扣方案主信息）
	expressDiscountDeatilAddWindow : null,
	getExpressDiscountDeatilAddWindow : function() {
		if (Ext.isEmpty(this.expressDiscountDeatilAddWindow)) {
			this.expressDiscountDeatilAddWindow = Ext.create('Foss.pricing.expressDiscount.ExpressDiscountDetailAddWindow');
			this.expressDiscountDeatilAddWindow.parent = this;
		}
		this.expressDiscountDeatilAddWindow.expressDiscountEntity = this.expressDiscountEntity;
		return this.expressDiscountDeatilAddWindow;
	},
	//修改快递折扣明细信息（单独仅仅用于修改）
	expressDiscountDeatilUpdateWindow : null,
	getExpressDiscountDeatilUpdateWindow : function() {
		if (Ext.isEmpty(this.expressDiscountDeatilUpdateWindow)) {
			this.expressDiscountDeatilUpdateWindow = Ext.create('Foss.pricing.expressDiscount.ExpressDiscountDetailUpdateWindow');	
			this.expressDiscountDeatilUpdateWindow.parent = this;
		}
		this.expressDiscountDeatilUpdateWindow.expressDiscountEntity = this.expressDiscountEntity;
		return this.expressDiscountDeatilUpdateWindow;
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.columns = [{xtype: 'rownumberer',
			width:40,
			text : pricing.expressDiscount.i18n('i18n.pricingRegion.num')//序号
		},{
			text : pricing.expressDiscount.i18n('i18n.pricingRegion.opra'),//操作
			xtype:'actioncolumn',
			align: 'center',
			width:80,
			items: [{
				iconCls: 'deppon_icons_edit',
                tooltip: pricing.expressDiscount.i18n('foss.pricing.update'),//修改
				width:42,
                handler: function(grid,rowIndex,colIndex) {
                	//获取选中的数据
    				var record=grid.getStore().getAt(rowIndex);
                	var expressDiscountDto = record.data;
                	//传递数据
                	me.getExpressDiscountDeatilUpdateWindow().expressDiscountDto =  expressDiscountDto; //json.expressDiscountPlanVo.expressDiscountDto;//获得数据
					me.getExpressDiscountDeatilUpdateWindow().show();//显示修改窗口
    			}
			},{
				iconCls: 'deppon_icons_delete',
                tooltip: pricing.expressDiscount.i18n('foss.pricing.delete'),//删除
				width:42,
                handler: function(grid, rowIndex, colIndex) {
            		//获取选中的数据
    				var record=grid.getStore().getAt(rowIndex);
            		pricing.showQuestionMes(pricing.expressDiscount.i18n('foss.pricing.expressDiscount.ifDeleteDiscountDetails'),function(e){//是否要删除这个折扣明细？
            			if(e=='yes'){//询问是否删除，是则发送请求
            				var expressDiscountDetailIds = new Array();
            				var expressDiscountDetailsId = record.get('id');//计费规则ID
            				expressDiscountDetailIds.push(expressDiscountDetailsId);
            				var params = {'expressDiscountPlanVo':{'expressDiscountDetailIds':expressDiscountDetailIds,'expressDiscountEntity':me.expressDiscountEntity}};
            				var successFun = function(json){
            					pricing.showInfoMes(json.message);
            					me.getPagingToolbar().moveFirst();
            				};
            				var failureFun = function(json){
            					if(Ext.isEmpty(json)){
            						pricing.showErrorMes(pricing.expressDiscount.i18n('i18n.pricingRegion.requestTimeOut'));//请求超时！
            					}else{
            						pricing.showErrorMes(json.message);
            					}
            				};
            				var url = pricing.realPath('deleteExpressDiscountDetail.action');
            				pricing.requestJsonAjax(url,params,successFun,failureFun);
            			}
            		})
                }
			}]
		},{
			text : pricing.expressDiscount.i18n('foss.pricing.expressDiscount.startRegion'),//出发区域
			dataIndex : 'startRegionName'
		},{
			text : pricing.expressDiscount.i18n('foss.pricing.expressDiscount.arriveRegion'),//到达区域
			dataIndex : 'arriveRegionName'
		},{
			text : pricing.expressDiscount.i18n('foss.pricing.beginningOfTheRange'),//开始范围
			dataIndex : 'leftRange'
		},{
			text : pricing.expressDiscount.i18n('foss.pricing.endOfTheRange'),//结束范围
			dataIndex : 'rightRange'
		},{
			text : pricing.expressDiscount.i18n('foss.pricing.discountRules'),//折扣规则
			dataIndex : 'discountRule',
			renderer:function(value){
				if(value=='chargeWeight'){
					return pricing.expressDiscount.i18n('foss.pricing.expressDiscount.chargeWeight');//'计费重量';
				}
			}
		},{
			text : pricing.expressDiscount.i18n('foss.pricing.product'),//产品
			dataIndex : 'productName'
		},{
			text : pricing.expressDiscount.i18n('foss.pricing.expressDiscount.firstDiscountRate'),//首重折扣
			dataIndex : 'firstDiscountRate'
		},{
			text : pricing.expressDiscount.i18n('foss.pricing.expressDiscount.renewalDiscountRate'),//续重折扣
			dataIndex : 'renewalDiscountRate'
		},{
			text : '续重最低费率',//续重最低费率
			dataIndex : 'continueHeavyLowestRate'
		}];
		me.store = Ext.create('Foss.pricing.expressDiscount.expressDiscountDetailStore',{
			autoLoad : false,
			pageSize : 5,
			listeners: {
				beforeload: function(store, operation, eOpts){
					var queryForm = me.up().getQueryExpressDiscountDetailForm();
					var expressDiscountPlanId = me.up('window').expressDiscountEntity.id;
					if(queryForm!=null){
						Ext.apply(operation,{
							params : {//传递参数去后台，通过查询出折扣主方案明细信息
								'expressDiscountPlanVo.expressDiscountDto.startRegionCode':queryForm.getForm().findField('startRegionCode').getValue(),//出发
								'expressDiscountPlanVo.expressDiscountDto.arriveRegionCode':queryForm.getForm().findField('arriveRegionCode').getValue(),//到达
								'expressDiscountPlanVo.expressDiscountDto.productCode':queryForm.getForm().findField('productCode').getValue(),//产品ID
								'expressDiscountPlanVo.expressDiscountDto.goodsTypeCode':queryForm.getForm().findField('goodsTypeCode').getValue(),//货物类型ID
								'expressDiscountPlanVo.expressDiscountDto.expressDiscountPlanId':expressDiscountPlanId//这块折扣方案主信息ID
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
			text : pricing.expressDiscount.i18n('foss.pricing.expressDiscount.addDetail'),//'新增明细',
			handler :function(){
				me.getExpressDiscountDeatilAddWindow().show();
			} 
		},'-', {
			//删除
			text : pricing.expressDiscount.i18n('foss.pricing.expressDiscount.deleteDetail'),//'删除明细',
			handler :function(){
				me.deleteExpressDiscountDeatil();
			} 
		}],
		me.bbar = me.getPagingToolbar();
		me.callParent([cfg]);
	}
});




/***
 * 用于仅仅只用来显示快递折扣详细信息的GRID
 ***/
Ext.define('Foss.pricing.expressDiscount.ExpressDiscountDetailGridPanel',{
	extend: 'Ext.grid.Panel',
	title :pricing.expressDiscount.i18n('foss.pricing.expressDiscount.discountDetailsQueryResult'),//'查询结果',
	frame: true,
	flex:1,
	autoScroll:true,
    sortableColumns:false,
    enableColumnHide:false,
    enableColumnMove:false,
	stripeRows : true, // 交替行效果
	selType : "rowmodel", // 选择类型设置为：行选择
	emptyText: pricing.expressDiscount.i18n('foss.pricing.theQueryResultIsEmpty'),//查询结果为空
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
		me.columns = [{
			text : pricing.expressDiscount.i18n('foss.pricing.expressDiscount.startRegion'),//出发区域
			dataIndex : 'startRegionName'
		},{
			text : pricing.expressDiscount.i18n('foss.pricing.expressDiscount.arriveRegion'),//到达区域
			dataIndex : 'arriveRegionName'
		},{
			text : pricing.expressDiscount.i18n('foss.pricing.beginningOfTheRange'),//开始范围
			dataIndex : 'leftRange'
		},{
			text : pricing.expressDiscount.i18n('foss.pricing.endOfTheRange'),//结束范围
			dataIndex : 'rightRange'
		},{
			text : pricing.expressDiscount.i18n('foss.pricing.discountRules'),//折扣规则
			dataIndex : 'discountRule',
			renderer:function(value){
				if(value=='chargeWeight'){
					return pricing.expressDiscount.i18n('foss.pricing.expressDiscount.chargeWeight');
				}
			}
		},{
			text : pricing.expressDiscount.i18n('foss.pricing.product'),//产品
			dataIndex : 'productName'
		},{
			text : pricing.expressDiscount.i18n('foss.pricing.expressDiscount.firstDiscountRate'),//首重折扣
			dataIndex : 'firstDiscountRate'
		},{
			text : pricing.expressDiscount.i18n('foss.pricing.expressDiscount.renewalDiscountRate'),//续重折扣
			dataIndex : 'renewalDiscountRate'
		},{
			text : '续重最低费率',
			dataIndex : 'continueHeavyLowestRate'
		}];
		me.store = Ext.create('Foss.pricing.expressDiscount.expressDiscountDetailStore',{
			autoLoad : false,
			pageSize : 5,
			listeners: {
				beforeload: function(store, operation, eOpts){
					var queryForm = me.up().getQueryExpressDiscountDetailForm();
					var expressDiscountPlanId = me.up('window').expressDiscountEntity.id;
					if(queryForm!=null){
						Ext.apply(operation,{
							params : {//传递参数去后台，通过查询出折扣主方案明细信息
								'expressDiscountPlanVo.expressDiscountDto.startRegionCode':queryForm.getForm().findField('startRegionCode').getValue(),//出发
								'expressDiscountPlanVo.expressDiscountDto.arriveRegionCode':queryForm.getForm().findField('arriveRegionCode').getValue(),//到达
								'expressDiscountPlanVo.expressDiscountDto.productCode':queryForm.getForm().findField('productCode').getValue(),//产品ID
								'expressDiscountPlanVo.expressDiscountDto.goodsTypeCode':queryForm.getForm().findField('goodsTypeCode').getValue(),//货物类型ID
								'expressDiscountPlanVo.expressDiscountDto.expressDiscountPlanId':expressDiscountPlanId//这块折扣方案主信息ID
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
		me.selModel = Ext.create('Ext.selection.CheckboxModel',{//带选择框
					mode:'MULTI',
					checkOnly:true
				});
		me.bbar = me.getPagingToolbar();
		me.callParent([cfg]);
	}
});


//------------------------------------------------------折扣方案明细的查询和新增的PANEL布局开始-------------------------------------------------------------
/*******
 * 
 * 快递折扣价格明细FORM表单
 * 
 ****/
Ext.define('Foss.pricing.expressDiscount.ExpressDiscountDetailEditFormPanel',{
	extend : 'Ext.form.Panel',
	title:pricing.expressDiscount.i18n('foss.pricing.expressDiscount.discountDetailsForm'),//新增快递折扣价格明细表单',
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
		columns: 1
	},
	constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		var productEntityList = pricing.expressDiscount.productEntityList;
		var goodTypeList = pricing.expressDiscount.goodTypeList;
		me.items = [{
			name: 'startRegionName',
	        fieldLabel:pricing.expressDiscount.i18n('foss.pricing.expressDiscount.startRegion'),//出发区域
	        allowBlank:false,
	        valueField:'id',
	        displayField:'regionName',
	        xtype:'commonexpresspriceregionselector',
	        colspan : 2,
	        startRegionName:null,
	        startRegionId:null,
	        listeners:{
	        	select:function(comb,records,eOpts){
	        		var record = records[0];
	        		comb.startRegionId = record.get('id');
	        		comb.startRegionName = record.get('regionName'); 
	        	}
	        }
		},{
			name: 'arriveRegionName',
	        fieldLabel:pricing.expressDiscount.i18n('foss.pricing.expressDiscount.arriveRegion'),//目的区域
	        allowBlank:false,
	        xtype : 'commonexpresspriceregionselector',
	        colspan : 2,
	        displayField:'regionName',
	        valueField:'id',
	        arriveRegionName:null,
	        arriveRegionId:null,
	        listeners:{
	        	select:function(comb,records,eOpts){
	        		var record = records[0];
	        		comb.arriveRegionId = record.get('id');
	        		comb.arriveRegionName = record.get('regionName'); 
	        	}
	        }
		},{
			name: 'discountRule',
			queryMode: 'local',
		    displayField: 'discountRuleName',
		    valueField: 'discountRuleCode',
		    editable:false,
		    value:'',
		    store:pricing.getStore(null,null,['discountRuleCode','discountRuleName']
		    ,[{'discountRuleCode':'chargeWeight','discountRuleName':pricing.expressDiscount.i18n('foss.pricing.expressDiscount.chargeWeight')}]),
		    fieldLabel:pricing.expressDiscount.i18n('foss.pricing.discountRules'),//'折扣规则',
	        xtype : 'combo'
		},{
			name: 'productCode',
			queryMode: 'local',
		    displayField: 'name',
		    valueField: 'id',
		    editable:false,
		    productItemId:null,
		    productItemName:null,
		    store:pricing.getStore(null,'Foss.pricing.expressDiscount.ProductEntity',null,productEntityList),
	        fieldLabel: pricing.expressDiscount.i18n('foss.pricing.basicProducts'),//基础产品
	        xtype : 'combo',
	        listeners:{
	        	select:function(comb,records){
	        		comb.productItemId = records[0].get('id');
	        		comb.productItemName = records[0].get('name');
	        	}
	        }
		},{
			name: 'goodsTypeName',
			queryMode: 'local',
		    displayField: 'name',
		    valueField: 'id',
		    editable:false,
		    goodsTypeCodes:null,
		    goodsTypeId:null,
		    goodsTypeName:null,
		    store:pricing.getStore(null,'Foss.pricing.expressDiscount.GoodsTypeEntity',null,goodTypeList),
	        fieldLabel: pricing.expressDiscount.i18n('foss.pricing.cargoType'),//货物类型
	        xtype : 'combo',
	        listeners:{
	        	select:function(comb,records){
	        		comb.goodsTypeCodes = records[0].get('code');
	        		comb.goodsTypeId = records[0].get('id');
	        		comb.goodsTypeName = records[0].get('name');      		
	        	}
	        }
		},{
			xtype:'numberfield',
	        decimalPrecision:2,
	        allowBlank:false,
	        name:'leftRange',
	        fieldLabel:pricing.expressDiscount.i18n('foss.pricing.beginningOfTheRange'),//开始范围
	        step:0.01,
	        maxValue: 99999999.99,
	        minValue: 0 
		},{
			xtype:'numberfield',
	        decimalPrecision:2,
	        name:'rightRange',
	        fieldLabel:pricing.expressDiscount.i18n('foss.pricing.endOfTheRange'),//结束范围
	        step:0.01,
	        maxValue: 99999999.99,
	        minValue: 0 
		},{
			xtype:'numberfield',
	        decimalPrecision:2,
	        allowBlank:false,
	        name:'firstDiscountRate',
	        value:100,
	        fieldLabel:pricing.expressDiscount.i18n('foss.pricing.expressDiscount.firstDiscountRate'),//首重折扣	
	        maxValue: 100,
	        step:0.01,
	        minValue: 0
		},{
			xtype:'numberfield',
	        decimalPrecision:2,
	        allowBlank:false,
	        name:'renewalDiscountRate',
	        value:100,
	        fieldLabel:pricing.expressDiscount.i18n('foss.pricing.expressDiscount.renewalDiscountRate'),//续重折扣	
	        maxValue: 100,
	        step:0.01,
	        minValue: 0
		},{
			xtype:'numberfield',
	        decimalPrecision:1,
	        allowBlank:false,
	        name:'continueHeavyLowestRate',
	        value:0,
	        fieldLabel:'续重最低费率',//	
	        maxValue: 5,
	        step:0.1,
	        minValue: 0
		}];
		me.callParent([cfg]);
	}
});

/****
 * 
 * 定义新增快递折扣价格明细界面
 * 
 ****/      
Ext.define('Foss.pricing.expressDiscount.ExpressDiscountDetailAddWindow',{
	extend : 'Ext.window.Window',
	title: pricing.expressDiscount.i18n('foss.pricing.expressDiscount.addExpressDiscountDetailWindow'),//'明细信息新增窗体',
	closable : true,
	modal : true,
	resizable:false,
	closeAction : 'hide',
	expressDiscountEntity:null,
	parent:null,
	width :380,
	height :500,
	expressPlanDetailDto:null,    //传递参数的实体
	grid:null,																	//父 grid
	listeners:{
		beforehide:function(me){
			me.getExpressDiscountDetailEditFormPanel().getForm().reset();
		},
		beforeshow:function(me){
		}
	},
    //明细信息新增-FORM
	expressDiscountDetailEditFormPanel:null,
    getExpressDiscountDetailEditFormPanel:function(){
    	if(Ext.isEmpty(this.expressDiscountDetailEditFormPanel)){
    		this.expressDiscountDetailEditFormPanel = Ext.create('Foss.pricing.expressDiscount.ExpressDiscountDetailEditFormPanel');
    	}
    	return this.expressDiscountDetailEditFormPanel;
    },
    
    //想GRID中设置数据
    commitExpressDiscountDetail:function(grid){
    	var me = this;
    	//得到明细form
    	var form = me.getExpressDiscountDetailEditFormPanel().getForm();
    	if(form.isValid()){
    		//获取model
    		var expressDiscountDto = new Foss.pricing.expressDiscount.ExpressDiscountDto();
    		form.updateRecord(expressDiscountDto);
    		//开始范围不能大于结束范围！
    		 if(expressDiscountDto.get('leftRange')>=expressDiscountDto.get('rightRange')){
             	pricing.showWoringMessage(pricing.expressDiscount.i18n('foss.pricing.leftRangeMoreRanRightRange'));
             	return;
             } 
    		 //首重折扣和续重折扣不能同时输入100
             if((expressDiscountDto.get('firstDiscountRate') == 100) && (expressDiscountDto.get('renewalDiscountRate')==100)){
            	 pricing.showWoringMessage(pricing.expressDiscount.i18n('foss.pricing.expressDiscount.firstDiscountRateAndRenewalDiscountRateNotEqualOneHundredAtSameTime'));//首重折扣和续重折扣不能同时为100！
             	return;
             }
			//获取明细信息
    		var startRegionCode = form.findField('startRegionName').getValue();
   
    		var arriveRegionCode = form.findField('arriveRegionName').getValue();
    	    
    	    var productItemCode = form.findField('productCode').productItemId;
    	   
    	    var goodsTypeId = form.findField('goodsTypeName').goodsTypeId;
    	    
    	    var goodsTypeCodes = form.findField('goodsTypeName').goodsTypeCodes;
    	
    	    var expressDiscountPlanId = me.expressDiscountEntity.id;
	    	//设置明细信息
	    	expressDiscountDto.set('startRegionCode',startRegionCode);
	    	expressDiscountDto.set('arriveRegionCode',arriveRegionCode);
	    	expressDiscountDto.set('productItemCode',productItemCode);
	    	expressDiscountDto.set('expressDiscountPlanId',expressDiscountPlanId);
	    	expressDiscountDto.set('goodsTypeCode',goodsTypeCodes);
	    	expressDiscountDto.set('goodsTypeId',goodsTypeId);
	    	expressDiscountDto.set('createTime',null);
	    	expressDiscountDto.set('modifyTime',null);
	    	expressDiscountDto.set('billTime',null);
	    	expressDiscountDto.set('beginTime',null);
	    	expressDiscountDto.set('endTime',null);
	    	
			//制定json请求参数
			var params = {'expressDiscountPlanVo':{'expressDiscountDto':expressDiscountDto.data}};
			//ajax请求
			var url = pricing.realPath('addExpressDiscountDetail.action');
			
			//成功提示
			var successFun = function(json){
				pricing.showInfoMes(json.message);
				me.close();
				me.parent.getPagingToolbar().moveFirst();
		    };
		    //失败提示
		    var failureFun = function(json){
		    	if(Ext.isEmpty(json)){
					pricing.showErrorMes(pricing.expressDiscount.i18n('i18n.pricingRegion.requestTimeOut'));
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
		me.items = [me.getExpressDiscountDetailEditFormPanel()];//设置window的元素
		me.fbar = [{
			text : pricing.expressDiscount.i18n('foss.pricing.expressDiscount.returnWindow'),//'返回',
			handler :function(){
				me.close();
			} 
		},{
			text : pricing.expressDiscount.i18n('foss.pricing.expressDiscount.reset'),//重置
			margin:'0 185 0 0',
			handler :function(){
				me.getExpressDiscountDetailEditFormPanel().getForm().reset();
			} 
		},{
			text : pricing.expressDiscount.i18n('foss.pricing.expressDiscount.save'),//提交
			cls:'yellow_button',
			handler :function(){
				me.commitExpressDiscountDetail();
			} 
		}];
		me.callParent([cfg]);
	}
});
/****
 * 
 * 定义修改快递折扣价格明细界面
 * 
 ****/
Ext.define('Foss.pricing.expressDiscount.ExpressDiscountDetailUpdateWindow',{
	extend : 'Ext.window.Window',
	title : pricing.expressDiscount.i18n('foss.pricing.expressDiscount.updateExpressDiscountDetailWindow'),//'修改快递折扣主方案明细界面',
	closable : true,
	modal : true,
	resizable:false,
	expressDiscountEntity:null,
	expressDiscountDto:null, //快递折扣主方案明细DTO
	parent:null,
	closeAction : 'hide',
	width :450,
	height :500,	
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	listeners:{
		beforehide:function(me){
			me.getExpressDiscountDetailEditFormPanel().getForm().reset();
		},
		beforeshow:function(me){
			me.getExpressDiscountDetailEditFormPanel().getForm().loadRecord(new Foss.pricing.expressDiscount.ExpressDiscountDto(me.expressDiscountDto));//设置值
		}
	},
	//维护折扣信息FORM
	expressDiscountDetailEditFormPanel:null,
	getExpressDiscountDetailEditFormPanel:function(){
		if(Ext.isEmpty(this.expressDiscountDetailEditFormPanel)){
    		this.expressDiscountDetailEditFormPanel = Ext.create('Foss.pricing.expressDiscount.ExpressDiscountDetailEditFormPanel');
    	}
    	return this.expressDiscountDetailEditFormPanel;
	},
	//提交修改快递折扣主方案明细信息
	commitExpressDiscountDetailEdit:function(){
		var me = this;
		//得到FORM表单
		var form = me.getExpressDiscountDetailEditFormPanel().getForm();
        if(form.isValid()){
        	//获取修改的原始数据
        	var expressDiscountDtoModel = new Foss.pricing.expressDiscount.ExpressDiscountDto(me.expressDiscountDto);
        	form.updateRecord(expressDiscountDtoModel);  
        	
        	//始发区域
        	var startRegionCode = form.findField('startRegionName').startRegionId;
        	//目的区域  
    		var arriveRegionCode = form.findField('arriveRegionName').arriveRegionId;
    	    //货物类型ID
    	    var goodsTypeId = form.findField('goodsTypeName').goodsTypeId;
    	    //货物类型code
    	    var goodsTypeCodes = form.findField('goodsTypeName').goodsTypeCodes;
    	    //主方案ID
    	    var expressDiscountPlanId = me.expressDiscountEntity.id;
	    	//设置明细信息
    	    expressDiscountDtoModel.set('startRegionCode',startRegionCode);
    	    expressDiscountDtoModel.set('arriveRegionCode',arriveRegionCode);
    	    expressDiscountDtoModel.set('expressDiscountPlanId',expressDiscountPlanId);
    	    expressDiscountDtoModel.set('goodsTypeCode',goodsTypeCodes);
    	    expressDiscountDtoModel.set('goodsTypeId',goodsTypeId);
    	    
    	    expressDiscountDtoModel.set('createTime',null);
    	    expressDiscountDtoModel.set('modifyTime',null);
    	    expressDiscountDtoModel.set('billTime',null);
    	    expressDiscountDtoModel.set('beginTime',null);
    	    expressDiscountDtoModel.set('endTime',null);
            
            //如果开始范围大于结束范围！
            if(expressDiscountDtoModel.get('leftRange')>=expressDiscountDtoModel.get('rightRange')){
            	pricing.showWoringMessage(pricing.expressDiscount.i18n('foss.pricing.leftRangeMoreRanRightRange'));//开始范围不能大于结束范围！
            	return;
            }
            //首重折扣和续重折扣不能同时输入100
            if((expressDiscountDtoModel.get('firstDiscountRate') == 100) && (expressDiscountDtoModel.get('renewalDiscountRate')==100)){
             	pricing.showWoringMessage(pricing.expressDiscount.i18n('foss.pricing.expressDiscount.firstDiscountRateAndRenewalDiscountRateNotEqualOneHundredAtSameTime'));
             	return;
             }
			var params = {'expressDiscountPlanVo':{'expressDiscountDto':expressDiscountDtoModel.data}};
			var successFun = function(json){
				pricing.showInfoMes(json.message);
				me.close();
				me.parent.getPagingToolbar().moveFirst();
			};
			var failureFun = function(json){
				if(Ext.isEmpty(json)){
					pricing.showErrorMes(pricing.expressDiscount.i18n('i18n.pricingRegion.requestTimeOut'));//请求超时！
				}else{
					pricing.showErrorMes(json.message);
				}
			};
			var url = pricing.realPath('updateExpressDiscountDetail.action');
			pricing.requestJsonAjax(url,params,successFun,failureFun);
        }		
	},
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [me.getExpressDiscountDetailEditFormPanel()];
		me.fbar = [{
			text : pricing.expressDiscount.i18n('foss.pricing.expressDiscount.returnWindow'),//'返回',
			handler :function(){
				me.close();
			} 
		},{
			text : pricing.expressDiscount.i18n('foss.pricing.reset'),
			handler :function(){
				me.getExpressDiscountEditFormPanel().getForm().reset();
			} 
		},{
			text : pricing.expressDiscount.i18n('foss.pricing.expressDiscount.save'),//保存
			margin:'0 0 0 185',
			cls:'yellow_button',
			handler :function(){
				me.commitExpressDiscountDetailEdit();
			} 
		}];
		me.callParent([cfg]);
	}
});


/****
 * 
 * 定义立即激活的窗体信息
 * 
 ****/

Ext.define('Foss.pricing.expressDiscount.ImmediatelyActiveTimeWindow',{
	extend:'Ext.window.Window',
	title:pricing.expressDiscount.i18n('foss.pricing.expressDiscount.immediatelyActiveDiscountPlanWindow'),//'立即激活方案',
	width:380,
	height:160,
	closeAction: 'hide' ,
	parent:null,
	expressDiscountEntity:null,			//	实体
	
	//立即激活的FORM表单
	immediatelyActiveFormPanel:null,
	getImmediatelyActiveFormPanel:function(){
		if(Ext.isEmpty(this.immediatelyActiveFormPanel)){
			this.immediatelyActiveFormPanel = Ext.create('Foss.pricing.expressDiscount.ImmediatelyActiveFormPanel');
		}
		return this.immediatelyActiveFormPanel;
	},
	
	listeners:{
		beforeshow:function(me){
			//开始时间
			var showbeginTime = Ext.Date.format(new Date(me.expressDiscountEntity.beginTime), 'Y-m-d');
			//结束时间
    		var showendTime = 	Ext.Date.format(new Date(me.expressDiscountEntity.endTime), 'Y-m-d');
    		//显示
    		var value = pricing.expressDiscount.i18n('foss.pricing.showleftTimeInfo')
			  +showbeginTime+pricing.expressDiscount.i18n('foss.pricing.showmiddleTimeInfo')
			  +showendTime+pricing.expressDiscount.i18n('foss.pricing.showrightEndTimeInfo');
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

/***
 * @author 200945
 * 立即激活功能的Form表单
 * 
 ***/
Ext.define('Foss.pricing.expressDiscount.ImmediatelyActiveFormPanel',{
	extend : 'Ext.form.Panel',
	layout:'column',
	
	activeAction:function(){
		var me = this;
		var form = me.getForm();
		if(form.isValid()){
			//实体：
			var expressDiscountEntity = new Foss.pricing.expressDiscount.expressDiscountEntity();
			//加载实体到对应的Form表单
			form.updateRecord(expressDiscountEntity);
			//设置当前时间
			expressDiscountEntity.set('beginTime',Ext.Date.parse(form.findField('beginTime').getValue(), 'Y-m-d H:i:s'));
			
			var id = me.up('window').expressDiscountEntity.id;
			var createTime = me.up('window').expressDiscountEntity.createTime;
			var endTime = me.up('window').expressDiscountEntity.endTime;
			var modifyTime = me.up('window').expressDiscountEntity.modifyTime;
			//fancy
			expressDiscountEntity.set('id',id);
			expressDiscountEntity.set('createTime',createTime);
			expressDiscountEntity.set('endTime',endTime);
			expressDiscountEntity.set('modifyTime',modifyTime);
			expressDiscountEntity.set('billTime',null);
			//封装对应的参数
    		var params = {'expressDiscountPlanVo':{'expressDiscountEntity':expressDiscountEntity.data}};
    		//立即激活的Action
    		var url = pricing.realPath('activeExpressDiscountPlan.action');
    		
    		var successFun = function(json){
    			pricing.showInfoMes(json.message);
    			me.up('window').hide();
				me.up('window').parent.getPagingToolbar().moveFirst();  			
    	    };
    	    var failureFun = function(json){
    	    	if(Ext.isEmpty(json)){
    				pricing.showErrorMes(pricing.expressDiscount.i18n('i18n.pricingRegion.requestTimeOut'));
    			}else{
    				pricing.showErrorMes(json.message);
    			}
    	    };
    	    //发送AJAX请求
    		pricing.requestJsonAjax(url,params,successFun,failureFun);
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
				fieldLabel:pricing.expressDiscount.i18n('foss.pricing.expressDiscount.effectiveDate'),//生效日期,
				name : 'beginTime',
				xtype : 'datetimefield_date97',
				editable:false,
				time : true,
				allowBlank:false,
				id : 'Foss_expressDiscount_activeEndTime_ID',
				dateConfig: {
					el : 'Foss_expressDiscount_activeEndTime_ID-inputEl'
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
				text : pricing.expressDiscount.i18n('i18n.pricingRegion.determine'),//确认
				handler : function() {
					me.activeAction();
				}
			},{
				xtype : 'button', 
				width:70,
				columnWidth:.15,
				text : pricing.expressDiscount.i18n('i18n.pricingRegion.cancel'),//"取消",
				handler : function() {
					me.up('window').hide();
				}
			}];
		this.callParent(cfg);
        }
});
/****
 * 
 * 定义立即终止的窗体信息
 * 
 ****/
Ext.define('Foss.pricing.expressDiscount.ImmediatelyStopEndTimeWindow',{
	extend: 'Ext.window.Window',
	title: pricing.expressDiscount.i18n('foss.pricing.expressDiscount.immediatelyStopDiscountPlanWindow'),//"立即中止方案",
	width:380,
	height:152,
	closeAction: 'hide' ,
	stopFormPanel:null,
	parent:null,
	
	getStopFormPanel : function(){
    	if(Ext.isEmpty(this.stopFormPanel)){
    		this.stopFormPanel = Ext.create('Foss.pricing.expressDiscount.ImmediatelyStopFormPanel');
    	}
    	return this.stopFormPanel;
    },
    listeners:{
    	beforeshow:function(me){
    		var showbeginTime = Ext.Date.format(new Date(me.expressDiscountEntity.beginTime), 'Y-m-d');
    		var showendTime = 	Ext.Date.format(new Date(me.expressDiscountEntity.endTime), 'Y-m-d');
    		var value = pricing.expressDiscount.i18n('foss.pricing.showleftTimeInfo')
			  +showbeginTime+pricing.expressDiscount.i18n('foss.pricing.showmiddleTimeInfo')
			  +showendTime+pricing.expressDiscount.i18n('foss.pricing.showstopRightEndTimeInfo');
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

/***
 * 
 *立即终止FORM表单
 *
 ***/
Ext.define('Foss.pricing.expressDiscount.ImmediatelyStopFormPanel',{
	extend : 'Ext.form.Panel',
	layout:'column',
	stop:function(){
		var me = this;
    	var form = me.getForm();
    	if(form.isValid()){
    		var expressDiscountEntity = new Foss.pricing.expressDiscount.expressDiscountEntity(me.expressDiscountEntity);
			form.updateRecord(expressDiscountEntity);
			
			expressDiscountEntity.set('endTime',Ext.Date.parse(form.findField('endTime').getValue(), 'Y-m-d H:i:s'));
			var id = me.up('window').expressDiscountEntity.id;
			
			var id = me.up('window').expressDiscountEntity.id;
			var createTime = me.up('window').expressDiscountEntity.createTime;
			var beginTime = me.up('window').expressDiscountEntity.beginTime;
			var modifyTime = me.up('window').expressDiscountEntity.modifyTime;
			//fancy
			expressDiscountEntity.set('id',id);
			expressDiscountEntity.set('createTime',createTime);
			expressDiscountEntity.set('beginTime',beginTime);
			expressDiscountEntity.set('modifyTime',modifyTime);
			expressDiscountEntity.set('billTime',null);
			//时间限制
			if(expressDiscountEntity.get('endTime')<expressDiscountEntity.get('beginTime')) {
				pricing.showWoringMessage(pricing.expressDiscount.i18n('Foss.pricing.expressDiscount.compareTime'));
				return;
			}
			//封装参数
			var params = {'expressDiscountPlanVo':{'expressDiscountEntity':expressDiscountEntity.data}};
    		
			//立即中止ACTION
    		var url = pricing.realPath('stopExpressDiscountPlan.action');
    		
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
				fieldLabel :pricing.expressDiscount.i18n('foss.pricing.suspendTime') ,//'中止日期',
				name : 'endTime',
				xtype : 'datetimefield_date97',
				editable:false,
				time : true,
				id : 'Foss_expressDiscount_stopEndTime_ID',
				dateConfig: {
					el : 'Foss_expressDiscount_stopEndTime_ID-inputEl'
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
				text : pricing.expressDiscount.i18n('i18n.pricingRegion.determine'),//"确认",
				handler : function() {
					
					me.stop();
				}
			},{
				xtype : 'button', 
				width:70,
				columnWidth:.15,
				text : pricing.expressDiscount.i18n('i18n.pricingRegion.cancel'),//"取消",
				handler : function() {
					me.up('window').hide();
				}
			}];
	        me.callParent(cfg);
    }
});

/****
 * 
 * 查看详细信息(所需要显示的Window)
 * 里有一个Tab:一个挂主方案的详细的菜单
 * 			一个用来挂查询主方案明细的菜单
 * 仅仅用于显示
 * 
 ****/
Ext.define('Foss.pricing.expressDiscount.ExpressDiscountDeatilShowWindow',{
	extend : 'Ext.window.Window',
	title : pricing.expressDiscount.i18n('foss.pricing.expressDiscount.expressDiscountShowWindow'),//'查看快递折扣窗体',
	resizable:false,
	expressDiscountEntity:null,//折扣方案主信息 expressDiscountEntity
	channelCodes:null,//渠道信息
	parent:null,
	modal:true,
	closeAction : 'hide',
	width :650,
	height :720,	
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	listeners:{
		beforehide:function(me){
			//
			me.getExpressDiscountShowTab().getExpressDiscountEditFormPanel().getForm().reset();
			me.getExpressDiscountShowTab().getExpressDiscountDetailShowPanel().getQueryExpressDiscountDetailForm().getForm().reset();
			me.getExpressDiscountShowTab().getExpressDiscountDetailShowPanel().getExpressDiscountDetailGridPanel().getStore().removeAll();
		},
		beforeshow:function(me){
			var form = me.getExpressDiscountShowTab().getExpressDiscountEditFormPanel();
			if(!Ext.isEmpty(me.expressDiscountEntity)){
				form.getForm().loadRecord( new Foss.pricing.expressDiscount.expressDiscountEntity(me.expressDiscountEntity));//加载数据
				var channelCodes = me.expressDiscountEntity.channelCodes;
				form.getForm().findField('channelType').setValue(channelCodes);
				/**
				 * dp-foss-dongjialing
				 * 225131
				 */
				var weekCodes = me.expressDiscountEntity.weekCodes;
				var checkBoxs = form.down('checkboxgroup');
				var items = checkBoxs.items;				
				checkBoxs.reset();
				items.each(function(item) {					
					if(weekCodes.search(item.inputValue)!=-1) {
						item.setValue(true);
					}
				});	
			}else{
				pricing.showErrorMes(pricing.expressDiscount.i18n('foss.pricing.expressDiscount.noExpressDiscountPlanInfo'));//没有折扣方案主信息！
				return;
			}
		}
	},
	//显示快递折扣主方案 和查询主方案对应的明细信息
	expressDiscountShowTab:null,
	getExpressDiscountShowTab:function(){
		if(Ext.isEmpty(this.expressDiscountShowTab)){
    		this.expressDiscountShowTab = Ext.create('Foss.pricing.expressDiscount.ExpressDiscountShowTab');
    	}
    	return this.expressDiscountShowTab;
	},
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [me.getExpressDiscountShowTab()];
		me.fbar = [{
			text : pricing.expressDiscount.i18n('foss.pricing.expressDiscount.returnWindow'),//'返回',
			handler :function(){
				me.close();
			} 
		}];
		me.callParent([cfg]);
	}
});
/****
 * @author 200945 wutao
 * 操作的主方案信息（用于修改和添加）
 * 里有一个Tab:一个挂主方案的详细的菜单
 * 			一个用来挂查询主方案明细的菜单
 * 
 ****/
Ext.define('Foss.pricing.expressDiscount.ExpressDiscountDeatilEditorWindow',{
	extend : 'Ext.window.Window',
	title : pricing.expressDiscount.i18n('foss.pricing.expressDiscount.expressDiscountWindow'),//'快递折扣窗体',
	resizable:false,
	expressDiscountEntity:null,//折扣方案主信息
	channelCodes:null,//渠道信息
	parent:null,
	modal:true,
	closeAction : 'hide',
	width :650,
	height :720,	
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	listeners:{
		beforehide:function(me){
			//
			me.getExpressDiscountEditorTab().getExpressDiscountEditFormPanel().getForm().reset();
			me.getExpressDiscountEditorTab().getExpressDiscountDetailEditorPanel().getQueryExpressDiscountDetailForm().getForm().reset();
			me.getExpressDiscountEditorTab().getExpressDiscountDetailEditorPanel().getExpressDiscountDetailGridEditorPanel().getStore().removeAll();
			//关闭后，重新设置
			//me.getExpressDiscountEditorTab().getExpressDiscountDetailEditorPanel().getExpressDiscountDetailGridEditorPanel().expressDiscountEntity = null;
		},
		beforeshow:function(me){ 
			var form = me.getExpressDiscountEditorTab().getExpressDiscountEditFormPanel();
			me.getExpressDiscountEditorTab().getExpressDiscountDetailEditorPanel().getExpressDiscountDetailGridEditorPanel().expressDiscountEntity = me.expressDiscountEntity;
			if(!Ext.isEmpty(me.expressDiscountEntity)){
				form.getForm().loadRecord(new Foss.pricing.expressDiscount.expressDiscountEntity(me.expressDiscountEntity));//加载数据
				var channelCodes = me.expressDiscountEntity.channelCodes;
				form.getForm().findField('channelType').setValue(channelCodes);
				//给老的渠道进行赋值，传递到对应的Form中去
				form.getForm().oldChannelCodes = channelCodes;
				//主价格方案的传递到FORM表单
				form.getForm().expressDiscountEntity = me.expressDiscountEntity;
				/**
				 * dp-foss-dongjialing
				 * 225131
				 */
				var weekCodes = me.expressDiscountEntity.weekCodes;
				var checkBoxs = form.down('checkboxgroup');
				var items = checkBoxs.items;				
				checkBoxs.reset();
				items.each(function(item) {					
					if(weekCodes.search(item.inputValue)!=-1) {
						item.setValue(true);
					}
				});				
			}else{
				pricing.showErrorMes(pricing.expressDiscount.i18n('foss.pricing.expressDiscount.noExpressDiscountPlanInfo'));//没有折扣方案主信息！
				return;
			}
		}
	},
	//显示快递折扣主方案 和查询主方案对应的明细信息
	expressDiscountEditorTab:null,
	getExpressDiscountEditorTab:function(){
		if(Ext.isEmpty(this.expressDiscountEditorTab)){
    		this.expressDiscountEditorTab = Ext.create('Foss.pricing.expressDiscount.ExpressDiscountEditorTab');
    	}
    	return this.expressDiscountEditorTab;
	},
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [me.getExpressDiscountEditorTab()];
		me.fbar = [{
			text : pricing.expressDiscount.i18n('foss.pricing.expressDiscount.returnWindow'),//'返回',
			handler :function(){
				me.close();
			} 
		}];
		me.callParent([cfg]);
	}
});

/**
 * 上传附件弹出框
 */
Ext.define('Foss.pricing.expressDiscount.uploadDiscountform',{
	extend:'Ext.window.Window',
	title:pricing.expressDiscount.i18n('foss.pricing.expressDiscount.importScheme'),
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
	parent:null,//（Foss.pricing.expressDiscount.expressDiscountformGrid）
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
				fieldLabel:pricing.expressDiscount.i18n('foss.pricing.pleaseSelectAttachments'),
				labelWidth:100,
				buttonText:pricing.expressDiscount.i18n('foss.pricing.browse'),
				flex:3
			}]
		}];
		me.fbar = me.getFbar();
		me.callParent([cfg]);
	},
	getFbar:function(){
		var me = this;
		return [{
			text:pricing.expressDiscount.i18n('foss.pricing.import'),
			xtype:'button',
			scope:me,
			handler:me.uploadFile
		},{
			text:pricing.expressDiscount.i18n('i18n.pricingRegion.cancel'),
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
			if(Ext.isEmpty(json.expressDiscountPlanVo.numList)){
				pricing.showInfoMes(pricing.expressDiscount.i18n('foss.pricing.allDataImportSuccess'));//全部数据导入成功！
				me.close();
			}else{
				var message = pricing.expressDiscount.i18n('foss.pricing.first');//第
				for(var i = 0;i<json.expressDiscountPlanVo.numList;i++){
					message = message+json.expressDiscountPlanVo.numList[i]+','
				}
				message = message+pricing.expressDiscount.i18n('foss.pricing.lineImportSuccess');
				pricing.showWoringMessage(message);
			}
		};
		var failureFn = function(json){
			if(Ext.isEmpty(json)){
				pricing.showErrorMes(pricing.expressDiscount.i18n('i18n.pricingRegion.requestTimeOut'));//pricing.expressDiscount.i18n('i18n.pricingRegion.requestTimeOut')
			}else{
				pricing.showErrorMes(json.message);
			}
		};
		var form = me.down('form').getForm();
		var url = pricing.realPath('importDiscount.action');
		form.submit({
            url: url,
			timeout:60000,   
            waitMsg: pricing.expressDiscount.i18n('foss.pricing.uploadYourAttachment'),
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
 * @author 200945
 * @deprecated:加载快递合同客户折扣信息主页面
 */
Ext.onReady(function(){
	Ext.QuickTips.init();
	if (Ext.getCmp('T_pricing-expressDiscount_content')) {
		return;
	}
	
	pricing.searchGoodTypeList();//货物类型
	pricing.searchProductEntityList();//产品类型
	pricing.haveServerNowTime();//加载获取当前时间
	
	var queryExpressDiscountForm = Ext.create('Foss.pricing.expressDiscount.QueryExpressDiscountForm');//查询条件
	var expressDiscountGridPanel = Ext.create('Foss.pricing.expressDiscount.ExpressDiscountGridPanel');//查询结果

	Ext.getCmp('T_pricing-expressDiscount').add(Ext.create('Ext.panel.Panel', {
	  	id : 'T_pricing-expressDiscount_content',
		cls : 'panelContentNToolbar',
		bodyCls : 'panelContentNToolbar-body',
		//获得查询FORM
		getQueryExpressDiscountForm : function() {
			return queryExpressDiscountForm;
		},
		//获得查询结果GRID
		getAreaGrid : function() {
			return expressDiscountGridPanel;
		},
		items : [ queryExpressDiscountForm, expressDiscountGridPanel]
	}));
});




