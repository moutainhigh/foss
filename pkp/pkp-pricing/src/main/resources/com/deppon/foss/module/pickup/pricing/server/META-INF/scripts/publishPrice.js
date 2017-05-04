

//--------------------------------------pricing----------------------------------------
//公布价MODEL
Ext.define('Foss.pricing.publishPrice.PublishPriceEntity', {
    extend: 'Ext.data.Model',
    fields : [{
        name : 'arrvRegionId',  //目的区域ID
        type : 'string'
    },{
        name : 'arrvRegionCode', //目的区域CODE
        type : 'string'
    },{
        name : 'arrvRegionName',//目的站
        type : 'string'
    },{
        name : 'productItemCode',//条目编号
        type : 'string'
    },{
        name : 'productItemName',//条目名称
        type : 'string'
    },{
        name : 'goodsTypeName',//货物类型
        type : 'string'
    },{
        name : 'flightShiftNo',//航班号
        type : 'string'
    },{
    	name : 'combBillTypeCode',	//合票类型code
    	type : 'string'
    },{
    	name : 'combBillTypeName',	//合票类型名称
    	type : 'string'
    },{
        name : 'arriveTime',//营运时效
        type : 'string'
    },{
        name : 'effectiveUnit',//营运时效单位
        type : 'string'
    },{
        name : 'pickupTime',//取货时间
        type : 'string'
    },{
        name : 'longOrShort',//长短途 
        type : 'string'
    },{
        name : 'minFee',//最低一票
        defaultValue : null
    },{
        name : 'heavyPrice',//重货价格
        defaultValue : null
    },{
        name : 'lightPrice',//轻货价格
        defaultValue : null
    },{
        name : 'startDept',//出发部门
        type : 'string'
    },{
        name : 'startDeptId',//出发部门ID
        type : 'string'
    },{
        name : 'startDeptCode', //出发部门CODE
        type : 'string'
    },{
        name : 'centralizePickup', //是否集中接货
        type : 'string'
    },{
        name : 'destinationCity',//目的城市
        type : 'string'
    },{
    	name : 'deliveryCharges', //送货费
    	type : 'string'
	 },{
    	name : 'heavyFeeRateStr', //重货价格字符串形式
    	type : 'string'
    },{
    	name : 'lightFeeRateStr', //轻货价格字符串形式
    	type : 'string'
    },{
    	name:'popPublicPriceDtoList'
    }]
});
//------------------------------------model---------------------------------------------------
/**
 * 公布价Store
 */
Ext.define('Foss.pricing.publishPrice.PublishPriceStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.pricing.publishPrice.PublishPriceEntity',//公布价MODEL
	pageSize:20,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url : "../pricing/searchPublishPriceByCondition.action",
		reader : {
			type : 'json',
			root : 'publishPriceVo.publishPriceEntityList',
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
 * 公布价查询表单
 */
Ext.define('Foss.pricing.publishPrice.QueryForm', {
	extend : 'Ext.form.Panel',
	title: pricing.publishPrice.i18n('i18n.pricingRegion.searchCondition'),
	frame: true,
	collapsible: true,
    defaults : {
    	columnWidth : .3,
    	margin : '8 10 5 10',
    	anchor : '100%'
    },
    height :100,
	defaultType : 'textfield',
	layout:'column',
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.items  = [{
			name: 'startDeptCode',
			allowBlank:false,
		    fieldLabel:pricing.publishPrice.i18n('foss.pricing.startingSector'),//出发部门
	        xtype : 'commonsaledeptandouterbranchselector'
		},{
			name: 'arrvRegionCode',
			allowBlank:false,
		    fieldLabel:pricing.publishPrice.i18n('foss.pricing.destinationStation'),//目的站
		    types : 'ORG,PX,KY',
	        xtype : 'commonsaledeptandouterbranchselector'
		},{
			xtype : 'container',
			margin : '0 0 0 0',
			items : [{
				xtype : 'button', 
				cls:'yellow_button',
				width:70,
				text : pricing.publishPrice.i18n('i18n.pricingRegion.search'),
				disabled: !pricing.publishPrice.isPermission('indexPublishPrice/indexPublishPriceQuerybutton'),
				hidden: !pricing.publishPrice.isPermission('indexPublishPrice/indexPublishPriceQuerybutton'),
				handler : function() {
					if(me.getForm().isValid()){
						var grid = Ext.getCmp('T_pricing-indexPublishPrice_content').getAreaGrid();//获取大查询GRID
						grid.getStore().load();
					}
				}
			},{
				xtype : 'button', 
				width:70,
				text : pricing.publishPrice.i18n('foss.pricing.reset'),//重置
				handler : function() {
					var form = this.up('form').getForm();
					form.findField('startDeptCode').setValue("");
					form.findField('arrvRegionCode').setValue("");
				}
			}]
		}]
		me.callParent([cfg]);
	}
});
/**
 * 公布价列表
 */
Ext.define('Foss.pricing.publishPrice.PublishPriceGridPanel', {
	extend: 'Ext.grid.Panel',
	title : pricing.publishPrice.i18n('i18n.pricingRegion.searchResults'),
	frame: true,
	cls: 'autoHeight',
	bodyCls: 'autoHeight', 
    sortableColumns:false,
    enableColumnHide:false,
    enableColumnMove:false,
	stripeRows : true, // 交替行效果
	selType : "rowmodel", // 选择类型设置为：行选择
	emptyText: pricing.publishPrice.i18n('foss.pricing.theQueryResultIsEmpty'),//查询结果为空
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.columns = [{xtype: 'rownumberer',
			flex:0.01,
			align : 'center',
			text : pricing.publishPrice.i18n('i18n.pricingRegion.num')//序号
		},{
			text : pricing.publishPrice.i18n('foss.pricing.destinationStation'),//目的站
			align : 'center',
			flex:0.12,
			dataIndex : 'arrvRegionName'
		},{
			text : pricing.publishPrice.i18n('foss.pricing.productInformation'),//产品信息
			align : 'center',
			flex:0.1,
			dataIndex : 'productItemName'
		},{
			text : pricing.publishPrice.i18n('foss.pricing.goodsType'),//货物类型
			align : 'center',
			flex:0.1,
			dataIndex : 'goodsTypeName'
		},{
			text : pricing.publishPrice.i18n('foss.pricing.publishPrice.hangban') + '<br>' + pricing.publishPrice.i18n('foss.pricing.publishPrice.leibie'),//航班类型
			align : 'center',
			flex:0.045,
			dataIndex : 'flightShiftNo'
		},{
			text :pricing.publishPrice.i18n('foss.pricing.publishPrice.hepiao') + '<br>' + pricing.publishPrice.i18n('foss.pricing.publishPrice.fangshi'),//合票类型
			flex:0.045,
			dataIndex : 'combBillTypeName'
		},{
			text : pricing.publishPrice.i18n('foss.pricing.publishPrice.shifou') + '<br>' + pricing.publishPrice.i18n('foss.pricing.publishPrice.jiehuo'),//集中接货
			align : 'center',
			flex:0.015,
			dataIndex : 'centralizePickup',
			renderer:function(value){
				if(value=='Y'){
					return pricing.publishPrice.i18n('foss.pricing.isYes');//是
				}else if(value=='N'){
					return pricing.publishPrice.i18n('foss.pricing.isNo');//否
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
					return val[0].lightPrice;
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
			text :pricing.publishPrice.i18n('foss.pricing.operatingAging'),//营运时效
			align : 'center',
			flex:0.07,
			dataIndex : 'arriveTime',
			renderer:function(value,obj,record){
				if(!Ext.isEmpty(value)){
					if(record.get('effectiveUnit')=='DAY'){
						return value+pricing.publishPrice.i18n('foss.pricing.day');
					}
					if(record.get('effectiveUnit')=='HOURS'){
						return value+'小时';
					}
					return value;
				}
			}
		},{
			text :pricing.publishPrice.i18n('foss.pricing.pickUpTime'),//取货时间
			align : 'center',
			flex:0.06,
			dataIndex : 'pickupTime'
		},{
			text :pricing.publishPrice.i18n('foss.pricing.shortAndLongDistance'),//长短途
			align : 'center',
			flex:0.04,
			dataIndex : 'longOrShort'
		},{
			text :pricing.publishPrice.i18n('foss.pricing.theLowestVotes'),//最低一票
			align : 'center',
			flex:0.08,
			dataIndex : 'minFee'
		},{
			text :pricing.publishPrice.i18n('foss.pricing.publishPrice.deliverFee') + '<br>' + pricing.publishPrice.i18n('foss.pricing.publishPrice.flagDownFare'),//'送货费起步价'
			align : 'center',
			flex:0.05,
			dataIndex : 'deliveryCharges'
		}];
		me.store = Ext.create('Foss.pricing.publishPrice.PublishPriceStore',{
			autoLoad : false,
			pageSize : 20,
			listeners: {
				beforeload: function(store, operation, eOpts){
					var queryForm = Ext.getCmp('T_pricing-indexPublishPrice_content').getQueryForm();
					if(queryForm!=null){
						Ext.apply(operation,{
							params : {
								'publishPriceVo.publishPriceEntity.startDeptCode':queryForm.getForm().findField('startDeptCode').getValue(),//始发网点CODE
								'publishPriceVo.publishPriceEntity.arrvRegionCode':queryForm.getForm().findField('arrvRegionCode').getValue()//目的站CODE
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
 * @description 公布价主页
 */
Ext.onReady(function() {
	Ext.QuickTips.init();
	if (Ext.getCmp('T_pricing-indexPublishPrice_content')) {
		return;
	}
	var queryForm = Ext.create('Foss.pricing.publishPrice.QueryForm');//查询FORM
	var publishPriceGrid = Ext.create('Foss.pricing.publishPrice.PublishPriceGridPanel');//查询结果GRID
	Ext.getCmp('T_pricing-indexPublishPrice').add(Ext.create('Ext.panel.Panel', {
		id : 'T_pricing-indexPublishPrice_content',
		cls : 'panelContentNToolbar',
		bodyCls : 'panelContentNToolbar-body',
		//获得查询FORM
		getQueryForm : function() {
			return queryForm;
		},
		//获得查询结果GRID
		getAreaGrid : function() {
			return publishPriceGrid;
		},
		items : [ queryForm, publishPriceGrid]
	}));
});


