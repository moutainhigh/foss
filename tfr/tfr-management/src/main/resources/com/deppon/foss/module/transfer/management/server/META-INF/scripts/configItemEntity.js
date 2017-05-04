//公共方法，查询字节数，看是否超长。modify by liangfuxiang 2013-04-25 BUG-8004
management.configItemEntity.getBytesLength = function(obj) {   
    var totalLength = 0;     
    var charCode;  
    for (var i = 0; i < obj.length; i++) {  
        charCode = obj.charCodeAt(i);  
        if (charCode < 0x007f)  {     
            totalLength++;     
        } else if ((0x0080 <= charCode) && (charCode <= 0x07ff))  {     
            totalLength += 2;     
        } else if ((0x0800 <= charCode) && (charCode <= 0xffff))  {     
            totalLength += 3;   
        } else{  
            totalLength += 4;   
        }          
    }  
    return totalLength;   
}


//Ajax请求--json---------------------公共方法---------------------------
management.configItemEntity.requestJsonAjax = function(url,params,successFn,failFn)
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
management.configItemEntity.showErrorMes = function(message,fun) {
	Ext.Msg.show({
	    title:management.configItemEntity.i18n('Foss.management.configItemEntity.infoTitle'),
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
management.configItemEntity.showQuestionMes = function(message,fun) {
	Ext.Msg.show({
	    title:management.configItemEntity.i18n('Foss.management.configItemEntity.infoTitle'),
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

//-------------------------------------------配置项类型---------------------------------------------------------------------------------

//定义配置类型model
Ext.define('Foss.management.configItemEntity.ConfigTypeEntity',{
	extend:'Ext.data.Model',
	fields:[{
			name:'id',//id
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

//定义查询配置项类型结果实体store
Ext.define('Foss.management.configItemEntity.confTypeStore',{
	extend:'Ext.data.Store',
    model:'Foss.management.configItemEntity.ConfigTypeEntity',
	pageSize:20,
	proxy:{
		type:'ajax',
		actionMethods:'post',
		url:management.realPath('queryConfigTypeEntityList.action'),
		reader:{
			type:'json',
			root:'configItemVo.configTypeEntityList',
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
					'configItemVo.configTypeEntity.confType' : management.configItemEntity.ConfTypePanel.getConfTypeForm().getForm().findField('confType').getValue()
				}
			});
		}
	}
});

//配置类型信息表单
Ext.define('Foss.management.configItemEntity.ConfTypeParamsForm', {
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
    constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.items = [{
			name: 'confType',
			columnWidth:.5,
			maxLength:50,
		    editable:true,
		    xtype : 'textfield',
		    allowBlank: false,
		    labelWidth:85,
	        fieldLabel: management.configItemEntity.i18n('Foss.management.configItemEntity.confType'),//配置项类型编码
		},{
			name: 'confTypeName',
			columnWidth:.5,
			maxLength:60,
		    editable:true,
		    xtype : 'textfield',
		    allowBlank: false,
		    labelWidth:85,
	        fieldLabel: management.configItemEntity.i18n('Foss.management.configItemEntity.confTypeName')//配置项类型名称
		},{
			xtype: "hiddenfield",
			name:'id'//id
		}];
		me.callParent([cfg]);
	}
});

//定义新增按钮对应的配置参数窗口
Ext.define('Foss.management.configItemEntity.ConfTypeAddWindow',{
	extend:'Ext.window.Window',
	title:management.configItemEntity.i18n('Foss.management.configItemEntity.addFormTitle'),
	parent:null,
	closable:true,
	modal:true,
	closeAction:'hide',
	width:450,
	height:460,
	//新增配置型具体信息
	confTypeParamsForm:null,
	getConfTypeParamsForm:function(){
		if(this.confTypeParamsForm==null){
			this.confTypeParamsForm=Ext.create('Foss.management.configItemEntity.ConfTypeParamsForm');
			this.confTypeParamsForm.parent=this;
		}
		return this.confTypeParamsForm;
	},
	
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.fbar = [{
				text : management.configItemEntity.i18n('Foss.management.configItemEntity.cancel'),//取消
				handler :function(){
					me.getConfTypeParamsForm().getForm().reset();
					me.close();
				}
			},{
				text : management.configItemEntity.i18n('Foss.management.configItemEntity.reset'),//重置
				handler:function(){
					me.getConfTypeParamsForm().getForm().reset();
				}
			},{
				text : management.configItemEntity.i18n('Foss.management.configItemEntity.save'),//保存
				cls:'yellow_button',
				margin:'0 0 0 180',
				handler:function(){
			    	var confType=me.getConfTypeParamsForm().getForm().findField('confType').getValue();
			    	//检验配置参数类型
			    	if(confType==null||confType==''){
			    		management.configItemEntity.showErrorMes(management.configItemEntity.i18n('Foss.management.configItemEntity.confTypeNull'));
						return;
			    	}
			    	
			    	//modify by liangfuxiang 2013-04-25 BUG-8004 参数超长 begin
			    	if(management.configItemEntity.getBytesLength(confType)>50){
			    		management.configItemEntity.showErrorMes(management.configItemEntity.i18n('Foss.management.configItemEntity.confTypeTooLong'));
						return;
			    	}
			    	//配置参数类型名称
			    	var confTypeName=me.getConfTypeParamsForm().getForm().findField('confTypeName').getValue();
			    	if(confTypeName==null||confTypeName==''){
			    		management.configItemEntity.showErrorMes(management.configItemEntity.i18n('Foss.management.configItemEntity.confTypeNameNull'));
						return;
			    	}
			    	
			    	//超长判断
			    	if(management.configItemEntity.getBytesLength(confTypeName)>200){
			    		management.configItemEntity.showErrorMes(management.configItemEntity.i18n('Foss.management.configItemEntity.confTypeNameTooLong'));
						return;
			    	}
			    	
			    	//modify by liangfuxiang 2013-04-25 BUG-8004 参数超长 end
			    	
			    	var model = Ext.create('Foss.management.configItemEntity.ConfigTypeEntity');
			    	model.set('confType',confType);
			    	model.set('confTypeName',confTypeName);
			    	var params = {configItemVo:{'configTypeEntity':model.data}};//保存参数
			    	//保存
			    	Ext.Ajax.request({
			    		jsonData:params,
			    		url:management.realPath('addConfigTypeEntity.action'),
			    		success:function(response){//成功
			    			me.getConfTypeParamsForm().getForm().reset();
		    				me.close();
		    				management.configItemEntity.ConfTypePanel.getConfTypeGridPanel().getPagingToolbar().moveFirst();
		    				management.configItemEntity.synchroConfType();
			    		},
			    		exception:function(response){//失败
			    			var json = Ext.decode(response.responseText);
			    			if(Ext.isEmpty(json)){
			    				Ext.ux.Toast.msg(management.configItemEntity.i18n('Foss.management.configItemEntity.requestTimeout'), json.message, 'error');//请求超时
			    			}
			    			else{
			    				Ext.ux.Toast.msg(management.configItemEntity.i18n('Foss.management.configItemEntity.addFail'), json.message, 'error');
			    			}
			    		}
			    	});
				}
			}];
		me.items = [me.getConfTypeParamsForm()];
		me.callParent([cfg]);
	},
	//modify by liangfuxiang 2013-04-25 BUG-8011 begin
	listeners:{
		beforeshow:function(me){//设置数据
			me.getConfTypeParamsForm().getForm().reset();
	    }
	}
	//modify by liangfuxiang 2013-04-25 BUG-8011 end
});

//配置类型修改窗口
Ext.define('Foss.management.configItemEntity.ConfTypeUpdateWindow',{
	extend:'Ext.window.Window',
	title:management.configItemEntity.i18n('Foss.management.configItemEntity.updateFormTitle'),
	parent:null,
	closable : true,
	modal : true,
	resizable:false,
	closeAction : 'hide',
	configTypeEntity:null,
	width :450,
	height :460,
	listeners:{
		beforehide:function(me){
			me.getConfTypeParamsForm().getForm().reset();//表格重置
		}
	},
	//修改配置类型FORM
	confTypeParamsForm:null,
    getConfTypeParamsForm : function(){
    	if(Ext.isEmpty(this.confTypeParamsForm)){
    		this.confTypeParamsForm=Ext.create('Foss.management.configItemEntity.ConfTypeParamsForm');
    	}
    	return this.confTypeParamsForm;
    },
    
    //保存
    commitConfTypeParamsForm:function(){
    	var me = this;
    	var form = me.getConfTypeParamsForm().getForm();
    	//配置参数ID
    	var id=form.findField('id').getValue();
    	if(id==null || id==''){
    		management.configItemEntity.showErrorMes(management.configItemEntity.i18n('Foss.management.configItemEntity.idNull'));
			return;
    	}
    	var confType=form.findField('confType').getValue();
    	//检验配置参数类型
    	if(confType==null||confType==''){
    		management.configItemEntity.showErrorMes(management.configItemEntity.i18n('Foss.management.configItemEntity.confTypeNull'));
			return;
    	}
    	
    	if(management.configItemEntity.getBytesLength(confType)>50){
    		management.configItemEntity.showErrorMes(management.configItemEntity.i18n('Foss.management.configItemEntity.confTypeTooLong'));
			return;
    	}
    	
    	//配置参数类型名称
    	var confTypeName=form.findField('confTypeName').getValue();
    	if(confTypeName==null||confTypeName==''){
    		management.configItemEntity.showErrorMes(management.configItemEntity.i18n('Foss.management.configItemEntity.confTypeNameNull'));
			return;
    	}

    	//超长判断
    	if(management.configItemEntity.getBytesLength(confTypeName)>200){
    		management.configItemEntity.showErrorMes(management.configItemEntity.i18n('Foss.management.configItemEntity.confTypeNameTooLong'));
			return;
    	}
    	
    	var model = Ext.create('Foss.management.configItemEntity.ConfigTypeEntity');
    	model.set('confType',confType);
    	model.set('confTypeName',confTypeName);
    	model.set('id',id);
    	var params = {configItemVo:{'configTypeEntity':model.data}};//保存参数

    	Ext.Ajax.request({
    		jsonData:params,
    		url:management.realPath('modifyConfigTypeEntity.action'),
    		success:function(response){//成功
				me.close();
				management.configItemEntity.ConfTypePanel.getConfTypeGridPanel().getPagingToolbar().moveFirst();
				management.configItemEntity.synchroConfType();
    		},
    		exception:function(response){//失败
    			var json = Ext.decode(response.responseText);
    			if(Ext.isEmpty(json)){
    				Ext.ux.Toast.msg(management.configItemEntity.i18n('Foss.management.configItemEntity.requestTimeout'), json.message, 'error');//请求超时
    			}
    			else{
    				Ext.ux.Toast.msg(management.configItemEntity.i18n('Foss.management.configItemEntity.modifyFail'), json.message, 'error');
    			}
    		}
    	});
    },
    //重置，重新加载数据
    loadData:function(me){
    	var me = this;
    	var data=publicConfigOrgRelationEntity;
    	var form = me.getConfTypeParamsForm().getForm();
    	form.loadRecord(data);//加载配置参数数据
	   	form.findField('confType').setValue(data.confType);
	   	form.findField('confTypeName').setValue(data.confTypeName);
    	form.findField('id').setValue(data.id);
    },
    
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.fbar = [{
				text : management.configItemEntity.i18n('Foss.management.configItemEntity.cancel'),//取消
				handler :function(){
					me.close();
				}
			},{
				text : management.configItemEntity.i18n('Foss.management.configItemEntity.reset'),//重置
				handler:function(){
					me.loadData(me);
				}
			},{
				text : management.configItemEntity.i18n('Foss.management.configItemEntity.save'),//保存
				cls:'yellow_button',
				margin:'0 0 0 180',
				handler:function(){
					me.commitConfTypeParamsForm();
				}
			}];
		me.items = [me.getConfTypeParamsForm()];
		me.callParent([cfg]);
	}
});

//配置项类型grid
Ext.define('Foss.management.configItemEntity.ConfTypeGridPanel',{
	extend:'Ext.grid.Panel',
	title:management.configItemEntity.i18n('Foss.management.configItemEntity.searchResult'),//查询结果
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
				//设置的最大分页数，防止用户输入太大数量，影响服务器性能
                limitWarning: 200    })
			});
		}
		return this.pagingToolbar;
	},
	
	confTypeAddWindow: null,//增加窗口
	getConfTypeAddWindow: function(){
		if(this.confTypeAddWindow==null){
			this.confTypeAddWindow = Ext.create('Foss.management.configItemEntity.ConfTypeAddWindow');
			this.confTypeAddWindow.parent = this;
		}
		return this.confTypeAddWindow;
	},

	confTypeUpdateWindow:null,//修改窗口
	getConfTypeUpdateWindow:function(){
		if(this.confTypeUpdateWindow==null){
			this.confTypeUpdateWindow=Ext.create('Foss.management.configItemEntity.ConfTypeUpdateWindow');
			this.confTypeUpdateWindow.parent=this;
		}
		return this.confTypeUpdateWindow;
	},
	//作废组织配置项信息
	abolishItem: function(btn){
		var me = this;
		//选中数据
		var selections = me.getSelectionModel().getSelection();
		if(selections.length<1){//未选择
			management.configItemEntity.showErrorMes(management.configItemEntity.i18n('Foss.management.configItemEntity.selectNoAbolishItemType')); //未选择要作废的配置项!
			return;
		}
		management.configItemEntity.showQuestionMes(management.configItemEntity.i18n('Foss.management.configItemEntity.yesOrNotBolishItemType'),function(e){//您确认要作废这些配置项信息?
			if(e=='yes'){//确认作废
				var idList = new Array();
				for(var i = 0 ; i<selections.length ; i++){
					idList.push(selections[i].get('id')); //将要作废的配置参数保存到列表中
				}
				var params = {'configItemVo':{'idList':idList}};
				var successFun = function(json){
					me.getPagingToolbar().moveFirst();
					management.configItemEntity.synchroConfType();
				};
				var failureFun = function(json){
					if(Ext.isEmpty(json)){
						management.configItemEntity.showErrorMes(management.configItemEntity.i18n('Foss.management.configItemEntity.requestTimeout'));//请求超时
					}else{
						management.configItemEntity.showErrorMes(json.message);
					}
				};
				var url = management.realPath('abolishConfigTypeEntity.action');
				management.configItemEntity.requestJsonAjax(url,params,successFun,failureFun);
			}
		})
	},
	columns : [{xtype: 'rownumberer',
		width:40,
		text : management.configItemEntity.i18n('Foss.management.configItemEntity.num')//序号
	},{
		text : management.configItemEntity.i18n('Foss.management.configItemEntity.op'),//操作
		align : 'center',
		xtype : 'actioncolumn',
		items: [{
					iconCls: 'deppon_icons_edit',
	                tooltip: management.configItemEntity.i18n('Foss.management.configItemEntity.update'),//修改
					width:42,
					handler: function(grid, rowIndex, colIndex) {
		                	//获取选中的数据
		    				var record=grid.getStore().getAt(rowIndex);
		                	var id = record.get('id');//词条代码
		    				var params = {'configItemVo':{'configTypeEntity':{'id':id}}};
		    				var successFun = function(json){
		    					var confTypeUpdateWindow = management.configItemEntity.ConfTypePanel.getConfTypeGridPanel().getConfTypeUpdateWindow();//获得修改窗口
		    					confTypeUpdateWindow.getConfTypeParamsForm().getForm().findField('confType').setValue(json.configItemVo.configTypeEntity.confType);
		    					confTypeUpdateWindow.getConfTypeParamsForm().getForm().findField('confTypeName').setValue(json.configItemVo.configTypeEntity.confTypeName);
		    					confTypeUpdateWindow.getConfTypeParamsForm().getForm().findField('id').setValue(json.configItemVo.configTypeEntity.id);
		    					publicConfigOrgRelationEntity=json.configItemVo.configTypeEntity;
		    					confTypeUpdateWindow.show();//显示修改窗口
		    				};
		    				var failureFun = function(json){
		    					if(Ext.isEmpty(json)){
		    						management.configItemEntity.showErrorMes(management.configItemEntity.i18n('Foss.management.configItemEntity.requestTimeout'));//请求超时
		    					}else{
		    						management.configItemEntity.showErrorMes(json.message);
		    					}
		    				};
		    				var url = management.realPath('queryConfigTypeEntity.action');
		    				management.configItemEntity.requestJsonAjax(url,params,successFun,failureFun);
		                }
				},{
					iconCls: 'deppon_icons_cancel',
	                tooltip: management.configItemEntity.i18n('Foss.management.configItemEntity.void'),//作废
					width:42,
					handler:function(grid, rowIndex, colIndex) {
						management.configItemEntity.showQuestionMes(management.configItemEntity.i18n('Foss.management.configItemEntity.yesOrNotBolishItemType'),function(e){
							if(e=='yes'){
	            				var idList = new Array();
	            				//获取选中的数据
	            				var record=grid.getStore().getAt(rowIndex);
	            				idList.push(record.get('id')); //
	            				var params = {'configItemVo':{'idList':idList}};
	            				var successFun = function(json){
	            					management.configItemEntity.ConfTypePanel.getConfTypeGridPanel().getPagingToolbar().moveFirst();
	            					management.configItemEntity.synchroConfType();
	            				};
	            				var failureFun = function(json){
	            					if(Ext.isEmpty(json)){
	            						management.configItemEntity.showErrorMes(management.configItemEntity.i18n('Foss.management.configItemEntity.requestTimeout'));//请求超时
	            					}else{
	            						management.configItemEntity.showErrorMes(json.message);
	            					}
	            				};
	            				var url = management.realPath('abolishConfigTypeEntity.action');
	            				management.configItemEntity.requestJsonAjax(url,params,successFun,failureFun);
							}
						})
					}
				}]
	},{
		text : management.configItemEntity.i18n('Foss.management.configItemEntity.confType'),//配置类型编码
		width:120,
		dataIndex : 'confType',
	},{
		text : management.configItemEntity.i18n('Foss.management.configItemEntity.confTypeName'),//配置类型名称
		width:120,
		dataIndex : 'confTypeName'
	}],
	
	constructor : function(config) {
		var me = this, 
		cfg = Ext.apply({}, config);
		me.store=Ext.create('Foss.management.configItemEntity.confTypeStore');
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
				text : management.configItemEntity.i18n('Foss.management.configItemEntity.void'),//作废
				width:80,
				disabled: !management.configItemEntity.isPermission('management/abolishConfigTypeEntityButton'),
				hidden: !management.configItemEntity.isPermission('management/abolishConfigTypeEntityButton'),
				handler:function(){
					me.abolishItem();//作废方法
				}
			},{
				text : management.configItemEntity.i18n('Foss.management.configItemEntity.add'),//新增
				disabled: !management.configItemEntity.isPermission('management/addConfigTypeEntityButton'),
				hidden: !management.configItemEntity.isPermission('management/addConfigTypeEntityButton'),
				width:80,
				handler:function(){
					me.getConfTypeAddWindow().show(); //显示新增窗口
				}
			} 
		];
		//设置最下分页工具栏
		me.bbar = me.getPagingToolbar();
		me.callParent([cfg]);	
	}
});
//配置项类型定义查询条件
Ext.define('Foss.management.configItemEntity.ConfTypeForm', {
	extend : 'Ext.form.Panel',
	title: management.configItemEntity.i18n('Foss.management.configItemEntity.searchCondiction'),//查询条件
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
		    store:management.configItemEntity.initConfTypeStore,
		    xtype:'combo',
	        fieldLabel: management.configItemEntity.i18n('Foss.management.configItemEntity.confTypeName')//配置项类型
		}];
		me.fbar = [{
			xtype : 'button', 
			width:70,
			text : management.configItemEntity.i18n('Foss.management.configItemEntity.reset'),//重置
			handler : function() {
				me.getForm().reset();
			}
		},{
			xtype : 'button', 
			width:70,
			cls:'yellow_button',
			margin:'0 0 0 750',
			text : management.configItemEntity.i18n('Foss.management.configItemEntity.search'),//查询
			disabled: !management.configItemEntity.isPermission('management/queryConfigTypeEntityListButton'),
			hidden: !management.configItemEntity.isPermission('management/queryConfigTypeEntityListButton'),
			handler : function() {
				if(me.getForm().isValid()){
					management.configItemEntity.ConfTypePanel.getConfTypeGridPanel().getPagingToolbar().moveFirst();
				}
			}
		}];
		me.callParent([cfg]);
	}
});

//配置项类型
Ext.define('Foss.management.configItemEntity.ConfTypePanel', {
	extend : 'Ext.container.Container',
	frame : true,
	autoScroll : true,
	confTypeForm : null,
	getConfTypeForm : function() {
		if (this.confTypeForm == null) {
			this.confTypeForm = Ext.create('Foss.management.configItemEntity.ConfTypeForm');
		}
		return this.confTypeForm;
	},
	confTypeGridPanel : null,
	getConfTypeGridPanel : function() {
		if (this.confTypeGridPanel == null) {
			this.confTypeGridPanel = Ext.create('Foss.management.configItemEntity.ConfTypeGridPanel');
		}
		return this.confTypeGridPanel;
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.items = [me.getConfTypeForm(), me.getConfTypeGridPanel()];
		me.callParent([cfg]);
	}
});

//-------------------------------------------------------配置项--------------------------------------------------
//定义配置类型model
Ext.define('Foss.management.configItemEntity.ConfigItemEntity',{
	extend:'Ext.data.Model',
	fields:[{
			name:'id',//id
			type:'string'
		},{
	    	name:'confCode', //配置项编码
	    	type:'string'
	    },{
	    	name:'confName', //配置项名称
	    	type:'string'
	    },{
	    	name:'confType', //配置项类型编码
	    	type:'string'
	    },{
	    	name:'confTypeName',//配置项类型名
	    	type:'string'
	    }
	]
});

//定义查询配置项结果实体store
Ext.define('Foss.management.configItemEntity.confItemStore',{
	extend:'Ext.data.Store',
    model:'Foss.management.configItemEntity.ConfigItemEntity',
	pageSize:20,
	proxy:{
		type:'ajax',
		actionMethods:'post',
		url:management.realPath('queryConfigItemEntityList.action'),
		reader:{
			type:'json',
			root:'configItemVo.configItemEntityList',
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
					'configItemVo.configItemEntity.confType' : management.configItemEntity.ConfItemPanel.getConfItemForm().getForm().findField('confType').getValue(),
				}
			});
		}
	}
});

//配置项信息表单
Ext.define('Foss.management.configItemEntity.ConfItemParamsForm', {
	extend : 'Ext.form.Panel',
	frame: true,
	height:400,
	collapsible: true,
	isSearchComb:true,
    defaults : {
    	colspan : 2,
    	margin : '5 5 5 5',
    	labelWidth:80
    },
	defaultType : 'textfield',
	layout: {
        type: 'table',
        columns: 2
    },
    constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.items = [{
			name: 'confType',
			allowBlank:false,
			queryMode: 'local',
		    displayField: 'confTypeName',
		    valueField: 'confType',
		    editable:true,
		    labelWidth:85,
		    store:management.configItemEntity.initConfTypeStore,
	        xtype : 'combo',
	        fieldLabel: management.configItemEntity.i18n('Foss.management.configItemEntity.confTypeName'),//配置项类型
		},{
			name: 'confCode',
			columnWidth:.5,
			maxLength:20,
		    editable:true,
		    xtype : 'textfield',
		    allowBlank: false,
		    labelWidth:85,
	        fieldLabel: management.configItemEntity.i18n('Foss.management.configItemEntity.confCode'),//配置项编码
		},{
			name: 'confName',
			columnWidth:.5,
			maxLength:60,
		    editable:true,
		    xtype : 'textfield',
		    allowBlank: false,
		    labelWidth:85,
	        fieldLabel: management.configItemEntity.i18n('Foss.management.configItemEntity.confName')//配置项类型名称
		},{
			xtype: "hiddenfield",
			name:'id'//id
		}];
		me.callParent([cfg]);
	}
});

//定义新增按钮对应的配置窗口
Ext.define('Foss.management.configItemEntity.ConfItemAddWindow',{
	extend:'Ext.window.Window',
	title:management.configItemEntity.i18n('Foss.management.configItemEntity.addFormTitle'),
	parent:null,
	closable:true,
	modal:true,
	closeAction:'hide',
	width:450,
	height:460,
	//新增配置项具体信息
	confItemParamsForm:null,
	getConfItemParamsForm:function(){
		if(this.confItemParamsForm==null){
			this.confItemParamsForm=Ext.create('Foss.management.configItemEntity.ConfItemParamsForm');
			this.confItemParamsForm.parent=this;
		}
		return this.confItemParamsForm;
	},
	
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.fbar = [{
				text : management.configItemEntity.i18n('Foss.management.configItemEntity.cancel'),//取消
				handler :function(){
					me.getConfItemParamsForm().getForm().reset();
					me.close();
				}
			},{
				text : management.configItemEntity.i18n('Foss.management.configItemEntity.reset'),//重置
				handler:function(){
					me.getConfItemParamsForm().getForm().reset();
				}
			},{
				text : management.configItemEntity.i18n('Foss.management.configItemEntity.save'),//保存
				cls:'yellow_button',
				margin:'0 0 0 180',
				handler:function(){
			    	var confType=me.getConfItemParamsForm().getForm().findField('confType').getValue();
			    	//检验配置参数类型
			    	if(confType==null||confType==''){
			    		management.configItemEntity.showErrorMes(management.configItemEntity.i18n('Foss.management.configItemEntity.confTypeNull'));
						return;
			    	}
			    	//配置参数类型名称
			    	var confTypeName=me.getConfItemParamsForm().getForm().findField('confType').getRawValue();
			    	if(confTypeName==null||confTypeName==''){
			    		management.configItemEntity.showErrorMes(management.configItemEntity.i18n('Foss.management.configItemEntity.confTypeNameNull'));
						return;
			    	}
			    	var confCode=me.getConfItemParamsForm().getForm().findField('confCode').getValue();
			    	//检验配置项编码
			    	if(confCode==null||confCode==''){
			    		management.configItemEntity.showErrorMes(management.configItemEntity.i18n('Foss.management.configItemEntity.confCodeNull'));
						return;
			    	}
			    	
			    	//超长判断
			    	if(management.configItemEntity.getBytesLength(confCode)>50){
			    		management.configItemEntity.showErrorMes(management.configItemEntity.i18n('Foss.management.configItemEntity.confCodeTooLong'));
						return;
			    	}
			    	//配置项编码名称
			    	var confName=me.getConfItemParamsForm().getForm().findField('confName').getValue();
			    	if(confName==null||confName==''){
			    		management.configItemEntity.showErrorMes(management.configItemEntity.i18n('Foss.management.configItemEntity.confNameNull'));
						return;
			    	}
			    	//超长判断
			    	if(management.configItemEntity.getBytesLength(confName)>200){
			    		management.configItemEntity.showErrorMes(management.configItemEntity.i18n('Foss.management.configItemEntity.confNameTooLong'));
						return;
			    	}
			    	
			    	var model = Ext.create('Foss.management.configItemEntity.ConfigItemEntity');
			    	model.set('confType',confType);
			    	model.set('confTypeName',confTypeName);
		    		model.set('confCode',confCode);
		    		model.set('confName',confName);
			    	var params = {configItemVo:{'configItemEntity':model.data}};//保存参数
			    	//保存
			    	Ext.Ajax.request({
			    		jsonData:params,
			    		url:management.realPath('addConfigItemEntity.action'),
			    		success:function(response){//成功
			    			me.getConfItemParamsForm().getForm().reset();
		    				me.close();
		    				management.configItemEntity.ConfItemPanel.getConfItemGridPanel().getPagingToolbar().moveFirst();
			    		},
			    		exception:function(response){//失败
			    			var json = Ext.decode(response.responseText);
			    			if(Ext.isEmpty(json)){
			    				Ext.ux.Toast.msg(management.configItemEntity.i18n('Foss.management.configItemEntity.requestTimeout'), json.message, 'error');//请求超时
			    			}
			    			else{
			    				Ext.ux.Toast.msg(management.configItemEntity.i18n('Foss.management.configItemEntity.addFail'), json.message, 'error');
			    			}
			    		}
			    	});
				}
			}];
		me.items = [me.getConfItemParamsForm()];
		me.callParent([cfg]);
	},
	
	//modify by liangfuxiang 2013-04-25 BUG-8011 begin
	listeners:{
		beforeshow:function(me){//设置数据
			me.getConfItemParamsForm().getForm().reset();
	    }
	}
	//modify by liangfuxiang 2013-04-25 BUG-8011 end
});
//配置项修改窗口
Ext.define('Foss.management.configItemEntity.ConfItemUpdateWindow',{
	extend:'Ext.window.Window',
	title:management.configItemEntity.i18n('Foss.management.configItemEntity.updateFormTitle'),
	parent:null,
	closable : true,
	modal : true,
	resizable:false,
	closeAction : 'hide',
	configItemEntity:null,
	width :450,
	height :460,
	listeners:{
		beforehide:function(me){
			me.getConfItemParamsForm().getForm().reset();//表格重置
		}
	},
	//修改配置FORM
	confItemParamsForm:null,
    getConfItemParamsForm : function(){
    	if(Ext.isEmpty(this.confItemParamsForm)){
    		this.confItemParamsForm=Ext.create('Foss.management.configItemEntity.ConfItemParamsForm');
    	}
    	return this.confItemParamsForm;
    },
    
    //保存
    commitConfItemParamsForm:function(){
    	var me = this;
    	var form = me.getConfItemParamsForm().getForm();
    	//配置参数ID
    	var id=form.findField('id').getValue();
    	if(id==null || id==''){
    		management.configItemEntity.showErrorMes(management.configItemEntity.i18n('Foss.management.configItemEntity.idNull'));
			return;
    	}
    	var confType=form.findField('confType').getValue();
    	//检验配置参数类型
    	if(confType==null||confType==''){
    		management.configItemEntity.showErrorMes(management.configItemEntity.i18n('Foss.management.configItemEntity.confTypeNull'));
			return;
    	}
    	//配置参数类型名称
    	var confTypeName=form.findField('confType').getRawValue();
    	if(confTypeName==null||confTypeName==''){
    		management.configItemEntity.showErrorMes(management.configItemEntity.i18n('Foss.management.configItemEntity.confTypeNameNull'));
			return;
    	}
    	var confCode=form.findField('confCode').getValue();
    	//检验配置项编码
    	if(confCode==null||confCode==''){
    		management.configItemEntity.showErrorMes(management.configItemEntity.i18n('Foss.management.configItemEntity.confCodeNull'));
			return;
    	}
    	
    	//超长判断
    	if(management.configItemEntity.getBytesLength(confCode)>50){
    		management.configItemEntity.showErrorMes(management.configItemEntity.i18n('Foss.management.configItemEntity.confCodeTooLong'));
			return;
    	}
    	
    	//配置项编码名称
    	var confName=form.findField('confName').getValue();
    	if(confName==null||confName==''){
    		management.configItemEntity.showErrorMes(management.configItemEntity.i18n('Foss.management.configItemEntity.confNameNull'));
			return;
    	}
    	
    	//超长判断
    	if(management.configItemEntity.getBytesLength(confName)>200){
    		management.configItemEntity.showErrorMes(management.configItemEntity.i18n('Foss.management.configItemEntity.confNameTooLong'));
			return;
    	}
    	
    	var model = Ext.create('Foss.management.configItemEntity.ConfigItemEntity');
    	model.set('confType',confType);
    	model.set('confTypeName',confTypeName);
    	model.set('confCode',confCode);
    	model.set('confName',confName);
    	model.set('id',id);
    	var params = {configItemVo:{'configItemEntity':model.data}};//保存参数

    	Ext.Ajax.request({
    		jsonData:params,
    		url:management.realPath('modifyConfigItemEntity.action'),
    		success:function(response){//成功
				me.close();
				management.configItemEntity.ConfItemPanel.getConfItemGridPanel().getPagingToolbar().moveFirst();
    		},
    		exception:function(response){//失败
    			var json = Ext.decode(response.responseText);
    			if(Ext.isEmpty(json)){
    				Ext.ux.Toast.msg(management.configItemEntity.i18n('Foss.management.configItemEntity.requestTimeout'), json.message, 'error');//请求超时
    			}
    			else{
    				Ext.ux.Toast.msg(management.configItemEntity.i18n('Foss.management.configItemEntity.modifyFail'), json.message, 'error');
    			}
    		}
    	});
    },
    //重置，重新加载数据
    loadData:function(me){
    	var me = this;
    	var data=publicConfigOrgRelationEntity;
    	var form = me.getConfItemParamsForm().getForm();
    	form.loadRecord(data);//加载配置参数数据
	   	form.findField('confType').setValue(data.confType);
	   	form.findField('confType').setRawValue(data.confTypeName);
	   	form.findField('confCode').setValue(data.confCode);
	   	form.findField('confName').setValue(data.confName);
    	form.findField('id').setValue(data.id);
    },
    
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.fbar = [{
				text : management.configItemEntity.i18n('Foss.management.configItemEntity.cancel'),//取消
				handler :function(){
					me.close();
				}
			},{
				text : management.configItemEntity.i18n('Foss.management.configItemEntity.reset'),//重置
				handler:function(){
					me.loadData(me);
				}
			},{
				text : management.configItemEntity.i18n('Foss.management.configItemEntity.save'),//保存
				cls:'yellow_button',
				margin:'0 0 0 180',
				handler:function(){
					me.commitConfItemParamsForm();
				}
			}];
		me.items = [me.getConfItemParamsForm()];
		me.callParent([cfg]);
	}
});

//配置项grid
Ext.define('Foss.management.configItemEntity.ConfItemGridPanel',{
	extend:'Ext.grid.Panel',
	title:management.configItemEntity.i18n('Foss.management.configItemEntity.searchResult'),//查询结果
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
	
	confItemAddWindow: null,//增加窗口
	getConfItemAddWindow: function(){
		if(this.confItemAddWindow==null){
			this.confItemAddWindow = Ext.create('Foss.management.configItemEntity.ConfItemAddWindow');
			this.confItemAddWindow.parent = this;
		}
		return this.confItemAddWindow;
	},

	confItemUpdateWindow:null,//修改窗口
	getConfItemUpdateWindow:function(){
		if(this.confItemUpdateWindow==null){
			this.confItemUpdateWindow=Ext.create('Foss.management.configItemEntity.ConfItemUpdateWindow');
			this.confItemUpdateWindow.parent=this;
		}
		return this.confItemUpdateWindow;
	},
	//作废组织配置项信息
	abolishItem: function(btn){
		var me = this;
		//选中数据
		var selections = me.getSelectionModel().getSelection();
		if(selections.length<1){//未选择
			management.configItemEntity.showErrorMes(management.configItemEntity.i18n('Foss.management.configItemEntity.selectNoAbolishItemType')); //未选择要作废的配置项!
			return;
		}
		management.configItemEntity.showQuestionMes(management.configItemEntity.i18n('Foss.management.configItemEntity.yesOrNotBolishItem'),function(e){//您确认要作废这些配置项信息?
			if(e=='yes'){//确认作废
				var idList = new Array();
				for(var i = 0 ; i<selections.length ; i++){
					idList.push(selections[i].get('id')); //将要作废的配置参数保存到列表中
				}
				var params = {'configItemVo':{'idList':idList}};
				var successFun = function(json){
					me.getPagingToolbar().moveFirst();
				};
				var failureFun = function(json){
					if(Ext.isEmpty(json)){
						management.configItemEntity.showErrorMes(management.configItemEntity.i18n('Foss.management.configItemEntity.requestTimeout'));//请求超时
					}else{
						management.configItemEntity.showErrorMes(json.message);
					}
				};
				var url = management.realPath('abolishConfigItemEntity.action');
				management.configItemEntity.requestJsonAjax(url,params,successFun,failureFun);
			}
		})
	},
	columns : [{xtype: 'rownumberer',
		width:40,
		text : management.configItemEntity.i18n('Foss.management.configItemEntity.num')//序号
	},{
		text : management.configItemEntity.i18n('Foss.management.configItemEntity.op'),//操作
		align : 'center',
		xtype : 'actioncolumn',
		items: [{
					iconCls: 'deppon_icons_edit',
	                tooltip: management.configItemEntity.i18n('Foss.management.configItemEntity.update'),//修改
					width:42,
					handler: function(grid, rowIndex, colIndex) {
		                	//获取选中的数据
		    				var record=grid.getStore().getAt(rowIndex);
		                	var id = record.get('id');//词条代码
		    				var params = {'configItemVo':{'configItemEntity':{'id':id}}};
		    				var successFun = function(json){
		    					var confItemUpdateWindow = management.configItemEntity.ConfItemPanel.getConfItemGridPanel().getConfItemUpdateWindow();//获得修改窗口
		    					confItemUpdateWindow.getConfItemParamsForm().getForm().findField('confType').setValue(json.configItemVo.configItemEntity.confType);
		    					confItemUpdateWindow.getConfItemParamsForm().getForm().findField('confType').setRawValue(json.configItemVo.configItemEntity.confTypeName);
		    					confItemUpdateWindow.getConfItemParamsForm().getForm().findField('confCode').setValue(json.configItemVo.configItemEntity.confCode);
		    					confItemUpdateWindow.getConfItemParamsForm().getForm().findField('confName').setValue(json.configItemVo.configItemEntity.confName);
		    					confItemUpdateWindow.getConfItemParamsForm().getForm().findField('id').setValue(json.configItemVo.configItemEntity.id);
		    					publicConfigOrgRelationEntity=json.configItemVo.configItemEntity;
		    					confItemUpdateWindow.show();//显示修改窗口
		    				};
		    				var failureFun = function(json){
		    					if(Ext.isEmpty(json)){
		    						management.configItemEntity.showErrorMes(management.configItemEntity.i18n('Foss.management.configItemEntity.requestTimeout'));//请求超时
		    					}else{
		    						management.configItemEntity.showErrorMes(json.message);
		    					}
		    				};
		    				var url = management.realPath('queryConfigItemEntity.action');
		    				management.configItemEntity.requestJsonAjax(url,params,successFun,failureFun);
		                }
				},{
					iconCls: 'deppon_icons_cancel',
	                tooltip: management.configItemEntity.i18n('Foss.management.configItemEntity.void'),//作废
					width:42,
					handler:function(grid, rowIndex, colIndex) {
						management.configItemEntity.showQuestionMes(management.configItemEntity.i18n('Foss.management.configItemEntity.yesOrNotBolishItem'),function(e){
							if(e=='yes'){
	            				var idList = new Array();
	            				//获取选中的数据
	            				var record=grid.getStore().getAt(rowIndex);
	            				idList.push(record.get('id')); //
	            				var params = {'configItemVo':{'idList':idList}};
	            				var successFun = function(json){
	            					management.configItemEntity.ConfItemPanel.getConfItemGridPanel().getPagingToolbar().moveFirst()
	            				};
	            				var failureFun = function(json){
	            					if(Ext.isEmpty(json)){
	            						management.configItemEntity.showErrorMes(management.configItemEntity.i18n('Foss.management.configItemEntity.requestTimeout'));//请求超时
	            					}else{
	            						management.configItemEntity.showErrorMes(json.message);
	            					}
	            				};
	            				var url = management.realPath('abolishConfigItemEntity.action');
	            				management.configItemEntity.requestJsonAjax(url,params,successFun,failureFun);
							}
						})
					}
				}]
	},{
		text : management.configItemEntity.i18n('Foss.management.configItemEntity.confCode'),//配置编码
		width:120,
		dataIndex : 'confCode',
	},{
		text : management.configItemEntity.i18n('Foss.management.configItemEntity.confName'),//配置编码名称
		width:120,
		dataIndex : 'confName',
	},{
		text : management.configItemEntity.i18n('Foss.management.configItemEntity.confType'),//配置类型编码
		width:120,
		dataIndex : 'confType',
	},{
		text : management.configItemEntity.i18n('Foss.management.configItemEntity.confTypeName'),//配置类型名称
		width:120,
		dataIndex : 'confTypeName'
	}],
	
	constructor : function(config) {
		var me = this, 
		cfg = Ext.apply({}, config);
		me.store=Ext.create('Foss.management.configItemEntity.confItemStore');
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
				text : management.configItemEntity.i18n('Foss.management.configItemEntity.void'),//作废
				width:80,
				disabled: !management.configItemEntity.isPermission('management/abolishConfigItemEntityButton'),
				hidden: !management.configItemEntity.isPermission('management/abolishConfigItemEntityButton'),
				handler:function(){
					me.abolishItem();//作废方法
				}
			},{
				text : management.configItemEntity.i18n('Foss.management.configItemEntity.add'),//新增
				disabled: !management.configItemEntity.isPermission('management/addConfigItemEntityButton'),
				hidden: !management.configItemEntity.isPermission('management/addConfigItemEntityButton'),
				width:80,
				handler:function(){
					me.getConfItemAddWindow().show(); //显示新增窗口
				}
			} 
		];
		//设置最下分页工具栏
		me.bbar = me.getPagingToolbar();
		me.callParent([cfg]);	
	}
});
//配置项定义查询条件
Ext.define('Foss.management.configItemEntity.ConfItemForm', {
	extend : 'Ext.form.Panel',
	title: management.configItemEntity.i18n('Foss.management.configItemEntity.searchCondiction'),//查询条件
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
		    store:management.configItemEntity.initConfTypeStore,
		    xtype:'combo',
	        fieldLabel: management.configItemEntity.i18n('Foss.management.configItemEntity.confTypeName')//配置项类型
		}];
		me.fbar = [{
			xtype : 'button', 
			width:70,
			text : management.configItemEntity.i18n('Foss.management.configItemEntity.reset'),//重置
			handler : function() {
				me.getForm().reset();
			}
		},{
			xtype : 'button', 
			width:70,
			cls:'yellow_button',
			margin:'0 0 0 750',
			text : management.configItemEntity.i18n('Foss.management.configItemEntity.search'),//查询
			disabled: !management.configItemEntity.isPermission('management/queryConfigItemEntityListButton'),
			hidden: !management.configItemEntity.isPermission('management/queryConfigItemEntityListButton'),
			handler : function() {
				if(me.getForm().isValid()){
					management.configItemEntity.ConfItemPanel.getConfItemGridPanel().getPagingToolbar().moveFirst();
				}
			}
		}];
		me.callParent([cfg]);
	}
});

//配置项
Ext.define('Foss.management.configItemEntity.ConfItemPanel', {
	extend : 'Ext.container.Container',
	frame : true,
	autoScroll : true,
	confItemForm : null,
	getConfItemForm : function() {
		if (this.confItemForm == null) {
			this.confItemForm = Ext.create('Foss.management.configItemEntity.ConfItemForm');
		}
		return this.confItemForm;
	},
	confItemGridPanel : null,
	getConfItemGridPanel : function() {
		if (this.confItemGridPanel == null) {
			this.confItemGridPanel = Ext.create('Foss.management.configItemEntity.ConfItemGridPanel');
		}
		return this.confItemGridPanel;
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.items = [me.getConfItemForm(), me.getConfItemGridPanel()];
		me.callParent([cfg]);
	}
});

//定义：---------------------------------------------------查询条件 配置项类型store---------------------------------
Ext.define('Foss.management.configItemEntity.initConfigTypeStore',{
	extend:'Ext.data.Store',
	fields: [
	 		{name: 'confType',  type: 'confType'},
	 		{name: 'confTypeName',  type: 'confTypeName'}
	 	],
		proxy: {
			type: 'memory',
			reader: {
				type: 'json',
				root: 'configTypeEntityList'
			}
		},
		constructor: function(config){
			var me = this,
			cfg = Ext.apply({}, config);
			me.callParent([cfg]);
		}
});

//-------------------------------------------------------------------页面加载初始化--------------------------------------------
//同步刷新，主要用于刷新增删改的配置项类型
management.configItemEntity.synchroConfType = function(){
	Ext.Ajax.request({
		url:management.realPath('queryAllConfigTypes.action'),
		success:function(response){
			var result = Ext.decode(response.responseText);
			if(result.success){
				management.configItemEntity.initConfTypeStore.loadData(result.configItemVo.configTypeEntityList);
			}
		},
		exception:function(response){
			var result = Ext.decode(response.responseText);
			if(Ext.isEmpty(json)){
				management.configItemEntity.showErrorMes(management.configItemEntity.i18n('Foss.management.configItemEntity.requestTimeout'));//请求超时
			}else{
				management.configItemEntity.showErrorMes(management.configItemEntity.i18n('Foss.management.configItemEntity.confTypeFail')+json.message);
			}
		}
	});
};

//
Ext.onReady(function(){
	
	Ext.QuickTips.init();
	
	//modify by liangfuxiang 2012-04-25 BUG-7996
	//公共变量，用于保存重置的初始化信息
	publicConfigOrgRelationEntity=null;	
	
	//主要用于查询的下拉列表
	management.configItemEntity.initConfTypeStore=Ext.create('Foss.management.configItemEntity.initConfigTypeStore');
	Ext.Ajax.request({
		url:management.realPath('queryAllConfigTypes.action'),
		success:function(response){
			var result = Ext.decode(response.responseText);
			if(result.success){
				management.configItemEntity.initConfTypeStore.loadData(result.configItemVo.configTypeEntityList);
			}
		},
		exception:function(response){
			var result = Ext.decode(response.responseText);
			if(Ext.isEmpty(json)){
				management.configItemEntity.showErrorMes(management.configItemEntity.i18n('Foss.management.configItemEntity.requestTimeout'));//请求超时
			}else{
				management.configItemEntity.showErrorMes(management.configItemEntity.i18n('Foss.management.configItemEntity.confTypeFail')+json.message);
			}
		}
	});
	
	var confTypePanel = Ext.create('Foss.management.configItemEntity.ConfTypePanel');//配置项类型
	var confItemPanel = Ext.create('Foss.management.configItemEntity.ConfItemPanel');//配置项
	management.configItemEntity.ConfTypePanel = confTypePanel;
	management.configItemEntity.ConfItemPanel = confItemPanel;
	
	Ext.create('Ext.panel.Panel', {
		id : 'T_management-configItemEntityIndex_content',
		cls : "panelContentNToolbar",
		bodyCls : 'panelContentNToolbar-body',
		layout : 'auto',
		// 定义容器中的项
		items : [{
					xtype : 'tabpanel',
					frame : false,
					bodyCls : 'autoHeight',
					cls : 'innerTabPanel',
					activeTab : 0,
					items : [{
								title : management.configItemEntity.i18n('Foss.management.configItemEntity.confTypeTab'),
								tabConfig : {
									width : 120
								},
								itemId : 'ConfTypeTab',
								items : confTypePanel
							}, {
								title : management.configItemEntity.i18n('Foss.management.configItemEntity.confItemTab'),
								tabConfig : {
									width : 120
								},
								itemId : 'ConfItemTab',
								items : confItemPanel
							}]
				}],
		renderTo : 'T_management-configItemEntityIndex-body'
	});
});
