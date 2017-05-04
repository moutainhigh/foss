pricing.effectiveExpressPlan.LONG = 'L';//长途
pricing.effectiveExpressPlan.SHORT = 'S';//短途
pricing.effectiveExpressPlan.DAY = 'DAY';//单位是天
pricing.effectiveExpressPlan.HOURS = 'HOURS';//单位是小时
pricing.effectiveExpressPlan.YES='Y';//是否激活
pricing.effectiveExpressPlan.VersionYES='Y';//是当前版本
pricing.effectiveExpressPlan.VersionNO ='N';//不是当前版本
pricing.effectiveExpressPlan.VersionALL ='ALL';//全部
//转换long类型为日期
pricing.effectiveExpressPlan.changeLongToDate = function(value) {
	if (value != null) {
		var date = new Date(value);
		return date;
	} else {
		return null;
	}
};
//Ajax请求--json
pricing.effectiveExpressPlan.requestJsonAjax = function(url,params,successFn,failFn)
{
	Ext.Ajax.request({
		url:url,
		timeout:60000*60,   
		jsonData:params,
		success:function(response){
			var result = Ext.decode(response.responseText);
			if(result.success){
				successFn(result);
			}else{
				failFn(result);
			}
		},
		exception:function(response){
			var result = Ext.decode(response.responseText);
			Ext.Msg.alert(pricing.effectiveExpressPlan.i18n('foss.pricing.promptMessage'),result.message);
		}
	});
};
pricing.effectiveExpressPlan.saleDepartmentProduct = [];
//查询所有三级产品
pricing.effectiveExpressPlan.searchThreeLevelProduct = function(){
	Ext.Ajax.request({
		url:pricing.realPath('searchThreeLevelProductExpress.action'),
		async:false,
		jsonData:null,
		success:function(response){
			var result = Ext.decode(response.responseText);
			pricing.effectiveExpressPlan.saleDepartmentProduct = result.effectiveExpressPlanVo.productEntityList;//查询三级产品
		},
		failure:function(response){
			var result = Ext.decode(response.responseText);
			if(Ext.isEmpty(result)){
				pricing.showErrorMes(pricing.effectiveExpressPlan.i18n('i18n.pricingRegion.requestTimeOut'));
			}else{
				pricing.showErrorMes(result.message);
			}
		} 
	});
};
//--------------------------------------pricing----------------------------------------
/**
 * 时效方案MODEL
 */
Ext.define('Foss.pricing.effectiveExpressPlan.EffectiveExpressPlanEntity', {
    extend: 'Ext.data.Model',
    fields : [{
        name : 'id',
        type : 'string'
    },{
        name : 'modifyDate',//修改时间
        type : 'date',
        defaultValue : null,
        convert : pricing.effectiveExpressPlan.changeLongToDate
    },{
        name : 'name',//方案名称
        type : 'string'
    },{
        name : 'beginTime',//方案开始时间
        type : 'date',
        defaultValue : null,
        convert : pricing.effectiveExpressPlan.changeLongToDate
    },{
        name : 'endTime',//方案结束时间
        type : 'date',
        defaultValue : null,
        convert : pricing.effectiveExpressPlan.changeLongToDate
    },{
        name : 'deptRegionId',//始发区域ID
        type : 'string'
    },{
        name : 'deptRegionCode',//始发区域编码
        type : 'string'
    },{
        name : 'deptRegionName',//始发区域名称
        type : 'string'
    },{
        name : 'active',//激活/未激活
        type : 'string'
    },{
        name : 'description',//描述
        type : 'string'
    },{
        name : 'updateDesTemp',//修改使用描述
        type : 'string'
    },{
        name : 'versionInfo',//版本信息
        type : 'string'
    },{
        name : 'refId',//原始方案ID
        type : 'string'
    },{
        name : 'createUserCode',//创建机构
        type : 'string'
    },{
        name : 'modifyUserCode',//修改机构
        type : 'string'
    },{
        name : 'modifyUserName',//修改人名称
        type : 'string'
    },{
        name : 'businessDate', //营业日期
        type : 'date',
        defaultValue : null,
        convert : pricing.effectiveExpressPlan.changeLongToDate
    },{
    	name : 'currentUsedVersion',//是否当前版本
    	type : 'string'
    },{
    	name: 'showTime', //立即激活所用到的提示日期信息
    	type: 'string'
    },{
    	name: 'isPromptly'//是否立即中止
    }]
});
						 

/**
 * 时效明细实体MODEL
 */
Ext.define('Foss.pricing.effectiveExpressPlan.EffectiveExpressPlanDetailEntity', {
    extend: 'Ext.data.Model',
    fields : [{
        name : 'id',
        type : 'string'
    },{
        name : 'productId',//产品ID
        type : 'string'
    },{
        name : 'productCode',//产品编码
        type : 'string'
    },{
        name : 'productName',//产品名称
        type : 'string'
    },{
        name : 'effectivePlanId',//时效主方案ID
        type : 'string'
    },{
        name : 'deptRegionId',//始发区域ID
        type : 'string'
    },{
        name : 'deptRegionCode',//始发区域编码
        type : 'string'
    },{
        name : 'arrvRegionName',//始发区域名称
        type : 'string'
    },{
        name : 'arrvRegionId',//目的地区域ID
        type : 'string'
    },{
        name : 'arrvRegionCode',//目的地区域编码
        type : 'string'
    },{
        name : 'arrvRegionName',//目的地区域名称
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
        name : 'arriveTime',//承诺到达营业部时间
        type : 'string'
    },{
        name : 'addDay',//派送承诺需加天数
        type : 'int'
    },{
        name : 'deliveryTime',//派送承诺时间
        type : 'string'
    },{
        name : 'hasSalesDept', //是否有驻地部门
        type : 'string'
    },{
        name : 'longOrShort',//长短途
        type : 'string'
    },{
        name : 'description',//备注
        type : 'string'
    },{
        name : 'createOrgCode',//创建组织
        type : 'string'
    },{
        name : 'modifyOrgCode',//更新组织
        type : 'string'
    },{
        name : 'active', //数据状态
        type : 'string'
    },{
    	 name : 'upDesTemp',//备注
         type : 'string'
    }]
});

//------------------------------------model---------------------------------------------------
/**
 * 时效方案Store
 */
Ext.define('Foss.pricing.effectiveExpressPlan.EffectiveExpressPlanStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.pricing.effectiveExpressPlan.EffectiveExpressPlanEntity',
	pageSize:20,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url : "../pricing/searchEffectiveExpressPlanByCondition.action",
		reader : {
			type : 'json',
			root : 'effectiveExpressPlanVo.effectivePlanEntityList',
			totalProperty : 'totalCount'
		}
	}
});
/**
 * 时效方案明细Store
 */
Ext.define('Foss.pricing.effectiveExpressPlan.EffectivePlanDetailStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.pricing.effectiveExpressPlan.EffectiveExpressPlanDetailEntity',
	pageSize:20,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url : "../pricing/searchEffectiveExpressPlanDetailByCondition.action",
		reader : {
			type : 'json',
			root : 'effectiveExpressPlanVo.effectivePlanDetailEntityList',
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
pricing.effectiveExpressPlan.getStore = function(storeId,model,fields,data) {
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
 * 时效方案查询表单
 */
Ext.define('Foss.pricing.effectiveExpressPlan.QueryEffectiveExpressPlanForm', {
	extend : 'Ext.form.Panel',
	title: pricing.effectiveExpressPlan.i18n('i18n.pricingRegion.searchCondition'),
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
			name: 'name',
	        fieldLabel:pricing.effectiveExpressPlan.i18n('foss.pricing.scenarioName'),//方案名称
	        xtype : 'textfield'
		},{
			name: 'deptRegionCode',
	        fieldLabel:pricing.effectiveExpressPlan.i18n('foss.pricing.originatingArea'),//始发区域
	        xtype : 'commonperscriptionregionexpressselector'
		},{
			columnWidth : .3,
			name: 'active',
			queryMode: 'local',
		    displayField: 'value',
		    value:'ALL',
		    valueField: 'key',
		    editable:false,
		    store:pricing.getStore('Foss.pricing.region.AreaStatusStore',null,['key','value']
		    ,[{'key':'N','value':pricing.effectiveExpressPlan.i18n('i18n.pricingRegion.unActive')},{'key':'Y','value':pricing.effectiveExpressPlan.i18n('i18n.pricingRegion.active')}
		    ,{'key':'ALL','value':pricing.effectiveExpressPlan.i18n('i18n.pricingRegion.all')}]),
		    fieldLabel: pricing.effectiveExpressPlan.i18n('foss.pricing.status'),//状态
		    xtype : 'combo'
		},{//是否当前版本 是，否，全部			
			name: 'currentUsedVersion',
			queryMode: 'local',
		    displayField: 'valueName',
		    valueField: 'valueCode',
		    editable:false,
		    value:pricing.effectiveExpressPlan.i18n('i18n.pricing.currentVersion_all'),
		    store:pricing.getStore(null,null,['valueName','valueCode']
		    ,[{'valueName':pricing.effectiveExpressPlan.i18n('i18n.pricing.currentVersion_all'),'valueCode':pricing.effectiveExpressPlan.VersionALL}//全部
		    ,{'valueName':pricing.effectiveExpressPlan.i18n('i18n.pricing.currentVersion_yes'),'valueCode':pricing.effectiveExpressPlan.VersionYES}//是当前版本
		    , {'valueName':pricing.effectiveExpressPlan.i18n('i18n.pricing.currentVersion_no'),'valueCode':pricing.effectiveExpressPlan.VersionNO}]),//不是当前版本
		    fieldLabel: pricing.effectiveExpressPlan.i18n('foss.pricing.currentUsedVersion'),//是否当前版本
		    xtype : 'combo'
		}]; 
		me.fbar = [{
			xtype : 'button', 
			width:70,
			left : 1,
			text : pricing.effectiveExpressPlan.i18n('foss.pricing.reset'),//重置
			margin:'0 825 0 0',
			handler : function() {
				me.getForm().reset();
			}
		},{
			xtype : 'container',
			margin : '0 0 0 0',
			columnWidth : .45,
			items : [{
				xtype : 'button', 
				width:70,
				text : pricing.effectiveExpressPlan.i18n('i18n.pricingRegion.search'),
				disabled: !pricing.effectiveExpressPlan.isPermission('effectiveExpressPlan/effectivePlanQuerybutton'),
				hidden: !pricing.effectiveExpressPlan.isPermission('effectiveExpressPlan/effectivePlanQuerybutton'),
				cls:'yellow_button',
				handler : function() {
					if(me.getForm().isValid()){
						var grid = Ext.getCmp('T_pricing-effectiveExpressPlan_content').getEffectivePlanGridPanel();
						grid.getPagingToolbar().moveFirst();
					}
				}
			}]
		}];
		me.callParent([cfg]);
	}
});
/**
 * 时效方案列表
 */
Ext.define('Foss.pricing.effectiveExpressPlan.EffectiveExpressPlanGridPanel', {
	extend: 'Ext.grid.Panel',
	title :pricing.effectiveExpressPlan.i18n('i18n.pricingRegion.searchResults'),//查询结果
	frame: true,
    sortableColumns:false,
    enableColumnHide:false,
    enableColumnMove:false,
	stripeRows : true, // 交替行效果
	selType : "rowmodel", // 选择类型设置为：行选择
	emptyText: pricing.effectiveExpressPlan.i18n('foss.pricing.theQueryResultIsEmpty'),//查询结果为空
	//得到bbar
	pagingToolbar : null,
	getPagingToolbar : function() {
		if (this.pagingToolbar == null) {
			this.pagingToolbar = Ext.create('Deppon.StandardPaging', {
				store : this.store,
				pageSize : 20
			});
		}
		return this.pagingToolbar;
	},
	//新增时效弹窗
	effectivePlanAddWindow : null,
	getEffectivePlanAddWindow : function() {
		if (Ext.isEmpty(this.effectivePlanAddWindow)) {
			this.effectivePlanAddWindow = Ext.create('Foss.pricing.effectiveExpressPlan.EffectiveExpressPlanAddWindow');
			this.effectivePlanAddWindow.parent = this;
		}
		return this.effectivePlanAddWindow;
	},
	//修改时效弹窗
	effectivePlanUpdateWindow : null,
	getEffectivePlanUpdateWindow : function() {
		if (Ext.isEmpty(this.effectivePlanUpdateWindow)) {
			this.effectivePlanUpdateWindow = Ext.create('Foss.pricing.effectiveExpressPlan.EffectiveExpressPlanUpdateWindow');
			this.effectivePlanUpdateWindow.parent = this;
		}
		return this.effectivePlanUpdateWindow;
	},
	//查看详情WINDOW
	effectivePlanDetailShowWindow : null,
	getEffectivePlanDetailShowWindow : function() {
		if (Ext.isEmpty(this.effectivePlanDetailShowWindow)) {
			this.effectivePlanDetailShowWindow = Ext.create('Foss.pricing.effectiveExpressPlan.EffectiveExpressPlanDetailShowWindow');
			this.effectivePlanDetailShowWindow.parent = this;
		}
		return this.effectivePlanDetailShowWindow;
	},
	
	//导入文件WINDOW
	uploadEffectivePlan : null,
	getUploadEffectivePlan : function() {
		if (Ext.isEmpty(this.uploadEffectivePlan)) {
			this.uploadEffectivePlan = Ext.create('Foss.pricing.UploadEffectiveExpressPlan');
			this.uploadEffectivePlan.parent = this;
		}
		return this.uploadEffectivePlan;
	},
	
	//删除价格方案
	deleteEffectivePlan:function(){
	 	var me = this;
	 	var selections = me.getSelectionModel().getSelection();
	 	if(selections.length < 1){
	 		pricing.showWoringMessage(pricing.effectiveExpressPlan.i18n('foss.pricing.pleaseSelectOneDeleteOperation'));
			return;
	 	}
	 	//过滤草稿状态数据进行删除
	 	for(var i = 0;i<selections.length;i++){
			if(selections[i].get('active')==pricing.effectiveExpressPlan.YES){
				pricing.showWoringMessage(pricing.effectiveExpressPlan.i18n('foss.pricing.pleaseChooseToNotActivateTheDataToBeBeleted'));
				return;
			}
		}
		//是否要删除这些汽运价格方案？
		pricing.showQuestionMes(pricing.effectiveExpressPlan.i18n('foss.pricing.wantDeleteTheseAgingProgram'),function(e){
			if(e=='yes'){
				//汽运价格方案
				var idList = new Array();
				for(var i = 0 ; i<selections.length ; i++){
					idList.push(selections[i].get('id'));
				}
				var params = {'effectiveExpressPlanVo':{'effectivePlans':idList}};
				var successFun = function(json){
					pricing.showInfoMes(json.message);
					me.getPagingToolbar().moveFirst();
				};
				var failureFun = function(json){
					if(Ext.isEmpty(json)){
						pricing.showErrorMes(pricing.effectiveExpressPlan.i18n('foss.pricing.requestTimedOut'));//请求超时
					}else{
						pricing.showErrorMes(json.message);
					}
				};
				var url = pricing.realPath('deleteEffectiveExpressPlan.action');
				pricing.effectiveExpressPlan.requestJsonAjax(url,params,successFun,failureFun);
			}
		});
	},
	
	//激活时效方案
    activeEffectivePlan:function(){
    	var me = this;
		var effectivePlans = new Array();
		//获取选中的数据
		var selections = me.getSelectionModel().getSelection();
		if(selections.length<1){
			pricing.showErrorMes(pricing.effectiveExpressPlan.i18n('foss.pricing.pleaseChooseActiveAging'));//请选择要激活的时效方案！
			return;
		}
		//过滤草稿状态数据进行激活
	 	for(var i = 0;i<selections.length;i++){
			if(selections[i].get('active')==pricing.effectiveExpressPlan.YES){
				pricing.showWoringMessage(pricing.effectiveExpressPlan.i18n('foss.pricing.pleaseSelectNotActivateProgramDataForActivation'));
				return;
			}
			effectivePlans.push(selections[i].get('id'));
		}
		if(effectivePlans.length<1){
			pricing.showErrorMes(pricing.effectiveExpressPlan.i18n('foss.pricing.pleaseSelectLeastInactiveAgingProgram'));//请至少选择一条未激活的时效方案！
			return;
		}
		//是否要激活这些快递时效方案？
		pricing.showQuestionMes(pricing.effectiveExpressPlan.i18n('foss.pricing.wantActivateTheseAgingProgram'),function(e){
			if(e=='yes'){
				var params = {'effectiveExpressPlanVo':{'effectivePlans':effectivePlans}};
				var successFun = function(json){
					pricing.showInfoMes(json.message);
					me.getPagingToolbar().moveFirst();
				};
				var failureFun = function(json){
					if(Ext.isEmpty(json)){
						pricing.showErrorMes(pricing.effectiveExpressPlan.i18n('i18n.pricingRegion.requestTimeOut'));//请求超时
					}else{
						pricing.showErrorMes(json.message);
					}
				};
				var url = pricing.realPath('activeEffectiveExpressPlan.action');
				pricing.effectiveExpressPlan.requestJsonAjax(url,params,successFun,failureFun);
			}
		});
    },
    
    
    	//中止方案弹出日期选择
	stopEffectivePlanWindow:null,
	getStopEffectivePlanWindow: function(effectivePlanId){
		if(Ext.isEmpty(this.stopEffectivePlanWindow)){
			this.stopEffectivePlanWindow = Ext.create('Foss.pricing.effectiveExpressPlan.EffectivePlanStopEndTimeWindow');
			this.stopEffectivePlanWindow.parent = this;
		}
		this.stopEffectivePlanWindow.effectivePlanId = effectivePlanId;
		return this.stopEffectivePlanWindow;
	},
	
	
	  /**
     * 立即生效
     * 对于实际业务可能在当天发生两次以上的调整，故给予立即生效功能用于支持该业务，
     * 1、立即生效功能给予特定角色来操作。根据所登陆的用户所属某某角色来决定是否给予立即生效功能。防止一般用户进行当天频繁调价操作
     * 2、立即生效功能中止日期可以等于今天但是不能小于今天。
     * 
     */
    immediatelyActiveEffectivePlanWindow:null,
	getImmediatelyActiveEffectivePlanWindow: function(effectivePlans){
		if(Ext.isEmpty(this.immediatelyActiveEffectivePlanWindow)){
			this.immediatelyActiveEffectivePlanWindow = Ext.create('Foss.pricing.effectiveExpressPlan.EffectivePlanImmediatelyActiveTimeWindow');
			this.immediatelyActiveEffectivePlanWindow.parent = this;
		}
		this.immediatelyActiveEffectivePlanWindow.effectivePlans = effectivePlans;
		return this.immediatelyActiveEffectivePlanWindow;
	},
	
	
	/**
     * 立即中止
     * 对于实际业务可能在当天发生两次以上的调整，故给予立即激中止功能用于支持该业务，
     * 1、立即中止功能给予特定角色来操作。根据所登陆的用户所属某某角色来决定是否给予立即中止功能。防止一般用户进行当天频繁调价操作
     * 2、立即中止功能中止日期可以等于今天但是不能小于今天。
     * 
     */
    immediatelyStopEffectivePlanWindow:null,
	getImmediatelyStopEffectivePlanWindow: function(effectivePlans){
		if(Ext.isEmpty(this.immediatelyStopEffectivePlanWindow)){
			this.immediatelyStopEffectivePlanWindow = Ext.create('Foss.pricing.effectiveExpressPlan.EffectivePlanImmediatelyStopEndTimeWindow');
			this.immediatelyStopEffectivePlanWindow.parent = this;
		}
		this.immediatelyStopEffectivePlanWindow.effectivePlans = effectivePlans;
		return this.immediatelyStopEffectivePlanWindow;
	},
	
	
	/**
	 * 立即中止
	 */
    immediatelyStopEffectivePlan:function(){
    	var me = this;
    	var effectivePlans = new Array();
	 	var selections = me.getSelectionModel().getSelection();
	 	if(selections.length < 1){
	 		pricing.showWoringMessage('请至少选择一条记录进行操作！');
			return;
	 	}
	 	for( var i=0;i<selections.length;i++){
	 		effectivePlans.push(selections[i].get('id'));
	 	}
	 	if(effectivePlans.length<1){
	 		pricing.showWoringMessage('请至少选择一条记录进行操作！');
	 		return;
	 	}
 		var immediatelyStopEffectivePlanWindow  = me.getImmediatelyStopEffectivePlanWindow(effectivePlans);
 		immediatelyStopEffectivePlanWindow.show();
	},
	
	/**
	 * 立即激活
	 */
    immediatelyActiveEffectivePlan:function(){
    	var me = this;
	 	var selections = me.getSelectionModel().getSelection();
	 	var effectivePlans = new Array();
	 	if(selections.length < 1){
	 		pricing.showWoringMessage('请选择至少一条记录进行操作！');
			return;
	 	}
		//对选中的数据进行过滤  只有未激活的数据才可以进行激活操作
	 	for(var i = 0;i<selections.length;i++){
			if(selections[i].get('active')==pricing.effectiveExpressPlan.YES){
				pricing.showWoringMessage(pricing.effectiveExpressPlan.i18n('foss.pricing.pleaseSelectNotActivateProgramDataForActivation'));
				return;
			}
			effectivePlans.push(selections[i].get('id'));
		}
		if(effectivePlans.length<1){
			pricing.showErrorMes(pricing.effectiveExpressPlan.i18n('foss.pricing.pleaseSelectLeastInactiveAgingProgram'));//请至少选择一条未激活的时效方案！
			return;
		}
	 	
 		var immediatelyActiveEffectivePlanWindow = me.getImmediatelyActiveEffectivePlanWindow(effectivePlans);
 		immediatelyActiveEffectivePlanWindow.show();

	},
	
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.columns = [{xtype: 'rownumberer',
			width:40,
			text : pricing.effectiveExpressPlan.i18n('i18n.pricingRegion.num')//序号
		},{
			text : pricing.effectiveExpressPlan.i18n('i18n.pricingRegion.opra'),//操作
			align : 'center',
			xtype : 'actioncolumn',
			items: [{
				iconCls: 'deppon_icons_edit',
                tooltip: pricing.effectiveExpressPlan.i18n('foss.pricing.update'),//修改
				width:42,
				getClass : function (v, m, r, rowIndex) {
					if (r.get('active') === 'N') {
					    return 'deppon_icons_edit';
					} else {
					    return 'statementBill_hide';
					}
				},
                handler: function(grid, rowIndex, colIndex) {
                	var id = grid.getStore().getAt(rowIndex).get('id');
    				var params = {'effectiveExpressPlanVo':{'effectivePlanEntity':{'id':id}}};
    				var successFun = function(json){
    					var updateWindow = me.getEffectivePlanUpdateWindow();
    					updateWindow.effectivePlanEntity = json.effectiveExpressPlanVo.effectivePlanEntity;
    					updateWindow.show();
    					updateWindow.getEffectivePlanDetailGridPanel().getStore().load();//加载明细数据
    				};
    				var failureFun = function(json){
    					if(Ext.isEmpty(json)){
    						pricing.showErrorMes(pricing.effectiveExpressPlan.i18n('foss.pricing.requestTimedOut'));//请求超时
    					}else{
    						pricing.showErrorMes(json.message);
    					}
    				};
    				var url = pricing.realPath('searchEffectiveExpressPlanById.action');
    				pricing.effectiveExpressPlan.requestJsonAjax(url,params,successFun,failureFun);
                }
			},{
				iconCls: 'deppon_icons_softwareUpgrade',
                tooltip: pricing.effectiveExpressPlan.i18n('foss.pricing.upgradedVersion'),//升级版本
                disabled: !pricing.effectiveExpressPlan.isPermission('effectiveExpressPlan/effectivePlanUpgradebutton'),
				width:42,
				getClass : function (v, m, r, rowIndex) {
					if (r.get('active') === 'Y') {
					    return 'deppon_icons_softwareUpgrade';
					} else {
					    return 'statementBill_hide';
					}
				},
                handler: function(grid, rowIndex, colIndex) {
                	var id = grid.getStore().getAt(rowIndex).get('id');
    				var params = {'effectiveExpressPlanVo':{'effectivePlanEntity':{'id':id}}};
    				var successFun = function(json){
    					grid.up().getPagingToolbar().moveFirst();
    					var updateWindow = me.getEffectivePlanUpdateWindow();
    					updateWindow.effectivePlanEntity = json.effectiveExpressPlanVo.effectivePlanEntity;
    					updateWindow.show();
    					updateWindow.getEffectivePlanDetailGridPanel().getStore().load();//加载明细数据
    				};
    				var failureFun = function(json){
    					if(Ext.isEmpty(json)){
    						pricing.showErrorMes(pricing.effectiveExpressPlan.i18n('foss.pricing.requestTimedOut'));//请求超时
    					}else{
    						pricing.showErrorMes(json.message);
    					}
    				};
    				var url = pricing.realPath('copyEffectiveExpressPlan.action');
    				pricing.effectiveExpressPlan.requestJsonAjax(url,params,successFun,failureFun);
                }
			},{
				iconCls: 'deppon_icons_showdetail',
                tooltip: pricing.effectiveExpressPlan.i18n('foss.pricing.details'),//查看详情
                disabled: !pricing.effectiveExpressPlan.isPermission('effectiveExpressPlan/effectivePlanDetailbutton'),
				width:42,
                handler: function(grid, rowIndex, colIndex) {
                	//获取选中的数据
    				var record=grid.getStore().getAt(rowIndex);
                	me.getEffectivePlanDetailShowWindow().show();
                	me.getEffectivePlanDetailShowWindow().effectivePlanId = record.get('id');
                }
			},{
				iconCls:'deppon_icons_end',
				tooltip: pricing.effectiveExpressPlan.i18n('foss.pricing.stop'), 
				disabled: !pricing.effectiveExpressPlan.isPermission('effectiveExpressPlan/effectivePlanStopbutton'),
				width:42,
				handler: function(grid, rowIndex, colIndex){
					//得到当前行
					var record = grid.getStore().getAt(rowIndex);
					//得到当前行中的具体字段
					var pricePlanId = record.get('id');
					var stoptEffectivePlanWindow = me.getStopEffectivePlanWindow(pricePlanId);
					stoptEffectivePlanWindow.show();
				}
			}]
		},{
			text : pricing.effectiveExpressPlan.i18n('foss.pricing.scenarioName'),//方案名称
			dataIndex : 'name'
		},{
			text : pricing.effectiveExpressPlan.i18n('foss.pricing.originatingArea'),//始发区域
			dataIndex : 'deptRegionName'
		},{
			text : pricing.effectiveExpressPlan.i18n('i18n.pricingRegion.lastCreateTime'),//最后修改时间
			dataIndex : 'modifyDate',
			width:140,
			renderer:function(value){
				return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
			}
		},{
			text : pricing.effectiveExpressPlan.i18n('i18n.pricingRegion.lastCreateUser'),//最后修改人
			dataIndex : 'modifyUserName'
		},{
			text : pricing.effectiveExpressPlan.i18n('foss.pricing.availabilityDate'),//生效日期
			dataIndex : 'beginTime',
			width:140,
			renderer:function(value){
				return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
			}
		},{
			text :pricing.effectiveExpressPlan.i18n('foss.pricing.deadline'),//截止日期
			dataIndex : 'endTime',
			width:140,
			renderer:function(value){
				return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
			}
		},{
			text : pricing.effectiveExpressPlan.i18n('foss.pricing.programStatus'),//是否激活（方案状态）
			dataIndex : 'active',
			renderer:function(value){
				if(value=='Y'){
					return pricing.effectiveExpressPlan.i18n('i18n.pricingRegion.active');
				}else if(value=='N'){
					return  pricing.effectiveExpressPlan.i18n('i18n.pricingRegion.unActive');
				}else{
					return '';
				}
			}
		},{
			text : '是否当前版本',
			dataIndex : 'currentUsedVersion'
		}];
		me.store = Ext.create('Foss.pricing.effectiveExpressPlan.EffectiveExpressPlanStore',{
			autoLoad : false,
			pageSize : 20,
			listeners: {
				beforeload: function(store, operation, eOpts){
					var queryForm = Ext.getCmp('T_pricing-effectiveExpressPlan_content').getQueryEffectivePlanForm();
					if(queryForm!=null){
						Ext.apply(operation,{
							params : {
								'effectiveExpressPlanVo.effectivePlanEntity.name' : queryForm.getForm().findField('name').getValue(),//方案名称
								'effectiveExpressPlanVo.effectivePlanEntity.active' : queryForm.getForm().findField('active').getValue(),//状态
								'effectiveExpressPlanVo.effectivePlanEntity.deptRegionCode' : queryForm.getForm().findField('deptRegionCode').getValue(),//始发区域编码
								'effectiveExpressPlanVo.effectivePlanEntity.currentUsedVersion':queryForm.getForm().findField('currentUsedVersion').getValue()//是否当前版本
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
		me.tbar = [{
			text : pricing.effectiveExpressPlan.i18n('foss.pricing.import'),//导入
			disabled: !pricing.effectiveExpressPlan.isPermission('effectiveExpressPlan/effectivePlanImportbutton'),
			hidden: !pricing.effectiveExpressPlan.isPermission('effectiveExpressPlan/effectivePlanImportbutton'),
			handler :function(){
				me.getUploadEffectivePlan().show();
			} 
		},'-',{
			text : pricing.effectiveExpressPlan.i18n('foss.pricing.export'),//导出
			disabled: !pricing.effectiveExpressPlan.isPermission('effectiveExpressPlan/effectivePlanExportbutton'),
			hidden: !pricing.effectiveExpressPlan.isPermission('effectiveExpressPlan/effectivePlanExportbutton'),
			handler :function(){
				var queryForm = Ext.getCmp('T_pricing-effectiveExpressPlan_content').getQueryEffectivePlanForm();
				var effectivePlanExport = '';
				var name = queryForm.getForm().findField('name').getValue(); // 方案名称
				var deptRegionCode = queryForm.getForm().findField('deptRegionCode').getValue(); //始发区域编码
				var active = queryForm.getForm().findField('active').getValue(); //状态
				
//				effectivePlanExport ='effectiveExpressPlanVo.effectivePlanEntity.active='+active;
//				if(!Ext.isEmpty(name)){
//					effectivePlanExport = effectivePlanExport+'&'+'effectiveExpressPlanVo.effectivePlanEntity.name='+name;
//				}
//				if(!Ext.isEmpty(deptRegionCode)){
//					if(!Ext.isEmpty(effectivePlanExport)){
//						effectivePlanExport = effectivePlanExport+'&'+'effectiveExpressPlanVo.effectivePlanEntity.deptRegionCode='+deptRegionCode;
//					}else{
//						effectivePlanExport = 'effectiveExpressPlanVo.effectivePlanEntity.deptRegionCode='+deptRegionCode;
//					}
//				}
				var selections = me.getSelectionModel().getSelection();
			 	if(selections.length < 1){
			 		pricing.showWoringMessage('请选择一条记录进行操作！');
					return;
			 	}
			 	if(selections.length > 1){
			 		pricing.showWoringMessage('请选择一条记录进行操作！');
					return;
			 	}
			 	var id = selections[0].data.id;
			 	if(!Ext.isEmpty(id)){
			 		if(!Ext.isEmpty(effectivePlanExport)){
						effectivePlanExport = effectivePlanExport+'&'+'effectiveExpressPlanVo.effectivePlanDetailEntity.effectivePlanId='+id;
					}else{
						effectivePlanExport = 'effectiveExpressPlanVo.effectivePlanDetailEntity.effectivePlanId='+id;
					}
			 	}
				var url = pricing.realPath('exportEffectiveExpressPlan.action');
				if(!Ext.isEmpty(effectivePlanExport)){
					url = url+'?'+effectivePlanExport;
				}
				window.location=url;
				effectivePlanExport = '';
			} 
		},'-', {
			text : pricing.effectiveExpressPlan.i18n('foss.pricing.newProgram'),//新增方案
			disabled: !pricing.effectiveExpressPlan.isPermission('effectiveExpressPlan/effectivePlanAddbutton'),
			hidden: !pricing.effectiveExpressPlan.isPermission('effectiveExpressPlan/effectivePlanAddbutton'),
			handler :function(){
				me.getEffectivePlanAddWindow().show()
			} 
		},'-', {
			text : pricing.effectiveExpressPlan.i18n('foss.pricing.activationProgram'),//激活方案
			disabled: !pricing.effectiveExpressPlan.isPermission('effectiveExpressPlan/effectivePlanActivebutton'),
			hidden: !pricing.effectiveExpressPlan.isPermission('effectiveExpressPlan/effectivePlanActivebutton'),
			handler :function(){
				me.activeEffectivePlan();
			} 
		},'-', {
			text : pricing.effectiveExpressPlan.i18n('foss.pricing.deleteProgram'),//删除方案
			disabled: !pricing.effectiveExpressPlan.isPermission('effectiveExpressPlan/effectivePlanDeletebutton'),
			hidden: !pricing.effectiveExpressPlan.isPermission('effectiveExpressPlan/effectivePlanDeletebutton'),
			handler :function(){
				me.deleteEffectivePlan();
			} 
		},'-',{
			text : pricing.effectiveExpressPlan.i18n('foss.pricing.immediatelyActivationProgram'),//'立即激活',
			disabled:!pricing.effectiveExpressPlan.isPermission('effectiveExpressPlan/effectivePlanImmediatelyActivebutton'),
			hidden:!pricing.effectiveExpressPlan.isPermission('effectiveExpressPlan/effectivePlanImmediatelyActivebutton'),
			handler :function(){
				me.immediatelyActiveEffectivePlan();
			} 
		},'-', {
			text : pricing.effectiveExpressPlan.i18n('foss.pricing.immediatelyStopProgram'),//'立即中止',
			disabled:!pricing.effectiveExpressPlan.isPermission('effectiveExpressPlan/effectivePlanImmediatelyStopbutton'),
			hidden:!pricing.effectiveExpressPlan.isPermission('effectiveExpressPlan/effectivePlanImmediatelyStopbutton'),
			handler :function(){
				me.immediatelyStopEffectivePlan();
			} 
		}];
		me.bbar = me.getPagingToolbar();
		pricing.pagingBar = me.bbar;
		me.callParent([cfg]);
	}
});



/**
 * 价格中止方案弹出框
 */
Ext.define('Foss.pricing.effectiveExpressPlan.EffectivePlanStopEndTimeWindow',{
		extend: 'Ext.window.Window',
		closeAction: 'hide',
		title: "中止时效方案",
		width:380,
		height:120,
		effectivePlanId:null,
		listeners:{
			beforehide:function(me){
				var form = me.down('form');
				form.getForm().reset();
			}
		},		
	    //中止
		effectivePlanStopFormPanel:null,
		getEffectivePlanStopFormPanel : function(){
	    	if(Ext.isEmpty(this.effectivePlanStopFormPanel)){
	    		this.effectivePlanStopFormPanel = Ext.create('Foss.pricing.effectiveExpressPlan.EffectivePlanStopFormPanel');
	    	}
	    	return this.effectivePlanStopFormPanel;
	    },
	    
	   	initComponent : function() {
			var me = this;
			me.items = [me.getEffectivePlanStopFormPanel()];//设置window的元素
			this.callParent(arguments);  
		}
		
		
});

/**
 * 中止功能window
 */
Ext.define('Foss.pricing.effectiveExpressPlan.EffectivePlanStopFormPanel',{
	extend : 'Ext.form.Panel',
	layout:'column',
	width:375,
	height:100,
	//中止方案
	stopPricePlan:function(effectivePlanId){
		var me = this;
    	//获取表单
    	var form = me.getForm();
    	if(form.isValid()){
			effectivePlanModel = new Foss.pricing.effectiveExpressPlan.EffectiveExpressPlanEntity();
			form.updateRecord(effectivePlanModel);
    		effectivePlanModel.set('id',effectivePlanId);
    		effectivePlanModel.set('endTime',Ext.Date.parse(form.findField('endTime').getValue(), 'Y-m-d H:i:s'));    		
    		var params = {'effectiveExpressPlanVo':{'effectivePlanEntity':effectivePlanModel.data}};
    		
    		//ajax请求
    		var url = pricing.realPath('stopEffectiveExpressPlan.action');
    		
    		//成功提示
    		var successFun = function(json){
    			pricing.showInfoMes(json.message);
    			var grid = Ext.getCmp('T_pricing-effectiveExpressPlan_content').getEffectivePlanGridPanel();
				grid.getPagingToolbar().moveFirst();
    			me.up('window').hide();
    	    };
    	    
    	    //失败提示
    	    var failureFun = function(json){
    	    	if(Ext.isEmpty(json)){
    				pricing.showErrorMes(pricing.effectiveExpressPlan.i18n('i18n.pricingRegion.requestTimeOut'));
    			}else{
    				pricing.showErrorMes(json.message);
    			}
    	    };
    		
    	    //调用ajax请求
    		pricing.effectiveExpressPlan.requestJsonAjax(url,params,successFun,failureFun);//发送AJAX请求
    	}
	},
	
	initComponent : function() {
		var me = this;
		me.items = [
			{
				xtype:'datetimefield_date97',
				fieldLabel:pricing.effectiveExpressPlan.i18n('foss.pricing.deadline'),//截止日期
				editable:false,
				time : true,
				name:'endTime',
				allowBlank:false,
				id : 'Foss_effectiveExpressPlan_suspendEndTime_ID',
				dateConfig: {
					el : 'Foss_effectiveExpressPlan_suspendEndTime_ID-inputEl'
				}
			},{
				xtype : 'container',
				margin : '0 0 2 0',
				items : [{
					xtype : 'button', 
					width:70,
					text : "中止",
					handler : function() {
						var effectivePlanId = me.up('window').effectivePlanId;
						me.stopPricePlan(effectivePlanId);
					}
				}]
			}
		];//设置window的元素
		me.callParent();
	}
});


/**
 * @description 时效方案主页
 */
Ext.onReady(function() {
	Ext.QuickTips.init();
	if (Ext.getCmp('T_pricing-effectiveExpressPlan_content')) {
		return;
	};
	pricing.effectiveExpressPlan.searchThreeLevelProduct();//获取三级产品
	var queryEffectivePlanForm = Ext.create('Foss.pricing.effectiveExpressPlan.QueryEffectiveExpressPlanForm');//查询FORM
	var effectivePlanGridPanel = Ext.create('Foss.pricing.effectiveExpressPlan.EffectiveExpressPlanGridPanel');//查询结果GRID
	Ext.getCmp('T_pricing-effectiveExpressPlan').add(Ext.create('Ext.panel.Panel', {
	  	 id : 'T_pricing-effectiveExpressPlan_content',
		cls : 'panelContentNToolbar',
		bodyCls : 'panelContentNToolbar-body',
		//获得查询FORM
		getQueryEffectivePlanForm : function() {
			return queryEffectivePlanForm;
		},
		//获得查询结果GRID
		getEffectivePlanGridPanel : function() {
			if(Ext.isEmpty(this.effectivePlanGridPanel)){
				this.effectivePlanGridPanel = Ext.create('Foss.pricing.effectiveExpressPlan.EffectiveExpressPlanGridPanel');//查询结果GRID
			}
			return effectivePlanGridPanel;
		},
		items : [ queryEffectivePlanForm, effectivePlanGridPanel]
	}));
});
//----------------------------------------------上面是整体布局，下面是弹出窗口----------------------------------
/**
 * 时效方案新增弹窗
 */
Ext.define('Foss.pricing.effectiveExpressPlan.EffectiveExpressPlanAddWindow',{
	extend : 'Ext.window.Window',
	title : pricing.effectiveExpressPlan.i18n('i18n.pricingRegion.deptDetailed'),
	closable : true,
	modal : true,
	parent:null,//(Foss.pricing.effectiveExpressPlan.EffectiveExpressPlanGridPanel)
	effectivePlanEntity:null,//时效方案实体
	resizable:false,
	closeAction : 'hide',
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	width :700,
	height :600,
	listeners:{
		beforehide:function(me){
			me.getEffectivePlanAddForm().getForm().reset();//表格重置
			me.getEffectivePlanDetailGridPanel().getStore().removeAll();
    		me.getEffectivePlanDetailGridPanel().getDockedItems()[1].items.items[0].setDisabled(true);//grid的新增按钮不可用
    		me.getEffectivePlanDetailGridPanel().getDockedItems()[1].items.items[2].setDisabled(true);//grid的删除按钮不可用
    		me.getEffectivePlanAddForm().getDockedItems()[1].items.items[1].setDisabled(false);//form的重置按钮可用
    		me.getEffectivePlanAddForm().getDockedItems()[1].items.items[2].setDisabled(false);//form的保存按钮可用
    		me.getEffectivePlanAddForm().getForm().getFields().each(function(item){
    			item.setReadOnly(false);
    		});
		},
		beforeshow:function(me){
			
		}
	},
	//时效方案详情GRID（目的地信息）
	effectivePlanDetailGridPanel:null,
    getEffectivePlanDetailGridPanel : function(){
    	if(Ext.isEmpty(this.effectivePlanDetailGridPanel)){
    		this.effectivePlanDetailGridPanel = Ext.create('Foss.pricing.effectiveExpressPlan.EffectivePlanDetailGridPanel',{
    			'isShow':false
    		});
    		this.effectivePlanDetailGridPanel.getDockedItems()[0].items.items[0].setDisabled(true);//grid的新增按钮不可用
    		this.effectivePlanDetailGridPanel.getDockedItems()[0].items.items[2].setDisabled(true);//grid的删除按钮不可用
    	}
    	return this.effectivePlanDetailGridPanel;
    },
    //时效方案FORM（出发地信息）
    effectivePlanAddForm:null,
    getEffectivePlanAddForm : function(){
    	if(Ext.isEmpty(this.effectivePlanAddForm)){
    		this.effectivePlanAddForm = Ext.create('Foss.pricing.effectiveExpressPlan.EffectivePlanAddForm',{
    			'isUpdate':false
    		});
    	}
    	return this.effectivePlanAddForm;
    },
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [ me.getEffectivePlanAddForm(),me.getEffectivePlanDetailGridPanel()];
		me.callParent([cfg]);
	}
});

/**
 * 时效方案修改弹窗
 */
Ext.define('Foss.pricing.effectiveExpressPlan.EffectiveExpressPlanUpdateWindow',{
	extend : 'Ext.window.Window',
	title : pricing.effectiveExpressPlan.i18n('foss.pricing.modifyAgingProgram'),
	closable : true,
	modal : true,
	effectivePlanEntity:null,//时效方案实体
	parent:null,//(Foss.pricing.effectiveExpressPlan.EffectiveExpressPlanGridPanel)
	resizable:false,
	closeAction : 'hide',
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	width :700,
	height :600,
	listeners:{
		beforehide:function(me){
			me.getEffectivePlanAddForm().getForm().reset();//表格重置
			me.getEffectivePlanAddForm().getForm().getFields().each(function(item){
				item.setReadOnly(false);
			});
			me.getEffectivePlanDetailGridPanel().getStore().removeAll();
		},
		beforeshow:function(me){
			me.getEffectivePlanAddForm().getForm().loadRecord(new Foss.pricing.effectiveExpressPlan.EffectiveExpressPlanEntity(me.effectivePlanEntity));
			me.getEffectivePlanAddForm().getForm().findField('deptRegionCode').setCombValue(me.effectivePlanEntity.deptRegionName,me.effectivePlanEntity.deptRegionCode);
			me.getEffectivePlanAddForm().getForm().findField('deptRegionCode').deptRegionId =  me.effectivePlanEntity.deptRegionId
		}
	},
	//时效方案详情GRID（目的地信息）
	effectivePlanDetailGridPanel:null,
    getEffectivePlanDetailGridPanel : function(){
    	if(Ext.isEmpty(this.effectivePlanDetailGridPanel)){
    		this.effectivePlanDetailGridPanel = Ext.create('Foss.pricing.effectiveExpressPlan.EffectivePlanDetailGridPanel');
    	}
    	return this.effectivePlanDetailGridPanel;
    },
    //时效方案FORM（出发地信息）
    effectivePlanAddForm:null,
    getEffectivePlanAddForm : function(){
    	if(Ext.isEmpty(this.effectivePlanAddForm)){
    		this.effectivePlanAddForm = Ext.create('Foss.pricing.effectiveExpressPlan.EffectivePlanAddForm');
    	}
    	return this.effectivePlanAddForm;
    },
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
	    
		/* 修改form */
	    var effectivePlanAddForm = me.getEffectivePlanAddForm();
	    effectivePlanAddForm.effectivePlanEntity = me.effectivePlanEntity;
	    effectivePlanAddForm.isUpdate = true;
	    
	    /*修改detai form*/
	    var effectivePlanDetailGridPanel = me.getEffectivePlanDetailGridPanel();
	    effectivePlanDetailGridPanel.isShow = false;
		me.items = [effectivePlanAddForm,effectivePlanDetailGridPanel];
		me.callParent([cfg]);
	}
});
/**
 * 出发地信息
 */
Ext.define('Foss.pricing.effectiveExpressPlan.EffectivePlanAddForm', {
	extend : 'Ext.form.Panel',
	title: pricing.effectiveExpressPlan.i18n('foss.pricing.departureinformation'),
	frame: true,
	effectivePlanEntity:null,
	collapsible: true,
    defaults : {
    	colspan : 1,
    	margin : '5 5 5 5'
    },
    height :250,
	defaultType : 'textfield',
	layout: {
        type: 'table',
        columns: 2
    },
    //提交时效方案
    commitEffectivePlan:function(){
    	var me = this;
    	var form = me.getForm(); //时效方案FORM（出发地信息）
    	if(form.isValid()){
    		var effectivePlanModel = null;
    		if(me.isUpdate){
        		effectivePlanModel = new Foss.pricing.effectiveExpressPlan.EffectiveExpressPlanEntity(me.up('window').effectivePlanEntity);
    		}else{
    			effectivePlanModel = new Foss.pricing.effectiveExpressPlan.EffectiveExpressPlanEntity();
    		}
    		form.updateRecord(effectivePlanModel);
    		effectivePlanModel.set('deptRegionId',form.findField('deptRegionCode').deptRegionId);// 获取始发区域的ID@TODO
    		effectivePlanModel.set('deptRegionCode',form.findField('deptRegionCode').getValue());// 获取始发区域的ID
    		effectivePlanModel.set('deptRegionName',form.findField('deptRegionCode').deptRegionName);// 获取始发区域的名称
    		effectivePlanModel.set('updateDesTemp',form.findField('description').getValue());// 获取方案描述
    		var params = {'effectiveExpressPlanVo':{'effectivePlanEntity':effectivePlanModel.data}};
    		var successFun = function(json){
    			pricing.showInfoMes(json.message);//提示新增成功
				if(!me.isUpdate){//修改了之后还要继续修改，新增之后不可以修改了
		    		me.up('window').getEffectivePlanDetailGridPanel().getDockedItems()[1].items.items[0].setDisabled(false);//grid的新增按钮可用
		    		me.up('window').getEffectivePlanDetailGridPanel().getDockedItems()[1].items.items[2].setDisabled(false);//grid的删除按钮可用
		    		me.getDockedItems()[1].items.items[1].setDisabled(true);//form的重置按钮不可用
		    		me.getDockedItems()[1].items.items[2].setDisabled(true);//form的保存按钮不可用
		    		me.getForm().getFields().each(function(item){
		    			item.setReadOnly(true);
		    		});
				}
				me.up('window').effectivePlanEntity = json.effectiveExpressPlanVo.effectivePlanEntity;//返回数据
				//me.up('window').getFlightPriceDetailGrid().getStore().load();(保存时效方案主信息之后不需要查询的)
				me.up('window').parent.getPagingToolbar().moveFirst();//成功之后重新查询刷新结果集
    	    };
    	    var failureFun = function(json){
    	    	if(Ext.isEmpty(json)){
    				pricing.showErrorMes(pricing.effectiveExpressPlan.i18n('i18n.pricingRegion.requestTimeOut'));
    			}else{
    				pricing.showErrorMes(json.message);
    			}
    	    };
    	    var url = null;
			if(me.isUpdate){
				url = pricing.realPath('updateEffectiveExpressPlanEntity.action');//请求时效方案修改
    		}else{
    			url = pricing.realPath('addEffectiveExpressPlanEntity.action');//请求时效方案新增
    		}
    		pricing.effectiveExpressPlan.requestJsonAjax(url,params,successFun,failureFun);//发送AJAX请求
    	}
    },
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [{
			name: 'name',
			allowBlank:false,
	        fieldLabel:pricing.effectiveExpressPlan.i18n('foss.pricing.scenarioName'),//方案名称
			maxLength : 15,
	        xtype : 'textfield'
		},{
			name: 'deptRegionCode',
			allowBlank:false,
	        fieldLabel:pricing.effectiveExpressPlan.i18n('foss.pricing.originatingArea'),//始发区域
	        xtype : 'commonperscriptionregionexpressselector',
	        deptRegionId: null, //定义始发区域ID
	        deptRegionName:null,//定义始发区域名称
	        listeners:{
	        	select:function(comb,records,objs){
	        		var record = records[0];
	        		var id = record.get('id');
	        		var name = record.get('regionName');
	        		comb.deptRegionId = id;
	        		comb.deptRegionName = name;
	        	}
	        }
		},{
			xtype:'datefield',
			allowBlank:false,
			fieldLabel:pricing.effectiveExpressPlan.i18n('foss.pricing.availabilityDate'),//生效日期
			format:'Y-m-d',
			name:'beginTime'
		},{
			xtype:'label'
		},{
			name: 'description',
			colspan : 2,
			width:500,
			maxLength : 200,
	        fieldLabel:pricing.effectiveExpressPlan.i18n('foss.pricing.programDescription'),//方案描述
	        xtype : 'textareafield'
		}];
		me.fbar = [{
			text :pricing.effectiveExpressPlan.i18n('i18n.pricingRegion.cancel'),//取消
			handler :function(){
				me.up('window').close();
			} 
		},{
			text :pricing.effectiveExpressPlan.i18n('foss.pricing.reset'),//重置
			margin:'0 395 0 0',
			handler :function(){
				if(Ext.isEmpty(me.up('window').effectivePlanEntity)){
					me.getForm().loadRecord(new Foss.pricing.effectiveExpressPlan.EffectiveExpressPlanEntity());
				}else{
					me.getForm().loadRecord(new Foss.pricing.effectiveExpressPlan.EffectiveExpressPlanEntity(me.up('window').effectivePlanEntity));
				}
			} 
		},{
			text :pricing.effectiveExpressPlan.i18n('i18n.pricingRegion.commit'),//提交
			cls:'yellow_button',
			handler :function(){
				me.commitEffectivePlan();
			} 
		}];
		me.callParent([cfg]);
	}
});
/**
 * 时效区域详情（目的地信息）
 */
Ext.define('Foss.pricing.effectiveExpressPlan.EffectivePlanDetailGridPanel', {
	extend: 'Ext.grid.Panel',
	title : pricing.effectiveExpressPlan.i18n('foss.pricing.destinationInformation'),//目的地信息
	frame: true,
	flex:1,
	autoScroll:true,
    sortableColumns:false,
    enableColumnHide:false,
    enableColumnMove:false,
	stripeRows : true, // 交替行效果
	selType : "rowmodel", // 选择类型设置为：行选择
	//删除时效方案运价明细
	deleteEffectivePlanDetail: function(){
		var me = this;
		var selections = me.getSelectionModel().getSelection();//获取选中的数据
		if(selections.length<1){//判断是否至少选中了一条
			pricing.showWoringMessage(pricing.effectiveExpressPlan.i18n('foss.pricing.pleaseSelectOneDeleteOperation'));//请选择一条进行删除操作！
			return;//没有则提示并返回
		}
		pricing.showQuestionMes(pricing.effectiveExpressPlan.i18n('foss.pricing.wantDeleteTheseAgingProgramDetails'),function(e){//是否要删除这些时效方案明细？
			if(e=='yes'){//询问是否删除，是则发送请求
				var idList = new Array();//航空公司运价
				for(var i = 0 ; i<selections.length ; i++){
					idList.push(selections[i].get('id'));
				}
				var params = {'effectiveExpressPlanVo':{'effectivePlans':idList}};
				var successFun = function(json){
					pricing.showInfoMes(json.message);
					me.getPagingToolbar().moveFirst();
				};
				var failureFun = function(json){
					if(Ext.isEmpty(json)){
						pricing.showErrorMes(pricing.effectiveExpressPlan.i18n('foss.pricing.requestTimedOut'));//请求超时
					}else{
						pricing.showErrorMes(json.message);
					}
				};
				var url = pricing.realPath('deleteEffectiveExpressPlanDetailEntity.action');
				pricing.effectiveExpressPlan.requestJsonAjax(url,params,successFun,failureFun);
			}
		})
	},
	//新增方案详情
	effectivePlanDetailWindow:null,
    getEffectivePlanDetailWindow : function(){
    	if(Ext.isEmpty(this.effectivePlanDetailWindow)){
    		this.effectivePlanDetailWindow = Ext.create('Foss.pricing.effectiveExpressPlan.EffectivePlanDetailWindow');
    		this.effectivePlanDetailWindow.parent = this;
    	}
    	return this.effectivePlanDetailWindow;
    },
     //修改方案详情
    effectivePlanDetailUpdateWindow:null,
    getEffectivePlanDetailUpdateWindow : function(){
    	if(Ext.isEmpty(this.effectivePlanDetailUpdateWindow)){
    		this.effectivePlanDetailUpdateWindow = Ext.create('Foss.pricing.effectiveExpressPlan.EffectivePlanDetailUpdateWindow');
    		this.effectivePlanDetailUpdateWindow.parent = this;
    	}
    	return this.effectivePlanDetailUpdateWindow;
    },
	//返回chekbox
	checkboxModel:null,
	getCheckboxModel:function(){
		if(Ext.isEmpty(this.checkboxModel)){
			this.checkboxModel = Ext.create('Ext.selection.CheckboxModel');	
		}
		return this.checkboxModel;
	},
	//得到bbar
	pagingToolbar : null,
	getPagingToolbar : function() {
		if (this.pagingToolbar == null) {
			this.pagingToolbar = Ext.create('Deppon.StandardPaging', {
				store : this.store,
				pageSize : 5
			});
		}
		return this.pagingToolbar;
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.columns  = [{xtype: 'rownumberer',
			width:40,
			text : pricing.effectiveExpressPlan.i18n('i18n.pricingRegion.num')//序号
		},{
			text : pricing.effectiveExpressPlan.i18n('i18n.pricingRegion.opra'),//操作
			align : 'center',
			xtype : 'actioncolumn',
			items: [{
				iconCls: 'deppon_icons_edit',
	            tooltip: pricing.effectiveExpressPlan.i18n('foss.pricing.update'),//修改
				width:42,
	            handler: function(grid, rowIndex, colIndex) {
	            	var id = grid.getStore().getAt(rowIndex).get('id');
					var params = {'effectiveExpressPlanVo':{'effectivePlanDetailEntity':{'id':id}}};
					var successFun = function(json){
						var updateWindow = me.getEffectivePlanDetailUpdateWindow();
						updateWindow.effectivePlanDetailEntity = json.effectiveExpressPlanVo.effectivePlanDetailEntity;
						updateWindow.show();
					};
					var failureFun = function(json){
						if(Ext.isEmpty(json)){
							pricing.showErrorMes(pricing.effectiveExpressPlan.i18n('foss.pricing.requestTimedOut'));//请求超时
						}else{
							pricing.showErrorMes(json.message);
						}
					};
					var url = pricing.realPath('searchEffectiveExpressDetailPlanById.action');
					pricing.effectiveExpressPlan.requestJsonAjax(url,params,successFun,failureFun);
	            }
			}]
		},{
			text :pricing.effectiveExpressPlan.i18n('foss.pricing.destinationArea'),//目的区域
			dataIndex : 'arrvRegionName',
			flex:2
		},{
			text :pricing.effectiveExpressPlan.i18n('foss.pricing.basicProducts'),//基础产品
			dataIndex : 'productName',
			flex:2
		},{
			text :pricing.effectiveExpressPlan.i18n('foss.pricing.commitmentMinimumNumberDaysHours'),//承诺最小天数或小时 ,
			dataIndex : 'minTime',
			flex:2,
			renderer:function(value,obj,record){
				var showValue = value;
				if(record.get('minTimeUnit')==pricing.effectiveExpressPlan.DAY){
					showValue = showValue+pricing.effectiveExpressPlan.i18n('foss.pricing.day');//天
				}else if(record.get('minTimeUnit')==pricing.effectiveExpressPlan.HOURS){
					showValue = showValue+pricing.effectiveExpressPlan.i18n('foss.pricing.hour');//小时
				}
				return showValue;
			}
		},{
			text :pricing.effectiveExpressPlan.i18n('foss.pricing.commitmentMaximumNumberDaysHours'),//承诺最大天数或小时 , ,
			dataIndex : 'maxTime',
			flex:2,
			renderer:function(value,obj,record){
				var showValue = value;
				if(record.get('maxTimeUnit')==pricing.effectiveExpressPlan.DAY){
					showValue = showValue+pricing.effectiveExpressPlan.i18n('foss.pricing.day');//天
				}else if(record.get('maxTimeUnit')==pricing.effectiveExpressPlan.HOURS){
					showValue = showValue+pricing.effectiveExpressPlan.i18n('foss.pricing.hour');//小时
				}
				return showValue;
			}
		},{
			text : pricing.effectiveExpressPlan.i18n('foss.pricing.commitmentPointTimeReachSalesDepartment'),//到达营业部承诺时点,
			dataIndex : 'arriveTime',
			flex:2
		},{
			text : pricing.effectiveExpressPlan.i18n('foss.pricing.whetherTheResidentSecto'),//是否驻地部门
			dataIndex : 'hasSalesDept',
			flex:2,
			renderer:function(value){
				if(value=='Y'){
					return pricing.effectiveExpressPlan.i18n('i18n.pricingRegion.ye');
				}else if(value=='N'){
					return pricing.effectiveExpressPlan.i18n('i18n.pricingRegion.no');
				}else{
					return;
				}	
			}
		},{
			text : pricing.effectiveExpressPlan.i18n('foss.pricing.shortAndLongDistance'),//长短途
			dataIndex : 'longOrShort',
			flex:2,
			renderer:function(value){
				if(value==pricing.effectiveExpressPlan.LONG){
					return pricing.effectiveExpressPlan.i18n('foss.pricing.longDistance');
				}else if(value==pricing.effectiveExpressPlan.SHORT){
					return pricing.effectiveExpressPlan.i18n('foss.pricing.shortDistance');
				}
			}
		},{
			text : pricing.effectiveExpressPlan.i18n('foss.pricing.deliveryCommitmentsNeedAddNumberDays'),//派送承诺需加天数
			dataIndex : 'addDay',
			flex:2
		},{
				text : pricing.effectiveExpressPlan.i18n('foss.pricing.deliveryCommitmentTimePoints'),//派送承诺时点,
				dataIndex : 'deliveryTime',
				flex:2
		},{
			text : pricing.effectiveExpressPlan.i18n('foss.pricing.remark'),//备注
			dataIndex : 'description',
			flex:2
		}];
		me.store = Ext.create('Foss.pricing.effectiveExpressPlan.EffectivePlanDetailStore',{
			autoLoad : false,//不自动加载
			pageSize : 5,
			listeners: {
				beforeload: function(store, operation, eOpts){
					Ext.apply(operation,{
						params : {//航空公司运价明细查询
							'effectiveExpressPlanVo.effectivePlanDetailEntity.effectivePlanId':me.up('window').effectivePlanEntity.id//时效方案ID
						}
					});	
				}
		    }			
		});
		me.selModel = me.getCheckboxModel();
		me.tbar = [{
            text: pricing.effectiveExpressPlan.i18n('i18n.pricingRegion.add'),
            handler:function(){ 
            	me.getEffectivePlanDetailWindow().show();
            }
        },'-',{
            text: pricing.effectiveExpressPlan.i18n('foss.pricing.delete'),
            handler:function(){
            	me.deleteEffectivePlanDetail();
            }
        }];
		me.listeners = {
	    	scrollershow: function(scroller) {
	    		if (scroller && scroller.scrollEl) {
	    				scroller.clearManagedListeners(); 
	    				scroller.mon(scroller.scrollEl, 'scroll', scroller.onElScroll, scroller); 
	    		}
	    	}
	    },
	    me.bbar = me.getPagingToolbar();
		me.callParent([cfg]);
	}
});
//------------------------------新增修改--------------------------------------------------------------------






/**
 * 明细信息新增WINDOW
 */
Ext.define('Foss.pricing.effectiveExpressPlan.EffectivePlanDetailWindow',{
	extend : 'Ext.window.Window',
	title: pricing.effectiveExpressPlan.i18n('foss.pricing.aBreakdownOfNew'),//明细信息新增
	closable : true,
	modal : true,
	resizable:false,
	closeAction : 'hide',
	parent:null,//(Foss.pricing.effectiveExpressPlan.EffectivePlanDetailGridPanel)
	width :575,
	height :600,
	deptEditGrid : null,//显示区域关联部门明细表
	editForm:null,//区域新增修改的表单
	listeners:{
		beforehide:function(me){
			//清楚上次使用的数据
			me.getEffectivePlanDetailForm().getForm().reset();
		},
		beforeshow:function(me){
			
		}
	},
    //提交数据
    commintEffectivePlanDetail:function(){
    	var me = this;
    	var form = me.getEffectivePlanDetailForm().getForm();
    	if(form.isValid()){//表单校验
    	    //提交时效方案
	    	var me = this;
	    	var effectivePlanDetailModel = new Foss.pricing.effectiveExpressPlan.EffectiveExpressPlanDetailEntity();
    		form.updateRecord(effectivePlanDetailModel);
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
    			pricing.showErrorMes(pricing.effectiveExpressPlan.i18n('foss.pricing.inconsistentUnitTimeSetTimDayNumberSelectConsistent'));
    			return;
    		}
			var maxTimeTemp = parseInt(maxTime); 
			var minTimeTemp = parseInt(minTime); 
			
    		if(maxTimeTemp<minTimeTemp){
    			pricing.showErrorMes(pricing.effectiveExpressPlan.i18n('foss.pricing.commitmentCanNotLessThanMaximumCommitmentMinimumTime'));
    			return;
    		}
    		var deliveryTime = form.findField('deliveryTime').getRawValue().replace(':','') ;
    		var arriveTime = form.findField('arriveTime').getRawValue().replace(':','');
    		effectivePlanDetailModel.set('deliveryTime',deliveryTime);
    		effectivePlanDetailModel.set('arriveTime',arriveTime);
    		effectivePlanDetailModel.set('productId',form.findField('productCode').productId);//产品ID
    		//时效主方案ID
    		var effectivePlanEntityId = me.parent.up('window').effectivePlanEntity.id;
    		effectivePlanDetailModel.set('arrvRegionId',form.findField('arrvRegionCode').arrvRegionId);// 获取目的区域的ID@TODO
    		effectivePlanDetailModel.set('arrvRegionCode',form.findField('arrvRegionCode').getValue());// 获取目的区域的ID
    		effectivePlanDetailModel.set('deptRegionId',me.parent.up('window').effectivePlanEntity.deptRegionId);// 获取始发区域的ID@TODO
    		effectivePlanDetailModel.set('deptRegionCode',me.parent.up('window').effectivePlanEntity.deptRegionCode);// 获取始发区域的ID
    		effectivePlanDetailModel.set('upDesTemp',form.findField('description').getValue());// 获取备注
    		effectivePlanDetailModel.set('effectivePlanId',effectivePlanEntityId);
    		var params = {'effectiveExpressPlanVo':{'effectivePlanDetailEntity':effectivePlanDetailModel.data}};
    		var successFun = function(json){
    			pricing.showInfoMes(json.message);//提示信息
    			me.parent.getPagingToolbar().moveFirst();
    			me.hide();
    	    };
    	    var failureFun = function(json){
    	    	if(Ext.isEmpty(json)){
    				pricing.showErrorMes(pricing.effectiveExpressPlan.i18n('i18n.pricingRegion.requestTimeOut'));
    			}else{
    				pricing.showErrorMes(json.message);
    			}
    	    };
    	    var url = pricing.realPath('addEffectiveExpressPlanDetailEntity.action');//新增时效方案明细
    		pricing.effectiveExpressPlan.requestJsonAjax(url,params,successFun,failureFun);//发送AJAX请求
    	}
    },
    //明细信息新增-FORM
    effectivePlanDetailForm:null,
    getEffectivePlanDetailForm:function(){
    	if(Ext.isEmpty(this.effectivePlanDetailForm)){
    		this.effectivePlanDetailForm = Ext.create('Foss.pricing.effectiveExpressPlan.EffectivePlanDetailForm');
    	}
    	return this.effectivePlanDetailForm;
    },
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [me.getEffectivePlanDetailForm()];//设置window的元素
		me.fbar = [{
			text : pricing.effectiveExpressPlan.i18n('i18n.pricingRegion.cancel'),//取消
			handler :function(){
				me.close();
			} 
		},{
			text : pricing.effectiveExpressPlan.i18n('foss.pricing.reset'),//重置
			margin:'0 185 0 0',
			handler :function(){
				me.getEffectivePlanDetailForm().getForm().reset();
			} 
		},{
			text : pricing.effectiveExpressPlan.i18n('i18n.pricingRegion.commit'),//提交
			cls:'yellow_button',
			handler :function(){
				me.commintEffectivePlanDetail();
			} 
		}];
		me.callParent([cfg]);
	}
});

/**
 * 明细信息修改WINDOW
 */
Ext.define('Foss.pricing.effectiveExpressPlan.EffectivePlanDetailUpdateWindow',{
	extend : 'Ext.window.Window',
	title: pricing.effectiveExpressPlan.i18n('foss.pricing.detailInformationChanges'),//明细信息修改
	closable : true,
	modal : true,
	resizable:false,
	closeAction : 'hide',
	effectivePlanDetailEntity:null,//修改的数据明细
	parent:null,//(Foss.pricing.effectiveExpressPlan.EffectivePlanDetailGridPanel)
	width :450,
	height :600,
	deptEditGrid : null,//显示区域关联部门明细表
	editForm:null,//区域新增修改的表单
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	listeners:{
		beforehide:function(me){
			//清楚上次使用的数据
			me.getEffectivePlanDetailForm().getForm().reset();
		},
		beforeshow:function(me){
			me.getEffectivePlanDetailForm().getForm().loadRecord(new Foss.pricing.effectiveExpressPlan.EffectiveExpressPlanDetailEntity(me.effectivePlanDetailEntity));
			me.getEffectivePlanDetailForm().getForm().findField('arrvRegionCode').setCombValue(me.effectivePlanDetailEntity.arrvRegionName,me.effectivePlanDetailEntity.arrvRegionCode);
			me.getEffectivePlanDetailForm().getForm().findField('productCode').productId = me.effectivePlanDetailEntity.productId;
			me.getEffectivePlanDetailForm().getForm().findField('arrvRegionCode').arrvRegionId = me.effectivePlanDetailEntity.arrvRegionId;
		}
	},
    //提交数据
    commintEffectivePlanDetail:function(){
    	var me = this;
    	var form = me.getEffectivePlanDetailForm().getForm();
    	if(form.isValid()){//表单校验
    	    //提交时效方案
	    	var me = this;
	    	var effectivePlanDetailModel = new Foss.pricing.effectiveExpressPlan.EffectiveExpressPlanDetailEntity(me.effectivePlanDetailEntity);
    		form.updateRecord(effectivePlanDetailModel);
    		var maxTime = form.findField('maxTime').getRawValue();
    		var minTime = form.findField('minTime').getRawValue();
    		var minTimeUnit = form.findField('minTimeUnit').getValue();
    		var maxTimeUnit = form.findField('maxTimeUnit').getValue();
    		//时间单位是否一致
    		if(minTimeUnit.minTimeUnit != maxTimeUnit.maxTimeUnit){
    			pricing.showErrorMes(pricing.effectiveExpressPlan.i18n('foss.pricing.inconsistentUnitTimeSetTimDayNumberSelectConsistent'));
    			return;
    		}
    		var maxTimeTemp = parseInt(maxTime); 
			var minTimeTemp = parseInt(minTime); 
			
    		if(maxTimeTemp<minTimeTemp){
    			pricing.showErrorMes(pricing.effectiveExpressPlan.i18n('foss.pricing.commitmentCanNotLessThanMaximumCommitmentMinimumTime'));
    			return;
    		}
    		var deliveryTime = form.findField('deliveryTime').getRawValue().replace(':','') ;
    		var arriveTime = form.findField('arriveTime').getRawValue().replace(':','') ;
    		effectivePlanDetailModel.set('deliveryTime',deliveryTime);
    		effectivePlanDetailModel.set('arriveTime',arriveTime);
    		effectivePlanDetailModel.set('productId',form.findField('productCode').productId);//产品ID
    		//时效主方案ID
    		var effectivePlanEntityId = me.parent.up('window').effectivePlanEntity.id;
    		effectivePlanDetailModel.set('arrvRegionId',form.findField('arrvRegionCode').arrvRegionId);// 获取目的区域的ID@TODO
    		effectivePlanDetailModel.set('arrvRegionCode',form.findField('arrvRegionCode').getValue());// 获取目的区域的ID
    		effectivePlanDetailModel.set('deptRegionId',me.parent.up('window').effectivePlanEntity.deptRegionId);// 获取始发区域的ID@TODO
    		effectivePlanDetailModel.set('deptRegionCode',me.parent.up('window').effectivePlanEntity.deptRegionCode);// 获取始发区域的ID
    		effectivePlanDetailModel.set('upDesTemp',form.findField('description').getValue());// 获取备注
    		effectivePlanDetailModel.set('effectivePlanId',effectivePlanEntityId);
    		var params = {'effectiveExpressPlanVo':{'effectivePlanDetailEntity':effectivePlanDetailModel.data}};
    		var successFun = function(json){
    			pricing.showInfoMes(json.message);//提示信息
    			me.parent.getPagingToolbar().moveFirst();
    			me.hide();
    	    };
    	    var failureFun = function(json){
    	    	if(Ext.isEmpty(json)){
    				pricing.showErrorMes(pricing.effectiveExpressPlan.i18n('i18n.pricingRegion.requestTimeOut'));
    			}else{
    				pricing.showErrorMes(json.message);
    			}
    	    };
    	    var url = pricing.realPath('updateEffectiveExpressPlanDetailEntity.action');//修改时效方案明细
    		pricing.effectiveExpressPlan.requestJsonAjax(url,params,successFun,failureFun);//发送AJAX请求
    	}
    },
    //明细信息新增-FORM
    effectivePlanDetailForm:null,
    getEffectivePlanDetailForm:function(){
    	if(Ext.isEmpty(this.effectivePlanDetailForm)){
    		this.effectivePlanDetailForm = Ext.create('Foss.pricing.effectiveExpressPlan.EffectivePlanDetailForm');
    	}
    	return this.effectivePlanDetailForm;
    },
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [me.getEffectivePlanDetailForm()];//设置window的元素
		me.fbar = [{
			text : pricing.effectiveExpressPlan.i18n('i18n.pricingRegion.cancel'),//取消
			handler :function(){
				me.hide();
			} 
		},{
			text : pricing.effectiveExpressPlan.i18n('foss.pricing.reset'),//重置
			margin:'0 185 0 0',
			handler :function(){
				me.getEffectivePlanDetailForm().getForm().loadRecord(new Foss.pricing.effectiveExpressPlan.EffectiveExpressPlanDetailEntity(me.effectivePlanDetailEntity));
				me.getEffectivePlanDetailForm().getForm().findField('arrvRegionCode').setCombValue(me.effectivePlanDetailEntity.arrvRegionName,me.effectivePlanDetailEntity.arrvRegionCode);
				me.getEffectivePlanDetailForm().getForm().findField('productCode').productId = me.effectivePlanDetailEntity.productId;
				me.getEffectivePlanDetailForm().getForm().findField('arrvRegionCode').arrvRegionId = me.effectivePlanDetailEntity.arrvRegionId;
			} 
		},{
			text : pricing.effectiveExpressPlan.i18n('i18n.pricingRegion.commit'),//提交
			cls:'yellow_button',
			handler :function(){
				me.commintEffectivePlanDetail();
			} 
		}];
		me.callParent([cfg]);
	}
});

/**
 * 明细信息新增-FORM
 */
Ext.define('Foss.pricing.effectiveExpressPlan.EffectivePlanDetailForm', {
	extend : 'Ext.form.Panel',
	frame: true,
	height:600,
	collapsible: true,
	isSearchComb:true,
    defaults : {
    	colspan : 2,
    	margin : '5 5 5 5',
    	//labelSeparator:'',
    	labelWidth:90,
    	anchor : '100%'
    },
	defaultType : 'textfield',
	layout: {
        type: 'table',
        columns: 2
    },
	items :[{
		name: 'arrvRegionCode',
		allowBlank:false,
		arrvRegionId:null,
		regionName:null,
        fieldLabel: pricing.effectiveExpressPlan.i18n('foss.pricing.destinationArea'),//目的区域
        xtype : 'commonperscriptionregionexpressselector',
        listeners:{
        	select:function(comb,records,objs){
        		var record = records[0];
        		var id = record.get('id');
        		var name = record.get('regionName');
        		comb.arrvRegionId = id;
        		comb.regionName = name;
        	}
        }
	},{
		name: 'productCode',
		queryMode: 'local',
	    displayField: 'name',
	    valueField: 'code',
	    productId:null,
	    editable:false,
	    allowBlank:false,
	    store:pricing.effectiveExpressPlan.getStore(null,null,['id','code','name'],pricing.effectiveExpressPlan.saleDepartmentProduct),
        fieldLabel:pricing.effectiveExpressPlan.i18n('foss.pricing.basicProducts'),//基础产品
        xtype : 'combo',
        listeners:{
        	select:function(comb,records){
        		comb.productId = records[0].get('id');
        	}
        }
	},{
		name: 'minTime',
		allowBlank:false,
		decimalPrecision:0,
		maxValue: 999999,
        minValue: 1,
        colspan : 1,
        fieldLabel: pricing.effectiveExpressPlan.i18n('foss.pricing.commitmentMinimumNumberDaysTime'),//承诺最小天数或时间
        xtype : 'numberfield'
	},{
		xtype:'radiogroup',
		 vertical:true,
		 colspan : 1,
		 allowBlank:false,
		 name:'minTimeUnit',
		 width:160,
		labelSeparator:'',
		 items:[{
		 	 xtype:'radio',
		     boxLabel:pricing.effectiveExpressPlan.i18n('foss.pricing.hour'),
		     name:'minTimeUnit',
		     inputValue:pricing.effectiveExpressPlan.HOURS
	     },{
	    	 xtype:'radio',
		     boxLabel:pricing.effectiveExpressPlan.i18n('foss.pricing.days'),
		     name:'minTimeUnit',
		     inputValue:pricing.effectiveExpressPlan.DAY
		 }]
	},{
		name: 'maxTime',
		allowBlank:false,
		decimalPrecision:0,
		maxValue: 999999,
        minValue: 1,
        colspan : 1,
        fieldLabel: pricing.effectiveExpressPlan.i18n('foss.pricing.commitmentMaximumNumberDaysTime'),//承诺最大天数或时间
        xtype : 'numberfield'
	},{
		xtype:'radiogroup',
		 vertical:true,
		 colspan : 1,
		 allowBlank:false,
		 name:'maxTimeUnit',
		 width:160,
		labelSeparator:'',
		 items:[{
		 	 xtype:'radio',
		     boxLabel:pricing.effectiveExpressPlan.i18n('foss.pricing.hour'),
		     name:'maxTimeUnit',
		     inputValue:pricing.effectiveExpressPlan.HOURS
	     },{
	    	 xtype:'radio',
		     boxLabel:pricing.effectiveExpressPlan.i18n('foss.pricing.days'),
		     name:'maxTimeUnit',
		     inputValue:pricing.effectiveExpressPlan.DAY
		 }]
	},{
		xtype: 'timefield',
	    fieldLabel: pricing.effectiveExpressPlan.i18n('foss.pricing.commitmentPointTimeReachSalesDepartment'),//到达营业部承诺时点
        name:'arriveTime',
        format:'H:i',//24小时制
        allowBlank:false
	},{
		name: 'addDay',
		allowBlank:false,
		decimalPrecision:0,
		maxValue: 999999,
        minValue: 0,
        fieldLabel: pricing.effectiveExpressPlan.i18n('foss.pricing.deliveryCommitmentsNeedAddNumberDays'),//派送承诺需加天数
        xtype : 'numberfield'
	},{
		xtype: 'timefield',
	    fieldLabel: pricing.effectiveExpressPlan.i18n('foss.pricing.deliveryCommitmentTimePoints'),//派送承诺时点
        name:'deliveryTime',
        format:'H:i',//24小时制
        allowBlank:false
	},{
		 xtype:'radiogroup',
		 vertical:true,
		 allowBlank:false,
		 name:'hasSalesDept',
		 width:200,
		 fieldLabel:pricing.effectiveExpressPlan.i18n('foss.pricing.whetherTheResidentSecto'),//是否驻地部门
		 items:[{
		 	 xtype:'radio',
		     boxLabel:pricing.effectiveExpressPlan.i18n('i18n.pricingRegion.ye'),
		     name:'hasSalesDept',
		     inputValue:'Y'
	     },{
	    	 xtype:'radio',
		     boxLabel:pricing.effectiveExpressPlan.i18n('i18n.pricingRegion.no'),
		     name:'hasSalesDept',
		     inputValue:'N'
		 }]
   	},{
		 xtype:'radiogroup',
		 vertical:true,
		 allowBlank:false,
		 name:'longOrShort',
		 width:200,
		 fieldLabel:pricing.effectiveExpressPlan.i18n('foss.pricing.shortAndLongDistance'),//长短途
		 items:[{
		 	 xtype:'radio',
		     boxLabel:pricing.effectiveExpressPlan.i18n('foss.pricing.longDistance'),//长途
		     name:'longOrShort',
		     inputValue:pricing.effectiveExpressPlan.LONG
	     },{
	    	 xtype:'radio',
		     boxLabel:pricing.effectiveExpressPlan.i18n('foss.pricing.shortDistance'),//短途
		     name:'longOrShort',
		     inputValue:pricing.effectiveExpressPlan.SHORT
		 }]
   	},{
		xtype: 'textareafield',
		width:300,
	    fieldLabel: pricing.effectiveExpressPlan.i18n('foss.pricing.remark'),//备注
		maxLength : 200,
        name:'description'
	}]
});


/**
 * 明细信息查看WINDOW
 */
Ext.define('Foss.pricing.effectiveExpressPlan.EffectiveExpressPlanDetailShowWindow',{
	extend : 'Ext.window.Window',
	title: pricing.effectiveExpressPlan.i18n('foss.pricing.theOriginatingAreaWithPurposeRegionalAgingProgramDetailsInquiry'),//始发区域与目的区域时效方案明细查询
	closable : true,
	modal : true,
	resizable:false,
	closeAction : 'hide',
	parent:null,//(Foss.pricing.effectiveExpressPlan.EffectiveExpressPlanGridPanel)
	effectivePlanId:null,//时效方案ID
	width :700,
	height :600,
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	listeners:{
		beforehide:function(me){
			//清楚上次使用的数据
			me.getQueryEffectivePlanDetailForm().getForm().reset();
			me.getEffectivePlanDetailShowGridPanel().getStore().removeAll();
			me.effectivePlanId = null;
		},
		beforeshow:function(me){
			//me.getEditForm().isSearchComb = true;
		}
	},
    //明细信息查询-FORM
	queryEffectivePlanDetailForm:null,
    getQueryEffectivePlanDetailForm:function(){
    	if(Ext.isEmpty(this.queryEffectivePlanDetailForm)){
    		this.queryEffectivePlanDetailForm = Ext.create('Foss.pricing.effectiveExpressPlan.QueryEffectivePlanDetailForm');
    	}
    	return this.queryEffectivePlanDetailForm;
    },
    //明细信息结果集
    effectivePlanDetailShowGridPanel:null,
    getEffectivePlanDetailShowGridPanel:function(){
    	if(Ext.isEmpty(this.effectivePlanDetailShowGridPanel)){
    		this.effectivePlanDetailShowGridPanel = Ext.create('Foss.pricing.effectiveExpressPlan.EffectivePlanDetailShowGridPanel');
    	}
    	return this.effectivePlanDetailShowGridPanel;
    },
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [me.getQueryEffectivePlanDetailForm(),me.getEffectivePlanDetailShowGridPanel()];//设置window的元素
		me.callParent([cfg]);
	}
});
/**
 * 时效方案明细查询表单
 */
Ext.define('Foss.pricing.effectiveExpressPlan.QueryEffectivePlanDetailForm', {
	extend : 'Ext.form.Panel',
	title: pricing.effectiveExpressPlan.i18n('i18n.pricingRegion.searchCondition'),
	frame: true,
	collapsible: true,
    defaults : {
    	columnWidth : .4,
    	margin : '8 10 5 10',
    	//labelSeparator:'',
    	anchor : '100%'
    },
    height :100,
	defaultType : 'textfield',
	layout:'column',
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.items = [{
			name: 'productCode',
			queryMode: 'local',
		    displayField: 'name',
		    valueField: 'code',
		    editable:false,
		    productId:null,//产品ID
		    store:pricing.effectiveExpressPlan.getStore(null,null,['id','code','name'],pricing.effectiveExpressPlan.saleDepartmentProduct),
	        fieldLabel:pricing.effectiveExpressPlan.i18n('foss.pricing.basicProducts'),//基础产品
	        xtype : 'combo',
	        listeners:{
	        	select:function(comb,records){
	        		combs.productId = records[0].get('id');
	        	}
	        }
		},{
			name: 'arrvRegionCode',
	        fieldLabel:pricing.effectiveExpressPlan.i18n('foss.pricing.destinationArea'),//目的区域
	        xtype : 'commonperscriptionregionexpressselector'
		},{
			xtype : 'container',
			columnWidth : .2,
			margin : '0 0 0 0',
			items : [{
				xtype : 'button', 
				width:70,
				text : pricing.effectiveExpressPlan.i18n('i18n.pricingRegion.search'),
				cls:'yellow_button',
				handler : function() {
					if(me.getForm().isValid()){
						var grid = me.up('window').getEffectivePlanDetailShowGridPanel();
						grid.getPagingToolbar().moveFirst();
					}
					
				}
			}]
		}];
		me.callParent([cfg]);
	}
});
/**
 * 时效方案明细列表
 */
Ext.define('Foss.pricing.effectiveExpressPlan.EffectivePlanDetailShowGridPanel', {
	extend: 'Ext.grid.Panel',
	title :pricing.effectiveExpressPlan.i18n('i18n.pricingRegion.searchResults'),//查询结果
	frame: true,
	flex:1,
    sortableColumns:false,
    enableColumnHide:false,
    enableColumnMove:false,
	stripeRows : true, // 交替行效果
	selType : "rowmodel", // 选择类型设置为：行选择
	emptyText: pricing.effectiveExpressPlan.i18n('foss.pricing.theQueryResultIsEmpty'),//查询结果为空
	//得到bbar
	pagingToolbar : null,
	getPagingToolbar : function() {
		if (this.pagingToolbar == null) {
			this.pagingToolbar = Ext.create('Deppon.StandardPaging', {
				store : this.store,
				pageSize : 20
			});
		}
		return this.pagingToolbar;
	},	
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.columns = [{xtype: 'rownumberer',
			width:40,
			text : pricing.effectiveExpressPlan.i18n('i18n.pricingRegion.num')//序号
		},{
			text :pricing.effectiveExpressPlan.i18n('foss.pricing.destinationArea'),//目的区域
			dataIndex : 'arrvRegionName',
			flex:2
		},{
			text :pricing.effectiveExpressPlan.i18n('foss.pricing.basicProducts'),//基础产品
			dataIndex : 'productName',
			flex:2,
			renderer:function(value){
				return value;
			}
		},{
			text :pricing.effectiveExpressPlan.i18n('foss.pricing.commitmentMinimumNumberDaysHours'),//承诺最小天数或小时 ,
			dataIndex : 'minTime',
			flex:2,
			renderer:function(value,obj,record){
				var showValue = value;
				if(record.get('minTimeUnit')==pricing.effectiveExpressPlan.DAY){
					showValue = showValue+pricing.effectiveExpressPlan.i18n('foss.pricing.day');//天
				}else if(record.get('minTimeUnit')==pricing.effectiveExpressPlan.HOURS){
					showValue = showValue+pricing.effectiveExpressPlan.i18n('foss.pricing.hour');//小时
				}
				return showValue;
			}
		},{
			text :pricing.effectiveExpressPlan.i18n('foss.pricing.commitmentMaximumNumberDaysHours'),//承诺最大天数或小时 , ,
			dataIndex : 'maxTime',
			flex:2,
			renderer:function(value,obj,record){
				var showValue = value;
				if(record.get('maxTimeUnit')==pricing.effectiveExpressPlan.DAY){
					showValue = showValue+pricing.effectiveExpressPlan.i18n('foss.pricing.day');//天
				}else if(record.get('maxTimeUnit')==pricing.effectiveExpressPlan.HOURS){
					showValue = showValue+pricing.effectiveExpressPlan.i18n('foss.pricing.hour');//小时
				}
				return showValue;
			}
		},{
			text : pricing.effectiveExpressPlan.i18n('foss.pricing.commitmentPointTimeReachSalesDepartment'),//到达营业部承诺时点,
			dataIndex : 'arriveTime',
			flex:2
		},{
			text : pricing.effectiveExpressPlan.i18n('foss.pricing.whetherTheResidentSecto'),//是否驻地部门
			dataIndex : 'hasSalesDept',
			flex:2,
			renderer:function(value){
				if(value=='Y'){
					return pricing.effectiveExpressPlan.i18n('i18n.pricingRegion.ye');//是
				}else if(value=='N'){
					return pricing.effectiveExpressPlan.i18n('i18n.pricingRegion.no');//否
				}else{
					return;
				}	
			}
		},{
			text : pricing.effectiveExpressPlan.i18n('foss.pricing.shortAndLongDistance'),//长短途
			dataIndex : 'longOrShort',
			flex:2,
			renderer:function(value){
				if(value==pricing.effectiveExpressPlan.LONG){
					return pricing.effectiveExpressPlan.i18n('foss.pricing.longDistance');
				}else if(value==pricing.effectiveExpressPlan.SHORT){
					return pricing.effectiveExpressPlan.i18n('foss.pricing.shortDistance');
				}
			}
		},{
			text : pricing.effectiveExpressPlan.i18n('foss.pricing.deliveryCommitmentsNeedAddNumberDays'),//派送承诺需加天数
			dataIndex : 'addDay',
			flex:2
		},{
			text : pricing.effectiveExpressPlan.i18n('foss.pricing.deliveryCommitmentTimePoints'),//派送承诺时点,
			dataIndex : 'deliveryTime',
			flex:2
		},{
			text : pricing.effectiveExpressPlan.i18n('foss.pricing.remark'),//备注
			dataIndex : 'description',
			flex:2
		}];
		me.store = Ext.create('Foss.pricing.effectiveExpressPlan.EffectivePlanDetailStore',{
			autoLoad : false,
			pageSize : 20,
			listeners: {
				beforeload: function(store, operation, eOpts){
					var queryForm =me.up('window').getQueryEffectivePlanDetailForm();
					var effectivePlanId = me.up('window').effectivePlanId;
					if(queryForm!=null){
						Ext.apply(operation,{
							params : {
								'effectiveExpressPlanVo.effectivePlanDetailEntity.arrvRegionCode' : queryForm.getForm().findField('arrvRegionCode').getValue(),//目的站
								'effectiveExpressPlanVo.effectivePlanDetailEntity.productCode' : queryForm.getForm().findField('productCode').getValue(),//产品编码
								'effectiveExpressPlanVo.effectivePlanDetailEntity.effectivePlanId' :effectivePlanId //时效方案ID
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
	    
	    me.tbar = [
		{
			text : pricing.effectiveExpressPlan.i18n('foss.pricing.export'),
			handler :function(){
				var queryForm =me.up('window').getQueryEffectivePlanDetailForm();
				var arrvRegionCode = queryForm.getForm().findField('arrvRegionCode').getValue()
				var productCode = queryForm.getForm().findField('productCode').getValue()
				var effectivePlanId = me.up('window').effectivePlanId;
				var url = pricing.realPath('exportEffectiveExpressPlanDetail.action');
				var condition = '';
				if(!Ext.isEmpty(effectivePlanId)){
					condition = "?effectiveExpressPlanVo.effectivePlanDetailEntity.effectivePlanId=" + effectivePlanId
				}
				if(!Ext.isEmpty(productCode)){
					condition += "&effectiveExpressPlanVo.effectivePlanDetailEntity.productCode="+productCode
				}
				if(!Ext.isEmpty(arrvRegionCode)){
					condition += "&effectiveExpressPlanVo.effectivePlanDetailEntity.arrvRegionCode="+arrvRegionCode
				}
	    		window.location=url + condition;
				pricePlanExport = '';
 			} 
		}];
		me.selModel = Ext.create('Ext.selection.CheckboxModel',{//多选框
					mode:'MULTI',
					checkOnly:true
				});
		me.bbar = me.getPagingToolbar();
		me.getPagingToolbar().store = me.store;
		me.callParent([cfg]);
	}
});

//上传附件弹出框
Ext.define('Foss.pricing.UploadEffectiveExpressPlan',{
	extend:'Ext.window.Window',
	title:pricing.effectiveExpressPlan.i18n('foss.pricing.importAgingProgram'),
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
	parent:null,//（Foss.pricing.PlatformGrid）
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
				fieldLabel:pricing.effectiveExpressPlan.i18n('foss.pricing.pleaseSelectAttachments'),//请选择附件
				labelWidth:100,
				buttonText:pricing.effectiveExpressPlan.i18n('foss.pricing.browse'),//浏览
				flex:3
			}]
		}];
		me.fbar = me.getFbar();
		me.callParent([cfg]);
	},
	getFbar:function(){
		var me = this;
		return [{
			text:pricing.effectiveExpressPlan.i18n('foss.pricing.import'),//导入
			xtype:'button',
			scope:me,
			handler:me.uploadFile
		},{
			text:pricing.effectiveExpressPlan.i18n('i18n.pricingRegion.cancel'),//取消
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
			if(Ext.isEmpty(json.effectiveExpressPlanVo.numList)){
				pricing.showInfoMes(pricing.effectiveExpressPlan.i18n('foss.pricing.allDataImportSuccess'));//全部数据导入成功！
				me.close();
			}else{
				var message = pricing.effectiveExpressPlan.i18n('foss.pricing.first');//第
				for(var i = 0;i<json.effectiveExpressPlanVo.numList;i++){
					message = message+json.effectiveExpressPlanVo.numList[i]+','
				}
				message = message+pricing.effectiveExpressPlan.i18n('foss.pricing.lineImportSuccess');//行，没有导入成功！
				pricing.showWoringMessage(message);
			}
		};
		var failureFn = function(json){
			if(Ext.isEmpty(json)){
				pricing.showErrorMes(pricing.effectiveExpressPlan.i18n('i18n.pricingRegion.requestTimeOut'));//pricing.effectiveExpressPlan.i18n('i18n.pricingRegion.requestTimeOut')
			}else{
				pricing.showErrorMes(json.message);
			}
		};
		var form = me.down('form').getForm();
		var url = pricing.realPath('importEffectiveExpressPlan.action');
		form.submit({
            url: url,
            waitMsg: pricing.effectiveExpressPlan.i18n('foss.pricing.uploadYourAttachment'),//上传您的附件...
            timeout:60000,   
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
 * 立即中止价格方案 Window
 */
Ext.define('Foss.pricing.effectiveExpressPlan.EffectivePlanImmediatelyStopEndTimeWindow',{
		extend: 'Ext.window.Window',
		title: pricing.effectiveExpressPlan.i18n('foss.pricing.immediatelySupendPricePriceScheme'),//"立即中止方案",
		width:380,
		height:152,
		effectivePlanEntity:null,
		effectivePlans:null,
		closeAction: 'hide' ,
		listeners:{
			beforehide:function(me){
				var form = me.down('form');
				form.getForm().reset();
			},
			beforeshow:function(me){
				var value = pricing.effectiveExpressPlan.i18n('foss.pricing.showrightTimeDefaultInfo');
				me.geteffectivePlanStopFormPanel().getForm().findField('showTime').setValue(value);
				me.geteffectivePlanStopFormPanel().getForm().findField('activeTime').setValue(Ext.Date.format(new Date(),'Y-m-d H:i:s'))
			}
		},
		effectivePlanStopFormPanel:null,
		geteffectivePlanStopFormPanel : function(effectivePlans){
	    	if(Ext.isEmpty(this.effectivePlanStopFormPanel)){
	    		this.effectivePlanStopFormPanel = Ext.create('Foss.pricing.effectiveExpressPlan.EffectivePlanImmediatelyStopFormPanel');
	    	}
	    	this.effectivePlanStopFormPanel.effectivePlans = effectivePlans;
	    	return this.effectivePlanStopFormPanel;
	    },
	   	constructor : function(config) {
	   		var me = this, cfg = Ext.apply({}, config);
			me.items = [me.geteffectivePlanStopFormPanel(me.effectivePlans)];
			me.callParent(cfg);
		}
});

/**
 * 立即中止功能FormPanel
 */
Ext.define('Foss.pricing.effectiveExpressPlan.EffectivePlanImmediatelyStopFormPanel',{
	extend : 'Ext.form.Panel',
	layout:'column',
	effectivePlanEntity:null,
	effectivePlans:null,
	stopPricePlan:function(effectivePlans){
		var me = this;
    	var form = me.getForm();
    	if(form.isValid()){
    		var endTime = Ext.Date.parse(form.findField('activeTime').getValue(), 'Y-m-d H:i:s');
    		var params = {'effectiveExpressPlanVo':{'effectivePlans':effectivePlans,'endTime':endTime}};
    		var url = pricing.realPath('stopImmediatelyEffectiveExpressPlan.action');
    		var successFun = function(json){
    			pricing.showInfoMes(json.message);
    			me.up('window').hide();
				pricing.pagingBar.moveFirst();   			
    	    };
    	    var failureFun = function(json){
    	    	if(Ext.isEmpty(json)){
    				pricing.showErrorMes(pricing.effectiveExpressPlan.i18n('i18n.pricingRegion.requestTimeOut'));
    			}else{
    				pricing.showErrorMes(json.message);
    			}
    	    };
    		pricing.effectiveExpressPlan.requestJsonAjax(url,params,successFun,failureFun);//发送AJAX请求
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
				fieldLabel :pricing.effectiveExpressPlan.i18n('foss.pricing.suspendTime') ,//'中止日期',
				name : 'activeTime',
				xtype : 'datetimefield_date97',
				editable:false,
				time : true,
				id : 'Foss_effectiveExpressPlan_stopEndTime_ID',
				allowBlank:false,
				dateConfig: {
					el : 'Foss_effectiveExpressPlan_stopEndTime_ID-inputEl'
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
				plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin', {
					  //设定间隔秒数,如果不设置，默认为2秒
					  seconds: 5
				}),
				text : pricing.effectiveExpressPlan.i18n('i18n.pricingRegion.determine'),//"确认",
				handler : function() {
					var effectivePlans = me.up('window').effectivePlans;
					me.stopPricePlan(effectivePlans);
				}
			},{
				xtype : 'button', 
				width:70,
				columnWidth:.15,
				text : pricing.effectiveExpressPlan.i18n('i18n.pricingRegion.cancel'),//"取消",
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
Ext.define('Foss.pricing.effectiveExpressPlan.EffectivePlanImmediatelyActiveTimeWindow',{
		extend: 'Ext.window.Window',
		title: pricing.effectiveExpressPlan.i18n('foss.pricing.immediatelyActiveationPriceScheme'),//"立即激活方案",
		width:380,
		height:152,
		effectivePlanEntity:null,
		effectivePlans:null,//批量激活的方案ID
		closeAction: 'hide' ,
		listeners:{
			beforehide:function(me){
				var form = me.down('form');
				form.getForm().reset();
			},
			beforeshow:function(me){
				var value = pricing.effectiveExpressPlan.i18n('foss.pricing.showleftTimeDefaultInfo');
				me.getEffectivePlanImmediatelyActiveFormPanel().getForm().findField('showTime').setValue(value);
				me.getEffectivePlanImmediatelyActiveFormPanel().getForm().findField('activeTime').setValue(Ext.Date.format(new Date(),'Y-m-d H:i:s'));
			}
		},
		effectivePlanImmediatelyActiveFormPanel:null,
		getEffectivePlanImmediatelyActiveFormPanel : function(effectivePlans){
	    	if(Ext.isEmpty(this.effectivePlanImmediatelyActiveFormPanel)){
	    		this.effectivePlanImmediatelyActiveFormPanel = Ext.create('Foss.pricing.effectiveExpressPlan.EffectivePlanImmediatelyActiveFormPanel');
	    	}
	    	this.effectivePlanImmediatelyActiveFormPanel.effectivePlans = effectivePlans;
	    	return this.effectivePlanImmediatelyActiveFormPanel;
	    },
	   	constructor : function(config) {
			var me = this, cfg = Ext.apply({}, config);
			me.items = [me.getEffectivePlanImmediatelyActiveFormPanel(me.effectivePlans)];
			me.callParent(cfg);
		}
});


/**
 * 立即激活功能Form
 */
Ext.define('Foss.pricing.effectiveExpressPlan.EffectivePlanImmediatelyActiveFormPanel',{
	extend : 'Ext.form.Panel',
	layout:'column',
	effectivePlanEntity:null,
	effectivePlans:null,
	activetionPricePlan:function(effectivePlans){
		var me = this;
    	var form = me.getForm();
    	if(form.isValid()){
    		
    		//判断选择的激活时间不能早于当前时间
    		var nowDate = new Date();
    		var selectedDate = Ext.Date.parse(form.findField('activeTime').getValue(), 'Y-m-d H:i:s');
    		if(selectedDate<nowDate){
    			pricing.showErrorMes(pricing.effectiveExpressPlan.i18n('foss.pricing.activeTimeIsMustBeforeStopTime'));
    			return false;
    		}
    		var params = {'effectiveExpressPlanVo':{'effectivePlans':effectivePlans,'beginTime':selectedDate}};
    		var url = pricing.realPath('immediatelyActiveEffectiveExpressPlan.action');
    		var successFun = function(json){
    			pricing.showInfoMes(json.message);
    			me.up('window').close();//关闭该window
				pricing.pagingBar.moveFirst();   			
    	    };
    	    var failureFun = function(json){
    	    	if(Ext.isEmpty(json)){
    				pricing.showErrorMes(pricing.effectiveExpressPlan.i18n('i18n.pricingRegion.requestTimeOut'));
    			}else{
    				pricing.showErrorMes(json.message);
    			}
    	    };
    		pricing.effectiveExpressPlan.requestJsonAjax(url,params,successFun,failureFun);//发送AJAX请求
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
				fieldLabel:pricing.effectiveExpressPlan.i18n('foss.pricing.availabilityDate'),//'生效日期',
				name : 'activeTime',
				xtype : 'datetimefield_date97',
				editable:false,
				time : true,
				id : 'Foss_effectiveExpressPlan_activetionEndTime_ID',
				allowBlank:false,
				dateConfig: {
					el : 'Foss_effectiveExpressPlan_activetionEndTime_ID-inputEl'
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
				plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin', {
					  //设定间隔秒数,如果不设置，默认为2秒
					  seconds: 5
				}),
				text : pricing.effectiveExpressPlan.i18n('i18n.pricingRegion.determine'),//,"确认",
				handler : function() {
					var effectivePlans = me.up('window').effectivePlans;
					me.activetionPricePlan(effectivePlans);
				}
			},{
				xtype : 'button', 
				width:70,
				columnWidth:.15,
				text : pricing.effectiveExpressPlan.i18n('i18n.pricingRegion.cancel'),//"取消",
				handler : function() {
					me.up('window').hide();
				}
			}];
        this.callParent(cfg);
    }
});






