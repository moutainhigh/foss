

//--------------------------------------pricing----------------------------------------
//公布价MODEL
Ext.define('Foss.pricing.newPublishPrice.PublicPriceDto', {
    extend: 'Ext.data.Model',
    fields : [{
        name : 'productCode',  //三级产品CODE
        type : 'string'
    },{
        name : 'productName', //产品名称
        type : 'string'
    },{
    	name : 'combBillTypeCode',	//合票类型code
    	type : 'string'
    },{
    	name : 'combBillTypeName',	//合票类型名称
    	type : 'string'
    },{
        name : 'flightShiftNo', //航班号
        type : 'string'
    },{
        name : 'deptRegionId',//时效始发区域ID
        type : 'string'
    },{
        name : 'deptRegionCode',//时效始发区域CODE
        type : 'string'
    },{
        name : 'deptRegionName',//时效始发区域名称
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
        name : 'arrvRegionId',//时效到达区域ID
        type : 'string'
    },{
        name : 'arrvRegionCode',//时效到达区域CODE
        type : 'string'
    },{
        name : 'arrvRegionName',//时效到达区域名称
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
        name : 'maxTime',//承诺最长时间
        type : 'string'
    },{
        name : 'maxTimeUnit',//承诺最长时间单位
        type : 'string'
    },{
        name : 'minTime',//承诺最短时间
        type : 'string'
    },{
        name : 'minTimeUnit',//承诺最短时间单位
        type : 'string'
    },{
        name : 'arriveTime',//承诺到达营业部时间
        type : 'string'
    },{
        name : 'addDay',//派送承诺需加天数
        type : 'string'
    },{
        name : 'deliveryTime',//派送承诺时间
        type : 'string'
    },{
        name : 'hasSalesDept',//是否有驻地部门
        type : 'string'
    },{
        name : 'longOrShort', //长途短途
        type : 'string'
    },{
        name : 'goodsTypeCode',//货物类型
        type : 'string'
    },{
        name : 'goodsTypeName',//货物类型名称
        type : 'string'
    },{
        name : 'centralizePickup',//是否接送货
        type : 'string'
    },{
        name : 'lightFeeRate',// 轻货费率（小数）或者单价（分）
        type : 'string'
    },{
        name : 'heavyFeeRate',//重货费率（小数）或者单价（分）
        type : 'string'
    },{
        name : 'minFee',//最低费用
        type : 'string'
    },{
        name : 'lightFeeRatePickUpYes',//接送货轻货费率（小数）或者单价（分）
        type : 'string'
    },{
        name : 'heavyFeeRatePickUpYes',//接送货重货费率（小数）或者单价（分）
        type : 'string'
    },{
        name : 'lightFeeRatePickUpNo',//非接送货轻货费率（小数）或者单价（分）
        type : 'string'
    },{
        name : 'heavyFeeRatePickUpNo',//非接送货重货费率（小数）或者单价（分）
        type : 'string'
    },{
        name : 'minFeePickUpYes',//接送货最低一票
        type : 'string'
    },{
        name : 'minFeePickUpNo',//非接送货最低一票
        type : 'string'
    },{
        name : 'pickTime',//取货时间
        type : 'string'
    },{
        name : 'centralizePickup', //是否集中接货
        type : 'string'
    },{
        name : 'deptCodes'//到达网点集合
    },{
        name : 'startDeptCodes'//始发网点集合
    },{
        name : 'priceStartDeptCodes'//价格始发网点集合
    },{
        name : 'priceArrvDeptCodes'//价格到达网点集合
    },{
        name : 'regionNature',//区域类型
        type : 'string'
    },{
        name : 'lightFeeRateStr',// 轻货费率（小数）或者单价（分）
        type : 'string'
    },{
        name : 'heavyFeeRateStr',//重货费率（小数）或者单价（分）
        type : 'string'
    },{
    	name:'popPublicPriceDtoList'
    }]
});

//网点MODEL
Ext.define('Foss.pricing.newPublishPrice.SaleDepartmentInfoDto', {
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
pricing.newPublishPrice.PRESCRIPTION_REGION = 'EFFECTIVE_REGION';
//价格区域
pricing.newPublishPrice.PRICING_REGION = 'PRICING_REGION';
//价格出发区域ID
pricing.newPublishPrice.PRICING_START_REGION_ID='PRICING_START_REGION_ID';
//价格到达区域ID
pricing.newPublishPrice.PRICING_ARRIVE_REGION_ID='PRICING_ARRIVE_REGION_ID';

//超链接点击区域时显示区域关联网点的WINDOW
pricing.showNetPoint = function(id){
	var grid = Ext.getCmp('T_pricing-publishPriceIndex_content').getPublishPriceGrid();
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

pricing.showStartNetPoint = function(id){
	var grid = Ext.getCmp('T_pricing-publishPriceIndex_content').getPublishPriceGrid();
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

pricing.showArrvNetPointPrice = function(id){
	var grid = Ext.getCmp('T_pricing-publishPriceIndex_content').getPublishPriceGrid();
	var deptCodes = [];
	var regionId;
	var regionNature;
	var productCode;
	var priceRegionIdClass;
	grid.getStore().each(function(record){
		if(record.get('id')==id){
			deptCodes = record.get('priceArrvDeptCodes');
			regionId = record.get('arrvPriceRegionId');
			regionNature = record.get('regionNature');
			productCode = record.get('productCode');
		}
	});
	var showNetPointWindow = grid.getShowNetPointWindow();
	showNetPointWindow.deptCodes = deptCodes;
	showNetPointWindow.deptRegionId = regionId;
	showNetPointWindow.productCode = productCode;
	showNetPointWindow.priceRegionIdClass=pricing.newPublishPrice.PRICING_ARRIVE_REGION_ID;
	
	if(regionNature == pricing.newPublishPrice.PRESCRIPTION_REGION) {
		regionNature = pricing.newPublishPrice.PRICING_REGION;
	}
	showNetPointWindow.regionNature = regionNature;
	showNetPointWindow.show();
};

pricing.showStartNetPointPrice = function(id){
	var grid = Ext.getCmp('T_pricing-publishPriceIndex_content').getPublishPriceGrid();
	var startDeptCodes = [];
	var regionId;
	var regionNature;
	var productCode;
	var priceRegionIdClass;
	grid.getStore().each(function(record){
		if(record.get('id')==id){
			startDeptCodes = record.get('priceStartDeptCodes');
			regionId = record.get('deptPriceRegionId');
			regionNature = record.get('regionNature');
			productCode = record.get('productCode');
		}
	});
	var showNetPointWindow = grid.getShowNetPointWindow();
	showNetPointWindow.deptCodes = startDeptCodes;
	showNetPointWindow.deptRegionId = regionId;
	showNetPointWindow.productCode = productCode;
	showNetPointWindow.priceRegionIdClass=pricing.newPublishPrice.PRICING_START_REGION_ID;
	
	if(regionNature == pricing.newPublishPrice.PRESCRIPTION_REGION) {
		regionNature = pricing.newPublishPrice.PRICING_REGION;
	}
	showNetPointWindow.regionNature = regionNature;
	showNetPointWindow.show();
};



//------------------------------------model---------------------------------------------------
/**
 * 公布价Store
 */
Ext.define('Foss.pricing.newPublishPrice.PublishPriceStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.pricing.newPublishPrice.PublicPriceDto',//公布价MODEL
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url : pricing.realPath('searchNewPublishPriceByCondition.action'),
		reader : {
			type : 'json',
			root : 'newPublishPriceVo.publicPriceDtoList'
		}
	}
});
/**
 * 网点Store
 */
Ext.define('Foss.pricing.newPublishPrice.NetPointStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.pricing.newPublishPrice.SaleDepartmentInfoDto',//网点MODEL
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
Ext.define('Foss.pricing.newPublishPrice.QueryForm', {
	extend : 'Ext.form.Panel',
	title: pricing.newPublishPrice.i18n('i18n.pricingRegion.searchCondition'),
	frame: true,
	collapsible: true,
	layout : {
		type : 'table',
		columns : 3
	},
	defaults:{
		margin : '5 5 5 0',
		labelWidth : 120,
		colspan : 1 
	}, 
	defaultType : 'textfield',
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.items  = [{
						forceSelection : true,
						xtype : 'commoncityselector',
						name: 'startCityCode',
						allowBlank:false,
					    fieldLabel:pricing.newPublishPrice.i18n('foss.pricing.departureCity')//出发城市
					},{
						xtype : 'textfield',
						margin : '8 0 5 0',
						name: 'destinationAddress',
					    fieldLabel:pricing.newPublishPrice.i18n('foss.pricing.address')//详细地址
					}, {
						xtype : 'combobox',
						name : 'flightSort',
						fieldLabel : pricing.newPublishPrice.i18n('foss.pricing.flightType'),
						displayField : 'valueName',
						valueField : 'valueCode',
						queryMode : 'local',
						triggerAction : 'all',
						editable : false,
						store : FossDataDictionary.getDataDictionaryStore('AIR_FLIGHT_TYPE', 'Foss_baseinfo_flight_FlightSortStore_Id', {
							'valueCode' : '',
							'valueName' : pricing.newPublishPrice.i18n('i18n.pricingRegion.all')
						}),
						value : ''
					},{
			   		   xtype:'linkregincombselector',
			   		   colspan : 4,
			   		   margin : '8 0 5 0',
			   		   columnWidth : 0.75,
			   		   allowBlank:false,
			   		   fieldLabel:pricing.newPublishPrice.i18n('foss.pricing.destination')//所在地
				   	}];
		me.fbar = [{
						xtype : 'button', 
						colspan : 1,
						margin:'0 0 0 845',
						cls:'yellow_button',
						text : pricing.newPublishPrice.i18n('i18n.pricingRegion.search'),
						disabled: !pricing.newPublishPrice.isPermission('publishPriceIndex/publishPriceIndexQuerybutton'),
						hidden: !pricing.newPublishPrice.isPermission('publishPriceIndex/publishPriceIndexQuerybutton'),
						handler : function() {
							if(me.getForm().isValid()){
								var grid = Ext.getCmp('T_pricing-publishPriceIndex_content').getPublishPriceGrid();//获取大查询GRID
								grid.getStore().load();
							}
						}
					}, {
						xtype : 'button', 
						colspan : 1,
						text : pricing.newPublishPrice.i18n('foss.pricing.reset'),//重置
						handler : function() {
							me.getForm().reset();
						}
					}, {
						xtype: 'hidden',
						colspan: 2
					}]
		me.callParent([cfg]);
	}
});
/**
 * 公布价列表
 */
Ext.define('Foss.pricing.newPublishPrice.PublishPriceGridPanel', {
	extend: 'Ext.grid.Panel',
	title : pricing.newPublishPrice.i18n('i18n.pricingRegion.searchResults'),
	frame: true,
	cls: 'autoHeight',
	bodyCls: 'autoHeight', 
    sortableColumns:false,
    enableColumnHide:false,
    enableColumnMove:false,
	stripeRows : true, // 交替行效果
	selType : "rowmodel", // 选择类型设置为：行选择
	emptyText: pricing.newPublishPrice.i18n('foss.pricing.theQueryResultIsEmpty'),//查询结果为空
	showNetPointWindow:null,
	//得到区域关联网点数据
    getShowNetPointWindow: function(){
    	if(Ext.isEmpty(this.showNetPointWindow)){
    		this.showNetPointWindow = Ext.create('Foss.pricing.newPublishPrice.ShowNetPointWindow');
    	}
    	return this.showNetPointWindow;
    },
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.columns = [{xtype: 'rownumberer',
			width:40,
			text : pricing.newPublishPrice.i18n('i18n.pricingRegion.num')//序号
		},{
			text : pricing.newPublishPrice.i18n('foss.pricing.originatingEffectiveArea'),//时效始发区域
			dataIndex : 'deptRegionName',
			renderer:function(value,obj,record){
				var id = record.get('id');
				return '<a href="javascript:pricing.showStartNetPoint('+"'"+id+"'"+');">'+value+'</a>';
			}
		},{
			text : pricing.newPublishPrice.i18n('foss.pricing.destinationEffectiveArea'),//时效目的区域
			dataIndex : 'arrvRegionName',
			renderer:function(value,obj,record){
				var id = record.get('id');
				return '<a href="javascript:pricing.showNetPoint('+"'"+id+"'"+');">'+value+'</a>';
			}
		},{
			text : pricing.newPublishPrice.i18n('foss.pricing.originatingPriceArea'),//价格始发区域
			dataIndex : 'deptPriceRegionName',
			renderer:function(value,obj,record){
				var id = record.get('id');
				return '<a href="javascript:pricing.showStartNetPointPrice('+"'"+id+"'"+');">'+value+'</a>';
			}
		},{
			text : pricing.newPublishPrice.i18n('foss.pricing.destinationPriceArea'),//价格目的区域
			dataIndex : 'arrvPriceRegionName',
			renderer:function(value,obj,record){
				var id = record.get('id');
				return '<a href="javascript:pricing.showArrvNetPointPrice('+"'"+id+"'"+');">'+value+'</a>';
			}
		},{
			text :pricing.newPublishPrice.i18n('foss.pricing.productInformation'),//产品名称
			dataIndex : 'productName'
		},{
			text :pricing.newPublishPrice.i18n('foss.pricing.theTypeGoods'),//货物类型
			dataIndex : 'goodsTypeName'
		},{
			text :pricing.newPublishPrice.i18n('foss.pricing.combBillType'),//合票类型
			dataIndex : 'combBillTypeName'
		},{
			text :pricing.newPublishPrice.i18n('foss.pricing.flightCategory'),//航班类型
			dataIndex : 'flightShiftNo'
		},{
			text :pricing.newPublishPrice.i18n('foss.pricing.whetherAcceptGoods'),//集中接货
			dataIndex : 'centralizePickup',
			renderer:function(value){
				if(value=='Y'){
					return pricing.newPublishPrice.i18n('foss.pricing.isYes');//是
				}else if(value=='N'){
					return pricing.newPublishPrice.i18n('foss.pricing.isNo');//否
				}else{
					return value;
				}
			}
		},{
			text:'重货价格',
			dataIndex:'popPublicPriceDtoList',
			renderer:function(val,meta,record){
				if(Ext.isEmpty(val)){
					return record.get('heavyFeeRateStr');
				}
				if(val.length==1){
					return val[0].heavyPrice
				}else if(val.length>1){
					var str = '';
					Ext.Array.each(val,function(e){
						if(str){
							str+='<br/>'
						}
						str+=('重货范围'+e.sectionId+':'+e.heavyRange+',价格:'+e.heavyPrice);
					});
					return str;
				}
				return val;
			}
		},{
			text:'轻货价格',
			dataIndex:'popPublicPriceDtoList',
			renderer:function(val,meta,record){
				if(Ext.isEmpty(val)){
					return record.get('lightFeeRateStr');
				}
				if(val.length==1){
					return val[0].lightPrice
				}else if(val.length>1){
					var str = '';
					Ext.Array.each(val,function(e){
						if(str){
							str+='<br/>'
						}
						str+=('轻货范围'+e.sectionId+':'+e.lightRange+',价格:'+e.lightPrice);
					});
					return str;
				}
				return val;
			}
		},{
			text :pricing.newPublishPrice.i18n('foss.pricing.operatingAging'),//营运时效
			dataIndex : 'arriveTime'
		},{
			text :pricing.newPublishPrice.i18n('foss.pricing.pickUpTime'),//取货时间
			dataIndex : 'pickTime'
		},{
			text :pricing.newPublishPrice.i18n('foss.pricing.deliveryIncreaseTheNumberOfDays'),//取货时间
			dataIndex : 'addDay'
		},{
			text :pricing.newPublishPrice.i18n('foss.pricing.deliveryTime'),//取货时间
			dataIndex : 'deliveryTime'
		},{
			text :pricing.newPublishPrice.i18n('foss.pricing.whetherTheResidentSecto'),//取货时间
			dataIndex : 'hasSalesDept',
			renderer:function(value){
				if(value=='Y'){
					return pricing.newPublishPrice.i18n('i18n.pricingRegion.ye');//是
				}else if(value=='N'){
					return pricing.newPublishPrice.i18n('i18n.pricingRegion.no');//否
				}else{
					return value;
				}
			}
		},{
			text :pricing.newPublishPrice.i18n('foss.pricing.shortAndLongDistance'),//长短途
			dataIndex : 'longOrShort',
			renderer:function(value){
				if(value=='L'){
					return pricing.newPublishPrice.i18n('foss.pricing.longDistance');//长途
				}else if(value=='S'){
					return pricing.newPublishPrice.i18n('foss.pricing.shortDistance');//短途
				}else{
					return value;
				}
			}
		},{
			text :pricing.newPublishPrice.i18n('foss.pricing.theLowestVotes'),//最低一票
			dataIndex : 'minFee'
		}];
		me.store = Ext.create('Foss.pricing.newPublishPrice.PublishPriceStore',{
			autoLoad : false,
			listeners: {
				beforeload: function(store, operation, eOpts){
					var queryForm = Ext.getCmp('T_pricing-publishPriceIndex_content').getQueryForm();
					var destinationCountryCode = queryForm.items.items[3].items.items[0].getValue();//国家
					var destinationProvinceCode =queryForm.items.items[3].items.items[1].getValue();//省
		    		var destinationCityCode = queryForm.items.items[3].items.items[2].getValue();//市
		    		var destinationCountyCode = queryForm.items.items[3].items.items[3].getValue();//区县
					if(queryForm!=null){
						Ext.apply(operation,{
							params : {
								'newPublishPriceVo.startCityCode':queryForm.getForm().findField('startCityCode').getValue(),//始发城市CODE
								'newPublishPriceVo.flightSort':queryForm.getForm().findField('flightSort').getValue(),//航班类型flightSort
								'newPublishPriceVo.destinationCountryCode':destinationCountryCode,//始发城市CODE
								'newPublishPriceVo.destinationProvinceCode':destinationProvinceCode,//始发城市CODE
								'newPublishPriceVo.destinationCityCode':destinationCityCode,//始发城市CODE
								'newPublishPriceVo.destinationCountyCode':destinationCountyCode,//始发城市CODE
								'newPublishPriceVo.destinationAddress':queryForm.getForm().findField('destinationAddress').getValue()
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
Ext.define('Foss.pricing.newPublishPrice.ShowNetPointWindow',{
	extend : 'Ext.window.Window',
	title : pricing.newPublishPrice.i18n('foss.pricing.regionCorrespondingOutlets'),
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
	productCode:null,
	priceRegionIdClass:null,
	listeners:{
		beforehide:function(me){
			me.getPublishPriceCityGrid().getStore().removeAll();
			me.regionNature=null;
			me.productCode=null;
			me.productCode =null;
			me.deptRegionId =null;
		},
		beforeshow:function(me){//根据区域ID和区域性质，查询区域关联部门
			var params = {'newPublishPriceVo':{'deptCodes':me.deptCodes, 'deptRegionId':me.deptRegionId, 'regionNature':me.regionNature,'productCode':me.productCode,'priceRegionIdClass':me.priceRegionIdClass}};
			var successFun = function(json){
				me.getPublishPriceCityGrid().getStore().add(json.newPublishPriceVo.saleDepartmentInfoDtoList);
			};
			var failureFun = function(json){
				if(Ext.isEmpty(json)){
					pricing.showErrorMes(pricing.newPublishPrice.i18n('i18n.pricingRegion.requestTimeOut'));
				}else{
					pricing.showErrorMes(json.message);
				}
			};
			var url = pricing.realPath('queryOuterAndDepartment.action');
			pricing.requestJsonAjax(url,params,successFun,failureFun);
		}
	},
	//得到区域关联网点数据
    getPublishPriceCityGrid: function(){
    	if(Ext.isEmpty(this.publishPriceCityGrid)){
    		this.publishPriceCityGrid = Ext.create('Foss.pricing.newPublishPrice.PublishPriceCityGridPanel');
    	}
    	return this.publishPriceCityGrid;
    },
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [ me.getPublishPriceCityGrid()];
		me.fbar = [{
			text : pricing.newPublishPrice.i18n('i18n.pricingRegion.returnGrid'),//返回列表，其实就是隐藏窗口
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
Ext.define('Foss.pricing.newPublishPrice.PublishPriceCityGridPanel', {
	extend: 'Ext.grid.Panel',
	title : pricing.newPublishPrice.i18n('foss.pricing.regionCorrespondingOutlets'),
	frame: true,
	height:240,
	autoScroll:true,
    sortableColumns:false,
    enableColumnHide:false,
    enableColumnMove:false,
	stripeRows : true, // 交替行效果
	selType : "rowmodel", // 选择类型设置为：行选择
	emptyText: pricing.newPublishPrice.i18n('foss.pricing.theQueryResultIsEmpty'),//查询结果为空
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.columns = [{xtype: 'rownumberer',
			width:40,
			text : pricing.newPublishPrice.i18n('i18n.pricingRegion.num')//序号
		},{
			text : pricing.newPublishPrice.i18n('foss.pricing.outletsReferredTo'),//网点简称
			dataIndex : 'simpleName'
		},{
			text : pricing.newPublishPrice.i18n('foss.pricing.networkName'),//网点名称
			dataIndex : 'name'
		},{
			text :pricing.newPublishPrice.i18n('foss.pricing.deliveryPointAddress'),//提货点地址
			dataIndex : 'address'
		},{
			text : pricing.newPublishPrice.i18n('foss.pricing.tel'),//联系电话
			dataIndex : 'telephone'
		},{
			text :pricing.newPublishPrice.i18n('foss.pricing.networkType'),//网点类别
			dataIndex : 'orgType',
			renderer:function(value){
				if(value=='ORG'){
					return pricing.newPublishPrice.i18n('foss.pricing.salesDepartment');//营业部
				}else if(value=='PX'){
					return pricing.newPublishPrice.i18n('foss.pricing.thePartialLineAgentOutlets');//偏线代理网点
				}else if(value=='KY'){
					return pricing.newPublishPrice.i18n('foss.pricing.airFreightForwardingOutlets');//空运代理网点
				}else{
					return value;
				}
			}
		},{
			text :pricing.newPublishPrice.i18n('foss.pricing.whetherDoorToDoor'),//是否送货上门
			dataIndex : 'pickupSelf',
			renderer:function(value){
				if(value=='Y'){
					return pricing.newPublishPrice.i18n('i18n.pricingRegion.ye');//是
				}else if(value=='N'){
					return pricing.newPublishPrice.i18n('i18n.pricingRegion.no');//否
				}else{
					return value;
				}
			}
		},{
			text :pricing.newPublishPrice.i18n('foss.pricing.whetherFromMentioning'),//是否可以自提
			dataIndex : 'delivery',
			renderer:function(value){
				if(value=='Y'){
					return pricing.newPublishPrice.i18n('i18n.pricingRegion.ye');//是
				}else if(value=='N'){
					return pricing.newPublishPrice.i18n('i18n.pricingRegion.no');//否
				}else{
					return value;
				}
			}
		}];
		me.store = Ext.create('Foss.pricing.newPublishPrice.NetPointStore');
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
	if (Ext.getCmp('T_pricing-publishPriceIndex_content')) {
		return;
	}
	var queryForm = Ext.create('Foss.pricing.newPublishPrice.QueryForm');//查询FORM
//	queryForm.items.items[1].items.items[3].allowBlank = true;
	var publishPriceGrid = Ext.create('Foss.pricing.newPublishPrice.PublishPriceGridPanel');//查询结果GRID
	Ext.getCmp('T_pricing-publishPriceIndex').add(Ext.create('Ext.panel.Panel', {
		id : 'T_pricing-publishPriceIndex_content',
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


