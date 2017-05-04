/*****
 * 偏线价格方案,维护偏线价格运费的基础信息,界面与汽运大致相同,区别在于
 * 偏线没有时效概念
 * 
 */


/**
 * 
 * @type String 激活
 */
pricing.yes = 'Y';
pricing.no = 'N';
/**
 * 
 * @type  偏线价费用
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
/**.
 * <p>
 * 公共方法，通过storeId和model创建STORE<br/>
 * <p>
 * @param  storeId  
 * @param  model   store所用到的model名
 * @param  fields   store所用到的fields
 * @returns store  返回创建的store
 * @author Foss-YueHongJie
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
			Ext.Msg.alert(pricing.outerPrice.i18n('foss.pricing.promptMessage'),result.message);
		}
	});
};

/**
 * 偏线价费用方案明细模型
 */
Ext.define('Foss.pricing.outerPrice.OuterPriceDetailDto', {
    extend: 'Ext.data.Model',
    fields : [{
    	name : 'outPriceId', //偏线ID
        type : 'string'
    },{
        name : 'outerPriceName', //名称
        type : 'string'
    },{
    	name : 'partialLineCode',//目的站
        type : 'string'
    },{
    	name : 'nationCode',//国家
        type : 'string'
    },{
    	name : 'provCode',//省份
        type : 'string'
    },{
    	name : 'cityCode',//城市
        type : 'string'
    },{
    	name : 'countyCode',//县区
        type : 'string'
    },{
    	name : 'outFieldCode',//外发外场
        type : 'string'
    },{
        name : 'heavyPrice'//重货价格
    },{
        name : 'lightPrice'//轻货价格 
    },{
        name : 'minimumOneTicket'//最低一票
    },{
        name : 'remark',//备注
        type : 'string'
    }]
});

//创建一个查询货量枚举store
Ext.define('Foss.pricing.outerPrice.Store.ToDoTypeStore',{
	extend: 'Ext.data.Store',
	fields: [
		{name: 'code',  type: 'string'},
		{name: 'name',  type: 'string'}
	],
	//定义一个代理对象
	proxy: {
		//代理的类型为内存代理
		type: 'memory',
		//定义一个读取器
		reader: {
			//以JSON的方式读取
			type: 'json',
			//定义读取JSON数据的根对象
			root: 'items'
		}
	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});


/**
 * 偏线价费用方案批次model
 */
Ext.define('Foss.pricing.outerPrice.OuterPriceModel', {
    extend: 'Ext.data.Model',
    fields : [
    {
        name : 'outerPriceId',//ID
        type : 'string'
    },{
      name : 'name',//方案名称
        type : 'string'
    },{
      name : 'partialLineCode',//目的站
        type : 'string'
    },{
      name : 'partialLineName',
        type : 'string'
    },{
        name : 'outFieldCode',//最终外场
        type : 'string'
    },{
      name : 'outFieldName',
        type : 'string'//productCode
    },{
      name : 'productCode',
        type : 'string'//productCode
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
        name : 'modifyDate',
        type : 'date',
        defaultValue : null,
        convert : pricing.changeLongToDate
    },{
        name : 'modifyUser',
        type : 'string'
    },{
        name : 'active',
        type : 'string'
    },{
        name : 'remark',
        type : 'string'
    },{
        name : 'version',
        type : 'string'
    },{
        name : 'provCode',
        type : 'string'
    },{
        name : 'provName',
        type : 'string'
    },{
        name : 'cityCode',
        type : 'string'
    },{
        name : 'cityName',
        type : 'string'
    },{
        name : 'countyCode',
        type : 'string'
    },{
        name : 'countyName',
        type : 'string'
    },{
        name : 'weightFeeRate',
        type : 'float'
    },{
        name : 'volumeFeeRate',
        type : 'float'
    },{
        name : 'minFee',
        type : 'int'
    },{
    	name:'showTime',
    	type : 'string'
    }]
});

/**
 *偏线价费用方案批次store
 */
Ext.define('Foss.pricing.outerPrice.OuterPricePlanStore',{
	extend: 'Ext.data.Store',
	model: 'Foss.pricing.outerPrice.OuterPriceModel',
	pageSize:20,
	proxy: {
		type : 'ajax',
		actionMethods:'POST',
		url: pricing.realPath('queryOuterPriceVoBatchInfo.action'),
		reader : {
			type : 'json',
			root : 'outerPriceVo.outerPricePlanDtoList',
			totalProperty : 'totalCount',
			successProperty: 'success'
		}
	},
	listeners: {
		beforeload: function(store, operation, eOpts){
			var n = pricing.outerPrice.queryform.getValues();
			var outFieldCode = pricing.outerPrice.queryform.getForm().findField('outFieldCode').getValue();
			if(outFieldCode != null)
			{
			   outFieldCode = pricing.outerPrice.queryform.getForm().findField('outFieldCode').displayTplData[0].orgCode;
			}
			Ext.apply(operation,{
				params : {
					'outerPriceVo.outerPriceCondtionDto.partialLineCode' : 	  n.partialLineCode,
					'outerPriceVo.outerPriceCondtionDto.outFieldCode'	: outFieldCode,
					'outerPriceVo.outerPriceCondtionDto.productCode'	: n.transportType,
					'outerPriceVo.outerPriceCondtionDto.active'	: n.active
				}
			});			
		}
	}
});
/**
 * 偏线价费用方案明细store
 */
Ext.define('Foss.pricing.outerPrice.OuterPriceDeatilStore',{
	extend: 'Ext.data.Store',
	model: 'Foss.pricing.outerPrice.OuterPriceDetailDto',
	pageSize:10,
	proxy: {
		type : 'ajax',
		actionMethods:'POST',
		url: pricing.realPath('queryOuterPriceDetailInfo.action'),
		reader : {
			type : 'json',
			root : 'outerPriceVo.outerPriceDetailDtoList',
			totalProperty : 'totalCount',
			successProperty: 'success'
		}
	}
});


/**
 * 偏线价格方案批次查询form表单
 */
Ext.define('Foss.pricing.outerPrice.OuterPriceFormPanel', {
	extend : 'Ext.form.Panel',
	  title: pricing.outerPrice.i18n('i18n.pricingRegion.searchCondition'),
	  frame: true,
	  collapsible: true,
	    defaults : {
	      margin : '8 10 8 10',
	      labelWidth:80
	    },
	     layout: 'auto',
	  defaultType : 'textfield',
	  layout:'column',
	  items :[{
	    xtype : 'commonvehagencydeptselector',
	    name:'partialLineCode',
	    fieldLabel:pricing.outerPrice.i18n('foss.pricing.destinationLine'),//目的站
	    columnWidth: 0.25
	  },{
	    xtype : 'commontransfercenterselector',
	    name: 'outFieldCode',
		active:'Y',  
	    fieldLabel: pricing.outerPrice.i18n('foss.pricing.outFieldName'),//外发外场(commontransfercenterselector):,
	    columnWidth: 0.25
	  },{
		name: 'transportType',
		fieldLabel: '运输类型',//运输类型
		columnWidth: 0.25,
		xtype: 'combobox',
		valueField:'valueCode', 
		displayField: 'valueName',
		//value: 'TRANS_VEHICLE',
		value: '',
		queryMode:'local',
		triggerAction:'all',
		store : FossDataDictionary.getDataDictionaryStore("TRANS_TYPE", null,{
			'valueCode' : '',
			'valueName' : pricing.outerPrice.i18n('i18n.pricingRegion.all')
		})
		//store : FossDataDictionary.getDataDictionaryStore("TRANS_TYPE")
	},{
	    xtype:'combo',
	    displayField:'name',
	    valueField:'code',
	    queryMode:'local',
	    triggerAction:'all',
	    editable:false,
	    name: 'active',
	    fieldLabel: pricing.outerPrice.i18n('foss.pricing.dataStatus'),//处理状态,
	    columnWidth: 0.25,
	    value:'',
	    store:Ext.create('Foss.pricing.outerPrice.Store.ToDoTypeStore',
	    {
	      data:{
	           'items':[
	          {'code':'','name':pricing.outerPrice.i18n('i18n.pricingRegion.all')},
	          {'code':'N','name':pricing.outerPrice.i18n('i18n.pricingRegion.unActive')},
	          {'code':'Y','name':pricing.outerPrice.i18n('i18n.pricingRegion.active')}	          
	         ]
	      }
	    })
	  },{		
			border: 1,
			xtype:'container',
			columnWidth:1,
			defaultType:'button',
			layout:'column',
			items:[{
				xtype : 'button', 
				colspan : 1,
		        width:80,
				text : pricing.outerPrice.i18n('foss.pricing.reset'),//重置
				handler : function() {
					pricing.outerPrice.queryform.getForm().reset();
				}
			},{
				xtype: 'container',
				border : false,
				columnWidth:.95,
				html: '&nbsp;'
			},{
		        xtype : 'button',
		        width:80,
		        cls:'yellow_button',
		        text : "查询",
		        disabled: !pricing.outerPrice.isPermission('outerPrice/outerPriceQueryButton'),
		        hidden: !pricing.outerPrice.isPermission('outerPrice/outerPriceQueryButton'),
		        handler : function() {
		        	var grid = Ext.getCmp('T_pricing-outerPrice_content').getOffLinePricePlanGridPanel();
		        	grid.getPagingToolbar().moveFirst();
		      }
		    }]
	  }]
});

//-------------------查询详情------------------
/**
 * 偏线价费用方案明细信息查看WINDOW
 */
Ext.define('Ext.pricing.outerPrice.OffLinePricePlanDetailShowWindow',{
	extend : 'Ext.window.Window',
	title: pricing.outerPrice.i18n('foss.pricing.thePriceOfTheProgramDetailQuery'),//始发区域与目的区域时效方案明细查询
	closable : true,
	modal : true,
	resizable:false,
	closeAction : 'hide',
	parent:null,
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
    		this.queryPricePlanDetailForm = Ext.create('Foss.pricing.outerPrice.OuterPriceDetailForm');
    	}
    	return this.queryPricePlanDetailForm;
    },
    //明细信息结果集
    pricePlanDetailShowGridPanel:null,
    getPricePlanDetailShowGridPanel:function(){
    	if(Ext.isEmpty(this.pricePlanDetailShowGridPanel)){
    		this.pricePlanDetailShowGridPanel = Ext.create('Foss.pricing.outerPrice.OuterPriceDetailShowGridPanel');
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

pricing.outerPrice.immediatelyActiveId = null;
pricing.outerPrice.immediatelyStopId = null;


/**
 * 偏线价费用方案批次列表gird
 */
Ext.define('Foss.pricing.outerPrice.OuterPriceGridPanel',{
	extend: 'Ext.grid.Panel',
	title : pricing.outerPrice.i18n('i18n.pricingRegion.searchResults'),//查询结果
	emptyText: pricing.outerPrice.i18n('foss.pricing.theQueryResultIsEmpty'),//查询结果为空
	frame: true,
	cls: 'autoHeight',
	bodyCls: 'autoHeight', 
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
			this.addPricePlanWindow = Ext.create('Foss.pricing.outerPrice.OuterPriceAddWindow');
			this.addPricePlanWindow.parent = this;
		}
		return this.addPricePlanWindow;
	},
	
	updatePricePlanWindow : null,
	//获取修改价格方案窗口
	getUpdatePricePlanWindow : function(){
		if(Ext.isEmpty(this.updateStandardWindow)){
			this.updatePricePlanWindow = Ext.create('Foss.pricing.outerPrice.OuterPriceUpdateWindow');
			//设置器父元素
			this.updatePricePlanWindow.parent = this;
		}
		return this.updatePricePlanWindow;
	},
	
	
	//中止方案弹出日期选择
	stopPricePlanWindow:null,
	getStopPricePlanWindow: function(){
		if(Ext.isEmpty(this.stopPricePlanWindow)){
			/*this.stopPricePlanWindow = Ext.create('Foss.pricing.outerPrice.OuterPriceStopEndTimeWindow',{
				'pricePlanId':pricePlanId//,
				//'endTime' : endTime
			});*/
			this.stopPricePlanWindow = Ext.create('Foss.pricing.outerPrice.OuterPriceStopEndTimeWindow');
			this.stopPricePlanWindow.parent = this;
		}
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
	getimmediatelyActiveOffLinePricePlanWindow: function(pricePlanEntity){
		if(Ext.isEmpty(this.immediatelyActivePricePlanWindow)){
			this.immediatelyActivePricePlanWindow = Ext.create('Foss.pricing.outerPrice.OuterPriceImmediatelyActiveTimeWindow');
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
	getImmediatelyStopOffLinePricePlanWindow: function(pricePlanEntity){
		if(Ext.isEmpty(this.immediatelyStopPricePlanWindow)){
			this.immediatelyStopPricePlanWindow = Ext.create('Foss.pricing.outerPrice.OuterPriceImmediatelyStopEndTimeWindow',{
				pricePlanEntity:pricePlanEntity
			});
			this.immediatelyStopPricePlanWindow.parent = this;
		}
		return this.immediatelyStopPricePlanWindow;
	},
	
	
	/**
	 * 立即中止
	 */
    immediatelyStopOffLinePricePlan:function(){
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
	 	if(selections[0].get('active')!=pricing.yes){
	 		pricing.showWoringMessage('请选择已激活数据进行操作！');
	 		return;
	 	}else{
	 		var pricePlanEntity = selections[0].data;
	 		me.getImmediatelyStopOffLinePricePlanWindow(pricePlanEntity).show();
	 	}
	},
	
	/**
	 * 立即激活
	 */
    immediatelyActiveOffLinePricePlan:function(){
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
	 	if(selections[0].get('active')==pricing.yes){
	 		pricing.showWoringMessage('请选择未激活数据进行操作！');
	 		return;
	 	}else{
	 		var pricePlanEntity = selections[0].data;
	 		me.getimmediatelyActiveOffLinePricePlanWindow(pricePlanEntity).show();
	 	}
	},
	
	//返回批次store
	pricePlanStore:null,
	getPricePlanStore: function(){
		if(Ext.isEmpty(this.pricePlanStore)){
			this.pricePlanStore = Ext.create('Foss.pricing.outerPrice.OuterPricePlanStore');
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
				pageSize : 20
			});
		}
		return this.pagingToolbar;
	},
	//查看详情界面
	/*pricePlanDetailShowWindow:null,
	getPricePlanDetailShowWindow:function(){
		if(Ext.isEmpty(this.pricePlanDetailShowWindow)){
			this.pricePlanDetailShowWindow = Ext.create('Ext.pricing.outerPrice.OffLinePricePlanDetailShowWindow');	
			this.pricePlanDetailShowWindow.parent = this;
		}
		return this.pricePlanDetailShowWindow;
	},
	*/
	//删除价格方案
	deletePricePlan:function(){
	 	var me = this;
	 	var selections = me.getSelectionModel().getSelection();
	 	if(selections.length < 1){
	 		pricing.showWoringMessage(pricing.outerPrice.i18n('foss.pricing.pleaseSelectOneDeleteOperation'));
			return;
	 	}
	 	//过滤草稿状态数据进行删除
	 	for(var i = 0;i<selections.length;i++){
			if(selections[i].get('active')==pricing.yes){
				pricing.showWoringMessage(pricing.outerPrice.i18n('foss.pricing.pleaseChooseToNotActivateTheDataToBeBeleted'));
				return;
			}
		}
		//是否要删除这些汽运价格方案？
		pricing.showQuestionMes(pricing.outerPrice.i18n('foss.pricing.theseTheAirPriceProgramYouWantToRemove'),function(e){
			if(e=='yes'){
				//汽运价格方案
				var idList = new Array();
				for(var i = 0 ; i<selections.length ; i++){
					idList.push(selections[i].get('outerPriceId'));
				}
				var params = {'outerPriceVo':{'outerPriceIds':idList}};
				var successFun = function(json){
					pricing.showInfoMes(json.message);
					me.getPagingToolbar().moveFirst();
				};
				var failureFun = function(json){
					if(Ext.isEmpty(json)){
						pricing.showErrorMes(pricing.outerPrice.i18n('foss.pricing.requestTimedOut'));//请求超时
					}else{
						pricing.showErrorMes(json.message);
					}
				};
				var url = pricing.realPath('deleteOuterPrice.action');
				pricing.requestJsonAjax(url,params,successFun,failureFun);
			}
		});
	},
	
	//激活时效方案
    activePricePlan:function(){
    	var me = this;
		var pricePlans = new Array();
		//获取选中的数据
		var selections = me.getSelectionModel().getSelection();
		if(selections.length<1){
			pricing.showErrorMes(pricing.outerPrice.i18n('foss.pricing.pleaseChooseThePriceYouWantToActivateTheProgram'));//请选择要激活的价格方案！
			return;
		}

		var outerPricePlans = new Array();
		//过滤草稿状态数据进行删除
	 	for(var i = 0;i<selections.length;i++){
			if(selections[i].get('active')==pricing.yes){
				pricing.showWoringMessage(pricing.outerPrice.i18n('foss.pricing.pleaseSelectNotActivateProgramDataForActivation'));
				return;
			}
			pricePlans.push(selections[i].get('outerPriceId'));
			outerPricePlans.push(selections[i].data);
		}
		
		if(pricePlans.length<1){
			pricing.showErrorMes(pricing.outerPrice.i18n('foss.pricing.pleaseSelectAtLeastOneInactivePriceProgram'));//请至少选择一条未激活的价格方案！
			return;
		}
		
		//是否要激活这些偏线价格方案？
		pricing.showQuestionMes(pricing.outerPrice.i18n('foss.pricing.doYouWantActivateTheseOuterPriceScheme'),function(e){
			if(e=='yes'){
				var params = {'outerPriceVo':{'outerPriceIds':pricePlans, 'yesOrNo': 'Y', outerPricePlanDtoList: outerPricePlans}};
				var successFun = function(json){
					pricing.showInfoMes(json.message);
					me.getPagingToolbar().moveFirst();
				};
				var failureFun = function(json){
					if(Ext.isEmpty(json)){
						pricing.showErrorMes(pricing.outerPrice.i18n('i18n.pricingRegion.requestTimeOut'));//请求超时
					}else{
						pricing.showErrorMes(json.message);
					}
				};
				var url = pricing.realPath('updateOuterPriceActiveById.action');
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
			this.uploadPriceform = Ext.create('Foss.pricing.outerPrice.UploadOuterPriceform');	
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
			this.queryPricePlanForm  = Ext.create('Foss.pricing.outerPrice.OuterPriceFormPanel')
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
			text : pricing.outerPrice.i18n('i18n.pricingRegion.num')//序号  
		},{
			text : pricing.outerPrice.i18n('i18n.pricingRegion.opra'),//操作
			align : 'center',
			xtype : 'actioncolumn',
			items: [/*{
				iconCls: 'deppon_icons_showdetail',
                tooltip: pricing.outerPrice.i18n('foss.pricing.details'), 
                disabled: !pricing.outerPrice.isPermission('outerPrice/outerPriceQueryDetailButton'),
				width:42,
                handler: function(grid, rowIndex, colIndex) {
    				var record=grid.getStore().getAt(rowIndex);
                	me.getPricePlanDetailShowWindow().show();
                	me.getPricePlanDetailShowWindow().pricePlanId = record.get('id');
                }
			},*/{
				iconCls:'deppon_icons_edit',
				tooltip: pricing.outerPrice.i18n('foss.pricing.toAmendTheProposal'), 
				disabled: !pricing.outerPrice.isPermission('outerPrice/outerPriceUpdateButton'),
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
                	updateWindow.isUpdate = true;
                	//updateWindow.pricePlanEntity = record.data;
                	//pricing.pricePlanId = record.get('outerPriceId');
        			
					//updateWindow.show();
                	var params = {'outerPriceVo':{'outerPriceId':record.get('outerPriceId')}};
    				var successFun = function(json){
						updateWindow.pricePlanEntity = json.outerPriceVo.outerPricePlanDto;
						pricing.pricePlanId = json.outerPriceVo.outerPricePlanDto.outerPriceId;
    					updateWindow.show();
    					//pricing.pagingBar.moveFirst();
    				};
    				var failureFun = function(json){
    					if(Ext.isEmpty(json)){
    						pricing.showErrorMes(pricing.outerPrice.i18n('foss.pricing.requestTimedOut'));//请求超时
    					}else{
    						pricing.showErrorMes(json.message);
    					}
    				};
    				var url = pricing.realPath('selectById.action');
    				pricing.requestJsonAjax(url,params,successFun,failureFun);
				}
			},{
				iconCls:'deppon_icons_softwareUpgrade',
				tooltip: pricing.outerPrice.i18n('foss.pricing.replicationScheme'), 
				disabled: !pricing.outerPrice.isPermission('outerPrice/outerPriceCopyButton'),
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
					var pricePlanId = record.get('outerPriceId');
					var active = record.get('active');
					var name = record.get('name');
					if(active == 'N'){
						pricing.showErrorMes(pricing.outerPrice.i18n('foss.pricing.pleaseSelectTheProgramHaBbeenActivatedCopy'));
					}else{
						pricing.showQuestionMes(pricing.outerPrice.i18n('foss.pricing.toDeterminePriceProgramCopy'),function(e){
							if(e=='yes'){
								var me = this;
								//处理复制功能
								var updateWindow =  grid.up().getUpdatePricePlanWindow();
								var params = {'outerPriceVo':{'outerPriceId':pricePlanId, 'copyName':name}};
								var successFun = function(json){
									pricing.showInfoMes(json.message);
									pricing.pricePlanId = json.outerPriceVo.outerPriceId;
									//record.data.outerPriceId = json.outerPriceVo.outerPriceId;
									//record.data.name = json.outerPriceVo.copyName;
									//record.data.active = 'N';
									updateWindow.pricePlanEntity = record.data;//json.outerPriceVo.pricePlanEntity;
									updateWindow.pricePlanEntity.outerPriceId = json.outerPriceVo.outerPriceId;
									updateWindow.pricePlanEntity.name = json.outerPriceVo.copyName;
									updateWindow.pricePlanEntity.active = 'N';
			    					updateWindow.isUpdate = true;
			    					updateWindow.show();
			    					var waitingProcessGrid = Ext.getCmp('T_pricing-outerPrice_content').getOffLinePricePlanGridPanel();
			    					waitingProcessGrid.store.load();
								};
								var failureFun = function(json){
									if(Ext.isEmpty(json)){
										pricing.showErrorMes(pricing.outerPrice.i18n('foss.pricing.requestTimedOut'));//请求超时
									}else{
										pricing.showErrorMes(json.message);
									}
								};
								var url = pricing.realPath('copyOuterPrice.action');
								pricing.requestJsonAjax(url,params,successFun,failureFun);
							}
						});
					}
				}
			},{
				iconCls:'deppon_icons_end',
				tooltip: pricing.outerPrice.i18n('foss.pricing.stop'), 
				disabled: !pricing.outerPrice.isPermission('outerPrice/outerPriceStopButton'),
				width:42,
				getClass : function (v, m, r, rowIndex) {
					if (r.get('active') === 'Y') {
					    return 'deppon_icons_end';
					} else {
					    return 'statementBill_hide';
					}
				},
				handler: function(grid, rowIndex, colIndex){
					//得到当前行
					var record = grid.getStore().getAt(rowIndex);
					//得到当前行中的具体字段
					var pricePlanId = record.get('outerPriceId');
					var endTime = record.get('endTime');

                	var stopPricePlanWindow =  grid.up().getStopPricePlanWindow();
                	
                	stopPricePlanWindow.pricePlanEntity = record.data;
					pricing.pricePlanId = pricePlanId;
                	stopPricePlanWindow.show();
					//me.getStopPricePlanWindow(pricePlanId).show();

				}
			}]
		},{
			hide:true,
			text: 'ID',
			hidden: true,
	        dataIndex: 'outerPriceId'
		},{
			width: 140,
			text: pricing.outerPrice.i18n('foss.pricing.scenarioName'),//方案名称
	        dataIndex: 'name'
		},{
			width: 140,
			hidden: true,
			text: "目的站编号",
	        dataIndex: 'partialLineCode'
		},{
			width: 140,
			text: "目的站",
	        dataIndex: 'partialLineName'
		},{
			width: 140,
			hidden: true,
			text: "外发外场编号",
	        dataIndex: 'outFieldCode'//productCode
		},{
			width: 140,
			text: "外发外场",
	        dataIndex: 'outFieldName'//productCode
		},{
			width: 70,
			text: "运输类型",
	        dataIndex: 'productCode',
	        renderer: function(value){
				if(value=='TRANS_VEHICLE') {//汽运
					return '汽运';			
				} else if(value=='TRANS_AIRCRAFT') {//空运
					return '空运';
				} else {
					return null;
				}
			}
		},{
			width: 70,
			text: "数据状态",
	        sortable: true,
	        dataIndex: 'active',
	        renderer:function(value){
				if(value=='Y'){//'Y'表示激活
					return "已激活";
				}else if(value=='N'){//'N'表示未激活
					return  "未激活";
				}else{
					return '';
				}
			}
		},{
			text: "生效日期",
	        dataIndex: 'beginTime',
	        renderer:function(value){
				return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
			}
		},{
			text: "截止日期",
	        dataIndex: 'endTime',
	        renderer:function(value){
				return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
			}
		},{
			text : '是否当前版本',
			dataIndex : 'version'
		},{
			dataIndex : 'provCode',
			hidden: true,
			text: "省份编码"
	    },{
	    	dataIndex : 'cityCode',
			hidden: true,
			text: "城市编码"
	    },{
	    	dataIndex : 'countyCode',
			hidden: true,
			text: "区县编码"
	    },{
			dataIndex : 'provName',
			text: "省份"
	    },{
	    	dataIndex : 'cityName',
			text: "城市"
	    },{
	    	dataIndex : 'countyName',
			text: "区县"
	    },{
			dataIndex : 'weightFeeRate',
			text: "重货费率"
	    },{
	    	dataIndex : 'volumeFeeRate',
			text: "轻货费率"
	    },{
	    	dataIndex : 'minFee',
			text: "最低一票"
	    },{
			text: "修改时间",
			width: 140,
	        dataIndex: 'modifyDate',
	        renderer:function(value){
				return Ext.Date.format(new Date(value), 'Y-m-d H:m:s');
			}
		},{
			text: "修改人",
			width: 80,
	        dataIndex: 'modifyUser'
		}];
		me.tbar = [
		{
			text : "新建方案",
			disabled: !pricing.outerPrice.isPermission('outerPrice/outerPriceAddButton'),
			hidden: !pricing.outerPrice.isPermission('outerPrice/outerPriceAddButton'),
			handler :function(){
				var addWindow = me.getAddpricePlanWindow();
				addWindow.isUpdate = false;
				addWindow.show();
				
			} 
		},'-', {
			text : pricing.outerPrice.i18n('foss.pricing.activationProgram'),//激活方案
			disabled: !pricing.outerPrice.isPermission('outerPrice/outerPriceActiveButton'),
			hidden: !pricing.outerPrice.isPermission('outerPrice/outerPriceActiveButton'),
			handler :function(){
				me.activePricePlan();
			} 
		},'-', {
			text : pricing.outerPrice.i18n('foss.pricing.deleteProgram'),
			disabled: !pricing.outerPrice.isPermission('outerPrice/outerPriceDeleteButton'),
			hidden: !pricing.outerPrice.isPermission('outerPrice/outerPriceDeleteButton'),
			handler :function(){
				me.deletePricePlan();
			} 
		},'-',{
			text : pricing.outerPrice.i18n('foss.pricing.immediatelyActivationProgram'),//'立即激活',
			disabled:!pricing.outerPrice.isPermission('outerPrice/outerPriceImmediatelyActiveButton'),
			hidden:!pricing.outerPrice.isPermission('outerPrice/outerPriceImmediatelyActiveButton'),
			handler :function(){
				var selection = Ext.getCmp('T_pricing-outerPrice_content').getOffLinePricePlanGridPanel().getSelectionModel().getSelection()[0];
				pricing.outerPrice.immediatelyActiveId = selection.get('outerPriceId');
				me.immediatelyActiveOffLinePricePlan();
			} 
		},'-', {
			text : pricing.outerPrice.i18n('foss.pricing.immediatelyStopProgram'),//'立即中止',
			disabled:!pricing.outerPrice.isPermission('outerPrice/outerPriceImmediatelyStopButton'),
			hidden:!pricing.outerPrice.isPermission('outerPrice/outerPriceImmediatelyStopButton'),
			handler :function(grid, rowIndex, colIndex){
				var selection = Ext.getCmp('T_pricing-outerPrice_content').getOffLinePricePlanGridPanel().getSelectionModel().getSelection()[0];
				pricing.outerPrice.immediatelyStopId = selection.get('outerPriceId');
				me.immediatelyStopOffLinePricePlan();
			} 
		},'-', {
			text : pricing.outerPrice.i18n('foss.pricing.export'),
			disabled: !pricing.outerPrice.isPermission('outerPrice/outerPriceExportButton'),
			hidden: !pricing.outerPrice.isPermission('outerPrice/outerPriceExportButton'),
			handler :function(){
				//var queryForm = me.getQueryPricePlanForm();
				var queryForm = pricing.outerPrice.queryform;
				var pricePlanExport = '';
				
				var partialLineCode = queryForm.getForm().findField('partialLineCode').getValue(); // 目的站
				var outFieldCode = queryForm.getForm().findField('outFieldCode').getValue(); //外发外场
				var transportType = queryForm.getForm().findField('transportType').getValue(); //运输类型
				var active = queryForm.getForm().findField('active').getValue(); //数据状态
				
				if(!Ext.isEmpty(partialLineCode)){
					pricePlanExport = 'outerPriceVo.outerPriceCondtionDto.partialLineCode='+partialLineCode;
				}
				if(!Ext.isEmpty(outFieldCode)){
					pricePlanExport = pricePlanExport + '&' + 'outerPriceVo.outerPriceCondtionDto.partialLineCode='+outFieldCode;
				}
				pricePlanExport = pricePlanExport + '&' + 'outerPriceVo.outerPriceCondtionDto.productCode='+transportType;
				pricePlanExport = pricePlanExport + '&' + 'outerPriceVo.outerPriceCondtionDto.active='+active;
				
				/*if(!Ext.isEmpty(deptRegionCode)){
					if(!Ext.isEmpty(pricePlanExport)){
						pricePlanExport = pricePlanExport+'&'+'priceManageMentVo.pricePlanEntity.deptRegionCode='+deptRegionCode;
					}else{
						pricePlanExport = 'priceManageMentVo.pricePlanEntity.deptRegionCode='+deptRegionCode;
					}
				}*/
				var url = pricing.realPath('exportOuterPrice.action');
				if(!Ext.isEmpty(pricePlanExport)){
					url = url+'?'+pricePlanExport;
				}
				window.location=url;
				pricePlanExport = '';
 			} 
		},'-', {
			text : pricing.outerPrice.i18n('foss.pricing.import'),
			disabled: !pricing.outerPrice.isPermission('outerPrice/outerPriceImportButton'),
			hidden: !pricing.outerPrice.isPermission('outerPrice/outerPriceImportButton'),
			handler :function(){
			 	me.getUploadPriceform().show();
			} 
		}];
		pricing.pagingBar = me.bbar;
		me.callParent([cfg]);
	}
 	
});
/**
 * 偏线价费用方案明细查询表单
 */
Ext.define('Foss.pricing.outerPrice.OuterPriceDetailForm', {
	extend : 'Ext.form.Panel',
	title: pricing.outerPrice.i18n('i18n.pricingRegion.searchCondition'),
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
			xtype : 'combobox',
			name : 'flightSort',
			allowBlank:false,
			fieldLabel : pricing.outerPrice.i18n('foss.pricing.flightCategory'),
			displayField : 'valueName',
			valueField : 'valueCode',
			queryMode : 'local',
			triggerAction : 'all',
			editable : false,
			flightRecord: null,
			store : FossDataDictionary.getDataDictionaryStore('AIR_FLIGHT_TYPE', 'Foss_baseinfo_flight_FlightSortStore_Id', {
				'valueCode' : '',
				'valueName' : pricing.outerPrice.i18n('i18n.pricingRegion.all')
			}),
			value : ''
		},{
			name: 'arrvRegionId',
			valueField: 'id',
	        fieldLabel:pricing.outerPrice.i18n('foss.pricing.destinationArea'),//目的区域
	        xtype : 'commonpriceregionselector',
	        airPriceFlag :'Y'
		},{
			xtype : 'container',
			columnWidth : .2,
			margin : '0 0 0 0',
			items : [{
						xtype : 'button', 
						colspan : 1,
						text : pricing.outerPrice.i18n('foss.pricing.reset'),//重置
						handler : function() {
							this.getForm().reset();
						}
					},{
					xtype : 'button', 
					width:70,
					text : pricing.outerPrice.i18n('i18n.pricingRegion.search'),
					cls:'yellow_button',
					handler : function() {
						if(me.getForm().isValid()){
							var grid = me.up('window').getPricePlanDetailShowGridPanel();
							grid.getStore().load();
						}
					}
				}
			]
		}];
		me.callParent([cfg]);
	}
});
/**
 * 偏线价费用方案明细列表
 */
Ext.define('Foss.pricing.outerPrice.OuterPriceDetailShowGridPanel', {
	extend: 'Ext.grid.Panel',
	title :pricing.outerPrice.i18n('i18n.pricingRegion.searchResults'),//查询结果
	frame: true,
	height :450,
    sortableColumns:false,
    enableColumnHide:false,
    enableColumnMove:false,
	stripeRows : true, // 交替行效果
	selType : "rowmodel", // 选择类型设置为：行选择
	emptyText: pricing.outerPrice.i18n('foss.pricing.theQueryResultIsEmpty'),//查询结果为空
	
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
			text : pricing.outerPrice.i18n('i18n.pricingRegion.num')//序号
		},{
			text :pricing.outerPrice.i18n('foss.pricing.destinationStation'),//目的站
			dataIndex : 'arrvRegionName',
			flex:2
		},{
			text :pricing.outerPrice.i18n('foss.pricing.flightType'),//航班类型
			dataIndex : 'flightTypeName',
			flex:2
		},{
			text :pricing.outerPrice.i18n('foss.pricing.cargoType'),//货物类型
			dataIndex : 'goodsTypeName',
			flex:2
		},{
			text :pricing.outerPrice.i18n('foss.pricing.heavyGoodsPrices'),//重货价格
			dataIndex : 'heavyPrice',
			flex:2
		},{
			text : pricing.outerPrice.i18n('foss.pricing.theLowestVotes'),//最低一票
			dataIndex : 'minimumOneTicket',
			flex:2
		},{
			width: 100,
			text: "是否接货",
	        dataIndex: 'centralizePickup',
	        renderer:function(value){
				if(value=='Y'){//'Y'表示激活
					return "是";
				}else if(value=='N'){//'N'表示未激活
					return  "否";
				}else{
					return '';
				}
			}
		},{
			text : pricing.outerPrice.i18n('foss.pricing.remark'),//备注
			dataIndex : 'remark',
			flex:2
		}];
		me.store = Ext.create('Foss.pricing.outerPrice.OuterPriceDeatilStore',{
			autoLoad : false,
			pageSize : 10,
			listeners: {
				beforeload: function(store, operation, eOpts){
					var queryForm =me.up('window').getQueryPricePlanDetailForm();
					var pricePlanId = me.up('window').pricePlanId;
					if(queryForm!=null){
						Ext.apply(operation,{
							params : {
								'priceManageMentVo.queryPricePlanDetailBean.flightTypeCode' : queryForm.getForm().findField('flightSort').getValue(),//航班类型编码
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
		   			text : pricing.outerPrice.i18n('foss.pricing.export'),
		   			handler :function(){
		   				var queryForm = me.up('window').getQueryPricePlanDetailForm();
		   				var pricePlanId = me.up('window').pricePlanId;
		   				var flightTypeCode = queryForm.getForm().findField('flightSort').getValue()
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
		   				if(!Ext.isEmpty(flightTypeCode)){
		   					if(!Ext.isEmpty(flightTypeCode)){
		   						pricePlanExport = pricePlanExport+'&'+'priceManageMentVo.queryPricePlanDetailBean.flightTypeCode='+flightTypeCode;
		   					}else{
		   						pricePlanExport = 'priceManageMentVo.queryPricePlanDetailBean.flightTypeCode='+flightTypeCode;
		   					}
		   				}
		   				var url = pricing.realPath('exportOuterPriceDetail.action');
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

//货物类型MODEL
Ext.define('Foss.pricing.GoodsTypeEntity', {
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

/**
 * 航偏线价费用方案明细form
 */
Ext.define('Foss.pricing.outerPrice.OuterPriceDetailForm', {
	extend : 'Ext.form.Panel',
	frame: true,
	height:430,
	collapsible: true,
	isSearchComb:true,
    defaults : {
    	colspan : 2,
    	margin : '5 5 5 5',
    	labelWidth:90,
    	anchor : '100%'
    },
	defaultType : 'textfield',
	layout: {
        type: 'table',
        columns: 2
    },
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [{
			name: 'arrvRegionCode',
	        fieldLabel:pricing.outerPrice.i18n('foss.pricing.destinationArea'),//目的区域
	        allowBlank:false,
	        xtype : 'commonpriceregionselector',
	        airPriceFlag:'Y',
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
			xtype : 'combobox',
			name : 'flightSort',
			allowBlank:false,
			fieldLabel : pricing.outerPrice.i18n('foss.pricing.flightCategory'),
			displayField : 'valueName',
			valueField : 'valueCode',
			valueName:null,
			valueCode:null,
			queryMode : 'local',
			triggerAction : 'all',
			editable : false,
			store : FossDataDictionary.getDataDictionaryStore('AIR_FLIGHT_TYPE', 'Foss_baseinfo_flight_FlightSortStore_Id'),
			value : '',
			listeners:{
	        	select:function(comb,records,eOpts){
	        		var record = records[0];
	        		comb.valueName = record.get('valueName');
	        		comb.valueCode = record.get('valueCode'); 
	        	}
	        }
		},{
			xtype : 'combo',
			name: 'goodsTypeId',
			queryMode: 'local',
			displayField: 'name',
		    valueField: 'code',
		    allowBlank:false,
		    editable:false,
		    store:pricing.getStore(null,'Foss.pricing.GoodsTypeEntity',null,pricing.goodTypeList),
	        fieldLabel: pricing.outerPrice.i18n('foss.pricing.cargoType'),//货物类型
	        goodsTypeName:null,
	        goodsTypeCode:null,
	        listeners:{
	        	select:function(comb,records,eOpts){
	        		var record = records[0];
	        		comb.goodsTypeCode = record.get('code');
	        		comb.goodsTypeName = record.get('name'); 
	        	}
	        }
		},{
			name: 'heavyPrice',
			allowBlank:false,
			decimalPrecision:2,
			step:0.01,
		    maxValue: 999999.99,
		    minValue:0,
	        fieldLabel: pricing.outerPrice.i18n('foss.pricing.heavyGoodsPrices'),//重货价格
	        xtype : 'numberfield'
		
		},{
			name: 'minimumOneTicket',
			allowBlank:false,
			decimalPrecision:0,
			step:1,
		    maxValue: 99999999,
		    minValue:0,
	        fieldLabel: pricing.outerPrice.i18n('foss.pricing.theLowestVotes'),//最低一票
	        xtype : 'numberfield'
		},{
			 xtype:'radiogroup',
			 vertical:true,
			 allowBlank:false,
			 name:'centralizePickup',
			 fieldLabel:pricing.outerPrice.i18n('foss.pricing.whetherorNot'),
			 items:[{
			 	 xtype:'radio',
			     boxLabel:pricing.outerPrice.i18n('i18n.pricingRegion.ye'),//是
			     name:'centralizePickup',		 
			     inputValue:'Y'
		     },{
		    	 xtype:'radio',
			     boxLabel:pricing.outerPrice.i18n('i18n.pricingRegion.no'),//否
			     name:'centralizePickup',
			     inputValue:'N'
			     }]
		},{
			xtype: 'textareafield',
			width:300,
		    fieldLabel: pricing.outerPrice.i18n('foss.pricing.remark'),//备注
		    maxLength:200,
	        name:'remark'
		}];
		me.callParent([cfg]);
	}
});

/**
 * 航偏线价费用方案明细信息新增弹出窗口window
 */
Ext.define('Foss.pricing.outerPrice.OuterPriceDetailWindow',{
	extend : 'Ext.window.Window',
	title: pricing.outerPrice.i18n('foss.pricing.aBreakdownOfNew'),//明细信息新增
	closable : true,
	modal : true,
	resizable:false,
	closeAction : 'hide',
	parent:null,
	width :450,
	height :600,
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
    		this.pricePlanDetailForm = Ext.create('Foss.pricing.outerPrice.OuterPriceDetailForm');
    	}
    	return this.pricePlanDetailForm;
    },
    
    //想GRID中设置数据
    commitPricePlanDetail:function(grid){
    	var me = this;
    	//得到明细form
    	var form = me.getPricePlanDetailForm().getForm();
    	if(form.isValid()){
    		var pricePlanDetailDto = new Foss.pricing.outerPrice.OffLinePricePlanDetailDto();
    		form.updateRecord(pricePlanDetailDto);
			//获取明细信息
	    	var goodsTypeCode = form.findField('goodsTypeId').getValue();
	    	var flightTypeCode = form.findField('flightSort').getValue();
	    	var arrvRegionCode = form.findField('arrvRegionCode').getValue();
	    	var arrvRegionName = form.findField('arrvRegionCode').arrvRegionName;
	    	var arrvRegionId = form.findField('arrvRegionCode').arrvRegionId;
	    	var remark = form.findField('remark').getValue();
	    	
	    	//设置明细信息
	    	pricePlanDetailDto.set('arrvRegionCode',arrvRegionCode);
	    	pricePlanDetailDto.set('arrvRegionId',arrvRegionId);
	    	pricePlanDetailDto.set('arrvRegionName',arrvRegionName);
	    	pricePlanDetailDto.set('remark',remark);
	    	pricePlanDetailDto.set('pricePlanId',pricing.pricePlanId);
	    	pricePlanDetailDto.set('flightTypeCode',flightTypeCode);
	    	pricePlanDetailDto.set('goodsTypeCode',goodsTypeCode);
	    	
			//制定json请求参数
			var params = {'priceManageMentVo':{'pricePlanDetailDto':pricePlanDetailDto.data}};
			//ajax请求
			var url = pricing.realPath('addOuterPriceDetail.action');
			
			//成功提示
			var successFun = function(json){
				pricing.showInfoMes(json.message);
				var arrayDate = json.priceManageMentVo.pricePlanDetailDtoList;
				if(!Ext.isEmpty(arrayDate)){
					grid.store.loadData(arrayDate);//显示第一页	
				}
		    };
		    //失败提示
		    var failureFun = function(json){
		    	if(Ext.isEmpty(json)){
					pricing.showErrorMes(pricing.outerPrice.i18n('i18n.pricingRegion.requestTimeOut'));
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
		me.items = [me.getPricePlanDetailForm()];//设置window的元素
		me.fbar = [{
			text : pricing.outerPrice.i18n('i18n.pricingRegion.cancel'),//取消
			handler :function(){
				me.close();
			} 
		},{
			text : pricing.outerPrice.i18n('foss.pricing.reset'),//重置
			margin:'0 185 0 0',
			handler :function(){
				me.getPricePlanDetailForm().getForm().reset();
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
 * 修改价格明细信息Window
 */
Ext.define('Foss.pricing.outerPrice.ModifyOffLinePricePlanDetailWindow',{
	extend : 'Ext.window.Window',
	title: '修改',//明细信息新增
	closable : true,
	modal : true,
	resizable:false,
	closeAction : 'hide',
	parent:null,
	width :450,
	height :450,
	pricePlanDetailDto:null,
	grid:null,																	//父 grid
	listeners:{
		beforehide:function(me){
			me.getPricePlanDetailForm().getForm().reset();
		},
		beforeshow:function(me){
			//赋值目的地区域相关信息
			me.getPricePlanDetailForm().getForm().loadRecord(new Foss.pricing.outerPrice.OffLinePricePlanDetailDto(me.pricePlanDetailDto));
			me.getPricePlanDetailForm().getForm().findField('arrvRegionCode').setCombValue(me.pricePlanDetailDto.arrvRegionName,me.pricePlanDetailDto.arrvRegionName);
			me.getPricePlanDetailForm().getForm().findField('arrvRegionCode').arrvRegionId = me.pricePlanDetailDto.arrvRegionId;
			me.getPricePlanDetailForm().getForm().findField('arrvRegionCode').arrvRegionName = me.pricePlanDetailDto.arrvRegionName;
			
			//赋值航班类型相关信息
			me.getPricePlanDetailForm().getForm().findField('flightSort').setValue(me.pricePlanDetailDto.flightTypeName); 
			me.getPricePlanDetailForm().getForm().findField('flightSort').valueCode = me.pricePlanDetailDto.flightTypeCode;
			me.getPricePlanDetailForm().getForm().findField('flightSort').valueName = me.pricePlanDetailDto.flightTypeName;
			
			//赋值货物类型相关信息
			me.getPricePlanDetailForm().getForm().findField('goodsTypeId').goodsTypeCode = me.pricePlanDetailDto.goodsTypeCode;
			me.getPricePlanDetailForm().getForm().findField('goodsTypeId').setValue(me.pricePlanDetailDto.goodsTypeName)
			
			//赋值最低一票
			me.getPricePlanDetailForm().getForm().findField('minimumOneTicket').setValue(me.pricePlanDetailDto.minimumOneTicket);
		}
	},
    //明细信息新增-FORM
    pricePlanDetailForm:null,
    getPricePlanDetailForm:function(){
    	if(Ext.isEmpty(this.pricePlanDetailForm)){
    		this.pricePlanDetailForm = Ext.create('Foss.pricing.outerPrice.OuterPriceDetailForm');
    	}
    	return this.pricePlanDetailForm;
    },
     	
    updatePriceDetailPlan:function(grid){
    	var me = this;
    	//得到明细form
    	var form = me.getPricePlanDetailForm().getForm();
    	if(form.isValid()){
    		 	var pricePlanDetailDto = new Foss.pricing.outerPrice.OffLinePricePlanDetailDto(me.pricePlanDetailDto);
    		form.updateRecord(pricePlanDetailDto);
			//获取明细信息
	    	var goodsTypeCode = form.findField('goodsTypeId').goodsTypeCode;
	    	var flightTypeCode = form.findField('flightSort').valueCode;
	    	var flightTypeName = form.findField('flightSort').valueName;
	    	var arrvRegionName = form.findField('arrvRegionCode').arrvRegionName;
	    	var arrvRegionId = form.findField('arrvRegionCode').arrvRegionId;
	    	var remark = form.findField('remark').getValue();
	    	
	    	//设置明细信息
	    	pricePlanDetailDto.set('arrvRegionId',arrvRegionId);
	    	pricePlanDetailDto.set('arrvRegionName',arrvRegionName);
	    	pricePlanDetailDto.set('remark',remark);
	    	pricePlanDetailDto.set('pricePlanId',pricing.pricePlanId);
	    	pricePlanDetailDto.set('flightTypeCode',flightTypeCode);
	    	pricePlanDetailDto.set('goodsTypeCode',goodsTypeCode);
	    	
			//制定json请求参数
			var params = {'priceManageMentVo':{'pricePlanDetailDto':pricePlanDetailDto.data}};
			//ajax请求
			var url = pricing.realPath('updateAirPriceDetailPlan.action');
			
			//成功提示
			var successFun = function(json){
				pricing.showInfoMes(json.message);
				var arrayDate = json.priceManageMentVo.pricePlanDetailDtoList;
				if(!Ext.isEmpty(arrayDate)){
					grid.store.loadData(arrayDate);//显示第一页	
				}
		    };
		    //失败提示
		    var failureFun = function(json){
		    	if(Ext.isEmpty(json)){
					pricing.showErrorMes(pricing.outerPrice.i18n('i18n.pricingRegion.requestTimeOut'));
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
		me.items = [me.getPricePlanDetailForm()];//设置window的元素
		me.fbar = [{
			text : pricing.outerPrice.i18n('i18n.pricingRegion.cancel'),//取消
			handler :function(){
				me.close();
			} 
		},{
			text : pricing.outerPrice.i18n('foss.pricing.reset'),//重置
			margin:'0 185 0 0',
			handler :function(){
				me.getPricePlanDetailForm().getForm().reset();
			} 
		},{
			text : "修改",//提交
			cls:'yellow_button',
			handler :function(){
				me.updatePriceDetailPlan(config.grid);
			} 
		}];
		me.callParent([cfg]);
	}
});

/**
 * 偏线价费用方案批次信息录入form
 */
Ext.define('Foss.pricing.outerPrice.OuterPriceAddFormPanel',{
	extend : 'Ext.form.Panel',
	title: "出发地信息",
	parent:null,
	frame: true,
	operaterCode:null,
	pricePlanEntity: null,
	priceRegionId: null,
	layout: 'column',
	height:300,
    defaults : {
    	columnWidth : 1,
    	margin : '5 10 5 10',
    	width:160,
		labelSeparator:'',
		labelWidth:80,
		xtype : 'textfield'
    },
	items: [
		{
			name: 'id',
			hidden : true
		},{
			name: 'name',
			allowBlank:false,
			columnWidth: .5,
			maxLength : 65,
	        fieldLabel:pricing.outerPrice.i18n('foss.pricing.scenarioName')//方案名称
		},{			
			name : 'partialLineCode',
			xtype : 'commonvehagencydeptselector',
			fieldLabel : '目的站',
			columnWidth: .5,
			listeners:{
				'select':function(combo,records,eOpts){
					if(records.length>0){
						var form = this.up('form').getForm();             
						var record = form.findField("partialLineCode");
						form.findField('provCode').setValue(record.displayTplData[0].provCode);
						form.findField('provName').setValue(record.displayTplData[0].provName);
						form.findField('cityCode').setValue(record.displayTplData[0].cityCode);
	 					form.findField('cityName').setValue(record.displayTplData[0].cityName);	
						form.findField('countyCode').setValue(record.displayTplData[0].countyCode);
	 					form.findField('countyName').setValue(record.displayTplData[0].countyName);  
					}
				}
			}
		},{
			name: 'provCode',
			readOnly: true,
			allowBlank:false,
			columnWidth: .5,
			hidden :true
		},{
			name: 'provName',
			readOnly: true,
			allowBlank:false,
			columnWidth: .5,
	        fieldLabel:pricing.outerPrice.i18n('foss.pricing.provinceName')//省份
		},{
			name: 'cityCode',
			readOnly: true,
			allowBlank:false,
			columnWidth: .5,
			hidden :true
		},{
			name: 'cityName',
			readOnly: true,
			allowBlank:false,
			columnWidth: .5,
	        fieldLabel:pricing.outerPrice.i18n('foss.pricing.cityName')//市
		},{
			name: 'countyCode',
			readOnly: true,
			allowBlank:false,
			columnWidth: .5,
			hidden :true
		},{
			name: 'countyName',
			readOnly: true,
			allowBlank:false,
			columnWidth: .5,
	        fieldLabel:pricing.outerPrice.i18n('foss.pricing.areaName')//区
		},{
			name: 'transportType',
			fieldLabel: '运输类型',//运输类型
			allowBlank:false,
			columnWidth: .5,
			xtype: 'combobox',
			valueField:'valueCode', 
			displayField: 'valueName',
			value: 'TRANS_VEHICLE',
			queryMode:'local',
			triggerAction:'all',
			store : FossDataDictionary.getDataDictionaryStore("TRANS_TYPE")
			//name: 'productCode',
			//columnWidth: .5,
		},{
			xtype : 'commontransfercenterselector', 
			fieldLabel : '外发外场', 
			allowBlank:false,
			active:'Y',  
			name:'outFieldCode',
			columnWidth: .5
		},{
			xtype:'datefield',
			allowBlank:false,
			fieldLabel:pricing.outerPrice.i18n('foss.pricing.availabilityDate'),//生效日期
			format:'20y-m-d',
			name:'beginTime',
			columnWidth: .5
		},{
			xtype: 'numberfield',
			anchor: '100%',
			minValue: 0,
			//step:0.01,
		    maxValue: 99999999,
			allowBlank:false,
			fieldLabel:'重货价格',
			name:'weightFeeRate',
			columnWidth: .5
		},{
			xtype: 'numberfield',
			anchor: '100%',
			minValue: 0,
			maxValue: 99999999,
			allowBlank:false,
			fieldLabel:'轻货价格',
			decimalPrecision :0,
			name:'volumeFeeRate',
			columnWidth: .5
		},{
			xtype: 'numberfield',
			anchor: '100%',
			minValue: 0,
			maxValue: 99999999,
			allowBlank:false,
			fieldLabel:'最低一票',
			name:'minFee',
			allowDecimals : false,//只能为数字，不能为小数
			columnWidth: .5
		}],
	
	/**修改价格方案**/
	commitUpdatePricePlan:function(){
    	var me = this;
    	var baseForm = me.getForm();
    	if(baseForm.isValid()){//校验form是否通过校验
    		var pricePlanEntity = me.up('window').pricePlanEntity;
    		var pricePlanModel = new Foss.pricing.outerPrice.OuterPriceModel(pricePlanEntity);
    		baseForm.updateRecord(pricePlanModel);//将FORM中数据设置到MODEL里面
    		
    		//处理特殊字段
    		var priceRegionId = baseForm.findField('priceRegionCode').priceRegionId;
    		var priceRegionName = baseForm.findField('priceRegionCode').priceRegionName;
    		var priceRegionCode = baseForm.findField('priceRegionCode').value;
    		if(null != priceRegionId){
    			pricePlanModel.set('priceRegionId',priceRegionId);
    		}
    		if(null != priceRegionName){
    			pricePlanModel.set('priceRegionName',priceRegionName);
    		}
    		if(null != priceRegionCode){
    			pricePlanModel.set('priceRegionCode',priceRegionCode);
    		}
    		var params = {'outerPriceVo':{'outerPriceEntity':pricePlanModel.data}};//组织需要修改的数据
    		var successFun = function(json){
				pricing.showInfoMes(json.message);//提示新增成功
			};
			var failureFun = function(json){
				if(Ext.isEmpty(json)){
					pricing.showErrorMes(pricing.outerPrice.i18n('foss.pricing.requestTimedOut'));//请求超时
				}else{
					pricing.showErrorMes(json.message);//提示失败原因
				}
			};
			var url = pricing.realPath('updateOuterPrice.action');//请求价格方案修改
			pricing.requestJsonAjax(url,params,successFun,failureFun);//发送AJAX请求
    	}
	 },
	
	/***批次保存***/
	commitPricePlan:function(){
		var me = this;
    	//获取表单
    	var form = me.getForm(); 
    	var pricePlanModel = new Foss.pricing.outerPrice.OuterPriceModel();
    	if(form.isValid()){
    		form.updateRecord(pricePlanModel);
    		var productCode = form.findField('transportType').getValue();	//
    		if (productCode == null ||productCode == '') {
    			pricing.showErrorMes("运输类型不能为空！");
    			return false;
    		}
    		var outFieldCode = form.findField('outFieldCode').getValue();
    		if (outFieldCode == null || outFieldCode == '') {
    			pricing.showErrorMes("外发外场不能为空！");
    			return false;
    		}
    		outFieldCode = form.findField('outFieldCode').displayTplData[0].orgCode;	//外发外场
    		if (outFieldCode == null || outFieldCode == '' || outFieldCode == undefined) {
    			pricing.showErrorMes("外发外场不能为空！");
    			return false;
    		}
    		
    		var beginTime = form.findField('beginTime').getValue();	//

    		var time = Date.parse(beginTime);
    		var nowDate = new Date(); 
    		if (time < Date.parse(nowDate.format('yyyy-MM-dd'))) {
    			pricing.showErrorMes("生效日期不能小于当前日期！");
    			return false;
    		}
    		var name = form.findField('name').getValue();
    		var partialLineCode = form.findField('partialLineCode').getValue();//目的站
    		var provCode = form.findField('provCode').getValue();	//省
    		var cityCode = form.findField('cityCode').getValue();	//市
    		var countyCode = form.findField('countyCode').getValue();	//区
    		var weightFeeRate = form.findField('weightFeeRate').getValue();
    		var volumeFeeRate = form.findField('volumeFeeRate').getValue();
    		var minFee = form.findField('minFee').getValue();
			
    		//处理特殊字段
    		pricePlanModel.set('name',name);
    		pricePlanModel.set('partialLineCode',partialLineCode);
    		pricePlanModel.set('outFieldCode',outFieldCode);
    		pricePlanModel.set('productCode',productCode);
    		pricePlanModel.set('provCode',provCode);
    		pricePlanModel.set('cityCode',cityCode);
    		pricePlanModel.set('countyCode',countyCode);
    		pricePlanModel.set('beginTime',beginTime);
    		pricePlanModel.set('weightFeeRate',weightFeeRate);
    		pricePlanModel.set('volumeFeeRate',volumeFeeRate);
    		pricePlanModel.set('minFee',minFee);
    		
    		var params = {'outerPriceVo':{'outerPriceEntity':pricePlanModel.data}};
    		var url = pricing.realPath('addOuterPrice.action');
    		
    		//成功提示
    		var successFun = function(json){
    			pricing.showInfoMes(json.message);
    			//成功后获取价格方案主ID
				pricing.pricePlanId = json.outerPriceVo.outerPriceEntity.outerPriceId;  
				//me.getPricePlanDetailGridPanel().store.add();
				//获取formPanel所有属性,全部设置为只读	    		
	    		var itemArrays = me.items.items;
	    		Ext.Array.each(itemArrays,function(value,index,arrays){
	    			itemArrays[index].setReadOnly(true);
	    		});
	    		me.getDockedItems()[1].items.items[1].setDisabled(true);//重置按钮不可用
				me.getDockedItems()[1].items.items[2].setDisabled(true);//保存按钮不可用
				//var dockedItems = me.up('window').getPricePlanDetailGridPanel().getDockedItems();
				//dockedItems[1].items.items[0].setDisabled(false);//新增明明细按钮可用
				//dockedItems[1].items.items[2].setDisabled(false);//删除明细按钮可用
				//dockedItems[1].items.items[4].setDisabled(false);//导入明细按钮可用
	    	};
    	    
    	    //失败提示
    	    var failureFun = function(json){
    	    	if(Ext.isEmpty(json)){
    				pricing.showErrorMes(pricing.outerPrice.i18n('i18n.pricingRegion.requestTimeOut'));
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
    			this.pricePlanDetailGridPanel = Ext.create('Foss.pricing.outerPrice.OuterPriceDetailGridPanel');
    			pricing.pricePlanDetailGridPanel  = this.pricePlanDetailGridPanel;
    	}
    	return this.pricePlanDetailGridPanel;
    },	
	//构造函数
	constructor : function(config) {
		var me = this, 
		cfg = Ext.apply({}, config);
		me.fbar = [{
			text :pricing.outerPrice.i18n('i18n.pricingRegion.cancel'),//取消
			handler :function(){
				me.up('window').isUpdate = null;
				me.up().close();
			}
		},{
			text : pricing.outerPrice.i18n('foss.pricing.reset'),//重置
			handler :function(){
					me.getForm().reset();//表格重置
			} 
		},{
			text : pricing.outerPrice.i18n('foss.pricing.save'),//保存
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
 * 偏线价费用方案批次信息修改form
 */
Ext.define('Foss.pricing.outerPrice.OuterPriceUpdateFormPanel',{
	extend : 'Ext.form.Panel',
	title: "出发地信息",
	parent:null,
	frame: true,
	operaterCode:null,
	pricePlanEntity: null,
	priceRegionId: null,
	layout: 'column',
	height:300,
    defaults : {
    	columnWidth : 1,
    	margin : '5 10 5 10',
    	width:160,
		labelSeparator:'',
		labelWidth:80,
		xtype : 'textfield'
    },
	items: [
		{
			name: 'id',
			hidden : true
		},{
			name: 'name',
			allowBlank:false,
			maxLength : 65,
			columnWidth: .5,
	        fieldLabel:pricing.outerPrice.i18n('foss.pricing.scenarioName')//方案名称
		},{
			name : 'partialLineCode',
			xtype : 'commonvehagencydeptselector',
			fieldLabel : '目的站',
			columnWidth: .5,
			listeners:{
				'select':function(combo,records,eOpts){
					if(records.length>0){
						var form = this.up('form').getForm();             
						var record = form.findField("partialLineCode");
						form.findField('provCode').setValue(record.displayTplData[0].provCode);
						form.findField('provName').setValue(record.displayTplData[0].provName);
						form.findField('cityCode').setValue(record.displayTplData[0].cityCode);
	 					form.findField('cityName').setValue(record.displayTplData[0].cityName);	
						form.findField('countyCode').setValue(record.displayTplData[0].countyCode);
	 					form.findField('countyName').setValue(record.displayTplData[0].countyName); 				
					}
				}/*,
				'change' : function(field, event, eOpts) {
					var form = field.up('form').getForm(),
					partialLineCode = form.findField("partialLineCode");
					
					if (field.value != '' && field.value != null) {
						//vehicleNo.setReadOnly(false);
						//vehicleNo.getStore().un('beforeload');
						//vehicleNo.getStore().on('beforeload', function(store,operation,eOpts) {
						partialLineCode.getStore().un('beforeload');
						partialLineCode.getStore().on('beforeload', function(store,operation,eOpts) {
							var searchParams = operation.params;
							if (Ext.isEmpty(searchParams)) {
								searchParams = {};
								Ext.apply(operation, {
									params : searchParams
								});
							}
							searchParams['outerBranchEntity.agentDeptName'] = field.value;
						});
						partialLineCode.getStore().load();
						partialLineCode.expand();
					} else {
						partialLineCode.setValue('');
					}
				}*/
			}
		},{
			name: 'provCode',
			readOnly: true,
			allowBlank:false,
			columnWidth: .5,
			hidden :true
		},{
			name: 'provName',
			readOnly: true,
			allowBlank:false,
			columnWidth: .5,
	        fieldLabel:pricing.outerPrice.i18n('foss.pricing.provinceName')//省
		},{
			name: 'cityCode',
			readOnly: true,
			allowBlank:false,
			columnWidth: .5,
			hidden :true
		},{
			name: 'cityName',
			readOnly: true,
			allowBlank:false,
			columnWidth: .5,
	        fieldLabel:pricing.outerPrice.i18n('foss.pricing.cityName')//市
		},{
			name: 'countyCode',
			readOnly: true,
			allowBlank:false,
			columnWidth: .5,
			hidden :true
		},{
			name: 'countyName',
			readOnly: true,
			allowBlank:false,
			columnWidth: .5,
	        fieldLabel:pricing.outerPrice.i18n('foss.pricing.areaName')//区
		},
		/*{
			fieldLabel : '省市区',
			provinceWidth : 120,
			cityWidth : 120,
			areaWidth : 120,
			cityLabel : '市',
			cityName : 'cityName',//名称
			provinceLabel : '省',
			provinceName:'privateName',//省名称
			areaLabel : '县',
			areaName : 'areaName',// 县名称
			type : 'P-C-C',
			columnWidth: 1,
			xtype : 'linkregincombselector'
		},*/{
			name: 'transportType',
			fieldLabel: '运输类型',//运输类型
			columnWidth: .5,
			xtype: 'combobox',
			valueField:'valueCode', 
			displayField: 'valueName',
			value: '',
			queryMode:'local',
			triggerAction:'all',
			convert:function(value, record){
				if(value=='TRANS_VEHICLE'){//汽运
					return '汽运'; 
				}else if(value=='TRANS_AIRCRAFT'){//空运
					return '空运';
				}else{
					return null;
				}
			},
			store : FossDataDictionary.getDataDictionaryStore("TRANS_TYPE")
			//name: 'productCode',
			//columnWidth: .5,
		},{
			xtype : 'commontransfercenterselector', 
			fieldLabel : '外发外场', 
			allowBlank:false,
			active:'Y',  
			name:'outFieldCode',
			columnWidth: .5
		},{
			xtype:'datefield',
			allowBlank:false,
			fieldLabel:pricing.outerPrice.i18n('foss.pricing.availabilityDate'),//生效日期
			format:'20y-m-d',
			name:'beginTime',
			columnWidth: .5
		},{
			xtype: 'numberfield',
			anchor: '100%',
			minValue: 0,
			allowBlank:false,
			fieldLabel:'重货价格',
			name:'weightFeeRate',
			columnWidth: .5
		},{
			xtype: 'numberfield',
			anchor: '100%',
			minValue: 0,
			allowBlank:false,
			fieldLabel:'轻货价格',
			name:'volumeFeeRate',
			columnWidth: .5
		},{
			xtype: 'numberfield',
			anchor: '100%',
			minValue: 0,
			maxValue: 99999999,
			allowBlank:false,
			fieldLabel:'最低一票',
			allowDecimals : false,//只能为数字，不能为小数
			name:'minFee',
			columnWidth: .5
		}],
	
	/**修改价格方案**/
	commitUpdatePricePlan:function(){
    	var me = this;
    	var form = me.getForm();
    	if(form.isValid()){//校验form是否通过校验
    		var pricePlanEntity = me.up('window').pricePlanEntity;
    		var pricePlanModel = new Foss.pricing.outerPrice.OuterPriceModel(pricePlanEntity);
    		
    		form.updateRecord(pricePlanModel);//将FORM中数据设置到MODEL里面
    		
	    	var productCode = form.findField('transportType').valueCode;//运输类型
    		if (productCode == null ||productCode == '') {
    			pricing.showErrorMes("运输类型不能为空！");
    			return false;
    		}
    		var valueCode = form.findField('transportType').displayTplData[0].valueCode;//运输类型
    		if (valueCode == null || valueCode == '' || valueCode == undefined) {
    			
    		} else {
    			productCode = valueCode;
    		}
    		var outFieldCode = form.findField('outFieldCode').getValue();	//外发外场
    		if (outFieldCode == null || outFieldCode == '') {
    			pricing.showErrorMes("外发外场不能为空！");
    			return false;
    		}
    		orgCode = form.findField('outFieldCode').displayTplData[0].orgCode;	//外发外场
    		if (orgCode == null || orgCode == '' || orgCode == undefined) {
    			
    		} else {
    			outFieldCode = orgCode;
    		}
    		
    		var beginTime = form.findField('beginTime').getValue();	//生效时间

    		var time = Date.parse(beginTime);
    		var nowDate = new Date(); 
    		if (time < Date.parse(nowDate.format('yyyy-MM-dd'))) {
    			pricing.showErrorMes("生效日期不能小于当前日期！");
    			return false;
    		}
    		var name = form.findField('name').getValue();
    		var partialLineCode = form.findField('partialLineCode').getValue();//目的站
    		var provCode = form.findField('provCode').getValue();	//省
    		var cityCode = form.findField('cityCode').getValue();	//市
    		var countyCode = form.findField('countyCode').getValue();	//区
    		
    		var weightFeeRate = form.findField('weightFeeRate').getValue();
    		var volumeFeeRate = form.findField('volumeFeeRate').getValue();
    		var minFee = form.findField('minFee').getValue();
			
    		//处理特殊字段
    		pricePlanModel.set('name',name);
    		pricePlanModel.set('partialLineCode',partialLineCode);
    		pricePlanModel.set('outFieldCode',outFieldCode);
    		pricePlanModel.set('productCode',productCode);
    		pricePlanModel.set('provCode',provCode);
    		pricePlanModel.set('cityCode',cityCode);
    		pricePlanModel.set('countyCode',countyCode);
    		pricePlanModel.set('beginTime',beginTime);
    		pricePlanModel.set('weightFeeRate',weightFeeRate);
    		pricePlanModel.set('volumeFeeRate',volumeFeeRate);
    		pricePlanModel.set('minFee',minFee);
    		
    		var params = {'outerPriceVo':{'outerPriceEntity':pricePlanModel.data}};//组织需要修改的数据
    		
    		var successFun = function(json){
				pricing.showInfoMes(json.message);//提示新增成功
				me.up('window').close();
				var waitingProcessGrid = Ext.getCmp('T_pricing-outerPrice_content').getOffLinePricePlanGridPanel();
				waitingProcessGrid.store.load();
			};
			var failureFun = function(json){
				if(Ext.isEmpty(json)){
					pricing.showErrorMes(pricing.outerPrice.i18n('foss.pricing.requestTimedOut'));//请求超时
				}else{
					pricing.showErrorMes(json.message);//提示失败原因
				}
			};
			var url = pricing.realPath('updateOuterPrice.action');//请求价格方案修改
			pricing.requestJsonAjax(url,params,successFun,failureFun);//发送AJAX请求
    	}
	 },
	
	/***批次保存***/
	commitPricePlan:function(){
		var me = this;
    	//获取表单
    	var form = me.getForm(); 
    	var pricePlanModel = new Foss.pricing.outerPrice.OuterPriceModel();
    	if(form.isValid()){
    		form.updateRecord(pricePlanModel);
    		var name = form.findField('name').getValue();
    		var partialLineCode = form.findField('partialLineCode').getValue();//目的站
    		var outFieldCode = form.findField('outFieldCode').getValue();	//最终外场
    		var provCode = form.findField('privateName').getValue();	//省
    		var cityCode = form.findField('cityName').getValue();	//市
    		var countyCode = form.findField('areaName').getValue();	//区
    		var productCode = form.findField('transportType').getValue();	//
    		var beginTime = form.findField('beginTime').getValue();	//
    		var weightFeeRate = form.findField('weightFeeRate').getValue();
    		var volumeFeeRate = form.findField('volumeFeeRate').getValue();
    		var minFee = form.findField('minFee').getValue();
			
    		//处理特殊字段
    		pricePlanModel.set('name',name);
    		pricePlanModel.set('partialLineCode',partialLineCode);
    		pricePlanModel.set('outFieldCode',outFieldCode);
    		pricePlanModel.set('productCode',productCode);
    		pricePlanModel.set('provCode',provCode);
    		pricePlanModel.set('cityCode',cityCode);
    		pricePlanModel.set('countyCode',countyCode);
    		pricePlanModel.set('beginTime',beginTime);
    		pricePlanModel.set('weightFeeRate',weightFeeRate);
    		pricePlanModel.set('volumeFeeRate',volumeFeeRate);
    		pricePlanModel.set('minFee',minFee);
    		
    		var params = {'outerPriceVo':{'outerPriceEntity':pricePlanModel.data}};
    		var url = pricing.realPath('addOuterPrice.action');
    		
    		//成功提示
    		var successFun = function(json){
    			pricing.showInfoMes(json.message);
    			//成功后获取价格方案主ID
				pricing.pricePlanId = json.priceManageMentVo.pricePlanEntity.id;  
				//me.getPricePlanDetailGridPanel().store.add();
				//获取formPanel所有属性,全部设置为只读	    		
	    		var itemArrays = me.items.items;
	    		Ext.Array.each(itemArrays,function(value,index,arrays){
	    			itemArrays[index].setReadOnly(true);
	    		});
	    		me.getDockedItems()[1].items.items[1].setDisabled(true);//重置按钮不可用
				me.getDockedItems()[1].items.items[2].setDisabled(true);//保存按钮不可用
				//var dockedItems = me.up('window').getPricePlanDetailGridPanel().getDockedItems();
				//dockedItems[1].items.items[0].setDisabled(false);//新增明明细按钮可用
				//dockedItems[1].items.items[2].setDisabled(false);//删除明细按钮可用
				//dockedItems[1].items.items[4].setDisabled(false);//导入明细按钮可用
	    	};
    	    
    	    //失败提示
    	    var failureFun = function(json){
    	    	if(Ext.isEmpty(json)){
    				pricing.showErrorMes(pricing.outerPrice.i18n('i18n.pricingRegion.requestTimeOut'));
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
    			this.pricePlanDetailGridPanel = Ext.create('Foss.pricing.outerPrice.OuterPriceDetailGridPanel');
    			pricing.pricePlanDetailGridPanel  = this.pricePlanDetailGridPanel;
    	}
    	return this.pricePlanDetailGridPanel;
    },	
	//构造函数
	constructor : function(config) {
		var me = this, 
		cfg = Ext.apply({}, config);
		me.fbar = [{
			text :pricing.outerPrice.i18n('i18n.pricingRegion.cancel'),//取消
			handler :function(){
				me.up('window').isUpdate = null;
				me.up().close();
			}
		},/*{
			text : pricing.outerPrice.i18n('foss.pricing.reset'),//重置
			handler :function(){
					me.getForm().reset();//表格重置
			} 
		},*/{
			text : pricing.outerPrice.i18n('foss.pricing.save'),//保存
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
 * 航偏线价费用方案明细目的地grid
 */
Ext.define('Foss.pricing.outerPrice.OuterPriceDetailGridPanel',{
	extend: 'Ext.grid.Panel',
	title : "目的地信息",
	frame: true,
	sortableColumns:false,
    enableColumnHide:false,
	selType : "rowmodel", 
	enableColumnMove:false,
	stripeRows:true, 
	border: true,
	height :440,
	pricePlanId: null,
	arrvRegionId: null,
	columns: [{
	    xtype: 'rownumberer',
		width:40,
		text : "序号"//序号
		
	},{
		text : pricing.outerPrice.i18n('i18n.pricingRegion.opra'),
		align : 'center',
		xtype : 'actioncolumn',
		items: [{
				iconCls:'deppon_icons_edit',
				tooltip: pricing.outerPrice.i18n('foss.pricing.toAmendTheProposal'), 
				width:42,
				handler: function(grid, rowIndex, colIndex){
					var me = this;
                	var record = grid.up().getStore().getAt(rowIndex);//选中数据
                	
                	var pricePlanDetaiModel = new Foss.pricing.outerPrice.OffLinePricePlanDetailDto();
                	//处理特殊字段
					pricePlanDetaiModel.set('pricePlanId',record.get('pricePlanId'));
					pricePlanDetaiModel.set('valuationId',record.get('valuationId'));
					var params = {'priceManageMentVo':{'queryPricePlanDetailBean':pricePlanDetaiModel.data}};
    				var successFun = function(json){
    					//获取明细window
    					var updateWindow =  grid.up().getModifyPriceDetailWindow();
    					//获取根据价格方案ID和计费规则ID所查询出来的计价规则以及费率信息包括重轻货
    					var arrayDate = json.priceManageMentVo.pricePlanDetailDtoList;
    					//如果数据非空才赋值给明细FormPanel作为显示数据,否则提示没有找到对应的数据
						if(!Ext.isEmpty(arrayDate)){
							var pricePlanDetailDto = arrayDate[0];
							updateWindow.pricePlanDetailDto = pricePlanDetailDto;
						} 
						updateWindow.show();
    				};
    				var failureFun = function(json){
    					if(Ext.isEmpty(json)){
    						pricing.showErrorMes(pricing.outerPrice.i18n('foss.pricing.requestTimedOut'));//请求超时
    					}else{
    						pricing.showErrorMes(json.message);
    					}
    				};
    				var url = pricing.realPath('queryBeforeModifyOuterPriceDetail.action');
    				pricing.requestJsonAjax(url,params,successFun,failureFun);
				}
		}]
	},{
		text: "目的区域",//目的区域
		width: 120,
        dataIndex: 'arrvRegionName'
	},{
		text: "航班类型",//航班
		width: 70,
        dataIndex: 'flightTypeName'
	},{
		text: "货物类型",//货物类型
		width: 70,
        dataIndex: 'goodsTypeName'
	},{
		text: "重货价格",//重货价格
    	width: 60,
        dataIndex: 'heavyPrice'
	},{
		text: "最低一票",//最低一票
		width: 65,
        dataIndex: 'minimumOneTicket'
	},{
		width: 80,
		text: "是否接货",
        dataIndex: 'centralizePickup',
        renderer:function(value){
			if(value=='Y'){
				return "是";
			}else if(value=='N'){
				return  "否";
			}else{
				return '';
			}
		}
	},{
		text: "备注",//备注
		width: 80,
        dataIndex: 'remark'
	}],
	
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
				pageSize : 10
			});
		}
		return this.pagingToolbar;
	},
    
	//弹出明细列表信息
    pricePlanDetailWindow:null,
    getPricePlanDetailWindow:function(){
    	var me = this;
    	if(Ext.isEmpty(this.pricePlanDetailWindow)){
    		this.pricePlanDetailWindow = Ext.create('Foss.pricing.outerPrice.OuterPriceDetailWindow',{
    			grid:me
    		});
	    	this.pricePlanDetailWindow.parent = this;
    	}
    	return this.pricePlanDetailWindow;
    },
    //修改明细信息window
    modifyPricePlanDetailWindow:null,
    getModifyPriceDetailWindow:function(){
    	if(Ext.isEmpty(this.modifyPricePlanDetailWindow)){
    		this.modifyPricePlanDetailWindow = Ext.create('Foss.pricing.outerPrice.ModifyOffLinePricePlanDetailWindow',{
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
			pricing.showWoringMessage(pricing.outerPrice.i18n('foss.pricing.pleaseSelectOneDeleteOperation'));//请选择一条进行删除操作！
			return;//没有则提示并返回
		}
		for(var i = 0;i<selections.length;i++){
			if(selections[i].get('active')==pricing.yes){
				pricing.showWoringMessage(pricing.outerPrice.i18n('foss.pricing.pleaseChooseToNotActivateTheDataToBeBeleted'));//请选择未激活数据进行删除！
				return;//没有则提示并返回
			}
		}
		pricing.showQuestionMes(pricing.outerPrice.i18n('foss.pricing.youWantDeletePricProgramDetails'),function(e){//是否要删除这些价格方案明细？
		if(e=='yes'){//询问是否删除，是则发送请求
				var valuationIds = new Array();//计费规则ID
				for(var i = 0 ; i<selections.length ; i++){
					valuationIds.push(selections[i].get('valuationId'));
				}
				var params = {'priceManageMentVo':{'pricePlanDetailIds':valuationIds}};
				var successFun = function(json){
					pricing.showInfoMes(json.message);
					var arrayDate = json.priceManageMentVo.pricePlanDetailDtoList;
							grid.store.loadData(arrayDate);//显示第一页	
					};
				var failureFun = function(json){
					if(Ext.isEmpty(json)){
						pricing.showErrorMes(pricing.outerPrice.i18n('foss.pricing.requestTimedOut'));//请求超时
					}else{
						pricing.showErrorMes(json.message);
					}
				};
				var url = pricing.realPath('deleteOuterPriceDetail.action');
				pricing.requestJsonAjax(url,params,successFun,failureFun);
			}
		})	
    },
	//初始化构造器
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
			me.store = Ext.create('Foss.pricing.outerPrice.OuterPriceDeatilStore',{
			autoLoad : false,
			pageSize : 10,
			listeners: {
				beforeload: function(store, operation, eOpts){
					var pricePlanId = pricing.pricePlanId;
					if(pricePlanId!=null){
						Ext.apply(operation,{
							params : {
								'priceManageMentVo.queryPricePlanDetailBean.pricePlanId' : pricePlanId//价格方案ID
							}
						});	
					}
				}
		    }
		});
		me.selModel = me.getCheckboxModel();
		me.store = Ext.create('Foss.pricing.outerPrice.OuterPriceDeatilStore',{
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
		});
		//加入tbar菜单
		me.tbar = [{
	            text: pricing.outerPrice.i18n('i18n.pricingRegion.add'),
	            handler:function(){ 
	            	me.getPricePlanDetailWindow().show();	 
	            }
	        },'-',{
	            text: pricing.outerPrice.i18n('foss.pricing.delete'),
	            handler:function(){
	            	me.deletePricePlanDetail(me); 
	            }
	        }];
	    //设置滚动条不失效
		me.listeners = {
	    	scrollershow: function(scroller) {
	    		if (scroller && scroller.scrollEl) {
	    				scroller.clearManagedListeners(); 
	    				scroller.mon(scroller.scrollEl, 'scroll', scroller.onElScroll, scroller); 
	    		}
	    	}
	    },
	    me.bbar = me.getPagingToolbar();
	    pricing.pagingBar = me.bbar;
		me.callParent([cfg]);
	}
});

/**
 * 偏线价费用方案弹出框
 */
Ext.define('Foss.pricing.outerPrice.OuterPriceAddWindow',{
		extend: 'Ext.window.Window',
		title: "新增价格方案",
		x:400,
		y:50,
		width:600,
		height:400,
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
			}
		},
	    //价格方案批次信息 （出发地批次信息录入）
		pricePlanAddFormPanel:null,
	    getPricePlanAddFormPanel : function(){
	    	var me = this;
	    	if(Ext.isEmpty(this.pricePlanAddFormPanel)){
	    		this.pricePlanAddFormPanel = Ext.create('Foss.pricing.outerPrice.OuterPriceAddFormPanel');
	    	} 
	    	return this.pricePlanAddFormPanel;
	    },
	    
	    //构造器
		constructor: function(config){
			var me = this,
			cfg = Ext.apply({}, config);
			me.isUpdate,
			me.items = [me.getPricePlanAddFormPanel()],
			
			me.callParent([cfg]);
		}	
});


/**
 * 航偏线价费用方案弹出修改框
 */
Ext.define('Foss.pricing.outerPrice.OuterPriceUpdateWindow',{
		extend: 'Ext.window.Window',
		title: "修改价格方案",
		width:750,
		height:390,
		modal:true,
		isUpdate:null,
		parent:null,
		pricePlanEntity:null,
 		pricePlanDetailDtoList:null,
		closeAction:'hide',
	    //监听器
	    listeners:{
			beforehide:function(me){
				me.getOuterPriceUpdateFormPanel().getForm().reset();
				me.isUpdate = null;
			},
			beforeshow:function(me){
				me.getOuterPriceUpdateFormPanel().getForm().loadRecord(new Foss.pricing.outerPrice.OuterPriceModel(me.pricePlanEntity));
				
				me.getOuterPriceUpdateFormPanel().getForm().findField('partialLineCode').setCombValue(me.pricePlanEntity.partialLineName, me.pricePlanEntity.partialLineCode);
				me.getOuterPriceUpdateFormPanel().getForm().findField('outFieldCode').setCombValue(me.pricePlanEntity.outFieldName, me.pricePlanEntity.outFieldCode);
				
				var productCode = me.pricePlanEntity.productCode;
				var productName = '';
				if (productCode == 'TRANS_VEHICLE') {//汽运
					productName = '汽运'; 
				} else if (productCode == 'TRANS_AIRCRAFT') {//空运
					productName = '空运';
				}
				me.getOuterPriceUpdateFormPanel().getForm().findField('transportType').setValue(productName); 
				me.getOuterPriceUpdateFormPanel().getForm().findField('transportType').valueCode = productCode;
				me.getOuterPriceUpdateFormPanel().getForm().findField('transportType').valueName = productName;
				
				/*me.getPricePlanDetailForm().getForm().findField('partialLineCode').arrvRegionId = me.pricePlanDetailDto.arrvRegionId;
				me.getPricePlanDetailForm().getForm().findField('partialLineCode').arrvRegionName = me.pricePlanDetailDto.arrvRegionName;

				me.getPricePlanDetailForm().getForm().findField('partialLineCode').setValue(me.pricePlanDetailDto.flightTypeName); 
				me.getPricePlanDetailForm().getForm().findField('transportType').valueCode = me.pricePlanDetailDto.productCode;
				me.getPricePlanDetailForm().getForm().findField('transportType').valueName = me.pricePlanDetailDto.flightTypeName;*/
				//me.getPricePlanDetailGridPanel().store.removeAll(true);
			}
		},
	    //价格方案批次信息 （出发地批次信息录入）
		outerPriceUpdateFormPanel:null,
	    getOuterPriceUpdateFormPanel : function(){
	    	var me = this;
	    	if(Ext.isEmpty(me.outerPriceUpdateFormPanel)){
		    		me.outerPriceUpdateFormPanel = Ext.create('Foss.pricing.outerPrice.OuterPriceUpdateFormPanel');
		    		//设置器父元素
 	    	} 
	    	return this.outerPriceUpdateFormPanel;
	    },
	    //构造器
		constructor: function(config){
			var me = this,
			cfg = Ext.apply({}, config);
			me.isUpdate,
			me.items = [me.getOuterPriceUpdateFormPanel()],
			
			me.callParent([cfg]);
		}	
});


/**
 * 偏线价费用方案中止方案弹出框
 */
Ext.define('Foss.pricing.outerPrice.OuterPriceStopEndTimeWindow',{
		extend: 'Ext.window.Window',
		title: "中止价格方案",
		width:380,
		height:120,
		parent:null,
		pricePlanEntity:null,
		pricePlanId:null,
		closeAction: 'hide',
	    //中止
		pricePlanStopFormPanel:null,
		getPricePlanStopFormPanel : function(){
	    	if(Ext.isEmpty(this.pricePlanAddFormPanel)){
	    		/*this.pricePlanStopFormPanel = Ext.create('Foss.pricing.outerPrice.OuterPriceStopFormPanel',{
	    			'pricePlanId':pricePlanId
	    		});*/
	    		this.pricePlanStopFormPanel = Ext.create('Foss.pricing.outerPrice.OuterPriceStopFormPanel');
	    	}
	    	return this.pricePlanStopFormPanel;
	    },
	    //构造器
		constructor: function(config){
			var me = this,
			cfg = Ext.apply({}, config);
			me.items = [me.getPricePlanStopFormPanel()],
			
			me.callParent([cfg]);
		}
	    /*
	   	initComponent : function() {
			var me = this;
			me.items = [me.getPricePlanStopFormPanel()];//设置window的元素
			me.callParent();
		}*/
});

/**
 * 中止功能window
 */
Ext.define('Foss.pricing.outerPrice.OuterPriceStopFormPanel',{
	extend : 'Ext.form.Panel',
	layout:'column',
	width:410,
	height:100,
	pricePlanId:null,
	//closeAction: 'hide',
	//中止方案
	stopPricePlan:function(){
		var me = this;
    	//获取表单
    	var form = me.getForm();
    	if(form.isValid()){
    		var pricePlanEntity = me.up('window').pricePlanEntity;
			var pricePlanModel = new Foss.pricing.outerPrice.OuterPriceModel(pricePlanEntity);
			form.updateRecord(pricePlanModel);
    		var time = form.findField('endTime').getValue();
    		
            //var endTime = new Date(time);//获取选择的时期对象  
            //endTime = Date.parse(endTime.format('yyyy-MM-dd'));
            
    		var nowDate = new Date(); 
    		if (Date.parse(time) < Date.parse(nowDate.format('yyyy-MM-dd'))) {
    			pricing.showErrorMes("中止日期不能小于当前日期！");
    			return false;
    		}
    		var pricePlanId = pricing.pricePlanId;
    		var params = {'outerPriceVo':{'outerPriceId':pricePlanId, 'yesOrNo': 'N', 'effectiveTime': time}};
    		var url = pricing.realPath('activeOuterPrice.action');
    		
    		//成功提示
    		var successFun = function(json){
    			pricing.showInfoMes(json.message);
    			me.up('window').close();
				var waitingProcessGrid = Ext.getCmp('T_pricing-outerPrice_content').getOffLinePricePlanGridPanel();
				waitingProcessGrid.store.load();
    	    };
    	    
    	    //失败提示
    	    var failureFun = function(json){
    	    	if(Ext.isEmpty(json)){
    				pricing.showErrorMes(pricing.outerPrice.i18n('i18n.pricingRegion.requestTimeOut'));
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
		me.items = [{
			/*xtype:'datefield',
			fieldLabel:pricing.outerPrice.i18n('foss.pricing.deadline'),//截止日期
			format:'20y-m-d',
			name:'endTime',
			allowBlank:false*/
			name : 'endTime',
			fieldLabel:pricing.outerPrice.i18n('foss.pricing.deadline'),//截止日期
			xtype : 'datetimefield_date97',
			editable:false,
			time : true,
			id : 'Foss_outerPrice_stopEndTime_grid_ID',
			allowBlank:false,
			dateConfig: {
				el : 'Foss_outerPrice_stopEndTime_grid_ID-inputEl'
			},
			columnWidth:.7
			},{
				xtype : 'container',
				margin : '0 0 2 0',
				items : [{
					xtype : 'button', 
					width:70,
					text : "中止",
					handler : function() {
						var pricePlanId = me.pricePlanId;
						me.stopPricePlan(pricePlanId);
					}
				}]
			}];//设置window的元素
		me.callParent();
	}
});



/**
 * 立即中止价格方案 Window
 */
Ext.define('Foss.pricing.outerPrice.OuterPriceImmediatelyStopEndTimeWindow',{
		extend: 'Ext.window.Window',
		title: pricing.outerPrice.i18n('foss.pricing.immediatelySupendPricePriceScheme'),//"立即中止方案",
		width:380,
		height:152,
		pricePlanEntity:null,
		closeAction: 'hide' ,
		pricePlanStopFormPanel:null,
		getOffLinePricePlanStopFormPanel : function(pricePlanEntity){
	    	if(Ext.isEmpty(this.pricePlanAddFormPanel)){
	    		this.pricePlanStopFormPanel = Ext.create('Foss.pricing.outerPrice.OuterPriceImmediatelyStopFormPanel',{
	    			pricePlanEntity:pricePlanEntity
	    		});
	    	}
	    	return this.pricePlanStopFormPanel;
	    },
	   	constructor : function(config) {
			var me = this, cfg = Ext.apply({}, config);
			me.pricePlanEntity = config.pricePlanEntity;
			me.items = [me.getOffLinePricePlanStopFormPanel(me.pricePlanEntity)];
			me.callParent(cfg);
		}
});

/**
 * 立即中止功能FormPanel
 */
Ext.define('Foss.pricing.outerPrice.OuterPriceImmediatelyStopFormPanel',{
	extend : 'Ext.form.Panel',
	layout:'column',
	pricePlanEntity:null,
	stopPricePlan:function(pricePlanId){
		var me = this;
    	var form = me.getForm();
    	if(form.isValid()){
    		var pricePlanModel = new Foss.pricing.outerPrice.OuterPriceModel();
			form.updateRecord(pricePlanModel);

    		var endTime = form.findField('endTime').getValue();
			var result = Ext.Date.parse(endTime,'Y-m-d H:i:s') - Ext.Date.parse(Ext.Date.format(new Date(),'Y-m-d H:i:s'),'Y-m-d H:i:s');
			if(result<0) {
    			pricing.showErrorMes("中止日期不能小于当前日期！");
    			return false;
			}
			
    		/*var time = form.findField('endTime').getValue();
    		var endTime = Date.parse(time.format('yyyy-MM-dd'));
    		var nowDate = new Date(); 
    		if (endTime < Date.parse(nowDate.format('yyyy-MM-dd'))) {
    			pricing.showErrorMes("中止日期不能小于当前日期！");
    			return false;
    		}*/
			
    		var params = {'outerPriceVo':{'outerPriceId':pricePlanId, 'yesOrNo': 'N', 'effectiveTime': endTime}};
    		var url = pricing.realPath('activeOuterPrice.action');
    		
    		var successFun = function(json){
    			pricing.showInfoMes(json.message);
    			me.up('window').hide();
    			var waitingProcessGrid = Ext.getCmp('T_pricing-outerPrice_content').getOffLinePricePlanGridPanel();
				waitingProcessGrid.store.load();	
    	    };
    	    var failureFun = function(json){
    	    	if(Ext.isEmpty(json)){
    				pricing.showErrorMes(pricing.outerPrice.i18n('i18n.pricingRegion.requestTimeOut'));
    			}else{
    				pricing.showErrorMes(json.message);
    			}
    	    };
    		pricing.requestJsonAjax(url,params,successFun,failureFun);//发送AJAX请求
    	}
	},
	constructor: function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.pricePlanEntity = config.pricePlanEntity;
		var showbeginTime = Ext.Date.format(new Date(me.pricePlanEntity.beginTime), 'Y-m-d');
		var showendTime = 	Ext.Date.format(new Date(me.pricePlanEntity.endTime), 'Y-m-d');
		me.items = [{
				width:280,
				xtype: 'displayfield',
				columnWidth:.9,
				value:pricing.outerPrice.i18n('foss.pricing.showleftTimeInfo')
					  +showbeginTime+ pricing.outerPrice.i18n('foss.pricing.showmiddleTimeInfo')
					  +showendTime  + pricing.outerPrice.i18n('foss.pricing.showstopRightEndTimeInfo')
				//'<p style="color:red">原方案生效日期为【2013-02-31】截止日期为【2013-09-11】,您是否立即中止该方案?</p>'
			},{ 
				fieldLabel :pricing.outerPrice.i18n('foss.pricing.suspendTime') ,//'中止日期',
				name : 'endTime',
				xtype : 'datetimefield_date97',
				editable:false,
				time : true,
				id : 'Foss_outerPrice_stopEndTime_ID',
				allowBlank:false,
				dateConfig: {
					el : 'Foss_outerPrice_stopEndTime_ID-inputEl'
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
				text : pricing.outerPrice.i18n('i18n.pricingRegion.determine'),//"确认",
				handler : function() {
					var pricePlanId = pricing.outerPrice.immediatelyStopId;
					me.stopPricePlan(pricePlanId);
				}
			},{
				xtype : 'button', 
				width:70,
				columnWidth:.15,
				text : pricing.outerPrice.i18n('i18n.pricingRegion.cancel'),//"取消",
				handler : function() {
					me.up('window').hide();
				}
			}];
	        me.callParent(cfg);
    }
});


/**
 * 立即激活Window
 */
Ext.define('Foss.pricing.outerPrice.OuterPriceImmediatelyActiveTimeWindow',{
		extend: 'Ext.window.Window',
		title: pricing.outerPrice.i18n('foss.pricing.immediatelyActiveationPriceScheme'),//"立即激活方案",
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
				//var beginTime = Ext.Date.format(new Date(me.pricePlanEntity.beginTime), 'Y-m-d H:i:s');
				var beginTime = Ext.Date.format(me.pricePlanEntity.beginTime, 'Y-m-d H:i:s')
				var endTime = Ext.Date.format(new Date(me.pricePlanEntity.endTime), 'Y-m-d H:i:s');
				var value = pricing.outerPrice.i18n('foss.pricing.showleftTimeInfo')
					  + beginTime + pricing.outerPrice.i18n('foss.pricing.showmiddleTimeInfo')
					  + endTime + pricing.outerPrice.i18n('foss.pricing.showrightEndTimeInfo');
				
				me.pricePlanEntity.showTime = value;
				me.getOffLinePricePlanImmediatelyActiveFormPanel().getForm().loadRecord(new Foss.pricing.outerPrice.OuterPriceModel(me.pricePlanEntity));
				me.getOffLinePricePlanImmediatelyActiveFormPanel().getForm().findField('beginTime').setValue(beginTime);
			}
		},
		OffLinePricePlanImmediatelyActiveFormPanel:null,
		getOffLinePricePlanImmediatelyActiveFormPanel : function(pricePlanEntity){
	    	if(Ext.isEmpty(this.OffLinePricePlanImmediatelyActiveFormPanel)){
	    		this.OffLinePricePlanImmediatelyActiveFormPanel = Ext.create('Foss.pricing.outerPrice.OuterPriceImmediatelyActiveFormPanel');
	    	}
	    	this.OffLinePricePlanImmediatelyActiveFormPanel.pricePlanEntity = pricePlanEntity;
	    	return this.OffLinePricePlanImmediatelyActiveFormPanel;
	    },
	   	constructor : function(config) {
			var me = this, cfg = Ext.apply({}, config);
			//me.pricePlanEntity = config.pricePlanEntity;
			me.items = [me.getOffLinePricePlanImmediatelyActiveFormPanel(me.pricePlanEntity)];
			me.callParent(cfg);
		}
});


/**
 * 立即激活Form
 */
Ext.define('Foss.pricing.outerPrice.OuterPriceImmediatelyActiveFormPanel',{
	extend : 'Ext.form.Panel',
	layout:'column',
	pricePlanEntity:null,
	activetionPricePlan:function(pricePlanId){
		var me = this;
    	var form = me.getForm();
    	if(form.isValid()){
    		/*var pricePlanModel = new Foss.pricing.outerPrice.OuterPriceModel();
			form.updateRecord(pricePlanModel);
			pricePlanModel.set('beginTime',Ext.Date.parse(form.findField('beginTime').getValue(), 'Y-m-d H:i:s'));
    		pricePlanModel.set('id',pricePlanId);*/


        	var pricePlanModel = new Foss.pricing.outerPrice.OuterPriceModel();
			form.updateRecord(pricePlanModel);
			pricePlanModel.set('beginTime',Ext.Date.parse(form.findField('beginTime').getValue(), 'Y-m-d H:i:s'));
    		pricePlanModel.set('id',pricePlanId);

    		var time = Ext.Date.parse(form.findField('beginTime').getValue(), 'Y-m-d H:i:s');
    		var nowDate = new Date(); 
    		if (time < nowDate) {
    			pricing.showErrorMes("生效日期不能小于系统当前时间！");
    			return false;
    		}
    		var params = {'outerPriceVo':{'outerPriceId':pricePlanId, 'yesOrNo': 'Y', 'effectiveTime': time}};
    		var url = pricing.realPath('activeOuterPrice.action');
    		var successFun = function(json){
    			pricing.showInfoMes(json.message);
    			me.up('window').hide();
				var waitingProcessGrid = Ext.getCmp('T_pricing-outerPrice_content').getOffLinePricePlanGridPanel();
				waitingProcessGrid.store.load();			
    	    };
    	    var failureFun = function(json){
    	    	if(Ext.isEmpty(json)){
    				pricing.showErrorMes(pricing.outerPrice.i18n('i18n.pricingRegion.requestTimeOut'));
    			} else {
    				pricing.showErrorMes(json.message);
    			}
    	    };
    		pricing.requestJsonAjax(url,params,successFun,failureFun);//发送AJAX请求
    	}
	},
	constructor: function(config) {
		var me = this, cfg = Ext.apply({}, config);
		//me.pricePlanEntity  = config.pricePlanEntity
		//var showbeginTime = Ext.Date.format(new Date(me.pricePlanEntity.beginTime), 'Y-m-d H:i:s');
		//var showendTime = 	Ext.Date.format(new Date(me.pricePlanEntity.endTime), 'Y-m-d H:i:s');
		me.items = [{
				width:280,
				name: 'showTime',
				xtype: 'displayfield',
				columnWidth:.9
				/*value:pricing.outerPrice.i18n('foss.pricing.showleftTimeInfo')
					  +showbeginTime+pricing.outerPrice.i18n('foss.pricing.showmiddleTimeInfo')
					  +showendTime+pricing.outerPrice.i18n('foss.pricing.showrightEndTimeInfo')*/
				//'<p style="color:red">原方案生效日期为【'+showbeginTime+'】截止日期为【'+showendTime+'】,您是否立即生效该方案?</p>'
			},{
				fieldLabel:pricing.outerPrice.i18n('foss.pricing.availabilityDate'),//'生效日期',
				name : 'beginTime',
				xtype : 'datetimefield_date97',
				editable:false,
				time : true,
				id : 'Foss_outerPrice_activetionEndTime_ID',
				allowBlank:false,
				//vtype: Ext.Date.format(pricing.getDateBeforeAfter(new Date(),0), 'Y-m-d H:i:s'),
				dateConfig: {
					el : 'Foss_outerPrice_activetionEndTime_ID-inputEl'
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
				text : pricing.outerPrice.i18n('i18n.pricingRegion.determine'),//,"确认",
				handler : function() {
					var pricePlanId = pricing.outerPrice.immediatelyActiveId;
					me.activetionPricePlan(pricePlanId);
				}
			},{
				xtype : 'button', 
				width:70,
				columnWidth:.15,
				text : pricing.outerPrice.i18n('i18n.pricingRegion.cancel'),//"取消",
				handler : function() {
					me.up('window').hide();
				}
			}];
        this.callParent(cfg);
    }
});









/**
 * 上传附件弹出框
 */
Ext.define('Foss.pricing.outerPrice.UploadOuterPriceform',{
	extend:'Ext.window.Window',
	title:pricing.outerPrice.i18n('foss.pricing.importPriceScheme'),
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
	parent:null,//（Foss.pricing.pricePlan.pricePlanformGrid）
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
				fieldLabel:pricing.outerPrice.i18n('foss.pricing.pleaseSelectAttachments'),
				labelWidth:100,
				buttonText:pricing.outerPrice.i18n('foss.pricing.browse'),
				flex:3
			}]
		}];
		me.fbar = me.getFbar();
		me.callParent([cfg]);
	},
	getFbar:function(){
		var me = this;
		return [{
			text:pricing.outerPrice.i18n('foss.pricing.import'),
			xtype:'button',
			scope:me,
			handler:me.uploadFile
		},{
			text:pricing.outerPrice.i18n('i18n.pricingRegion.cancel'),
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
			if (json.success) {
				pricing.showInfoMes(pricing.outerPrice.i18n('foss.pricing.allDataImportSuccess'));//全部数据导入成功！
				me.close();
			}
			var waitingProcessGrid = Ext.getCmp('T_pricing-outerPrice_content').getOffLinePricePlanGridPanel();
			waitingProcessGrid.store.load();
			/*if(Ext.isEmpty(json.priceManageMentVo.numList)){
				pricing.showInfoMes(pricing.outerPrice.i18n('foss.pricing.allDataImportSuccess'));//全部数据导入成功！
				me.close();
			}else{
				var message = pricing.outerPrice.i18n('foss.pricing.first');//第
				for(var i = 0;i<json.platformVo.numList;i++){
					message = message+json.platformVo.numList[i]+','
				}
				message = message+pricing.outerPrice.i18n('foss.pricing.lineImportSuccess');
				pricing.showWoringMessage(message);
			}*/
		};
		var failureFn = function(json){
			if(Ext.isEmpty(json)){
				pricing.showErrorMes(pricing.outerPrice.i18n('i18n.pricingRegion.requestTimeOut'));//pricing.outerPrice.i18n('i18n.pricingRegion.requestTimeOut')
			}else{
				pricing.showErrorMes(json.message);
			}
		};
		var form = me.down('form').getForm();
		var url = pricing.realPath('importOuterPrice.action');
		form.submit({
            url: url,
            waitMsg: pricing.outerPrice.i18n('foss.pricing.uploadYourAttachment'),
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
	var queryform = Ext.create('Foss.pricing.outerPrice.OuterPriceFormPanel');//查询
	var gridPanel =	Ext.create('Foss.pricing.outerPrice.OuterPriceGridPanel');
	pricing.outerPrice.queryform = queryform;
	pricing.outerPrice.gridPanel = gridPanel;
	Ext.getCmp('T_pricing-outerPrice').add(Ext.create('Ext.panel.Panel', {
	 	id:'T_pricing-outerPrice_content',
		cls:"panelContentNToolbar",
		bodyCls:'panelContent-body',
		
		//获得查询FORM
		getQueryOffLinePricePlanForm : function() {
			return queryform;
		},
		//获得查询结果GRID
		getOffLinePricePlanGridPanel : function() {
			if(Ext.isEmpty(this.gridPanel)){
				this.gridPanel = Ext.create('Foss.pricing.outerPrice.OuterPriceGridPanel');//查询结果GRID
			}
			return gridPanel;
		},
		
		items : [queryform,gridPanel]
	}));
});

