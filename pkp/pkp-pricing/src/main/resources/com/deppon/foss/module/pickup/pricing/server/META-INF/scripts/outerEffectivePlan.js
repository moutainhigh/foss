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
pricing.outerEffectivePlan.DAY = 'DAY'//单位是天
pricing.outerEffectivePlan.HOURS = 'HOURS'//单位是小时
/**
 * 
 * @type  偏线价费用
 */
pricing.outerEffectivePlanId = null;
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


//产品类型的store
Ext.define('Foss.Pricing.EffectivePlan.Store.ProductStore',{
	extend: 'Ext.data.Store',
	fields: [
		{name: 'key',  type: 'string'},
		{name: 'value',  type: 'string'}
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
	}
});
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
			Ext.Msg.alert(pricing.outerEffectivePlan.i18n('foss.pricing.promptMessage'),result.message);
		}
	});
};


//创建一个查询货量枚举store
Ext.define('Foss.pricing.outerEffectivePlan.Store.ToDoTypeStore',{
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
Ext.define('Foss.pricing.outerEffectivePlan.outerEffectivePlanModel', {
    extend: 'Ext.data.Model',
    fields : [
    {
        name : 'outerEffectivePlanId',//ID
        type : 'string'
    },{
      name : 'name',//方案名称
        type : 'string'
    },{
        name : 'versionNo',
        type : 'string'
    },{
        name : 'active',
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
        name : 'maxTime',//承诺最长时间
        type : 'int'
    },{
        name : 'maxTimeUnit',//承诺最长时间单位
        type : 'string'
    },{
        name : 'minTime',//承诺最短时间
        type : 'int'
    },{
        name : 'minTimeUnit',//承诺最短时间单位
        type : 'string'
    },{
        name : 'arriveOuterBranchTime',//到达代理网点承诺时点
        type : 'string'
    },{
        name : 'addDay',//派送承诺需加天数
        type : 'int'
    },{
        name : 'deliveryTime',//派送承诺时间
        type : 'string'
    }]
});

/**
 *偏线价费用方案批次store
 */
Ext.define('Foss.pricing.outerEffectivePlan.OuterEffectivePlanStore',{
	extend: 'Ext.data.Store',
	model: 'Foss.pricing.outerEffectivePlan.outerEffectivePlanModel',
	pageSize:20,
	proxy: {
		type : 'ajax',
		actionMethods:'POST',
		url: pricing.realPath('queryOuterEffectivePlanVoBatchInfo.action'),
		reader : {
			type : 'json',
			root : 'outerEffectiveVo.outerEffectivePlanDtoList',
			totalProperty : 'totalCount',
			successProperty: 'success'
		}
	},
	listeners: {
		beforeload: function(store, operation, eOpts){
			var n = pricing.outerEffectivePlan.queryform.getValues();
			var outFieldCode = pricing.outerEffectivePlan.queryform.getForm().findField('outFieldCode').getValue();
			if(outFieldCode != null)
			{
			   outFieldCode = pricing.outerEffectivePlan.queryform.getForm().findField('outFieldCode').displayTplData[0].orgCode;
			}
			Ext.apply(operation,{
				params : {
					'outerEffectiveVo.outerEffectivePlanConditionDto.partialLineCode' : 	  n.partialLineCode,
					'outerEffectiveVo.outerEffectivePlanConditionDto.outFieldCode'	: outFieldCode,
					'outerEffectiveVo.outerEffectivePlanConditionDto.productCode'	: n.transportType,
					'outerEffectiveVo.outerEffectivePlanConditionDto.active'	: n.active
				}
			});			
		}
	}
});



/**
 * 偏线价格方案批次查询form表单
 */
Ext.define('Foss.pricing.outerEffectivePlan.OuterPriceFormPanel', {
	extend : 'Ext.form.Panel',
	  title: pricing.outerEffectivePlan.i18n('i18n.pricingRegion.searchCondition'),
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
	    fieldLabel:pricing.outerEffectivePlan.i18n('foss.pricing.destinationLine'),//目的站
	    columnWidth: 0.25
	  },{
	    xtype : 'commontransfercenterselector',
	    name: 'outFieldCode',
		active:'Y',  
	    fieldLabel: pricing.outerEffectivePlan.i18n('foss.pricing.outFieldName'),//外发外场(commontransfercenterselector):,
	    columnWidth: 0.25
	  },{
			name: 'productCode',
			fieldLabel: pricing.outerEffectivePlan.i18n('foss.pricing.outerEffectivePlan.productName'),//运输类型 
			allowBlank:false,
			columnWidth: 0.25,
			xtype: 'combobox',
			value:'TRANS_VEHICLE',
            valueField: 'code',
            displayField: 'name',
			queryMode:'local',
			triggerAction:'all',
			store:Ext.create('Foss.pricing.outerEffectivePlan.Store.ToDoTypeStore',
		    {
		      data:{
		    	  'items':
	              [
	                 {'code':'','name':pricing.outerEffectivePlan.i18n('i18n.pricingRegion.all')},
	                {'code':'TRANS_VEHICLE','name':pricing.outerEffectivePlan.i18n('foss.pricing.outerEffectivePlan.transVehicle')},
	                {'code':'AIR_FREIGHT','name':pricing.outerEffectivePlan.i18n('foss.pricing.outerEffectivePlan.airFreight')}//
	              ]
		      }
		    })
	},{
	    xtype:'combo',
	    displayField:'name',
	    valueField:'code',
	    queryMode:'local',
	    triggerAction:'all',
	    editable:false,
	    name: 'active',
	    fieldLabel: pricing.outerEffectivePlan.i18n('foss.pricing.dataStatus'),//处理状态,
	    columnWidth: 0.25,
	    value:'',
	    store:Ext.create('Foss.pricing.outerEffectivePlan.Store.ToDoTypeStore',
	    {
	      data:{
	           'items':[
	          {'code':'','name':pricing.outerEffectivePlan.i18n('i18n.pricingRegion.all')},
	          {'code':'N','name':pricing.outerEffectivePlan.i18n('i18n.pricingRegion.unActive')},
	          {'code':'Y','name':pricing.outerEffectivePlan.i18n('i18n.pricingRegion.active')}	          
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
				columnWidth:.08,
				text : pricing.outerEffectivePlan.i18n('foss.pricing.reset'),//重置
				handler : function() {
					pricing.outerEffectivePlan.queryform.getForm().reset();
				}
			},{
				xtype: 'container',
				border : false,
				columnWidth:.84,
				html: '&nbsp;'
			},{
		        xtype : 'button',
		        cls:'yellow_button',
		        text : pricing.outerEffectivePlan.i18n('i18n.pricingRegion.search'),//查询,
		        columnWidth:.08,
		        disabled: !pricing.outerEffectivePlan.isPermission('outerEffectivePlan/outerEffectivePlanQueryButton'),
		        hidden: !pricing.outerEffectivePlan.isPermission('outerEffectivePlan/outerEffectivePlanQueryButton'),
		        handler : function() {
		        	var grid = Ext.getCmp('T_pricing-outerEffectivePlan_content').getOuterEffectivePlanGridPanel();
		        	grid.getPagingToolbar().moveFirst();
		      }
		    }]
	  }]
});


pricing.outerEffectivePlan.immediatelyActiveId = null;
pricing.outerEffectivePlan.immediatelyStopId = null;


/**
 * 偏线价费用方案批次列表gird
 */
Ext.define('Foss.pricing.outerEffectivePlan.OuterPriceGridPanel',{
	extend: 'Ext.grid.Panel',
	title : pricing.outerEffectivePlan.i18n('i18n.pricingRegion.searchResults'),//查询结果
	emptyText: pricing.outerEffectivePlan.i18n('foss.pricing.theQueryResultIsEmpty'),//查询结果为空
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
			this.addPricePlanWindow = Ext.create('Foss.pricing.outerEffectivePlan.OuterPriceAddWindow');
			this.addPricePlanWindow.parent = this;
		}
		return this.addPricePlanWindow;
	},
	
	updatePricePlanWindow : null,
	//获取修改价格方案窗口
	getUpdatePricePlanWindow : function(){
		if(Ext.isEmpty(this.updateStandardWindow)){
			this.updatePricePlanWindow = Ext.create('Foss.pricing.outerEffectivePlan.OuterPriceUpdateWindow');
			//设置器父元素
			this.updatePricePlanWindow.parent = this;
		}
		return this.updatePricePlanWindow;
	},
	
	
	//中止方案弹出日期选择
	stopPricePlanWindow:null,
	getStopPricePlanWindow: function(){
		if(Ext.isEmpty(this.stopPricePlanWindow)){
			/*this.stopPricePlanWindow = Ext.create('Foss.pricing.outerEffectivePlan.OuterPriceStopEndTimeWindow',{
				'pricePlanId':pricePlanId//,
				//'endTime' : endTime
			});*/
			this.stopPricePlanWindow = Ext.create('Foss.pricing.outerEffectivePlan.OuterPriceStopEndTimeWindow');
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
	getimmediatelyActiveOffLinePricePlanWindow: function(outerEffectivePlanEntity){
		if(Ext.isEmpty(this.immediatelyActivePricePlanWindow)){
			this.immediatelyActivePricePlanWindow = Ext.create('Foss.pricing.outerEffectivePlan.OuterPriceImmediatelyActiveTimeWindow');
			this.immediatelyActivePricePlanWindow.parent = this;
		}
		this.immediatelyActivePricePlanWindow.outerEffectivePlanEntity = outerEffectivePlanEntity;
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
	getImmediatelyStopOffLinePricePlanWindow: function(outerEffectivePlanEntity){
		if(Ext.isEmpty(this.immediatelyStopPricePlanWindow)){
			this.immediatelyStopPricePlanWindow = Ext.create('Foss.pricing.outerEffectivePlan.OuterPriceImmediatelyStopEndTimeWindow',{
				outerEffectivePlanEntity:outerEffectivePlanEntity
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
	 		var outerEffectivePlanEntity = selections[0].data;
	 		pricing.outerEffectivePlan.immediatelyStopId = outerEffectivePlanEntity.outerEffectivePlanId;
	 		me.getImmediatelyStopOffLinePricePlanWindow(outerEffectivePlanEntity).show();
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
	 		var outerEffectivePlanEntity = selections[0].data;
	 		pricing.outerEffectivePlan.immediatelyActiveId = outerEffectivePlanEntity.outerEffectivePlanId;
	 		me.getimmediatelyActiveOffLinePricePlanWindow(outerEffectivePlanEntity).show();
	 	}
	},
	
	//返回批次store
	pricePlanStore:null,
	getPricePlanStore: function(){
		if(Ext.isEmpty(this.pricePlanStore)){
			this.pricePlanStore = Ext.create('Foss.pricing.outerEffectivePlan.OuterEffectivePlanStore');
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
	//删除价格方案
	deletePricePlan:function(){
	 	var me = this;
	 	var selections = me.getSelectionModel().getSelection();
	 	if(selections.length < 1){
	 		pricing.showWoringMessage(pricing.outerEffectivePlan.i18n('foss.pricing.pleaseSelectOneDeleteOperation'));
			return;
	 	}
	 	//过滤草稿状态数据进行删除
	 	for(var i = 0;i<selections.length;i++){
			if(selections[i].get('active')==pricing.yes){
				pricing.showWoringMessage(pricing.outerEffectivePlan.i18n('foss.pricing.pleaseChooseToNotActivateTheDataToBeBeleted'));
				return;
			}
		}
		//是否要删除这些汽运价格方案？
		pricing.showQuestionMes(pricing.outerEffectivePlan.i18n('foss.pricing.theseTheAirPriceProgramYouWantToRemove'),function(e){
			if(e=='yes'){
				//汽运价格方案
				var idList = new Array();
				for(var i = 0 ; i<selections.length ; i++){
					idList.push(selections[i].get('outerEffectivePlanId'));
				}
				var params = {'outerEffectiveVo':{'outerEffectivePlanIds':idList}};
				var successFun = function(json){
					pricing.showInfoMes(json.message);
					me.getPagingToolbar().moveFirst();
				};
				var failureFun = function(json){
					if(Ext.isEmpty(json)){
						pricing.showErrorMes(pricing.outerEffectivePlan.i18n('foss.pricing.requestTimedOut'));//请求超时
					}else{
						pricing.showErrorMes(json.message);
					}
				};
				var url = pricing.realPath('deleteOuterEffectivePlan.action');
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
			pricing.showErrorMes(pricing.outerEffectivePlan.i18n('foss.pricing.pleaseChooseThePriceYouWantToActivateTheProgram'));//请选择要激活的价格方案！
			return;
		}

		var outerPricePlans = new Array();
		//过滤草稿状态数据进行删除
	 	for(var i = 0;i<selections.length;i++){
			if(selections[i].get('active')==pricing.yes){
				pricing.showWoringMessage(pricing.outerEffectivePlan.i18n('foss.pricing.pleaseSelectNotActivateProgramDataForActivation'));
				return;
			}
			pricePlans.push(selections[i].get('outerEffectivePlanId'));
			outerPricePlans.push(selections[i].data);
		}
		
		if(pricePlans.length<1){
			pricing.showErrorMes(pricing.outerEffectivePlan.i18n('foss.pricing.pleaseSelectAtLeastOneInactivePriceProgram'));//请至少选择一条未激活的价格方案！
			return;
		}
		
		//是否要激活这些偏线价格方案？
		pricing.showQuestionMes(pricing.outerEffectivePlan.i18n('foss.pricing.doYouWantActivateTheseOuterPriceScheme'),function(e){
			if(e=='yes'){
				var params = {'outerEffectiveVo':{'outerEffectivePlanIds':pricePlans, 'yesOrNo': 'Y', outerEffectivePlanDtoList: outerPricePlans}};
				var successFun = function(json){
					pricing.showInfoMes(json.message);
					me.getPagingToolbar().moveFirst();
				};
				var failureFun = function(json){
					if(Ext.isEmpty(json)){
						pricing.showErrorMes(pricing.outerEffectivePlan.i18n('i18n.pricingRegion.requestTimeOut'));//请求超时
					}else{
						pricing.showErrorMes(json.message);
					}
				};
				var url = pricing.realPath('updateOuterEffectivePlanActiveById.action');
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
			this.uploadPriceform = Ext.create('Foss.pricing.outerEffectivePlan.UploadOuterPriceform');	
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
			this.queryPricePlanForm  = Ext.create('Foss.pricing.outerEffectivePlan.OuterPriceFormPanel')
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
			text : pricing.outerEffectivePlan.i18n('i18n.pricingRegion.num')//序号  
		},{
			text : pricing.outerEffectivePlan.i18n('i18n.pricingRegion.opra'),//操作
			align : 'center',
			xtype : 'actioncolumn',
			items: [{
				iconCls:'deppon_icons_edit',
				tooltip: pricing.outerEffectivePlan.i18n('foss.pricing.toAmendTheProposal'), 
				disabled: !pricing.outerEffectivePlan.isPermission('outerEffectivePlan/outerEffectivePlanUpdateButton'),
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
                	var params = {'outerEffectiveVo':{'outerEffectivePlanId':record.get('outerEffectivePlanId')}};
    				var successFun = function(json){
						updateWindow.outerEffectivePlanEntity = json.outerEffectiveVo.outerEffectivePlanDto;
						pricing.outerEffectivePlanId = json.outerEffectiveVo.outerEffectivePlanDto.outerEffectivePlanId;
    					updateWindow.show();
    					//pricing.pagingBar.moveFirst();
    				};
    				var failureFun = function(json){
    					if(Ext.isEmpty(json)){
    						pricing.showErrorMes(pricing.outerEffectivePlan.i18n('foss.pricing.requestTimedOut'));//请求超时
    					}else{
    						pricing.showErrorMes(json.message);
    					}
    				};
    				var url = pricing.realPath('selectOuterEffectivePlanById.action');
    				pricing.requestJsonAjax(url,params,successFun,failureFun);
				}
			},{
				iconCls:'deppon_icons_softwareUpgrade',
				tooltip: pricing.outerEffectivePlan.i18n('foss.pricing.replicationScheme'), 
				disabled: !pricing.outerEffectivePlan.isPermission('outerEffectivePlan/outerEffectivePlanCopyButton'),
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
					var outerEffectivePlanId = record.get('outerEffectivePlanId');
					var active = record.get('active');
					var name = record.get('name');
					if(active == 'N'){
						pricing.showErrorMes(pricing.outerEffectivePlan.i18n('foss.pricing.pleaseSelectTheProgramHaBbeenActivatedCopy'));
					}else{
						pricing.showQuestionMes(pricing.outerEffectivePlan.i18n('foss.pricing.toDeterminePriceProgramCopy'),function(e){
							if(e=='yes'){
								var me = this;
								//处理复制功能
								var updateWindow =  grid.up().getUpdatePricePlanWindow();
								var params = {'outerEffectiveVo':{'outerEffectivePlanId':outerEffectivePlanId, 'copyName':name}};
								var successFun = function(json){
									pricing.showInfoMes(json.message);
									pricing.outerEffectivePlanId = json.outerEffectiveVo.outerEffectivePlanId;
									updateWindow.outerEffectivePlanEntity = record.data;
									updateWindow.outerEffectivePlanEntity.outerEffectivePlanId= json.outerEffectiveVo.outerEffectivePlanId;
									updateWindow.outerEffectivePlanEntity.name = json.outerEffectiveVo.copyName;
									updateWindow.outerEffectivePlanEntity.active = 'N';
			    					updateWindow.isUpdate = true;
			    					updateWindow.show();
			    					var waitingProcessGrid = Ext.getCmp('T_pricing-outerEffectivePlan_content').getOuterEffectivePlanGridPanel();
			    					waitingProcessGrid.store.load();
								};
								var failureFun = function(json){
									if(Ext.isEmpty(json)){
										pricing.showErrorMes(pricing.outerEffectivePlan.i18n('foss.pricing.requestTimedOut'));//请求超时
									}else{
										pricing.showErrorMes(json.message);
									}
								};
								var url = pricing.realPath('copyOuterEffectivePlan.action');
								pricing.requestJsonAjax(url,params,successFun,failureFun);
							}
						});
					}
				}
			},{
				iconCls:'deppon_icons_end',
				tooltip: pricing.outerEffectivePlan.i18n('foss.pricing.stop'), 
				disabled: !pricing.outerEffectivePlan.isPermission('outerEffectivePlan/outerEffectivePlanStopButton'),
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
					var outerEffectivePlanId = record.get('outerEffectivePlanId');
					var endTime = record.get('endTime');
                	var stopPricePlanWindow =  grid.up().getStopPricePlanWindow();                	
                	stopPricePlanWindow.outerEffectivePlanEntity = record.data;
					pricing.outerEffectivePlanId = outerEffectivePlanId;
                	stopPricePlanWindow.show();
				}
			}]
		},{
			hide:true,
			text: 'ID',
			hidden: true,
	        dataIndex: 'outerEffectivePlanId'
		},{
			width: 140,
			text: pricing.outerEffectivePlan.i18n('foss.pricing.scenarioName'),//方案名称
			align: 'center',
	        dataIndex: 'name'
		},{
			width: 140,
			hidden: true,
	        dataIndex: 'partialLineCode'
		},{
			width: 140,
			text: pricing.outerEffectivePlan.i18n('foss.pricing.destinationLine'),//"目的站",
			align: 'center',
	        dataIndex: 'partialLineName'
		},{
			width: 140,
			hidden: true,
			align: 'center',
	        dataIndex: 'outFieldCode'//productCode
		},{
			width: 140,
			text: pricing.outerEffectivePlan.i18n('foss.pricing.outFieldName'),//"外发外场",
			align: 'center',
	        dataIndex: 'outFieldName'//productCode
		},{
			width: 70,
			text: pricing.outerEffectivePlan.i18n('foss.pricing.outerEffectivePlan.productName'),//"运输类型",//
			align: 'center',
	        dataIndex: 'productCode',
	        renderer: function(value){
				if(value=='TRANS_VEHICLE') {//汽运
					return pricing.outerEffectivePlan.i18n('foss.pricing.outerEffectivePlan.transVehicle');			
				} else if(value=='TRANS_AIRCRAFT') {//空运
					return pricing.outerEffectivePlan.i18n('foss.pricing.outerEffectivePlan.airFreight');
				} else {
					return null;
				}
			}
		},{
			width: 70,
			text: pricing.outerEffectivePlan.i18n('foss.pricing.outerEffectivePlan.dataStatus'),//"数据状态",
			align: 'center',
	        sortable: true,
	        dataIndex: 'active',
	        renderer:function(value){
				if(value=='Y'){//'Y'表示激活
					return pricing.outerEffectivePlan.i18n('foss.pricing.active');//"已激活";
				}else if(value=='N'){//'N'表示未激活
					return  pricing.outerEffectivePlan.i18n('i18n.pricingRegion.unActive');//"已激活";
				}else{
					return '';
				}
			}
		},{
			text: pricing.outerEffectivePlan.i18n('foss.pricing.availabilityDate'),//"生效日期",//
	        dataIndex: 'beginTime',
	        align: 'center',
	        renderer:function(value){
				return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
			}
		},{
			text: pricing.outerEffectivePlan.i18n('foss.pricing.endTimeTwo'),//"截止日期",
	        dataIndex: 'endTime',
	        align: 'center',
	        renderer:function(value){
				return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
			}
		},{
			text : pricing.outerEffectivePlan.i18n('foss.pricing.outerEffectivePlan.currentUsedVersion'),//'是否当前版本',//
			align: 'center',
			dataIndex : 'versionNo',
			renderer:function(value,obj,record){
				if(record.get('versionNo')==pricing.yes){
					return pricing.outerEffectivePlan.i18n('i18n.pricingRegion.ye');//是
				}else if(record.get('versionNo')==pricing.no){
					return pricing.outerEffectivePlan.i18n('i18n.pricingRegion.no');//否
				}else{
					return '';
				}
			}
		},{
			dataIndex : 'provCode',
			align: 'center',
			hidden: true
	    },{
	    	dataIndex : 'cityCode',
	    	align: 'center',
			hidden: true
	    },{
	    	dataIndex : 'countyCode',
	    	align: 'center',
			hidden: true
	    },{
			dataIndex : 'provName',
			align: 'center',
			text: pricing.outerEffectivePlan.i18n('i18n.pricingRegion.pro'),//"省份"
	    },{
	    	dataIndex : 'cityName',
	    	align: 'center',
			text: pricing.outerEffectivePlan.i18n('i18n.pricingRegion.city'),//"城市"
	    },{
	    	dataIndex : 'countyName',
	    	align: 'center',
			text: pricing.outerEffectivePlan.i18n('i18n.pricingRegion.county'),//"区县"
	    },{
			text :pricing.outerEffectivePlan.i18n('foss.pricing.commitmentMinimumNumberDaysHours'),//承诺最小天数或小时 ,
			dataIndex : 'minTime',
			align: 'center',
			renderer:function(value,obj,record){
				var showValue = value;
				if(record.get('minTimeUnit')==pricing.outerEffectivePlan.DAY){
					showValue = showValue+pricing.outerEffectivePlan.i18n('foss.pricing.day');//天
				}else if(record.get('minTimeUnit')==pricing.outerEffectivePlan.HOURS){
					showValue = showValue+pricing.outerEffectivePlan.i18n('foss.pricing.hour');//小时
				}
				return showValue;
			}
		},{
			text :pricing.outerEffectivePlan.i18n('foss.pricing.commitmentMaximumNumberDaysHours'),//承诺最大天数或小时 , ,
			dataIndex : 'maxTime',
			align: 'center',
			renderer:function(value,obj,record){
				var showValue = value;
				if(record.get('maxTimeUnit')==pricing.outerEffectivePlan.DAY){
					showValue = showValue+pricing.outerEffectivePlan.i18n('foss.pricing.day');//天
				}else if(record.get('maxTimeUnit')==pricing.outerEffectivePlan.HOURS){
					showValue = showValue+pricing.outerEffectivePlan.i18n('foss.pricing.hour');//小时
				}
				return showValue;
			}
		},{
			dataIndex : 'arriveOuterBranchTime',
			align: 'center',
			text: pricing.outerEffectivePlan.i18n('foss.pricing.outerEffectivePlan.deliveryCommitmentTimePoints')//"到达偏线承诺时间点"
	    },{
	    	dataIndex : 'deliveryTime',
	    	align: 'center',
			text: pricing.outerEffectivePlan.i18n('foss.pricing.outerEffectivePlan.deliveryTime')//"承诺派送时间"
	    },{
	    	dataIndex : 'addDay',
	    	align: 'center',
			text: pricing.outerEffectivePlan.i18n('foss.pricing.outerEffectivePlan.deliveryAddDay')//"派送承诺需增天数"
	    },{
			text: pricing.outerEffectivePlan.i18n('foss.pricing.updateTime'),//"修改时间",
			width: 140,
	        dataIndex: 'modifyDate',
	        align: 'center',
	        renderer:function(value){
				return Ext.Date.format(new Date(value), 'Y-m-d H:m:s');
			}
		},{
			text: pricing.outerEffectivePlan.i18n('foss.pricing.modifyUser'),//"修改人",
			width: 80,
			align: 'center',
	        dataIndex: 'modifyUser'
		}];
		me.tbar = [
		{
			text : pricing.outerEffectivePlan.i18n('foss.pricing.theNewScheme'),//"新建方案",
			disabled: !pricing.outerEffectivePlan.isPermission('outerEffectivePlan/outerEffectivePlanAddButton'),
			hidden: !pricing.outerEffectivePlan.isPermission('outerEffectivePlan/outerEffectivePlanAddButton'),
			handler :function(){
				var addWindow = me.getAddpricePlanWindow();
				addWindow.isUpdate = false;
				addWindow.show();
				
			} 
		},'-', {
			text : pricing.outerEffectivePlan.i18n('foss.pricing.activationProgram'),//激活方案
			disabled: !pricing.outerEffectivePlan.isPermission('outerEffectivePlan/outerEffectivePlanActiveButton'),
			hidden: !pricing.outerEffectivePlan.isPermission('outerEffectivePlan/outerEffectivePlanActiveButton'),
			handler :function(){
				me.activePricePlan();
			} 
		},'-', {
			text : pricing.outerEffectivePlan.i18n('foss.pricing.deleteProgram'),
			disabled: !pricing.outerEffectivePlan.isPermission('outerEffectivePlan/outerEffectivePlanDeleteButton'),
			hidden: !pricing.outerEffectivePlan.isPermission('outerEffectivePlan/outerEffectivePlanDeleteButton'),
			handler :function(){
				me.deletePricePlan();
			} 
		},'-',{
			text : pricing.outerEffectivePlan.i18n('foss.pricing.immediatelyActivationProgram'),//'立即激活',
			disabled:!pricing.outerEffectivePlan.isPermission('outerEffectivePlan/outerEffectivePlanImmediatelyActiveButton'),
			hidden:!pricing.outerEffectivePlan.isPermission('outerEffectivePlan/outerEffectivePlanImmediatelyActiveButton'),
			handler :function(){
				me.immediatelyActiveOffLinePricePlan();
			} 
		},'-', {
			text : pricing.outerEffectivePlan.i18n('foss.pricing.immediatelyStopProgram'),//'立即中止',
			disabled:!pricing.outerEffectivePlan.isPermission('outerEffectivePlan/outerEffectivePlanImmediatelyStopButton'),
			hidden:!pricing.outerEffectivePlan.isPermission('outerEffectivePlan/outerEffectivePlanImmediatelyStopButton'),
			handler :function(grid, rowIndex, colIndex){
				me.immediatelyStopOffLinePricePlan();
			} 
		},'-', {
			text : pricing.outerEffectivePlan.i18n('foss.pricing.export'),
			disabled: !pricing.outerEffectivePlan.isPermission('outerEffectivePlan/outerEffectivePlanExportButton'),
			hidden: !pricing.outerEffectivePlan.isPermission('outerEffectivePlan/outerEffectivePlanExportButton'),
			handler :function(){
				var queryForm = pricing.outerEffectivePlan.queryform;
				var pricePlanExport = '';
				
				var partialLineCode = queryForm.getForm().findField('partialLineCode').getValue(); // 目的站
				var outFieldCode = queryForm.getForm().findField('outFieldCode').getValue(); //外发外场
				var productCode = queryForm.getForm().findField('productCode').getValue(); //运输类型
				var active = queryForm.getForm().findField('active').getValue(); //数据状态
				
				if(!Ext.isEmpty(partialLineCode)){
					pricePlanExport = 'copyOuterEffectivePlan.outerEffectivePlanConditionDto.partialLineCode='+partialLineCode;
				}
				if(!Ext.isEmpty(outFieldCode)){
					pricePlanExport = pricePlanExport + '&' + 'outerEffectiveVo.outerEffectivePlanConditionDto.partialLineCode='+outFieldCode;
				}
				pricePlanExport = pricePlanExport + '&' + 'outerEffectiveVo.outerEffectivePlanConditionDto.productCode='+productCode;
				pricePlanExport = pricePlanExport + '&' + 'outerEffectiveVo.outerEffectivePlanConditionDto.active='+active;
				
				var url = pricing.realPath('exportOuterEffectivePlan.action');
				if(!Ext.isEmpty(pricePlanExport)){
					url = url+'?'+pricePlanExport;
				}
				window.location=url;
				pricePlanExport = '';
 			} 
		},'-', {
			text : pricing.outerEffectivePlan.i18n('foss.pricing.import'),
			disabled: !pricing.outerEffectivePlan.isPermission('outerEffectivePlan/outerEffectivePlanImportButton'),
			hidden: !pricing.outerEffectivePlan.isPermission('outerEffectivePlan/outerEffectivePlanImportButton'),
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
Ext.define('Foss.pricing.outerEffectivePlan.OuterPriceDetailForm', {
	extend : 'Ext.form.Panel',
	title: pricing.outerEffectivePlan.i18n('i18n.pricingRegion.searchCondition'),
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
			fieldLabel : pricing.outerEffectivePlan.i18n('foss.pricing.flightCategory'),
			displayField : 'valueName',
			valueField : 'valueCode',
			queryMode : 'local',
			triggerAction : 'all',
			editable : false,
			flightRecord: null,
			store : FossDataDictionary.getDataDictionaryStore('AIR_FLIGHT_TYPE', 'Foss_baseinfo_flight_FlightSortStore_Id', {
				'valueCode' : '',
				'valueName' : pricing.outerEffectivePlan.i18n('i18n.pricingRegion.all')
			}),
			value : ''
		},{
			name: 'arrvRegionId',
			valueField: 'id',
	        fieldLabel:pricing.outerEffectivePlan.i18n('foss.pricing.destinationArea'),//目的区域
	        xtype : 'commonpriceregionselector',
	        airPriceFlag :'Y'
		},{
			xtype : 'container',
			columnWidth : .2,
			margin : '0 0 0 0',
			items : [{
						xtype : 'button', 
						colspan : 1,
						text : pricing.outerEffectivePlan.i18n('foss.pricing.reset'),//重置
						handler : function() {
							this.getForm().reset();
						}
					},{
					xtype : 'button', 
					width:70,
					text : pricing.outerEffectivePlan.i18n('i18n.pricingRegion.search'),
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
Ext.define('Foss.pricing.outerEffectivePlan.OuterPriceDetailForm', {
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
	        fieldLabel:pricing.outerEffectivePlan.i18n('foss.pricing.destinationArea'),//目的区域
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
			fieldLabel : pricing.outerEffectivePlan.i18n('foss.pricing.flightCategory'),
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
	        fieldLabel: pricing.outerEffectivePlan.i18n('foss.pricing.cargoType'),//货物类型
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
	        fieldLabel: pricing.outerEffectivePlan.i18n('foss.pricing.heavyGoodsPrices'),//重货价格
	        xtype : 'numberfield'
		
		},{
			name: 'minimumOneTicket',
			allowBlank:false,
			decimalPrecision:0,
			step:1,
		    maxValue: 99999999,
		    minValue:0,
	        fieldLabel: pricing.outerEffectivePlan.i18n('foss.pricing.theLowestVotes'),//最低一票
	        xtype : 'numberfield'
		},{
			 xtype:'radiogroup',
			 vertical:true,
			 allowBlank:false,
			 name:'centralizePickup',
			 fieldLabel:pricing.outerEffectivePlan.i18n('foss.pricing.whetherorNot'),
			 items:[{
			 	 xtype:'radio',
			     boxLabel:pricing.outerEffectivePlan.i18n('i18n.pricingRegion.ye'),//是
			     name:'centralizePickup',		 
			     inputValue:'Y'
		     },{
		    	 xtype:'radio',
			     boxLabel:pricing.outerEffectivePlan.i18n('i18n.pricingRegion.no'),//否
			     name:'centralizePickup',
			     inputValue:'N'
			     }]
		},{
			xtype: 'textareafield',
			width:300,
		    fieldLabel: pricing.outerEffectivePlan.i18n('foss.pricing.remark'),//备注
		    maxLength:200,
	        name:'remark'
		}];
		me.callParent([cfg]);
	}
});

/**
 * 航偏线价费用方案明细信息新增弹出窗口window
 */
Ext.define('Foss.pricing.outerEffectivePlan.OuterPriceDetailWindow',{
	extend : 'Ext.window.Window',
	title: pricing.outerEffectivePlan.i18n('foss.pricing.aBreakdownOfNew'),//明细信息新增
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
    		this.pricePlanDetailForm = Ext.create('Foss.pricing.outerEffectivePlan.OuterPriceDetailForm');
    	}
    	return this.pricePlanDetailForm;
    },
    
    //想GRID中设置数据
    commitPricePlanDetail:function(grid){
    	var me = this;
    	//得到明细form
    	var form = me.getPricePlanDetailForm().getForm();
    	if(form.isValid()){
    		var pricePlanDetailDto = new Foss.pricing.outerEffectivePlan.OffLinePricePlanDetailDto();
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
	    	pricePlanDetailDto.set('outerEffectivePlanId',pricing.outerEffectivePlanId);
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
					pricing.showErrorMes(pricing.outerEffectivePlan.i18n('i18n.pricingRegion.requestTimeOut'));
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
			text : pricing.outerEffectivePlan.i18n('i18n.pricingRegion.cancel'),//取消
			handler :function(){
				me.close();
			} 
		},{
			text : pricing.outerEffectivePlan.i18n('foss.pricing.reset'),//重置
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
 * 修改偏线时效方案明细信息Window
 */
Ext.define('Foss.pricing.outerEffectivePlan.ModifyOffLinePricePlanDetailWindow',{
	extend : 'Ext.window.Window',
	title: pricing.outerEffectivePlan.i18n('foss.pricing.update'),//'修改',//明细信息新增
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
			me.getPricePlanDetailForm().getForm().loadRecord(new Foss.pricing.outerEffectivePlan.OffLinePricePlanDetailDto(me.pricePlanDetailDto));
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
    		this.pricePlanDetailForm = Ext.create('Foss.pricing.outerEffectivePlan.OuterPriceDetailForm');
    	}
    	return this.pricePlanDetailForm;
    },
     	
    updatePriceDetailPlan:function(grid){
    	var me = this;
    	//得到明细form
    	var form = me.getPricePlanDetailForm().getForm();
    	if(form.isValid()){
    		 	var pricePlanDetailDto = new Foss.pricing.outerEffectivePlan.OffLinePricePlanDetailDto(me.pricePlanDetailDto);
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
	    	pricePlanDetailDto.set('outerEffectivePlanId',pricing.outerEffectivePlanId);
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
					pricing.showErrorMes(pricing.outerEffectivePlan.i18n('i18n.pricingRegion.requestTimeOut'));
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
			text : pricing.outerEffectivePlan.i18n('i18n.pricingRegion.cancel'),//取消
			handler :function(){
				me.close();
			} 
		},{
			text : pricing.outerEffectivePlan.i18n('foss.pricing.reset'),//重置
			margin:'0 185 0 0',
			handler :function(){
				me.getPricePlanDetailForm().getForm().reset();
			} 
		},{
			text : pricing.outerEffectivePlan.i18n('foss.pricing.update'),//'修改',
			cls:'yellow_button',
			handler :function(){
				me.updatePriceDetailPlan(config.grid);
			} 
		}];
		me.callParent([cfg]);
	}
});

/**
 * 偏线时效方案批次信息录入form
 */
Ext.define('Foss.pricing.outerEffectivePlan.OuterPriceAddFormPanel',{
	extend : 'Ext.form.Panel',
	title: pricing.outerEffectivePlan.i18n('foss.pricing.departureinformation'),//出发地信息
	parent:null,
	frame: true,
	operaterCode:null,
	outerEffectivePlanEntity: null,
	priceRegionId: null,
	layout: {
		type:'table',
		columns:2,
	},
	height:450,
    defaults : {
    	margin : '5 10 5 10',
    	columnWidth: 200,
		labelSeparator:'',
		labelWidth:120,
		xtype : 'textfield'
    },
	items: [{
			name: 'id',
			hidden : true
		},{
			name: 'name',
			allowBlank:false,
			maxLength : 65,
	        fieldLabel:pricing.outerEffectivePlan.i18n('foss.pricing.scenarioName')//方案名称
		},{			
			name : 'partialLineCode',
			xtype : 'commonvehagencydeptselector',
			fieldLabel : pricing.outerEffectivePlan.i18n('foss.pricing.destinationStation'),//'目的站',
			allowBlank:false,
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
			hidden :true
		},{
			name: 'provName',
			readOnly: true,
			allowBlank:false,
	        fieldLabel:pricing.outerEffectivePlan.i18n('foss.pricing.provinceName')//省份
		},{
			name: 'cityCode',
			readOnly: true,
			allowBlank:false,
			hidden :true
		},{
			name: 'cityName',
			readOnly: true,
			allowBlank:false,
	        fieldLabel:pricing.outerEffectivePlan.i18n('foss.pricing.cityName')//市
		},{
			name: 'countyCode',
			readOnly: true,
			allowBlank:false,
			hidden :true
		},{
			name: 'countyName',
			readOnly: true,
			allowBlank:false,
	        fieldLabel:pricing.outerEffectivePlan.i18n('foss.pricing.areaName')//区
		},{
			name: 'productCode',
			fieldLabel: pricing.outerEffectivePlan.i18n('foss.pricing.outerEffectivePlan.productName'),//运输类型 
			allowBlank:false,
			xtype: 'combobox',
			value:'TRANS_VEHICLE',
            valueField: 'code',
            displayField: 'name',
			queryMode:'local',
			triggerAction:'all',
			store:Ext.create('Foss.pricing.outerEffectivePlan.Store.ToDoTypeStore',
		    {
		      data:{
		    	  'items':
	              [
	                 {'code':'','name':pricing.outerEffectivePlan.i18n('i18n.pricingRegion.all')},
	                {'code':'TRANS_VEHICLE','name':pricing.outerEffectivePlan.i18n('foss.pricing.outerEffectivePlan.transVehicle')},
	                {'code':'AIR_FREIGHT','name':pricing.outerEffectivePlan.i18n('foss.pricing.outerEffectivePlan.airFreight')}//
	              ]
		      }
		    })
		},{
			xtype : 'commontransfercenterselector', 
			fieldLabel : pricing.outerEffectivePlan.i18n('foss.pricing.outFieldName'),//'外发外场', 
			allowBlank:false,
			active:'Y',  
			name:'outFieldCode'
		},{
			xtype:'datefield',
			allowBlank:false,
			fieldLabel:pricing.outerEffectivePlan.i18n('foss.pricing.availabilityDate'),//生效日期
			format:'20y-m-d',
			name:'beginTime'
		},{
		      name: 'minTime',
		      allowBlank:false,
		      decimalPrecision:0,
		      maxValue: 999999,
	          minValue: 1,
	          fieldLabel: pricing.outerEffectivePlan.i18n('foss.pricing.commitmentMinimumNumberDaysTime'),//承诺最小天数或时间
	          xtype : 'numberfield'
		    },{
		      xtype:'radiogroup',
		      vertical:true,
		      allowBlank:false,
		      name:'minTimeUnit',
		      labelSeparator:'',
		       items:[{
		          xtype:'radio',
		           boxLabel:pricing.outerEffectivePlan.i18n('foss.pricing.hour'),
		           name:'minTimeUnit',
		           inputValue:pricing.outerEffectivePlan.HOURS
		         },{
		           xtype:'radio',
		           boxLabel:pricing.outerEffectivePlan.i18n('foss.pricing.days'),
		           name:'minTimeUnit',
		           inputValue:pricing.outerEffectivePlan.DAY
		       }]
		    },{
		      name: 'maxTime',
		      allowBlank:false,
		      decimalPrecision:0,
		      maxValue: 999999,
	          minValue: 1,
	          fieldLabel: pricing.outerEffectivePlan.i18n('foss.pricing.commitmentMaximumNumberDaysTime'),//承诺最大天数或时间
	          xtype : 'numberfield'
		    },{
		      xtype:'radiogroup',
		       vertical:true,
		       allowBlank:false,
		       name:'maxTimeUnit',
		      labelSeparator:'',
		       items:[{
		          xtype:'radio',
		           boxLabel:pricing.outerEffectivePlan.i18n('foss.pricing.hour'),
		           name:'maxTimeUnit',
		           inputValue:pricing.outerEffectivePlan.HOURS
		         },{
		           xtype:'radio',
		           boxLabel:pricing.outerEffectivePlan.i18n('foss.pricing.days'),
		           name:'maxTimeUnit',
		           inputValue:pricing.outerEffectivePlan.DAY
		       }]
		    },{
		      xtype: 'timefield',
		      fieldLabel: pricing.outerEffectivePlan.i18n('foss.pricing.outerEffectivePlan.deliveryCommitmentTimePoints'),//到达代理网点承诺时点
		      name:'arriveOuterBranchTime',
		      format:'H:i'//24小时制
		    },{
		      xtype: 'displayfield',
		      name:'attention'
		    },{
		      name: 'addDay',//顺序号
		      allowBlank:false,
	          fieldLabel: pricing.outerEffectivePlan.i18n('foss.pricing.deliveryCommitmentsNeedAddNumberDays'),//
	          step:1,
	          maxValue:50,
	          minValue:0,
	          xtype : 'numberfield'
		    },{
		      xtype: 'displayfield',
		      name:'attention'
		    },{
		      xtype: 'timefield',
		      fieldLabel: pricing.outerEffectivePlan.i18n('foss.pricing.deliveryCommitmentTimePoints'),//派送承诺时点
	          name:'deliveryTime',
	          format:'H:i'//24小时制
		    },{
		      xtype: 'displayfield',
		      name:'attention'
		    }],
	
	/**修改价格方案**/
	commitUpdatePricePlan:function(){
    	var me = this;
    	var form = me.getForm();
    	if(form.isValid()){//校验form是否通过校验
    		var outerEffectivePlanEntity = me.up('window').outerEffectivePlanEntity;
    		var pricePlanModel = new Foss.pricing.outerEffectivePlan.outerEffectivePlanModel(outerEffectivePlanEntity);
    		form.updateRecord(pricePlanModel);//将FORM中数据设置到MODEL里面
    		//方案名称
    		var name = form.findField('name').getValue();	//
    		if (name == null ||name == '') {
    			pricing.showErrorMes("方案名称不能为空！");
    			return false;
    		}
    		//目的站
    		var partialLineCode = form.findField('partialLineCode').getValue();	//
    		if (partialLineCode == null ||partialLineCode == '') {
    			pricing.showErrorMes("目的站不能为空！");
    			return false;
    		}    		
    		//产品类型
	    	var productCode = form.findField('productCode').valueCode;//运输类型
    		if (productCode == null ||productCode == '') {
    			pricing.showErrorMes("运输类型不能为空！");
    			return false;
    		}
    		var valueCode = form.findField('productCode').displayTplData[0].valueCode;//运输类型
    		if (valueCode == null || valueCode == '' || valueCode == undefined) {
    			
    		} else {
    			productCode = valueCode;
    		}
    		//外发外场
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
    		
    		//承诺最大小时
    		var maxTime = form.findField('maxTime').getRawValue();
    		//承诺最小小时
    		var minTime = form.findField('minTime').getRawValue();
    		//承诺最大天数
    		var minTimeUnit = form.findField('minTimeUnit').getValue();
    		//承诺最小天数
    		var maxTimeUnit = form.findField('maxTimeUnit').getValue();
    		//时间单位是否一致
    		if(minTimeUnit.minTimeUnit != maxTimeUnit.maxTimeUnit){
    			pricing.showErrorMes(pricing.outerEffectivePlan.i18n('foss.pricing.inconsistentUnitTimeSetTimDayNumberSelectConsistent'));
    			return;
    		}
    		if(maxTime<minTime){
    			pricing.showErrorMes(pricing.outerEffectivePlan.i18n('foss.pricing.commitmentCanNotLessThanMaximumCommitmentMinimumTime'));
    			return;
    		}
    		var deliveryTime = form.findField('deliveryTime').getRawValue().replace(':','') ;
    		var arriveOuterBranchTime = form.findField('arriveOuterBranchTime').getRawValue().replace(':','');
    		var name = form.findField('name').getValue();
    		var partialLineCode = form.findField('partialLineCode').getValue();//目的站
    		var provCode = form.findField('provCode').getValue();	//省
    		var cityCode = form.findField('cityCode').getValue();	//市
    		var countyCode = form.findField('countyCode').getValue();	//区
    		var productCode = form.findField('productCode').getValue();//产品类型
    		var beginTime = form.findField('beginTime').getValue();//开始时间
    		var minTime = form.findField('minTime').getValue();//结束时间
    		var maxTime = form.findField('maxTime').getValue();//
    		var addDay = form.findField('addDay').getValue();
    		var outerEffectivePlanId = form.findField('outerEffectivePlanId').getValue();
			
    		//处理特殊字段
    		pricePlanModel.set('id',outerEffectivePlanId);
    		pricePlanModel.set('name',name);
    		pricePlanModel.set('partialLineCode',partialLineCode);
    		pricePlanModel.set('provCode',provCode);
    		pricePlanModel.set('cityCode',cityCode);
    		pricePlanModel.set('countyCode',countyCode);
    		pricePlanModel.set('outFieldCode',outFieldCode);
    		pricePlanModel.set('productCode',productCode);
    		pricePlanModel.set('beginTime',beginTime);
    		
    		pricePlanModel.set('minTime',minTime);
    		pricePlanModel.set('maxTime',maxTime);
    		
    		pricePlanModel.set('deliveryTime',deliveryTime);
    		pricePlanModel.set('arriveOuterBranchTime',arriveOuterBranchTime);
    		pricePlanModel.set('addDay',addDay);
    		
    		var params = {'outerEffectiveVo':{'outerEffectivePlanEntity':pricePlanModel.data}};//组织需要修改的数据
    		var successFun = function(json){
				pricing.showInfoMes(json.message);//提示新增成功
			};
			var failureFun = function(json){
				if(Ext.isEmpty(json)){
					pricing.showErrorMes(pricing.outerEffectivePlan.i18n('foss.pricing.requestTimedOut'));//请求超时
				}else{
					pricing.showErrorMes(json.message);//提示失败原因
				}
			};
			var url = pricing.realPath('updateOuterEffectivePlan.action');//请求价格方案修改
			pricing.requestJsonAjax(url,params,successFun,failureFun);//发送AJAX请求
    	}
	 },
	
	/***批次保存***/
	commitPricePlan:function(){
		var me = this;
    	//获取表单
    	var form = me.getForm(); 
    	var pricePlanModel = new Foss.pricing.outerEffectivePlan.outerEffectivePlanModel();
    	if(form.isValid()){
    		form.updateRecord(pricePlanModel);
    		//产品类型
    		var productCode = form.findField('productCode').getValue();	//
    		if (productCode == null ||productCode == '') {
    			pricing.showErrorMes("运输类型不能为空！");
    			return false;
    		}
    		var valueCode = form.findField('productCode').displayTplData[0].valueCode;//运输类型
    		if (valueCode == null || valueCode == '' || valueCode == undefined) {
    			
    		} else {
    			productCode = valueCode;
    		}
    		//外发外场编码
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
    		//生效时间
    		var beginTime = form.findField('beginTime').getValue();
    		var time = Date.parse(beginTime);
    		var nowDate = new Date(); 
    		if (time < Date.parse(nowDate.format('yyyy-MM-dd'))) {
    			pricing.showErrorMes("生效日期不能小于当前日期！");
    			return false;
    		}
    		
    		//承诺最大小时
    		var maxTime = form.findField('maxTime').getRawValue();
    		//承诺最小小时
    		var minTime = form.findField('minTime').getRawValue();
    		//承诺最大天数
    		var minTimeUnit = form.findField('minTimeUnit').getValue();
    		//承诺最小天数
    		var maxTimeUnit = form.findField('maxTimeUnit').getValue();
    		//时间单位是否一致
    		if(minTimeUnit.minTimeUnit != maxTimeUnit.maxTimeUnit){
    			pricing.showErrorMes(pricing.outerEffectivePlan.i18n('foss.pricing.inconsistentUnitTimeSetTimDayNumberSelectConsistent'));
    			return;
    		}
    		if(maxTime<minTime){
    			pricing.showErrorMes(pricing.outerEffectivePlan.i18n('foss.pricing.commitmentCanNotLessThanMaximumCommitmentMinimumTime'));
    			return;
    		}
    		var deliveryTime = form.findField('deliveryTime').getRawValue().replace(':','') ;
    		var arriveOuterBranchTime = form.findField('arriveOuterBranchTime').getRawValue().replace(':','');
    		var name = form.findField('name').getValue();
    		var partialLineCode = form.findField('partialLineCode').getValue();//目的站
    		var provCode = form.findField('provCode').getValue();	//省
    		var cityCode = form.findField('cityCode').getValue();	//市
    		var countyCode = form.findField('countyCode').getValue();	//区
    		var productCode = form.findField('productCode').getValue();
    		var beginTime = form.findField('beginTime').getValue();
    		var minTime = form.findField('minTime').getValue();
    		var maxTime = form.findField('maxTime').getValue();
    		var addDay = form.findField('addDay').getValue();
			
    		//处理特殊字段
    		pricePlanModel.set('name',name);
    		pricePlanModel.set('partialLineCode',partialLineCode);
    		pricePlanModel.set('provCode',provCode);
    		pricePlanModel.set('cityCode',cityCode);
    		pricePlanModel.set('countyCode',countyCode);
    		pricePlanModel.set('outFieldCode',outFieldCode);
    		pricePlanModel.set('productCode',productCode);
    		pricePlanModel.set('beginTime',beginTime);
    		
    		pricePlanModel.set('minTime',minTime);
    		pricePlanModel.set('maxTime',maxTime);
    		
    		pricePlanModel.set('deliveryTime',deliveryTime);
    		pricePlanModel.set('arriveOuterBranchTime',arriveOuterBranchTime);
    		pricePlanModel.set('addDay',addDay);
    		
    		var params = {'outerEffectiveVo':{'outerEffectivePlanEntity':pricePlanModel.data}};
    		var url = pricing.realPath('addOuterEffectivePlan.action');
    		
    		//成功提示
    		var successFun = function(json){
    			pricing.showInfoMes(json.message);
    			//成功后获取价格方案主ID
				pricing.outerEffectivePlanId = json.outerEffectiveVo.outerEffectivePlanEntity.id;  
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
    				pricing.showErrorMes(pricing.outerEffectivePlan.i18n('i18n.pricingRegion.requestTimeOut'));
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
    		outerEffectivePlanEntity = me.up('window').outerEffectivePlanEntity;
    		priceRegionId = baseForm.findField('arrvRegionCode').priceRegionId;
    		
    		var grid = me.up('window').getPricePlanDetailGridPanel();
    		grid.arrvRegionId = priceRegionId;
    		grid.pricePlanId = outerEffectivePlanEntity.id;
			grid.getStore().load();
    	}
	 },
	 
	//价格方案明细信息 （目的明细列表）
	pricePlanDetailGridPanel:null,
    getPricePlanDetailGridPanel: function(){
    	if(Ext.isEmpty(this.pricePlanDetailGridPanel)){
    			this.pricePlanDetailGridPanel = Ext.create('Foss.pricing.outerEffectivePlan.OuterPriceDetailGridPanel');
    			pricing.pricePlanDetailGridPanel  = this.pricePlanDetailGridPanel;
    	}
    	return this.pricePlanDetailGridPanel;
    },	
	//构造函数
	constructor : function(config) {
		var me = this, 
		cfg = Ext.apply({}, config);
		me.fbar = [{
			text :pricing.outerEffectivePlan.i18n('i18n.pricingRegion.cancel'),//取消
			handler :function(){
				me.up('window').isUpdate = null;
				me.up().close();
			}
		},{
			text : pricing.outerEffectivePlan.i18n('foss.pricing.reset'),//重置
			handler :function(){
					me.getForm().reset();//表格重置
			} 
		},{
			text : pricing.outerEffectivePlan.i18n('foss.pricing.save'),//保存
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
Ext.define('Foss.pricing.outerEffectivePlan.OuterPriceUpdateFormPanel',{
	extend : 'Ext.form.Panel',
	title: pricing.outerEffectivePlan.i18n('foss.pricing.departureinformation'),//"出发地信息",
	parent:null,
	frame: true,
	operaterCode:null,
	outerEffectivePlanEntity: null,
	priceRegionId: null,
	layout: {
		type:'table',
		columns:2,
	},
	height:450,
    defaults : {
    	//columnWidth : 1,
    	margin : '5 10 5 10',
    	//width:160,
    	columnWidth: 200,
		labelSeparator:'',
		labelWidth:120,
		xtype : 'textfield'
    },
	items: [{
	      name: 'outerEffectivePlanId',
	      hidden : true
	    },{
	      name: 'name',
	      allowBlank:false,
	      maxLength : 65,
	      fieldLabel:pricing.outerEffectivePlan.i18n('foss.pricing.scenarioName')//方案名称
	    },{      
	      name : 'partialLineCode',
	      xtype : 'commonvehagencydeptselector',
	      fieldLabel : pricing.outerEffectivePlan.i18n('foss.pricing.destinationStation'),//'目的站',
	      allowBlank:false,
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
	      hidden :true
	    },{
	      name: 'provName',
	      readOnly: true,
	      allowBlank:false,
	          fieldLabel:pricing.outerEffectivePlan.i18n('foss.pricing.provinceName')//省份
	    },{
	      name: 'cityCode',
	      readOnly: true,
	      allowBlank:false,
	      hidden :true
	    },{
	      name: 'cityName',
	      readOnly: true,
	      allowBlank:false,
	      fieldLabel:pricing.outerEffectivePlan.i18n('foss.pricing.cityName')//市
	    },{
	      name: 'countyCode',
	      readOnly: true,
	      allowBlank:false,
	      hidden :true
	    },{
	      name: 'countyName',
	      readOnly: true,
	      allowBlank:false,
	      fieldLabel:pricing.outerEffectivePlan.i18n('foss.pricing.areaName')//区
	    },{
			name: 'productCode',
			fieldLabel: pricing.outerEffectivePlan.i18n('foss.pricing.outerEffectivePlan.productName'),//运输类型 
			allowBlank:false,
			xtype: 'combobox',
			value:'TRANS_VEHICLE',
            valueField: 'code',
            displayField: 'name',
			queryMode:'local',
			triggerAction:'all',
			store:Ext.create('Foss.pricing.outerEffectivePlan.Store.ToDoTypeStore',
		    {
		      data:{
		    	  'items':
	              [
	                 {'code':'','name':pricing.outerEffectivePlan.i18n('i18n.pricingRegion.all')},
	                {'code':'TRANS_VEHICLE','name':pricing.outerEffectivePlan.i18n('foss.pricing.outerEffectivePlan.transVehicle')},
	                {'code':'AIR_FREIGHT','name':pricing.outerEffectivePlan.i18n('foss.pricing.outerEffectivePlan.airFreight')}//
	              ]
		      }
		    })
		},{
	      xtype : 'commontransfercenterselector', 
	      fieldLabel : pricing.outerEffectivePlan.i18n('foss.pricing.outFieldName'),//'外发外场', //
	      allowBlank:false,
	      active:'Y',  
	      name:'outFieldCode'
	      
	    },{
	      xtype:'datefield',
	      allowBlank:false,
	      fieldLabel:pricing.outerEffectivePlan.i18n('foss.pricing.availabilityDate'),//生效日期
	      format:'20y-m-d',
	      name:'beginTime'
	    },{
		  name: 'minTime',
		  allowBlank:false,
		  decimalPrecision:0,
		  maxValue: 999999,
		  minValue: 1,
		  fieldLabel: pricing.outerEffectivePlan.i18n('foss.pricing.commitmentMinimumNumberDaysTime'),//承诺最小天数或时间
		  xtype : 'numberfield'
	    },{
          xtype:'radiogroup',
          vertical:true,
          allowBlank:false,
          name:'minTimeUnit',
          labelSeparator:'',
           items:[{
              xtype:'radio',
               boxLabel:pricing.outerEffectivePlan.i18n('foss.pricing.hour'),
               name:'minTimeUnit',
               inputValue:pricing.outerEffectivePlan.HOURS
             },{
               xtype:'radio',
               boxLabel:pricing.outerEffectivePlan.i18n('foss.pricing.days'),
               name:'minTimeUnit',
               inputValue:pricing.outerEffectivePlan.DAY
           }]
        },{
          name: 'maxTime',
          allowBlank:false,
          decimalPrecision:0,
          maxValue: 999999,
	      minValue: 1,
	      fieldLabel: pricing.outerEffectivePlan.i18n('foss.pricing.commitmentMaximumNumberDaysTime'),//承诺最大天数或时间
	      xtype : 'numberfield'
        },{
          xtype:'radiogroup',
           vertical:true,
           allowBlank:false,
           name:'maxTimeUnit',
          labelSeparator:'',
           items:[{
              xtype:'radio',
               boxLabel:pricing.outerEffectivePlan.i18n('foss.pricing.hour'),
               name:'maxTimeUnit',
               inputValue:pricing.outerEffectivePlan.HOURS
             },{
               xtype:'radio',
               boxLabel:pricing.outerEffectivePlan.i18n('foss.pricing.days'),
               name:'maxTimeUnit',
               inputValue:pricing.outerEffectivePlan.DAY
           }]
        },{
          xtype: 'timefield',
          fieldLabel: pricing.outerEffectivePlan.i18n('foss.pricing.outerEffectivePlan.deliveryCommitmentTimePoints'),//到达代理网点承诺时点
          name:'arriveOuterBranchTime',
          format:'H:i'//24小时制
        },{
          name: 'addDay',//顺序号
          allowBlank:false,
          fieldLabel: pricing.outerEffectivePlan.i18n('foss.pricing.deliveryCommitmentsNeedAddNumberDays'),//
          step:1,
          maxValue:50,
          minValue:0,
          xtype : 'numberfield'
        },{
          xtype: 'timefield',
          fieldLabel: pricing.outerEffectivePlan.i18n('foss.pricing.deliveryCommitmentTimePoints'),//派送承诺时点
            name:'deliveryTime',
            format:'H:i'//24小时制
        }],
	
	/**修改价格方案**/
	commitUpdatePricePlan:function(){
    	var me = this;
    	var form = me.getForm();
    	if(form.isValid()){//校验form是否通过校验
    		var outerEffectivePlanEntity = me.up('window').outerEffectivePlanEntity;
    		var pricePlanModel = new Foss.pricing.outerEffectivePlan.outerEffectivePlanModel(outerEffectivePlanEntity);
    		
    		form.updateRecord(pricePlanModel);//将FORM中数据设置到MODEL里面
    		
    		//方案名称
    		var name = form.findField('name').getValue();	//
    		if (name == null ||name == '') {
    			pricing.showErrorMes("方案名称不能为空！");
    			return false;
    		}
    		//目的站
    		var partialLineCode = form.findField('partialLineCode').getValue();	//
    		if (partialLineCode == null ||partialLineCode == '') {
    			pricing.showErrorMes("目的站不能为空！");
    			return false;
    		}    		
    		//产品类型
	    	var productCode = form.findField('productCode').valueCode;//运输类型
    		if (productCode == null ||productCode == '') {
    			pricing.showErrorMes("运输类型不能为空！");
    			return false;
    		}
    		var valueCode = form.findField('productCode').displayTplData[0].valueCode;//运输类型
    		if (valueCode == null || valueCode == '' || valueCode == undefined) {
    			
    		} else {
    			productCode = valueCode;
    		}
    		//外发外场
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
    		
    		//承诺最大小时
    		var maxTime = form.findField('maxTime').getRawValue();
    		//承诺最小小时
    		var minTime = form.findField('minTime').getRawValue();
    		//承诺最大天数
    		var minTimeUnit = form.findField('minTimeUnit').getValue();
    		//承诺最小天数
    		var maxTimeUnit = form.findField('maxTimeUnit').getValue();
    		//时间单位是否一致
    		if(minTimeUnit.minTimeUnit != maxTimeUnit.maxTimeUnit){
    			pricing.showErrorMes(pricing.outerEffectivePlan.i18n('foss.pricing.inconsistentUnitTimeSetTimDayNumberSelectConsistent'));
    			return;
    		}
    		if(maxTime<minTime){
    			pricing.showErrorMes(pricing.outerEffectivePlan.i18n('foss.pricing.commitmentCanNotLessThanMaximumCommitmentMinimumTime'));
    			return;
    		}
    		var deliveryTime = form.findField('deliveryTime').getRawValue().replace(':','') ;
    		var arriveOuterBranchTime = form.findField('arriveOuterBranchTime').getRawValue().replace(':','');
    		var name = form.findField('name').getValue();
    		var partialLineCode = form.findField('partialLineCode').getValue();//目的站
    		var provCode = form.findField('provCode').getValue();	//省
    		var cityCode = form.findField('cityCode').getValue();	//市
    		var countyCode = form.findField('countyCode').getValue();	//区
    		var beginTime = form.findField('beginTime').getValue();//开始时间
    		var minTime = form.findField('minTime').getValue();//结束时间
    		var maxTime = form.findField('maxTime').getValue();//
    		var addDay = form.findField('addDay').getValue();
    		var outerEffectivePlanId = form.findField('outerEffectivePlanId').getValue();
			
    		//处理特殊字段
    		pricePlanModel.set('id',outerEffectivePlanId);
    		pricePlanModel.set('name',name);
    		pricePlanModel.set('partialLineCode',partialLineCode);
    		pricePlanModel.set('provCode',provCode);
    		pricePlanModel.set('cityCode',cityCode);
    		pricePlanModel.set('countyCode',countyCode);
    		pricePlanModel.set('outFieldCode',outFieldCode);
    		pricePlanModel.set('productCode',productCode);
    		pricePlanModel.set('beginTime',beginTime);
    		
    		pricePlanModel.set('minTime',minTime);
    		pricePlanModel.set('maxTime',maxTime);
    		
    		pricePlanModel.set('deliveryTime',deliveryTime);
    		pricePlanModel.set('arriveOuterBranchTime',arriveOuterBranchTime);
    		pricePlanModel.set('addDay',addDay);
    		pricePlanModel.set('versionNo',null);
    		var params = {'outerEffectiveVo':{'outerEffectivePlanEntity':pricePlanModel.data}};//组织需要修改的数据
    		
    		var successFun = function(json){
				pricing.showInfoMes(json.message);//提示新增成功
				me.up('window').close();
				var waitingProcessGrid = Ext.getCmp('T_pricing-outerEffectivePlan_content').getOuterEffectivePlanGridPanel();
				waitingProcessGrid.store.load();
			};
			var failureFun = function(json){
				if(Ext.isEmpty(json)){
					pricing.showErrorMes(pricing.outerEffectivePlan.i18n('foss.pricing.requestTimedOut'));//请求超时
				}else{
					pricing.showErrorMes(json.message);//提示失败原因
				}
			};
			var url = pricing.realPath('updateOuterEffectivePlan.action');//请求价格方案修改
			pricing.requestJsonAjax(url,params,successFun,failureFun);//发送AJAX请求
    	}
	 },
	
	/***批次保存***/
	commitPricePlan:function(){
		var me = this;
    	//获取表单
    	var form = me.getForm(); 
    	var pricePlanModel = new Foss.pricing.outerEffectivePlan.outerEffectivePlanModel();
    	if(form.isValid()){
    		form.updateRecord(pricePlanModel);
    		//方案名称
    		var name = form.findField('name').getValue();	//
    		if (name == null ||name == '') {
    			pricing.showErrorMes("方案名称不能为空！");
    			return false;
    		}
    		//目的站
    		var partialLineCode = form.findField('partialLineCode').getValue();	//
    		if (partialLineCode == null ||partialLineCode == '') {
    			pricing.showErrorMes("目的站不能为空！");
    			return false;
    		}    		
    		//产品类型
	    	var productCode = form.findField('productCode').valueCode;//运输类型
    		if (productCode == null ||productCode == '') {
    			pricing.showErrorMes("运输类型不能为空！");
    			return false;
    		}
    		var valueCode = form.findField('productCode').displayTplData[0].valueCode;//运输类型
    		if (valueCode == null || valueCode == '' || valueCode == undefined) {
    			
    		} else {
    			productCode = valueCode;
    		}
    		//外发外场
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
    		
    		//承诺最大小时
    		var maxTime = form.findField('maxTime').getRawValue();
    		//承诺最小小时
    		var minTime = form.findField('minTime').getRawValue();
    		//承诺最大天数
    		var minTimeUnit = form.findField('minTimeUnit').getValue();
    		//承诺最小天数
    		var maxTimeUnit = form.findField('maxTimeUnit').getValue();
    		//时间单位是否一致
    		if(minTimeUnit.minTimeUnit != maxTimeUnit.maxTimeUnit){
    			pricing.showErrorMes(pricing.outerEffectivePlan.i18n('foss.pricing.inconsistentUnitTimeSetTimDayNumberSelectConsistent'));
    			return;
    		}
    		if(maxTime<minTime){
    			pricing.showErrorMes(pricing.outerEffectivePlan.i18n('foss.pricing.commitmentCanNotLessThanMaximumCommitmentMinimumTime'));
    			return;
    		}
    		var deliveryTime = form.findField('deliveryTime').getRawValue().replace(':','') ;
    		var arriveOuterBranchTime = form.findField('arriveOuterBranchTime').getRawValue().replace(':','');
    		var name = form.findField('name').getValue();
    		var partialLineCode = form.findField('partialLineCode').getValue();//目的站
    		var provCode = form.findField('provCode').getValue();	//省
    		var cityCode = form.findField('cityCode').getValue();	//市
    		var countyCode = form.findField('countyCode').getValue();	//区
    		var beginTime = form.findField('beginTime').getValue();//开始时间
    		var minTime = form.findField('minTime').getValue();//结束时间
    		var maxTime = form.findField('maxTime').getValue();//
    		var addDay = form.findField('addDay').getValue();
    		var outerEffectivePlanId = form.findField('outerEffectivePlanId').getValue();
			
    		//处理特殊字段
    		pricePlanModel.set('id',outerEffectivePlanId);
    		pricePlanModel.set('name',name);
    		pricePlanModel.set('partialLineCode',partialLineCode);
    		pricePlanModel.set('provCode',provCode);
    		pricePlanModel.set('cityCode',cityCode);
    		pricePlanModel.set('countyCode',countyCode);
    		pricePlanModel.set('outFieldCode',outFieldCode);
    		pricePlanModel.set('productCode',productCode);
    		pricePlanModel.set('beginTime',beginTime);
    		
    		pricePlanModel.set('minTime',minTime);
    		pricePlanModel.set('maxTime',maxTime);
    		
    		pricePlanModel.set('deliveryTime',deliveryTime);
    		pricePlanModel.set('arriveOuterBranchTime',arriveOuterBranchTime);
    		pricePlanModel.set('addDay',addDay);
    		
    		var params = {'outerEffectiveVo':{'outerEffectivePlanEntity':pricePlanModel.data}};
    		var url = pricing.realPath('addOuterEffectivePlan.action');
    		
    		//成功提示
    		var successFun = function(json){
    			pricing.showInfoMes(json.message);
    			//成功后获取价格方案主ID
				pricing.outerEffectivePlanId = json.priceManageMentVo.outerEffectivePlanEntity.id;  
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
    				pricing.showErrorMes(pricing.outerEffectivePlan.i18n('i18n.pricingRegion.requestTimeOut'));
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
    		outerEffectivePlanEntity = me.up('window').outerEffectivePlanEntity;
    		priceRegionId = baseForm.findField('arrvRegionCode').priceRegionId;
    		
    		var grid = me.up('window').getPricePlanDetailGridPanel();
    		grid.arrvRegionId = priceRegionId;
    		grid.pricePlanId = outerEffectivePlanEntity.id;
			grid.getStore().load();
    	}
	 },
	 
	//价格方案明细信息 （目的明细列表）
	pricePlanDetailGridPanel:null,
    getPricePlanDetailGridPanel: function(){
    	if(Ext.isEmpty(this.pricePlanDetailGridPanel)){
    			this.pricePlanDetailGridPanel = Ext.create('Foss.pricing.outerEffectivePlan.OuterPriceDetailGridPanel');
    			pricing.pricePlanDetailGridPanel  = this.pricePlanDetailGridPanel;
    	}
    	return this.pricePlanDetailGridPanel;
    },	
	//构造函数
	constructor : function(config) {
		var me = this, 
		cfg = Ext.apply({}, config);
		me.fbar = [{
			text :pricing.outerEffectivePlan.i18n('i18n.pricingRegion.cancel'),//取消
			handler :function(){
				me.up('window').isUpdate = null;
				me.up().close();
			}
		},/*{
			text : pricing.outerEffectivePlan.i18n('foss.pricing.reset'),//重置
			handler :function(){
					me.getForm().reset();//表格重置
			} 
		},*/{
			text : pricing.outerEffectivePlan.i18n('foss.pricing.save'),//保存
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
Ext.define('Foss.pricing.outerEffectivePlan.OuterPriceDetailGridPanel',{
	extend: 'Ext.grid.Panel',
	title : pricing.outerEffectivePlan.i18n('foss.pricing.destinationInformation'),//"目的地信息",
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
		text : pricing.outerEffectivePlan.i18n('i18n.pricingRegion.num')//"序号"//序号
		
	},{
		text : pricing.outerEffectivePlan.i18n('i18n.pricingRegion.opra'),
		align : 'center',
		xtype : 'actioncolumn',
		items: [{
				iconCls:'deppon_icons_edit',
				tooltip: pricing.outerEffectivePlan.i18n('foss.pricing.toAmendTheProposal'), 
				width:42,
				handler: function(grid, rowIndex, colIndex){
					var me = this;
                	var record = grid.up().getStore().getAt(rowIndex);//选中数据
                	
                	var pricePlanDetaiModel = new Foss.pricing.outerEffectivePlan.OffLinePricePlanDetailDto();
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
    						pricing.showErrorMes(pricing.outerEffectivePlan.i18n('foss.pricing.requestTimedOut'));//请求超时
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
    		this.pricePlanDetailWindow = Ext.create('Foss.pricing.outerEffectivePlan.OuterPriceDetailWindow',{
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
    		this.modifyPricePlanDetailWindow = Ext.create('Foss.pricing.outerEffectivePlan.ModifyOffLinePricePlanDetailWindow',{
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
			pricing.showWoringMessage(pricing.outerEffectivePlan.i18n('foss.pricing.pleaseSelectOneDeleteOperation'));//请选择一条进行删除操作！
			return;//没有则提示并返回
		}
		for(var i = 0;i<selections.length;i++){
			if(selections[i].get('active')==pricing.yes){
				pricing.showWoringMessage(pricing.outerEffectivePlan.i18n('foss.pricing.pleaseChooseToNotActivateTheDataToBeBeleted'));//请选择未激活数据进行删除！
				return;//没有则提示并返回
			}
		}
		pricing.showQuestionMes(pricing.outerEffectivePlan.i18n('foss.pricing.youWantDeletePricProgramDetails'),function(e){//是否要删除这些价格方案明细？
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
						pricing.showErrorMes(pricing.outerEffectivePlan.i18n('foss.pricing.requestTimedOut'));//请求超时
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
			me.store = Ext.create('Foss.pricing.outerEffectivePlan.OuterPriceDeatilStore',{
			autoLoad : false,
			pageSize : 10,
			listeners: {
				beforeload: function(store, operation, eOpts){
					var outerEffectivePlanId = pricing.outerEffectivePlanId;
					if(outerEffectivePlanId!=null){
						Ext.apply(operation,{
							params : {
								'priceManageMentVo.queryPricePlanDetailBean.pricePlanId' : outerEffectivePlanId//价格方案ID
							}
						});	
					}
				}
		    }
		});
		me.selModel = me.getCheckboxModel();
		me.store = Ext.create('Foss.pricing.outerEffectivePlan.OuterPriceDeatilStore',{
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
	            text: pricing.outerEffectivePlan.i18n('i18n.pricingRegion.add'),
	            handler:function(){ 
	            	me.getPricePlanDetailWindow().show();	 
	            }
	        },'-',{
	            text: pricing.outerEffectivePlan.i18n('foss.pricing.delete'),
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
Ext.define('Foss.pricing.outerEffectivePlan.OuterPriceAddWindow',{
		extend: 'Ext.window.Window',
		title: pricing.outerEffectivePlan.i18n('pricing.outerEffectivePlan.addOuterEffectivePlan'),//"新增价格方案",
		x:400,
		y:50,
		width:700,
		height:520,
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
	    		this.pricePlanAddFormPanel = Ext.create('Foss.pricing.outerEffectivePlan.OuterPriceAddFormPanel');
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
Ext.define('Foss.pricing.outerEffectivePlan.OuterPriceUpdateWindow',{
		extend: 'Ext.window.Window',
		title: pricing.outerEffectivePlan.i18n('foss.pricing.modifyAgingProgram'),//"修改价格方案",
		width:650,
		height:520,
		modal:true,
		isUpdate:null,
		parent:null,
		outerEffectivePlanEntity:null,
 		pricePlanDetailDtoList:null,
		closeAction:'hide',
	    //监听器
	    listeners:{
			beforehide:function(me){
				me.getOuterPriceUpdateFormPanel().getForm().reset();
				me.isUpdate = null;
			},
			beforeshow:function(me){
				me.getOuterPriceUpdateFormPanel().getForm().loadRecord(new Foss.pricing.outerEffectivePlan.outerEffectivePlanModel(me.outerEffectivePlanEntity));
				
				me.getOuterPriceUpdateFormPanel().getForm().findField('partialLineCode').setCombValue(me.outerEffectivePlanEntity.partialLineName, me.outerEffectivePlanEntity.partialLineCode);
				me.getOuterPriceUpdateFormPanel().getForm().findField('outFieldCode').setCombValue(me.outerEffectivePlanEntity.outFieldName, me.outerEffectivePlanEntity.outFieldCode);
				
				var productCode = me.outerEffectivePlanEntity.productCode;
				var productName = '';
				if (productCode == 'TRANS_VEHICLE') {//汽运
					productName = pricing.outerEffectivePlan.i18n('foss.pricing.outerEffectivePlan.transVehicle');//'汽运'; 
				} else if (productCode == 'TRANS_AIRCRAFT') {//空运
					productName = pricing.outerEffectivePlan.i18n('foss.pricing.outerEffectivePlan.airFreight');//'空运';
				}
				me.getOuterPriceUpdateFormPanel().getForm().findField('productCode').setValue(productName); 
				me.getOuterPriceUpdateFormPanel().getForm().findField('productCode').valueCode = productCode;
				me.getOuterPriceUpdateFormPanel().getForm().findField('productCode').valueName = productName;
				me.getOuterPriceUpdateFormPanel().getForm().findField('outerEffectivePlanId').setValue(me.outerEffectivePlanEntity.outerEffectivePlanId); 
			}
		},
	    //价格方案批次信息 （出发地批次信息录入）
		outerPriceUpdateFormPanel:null,
	    getOuterPriceUpdateFormPanel : function(){
	    	var me = this;
	    	if(Ext.isEmpty(me.outerPriceUpdateFormPanel)){
		    		me.outerPriceUpdateFormPanel = Ext.create('Foss.pricing.outerEffectivePlan.OuterPriceUpdateFormPanel');
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
Ext.define('Foss.pricing.outerEffectivePlan.OuterPriceStopEndTimeWindow',{
		extend: 'Ext.window.Window',
		title: pricing.outerEffectivePlan.i18n('foss.pricing.outerEffectivePlan.stopSchedulePlan'),//"中止价格方案",//
		width:380,
		height:120,
		parent:null,
		outerEffectivePlanEntity:null,
		pricePlanId:null,
		closeAction: 'hide',
	    //中止
		pricePlanStopFormPanel:null,
		getPricePlanStopFormPanel : function(){
	    	if(Ext.isEmpty(this.pricePlanAddFormPanel)){
	    		this.pricePlanStopFormPanel = Ext.create('Foss.pricing.outerEffectivePlan.OuterPriceStopFormPanel');
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
Ext.define('Foss.pricing.outerEffectivePlan.OuterPriceStopFormPanel',{
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
    		var outerEffectivePlanEntity = me.up('window').outerEffectivePlanEntity;
			var pricePlanModel = new Foss.pricing.outerEffectivePlan.outerEffectivePlanModel(outerEffectivePlanEntity);
			form.updateRecord(pricePlanModel);
    		var time = form.findField('endTime').getValue();
    		
            //var endTime = new Date(time);//获取选择的时期对象  
            //endTime = Date.parse(endTime.format('yyyy-MM-dd'));
            
    		var nowDate = new Date(); 
    		if (Date.parse(time) < Date.parse(nowDate.format('yyyy-MM-dd'))) {
    			pricing.showErrorMes("中止日期不能小于当前日期！");
    			return false;
    		}
    		var outerEffectivePlanId = pricing.outerEffectivePlanId;
    		var params = {'outerEffectiveVo':{'outerEffectivePlanId':outerEffectivePlanId, 'yesOrNo': 'N', 'effectiveTime': time}};
    		var url = pricing.realPath('activeOuterEffectivePlan.action');
    		
    		//成功提示
    		var successFun = function(json){
    			pricing.showInfoMes(json.message);
    			me.up('window').close();
				var waitingProcessGrid = Ext.getCmp('T_pricing-outerEffectivePlan_content').getOuterEffectivePlanGridPanel();
				waitingProcessGrid.store.load();
    	    };
    	    
    	    //失败提示
    	    var failureFun = function(json){
    	    	if(Ext.isEmpty(json)){
    				pricing.showErrorMes(pricing.outerEffectivePlan.i18n('i18n.pricingRegion.requestTimeOut'));
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
			fieldLabel:pricing.outerEffectivePlan.i18n('foss.pricing.deadline'),//截止日期
			format:'20y-m-d',
			name:'endTime',
			allowBlank:false*/
			name : 'endTime',
			fieldLabel:pricing.outerEffectivePlan.i18n('foss.pricing.deadline'),//截止日期
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
Ext.define('Foss.pricing.outerEffectivePlan.OuterPriceImmediatelyStopEndTimeWindow',{
		extend: 'Ext.window.Window',
		title: pricing.outerEffectivePlan.i18n('foss.pricing.immediatelySupendPricePriceScheme'),//"立即中止方案",
		width:380,
		height:152,
		outerEffectivePlanEntity:null,
		closeAction: 'hide' ,
		pricePlanStopFormPanel:null,
		getOffLinePricePlanStopFormPanel : function(outerEffectivePlanEntity){
	    	if(Ext.isEmpty(this.pricePlanAddFormPanel)){
	    		this.pricePlanStopFormPanel = Ext.create('Foss.pricing.outerEffectivePlan.OuterPriceImmediatelyStopFormPanel',{
	    			outerEffectivePlanEntity:outerEffectivePlanEntity
	    		});
	    	}
	    	return this.pricePlanStopFormPanel;
	    },
	   	constructor : function(config) {
			var me = this, cfg = Ext.apply({}, config);
			me.outerEffectivePlanEntity = config.outerEffectivePlanEntity;
			me.items = [me.getOffLinePricePlanStopFormPanel(me.outerEffectivePlanEntity)];
			me.callParent(cfg);
		}
});

/**
 * 立即中止功能FormPanel
 */
Ext.define('Foss.pricing.outerEffectivePlan.OuterPriceImmediatelyStopFormPanel',{
	extend : 'Ext.form.Panel',
	layout:'column',
	outerEffectivePlanEntity:null,
	stopPricePlan:function(outerEffectivePlanId){
		var me = this;
    	var form = me.getForm();
    	if(form.isValid()){
    		var pricePlanModel = new Foss.pricing.outerEffectivePlan.outerEffectivePlanModel();
			form.updateRecord(pricePlanModel);

    		var endTime = form.findField('endTime').getValue();
			var result = Ext.Date.parse(endTime,'Y-m-d H:i:s') - Ext.Date.parse(Ext.Date.format(new Date(),'Y-m-d H:i:s'),'Y-m-d H:i:s');
			if(result<0) {
    			pricing.showErrorMes("中止日期不能小于当前日期！");
    			return false;
			}
    		var params = {'outerEffectiveVo':{'outerEffectivePlanId':outerEffectivePlanId, 'yesOrNo': 'N', 'effectiveTime': endTime}};
    		var url = pricing.realPath('activeOuterEffectivePlan.action');
    		
    		var successFun = function(json){
    			pricing.showInfoMes(json.message);
    			me.up('window').hide();
    			var waitingProcessGrid = Ext.getCmp('T_pricing-outerEffectivePlan_content').getOuterEffectivePlanGridPanel();
				waitingProcessGrid.store.load();	//outerEffectivePlan
    	    };
    	    var failureFun = function(json){
    	    	if(Ext.isEmpty(json)){
    				pricing.showErrorMes(pricing.outerEffectivePlan.i18n('i18n.pricingRegion.requestTimeOut'));
    			}else{
    				pricing.showErrorMes(json.message);
    			}
    	    };
    		pricing.requestJsonAjax(url,params,successFun,failureFun);//发送AJAX请求
    	}
	},
	constructor: function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.outerEffectivePlanEntity = config.outerEffectivePlanEntity;
		var showbeginTime = Ext.Date.format(new Date(me.outerEffectivePlanEntity.beginTime), 'Y-m-d');
		var showendTime = 	Ext.Date.format(new Date(me.outerEffectivePlanEntity.endTime), 'Y-m-d');
		me.items = [{
				width:280,
				xtype: 'displayfield',
				columnWidth:.9,
				value:pricing.outerEffectivePlan.i18n('foss.pricing.showleftTimeInfo')
					  +showbeginTime+ pricing.outerEffectivePlan.i18n('foss.pricing.showmiddleTimeInfo')
					  +showendTime  + pricing.outerEffectivePlan.i18n('foss.pricing.showstopRightEndTimeInfo')
				//'<p style="color:red">原方案生效日期为【2013-02-31】截止日期为【2013-09-11】,您是否立即中止该方案?</p>'
			},{ 
				fieldLabel :pricing.outerEffectivePlan.i18n('foss.pricing.suspendTime') ,//'中止日期',
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
				text : pricing.outerEffectivePlan.i18n('i18n.pricingRegion.determine'),//"确认",
				handler : function() {
					var outerEffectivePlanId = pricing.outerEffectivePlan.immediatelyStopId;
					me.stopPricePlan(outerEffectivePlanId);
				}
			},{
				xtype : 'button', 
				width:70,
				columnWidth:.15,
				text : pricing.outerEffectivePlan.i18n('i18n.pricingRegion.cancel'),//"取消",
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
Ext.define('Foss.pricing.outerEffectivePlan.OuterPriceImmediatelyActiveTimeWindow',{
		extend: 'Ext.window.Window',
		title: pricing.outerEffectivePlan.i18n('foss.pricing.immediatelyActiveationPriceScheme'),//"立即激活方案",
		width:380,
		height:152,
		outerEffectivePlanEntity:null,
		closeAction: 'hide' ,
		listeners:{
			beforehide:function(me){
				var form = me.down('form');
				form.getForm().reset();
			},
			beforeshow:function(me){
				var beginTime = Ext.Date.format(me.outerEffectivePlanEntity.beginTime, 'Y-m-d H:i:s')
				var endTime = Ext.Date.format(new Date(me.outerEffectivePlanEntity.endTime), 'Y-m-d H:i:s');
				var value = pricing.outerEffectivePlan.i18n('foss.pricing.showleftTimeInfo')
					  + beginTime + pricing.outerEffectivePlan.i18n('foss.pricing.showmiddleTimeInfo')
					  + endTime + pricing.outerEffectivePlan.i18n('foss.pricing.showrightEndTimeInfo');
				
				me.outerEffectivePlanEntity.showTime = value;
				me.getOffLinePricePlanImmediatelyActiveFormPanel().getForm().loadRecord(new Foss.pricing.outerEffectivePlan.outerEffectivePlanModel(me.outerEffectivePlanEntity));
				me.getOffLinePricePlanImmediatelyActiveFormPanel().getForm().findField('beginTime').setValue(beginTime);
			}
		},
		OffLinePricePlanImmediatelyActiveFormPanel:null,
		getOffLinePricePlanImmediatelyActiveFormPanel : function(outerEffectivePlanEntity){
	    	if(Ext.isEmpty(this.OffLinePricePlanImmediatelyActiveFormPanel)){
	    		this.OffLinePricePlanImmediatelyActiveFormPanel = Ext.create('Foss.pricing.outerEffectivePlan.OuterPriceImmediatelyActiveFormPanel');
	    	}
	    	this.OffLinePricePlanImmediatelyActiveFormPanel.outerEffectivePlanEntity = outerEffectivePlanEntity;
	    	return this.OffLinePricePlanImmediatelyActiveFormPanel;
	    },
	   	constructor : function(config) {
			var me = this, cfg = Ext.apply({}, config);
			me.items = [me.getOffLinePricePlanImmediatelyActiveFormPanel(me.outerEffectivePlanEntity)];
			me.callParent(cfg);
		}
});


/**
 * 立即激活Form
 */
Ext.define('Foss.pricing.outerEffectivePlan.OuterPriceImmediatelyActiveFormPanel',{
	extend : 'Ext.form.Panel',
	layout:'column',
	outerEffectivePlanEntity:null,
	activetionPricePlan:function(outerEffectivePlanId){
		var me = this;
    	var form = me.getForm();
    	if(form.isValid()){
    		var pricePlanModel = new Foss.pricing.outerEffectivePlan.outerEffectivePlanModel();
			form.updateRecord(pricePlanModel);
			pricePlanModel.set('beginTime',Ext.Date.parse(form.findField('beginTime').getValue(), 'Y-m-d H:i:s'));
    		pricePlanModel.set('id',outerEffectivePlanId);

    		var time = Ext.Date.parse(form.findField('beginTime').getValue(), 'Y-m-d H:i:s');
    		var nowDate = new Date(); 
    		if (time < nowDate) {
    			pricing.showErrorMes("生效日期不能小于系统当前时间！");
    			return false;
    		}
    		var params = {'outerEffectiveVo':{'outerEffectivePlanId':outerEffectivePlanId, 'yesOrNo': 'Y', 'effectiveTime': time}};
    		var url = pricing.realPath('activeOuterEffectivePlan.action');
    		var successFun = function(json){
    			pricing.showInfoMes(json.message);
    			me.up('window').hide();
				var waitingProcessGrid = Ext.getCmp('T_pricing-outerEffectivePlan_content').getOuterEffectivePlanGridPanel();
				waitingProcessGrid.store.load();			
    	    };
    	    var failureFun = function(json){
    	    	if(Ext.isEmpty(json)){
    				pricing.showErrorMes(pricing.outerEffectivePlan.i18n('i18n.pricingRegion.requestTimeOut'));
    			} else {
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
				/*value:pricing.outerEffectivePlan.i18n('foss.pricing.showleftTimeInfo')
					  +showbeginTime+pricing.outerEffectivePlan.i18n('foss.pricing.showmiddleTimeInfo')
					  +showendTime+pricing.outerEffectivePlan.i18n('foss.pricing.showrightEndTimeInfo')*/
				//'<p style="color:red">原方案生效日期为【'+showbeginTime+'】截止日期为【'+showendTime+'】,您是否立即生效该方案?</p>'
			},{
				fieldLabel:pricing.outerEffectivePlan.i18n('foss.pricing.availabilityDate'),//'生效日期',
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
				text : pricing.outerEffectivePlan.i18n('i18n.pricingRegion.determine'),//,"确认",
				handler : function() {
					var outerEffectivePlanId = pricing.outerEffectivePlan.immediatelyActiveId;
					me.activetionPricePlan(outerEffectivePlanId);
				}
			},{
				xtype : 'button', 
				width:70,
				columnWidth:.15,
				text : pricing.outerEffectivePlan.i18n('i18n.pricingRegion.cancel'),//"取消",
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
Ext.define('Foss.pricing.outerEffectivePlan.UploadOuterPriceform',{
	extend:'Ext.window.Window',
	title:pricing.outerEffectivePlan.i18n('foss.pricing.importPriceScheme'),
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
				fieldLabel:pricing.outerEffectivePlan.i18n('foss.pricing.pleaseSelectAttachments'),
				labelWidth:100,
				buttonText:pricing.outerEffectivePlan.i18n('foss.pricing.browse'),
				flex:3
			}]
		}];
		me.fbar = me.getFbar();
		me.callParent([cfg]);
	},
	getFbar:function(){
		var me = this;
		return [{
			text:pricing.outerEffectivePlan.i18n('foss.pricing.import'),
			xtype:'button',
			scope:me,
			handler:me.uploadFile
		},{
			text:pricing.outerEffectivePlan.i18n('i18n.pricingRegion.cancel'),
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
				pricing.showInfoMes(pricing.outerEffectivePlan.i18n('foss.pricing.allDataImportSuccess'));//全部数据导入成功！
				me.close();
			}
			var waitingProcessGrid = Ext.getCmp('T_pricing-outerEffectivePlan_content').getOuterEffectivePlanGridPanel();
			waitingProcessGrid.store.load();
		};
		var failureFn = function(json){
			if(Ext.isEmpty(json)){
				pricing.showErrorMes(pricing.outerEffectivePlan.i18n('i18n.pricingRegion.requestTimeOut'));//pricing.outerEffectivePlan.i18n('i18n.pricingRegion.requestTimeOut')
			}else{
				pricing.showErrorMes(json.message);
			}
		};
		var form = me.down('form').getForm();
		var url = pricing.realPath('importOuterEffectivePlan.action');
		form.submit({
            url: url,
            waitMsg: pricing.outerEffectivePlan.i18n('foss.pricing.uploadYourAttachment'),
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
	var queryform = Ext.create('Foss.pricing.outerEffectivePlan.OuterPriceFormPanel');//查询
	var gridPanel =	Ext.create('Foss.pricing.outerEffectivePlan.OuterPriceGridPanel');
	pricing.outerEffectivePlan.queryform = queryform;
	pricing.outerEffectivePlan.gridPanel = gridPanel;
	Ext.getCmp('T_pricing-outerEffectivePlan').add(Ext.create('Ext.panel.Panel', {
	 	id:'T_pricing-outerEffectivePlan_content',
		cls:"panelContentNToolbar",
		bodyCls:'panelContent-body',
		
		//获得查询FORM
		getQueryOuterEffectivePlanForm : function() {
			if(Ext.isEmpty(this.queryform)){
				this.queryform = Ext.create('Foss.pricing.outerEffectivePlan.OuterPriceFormPanel');//查询结果GRID
			}
			return queryform;
		},
		//获得查询结果GRID
		getOuterEffectivePlanGridPanel : function() {
			if(Ext.isEmpty(this.gridPanel)){
				this.gridPanel = Ext.create('Foss.pricing.outerEffectivePlan.OuterPriceGridPanel');//查询结果GRID
			}
			return gridPanel;
		},
		
		items : [queryform,gridPanel]
	}));
});

