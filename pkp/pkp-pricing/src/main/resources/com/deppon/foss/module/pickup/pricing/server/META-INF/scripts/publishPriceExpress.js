

//--------------------------------------pricing----------------------------------------
//公布价MODEL
Ext.define('Foss.pricing.publishPriceExpress.PublishPriceExpressEntity', {
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
        name : 'arrvRegionId',  //目的区域ID
        type : 'string'
    },{
        name : 'arrvRegionCode', //目的区域CODE
        type : 'string'
    },{
        name : 'arrvRegionName',//目的站
        type : 'string'
    },{
        name : 'productItemName',//条目名称
        type : 'string'
    },{
        name : 'arriveTime',//营运时效
        type : 'string'
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
        name : 'destinationCity',//目的城市
        type : 'string'
    }]
});
//------------------------------------model---------------------------------------------------
/**
 * 公布价Store
 */
Ext.define('Foss.pricing.publishPriceExpress.PublishPriceStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.pricing.publishPriceExpress.PublishPriceExpressEntity',//公布价MODEL
	pageSize:20,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url : "../pricing/searchPublishPriceExpressByCondition.action",
		reader : {
			type : 'json',
			root : 'publishPriceExpressVo.publishPriceExpressEntityList',
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
Ext.define('Foss.pricing.publishPriceExpress.QueryForm', {
	extend : 'Ext.form.Panel',
	title: pricing.publishPriceExpress.i18n('i18n.pricingRegion.searchCondition'),
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
		    fieldLabel:pricing.publishPriceExpress.i18n('foss.pricing.startingSector'),//出发部门
	        xtype : 'commonsaledeptandouterbranchselector'
		},{
			name: 'arrvRegionCode',
			allowBlank:false,
		    fieldLabel:pricing.publishPriceExpress.i18n('foss.pricing.destinationStation'),//目的站
	        xtype : 'commonsaledeptandouterbranchselector'
		},{
			xtype : 'container',
			margin : '0 0 0 0',
			items : [{
				xtype : 'button', 
				width:70,
				text : pricing.publishPriceExpress.i18n('i18n.pricingRegion.search'),
				disabled: !pricing.publishPriceExpress.isPermission('indexPublishPriceExpress/indexPublishPriceExpressQuerybutton'),
				hidden: !pricing.publishPriceExpress.isPermission('indexPublishPriceExpress/indexPublishPriceExpressQuerybutton'),
				handler : function() {
					if(me.getForm().isValid()){
						var grid = Ext.getCmp('T_pricing-indexPublishPriceExpress_content').getAreaGrid();//获取大查询GRID
						grid.getStore().load();
					}
				}
			}]
		}]
		me.callParent([cfg]);
	}
});
/**
 * 公布价列表
 */
Ext.define('Foss.pricing.publishPriceExpress.PublishPriceGridPanel', {
	extend: 'Ext.grid.Panel',
	title : pricing.publishPriceExpress.i18n('i18n.pricingRegion.searchResults'),
	frame: true,
    sortableColumns:false,
    enableColumnHide:false,
    enableColumnMove:false,
	stripeRows : true, // 交替行效果
	selType : "rowmodel", // 选择类型设置为：行选择
	emptyText: pricing.publishPriceExpress.i18n('foss.pricing.theQueryResultIsEmpty'),//查询结果为空
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.columns = [{xtype: 'rownumberer',
			width:40,
			text : pricing.publishPriceExpress.i18n('i18n.pricingRegion.num')//序号
		},{
			text : pricing.publishPriceExpress.i18n('foss.pricing.destinationStation'),//目的站
			dataIndex : 'arrvRegionName'
		},{
			text : pricing.publishPriceExpress.i18n('foss.pricing.productInformation'),//产品信息
			dataIndex : 'productItemName'
		},{
			text :pricing.publishPriceExpress.i18n('foss.pricing.firstWeight'),//改为首重
			dataIndex : 'firstWeight',
			renderer:function(value){
				if(!Ext.isEmpty(value)){
					return value;
				}
			}
		},{
		    text : pricing.publishPriceExpress.i18n('foss.pricing.continuedWeight1'),//续重1
		    columns: [{
		    	text : pricing.publishPriceExpress.i18n('foss.pricing.weightOffline'),//重量下线
		    	dataIndex: 'weightOffline1',
				renderer:function(value){
					if(!Ext.isEmpty(value)){
						return value;
					}
				}
		    }, {
		    	text : pricing.publishPriceExpress.i18n('foss.pricing.weightOnline'),//重量上线
		    	dataIndex: 'weightOnline1',
				renderer:function(value){
					if(!Ext.isEmpty(value)){
						return value;
					}
				}
		    }, {
		    	text : pricing.publishPriceExpress.i18n('foss.pricing.rates'),// 费率
		    	dataIndex: 'feeRate1',
				renderer:function(value){
					if(!Ext.isEmpty(value)){
						return value;
					}
				}
		    }]
		},{
		    text : pricing.publishPriceExpress.i18n('foss.pricing.continuedWeight2'),//续重2
		    columns: [{
		    	text : pricing.publishPriceExpress.i18n('foss.pricing.weightOffline'),//重量下线
		    	dataIndex: 'weightOffline2',
				renderer:function(value){
					if(!Ext.isEmpty(value)){
						return value;
					}
				}
		    }, {
		    	text : pricing.publishPriceExpress.i18n('foss.pricing.weightOnline'),//重量上线
		    	dataIndex: 'weightOnline2',
				renderer:function(value){
					if(!Ext.isEmpty(value)){
						return value;
					}
				}
		    }, {
		    	text : pricing.publishPriceExpress.i18n('foss.pricing.rates'),// 费率
		    	dataIndex: 'feeRate2',
				renderer:function(value){
					if(!Ext.isEmpty(value)){
						return value;
					}
				}
		    }]
		} 
		,{
			text :pricing.publishPriceExpress.i18n('foss.pricing.operatingAging'),//营运时效
			dataIndex : 'arriveTime',
			renderer:function(value){
				if(!Ext.isEmpty(value)){
					return value+pricing.publishPriceExpress.i18n('foss.pricing.day');
				}
			}
		}];
		me.store = Ext.create('Foss.pricing.publishPriceExpress.PublishPriceStore',{
			autoLoad : true,
			pageSize : 20,
			listeners: {
				beforeload: function(store, operation, eOpts){
					var queryForm = Ext.getCmp('T_pricing-indexPublishPriceExpress_content').getQueryForm();
					if(queryForm!=null){
						Ext.apply(operation,{
							params : {
								'publishPriceExpressVo.publishPriceExpressEntity.arrvRegionCode' : queryForm.getForm().findField('arrvRegionCode').getValue(),//目的站CODE
								'publishPriceExpressVo.publishPriceExpressEntity.startDeptCode' :  queryForm.getForm().findField('startDeptCode').getValue()//始发网点CODE
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
	if (Ext.getCmp('T_pricing-indexPublishPriceExpress_content')) {
		return;
	}
	var queryForm = Ext.create('Foss.pricing.publishPriceExpress.QueryForm');//查询FORM
	var publishPriceGrid = Ext.create('Foss.pricing.publishPriceExpress.PublishPriceGridPanel');//查询结果GRID
	Ext.getCmp('T_pricing-indexPublishPriceExpress').add(Ext.create('Ext.panel.Panel', {
		id : 'T_pricing-indexPublishPriceExpress_content',
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


