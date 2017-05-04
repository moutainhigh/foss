/**
 *快递价格方案页面
 *@date 2013-8-2 下午1:59:18
 *@author 094463-foss-xieyantao
 * 
 */

/**
 * 
 * @type String 激活
 */
pricing.expressPricePlan.yes = 'Y';
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

//消息提醒框
pricing.expressPricePlan.showWarningMsg = function(title,message,fun){
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
	pricing.expressPricePlan.showWoringMessage = function(message,fun) {
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
			Ext.Msg.alert(pricing.expressPricePlan.i18n('foss.pricing.promptMessage'),result.message);
		}
	});
};

/**
 * 价格方案明细模型
 */
Ext.define('Foss.pricing.expressPricePlan.PricePlanDetailDtoModel', {
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
    	name : 'selfPickUp',//是否自提
        type : 'string'
    },{
    	name : 'priceDetailId',//计价明细ID(首重)
        type : 'string'
    },{
    	name : 'weightOnline',//重量上限(首重)
        type : 'number'
    },{
    	name : 'weightDownline',//重量下限（首重）
        type : 'number'
    },{
    	name : 'firstFee',//价格（首重）
        type : 'number'
    },{
    	name : 'oneDetailId',//计价明细ID(续重1)
        type : 'string'
    },{
    	name : 'weightOnlineOne',//重量上限(续重1)
        type : 'number'
    },{
    	name : 'weightDownlineOne',//重量下限(续重1)
        type : 'number'
    },{
    	name : 'feeRateOne',//费率(续重1)
        type : 'number'
    },{
    	name : 'twoDetailId',//计价明细ID(续重2)
        type : 'string'
    },{
    	name : 'weightOnlineTwo',//重量上限(续重2)
        type : 'number'
    },{
    	name : 'weightDownlineTwo',//重量下限(续重2)
        type : 'number'
    },{
    	name : 'feeRateTwo',//费率(续重2)
        type : 'number'
    },{
        name : 'remark',//备注
        type : 'string'
    },{
        name : 'pricePlanId',//
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
 * 
 * 货物类型定义-通用
 */
pricing.GOODS_TYPE_ALL = 'H00001'

 
pricing.searchProductItemEntityList = function(){
	Ext.Ajax.request({
		url:pricing.realPath('queryExpressProductItemLevel2.action'),//查询产品条目
		async:false,
		success:function(response){
			var result = Ext.decode(response.responseText);
			pricing.queryProductItemEntityList = result.productItemVo.productItemEntityList;
			var countries = pricing.queryProductItemEntityList;
			var arrays = new Array(); 
			var nullproduct = {
				name:pricing.expressPricePlan.i18n('i18n.pricingRegion.all'),//"全部",
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
				pricing.showErrorMes(pricing.expressPricePlan.i18n('i18n.pricingRegion.requestTimeOut'));
			}else{
				pricing.showErrorMes(result.message);
			}
		}
	});
};


pricing.searchProductItemEntityAddList = function(){
	Ext.Ajax.request({
//		url:pricing.realPath('queryProductItemLevel2.action'),//查询产品条目
		url:pricing.realPath('queryExpressProductItemLevel2.action'),//查询产品条目
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
				pricing.showErrorMes(pricing.expressPricePlan.i18n('i18n.pricingRegion.requestTimeOut'));
			}else{
				pricing.showErrorMes(result.message);
			}
		}
	});
};

/**
 * 价格方案批次model
 */
Ext.define('Foss.pricing.expressPricePlan.PricePlanModel', {
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
        name : 'descNote',
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
    },{
    	name:'customerName',
    	type:'string'
    },{
    	name:'customerCode',
    	type:'string'
    }]
});

/**
 * 价格方案批次store
 */
Ext.define('Foss.pricing.expressPricePlan.PricePlanStore',{
	extend: 'Ext.data.Store',
	model: 'Foss.pricing.expressPricePlan.PricePlanModel',
	pageSize:20,
	proxy: {
		type : 'ajax',
		actionMethods:'POST',
		url: pricing.realPath('queryPricePlanBatchInfoA.action'),
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
					'priceManageMentVo.pricePlanEntity.endTime'	: n.endTime,
					'priceManageMentVo.pricePlanEntity.customerCode' : n.customerCode
				}
			});			
		}
	}
});
/**
 * 价格方案明细store
 * queryPricePlanDetailInfoA
 */
Ext.define('Foss.pricing.expressPricePlan.PricePlanDeatilStore',{
	extend: 'Ext.data.Store',
	model: 'Foss.pricing.expressPricePlan.PricePlanDetailDtoModel',
	pageSize:10,
	proxy: {
		type : 'ajax',
		actionMethods:'POST',
		url: pricing.realPath('queryPricePlanDetailInfoA.action'),
		reader : {
			type : 'json',
			root : 'priceManageMentVo.detailDtoList',
			totalProperty : 'totalCount',
			successProperty: 'success'
		}
	}
});


/**
 * 价格方案批次查询form表单
 */
Ext.define('Foss.pricing.expressPricePlan.PricePlanFormPanel', {
	extend : 'Ext.form.Panel',
	title: pricing.expressPricePlan.i18n('i18n.pricingRegion.searchCondition'),
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
		fieldLabel:pricing.expressPricePlan.i18n('foss.pricing.scenarioName'),//方案名称
		name: 'name',
        maxLength : 60,
        maxLengthText:pricing.expressPricePlan.i18n('foss.pricing.theProgramNameLengthExceedsTheMaximumLimit'),
	    allowBlank:true,
	    columnWidth:.3
	},{
		xtype: 'container',
		columnWidth:.2,
		html: '&nbsp;'
	},{
		name: 'priceRegionCode',
	    fieldLabel:pricing.expressPricePlan.i18n('foss.pricing.originatingArea'),//始发区域
	    xtype : 'commonexpresspriceregionselector',
	    columnWidth:.3
	},{
		xtype:'datefield',
		fieldLabel:pricing.expressPricePlan.i18n('foss.pricing.availabilityDate'),//生效日期
		format:'Y-m-d',
		name:'beginTime',
		columnWidth:.3
	},{
		xtype: 'container',
		columnWidth:.2,
		html: '&nbsp;'
	},{
		xtype:'datefield',
		fieldLabel:pricing.expressPricePlan.i18n('foss.pricing.deadline'),//截止日期
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
	    ,[{'key':'N','value':pricing.expressPricePlan.i18n('i18n.pricingRegion.unActive')},{'key':'Y','value':pricing.expressPricePlan.i18n('i18n.pricingRegion.active')}
	    ,{'key':'ALL','value':pricing.expressPricePlan.i18n('i18n.pricingRegion.all')}]),
	    fieldLabel: pricing.expressPricePlan.i18n('foss.pricing.status'),//状态
	    xtype : 'combo'
	},{
		xtype: 'container',
		columnWidth:.2,
		html: '&nbsp;'
	},{
		xtype : 'commoncustomerselector',
		name: 'customerCode',
	    fieldLabel:pricing.expressPricePlan.i18n('foss.pricing.customerName'),//客户名称
	    isPaging:true,
		singlePeopleFlag:'Y',
		all:'true',
	    columnWidth:.3
	}];
	me.fbar = [{
			xtype : 'button', 
			width:70,
			left : 1,
			text : pricing.expressPricePlan.i18n('foss.pricing.reset'),//重置
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
				text : pricing.expressPricePlan.i18n('i18n.pricingRegion.search'),//"查询",
//				hidden: !pricing.expressPricePlan.isPermission('pricePlan/pricePlanQueryButton'),
				handler : function() {
					var beginTime = me.getForm().findField('beginTime').getValue();
					var endTime = me.getForm().findField('endTime').getValue();
					if(!Ext.isEmpty(beginTime) && !Ext.isEmpty(endTime)){
						if(beginTime>endTime){
							pricing.showWoringMessage(pricing.expressPricePlan.i18n('foss.pricing.deadlineForInputGreaterThanEfectiveDate'));//截止日期要大于起始日期！
			    			return;
						}
					}
					var grid = Ext.getCmp('T_pricing-expressPricePlanIndex_content').getPricePlanGridPanel();
					grid.getPagingToolbar().moveFirst();
				}
			}]
			},{
				xtype: 'displayfield',
				columnWidth : .15,
				value:pricing.expressPricePlan.i18n('foss.pricing.specialP')//根据业务日期查询介于生效日期和截止日期之间的增值服务
		}];
		me.callParent([cfg]);
	}
});

/**
 * 价格方案批次列表gird
 */
Ext.define('Foss.pricing.expressPricePlan.PricePlanGridPanel',{
	extend: 'Ext.grid.Panel',
	title : pricing.expressPricePlan.i18n('i18n.pricingRegion.searchResults'),//查询结果
	emptyText: pricing.expressPricePlan.i18n('foss.pricing.theQueryResultIsEmpty'),//查询结果为空
	frame: true,
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
			this.addPricePlanWindow = Ext.create('Foss.pricing.expressPricePlan.PricePlanAddWindow');
			this.addPricePlanWindow.parent = this;
		}
		return this.addPricePlanWindow;
	},
	
	updatePricePlanWindow : null,
	//获取修改价格方案窗口
	getUpdatePricePlanWindow : function(){
		if(Ext.isEmpty(this.updateStandardWindow)){
			this.updatePricePlanWindow = Ext.create('Foss.pricing.expressPricePlan.PricePlanUpdateWindow');
			//设置器父元素
			this.updatePricePlanWindow.parent = this;
		}
		return this.updatePricePlanWindow;
	},
	
	
	//中止方案弹出日期选择
	stopPricePlanWindow:null,
	getStopPricePlanWindow: function(pricePlanId){
		if(Ext.isEmpty(this.stopPricePlanWindow)){
			this.stopPricePlanWindow = Ext.create('Foss.pricing.expressPricePlan.PricePlanStopEndTimeWindow');
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
			this.immediatelyActivePricePlanWindow = Ext.create('Foss.pricing.expressPricePlan.PricePlanImmediatelyActiveTimeWindow');
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
			this.immediatelyStopPricePlanWindow = Ext.create('Foss.pricing.expressPricePlan.PricePlanImmediatelyStopEndTimeWindow');
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
	 	if(selections[0].get('active')!=pricing.expressPricePlan.yes){
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
    immediatelyActivePricePlan:function(){
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
	 	if(selections[0].get('active')==pricing.expressPricePlan.yes){
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
			this.pricePlanStore = Ext.create('Foss.pricing.expressPricePlan.PricePlanStore');
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
				pageSize : 20,
				prependButtons: true,
				defaults : {
					margin : '0 0 15 3'
				}
			});
		}
		return this.pagingToolbar;
	},
	//查看详情界面
	pricePlanDetailShowWindow:null,
	getPricePlanDetailShowWindow:function(){
		if(Ext.isEmpty(this.pricePlanDetailShowWindow)){
			this.pricePlanDetailShowWindow = Ext.create('Ext.pricing.expressPricePlan.PricePlanDetailShowWindow');	
			this.pricePlanDetailShowWindow.parent = this;
		}
		return this.pricePlanDetailShowWindow;
	},
	
	//删除价格方案
	deletePricePlan:function(){
	 	var me = this;
	 	var selections = me.getSelectionModel().getSelection();
	 	if(selections.length < 1){
	 		pricing.showWoringMessage(pricing.expressPricePlan.i18n('foss.pricing.pleaseSelectOneDeleteOperation'));
			return;
	 	}
	 	//过滤草稿状态数据进行删除
	 	for(var i = 0;i<selections.length;i++){
			if(selections[i].get('active')==pricing.expressPricePlan.yes){
				pricing.showWoringMessage(pricing.expressPricePlan.i18n('foss.pricing.pleaseChooseToNotActivateTheDataToBeBeleted'));
				return;
			}
		}
		//是否要删除这些汽运价格方案？
		pricing.showQuestionMes(pricing.expressPricePlan.i18n('foss.pricing.theseTheQiyunPriceProgramYouWantToRemove'),function(e){
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
						pricing.showErrorMes(pricing.expressPricePlan.i18n('foss.pricing.requestTimedOut'));//请求超时
					}else{
						pricing.showErrorMes(json.message);
					}
				};
				var url = pricing.realPath('deletePricePlanA.action');
				pricing.requestJsonAjax(url,params,successFun,failureFun);
			}
		});
	},
	
	//激活方案
    activePricePlan:function(){
    	var me = this;
		var pricePlans = new Array();
		//获取选中的数据
		var selections = me.getSelectionModel().getSelection();
		if(selections.length<1){
			pricing.showErrorMes(pricing.expressPricePlan.i18n('foss.pricing.pleaseChooseThePriceYouWantToActivateTheProgram'));//请选择要激活的价格方案！
			return;
		}
		//过滤草稿状态数据进行激活
	 	for(var i = 0;i<selections.length;i++){
			if(selections[i].get('active')==pricing.expressPricePlan.yes){
				pricing.showWoringMessage(pricing.expressPricePlan.i18n('foss.pricing.pleaseSelectNotActivateProgramDataForActivation'));
				return;
			}
			pricePlans.push(selections[i].get('id'));
		}
		if(pricePlans.length<1){
			pricing.showErrorMes(pricing.expressPricePlan.i18n('foss.pricing.pleaseSelectAtLeastOneInactivePriceProgram'));//请至少选择一条未激活的价格方案！
			return;
		}
		//是否要激活这些快递价格方案？
		pricing.showQuestionMes('是否要激活这些快递价格方案？',function(e){
			if(e=='yes'){
				var params = {'priceManageMentVo':{'pricePlanIds':pricePlans}};
				var successFun = function(json){
					pricing.showInfoMes(json.message);
					me.getPagingToolbar().moveFirst();
				};
				var failureFun = function(json){
					if(Ext.isEmpty(json)){
						pricing.showErrorMes(pricing.expressPricePlan.i18n('i18n.pricingRegion.requestTimeOut'));//请求超时
					}else{
						pricing.showErrorMes(json.message);
					}
				};
				var url = pricing.realPath('activePricePlanA.action');
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
			this.uploadPriceform = Ext.create('Foss.pricing.expressPricePlan.UploadPriceform');	
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
			this.queryPricePlanForm  = Ext.create('Foss.pricing.expressPricePlan.PricePlanFormPanel')
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
			text : pricing.expressPricePlan.i18n('i18n.pricingRegion.num')//序号 
		},{
			text : pricing.expressPricePlan.i18n('i18n.pricingRegion.opra'),
			align : 'center',
			xtype : 'actioncolumn',
			items: [{
				iconCls: 'deppon_icons_showdetail',
                tooltip: pricing.expressPricePlan.i18n('foss.pricing.details'), 
//                disabled: !pricing.expressPricePlan.isPermission('pricePlan/pricePlanQueryDetailButton'),
				width:42,
                handler: function(grid, rowIndex, colIndex) {
    				var record=grid.getStore().getAt(rowIndex);
                	me.getPricePlanDetailShowWindow().show();
                	me.getPricePlanDetailShowWindow().pricePlanId = record.get('id');
                }
			},{
				iconCls:'deppon_icons_edit',
				tooltip: pricing.expressPricePlan.i18n('foss.pricing.toAmendTheProposal'), 
				disabled: !pricing.expressPricePlan.isPermission('expressPricePlan/updateButton'),
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
    						pricing.showErrorMes(pricing.expressPricePlan.i18n('foss.pricing.requestTimedOut'));//请求超时
    					}else{
    						pricing.showErrorMes(json.message);
    					}
    				};
    				var url = pricing.realPath('queryPricePlanAndDetailInfoByIdA.action');
    				pricing.requestJsonAjax(url,params,successFun,failureFun);
				}
			},{
				iconCls:'deppon_icons_softwareUpgrade',
				tooltip: pricing.expressPricePlan.i18n('foss.pricing.replicationScheme'), 
				disabled: !pricing.expressPricePlan.isPermission('expressPricePlan/pricePlanCopyButton'),
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
						pricing.showErrorMes(pricing.expressPricePlan.i18n('foss.pricing.pleaseSelectTheProgramHaBbeenActivatedCopy'));
					}else{
						pricing.showQuestionMes(pricing.expressPricePlan.i18n('foss.pricing.toDeterminePriceProgramCopy'),function(e){
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
										pricing.showErrorMes(pricing.expressPricePlan.i18n('foss.pricing.requestTimedOut'));//请求超时
									}else{
										pricing.showErrorMes(json.message);
									}
								};
								var url = pricing.realPath('copyPricePlanA.action');
								pricing.requestJsonAjax(url,params,successFun,failureFun);
							}
						});
					}
				}
			},{
				iconCls:'deppon_icons_end',
				tooltip: pricing.expressPricePlan.i18n('foss.pricing.stop'), 
				disabled: !pricing.expressPricePlan.isPermission('expressPricePlan/pricePlanStopButton'),
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
		    text:pricing.expressPricePlan.i18n('foss.pricing.customerName'),//客户名称
		    dataIndex:'customerName'
		    
		},{
			width: 140,
		    text:pricing.expressPricePlan.i18n('foss.pricing.customerCode'),//客户名称
		    dataIndex:'customerCode'
		},{
			width: 140,
			text: pricing.expressPricePlan.i18n('foss.pricing.scenarioName'), //"方案名称", 
	        dataIndex: 'name'
		},{
			width: 140,
			text: pricing.expressPricePlan.i18n('foss.pricing.theOriginatingRegion'),//"始发区域",
	        dataIndex: 'priceRegionName'
		},{
			text: pricing.expressPricePlan.i18n('foss.pricing.updateTime'),//"修改时间",
			width: 120,
	        dataIndex: 'modifyDate',
	        renderer:function(value){
				return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
			}
		},{
			text: pricing.expressPricePlan.i18n('foss.pricing.modifyUser'),//"修改人",
			width: 80,
	        dataIndex: 'modifyUserName'
		},{
			text: pricing.expressPricePlan.i18n('foss.pricing.availabilityDate'),//"生效日期",
	        width: 100,
	        dataIndex: 'beginTime',
	        renderer:function(value){
				return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
			}
		},{
			text: pricing.expressPricePlan.i18n('foss.pricing.endTimeTwo'),//"截止日期",
	        width: 100,
	        dataIndex: 'endTime',
	        renderer:function(value){
				return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
			}
		},{
			width: 80,
			text: pricing.expressPricePlan.i18n('foss.pricing.dataType'),//"数据状态",
	        sortable: true,
	        dataIndex: 'active',
	        renderer:function(value){
				if(value=='Y'){//'Y'表示激活
					return pricing.expressPricePlan.i18n('i18n.pricingRegion.active')//"已激活";
				}else if(value=='N'){//'N'表示未激活
					return  pricing.expressPricePlan.i18n('i18n.pricingRegion.unActive')//"未激活";
				}else{
					return '';
				}
			}
		},{
			text : '是否当前版本',
			dataIndex : 'currentUsedVersion',
			flex:1
		}];
		me.tbar = [
		{
			text : pricing.expressPricePlan.i18n('foss.pricing.theNewScheme'),//"新建方案",
			disabled: !pricing.expressPricePlan.isPermission('expressPricePlan/pricePlanAddButton'),
			hidden: !pricing.expressPricePlan.isPermission('expressPricePlan/pricePlanAddButton'),
			handler :function(){
				var addWindow = me.getAddpricePlanWindow();
				addWindow.isUpdate = false;
				addWindow.show();
				
			} 
		},'-', {
			text : pricing.expressPricePlan.i18n('foss.pricing.activationProgram'),
			disabled: !pricing.expressPricePlan.isPermission('expressPricePlan/pricePlanActiveButton'),
			hidden: !pricing.expressPricePlan.isPermission('expressPricePlan/pricePlanActiveButton'),
			handler :function(){
				me.activePricePlan();
			} 
		},'-', {
			text : pricing.expressPricePlan.i18n('foss.pricing.deleteProgram'),
			disabled: !pricing.expressPricePlan.isPermission('expressPricePlan/pricePlanDeleteButton'),
			hidden: !pricing.expressPricePlan.isPermission('expressPricePlan/pricePlanDeleteButton'),
			handler :function(){
				me.deletePricePlan();
			} 
		},'-', {
			text : pricing.expressPricePlan.i18n('foss.pricing.immediatelyActivationProgram'),//'立即激活',
			disabled:!pricing.expressPricePlan.isPermission('expressPricePlan/pricePlanImmediatelyActiveButton'),
			hidden:!pricing.expressPricePlan.isPermission('expressPricePlan/pricePlanImmediatelyActiveButton'),
			handler :function(){
				me.immediatelyActivePricePlan();
			} 
		},'-', {
			text : pricing.expressPricePlan.i18n('foss.pricing.immediatelyStopProgram'),//'立即中止',
			disabled:!pricing.expressPricePlan.isPermission('expressPricePlan/pricePlanImmediatelyStopButton'),
			hidden:!pricing.expressPricePlan.isPermission('expressPricePlan/pricePlanImmediatelyStopButton'),
			handler :function(){
				me.immediatelyStopPricePlan();
			} 
		},'-',{
			text : pricing.expressPricePlan.i18n('foss.pricing.export'),
			disabled:!pricing.expressPricePlan.isPermission('expressPricePlan/pricePlanExportButton'),
			hidden:!pricing.expressPricePlan.isPermission('expressPricePlan/pricePlanExportButton'),
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
				var url = pricing.realPath('exportPricePlanA.action');
				if(!Ext.isEmpty(pricePlanExport)){
					url = url+'?'+pricePlanExport;
				}
				window.location=url;
				pricePlanExport = '';
 			} 
		},'-', {
			text : pricing.expressPricePlan.i18n('foss.pricing.import'),
			disabled:!pricing.expressPricePlan.isPermission('expressPricePlan/pricePlanImportButton'),
			hidden:!pricing.expressPricePlan.isPermission('expressPricePlan/pricePlanImportButton'),
			handler :function(){
				pricing.expressPricePlan.showWoringMessage(pricing.expressPricePlan.i18n('foss.pricing.importWoring'),function(e) {
					me.getUploadPriceform().show();
				});
			 	
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
Ext.define('Ext.pricing.expressPricePlan.PricePlanDetailShowWindow',{
	extend : 'Ext.window.Window',
	title: pricing.expressPricePlan.i18n('foss.pricing.thePriceOfTheProgramDetailQuery'),//始发区域与目的区域时效方案明细查询
	closable : true,
	modal : true,
	resizable:false,
	closeAction : 'hide',
	parent:null,//(Foss.pricing.expressPricePlan.PricePlanGridPanel)
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
    		this.queryPricePlanDetailForm = Ext.create('Foss.pricing.expressPricePlan.QueryPricePlanDetailForm');
    	}
    	return this.queryPricePlanDetailForm;
    },
    //明细信息结果集
    pricePlanDetailShowGridPanel:null,
    getPricePlanDetailShowGridPanel:function(){
    	if(Ext.isEmpty(this.pricePlanDetailShowGridPanel)){
    		this.pricePlanDetailShowGridPanel = Ext.create('Foss.pricing.expressPricePlan.PricePlanDetailShowGridPanel');
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
Ext.define('Foss.pricing.expressPricePlan.QueryPricePlanDetailForm', {
	extend : 'Ext.form.Panel',
	title: pricing.expressPricePlan.i18n('i18n.pricingRegion.searchCondition'),
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
//		    store : Ext.create('Foss.pricing.expressPricePlan.ExpressPricePlanDetailFormProductStore', {
//				data : {
//					'items' : [{
//							'id':'ExpressPricePlanDetailFormProductStoreId',
//							'code' : 'jjkd',
//							'name' : '快递-标准快递'
//						},{
//							'id':'ExpressPricePlanDetailFormProductStoreId360',
//							'code':'EXPRESS_RCP_ITEM',
//							'name':'快递-3.60特惠件'
//						},{
//							'id':'ExpressPricePlanDetailFormProductStoreIdEPEP',
//							'code':'EXPRESS_EPEP',
//							'name':'快递-电商尊享'
//						},{
//							'id':'ExpressPricePlanDetailFormProductStoreIdAll',
//							'code':'',
//							'name':'全部'
//						}
//					]
//				}
//			}),
	        fieldLabel:pricing.expressPricePlan.i18n('foss.pricing.productEntry'),//条目
	        value:'',
	        xtype : 'combo'
		},{
			name: 'arrvRegionId',
			valueField: 'id',
	        fieldLabel:pricing.expressPricePlan.i18n('foss.pricing.destinationArea'),//目的区域
	        xtype : 'commonexpresspriceregionselector'
		},{
			xtype : 'container',
			columnWidth : .2,
			margin : '0 0 0 0',
			items : [{
				xtype : 'button', 
				width:70,
				text : pricing.expressPricePlan.i18n('i18n.pricingRegion.search'),
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
Ext.define('Foss.pricing.expressPricePlan.PricePlanDetailShowGridPanel', {
	extend: 'Ext.grid.Panel',
	title :pricing.expressPricePlan.i18n('i18n.pricingRegion.searchResults'),//查询结果
	frame: true,
	height :450,
    sortableColumns:false,
    enableColumnHide:false,
    enableColumnMove:false,
	stripeRows : true, // 交替行效果
	selType : "rowmodel", // 选择类型设置为：行选择
	emptyText: pricing.expressPricePlan.i18n('foss.pricing.theQueryResultIsEmpty'),//查询结果为空
	
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
			text : pricing.expressPricePlan.i18n('i18n.pricingRegion.num')//序号
		},{
			text: pricing.expressPricePlan.i18n('foss.pricing.destinationArea'),//目的区域
			width: 120,
	        dataIndex: 'arrvRegionName'
		},{
			text: pricing.expressPricePlan.i18n('foss.pricing.productEntryName'),// "产品条目名称",
			width: 90,
	        dataIndex: 'productItemName'
		},{
			text :pricing.expressPricePlan.i18n('foss.pricing.firstWeight'),//改为首重
			dataIndex : 'firstFee',
			//DN201510290016 DP-FOSS-快递价格方案首重价格输入优化需求
			//2015/12/3 liding add
			decimalPrecision : 1, 
			allowDecimals : true, 
			renderer:function(value){
				if(!Ext.isEmpty(value)){
					//DN201510290016 DP-FOSS-快递价格方案首重价格输入优化需求
					//2015/12/3 liding mod
					//return value;
					return parseFloat(value).toFixed(1); 
				}
			}
		},{
		    text : pricing.expressPricePlan.i18n('foss.pricing.continuedWeight1'),//续重1
		    columns: [{
		    	text : pricing.expressPricePlan.i18n('foss.pricing.weightOffline'),//重量下限
		    	dataIndex: 'weightDownlineOne',
				renderer:function(value){
					if(!Ext.isEmpty(value)){
						return value;
					}
				}
		    }, {
		    	text : pricing.expressPricePlan.i18n('foss.pricing.weightOnline'),//重量上限
		    	dataIndex: 'weightOnlineOne',
				renderer:function(value){
					if(!Ext.isEmpty(value)){
						return value;
					}
				}
		    }, {
		    	text : pricing.expressPricePlan.i18n('foss.pricing.rates'),// 费率
		    	dataIndex: 'feeRateOne',
				renderer:function(value){
					if(!Ext.isEmpty(value)){
						return value;
					}
				}
		    }]
		},{
		    text : pricing.expressPricePlan.i18n('foss.pricing.continuedWeight2'),//续重2
		    columns: [{
		    	text : pricing.expressPricePlan.i18n('foss.pricing.weightOffline'),//重量下限
		    	dataIndex: 'weightDownlineTwo',
				renderer:function(value){
					if(!Ext.isEmpty(value)){
						return value;
					}
				}
		    }, {
		    	text : pricing.expressPricePlan.i18n('foss.pricing.weightOnline'),//重量上限
		    	dataIndex: 'weightOnlineTwo',
				renderer:function(value){
					if(!Ext.isEmpty(value)){
						return value;
					}
				}
		    }, {
		    	text : pricing.expressPricePlan.i18n('foss.pricing.rates'),// 费率
		    	dataIndex: 'feeRateTwo',
				renderer:function(value){
					if(!Ext.isEmpty(value)){
						return value;
					}
				}
		    }]
		}/*,{
			width: 100,
			text: '是否自提',//是否自提,
	        dataIndex: 'selfPickUp',
	        renderer:function(value){
				if(value=='Y'){
					return pricing.expressPricePlan.i18n('i18n.pricingRegion.ye')//"是";
				}else if(value=='N'){
					return pricing.expressPricePlan.i18n('i18n.pricingRegion.no')//"否";
				}else{
					return '';
				}
			}
		}*/];
		me.store = Ext.create('Foss.pricing.expressPricePlan.PricePlanDeatilStore',{
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
			text : pricing.expressPricePlan.i18n('foss.pricing.export'),
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
				var url = pricing.realPath('exportPricePlanDetailA.action');
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
/**
 * 快递价格方案明细form
 */
Ext.define('Foss.pricing.expressPricePlan.ExpressPricePlanDetailForm', {
	extend : 'Ext.form.Panel',
	frame: true,
	height:350,
	collapsible: true,
	isSearchComb:true,
    defaults : {
//    	colspan : 2,
    	margin : '5 5 5 5',
    	labelWidth:90,
    	anchor : '100%'
    },
	defaultType : 'textfield',
	layout: {
        type: 'table',
        columns: 4
    },
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [{
			name: 'arrvRegionCode',
	        fieldLabel:pricing.expressPricePlan.i18n('foss.pricing.destinationArea'),//目的区域
	        allowBlank:false,
	        xtype : 'commonexpresspriceregionselector',
	        colspan : 2,
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
		    colspan : 2,
		    valueField: 'code',
		    productItemId:null,
		    productItemName:null,
		    allowBlank:false,
		    editable:false,
		    store:pricing.getStore(null,null,['id','code','name'],pricing.queryProductItemEntityAddList),
//		    store : Ext.create('Foss.pricing.expressPricePlan.ExpressPricePlanDetailFormProductStore', {
//				data : {
//					'items' : [{
//							'id':'ExpressPricePlanDetailFormProductStoreId',
//							'code' : 'jjkd',
//							'name' : '快递-标准快递'
//						},{
//							'id':'ExpressPricePlanDetailFormProductStoreId360',
//							'code':'EXPRESS_RCP_ITEM',
//							'name':'快递-3.60特惠件'
//						},{
//							'id':'ExpressPricePlanDetailFormProductStoreIdEPEP',
//							'code':'EXPRESS_EPEP',
//							'name':'快递-电商尊享'
//						}
//					]
//				}
//			}),
	        fieldLabel:pricing.expressPricePlan.i18n('foss.pricing.productEntry'),//产品条目
	        xtype : 'combo',
	        listeners:{
	        	select:function(comb,records){
	        		comb.productItemId = records[0].get('id');
//	        		me.getForm().findField('minimumOneTicket').setValue(records[0].get('feeBottom'));
	        	}
	        }
		}/*,{
			 xtype:'radiogroup',
			 vertical:true,
//			 allowBlank:false,
			 colspan : 4,
			 name:'selfPickUps',
			 fieldLabel:'是否自提',
			 layout:'column',
			 defaults:{
	 			width:100
	 		 },
			 items:[{
			 	 xtype:'radio',
			     boxLabel:pricing.expressPricePlan.i18n('i18n.pricingRegion.ye'),//是
			     name:'selfPickUp',	
			     columnWidth:.5,
			     inputValue:'Y'
		     },{
		    	 xtype:'radio',
			     boxLabel:pricing.expressPricePlan.i18n('i18n.pricingRegion.no'),//否
			     name:'selfPickUp',
			     columnWidth:.5,
			     inputValue:'N'
			     }]
		}*/,{
			xtype: 'label',
	        forId: 'myFirstId',
	        text: '首重：'
		},{
			name: 'weightDownline',
			allowBlank:false,
			decimalPrecision:1,
			labelWidth:70,
			maxWidth:220,
			step:0.1,
		    maxValue: 999999.9,
		    minValue:0,
		    value:0,
	        fieldLabel: '重量下限',//重量下限
	        xtype : 'numberfield'
		
		},{
			name: 'weightOnline',
			allowBlank:false,
			decimalPrecision:1,
			labelWidth:70,
			maxWidth:220,
			step:0.1,
		    maxValue: 999999.9,
		    minValue:0,
	        fieldLabel: '重量上限',//重量上限
	        xtype : 'numberfield',
	        listeners:{
	        	blur : function (me, event) {
					var source = me.getValue();
					var target = me.up('form').getForm().findField('weightDownlineOne').setValue(source);
				}
	        }
		
		},{
			name: 'firstFee',
			allowBlank:false,
			//DN201510290016 DP-FOSS-快递价格方案首重价格输入优化需求
			//2015/12/3 liding mod
			decimalPrecision:1,
			labelWidth:60,
			maxWidth:220,
			//DN201510290016 DP-FOSS-快递价格方案首重价格输入优化需求
			//2015/12/3 liding mod
			step:0.1,
		    maxValue: 999999.9,
		    minValue:0,
	        fieldLabel: '价格',//价格
	        xtype : 'numberfield'
		
		},{
			xtype: 'label',
	        forId: 'myOneId',
	        text: '续重1：'
		},{
			name: 'weightDownlineOne',
			allowBlank:false,
			decimalPrecision:1,
			labelWidth:70,
			maxWidth:220,
			step:0.1,
		    maxValue: 999999.9,
		    minValue:0,
	        fieldLabel: '重量下限',//重量下限
	        xtype : 'numberfield',
	        readOnly:true
		
		},{
			name: 'weightOnlineOne',
			allowBlank:false,
			decimalPrecision:1,
			labelWidth:70,
			maxWidth:220,
			step:0.1,
		    maxValue: 999999.9,
		    minValue:0,
	        fieldLabel: '重量上限',//重量上限
	        xtype : 'numberfield',
	        listeners:{
	        	blur : function (me, event) {
					var source = me.getValue();
					var target = me.up('form').getForm().findField('weightDownlineTwo').setValue(source);
				}
	        }
		},{
			name: 'feeRateOne',
			allowBlank:false,
			decimalPrecision:2,
			labelWidth:60,
			maxWidth:220,
			step:0.01,
		    maxValue: 999999.99,
		    minValue:0,
	        fieldLabel: '费率',//价格
	        xtype : 'numberfield'
		
		},{
			xtype: 'label',
	        forId: 'myTwoId',
	        text: '续重2：'
		},{
			name: 'weightDownlineTwo',
			allowBlank:false,
			decimalPrecision:1,
			labelWidth:70,
			maxWidth:220,
			step:0.1,
		    maxValue: 999999.9,
		    minValue:0,
	        fieldLabel: '重量下限',//重量下限
	        xtype : 'numberfield',
	        readOnly:true
		
		},{
			name: 'weightOnlineTwo',
			allowBlank:false,
			decimalPrecision:1,
			labelWidth:70,
			maxWidth:220,
			step:0.1,
		    maxValue: 999999.9,
		    minValue:0,
	        fieldLabel: '重量上限',//重量上限
	        xtype : 'numberfield'
		
		},{
			name: 'feeRateTwo',
			allowBlank:false,
			decimalPrecision:2,
			labelWidth:60,
			maxWidth:220,
			step:0.01,
		    maxValue: 999999.99,
		    minValue:0,
	        fieldLabel: '费率',//费率
	        xtype : 'numberfield'
		
		},{
			xtype: 'textareafield',
			width:600,
			colspan : 4,
		    fieldLabel: pricing.expressPricePlan.i18n('foss.pricing.remark'),//备注
		    maxLength:200,
	        name:'remark'
		}];
		me.callParent([cfg]);
	}
});

/**
 * 价格方案明细信息新增弹出窗口window
 */
Ext.define('Foss.pricing.expressPricePlan.PricePlanDetailWindow',{
	extend : 'Ext.window.Window',
	title: pricing.expressPricePlan.i18n('foss.pricing.aBreakdownOfNew'),//明细信息新增
	closable : true,
	modal : true,
	resizable:false,
	closeAction : 'hide',
	parent:null,
	width :880,
	height :500,
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
    		this.pricePlanDetailForm = Ext.create('Foss.pricing.expressPricePlan.ExpressPricePlanDetailForm');
    	}
    	return this.pricePlanDetailForm;
    },
    
    //想GRID中设置数据
    commitPricePlanDetail:function(grid){
    	var me = this;
    	//得到明细form
    	var form = me.getPricePlanDetailForm().getForm();
    	if(form.isValid()){
    		var pricePlanDetailDto = new Foss.pricing.expressPricePlan.PricePlanDetailDtoModel();
    		form.updateRecord(pricePlanDetailDto);
    		
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
	    	pricePlanDetailDto.set('remark',remark);
	    	pricePlanDetailDto.set('pricePlanId',pricing.pricePlanId);
	    	
			//制定json请求参数
			var params = {'priceManageMentVo':{'expressPricePlanDetailDto':pricePlanDetailDto.data}};
			//ajax请求
			var url = pricing.realPath('addPricePlanDetailA.action');
			
			//成功提示
			var successFun = function(json){
				pricing.showInfoMes(json.message);
				me.close();
				grid.getPagingToolbar().moveFirst();
		    };
		    //失败提示
		    var failureFun = function(json){
		    	if(Ext.isEmpty(json)){
					pricing.showErrorMes(pricing.expressPricePlan.i18n('i18n.pricingRegion.requestTimeOut'));
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
			text : pricing.expressPricePlan.i18n('i18n.pricingRegion.cancel'),//取消
			handler :function(){
				me.close();
			} 
		},{
			text : pricing.expressPricePlan.i18n('foss.pricing.reset'),//重置
			margin:'0 185 0 0',
			handler :function(){
				if(Ext.isEmpty(me.pricePlanDetailDto)){
					me.getPricePlanDetailForm().getForm().loadRecord(new Foss.pricing.expressPricePlan.PricePlanDetailDtoModel());
				}else{
					me.getPricePlanDetailForm().getForm().loadRecord(new Foss.pricing.expressPricePlan.PricePlanDetailDtoModel(me.effectivePlanEntity));
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
Ext.define('Foss.pricing.expressPricePlan.PricePlanAddFormPanel',{
	extend : 'Ext.form.Panel',
	title: pricing.expressPricePlan.i18n('foss.pricing.departureinformation'),//"出发地信息",
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
    		var customerName = baseForm.findField('customerCode').customerName;
    		var customerCode = baseForm.findField('customerCode').customerCode;
    		var customerValue = baseForm.findField('customerCode').value;
    		var pricePlanModel = new Foss.pricing.expressPricePlan.PricePlanModel(pricePlanEntity);
    		if(!Ext.isEmpty(priceRegionId)){
    			pricePlanModel.set('priceRegionId',priceRegionId);	
    		}
    		if(!Ext.isEmpty(priceRegionName)){
    			pricePlanModel.set('priceRegionName',priceRegionName);
    		}
    		if(!Ext.isEmpty(priceRegionCode)){
    			pricePlanModel.set('priceRegionCode',priceRegionCode);	
    		}
    		   if(Ext.isEmpty(customerValue)) {
    			   pricePlanModel.set('customerName',null);	
       			   pricePlanModel.set('customerCode',null);
    		   }else {
    			   if(!Ext.isEmpty(customerName)){
    				   pricePlanModel.set('customerName',customerName);
    			   }
    			   if(!Ext.isEmpty(customerCode)){
    				   pricePlanModel.set('customerCode',customerCode);
    			   }
    		   }
    				
    		
    		
    		baseForm.updateRecord(pricePlanModel);//将FORM中数据设置到MODEL里面
    		var params = {'priceManageMentVo':{'pricePlanEntity':pricePlanModel.data}};//组织需要修改的数据
    		var successFun = function(json){
				pricing.showInfoMes(json.message);//提示新增成功
			};
			var failureFun = function(json){
				if(Ext.isEmpty(json)){
					pricing.showErrorMes(pricing.expressPricePlan.i18n('foss.pricing.requestTimedOut'));//请求超时
				}else{
					pricing.showErrorMes(json.message);//提示失败原因
				}
			};
			var url = pricing.realPath('updatePricePlanA.action');//请求价格方案修改
			pricing.requestJsonAjax(url,params,successFun,failureFun);//发送AJAX请求
    	}
	 },
	
	/***批次保存***/
	commitPricePlan:function(){
		var me = this;
    	//获取表单
    	var form = me.getForm();
    	var pricePlanModel = new Foss.pricing.expressPricePlan.PricePlanModel();
    	if(form.isValid()){
    		form.updateRecord(pricePlanModel);
    		var priceRegionId = form.findField('priceRegionCode').priceRegionId;
    		var priceRegionName = form.findField('priceRegionCode').priceRegionName;
    		var customerName = form.findField('customerCode').customerName;
    		var customerCode = form.findField('customerCode').customerCode;
    		//处理特殊字段
    		pricePlanModel.set('priceRegionId',priceRegionId);
    		pricePlanModel.set('priceRegionName',priceRegionName);
    		pricePlanModel.set('customerName',customerName);
    		pricePlanModel.set('customerCode',customerCode);
    		var params = {'priceManageMentVo':{'pricePlanEntity':pricePlanModel.data}};
    		var url = pricing.realPath('addPricePlanA.action');
    		
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
	    		me.getDockedItems()[1].items.items[1].setDisabled(true);//取消按钮不可用
				me.getDockedItems()[1].items.items[2].setDisabled(true);//重置按钮不可用
				me.getDockedItems()[1].items.items[3].setDisabled(true);//保存按钮不可用
				var dockedItems = me.up('window').getPricePlanDetailGridPanel().getDockedItems();
				dockedItems[1].items.items[0].setDisabled(false);//新增明明细按钮可用
				dockedItems[1].items.items[2].setDisabled(false);//删除明细按钮可用
				//dockedItems[1].items.items[4].setDisabled(false);//导入明细按钮可用
    	    };
    	    
    	    //失败提示
    	    var failureFun = function(json){
    	    	if(Ext.isEmpty(json)){
    				pricing.showErrorMes(pricing.expressPricePlan.i18n('i18n.pricingRegion.requestTimeOut'));
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
    			this.pricePlanDetailGridPanel = Ext.create('Foss.pricing.expressPricePlan.PricePlanDetailGridPanel');
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
	        fieldLabel:pricing.expressPricePlan.i18n('foss.pricing.scenarioName')//方案名称
		},{
			name: 'priceRegionCode',
			allowBlank:false,
	        fieldLabel:pricing.expressPricePlan.i18n('foss.pricing.originatingArea'),//始发区域
	        xtype : 'commonexpresspriceregionselector',
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
			fieldLabel:pricing.expressPricePlan.i18n('foss.pricing.availabilityDate'),//生效日期
			format:'Y-m-d',
			name:'beginTime'
		},{
			name: 'descNote',
			colspan : 2,
	        fieldLabel:pricing.expressPricePlan.i18n('foss.pricing.programDescription'),//方案描述
	        xtype : 'textareafield',
	        maxLength : 200
		},{
			name: 'arrvRegionCode',
	        fieldLabel:pricing.expressPricePlan.i18n('foss.pricing.destinationAreaNew'),//目的区域
	        xtype : 'commonexpresspriceregionselector',
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
			name: 'customerCode',
		    fieldLabel:pricing.expressPricePlan.i18n('foss.pricing.customerName'),//客户名称
		    xtype : 'commoncustomerselector',
		    
				
				
			    
		    customerName:null,
		    customerCode:null,
		    listeners:{
	        	select:function(comb,records,objs){
	        		var record = records[0];
	        		var name = record.get('name');
	        	
	        		var cusCode = record.get('cusCode');
	   
	        		comb.customerName = name;
	        		comb.customerCode = cusCode;
	        	}
        	}
		}],
		me.fbar = [{
			text : pricing.expressPricePlan.i18n('foss.pricing.destinationAreaSearch'),//目的区域查询
			cls:'yellow_button',
			handler :function(){
				me.arrvRegionSearch();
			}  
		},{
			text :pricing.expressPricePlan.i18n('i18n.pricingRegion.cancel'),//取消
			handler :function(){
				me.up('window').isUpdate = null;
				me.up().close();
			}
		},{
			text : pricing.expressPricePlan.i18n('foss.pricing.reset'),//重置
			handler :function(){
					me.getForm().reset();//表格重置
			} 
		},{
			text : pricing.expressPricePlan.i18n('foss.pricing.save'),//保存
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
Ext.define('Foss.pricing.expressPricePlan.PricePlanDetailGridPanel',{
	extend: 'Ext.grid.Panel',
	title : pricing.expressPricePlan.i18n('foss.pricing.destinationInformation'),//"目的地信息",
	frame: true,
	sortableColumns:false,
    enableColumnHide:false,
	selType : "rowmodel", 
	enableColumnMove:false,
	stripeRows:true, 
	border: true,
	height :420,
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
    		this.pricePlanDetailWindow = Ext.create('Foss.pricing.expressPricePlan.PricePlanDetailWindow',{
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
    		this.modifyPricePlanDetailWindow = Ext.create('Foss.pricing.expressPricePlan.ModifyPriceDetailWindow',{
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
			pricing.showWoringMessage(pricing.expressPricePlan.i18n('foss.pricing.pleaseSelectOneDeleteOperation'));//请选择一条进行删除操作！
			return;//没有则提示并返回
		}
		for(var i = 0;i<selections.length;i++){
			if(selections[i].get('active')==pricing.expressPricePlan.yes){
				pricing.showWoringMessage(pricing.expressPricePlan.i18n('foss.pricing.pleaseChooseToNotActivateTheDataToBeBeleted'));//请选择未激活数据进行删除！
				return;//没有则提示并返回
			}
		}
		pricing.showQuestionMes(pricing.expressPricePlan.i18n('foss.pricing.youWantDeletePricProgramDetails'),function(e){//是否要删除这些价格方案明细？
		if(e=='yes'){//询问是否删除，是则发送请求
				var valuationIds = new Array();//计费规则ID
				for(var i = 0 ; i<selections.length ; i++){
					valuationIds.push(selections[i].get('valuationId'));
				}
				var params = {'priceManageMentVo':{'pricePlanDetailIds':valuationIds}};
				var successFun = function(json){
					pricing.showInfoMes(json.message);
					var arrayDate = json.priceManageMentVo.pricePlanDetailDtoList;
//							grid.store.loadData(arrayDate);//显示第一页	
							grid.getPagingToolbar().moveFirst();
					};
				var failureFun = function(json){
					if(Ext.isEmpty(json)){
						pricing.showErrorMes(pricing.expressPricePlan.i18n('foss.pricing.requestTimedOut'));//请求超时
					}else{
						pricing.showErrorMes(json.message);
					}
				};
				var url = pricing.realPath('deletePricePlanDetailA.action');
				pricing.requestJsonAjax(url,params,successFun,failureFun);
			}
		})	
    },
	//初始化构造器
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.pricing.expressPricePlan.PricePlanDeatilStore',{
			autoLoad : false,
			pageSize : 10,
			listeners: {
				beforeload: function(store, operation, eOpts){
					var pricePlanId = pricing.pricePlanId
					if(pricePlanId!=null){
						Ext.apply(operation,{
							params : {
								'priceManageMentVo.queryPricePlanDetailBean.pricePlanId' : pricePlanId,//价格方案ID
								'priceManageMentVo.queryPricePlanDetailBean.arrvRegionId' : me.arrvRegionId
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
	            text: pricing.expressPricePlan.i18n('i18n.pricingRegion.add'),
	            handler:function(){ 
	            	me.getPricePlanDetailWindow().show();	 
	            }
	        },'-',{
	            text: pricing.expressPricePlan.i18n('foss.pricing.delete'),
	            handler:function(){
	            	me.deletePricePlanDetail(me); 
	            }
	    }];
		/*me.store = Ext.create('Foss.pricing.expressPricePlan.PricePlanDeatilStore',{
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
		});*/
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
		text: pricing.expressPricePlan.i18n('i18n.pricingRegion.num')//"序号"//序号
		
		},{
			text : pricing.expressPricePlan.i18n('i18n.pricingRegion.opra'),
			align : 'center',
			xtype : 'actioncolumn',
			items: [{
					iconCls:'deppon_icons_edit',
					tooltip: pricing.expressPricePlan.i18n('foss.pricing.toAmendTheProposal'), 
					width:42,
					handler: function(grid, rowIndex, colIndex){
						var me = this;
	                	var record = grid.up().getStore().getAt(rowIndex);//选中数据
	                	var pricePlanDetaiModel = new Foss.pricing.expressPricePlan.PricePlanDetailDtoModel();
 	                	//处理特殊字段
    					pricePlanDetaiModel.set('pricePlanId',record.get('pricePlanId'));
    					pricePlanDetaiModel.set('valuationId',record.get('valuationId'));
    					var params = {'priceManageMentVo':{'queryPricePlanDetailBean':pricePlanDetaiModel.data}};
	    				var successFun = function(json){
	    					//获取明细window
	    					var updateWindow =  grid.up().getModifyPriceDetailWindow();
	    					//获取根据计费规则ID所查询出来的计价规则以及计价明细信息
	    					var dto = json.priceManageMentVo.expressPricePlanDetailDto;
	    					//如果数据非空才赋值给明细FormPanel作为显示数据,否则提示没有找到对应的数据
							if(!Ext.isEmpty(dto)){
//								var pricePlanDetailDto = arrayDate[0];
								updateWindow.pricePlanDetailDto = dto;
							} 
							updateWindow.show();
	    				};
	    				var failureFun = function(json){
	    					if(Ext.isEmpty(json)){
	    						pricing.showErrorMes(pricing.expressPricePlan.i18n('foss.pricing.requestTimedOut'));//请求超时
	    					}else{
	    						pricing.showErrorMes(json.message);
	    					}
	    				};
	    				var url = pricing.realPath('queryBeforeModifyPricePlanDetailA.action');
	    				pricing.requestJsonAjax(url,params,successFun,failureFun);
					}
			}]
		},{
			text: pricing.expressPricePlan.i18n('foss.pricing.destinationArea'),//目的区域
			width: 120,
	        dataIndex: 'arrvRegionName'
		},{
			text: pricing.expressPricePlan.i18n('foss.pricing.productEntryName'),// "产品条目名称",
			width: 90,
	        dataIndex: 'productItemName'
		},{
			text :pricing.expressPricePlan.i18n('foss.pricing.firstWeight'),//改为首重
			dataIndex : 'firstFee',
			//DN201510290016 DP-FOSS-快递价格方案首重价格输入优化需求
			//2015/12/3 liding add
			decimalPrecision : 1, 
			allowDecimals : true, 
			renderer:function(value){
				if(!Ext.isEmpty(value)){
					//DN201510290016 DP-FOSS-快递价格方案首重价格输入优化需求
					//2015/12/3 liding mod
					//return value;
					return parseFloat(value).toFixed(1);
				}
			}
		},{
		    text : pricing.expressPricePlan.i18n('foss.pricing.continuedWeight1'),//续重1
		    columns: [{
		    	text : pricing.expressPricePlan.i18n('foss.pricing.weightOffline'),//重量下限
		    	dataIndex: 'weightDownlineOne',
				renderer:function(value){
					if(!Ext.isEmpty(value)){
						return value;
					}
				}
		    }, {
		    	text : pricing.expressPricePlan.i18n('foss.pricing.weightOnline'),//重量上限
		    	dataIndex: 'weightOnlineOne',
				renderer:function(value){
					if(!Ext.isEmpty(value)){
						return value;
					}
				}
		    }, {
		    	text : pricing.expressPricePlan.i18n('foss.pricing.rates'),// 费率
		    	dataIndex: 'feeRateOne',
				renderer:function(value){
					if(!Ext.isEmpty(value)){
						return value;
					}
				}
		    }]
		},{
		    text : pricing.expressPricePlan.i18n('foss.pricing.continuedWeight2'),//续重2
		    columns: [{
		    	text : pricing.expressPricePlan.i18n('foss.pricing.weightOffline'),//重量下限
		    	dataIndex: 'weightDownlineTwo',
				renderer:function(value){
					if(!Ext.isEmpty(value)){
						return value;
					}
				}
		    }, {
		    	text : pricing.expressPricePlan.i18n('foss.pricing.weightOnline'),//重量上限
		    	dataIndex: 'weightOnlineTwo',
				renderer:function(value){
					if(!Ext.isEmpty(value)){
						return value;
					}
				}
		    }, {
		    	text : pricing.expressPricePlan.i18n('foss.pricing.rates'),// 费率
		    	dataIndex: 'feeRateTwo',
				renderer:function(value){
					if(!Ext.isEmpty(value)){
						return value;
					}
				}
		    }]
		}/*,{
			width: 100,
			text: '是否自提',//是否自提,
	        dataIndex: 'selfPickUp',
	        renderer:function(value){
				if(value=='Y'){
					return pricing.expressPricePlan.i18n('i18n.pricingRegion.ye')//"是";
				}else if(value=='N'){
					return pricing.expressPricePlan.i18n('i18n.pricingRegion.no')//"否";
				}else{
					return '';
				}
			}
		}*//*,{
			text: pricing.expressPricePlan.i18n('foss.pricing.remark'),//"备注",//备注
			width: 120,
	        dataIndex: 'remark'
		}*/],
		pricing.pagingBar = me.bbar;
		me.callParent([cfg]);
	}
});

/**
 * 价格方案弹出框
 */
Ext.define('Foss.pricing.expressPricePlan.PricePlanAddWindow',{
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
				me.getPricePlanAddFormPanel().getDockedItems()[1].items.items[3].setDisabled(false);//保存按钮可用
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
	    		this.pricePlanAddFormPanel = Ext.create('Foss.pricing.expressPricePlan.PricePlanAddFormPanel');
	    	} 
	    	return this.pricePlanAddFormPanel;
	    },
	     //价格方案明细信息 （目的明细列表）
		pricePlanDetailGridPanel:null,
	    getPricePlanDetailGridPanel: function(){
	    	if(Ext.isEmpty(this.pricePlanDetailGridPanel)){
	    		this.pricePlanDetailGridPanel = Ext.create('Foss.pricing.expressPricePlan.PricePlanDetailGridPanel');
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
Ext.define('Foss.pricing.expressPricePlan.PricePlanUpdateWindow',{
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
				me.getPricePlanAddFormPanel().getForm().loadRecord(new Foss.pricing.expressPricePlan.PricePlanModel(me.pricePlanEntity));
				me.getPricePlanAddFormPanel().getForm().findField('priceRegionCode').setCombValue(me.pricePlanEntity.priceRegionName,me.pricePlanEntity.priceRegionCode);
				me.getPricePlanAddFormPanel().getForm().findField('customerCode').setCombValue(me.pricePlanEntity.customerName,me.pricePlanEntity.customerCode);
				me.getPricePlanDetailGridPanel().store.removeAll(true);
			}
		},
	    //价格方案批次信息 （出发地批次信息录入）
		pricePlanAddFormPanel:null,
	    getPricePlanAddFormPanel : function(){
	    	var me = this;
	    	if(Ext.isEmpty(me.pricePlanAddFormPanel)){
		    		me.pricePlanAddFormPanel = Ext.create('Foss.pricing.expressPricePlan.PricePlanAddFormPanel');
		    		//设置器父元素
 	    	} 
	    	return this.pricePlanAddFormPanel;
	    },
	     //价格方案明细信息 （目的明细列表）
		pricePlanDetailGridPanel:null,
	    getPricePlanDetailGridPanel: function(){
	    	if(Ext.isEmpty(this.pricePlanDetailGridPanel)){
	    			this.pricePlanDetailGridPanel = Ext.create('Foss.pricing.expressPricePlan.PricePlanDetailGridPanel');
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
Ext.define('Foss.pricing.expressPricePlan.PricePlanStopEndTimeWindow',{
		extend: 'Ext.window.Window',
		title: pricing.expressPricePlan.i18n('foss.pricing.suspendPriceScheme'),//"中止价格方案",
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
	    		this.pricePlanStopFormPanel = Ext.create('Foss.pricing.expressPricePlan.PricePlanStopFormPanel');
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
Ext.define('Foss.pricing.expressPricePlan.PricePlanStopFormPanel',{
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
			var pricePlanModel = new Foss.pricing.expressPricePlan.PricePlanModel();
			form.updateRecord(pricePlanModel);
    		pricePlanModel.set('id',pricePlanId);
    		pricePlanModel.set('endTime',Ext.Date.parse(form.findField('endTime').getValue(), 'Y-m-d H:i:s')); 
    		var params = {'priceManageMentVo':{'pricePlanEntity':pricePlanModel.data}};
    		
    		//ajax请求
    		var url = pricing.realPath('stopPricePlanA.action');
    		
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
    				pricing.showErrorMes(pricing.expressPricePlan.i18n('i18n.pricingRegion.requestTimeOut'));
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
				fieldLabel:pricing.expressPricePlan.i18n('foss.pricing.deadline'),//截止日期
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
					text : pricing.expressPricePlan.i18n('foss.pricing.stop'),//"中止",
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
Ext.define('Foss.pricing.expressPricePlan.PricePlanImmediatelyStopEndTimeWindow',{
		extend: 'Ext.window.Window',
		title: pricing.expressPricePlan.i18n('foss.pricing.immediatelySupendPricePriceScheme'),//"立即中止方案",
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
				var endTime = Ext.Date.format(new Date(me.pricePlanEntity.beginTime), 'Y-m-d H:i:s');
				var value = pricing.expressPricePlan.i18n('foss.pricing.showleftTimeInfo')
					  +beginTime+pricing.expressPricePlan.i18n('foss.pricing.showmiddleTimeInfo')
					  +endTime+pricing.expressPricePlan.i18n('foss.pricing.showstopRightEndTimeInfo');
				me.pricePlanEntity.showTime = value;
				me.getPricePlanStopFormPanel().getForm().loadRecord(new Foss.pricing.expressPricePlan.PricePlanModel(me.pricePlanEntity));
				me.getPricePlanStopFormPanel().getForm().findField('endTime').setValue(Ext.Date.format(me.pricePlanEntity.endTime,'Y-m-d H:i:s'));
			}
		},
		pricePlanStopFormPanel:null,
		getPricePlanStopFormPanel : function(pricePlanEntity){
	    	if(Ext.isEmpty(this.pricePlanStopFormPanel)){
	    		this.pricePlanStopFormPanel = Ext.create('Foss.pricing.expressPricePlan.PricePlanImmediatelyStopFormPanel');
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
Ext.define('Foss.pricing.expressPricePlan.PricePlanImmediatelyStopFormPanel',{
	extend : 'Ext.form.Panel',
	layout:'column',
	pricePlanEntity:null,
	stopPricePlan:function(pricePlanId){
		var me = this;
    	var form = me.getForm();
    	if(form.isValid()){
			var pricePlanModel = new Foss.pricing.expressPricePlan.PricePlanModel();
			form.updateRecord(pricePlanModel);
    		pricePlanModel.set('id',pricePlanId);
    		pricePlanModel.set('isPromptly',true);
    		var params = {'priceManageMentVo':{'pricePlanEntity':pricePlanModel.data}};
    		pricePlanModel.set('endTime',Ext.Date.parse(form.findField('endTime').getValue(), 'Y-m-d H:i:s'));
    		var url = pricing.realPath('stopPricePlanA.action');
    		var successFun = function(json){
    			pricing.showInfoMes(json.message);
    			me.up('window').hide();
				pricing.pagingBar.moveFirst();   			
    	    };
    	    var failureFun = function(json){
    	    	if(Ext.isEmpty(json)){
    				pricing.showErrorMes(pricing.expressPricePlan.i18n('i18n.pricingRegion.requestTimeOut'));
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
				fieldLabel :pricing.expressPricePlan.i18n('foss.pricing.suspendTime') ,//'中止日期',
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
				text : pricing.expressPricePlan.i18n('i18n.pricingRegion.determine'),//"确认",
				handler : function() {
					var pricePlanId = me.up('window').pricePlanEntity.id;
					me.stopPricePlan(pricePlanId);
				}
			},{
				xtype : 'button', 
				width:70,
				columnWidth:.15,
				text : pricing.expressPricePlan.i18n('i18n.pricingRegion.cancel'),//"取消",
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
Ext.define('Foss.pricing.expressPricePlan.PricePlanImmediatelyActiveTimeWindow',{
		extend: 'Ext.window.Window',
		title: pricing.expressPricePlan.i18n('foss.pricing.immediatelyActiveationPriceScheme'),//"立即激活方案",
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
				var value = pricing.expressPricePlan.i18n('foss.pricing.showleftTimeInfo')
					  +beginTime+pricing.expressPricePlan.i18n('foss.pricing.showmiddleTimeInfo')
					  +endTime+pricing.expressPricePlan.i18n('foss.pricing.showrightEndTimeInfo');
				me.pricePlanEntity.showTime = value;
				me.getPricePlanImmediatelyActiveFormPanel().getForm().loadRecord(new Foss.pricing.expressPricePlan.PricePlanModel(me.pricePlanEntity));
				me.getPricePlanImmediatelyActiveFormPanel().getForm().findField('beginTime').setValue(Ext.Date.format(new Date(),'Y-m-d H:i:s'));
			}
		},
		//创建FormPanel 不管FormPanel是否存在都覆盖最新的信息值 pricePlanEntity
		pricePlanImmediatelyActiveFormPanel:null,
		getPricePlanImmediatelyActiveFormPanel : function(pricePlanEntity){
	    	if(Ext.isEmpty(this.pricePlanImmediatelyActiveFormPanel)){
	    		this.pricePlanImmediatelyActiveFormPanel = Ext.create('Foss.pricing.expressPricePlan.PricePlanImmediatelyActiveFormPanel');
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
Ext.define('Foss.pricing.expressPricePlan.PricePlanImmediatelyActiveFormPanel',{
	extend : 'Ext.form.Panel',
	layout:'column',
	pricePlanEntity:null,
	activetionPricePlan:function(pricePlanId){
		var me = this;
    	var form = me.getForm();
    	if(form.isValid()){
			var pricePlanModel = new Foss.pricing.expressPricePlan.PricePlanModel();
			form.updateRecord(pricePlanModel);
			pricePlanModel.set('beginTime',Ext.Date.parse(form.findField('beginTime').getValue(), 'Y-m-d H:i:s'));
    		pricePlanModel.set('id',pricePlanId);
    		var params = {'priceManageMentVo':{'pricePlanEntity':pricePlanModel.data}};
    		var url = pricing.realPath('immediatelyActivePricePlanA.action');
    		var successFun = function(json){
    			pricing.showInfoMes(json.message);
    			me.up('window').hide();
				pricing.pagingBar.moveFirst();   			
    	    };
    	    var failureFun = function(json){
    	    	if(Ext.isEmpty(json)){
    				pricing.showErrorMes(pricing.expressPricePlan.i18n('i18n.pricingRegion.requestTimeOut'));
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
				fieldLabel:pricing.expressPricePlan.i18n('foss.pricing.availabilityDate'),//'生效日期',
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
				width:70,
				columnWidth:.15,
				text : pricing.expressPricePlan.i18n('i18n.pricingRegion.determine'),//,"确认",
				handler : function() {
					var pricePlanId = me.up('window').pricePlanEntity.id;
					me.activetionPricePlan(pricePlanId);
				}
			},{
				xtype : 'button', 
				width:70,
				columnWidth:.15,
				text : pricing.expressPricePlan.i18n('i18n.pricingRegion.cancel'),//"取消",
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
Ext.define('Foss.pricing.expressPricePlan.ModifyPriceDetailWindow',{
	extend : 'Ext.window.Window',
	title: '修改快递价格方案明细',//明细信息新增
	closable : true,
	modal : true,
	resizable:false,
	closeAction : 'hide',
	parent:null,
	width :880,
	height :500,
	pricePlanDetailDto:null,
	grid:null,																	//父 grid
	listeners:{
		beforehide:function(me){
			me.getPricePlanDetailForm().getForm().reset();
		},
		beforeshow:function(me){
			me.getPricePlanDetailForm().getForm().loadRecord(new Foss.pricing.expressPricePlan.PricePlanDetailDtoModel(me.pricePlanDetailDto));
			me.getPricePlanDetailForm().getForm().findField('arrvRegionCode').setCombValue(me.pricePlanDetailDto.arrvRegionName,me.pricePlanDetailDto.arrvRegionName);
			me.getPricePlanDetailForm().getForm().findField('arrvRegionCode').arrvRegionId = me.pricePlanDetailDto.arrvRegionId;
			me.getPricePlanDetailForm().getForm().findField('arrvRegionCode').arrvRegionName = me.pricePlanDetailDto.arrvRegionName;
			me.getPricePlanDetailForm().getForm().findField('productItemCode').setValue(me.pricePlanDetailDto.productItemCode);
			me.getPricePlanDetailForm().getForm().findField('productItemCode').productItemId = me.pricePlanDetailDto.productItemId;
			me.getPricePlanDetailForm().getForm().findField('productItemCode').productItemName = me.pricePlanDetailDto.productItemName;
		}
	},
    //明细信息新增-FORM
    pricePlanDetailForm:null,
    getPricePlanDetailForm:function(){
    	if(Ext.isEmpty(this.pricePlanDetailForm)){
    		this.pricePlanDetailForm = Ext.create('Foss.pricing.expressPricePlan.ExpressPricePlanDetailForm');
    	}
    	return this.pricePlanDetailForm;
    },
    updatePriceDetailPlan:function(grid){
    	var me = this;
    	//得到明细form
    	var form = me.getPricePlanDetailForm().getForm();
    	if(form.isValid()){
    		var pricePlanDetailDto = new Foss.pricing.expressPricePlan.PricePlanDetailDtoModel(me.pricePlanDetailDto);
    		form.updateRecord(pricePlanDetailDto);
    	 
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
	    	pricePlanDetailDto.set('remark',remark);
	    	pricePlanDetailDto.set('pricePlanId',pricePlanDetailDto.data.pricePlanId);
	    	
			//制定json请求参数
			var params = {'priceManageMentVo':{'expressPricePlanDetailDto':pricePlanDetailDto.data}};
			//ajax请求
			var url = pricing.realPath('updatePriceDetailPlanA.action');
			
			//成功提示
			var successFun = function(json){
				pricing.showInfoMes(json.message);
				pricing.pricePlanId = pricePlanDetailDto.data.pricePlanId;
//				pricing.pagingBar.moveFirst();
				me.close();
				grid.getPagingToolbar().moveFirst();
		    };
		    //失败提示
		    var failureFun = function(json){
		    	if(Ext.isEmpty(json)){
					pricing.showErrorMes(pricing.expressPricePlan.i18n('i18n.pricingRegion.requestTimeOut'));
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
		me.items = [me.getPricePlanDetailForm()];//设置window的元素
		me.fbar = [{
			text : pricing.expressPricePlan.i18n('i18n.pricingRegion.cancel'),//取消
			handler :function(){
				me.close();
			} 
		},{
			text : pricing.expressPricePlan.i18n('foss.pricing.reset'),//重置
			margin:'0 185 0 0',
			handler :function(){
				me.getPricePlanDetailForm().getForm().reset();
//				if(Ext.isEmpty(me.pricePlanDetailDto)){
//					me.getPricePlanDetailForm().getForm().loadRecord(new Foss.pricing.expressPricePlan.PricePlanDetailDtoModel());
//				}else{
//					me.getPricePlanDetailForm().getForm().loadRecord(new Foss.pricing.expressPricePlan.PricePlanDetailDtoModel(me.pricePlanDetailDto));
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
Ext.define('Foss.pricing.expressPricePlan.UploadPriceform',{
	extend:'Ext.window.Window',
	title:pricing.expressPricePlan.i18n('foss.pricing.importPriceScheme'),
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
	parent:null,//（Foss.pricing.expressPricePlan.pricePlanformGrid）
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
				fieldLabel:pricing.expressPricePlan.i18n('foss.pricing.pleaseSelectAttachments'),
				labelWidth:100,
				buttonText:pricing.expressPricePlan.i18n('foss.pricing.browse'),
				flex:3
			}]
		}];
		me.fbar = me.getFbar();
		me.callParent([cfg]);
	},
	getFbar:function(){
		var me = this;
		return [{
			text:pricing.expressPricePlan.i18n('foss.pricing.import'),
			xtype:'button',
			scope:me,
			handler:me.uploadFile
		},{
			text:pricing.expressPricePlan.i18n('i18n.pricingRegion.cancel'),
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
				pricing.showInfoMes(pricing.expressPricePlan.i18n('foss.pricing.allDataImportSuccess'));//全部数据导入成功！
				me.close();
			}else{
				var message = pricing.expressPricePlan.i18n('foss.pricing.first');//第
				for(var i = 0;i<json.platformVo.numList;i++){
					message = message+json.platformVo.numList[i]+','
				}
				message = message+pricing.expressPricePlan.i18n('foss.pricing.lineImportSuccess');
				pricing.showWoringMessage(message);
			}
		};
		var failureFn = function(json){
			if(Ext.isEmpty(json)){
				pricing.showErrorMes(pricing.expressPricePlan.i18n('i18n.pricingRegion.requestTimeOut'));//pricing.expressPricePlan.i18n('i18n.pricingRegion.requestTimeOut')
			}else{
				pricing.showErrorMes(json.message);
			}
		};
		var form = me.down('form').getForm();
		var url = pricing.realPath('importPriceA.action');
		form.submit({
            url: url,
			timeout:60000,   
            waitMsg: pricing.expressPricePlan.i18n('foss.pricing.uploadYourAttachment'),
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
	var queryform = Ext.create('Foss.pricing.expressPricePlan.PricePlanFormPanel');
	var gridPanel =	Ext.create('Foss.pricing.expressPricePlan.PricePlanGridPanel');
	pricing.queryform = queryform;
	pricing.gridPanel = gridPanel;
	Ext.getCmp('T_pricing-expressPricePlanIndex').add(Ext.create('Ext.panel.Panel', {
	  	id:'T_pricing-expressPricePlanIndex_content',
		cls:"panelContentNToolbar",
		bodyCls:'panelContent-body',
		//获得查询FORM
		getQueryPricePlanForm : function() {
			return queryform;
		},
		//获得查询结果GRID
		getPricePlanGridPanel : function() {
			if(Ext.isEmpty(this.gridPanel)){
				this.gridPanel = Ext.create('Foss.pricing.expressPricePlan.PricePlanGridPanel');//查询结果GRID
			}
			return gridPanel;
		},
		items : [queryform,gridPanel]
	}));
});

