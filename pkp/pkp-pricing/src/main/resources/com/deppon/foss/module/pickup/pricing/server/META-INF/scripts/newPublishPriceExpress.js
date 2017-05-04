

//--------------------------------------pricing----------------------------------------
//公布价MODEL
Ext.define('Foss.pricing.newPublishPriceExpress.PublishPriceExpressEntity', {
    extend: 'Ext.data.Model',
    fields : [{
        name : 'firstWeight',  //首重
        defaultValue : null
    },{
        name : 'weightOnline1', //重量上限1
        defaultValue : null
    },{
        name : 'weightOffline1',//重量下限1
        defaultValue : null
    },{
        name : 'feeRate1',  //费率1
        defaultValue : null
    },{
        name : 'weightOnline2', //重量上限2
        defaultValue : null
    },{
        name : 'weightOffline2',//重量下限2
        defaultValue : null
    },{
        name : 'feeRate2',//费率2
        defaultValue : null
    },{
        name : 'deptRegionName',  //始发区域
        type : 'string'
    },{
        name : 'deptRegionId',  //始发ID
        type : 'string'
    },{
        name : 'deptPriceRegionId',//价格始发区域ID
        type : 'string'
    },{
        name : 'deptPriceRegionCode',//价格始发区域CODE
        type : 'string'
    },{
        name : 'deptPriceRegionName',//价格始发区域名称
        type : 'string'
    },{
        name : 'arrvRegionId',  //目的区域ID
        type : 'string'
    },{
        name : 'arrvRegionCode', //目的区域CODE
        type : 'string'
    },{
        name : 'arrvRegionName',//目的站
        type : 'string'
    },{
        name : 'arrvPriceRegionId',//价格到达区域ID
        type : 'string'
    },{
        name : 'arrvPriceRegionCode',//价格到达区域CODE
        type : 'string'
    },{
        name : 'arrvPriceRegionName',//价格到达区域名称
        type : 'string'
    },{
        name : 'productItemName',//条目名称
        type : 'string'
    },{
        name : 'arriveTime',//营运时效
        type : 'string'
    },{
        name : 'deliveryTime',//派送时间
        type : 'string'
    },{
        name : 'pickupTime',//取货时间
        type : 'string'
    },{
        name : 'regionNature',//区域类型
        type : 'string'
    }]
});

//网点MODEL
Ext.define('Foss.pricing.newPublishPriceExpress.SaleDepartmentInfoDto', {
    extend: 'Ext.data.Model',
    fields : [{
        name : 'code',  //部门编码
        type : 'string'
    },{
        name : 'name', //部门名称
        type : 'string'
    },{
        name : 'pickupSelf',//自提
        type : 'string'
    },{
        name : 'delivery',//可派送
        type : 'string'
    },{
        name : 'orgType',//营业部类型
        type : 'string'
    },{
        name : 'simpleName',//简称
        type : 'string'
    },{
        name : 'address',//营业部地址
        type : 'string'
    },{
        name : 'telephone',//营业部电话
        type : 'string'
    }]
});

//时效区域
pricing.newPublishPriceExpress.PRESCRIPTION_REGION = 'EFFECTIVE_REGION';
//价格区域
pricing.newPublishPriceExpress.PRICING_REGION = 'PRICING_REGION';

//超链接点击区域时显示区域关联网点的WINDOW
pricing.expressshowNetPoint = function(id){
	var grid = Ext.getCmp('T_pricing-publishPriceExpressIndex_content').getPublishPriceGrid();
	var deptCodes = [];
	var regionId;
	var regionNature;
	grid.getStore().each(function(record){
		if(record.get('id')==id){
			deptCodes = record.get('deptCodes');
			regionId = record.get('arrvRegionId');
			regionNature = record.get('regionNature');
		}
	});
	var showNetPointWindow = grid.getShowNetPointWindow();
	showNetPointWindow.deptCodes = deptCodes;
	showNetPointWindow.deptRegionId = regionId;
	showNetPointWindow.regionNature = regionNature;
	showNetPointWindow.show();
};

pricing.expressshowStartNetPoint = function(id){
	var grid = Ext.getCmp('T_pricing-publishPriceExpressIndex_content').getPublishPriceGrid();
	var startDeptCodes = [];
	var regionId;
	var regionNature;
	grid.getStore().each(function(record){
		if(record.get('id')==id){
			startDeptCodes = record.get('startDeptCodes');
			regionId = record.get('deptRegionId');
			regionNature = record.get('regionNature');
		}
	});
	var showNetPointWindow = grid.getShowNetPointWindow();
	showNetPointWindow.deptCodes = startDeptCodes;
	showNetPointWindow.deptRegionId = regionId;
	showNetPointWindow.regionNature = regionNature;
	showNetPointWindow.show();
};

pricing.expressshowArrvNetPointPrice = function(id){
	var grid = Ext.getCmp('T_pricing-publishPriceExpressIndex_content').getPublishPriceGrid();
	var deptCodes = [];
	var regionId;
	var regionNature;
	grid.getStore().each(function(record){
		if(record.get('id')==id){
			deptCodes = record.get('priceArrvDeptCodes');
			regionId = record.get('arrvPriceRegionId');
			regionNature = record.get('regionNature');
		}
	});
	var showNetPointWindow = grid.getShowNetPointWindow();
	showNetPointWindow.deptCodes = deptCodes;
	showNetPointWindow.deptRegionId = regionId;
	if(regionNature == pricing.newPublishPriceExpress.PRESCRIPTION_REGION) {
		regionNature = pricing.newPublishPriceExpress.PRICING_REGION;
	}
	showNetPointWindow.regionNature = regionNature;
	showNetPointWindow.show();
};

pricing.expressshowStartNetPointPrice = function(id){
	var grid = Ext.getCmp('T_pricing-publishPriceExpressIndex_content').getPublishPriceGrid();
	var startDeptCodes = [];
	var regionId;
	var regionNature;
	grid.getStore().each(function(record){
		if(record.get('id')==id){
			startDeptCodes = record.get('priceStartDeptCodes');
			regionId = record.get('deptPriceRegionId');
			regionNature = record.get('regionNature');
		}
	});
	var showNetPointWindow = grid.getShowNetPointWindow();
	showNetPointWindow.deptCodes = startDeptCodes;
	showNetPointWindow.deptRegionId = regionId;
	if(regionNature == pricing.newPublishPriceExpress.PRESCRIPTION_REGION) {
		regionNature = pricing.newPublishPriceExpress.PRICING_REGION;
	}
	showNetPointWindow.regionNature = regionNature;
	showNetPointWindow.show();
};



//------------------------------------model---------------------------------------------------
/**
 * 公布价Store
 */
Ext.define('Foss.pricing.newPublishPriceExpress.PublishPriceStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.pricing.newPublishPriceExpress.PublishPriceExpressEntity',//公布价MODEL
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url : pricing.realPath('searchNewPublishPriceExpressByCondition.action'),
		reader : {
			type : 'json',
			root : 'newPublishPriceExpressVo.publishPriceExpressList'
		}
	}
});
/**
 * 网点Store
 */
Ext.define('Foss.pricing.newPublishPriceExpress.NetPointStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.pricing.newPublishPriceExpress.SaleDepartmentInfoDto',//网点MODEL
	data :[]
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
 * 公布价查询表单
 */
Ext.define('Foss.pricing.newPublishPriceExpress.QueryForm', {
	extend : 'Ext.form.Panel',
	title: pricing.newPublishPriceExpress.i18n('i18n.pricingRegion.searchCondition'),
	frame: true,
	collapsible: true,
    defaults : {
    	type:'table',
    	columns: 4
    },
    height :220,
	defaultType : 'textfield',
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.items  = [{
			forceSelection : true,
			xtype : 'commoncityselector',
			colspan : 4,
			name: 'startCityCode',
			allowBlank:false,
		    fieldLabel:pricing.newPublishPriceExpress.i18n('foss.pricing.departureCity')//出发城市
		},{
   		   xtype:'linkregincombselector',
   		   colspan : 4,
   		   margin : '8 0 5 0',
   		   columnWidth : 0.75,
   		   allowBlank:false,
   		   fieldLabel:pricing.newPublishPriceExpress.i18n('foss.pricing.destination')//所在地
	   	},{
			xtype : 'textfield',
			colspan : 2,
			margin : '8 0 5 0',
			name: 'destinationAddress',
		    fieldLabel:pricing.newPublishPriceExpress.i18n('foss.pricing.address')//详细地址
		}];
		me.fbar = [{
			xtype : 'button', 
			colspan : 1,
			text : pricing.newPublishPriceExpress.i18n('foss.pricing.reset'),//重置
			handler : function() {
				me.getForm().reset();
			}
		},{
			xtype : 'button', 
			colspan : 1,
			margin:'0 0 0 845',
			cls:'yellow_button',
			text : pricing.newPublishPriceExpress.i18n('i18n.pricingRegion.search'),
			disabled: !pricing.newPublishPriceExpress.isPermission('publishPriceExpressIndex/publishPriceExpressIndexQuerybutton'),
			hidden: !pricing.newPublishPriceExpress.isPermission('publishPriceExpressIndex/publishPriceExpressIndexQuerybutton'),
			handler : function() {
				if(me.getForm().isValid()){
					var grid = Ext.getCmp('T_pricing-publishPriceExpressIndex_content').getPublishPriceGrid();//获取大查询GRID
					grid.getStore().load();
				}
			}
		},{
			xtype: 'hidden',
			colspan: 2
		}]
		me.callParent([cfg]);
	}
});
/**
 * 公布价列表
 */
Ext.define('Foss.pricing.newPublishPriceExpress.PublishPriceGridPanel', {
	extend: 'Ext.grid.Panel',
	title : pricing.newPublishPriceExpress.i18n('i18n.pricingRegion.searchResults'),
	frame: true,
    sortableColumns:false,
    enableColumnHide:false,
    enableColumnMove:false,
	stripeRows : true, // 交替行效果
	selType : "rowmodel", // 选择类型设置为：行选择
	emptyText: pricing.newPublishPriceExpress.i18n('foss.pricing.theQueryResultIsEmpty'),//查询结果为空
	showNetPointWindow:null,
	//得到区域关联网点数据
    getShowNetPointWindow: function(){
    	if(Ext.isEmpty(this.showNetPointWindow)){
    		this.showNetPointWindow = Ext.create('Foss.pricing.newPublishPriceExpress.ShowNetPointWindow');
    	}
    	return this.showNetPointWindow;
    },
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.columns = [{xtype: 'rownumberer',
			width:40,
			text : pricing.newPublishPriceExpress.i18n('i18n.pricingRegion.num')//序号
		},{
			text : pricing.newPublishPriceExpress.i18n('foss.pricing.originatingEffectiveArea'),//始发区域
			dataIndex : 'deptRegionName',
			renderer:function(value,obj,record){
				var id = record.get('id');
				return '<a href="javascript:pricing.expressshowStartNetPoint('+"'"+id+"'"+');">'+value+'</a>';
			}
		},{
			text : pricing.newPublishPriceExpress.i18n('foss.pricing.destinationEffectiveArea'),//目的地区域
			dataIndex : 'arrvRegionName',
			renderer:function(value,obj,record){
				var id = record.get('id');
				return '<a href="javascript:pricing.expressshowNetPoint('+"'"+id+"'"+');">'+value+'</a>';
			}
		},{
			text : pricing.newPublishPriceExpress.i18n('foss.pricing.originatingPriceArea'),//价格始发区域
			dataIndex : 'deptPriceRegionName',
			renderer:function(value,obj,record){
				var id = record.get('id');
				return '<a href="javascript:pricing.expressshowStartNetPointPrice('+"'"+id+"'"+');">'+value+'</a>';
			}
		},{
			text : pricing.newPublishPriceExpress.i18n('foss.pricing.destinationPriceArea'),//价格目的区域
			dataIndex : 'arrvPriceRegionName',
			renderer:function(value,obj,record){
				var id = record.get('id');
				return '<a href="javascript:pricing.expressshowArrvNetPointPrice('+"'"+id+"'"+');">'+value+'</a>';
			}
		},{
			text :pricing.newPublishPriceExpress.i18n('foss.pricing.productInformation'),//产品名称
			dataIndex : 'productItemName'
		},{
			text :pricing.newPublishPriceExpress.i18n('foss.pricing.firstWeight'),//改为首重
			dataIndex : 'firstWeight',
			renderer:function(value){
				if(!Ext.isEmpty(value)){
					return value;
				}
			}
		},{
		    text : pricing.newPublishPriceExpress.i18n('foss.pricing.continuedWeight1'),//续重1
		    columns: [{
		    	text : pricing.newPublishPriceExpress.i18n('foss.pricing.weightOffline'),//重量下线
		    	dataIndex: 'weightOffline1',
				renderer:function(value){
					if(!Ext.isEmpty(value)){
						return value;
					}
				}
		    }, {
		    	text : pricing.newPublishPriceExpress.i18n('foss.pricing.weightOnline'),//重量上线
		    	dataIndex: 'weightOnline1',
				renderer:function(value){
					if(!Ext.isEmpty(value)){
						return value;
					}
				}
		    }, {
		    	text : pricing.newPublishPriceExpress.i18n('foss.pricing.rates'),// 费率
		    	dataIndex: 'feeRate1',
				renderer:function(value){
					if(!Ext.isEmpty(value)){
						return value;
					}
				}
		    }]
		},{
		    text : pricing.newPublishPriceExpress.i18n('foss.pricing.continuedWeight2'),//续重2
		    columns: [{
		    	text : pricing.newPublishPriceExpress.i18n('foss.pricing.weightOffline'),//重量下线
		    	dataIndex: 'weightOffline2',
				renderer:function(value){
					if(!Ext.isEmpty(value)){
						return value;
					}
				}
		    }, {
		    	text : pricing.newPublishPriceExpress.i18n('foss.pricing.weightOnline'),//重量上线
		    	dataIndex: 'weightOnline2',
				renderer:function(value){
					if(!Ext.isEmpty(value)){
						return value;
					}
				}
		    }, {
		    	text : pricing.newPublishPriceExpress.i18n('foss.pricing.rates'),// 费率
		    	dataIndex: 'feeRate2',
				renderer:function(value){
					if(!Ext.isEmpty(value)){
						return value;
					}
				}
		    }]
		},{
			text :pricing.newPublishPriceExpress.i18n('foss.pricing.operatingAging'),//营运时效
			dataIndex : 'arriveTime',
			renderer:function(value){
				if(!Ext.isEmpty(value)){
					return value+pricing.newPublishPriceExpress.i18n('foss.pricing.day');
				}
			}
		},{
			text :pricing.newPublishPriceExpress.i18n('foss.pricing.pickTime'),//自提时间
			dataIndex : 'pickupTime'
		},{
			text :pricing.newPublishPriceExpress.i18n('foss.pricing.deliveryTime'),//派送时间
			dataIndex : 'deliveryTime'
		}];
		me.store = Ext.create('Foss.pricing.newPublishPriceExpress.PublishPriceStore',{
			autoLoad : false,
			listeners: {
				beforeload: function(store, operation, eOpts){
					var queryForm = Ext.getCmp('T_pricing-publishPriceExpressIndex_content').getQueryForm();
					var destinationCountryCode = queryForm.items.items[1].items.items[0].getValue();//国家
					var destinationProvinceCode =queryForm.items.items[1].items.items[1].getValue();//省
		    		var destinationCityCode = queryForm.items.items[1].items.items[2].getValue();//市
		    		var destinationCountyCode = queryForm.items.items[1].items.items[3].getValue();//区县
					if(queryForm!=null){
						Ext.apply(operation,{
							params : {
								'newPublishPriceExpressVo.startCityCode':queryForm.getForm().findField('startCityCode').getValue(),//始发城市CODE
								'newPublishPriceExpressVo.destinationCountryCode':destinationCountryCode,//始发城市CODE
								'newPublishPriceExpressVo.destinationProvinceCode':destinationProvinceCode,//始发城市CODE
								'newPublishPriceExpressVo.destinationCityCode':destinationCityCode,//始发城市CODE
								'newPublishPriceExpressVo.destinationCountyCode':destinationCountyCode,//始发城市CODE
								'newPublishPriceExpressVo.destinationAddress':queryForm.getForm().findField('destinationAddress').getValue()
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
		me.callParent([cfg]);
	}
});
/**
 * 区域管理-区域部门关联显示弹窗
 */
Ext.define('Foss.pricing.newPublishPriceExpress.ShowNetPointWindow',{
	extend : 'Ext.window.Window',
	title : pricing.newPublishPriceExpress.i18n('foss.pricing.regionCorrespondingOutlets'),
	closable : true,
	modal : true,
	resizable:false,
	closeAction : 'hide',
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	width :600,
	height :350,
	deptShowGrid : null,//显示区域关联部门明细表
	deptCodes:[],
	deptRegionId:null,
	regionNature:null,
	listeners:{
		beforehide:function(me){
			me.getPublishPriceCityGrid().getStore().removeAll();
		},
		beforeshow:function(me){//根据区域ID和区域性质，查询区域关联部门
			var params = {'newPublishPriceExpressVo':{'deptCodes':me.deptCodes, 'deptRegionId':me.deptRegionId, 'regionNature':me.regionNature}};
			var successFun = function(json){
				me.getPublishPriceCityGrid().getStore().add(json.newPublishPriceExpressVo.saleDepartmentInfoDtoList);
			};
			var failureFun = function(json){
				if(Ext.isEmpty(json)){
					pricing.showErrorMes(pricing.newPublishPriceExpress.i18n('i18n.pricingRegion.requestTimeOut'));
				}else{
					pricing.showErrorMes(json.message);
				}
			};
			var url = pricing.realPath('queryOuterAndDepartmentExpress.action');
			pricing.requestJsonAjax(url,params,successFun,failureFun);
		}
	},
	//得到区域关联网点数据
    getPublishPriceCityGrid: function(){
    	if(Ext.isEmpty(this.publishPriceCityGrid)){
    		this.publishPriceCityGrid = Ext.create('Foss.pricing.newPublishPriceExpress.PublishPriceCityGridPanel');
    	}
    	return this.publishPriceCityGrid;
    },
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [ me.getPublishPriceCityGrid()];
		me.fbar = [{
			text : pricing.newPublishPriceExpress.i18n('i18n.pricingRegion.returnGrid'),//返回列表，其实就是隐藏窗口
			handler :function(){
				me.close();
			} 
		}];
		me.callParent([cfg]);
	}
});

/**
 * 网点列表
 */
Ext.define('Foss.pricing.newPublishPriceExpress.PublishPriceCityGridPanel', {
	extend: 'Ext.grid.Panel',
	title : pricing.newPublishPriceExpress.i18n('foss.pricing.regionCorrespondingOutlets'),
	frame: true,
	height:240,
	autoScroll:true,
    sortableColumns:false,
    enableColumnHide:false,
    enableColumnMove:false,
	stripeRows : true, // 交替行效果
	selType : "rowmodel", // 选择类型设置为：行选择
	emptyText: pricing.newPublishPriceExpress.i18n('foss.pricing.theQueryResultIsEmpty'),//查询结果为空
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.columns = [{xtype: 'rownumberer',
			width:40,
			text : pricing.newPublishPriceExpress.i18n('i18n.pricingRegion.num')//序号
		},{
			text : pricing.newPublishPriceExpress.i18n('foss.pricing.outletsReferredTo'),//网点简称
			dataIndex : 'simpleName'
		},{
			text : pricing.newPublishPriceExpress.i18n('foss.pricing.networkName'),//网点名称
			dataIndex : 'name'
		},{
			text :pricing.newPublishPriceExpress.i18n('foss.pricing.deliveryPointAddress'),//提货点地址
			dataIndex : 'address'
		},{
			text : pricing.newPublishPriceExpress.i18n('foss.pricing.tel'),//联系电话
			dataIndex : 'telephone'
		},{
			text :pricing.newPublishPriceExpress.i18n('foss.pricing.networkType'),//网点类别
			dataIndex : 'orgType',
			renderer:function(value){
				if(value=='ORG'){
					return pricing.newPublishPriceExpress.i18n('foss.pricing.salesDepartment');//营业部
				}else if(value=='PX'){
					return pricing.newPublishPriceExpress.i18n('foss.pricing.thePartialLineAgentOutlets');//偏线代理网点
				}else if(value=='KY'){
					return pricing.newPublishPriceExpress.i18n('foss.pricing.airFreightForwardingOutlets');//空运代理网点
				}else{
					return value;
				}
			}
		},{
			text :pricing.newPublishPriceExpress.i18n('foss.pricing.whetherDoorToDoor'),//是否送货上门
			dataIndex : 'pickupSelf',
			renderer:function(value){
				if(value=='Y'){
					return pricing.newPublishPriceExpress.i18n('i18n.pricingRegion.ye');//是
				}else if(value=='N'){
					return pricing.newPublishPriceExpress.i18n('i18n.pricingRegion.no');//否
				}else{
					return value;
				}
			}
		},{
			text :pricing.newPublishPriceExpress.i18n('foss.pricing.whetherFromMentioning'),//是否可以自提
			dataIndex : 'delivery',
			renderer:function(value){
				if(value=='Y'){
					return pricing.newPublishPriceExpress.i18n('i18n.pricingRegion.ye');//是
				}else if(value=='N'){
					return pricing.newPublishPriceExpress.i18n('i18n.pricingRegion.no');//否
				}else{
					return value;
				}
			}
		}];
		me.store = Ext.create('Foss.pricing.newPublishPriceExpress.NetPointStore');
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
		me.callParent([cfg]);
	}
});
/**
 * @description 公布价主页
 */
Ext.onReady(function() {
	Ext.QuickTips.init();
	if (Ext.getCmp('T_pricing-publishPriceExpressIndex_content')) {
		return;
	}
	var queryForm = Ext.create('Foss.pricing.newPublishPriceExpress.QueryForm');//查询FORM
	queryForm.items.items[1].items.items[3].allowBlank = true;
	var publishPriceGrid = Ext.create('Foss.pricing.newPublishPriceExpress.PublishPriceGridPanel');//查询结果GRID
	Ext.getCmp('T_pricing-publishPriceExpressIndex').add(Ext.create('Ext.panel.Panel', {
		id : 'T_pricing-publishPriceExpressIndex_content',
		cls : 'panelContentNToolbar',
		bodyCls : 'panelContentNToolbar-body',
		//获得查询FORM
		getQueryForm : function() {
			return queryForm;
		},
		//获得查询结果GRID
		getPublishPriceGrid : function() {
			return publishPriceGrid;
		},
		items : [ queryForm, publishPriceGrid]
	}));
});


