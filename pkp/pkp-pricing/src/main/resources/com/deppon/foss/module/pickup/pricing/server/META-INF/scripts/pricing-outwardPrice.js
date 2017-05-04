/**
 * @type String 激活
 */
pricing.yes = 'Y';
pricing.no = 'N';
pricing.outwardPrice.immediatelyActiveId = null;
pricing.outwardPrice.immediatelyStopId = null;
/**
 * @type  偏线外发价费用
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
			Ext.Msg.alert(pricing.outwardPrice.i18n('foss.pricing.promptMessage'),result.message);
		}
	});
};

//创建一个查询货量枚举store
Ext.define('Foss.pricing.outwardPrice.Store.ToDoTypeStore',{
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
 * 偏线外发价费用方案批次model
 */
Ext.define('Foss.pricing.outwardPrice.outwardPriceModel', {
    extend: 'Ext.data.Model',
    fields : [
    {
        name : 'id',//ID
        type : 'string'
    },{
      name : 'schemeName',//方案名称
        type : 'string'
    },{
      name : 'agentDeptCode',//目的站
        type : 'string'
    },{
      name : 'agentDeptName',
        type : 'string'
    },{
        name : 'externalOrgCode',//最终外场
        type : 'string'
    },{
      name : 'externalOrgName',
        type : 'string'//productCode
    },{
      name : 'transportType',
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
        name : 'currentUsedVersion',
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
        name : 'heavyPrice',
        type : 'float'
    },{
        name : 'lightPrice',
        type : 'float'
    },{
        name : 'lowestPrice',
        type : 'int'
    },{
    	name:'showTime',
    	type : 'string'
    }]
});

/**
 *偏线价费用方案批次store
 */
Ext.define('Foss.pricing.outwardPrice.OuterPricePlanStore',{
	extend: 'Ext.data.Store',
	model: 'Foss.pricing.outwardPrice.outwardPriceModel',
	pageSize:20,
	proxy: {
		type : 'ajax',
		actionMethods:'POST',
		url: pricing.realPath('queryExternalPriceSchemeByParams.action'),
		reader : {
			type : 'json',
			root : 'externalPriceSchemeVo.entityList',
			totalProperty : 'totalCount',
			successProperty: 'success'
		}
	},
	listeners: {
		beforeload: function(store, operation, eOpts){
			var n = pricing.outwardPrice.queryform.getValues();
			var externalOrgCode = pricing.outwardPrice.queryform.getForm().findField('externalOrgCode').getValue();
			if(externalOrgCode != null)
			{
				externalOrgCode = pricing.outwardPrice.queryform.getForm().findField('externalOrgCode').displayTplData[0].orgCode;
			}
			Ext.apply(operation,{
				params : {
					'externalPriceSchemeVo.entity.agentDeptCode' : 	  n.agentDeptCode,
					'externalPriceSchemeVo.entity.externalOrgCode'	: externalOrgCode,
					'externalPriceSchemeVo.entity.transportType'	: n.transportType,
					'externalPriceSchemeVo.entity.active'	: n.active
				}
			});			
		}
	}
});



/**
 * 偏线外发价格方案批次查询form表单
 */
Ext.define('Foss.pricing.outwardPrice.outwardPriceFormPanel', {
	extend : 'Ext.form.Panel',
	  title: pricing.outwardPrice.i18n('i18n.pricingRegion.searchCondition'),//查询条件
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
	    name:'agentDeptCode',
	    fieldLabel:pricing.outwardPrice.i18n('foss.pricing.destinationLine'),//目的站
	    columnWidth: 0.25
	  },{
	    xtype : 'commontransfercenterselector',
	    name: 'externalOrgCode',
		active:'Y',  
	    fieldLabel: pricing.outwardPrice.i18n('foss.pricing.outFieldName'),//外发外场
	    columnWidth: 0.25
	  },{
		name: 'transportType',
		fieldLabel: pricing.outwardPrice.i18n('foss.pricing.transferType'),
		columnWidth: 0.25,
		xtype: 'combobox',
		valueField:'valueCode', 
		displayField: 'valueName',
		value: '',
		queryMode:'local',
		triggerAction:'all',
		store : FossDataDictionary.getDataDictionaryStore("TRANS_TYPE", null,{
			'valueCode' : '',
			'valueName' : pricing.outwardPrice.i18n('i18n.pricingRegion.all')
		})
	},{
	    xtype:'combo',
	    displayField:'name',
	    valueField:'code',
	    queryMode:'local',
	    triggerAction:'all',
	    editable:false,
	    name: 'active',
	    fieldLabel: pricing.outwardPrice.i18n('foss.pricing.dataStatus'),//数据状态,
	    columnWidth: 0.25,
	    value:'',
	    store:Ext.create('Foss.pricing.outwardPrice.Store.ToDoTypeStore',
	    {
	      data:{
	           'items':[
	          {'code':'','name':pricing.outwardPrice.i18n('i18n.pricingRegion.all')},//全部
	          {'code':'N','name':pricing.outwardPrice.i18n('i18n.pricingRegion.unActive')},//未激活
	          {'code':'Y','name':pricing.outwardPrice.i18n('i18n.pricingRegion.active')}	 //  激活       
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
				text : pricing.outwardPrice.i18n('foss.pricing.reset'),//重置
				handler : function() {
					pricing.outwardPrice.queryform.getForm().reset();
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
		        text :pricing.outwardPrice.i18n('i18n.pricingRegion.search'),
		        disabled: !pricing.outwardPrice.isPermission('outerPrice/outerPriceQueryButton'),
		        hidden: !pricing.outwardPrice.isPermission('outerPrice/outerPriceQueryButton'),
		        handler : function() {
		        	var grid = Ext.getCmp('T_pricing-outwardPriceIndex_content').getOffLinePricePlanGridPanel();
		        	grid.getPagingToolbar().moveFirst();
		      }
		    }]
	  }]
});

//-------------------查询详情------------------

/**
 * 偏线外发价费用方案批次列表gird
 */
Ext.define('Foss.pricing.outwardPrice.outwardPriceGridPanel',{
	extend: 'Ext.grid.Panel',
	title : pricing.outwardPrice.i18n('i18n.pricingRegion.searchResults'),//查询结果
	emptyText: pricing.outwardPrice.i18n('foss.pricing.theQueryResultIsEmpty'),//查询结果为空
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
	
	//获取偏线外发价格新增弹出框
	addPricePlanWindow:null,
	getAddpricePlanWindow : function(){
		if(Ext.isEmpty(this.addPricePlanWindow)){
			this.addPricePlanWindow = Ext.create('Foss.pricing.outwardPrice.outwardPriceAddWindow');
			this.addPricePlanWindow.parent = this;
		}
		return this.addPricePlanWindow;
	},
	
	updatePricePlanWindow : null,
	//获取修改偏线外发价格方案窗口
	getUpdatePricePlanWindow : function(){
		if(Ext.isEmpty(this.updateStandardWindow)){
			this.updatePricePlanWindow = Ext.create('Foss.pricing.outwardPrice.outwardPriceUpdateWindow');
			//设置器父元素
			this.updatePricePlanWindow.parent = this;
		}
		return this.updatePricePlanWindow;
	},
	
	
	//获取中止方案弹出日期选择
	stopPricePlanWindow:null,
	getStopPricePlanWindow: function(){
		if(Ext.isEmpty(this.stopPricePlanWindow)){			
			this.stopPricePlanWindow = Ext.create('Foss.pricing.outwardPrice.outwardPriceStopEndTimeWindow');
			this.stopPricePlanWindow.parent = this;
		}
		return this.stopPricePlanWindow;
	},
	
	//获取立即激活弹出窗口
    immediatelyActivePricePlanWindow:null,
	getimmediatelyActiveOffLinePricePlanWindow: function(pricePlanEntity){
		if(Ext.isEmpty(this.immediatelyActivePricePlanWindow)){
			this.immediatelyActivePricePlanWindow = Ext.create('Foss.pricing.outwardPrice.outwardPriceImmediatelyActiveTimeWindow');
			this.immediatelyActivePricePlanWindow.parent = this;
		}
		this.immediatelyActivePricePlanWindow.pricePlanEntity = pricePlanEntity;
		return this.immediatelyActivePricePlanWindow;
	},
	
	
	//获取立即中止弹出窗口
    immediatelyStopPricePlanWindow:null,
	getImmediatelyStopOffLinePricePlanWindow: function(pricePlanEntity){
		if(Ext.isEmpty(this.immediatelyStopPricePlanWindow)){
			this.immediatelyStopPricePlanWindow = Ext.create('Foss.pricing.outwardPrice.outwardPriceImmediatelyStopEndTimeWindow',{
				pricePlanEntity:pricePlanEntity
			});
			this.immediatelyStopPricePlanWindow.parent = this;
		}
		return this.immediatelyStopPricePlanWindow;
	},
	
	
	/**
	 * 立即中止功能
	 */
    immediatelyStopOffLinePricePlan:function(){
    	var me = this;
	 	var selections = me.getSelectionModel().getSelection();
	 	if(selections.length < 1){
	 		pricing.showWoringMessage(pricing.outwardPrice.i18n('foss.pricing.selectOneRecordToOp'));
			return;
	 	}
	 	if(selections.length > 1){
	 		pricing.showWoringMessage(pricing.outwardPrice.i18n('foss.pricing.selectOneRecordToOp'));
			return;
	 	}
	 	if(selections[0].get('active')!=pricing.yes){
	 		pricing.showWoringMessage(pricing.outwardPrice.i18n('foss.pricing.selectOneActiveRecordToOp'));
	 		return;
	 	}else{
	 		var pricePlanEntity = selections[0].data;
	 		me.getImmediatelyStopOffLinePricePlanWindow(pricePlanEntity).show();
	 	}
	},
	
	/**
	 * 立即激活功能
	 */
    immediatelyActiveOffLinePricePlan:function(){
    	var me = this;
	 	var selections = me.getSelectionModel().getSelection();
	 	if(selections.length < 1){
	 		pricing.showWoringMessage(pricing.outwardPrice.i18n('foss.pricing.selectOneRecordToOp'));
			return;
	 	}
	 	if(selections.length > 1){
	 		pricing.showWoringMessage(pricing.outwardPrice.i18n('foss.pricing.selectOneRecordToOp'));
			return;
	 	}
	 	if(selections[0].get('active')==pricing.yes){
	 		pricing.showWoringMessage(pricing.outwardPrice.i18n('foss.pricing.selectOneUnActiveRecordToOp'));
	 		return;
	 	}else{
	 		var pricePlanEntity = selections[0].data;
	 		me.getimmediatelyActiveOffLinePricePlanWindow(pricePlanEntity).show();
	 	}
	},
	
	//返回查询结果集store
	pricePlanStore:null,
	getPricePlanStore: function(){
		if(Ext.isEmpty(this.pricePlanStore)){
			this.pricePlanStore = Ext.create('Foss.pricing.outwardPrice.OuterPricePlanStore');
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
	
	/**
	 * 删除价格方案
	 */
	deletePricePlan:function(){
	 	var me = this;
	 	var selections = me.getSelectionModel().getSelection();
	 	if(selections.length < 1){//请选择一条进行删除操作！
	 		pricing.showWoringMessage(pricing.outwardPrice.i18n('foss.pricing.pleaseSelectOneDeleteOperation'));
			return;
	 	}
	 	for(var i = 0;i<selections.length;i++){
			if(selections[i].get('active')==pricing.yes){// 请选择未激活数据进行删除！
				pricing.showWoringMessage(pricing.outwardPrice.i18n('foss.pricing.pleaseChooseToNotActivateTheDataToBeBeleted'));
				return;
			}
		}
		//是否要删除这些汽运价格方案？
		pricing.showQuestionMes(pricing.outwardPrice.i18n('foss.pricing.theseTheProxyPriceProgramYouWantToRemove'),function(e){
			if(e=='yes'){
				//汽运价格方案
				var idList = new Array();
				for(var i = 0 ; i<selections.length ; i++){
					idList.push(selections[i].get('id'));
				}
				var params = {'externalPriceSchemeVo':{'idList':idList}};
				var successFun = function(json){
					pricing.showInfoMes(pricing.outwardPrice.i18n('foss.pricing.deleteSuccess'));//删除成功
					me.getPagingToolbar().moveFirst();//重新加载页面
				};
				var failureFun = function(json){
					if(Ext.isEmpty(json)){
						pricing.showErrorMes(pricing.outwardPrice.i18n('foss.pricing.requestTimedOut'));//请求超时
					}else{
						pricing.showErrorMes(json.message);
					}
				};
				var url = pricing.realPath('deleteExternalPriceScheme.action');
				pricing.requestJsonAjax(url,params,successFun,failureFun);
			}
		});
	},
	
	/**
	 * 激活时效方案
	 */
    activePricePlan:function(){
    	var me = this;
		var pricePlans = new Array();
		//获取选中的数据
		var selections = me.getSelectionModel().getSelection();
		if(selections.length<1){
			pricing.showErrorMes(pricing.outwardPrice.i18n('foss.pricing.pleaseChooseThePriceYouWantToActivateTheProgram'));//请选择要激活的价格方案！
			return;
		}

		var outerPricePlans = new Array();
		//过滤草稿状态数据进行删除
	 	for(var i = 0;i<selections.length;i++){
			if(selections[i].get('active')==pricing.yes){
				pricing.showWoringMessage(pricing.outwardPrice.i18n('foss.pricing.pleaseSelectNotActivateProgramDataForActivation'));
				return;
			}
			pricePlans.push(selections[i].get('id'));
			outerPricePlans.push(selections[i].data);
		}
		
		if(pricePlans.length<1){
			pricing.showErrorMes(pricing.outwardPrice.i18n('foss.pricing.pleaseSelectAtLeastOneInactivePriceProgram'));//请至少选择一条未激活的价格方案！
			return;
		}
		
		//是否要激活这些偏线外发价格方案？
		pricing.showQuestionMes(pricing.outwardPrice.i18n('foss.pricing.doYouWantActivateTheseProxyPriceScheme'),function(e){
			if(e=='yes'){
				var params = {'externalPriceSchemeVo':{entityList: outerPricePlans}};
				var successFun = function(json){
					pricing.showInfoMes(pricing.outwardPrice.i18n('dpap.authorization.activeSuccess'));//激活成功
					me.getPagingToolbar().moveFirst();
				};
				var failureFun = function(json){
					if(Ext.isEmpty(json)){
						pricing.showErrorMes(pricing.outwardPrice.i18n('i18n.pricingRegion.requestTimeOut'));//请求超时
					}else{
						pricing.showErrorMes(json.message);
					}
				};
				var url = pricing.realPath('activateExternalPriceScheme.action');
				pricing.requestJsonAjax(url,params,successFun,failureFun);
			}
		});
    },
    
    
    
    /**
     * 上传时效方案信息
     * @return {}
     */
    uploadPriceform : null,
    getUploadPriceform: function(){
    	if(Ext.isEmpty(this.uploadPriceform)){
			this.uploadPriceform = Ext.create('Foss.pricing.outwardPrice.UploadOuterPriceform');	
		}
		return this.uploadPriceform;
    },
    
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.store = me.getPricePlanStore();
		me.selModel = me.getCheckboxModel();
		me.bbar = me.getPagingToolbar();
		me.columns = [{xtype: 'rownumberer',
			width:40,
			text : pricing.outwardPrice.i18n('i18n.pricingRegion.num')//序号  
		},{
			text : pricing.outwardPrice.i18n('i18n.pricingRegion.opra'),//操作
			align : 'center',
			xtype : 'actioncolumn',
			items: [{
				iconCls:'deppon_icons_edit',
				tooltip: pricing.outwardPrice.i18n('foss.pricing.toAmendTheProposal'), //编辑
				disabled: !pricing.outwardPrice.isPermission('outerPrice/outerPriceUpdateButton'),
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
                	var params = {'externalPriceSchemeVo':{'id':record.get('id')}};
    				var successFun = function(json){
						updateWindow.pricePlanEntity = json.externalPriceSchemeVo.entity;
						pricing.pricePlanId = json.externalPriceSchemeVo.entity.id;
    					updateWindow.show(); 					
    				};
    				var failureFun = function(json){
    					if(Ext.isEmpty(json)){
    						pricing.showErrorMes(pricing.outwardPrice.i18n('foss.pricing.requestTimedOut'));//请求超时
    					}else{
    						pricing.showErrorMes(json.message);
    					}
    				};
    				var url = pricing.realPath('queryExternalPriceSchemeById.action');
    				pricing.requestJsonAjax(url,params,successFun,failureFun);
				}
			},{
				iconCls:'deppon_icons_softwareUpgrade',
				tooltip: pricing.outwardPrice.i18n('foss.pricing.replicationScheme'), //复制
				disabled: !pricing.outwardPrice.isPermission('outerPrice/outerPriceCopyButton'),
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
					var pricePlanId = record.get('id');
					var active = record.get('active');
					if(active == 'N'){
						pricing.showErrorMes(pricing.outwardPrice.i18n('foss.pricing.pleaseSelectTheProgramHaBbeenActivatedCopy'));
					}else{
						pricing.showQuestionMes(pricing.outwardPrice.i18n('foss.pricing.toDeterminePriceProgramCopy'),function(e){
							if(e=='yes'){
								var me = this;
								//处理复制功能
								var updateWindow =  grid.up().getUpdatePricePlanWindow();
								var params = {'externalPriceSchemeVo':{'id':pricePlanId}};
								var successFun = function(json){
									pricing.showInfoMes(json.message);
									pricing.pricePlanId = json.externalPriceSchemeVo.id;									
									updateWindow.pricePlanEntity = record.data;
									updateWindow.pricePlanEntity.id = json.externalPriceSchemeVo.id;
									//updateWindow.pricePlanEntity.name = json.outerPriceVo.copyName;
									updateWindow.pricePlanEntity.active = 'N';
			    					updateWindow.isUpdate = true;
			    					updateWindow.show();
			    					var waitingProcessGrid = Ext.getCmp('T_pricing-outwardPriceIndex_content').getOffLinePricePlanGridPanel();
			    					waitingProcessGrid.store.load();
								};
								var failureFun = function(json){
									if(Ext.isEmpty(json)){
										pricing.showErrorMes(pricing.outwardPrice.i18n('foss.pricing.requestTimedOut'));//请求超时
									}else{
										pricing.showErrorMes(json.message);
									}
								};
								var url = pricing.realPath('copyExternalPriceScheme.action');
								pricing.requestJsonAjax(url,params,successFun,failureFun);
							}
						});
					}
				}
			},{
				iconCls:'deppon_icons_end',
				tooltip: pricing.outwardPrice.i18n('foss.pricing.stop'), //中止
				disabled: !pricing.outwardPrice.isPermission('outerPrice/outerPriceStopButton'),
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
					var pricePlanId = record.get('id');
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
	        dataIndex: 'id'
		},{
			width: 140,
			text: pricing.outwardPrice.i18n('foss.pricing.scenarioName'),//方案名称
	        dataIndex: 'schemeName'
		},{
			width: 140,
			hidden: true,
			text: pricing.outwardPrice.i18n('foss.pricing.destinationStationNumber'),//目的站编号
	        dataIndex: 'agentDeptCode'
		},{
			width: 140,
			text: pricing.outwardPrice.i18n('foss.pricing.destinationStation'),
	        dataIndex: 'agentDeptName'
		},{
			width: 140,
			hidden: true,
			text:pricing.outwardPrice.i18n('foss.pricing.outFieldNameNumber'),
	        dataIndex: 'externalOrgCode'
		},{
			width: 140,
			text: pricing.outwardPrice.i18n('foss.pricing.outFieldName'),
	        dataIndex: 'externalOrgName'
		},{
			width: 70,
			text: pricing.outwardPrice.i18n('foss.pricing.transferType'),
	        dataIndex: 'transportType',
	        renderer: function(value){
				if(value=='TRANS_VEHICLE') {//汽运
					return pricing.outwardPrice.i18n('foss.pricing.car');			
				} else if(value=='TRANS_AIRCRAFT') {//空运
					return pricing.outwardPrice.i18n('foss.pricing.air');
				} else if(value=='TRANS_EXPRESS'){
					return pricing.outwardPrice.i18n('foss.pricing.exp');
				} else if(value=='TRANS_zhengche'){
					return pricing.outwardPrice.i18n('foss.pricing.wvh');
				}else{
					return null;
				}
			}
		},{
			width: 70,
			text: pricing.outwardPrice.i18n('foss.pricing.dataStatus'),
	        sortable: true,
	        dataIndex: 'active',
	        renderer:function(value){
				if(value=='Y'){
					return pricing.outwardPrice.i18n('foss.pricing.active');
				}else if(value=='N'){
					return pricing.outwardPrice.i18n('i18n.pricingRegion.unActive');
				}else{
					return '';
				}
			}
		},{
			text:pricing.outwardPrice.i18n('foss.pricing.availabilityDate'),
	        dataIndex: 'beginTime',
	        renderer:function(value){
				return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
			}
		},{
			text: pricing.outwardPrice.i18n('foss.pricing.endTimeTwo'),
	        dataIndex: 'endTime',
	        renderer:function(value){
				return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
			}
		},{
			text : pricing.outwardPrice.i18n('i18n.pricingRegion.isCurrentEdition'),
			dataIndex : 'currentUsedVersion'
		},{
			dataIndex : 'provCode',
			hidden: true,
			text: pricing.outwardPrice.i18n('foss.pricing.proCode')
	    },{
	    	dataIndex : 'cityCode',
			hidden: true,
			text:pricing.outwardPrice.i18n('foss.prcing.cityCode')
	    },{
	    	dataIndex : 'countyCode',
			hidden: true,
			text:pricing.outwardPrice.i18n('foss.pricing.countyCode')
	    },{
			dataIndex : 'provName',
			text: pricing.outwardPrice.i18n('i18n.pricingRegion.pro')
	    },{
	    	dataIndex : 'cityName',
			text:pricing.outwardPrice.i18n('i18n.pricingRegion.city')
	    },{
	    	dataIndex : 'countyName',
			text:pricing.outwardPrice.i18n('i18n.pricingRegion.county')
	    },{
			dataIndex : 'heavyPrice',
			text:pricing.outwardPrice.i18n('foss.prcing.heavyCargoRate')
	    },{
	    	dataIndex : 'lightPrice',
			text:pricing.outwardPrice.i18n('foss.pricing.lightCargoRate')
	    },{
	    	dataIndex : 'lowestPrice',
			text: pricing.outwardPrice.i18n('foss.pricing.theLowestVotes')
	    },{
			text: pricing.outwardPrice.i18n('foss.pricing.updateTime'),
			width: 140,
	        dataIndex: 'modifyDate',
	        renderer:function(value){
				return Ext.Date.format(new Date(value), 'Y-m-d H:m:s');
			}
		},{
			text: pricing.outwardPrice.i18n('foss.pricing.modifyUser'),
			width: 80,
	        dataIndex: 'modifyUser'
		}];
		me.tbar = [
		{
			text : pricing.outwardPrice.i18n('foss.pricing.theNewScheme'),//新建方案
			disabled: !pricing.outwardPrice.isPermission('outerPrice/outerPriceAddButton'),
			hidden: !pricing.outwardPrice.isPermission('outerPrice/outerPriceAddButton'),
			handler :function(){
				var addWindow = me.getAddpricePlanWindow();
				addWindow.isUpdate = false;
				addWindow.show();
				
			} 
		},'-', {
			text : pricing.outwardPrice.i18n('foss.pricing.activationProgram'),//激活方案
			disabled: !pricing.outwardPrice.isPermission('outerPrice/outerPriceActiveButton'),
			hidden: !pricing.outwardPrice.isPermission('outerPrice/outerPriceActiveButton'),
			handler :function(){
				me.activePricePlan();
			} 
		},'-', {
			text : pricing.outwardPrice.i18n('foss.pricing.deleteProgram'),//删除方案
			disabled: !pricing.outwardPrice.isPermission('outerPrice/outerPriceDeleteButton'),
			hidden: !pricing.outwardPrice.isPermission('outerPrice/outerPriceDeleteButton'),
			handler :function(){
				me.deletePricePlan();
			} 
		},'-',{
			text : pricing.outwardPrice.i18n('foss.pricing.immediatelyActivationProgram'),//立即激活
			disabled:!pricing.outwardPrice.isPermission('outerPrice/outerPriceImmediatelyActiveButton'),
			hidden:!pricing.outwardPrice.isPermission('outerPrice/outerPriceImmediatelyActiveButton'),
			handler :function(){
				var selection = Ext.getCmp('T_pricing-outwardPriceIndex_content').getOffLinePricePlanGridPanel().getSelectionModel().getSelection()[0];
				pricing.outwardPrice.immediatelyActiveId = selection.get('id');
				me.immediatelyActiveOffLinePricePlan();
			} 
		},'-', {
			text : pricing.outwardPrice.i18n('foss.pricing.immediatelyStopProgram'),//立即中止
			disabled:!pricing.outwardPrice.isPermission('outerPrice/outerPriceImmediatelyStopButton'),
			hidden:!pricing.outwardPrice.isPermission('outerPrice/outerPriceImmediatelyStopButton'),
			handler :function(grid, rowIndex, colIndex){
				var selection = Ext.getCmp('T_pricing-outwardPriceIndex_content').getOffLinePricePlanGridPanel().getSelectionModel().getSelection()[0];
				pricing.outwardPrice.immediatelyStopId = selection.get('id');
				me.immediatelyStopOffLinePricePlan();
			} 
		},'-', {
			text : pricing.outwardPrice.i18n('foss.pricing.export'),//导出
			disabled: !pricing.outwardPrice.isPermission('outerPrice/outerPriceExportButton'),
			hidden: !pricing.outwardPrice.isPermission('outerPrice/outerPriceExportButton'),
			handler :function(){
				var queryForm = pricing.outwardPrice.queryform;
				var pricePlanExport = '';				
				var agentDeptCode = queryForm.getForm().findField('agentDeptCode').getValue(); // 目的站
				var externalOrgCode = queryForm.getForm().findField('externalOrgCode').getValue(); //外发外场
				var transportType = queryForm.getForm().findField('transportType').getValue(); //运输类型
				var active = queryForm.getForm().findField('active').getValue(); //数据状态
				
				if(!Ext.isEmpty(agentDeptCode)){
					pricePlanExport = 'externalPriceSchemeVo.entity.agentDeptCode='+agentDeptCode;
				}
				if(!Ext.isEmpty(externalOrgCode)){
					pricePlanExport = pricePlanExport + '&' + 'externalPriceSchemeVo.entity.agentDeptCode='+externalOrgCode;
				}
				pricePlanExport = pricePlanExport + '&' + 'externalPriceSchemeVo.entity.transportType='+transportType;
				pricePlanExport = pricePlanExport + '&' + 'externalPriceSchemeVo.entity.active='+active;
				
				var url = pricing.realPath('exportExternalPriceScheme.action');
				if(!Ext.isEmpty(pricePlanExport)){
					url = url+'?'+pricePlanExport;
				}
				window.location=url;
				pricePlanExport = '';
 			} 
		},'-', {
			text : pricing.outwardPrice.i18n('foss.pricing.import'),//导入
			disabled: !pricing.outwardPrice.isPermission('outerPrice/outerPriceImportButton'),
			hidden: !pricing.outwardPrice.isPermission('outerPrice/outerPriceImportButton'),
			handler :function(){
			 	me.getUploadPriceform().show();
			} 
		}];
		pricing.pagingBar = me.bbar;
		me.callParent([cfg]);
	}
 	
});


/**
 * 偏线外发价费用方案批次信息录入form
 */
Ext.define('Foss.pricing.outwardPrice.outwardPriceAddFormPanel',{//新增panel
	extend : 'Ext.form.Panel',
	title: pricing.outwardPrice.i18n('foss.pricing.departureinformation'),
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
			name: 'schemeName',
			allowBlank:false,
			columnWidth: .5,
			maxLength : 65,
	        fieldLabel:pricing.outwardPrice.i18n('foss.pricing.scenarioName')//方案名称
		},{			
			name : 'agentDeptCode',
			xtype : 'commonvehagencydeptselector',
			fieldLabel :  pricing.outwardPrice.i18n('foss.pricing.destinationStation'),
			columnWidth: .5,
			listeners:{
				'select':function(combo,records,eOpts){
					if(records.length>0){
						var form = this.up('form').getForm();             
						var record = form.findField("agentDeptCode");
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
	        fieldLabel:pricing.outwardPrice.i18n('foss.pricing.provinceName')//省份
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
	        fieldLabel:pricing.outwardPrice.i18n('foss.pricing.cityName')//市
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
	        fieldLabel:pricing.outwardPrice.i18n('foss.pricing.areaName')//区
		},{
			name: 'transportType',
			fieldLabel: pricing.outwardPrice.i18n('foss.pricing.transferType'),
			allowBlank:false,
			columnWidth: .5,
			xtype: 'combobox',
			valueField:'valueCode', 
			displayField: 'valueName',
			value: 'TRANS_VEHICLE',
			queryMode:'local',
			triggerAction:'all',
			store : FossDataDictionary.getDataDictionaryStore("TRANS_TYPE")
		},{
			xtype : 'commontransfercenterselector', 
			fieldLabel : pricing.outwardPrice.i18n('foss.pricing.outFieldName'), 
			allowBlank:false,
			active:'Y',  
			name:'externalOrgCode',
			columnWidth: .5
		},{
			xtype:'datefield',
			allowBlank:false,
			fieldLabel:pricing.outwardPrice.i18n('foss.pricing.availabilityDate'),//生效日期
			format:'20y-m-d',
			name:'beginTime',
			columnWidth: .5
		},{
			xtype: 'numberfield',
			anchor: '100%',
			minValue: 0,
		    maxValue: 99999999,
			allowBlank:false,
			fieldLabel:pricing.outwardPrice.i18n('foss.pricing.heavyGoodsPrices'),
			name:'heavyPrice',
			columnWidth: .5
		},{
			xtype: 'numberfield',
			anchor: '100%',
			minValue: 0,
			maxValue: 99999999,
			allowBlank:false,
			fieldLabel:pricing.outwardPrice.i18n('foss.pricing.lightCargoPrices'),
			decimalPrecision :0,
			name:'lightPrice',
			columnWidth: .5
		},{
			xtype: 'numberfield',
			anchor: '100%',
			minValue: 0,
			maxValue: 99999999,
			allowBlank:false,
			fieldLabel:pricing.outwardPrice.i18n('foss.pricing.theLowestVotes'),
			name:'lowestPrice',
			allowDecimals : false,//只能为数字，不能为小数
			columnWidth: .5
		}],
	
	
	/***批次保存***/
	commitPricePlan:function(){
		var me = this;
    	//获取表单
    	var form = me.getForm(); 
    	var pricePlanModel = new Foss.pricing.outwardPrice.outwardPriceModel();
    	if(form.isValid()){
    		form.updateRecord(pricePlanModel);
    		var transportType = form.findField('transportType').getValue();	//
    		if (transportType == null ||transportType == '') {
    			pricing.showErrorMes(pricing.outwardPrice.i18n('foss.pricing.tranferTypeIsNotNull'));
    			return false;
    		}
    		var externalOrgCode = form.findField('externalOrgCode').getValue();
    		if (externalOrgCode == null || externalOrgCode == '') {
    			pricing.showErrorMes(pricing.outwardPrice.i18n('foss.pricing.outFieldNameIsNotNull'));
    			return false;
    		}
    		externalOrgCode = form.findField('externalOrgCode').displayTplData[0].orgCode;	//外发外场
    		if (externalOrgCode == null || externalOrgCode == '' || externalOrgCode == undefined) {
    			pricing.showErrorMes(pricing.outwardPrice.i18n('foss.pricing.outFieldNameIsNotNull'));
    			return false;
    		}
    		
    		var beginTime = form.findField('beginTime').getValue();	//

    		var time = Date.parse(beginTime);
    		var nowDate = new Date(); 
    		if (time < Date.parse(nowDate.format('yyyy-MM-dd'))) {
    			pricing.showErrorMes(pricing.outwardPrice.i18n('foss.pricing.effectiveDateCannotLessThanCurrentDate'));
    			return false;
    		}
    		var schemeName = form.findField('schemeName').getValue();
    		var agentDeptCode = form.findField('agentDeptCode').getValue();//目的站
    		var provCode = form.findField('provCode').getValue();	//省
    		var cityCode = form.findField('cityCode').getValue();	//市
    		var countyCode = form.findField('countyCode').getValue();	//区
    		var heavyPrice = form.findField('heavyPrice').getValue();
    		var lightPrice = form.findField('lightPrice').getValue();
    		var lowestPrice = form.findField('lowestPrice').getValue();
			
    		//处理特殊字段
    		pricePlanModel.set('schemeName',schemeName);
    		pricePlanModel.set('agentDeptCode',agentDeptCode);
    		pricePlanModel.set('externalOrgCode',externalOrgCode);
    		pricePlanModel.set('transportType',transportType);
    		pricePlanModel.set('provCode',provCode);
    		pricePlanModel.set('cityCode',cityCode);
    		pricePlanModel.set('countyCode',countyCode);
    		pricePlanModel.set('beginTime',beginTime);
    		pricePlanModel.set('heavyPrice',heavyPrice);
    		pricePlanModel.set('lightPrice',lightPrice);
    		pricePlanModel.set('lowestPrice',lowestPrice);
    		
    		var params = {'externalPriceSchemeVo':{'entity':pricePlanModel.data}};
    		
    		//成功提示
    		var successFun = function(json){
    			pricing.showInfoMes(pricing.outwardPrice.i18n('foss.pricing.saveSuccess'));//保存成功
    			Ext.getCmp('T_pricing-outwardPriceIndex_content').getOffLinePricePlanGridPanel().getPagingToolbar().moveFirst();//重新加载数据
        		me.getEl().unmask();  			
				pricing.pricePlanId = json.externalPriceSchemeVo.entity.id;  	//成功后获取价格方案主ID	
	    		var itemArrays = me.items.items;
	    		Ext.Array.each(itemArrays,function(value,index,arrays){
	    			itemArrays[index].setReadOnly(true);
	    		});
	    		me.getDockedItems()[1].items.items[1].setDisabled(true);//重置按钮不可用
				me.getDockedItems()[1].items.items[2].setDisabled(true);//保存按钮不可用				
	    	};
    	    
    	    //失败提示
    	    var failureFun = function(json){
    	    	if(Ext.isEmpty(json)){
    				pricing.showErrorMes(pricing.outwardPrice.i18n('i18n.pricingRegion.requestTimeOut'));
    			}else{
    				pricing.showErrorMes(json.message);
    			}
    	    };
    	    var url = pricing.realPath('addExternalPriceScheme.action');
    	    me.getEl().mask();
    		pricing.requestJsonAjax(url,params,successFun,failureFun);//发送AJAX请求
    	}
	},
	
	//构造函数
	constructor : function(config) {
		var me = this, 
		cfg = Ext.apply({}, config);
		me.fbar = [{
			text :pricing.outwardPrice.i18n('i18n.pricingRegion.cancel'),//取消
			handler :function(){
				me.up('window').isUpdate = null;
				me.up().close();
			}
		},{
			text : pricing.outwardPrice.i18n('foss.pricing.reset'),//重置
			handler :function(){
					me.getForm().reset();//表格重置
			} 
		},{
			text : pricing.outwardPrice.i18n('foss.pricing.save'),//保存
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
 * 偏线外发价费用方案批次信息修改form
 */
Ext.define('Foss.pricing.outwardPrice.outwardPriceUpdateFormPanel',{//修改panel
	extend : 'Ext.form.Panel',
	title:pricing.outwardPrice.i18n ('foss.pricing.departureinformation'),
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
			name: 'schemeName',
			allowBlank:false,
			maxLength : 65,
			columnWidth: .5,
	        fieldLabel:pricing.outwardPrice.i18n('foss.pricing.scenarioName')//方案名称
		},{
			name : 'agentDeptCode',
			xtype : 'commonvehagencydeptselector',
			fieldLabel :  pricing.outwardPrice.i18n('foss.pricing.destinationStation'),
			columnWidth: .5,
			listeners:{
				'select':function(combo,records,eOpts){
					if(records.length>0){
						var form = this.up('form').getForm();             
						var record = form.findField("agentDeptCode");
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
	        fieldLabel:pricing.outwardPrice.i18n('foss.pricing.provinceName')//省
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
	        fieldLabel:pricing.outwardPrice.i18n('foss.pricing.cityName')//市
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
	        fieldLabel:pricing.outwardPrice.i18n('foss.pricing.areaName')//区
		},{
			name: 'transportType',
			fieldLabel: pricing.outwardPrice.i18n('foss.pricing.transferType'),
			columnWidth: .5,
			xtype: 'combobox',
			valueField:'valueCode', 
			displayField: 'valueName',
			value: '',
			queryMode:'local',
			triggerAction:'all',
			convert:function(value, record){
				if(value=='TRANS_VEHICLE'){//汽运
					return pricing.outwardPrice.i18n('foss.pricing.car'); 
				}else if(value=='TRANS_AIRCRAFT'){//空运
					return pricing.outwardPrice.i18n('foss.pricing.air');
				}else{
					return null;
				}
			},
			store : FossDataDictionary.getDataDictionaryStore("TRANS_TYPE")
		},{
			xtype : 'commontransfercenterselector', 
			fieldLabel :pricing.outwardPrice.i18n('foss.pricing.outFieldName'), 
			allowBlank:false,
			active:'Y',  
			name:'externalOrgCode',
			columnWidth: .5
		},{
			xtype:'datefield',
			allowBlank:false,
			fieldLabel:pricing.outwardPrice.i18n('foss.pricing.availabilityDate'),//生效日期
			format:'20y-m-d',
			name:'beginTime',
			columnWidth: .5
		},{
			xtype: 'numberfield',
			anchor: '100%',
			minValue: 0,
			allowBlank:false,
			fieldLabel:pricing.outwardPrice.i18n('foss.pricing.heavyGoodsPrices'),
			name:'heavyPrice',
			columnWidth: .5
		},{
			xtype: 'numberfield',
			anchor: '100%',
			minValue: 0,
			allowBlank:false,
			fieldLabel:pricing.outwardPrice.i18n('foss.pricing.lightCargoPrices'),
			name:'lightPrice',
			columnWidth: .5
		},{
			xtype: 'numberfield',
			anchor: '100%',
			minValue: 0,
			maxValue: 99999999,
			allowBlank:false,
			fieldLabel:pricing.outwardPrice.i18n('foss.pricing.theLowestVotes'),
			allowDecimals : false,//只能为数字，不能为小数
			name:'lowestPrice',
			columnWidth: .5
		}],
	
	/**修改价格方案**/
	commitUpdatePricePlan:function(){
    	var me = this;
    	var form = me.getForm();
    	if(form.isValid()){//校验form是否通过校验
    		var pricePlanEntity = me.up('window').pricePlanEntity;
    		var pricePlanModel = new Foss.pricing.outwardPrice.outwardPriceModel(pricePlanEntity);
    		
    		form.updateRecord(pricePlanModel);//将FORM中数据设置到MODEL里面
    		
	    	var transportType = form.findField('transportType').valueCode;//运输类型
    		if (transportType == null ||transportType == '') {
    			pricing.showErrorMes(pricing.outwardPrice.i18n('foss.pricing.tranferTypeIsNotNull'));
    			return false;
    		}
    		var valueCode = form.findField('transportType').displayTplData[0].valueCode;//运输类型
    		if (valueCode == null || valueCode == '' || valueCode == undefined) {
    			
    		} else {
    			transportType = valueCode;
    		}
    		var externalOrgCode = form.findField('externalOrgCode').getValue();	//外发外场
    		if (externalOrgCode == null || externalOrgCode == '') {
    			pricing.showErrorMes(pricing.outwardPrice.i18n('foss.pricing.outFieldNameIsNotNull'));
    			return false;
    		}
    		orgCode = form.findField('externalOrgCode').displayTplData[0].orgCode;	//外发外场
    		if (orgCode == null || orgCode == '' || orgCode == undefined) {
    			
    		} else {
    			externalOrgCode = orgCode;
    		}
    		
    		var beginTime = form.findField('beginTime').getValue();	//生效时间

    		var time = Date.parse(beginTime);
    		var nowDate = new Date(); 
    		if (time < Date.parse(nowDate.format('yyyy-MM-dd'))) {
    			pricing.showErrorMes(pricing.outwardPrice.i18n('foss.pricing.effectiveDateCannotLessThanCurrentDate'));
    			return false;
    		}
    		var schemeName = form.findField('schemeName').getValue();
    		var agentDeptCode = form.findField('agentDeptCode').getValue();//目的站
    		var provCode = form.findField('provCode').getValue();	//省
    		var cityCode = form.findField('cityCode').getValue();	//市
    		var countyCode = form.findField('countyCode').getValue();	//区
    		
    		var heavyPrice = form.findField('heavyPrice').getValue();
    		var lightPrice = form.findField('lightPrice').getValue();
    		var lowestPrice = form.findField('lowestPrice').getValue();
			
    		//处理特殊字段
    		pricePlanModel.set('schemeName',schemeName);
    		pricePlanModel.set('agentDeptCode',agentDeptCode);
    		pricePlanModel.set('externalOrgCode',externalOrgCode);
    		pricePlanModel.set('transportType',transportType);
    		pricePlanModel.set('provCode',provCode);
    		pricePlanModel.set('cityCode',cityCode);
    		pricePlanModel.set('countyCode',countyCode);
    		pricePlanModel.set('beginTime',beginTime);
    		pricePlanModel.set('heavyPrice',heavyPrice);
    		pricePlanModel.set('lightPrice',lightPrice);
    		pricePlanModel.set('lowestPrice',lowestPrice);
    		
    		var params = {'externalPriceSchemeVo':{'entity':pricePlanModel.data}};//组织需要修改的数据
    		
    		var successFun = function(json){
    			pricing.showInfoMes(pricing.outwardPrice.i18n('foss.pricing.saveSuccess'));//保存成功
				me.up('window').close();
				var waitingProcessGrid = Ext.getCmp('T_pricing-outwardPriceIndex_content').getOffLinePricePlanGridPanel();
				waitingProcessGrid.store.load();
			};
			var failureFun = function(json){
				if(Ext.isEmpty(json)){
					pricing.showErrorMes(pricing.outwardPrice.i18n('foss.pricing.requestTimedOut'));//请求超时
				}else{
					pricing.showErrorMes(json.message);//提示失败原因
				}
			};
			var url = pricing.realPath('updateExternalPriceScheme.action');//请求价格方案修改
			pricing.requestJsonAjax(url,params,successFun,failureFun);//发送AJAX请求
    	}
	 },
	
	/***批次保存***/
	commitPricePlan:function(){
		var me = this;
    	//获取表单
    	var form = me.getForm(); 
    	var pricePlanModel = new Foss.pricing.outwardPrice.outwardPriceModel();
    	if(form.isValid()){
    		form.updateRecord(pricePlanModel);
    		var schemeName = form.findField('schemeName').getValue();
    		var agentDeptCode = form.findField('agentDeptCode').getValue();//目的站
    		var externalOrgCode = form.findField('externalOrgCode').getValue();	//最终外场
    		var provCode = form.findField('privateName').getValue();	//省
    		var cityCode = form.findField('cityName').getValue();	//市
    		var countyCode = form.findField('areaName').getValue();	//区
    		var transportType = form.findField('transportType').getValue();	//
    		var beginTime = form.findField('beginTime').getValue();	//
    		var heavyPrice = form.findField('heavyPrice').getValue();
    		var lightPrice = form.findField('lightPrice').getValue();
    		var lowestPrice = form.findField('lowestPrice').getValue();
			
    		//处理特殊字段
    		pricePlanModel.set('schemeName',schemeName);
    		pricePlanModel.set('agentDeptCode',agentDeptCode);
    		pricePlanModel.set('externalOrgCode',externalOrgCode);
    		pricePlanModel.set('transportType',transportType);
    		pricePlanModel.set('provCode',provCode);
    		pricePlanModel.set('cityCode',cityCode);
    		pricePlanModel.set('countyCode',countyCode);
    		pricePlanModel.set('beginTime',beginTime);
    		pricePlanModel.set('heavyPrice',heavyPrice);
    		pricePlanModel.set('lightPrice',lightPrice);
    		pricePlanModel.set('lowestPrice',lowestPrice);
    		
    		var params = {'externalPriceSchemeVo':{'entity':pricePlanModel.data}};//组织需要修改的数据
    		var url = pricing.realPath('updateExternalPriceScheme.action');
    		
    		//成功提示
    		var successFun = function(json){
    			pricing.showInfoMes(pricing.outwardPrice.i18n('foss.pricing.saveSuccess'));//保存成功
    			//成功后获取价格方案主ID
				pricing.pricePlanId = json.externalPriceSchemeVo.entity.id;  
				//me.getPricePlanDetailGridPanel().store.add();
				//获取formPanel所有属性,全部设置为只读	    		
	    		var itemArrays = me.items.items;
	    		Ext.Array.each(itemArrays,function(value,index,arrays){
	    			itemArrays[index].setReadOnly(true);
	    		});
	    		me.getDockedItems()[1].items.items[1].setDisabled(true);//重置按钮不可用
				me.getDockedItems()[1].items.items[2].setDisabled(true);//保存按钮不可用				
	    	};
    	    
    	    //失败提示
    	    var failureFun = function(json){
    	    	if(Ext.isEmpty(json)){
    				pricing.showErrorMes(pricing.outwardPrice.i18n('i18n.pricingRegion.requestTimeOut'));
    			}else{
    				pricing.showErrorMes(json.message);
    			}
    	    };
    		pricing.requestJsonAjax(url,params,successFun,failureFun);//发送AJAX请求
    	}
	},

	//构造函数
	constructor : function(config) {
		var me = this, 
		cfg = Ext.apply({}, config);
		me.fbar = [{
			text :pricing.outwardPrice.i18n('i18n.pricingRegion.cancel'),//取消
			handler :function(){
				me.up('window').isUpdate = null;
				me.up().close();
			}
		},{
			text : pricing.outwardPrice.i18n('foss.pricing.save'),//保存
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


//---------------------------------------------窗口------------------------------------------------
/**
 * 偏线外发价格费用新增方案弹出框
 */
Ext.define('Foss.pricing.outwardPrice.outwardPriceAddWindow',{
		extend: 'Ext.window.Window',
		title: pricing.outwardPrice.i18n('foss.pricing.newPricingPlan'),
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
				//wutao 修改 DEFECT-4044
				//属性设置只读属性为false 
				me.getPricePlanAddFormPanel().getForm().getFields().each(function(item){
					item.setReadOnly(false);
				});
				//上面循环把所有的都设置为可编辑，但是要把下面的设置为不可编辑
				me.getPricePlanAddFormPanel().getForm().findField('provName').setReadOnly(true);
				me.getPricePlanAddFormPanel().getForm().findField('cityName').setReadOnly(true);
				me.getPricePlanAddFormPanel().getForm().findField('countyName').setReadOnly(true);
				//end 
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
	    		this.pricePlanAddFormPanel = Ext.create('Foss.pricing.outwardPrice.outwardPriceAddFormPanel');
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
 * 偏线价费用方案弹出修改框
 */
Ext.define('Foss.pricing.outwardPrice.outwardPriceUpdateWindow',{
		extend: 'Ext.window.Window',
		title: pricing.outwardPrice.i18n('foss.pricing.modifyThePriceOfTheProgram'),
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
				me.getOuterPriceUpdateFormPanel().getForm().loadRecord(new Foss.pricing.outwardPrice.outwardPriceModel(me.pricePlanEntity));
				
				me.getOuterPriceUpdateFormPanel().getForm().findField('agentDeptCode').setCombValue(me.pricePlanEntity.agentDeptName, me.pricePlanEntity.agentDeptCode);
				me.getOuterPriceUpdateFormPanel().getForm().findField('externalOrgCode').setCombValue(me.pricePlanEntity.externalOrgName, me.pricePlanEntity.externalOrgCode);
				
				var transportType = me.pricePlanEntity.transportType;
				var productName = '';
				if (transportType == 'TRANS_VEHICLE') {//汽运
					productName = pricing.outwardPrice.i18n('foss.pricing.car'); 
				} else if (transportType == 'TRANS_AIRCRAFT') {//空运
					productName = pricing.outwardPrice.i18n('foss.pricing.air');
				} else if (transportType == 'TRANS_EXPRESS'){
					productName = pricing.outwardPrice.i18n('foss.pricing.exp');
				} else if (transportType == 'TRANS_zhengche'){
					productName = pricing.outwardPrice.i18n('foss.pricing.wvh');
				}
				me.getOuterPriceUpdateFormPanel().getForm().findField('transportType').setValue(productName); 
				me.getOuterPriceUpdateFormPanel().getForm().findField('transportType').valueCode = transportType;
				me.getOuterPriceUpdateFormPanel().getForm().findField('transportType').valueName = productName;				
			}
		},
	    //价格方案批次信息 （出发地批次信息录入）
		outerPriceUpdateFormPanel:null,
	    getOuterPriceUpdateFormPanel : function(){
	    	var me = this;
	    	if(Ext.isEmpty(me.outerPriceUpdateFormPanel)){
		    		me.outerPriceUpdateFormPanel = Ext.create('Foss.pricing.outwardPrice.outwardPriceUpdateFormPanel');
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
 * 偏线外发价费用方案中止方案弹出框
 */
Ext.define('Foss.pricing.outwardPrice.outwardPriceStopEndTimeWindow',{
		extend: 'Ext.window.Window',
		title: pricing.outwardPrice.i18n('foss.pricing.suspendPriceScheme'),
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
	    		this.pricePlanStopFormPanel = Ext.create('Foss.pricing.outwardPrice.outwardPriceStopFormPanel');
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
Ext.define('Foss.pricing.outwardPrice.outwardPriceStopFormPanel',{
	extend : 'Ext.form.Panel',
	layout:'column',
	width:410,
	height:100,
	pricePlanId:null,
	
	stopPricePlan:function(){
		var me = this;
    	//获取表单
    	var form = me.getForm();
    	if(form.isValid()){
    		var pricePlanEntity = me.up('window').pricePlanEntity;
			var pricePlanModel = new Foss.pricing.outwardPrice.outwardPriceModel(pricePlanEntity);
			form.updateRecord(pricePlanModel);
    		var time = form.findField('endTime').getValue();           
            
    		var nowDate = new Date(); 
    		if (Date.parse(time) < Date.parse(nowDate.format('yyyy-MM-dd'))) {
    			pricing.showErrorMes(pricing.outwardPrice.i18n('foss.prcing.endDateCannotLessThanCurrentDate'));
    			return false;
    		}
    		var pricePlanId = pricing.pricePlanId;
    		
    		var params = {'externalPriceSchemeVo':{'entity':pricePlanModel.data,'endTime': time}};
    		var url = pricing.realPath('immediatelySuspendScheme.action');
    		//成功提示
    		var successFun = function(json){
    			pricing.showInfoMes(pricing.outwardPrice.i18n('foss.pricing.stopSuccess'));//中止成功
    			me.up('window').close();
				var waitingProcessGrid = Ext.getCmp('T_pricing-outwardPriceIndex_content').getOffLinePricePlanGridPanel();
				waitingProcessGrid.store.load();
    	    };
    	    
    	    //失败提示
    	    var failureFun = function(json){
    	    	if(Ext.isEmpty(json)){
    				pricing.showErrorMes(pricing.outwardPrice.i18n('i18n.pricingRegion.requestTimeOut'));
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
			fieldLabel:pricing.outwardPrice.i18n('foss.pricing.deadline'),//截止日期
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
					text :pricing.outwardPrice.i18n('foss.pricing.stop'),
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
Ext.define('Foss.pricing.outwardPrice.outwardPriceImmediatelyStopEndTimeWindow',{
		extend: 'Ext.window.Window',
		title: pricing.outwardPrice.i18n('foss.pricing.immediatelySupendPricePriceScheme'),//"立即中止方案",
		width:380,
		height:152,
		pricePlanEntity:null,
		closeAction: 'hide' ,
		pricePlanStopFormPanel:null,
		getOffLinePricePlanStopFormPanel : function(pricePlanEntity){
	    	if(Ext.isEmpty(this.pricePlanAddFormPanel)){
	    		this.pricePlanStopFormPanel = Ext.create('Foss.pricing.outwardPrice.outwardPriceImmediatelyStopFormPanel',{
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
Ext.define('Foss.pricing.outwardPrice.outwardPriceImmediatelyStopFormPanel',{
	extend : 'Ext.form.Panel',
	layout:'column',
	pricePlanEntity:null,
	stopPricePlan:function(pricePlanId){
		var me = this;
    	var form = me.getForm();
    	if(form.isValid()){
    		var pricePlanModel = new Foss.pricing.outwardPrice.outwardPriceModel();
			form.updateRecord(pricePlanModel);
			pricePlanModel.set('endTime',Ext.Date.parse(form.findField('endTime').getValue(), 'Y-m-d H:i:s'));
    		pricePlanModel.set('id',pricePlanId);

    		var endTime = form.findField('endTime').getValue();
			var result = Ext.Date.parse(endTime,'Y-m-d H:i:s') - Ext.Date.parse(Ext.Date.format(new Date(),'Y-m-d H:i:s'),'Y-m-d H:i:s');
			if(result<0) {
    			pricing.showErrorMes(pricing.outwardPrice.i18n('foss.prcing.endDateCannotLessThanCurrentDate'));
    			return false;
			}						
			var params = {'externalPriceSchemeVo':{'entity':pricePlanModel.data,'endTime': endTime}};
    		var url = pricing.realPath('immediatelySuspendScheme.action');
    		
    		var successFun = function(json){
    			pricing.showInfoMes(pricing.outwardPrice.i18n('foss.pricing.stopSuccess'));//中止成功
    			me.up('window').hide();
    			var waitingProcessGrid = Ext.getCmp('T_pricing-outwardPriceIndex_content').getOffLinePricePlanGridPanel();
				waitingProcessGrid.store.load();	
    	    };
    	    var failureFun = function(json){
    	    	if(Ext.isEmpty(json)){
    				pricing.showErrorMes(pricing.outwardPrice.i18n('i18n.pricingRegion.requestTimeOut'));
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
				value:pricing.outwardPrice.i18n('foss.pricing.showleftTimeInfo')
					  +showbeginTime+ pricing.outwardPrice.i18n('foss.pricing.showmiddleTimeInfo')
					  +showendTime  + pricing.outwardPrice.i18n('foss.pricing.showstopRightEndTimeInfo')
				//'<p style="color:red">原方案生效日期为【2013-02-31】截止日期为【2013-09-11】,您是否立即中止该方案?</p>'
			},{ 
				fieldLabel :pricing.outwardPrice.i18n('foss.pricing.suspendTime') ,//'中止日期',
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
				text : pricing.outwardPrice.i18n('i18n.pricingRegion.determine'),//"确认",
				handler : function() {
					var pricePlanId = pricing.outwardPrice.immediatelyStopId;
					me.stopPricePlan(pricePlanId);
				}
			},{
				xtype : 'button', 
				width:70,
				columnWidth:.15,
				text : pricing.outwardPrice.i18n('i18n.pricingRegion.cancel'),//"取消",
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
Ext.define('Foss.pricing.outwardPrice.outwardPriceImmediatelyActiveTimeWindow',{
		extend: 'Ext.window.Window',
		title: pricing.outwardPrice.i18n('foss.pricing.immediatelyActiveationPriceScheme'),//"立即激活方案",
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
				var value = pricing.outwardPrice.i18n('foss.pricing.showleftTimeInfo')
					  + beginTime + pricing.outwardPrice.i18n('foss.pricing.showmiddleTimeInfo')
					  + endTime + pricing.outwardPrice.i18n('foss.pricing.showrightEndTimeInfo');
				
				me.pricePlanEntity.showTime = value;
				//DEFECT-4612:偏线外发存在已激活的方案，然后再新增一个新的方案，点立即激活的时候，之前的方案自动中止，请修改
				//wutao-200945 修改 
				//@date 2014-09-25 :20:56
				pricePlanEntity = me.pricePlanEntity;
				me.getOffLinePricePlanImmediatelyActiveFormPanel().getForm().loadRecord(new Foss.pricing.outwardPrice.outwardPriceModel(me.pricePlanEntity));
				me.getOffLinePricePlanImmediatelyActiveFormPanel().getForm().findField('beginTime').setValue(beginTime);
			}
		},
		OffLinePricePlanImmediatelyActiveFormPanel:null,
		getOffLinePricePlanImmediatelyActiveFormPanel : function(pricePlanEntity){
	    	if(Ext.isEmpty(this.OffLinePricePlanImmediatelyActiveFormPanel)){
	    		this.OffLinePricePlanImmediatelyActiveFormPanel = Ext.create('Foss.pricing.outwardPrice.outwardPriceImmediatelyActiveFormPanel');
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
Ext.define('Foss.pricing.outwardPrice.outwardPriceImmediatelyActiveFormPanel',{
	extend : 'Ext.form.Panel',
	layout:'column',
	pricePlanEntity:null,
	activetionPricePlan:function(pricePlanId){
		var me = this;
    	var form = me.getForm();
    	if(form.isValid()){    
        	var pricePlanModel = new Foss.pricing.outwardPrice.outwardPriceModel();
			form.updateRecord(pricePlanModel);
			pricePlanModel.data=pricePlanEntity;
			pricePlanModel.set('beginTime',Ext.Date.parse(form.findField('beginTime').getValue(), 'Y-m-d H:i:s'));
    		pricePlanModel.set('id',pricePlanId);
    		
    		var time = Ext.Date.parse(form.findField('beginTime').getValue(), 'Y-m-d H:i:s');
    		var nowDate = new Date(); 
    		if (time < nowDate) {
    			pricing.showErrorMes(pricing.outwardPrice.i18n('foss.pricing.effectiveDateCannotLessThanCurrentDate'));
    			return false;
    		}
    		 		
    		var params = {'externalPriceSchemeVo':{'entity':pricePlanModel.data,'beginTime': time}};
    		var url = pricing.realPath('immediatelyActivateScheme.action');
    		var successFun = function(json){
    			pricing.showInfoMes(pricing.outwardPrice.i18n('dpap.authorization.activeSuccess'));//激活成功
    			me.up('window').hide();
				var waitingProcessGrid = Ext.getCmp('T_pricing-outwardPriceIndex_content').getOffLinePricePlanGridPanel();
				waitingProcessGrid.store.load();			
    	    };
    	    var failureFun = function(json){
    	    	if(Ext.isEmpty(json)){
    				pricing.showErrorMes(pricing.outwardPrice.i18n('i18n.pricingRegion.requestTimeOut'));
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
				fieldLabel:pricing.outwardPrice.i18n('foss.pricing.availabilityDate'),//'生效日期',
				name : 'beginTime',
				xtype : 'datetimefield_date97',
				editable:false,
				time : true,
				id : 'Foss_outerPrice_activetionEndTime_ID',
				allowBlank:false,			
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
				text : pricing.outwardPrice.i18n('i18n.pricingRegion.determine'),//,"确认",
				handler : function() {
					var pricePlanId = pricing.outwardPrice.immediatelyActiveId;			
					me.activetionPricePlan(pricePlanId);
				}
			},{
				xtype : 'button', 
				width:70,
				columnWidth:.15,
				text : pricing.outwardPrice.i18n('i18n.pricingRegion.cancel'),//"取消",
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
Ext.define('Foss.pricing.outwardPrice.UploadOuterPriceform',{
	extend:'Ext.window.Window',
	title:pricing.outwardPrice.i18n('foss.pricing.importPriceScheme'),//导入价格方案
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
				fieldLabel:pricing.outwardPrice.i18n('foss.pricing.pleaseSelectAttachments'),//请选择附件
				labelWidth:100,
				buttonText:pricing.outwardPrice.i18n('foss.pricing.browse'),//浏览
				flex:3
			}]
		}];
		me.fbar = me.getFbar();
		me.callParent([cfg]);
	},
	getFbar:function(){
		var me = this;
		return [{
			text:pricing.outwardPrice.i18n('foss.pricing.import'),//导入
			xtype:'button',
			scope:me,
			handler:me.uploadFile
		},{
			text:pricing.outwardPrice.i18n('i18n.pricingRegion.cancel'),//取消
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
				pricing.showInfoMes(pricing.outwardPrice.i18n('foss.pricing.allDataImportSuccess'));//全部数据导入成功！
				me.close();
			}
			var waitingProcessGrid = Ext.getCmp('T_pricing-outwardPriceIndex_content').getOffLinePricePlanGridPanel();
			waitingProcessGrid.store.load();			
		};
		var failureFn = function(json){
			if(Ext.isEmpty(json)){
				pricing.showErrorMes(pricing.outwardPrice.i18n('i18n.pricingRegion.requestTimeOut'));//pricing.outwardPrice.i18n('i18n.pricingRegion.requestTimeOut')
			}else{
				pricing.showErrorMes(json.message);
			}
		};
		var uploadForm = me.down('form').getForm();
		var url = pricing.realPath('importExternalPriceScheme.action');
		uploadForm.submit({
            url: url,
            waitMsg: pricing.outwardPrice.i18n('foss.pricing.uploadYourAttachment'),
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
    		exception:function(form, action) {
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
	var queryform = Ext.create('Foss.pricing.outwardPrice.outwardPriceFormPanel');//查询条件Panel
	var gridPanel =	Ext.create('Foss.pricing.outwardPrice.outwardPriceGridPanel');//查询结果显示Grid
	pricing.outwardPrice.queryform = queryform;
	pricing.outwardPrice.gridPanel = gridPanel;
	Ext.getCmp('T_pricing-outwardPriceIndex').add(Ext.create('Ext.panel.Panel', {
	 	id:'T_pricing-outwardPriceIndex_content',
		cls:"panelContentNToolbar",
		bodyCls:'panelContent-body',		
		//获得查询FORM
		getQueryOffLinePricePlanForm : function() {
			return queryform;
		},
		//获得查询结果GRID
		getOffLinePricePlanGridPanel : function() {
			if(Ext.isEmpty(this.gridPanel)){
				this.gridPanel = Ext.create('Foss.pricing.outwardPrice.outwardPriceGridPanel');//查询结果GRID
			}
			return gridPanel;
		},
		
		items : [queryform,gridPanel]
	}));
});

