/**
 * 临时租车标记时间管理菜单
 * 
 * @author 218392 张永雪
 * Build data 2014-12-15 15:23:19
 * 
 */

/*****************************定义Model*********************************/
Ext.define('Foss.baseinfo.temporaryRentalCarMarkTimeManagement.Model',{
	extend : 'Ext.data.Model',
	fields : [{
		name : 'id',
		type : 'string'
	},{
		name : 'deptAttributes',//部门属性
		type : 'string'
	},{
		name : 'deptAttributesName',//部门属性
		type : 'string'
	},{
		name : 'setTime',//设置时长(天)
		type : 'string'
	},{
		name : 'operationCode',//操作人工号 
		type : 'string'
	},{
		name : 'operationName',//操作人姓名
		type : 'string'
	},{
		name : 'operationDate',//操作时间
		type : 'date',
		convert: dateConvert,
		defaultValue:null
	},{
		name : 'active',//是否有效
		type : 'string'
	}]
});

/********************************定义store****************************************/
Ext.define('Foss.baseinfo.temporaryRentalCarMarkTimeManagement.Store',{
	extend : 'Ext.data.Store',
	pageSize : 10,//定义页面条数
	model : 'Foss.baseinfo.temporaryRentalCarMarkTimeManagement.Model',//绑定Model
	proxy :{ //以json方式加载数据
		type : 'ajax',
		actionMethods : 'POST',
		url : baseinfo.realPath('queryTemporaryRentalCarMarkTimeManagement.action'),
		reader : {
			type : 'json', 
			root : 'temporaryRentalCarMarkTimeManagementVo.entityList',  
			totalProperty : 'totalCount', //检索数据集中记录总数的属性名称
			successProperty : 'success'  //检索'success'标识的属性名称，该标识属性的值标示指定请求是否成功（典型表示为一个布尔值，或‘true’|‘fasle’）。 ...
		}
	},
	//构造函数
	constructor : function(config){
		var me = this,cfg = Ext.apply({},config);
		me.callParent([cfg]);
	},
	//监听器
	listeners : {
		beforeload : function (store,operation,eOpts){ //在一个新数据对象请求发送前，触发此事件
			var queryForm = Ext.getCmp('Foss_baseinfo_temporaryRentalCarMarkTimeManagement_queryForm_Id').getForm();
			if(queryForm != null){
				var deptAttributes = queryForm.findField('deptAttributes').getValue();
				var operationName = queryForm.findField('operationName').getValue();
				Ext.apply(operation,{
					params : {
						'temporaryRentalCarMarkTimeManagementVo.entity.deptAttributes' : deptAttributes,
						'temporaryRentalCarMarkTimeManagementVo.entity.operationName' : operationName
					}
				});
			}
		} 
	}
});

/*****************************定义新增、修改form表单*********************************/
//新增表单Form
Ext.define('Foss.baseinfo.temporaryRentalCarMarkTimeManagement.AddWindowForm',{
	extend : 'Ext.form.Panel',
	frame : true,
	height : 200,
	collapsible : true,
	title : baseinfo.temporaryRentalCarMarkTimeManagement.i18n('foss.baseinfo.add'),//新增
	defaults : {
		margin : '25 15 10 1',
		labelWidth : 90,
		labelAlign : 'right'
	},
	defaultType : 'textfield',
	layout :{
		type : 'table',
		columns : 2
	},
	//提交表单
	commitInfo : function(){
		var me = this;
		var basicForm = me.getForm();
		if(basicForm.isValid()){
			var record = null;//获取model实例
			record = Ext.create('Foss.baseinfo.temporaryRentalCarMarkTimeManagement.Model');
		
			basicForm.updateRecord(record);//将Form表单中的数据添加到设置到model中
			var jsonData = {'temporaryRentalCarMarkTimeManagementVo' : {entity : record.data}};
			var url = null;
			url = baseinfo.realPath('addTemporaryRentalCarMarkTimeManagement.action');
			var infoGrid = Ext.getCmp('T_baseinfo-temporaryRentalCarMarkTimeManagement_content').getQueryResultGrid();//获取查询列表
			Ext.Ajax.request({
				url : url,
				jsonData : jsonData,
				//成功
				success : function (response){
					var json = Ext.decode(response.responseText);//解析JSON字符串
					infoGrid.getPagingToolbar().moveFirst(); //保存成功，将列表数据重新加载
					infoGrid.showWarningMsg(baseinfo.temporaryRentalCarMarkTimeManagement.i18n('foss.baseinfo.notice'),json.message);//引用其他的国际化“通知”
					me.up('window').close();//关闭该Window窗口
				},
				//失败
				exception : function (response){
					var json = Ext.decode(response.responseText);//解析JSON字符串
					infoGrid.showWarningMsg(baseinfo.temporaryRentalCarMarkTimeManagement.i18n('foss.baseinfo.notice'),json.message);//保存失败，显示提示信息
				}
			});
		}
	},
	items : [FossDataDictionary.getDataDictionaryCombo('DEPARTMENT_OF_ATTRIBUTES',{
		fieldLabel : baseinfo.temporaryRentalCarMarkTimeManagement.i18n('foss.baseinfo.temporaryRentalCarMarkTimeManagement.deptAttributes'),//部门属性
		forceSelection:true,
		name : 'deptAttributes',
		allowBlank : false,
		width : 300
	}),{
		fieldLabel : baseinfo.temporaryRentalCarMarkTimeManagement.i18n('foss.baseinfo.temporaryRentalCarMarkTimeManagement.setTime'),//设置时长
		name : 'setTime',
		xtype : 'textfield',
		regex : /^([1-9]\d*)$/,
		regexText : '只能输入正整数!', // 正则表达式错误提示
		allowBlank : false,
		width : 300
	}],
	//构造函数
	constructor : function(config){
		var me = this,cfg = Ext.apply({},config);
		me.fbar = [{
			text : baseinfo.temporaryRentalCarMarkTimeManagement.i18n('foss.baseinfo.cancel'),//取消
			handler : function (){
				me.up().close();
			}
		},{
			text : baseinfo.temporaryRentalCarMarkTimeManagement.i18n('foss.baseinfo.reset'),//重置
			handler : function (){
					me.getForm().reset();
			}
		},{
			text : baseinfo.temporaryRentalCarMarkTimeManagement.i18n('foss.baseinfo.save'),//保存
			cls : 'yellow_button',
			handler : function (){
				me.commitInfo();
			}
		}];
		me.callParent([cfg]);
	}
});

//修改表单form
Ext.define('Foss.baseinfo.temporaryRentalCarMarkTimeManagement.UpdateWindowForm',{
	extend : 'Ext.form.Panel',
	frame : true,
	height : 200,
	collapsible : true,
	title : baseinfo.temporaryRentalCarMarkTimeManagement.i18n('foss.baseinfo.update'),//修改
	defaults : {
		margin : '25 15 10 1',
		labelWidth : 90,
		labelAlign : 'right'
	},
	defaultType : 'textfield',
	layout :{
		type : 'table',
		columns : 2
	},
	//提交表单
	commitInfo : function(){
		var me = this;
		var basicForm = this.getForm();
		if(basicForm.isValid()){
			var record = null;//获取model实例
			record = basicForm.getRecord();//
			basicForm.updateRecord(record);//将Form表单中的数据添加到设置到model中
			var jsonData = {'temporaryRentalCarMarkTimeManagementVo' : {entity : record.data}};
			var url = null;
			url = baseinfo.realPath('updateTemporaryRentalCarMarkTimeManagement.action');
			var infoGrid = Ext.getCmp('T_baseinfo-temporaryRentalCarMarkTimeManagement_content').getQueryResultGrid();//获取查询列表
			Ext.Ajax.request({
				url : url,
				jsonData : jsonData,
				//成功
				success : function (response){
					var json = Ext.decode(response.responseText);//解析JSON字符串
					infoGrid.getPagingToolbar().moveFirst(); //保存成功，将列表数据重新加载
					infoGrid.showWarningMsg(baseinfo.temporaryRentalCarMarkTimeManagement.i18n('foss.baseinfo.notice'),json.message);//引用其他的国际化“通知”
					me.up('window').close();//关闭该Window窗口
				},
				//失败
				exception : function (response){
					var json = Ext.decode(response.responseText);//解析JSON字符串
					infoGrid.showWarningMsg(baseinfo.temporaryRentalCarMarkTimeManagement.i18n('foss.baseinfo.notice'),json.message);//保存失败，显示提示信息
				}
			});
		}
	},
	items : [FossDataDictionary.getDataDictionaryCombo('DEPARTMENT_OF_ATTRIBUTES',{
		fieldLabel : baseinfo.temporaryRentalCarMarkTimeManagement.i18n('foss.baseinfo.temporaryRentalCarMarkTimeManagement.deptAttributes'),//部门属性
		forceSelection:true,
		name : 'deptAttributes',
		allowBlank : false,
		width : 300,
		hidden : true
	}),{
		fieldLabel : baseinfo.temporaryRentalCarMarkTimeManagement.i18n('foss.baseinfo.temporaryRentalCarMarkTimeManagement.deptAttributes'),//部门属性
		name : 'deptAttributesName',
		readOnly : true,
		xtype : 'textfield',
		width : 300
	},{
		fieldLabel : baseinfo.temporaryRentalCarMarkTimeManagement.i18n('foss.baseinfo.temporaryRentalCarMarkTimeManagement.setTime'),//设置时长
		name : 'setTime',
		xtype : 'textfield',
		regex : /^([1-9]\d*)$/,
		regexText : '只能输入正整数!', // 正则表达式错误提示
		fieldInitValue:null,
		allowBlank : false,
		width : 300
	}],
	//构造函数
	constructor : function(config){
		var me = this,cfg = Ext.apply({},config);
		me.fbar = [{
			text : baseinfo.temporaryRentalCarMarkTimeManagement.i18n('foss.baseinfo.cancel'),//取消
			handler : function (){
				me.up().close();
			}
		},{
			text : baseinfo.temporaryRentalCarMarkTimeManagement.i18n('foss.baseinfo.reset'),//重置
			handler : function (){
					//me.getForm().reset();
					var setTimeField =me.getForm().findField('setTime');
					setTimeField.setValue(setTimeField.fieldInitValue);
			}
		},{
			text : baseinfo.temporaryRentalCarMarkTimeManagement.i18n('foss.baseinfo.save'),//保存
			cls : 'yellow_button',
			handler : function (){
				me.commitInfo();
			}
		}];
		me.callParent([cfg]);
	}
});

/****************************定义新增、修改窗口*******************************/
//新增窗口
Ext.define('Foss.baseinfo.temporaryRentalCarMarkTimeManagement.AddWindow',{
	extend : 'Ext.window.Window',
	title : baseinfo.temporaryRentalCarMarkTimeManagement.i18n('foss.baseinfo.temporaryRentalCarMarkTimeManagement.AddWindowInfo'),//新增信息
	width : 700,
	height : 300,
	parent : null,//父元素
	infoModel : null,//保存信息model
	modal : true,//为true，窗口掩饰它背后的一切
	closeAction : 'hidden',//当标题头的关闭按钮被单击这个动作将被执行(默认为destroy)：这里设置为hide，hidden这个window通过将其的可见属性置为隐藏。这个window可以通过show方法再次渲染
	detailEntity : null,
	addWindowForm : null,
	//监听器
	listeners : {
		beforehide : function(me){//隐藏Window的时候清除数据
			me.getAddWindowForm().getForm().reset();//重置
			me.parent.getPagingToolbar().moveFirst();//移动到分页的第一页，和点击first按钮效果是一样的
		},
		beforeshow : function(me){
		}
	},
	//获取新增窗口的Form
	getAddWindowForm : function(){
		if(Ext.isEmpty(this.addWindowForm)){
			this.addWindowForm = Ext.create('Foss.baseinfo.temporaryRentalCarMarkTimeManagement.AddWindowForm');//获取新增窗口的form
		}
		return this.addWindowForm;
	},
	bindData : null,
	operationUrl : 'save',
	//获取信息列表
	infoGrid : null,
	getInfoGrid : function(){
		if(Ext.isEmpty(this.infoGrid)){
			this.infoGrid = Ext.getCmp('T_baseinfo-temporaryRentalCarMarkTimeManagement_content').getQueryResultGrid();//获取查询列表
		}
	},
	resetWindow : function (record,operationUrl){
		this.getAddWindowForm().loadRecord(record);//重新加载
		this.bindData = record;
		this.operationUrl = operationUrl;
	},
	constructor : function(config){
		var me = this,cfg = Ext.apply({},config);
		me.items = [me.getAddWindowForm()];
		me.callParent([cfg]);
	}
});

//修改窗口
Ext.define('Foss.baseinfo.temporaryRentalCarMarkTimeManagement.UpdateWindow',{
	extend : 'Ext.window.Window',
	title : baseinfo.temporaryRentalCarMarkTimeManagement.i18n('foss.baseinfo.temporaryRentalCarMarkTimeManagement.UpdateWindowInfo'),//修改信息信息
	width : 700,
	height : 300,
	parent : null,//父元素
	infoModel : null,//保存信息model
	modal : true,//为true，窗口掩饰它背后的一切
	closeAction : 'hidden',//当标题头的关闭按钮被单击这个动作将被执行(默认为destroy)：这里设置为hide，hidden这个window通过将其的可见属性置为隐藏。这个window可以通过show方法再次渲染
	detailEntity : null,
	updateWindowForm : null,
	//监听器
	listeners : {
		beforehide : function(me){//隐藏Window的时候清除数据
			me.getUpdateWindowForm().getForm().reset();//重置
			me.parent.getPagingToolbar().moveFirst();//移动到分页的第一页，和点击first按钮效果是一样的
		},
		beforeshow : function(me){
		}
	},
	//获取修改窗口的form
	getUpdateWindowForm : function(){
		if(Ext.isEmpty(this.updateWindowForm)){
			this.updateWindowForm = Ext.create('Foss.baseinfo.temporaryRentalCarMarkTimeManagement.UpdateWindowForm');//获取修改form
		}
		return this.updateWindowForm;
	},
	bindData : null,
	operationUrl : 'save',
	//获取信息列表
	infoGrid : null,
	getInfoGrid : function(){
		if(Ext.isEmpty(this.infoGrid)){
			this.infoGrid = Ext.getCmp('T_baseinfo-temporaryRentalCarMarkTimeManagement_content').getQueryResultGrid();//获取查询列表
		}
	},
	resetWindow : function (record,operationUrl){
		this.getUpdateWindowForm().loadRecord(record);//重新加载
		var setTimeField = this.getUpdateWindowForm().getForm().findField('setTime');
		setTimeField.fieldInitValue=setTimeField.getValue();
		this.bindData = record;
		this.operationUrl = operationUrl;
	},
	constructor : function(config){
		var me = this,cfg = Ext.apply({},config);
		me.items = [me.getUpdateWindowForm()];
		me.callParent([cfg]);
	}
});

/*************************查询结果Grid**************************/
Ext.define('Foss.baseinfo.temporaryRentalCarMarkTimeManagement.QueryResultGrid',{
	extend : 'Ext.grid.Panel',
	title : baseinfo.temporaryRentalCarMarkTimeManagement.i18n('foss.baseinfo.QueryResultGrid'),//查询结果信息
	id : 'Foss_baseinfo_temporaryRentalCarMarkTimeManagement_queryResultGrid_Id',
	cls : 'autoHeight',
	bodyCls : 'autoHeight',
	emptyText : baseinfo.temporaryRentalCarMarkTimeManagement.i18n('foss.baseinfo.temporaryRentalCarMarkTimeManagement.queryEmpty'),//查询结果为空
	frame : true,//表格对象边框线
	columnLines : true,//表格线
	stripeRows : true,//实现各行换色
	collapsible : true,//可收缩展开
	animCollapse : true,//动画的效果展开或收缩
	selModel : Ext.create('Ext.selection.CheckboxModel'),//Ext.selection.CheckboxModel:一个选择模式它将渲染一列可以选中或者反选的复选框. 默认选择模型是多选
	store : null,
	//定义新增窗口的函数
	addWindow : null,
	getAddWindow : function(){
		if(Ext.isEmpty(this.addWindow)){
			this.addWindow = Ext.create('Foss.baseinfo.temporaryRentalCarMarkTimeManagement.AddWindow');
			this.addWindow.parent = this;//父元素
		}
		return this.addWindow;
	},
	//定义一个修改窗口的函数
	updateWindow : null,
	getUpdateWindow : function(){
		if(Ext.isEmpty(this.updateWindow)){
			this.updateWindow = Ext.create('Foss.baseinfo.temporaryRentalCarMarkTimeManagement.UpdateWindow');
			this.updateWindow.parent = this;//父元素
		}
		return this.updateWindow;
	},
	//作废函数
	deleteInfos : function(){
		var me = this;
		var selections = me.getSelectionModel().getSelection();//获取选中的数据
		if(selections.length < 1){//判断是否选至少中了一条 
			me.showWarningMsg(baseinfo.temporaryRentalCarMarkTimeManagement.i18n('foss.baseinfo.notice'),baseinfo.temporaryRentalCarMarkTimeManagement.i18n('foss.baseinfo.deleteNoticeMsg'));//弹出提示语：请选择一条数据进行作废操作！
			return ;//没有则提示返回
		}
		
		Ext.Msg.confirm(baseinfo.temporaryRentalCarMarkTimeManagement.i18n('foss.baseinfo.notice'),baseinfo.temporaryRentalCarMarkTimeManagement.i18n('foss.baseinfo.deleteWarnMsg'),function(e){// 作废数据后不可恢复，确定要作废么？---------yinyong------
			Ext.MessageBox.buttonText.yes = baseinfo.temporaryRentalCarMarkTimeManagement.i18n('foss.baseinfo.confirm');//确定
			Ext.MessageBox.buttonText.no = baseinfo.temporaryRentalCarMarkTimeManagement.i18n('foss.baseinfo.cancel');//取消
			if(e == 'yes'){ //询问是否删除
				var ids = new Array();//定义一个存放虚拟编码的数组
				for(var i = 0;i< selections.length;i++){
					ids.push(selections[i].get('id'));
				}
				var url = baseinfo.realPath('deleteTemporaryRentalCarMarkTimeManagement.action');
				var jsonData = {'temporaryRentalCarMarkTimeManagementVo' : {'idList' : ids}};
				//调用Ajax请求
				me.ajaxRequest(url,jsonData);
			}
		});
	},
	//ajax请求
	ajaxRequest : function (url,jsonData){
		var me = this;
		Ext.Ajax.request({
			url : url,
			jsonData : jsonData,
			//作废成功
			success : function (response){
				var json = Ext.decode(response.responseText);
				me.getPagingToolbar().moveFirst();
				//打印成功消息
				me.showWarningMsg(baseinfo.temporaryRentalCarMarkTimeManagement.i18n('foss.baseinfo.notice'),json.message);
			},
			//保存失败
			exception : function (){
				var json = Ext.decode(response.responseText);
				me.showWarningMsg(baseinfo.temporaryRentalCarMarkTimeManagement.i18n('foss.baseinfo.notice'),json.message);
			} 
		});
	},
	//消息提醒框
	showWarningMsg : function (title,message,fun){
		Ext.Msg.show({
			title : title,
			msg : message,
			width : 120,
			buttons : Ext.Msg.OK,
			icon : Ext.MessageBox.WARNING,
			callback : function (e){
				if(!Ext.isEmpty(fun)){
					if(e == 'ok'){
						fun();
					}
				}
			} 
		});
		//3秒后提醒框隐藏
		setTimeout(function(){
			Ext.Msg.hide();
		},3000);
	},
	// 分页组件
	pagingToolbar : null,
	getPagingToolbar : function(){
		var me = this;
		if(Ext.isEmpty(me.pagingToolbar)){
			me.pagingToolbar =Ext.create('Deppon.StandardPaging',{//创建分页组件，Deppon.StandardPaging
				store : me.store,
				pageSize : 10,
				prependButtons : true,//前置按钮true插入的任何配置项，在分页按钮前
				defaults : {
					margin : '0 0 15 3'
				}
			});
		}
		return me.pagingToolbar;
	},
	//定义表格列信息
	columns : [{
		xtype : 'actioncolumn',//一个用于简化列定义的子类，用来在单元格里渲染一个或多个图标。 每个图标均可自定义点击处理事件。
		width : 100,
		text : baseinfo.temporaryRentalCarMarkTimeManagement.i18n('foss.baseinfo.operate'),//操作
		align : 'center',
		items : [{
			iconCls : 'deppon_icons_edit',  //引用编辑图标样式
			tooltip : baseinfo.temporaryRentalCarMarkTimeManagement.i18n('foss.baseinfo.edit'),// 设置提示框 ： 编辑
			disabled:!baseinfo.temporaryRentalCarMarkTimeManagement.isPermission('temporaryRentalCarMarkTimeManagement/temporaryRentalCarMarkTimeManagementUpdateButton'),
			hidden:!baseinfo.temporaryRentalCarMarkTimeManagement.isPermission('temporaryRentalCarMarkTimeManagement/temporaryRentalCarMarkTimeManagementUpdateButton'),
			//编辑事件
			handler : function (grid,rowIndex,colIndex){
				//获取选中的数据
				var record = grid.getStore().getAt(rowIndex);
				grid.up('grid').getUpdateWindow().infoModel = record;
				//获得修改窗口
				var updateWindow = grid.up('grid').getUpdateWindow();
				updateWindow.resetWindow(record,'update');
				updateWindow.show();
			}
		},
		{
			iconCls:'deppon_icons_delete',
			tooltip : baseinfo.temporaryRentalCarMarkTimeManagement.i18n('foss.baseinfo.void'),//作废
			disabled:!baseinfo.temporaryRentalCarMarkTimeManagement.isPermission('temporaryRentalCarMarkTimeManagement/temporaryRentalCarMarkTimeManagementGridVoidButton'),
			hidden:!baseinfo.temporaryRentalCarMarkTimeManagement.isPermission('temporaryRentalCarMarkTimeManagement/temporaryRentalCarMarkTimeManagementGridVoidButton'),
			handler : function(grid, rowIndex,colIndex) {
				//作废操作提示窗口
				Ext.Msg.show({
					title:baseinfo.temporaryRentalCarMarkTimeManagement.i18n('foss.baseinfo.notice'),
					msg:baseinfo.temporaryRentalCarMarkTimeManagement.i18n('foss.baseinfo.deleteWarnMsg'),
					buttons:Ext.Msg.YESNO,
					icon: Ext.Msg.QUESTION, 
					fn:function(btn){
						if(btn == 'yes'){
							//获取选中的数据
							var record = grid.getStore().getAt(rowIndex)
							var ids = new Array();//虚拟编码数组
							ids.push(record.data.id);
							var url = baseinfo.realPath('deleteTemporaryRentalCarMarkTimeManagement.action');
							var jsonData = {'temporaryRentalCarMarkTimeManagementVo':{'idList':ids}};
							//调用Ajax请求
							grid.up('grid').ajaxRequest(url,jsonData);
						}
					}
				});
			}
		}]
	},{
		header : baseinfo.temporaryRentalCarMarkTimeManagement.i18n('foss.baseinfo.temporaryRentalCarMarkTimeManagement.deptAttributes'),//部门属性
		dataIndex : 'deptAttributesName',
		flex:1,
		align : 'center'
	},{
		header : baseinfo.temporaryRentalCarMarkTimeManagement.i18n('foss.baseinfo.temporaryRentalCarMarkTimeManagement.setTime'),//设置时长（天）
		dataIndex : 'setTime',
		flex:1,
		align : 'center'
	},{
		header : baseinfo.temporaryRentalCarMarkTimeManagement.i18n('foss.baseinfo.temporaryRentalCarMarkTimeManagement.operationCode'),//操作人工号
		dataIndex : 'operationCode',
		flex:1,
		align : 'center'
	},{
		header : baseinfo.temporaryRentalCarMarkTimeManagement.i18n('foss.baseinfo.temporaryRentalCarMarkTimeManagement.operationName'),//操作人工号
		dataIndex : 'operationName',
		flex:1,
		align : 'center'
	},{
		header : baseinfo.temporaryRentalCarMarkTimeManagement.i18n('foss.baseinfo.temporaryRentalCarMarkTimeManagement.operationDate'),//操作时间
		dataIndex : 'operationDate',
		flex:1,
		align : 'center',
		renderer:function(v){
			if(!Ext.isEmpty(v)){
				return Ext.Date.format(new Date(v), 'Y-m-d H:i:s');
		    }
		    return v;
		}
	}],
	//构造函数
	constructor : function (config){
		var me = this,cfg = Ext.apply({},config);
		me.store = Ext.create('Foss.baseinfo.temporaryRentalCarMarkTimeManagement.Store');
		me.bbar = me.getPagingToolbar();
		me.getPagingToolbar().store = me.store;
		me.dockedItems = [{
			xtype : 'toolbar',
			dock : 'top',
			layout : 'column',
			defaults : {
				margin : '0 0 5 3'
			},
			items : [{
				xtype : 'button',
				text : baseinfo.temporaryRentalCarMarkTimeManagement.i18n('foss.baseinfo.add'),//增加
				disabled:!baseinfo.temporaryRentalCarMarkTimeManagement.isPermission('temporaryRentalCarMarkTimeManagement/temporaryRentalCarMarkTimeManagementAddButton'),
				hidden:!baseinfo.temporaryRentalCarMarkTimeManagement.isPermission('temporaryRentalCarMarkTimeManagement/temporaryRentalCarMarkTimeManagementAddButton'),
				width : 80,
				handler : function (){
					this.addWindow = me.getAddWindow();
					this.addWindow.show();
					
				}
			},{
				xtype : 'button',
				text : baseinfo.temporaryRentalCarMarkTimeManagement.i18n('foss.baseinfo.void'),//作废
				disabled:!baseinfo.temporaryRentalCarMarkTimeManagement.isPermission('temporaryRentalCarMarkTimeManagement/temporaryRentalCarMarkTimeManagementVoidButton'),
				hidden:!baseinfo.temporaryRentalCarMarkTimeManagement.isPermission('temporaryRentalCarMarkTimeManagement/temporaryRentalCarMarkTimeManagementVoidButton'),
				width : 80,
				handler : function (){
					me.deleteInfos();
				}
			}]
		}],
		me.callParent([cfg]);
	}
});

/*************************查询条件Form***************************/
Ext.define('Foss.baseinfo.temporaryRentalCarMarkTimeManagement.QueryConditionForm',{
	extend : 'Ext.form.Panel',
	title : baseinfo.temporaryRentalCarMarkTimeManagement.i18n('foss.baseinfo.TemporaryRentalCarMarkTimeManagement'),//临时租车标记时间管理信息查询条件
	id : 'Foss_baseinfo_temporaryRentalCarMarkTimeManagement_queryForm_Id',
	frame : true,
	collapsible : true,
	layout : {
		type : 'table',
		columns : 3
	},
	defaults : {
		labelSeparator : ':',
		margin : '12 10 5 10',
		anchor : '100%'
	},
	height : 180,
	defaultType : 'textfield',
	layout : 'column',
	items : [FossDataDictionary.getDataDictionaryCombo('DEPARTMENT_OF_ATTRIBUTES',{
		fieldLabel : baseinfo.temporaryRentalCarMarkTimeManagement.i18n('foss.baseinfo.temporaryRentalCarMarkTimeManagement.deptAttributes'),//部门属性
		forceSelection:true,
		name : 'deptAttributes',
		columnWidth : 0.33
	}),{
		fieldLabel : baseinfo.temporaryRentalCarMarkTimeManagement.i18n('foss.baseinfo.temporaryRentalCarMarkTimeManagement.Name'),//姓名
		name : 'operationName',
		maxLength : 20,
		xtype : 'textfield',
		columnWidth : 0.33
	}],
	constructor : function(config){
		var me = this,cfg = Ext.apply({},config);
		me.fbar = [{
			xtype : 'button',
			text : baseinfo.temporaryRentalCarMarkTimeManagement.i18n('foss.baseinfo.reset'), //重置
			width : 70,
			margin : '0 800 0 0',
			handler : function (){
				me.getForm().reset();
			}
		},{
			xtype : 'button',
			text : baseinfo.temporaryRentalCarMarkTimeManagement.i18n('foss.baseinfo.query'),//查询
			disabled:!baseinfo.temporaryRentalCarMarkTimeManagement.isPermission('temporaryRentalCarMarkTimeManagement/temporaryRentalCarMarkTimeManagementQueryButton'),
			hidden:!baseinfo.temporaryRentalCarMarkTimeManagement.isPermission('temporaryRentalCarMarkTimeManagement/temporaryRentalCarMarkTimeManagementQueryButton'),
			width : 70,
			cls : 'yellow_button',
			handler : function (){
				if(me.getForm().isValid()){
					Ext.getCmp('T_baseinfo-temporaryRentalCarMarkTimeManagement_content').getQueryResultGrid().getPagingToolbar().moveFirst();//---zhuyi-这个方法要在查询grid列表中写--getPagingToolbar--
				}
			}
		}];
		me.callParent([cfg]);
	}
});

/***********************入口onReady*****************************/
Ext.onReady(function(){
	Ext.QuickTips.init();//设置悬浮提示
	//查询Form
	var queryForm = Ext.create('Foss.baseinfo.temporaryRentalCarMarkTimeManagement.QueryConditionForm'); 
	//查询结果Grid
	var queryGrid = Ext.create('Foss.baseinfo.temporaryRentalCarMarkTimeManagement.QueryResultGrid');
	
	Ext.getCmp('T_baseinfo-temporaryRentalCarMarkTimeManagement').add(Ext.create('Ext.panel.Panel',{
		id : 'T_baseinfo-temporaryRentalCarMarkTimeManagement_content',
		cls:'panelContentNToolbar',
		bodyCls : 'panelContentNToolbar-body',
		layout : 'auto',
		//查询Form
		getQueryConditionForm : function(){
			return queryForm;
		},
		//查询结果列表Grid
		getQueryResultGrid : function(){
			return queryGrid;
		},
		items : [queryForm,queryGrid]
	}));
});