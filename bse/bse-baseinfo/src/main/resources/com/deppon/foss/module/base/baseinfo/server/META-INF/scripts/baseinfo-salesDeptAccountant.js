/**
 * 区域会计model									Foss.baseinfo.salesDeptAccountantIndex.SalesDeptAccountantEntityModel
 * 区域会计store									Foss.baseinfo.salesDeptAccountantIndex.BillSloganEntityStore
 * 区域会计form									Foss.baseinfo.salesDeptAccountantIndex.QueryConditionForm
 * 区域会计grid									Foss.baseinfo.salesDeptAccountantIndex.QueryResultGrid
 * 区域会计winForm								Foss.baseinfo.salesDeptAccountantIndex.BillSloganEntityWinForm
 * 区域会计winGrid								Foss.baseinfo.salesDeptAccountantIndex.BillSloganEntityWinGrid
 * 区域会计win									Foss.baseinfo.salesDeptAccountantIndex.BillSloganEntityWin
 */

//------------------------------------常量和公用方法----------------------------------
baseinfo.advertiseType = {dg:'DG',ddg:'DDG'};						//标识 是区域会计DG还是部门区域会计DDG
//初始化界面数据 
baseinfo.salesDeptAccountantIndex.initWinData = function(win,viewState,formRecord,gridData,advertiseType){
	//区域会计和部门广告 form 加载数据
	win.down('form').loadRecord(formRecord);
	//区域会计界面
	if(baseinfo.advertiseType.dg === advertiseType){
		if(baseinfo.viewState.add != viewState){
			win.down('grid').store.load();
		}else{
			win.down('grid').store.loadData([]);
		}
	}
	return win;
};
//保存事件 
baseinfo.salesDeptAccountantIndex.submitEntity = function(win,viewState,operatEntity,advertiseType){
	var grid = Ext.getCmp('T_baseinfo-salesDeptAccountantIndex_content').getQueryGrid()
		,aType = baseinfo.advertiseType.dg === advertiseType		//标识 是区域会计DG还是部门区域会计DDG
		,url = aType?'../baseinfo/addBillSloganEntity.action':'../baseinfo/addBillSloganAppOrgEntity.action'
		,m_success = '保存成功！',m_failure = '保存失败！',m_dateError = '数据异常！'	
		,objectVo = {};
	if(aType){
		objectVo.billSloganEntity = operatEntity;
	}else{
		objectVo.billSloganAppOrgEntity = operatEntity;
	}
	//如果是修改 则调用修改 action
	if(baseinfo.viewState.update === viewState){
		url = aType?'../baseinfo/updateBillSloganEntity.action':'../baseinfo/updateBillSloganAppOrgEntity.action';
	}
	baseinfo.requestAjaxJson(url,{'objectVo':objectVo},function(result){
		if(baseinfo.operatorCount.successV === result.objectVo.returnInt){
			//预操作grid加载数据
			grid.store.loadPage(1);
			if(aType){
				if(baseinfo.viewState.add === viewState){
					//只有保存后，才能点击"添加部门广告语"；
					win.down('button').setDisabled(false);
					// 把虚拟编码 写到 win表单上
					win.formRecord.set('virtualCode',result.objectVo.billSloganEntity.virtualCode);
				}
				//查看状态下 只有 取消按钮可用 [添加部门广告语,取消]按钮分别占 0和1
				baseinfo.viewState.operateWinBtn(win,viewState,baseinfo.operateType.save);
				//form表单元素都设置成只读
				baseinfo.formFieldSetReadOnly(true,win.down('form'));
			}else{
				win.hide();
			}
			//提示保存成功
			baseinfo.showInfoMsg(m_success);
		}else{
			baseinfo.showInfoMsg(result.message);
		}
	},function(result){
		baseinfo.showInfoMsg(result.message);
	});
};
//作废事件
baseinfo.salesDeptAccountantIndex.deleteEntityByCode = function(delType,operatRecord,grid,advertiseType){
//	var grid = Ext.getCmp('T_baseinfo-salesDeptAccountantIndex_content').getQueryGrid()
	var aType = baseinfo.advertiseType.dg === advertiseType		//标识 是区域会计DG还是部门区域会计DDG
		,url = aType?'../baseinfo/deleteBillSloganEntity.action':'../baseinfo/deleteBillSloganAppOrgEntity.action'
		,objectVo = {},keyId = aType?'virtualCode':'id';
	selection=grid.getSelectionModel().getSelection();
	if(selection.length<=0 && Ext.isEmpty(operatRecord)){
		Ext.MessageBox.alert('提醒','请选择一条数据！');
	}else{	
		if(!Ext.isEmpty(delType)&&baseinfo.delType===delType){
			var codeStr = '';
			//批量作废
			url = '../baseinfo/deletePickupAndDeliverySmallZone.action';
			for (var j = 0; j < selection.length; j++) {
				codeStr = codeStr + ',' + selection[j].get(keyId+'');
			}
			objectVo.codeStr = codeStr;
		}else{
			objectVo.codeStr = operatRecord.get(keyId+'');
		}
		Ext.MessageBox.buttonText.yes = '确认';
		Ext.MessageBox.buttonText.no = '取消';
		Ext.Msg.confirm('FOSS提醒您','是否要作废当前记录？',function(btn,text) {
			if (btn == 'yes') {
				baseinfo.requestAjaxJson(url,{'objectVo':objectVo},function(result){
					grid.store.loadPage(1);
					baseinfo.showInfoMsg('作废成功！');
				},function(result){
					baseinfo.showInfoMsg(result.message);
				});
			}
		});
	}
};
baseinfo.salesDeptAccountantIndex.entityIsExist = function(field,fieldValue,fieldLabel,fieldNmae){
	var url = '../baseinfo/billSloganEntityExist.action',objectVo ={}
	,entytyRecord = Ext.create('Foss.baseinfo.salesDeptAccountantIndex.SalesDeptAccountantEntityModel');
	entytyRecord.set(fieldNmae+'',fieldValue);
	objectVo.pickupAndDeliverySmallZoneEntityList = entytyRecord.data;
	baseinfo.requestAjaxJson(url,{'objectVo':objectVo},function(result){
		if(Ext.isEmpty(result.objectVo.pickupAndDeliverySmallZoneEntity)){
			field.clearInvalid();
		}else{
			field.markInvalid('数据重复，请修改【'+fieldLabel+'】！');
		}
	},function(result){
		field.markInvalid('数据重复，请修改【'+fieldLabel+'】！');
	});
};

//------------------------------------MODEL----------------------------------
//区域会计Model
Ext.define('Foss.baseinfo.salesDeptAccountantIndex.SalesDeptAccountantEntityModel', {
extend: 'Ext.data.Model',
fields : [//会计姓名
    {name:'accountantName',type:'string'},
    //会计工号
    {name:'accountantNO',type:'string'},
    //会计所属部门
    {name:'accountantDept',type:'string'},
    //所辖营业部
    {name:'salesDepartment',type:'string'},
    //虚拟code
    {name:'virtualCode',type:'string'},
    //是否启用
    {name:'active',type:'string'}]
});
//部门区域会计Model
Ext.define('Foss.baseinfo.salesDeptAccountantIndex.BillSloganAppOrgEntityModel', {
	extend: 'Ext.data.Model',
	fields : [//适用部门Code
        {name:'orgCode',type:'string'},
        //部门广告语内容
        {name:'sloganContent',type:'string'},
        //广告语类型：DictionaryValueConstants.BSE_SLOGAN_TYPE_SMS：短信广告语 DictionaryValueConstants.BSE_SLOGAN_TYPE_BILL：区域会计语
        {name:'sloganSort',type:'string'},
        //是否启用
        {name:'active',type:'string'},
        //所属广告语code
        {name:'sloganCode',type:'string'}]
	});
//------------------------------------STORE----------------------------------
//区域会计STORE
Ext.define('Foss.baseinfo.salesDeptAccountantIndex.BillSloganEntityStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.baseinfo.salesDeptAccountantIndex.SalesDeptAccountantEntityModel',
	pageSize:20,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url : baseinfo.realPath('queryBillSloganEntityByEntity.action'),
		reader : {
			type : 'json',
			root : 'objectVo.billSloganEntityList',
			totalProperty : 'totalCount'
		}
	}
});
//部门区域会计STORE
Ext.define('Foss.baseinfo.salesDeptAccountantIndex.BillSloganAppOrgEntityStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.baseinfo.salesDeptAccountantIndex.BillSloganAppOrgEntityModel',
	pageSize:20,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url : baseinfo.realPath('queryBillAdvertisingSloganForDepts.action'),
		reader : {
			type : 'json',
			root : 'objectVo.billSloganAppOrgEntityList'
//				,
//			totalProperty : 'totalCount'
		}
	}
});
//------------------------------------FORM----------------------------------
//区域会计 查询条件
Ext.define('Foss.baseinfo.salesDeptAccountantIndex.QueryConditionForm', {
	extend : 'Ext.form.Panel',
	title: '查询条件',
	frame: true,
	collapsible: true,
    defaults : {
    	margin : '8 10 5 10',
    	//labelSeparator:'',
    	labelWidth:130
    },
    height :140,
	defaultType : 'textfield',
	layout:{
        type: 'table',
        columns: 4
    },
    record:null,												//绑定的model Foss.baseinfo.salesDeptAccountantIndex.SalesDeptAccountantEntityModel
	constructor : function(config) {							//构造器
		var me = this,cfg = Ext.apply({}, config);
		me.items = me.getItems();
		me.callParent([cfg]);
	},
	getItems:function(){
		var me = this;
		return [{
			fieldLabel:'广告语代码',							//广告语代码
			name:'sloganCode'
		},{
			fieldLabel:'广告语名称',							//广告语名称
			name:'sloganName'
		},{
			fieldLabel:'所属子系统',							//所属子系统
			name:'subSystem'
		},{
			fieldLabel:'子系统功能模块',						//子系统功能模块
			name:'subSystemModule'
		}];
	},
	buttons : [ {
		text : '查询',
		cls:'yellow_button',
		handler : function() {
			var grid  = Ext.getCmp('T_baseinfo-salesDeptAccountantIndex_content').getQueryGrid();//得到grid
			grid.store.loadPage(1);//用分页的moveFirst()方法
		}
	}, {
		text : '重置',
		cls:'yellow_button',
		handler : function() {
			this.up('form').getForm().reset();
		}
	}]
});
//区域会计 界面form
Ext.define('Foss.baseinfo.salesDeptAccountantIndex.BillSloganEntityWinForm', {
	extend : 'Ext.form.Panel',
	defaultType : 'textfield',
	autoScroll:true,
	frame:true,
	layout:{
        type: 'table',
        columns: 2
    },
    formRecord:null,												//绑定的model Foss.baseinfo.salesDeptAccountantIndex.SalesDeptAccountantEntityModel
    formStore:null,													//绑定的formStore Foss.baseinfo.salesDeptAccountantIndex.PickupAndDeliveryBillSloganEntityStore
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
			allowBlank:false,
//			width:300,
			readOnly:(baseinfo.viewState.view === config.viewState)?true:false
	    };
	},
	getItems:function(config){
		var me = this;
		return [{
			fieldLabel:'所属子系统',							//区域会计编码
			name:'subSystem'
		},{
			fieldLabel:'子系统功能模块',							//区域会计名称
			name:'subSystemModule'
		},{
			fieldLabel:'广告语代码',							//上机部门名称
			name:'sloganCode'
		},{
			fieldLabel:'广告语名称',							//上机部门名称
			name:'sloganName'
		},{
			colspan:2,
			xtype:'textareafield',
			fieldLabel:'广告语内容',	
			name:'content',
			allowBlank:true,
			width:500,
			height:40
		}];
	}
});
//TODO 部门区域会计  界面form
//------------------------------------GRID----------------------------------
//区域会计 查询结果grid
Ext.define('Foss.baseinfo.salesDeptAccountantIndex.QueryResultGrid', {
	extend: 'Ext.grid.Panel',
	title : '查询结果列表',
    sortableColumns:false,
    enableColumnHide:false,
    enableColumnMove:false,
	stripeRows : true, 									// 交替行效果
	selType : "rowmodel", 								// 选择类型设置为：行选择
	emptyText: '查询结果为空',							//查询结果为空
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
			text : '新增',								//新增
			//hidden:!pricing.isPermission('../pricing/saveRole.action'),
			handler :function(){
				me.addBillSloganEntity({}).show();
			} 
		},'-', {
			text : '作废',								//作废
			//hidden:!pricing.isPermission('../pricing/deleteRole.action'),
			handler :function(){
				baseinfo.salesDeptAccountantIndex.deleteEntityByCode(baseinfo.delType);
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
	//得到区域会计编辑窗体 得到区域会计编辑窗体,查看状态viewState："ADD"新增,"UPDATE"修改,"VIEW"查看
	getBillSloganEntityWin:function(win,title,viewState,param){
		var formRecord = Ext.isEmpty(param.formRecord)?Ext.create('Foss.baseinfo.salesDeptAccountantIndex.SalesDeptAccountantEntityModel'):param.formRecord;
		if (Ext.isEmpty(win)) {
			win = Ext.create('Foss.baseinfo.salesDeptAccountantIndex.BillSloganEntityWin',{
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
	addBillSloganEntityWin:null,						//新增基区域会计
	addBillSloganEntity:function(param){
		return this.getBillSloganEntityWin(this.addBillSloganEntityWin,'新增区域会计',baseinfo.viewState.add,param);
	},
	updateBillSloganEntityWin:null,						//修改基区域会计
	updateBillSloganEntity:function(param){
		return this.getBillSloganEntityWin(this.updateBillSloganEntityWin,'修改区域会计',baseinfo.viewState.update,param);
	},
	viewBillSloganEntityWin:null,						//查看基区域会计
	viewBillSloganEntity:function(param){
		return this.getBillSloganEntityWin(this.viewBillSloganEntityWin,'查看区域会计',baseinfo.viewState.view,param);
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
	    	//查看 区域会计
	    	itemdblclick: function(view,record) {
				var param = {};
            	param.formRecord = record;
				me.viewBillSloganEntity(param).show();
	    	}
	    };
	},
	getStore:function(){
		return Ext.create('Foss.baseinfo.salesDeptAccountantIndex.BillSloganEntityStore',{
			autoLoad : false,
			pageSize : 20,
			listeners: {
				beforeload: function(store, operation, eOpts){
					var queryForm = Ext.getCmp('T_baseinfo-salesDeptAccountantIndex_content').getQueryForm().getForm();//得到查询的FORM表单
					queryForm.updateRecord(queryForm.record);
					var entity = queryForm.record.data;
					if(queryForm!=null){
						Ext.apply(operation,{
							params : {
								//广告语代码
								'objectVo.billSloganEntity.sloganCode':entity.sloganCode,
								//广告语名称
								'objectVo.billSloganEntity.sloganName':entity.sloganName,
								//所属子系统
								'objectVo.billSloganEntity.subSystem':entity.subSystem,
								//子系统功能模块
								'objectVo.billSloganEntity.subSystemModule':entity.subSystemModule
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
			text : '操作',//操作
			items: [{
            	iconCls:'deppon_icons_edit',
                tooltip: '修改',
                handler: function(grid, rowIndex, colIndex) {
    				var param = {};
                	param.formRecord = grid.getStore().getAt(rowIndex);
    				me.updateBillSloganEntity(param).show();
    			}
            },{
            	iconCls:'deppon_icons_cancel',
                tooltip: '作废',
                disabled:baseinfo.actioncolumnDisabled,
                handler: function(grid, rowIndex, colIndex) {
    				baseinfo.salesDeptAccountantIndex.deleteEntityByCode(null,grid.getStore().getAt(rowIndex),grid);
                }
            }]
		},{
			text : '广告语代码',									//区域会计编码
			dataIndex : 'sloganCode'
		},{
			text : '广告语名称',									//区域会计名称
			dataIndex : 'sloganName'
		},{
			text : '所属子系统',									//上机部门名称
			dataIndex : 'subSystem'
		},{
			text : '子系统功能模块',											//队员
			dataIndex : 'subSystemModule'
		}];
	}
});
// 部门区域会计  grid
Ext.define('Foss.baseinfo.BillSloganEntityWinGrid', {
	extend: 'Ext.grid.Panel',
	id:'Foss_baseinfo_BillSloganEntityWinGrid_ID',
	title : '部门广告语',
    sortableColumns:false,
    enableColumnHide:false,
    enableColumnMove:false,
	stripeRows : true, 									// 交替行效果
	selType : "rowmodel", 								// 选择类型设置为：行选择
	emptyText: '查询结果为空',							//查询结果为空
	viewState:null,										//查看状态
	frame: true,
	//得到部门区域会计编辑窗体 得到部门区域会计编辑窗体,查看状态viewState："ADD"新增,"UPDATE"修改,"VIEW"查看
	getBillSloganAppOrgWin:function(win,title,viewState,param){
		var formRecord = Ext.isEmpty(param.formRecord)?Ext.create('Foss.baseinfo.salesDeptAccountantIndex.BillSloganAppOrgEntityModel'):param.formRecord;
		var gridData = Ext.isEmpty(param.gridDate)?[]:param.gridDate;
		if (Ext.isEmpty(win)) {
			win = Ext.create('Foss.baseinfo.salesDeptAccountantIndex.BillSloganAppOrgEntityWin',{
				'title':title,
				'sourceGrid':this,
				'viewState':viewState,
				'formRecord':formRecord,
				'gridDate':gridData
			});
		}
		//加载数据
		return baseinfo.salesDeptAccountantIndex.initWinData(win,viewState,formRecord,gridData,baseinfo.advertiseType.ddg);
	},
	addBillSloganAppOrgWin:null,						//新增基部门区域会计
	addBillSloganAppOrg:function(param){
		return this.getBillSloganAppOrgWin(this.addBillSloganAppOrgWin,'新增空运代理',baseinfo.viewState.add,param);
	},
	updateBillSloganAppOrgWin:null,						//修改基部门区域会计
	updateBillSloganAppOrg:function(param){
		return this.getBillSloganAppOrgWin(this.updateBillSloganAppOrgWin,'修改空运代理',baseinfo.viewState.update,param);
	},
	viewBillSloganAppOrgWin:null,						//查看基部门区域会计
	viewBillSloganAppOrg:function(param){
		return this.getBillSloganAppOrgWin(this.viewBillSloganAppOrgWin,'查看空运代理',baseinfo.viewState.view,param);
	},
	constructor : function(config){
		var me = this, cfg = Ext.apply({}, config);
		me.columns = me.getColumns(config);
		me.store = me.getGridStore(config);
		me.listeners = me.getMyListeners(config);
		//添加头部按钮
		me.tbar = me.getTbar();
		me.callParent([cfg]);
	},
	//监听事件
	getMyListeners:function(config){
		var me = this;
		return {
		    //增加滚动条事件，防止出现滚动条后却不能用
	    	scrollershow: function(scroller) {
	    		if (scroller && scroller.scrollEl) {
	    				scroller.clearManagedListeners(); 
	    				scroller.mon(scroller.scrollEl, 'scroll', scroller.onElScroll, scroller); 
	    		}
	    	},
	    	//查看 偏线代理网点
	    	itemdblclick: function(view,record) {
				var param = {};
            	param.formRecord = record;
				me.viewBillSloganAppOrg(param).show();
	    	}
	    };
	},
	getTbar:function(config){
		var me = this;
		return [{
			text : '添加部门广告语',							//添加网点
			name:'grid_addDeptBtn_name',
			//hidden:!pricing.isPermission('../pricing/saveRole.action'),
			handler :function(){
				var param = {},formRecord = Ext.create('Foss.baseinfo.salesDeptAccountantIndex.BillSloganAppOrgEntityModel');
				param.formRecord = formRecord;
				me.addBillSloganAppOrg(param).show();
			} 
		}];
	},
	//表格数据源
	getGridStore:function(config){
		var me = this;
		return Ext.create('Foss.baseinfo.AirAgencyCompanyDeptStore',{
			autoLoad : false,
			listeners: {
				beforeload: function(store, operation, eOpts){
					Ext.apply(operation,{
						params : {
							//代理公司 虚拟code
							'objectVo.businessPartnerEntity.virtualCode':me.up('window').formRecord.get('virtualCode')
						}
					});	
				}
		    }
		});
	},
	//表格数据列
	getColumns:function(config){
		var me = this;
		return [{
			align : 'center',
			xtype : 'actioncolumn',
			hidden:(baseinfo.viewState.view === config.viewState),
			text : '操作',//操作
			items: [{
            	iconCls:'deppon_icons_edit',
                tooltip: '修改',
                disabled:baseinfo.actioncolumnDisabled,
                handler: function(grid, rowIndex, colIndex) {
    				var param = {};
                	var record = grid.getStore().getAt(rowIndex);				//选中的计费规则数据
                	param.formRecord = record;
    				me.updateBillSloganAppOrg(param).show();
    			}
            },{
            	iconCls:'deppon_icons_cancel',
                tooltip: '作废',
                disabled:baseinfo.actioncolumnDisabled,
                handler: function(grid, rowIndex, colIndex) {
                	baseinfo.salesDeptAccountantIndex.deleteEntityByCode(null,grid.getStore().getAt(rowIndex),grid,baseinfo.agencyType.kx);
                }
            }]
		},{
			text : '适用部门',						//适用部门
			flex:1,
			dataIndex : 'agentDeptName'
		},{
			text : '广告语内容',						//广告语内容
			flex:1,
			dataIndex : 'provName'
		}];
	}
});
//------------------------------------ONREADY----------------------------------
/**
 * 程序入口方法
 */
Ext.onReady(function() {
	Ext.QuickTips.init();
	if (Ext.getCmp('T_baseinfo-salesDeptAccountantIndex_content')){
		return;
	}
	var queryForm  = Ext.create('Foss.baseinfo.salesDeptAccountantIndex.QueryConditionForm',{'record':Ext.create('Foss.baseinfo.salesDeptAccountantIndex.SalesDeptAccountantEntityModel')});//查询FORM
	var queryGrid  = Ext.create('Foss.baseinfo.salesDeptAccountantIndex.QueryResultGrid');//查询结果显示列表
	Ext.getCmp('T_baseinfo-salesDeptAccountantIndex').add(Ext.create('Ext.panel.Panel', {
		id : 'T_baseinfo-salesDeptAccountantIndex_content',
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
			text:'作废',								//作废
			//hidden:!pricing.isPermission('../pricing/deleteRole.action'),
			handler :function(){
				baseinfo.salesDeptAccountantIndex.deleteEntityByCode(baseinfo.delType);
			}
		}]
	}));
});
//------------------------------------WINDOW--------------------------------
//区域会计界面win
Ext.define('Foss.baseinfo.salesDeptAccountantIndex.BillSloganEntityWin',{
	extend : 'Ext.window.Window',
	title : '新增区域会计',								//新增区域会计   默认新增
	closable : true,
	modal : true,
	resizable:false,
	closeAction : 'hide',
	width :600,
	height :500,	
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	listeners:{
		beforehide:function(me){
			//清空 有ID的组件
			var winGrid = Ext.getCmp('Foss_baseinfo_BillSloganEntityWinGrid_ID');
			if(!Ext.isEmpty(winGrid)){
				winGrid.destroy();
			}
		}
	},
	viewState:baseinfo.viewState.add,				//查看状态,默认为新增
	editForm:null,											//区域会计表单Form
	editGrid:null,											//区域会计表格Grid
	formRecord:null,										//区域会计实体 Foss.baseinfo.BusinessPartnerModel
	gridDate:null,											//区域会计 网点信息数组  [Foss.baseinfo.OuterBranchModel]
    constructor : function(config) {
		var me = this,cfg = Ext.apply({}, config);
		me.editForm = Ext.create('Foss.baseinfo.salesDeptAccountantIndex.BillSloganEntityWinForm',{'height':150,'viewState':config.viewState,'formRecord':config.formRecord});
		me.editGrid = Ext.create('Foss.baseinfo.BillSloganEntityWinGrid',{'height':200,'viewState':me.viewState});
		me.items = [me.editForm,me.editGrid];
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
			text : '取消',
			handler :function(){
				me.hide();
			}
		},{
			text : '重置',
			disabled:(baseinfo.viewState.view === config.viewState),
			handler :function(){
				// 重置
				baseinfo.formReset([me.editForm.getForm()],[me.formRecord]);
			} 
		},{
			text : '保存',
			disabled:(baseinfo.viewState.view === config.viewState),
			handler :function(){
		    	var editForm = me.editForm.getForm();
		    	//实时校验的 结果是否通过,判断偏线代理必填项是否填写并全部填写合法
		    	if(!editForm.isValid()){
		    		baseinfo.showInfoMsg('请检测数据是否填写完全并填写正确！');
		    		return;
		    	}
	    		editForm.updateRecord(me.formRecord);
	    		baseinfo.salesDeptAccountantIndex.submitEntity(me,me.viewState,me.formRecord.data,baseinfo.advertiseType.dg);
			}
		}];
	}
});
//部门区域会计界面win
Ext.define('Foss.baseinfo.salesDeptAccountantIndex.BillSloganAppOrgEntityWin',{
	extend : 'Ext.window.Window',
	title : '新增部门广告语',								//新增部门广告语   默认新增
	closable : true,
	modal : true,
	resizable:false,
	closeAction : 'hide',
	width :300,
	height :200,	
	viewState:baseinfo.viewState.add,				//查看状态,默认为新增
	editForm:null,											//区域会计表单Form
	editGrid:null,											//区域会计表格Grid
	formRecord:null,										//区域会计实体 Foss.baseinfo.BusinessPartnerModel
	gridDate:null,											//区域会计 网点信息数组  [Foss.baseinfo.OuterBranchModel]
    constructor : function(config) {
		var me = this,cfg = Ext.apply({}, config);
		me.items = [{	
			xtype : 'form',
			layout:'column',
			defaults:{allowBlank:false},
		    items:[{
		    	xtype:'combo',
		    	fieldLabel:'适用部门'
		    },{
		    	xtype:'textareafield',
		    	fieldLabel:'广告语内容',
		    	height:60
		    }]
		}];
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
			text : '取消',
			handler :function(){
				me.hide();
			}
		},{
			text : '重置',
			disabled:(baseinfo.viewState.view === config.viewState),
			handler :function(){
				// 重置
				baseinfo.formReset([me.down('form').getForm()],[me.formRecord]);
			} 
		},{
			text : '保存',
			disabled:(baseinfo.viewState.view === config.viewState),
			handler :function(){
		    	var editForm = me.down('form').getForm();
		    	//实时校验的 结果是否通过,判断偏线代理必填项是否填写并全部填写合法
		    	if(!editForm.isValid()){
		    		baseinfo.showInfoMsg('请检测数据是否填写完全并填写正确！');
		    		return;
		    	}
	    		editForm.updateRecord(me.formRecord);
	    		baseinfo.salesDeptAccountantIndex.submitEntity(me,me.viewState,me.formRecord.data,baseinfo.advertiseType.ddg);
			}
		}];
	}
});