/*
 * Ajax请求--json
 */
baseinfo.requestJsonAjax = function (url, params, successFn, failFn) {
	Ext.Ajax.request({
		url : url,
		jsonData : params,
		success : function (response) {
			var result = Ext.decode(response.responseText);
			if (result.success) {
				successFn(result);
			} else {
				failFn(result);
			}
		},
		failure : function (response) {
			var result = Ext.decode(response.responseText);
			failFn(result);
		},
		exception : function (response) {
			var result = Ext.decode(response.responseText);
			failFn(result);
		}
	});
};

//--------------------------------------baseinfo----------------------------------------
//公布价MODEL
Ext.define('Foss.baseinfo.FreightRouteSearchEntity', {
    extend: 'Ext.data.Model',
    fields : [{
        name : 'sourceName',  //出发部门名称
        type : 'string'
    },{
        name : 'targetName',  //到达部门名称
        type : 'string'
    },{
        name : 'lineName',  //对应的线路名称
        type : 'string'
    },{
        name : 'sourceCode',  //出发部门编码
        type : 'string'
    },{
        name : 'targetCode', //到达部门编码
        type : 'string'
    },{
        name : 'lineVirtualCode',//对应的线路虚拟编码
        type : 'string'
    },{
        name : 'transType',// 运输类型（汽运，空运）-始发到达
        type : 'string'
    },{
        name : 'lineType',// 线路类型（专线，偏线，空运）-中转
        type : 'string'
    },{
        name : 'lineSort',// 线路类别 （始发，到达，中转到中转）
        type : 'string'
    },{
        name : 'aging',//时效（单位：分钟）
        type : 'string'
    },{
        name : 'leaveDate',// 计划出发时间
        type : 'date',
        defaultValue : null,
        convert : baseinfo.changeLongToDate
    },{
        name : 'arriveDate',// 计划到达时间
        type : 'date',
        defaultValue : null,
        convert : baseinfo.changeLongToDate
    },{
        name : 'passbyDate',// 经停时间（到达以后的时间再加上经停时间）
        type : 'date',
        defaultValue : null,
        convert : baseinfo.changeLongToDate
    }]
});
//基础产品明细MODEL
Ext.define('Foss.baseinfo.ProductEntity', {
    extend: 'Ext.data.Model',
    fields : [{
        name : 'id',
        type : 'string'
    },{
        name : 'code',//产品CODE
        type : 'string'
    },{
        name : 'name',//产品名称
        type : 'string'
    },{
        name : 'active',//产品激活状态
        type : 'string'
    },{
        name : 'description',//产品备注
        type : 'string'
    },{
        name : 'transportType', //产品性质(空运/汽运)
        type : 'string'
    },{
        name : 'levels', //产品层级
        type : 'int'
    },{
        name : 'parentCode', //父级产品编号
        type : 'string'
    },{
        name : 'refId',//父级产品ID
        type : 'string'
    },{
        name : 'shortName',//简称
        type : 'string'
    },{
        name : 'priority',//优先级  	快-慢
        type : 'string'
    },{
        name : 'seq',//显示排序用
        type : 'int'
    },{
        name : 'createDate',//创建时间
        type : 'date',
        defaultValue : null,
        convert : baseinfo.changeLongToDate
    },{
        name : 'createUserName',//创建人姓名
        type : 'string'
    },{
        name : 'modifyDate',//修改时间
        type : 'date',
        defaultValue : null,
        convert : baseinfo.changeLongToDate
    },{
        name : 'modifyUserName',//修改人姓名
        type : 'string'
    }]
});
baseinfo.freightRouteSearch.threeLevelProductFreightRoute = [];//三级产品
//------------------------------------model---------------------------------------------------
/**
 * 走货路径查询Store
 */
Ext.define('Foss.baseinfo.FreightRouteSearchStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.baseinfo.FreightRouteSearchEntity',//走货路径查询MODEL
	data :[]
});
//三级产品
baseinfo.searchThreeLeveProduct = function(){
	Ext.Ajax.request({
		url:baseinfo.realPath('../pricing/findThreeLevelProduct.action'),
		async:false,
		jsonData:{},
		success:function(response){
			var result = Ext.decode(response.responseText);
			baseinfo.freightRouteSearch.threeLevelProductFreightRoute = result.pricingValuationVo.productEntityList;//查询二级产品
		},
		failure:function(response){
			var result = Ext.decode(response.responseText);
			if(Ext.isEmpty(result)){
				baseinfo.showErrorMes(baseinfo.freightRouteSearch.i18n('foss.baseinfo.requestOvertime'));//请求超时！
			}else{
				baseinfo.showErrorMes(result.message);
			}
		},
		exception:function(response){
			var result = Ext.decode(response.responseText);
			if(Ext.isEmpty(result)){
				baseinfo.showErrorMes(baseinfo.freightRouteSearch.i18n('foss.baseinfo.requestOvertime'));//请求超时！
			}else{
				baseinfo.showErrorMes(result.message);
			}
		}
	});
};

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
 * 公布价查询表单
 */
Ext.define('Foss.baseinfo.QueryFreightRouteSearchForm', {
	extend : 'Ext.form.Panel',
	title: baseinfo.freightRouteSearch.i18n('foss.baseinfo.inquiriesTakeGoodsPath'),
	frame: true,
	collapsible: true,
    defaults : {
    	columnWidth : .3,
    	margin : '8 10 5 10',
    	anchor : '100%'
    },
    height :180,
	defaultType : 'textfield',
	layout:'column',
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.items  = [{
			xtype : 'dynamicorgcombselector',
			name : 'sourceCode',
			allowBlank:false,
			types : 'ORG,PX,KY',
			fieldLabel : baseinfo.freightRouteSearch.i18n('foss.baseinfo.startingSector')//出发部门
		},{
			xtype : 'dynamicorgcombselector',
			name : 'targetCode',
			allowBlank:false,
			types : 'ORG,PX,KY,LD',
			fieldLabel : baseinfo.freightRouteSearch.i18n('foss.baseinfo.reachDepartment')//到达部门
		},{
			name:'productCode',
			queryMode: 'local',
			allowBlank:false,
    	    displayField: 'name',
    	    valueField: 'code',
    	    editable:false,
    	    fieldLabel:baseinfo.freightRouteSearch.i18n('foss.baseinfo.threeProducts'),//三级产品
    	    store:baseinfo.getStore(null,'Foss.baseinfo.ProductEntity',null
    	    ,baseinfo.freightRouteSearch.threeLevelProductFreightRoute),
            xtype : 'combo'
		},{
			fieldLabel:baseinfo.freightRouteSearch.i18n('foss.baseinfo.dateF'),//'生效日期',
			name : 'time',
			xtype : 'datetimefield_date97',
			editable:false,
			time : true,
			value:Ext.Date.format(new Date(), 'Y-m-d H:i:s'),
			allowBlank:false,
			id : 'Foss_freightRouteSearch_searchTime_ID',
			dateConfig: {
				el : 'Foss_freightRouteSearch_searchTime_ID-inputEl'
			}
		},{
			xtype : 'container',
			margin : '0 0 0 0',
			items : [{
				xtype : 'button', 
				width:70,
				cls:'yellow_button',
				text : baseinfo.freightRouteSearch.i18n('foss.baseinfo.query'),
				disabled:!baseinfo.freightRouteSearch.isPermission('freightRouteSearch/freightRouteSearchQueryButton'),
				hidden:!baseinfo.freightRouteSearch.isPermission('freightRouteSearch/freightRouteSearchQueryButton'),
				handler : function() {
					if(me.getForm().isValid()){
						var sourceCode = me.getForm().findField('sourceCode').getValue();//出发部门
						var targetCode = me.getForm().findField('targetCode').getValue();//到达部门
						var productCode = me.getForm().findField('productCode').getValue();//产品code
						var time = new Date(me.getForm().findField('time').getValue().replace(/-/g,'/')).getTime();//时间
						var params = {'freightRouteVo':{'sourceCode':sourceCode,'targetCode':targetCode,'productCode':productCode,'time':time}};
						var succeeeFun = function(json){
							me.up('panel').getFreightRouteSearchGrid().getStore().loadData(json.freightRouteVo.freightRouteLineDtoList);
						}
						var failureFun = function(json){
							if (Ext.isEmpty(json)) {
								baseinfo.showErrorMes('请求超时'); //请求超时
							} else {
								baseinfo.showErrorMes(json.message);
							}
						}
						var url = baseinfo.realPath('queryFreightRouteBySourceTarget.action');
						baseinfo.requestJsonAjax(url, params, succeeeFun, failureFun);
					}
				}
			}]
		}]
		me.callParent([cfg]);
	}
});
/**
 * 走货路径列表
 */
Ext.define('Foss.baseinfo.FreightRouteSearchGridPanel', {
	extend: 'Ext.grid.Panel',
	title : baseinfo.freightRouteSearch.i18n('foss.baseinfo.queryResults'),
	frame: true,
    sortableColumns:false,
    enableColumnHide:false,
    enableColumnMove:false,
	stripeRows : true, // 交替行效果
	selType : "rowmodel", // 选择类型设置为：行选择
	emptyText: baseinfo.freightRouteSearch.i18n('foss.baseinfo.queryResultIsNull'),//查询结果为空
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.columns = [{xtype: 'rownumberer',
			width:40,
			text : '序号'//序号
		},{
			text : baseinfo.freightRouteSearch.i18n('foss.baseinfo.startingDepartmentName'),//出发部门名称
			dataIndex : 'sourceName'
		},{
			text : baseinfo.freightRouteSearch.i18n('foss.baseinfo.startingSectorCoding'),//出发部门编码
			dataIndex : 'sourceCode'
		},{
			text : baseinfo.freightRouteSearch.i18n('foss.baseinfo.reachDepartmentCode'),//到达部门编码
			dataIndex : 'targetCode'
		},{
			text : baseinfo.freightRouteSearch.i18n('foss.baseinfo.reachDepartmentName'),//到达部门名称
			dataIndex : 'targetName'
		},{
			text :' 对应的线路虚拟编码',//对应的线路虚拟编码
			dataIndex : 'lineVirtualCode'
		},{
			text :' 对应的线路名称',//对应的线路名称
			dataIndex : 'lineName'
		},{
			text : baseinfo.freightRouteSearch.i18n('foss.baseinfo.scheduledDepartureTime'),//计划出发时间
			dataIndex : 'leaveDate',
			renderer:function(value){
				if(!Ext.isEmpty(value)){
					return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
				}else{
					return '';
				}
			}
		},{
			text :'计划到达时间',//计划到达时间
			dataIndex : 'arriveDate',
			renderer:function(value){
				if(!Ext.isEmpty(value)){
					return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
				}else{
					return '';
				}
			}
		},{
			text :baseinfo.freightRouteSearch.i18n('foss.baseinfo.stopoverTime'),//经停时间
			dataIndex : 'passbyDate',
			renderer:function(value){
				if(!Ext.isEmpty(value)){
					return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
				}else{
					return '';
				}
				
			}
		},{
			text :baseinfo.freightRouteSearch.i18n('foss.baseinfo.typesOfTransport'),//运输类型
			dataIndex : 'transType',
			hidden:true,
			renderer:function(value){
				return value;
			}
		},{
			text :baseinfo.freightRouteSearch.i18n('foss.baseinfo.lineType'),//线路类型
			dataIndex : 'lineType',
			hidden:true,
			renderer:function(value){
                  return value;
			}
		},{
			text :baseinfo.freightRouteSearch.i18n('foss.baseinfo.lineCategory'),//线路类别
			dataIndex : 'lineSort',
			hidden:true,
			renderer:function(value){
				return value;
			}
		},{
			text :baseinfo.freightRouteSearch.i18n('foss.baseinfo.agingNoHour'),//时效
			dataIndex : 'aging',
			hidden:true,
			renderer:function(value){
                return value/1000+baseinfo.freightRouteSearch.i18n('foss.baseinfo.hours');//小时
			}
		}];
		me.store = Ext.create('Foss.baseinfo.FreightRouteSearchStore');
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
	if (Ext.getCmp('T_baseinfo-freightRouteSearch_content')) {
		return;
	}
	baseinfo.searchThreeLeveProduct();
	var queryForm = Ext.create('Foss.baseinfo.QueryFreightRouteSearchForm');//查询FORM
	var freightRouteSearchGrid = Ext.create('Foss.baseinfo.FreightRouteSearchGridPanel');//查询结果GRID
	Ext.getCmp('T_baseinfo-freightRouteSearch').add(Ext.create('Ext.panel.Panel', {
		id : 'T_baseinfo-freightRouteSearch_content',
		cls : 'panelContentNToolbar',
		bodyCls : 'panelContentNToolbar-body',
		//获得查询FORM
		getQueryForm : function() {
			return queryForm;
		},
		//获得查询结果GRID
		getFreightRouteSearchGrid : function() {
			return freightRouteSearchGrid;
		},
		items : [ queryForm, freightRouteSearchGrid] 
	}));
});


