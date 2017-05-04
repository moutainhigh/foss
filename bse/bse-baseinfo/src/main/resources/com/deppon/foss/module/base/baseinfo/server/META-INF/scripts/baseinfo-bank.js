/**
 * 银行查询Form								Foss.baseinfo.bankIndex.QueryBankForm
 * 银行查询结果grid							Foss.baseinfo.bankIndex.QueryBankGrid
 * 银行修改Win								Foss.baseinfo.bankIndex.BankWin
 * 银行界面form								Foss.baseinfo.bankIndex.BankWinForm
 */
//------------------------------------业务方法----------------------------------
//修改银行
baseinfo.bankIndex.operatorCount = {defaultV:0,successV:1,failureV:-1};	//偏线代理 操作返回值 1为成功，-1为失败
baseinfo.bankIndex.readOnly;									//readOnly属性（新增）
baseinfo.bankIndex.booleanType = {all:'ALL',yes:'Y',no:'N'};	//booleanType  对应后台常量 "布尔类型"
baseinfo.bankIndex.booleanStr = {yes:'true',no:'false'};		//booleanStr   从复选框中得到值
//查看状态viewBankState："ADD"新增,"UPDATE"修改,"VIEW"查看
baseinfo.bankIndex.viewBankState = {add:'ADD',update:'UPDATE',view:'VIEW'};
//信息
baseinfo.bankIndex.showInfoMsg = function(message,fun) {
	var len = message.length;
	Ext.Msg.show({
	    title:baseinfo.bankIndex.i18n('i18n.baseinfo-util.fossAlert'),
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

//提交银行
baseinfo.bankIndex.submitBank = function(win,viewState,bankEntity,grid){
//	var grid = Ext.getCmp('T_baseinfo-bankIndex_content').getQueryGrid();
	var url = baseinfo.realPath('addBankDept.action')
		,m_success = baseinfo.bankIndex.i18n('foss.baseinfo.saveSuccess')								//保存成功！
		,m_failure = baseinfo.bankIndex.i18n('foss.baseinfo.airagencycompany.saveFail')								//保存失败！
		,m_dateError = baseinfo.bankIndex.i18n('foss.baseinfo.airagencycompany.dataError')								//数据异常！
		,bankVo = {};
	bankVo.bankEntity = bankEntity;
	if(baseinfo.bankIndex.viewBankState.add === viewState){
		//新增URL(已经有)
	}else if(baseinfo.bankIndex.viewBankState.update === viewState){
		//修改URL
		url = baseinfo.realPath('updateBank.action');
	}

	Ext.Ajax.request({
		url:url,
		async:false,
		jsonData:{'bankVo':bankVo},
		success:function(response){
			var result = Ext.decode(response.responseText);
			if(Ext.isEmpty(result)){
				baseinfo.showErrorMes(m_dateError);
			}else{//操作返回值 1：成功；-1：失败
				if(baseinfo.bankIndex.operatorCount.successV === result.bankVo.returnInt){
					grid.store.loadPage(1);
					baseinfo.bankIndex.showInfoMsg(m_success);
					win.hide();
				}else if(baseinfo.bankIndex.operatorCount.failureV === result.bankVo.returnInt){
					baseinfo.showErrorMes(Ext.isEmpty(result.message)?m_failure:result.message);
				}
			}
		},
		exception:function(response){
			var result = Ext.decode(response.responseText);
			if(Ext.isEmpty(result)){
				baseinfo.showErrorMes(m_dateError);
			}else{
				baseinfo.showErrorMes(result.message);
			}
		}
	});
};
//处理 银行信息 【是否支持几日退】 后台用 "Y"/"N" 区别
baseinfo.bankIndex.dealBankDis2Boolean = function(bankModel){
	var bankEntity = bankModel.copy();
	if(baseinfo.bankIndex.booleanType.yes === bankEntity.get('intraDayType') ){
		bankEntity.set('intraDayType',true);
	}
	if(baseinfo.bankIndex.booleanType.no === bankEntity.get('intraDayType') ){
		bankEntity.set('intraDayType',false);
	}
	return bankEntity;
};
baseinfo.bankIndex.dealBankBoolean2Dis = function(bankModel){
	var bankEntity = bankModel.copy();
	if(baseinfo.bankIndex.booleanStr.yes === bankEntity.get('intraDayType') ){
		bankEntity.set('intraDayType',baseinfo.bankIndex.booleanType.yes);
	}
	if(baseinfo.bankIndex.booleanStr.no === bankEntity.get('intraDayType') ){
		bankEntity.set('intraDayType',baseinfo.bankIndex.booleanType.no);
	}
	return bankEntity;
};
//------------------------------------MODEL----------------------------------
//用来存储交互“银行”的数据库对应实体MODEL
Ext.define('Foss.baseinfo.bankIndex.BankModel', {
extend: 'Ext.data.Model',
fields : [//银行编码
    {name:'code',type:'string'},
    //银行名称
    {name:'name',type:'string'},
    //上级银行
    {name:'parentBank',type:'string'},
    //省份
    {name:'provId',type:'string'},
    //省份
    {name:'provName',type:'string'},
    //城市
    {name:'cityCode',type:'string'},
    //城市
    {name:'cityName',type:'string'},
    //是否总行
    {name:'headOffice',type:'string'},
    //是否支持即日退
    {name:'intraDayType',type:'string'},
    //是否启用
    {name:'active',type:'string'}]
});
//------------------------------------STORE----------------------------------
//银行STORE
Ext.define('Foss.baseinfo.bankIndex.BankStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.baseinfo.bankIndex.BankModel',
	pageSize:20,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url : baseinfo.realPath('queryBanks.action'),
		reader : {
			type : 'json',
			root : 'bankVo.bankList',
			totalProperty : 'totalCount'
		}
	}
});
//------------------------------------FORM----------------------------------
//银行 查询条件
Ext.define('Foss.baseinfo.bankIndex.QueryBankForm', {
	extend : 'Ext.form.Panel',
	title: baseinfo.bankIndex.i18n('foss.baseinfo.queryCondition'),
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
        columns: 3
    },
    record:null,												//绑定的model Foss.baseinfo.bankIndex.BankModel
	constructor : function(config) {							//构造器
		var me = this,cfg = Ext.apply({}, config);
		me.items = me.getItems();
		me.callParent([cfg]);
		me.loadRecord(Ext.isEmpty(me.record)?Ext.create('Foss.baseinfo.bankIndex.BankModel'):me.record);
	},
	getItems:function(){
		var me = this;
		return [{
			fieldLabel:baseinfo.bankIndex.i18n('foss.baseinfo.bank.bankName'),							//银行名称
			name:'name'
		},{
	        fieldLabel: baseinfo.bankIndex.i18n('foss.baseinfo.bank.bankCode'),							//银行编码
			name:'code'
		},{
			xtype:'combo',
			fieldLabel:baseinfo.bankIndex.i18n('foss.baseinfo.bank.sameDayRetreat'),						//是否支持即日退
			name: 'intraDayType',
	    	//labelSeparator:'',
			displayField:'name',
			valueField:'code',
	    	store:Ext.create('Ext.data.Store',{
	    		fields: ['code', 'name'],
	    		data:[{'code':baseinfo.bankIndex.booleanType.all, 'name':'全部'},
	    		        {'code':baseinfo.bankIndex.booleanType.yes, 'name':baseinfo.bankIndex.i18n('foss.baseinfo.yes')},
	    		        {'code':baseinfo.bankIndex.booleanType.no, 'name':baseinfo.bankIndex.i18n('foss.baseinfo.no')}
	    		       ]
	    	})
		}];
	},
	buttons : [ {
		text : baseinfo.bankIndex.i18n('foss.baseinfo.query'),
		cls:'yellow_button',
		disabled:!baseinfo.bankIndex.isPermission('bankIndex/bankQueryButton'),
		hidden:!baseinfo.bankIndex.isPermission('bankIndex/bankQueryButton'),
		handler : function() {
		    //TODO 查询条件是否全部可为空
			var grid  = Ext.getCmp('T_baseinfo-bankIndex_content').getQueryGrid();//得到grid
			grid.getPagingToolbar().moveFirst();//用分页的moveFirst()方法
		}
	}, {
		text : baseinfo.bankIndex.i18n('foss.baseinfo.reset'),
		cls:'yellow_button',
		disabled:!baseinfo.bankIndex.isPermission('bankIndex/bankQueryButton'),
		hidden:!baseinfo.bankIndex.isPermission('bankIndex/bankQueryButton'),
		handler : function() {
			this.up('form').getForm().reset();
		}
	}]
});
//银行 窗体 form
Ext.define('Foss.baseinfo.bankIndex.BankWinForm', {
	extend : 'Ext.form.Panel',
	frame: true,
	defaultType : 'textfield',
	layout:'column',
    record:null,												//绑定的model Foss.baseinfo.bankIndex.BankModel
	constructor : function(config) {							//构造器
		var me = this,cfg = Ext.apply({}, config);
		me.defaults = me.getDefaults();
		me.items = me.getItems();
		me.callParent([cfg]);
		me.loadRecord(Ext.isEmpty(me.record)?Ext.create('Foss.baseinfo.bankIndex.BankModel'):me.record);
	},
	getDefaults:function(){
		return {
	    	margin : '8 10 5 10',
	    	//labelSeparator:'',
	    	labelWidth:110,
	    	readOnly:true
	    };
	},
	getItems:function(){
		var me = this;
		return [{
	        fieldLabel: baseinfo.bankIndex.i18n('foss.baseinfo.code'),									//银行编码
			name:'code'
		},{
			fieldLabel:baseinfo.bankIndex.i18n('foss.baseinfo.bank.bankName'),								//银行名称
			name:'name'
		},{
			fieldLabel:baseinfo.bankIndex.i18n('foss.baseinfo.airagencycompany.province'),									//省份
			name:'provName'
		},{
			fieldLabel:baseinfo.bankIndex.i18n('foss.baseinfo.airagencycompany.city'),									//城市
			name:'cityName'
		},{
			xtype:'checkboxfield',
			fieldLabel:' ',
			labelWidth:110,
			boxLabel:baseinfo.bankIndex.i18n('foss.baseinfo.bank.sameDayRetreat'),							//是否支持即日退
			name:'intraDayType',
	    	readOnly:baseinfo.bankIndex.readOnly
		},{
			fieldLabel:baseinfo.bankIndex.i18n('foss.baseinfo.airagencycompany.province'),									//省份
			name:'provId',hidden:true
		},{
			fieldLabel:baseinfo.bankIndex.i18n('foss.baseinfo.airagencycompany.city'),									//城市
			name:'cityCode',hidden:true
		}];
	}
});
//------------------------------------GRID----------------------------------
/**
 * 银行查询grid
 */
Ext.define('Foss.baseinfo.bankIndex.QueryBankGrid', {
	extend: 'Ext.grid.Panel',
	title : baseinfo.bankIndex.i18n('foss.baseinfo.airagencycompany.resultList'),
    sortableColumns:false,
    enableColumnHide:false,
    enableColumnMove:false,
    bodyCls:'autoHeight',
	stripeRows : true, 									// 交替行效果
	selType : "rowmodel", 								// 选择类型设置为：行选择
	emptyText: baseinfo.bankIndex.i18n('foss.baseinfo.queryResultIsNull'),							//查询结果为空
	frame: true,
	//得到bbar（分页）
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
	//得到银行编辑窗体
	getAgencyDeptWin:function(win,title,viewState,param){
		var formRecord = Ext.isEmpty(param.formRecord)?Ext.create('Foss.baseinfo.bankIndex.BankModel'):param.formRecord;
		var gridData = Ext.isEmpty(param.gridDate)?[]:param.gridDate;
		if (Ext.isEmpty(win)) {
			win = Ext.create('Foss.baseinfo.bankIndex.BankWin',{
				'title':title,
				'viewState':viewState,
				'sourceGrid':this,
				'formRecord':formRecord,
				'gridDate':gridData
			});
		}
		//加载数据
		win.editForm.loadRecord(baseinfo.bankIndex.dealBankDis2Boolean(formRecord));
		return win;
	},
	//得到银行编辑窗体,查看状态viewState："ADD"新增,"UPDATE"修改,"VIEW"查看
	getBankWin:function(viewState,param){
		if(baseinfo.bankIndex.viewBankState.update === viewState){
			baseinfo.bankIndex.readOnly = false;
			return this.getAgencyDeptWin(this.updateBankWin,baseinfo.bankIndex.i18n('foss.baseinfo.bank.alterBank'),viewState,param);
		}
	},
	updateBankWin:null,						//修改基银行
	updateBank:function(param){
		return this.getBankWin(baseinfo.bankIndex.viewBankState.update,param);
	},
	constructor : function(config){
		var me = this, cfg = Ext.apply({}, config);
		me.columns = me.getColumns();
		me.store = me.getStore();
		me.listeners = me.getMyListeners();
	    //添加多选框
		me.selModel = Ext.create('Ext.selection.CheckboxModel',{mode:'MULTI',checkOnly:true});
		//添加分页控件
		me.bbar = me.getPagingToolbar();
		//设置分页控件的store属性
		me.getPagingToolbar().store = me.store;
		me.callParent([cfg]);
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
	    	}
	    };
	},
	getStore:function(){
		return Ext.create('Foss.baseinfo.bankIndex.BankStore',{
			autoLoad : false,
			pageSize : 20,
			listeners: {
				beforeload: function(store, operation, eOpts){
					var queryForm = Ext.getCmp('T_baseinfo-bankIndex_content').getQueryForm().getForm();//得到查询的FORM表单
					queryForm.updateRecord(queryForm.record);
					var bankEntity = queryForm.record.getData();
					if(queryForm!=null){
						var intraDayType = queryForm.findField('intraDayType').getValue();
						Ext.apply(operation,{
							params : {
								//银行编码
								'bankVo.bankEntity.code':queryForm.findField('code').getValue(),
								//银行名称
								'bankVo.bankEntity.name':queryForm.findField('name').getValue(),
								//是否支持即日退
								'bankVo.bankEntity.intraDayType':(baseinfo.bankIndex.booleanType.all === intraDayType)?'':intraDayType
							}
						});	
					}
				}
		    }
		});
	},
	getColumns:function(){
		var me = this;
		return [{
			align : 'center',
			xtype : 'actioncolumn',
			text : baseinfo.bankIndex.i18n('foss.baseinfo.operate'),//操作
			items: [{
            	iconCls:'deppon_icons_edit',
            	disabled:!baseinfo.bankIndex.isPermission('bankIndex/bankEditButton'),
                tooltip: baseinfo.bankIndex.i18n('foss.baseinfo.update'),
                getClass : function (v, m, r, rowIndex) {
					var valid = null;
					if(!Ext.isEmpty(r.get('headOffice'))){
						if (r.get('headOffice') == 'Y') {
							return 'deppon_icons_edit';
						} else {
						    return 'statementBill_hide';
						}
					}else{
						return 'statementBill_hide';
					}
				},
                handler: function(grid, rowIndex, colIndex) {
    				var param = {};
                	var record = grid.getStore().getAt(rowIndex);				//选中的计费规则数据
                	param.formRecord = record;
    				me.updateBank(param).show();
    			}
            }]
		},{
			//TODO 银行编码  超链接
			text : baseinfo.bankIndex.i18n('foss.baseinfo.bank.bankCode'),									//代理编码
			width : 150,
			dataIndex : 'code'
		},{
			text : baseinfo.bankIndex.i18n('foss.baseinfo.bank.bankName'),									//银行名称
			dataIndex : 'name',
			width : 200
		},{
			text : baseinfo.bankIndex.i18n('foss.baseinfo.airagencycompany.province'),										//省份
			width : 150,
			dataIndex : 'provName'
		},{
			text : baseinfo.bankIndex.i18n('foss.baseinfo.airagencycompany.city'),										//城市
			width : 150,
			dataIndex : 'cityName'
		},{
			text : baseinfo.bankIndex.i18n('foss.baseinfo.bank.sameDayRetreat'),								//是否支持即日退
			dataIndex : 'intraDayType',
			renderer:function(v,object,record){
				var headOffice = record.get('headOffice');
				if(headOffice == 'Y'){
					if(baseinfo.bankIndex.booleanType.yes === v || baseinfo.bankIndex.booleanStr.yes === v){
						return baseinfo.bankIndex.i18n('foss.baseinfo.yes');
					}else if(baseinfo.bankIndex.booleanType.no === v || baseinfo.bankIndex.booleanStr.no === v){
						return baseinfo.bankIndex.i18n('foss.baseinfo.no');
					}
					return v;
				}else{
					return '';
				}
				
			}
		}];
	}
});
//------------------------------------ONREADY----------------------------------
/**
 * 程序入口方法
 */
Ext.onReady(function() {
	Ext.QuickTips.init();
	if (Ext.getCmp('T_baseinfo-bankIndex_content')){
		return;
	}
	var queryForm  = Ext.create('Foss.baseinfo.bankIndex.QueryBankForm',{'record':Ext.create('Foss.baseinfo.bankIndex.BankModel')});//查询FORM
	var queryGrid  = Ext.create('Foss.baseinfo.bankIndex.QueryBankGrid');//查询结果显示列表
	Ext.getCmp('T_baseinfo-bankIndex').add(Ext.create('Ext.panel.Panel', {
		id : 'T_baseinfo-bankIndex_content',
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
		items : [ queryForm, queryGrid]
	}));
});
//------------------------------------WINDOW----------------------------------
/**
 * 银行window
 */
Ext.define('Foss.baseinfo.bankIndex.BankWin',{
	extend : 'Ext.window.Window',
	title : baseinfo.bankIndex.i18n('foss.baseinfo.bank.addBank'),								//新增银行   默认新增
	closable : true,
	modal : true,
	resizable:false,
	closeAction : 'hide',
	width :350,
	height :330,	
	layout : 'fit',
	listeners:{
		beforehide:function(me){
			//TODO 清空数据
		}
	},
	editForm:null,											//银行表单Form
	sourceGrid:null,										//来源表格 Grid
	viewState:null,											//查看状态 baseinfo.bankIndex.viewBankState
	formRecord:null,										//银行实体 Foss.baseinfo.BusinessPartnerModel
    constructor : function(config) {
		var me = this,cfg = Ext.apply({}, config);
		me.editForm = Ext.create('Foss.baseinfo.bankIndex.BankWinForm',{'height':260});
		me.items = [me.editForm];
		me.fbar = me.getFbar();
		me.callParent([cfg]);
	},
	//操作界面上的按钮
	getFbar:function(){
		var me = this;
		return [{
			text : baseinfo.bankIndex.i18n('foss.baseinfo.cancel'),
			margin : '0 165 0 0',
			handler :function(){
				me.hide();
			} 
		},{
			text : baseinfo.bankIndex.i18n('foss.baseinfo.save'),
			cls:'yellow_button',
			handler :function(){
		    	var editForm = me.editForm.getForm();
		    	//判断偏线代理必填项是否填写并全部填写合法
		    	if(editForm.isValid()){
		    		editForm.updateRecord(me.formRecord);
		    		baseinfo.bankIndex.submitBank(me,me.viewState,baseinfo.bankIndex.dealBankBoolean2Dis(me.formRecord).data,me.sourceGrid);
		    	}else{
		    		baseinfo.showInfoMsg(baseinfo.bankIndex.i18n('foss.baseinfo.airagencycompany.checkData'));
		    	}
			}
		}];
	}
});

