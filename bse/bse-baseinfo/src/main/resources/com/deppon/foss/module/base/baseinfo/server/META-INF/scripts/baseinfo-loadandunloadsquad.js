/**
 * 装卸车小队model									Foss.baseinfo.loadAndUnloadSquad.LoadAndUnloadSquadEntityModel
 * 装卸车小队store									Foss.baseinfo.loadAndUnloadSquad.LoadAndUnloadSquadEntityStore
 * 装卸车小队form									Foss.baseinfo.loadAndUnloadSquad.QueryConditionForm
 * 装卸车小队grid									Foss.baseinfo.loadAndUnloadSquad.QueryResultGrid
 * 装卸车小队winForm									Foss.baseinfo.loadAndUnloadSquad.LoadAndUnloadSquadEntityWinForm
 * 装卸车小队winGrid									Foss.baseinfo.loadAndUnloadSquad.LoadAndUnloadSquadEntityWinGrid
 * 装卸车小队win										Foss.baseinfo.loadAndUnloadSquad.LoadAndUnloadSquadEntityWin
 */

//------------------------------------常量和公用方法----------------------------------
baseinfo.loadAndUnloadSquad.sourcePorterArr = [];				//理货员 初始数据model集合 
baseinfo.loadAndUnloadSquad.oprPorterArr = [];					//理货员 操作数据model集合
baseinfo.loadAndUnloadSquad.addPorterArr = [];					//理货员 新增数据model集合
baseinfo.loadAndUnloadSquad.delPorterArr = [];					//理货员 删除数据model集合
baseinfo.loadAndUnloadSquad.arrOprType = {opr:'OPR'};			//model集合 操作类型  OPR为操作数据类型的集合
baseinfo.loadAndUnloadSquad.init = {init:'INIT'};			//model集合 操作类型  OPR为操作数据类型的集合

//信息
baseinfo.showInfoMsg = function(message,fun) {
	var len = message.length;
	Ext.Msg.show({
	    title:baseinfo.loadAndUnloadSquad.i18n('i18n.baseinfo-util.fossAlert'),
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
//初始化界面数据 
baseinfo.loadAndUnloadSquad.initWinData = function(win,viewState,formRecord,gridData,levelType){
	//装卸车小队 form 加载数据
	win.down('form').loadRecord(formRecord);
	//TODO 上级别部门
	win.down('form').down('dynamicorgcombselector').setCombValue(formRecord.get('parentOrgName'),formRecord.get('parentOrgCode'));
	// 理货员 数据
	if(baseinfo.levelType.p === levelType){
		var store = win.down('grid').store;
		//store.removeAll();//清空store原来数据
		if(baseinfo.viewState.add != viewState){
			store.load();//store已经load加载过数据 不必要再 重新加一遍
			store.on('load',function(s,records){   
		        s.each(function(record){
					baseinfo.loadAndUnloadSquad.sourcePorterArr.push(record);
					Ext.Array.push(baseinfo.loadAndUnloadSquad.oprPorterArr,record);
					baseinfo.loadAndUnloadSquad.addPorter(win.down('form').down('container'),record,null,viewState,baseinfo.loadAndUnloadSquad.init);
				});   
			});  
			//将值赋值给理货员初始化数据
			win.sourcePorterData = store.data;  
		}
	}
	return win;
};
//选择理货员 界面增加 panel事件 
baseinfo.loadAndUnloadSquad.addPorter = function(parentContainer,childRecord,store,viewState,initType){
	//当store现有数据中没有选择的该理货员时 进行新增
	var childContainer = Ext.create('Foss.baseinfo.loadAndUnloadSquad.PortersContainer',{
		record:childRecord,store:store,viewState:viewState
	});
	// 判断 界面打开后 源 队员是否存在 该成员
	if(!baseinfo.loadAndUnloadSquad.sourceHasPorter(childRecord)
			&& !baseinfo.loadAndUnloadSquad.arrayHasRecord(childRecord,baseinfo.loadAndUnloadSquad.addPorterArr)){
		baseinfo.loadAndUnloadSquad.addPorterArr.push(childRecord);
	}
	// 界面 打开时 添加 该数据
	if(!Ext.isEmpty(initType) && baseinfo.loadAndUnloadSquad.init === initType){
		// 新增一条数据展示
		parentContainer.add(childContainer);
	}
	// 判断 界面上 显示的 队员是否存在 该成员
	if(!baseinfo.loadAndUnloadSquad.sourceHasPorter(childRecord,baseinfo.loadAndUnloadSquad.arrOprType.opr)){
		Ext.Array.push(baseinfo.loadAndUnloadSquad.oprPorterArr,childRecord);
		// 新增一条数据展示
		parentContainer.add(childContainer);
	}
};
//保存事件 
baseinfo.loadAndUnloadSquad.submitLoadAndUnloadSquadEntity = function(win,viewState,operatEntity,levelType){
	var grid = Ext.getCmp('T_baseinfo-loadAndUnloadSquadIndex_content').getQueryGrid()
		,aType = baseinfo.levelType.p === levelType,url = '../baseinfo/addLoadAndUnloadSquad.action'
		,m_success = baseinfo.loadAndUnloadSquad.i18n('foss.baseinfo.saveSuccess'),m_failure = baseinfo.loadAndUnloadSquad.i18n('foss.baseinfo.airagencycompany.saveFail'),m_dateError = baseinfo.loadAndUnloadSquad.i18n('foss.baseinfo.airagencycompany.dataError')								//数据异常！
		,objectVo = {},store = win.down('grid').store,addArr = [],delArr = [];
	operatEntity.porters = [];//制空 理货员对象以防后台映射 报错
	objectVo.loadAndUnloadSquadEntity = operatEntity;
	if(baseinfo.viewState.add === viewState){
		addArr = baseinfo.loadAndUnloadSquad.changeRecords2Pojos(baseinfo.loadAndUnloadSquad.addPorterArr,addArr,baseinfo.viewState.add);
	}else if(baseinfo.viewState.update === viewState){
		//修改URL
		url = '../baseinfo/updateLoadAndUnloadSquad.action';
		addArr = baseinfo.loadAndUnloadSquad.changeRecords2Pojos(baseinfo.loadAndUnloadSquad.addPorterArr,addArr,baseinfo.viewState.add);
		delArr = baseinfo.loadAndUnloadSquad.changeRecords2Pojos(baseinfo.loadAndUnloadSquad.delPorterArr,delArr,baseinfo.viewState.update);
	}
	objectVo.porterEntityList = addArr;//新增 理货员实体LIST
	objectVo.codeStr = delArr;//删除 理货员实体LIST
	baseinfo.requestAjaxJson(url,{'objectVo':objectVo},function(result){
		if(!Ext.isEmpty(result.objectVo.loadAndUnloadSquadEntity)){
			grid.store.loadPage(1);
			//TODO 保存按钮 变为可用
			baseinfo.showInfoMsg(m_success);
			win.hide();
		}else{
			baseinfo.showInfoMsg(result.message);
			baseinfo.formReset([me.editForm.getForm()],[me.formRecord]);
		}
	},function(result){
		baseinfo.showInfoMsg(result.message);
	});
};
//判断 初始 界面中 有没有 baseinfo.loadAndUnloadSquad.arrOprType.OPR,默认为 初始数据 集合类型
baseinfo.loadAndUnloadSquad.sourceHasPorter = function(record,opr){
	var i = 0,arr = Ext.isEmpty(opr)?baseinfo.loadAndUnloadSquad.sourcePorterArr:baseinfo.loadAndUnloadSquad.oprPorterArr;
	return baseinfo.loadAndUnloadSquad.arrayHasRecord(record,arr);
};
baseinfo.loadAndUnloadSquad.arrayHasRecord = function(record,arr){
	for(var i = 0;!Ext.isEmpty(arr)&&i<arr.length;i++){
		if(arr[i].get('empCode') === record.get('empCode')){
			return true;
		}
	}
	return false;
};
//传入record返回对应实体对象
baseinfo.loadAndUnloadSquad.changeRecords2Pojos = function(records,opraArray,viewState){
	for(var i = 0;!Ext.isEmpty(records)&&i<records.length;i++){
		(baseinfo.viewState.add === viewState)?opraArray.push(records[i].data):opraArray.push(records[i].get('empCode'));
	}
	return opraArray;
};
//作废事件
baseinfo.loadAndUnloadSquad.deleteLoadAndUnloadSquadEntityByCode = function(delAgencyCompanyType,operatRecord){
	var grid = Ext.getCmp('T_baseinfo-loadAndUnloadSquadIndex_content').getQueryGrid(),
		url = '../baseinfo/deleteLoadAndUnloadSquad.action',
		objectVo = {};
	selection=grid.getSelectionModel().getSelection();
	if(selection.length<=0 && Ext.isEmpty(operatRecord)){
		Ext.MessageBox.alert(baseinfo.loadAndUnloadSquad.i18n('foss.baseinfo.airagencycompany.remind'),baseinfo.loadAndUnloadSquad.i18n('foss.baseinfo.airagencycompany.selectData'));
	}else{	
		if(!Ext.isEmpty(delAgencyCompanyType)&&baseinfo.delAgencyType===delAgencyCompanyType){
			var codeStr = [];
			//批量作废
			url = '../baseinfo/deleteLoadAndUnloadSquadMore.action';
			for (var j = 0; j < selection.length; j++) {
				codeStr.push(selection[j].get('code'));
			}
			objectVo.codeStr = codeStr;
		}else{
			objectVo.loadAndUnloadSquadEntity = operatRecord.data;
		}
		Ext.MessageBox.buttonText.yes = baseinfo.loadAndUnloadSquad.i18n('foss.baseinfo.sure');
		Ext.MessageBox.buttonText.no = baseinfo.loadAndUnloadSquad.i18n('foss.baseinfo.cancel');
		Ext.Msg.confirm(baseinfo.loadAndUnloadSquad.i18n('foss.baseinfo.billAdvertisingSlogan.fossAlertU'),baseinfo.loadAndUnloadSquad.i18n('foss.baseinfo.billAdvertisingSlogan.confirmAlertRecord'),function(btn,text) {
			if (btn == 'yes') {
				baseinfo.requestAjaxJson(url,{'objectVo':objectVo},function(result){
					grid.store.loadPage(1);
					baseinfo.showInfoMsg(baseinfo.loadAndUnloadSquad.i18n('foss.baseinfo.deleteSuccess'));
				},function(result){
					baseinfo.showInfoMsg(result.message);
				});
			}
		});
	}
};
baseinfo.loadAndUnloadSquad.prohibitedArticlesIsExist = function(field,fieldValue,fieldLabel,fieldNmae){
	var url = '../baseinfo/limitedwarrantygoodsIsExist.action',objectVo ={}
	,entytyRecord = Ext.create('Foss.baseinfo.loadAndUnloadSquad.LoadAndUnloadSquadEntityModel');
	entytyRecord.set(fieldNmae+'',fieldValue);
	objectVo.loadAndUnloadSquadEntityList = entytyRecord.data;
	baseinfo.requestAjaxJson(url,{'objectVo':objectVo},function(result){
		if(Ext.isEmpty(result.objectVo.loadAndUnloadSquadEntity)){
			field.clearInvalid();
		}else{
			field.markInvalid(baseinfo.loadAndUnloadSquad.i18n('foss.baseinfo.airagencycompany.dataRepeatBegin')+fieldLabel+baseinfo.loadAndUnloadSquad.i18n('foss.baseinfo.airagencycompany.dataRepeatEnd'));
		}
	},function(result){
		field.markInvalid(baseinfo.loadAndUnloadSquad.i18n('foss.baseinfo.airagencycompany.dataRepeatBegin')+fieldLabel+baseinfo.loadAndUnloadSquad.i18n('foss.baseinfo.airagencycompany.dataRepeatEnd'));
	});
};

//------------------------------------MODEL----------------------------------
//装卸车小队Model
Ext.define('Foss.baseinfo.loadAndUnloadSquad.LoadAndUnloadSquadEntityModel', {
extend: 'Ext.data.Model',
fields : [//装卸车小队名称
      {name:'name',type:'string'},
      //装卸车小队编码
      {name:'code',type:'string'},
      //理货业务类型
      {name:'arrangeBizType',type:'string'},
      //上级部门
      {name:'parentOrgCode',type:'string'},
      //上级部门
      {name:'parentOrgName',type:'string'},
      //队员
      {name:'porters',defaultType:[]},
      //是否启用
      {name:'active',type:'string'}]
});
//理货员Model
Ext.define('Foss.baseinfo.loadAndUnloadSquad.PorterEntityModel', {
extend: 'Ext.data.Model',
fields : [//工号
      {name:'empCode',type:'string'},
      //姓名
      {name:'empName',type:'string'},
      //拼音
      {name:'pinyin',type:'string'},
      //类型
      {name:'type',type:'string'},
      //装卸车小队
      {name:'parentOrgCode',type:'string'},
      //是否启用
      {name:'active',type:'string'}]
});
//------------------------------------STORE----------------------------------
//装卸车小队STORE
Ext.define('Foss.baseinfo.loadAndUnloadSquad.LoadAndUnloadSquadEntityStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.baseinfo.loadAndUnloadSquad.LoadAndUnloadSquadEntityModel',
	pageSize:20,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url : "../baseinfo/queryLoadAndUnloadSquadExactByEntity.action",
		reader : {
			type : 'json',
			root : 'objectVo.loadAndUnloadSquadEntityList',
			totalProperty : 'totalCount'
		}
	}
});
//理货员STORE
Ext.define('Foss.baseinfo.loadAndUnloadSquad.PorterEntityStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.baseinfo.loadAndUnloadSquad.PorterEntityModel',
	pageSize:20,
	proxy : {
		type:'ajax',
		api:{
			read:'../baseinfo/queryPorterBatchByParentOrgCode.action'
		},
		actionMethods:'POST',
		reader : {
			type : 'json',
			root : 'objectVo.porterEntityList'
		}
	}
});
//------------------------------------FORM----------------------------------
//装卸车小队 查询条件
Ext.define('Foss.baseinfo.loadAndUnloadSquad.QueryConditionForm', {
	extend : 'Ext.form.Panel',
	title: baseinfo.loadAndUnloadSquad.i18n('foss.baseinfo.queryCondition'),
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
        columns: 4
    },
    record:null,												//绑定的model Foss.baseinfo.loadAndUnloadSquad.LoadAndUnloadSquadEntityModel
	constructor : function(config) {							//构造器
		var me = this,cfg = Ext.apply({}, config);
		me.items = me.getItems();
		me.callParent([cfg]);
	},
	getItems:function(){
		var me = this;
		return [{
			fieldLabel:baseinfo.loadAndUnloadSquad.i18n('foss.baseinfo.loadAndUnloadSquadCode'),							//装卸车小队编码
			name:'code'
		},{
			fieldLabel:baseinfo.loadAndUnloadSquad.i18n('foss.baseinfo.loadAndUnloadSquadName'),							//装卸车小队名称
			name:'name'
		},{
			xtype:'dynamicorgcombselector',
			fieldLabel:baseinfo.loadAndUnloadSquad.i18n('foss.baseinfo.superiorDeptName'),							//上级部门名称
			isArrangeGoods:'Y',
			name:'parentOrgCode'
		}];
	},
	buttons : [ {
		text : baseinfo.loadAndUnloadSquad.i18n('foss.baseinfo.reset'),
		cls:'yellow_button',
		disabled:!baseinfo.loadAndUnloadSquad.isPermission('loadAndUnloadSquadIndex/loadAndUnloadSquadIndexQueryButton'),
		hidden:!baseinfo.loadAndUnloadSquad.isPermission('loadAndUnloadSquadIndex/loadAndUnloadSquadIndexQueryButton'),
		handler : function() {
			this.up('form').getForm().reset();
		}
	}, {
		text : baseinfo.loadAndUnloadSquad.i18n('foss.baseinfo.query'),
		disabled:!baseinfo.loadAndUnloadSquad.isPermission('loadAndUnloadSquadIndex/loadAndUnloadSquadIndexQueryButton'),
		hidden:!baseinfo.loadAndUnloadSquad.isPermission('loadAndUnloadSquadIndex/loadAndUnloadSquadIndexQueryButton'),
		cls:'yellow_button',
		handler : function() {
			var grid  = Ext.getCmp('T_baseinfo-loadAndUnloadSquadIndex_content').getQueryGrid();//得到grid
			grid.store.loadPage(1);//用分页的moveFirst()方法
		}
	}]
});
//装卸车小队 界面form
Ext.define('Foss.baseinfo.loadAndUnloadSquad.LoadAndUnloadSquadEntityWinForm', {
	extend : 'Ext.form.Panel',
	defaultType : 'textfield',
//	autoScroll:true,
	layout:{
        type: 'table',
        columns: 3
    },
    formRecord:null,												//绑定的model Foss.baseinfo.loadAndUnloadSquad.LoadAndUnloadSquadEntityModel
    formStore:null,													//绑定的formStore Foss.baseinfo.loadAndUnloadSquad.PorterEntityStore
    porterStore:null,												//理货员store 利用store的api操作新增和删除的装卸车小队的人员
    viewState:null,
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
			allowBlank:true,
//			width:300,
			readOnly:(baseinfo.viewState.view === config.viewState)?true:false
	    };
	},
	getPorterStore:function(config){
		var me = this;
		//单例模式获得 装卸车小队的 理货员
		if(me.porterStore === null){
			me.porterStore = Ext.create('Foss.baseinfo.loadAndUnloadSquad.PorterEntityStore',{
				autoLoad : false,
				listeners: {
					beforeload: function(store, operation, eOpts){
						Ext.apply(operation,{
							params : {
								//装卸车小队编码
								'objectVo.codeStr':config.formRecord.get('code')
							}
						});	
					}
			    }
			});
		}
		return me.porterStore;
	},
	getItems:function(config){
		var me = this;
		return [{
			fieldLabel:baseinfo.loadAndUnloadSquad.i18n('foss.baseinfo.loadAndUnloadSquadCode'),							//装卸车小队编码
			name:'code',
			regex: /^[A-Z]{1,}-[A-Z]{0,}\d{1,}-{0,}\d{0,}$/,
			regexText : baseinfo.loadAndUnloadSquad.i18n('foss.baseinfo.pleaseOnTheRuleNamed'),  //请按规则命名！
			//readOnly:(baseinfo.viewState.add != config.viewState)?true:false,
			allowBlank:false
		},{
			fieldLabel:baseinfo.loadAndUnloadSquad.i18n('foss.baseinfo.loadAndUnloadSquadName'),							//装卸车小队名称
			name:'name',
			allowBlank:false
		},FossDataDictionary.getDataDictionaryCombo('TFR_LOAD_UNLOAD_TYPE',{
			fieldLabel:baseinfo.loadAndUnloadSquad.i18n('foss.baseinfo.loadandunloadsquad.loadUnloadTruckType'),								//装卸车类型
			name: 'arrangeBizType',
			readOnly:baseinfo.viewState.view === config.viewState,
			allowBlank:false
		}),{
			xtype:'dynamicorgcombselector',
			fieldLabel:baseinfo.loadAndUnloadSquad.i18n('foss.baseinfo.superiorDeptName'),							//上级部门名称
			isArrangeGoods:'Y',
			name:'parentOrgCode',
			allowBlank:false,
	    	listeners:{
	    		select:function(field,records){
	    			if(!Ext.isEmpty(records)){
	    				me.setDeptIsChange(true);
	    			}
	    		}
	    	}
		},{
			xtype:'commonemployeeselector',
			colspan:2,
			fieldLabel:baseinfo.loadAndUnloadSquad.i18n('foss.baseinfo.selectEmployee'),
	    	labelWidth:110,
			allowBlank:true,
	    	listeners:{
	    		select:function(field,records){
	    			if(!Ext.isEmpty(records)){
	    				baseinfo.loadAndUnloadSquad.addPorter(me.query('container')[0],records[0],me.getPorterStore(config),config.viewState);
	    		         field.setValue(null);
	    			}
	    		},
	    		expand:function(field){
	    			me.getEmp(field);
	    		},
	    		specialkey: function(field, e){
	    			me.getEmp(field);
	    		}
	    	},
			listConfig: {
				loadMask:false
			}
		},{
			colspan:3,
			xtype:'container',
			layout:'column',//队员
			name:'porters',
			items:[{
				xtype:'label',
				text:baseinfo.loadAndUnloadSquad.i18n('foss.baseinfo.loadandunloadsquad.player')
			}]
		},{
			colspan:3,
			xtype:'label',
			text:baseinfo.loadAndUnloadSquad.i18n('foss.baseinfo.loadAndUnloadSquadNomenclatureRule')
		},{
			colspan:3,
			xtype:'label',
			height:40,
			text:'小队所属的外场的名称简称+“-”+上级组织的类型简称+上级组织小组序号（两位数字）+“-”+小队序号（两位数字），eg:上海外场卸车三组四队 SH-XC03-04，上海浦东卸车二组五队 SHPD-XC02-05'
		},{
			//方便寻找到 理货员store 增加一个隐藏表格grid
			xtype:'grid',
			hidden:true,
			store:me.getPorterStore(config),
			columns: []
		}
//		,FossDataDictionary.getDataDictionaryCombo('TFR_LOAD_UNLOAD_TYPE',{
//			fieldLabel:baseinfo.loadAndUnloadSquad.i18n('foss.baseinfo.loadandunloadsquad.truckType'),								//车队类型
////			name: 'goodsType',
//	    	labelWidth:110
//		})
		];
	},
	getEmp:function(field){
		var me = this,parentOrgCode = field.up('form').getForm().findField('parentOrgCode').getValue();
		field.store.on('beforeload',function(store, operation, eOpts){
			var params = operation.params;
			params['employeeVo.employeeDetail.orgCode'] = Ext.isEmpty(parentOrgCode)?baseinfo.loadAndUnloadSquad.i18n('foss.baseinfo.isNull'):parentOrgCode;
			Ext.applyIf(operation, {
				params : params
			});
		});
		if(me.getDetpIsChange()){
			if(Ext.isEmpty(parentOrgCode)){
				field.store.loadData([]);
			}else{
				field.store.load();
			}
		}
	},
	chage:false,
	setDeptIsChange:function(v){
		this.chage = v;
	},
	getDetpIsChange:function(){
		return this.chage;
	}
});
//理货员 负载container
Ext.define('Foss.baseinfo.loadAndUnloadSquad.PortersContainer', {
	extend : 'Ext.form.Panel',
    record:null,												//绑定的model Foss.baseinfo.loadAndUnloadSquad.LoadAndUnloadSquadEntityModel
    store:null,													//绑定的Store Foss.baseinfo.loadAndUnloadSquad.LoadAndUnloadSquadEntityStore
	layout:'column',
	viewState:null,												//状态
    constructor : function(config) {							//构造器
		var me = this,cfg = Ext.apply({}, config);
		me.items = me.getItems(config);
		me.callParent([cfg]);
	},
	getItems:function(config){
		var me = this;
		return [{xtype:'label',text:'('+config.record.get('empCode')+')'+config.record.get('empName')}
		,{xtype:'button',cls:'delSBtn',height:13,tooltip: baseinfo.loadAndUnloadSquad.i18n('foss.baseinfo.loadandunloadsquad.delete'),
            disabled:(baseinfo.viewState.view === config.viewState)?true:false,
		    handler: function(){
		    	me.hide();
		    	if(baseinfo.loadAndUnloadSquad.sourceHasPorter(config.record)){
		    		baseinfo.loadAndUnloadSquad.delPorterArr.push(config.record);
		    	}
				Ext.Array.remove(baseinfo.loadAndUnloadSquad.oprPorterArr,record);
		    	config.store.remove(config.record);//理货员store中移除该数据
		    }}];
	}
});
//------------------------------------GRID----------------------------------
//装卸车小队 查询结果grid
Ext.define('Foss.baseinfo.loadAndUnloadSquad.QueryResultGrid', {
	extend: 'Ext.grid.Panel',
	title : baseinfo.loadAndUnloadSquad.i18n('foss.baseinfo.airagencycompany.resultList'),
//    sortableColumns:false,
    enableColumnHide:false,
    enableColumnMove:false,
	stripeRows : true, 									// 交替行效果
	selType : "rowmodel", 								// 选择类型设置为：行选择
	emptyText: baseinfo.loadAndUnloadSquad.i18n('foss.baseinfo.queryResultIsNull'),							//查询结果为空
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
			text : baseinfo.loadAndUnloadSquad.i18n('foss.baseinfo.add'),								//新增
			disabled:!baseinfo.loadAndUnloadSquad.isPermission('loadAndUnloadSquadIndex/loadAndUnloadSquadIndexAddButton'),
			hidden:!baseinfo.loadAndUnloadSquad.isPermission('loadAndUnloadSquadIndex/loadAndUnloadSquadIndexAddButton'),
			//hidden:!pricing.isPermission('../pricing/saveRole.action'),
			handler :function(){
				me.addLoadAndUnloadSquadEntity({}).show();
			} 
		},'-', {
			text : baseinfo.loadAndUnloadSquad.i18n('foss.baseinfo.void'),								//作废
			disabled:!baseinfo.loadAndUnloadSquad.isPermission('loadAndUnloadSquadIndex/loadAndUnloadSquadIndexCancelButton'),
			hidden:!baseinfo.loadAndUnloadSquad.isPermission('loadAndUnloadSquadIndex/loadAndUnloadSquadIndexCancelButton'),
			//hidden:!pricing.isPermission('../pricing/deleteRole.action'),
			handler :function(){
				baseinfo.loadAndUnloadSquad.deleteLoadAndUnloadSquadEntityByCode(baseinfo.delAgencyType);
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
	//得到装卸车小队编辑窗体
	getAgencyDeptWin:function(win,title,viewState,param){
		var formRecord = Ext.isEmpty(param.formRecord)?Ext.create('Foss.baseinfo.loadAndUnloadSquad.LoadAndUnloadSquadEntityModel'):param.formRecord;
		if (Ext.isEmpty(win)) {
			win = Ext.create('Foss.baseinfo.loadAndUnloadSquad.LoadAndUnloadSquadEntityWin',{
				'title':title,
				'viewState':viewState,
				'sourceGrid':this,
				'formRecord':formRecord
			});
		}
		//加载数据
		return baseinfo.loadAndUnloadSquad.initWinData(win,viewState,formRecord,[],baseinfo.levelType.p);
	},
	//得到装卸车小队编辑窗体,查看状态viewState："ADD"新增,"UPDATE"修改,"VIEW"查看
	getLoadAndUnloadSquadEntityWin:function(viewState,param){
		if(baseinfo.viewState.add === viewState){
			return this.getAgencyDeptWin(this.addLoadAndUnloadSquadEntityWin,baseinfo.loadAndUnloadSquad.i18n('foss.baseinfo.loadandunloadsquad.addLoadUnloadSquad'),viewState,param);
		}else if(baseinfo.viewState.update === viewState){
			return this.getAgencyDeptWin(this.updateLoadAndUnloadSquadEntityWin,baseinfo.loadAndUnloadSquad.i18n('foss.baseinfo.updateLoadAndUnloadSquad'),viewState,param);
		}else if(baseinfo.viewState.view === viewState){
			return this.getAgencyDeptWin(this.viewLoadAndUnloadSquadEntityWin,baseinfo.loadAndUnloadSquad.i18n('foss.baseinfo.seeLoadAndUnloadSquad'),viewState,param);
		}
	},
	addLoadAndUnloadSquadEntityWin:null,						//新增基偏线代理公司
	addLoadAndUnloadSquadEntity:function(param){
		return this.getLoadAndUnloadSquadEntityWin(baseinfo.viewState.add,param);
	},
	updateLoadAndUnloadSquadEntityWin:null,						//修改基装卸车小队
	updateLoadAndUnloadSquadEntity:function(param){
		return this.getLoadAndUnloadSquadEntityWin(baseinfo.viewState.update,param);
	},
	viewLoadAndUnloadSquadEntityWin:null,						//查看基装卸车小队
	viewLoadAndUnloadSquadEntity:function(param){
		return this.getLoadAndUnloadSquadEntityWin(baseinfo.viewState.view,param);
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
	    	//查看 装卸车小队
	    	itemdblclick: function(view,record) {
				var param = {};
            	param.formRecord = record;
				me.viewLoadAndUnloadSquadEntity(param).show();
	    	}
	    };
	},
	getStore:function(){
		return Ext.create('Foss.baseinfo.loadAndUnloadSquad.LoadAndUnloadSquadEntityStore',{
			autoLoad : false,
			pageSize : 20,
			listeners: {
				beforeload: function(store, operation, eOpts){
					var queryForm = Ext.getCmp('T_baseinfo-loadAndUnloadSquadIndex_content').getQueryForm().getForm();//得到查询的FORM表单
					queryForm.updateRecord(queryForm.record);
					var entity = queryForm.record.data;
					if(queryForm!=null){
						Ext.apply(operation,{
							params : {
								//装卸车小队编码
								'objectVo.loadAndUnloadSquadEntity.code':entity.code,
								//装卸车小队名称
								'objectVo.loadAndUnloadSquadEntity.name':entity.name,
								//上级部门名称
								'objectVo.loadAndUnloadSquadEntity.parentOrgCode':entity.parentOrgCode
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
			text : baseinfo.loadAndUnloadSquad.i18n('foss.baseinfo.operate'),//操作
			items: [{
            	iconCls:'deppon_icons_edit',
                tooltip: baseinfo.loadAndUnloadSquad.i18n('foss.baseinfo.update'),
                disabled:!baseinfo.loadAndUnloadSquad.isPermission('loadAndUnloadSquadIndex/loadAndUnloadSquadIndexUpdateButton'),
                hidden:!baseinfo.loadAndUnloadSquad.isPermission('loadAndUnloadSquadIndex/loadAndUnloadSquadIndexUpdateButton'),
                handler: function(grid, rowIndex, colIndex) {
    				var param = {};
                	param.formRecord = grid.getStore().getAt(rowIndex);
    				me.updateLoadAndUnloadSquadEntity(param).show();
    			}
            },{
            	iconCls:'deppon_icons_cancel',
                tooltip: baseinfo.loadAndUnloadSquad.i18n('foss.baseinfo.void'),
                disabled:!baseinfo.loadAndUnloadSquad.isPermission('loadAndUnloadSquadIndex/loadAndUnloadSquadIndexCancelButton'),
                hidden:!baseinfo.loadAndUnloadSquad.isPermission('loadAndUnloadSquadIndex/loadAndUnloadSquadIndexCancelButton'),
                disabled:baseinfo.actioncolumnDisabled,
                handler: function(grid, rowIndex, colIndex) {
    				baseinfo.loadAndUnloadSquad.deleteLoadAndUnloadSquadEntityByCode(null,grid.getStore().getAt(rowIndex),grid);
                }
            }]
		},{
			text : baseinfo.loadAndUnloadSquad.i18n('foss.baseinfo.loadAndUnloadSquadCode'),									//装卸车小队编码
			xtype : 'ellipsiscolumn',
			dataIndex : 'code'
		},{
			text : baseinfo.loadAndUnloadSquad.i18n('foss.baseinfo.loadAndUnloadSquadName'),									//装卸车小队名称
			xtype : 'ellipsiscolumn',
			dataIndex : 'name'
		},{
			text : baseinfo.loadAndUnloadSquad.i18n('foss.baseinfo.superiorDeptName'),									//上级部门名称
			xtype : 'ellipsiscolumn',
			dataIndex : 'parentOrgName'
		},{
			xtype:'linebreakcolumn',
			text : baseinfo.loadAndUnloadSquad.i18n('foss.baseinfo.porter'),											//队员
			dataIndex : 'porters',
			flex:1,
			renderer:function(value){
				// 循环拼接 队员信息
				if(!Ext.isEmpty(value)){
					var v = '';
					for(var i in value){
						v+='【'+value[i].empName+'】 ';
					}
					return v;
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
	if (Ext.getCmp('T_baseinfo-loadAndUnloadSquadIndex_content')){
		return;
	}
	var queryForm  = Ext.create('Foss.baseinfo.loadAndUnloadSquad.QueryConditionForm',{'record':Ext.create('Foss.baseinfo.loadAndUnloadSquad.LoadAndUnloadSquadEntityModel')});//查询FORM
	var queryGrid  = Ext.create('Foss.baseinfo.loadAndUnloadSquad.QueryResultGrid');//查询结果显示列表
	Ext.getCmp('T_baseinfo-loadAndUnloadSquadIndex').add(Ext.create('Ext.panel.Panel', {
		id : 'T_baseinfo-loadAndUnloadSquadIndex_content',
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
		items : [ queryForm, queryGrid,{
			xtype:'button',
			text:baseinfo.loadAndUnloadSquad.i18n('foss.baseinfo.void'),								//作废
			hidden:true,
			handler :function(){
				baseinfo.loadAndUnloadSquad.deleteLoadAndUnloadSquadEntityByCode(baseinfo.delAgencyType);
			}
		}] 
	}));
});
//------------------------------------WINDOW--------------------------------
//装卸车小队界面win
Ext.define('Foss.baseinfo.loadAndUnloadSquad.LoadAndUnloadSquadEntityWin',{
	extend : 'Ext.window.Window',
	title : baseinfo.loadAndUnloadSquad.i18n('foss.baseinfo.loadandunloadsquad.addLoadUnloadSquad'),								//新增偏线代理公司   默认新增
	closable : true,
	modal : true,
	resizable:false,
	closeAction : 'hide',
	autoScroll : true,
	width :900,
	height :320,	
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	listeners:{
		beforehide:function(me){
			//清空 理货员 变量数据的组件
			baseinfo.loadAndUnloadSquad.sourcePorterArr = [];				//理货员 初始数据model集合 
			baseinfo.loadAndUnloadSquad.oprPorterArr = [];					//理货员 操作数据model集合
			baseinfo.loadAndUnloadSquad.addPorterArr = [];					//理货员 新增数据model集合
			baseinfo.loadAndUnloadSquad.delPorterArr = [];					//理货员 删除数据model集合
		}
	},
	viewState:baseinfo.viewState.add,				//查看状态,默认为新增
	editForm:null,											//偏线代理公司表单Form
	editGrid:null,											//偏线代理公司表格Grid
	formRecord:null,										//偏线代理公司实体 Foss.baseinfo.BusinessPartnerModel
	gridDate:null,											//偏线代理公司 网点信息数组  [Foss.baseinfo.OuterBranchModel]
	sourcePorterData:null,								//理货员原始数据
    constructor : function(config) {
		var me = this,cfg = Ext.apply({}, config);
		me.editForm = Ext.create('Foss.baseinfo.loadAndUnloadSquad.LoadAndUnloadSquadEntityWinForm',{'viewState':config.viewState,'formRecord':config.formRecord});
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
			text : baseinfo.loadAndUnloadSquad.i18n('foss.baseinfo.cancel'),
			handler :function(){
				me.hide();
			}
		},{
			text : baseinfo.loadAndUnloadSquad.i18n('foss.baseinfo.reset'),
			handler :	function(records){
				// 重置
				baseinfo.formReset([me.editForm.getForm()],[me.formRecord]);
				me.down('form').down('container').removeAll();
				me.down('form').down('container').add({
					xtype:'label',
					text:baseinfo.loadAndUnloadSquad.i18n('foss.baseinfo.loadandunloadsquad.player')
				});
				var store=me.down('grid').store;
				//store.removeAll();
				//store.loadData(me.sourcePorterData);
				store.load();//store已经load加载过数据 不必要再 重新加一遍
				store.on('load',function(s,records){   
			        s.each(function(record){
						baseinfo.loadAndUnloadSquad.sourcePorterArr.push(record);
						Ext.Array.push(baseinfo.loadAndUnloadSquad.oprPorterArr,record);
						//baseinfo.loadAndUnloadSquad.addPorter(me.down('form').down('container'),record,null,config.viewState,baseinfo.loadAndUnloadSquad.init);
					});   
				}); 
			} 
		},{
			text : baseinfo.loadAndUnloadSquad.i18n('foss.baseinfo.save'),
			handler :function(){
		    	var editForm = me.editForm.getForm();
		    	//实时校验的 结果是否通过,判断偏线代理必填项是否填写并全部填写合法
		    	if(!editForm.isValid()){
		    		baseinfo.showInfoMsg(baseinfo.loadAndUnloadSquad.i18n('foss.baseinfo.airagencycompany.checkData'));
		    		return;
		    	}
	    		editForm.updateRecord(me.formRecord);
	    		baseinfo.loadAndUnloadSquad.submitLoadAndUnloadSquadEntity(me,me.viewState,me.formRecord.data);
			}
		}];
	}
});

