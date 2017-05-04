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
		timeout:60000,
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

//Grid中的Store中的模型Model
Ext.define('Foss.baseinfo.focusRecordManagement.Model', {
    extend: 'Ext.data.Model',
    fields:[{
		name:'id',//ID
		type:'string'
	},{
		name :'billingGroupCode',//开单组编号
		type : 'string'
	},{
		name : 'billingGroupName',
		type : 'string'
	},{
		name :'transDepartmentName',//车队
		type : 'string'
	},{
		name : 'transDepartmentCode',
		type : 'string'
	},{
		name :'salesDepartmentName',//营业部
		type : 'string'
	},{
		name : 'salesDepartmentCode',
		type : 'string'
	},{
		name : 'billingAmount',//开单量
		type : 'string'
	},{
		name:'createDate', //创建时间             
		type : 'date',
		defaultValue : null,
		convert : dateConvert    
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
	},{
		name:'startDate',//开始时间
		type:'date',
		convert : dateConvert
	},{
		name:'endDate',//结束时间
		type:'date',
		convert : dateConvert
	},{
		name:'lastDate',
		type:'String',
		convert:function(v,record){
			if(Ext.isEmpty(record.data.startDate)||Ext.isEmpty(record.data.endDate)){
				return null;
			}
			return Ext.Date.format(record.data.startDate,'H:i')+'-'+Ext.Date.format(record.data.endDate,'H:i')
		}
	}]
});
//store
Ext.define('Foss.baseinfo.focusRecordManagement.Store',{
	extend:'Ext.data.Store',
	model:'Foss.baseinfo.focusRecordManagement.Model',
	pageSize:10,
	proxy : {
		timeout:60000,
		type : 'ajax',
		actionMethods : 'post',
		url : baseinfo.realPath('queryFocusRecordManagementList.action'),// 查询的url
		reader : {
			type : 'json',
			root : 'focusRecordManagementVo.entityList',// 结果集
			totalProperty : 'totalCount'// 个数
		}
	}
});

Ext.define('Foss.baseinfo.focusRecordManagement.QueryForm',{
	extend:'Ext.form.Panel',
	title : baseinfo.focusRecordManagement.i18n('foss.baseinfo.queryCondition'),// 查询条件
	frame : true,
	collapsible : true,
	defaults: {
		readOnly : false,
		margin:'5 5 5 10',
		anchor: '100%',
		labelWidth : 100,
		width : 100
	},
	height :180,
	defaultType : 'textfield',
	 //列布局
	layout:'column',
	constructor:function(config){
		var me =this,cfg =Ext.apply({},config);
		me.items=[{
			xtype:'dynamicorgcombselector',
			fieldLabel:'集中开单组',
			name:'billingGroupCode',
			billingGroup:'Y',
			type : 'ORG',
			columnWidth:0.4
		},{
			border: 1,
			xtype:'container',
			columnWidth:1, 
			defaultType:'button',
			layout:'column',
			items:[{
				  text : baseinfo.focusRecordManagement.i18n('foss.baseinfo.reset'),//重置
				  disabled:!baseinfo.focusRecordManagement.isPermission('focusRecordManagement/queryButton'),
				  hidden:!baseinfo.focusRecordManagement.isPermission('focusRecordManagement/queryButton'),
				  columnWidth:.08,
				  handler : function() {
						me.getForm().reset();
					}
			  	},{
					xtype:'container',
					border:false,
					html:'&nbsp;',
					columnWidth:.84
				},{
				  text : baseinfo.focusRecordManagement.i18n('foss.baseinfo.query'),//查询
				  disabled:!baseinfo.focusRecordManagement.isPermission('focusRecordManagement/queryButton'),
				  hidden:!baseinfo.focusRecordManagement.isPermission('focusRecordManagement/queryButton'),
				  columnWidth:.08,
				  cls:'yellow_button',  
				  handler:function() {
					Ext.getCmp('T_baseinfo-focusRecordManagement_content').getQueryGrid().getPagingToolbar().moveFirst();
				  }
			  	}]
		}];
		me.callParent([cfg]);
	}
});

Ext.define('Foss.baseinfo.focusRecordManagement.QueryGrid',{
	extend:'Ext.grid.Panel',
	title:baseinfo.focusRecordManagement.i18n('foss.baseinfo.queryGrid'),//'排班查询结果列表',
	frame:true,
	stripeRows : true, // 交替行效果
	selType : "rowmodel", // 选择类型设置为：行选择
	emptyText:baseinfo.focusRecordManagement.i18n('foss.baseinfo.queryResultIsNull'),//查询结果为空
	pagingToolbar : null,
	getPagingToolbar : function() {
		if (this.pagingToolbar == null) {
			this.pagingToolbar = Ext.create('Deppon.StandardPaging', {
				store : this.store,
				plugins: 'pagesizeplugin'
			});
		}
		return this.pagingToolbar;
	},
	getMyListeners:function(){
		var me=this;
		return {
			itemdblclick: function(view,record) {
            	var model = record.data;
            	me.getViewWindow().model=model;
				me.getViewWindow().show();
    			
	    	}
		};
	},
	//新增窗口
	addWindow:null,
	getAddWindow:function(){
		if(this.addWindow==null){
			this.addWindow=Ext.create('Foss.baseinfo.focusRecordManagement.AddWindow');
			this.addWindow.parent=this;
		}
		return this.addWindow;
	},
	//修改窗口
	updateWindow:null,
	getUpdateWindow:function(){
		if(this.updateWindow==null){
			this.updateWindow =Ext.create('Foss.baseinfo.focusRecordManagement.UpdateWindow');
			this.updateWindow.parent =this;
		}
		return this.updateWindow;
	},
	//报表式查看窗口
	viewWindow:null,
	getViewWindow:function(){
		if(this.viewWindow==null){
			this.viewWindow =Ext.create('Foss.baseinfo.focusRecordManagement.ViewWindow');
		}
		return this.viewWindow;
	},
	constructor:function(config){
		var me =this,cfg =Ext.apply({},config);
		me.columns =[{
			align:'center',
			xtype:'actioncolumn',
			text : baseinfo.focusRecordManagement.i18n('foss.baseinfo.operate'),//操作
			items:[{
				iconCls: 'deppon_icons_edit',
				tooltip:baseinfo.focusRecordManagement.i18n('foss.baseinfo.edit'),//编辑
				width:30,
				handler:function(grid,rowIndex,colIndex){
					var rowModel =grid.getStore().getAt(rowIndex);
					var updateWindow =me.getUpdateWindow();
					updateWindow.billingAmount =rowModel.data.billingAmount;
					var form=updateWindow.down('form');
					form.getForm().findField('billingGroupCode').setCombValue(rowModel.data.billingGroupName,rowModel.data.billingGroupCode);
					form.getForm().findField('billingAmount').setValue(rowModel.data.billingAmount);
					form.getForm().findField('id').setValue(rowModel.data.id);
					form.getForm().findField('billingGroupCode').setReadOnly(true);
					form.getForm().findField('startDate').setValue(rowModel.data.startDate);
					form.getForm().findField('endDate').setValue(rowModel.data.endDate);
					updateWindow.show();
				}
			},{
				iconCls: 'deppon_icons_cancel',
				tooltip:baseinfo.focusRecordManagement.i18n('foss.baseinfo.void'),//作废
				width:30,
				  handler: function(grid, rowIndex, colIndex) {
            		//获取选中的数据
    				var record=grid.getStore().getAt(rowIndex);
            		baseinfo.showQuestionMes(baseinfo.focusRecordManagement.i18n('foss.baseinfo.shortFieldMap.deleteOrNot'),function(e){//是否要作废这个映射关系？
            			if(e=='yes'){//询问是否作废，选择是的话
            				//创建一个数组，把ID放进去，并把这个数组发送出去
            				var ids=record.get('id');
            				var params = {'focusRecordManagementVo':{'ids':ids}};
            				var successFun = function(json){
            					baseinfo.showInfoMes(json.message);
            					//me.getPagingToolbar().moveFirst();
            				};
            				var failureFun = function(json){
            					if(Ext.isEmpty(json)){
            						baseinfo.showErrorMes(baseinfo.focusRecordManagement.i18n('foss.baseinfo.requestTimeout'));//请求超时
            					}else{
            						baseinfo.showErrorMes(json.message);
            					}
            				};
            				var url = baseinfo.realPath('deleteFocusRecordManagement.action');
            				baseinfo.requestJsonAjax(url,params,successFun,failureFun);
            			}
            		})
                }
			}]
		},{
			text:'部门名称',
			dataIndex:'billingGroupName',
			width:150
		},{
			text:'部门开单量',//收派小区
			dataIndex:'billingAmount',
			width:120
		},{
			text:'服务车队',//车牌
			dataIndex:'transDepartmentName',
			width:150
		},{
			text:'创建人',
			dataIndex:'createUser'
			
		},{
			text:'创建时间',
			dataIndex:'createDate',
			xtype: 'datecolumn',   
			format:'Y-m-d'
		},{
			text:'生效时间段',
			dataIndex:'lastDate',
			width:150
		}];
		me.store=Ext.create('Foss.baseinfo.focusRecordManagement.Store',{
			autoLoad:false,
			listeners:{
				beforeload:function(store, operation, eOpts){
					var queryForm =Ext.getCmp('T_baseinfo-focusRecordManagement_content').getQueryForm();
					if(queryForm !=null){
						var queryFormValue =queryForm.getForm();
						Ext.apply(operation,{
							params:{
								'focusRecordManagementVo.entity.billingGroupCode':queryFormValue.findField('billingGroupCode').getValue()
							}
						});
					}
				}
			}
		});
		me.tbar=[{
			text:'新增', //新增
			disabled:!baseinfo.focusRecordManagement.isPermission('focusRecordManagement/addButton'),
			hidden:!baseinfo.focusRecordManagement.isPermission('focusRecordManagement/addButton'),
			handler:function(){
				me.getAddWindow().show();
			}
		}];
		me.listeners ={
				scrollershow: function(scroller) {
		    		if (scroller && scroller.scrollEl) {
		    				scroller.clearManagedListeners(); 
		    				scroller.mon(scroller.scrollEl, 'scroll', scroller.onElScroll, scroller); 
		    		}
		    	}	
		};
		me.selModel = Ext.create('Ext.selection.CheckboxModel',{//多选框
			mode:'MULTI',
			checkOnly:true
		});
		me.bbar =me.getPagingToolbar();
		me.getPagingToolbar().store = me.store;
		me.listeners = me.getMyListeners();
		me.callParent([cfg]);
	}
});
//新增窗口
Ext.define('Foss.baseinfo.focusRecordManagement.AddWindow',{
	extend : 'Ext.window.Window',
	title : '新增录单部门信息',//新增短距离外场映射
	closable : true,
	resizable:true,//可以调整窗口的大小
	closeAction : 'hide',//点击关闭是隐藏窗口
	width :650,
	height :270,
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	listeners:{
		beforehide:function(me){
			//关闭窗口的时候清空数据，由于不能清本外场文本框，所以重置另外一个框
			this.down('form').getForm().reset();
		}
	},
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		//新增窗口的底部按钮（取消、保存）
		me.fbar = [{
			text :baseinfo.focusRecordManagement.i18n('foss.baseinfo.cancel'),//取消
			margin:'0 0 0 330',
			handler :function(){
				me.close();
			}
		},{
			text : baseinfo.focusRecordManagement.i18n('foss.baseinfo.save'),//保存
			cls:'yellow_button',
			handler :function(){
				var form=me.down('form');
				if(form.getForm().isValid()){//校验form是否通过非空校验
		    		var model = new Foss.baseinfo.focusRecordManagement.Model();
		    		form.getForm().updateRecord(model);//将FORM中数据设置到MODEL里面
		    		var params = {'focusRecordManagementVo':{'entity':model.data}};
		    		var successFun = function(json){
						baseinfo.showInfoMes(json.message);//提示保存成功
						me.close();
						//刷新查询结果
						//Ext.getCmp('T_baseinfo-focusRecordManagement_content').getQueryGrid().getPagingToolbar().moveFirst();
					};
					var failureFun = function(json){
						if(Ext.isEmpty(json)){
							baseinfo.showErrorMes(baseinfo.focusRecordManagement.i18n('foss.baseinfo.requestTimeout'));//请求超时
						}else{
							baseinfo.showErrorMes(json.message);//提示失败原因
						}
					};
					var url = baseinfo.realPath('addFocusRecordManagement.action');
					baseinfo.requestJsonAjax(url,params,successFun,failureFun);//发送AJAX请求
				}}
		}];
		me.items = [Ext.create('Foss.baseinfo.focusRecordManagement.Form')];
		me.callParent([cfg]);
	}
});
/**
 * 修改窗口
 */
Ext.define('Foss.baseinfo.focusRecordManagement.UpdateWindow',{
	extend : 'Ext.window.Window',
	title : '修改录单部门信息',//修改短距离外场映射
	billingAmount:null,
	closable : true,
	resizable:false,
	closeAction : 'hide',
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	width :650,
	height :270,
	listeners:{
		beforehide:function(me){
			//关闭窗口的时候清空数据，由于不能清本外场文本框，所以重置另外一个框
			this.down('form').getForm().reset();//表格重置
		}
	},
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		//修改窗口的底部按钮（取消、保存）
		me.fbar = [{
			text :baseinfo.focusRecordManagement.i18n('foss.baseinfo.cancel'),//取消
			margin:'0 0 0 330',
			handler :function(){
				me.close();
			}
		},{
			text : baseinfo.focusRecordManagement.i18n('foss.baseinfo.save'),//保存
			cls:'yellow_button',
			handler :function(){
				var form=me.down('form');
				if(form.getForm().isValid()){//校验form是否通过非空校验
					var model = new Foss.baseinfo.focusRecordManagement.Model();
		    		form.getForm().updateRecord(model);//将FORM中数据设置到MODEL里面
		    		//当没修改原数据的情况下，点击保存，将直接提示保存成功，不走后台
					
			    		var params = {'focusRecordManagementVo':{'entity':model.data}};//组织新增数据
					params.focusRecordManagementVo.entity.startDate=form.getForm().findField('startDate').getValue();
					params.focusRecordManagementVo.entity.endDate=form.getForm().findField('endDate').getValue();
			    		var successFun = function(json){
							baseinfo.showInfoMes(json.message);//提示新增成功
							me.close();
							//刷新查询结果
							//Ext.getCmp('T_baseinfo-focusRecordManagement_content').getQueryGrid().getPagingToolbar().moveFirst();
						};
						var failureFun = function(json){
							if(Ext.isEmpty(json)){
								baseinfo.showErrorMes(baseinfo.shortFieldMap.i18n('foss.baseinfo.requestTimeout'));//请求超时
							}else{
								baseinfo.showErrorMes(json.message);//提示失败原因
							}
						};
						var url = baseinfo.realPath('updateFocusRecordManagement.action');
						baseinfo.requestJsonAjax(url,params,successFun,failureFun);//发送AJAX请求
					
		    	}
		    	} 
		}];
		me.items = [Ext.create('Foss.baseinfo.focusRecordManagement.Form')];
		me.callParent([cfg]);
	}
});
//新增窗口、修改窗口内部的Form
Ext.define('Foss.baseinfo.focusRecordManagement.Form', {
	extend : 'Ext.form.Panel',
	title:'选择部门信息',
	frame: true,
	flex:1,
	collapsible: true,
    defaults : {
    	xtype:'textfield',
    	margin : '5 5 5 10',
    	labelWidth:80,
    	anchor: '90%'
    },
    layout:'column',
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [{
					name : 'billingGroupCode',
					fieldLabel : '集中开单组',
					xtype : 'dynamicorgcombselector',
					type : 'ORG',
					billingGroup:'Y',//查询外场
					//labelWidth:100,
					allowBlank:false,
					columnWidth:0.5
		},{
			name: 'billingAmount',
			xtype:'textfield',
			//labelWidth:100,
			//width:400,
			allowBlank:false,
			regex:/(^[0-9]{0,50}$)/,
			regexText:'开单量必须为数字类型',
			columnWidth:0.4,
			fieldLabel : '开单量'
		},{
			xtype: 'rangeDateField',
			fieldLabel:'生效时间段',
			dateType: 'timefield',
			fromName: 'startDate',
			toName: 'endDate',
			disallowBlank: true,
			//editable:false,
			//fromValue:,
			//toValue:Ext.Date.format(baseinfo.getEndDate(new Date(),0),'Y-m-d H:i:s'),
			columnWidth: 0.9
		},{
			//ID，隐藏，用于修改方法的检索参数
			name:'id',
			hidden:true
		}];
		me.callParent([cfg]);
	}
});


//查询详情
Ext.define('Foss.baseinfo.focusRecordManagement.ViewWindow',{
	extend : 'Ext.window.Window',
	title : '查看录单部门信息',//修改短距离外场映射
	model:null,
	closable : true,
	resizable:false,
	closeAction : 'hide',
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	width :700,
	height :500,
	listeners:{
		beforehide:function(me){
			//关闭窗口的时候清空数据，由于不能清本外场文本框，所以重置另外一个框
			this.down('form').getForm().reset();//表格重置
		},
		beforeshow : function (me) { //显示WINDOW的时候清除数据
			var form=this.down('form').getForm();
			var model=me.model;
			form.findField('billingGroupCode').setCombValue(model.billingGroupName,model.billingGroupCode);
			form.findField('billingAmount').setValue(model.billingAmount);
			form.findField('transDepartmentCode').setValue(model.transDepartmentName);
			form.findField('salesDepartmentCode').setValue(model.salesDepartmentName);
		}
	},
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		//修改窗口的底部按钮（取消、保存）
		me.items = [Ext.create('Foss.baseinfo.focusRecordManagement.ViewForm')];
		me.callParent([cfg]);
	}
});
//查询详情
Ext.define('Foss.baseinfo.focusRecordManagement.ViewForm', {
	extend : 'Ext.form.Panel',
	title:'部门信息',
	frame: true,
	flex:1,
	collapsible: true,
    defaults : {
    	xtype:'textfield',
    	margin : '5 5 5 10',
    	labelWidth:80,
    	anchor: '90%'
    },
    layout:'column',
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
			me.items = [{
					name : 'billingGroupCode',
					fieldLabel : '集中开单组',
					xtype : 'dynamicorgcombselector',
					type : 'ORG',
					billingGroup:'Y',//查询外场
					columnWidth:0.5,
					readOnly:true
		},{
			name: 'billingAmount',
			xtype:'textfield',
			columnWidth:0.5,
			readOnly:true,
			fieldLabel : '开单量'//对应短距离外场
		},{
			name:'transDepartmentCode',
			xtype:'textfield',
			columnWidth:0.5,
			fieldLabel : '服务车队',
			labelWidth:80,
			readOnly:true
		},{
			name:'salesDepartmentCode',
			xtype:'textareafield',
			columnWidth:1,
			width:600,
			height :200,
			fieldLabel : '服务营业部',
			readOnly:true
		}];
		me.callParent([cfg]);
	}
});
Ext.onReady(function(){
	Ext.QuickTips.init();
	if (Ext.getCmp('T_baseinfo-focusRecordManagement_content')) {
		return;
	}
	var queryForm =Ext.create('Foss.baseinfo.focusRecordManagement.QueryForm');
	var queryGrid =Ext.create('Foss.baseinfo.focusRecordManagement.QueryGrid');
	Ext.getCmp('T_baseinfo-focusRecordManagement').add(Ext.create('Ext.panel.Panel',{
		id: 'T_baseinfo-focusRecordManagement_content',
		cls: "panelContentNToolbar",
		bodyCls: 'panelContentNToolbar-body',
		layout: 'auto',
		getQueryForm : function() {
			return queryForm;
		},
		getQueryGrid : function() {
			return queryGrid;
		},
		items: [queryForm,queryGrid]
	}));
});