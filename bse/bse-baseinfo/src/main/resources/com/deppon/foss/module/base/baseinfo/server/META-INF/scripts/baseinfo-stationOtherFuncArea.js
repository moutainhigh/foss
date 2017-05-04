//保存事件 
baseinfo.stationOtherFuncArea.submitSstationOtherFuncAreaEntity = function(win,viewState,operatEntity){
	var grid = Ext.getCmp('T_baseinfo-stationOtherFuncArea_content').getQueryGrid()
		,url = baseinfo.realPath('addStationOtherFuncArea.action')
		,m_success =baseinfo.stationOtherFuncArea.i18n('foss.baseinfo.saveSuccessful')							//保存成功！
		,m_failure =baseinfo.stationOtherFuncArea.i18n('foss.baseinfo.saveFail')								//保存失败！
		,m_dateError =baseinfo.stationOtherFuncArea.i18n('foss.baseinfo.expressDeliverySmallZone.dataError')								//数据异常！
		,objectVo = {};
	objectVo.stationOtherFuncAreaEntity = operatEntity;
	 
	if('ADD' === viewState){
		 
	}else if('UPDATE' === viewState){
		
		url = baseinfo.realPath('updateStationOtherFuncArea.action');
	}
	baseinfo.requestAjaxJson(url,{'objectVo':objectVo},function(result){
		if(!Ext.isEmpty(result.objectVo.stationOtherFuncAreaEntity)){
			grid.store.loadPage(1);
			baseinfo.showInfoMsg(m_success);
			win.hide();
		}else{
			baseinfo.showInfoMsg(result.message);
		}
	},function(result){
		 baseinfo.showInfoMsg(result.message); 
	});
};



//作废事件
baseinfo.stationOtherFuncArea.deletestationOtherFuncAreaByCode = function(delAgencyCompanyType,operatRecord){
	var grid = Ext.getCmp('T_baseinfo-stationOtherFuncArea_content').getQueryGrid(),
		url = baseinfo.realPath('deleteStationOtherFuncArea.action'),
		objectVo = {};
	selection=grid.getSelectionModel().getSelection();
	if(selection.length<=0 && Ext.isEmpty(operatRecord)){
		Ext.MessageBox.alert(baseinfo.stationOtherFuncArea.i18n('foss.baseinfo.airagencycompany.remind'),baseinfo.stationOtherFuncArea.i18n('foss.baseinfo.airagencycompany.selectData'));
	}else{	
		 
		if(!Ext.isEmpty(delAgencyCompanyType)){
			var codeStr = '';
			//批量作废
			url = baseinfo.realPath('deleteStationOtherFuncArea.action');
			for (var j = 0; j < selection.length; j++) {
				codeStr = codeStr + ',' + selection[j].get('id');
			}
			objectVo.codeStr = codeStr;
		}else{
			objectVo.codeStr = operatRecord.get('id');
		}
		Ext.MessageBox.buttonText.yes =baseinfo.stationOtherFuncArea.i18n('foss.baseinfo.sure');
		Ext.MessageBox.buttonText.no =baseinfo.stationOtherFuncArea.i18n('foss.baseinfo.cancel');
		Ext.Msg.confirm(baseinfo.stationOtherFuncArea.i18n('foss.baseinfo.billAdvertisingSlogan.fossAlertU'),baseinfo.stationOtherFuncArea.i18n('foss.baseinfo.billAdvertisingSlogan.confirmAlertRecord'),function(btn,text) {
			if (btn == 'yes') {
				baseinfo.requestAjaxJson(url,{'objectVo':objectVo},function(result){
					grid.store.loadPage(1);
					baseinfo.showInfoMsg(baseinfo.stationOtherFuncArea.i18n('foss.baseinfo.expressDeliverySmallZone.deleteSuccess'));
				},function(result){
					baseinfo.showInfoMsg(result.message);
				});
			}
		});
	}
};



//弹出界面上 数据渲染
baseinfo.stationOtherFuncArea.initParentWin = function(win,viewState,formRecord,gridData){
	if('ADD' === viewState){
		//新增时 必填项不用
		win.editForm.getForm().reset();
	}else{
		// 公共组件 
		win.editForm.down('commontransfercenterselector').setCombValue(formRecord.get('deptName'),formRecord.get('deptNo'));//部门
		//加载数据
		win.editForm.loadRecord(formRecord);
	}
	 
	return win;
};

//查询FORM
Ext.define('Foss.baseinfo.stationOtherFuncArea.QueryForm', {
	extend : 'Ext.form.Panel',
	title:'查询条件',
	frame: true,
	collapsible: true,
    defaults : {
    	margin : '8 10 5 10',
    	//labelWidth:130
    },
    height :180,
	layout:{
        type: 'table',
        columns: 1
    },
    record:null,												 
	constructor : function(config) {							 
		var me = this,cfg = Ext.apply({}, config);
		me.items = me.getItems();
		me.fbar=[{
						width: 75,
						text :'重置',
						margin : '0 800 0 0',
						disabled:!baseinfo.stationOtherFuncArea.isPermission('stationOtherFuncArea/stationOtherFuncAreaQueryButton'),//按钮权限
						hidden:!baseinfo.stationOtherFuncArea.isPermission('stationOtherFuncArea/stationOtherFuncAreaQueryButton'),//按钮权限
						handler : function() {
							this.up('form').getForm().reset();
						}
					},{
						xtype:'button',
						margin : '0 20 0 0',
						width: 75,
						disabled:!baseinfo.stationOtherFuncArea.isPermission('stationOtherFuncArea/stationOtherFuncAreaQueryButton'),
						hidden:!baseinfo.stationOtherFuncArea.isPermission('stationOtherFuncArea/stationOtherFuncAreaQueryButton'),
						text : '查询',
						cls:'yellow_button',
						handler : function() {
							Ext.getCmp('T_baseinfo-stationOtherFuncArea_content').getQueryGrid().getPagingToolbar().moveFirst();
						}
					}]
		
		me.callParent([cfg]);
	},
	getItems:function(){
		var me = this;
		return [{
					fieldLabel:'外场',
					xtype:'commontransfercenterselector',
					userCode:FossUserContext.getCurrentUserEmp().empCode,
					currentOrgCode :FossUserContext.getCurrentDeptCode(),
					transfer:'Y',
					name:'deptNo',
					flag:'Y'
				}];
	}
});

Ext.define('Foss.baseinfo.stationOtherFuncArea.QueryGrid', {
	extend: 'Ext.grid.Panel',
	title :'查询结果',
    sortableColumns:false,
    enableColumnHide:false,
    enableColumnMove:false,
    cls: 'autoHeight',
	bodyCls: 'autoHeight', 
	stripeRows : true, 									// 交替行效果
	selType : "rowmodel", 								// 选择类型设置为：行选择
	emptyText:'',							//查询结果为空
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
			text :'新增',								//新增
			disabled:!baseinfo.stationOtherFuncArea.isPermission('stationOtherFuncArea/stationOtherFuncAreaAddButton'),   //按钮权限
			hidden:!baseinfo.stationOtherFuncArea.isPermission('stationOtherFuncArea/stationOtherFuncAreaAddButton'),   //按钮权限
			handler :function(){
				me.getAddStationOtherFuncAreaWin({}).show();
			} 
		},'-', {
			text :'作废',								//作废
			disabled:!baseinfo.stationOtherFuncArea.isPermission('stationOtherFuncArea/stationOtherFuncAreaDisableButton'),
			hidden:!baseinfo.stationOtherFuncArea.isPermission('stationOtherFuncArea/stationOtherFuncAreaDisableButton'),
			handler :function(){
				
				baseinfo.stationOtherFuncArea.deletestationOtherFuncAreaByCode(1,null);	
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
	//得到快递收派小区编辑窗体
	getAgencyDeptWin:function(win,title,viewState,param,id){
		var formRecord = Ext.isEmpty(param.formRecord)?Ext.create('Foss.baseinfo.stationOtherFuncArea.StationOtherFuncAreaModel'):param.formRecord;
		if (Ext.isEmpty(win)) {
			win = Ext.create('Foss.baseinfo.stationOtherFuncArea.StationOtherFuncAreaWin',{
				'title':title,
				'viewState':viewState,
				'sourceGrid':this,
				'formRecord':formRecord,
				'id':id
			});
		}
		return baseinfo.stationOtherFuncArea.initParentWin(win,viewState,formRecord,null);
	},
	addStationOtherFuncAreaWin:null,						 
	getAddStationOtherFuncAreaWin:function(param){
		var addForm=Ext.getCmp('T_stationOtherFuncArea_ADD');
		if(null!=addForm){
			addForm.editForm.getForm().reset()
			return addForm;
		}
		return this.getAgencyDeptWin(this.addStationOtherFuncAreaWin,'新增窗体','ADD',param,'T_stationOtherFuncArea_ADD');
	},
	updateStationOtherFuncAreaWin:null,						 
	getUpdateStationOtherFuncAreaWin:function(param){
		return this.getAgencyDeptWin(this.updateStationOtherFuncAreaWin,'更新窗体','UPDATE',param,null);
	},
	getMyListeners:function(){
		var me = this;
		return {
		    //增加滚动条事件，防止出现滚动条后却不能用
	    	scrollershow: function(scroller) {
	    		 
	    	},
	    	//查看 快递收派小区
	    	itemdblclick: function(view,record) {
				
	    	}
	    };
	},
	getStore:function(){
		return Ext.create('Foss.baseinfo.stationOtherFuncArea.StationOtherFuncAreaStore',{
			autoLoad : false,
			pageSize : 20,
			listeners: {
				beforeload: function(store, operation, eOpts){
					var queryForm = Ext.getCmp('T_baseinfo-stationOtherFuncArea_content').getQueryForm().getForm();//得到查询的FORM表单
					queryForm.updateRecord(queryForm.record);
					var entity = queryForm.record.data;
					if(queryForm!=null){
						Ext.apply(operation,{
							params : {
								'objectVo.stationOtherFuncAreaEntity.deptNo':entity.deptNo,
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
			xtype: 'rownumberer',
			text:'序号',
			width:40
		},{
			align : 'center',
			xtype : 'actioncolumn',
			text :'操作',//操作
			items: [{
				iconCls : 'deppon_icons_edit',
				tooltip :'修改', //修改
                disabled:!baseinfo.stationOtherFuncArea.isPermission('stationOtherFuncArea/stationOtherFuncAreaEditButton'),
				width : 42,
				handler : function (grid, rowIndex, colIndex) {
					var param = {};
                	param.formRecord = grid.getStore().getAt(rowIndex);
					me.getUpdateStationOtherFuncAreaWin(param).show();
				}
			}, {
				iconCls : 'deppon_icons_cancel',
				tooltip :'作废', //作废
				disabled:!baseinfo.stationOtherFuncArea.isPermission('stationOtherFuncArea/stationOtherFuncAreaDisableButton'),
				width : 42,
				handler : function (grid, rowIndex, colIndex) {
					baseinfo.stationOtherFuncArea.deletestationOtherFuncAreaByCode(null,grid.getStore().getAt(rowIndex));

				}
			}]
		},{
			text :'外场名称',									
			dataIndex : 'deptName',
			width : 130,
		},{
			text :'外场编号',									
			dataIndex : 'deptNo',
			width : 100,
			 
		},{
			text :'功能区名称',									
			dataIndex : 'funcAreaName'
		},{
			text :'功能区编号',											
			dataIndex : 'funcAreaNo'
		},{
			text :'横坐标',											
			dataIndex : 'lat',
		},{
			text :'纵坐标',											
			dataIndex : 'lng',
		},{
			text :'功能区长度(单位:米)',											
			dataIndex : 'funcAreaWidth',
		},{
			text :'功能区宽度(单位:米)',											
			dataIndex : 'funcAreaHeight',
		},{
			text :'备注',											
			dataIndex : 'remark',
		}];
	}
});
//------------------------------------------------------------------------------
//
Ext.define('Foss.baseinfo.stationOtherFuncArea.StationOtherFuncAreaWin',{
	extend : 'Ext.window.Window',
	title :'新增场内其他功能区',								 
	closable : true,
	modal : true,
	resizable:false,
	closeAction : 'hide',
	width :670,
	height :300,	
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	editForm:null,											
	editGrid:null,											
	formRecord:null,										
	gridDate:null,	
	viewState:'ADD',
    constructor : function(config) {
		var me = this,cfg = Ext.apply({}, config);
		me.editForm = Ext.create('Foss.baseinfo.stationOtherFuncArea.StationOtherFuncAreaWinForm');
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
		return [
		   {
			text :'取消',
			handler :function(){
				me.hide();
			}
		},{
			text :'重置',
			handler :function(){
				baseinfo.stationOtherFuncArea.initParentWin(me,config.viewState,config.formRecord,null);
			} 
		},{
			text :'保存',
			handler :function(){
				var editForm = me.editForm.getForm();
				if(editForm.isValid()){
					baseinfo.stationOtherFuncArea.submitSstationOtherFuncAreaEntity(me,me.viewState,editForm.getValues());
				}
			}
		}];
	}
});

//新增、修改FORM
Ext.define('Foss.baseinfo.stationOtherFuncArea.StationOtherFuncAreaWinForm', {
	extend : 'Ext.form.Panel',
	defaultType : 'textfield',
	layout:{
        type: 'table',
        columns: 2
    },
    formRecord:null,												
    formStore:null,								
    viewState:null,
    id:null,
    constructor : function(config) {							 
		var me = this,cfg = Ext.apply({}, config);
		me.defaults = me.getDefaults(config);
		me.items = me.getItems(config);
		me.callParent([cfg]);
	},
	getDefaults:function(config){
		var me = this;
		return {
			allowBlank:false,
			labelWidth : 140,
	    };
	},
	getItems:function(config){
		var me = this;
		return [{
			xtype : 'hiddenfield',
			hidden:true,				
			name:'id'
			},{
			xtype : 'hiddenfield',
			hidden:true,				
			name:'deptName'
			},{
				xtype : 'hiddenfield',
				hidden:true,				
				name:'deptCode'
				},{
			fieldLabel:'外场名称',
			xtype:'commontransfercenterselector',
			transfer:'Y',
			userCode:FossUserContext.getCurrentUserEmp().empCode,
			currentOrgCode :FossUserContext.getCurrentDeptCode(),
			name:'code',
			flag:'Y',
			listeners : {
				select : function(text, records, eops) {
					me.getForm().findField('deptNo').setValue(records[0].get('code'));
					me.getForm().findField('deptName').setValue(records[0].get('name'));
					me.getForm().findField('deptCode').setValue(records[0].get('orgCode'));
				}
			}
		},{
			fieldLabel:'外场编号',							
			name:'deptNo',
			readOnly:true
		},{
			fieldLabel:'功能区名称',							
			name:'funcAreaName',
			maxLength:50,
			maxLengthText:'长度不能超过50'
		},{
			fieldLabel:'功能区编号',
			allowBlank:true,
			name:'funcAreaNo',
			maxLength:50,
			maxLengthText:'长度不能超过50'
		},{
			fieldLabel:'横坐标',
			xtype:'numberfield',
			decimalPrecision:2,
			name:'lat',
			maxValue:99999.99,
			maxText:'不能超过6位整数'
		},{
			fieldLabel:'纵坐标',
			xtype:'numberfield',
			decimalPrecision:2,
			name:'lng',
			maxValue:99999.99,
			maxText:'不能超过6位整数'
		},{
			fieldLabel:'功能区长度',
			xtype:'numberfield',
			decimalPrecision:2,
			name:'funcAreaWidth',
			value:0,
			maxValue:99999.99,
			minValue:0,
			maxText:'不能超过6位整数'
		},{
			fieldLabel:'功能区宽度',
			xtype:'numberfield',
			decimalPrecision:2,
			name:'funcAreaHeight',
			value:0,
			maxValue:99999.99,
			minValue:0,
			maxText:'不能超过6位整数'
		},{
			fieldLabel:'备注',
			allowBlank:true,
			colspan:2,
			xtype:'textareafield',
			width:557,
			height:60,
			name:'remark',
			maxLength:200,
			maxLengthText:'长度不能超过200'
		}];
	}
});
//model

Ext.define('Foss.baseinfo.stationOtherFuncArea.StationOtherFuncAreaModel', {
	extend: 'Ext.data.Model',
	fields : [{name:'id',type:'stirng'},
	          {name:'deptCode',type:'stirng'},
	          {name:'deptName',type:'stirng'},
	          {name:'deptNo',type:'stirng'},
	          {name:'funcAreaName',type:'stirng'},
	          {name:'funcAreaNo',type:'stirng'},
	          {name:'lat',type:'stirng'},
	          {name:'lng',type:'stirng'},
	          {name:'funcAreaWidth',type:'stirng'},
	          {name:'funcAreaHeight',type:'stirng'},
	          {name:'remark',type:'stirng'}] 
		  
	});
	//------------------------------------STORE----------------------------------
	Ext.define('Foss.baseinfo.stationOtherFuncArea.StationOtherFuncAreaStore', {
		extend : 'Ext.data.Store',
		model : 'Foss.baseinfo.stationOtherFuncArea.StationOtherFuncAreaModel',
		pageSize:20,
		proxy : {
			type : 'ajax',
			actionMethods : 'post',
			url : baseinfo.realPath('queryAll.action'),
			reader : {
				type : 'json',
				root : 'objectVo.stationOtherFuncAreaList',
				totalProperty : 'totalCount'
			}
		}
	});



//------------------------------------ONREADY----------------------------------
/**
 * 程序入口方法
 */
Ext.onReady(function() {
	Ext.QuickTips.init();
	if (Ext.getCmp('T_baseinfo-stationOtherFuncArea_content')){
		return;
	}
	var queryForm  = Ext.create('Foss.baseinfo.stationOtherFuncArea.QueryForm',{'record':Ext.create('Foss.baseinfo.stationOtherFuncArea.StationOtherFuncAreaModel')});//查询FORM
	var queryGrid  = Ext.create('Foss.baseinfo.stationOtherFuncArea.QueryGrid');//查询结果显示列表
	Ext.getCmp('T_baseinfo-stationOtherFuncArea').add(Ext.create('Ext.panel.Panel', {
		id : 'T_baseinfo-stationOtherFuncArea_content',
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



