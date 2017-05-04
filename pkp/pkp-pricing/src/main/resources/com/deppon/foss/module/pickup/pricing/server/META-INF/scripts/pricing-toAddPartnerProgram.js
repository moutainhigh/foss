

/**
 * 
 * @type String 激活
 */
pricing.yes = 'Y';
pricing.no = 'N';

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
 * @author Foss-ZouShengli
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
			Ext.Msg.alert(pricing.toAddPartnerProgram.i18n('foss.pricing.promptMessage'),result.message);
		}
	});
};

/**
 * 合伙人到达加收明细模型
 */
Ext.define('Foss.pricing.toAddPartnerProgram.toAddPartnerProgramDetailDto', {
    extend: 'Ext.data.Model',
    fields : [{
    	name : 'toAddPartnerProgramid', //合伙人到达加收ID
        type : 'string'
    },{
        name : 'planName', //名称
        type : 'string'
    },{
    	name : 'orgCode',//目的站
        type : 'string'
    },{
        name : 'heavyPrice'//重货价格
    },{
        name : 'lightPrice'//轻货价格 
    },{
        name : 'lowestPrice'//最低一票
    }]
});

//创建一个查询货量枚举store
Ext.define('Foss.pricing.toAddPartnerProgram.Store.ToDoTypeStore',{
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
 * 合伙人到达加收方案批次model
 */
Ext.define('Foss.pricing.toAddPartnerProgram.toAddPartnerProgramModel', {
    extend: 'Ext.data.Model',
    fields : [
    {
        name : 'toAddPartnerProgramid',//ID
        type : 'string'
    },{
      name : 'planName',//方案名称
        type : 'string'
    },{
      name : 'name',//目的站
        type : 'string'
    },{
      name : 'orgCode',
        type : 'string'
    },{
      name : 'transportFlag',
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
        name : 'modifyTime',
        type : 'date',
        defaultValue : null,
        convert : pricing.changeLongToDate
    },{
        name : 'modifyUserCode',
        type : 'string'
    },{
        name : 'active',
        type : 'string'
    },{
        name : 'version',
        type : 'string'
    },{
        name : 'heavyPrice',
        type : 'float'
    },{
        name : 'lightPrice',
        type : 'float'
    },{
        name : 'lowestPrice',
        type : 'float'
    },{
        name : 'showTime',
        type : 'string'
    },{
        name : 'networkModelName',
        type : 'string'
    }]
});

/**
 *合伙人到达加收方案store
 */
Ext.define('Foss.pricing.toAddPartnerProgram.toAddPartnerProgramPlanStore',{
	extend: 'Ext.data.Store',
	model: 'Foss.pricing.toAddPartnerProgram.toAddPartnerProgramModel',
	pageSize:20,
	proxy: {
		type : 'ajax',
		actionMethods:'POST',
		url: pricing.realPath('querytoAddPartnerProgramVoBatchInfo.action'),
		reader : {
			type : 'json',
			root : 'toAddPartnerProgramVo.toAddPartnerProgramEntityList',
			totalProperty : 'totalCount',
			successProperty: 'success'
		}
	},
	listeners: {
		beforeload: function(store, operation, eOpts){
			var n = pricing.toAddPartnerProgram.queryform.getValues();
			   if(Ext.isEmpty(n.startDate) ||  Ext.isEmpty(n.endDate)){
				   pricing.showWoringMessage('请选择时间日期');
				   return false;
			   }
			Ext.apply(operation,{
				params : {
					'toAddPartnerProgramVo.toAddPartnerProgramCondtionDto.orgCode' : 	  n.orgCode,
					'toAddPartnerProgramVo.toAddPartnerProgramCondtionDto.startDate'	: n.startDate,
					'toAddPartnerProgramVo.toAddPartnerProgramCondtionDto.endDate'	: n.endDate,
					'toAddPartnerProgramVo.toAddPartnerProgramCondtionDto.active'	: n.active
				}
			});			
		}
	}
});
/**
 * 合伙人到达加收方案明细store
 */
Ext.define('Foss.pricing.toAddPartnerProgram.toAddPartnerProgramDeatilStore',{
	extend: 'Ext.data.Store',
	model: 'Foss.pricing.toAddPartnerProgram.toAddPartnerProgramDetailDto',
	pageSize:10,
	proxy: {
		type : 'ajax',
		actionMethods:'POST',
		url: pricing.realPath('querytoAddPartnerProgramDetailInfo.action'),
		reader : {
			type : 'json',
			root : 'toAddPartnerProgramVo.toAddPartnerProgramDetailDtoList',
			totalProperty : 'totalCount',
			successProperty: 'success'
		}
	}
});


/**
 * 合伙人到达加收方案批次查询form表单
 */
Ext.define('Foss.pricing.toAddPartnerProgram.toAddPartnerProgramFormPanel', {
	extend : 'Ext.form.Panel',
	  title: pricing.toAddPartnerProgram.i18n('i18n.pricingRegion.searchCondition'),
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
		xtype : 'commonTwolevelvehagencydeptselector',
		name:'orgCode',
	    fieldLabel:pricing.toAddPartnerProgram.i18n('foss.pricing.destinationLinename'),//目的站网点名称
	    columnWidth: 0.25
	  },{
			xtype : 'rangeDateField',
			fieldLabel : '日期时间',
			columnWidth : 1/2,
			/*dateType: 'datetimefield_date97',*/
			dateRange : 31,
			fromName : 'startDate',
			fromValue : Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()), 'Y-m-d'),
			toValue : Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()), 'Y-m-d'),
			toName : 'endDate',
			allowBlank : false,
			disallowBlank : true
		},{
	    xtype:'combo',
	    displayField:'name',
	    valueField:'code',
	    queryMode:'local',
	    triggerAction:'all',
	    editable:false,
	    name: 'active',
	    fieldLabel: pricing.toAddPartnerProgram.i18n('foss.pricing.dataStatus'),//处理状态,
	    columnWidth: 0.25,
	    value:'',
	    store:Ext.create('Foss.pricing.toAddPartnerProgram.Store.ToDoTypeStore',
	    {
	      data:{
	           'items':[
	          {'code':'','name':pricing.toAddPartnerProgram.i18n('i18n.pricingRegion.all')},
	          {'code':'N','name':pricing.toAddPartnerProgram.i18n('i18n.pricingRegion.unActive')},
	          {'code':'Y','name':pricing.toAddPartnerProgram.i18n('foss.pricing.active')}	          
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
				text : pricing.toAddPartnerProgram.i18n('foss.pricing.reset'),//重置
				handler : function() {
					pricing.toAddPartnerProgram.queryform.getForm().reset();
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
		        disabled: !pricing.toAddPartnerProgram.isPermission('toAddPartnerProgram/toAddPartnerProgramQueryButton'),
		        hidden: !pricing.toAddPartnerProgram.isPermission('toAddPartnerProgram/toAddPartnerProgramQueryButton'),
		        handler : function() {
		        	var n = pricing.toAddPartnerProgram.queryform.getValues();
					n.endDate = Ext.Date.add(new Date(n.endDate), Ext.Date.DAY, 1);
					n.startDate = Ext.Date.add(new Date(n.startDate), Ext.Date.DAY, 1);
					if(!Ext.isEmpty(n.startDate) && !Ext.isEmpty(n.endDate)){
						if(n.startDate>n.endDate){
							pricing.showWoringMessage(pricing.toAddPartnerProgram.i18n('foss.pricing.deadlineForInputGreaterThanEfectiveDate'));//截止日期要大于起始日期！
			    			return false;
						}
						
						if(n.endDate-n.startDate>31*24*60*60*1000){
							pricing.showWoringMessage(pricing.toAddPartnerProgram.i18n('foss.pricing.inempDiscount.startAndEndDateDiffCannotExceedSomeDays'));
							return false;
						}
					}
					
		        	
		        	var grid = Ext.getCmp('T_pricing-toAddPartnerProgram_content').getOffLinePricePlanGridPanel();
		        	grid.getPagingToolbar().moveFirst();
		      },
			  plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin', {
					  //设定间隔秒数,如果不设置，默认为5秒
					  seconds: 5
					})
		    }]
	  }]
});

//-------------------查询详情------------------
/**
 * 合伙人到达加收方案明细信息查看WINDOW
 */
Ext.define('Ext.pricing.toAddPartnerProgram.OffLinePricePlanDetailShowWindow',{
	extend : 'Ext.window.Window',
	title: pricing.toAddPartnerProgram.i18n('foss.pricing.thePriceOfTheProgramDetailQuery'),//始发区域与目的区域时效方案明细查询
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
		}
	},
    //明细信息查询-FORM
	queryPricePlanDetailForm:null,
    getQueryPricePlanDetailForm:function(){
    	if(Ext.isEmpty(this.queryPricePlanDetailForm)){
    		this.queryPricePlanDetailForm = Ext.create('Foss.pricing.toAddPartnerProgram.toAddPartnerProgramDetailForm');
    	}
    	return this.queryPricePlanDetailForm;
    },
    //明细信息结果集
    pricePlanDetailShowGridPanel:null,
    getPricePlanDetailShowGridPanel:function(){
    	if(Ext.isEmpty(this.pricePlanDetailShowGridPanel)){
    		this.pricePlanDetailShowGridPanel = Ext.create('Foss.pricing.toAddPartnerProgram.toAddPartnerProgramDetailShowGridPanel');
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

pricing.toAddPartnerProgram.immediatelyActiveId = null;
pricing.toAddPartnerProgram.immediatelyStopId = null;


/**
 * 合伙人到达加收批次列表gird
 */
Ext.define('Foss.pricing.toAddPartnerProgram.toAddPartnerProgramGridPanel',{
	extend: 'Ext.grid.Panel',
	title : pricing.toAddPartnerProgram.i18n('i18n.pricingRegion.searchResults'),//查询结果
	emptyText: pricing.toAddPartnerProgram.i18n('foss.pricing.theQueryResultIsEmpty'),//查询结果为空
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
			this.addPricePlanWindow = Ext.create('Foss.pricing.toAddPartnerProgram.toAddPartnerProgramAddWindow');
			this.addPricePlanWindow.parent = this;
		}
		return this.addPricePlanWindow;
	},
	
	updatePricePlanWindow : null,
	//获取修改价格方案窗口
	getUpdatePricePlanWindow : function(){
		if(Ext.isEmpty(this.updateStandardWindow)){
			this.updatePricePlanWindow = Ext.create('Foss.pricing.toAddPartnerProgram.toAddPartnerProgramUpdateWindow');
			//设置器父元素
			this.updatePricePlanWindow.parent = this;
		}
		return this.updatePricePlanWindow;
	},
	
	
	//中止方案弹出日期选择
	stopPricePlanWindow:null,
	getStopPricePlanWindow: function(){
		if(Ext.isEmpty(this.stopPricePlanWindow)){
			this.stopPricePlanWindow = Ext.create('Foss.pricing.toAddPartnerProgram.toAddPartnerProgramStopEndTimeWindow');
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
			this.immediatelyActivePricePlanWindow = Ext.create('Foss.pricing.toAddPartnerProgram.toAddPartnerProgramImmediatelyActiveTimeWindow');
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
			this.immediatelyStopPricePlanWindow = Ext.create('Foss.pricing.toAddPartnerProgram.toAddPartnerProgramImmediatelyStopEndTimeWindow',{
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
			this.pricePlanStore = Ext.create('Foss.pricing.toAddPartnerProgram.toAddPartnerProgramPlanStore');
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
	 		pricing.showWoringMessage(pricing.toAddPartnerProgram.i18n('foss.pricing.pleaseSelectOneDeleteOperation'));
			return;
	 	}
	 	//过滤草稿状态数据进行删除
	 	for(var i = 0;i<selections.length;i++){
			if(selections[i].get('active')==pricing.yes){
				pricing.showWoringMessage(pricing.toAddPartnerProgram.i18n('foss.pricing.pleaseChooseToNotActivateTheDataToBeBeleted'));
				return;
			}
		}
		//是否要删除这些汽运价格方案？
		pricing.showQuestionMes(pricing.toAddPartnerProgram.i18n('foss.pricing.thesePartnerPriceProgramYouWantToRemove'),function(e){
			if(e=='yes'){
				//汽运价格方案
				var idList = new Array();
				for(var i = 0 ; i<selections.length ; i++){
					idList.push(selections[i].get('toAddPartnerProgramid'));
				}
				var params = {'toAddPartnerProgramVo':{'toAddPartnerProgramids':idList}};
				var successFun = function(json){
					pricing.showInfoMes(json.message);
					me.getPagingToolbar().moveFirst();
				};
				var failureFun = function(json){
					if(Ext.isEmpty(json)){
						pricing.showErrorMes(pricing.toAddPartnerProgram.i18n('foss.pricing.requestTimedOut'));//请求超时
					}else{
						pricing.showErrorMes(json.message);
					}
				};
				var url = pricing.realPath('deletetoAddPartnerProgram.action');
				pricing.requestJsonAjax(url,params,successFun,failureFun);
			}
		});
	},
	
	//激活时效方案
    activePricePlan:function(){
    	var me = this;
		var pricePlans = new Array();
		var orgCodePlans = new Array();
		//获取选中的数据
		var selections = me.getSelectionModel().getSelection();
		if(selections.length<1){
			pricing.showErrorMes(pricing.toAddPartnerProgram.i18n('foss.pricing.pleaseChooseThePriceYouWantToActivateTheProgram'));//请选择要激活的价格方案！
			return;
		}
		var toAddPartnerProgramPlans = new Array();
		//过滤草稿状态数据进行删除
	 	for(var i = 0;i<selections.length;i++){
			if(selections[i].get('active')==pricing.yes){
				pricing.showWoringMessage(pricing.toAddPartnerProgram.i18n('foss.pricing.pleaseSelectNotActivateProgramDataForActivation'));
				return;
			}
			pricePlans.push(selections[i].get('toAddPartnerProgramid'));
			toAddPartnerProgramPlans.push(selections[i].data);
			for(var j = 0;j<orgCodePlans.length;j++){
				if(orgCodePlans[j]==selections[i].get('orgCode')){
					pricing.showWoringMessage('批量激活时,同一目的站不能选着多条');
					return;
				}
			}
			orgCodePlans.push(selections[i].get('orgCode'));
			var d = new Date(selections[i].get('beginTime'));
		    var nowDate = new Date(); 
    		if (d < nowDate) {
    			pricing.showErrorMes("生效日期不能小于系统当前时间！");
    			return false;
    		}
			
		}
		
		if(pricePlans.length<1){
			pricing.showErrorMes(pricing.toAddPartnerProgram.i18n('foss.pricing.pleaseSelectAtLeastOneInactivePriceProgram'));//请至少选择一条未激活的价格方案！
			return;
		}
		
		//是否要激活这些合伙人到达加收方案？
		pricing.showQuestionMes('是否要激活这些合伙人到达加收方案?',function(e){
			if(e=='yes'){
				var params = {'toAddPartnerProgramVo':{'toAddPartnerProgramids':pricePlans, 'yesOrNo': 'Y', toAddPartnerProgramEntityList: toAddPartnerProgramPlans}};
				var successFun = function(json){
					pricing.showInfoMes(json.message);
					me.getPagingToolbar().moveFirst();
				};
				var failureFun = function(json){
					if(Ext.isEmpty(json)){
						pricing.showErrorMes(pricing.toAddPartnerProgram.i18n('i18n.pricingRegion.requestTimeOut'));//请求超时
					}else{
						pricing.showErrorMes(json.message);
					}
				};
				var url = pricing.realPath('updatetoAddPartnerProgramActiveById.action');
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
			this.uploadPriceform = Ext.create('Foss.pricing.toAddPartnerProgram.UploadtoAddPartnerProgramform');	
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
			this.queryPricePlanForm  = Ext.create('Foss.pricing.toAddPartnerProgram.toAddPartnerProgramFormPanel')
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
			text : pricing.toAddPartnerProgram.i18n('i18n.pricingRegion.num')//序号  
		},{
			text : pricing.toAddPartnerProgram.i18n('i18n.pricingRegion.opra'),//操作
			align : 'center',
			xtype : 'actioncolumn',
			items: [{
				iconCls:'deppon_icons_edit',
				tooltip: pricing.toAddPartnerProgram.i18n('foss.pricing.toAmendTheProposal'), 
				disabled: !pricing.toAddPartnerProgram.isPermission('toAddPartnerProgram/toAddPartnerProgramUpdateButton'),
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
                	var params = {'toAddPartnerProgramVo':{'toAddPartnerProgramid':record.get('toAddPartnerProgramid')}};
    				var successFun = function(json){
						updateWindow.pricePlanEntity = json.toAddPartnerProgramVo.toAddPartnerProgramEntity;
						pricing.pricePlanId = json.toAddPartnerProgramVo.toAddPartnerProgramEntity.toAddPartnerProgramid;
    					updateWindow.show();
    				};
    				var failureFun = function(json){
    					if(Ext.isEmpty(json)){
    						pricing.showErrorMes(pricing.toAddPartnerProgram.i18n('foss.pricing.requestTimedOut'));//请求超时
    					}else{
    						pricing.showErrorMes(json.message);
    					}
    				};
    				var url = pricing.realPath('selectByToId.action');
    				pricing.requestJsonAjax(url,params,successFun,failureFun);
				}
			},{
				iconCls:'deppon_icons_end',
				tooltip: pricing.toAddPartnerProgram.i18n('foss.pricing.stop'), 
				disabled: !pricing.toAddPartnerProgram.isPermission('toAddPartnerProgram/toAddPartnerProgramStopButton'),
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
					var pricePlanId = record.get('toAddPartnerProgramid');
					var endTime = record.get('endTime');

                	var stopPricePlanWindow =  grid.up().getStopPricePlanWindow();
                	
                	stopPricePlanWindow.pricePlanEntity = record.data;
					pricing.pricePlanId = pricePlanId;
                	stopPricePlanWindow.show();

				}
			}]
		},{
			hide:true,
			text: 'ID',
			hidden: true,
	        dataIndex: 'toAddPartnerProgramid'
		},{
			width: 140,
			text: pricing.toAddPartnerProgram.i18n('foss.pricing.scenarioName'),//方案名称
	        dataIndex: 'planName'
		},{
			width: 140,
			hidden: true,
			text: "目的站编号",
	        dataIndex: 'orgCode'
		},{
			width: 140,
			text:  pricing.toAddPartnerProgram.i18n('foss.pricing.destinationLinename'),
	        dataIndex: 'name'
		},{
			width: 70,
			text: "运输类型",
	        dataIndex: 'transportFlag',
	        renderer: function(value){
				if(value=='0') {//汽运
					return '汽运';			
				} else if(value=='1') {//空运
					return '空运';
				} else {
					return null;
				}
			}
		},{
			width: 140,
			text: '网点模式',
	        dataIndex: 'networkModelName'
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
			dataIndex : 'heavyPrice',
			text: "重货费率"
	    },{
	    	dataIndex : 'lightPrice',
			text: "轻货费率"
	    },{
	    	dataIndex : 'lowestPrice',
			text: "最低一票"
	    },{
			text: "修改时间",
			width: 140,
	        dataIndex: 'modifyTime',
	        renderer:function(value){
				return Ext.Date.format(new Date(value), 'Y-m-d H:m:s');
			}
		},{
			text: "修改人",
			width: 80,
	        dataIndex: 'modifyUserCode'
		}];
		me.tbar = [
		{
			text : "新建方案",
			disabled: !pricing.toAddPartnerProgram.isPermission('toAddPartnerProgram/toAddPartnerProgramAddButton'),
			hidden: !pricing.toAddPartnerProgram.isPermission('toAddPartnerProgram/toAddPartnerProgramAddButton'),
			handler :function(){
				var addWindow = me.getAddpricePlanWindow();
				addWindow.isUpdate = false;
				addWindow.show();
				
			} 
		},'-', {
			text : pricing.toAddPartnerProgram.i18n('foss.pricing.activationProgram'),//激活方案
			disabled: !pricing.toAddPartnerProgram.isPermission('toAddPartnerProgram/toAddPartnerProgramActiveButton'),
			hidden: !pricing.toAddPartnerProgram.isPermission('toAddPartnerProgram/toAddPartnerProgramActiveButton'),
			handler :function(){
				me.activePricePlan();
			} 
		},'-', {
			text : pricing.toAddPartnerProgram.i18n('foss.pricing.deleteProgram'),
			disabled: !pricing.toAddPartnerProgram.isPermission('toAddPartnerProgram/toAddPartnerProgramDeleteButton'),
			hidden: !pricing.toAddPartnerProgram.isPermission('toAddPartnerProgram/toAddPartnerProgramDeleteButton'),
			handler :function(){
				me.deletePricePlan();
			} 
		},'-',{
			text : pricing.toAddPartnerProgram.i18n('foss.pricing.immediatelyActivationProgram'),//'立即激活',
			disabled:!pricing.toAddPartnerProgram.isPermission('toAddPartnerProgram/toAddPartnerProgramImmediatelyActiveButton'),
			hidden:!pricing.toAddPartnerProgram.isPermission('toAddPartnerProgram/toAddPartnerProgramImmediatelyActiveButton'),
			handler :function(){
				var selection = Ext.getCmp('T_pricing-toAddPartnerProgram_content').getOffLinePricePlanGridPanel().getSelectionModel().getSelection()[0];
				pricing.toAddPartnerProgram.immediatelyActiveId = selection.get('toAddPartnerProgramid');
				me.immediatelyActiveOffLinePricePlan();
			} 
		},'-', {
			text : pricing.toAddPartnerProgram.i18n('foss.pricing.immediatelyStopProgram'),//'立即中止',
			disabled:!pricing.toAddPartnerProgram.isPermission('toAddPartnerProgram/toAddPartnerProgramImmediatelyStopButton'),
			hidden:!pricing.toAddPartnerProgram.isPermission('toAddPartnerProgram/toAddPartnerProgramImmediatelyStopButton'),
			handler :function(grid, rowIndex, colIndex){
				var selection = Ext.getCmp('T_pricing-toAddPartnerProgram_content').getOffLinePricePlanGridPanel().getSelectionModel().getSelection()[0];
				pricing.toAddPartnerProgram.immediatelyStopId = selection.get('toAddPartnerProgramid');
				me.immediatelyStopOffLinePricePlan();
			} 
		}
		/*导入导出功能*/
		/*,'-', {
			text : pricing.toAddPartnerProgram.i18n('foss.pricing.export'),
			disabled: !pricing.toAddPartnerProgram.isPermission('toAddPartnerProgram/toAddPartnerProgramExportButton'),
			hidden: !pricing.toAddPartnerProgram.isPermission('toAddPartnerProgram/toAddPartnerProgramExportButton'),
			handler :function(){
				//var queryForm = me.getQueryPricePlanForm();
				var queryForm = pricing.toAddPartnerProgram.queryform;
				var pricePlanExport = '';
				
				var orgCode = queryForm.getForm().findField('orgCode').getValue(); // 目的站
				var startDate = queryForm.getForm().findField('startDate').getValue(); //运输类型
				var endDate = queryForm.getForm().findField('endDate').getValue(); //运输类型
				var active = queryForm.getForm().findField('active').getValue(); //数据状态
				
				if(!Ext.isEmpty(orgCode)){
					pricePlanExport = 'toAddPartnerProgramVo.toAddPartnerProgramCondtionDto.orgCode='+orgCode;
				}
			
				pricePlanExport = pricePlanExport + '&' + 'toAddPartnerProgramVo.toAddPartnerProgramCondtionDto.startDate='+startDate;
				pricePlanExport = pricePlanExport + '&' + 'toAddPartnerProgramVo.toAddPartnerProgramCondtionDto.active='+active;
				pricePlanExport = pricePlanExport + '&' + 'toAddPartnerProgramVo.toAddPartnerProgramCondtionDto.endDate='+endDate;
				if(!Ext.isEmpty(deptRegionCode)){
					if(!Ext.isEmpty(pricePlanExport)){
						pricePlanExport = pricePlanExport+'&'+'priceManageMentVo.pricePlanEntity.deptRegionCode='+deptRegionCode;
					}else{
						pricePlanExport = 'priceManageMentVo.pricePlanEntity.deptRegionCode='+deptRegionCode;
					}
				}
				var url = pricing.realPath('exporttoAddPartnerProgram.action');
				if(!Ext.isEmpty(pricePlanExport)){
					url = url+'?'+pricePlanExport;
				}
				window.location=url;
				pricePlanExport = '';
 			} 
		},'-', {
			text : pricing.toAddPartnerProgram.i18n('foss.pricing.import'),
			disabled: !pricing.toAddPartnerProgram.isPermission('toAddPartnerProgram/toAddPartnerProgramImportButton'),
			hidden: !pricing.toAddPartnerProgram.isPermission('toAddPartnerProgram/toAddPartnerProgramImportButton'),
			handler :function(){
			 	me.getUploadPriceform().show();
			} */
		];
		pricing.pagingBar = me.bbar;
		me.callParent([cfg]);
	}
 	
});
/**
 * 合伙人到达加收方案明细查询表单
 */
Ext.define('Foss.pricing.toAddPartnerProgram.toAddPartnerProgramDetailForm', {
	extend : 'Ext.form.Panel',
	title: pricing.toAddPartnerProgram.i18n('i18n.pricingRegion.searchCondition'),
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
			fieldLabel : pricing.toAddPartnerProgram.i18n('foss.pricing.flightCategory'),
			displayField : 'valueName',
			valueField : 'valueCode',
			queryMode : 'local',
			triggerAction : 'all',
			editable : false,
			flightRecord: null,
			store : FossDataDictionary.getDataDictionaryStore('AIR_FLIGHT_TYPE', 'Foss_baseinfo_flight_FlightSortStore_Id', {
				'valueCode' : '',
				'valueName' : pricing.toAddPartnerProgram.i18n('i18n.pricingRegion.all')
			}),
			value : ''
		},{
			name: 'arrvRegionId',
			valueField: 'id',
	        fieldLabel:pricing.toAddPartnerProgram.i18n('foss.pricing.destinationArea'),//目的区域
	        xtype : 'commonpriceregionselector',
	        airPriceFlag :'Y'
		},{
			xtype : 'container',
			columnWidth : .2,
			margin : '0 0 0 0',
			items : [{
						xtype : 'button', 
						colspan : 1,
						text : pricing.toAddPartnerProgram.i18n('foss.pricing.reset'),//重置
						handler : function() {
							this.getForm().reset();
						}
					},{
					xtype : 'button', 
					width:70,
					text : pricing.toAddPartnerProgram.i18n('i18n.pricingRegion.search'),
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
 * 合伙人到达加收方案明细列表
 */
Ext.define('Foss.pricing.toAddPartnerProgram.toAddPartnerProgramDetailShowGridPanel', {
	extend: 'Ext.grid.Panel',
	title :pricing.toAddPartnerProgram.i18n('i18n.pricingRegion.searchResults'),//查询结果
	frame: true,
	height :450,
    sortableColumns:false,
    enableColumnHide:false,
    enableColumnMove:false,
	stripeRows : true, // 交替行效果
	selType : "rowmodel", // 选择类型设置为：行选择
	emptyText: pricing.toAddPartnerProgram.i18n('foss.pricing.theQueryResultIsEmpty'),//查询结果为空
	
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
			text : pricing.toAddPartnerProgram.i18n('i18n.pricingRegion.num')//序号
		},{
			text :pricing.toAddPartnerProgram.i18n('foss.pricing.destinationStation'),//目的站
			dataIndex : 'arrvRegionName',
			flex:2
		},{
			text :pricing.toAddPartnerProgram.i18n('foss.pricing.flightType'),//航班类型
			dataIndex : 'flightTypeName',
			flex:2
		},{
			text :pricing.toAddPartnerProgram.i18n('foss.pricing.cargoType'),//货物类型
			dataIndex : 'goodsTypeName',
			flex:2
		},{
			text :pricing.toAddPartnerProgram.i18n('foss.pricing.heavyGoodsPrices'),//重货价格
			dataIndex : 'heavyPrice',
			flex:2
		},{
			text : pricing.toAddPartnerProgram.i18n('foss.pricing.theLowestVotes'),//最低一票
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
			text : pricing.toAddPartnerProgram.i18n('foss.pricing.remark'),//备注
			dataIndex : 'remark',
			flex:2
		}];
		me.store = Ext.create('Foss.pricing.toAddPartnerProgram.toAddPartnerProgramDeatilStore',{
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
		   			text : pricing.toAddPartnerProgram.i18n('foss.pricing.export'),
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
		   				var url = pricing.realPath('exporttoAddPartnerProgramDetail.action');
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
 * 合伙人到达加收方案明细form
 */
Ext.define('Foss.pricing.toAddPartnerProgram.toAddPartnerProgramDetailForm', {
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
	        fieldLabel:pricing.toAddPartnerProgram.i18n('foss.pricing.destinationArea'),//目的区域
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
			fieldLabel : pricing.toAddPartnerProgram.i18n('foss.pricing.flightCategory'),
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
	        fieldLabel: pricing.toAddPartnerProgram.i18n('foss.pricing.cargoType'),//货物类型
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
	        fieldLabel: pricing.toAddPartnerProgram.i18n('foss.pricing.heavyGoodsPrices'),//重货价格
	        xtype : 'numberfield'
		
		},{
			name: 'minimumOneTicket',
			allowBlank:false,
			decimalPrecision:2,
			step:0.01,
		    maxValue: 999999.99,
		    minValue:0,
	        fieldLabel: pricing.toAddPartnerProgram.i18n('foss.pricing.theLowestVotes'),//最低一票
	        xtype : 'numberfield'
		},{
			 xtype:'radiogroup',
			 vertical:true,
			 allowBlank:false,
			 name:'centralizePickup',
			 fieldLabel:pricing.toAddPartnerProgram.i18n('foss.pricing.whetherorNot'),
			 items:[{
			 	 xtype:'radio',
			     boxLabel:pricing.toAddPartnerProgram.i18n('i18n.pricingRegion.ye'),//是
			     name:'centralizePickup',		 
			     inputValue:'Y'
		     },{
		    	 xtype:'radio',
			     boxLabel:pricing.toAddPartnerProgram.i18n('i18n.pricingRegion.no'),//否
			     name:'centralizePickup',
			     inputValue:'N'
			     }]
		},{
			xtype: 'textareafield',
			width:300,
		    fieldLabel: pricing.toAddPartnerProgram.i18n('foss.pricing.remark'),//备注
		    maxLength:200,
	        name:'remark'
		}];
		me.callParent([cfg]);
	}
});

/**
 * 合伙人到达加收方案明细信息新增弹出窗口window
 */
Ext.define('Foss.pricing.toAddPartnerProgram.toAddPartnerProgramDetailWindow',{
	extend : 'Ext.window.Window',
	title: pricing.toAddPartnerProgram.i18n('foss.pricing.aBreakdownOfNew'),//明细信息新增
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
    		this.pricePlanDetailForm = Ext.create('Foss.pricing.toAddPartnerProgram.toAddPartnerProgramDetailForm');
    	}
    	return this.pricePlanDetailForm;
    },
    
    //想GRID中设置数据
    commitPricePlanDetail:function(grid){
    	var me = this;
    	//得到明细form
    	var form = me.getPricePlanDetailForm().getForm();
    	if(form.isValid()){
    		var pricePlanDetailDto = new Foss.pricing.toAddPartnerProgram.OffLinePricePlanDetailDto();
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
			var url = pricing.realPath('addToAddPartnerProgramDetail.action');
			
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
					pricing.showErrorMes(pricing.toAddPartnerProgram.i18n('i18n.pricingRegion.requestTimeOut'));
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
			text : pricing.toAddPartnerProgram.i18n('i18n.pricingRegion.cancel'),//取消
			handler :function(){
				me.close();
			} 
		},{
			text : pricing.toAddPartnerProgram.i18n('foss.pricing.reset'),//重置
			margin:'0 185 0 0',
			handler :function(){
				me.getPricePlanDetailForm().getForm().reset();
			} 
		},{
			text : "保存",//提交
			cls:'yellow_button',
			handler :function(){
				me.commitPricePlanDetail(config.grid);
			} ,
			  plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin', {
				  //设定间隔秒数,如果不设置，默认为5秒
				  seconds: 5
				})
		}];
		me.callParent([cfg]);
	}
});




/**
 * 修改价格明细信息Window
 */
Ext.define('Foss.pricing.toAddPartnerProgram.ModifyOffLinePricePlanDetailWindow',{
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
			me.getPricePlanDetailForm().getForm().loadRecord(new Foss.pricing.toAddPartnerProgram.OffLinePricePlanDetailDto(me.pricePlanDetailDto));
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
    		this.pricePlanDetailForm = Ext.create('Foss.pricing.toAddPartnerProgram.toAddPartnerProgramDetailForm');
    	}
    	return this.pricePlanDetailForm;
    },
     	
    updatePriceDetailPlan:function(grid){
    	var me = this;
    	//得到明细form
    	var form = me.getPricePlanDetailForm().getForm();
    	if(form.isValid()){
    		 	var pricePlanDetailDto = new Foss.pricing.toAddPartnerProgram.OffLinePricePlanDetailDto(me.pricePlanDetailDto);
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
					pricing.showErrorMes(pricing.toAddPartnerProgram.i18n('i18n.pricingRegion.requestTimeOut'));
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
			text : pricing.toAddPartnerProgram.i18n('i18n.pricingRegion.cancel'),//取消
			handler :function(){
				me.close();
			} 
		},{
			text : pricing.toAddPartnerProgram.i18n('foss.pricing.reset'),//重置
			margin:'0 185 0 0',
			handler :function(){
				me.getPricePlanDetailForm().getForm().reset();
			} 
		},{
			text : "修改",//提交
			cls:'yellow_button',
			handler :function(){
				me.updatePriceDetailPlan(config.grid);
			} ,
			  plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin', {
				  //设定间隔秒数,如果不设置，默认为5秒
				  seconds: 5
				})
		}];
		me.callParent([cfg]);
	}
});

/**
 * 合伙人到达加收方案批次信息录入form
 */
Ext.define('Foss.pricing.toAddPartnerProgram.toAddPartnerProgramAddFormPanel',{
	extend : 'Ext.form.Panel',
	title: "到达加收方案信息",
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
			name: 'toAddPartnerProgramid',
			hidden : true
		},{
			name: 'planName',
			allowBlank:false,
			columnWidth: .5,
			maxLength : 100,
	        fieldLabel:pricing.toAddPartnerProgram.i18n('foss.pricing.scenarioName')//方案名称
		},{			
			name : 'orgCode',
			allowBlank:false,
			xtype : 'commonTwolevelvehagencydeptselector',
			fieldLabel : pricing.toAddPartnerProgram.i18n('foss.pricing.destinationLinename'),//注意一下，取的是网点信息
			columnWidth: .5,
			listeners:{
				'select':function(combo,records,eOpts){
					if(records.length>0){
						var form = this.up('form').getForm();             
						var record = form.findField("orgCode");
						form.findField('networkModelName').setValue(record.displayTplData[0].networkModel);
					}
				}
			}
		},{
			xtype:'combo',
		    displayField:'name',
		    allowBlank:false,
		    valueField:'code',
		    queryMode:'local',
		    triggerAction:'all',
		    editable:false,
		    name: 'transportFlag',
		    fieldLabel: '运输类型',//处理状态,
		    columnWidth: .5,
		    value:'',
		    store:Ext.create('Foss.pricing.toAddPartnerProgram.Store.ToDoTypeStore',
		    {
		      data:{
		           'items':[
		          {'code':'0','name':'汽运'}
		             
		         ]
		      }
		    })
		   },{
			xtype:'datefield',
			allowBlank:false,
			fieldLabel:pricing.toAddPartnerProgram.i18n('foss.pricing.availabilityDate'),//生效日期
			format:'20y-m-d',
			name:'beginTime',
			columnWidth: .5
		},{
			name: 'networkModelName',
			readOnly: true,
			allowBlank:false,
			columnWidth: .5,
	        fieldLabel:'网点模式'
		},{
			xtype: 'numberfield',
			anchor: '100%',
			minValue: 0,
			step:0.01,
		    maxValue: 999999.99,
			allowBlank:false,
			fieldLabel:'重货价格',
			name:'heavyPrice',
			columnWidth: .5
		},{
			xtype: 'numberfield',
			anchor: '100%',
			minValue: 0,
			step:0.01,
			maxValue: 999999.99,
			allowBlank:false,
			fieldLabel:'轻货价格',
			name:'lightPrice',
			columnWidth: .5
		},{
			xtype: 'numberfield',
			anchor: '100%',
			minValue: 0,
			step:0.01,
			maxValue: 999999.99,
			allowBlank:false,
			fieldLabel:'最低一票',
			name:'lowestPrice',
			//allowDecimals : false,//只能为数字，不能为小数
			columnWidth: .5
		}],
	/**修改合伙人到达加收方案**/
	commitUpdatePricePlan:function(){
    	var me = this;
    	var baseForm = me.getForm();
    	if(baseForm.isValid()){//校验form是否通过校验
    		var pricePlanEntity = me.up('window').pricePlanEntity;
    		var pricePlanModel = new Foss.pricing.toAddPartnerProgram.toAddPartnerProgramModel(pricePlanEntity);
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
    		var params = {'toAddPartnerProgramVo':{'toAddPartnerProgramEntity':pricePlanModel.data}};//组织需要修改的数据
    		var successFun = function(json){
				pricing.showInfoMes(json.message);//提示新增成功
			};
			var failureFun = function(json){
				if(Ext.isEmpty(json)){
					pricing.showErrorMes(pricing.toAddPartnerProgram.i18n('foss.pricing.requestTimedOut'));//请求超时
				}else{
					pricing.showErrorMes(json.message);//提示失败原因
				}
			};
			var url = pricing.realPath('updatetoAddPartnerProgram.action');//请求价格方案修改
			pricing.requestJsonAjax(url,params,successFun,failureFun);//发送AJAX请求
    	}
	 },
	
	/***批次保存***/
	commitPricePlan:function(){
		var me = this;
    	//获取表单
    	var form = me.getForm(); 
    	var pricePlanModel = new Foss.pricing.toAddPartnerProgram.toAddPartnerProgramModel();
    	if(form.isValid()){
    		form.updateRecord(pricePlanModel);
    		var transportFlag = form.findField('transportFlag').getValue();	//
    		if (transportFlag == null ||transportFlag == '') {
    			pricing.showErrorMes("运输类型不能为空！");
    			return false;
    		}
    		var beginTime = form.findField('beginTime').getValue();	//

    		var time = Date.parse(beginTime);
    		var nowDate = new Date(); 
    		if (time < Date.parse(nowDate.format('yyyy-MM-dd'))) {
    			pricing.showErrorMes("生效日期不能小于当前日期！");
    			return false;
    		}
    		var planName = form.findField('planName').getValue();
    		//var networkModelName = form.findField('networkModelName').getValue();
    		var orgCode = form.findField('orgCode').getValue();//目的站
    		var heavyPrice = form.findField('heavyPrice').getValue();
    		var lightPrice = form.findField('lightPrice').getValue();
    		var lowestPrice = form.findField('lowestPrice').getValue();
			
    		//处理特殊字段
    		pricePlanModel.set('planName',planName);
    		pricePlanModel.set('orgCode',orgCode);
    		pricePlanModel.set('transportFlag',transportFlag);
    		pricePlanModel.set('beginTime',beginTime);
    		//pricePlanModel.set('networkModelName',networkModelName);
    		pricePlanModel.set('heavyPrice',heavyPrice);
    		pricePlanModel.set('lightPrice',lightPrice);
    		pricePlanModel.set('lowestPrice',lowestPrice);
    		
    		var params = {'toAddPartnerProgramVo':{'toAddPartnerProgramEntity':pricePlanModel.data}};
    		var url = pricing.realPath('addToAddPartnerProgram.action');
    		
    		//成功提示
    		var successFun = function(json){
    			pricing.showInfoMes(json.message);
    			//成功后获取价格方案主ID
				pricing.pricePlanId = json.toAddPartnerProgramVo.toAddPartnerProgramEntity.toAddPartnerProgramid;  
				//me.getPricePlanDetailGridPanel().store.add();
				//获取formPanel所有属性,全部设置为只读	    		
	    		var itemArrays = me.items.items;
	    		Ext.Array.each(itemArrays,function(value,index,arrays){
	    			itemArrays[index].setReadOnly(true);
	    		});
	    		me.getDockedItems()[1].items.items[1].setDisabled(true);//重置按钮不可用
				me.getDockedItems()[1].items.items[2].setDisabled(true);//保存按钮不可用
				me.up('window').close();
	    	};
    	    
    	    //失败提示
    	    var failureFun = function(json){
    	    	if(Ext.isEmpty(json)){
    				pricing.showErrorMes(pricing.toAddPartnerProgram.i18n('i18n.pricingRegion.requestTimeOut'));
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
    			this.pricePlanDetailGridPanel = Ext.create('Foss.pricing.toAddPartnerProgram.toAddPartnerProgramDetailGridPanel');
    			pricing.pricePlanDetailGridPanel  = this.pricePlanDetailGridPanel;
    	}
    	return this.pricePlanDetailGridPanel;
    },	
	//构造函数
	constructor : function(config) {
		var me = this, 
		cfg = Ext.apply({}, config);
		me.fbar = [{
			text :pricing.toAddPartnerProgram.i18n('i18n.pricingRegion.cancel'),//取消
			handler :function(){
				me.up('window').isUpdate = null;
				me.up().close();
			}
		},{
			text : pricing.toAddPartnerProgram.i18n('foss.pricing.reset'),//重置
			handler :function(){
					me.getForm().reset();//表格重置
			} 
		},{
			text : pricing.toAddPartnerProgram.i18n('foss.pricing.save'),//保存
			handler :function(){
				var isUpdate = me.up('window').isUpdate;
				if(isUpdate){
					me.commitUpdatePricePlan();	
				}else{
					me.commitPricePlan();	
				}
			} ,
			  plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin', {
				  //设定间隔秒数,如果不设置，默认为5秒
				  seconds: 5
				})
		}];
		me.callParent([cfg]);
	}
});

/**
 * 合伙人到达加收方案批次信息修改form
 */
Ext.define('Foss.pricing.toAddPartnerProgram.toAddPartnerProgramUpdateFormPanel',{
	extend : 'Ext.form.Panel',
	title: "到达加收方案信息",
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
			name: 'toAddPartnerProgramid',
			hidden : true
		},{
			name: 'planName',
			allowBlank:false,
			maxLength : 65,
			columnWidth: .5,
	        fieldLabel:pricing.toAddPartnerProgram.i18n('foss.pricing.scenarioName')//方案名称
		},{
			name : 'orgCode',
			allowBlank:false,
			xtype : 'commonTwolevelvehagencydeptselector',
			fieldLabel : pricing.toAddPartnerProgram.i18n('foss.pricing.destinationLinename'),
			columnWidth: .5,
			listeners:{
				'select':function(combo,records,eOpts){
					if(records.length>0){
						var form = this.up('form').getForm();             
						var record = form.findField("orgCode");
						form.findField('networkModelName').setValue(record.displayTplData[0].networkModel);
					}
				}}
		},{
			xtype:'combo',
		    displayField:'name',
		    allowBlank:false,
		    valueField:'code',
		    queryMode:'local',
		    triggerAction:'all',
		    editable:false,
		    name: 'transportFlag',
		    fieldLabel: '运输类型',//处理状态,
		    columnWidth: .5,
		    value:'',
		    convert:function(value, record){
				if(value=='0'){//汽运
					return '汽运'; 
				}else if(value=='TRANS_AIRCRAFT'){//空运
					return '空运';
				}else{
					return null;
				}
			},
		    store:Ext.create('Foss.pricing.toAddPartnerProgram.Store.ToDoTypeStore',
		    {
		      data:{
		           'items':[
		          {'code':'0','name':'汽运'}
		             
		         ]
		      }
		    })
			
		},{
			xtype:'datefield',
			allowBlank:false,
			fieldLabel:pricing.toAddPartnerProgram.i18n('foss.pricing.availabilityDate'),//生效日期
			format:'20y-m-d',
			name:'beginTime',
			columnWidth: .5
		},{
			name: 'networkModelName',
			readOnly: true,
			allowBlank:false,
			columnWidth: .5,
	        fieldLabel:'网点模式'
		},{
			xtype: 'numberfield',
			anchor: '100%',
			minValue: 0,
			maxValue: 999999.99,
			step:0.01,
			allowBlank:false,
			fieldLabel:'重货价格',
			name:'heavyPrice',
			columnWidth: .5
		},{
			xtype: 'numberfield',
			anchor: '100%',
			minValue: 0,
			maxValue: 999999.99,
			step:0.01,
			allowBlank:false,
			fieldLabel:'轻货价格',
			name:'lightPrice',
			columnWidth: .5
		},{
			xtype: 'numberfield',
			anchor: '100%',
			minValue: 0,
			maxValue: 999999.99,
			step:0.01,
			allowBlank:false,
			fieldLabel:'最低一票',
			//allowDecimals : false,//只能为数字，不能为小数
			name:'lowestPrice',
			columnWidth: .5
		}],
	
	/**修改价格方案**/
	commitUpdatePricePlan:function(){
    	var me = this;
    	var form = me.getForm();
    	if(form.isValid()){//校验form是否通过校验
    		var pricePlanEntity = me.up('window').pricePlanEntity;
    		var pricePlanModel = new Foss.pricing.toAddPartnerProgram.toAddPartnerProgramModel(pricePlanEntity);
    		
    		form.updateRecord(pricePlanModel);//将FORM中数据设置到MODEL里面
    		
	    	var transportFlag = form.findField('transportFlag').valueCode;//运输类型
    		if (transportFlag == null ||transportFlag == '') {
    			pricing.showErrorMes("运输类型不能为空！");
    			return false;
    		}
    		var valueCode = form.findField('transportFlag').displayTplData[0].valueCode;//运输类型
    		if (valueCode == null || valueCode == '' || valueCode == undefined) {
    			
    		} else {
    			transportFlag = valueCode;
    		}
    		
    		
    		var beginTime = form.findField('beginTime').getValue();	//生效时间
    		var planName = form.findField('planName').getValue();
    		var orgCode = form.findField('orgCode').getValue();//目的站
    		var heavyPrice = form.findField('heavyPrice').getValue();
    		var lightPrice = form.findField('lightPrice').getValue();
    		var lowestPrice = form.findField('lowestPrice').getValue();
			
    		//处理特殊字段
    		pricePlanModel.set('planName',planName);
    		pricePlanModel.set('orgCode',orgCode);
    		pricePlanModel.set('transportFlag',transportFlag);
    		pricePlanModel.set('beginTime',beginTime);
    		pricePlanModel.set('heavyPrice',heavyPrice);
    		pricePlanModel.set('lightPrice',lightPrice);
    		pricePlanModel.set('lowestPrice',lowestPrice);
    		
    		var params = {'toAddPartnerProgramVo':{'toAddPartnerProgramEntity':pricePlanModel.data}};//组织需要修改的数据
    		
    		var successFun = function(json){
				pricing.showInfoMes(json.message);//提示新增成功
				me.up('window').close();
				var waitingProcessGrid = Ext.getCmp('T_pricing-toAddPartnerProgram_content').getOffLinePricePlanGridPanel();
				waitingProcessGrid.store.load();
			};
			var failureFun = function(json){
				if(Ext.isEmpty(json)){
					pricing.showErrorMes(pricing.toAddPartnerProgram.i18n('foss.pricing.requestTimedOut'));//请求超时
				}else{
					pricing.showErrorMes(json.message);//提示失败原因
				}
			};
			var url = pricing.realPath('updatetoAddPartnerProgram.action');//请求价格方案修改
			pricing.requestJsonAjax(url,params,successFun,failureFun);//发送AJAX请求
    	}
	 },
	
	/***批次保存***/
	commitPricePlan:function(){
		var me = this;
    	//获取表单
    	var form = me.getForm(); 
    	var pricePlanModel = new Foss.pricing.toAddPartnerProgram.toAddPartnerProgramModel();
    	if(form.isValid()){
    		form.updateRecord(pricePlanModel);
    		var planName = form.findField('planName').getValue();
    		var orgCode = form.findField('orgCode').getValue();//目的站
    		var transportFlag = form.findField('transportFlag').getValue();	//
    		var beginTime = form.findField('beginTime').getValue();	//
//    		var networkModelName = form.findField('networkModelName').getValue();
    		var heavyPrice = form.findField('heavyPrice').getValue();
    		var lightPrice = form.findField('lightPrice').getValue();
    		var lowestPrice = form.findField('lowestPrice').getValue();
			
    		//处理特殊字段
    		pricePlanModel.set('name',name);
    		pricePlanModel.set('orgCode',orgCode);
    		pricePlanModel.set('productCode',productCode);
    		pricePlanModel.set('beginTime',beginTime);
//    		pricePlanModel.set('networkModelName',networkModelName);
    		pricePlanModel.set('heavyPrice',heavyPrice);
    		pricePlanModel.set('lightPrice',lightPrice);
    		pricePlanModel.set('lowestPrice',lowestPrice);
    		
    		var params = {'toAddPartnerProgramVo':{'toAddPartnerProgramEntity':pricePlanModel.data}};
    		var url = pricing.realPath('addtoAddPartnerProgram.action');
    		
    		//成功提示
    		var successFun = function(json){
    			pricing.showInfoMes(json.message);
    			//成功后获取价格方案主ID
				pricing.pricePlanId = json.priceManageMentVo.pricePlanEntity.id;  
				//获取formPanel所有属性,全部设置为只读	    		
	    		var itemArrays = me.items.items;
	    		Ext.Array.each(itemArrays,function(value,index,arrays){
	    			itemArrays[index].setReadOnly(true);
	    		});
	    		me.getDockedItems()[1].items.items[1].setDisabled(true);//重置按钮不可用
				me.getDockedItems()[1].items.items[2].setDisabled(true);//保存按钮不可用
				me.up('window').close();
				//var dockedItems = me.up('window').getPricePlanDetailGridPanel().getDockedItems();
				//dockedItems[1].items.items[0].setDisabled(false);//新增明明细按钮可用
				//dockedItems[1].items.items[2].setDisabled(false);//删除明细按钮可用
				//dockedItems[1].items.items[4].setDisabled(false);//导入明细按钮可用
	    	};
    	    
    	    //失败提示
    	    var failureFun = function(json){
    	    	if(Ext.isEmpty(json)){
    				pricing.showErrorMes(pricing.toAddPartnerProgram.i18n('i18n.pricingRegion.requestTimeOut'));
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
    			this.pricePlanDetailGridPanel = Ext.create('Foss.pricing.toAddPartnerProgram.toAddPartnerProgramDetailGridPanel');
    			pricing.pricePlanDetailGridPanel  = this.pricePlanDetailGridPanel;
    	}
    	return this.pricePlanDetailGridPanel;
    },	
	//构造函数
	constructor : function(config) {
		var me = this, 
		cfg = Ext.apply({}, config);
		me.fbar = [{
			text :pricing.toAddPartnerProgram.i18n('i18n.pricingRegion.cancel'),//取消
			handler :function(){
				me.up('window').isUpdate = null;
				me.up().close();
			}
		},{
			text : pricing.toAddPartnerProgram.i18n('foss.pricing.save'),//保存
			handler :function(){
				var isUpdate = me.up('window').isUpdate;
				if(isUpdate){
					me.commitUpdatePricePlan();	
				}else{
					me.commitPricePlan();	
				}
			} ,
			  plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin', {
				  //设定间隔秒数,如果不设置，默认为5秒
				  seconds: 5
				})
		}];
		me.callParent([cfg]);
	}
});

/**
 * 合伙人到达加收方案明细目的地grid
 */
Ext.define('Foss.pricing.toAddPartnerProgram.toAddPartnerProgramDetailGridPanel',{
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
		text : pricing.toAddPartnerProgram.i18n('i18n.pricingRegion.opra'),
		align : 'center',
		xtype : 'actioncolumn',
		items: [{
				iconCls:'deppon_icons_edit',
				tooltip: pricing.toAddPartnerProgram.i18n('foss.pricing.toAmendTheProposal'), 
				width:42,
				handler: function(grid, rowIndex, colIndex){
					var me = this;
                	var record = grid.up().getStore().getAt(rowIndex);//选中数据
                	
                	var pricePlanDetaiModel = new Foss.pricing.toAddPartnerProgram.OffLinePricePlanDetailDto();
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
    						pricing.showErrorMes(pricing.toAddPartnerProgram.i18n('foss.pricing.requestTimedOut'));//请求超时
    					}else{
    						pricing.showErrorMes(json.message);
    					}
    				};
    				var url = pricing.realPath('queryBeforeModifytoAddPartnerProgramDetail.action');
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
    		this.pricePlanDetailWindow = Ext.create('Foss.pricing.toAddPartnerProgram.toAddPartnerProgramDetailWindow',{
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
    		this.modifyPricePlanDetailWindow = Ext.create('Foss.pricing.toAddPartnerProgram.ModifyOffLinePricePlanDetailWindow',{
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
			pricing.showWoringMessage(pricing.toAddPartnerProgram.i18n('foss.pricing.pleaseSelectOneDeleteOperation'));//请选择一条进行删除操作！
			return;//没有则提示并返回
		}
		for(var i = 0;i<selections.length;i++){
			if(selections[i].get('active')==pricing.yes){
				pricing.showWoringMessage(pricing.toAddPartnerProgram.i18n('foss.pricing.pleaseChooseToNotActivateTheDataToBeBeleted'));//请选择未激活数据进行删除！
				return;//没有则提示并返回
			}
		}
		pricing.showQuestionMes(pricing.toAddPartnerProgram.i18n('foss.pricing.youWantDeletePricProgramDetails'),function(e){//是否要删除这些价格方案明细？
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
						pricing.showErrorMes(pricing.toAddPartnerProgram.i18n('foss.pricing.requestTimedOut'));//请求超时
					}else{
						pricing.showErrorMes(json.message);
					}
				};
				var url = pricing.realPath('deletetoAddPartnerProgramDetail.action');
				pricing.requestJsonAjax(url,params,successFun,failureFun);
			}
		})	
    },
	//初始化构造器
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
			me.store = Ext.create('Foss.pricing.toAddPartnerProgram.toAddPartnerProgramDeatilStore',{
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
		me.store = Ext.create('Foss.pricing.toAddPartnerProgram.toAddPartnerProgramDeatilStore',{
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
	            text: pricing.toAddPartnerProgram.i18n('i18n.pricingRegion.add'),
	            handler:function(){ 
	            	me.getPricePlanDetailWindow().show();	 
	            }
	        },'-',{
	            text: pricing.toAddPartnerProgram.i18n('foss.pricing.delete'),
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
 * 合伙人到达加收方案弹出框
 */
Ext.define('Foss.pricing.toAddPartnerProgram.toAddPartnerProgramAddWindow',{
		extend: 'Ext.window.Window',
		title: "新增合伙人价格方案",
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
				//上面循环把所有的都设置为可编辑，但是要把下面的设置为不可编辑
				me.getPricePlanAddFormPanel().getForm().findField('networkModelName').setReadOnly(true);
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
	    		this.pricePlanAddFormPanel = Ext.create('Foss.pricing.toAddPartnerProgram.toAddPartnerProgramAddFormPanel');
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
 *合伙人到达加收方案弹出修改框
 */
Ext.define('Foss.pricing.toAddPartnerProgram.toAddPartnerProgramUpdateWindow',{
		extend: 'Ext.window.Window',
		title: "修改合伙人价格方案",
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
				me.gettoAddPartnerProgramUpdateFormPanel().getForm().reset();
				me.isUpdate = null;
			},
			beforeshow:function(me){
				me.gettoAddPartnerProgramUpdateFormPanel().getForm().loadRecord(new Foss.pricing.toAddPartnerProgram.toAddPartnerProgramModel(me.pricePlanEntity));
				
				me.gettoAddPartnerProgramUpdateFormPanel().getForm().findField('orgCode').setCombValue(me.pricePlanEntity.name, me.pricePlanEntity.orgCode);
				
				var transportFlag = me.pricePlanEntity.transportFlag;
				var productName = '';
				if (transportFlag == '0') {//汽运
					productName = '汽运'; 
				} else if (transportFlag == 'TRANS_AIRCRAFT') {//空运
					productName = '空运';
				}
				me.gettoAddPartnerProgramUpdateFormPanel().getForm().findField('transportFlag').setValue(productName); 
				me.gettoAddPartnerProgramUpdateFormPanel().getForm().findField('transportFlag').valueCode = transportFlag;
				me.gettoAddPartnerProgramUpdateFormPanel().getForm().findField('transportFlag').valueName = productName;
			}
		},
	    //价格方案批次信息 （出发地批次信息录入）
		toAddPartnerProgramUpdateFormPanel:null,
	    gettoAddPartnerProgramUpdateFormPanel : function(){
	    	var me = this;
	    	if(Ext.isEmpty(me.toAddPartnerProgramUpdateFormPanel)){
		    		me.toAddPartnerProgramUpdateFormPanel = Ext.create('Foss.pricing.toAddPartnerProgram.toAddPartnerProgramUpdateFormPanel');
		    		//设置器父元素
 	    	} 
	    	return this.toAddPartnerProgramUpdateFormPanel;
	    },
	    //构造器
		constructor: function(config){
			var me = this,
			cfg = Ext.apply({}, config);
			me.isUpdate,
			me.items = [me.gettoAddPartnerProgramUpdateFormPanel()],
			
			me.callParent([cfg]);
		}	
});


/**
 * 合伙人到达加收方案中止方案弹出框
 */
Ext.define('Foss.pricing.toAddPartnerProgram.toAddPartnerProgramStopEndTimeWindow',{
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
	    		this.pricePlanStopFormPanel = Ext.create('Foss.pricing.toAddPartnerProgram.toAddPartnerProgramStopFormPanel');
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
});

/**
 * 中止功能window
 */
Ext.define('Foss.pricing.toAddPartnerProgram.toAddPartnerProgramStopFormPanel',{
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
			var pricePlanModel = new Foss.pricing.toAddPartnerProgram.toAddPartnerProgramModel(pricePlanEntity);
			form.updateRecord(pricePlanModel);
    		var time = form.findField('endTime').getValue();
    		var nowDate = new Date(); 
    		if (Date.parse(time) < Date.parse(nowDate.format('yyyy-MM-dd'))) {
    			pricing.showErrorMes("中止日期不能小于当前日期！");
    			return false;
    		}
    		var pricePlanId = pricing.pricePlanId;
    		var params = {'toAddPartnerProgramVo':{'toAddPartnerProgramid':pricePlanId, 'yesOrNo': 'N', 'effectiveTime': time}};
    		var url = pricing.realPath('activetoAddPartnerProgram.action');
    		
    		//成功提示
    		var successFun = function(json){
    			pricing.showInfoMes(json.message);
    			me.up('window').close();
				var waitingProcessGrid = Ext.getCmp('T_pricing-toAddPartnerProgram_content').getOffLinePricePlanGridPanel();
				waitingProcessGrid.store.load();
    	    };
    	    
    	    //失败提示
    	    var failureFun = function(json){
    	    	if(Ext.isEmpty(json)){
    				pricing.showErrorMes(pricing.toAddPartnerProgram.i18n('i18n.pricingRegion.requestTimeOut'));
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
			name : 'endTime',
			fieldLabel:pricing.toAddPartnerProgram.i18n('foss.pricing.deadline'),//截止日期
			xtype : 'datetimefield_date97',
			editable:false,
			time : true,
			id : 'Foss_toAddPartnerProgram_stopEndTime_grid_ID',
			allowBlank:false,
			dateConfig: {
				el : 'Foss_toAddPartnerProgram_stopEndTime_grid_ID-inputEl'
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
Ext.define('Foss.pricing.toAddPartnerProgram.toAddPartnerProgramImmediatelyStopEndTimeWindow',{
		extend: 'Ext.window.Window',
		title: pricing.toAddPartnerProgram.i18n('foss.pricing.immediatelySupendPricePriceScheme'),//"立即中止方案",
		width:380,
		height:152,
		pricePlanEntity:null,
		closeAction: 'hide' ,
		pricePlanStopFormPanel:null,
		getOffLinePricePlanStopFormPanel : function(pricePlanEntity){
	    	if(Ext.isEmpty(this.pricePlanAddFormPanel)){
	    		this.pricePlanStopFormPanel = Ext.create('Foss.pricing.toAddPartnerProgram.toAddPartnerProgramImmediatelyStopFormPanel',{
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
Ext.define('Foss.pricing.toAddPartnerProgram.toAddPartnerProgramImmediatelyStopFormPanel',{
	extend : 'Ext.form.Panel',
	layout:'column',
	pricePlanEntity:null,
	stopPricePlan:function(pricePlanId){
		var me = this;
    	var form = me.getForm();
    	if(form.isValid()){
    		var pricePlanModel = new Foss.pricing.toAddPartnerProgram.toAddPartnerProgramModel();
			form.updateRecord(pricePlanModel);

    		var endTime = form.findField('endTime').getValue();
			var result = Ext.Date.parse(endTime,'Y-m-d H:i:s') - Ext.Date.parse(Ext.Date.format(new Date(),'Y-m-d H:i:s'),'Y-m-d H:i:s');
			if(result<0) {
    			pricing.showErrorMes("中止日期不能小于当前日期！");
    			return false;
			}
			
    		var params = {'toAddPartnerProgramVo':{'toAddPartnerProgramid':pricePlanId, 'yesOrNo': 'N', 'effectiveTime': endTime}};
    		var url = pricing.realPath('activetoAddPartnerProgram.action');
    		
    		var successFun = function(json){
    			pricing.showInfoMes(json.message);
    			me.up('window').hide();
    			var waitingProcessGrid = Ext.getCmp('T_pricing-toAddPartnerProgram_content').getOffLinePricePlanGridPanel();
				waitingProcessGrid.store.load();	
    	    };
    	    var failureFun = function(json){
    	    	if(Ext.isEmpty(json)){
    				pricing.showErrorMes(pricing.toAddPartnerProgram.i18n('i18n.pricingRegion.requestTimeOut'));
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
				value:pricing.toAddPartnerProgram.i18n('foss.pricing.showleftTimeInfo')
					  +showbeginTime+ pricing.toAddPartnerProgram.i18n('foss.pricing.showmiddleTimeInfo')
					  +showendTime  + pricing.toAddPartnerProgram.i18n('foss.pricing.showstopRightEndTimeInfo')
				//'<p style="color:red">原方案生效日期为【2013-02-31】截止日期为【2013-09-11】,您是否立即中止该方案?</p>'
			},{ 
				fieldLabel :pricing.toAddPartnerProgram.i18n('foss.pricing.suspendTime') ,//'中止日期',
				name : 'endTime',
				xtype : 'datetimefield_date97',
				editable:false,
				time : true,
				id : 'Foss_toAddPartnerProgram_stopEndTime_ID',
				allowBlank:false,
				dateConfig: {
					el : 'Foss_toAddPartnerProgram_stopEndTime_ID-inputEl'
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
				text : pricing.toAddPartnerProgram.i18n('i18n.pricingRegion.determine'),//"确认",
				handler : function() {
					var pricePlanId = pricing.toAddPartnerProgram.immediatelyStopId;
					me.stopPricePlan(pricePlanId);
				}
			},{
				xtype : 'button', 
				width:70,
				columnWidth:.15,
				text : pricing.toAddPartnerProgram.i18n('i18n.pricingRegion.cancel'),//"取消",
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
Ext.define('Foss.pricing.toAddPartnerProgram.toAddPartnerProgramImmediatelyActiveTimeWindow',{
		extend: 'Ext.window.Window',
		title: pricing.toAddPartnerProgram.i18n('foss.pricing.immediatelyActiveationPriceScheme'),//"立即激活方案",
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
				var beginTime = Ext.Date.format(me.pricePlanEntity.beginTime, 'Y-m-d H:i:s')
				var endTime = Ext.Date.format(new Date(me.pricePlanEntity.endTime), 'Y-m-d H:i:s');
				var value = pricing.toAddPartnerProgram.i18n('foss.pricing.showleftTimeInfo')
					  + beginTime + pricing.toAddPartnerProgram.i18n('foss.pricing.showmiddleTimeInfo')
					  + endTime + pricing.toAddPartnerProgram.i18n('foss.pricing.showrightEndTimeInfo');
				
				me.pricePlanEntity.showTime = value;
				me.getOffLinePricePlanImmediatelyActiveFormPanel().getForm().loadRecord(new Foss.pricing.toAddPartnerProgram.toAddPartnerProgramModel(me.pricePlanEntity));
				me.getOffLinePricePlanImmediatelyActiveFormPanel().getForm().findField('beginTime').setValue(beginTime);
			}
		},
		OffLinePricePlanImmediatelyActiveFormPanel:null,
		getOffLinePricePlanImmediatelyActiveFormPanel : function(pricePlanEntity){
	    	if(Ext.isEmpty(this.OffLinePricePlanImmediatelyActiveFormPanel)){
	    		this.OffLinePricePlanImmediatelyActiveFormPanel = Ext.create('Foss.pricing.toAddPartnerProgram.toAddPartnerProgramImmediatelyActiveFormPanel');
	    	}
	    	this.OffLinePricePlanImmediatelyActiveFormPanel.pricePlanEntity = pricePlanEntity;
	    	return this.OffLinePricePlanImmediatelyActiveFormPanel;
	    },
	   	constructor : function(config) {
			var me = this, cfg = Ext.apply({}, config);
			me.items = [me.getOffLinePricePlanImmediatelyActiveFormPanel(me.pricePlanEntity)];
			me.callParent(cfg);
		}
});


/**
 * 立即激活Form
 */
Ext.define('Foss.pricing.toAddPartnerProgram.toAddPartnerProgramImmediatelyActiveFormPanel',{
	extend : 'Ext.form.Panel',
	layout:'column',
	pricePlanEntity:null,
	activetionPricePlan:function(pricePlanId){
		var me = this;
    	var form = me.getForm();
    	if(form.isValid()){

        	var pricePlanModel = new Foss.pricing.toAddPartnerProgram.toAddPartnerProgramModel();
			form.updateRecord(pricePlanModel);
			pricePlanModel.set('beginTime',Ext.Date.parse(form.findField('beginTime').getValue(), 'Y-m-d H:i:s'));
    		pricePlanModel.set('id',pricePlanId);

    		var time = Ext.Date.parse(form.findField('beginTime').getValue(), 'Y-m-d H:i:s');
    		var nowDate = new Date(); 
    		if (time < nowDate) {
    			pricing.showErrorMes("生效日期不能小于系统当前时间！");
    			return false;
    		}
    		var params = {'toAddPartnerProgramVo':{'toAddPartnerProgramid':pricePlanId, 'yesOrNo': 'Y', 'effectiveTime': time}};
    		var url = pricing.realPath('activetoAddPartnerProgram.action');
    		var successFun = function(json){
    			pricing.showInfoMes(json.message);
    			me.up('window').hide();
				var waitingProcessGrid = Ext.getCmp('T_pricing-toAddPartnerProgram_content').getOffLinePricePlanGridPanel();
				waitingProcessGrid.store.load();			
    	    };
    	    var failureFun = function(json){
    	    	if(Ext.isEmpty(json)){
    				pricing.showErrorMes(pricing.toAddPartnerProgram.i18n('i18n.pricingRegion.requestTimeOut'));
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
		
	        },{
				fieldLabel:pricing.toAddPartnerProgram.i18n('foss.pricing.availabilityDate'),//'生效日期',
				name : 'beginTime',
				xtype : 'datetimefield_date97',
				editable:false,
				time : true,
				id : 'Foss_toAddPartnerProgram_activetionEndTime_ID',
				allowBlank:false,
				dateConfig: {
					el : 'Foss_toAddPartnerProgram_activetionEndTime_ID-inputEl'
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
				text : pricing.toAddPartnerProgram.i18n('i18n.pricingRegion.determine'),//,"确认",
				handler : function() {
					var pricePlanId = pricing.toAddPartnerProgram.immediatelyActiveId;
					me.activetionPricePlan(pricePlanId);
				}
			},{
				xtype : 'button', 
				width:70,
				columnWidth:.15,
				text : pricing.toAddPartnerProgram.i18n('i18n.pricingRegion.cancel'),//"取消",
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
Ext.define('Foss.pricing.toAddPartnerProgram.UploadtoAddPartnerProgramform',{
	extend:'Ext.window.Window',
	title:pricing.toAddPartnerProgram.i18n('foss.pricing.importPriceScheme'),
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
				fieldLabel:pricing.toAddPartnerProgram.i18n('foss.pricing.pleaseSelectAttachments'),
				labelWidth:100,
				buttonText:pricing.toAddPartnerProgram.i18n('foss.pricing.browse'),
				flex:3
			}]
		}];
		me.fbar = me.getFbar();
		me.callParent([cfg]);
	},
	getFbar:function(){
		var me = this;
		return [{
			text:pricing.toAddPartnerProgram.i18n('foss.pricing.import'),
			xtype:'button',
			scope:me,
			handler:me.uploadFile
		},{
			text:pricing.toAddPartnerProgram.i18n('i18n.pricingRegion.cancel'),
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
				pricing.showInfoMes(pricing.toAddPartnerProgram.i18n('foss.pricing.allDataImportSuccess'));//全部数据导入成功！
				me.close();
			}
			var waitingProcessGrid = Ext.getCmp('T_pricing-toAddPartnerProgram_content').getOffLinePricePlanGridPanel();
			waitingProcessGrid.store.load();
		};
		var failureFn = function(json){
			if(Ext.isEmpty(json)){
				pricing.showErrorMes(pricing.toAddPartnerProgram.i18n('i18n.pricingRegion.requestTimeOut'));//pricing.toAddPartnerProgram.i18n('i18n.pricingRegion.requestTimeOut')
			}else{
				pricing.showErrorMes(json.message);
			}
		};
		var form = me.down('form').getForm();
		var url = pricing.realPath('importtoAddPartnerProgram.action');
		form.submit({
            url: url,
            waitMsg: pricing.toAddPartnerProgram.i18n('foss.pricing.uploadYourAttachment'),
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
	var queryform = Ext.create('Foss.pricing.toAddPartnerProgram.toAddPartnerProgramFormPanel');//查询
	var gridPanel =	Ext.create('Foss.pricing.toAddPartnerProgram.toAddPartnerProgramGridPanel');
	pricing.toAddPartnerProgram.queryform = queryform;
	pricing.toAddPartnerProgram.gridPanel = gridPanel;
	Ext.getCmp('T_pricing-toAddPartnerProgram').add(Ext.create('Ext.panel.Panel', {
	 	id:'T_pricing-toAddPartnerProgram_content',
		cls:"panelContentNToolbar",
		bodyCls:'panelContent-body',
		
		//获得查询FORM
		getQueryOffLinePricePlanForm : function() {
			return queryform;
		},
		//获得查询结果GRID
		getOffLinePricePlanGridPanel : function() {
			if(Ext.isEmpty(this.gridPanel)){
				this.gridPanel = Ext.create('Foss.pricing.toAddPartnerProgram.toAddPartnerProgramGridPanel');//查询结果GRID
			}
			return gridPanel;
		},
		
		items : [queryform,gridPanel]
	}));
});

