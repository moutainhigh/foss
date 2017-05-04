//Ajax请求--json---------------------公共方法---------------------------
management.configOrgRelation.requestJsonAjax = function(url,params,successFn,failFn)
{
	Ext.Ajax.request({
		url:url,
		jsonData:params,
		success:function(response){
			var result = Ext.decode(response.responseText);
			if(result.success){
				successFn(result);
			}
		},
		exception:function(response){
			var result = Ext.decode(response.responseText);
			failFn(result);
		}
	});
};
//错误提示窗口
management.configOrgRelation.showErrorMes = function(message,fun) {
	Ext.Msg.show({
	    title:management.configOrgRelation.i18n('Foss.management.ConfigOrgRelation.infoTitle'),
	    width:200,
	    msg:'<div id="message">'+message+'</div>',
	    buttons: Ext.Msg.OK,
	    icon: Ext.MessageBox.ERROR,
	    callback:function(e){
	    	if(!Ext.isEmpty(fun)){
	    		if(e=='ok'){
		    		fun();
		    	}
	    	}
	    }
	});
	setTimeout(function(){
        Ext.Msg.hide();
    }, 3000);
};
//是否选择窗口
management.configOrgRelation.showQuestionMes = function(message,fun) {
	Ext.Msg.show({
	    title:management.configOrgRelation.i18n('Foss.management.ConfigOrgRelation.infoTitle'),
	    width:200,
	    msg:'<div id="message">'+message+'</div>',
	    buttons: Ext.Msg.YESNO,
	    icon:Ext.MessageBox.QUESTION,
	    callback:function(e){
	    	if(!Ext.isEmpty(fun)){
		    		fun(e);
	    	}
	    }
	});
};
management.configOrgRelation.showInfoMes = function(message,fun) {
	Ext.Msg.show({
	    title:management.configOrgRelation.i18n('Foss.management.ConfigOrgRelation.infoTitle'),
	    width:200,
	    msg:'<div id="message">'+message+'</div>',
	    buttons: Ext.Msg.OK,
	    icon: Ext.MessageBox.INFO,
	    callback:function(e){
	    	if(!Ext.isEmpty(fun)){
	    		if(e=='ok'){
		    		fun();
		    	}
	    	}
	    }
	});
	setTimeout(function(){
        Ext.Msg.hide();
    }, 3000);
};
//----------------------------------------------------------------------业务--------------------------------
//定义：配置项信息model
Ext.define('Foss.management.ConfigOrgRelation.ConfigItemEntity',{
	extend:'Ext.data.Model',
	fields:[{
			name:'id',//id
			type:'string' 
	    },
	    {
	    	name:'confCode',//配置项编码
	    	type:'string'
	    },
	    {
	    	name:'confName',//配置项名称
	    	type:'string'
	    },
	    {
	    	name:'confType',//配置项类型
	    	type:'string'
	    },
	    {
	    	name:'active',//是否有效
	    	type:'string'
	    }
	 ]
});

//定义：查询条件 配置项类型store
Ext.define('Foss.management.ConfigOrgRelation.DistinctConfigTypesStore',{
	extend:'Ext.data.Store',
	fields: [
	 		{name: 'confType',  type: 'confType'},
	 		{name: 'confTypeName',  type: 'confTypeName'}
	 	],
	
		proxy: {
			type: 'memory',
			reader: {
				type: 'json',
				root: 'distinctConfigTypeEntityList'
			}
		},
		constructor: function(config){
			var me = this,
			cfg = Ext.apply({}, config);
			me.callParent([cfg]);
		}
});

//定义：根据配置项类型查询配置项store
Ext.define('Foss.management.ConfigOrgRelation.configItemEntitsByConfigTypeStore',{
	extend:'Ext.data.Store',
	fields: [
	 		{name: 'confCode',  type: 'string'},
	 		{name: 'confName',  type: 'string'}
	 	],
	
		proxy: {
			type: 'memory',
			reader: {
				type: 'json',
				root: 'configItemEntityList'
			}
		},
		constructor: function(config){
			var me = this,
			cfg = Ext.apply({}, config);
			me.callParent([cfg]);
		}
});

management.configOrgRelation.configItemEntitsByConfigTypeStore=Ext.create('Foss.management.ConfigOrgRelation.configItemEntitsByConfigTypeStore');

//定义查询条件
Ext.define('Foss.management.ConfigOrgRelationQueryForm', {
	extend : 'Ext.form.Panel',
	title: management.configOrgRelation.i18n('Foss.management.ConfigOrgRelation.searchCondiction'),//查询条件
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
		me.items = [{
			name: 'confType',
			queryMode: 'local',
		    displayField: 'confTypeName',
		    valueField: 'confType',
		    value:'',
		    store:management.configOrgRelation.DistinctConfigTypesStore,
		    xtype:'combo',
	        fieldLabel: management.configOrgRelation.i18n('Foss.management.ConfigOrgRelation.configuration')//配置项类型
	        
		}
		,{
			xtype : 'dynamicorgcombselector',  
			name: 'orgCode',
			types:'ORG',
	        fieldLabel: management.configOrgRelation.i18n('Foss.management.ConfigOrgRelation.org')//组织
		}
		];
		me.fbar = [{
			xtype : 'button', 
			width:70,
			text : management.configOrgRelation.i18n('Foss.management.ConfigOrgRelation.reset'),//重置
			handler : function() {
				me.getForm().reset();
			}
		},{
			xtype : 'button', 
			width:70,
			cls:'yellow_button',
			margin:'0 0 0 750',
			text : management.configOrgRelation.i18n('Foss.management.ConfigOrgRelation.search'),//查询
			disabled: !management.configOrgRelation.isPermission('management/queryConfigOrgRelationEntitiesButton'),
			hidden: !management.configOrgRelation.isPermission('management/queryConfigOrgRelationEntitiesButton'),
			handler : function() {
				if(me.getForm().isValid()){
					management.configOrgRelation.configOrgRelationGridPanel.getPagingToolbar().moveFirst();
				}
			}
		}];
		me.callParent([cfg]);
	}
});

//--------------------------------------------------------------------查询结果---------------------------------------------------
//定义查询结果对象模型
Ext.define('Foss.management.ConfigOrgRelation.ConfigOrgRelationEntity',{
	extend:'Ext.data.Model',
	fields:[{
			name:'id',//id
			type:'string'
		},
	    {
	    	name:'confCode',//配置项编码
	    	type:'string'
	    },
	    {
	    	name:'confName',//配置项名称
	    	type:'string'
	    },
	    {
	    	name:'orgCode',//组织编码
	    	type:'string'
	    },
	    {
	    	name:'orgName',//组织名称
	    	type:'string'
	    },
	    {
	    	name:'confType', //配置项类型编码
	    	type:'string'
	    },
	    {
	    	name:'confTypeName',//配置项类型名
	    	type:'string'
	    }
	]
});
//定义查询结果实体store
Ext.define('Foss.management.ConfigOrgRelation.ConfigOrgRelationStore',{
	extend:'Ext.data.Store',
    model:'Foss.management.ConfigOrgRelation.ConfigOrgRelationEntity',
	pageSize:20,
	proxy:{
		type:'ajax',
		actionMethods:'post',
		url:management.realPath('queryConfigOrgRelationEntities.action'),
		reader:{
			type:'json',
			root:'configOrgRelationVo.configOrgRelationEntityList',
			totalProperty:'totalCount'
		}
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	},
	listeners : {
		beforeload : function(store, operation, eOpts) {
			Ext.apply(operation, {
				params : {
					'configOrgRelationVo.configOrgRelationEntity.confType' : management.configOrgRelation.queryForm.getForm().findField('confType').getValue(),
					'configOrgRelationVo.configOrgRelationEntity.orgCode' :management.configOrgRelation.queryForm.getForm().findField('orgCode').getValue()
				}
			});
		}
	}
});
//定义查询结果表格
Ext.define('Foss.management.ConfigOrgRelationGridPanel',{
	extend:'Ext.grid.Panel',
	title:management.configOrgRelation.i18n('Foss.management.ConfigOrgRelation.searchResult'),//查询结果
	frame:true,
	columnLines: true,
	sortableColumns:true,
	enableColumnHide:false,
	enableColumnMove:false,
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	selType:'rowmodel',
	pagingToolbar: null,
	getPagingToolbar: function() {
		if (this.pagingToolbar == null) {
			this.pagingToolbar = Ext.create('Deppon.StandardPaging', {
				store: this.store,
				pageSize : 20,
				plugins: Ext.create('Deppon.ux.PageSizePlugin', { 
                limitWarning: 200    })
			});
		}
		return this.pagingToolbar;
	},
	
	configOrgRelationAddWindow: null,//增加窗口
	getConfigOrgRelationAddWindow: function(){
		if(this.configOrgRelationAddWindow==null){
			this.configOrgRelationAddWindow = Ext.create('Foss.management.ConfigOrgRelationAddWindow');
			this.configOrgRelationAddWindow.parent = this;
		}
		return this.configOrgRelationAddWindow;
	},

	configOrgRelationUpdateWindow:null,//修改窗口
	getConfigOrgRelationUpdateWindow:function(){
		if(this.configOrgRelationUpdateWindow==null){
			this.configOrgRelationUpdateWindow=Ext.create('Foss.management.ConfigOrgRelationUpdateWindow');
			this.configOrgRelationUpdateWindow.parent=this;
		}
		return this.configOrgRelationUpdateWindow;
	},
	//作废组织配置项信息
	abolishItem: function(btn){
		var me = this;
		//选中数据
		var selections = me.getSelectionModel().getSelection();
		if(selections.length<1){//未选择
			management.configOrgRelation.showErrorMes(management.configOrgRelation.i18n('Foss.management.ConfigOrgRelation.selectNoAbolishItem')); //未选择要作废的配置项!
			return;
		}
		management.configOrgRelation.showQuestionMes(management.configOrgRelation.i18n('Foss.management.ConfigOrgRelation.yesOrNotBolishItem'),function(e){//您确认要作废这些配置项信息?
			if(e=='yes'){//确认作废
				var idList = new Array();
				for(var i = 0 ; i<selections.length ; i++){
					idList.push(selections[i].get('id')); //将要作废的配置参数保存到列表中
				}
				var params = {'configOrgRelationVo':{'idList':idList}};
				var successFun = function(json){
					me.getPagingToolbar().moveFirst();
				};
				var failureFun = function(json){
					if(Ext.isEmpty(json)){
						management.configOrgRelation.showErrorMes(management.configOrgRelation.i18n('Foss.management.ConfigOrgRelation.requestTimeout'));//请求超时
					}else{
						management.configOrgRelation.showErrorMes(json.message);
					}
				};
				var url = management.realPath('abolishConfigOrgRelation.action');
				management.configOrgRelation.requestJsonAjax(url,params,successFun,failureFun);
			}
		})
	},
	
	columns : [{xtype: 'rownumberer',
		width:40,
		text : management.configOrgRelation.i18n('Foss.management.ConfigOrgRelation.num')//序号
	},
	{
		text : management.configOrgRelation.i18n('Foss.management.ConfigOrgRelation.op'),//操作
		align : 'center',
		xtype : 'actioncolumn',
		items: [{
					iconCls: 'deppon_icons_edit',
	                tooltip: management.configOrgRelation.i18n('Foss.management.ConfigOrgRelation.update'),//修改
					width:42,
					handler: function(grid, rowIndex, colIndex) {
		                	//获取选中的数据
		    				var record=grid.getStore().getAt(rowIndex);
		                	var id = record.get('id');//词条代码
		    				var params = {'configOrgRelationVo':{'configOrgRelationEntity':{'id':id}}};
		    				var successFun = function(json){
		    					var configOrgRelationUpdateWindow = management.configOrgRelation.configOrgRelationGridPanel.getConfigOrgRelationUpdateWindow();//获得修改窗口
		    					configOrgRelationUpdateWindow.getConfigOrgRelationParamsForm().getForm().findField('confType').setValue(json.configOrgRelationVo.configOrgRelationEntity.confType);
		    					configOrgRelationUpdateWindow.getConfigOrgRelationParamsForm().getForm().findField('confType').setRawValue(json.configOrgRelationVo.configOrgRelationEntity.confTypeName);
		    					configOrgRelationUpdateWindow.getConfigOrgRelationParamsForm().getForm().findField('confCode').setValue(json.configOrgRelationVo.configOrgRelationEntity.confCode);
		    					configOrgRelationUpdateWindow.getConfigOrgRelationParamsForm().getForm().findField('confCode').setRawValue(json.configOrgRelationVo.configOrgRelationEntity.confName);
		    					configOrgRelationUpdateWindow.getConfigOrgRelationParamsForm().getForm().findField('orgCodes').setValue(json.configOrgRelationVo.configOrgRelationEntity.orgName);
		    					configOrgRelationUpdateWindow.getConfigOrgRelationParamsForm().getForm().findField('orgCodes').orgCodes=json.configOrgRelationVo.configOrgRelationEntity.orgCode;
		    					configOrgRelationUpdateWindow.getConfigOrgRelationParamsForm().getForm().findField('id').setValue(json.configOrgRelationVo.configOrgRelationEntity.id);
		    					publicConfigOrgRelationEntity=json.configOrgRelationVo.configOrgRelationEntity;
		    					configOrgRelationUpdateWindow.show();//显示修改窗口
		    				};
		    				var failureFun = function(json){
		    					if(Ext.isEmpty(json)){
		    						management.configOrgRelation.showErrorMes(management.configurationParams.i18n('Foss.management.ConfigOrgRelation.requestTimeout'));//请求超时
		    					}else{
		    						management.configOrgRelation.showErrorMes(json.message);
		    					}
		    				};
		    				var url = management.realPath('queryConfigOrgRelationEntity.action');
		    				management.configOrgRelation.requestJsonAjax(url,params,successFun,failureFun);
		                }
				},
				{
					iconCls: 'deppon_icons_cancel',
	                tooltip: management.configOrgRelation.i18n('Foss.management.ConfigOrgRelation.void'),//作废
					width:42,
					handler:function(grid, rowIndex, colIndex) {
						management.configOrgRelation.showQuestionMes(management.configOrgRelation.i18n('Foss.management.ConfigOrgRelation.yesOrNotBolishItem'),function(e){
							if(e=='yes'){
	            				var idList = new Array();
	            				//获取选中的数据
	            				var record=grid.getStore().getAt(rowIndex);
	            				idList.push(record.get('id')); //
	            				var params = {'configOrgRelationVo':{'idList':idList}};
	            				var successFun = function(json){
	            					management.configOrgRelation.configOrgRelationGridPanel.getPagingToolbar().moveFirst()
	            				};
	            				var failureFun = function(json){
	            					if(Ext.isEmpty(json)){
	            						management.configOrgRelation.showErrorMes(management.configurationParams.i18n('Foss.management.ConfigOrgRelation.requestTimeout'));//请求超时
	            					}else{
	            						management.configOrgRelation.showErrorMes(json.message);
	            					}
	            				};
	            				var url = management.realPath('abolishConfigOrgRelation.action');
	            				management.configOrgRelation.requestJsonAjax(url,params,successFun,failureFun);
							}
						})
					}
				}]
	},
	{
		text : management.configOrgRelation.i18n('Foss.management.ConfigOrgRelation.confCode'),//配置项编码
		width:120,
		dataIndex : 'confCode',
	},
	{
		text : management.configOrgRelation.i18n('Foss.management.ConfigOrgRelation.confName'),//配置项名称
		width:120,
		dataIndex : 'confName',
	},
	{
		text : management.configOrgRelation.i18n('Foss.management.ConfigOrgRelation.orgCode'),//组织编码
		width:120,
		dataIndex : 'orgCode',
	},
	{
		text : management.configOrgRelation.i18n('Foss.management.ConfigOrgRelation.orgName'),//组织名称
		width:120,
		dataIndex : 'orgName',
	},
	{
		text : management.configOrgRelation.i18n('Foss.management.ConfigOrgRelation.confTypeName'),//配置项类型名称
		width:120,
		dataIndex : 'confTypeName'
	}],
	
	constructor : function(config) {
		var me = this, 
		cfg = Ext.apply({}, config);
		me.store=Ext.create('Foss.management.ConfigOrgRelation.ConfigOrgRelationStore');
		me.listeners = {
		    	scrollershow: function(scroller) {
		    		if (scroller && scroller.scrollEl) {
		    				scroller.clearManagedListeners(); 
		    				scroller.mon(scroller.scrollEl, 'scroll', scroller.onElScroll, scroller); 
		    		}
		    	}
		    },
		//多选框
		me.selModel=Ext.create('Ext.selection.CheckboxModel',{//多选框
			mode:'MULTI',
			checkOnly:true
		});
		//作废与新增按钮
		me.tbar = [{
				text : management.configOrgRelation.i18n('Foss.management.ConfigOrgRelation.void'),//作废
				width:80,
				disabled: !management.configOrgRelation.isPermission('management/abolishConfigOrgRelationButton'),
				hidden: !management.configOrgRelation.isPermission('management/abolishConfigOrgRelationButton'),
				handler:function(){
					me.abolishItem();//作废方法
				}
			},
			{
				text : management.configOrgRelation.i18n('Foss.management.ConfigOrgRelation.add'),//新增
				width:80,
				disabled: !management.configOrgRelation.isPermission('management/addConfigOrgRelationListButton'),
				hidden: !management.configOrgRelation.isPermission('management/addConfigOrgRelationListButton'),
				handler:function(){
					me.getConfigOrgRelationAddWindow().show(); //显示新增窗口
				}
			} 
		];

		//设置最下分页工具栏
		me.bbar = me.getPagingToolbar();
		me.callParent([cfg]);	
	}
});

//------------------------------------------------------------------------如下弹出窗口---------------------------------------------
//参数配置信息表单
Ext.define('Foss.management.ConfigOrgRelationParamsForm', {
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
    			types:'ORG',
    			active:'Y',
    			modal:true,
    			commitFun:function(){
    				var selections = this.getGridRecord();
    				
    				if(selections.length<1){
    					management.configOrgRelation.showErrorMes(management.configOrgRelation.i18n('Foss.management.ConfigOrgRelation.selectNoOne'));//请至少选择一条数据！
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
				name : management.configOrgRelation.i18n('Foss.management.ConfigOrgRelation.interiorTissue')//内部组织
			});
    		this.orgWindow.parent = this;
    	}
    	return this.orgWindow;
    },
    constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.items = [{
			name: 'confType',
			allowBlank:false,
			queryMode: 'local',
		    displayField: 'confTypeName',
		    valueField: 'confType',
		    editable:false,
		    store:management.configOrgRelation.DistinctConfigTypesStore,
	        xtype : 'combo',
	        fieldLabel: management.configOrgRelation.i18n('Foss.management.ConfigOrgRelation.confType'),//配置项类型
	        listeners:{
	        	select:{
	        		fn:function(combo,record,index){
	        			Ext.Ajax.request({
	        				url:management.realPath('queryConfigItemEntitsByConfigType.action'),
	        				params : {'configOrgRelationVo.configItemEntity.confType' : combo.getValue()},
	        				success:function(response){
	        					var result = Ext.decode(response.responseText);
	        					if(result.success){
	        						management.configOrgRelation.configItemEntitsByConfigTypeStore.loadData(result.configOrgRelationVo.configItemEntityList);
	        					}
	        				},
	        				exception:function(response){
	        					var result = Ext.decode(response.responseText);
	        					if(Ext.isEmpty(json)){
	        						management.configOrgRelation.showErrorMes(management.configOrgRelation.i18n('Foss.management.ConfigOrgRelation.requestTimeout'));//请求超时
	        					}else{
	        						management.configOrgRelation.showErrorMes(management.configOrgRelation.i18n('Foss.management.ConfigOrgRelation.confCodeFail')+json.message);
	        					}
	        				}
	        			});
	        		}
	        	}
	        }
	        	
		},{
			name: 'confCode',
			allowBlank:false,
			queryMode: 'local',
		    displayField: 'confName',
		    valueField: 'confCode',
		    editable:false,
		    store:management.configOrgRelation.configItemEntitsByConfigTypeStore,
		    xtype:'combo',
	        fieldLabel: management.configOrgRelation.i18n('Foss.management.ConfigOrgRelation.confCode')//配置项编码
		},{
			name: 'orgCodes',//组织
			colspan : 1,
			width:300,
			orgCodes:[],
			readOnly:true,
	        fieldLabel: management.configOrgRelation.i18n('Foss.management.ConfigOrgRelation.org'), //组织
	        xtype : 'textareafield'
		},{
			colspan : 1,
	        text: management.configOrgRelation.i18n('Foss.management.ConfigOrgRelation.select'),  //选择
	        xtype : 'button',
	        handler: function() {
	            me.getOrgWindow().show();
	        }
		},{
			xtype: "hiddenfield",
			name:'id'//id
		}];
		me.callParent([cfg]);
	}
});


//---------------------------------------------------------增加按钮(跳出新窗口)-----------------------------------
//定义新增按钮对应的配置参数窗口
Ext.define('Foss.management.ConfigOrgRelationAddWindow',{
	extend:'Ext.window.Window',
	title:management.configOrgRelation.i18n('Foss.management.ConfigOrgRelation.addFormTitle'),
	parent:null,
	closable:true,
	modal:true,
	closeAction:'hide',
	width:450,
	height:460,
	//新增配置型具体信息
	configOrgRelationParamsForm:null,
	getConfigOrgRelationParamsForm:function(){
		if(this.configOrgRelationParamsForm==null){
			this.configOrgRelationParamsForm=Ext.create('Foss.management.ConfigOrgRelationParamsForm');
			this.configOrgRelationParamsForm.parent=this;
		}
		return this.configOrgRelationParamsForm;
	},
	
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.fbar = [{
				text : management.configOrgRelation.i18n('Foss.management.ConfigOrgRelation.cancel'),//取消
				handler :function(){
					me.getConfigOrgRelationParamsForm().getForm().reset();
					me.close();
				}
			},
			{
				text : management.configOrgRelation.i18n('Foss.management.ConfigOrgRelation.reset'),//重置
				handler:function(){
					me.getConfigOrgRelationParamsForm().getForm().reset();
				}
			},
			{
				text : management.configOrgRelation.i18n('Foss.management.ConfigOrgRelation.save'),//保存
				cls:'yellow_button',
				margin:'0 0 0 180',
				handler:function(){
			    	var confType=me.getConfigOrgRelationParamsForm().getForm().findField('confType').getValue();
			    	//检验配置参数类型
			    	if(confType==null||confType==''){
			    		management.configOrgRelation.showErrorMes(management.configOrgRelation.i18n('Foss.management.ConfigOrgRelation.confTypeNull'));
						return;
			    	}
			    	
			    	//配置参数类型名称
			    	var confTypeName=me.getConfigOrgRelationParamsForm().getForm().findField('confType').getRawValue();
			    	if(confTypeName==null||confTypeName==''){
			    		management.configOrgRelation.showErrorMes(management.configOrgRelation.i18n('Foss.management.ConfigOrgRelation.confTypeNameNull'));
						return;
			    	}
			    	
			    	var confCode=me.getConfigOrgRelationParamsForm().getForm().findField('confCode').getValue();
			    	//检验配置项编码
			    	if(confCode==null||confCode==''){
			    		management.configOrgRelation.showErrorMes(management.configOrgRelation.i18n('Foss.management.ConfigOrgRelation.confCodeNull'));
						return;
			    	}
			    	
			    	//配置项编码名称
			    	var confName=me.getConfigOrgRelationParamsForm().getForm().findField('confCode').getRawValue();
			    	if(confName==null||confName==''){
			    		management.configOrgRelation.showErrorMes(management.configOrgRelation.i18n('Foss.management.ConfigOrgRelation.confNameNull'));
						return;
			    	}
			    	
					var orgCodes = me.getConfigOrgRelationParamsForm().getForm().findField('orgCodes').orgCodes;
					//检验是否选择部门
					if(orgCodes==null||orgCodes.length<1){
						management.configOrgRelation.showErrorMes(management.configOrgRelation.i18n('Foss.management.ConfigOrgRelation.orgCodeNull'));
						return;
					}
			    	
					var orgCodeNames= me.getConfigOrgRelationParamsForm().getForm().findField('orgCodes').getValue().split(',');
			    	var configOrgRelationEntityList = new Array();
			    	
		    	   for(var i = 0;i<orgCodes.length;i++){
			    		var model = Ext.create('Foss.management.ConfigOrgRelation.ConfigOrgRelationEntity');
			    		model.set('orgCode',orgCodes[i]);
			    		model.set('orgName',orgCodeNames[i]);
			    		model.set('confCode',confCode);
			    		model.set('confName',confName);
			    		model.set('confType',confType);
			    		model.set('confTypeName',confTypeName);
			    		configOrgRelationEntityList.push(model.data);
			    	}
			    	
			    	var params = {configOrgRelationVo:{'configOrgRelationEntityList':configOrgRelationEntityList}};//保存参数
			    	//保存
			    	Ext.Ajax.request({
			    		jsonData:params,
			    		url:management.realPath('addConfigOrgRelationList.action'),
			    		success:function(response){//成功
			    			me.getConfigOrgRelationParamsForm().getForm().reset();
		    				me.close();
		    				management.configOrgRelation.configOrgRelationGridPanel.getPagingToolbar().moveFirst();
			    		},
			    		exception:function(response){//失败
			    			var json = Ext.decode(response.responseText);
			    			if(Ext.isEmpty(json)){
			    				Ext.ux.Toast.msg(management.configOrgRelation.i18n('Foss.management.ConfigOrgRelation.requestTimeout'), json.message, 'error');//请求超时
			    			}
			    			else{
			    				Ext.ux.Toast.msg(management.configOrgRelation.i18n('Foss.management.ConfigOrgRelation.addFail'), json.message, 'error');
			    			}
			    		}
			    	});
			    	
				}
			}];
		me.items = [me.getConfigOrgRelationParamsForm()];
		me.callParent([cfg]);
	},
	//modify by liangfuxiang 2013-04-25 BUG-8011 begin
	listeners:{
		beforeshow:function(me){//设置数据
			me.getConfigOrgRelationParamsForm().getForm().reset();
	    }
	}
	//modify by liangfuxiang 2013-04-25 BUG-8011 end
});

//---------------------------------------------------------修改按钮(跳出新窗口)-----------------------------------
//修改配置参数窗口
Ext.define('Foss.management.ConfigOrgRelationUpdateWindow',{
	extend:'Ext.window.Window',
	title:management.configOrgRelation.i18n('Foss.management.ConfigOrgRelation.updateFormTitle'),
	parent:null,
	closable : true,
	modal : true,
	resizable:false,
	closeAction : 'hide',
	configOrgRelationEntity:null,
	width :450,
	height :460,
	listeners:{
		beforehide:function(me){
			me.getConfigOrgRelationParamsForm().getForm().reset();//表格重置
		}
	},
	//修改参数配置FORM
	configOrgRelationParamsForm:null,
    getConfigOrgRelationParamsForm : function(){
    	if(Ext.isEmpty(this.configOrgRelationParamsForm)){
    		this.configOrgRelationParamsForm=Ext.create('Foss.management.ConfigOrgRelationParamsForm');
    		this.configOrgRelationParamsForm.items.items[3].setDisabled(true);
    	}
    	return this.configOrgRelationParamsForm;
    },
    
    //保存
    commitConfigurationParams:function(){
    	var me = this;
    	var form = me.getConfigOrgRelationParamsForm().getForm();
    	
    	//配置参数ID
    	var id=form.findField('id').getValue();
    	if(id==null || id==''){
    		management.configOrgRelation.showErrorMes(management.configOrgRelation.i18n('Foss.management.ConfigOrgRelation.idNull'));
			return;
    	}

    		var confType=form.findField('confType').getValue();
    	//检验配置参数类型
    	if(confType==null||confType==''){
    		management.configOrgRelation.showErrorMes(management.configOrgRelation.i18n('Foss.management.ConfigOrgRelation.confTypeNull'));
			return;
    	}
    	
    	//配置参数类型名称
    	var confTypeName=form.findField('confType').getRawValue();
    	if(confTypeName==null||confTypeName==''){
    		management.configOrgRelation.showErrorMes(management.configOrgRelation.i18n('Foss.management.ConfigOrgRelation.confTypeNameNull'));
			return;
    	}
    	
    	var confCode=form.findField('confCode').getValue();
    	//检验配置项编码
    	if(confCode==null||confCode==''){
    		management.configOrgRelation.showErrorMes(management.configOrgRelation.i18n('Foss.management.ConfigOrgRelation.confCodeNull'));
			return;
    	}
    	
    	//配置项编码名称
    	var confName=form.findField('confCode').getRawValue();
    	if(confName==null||confName==''){
    		management.configOrgRelation.showErrorMes(management.configOrgRelation.i18n('Foss.management.ConfigOrgRelation.confNameNull'));
			return;
    	}
    	
		var orgCodes = form.findField('orgCodes').orgCodes;
		//检验是否选择部门
		if(orgCodes==null||orgCodes==''){
			management.configOrgRelation.showErrorMes(management.configOrgRelation.i18n('Foss.management.ConfigOrgRelation.orgCodeNull'));
			return;
		}

    	var orgCodeName= form.findField('orgCodes').getValue();
    	var model = Ext.create('Foss.management.ConfigOrgRelation.ConfigOrgRelationEntity');
    	model.set('orgCode',orgCodes);
    	model.set('orgName',orgCodeName);
    	model.set('confCode',confCode);
    	model.set('confName',confName);
    	model.set('confType',confType);
    	model.set('confTypeName',confTypeName);
    	model.set('id',id);
    	var params = {configOrgRelationVo:{'configOrgRelationEntity':model.data}};//保存参数

    	Ext.Ajax.request({
    		jsonData:params,
    		url:management.realPath('modifyConfigOrgRelation.action'),
    		success:function(response){//成功
				me.close();
				management.configOrgRelation.configOrgRelationGridPanel.getPagingToolbar().moveFirst();
    		},
    		exception:function(response){//失败
    			var json = Ext.decode(response.responseText);
    			if(Ext.isEmpty(json)){
    				Ext.ux.Toast.msg(management.configOrgRelation.i18n('Foss.management.ConfigOrgRelation.requestTimeout'), json.message, 'error');//请求超时
    			}
    			else{
    				Ext.ux.Toast.msg(management.configOrgRelation.i18n('Foss.management.ConfigOrgRelation.modifyFail'), json.message, 'error');
    			}
    		}
    	});
		
    },
    //重置，重新加载数据
    loadData:function(me){
    	var me = this;
    	var data=publicConfigOrgRelationEntity;
    	var form = me.getConfigOrgRelationParamsForm().getForm();
    	form.loadRecord(data);//加载配置参数数据
	   	form.findField('confType').setValue(data.confType);
	   	form.findField('confType').setRawValue(data.confTypeName);
    	form.findField('confCode').setValue(data.confCode);
    	form.findField('confCode').setRawValue(data.confName);
    	form.findField('id').setValue(data.id);
    	var orgCodes = new Array();
    	orgCodes.push(data.orgCode);
    	form.findField('orgCodes').orgCodes = orgCodes;
    	form.findField('orgCodes').setValue(data.orgName);
    },
    
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.fbar = [{
				text : management.configOrgRelation.i18n('Foss.management.ConfigOrgRelation.cancel'),//取消
				handler :function(){
					me.close();
				}
			},
			{
				text : management.configOrgRelation.i18n('Foss.management.ConfigOrgRelation.reset'),//重置
				handler:function(){
					me.loadData(me);
				}
			},
			{
				text : management.configOrgRelation.i18n('Foss.management.ConfigOrgRelation.save'),//保存
				cls:'yellow_button',
				margin:'0 0 0 180',
				handler:function(){
					me.commitConfigurationParams();
				}
			}];
		me.items = [me.getConfigOrgRelationParamsForm()];
		me.callParent([cfg]);
	}
});

//-------------------------------------------------------------------页面加载初始化--------------------------------------------
Ext.onReady(function(){
	
	Ext.QuickTips.init();
	
	//modify by liangfuxiang 2012-04-25 BUG-7996
	//公共变量，用于保存重置的初始化信息
	publicConfigOrgRelationEntity=null;	
	
	//--------------------获取配置项类型------------------------------------
	management.configOrgRelation.DistinctConfigTypesStore=Ext.create('Foss.management.ConfigOrgRelation.DistinctConfigTypesStore');
	
	Ext.Ajax.request({
		url:management.realPath('queryDistinctConfigTypes.action'),
		success:function(response){
			var result = Ext.decode(response.responseText);
			if(result.success){
				management.configOrgRelation.DistinctConfigTypesStore.loadData(result.configOrgRelationVo.distinctConfigTypeEntityList);
			}
		},
		exception:function(response){
			var result = Ext.decode(response.responseText);
			if(Ext.isEmpty(json)){
				management.configOrgRelation.showErrorMes(management.configOrgRelation.i18n('Foss.management.ConfigOrgRelation.requestTimeout'));//请求超时
			}else{
				management.configOrgRelation.showErrorMes(management.configOrgRelation.i18n('Foss.management.ConfigOrgRelation.confTypeFail')+json.message);
			}
		}
	});
	//创建查询条件
	var configOrgRelationQueryForm=Ext.create('Foss.management.ConfigOrgRelationQueryForm');
	management.configOrgRelation.queryForm=configOrgRelationQueryForm;
	var configOrgRelationGridPanel=Ext.create('Foss.management.ConfigOrgRelationGridPanel');
	management.configOrgRelation.configOrgRelationGridPanel = configOrgRelationGridPanel;
	Ext.create('Ext.panel.Panel',{
		id: 'T_management-configOrgRelation_content',
		cls: "panelContentNToolbar",
		bodyCls: 'panelContentNToolbar-body',
		layout: 'auto',	
		items: [configOrgRelationQueryForm,configOrgRelationGridPanel],
		renderTo: 'T_management-configOrgRelation-body'
	});
});
