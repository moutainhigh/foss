/**
 * 禁运物品model									Foss.baseinfo.prohibitedArticlesIndex.ProhibitedArticlesModel
 * 禁运物品store									Foss.baseinfo.prohibitedArticlesIndex.ProhibitedArticlesStore
 * 禁运物品form									Foss.baseinfo.prohibitedArticlesIndex.QueryConditionForm
 * 禁运物品grid									Foss.baseinfo.prohibitedArticlesIndex.QueryResultGrid
 * 禁运物品winForm								Foss.baseinfo.prohibitedArticlesIndex.ProhibitedArticlesWinForm
 * 禁运物品winGrid								Foss.baseinfo.prohibitedArticlesIndex.ProhibitedArticlesWinGrid
 * 禁运物品win									Foss.baseinfo.prohibitedArticlesIndex.ProhibitedArticlesWin
 */
//------------------------------------常量和公用方法----------------------------------
//信息
baseinfo.showInfoMsg = function(message,fun) {
	var len = message.length;
	Ext.Msg.show({
	    title:baseinfo.prohibitedArticlesIndex.i18n('i18n.baseinfo-util.fossAlert'),
	    width:110+len*15,
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
//保存事件 
baseinfo.prohibitedArticlesIndex.submitProhibitedArticles = function(win,viewState,operatEntity){
	var grid = Ext.getCmp('T_baseinfo-prohibitedArticlesIndex_content').getQueryGrid()
		,url = baseinfo.realPath('addProhibitedArticles.action')
		,m_success = baseinfo.prohibitedArticlesIndex.i18n('foss.baseinfo.saveSuccess')								//保存成功！
		,m_failure = baseinfo.prohibitedArticlesIndex.i18n('foss.baseinfo.airagencycompany.saveFail')								//保存失败！
		,m_dateError = baseinfo.prohibitedArticlesIndex.i18n('foss.baseinfo.airagencycompany.dataError')								//数据异常！
		,objectVo = {};
	objectVo.prohibitedArticlesEntity = operatEntity;
	if(baseinfo.viewState.add === viewState){
		//新增URL(已经有)
	}else if(baseinfo.viewState.update === viewState){
		//修改URL
		url = baseinfo.realPath('updateProhibitedArticles.action');
	}
	baseinfo.requestAjaxJson(url,{'objectVo':objectVo},function(result){
		if(!Ext.isEmpty(result.objectVo.prohibitedArticlesEntity)){
			grid.store.loadPage(1);
			//TODO 保存按钮 变为可用
			baseinfo.showInfoMsg(m_success);
			win.hide();
		}else{
			baseinfo.showErrorMes(result.message);
		}
	},function(result){
		baseinfo.showErrorMes(result.message);
	});
};
//作废事件
baseinfo.prohibitedArticlesIndex.deleteProhibitedArticlesByCode = function(delAgencyCompanyType,operatRecord){
	var grid = Ext.getCmp('T_baseinfo-prohibitedArticlesIndex_content').getQueryGrid(),
		url = baseinfo.realPath('deleteProhibitedArticles.action'),
		objectVo = {};
	selection=grid.getSelectionModel().getSelection();
	if(selection.length<=0 && Ext.isEmpty(operatRecord)){
		Ext.MessageBox.alert(baseinfo.prohibitedArticlesIndex.i18n('foss.baseinfo.airagencycompany.remind'),baseinfo.prohibitedArticlesIndex.i18n('foss.baseinfo.airagencycompany.selectData'));
	}else{	
		if(!Ext.isEmpty(delAgencyCompanyType)&&baseinfo.delAgencyType===delAgencyCompanyType){
			var codeStr = [];
			//批量作废
			url = baseinfo.realPath('deleteProhibitedArticlesMore.action');
			for (var j = 0; j < selection.length; j++) {
				codeStr.push(selection[j].get('virtualCode'));
			}
			objectVo.codeStr = codeStr;
		}else{
			objectVo.prohibitedArticlesEntity = operatRecord.data;
		}
		Ext.MessageBox.buttonText.yes = baseinfo.prohibitedArticlesIndex.i18n('foss.baseinfo.sure');
		Ext.MessageBox.buttonText.no = baseinfo.prohibitedArticlesIndex.i18n('foss.baseinfo.cancel');
		Ext.Msg.confirm(baseinfo.prohibitedArticlesIndex.i18n('foss.baseinfo.billAdvertisingSlogan.fossAlertU'),baseinfo.prohibitedArticlesIndex.i18n('foss.baseinfo.billAdvertisingSlogan.confirmAlertRecord'),function(btn,text) {
			if (btn == 'yes') {
				baseinfo.requestAjaxJson(url,{'objectVo':objectVo},function(result){
					grid.store.loadPage(1);
					baseinfo.showInfoMsg(baseinfo.prohibitedArticlesIndex.i18n('foss.baseinfo.deleteSuccess'));
				},function(result){
					baseinfo.showErrorMes(result.message);
				});
			}
		});
	}
};
baseinfo.prohibitedArticlesIndex.prohibitedArticlesIsExist = function(field,fieldValue,fieldLabel,fieldNmae){
	var url = baseinfo.realPath('prohibitedArticlesIsExist.action'),objectVo ={}
	,entytyRecord = Ext.create('Foss.baseinfo.prohibitedArticlesIndex.ProhibitedArticlesModel');
	entytyRecord.set(fieldNmae+'',fieldValue);
	objectVo.prohibitedArticlesEntity = entytyRecord.data;
	baseinfo.requestAjaxJson(url,{'objectVo':objectVo},function(result){
		if(Ext.isEmpty(result.objectVo.prohibitedArticlesEntity)){
			field.clearInvalid();
		}else{
			field.markInvalid(baseinfo.prohibitedArticlesIndex.i18n('foss.baseinfo.airagencycompany.dataRepeatBegin')+fieldLabel+baseinfo.prohibitedArticlesIndex.i18n('foss.baseinfo.airagencycompany.dataRepeatEnd'));
		}
	},function(result){
		field.markInvalid(baseinfo.prohibitedArticlesIndex.i18n('foss.baseinfo.airagencycompany.dataRepeatBegin')+fieldLabel+baseinfo.prohibitedArticlesIndex.i18n('foss.baseinfo.airagencycompany.dataRepeatEnd'));
	});
};

//------------------------------------MODEL----------------------------------
//禁运物品Model
Ext.define('Foss.baseinfo.prohibitedArticlesIndex.ProhibitedArticlesModel', {
extend: 'Ext.data.Model',
fields : [//虚拟编码
	  {name:'virtualCode',type:'string'},
	  //违禁品名称
	  {name:'goodsName',type:'string'},
	  //违禁品类型
	  {name:'goodsType',type:'string'},
	  //违禁品级别
	  {name:'goodsLevel',type:'string'},
	  //违禁品类别
	  {name:'goodsSort',type:'string'},
	  //备注
	  {name:'notes',type:'string'},
	  //是否启用
	  {name:'active',type:'string'},
	  //关键字
	  {name:'keyWords',type:'string'}
	  ]
});
//------------------------------------STORE----------------------------------
//禁运物品STORE
Ext.define('Foss.baseinfo.prohibitedArticlesIndex.ProhibitedArticlesStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.baseinfo.prohibitedArticlesIndex.ProhibitedArticlesModel',
	pageSize:20,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url : baseinfo.realPath('queryProhibitedArticlesExactByEntity.action'),
		reader : {
			type : 'json',
			root : 'objectVo.prohibitedArticlesEntityList',
			totalProperty : 'totalCount'
		}
	}
});
//------------------------------------FORM----------------------------------
//禁运物品 查询条件
Ext.define('Foss.baseinfo.prohibitedArticlesIndex.QueryConditionForm', {
	extend : 'Ext.form.Panel',
	title: baseinfo.prohibitedArticlesIndex.i18n('foss.baseinfo.queryCondition'),
	frame: true,
	collapsible: true,
    defaults : {
    	//labelSeparator:'',
    	labelWidth:100
    },
    height :140,
	defaultType : 'textfield',
	layout:{
        type: 'table',
        columns: 4
    },
    record:null,												//绑定的model Foss.baseinfo.prohibitedArticlesIndex.ProhibitedArticlesModel
	constructor : function(config) {							//构造器
		var me = this,cfg = Ext.apply({}, config);
		me.items = me.getItems();
		me.callParent([cfg]);
		me.loadRecord(Ext.isEmpty(me.record)?Ext.create('Foss.baseinfo.prohibitedArticlesIndex.ProhibitedArticlesModel'):me.record);
	},
	getItems:function(){
		var me = this;
		return [{
			fieldLabel:baseinfo.prohibitedArticlesIndex.i18n('foss.baseinfo.prohibitedarticles.prohibitedName'),							//违禁品名称
			colspan:1,
			name:'goodsName'
		},FossDataDictionary.getDataDictionaryCombo('CONTRABAND_TYPE',{
			fieldLabel:baseinfo.prohibitedArticlesIndex.i18n('foss.baseinfo.prohibitedarticles.prohibitedType'),								//违禁品类型
			colspan:1,
			name: 'goodsType'
		}),FossDataDictionary.getDataDictionaryCombo('CONTRABAND_LEVEL',{
			fieldLabel:baseinfo.prohibitedArticlesIndex.i18n('foss.baseinfo.prohibitedarticles.prohibitedLevel'),								//违禁品级别
			colspan:1,
			name: 'goodsLevel'
		}),FossDataDictionary.getDataDictionaryCombo('CONTRABAND_GOODS_CATEGORY',{
			fieldLabel:baseinfo.prohibitedArticlesIndex.i18n('foss.baseinfo.prohibitedarticles.prohibitedClass'),								//违禁品类别
			colspan:1,
			name: 'goodsSort'
		}),{
			border: 1,
			xtype:'container',
			columnWidth:1,
			colspan:4,
			defaultType:'button',
			layout:'column',
			items:[{
				text : baseinfo.prohibitedArticlesIndex.i18n('foss.baseinfo.reset'),//重置   
				  columnWidth:.08,
				  disabled:!baseinfo.prohibitedArticlesIndex.isPermission('queryProhibitedArticlesExactByEntity/prohibitedArticlesQueryButton'),
				  hidden:!baseinfo.prohibitedArticlesIndex.isPermission('queryProhibitedArticlesExactByEntity/prohibitedArticlesQueryButton'),
				  handler : function() {
						this.up('form').getForm().reset();
					}
			  	},{
					xtype:'container',
					border:false,
					html:'&nbsp;',
					columnWidth:.84
				},{
				  text : baseinfo.prohibitedArticlesIndex.i18n('foss.baseinfo.query'),//查询
				  disabled:!baseinfo.prohibitedArticlesIndex.isPermission('queryProhibitedArticlesExactByEntity/prohibitedArticlesQueryButton'),
				  hidden:!baseinfo.prohibitedArticlesIndex.isPermission('queryProhibitedArticlesExactByEntity/prohibitedArticlesQueryButton'),
				  columnWidth:.08,
				  cls:'yellow_button',  
				  handler : function() {
						var grid  = Ext.getCmp('T_baseinfo-prohibitedArticlesIndex_content').getQueryGrid();//得到grid
						//grid.getPagingToolbar().moveFirst();//用分页的moveFirst()方法
						grid.store.loadPage(1);//用分页的moveFirst()方法
					}
			  	}]
		}];
	}
});
//禁运物品 界面form
Ext.define('Foss.baseinfo.prohibitedArticlesIndex.ProhibitedArticlesWinForm', {
	extend : 'Ext.form.Panel',
	frame: true,
	defaultType : 'textfield',
	layout:{
        type: 'table',
        columns: 2
    },
    formRecord:null,												//绑定的model Foss.baseinfo.prohibitedArticlesIndex.ProhibitedArticlesModel
	constructor : function(config) {							//构造器
		var me = this,cfg = Ext.apply({}, config);
		me.defaults = me.getDefaults(config);
		me.items = me.getItems(config);
		me.callParent([cfg]);
	},
	getDefaults:function(config){
		var me = this;
		return {
	    	margin : '8 10 5 10',
	    	//labelSeparator:'',
//			allowBlank:false,
			readOnly:(baseinfo.viewState.view === config.viewState)?true:false
	    };
	},
	getItems:function(config){
		var me = this;
		return [{
			fieldLabel:baseinfo.prohibitedArticlesIndex.i18n('foss.baseinfo.prohibitedarticles.prohibitedName'),							//违禁品名称
			name:'goodsName',
			//违禁品名称 不能重复
			listeners:{
				blur:function(field){
        			if(!Ext.isEmpty(field.getValue())
        				&&(baseinfo.viewState.view != config.viewState)
        				&& config.formRecord.get('goodsName') != field.getValue()){
        				baseinfo.prohibitedArticlesIndex.prohibitedArticlesIsExist(field,field.getValue(),field.fieldLabel,field.name);
        			}
        		}
        	}
		},FossDataDictionary.getDataDictionaryCombo('CONTRABAND_TYPE',{
			fieldLabel:baseinfo.prohibitedArticlesIndex.i18n('foss.baseinfo.prohibitedarticles.prohibitedType'),								//违禁品类型
			name: 'goodsType',
			allowBlank:false,
	    	labelWidth:110,
			readOnly:(baseinfo.viewState.view === config.viewState)?true:false
		}),FossDataDictionary.getDataDictionaryCombo('CONTRABAND_LEVEL',{
			fieldLabel:baseinfo.prohibitedArticlesIndex.i18n('foss.baseinfo.prohibitedarticles.prohibitedLevel'),								//违禁品级别
			name: 'goodsLevel',
	    	labelWidth:100,
			allowBlank:false,
			readOnly:(baseinfo.viewState.view === config.viewState)?true:false
		}),FossDataDictionary.getDataDictionaryCombo('CONTRABAND_GOODS_CATEGORY',{
			fieldLabel:baseinfo.prohibitedArticlesIndex.i18n('foss.baseinfo.prohibitedarticles.prohibitedClass'),								//违禁品类别
			name: 'goodsSort',
			allowBlank:false,
	    	labelWidth:110,
			readOnly:(baseinfo.viewState.view === config.viewState)?true:false
		}),{
			colspan:2,
			name: 'keyWords',
	        fieldLabel: '关键字',
	        emptyText :baseinfo.prohibitedArticlesIndex.i18n('foss.baseinfo.prohibitedarticles.keywordshouldSplitByComma'),
	        allowBlank:false,
	        width:590,
	        maxLength:100,
	        xtype : 'textfield',
	        listeners:{
	        	blur:function(comp,eventObj,obj){
	        		var length = comp.getValue().length;
	        		if(length > 100){
	        			comp.setValue(comp.getValue().substring(0,100));
	        		}
	        		
	        	}
	        }
		},{colspan:2,xtype:'label',text: baseinfo.prohibitedArticlesIndex.i18n('foss.baseinfo.prohibitedarticles.discription')}	//displayfield	 label					//违禁品类别
		,{
			colspan:2,
			width:618,
			height:40,
			maxLength:500,
			allowBlank:true,
	        hideLabel:true,
			name:'notes'
		}];
	}
});
//------------------------------------GRID----------------------------------
//禁运物品 查询结果grid
Ext.define('Foss.baseinfo.prohibitedArticlesIndex.QueryResultGrid', {
	extend: 'Ext.grid.Panel',
	title : baseinfo.prohibitedArticlesIndex.i18n('foss.baseinfo.airagencycompany.resultList'),
    sortableColumns:false,
    enableColumnHide:false,
    enableColumnMove:false,
    bodyCls:'autoHeight',
	stripeRows : true, 									// 交替行效果
	selType : "rowmodel", 								// 选择类型设置为：行选择
	emptyText: baseinfo.prohibitedArticlesIndex.i18n('foss.baseinfo.queryResultIsNull'),							//查询结果为空
	frame: true,
	defaults:{
		flex:1
	},
	//得到BBAR（分页）
	pagingToolbar : null,
	constructor : function(config){
		var me = this, cfg = Ext.apply({}, config);
		me.columns = me.getColumns(config);
		me.store = me.getStore();
		me.listeners = me.getMyListeners(config);
	    //添加多选框
		me.selModel = Ext.create('Ext.selection.CheckboxModel',{mode:'MULTI',checkOnly:true});
		//添加头部按钮
		me.tbar = me.getTbar(config);
		//添加分页控件
		me.bbar = me.getPagingToolbar(config);
		//设置分页控件的store属性
		me.getPagingToolbar().store = me.store;
		me.callParent([cfg]);
	},
	getTbar:function(config){
		var me = this;
		return[{
			text : baseinfo.prohibitedArticlesIndex.i18n('foss.baseinfo.add'),								//新增
			disabled:!baseinfo.prohibitedArticlesIndex.isPermission('queryProhibitedArticlesExactByEntity/prohibitedArticlesAddButton'),
			hidden:!baseinfo.prohibitedArticlesIndex.isPermission('queryProhibitedArticlesExactByEntity/prohibitedArticlesAddButton'),
			//hidden:!pricing.isPermission('../pricing/saveRole.action')),
			handler :function(){
				me.addProhibitedArticles({}).show();
			} 
		},'-', {
			text : baseinfo.prohibitedArticlesIndex.i18n('foss.baseinfo.void'),								//作废
			//hidden:!pricing.isPermission('../pricing/deleteRole.action')),
			disabled:!baseinfo.prohibitedArticlesIndex.isPermission('queryProhibitedArticlesExactByEntity/prohibitedArticlesDisableButton'),
			hidden:!baseinfo.prohibitedArticlesIndex.isPermission('queryProhibitedArticlesExactByEntity/prohibitedArticlesDisableButton'),
			handler :function(){
				baseinfo.prohibitedArticlesIndex.deleteProhibitedArticlesByCode(baseinfo.delAgencyType);
			} 
		} ];
	},
	getPagingToolbar : function(config) {
		if (Ext.isEmpty(this.pagingToolbar)) {
			this.pagingToolbar = Ext.create('Deppon.StandardPaging', {
				store : this.store,
				pageSize : 20
			});
		}
		return this.pagingToolbar;
	},
	//得到禁运物品编辑窗体
	getAgencyDeptWin:function(win,title,viewState,param){
		var formRecord = Ext.isEmpty(param.formRecord)?Ext.create('Foss.baseinfo.prohibitedArticlesIndex.ProhibitedArticlesModel'):param.formRecord;
		if (Ext.isEmpty(win)) {
			win = Ext.create('Foss.baseinfo.prohibitedArticlesIndex.ProhibitedArticlesWin',{
				'title':title,
				'viewState':viewState,
				'sourceGrid':this,
				'formRecord':formRecord
			});
		}
		//加载数据
		win.editForm.loadRecord(formRecord);
		return win;
	},
	//得到禁运物品编辑窗体,查看状态viewState："ADD"新增,"UPDATE"修改,"VIEW"查看
	getProhibitedArticlesWin:function(viewState,param){
		if(baseinfo.viewState.add === viewState){
			return this.getAgencyDeptWin(this.addProhibitedArticlesWin,baseinfo.prohibitedArticlesIndex.i18n('foss.baseinfo.prohibitedarticles.addProhibited'),viewState,param);
		}else if(baseinfo.viewState.update === viewState){
			return this.getAgencyDeptWin(this.updateProhibitedArticlesWin,baseinfo.prohibitedArticlesIndex.i18n('foss.baseinfo.prohibitedarticles.alterProhibited'),viewState,param);
		}else if(baseinfo.viewState.view === viewState){
			return this.getAgencyDeptWin(this.viewProhibitedArticlesWin,baseinfo.prohibitedArticlesIndex.i18n('foss.baseinfo.prohibitedarticles.viewProhibited'),viewState,param);
		}
	},
	addProhibitedArticlesWin:null,						//新增基偏线代理公司
	addProhibitedArticles:function(param){
		return this.getProhibitedArticlesWin(baseinfo.viewState.add,param);
	},
	updateProhibitedArticlesWin:null,						//修改基禁运物品
	updateProhibitedArticles:function(param){
		return this.getProhibitedArticlesWin(baseinfo.viewState.update,param);
	},
	viewProhibitedArticlesWin:null,						//查看基禁运物品
	viewProhibitedArticles:function(param){
		return this.getProhibitedArticlesWin(baseinfo.viewState.view,param);
	},
	getMyListeners:function(){
		var me = this;
		return {
		    //增加滚动条事件，防止出现滚动条后却不能用
	    	scrollershow: function(scroller) {
	    		if (scroller && scroller.scrollEl) {
	    				scroller.clearManagedListeners(); 
	    				scroller.mon(scroller.scrollEl, 'scroll', scroller.onElScroll, scroller); 
	    		}
	    	},
	    	//查看 禁运物品
	    	itemdblclick: function(view,record) {
				var param = {};
            	param.formRecord = record;
				me.viewProhibitedArticles(param).show();
	    	}
	    };
	},
	getStore:function(){
		return Ext.create('Foss.baseinfo.prohibitedArticlesIndex.ProhibitedArticlesStore',{
			autoLoad : false,
			pageSize : 20,
			listeners: {
				beforeload: function(store, operation, eOpts){
					var queryForm = Ext.getCmp('T_baseinfo-prohibitedArticlesIndex_content').getQueryForm().getForm();//得到查询的FORM表单
					queryForm.updateRecord(queryForm.record);
					var entity = queryForm.record.data;
					if(queryForm!=null){
						Ext.apply(operation,{
							params : {
								//禁运物品名称
								'objectVo.prohibitedArticlesEntity.goodsName':entity.goodsName,
								//禁运物品类型
								'objectVo.prohibitedArticlesEntity.goodsType':entity.goodsType,
								//禁运物品级别
								'objectVo.prohibitedArticlesEntity.goodsLevel':entity.goodsLevel,
								//禁运物品类别
								'objectVo.prohibitedArticlesEntity.goodsSort':entity.goodsSort
							}
						});	
					}
				}
		    }
		});
	},
	getColumns:function(config){
		var me = this;
		return [{
			align : 'center',
			xtype : 'actioncolumn',
			width:100,
			text : baseinfo.prohibitedArticlesIndex.i18n('foss.baseinfo.operate'),//操作
			items: [{
            	iconCls:'deppon_icons_edit',
                tooltip: baseinfo.prohibitedArticlesIndex.i18n('foss.baseinfo.update'),
                disabled:!baseinfo.prohibitedArticlesIndex.isPermission('queryProhibitedArticlesExactByEntity/prohibitedArticlesUpdateButton'),
                handler: function(grid, rowIndex, colIndex) {
    				var param = {};
                	param.formRecord = grid.getStore().getAt(rowIndex);
    				me.updateProhibitedArticles(param).show();
    			}
            },{
            	iconCls:'deppon_icons_cancel',
                tooltip: baseinfo.prohibitedArticlesIndex.i18n('foss.baseinfo.void'),
                disabled:!baseinfo.prohibitedArticlesIndex.isPermission('queryProhibitedArticlesExactByEntity/prohibitedArticlesDisableButton'),
                hidden:!baseinfo.prohibitedArticlesIndex.isPermission('queryProhibitedArticlesExactByEntity/prohibitedArticlesDisableButton'),
                disabled:baseinfo.actioncolumnDisabled,
                handler: function(grid, rowIndex, colIndex) {
    				baseinfo.prohibitedArticlesIndex.deleteProhibitedArticlesByCode(null,grid.getStore().getAt(rowIndex),grid);
                }
            }]
		},{
			text : baseinfo.prohibitedArticlesIndex.i18n('foss.baseinfo.prohibitedarticles.prohibitedArticlesName'),									//禁运物品名称
			dataIndex : 'goodsName',
			flex:1
		},{
			text : baseinfo.prohibitedArticlesIndex.i18n('foss.baseinfo.prohibitedarticles.prohibitedArticlesType'),									//禁运物品类型
			dataIndex : 'goodsType',
			flex:1,
			renderer:function(v){
				return FossDataDictionary. rendererSubmitToDisplay (v,'CONTRABAND_TYPE');
			}
		},{
			text : baseinfo.prohibitedArticlesIndex.i18n('foss.baseinfo.prohibitedarticles.prohibitedArticlesLevel'),									//禁运物品级别
			dataIndex : 'goodsLevel',
			flex:1,
			renderer:function(v){
				return FossDataDictionary. rendererSubmitToDisplay (v,'CONTRABAND_LEVEL');
			}
		},{
			text : baseinfo.prohibitedArticlesIndex.i18n('foss.baseinfo.prohibitedarticles.prohibitedArticlesClass'),									//禁运物品类别
			dataIndex : 'goodsSort',
			flex:1,
			renderer:function(v){
				return FossDataDictionary. rendererSubmitToDisplay (v,'CONTRABAND_GOODS_CATEGORY');
			}
		},{
			text : '关键字',									//关键字
			dataIndex : 'keyWords',
			flex:1
		}];
	}
});
//------------------------------------ONREADY----------------------------------
/**
 * 程序入口方法
 */
Ext.onReady(function() {
	Ext.QuickTips.init();
	if (Ext.getCmp('T_baseinfo-prohibitedArticlesIndex_content')){
		return;
	}
	var queryForm  = Ext.create('Foss.baseinfo.prohibitedArticlesIndex.QueryConditionForm',{'record':Ext.create('Foss.baseinfo.prohibitedArticlesIndex.ProhibitedArticlesModel')});//查询FORM
	var queryGrid  = Ext.create('Foss.baseinfo.prohibitedArticlesIndex.QueryResultGrid');//查询结果显示列表
	Ext.getCmp('T_baseinfo-prohibitedArticlesIndex').add(Ext.create('Ext.panel.Panel', {
		id : 'T_baseinfo-prohibitedArticlesIndex_content',
		cls : 'panelContentNToolbar',
		bodyCls : 'panelContentNToolbar-body',
		//获得查询FORM
		getQueryForm : function() {
			return queryForm;
		},
		//获得查询结果GRID
		getQueryGrid : function() {
			return queryGrid;
		},
//		items : [ queryForm, queryGrid,{
//			xtype:'button',
//			text:baseinfo.prohibitedArticlesIndex.i18n('foss.baseinfo.void'),								//作废
//			//hidden:!pricing.isPermission('../pricing/deleteRole.action')),
//			handler :function(){
//				baseinfo.prohibitedArticlesIndex.deleteProhibitedArticlesByCode(baseinfo.delAgencyType);
//			}
//		}] 
		items : [queryForm,queryGrid]
	}));
});
//------------------------------------WINDOW--------------------------------
//禁运物品界面win
Ext.define('Foss.baseinfo.prohibitedArticlesIndex.ProhibitedArticlesWin',{
	extend : 'Ext.window.Window',
	title : baseinfo.prohibitedArticlesIndex.i18n('foss.baseinfo.prohibitedarticles.addProhibited'),								//新增偏线代理公司   默认新增
	closable : true,
	modal : true,
	resizable:false,
	closeAction : 'hide',
	width :700,
	height :350,	
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	listeners:{
		beforehide:function(me){
			//清空 有ID的组件
		}
	},
	viewState:baseinfo.viewState.add,				//查看状态,默认为新增
	editForm:null,											//偏线代理公司表单Form
	editGrid:null,											//偏线代理公司表格Grid
	formRecord:null,										//偏线代理公司实体 Foss.baseinfo.BusinessPartnerModel
	gridDate:null,											//偏线代理公司 网点信息数组  [Foss.baseinfo.OuterBranchModel]
    constructor : function(config) {
		var me = this,cfg = Ext.apply({}, config);
		me.editForm = Ext.create('Foss.baseinfo.prohibitedArticlesIndex.ProhibitedArticlesWinForm',{'height':240,'viewState':config.viewState,'formRecord':config.formRecord});
		me.items = [me.editForm];
		me.fbar = me.getFbar(config);
		me.callParent([cfg]);
	},
	initComponent:function(){
		var me = this;
		this.callParent();
	},
	//操作界面上的按钮
	getFbar:function(config){
		var me = this;
		return [{
			text : baseinfo.prohibitedArticlesIndex.i18n('foss.baseinfo.cancel'),
			handler :function(){
				me.hide();
			}
		},{
			text : baseinfo.prohibitedArticlesIndex.i18n('foss.baseinfo.reset'),
			disabled:(baseinfo.viewState.view === config.viewState)?true:false,
			handler :function(){
				// 重置
				baseinfo.formReset([me.editForm.getForm()],[me.formRecord]);
			} 
		},{
			text : baseinfo.prohibitedArticlesIndex.i18n('foss.baseinfo.save'),
			disabled:(baseinfo.viewState.view === config.viewState)?true:false,
			handler :function(){
		    	var editForm = me.editForm.getForm();
		    	//实时校验的 结果是否通过,判断偏线代理必填项是否填写并全部填写合法
		    	if(editForm.findField('goodsName').hasActiveError()
		    			||!editForm.isValid()){
		    		baseinfo.showInfoMsg(baseinfo.prohibitedArticlesIndex.i18n('foss.baseinfo.airagencycompany.checkData'));
		    		return;
		    	}
	    		editForm.updateRecord(me.formRecord);
	    		baseinfo.prohibitedArticlesIndex.submitProhibitedArticles(me,me.viewState,me.formRecord.data);
			}
		}];
	}
});

