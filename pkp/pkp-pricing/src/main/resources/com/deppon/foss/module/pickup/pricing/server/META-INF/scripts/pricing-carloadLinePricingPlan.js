/**
 * 整车线路价格方案
 */

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
			Ext.Msg.alert('网络请求异常',result.message);
		}
	});
};

/**
 * 整车线路价格方案模型
 */
Ext.define('Foss.pricing.carloadLinePricingPlan.CarloadLinePriceModel', {
    extend: 'Ext.data.Model',
    fields : [{
        name : 'id',//整车线路价格方案ID
        type : 'string'
    },{
        name : 'departureCityCode',//始发城市code
        type : 'string'
    },{
        name : 'departureCityName',//始发城市name
        type : 'string'
    },{
        name : 'arrivalCityCode',//到达城市code
        type : 'string'
    },{
        name : 'arrivalCityName',//到达城市name
        type : 'string'
    },{
        name : 'modifyUser',//修改人
        type : 'string'
    },{
        name : 'modifyDate', //修改时间
        type : 'date',
        defaultValue : null,
        convert : pricing.changeLongToDate
    },{
        name : 'remark',//备注
        type : 'string'
    }]
});

/**
 * 价格方案明细模型
 */
Ext.define('Foss.pricing.carloadLinePricingPlan.CarloadLinePriceDetailModel', {
    extend: 'Ext.data.Model',
    fields : [{
        name : 'id',//方案明细ID
        type : 'string'
    },{
        name : 'carloadLinePriceId',//方案ID
        type : 'string'
    },{
        name : 'invoiceType',//发票标记
        type : 'string'
    },{
        name : 'type',//类型
        type : 'string'
    },{
        name : 'createDate', //创建时间
        type : 'date',
        defaultValue : null,
        convert : pricing.changeLongToDate
    },{
        name : 'upLimit'//上限
    },{
        name : 'downLimit'//下限 
    },{
        name : 'chargeStandard',//标准费用
        type : 'float'
    },{
        name : 'type',//类型
        type : 'string'
    },{
        name : 'remark',//备注
        type : 'string'
    }]
});


/**
 * 整车线路价格方案store
 */
Ext.define('Foss.pricing.carloadLinePricingPlan.CarloadLinePriceStore',{
	extend: 'Ext.data.Store',
	model: 'Foss.pricing.carloadLinePricingPlan.CarloadLinePriceModel',
	pageSize:20,
	proxy: {
		type : 'ajax',
		actionMethods:'POST',
		url: pricing.realPath('queryCarloadLinePricePlanInfo.action'),
		reader : {
			type : 'json',
			root : 'carloadLinePricePlanVo.carloadLinePricePlanEntityList',
			totalProperty : 'totalCount',
			successProperty: 'success'
		}
	},
	listeners: {
		beforeload: function(store, operation, eOpts){
			var n = pricing.queryform.getValues();
			Ext.apply(operation,{
				params : {
					'carloadLinePricePlanVo.carloadLinePricePlanEntity.departureCityCode': n.departureCity,
					'carloadLinePricePlanVo.carloadLinePricePlanEntity.arrivalCityCode': n.arrivalCity
				}
			});			
		}
	}
});
/**
 * 价格方案明细store
 */
Ext.define('Foss.pricing.carloadLinePricePlan.PricePlanDeatilStore',{
	extend: 'Ext.data.Store',
	model: 'Foss.pricing.carloadLinePricingPlan.CarloadLinePriceDetailModel',
	pageSize:10,
	proxy: {
		type : 'ajax',
		actionMethods:'POST',
		url: pricing.realPath('queryCarloadLinePricePlanDetailInfo.action'),
		reader : {
			type : 'json',
			root : 'carloadLinePricePlanVo.carloadLinePricePlanDetailEntityList',
			totalProperty : 'totalCount',
			successProperty: 'success'
		}
	}
});


/**
 * 整车线路价格方案查询form表单
 */
Ext.define('Foss.pricing.carloadLinePricingPlan.CarloadLinePriceFormPanel', {
	extend : 'Ext.form.Panel',
	title: '查询条件',
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
		name : 'departureCity',
        xtype : 'textfield',
		fieldLabel:'始发城市',//始发城市
		xtype : 'commoncityselector',
	    columnWidth:.3
	},{
		xtype: 'container',
		columnWidth:.2,
		html: '&nbsp;'
	},{
		name: 'arrivalCity',
	    fieldLabel:'到达城市',//到达城市
	    xtype : 'commoncityselector',
	    columnWidth:.3
	}];
	me.fbar = [{
			xtype : 'button', 
			width:70,
			left : 1,
			text : '重置',//重置
			margin:'0 825 0 0',
			handler : function() {
				me.getForm().reset();
			}
		},{
			margin : '0 0 0 0',
			xtype : 'button', 
			width:70,
			cls:'yellow_button',
			text : '查询',//查询
			disabled: !pricing.carloadLinePricePlan.isPermission('carloadLinePricePlanIndex/carloadLinePricePlanQueryButton'),
			hidden: !pricing.carloadLinePricePlan.isPermission('carloadLinePricePlanIndex/carloadLinePricePlanQueryButton'),
			handler : function() {
				var departureCity = me.getForm().findField('departureCity').getValue();
				var arrivalCity = me.getForm().findField('arrivalCity').getValue();
				if(Ext.isEmpty(departureCity) && Ext.isEmpty(arrivalCity)){
					pricing.showWoringMessage('请先选择始发城市或到达城市');//请先选择始发城市或到达城市
					return;
				}
				var grid = Ext.getCmp('T_pricing-carloadLinePlanIndex_content').getCarloadLinePricePlanGridPanel();
				grid.getPagingToolbar().moveFirst();
			}
		}];
		me.callParent([cfg]);
	}
});

/**
 * 整车线路价格方案列表gird
 */
Ext.define('Foss.pricing.carloadLinePricingPlan.CarloadLinePriceGridPanel',{
	extend: 'Ext.grid.Panel',
	title : '查询结果',//查询结果
	emptyText: '查询结果为空',//查询结果为空
	frame: true,
	//sortableColumns:false,
    enableColumnHide:false,
	selType : "rowmodel", 
	enableColumnMove:false,
	stripeRows:true, 
	border: true,
	defaults: {
		width: 150
	},
	
	pricePlanDetailShowWindow: null,
	getPricePlanDetailShowWindow: function(){
		if(Ext.isEmpty(this.pricePlanDetailShowWindow)){
			this.pricePlanDetailShowWindow = Ext.create('Foss.pricing.carloadLinePricePlan.PricePlanDetailShowWindow');
			//设置器父元素
			this.pricePlanDetailShowWindow.parent = this;
		}
		return this.pricePlanDetailShowWindow;
	},
	
	//返回批次store
	pricePlanStore:null,
	getPricePlanStore: function(){
		if(Ext.isEmpty(this.pricePlanStore)){
			this.pricePlanStore = Ext.create('Foss.pricing.carloadLinePricingPlan.CarloadLinePriceStore');
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
				plugins: 'pagesizeplugin',
				pageSize : 20
			});
		}
		return this.pagingToolbar;
	},
	
	uploadLinePriceform : null,
    /**
     * 上传整车线路方案信息
     * @return {}
     */
    getUploadLinePriceform: function(){
    	if(Ext.isEmpty(this.uploadPriceform)){
			this.uploadLinePriceform = Ext.create('Foss.pricing.carloadLinePricePlan.UploadLinePriceform');	
			this.uploadLinePriceform.show();
		}
		return this.uploadLinePriceform;
    },
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.store = me.getPricePlanStore();
		me.selModel = me.getCheckboxModel();
		me.bbar = me.getPagingToolbar();
		me.columns = [{xtype: 'rownumberer',
			width:50,
			text : '序号'//序号 
		},{
			text : '操作',//操作
			align : 'center',
			xtype : 'actioncolumn',
			items: [{
				iconCls: 'deppon_icons_showdetail',
                tooltip: '查看明细', 
                //disabled: !pricing.carloadPricingFloat.isPermission('carloadPricingFloat/pricePlanQueryDetailButton'),
				width:42,
                handler: function(grid, rowIndex, colIndex) {
    				var record=grid.getStore().getAt(rowIndex);
                	me.getPricePlanDetailShowWindow().carloadLinePriceModel = record.data;
                	me.getPricePlanDetailShowWindow().show();
                	
                }
			}]
		},{
			width: 200,
			text: '始发城市', //始发城市
	        dataIndex: 'departureCityName'
		},{
			width: 200,
			text: '到达城市',//到达城市
	        dataIndex: 'arrivalCityName'
		},{
			text: '修改时间',//修改时间
			width: 200,
	        dataIndex: 'modifyDate',
	        renderer:function(value){
	        	if (Ext.isEmpty(value))
	        		return '';
	        	else 
	        		return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
			}
		},{
			text: '修改人',//修改人,
			width: 160,
	        dataIndex: 'modifyUserName'
		}];
		me.tbar = [
		/*{
			text : '新建',//新建
			//hidden: !pricing.carloadPricingFloat.isPermission('carloadPricingFloat/pricePlanAddButton'),
			handler :function(){
				alert('系统完善中');
			} 
		},'-', {
			text : '删除',//删除
			//hidden:!pricing.carloadPricingFloat.isPermission('carloadPricingFloat/pricePlanImmediatelyActiveButton'),
			handler :function(){
				//me.activeCarloadPricePlan();
				alert('系统完善中');
			} 
		},'-', */{
			text : '导入',  //导入
			disabled: !pricing.carloadLinePricePlan.isPermission('carloadLinePricePlanIndex/carloadLinePricePlanImportButton'),
			hidden: !pricing.carloadLinePricePlan.isPermission('carloadLinePricePlanIndex/carloadLinePricePlanImportButton'),
			handler :function(){
				me.getUploadLinePriceform();
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
Ext.define('Foss.pricing.carloadLinePricePlan.PricePlanDetailShowWindow',{
	extend : 'Ext.window.Window',
	title: '整车线路价格明细查询',//
	closable : true,
	modal : true,
	resizable:false,
	closeAction : 'hide',
	parent:null,
	carloadLinePriceModel:null,
	width :500,
	height :650,
	listeners:{
		beforehide:function(me){
			//清楚上次使用的数据
			me.getQueryPricePlanDetailForm().getForm().reset();
			me.getPricePlanDetailShowGridPanel().getStore().removeAll();
			me.carloadLinePriceModel = null;
		},
		beforeshow:function(me){
			var record = new Foss.pricing.carloadLinePricingPlan.CarloadLinePriceModel(me.carloadLinePriceModel);
			me.getQueryPricePlanDetailForm().getForm().loadRecord(record);
			me.getQueryPricePlanDetailForm().getForm().findField('departureCity').setCombValue(me.carloadLinePriceModel.departureCityName,me.carloadLinePriceModel.departureCityCode);
			me.getQueryPricePlanDetailForm().getForm().findField('arrivalCity').setCombValue(me.carloadLinePriceModel.arrivalCityName,me.carloadLinePriceModel.arrivalCityCode);
			me.getQueryPricePlanDetailForm().getForm().findField('departureCity').setReadOnly(true);
			me.getQueryPricePlanDetailForm().getForm().findField('arrivalCity').setReadOnly(true);
		}
	},
    //明细信息查询-FORM
	queryPricePlanDetailForm:null,
    getQueryPricePlanDetailForm:function(){
    	if(Ext.isEmpty(this.queryPricePlanDetailForm)){
    		this.queryPricePlanDetailForm = Ext.create('Foss.pricing.carloadLinePricePlan.QueryPricePlanDetailForm');
    	}
    	return this.queryPricePlanDetailForm;
    },
    //明细信息结果集
    pricePlanDetailShowGridPanel:null,
    getPricePlanDetailShowGridPanel:function(){
    	if(Ext.isEmpty(this.pricePlanDetailShowGridPanel)){
    		this.pricePlanDetailShowGridPanel = Ext.create('Foss.pricing.carloadLinePricePlan.PricePlanDetailShowGridPanel');
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
Ext.define('Foss.pricing.carloadLinePricePlan.QueryPricePlanDetailForm', {
	extend : 'Ext.form.Panel',
	title: '查询条件',//查询条件
	frame: true,
	collapsible: true,
	defaults : {
    	margin : '5 5 5 5',
    	labelWidth:100,
    	allowBlank:false,
    	colspan : 1
    },
    layout:{
		type:'table',
		columns: 1
	},
    height :190,
	defaultType : 'textfield',
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.items = [{
			name : 'departureCity',
			fieldLabel:'始发城市',//始发城市
			xtype : 'commoncityselector',
			allowBlank:false,
			width : 250
		},{
			name: 'arrivalCity',
		    fieldLabel:'到达城市',//到达城市
		    xtype : 'commoncityselector',
		    allowBlank:false,
		    width : 250
		},{
        	name: 'invoiceType',
			queryMode: 'local',
			allowBlank:false,
		    displayField: 'value',
		    value:'ALL',
		    valueField: 'key',
		    width : 250,
		    store:pricing.getStore('Foss.pricing.region.invoiceasType',null,['key','value'],
		     [{'key':'INVOICE_TYPE_01','value':'01—运输专票11%'},
		      {'key':'INVOICE_TYPE_02','value':'02—非运输专票6%'},
		      {'key':'ALL','value':'全部'}]),
		    fieldLabel: '发票标记',//发票标记
		    xtype : 'combo'
		},{
			xtype : 'container',
			margin : '0 0 0 0',
			items : [{
				xtype : 'button', 
				width:70,
				margin : '0 0 0 340',
				text : '查询',//查询
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
Ext.define('Foss.pricing.carloadLinePricePlan.PricePlanDetailShowGridPanel', {
	extend: 'Ext.grid.Panel',
	title :'查询结果',//查询结果
	frame: true,
	height :370,
    sortableColumns:false,
    enableColumnHide:false,
    enableColumnMove:false,
	stripeRows : true, // 交替行效果
	selType : "rowmodel", // 选择类型设置为：行选择
	emptyText: '查询结果为空',//查询结果为空
	
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
		me.columns = [{
			width:40,
			xtype : 'actioncolumn',
			text : '操作'//序号
		},{
			text: '发票标记',//发票标记
			dataIndex : 'invoiceType',
			flex:3,
			renderer:function(value){
				if(value=='INVOICE_TYPE_01'){//01—运输专票11%
					return '01—运输专票11%'
				}else if(value=='INVOICE_TYPE_02'){//02—非运输专票
					return '02—非运输专票6%'
				}else{
					return '';
				}
			}
		},{
			text: '范围(起点)',//范围(起点)
			dataIndex : 'upLimit',
			flex:2,
			renderer:function(value,metedata,record){
				var type = record.data['type'];
				if (type == 'WEIGHT') {
					return value + '吨';
				} else if (type == 'VOLUME') {
					return value + '方';
				} else {
					return value;
				}
			}
		},{
			text: '范围(终点)',//范围(终点)
			dataIndex : 'downLimit',
			flex:2,
			renderer:function(value,metedata,record){
				var type = record.data['type'];
				if (type == 'WEIGHT') {
					return value + '吨';
				} else if (type == 'VOLUME') {
					return value + '方';
				} else {
					return value;
				}
			}
		},{
			text: '收费标准',//收费标准
			dataIndex : 'chargeStandard',
			renderer:function(value,metedata,record){
				var type = record.data['type'];
				if (type == 'WEIGHT') {
					return value + '元/吨';
				} else if (type == 'VOLUME') {
					return value + '元/方';
				} else {
					return value;
				}
			}
		}];
		me.store = Ext.create('Foss.pricing.carloadLinePricePlan.PricePlanDeatilStore',{
			autoLoad : false,
			pageSize : 10,
			listeners: {
				beforeload: function(store, operation, eOpts){
					var queryForm =me.up('window').getQueryPricePlanDetailForm();
					var carloadLinePriceId = me.up('window').carloadLinePriceModel.id;
					if(queryForm!=null){
						Ext.apply(operation,{
							params : {
								'carloadLinePricePlanVo.carloadLinePricePlanDetailEntity.invoiceType' : queryForm.getForm().findField('invoiceType').getValue(),//发票标记
								'carloadLinePricePlanVo.carloadLinePricePlanDetailEntity.carloadLinePriceId' : carloadLinePriceId//价格方案ID
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
		/*me.tbar = [
		   		{
		   			text : '按照重量新增',//新建
		   			//hidden: !pricing.carloadPricingFloat.isPermission('carloadPricingFloat/pricePlanAddButton'),
		   			handler :function(){
		   				alert('系统完善中');
		   			} 
		   		},'-', {
		   			text : '按照体积新增',//删除
		   			//hidden:!pricing.carloadPricingFloat.isPermission('carloadPricingFloat/pricePlanImmediatelyActiveButton'),
		   			handler :function(){
		   				//me.activeCarloadPricePlan();
		   				alert('系统完善中');
		   			} 
		   		}];*/
		me.bbar = me.getPagingToolbar();
		me.callParent([cfg]);
	}
});


/**
 * 上传附件弹出框
 */
Ext.define('Foss.pricing.carloadLinePricePlan.UploadLinePriceform',{
	extend:'Ext.window.Window',
	title:'导入',
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
	parent:null,
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
				fieldLabel:'请选择附件',
				labelWidth:100,
				buttonText:'浏览',
				flex:3
			}]
		}];
		me.fbar = me.getFbar();
		me.callParent([cfg]);
	},
	getFbar:function(){
		var me = this;
		return [{
			text:'上传',
			xtype:'button',
			scope:me,
			handler:me.uploadFile
		},{
			text:'取消',
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
			if(Ext.isEmpty(json.carloadLinePricePlanVo.numList)){
				pricing.showInfoMes('全部数据导入成功！');//全部数据导入成功！
				me.close();
			}else{
				var message = '第';//第
				for(var i = 0;i<json.platformVo.numList;i++){
					message = message+json.platformVo.numList[i]+','
				}
				message = '行导入成功';
				pricing.showWoringMessage(message);
			}
		};
		var failureFn = function(json){
			if(Ext.isEmpty(json)){
				pricing.showErrorMes('请求超时！');//pricing.carloadPricingFloat.i18n('i18n.pricingRegion.requestTimeOut')
			}else{
				pricing.showErrorMes(json.message);
			}
		};
		var form = me.down('form').getForm();
		var url = pricing.realPath('importLinePrice.action');
		form.submit({
            url: url,
			timeout:60000,   
            waitMsg: '正在导入',
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

/*Ext.require('Foss.pricing.carloadLinePricePlan.QueryPricePlanDetailForm');*/

/**
 * 开始加载界面
 */
Ext.onReady(function(){
	Ext.QuickTips.init();
	
	var queryform = Ext.create('Foss.pricing.carloadLinePricingPlan.CarloadLinePriceFormPanel');
	var gridPanel =	Ext.create('Foss.pricing.carloadLinePricingPlan.CarloadLinePriceGridPanel');
	pricing.queryform = queryform;
	pricing.gridPanel = gridPanel;
	Ext.getCmp('T_pricing-carloadLinePricePlanIndex').add(Ext.create('Ext.panel.Panel', {
	  	id:'T_pricing-carloadLinePlanIndex_content',
		cls:"panelContentNToolbar",
		bodyCls:'panelContent-body',
		//获得查询FORM
		getQueryCarloadLinePricePlanForm : function() {
			return queryform;
		},
		//获得查询结果GRID
		getCarloadLinePricePlanGridPanel : function() {
			if(Ext.isEmpty(this.gridPanel)){
				this.gridPanel = Ext.create('Foss.pricing.carloadLinePricingPlan.CarloadLinePriceGridPanel');//查询结果GRID
			}
			return gridPanel;
		},
		items : [queryform,gridPanel]
	}));
});


