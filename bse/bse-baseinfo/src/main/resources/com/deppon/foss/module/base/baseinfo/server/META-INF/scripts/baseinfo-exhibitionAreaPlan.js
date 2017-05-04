//展馆区域规划及展会货标记优化-187862-dujunhui
baseinfo.exhibitionAreaPlan.ployfeature = null;					//地图服务
baseinfo.exhibitionAreaPlan.ployfeature1 = null;
baseinfo.exhibitionAreaPlan.exhibition_map = {exh:'EXHIBITION_REGIONS'};
baseinfo.exhibitionAreaPlan.specialActor = null;               //特殊角色
//信息
baseinfo.showInfoMsg = function(message,fun) {
	var len = message.length;
	Ext.Msg.show({
	    title:baseinfo.exhibitionAreaPlan.i18n('i18n.baseinfo-util.fossAlert'),
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
  }, 2000);
};

//获取当前登录人角色
baseinfo.exhibitionAreaPlan.currentUserRole = function(){//角色判断
	//只包含营业部操作员角色而没有营运分析查询员角色
	if(!Ext.Array.contains(FossUserContext.getCurrentUser().roleids, baseinfo.exhibitionAreaPlan.specialActor)){
		return true;
	}else{
		return false;
	}
};

//根据当前登录人角色判断查询界面管理部门是否可选
baseinfo.exhibitionAreaPlan.selectableOrNot = function(){
	var me=Ext.getCmp('T_baseinfo-exhibitionAreaPlanQueryForm_Id').getForm();
	if(baseinfo.exhibitionAreaPlan.currentUserRole()){//包含营业部操作员角色而没有营运分析查询员角色
		//默认当前登陆部门为管理部门
		me.findField('managementCode').setCombValue(FossUserContext.getCurrentDept().name,
				FossUserContext.getCurrentDeptCode());
		me.findField('managementCode').setReadOnly(true);
	}else{
		//营运分析查询员角色暂不设置
	}
};

//查询特殊角色
baseinfo.exhibitionAreaPlan.querySpecialActor = function(){
	//查询特殊角色
	Ext.Ajax.request({
		url:baseinfo.realPath('querySpecialActorParam.action'),
		async: false,//ASYNC是否异步(TRUE异步, FALSE同步)
		params:{
			"objectVo.confValue" : null
		},
		success:function(response){//查询特殊角色成功
			var json=Ext.decode(response.responseText);
			baseinfo.exhibitionAreaPlan.specialActor=json.objectVo.confValue;
		},
		exception:function(response){
			baseinfo.showInfoMsg(result.message);
		}
	});
};

//根据展馆名称名称查询
baseinfo.exhibitionAreaPlan.queryExhibitionByName = function(obj,viewState,virtualCode){
	if(""==obj.getValue()){
		return;
	}
	var objectVo = {},exhibitionAreaPlanEntity = {};
	exhibitionAreaPlanEntity.exhibitionName = obj.getValue();
	exhibitionAreaPlanEntity.viewState = viewState;
	exhibitionAreaPlanEntity.virtualCode = virtualCode;
	objectVo.exhibitionAreaPlanEntity = exhibitionAreaPlanEntity;
	if(!Ext.isEmpty(obj.getValue())){
		//自动生成 区域编码
		baseinfo.requestAjaxJson(baseinfo.realPath('queryAreaPlanByName.action'), {
			'objectVo' : objectVo
		}, function(result) {
			//验证唯一性成功则无操作
		}, function(result) {
			obj.setValue(null);
			baseinfo.showInfoMsg(result.message);
		});
	}
};

//根据管理部门自动生成展馆编码
baseinfo.exhibitionAreaPlan.autoCreateExhibitionCode = function(managementCode,form){
	form.getForm().findField('exhibitionCode').reset();
	var objectVo = {},exhibitionAreaPlanEntity = {};
	exhibitionAreaPlanEntity.managementCode = managementCode;
	objectVo.exhibitionAreaPlanEntity = exhibitionAreaPlanEntity;
	if(!Ext.isEmpty(managementCode)){
		//自动生成 展馆编码
		baseinfo.requestAjaxJson(baseinfo.realPath('createCodeByManagement.action'), {
			'objectVo' : objectVo
		}, function(result) {
			//自动生成编码
			form.getForm().findField('exhibitionCode').setValue(result.
					objectVo.exhibitionAreaPlanEntity.exhibitionCode);
		}, function(result){
			
		});
	}
};

//作废事件
baseinfo.exhibitionAreaPlan.deleteExhibitionAreaPlanEntityByCode = function(delType,operatRecord){
	var grid = Ext.getCmp('T_baseinfo-exhibitionAreaPlan_content').getQueryGrid(),
		url = baseinfo.realPath('deleteExhibitionAreaPlan.action'),
		objectVo = {};
	selection=grid.getSelectionModel().getSelection();
	if(selection.length<=0 && Ext.isEmpty(operatRecord)){
		Ext.MessageBox.alert(baseinfo.exhibitionAreaPlan.i18n('i18n.baseinfo-util.fossAlert'),
				baseinfo.exhibitionAreaPlan.i18n('foss.baseinfo.deleteNoticeMsg'));
	}else{	
		//定义待删除地图范围ID数组
		var gisidArray = [];
		//定义待删除展馆虚拟编码数组
		var codes = [];
		if(!Ext.isEmpty(delType) && baseinfo.delType===delType){//批量作废
			for (var j = 0; j < selection.length; j++) {
				//把选中的展馆区域规划信息虚拟编码存放到数组中
				codes.push(selection[j].get('exhibitionCode'));
				//把选中的展馆区域规划范围gisid存放到数组中
				gisidArray.push(selection[j].get('gisid'));
			}
		}else{//逐条作废
			codes.push(operatRecord.get('exhibitionCode'));
			gisidArray.push(operatRecord.get('gisid'));
		}
		objectVo.codes = codes;
		
		Ext.MessageBox.buttonText.yes =baseinfo.exhibitionAreaPlan.i18n('foss.baseinfo.confirm');
		Ext.MessageBox.buttonText.no =baseinfo.exhibitionAreaPlan.i18n('foss.baseinfo.cancel');
		Ext.Msg.confirm(baseinfo.exhibitionAreaPlan.i18n('i18n.baseinfo-util.fossAlert'),
				baseinfo.exhibitionAreaPlan.i18n('foss.baseinfo.exhibitionAreaPlan.confirmAlertRecord'),function(btn,text) {
			if (btn == 'yes') {
				baseinfo.requestAjaxJson(url,{'objectVo':objectVo},function(result){
					grid.store.loadPage(1);
					var dMap = new DpMap('gisMap',{center : '上海市', zoom : 13 },function(map) {
						var polyFeature = new DpMap.service.DpMap_polygon(map,'gisMap',{isAddable:true,callBackFun:function(data){}});
						//FOSS删除展馆信息成功后删除展馆在GIS上面的范围
						for(var i= 0;i < gisidArray.length; i++){
							//根据展馆gisid删除展馆地图范围
							polyFeature.deletePolygonByCode(gisidArray[i]);
						}
					});
					baseinfo.showInfoMsg(baseinfo.exhibitionAreaPlan.i18n('foss.baseinfo.deleteSuccess'));
				},function(result){
					baseinfo.showInfoMsg(result.message);
				});
			}
		});
	}
};
//保存事件 
baseinfo.exhibitionAreaPlan.submitExhibitionAreaPlanEntity = function(win,viewState,operatEntity,obj){
	var grid = Ext.getCmp('T_baseinfo-exhibitionAreaPlan_content').getQueryGrid(),
		url = baseinfo.realPath('addExhibitionAreaPlan.action'),
		m_success =baseinfo.exhibitionAreaPlan.i18n('foss.baseinfo.saveSuccessful'),							//保存成功！
		m_failure =baseinfo.exhibitionAreaPlan.i18n('foss.baseinfo.saveFail'),								//保存失败！
		m_dateError =baseinfo.exhibitionAreaPlan.i18n('foss.baseinfo.exhibitionAreaPlan.dataError'),								//数据异常！
		objectVo = {};
	objectVo.exhibitionAreaPlanEntity = operatEntity;
	if(Ext.isEmpty(operatEntity.porters)){
		operatEntity.porters = [];
	}
	if('ADD' === viewState){
		//暂无操作
	}else if('UPDATE' === viewState){
		//修改URL
		url = baseinfo.realPath('updateExhibitionAreaPlan.action');
	}
	baseinfo.requestAjaxJson(url,{'objectVo':objectVo},function(result){
		if(!Ext.isEmpty(result.objectVo.exhibitionAreaPlanEntity)){
			grid.store.loadPage(1);
			baseinfo.showInfoMsg(m_success);
			if(obj==null){
			   win.hide();
			}
		}else{
			baseinfo.showInfoMsg(result.message);
		}
	},function(result){
		 grid.store.loadPage(1);
		 baseinfo.showInfoMsg(result.message); 
	});
};

//弹出界面上 数据渲染
baseinfo.exhibitionAreaPlan.initParentWin = function(win,viewState,formRecord,gridData){
	if('ADD' === viewState){
		//新增时 必填项不用
		win.editForm.getForm().reset();
	}else{
		// 公共组件
		win.editForm.down('dynamicorgcombselector').setCombValue(formRecord.get('managementName'),formRecord.get('managementCode'));//管理部门
		win.editForm.down('linkregincombselector').setReginValue(formRecord.get('provName'),formRecord.get('provCode'),'1');//省份
		win.editForm.down('linkregincombselector').setReginValue(formRecord.get('cityName'),formRecord.get('cityCode'),'2');//市
		win.editForm.down('linkregincombselector').setReginValue(formRecord.get('countyName'),formRecord.get('countyCode'),'3');//县
		//加载数据
		win.editForm.loadRecord(formRecord);
	}
	if(baseinfo.viewState.view=== viewState){
		baseinfo.formFieldSetReadOnly(true,win.editForm);
		win.editForm.getForm().findField('p').setValue(formRecord.get('provName'));
		win.editForm.getForm().findField('c').setValue(formRecord.get('cityName'));
		win.editForm.getForm().findField('d').setValue(formRecord.get('countyName'));
	}
	if(baseinfo.exhibitionAreaPlan.currentUserRole()){//普通角色
		//当前登录人角色不可选择管理部门,指定为当前登录人部门
		win.editForm.getForm().findField('managementCode').setCombValue(
				FossUserContext.getCurrentDept().name,
				FossUserContext.getCurrentDeptCode());
		win.editForm.getForm().findField('managementName').setValue(
				FossUserContext.getCurrentDept().name);  
		//且不可读
		win.editForm.getForm().findField('managementCode').setReadOnly(true);
		//展馆编码自动生成
		if('ADD' === viewState){//对普通角色只有新增时自动生成编码，修改时不生成，且不可操作
			win.editForm.getForm().findField('exhibitionCode').setValue(
					baseinfo.exhibitionAreaPlan.autoCreateExhibitionCode(
							FossUserContext.getCurrentDeptCode(),win.editForm));
		}
	}
	return win;
};
//查询按钮对应事件
baseinfo.exhibitionAreaPlan.QueryResult=function(exhibitionCode,exhibitionName,managementCode){
	var grid  = Ext.getCmp('T_baseinfo-exhibitionAreaPlan_content').getQueryGrid();//得到grid
	grid.store.loadPage(1);//用分页的moveFirst()方法
};

//展馆区域规划信息Model
Ext.define('Foss.baseinfo.exhibitionAreaPlan.AreaPlanEntityModel', {
	extend: 'Ext.data.Model',
	fields : [{name:'exhibitionCode',type:'string'},//展馆编码
	          {name:'exhibitionName',type:'string'},//展馆名称
	          {name:'managementCode',type:'string'}, //管理部门编码
			  {name:'managementName',type:'string'},//管理部门名称
			  {name:'notes',type:'string'},//备注
			  {name:'virtualCode',type:'string'},//虚拟编码
			  {name:'gisid',type:'string'}, //展馆规划区域范围ID
			  {name:'gisArea',type:'string'}, //展馆规划区域GIS面积
			  {name:'provCode',type:'string'},// 所在省编码
			  {name:'provName',type:'string'},// 所在省名称（扩展）
			  {name:'cityCode',type:'string'},// 所在市编码
			  {name:'cityName',type:'string'},// 所在市名称（扩展）
			  {name:'countyCode',type:'string'},// 所在区县
			  {name:'countyName',type:'string'}]// 所在区县名称（扩展）
});
//------------------------------------STORE----------------------------------
//展馆STORE
Ext.define('Foss.baseinfo.exhibitionAreaPlan.AreaPlanEntityStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.baseinfo.exhibitionAreaPlan.AreaPlanEntityModel',
	pageSize:20,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url : baseinfo.realPath('queryExhibitionAreaPlanByCondition.action'),
		reader : {
			type : 'json',
			root : 'objectVo.areaPlanEntityList',
			totalProperty : 'totalCount'
		}
	}
});

//展馆区域规划查询条件
Ext.define('Foss.baseinfo.exhibitionAreaPlan.QueryConditionForm', {
	extend: 'Ext.form.Panel',
	title:baseinfo.exhibitionAreaPlan.i18n('foss.baseinfo.queryCondition'),
	id:'T_baseinfo-exhibitionAreaPlanQueryForm_Id',
	frame: true,
	html:"<div id='gisMap' style='position:absolute;top:10%; left:25%; z-index:1; visibility: hidden; '></div>",
	collapsible: true,
    defaults : {
    	margin : '8 10 5 10',
    	labelWidth:130
    },
    height :180,
	defaultType : 'textfield',
	layout:{
        type: 'table',
        columns: 3
    },
    record:null,												//绑定的model Foss.baseinfo.exhibitionAreaPlan.AreaPlanEntityModel
	constructor : function(config) {							//构造器
		var me = this,cfg = Ext.apply({}, config);
		me.items = me.getItems();
		me.callParent([cfg]);
	},
	getItems:function(){
		var me = this;
		return [{
			fieldLabel:baseinfo.exhibitionAreaPlan.i18n('foss.baseinfo.exhibitionAreaPlan.exhibitionCode'),							//快递收派小区编码
			name:'exhibitionCode',
			columnWidth:0.33
		},{
			fieldLabel:baseinfo.exhibitionAreaPlan.i18n('foss.baseinfo.exhibitionAreaPlan.exhibitionName'),							//快递收派小区名称
			name:'exhibitionName',
			columnWidth:0.33
		},{ 
			xtype:'dynamicorgcombselector',
			fieldLabel:baseinfo.exhibitionAreaPlan.i18n('foss.baseinfo.exhibitionAreaPlan.managementCode'),									//管理部门
			name:'managementCode',
			columnWidth:0.33
		},{
			xtype : 'container',
			colspan:3,
			defaultType:'button',
			layout:'column',
			items : [{
				width: 75,
				text :baseinfo.exhibitionAreaPlan.i18n('foss.baseinfo.reset'),
				disabled:!baseinfo.exhibitionAreaPlan.isPermission('exhibitionAreaPlan/exhibitionAreaPlanQueryButton'),//按钮权限
				hidden:!baseinfo.exhibitionAreaPlan.isPermission('exhibitionAreaPlan/exhibitionAreaPlanQueryButton'),//按钮权限
				handler : function() {
					this.up('form').getForm().reset();
					if(baseinfo.exhibitionAreaPlan.currentUserRole()){//普通角色
						this.up('form').getForm().findField('managementCode').setCombValue(
								FossUserContext.getCurrentDept().name,
								FossUserContext.getCurrentDeptCode());
//						this.up('form').getForm().findField('managementCode').setReadOnly(true);
					}
				}
			},{
				xtype:'container',
				html:'&nbsp;',
				columnWidth:.99
			},{
				xtype:'button',
				width: 75,
				disabled:!baseinfo.exhibitionAreaPlan.isPermission('exhibitionAreaPlan/exhibitionAreaPlanQueryButton'),
				hidden:!baseinfo.exhibitionAreaPlan.isPermission('exhibitionAreaPlan/exhibitionAreaPlanQueryButton'),
				text : baseinfo.exhibitionAreaPlan.i18n('foss.baseinfo.query'),
				cls:'yellow_button',
				handler : function() {//查询事件
					baseinfo.exhibitionAreaPlan.QueryResult(
							this.up('form').getForm().findField('exhibitionCode').getValue(),
							this.up('form').getForm().findField('exhibitionName').getValue(),
							this.up('form').getForm().findField('managementCode').getValue());
				}
			}]
		}];
	}
});
//---------------------------------------------------------------GRID-----------------------------------------------------------------------------
//展馆区域规划查询结果grid
Ext.define('Foss.baseinfo.exhibitionAreaPlan.QueryResultGrid', {
	extend: 'Ext.grid.Panel',
	title :baseinfo.exhibitionAreaPlan.i18n('foss.baseinfo.queryResults'),
    sortableColumns:false,
    enableColumnHide:false,
    enableColumnMove:false,
    cls: 'autoHeight',
	bodyCls: 'autoHeight', 
	stripeRows : true, 									// 交替行效果
	selType : "rowmodel", 								// 选择类型设置为：行选择
	emptyText:baseinfo.exhibitionAreaPlan.i18n('foss.baseinfo.queryResultIsNull'),							//查询结果为空
	queryForm:null,
	frame: true,
	//得到BBAR（分页）
	pagingToolbar : null,
	constructor : function(config){
		var me = this, cfg = Ext.apply({}, config);
		me.columns = me.getColumns(config);
		me.store = me.getStore();
		me.listeners = me.getMyListeners(config);
	    //添加多选框
		me.selModel = Ext.create('Ext.selection.CheckboxModel',
				{mode:'MULTI',checkOnly:true});
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
		return [{
			text :baseinfo.exhibitionAreaPlan.i18n('foss.baseinfo.add'),//新增
			disabled:!baseinfo.exhibitionAreaPlan.isPermission('exhibitionAreaPlan/exhibitionAreaPlanAddButton'),   //按钮权限
			hidden:!baseinfo.exhibitionAreaPlan.isPermission('exhibitionAreaPlan/exhibitionAreaPlanAddButton'),   //按钮权限
			handler :function(){
				me.addExhibitionAreaPlanEntity({}).show();
			} 
		},'-', {
			text :baseinfo.exhibitionAreaPlan.i18n('foss.baseinfo.void'),//作废
			disabled:!baseinfo.exhibitionAreaPlan.isPermission('exhibitionAreaPlan/exhibitionAreaPlanVoidButton'),
			hidden:!baseinfo.exhibitionAreaPlan.isPermission('exhibitionAreaPlan/exhibitionAreaPlanVoidButton'),
			handler :function(){
				baseinfo.exhibitionAreaPlan.deleteExhibitionAreaPlanEntityByCode(baseinfo.delType,null);
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
	//得到展馆区域规划编辑窗体
	getAgencyDeptWin:function(win,title,viewState,param,id){
		var formRecord = Ext.isEmpty(param.formRecord)?Ext.create('Foss.baseinfo.exhibitionAreaPlan.AreaPlanEntityModel'):param.formRecord;
		if (Ext.isEmpty(win)) {
			win = Ext.create('Foss.baseinfo.exhibitionAreaPlan.ExhibitionAreaPlanEntityWin',{
				'title':title,
				'viewState':viewState,
				'sourceGrid':this,
				'formRecord':formRecord,
				'id':id
			});
		}
		return baseinfo.exhibitionAreaPlan.initParentWin(win,viewState,formRecord,null);
	},
	addExhibitionAreaPlanEntityWin:null,						//新增展馆规划区域信息
	addExhibitionAreaPlanEntity:function(param){
		var addForm=Ext.getCmp('T_ExhibitionAreaPlan_ADD');
		if(null!=addForm){
			addForm.editForm.getForm().reset();
			return addForm;
		}
		return this.getAgencyDeptWin(this.addExhibitionAreaPlanEntityWin,
				baseinfo.exhibitionAreaPlan.i18n('foss.baseinfo.exhibitionAreaPlan.addExhibitionInfo'),'ADD',param,'T_ExhibitionAreaPlan_ADD');
	},
	updateExhibitionAreaPlanEntityWin:null,						//修改展馆规划区域
	updateExhibitionAreaPlanEntity:function(param){
		return this.getAgencyDeptWin(this.updateExhibitionAreaPlanEntityWin,
				baseinfo.exhibitionAreaPlan.i18n('foss.baseinfo.exhibitionAreaPlan.updateExhibitionInfo'),'UPDATE',param,null);
	},
	viewExhibitionAreaPlanEntityWin:null,						//查看展馆规划区域
	viewExhibitionAreaPlanEntity:function(param){
		return this.getAgencyDeptWin(this.viewExhibitionAreaPlanEntityWin,
				baseinfo.exhibitionAreaPlan.i18n('foss.baseinfo.exhibitionAreaPlan.viewExhibitionInfo'),baseinfo.viewState.view,param);
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
	    	//查看展馆区域规划信息
	    	itemdblclick: function(view,record) {
				var param = {};
            	param.formRecord = record;
				var win = me.viewExhibitionAreaPlanEntity(param);
				win.show();
	    	}
	    };
	},
	getStore:function(){
		return Ext.create('Foss.baseinfo.exhibitionAreaPlan.AreaPlanEntityStore',{
			autoLoad : false,
			pageSize : 20,
			listeners: {
				beforeload: function(store, operation, eOpts){
					var queryForm = Ext.getCmp('T_baseinfo-exhibitionAreaPlan_content').getQueryForm().getForm();//得到查询的FORM表单
					queryForm.updateRecord(queryForm.record);
					var entity = queryForm.record.data;
					if(queryForm!=null){
						Ext.apply(operation,{
							params : {
//								//展馆编码
								'objectVo.exhibitionAreaPlanEntity.exhibitionCode':entity.exhibitionCode,
//								//展馆名称
								'objectVo.exhibitionAreaPlanEntity.exhibitionName':entity.exhibitionName,
//								//管理部门编码
								'objectVo.exhibitionAreaPlanEntity.managementCode':entity.managementCode
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
			text :baseinfo.exhibitionAreaPlan.i18n('foss.baseinfo.operate'),//操作
			items: [{
				iconCls : 'deppon_icons_edit',
				tooltip :baseinfo.exhibitionAreaPlan.i18n('foss.baseinfo.update'), //修改
                disabled:!baseinfo.exhibitionAreaPlan.isPermission('exhibitionAreaPlan/exhibitionAreaPlanEditButton'),
				width : 42,
				handler : function (grid, rowIndex, colIndex) {
					var param = {};
                	param.formRecord = grid.getStore().getAt(rowIndex);
                	var win = me.updateExhibitionAreaPlanEntity(param);    		
                	win.show();
				}
			}, {
				iconCls : 'deppon_icons_cancel',
				tooltip :baseinfo.exhibitionAreaPlan.i18n('foss.baseinfo.void'), //作废
				disabled:!baseinfo.exhibitionAreaPlan.isPermission('exhibitionAreaPlan/exhibitionAreaPlanVoidButton'),
				width : 42,
				handler : function (grid, rowIndex, colIndex) {
					baseinfo.exhibitionAreaPlan.deleteExhibitionAreaPlanEntityByCode(null,grid.getStore().getAt(rowIndex));
				}
			}]
		},{
			text :baseinfo.exhibitionAreaPlan.i18n('foss.baseinfo.exhibitionAreaPlan.exhibitionCode'),									//快递收派小区编码
			dataIndex : 'exhibitionCode',
			width:150
		},{
			text :baseinfo.exhibitionAreaPlan.i18n('foss.baseinfo.exhibitionAreaPlan.exhibitionName'),									//快递收派小区名称
			dataIndex : 'exhibitionName',
			width:150
		},{
			text :baseinfo.exhibitionAreaPlan.i18n('foss.baseinfo.exhibitionAreaPlan.managementCode'),									//管理部门
			dataIndex : 'managementName',
			width:150
		},{
			text :baseinfo.exhibitionAreaPlan.i18n('foss.baseinfo.notes'),									//管理部门
			dataIndex : 'notes',
			width:410
		}];
	}
});

//展馆区域规划界面FORM
Ext.define('Foss.baseinfo.exhibitionAreaPlan.ExhibitionAreaPlanEntityWinForm', {
	extend : 'Ext.form.Panel',
	id:'T_baseinfo-exhibitionAreaPlanWinForm_Id',
	defaultType : 'textfield',
	layout:{
        type: 'table',
        columns: 2
    },
    formRecord:null,												//绑定的model Foss.baseinfo.exhibitionAreaPlan.AreaPlanEntityModel
    formStore:null,													//绑定的formStore Foss.baseinfo.exhibitionAreaPlan.exhibitionAreaPlanEntityStore
    viewState:null,
    id:null,
    constructor : function(config) {							//构造器
		var me = this,cfg = Ext.apply({}, config);
		me.defaults = me.getDefaults(config);
		me.items = me.getItems(config);
		me.callParent([cfg]);
	},
	getDefaults:function(config){
		var me = this;
		return {
			allowBlank:true,
			labelWidth : 140,
			readOnly:(baseinfo.viewState.view === config.viewState)?true:false
	    };
	},
	getItems:function(config){
		var me = this;
		return [{
			fieldLabel:baseinfo.exhibitionAreaPlan.i18n('foss.baseinfo.exhibitionAreaPlan.exhibitionCode'),							//快递收派编码
			name:'exhibitionCode',
			labelWidth:140,
			columnWidth:0.45,
			colspan:2,
			readOnly:true,
			allowBlank:false
		},{
			fieldLabel:baseinfo.exhibitionAreaPlan.i18n('foss.baseinfo.exhibitionAreaPlan.exhibitionName'),							//快递收派名称
			name:'exhibitionName',
			maxLength : 50,
			maxLengthText:'展馆名称不能超过50个字符！',
			labelWidth:140,
			columnWidth:0.45,
			colspan:2,
			allowBlank:(baseinfo.viewState.view === config.viewState),
			listeners:{
				blur:function(The,eOpts ){
					baseinfo.exhibitionAreaPlan.queryExhibitionByName(The,
							config.viewState,me.getForm().findField('virtualCode').getValue());
        		}
			}
		},{
			labelWidth : 140,
			columnWidth:0.45,
			colspan:2,
			xtype:'dynamicorgcombselector',
			fieldLabel:baseinfo.exhibitionAreaPlan.i18n('foss.baseinfo.exhibitionAreaPlan.managementCode'),								//管理部门
			name: 'managementCode',
			allowBlank:(baseinfo.viewState.view === config.viewState),
			listeners:{
				select:function(field,rs){
					if(!Ext.isEmpty(rs)){
        				baseinfo.exhibitionAreaPlan.autoCreateExhibitionCode(me.getForm().findField('managementCode').getValue(),me);
        				me.getForm().findField('managementName').setValue(me.getForm().findField('managementCode').getRawValue());  
        			}
        		}
			}
		},{
		   xtype:'container',
		   defaultType : 'textfield',
		   colspan:2,
		   hidden:(baseinfo.viewState.view != config.viewState),
		   layout:{
				type: 'table',
				columns: 3
			},
		   items:[{
					fieldLabel:'省份',					
					name:'p',
					readOnly:true,
					hidden:(baseinfo.viewState.view != config.viewState)
				},{
					fieldLabel:'城市',			
					name:'c',
					readOnly:true,
					hidden:(baseinfo.viewState.view != config.viewState)
				},{
					fieldLabel:'区县',
					name:'d',
					readOnly:true,
					hidden:(baseinfo.viewState.view != config.viewState)
				}]
			},{
			colspan:2,
			labelWidth : 140,
			columnWidth:0.9,
			width: 650,
			hidden :(baseinfo.viewState.view === config.viewState),
			fieldLabel :baseinfo.exhibitionAreaPlan.i18n('foss.baseinfo.exhibitionAreaPlan.provCityCounty'),
			provinceName:'provCode',// 省份名称—对应name
			cityName:'cityCode',// 城市name	
			areaName:'countyCode',// 县name
			allowBlank:(baseinfo.viewState.view === config.viewState),
			nationIsBlank:true,
			provinceIsBlank:(baseinfo.viewState.view === config.viewState),
			cityIsBlank:(baseinfo.viewState.view === config.viewState),
			areaIsBlank:(baseinfo.viewState.view === config.viewState),
			type : 'P-C-C',
			xtype : 'linkregincombselector'
		},{
			colspan:2,
			xtype: 'textareafield',
			name:'notes',
			fieldLabel:baseinfo.exhibitionAreaPlan.i18n('foss.baseinfo.notes'),
			maxLength : 300,
			maxLengthText:'备注不能超过300个字符！',
			width: 595,
			height:60,
			allowBlank:true
		},{
			name:'managementName',
			hidden:true
		},{
			name:'provName',
			hidden:true
		},{
			name:'cityName',
			hidden:true
		},{
			name:'countyName',
			hidden:true
		},{
			fieldLabel:baseinfo.exhibitionAreaPlan.i18n('foss.baseinfo.exhibitionAreaPlan.mapAddress'),
			name:'gisid',
			hidden:true,
			allowBlank:true
		},{
			fieldLabel:baseinfo.exhibitionAreaPlan.i18n('foss.baseinfo.exhibitionAreaPlan.exhibitionAreaPlan'),
			name:'gisArea',
			hidden:true,
			allowBlank:true
		},{ 
			name:'virtualCode',
			hidden:true,
			allowBlank:true
		}];
	}
});

//展馆区域规划界面win
Ext.define('Foss.baseinfo.exhibitionAreaPlan.ExhibitionAreaPlanEntityWin',{
	extend : 'Ext.window.Window',
	title : baseinfo.exhibitionAreaPlan.i18n('foss.baseinfo.exhibitionAreaPlan.addExhibitionInfo'),								//新增展馆区域规划默认新增
	closable : true,
	modal : true,
	resizable:false,
	closeAction : 'destroy',
	width :670,
	height :300,	
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	listeners:{
		beforehide:function(me){
			//清空 有ID的组件
		}
	},
	viewState:'ADD',				//查看状态,默认为新增
	editForm:null,											//展馆区域规划表单Form
	editGrid:null,											//展馆区域规划表格Grid
	formRecord:null,										//展馆区域规划实体 Foss.baseinfo.exhibitionAreaPlan.AreaPlanEntityModel
	gridDate:null,											//展馆区域规划网点信息数组  [Foss.baseinfo.OuterBranchModel]
    constructor : function(config) {
		var me = this,cfg = Ext.apply({}, config);
		me.editForm = Ext.create('Foss.baseinfo.exhibitionAreaPlan.ExhibitionAreaPlanEntityWinForm',
				{'height':180,'viewState':config.viewState,'formRecord':config.formRecord});
		me.items = [me.editForm];
		me.listeners = {
			'beforeshow':function(me){
				//为新增的时候,暂无操作
			},
		};
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
			text :baseinfo.exhibitionAreaPlan.i18n('foss.baseinfo.exhibitionAreaPlan.map'),
			handler :function(){
				var form = me.down('form').getForm(),
				    provName = form.findField('provCode').getRawValue(),
					cityName = form.findField('cityCode').getRawValue(),
					countyName = form.findField('countyCode').getRawValue(),
					exhibitionCode = form.findField('exhibitionCode').getValue();//展馆编码
					exhibitionName = form.findField('exhibitionName').getValue();//展馆名称
				var managementName = form.findField('managementCode').getRawValue();
				
				if(Ext.isEmpty(exhibitionCode)){
					Ext.Msg.alert(baseinfo.exhibitionAreaPlan.i18n('i18n.baseinfo-util.fossAlert'),
							baseinfo.exhibitionAreaPlan.i18n('foss.baseinfo.exhibitionAreaPlan.exhibitionCodeNullNotAllowed'));
					return;
				}
				
				if(Ext.isEmpty(exhibitionName)){
					Ext.Msg.alert(baseinfo.exhibitionAreaPlan.i18n('i18n.baseinfo-util.fossAlert'),
							baseinfo.exhibitionAreaPlan.i18n('foss.baseinfo.exhibitionAreaPlan.exhibitionNameNullNotAllowed'));
					return;
				}
				
				if(Ext.isEmpty(provName)||Ext.isEmpty(cityName)||
						Ext.isEmpty(countyName)){
					Ext.Msg.alert(baseinfo.exhibitionAreaPlan.i18n('i18n.baseinfo-util.fossAlert'),
							baseinfo.exhibitionAreaPlan.i18n('foss.baseinfo.exhibitionAreaPlan.provCityCountyNullNotAllowed'));
					return;
				}
				if(Ext.isEmpty(managementName)){
					Ext.Msg.alert(baseinfo.exhibitionAreaPlan.i18n('i18n.baseinfo-util.fossAlert'),
							baseinfo.exhibitionAreaPlan.i18n('foss.baseinfo.exhibitionAreaPlan.managementNullNotAllowed'));
					return;
				}
				
				var mapLocation;//地图位置
				if(!Ext.isEmpty(cityName) && Ext.isEmpty(countyName)){
					mapLocation = cityName;	
				}else if(!Ext.isEmpty(cityName) && !Ext.isEmpty(countyName)){
					mapLocation = cityName + countyName;
				}else{
					mapLocation = baseinfo.exhibitionAreaPlan.i18n('foss.baseinfo.shanghaishi');
				}

				if(!DpMap){//GIS服务
					Ext.Msg.alert(baseinfo.exhibitionAreaPlan.i18n('i18n.baseinfo-util.fossAlert'),
							baseinfo.exhibitionAreaPlan.i18n('foss.baseinfo.exhibitionAreaPlan.alertDpMap'));
					return;
				}
				
				//城市不为空  查看时  聚焦到 对应城市  ，修改时  聚焦到 对应绘画 区域
				else if(baseinfo.viewState.view != config.viewState){
					var w=Ext.create('Ext.window.Window',{
						layout:'fit',
						resizable : false,
						items:[{
							xtype: 'container',
							height:630,
							width:1000,
							listeners: {
								afterrender: function(field) {
									var aa = new DpMap(field.getId(), {center :mapLocation,zoom : 13}, function (map) { 
				    						  var fun = function(data){
				    							  if('ADD' === data.type){
														form.findField('gisid').setValue(data.code);
														form.findField('gisArea').setValue(data.area);
														baseinfo.exhibitionAreaPlan.ployfeature1.closeEditTool();
														if(config.viewState=='UPDATE'){
															baseinfo.exhibitionAreaPlan.submitExhibitionAreaPlanEntity(me,me.viewState,me.down('form').getForm().getValues(),'ADD');
														}else{
														    field.up().close();
														}
				    							  }else if('DELETE' === data.type){
														form.findField('gisid').setValue(null);
														form.findField('gisArea').setValue(null);
														baseinfo.exhibitionAreaPlan.ployfeature1.openEditTool();
														baseinfo.exhibitionAreaPlan.submitExhibitionAreaPlanEntity(me,'UPDATE',me.down('form').getForm().getValues(),'DELETE');
				    							  }else if('UPDATE' ===data.type){
														form.findField('gisArea').setValue(data.area);
				    							  }
				    						  }
				    						  var callFun=function(data){
				    							  if(data.type ='QUERY'){
				    								  baseinfo.exhibitionAreaPlan.ployfeature1.closeEditTool();
				    							  }
				    						  }
				    						 //实例化一个新类			
				    						  baseinfo.exhibitionAreaPlan.ployfeature1 =  new DpMap.service.DpMap_polygon(map,field.getId(),{
				    							  isAddable:(baseinfo.viewState.view != config.viewState),polygonName:exhibitionName, callBackFun:fun, 
				    							  foregroundType:baseinfo.exhibitionAreaPlan.exhibition_map.exh, backgroundType:'COUNTY',closeToolCallback:callFun});
				    					});	
								}
							}
						}]
					}).show();
					setTimeout(function(){
					if(Ext.isEmpty(baseinfo.exhibitionAreaPlan.ployfeature1)){
							if(baseinfo.viewState.update == config.viewState){
								var ifWhile = true;
									while(ifWhile && Ext.isEmpty(baseinfo.exhibitionAreaPlan.ployfeature1)){
										if(!Ext.isEmpty(baseinfo.exhibitionAreaPlan.ployfeature1)){
											baseinfo.exhibitionAreaPlan.ployfeature1.showModifiablePolygons([form.findField('gisid').getValue()]);
											if(!Ext.isEmpty(form.findField('gisid').getValue())){
												baseinfo.exhibitionAreaPlan.ployfeature1.closeEditTool();
											}
											ifWhile = false;
											return;
										}
									}
							}
					}else{
						var obj=baseinfo.exhibitionAreaPlan.ployfeature1.showModifiablePolygons([form.findField('gisid').getValue()]);
						if(!Ext.isEmpty(form.findField('gisid').getValue())){
							baseinfo.exhibitionAreaPlan.ployfeature1.closeEditTool();
						}
					}
					}, 2000);
				}else if(baseinfo.viewState.view == config.viewState){
					Ext.create('Ext.window.Window',{
						layout:'fit',
						resizable : false,
						items:[{
							xtype: 'container',
							height:630,
							width:1000,
							listeners: {
								afterrender: function(field) {
									var aa = new DPMap.MyMap(baseinfo.viewState.view, field.getId(),{center : mapLocation, zoom : 'STREET' },function(map) {
										baseinfo.exhibitionAreaPlan.ployfeature = DMap.PolygonFeature(map,{isAddable:(baseinfo.viewState.view != config.viewState),callBackFun:function(data) {
											if('ADD' === data.type){
												form.findField('gisid').setValue(data.code);
												form.findField('gisArea').setValue(data.area);
											}else if('DELETE' === data.type){
												form.findField('gisid').setValue(null);
												form.findField('gisArea').setValue(null);
												baseinfo.exhibitionAreaPlan.ployfeature1.openEditTool();
												baseinfo.exhibitionAreaPlan.submitExhibitionAreaPlanEntity(me,'UPDATE',me.down('form').getForm().getValues());
											}else if('UPDATE' ===data.type){
												form.findField('gisArea').setValue(data.area);
												baseinfo.exhibitionAreaPlan.ployfeature1.openEditTool();
											}
										},foregroundType:baseinfo.exhibitionAreaPlan.exhibition_map.exh,backgroundType:'COUNTY',manipulable:1});
									})
								}
							}
						}]
					}).show();
					setTimeout(function(){
						if(Ext.isEmpty(baseinfo.exhibitionAreaPlan.ployfeature)){
							if(baseinfo.viewState.update == config.viewState){
								var ifWhile = true;
									while(ifWhile && Ext.isEmpty(baseinfo.exhibitionAreaPlan.ployfeature)){
										if(!Ext.isEmpty(baseinfo.exhibitionAreaPlan.ployfeature)){
											baseinfo.exhibitionAreaPlan.ployfeature.showModifiyAblePolygons([form.findField('gisid').getValue()]);
											ifWhile = false;
											return;
										}
									}
							}
					 
					}else{
						baseinfo.exhibitionAreaPlan.ployfeature.showModifiyAblePolygons([form.findField('gisid').getValue()]);
					} }, 2000);
				}
			}
				
		},{
			text :baseinfo.exhibitionAreaPlan.i18n('foss.baseinfo.cancel'),
			handler :function(){
				me.hide();
			}
		},{
			text :baseinfo.exhibitionAreaPlan.i18n('foss.baseinfo.reset'),
			disabled:(baseinfo.viewState.view === config.viewState),
			handler :function(){
				// 重置
				baseinfo.formReset([me.editForm.getForm()],[me.formRecord]);
				// 公共组件 
				me.editForm.down('linkregincombselector').setReginValue(me.formRecord.get('provName'),me.formRecord.get('provCode'),'1');//省份
				me.editForm.down('linkregincombselector').setReginValue(me.formRecord.get('cityName'),me.formRecord.get('cityCode'),'2');//市
				me.editForm.down('linkregincombselector').setReginValue(me.formRecord.get('countyName'),me.formRecord.get('countyCode'),'3');//县
			} 
		},{
			text :baseinfo.exhibitionAreaPlan.i18n('foss.baseinfo.save'),
			disabled:(baseinfo.viewState.view === config.viewState),
			plugins: {
		        ptype: 'buttondisabledplugin',
		        seconds: 3
		    },
			handler :function(){
				var editForm = me.editForm.getForm();
		    	//实时校验的 结果是否通过,判断Form表单必填项是否填写并全部填写合法
				var gisid=editForm.findField('gisid').getValue();
				if(Ext.isEmpty(gisid)){
					Ext.Msg.alert(baseinfo.exhibitionAreaPlan.i18n('i18n.baseinfo-util.fossAlert'),
							baseinfo.exhibitionAreaPlan.i18n('foss.baseinfo.exhibitionAreaPlan.alertMapDraw'));
					return;
				}
		    	if(!editForm.isValid()){
		    		baseinfo.showInfoMsg(baseinfo.exhibitionAreaPlan.i18n('foss.baseinfo.exhibitionAreaPlan.checkData'));
		    		return;
		    	}
	    		editForm.updateRecord(me.formRecord);
	    		baseinfo.exhibitionAreaPlan.submitExhibitionAreaPlanEntity(me,me.viewState,me.formRecord.data);
			}
		}];
	}
});

/**
 * 程序入口方法
 */
Ext.onReady(function() {
	Ext.QuickTips.init();
	if (Ext.getCmp('T_baseinfo-exhibitionAreaPlan_content')){
		return;
	}
	
	var queryForm  = Ext.create('Foss.baseinfo.exhibitionAreaPlan.QueryConditionForm',
			{'record':Ext.create('Foss.baseinfo.exhibitionAreaPlan.AreaPlanEntityModel')});//查询FORM
	var queryGrid  = Ext.create('Foss.baseinfo.exhibitionAreaPlan.QueryResultGrid',
			{'queryForm':queryForm});//查询结果显示列表
	
	//查询特殊角色
	baseinfo.exhibitionAreaPlan.querySpecialActor();
	//根据当前登录人角色判断查询界面管理部门是否可选
	baseinfo.exhibitionAreaPlan.selectableOrNot();
	
	Ext.getCmp('T_baseinfo-exhibitionAreaPlan').add(Ext.create('Ext.panel.Panel', {
	id : 'T_baseinfo-exhibitionAreaPlan_content',
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
