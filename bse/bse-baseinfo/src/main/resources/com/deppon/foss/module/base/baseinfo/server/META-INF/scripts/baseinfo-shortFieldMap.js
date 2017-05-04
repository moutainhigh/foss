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
Ext.define('Foss.baseinfo.shortFieldMap.SelectForm',{
	extend:'Ext.form.Panel',
	id: 'Foss_baseinfo_shortFieldMap_SelectForm_Id',
	layout:'column',
	frame : true,
	columnWidth: 0.98,
	title: baseinfo.shortFieldMap.i18n('foss.baseinfo.shortFieldMap.query'), //查询
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
					//外场
					name : 'name',
					fieldLabel : baseinfo.shortFieldMap.i18n('foss.baseinfo.outfield'),//外场
					xtype : 'dynamicorgcombselector',//动态部门公共选择器
					type : 'ORG',//部门
					transferCenter:'Y',//查询外场
					labelWidth:70,
					columnWidth:0.3,
					listeners: {
						//当组件被添加到容器中执行的方法
						added:function(container, component , number , eOpts){
								//业务要求：外场自动显示成自己的顶级外场，不可编辑
							   var params = {'shortFieldMapVo':{'shortFieldMapEntity':{
					              'code':FossUserContext.getCurrentDeptCode()
					            }}};
								var successFun = function(json){
									//从返回的json中取出值，赋值给本选择器
									me.getForm().findField('name').setCombValue(json.shortFieldMapVo.shortFieldMapEntity.name,json.shortFieldMapVo.shortFieldMapEntity.code);
									//设置成只读
									me.getForm().findField('name').setReadOnly(true)
								};
								var failureFun = function(json){
									if(Ext.isEmpty(json)){
										baseinfo.showErrorMes(baseinfo.shortFieldMap.i18n('foss.baseinfo.requestTimeout'));//请求超时
									}else{
										baseinfo.showErrorMes(json.message);
									}
								};
								var url = baseinfo.realPath('queryTransferCenter.action');
								baseinfo.requestJsonAjax(url,params,successFun,failureFun);
						}
					}
				},{
					//对应短距离外场
					name: 'shortFieldName',
					xtype : 'dynamicorgcombselector',//动态部门公共选择器
					type : 'ORG',//部门
					transferCenter:'Y',//查询外场
					fieldLabel : baseinfo.shortFieldMap.i18n('foss.baseinfo.shortFieldMap.shortField'),//对应短距离外场
					columnWidth:0.4
				}
		];
		//底部按钮（重置、保存）
		me.fbar = [{
			xtype : 'button', 
			width:80,
			text : baseinfo.shortFieldMap.i18n('foss.baseinfo.reset'),//重置
			//根据按钮权限决定是否隐藏
			disabled:!baseinfo.shortFieldMap.isPermission('shortFieldMap/shortFieldMapQueryButton'),
			hidden:!baseinfo.shortFieldMap.isPermission('shortFieldMap/shortFieldMapQueryButton'),
			margin:'0 800 0 0',
			handler : function() {
				me.getForm().findField('shortFieldName').reset();
			}
		},{
			xtype : 'button', 
			width:80,
			cls:'yellow_button',
			text : baseinfo.shortFieldMap.i18n('foss.baseinfo.query'),//查询
			//根据按钮权限决定是否隐藏
			disabled:!baseinfo.shortFieldMap.isPermission('shortFieldMap/shortFieldMapQueryButton'),
			hidden:!baseinfo.shortFieldMap.isPermission('shortFieldMap/shortFieldMapQueryButton'),
			handler : function() {
				Ext.getCmp('Foss_baseinfo_shortFieldMap_ResultGrid_Id').getPagingToolbar().moveFirst();
			}
		}];
		me.callParent([cfg]);
		}
});
//Grid中的Store中的模型Model
Ext.define('Foss.baseinfo.shortFieldMap.Model', {
    extend: 'Ext.data.Model',
    fields:[{
		name:'id',//ID
		type:'string'
	},{
		name :'code',//本外场code
		type : 'string'
	},{
		name : 'name',//本外场名称
		type : 'string'
	},{
		name :'shortFieldCode',//短距离外场code
		type : 'string'
	},{
		name : 'shortFieldName',//短距离外场名称
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
Ext.define('Foss.baseinfo.shortFieldMap.ResultGrid', {
	extend: 'Ext.grid.Panel',
	id:'Foss_baseinfo_shortFieldMap_ResultGrid_Id',
	title : baseinfo.shortFieldMap.i18n('foss.baseinfo.shortFieldMap.shortFieldMapList'),//短距离外场映射列表
	frame: true,
	flex:1,
    sortableColumns:false,
    enableColumnHide:false,
    enableColumnMove:false,
	stripeRows : true, // 交替行效果
	selType : "rowmodel", // 选择方式设置为：行选择
	emptyText: baseinfo.shortFieldMap.i18n('foss.baseinfo.queryResultIsNull'),//无内容时显示：查询结果为空
	
	constructor : function(config) {
		var me = this, 
		cfg = Ext.apply({}, config);
		me.columns = [
		  {
		  	//序号列
			xtype: 'rownumberer',
			width:40,
			text : baseinfo.shortFieldMap.i18n('foss.baseinfo.sequence')//序号
		},{
			//按钮列（修改、作废）
			text : baseinfo.shortFieldMap.i18n('foss.baseinfo.operate'),//操作
			xtype:'actioncolumn',
			align: 'center',
			width:80,
			items: [{
				//修改按钮
				iconCls: 'deppon_icons_edit',
                tooltip: baseinfo.shortFieldMap.i18n('foss.baseinfo.update'),//修改
                disabled:!baseinfo.shortFieldMap.isPermission('shortFieldMap/shortFieldMapUpdateButton'),
				width:42,
                handler: function(grid,rowIndex,colIndex) {
                	//获取选中的那一行数据
    				var record=grid.getStore().getAt(rowIndex);
    				var updatewindow=me.getUpdateWindow();
    				//赋值给更新窗口的属性，用于检验未修改情况下的保存
    				updatewindow.code=record.data.code;
    				updatewindow.shortFieldCode=record.data.shortFieldCode;
    				var form=updatewindow.down('form');
    				//赋值给表格（ID）
    				form.getForm().setValues(record.data);
    				//选择器的赋值
    				form.getForm().findField('shortFieldCode').setCombValue(record.data.shortFieldName,record.data.shortFieldCode);
    				updatewindow.show();
    				}
				},{
				//作废按钮
				iconCls: 'deppon_icons_cancel',
                tooltip: baseinfo.shortFieldMap.i18n('foss.baseinfo.void'),//作废
                disabled:!baseinfo.shortFieldMap.isPermission('shortFieldMap/shortFieldMapDeleteButton'),
				width:42,
                handler: function(grid, rowIndex, colIndex) {
            		//获取选中的数据
    				var record=grid.getStore().getAt(rowIndex);
            		baseinfo.showQuestionMes(baseinfo.shortFieldMap.i18n('foss.baseinfo.shortFieldMap.deleteOrNot'),function(e){//是否要作废这个映射关系？
            			if(e=='yes'){//询问是否作废，选择是的话
            				//创建一个数组，把ID放进去，并把这个数组发送出去
            				var ids = new Array();
            				ids.push(record.get('id'));
            				var params = {'shortFieldMapVo':{'ids':ids}};
            				var successFun = function(json){
            					baseinfo.showInfoMes(json.message);
            					me.getPagingToolbar().moveFirst();
            				};
            				var failureFun = function(json){
            					if(Ext.isEmpty(json)){
            						baseinfo.showErrorMes(baseinfo.shortFieldMap.i18n('foss.baseinfo.requestTimeout'));//请求超时
            					}else{
            						baseinfo.showErrorMes(json.message);
            					}
            				};
            				var url = baseinfo.realPath('deleteShortFieldMap.action');
            				baseinfo.requestJsonAjax(url,params,successFun,failureFun);
            			}
            		})
                }
			}]
		},{
			//数据列：外场名称
			text : baseinfo.shortFieldMap.i18n('foss.baseinfo.fieldName'),//外场名称
			width:170,
			dataIndex : 'name'
		},{
			//数据列：对应短距离外场名称
			text : baseinfo.shortFieldMap.i18n('foss.baseinfo.shortFieldMap.shortFieldName'),//对应短距离外场名称
			width:270,
			dataIndex : 'shortFieldName'
		}];
		//store（内部有代理，发送ajax请求，并接受返回数据）
		me.store = Ext.create('Ext.data.Store',{
			model : 'Foss.baseinfo.shortFieldMap.Model',//短距离外场的MODEL
			autoLoad : false,//不自动加载
			pageSize : 20,
			proxy : {
				type : 'ajax',
				actionMethods : 'post',
				url : baseinfo.realPath('queryShortFieldMapListByCondition.action'),//请求地址
				reader : {
					type : 'json',
					root : 'shortFieldMapVo.shortFieldMapEntityList',//获取的数据，实体List
					totalProperty : 'totalCount'//个数
				}
			},
			listeners: {
				beforeload: function(store, operation, eOpts){
					var queryForm = Ext.getCmp('Foss_baseinfo_shortFieldMap_SelectForm_Id');
					if(queryForm!=null){
						//拿到查询表格中的数据，一个一个拿
						var code = queryForm.getForm().findField('name').getValue();
						var shortFieldCode = queryForm.getForm().findField('shortFieldName').getValue();
						Ext.apply(operation,{
							params : {
								'shortFieldMapVo.shortFieldMapEntity.code':code,//外场CODE
								'shortFieldMapVo.shortFieldMapEntity.shortFieldCode':shortFieldCode//短距离外场CODE
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
			text : baseinfo.shortFieldMap.i18n('foss.baseinfo.add'),//新增
			disabled:!baseinfo.shortFieldMap.isPermission('shortFieldMap/shortFieldMapAddButton'),
			hidden:!baseinfo.shortFieldMap.isPermission('shortFieldMap/shortFieldMapAddButton'),
			handler :function(){
				//显示出新增窗口即可，不需做任何处理
				me.getAddWindow().show();
			} 
		},'-',{
			text : baseinfo.shortFieldMap.i18n('foss.baseinfo.void'),//作废
			disabled:!baseinfo.shortFieldMap.isPermission('shortFieldMap/shortFieldMapDeleteButton'),
			hidden:!baseinfo.shortFieldMap.isPermission('shortFieldMap/shortFieldMapDeleteButton'),
			handler :function(){
				var selections = me.getSelectionModel().getSelection();//获取选中的多条记录
				if(selections.length<1){//判断是否至少选中了一条，如果没选
					baseinfo.showWoringMessage('请选择一条进行作废操作！');//请选择一条进行作废操作！
					return;//返回
				}
				baseinfo.showQuestionMes(baseinfo.shortFieldMap.i18n('foss.baseinfo.shortFieldMap.deleteTheyOrNot'),function(e){//是否要作废这些映射关系？
					if(e=='yes'){//询问是否删除，选择是的话
						//建一个数组，把所有ID全部放进去
						var ids = new Array();
						for(var i = 0 ; i<selections.length ; i++){
							ids.push(selections[i].get('id'));
						}
						var params = {'shortFieldMapVo':{'ids':ids}};
						var successFun = function(json){
							baseinfo.showInfoMes(json.message);
							me.getPagingToolbar().moveFirst();
						};
						var failureFun = function(json){
							if(Ext.isEmpty(json)){
								baseinfo.showErrorMes(baseinfo.shortFieldMap.i18n('foss.baseinfo.requestTimeout'));//请求超时
							}else{
								baseinfo.showErrorMes(json.message);
							}
						};
						var url = baseinfo.realPath('deleteShortFieldMap.action');
						baseinfo.requestJsonAjax(url,params,successFun,failureFun);
					}
				})} 
		}];
		//德邦的分页工具
		var sp=Ext.create('Deppon.StandardPaging', {
				store : me.store,
				pageSize : 20
			});
		//将分页组件配置到Grid里
		me.bbar=sp; 
		//获得分页组件的方法，用于查询
		me.getPagingToolbar = function() {
			return sp;
		};
		var aw=Ext.create('Foss.baseinfo.shortFieldMap.AddWindow');
		var uw=Ext.create('Foss.baseinfo.shortFieldMap.UpdateWindow');
		//获得新增窗口和修改窗口的方法
		me.getAddWindow = function() {
			return aw;
		};
		me.getUpdateWindow = function() {
			return uw;
		};
		me.callParent([cfg]);
	}
});
//新增窗口
Ext.define('Foss.baseinfo.shortFieldMap.AddWindow',{
	extend : 'Ext.window.Window',
	title : baseinfo.shortFieldMap.i18n('foss.baseinfo.shortFieldMap.addShortFieldMap'),//新增短距离外场映射
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
			this.down('form').getForm().findField('shortFieldCode').reset();
		}
	},
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		//新增窗口的底部按钮（取消、保存）
		me.fbar = [{
			text :baseinfo.shortFieldMap.i18n('foss.baseinfo.cancel'),//取消
			margin:'0 0 0 330',
			handler :function(){
				me.close();
			}
		},{
			text : baseinfo.shortFieldMap.i18n('foss.baseinfo.save'),//保存
			cls:'yellow_button',
			handler :function(){
				var form=me.down('form');
				if(form.getForm().isValid()){//校验form是否通过非空校验
		    		var model = new Foss.baseinfo.shortFieldMap.Model();
		    		form.getForm().updateRecord(model);//将FORM中数据设置到MODEL里面
		    		var params = {'shortFieldMapVo':{'shortFieldMapEntity':model.data}};
		    		var successFun = function(json){
						baseinfo.showInfoMes(json.message);//提示保存成功
						me.close();
						//刷新查询结果
						Ext.getCmp('Foss_baseinfo_shortFieldMap_ResultGrid_Id').getPagingToolbar().moveFirst();
					};
					var failureFun = function(json){
						if(Ext.isEmpty(json)){
							baseinfo.showErrorMes(baseinfo.shortFieldMap.i18n('foss.baseinfo.requestTimeout'));//请求超时
						}else{
							baseinfo.showErrorMes(json.message);//提示失败原因
						}
					};
					var url = baseinfo.realPath('addShortFieldMap.action');
					baseinfo.requestJsonAjax(url,params,successFun,failureFun);//发送AJAX请求
				}}
		}];
		me.items = [Ext.create('Foss.baseinfo.shortFieldMap.Form')];
		me.callParent([cfg]);
	}
});
/**
 * 修改窗口
 */
Ext.define('Foss.baseinfo.shortFieldMap.UpdateWindow',{
	extend : 'Ext.window.Window',
	title : baseinfo.shortFieldMap.i18n('foss.baseinfo.shortFieldMap.updateShortFieldMap'),//修改短距离外场映射
	code:null,
	shortFieldCode:null,
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
			this.down('form').getForm().findField('shortFieldCode').reset();//表格重置
		}
	},
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		//修改窗口的底部按钮（取消、保存）
		me.fbar = [{
			text :baseinfo.shortFieldMap.i18n('foss.baseinfo.cancel'),//取消
			margin:'0 0 0 330',
			handler :function(){
				me.close();
			}
		},{
			text : baseinfo.shortFieldMap.i18n('foss.baseinfo.save'),//保存
			cls:'yellow_button',
			handler :function(){
				var form=me.down('form');
				if(form.getForm().isValid()){//校验form是否通过非空校验
					var model = new Foss.baseinfo.shortFieldMap.Model();
		    		form.getForm().updateRecord(model);//将FORM中数据设置到MODEL里面
		    		//当没修改原数据的情况下，点击保存，将直接提示保存成功，不走后台
					if(me.code==model.data.code&&me.shortFieldCode==model.data.shortFieldCode){
			    		baseinfo.showInfoMes('保存成功!');
			    		me.close();
					}else{
			    		var params = {'shortFieldMapVo':{'shortFieldMapEntity':model.data}};//组织新增数据
			    		var successFun = function(json){
							baseinfo.showInfoMes(json.message);//提示新增成功
							me.close();
							//刷新查询结果
							Ext.getCmp('Foss_baseinfo_shortFieldMap_ResultGrid_Id').getPagingToolbar().moveFirst();
						};
						var failureFun = function(json){
							if(Ext.isEmpty(json)){
								baseinfo.showErrorMes(baseinfo.shortFieldMap.i18n('foss.baseinfo.requestTimeout'));//请求超时
							}else{
								baseinfo.showErrorMes(json.message);//提示失败原因
							}
						};
						var url = baseinfo.realPath('updateShortFieldMap.action');
						baseinfo.requestJsonAjax(url,params,successFun,failureFun);//发送AJAX请求
					}
		    	}} 
		}];
		me.items = [Ext.create('Foss.baseinfo.shortFieldMap.Form')];
		me.callParent([cfg]);
	}
});
//新增窗口、修改窗口内部的Form
Ext.define('Foss.baseinfo.shortFieldMap.Form', {
	extend : 'Ext.form.Panel',
	frame: true,
	isUpdate:false,
	flex:1,
	collapsible: true,
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
					//外场名称
					name : 'code',
					fieldLabel : baseinfo.shortFieldMap.i18n('foss.baseinfo.fieldName'),//外场名称
					xtype : 'dynamicorgcombselector',
					type : 'ORG',
					transferCenter:'Y',//查询外场
					labelWidth:100,
					listeners: {
						//当组件被添加到容器中执行的方法
						added:function(container, component , number , eOpts){
							   //业务要求：外场自动显示成自己的顶级外场，不可编辑
							   var params = {'shortFieldMapVo':{'shortFieldMapEntity':{
					              'code':FossUserContext.getCurrentDeptCode()
					            }}};
								var successFun = function(json){
									me.getForm().findField('code').setCombValue(json.shortFieldMapVo.shortFieldMapEntity.name,json.shortFieldMapVo.shortFieldMapEntity.code);
									me.getForm().findField('code').setReadOnly(true)
								};
								var failureFun = function(json){
									if(Ext.isEmpty(json)){
										baseinfo.showErrorMes(baseinfo.shortFieldMap.i18n('foss.baseinfo.requestTimeout'));//请求超时
									}else{
										baseinfo.showErrorMes(json.message);
									}
								};
								var url = baseinfo.realPath('queryTransferCenter.action');
								baseinfo.requestJsonAjax(url,params,successFun,failureFun);
						}
					}
		},{
			//对应短距离外场
			name: 'shortFieldCode',
			xtype : 'dynamicorgcombselector',
			type : 'ORG',
			transferCenter:'Y',//查询外场
			labelWidth:100,
			width:400,
			allowBlank:false,
			fieldLabel : baseinfo.shortFieldMap.i18n('foss.baseinfo.shortFieldMap.shortField')//对应短距离外场
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
	if (Ext.getCmp('T_baseinfo-shortFieldMap_content')) {
		return;
	};
	Ext.getCmp('T_baseinfo-shortFieldMap').add(Ext.create('Ext.panel.Panel', {
		id : 'T_baseinfo-shortFieldMap_content',
		cls : 'panelContentNToolbar',
		bodyCls : 'panelContentNToolbar-body',
		items: [
			Ext.create('Foss.baseinfo.shortFieldMap.SelectForm'),
			Ext.create('Foss.baseinfo.shortFieldMap.ResultGrid')
		]
	}));
});
