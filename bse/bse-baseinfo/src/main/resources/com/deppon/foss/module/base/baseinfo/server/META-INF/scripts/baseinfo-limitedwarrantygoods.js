/**
 * 限保物品model									Foss.baseinfo.limitedWarrantyGoodsIndex.LimitedWarrantyItemsEntityModel
 * 限保物品store									Foss.baseinfo.limitedWarrantyGoodsIndex.LimitedWarrantyItemsEntityStore
 * 限保物品form									Foss.baseinfo.limitedWarrantyGoodsIndex.QueryConditionForm
 * 限保物品grid									Foss.baseinfo.limitedWarrantyGoodsIndex.QueryResultGrid
 * 限保物品winForm								Foss.baseinfo.limitedWarrantyGoodsIndex.LimitedWarrantyItemsEntityWinForm
 * 限保物品winGrid								Foss.baseinfo.limitedWarrantyGoodsIndex.LimitedWarrantyItemsEntityWinGrid
 * 限保物品win									Foss.baseinfo.limitedWarrantyGoodsIndex.LimitedWarrantyItemsEntityWin
 */

//------------------------------------常量和公用方法----------------------------------
//信息
baseinfo.showInfoMsg = function(message,fun) {
	var len = message.length;
	Ext.Msg.show({
	    title:baseinfo.limitedWarrantyGoodsIndex.i18n('i18n.baseinfo-util.fossAlert'),
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
baseinfo.limitedWarrantyGoodsIndex.submitLimitedWarrantyItemsEntity = function(win,viewState,operatEntity){
	var grid = Ext.getCmp('T_baseinfo-limitedWarrantyGoodsIndex_content').getQueryGrid()
		,url = baseinfo.realPath('addLimitedWarrantyItems.action')
		,m_success = baseinfo.limitedWarrantyGoodsIndex.i18n('foss.baseinfo.saveSuccess')								//保存成功！
		,m_failure = baseinfo.limitedWarrantyGoodsIndex.i18n('foss.baseinfo.airagencycompany.saveFail')								//保存失败！
		,m_dateError = baseinfo.limitedWarrantyGoodsIndex.i18n('foss.baseinfo.airagencycompany.dataError')								//数据异常！
		,objectVo = {};
	objectVo.limitedWarrantyItemsEntity = operatEntity;
	if(baseinfo.viewState.add === viewState){
		//新增URL(已经有)
	}else if(baseinfo.viewState.update === viewState){
		//修改URL
		url = baseinfo.realPath('updateLimitedWarrantyItems.action');
	}
	baseinfo.requestAjaxJson(url,{'objectVo':objectVo},function(result){
		if(!Ext.isEmpty(result.objectVo.limitedWarrantyItemsEntity)){
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
baseinfo.limitedWarrantyGoodsIndex.deleteLimitedWarrantyItemsEntityByCode = function(delAgencyCompanyType,operatRecord){
	var grid = Ext.getCmp('T_baseinfo-limitedWarrantyGoodsIndex_content').getQueryGrid(),
		url = baseinfo.realPath('deleteLimitedWarrantyItems.action'),
		objectVo = {};
	selection=grid.getSelectionModel().getSelection();
	if(selection.length<=0 && Ext.isEmpty(operatRecord)){
		Ext.MessageBox.alert(baseinfo.limitedWarrantyGoodsIndex.i18n('foss.baseinfo.airagencycompany.remind'),baseinfo.limitedWarrantyGoodsIndex.i18n('foss.baseinfo.airagencycompany.selectData'));
	}else{	
		if(!Ext.isEmpty(delAgencyCompanyType)&&baseinfo.delAgencyType===delAgencyCompanyType){
			var codeStr = [];
			//批量作废
			url = baseinfo.realPath('deleteLimitedWarrantyItemsMore.action');
			for (var j = 0; j < selection.length; j++) {
				codeStr.push(selection[j].get('virtualCode'));
			}
			objectVo.codeStr = codeStr;
		}else{
			objectVo.limitedWarrantyItemsEntity = operatRecord.data;
		}
		Ext.MessageBox.buttonText.yes = baseinfo.limitedWarrantyGoodsIndex.i18n('foss.baseinfo.sure');
		Ext.MessageBox.buttonText.no = baseinfo.limitedWarrantyGoodsIndex.i18n('foss.baseinfo.cancel');
		Ext.Msg.confirm(baseinfo.limitedWarrantyGoodsIndex.i18n('foss.baseinfo.billAdvertisingSlogan.fossAlertU'),baseinfo.limitedWarrantyGoodsIndex.i18n('foss.baseinfo.billAdvertisingSlogan.confirmAlertRecord'),function(btn,text) {
			if (btn == 'yes') {
				baseinfo.requestAjaxJson(url,{'objectVo':objectVo},function(result){
					grid.store.loadPage(1);
					baseinfo.showInfoMsg(baseinfo.limitedWarrantyGoodsIndex.i18n('foss.baseinfo.deleteSuccess'));
				},function(result){
					baseinfo.showErrorMes(result.message);
				});
			}
		});
	}
};
baseinfo.limitedWarrantyGoodsIndex.prohibitedArticlesIsExist = function(field,fieldValue,fieldLabel,fieldNmae){
	var url = baseinfo.realPath('limitedwarrantygoodsIsExist.action'),objectVo ={}
	,entytyRecord = Ext.create('Foss.baseinfo.limitedWarrantyGoodsIndex.LimitedWarrantyItemsEntityModel');
	entytyRecord.set(fieldNmae+'',fieldValue);
	objectVo.limitedWarrantyItemsEntity = entytyRecord.data;
	baseinfo.requestAjaxJson(url,{'objectVo':objectVo},function(result){
		if(Ext.isEmpty(result.objectVo.limitedWarrantyItemsEntity)){
			field.clearInvalid();
		}else{
			field.markInvalid(baseinfo.limitedWarrantyGoodsIndex.i18n('foss.baseinfo.airagencycompany.dataRepeatBegin')+fieldLabel+baseinfo.limitedWarrantyGoodsIndex.i18n('foss.baseinfo.airagencycompany.dataRepeatEnd'));
		}
	},function(result){
		field.markInvalid(baseinfo.limitedWarrantyGoodsIndex.i18n('foss.baseinfo.airagencycompany.dataRepeatBegin')+fieldLabel+baseinfo.limitedWarrantyGoodsIndex.i18n('foss.baseinfo.airagencycompany.dataRepeatEnd'));
	});
};

//------------------------------------MODEL----------------------------------
//限保物品Model
Ext.define('Foss.baseinfo.limitedWarrantyGoodsIndex.LimitedWarrantyItemsEntityModel', {
extend: 'Ext.data.Model',
fields : [//虚拟编码
	  {name:'virtualCode',type:'string'},
	  //物品名称
	  {name:'goodsName',type:'string'},
	  //限保金额
	  {name:'limitedAmount',type:'string'},
	  //备注
	  {name:'notes',type:'string'},
	  //是否启用
	  {name:'active',type:'string'}]
});
//------------------------------------STORE----------------------------------
//限保物品STORE
Ext.define('Foss.baseinfo.limitedWarrantyGoodsIndex.LimitedWarrantyItemsEntityStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.baseinfo.limitedWarrantyGoodsIndex.LimitedWarrantyItemsEntityModel',
	pageSize:20,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url : baseinfo.realPath('queryLimitedWarrantyItemsExactByEntity.action'),
		reader : {
			type : 'json',
			root : 'objectVo.limitedWarrantyItemsEntityList',
			totalProperty : 'totalCount'
		}
	}
});
//------------------------------------FORM----------------------------------
//限保物品 查询条件
Ext.define('Foss.baseinfo.limitedWarrantyGoodsIndex.QueryConditionForm', {
	extend : 'Ext.form.Panel',
	title: baseinfo.limitedWarrantyGoodsIndex.i18n('foss.baseinfo.queryCondition'),
	frame: true,
	collapsible: true,
    defaults : {
    	margin : '8 10 5 10',
    	//labelSeparator:'',
    	labelWidth:110
    },
    height :140,
	defaultType : 'textfield',
	layout:{
        type: 'table',
        columns: 2
    },
    record:null,												//绑定的model Foss.baseinfo.limitedWarrantyGoodsIndex.LimitedWarrantyItemsEntityModel
	constructor : function(config) {							//构造器
		var me = this,cfg = Ext.apply({}, config);
		me.items = me.getItems();
		me.callParent([cfg]);
	},
	getItems:function(){
		var me = this;
		return [{
			fieldLabel:'物品名称',							//物品名称
			name:'goodsName',
			colspan:2
		},{
			border: 1,
			xtype:'container',
			columnWidth:1,
			colspan:2,
			defaultType:'button',
			layout:'column',
			items:[{
				  text : baseinfo.limitedWarrantyGoodsIndex.i18n('foss.baseinfo.reset'),//重置   
				  columnWidth:.3,
				  disabled:!baseinfo.limitedWarrantyGoodsIndex.isPermission('queryLimitedWarrantyItemsExactByEntity/LimitedWarrantyQueryButton'),
				  hidden:!baseinfo.limitedWarrantyGoodsIndex.isPermission('queryLimitedWarrantyItemsExactByEntity/LimitedWarrantyQueryButton'),
				  handler : function() {
					  this.up('form').getForm().reset();
					}
			  	},{
					xtype:'container',
					border:false,
					html:'&nbsp;',
					columnWidth:.4
				},{
					text : baseinfo.limitedWarrantyGoodsIndex.i18n('foss.baseinfo.query'),//查询
				  columnWidth:.3,
				  disabled:!baseinfo.limitedWarrantyGoodsIndex.isPermission('queryLimitedWarrantyItemsExactByEntity/LimitedWarrantyQueryButton'),
				  hidden:!baseinfo.limitedWarrantyGoodsIndex.isPermission('queryLimitedWarrantyItemsExactByEntity/LimitedWarrantyQueryButton'),
				  cls:'yellow_button',  
				  handler : function() {
					  var grid  = Ext.getCmp('T_baseinfo-limitedWarrantyGoodsIndex_content').getQueryGrid();//得到grid
					  //grid.getPagingToolbar().moveFirst();//用分页的moveFirst()方法
						grid.store.loadPage(1);//用分页的moveFirst()方法
					}
			  	}]
		}];
	}
});
//限保物品 界面form
Ext.define('Foss.baseinfo.limitedWarrantyGoodsIndex.LimitedWarrantyItemsEntityWinForm', {
	extend : 'Ext.form.Panel',
	defaultType : 'textfield',
	layout:{
        type: 'table',
        columns: 1
    },
    formRecord:null,												//绑定的model Foss.baseinfo.limitedWarrantyGoodsIndex.LimitedWarrantyItemsEntityModel
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
			allowBlank:false,
			width:300,
			readOnly:(baseinfo.viewState.view === config.viewState)?true:false
	    };
	},
	getItems:function(config){
		var me = this;
		return [{
			fieldLabel:'物品名称',							//物品名称
			name:'goodsName',
			maxLength:40,
			//物品名称 不能重复
			listeners:{
				blur:function(field){
        			if(!Ext.isEmpty(field.getValue())
        				&&(baseinfo.viewState.view != config.viewState)
        				&& config.formRecord.get('goodsName') != field.getValue()){
        				baseinfo.limitedWarrantyGoodsIndex.prohibitedArticlesIsExist(field,field.getValue(),field.fieldLabel,field.name);
        			}
        		}
        	}
		},{
			xtype:'numberfield',
	        fieldLabel: '限保金额（元）',							//物品类型
			name:'limitedAmount',
			maxLength:10,
			regex:/^\d*$/,
			hideTrigger: true,
			maxValue:99999999,
	        keyNavEnabled: false,
	        mouseWheelEnabled: false
		},{
			fieldLabel:baseinfo.limitedWarrantyGoodsIndex.i18n('foss.baseinfo.notes'),
			xtype:'textareafield',
			height:40,
			maxLength:500,
			allowBlank:true,
			name:'notes'
		}];
	}
});
//------------------------------------GRID----------------------------------
//限保物品 查询结果grid
Ext.define('Foss.baseinfo.limitedWarrantyGoodsIndex.QueryResultGrid', {
	extend: 'Ext.grid.Panel',
	title : baseinfo.limitedWarrantyGoodsIndex.i18n('foss.baseinfo.airagencycompany.resultList'),
    sortableColumns:false,
    enableColumnHide:false,
    enableColumnMove:false,
    bodyCls:'autoHeight',
	stripeRows : true, 									// 交替行效果
	selType : "rowmodel", 								// 选择类型设置为：行选择
	emptyText: baseinfo.limitedWarrantyGoodsIndex.i18n('foss.baseinfo.queryResultIsNull'),							//查询结果为空
	frame: true,
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
			text : baseinfo.limitedWarrantyGoodsIndex.i18n('foss.baseinfo.add'),								//新增
			disabled:!baseinfo.limitedWarrantyGoodsIndex.isPermission('queryLimitedWarrantyItemsExactByEntity/LimitedWarrantyAddButton'),
			hidden:!baseinfo.limitedWarrantyGoodsIndex.isPermission('queryLimitedWarrantyItemsExactByEntity/LimitedWarrantyAddButton'),
			//hidden:!pricing.isPermission('../pricing/saveRole.action')),
			handler :function(){
				me.addLimitedWarrantyItemsEntity({}).show();
			} 
		},'-', {
			text : baseinfo.limitedWarrantyGoodsIndex.i18n('foss.baseinfo.void'),								//作废
			disabled:!baseinfo.limitedWarrantyGoodsIndex.isPermission('queryLimitedWarrantyItemsExactByEntity/LimitedWarrantyDisableButton'),
			hidden:!baseinfo.limitedWarrantyGoodsIndex.isPermission('queryLimitedWarrantyItemsExactByEntity/LimitedWarrantyDisableButton'),
			//hidden:!pricing.isPermission('../pricing/deleteRole.action')),
			handler :function(){
				baseinfo.limitedWarrantyGoodsIndex.deleteLimitedWarrantyItemsEntityByCode(baseinfo.delAgencyType);
			} 
		}];
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
	//得到限保物品编辑窗体
	getAgencyDeptWin:function(win,title,viewState,param){
		var formRecord = Ext.isEmpty(param.formRecord)?Ext.create('Foss.baseinfo.limitedWarrantyGoodsIndex.LimitedWarrantyItemsEntityModel'):param.formRecord;
		if (Ext.isEmpty(win)) {
			win = Ext.create('Foss.baseinfo.limitedWarrantyGoodsIndex.LimitedWarrantyItemsEntityWin',{
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
	//得到限保物品编辑窗体,查看状态viewState："ADD"新增,"UPDATE"修改,"VIEW"查看
	getLimitedWarrantyItemsEntityWin:function(viewState,param){
		if(baseinfo.viewState.add === viewState){
			return this.getAgencyDeptWin(this.addLimitedWarrantyItemsEntityWin,'新增限保物品',viewState,param);
		}else if(baseinfo.viewState.update === viewState){
			return this.getAgencyDeptWin(this.updateLimitedWarrantyItemsEntityWin,'修改限保物品',viewState,param);
		}else if(baseinfo.viewState.view === viewState){
			return this.getAgencyDeptWin(this.viewLimitedWarrantyItemsEntityWin,'查看限保物品',viewState,param);
		}
	},
	addLimitedWarrantyItemsEntityWin:null,						//新增基偏线代理公司
	addLimitedWarrantyItemsEntity:function(param){
		return this.getLimitedWarrantyItemsEntityWin(baseinfo.viewState.add,param);
	},
	updateLimitedWarrantyItemsEntityWin:null,						//修改基限保物品
	updateLimitedWarrantyItemsEntity:function(param){
		return this.getLimitedWarrantyItemsEntityWin(baseinfo.viewState.update,param);
	},
	viewLimitedWarrantyItemsEntityWin:null,						//查看基限保物品
	viewLimitedWarrantyItemsEntity:function(param){
		return this.getLimitedWarrantyItemsEntityWin(baseinfo.viewState.view,param);
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
	    	//查看 限保物品
	    	itemdblclick: function(view,record) {
				var param = {};
            	param.formRecord = record;
				me.viewLimitedWarrantyItemsEntity(param).show();
	    	}
	    };
	},
	getStore:function(){
		return Ext.create('Foss.baseinfo.limitedWarrantyGoodsIndex.LimitedWarrantyItemsEntityStore',{
			autoLoad : false,
			pageSize : 20,
			listeners: {
				beforeload: function(store, operation, eOpts){
					var queryForm = Ext.getCmp('T_baseinfo-limitedWarrantyGoodsIndex_content').getQueryForm().getForm();//得到查询的FORM表单
					queryForm.updateRecord(queryForm.record);
					var entity = queryForm.record.data;
					if(queryForm!=null){
						Ext.apply(operation,{
							params : {
								//限保物品名称
								'objectVo.limitedWarrantyItemsEntity.goodsName':entity.goodsName
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
			text : baseinfo.limitedWarrantyGoodsIndex.i18n('foss.baseinfo.operate'),//操作
			items: [{
            	iconCls:'deppon_icons_edit',
                tooltip: baseinfo.limitedWarrantyGoodsIndex.i18n('foss.baseinfo.update'),
                disabled:!baseinfo.limitedWarrantyGoodsIndex.isPermission('queryLimitedWarrantyItemsExactByEntity/LimitedWarrantyEditButton'),
                handler: function(grid, rowIndex, colIndex) {
    				var param = {};
                	param.formRecord = grid.getStore().getAt(rowIndex);
    				me.updateLimitedWarrantyItemsEntity(param).show();
    			}
            },{
            	iconCls:'deppon_icons_cancel',
                tooltip: baseinfo.limitedWarrantyGoodsIndex.i18n('foss.baseinfo.void'),
                disabled:baseinfo.actioncolumnDisabled,
                disabled:!baseinfo.limitedWarrantyGoodsIndex.isPermission('queryLimitedWarrantyItemsExactByEntity/LimitedWarrantyDisableButton'),
                handler: function(grid, rowIndex, colIndex) {
    				baseinfo.limitedWarrantyGoodsIndex.deleteLimitedWarrantyItemsEntityByCode(null,grid.getStore().getAt(rowIndex),grid);
                }
            }]
		},{
			text : '物品名称',									//限保物品名称
			flex:1,
			dataIndex : 'goodsName'
		},{
			text : '限保金额（元）',									//限保物品类型
			flex:1,
			dataIndex : 'limitedAmount'
		}];
	}
});
//------------------------------------ONREADY----------------------------------
/**
 * 程序入口方法
 */
Ext.onReady(function() {
	Ext.QuickTips.init();
	if (Ext.getCmp('T_baseinfo-limitedWarrantyGoodsIndex_content')){
		return;
	}
	var queryForm  = Ext.create('Foss.baseinfo.limitedWarrantyGoodsIndex.QueryConditionForm',{'record':Ext.create('Foss.baseinfo.limitedWarrantyGoodsIndex.LimitedWarrantyItemsEntityModel')});//查询FORM
	var queryGrid  = Ext.create('Foss.baseinfo.limitedWarrantyGoodsIndex.QueryResultGrid');//查询结果显示列表
	Ext.getCmp('T_baseinfo-limitedWarrantyGoodsIndex').add(Ext.create('Ext.panel.Panel', {
		id : 'T_baseinfo-limitedWarrantyGoodsIndex_content',
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
		items : [queryForm, queryGrid]
//		renderTo : 'T_baseinfo-limitedWarrantyGoodsIndex-body'
	}));
});
//------------------------------------WINDOW--------------------------------
//限保物品界面win
Ext.define('Foss.baseinfo.limitedWarrantyGoodsIndex.LimitedWarrantyItemsEntityWin',{
	extend : 'Ext.window.Window',
	title : '新增限保物品',								//新增偏线代理公司   默认新增
	closable : true,
	modal : true,
	resizable:false,
	closeAction : 'hide',
	width :370,
	height :250,	
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
		me.editForm = Ext.create('Foss.baseinfo.limitedWarrantyGoodsIndex.LimitedWarrantyItemsEntityWinForm',{'height':180,'viewState':config.viewState,'formRecord':config.formRecord});
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
			text : baseinfo.limitedWarrantyGoodsIndex.i18n('foss.baseinfo.cancel'),
			handler :function(){
				me.hide();
			}
		},{
			text : baseinfo.limitedWarrantyGoodsIndex.i18n('foss.baseinfo.reset'),
			disabled:(baseinfo.viewState.view === config.viewState)?true:false,
			handler :function(){
				// 重置
				baseinfo.formReset([me.editForm.getForm()],[me.formRecord]);
			} 
		},{
			text : baseinfo.limitedWarrantyGoodsIndex.i18n('foss.baseinfo.save'),
			disabled:(baseinfo.viewState.view === config.viewState)?true:false,
			handler :function(){
		    	var editForm = me.editForm.getForm();
		    	//实时校验的 结果是否通过,判断偏线代理必填项是否填写并全部填写合法
		    	if(editForm.findField('goodsName').hasActiveError()
		    			||!editForm.isValid()){
		    		baseinfo.showInfoMsg(baseinfo.limitedWarrantyGoodsIndex.i18n('foss.baseinfo.airagencycompany.checkData'));
		    		return;
		    	}
	    		editForm.updateRecord(me.formRecord);
	    		baseinfo.limitedWarrantyGoodsIndex.submitLimitedWarrantyItemsEntity(me,me.viewState,me.formRecord.data);
			}
		}];
	}
});

