//转换long类型为日期
dict.changeLongToDate = function(value) {
	if (value != null) {
		var date = new Date(value);
		return date;
	} else {
		return null;
	}
};
//Ajax请求--json
dict.requestJsonAjax = function(url,params,successFn,failFn)
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
			failFn(result);
		}
	});
};
dict.configurationParams.configurationParamsSystem = [{'valueName':dict.configurationParams.i18n('foss.dict.integratedModule'),'valueCode':'SYSTEM_CONFIG_PARM__BAS'}
,{'valueName':dict.configurationParams.i18n('foss.dict.takeDeliveryModule'),'valueCode':'SYSTEM_CONFIG_PARM__PKP'},{'valueName':dict.configurationParams.i18n('foss.dict.transitModule'),'valueCode':'SYSTEM_CONFIG_PARM__TFR'}
,{'valueName':dict.configurationParams.i18n('foss.dict.accountingModule'),'valueCode':'SYSTEM_CONFIG_PARM__STL'}];
dict.configurationParams.configurationParamsSystemNbsp = [{'valueName':dict.configurationParams.i18n('foss.dict.integratedModuleNBSP'),'valueCode':'SYSTEM_CONFIG_PARM__BAS'}
,{'valueName':dict.configurationParams.i18n('foss.dict.takeDeliveryModuleNBSP'),'valueCode':'SYSTEM_CONFIG_PARM__PKP'},{'valueName':dict.configurationParams.i18n('foss.dict.transitModuleNBSP'),'valueCode':'SYSTEM_CONFIG_PARM__TFR'}
,{'valueName':dict.configurationParams.i18n('foss.dict.accountingModuleNBSP'),'valueCode':'SYSTEM_CONFIG_PARM__STL'}];
//--------------------------------------dict----------------------------------------

//配置参数MODEL
Ext.define('Foss.dict.configurationParams.ConfigurationParamsEntity', {
    extend: 'Ext.data.Model',
    fields : [{
        name : 'id',
        type : 'string'
    },{
        name : 'virtualCode',//虚拟编码
        type : 'string'
    },{
        name : 'code',//配置项代码
        type : 'string'
    },{
        name : 'confName',//配置项名称
        type : 'string'
    },{
        name : 'confValue',//配置项值
        type : 'string'
    },{
        name : 'orgCode',//部门编码
        type : 'string'
    },{
        name : 'orgName',//部门名称
        type : 'string'
    },{
        name : 'confType',//配置参数类别
        type : 'string'
    },{
        name : 'dataType',//数据类别
        type : 'string'
    },{
        name : 'unit',//单位
        type : 'string'
    },{
        name : 'notes',//备注
        type : 'string'
    },{
        name : 'active',//是否启用
        type : 'string'
    },{
        name : 'type',//类型，我自己加的，不会传到后台，用来在保存数据时获取值
        type : 'string'
    }]
});
//------------------------------------model---------------------------------------------------

/**
 * p配置参数Store
 */
Ext.define('Foss.dict.configurationParams.ConfigurationParamsStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.dict.configurationParams.ConfigurationParamsEntity',//配置参数MODEL
	pageSize:20,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url : "../dict/queryConfigurationParamsByEntity.action",//查询的url
		reader : {
			type : 'json',
			root : 'configurationParamsVo.configurationParamsEntityList',//结果集
			totalProperty : 'totalCount'//个数
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
dict.getStore = function(storeId,model,fields,data) {
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
 * 查询配置参数表单
 */
Ext.define('Foss.dict.configurationParams.QueryConfigurationParamsForm', {
	extend : 'Ext.form.Panel',
	title: dict.configurationParams.i18n('foss.dict.searchCondiction'),//查询条件
	frame: true,
	collapsible: true,
    defaults : {
    	columnWidth : .3,
    	margin : '8 10 5 10',
       	anchor : '100%'
    },
    height :150,
	defaultType : 'textfield',
	layout:'column',
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		var dataStore = FossDataDictionary.getDataDictionaryStore(config.itemId);
		if(!Ext.isEmpty(dataStore)){
			var all = {valueCode:'',valueName:dict.configurationParams.i18n('foss.dict.all')};
			dataStore.add(all);
		}
		me.items = [{
			name: 'code',
			queryMode: 'local',
		    displayField: 'valueName',
		    valueField: 'valueCode',
		    value:'',
		    store:dataStore,
		    xtype:'combo',
	        fieldLabel: dict.configurationParams.i18n('foss.dict.configuration')//配置项编码
		},{
			xtype : 'dynamicorgcombselector',
			name: 'orgCode',
			types:'ORG',
	        fieldLabel: dict.configurationParams.i18n('foss.dict.org')//组织
		}];
		me.fbar = [{
			xtype : 'button', 
			width:70,
			disabled:!dict.configurationParams.isPermission('configurationParams/configurationParamsQueryButton'),
			hidden:!dict.configurationParams.isPermission('configurationParams/configurationParamsQueryButton'),
			text : dict.configurationParams.i18n('foss.dict.reset'),//重置
			handler : function() {
				me.getForm().reset();
			}
		},{
			xtype : 'button', 
			width:70,
			cls:'yellow_button',
			margin:'0 0 0 810',
			disabled:!dict.configurationParams.isPermission('configurationParams/configurationParamsQueryButton'),
			hidden:!dict.configurationParams.isPermission('configurationParams/configurationParamsQueryButton'),
			text : dict.configurationParams.i18n('foss.dict.search'),//查询
			handler : function() {
				if(me.getForm().isValid()){
					me.up().getConfigurationParamsGridPanel().getPagingToolbar().moveFirst();
				}
			}
		}];
		me.callParent([cfg]);
	}
});
/**
 * 配置项列表
 */
Ext.define('Foss.dict.configurationParams.ConfigurationParamsGridPanel', {
	extend: 'Ext.grid.Panel',
	title : dict.configurationParams.i18n('foss.dict.searchResult'),//查询结果列表
	frame: true,
    sortableColumns:false,
    enableColumnHide:false,
    enableColumnMove:false,
    bodyCls: 'autoHeight',
    //enableColumnResize : false, //固定列的尺寸，禁止其拖拽，改变宽度大小
	stripeRows : true, // 交替行效果
	selType : "rowmodel", // 选择类型设置为：行选择
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
	//作废配置参数
	toVoid: function(btn){
		var me = this;
		//获取选中的数据
		var selections = me.getSelectionModel().getSelection();
		if(selections.length<1){
			dict.showErrorMes(dict.configurationParams.i18n('foss.dict.pleaseOneToOp'));
			return;
		}
		dict.showQuestionMes(dict.configurationParams.i18n('foss.dict.isVoidConfigurationParams'),function(e){//是否要作废这些配置参数？
			if(e=='yes'){//询问是否删除，是则发送请求
				var virtualCodeList = new Array();
				for(var i = 0 ; i<selections.length ; i++){
					virtualCodeList.push(selections[i].get('virtualCode'));
				}
				var params = {'configurationParamsVo':{'configurationParamsVirtualCodeList':virtualCodeList}};
				var successFun = function(json){
					dict.showInfoMes(json.message);
					me.getPagingToolbar().moveFirst();
				};
				var failureFun = function(json){
					if(Ext.isEmpty(json)){
						dict.showErrorMes(dict.configurationParams.i18n('foss.dict.requestTimeout'));//请求超时
					}else{
						dict.showErrorMes(json.message);
					}
				};
				var url = dict.realPath('deleteConfigurationParams.action');
				dict.requestJsonAjax(url,params,successFun,failureFun);
			}
		})
	},
	
	//配置参数新增
	configurationParamsAddWindow:null,
	getConfigurationParamsAddWindow : function() {
		if (this.configurationParamsAddWindow == null) {
			this.configurationParamsAddWindow = Ext.create('Foss.dict.configurationParams.ConfigurationParamsAddWindow',{
				'itemId':this.up('panel').itemId
			});
			this.configurationParamsAddWindow.parent = this;
		}
		return this.configurationParamsAddWindow;
	},
	//配置参数新增
	configurationParamsUpdateWindow:null,
	getConfigurationParamsUpdateWindow : function() {
		if (this.configurationParamsUpdateWindow == null) {
			this.configurationParamsUpdateWindow = Ext.create('Foss.dict.configurationParams.ConfigurationParamsUpdateWindow',{
				'itemId':this.up('panel').itemId
			});
			this.configurationParamsUpdateWindow.parent = this;
		}
		return this.configurationParamsUpdateWindow;
	},
	
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.columns = [{xtype: 'rownumberer',
			width:40,
			text : dict.configurationParams.i18n('foss.dict.num')//序号
		},{
			text : dict.configurationParams.i18n('foss.dict.op'),//操作
			align : 'center',
			xtype : 'actioncolumn',
			items: [{
				iconCls: 'deppon_icons_edit',
				tooltip: dict.configurationParams.i18n('foss.dict.update'),//修改
				disabled:!dict.configurationParams.isPermission('configurationParams/configurationParamsQueryUpdateButton'),
				width:42,
                handler: function(grid, rowIndex, colIndex) {
                	//获取选中的数据
    				var record=grid.getStore().getAt(rowIndex);
                	var virtualCode = record.get('virtualCode');//词条代码
    				var params = {'configurationParamsVo':{'configurationParamsEntity':{'virtualCode':virtualCode}}};
    				var successFun = function(json){
    					var updateWindow = me.getConfigurationParamsUpdateWindow();//获得修改窗口
    					updateWindow.configurationParamsEntity = json.configurationParamsVo.configurationParamsEntity;//配置参数
    					updateWindow.show();//显示修改窗口
    				};
    				var failureFun = function(json){
    					if(Ext.isEmpty(json)){
    						dict.showErrorMes(dict.configurationParams.i18n('foss.dict.requestTimeout'));//请求超时
    					}else{
    						dict.showErrorMes(json.message);
    					}
    				};
    				var url = dict.realPath('searchConfigurationParamsInfo.action');
    				dict.requestJsonAjax(url,params,successFun,failureFun);
                	
                }
			},{
				iconCls: 'deppon_icons_cancel',
                tooltip: dict.configurationParams.i18n('foss.dict.void'),//作废
                disabled:!dict.configurationParams.isPermission('configurationParams/configurationParamsQueryCancelButton'),
				width:42,
                handler: function(grid, rowIndex, colIndex) {
                	dict.showQuestionMes(dict.configurationParams.i18n('foss.dict.isVoidDataDictionary'),function(e){//是否要作废这些业务字典？
            			if(e=='yes'){//询问是否删除，是则发送请求
            				var virtualCodeList = new Array();
            				//获取选中的数据
            				var record=grid.getStore().getAt(rowIndex);
            				virtualCodeList.push(record.get('virtualCode'));
            				var params = {'configurationParamsVo':{'configurationParamsVirtualCodeList':virtualCodeList}};
            				var successFun = function(json){
            					dict.showInfoMes(json.message);
            					me.getPagingToolbar().moveFirst();
            				};
            				var failureFun = function(json){
            					if(Ext.isEmpty(json)){
            						dict.showErrorMes(dict.configurationParams.i18n('foss.dict.requestTimeout'));//请求超时
            					}else{
            						dict.showErrorMes(json.message);
            					}
            				};
            				var url = dict.realPath('deleteConfigurationParams.action');
            				dict.requestJsonAjax(url,params,successFun,failureFun);
            			}
            		})
                	
                }
			}]

		},{
			text : dict.configurationParams.i18n('foss.dict.configurationItemCode'),//配置项编码
			width:120,
			xtype : 'ellipsiscolumn',
			dataIndex : 'code'
		},{
			text : dict.configurationParams.i18n('foss.dict.configurationName'),//配置项名称
			width:120,
			xtype : 'ellipsiscolumn',
			dataIndex : 'confName'
		},{
			text : dict.configurationParams.i18n('foss.dict.organizationCode'),//组织编码
			width:120,
			xtype : 'ellipsiscolumn',
			dataIndex : 'orgCode'
		},{
			text : dict.configurationParams.i18n('foss.dict.organizationName'),//配置项名称
			width:120,
			xtype : 'ellipsiscolumn',
			dataIndex : 'orgName'
		},{
			text : dict.configurationParams.i18n('foss.dict.value'),//值
			width:120,
			xtype : 'ellipsiscolumn',
			dataIndex : 'confValue'
		},{
			text : dict.configurationParams.i18n('foss.dict.util'),//单位
			width:120,
			xtype : 'ellipsiscolumn',
			dataIndex : 'unit'
		}];
		me.store = Ext.create('Foss.dict.configurationParams.ConfigurationParamsStore',{
			autoLoad : false,
			pageSize : 20,
			listeners: {
				beforeload: function(store, operation, eOpts){
					var queryForm = me.up().getQueryConfigurationParamsForm();
					if(queryForm!=null){
						Ext.apply(operation,{
							params : {
								'configurationParamsVo.configurationParamsEntity.code':queryForm.getForm().findField('code').getValue(),
								'configurationParamsVo.configurationParamsEntity.orgCode':queryForm.getForm().findField('orgCode').getValue(),
								'configurationParamsVo.configurationParamsEntity.confType':me.up('panel').itemId
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
			text : dict.configurationParams.i18n('foss.dict.void'),//作废
			disabled:!dict.configurationParams.isPermission('configurationParams/configurationParamsQueryCancelButton'),
			hidden:!dict.configurationParams.isPermission('configurationParams/configurationParamsQueryCancelButton'),
			handler :function(){
				me.toVoid();
			} 
		},{
			text : dict.configurationParams.i18n('foss.dict.add'),//新增
			disabled:!dict.configurationParams.isPermission('configurationParams/configurationParamsQueryAddButton'),
			hidden:!dict.configurationParams.isPermission('configurationParams/configurationParamsQueryAddButton'),
			handler :function(){
				var itemId = me.up('panel').itemId;
				me.getConfigurationParamsAddWindow().show();
				me.getConfigurationParamsAddWindow().getConfigurationParamsForm().getForm().findField('confType').setValue(itemId);
			} 
		} ];
		me.bbar = me.getPagingToolbar();
		me.getPagingToolbar().store = me.store;
		me.callParent([cfg]);
	}
});
//参数配置TAB
Ext.define('Foss.dict.configurationParams.ConfigurationParamsTabPanel', {
	extend:'Ext.tab.Panel',
	height:'100%',
	width:'100%',
	cls : 'innerTabPanel',
    createTab:function(){
    	var me = this;
    	var items = new Array();
    	for(var i= 0;i<dict.configurationParams.configurationParamsSystemNbsp.length;i++){
    		var tab = Ext.create('Foss.dict.configurationParams.ConfigurationParamsInnerPanel',{
    			title:dict.configurationParams.configurationParamsSystemNbsp[i].valueName,
    			itemId:dict.configurationParams.configurationParamsSystemNbsp[i].valueCode
    		});
    		items.push(tab);
    	}
    	return items;
    },
    constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
        me.items = me.createTab();
        me.activeTab = 0;
		me.callParent([cfg]);
		
	}
});
Ext.define('Foss.dict.configurationParams.ConfigurationParamsInnerPanel',{
	extend:'Ext.panel.Panel',
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	//查询form
	queryConfigurationParamsForm:null,
	getQueryConfigurationParamsForm:function(itemId){
		if (this.queryConfigurationParamsForm == null) {
			this.queryConfigurationParamsForm = Ext.create('Foss.dict.configurationParams.QueryConfigurationParamsForm',{
				'itemId':itemId
			});
		}
		return this.queryConfigurationParamsForm;
	},
	//查询GRID
	configurationParamsGridPanel:null,
	getConfigurationParamsGridPanel:function(){
		if (this.configurationParamsGridPanel == null) {
			this.configurationParamsGridPanel = Ext.create('Foss.dict.configurationParams.ConfigurationParamsGridPanel');
		}
		return this.configurationParamsGridPanel;
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.items = [me.getQueryConfigurationParamsForm(config.itemId),me.getConfigurationParamsGridPanel()]
		me.callParent([cfg]);
	}
});
/**
 * @description 参数配置主页
 */
Ext.onReady(function() {
	Ext.QuickTips.init();
	if (Ext.getCmp('T_dict-configurationParams_content')) {
		return;
	}
	var configurationParamsTab = Ext.create('Foss.dict.configurationParams.ConfigurationParamsTabPanel');//查询FORM
	Ext.create('Ext.panel.Panel', {
		id : 'T_dict-configurationParams_content',
		cls : 'panelContentNToolbar',
		bodyCls : 'panelContentNToolbar-body',
		//获取TAB
		getConfigurationParamsTab : function() {
			return configurationParamsTab;
		},
		items : [configurationParamsTab],
		renderTo : 'T_dict-configurationParams-body'
	});
});
//----------------------------------------------上面是整体布局，下面是弹出窗口----------------------------------
/**
 * 新增配置参数
 */
Ext.define('Foss.dict.configurationParams.ConfigurationParamsAddWindow',{
	extend : 'Ext.window.Window',
	title : dict.configurationParams.i18n('foss.dict.theNewConfigurationParameters'),//新增配置参数
	parent:null,//父元素（Foss.dict.configurationParams.ConfigurationParamsGridPanel）
	closable : true,
	modal : true,
	resizable:false,
	closeAction : 'hide',
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	width :450,
	height :460,
	listeners:{
		beforehide:function(me){
			me.getConfigurationParamsForm().getForm().reset();//表格重置
			var field  = me.getConfigurationParamsForm().getForm().findField('dataType');
			me.getConfigurationParamsForm().getForm().findField(field.oldFiled).hide();
			field.oldFiled = 'show';
			me.getConfigurationParamsForm().getForm().findField('show').show();
			
		},
		beforeshow:function(me){//根据区域ID和区域性质，查询区域关联部门
		}
	},
	//新增参数配置FORM
	configurationParamsForm:null,
    getConfigurationParamsForm : function(itemId){
    	if(Ext.isEmpty(this.configurationParamsForm)){
    		this.configurationParamsForm = Ext.create('Foss.dict.configurationParams.ConfigurationParamsForm',{
    			itemId:itemId
    		});
    	}
    	return this.configurationParamsForm;
    },
    //提交配置参数
    commitConfigurationParams:function(){
    	var me = this;
    	var configurationParamsModel = new Foss.dict.configurationParams.ConfigurationParamsEntity();
    	var form = me.getConfigurationParamsForm().getForm();
    	form.updateRecord(configurationParamsModel);
    	

    	// 087584-foss-lijun 解决提交时“名称”为空的问题
		// 请求合法性验证
		if(!form.isValid()){
			return;
		}
		
    	var orgCodes = form.findField('orgCodes').orgCodes;
    	/*
    	if(Ext.isEmpty(orgCodes)){
    		dict.showErrorMes(dict.configurationParams.i18n('foss.dict.pleaseSelectOrg'));//请至少选择一个部门！
    		return;
    	}*/
    	if(configurationParamsModel.get('dataType')==null){
    		dict.showErrorMes(dict.configurationParams.i18n('foss.dict.pleaseSelectTheTypeOf'));
    		return;
    	}
//    	if(configurationParamsModel.get('confValue')==null){
//    		dict.showErrorMes(dict.configurationParams.i18n('foss.dict.pleaseSelectConfigurationItemCode'));
//    		return;
//    	}
    	
    	configurationParamsModel.set('confValue',form.findField(configurationParamsModel.get('dataType')).getValue());
    	var configurationParamsEntityList = new Array();
    	for(var i = 0;i<orgCodes.length;i++){
    		var model = new Foss.dict.configurationParams.ConfigurationParamsEntity(configurationParamsModel.data);
    		model.set('orgCode',orgCodes[i]);
    		configurationParamsEntityList.push(model.data);
    	}
    	var params = {'configurationParamsVo':{'configurationParamsEntityList':configurationParamsEntityList}};//保存参数
    	var successFun = function(json){
			dict.showInfoMes(json.message);
			me.parent.getPagingToolbar().moveFirst();
			me.close();
		};
		var failureFun = function(json){
			if(Ext.isEmpty(json)){
				dict.showErrorMes(dict.configurationParams.i18n('foss.dict.requestTimeout'));//请求超时
			}else{
				dict.showErrorMes(json.message);
			}
		};
		var url = dict.realPath('addConfigurationParams.action');
		dict.requestJsonAjax(url,params,successFun,failureFun);
    },
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.fbar = [{
			text : dict.configurationParams.i18n('foss.dict.cancel'),//取消
			handler :function(){
				me.close();
			} 
		},{
			text : dict.configurationParams.i18n('foss.dict.reset'),//重置
			handler :function(){
				me.getConfigurationParamsForm().getForm().reset();
				var field  = me.getConfigurationParamsForm().getForm().findField('dataType');
				me.getConfigurationParamsForm().getForm().findField(field.oldFiled).hide();
				field.oldFiled = 'show';
				me.getConfigurationParamsForm().getForm().findField('show').show();
			} 
		},{
			text : dict.configurationParams.i18n('foss.dict.save'),//保存
			cls:'yellow_button',
			margin:'0 0 0 180',
			handler :function(){
				me.commitConfigurationParams();
			} 
		}];
		me.items = [me.getConfigurationParamsForm(config.itemId)];
		me.callParent([cfg]);
	}
});

/**
 * 修改配置参数
 */
Ext.define('Foss.dict.configurationParams.ConfigurationParamsUpdateWindow',{
	extend : 'Ext.window.Window',
	title : dict.configurationParams.i18n('foss.dict.modifyTheConfigurationParameters'),//修改配置参数
	parent:null,//父元素（Foss.dict.configurationParams.ConfigurationParamsGridPanel）
	closable : true,
	modal : true,
	resizable:false,
	configurationParamsEntity:null,//修改后的值存在window的属性中
	closeAction : 'hide',
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	width :450,
	height :460,
	listeners:{
		beforehide:function(me){
			me.getConfigurationParamsForm().getForm().reset();//表格重置
			var field  = me.getConfigurationParamsForm().getForm().findField('dataType');
			me.getConfigurationParamsForm().getForm().findField(field.oldFiled).hide();
			field.oldFiled = 'show';
			me.getConfigurationParamsForm().getForm().findField('show').show();
		},
		beforeshow:function(me){//设置数据
			me.loadData(me);
		}
	},
	//修改参数配置FORM
	configurationParamsForm:null,
    getConfigurationParamsForm : function(itemId){
    	if(Ext.isEmpty(this.configurationParamsForm)){
    		this.configurationParamsForm = Ext.create('Foss.dict.configurationParams.ConfigurationParamsForm',{
    			itemId:itemId
    		});
    		this.configurationParamsForm.items.items[3].setDisabled(true);
    	}
    	return this.configurationParamsForm;
    },
    //提交配置参数数据
    commitConfigurationParams:function(){
    	var me = this;
    	var configurationParamsModel = new Foss.dict.configurationParams.ConfigurationParamsEntity(me.configurationParamsEntity);
    	var form = me.getConfigurationParamsForm().getForm();
    	form.updateRecord(configurationParamsModel);
    	
    	// 087584-foss-lijun 解决提交时“名称”为空的问题
		// 请求合法性验证
		if(!form.isValid() ){
			return;
		}
    	
    	configurationParamsModel.set('confValue',form.findField(configurationParamsModel.get('dataType')).getValue());
    	var params = {'configurationParamsVo':{'configurationParamsEntity':configurationParamsModel.data}};//保存参数
    	var successFun = function(json){
			dict.showInfoMes(json.message);
			me.parent.getPagingToolbar().moveFirst();
			me.close();
		};
		var failureFun = function(json){
			if(Ext.isEmpty(json)){
				dict.showErrorMes(dict.configurationParams.i18n('foss.dict.requestTimeout'));//请求超时
			}else{
				dict.showErrorMes(json.message);
			}
		};
		var url = dict.realPath('updateConfigurationParams.action');
		dict.requestJsonAjax(url,params,successFun,failureFun);
    },
    //加载数据
    loadData:function(me){
    	var me = this;
    	var data = new Foss.dict.configurationParams.ConfigurationParamsEntity(me.configurationParamsEntity);
    	var form = me.getConfigurationParamsForm().getForm();
    	form.loadRecord(data);//加载配置参数数据
    	if(Ext.isEmpty(form.findField(data.get('dataType')))){
    		dict.showErrorMes(dict.configurationParams.i18n('foss.dict.dataTypeError'));//数据类型有误，无法打开修改界面！
    		me.hide();
    		return;
    	}
    	form.findField(data.get('dataType')).setValue(data.get('confValue'));
    	form.findField(data.get('dataType')).show();
    	//form.findField('dataType').setReadOnly(true);
    	var orgName = data.get('orgName');
    	form.findField('orgCodes').setValue(orgName);
    	var orgCodes = new Array();
    	orgCodes.push(data.get('orgCode'));
    	form.findField('orgCodes').orgCodes = orgCodes;
    },
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.fbar = [{
			text : dict.configurationParams.i18n('foss.dict.cancel'),//取消
			handler :function(){
				me.close();
			} 
		},{
			text : dict.configurationParams.i18n('foss.dict.reset'),//重置
			handler :function(){
				me.loadData();
			} 
		},{
			text : dict.configurationParams.i18n('foss.dict.save'),//保存
			cls:'yellow_button',
			margin:'0 0 0 180',
			handler :function(){
				me.commitConfigurationParams();
			} 
		}];
		me.items = [ me.getConfigurationParamsForm(config.itemId)];
		me.callParent([cfg]);
	}
});
/**
 * 参数配置-FORM
 */
Ext.define('Foss.dict.configurationParams.ConfigurationParamsForm', {
	extend : 'Ext.form.Panel',
	frame: true,
	height:400,
	collapsible: true,
	isSearchComb:true,
    defaults : {
    	colspan : 2,
    	margin : '5 5 5 5',
    	labelWidth:80,
    	anchor : '100%'
    },
	defaultType : 'textfield',
	layout: {
        type: 'table',
        columns: 2
    },
    orgWindow:null,
    getOrgWindow:function(){
    	var me = this;
    	if(Ext.isEmpty(this.orgWindow)){
    		this.orgWindow = Ext.create('Foss.baseinfo.commonSelector.orgSelectorWindow',{
    			type:'ORG',
    			active:'Y',
    			modal:true,
    			commitFun:function(){
    				var selections = this.getGridRecord();
    				
    				if(selections.length<1){
    					dict.showErrorMes(dict.configurationParams.i18n('foss.dict.pleaseSelectOrg'));//请至少选择一条数据！
    					return;
    				}
    				
    				var orgCodes = new Array();
    				var value = '';
    				for(var i = 0;i<selections.length;i++){
    					orgCodes.push(selections[i].get('code'));
    					value = value + selections[i].get('name') + ',';
    				}
    				me.getForm().findField('orgCodes').setValue(value);
    				me.getForm().findField('orgCodes').orgCodes = orgCodes;
    				this.close();
    			}
    		});
    		me.orgWindow.items.items[0].getForm().findField('type').getStore().removeAll();
    		me.orgWindow.items.items[0].getForm().findField('type').getStore().add({
				code : 'ORG',
				name : dict.configurationParams.i18n('foss.dict.interiorTissue')//内部组织
			});
    		this.orgWindow.parent = this;
    	}
    	return this.orgWindow;
    },
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		var dataStore = FossDataDictionary.getDataDictionaryStore(config.itemId);
		me.items = [{
			name: 'confType',
			queryMode: 'local',
		    displayField: 'valueName',
		    valueField: 'valueCode',
		    editable:false,
		    readOnly:true,
		    value:config.itemId,
		    store:dict.getStore(null,null,['valueName','valueCode']
		    ,dict.configurationParams.configurationParamsSystem),
	        fieldLabel: dict.configurationParams.i18n('foss.dict.blongSys'),//所属系统
	        xtype : 'combo'
		},{
			allowBlank:false,
			name: 'code',
			queryMode: 'local',
		    displayField: 'valueName',
		    valueField: 'valueCode',
		    editable:false,
		    store:dataStore,
		    xtype:'combo',
	        fieldLabel: dict.configurationParams.i18n('foss.dict.configurationItemCode')//配置项编码
		},{
			name: 'orgCodes',//组织
			colspan : 1,
			width:300,
			orgCodes:[],
			readOnly:true,
	        fieldLabel: dict.configurationParams.i18n('foss.dict.org'),
	        xtype : 'textareafield'
		},{
			colspan : 1,
	        text: dict.configurationParams.i18n('foss.dict.select'),
	        xtype : 'button',
	        handler: function() {
	            me.getOrgWindow().show();
	        }
		},{
			name: 'confName',//名称
			allowBlank:false,
	        fieldLabel: dict.configurationParams.i18n('foss.dict.name'),
	        xtype : 'textfield'
		},{
			allowBlank:false,
			name: 'dataType',
			queryMode: 'local',
		    displayField: 'valueName',
		    valueField: 'valueCode',
		    editable:false,
		    oldFiled:'show',
		    store:dict.getStore(null,null,['valueName','valueCode']
		    ,[{'valueName':dict.configurationParams.i18n('foss.dict.integer'),'valueCode':'integer'}//整型
		    ,{'valueName':dict.configurationParams.i18n('foss.dict.double'),'valueCode':'double'}//小数
		    ,{'valueName':dict.configurationParams.i18n('foss.dict.yesOrNot'),'valueCode':'boolean'}//是否
		    ,{'valueName':dict.configurationParams.i18n('foss.dict.string'),'valueCode':'string'}]),//字符
	        fieldLabel: dict.configurationParams.i18n('foss.dict.type'),//类型
	        xtype : 'combo',
	        listeners:{
	        	change:function(comb,newValue,oldValue){
	        		    if(!Ext.isEmpty(newValue)){
		        			comb.up('form').getForm().findField(newValue).show();
		        			comb.up('form').getForm().findField(comb.oldFiled).hide();
		        			comb.oldFiled = newValue;
	        		    }else{
	        		    	return;
	        		    }

	        	}
	        }
		},{
			xtype: 'displayfield',
			name:'show',
	        fieldLabel: dict.configurationParams.i18n('foss.dict.value'),
	        value: dict.configurationParams.i18n('foss.dict.paleseSelectType')//请先选择类型
		},{
			xtype: 'numberfield',
		    name: 'integer',
		    decimalPrecision:0,
		    fieldLabel: dict.configurationParams.i18n('foss.dict.value'),//值
		   // allowBlank:false,
		    hidden:true,
		    value: 0,
		    maxValue: 99999999,
		    minValue: -99999999
		},{
			xtype: 'numberfield',
		    name: 'double',
		    decimalPrecision:3,
		    step:0.001,
		    fieldLabel: dict.configurationParams.i18n('foss.dict.value'),//值
		    hidden:true,
		  //  allowBlank:false,
		    value: 0,
		    maxValue: 99999999.999,
		    minValue: -99999999.999
		},{
			name: 'boolean',
			queryMode: 'local',
		    displayField: 'valueName',
		    valueField: 'valueCode',
		    editable:false,
		    hidden:true,
		 //   allowBlank:false,
		    value:'Y',
		    store:dict.getStore(null,null,['valueName','valueCode']
		    ,[{'valueName':dict.configurationParams.i18n('foss.dict.yes'),'valueCode':'Y'},{'valueName':dict.configurationParams.i18n('foss.dict.no'),'N':'double'}]),
	        fieldLabel: dict.configurationParams.i18n('foss.dict.value'),//值
	        xtype : 'combo'
		},{
			name: 'string',
			//allowBlank:false,
			hidden:true,
			value:'',
	        fieldLabel: dict.configurationParams.i18n('foss.dict.value'),//值
	        xtype : 'textfield'
		},{
			name: 'unit',
	        fieldLabel: dict.configurationParams.i18n('foss.dict.util'),//单位
	        xtype : 'textfield'
		}];
		me.callParent([cfg]);
	}
});

