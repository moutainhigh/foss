//转换long类型为日期
baseinfo.changeLongToDate = function(value) {
	if (value != null) {
		var date = new Date(value);
		return date;
	} else {
		return null;
	}
};
//发送Ajax请求，发送并接收 json
baseinfo.requestJsonAjax = function(url,params,successFn,failFn)
{
	Ext.Ajax.request({
		url:url,
		jsonData:params,
		success:function(response){
			var result = Ext.decode(response.responseText);
			if(result.success){
				successFn(result);
			}else{
				failFn(result);
			}
		},
		failure:function(response){
			var result = Ext.decode(response.responseText);
			failFn(result);
		},
		exception:function(response){
			var result = Ext.decode(response.responseText);
			failFn(result);
		}
	});
};
//查询条件Form
Ext.define('Foss.baseinfo.expressSalesAgentMap.SelectForm',{
	extend:'Ext.form.Panel',
	id: 'Foss_baseinfo_expressSalesAgentMap_SelectForm_Id',
	layout:'column',
	frame : true,
	columnWidth: 0.98,
	title: baseinfo.expressSalesAgentMap.i18n('foss.baseinfo.queryCondition'), //查询条件
	defaults: {
		xtype : 'textfield',
		readOnly : false,
		margin:'5 5 5 10',
		anchor: '90%'
	},
	initComponent: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.items = [{
					//虚拟营业部
					name : 'expressSalesDeptCode',
					fieldLabel : baseinfo.expressSalesAgentMap.i18n('foss.baseinfo.expressSalesAgentMap.expressSalesDept'),//虚拟营业部
					xtype : 'orgCombSelector',//org组织表选择器
					expressSalesDepartment:'Y',//查询虚拟营业部（前台注入参数）
					columnWidth:0.37,
					//labelWidth:70,
					margin:'5 25 5 10'
				},{
					//快递代理网点
					name: 'expressAgentDeptCode',
					fieldLabel : baseinfo.expressSalesAgentMap.i18n('foss.baseinfo.expressSalesAgentMap.expressAgentDept'),//快递代理网点
					xtype : 'commonldpagencydeptselector',//查询快递代理网点 （后台注入参数）
					columnWidth:0.37
				}
		];
		//按钮（重置、保存）
		me.fbar = [{
			xtype : 'button', 
			width:80,
			text : baseinfo.expressSalesAgentMap.i18n('foss.baseinfo.reset'),//重置
			//根据按钮权限决定是否隐藏
			disabled:!baseinfo.expressSalesAgentMap.isPermission('expressSalesAgentMap/expressSalesAgentMapQueryButton'),
			hidden:!baseinfo.expressSalesAgentMap.isPermission('expressSalesAgentMap/expressSalesAgentMapQueryButton'),
			margin:'10 800 0 0',
			handler : function() {
				me.getForm().reset();
			}
		},{
			xtype : 'button', 
			width:80,
			cls:'yellow_button',
			text : baseinfo.expressSalesAgentMap.i18n('foss.baseinfo.query'),//查询
			//根据按钮权限决定是否隐藏
			disabled:!baseinfo.expressSalesAgentMap.isPermission('expressSalesAgentMap/expressSalesAgentMapQueryButton'),
			hidden:!baseinfo.expressSalesAgentMap.isPermission('expressSalesAgentMap/expressSalesAgentMapQueryButton'),
			margin:'10 0 0 0',
			handler : function() {
				Ext.getCmp('Foss_baseinfo_expressSalesAgentMap_ResultGrid_Id').pagingToolbar.moveFirst();
			}
		}];
		me.callParent([cfg]);
		}
});
//Grid中的Store中的模型Model
Ext.define('Foss.baseinfo.expressSalesAgentMap.Model', {
    extend: 'Ext.data.Model',
    fields:[{
		name:'id',//ID
		type:'string'
	},{
		name :'expressSalesDeptCode',//虚拟营业部编码
		type : 'string'
	},{
		name :'expressSalesDeptName',//虚拟营业部名称
		type : 'string'
	},{
		name :'expressAgentDeptCode',//快递代理网点编码
		type : 'string'
	},{
		name :'expressAgentDeptName',//快递代理网点名称
		type : 'string'
	},{
		name:'createDate', //创建时间             
		type : 'date',
		defaultValue : null,
		convert : baseinfo.changeLongToDate    
	},{
		name:'modifyDate', //修改时间                                
		type : 'date',
		defaultValue : null,
		convert : baseinfo.changeLongToDate  
	},{
		name:'createUser',//创建人工号           
		type:'String'                                 
	},{
		name:'modifyUser',//更新人工号 
		type:'String'    
	},{
		name:'active',//是否启用     
		type:'String'     
	}]
});
//查询结果Grid
Ext.define('Foss.baseinfo.expressSalesAgentMap.ResultGrid', {
	extend: 'Ext.grid.Panel',
	id:'Foss_baseinfo_expressSalesAgentMap_ResultGrid_Id',
	title : baseinfo.expressSalesAgentMap.i18n('foss.baseinfo.queryResults'),//查询结果
	frame: true,
	flex:1,
    sortableColumns:true,
    enableColumnHide:false,
    enableColumnMove:false,
	stripeRows : true, // 交替行效果
	selType : "rowmodel", // 选择方式设置为：行选择
	emptyText: baseinfo.expressSalesAgentMap.i18n('foss.baseinfo.queryResultIsNull'),//无内容时显示：查询结果为空
	constructor : function(config) {
		var me = this, 
		cfg = Ext.apply({}, config);
		me.addWindow=Ext.create('Foss.baseinfo.expressSalesAgentMap.AddWindow');
		me.updateWindow=Ext.create('Foss.baseinfo.expressSalesAgentMap.UpdateWindow');
		me.columns = [
		  {
		  	//序号列
		  	text : baseinfo.expressSalesAgentMap.i18n('foss.baseinfo.sequence'),//序号
			xtype: 'rownumberer',
			align: 'center',
			width:40
		},{
			//按钮列（修改、作废）
			text : baseinfo.expressSalesAgentMap.i18n('foss.baseinfo.operate'),//操作
			xtype:'actioncolumn',
			align: 'center',
			width:80,
			items: [{
				//修改按钮
				iconCls: 'deppon_icons_edit',
                tooltip: baseinfo.expressSalesAgentMap.i18n('foss.baseinfo.update'),//修改
                disabled:!baseinfo.expressSalesAgentMap.isPermission('expressSalesAgentMap/expressSalesAgentMapUpdateButton'),
				width:40,
                handler: function(grid,rowIndex,colIndex) {
                	//获取选中的那一行数据
    				var record=grid.getStore().getAt(rowIndex);
    				var updatewindow=me.updateWindow;
    				var windowForm=updatewindow.windowForm;
    				//批量赋值给表格（其实就是为了赋ID）
    				windowForm.getForm().setValues(record.data);
    				//选择器的赋值
    				windowForm.getForm().findField('expressSalesDeptCode').setCombValue(record.data.expressSalesDeptName,record.data.expressSalesDeptCode);
    				windowForm.getForm().findField('expressAgentDeptCode').setCombValue(record.data.expressAgentDeptName,record.data.expressAgentDeptCode);
    				updatewindow.show();
    				}
				},{
				//作废按钮
				iconCls: 'deppon_icons_cancel',
                tooltip: baseinfo.expressSalesAgentMap.i18n('foss.baseinfo.void'),//作废
                disabled:!baseinfo.expressSalesAgentMap.isPermission('expressSalesAgentMap/expressSalesAgentMapDeleteButton'),
				width:40,
                handler: function(grid, rowIndex, colIndex) {
            		baseinfo.showQuestionMes(baseinfo.expressSalesAgentMap.i18n('foss.baseinfo.isConfirmDelete'),function(e){//是否确认作废？
            			if(e=='yes'){//询问是否作废，选择是的话
            				//获取选中的数据
    						var record=grid.getStore().getAt(rowIndex);
            				//创建一个数组，把ID放进去，并把这个数组发送出去
            				var ids = new Array();
            				ids.push(record.get('id'));
            				var params = {'expressSalesAgentMapVo':{'ids':ids}};
            				var successFun = function(json){
            					baseinfo.showInfoMes(json.message);
            					me.pagingToolbar.moveFirst();
            				};
            				var failureFun = function(json){
            					if(Ext.isEmpty(json)){
            						baseinfo.showErrorMes(baseinfo.expressSalesAgentMap.i18n('foss.baseinfo.requestTimeout'));//请求超时
            					}else{
            						baseinfo.showErrorMes(json.message);
            					}
            				};
            				var url = baseinfo.realPath('deleteExpressSalesAgentMap.action');
            				baseinfo.requestJsonAjax(url,params,successFun,failureFun);
            			}
            		})
                }
			}]
		},{
			//数据列：虚拟营业部
			text : baseinfo.expressSalesAgentMap.i18n('foss.baseinfo.expressSalesAgentMap.expressSalesDept'),//虚拟营业部
			width:350,
			dataIndex : 'expressSalesDeptName'
		},{
			//数据列：快递代理网点
			text : baseinfo.expressSalesAgentMap.i18n('foss.baseinfo.expressSalesAgentMap.expressAgentDept'),//快递代理网点
			width:350,
			dataIndex : 'expressAgentDeptName'
		}];
		//store（内部有代理，发送ajax请求，并接受返回数据）
		me.store = Ext.create('Ext.data.Store',{
			model : 'Foss.baseinfo.expressSalesAgentMap.Model',//MODEL
			autoLoad : false,//不自动加载
			pageSize : 10,
			proxy : {
				type : 'ajax',
				actionMethods : 'post',
				url : baseinfo.realPath('queryExpressSalesAgentMapListByCondition.action'),//请求地址
				reader : {
					type : 'json',
					root : 'expressSalesAgentMapVo.expressSalesAgentMapEntityList',//获取的数据，实体List
					totalProperty : 'totalCount'//个数
				}
			},
			listeners: {
				beforeload: function(store, operation, eOpts){
					var queryForm = Ext.getCmp('Foss_baseinfo_expressSalesAgentMap_SelectForm_Id');
					if(queryForm!=null){
						var model = new Foss.baseinfo.expressSalesAgentMap.Model();
		    			queryForm.getForm().updateRecord(model);//将FORM中数据设置到MODEL里面
						//拿到查询表格中的数据，一个一个拿
						var expressSalesDeptCode = queryForm.getForm().findField('expressSalesDeptCode').getValue();
						var expressAgentDeptCode = queryForm.getForm().findField('expressAgentDeptCode').getValue();
						Ext.apply(operation,{
							params : { 
								'expressSalesAgentMapVo.expressSalesAgentMapEntity.expressSalesDeptCode':model.data.expressSalesDeptCode,
								'expressSalesAgentMapVo.expressSalesAgentMapEntity.expressAgentDeptCode':model.data.expressAgentDeptCode
							}
						});
					}
				}
		    }
		});
		me.listeners = {
	    	scrollershow: function(scroller) {
	    		if (scroller && scroller.scrollEl) {
	    				scroller.clearManagedListeners(); 
	    				scroller.mon(scroller.scrollEl, 'scroll', scroller.onElScroll, scroller); 
	    		}
	    	}
	    },
	    //表格最左侧多选框
		me.selModel = Ext.create('Ext.selection.CheckboxModel',{//多选框
					mode:'MULTI',
					checkOnly:true
				});
		//表格顶部按钮（新增、作废）
		me.tbar = [{
			text : baseinfo.expressSalesAgentMap.i18n('foss.baseinfo.add'),//新增
			disabled:!baseinfo.expressSalesAgentMap.isPermission('expressSalesAgentMap/expressSalesAgentMapAddButton'),
			hidden:!baseinfo.expressSalesAgentMap.isPermission('expressSalesAgentMap/expressSalesAgentMapAddButton'),
			handler :function(){
				//显示出新增窗口即可，不需做任何处理
				me.addWindow.show();
			} 
		},'-',{
			text : baseinfo.expressSalesAgentMap.i18n('foss.baseinfo.void'),//作废
			disabled:!baseinfo.expressSalesAgentMap.isPermission('expressSalesAgentMap/expressSalesAgentMapDeleteButton'),
			hidden:!baseinfo.expressSalesAgentMap.isPermission('expressSalesAgentMap/expressSalesAgentMapDeleteButton'),
			handler :function(){
				var selections = me.getSelectionModel().getSelection();//获取选中的多条记录
				if(selections.length<1){//判断是否至少选中了一条，如果没选
					baseinfo.showWoringMessage('请选择一条进行作废操作！');//请选择一条进行作废操作！
					return;//返回
				}
				baseinfo.showQuestionMes(baseinfo.expressSalesAgentMap.i18n('foss.baseinfo.isConfirmDelete'),function(e){//是否确认作废？
					if(e=='yes'){//询问是否删除，选择是的话
						//建一个数组，把所有ID全部放进去
						var ids = new Array();
						for(var i = 0 ; i<selections.length ; i++){
							ids.push(selections[i].get('id'));
						}
						var params = {'expressSalesAgentMapVo':{'ids':ids}};
						var successFun = function(json){
							baseinfo.showInfoMes(json.message);
							me.pagingToolbar.moveFirst();
						};
						var failureFun = function(json){
							if(Ext.isEmpty(json)){
								baseinfo.showErrorMes(baseinfo.expressSalesAgentMap.i18n('foss.baseinfo.requestTimeout'));//请求超时
							}else{
								baseinfo.showErrorMes(json.message);
							}
						};
						var url = baseinfo.realPath('deleteExpressSalesAgentMap.action');
						baseinfo.requestJsonAjax(url,params,successFun,failureFun);
					}
				})} 
		}];
		//德邦的分页工具
		me.pagingToolbar=Ext.create('Deppon.StandardPaging', {
				store : me.store,
				pageSize : 10,
				plugins: 'pagesizeplugin'
			});
		//将分页组件配置到Grid里
		me.bbar=me.pagingToolbar; 
		me.callParent([cfg]);
	}
});
//新增窗口
Ext.define('Foss.baseinfo.expressSalesAgentMap.AddWindow',{
	extend : 'Ext.window.Window',
	title : baseinfo.expressSalesAgentMap.i18n('foss.baseinfo.add'),//新增
	closable : true,
	resizable:true,//可以调整窗口的大小
	closeAction : 'hide',//点击关闭是隐藏窗口
	width :500,
	height :300,
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	listeners:{
		beforehide:function(me){
			//关闭窗口的时候清空数据，由于不能清本外场文本框，所以重置另外一个框
			this.windowForm.getForm().reset();
		}
	},
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.windowForm=Ext.create('Foss.baseinfo.expressSalesAgentMap.WindowForm');
		//新增窗口的底部按钮（取消、保存）
		me.fbar = [{
			text :baseinfo.expressSalesAgentMap.i18n('foss.baseinfo.cancel'),//取消
			margin:'0 10 0 0',
			handler :function(){
				me.close();
			}
		},{
			text : baseinfo.expressSalesAgentMap.i18n('foss.baseinfo.reset'),//重置
			margin:'0 220 0 0',
			handler : function() {
				me.windowForm.getForm().reset();
			}
		},{
			text : baseinfo.expressSalesAgentMap.i18n('foss.baseinfo.save'),//保存
			cls:'yellow_button',
			handler :function(){
				var windowForm=me.windowForm;
				if(windowForm.getForm().isValid()){//校验form是否通过非空校验
		    		var model = new Foss.baseinfo.expressSalesAgentMap.Model();
		    		windowForm.getForm().updateRecord(model);//将FORM中数据设置到MODEL里面
		    		var params = {'expressSalesAgentMapVo':{'expressSalesAgentMapEntity':model.data}};
		    		var successFun = function(json){
						baseinfo.showInfoMes(json.message);//提示保存成功
						me.close();
						//刷新查询结果
						Ext.getCmp('Foss_baseinfo_expressSalesAgentMap_ResultGrid_Id').pagingToolbar.moveFirst();
					};
					var failureFun = function(json){
						if(Ext.isEmpty(json)){
							baseinfo.showErrorMes(baseinfo.expressSalesAgentMap.i18n('foss.baseinfo.requestTimeout'));//请求超时
						}else{
							baseinfo.showErrorMes(json.message);//提示失败原因
						}
					};
					var url = baseinfo.realPath('addExpressSalesAgentMap.action');
					baseinfo.requestJsonAjax(url,params,successFun,failureFun);//发送AJAX请求
				}}
		}];
		me.items = [me.windowForm];
		me.callParent([cfg]);
	}
});
/**
 * 修改窗口
 */
Ext.define('Foss.baseinfo.expressSalesAgentMap.UpdateWindow',{
	extend : 'Ext.window.Window',
	title : baseinfo.expressSalesAgentMap.i18n('foss.baseinfo.update'),//修改
	closable : true,
	resizable:false,
	closeAction : 'hide',
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	width :500,
	height :300,
	listeners:{
		beforehide:function(me){
			//关闭窗口的时候清空数据，由于不能清本外场文本框，所以重置另外一个框
			this.windowForm.getForm().reset();//表格重置
		}
	},
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.windowForm=Ext.create('Foss.baseinfo.expressSalesAgentMap.WindowForm');
		//修改窗口的底部按钮（取消、保存）
		me.fbar = [{
			text :baseinfo.expressSalesAgentMap.i18n('foss.baseinfo.cancel'),//取消
			margin:'0 10 0 0',
			handler :function(){
				me.close();
			}
		},{
			text : baseinfo.expressSalesAgentMap.i18n('foss.baseinfo.reset'),//重置
			margin:'0 220 0 0',
			handler : function() {
				me.windowForm.getForm().reset();
			}
		},{
			text : baseinfo.expressSalesAgentMap.i18n('foss.baseinfo.save'),//保存
			cls:'yellow_button',
			handler :function(){
				var windowForm=me.windowForm;
				if(windowForm.getForm().isValid()){//校验form是否通过非空校验
					var model = new Foss.baseinfo.expressSalesAgentMap.Model();
		    		windowForm.getForm().updateRecord(model);//将FORM中数据设置到MODEL里面
			    	var params = {'expressSalesAgentMapVo':{'expressSalesAgentMapEntity':model.data}};//组织新增数据
			    	var successFun = function(json){
						baseinfo.showInfoMes(json.message);//提示新增成功
						me.close();
						//刷新查询结果
						Ext.getCmp('Foss_baseinfo_expressSalesAgentMap_ResultGrid_Id').pagingToolbar.moveFirst();
					};
					var failureFun = function(json){
						if(Ext.isEmpty(json)){
							baseinfo.showErrorMes(baseinfo.expressSalesAgentMap.i18n('foss.baseinfo.requestTimeout'));//请求超时
						}else{
							baseinfo.showErrorMes(json.message);//提示失败原因
						}
					};
					var url = baseinfo.realPath('updateExpressSalesAgentMap.action');
					baseinfo.requestJsonAjax(url,params,successFun,failureFun);//发送AJAX请求
				}
		    } 
		}];
		me.items = [me.windowForm];
		me.callParent([cfg]);
	}
});
//新增窗口、修改窗口内部的Form
Ext.define('Foss.baseinfo.expressSalesAgentMap.WindowForm', {
	extend : 'Ext.form.Panel',
	frame: true,
	flex:1,
    defaults : {
    	xtype:'textfield',
    	margin : '5 5 5 10',
    	labelWidth:80,
    	anchor: '90%'
    },
    layout:'anchor',
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [{
					//虚拟营业部
					name : 'expressSalesDeptCode',
					fieldLabel : baseinfo.expressSalesAgentMap.i18n('foss.baseinfo.expressSalesAgentMap.expressSalesDept'),//虚拟营业部
					xtype : 'orgCombSelector',//org组织表选择器
					expressSalesDepartment:'Y',//查询虚拟营业部（前台注入参数）
					labelWidth:100,
					margin:'5 5 20 10',
					allowBlank:false
				},{
					//快递代理网点
					name: 'expressAgentDeptCode',
					fieldLabel : baseinfo.expressSalesAgentMap.i18n('foss.baseinfo.expressSalesAgentMap.expressAgentDept'),//快递代理网点
					xtype : 'commonldpagencydeptselector',//查询快递代理网点 （后台注入参数）
					labelWidth:100,
					//width:400,
					allowBlank:false
				},{
					//ID，隐藏，用于修改方法的检索参数
					name:'id',
					hidden:true
		}];
		me.callParent([cfg]);
	}
});

Ext.onReady(function() {
	Ext.QuickTips.init();
	if (Ext.getCmp('T_baseinfo-expressSalesAgentMap_content')) {
		return;
	};
	Ext.getCmp('T_baseinfo-expressSalesAgentMap').add(Ext.create('Ext.panel.Panel', {
		id : 'T_baseinfo-expressSalesAgentMap_content',
		cls : 'panelContentNToolbar',
		bodyCls : 'panelContentNToolbar-body',
		items: [
			Ext.create('Foss.baseinfo.expressSalesAgentMap.SelectForm'),
			Ext.create('Foss.baseinfo.expressSalesAgentMap.ResultGrid')
		]
	}));
});