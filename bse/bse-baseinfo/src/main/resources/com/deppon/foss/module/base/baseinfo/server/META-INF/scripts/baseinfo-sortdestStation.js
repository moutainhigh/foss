//childAdd弹出界面上 数据渲染 packAgeType
baseinfo.sortdestStation.initParentChildGridWinWin = function(grid,win,viewState,formRecord,gridData){
	
	if('childAdd' === viewState){
		//新增时 必填项不用
		var form=win.getSortdestStationChildGridWinForm().getForm();
		form.reset();
		form.findField('packAgeType').setValue('MIX_PACKAGE');
	}else{
//		formRecord.set('parentVirtualCode',grid.parentVirtualCode);
	    //加载数据
		win.getSortdestStationChildGridWinForm().loadRecord(formRecord);
		// 公共组件  
		win.getSortdestStationChildGridWinForm().down('dynamicorgcombselector').setCombValue(formRecord.get('destOrgName'),formRecord.get('destOrgCode'));//部门
		//查询当前外场和方案的所有方案信息
	   grid.getStore().loadPage(1); 	
	}
	return win;
};
//子方案信息操作
baseinfo.sortdestStation.childGridInfoOperateInfo= function(win,operateStaus,record,grid){

	if('childAdd' === operateStaus){
	  var url=baseinfo.realPath('addChildSortdestStations.action');
	  var obj=grid.up().getSortdestStationEntityWinForm().getForm().getValues();
		  record.parentVirtualCode=obj.virtualCode;
	}else if('childUpdate'=== operateStaus) {
	    url=baseinfo.realPath('updateChildSortdestStations.action');
	}
	
	var objectVo={}; 
	objectVo.sortdestStationDto=record;
	baseinfo.requestAjaxJson(url,{'objectVo':objectVo},function(result){
					grid.store.loadPage(1);
					baseinfo.showInfoMsg('操作成功！');
					win.hide();
				},function(result){
					baseinfo.showInfoMsg(result.message);
				});

};
//子表作废

//方案作废事件
baseinfo.sortdestStation.deleteChildSortdestStationByCode = function(grid,delAgencyCompanyType,selection,operatRecord){
	 
	var url = baseinfo.realPath('delChildSortdestStations.action'),
	objectVo = {};
	if(selection.length<=0 && Ext.isEmpty(operatRecord)){
		Ext.MessageBox.alert(baseinfo.sortdestStation.i18n('foss.baseinfo.airagencycompany.remind'),'请选择一条数据进行操作！');
	}else{	
		if(!Ext.isEmpty(delAgencyCompanyType)){
			var codeStr = '';
			//批量作废
			for (var j = 0; j < selection.length; j++) {
				codeStr = codeStr + ',' + selection[j].get('id');
			}
			objectVo.codeStr = codeStr;
		}else{
			objectVo.codeStr = operatRecord.get('id');
		}
		Ext.MessageBox.buttonText.yes =baseinfo.sortdestStation.i18n('foss.baseinfo.sure');
		Ext.MessageBox.buttonText.no =baseinfo.sortdestStation.i18n('foss.baseinfo.cancel');
		
		var remindMsg="是否确定需要删除该记录！";
		Ext.Msg.confirm(baseinfo.sortdestStation.i18n('foss.baseinfo.billAdvertisingSlogan.fossAlertU'),remindMsg,function(btn,text) {
			if (btn == 'yes') {
				baseinfo.requestAjaxJson(url,{'objectVo':objectVo},function(result){
					grid.store.loadPage(1);
					baseinfo.showInfoMsg('操作成功！');
				},function(result){
					baseinfo.showInfoMsg(result.message);
				});
			}
		});
	}
};

//比较
baseinfo.sortdestStation.childGridInfoCompareInfo=function(oldRecord,newRecord){
	//boxNo cellNo destOrgCode packAgeType
	if(oldRecord.boxNo==newRecord.boxNo&&
			oldRecord.cellNo==newRecord.cellNo&&
			oldRecord.destOrgCode==newRecord.destOrgCode
			&&oldRecord.packAgeType==newRecord.packAgeType){
		
		return true;
	}
	return false;
}
//---------------------------------------------------------------------------------------------
//方案作废事件
baseinfo.sortdestStation.deleteSortdestStationByCode = function(delAgencyCompanyType,operatRecord,active){
	var grid = Ext.getCmp('T_baseinfo-sortdestStation_content').getQueryGrid(),
		url = baseinfo.realPath('delSortdestStation.action'),
		objectVo = {};
	selection=grid.getSelectionModel().getSelection();
	if(selection.length<=0 && Ext.isEmpty(operatRecord)){
		Ext.MessageBox.alert(baseinfo.sortdestStation.i18n('foss.baseinfo.airagencycompany.remind'),'请选择一条数据进行操作！');
	}else{	
		if(!Ext.isEmpty(delAgencyCompanyType)&&baseinfo.delAgencyType===delAgencyCompanyType){
			var codeStr = '';
			var origOrgCode='';
			var fieldCodes=new Array();
			//批量作废
			for (var j = 0; j < selection.length; j++) {
				codeStr = codeStr + ',' + selection[j].get('virtualCode');
				origOrgCode=selection[j].get('origOrgCode');
				if(active!=null&&selection[j].get('status')==active){
					var msg=active=='Y'?'你所选择的方案中存在已启用的方案！':'你所选择的方案中存在已禁用的方案！';
					baseinfo.showInfoMsg(msg);
					return;
				}
				/*var index=Ext.Array.indexOf(fieldCodes,origOrgCode,0);
				
				if(index>=0){
					baseinfo.showInfoMsg("你选择的启用方案中有重复,一个外场只能启用一种方案！");
					return;
				}*/
				fieldCodes.push(origOrgCode);
			}
			objectVo.codeStr = codeStr;
		}else{
			objectVo.codeStr = operatRecord.get('virtualCode');
		}
		objectVo.active=active;
		Ext.MessageBox.buttonText.yes =baseinfo.sortdestStation.i18n('foss.baseinfo.sure');
		Ext.MessageBox.buttonText.no =baseinfo.sortdestStation.i18n('foss.baseinfo.cancel');
		
		var remindMsg=null;
		 if(active=="Y"){
			 remindMsg='是否确定启用方案！'; 
		 }else if(active=="N"){
			 remindMsg='是否确定禁用方案！';
		 }else{
			 remindMsg=baseinfo.sortdestStation.i18n('foss.baseinfo.billAdvertisingSlogan.confirmAlertRecord'); 
		 }
		
		
		Ext.Msg.confirm(baseinfo.sortdestStation.i18n('foss.baseinfo.billAdvertisingSlogan.fossAlertU'),remindMsg,function(btn,text) {
			if (btn == 'yes') {
				baseinfo.requestAjaxJson(url,{'objectVo':objectVo},function(result){
					grid.store.loadPage(1);
					baseinfo.showInfoMsg('操作成功！');
				},function(result){
					baseinfo.showInfoMsg(result.message);
				});
			}
		});
	}
};
 
//弹出界面上 数据渲染
baseinfo.sortdestStation.initParentWin = function(win,viewState,formRecord,gridData){
	if(baseinfo.viewState.add === viewState){
		//新增时 必填项不用
		win.getSortdestStationEntityWinForm().getForm().reset();
	}else{
	    //加载数据
		win.getSortdestStationEntityWinForm().loadRecord(formRecord);
		// 公共组件 
		win.getSortdestStationEntityWinForm().down('dynamicorgcombselector').setCombValue(formRecord.get('origOrgName'),formRecord.get('origOrgCode'));//部门
		win.getQueryResultChildGrid().parentVirtualCode=formRecord.virtualCode;
		//查询当前外场和方案的所有方案信息
	    win.getQueryResultChildGrid().getStore().loadPage(1); 	
	}
	return win;
};


var map =new Ext.util.HashMap();;
//
Ext.define('Foss.baseinfo.sortdestStation.SortdestStationModel', {
	extend: 'Ext.data.Model',
	fields : [ {name:'id',type:'string'},
		   
			  {name:'origOrgCode',type:'string'},
			  
			  {name:'origOrgName',type:'string'},
			   
			  {name:'schemeCode',type:'string'},
			   
			  {name:'schemeName',type:'string'},
			   
			  {name:'virtualCode',type:'string'},
			   
			  {name:'active',type:'string'},
			  {name:'status',type:'string'},
			  {name:'scheme',type:'string'}]
	});
 
Ext.define('Foss.baseinfo.sortdestStation.SortdestStationStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.baseinfo.sortdestStation.SortdestStationModel',
	pageSize:20,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url : baseinfo.realPath('querySortdestStation.action'),
		reader : {
			type : 'json',
			root : 'objectVo.sortdestStationEntitys',
			totalProperty : 'totalCount'
		}
	}
});

//查询form
Ext.define('Foss.baseinfo.sortdestStation.QueryConditionForm',{
	extend : 'Ext.form.Panel',
	title:'条件查询',
	frame: true,
	html:"<div id='gisMap' style='position:absolute;top:10%; left:25%; z-index:1; visibility: hidden; '></div>",
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
        columns: 2
    },
    record:null,												//绑定的model Foss.baseinfo.pickupAndDeliverySmallZone.SmallZoneEntityModel
	constructor : function(config) {							//构造器
		var me = this,cfg = Ext.apply({}, config);
		me.items = me.getItems();
		me.callParent([cfg]);
	},
	getItems:function(){
		var me = this;
		return [{
			fieldLabel :'外场',									 
			name : 'origOrgCode',
			xtype:'dynamicorgcombselector',
			type : 'ORG',
			labelWidth:80,
			allowBlank : true,
			transferCenter:'Y', 
			airDispatch  : 'Y'
		},FossDataDictionary.getDataDictionaryCombo('SORTING_SCHEMENAME_TYPE',{
				fieldLabel:'方案类型:',							 
				forceSelection:true,
				name: 'schemeCode',
				labelWidth:80,
				editable:false,
				labelSeparator:'',
				listeners:{
					change:function(field,newValue,oldValue){
						if(Ext.isEmpty(newValue)){
							field.setValue(null);
						}
					}
				}
			}),{
			xtype : 'container', 
			defaultType:'button',
			colspan:2,
			items : [{
				width: 75,
				text :'重置',
				disabled:!baseinfo.sortdestStation.isPermission('querysortdestStationByEntity/sortdestStationQueryButton'),
				hidden:!baseinfo.sortdestStation.isPermission('querysortdestStationByEntity/sortdestStationQueryButton'),
				margin:'0 790 0 0',
				handler : function() {
					this.up('form').getForm().reset();
				}
			},{
				xtype:'button',
				width: 75,
				disabled:!baseinfo.sortdestStation.isPermission('querysortdestStationByEntity/sortdestStationQueryButton'),
				hidden:!baseinfo.sortdestStation.isPermission('querysortdestStationByEntity/sortdestStationQueryButton'),
				text :'查询',
				cls:'yellow_button',
				handler : function() {
					var grid  = Ext.getCmp('T_baseinfo-sortdestStation_content').getQueryGrid();//得到grid
					grid.store.loadPage(1); 
				}
			}]
		}];
	}
});

//第一个grid 
Ext.define('Foss.baseinfo.sortdestStation.QueryResultGrid', {
	extend: 'Ext.grid.Panel',
	title : '显示结果',
    sortableColumns:false,
    enableColumnHide:false,
    enableColumnMove:false,
    cls: 'autoHeight',
	bodyCls: 'autoHeight', 
	stripeRows : true, 									// 交替行效果
	selType : "rowmodel", 								// 选择类型设置为：行选择
	emptyText: '查询结果为空',							//查询结果为空
	queryForm:null,
	frame: true,
	viewConfig: {
		enableTextSelection: true,
		getRowClass:function(record,rowIndex,rowParams,store){
		    var value=record.get('status');
		     if(value=='N'){
			   return 'row-w';
			 }
		}
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
			text : '新增',
			disabled:!baseinfo.sortdestStation.isPermission('sortdestStation/sortdestStationAddButton'),   //按钮权限
			hidden:!baseinfo.sortdestStation.isPermission('sortdestStation/sortdestStationAddButton'),   //按钮权限
			handler :function(){
				me.addSortdestStationeEntity().getSortdestStationFirstAddWinForm().getForm().reset();
				me.addSortdestStationeEntity({}).show();
			} 
		},'-', {
			text :'作废',	
			disabled:!baseinfo.sortdestStation.isPermission('sortdestStation/sortdestStationDelButton'),
			hidden:!baseinfo.sortdestStation.isPermission('sortdestStation/sortdestStationDelButton'),
			handler :function(){
				baseinfo.sortdestStation.deleteSortdestStationByCode(baseinfo.delAgencyType,null); 
			} 
		},'-', {
			text :'启用',		
			disabled:!baseinfo.sortdestStation.isPermission('sortdestStation/sortdestStationActiveButton'),
			hidden:!baseinfo.sortdestStation.isPermission('sortdestStation/sortdestStationActiveButton'),
			handler :function(){
				baseinfo.sortdestStation.deleteSortdestStationByCode(baseinfo.delAgencyType,null,"Y");
			} 
		},'-', {
			text :'禁用',	
			disabled:!baseinfo.sortdestStation.isPermission('sortdestStation/sortdestStationNotActiveButton'),
			hidden:!baseinfo.sortdestStation.isPermission('sortdestStation/sortdestStationNotActiveButton'),
			handler :function(){
				baseinfo.sortdestStation.deleteSortdestStationByCode(baseinfo.delAgencyType,null,"N"); 
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
	//得到接送货小区编辑窗体
	getAgencyDeptWin:function(win,title,viewState,param){
		var formRecord = Ext.isEmpty(param.formRecord)?Ext.create('Foss.baseinfo.sortdestStation.SortdestStationModel'):param.formRecord;
		if (Ext.isEmpty(win)) {
			win = Ext.create('Foss.baseinfo.sortdestStation.SortdestStationEntityWin',{
				'title':title,
				'viewState':viewState,
				'sourceGrid':this,
				'formRecord':formRecord
			});
		}
		return baseinfo.sortdestStation.initParentWin(win,viewState,formRecord,null);
	},
	addSortdestStationEntityWin:null,						 
	addSortdestStationeEntity:function(param){
		 
        if(this.addSortdestStationEntityWin==null){
		   this.addSortdestStationEntityWin=Ext.create('Foss.baseinfo.sortdestStation.SortdestStationFirstAddWin');
		}	
		return this.addSortdestStationEntityWin;
	},
	updateSortdestStationEntityWin:null,						  
	updateSortdestStationEntity:function(param){
		return this.getAgencyDeptWin(this.updateSortdestStationEntityWin,'修改',baseinfo.viewState.update,param);
	},
	viewSortdestStationEntityWin:null,						 
	viewSortdestStationEntity:function(param){
		return this.getAgencyDeptWin(this.viewSortdestStationEntityWin,'查看',baseinfo.viewState.view,param);
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
	    	//查看 接送货小区
	    	itemdblclick: function(view,record) {
				 
	    	}
	    };
	},
	getStore:function(){
		return Ext.create('Foss.baseinfo.sortdestStation.SortdestStationStore',{
			autoLoad : false,
			pageSize : 20,
			listeners: {
				beforeload: function(store, operation, eOpts){
					var queryForm = Ext.getCmp('T_baseinfo-sortdestStation_content').getQueryForm().getForm();//得到查询的FORM表单
					
					var entity = queryForm.getValues();
					if(queryForm!=null){
						Ext.apply(operation,{
							params : {
								'objectVo.sortdestStationEntity.origOrgCode': entity.origOrgCode,
								'objectVo.sortdestStationEntity.schemeCode': entity.schemeCode
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
			items: [{dataIndex: 'flag' ,
				xtype: 'checkcolumn'},{
            	iconCls:'deppon_icons_edit',
                tooltip: '方案详细信息操作',
//                disabled:!baseinfo.sortdestStation.isPermission('querysortdestStationByEntity/sortdestStationEditButton'),
            	/*getClass: function(v, m, r, rowIndex) {
    				
				    if(r.data.status=='Y'){
					  return 'deppon_icons_edit';
					}
					return 'deppon_icons_hide';
				},*/
                handler: function(grid, rowIndex, colIndex) {
					var param = {};
                	param.formRecord = grid.getStore().getAt(rowIndex);
    				var win = me.updateSortdestStationEntity(param);
    				win.show();
    			}
            },{
            	iconCls:'deppon_icons_cancel',
            	disabled:!baseinfo.sortdestStation.isPermission('sortdestStation/sortdestStationDelButton'),
            	hidden:!baseinfo.sortdestStation.isPermission('sortdestStation/sortdestStationDelButton'),
                tooltip:'作废',
                handler: function(grid, rowIndex, colIndex) {
                	baseinfo.sortdestStation.deleteSortdestStationByCode(null,grid.getStore().getAt(rowIndex)); 
                }
            }]
		},{
			text :'外场',									 
			dataIndex : 'origOrgName',
			flex:1,
			width:150
		},{
			text :'方案类型',									 
			dataIndex : 'schemeCode',
			width:150,
			flex:1,
			renderer :function(value ,metaData ,record ){
							  
				return FossDataDictionary.rendererSubmitToDisplay (value,'SORTING_SCHEMENAME_TYPE'); 
			}
		},{
			text :'方案名称',									 
			dataIndex : 'scheme',
			width:150,
			flex:1
		},{
			text :'状态',									 
			dataIndex : 'status',
			width:150,
			flex:1,
			renderer :function(value ,metaData ,record ){
							  
				return value=="N"?"已禁用":"已启用"; 
			}
		}];
	}
});



Ext.onReady(function(){
	
	Ext.QuickTips.init();
	if (Ext.getCmp('T_baseinfo-sortdestStation_content')) {
		return;
	};
	var queryForm  = Ext.create('Foss.baseinfo.sortdestStation.QueryConditionForm');//查询FORM
	var queryGrid  = Ext.create('Foss.baseinfo.sortdestStation.QueryResultGrid',{'queryForm':queryForm});//查询结果显示列表
	Ext.getCmp('T_baseinfo-sortdestStation').add(Ext.create('Ext.panel.Panel',{
		id: 'T_baseinfo-sortdestStation_content',
		cls : 'panelContentNToolbar',
		bodyCls : 'panelContentNToolbar-body',
		getQueryForm:function(){
			
			return queryForm;
		},
		getQueryGrid:function(){
			
			return queryGrid;
		},
		items:[queryForm,queryGrid]
	}));	
	
});
///-----------------------------------11111111111111111111111-------------------------------------------

//第一层新增窗体
Ext.define('Foss.baseinfo.sortdestStation.SortdestStationFirstAddWin',{
	extend : 'Ext.window.Window',
	title : '新增分拣柜目的站',								 
	closable : true,
	modal : true,
    width:660,
	resizable:false,
	closeAction : 'hide',
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	listeners:{
		beforehide:function(me){
			 
		}
	},
	sortdestStationEntityFirstAddForm:null,
	getSortdestStationFirstAddWinForm:function(){
	 if(this.sortdestStationEntityFirstAddForm==null){
			
			this.sortdestStationEntityFirstAddForm=Ext.create('Foss.baseinfo.sortdestStation.SortdestStationFirstAddForm');
		}
		return this.sortdestStationEntityFirstAddForm;
	},
	editForm:null,											
	editGrid:null,											 
	formRecord:null,										 
	gridDate:null,											 
    constructor : function(config) {
		var me = this,cfg = Ext.apply({}, config);
 		me.items = [me.getSortdestStationFirstAddWinForm()];
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
			text :'取消',
			handler :function(){
				me.hide();
			}
		},{
			text :'重置',
			handler :function(){
				// 重置
//				baseinfo.formReset([me.editForm.getForm()],[me.formRecord]);
				me.getSortdestStationFirstAddWinForm().getForm().reset();
			} 
		},{
			text :'保存',
			handler :function(){
				var editForm = me.getSortdestStationFirstAddWinForm().getForm();
				if(editForm.isValid()){
						var objectVo={};
						objectVo.sortdestStationEntity=editForm.getValues();
						baseinfo.requestAjaxJson(baseinfo.realPath('insertSortdestStation.action'),
								{'objectVo':objectVo},function(result){
									var grid=Ext.getCmp('T_baseinfo-sortdestStation_content').getQueryGrid();
									grid.getPagingToolbar().moveFirst();
									grid.addSortdestStationeEntity().hide();
									grid.addSortdestStationeEntity().getSortdestStationFirstAddWinForm().getForm().reset();
						},function(result){
							baseinfo.showInfoMsg(result.message);
						});	
				}
					 		
			}
		}];
	}
});

 

//第一层新增FORM
Ext.define('Foss.baseinfo.sortdestStation.SortdestStationFirstAddForm', {
	extend : 'Ext.form.Panel',
	defaultType : 'textfield',
//	autoScroll:true,
    title:'基础信息',
	layout:{
        type: 'table',
        columns: 2
    },
   /* defaults : {
		labelWidth : 120,
	},*/
	frame:true,
    formRecord:null,												
    formStore:null,													
    viewState:null,
    constructor : function(config) {							 
		var me = this,cfg = Ext.apply({}, config);
		me.items = [{
		   xtype:'hiddenfield',
		   name:'origOrgName',
		   hidden:true,
		},{
			fieldLabel :'方案名称',	
			maxLength:20,
			maxLengthText:'不能超过20个字符!',
			name : 'scheme',
			allowBlank : false
			},{
			   xtype:'hiddenfield',
			   name:'schemeName',
			   hidden:true,
			},{
			fieldLabel:'外场',							 
			xtype:'dynamicorgcombselector',
			name:'origOrgCode',
			allowBlank : false,
			type : 'ORG',
			transferCenter:'Y', 
			airDispatch  : 'Y',
			listeners:{
					change:function(field,newValue,oldValue){
						this.up('panel').getForm().findField('origOrgName').setValue(field.rawValue);
					}
				}
		},FossDataDictionary.getDataDictionaryCombo('SORTING_SCHEMENAME_TYPE',{
				fieldLabel:'方案类型:',							 
				forceSelection:true,
				editable:false,
				allowBlank : false,
				name: 'schemeCode',
				labelSeparator:'',
				listeners:{
					change:function(field,newValue,oldValue){
						this.up('panel').getForm().findField('schemeName').setValue(field.rawValue);
					}
				}
			})];
		me.callParent([cfg]);
	}
	
});


//子window=子表格+子form
Ext.define('Foss.baseinfo.sortdestStation.SortdestStationEntityWin',{
	extend : 'Ext.window.Window',
	title : '窗体',								 
	closable : true,
	modal : true,
	resizable:false,
	closeAction : 'hide',
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	listeners:{
		beforehide:function(me){
			me.getQueryResultChildGrid().getStore().removeAll(); 
		}
	},
	queryResultChildGrid:null,
	getQueryResultChildGrid:function(){
		if(this.queryResultChildGrid==null){
			
			this.queryResultChildGrid=Ext.create('Foss.baseinfo.sortdestStation.QueryResultChildGrid');
		}
		return this.queryResultChildGrid;
	},
	sortdestStationEntityWinForm:null,
	getSortdestStationEntityWinForm:function(){
	 if(this.sortdestStationEntityWinForm==null){
			
			this.sortdestStationEntityWinForm=Ext.create('Foss.baseinfo.sortdestStation.SortdestStationEntityWinForm');
		}
		return this.sortdestStationEntityWinForm;
	},
	editForm:null,											
	editGrid:null,											 
	formRecord:null,										 
	gridDate:null,											 
    constructor : function(config) {
		var me = this,cfg = Ext.apply({}, config);
		//me.editForm = Ext.create('Foss.baseinfo.sortdestStation.SortdestStationEntityWinForm',{'viewState':config.viewState,'formRecord':config.formRecord});
		me.items = [me.getSortdestStationEntityWinForm(),me.getQueryResultChildGrid()];
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
			text :'取消',
			handler :function(){
				me.hide();
			}
		},{
			text :'保存',
			handler :function(){
		    	var editForm = me.getSortdestStationEntityWinForm().getForm();
				var values=editForm.getValues();
				var scheme=editForm.findField('scheme').getValue();
				var oldScheme=me.formRecord.get('scheme');
				if(null==scheme){
					baseinfo.showInfoMsg("方案名称不能为空！");
					return;
				}
				if(oldScheme==scheme){
					me.hide();
					return;
				}
				if(editForm.isValid()){
					var objectVo={};
					objectVo.sortdestStationEntity=values;
					baseinfo.requestAjaxJson(baseinfo.realPath('updateSortdestStation.action'),
							{'objectVo':objectVo},function(result){
								var grid=Ext.getCmp('T_baseinfo-sortdestStation_content').getQueryGrid();
								grid.getPagingToolbar().moveFirst();
								me.hide();
					},function(result){
						baseinfo.showInfoMsg(result.message);
					});	
			    }
				
				
			}
		}];
	}
});


///----------------------------------------------------------------------------------------------------------
///------------------------------------------------2222222222222222222222222----------------------------------------------------------



//子form
Ext.define('Foss.baseinfo.sortdestStation.SortdestStationEntityWinForm', {
	extend : 'Ext.form.Panel',
	defaultType : 'textfield',
//	autoScroll:true,
    title:'基础信息',
	layout:{
        type: 'table',
        columns: 3
    },
	frame:true,
    formRecord:null,												
    formStore:null,													
    viewState:null,
    constructor : function(config) {							 
		var me = this,cfg = Ext.apply({}, config);
		me.items = [{
			   xtype:'hiddenfield',
			   name:'status',
			   hidden:true,
			},{
			   xtype:'hiddenfield',
			   name:'schemeName',
			   hidden:true,
			},{
			   xtype:'hiddenfield',
			   name:'origOrgName',
			   hidden:true,
			},{
			   xtype:'hiddenfield',
			   name:'id',
			   hidden:true,
			},{
			   xtype:'hiddenfield',
			   name:'virtualCode',
			   hidden:true,
			},{
			fieldLabel:'外场',							 
			xtype:'dynamicorgcombselector',
			type : 'ORG',
			readOnly:true,
			transferCenter:'Y', 
			airDispatch  : 'Y',
			name:'origOrgCode'
		},FossDataDictionary.getDataDictionaryCombo('SORTING_SCHEMENAME_TYPE',{
				fieldLabel:'方案类型:',							 
				forceSelection:true,
				editable:false,
				name: 'schemeCode',
				readOnly:true,
				labelSeparator:'',
			}),{
			       fieldLabel:'方案名称',	 
				   name:'scheme',
				   maxLength:20,
				   maxLengthText:'不能超过20个字符!',
				   allowBlank : false
				}];
		me.callParent([cfg]);
	}
	
});


//子表格
Ext.define('Foss.baseinfo.sortdestStation.QueryResultChildGrid', {
	extend : 'Ext.grid.Panel',
	autoScroll : false,
	layout : 'fit',
	colspan : 2,
	frame : true,
	parentVirtualCode:null,
	width:800,
	border : false,
	selType : 'cellmodel',
	emptyText: '查询结果为空',
	viewConfig : {
		forceFit : true
	},
	//得到接送货小区编辑窗体
	getAgencyDeptChildGridWinWin:function(grid,win,title,viewState,param){
		var formRecord = Ext.isEmpty(param)?Ext.create('Foss.baseinfo.sortdestStation.SortdestStationChildeModel'):param;
		if (Ext.isEmpty(win)) {
			win = Ext.create('Foss.baseinfo.sortdestStation.SortdestStationChildGridWin',{
				'title':title,
				'viewState':viewState,
				'sourceGrid':this,
				'formRecord':formRecord,
				 
			});
		}
		return baseinfo.sortdestStation.initParentChildGridWinWin(grid,win,viewState,formRecord,null);
	},
	sortdestStationChildGridAddWin:null,						 
	addSortdestStationChildGridWinWin:function(param){
		return this.getAgencyDeptChildGridWinWin(this,this.sortdestStationChildGridAddWin,'新增','childAdd',param);
	},
	sortdestStationChildGridUpdateWin:null,						  
	updateSortdestStationChildGridWinWin:function(param){
		return this.getAgencyDeptChildGridWinWin(this,this.sortdestStationChildGridUpdateWin,'修改','childUpdate',param);
	},
	sortdestStationChildGridViewWin:null,						 
	viewSortdestStationChildGridWinWin:function(param){
		return this.getAgencyDeptChildGridWinWin(this,this.sortdestStationChildGridViewWin,'查看','childView',param);
	},
	sortableColumns:false,
    enableColumnHide:false,
    enableColumnMove:false,
	title : '显示结果',
	height:370,
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.baseinfo.sortdestStation.SortdestStationChildeStore',{
			autoLoad : false,
			pageSize : 20,
			listeners: {
				beforeload: function(store, operation, eOpts){
					var queryForm = me.up('window').getSortdestStationEntityWinForm().getForm();//得到查询的FORM表单
					//parentVirtualCode
					//queryForm.updateRecord(queryForm.record);
					var entity = queryForm.getValues();
					if(queryForm!=null){
						Ext.apply(operation,{
							params : {
								'objectVo.sortdestStationDto.parentVirtualCode': entity.virtualCode
							}
						});	
					}
				}
		    }
		});
		/*me.plugins = [ Ext.create('Ext.grid.plugin.CellEditing', {
						clicksToEdit : 1
					}) ];*/
		me.columns = [{
							align : 'center',
							xtype : 'actioncolumn',
							text : '操作',//操作
							items: [{dataIndex: 'flag' ,
								xtype: 'checkcolumn'},{
								iconCls:'deppon_icons_edit',
				                tooltip: baseinfo.sortdestStation.i18n('foss.baseinfo.update'),
				                disabled:!baseinfo.sortdestStation.isPermission('querysortdestStationByEntity/sortdestStationEditButton'),
								handler: function(grid, rowIndex, colIndex) {
									var param=grid.getStore().getAt(rowIndex);
									me.updateSortdestStationChildGridWinWin(param).show();
								}
							},{
								iconCls:'deppon_icons_cancel',
								disabled:!baseinfo.sortdestStation.isPermission('sortdestStation/sortdestStationDelButton'),
				                tooltip: baseinfo.sortdestStation.i18n('foss.baseinfo.void'),
								handler: function(grid, rowIndex, colIndex) {
									var operatRecord=grid.getStore().getAt(rowIndex);
									baseinfo.sortdestStation.deleteChildSortdestStationByCode(grid,null,[],operatRecord);
									
								}
							}]
						},{
							text :'柜号',									 
							dataIndex : 'boxNo',
							width:150,
							editor: 'textfield',
							flex:1
						},{
							text :'格口号',									 
							dataIndex : 'cellNo',
							width:150,
							editor: 'textfield',
							flex:1
						},{
							text :'目的站',									 
							dataIndex : 'destOrgName',
							width:150,
							editor: {
							  xtype:'dynamicorgcombselector',
							  type : 'ORG',
							  transferCenter:'Y', 
							  airDispatch  : 'Y',
							  salesDepartment:'Y',
							  listeners:{
										select:function(the,record,index,eOpts){
											  console.log(record);
											   map.clear();
											   map.add(record[0].data.code,record[0].data.name);
										}
									}
							},
							renderer :function(value ,metaData ,record ){
							  console.log(value);
							  var v=map.get(value);
							  if(v==null||v==''){
							     if(record.data.destOrgName!=null||record.data.destOrgName!=''){
								   return record.data.destOrgName; 
								 }
							    return value; 
							  }else{
							   
							    return v; 
							  }
							},
							flex:1
						},{
							text :'类型',									 
							dataIndex : 'packAgeType',
							width:150,
							flex:1,
							editor : FossDataDictionary.getDataDictionaryCombo('SORTING_PACKAGE_TYPE',{		 
									forceSelection:true,
									name: 'schemeCode',
									labelSeparator:'',
									labelWidth:110,
									listeners:{
										change:function(field,newValue,oldValue){
											if(Ext.isEmpty(newValue)){
												field.setValue(null);
											}
										}
									}
							}),
							renderer :function(value ,metaData ,record ){
							  
							  return FossDataDictionary.rendererSubmitToDisplay (value,'SORTING_PACKAGE_TYPE'); 
							}
						} ];
		me.tbar = [{
			 
						text : '新增',
						disabled:!baseinfo.sortdestStation.isPermission('sortdestStation/sortdestStationAddButton'),
						hidden:!baseinfo.sortdestStation.isPermission('sortdestStation/sortdestStationAddButton'),
						handler : function(btn) {
							me.addSortdestStationChildGridWinWin({}).show();
							
						}
				} ,'-',{
			 
					text : '作废',
					disabled:!baseinfo.sortdestStation.isPermission('sortdestStation/sortdestStationDelButton'),
					hidden:!baseinfo.sortdestStation.isPermission('sortdestStation/sortdestStationDelButton'),
					handler : function(btn) {
						var smodel = btn.up('panel').getSelectionModel();
						var selection = smodel.getSelection();
//						btn.up('panel').getStore().remove(sel);
						baseinfo.sortdestStation.deleteChildSortdestStationByCode(btn.up('panel'),"many",selection,null);
					}
				}
				
				/*,'-', {
			 
				text : '新增',
				handler : function(btn) {
				var p = Ext.create('Foss.baseinfo.sortdestStation.SortdestStationChildeModel', {
					    id:'',  
                        active:'',  
                        parentVirtualCode:'',  
						boxNo:'',  	
						cellNo:'',  					
						destOrgCode  :'',                      
						destOrgName	:'',  					
						packAgeType	:''
				});
				var store = btn.up('panel').getStore(),
				count = store.count();
				
				var obj=store.getAt(store.getCount()-1);
						if(null!=obj){
					
							 if(obj.data.boxNo==null||obj.data.boxNo==''){
								Ext.Msg.alert("错误","000。");  
								return;
							 }
							  if(obj.data.cellNo==null||obj.data.cellNo==''){
								Ext.Msg.alert("错误","111。");  
								return;
							 }
							  if(obj.data.destOrgCode==null||obj.data.destOrgCode==''){
								Ext.Msg.alert("错误","22222。");  
								return;
							 }
							 if(obj.data.packAgeType==null||obj.data.packAgeType==''){
								Ext.Msg.alert("错误","2444。");  
								return;
							 }
						}
				
				store.insert(count, p);			
			}
		}*/
				
				];
		
		me.dockedItems = [ {
		  xtype: 'standardpaging',
			  store: me.store,
			  dock: 'bottom',
			  plugins: {
					ptype: 'pagesizeplugin',
					//超出输入最大限制是否提示，true则提示，默认不提示
					alertOperation: true,
					//自定义分页comobo数据
					sizeArray: [['2', 2], ['5', 5], ['10', 10], ['20', 20]],
					//入最大限制，默认为200
					maximumSize: 100
					       }            
			} ];
		me.selModel = Ext.create('Ext.selection.CheckboxModel',{mode:'MULTI',checkOnly:true});	
		me.callParent([ cfg ]);
	}
});
 

//----------------------------------------------------------------------------------------------------
//----------------------------------------------------------------------------------------------------
//-----------------------33333333333333333-----------------------------------------------------------------------------

//子表格-->window
Ext.define('Foss.baseinfo.sortdestStation.SortdestStationChildGridWin',{
	extend : 'Ext.window.Window',
	title : '窗体',								 
	closable : true,
	modal : true,
	resizable:false,
	closeAction : 'hide',
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	listeners:{
		beforehide:function(me){
			
		}
	},
	sortdestStationChildGridWinForm:null,
	getSortdestStationChildGridWinForm:function(){
	 if(this.sortdestStationChildGridWinForm==null){
			
			this.sortdestStationChildGridWinForm=Ext.create('Foss.baseinfo.sortdestStation.SortdestStationChildGridWinForm');
		}
		return this.sortdestStationChildGridWinForm;
	},
	editForm:null,											
	editGrid:null,											 
	formRecord:null,										 
	gridDate:null,											 
    constructor : function(config) {
		var me = this,cfg = Ext.apply({}, config);
		//me.editForm = Ext.create('Foss.baseinfo.sortdestStation.SortdestStationEntityWinForm',{'viewState':config.viewState,'formRecord':config.formRecord});
		me.items = [me.getSortdestStationChildGridWinForm()];
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
			text :'取消',
			handler :function(){
				me.hide();
			}
		},{
			text :'重置',
			handler :function(){
				// 重置
//			   me.getSortdestStationChildGridWinForm().getForm().reset();
			   if(me.viewState=='childAdd'){
				   me.getSortdestStationChildGridWinForm().getForm().reset();
			   }else{ 	
			   me.getSortdestStationChildGridWinForm().loadRecord(me.formRecord);
			   }
			} 
		},{
			text :'保存',
			handler :function(){
				var form=me.getSortdestStationChildGridWinForm().getForm();
				var oldRecord=me.formRecord.data;
				var newRecord=form.getValues();
				
				if(null!=oldRecord){
					var flag=baseinfo.sortdestStation.childGridInfoCompareInfo(oldRecord,newRecord);
					if(flag){
						baseinfo.showInfoMsg("你没有对记录进行修改,无需提交！");
						return;
					}
				}
 			    if(form.isValid()){
		    	   baseinfo.sortdestStation.childGridInfoOperateInfo(me,me.viewState,form.getValues(),me.sourceGrid);
				}
		}
		}];
	}
});
//子表格-->window-->form
Ext.define('Foss.baseinfo.sortdestStation.SortdestStationChildGridWinForm', {
	extend : 'Ext.form.Panel',
	defaultType : 'textfield',
//	autoScroll:true,
    title:'方案详细信息',
	layout:{
        type: 'table',
        columns: 2
    },
	frame:true,
    formRecord:null,												
    formStore:null,													
    viewState:null,
    constructor : function(config) {							 
		var me = this,cfg = Ext.apply({}, config);
		me.items = [{
					   xtype:'hiddenfield',
					   name:'id',
					   hidden:true,
					},{
					   xtype:'hiddenfield',
					   name:'destOrgName',
					   hidden:true,
						},{
							   xtype:'hiddenfield',
							   name:'parentVirtualCode',
							   hidden:true,
								},{
							fieldLabel :'柜号',									 
							name : 'boxNo',
							maxLength:5,
							maxLengthText:'不能超过5个字符!',
							allowBlank : false,
						},{
							fieldLabel :'格口号',	
							maxLength:5,
							maxLengthText:'不能超过5个字符!',
							name : 'cellNo',
							allowBlank : false,
						},{
							fieldLabel :'目的站',									 
							name : 'destOrgCode',
							xtype:'dynamicorgcombselector',
							type : 'ORG',
							allowBlank : false,
							width: 255,
							transferCenter:'Y', 
							airDispatch  : 'Y',
							salesDepartment:'Y',
							listeners:{
								change:function(field,newValue,oldValue){
									this.up('panel').getForm().findField('destOrgName').setValue(field.rawValue);
								}
							}
							 
						},FossDataDictionary.getDataDictionaryCombo('SORTING_PACKAGE_TYPE',{		 
									forceSelection:true,
									name: 'packAgeType',
									editable:false,
									allowBlank : false,
									fieldLabel:'包类型:',
									labelSeparator:'',
									width: 255
							})];
		me.callParent([cfg]);
	}
	
});



//子表格Model
Ext.define('Foss.baseinfo.sortdestStation.SortdestStationChildeModel', {
	extend: 'Ext.data.Model',
	fields : [ {name:'id',type:'string'},
		   
			  {name:'active',type:'string'},
			  
			  {name:'parentVirtualCode',type:'string'},
			   
			  {name:'boxNo',type:'string'},
			   
			  {name:'cellNo',type:'string'},
			   
			  {name:'destOrgCode',type:'string'},
			   
			  {name:'destOrgName',type:'string'},
			  {name:'packAgeType',type:'string'}
			  ]
	});
//子表格Store
Ext.define('Foss.baseinfo.sortdestStation.SortdestStationChildeStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.baseinfo.sortdestStation.SortdestStationChildeModel',
	pageSize:20,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url : baseinfo.realPath('queryChildSortdestStations.action'),
		reader : {
			type : 'json',
			root : 'objectVo.sortdestStationDtos',
			totalProperty : 'totalCount'
		}
	}
});